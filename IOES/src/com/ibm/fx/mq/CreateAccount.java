//tag		Name       Date			CSR No			Description 
//[001]   Raveendra    28-Nov-2014                  Set processing count 10 in case of data issue exception is generated when scheduler is in progress

package com.ibm.fx.mq;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.ibm.fx.dto.AcctDTO;
import com.ibm.fx.dto.ExtendedData;
import com.ibm.ioes.exception.IOESException;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Utility;



public class CreateAccount {

	private static final int Fetch_Acc_Status_DataFound=1;
	private static final int Fetch_Acc_Status_DataNotFound=2;
	
	private static final int Account_Created_Success=3;
	private static final int Account_Created_Failure=2;
	private static final String sqlGetAccountExternalIds = "{call IOE.SP_GET_ACC_EXT_IDS(?)}";
	
	
	public final static int ENTRIES_NEW =0;
	public final static int ENTRIES_UNDER_PROCESS =1;
	public final static int ENTRIES_FAILED =2;
	private static final String sqlGetAccountExtendedData = "call IOE.FX_GET_ACC_EXTENDED_DATA(?)";
	private static final String sqlFXAccountStatusSet = "{call IOE.FX_ACCOUNT_SET_STATUS(?,?,?,?,?,?,?,?)}";
	
	
	/**
	 * Read account info from intermediate tables and send them to FX for searching/creation forthe specified order
	 * update local table with the account internal id  created/searched 
	 * @param conn
	 * @param orderNo
	 * @throws IOESException
	 */
	public void createAccount(Connection conn, Long orderNo) throws IOESException
	{
		
		AcctDTO accDto=null;
		int fetchAccStatus=0;
		resetStatsToZero(conn,orderNo);
		String errorLogMsg=null;
		//Start [001]
		ArrayList<String> dataIssueException=Utility.getdataIssueException(conn);
		//End [001]
		
		do
		{
			try
			{
				accDto=new AcctDTO();
				//fetch next unprocessed() account creation adat from local table
				fetchAccStatus=fetchNextAccountData(conn,accDto,orderNo);
				if(fetchAccStatus==Fetch_Acc_Status_DataFound)
				{
					//send account data to fx for creation or find in case of existing account
					if(orderNo == null || orderNo== 0 )
					{
					 errorLogMsg="In Account Creation at FX Process . Processing AC External Id:"+accDto.getAcctExternalId()
									+"\nRow Id :"+accDto.getId();
									
					saveAccount(accDto,errorLogMsg);	
					}
					else
					{
						 errorLogMsg="In Account Creation at FX Process . Processing AC External Id:"+accDto.getAcctExternalId()
						             +"\nRow Id :"+accDto.getId()
						             +"\nOrder No:"+orderNo;
						saveAccount(accDto,errorLogMsg);
					}
						
					
					int saveStatus=accDto.getReturnStatus();
					if(saveStatus==1)
					{
						//success
						setStatus(conn,accDto,Account_Created_Success,errorLogMsg);
					}else
					{
						
						boolean isDataIssueException=Utility.isDataIssueException(dataIssueException,accDto.getException());
				    	accDto.setDataIssueException(isDataIssueException);
						setStatus(conn,accDto,Account_Created_Failure,errorLogMsg);	
					}
				}
			}
			catch(Throwable ex)
			{
				if(orderNo == null || orderNo== 0 )
				{
				 Utility.onEx_LOG_RET_NEW_EX(ex, "createAccount", "CreateAccount", "In FX account creation , order no:", true, true);
				}
				else
				{
				 Utility.onEx_LOG_RET_NEW_EX(ex, "createAccount", "CreateAccount", "In FX account creation , order no:"+orderNo, true, true);
				}
				}
		}while(fetchAccStatus==Fetch_Acc_Status_DataFound);
		
	
	}

