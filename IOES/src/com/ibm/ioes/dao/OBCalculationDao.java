//[001] Pankaj Thakur: added the functionality of OB_YEAR


package com.ibm.ioes.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import com.ibm.ioes.forms.OBCalculationDTO;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Utility;

public class OBCalculationDao {

	public static final String COPC_Approval="COPC_Approval";
	public static final String BULK_Approval="BULK_Approval";
	public static final String ManualOB_Charge="ManualOB_Charge";
	public static final String ManualOB_Component="ManualOB_Component";
	public static final String Rejection="Rejection";
	public static final String M6Cancel="M6Cancel";
	public static final String CancelAndCopy="CancelAndCopy";
	public static final String ServiceCancel="ServiceCancel";
	public static final String BillingTriggerEnds="BillingTriggerEnds";
	public static final String Rejection_Component="Rejection_Component";
	public static final String M6Cancel_Component="M6Cancel_Component";
	public static final String CancelAndCopy_Component="CancelAndCopy_Component";
	public static final String ServiceCancel_Component="ServiceCancel_Component";
	
	public static final int Yes = 1 ;
	public static final int No = 0 ;
	public static final int Validation_Required = 1;
	public static final int Validation_Not_Required= 0;
	public static final int OrderNo_Passing = 1;
	public static final int ServiceNo_Passing= 0;

	private static final int NEW_CHARGE = 1 ;
	private static final int DISCONNECTED_CHARGE = 2 ;
	private static final int LINKED_CHARGE = 3 ;

	private static String sqlGetChargeOBDetails= 
			" SELECT TCI.CHARGES_STATUS, TCI.OB_LINK_CHARGEID, TCI_NEW.CHARGEINFOID AS NEW_LINKED_CHARGEINFOID, MAS.SUB_CHANGE_TYPE_ID, TCI.NET_OB_ID, TCI.CHARGEINFOID, "+
		    	" TCI.CHARGEAMOUNT AS NEW_CHARGE_AMOUNT, TCI.CHARGEPERIOD, TCI.CHARGESTYPE, TCI.START_DATE_MONTH, TCI.SYTEM_OB, TCI_OLD.SYTEM_OB AS OLD_SYSTEM_OB, "+
		    	" OB_NET.TENTATIVE_OB_START_DATE, OB_NET.TENTATIVE_OB_END_DATE, OB_NET_OLD.ACTUAL_OB_START_DATE AS OLD_ACTUAL_OB_START_DATE, "+
		    	" OB_NET_OLD.ACTUAL_OB_END_DATE AS OLD_ACTUAL_OB_END_DATE, TCI_OLD.NET_OB_ID AS OLD_CHARGE_NET_OB_ID,TCI_OLD.CHARGEAMOUNT AS OLD_CHARGEAMOUNT, " +
		    	" OB_NET_OLD.INITIAL_DURATION_NET_OB_ID AS OLD_INITIAL_DURATION_NET_OB_ID, OB_NET.INITIAL_DURATION_NET_OB_ID, "+
		    	" IOE.FORMAT_CALENDERDATE(TCI.START_DATE) AS CHARGE_START_DATE, TCI.OB_VALUE, " +
		    	" OB_NET.ACTUAL_OB_START_DATE , OB_NET.ACTUAL_OB_END_DATE , INITIAL_DURATION_OB.ACTUAL_OB_START_DATE AS INITIAL_ACTUAL_OB_START_DATE, "+
		    	" INITIAL_DURATION_OB.ACTUAL_OB_END_DATE AS INITIAL_ACTUAL_OB_END_DATE , TCI.ISDISCONNECTED AS IS_CHARGE_DISCONNECTED, "+
		    	" ORDER_SERVICE_LINE_MAPP.IS_DISCONNECTED AS IS_LINE_DISCONNECTED, TCI.ENDDATELOGIC, TCI.DISCONNECTED_IN_ORDER_NO, "+
		    	" TSD.BILLING_TRIGGER_STATUS, IOE.FORMAT_CALENDERDATE(TCI.END_DATE) AS CHARGE_END_DATE "+
		    	" FROM IOE.TPOSERVICEMASTER SERMAS "+
		    	" INNER JOIN IOE.TPOMASTER MAS ON MAS.ORDERNO=SERMAS.ORDERNO "+ 
		    	" INNER JOIN IOE.TDISCONNECTION_HISTORY ORDER_SERVICE_LINE_MAPP ON ORDER_SERVICE_LINE_MAPP.MAIN_SERVICEID=SERMAS.SERVICEID "+ 
	        	" 		AND SERMAS.IS_IN_HISTORY=0 "+
			    " INNER JOIN IOE.TCHARGES_INFO TCI ON TCI.SERVICEPRODUCTID=ORDER_SERVICE_LINE_MAPP.SERVICE_PRODUCT_ID " +
			    " INNER JOIN IOE.TPOSERVICEDETAILS TSD ON TSD.SERVICEPRODUCTID = ORDER_SERVICE_LINE_MAPP.SERVICE_PRODUCT_ID "+ 
			    " LEFT JOIN IOE.TCHARGES_INFO TCI_NEW ON TCI_NEW.OB_LINK_CHARGEID=TCI.CHARGEINFOID "+
			    " LEFT OUTER JOIN IOE.OB_NET OB_NET ON OB_NET.NET_OB_ID= TCI.NET_OB_ID "+
			    " LEFT JOIN IOE.TCHARGES_INFO TCI_OLD ON TCI_OLD.CHARGEINFOID=TCI.OB_LINK_CHARGEID "+
			    " LEFT OUTER JOIN IOE.OB_NET OB_NET_OLD ON OB_NET_OLD.NET_OB_ID = TCI_OLD.NET_OB_ID "+
			    " LEFT OUTER JOIN IOE.OB_NET INITIAL_DURATION_OB ON INITIAL_DURATION_OB.NET_OB_ID = OB_NET.INITIAL_DURATION_NET_OB_ID "+
			    " WHERE SERMAS.SERVICEID=? "+
			" UNION "+
			" SELECT TCI.CHARGES_STATUS, TCI.OB_LINK_CHARGEID, TCI_NEW.CHARGEINFOID AS NEW_LINKED_CHARGEINFOID, MAS.SUB_CHANGE_TYPE_ID, TCI.NET_OB_ID, TCI.CHARGEINFOID, "+
			    " TCI.CHARGEAMOUNT AS NEW_CHARGE_AMOUNT, TCI.CHARGEPERIOD, TCI.CHARGESTYPE, TCI.START_DATE_MONTH, TCI.SYTEM_OB, TCI_OLD.SYTEM_OB AS OLD_SYSTEM_OB, "+
			    " OB_NET.TENTATIVE_OB_START_DATE, OB_NET.TENTATIVE_OB_END_DATE, OB_NET_OLD.ACTUAL_OB_START_DATE AS OLD_ACTUAL_OB_START_DATE,  "+
			    " OB_NET_OLD.ACTUAL_OB_END_DATE AS OLD_ACTUAL_OB_END_DATE, TCI_OLD.NET_OB_ID AS OLD_CHARGE_NET_OB_ID,TCI_OLD.CHARGEAMOUNT AS OLD_CHARGEAMOUNT, "+
			    " OB_NET_OLD.INITIAL_DURATION_NET_OB_ID AS OLD_INITIAL_DURATION_NET_OB_ID, OB_NET.INITIAL_DURATION_NET_OB_ID, "+
			    " IOE.FORMAT_CALENDERDATE(TCI.START_DATE) AS CHARGE_START_DATE, TCI.OB_VALUE, " +
			    " OB_NET.ACTUAL_OB_START_DATE , OB_NET.ACTUAL_OB_END_DATE , INITIAL_DURATION_OB.ACTUAL_OB_START_DATE AS INITIAL_ACTUAL_OB_START_DATE, "+
			    " INITIAL_DURATION_OB.ACTUAL_OB_END_DATE AS INITIAL_ACTUAL_OB_END_DATE , TCI.ISDISCONNECTED AS IS_CHARGE_DISCONNECTED, "+
		    	" ORDER_SERVICE_LINE_MAPP.IS_DISCONNECTED AS IS_LINE_DISCONNECTED, TCI.ENDDATELOGIC, TCI.DISCONNECTED_IN_ORDER_NO," +
		    	" TSD.BILLING_TRIGGER_STATUS, IOE.FORMAT_CALENDERDATE(TCI.END_DATE) AS CHARGE_END_DATE "+
		    	" FROM IOE.TPOSERVICEMASTER SERMAS "+
			    " INNER JOIN IOE.TPOMASTER MAS ON MAS.ORDERNO=SERMAS.ORDERNO "+ 
			    " INNER JOIN IOE.TDISCONNECTION_HISTORY ORDER_SERVICE_LINE_MAPP ON ORDER_SERVICE_LINE_MAPP.MAIN_SERVICEID=SERMAS.SERVICEID "+ 
			    "     AND SERMAS.IS_IN_HISTORY=1 "+
			    " INNER JOIN IOE.TCHARGES_INFO_HISTORY TCI ON TCI.SERVICEPRODUCTID=ORDER_SERVICE_LINE_MAPP.SERVICE_PRODUCT_ID " +
			    " AND TCI.MAIN_SERVICE_ID=ORDER_SERVICE_LINE_MAPP.MAIN_SERVICEID "+
			    " INNER JOIN IOE.TPOSERVICEDETAILS TSD ON TSD.SERVICEPRODUCTID = ORDER_SERVICE_LINE_MAPP.SERVICE_PRODUCT_ID "+ 
			    " LEFT JOIN IOE.TCHARGES_INFO_HISTORY TCI_NEW ON TCI_NEW.OB_LINK_CHARGEID=TCI.CHARGEINFOID AND TCI_NEW.MAIN_SERVICE_ID=TCI.MAIN_SERVICE_ID "+ 
			    " LEFT OUTER JOIN IOE.OB_NET OB_NET ON OB_NET.NET_OB_ID= TCI.NET_OB_ID "+
			    " LEFT JOIN IOE.TCHARGES_INFO_HISTORY TCI_OLD ON TCI_OLD.CHARGEINFOID=TCI.OB_LINK_CHARGEID AND TCI_OLD.MAIN_SERVICE_ID=TCI.MAIN_SERVICE_ID "+ 
			    " LEFT OUTER JOIN IOE.OB_NET OB_NET_OLD ON OB_NET_OLD.NET_OB_ID = TCI_OLD.NET_OB_ID " +
			    " LEFT OUTER JOIN IOE.OB_NET INITIAL_DURATION_OB ON INITIAL_DURATION_OB.NET_OB_ID = OB_NET.INITIAL_DURATION_NET_OB_ID "+
			    " WHERE SERMAS.SERVICEID=? ";

	private static String sqlGetCancelledServiceDetails="SELECT MAS.ORDERDATE, DATE(OBS.CREATEDATE) AS SERVICE_CANCEL_DATE FROM IOE.OB_SCHEDULER OBS "+
			" INNER JOIN IOE.TPOMASTER MAS ON MAS.ORDERNO = OBS.ORDER_NO WHERE OBS.ID = ? ";
	
	private static String sqlGetNetOBIds_Charge = "SELECT CHARGEINFOID,OB_NET.NET_OB_ID FROM IOE.TCHARGES_INFO TCI "+
			" INNER JOIN IOE.OB_NET OB_NET ON OB_NET.SOURCE_CHARGEIFOID = TCI.CHARGEINFOID WHERE TCI.CREATEDIN_SERVICEID = ? ";
	
