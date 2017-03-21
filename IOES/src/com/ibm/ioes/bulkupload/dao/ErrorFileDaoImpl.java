//=================================================================================================================
//Tag Name		Resource Name		Date		CSR No			Description
//[001]			ANIL KUMAR	   28-JULY-2011		00-05422		to get Error excel file
//[002]			ANIL KUMAR	   28-JULY-2011		00-05422		to get Result excel file
//[003]			ANIL KUMAR	   28-JULY-2011		00-05422		to get data for write filled template excel file
//[004]			ANIL KUMAR	   28-JULY-2011		00-05422		to get number of rows for write filled template excel file
//[005]			SAURABH SINGH  28-JULY-2011		00-05422		to get party details for search parameter list
//[006]			SAURABH SINGH  28-JULY-2011		00-05422		to get account details for search parameter list
//[007]			SAURABH SINGH  28-JULY-2011		00-05422		to get logical lsi details for search parameter list
//=================================================================================================================
package com.ibm.ioes.bulkupload.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//import jxl.format.Format;

import org.apache.log4j.Logger;

import com.ibm.ioes.bulkupload.dto.ErrorLogDto;
import com.ibm.ioes.bulkupload.dto.TransactionTemplateDto;
import com.ibm.ioes.exception.IOESException;
import com.ibm.ioes.forms.NewOrderDto;
import com.ibm.ioes.forms.PagingDto;
import com.ibm.ioes.utilities.ApplicationFlags;
import com.ibm.ioes.utilities.DbConnection;

public class ErrorFileDaoImpl 
{
	/**
	 * @version 	1.0
	 * @author		 Anil Kumar & Saurabh Singh
	 */
	/**
	 * Logger for the class.
	**/
	private static final Logger logger;
	
	static {

		logger = Logger.getLogger(ErrorFileDaoImpl.class);
	}
	
	public static String sqlGetErrorLog = "{CALL BULKUPLOAD.SP_GET_BULKERRORLOG(?)}";
	public static String sqlGetResultLog = "{CALL BULKUPLOAD.SP_GET_BULKRESULTLOG(?)}";
	public static String sqlGetFilledTemplate = "{CALL IOE.SP_GET_BULKFILLEDTEMPLATE_DATA(?,?,?,?,?)}";
	public static String sqlGetRowsOfSheet = "{CALL IOE.SP_GET_BULKTOTALROWSOFSHEET(?,?,?,?,?)}";
	public static String sqlGetFilledTemplateForAccount = "{CALL IOE.SP_GET_BULKFILLEDTEMPLATE_DATA_ACCOUNT(?,?,?)}";
	public static String sqlGetRowsOfSheetForAccount = "{CALL IOE.SP_GET_BULKTOTALROWSOFSHEET_ACCOUNT(?,?,?)}";
	//lawkush
	public static String sqlGetPartyId = "{CALL IOE.SP_GET_PARTYID(?)}";
	public static String sqlGetAccountId = "{CALL IOE.SPBULK_GET_ACCOUNTID(?,?,?,?,?,?,?)}";
	public static String sqlGetLSI = "{CALL IOE.SPBULK_GET_LSI(?,?,?,?,?,?,?,?)}";
	