	private void resetStatsToZero(Connection conn, Long orderNo) {
		try
		{
			String sqlUpdate=null;
				if(orderNo == null || orderNo == 0)
				{
				 sqlUpdate="Update IOE.TFX_AccountCreate " +
						" set processingStatus=0, lastUpdatedDate=CURRENT TIMESTAMP " +
						" where processingStatus in (1,2) " ;
				conn.createStatement().executeUpdate(sqlUpdate);
				}
				else
				{
					sqlUpdate="Update IOE.TFX_AccountCreate " +
					" set processingStatus=0, lastUpdatedDate=CURRENT TIMESTAMP " +
					" where processingStatus in (1,2)  and ORDERNO ="+orderNo ;
			        conn.createStatement().executeUpdate(sqlUpdate);
				}
		}catch (Exception ex) {
			Utility.onEx_LOG_RET_NEW_EX(ex, "setStatus", "CreateAccount", null, true, true);
		}
	}
	private void setStatus(Connection conn, AcctDTO accDto, int status, String errorLogMsg) throws IOESException {
		
		CallableStatement cstmt=null;
		try
		{
			cstmt=conn.prepareCall(sqlFXAccountStatusSet);
				cstmt.setInt(1, status);
				if(accDto.getAcctInternalId()==null)
				{
					cstmt.setNull(2, java.sql.Types.INTEGER);	
				}
				else
				{
					cstmt.setInt(2, Integer.parseInt(accDto.getAcctInternalId()));	
				}
				
				cstmt.setLong(3, accDto.getId());
				
				if("YES".equals(accDto.getIsCreatedNow()))
				{
					cstmt.setInt(4, 1);	
				}
				else
				{
					cstmt.setInt(4, 0);
				}
				if(accDto.getFoundActiveDate()!=null)
				{
					cstmt.setTimestamp(5, new java.sql.Timestamp(accDto.getFoundActiveDate().getTime()));
				}
				else
				{
					cstmt.setNull(5, java.sql.Types.TIMESTAMP);
				}
				if(status==2){
				java.sql.Clob clobData = 
					com.ibm.db2.jcc.t2zos.DB2LobFactory.createClob(Utility.getStackTrace(accDto.getException()));
				cstmt.setClob(6, clobData);
				cstmt.setString(7,(accDto.getException_message()));	
				}
				
				else{
					
					cstmt.setNull(6, java.sql.Types.CLOB);
					cstmt.setNull(7, java.sql.Types.CHAR);
					
				}
				
				if(accDto.isDataIssueException()){
					cstmt.setLong(8, 1);
				}else{
					cstmt.setLong(8, 0);
				}
				cstmt.execute();
				
				/*String sqlUpdate="Update IOE.TFX_AccountCreate " +
						" set processingStatus="+status+" , lastUpdatedDate=CURRENT TIMESTAMP " +
						" , acctInternalId="+accDto.getAcctInternalId()+
						" where ID="+accDto.getId();*/
				//conn.createStatement().executeUpdate(sqlUpdate);
		}catch (Exception ex) {
			throw Utility.onEx_LOG_RET_NEW_EX(ex, "setStatus", "CreateAccount", errorLogMsg, true, true);
		}
		finally
		{
			if(cstmt!=null)DbConnection.closeCallableStatement(cstmt);
		}
		
	}

