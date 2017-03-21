//=================================================================================================================
//Tag Name		Resource Name		Date		CSR No			Description
//[001]			ANIL KUMAR	   28-JULY-2011		00-05422		get list of transactions that the user has access to
//[002]			ANIL KUMAR	   28-JULY-2011		00-05422		get uploaded files for the selected transaction
//[003]			ANIL KUMAR	   28-JULY-2011		00-05422		send selected file for validation 
//[004]			ANIL KUMAR	   28-JULY-2011		00-05422		to send the validated file for being marked ready for processing 	
//[005]			ANIL KUMAR	   28-JULY-2011		00-05422		to download error file	
//[006]			ANIL KUMAR	   28-JULY-2011		00-05422		to download Result file	
//[007]			SUMIT ARORA	   28-JULY-2011		00-05422		to perform download 
//[008]			MANISHA GARG   18-DEC-2012		00-05422		to process billing trigger for bulkupload
//[009]    Lawkush  30-MAR-13  BUG ID TRNG22032013005 fixed
//[0010]        Gunjan Singla  19-JAN-2015    20141113-R1-020802   ParallelUpgrade-Multiple LSI Selection
//[0011] Raveendra Kumar  15-May-2015 20141219-R2-020936-Billing Efficiency Program      Currency Transfer bulk upload
//[0012] Priya Gupta CSR-20160301-XX-022139 -Auto billing for RR orders & other Enhancements
//=================================================================================================================
package com.ibm.ioes.bulkupload.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;

import com.ibm.ioes.beans.BillingTriggerValidation;
import com.ibm.ioes.beans.ViewOrderDto;
import com.ibm.ioes.bulkupload.dto.TransactionProcessDto;
import com.ibm.ioes.dao.NewOrderDaoExt;
import com.ibm.ioes.dao.NewOrderDaoExt.CurrencyTransferResult;
import com.ibm.ioes.dao.NewOrderDaoExt.SITransferResult;
import com.ibm.ioes.dao.NewOrderDaoExt.SITransferResult.ResultType;
import com.ibm.ioes.dao.ViewOrderDao;
import com.ibm.ioes.exception.IOESException;
import com.ibm.ioes.forms.CurrencyChangeDto;
import com.ibm.ioes.forms.NewOrderDto;
import com.ibm.ioes.forms.SITransferDto;
import com.ibm.ioes.newdesign.dto.BillingTriggerBulkuploadDto;
import com.ibm.ioes.schedular.BulkuploadDto;
import com.ibm.ioes.utilities.AjaxValidation;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Utility;
import com.ibm.ioes.utilities.Utility.ExceptionLogDto;


/**
 * @version 	1.0
 * @author		Sumit Arora and Anil Kumar
 */
public class TransactionProcessDaoImpl 
{
	/**
	 * Logger for the class.
	**/
	private static final Logger logger;
	
	static {

		logger = Logger.getLogger(TransactionProcessDaoImpl.class);
	}
	
	public static String sqlGetTransactionType = "{call spBulkGetTransactionType(?)}";
	public static String sqlGetTransFileList = "{call IOE.spBulk_GetFileList(?,?,?)}";
	
	public static String sqlProcessFile = "{call BULKUPLOAD.SPBULK_PROCESSFILE_PERMANENT_DISCONNECTION(?,?,?,?,?,?)}";
	public static String sqlGetTemporarySPName = "{call BULKUPLOAD.SP_BULK_GET_SPNAME(?)}";
	public static String spCall = "{call "; 
	public static String sqlUpdateTempTable = "{call BULKUPLOAD.SPBULK_PROCESSFILE_BILLINGTRIGGER(?)}";
	public static String sqlgetTempTableLineData = "{call BULKUPLOAD.SP_BULK_GET_TEMP_LINE_DATA(?)}";
	public static String sqlgetTempTableChargeData = "{call IOE.SP_BULK_GET_TEMP_CHARGES_DATA(?)}";
	public static String sqlgetDownloadLineDetails = "{call BULKUPLOAD.SP_GET_LINE_DETAILS_FOR_BT_BULKUPLOAD(?)}";
	public static String sqlgetDownloadChargesDetails = "{call IOE.SP_GET_CHARGE_DETAILS_FOR_ALL_ORDERS_BULKUPLOAD(?)}";
	public static String sqlUpdateTM_TTEMPLATEFILE_VALIDATE_PROCESS = "{call BULKUPLOAD.SPUPDATE_TM_TTEMPLATEFILE_VALIDATE_PROCESS(?,?)}";
	//[0012]
	//public static String sqlUpdateTM_TTEMPLATEFILE_PROCESS = "{call BULKUPLOAD.SPUPDATE_TM_TTEMPLATEFILE_PROCESS(?,?)}";
	public static String sqlUpdateTM_TTEMPLATEFILE_PROCESS = "{call BULKUPLOAD.SPUPDATE_TM_TTEMPLATEFILE_PROCESS(?,?,?)}";
	public static String spExceptionFileStatus_update = "{call BULKUPLOAD.SPUPDATE_EXCEPTIONFILE_TM_TTEMPLATEFILE_PROCESS(?,?,?)}";
	//[0010] start
	/*private String sqlGetParrellUpgradeLsi=" SELECT "+
												" 	LINENO,NEW_SERVICES.PARALLELUPGRADELSINO,OLD_PARELLEL_SERVICE.SUBCHANGETYPEID,OLD_PARELLEL_SERVICE.M6_FX_PROGRESS_STATUS "+
												" FROM BULKUPLOAD.TBULKUPLOAD_BILLING_TRIGGER BULK_UPLOAD_TEMP"+
												" INNER JOIN IOE.TDISCONNECTION_HISTORY DISCONN_HIS ON DISCONN_HIS.SERVICE_PRODUCT_ID=BULK_UPLOAD_TEMP.LINENO"+
												" INNER JOIN IOE.TPOSERVICEMASTER NEW_SERVICES ON NEW_SERVICES.SERVICEID= DISCONN_HIS.MAIN_SERVICEID"+
												" AND NEW_SERVICES.IS_CHANGED_LSI=0"+
												" INNER JOIN IOE.TPOMASTER TPOMASTER ON TPOMASTER.ORDERNO=NEW_SERVICES.ORDERNO AND TPOMASTER.ORDERTYPE='New'"+
												" INNER JOIN IOE.TPOSERVICEMASTER OLD_PARELLEL_SERVICE ON OLD_PARELLEL_SERVICE.LOGICAL_SI_NO=NEW_SERVICES.PARALLELUPGRADELSINO"+
												" AND OLD_PARELLEL_SERVICE.IS_CHANGED_LSI=0 AND "+
												" ( NOT(OLD_PARELLEL_SERVICE.SUBCHANGETYPEID=3 "+
												 "        AND OLD_PARELLEL_SERVICE.M6_FX_PROGRESS_STATUS='FX_BT_END' "+
												  "       AND OLD_PARELLEL_SERVICE.SERVICETYPEID=NEW_SERVICES.SERVICETYPEID)"+
												" or OLD_PARELLEL_SERVICE.SUBCHANGETYPEID IS NULL or  OLD_PARELLEL_SERVICE.M6_FX_PROGRESS_STATUS IS NULL)"+
												" where FILEID=? ";*/

	private String sqlGetNonValidMultipleParrellUpgradeLsi="SELECT LINENO,PG.PARALLEL_UPGRADE_LSI,OLD_PARELLEL_SERVICE.SUBCHANGETYPEID,OLD_PARELLEL_SERVICE.M6_FX_PROGRESS_STATUS" 
													+" FROM BULKUPLOAD.TBULKUPLOAD_BILLING_TRIGGER BULK_UPLOAD_TEMP"
													+" INNER JOIN IOE.TDISCONNECTION_HISTORY DISCONN_HIS ON DISCONN_HIS.SERVICE_PRODUCT_ID=BULK_UPLOAD_TEMP.LINENO"
													+" INNER JOIN IOE.TPOSERVICEMASTER SM ON SM.SERVICEID=DISCONN_HIS.MAIN_SERVICEID AND SM.IS_CHANGED_LSI=0"
													+" INNER JOIN IOE.TPOMASTER TPOMASTER ON TPOMASTER.ORDERNO=SM.ORDERNO AND TPOMASTER.ORDERTYPE='New'"
													+" INNER JOIN IOE.TPOSERVICE_PARALLEL_UPGRADE PG ON SM.SERVICEID=PG.SERVICEID"
													+" INNER JOIN IOE.TPOSERVICEMASTER OLD_PARELLEL_SERVICE ON OLD_PARELLEL_SERVICE.LOGICAL_SI_NO=PG.PARALLEL_UPGRADE_LSI"
													+" AND OLD_PARELLEL_SERVICE.IS_CHANGED_LSI=0"
													+" INNER JOIN IOE.TPOMASTER  MASTER_PARALLEL_ORDER ON MASTER_PARALLEL_ORDER.ORDERNO=OLD_PARELLEL_SERVICE.ORDERNO"
													+" AND (NOT(OLD_PARELLEL_SERVICE.SUBCHANGETYPEID=3 AND OLD_PARELLEL_SERVICE.M6_FX_PROGRESS_STATUS='FX_BT_END' " 
													//+" AND MASTER_PARALLEL_ORDER.ACCOUNTID=TPOMASTER.ACCOUNTID ) or OLD_PARELLEL_SERVICE.SUBCHANGETYPEID IS NULL " 
													+"  ) or OLD_PARELLEL_SERVICE.SUBCHANGETYPEID IS NULL " +
													" or  OLD_PARELLEL_SERVICE.M6_FX_PROGRESS_STATUS IS NULL)"
													+" WHERE FILEID=?";
	
	//[0010] end
	
