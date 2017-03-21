package com.ibm.fx.mq;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.ibm.fx.dto.NrcDto;
import com.ibm.fx.dto.RcDto;
import com.ibm.fx.dto.ServiceDTO;
import com.ibm.ioes.exception.IOESException;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Utility;



public class CreateServiceWith_RC_or_NRC {
/*
	private static final int Fetch_Ser_Status_DataFound=1;
	private static final int Fetch_Ser_Status_DataNotFound=2;
	
	private static final int Service_Created_Success=3;
	private static final int Service_Created_Failure=2;
	
	private static final String sqlGetFX_RC_Charges = "{call IOE.GET_FX_RC(?)}";
	private static final String sqlGetFX_NRC_Charges = "{call IOE.GET_FX_NRC(?)}";
	private static final String sqlUpdateRcCreatoinResponse = "{call IOE.FX_UPDATE_RC_RESPONSE(?,?,?)}";
	private static final String sqlUpdateNrcCreatoinResponse = "{call IOE.FX_UPDATE_NRC_RESPONSE(?,?)}";
	
	public void createServiceWith_RCs_or_NRCs() throws IOESException
	{
		
		ServiceDTO serDto=null;
		int fetchAccStatus=0;
		do
		{
			try
			{
				serDto=new ServiceDTO();
				//fetchAccStatus=fetchNextServiceData(serDto);
				if(fetchAccStatus==Fetch_Ser_Status_DataFound)
				{
					
					sendToFxServiceWith_RCs_or_NRCs(serDto);	
					int saveStatus=serDto.getReturnStatus();
					if(saveStatus==1)
					{
						//success
						setStatus(serDto,Service_Created_Success);
					}else //if(saveStatus==-1)
					{
						setStatus(serDto,Service_Created_Failure);	
					}
				}
			}
			catch(Exception ex)
			{
				Utility.onEx_LOG_RET_NEW_EX(ex, "createService", "CreateService", null, true, true);
			}
		}while(fetchAccStatus==Fetch_Ser_Status_DataFound);
		
	
	}

	private void setStatus(ServiceDTO serDto, int status) throws IOESException {
		Connection conn=null;
		try
		{
			try
			{
				conn=DbConnection.getConnectionObject();
				//for failure
				//1.status to 2
				
				//for success
				//1.update service internal id and reset 
				//2.for its rcs , update its trackingid and serv
				//3.for its nrcs,update their viewId
				
				
				
				//1.for success update service internal id and reset
				//for failure they will be null
				String sqlUpdate="Update IOE.TFX_ServiceCreate " +
						" set processingStatus="+status+" " +
						" , subscrNo='"+serDto.getSubscrNo() +"' " +
						" , lastUpdatedDate=CURRENT TIMESTAMP" +
						" , subscrNoReset='"+serDto.getSubscrNoReset()+"'" +
						" where ID="+serDto.getId();
				conn.createStatement().executeUpdate(sqlUpdate);
				
				if(status==Service_Created_Success)
				{
					//2.for its rcs , update its trackingid and serv
					ArrayList<RcDto> rcs=serDto.getRcs();
					if(rcs!=null && rcs.size()>0)
					{
						CallableStatement cstmt=conn.prepareCall(sqlUpdateRcCreatoinResponse);
						for (RcDto rc : rcs) {
							cstmt.setLong(1, rc.getRowId());
							cstmt.setInt(2, rc.getTrackingId());
							cstmt.setInt(3, rc.getTrackingIdServ());
							cstmt.execute();
						}
					}
					
					//3.for its nrcs,update their viewId
					ArrayList<NrcDto> nrcs=serDto.getNrcs();
					if(nrcs!=null && nrcs.size()>0)
					{
						CallableStatement cstmt=conn.prepareCall(sqlUpdateNrcCreatoinResponse);
						for (NrcDto nrc : nrcs) {
							cstmt.setLong(1, nrc.getRowId());
							cstmt.setString(2, nrc.getViewId());
							cstmt.execute();
						}
					}
				}
			}
			finally
			{
				DbConnection.freeConnection(conn);
			}
		}catch (Exception ex) {
			throw Utility.onEx_LOG_RET_NEW_EX(ex, "setStatus", "CreateService", null, true, true);
		}
		
	}

	public int fetchNextServiceData(ServiceDTO serDto,Connection conn) throws IOESException{
		int fetchAccStatus=0;
		
		
		try
		{
				String sql="SELECT  ID, acctExternalId, subscrNo, subscrNoReset, prename, fName, lName, address1, address2, address3, billcompany, billPeriod, billcountrycode, billstate, billcity, billzip, custfaxno, rateClassDefault, mobNo, stbId, casId, vcId, servExtId, processingStatus FROM IOE.TFX_ServiceCreate where processingStatus=0 order by ID asc fetch first row only";
				ResultSet rs=conn.createStatement().executeQuery(sql);
				if(rs.next())
				{
					fetchAccStatus=Fetch_Ser_Status_DataFound;
				//	serDto=new ServiceDTO();
					//serDto.setAcctInternalId((rs.getInt("acctInternalId")));
					serDto.setAcctExternalId(rs.getString("acctExternalId"));
					serDto.setServExtId(rs.getString("servExtId"));
					
					serDto.setPrename((rs.getString("prename")));
					serDto.setAddress1((rs.getString("address1")));
					serDto.setAddress2((rs.getString("address2")));
					serDto.setAddress3((rs.getString("address3")));
					serDto.setBillcity((rs.getString("billcity")));
					serDto.setBillcompany((rs.getString("billcompany")));
					
					String val=rs.getString("billcountrycode");
					serDto.setBillcountrycode(val==null?0:Integer.parseInt(val));
					
					serDto.setBillPeriod((rs.getString("billPeriod")));
					serDto.setBillstate((rs.getString("billstate")));
					serDto.setBillzip((rs.getString("billzip")));
					serDto.setCustfaxno((rs.getString("custfaxno")));
					serDto.setFName((rs.getString("fName")));
					serDto.setLName((rs.getString("lName")));
					serDto.setRateClassDefault((rs.getInt("rateClassDefault")));
					serDto.setMobNo((rs.getString("mobNo")));
					
				
					
					long id=rs.getLong("ID");
					serDto.setId(id);
					rs.close();
					
					//fetch RC charges info for the above fetched service :START
					String sql_Charges=sqlGetFX_RC_Charges;
					CallableStatement cstmt=conn.prepareCall(sql_Charges);
					cstmt.setLong(1, id);
					ResultSet rs_rc_charges=cstmt.executeQuery();
					ArrayList<RcDto> rcs=new ArrayList<RcDto>();
					
					RcDto rcDto=null;
					
					while(rs_rc_charges.next())
					{
						rcDto = new RcDto();
						
						rcDto.setRowId(rs_rc_charges.getLong("ID"));
						rcDto.setElementId(rs_rc_charges.getInt("ELEMENTID"));
						rcDto.setOpenItemId(rs_rc_charges.getInt("OPENITEMID"));
						rcDto.setProductActiveDate(rs_rc_charges.getDate("PRODUCTACTIVEDT"));
						rcDto.setBillingActiveDate(rs_rc_charges.getDate("BILLINGACTIVEDT"));
						rcDto.setInArrearsOverride(rs_rc_charges.getInt("INARREARSOVERRIDE"));
						rcDto.setBillPeriod(rs_rc_charges.getString("BILLPERIOD"));
						rcDto.setOrderNumber(rs_rc_charges.getString("ORDERNO"));
						rcDto.setOverrideRate(rs_rc_charges.getString("OVERRIDE_RATE"));
						
						rcs.add(rcDto);
					}
					
					rs_rc_charges.close();
					cstmt.close();
					serDto.setRcs(rcs);
					//fetch RC charges info  :END
					
					//fetch NRC charges info for the above fetched service  :START
					String sql_NRC_Charges=sqlGetFX_NRC_Charges;
					CallableStatement cstmt_NRC=conn.prepareCall(sql_NRC_Charges);
					cstmt_NRC.setLong(1, id);
					ResultSet rs_nrc_charges=cstmt_NRC.executeQuery();
					ArrayList<NrcDto> nrcs=new ArrayList<NrcDto>();
					
					NrcDto nrcDto=null;
					
					while(rs_nrc_charges.next())
					{
						nrcDto = new NrcDto();
						
						nrcDto.setRowId(rs_nrc_charges.getLong("ID"));
						nrcDto.setTypeidNrc(rs_nrc_charges.getInt("TYPEIDNRC"));
						nrcDto.setCurrencyCode(rs_nrc_charges.getInt("CURRENCYCODE"));
						nrcDto.setEffectiveDate(rs_nrc_charges.getDate("EFFECTIVE_DATE"));
						nrcDto.setAnnotation(rs_nrc_charges.getString("ANNOTATION"));
						nrcDto.setNrcAmount(rs_nrc_charges.getString("NRC_AMOUNT"));
						
						nrcs.add(nrcDto);
					}
					cstmt_NRC.close();
					serDto.setNrcs(nrcs);
					
					//fetch NRC charges info  :END
					
					String sqlUpdate="Update IOE.TFX_ServiceCreate set processingStatus=1 , lastUpdatedDate=CURRENT TIMESTAMP where ID="+id;
					conn.createStatement().executeUpdate(sqlUpdate);
					
				}
				else
				{
					fetchAccStatus=Fetch_Ser_Status_DataNotFound;	
				}
			
			
		}catch (Exception ex) {
			throw Utility.onEx_LOG_RET_NEW_EX(ex, "fetchNextServiceData", "CreateService", null, true, true);
		}
		
		
		return fetchAccStatus;
	}

	private int sendToFxServiceWith_RCs_or_NRCs(ServiceDTO serDto) throws IOESException{
		int saveStatus=0;
		
		try
		{
			IOESKenanManager API=new IOESKenanManager();
			API.loginKenan();
			try
			{
				//API.createService1(serDto);
				API.sendToFxServiceWith_RCs_or_NRCs(serDto);
			}
			finally
			{
				API.close();
			}
		}
		catch (Exception ex) {
			Utility.onEx_LOG_RET_NEW_EX(ex, "saveService", "CreateService", null, true, true);
		}
		
		return saveStatus;
	}
	
		
	public static void main(String[] args) {
		CreateServiceWith_RC_or_NRC service=new CreateServiceWith_RC_or_NRC();
		try {
			service.createServiceWith_RCs_or_NRCs();
		} catch (IOESException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
}