	private int fetchNextAccountData(Connection conn, AcctDTO accDto, Long orderNo) throws IOESException{
		int fetchAccStatus=0;
		
		ResultSet rs=null;
		try
		{
			
			String sql=null;
			
				if(orderNo == null || orderNo== 0)
				{
				 sql=" SELECT  ID, prename, fName, lName, address1, address2, address3, " +
						" billcompany,  billcountrycode, billstate, billcity, billzip, " +
						" STATEMENT_TO_FAX_NO, vipCode, mktCode,  acctInternalId," +
						" acctExternalId, processingStatus,acctCategory," +
						" custCountryCode,billFmtOpt,billDispMeth," +
						" DateActive,BILL_PERIOD,CURRENCY_CODE,SEGMENT_ID,OWNING_COST_CTR," +
						" FX_PARENT_ACC_EXTERNAL_ID,orderNo,CONTACT1_NAME,CONTACT1_PHONE,STATEMENT_TO_EMAIL_FOR_CONTACT1,EXRATE_CLASS,NOTES_TYPE_OF_ORDER,RATE_CLASS " +
						" FROM IOE.TFX_AccountCreate " +
						" where processingStatus in (0) and PROCESSING_COUNT<(SELECT BIGINT(KEYVALUE) FROM IOE.TM_APPCONFIG WHERE KEYNAME='PROCESSING_COUNT') and acctExternalId is not null order by ID asc fetch first row only";
				    rs=conn.createStatement().executeQuery(sql);
				}
				else
				{
					sql=" SELECT  ID, prename, fName, lName, address1, address2, address3, " +
					" billcompany,  billcountrycode, billstate, billcity, billzip, " +
					" STATEMENT_TO_FAX_NO, vipCode, mktCode,  acctInternalId," +
					" acctExternalId, processingStatus,acctCategory," +
					" custCountryCode,billFmtOpt,billDispMeth," +
					" DateActive,BILL_PERIOD,CURRENCY_CODE,SEGMENT_ID,OWNING_COST_CTR," +
					" FX_PARENT_ACC_EXTERNAL_ID,orderNo,CONTACT1_NAME,CONTACT1_PHONE,STATEMENT_TO_EMAIL_FOR_CONTACT1,EXRATE_CLASS ,NOTES_TYPE_OF_ORDER,RATE_CLASS" +
					" FROM IOE.TFX_AccountCreate " +
					" where processingStatus in (0) and PROCESSING_COUNT<(SELECT BIGINT(KEYVALUE) FROM IOE.TM_APPCONFIG WHERE KEYNAME='PROCESSING_COUNT') and orderNo="+orderNo+" and acctExternalId is not null order by ID asc fetch first row only";
			        rs=conn.createStatement().executeQuery(sql);
				}
				if(rs.next())
				{
					fetchAccStatus=Fetch_Acc_Status_DataFound;
				//	accDto=new AcctDTO();
					accDto.setAcctExternalId((rs.getString("acctExternalId")));
					
					accDto.setAddress1((rs.getString("address1")));
					accDto.setAddress2((rs.getString("address2")));
					accDto.setAddress3((rs.getString("address3")));
					accDto.setBillcity((rs.getString("billcity")));
					accDto.setBillcompany((rs.getString("billcompany")));
					
					if(rs.getString("billcountrycode")!=null)
						accDto.setBillcountrycode(rs.getInt("billcountrycode"));
					
					accDto.setBillPeriod((rs.getString("BILL_PERIOD")));
					accDto.setBillstate((rs.getString("billstate")));
					accDto.setBillzip((rs.getString("billzip")));
					accDto.setStatmentfaxno((rs.getString("STATEMENT_TO_FAX_NO")));
					accDto.setPrename((rs.getString("prename")));
					accDto.setFName((rs.getString("fName")));
					accDto.setLName((rs.getString("lName")));
					
					if(rs.getString("mktCode")!=null)
						accDto.setMktCode(rs.getInt("mktCode"));
					
					
					
					
					if(rs.getString("vipCode")!=null)
						accDto.setVipCode(rs.getInt("vipCode"));
					
					if(rs.getString("acctCategory")!=null)
						accDto.setAccountCategory(rs.getInt("acctCategory"));
					
					//if(rs.getString("FX_PARENT_INTERNAL_ID")!=null)
						//accDto.setParentInternalId(rs.getInt("FX_PARENT_INTERNAL_ID"));
					accDto.setParentAccExternalId((rs.getString("FX_PARENT_ACC_EXTERNAL_ID")));
					
					if(rs.getString("custCountryCode")!=null)
						accDto.setCustCountryCode(rs.getInt("custCountryCode"));
					
					
					if(rs.getString("billFmtOpt")!=null)
						accDto.setBillFmtOpt(rs.getInt("billFmtOpt"));
					
					if(rs.getString("billDispMeth")!=null)
						accDto.setBillDispMeth(rs.getInt("billDispMeth"));
					
					
					accDto.setDateActive(rs.getDate("DateActive"));
					
					if(rs.getString("CURRENCY_CODE")!=null)
						accDto.setCurrencyCode(rs.getInt("CURRENCY_CODE"));
					
					if(rs.getString("SEGMENT_ID")!=null)
						accDto.setSegmentId(rs.getInt("SEGMENT_ID"));
					
					if(rs.getString("OWNING_COST_CTR")!=null)
						accDto.setOwningCostCtr(rs.getInt("OWNING_COST_CTR"));
					
					accDto.setOrderNo(rs.getInt("ORDERNO"));
					
					accDto.setContact1_Name(rs.getString("CONTACT1_NAME"));
					
					accDto.setContact1_Phone(rs.getString("CONTACT1_PHONE"));
					
					accDto.setStatement_to_email_for_contact1(rs.getString("STATEMENT_TO_EMAIL_FOR_CONTACT1"));
					
					if(rs.getString("EXRATE_CLASS")!=null)
						accDto.setExrateClass(rs.getInt("EXRATE_CLASS"));
					
					if(rs.getString("RATE_CLASS")!=null)
					{
						accDto.setRateClassDefault(rs.getInt("RATE_CLASS"));
					}
					else
					{
						accDto.setRateClassDefault(null);
					}
					
					long id=rs.getLong("ID");
					accDto.setId(id);
					accDto.setTypeOfOrder(rs.getString("NOTES_TYPE_OF_ORDER"));
					rs.close();
					
					//fetch externalids of account
					/*ArrayList<AccountExternalIdDto> externalIds = new ArrayList<AccountExternalIdDto>();
					
					CallableStatement cstmt=conn.prepareCall(sqlGetAccountExternalIds);
					cstmt.setLong(1, id);
					ResultSet rsExternalIds=cstmt.executeQuery();
					AccountExternalIdDto extDto=null;
					while(rsExternalIds.next())
					{
						extDto=new AccountExternalIdDto();
						extDto.setAccountExternalId(rsExternalIds.getString("EXTERNAL_ID"));
						extDto.setAccountExternalIdType((short)rsExternalIds.getInt("EXTERNAL_ID_TYPE"));
						int isCurrent=rsExternalIds.getShort("IS_CURRENT");
						if(isCurrent==1)
						{
							extDto.setIsCurrent(true);	
						}
						else
						{
							extDto.setIsCurrent(false);
						}
						externalIds.add(extDto);
					}
					
					accDto.setExternalIds(externalIds);*/
					
					
					//fetch Extended Data
//					fetch externalids of account
					ArrayList<ExtendedData> extendedData = Utility.getFxExtendedData(conn,id,ExtendedData.AssociatedWith_CMF);
					accDto.setExtendedData(extendedData);
					
					
					String sqlUpdate="Update IOE.TFX_AccountCreate set processingStatus=1,PROCESSING_COUNT=PROCESSING_COUNT+1, lastUpdatedDate=CURRENT TIMESTAMP where ID="+id;
					conn.createStatement().executeUpdate(sqlUpdate);
					
					
				}
				else
				{
					fetchAccStatus=Fetch_Acc_Status_DataNotFound;	
				}
			
			
		}catch (Exception ex) {
			throw Utility.onEx_LOG_RET_NEW_EX(ex, "fetchNextAccountData", "CreateAccount", null, true, true);
		}
		finally
		{
			try
			{
			if(rs!=null)DbConnection.closeResultset(rs);
			}
			catch(Exception ex){ex.printStackTrace();}
		}
		
		
		return fetchAccStatus;
	}

	private int saveAccount(AcctDTO accDto, String errorLogMsg) throws IOESException{
		int saveStatus=0;
		
		try
		{
			IOESKenanManager API=new IOESKenanManager();
			API.loginKenan();
			try
			{
				API.createAccount1(accDto,errorLogMsg);
			}
			finally
			{
				API.close();
			}
		}
		catch (Exception ex) {
			Utility.onEx_LOG_RET_NEW_EX(ex, "saveAccount", "CreateAccount", errorLogMsg, true, true);
		}
		
		return saveStatus;
	}

	
	
		
	/*public static void main(String[] args) {
		CreateAccount account=new CreateAccount();
		try {
			account.createAccount();
		} catch (FXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
}