	private String query_updateflag="update BULKUPLOAD.TBULKUPLOAD_BILLING_TRIGGER set FLAG_TILL_BTDATEVALIDATE =? where LINENO =? and FILEID = ? ";
	/**
	 * @method 	getTransactionType
	 * @purpose	get list of transactions that the user has access to
	 * @param 	userID
	 * @return	ArrayList of transactions
	 * @throws 	IOESException
	 */
	//START[001]
	public ArrayList getTransactionType(int userID) throws IOESException
	{
		//logger.info(" Entered into getTransactionType method of " + this.getClass().getSimpleName());
		
		String strUserID = ((Integer)userID).toString();
		ArrayList<TransactionProcessDto> transactionTypeList = new ArrayList<TransactionProcessDto>();
		TransactionProcessDto dtoTransactionType;
		Connection con = null;
		CallableStatement csGetTransactionType = null;
		ResultSet rsGetTransactionType = null;
		
		try
		{
			DbConnection connectionClassObj = new DbConnection();	
			con = connectionClassObj.getConnectionObject();
						
			csGetTransactionType = con.prepareCall(sqlGetTransactionType);
			csGetTransactionType.setString(1,strUserID);
			rsGetTransactionType = csGetTransactionType.executeQuery();
			
			while(rsGetTransactionType.next())
			{
				dtoTransactionType = new TransactionProcessDto();
				dtoTransactionType.setTransactionId(rsGetTransactionType.getInt("TRANSACTIONID"));
				dtoTransactionType.setStrTransactionName(rsGetTransactionType.getString("TRANSACTIONNAME"));
			
				transactionTypeList.add(dtoTransactionType);
			}
		}
		catch(SQLException sqlEx)
		{
			logger.error(sqlEx.getMessage() + " SQLException occured in getTransactionType method of " + this.getClass().getSimpleName());
			throw new IOESException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
		}
		catch(Exception ex)
		{
			logger.error(ex.getMessage() + " Exception occured in getTransactionType method of " + this.getClass().getSimpleName());
			throw new IOESException("Exception : "+ ex.getMessage(), ex);
		}
		finally
		{
			try
			{
				DbConnection.closeResultset(rsGetTransactionType);
				DbConnection.closeCallableStatement(csGetTransactionType);
				DbConnection.freeConnection(con);
			}
			catch(SQLException sqlEx)
			{
				logger.error(sqlEx.getMessage() + " SQLException occured while closing database objects in getTransactionType method of " + this.getClass().getSimpleName());
				throw new IOESException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
			}
			catch(Exception ex)
			{
				logger.error(ex.getMessage() + " Exception occured while closing database objects in getTransactionType method of " + this.getClass().getSimpleName());
				throw new IOESException("Exception : "+ ex.getMessage(), ex);
			}
		}
		return transactionTypeList;
	}	
	//END[001]
	/**
	 * @method	getTransFileList
	 * @purpose	get list of files validated successfully
	 * @param 	transactionId
	 * @param	userId
	 * @return	ArrayList of files that have been validated successfully 
	 * @throws 	IOESException
	 */
	//START[002]
	public ArrayList getTransFileList(int transactionId, int userId, int fileStatusId) throws IOESException
	{
		//logger.info(" Entered into getTransFileList method of " + this.getClass().getSimpleName());
		
		ArrayList<TransactionProcessDto> transFileList = new ArrayList<TransactionProcessDto>();
		Connection con = null;
		CallableStatement csGetTransFileList = null;
		ResultSet rsGetTransFileList = null;
		TransactionProcessDto dtoTransFileList;
		try
		{
			DbConnection connectionClassObj = new DbConnection();	
			con = connectionClassObj.getConnectionObject();
						
			csGetTransFileList = con.prepareCall(sqlGetTransFileList);
			csGetTransFileList.setInt(1,transactionId);
			csGetTransFileList.setInt(2,fileStatusId);
			csGetTransFileList.setInt(3,userId);
			rsGetTransFileList = csGetTransFileList.executeQuery();
			
			while(rsGetTransFileList.next())
			{
				dtoTransFileList = new TransactionProcessDto();
				dtoTransFileList.setFileId(rsGetTransFileList.getInt("FileID"));
				//dtoTransFileList.setStrSelectedTransactionName(rsGetTransFileList.getString("TransactionName"));
				dtoTransFileList.setStrFileName(rsGetTransFileList.getString("strFile"));
				dtoTransFileList.setStrTransDate(rsGetTransFileList.getString("TransactionDate"));
				
				transFileList.add(dtoTransFileList);
			}
		}
		catch(SQLException sqlEx)
		{
			logger.error(sqlEx.getMessage() + " SQLException occured in getTransFileList method of " + this.getClass().getSimpleName());
			throw new IOESException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
		}
		catch(Exception ex)
		{
			logger.error(ex.getMessage() + " Exception occured in getTransFileList method of " + this.getClass().getSimpleName());
			throw new IOESException("Exception : "+ ex.getMessage(), ex);
		}
		finally
		{
			try
			{
				DbConnection.closeResultset(rsGetTransFileList);
				DbConnection.closeCallableStatement(csGetTransFileList);
				DbConnection.freeConnection(con);
			}
			catch(SQLException sqlEx)
			{
				logger.error(sqlEx.getMessage() + " SQLException occured while closing database objects in getTransFileList method of " + this.getClass().getSimpleName());
				throw new IOESException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
			}
			catch(Exception ex)
			{
				logger.error(ex.getMessage() + " Exception occured while closing database objects in getTransFileList method of " + this.getClass().getSimpleName());
				throw new IOESException("Exception : "+ ex.getMessage(), ex);
			}
		}
		return transFileList;
	}
	//END[002]
	/**
	 * @method 	processFiles
	 * @purpose	mark files ready for processing
	 * @param 	fileIds
	 * @throws 	IOESException
	 */
	//START[003]
	public String processFiles(String fileIds, int templateid,String empid) throws Exception
	{
		logger.info(" Entered into processFiles method of " + this.getClass().getSimpleName());
		
		Connection con = null, conn = null;
		Statement stmt=null;
		CallableStatement csProcessFiles = null;
		String retval = "";
		CallableStatement csGetTemporarySPName = null;
		ResultSet rsGetTemporarySPName = null;
		String spName="";
		StringBuffer strBufSpCall =null;
		long employeeID=0l;
		try
		{
			DbConnection connectionClassObj = new DbConnection();	
			con = connectionClassObj.getConnectionObject();
			conn = connectionClassObj.getConnectionObject();
			conn.setAutoCommit(false);			
			
			if(empid!=null){
				employeeID=Long.valueOf(empid);
			}else{
				employeeID=1;
			}
			csGetTemporarySPName = con.prepareCall(sqlGetTemporarySPName);
			csGetTemporarySPName.setInt(1,templateid);
			
			rsGetTemporarySPName = csGetTemporarySPName.executeQuery();
			
			while(rsGetTemporarySPName.next())
			{
				spName = rsGetTemporarySPName.getString("PROCESS_PROC");
				
				strBufSpCall = new StringBuffer(spCall);
				strBufSpCall.append(spName);
				strBufSpCall.append("(?,?,?,?,?,?,?,?");
				strBufSpCall.append(")}");;
			}
			
				csProcessFiles = conn.prepareCall(strBufSpCall.toString());
				csProcessFiles.setLong(1,Long.valueOf(fileIds));
				csProcessFiles.setLong(2,0);//RESULT
				csProcessFiles.setLong(3,0);
				csProcessFiles.setString(4,"");
				csProcessFiles.setString(5,"");
				csProcessFiles.setString(6,"");
				csProcessFiles.setLong(7,0);
				csProcessFiles.setLong(8,employeeID);//EMPID
	
				csProcessFiles.execute();
				retval = csProcessFiles.getString(2);
				//System.out.println("Error:::::::::::::::::::::::::"+ csProcessFiles.getString(5));
				//System.out.println("Step:::::::::::::::::::::::::::"+ csProcessFiles.getString(6));			
				if(csProcessFiles.getString(5)==null)
				{
					conn.commit();
				}
				else
				{
					conn.rollback();				
					retval="-1";
				}
					
			
			
		}
		catch(SQLException sqlEx)
		{
			sqlEx.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logger.error(sqlEx.getMessage() + " SQLException occured in processFiles method of " + this.getClass().getSimpleName());
			throw new IOESException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
		}
		catch(Exception ex)
		{
			try {
				conn.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ex.printStackTrace();
			logger.error(ex.getMessage() + " Exception occured in processFiles method of " + this.getClass().getSimpleName());
			throw new IOESException("Exception : "+ ex.getMessage(), ex);
		}
		finally
		{
			try
			{	
				DbConnection.closeResultset(rsGetTemporarySPName);
				DbConnection.closeStatement(stmt);
				DbConnection.closeCallableStatement(csGetTemporarySPName);
				DbConnection.closeCallableStatement(csProcessFiles);
				DbConnection.freeConnection(con);
				DbConnection.freeConnection(conn);
			}
			catch(SQLException sqlEx)
			{
				logger.error(sqlEx.getMessage() + " SQLException occured while closing database objects in processFiles method of " + this.getClass().getSimpleName());
				throw new IOESException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
			}
			catch(Exception ex)
			{
				logger.error(ex.getMessage() + " Exception occured while closing database objects in processFiles method of " + this.getClass().getSimpleName());
				throw new IOESException("Exception : "+ ex.getMessage(), ex);
			}
		}
		return retval;
	}
//END[003]
	
//008 start	
	public String processFiles_forBTBulkUpload(int fileIds, int templateid,String empid) throws Exception
	{
		logger.info(" Entered into processFiles method of " + this.getClass().getSimpleName());
		
		Connection con = null;
		String retval = "";
		String str=null;
		String str_sps=null;
		ArrayList<ViewOrderDto> spiids = new ArrayList<ViewOrderDto>();
		String strBillingTrigger=null;
		CallableStatement csUpdateTempTable = null;
		CallableStatement csGetTempTableData = null;
		CallableStatement csUpdateTM_TTEMPLATEFILE_PROCESS=null;
		CallableStatement csGetDownloadedLineItemForBT = null;
		CallableStatement csException =null;
		ResultSet rsGetTemporarySPName = null;
		ResultSet rsGetDownloadedLineItemForBT = null;
		int flag=1;
		ViewOrderDto objLinedto=null;
		ViewOrderDto objfinaldto=null;
		ViewOrderDto objfinaldto_HashMap=null;
		ViewOrderDto objupdateErrordto=null;
		ViewOrderDto objErrordto=null;
		ViewOrderDto objServiceids=null;
		String locdate=null;
		String locrecdate=null;
		ViewOrderDto objvalidate_returndto=null;
		ViewOrderDto objHashdto=null;
		ViewOrderDto objsavedto=null;
		NewOrderDto objBillingTriggerDto=null;
		ViewOrderDto objvalidatedto=null;
		PreparedStatement ps=null;
		PreparedStatement ps_update_till_val=null;
		PreparedStatement ps_update_bt_error=null;
		int k=0;
		int u=0;
		int bterror=0;
		ArrayList<ViewOrderDto> alTempLineDetails = null;
		ArrayList<ViewOrderDto> alLineForBTDetails = null;
		AjaxValidation objValidate = new AjaxValidation();
		ViewOrderDao objDao = new ViewOrderDao();
		BillingTriggerValidation validate=new BillingTriggerValidation();
		HashMap<String,ViewOrderDto> map_lineid_btreadybean=new HashMap<String,ViewOrderDto>();
		HashMap<String,ViewOrderDto> map_validate=new HashMap<String,ViewOrderDto>();
		HashMap<String,ViewOrderDto> map_lines_uploaded=new HashMap<String,ViewOrderDto>();
		DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
		
		Long btDoneBy=0l;
		// start[009]
		if(!(empid==null || empid==""))
			btDoneBy=Long.valueOf(empid);
		// End[009]
		
		
		try
		{
			con = DbConnection.getConnectionObject();
			alTempLineDetails = new ArrayList<ViewOrderDto>();
			alLineForBTDetails = new ArrayList<ViewOrderDto>();
			// step 1: to update those line which have duplicate lines
			Utility.LOG("Step1 BT_BULK Started  retrieving data for fileID : "+fileIds);
			csUpdateTempTable = con.prepareCall(sqlUpdateTempTable);  // update those line which have duplicate SPids
			csUpdateTempTable.setLong(1,Long.valueOf(fileIds));
			csUpdateTempTable.executeUpdate();
			Utility.LOG("Step1  BT_BULK END for fileID : "+fileIds);
			// STEP 1 END
			
			//  step 2: to get Uploaded Line Items error free 
			Utility.LOG("Step2 BT_BULK Started  retrieving data for fileID : "+fileIds);
			csGetTempTableData = con.prepareCall(sqlgetTempTableLineData); // get UPloaded line items
			csGetTempTableData.setLong(1,Long.valueOf(fileIds));
			rsGetTemporarySPName = csGetTempTableData.executeQuery();
			
			while(rsGetTemporarySPName.next())
			{
				ViewOrderDto objViewOrderDto= new ViewOrderDto();
				objViewOrderDto.setServiceProductID(rsGetTemporarySPName.getString("LINEITEMNUMBER")); 
				objViewOrderDto.setLogicalSino(rsGetTemporarySPName.getString("LSI_NO")); 
				objViewOrderDto.setOrderNo(rsGetTemporarySPName.getString("ORDERNO")); 
				objViewOrderDto.setLocNo(rsGetTemporarySPName.getString("LOCNO"));
				objViewOrderDto.setLocDate(rsGetTemporarySPName.getString("LOCDATE"));
				objViewOrderDto.setLocRecDate(rsGetTemporarySPName.getString("LOC_REC_DATE"));
				objViewOrderDto.setBillingTriggerDate(rsGetTemporarySPName.getString("BT_DATE")); 
				objViewOrderDto.setErrorlog(rsGetTemporarySPName.getString("ERRORLOG"));
				//[0012]
				objViewOrderDto.setIsavailableforbt(false);
				alTempLineDetails.add(objViewOrderDto);
				map_lines_uploaded.put(objViewOrderDto.getServiceProductID(), objViewOrderDto);
			}
			rsGetTemporarySPName.close();
			Utility.LOG("Step2 BT_BULK End for fileID : "+fileIds);
			// step 2: end
			Utility.LOG("Step3 BT_BULK Started retrieving data for fileID : "+fileIds);
			csGetDownloadedLineItemForBT = con.prepareCall(sqlgetDownloadLineDetails); // get downloaded  line items
			csGetDownloadedLineItemForBT.setLong(1,Long.valueOf(fileIds));
			rsGetDownloadedLineItemForBT = csGetDownloadedLineItemForBT.executeQuery();
			while(rsGetDownloadedLineItemForBT.next())
			{
				ViewOrderDto objLineForBTDto= new ViewOrderDto();
				objLineForBTDto.setServiceProductID(rsGetDownloadedLineItemForBT.getString("LINENUMBER")); 
				objLineForBTDto.setOrderNo(rsGetDownloadedLineItemForBT.getString("ORDERNO"));
				objLineForBTDto.setLogicalSino(rsGetDownloadedLineItemForBT.getString("LOGICALSINO"));
				objLineForBTDto.setIsAutoBilling(rsGetDownloadedLineItemForBT.getInt("ISAUTOBILLING"));
				objLineForBTDto.setLocNo(rsGetDownloadedLineItemForBT.getString("LOCNO"));
				objLineForBTDto.setLocDate(rsGetDownloadedLineItemForBT.getString("LOCDATE"));
				objLineForBTDto.setLocRecDate(rsGetDownloadedLineItemForBT.getString("LOC_REC_DATE"));
				objLineForBTDto.setBillingTriggerDate(rsGetDownloadedLineItemForBT.getString("BILLINGTRIGGERDATE"));
				objLineForBTDto.setOrder_subtype(rsGetDownloadedLineItemForBT.getString("CHANGETYPENAME"));
				objLineForBTDto.setOrdertype(rsGetDownloadedLineItemForBT.getString("ORDERTYPE"));
				objLineForBTDto.setIsLineDisconnected(Utility.fnCheckNull(rsGetDownloadedLineItemForBT.getString("ISLINE_DISCONNECTED")));
				objLineForBTDto.setLineOldOrNew(Utility.fnCheckNull(rsGetDownloadedLineItemForBT.getString("LINE_OLD_OR_NEW")));
				objLineForBTDto.setNoOfDisconnectClose(rsGetDownloadedLineItemForBT.getInt("NO_DISCONNECT_DATE_PENDING"));
				objLineForBTDto.setLine_status(rsGetDownloadedLineItemForBT.getString("LINE_STATUS"));
				alLineForBTDetails.add(objLineForBTDto);
				map_lineid_btreadybean.put(objLineForBTDto.getServiceProductID(), objLineForBTDto);
			}
			rsGetDownloadedLineItemForBT.close();
			Utility.LOG("Step3 BT_BULK END for fileID : "+fileIds);
			// step 3 : end
			
			// step 4 : test validation for Line Items
			Utility.LOG("Step4 BT_BULK Started Partial Validation for fileID : "+fileIds);
			 for(int i=0;i<alTempLineDetails.size();i++)
			  {
				 objLinedto=alTempLineDetails.get(i);
				 boolean result=map_lineid_btreadybean.containsKey(objLinedto.getServiceProductID());
				 if(result==false)
				 {
					 objLinedto.setErrorlog(Utility.fnCheckNull(objLinedto.getErrorlog()) + "This Line Item is not available for Billing Trigger");
				 }
				 
				 if(objLinedto.getErrorlog()!=null)
				 {
					 
					 continue;
				 }
					 
				 objHashdto=map_lineid_btreadybean.get(objLinedto.getServiceProductID());
				 if((!(objHashdto.getOrderNo().equals(objLinedto.getOrderNo()))) || (!(objHashdto.getLogicalSino().equals(objLinedto.getLogicalSino()))))
					{
					  objLinedto.setErrorlog(Utility.fnCheckNull(objLinedto.getErrorlog())+"Line Item does not match with Logical SI Number or Order Number");	
						 	
					}
				if(objLinedto.getErrorlog()!=null)
					
				 	{
						continue;
				 	}
						 
				validate =new BillingTriggerValidation(); 
		        validate.setSourceData(objHashdto,null,null/*eventids*/);
		        validate.computeProperties_FORBULKUPLOAD();
		        if(objHashdto.getIsAutoBilling()==1)
		        {
		        	
		        	if((objLinedto.getLocNo()==null || "".equals(objLinedto.getLocNo()))  &&  (objLinedto.getLocDate()==null || "".equals(objLinedto.getLocDate())) && (objLinedto.getLocRecDate()==null || "".equals(objLinedto.getLocRecDate())))
		        	{
		        				objLinedto.setErrorlog(Utility.fnCheckNull(objLinedto.getErrorlog())+"Please input atleast one Field");	
		        	}
		        	if((objHashdto.getLocNo()!=null && (!("".equals(objHashdto.getLocNo())))) && (objLinedto.getLocDate()==null || "".equals(objLinedto.getLocDate())) && (objLinedto.getLocRecDate()==null || "".equals(objLinedto.getLocRecDate())))
        			{
        				objLinedto.setErrorlog(Utility.fnCheckNull(objLinedto.getErrorlog())+"Please input Loc Date or Loc Rec Date or Both");	
        			}
		        	if((objHashdto.getLocDate()!=null && (!("".equals(objHashdto.getLocDate())))) && (objLinedto.getLocNo()==null || "".equals(objLinedto.getLocNo())) && (objLinedto.getLocRecDate()==null || "".equals(objLinedto.getLocRecDate())))
        			{
        				objLinedto.setErrorlog(Utility.fnCheckNull(objLinedto.getErrorlog())+"Please input Loc No or Loc Rec Date or Both");	
        			}
		        	if((objHashdto.getLocRecDate()!=null && (!("".equals(objHashdto.getLocRecDate()))) ) && (objLinedto.getLocDate()==null || "".equals(objLinedto.getLocDate())) && (objLinedto.getLocNo()==null || "".equals(objLinedto.getLocNo())))
        			{
        				objLinedto.setErrorlog(Utility.fnCheckNull(objLinedto.getErrorlog())+"Please input Loc No or Loc Date or Both");	
        			}
		        	if((objHashdto.getLocNo()!=null && (!("".equals(objHashdto.getLocNo())))) && (objHashdto.getLocDate()!=null && (!("".equals(objHashdto.getLocDate())))) && (objLinedto.getLocRecDate()==null || "".equals(objLinedto.getLocRecDate())))
        			{
        				objLinedto.setErrorlog(Utility.fnCheckNull(objLinedto.getErrorlog())+"Please input Loc Rec Date");	
        			}
		        	if((objHashdto.getLocNo()!=null && (!("".equals(objHashdto.getLocNo()))) ) && (objHashdto.getLocRecDate()!=null && (!("".equals(objHashdto.getLocRecDate())))) && (objLinedto.getLocDate()==null || "".equals(objLinedto.getLocDate())))
        			{
        				objLinedto.setErrorlog(Utility.fnCheckNull(objLinedto.getErrorlog())+"Please input Loc Date");	
        			}
		        	if((objHashdto.getLocRecDate()!=null && (!("".equals(objHashdto.getLocRecDate())))) && (objHashdto.getLocDate()!=null &&(!("".equals(objHashdto.getLocDate())))) && (objLinedto.getLocNo()==null || "".equals(objLinedto.getLocNo())))
        			{
        				objLinedto.setErrorlog(Utility.fnCheckNull(objLinedto.getErrorlog())+"Please input Loc No");	
        			}
		        	
		        	if((objHashdto.getBillingTriggerDate()==null || "".equals(objHashdto.getBillingTriggerDate())) && (objLinedto.getBillingTriggerDate()!=null && (!("".equals(objLinedto.getBillingTriggerDate()))))) 
        			{
        				objLinedto.setErrorlog(Utility.fnCheckNull(objLinedto.getErrorlog())+"This is a Auto Billing Line so Billing Trigger Date is not required");	
        			}
		        	
		        	if((objHashdto.getBillingTriggerDate()!=null && (!("".equals(objHashdto.getBillingTriggerDate())))) && (objLinedto.getBillingTriggerDate()==null || "".equals(objLinedto.getBillingTriggerDate())))
		        	{
		        		objLinedto.setErrorlog(Utility.fnCheckNull(objLinedto.getErrorlog())+"You have delete or modified Billing trigger Date");	
		        	}
		        	
		        	if((objHashdto.getBillingTriggerDate()!=null && (!("".equals(objHashdto.getBillingTriggerDate())))) && (objLinedto.getBillingTriggerDate()!=null && (!("".equals(objLinedto.getBillingTriggerDate())))))
		        	{
		        		
		        		if(!((Utility.showDate_Report3(df.parse(objLinedto.getBillingTriggerDate()))).equals(objHashdto.getBillingTriggerDate())))
		        		{
		        			objLinedto.setErrorlog(Utility.fnCheckNull(objLinedto.getErrorlog())+"You have changed the  Billing trigger Date,please enter correct Billing Trigger Date");	
		        		}
		        	}
		        }
		        else if(objHashdto.getIsAutoBilling()==2)
		        {
		        	if("NEW".equals(objHashdto.getLineOldOrNew()))
					{
		        		objLinedto.setErrorlog(Utility.fnCheckNull(objLinedto.getErrorlog())+"Billing Mode for this Line Item is Demmed hence Billing Trigger is not required ");	
					}
		        	else
		        	{
		        		if((!(Utility.fnCheckNull(objLinedto.getLocNo())).equals(Utility.fnCheckNull((objHashdto.getLocNo())))) || (!(Utility.fnCheckNull((objLinedto.getLocDate()))).equals(Utility.fnCheckNull(objHashdto.getLocDate()))) || (!(Utility.fnCheckNull(objLinedto.getLocRecDate())).equals(Utility.fnCheckNull(objHashdto.getLocRecDate()))))
			        	{
			        		objLinedto.setErrorlog(Utility.fnCheckNull(objLinedto.getErrorlog())+"Loc Data is non editable");	
			        	}
		        	}
		        }
		        else
		        {
		        	if("allow".equalsIgnoreCase(validate.getLocNoForEdit()) && ("".equals(objLinedto.getLocNo()) || objLinedto.getLocNo()==null))
			           {
			            	objLinedto.setErrorlog(Utility.fnCheckNull(objLinedto.getErrorlog())+"Please input LOC NO");	
			            		
			           }
			        
			          if("allow".equalsIgnoreCase(validate.getLocDateForEdit()) && ("".equals(objLinedto.getLocDate()) || objLinedto.getLocDate()==null))
			           {
			            	objLinedto.setErrorlog(Utility.fnCheckNull(objLinedto.getErrorlog())+"Please input LOC NO");	
			            		
			           }
			          
			           if("allow".equalsIgnoreCase(validate.getLocRecDateForEdit()) && ("".equals(objLinedto.getLocRecDate()) || objLinedto.getLocRecDate()==null))
			           {
			        	   objLinedto.setErrorlog(Utility.fnCheckNull(objLinedto.getErrorlog())+"Please input LOC Recieve Date");	
			            		
			           }
			          
			           if("allow".equalsIgnoreCase(validate.getBtdForEdit()) && ("".equals(objLinedto.getBillingTriggerDate())|| objLinedto.getBillingTriggerDate()==null ))
			            {
			            	objLinedto.setErrorlog(Utility.fnCheckNull(objLinedto.getErrorlog())+"Please input Billing Trigger Date");	
			            		
			            }
		        	
		        }
	           if(objLinedto.getErrorlog()!=null)
				{
						 continue;
						 
				}
	           
	           if(objHashdto.getIsAutoBilling()==1)
		        {
	        	   
	        	   
	        	   if((objHashdto.getLocNo()==null || "".equals(objHashdto.getLocNo())) && (objLinedto.getLocNo()!=null && (!("".equals(objLinedto.getLocNo())))))
	        	   {
	        		   objLinedto.setLocNo_Status("edited");
	        	   }
	        	   if((objHashdto.getLocDate()==null || "".equals(objHashdto.getLocDate())) && (objLinedto.getLocDate()!=null && (!("".equals(objLinedto.getLocDate())))))
	        	   {
	        		   objLinedto.setLocDate_Status("edited");
	        	   }
	        	   if((objHashdto.getLocRecDate()==null || "".equals(objHashdto.getLocRecDate())) && (objLinedto.getLocRecDate()!=null && (!("".equals(objLinedto.getLocRecDate())))))
	        	   {
	        		   objLinedto.setLocRecDate_Status("edited");
	        	   }
		        }
	           
	           
		       if("allow".equalsIgnoreCase(validate.getBtdForEdit()))
		        {
		           	 if(str_sps==null)
					 {
	        	   		str_sps=objLinedto.getServiceProductID() ;
	        	   		spiids.add(objLinedto);
					 }
					 else
					 {
					 	str_sps=str_sps + "," + objLinedto.getServiceProductID();
					 	spiids.add(objLinedto);
					 }
			 		
		           if(strBillingTrigger==null)
		           {
		        	   strBillingTrigger=objLinedto.getServiceProductID()+"~"+objLinedto.getLocNo()+"~"+objLinedto.getLocDate()+"~"+objLinedto.getBillingTriggerDate()+"~"+objLinedto.getLocRecDate();
		        	   objLinedto.setBillingTriggerString(strBillingTrigger);
		           }
		           else
		           {
		        	   strBillingTrigger=strBillingTrigger+"@@"+(objLinedto.getServiceProductID()+"~"+objLinedto.getLocNo()+"~"+objLinedto.getLocDate()+"~"+objLinedto.getBillingTriggerDate()+"~"+objLinedto.getLocRecDate());
		        	   objLinedto.setBillingTriggerString(strBillingTrigger);
		           }
		      }
		}
		Utility.LOG("Step4 BT_BULK END for fileID : "+fileIds);
	    con.setAutoCommit(false);		
	    Utility.LOG("Step5 BT_BULK Started to update partial Validation Flag for fileID : "+fileIds);
	    if(str_sps!=null)
		{
		    String query_updateflag="update BULKUPLOAD.TBULKUPLOAD_BILLING_TRIGGER set FLAG_TILL_BTDATEVALIDATE =1 where LINENO =? and FILEID = ? ";
		    ps_update_till_val = con.prepareStatement(query_updateflag);
		    for(int count_update=0; count_update<spiids.size();count_update++)
		    {
		    	objServiceids=spiids.get(count_update);
		    	ps_update_till_val.setString(1,objServiceids.getServiceProductID());
		    	ps_update_till_val.setLong(2,Long.valueOf(fileIds));
		    	ps_update_till_val.addBatch();
		    	u++;
			   
		    }
		    int[] updateCounts_validate = ps_update_till_val.executeBatch();
			if (updateCounts_validate.length == u) 
			{
				con.commit();
				//System.out.println("All FLAG_TILL_BTDATEVALIDATE for Lines Updated Successfully");
				Utility.LOG("All FLAG_TILL_BTDATEVALIDATE for Lines Updated Successfully for FileID : "+fileIds);
			} 
			else 
			{
				//System.out.println("failed to Update FLAG_TILL_BTDATEVALIDATE for Lines");
				Utility.LOG("failed to Update FLAG_TILL_BTDATEVALIDATE for Lines for FileID : "+fileIds);
				con.rollback();
			}
		}		
	   
		if(str_sps!=null)
		{
			//Added Validation: Parallel Upgrade Logical Lsi Disconnection Order must be complete before proceed billing trigger.
			//By:Anil Kumar::Date- 17-Sep-2014 BugFix: Ticket::IN293525
			//[0010] start
			Utility.LOG("Step5.1 BT_BULK Started to update partial Validation Flag for fileID : "+fileIds);
			ArrayList<BillingTriggerBulkuploadDto> btDtolist=getParrellelUpgradeLsiDetails(fileIds,con);
			ArrayList<Long> listParallelUpgradeLSISPID=new ArrayList<Long>();
			String strParallelValidationMsgs="";
			for(BillingTriggerBulkuploadDto element:btDtolist){
				strParallelValidationMsgs="";
				ViewOrderDto objViewOrderDto=map_lines_uploaded.get(String.valueOf(element.getLineNumber()));
				if(element.getSubchangeTypeid() !=3){
					strParallelValidationMsgs=Utility.fnCheckNull(objViewOrderDto.getErrorlog()) +"Latest order is not Disconnection Order of Parallel Upgrade Lsi No "+element.getParrellelUpgradeLsiNo()+". " ;
					//objViewOrderDto.setErrorlog(Utility.fnCheckNull(objViewOrderDto.getErrorlog()) +"Latest order is not Disconnection Order of Parallel Upgrade Lsi No "+element.getParrellelUpgradeLsiNo() );
					objViewOrderDto.setErrorlog(strParallelValidationMsgs);
				}else{
					if(element.getM6_fx_progress_status() ==null || !(element.getM6_fx_progress_status().equals("FX_BT_END"))){
						strParallelValidationMsgs=Utility.fnCheckNull(objViewOrderDto.getErrorlog()) +"Disconnection order of Parallel Upgrade Lsi No "+element.getParrellelUpgradeLsiNo() +" is not completed"+". ";
						//objViewOrderDto.setErrorlog(Utility.fnCheckNull(objViewOrderDto.getErrorlog()) +"Disconnection order of Parallel Upgrade Lsi No "+element.getParrellelUpgradeLsiNo() +" is not completed !!! ");
						objViewOrderDto.setErrorlog(strParallelValidationMsgs);
					}					
				}
				//objViewOrderDto.setErrorlog(strParallelValidationMsgs);
				listParallelUpgradeLSISPID.add(element.getLineNumber());
			}
			
			Utility.LOG("Step5.2 BT_BULK Started to update partial Validation Flag for fileID : "+fileIds);
			if(listParallelUpgradeLSISPID.size() > 0){
				int status=updateBulkUploadTempFlag(con,listParallelUpgradeLSISPID,fileIds,0);
			}
			//Added Validation: Parallel Upgrade Logical Lsi Disconnection Order must be complete before proceed billing trigger.
			Utility.LOG("Step5.3 BT_BULK Started to update partial Validation Flag for fileID : "+fileIds);
	 		ViewOrderDto obj_for_FX_Validation=new ViewOrderDto();
	 		obj_for_FX_Validation.setBillingTriggerString(strBillingTrigger);
		 	objvalidatedto = objValidate.validateActiveDate_forbulkupload(obj_for_FX_Validation,con,"N",str_sps,flag,Long.valueOf(fileIds));
		 	Utility.LOG("Step5.4 BT_BULK Started to update partial Validation Flag for fileID : "+fileIds);
		 	 for(int h=0; h<alTempLineDetails.size();h++)
			    {
		 		 	objErrordto=alTempLineDetails.get(h);
		 		 	map_validate=objvalidatedto.getMap_validate();
		 		 	boolean result_validate=map_validate.containsKey(objErrordto.getServiceProductID());
		 		 	if(result_validate==true)
		 		 	{
		 		 		objvalidate_returndto=map_validate.get(objErrordto.getServiceProductID());
		 		 		if(objvalidate_returndto.getBillingTriggerValidationErrors()!=null)
		 		 		{
		 		 			objErrordto.setErrorlog((Utility.fnCheckNull(objErrordto.getErrorlog()))+(objvalidate_returndto.getBillingTriggerValidationErrors()));
		 		 		}
		 		 	}
			    }
		}// step 5 end	
		Utility.LOG("Step5 BT_BULK End for fileID : "+fileIds);
		
		// step 6:update errorlog for lines 
		Utility.LOG("Step6,7,8 BT_BULK Started to update after complete validation for fileID : "+fileIds);
		//[0012]
		//String query="update BULKUPLOAD.TBULKUPLOAD_BILLING_TRIGGER set ERROR_LOG =? where LINENO = ? and FILEID = ? ";
	 	String query="update BULKUPLOAD.TBULKUPLOAD_BILLING_TRIGGER set ERROR_LOG =?,RESULT_LOG = ? where LINENO = ? and FILEID = ? ";
    	ps = con.prepareStatement(query);
	    for(int count=0; count<alTempLineDetails.size();count++)
	    {
	    	objupdateErrordto=alTempLineDetails.get(count);
	    	ps.setString(1, objupdateErrordto.getErrorlog());
			//[0012]
	    	ps.setString(2, objupdateErrordto.getErrorlog());
		    ps.setString(3,objupdateErrordto.getServiceProductID());
		    ps.setLong(4,Long.valueOf(fileIds));
	    	ps.addBatch();
	    	k++;
		   
	    }
	    int[] updateCounts = ps.executeBatch();
		if (updateCounts.length == k) 
		{
			con.commit();
			//System.out.println("All ErrorLog for Lines Updated Successfully");
			Utility.LOG("All ErrorLog for Lines Updated Successfully for FileID : "+fileIds);
		} 
		else 
		{
			//System.out.println("failed to Update ErrorLog for Lines");
			Utility.LOG("failed to Update ErrorLog for Lines for FileID : "+fileIds);
			con.rollback();
		}
		//step 7:end
		Utility.LOG("Step6,7,8 BT_BULK end for fileID : "+fileIds);
		
		// step9: update status
		Utility.LOG("Step9 BT_BULK START validation update using proc SPUPDATE_TM_TTEMPLATEFILE_VALIDATE_PROCESS for fileID : "+fileIds);
		 csUpdateTM_TTEMPLATEFILE_PROCESS = con.prepareCall(sqlUpdateTM_TTEMPLATEFILE_VALIDATE_PROCESS);
		 csUpdateTM_TTEMPLATEFILE_PROCESS.setLong(1,Long.valueOf(fileIds));
		 csUpdateTM_TTEMPLATEFILE_PROCESS.setLong(2,0);
		 csUpdateTM_TTEMPLATEFILE_PROCESS.executeUpdate();
		 retval = csUpdateTM_TTEMPLATEFILE_PROCESS.getString(2);
		 //	step9: end
		 Utility.LOG("Step9 BT_BULK End for fileID : "+fileIds);
		 // step 10:do billing trigger for line items
		 Utility.LOG("Step10 BT_BULK STARTED Billing Trigger LInes for fileID : "+fileIds);
		 //[0012]
		 int statusofbtofanylineitem = 0;
		 if("3".equals(retval))
		 {
			 for(int i=0;i<alTempLineDetails.size();i++)
				{
				 	objfinaldto=alTempLineDetails.get(i);
				 	//[0012]
				 	String errorlog = objfinaldto.getErrorlog();
				 	if(errorlog == null)
					 {
				 		objfinaldto.setIsavailableforbt(true);
					 	objfinaldto_HashMap=map_lineid_btreadybean.get(objfinaldto.getServiceProductID());
				 	
				 	if(objfinaldto_HashMap.getIsAutoBilling()==1)
				 	{
				 	
				 		objsavedto=objDao.saveLocData_forBulkUpload(con,objfinaldto,-1);
				 		if(objsavedto.getFailuetoInsertLocData().size()>0)
				 		{
				 			objfinaldto.setErrorlog("InternalError");
				 			objfinaldto.setBillingTriggerProcess("InternalError");
				 		}
				 	}
				 	
				 	else
				 	{
				 		
				 		 String locno=objfinaldto.getLocNo();
				 		 if (objfinaldto.getLocDate() != null && !"".equals(objfinaldto.getLocDate()))
							{
				 			  locdate=Utility.showDate_Report3(df.parse(objfinaldto.getLocDate()));
							}
				 		 else
				 		 {
				 			  locdate=objfinaldto.getLocDate();
				 		 }
				 		 if (objfinaldto.getLocRecDate() != null && !"".equals(objfinaldto.getLocRecDate()))
							{
				 			  locrecdate=Utility.showDate_Report3(df.parse(objfinaldto.getLocRecDate()));
				 		 }
				 		 else
				 		 {
				 			 locrecdate=objfinaldto.getLocRecDate();
				 			 
				 		 }
				 		 	String btdate=Utility.showDate_Report3(df.parse(objfinaldto.getBillingTriggerDate()));
					 		if("Rate Revision".equalsIgnoreCase(objfinaldto_HashMap.getOrder_subtype()))
				 			{
					 			if("Unchanged".equalsIgnoreCase(objfinaldto.getLine_status()))
					 			{
					 				locno=objfinaldto_HashMap.getLocNo();
					 				locdate=objfinaldto_HashMap.getLocDate();
					 			}
					 			
				 			}
					 		if("Disconnection".equalsIgnoreCase(objfinaldto_HashMap.getOrder_subtype()))
				 			{
					 			locno=objfinaldto_HashMap.getLocNo();
					 			locdate=objfinaldto_HashMap.getLocDate();
					 			
				 			}
				 			objBillingTriggerDto=objDao.pushChargesInSecondaryTablesForChangeOrders(con,(Long.parseLong(objfinaldto.getServiceProductID())),locno,locdate,btdate,locrecdate,(Long.parseLong(objfinaldto.getOrderNo())),"0",btDoneBy);// Start [009] 
				 			if(!("SUCCESS".equalsIgnoreCase(objBillingTriggerDto.getBillingTriggerStatus())))
			 					{
				 					objfinaldto.setErrorlog("InternalError");
				 					objfinaldto.setBillingTriggerProcess(objBillingTriggerDto.getBillingTriggerMsg());
			 				
			 					}
				 			else
				 			{
				 				objfinaldto.setErrorlog("Billing Trigger done Successfully");
					 				statusofbtofanylineitem = 1;
				 				
				 			}
				 			
				 			
				 		}
				 	//objDao.setBTEndIfPossible((Long.parseLong(objfinaldto.getOrderNo())),con);	
				 		}
					}
			 			
				 }
		     //step 10 end
		 	Utility.LOG("Step10 BT_BULK END for fileID : "+fileIds);
		 
		 	 // step 11: update error if any come during billing trigger
		 	Utility.LOG("Step11 BT_BULK START to Update Errors in Temporary table for fileID : "+fileIds);
			 String query1="update BULKUPLOAD.TBULKUPLOAD_BILLING_TRIGGER set RESULT_LOG =? , IMPLEMENTATION_ERROR=? where LINENO = ? and FILEID = ? ";
			 ps_update_bt_error = con.prepareStatement(query1);
			 for(int count=0; count<alTempLineDetails.size();count++)
			    {
			    	objupdateErrordto=alTempLineDetails.get(count);
			    	//[0012]
			    	if(objupdateErrordto.getIsavailableforbt())
			    	{
			    	ps_update_bt_error.setString(1, objupdateErrordto.getErrorlog());
			    	ps_update_bt_error.setString(2, objupdateErrordto.getBillingTriggerProcess());
			    	ps_update_bt_error.setString(3,objupdateErrordto.getServiceProductID());
			    	ps_update_bt_error.setLong(4,Long.valueOf(fileIds));
			    	ps_update_bt_error.addBatch();
				    	//[0012]
					    //bterror++;
			    	}
			    	//[0012]
			    	bterror++;
				   
			    }
			 	int[] updateCounts_bt_error = ps_update_bt_error.executeBatch();
				//if (updateCounts_bt_error.length == bterror) 
			    if (bterror>0) 
				{
					con.commit();
					//System.out.println("All Billing trigger internal error updated suceesfully");
					Utility.LOG("All Billing trigger internal error updated suceesfully for fileID : "+fileIds);
				} 
				else 
				{
					//System.out.println("failed to update please retry");
					Utility.LOG("failed to update please retry");
					con.rollback();
				}
				//step 11: end
				Utility.LOG("Step11 BT_BULK END for fileID : "+fileIds);
				// step 12: final status update
				Utility.LOG("Step12 BT_BULK START to execute proc  SPUPDATE_TM_TTEMPLATEFILE_PROCESS for fileID : "+fileIds);
				csUpdateTM_TTEMPLATEFILE_PROCESS = con.prepareCall(sqlUpdateTM_TTEMPLATEFILE_PROCESS);
				csUpdateTM_TTEMPLATEFILE_PROCESS.setLong(1,Long.valueOf(fileIds));
				//[0012]
				csUpdateTM_TTEMPLATEFILE_PROCESS.setInt(2, statusofbtofanylineitem);
				csUpdateTM_TTEMPLATEFILE_PROCESS.setLong(3,0);
				csUpdateTM_TTEMPLATEFILE_PROCESS.executeUpdate();
				//[0012]
				retval = csUpdateTM_TTEMPLATEFILE_PROCESS.getString(3);
				con.commit();
				// step 12: end
				Utility.LOG("Step12 BT_BULK END for fileID : "+fileIds);
			
		}
		/*catch(SQLException sqlEx)
		{
			Utility.LOG(sqlEx+"Billing Trigger Exception Occured at LineItem No : "+objServiceids.getServiceProductID()+" in fileid : "+fileIds);
			sqlEx.printStackTrace();
			Utility.LOG_ITER(true, true, sqlEx, "");
			
			try {
				con.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				Utility.LOG(sqlEx+"Billing Trigger Exception Occured at LineItem No : "+objServiceids.getServiceProductID()+" in fileid : "+fileIds);
				e.printStackTrace();
			}
			logger.error(sqlEx.getMessage() + " SQLException occured in processFiles method of " + this.getClass().getSimpleName());
			throw new IOESException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
		}
		*/
		catch(Exception ex)
		{
			
			Utility.LOG_ITER(true, true, ex, "Billing Trigger Exception Occured at LineItem No : "+objServiceids.getServiceProductID()+" in fileid : "+fileIds);
			
			try {
				con.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try{
				String msg="Exception in Billing Trigger .\n Exception , Billing Trigger Exception Occured at LineItem No : "+objServiceids.getServiceProductID()+" in fileid : "+fileIds;
							
				csException = con.prepareCall(spExceptionFileStatus_update);
				csException.setLong(1,Long.valueOf(fileIds));
				
				java.sql.Clob clobData = 
						com.ibm.db2.jcc.t2zos.DB2LobFactory.createClob(Utility.getStackTrace(ex));
				
				csException.setClob(2, clobData);
				csException.setString(3, msg);
				
				
				csException.execute();
				
				con.commit();
				}catch(Exception exc){
					Utility.LOG_ITER(true, true, ex, "Exception Occured while changing file status in Exception Handling at LineItem No : "+objServiceids.getServiceProductID()+" in fileid : "+fileIds);
				}
			
			logger.error(ex.getMessage() + " Exception occured in processFiles method of " + this.getClass().getSimpleName());
		}
		finally
		{
			try
			{	
				DbConnection.closeCallableStatement(csUpdateTM_TTEMPLATEFILE_PROCESS);
				DbConnection.closePreparedStatement(ps_update_bt_error);
				DbConnection.closePreparedStatement(ps);
				DbConnection.closePreparedStatement(ps_update_till_val);
				DbConnection.closeCallableStatement(csGetDownloadedLineItemForBT);
				DbConnection.closeCallableStatement(csGetTempTableData);
				DbConnection.closeCallableStatement(csException);
				DbConnection.freeConnection(con);
			}
			/*catch(SQLException sqlEx)
			{
				
				logger.error(sqlEx.getMessage() + " SQLException occured while closing database objects in processFiles method of " + this.getClass().getSimpleName());
				throw new IOESException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
			}
			*/catch(Exception ex)
			{
				
				Utility.LOG_ITER(true, false, ex, "Billing Trigger Exception Occured at LineItem No : "+objServiceids.getServiceProductID()+" in fileid : "+fileIds);
				logger.error(ex.getMessage() + " Exception occured while closing database objects in processFiles method of " + this.getClass().getSimpleName());
			}
		}
		return retval;
	}
//008 end
	private ArrayList<BillingTriggerBulkuploadDto> getParrellelUpgradeLsiDetails(int fileid,Connection conn) throws Exception{
		BillingTriggerBulkuploadDto billTrgDto=null;		
		PreparedStatement pstmt=null;
		//[0010] start
		ResultSet rs=null;
		ArrayList<BillingTriggerBulkuploadDto> listBTDto=new ArrayList<BillingTriggerBulkuploadDto>();
		try{
				pstmt=conn.prepareStatement(sqlGetNonValidMultipleParrellUpgradeLsi); //[0010]
				pstmt.setLong(1, fileid);
				rs=pstmt.executeQuery();
				while(rs.next()){
					billTrgDto=new BillingTriggerBulkuploadDto();
					billTrgDto.setLineNumber(rs.getLong("LINENO"));
					billTrgDto.setParrellelUpgradeLsiNo(rs.getLong("PARALLEL_UPGRADE_LSI")); //[0010]
					billTrgDto.setSubchangeTypeid(rs.getLong("SUBCHANGETYPEID"));
					billTrgDto.setM6_fx_progress_status(rs.getString("M6_FX_PROGRESS_STATUS"));
					listBTDto.add(billTrgDto);
				}
		
			
		}catch(Exception exp){
		
			Utility.LOG(true,false,exp," err from method:getParrellelUpgradeLsiDetails");
			//throw  exp;
		}finally{
			try{
			DbConnection.closeResultset(rs);
			DbConnection.closePreparedStatement(pstmt);	
			}catch(Exception e){
				Utility.LOG(true,false,e,"err from method:getParrellelUpgradeLsiDetails");
				//throw  e;
			}
		}
		return listBTDto;
	}
	public int updateBulkUploadTempFlag(Connection con,ArrayList<Long> spidslist,int fileid,long flag) throws Exception {
		    PreparedStatement ps_update_till_val=null;
		    int row_count=0;
		    int success=0;
		    try{
		    		ps_update_till_val = con.prepareStatement(query_updateflag);
				    for(int count_update=0; count_update<spidslist.size();count_update++)
				    {
				    	ps_update_till_val.setLong(1, flag);
				    	ps_update_till_val.setString(2, String.valueOf(spidslist.get(count_update)));
				    	ps_update_till_val.setLong(3,Long.valueOf(fileid));
				    	ps_update_till_val.addBatch();
				    	row_count++;
				    }
				    int[] updateCounts_validate = ps_update_till_val.executeBatch();
					if (updateCounts_validate.length == row_count) 
					{
						con.commit();
						success=1;
					} 
					else 
					{
						con.rollback();
					}
		    }catch(Exception e){
		    	Utility.LOG_ITER(true, false, e, " from method updateBulkUploadTempFlag of class TransactionProcessDaoImpl");
//		    	throw e;
		    }finally{
		    	try{
		    		DbConnection.closePreparedStatement(ps_update_till_val);
		    	}catch(Exception e){
		    		Utility.LOG_ITER(true, false, e, " from method updateBulkUploadTempFlag of class TransactionProcessDaoImpl");
//		    		throw e;
		    	}
		    }
			return success;
	}
	
	public static boolean processMasterValidationForSITransfer(long fileid, Integer respId, long empId) {
		
		boolean successFileIds=false;
		try{
			ArrayList<BulkuploadDto> listtempData=fetchBulkSITransferDataFromTemporaryTableByFileId(fileid);
			//Data master validation start
			bulkSITransferMasterDataValidation(listtempData,fileid,respId);
			//SITransfer Processing start
			processSITransferBulkupload(listtempData,fileid,empId);
			
			successFileIds=true;
			
		}catch(Exception e){
			Utility.LOG_ITER(true, false, e, "Error Occurred from method processMasterValidationForSITransfer() in TransactionProcessDaoImpl class for fileid:"+fileid);
			try{
				ExceptionLogDto excepDtoObj=new ExceptionLogDto();
				java.sql.Clob clobData = 
						com.ibm.db2.jcc.t2zos.DB2LobFactory.createClob(Utility.getStackTrace(e));
				excepDtoObj.setAssociatedWith("BULKSITRANSFERS");
				excepDtoObj.setExceptionMessage("BULKSITRANSFER_"+fileid);
				excepDtoObj.setStackTraceMsg(clobData);
				
				Utility.logErrorToExceptionMaster(excepDtoObj);
				updateFileStatus(fileid,11); 
			}catch(Exception exp){
				Utility.LOG_ITER(true, false, e, "Error Occurred from method processMasterValidationForSITransfer() for log exception in exception master of fileid:"+fileid);
			}
		}
		
		return successFileIds;
	}
	static class SITransferBulkKey{
		
		String srcPartyNo = null;
		String targrtAccountNo = null;
		
		public String getSrcPartyNo() {
			return srcPartyNo;
		}
		public void setSrcPartyNo(String srcPartyNo) {
			this.srcPartyNo = srcPartyNo;
		}
		public String getTargrtAccountNo() {
			return targrtAccountNo;
		}
		public void setTargrtAccountNo(String targrtAccountNo) {
			this.targrtAccountNo = targrtAccountNo;
		}
		
		@Override
		public boolean equals(Object obj) {
			
			if(obj==this){
				return true;
			}
			else if(!(obj instanceof SITransferBulkKey)){
				return false;
			}
			SITransferBulkKey sitransferKeyObj=(SITransferBulkKey)obj;
			return (sitransferKeyObj.srcPartyNo.equals(srcPartyNo) && sitransferKeyObj.targrtAccountNo.equals(targrtAccountNo));			
		}
		@Override
		public int hashCode(){			
			return (srcPartyNo.hashCode()+targrtAccountNo.hashCode()*7);
		}
		
	}
	
	private static void processSITransferBulkupload(
			ArrayList<BulkuploadDto> listtempData, long fileid, long empId) throws Exception {
		
		logSITransferMasterVakidationErrors(listtempData,fileid);		
		
		clearLSIandLineitemAlreadyInSITransferDetails(fileid);
		insertLSIandLineitemToSITransferDetails(fileid);

		ArrayList<BulkuploadDto> successValidationList = loadValidationSuccessDataWithOtherAttributes(fileid);
		if(successValidationList.size() > 0){
			HashMap<String,ArrayList<BulkuploadDto>> segregatedData= groupDataForBatches(successValidationList);
			HashMap<String,SITransferDto> formattedData = organiseForSITransferAPI(segregatedData,empId);
			
			Utility.LOG("Step->10: SITransferBulkupload: Bulk SI Transfer Process start for fileid:"+fileid);
			for (Entry<String,SITransferDto> entry : formattedData.entrySet()) {
				NewOrderDaoExt objDao=new NewOrderDaoExt();
				SITransferResult transferResult =objDao.processSITransfer(entry.getValue()) ;
				logSITransferBatchResult(entry.getKey(),segregatedData,transferResult,fileid);
			}
			Utility.LOG("Step->11: SITransferBulkupload: updating file status to file tracking for fileid:"+fileid);
			updateFileStatus(fileid,12); //comment for test purpose uncomment it 		
		}else{
			Utility.LOG("SITransferBulkupload: Step->7(Validated temp data fetching failed due to (File contains errors) for fileid:"+fileid);
			updateFileStatus(fileid,1);
		}
		
	}

	private static boolean insertLSIandLineitemToSITransferDetails(long fileid)throws Exception {
		Utility.LOG("Step->6: SITransferBulkupload:Inserting LSI and Lineitems in SI Transfer Details Table for fileid:"+fileid);
		Connection conn=null;
		PreparedStatement pstmt=null;
		String sql="INSERT INTO IOE.TM_SI_TRANSFERDETAILS(LOGICALSI,SERVICEPRODUCTID,BILLINGID,DISPATCHID)" +
				"SELECT LOGICAL_SI_NO,SERVICE_PRODUCT_ID,BCPID,DISPATCHID FROM IOE.TPOSERVICEMASTER SERVICE " +
				"INNER JOIN IOE.TDISCONNECTION_HISTORY TDISCONNHIS ON TDISCONNHIS.MAIN_SERVICEID=SERVICE.SERVICEID " +
				"INNER JOIN IOE.TPOSERVICEDETAILS TPOSERVICEDETAILS ON TPOSERVICEDETAILS.SERVICEPRODUCTID=TDISCONNHIS.SERVICE_PRODUCT_ID " +
				"AND TDISCONNHIS.PARENT_SPID <> 0 AND  IS_CHANGED_LSI=0 AND M6_FX_PROGRESS_STATUS='FX_BT_END' " +
				"AND ISDISCONNECTED=0 "+//AND ISSUSPENDED=0 " +
				"INNER JOIN BULKUPLOAD.TBULKUPLOAD_LSI_TRANSFER TEMP ON TEMP.LSINO=SERVICE.LOGICAL_SI_NO " +
				"AND TEMP.ERROR_LOG IS NULL AND FILEID=?  ORDER BY LOGICAL_SI_NO";
		
		try{
			
			conn=DbConnection.getConnectionObject();	
			pstmt=conn.prepareStatement(sql);				
			pstmt.setLong(1, fileid);					
			pstmt.execute();
					
		}catch(Exception e){
			Utility.LOG(true, false, e, "Exception occurred during executing insertLSIandLineitemToSITransferDetails for Fileid::"+fileid);
			throw e;
		}finally{
			DbConnection.closePreparedStatement(pstmt);
			DbConnection.freeConnection(conn);
		}
		return true;
	}
	private static boolean clearLSIandLineitemAlreadyInSITransferDetails(long fileid) throws Exception {
		Utility.LOG("Step->5: SITransferBulkupload:Deleting LSI and Lineitems already available in SI Transfer Details Table for fileid:"+fileid);
		Connection conn=null;
		PreparedStatement pstmt=null;
		String sql="DELETE FROM IOE.TM_SI_TRANSFERDETAILS WHERE (LOGICALSI,SERVICEPRODUCTID) IN( " +
				"SELECT LOGICAL_SI_NO,SERVICE_PRODUCT_ID FROM IOE.TPOSERVICEMASTER SERVICE INNER JOIN IOE.TDISCONNECTION_HISTORY TDISCONNHIS " +
				"ON TDISCONNHIS.MAIN_SERVICEID=SERVICE.SERVICEID INNER JOIN IOE.TPOSERVICEDETAILS TPOSERVICEDETAILS " +
				"ON TPOSERVICEDETAILS.SERVICEPRODUCTID=TDISCONNHIS.SERVICE_PRODUCT_ID " +
				"AND TDISCONNHIS.PARENT_SPID <> 0 AND  IS_CHANGED_LSI=0 AND M6_FX_PROGRESS_STATUS='FX_BT_END' " +
				"AND ISDISCONNECTED=0 "+//AND ISSUSPENDED=0 " +
				"INNER JOIN BULKUPLOAD.TBULKUPLOAD_LSI_TRANSFER TEMP ON TEMP.LSINO=SERVICE.LOGICAL_SI_NO " +
				"AND TEMP.ERROR_LOG IS NULL AND FILEID=? ORDER BY LOGICAL_SI_NO)";
		
		try{
			
			conn=DbConnection.getConnectionObject();	
			pstmt=conn.prepareStatement(sql);				
			pstmt.setLong(1, fileid);					
			pstmt.execute();
					
		}catch(Exception e){
			Utility.LOG(true, false, e, "Exception occurred during executing clearLSIandLineitemAlreadyInSITransferDetails for Fileid::"+fileid);
			throw e;
		}finally{
			DbConnection.closePreparedStatement(pstmt);
			DbConnection.freeConnection(conn);
		}
		return true;
	}
	private static void updateFileStatus(long fileid,int status) throws Exception {
		Utility.LOG("SITransferBulkupload:Updating Processing Status to Bulk Tracking table start for fileid:"+fileid);
		Connection conn=null;
		PreparedStatement pstmt=null;
		String sql="UPDATE BULKUPLOAD.TM_TTEMPLATEFILE_PROCESS SET FILESTATUSID=? WHERE FILEID=?";
		
		try{
			
			conn=DbConnection.getConnectionObject();	
			pstmt=conn.prepareStatement(sql);				
			pstmt.setInt(1, status);
			pstmt.setLong(2, fileid);			
			pstmt.execute();
					
		}catch(Exception e){
			Utility.LOG(true, false, e, "Exception occurred during executing updateFileStatus for Fileid::"+fileid);
			throw e;
		}finally{
			DbConnection.closePreparedStatement(pstmt);
			DbConnection.freeConnection(conn);
		}
		
	}
	private static void logSITransferBatchResult(
			String key,
			HashMap<String, ArrayList<BulkuploadDto>> segregatedData,
			SITransferResult transferResult,long fileid)throws Exception {
		
		Utility.LOG("SITransferBulkupload:Updating Error Log to Temporary table start for fileid:"+fileid);
		Connection conn=null;
		PreparedStatement pstmtSuccess=null;
		PreparedStatement pstmtError=null;
		String sqlerr="UPDATE BULKUPLOAD.TBULKUPLOAD_LSI_TRANSFER SET ERROR_LOG=? WHERE FILEID=? AND ID=?";
		String sqlSuccess="UPDATE BULKUPLOAD.TBULKUPLOAD_LSI_TRANSFER SET RESULT_LOG=? WHERE FILEID=? AND ID=?";
		
		boolean isBatchGeneratedErr=false,isBatchGenerated=false;
		try{
			
			conn=DbConnection.getConnectionObject();	
			pstmtSuccess=conn.prepareStatement(sqlSuccess);
			pstmtError=conn.prepareStatement(sqlerr);			
			
			boolean flagSuccess = false;
			boolean flagError = false;
			
			ArrayList<BulkuploadDto> lsiUploadedListOfOneBatch=segregatedData.get(key);
			Map<String,SITransferDto> mapResultedLSI=transferResult.getMapLsiToResult();
							
			for(BulkuploadDto buLsiRow:lsiUploadedListOfOneBatch){
				String lsinokey=buLsiRow.getLsiNo();
				
				flagSuccess = false;
				flagError = false;
				
				
				String errolog="",resultlog="";
				if(transferResult.getResultType().compareTo(SITransferResult.ResultType.BATCH_CREATION_ERROR)==0){
					errolog="Internal Error[Batch creation failed]";						
					flagError=true;
					
				}else if(transferResult.getResultType().compareTo(SITransferResult.ResultType.EXC_IN_MIDWAY)==0){
					if(mapResultedLSI==null || !mapResultedLSI.containsKey(lsinokey)){
						ExceptionLogDto excepObj=new ExceptionLogDto();						
						java.sql.Clob clobData = 
								com.ibm.db2.jcc.t2zos.DB2LobFactory.createClob(Utility.getStackTrace(transferResult.getException()));
						excepObj.setStackTraceMsg(clobData);
						excepObj.setAssociatedWith("BULKSITRANSFER");
						excepObj.setExceptionMessage(transferResult.getException().getMessage());
						
						long exceptionId=Utility.logErrorToExceptionMaster(excepObj);						
						errolog="Internal Error[Exception Id:"+exceptionId+"]";
						flagError=true;
						
					}else{
						SITransferDto lsiResultRow=mapResultedLSI.get(lsinokey);
						if(SITransferDto.SuccessFailure.FAILURE.toString().equalsIgnoreCase(lsiResultRow.getProgress())){	
							errolog="LSI No:"+lsiResultRow.getLogicalSINo()+" Error:"+lsiResultRow.getUserSpecificError();
							flagError=true;
							
						}else if(SITransferDto.SuccessFailure.SUCCESS.toString().equalsIgnoreCase(lsiResultRow.getProgress())){
							resultlog="Batch Id:"+lsiResultRow.getBatchID()+" Disconnected OrderNo:"+lsiResultRow.getBdisconorderno()+" New OrderNo:"+lsiResultRow.getNeworderno()+" Logical Si No:"+lsiResultRow.getLogicalSINo();
							flagSuccess=true;
							
						}		
					}
											
				}else if(transferResult.getResultType().compareTo(SITransferResult.ResultType.PROCESSED)==0){
					SITransferDto lsiResultRow=mapResultedLSI.get(lsinokey);
					if(SITransferDto.SuccessFailure.FAILURE.toString().equalsIgnoreCase(lsiResultRow.getProgress())){	
						errolog="LSI No:"+lsiResultRow.getLogicalSINo()+" Error:"+lsiResultRow.getUserSpecificError();
						flagError=true;
						
					}else if(SITransferDto.SuccessFailure.SUCCESS.toString().equalsIgnoreCase(lsiResultRow.getProgress())){
						resultlog="Batch Id:"+lsiResultRow.getBatchID()+" Disconnected OrderNo:"+lsiResultRow.getBdisconorderno()+" New OrderNo:"+lsiResultRow.getNeworderno()+" Logical Si No:"+lsiResultRow.getLogicalSINo();
						flagSuccess=true;
					}								
				}
				if(flagSuccess==true){
					pstmtSuccess.setString(1, resultlog);
					pstmtSuccess.setLong(2, fileid);
					pstmtSuccess.setLong(3, Long.valueOf(buLsiRow.getRowId()));
					pstmtSuccess.addBatch();
					isBatchGenerated=true;
				}else /*if(flagError==true)*/{
					pstmtError.setString(1, errolog.replaceAll("\n", " "));
					pstmtError.setLong(2, fileid);
					pstmtError.setLong(3, Long.valueOf(buLsiRow.getRowId()));
					pstmtError.addBatch();
					isBatchGeneratedErr=true;
				}
			}
				
			if(isBatchGenerated){
				int[] results=pstmtSuccess.executeBatch();				
				for(int i:results){
					if(i==PreparedStatement.EXECUTE_FAILED){						
						throw new Exception();
					}
				}
			}
			if(isBatchGeneratedErr){
				int[] results=pstmtError.executeBatch();				
				for(int i:results){
					if(i==PreparedStatement.EXECUTE_FAILED){						
						throw new Exception();
					}
				}
			}	
			
		}catch(Exception e){
			Utility.LOG_ITER(true, false, e, "Exception occurred during executing error log updation of SI Temporary table Batch::");
			throw e;
		}finally{			
			DbConnection.closePreparedStatement(pstmtError);
			DbConnection.closePreparedStatement(pstmtSuccess);
			DbConnection.freeConnection(conn);
		}
		
	}
	private static HashMap<String, SITransferDto> organiseForSITransferAPI(
			HashMap<String, ArrayList<BulkuploadDto>> segregatedData, long empId)throws Exception {
		
		Utility.LOG("Step->9: SITransferBulkupload:Start organise LSI according to existing SITransfer method input parameter");
		HashMap<String,SITransferDto> mapObjSITransfer=null;
		if(!segregatedData.isEmpty()){
			mapObjSITransfer=new HashMap<String, SITransferDto>();
			for(Entry<String,ArrayList<BulkuploadDto>> segdata:segregatedData.entrySet()){
				String key=segdata.getKey();
				String logicalLsi="",transferDate="",servicveId="",sourceParty="",targetParty="",accountId="",accountIdstr="";
				SITransferDto siTransferObj=new SITransferDto();
				
				boolean once=false;
				for(BulkuploadDto element:segdata.getValue()){
					logicalLsi=logicalLsi+element.getLsiNo()+",";
					servicveId=servicveId+element.getServiceId()+",";
					transferDate=transferDate+Utility.showDate_ddmmyyyy(element.getTransferedDate())+",";	
					accountIdstr=accountIdstr+element.getSrcAccountNo()+",";
					if(once==false){
						once=true;
						sourceParty=element.getSourcePartyNo();
						targetParty=element.getTargetPartyNo();	
						accountId=element.getAccountId();
					}
				}
				siTransferObj.setLogicalSistr(logicalLsi);
				siTransferObj.setDateOfTransfers(transferDate);
				siTransferObj.setServiceIdString(servicveId);
				siTransferObj.setAccountstr(accountIdstr);
				siTransferObj.setSourcePartyNo(sourceParty);
				siTransferObj.setTargetPartyNo(targetParty);
				siTransferObj.setAccountno(accountId);
				siTransferObj.setSourceModule("BULKSITRANSFER");
				siTransferObj.setRemarks("");
				siTransferObj.setError("");
				siTransferObj.setEmployeeid(Integer.valueOf(""+empId));//rahul T
				mapObjSITransfer.put(key, siTransferObj);
			}
		}
		return mapObjSITransfer;
	}
	private static HashMap<String, ArrayList<BulkuploadDto>> groupDataForBatches(ArrayList<BulkuploadDto> successValidationList)throws Exception {
		Utility.LOG("Step->8: SITransferBulkupload:Start creating Batch group");
		HashMap<String,ArrayList<BulkuploadDto>> mapGroupDataForBatch=null;
		ArrayList<BulkuploadDto> listSeperatePartyAndAccount=null;
		if(!successValidationList.isEmpty()){
			mapGroupDataForBatch=new HashMap<String,ArrayList<BulkuploadDto>>();
			for(BulkuploadDto bulkLSIRow:successValidationList){
				String key=bulkLSIRow.getSourcePartyNo()+"_"+bulkLSIRow.getTargetAccountNo();
				if(mapGroupDataForBatch.containsKey(key)){
					listSeperatePartyAndAccount=mapGroupDataForBatch.get(key);
					listSeperatePartyAndAccount.add(bulkLSIRow);	//bydefault list is already in the map				
				}else{
					listSeperatePartyAndAccount=new ArrayList<BulkuploadDto>();
					listSeperatePartyAndAccount.add(bulkLSIRow);
					mapGroupDataForBatch.put(key, listSeperatePartyAndAccount);
				}
			}
		}
		return mapGroupDataForBatch;
	}
	private static ArrayList<BulkuploadDto> loadValidationSuccessDataWithOtherAttributes(long fileid)throws Exception {
		
		Utility.LOG("Step->7: SITransferBulkupload:fetching sucess validate Temp data for fileid:"+fileid);
		ArrayList<BulkuploadDto>  listValidationSuccessData=new ArrayList<BulkuploadDto>();
		String sql="SELECT ID, SOURCE_PARTY_NO,TARGET_PARTY_NO,TARGET_ACCOUNT_NO,LSINO,TRANSFERDATE,BCPID,DISPATCHID,SERVICE.SERVICEID,ACCOUNT.ACCOUNTID,mas.accountid as src_accountid " +
				"FROM BULKUPLOAD.TBULKUPLOAD_LSI_TRANSFER TEMP INNER JOIN IOE.TM_ACCOUNT ACCOUNT " +
				"ON ACCOUNT.CRMACCOUNTNO=TEMP.TARGET_ACCOUNT_NO INNER JOIN IOE.TPOSERVICEMASTER SERVICE " +
				"ON SERVICE.LOGICAL_SI_NO=TEMP.LSINO AND IS_CHANGED_LSI=0 AND M6_FX_PROGRESS_STATUS='FX_BT_END' " +
				"inner join ioe.tpomaster mas on mas.orderno=service.orderno WHERE FILEID=? AND ERROR_LOG IS NULL";
		PreparedStatement pstmt=null;
		Connection conn=null;
		ResultSet rsTempdata=null;
		BulkuploadDto objDto=null;
		try{
			conn=DbConnection.getConnectionObject();
			pstmt=conn.prepareStatement(sql);
			pstmt.setLong(1,fileid);
			rsTempdata=pstmt.executeQuery();
			//Set<String> uniqueLSINo=new HashSet<String>();
			while(rsTempdata.next()){
				objDto=new BulkuploadDto();
				
				
				objDto.setFileID(String.valueOf(fileid));
				objDto.setRowId(rsTempdata.getString("ID"));
				objDto.setSourcePartyNo(rsTempdata.getString("SOURCE_PARTY_NO"));
				objDto.setTargetPartyNo(rsTempdata.getString("TARGET_PARTY_NO"));
				objDto.setTargetAccountNo(rsTempdata.getString("TARGET_ACCOUNT_NO"));
				
				String lsiNo=rsTempdata.getString("LSINO").trim();
				/*if(uniqueLSINo.contains(lsiNo)){
					throw new Exception("Duplicate Latest Services found on Single LSI[Hint: Data may be not correct] for LSI:"+lsiNo);
				}
				
				uniqueLSINo.add(lsiNo);*/
				
				objDto.setLsiNo(lsiNo);				
				objDto.setTransferedDate(rsTempdata.getString("TRANSFERDATE"));
				objDto.setBcpId(rsTempdata.getString("BCPID"));
				objDto.setDispatchId(rsTempdata.getString("DISPATCHID"));
				objDto.setServiceId(rsTempdata.getString("SERVICEID"));
				objDto.setAccountId(rsTempdata.getString("ACCOUNTID"));
				
				objDto.setSrcAccountNo(rsTempdata.getString("SRC_ACCOUNTID"));
				
				listValidationSuccessData.add(objDto);
			}
		}catch(Exception e){
			throw e;
		}finally{
			DbConnection.closeResultset(rsTempdata);
			DbConnection.closePreparedStatement(pstmt);
			DbConnection.freeConnection(conn);
		}
		return listValidationSuccessData;		
	}
	private static void logSITransferMasterVakidationErrors(
			ArrayList<BulkuploadDto> listtempData, long fileid)throws Exception {
		
		Utility.LOG("Step-4: SITransferBulkupload:Updating Error Log to Temporary table start for fileid:"+fileid);
		Connection conn=null;
		PreparedStatement pstmt=null;
		String sql="UPDATE BULKUPLOAD.TBULKUPLOAD_LSI_TRANSFER SET ERROR_LOG=? WHERE FILEID=? AND ID=?";
		boolean isBatchGenerated=false;		
		try{
			
			conn=DbConnection.getConnectionObject();	
			pstmt=conn.prepareStatement(sql);
			for(BulkuploadDto element:listtempData){
				if(element.getErrors().length() > 0){					
					pstmt.setString(1, element.getErrors().replaceAll("\n", " "));
					pstmt.setLong(2, fileid);
					pstmt.setLong(3, Long.valueOf(element.getRowId()));
					pstmt.addBatch();
					isBatchGenerated=true;
				}						
			}
			if(isBatchGenerated){
				int[] results=pstmt.executeBatch();				
				for(int i:results){
					if(i==PreparedStatement.EXECUTE_FAILED){						
						throw new Exception();
					}
				}
			}
		}catch(Exception e){
			Utility.LOG_ITER(true, false, e, "Exception occurred during executing error log updation of SI Temporary table Batch::");
			throw e;
		}finally{
			DbConnection.closePreparedStatement(pstmt);
			DbConnection.freeConnection(conn);
		}
	}		
	
	private static void bulkSITransferMasterDataValidation(ArrayList<BulkuploadDto> listtempData,long fileid, Integer respId) throws Exception {
		
		Utility.LOG("Step->3: SITransferBulkupload:Master data validation start for fileid:"+fileid);
		validateBulkSITransfer_TransferDate(listtempData,fileid);
		validateBulkSITransferSourcePartyNoExists(listtempData,fileid);
		validateBulkSITransferTargetPartyNoExists(listtempData,fileid);
		validateBulkSITransferTargetAccountNoExists(listtempData,fileid);
		validateBulkSITransferLsiNoExists(listtempData,fileid);
		validateBulkSITransferDuplicateLSINo(listtempData,fileid);
		validateBulkSITransferFreeLSINo(listtempData,fileid);//		ok
		validateBulkSITransferNonUsageLSIandLinkingLSI(listtempData,fileid);
		validateBulkSITransferSourcePartyAndLSI(listtempData,fileid);//
		validateBulkSITransferTargetPartyAndTargetAccount(listtempData,fileid);//
		validateBulkSITransferSourceAndTargetAccountM6ShortCode(listtempData,fileid);//
		validateBulkSITransferBCPIDExists(listtempData,fileid);
		validateBulkSITransferBCPIDWithAccountNo(listtempData,fileid);//
		validateBulkSITransferMandatoryDispatchId(listtempData,fileid);
		validateBulkSITransferDispatchIdExists(listtempData,fileid);
		validateBulkSITransferDispatchIdWithAccount(listtempData,fileid);//
		//Shubhranshu, Drop&Carry Validation On ServiceFlavour
		validateBulkSITransferLSIServiceFlavour(listtempData,fileid);
		//Drop&Carry Validation Ends
		validateBulkSIRespIdAndLSI(listtempData,fileid,respId);
		validateBulkSIDuplicateServiceOnSingleIsLSIExist(listtempData,fileid);
		Utility.LOG("Step->3: SITransferBulkupload:Master data validation end for fileid:"+fileid);
		
		
		//TODO validate based on Exists
			//also validateBulkSITransferSourcePartyAndLSI based freeLSINo pass and existence
		//TODO validateBulkSITransferSourceAndTargetAccountM6ShortCode to include latest service check
			//also this based on freeLSINo and existence 
		
	}
	private static void validateBulkSITransferLSIServiceFlavour(ArrayList<BulkuploadDto> listtempData, long fileid)throws Exception
		{	
				final String sqlgetListOfLSIDropNCarry=" SELECT TEMP.LSINO FROM BULKUPLOAD.TBULKUPLOAD_LSI_TRANSFER TEMP " +
																						" INNER JOIN IOE.TPOSERVICEMASTER TSM " +
																						" ON TSM.LOGICAL_SI_NO=TEMP.LSINO " +
																						" INNER JOIN IOE.TPOSERVICEMASTER_EXTENDED TSEM " +
																						" ON TSM.SERVICEID=TSEM.SERVICEID " +
																						" WHERE TSEM.SERVICE_FLAVOUR='DC' AND TEMP.FILEID=? ";			
				Connection conn=null;
				PreparedStatement pst=null;
				ResultSet rst=null;
				ArrayList<String>lsiListDropNCarry=new ArrayList<String>();
			
				try
				{
					conn=DbConnection.getConnectionObject();
					pst=conn.prepareStatement(sqlgetListOfLSIDropNCarry);
					pst.setLong(1, fileid);
					rst=pst.executeQuery();
					
						while(rst.next())
						{
							lsiListDropNCarry.add(rst.getString("LSINO").trim());
						}
				}
				catch(SQLException sq)
				{
					Utility.LOG(sq);
				}
				finally
				{
					DbConnection.closeResultset(rst);
					DbConnection.closePreparedStatement(pst);
					DbConnection.freeConnection(conn);
				}
				
				for(BulkuploadDto element:listtempData)
				{
					if(lsiListDropNCarry.contains(element.getLsiNo().trim())==true && element.isLsiNotPresent()==false){
						String errors=element.getErrors();
						element.setErrors(errors+" SI Transfer Cannot be performed :Hint(LSI is of type Drop&Carry ) , ");
					}
				}
		}
	private static void validateBulkSIDuplicateServiceOnSingleIsLSIExist(
			ArrayList<BulkuploadDto> listtempData, long fileid) throws Exception{
		
		Utility.LOG("Step->7: CurrencyTransferBulkupload:fetching sucess validate Temp data for fileid:"+fileid);
		String sql="SELECT COUNT(*) AS FOUNDDUPLICATELSI, LSINO FROM BULKUPLOAD.TBULKUPLOAD_LSI_TRANSFER TEMP INNER JOIN IOE.TPOSERVICEMASTER SERVICE " +
				"ON SERVICE.LOGICAL_SI_NO=TEMP.LSINO " +
				" AND IS_CHANGED_LSI=0 WHERE FILEID=? GROUP BY LSINO HAVING COUNT(*) > 1";
		PreparedStatement pstmt=null;
		Connection conn=null;
		Set<String> uniqueLSINo=new HashSet<String>();
		ResultSet rsTempdata=null;
		try{
			conn=DbConnection.getConnectionObject();
			pstmt=conn.prepareStatement(sql);
			pstmt.setLong(1,fileid);
			rsTempdata=pstmt.executeQuery();
			while(rsTempdata.next()){
				uniqueLSINo.add(rsTempdata.getString("LSINO").trim());
			}
		}catch(Exception e){
			throw e;
		}finally{
			DbConnection.closeResultset(rsTempdata);
			DbConnection.closePreparedStatement(pstmt);
			DbConnection.freeConnection(conn);
		}
		for(BulkuploadDto element:listtempData){
			if(uniqueLSINo.contains(element.getLsiNo().trim())==true){
				String errors=element.getErrors();
				element.setErrors(errors+" Duplicate Latest Services found on Single LSI[Hint: Data may be not correct]");
			}
		}
				
	}
	private static void validateBulkSIRespIdAndLSI(
			ArrayList<BulkuploadDto> listtempData, long fileid, Integer respId) throws Exception{

		Set<String> lsino=new HashSet<String>();
		String sql="SELECT DISTINCT LSINO FROM BULKUPLOAD.TBULKUPLOAD_LSI_TRANSFER TEMP INNER JOIN " +
				"IOE.TPOSERVICEMASTER SERVICE ON SERVICE.LOGICAL_SI_NO=TEMP.LSINO AND" +
				" IS_CHANGED_LSI=0 AND M6_FX_PROGRESS_STATUS='FX_BT_END' AND FILEID=? INNER JOIN IOE.TPOMASTER TPOMASTER " +
				"ON TPOMASTER.ORDERNO=SERVICE.ORDERNO INNER JOIN IOE.TM_ACCOUNT ACCOUNT ON " +
				"TPOMASTER.ACCOUNTID=ACCOUNT.ACCOUNTID  INNER JOIN " +
				" IOE.TM_RESP_SEGMENT_MAPPING AS RSM ON RSM.CUST_SEGMENT_ID=ACCOUNT.CUSTOMERSEGMENT " +
				"where RESP_SEG_ID=?	";

		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rslsino=null;
		try{
			conn=DbConnection.getConnectionObject();
			pstmt=conn.prepareStatement(sql);
			pstmt.setLong(1, fileid);
			pstmt.setLong(2, respId);
			rslsino=pstmt.executeQuery();
			while(rslsino.next()){
				lsino.add(rslsino.getString("LSINO").trim());
			}
		}finally{
			DbConnection.closeResultset(rslsino);
			DbConnection.closePreparedStatement(pstmt);
			DbConnection.freeConnection(conn);
		}
		
		
		for(BulkuploadDto element:listtempData){
			if(lsino.contains(element.getLsiNo().trim())==false){
				String errors=element.getErrors();
				element.setErrors(errors+" LSI doesn't belong to Customer Segment for Resposibility.,");
			}
		}
	
		
		
		
	}
	private static void validateBulkSITransferNonUsageLSIandLinkingLSI(
			ArrayList<BulkuploadDto> listtempData, long fileid) throws Exception {

		Set<String> usageandlinkinglsi=new HashSet<String>();
		String sql="SELECT DISTINCT(LOGICAL_SI_NO) as LOGICAL_SI_NO FROM IOE.TPOSERVICEMASTER TP "+ 
				"INNER JOIN BULKUPLOAD.TBULKUPLOAD_LSI_TRANSFER TEMP1 ON TEMP1.LSINO=TP.LOGICAL_SI_NO AND FILEID=? "+
				"INNER JOIN IOE.TPOSERVICEDETAILS TS ON TS.SERVICEID=TP.SERVICEID "+
				"INNER JOIN IOE.TBILLING_INFO TB ON TB.SERVICEPRODUCTID=TS.SERVICEPRODUCTID AND TB.ISUSAGE=1 AND TS.ISDISCONNECTED=0 "+
				"UNION ALL "+
				//"SELECT DISTINCT ATTVALUE as LOGICAL_SI_NO FROM IOE.TPRODUCTLINEATTVALUE TPRODUCTLINEATTVALUE "+
				//"INNER JOIN BULKUPLOAD.TBULKUPLOAD_LSI_TRANSFER TEMP2 ON TEMP2.LSINO=TPRODUCTLINEATTVALUE.ATTVALUE "+
				//"AND ATTMASTERID=3947 AND FILEID=? "+
				//"UNION ALL "+
				"SELECT DISTINCT(LOGICAL_SI_NO) as LOGICAL_SI_NO FROM IOE.TPOSERVICEMASTER SERVICE "+
				"INNER JOIN BULKUPLOAD.TBULKUPLOAD_LSI_TRANSFER TEMP3 ON TEMP3.LSINO=SERVICE.LOGICAL_SI_NO AND FILEID=? "+
				"INNER JOIN IOE.TPOSERVICEDETAILS TS ON TS.SERVICEID=SERVICE.SERVICEID "+
				"INNER JOIN IOE.TPRODUCTLINEATTVALUE TP ON TP.SERVICEPRODUCTID=TS.SERVICEPRODUCTID "+
				//"AND (TP.ATTMASTERID = 4093 OR TP.ATTMASTERID = 3969 OR TP.ATTMASTERID=5000327) "+
				"AND (TP.ATTMASTERID in (SELECT PRIMARY_ATTMASTERID FROM ioe.TM_SERVICE_LINKING)) "+
				"AND TP.ATTVALUE IS NOT NULL AND TP.ATTVALUE <> '' ";
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rslsi=null;
		try{
			conn=DbConnection.getConnectionObject();
			pstmt=conn.prepareStatement(sql);
			pstmt.setLong(1, fileid);
			pstmt.setLong(2, fileid);
			rslsi=pstmt.executeQuery();
			while(rslsi.next()){
				usageandlinkinglsi.add(rslsi.getString("LOGICAL_SI_NO").trim());
			}
		
		}finally{
			DbConnection.closeResultset(rslsi);
			DbConnection.closePreparedStatement(pstmt);
			DbConnection.freeConnection(conn);
		}
		
		for(BulkuploadDto element:listtempData){
			if(usageandlinkinglsi.contains(element.getLsiNo().trim())==true && element.isLsiNotPresent()==false){
				String errors=element.getErrors();
				element.setErrors(errors+" Usage LSI or Linked LSI are not allowed.");
			}
		}
		
		
	}
	private static void validateBulkSITransfer_TransferDate(
			ArrayList<BulkuploadDto> listtempData, long fileid)throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		Date date=new Date();
		String currentDate=sdf.format(date);
		for(BulkuploadDto element:listtempData){
			
			if(Utility.dateCompare(element.getTransferedDate(),currentDate,"dd-MMM-yyyy")==1){
				String errors=element.getErrors();
				element.setErrors(errors+" Transfer Date can not be greater than current date.");
			}			
		}		
	}
	private static void validateBulkSITransferMandatoryDispatchId(
			ArrayList<BulkuploadDto> listtempData, long fileid) throws Exception {
		Set<String> bcpids=new HashSet<String>();
		String sql="SELECT distinct TEMP.ID,TEMP.LSINO FROM BULKUPLOAD.TBULKUPLOAD_LSI_TRANSFER TEMP " +
						"INNER JOIN IOE.TPOSERVICEMASTER SERVICE ON TEMP.LSINO=SERVICE.LOGICAL_SI_NO " +
							"AND SERVICE.IS_CHANGED_LSI=0 AND SERVICE.M6_FX_PROGRESS_STATUS='FX_BT_END' " +
						"INNER JOIN IOE.TDISCONNECTION_HISTORY DISCONN_HIS ON DISCONN_HIS.MAIN_SERVICEID=SERVICE.SERVICEID " +
							"AND DISCONN_HIS.SERVICE_PRODUCT_ID <> 0 AND DISCONN_HIS.IS_DISCONNECTED=0 " +
						"INNER JOIN IOE.TPOSERVICEDETAILS TPOSERVICEDETAILS ON TPOSERVICEDETAILS.SERVICEPRODUCTID=DISCONN_HIS.SERVICE_PRODUCT_ID " +
						"INNER JOIN IOE.TSERVICETYPEDETAIL LINEMASTER ON LINEMASTER.SERVICEDETAILID=TPOSERVICEDETAILS.SERVICEDETAILID " +
							"AND LINEMASTER.HARDWAREINFO=1 AND FILEID=? " +
					"WHERE (TEMP.DISPATCHID IS NULL OR TEMP.DISPATCHID ='' OR TEMP.DISPATCHID='0')";
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rslsi=null;
		try{
			conn=DbConnection.getConnectionObject();
			pstmt=conn.prepareStatement(sql);
			pstmt.setLong(1, fileid);
			rslsi=pstmt.executeQuery();
			while(rslsi.next()){
				bcpids.add(rslsi.getString("LSINO").trim());
			}
		
		}finally{
			DbConnection.closeResultset(rslsi);
			DbConnection.closePreparedStatement(pstmt);
			DbConnection.freeConnection(conn);
		}
		
		for(BulkuploadDto element:listtempData){
			if(bcpids.contains(element.getLsiNo().trim())==true){
				String errors=element.getErrors();
				element.setErrors(errors+" Dispacth Id is mandatory for hardware logical lsi");
				element.setDispatchIdAvailableInSheet(false);
			}else{
				if(element.getDispatchId()!=null && !"0".equals(element.getDispatchId()) && !("".equals(element.getDispatchId()))){
					//TODO comments
					element.setDispatchIdAvailableInSheet(true);
				}
				
			}
		}
		
	}
	private static void validateBulkSITransferDuplicateLSINo(
			ArrayList<BulkuploadDto> listtempData, long fileid) throws Exception {
		Set<String> lsino=new HashSet<String>();
		String sql="SELECT COUNT(*) AS FOUNDDUPLICATELSI,LSINO FROM BULKUPLOAD.TBULKUPLOAD_LSI_TRANSFER WHERE FILEID=? GROUP BY LSINO HAVING COUNT(*) > 1 ";
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rslsino=null;
		try{
			conn=DbConnection.getConnectionObject();
			pstmt=conn.prepareStatement(sql);
			pstmt.setLong(1, fileid);
			rslsino=pstmt.executeQuery();
			while(rslsino.next()){
				lsino.add(rslsino.getString("LSINO").trim());
			}
		}finally{
			DbConnection.closeResultset(rslsino);
			DbConnection.closePreparedStatement(pstmt);
			DbConnection.freeConnection(conn);
		}	
		
		for(BulkuploadDto element:listtempData){
			if(lsino.contains(element.getLsiNo().trim())==true){
				String errors=element.getErrors();
				element.setErrors(errors+" Duplicate LSI No found.");
			}
		}
	}
	private static void validateBulkSITransferDispatchIdExists(
			ArrayList<BulkuploadDto> listtempData, long fileid) throws Exception {
		
		Set<String> dispatchid=new HashSet<String>();
		String sql="SELECT TEMP.DISPATCHID FROM BULKUPLOAD.TBULKUPLOAD_LSI_TRANSFER TEMP " +
						"INNER JOIN IOE.TDISPATCH_ADDRESS_MSTR DISPATCHADDRESS ON TEMP.DISPATCHID=DISPATCHADDRESS.DISPATCH_ADDRESS_CODE " +
							"AND FILEID=? ";				
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rsdispatchid=null;
		try{
			conn=DbConnection.getConnectionObject();
			pstmt=conn.prepareStatement(sql);
			pstmt.setLong(1, fileid);
			rsdispatchid=pstmt.executeQuery();
			while(rsdispatchid.next()){
				dispatchid.add(rsdispatchid.getString("DISPATCHID").trim());
			}
		}finally{
			DbConnection.closeResultset(rsdispatchid);
			DbConnection.closePreparedStatement(pstmt);
			DbConnection.freeConnection(conn);
		}		
		
		for(BulkuploadDto element:listtempData){
			if(dispatchid.contains(element.getDispatchId().trim())==false && element.isDispatchIdAvailableInSheet()==true){
				String errors=element.getErrors();
				element.setErrors(errors+" Dispatch Id does not exist,");
				element.setDispatchIdNotPresent(true);
			}
		}			
	}
	private static void validateBulkSITransferBCPIDWithAccountNo(
			ArrayList<BulkuploadDto> listtempData, long fileid) throws Exception {
		
		Set<String> bcpids=new HashSet<String>();
		String sql="SELECT TEMP.BCPID FROM BULKUPLOAD.TBULKUPLOAD_LSI_TRANSFER TEMP INNER JOIN IOE.TBCP_ADDRESS_MSTR BCPADDRESS " +
				"ON TEMP.BCPID=BCPADDRESS.BCP_ID INNER JOIN IOE.TM_ACCOUNT ACCOUNT " +
				"ON ACCOUNT.ACCOUNTID=BCPADDRESS.ACCOUNTID AND TEMP.TARGET_ACCOUNT_NO=ACCOUNT.CRMACCOUNTNO AND FILEID=?";
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rsbcpid=null;
		try{
			conn=DbConnection.getConnectionObject();
			pstmt=conn.prepareStatement(sql);
			pstmt.setLong(1, fileid);
			rsbcpid=pstmt.executeQuery();
			while(rsbcpid.next()){
				bcpids.add(rsbcpid.getString("BCPID").trim());
			}
		
		}finally{
			DbConnection.closeResultset(rsbcpid);
			DbConnection.closePreparedStatement(pstmt);
			DbConnection.freeConnection(conn);
		}
		
		for(BulkuploadDto element:listtempData){
			if(bcpids.contains(element.getBcpId().trim())==false && element.isBcpIdNotPresent()==false && element.isTgtAccountNotPresent()==false){
				String errors=element.getErrors();
				element.setErrors(errors+" BCP Id does not match with Target Account No,");
			}
		}
			
		
	}
	private static void validateBulkSITransferBCPIDExists(ArrayList<BulkuploadDto> listtempData,
			long fileid) throws Exception {
		Set<String> bcpids=new HashSet<String>();
		String sql="SELECT TEMP.BCPID FROM BULKUPLOAD.TBULKUPLOAD_LSI_TRANSFER TEMP INNER JOIN IOE.TBCP_ADDRESS_MSTR BCPADDRESS " +
				"ON TEMP.BCPID=BCPADDRESS.BCP_ID AND FILEID=?";
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rsbcpid=null;
		try{
			conn=DbConnection.getConnectionObject();
			pstmt=conn.prepareStatement(sql);
			pstmt.setLong(1, fileid);
			rsbcpid=pstmt.executeQuery();
			while(rsbcpid.next()){
				bcpids.add(rsbcpid.getString("BCPID").trim());
			}
			
		}finally{
			DbConnection.closeResultset(rsbcpid);
			DbConnection.closePreparedStatement(pstmt);
			DbConnection.freeConnection(conn);
		}
		
		
		for(BulkuploadDto element:listtempData){
			if(bcpids.contains(element.getBcpId().trim())==false){
				String errors=element.getErrors();
				element.setErrors(errors+" BCP Id does not exist,");
				element.setBcpIdNotPresent(true);
			}
		}
	}
	private static void validateBulkSITransferDispatchIdWithAccount(
			ArrayList<BulkuploadDto> listtempData, long fileid)throws Exception {
		Set<String> dispatchid=new HashSet<String>();
		String sql="SELECT TEMP.DISPATCHID FROM BULKUPLOAD.TBULKUPLOAD_LSI_TRANSFER TEMP INNER JOIN IOE.TDISPATCH_ADDRESS_MSTR DISPATCHADDRESS " +
				"ON TEMP.DISPATCHID=DISPATCHADDRESS.DISPATCH_ADDRESS_CODE INNER JOIN IOE.TM_ACCOUNT ACCOUNT " +
				"ON ACCOUNT.ACCOUNTID=DISPATCHADDRESS.ACCOUNTID AND TEMP.TARGET_ACCOUNT_NO=ACCOUNT.CRMACCOUNTNO AND FILEID=?";				
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rsdispatchid=null;
		try{
			conn=DbConnection.getConnectionObject();
			pstmt=conn.prepareStatement(sql);
			pstmt.setLong(1, fileid);
			rsdispatchid=pstmt.executeQuery();
			while(rsdispatchid.next()){
				dispatchid.add(rsdispatchid.getString("DISPATCHID").trim());
			}
		}finally{
			DbConnection.closeResultset(rsdispatchid);
			DbConnection.closePreparedStatement(pstmt);
			DbConnection.freeConnection(conn);
		}
			
		for(BulkuploadDto element:listtempData){
			if(dispatchid.contains(element.getDispatchId().trim())==false && element.isDispatchIdAvailableInSheet()==true && element.isDispatchIdNotPresent()==false){
				String errors=element.getErrors();
				element.setErrors(errors+" Dispatch Id does not match with Target Account No,");
			}
		}
	}
	private static void validateBulkSITransferFreeLSINo(ArrayList<BulkuploadDto> listtempData, long fileid) throws Exception {
		Set<String> lsino=new HashSet<String>();
		String sql="SELECT LSINO FROM BULKUPLOAD.TBULKUPLOAD_LSI_TRANSFER TEMP INNER JOIN IOE.TPOSERVICEMASTER SERVICE " +
				"ON SERVICE.LOGICAL_SI_NO=TEMP.LSINO AND FILEID=? WHERE SERVICE.IS_CHANGED_LSI=0 AND M6_FX_PROGRESS_STATUS='FX_BT_END'";
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rslsino=null;
		try{
			conn=DbConnection.getConnectionObject();
			pstmt=conn.prepareStatement(sql);
			pstmt.setLong(1, fileid);
			rslsino=pstmt.executeQuery();
			while(rslsino.next()){
				lsino.add(rslsino.getString("LSINO").trim());
			}
		}finally{
			DbConnection.closeResultset(rslsino);
			DbConnection.closePreparedStatement(pstmt);
			DbConnection.freeConnection(conn);
		}	
			
		for(BulkuploadDto element:listtempData){
			if(lsino.contains(element.getLsiNo().trim())==false && element.isLsiNotPresent()==false){
				String errors=element.getErrors();
				element.setErrors(errors+" LSI No doesn't available for SI Transfer:Hint(LSI No may be in draft or not free),");
			}
		}
	}
	private static void validateBulkSITransferSourceAndTargetAccountM6ShortCode(ArrayList<BulkuploadDto> listtempData, long fileid) throws Exception {
		
		Set<String> targetAccountNo=new HashSet<String>();
		String sql="SELECT DISTINCT TARGET_ACCOUNT_NO FROM BULKUPLOAD.TBULKUPLOAD_LSI_TRANSFER TEMP INNER JOIN IOE.TPOSERVICEMASTER SERVICE " +
				"ON SERVICE.LOGICAL_SI_NO=TEMP.LSINO INNER JOIN IOE.TPOMASTER TPOMASTER " +
				"ON TPOMASTER.ORDERNO=SERVICE.ORDERNO  INNER JOIN IOE.TM_ACCOUNT SOURCE_ACCOUNT " +
				"ON TPOMASTER.ACCOUNTID=SOURCE_ACCOUNT.ACCOUNTID INNER JOIN IOE.TM_ACCOUNT TARGET_ACCOUNT " +
				"ON TEMP.TARGET_ACCOUNT_NO=TARGET_ACCOUNT.CRMACCOUNTNO AND TARGET_ACCOUNT.M6SHORT_CODE=SOURCE_ACCOUNT.M6SHORT_CODE AND FILEID=?";
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rsltargetAccountNo=null;
		try{
			conn=DbConnection.getConnectionObject();
			pstmt=conn.prepareStatement(sql);
			pstmt.setLong(1, fileid);
			rsltargetAccountNo=pstmt.executeQuery();
			while(rsltargetAccountNo.next()){
				targetAccountNo.add(rsltargetAccountNo.getString("TARGET_ACCOUNT_NO").trim());
			}
		}finally{
			DbConnection.closeResultset(rsltargetAccountNo);
			DbConnection.closePreparedStatement(pstmt);
			DbConnection.freeConnection(conn);
		}	
		for(BulkuploadDto element:listtempData){
			if(targetAccountNo.contains(element.getTargetAccountNo().trim())==false && element.isTgtAccountNotPresent()==false && element.isLsiNotPresent()==false){
				String errors=element.getErrors();
				element.setErrors(errors+" M6 Short code of Target Account No doesn't match with M6 Short code of Source Account No,");
			}
		}
	}
	private static void validateBulkSITransferTargetPartyAndTargetAccount(ArrayList<BulkuploadDto> listtempData, long fileid)throws Exception {
		
		Set<String> targetAccountNo=new HashSet<String>();
		String sql="SELECT TARGET_ACCOUNT_NO FROM BULKUPLOAD.TBULKUPLOAD_LSI_TRANSFER TEMP INNER JOIN IOE.TM_ACCOUNT ACCOUNT " +
				"ON TEMP.TARGET_ACCOUNT_NO=ACCOUNT.CRMACCOUNTNO AND  FILEID=? INNER JOIN IOE.TM_PARTY PARTY " +
				"ON PARTY.PARTY_ID=ACCOUNT.PARTY_ID AND TEMP.TARGET_PARTY_NO=PARTY.PARTY_NO";
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rsltargetAccountNo=null;
		try{
			conn=DbConnection.getConnectionObject();
			pstmt=conn.prepareStatement(sql);
			pstmt.setLong(1, fileid);
			rsltargetAccountNo=pstmt.executeQuery();
			while(rsltargetAccountNo.next()){
				targetAccountNo.add(rsltargetAccountNo.getString("TARGET_ACCOUNT_NO").trim());
			}
			
		}finally{
			DbConnection.closeResultset(rsltargetAccountNo);
			DbConnection.closePreparedStatement(pstmt);
			DbConnection.freeConnection(conn);
		}
		
		for(BulkuploadDto element:listtempData){
			if(targetAccountNo.contains(element.getTargetAccountNo().trim())==false && element.isTgtPartyNotPresent()==false && element.isTgtAccountNotPresent()==false){
				String errors=element.getErrors();
				element.setErrors(errors+" Target Party No doesn't match with Target Account No,");
			}
		}
	}
	private static void validateBulkSITransferSourcePartyAndLSI(
			ArrayList<BulkuploadDto> listtempData, long fileid)throws Exception {
		Set<String> lsino=new HashSet<String>();
		String sql="SELECT DISTINCT LSINO FROM BULKUPLOAD.TBULKUPLOAD_LSI_TRANSFER TEMP INNER JOIN IOE.TPOSERVICEMASTER SERVICE " +
				"ON SERVICE.LOGICAL_SI_NO=TEMP.LSINO AND IS_CHANGED_LSI=0 AND M6_FX_PROGRESS_STATUS='FX_BT_END' " +
				"AND FILEID=? INNER JOIN IOE.TPOMASTER TPOMASTER " +
				"ON TPOMASTER.ORDERNO=SERVICE.ORDERNO INNER JOIN IOE.TM_ACCOUNT ACCOUNT " +
				"ON TPOMASTER.ACCOUNTID=ACCOUNT.ACCOUNTID  INNER JOIN IOE.TM_PARTY PARTY " +
				"ON PARTY.PARTY_ID=ACCOUNT.PARTY_ID AND TEMP.SOURCE_PARTY_NO=PARTY.PARTY_NO";
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rslsino=null;
		try{
			conn=DbConnection.getConnectionObject();
			pstmt=conn.prepareStatement(sql);
			pstmt.setLong(1, fileid);
			rslsino=pstmt.executeQuery();
			while(rslsino.next()){
				lsino.add(rslsino.getString("LSINO").trim());
			}
		}finally{
			DbConnection.closeResultset(rslsino);
			DbConnection.closePreparedStatement(pstmt);
			DbConnection.freeConnection(conn);
		}
		
		
		for(BulkuploadDto element:listtempData){
			if(lsino.contains(element.getLsiNo().trim())==false && element.isSrcPartyNotPresent()==false && element.isLsiNotPresent()==false){
				String errors=element.getErrors();
				element.setErrors(errors+" Source Party No doesn't match with Source LSI,");
			}
		}
	}
	private static void validateBulkSITransferLsiNoExists(ArrayList<BulkuploadDto> listtempData,
			long fileid)throws Exception {
		
		Set<String> lsino=new HashSet<String>();
		String sql="SELECT DISTINCT LOGICAL_SI_NO FROM IOE.TPOSERVICEMASTER SERVICE " +
				"INNER JOIN BULKUPLOAD.TBULKUPLOAD_LSI_TRANSFER TEMPTABLE ON SERVICE.LOGICAL_SI_NO=TEMPTABLE.LSINO AND FILEID=?";
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rslsino=null;
		try{
			conn=DbConnection.getConnectionObject();
			pstmt=conn.prepareStatement(sql);
			pstmt.setLong(1, fileid);
			rslsino=pstmt.executeQuery();
			while(rslsino.next()){
				lsino.add(rslsino.getString("LOGICAL_SI_NO").trim());
			}
			
		}finally{
			DbConnection.closeResultset(rslsino);
			DbConnection.closePreparedStatement(pstmt);
			DbConnection.freeConnection(conn);
		}	
		
		for(BulkuploadDto element:listtempData){
			if(lsino.contains(element.getLsiNo().trim())==false){
				String errors=element.getErrors();
				element.setErrors(errors+" LSI No doesn't exist,");
				element.setLsiNotPresent(true);
			}
		}
			
	}
	private static void validateBulkSITransferTargetAccountNoExists(ArrayList<BulkuploadDto> listtempData, long fileid)throws Exception {
		
		Set<String> targetAccountNo=new HashSet<String>();
		String sql="SELECT CRMACCOUNTNO AS TARGET_ACCOUNT_NO FROM IOE.TM_ACCOUNT TARGETACCOUNT " +
				"INNER JOIN BULKUPLOAD.TBULKUPLOAD_LSI_TRANSFER TEMPTABLE ON TARGETACCOUNT.CRMACCOUNTNO=TEMPTABLE.TARGET_ACCOUNT_NO AND FILEID=?";
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rsTargetAccountNo=null;
		try{
			conn=DbConnection.getConnectionObject();
			pstmt=conn.prepareStatement(sql);
			pstmt.setLong(1, fileid);
			rsTargetAccountNo=pstmt.executeQuery();
			while(rsTargetAccountNo.next()){
				targetAccountNo.add(rsTargetAccountNo.getString("TARGET_ACCOUNT_NO").trim());
			}
		}finally{
			DbConnection.closeResultset(rsTargetAccountNo);
			DbConnection.closePreparedStatement(pstmt);
			DbConnection.freeConnection(conn);
		}
		
		for(BulkuploadDto element:listtempData){
			if(targetAccountNo.contains(element.getTargetAccountNo().trim())==false){
				String errors=element.getErrors();
				element.setErrors(errors+" Target Account No doesn't exist,");
				element.setTgtAccountNotPresent(true);
			}
		}
	}
	private static void validateBulkSITransferTargetPartyNoExists(ArrayList<BulkuploadDto> listtempData, long fileid)throws Exception {
		
		Set<String> targetPartyNo=new HashSet<String>();
		String sql="SELECT PARTY_NO AS TARGET_PARTY_NO FROM IOE.TM_PARTY TARGETPARTY " +
				"INNER JOIN BULKUPLOAD.TBULKUPLOAD_LSI_TRANSFER TEMPTABLE ON TARGETPARTY.PARTY_NO=TEMPTABLE.TARGET_PARTY_NO AND FILEID=?";
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rsTargetParty=null;
		try{
			conn=DbConnection.getConnectionObject();
			pstmt=conn.prepareStatement(sql);
			pstmt.setLong(1, fileid);
			rsTargetParty=pstmt.executeQuery();
			while(rsTargetParty.next()){
				targetPartyNo.add(rsTargetParty.getString("TARGET_PARTY_NO").trim());
			}
		}finally{
			DbConnection.closeResultset(rsTargetParty);
			DbConnection.closePreparedStatement(pstmt);
			DbConnection.freeConnection(conn);
		}
		
		for(BulkuploadDto element:listtempData){
			if(targetPartyNo.contains(element.getTargetPartyNo().trim())==false){
				String errors=element.getErrors();
				element.setErrors(errors+" Target party doesn't exist,");
				element.setTgtPartyNotPresent(true);
			}
		}
	}
	private static void validateBulkSITransferSourcePartyNoExists(ArrayList<BulkuploadDto> listtempData, long fileid)throws Exception {
		
		Set<String> sourcePartyNo=new HashSet<String>();
		String sql="SELECT PARTY_NO AS SOURCE_PARTY_NO FROM IOE.TM_PARTY SOURCEPARTY " +
				"INNER JOIN BULKUPLOAD.TBULKUPLOAD_LSI_TRANSFER TEMPTABLE ON SOURCEPARTY.PARTY_NO=TEMPTABLE.SOURCE_PARTY_NO AND FILEID=?";
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rsSourceParty=null;
		try{
			conn=DbConnection.getConnectionObject();
			pstmt=conn.prepareStatement(sql);
			pstmt.setLong(1, fileid);
			rsSourceParty=pstmt.executeQuery();
			while(rsSourceParty.next()){
				sourcePartyNo.add(rsSourceParty.getString("SOURCE_PARTY_NO").trim());
			}
		}finally{
			DbConnection.closeResultset(rsSourceParty);
			DbConnection.closePreparedStatement(pstmt);
			DbConnection.freeConnection(conn);
		}
		
		
		for(BulkuploadDto element:listtempData){
			if(sourcePartyNo.contains(element.getSourcePartyNo().trim())==false){
				String errors=element.getErrors();
				element.setErrors(errors+" Source party doesn't exist,");
				element.setSrcPartyNotPresent(true);
			}
		}
		
	}
	public static ArrayList<BulkuploadDto> fetchBulkSITransferDataFromTemporaryTableByFileId(long fileid) throws Exception{
		
		Utility.LOG("Step->2: SITransferBulkupload:fetching Temp data for fileid:"+fileid);
		ArrayList<BulkuploadDto>  listTempData=new ArrayList<BulkuploadDto>();
		String sql="SELECT ID, SOURCE_PARTY_NO,TARGET_PARTY_NO,TARGET_ACCOUNT_NO,LSINO,TRANSFERDATE,BCPID,DISPATCHID FROM BULKUPLOAD.TBULKUPLOAD_LSI_TRANSFER WHERE FILEID=?";
		PreparedStatement pstmt=null;
		Connection conn=null;
		ResultSet rsTempdata=null;
		BulkuploadDto objDto=null;
		try{
			conn=DbConnection.getConnectionObject();
			pstmt=conn.prepareStatement(sql);
			pstmt.setLong(1,fileid);
			rsTempdata=pstmt.executeQuery();
			while(rsTempdata.next()){
				objDto=new BulkuploadDto();
				objDto.setFileID(String.valueOf(fileid));
				objDto.setRowId(rsTempdata.getString("ID"));
				objDto.setSourcePartyNo(rsTempdata.getString("SOURCE_PARTY_NO"));
				objDto.setTargetPartyNo(rsTempdata.getString("TARGET_PARTY_NO"));
				objDto.setTargetAccountNo(rsTempdata.getString("TARGET_ACCOUNT_NO"));
				objDto.setLsiNo(rsTempdata.getString("LSINO"));
				objDto.setTransferedDate(rsTempdata.getString("TRANSFERDATE"));
				objDto.setBcpId(rsTempdata.getString("BCPID"));
				objDto.setDispatchId(rsTempdata.getString("DISPATCHID"));
				
				listTempData.add(objDto);
			}
		}catch(Exception e){
			throw e;
		}finally{
			DbConnection.closeResultset(rsTempdata);
			DbConnection.closePreparedStatement(pstmt);
			DbConnection.freeConnection(conn);
		}
		return listTempData;
	}
	//Start [0011]
	public static boolean processMasterValidationForCurrencyTransfer(
			Long fileid, Integer respId, long empId) {

		
		boolean successFileIds=false;
		try{
			ArrayList<BulkuploadDto> listtempData=fetchBulkCurrencyTransferDataFromTemporaryTableByFileId(fileid);
			//Data master validation start
			bulkCurrencyTransferMasterDataValidation(listtempData,fileid,respId);
			//Currency Transfer Processing start
			processCurrencyTransferBulkupload(listtempData,fileid,empId);
			
			successFileIds=true;
			
		}catch(Exception e){
			Utility.LOG_ITER(true, false, e, "Error Occurred from method processMasterValidationForCurrencyTransfer() in TransactionProcessDaoImpl class for fileid:"+fileid);
			try{
				ExceptionLogDto excepDtoObj=new ExceptionLogDto();
				java.sql.Clob clobData = 
						com.ibm.db2.jcc.t2zos.DB2LobFactory.createClob(Utility.getStackTrace(e));
				excepDtoObj.setAssociatedWith("BULKCURRENCYTRANSFERS");
				excepDtoObj.setExceptionMessage("BULKCURRENCYTRANSFER_"+fileid);
				excepDtoObj.setStackTraceMsg(clobData);
				
				Utility.logErrorToExceptionMaster(excepDtoObj);
				updateFileStatus(fileid,11); 
			}catch(Exception exp){
				Utility.LOG_ITER(true, false, e, "Error Occurred from method processMasterValidationForCurrencyTransfer() for log exception in exception master of fileid:"+fileid);
			}
		}
		
		return successFileIds;
	}
	private static void processCurrencyTransferBulkupload(
			ArrayList<BulkuploadDto> listtempData, Long fileid, long empId) throws Exception {
		
		logCurrencyTransferMasterVakidationErrors(listtempData,fileid);		
		
		//clearLSIandLineitemAlreadyInCurrencyTransferDetails(fileid);
		//insertLSIandLineitemToSITransferDetails(fileid);

		ArrayList<BulkuploadDto> successValidationList = loadValidationSuccessCurrencyTrDataWithOtherAttributes(fileid);
		if(successValidationList.size() > 0){
			HashMap<String,ArrayList<BulkuploadDto>> segregatedData= groupCurrencyTransferDataForBatches(successValidationList);
			HashMap<String,CurrencyChangeDto> formattedData = organiseForCurrencyTransferAPI(segregatedData,empId);
			
			Utility.LOG("Step->10: CurrencyTransferBulkupload: Bulk Currency Transfer Process start for fileid:"+fileid);
			for (Entry<String,CurrencyChangeDto> entry : formattedData.entrySet()) {
				NewOrderDaoExt objDao=new NewOrderDaoExt();
				CurrencyTransferResult transferResult =objDao.processCurrencyChange(entry.getValue()) ;
				logCurrencyTransferBatchResult(entry.getKey(),segregatedData,transferResult,fileid);
			}
			Utility.LOG("Step->11: CurrencyTransferBulkupload: updating file status to file tracking for fileid:"+fileid);
			updateFileStatus(fileid,13); //comment for test purpose uncomment it 		
		}else{
			Utility.LOG("CurrencyTransferBulkupload: Step->7(Validated temp data fetching failed due to (File contains errors) for fileid:"+fileid);
			updateFileStatus(fileid,1);
		}
		
	}
	private static void logCurrencyTransferBatchResult(String key,
			HashMap<String, ArrayList<BulkuploadDto>> segregatedData,
			CurrencyTransferResult transferResult, Long fileid) throws Exception {
		
		Utility.LOG("CurrencyTransferBulkupload:Updating Error Log to Temporary table start for fileid:"+fileid);
		Connection conn=null;
		PreparedStatement pstmtSuccess=null;
		PreparedStatement pstmtError=null;
		String sqlerr="UPDATE BULKUPLOAD.TBULKUPLOAD_CURRENCY_TRANSFER SET ERROR_LOG=? WHERE FILEID=? AND ID=?";
		String sqlSuccess="UPDATE BULKUPLOAD.TBULKUPLOAD_CURRENCY_TRANSFER SET RESULT_LOG=? WHERE FILEID=? AND ID=?";
		
		boolean isBatchGeneratedErr=false,isBatchGenerated=false;
		try{
			
			conn=DbConnection.getConnectionObject();	
			pstmtSuccess=conn.prepareStatement(sqlSuccess);
			pstmtError=conn.prepareStatement(sqlerr);			
			
			boolean flagSuccess = false;
			boolean flagError = false;
			
			ArrayList<BulkuploadDto> currencyUploadedListOfOneBatch=segregatedData.get(key);
			Map<String,CurrencyChangeDto> mapResultedCurrency=transferResult.getMapCurrencyToResult();
							
			for(BulkuploadDto bulkCurrencyRow:currencyUploadedListOfOneBatch){
				String lsinokey=bulkCurrencyRow.getLsiNo();
				
				flagSuccess = false;
				flagError = false;
				
				
				String errolog="",resultlog="";
				if(transferResult.getResultType().compareTo(CurrencyTransferResult.ResultType.BATCH_CREATION_ERROR)==0){
					errolog="Internal Error[Batch creation failed]";						
					flagError=true;
					
				}else if(transferResult.getResultType().compareTo(CurrencyTransferResult.ResultType.EXC_IN_MIDWAY)==0){
					if(mapResultedCurrency==null || !mapResultedCurrency.containsKey(lsinokey)){
						ExceptionLogDto excepObj=new ExceptionLogDto();						
						java.sql.Clob clobData = 
								com.ibm.db2.jcc.t2zos.DB2LobFactory.createClob(Utility.getStackTrace(transferResult.getException()));
						excepObj.setStackTraceMsg(clobData);
						excepObj.setAssociatedWith("BULKCURRENCYTRANSFER");
						excepObj.setExceptionMessage(transferResult.getException().getMessage());
						
						long exceptionId=Utility.logErrorToExceptionMaster(excepObj);						
						errolog="Internal Error[Exception Id:"+exceptionId+"]";
						flagError=true;
						
					}else{
						CurrencyChangeDto currencyResultRow=mapResultedCurrency.get(lsinokey);
						if(CurrencyChangeDto.SuccessFailure.FAILURE.toString().equalsIgnoreCase(currencyResultRow.getProgress())){	
							errolog="LSI No:"+currencyResultRow.getLogicalSINo()+" Error:"+currencyResultRow.getUserSpecificError();
							flagError=true;
							
						}else if(SITransferDto.SuccessFailure.SUCCESS.toString().equalsIgnoreCase(currencyResultRow.getProgress())){
							resultlog="Batch Id:"+currencyResultRow.getBatchID()+" Disconnected OrderNo:"+currencyResultRow.getBdisconorderno()+" New OrderNo:"+currencyResultRow.getNeworderno()+" Logical Si No:"+currencyResultRow.getLogicalSINo();
							flagSuccess=true;
							
						}		
					}
											
				}else if(transferResult.getResultType().compareTo(CurrencyTransferResult.ResultType.PROCESSED)==0){
					CurrencyChangeDto currencyResultRow=mapResultedCurrency.get(lsinokey);
					if(CurrencyChangeDto.SuccessFailure.FAILURE.toString().equalsIgnoreCase(currencyResultRow.getProgress())){	
						errolog="LSI No:"+currencyResultRow.getLogicalSINo()+" Error:"+currencyResultRow.getUserSpecificError();
						flagError=true;
						
					}else if(CurrencyChangeDto.SuccessFailure.SUCCESS.toString().equalsIgnoreCase(currencyResultRow.getProgress())){
						resultlog="Batch Id:"+currencyResultRow.getBatchID()+" Disconnected OrderNo:"+currencyResultRow.getBdisconorderno()+" New OrderNo:"+currencyResultRow.getNeworderno()+" Logical Si No:"+currencyResultRow.getLogicalSINo();
						flagSuccess=true;
					}								
				}
				if(flagSuccess==true){
					pstmtSuccess.setString(1, resultlog);
					pstmtSuccess.setLong(2, fileid);
					pstmtSuccess.setLong(3, Long.valueOf(bulkCurrencyRow.getRowId()));
					pstmtSuccess.addBatch();
					isBatchGenerated=true;
				}else /*if(flagError==true)*/{
					pstmtError.setString(1, errolog.replaceAll("\n", " "));
					pstmtError.setLong(2, fileid);
					pstmtError.setLong(3, Long.valueOf(bulkCurrencyRow.getRowId()));
					pstmtError.addBatch();
					isBatchGeneratedErr=true;
				}
			}
				
			if(isBatchGenerated){
				int[] results=pstmtSuccess.executeBatch();				
				for(int i:results){
					if(i==PreparedStatement.EXECUTE_FAILED){						
						throw new Exception();
					}
				}
			}
			if(isBatchGeneratedErr){
				int[] results=pstmtError.executeBatch();				
				for(int i:results){
					if(i==PreparedStatement.EXECUTE_FAILED){						
						throw new Exception();
					}
				}
			}	
			
		}catch(Exception e){
			Utility.LOG_ITER(true, false, e, "Exception occurred during executing error log updation of Currency Temporary table Batch::");
			throw e;
		}finally{			
			DbConnection.closePreparedStatement(pstmtError);
			DbConnection.closePreparedStatement(pstmtSuccess);
			DbConnection.freeConnection(conn);
		}
		
	}
	private static HashMap<String, CurrencyChangeDto> organiseForCurrencyTransferAPI(
			HashMap<String, ArrayList<BulkuploadDto>> segregatedData, long empId) {
		
		Utility.LOG("Step->9: CurrencyTransferBulkupload:Start organise LSI according to existing CurrencyTransfer method input parameter");
		HashMap<String,CurrencyChangeDto> mapObjCurrencyTransfer=null;
		if(!segregatedData.isEmpty()){
			mapObjCurrencyTransfer=new HashMap<String, CurrencyChangeDto>();
			for(Entry<String,ArrayList<BulkuploadDto>> segdata:segregatedData.entrySet()){
				String key=segdata.getKey();
				String logicalLsi="",transferDate="",servicveId="",sourceParty="",oldCurIds="",newCurIds="",accountId="",accountIdstr="",exchangerateStr="";
				double exchangerate=0;
				CurrencyChangeDto currencyTransferObj=new CurrencyChangeDto();
				
				boolean once=false;
				for(BulkuploadDto element:segdata.getValue()){
					logicalLsi=logicalLsi+element.getLsiNo()+",";
					servicveId=servicveId+element.getServiceId()+",";
					transferDate=transferDate+Utility.showDate_ddmmyyyy(element.getTransferedDate())+",";	
					accountIdstr=accountIdstr+element.getAccountId()+",";
					oldCurIds=oldCurIds+element.getOldCurrencyId()+",";
					newCurIds=newCurIds+element.getNewCurrencyId()+",";
					exchangerateStr=exchangerateStr+String.valueOf(element.getExchangeRate())+",";
					if(once==false){
						once=true;
						
						accountId=element.getAccountId();
						
					}
				}
				currencyTransferObj.setLogicalSistr(logicalLsi);
				currencyTransferObj.setDateOfTransfer(transferDate);
				currencyTransferObj.setServiceIdString(servicveId);
				currencyTransferObj.setSourcePartyNo(sourceParty);
				currencyTransferObj.setOldCurIds(oldCurIds);
				currencyTransferObj.setNewCurIds(newCurIds);
				currencyTransferObj.setRateStr(exchangerateStr);
				currencyTransferObj.setAccountno(accountIdstr);
				currencyTransferObj.setStatusOfCurrencyChange("NEW");
				currencyTransferObj.setSourceModule("BULKCURRENCYTRANSFER");
				currencyTransferObj.setRemarks("");
				currencyTransferObj.setError("");
				currencyTransferObj.setEmployeeid(Integer.valueOf(""+empId));//rahul T
				mapObjCurrencyTransfer.put(key, currencyTransferObj);
			}
		}
		return mapObjCurrencyTransfer;
	}
	private static HashMap<String, ArrayList<BulkuploadDto>> groupCurrencyTransferDataForBatches(
			ArrayList<BulkuploadDto> successValidationList) throws Exception {
		Utility.LOG("Step->8: CurrencyTransferBulkupload:Start creating Batch group");
		HashMap<String,ArrayList<BulkuploadDto>> mapGroupDataForBatch=null;
		ArrayList<BulkuploadDto> listSeperateParty =null;
		if(!successValidationList.isEmpty()){
			mapGroupDataForBatch=new HashMap<String,ArrayList<BulkuploadDto>>();
			for(BulkuploadDto bulkLSIRow:successValidationList){
				String key=bulkLSIRow.getSourcePartyNo();
				if(mapGroupDataForBatch.containsKey(key)){
					listSeperateParty =mapGroupDataForBatch.get(key);
					listSeperateParty .add(bulkLSIRow);	//bydefault list is already in the map				
				}else{
					listSeperateParty =new ArrayList<BulkuploadDto>();
					listSeperateParty .add(bulkLSIRow);
					mapGroupDataForBatch.put(key, listSeperateParty );
				}
			}
		}
		return mapGroupDataForBatch;
	}
	private static ArrayList<BulkuploadDto> loadValidationSuccessCurrencyTrDataWithOtherAttributes(
			Long fileid) throws Exception {
		
		Utility.LOG("Step->7: CurrencyTransferBulkupload:fetching sucess validate Temp data for fileid:"+fileid);
		ArrayList<BulkuploadDto>  listValidationSuccessData=new ArrayList<BulkuploadDto>();
		String sql="SELECT ID, PARTY_NO, LSINO, TRANSFERDATE, OLD_CURRENCY_ID, NEW_CURRENCY_ID, EXCHANGE_RATE, SERVICE.SERVICEID, CRMACCOUNTNO, ACCOUNT.ACCOUNTID " +
				"FROM BULKUPLOAD.TBULKUPLOAD_CURRENCY_TRANSFER TEMP INNER JOIN IOE.TPOSERVICEMASTER SERVICE " +
				"ON SERVICE.LOGICAL_SI_NO=TEMP.LSINO INNER JOIN IOE.TPOMASTER TPOMASTER ON TPOMASTER.ORDERNO=SERVICE.ORDERNO" +
				" INNER JOIN IOE.TM_ACCOUNT ACCOUNT " +
				"ON TPOMASTER.ACCOUNTID=ACCOUNT.ACCOUNTID AND IS_CHANGED_LSI=0 AND M6_FX_PROGRESS_STATUS='FX_BT_END' WHERE FILEID=? AND ERROR_LOG IS NULL";
		PreparedStatement pstmt=null;
		Connection conn=null;
		ResultSet rsTempdata=null;
		BulkuploadDto objDto=null;
		try{
			conn=DbConnection.getConnectionObject();
			pstmt=conn.prepareStatement(sql);
			pstmt.setLong(1,fileid);
			rsTempdata=pstmt.executeQuery();
			Set<String> uniqueLSINo=new HashSet<String>();
			while(rsTempdata.next()){
				objDto=new BulkuploadDto();
				objDto.setFileID(String.valueOf(fileid));
				objDto.setRowId(rsTempdata.getString("ID"));
				objDto.setSourcePartyNo(rsTempdata.getString("PARTY_NO"));
				objDto.setTargetAccountNo(rsTempdata.getString("CRMACCOUNTNO"));
				objDto.setOldCurrencyId(rsTempdata.getString("OLD_CURRENCY_ID"));
				objDto.setNewCurrencyId(rsTempdata.getString("NEW_CURRENCY_ID"));
				String lsiNo=rsTempdata.getString("LSINO").trim();
				/*if(uniqueLSINo.contains(lsiNo)){
					throw new Exception("Duplicate Latest Services found on Single LSI[Hint: Data may be not correct] for LSI:"+lsiNo);
				}
				uniqueLSINo.add(lsiNo);*/
				objDto.setLsiNo(lsiNo);
				objDto.setExchangeRate(rsTempdata.getDouble("EXCHANGE_RATE"));
				objDto.setTransferedDate(rsTempdata.getString("TRANSFERDATE"));
				objDto.setServiceId(rsTempdata.getString("SERVICEID"));
				objDto.setAccountId(rsTempdata.getString("ACCOUNTID"));
				listValidationSuccessData.add(objDto);
			}
		}catch(Exception e){
			throw e;
		}finally{
			DbConnection.closeResultset(rsTempdata);
			DbConnection.closePreparedStatement(pstmt);
			DbConnection.freeConnection(conn);
		}
		return listValidationSuccessData;		
	}
	private static boolean clearLSIandLineitemAlreadyInCurrencyTransferDetails(
			Long fileid) throws Exception{
		Utility.LOG("Step->5: CurrencyTransferBulkupload:Deleting LSI and Lineitems already available in Currency Transfer Details Table for fileid:"+fileid);
		Connection conn=null;
		PreparedStatement pstmt=null;
		String sql="DELETE FROM IOE.TM_SI_TRANSFERDETAILS WHERE (LOGICALSI,SERVICEPRODUCTID) IN( " +
				"SELECT LOGICAL_SI_NO,SERVICE_PRODUCT_ID FROM IOE.TPOSERVICEMASTER SERVICE INNER JOIN IOE.TDISCONNECTION_HISTORY TDISCONNHIS " +
				"ON TDISCONNHIS.MAIN_SERVICEID=SERVICE.SERVICEID INNER JOIN IOE.TPOSERVICEDETAILS TPOSERVICEDETAILS " +
				"ON TPOSERVICEDETAILS.SERVICEPRODUCTID=TDISCONNHIS.SERVICE_PRODUCT_ID " +
				"AND TDISCONNHIS.PARENT_SPID <> 0 AND  IS_CHANGED_LSI=0 AND M6_FX_PROGRESS_STATUS='FX_BT_END' " +
				"AND ISDISCONNECTED=0 "+//AND ISSUSPENDED=0 " +
				"INNER JOIN BULKUPLOAD.TBULKUPLOAD_LSI_TRANSFER TEMP ON TEMP.LSINO=SERVICE.LOGICAL_SI_NO " +
				"AND TEMP.ERROR_LOG IS NULL AND FILEID=? ORDER BY LOGICAL_SI_NO)";
		
		try{
			
			conn=DbConnection.getConnectionObject();	
			pstmt=conn.prepareStatement(sql);				
			pstmt.setLong(1, fileid);					
			pstmt.execute();
					
		}catch(Exception e){
			Utility.LOG(true, false, e, "Exception occurred during executing clearLSIandLineitemAlreadyInCurrencyTransferDetails for Fileid::"+fileid);
			throw e;
		}finally{
			DbConnection.closePreparedStatement(pstmt);
			DbConnection.freeConnection(conn);
		}
		return true;
	}
	private static void logCurrencyTransferMasterVakidationErrors(
			ArrayList<BulkuploadDto> listtempData, Long fileid) throws Exception {
		
		Utility.LOG("Step-4: CurrencyTransferBulkupload:Updating Error Log to Temporary table start for fileid:"+fileid);
		Connection conn=null;
		PreparedStatement pstmt=null;
		String sql="UPDATE BULKUPLOAD.TBULKUPLOAD_CURRENCY_TRANSFER SET ERROR_LOG=? WHERE FILEID=? AND ID=?";
		boolean isBatchGenerated=false;		
		try{
			
			conn=DbConnection.getConnectionObject();	
			pstmt=conn.prepareStatement(sql);
			for(BulkuploadDto element:listtempData){
				if(element.getErrors().length() > 0){					
					pstmt.setString(1, element.getErrors().replaceAll("\n", " "));
					pstmt.setLong(2, fileid);
					pstmt.setLong(3, Long.valueOf(element.getRowId()));
					pstmt.addBatch();
					isBatchGenerated=true;
				}						
			}
			if(isBatchGenerated){
				int[] results=pstmt.executeBatch();				
				for(int i:results){
					if(i==PreparedStatement.EXECUTE_FAILED){						
						throw new Exception();
					}
				}
			}
		}catch(Exception e){
			Utility.LOG_ITER(true, false, e, "Exception occurred during executing error log updation of SI Temporary table Batch::");
			throw e;
		}finally{
			DbConnection.closePreparedStatement(pstmt);
			DbConnection.freeConnection(conn);
		}
	}
	private static void bulkCurrencyTransferMasterDataValidation(
			ArrayList<BulkuploadDto> listtempData, Long fileid, Integer respId) throws Exception {
		
		Utility.LOG("Step->3: CurrencyTransferBulkupload:Master data validation start for fileid:"+fileid);
		validateBulkCurrencyTransfer_TransferDate(listtempData,fileid);
		validateBulkCurrencyTransferSourcePartyNoExists(listtempData,fileid);
		validateBulkCurrencyTransferLsiNoExists(listtempData,fileid);
		validateBulkCurrencyTransferDuplicateLSINo(listtempData,fileid);
		validateBulkCurrencyTransferFreeLSINo(listtempData,fileid);//		
		validateBulkCurrencyTransferSourcePartyAndLSI(listtempData,fileid);
		validateBulkCurrencyTransferWithSameCurrency(listtempData,fileid);
		validateBulkCurrencyTransferNewCurrencyIdExists(listtempData,fileid);
		validateBulkCurrencyTransferOldCurrencyWithPartyExists(listtempData,fileid);
		validateBulkCurrencyTransferNonUsageLSIandLinkingLSI(listtempData,fileid);
		validateBulkCurrencyTransferRespIdAndLSI(listtempData,fileid,respId);
		validateBulkCurrencyTransferDuplicateServiceOnSingleIsLSIExist(listtempData,fileid);
		//Shubhranshu, Drop&Carry Validation On ServiceFlavour
		validateBulkCurrencyTransferLSIServiceFlavour(listtempData,fileid);
		//Drop&Carry Validation Ends
		Utility.LOG("Step->3: CurrencyTransferBulkupload:Master data validation end for fileid:"+fileid);
	}
	//Shubhranshu, 8-12-2016
	private static void validateBulkCurrencyTransferLSIServiceFlavour(ArrayList<BulkuploadDto> listtempData, Long fileid)throws Exception
	{
		final String sqlgetListOfLSIDropNCarry=" SELECT TEMP.LSINO FROM BULKUPLOAD.TBULKUPLOAD_CURRENCY_TRANSFER TEMP " +
				" INNER JOIN IOE.TPOSERVICEMASTER TSM " +
				" ON TSM.LOGICAL_SI_NO=TEMP.LSINO " +
				" INNER JOIN IOE.TPOSERVICEMASTER_EXTENDED TSEM " +
				" ON TSM.SERVICEID=TSEM.SERVICEID " +
				" WHERE TSEM.SERVICE_FLAVOUR='DC' AND TEMP.FILEID=? ";			
				
		Connection conn=null;
		PreparedStatement pst=null;
		ResultSet rst=null;
		ArrayList<String>lsiListDropNCarry=new ArrayList<String>();
				
				try
				{
						conn=DbConnection.getConnectionObject();
						pst=conn.prepareStatement(sqlgetListOfLSIDropNCarry);
						pst.setLong(1, fileid);
						rst=pst.executeQuery();
				
						while(rst.next())
							{
								lsiListDropNCarry.add(rst.getString("LSINO").trim());
							}
				}
				catch(SQLException sq)
					{
					Utility.LOG(sq);
					}
				finally
					{
						DbConnection.closeResultset(rst);
						DbConnection.closePreparedStatement(pst);
						DbConnection.freeConnection(conn);
					}
				
				for(BulkuploadDto element:listtempData)
				{
					if(lsiListDropNCarry.contains(element.getLsiNo().trim())==true && element.isLsiNotPresent()==false){
							String errors=element.getErrors();
								element.setErrors(errors+" Currency Transfer Cannot be performed :Hint(LSI is of type Drop&Carry )");
					}
				}
	}
	// Method Ends..
	private static void validateBulkCurrencyTransferDuplicateServiceOnSingleIsLSIExist(ArrayList<BulkuploadDto> listtempData, Long fileid) throws Exception{
		
		Utility.LOG("Step->7: CurrencyTransferBulkupload:fetching sucess validate Temp data for fileid:"+fileid);
		String sql="SELECT COUNT(*) AS FOUNDDUPLICATELSI, LSINO FROM BULKUPLOAD.TBULKUPLOAD_CURRENCY_TRANSFER TEMP INNER JOIN IOE.TPOSERVICEMASTER SERVICE " +
				"ON SERVICE.LOGICAL_SI_NO=TEMP.LSINO " +
				" AND IS_CHANGED_LSI=0 WHERE FILEID=? GROUP BY LSINO HAVING COUNT(*) > 1";
		PreparedStatement pstmt=null;
		Connection conn=null;
		Set<String> uniqueLSINo=new HashSet<String>();
		ResultSet rsTempdata=null;
		try{
			conn=DbConnection.getConnectionObject();
			pstmt=conn.prepareStatement(sql);
			pstmt.setLong(1,fileid);
			rsTempdata=pstmt.executeQuery();
			while(rsTempdata.next()){
				uniqueLSINo.add(rsTempdata.getString("LSINO").trim());
			}
		}catch(Exception e){
			throw e;
		}finally{
			DbConnection.closeResultset(rsTempdata);
			DbConnection.closePreparedStatement(pstmt);
			DbConnection.freeConnection(conn);
		}
		for(BulkuploadDto element:listtempData){
			if(uniqueLSINo.contains(element.getLsiNo().trim())==true){
				String errors=element.getErrors();
				element.setErrors(errors+" Duplicate Latest Services found on Single LSI[Hint: Data may be not correct]");
			}
		}
				
	}
	private static void validateBulkCurrencyTransferRespIdAndLSI(
			ArrayList<BulkuploadDto> listtempData, Long fileid, Integer respId) throws Exception {

		Set<String> lsino=new HashSet<String>();
		String sql="SELECT DISTINCT LSINO FROM BULKUPLOAD.TBULKUPLOAD_CURRENCY_TRANSFER TEMP INNER JOIN " +
				"IOE.TPOSERVICEMASTER SERVICE ON SERVICE.LOGICAL_SI_NO=TEMP.LSINO AND" +
				" IS_CHANGED_LSI=0 AND M6_FX_PROGRESS_STATUS='FX_BT_END' AND FILEID=? INNER JOIN IOE.TPOMASTER TPOMASTER " +
				"ON TPOMASTER.ORDERNO=SERVICE.ORDERNO INNER JOIN IOE.TM_ACCOUNT ACCOUNT ON " +
				"TPOMASTER.ACCOUNTID=ACCOUNT.ACCOUNTID  INNER JOIN " +
				" IOE.TM_RESP_SEGMENT_MAPPING AS RSM ON RSM.CUST_SEGMENT_ID=ACCOUNT.CUSTOMERSEGMENT " +
				"where RESP_SEG_ID=?";

		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rslsino=null;
		try{
			conn=DbConnection.getConnectionObject();
			pstmt=conn.prepareStatement(sql);
			pstmt.setLong(1, fileid);
			pstmt.setLong(2, respId);
			rslsino=pstmt.executeQuery();
			while(rslsino.next()){
				lsino.add(rslsino.getString("LSINO").trim());
			}
		}finally{
			DbConnection.closeResultset(rslsino);
			DbConnection.closePreparedStatement(pstmt);
			DbConnection.freeConnection(conn);
		}
		
		
		for(BulkuploadDto element:listtempData){
			if(lsino.contains(element.getLsiNo().trim())==false){
				String errors=element.getErrors();
				element.setErrors(errors+" LSI doesn't belong to Customer Segment Responsibility,");
			}
		}
	
		
		
		
	}
	private static void validateBulkCurrencyTransferNonUsageLSIandLinkingLSI(
			ArrayList<BulkuploadDto> listtempData, Long fileid) throws Exception {

		Set<String> usageandlinkinglsi=new HashSet<String>();
		String sql="SELECT DISTINCT(LOGICAL_SI_NO) as LOGICAL_SI_NO FROM IOE.TPOSERVICEMASTER TP "+ 
				"INNER JOIN BULKUPLOAD.TBULKUPLOAD_CURRENCY_TRANSFER TEMP1 ON TEMP1.LSINO=TP.LOGICAL_SI_NO AND FILEID=? "+
				"INNER JOIN IOE.TPOSERVICEDETAILS TS ON TS.SERVICEID=TP.SERVICEID "+
				"INNER JOIN IOE.TBILLING_INFO TB ON TB.SERVICEPRODUCTID=TS.SERVICEPRODUCTID AND TB.ISUSAGE=1 AND TS.ISDISCONNECTED=0 "+
				"UNION ALL "+
				//"SELECT DISTINCT ATTVALUE as LOGICAL_SI_NO FROM IOE.TPRODUCTLINEATTVALUE TPRODUCTLINEATTVALUE "+
				//"INNER JOIN BULKUPLOAD.TBULKUPLOAD_CURRENCY_TRANSFER TEMP2 ON TEMP2.LSINO=TPRODUCTLINEATTVALUE.ATTVALUE "+
				//"AND ATTMASTERID=3947 AND FILEID=? "+
				//"UNION ALL "+
				"SELECT DISTINCT(LOGICAL_SI_NO) as LOGICAL_SI_NO FROM IOE.TPOSERVICEMASTER SERVICE "+
				"INNER JOIN BULKUPLOAD.TBULKUPLOAD_CURRENCY_TRANSFER TEMP3 ON TEMP3.LSINO=SERVICE.LOGICAL_SI_NO AND FILEID=? "+
				"INNER JOIN IOE.TPOSERVICEDETAILS TS ON TS.SERVICEID=SERVICE.SERVICEID "+
				"INNER JOIN IOE.TPRODUCTLINEATTVALUE TP ON TP.SERVICEPRODUCTID=TS.SERVICEPRODUCTID "+
				//"AND (TP.ATTMASTERID = 4093 OR TP.ATTMASTERID = 3969 OR TP.ATTMASTERID=5000327) "+
				"AND (TP.ATTMASTERID in (SELECT PRIMARY_ATTMASTERID FROM ioe.TM_SERVICE_LINKING)) "+
				"AND TP.ATTVALUE IS NOT NULL AND TP.ATTVALUE <> '' ";
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rslsi=null;
		try{
			conn=DbConnection.getConnectionObject();
			pstmt=conn.prepareStatement(sql);
			pstmt.setLong(1, fileid);
			pstmt.setLong(2, fileid);
			rslsi=pstmt.executeQuery();
			while(rslsi.next()){
				usageandlinkinglsi.add(rslsi.getString("LOGICAL_SI_NO").trim());
			}
		
		}finally{
			DbConnection.closeResultset(rslsi);
			DbConnection.closePreparedStatement(pstmt);
			DbConnection.freeConnection(conn);
		}
		
		for(BulkuploadDto element:listtempData){
			if(usageandlinkinglsi.contains(element.getLsiNo().trim())==true && element.isLsiNotPresent()==false){
				String errors=element.getErrors();
				element.setErrors(errors+" Usage LSI or Linked LSI are not allowed.");
			}
		}
		
		
	}
	private static void validateBulkCurrencyTransferOldCurrencyWithPartyExists(
			ArrayList<BulkuploadDto> listtempData, Long fileid) throws Exception {
		Set<String> lsiandcurrency=new HashSet<String>();
		String sql="SELECT DISTINCT LSINO, CURRENCYID FROM BULKUPLOAD.TBULKUPLOAD_CURRENCY_TRANSFER TEMP INNER JOIN IOE.TPOSERVICEMASTER SERVICE " +
				"ON SERVICE.LOGICAL_SI_NO=TEMP.LSINO AND IS_CHANGED_LSI=0 AND M6_FX_PROGRESS_STATUS='FX_BT_END' " +
				"AND FILEID=? INNER JOIN IOE.TPOMASTER TPOMASTER " +
				"ON TPOMASTER.ORDERNO=SERVICE.ORDERNO ";
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rslsino=null;
		try{
			conn=DbConnection.getConnectionObject();
			pstmt=conn.prepareStatement(sql);
			pstmt.setLong(1, fileid);
			rslsino=pstmt.executeQuery();
			while(rslsino.next()){
				lsiandcurrency.add(rslsino.getString("LSINO").trim()+"_"+rslsino.getInt("CURRENCYID"));
			}
		}finally{
			DbConnection.closeResultset(rslsino);
			DbConnection.closePreparedStatement(pstmt);
			DbConnection.freeConnection(conn);
		}
		
		
		for(BulkuploadDto element:listtempData){
			if(lsiandcurrency.contains(element.getLsiNo().trim()+"_"+element.getOldCurrencyId())==false ){
				String errors=element.getErrors();
				element.setErrors(errors+" Old Currency  doesn't match with Source LSI,");
			}
		}
	}
	private static void validateBulkCurrencyTransferNewCurrencyIdExists(
			ArrayList<BulkuploadDto> listtempData, Long fileid) throws Exception {
		
		Set<String> currencyIds=new HashSet<String>();
		String sql="SELECT CURRENCYID FROM IOE.TCURRENCY";
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rsCurrency=null;
		try{
			conn=DbConnection.getConnectionObject();
			pstmt=conn.prepareStatement(sql);
			rsCurrency=pstmt.executeQuery();
			while(rsCurrency.next()){
				currencyIds.add(String.valueOf(rsCurrency.getInt("CURRENCYID")));
			}
			
		}finally{
			DbConnection.closeResultset(rsCurrency);
			DbConnection.closePreparedStatement(pstmt);
			DbConnection.freeConnection(conn);
		}	
		
		for(BulkuploadDto element:listtempData){
			if(currencyIds.contains(element.getNewCurrencyId().trim())==false){
				String errors=element.getErrors();
				element.setErrors(errors+" New Currency Id  doesn't exist,");
				element.setCurrencyIdNotPresent(true);
			}
		}
			
	}
	private static void validateBulkCurrencyTransferWithSameCurrency(
			ArrayList<BulkuploadDto> listtempData, Long fileid) throws Exception {
		
		for(BulkuploadDto element:listtempData){
			
			if(element.getOldCurrencyId().equals(element.getNewCurrencyId())){
				String errors=element.getErrors();
				element.setErrors(errors+" Please Select Different Curreny For LSI .");
			}			
		}		
	}
	private static void validateBulkCurrencyTransfer_TransferDate(
			ArrayList<BulkuploadDto> listtempData, Long fileid) throws Exception{
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		Date date=new Date();
		String currentDate=sdf.format(date);
		for(BulkuploadDto element:listtempData){
			
			if(Utility.dateCompare(element.getTransferedDate(),currentDate,"dd-MMM-yyyy")==1){
				String errors=element.getErrors();
				element.setErrors(errors+" Transfer Date can not be greater than current date.");
			}			
		}		
	}
	private static void validateBulkCurrencyTransferSourcePartyNoExists(
			ArrayList<BulkuploadDto> listtempData, Long fileid) throws Exception {
		
		Set<String> sourcePartyNo=new HashSet<String>();
		String sql="SELECT SOURCEPARTY.PARTY_NO AS SOURCE_PARTY_NO FROM IOE.TM_PARTY SOURCEPARTY " +
				"INNER JOIN BULKUPLOAD.TBULKUPLOAD_CURRENCY_TRANSFER TEMPTABLE ON SOURCEPARTY.PARTY_NO=TEMPTABLE.PARTY_NO AND FILEID=?";
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rsSourceParty=null;
		try{
			conn=DbConnection.getConnectionObject();
			pstmt=conn.prepareStatement(sql);
			pstmt.setLong(1, fileid);
			rsSourceParty=pstmt.executeQuery();
			while(rsSourceParty.next()){
				sourcePartyNo.add(rsSourceParty.getString("SOURCE_PARTY_NO").trim());
			}
		}finally{
			DbConnection.closeResultset(rsSourceParty);
			DbConnection.closePreparedStatement(pstmt);
			DbConnection.freeConnection(conn);
		}
		
		
		for(BulkuploadDto element:listtempData){
			if(sourcePartyNo.contains(element.getSourcePartyNo().trim())==false){
				String errors=element.getErrors();
				element.setErrors(errors+" Source party doesn't exist,");
				element.setSrcPartyNotPresent(true);
			}
		}
		
	}
	private static void validateBulkCurrencyTransferLsiNoExists(
			ArrayList<BulkuploadDto> listtempData, Long fileid) throws Exception {
		
		Set<String> lsino=new HashSet<String>();
		String sql="SELECT DISTINCT LOGICAL_SI_NO FROM IOE.TPOSERVICEMASTER SERVICE " +
				"INNER JOIN BULKUPLOAD.TBULKUPLOAD_CURRENCY_TRANSFER TEMPTABLE ON SERVICE.LOGICAL_SI_NO=TEMPTABLE.LSINO AND FILEID=?";
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rslsino=null;
		try{
			conn=DbConnection.getConnectionObject();
			pstmt=conn.prepareStatement(sql);
			pstmt.setLong(1, fileid);
			rslsino=pstmt.executeQuery();
			while(rslsino.next()){
				lsino.add(rslsino.getString("LOGICAL_SI_NO").trim());
			}
			
		}finally{
			DbConnection.closeResultset(rslsino);
			DbConnection.closePreparedStatement(pstmt);
			DbConnection.freeConnection(conn);
		}	
		
		for(BulkuploadDto element:listtempData){
			if(lsino.contains(element.getLsiNo().trim())==false){
				String errors=element.getErrors();
				element.setErrors(errors+" LSI No doesn't exist,");
				element.setLsiNotPresent(true);
			}
		}
			
	}
	private static void validateBulkCurrencyTransferDuplicateLSINo(
			ArrayList<BulkuploadDto> listtempData, Long fileid)throws Exception {
		Set<String> lsino=new HashSet<String>();
		String sql="SELECT COUNT(*) AS FOUNDDUPLICATELSI,LSINO FROM BULKUPLOAD.TBULKUPLOAD_CURRENCY_TRANSFER WHERE FILEID=? GROUP BY LSINO HAVING COUNT(*) > 1 ";
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rslsino=null;
		try{
			conn=DbConnection.getConnectionObject();
			pstmt=conn.prepareStatement(sql);
			pstmt.setLong(1, fileid);
			rslsino=pstmt.executeQuery();
			while(rslsino.next()){
				lsino.add(rslsino.getString("LSINO").trim());
			}
		}finally{
			DbConnection.closeResultset(rslsino);
			DbConnection.closePreparedStatement(pstmt);
			DbConnection.freeConnection(conn);
		}	
		
		for(BulkuploadDto element:listtempData){
			if(lsino.contains(element.getLsiNo().trim())==true){
				String errors=element.getErrors();
				element.setErrors(errors+" Duplicate LSI No found.");
			}
		}
	}
	private static void validateBulkCurrencyTransferFreeLSINo(
			ArrayList<BulkuploadDto> listtempData, Long fileid) throws Exception {
		Set<String> lsino=new HashSet<String>();
		String sql="SELECT LSINO FROM BULKUPLOAD.TBULKUPLOAD_CURRENCY_TRANSFER TEMP INNER JOIN IOE.TPOSERVICEMASTER SERVICE " +
				"ON SERVICE.LOGICAL_SI_NO=TEMP.LSINO AND FILEID=? WHERE SERVICE.IS_CHANGED_LSI=0 AND M6_FX_PROGRESS_STATUS='FX_BT_END'";
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rslsino=null;
		try{
			conn=DbConnection.getConnectionObject();
			pstmt=conn.prepareStatement(sql);
			pstmt.setLong(1, fileid);
			rslsino=pstmt.executeQuery();
			while(rslsino.next()){
				lsino.add(rslsino.getString("LSINO").trim());
			}
		}finally{
			DbConnection.closeResultset(rslsino);
			DbConnection.closePreparedStatement(pstmt);
			DbConnection.freeConnection(conn);
		}	
			
		for(BulkuploadDto element:listtempData){
			if(lsino.contains(element.getLsiNo().trim())==false && element.isLsiNotPresent()==false){
				String errors=element.getErrors();
				element.setErrors(errors+" LSI No doesn't available for Currency Transfer:Hint(LSI No may be in draft or not free),");
			}
		}
	}
	private static void validateBulkCurrencyTransferSourcePartyAndLSI(
			ArrayList<BulkuploadDto> listtempData, Long fileid) throws Exception {
		Set<String> lsino=new HashSet<String>();
		String sql="SELECT DISTINCT LSINO FROM BULKUPLOAD.TBULKUPLOAD_CURRENCY_TRANSFER TEMP INNER JOIN IOE.TPOSERVICEMASTER SERVICE " +
				"ON SERVICE.LOGICAL_SI_NO=TEMP.LSINO AND IS_CHANGED_LSI=0 AND M6_FX_PROGRESS_STATUS='FX_BT_END' " +
				"AND FILEID=? INNER JOIN IOE.TPOMASTER TPOMASTER " +
				"ON TPOMASTER.ORDERNO=SERVICE.ORDERNO INNER JOIN IOE.TM_ACCOUNT ACCOUNT " +
				"ON TPOMASTER.ACCOUNTID=ACCOUNT.ACCOUNTID  INNER JOIN IOE.TM_PARTY PARTY " +
				"ON PARTY.PARTY_ID=ACCOUNT.PARTY_ID AND TEMP.PARTY_NO=PARTY.PARTY_NO";
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rslsino=null;
		try{
			conn=DbConnection.getConnectionObject();
			pstmt=conn.prepareStatement(sql);
			pstmt.setLong(1, fileid);
			rslsino=pstmt.executeQuery();
			while(rslsino.next()){
				lsino.add(rslsino.getString("LSINO").trim());
			}
		}finally{
			DbConnection.closeResultset(rslsino);
			DbConnection.closePreparedStatement(pstmt);
			DbConnection.freeConnection(conn);
		}
		
		
		for(BulkuploadDto element:listtempData){
			if(lsino.contains(element.getLsiNo().trim())==false && element.isSrcPartyNotPresent()==false && element.isLsiNotPresent()==false){
				String errors=element.getErrors();
				element.setErrors(errors+" Source Party No doesn't match with Source LSI,");
			}
		}
	}
	private static ArrayList<BulkuploadDto> fetchBulkCurrencyTransferDataFromTemporaryTableByFileId(
			Long fileid) throws Exception{
		
		Utility.LOG("Step->2: CurrencyTransferBulkupload:fetching Temp data for fileid:"+fileid);
		ArrayList<BulkuploadDto>  listTempData=new ArrayList<BulkuploadDto>();
		String sql="SELECT ID, PARTY_NO, LSINO, TRANSFERDATE, OLD_CURRENCY_ID, NEW_CURRENCY_ID, EXCHANGE_RATE FROM BULKUPLOAD.TBULKUPLOAD_CURRENCY_TRANSFER WHERE FILEID=?";
		PreparedStatement pstmt=null;
		Connection conn=null;
		ResultSet rsTempdata=null;
		BulkuploadDto objDto=null;
		try{
			conn=DbConnection.getConnectionObject();
			pstmt=conn.prepareStatement(sql);
			pstmt.setLong(1,fileid);
			rsTempdata=pstmt.executeQuery();
			while(rsTempdata.next()){
				objDto=new BulkuploadDto();
				objDto.setFileID(String.valueOf(fileid));
				objDto.setRowId(rsTempdata.getString("ID"));
				objDto.setSourcePartyNo(rsTempdata.getString("PARTY_NO"));
				objDto.setLsiNo(rsTempdata.getString("LSINO"));
				objDto.setTransferedDate(rsTempdata.getString("TRANSFERDATE"));
				objDto.setOldCurrencyId(rsTempdata.getString("OLD_CURRENCY_ID"));
				objDto.setNewCurrencyId(rsTempdata.getString("NEW_CURRENCY_ID"));
				objDto.setExchangeRate(rsTempdata.getDouble("EXCHANGE_RATE"));
				listTempData.add(objDto);
			}
		}catch(Exception e){
			throw e;
		}finally{
			DbConnection.closeResultset(rsTempdata);
			DbConnection.closePreparedStatement(pstmt);
			DbConnection.freeConnection(conn);
		}
		return listTempData;
	}
	
	
	
	// End [0011]	
}