	//START[001]
	public ArrayList getErrorLog(int fileId) throws IOESException
	{
		//logger.info(" Entered into getErrorLog method of " + this.getClass().getSimpleName());
		
		ArrayList<ErrorLogDto> errorList = new ArrayList<ErrorLogDto>();
		CallableStatement csGetErrorLog = null;
		ResultSet rsGetErrorLog = null;
		Connection con = null;
		ErrorLogDto dtoObj ;
		
		try
		{
			DbConnection connectionClassObj = new DbConnection();	
			con = connectionClassObj.getConnectionObject();
			
			csGetErrorLog = con.prepareCall(sqlGetErrorLog);
			csGetErrorLog.setInt(1, fileId);
			rsGetErrorLog = csGetErrorLog.executeQuery();

			while(rsGetErrorLog.next())
			{
				dtoObj = new ErrorLogDto();
				dtoObj.setErrorSheet(rsGetErrorLog.getString("SHEEETNO"));
				dtoObj.setErrorLogValue(rsGetErrorLog.getString("ERRORLOG"));
				errorList.add(dtoObj);
			}
		}
		catch(SQLException sqlEx)
		{
			logger.error(sqlEx.getMessage() + " SQLException occured in getErrorLog method of " + this.getClass().getSimpleName());
			throw new IOESException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
		}
		catch(Exception ex)
		{
			logger.error(ex.getMessage() + " Exception occured in getErrorLog method of " + this.getClass().getSimpleName());
			throw new IOESException("Exception : "+ ex.getMessage(), ex);
		}
		finally
		{
			try
			{
				DbConnection.closeResultset(rsGetErrorLog);
				DbConnection.closeCallableStatement(csGetErrorLog);
				DbConnection.freeConnection(con);
			}
			catch(SQLException sqlEx)
			{
				logger.error(sqlEx.getMessage() + " SQLException occured while closing database objects in getErrorLog method of " + this.getClass().getSimpleName());
				throw new IOESException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
			}
			catch(Exception ex)
			{
				logger.error(ex.getMessage() + " Exception occured while closing database objects in getErrorLog method of " + this.getClass().getSimpleName());
				throw new IOESException("Exception : "+ ex.getMessage(), ex);
			}
		}
		return errorList;
	}
	//END[001]
	//START[002]
	public ArrayList getResultLog(int fileId) throws IOESException
	{
		//logger.info(" Entered into getErrorLog method of " + this.getClass().getSimpleName());
		
		ArrayList<ErrorLogDto> resultList = new ArrayList<ErrorLogDto>();
		CallableStatement csGetErrorLog = null;
		ResultSet rsGetErrorLog = null;
		Connection con = null;
		ErrorLogDto dtoObj ;
		
		try
		{
			DbConnection connectionClassObj = new DbConnection();	
			con = connectionClassObj.getConnectionObject();
			
			csGetErrorLog = con.prepareCall(sqlGetResultLog);
			csGetErrorLog.setInt(1, fileId);
			rsGetErrorLog = csGetErrorLog.executeQuery();

			while(rsGetErrorLog.next())
			{
				dtoObj = new ErrorLogDto();
				dtoObj.setResultSheet(rsGetErrorLog.getString("SHEEETNO"));
				if(("null"==rsGetErrorLog.getString("RESULTLOG"))||(null==rsGetErrorLog.getString("RESULTLOG")))
				{
					dtoObj.setResultLogValue("OrderNo Not found");
				}
				else
					{
				dtoObj.setResultLogValue(rsGetErrorLog.getString("RESULTLOG"));
					}
				if(("null"==rsGetErrorLog.getString("ORDERNO"))||(null==rsGetErrorLog.getString("ORDERNO")))
				{
					dtoObj.setResultLogValue("0");
				}else
				{
				dtoObj.setOrderNo(rsGetErrorLog.getString("ORDERNO"));
				}
				resultList.add(dtoObj);
					
			}
		}
		catch(SQLException sqlEx)
		{
			logger.error(sqlEx.getMessage() + " SQLException occured in getErrorLog method of " + this.getClass().getSimpleName());
			throw new IOESException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
		}
		catch(Exception ex)
		{
			logger.error(ex.getMessage() + " Exception occured in getErrorLog method of " + this.getClass().getSimpleName());
			throw new IOESException("Exception : "+ ex.getMessage(), ex);
		}
		finally
		{
			try
			{
				DbConnection.closeResultset(rsGetErrorLog);
				DbConnection.closeCallableStatement(csGetErrorLog);
				DbConnection.freeConnection(con);
			}
			catch(SQLException sqlEx)
			{
				logger.error(sqlEx.getMessage() + " SQLException occured while closing database objects in getErrorLog method of " + this.getClass().getSimpleName());
				throw new IOESException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
			}
			catch(Exception ex)
			{
				logger.error(ex.getMessage() + " Exception occured while closing database objects in getErrorLog method of " + this.getClass().getSimpleName());
				throw new IOESException("Exception : "+ ex.getMessage(), ex);
			}
		}
		return resultList;
	}
	//END[002]
	/*function:getFilledTemplate
	 * return:ActionForward
	 * createdby:Anil Kumar
	 * Purpose:to download filled template based on parameter.
	 * */
	//START[003]
	public ArrayList getFilledTemplate(int sheet,int templateId , int flag , String logicalLSI) throws IOESException
	{
		//logger.info(" Entered into getErrorLog method of " + this.getClass().getSimpleName());
		
		ArrayList<TransactionTemplateDto> templateData = new ArrayList<TransactionTemplateDto>();;
		CallableStatement csGetErrorLog = null;
		ResultSet rsGetErrorLog = null;
		Connection con = null;
		Statement stmt=null;
		TransactionTemplateDto dtoObj ;
		String deleteSplitedList="DELETE FROM IOE.TM_SPLITEDLIST";
		
		try
		{
			DbConnection connectionClassObj = new DbConnection();	
			con = connectionClassObj.getConnectionObject();
			stmt=con.createStatement();	
			boolean LogicalSIFlag=false,accountIDFlag=false,partyIDFlag=false;
									
			String accountID="101,102";
			
			if(flag==1)
			{
				LogicalSIFlag=true;
				accountIDFlag=false;
				partyIDFlag=false;
			}
			else if(flag==0)
			{
				LogicalSIFlag=false;
				accountIDFlag=true;
				partyIDFlag=false;
			}
				if(templateId==1||templateId==22||templateId==21||templateId==41)
				{	
					if(sheet==1)
					{
						if(LogicalSIFlag){
							csGetErrorLog = con.prepareCall(sqlGetFilledTemplate);
							csGetErrorLog.setInt(1, templateId);
							csGetErrorLog.setString(2, logicalLSI);
							csGetErrorLog.setString(3, "");
							csGetErrorLog.setString(4, "");
							csGetErrorLog.setLong(5, sheet);
						}else if(accountIDFlag){
							csGetErrorLog = con.prepareCall(sqlGetFilledTemplateForAccount);
							csGetErrorLog.setInt(1, templateId);
							csGetErrorLog.setString(2, accountID);							
							csGetErrorLog.setLong(3, sheet);
						}
						
						rsGetErrorLog = csGetErrorLog.executeQuery();
						while(rsGetErrorLog.next())
						{
							dtoObj = new TransactionTemplateDto();
							dtoObj.setOrderNo(rsGetErrorLog.getLong("ORDERNO"));
							dtoObj.setAccountID(rsGetErrorLog.getString("ACCOUNTID"));
							dtoObj.setSource(rsGetErrorLog.getString("SOURCEID"));
							dtoObj.setCurrencyID(rsGetErrorLog.getString("CURRENCYID"));
							dtoObj.setOpportunityId(rsGetErrorLog.getString("OPPORTUNITYID"));
							dtoObj.setQuoteNo(rsGetErrorLog.getString("QUOTENO"));
							dtoObj.setProjectMangerID(rsGetErrorLog.getString("PROJECTMANAGET_ID"));
							dtoObj.setZoneId(rsGetErrorLog.getString("ZONEID"));
							templateData.add(dtoObj);											
						}
						stmt.executeUpdate(deleteSplitedList);
						return templateData;
					}
					if(sheet==11)
					{
						int rowCount=0;	
						if(LogicalSIFlag){
							csGetErrorLog = con.prepareCall(sqlGetFilledTemplate);
							csGetErrorLog.setInt(1, templateId);
							csGetErrorLog.setString(2, logicalLSI);
							csGetErrorLog.setString(3, "");
							csGetErrorLog.setString(4, "");
							csGetErrorLog.setLong(5, sheet);
						}else if(accountIDFlag){
							csGetErrorLog = con.prepareCall(sqlGetFilledTemplateForAccount);
							csGetErrorLog.setInt(1, templateId);
							csGetErrorLog.setString(2, accountID);							
							csGetErrorLog.setLong(3, sheet);
						}						
						rsGetErrorLog = csGetErrorLog.executeQuery();
						dtoObj = new TransactionTemplateDto();						
						while(rsGetErrorLog.next())
						{	
							dtoObj.setOrderNo(rsGetErrorLog.getLong("ORDERNO"));
							String rfsDate_ID=null;
							String actMngrPhoneNo_ID=null;
							String actMngrEmailID_ID=null;
							String iRUOrderYN_ID=null;
							String freePeriodYN_ID=null;
							String ordExclusiveTax_ID=null;
							String cAFDate_ID=null;
							
							if(ApplicationFlags.IB2B_ENVIRONMENT.equals("DEV"))
							{
								rfsDate_ID="1";
								actMngrPhoneNo_ID="2";
								actMngrEmailID_ID="3";
								iRUOrderYN_ID="4";
								freePeriodYN_ID="5";
								ordExclusiveTax_ID="6";
								cAFDate_ID="7";
							
							}
							else if(ApplicationFlags.IB2B_ENVIRONMENT.equals("PROD"))
							{
								rfsDate_ID="21";
								actMngrPhoneNo_ID="22";
								actMngrEmailID_ID="23";
								iRUOrderYN_ID="24";
								freePeriodYN_ID="25";
								ordExclusiveTax_ID="26";
								cAFDate_ID="27";
							}
							if(rfsDate_ID.equalsIgnoreCase(rsGetErrorLog.getString("ATTRIBUTEID"))){	
								
									if(rsGetErrorLog.getString("ATTVALUE")!=null ||rsGetErrorLog.getString("ATTVALUE")!=""){
										SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
										java.util.Date tDate = new java.util.Date();	
										tDate = df.parse(rsGetErrorLog.getString("ATTVALUE"));
										df = new SimpleDateFormat("dd-MMM-yyyy");
										String date1 = df.format(tDate);
										dtoObj.setRFSDate(date1);
									}else{
										dtoObj.setRFSDate("");	
									}																
							}
							else if(actMngrPhoneNo_ID.equalsIgnoreCase(rsGetErrorLog.getString("ATTRIBUTEID"))){
								dtoObj.setActMngrPhoneNo(rsGetErrorLog.getString("ATTVALUE"));
							}
							else if(actMngrEmailID_ID.equalsIgnoreCase(rsGetErrorLog.getString("ATTRIBUTEID"))){
								dtoObj.setActMngrEmailID(rsGetErrorLog.getString("ATTVALUE"));
							}
							else if(iRUOrderYN_ID.equalsIgnoreCase(rsGetErrorLog.getString("ATTRIBUTEID"))){
								dtoObj.setIRUOrderYN(rsGetErrorLog.getString("ATTVALUE"));
							}
							else if(freePeriodYN_ID.equalsIgnoreCase(rsGetErrorLog.getString("ATTRIBUTEID"))){
								dtoObj.setFreePeriodYN(rsGetErrorLog.getString("ATTVALUE"));
							}
							else if(ordExclusiveTax_ID.equalsIgnoreCase(rsGetErrorLog.getString("ATTRIBUTEID"))){
								dtoObj.setOrdExclusiveTax(rsGetErrorLog.getString("ATTVALUE"));
							}
							else if(cAFDate_ID.equalsIgnoreCase(rsGetErrorLog.getString("ATTRIBUTEID"))){								
								if(rsGetErrorLog.getString("ATTVALUE")!=null ||rsGetErrorLog.getString("ATTVALUE")!=""){
									SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
									java.util.Date tDate1 = new java.util.Date();
									tDate1 = df1.parse(rsGetErrorLog.getString("ATTVALUE").trim());
									df1 = new SimpleDateFormat("dd-MMM-yyyy");
									String date11 = df1.format(tDate1);
									dtoObj.setCAFDate(date11);
								}else{
									dtoObj.setCAFDate("");
								}													
							}		
							rowCount=rowCount+1;
							if(rowCount==7){
								templateData.add(dtoObj);
								dtoObj = new TransactionTemplateDto();
								rowCount=0;
							}							
						}
						stmt.executeUpdate(deleteSplitedList);
						return templateData;
					}
					if(sheet==2){
						//to do nothing
					}
					if(sheet==3)
					{	
						if(LogicalSIFlag){
							csGetErrorLog = con.prepareCall(sqlGetFilledTemplate);
							csGetErrorLog.setInt(1, templateId);
							csGetErrorLog.setString(2, logicalLSI);
							csGetErrorLog.setString(3, "");
							csGetErrorLog.setString(4, "");
							csGetErrorLog.setLong(5, sheet);
						}else if(accountIDFlag){
							csGetErrorLog = con.prepareCall(sqlGetFilledTemplateForAccount);
							csGetErrorLog.setInt(1, templateId);
							csGetErrorLog.setString(2, accountID);							
							csGetErrorLog.setLong(3, sheet);
						}						
						rsGetErrorLog = csGetErrorLog.executeQuery();

						while(rsGetErrorLog.next())
						{	
							dtoObj = new TransactionTemplateDto();
							dtoObj.setOrderNo(rsGetErrorLog.getLong("ORDERNO"));
							dtoObj.setContactType(rsGetErrorLog.getInt("CONTACTTYPE"));
							dtoObj.setSalutation(rsGetErrorLog.getString("ID"));
							dtoObj.setFirstName(rsGetErrorLog.getString("FIRSTNAME"));
							dtoObj.setLastName(rsGetErrorLog.getString("LASTNAME"));
							dtoObj.setEmail(rsGetErrorLog.getString("EMAIL"));
							dtoObj.setCellno(rsGetErrorLog.getString("CELLNO"));
							dtoObj.setFaxno(rsGetErrorLog.getString("FAXNO"));
							dtoObj.setAddress1(rsGetErrorLog.getString("ADDRESS1"));
							dtoObj.setAddress2(rsGetErrorLog.getString("ADDRESS2"));
							dtoObj.setAddress3(rsGetErrorLog.getString("ADDRESS3"));
							dtoObj.setCountrycode(rsGetErrorLog.getString("COUNTRYCODE"));
							dtoObj.setStateid(rsGetErrorLog.getString("STATEID"));
							dtoObj.setCityid(rsGetErrorLog.getString("CITYID"));
							dtoObj.setPincode(rsGetErrorLog.getString("PINCODE"));							
							templateData.add(dtoObj);							
						}
						stmt.executeUpdate(deleteSplitedList);
						return templateData;
					}
					if(templateId==21){
						if(sheet==4){
							
							if(LogicalSIFlag){
								csGetErrorLog = con.prepareCall(sqlGetFilledTemplate);
								csGetErrorLog.setInt(1, templateId);
								csGetErrorLog.setString(2, logicalLSI);
								csGetErrorLog.setString(3, "");
								csGetErrorLog.setString(4, "");
								csGetErrorLog.setLong(5, sheet);
							}else if(accountIDFlag){
								csGetErrorLog = con.prepareCall(sqlGetFilledTemplateForAccount);
								csGetErrorLog.setInt(1, templateId);
								csGetErrorLog.setString(2, accountID);							
								csGetErrorLog.setLong(3, sheet);
							}							
							rsGetErrorLog = csGetErrorLog.executeQuery();

							while(rsGetErrorLog.next())
							{							
								dtoObj = new TransactionTemplateDto();								
								dtoObj.setOrderNo(rsGetErrorLog.getLong("ORDERNO"));
								dtoObj.setServiceid(rsGetErrorLog.getLong("SERVICEID"));
								dtoObj.setServiceName(rsGetErrorLog.getString("SERVICESTAGE"));
								dtoObj.setLogicalsiNo(rsGetErrorLog.getLong("LOGICAL_SI_NO"));
								dtoObj.setLineItemID(rsGetErrorLog.getLong("SERVICEPRODUCTID"));
								dtoObj.setLineItemName(rsGetErrorLog.getString("LINEITEMNAME"));
								templateData.add(dtoObj);							
							}
							stmt.executeUpdate(deleteSplitedList);
							return templateData;
						}
					}
					else if(templateId==1||templateId==22)
					{
						if(sheet==4){
								
							if(LogicalSIFlag){
								csGetErrorLog = con.prepareCall(sqlGetFilledTemplate);
								csGetErrorLog.setInt(1, templateId);
								csGetErrorLog.setString(2, logicalLSI);
								csGetErrorLog.setString(3, "");
								csGetErrorLog.setString(4, "");
								csGetErrorLog.setLong(5, sheet);
							}else if(accountIDFlag){
								csGetErrorLog = con.prepareCall(sqlGetFilledTemplateForAccount);
								csGetErrorLog.setInt(1, templateId);
								csGetErrorLog.setString(2, accountID);							
								csGetErrorLog.setLong(3, sheet);
							}							
							rsGetErrorLog = csGetErrorLog.executeQuery();

						while(rsGetErrorLog.next())
						{							
							dtoObj = new TransactionTemplateDto();							
							dtoObj.setOrderNo(rsGetErrorLog.getLong("ORDERNO"));
							dtoObj.setServiceid(rsGetErrorLog.getLong("SERVICEID"));
							dtoObj.setLogicalsiNo(rsGetErrorLog.getLong("LOGICAL_SI_NO"));
							dtoObj.setLineItemID(rsGetErrorLog.getLong("SERVICEPRODUCTID"));							
							templateData.add(dtoObj);							
						}	
						stmt.executeUpdate(deleteSplitedList);
						return templateData;
					}	
				  }
				else if(templateId==41 && sheet==4)
				{
					if(LogicalSIFlag){
						csGetErrorLog = con.prepareCall(sqlGetFilledTemplate);
						csGetErrorLog.setInt(1, templateId);
						csGetErrorLog.setString(2, logicalLSI);
						csGetErrorLog.setString(3, "");
						csGetErrorLog.setString(4, "");
						csGetErrorLog.setLong(5, sheet);
					}else if(accountIDFlag){
						csGetErrorLog = con.prepareCall(sqlGetFilledTemplateForAccount);
						csGetErrorLog.setInt(1, templateId);
						csGetErrorLog.setString(2, accountID);							
						csGetErrorLog.setLong(3, sheet);
					}
					rsGetErrorLog = csGetErrorLog.executeQuery();					 				
					while(rsGetErrorLog.next())
					{							
						dtoObj = new TransactionTemplateDto();							
						dtoObj.setOrderNo(rsGetErrorLog.getLong("PONUMBER"));
						dtoObj.setCustPONumber(rsGetErrorLog.getString("CUSTPONUMBER"));						
						dtoObj.setCustPODate(rsGetErrorLog.getString("CUSTPODATE"));													
						dtoObj.setIsDefaultPO(rsGetErrorLog.getString("ISDEFAULTPO"));
						dtoObj.setLegalEntity(rsGetErrorLog.getString("LEGALENTITY"));
						dtoObj.setTotalPOAmount(rsGetErrorLog.getString("POAMOUNT"));
						dtoObj.setPeriodInMonths(rsGetErrorLog.getString("CONTRACTPERIOD"));						
						dtoObj.setContractStartDate(rsGetErrorLog.getString("CONTRACTSTARTDATE"));																			
						dtoObj.setContractEndDate(rsGetErrorLog.getString("CONTRACTENDDATE"));																							
						dtoObj.setPoRemarks(rsGetErrorLog.getString("POREMARKS"));
						dtoObj.setPoEmailId(rsGetErrorLog.getString("EMAILID"));
						templateData.add(dtoObj);							
					}	
					stmt.executeUpdate(deleteSplitedList);
					return templateData;
				}
				else if(templateId==41 && sheet==5)
				{
					if(LogicalSIFlag){
						csGetErrorLog = con.prepareCall(sqlGetFilledTemplate);
						csGetErrorLog.setInt(1, templateId);
						csGetErrorLog.setString(2, logicalLSI);
						csGetErrorLog.setString(3, "");
						csGetErrorLog.setString(4, "");
						csGetErrorLog.setLong(5, sheet);
					}else if(accountIDFlag){
						csGetErrorLog = con.prepareCall(sqlGetFilledTemplateForAccount);
						csGetErrorLog.setInt(1, templateId);
						csGetErrorLog.setString(2, accountID);							
						csGetErrorLog.setLong(3, sheet);
					}					
					rsGetErrorLog = csGetErrorLog.executeQuery();

					while(rsGetErrorLog.next())
					{							
						dtoObj = new TransactionTemplateDto();					
						dtoObj.setOrderNo(rsGetErrorLog.getLong("ORDERNO"));
						dtoObj.setServiceid(rsGetErrorLog.getLong("SERVICEID"));
						dtoObj.setLogicalsiNo(rsGetErrorLog.getLong("LOGICAL_SI_NO"));
						dtoObj.setLineItemID(rsGetErrorLog.getLong("SERVICEPRODUCTID"));
						dtoObj.setChargeID(rsGetErrorLog.getLong("CHARGEINFOID"));
						dtoObj.setChargeAmount(rsGetErrorLog.getLong("CHARGEAMOUNT"));
						dtoObj.setChargeFrequency(rsGetErrorLog.getString("CHARGEFREQUENCY"));
						dtoObj.setStrChargeType(rsGetErrorLog.getString("CHARGE_TYPE"));
						dtoObj.setStrChargeName(rsGetErrorLog.getString("CHARGE_NAME"));
						dtoObj.setFrequncyAmount(rsGetErrorLog.getLong("CHARGEFREQUENCYAMT"));
						dtoObj.setAnnotation(rsGetErrorLog.getString("ANNOTATION"));
						templateData.add(dtoObj);							
					}	
					stmt.executeUpdate(deleteSplitedList);
					return templateData;
				}
				else if(templateId==41 && sheet==6)
				{
					if(LogicalSIFlag){
						csGetErrorLog = con.prepareCall(sqlGetFilledTemplate);
						csGetErrorLog.setInt(1, templateId);
						csGetErrorLog.setString(2, logicalLSI);
						csGetErrorLog.setString(3, "");
						csGetErrorLog.setString(4, "");
						csGetErrorLog.setLong(5, sheet);
					}else if(accountIDFlag){
						csGetErrorLog = con.prepareCall(sqlGetFilledTemplateForAccount);
						csGetErrorLog.setInt(1, templateId);
						csGetErrorLog.setString(2, accountID);							
						csGetErrorLog.setLong(3, sheet);
					}					
					rsGetErrorLog = csGetErrorLog.executeQuery();

					while(rsGetErrorLog.next())
					{							
						dtoObj = new TransactionTemplateDto();					
						dtoObj.setLineItemID(rsGetErrorLog.getLong("SERVICEPRODUCTID"));
						dtoObj.setCreditPeriodID(rsGetErrorLog.getLong("CREDITPERIOD"));
						dtoObj.setCreditPeriodName(rsGetErrorLog.getString("CREDIT_PERIODNAME"));
						dtoObj.setLegealEntityID(rsGetErrorLog.getLong("ENTITYID"));
						dtoObj.setLegalEntityName(rsGetErrorLog.getString("ENTITYNAME"));
						dtoObj.setLicenseCompanyID(rsGetErrorLog.getLong("LICENCECOID"));
						dtoObj.setLicenseCompanyName(rsGetErrorLog.getString("LCOMPANYNAME"));
						dtoObj.setBillingModeID(rsGetErrorLog.getLong("BILLINGMODE"));
						dtoObj.setBillingModeName(rsGetErrorLog.getString("BILLINGMODENAME"));
						dtoObj.setBillingFormatID(rsGetErrorLog.getLong("BILLINGFORMAT"));
						dtoObj.setBillingFormatName(rsGetErrorLog.getString("BILLING_FORMATNAME"));
						dtoObj.setBillingTypeID(rsGetErrorLog.getLong("BILLINGTYPE"));
						dtoObj.setBillingTypeName(rsGetErrorLog.getString("BILLING_TYPENAME"));
						dtoObj.setTaxationID(rsGetErrorLog.getLong("TAXATION"));
						dtoObj.setTaxationName(rsGetErrorLog.getString("TAXATIONNAME"));
						dtoObj.setBillingLevelID(rsGetErrorLog.getLong("BILLINGLEVEL"));
						dtoObj.setBillingLevelName(rsGetErrorLog.getString("BILLING_LEVELNAME"));
						dtoObj.setNoticePeriod(rsGetErrorLog.getInt("NOTICEPERIOD"));
						dtoObj.setPenaltyClause(rsGetErrorLog.getString("PENALTYCLAUSE"));
						dtoObj.setCommitPeriod(rsGetErrorLog.getInt("COMMITMENTPERIOD"));
						dtoObj.setIsNfa(rsGetErrorLog.getInt("IS_NFA"));
						dtoObj.setBcpID(rsGetErrorLog.getLong("BCPID"));
						dtoObj.setBcpName(rsGetErrorLog.getString("BCPNAME"));
						dtoObj.setStandardReasonId(rsGetErrorLog.getString("STDREASION_ID"));
						dtoObj.setStandardReasonName(rsGetErrorLog.getString("REASONNAME"));						
						templateData.add(dtoObj);							
					}	
					stmt.executeUpdate(deleteSplitedList);
					return templateData;
				}
			}			
		}
		catch(SQLException sqlEx)
		{
			logger.error(sqlEx.getMessage() + " SQLException occured in getErrorLog method of " + this.getClass().getSimpleName());
			throw new IOESException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
		}
		catch(Exception ex)
		{
			logger.error(ex.getMessage() + " Exception occured in getErrorLog method of " + this.getClass().getSimpleName());
			throw new IOESException("Exception : "+ ex.getMessage(), ex);
		}
		finally
		{
			try
			{
				stmt.executeUpdate(deleteSplitedList);
				DbConnection.closeResultset(rsGetErrorLog);
				DbConnection.closeStatement(stmt);
				DbConnection.closeCallableStatement(csGetErrorLog);
				DbConnection.freeConnection(con);
			}
			catch(SQLException sqlEx)
			{
				logger.error(sqlEx.getMessage() + " SQLException occured while closing database objects in getErrorLog method of " + this.getClass().getSimpleName());
				throw new IOESException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
			}
			catch(Exception ex)
			{
				logger.error(ex.getMessage() + " Exception occured while closing database objects in getErrorLog method of " + this.getClass().getSimpleName());
				throw new IOESException("Exception : "+ ex.getMessage(), ex);
			}
		}
		return templateData;
	}
	//END[003]
	/*function:getTotalRowsOfSheet
	 * return:int
	 * createdby:Anil Kumar
	 * Purpose:get number of rows for write to excel sheeet.
	 * */
	//START[004]
	public int getTotalRowsOfSheet(int sheet,int templateId , int flag , String logicalLSI) throws IOESException
	{
		//logger.info(" Entered into getErrorLog method of " + this.getClass().getSimpleName());			
		CallableStatement csGetErrorLog = null;
		ResultSet rsGetErrorLog = null;
		Connection con = null;	
		Statement stmt=null;
		int totalRows=0;
		String deleteSplitedList="DELETE FROM IOE.TM_SPLITEDLIST";
		try
		{
			DbConnection connectionClassObj = new DbConnection();	
			con = connectionClassObj.getConnectionObject();
			stmt=con.createStatement();		
			boolean LogicalSIFlag=false,accountIDFlag=true,partyIDFlag=false;
									
			String accountID="101,102";
			
			if(flag==1)
			{
				LogicalSIFlag=true;
				accountIDFlag=false;
				partyIDFlag=false;
			}
			else if(flag==0)
			{
				LogicalSIFlag=false;
				accountIDFlag=true;
				partyIDFlag=false;
			}
			
			if(templateId==1||templateId==22||templateId==21||templateId==41){	
				if(sheet==1){	
					if(LogicalSIFlag){
						csGetErrorLog = con.prepareCall(sqlGetRowsOfSheet);
						csGetErrorLog.setInt(1, templateId);
						csGetErrorLog.setString(2, logicalLSI);
						csGetErrorLog.setString(3, "");
						csGetErrorLog.setString(4, "");
						csGetErrorLog.setLong(5, sheet);
					}else if(accountIDFlag){
						csGetErrorLog = con.prepareCall(sqlGetRowsOfSheetForAccount);
						csGetErrorLog.setInt(1, templateId);
						csGetErrorLog.setString(2, accountID);						
						csGetErrorLog.setLong(3, sheet);
					}
					
					rsGetErrorLog = csGetErrorLog.executeQuery();
					while(rsGetErrorLog.next())
					{
						totalRows=rsGetErrorLog.getInt("totalRows");
					}
					stmt.executeUpdate(deleteSplitedList);
					return totalRows;
				}
					if(sheet==11){	
						if(LogicalSIFlag){
							csGetErrorLog = con.prepareCall(sqlGetRowsOfSheet);
							csGetErrorLog.setInt(1, templateId);
							csGetErrorLog.setString(2, logicalLSI);
							csGetErrorLog.setString(3, "");
							csGetErrorLog.setString(4, "");
							csGetErrorLog.setLong(5, sheet);
						}else if(accountIDFlag){
							csGetErrorLog = con.prepareCall(sqlGetRowsOfSheetForAccount);
							csGetErrorLog.setInt(1, templateId);
							csGetErrorLog.setString(2, accountID);						
							csGetErrorLog.setLong(3, sheet);
						}						
						rsGetErrorLog = csGetErrorLog.executeQuery();
						while(rsGetErrorLog.next())
						{
							totalRows=rsGetErrorLog.getInt("totalRows");
						}
						stmt.executeUpdate(deleteSplitedList);
						return totalRows;
					}
					if(sheet==2){	
						//to do nothing
					}
					if(sheet==3){	
						if(LogicalSIFlag){
							csGetErrorLog = con.prepareCall(sqlGetRowsOfSheet);
							csGetErrorLog.setInt(1, templateId);
							csGetErrorLog.setString(2, logicalLSI);
							csGetErrorLog.setString(3, "");
							csGetErrorLog.setString(4, "");
							csGetErrorLog.setLong(5, sheet);
						}else if(accountIDFlag){
							csGetErrorLog = con.prepareCall(sqlGetRowsOfSheetForAccount);
							csGetErrorLog.setInt(1, templateId);
							csGetErrorLog.setString(2, accountID);						
							csGetErrorLog.setLong(3, sheet);
						}						
						rsGetErrorLog = csGetErrorLog.executeQuery();
						while(rsGetErrorLog.next())
						{
							totalRows=rsGetErrorLog.getInt("totalRows");
						}
						stmt.executeUpdate(deleteSplitedList);
						return totalRows;
					}
			}
			if(templateId==1){
					if(sheet==4)
					{	
						if(LogicalSIFlag){
							csGetErrorLog = con.prepareCall(sqlGetRowsOfSheet);
							csGetErrorLog.setInt(1, templateId);
							csGetErrorLog.setString(2, logicalLSI);
							csGetErrorLog.setString(3, "");
							csGetErrorLog.setString(4, "");
							csGetErrorLog.setLong(5, sheet);
						}else if(accountIDFlag){
							csGetErrorLog = con.prepareCall(sqlGetRowsOfSheetForAccount);
							csGetErrorLog.setInt(1, templateId);
							csGetErrorLog.setString(2, accountID);						
							csGetErrorLog.setLong(3, sheet);
						}						
						rsGetErrorLog = csGetErrorLog.executeQuery();
						while(rsGetErrorLog.next())
						{
							totalRows=rsGetErrorLog.getInt("totalRows");
						}
						stmt.executeUpdate(deleteSplitedList);
						return totalRows;
					}								
				}
			if(templateId==22){
				if(sheet==4)
				{
					if(LogicalSIFlag){
						csGetErrorLog = con.prepareCall(sqlGetRowsOfSheet);
						csGetErrorLog.setInt(1, templateId);
						csGetErrorLog.setString(2, logicalLSI);
						csGetErrorLog.setString(3, "");
						csGetErrorLog.setString(4, "");
						csGetErrorLog.setLong(5, sheet);
					}else if(accountIDFlag){
						csGetErrorLog = con.prepareCall(sqlGetRowsOfSheetForAccount);
						csGetErrorLog.setInt(1, templateId);
						csGetErrorLog.setString(2, accountID);						
						csGetErrorLog.setLong(3, sheet);
					}					
					rsGetErrorLog = csGetErrorLog.executeQuery();
					while(rsGetErrorLog.next())
					{
						totalRows=rsGetErrorLog.getInt("totalRows");
					}
					stmt.executeUpdate(deleteSplitedList);
					return totalRows;
				}								
			}
			if(templateId==21){
				if(sheet==4)
				{	
					if(LogicalSIFlag){
						csGetErrorLog = con.prepareCall(sqlGetRowsOfSheet);
						csGetErrorLog.setInt(1, templateId);
						csGetErrorLog.setString(2, logicalLSI);
						csGetErrorLog.setString(3, "");
						csGetErrorLog.setString(4, "");
						csGetErrorLog.setLong(5, sheet);
					}else if(accountIDFlag){
						csGetErrorLog = con.prepareCall(sqlGetRowsOfSheetForAccount);
						csGetErrorLog.setInt(1, templateId);
						csGetErrorLog.setString(2, accountID);						
						csGetErrorLog.setLong(3, sheet);
					}					
					rsGetErrorLog = csGetErrorLog.executeQuery();
					while(rsGetErrorLog.next())
					{
						totalRows=rsGetErrorLog.getInt("totalRows");
					}
					stmt.executeUpdate(deleteSplitedList);
					return totalRows;
				}								
			}
			if(templateId==41){
				if(sheet==4)
				{	
					if(LogicalSIFlag){
						csGetErrorLog = con.prepareCall(sqlGetRowsOfSheet);
						csGetErrorLog.setInt(1, templateId);
						csGetErrorLog.setString(2, logicalLSI);
						csGetErrorLog.setString(3, "");
						csGetErrorLog.setString(4, "");
						csGetErrorLog.setLong(5, sheet);
					}else if(accountIDFlag){
						csGetErrorLog = con.prepareCall(sqlGetRowsOfSheetForAccount);
						csGetErrorLog.setInt(1, templateId);
						csGetErrorLog.setString(2, accountID);						
						csGetErrorLog.setLong(3, sheet);
					}					
					rsGetErrorLog = csGetErrorLog.executeQuery();
					while(rsGetErrorLog.next())
					{
						totalRows=rsGetErrorLog.getInt("totalRows");
					}
					stmt.executeUpdate(deleteSplitedList);
					return totalRows;
				}
				if(sheet==5)
				{	
					if(LogicalSIFlag){
						csGetErrorLog = con.prepareCall(sqlGetRowsOfSheet);
						csGetErrorLog.setInt(1, templateId);
						csGetErrorLog.setString(2, logicalLSI);
						csGetErrorLog.setString(3, "");
						csGetErrorLog.setString(4, "");
						csGetErrorLog.setLong(5, sheet);
					}else if(accountIDFlag){
						csGetErrorLog = con.prepareCall(sqlGetRowsOfSheetForAccount);
						csGetErrorLog.setInt(1, templateId);
						csGetErrorLog.setString(2, accountID);						
						csGetErrorLog.setLong(3, sheet);
					}					
					rsGetErrorLog = csGetErrorLog.executeQuery();
					while(rsGetErrorLog.next())
					{
						totalRows=rsGetErrorLog.getInt("totalRows");
					}
					stmt.executeUpdate(deleteSplitedList);
					return totalRows;
				}
				if(sheet==6)
				{	
					if(LogicalSIFlag){
						csGetErrorLog = con.prepareCall(sqlGetRowsOfSheet);
						csGetErrorLog.setInt(1, templateId);
						csGetErrorLog.setString(2, logicalLSI);
						csGetErrorLog.setString(3, "");
						csGetErrorLog.setString(4, "");
						csGetErrorLog.setLong(5, sheet);
					}else if(accountIDFlag){
						csGetErrorLog = con.prepareCall(sqlGetRowsOfSheetForAccount);
						csGetErrorLog.setInt(1, templateId);
						csGetErrorLog.setString(2, accountID);						
						csGetErrorLog.setLong(3, sheet);
					}					
					rsGetErrorLog = csGetErrorLog.executeQuery();
					while(rsGetErrorLog.next())
					{
						totalRows=rsGetErrorLog.getInt("totalRows");
					}
					stmt.executeUpdate(deleteSplitedList);
					return totalRows;
				}
			}			
		}
		catch(SQLException sqlEx)
		{
			logger.error(sqlEx.getMessage() + " SQLException occured in getErrorLog method of " + this.getClass().getSimpleName());
			throw new IOESException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
		}
		catch(Exception ex)
		{
			logger.error(ex.getMessage() + " Exception occured in getErrorLog method of " + this.getClass().getSimpleName());
			throw new IOESException("Exception : "+ ex.getMessage(), ex);
		}
		finally
		{
			try
			{
				stmt.executeUpdate(deleteSplitedList);
				DbConnection.closeResultset(rsGetErrorLog);
				DbConnection.closeCallableStatement(csGetErrorLog);
				DbConnection.closeStatement(stmt);
				DbConnection.freeConnection(con);
			}
			catch(SQLException sqlEx)
			{
				logger.error(sqlEx.getMessage() + " SQLException occured while closing database objects in getErrorLog method of " + this.getClass().getSimpleName());
				throw new IOESException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
			}
			catch(Exception ex)
			{
				logger.error(ex.getMessage() + " Exception occured while closing database objects in getErrorLog method of " + this.getClass().getSimpleName());
				throw new IOESException("Exception : "+ ex.getMessage(), ex);
			}
		}
		return totalRows;
	}		
	//END[004]
	//START[005]
	//Started By Saurabh for Search BulkUpload to get Parties
	public ArrayList fetchPartyDetailsForSearchBulkUpload() throws Exception
	{
		
		ArrayList<TransactionTemplateDto> PartyList = new ArrayList<TransactionTemplateDto>();
		CallableStatement csGetErrorLog = null;
		ResultSet rsGetPart = null;
		Connection con = null;
		TransactionTemplateDto dtoObj ;
		
		try
		{
			con = DbConnection.getConnectionObject();
			
			csGetErrorLog = con.prepareCall(sqlGetPartyId);
			csGetErrorLog.setInt(1, 0);
			rsGetPart = csGetErrorLog.executeQuery();

			while(rsGetPart.next())
			{
				dtoObj = new TransactionTemplateDto();
				dtoObj.setPartyId(rsGetPart.getString("PARTY_ID"));
				dtoObj.setPartyName(rsGetPart.getString("PARTYNAME"));
				PartyList.add(dtoObj);
			}
		}
		
		catch(Exception ex)
		{
			con.rollback();
			ex.printStackTrace();
		}
		finally
		{
			try 
			{
				DbConnection.closeResultset(rsGetPart);
				DbConnection.closeCallableStatement(csGetErrorLog);
				DbConnection.freeConnection(con);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		
		return PartyList;
	}
	//End[005]
	//START[006]
//	Started By Saurabh for Search BulkUpload to get Accounts
	public ArrayList fetchAccountDetailsForSearchBulkUpload(PagingDto objDto) throws Exception
	{
		ArrayList<TransactionTemplateDto> AccountList = new ArrayList<TransactionTemplateDto>();
		CallableStatement csGetErrorLog = null;
		ResultSet rsGetPart = null;
		Connection con = null;
		TransactionTemplateDto dtoObj ;
		int recordCount=0;
		NewOrderDto objNewOrderDto = new NewOrderDto();
		try
		{
			con = DbConnection.getConnectionObject();
			
			csGetErrorLog = con.prepareCall(sqlGetAccountId);
			csGetErrorLog.setString(1, objDto.getSourcePartyNo());
			csGetErrorLog.setString(2, objDto.getAccountName());
			csGetErrorLog.setString(3, objDto.getAccountIDString());
			csGetErrorLog.setString(4, objDto.getSortBycolumn());
			csGetErrorLog.setString(5, objDto.getSortByOrder());
			csGetErrorLog.setInt(6, objDto.getStartIndex());
			csGetErrorLog.setInt(7, objDto.getEndIndex());
			rsGetPart = csGetErrorLog.executeQuery();

			while(rsGetPart.next())
			{
				dtoObj = new TransactionTemplateDto();
				dtoObj.setAccountID(rsGetPart.getString("ACCOUNTID"));
				dtoObj.setCrmAccountNo(rsGetPart.getString("CRMACCOUNTNO"));
				dtoObj.setAccountName(rsGetPart.getString("ACCOUNTNAME"));
				recordCount=rsGetPart.getInt("FULL_REC_COUNT");
				objNewOrderDto.getPagingSorting().setRecordCount(recordCount);	
				dtoObj.setMaxPageNo(objNewOrderDto.getPagingSorting().getMaxPageNumber());
				AccountList.add(dtoObj);
			}
		}
		
		catch(Exception ex)
		{
			con.rollback();
			ex.printStackTrace();
		}
		finally
		{
			try 
			{
				DbConnection.closeResultset(rsGetPart);
				DbConnection.closeCallableStatement(csGetErrorLog);
				DbConnection.freeConnection(con);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		
		return AccountList;
	}
	//End
	//END[006]
	//START[007]
//	Started By Saurabh for Search BulkUpload to get LSIs
	public ArrayList fetchLSIDetailsForSearchBulkUpload(PagingDto objDto) throws Exception
	{
		ArrayList<TransactionTemplateDto> LSIList  = new ArrayList<TransactionTemplateDto>();
		CallableStatement csGetErrorLog = null;
		ResultSet rsGetPart = null;
		Connection con = null;
		TransactionTemplateDto dtoObj ;
		int recordCount=0;
		NewOrderDto objNewOrderDto = new NewOrderDto();
		try
		{
			con = DbConnection.getConnectionObject();
			
			csGetErrorLog = con.prepareCall(sqlGetLSI);
			csGetErrorLog.setString(1,objDto.getAccountIDString());
			csGetErrorLog.setInt(2,objDto.getSubChangeTypeId());
			csGetErrorLog.setString(3,objDto.getLogicalSINo());
			csGetErrorLog.setString(4, objDto.getSearchAccountNo());
			csGetErrorLog.setString(5, objDto.getSortBycolumn());
			csGetErrorLog.setString(6, objDto.getSortByOrder());
			csGetErrorLog.setInt(7, objDto.getStartIndex());
			csGetErrorLog.setInt(8, objDto.getEndIndex());
			rsGetPart = csGetErrorLog.executeQuery();

			while(rsGetPart.next())
			{
				dtoObj = new TransactionTemplateDto();
				//Start[Date:01-04-2013 Paging Size Configurable for Bulkupload LSI Look up]---
				objNewOrderDto.getPagingSorting().setPageRecords(objDto.getPageRecords());
				dtoObj.setCrmAccountNo(rsGetPart.getString("CRMACCOUNTNO"));
				dtoObj.setLogicalsiNo(rsGetPart.getLong("LOGICAL_SI_NO"));
				recordCount=rsGetPart.getInt("FULL_REC_COUNT");
				objNewOrderDto.getPagingSorting().setRecordCount(recordCount);	
				dtoObj.setMaxPageNo(objNewOrderDto.getPagingSorting().getMaxPageNumber());
				LSIList .add(dtoObj);
			}
		}
		
		catch(Exception ex)
		{
			con.rollback();
			ex.printStackTrace();
		}
		finally
		{
			try 
			{
				DbConnection.closeResultset(rsGetPart);
				DbConnection.closeCallableStatement(csGetErrorLog);
				DbConnection.freeConnection(con);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		
		return LSIList ;
	}
	//End[007]
	
	
	public ArrayList getResultErrorMixLog(int fileId) throws IOESException
	{
		//logger.info(" Entered into getErrorLog method of " + this.getClass().getSimpleName());
		
		ArrayList<ErrorLogDto> resultList = new ArrayList<ErrorLogDto>();
		CallableStatement csGetErrorLog = null;
		ResultSet rsGetErrorLog = null;
		Connection con = null;
		ErrorLogDto dtoObj ;
		
		try
		{
			DbConnection connectionClassObj = new DbConnection();	
			con = connectionClassObj.getConnectionObject();
			
			csGetErrorLog = con.prepareCall(sqlGetResultLog);
			csGetErrorLog.setInt(1, fileId);
			rsGetErrorLog = csGetErrorLog.executeQuery();

			while(rsGetErrorLog.next())
			{
				dtoObj = new ErrorLogDto();
				dtoObj.setResultSheet(rsGetErrorLog.getString("SHEEETNO"));				
				dtoObj.setResultLogValue(rsGetErrorLog.getString("RESULTLOG"));
				dtoObj.setErrorLogValue(rsGetErrorLog.getString("ERRORLOG"));
				
				resultList.add(dtoObj);
					
			}
		}
		catch(SQLException sqlEx)
		{
			logger.error(sqlEx.getMessage() + " SQLException occured in getErrorLog method of " + this.getClass().getSimpleName());
			throw new IOESException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
		}
		catch(Exception ex)
		{
			logger.error(ex.getMessage() + " Exception occured in getErrorLog method of " + this.getClass().getSimpleName());
			throw new IOESException("Exception : "+ ex.getMessage(), ex);
		}
		finally
		{
			try
			{
				DbConnection.closeResultset(rsGetErrorLog);
				DbConnection.closeCallableStatement(csGetErrorLog);
				DbConnection.freeConnection(con);
			}
			catch(SQLException sqlEx)
			{
				logger.error(sqlEx.getMessage() + " SQLException occured while closing database objects in getErrorLog method of " + this.getClass().getSimpleName());
				throw new IOESException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
			}
			catch(Exception ex)
			{
				logger.error(ex.getMessage() + " Exception occured while closing database objects in getErrorLog method of " + this.getClass().getSimpleName());
				throw new IOESException("Exception : "+ ex.getMessage(), ex);
			}
		}
		return resultList;
	}
}