	private static String sqlGetNetOBIds_Component = "SELECT COMPONENTINFOID,OB_NET.NET_OB_ID FROM IOE.TCOMPONENT_INFO TCI "+
			" INNER JOIN IOE.OB_NET OB_NET ON OB_NET.SOURCE_CHARGEIFOID = TCI.COMPONENTINFOID WHERE TCI.CREATEDIN_SERVICEID = ? ";

	private static String sqlGetIsLatestCount = "SELECT COUNT(1) AS IS_LATEST_COUNT FROM IOE.OB_TRANSACTION WHERE CHARGEINFOID = ? AND SERVICEID = ? AND IS_LATEST=1";

	private static String sqlGetDisconnectedChargeInitialOB = "SELECT CHARGEINFOID,SYTEM_OB FROM IOE.TCHARGES_INFO_HISTORY "+
			" WHERE MAIN_SERVICE_ID < ? AND CHARGEINFOID IN (@DisconnectChargeList) ";
	
	private static String sqlGetChargeOBDetailsForBulkUpload= "SELECT BULK_OB.CHARGE_ID,  TCI.NET_OB_ID, DOUBLE(TRIM(BULK_OB.OB_VALUE)) OB_VALUE " +
			" FROM IOE.TDISCONNECTION_HISTORY ORDER_SERVICE_LINE_MAPP"+
			" INNER JOIN BULKUPLOAD.TBULKUPLOAD_OB_VALUE BULK_OB ON  BULK_OB.ORDERNO = ORDER_SERVICE_LINE_MAPP.ORDERNO AND BULK_OB.FILEID = ?"+
			" AND BULK_OB.LINE_ITEM_ID = ORDER_SERVICE_LINE_MAPP.SERVICE_PRODUCT_ID "+
			" INNER JOIN IOE.TCHARGES_INFO TCI ON TCI.CHARGEINFOID = BULK_OB.CHARGE_ID WHERE ORDER_SERVICE_LINE_MAPP.MAIN_SERVICEID=?";

	private static String sqlGetCompOBDetailsForBulkUpload = "SELECT BULK_OB.COMPONENT_ID,TCI.NET_OB_ID, DOUBLE(TRIM(BULK_OB.OB_VALUE)) OB_VALUE "+
			" FROM IOE.TDISCONNECTION_HISTORY ORDER_SERVICE_LINE_MAPP "+
			" INNER JOIN BULKUPLOAD.TBULKUPLOAD_OBVALUE_USAGE BULK_OB ON  BULK_OB.ORDER_NO = ORDER_SERVICE_LINE_MAPP.ORDERNO AND BULK_OB.FILEID = ? "+
			" AND BULK_OB.LINE_ITEM_ID = ORDER_SERVICE_LINE_MAPP.SERVICE_PRODUCT_ID "+
			" INNER JOIN IOE.TCOMPONENT_INFO TCI ON TCI.COMPONENTINFOID = BULK_OB.COMPONENT_ID "+
			" WHERE ORDER_SERVICE_LINE_MAPP.MAIN_SERVICEID=? ";

	private static String sqlGetComponentInfoIdForReversal = "SELECT CHARGEINFOID FROM IOE.OB_TRANSACTION WHERE SERVICEID= ? AND IS_LATEST = 1";
	
	private String FXServiceStatusUpdate="UPDATE IOE.OB_SCHEDULER SET STATUS='P' WHERE STATUS IN ('F','Q') ";
	private String sqlOB_NO_SCH_GETSERVICEDATA = "CALL IOE.GET_NEXT_OB_DATA(?)";
	private String sqlOB_SCHEDULER_SUCCESS="call IOE.OB_SCHEDULER_SUCCESS(?,?,?)";
	private String sqlOB_SCHEDULER_FAILURE="call IOE.OB_SCHEDULER_FAILURE(?,?,?)";

	public static String sqlValAndInsObSchedulerData ="call IOE.SP_VALIDATE_AND_INSERT_OB_SCHEDULER_DATA(?,?,?,?,?,?,?)";
	public static void main(String[] a){
		OBCalculationDao obCalculationDao = new OBCalculationDao();
		try {
			obCalculationDao.OBValueScheduler(0L);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*public static Connection getConnectionObject() throws Exception {
		Connection conn = null;
		DriverManager.registerDriver(new com.ibm.db2.jcc.DB2Driver());
		conn = DriverManager.getConnection("jdbc:db2://10.5.153.243:60004/IOES", "a1448525","jun@2013");
		return conn;

	}*/

	/**	
	 *	This function calculates and updates OB Value for required charges
	 */
	public void OBValueScheduler(long serviceNo)
	{
		try
		{
			Utility.LOG(true, true, " In  calculateAndUpdateOBValue() of Class OBCalculationDao");
			Connection connection = null;
			String logIdentifier = null;
			try{			
				connection = DbConnection.getConnectionObject();
				//connection = getConnectionObject();

				try{
					statusUpdateforOBFailed(connection);
				}catch(Exception ex){
					Utility.LOG(true,true,ex,null);
				}				

				connection.setAutoCommit(false);
				OBCalculationDTO calculationDTO = null;
				do{
					//fetch one service for OB Calculation of all charges attached in that line
					try{
						calculationDTO = fetchNextOBService(connection,serviceNo);
						connection.commit(); //code to commit for feb 7 deployment
					}catch(Exception ex){
						Utility.LOG(true,true,ex,null);
						connection.commit();//code to commit for feb 7 deployment
						continue;
					}
					if(calculationDTO!=null) 
					{ 
						try
						{	new OBCalculationDao().calculateSaveOB( connection,calculationDTO,logIdentifier);
						}
						catch(Exception ex)
						{
							String msg=logIdentifier+"Exception in saving FX New Order Service Schedular final Result for serviceproductId :"+calculationDTO.getServiceId()
									+" \n Result was returnStatus="+calculationDTO.getReturnStatus()
									+" \n Exception , if any, = "+((calculationDTO.getException()==null)?null:Utility.getStackTrace(calculationDTO.getException()))
									+" \n Exception Message , if any,= "+calculationDTO.getExceptionMessage();
							Utility.LOG(true,true,ex,msg);
							connection.rollback();
						}

						if(calculationDTO.getReturnStatus()==-1)
							connection.rollback();

						updateSuccesFailureStatus(connection,calculationDTO);
						connection.commit();
					}
				}while(calculationDTO!=null);
				connection.setAutoCommit(true);
			}
			finally
			{
				try
				{
					if(connection!=null)
					{
						DbConnection.freeConnection(connection);
					}
				}
				catch(Exception ex)
				{
					Utility.LOG(true,true,ex,null);
				}
			}
		}
		catch(Throwable th)
		{
			Utility.LOG(true,true,new Exception(th),null);
		}
	}

	public void statusUpdateforOBFailed(Connection connection)
	{
		PreparedStatement pstmt=null;
		try{
			pstmt=connection.prepareStatement(FXServiceStatusUpdate);
			pstmt.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try{
				if(pstmt!=null)DbConnection.closePreparedStatement(pstmt);
			}catch(Exception ex){
				Utility.LOG(true,true,ex,null);
				ex.printStackTrace();
			}
		}
	}

	OBCalculationDTO fetchNextOBService(Connection connection,long ServiceNo) throws Exception
	{
		OBCalculationDTO obCalculationDTO=null;
		ResultSet rs=null;
		CallableStatement cstmt_Service = null;
		CallableStatement cstmt_ServiceExternalIds = null;
		CallableStatement cstmt_ServiceExtendedData = null;
		try{
			cstmt_Service=connection.prepareCall(sqlOB_NO_SCH_GETSERVICEDATA);
			cstmt_Service.setLong(1,ServiceNo);
			rs=cstmt_Service.executeQuery();
			if(rs.next()){
				obCalculationDTO=new OBCalculationDTO();
				obCalculationDTO.setRowId(rs.getLong("ID"));
				obCalculationDTO.setServiceId(rs.getLong("SERVICE_ID"));
				obCalculationDTO.setLogicalSINumber(rs.getLong("LSI_NO"));
				obCalculationDTO.setOrderNo(rs.getLong("ORDER_NO"));
				obCalculationDTO.setAction(rs.getString("ACTION"));
				obCalculationDTO.setCurrencyRate(rs.getDouble("CURRENCY_RATE"));
				obCalculationDTO.setCopcApprovalDate(rs.getDate("CREATEDATE"));
				obCalculationDTO.setFileId(rs.getLong("FILEID"));
			}
		}
		finally{
			if(rs!=null)DbConnection.closeResultset(rs);
			if(cstmt_Service!=null)DbConnection.closeCallableStatement(cstmt_Service) ;
			if(cstmt_ServiceExternalIds!=null) DbConnection.closeCallableStatement(cstmt_ServiceExternalIds);
			if(cstmt_ServiceExtendedData!=null) DbConnection.closeCallableStatement(cstmt_ServiceExtendedData);
		}
		return obCalculationDTO;
	}

	@SuppressWarnings("resource")
	public void calculateSaveOB (Connection connection,OBCalculationDTO dto,String logIdentifier) throws Exception{
		int returnStatus = 0;
		Format formatter = new SimpleDateFormat("MMM");
		Format formatterYear = new SimpleDateFormat("yyyy");
		CallableStatement callstmt =null;
		PreparedStatement pstmt = null,pstmt1= null,pstmt2= null,pstmt3= null,pstmt4= null,pstmt5=null;
		ResultSet rs = null;
		int[] insertCounts = null;
		OBCalculationDTO obCalculationDTO = null;
		ArrayList<OBCalculationDTO> arrayListOBCalculationDTO_Charge = new ArrayList<OBCalculationDTO>();
		ArrayList<OBCalculationDTO> arrayListOBCalculationDTO_Component = new ArrayList<OBCalculationDTO>();
		HashMap<Long , Long> hashMap = null;
		HashMap<Long, Double> hashMapPreviousOBOfDisconnectedCharge =  null;
		int insertObBatchCount = 0,updateObBatchCount = 0,insertOBTransactionCount=0,initialDurationNetObIdUpdateCounter=0;
		boolean obReversalTobegenerated = false;
		String disconnectedChargeList="";
		
		String queryForInsertOB_NetWithTentativeOBDates = 	"INSERT INTO IOE.OB_NET (SOURCE_CHARGEIFOID,TENTATIVE_OB_START_DATE,TENTATIVE_OB_END_DATE) VALUES (?,?,?)";
		String queryForupdateOB_NetForTentativeOB = 		"UPDATE IOE.OB_NET SET TENTATIVE_OB_START_DATE=?, TENTATIVE_OB_END_DATE=? WHERE NET_OB_ID =? ";
		String queryForupdateActualOBDates = 				"UPDATE IOE.OB_NET SET ACTUAL_OB_START_DATE=?, ACTUAL_OB_END_DATE=? WHERE SOURCE_CHARGEIFOID =? ";
		String queryForUpdateInitialDurationNetObid = 		"UPDATE IOE.OB_NET SET INITIAL_DURATION_NET_OB_ID=? WHERE NET_OB_ID =? ";
		
		String queryForInsertOB_Transaction_Approval = 		"INSERT INTO IOE.OB_TRANSACTION(NET_OB_ID,ORDERNO,SERVICEID,LOGICAL_SI_NO,CHARGEINFOID,OB_MONTH,OB_VALUE,EXCHANGE_RATE,ENTRY_TYPE,IS_LATEST,OB_YEAR) "+
				"VALUES(?,?,?,?,?,?,?,?,?,?,?)";
		String queryForUpdate_ChargeOB = 					"UPDATE IOE.TCHARGES_INFO SET SYTEM_OB=?,NET_OB_ID=?,OB_VALUE_LAST_UPDATE_DATE=CURRENT TIMESTAMP WHERE CHARGEINFOID = ?";
		String queryForUpdate_ChargeHistoryOB = 			"UPDATE IOE.TCHARGES_INFO_HISTORY SET SYTEM_OB=?,NET_OB_ID=?,OB_VALUE_LAST_UPDATE_DATE=CURRENT TIMESTAMP WHERE CHARGEINFOID = ? AND MAIN_SERVICE_ID >= ?";
		String queryForIsLatestUpdate = 					"UPDATE IOE.OB_TRANSACTION SET IS_LATEST=0 WHERE SERVICEID = ? AND IS_LATEST=1";
		String queryForInsertOB_transaction_Reversal_Service ="INSERT INTO IOE.OB_TRANSACTION" +
				"		(NET_OB_ID,ORDERNO,SERVICEID,LOGICAL_SI_NO,CHARGEINFOID,OB_MONTH,OB_VALUE,EXCHANGE_RATE,ENTRY_TYPE,IS_LATEST,OB_YEAR) "+
				" SELECT NET_OB_ID,ORDERNO,SERVICEID,LOGICAL_SI_NO,CHARGEINFOID,?,	  -1*OB_VALUE,EXCHANGE_RATE,'System',0,?"+
				" FROM IOE.OB_TRANSACTION WHERE SERVICEID = ? AND IS_LATEST = 1 ";
		String queryForInsertOB_transaction_Reversal_Charge ="INSERT INTO IOE.OB_TRANSACTION "+
				" 		(NET_OB_ID, ORDERNO, SERVICEID, LOGICAL_SI_NO, CHARGEINFOID, OB_MONTH,    OB_VALUE, EXCHANGE_RATE, ENTRY_TYPE, IS_LATEST,OB_YEAR) "+
				" SELECT NET_OB_ID, ORDERNO, SERVICEID, LOGICAL_SI_NO, CHARGEINFOID, OB_MONTH, -1*OB_VALUE, EXCHANGE_RATE, 'Manual', 0,OB_YEAR"+
				" FROM IOE.OB_TRANSACTION WHERE CHARGEINFOID = ? AND IS_LATEST = 1 ";
		String queryForIsLatestUpdate_Charge = " UPDATE IOE.OB_TRANSACTION SET IS_LATEST=0 WHERE CHARGEINFOID = ? AND IS_LATEST=1 ";

		String queryForUpdate_ManualChargeOB = 		  " UPDATE IOE.TCHARGES_INFO 		 	SET OB_VALUE=?,				 OB_VALUE_LAST_UPDATE_DATE=CURRENT TIMESTAMP WHERE CHARGEINFOID = ? ";
		String queryForUpdate_ManualChargeHistoryOB = " UPDATE IOE.TCHARGES_INFO_HISTORY 	SET OB_VALUE=?,				 OB_VALUE_LAST_UPDATE_DATE=CURRENT TIMESTAMP WHERE CHARGEINFOID = ? 	AND MAIN_SERVICE_ID >= ? ";
		String queryForUpdate_ManualCompOB = 		  " UPDATE IOE.TCOMPONENT_INFO 		 	SET OB_VALUE=?, NET_OB_ID=?, OB_VALUE_LAST_UPDATE_DATE=CURRENT TIMESTAMP WHERE COMPONENTINFOID = ? ";
		String queryForUpdate_ManualCompHistoryOB =   " UPDATE IOE.TCOMPONENT_INFO_HISTORY 	SET OB_VALUE=?, NET_OB_ID=?, OB_VALUE_LAST_UPDATE_DATE=CURRENT TIMESTAMP WHERE COMPONENTINFOID = ? 	AND MAIN_SERVICE_ID >= ? ";

		String queryForUpdate_ChargeOBForDisconnectedCharge = 		"UPDATE IOE.TCHARGES_INFO 			SET SYTEM_OB=?,OB_VALUE_LAST_UPDATE_DATE=CURRENT TIMESTAMP WHERE CHARGEINFOID = ?";
		String queryForUpdate_ChargeHistoryOBForDisconnectedCharge ="UPDATE IOE.TCHARGES_INFO_HISTORY 	SET SYTEM_OB=?,OB_VALUE_LAST_UPDATE_DATE=CURRENT TIMESTAMP WHERE CHARGEINFOID = ? AND MAIN_SERVICE_ID >= ?";
		String queryForUpdate_ManualCompOB_Reversal = 		  		"UPDATE IOE.TCOMPONENT_INFO 		SET OB_VALUE=0,OB_VALUE_LAST_UPDATE_DATE=CURRENT TIMESTAMP WHERE COMPONENTINFOID = ? ";
		String queryForUpdate_ManualCompHistoryOB_Reversal =   		"UPDATE IOE.TCOMPONENT_INFO_HISTORY SET OB_VALUE=0,OB_VALUE_LAST_UPDATE_DATE=CURRENT TIMESTAMP WHERE COMPONENTINFOID = ? 	AND MAIN_SERVICE_ID >= ? ";
		
		try{	
			if(dto.getAction().equals(COPC_Approval) || dto.getAction().equals(BULK_Approval) || dto.getAction().equals(BillingTriggerEnds)){
				/* 
				 * Fetching charge details for OB Calculation
				 * 22/12/14 closing db objects by Nagarjuna
				 */
				
			try{
					
				callstmt= connection.prepareCall(sqlGetChargeOBDetails);	
				callstmt.setLong(1, dto.getServiceId());
				callstmt.setLong(2, dto.getServiceId());
				rs=callstmt.executeQuery();

				while (rs.next()){
					obCalculationDTO = new OBCalculationDTO();
					obCalculationDTO.setChargeStatus(rs.getString("CHARGES_STATUS"));
					obCalculationDTO.setObLinkChargeId(rs.getLong("OB_LINK_CHARGEID"));
					obCalculationDTO.setNewLinkedChargeInfoId(rs.getLong("NEW_LINKED_CHARGEINFOID"));
					obCalculationDTO.setSubChangeTypeId(rs.getInt("SUB_CHANGE_TYPE_ID"));
					obCalculationDTO.setNetObId(rs.getLong("NET_OB_ID"));
					obCalculationDTO.setChargeInfoId(rs.getLong("CHARGEINFOID"));
					obCalculationDTO.setNewChargeAmount(rs.getDouble("NEW_CHARGE_AMOUNT"));
					obCalculationDTO.setChargePeriod(rs.getInt("CHARGEPERIOD"));
					obCalculationDTO.setChargeType(rs.getInt("CHARGESTYPE"));
					obCalculationDTO.setChargeStartDateMonth(rs.getInt("START_DATE_MONTH"));
					obCalculationDTO.setSystemOB(rs.getDouble("SYTEM_OB"));
					obCalculationDTO.setOldSystemOB(rs.getDouble("OLD_SYSTEM_OB"));
					obCalculationDTO.setTentativeObStartDate(rs.getDate("TENTATIVE_OB_START_DATE"));
					obCalculationDTO.setTentativeObEndDate(rs.getDate("TENTATIVE_OB_END_DATE"));
					obCalculationDTO.setActualObStartDate(rs.getDate("ACTUAL_OB_START_DATE"));
					obCalculationDTO.setActualObEndDate(rs.getDate("ACTUAL_OB_END_DATE"));
					obCalculationDTO.setOldActualObStartDate(rs.getDate("OLD_ACTUAL_OB_START_DATE"));
					obCalculationDTO.setOldActualObEndDate(rs.getDate("OLD_ACTUAL_OB_END_DATE"));
					obCalculationDTO.setOldChargeNetOBId(rs.getLong("OLD_CHARGE_NET_OB_ID"));
					obCalculationDTO.setOldChargeAmount(rs.getDouble("OLD_CHARGEAMOUNT"));
					obCalculationDTO.setOldInitialDurationNetObId(rs.getLong("OLD_INITIAL_DURATION_NET_OB_ID"));
					obCalculationDTO.setInitialDurationNetObId(rs.getLong("INITIAL_DURATION_NET_OB_ID"));
					obCalculationDTO.setChargeStartDate(rs.getDate("CHARGE_START_DATE"));
					obCalculationDTO.setManualOB(rs.getDouble("OB_VALUE"));
					obCalculationDTO.setInitialDurationActualObStartDate(rs.getDate("INITIAL_ACTUAL_OB_START_DATE"));
					obCalculationDTO.setInitialDurationActualObEndDate(rs.getDate("INITIAL_ACTUAL_OB_END_DATE"));
					obCalculationDTO.setIsChargeDisconnected(rs.getInt("IS_CHARGE_DISCONNECTED"));
					obCalculationDTO.setIsLineDisconnected(rs.getInt("IS_LINE_DISCONNECTED"));
					obCalculationDTO.setBillingTriggerStatus(rs.getInt("BILLING_TRIGGER_STATUS"));
					obCalculationDTO.setDisconnectedOrderNo(rs.getLong("DISCONNECTED_IN_ORDER_NO"));
					obCalculationDTO.setChargeEndDate(rs.getDate("CHARGE_END_DATE"));
					obCalculationDTO.setEndDateLogic(rs.getString("ENDDATELOGIC"));
					
					obCalculationDTO.setCopcApprovalDate(dto.getCopcApprovalDate());
					arrayListOBCalculationDTO_Charge.add(obCalculationDTO);		
				}
				}finally{
						DbConnection.closeResultset(rs);
						DbConnection.closeCallableStatement(callstmt);
						}
				/* 
				 * Calculating OB and Inserting and Updating Related tables
				 */
				if(arrayListOBCalculationDTO_Charge != null && arrayListOBCalculationDTO_Charge.size()>0){
					for(Iterator<OBCalculationDTO> iterator=arrayListOBCalculationDTO_Charge.iterator();iterator.hasNext();){
						OBCalculationDTO element = (OBCalculationDTO) iterator.next();
						if(element.getChargeStatus().equals("NEW") && element.getObLinkChargeId()==0){

							element.setCalculationLogic(NEW_CHARGE);
							element=calculateOBValueForNewCharge(element,dto);

						}/*else if((element.getChargeStatus().equals("CHANGED") && element.getNewLinkedChargeInfoId()==0 && element.getNetObId()!=0)
								|| ((element.getSubChangeTypeId()==3 || element.getSubChangeTypeId()==4 )  && element.getNetObId()!=0//&& element.getChargeStatus().equals("UNCHANGED")
								&& dto.getCopcApprovalDate().getTime()>=element.getActualObStartDate().getTime() && dto.getCopcApprovalDate().getTime()<element.getActualObEndDate().getTime()))*/
								
						else if((element.getChargeStatus().equals("CHANGED") && element.getNewLinkedChargeInfoId()==0 && element.getNetObId()!=0
								&& element.getIsChargeDisconnected()==1 && element.getDisconnectedOrderNo()==dto.getOrderNo())
								|| ((element.getSubChangeTypeId()==3 || element.getSubChangeTypeId()==4 )  && element.getNetObId()!=0 && element.getIsLineDisconnected()==1
								&& element.getBillingTriggerStatus()!=20 && element.getIsChargeDisconnected()==0
								&& (element.getEndDateLogic().equals("TD") || (element.getEndDateLogic().equals("BTD") && element.getChargeEndDate().getTime()>element.getCopcApprovalDate().getTime()))
								&& dto.getCopcApprovalDate().getTime()>=element.getActualObStartDate().getTime() && dto.getCopcApprovalDate().getTime()<element.getActualObEndDate().getTime())){
								
							element.setCalculationLogic(DISCONNECTED_CHARGE);
							element=calculateOBValueForDisconnectedCharge(element);

						}else if(element.getChargeStatus().equals("NEW") && element.getObLinkChargeId()!=0 && element.getOldChargeNetOBId()!=0){
							
							element.setCalculationLogic(LINKED_CHARGE);
							element=calculateOBValueForLinkedCharge(element,dto);
						}
					}
				}
				
				if(dto.getAction().equals(COPC_Approval) || dto.getAction().equals(BULK_Approval)){
					/* 
					 * Preparing Batch for NET_OB for new charge
					 */
					pstmt = connection.prepareStatement(queryForInsertOB_NetWithTentativeOBDates);
					pstmt1 = connection.prepareStatement(queryForupdateOB_NetForTentativeOB);
					for(Iterator<OBCalculationDTO> iterator=arrayListOBCalculationDTO_Charge.iterator();iterator.hasNext();){
						OBCalculationDTO element = (OBCalculationDTO) iterator.next();
						if(element.getNetObId()==0 && (element.getCalculationLogic()==LINKED_CHARGE || element.getCalculationLogic()==NEW_CHARGE) ){
							/*
							 * Inserting data in NET_OB table for OB generation
							 */
							pstmt.setLong(1,element.getChargeInfoId());
							pstmt.setDate(2,(java.sql.Date) element.getCalculatedTentativeObStartDate());
							pstmt.setDate(3,(java.sql.Date) element.getCalculatedTentativeObEndDate());
							pstmt.addBatch();
							insertObBatchCount++;
							element.setNewlyGeneratedNetObId(true);
						}else if(element.getNetObId()!=0 && (element.getCalculationLogic()==LINKED_CHARGE || element.getCalculationLogic()==NEW_CHARGE) ){
							/*
							 * Updating data in NET_OB table
							 */
							pstmt1.setDate(1,(java.sql.Date) element.getCalculatedTentativeObStartDate());
							pstmt1.setDate(2,(java.sql.Date) element.getCalculatedTentativeObEndDate());
							pstmt1.setLong(3,element.getChargeInfoId());
							pstmt1.addBatch();
							updateObBatchCount++;
						}
					}
					if(insertObBatchCount>0){
						//22/12/14 closing db objects by Nagarjuna
						try{
							insertCounts = pstmt.executeBatch();
							}finally{
									DbConnection.closePreparedStatement(pstmt);
									}
						if(insertCounts.length != insertObBatchCount){
							returnStatus = -1;
							throw new Exception("Error while inserting tentativeOB Dates in OB_NET table for ServiceId "+dto.getServiceId());
						} else{
							returnStatus = 1;
						}
					}
					if(updateObBatchCount>0){
						//22/12/14 closing db objects by Nagarjuna
						try{
							insertCounts = pstmt1.executeBatch();
							}finally{
									DbConnection.closePreparedStatement(pstmt1);
									}
						if(insertCounts.length != updateObBatchCount){
							returnStatus = -1;
							throw new Exception("Error while updating tentativeOB Dates in OB_NET table for ServiceId "+dto.getServiceId());
						} 
					}
	
					/*
					 * If Net_OB Insertion/Update successful, then fetching NET_OB_ID against new charges
					 */
					//22/12/14 closing db objects by Nagarjuna
					try{						
						callstmt= connection.prepareCall(sqlGetNetOBIds_Charge);	
						callstmt.setLong(1, dto.getServiceId());
						rs=callstmt.executeQuery();
						hashMap = new HashMap<Long, Long>();
						while (rs.next()){
							hashMap.put(rs.getLong("CHARGEINFOID"), rs.getLong("NET_OB_ID"));
						}
					}finally{
							DbConnection.closeResultset(rs);
							DbConnection.closeCallableStatement(callstmt);
					}
	
					pstmt3 = connection.prepareStatement(queryForUpdateInitialDurationNetObid);
					pstmt = connection.prepareStatement(queryForInsertOB_Transaction_Approval);
					pstmt1 = connection.prepareStatement(queryForUpdate_ChargeOB);
					pstmt2 = connection.prepareStatement(queryForUpdate_ChargeHistoryOB);
	
					for(Iterator<OBCalculationDTO> iterator=arrayListOBCalculationDTO_Charge.iterator();iterator.hasNext();){
						OBCalculationDTO element = (OBCalculationDTO) iterator.next();
						if(element.getObValue()!=0 || element.isObComputed()==true){
							
							/*
							 * Inserting data in OB_Transaction table
							 */
							int isLatestCount = 0;
							//22/12/14 closing db objects by Nagarjuna
							try{
								callstmt= connection.prepareCall(sqlGetIsLatestCount);
								callstmt.setLong(1, element.getChargeInfoId());
								callstmt.setLong(2, dto.getServiceId());
								rs=callstmt.executeQuery();
								while (rs.next()){
									isLatestCount = rs.getInt("IS_LATEST_COUNT");
								}
							}finally{
									DbConnection.closeResultset(rs);
									DbConnection.closeCallableStatement(callstmt);
							}
							
							if(isLatestCount>0){
								throw new Exception("More than 1 isLatestCount For ChargeInfoId "+element.getChargeInfoId()+" of Service "+dto.getServiceId());
							}
							
							if(element.getCalculationLogic()==DISCONNECTED_CHARGE)
								pstmt.setLong(1,element.getNetObId());
							else
								pstmt.setLong(1,hashMap.get(element.getChargeInfoId()));
	
							pstmt.setLong(2,dto.getOrderNo());
							pstmt.setLong(3,dto.getServiceId());
							pstmt.setLong(4,dto.getLogicalSINumber());
							pstmt.setLong(5,element.getChargeInfoId());
							pstmt.setString(6,formatter.format(dto.getCopcApprovalDate()));
							pstmt.setDouble(7, new Double(new DecimalFormat("##.##").format(element.getObValue())));
							pstmt.setDouble(8,dto.getCurrencyRate());
							pstmt.setString(9, "System");
							pstmt.setInt(10, 1);
							pstmt.setString(11,formatterYear.format(dto.getCopcApprovalDate()));
							pstmt.addBatch();
							insertOBTransactionCount++;
	
							/*
							 * Query For Updating OB Value and Net_OB_ID in tchargeInfo
							 */
	
							if(element.getCalculationLogic()==DISCONNECTED_CHARGE)
								pstmt1.setDouble(1, new Double(new DecimalFormat("##.##").format(element.getSystemOB()+element.getObValue())));//here we need to update the diff of new and oldOb
							else
								pstmt1.setDouble(1, new Double(new DecimalFormat("##.##").format(element.getObValue())));
							//pstmt1.setDouble(1,new Double(new DecimalFormat("##.##").format(element.getObValue())));
							if(element.getCalculationLogic()==DISCONNECTED_CHARGE)
								pstmt1.setLong(2,element.getNetObId());
							else
								pstmt1.setLong(2,hashMap.get(element.getChargeInfoId()));
							pstmt1.setLong(3,element.getChargeInfoId());
							pstmt1.addBatch();
	
							/*
							 * Query For Updating OB Value and Net_OB_ID in tchargeInfo_History
							 */
	
							if(element.getCalculationLogic()==DISCONNECTED_CHARGE){
								pstmt2.setDouble(1, new Double(new DecimalFormat("##.##").format(element.getSystemOB()+element.getObValue())));//here we need to update the diff of new and oldOb
								pstmt2.setLong(2,element.getNetObId());
							}else{
								pstmt2.setDouble(1, new Double(new DecimalFormat("##.##").format(element.getObValue())));
								pstmt2.setLong(2,hashMap.get(element.getChargeInfoId()));
							}
							pstmt2.setLong(3,element.getChargeInfoId());
							pstmt2.setLong(4,dto.getServiceId());
							pstmt2.addBatch();
							
							/*
							 * Updating Initial Duration NetObId only for those NetObIds which are newly generated 
							 */
							
							if(element.isNewlyGeneratedNetObId()){
								if (element.getCalculationLogic()==NEW_CHARGE)//If it is new charge, then net ob Id will be initial duration net ob id
									pstmt3.setLong(1,hashMap.get(element.getChargeInfoId()));
								else											// else old charge's initial duration net ob id
									pstmt3.setLong(1,element.getOldInitialDurationNetObId());
								pstmt3.setLong(2,hashMap.get(element.getChargeInfoId()));
								pstmt3.addBatch();
								initialDurationNetObIdUpdateCounter++;
							}
						}
					}
	
					if(insertOBTransactionCount>0){
						//22/12/14 closing db objects by Nagarjuna
						try{
							insertCounts = pstmt.executeBatch();
							}finally{
									DbConnection.closePreparedStatement(pstmt);
									}
						if(insertCounts.length != insertOBTransactionCount){
							returnStatus = -1;
							throw new Exception("Error while inserting data in OB_TRANSACTION for Approval of ServiceId "+dto.getServiceId());
						} else{
							returnStatus = 1;
						}
						//22/12/14 closing db objects by Nagarjuna
						try{
							insertCounts = pstmt1.executeBatch();
							}finally{
									DbConnection.closePreparedStatement(pstmt1);
									}
						if(insertCounts.length != insertOBTransactionCount){
							returnStatus = -1;
							throw new Exception("Error while updating data in TCHARGES_INFO for Approval of ServiceId "+dto.getServiceId());
						}
						//22/12/14 closing db objects by Nagarjuna
						try{
							insertCounts = pstmt2.executeBatch();
							}finally{
									DbConnection.closePreparedStatement(pstmt2);
									}
						if(insertCounts.length != insertOBTransactionCount){
							returnStatus = -1;
							throw new Exception("Error while updating data in TCHARGES_INFO_HISTORY for Approval of ServiceId "+dto.getServiceId());
						}
						if(initialDurationNetObIdUpdateCounter>0){
							//22/12/14 closing db objects by Nagarjuna
							try{
								insertCounts = pstmt3.executeBatch();
								}finally{
										DbConnection.closePreparedStatement(pstmt3);
										}
							if(insertCounts.length != initialDurationNetObIdUpdateCounter){
								returnStatus = -1;
								throw new Exception("Error while updating InitialDurationNetOBIdin OB_NET table for ServiceId "+dto.getServiceId());
							}
						}
					}
				}else if(dto.getAction().equals(BillingTriggerEnds)){
					pstmt1 = connection.prepareStatement(queryForupdateActualOBDates);
					
					for(Iterator<OBCalculationDTO> iterator=arrayListOBCalculationDTO_Charge.iterator();iterator.hasNext();){
						OBCalculationDTO element = (OBCalculationDTO) iterator.next();
					
						if(element.getNetObId()!=0 && element.getCalculatedActualObStartDate()!= null && 
								(element.getCalculationLogic()==LINKED_CHARGE || element.getCalculationLogic()==NEW_CHARGE) ){
							/*
							 * Updating data in NET_OB table
							 */
							pstmt1.setDate(1,(java.sql.Date) element.getCalculatedActualObStartDate());
							pstmt1.setDate(2,(java.sql.Date) element.getCalculatedActualObEndDate());
							pstmt1.setLong(3,element.getChargeInfoId());
							pstmt1.addBatch();
							updateObBatchCount++;
						}
					}
					if(updateObBatchCount>0){
						//22/12/14 closing db objects by Nagarjuna
						try{
							insertCounts = pstmt1.executeBatch();
							}finally{
									DbConnection.closePreparedStatement(pstmt1);
									}
						if(insertCounts.length != updateObBatchCount){
							returnStatus = -1;
							throw new Exception("Error while updating Actual Dates in OB_NET table for ServiceId "+dto.getServiceId());
						} else{
							returnStatus = 1;
						}
					}
					//pstmt1.close();
				}
			}
			/*
			 * Below are the cases for which we have to generate reversal
			 */
			else if (dto.getAction().equals(Rejection) || dto.getAction().equals(M6Cancel) || dto.getAction().equals(CancelAndCopy) || dto.getAction().equals(ServiceCancel)){
				
				if (dto.getAction().equals(M6Cancel) || dto.getAction().equals(CancelAndCopy) || dto.getAction().equals(ServiceCancel)){
					/* 
					 * Fetching Cancel Date and Order Date for OB Calculation
					 */
					//22/12/14 closing db objects by Nagarjuna
					try{
						callstmt= connection.prepareCall(sqlGetCancelledServiceDetails);	
						callstmt.setLong(1, dto.getRowId());
						rs=callstmt.executeQuery();
		
						while (rs.next()){
							dto.setOrderDate(rs.getDate("ORDERDATE"));
							dto.setServiceCancelDate(rs.getDate("SERVICE_CANCEL_DATE"));
						}
					}finally{
							DbConnection.closeResultset(rs);
							DbConnection.closeCallableStatement(callstmt);
							}
					
					
					if(isSameFinancialYear(dto.getOrderDate(),dto.getServiceCancelDate())){
						obReversalTobegenerated = true;
					}
					else if(isDateDifferenceLessThanPeriod(dto.getOrderDate(),dto.getServiceCancelDate(),6)){//Period passed id 6 months
						obReversalTobegenerated = true;
					}				
				}
				
				
				/*
				 * Fetching Charge details For Current Service
				*/
				//22/12/14 closing db objects by Nagarjuna
			try{
				callstmt= connection.prepareCall(sqlGetChargeOBDetails);	
				callstmt.setLong(1, dto.getServiceId());
				callstmt.setLong(2, dto.getServiceId());
				rs=callstmt.executeQuery();
				while (rs.next()){
					obCalculationDTO = new OBCalculationDTO();
					obCalculationDTO.setChargeInfoId(rs.getLong("CHARGEINFOID"));
					obCalculationDTO.setChargeStatus(rs.getString("CHARGES_STATUS"));
					obCalculationDTO.setNewLinkedChargeInfoId(rs.getLong("NEW_LINKED_CHARGEINFOID"));
					obCalculationDTO.setSubChangeTypeId(rs.getInt("SUB_CHANGE_TYPE_ID"));
					obCalculationDTO.setNetObId(rs.getLong("NET_OB_ID"));
					obCalculationDTO.setActualObStartDate(rs.getDate("ACTUAL_OB_START_DATE"));
					obCalculationDTO.setActualObEndDate(rs.getDate("ACTUAL_OB_END_DATE"));
					obCalculationDTO.setIsChargeDisconnected(rs.getInt("IS_CHARGE_DISCONNECTED"));
					obCalculationDTO.setIsLineDisconnected(rs.getInt("IS_LINE_DISCONNECTED"));
					obCalculationDTO.setBillingTriggerStatus(rs.getInt("BILLING_TRIGGER_STATUS"));
					obCalculationDTO.setDisconnectedOrderNo(rs.getLong("DISCONNECTED_IN_ORDER_NO"));
					obCalculationDTO.setChargeEndDate(rs.getDate("CHARGE_END_DATE"));
					obCalculationDTO.setEndDateLogic(rs.getString("ENDDATELOGIC"));
					obCalculationDTO.setCopcApprovalDate(dto.getCopcApprovalDate());
					arrayListOBCalculationDTO_Charge.add(obCalculationDTO);	
				}
				}finally{
						DbConnection.closeResultset(rs);
						DbConnection.closeCallableStatement(callstmt);
						}
				
				
				/*
				 * Identifying those charge which are disconnected and not linked
				*/
				if(arrayListOBCalculationDTO_Charge != null && arrayListOBCalculationDTO_Charge.size()>0){
					for(Iterator<OBCalculationDTO> iterator=arrayListOBCalculationDTO_Charge.iterator();iterator.hasNext();){
						OBCalculationDTO element = (OBCalculationDTO) iterator.next();
						/*if((element.getChargeStatus().equals("CHANGED") && element.getNewLinkedChargeInfoId()==0 && element.getNetObId()!=0)
								|| ((element.getSubChangeTypeId()==3 || element.getSubChangeTypeId()==4 )  && element.getNetObId()!=0//&& element.getChargeStatus().equals("UNCHANGED")
								&& dto.getCopcApprovalDate().getTime()>=element.getActualObStartDate().getTime() && dto.getCopcApprovalDate().getTime()<element.getActualObEndDate().getTime())){
						*/
						 if((element.getChargeStatus().equals("CHANGED") && element.getNewLinkedChargeInfoId()==0 && element.getNetObId()!=0
								&& element.getIsChargeDisconnected()==1 && element.getDisconnectedOrderNo()==dto.getOrderNo())
								|| ((element.getSubChangeTypeId()==3 || element.getSubChangeTypeId()==4 )  && element.getNetObId()!=0 && element.getIsLineDisconnected()==1
								&& element.getBillingTriggerStatus()!=20 && element.getIsChargeDisconnected()==0
								&& (element.getEndDateLogic().equals("TD") || (element.getEndDateLogic().equals("BTD") && element.getChargeEndDate().getTime()>element.getCopcApprovalDate().getTime()))
								&& dto.getCopcApprovalDate().getTime()>=element.getActualObStartDate().getTime() && dto.getCopcApprovalDate().getTime()<element.getActualObEndDate().getTime())){
						
							element.setCalculationLogic(DISCONNECTED_CHARGE);
							if(disconnectedChargeList=="")
								disconnectedChargeList = String.valueOf(element.getChargeInfoId());
							else
								disconnectedChargeList=disconnectedChargeList+","+element.getChargeInfoId();
						}
					}
				}
				
				/*
				 * Getting previous systemOb for disconnected charge from history
				*/
				if(disconnectedChargeList!=""){
					//22/12/14 closing db objects by Nagarjuna
					try{
						callstmt= connection.prepareCall(sqlGetDisconnectedChargeInitialOB.replaceAll("@DisconnectChargeList", disconnectedChargeList));	
						callstmt.setLong(1, dto.getServiceId());
						///@DisconnectChargeList
						//callstmt.setString(2, disconnectedChargeList);
						rs=callstmt.executeQuery();
						hashMapPreviousOBOfDisconnectedCharge = new HashMap<Long, Double>();
						while (rs.next()){
							hashMapPreviousOBOfDisconnectedCharge.put(rs.getLong("CHARGEINFOID"), rs.getDouble("SYTEM_OB"));
						}
						}finally{
							DbConnection.closeResultset(rs);
							DbConnection.closeCallableStatement(callstmt);
							}
				}
				
				/*
				 * Generating reversal below for services rejected or cancelled services,
				 * eligible for reversal
				*/
				if(obReversalTobegenerated || dto.getAction().equals(Rejection)){
				
					/*
					 * Reversing latest OB Value for all charges of current service
					 */
					try{
						pstmt=connection.prepareStatement(queryForInsertOB_transaction_Reversal_Service);
						pstmt.setString(1, formatter.format(dto.getCopcApprovalDate()));
						pstmt.setString(2, formatterYear.format(dto.getCopcApprovalDate()));
						pstmt.setLong(3,dto.getServiceId());
						pstmt.executeUpdate();
						returnStatus = 1;
					}catch (SQLException e) {
						returnStatus = -1;
						throw new Exception("Error while doing reversal in Transaction Table for ServiceId "+dto.getServiceId());
					}finally{
						try{
							if(pstmt!=null)DbConnection.closePreparedStatement(pstmt);
						}catch(Exception ex){
							ex.printStackTrace();
						}
					}
	
					/*
					 * Updating IS_LATEST in OB_TRANSACTION to Zero for all charges in current service
					 */
					try{
						pstmt = connection.prepareStatement(queryForIsLatestUpdate);
						pstmt.setLong(1,dto.getServiceId());
						pstmt.executeUpdate();
					}catch (SQLException e) {
						returnStatus = -1;
						throw new Exception("Error while updating IS_LATEST in Transaction Table for ServiceId "+dto.getServiceId());
					}finally{
						try{
							if(pstmt!=null)DbConnection.closePreparedStatement(pstmt);
						}catch(Exception ex){
							ex.printStackTrace();
						}
					}
					
					/*
					 * Updating system Ob for disconnected charge in there present order
					*/
					if(disconnectedChargeList!=""){
						pstmt = connection.prepareStatement(queryForUpdate_ChargeOBForDisconnectedCharge);
						pstmt1 = connection.prepareStatement(queryForUpdate_ChargeHistoryOBForDisconnectedCharge);
						
						for(Iterator<OBCalculationDTO> iterator=arrayListOBCalculationDTO_Charge.iterator();iterator.hasNext();){
							OBCalculationDTO element = (OBCalculationDTO) iterator.next();
						
							if(element.getCalculationLogic()==DISCONNECTED_CHARGE && hashMapPreviousOBOfDisconnectedCharge.containsKey(element.getChargeInfoId())){
							
								updateObBatchCount++;
								pstmt.setDouble(1,hashMapPreviousOBOfDisconnectedCharge.get(element.getChargeInfoId()));
								pstmt.setLong(2,element.getChargeInfoId());
								pstmt.addBatch();
			
								pstmt1.setDouble(1,hashMapPreviousOBOfDisconnectedCharge.get(element.getChargeInfoId()));
								pstmt1.setLong(2,element.getChargeInfoId());
								pstmt1.setLong(3,dto.getServiceId());
								pstmt1.addBatch();
							}
						}
						
						if(updateObBatchCount>0){
							//22/12/14 closing db objects by Nagarjuna
							try{
								insertCounts = pstmt.executeBatch();
								}finally{
										DbConnection.closePreparedStatement(pstmt);
										}
							if(insertCounts.length != updateObBatchCount){
								returnStatus = -1;
								throw new Exception("Error while doing rollback of OB in TCHARGES_INFO for disconnected charge for ServiceId "+dto.getServiceId());
							}else{
								returnStatus = 1;
							}
							//22/12/14 closing db objects by Nagarjuna
							try{
								insertCounts = pstmt1.executeBatch();
								}finally{
										DbConnection.closePreparedStatement(pstmt1);
										}
							if(insertCounts.length != updateObBatchCount){
								returnStatus = -1;
								throw new Exception("Error while doing rollback of OB in TCHARGES_INFO for disconnected charge for ServiceId "+dto.getServiceId());
							}
						}
					}
				}
			}
			else if (dto.getAction().equals(ManualOB_Charge)){
				/* 
				 * Fetching charge details for OB Calculation
				 */
				//22/12/14 closing db objects by Nagarjuna
			try{
				callstmt= connection.prepareCall(sqlGetChargeOBDetailsForBulkUpload);	
				callstmt.setLong(1, dto.getFileId());
				callstmt.setLong(2, dto.getServiceId());
				rs=callstmt.executeQuery();

				while (rs.next()){
					obCalculationDTO = new OBCalculationDTO();
					obCalculationDTO.setChargeInfoId(rs.getLong("CHARGE_ID"));
					obCalculationDTO.setNetObId(rs.getLong("NET_OB_ID"));
					obCalculationDTO.setObValue(rs.getDouble("OB_VALUE"));
					obCalculationDTO.setCopcApprovalDate(dto.getCopcApprovalDate());
					arrayListOBCalculationDTO_Charge.add(obCalculationDTO);	
				}
				}finally{
						DbConnection.closeResultset(rs);
						DbConnection.closeCallableStatement(callstmt);
						}

				pstmt = connection.prepareStatement(queryForInsertOB_transaction_Reversal_Charge);
				pstmt1 = connection.prepareStatement(queryForIsLatestUpdate_Charge);
				pstmt2 = connection.prepareStatement(queryForInsertOB_Transaction_Approval);
				pstmt3 = connection.prepareStatement(queryForUpdate_ManualChargeOB);
				pstmt4 = connection.prepareStatement(queryForUpdate_ManualChargeHistoryOB);

				for(Iterator<OBCalculationDTO> iterator=arrayListOBCalculationDTO_Charge.iterator();iterator.hasNext();){
					OBCalculationDTO element = (OBCalculationDTO) iterator.next();

					insertOBTransactionCount++;
					
					pstmt.setLong(1,element.getChargeInfoId());
					pstmt.addBatch();

					pstmt1.setLong(1,element.getChargeInfoId());
					pstmt1.addBatch();

					pstmt2.setLong(1,element.getNetObId());
					pstmt2.setLong(2,dto.getOrderNo());
					pstmt2.setLong(3,dto.getServiceId());
					pstmt2.setLong(4,dto.getLogicalSINumber());
					pstmt2.setLong(5,element.getChargeInfoId());
					pstmt2.setString(6,formatter.format(dto.getCopcApprovalDate()));
					pstmt2.setDouble(7, element.getObValue());
					pstmt2.setDouble(8,dto.getCurrencyRate());
					pstmt2.setString(9, "Manual");
					pstmt2.setInt(10, 1);
					pstmt.setString(11,formatterYear.format(dto.getCopcApprovalDate()));
					pstmt2.addBatch();

					pstmt3.setDouble(1,element.getObValue());
					pstmt3.setLong(2,element.getChargeInfoId());
					pstmt3.addBatch();

					pstmt4.setDouble(1,element.getObValue());
					pstmt4.setLong(2,element.getChargeInfoId());
					pstmt4.setLong(3,dto.getServiceId());
					pstmt4.addBatch();
				}

				if(insertOBTransactionCount>0){
					//22/12/14 closing db objects by Nagarjuna
					try{
						insertCounts = pstmt.executeBatch();
						}finally{
								DbConnection.closePreparedStatement(pstmt);
								}
					if(insertCounts.length != insertOBTransactionCount){
						returnStatus = -1;
						throw new Exception("Error while doing reversal in Transaction Table in case of Manual OB for ServiceId "+dto.getServiceId());
					}else{
						returnStatus = 1;
					}
					//22/12/14 closing db objects by Nagarjuna
					try{
						insertCounts = pstmt1.executeBatch();
						}finally{
								DbConnection.closePreparedStatement(pstmt1);
								}
					if(insertCounts.length != insertOBTransactionCount){
						returnStatus = -1;
						throw new Exception("Error while updating IS_LATEST in Transaction Table for ServiceId "+dto.getServiceId()+" in Manual OB ");
					}
					//22/12/14 closing db objects by Nagarjuna
					try{
						insertCounts = pstmt2.executeBatch();
						}finally{
								DbConnection.closePreparedStatement(pstmt2);
								}
					if(insertCounts.length != insertOBTransactionCount){
						returnStatus = -1;
						throw new Exception("Error while inserting data in OB_TRANSACTION for Approval of ServiceId "+dto.getServiceId()+" in Manual OB");
					}
					//22/12/14 closing db objects by Nagarjuna
					try{
						insertCounts = pstmt3.executeBatch();
						}finally{
								DbConnection.closePreparedStatement(pstmt3);
								}
					if(insertCounts.length != insertOBTransactionCount){
						returnStatus = -1;
						throw new Exception("Error while updating data in TChargeInfo for BulkUpload of ServiceId "+dto.getServiceId()+" in Manual OB ");
					}
					//22/12/14 closing db objects by Nagarjuna
					try{
						insertCounts = pstmt4.executeBatch();
						}finally{
								DbConnection.closePreparedStatement(pstmt4);
								}
					if(insertCounts.length != insertOBTransactionCount){
						returnStatus = -1;
						throw new Exception("Error while updating data in TChargeInfo_History for BulkUpload of ServiceId "+dto.getServiceId()+" in Manual OB ");
					}
				}
			}else if (dto.getAction().equals(ManualOB_Component)){

				/* 
				 * Fetching component details for OB Calculation
				 */
				//22/12/14 closing db objects by Nagarjuna
			try{
				callstmt= connection.prepareCall(sqlGetCompOBDetailsForBulkUpload);	
				callstmt.setLong(1, dto.getFileId());
				callstmt.setLong(2, dto.getServiceId());
				rs=callstmt.executeQuery();

				while (rs.next()){
					obCalculationDTO = new OBCalculationDTO();
					obCalculationDTO.setComponentInfoId(rs.getLong("COMPONENT_ID"));
					obCalculationDTO.setNetObId(rs.getLong("NET_OB_ID"));
					obCalculationDTO.setObValue(rs.getDouble("OB_VALUE"));
					obCalculationDTO.setCopcApprovalDate(dto.getCopcApprovalDate());
					arrayListOBCalculationDTO_Component.add(obCalculationDTO);	
				}
				}finally{
						DbConnection.closeResultset(rs);
						DbConnection.closeCallableStatement(callstmt);
						}
				/* 
				 * Preparing Batch for NET_OB generation for new Component
				 */
				pstmt5 = connection.prepareStatement(queryForInsertOB_NetWithTentativeOBDates);
				for(Iterator<OBCalculationDTO> iterator=arrayListOBCalculationDTO_Component.iterator();iterator.hasNext();){
					OBCalculationDTO element = (OBCalculationDTO) iterator.next();
					if(element.getNetObId()==0 ){

						java.sql.Date  obEndDate =  (java.sql.Date) element.getCopcApprovalDate();
						Calendar cal = Calendar.getInstance();
						cal.setTime(obEndDate);
						cal.set(Calendar.MONTH, (cal.get(Calendar.MONTH)+12));
						obEndDate =  new java.sql.Date(cal.getTime().getTime()); 

						pstmt5.setLong(1,element.getComponentInfoId());
						pstmt5.setDate(2,(java.sql.Date) element.getCopcApprovalDate());
						pstmt5.setDate(3,(java.sql.Date) obEndDate);
						pstmt5.addBatch();
						insertObBatchCount++;
					}
				}
				
				if(insertObBatchCount>0){
					//22/12/14 closing db objects by Nagarjuna
					try{
						insertCounts = pstmt5.executeBatch();
						}finally{
								DbConnection.closePreparedStatement(pstmt5);
								}
					if(insertCounts.length != insertObBatchCount){
						returnStatus = -1;
						throw new Exception("Error while inserting data in OB_NET table of components for ServiceId "+dto.getServiceId());
					} else{
						returnStatus = 1;
					}
				}
				
				/*
				 * If Insertion successful, then fetching NET_OB_ID against new components
				 */
				//22/12/14 closing db objects by Nagarjuna
				try{
					callstmt= connection.prepareCall(sqlGetNetOBIds_Component);	
					callstmt.setLong(1, dto.getServiceId());
					rs=callstmt.executeQuery();
					hashMap = new HashMap<Long, Long>();
					while (rs.next()){
						hashMap.put(rs.getLong("COMPONENTINFOID"), rs.getLong("NET_OB_ID"));
					}
				}finally{
						DbConnection.closeResultset(rs);
						DbConnection.closeCallableStatement(callstmt);
						}
				
				
				pstmt = connection.prepareStatement(queryForInsertOB_transaction_Reversal_Charge);
				pstmt1 = connection.prepareStatement(queryForIsLatestUpdate_Charge);
				pstmt2 = connection.prepareStatement(queryForInsertOB_Transaction_Approval);
				pstmt3 = connection.prepareStatement(queryForUpdate_ManualCompOB);
				pstmt4 = connection.prepareStatement(queryForUpdate_ManualCompHistoryOB);
				for(Iterator<OBCalculationDTO> iterator=arrayListOBCalculationDTO_Component.iterator();iterator.hasNext();){
					OBCalculationDTO element = (OBCalculationDTO) iterator.next();

					insertOBTransactionCount++;
					pstmt.setLong(1,element.getComponentInfoId());
					pstmt.addBatch();

					pstmt1.setLong(1,element.getComponentInfoId());
					pstmt1.addBatch();

					if(element.getNetObId()==0 )
						pstmt2.setLong(1,hashMap.get(element.getComponentInfoId()));
					else
						pstmt2.setLong(1,element.getNetObId());
					pstmt2.setLong(2,dto.getOrderNo());
					pstmt2.setLong(3,dto.getServiceId());
					pstmt2.setLong(4,dto.getLogicalSINumber());
					pstmt2.setLong(5,element.getComponentInfoId());
					pstmt2.setString(6,formatter.format(dto.getCopcApprovalDate()));
					pstmt2.setDouble(7, element.getObValue());
					pstmt2.setDouble(8,1);
					pstmt2.setString(9, "Manual");
					pstmt2.setInt(10, 1);
					pstmt.setString(11,formatterYear.format(dto.getCopcApprovalDate()));
					pstmt2.addBatch();

					pstmt3.setDouble(1,element.getObValue());
					if(element.getNetObId()==0 )
						pstmt3.setLong(2,hashMap.get(element.getComponentInfoId()));
					else
						pstmt3.setLong(2,element.getNetObId());
					pstmt3.setLong(3,element.getComponentInfoId());
					pstmt3.addBatch();

					pstmt4.setDouble(1,element.getObValue());
					if(element.getNetObId()==0 )
						pstmt4.setLong(2,hashMap.get(element.getComponentInfoId()));
					else
						pstmt4.setLong(2,element.getNetObId());
					pstmt4.setLong(3,element.getComponentInfoId());
					pstmt4.setLong(4,dto.getServiceId());
					pstmt4.addBatch();
				}
				if(insertOBTransactionCount>0){
					//22/12/14 closing db objects by Nagarjuna
					try{
						insertCounts = pstmt.executeBatch();
						}finally{
								DbConnection.closePreparedStatement(pstmt);
								}
					if(insertCounts.length != insertOBTransactionCount){
						returnStatus = -1;
						throw new Exception("Error while doing reversal in Transaction Table in case of Manual OB(Component) for ServiceId "+dto.getServiceId());
					}
					//22/12/14 closing db objects by Nagarjuna
					try{
						insertCounts = pstmt1.executeBatch();
						}finally{
								DbConnection.closePreparedStatement(pstmt1);
								}
					
					if(insertCounts.length != insertOBTransactionCount){
						returnStatus = -1;
						throw new Exception("Error while updating IS_LATEST in Transaction Table for ServiceId "+dto.getServiceId()+" in Manual OB(Component) ");
					}
					//22/12/14 closing db objects by Nagarjuna
					try{
						insertCounts = pstmt2.executeBatch();
						}finally{
								DbConnection.closePreparedStatement(pstmt2);
								}
					if(insertCounts.length != insertOBTransactionCount){
						returnStatus = -1;
						throw new Exception("Error while inserting data in OB_TRANSACTION for BulkUpload of ServiceId "+dto.getServiceId()+" in Manual OB(Component) ");
					}

					//22/12/14 closing db objects by Nagarjuna
					try{
						insertCounts = pstmt3.executeBatch();
						}finally{
								DbConnection.closePreparedStatement(pstmt3);
								}
					if(insertCounts.length != insertOBTransactionCount){
						returnStatus = -1;
						throw new Exception("Error while updating data in TComponentInfo for BulkUpload of ServiceId "+dto.getServiceId()+" in Manual OB(Component) ");
					}
					//22/12/14 closing db objects by Nagarjuna
					try{
						insertCounts = pstmt4.executeBatch();
						}finally{
								DbConnection.closePreparedStatement(pstmt4);
								}
					if(insertCounts.length != insertOBTransactionCount){
						returnStatus = -1;
						throw new Exception("Error while updating data in TComponentInfo_History for BulkUpload of ServiceId "+dto.getServiceId()+" in Manual OB(Component) ");
					}
				}
			}else if (dto.getAction().equals(Rejection_Component) || dto.getAction().equals(M6Cancel_Component) 
					|| dto.getAction().equals(CancelAndCopy_Component) || dto.getAction().equals(ServiceCancel_Component)){
				
				/*
				 * Getting componentId for reversal of Manual OB
				*/
				//22/12/14 closing db objects by Nagarjuna
				try{
					callstmt= connection.prepareCall(sqlGetComponentInfoIdForReversal);	
					callstmt.setLong(1, dto.getServiceId());
					rs=callstmt.executeQuery();
	
					while (rs.next()){
						obCalculationDTO = new OBCalculationDTO();
						obCalculationDTO.setComponentInfoId(rs.getLong("CHARGEINFOID"));
						obCalculationDTO.setCopcApprovalDate(dto.getCopcApprovalDate());
						arrayListOBCalculationDTO_Component.add(obCalculationDTO);	
					}
				}finally{
						DbConnection.closeResultset(rs);
						DbConnection.closeCallableStatement(callstmt);
						}
				
				/*
				 * Preparing statements for updating manualOb to 0 for above selected component
				*/
				pstmt3 = connection.prepareStatement(queryForUpdate_ManualCompOB_Reversal);
				pstmt4 = connection.prepareStatement(queryForUpdate_ManualCompHistoryOB_Reversal);
				
				for(Iterator<OBCalculationDTO> iterator=arrayListOBCalculationDTO_Component.iterator();iterator.hasNext();){
					OBCalculationDTO element = (OBCalculationDTO) iterator.next();

					insertOBTransactionCount++;
					pstmt3.setLong(1,element.getComponentInfoId());
					pstmt3.addBatch();

					pstmt4.setLong(1,element.getComponentInfoId());
					pstmt4.setLong(1,dto.getServiceId());
					pstmt4.addBatch();
				}
				
				if(insertOBTransactionCount>0){
					//22/12/14 closing db objects by Nagarjuna
					try{
						insertCounts = pstmt3.executeBatch();
						}finally{
								DbConnection.closePreparedStatement(pstmt3);
								}
					if(insertCounts.length != insertOBTransactionCount){
						returnStatus = -1;
						throw new Exception("Error while doing reversal in Transaction Table in case of Manual OB(Component) for ServiceId "+dto.getServiceId());
					}else{
						returnStatus = 1;
					}
					//22/12/14 closing db objects by Nagarjuna
					try{
						insertCounts = pstmt4.executeBatch();
						}finally{
								DbConnection.closePreparedStatement(pstmt4);
								}
					if(insertCounts.length != insertOBTransactionCount){
						returnStatus = -1;
						throw new Exception("Error while updating IS_LATEST in Transaction Table for ServiceId "+dto.getServiceId()+" in Manual OB(Component) ");
					}
				}
				
				/*
				 * Reversing latest OB Value for all components of current service
				 */
				try{
					pstmt=connection.prepareStatement(queryForInsertOB_transaction_Reversal_Service);
					pstmt.setString(1, formatter.format(dto.getCopcApprovalDate()));
					pstmt.setString(2, formatterYear.format(dto.getCopcApprovalDate()));
					pstmt.setLong(3,dto.getServiceId());
					pstmt.executeUpdate();
					returnStatus = 1;
				}catch (SQLException e) {
					returnStatus = -1;
					throw new Exception("Error while doing reversal of components in Transaction Table for ServiceId "+dto.getServiceId());
				}finally{
					try{
						if(pstmt!=null)DbConnection.closePreparedStatement(pstmt);
					}catch(Exception ex){
						ex.printStackTrace();
					}
				}

				/*
				 * Updating IS_LATEST in OB_TRANSACTION to Zero for all components in current service
				 */
				try{
					pstmt = connection.prepareStatement(queryForIsLatestUpdate);
					pstmt.setLong(1,dto.getServiceId());
					pstmt.executeUpdate();
				}catch (SQLException e) {
					returnStatus = -1;
					throw new Exception("Error while updating IS_LATEST of components in Transaction Table for ServiceId "+dto.getServiceId());
				}finally{
					try{
						if(pstmt!=null)DbConnection.closePreparedStatement(pstmt);
					}catch(Exception ex){
						ex.printStackTrace();
					}
				}
			}
		}catch(Exception e){
			Utility.LOG(true,true,e,null);
			returnStatus = -1;
			dto.setException(e);
			String message=logIdentifier+"Exception for serviceId :"+dto.getServiceId()+" and Row Id :"+dto.getRowId();
			Utility.LOG(true,true,message);
			dto.setExceptionMessage(message);
		}finally{
			try{
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(callstmt);
			}catch (SQLException e){
				Utility.LOG(true,true,e,null);
			}
		}
		dto.setReturnStatus(returnStatus);
	}

	private void updateSuccesFailureStatus(Connection connection,OBCalculationDTO calculationDTO) throws Exception{

		if(calculationDTO.getReturnStatus()==0 || calculationDTO.getReturnStatus()==1)//SUCCESS
		{
			CallableStatement cstmt_Service_Success = null;
			try{
				cstmt_Service_Success=connection.prepareCall(sqlOB_SCHEDULER_SUCCESS);
				cstmt_Service_Success.setLong(1, calculationDTO.getRowId());
				cstmt_Service_Success.setString(2, (calculationDTO.getReturnStatus()==1)?"S":"N");
				cstmt_Service_Success.setLong(3,calculationDTO.getFileId());
				cstmt_Service_Success.executeUpdate();
			}finally{
				if(cstmt_Service_Success!=null)DbConnection.closeCallableStatement(cstmt_Service_Success) ;
			}
		}else{
			CallableStatement cstmt_Service_Failure = null;
			try{
				cstmt_Service_Failure=connection.prepareCall(sqlOB_SCHEDULER_FAILURE);
				cstmt_Service_Failure.setLong(1, calculationDTO.getRowId());
				java.sql.Clob clobData = com.ibm.db2.jcc.t2zos.DB2LobFactory.createClob(Utility.getStackTrace(calculationDTO.getException()));
				cstmt_Service_Failure.setClob(2, clobData );
				cstmt_Service_Failure.setString(3, calculationDTO.getExceptionMessage());
				cstmt_Service_Failure.executeUpdate();
			}finally{
				if(cstmt_Service_Failure!=null)DbConnection.closeCallableStatement(cstmt_Service_Failure) ;
			}
		}
	}

	public OBCalculationDTO calculateOBValueForNewCharge(OBCalculationDTO element, OBCalculationDTO dto) {

		double obValue = 0;
		double newChargeAmount = element.getNewChargeAmount();
		double chargePeriod = element.getChargePeriod();
		
		if (dto.getAction().equals(COPC_Approval) || dto.getAction().equals(BULK_Approval)){
			if(element.getChargeType()==1 && element.getChargeStartDateMonth()<12){
				//New RC Charge
				if(chargePeriod>12){
					obValue = newChargeAmount/chargePeriod*12;
					element.setCalculatedTentativeObStartDate(element.getCopcApprovalDate());
					element.setCalculatedTentativeObEndDate(calcEndDate(element.getCopcApprovalDate(),chargePeriodMax12((int)chargePeriod)));
					element.setObComputed(true);
				}else{
					obValue = newChargeAmount;
					element.setCalculatedTentativeObStartDate(element.getCopcApprovalDate());
					element.setCalculatedTentativeObEndDate(calcEndDate(element.getCopcApprovalDate(),chargePeriodMax12((int)chargePeriod)));
					element.setObComputed(true);
				}
			}else if (element.getChargeType()==2){
				//New NRC Charge
				obValue = newChargeAmount;
				element.setCalculatedTentativeObStartDate(element.getCopcApprovalDate());
				element.setCalculatedTentativeObEndDate(calcEndDate(element.getCopcApprovalDate(),chargePeriodMax12((int)chargePeriod)));
				element.setObComputed(true);
			}
			element.setObValue(obValue) ;
		}
		else if (dto.getAction().equals(BillingTriggerEnds)){
			setCalcActualOBDates(element,element.getChargeStartDate(),calcEndDate(element.getChargeStartDate(),chargePeriodMax12((int)chargePeriod)));
		}
		return element;
	}
	
	public OBCalculationDTO calculateOBValueForDisconnectedCharge(OBCalculationDTO element) {

		double obValue = 0;

		/*
		 * We will calculate OB for disconnection order, when charge is disconnected before initial duration actual OB end date and it's a recurring charge
		 * If manual Ob is uploaded against charge , then reversal on Manual OB , else Charge Amount
		 * 
		 * Else we will check if charge is disconnected before ActualOBEndDate, then will give reversal on previous OB
		*/
		if(element.getChargeType()==1){
			if(element.getCopcApprovalDate().getTime()<element.getInitialDurationActualObEndDate().getTime()){
				if(element.getManualOB()!= 0){
					obValue = -1*element.getManualOB()*
							daysBetween(element.getCopcApprovalDate(),element.getInitialDurationActualObEndDate())/(365*(chargePeriodMax12((int)element.getChargePeriod()))/12);
				}else{
					obValue=-1*element.getNewChargeAmount()*
							daysBetween(element.getCopcApprovalDate(),element.getInitialDurationActualObEndDate())/(365*(chargePeriodMax12((int)element.getChargePeriod()))/12);
				}
				element.setObComputed(true);
			}else if (element.getCopcApprovalDate().getTime()<element.getActualObEndDate().getTime()){
				obValue=-1*element.getSystemOB()*
						daysBetween(element.getCopcApprovalDate(),element.getActualObEndDate())/(365*(chargePeriodMax12((int)element.getChargePeriod()))/12);
				element.setObComputed(true);
			}
		}
		element.setObValue(obValue);
		return element;
	}
	
	private OBCalculationDTO calculateOBValueForLinkedCharge(OBCalculationDTO element, OBCalculationDTO dto) {

		double obValue=0;
		double newChargeAmount = element.getNewChargeAmount();
		double oldChargeAmount = element.getOldChargeAmount();
		double chargePeriod = element.getChargePeriod();
		
		if(element.getSubChangeTypeId()==9){// For Downgrade Orders
			if(element.getCopcApprovalDate().getTime()>element.getOldActualObStartDate().getTime() && 
					element.getCopcApprovalDate().getTime()<element.getOldActualObEndDate().getTime()){
				/*
				 * Between previous OB Period
				 */
				if (dto.getAction().equals(COPC_Approval) || dto.getAction().equals(BULK_Approval)){
					obValue = (generateARC(newChargeAmount,chargePeriod) - generateARC(oldChargeAmount,chargePeriod))*
							daysBetween(element.getCopcApprovalDate(),element.getOldActualObEndDate())/(365*(chargePeriodMax12((int)chargePeriod))/12);
					setCalcTentativeOBDates(element, element.getCopcApprovalDate(), element.getOldActualObEndDate());
				}
				else if(dto.getAction().equals(BillingTriggerEnds)){
					setCalcActualOBDates(element,element.getCopcApprovalDate(),element.getOldActualObEndDate());
				}
				
			}else if(element.getCopcApprovalDate().getTime()<=element.getOldActualObStartDate().getTime()){
				/*
				 * Before Previous OB Period Start Date
				 */
				if (dto.getAction().equals(COPC_Approval) || dto.getAction().equals(BULK_Approval)){
					obValue = generateARC(newChargeAmount,chargePeriod) - generateARC(oldChargeAmount,chargePeriod);
					setCalcTentativeOBDates(element, element.getCopcApprovalDate(), calcEndDate(element.getCopcApprovalDate(),chargePeriodMax12((int)chargePeriod)));
				}else if(dto.getAction().equals(BillingTriggerEnds) && element.getChargeStartDate()!=null){
					setCalcActualOBDates(element,element.getChargeStartDate(),calcEndDate(element.getChargeStartDate(),chargePeriodMax12((int)chargePeriod)));
				}
				
			}else if(element.getCopcApprovalDate().getTime()>=element.getOldActualObEndDate().getTime()){
				/*
				 * After Previous OB Period End Date
				 */
				if (dto.getAction().equals(COPC_Approval) || dto.getAction().equals(BULK_Approval)){
					obValue = 0;
					setCalcTentativeOBDates(element, element.getCopcApprovalDate(), calcEndDate(element.getCopcApprovalDate(),chargePeriodMax12((int)chargePeriod)));
				}else if(dto.getAction().equals(BillingTriggerEnds) && element.getChargeStartDate()!=null){
					setCalcActualOBDates(element,element.getChargeStartDate(),calcEndDate(element.getChargeStartDate(),chargePeriodMax12((int)chargePeriod)));
				}
			}
		}else if (element.getSubChangeTypeId() ==8 || element.getSubChangeTypeId() ==10 || element.getSubChangeTypeId() ==12 || element.getSubChangeTypeId() ==5){
			//Upgrade, Shifting, Demo Regularize, Rate Revision
			if(element.getCopcApprovalDate().getTime()>element.getOldActualObStartDate().getTime() && 
					element.getCopcApprovalDate().getTime()<element.getOldActualObEndDate().getTime()){
				/*
				 * Between previous OB Period
				 */
				
				if(newChargeAmount>=oldChargeAmount)
				{
					if (dto.getAction().equals(COPC_Approval) || dto.getAction().equals(BULK_Approval)){
						obValue = (generateARC(newChargeAmount,chargePeriod) - generateARC(oldChargeAmount,chargePeriod));
						
						setCalcTentativeOBDates(element, element.getCopcApprovalDate(), element.getOldActualObEndDate());
						//element.setCalculatedTentativeObStartDate(element.getCopcApprovalDate());
						//element.setCalculatedTentativeObEndDate(calcEndDate(element.getCopcApprovalDate(),chargePeriodMax12((int)chargePeriod)));
						element.setObComputed(true);
					}else if(dto.getAction().equals(BillingTriggerEnds) && element.getChargeStartDate()!=null){
						setCalcActualOBDates(element,element.getChargeStartDate(),calcEndDate(element.getChargeStartDate(),chargePeriodMax12((int)chargePeriod)));
					}
					
				}else{
					if (dto.getAction().equals(COPC_Approval) || dto.getAction().equals(BULK_Approval)){
						obValue = (generateARC(newChargeAmount,chargePeriod) - generateARC(oldChargeAmount,chargePeriod))*
								daysBetween(element.getCopcApprovalDate(),element.getOldActualObEndDate())/(365*(chargePeriodMax12((int)chargePeriod))/12);
						setCalcTentativeOBDates(element, element.getCopcApprovalDate(), element.getOldActualObEndDate());
					}else if(dto.getAction().equals(BillingTriggerEnds)){
						setCalcActualOBDates(element,element.getCopcApprovalDate(),element.getOldActualObEndDate());
					}
				}
				
			}else if(element.getCopcApprovalDate().getTime()<=element.getOldActualObStartDate().getTime()){
				/*
				 * Before Previous OB Period Start Date
				 */
				if(newChargeAmount>=oldChargeAmount){
					
					if (dto.getAction().equals(COPC_Approval) || dto.getAction().equals(BULK_Approval)){
						obValue = generateARC(newChargeAmount,chargePeriod) - generateARC(oldChargeAmount,chargePeriod);
						setCalcTentativeOBDates(element, element.getCopcApprovalDate(), calcEndDate(element.getCopcApprovalDate(),chargePeriodMax12((int)chargePeriod)));
					}else if(dto.getAction().equals(BillingTriggerEnds) && element.getChargeStartDate()!=null){
						setCalcActualOBDates(element,element.getChargeStartDate(),calcEndDate(element.getChargeStartDate(),chargePeriodMax12((int)chargePeriod)));
					}
				}else{
					if (dto.getAction().equals(COPC_Approval) || dto.getAction().equals(BULK_Approval)){
						obValue = generateARC(newChargeAmount,chargePeriod) - generateARC(oldChargeAmount,chargePeriod);
						
						setCalcTentativeOBDates(element, element.getCopcApprovalDate(), calcEndDate(element.getCopcApprovalDate(),chargePeriodMax12((int)chargePeriod)));
					}else if(dto.getAction().equals(BillingTriggerEnds) && element.getChargeStartDate()!=null){
						setCalcActualOBDates(element,element.getChargeStartDate(),calcEndDate(element.getChargeStartDate(),chargePeriodMax12((int)chargePeriod)));
					}
				}
				
			}else if(element.getCopcApprovalDate().getTime()>=element.getOldActualObEndDate().getTime()){
				/*
				 * After Previous OB Period End Date
				 */
				if(newChargeAmount>=oldChargeAmount){
					
					if (dto.getAction().equals(COPC_Approval) || dto.getAction().equals(BULK_Approval)){
						obValue = (generateARC(newChargeAmount,chargePeriod) - generateARC(oldChargeAmount,chargePeriod));
						setCalcTentativeOBDates(element,element.getCopcApprovalDate(),calcEndDate(element.getCopcApprovalDate(),chargePeriodMax12((int)chargePeriod)));
					}else if(dto.getAction().equals(BillingTriggerEnds) && element.getChargeStartDate()!=null){
						setCalcActualOBDates(element,element.getChargeStartDate(),calcEndDate(element.getChargeStartDate(),chargePeriodMax12((int)chargePeriod)));
					}
				}else{
					if (dto.getAction().equals(COPC_Approval) || dto.getAction().equals(BULK_Approval)){
						obValue = 0;
						setCalcTentativeOBDates(element, element.getCopcApprovalDate(), calcEndDate(element.getCopcApprovalDate(),chargePeriodMax12((int)chargePeriod)));
					}else if(dto.getAction().equals(BillingTriggerEnds) && element.getChargeStartDate()!=null){
						setCalcActualOBDates(element,element.getChargeStartDate(),calcEndDate(element.getChargeStartDate(),chargePeriodMax12((int)chargePeriod)));
					}
				}
			}
		}
		
		element.setObValue(obValue);
		return element;
	}

	public double generateARC(double chargeAmount, double chargePeriod){
		if(chargePeriod>12){
			chargeAmount = chargeAmount*12/chargePeriod;
		}
		return chargeAmount;
	}
	
	public int chargePeriodMax12(int chargePeriod){
		if(chargePeriod>12){
			chargePeriod = 12;
		}
		return chargePeriod;
	}
	
	public Date calcEndDate(Date startDate, int period){
		java.sql.Date  endDate =  (java.sql.Date) startDate;
		Calendar cal = Calendar.getInstance();
		cal.setTime(endDate);
		cal.set(Calendar.MONTH, (cal.get(Calendar.MONTH)+period));
		endDate =  new java.sql.Date(cal.getTime().getTime());
		return endDate ;
	}
	
	public void setCalcTentativeOBDates(OBCalculationDTO element, Date startDate, Date endDate){
		element.setCalculatedTentativeObStartDate(startDate);
		element.setCalculatedTentativeObEndDate(endDate);
		element.setCalculatedInitialDurationNetObId(element.getOldInitialDurationNetObId());
		element.setObComputed(true);
	}
	
	public void setCalcActualOBDates(OBCalculationDTO element, Date startDate, Date endDate){
		element.setCalculatedActualObStartDate(startDate);
		element.setCalculatedActualObEndDate(endDate);
		element.setObComputed(true);
	}
	
	
	/*
	 * This method isSameFinancialYear(Date date1, Date date2) will check
	 * if two dates are in same financial year or different
	*/
	@SuppressWarnings("deprecation")
	public boolean isSameFinancialYear(Date date1, Date date2){
		
		date1 = getFirstDateOfFinancialYear(date1);
		date2 = getFirstDateOfFinancialYear(date2);
		
		if(date1.getDay()==date2.getDay()){
			return true;
		}
		return false;
	}
	
	/*
	 * This method getFirstDateOfFinancialYear(Date date) will return 
	 * 1st day of current financial year
	*/
	public Date getFirstDateOfFinancialYear(Date date){
		
		Calendar cal = Calendar.getInstance();
        cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		
		if(month>= 3){ //Here in calendar , month start from 0,1,2 for jan, feb , mar .... so on
			//Setting date as Same year 1st April
			cal.set(Calendar.YEAR, year);
	        cal.set(Calendar.MONTH, 3);
	        cal.set(Calendar.DAY_OF_MONTH, 1);
		}else{
			//Setting date as Previous year 1st April
			cal.set(Calendar.YEAR, year-1);
	        cal.set(Calendar.MONTH, 3);
	        cal.set(Calendar.DAY_OF_MONTH, 1);
		}
		
		date = cal.getTime();
		
		return date;
	}
	
	/*
	 * This method isDateDifferenceLessThanPeriod(Date date1,Date date2,int period)
	 * will check, whether 1st Date comes after 2nd date, even after adding mentioned period to 1st date or not
	*/
	public boolean isDateDifferenceLessThanPeriod(Date date1,Date date2,int period){
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date1);
		cal.add(Calendar.MONTH, period);
		
		date1 = cal.getTime();
		
		if(date1.after(date2)){
			return true;
		}
		
		return false;
	}
	
	@SuppressWarnings("deprecation")
	public int getDiffInMonth(Date startDate, Date endDate) {
		int previousChargeBillingPeriod=(endDate.getYear()-startDate.getYear())*12+(endDate.getMonth()-startDate.getMonth()+1);
		return previousChargeBillingPeriod;
	}
	
	public int daysBetween(Date StartDate, Date EndDate){
        return (int)( (EndDate.getTime() - StartDate.getTime()) / (1000 * 60 * 60 * 24));
	}

	/**
	 * @author Vijay
	 * @param orderOrServiceNo
	 * @param isOrderNo
	 * @param action	
	 * @param isValidationRequired
	 * @param connection
	 * @return boolean value that denote the status of successfully insertion. 
	 * 		If status is true that means data inserted successfully
	 */
	public static boolean validateAndsaveDataIntoObScheduler(long orderOrServiceNo, int isOrderNo, String action, int isValidationRequired, Connection connection){
		String methodName="validateAndsaveDataIntoObScheduler", className="OBCalculationDao", msg="";
		boolean logToFile=true, logToConsole=true;
		boolean saveStatus = false;
		CallableStatement stmtInsertOBSchedulerdata=null;
		int errorCode = 0;
		try{

			stmtInsertOBSchedulerdata=connection.prepareCall(sqlValAndInsObSchedulerData);

			stmtInsertOBSchedulerdata.setLong(1, orderOrServiceNo);//Order No or Service No
			stmtInsertOBSchedulerdata.setInt(2, isOrderNo);   // is order no passing. This value should be 1(in case of order no) or 0(in case of service no)
			stmtInsertOBSchedulerdata.setString(3, action);
			stmtInsertOBSchedulerdata.setInt(4, isValidationRequired);
			stmtInsertOBSchedulerdata.setInt(5, 0);  //SQLSTATE 
			stmtInsertOBSchedulerdata.setInt(6, 0);	// ERROR CODE
			stmtInsertOBSchedulerdata.setString(7, ""); // ERROR MESSAGE
			stmtInsertOBSchedulerdata.execute();

			errorCode = stmtInsertOBSchedulerdata.getInt(6);
			msg =  stmtInsertOBSchedulerdata.getString(7);
			if(errorCode == 0){
				saveStatus = true ;
			}

		}
		catch(Exception ex){
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);
		}
		finally
		{
			try 
			{
				DbConnection.closeCallableStatement(stmtInsertOBSchedulerdata);
			} 
			catch (Exception ex) 
			{
				Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);
			}
		}

		return saveStatus;
	}
}