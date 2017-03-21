package com.ibm.ioes.dao;
//[001]	 Raghu	10-Mar-11	00-05422		Publish Button Enable and disable
//[002]	 Manisha Garg	24-jun-11	00-05422		ADD SOME NEW COLUMN IN GET_BT_PRODUCT_NEW_ORDER
//[003]	 Manisha Garg	24-jun-11	00-05422		ADD SOME NEW COLUMN IN GET_BT_PRODUCT_CHANGE_ORDER
//[004]	 Ashutosh SING	29-SEP-11	00-05422		UPDATE LOC_DATE AS CURRENT DATE FOR NON M6 SERVICE LINE
//[077]    ASHUTOSH SINGH  01-DEC-11  00-05422        Changes in PO AMOUNT(Decimal validation) 
//[078]    Manisha Garg    24-Feb-12  00-05422         Get Charge Data For Charge Summary Section
//[079]    Manisha Garg    24-Feb-12  00-05422         Get Component Data For Charge Summary Section
//[080]	 Manisha Garg	01-oct-12	00-05422		To get Line Items for Billing Trigger BulkUpload
//[081]	 Manisha Garg	01-oct-12	00-05422		To get Charge Items for Billing Trigger BulkUpload
//[082]    Lawkush  30-MAR-13  BUG ID TRNG22032013005 fixed
//[006]	 Rohit Verma	5-Sep-13	09112			Adding PO Expiry Date and Billing Trigger Action Date in Billing Trigger Screen
//[010]  Gunjan Singla  22-sep-14  CBR_20140704-XX-020224     Global Data Billing Efficiencies Phase1
//[011]  Gunjan Singla  7-Jan-15         20141113-R1-020802		ParallelUpgrade-Multiple LSI Selection
//[012]  Pankaj Thakur  3-Jun-15     Added a function logExceptionForBillingTrigger() to get the Exception for Components Billing Trigger Date
//[013]  Gunjan Singla  20-Oct-15    Charge End Date Modification 
//[014]  Gunjan Singla  22-Oct-2016  20160219-XX-022117   CBR-ANG bandwidth correction in iB2B and M6
import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.apache.struts.util.LabelValueBean;

import com.ibm.fx.dto.ChargesDto;
import com.ibm.fx.dto.ComponentDTO;
import com.ibm.fx.dto.ExtendedData;
import com.ibm.fx.dto.FxExternalIdDto;
import com.ibm.fx.dto.NrcDto;
import com.ibm.fx.dto.RcDto;
import com.ibm.fx.dto.ServiceDTO;
import com.ibm.fx.mq.IOESKenanManager;
import com.ibm.ioes.beans.BillingTriggerValidation;
import com.ibm.ioes.beans.ViewOrderDto;
import com.ibm.ioes.ei.m6.M6Listener;
import com.ibm.ioes.exception.IOESException;
import com.ibm.ioes.forms.ChargeSummaryChargeSection;
import com.ibm.ioes.forms.ChargeSummaryComponentSection;
import com.ibm.ioes.forms.ChargeSummaryProductCatelog;
import com.ibm.ioes.forms.ChargeSummaryServiceDto;
import com.ibm.ioes.forms.ComponentsDto;
import com.ibm.ioes.forms.OrderHeaderDTO;
import com.ibm.ioes.forms.PagingDto;
import com.ibm.ioes.forms.PoDetailsDTO;

import com.ibm.ioes.forms.NewOrderDto;
import com.ibm.ioes.forms.ViewOrderFormBean;
import com.ibm.ioes.newdesign.dto.DifferentialCreditNoteReportDTO;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Utility;

public class ViewOrderDao {

	//To Fetch Task List of Order from Database	
	// added by sandeep for rateranewal disconnection
	private static String sqlUpdate_BT_STATUS = "UPDATE IOE.TPOSERVICEMASTER TPOSERVICEMASTER SET TPOSERVICEMASTER.M6_FX_PROGRESS_STATUS = 'FX_BT_END' WHERE TPOSERVICEMASTER.SERVICEID = ?";
	private static String sqlGet_BT_SERVICES_CHANGE_ORDERS="{CALL IOE.FX_GET_SERVICES_FOR_ORDER(?)}";
	private static String sqlGet_OrderType="{CALL IOE.FX_GET_ORDERTYPE(?,?)}";
	private static String sqlGet_ValidateBillingTriggerDate= "{CALL IOE.FX_VALIDATE_BILLING_TRIGGER_DATE(?,?,?,?)}";
	private static String sqlGet_ValidateBillingTriggerDate_bulkupload= "{CALL BULKUPLOAD.FX_VALIDATE_BILLING_TRIGGER_DATE_BULKUPLOAD(?)}";
	
	private static String sqlGet_Select_Charges_For_SolutionChange_Disconnection="{CALL IOE.FX_GET_CHARGES_FOR_SOLUTIONCHANGE_DISCONNECTION(?)}";
	private static String sqlGet_Select_Charges_For_Disconnection_For_RateRenewal="{CALL IOE.FX_GET_CHARGES_FOR_RATERENEWAL_DISCONNECTION(?)}";
	private static String sqlGet_Push_Charges_In_FX_For_Disconnection="{CALL IOE.FX_PUSH_CHARGES_FOR_DISCONNECTION(?,?,?,?,?,?,?)}";
	//private static String sqlGet_Push_charges_In_FX_For_SOLUTIONCHANGE_N_DISCONNECTION="{CALL IOE.FX_PUSH_CHARGES_FOR_SOLUTIONCHANGE_N_DISCONNECTION(?,?,?,?,?,?,?,?)}";
	private static String sqlGet_Push_charges_In_SecondaryTables_For_Change_Orders="{CALL IOE.FX_PUSH_CHARGES_IN_SECONDARY_TABLES_FOR_CHANGE_ORDERS(?,?,?,?,?,?,?,?,?,?,?)}";
	//*****************************************
//	Start[082]
	private String sql_INSERT_LOC_DATA="{CALL IOE.SPUPDATELOCDATA(?,?,?,?,?,?,?,?,?)}";
//	End[082]
	private String sp_update_fx_si_id="{CALL IOE.FX_UPDATE_FX_SI_ID(?)}";
	private String sql_update_loc_details="{CALL IOE.SP_UPDATE_LOC_DETAILS(?,?,?,?,?,?,?)}";
	private String sql_INSERT_LOC_DATA_AUTO="{CALL IOE.SPUPDATELOCDATA_AUTO(?,?,?,?,?)}";
	 private String sqlGetPushStatus="{CALL IOE.SP_GET_FX_PUSH_STATUS(?)}";
	private String sqlGet_Osn_List="{CALL IOE.SP_GET_OSN_DATA(?)}";
	public static String sp_insert_loc_data="{CALL IOE.SPINSERTDATAINTABLE(?)}";
	private static String sqlGet_SelectServiceDetails_InFx_ForChange="{CALL IOE.FX_GET_CHARGES_FOR_DISCONNECT(?)}";
	public static String sqlGet_TaskListOfOrder = "{CALL IOE.SPGETTASKWORKFLOWDETAILS(?)}";
	public static String sqlGet_TaskListHistoryOfOrder = "{CALL IOE.SPGETTASKWORKFLOWHISTORY(?)}";
	public static String sqlGet_TaskListHistoryOfOrder_all = "{CALL IOE.SPGETTASKWORKFLOWHISTORY_ALL_PAGING(?,?,?,?,?)}";
	public static String sqlGet_TaskListHistoryOfOrder_selected = "{CALL IOE.SPGETTASKWORKFLOWHISTORY_SELECTED_PAGING(?,?,?,?,?,?)}";
	public static String sqlGetServiceList = "{CALL IOE.GETSERVICELIST(?)}";
	public static String sqlapprovalTabVisible = "{CALL IOE.GETAPPROVALTABVISIBILITYSTATUS(?)}";
	public static String sqlGet_BillinmgTriggerResult="{CALL IOE.SPGETCHARGEBILLING(?)}";
	public static String sqlGet_BillinmgTriggerResult_Disconnection="{CALL IOE.SPGETCHARGEBILLING_DISCONNECTION(?)}";
	//public static String sqlGet_ChargeSummaryList="{CALL IOE.SP_GET_CHARGE_SUMMARY(?)}";
	public static String sqlInsert_PublishOrder="{CALL IOE.SP_TM6_NEWORDER_STATUS_INSERT(?, ?, ?, ?,?,?,?)}";
	public static String sqlGet_IsOrderPublishStatus="{CALL IOE.SP_GET_ORDER_PUBLISH_STATUS(?,?,?,?)}";
	public static String sqlGet_INTERNAL_ID="{CALL IOE.FX_GETINTERNAL_ID(?)}";
	//public static String sqlGet_chargeDeatilsForNew="{CALL IOE.SP_GET_CHARGE_DETAILS_FOR_NEW(?)}";
	//public static String sqlGet_chargeDeatilsForDisconnect="{CALL IOE.SP_GET_CHARGE_DETAILS_FOR_DISCONNECT(?)}";
	//public static String sqlGet_chargeDeatilsForRateRenewal="{CALL IOE.SP_GET_CHARGE_DETAILS_FOR_RATE_RENEWAL(?)}";
	public static String sqlGet_chargeDeatilsForAllOrders="{CALL IOE.SP_GET_CHARGE_DETAILS_FOR_ALL_Orders(?,?,?,?,?)}";
	//public static String sqlGet_SelectServiceDetails="{CALL IOE.SP_SELECT_SERVICE_DETAILS(?)}";
	//public static String sqlGet_SELECT_BT_PRODUCTS_NEWORDER="{CALL IOE.SP_SELECT_BT_PRODUCTS_NEWORDER(?)}";
	//public static String sqlGet_SELECT_BT_PRODUCTS_DISCONNECTION="{CALL IOE.SP_SELECT_BT_PRODUCTS_DISCONNECTION(?)}";
	//public static String sqlGet_Select_BT_PRODUCTS_RATERENEWAL="{CALL IOE.SP_SELECT_BT_PRODUCTS_RATERENEWAL(?)}";
	public static String sqlGet_Select_BT_PRODUCTS_CHANGE_ORDERS="{CALL IOE.SP_SELECT_BT_PRODUCTS_FOR_CHANGE_ORDERS(?,?,?,?,?,?,?)}";
	public static String sqlGet_SelectFx_Sttus_InFx_ForChange="{CALL IOE.FX_GET_FX_STATUS_FOR_DISCONNECT(?)}";
	public static String sqlGet_SelectChargeDetails="{CALL IOE.SP_SELECT_CHARGE_DETAILS(?)}";

	private static final String sqlGet_ChargeSummaryServicesList = "{CALL IOE.SP_GET_CHARGE_SUMMARY_SERVICES(?)}";
	private static final String sqlGet_ChargeSummaryProductCatelogList = "{call IOE.SP_GET_CHARGE_SUMMARY_PRODUCTCATELOGS(?)}";
	public static String sql_update_last_publish_index="{CALL IOE.SP_UPDATE_LAST_PUBLISH_INDEX(?)}";
	public static String sp_insert_account_info_fx="{CALL IOE.SP_INSERT_ACCOUNT_INFO_FX(?,?,?,?,?,?,?)}";
	private String sp_update_line_status="{call IOE.SP_UPDATE_LINE_STATUS(?)}";
	//For submission of Billing Triger
	public static String sqlInsert_ChargeInfoFx  = "{CALL IOE.SP_INSERT_CHARGE_INFO_FX(?,?)}";
	public static String sqlInsert_ServiceInfoFx = "{CALL IOE.SP_INSERT_SERVICE_INFO_FX(?,?)}";
	public static String sqlUpdate_LocDate       = "{CALL IOE.SP_UPDATE_LOCDATE(?,?,?,?,?)}";
	
	private static final Logger logger;
	//private static final String sqlInsert_ServiceAndChargesInfoFx = "{CALL IOE.SP_INSERT_UPDATE_SERVICE_N_CHARGES_FX(?,?,?,?)}";
	private static final String sqlInsert_ServiceAndChargesInfoFx = "{CALL IOE.SP_INSERT_UPDATE_SERVICE_N_CHARGES_FX(?,?,?,?,?)}";
	private static final String sqlInsert_Charges_AccountLevelInfoFx = "{call IOE.SP_INSERT_UPDATE_ACCOUNTLEVEL_CHARGES(?,?,?,?,?)}";
	public static final String sqlUpdateBilling_Trigger_Status = "{CALL IOE.SP_UPDATEBILLING_TRIGGER_STATUS(?,?)}";
	private static final String sqlGetFX_RC_Charges = "{call IOE.GET_FX_RC(?)}";
	private static final String sqlGetFX_NRC_Charges = "{call IOE.GET_FX_NRC(?)}";
	private static final String sqlSP_FX_GET_SERVICEDATA = "{call IOE.SP_FX_GET_SERVICEDATA(?)}";
	private static final String sqlUpdateRcCreatoinResponse = "{call IOE.FX_UPDATE_RC_RESPONSE(?,?)}";
	private static final String sqlUpdateNrcCreatoinResponse = "{call IOE.FX_UPDATE_NRC_RESPONSE(?,?)}";
	private static final String sqlUpdateFxServiceOnfailure = "{call IOE.FX_UPDATE_SERVICE_CREATE_FAILURE(?,?,?,?)}";
    private static final String sp_custom_activity_for_publish = "{call IOE.CUSTOM_ACTIVITY_FOR_PUBLISH(?,?,?,?)}";
    //[004] Start
    private static final String sp_update_locdate_for_publish = "{call IOE.FX_UPDATE_LOCDATE_FOR_NON_M6(?)}";
    //[004] End
    public static final int TFX_SERVICE_CREATE_PS_SUCCESS=3;
	public static final int TFX_SERVICE_CREATE_PS_FAILURE=2;
	private static final String sqlUpdateFxServiceOnSuccess = "{call IOE.FX_UPDATE_SERVICE_CREATE_SUCCESS(?,?,?,?,?,?)}";
	private static final String sqlInfo_ServiceProduct = "call IOE.GET_INFO_SERVICEPRODUCT(?)";
	private static final String sqlGetFX_Account_Level_RC_Create = "call IOE.FX_GetAccount_Level_RC_Create(?)";
	private static final String sqlGetFX_Account_Level_NRC_Create = "call IOE.FX_GetAccount_Level_NRC_Create(?)";
	private static final String sqlGetFX_Account_For_CHARGES = "call IOE.FX_GetAccount_For_CHARGES(?)";
	private static final String sqlUpdateFxAccLevelChargesOnSuccessFailure = "call IOE.FX_UPDATE_ACC_LEVEL_CHARGES_ONSUCCESS_FAILURE(?,?)";
	private static final String sqlGet_FX_Account_Creation_Status = "call IOE.FX_Get_Account_Creation_Status(?)";
	private static final String sqlGetOrderType = "call IOE.GET_ORDER_TYPE(?,?,?,?)";
	private static final String sp_update_gam_sync_status = "{call IOE.UPDATE_GAM_SYNC_STATUS(?)}";
	private static final String sqlGetBTScreenServicesList = "call IOE.SP_GET_BT_SCREEN_SERVICESLIST(?)";
	//private static final String sqlFx_CHG_Disconnect_Request = "{call IOE.FX_CO_DISCONNECT_REQUEST_SUBMIT(?,?)}";
	public static String sqlGetRegionList= "{call IOE.GETREGIONLIST()}";// To Fetch Data from TREGION Table
	public static String sqlUpdateMainViewOrder="{call IOE.SP_UPDATE_MAIN_VIEWORDER(?,?,?,?,?,?,?,?,?,?)}";
	public static String sqlGetTM6OrderNo="{call IOE.SP_GETORDER_TM6_NEWORDER_STATUS(?,?,?)}";
	public static String sqlInsert_PublishOrder_RateRenewal = "{CALL IOE.SP_TM6_NEWORDER_STATUS_INSERT_RATERENEWAL(?,?,?,?)}";//Added By Sauarabh for RateRenewal
	private String sqlUpdate_ORDER_STAGE="{CALL IOE.SPUPDATEORDER_STAGE(?,?,?,?)}";
	public static String sqlGet_componentsDeatilsForAllOrders="{CALL IOE.SP_GET_COMPONENTS_DETAILS_FOR_PRODUCT(?,?)}";
	public static String sqlInsert_ServiceAndComponentsInfoFx = "{CALL IOE.SP_INSERT_UPDATE_SERVICE_N_COMPONENTS_FX(?,?,?,?,?)}";
	private static final String sqlGet_ChargeSummaryChargesList = "{CALL IOE.SP_GET_CHARGE_DETAILS_FOR_ALL_ORDERS_CHARGE_SUMMARY(?,?)}";
	private String sp_update_flag_for_Billing_Trigger="{CALL IOE.SP_UPDATE_FLAG_FOR_BILLING_TRIGGER(?)}";
    public static String sqlGetLineDetailsforBulkUpload="{CALL IOE.SP_GET_LINE_DETAILS_FOR_BT_BULKUPLOAD(?)}";
    public static String sqlGetChargeDetailsforBulkUpload="{CALL IOE.SP_GET_CHARGE_DETAILS_FOR_ALL_ORDERS_BULKUPLOAD(?)}";
    private static final String sqlGet_ChargeSummaryComponentsList = "{CALL IOE.SP_GET_COMPONENTS_DETAILS_FOR_PRODUCT(?,?)}";

	//Start By Saurabh Below Codes for processing lines with Component(Arbor) in FX and other related work
	public static String sqlUpdateArborDataScheduler = "{CALL IOE.SP_UPDATE_DATA_ON_FX_ACCOUNT_CREATION()}";
	public static String sqlProcessArborDataScheduler = "{CALL IOE.SP_LOC_TRACKING_PARTIAL_BILLING_TRIGGER(?)}";
	public static String sqlGetUnProcessedSpids = " SELECT SERVICEPRODUCTID FROM IOE.TPOSERVICEDETAILS_CONFIGATT_VALUES "+
				"WHERE ((ATTRIBUTE_ID='LOC_TRACKING_FX_SI' AND ATTRIBUTE_VALUE='LOC_TRACKING_FX_SI' ) OR (ATTRIBUTE_ID='NON_DEFAULT_FX_SI_ID' and ATTRIBUTE_VALUE='FX_SUBSCR_NO')) AND MILESTONE_STATUS IN (1,4)";
	//End By Saurabh
	public static String sqlinsertFullPublish = "{call IOE.SP_UPDATE_FULLPUBLISH(?,?,?,?,?,?)}";
	public static String sqlinsertViewPartialPublish = "{call IOE.SP_UPDATE_PARTIALPUBLISH(?,?,?,?,?,?,?)}";
	
	//Start[082]
		private static String sqlValidateNOFXSIComponent="{call IOE.SP_NOFXSI_COMPONENT_VALIDATION(?)}";
	//End[082]
		private static String sqlGet_ValidateInitiateDate="{call IOE.SP_VALIDATE_INITIATE_DATE(?,?)}";
		
		//private static String sqlGet_ValidatebILLINGtRIGGERDate="{call IOE.GET_DISCONNECTION_DATA_BILLING_TRIGGER_BULKUPLOAD(?)}";
		private static String sqlGet_ValidatebILLINGtRIGGERDate_BULKUPLOAD="{call BULKUPLOAD.GET_DISCONNECTION_DATA_BILLING_TRIGGER_BULKUPLOAD(?)}";
		private static String sqlLogException="{call IOE.SP_LOG_EXCEPTION_BT(?,?)}";
		
		
	static {
		logger = Logger.getLogger(ViewOrderDao.class);
	}
	
	public ViewOrderDto validateProductBillingTriggerDateER(Connection conn,String csvServiceProductIds,String csvBillingTriggerDate,String csvLineType) throws Exception
	{
		//	Nagarjuna
		String methodName="validateProductBillingTriggerDateER", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;
		//end Nagarjuna
		ViewOrderDto  objDto = null;
		String activeDateStatus = null;
		CallableStatement csValidateBillingDate =null;
		ArrayList arrViewOrderDto = new  ArrayList();
		ViewOrderDto objViewOrderDto = null;
		conn = DbConnection.getConnectionObject();
		
		csValidateBillingDate = conn.prepareCall(sqlGet_ValidateBillingTriggerDate);
		csValidateBillingDate.setString(1,csvServiceProductIds);
		csValidateBillingDate.setString(2,csvBillingTriggerDate);
		csValidateBillingDate.setString(3,csvLineType);
		csValidateBillingDate.registerOutParameter(4,java.sql.Types.VARCHAR); // out param
		csValidateBillingDate.registerOutParameter(5,java.sql.Types.VARCHAR); // out param
		
		csValidateBillingDate.executeQuery();
		
		String[] activeDate = csValidateBillingDate.getString(4).split(",");
		String[] status     = csValidateBillingDate.getString(5).split(",");    
		String[] serviceProductIds = csvServiceProductIds.split(",");
		
		for(int i=0;i<activeDate.length;i++) {
			
			objViewOrderDto = new  ViewOrderDto();
			
			objViewOrderDto.setDateValidationStatus(status[i]);
			objViewOrderDto.setAccountActiveDate(activeDate[i]);
			objViewOrderDto.setServiceProductID(serviceProductIds[i]);
			if(objViewOrderDto.getDateValidationStatus().equalsIgnoreCase("FAIL")) {
				
				activeDateStatus	=	"FAILURE";
				
			}
			
			arrViewOrderDto.add(objViewOrderDto);
			
		
		}
		
		objViewOrderDto.setIfAnyFailValidation(activeDateStatus);
		objViewOrderDto.setArrViewOrderDto(arrViewOrderDto);	
		try {
			DbConnection.freeConnection(conn);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,"Exception"+msg, logToFile, logToConsole);//added by nagarjuna
		}
		finally
		{
			DbConnection.closeCallableStatement(csValidateBillingDate);
		}
		return objViewOrderDto;
	}
	
	public ViewOrderDto validateProductBillingTriggerDate(Connection optionalConnection,String csvServiceProductIds,HashMap<String, String> hmap_SPID_BTDate, long orderNo,String clepFlag,String csvSPOrderIDS,int flag) throws Exception
	{
		//	Nagarjuna
		String methodName="validateProductBillingTriggerDate", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;
		//end Nagarjuna
		ViewOrderDto  objDto = null;
		String activeDateStatus = "SUCCESS";
		CallableStatement csValidateBillingDate =null;
		ArrayList<ViewOrderDto> arrViewOrderDto = new  ArrayList<ViewOrderDto>();
		ViewOrderDto objViewOrderDto = null;
		ViewOrderDto objRetViewOrderDto = null;
		HashMap<String,ViewOrderDto> mapvalidate_dto=new HashMap<String,ViewOrderDto>();
		Connection conn=null;
		String str_Error=null;
	
		if(optionalConnection==null)
		{
			conn = DbConnection.getConnectionObject();
		}
		else
			conn=optionalConnection;
		
		csValidateBillingDate = conn.prepareCall(sqlGet_ValidateBillingTriggerDate);
		//only thise line items which are seelcyed in BT sctreen for billing trigger
		csValidateBillingDate.setString(1,csvServiceProductIds);
		csValidateBillingDate.setLong(2,orderNo);
		csValidateBillingDate.setString(3,csvSPOrderIDS);
		csValidateBillingDate.setInt(4,flag);
		ResultSet rs=csValidateBillingDate.executeQuery();
		
		
		ArrayList<String> errorMsgs=new ArrayList<String>();
		//[005] Start
		ArrayList<String> errorMsgsForCurrencyChange=new ArrayList<String>();
		//[005] End
		int validationFailure=0;
		
		while(rs.next())
		{
			objViewOrderDto = new  ViewOrderDto();
			str_Error=null;
			String lineId=rs.getString("SERVICEPRODUCTID");
			Timestamp AccActiveDate=rs.getTimestamp("ACC_ACTIVE_DATE");
			String inputBTDate=hmap_SPID_BTDate.get(lineId);
			String line_oldOrNew=rs.getString("LINE_OLD_OR_NEW");
			String isLineDisconnected=rs.getString("ISLINE_DISCONNECTED");
			String Servicetype=rs.getString("SERVICETYPE");
			int NO_New_Charges=rs.getInt("NO_New_Charges");
			
						//----------[ Start: To Find Commulative SPID,LSI and ORDERNO ]------------------
			int billing_scenario=rs.getInt("BILLING_SCENARIO");
			String target_BT_done_flag=rs.getString("TARGET_BT_DONE_FLAG");
			String targetCommulativeLineId=rs.getString("FX_REDIRECTION_SPID");
			String targetCommulativeLSI=rs.getString("FX_REDIRECTION_LSI");
			String targetommulativeOrderNo=rs.getString("TARGET_COMMULATIVE_ORDERNO");
			//----------[ End: To Find Commulative SPID,LSI and ORDERNO ]--------------------

			//below line add by anil , for clep validation, when AccActiveDate is null for clep then set AccActiveDate
			
			SimpleDateFormat sdfActiveDate = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a"); 
			java.util.Date date = sdfActiveDate.parse("1/1/1999 12:00:00 PM"); 
			java.sql.Timestamp timest = new java.sql.Timestamp(date.getTime()); 
			
				if("Y".equalsIgnoreCase(clepFlag) && AccActiveDate==null){
					AccActiveDate=timest;
				}
			//above line add by anil , for clep validation, when AccActiveDate is null for clep then set AccActiveDate
				
			
			SimpleDateFormat sdf= new SimpleDateFormat(AppConstants.CALENDER_FORMAT_MMDDYY);
			int lineVaidationFailure=0;
			
			if("OLD".equals(line_oldOrNew))
			{ 
				
				  Date siActiveDate=rs.getDate("SI_ACTIVE_DATE");
				//if disconnected line, bt date>...(is  si in fx),..si active date,all fx charges'active date,failed charges-ask for continue/or not...,all future charges should be made inactive-Msg
				//if non disconnected line bt date>account active date
				if("1".equals(isLineDisconnected))
				{//if disconnected line
					String isFX_SI_Equivalent=rs.getString("IS_FX_SI_EQUIVALENT");
					String isInFxStatus=rs.getString("IS_SI_IN_FX_STATUS");
					Date maxChargesInFxActiveDate=rs.getDate("MAX_CHARGES_IN_FX_ACTIVE_DATE");//this also includes charges failed in FX
					Date alreadyDisconnectedChargesMaxActiveDate=rs.getDate("DISCONNECTED_CHARGES_IN_FX_MAX_ACTIVE_DATE");
					String noFailedCharges=rs.getString("NO_FAILED_CHARGES");
					String noFutureCharges=rs.getString("NO_FUTURE_CHARGES");
					Date maxComponentsInFxActiveDate=rs.getDate("MAX_COMPONENTS_IN_FX_ACTIVE_DATE");//this also includes charges failed in FX
					Date alreadyDisconnectedComponentsMaxActiveDate=rs.getDate("DISCONNECTED_COMPONENTS_IN_FX_MAX_ACTIVE_DATE");
					
					if("1".equals(isFX_SI_Equivalent))
					{
						/*if(!"3".equals(isInFxStatus))
						{
							//fail
							errorMsgs.add("Billing Trigger can not be done"
									+" for Line No :"
									+lineId+" beacuse SI is not created in FX. ");
							//[005] Strar
							errorMsgsForCurrencyChange.add("During Disconnect Order Generation- "+errorMsgs.get(errorMsgs.size()-1));
							//[005] END
							lineVaidationFailure=1;
						}else
						{
							//cfk btd is grater than si active date
							if(sdf.parse(inputBTDate).getTime()<siActiveDate.getTime())
							{
								//objViewOrderDto.setDateValidationStatus("FAIL");
								errorMsgs.add("Billing Trigger Date can not be less than SI Active Date -"
												+Utility.showDate_Report(siActiveDate)
												+" for Line No :"
												+lineId);
								//[005]Start
								errorMsgsForCurrencyChange.add("During Disconnect Order Generation- "+errorMsgs.get(errorMsgs.size()-1)+" - (Billing Trigger Date = Currency Change Date)");
								//[005] End
								lineVaidationFailure=1;
							}
						}*/
						
//						cfk btd is grater than si active date
						if(/*"3".equals(isInFxStatus) && */sdf.parse(inputBTDate).getTime()<siActiveDate.getTime())
						{
							//objViewOrderDto.setDateValidationStatus("FAIL");
							str_Error="Billing Trigger Date can not be less than SI Active Date -"
									+Utility.showDate_Report(siActiveDate);
							errorMsgs.add(str_Error
											+" for Line No :"
											+lineId);
						
							//[005]Start
							errorMsgsForCurrencyChange.add("During Disconnect Order Generation- "+errorMsgs.get(errorMsgs.size()-1)+" - (Billing Trigger Date = Currency Change Date)");
							//[005] End
							lineVaidationFailure=1;
						}
						
//						now on charges 
						//check BTD which is also disonnect date is greater than all old charges(<current date)  's active date
						if(maxChargesInFxActiveDate!=null && sdf.parse(inputBTDate).getTime()<maxChargesInFxActiveDate.getTime())
						{
							str_Error="Billing Trigger Date can not be less than All Current Charges's Active Date -"
								+Utility.showDate_Report(maxChargesInFxActiveDate);
								
								
							//objViewOrderDto.setDateValidationStatus("FAIL");
							errorMsgs.add(str_Error
											+" for Line No :"
											+lineId);
							
							
							//[005] Start
							errorMsgsForCurrencyChange.add("During Disconnect Order Generation- "+errorMsgs.get(errorMsgs.size()-1)+" - (Billing Trigger Date = Currency Change Date)");
							//[005] End
							lineVaidationFailure=1;
						}
						
						if(alreadyDisconnectedChargesMaxActiveDate!=null && sdf.parse(inputBTDate).getTime()<alreadyDisconnectedChargesMaxActiveDate.getTime())
						{
							//objViewOrderDto.setDateValidationStatus("FAIL");
							str_Error="Billing Trigger Date can not be less than current FX Charges's max Disconnect/Inactive Processed Date -"
							+Utility.showDate_Report(alreadyDisconnectedChargesMaxActiveDate);
							errorMsgs.add(str_Error
											+" for Line No :"
											+lineId);
							
							//[005] Start
							errorMsgsForCurrencyChange.add("During Disconnect Order Generation- "+errorMsgs.get(errorMsgs.size()-1)+" - (Billing Trigger Date = Currency Change Date)");
							//[005] End
							lineVaidationFailure=1;
						}
					
						if(maxComponentsInFxActiveDate!=null && sdf.parse(inputBTDate).getTime()<maxComponentsInFxActiveDate.getTime())
						{
							//objViewOrderDto.setDateValidationStatus("FAIL");
							str_Error="Billing Trigger Date can not be less than All Current Components's Active Date -"
							+Utility.showDate_Report(maxComponentsInFxActiveDate);
							errorMsgs.add(str_Error
											+" for Line No :"
											+lineId);
							//[005] Start
							errorMsgsForCurrencyChange.add("During Disconnect Order Generation- "+errorMsgs.get(errorMsgs.size()-1)+" - (Billing Trigger Date = Currency Change Date)");
							//[005] End
							lineVaidationFailure=1;
						}
						
						if(alreadyDisconnectedComponentsMaxActiveDate!=null && sdf.parse(inputBTDate).getTime()<alreadyDisconnectedComponentsMaxActiveDate.getTime())
						{
							//objViewOrderDto.setDateValidationStatus("FAIL");
							str_Error="Billing Trigger Date can not be less than current FX Components's max Disconnect/Inactive Processed Date -"
							+Utility.showDate_Report(alreadyDisconnectedComponentsMaxActiveDate);
							errorMsgs.add(str_Error
											+" for Line No :"
											+lineId);
							//[005] Start
							errorMsgsForCurrencyChange.add("During Disconnect Order Generation- "+errorMsgs.get(errorMsgs.size()-1)+" - (Billing Trigger Date = Currency Change Date)");
							//[005] End
							lineVaidationFailure=1;
						}
					}
				}
				else
				{//if non disconnected
					//int no_disconnect_date_pending=rs.getInt("NO_DISCONNECT_DATE_PENDING");
					//int no_disconnect_date_pending_for_components=rs.getInt("NO_DISCONNECT_DATE_PENDING_FOR_COMPONENTS");
					
				  if(sdf.parse(inputBTDate).getTime()<AccActiveDate.getTime())
					{
						//objViewOrderDto.setDateValidationStatus("FAIL");
					  str_Error="Billing Trigger Date can not be less than Account Active Date -"
							+Utility.showDate_Report(rs.getTimestamp("ACC_ACTIVE_DATE"));
						errorMsgs.add(str_Error
										+" for Line No :"
										+lineId);
						
						lineVaidationFailure=1;
					}
				  
				  if(/*NO_New_Charges>0 && */  "Service".equalsIgnoreCase(Servicetype))
				  {
					   
					  if(sdf.parse(inputBTDate).getTime()<siActiveDate.getTime())
						{
						  
						  str_Error="Billing Trigger Date can not be less than SI Active Date -"
								+Utility.showDate_Report(siActiveDate);
						  
							errorMsgs.add(str_Error
									+" for Line No :"
									+lineId);
					lineVaidationFailure=1;
						}
				  }
				  
				  
				
					
					/*if(no_disconnect_date_pending>0)
					{//if charges' disconnect date is not entered
						errorMsgs.add("Disconnect Dates are not entered for Charges of "
								+" Line No :"
								+lineId);
						lineVaidationFailure=1;
					}
					
					
					if(no_disconnect_date_pending_for_components>0)
					{//if components' disconnect date is not entered
						errorMsgs.add("Disconnect Dates are not entered for Components of "
								+" Line No :"
								+lineId);
						lineVaidationFailure=1;
					}*/
					
					
				  }			
				
				//objViewOrderDto.setDateValidationStatus("PASS");	
			}
			else if("NEW".equals(line_oldOrNew))
			{	//if new line , bt date >=account active date
				if(sdf.parse(inputBTDate).getTime()<AccActiveDate.getTime())
				{
					//objViewOrderDto.setDateValidationStatus("FAIL");
					str_Error="Billing Trigger Date can not be less than Account Active Date -"
						+Utility.showDate_Report(rs.getTimestamp("ACC_ACTIVE_DATE"));
					errorMsgs.add(str_Error
									+" for Line No :"
									+lineId);
					lineVaidationFailure=1;
				}
				Date siActiveDate=rs.getDate("SI_ACTIVE_DATE");
				
				if(siActiveDate!=null && sdf.parse(inputBTDate).getTime()<siActiveDate.getTime())
				{
					str_Error="Billing Trigger Date can not be less than FX SI's Active Date -"
						+Utility.showDate_Report(rs.getDate("SI_ACTIVE_DATE"));
					errorMsgs.add(str_Error
							+" for Line No :"
							+lineId);
			lineVaidationFailure=1;
				}
					
				//---------------[Start : If Source SPIDs belongs to Commulative SPIDs then validate its Target SPIDs are already completed BT or include in list od source SPIDs]
					if(billing_scenario==2){
						if(!"1".equals(target_BT_done_flag)){
							str_Error="Billing Trigger cannot be done for Line Item: "+lineId+" as Referneced Target Cumulative Line Item is not Billing Triggered.[ "
							+"Target Line Item: "+targetCommulativeLineId +", Target Commulative LSI: "+targetCommulativeLSI+" of Order No "+targetommulativeOrderNo+" ]";
							errorMsgs.add(str_Error);
							lineVaidationFailure=1;
						}
						
					}
				//---------------[End : If Source SPIDs belongs to Commulative SPIDs then validate its Target SPIDs are already completed BT or include in list od source SPIDs]
				/*else
				{
					objViewOrderDto.setDateValidationStatus("PASS");	
				}*/
			}
			
			if(lineVaidationFailure==1)
			{
				validationFailure=1;
			}
			
			//objViewOrderDto.setAccountActiveDate(Utility.showDate_Report(rs.getTimestamp("ACC_ACTIVE_DATE")));
			objViewOrderDto.setServiceProductID(rs.getString("SERVICEPRODUCTID"));
			if(str_Error!=null)
			{
				objViewOrderDto.setErrorlog(Utility.fnCheckNull(objViewOrderDto.getErrorlog())+str_Error);
			}
			/*if(objViewOrderDto.getDateValidationStatus().equalsIgnoreCase("FAIL")) {
				
				activeDateStatus	=	"FAILURE";
				
			}*/
			//arrViewOrderDto.add(objViewOrderDto);
			mapvalidate_dto.put(objViewOrderDto.getServiceProductID(), objViewOrderDto);
			
			
		
		}
		
		objRetViewOrderDto = new ViewOrderDto();
		if(validationFailure>0)
		{
			objRetViewOrderDto.setIfAnyFailValidation("FAILURE");
			objRetViewOrderDto.setBillingTriggerValidationErrors(errorMsgs);
			//[005] Start
			objRetViewOrderDto.setCurrencyChangeBillingTriggerValidationErrors(errorMsgsForCurrencyChange);
			//[005] End
		}
		else
		{
			objRetViewOrderDto.setIfAnyFailValidation("SUCCESS");
		}
		
		objRetViewOrderDto.setArrViewOrderDto(arrViewOrderDto);
		objRetViewOrderDto.setMap_validate(mapvalidate_dto);
		try
		{
		DbConnection.closeResultset(rs);
		DbConnection.closeCallableStatement(csValidateBillingDate);
			if(optionalConnection==null)
			{
				DbConnection.freeConnection(conn);
			}
		}
		catch(Exception ex)
		{
			//ex.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,"Exception"+msg, logToFile, logToConsole);//added by nagarjuna
		}
		
		
		return objRetViewOrderDto;
	}
	
	public ViewOrderDto validateProductBillingTriggerDate_bulkupload(Connection optionalConnection,String csvServiceProductIds,HashMap<String, String> hmap_SPID_BTDate, long orderNo,String spids,String clepFlag,int flag,long fileid) throws Exception
	{
		//	Nagarjuna
		String methodName="validateProductBillingTriggerDate_bulkupload", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;
		//end Nagarjuna
		ViewOrderDto  objDto = null;
		String activeDateStatus = "SUCCESS";
		CallableStatement csValidateBillingDate =null;
		ArrayList<ViewOrderDto> arrViewOrderDto = new  ArrayList<ViewOrderDto>();
		ViewOrderDto objViewOrderDto = null;
		ViewOrderDto objRetViewOrderDto = null;
		HashMap<String,ViewOrderDto> mapvalidate_dto=new HashMap<String,ViewOrderDto>();
		Connection conn=null;
		String str_Error=null;
	
		if(optionalConnection==null)
		{
			conn = DbConnection.getConnectionObject();
		}
		else
			conn=optionalConnection;
		try{
		csValidateBillingDate = conn.prepareCall(sqlGet_ValidateBillingTriggerDate_bulkupload);
		csValidateBillingDate.setLong(1,fileid);
		//only thise line items which are seelcyed in BT sctreen for billing trigger
		ResultSet rs=csValidateBillingDate.executeQuery();
		ArrayList<String> errorMsgs= null;//new ArrayList<String>();
		//[005] Start
		ArrayList<String> errorMsgsForCurrencyChange=new ArrayList<String>();
		//[005] End
		int validationFailure=0;
		
		while(rs.next())
		{
			errorMsgs=new ArrayList<String>();
			objViewOrderDto = new  ViewOrderDto();
			str_Error=null;
			String lineId=rs.getString("SERVICEPRODUCTID");
			Timestamp AccActiveDate=rs.getTimestamp("ACC_ACTIVE_DATE");
			String inputBTDate=hmap_SPID_BTDate.get(lineId);
			String line_oldOrNew=rs.getString("LINE_OLD_OR_NEW");
			String isLineDisconnected=rs.getString("ISLINE_DISCONNECTED");
			String Servicetype=rs.getString("SERVICETYPE");
			int NO_New_Charges=rs.getInt("NO_New_Charges");
			
						//----------[ Start: To Find Commulative SPID,LSI and ORDERNO ]------------------
			int billing_scenario=rs.getInt("BILLING_SCENARIO");
			String target_BT_done_flag=rs.getString("TARGET_BT_DONE_FLAG");
			String targetCommulativeLineId=rs.getString("FX_REDIRECTION_SPID");
			String targetCommulativeLSI=rs.getString("FX_REDIRECTION_LSI");
			String targetommulativeOrderNo=rs.getString("TARGET_COMMULATIVE_ORDERNO");
			//----------[ End: To Find Commulative SPID,LSI and ORDERNO ]--------------------

			//below line add by anil , for clep validation, when AccActiveDate is null for clep then set AccActiveDate
			
			SimpleDateFormat sdfActiveDate = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a"); 
			java.util.Date date = sdfActiveDate.parse("1/1/1999 12:00:00 PM"); 
			java.sql.Timestamp timest = new java.sql.Timestamp(date.getTime()); 
			
				if("Y".equalsIgnoreCase(clepFlag) && AccActiveDate==null){
					AccActiveDate=timest;
				}
			//above line add by anil , for clep validation, when AccActiveDate is null for clep then set AccActiveDate
				
			
			SimpleDateFormat sdf= new SimpleDateFormat(AppConstants.CALENDER_FORMAT_MMDDYY);
			int lineVaidationFailure=0;
			
			if("OLD".equals(line_oldOrNew))
			{ 
				
				  Date siActiveDate=rs.getDate("SI_ACTIVE_DATE");
				//if disconnected line, bt date>...(is  si in fx),..si active date,all fx charges'active date,failed charges-ask for continue/or not...,all future charges should be made inactive-Msg
				//if non disconnected line bt date>account active date
				if("1".equals(isLineDisconnected))
				{//if disconnected line
					String isFX_SI_Equivalent=rs.getString("IS_FX_SI_EQUIVALENT");
					String isInFxStatus=rs.getString("IS_SI_IN_FX_STATUS");
					Date maxChargesInFxActiveDate=rs.getDate("MAX_CHARGES_IN_FX_ACTIVE_DATE");//this also includes charges failed in FX
					Date alreadyDisconnectedChargesMaxActiveDate=rs.getDate("DISCONNECTED_CHARGES_IN_FX_MAX_ACTIVE_DATE");
					String noFailedCharges=rs.getString("NO_FAILED_CHARGES");
					String noFutureCharges=rs.getString("NO_FUTURE_CHARGES");
					Date maxComponentsInFxActiveDate=rs.getDate("MAX_COMPONENTS_IN_FX_ACTIVE_DATE");//this also includes charges failed in FX
					Date alreadyDisconnectedComponentsMaxActiveDate=rs.getDate("DISCONNECTED_COMPONENTS_IN_FX_MAX_ACTIVE_DATE");
					
					if("1".equals(isFX_SI_Equivalent))
					{
						/*if(!"3".equals(isInFxStatus))
						{
							//fail
							errorMsgs.add("Billing Trigger can not be done"
									+" for Line No :"
									+lineId+" beacuse SI is not created in FX. ");
							//[005] Strar
							errorMsgsForCurrencyChange.add("During Disconnect Order Generation- "+errorMsgs.get(errorMsgs.size()-1));
							//[005] END
							lineVaidationFailure=1;
						}else
						{
							//cfk btd is grater than si active date
							if(sdf.parse(inputBTDate).getTime()<siActiveDate.getTime())
							{
								//objViewOrderDto.setDateValidationStatus("FAIL");
								errorMsgs.add("Billing Trigger Date can not be less than SI Active Date -"
												+Utility.showDate_Report(siActiveDate)
												+" for Line No :"
												+lineId);
								//[005]Start
								errorMsgsForCurrencyChange.add("During Disconnect Order Generation- "+errorMsgs.get(errorMsgs.size()-1)+" - (Billing Trigger Date = Currency Change Date)");
								//[005] End
								lineVaidationFailure=1;
							}
						}*/
						
//						cfk btd is grater than si active date
						if(/*"3".equals(isInFxStatus) && */sdf.parse(inputBTDate).getTime()<siActiveDate.getTime())
						{
							//objViewOrderDto.setDateValidationStatus("FAIL");
							str_Error="Billing Trigger Date can not be less than SI Active Date -"
									+Utility.showDate_Report(siActiveDate);
							errorMsgs.add(str_Error
											+" for Line No :"
											+lineId);
						
							//[005]Start
							errorMsgsForCurrencyChange.add("During Disconnect Order Generation- "+errorMsgs.get(errorMsgs.size()-1)+" - (Billing Trigger Date = Currency Change Date)");
							//[005] End
							lineVaidationFailure=1;
						}
						
//						now on charges 
						//check BTD which is also disonnect date is greater than all old charges(<current date)  's active date
						if(maxChargesInFxActiveDate!=null && sdf.parse(inputBTDate).getTime()<maxChargesInFxActiveDate.getTime())
						{
							str_Error="Billing Trigger Date can not be less than All Current Charges's Active Date -"
								+Utility.showDate_Report(maxChargesInFxActiveDate);
								
								
							//objViewOrderDto.setDateValidationStatus("FAIL");
							errorMsgs.add(str_Error
											+" for Line No :"
											+lineId);
							
							
							//[005] Start
							errorMsgsForCurrencyChange.add("During Disconnect Order Generation- "+errorMsgs.get(errorMsgs.size()-1)+" - (Billing Trigger Date = Currency Change Date)");
							//[005] End
							lineVaidationFailure=1;
						}
						
						if(alreadyDisconnectedChargesMaxActiveDate!=null && sdf.parse(inputBTDate).getTime()<alreadyDisconnectedChargesMaxActiveDate.getTime())
						{
							//objViewOrderDto.setDateValidationStatus("FAIL");
							str_Error="Billing Trigger Date can not be less than current FX Charges's max Disconnect/Inactive Processed Date -"
							+Utility.showDate_Report(alreadyDisconnectedChargesMaxActiveDate);
							errorMsgs.add(str_Error
											+" for Line No :"
											+lineId);
							
							//[005] Start
							errorMsgsForCurrencyChange.add("During Disconnect Order Generation- "+errorMsgs.get(errorMsgs.size()-1)+" - (Billing Trigger Date = Currency Change Date)");
							//[005] End
							lineVaidationFailure=1;
						}
					
						if(maxComponentsInFxActiveDate!=null && sdf.parse(inputBTDate).getTime()<maxComponentsInFxActiveDate.getTime())
						{
							str_Error="Billing Trigger Date can not be less than All Current Components's Active Date -"
								+Utility.showDate_Report(maxComponentsInFxActiveDate);
							//objViewOrderDto.setDateValidationStatus("FAIL");
							errorMsgs.add(str_Error
											+" for Line No :"
											+lineId);
							//[005] Start
							errorMsgsForCurrencyChange.add("During Disconnect Order Generation- "+errorMsgs.get(errorMsgs.size()-1)+" - (Billing Trigger Date = Currency Change Date)");
							//[005] End
							lineVaidationFailure=1;
						}
						
						if(alreadyDisconnectedComponentsMaxActiveDate!=null && sdf.parse(inputBTDate).getTime()<alreadyDisconnectedComponentsMaxActiveDate.getTime())
						{
							str_Error="Billing Trigger Date can not be less than current FX Components's max Disconnect/Inactive Processed Date -"
								+Utility.showDate_Report(alreadyDisconnectedComponentsMaxActiveDate);
							//objViewOrderDto.setDateValidationStatus("FAIL");
							errorMsgs.add(str_Error
											+" for Line No :"
											+lineId);
							//[005] Start
							errorMsgsForCurrencyChange.add("During Disconnect Order Generation- "+errorMsgs.get(errorMsgs.size()-1)+" - (Billing Trigger Date = Currency Change Date)");
							//[005] End
							lineVaidationFailure=1;
						}
					}
				}
				else
				{//if non disconnected
					//int no_disconnect_date_pending=rs.getInt("NO_DISCONNECT_DATE_PENDING");
					//int no_disconnect_date_pending_for_components=rs.getInt("NO_DISCONNECT_DATE_PENDING_FOR_COMPONENTS");
					
				  if(sdf.parse(inputBTDate).getTime()<AccActiveDate.getTime())
					{
						//objViewOrderDto.setDateValidationStatus("FAIL");
					  str_Error="Billing Trigger Date can not be less than Account Active Date -"
							+Utility.showDate_Report(rs.getTimestamp("ACC_ACTIVE_DATE"));
						errorMsgs.add(str_Error
										+" for Line No :"
										+lineId);
						
						lineVaidationFailure=1;
					}
				  
				  if("Service".equalsIgnoreCase(Servicetype))
				  {
					   
					  if(sdf.parse(inputBTDate).getTime()<siActiveDate.getTime())
						{
						  
						  str_Error="Billing Trigger Date can not be less than SI Active Date -"
								+Utility.showDate_Report(siActiveDate);
						  
							errorMsgs.add(str_Error
									+" for Line No :"
									+lineId);
					lineVaidationFailure=1;
						}
				  }
				  
				  
				
					
					/*if(no_disconnect_date_pending>0)
					{//if charges' disconnect date is not entered
						errorMsgs.add("Disconnect Dates are not entered for Charges of "
								+" Line No :"
								+lineId);
						lineVaidationFailure=1;
					}
					
					
					if(no_disconnect_date_pending_for_components>0)
					{//if components' disconnect date is not entered
						errorMsgs.add("Disconnect Dates are not entered for Components of "
								+" Line No :"
								+lineId);
						lineVaidationFailure=1;
					}*/
					
					
				  }			
				
				//objViewOrderDto.setDateValidationStatus("PASS");	
			}
			else if("NEW".equals(line_oldOrNew))
			{	//if new line , bt date >=account active date
				if(sdf.parse(inputBTDate).getTime()<AccActiveDate.getTime())
				{
					//objViewOrderDto.setDateValidationStatus("FAIL");
					str_Error="Billing Trigger Date can not be less than Account Active Date -"
						+Utility.showDate_Report(rs.getTimestamp("ACC_ACTIVE_DATE"));
					errorMsgs.add(str_Error
									+" for Line No :"
									+lineId);
					lineVaidationFailure=1;
				}
				Date siActiveDate=rs.getDate("SI_ACTIVE_DATE");
				
				if(siActiveDate!=null && sdf.parse(inputBTDate).getTime()<siActiveDate.getTime())
				{
					str_Error="Billing Trigger Date can not be less than FX SI's Active Date -"
						+Utility.showDate_Report(rs.getDate("SI_ACTIVE_DATE"));
					errorMsgs.add(str_Error
							+" for Line No :"
							+lineId);
			lineVaidationFailure=1;
				}
					
				//---------------[Start : If Source SPIDs belongs to Commulative SPIDs then validate its Target SPIDs are already completed BT or include in list od source SPIDs]
					if(billing_scenario==2){
						if(!"1".equals(target_BT_done_flag)){
							str_Error="Billing Trigger cannot be done for Line Item: "+lineId+" as Referneced Target Cumulative Line Item is not Billing Triggered.[ "
							                                                                                          									+"Target Line Item: "+targetCommulativeLineId +", Target Commulative LSI: "+targetCommulativeLSI+" of Order No "+targetommulativeOrderNo+" ]";
							errorMsgs.add(str_Error);
							lineVaidationFailure=1;
						}
						
					}
				//---------------[End : If Source SPIDs belongs to Commulative SPIDs then validate its Target SPIDs are already completed BT or include in list od source SPIDs]
				/*else
				{
					objViewOrderDto.setDateValidationStatus("PASS");	
				}*/
			}
			
			if(lineVaidationFailure==1)
			{
				validationFailure=1;
			}
			
			//objViewOrderDto.setAccountActiveDate(Utility.showDate_Report(rs.getTimestamp("ACC_ACTIVE_DATE")));
			objViewOrderDto.setServiceProductID(rs.getString("SERVICEPRODUCTID"));
			if(str_Error!=null)
			{
				objViewOrderDto.setBillingTriggerValidationErrors(errorMsgs);
			}
			/*if(objViewOrderDto.getDateValidationStatus().equalsIgnoreCase("FAIL")) {
				
				activeDateStatus	=	"FAILURE";
				
			}*/
			//arrViewOrderDto.add(objViewOrderDto);
			mapvalidate_dto.put(objViewOrderDto.getServiceProductID(), objViewOrderDto);
			
			
		
		}
		
		objRetViewOrderDto = new ViewOrderDto();
		if(validationFailure>0)
		{
			objRetViewOrderDto.setIfAnyFailValidation("FAILURE");
			objRetViewOrderDto.setBillingTriggerValidationErrors(errorMsgs);
			//[005] Start
			objRetViewOrderDto.setCurrencyChangeBillingTriggerValidationErrors(errorMsgsForCurrencyChange);
			//[005] End
		}
		else
		{
			objRetViewOrderDto.setIfAnyFailValidation("SUCCESS");
		}
		
		objRetViewOrderDto.setArrViewOrderDto(arrViewOrderDto);
		objRetViewOrderDto.setMap_validate(mapvalidate_dto);
		try
		{
		DbConnection.closeResultset(rs);
		DbConnection.closeCallableStatement(csValidateBillingDate);
			if(optionalConnection==null)
			{
				DbConnection.freeConnection(conn);
			}
		}
		catch(Exception ex)
		{
			//ex.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,"Exception - BT_Bulk (Billing Trigger)"+msg, logToFile, logToConsole);//added by nagarjuna
			
		}
		}catch(Exception e)
		{
			Utility.LOG_ITER(true, true, e, "Billing Trigger Exception Occured at LineItem No : "+objViewOrderDto.getServiceProductID()+" in fileid : "+fileid);
			throw e;
		}
		return objRetViewOrderDto;
	}
	public ArrayList<ViewOrderDto> getTaskListOfOrder(long orderNo) throws Exception{
		//	Nagarjuna
		String methodName="getTaskListOfOrder", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;
		//end Nagarjuna
		ArrayList<ViewOrderDto> alTaskListOfOrder = null;
		Connection conn = null;
		ResultSet rsTaskListOfOrder = null;
		CallableStatement csTaskListOfOrder =null;
		ArrayList<ViewOrderDto> alTaskListOfOrder1 = new ArrayList<ViewOrderDto>();
		logger.info("View Order Interface and getTaskListOfOrder method of ViewOrderDao class have been called");
		
		try {
			alTaskListOfOrder = new ArrayList<ViewOrderDto>();
			String taskId="";
			String isFirstTask="";
			conn = DbConnection.getConnectionObject();
			csTaskListOfOrder = conn.prepareCall(sqlGet_TaskListOfOrder);
			csTaskListOfOrder.setLong(1,orderNo);
			rsTaskListOfOrder=csTaskListOfOrder.executeQuery();
			ViewOrderDto objViewOrderDto= new ViewOrderDto();
			
			while (rsTaskListOfOrder.next()) {
				objViewOrderDto= new ViewOrderDto();
				objViewOrderDto.setTaskID(Utility.fnCheckNull(rsTaskListOfOrder
						.getInt("TASKID"))); 
				objViewOrderDto.setTaskType(Utility.fnCheckNull(rsTaskListOfOrder
						.getString("TASK_TYPE_DESC")));				
				objViewOrderDto.setIsLastTask(Utility.fnCheckNull(rsTaskListOfOrder
						.getString("IS_LAST_TASK")));
				objViewOrderDto.setIsFirstTask(Utility.fnCheckNull(rsTaskListOfOrder
						.getString("IS_FIRST_TASK")));
				objViewOrderDto.setOwnerType(Utility.fnCheckNull(rsTaskListOfOrder
						.getString("ROLENAME")));
				objViewOrderDto.setTaskStatus(Utility.fnCheckNull(rsTaskListOfOrder
						.getString("TASKSTATUS_DESC")));
				objViewOrderDto.setTaskStartDate(Utility.fnCheckNull(rsTaskListOfOrder
						.getString("TASKSTARTDATE")));
				objViewOrderDto.setTaskEndDate(Utility.fnCheckNull(rsTaskListOfOrder
						.getString("TASKENDDATE")));
					/*[007] start*/
				objViewOrderDto.setPreviousTaskID(Utility.fnCheckNull(rsTaskListOfOrder
						.getString("PREV_TASK_ID")));
				/*[007] end */
				objViewOrderDto.setOwnerTypeId(rsTaskListOfOrder.getString("OWNERTYPE_ID"));
				objViewOrderDto.setLastTask(rsTaskListOfOrder.getInt("IS_LAST_TASK"));
				objViewOrderDto.setChangeorderstatus(rsTaskListOfOrder.getString("CHANGE_ORDER_TASK_STATUS"));
				
				alTaskListOfOrder.add(objViewOrderDto);
			}
			for(ViewOrderDto list : alTaskListOfOrder){
				if(list.getIsFirstTask().equals(Integer.toString(1))){
					taskId = list.getTaskID();
					alTaskListOfOrder1.add(list);
					break;
				}
			}
			if(!taskId.equals("")){
				while(alTaskListOfOrder.size()!= alTaskListOfOrder1.size()){
					ViewOrderDto next = getNextTask(alTaskListOfOrder,taskId);
					if(null != next){
						taskId = next.getTaskID();
						alTaskListOfOrder1.add(next);
					}else{
						break;
					}
				}				
			}
		}catch (Exception e){
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,"Exception"+msg, logToFile, logToConsole);//added by nagarjuna
			//e.printStackTrace();
			throw new Exception("No Record Found");
		} finally {
			try {
				DbConnection.closeResultset(rsTaskListOfOrder);
				DbConnection .closeCallableStatement(csTaskListOfOrder);
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				//e.printStackTrace();
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,"Exception"+msg, logToFile, logToConsole);//added by nagarjuna
				throw new Exception("No Record Found");
			}
		}
		if(null != alTaskListOfOrder1 && alTaskListOfOrder1.size() == alTaskListOfOrder.size())
			return alTaskListOfOrder1;
		else
			return alTaskListOfOrder;
	}
	
	private ViewOrderDto getNextTask(ArrayList<ViewOrderDto> taskList, String taskId){		
		for(ViewOrderDto list : taskList){
			if(taskId.equals(list.getPreviousTaskID())){
				return list;
			}
		}
		return null;
	}
	/*public ArrayList getTaskListOfOrder(long orderNo) throws Exception{
		//	Nagarjuna
		String methodName="getTaskListOfOrder", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;
		//end Nagarjuna
		ArrayList<ViewOrderDto> alTaskListOfOrder = null;
		Connection conn = null;
		ResultSet rsTaskListOfOrder = null;
		CallableStatement csTaskListOfOrder =null;
		
		logger.info("View Order Interface and getTaskListOfOrder method of ViewOrderDao class have been called");
		
		try {
			alTaskListOfOrder = new ArrayList<ViewOrderDto>();
			
			conn = DbConnection.getConnectionObject();
			csTaskListOfOrder = conn.prepareCall(sqlGet_TaskListOfOrder);
			csTaskListOfOrder.setLong(1,orderNo);
			rsTaskListOfOrder=csTaskListOfOrder.executeQuery();
			
			while (rsTaskListOfOrder.next()) {
				ViewOrderDto objViewOrderDto= new ViewOrderDto();
				objViewOrderDto.setTaskID(Utility.fnCheckNull(rsTaskListOfOrder
						.getInt("TASKID"))); 
				objViewOrderDto.setTaskType(Utility.fnCheckNull(rsTaskListOfOrder
						.getString("TASK_TYPE_DESC")));
				objViewOrderDto.setOwnerType(Utility.fnCheckNull(rsTaskListOfOrder
						.getString("ROLENAME")));
				objViewOrderDto.setTaskStatus(Utility.fnCheckNull(rsTaskListOfOrder
						.getString("TASKSTATUS_DESC")));
				objViewOrderDto.setTaskStartDate(Utility.fnCheckNull(rsTaskListOfOrder
						.getString("TASKSTARTDATE")));
				objViewOrderDto.setTaskEndDate(Utility.fnCheckNull(rsTaskListOfOrder
						.getString("TASKENDDATE")));
				objViewOrderDto.setOwnerTypeId(rsTaskListOfOrder.getString("OWNERTYPE_ID"));
				objViewOrderDto.setChangeorderstatus(rsTaskListOfOrder.getString("CHANGE_ORDER_TASK_STATUS"));
				
				alTaskListOfOrder.add(objViewOrderDto);
			}
		
		} catch (Exception e) {
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,"Exception"+msg, logToFile, logToConsole);//added by nagarjuna
			//e.printStackTrace();
			throw new Exception("No Record Found");
		} finally {
			try {
				DbConnection.closeResultset(rsTaskListOfOrder);
				DbConnection .closeCallableStatement(csTaskListOfOrder);
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				//e.printStackTrace();
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,"Exception"+msg, logToFile, logToConsole);//added by nagarjuna
				throw new Exception("No Record Found");
			}
		}
		return alTaskListOfOrder;
	}
	*/
	public ArrayList getTaskListHistory_all(long orderNo,PagingDto objDto) throws Exception{
		//	Nagarjuna
		String methodName="getTaskListHistory_all", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;
		//end Nagarjuna
		ArrayList<ViewOrderDto> alTaskListOfOrder = null;
		Connection conn = null;
		ResultSet rsTaskListOfOrder = null;
		CallableStatement csTaskListOfOrder =null;
		int recordCount=0;
		
		logger.info("View Order Interface and getTaskListOfOrder method of ViewOrderDao class have been called");
		
		try {
			alTaskListOfOrder = new ArrayList<ViewOrderDto>();
			
			conn = DbConnection.getConnectionObject();
			csTaskListOfOrder = conn.prepareCall(sqlGet_TaskListHistoryOfOrder_all);
			csTaskListOfOrder.setLong(1,orderNo);
			csTaskListOfOrder.setString(2,objDto.getSortBycolumn());
			csTaskListOfOrder.setString(3,objDto.getSortByOrder());
			csTaskListOfOrder.setInt(4,objDto.getStartIndex());
			csTaskListOfOrder.setInt(5,objDto.getEndIndex());
			rsTaskListOfOrder=csTaskListOfOrder.executeQuery();
			
			while (rsTaskListOfOrder.next()) 
			{
				ViewOrderDto objViewOrderDto= new ViewOrderDto();
				objViewOrderDto.getPagingSorting().setPageRecords(objDto.getPageRecords());
				
				objViewOrderDto.setHistoryAction(Utility.fnCheckNull(rsTaskListOfOrder.getString("ActionTaken"))); 
				objViewOrderDto.setHistoryActionRoleName(Utility.fnCheckNull(rsTaskListOfOrder.getString("ACTION_ROLENAME")));
				objViewOrderDto.setHistoryRejectionSendTo(rsTaskListOfOrder.getString("REJECTION_SENDTO_ROLE_NAME"));
				objViewOrderDto.setHistoryActionDate(rsTaskListOfOrder.getString("ACTION_TAKEN_ON"));
				objViewOrderDto.setHistoryRemraks(rsTaskListOfOrder.getString("ACTION_REMARKS"));
				objViewOrderDto.setActionTakenById(Utility.fnCheckNull(rsTaskListOfOrder.getString("EMPID")));
				objViewOrderDto.setActionTakenByName(Utility.fnCheckNull(rsTaskListOfOrder.getString("EMPNAME")));
				objViewOrderDto.setTaskOwnerId(rsTaskListOfOrder.getInt("TASK_OWNER_ID"));
				objViewOrderDto.setServiceno_m6(rsTaskListOfOrder.getString("SERVICENO_M6NO"));
				objViewOrderDto.setTaskSendTo(rsTaskListOfOrder.getString("TASK_SEND_TO"));
				objViewOrderDto.setM6No(rsTaskListOfOrder.getString("M6NO"));
				//LAWKUSH START
				objViewOrderDto.setReasonName(rsTaskListOfOrder.getString("REJECTIONREASON"));
				//LAKWUSH END
			 objViewOrderDto.setDelayReasonValue(rsTaskListOfOrder.getString("DELAYREASON"));
				recordCount=rsTaskListOfOrder.getInt("FULL_REC_COUNT");
				objViewOrderDto.getPagingSorting().setRecordCount(recordCount);	
				objViewOrderDto.setMaxPageNo(objViewOrderDto.getPagingSorting().getMaxPageNumber());
				alTaskListOfOrder.add(objViewOrderDto);
			}
		
		} catch (Exception e) {
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,"Exception"+msg, logToFile, logToConsole);//added by nagarjuna
			//e.printStackTrace();
			throw new Exception("No Record Found");
		} finally {
			try {
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				//e.printStackTrace();
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,"Exception"+msg, logToFile, logToConsole);//added by nagarjuna
				throw new Exception("No Record Found");
			}
		}
		return alTaskListOfOrder;
	}
	
		public ArrayList getTaskListHistory(long orderNo) throws Exception{
			//	Nagarjuna
			String methodName="getTaskListHistory", className=this.getClass().getName(), msg="";
			boolean logToFile=true, logToConsole=true;
			//end Nagarjuna
			ArrayList<ViewOrderDto> alTaskListOfOrder = null;
			Connection conn = null;
			ResultSet rsTaskListOfOrder = null;
			CallableStatement csTaskListOfOrder =null;
			
			logger.info("View Order Interface and getTaskListOfOrder method of ViewOrderDao class have been called");
			
			try {
				alTaskListOfOrder = new ArrayList<ViewOrderDto>();
				
				conn = DbConnection.getConnectionObject();
				csTaskListOfOrder = conn.prepareCall(sqlGet_TaskListHistoryOfOrder);
				csTaskListOfOrder.setLong(1,orderNo);				
				rsTaskListOfOrder=csTaskListOfOrder.executeQuery();
				
				while (rsTaskListOfOrder.next()) 
				{
					ViewOrderDto objViewOrderDto= new ViewOrderDto();
					objViewOrderDto.setHistoryAction(rsTaskListOfOrder.getString("ActionTaken")); 
					objViewOrderDto.setHistoryActionRoleName(rsTaskListOfOrder.getString("ACTION_ROLENAME"));
					objViewOrderDto.setHistoryRejectionSendTo(rsTaskListOfOrder.getString("REJECTION_SENDTO_ROLE_NAME"));
					objViewOrderDto.setHistoryActionDate(rsTaskListOfOrder.getString("ACTION_TAKEN_ON"));
					objViewOrderDto.setHistoryRemraks(rsTaskListOfOrder.getString("ACTION_REMARKS"));
					objViewOrderDto.setActionTakenById(rsTaskListOfOrder.getString("EMPID"));
					objViewOrderDto.setActionTakenByName(rsTaskListOfOrder.getString("EMPNAME"));
					objViewOrderDto.setTaskOwnerId(rsTaskListOfOrder.getInt("TASK_OWNER_ID"));
					objViewOrderDto.setServiceno_m6(rsTaskListOfOrder.getString("SERVICENO_M6NO"));
					objViewOrderDto.setTaskSendTo(rsTaskListOfOrder.getString("TASK_SEND_TO"));	
					//for approval order we dissociate into service id and m6number
					//two extra column being fetched for xl Starts
					objViewOrderDto.setM6No(rsTaskListOfOrder.getString("M6ORDERNO"));
					objViewOrderDto.setServiceId(rsTaskListOfOrder.getString("SERVICEID"));
					//two extra column being fetched for xl Ends
					//for approval order we dissociate into service id and m6number
					alTaskListOfOrder.add(objViewOrderDto);
				}
			
			} catch (Exception e) {
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,"Exception"+msg, logToFile, logToConsole);//added by nagarjuna
				//e.printStackTrace();
				throw new Exception("No Record Found");
			} finally {
				try {
					DbConnection.closeResultset(rsTaskListOfOrder);
					DbConnection .closeCallableStatement(csTaskListOfOrder);
					DbConnection.freeConnection(conn);
				} catch (Exception e) {
					Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,"Exception"+msg, logToFile, logToConsole);//added by nagarjuna
					//e.printStackTrace();
					throw new Exception("No Record Found");
				}
			}
			return alTaskListOfOrder;
		}
		
		public ArrayList getTaskListHistory_selected(long orderNo,long m6No,PagingDto objDto) throws Exception{
			//	Nagarjuna
			String methodName="getTaskListHistory_selected", className=this.getClass().getName(), msg="";
			boolean logToFile=true, logToConsole=true;
			//end Nagarjuna
			ArrayList<ViewOrderDto> alTaskListOfOrder = null;
			Connection conn = null;
			ResultSet rsTaskListOfOrder = null;
			CallableStatement csTaskListOfOrder =null;
			int recordCount=0;
			
			logger.info("View Order Interface and getTaskListOfOrder method of ViewOrderDao class have been called");
			
			try {
				alTaskListOfOrder = new ArrayList<ViewOrderDto>();
				
				conn = DbConnection.getConnectionObject();
				csTaskListOfOrder = conn.prepareCall(sqlGet_TaskListHistoryOfOrder_selected);
				csTaskListOfOrder.setLong(1,orderNo);
				csTaskListOfOrder.setLong(2,m6No);
				csTaskListOfOrder.setString(3,objDto.getSortBycolumn());
				csTaskListOfOrder.setString(4,objDto.getSortByOrder());
				csTaskListOfOrder.setInt(5,objDto.getStartIndex());
				csTaskListOfOrder.setInt(6,objDto.getEndIndex());
				rsTaskListOfOrder=csTaskListOfOrder.executeQuery();
				
				while (rsTaskListOfOrder.next()) 
				{
					ViewOrderDto objViewOrderDto= new ViewOrderDto();
					objViewOrderDto.getPagingSorting().setPageRecords(objDto.getPageRecords());
					objViewOrderDto.setHistoryAction(Utility.fnCheckNull(rsTaskListOfOrder.getString("ActionTaken"))); 
					objViewOrderDto.setHistoryActionRoleName(Utility.fnCheckNull(rsTaskListOfOrder.getString("ACTION_ROLENAME")));
					objViewOrderDto.setHistoryRejectionSendTo(rsTaskListOfOrder.getString("REJECTION_SENDTO_ROLE_NAME"));
					objViewOrderDto.setHistoryActionDate(rsTaskListOfOrder.getString("ACTION_TAKEN_ON"));
					objViewOrderDto.setHistoryRemraks(rsTaskListOfOrder.getString("ACTION_REMARKS"));
					objViewOrderDto.setActionTakenById(Utility.fnCheckNull(rsTaskListOfOrder.getString("EMPID")));
					objViewOrderDto.setActionTakenByName(Utility.fnCheckNull(rsTaskListOfOrder.getString("EMPNAME")));
					objViewOrderDto.setTaskOwnerId(rsTaskListOfOrder.getInt("TASK_OWNER_ID"));
					objViewOrderDto.setServiceno_m6(rsTaskListOfOrder.getString("SERVICENO_M6NO"));
					objViewOrderDto.setTaskSendTo(rsTaskListOfOrder.getString("TASK_SEND_TO"));
//					LAWKUSH START
					objViewOrderDto.setReasonName(rsTaskListOfOrder.getString("REJECTIONREASON"));
					//LAKWUSH END
					recordCount=rsTaskListOfOrder.getInt("FULL_REC_COUNT");
					objViewOrderDto.getPagingSorting().setRecordCount(recordCount);	
					objViewOrderDto.setMaxPageNo(objViewOrderDto.getPagingSorting().getMaxPageNumber());
					objViewOrderDto.setDelayReasonValue(rsTaskListOfOrder.getString("DELAYREASON"));
					alTaskListOfOrder.add(objViewOrderDto);
				}
			
			} catch (Exception e) {
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,"Exception"+msg, logToFile, logToConsole);//added by nagarjuna
				//e.printStackTrace();
				throw new Exception("No Record Found");
			} finally {
				try {
					DbConnection.freeConnection(conn);
				} catch (Exception e) {
					Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,"Exception"+msg, logToFile, logToConsole);//added by nagarjuna
					//e.printStackTrace();
					throw new Exception("No Record Found");
				}
			}
			return alTaskListOfOrder;
		}
	
	public ArrayList getServiceList(long orderNo) throws Exception{
		//	Nagarjuna
		String methodName="getServiceList", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;
		//end Nagarjuna
		ArrayList<ViewOrderDto> al = null;
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs =null;		
		try {
			al = new ArrayList<ViewOrderDto>();
			
			conn = DbConnection.getConnectionObject();
			cs = conn.prepareCall(sqlGetServiceList);
			cs.setLong(1,orderNo);			
			rs=cs.executeQuery();
			
			while (rs.next()) 
			{
				ViewOrderDto objViewOrderDto= new ViewOrderDto();
				objViewOrderDto.setServiceID(rs.getInt("SERVICEID")); 
				objViewOrderDto.setM6No(rs.getString("M6ORDERNO"));							
				al.add(objViewOrderDto);
			}
		
		} catch (Exception e) {
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,"Exception"+msg, logToFile, logToConsole);//added by nagarjuna
			//e.printStackTrace();
			throw new Exception("No Record Found");
		} finally {
			try {
				DbConnection.closeResultset(rs);
				DbConnection .closeCallableStatement(cs);
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,"Exception"+msg, logToFile, logToConsole);//added by nagarjuna
				//e.printStackTrace();
				throw new Exception("No Record Found");
			}
		}
		return al;
	}
	
	public String approvalTabVisible(long orderNo) throws Exception{
		//	Nagarjuna
		String methodName="approvalTabVisible", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;
		//end Nagarjuna
		String approvalTabVisible = null;
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs =null;		
		try {			
			conn = DbConnection.getConnectionObject();
			cs = conn.prepareCall(sqlapprovalTabVisible);
			cs.setLong(1,orderNo);			
			rs=cs.executeQuery();
			
			while (rs.next()) 
			{				
				approvalTabVisible = rs.getString("STATUS");
			}
		
		} catch (Exception e) {
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,"Exception"+msg, logToFile, logToConsole);//added by nagarjuna
			//e.printStackTrace();
			throw new Exception("No Record Found");
		} finally {
			try {
				DbConnection.closeResultset(rs);
				DbConnection .closeCallableStatement(cs);
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,"Exception"+msg, logToFile, logToConsole);//added by nagarjuna
				//e.printStackTrace();
				throw new Exception("No Record Found");
			}
		}
		return approvalTabVisible;
	}
	public static String sqlsignageGlobalCount = "{CALL IOE.GETSIGNAGEGLOBALCOUNT(?,?)}";
	public long signageGlobalCount(long orderNo) throws Exception{
		//	Nagarjuna
		String methodName="signageGlobalCount", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;
		//end Nagarjuna
		long signageGlobalCount = 0;
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs =null;		
		try {			
			conn = DbConnection.getConnectionObject();
			cs = conn.prepareCall(sqlsignageGlobalCount);
			cs.setLong(1,orderNo);	
			cs.setLong(2,0);	
			cs.execute();				
			signageGlobalCount = cs.getLong(2);
			
		} catch (Exception e) {
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,"Exception"+msg, logToFile, logToConsole);//added by nagarjuna
			//e.printStackTrace();
			throw new Exception("No Record Found");
		} finally {
			try {
				DbConnection.closeResultset(rs);
				DbConnection .closeCallableStatement(cs);
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,"Exception"+msg, logToFile, logToConsole);//added by nagarjuna
				//e.printStackTrace();
				throw new Exception("No Record Found");
			}
		}
		return signageGlobalCount;
	}
	public static String sqllsiCheckForSignageValidation = "{CALL IOE.LSICHECKFORSIGNAGEATVALIDATION(?,?)}";
	public long lsiCheckForSignageValidation(long serviceIDChild) throws Exception{
		//	Nagarjuna
		String methodName="lsiCheckForSignageValidation", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;
		//end Nagarjuna
		long lsiCheckForSignageValidation = 0;
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs =null;		
		try {			
			conn = DbConnection.getConnectionObject();
			cs = conn.prepareCall(sqllsiCheckForSignageValidation);
			cs.setLong(1,serviceIDChild);	
			cs.setLong(2,0);	
			cs.execute();				
			lsiCheckForSignageValidation = cs.getLong(2);
			
		} catch (Exception e) {
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,"Exception"+msg, logToFile, logToConsole);//added by nagarjuna
			//e.printStackTrace();
			throw new Exception("No Record Found");
		} finally {
			try {
				DbConnection.closeResultset(rs);
				DbConnection .closeCallableStatement(cs);
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,"Exception"+msg, logToFile, logToConsole);//added by nagarjuna
				//e.printStackTrace();
				throw new Exception("No Record Found");
			}
		}
		return lsiCheckForSignageValidation;
	}
/*	public ArrayList getChargeSummaryList(long orderNo) throws Exception{
		ArrayList<ViewOrderDto> alChargeSummaryList = null;
		Connection conn = null;
		ResultSet rsChargeSummaryList = null;
		CallableStatement csChargeSummaryList=null;
		
		logger.info("View Order Interface and getChargeSummaryList method of ViewOrderDao class have been called");
		
		try {
			alChargeSummaryList = new ArrayList<ViewOrderDto>();
			
			conn = DbConnection.getConnectionObject();
			csChargeSummaryList = conn.prepareCall(sqlGet_ChargeSummaryList);
			csChargeSummaryList.setLong(1,orderNo);
			rsChargeSummaryList=csChargeSummaryList.executeQuery();
			
			while (rsChargeSummaryList.next()) 
			{
				
				ViewOrderDto objViewOrderDto= new ViewOrderDto();
				objViewOrderDto.setChargeSummaryOrderNo(rsChargeSummaryList.getString("ORDERNO")); 
				objViewOrderDto.setChargeSummaryTotalAmount(rsChargeSummaryList.getString("CHARGEAMOUNT"));
				objViewOrderDto.setChargeSummaryLogicalCircuitID(rsChargeSummaryList.getString("CUSTLOGICALSINO"));
				objViewOrderDto.setChargeSummaryServiceNo(rsChargeSummaryList.getString("SERVICESTAGE"));
				objViewOrderDto.setChargeSummaryStartDateLogic(rsChargeSummaryList.getString("STARTDATELOGIC"));
				objViewOrderDto.setChargeSummaryEndDateLogic(rsChargeSummaryList.getString("ENDDATELOGIC"));
				objViewOrderDto.setChargeSummaryPartyName(rsChargeSummaryList.getString("ACCOUNTNAME"));
				alChargeSummaryList.add(objViewOrderDto);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("No Record Found");
		} finally {
			try {
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("No Record Found");
			}
		}
		return alChargeSummaryList;
	}
	*/
	
	/**
	 * returns publishResult=0 :for success of data in M6 and FX(where ever applicable) intermediate tables 
	 */
	public int getPublishResult(long orderNo,String publishPage, OrderHeaderDTO orderTypeDto,Connection optionalConnection,String serviceList,String publishList,String roleid,String owner)throws Exception
	{
		//	Nagarjuna
		String methodName="getPublishResult", className=this.getClass().getName(), msg1="";
		boolean logToFile=true, logToConsole=true;
		//end Nagarjuna
		logger.info("View Order Interface and getTaskListOfOrder method of ViewOrderModel class have been called");
		int publishResult=0;
		NewOrderDao objdao=new NewOrderDao();
		int errorCode=0;
		int status=0;
		String msgCode=null;
		String msg=null;
		int result=0;
		int sqlCode=0;
		Connection conn = null;
		CallableStatement csPublishOrder=null;
		CallableStatement cs=null;// Added by Saurabh
        CallableStatement cs_last_publish_index=null;// Added by Manisha
		try 
		{
			//below code added by Anil for CLEP
			if(optionalConnection==null){
				conn = DbConnection.getConnectionObject();
			}else{
				conn=optionalConnection;
			}
			//end CLEP
			 conn.setAutoCommit(false);
			 
			 if("".equalsIgnoreCase(serviceList) || "CHANGE".equalsIgnoreCase(publishPage)) {//If New Order Full Publish or Change Order Publish(here we dont send service list)
				 errorCode=updateFullPublish(orderTypeDto,orderNo,conn,cs);
			 }else{
				 if(!"CHANGE".equalsIgnoreCase(publishPage)) {
				 errorCode=updateViewIsPublish(orderTypeDto,serviceList,publishList,conn,cs);
			 	}
			 }
			 
			 if (errorCode!=0){
				 publishResult=1;
				 conn.rollback();
			 }else{
				 String orderType=orderTypeDto.getOrderInfo_OrderType();
                 cs_last_publish_index = conn.prepareCall(sql_update_last_publish_index);
                 cs_last_publish_index.setLong(1,orderNo);
                 cs_last_publish_index.execute();	
			 
				 if(orderTypeDto.ORDER_TYPE_CHANGE.equalsIgnoreCase(orderType)){
					 CallableStatement cstmt_update_line_status=conn.prepareCall(sp_update_line_status);
					 cstmt_update_line_status.setLong(1, orderNo);
					 cstmt_update_line_status.execute();
				 }
			 
				 CallableStatement cstmt_SP_UPDATE_FLAG_FOR_BILLING_TRIGGER=conn.prepareCall("call IOE.SP_UPDATE_FLAG_FOR_BILLING_TRIGGER(?)");
				 cstmt_SP_UPDATE_FLAG_FOR_BILLING_TRIGGER.setLong(1, orderNo);
				 cstmt_SP_UPDATE_FLAG_FOR_BILLING_TRIGGER.execute();	
				 
				 String toM6="YES";
			      // [014] 
				 	CallableStatement cs_Headend=conn.prepareCall("call ioe.SP_UPDATE_HEADEND_CODE(?,?,?,?,?)");
				 	cs_Headend.setLong(1, orderNo);
				 	cs_Headend.registerOutParameter(2, java.sql.Types.NUMERIC);
				 	cs_Headend.registerOutParameter(3, java.sql.Types.VARCHAR);
				 	cs_Headend.registerOutParameter(4, java.sql.Types.VARCHAR);
				 	cs_Headend.registerOutParameter(5, java.sql.Types.VARCHAR);
				 	cs_Headend.executeUpdate();
				 
				 	//[014] 	
			 
				 //Vijay. add condition for demo- regularize order
				 /*
				  * Now the condition would be true : if order type is change And (Change type ID is 5 )OR
				  * (change type ID is 4 with Sub change type ID =12) 
				  */
				 if (orderTypeDto.ORDER_TYPE_CHANGE.equalsIgnoreCase(orderType)
						&&
						( orderTypeDto.CHANGE_TYPE_RATERENEWAL == orderTypeDto.getOrderInfo_ChangeType()
								|| ( orderTypeDto.CHANGE_TYPE_DEMO == orderTypeDto.getOrderInfo_ChangeType()
								&& orderTypeDto.SUB_CHANGE_TYPE_REGULARIZE == orderTypeDto.getOrderInfo_ChangeSubType() )))
				 {
					 toM6="NO";
				 }
				 
				 if("YES".equals(toM6)){
					 csPublishOrder = conn.prepareCall(sqlInsert_PublishOrder);
					 csPublishOrder.setLong(1,orderNo);
					 csPublishOrder.setString(2,publishPage);
					 csPublishOrder.setLong(3,0);
					 csPublishOrder.setLong(4,0);
					 csPublishOrder.setString(5,"CheckStatus");
					 csPublishOrder.setLong(6,orderTypeDto.getPublished_by_empid());
					 csPublishOrder.setLong(7,orderTypeDto.getPublished_by_roleid());
					 csPublishOrder.execute();			
					 errorCode=csPublishOrder.getInt(4);
				 }else{
					 
					 //Started By Saurabh
					 //For Rate Renewal Only
					 //M6_END-FX_BT_START
					 cs = conn.prepareCall(sqlInsert_PublishOrder_RateRenewal);
					 cs.setLong(1, orderNo);
					 cs.setString(2, "");
					 cs.setInt(3, 0);
					 cs.setString(4, "");
					 cs.execute();
					 errorCode=cs.getInt(2);
					 // End By Saurabh	
				 }
				 if (errorCode!=0){
					 publishResult=1;
					 conn.rollback();
				 }else{
					 orderType=orderTypeDto.getOrderInfo_OrderType();
					 if(orderTypeDto.ORDER_TYPE_CHANGE.equalsIgnoreCase(orderType) && orderTypeDto.getOrderInfo_ChangeType()==orderTypeDto.CHANGE_TYPE_DISCONNECTION)
					 {
						 	 //no fx operation reqd
							 publishResult=0;
						 
					 }
					 else 
					 {
						 CallableStatement cstmt=conn.prepareCall(sp_insert_account_info_fx);
						 
						 cstmt.setLong(1, orderNo);
						 cstmt.setString(2, orderType);
						 cstmt.setInt(3, 0);
						 cstmt.registerOutParameter(4, java.sql.Types.INTEGER);
						 cstmt.registerOutParameter(5, java.sql.Types.VARCHAR);
						 cstmt.registerOutParameter(6, java.sql.Types.VARCHAR);
						 cstmt.registerOutParameter(7, java.sql.Types.VARCHAR);
						 cstmt.execute();
						 Utility.throwExceptionIfFound(cstmt,4,5,6,7);
						 publishResult=0;
					 }
	                 CallableStatement cstmt_insert=conn.prepareCall(sp_insert_loc_data);
			 
					 cstmt_insert.setLong(1, orderNo);
					 cstmt_insert.execute();
				 
					/* CallableStatement cstmt_insert_locdate=conn.prepareCall(sp_custom_activity_for_publish);
					 
					 cstmt_insert_locdate.setLong(1, orderNo);
			         cstmt_insert_locdate.registerOutParameter(2, java.sql.Types.INTEGER);
					 cstmt_insert_locdate.registerOutParameter(3, java.sql.Types.VARCHAR);
					 cstmt_insert_locdate.registerOutParameter(4, java.sql.Types.VARCHAR);
					 cstmt_insert_locdate.execute();
				     sqlCode=cstmt_insert_locdate.getInt(2);
					 msgCode= cstmt_insert_locdate.getString(3);
					 msg= cstmt_insert_locdate.getString(4);*/
					 
					 //[004] Start
					 System.err.println("sp_update_locdate_for_publish proc called==========>");
					 CallableStatement cstmt_update_locdate=conn.prepareCall(sp_update_locdate_for_publish);
					 cstmt_update_locdate.setLong(1, orderNo);
					 cstmt_update_locdate.execute();
					 System.err.println("sp_update_locdate_for_publish proc Executed Successfuly==========>");
	
					 //Commented By:Anil Kumar,for ACS Lineitem
					 /*CallableStatement cstmt_update_fx_si_id=conn.prepareCall(sp_update_fx_si_id);
					 cstmt_update_fx_si_id.setLong(1, orderNo);
					 cstmt_update_fx_si_id.execute();*/
				 
					 CallableStatement cstmt_update_gam_sync_status=conn.prepareCall(sp_update_gam_sync_status);
					 cstmt_update_gam_sync_status.setLong(1, orderNo);
					 cstmt_update_gam_sync_status.execute();
					 
				 	 if(owner!=null){
					 	if(!(roleid.equalsIgnoreCase(owner))){
					 		String stage=AppConstants.ORDER_STAGE_PARTIAL_INITIATED;
					 		result=objdao.updateOrderStagePI(orderNo,stage,conn);
					 	}	
					 	if(result!=0){
					 		publishResult=1;
							conn.rollback();
					 	}
					 	else
					 	{
					 		 conn.commit();
					 	}
			 	 	}else{
			 	 		conn.commit();
			 	 	}
				 }
			 }
			 
			 Utility.SysOut(" getTaskListOfOrder of Inside Model of View Order Interface");
		} catch (Exception ex) {
			logger.error(ex.getMessage()+ " Exception occured in viewUniversalWorkQueue method of "+ this.getClass().getSimpleName());
			//ex.printStackTrace();
			//Utility.LOG(true, true, ex, null);
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,"Exception"+msg, logToFile, logToConsole);//added by nagarjuna
			try {
				conn.rollback();
				publishResult=1;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,"Exception"+msg, logToFile, logToConsole);//added by nagarjuna
			}
			
			//throw ex;
		}  finally {
			try {
				DbConnection.closeCallableStatement(cs);
				DbConnection.closeCallableStatement(cs_last_publish_index);
				DbConnection.closeCallableStatement(csPublishOrder);
				if(optionalConnection==null){
					DbConnection.freeConnection(conn);
				}
				
			} catch (Exception e) {
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,"Exception"+msg, logToFile, logToConsole);//added by nagarjuna
				//e.printStackTrace();
				throw new Exception(e);
			}
		}
		return publishResult;
	}
	
	//start [001]
	public String fnIsOrderPublishedBillingTrigger(long OrderNo,long roleId)
	{
		//	Nagarjuna
		String methodName="fnIsOrderPublishedBillingTrigger", className=this.getClass().getName(), msg1="";
		boolean logToFile=true, logToConsole=true;
		//end Nagarjuna	
		logger.info("View Order Interface and fnIsOrderPublishedBillingTrigger method of ViewOrderModel class have been called");
		//edited by raghu for publish button enable disable functionality
		String isOrderBillingTrigger=null;
		String isOrderPublishStatus=null;
		String isOrderPublishedBillingTrigger=null;
		String isOrderWorkflowRelaunchStatus=null;
		Connection conn = null;
		CallableStatement csPublishOrder=null;
		try 
		{
			 conn = DbConnection.getConnectionObject();
			 csPublishOrder = conn.prepareCall(sqlGet_IsOrderPublishStatus);
			 csPublishOrder.setLong(1,OrderNo);
			 csPublishOrder.setLong(2,roleId);
			 csPublishOrder.setString(3,"0");
			 csPublishOrder.setString(4,"0");
			 //csPublishOrder.setString(5, "0");
			 csPublishOrder.execute();			
			 isOrderPublishStatus=csPublishOrder.getString(3);
			 isOrderBillingTrigger=csPublishOrder.getString(4);
			 //isOrderWorkflowRelaunchStatus=csPublishOrder.getString(5);
			 
			 //isOrderPublishedBillingTrigger=isOrderPublishStatus+"@@"+isOrderBillingTrigger+"@@"+isOrderWorkflowRelaunchStatus;
			 isOrderPublishedBillingTrigger=isOrderPublishStatus+"@@"+isOrderBillingTrigger;
			 
			 Utility.SysOut(" getTaskListOfOrder of Inside Model of View Order Interface");
		} catch (Exception ex) {
			logger.error(ex.getMessage()+ " Exception occured in viewUniversalWorkQueue method of "+ this.getClass().getSimpleName());
			//ex.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,"Exception"+msg1, logToFile, logToConsole);//added by nagarjuna
		} 
		finally
		{
			try {
				DbConnection.closeCallableStatement(csPublishOrder);
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,"Exception"+msg1, logToFile, logToConsole);//added by nagarjuna
				//e.printStackTrace();
			}
		}
		return isOrderPublishedBillingTrigger;
	}
//	end [001]
	
	/*public ArrayList<ViewOrderDto> getSELECT_BT_PRODUCTS_NEWORDER(ViewOrderDto objViewdto) throws Exception{
		ArrayList<ViewOrderDto> alSelectServiceDetails = null;
		Connection conn = null;
		ResultSet rsSelectServiceDetails = null;
		CallableStatement csSelectServiceDetails =null;
		ViewOrderDto objdto=new ViewOrderDto();
		logger.info("View Order Interface and getSelectServiceDetails method of ViewOrderDao class have been called");
		
		try {
			alSelectServiceDetails = new ArrayList<ViewOrderDto>();
			
			conn = DbConnection.getConnectionObject();
			csSelectServiceDetails= conn.prepareCall(sqlGet_SELECT_BT_PRODUCTS_NEWORDER);
			csSelectServiceDetails.setLong(1,objViewdto.getPonum());
		    rsSelectServiceDetails=csSelectServiceDetails.executeQuery();

				
		        while (rsSelectServiceDetails.next()) {
				ViewOrderDto objViewOrderDto= new ViewOrderDto();
				objViewOrderDto.setPmProvisioningDate(Utility.fnCheckNull(rsSelectServiceDetails.getString("PMPROVISIONINGDATE")));
				objViewOrderDto.setLocNo(Utility.fnCheckNull(rsSelectServiceDetails.getString("LOCNO")));
				objViewOrderDto.setLocDate(Utility.fnCheckNull(rsSelectServiceDetails.getString("LOCDATE")));
				objViewOrderDto.setBillingTriggerDate(Utility.fnCheckNull(rsSelectServiceDetails.getString("BILLINGTRIGGERDATE")));
				objViewOrderDto.setLineNumber(Utility.fnCheckNull(rsSelectServiceDetails.getString("LINENUMBER")));    
				objViewOrderDto.setLineName(Utility.fnCheckNull(rsSelectServiceDetails.getString("LINENAME")));
				objViewOrderDto.setLogicalSino(Utility.fnCheckNull(rsSelectServiceDetails.getString("LOGICALSINO")));
				objViewOrderDto.setCustLogicalSino(Utility.fnCheckNull(rsSelectServiceDetails.getString("CUSTLOGICALSINO")));
				objViewOrderDto.setFxSiid(Utility.fnCheckNull(rsSelectServiceDetails.getString("FXSIID")));
				objViewOrderDto.setSiid(Utility.fnCheckNull(rsSelectServiceDetails.getString("SIID")));
				objViewOrderDto.setFxId(Utility.fnCheckNull(rsSelectServiceDetails.getString("FXID")));
				objViewOrderDto.setFxAccNo(Utility.fnCheckNull(rsSelectServiceDetails.getString("FXACCNO")));     
				objViewOrderDto.setFxStatus(Utility.fnCheckNull(rsSelectServiceDetails.getString("FXSTATUS")));
				objViewOrderDto.setServiceId(Utility.fnCheckNull(rsSelectServiceDetails.getString("SERVICEID")));
				objViewOrderDto.setBillingOrderNo(Utility.fnCheckNull(rsSelectServiceDetails.getString("ORDERNO")));
				objViewOrderDto.setServiceDetailId(Utility.fnCheckNull(rsSelectServiceDetails.getString("SERVICEDETAILID")));
				objViewOrderDto.setBillingTriggerStatus(Utility.fnCheckNull(rsSelectServiceDetails.getString("BILLING_TRIGGER_STATUS")));
				objViewOrderDto.setFx_ACCOUNT_EXTERNAL_ID(Utility.fnCheckNull(rsSelectServiceDetails.getString("FX_ACCOUNT_EXTERNAL_ID")));
				objViewOrderDto.setNoOfCharges(Utility.fnCheckNull(rsSelectServiceDetails.getString("NO_OF_CHARGES")));
				//002 start
				objViewOrderDto.setFx_Token_no(Utility.fnCheckNull(rsSelectServiceDetails.getString("TOKEN_ID")));
				objViewOrderDto.setFx_Response(Utility.fnCheckNull(rsSelectServiceDetails.getString("FX_RESPONSE")));
				objViewOrderDto.setFx_status(Utility.fnCheckNull(rsSelectServiceDetails.getString("FX_STATUS")));
			    objViewOrderDto.setChallen_No(Utility.fnCheckNull(rsSelectServiceDetails.getString("CHALLEN_NO")));
				objViewOrderDto.setChallen_date(Utility.fnCheckNull(rsSelectServiceDetails.getString("CHALLEN_DATE")));
				//002 end
					objViewOrderDto.setNoOfComponents(Utility.fnCheckNull(rsSelectServiceDetails.getString("NO_OF_COMPONENT")));
					objViewOrderDto.setProductBillType(Utility.fnCheckNull(rsSelectServiceDetails.getString("PRODUCT_BILLING_TYPE")));
				if("0".equals(objViewOrderDto.getNoOfCharges()) && "0".equals(objViewOrderDto.getNoOfComponents()))
				{
					continue;//Not added sp which have no charge
				}
				
				alSelectServiceDetails.add(objViewOrderDto);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("No Record Found");
		} finally {
			try {
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("No Record Found");
			}
		}
		return alSelectServiceDetails;
	}*/
	
	 /**
	  * f0LLOWING FUNCTION ADD BY MANISHA FOR CHANGE ORDER BILLING TRRIGER
	    Change Order Type : connection
	  */
	/*public ArrayList<ViewOrderDto> getSELECT_BT_PRODUCTS_DISCONNECTION(long orderNo) throws Exception{
		ArrayList<ViewOrderDto> alSelectServiceDetails = null;
		Connection conn = null;
		ResultSet rsSelectServiceDetails = null;
		CallableStatement csSelectServiceDetails =null;
		
		logger.info("View Order Interface and getSelectServiceDetails method of ViewOrderDao class have been called");
		
		try {
			alSelectServiceDetails = new ArrayList<ViewOrderDto>();
			
			conn = DbConnection.getConnectionObject();
			csSelectServiceDetails= conn.prepareCall(sqlGet_SELECT_BT_PRODUCTS_DISCONNECTION);
			csSelectServiceDetails.setLong(1,orderNo);
			rsSelectServiceDetails=csSelectServiceDetails.executeQuery();
			
			while (rsSelectServiceDetails.next()) {
				ViewOrderDto objViewOrderDto= new ViewOrderDto();
				objViewOrderDto.setPmProvisioningDate(Utility.fnCheckNull(rsSelectServiceDetails.getString("PMPROVISIONINGDATE")));
				objViewOrderDto.setLocNo(Utility.fnCheckNull(rsSelectServiceDetails.getString("LOCNO")));
				objViewOrderDto.setLocDate(Utility.fnCheckNull(rsSelectServiceDetails.getString("LOCDATE")));
				objViewOrderDto.setBillingTriggerDate(Utility.fnCheckNull(rsSelectServiceDetails.getString("BILLINGTRIGGERDATE")));
				objViewOrderDto.setLineNumber(Utility.fnCheckNull(rsSelectServiceDetails.getString("LINENUMBER")));    
				objViewOrderDto.setLineName(Utility.fnCheckNull(rsSelectServiceDetails.getString("LINENAME")));
				objViewOrderDto.setLogicalSino(Utility.fnCheckNull(rsSelectServiceDetails.getString("LOGICALSINO")));
				objViewOrderDto.setCustLogicalSino(Utility.fnCheckNull(rsSelectServiceDetails.getString("CUSTLOGICALSINO")));
				objViewOrderDto.setFxSiid(Utility.fnCheckNull(rsSelectServiceDetails.getString("FXSIID")));
				objViewOrderDto.setSiid(Utility.fnCheckNull(rsSelectServiceDetails.getString("SIID")));
				objViewOrderDto.setFxId(Utility.fnCheckNull(rsSelectServiceDetails.getString("FXID")));
				objViewOrderDto.setFxAccNo(Utility.fnCheckNull(rsSelectServiceDetails.getString("FXACCNO")));     
				objViewOrderDto.setFxStatus(Utility.fnCheckNull(rsSelectServiceDetails.getString("FXSTATUS")));
				objViewOrderDto.setServiceId(Utility.fnCheckNull(rsSelectServiceDetails.getString("SERVICEID")));
				objViewOrderDto.setBillingOrderNo(Utility.fnCheckNull(rsSelectServiceDetails.getString("ORDERNO")));
				objViewOrderDto.setServiceDetailId(Utility.fnCheckNull(rsSelectServiceDetails.getString("SERVICEDETAILID")));
				objViewOrderDto.setBillingTriggerStatus(Utility.fnCheckNull(rsSelectServiceDetails.getString("BILLING_TRIGGER_STATUS")));
				objViewOrderDto.setFx_ACCOUNT_EXTERNAL_ID(Utility.fnCheckNull(rsSelectServiceDetails.getString("FX_ACCOUNT_EXTERNAL_ID")));
				
				objViewOrderDto.setFxAccNoStatus(Utility.fnCheckNull(rsSelectServiceDetails.getString("FXACCNO_STATUS")));
				objViewOrderDto.setBillingTriggerDisconnectStatus(Utility.fnCheckNull(rsSelectServiceDetails.getString("BILLING_TRIGGER_DISCONNECT_STATUS")));
				
				
				alSelectServiceDetails.add(objViewOrderDto);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("No Record Found");
		} finally {
			try {
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("No Record Found");
			}
		}
		return alSelectServiceDetails;
	}*/
	
	
	//FOLLOWING FUNCTION FOR BILLING TRIGGER RATERENEWAL
/*	public ArrayList<ViewOrderDto> getSELECT_BT_PRODUCTS_RATERENEWAL(long orderNo) throws Exception{
		ArrayList<ViewOrderDto> alSelectServiceDetails = null;
		Connection conn = null;
		ResultSet rsSelectServiceDetails = null;
		CallableStatement csSelectServiceDetails =null;
		
		logger.info("View Order Interface and getSelectServiceDetails method of ViewOrderDao class have been called");
		
		try {
			alSelectServiceDetails = new ArrayList<ViewOrderDto>();
			
			conn = DbConnection.getConnectionObject();
			csSelectServiceDetails= conn.prepareCall(sqlGet_Select_BT_PRODUCTS_RATERENEWAL);
			csSelectServiceDetails.setLong(1,orderNo);
			rsSelectServiceDetails=csSelectServiceDetails.executeQuery();
			
			while (rsSelectServiceDetails.next()) {
				ViewOrderDto objViewOrderDto= new ViewOrderDto();
				objViewOrderDto.setPmProvisioningDate(Utility.fnCheckNull(rsSelectServiceDetails.getString("PMPROVISIONINGDATE")));
				objViewOrderDto.setLocNo(Utility.fnCheckNull(rsSelectServiceDetails.getString("LOCNO")));
				objViewOrderDto.setLocDate(Utility.fnCheckNull(rsSelectServiceDetails.getString("LOCDATE")));
				objViewOrderDto.setBillingTriggerDate(Utility.fnCheckNull(rsSelectServiceDetails.getString("BILLINGTRIGGERDATE")));
				objViewOrderDto.setLineNumber(Utility.fnCheckNull(rsSelectServiceDetails.getString("LINENUMBER")));    
				objViewOrderDto.setLineName(Utility.fnCheckNull(rsSelectServiceDetails.getString("LINENAME")));
				objViewOrderDto.setLogicalSino(Utility.fnCheckNull(rsSelectServiceDetails.getString("LOGICALSINO")));
				objViewOrderDto.setCustLogicalSino(Utility.fnCheckNull(rsSelectServiceDetails.getString("CUSTLOGICALSINO")));
				objViewOrderDto.setFxSiid(Utility.fnCheckNull(rsSelectServiceDetails.getString("FXSIID")));
				objViewOrderDto.setSiid(Utility.fnCheckNull(rsSelectServiceDetails.getString("SIID")));
				objViewOrderDto.setFxId(Utility.fnCheckNull(rsSelectServiceDetails.getString("FXID")));
				objViewOrderDto.setFx_ACCOUNT_EXTERNAL_ID(Utility.fnCheckNull(rsSelectServiceDetails.getString("FX_ACCOUNT_EXTERNAL_ID")));     
				objViewOrderDto.setFxStatus(Utility.fnCheckNull(rsSelectServiceDetails.getString("FXSTATUS")));
				objViewOrderDto.setFxAccNoStatus(Utility.fnCheckNull(rsSelectServiceDetails.getString("FXACCNO_STATUS")));
				objViewOrderDto.setBillingTriggerStatus(Utility.fnCheckNull(rsSelectServiceDetails.getInt("BILLING_TRIGGER_STATUS")));
			
				
				
				alSelectServiceDetails.add(objViewOrderDto);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("No Record Found");
		} finally {
			try {
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("No Record Found");
			}
		}
		return alSelectServiceDetails;
	}*/
	

	//FOLLOWING FUNCTION FOR all change orders except disconnection
	public ArrayList<ViewOrderDto> getSELECT_BT_PRODUCTS_FOR_CHANGE_ORDERS(ViewOrderDto objViewdto,Connection optionalConnection) throws Exception{
		//	Nagarjuna
		String methodName="getSELECT_BT_PRODUCTS_FOR_CHANGE_ORDERS", className=this.getClass().getName(), msg1="";
		boolean logToFile=true, logToConsole=true;
		//end Nagarjuna	
		ArrayList<ViewOrderDto> alSelectServiceDetails = null;
		Connection conn = null;
		ResultSet rsSelectServiceDetails = null;
		CallableStatement csSelectServiceDetails =null;
		
		logger.info("getSELECT_BT_PRODUCTS_FOR_CHANGE_ORDERS method of ViewOrderDao class have been called");
		
		try {
			alSelectServiceDetails = new ArrayList<ViewOrderDto>();
			
			if(optionalConnection==null)
			{
				conn = DbConnection.getConnectionObject();
			}
			else
			{
				conn = optionalConnection;
			}
			csSelectServiceDetails= conn.prepareCall(sqlGet_Select_BT_PRODUCTS_CHANGE_ORDERS);
			csSelectServiceDetails.setLong(1,objViewdto.getPonum());
			if(objViewdto.getSearchLSI()==null)
			{
				csSelectServiceDetails.setNull(2, java.sql.Types.BIGINT);
			}
			else
			{
				csSelectServiceDetails.setLong(2,Long.parseLong(objViewdto.getSearchLSI()));
			}
			
			if(objViewdto.getCustLogicalSino()==null)
			{
				csSelectServiceDetails.setNull(3, java.sql.Types.BIGINT);
			}
			else
			{
				csSelectServiceDetails.setLong(3,Long.parseLong(objViewdto.getCustLogicalSino()));
			}
			
			if(objViewdto.getSearchLineTriggerStatus()==null)
			{
				csSelectServiceDetails.setNull(4, java.sql.Types.VARCHAR);
			}
			else
			{
				csSelectServiceDetails.setString(4,objViewdto.getSearchLineTriggerStatus());
			}
			
			if(objViewdto.getPagingSorting()!=null && objViewdto.getPagingSorting().isPagingToBeDone()==true)
			{
				csSelectServiceDetails.setLong(5, 1);
				csSelectServiceDetails.setLong(6, objViewdto.getPagingSorting().getStartRecordId());
				csSelectServiceDetails.setLong(7, objViewdto.getPagingSorting().getEndRecordId());
			}
			else
			{
				csSelectServiceDetails.setLong(5,0);
				csSelectServiceDetails.setLong(6,0);
				csSelectServiceDetails.setLong(7,0);
			}
			
			csSelectServiceDetails.setMaxRows(25);
			rsSelectServiceDetails=csSelectServiceDetails.executeQuery();
			int record_count =0;
			while (rsSelectServiceDetails.next()) {
				ViewOrderDto objViewOrderDto= new ViewOrderDto();
				objViewOrderDto.setPmProvisioningDate(Utility.fnCheckNull(rsSelectServiceDetails.getString("PMPROVISIONINGDATE")));
				objViewOrderDto.setLocNo(Utility.fnCheckNull(rsSelectServiceDetails.getString("LOCNO")));
				objViewOrderDto.setLocDate(Utility.fnCheckNull(rsSelectServiceDetails.getString("LOCDATE")));
				objViewOrderDto.setBillingTriggerDate(Utility.fnCheckNull(rsSelectServiceDetails.getString("BILLINGTRIGGERDATE")));
				objViewOrderDto.setLineNumber(Utility.fnCheckNull(rsSelectServiceDetails.getString("LINENUMBER")));    
				objViewOrderDto.setLineName(Utility.fnCheckNull(rsSelectServiceDetails.getString("LINENAME")));
				objViewOrderDto.setLogicalSino(Utility.fnCheckNull(rsSelectServiceDetails.getString("LOGICALSINO")));
				objViewOrderDto.setCustLogicalSino(Utility.fnCheckNull(rsSelectServiceDetails.getString("CUSTLOGICALSINO")));
				//objViewOrderDto.setFxSiid(Utility.fnCheckNull(rsSelectServiceDetails.getString("FXSIID")));
				objViewOrderDto.setSiid(Utility.fnCheckNull(rsSelectServiceDetails.getString("SIID")));
				//objViewOrderDto.setFxId(Utility.fnCheckNull(rsSelectServiceDetails.getString("FXID")));
				objViewOrderDto.setFx_ACCOUNT_EXTERNAL_ID(Utility.fnCheckNull(rsSelectServiceDetails.getString("FX_ACCOUNT_EXTERNAL_ID")));     
				objViewOrderDto.setFxStatus(Utility.fnCheckNull(rsSelectServiceDetails.getString("FX_ACCOUNT_STATUS")));
				objViewOrderDto.setFxAccNoStatus(Utility.fnCheckNull(rsSelectServiceDetails.getString("FXACCNO_STATUS")));
				objViewOrderDto.setBillingTriggerStatus(Utility.fnCheckNull(rsSelectServiceDetails.getInt("BILLING_TRIGGER_STATUS")));
				//objViewOrderDto.setBillingTriggerDisconnectStatus(Utility.fnCheckNull(rsSelectServiceDetails.getInt("BILLING_TRIGGER_DISCONNECT_STATUS")));
//				--objViewOrderDto.setLineProp(Utility.fnCheckNull(rsSelectServiceDetails.getString("LINE_PROP")));
//				--objViewOrderDto.setNoOfCharges(Utility.fnCheckNull(rsSelectServiceDetails.getString("NO_OF_CHARGES")));
//				--objViewOrderDto.setNoOfChargesForDisconnectForOldLine(Utility.fnCheckNull(rsSelectServiceDetails.getString("OLD_LINE_DISCONNECT_CHARGE_REQUEST_COUNT")));
				//003 start
				objViewOrderDto.setFx_Token_no(Utility.fnCheckNull(rsSelectServiceDetails.getString("TOKEN_ID")));
				objViewOrderDto.setFx_Response(Utility.fnCheckNull(rsSelectServiceDetails.getString("FX_RESPONSE")));
				objViewOrderDto.setFx_status(Utility.fnCheckNull(rsSelectServiceDetails.getString("FX_STATUS")));
				objViewOrderDto.setLine_status(Utility.fnCheckNull(rsSelectServiceDetails.getString("LINE_STATUS")));
			    objViewOrderDto.setChallen_No(Utility.fnCheckNull(rsSelectServiceDetails.getString("CHALLEN_NO")));
				objViewOrderDto.setChallen_date(Utility.fnCheckNull(rsSelectServiceDetails.getString("CHALLEN_DATE")));
			    //003 end
				//objViewOrderDto.setM6_att_fxchanged(Utility.fnCheckNull(rsSelectServiceDetails.getString("M6_ATT_FX_CHANGED")));
				objViewOrderDto.setIsLineDisconnected(Utility.fnCheckNull(rsSelectServiceDetails.getString("ISLINE_DISCONNECTED")));
				objViewOrderDto.setLineOldOrNew(Utility.fnCheckNull(rsSelectServiceDetails.getString("LINE_OLD_OR_NEW")));
				objViewOrderDto.setIsInHistory(rsSelectServiceDetails.getString("IS_IN_HISTORY"));
				objViewOrderDto.setServiceId(rsSelectServiceDetails.getString("SERVICEID"));
				//objViewOrderDto.setNoOfCharges_integer(rsSelectServiceDetails.getInt("TOTAL_CHARGES"));
				//objViewOrderDto.setNoOfChangedCharges(rsSelectServiceDetails.getInt("NO_CHANGED_CHARGES"));
				//objViewOrderDto.setNoOfDisconnectClose(rsSelectServiceDetails.getInt("NO_OF_DISCONNECT_CLOSE"));
				//objViewOrderDto.setNoOfDisconnectInactive(rsSelectServiceDetails.getInt("NO_OF_DISCONNECT_INACTIVE"));
			    objViewOrderDto.setDuplicate_cktid(rsSelectServiceDetails.getString("DUPLICATE_CKTID"));
			    objViewOrderDto.setCktId(rsSelectServiceDetails.getString("CKTID"));
				//004 start
				objViewOrderDto.setIsAutoBilling(rsSelectServiceDetails.getInt("ISAUTOBILLING"));
				objViewOrderDto.setServiceType(rsSelectServiceDetails.getString("SERVICETYPE"));
				objViewOrderDto.setSendToM6(rsSelectServiceDetails.getInt("SENDTOM6"));
				objViewOrderDto.setLocRecDate(Utility.fnCheckNull(rsSelectServiceDetails.getString("LOC_REC_DATE")));
				objViewOrderDto.setFxServiceUpdateStatus(Utility.fnCheckNull(rsSelectServiceDetails.getString("FX_SERVICE_UPDATE_STATUS")));
				//004 end
//				Meenakshi : added for Components
				//objViewOrderDto.setProductBillType(rsSelectServiceDetails.getString("PRODUCT_BILLING_TYPE"));
				objViewOrderDto.setIsUsage(rsSelectServiceDetails.getString("ISUSAGE"));
				//objViewOrderDto.setNoOfComponents(""+(rsSelectServiceDetails.getInt("NO_OF_COMPONENT")));
				//objViewOrderDto.setIsNewOrDisconnectedInCurrentService(rsSelectServiceDetails.getString("COMPONENT_NEW_OR_DISCONNECTED"));
				
				objViewOrderDto.setRedirectedLSI(rsSelectServiceDetails.getString("FX_REDIRECTION_LSI"));
				objViewOrderDto.setRedirectExternalAc(rsSelectServiceDetails.getString("FX_REDIRECT_ACCOUNT_EXTERNAL_ID"));
				objViewOrderDto.setRedirectInternalAc(rsSelectServiceDetails.getString("REDIRECTED_INTERNAL_ACCOUNT"));
				objViewOrderDto.setRedirect_status(rsSelectServiceDetails.getString("FX_CHARGE_REDIRECT_STATUS"));
				objViewOrderDto.setRedirect_status_desc(rsSelectServiceDetails.getString("FX_CHARGE_REDIRECT_STATUS_DESC"));
				
				objViewOrderDto.setCumulative_status(rsSelectServiceDetails.getString("CUMULATIVE_PROCESS_STATUS"));
				objViewOrderDto.setCumulative_status_desc(rsSelectServiceDetails.getString("CUMULATIVE_PROCESS_STATUS_DESC"));
				//[006] start
				objViewOrderDto.setBtDoneDate(Utility.showDate_Report3(rsSelectServiceDetails.getDate("BILLING_TRIGGER_CREATEDATE")));
				objViewOrderDto.setPoExpiryDate(Utility.showDate_Report3(rsSelectServiceDetails.getDate("POEXPIRY_DATE")));
				//objViewOrderDto.setLocdataupdate(rsSelectServiceDetails.getInt("LOCDATAUPDATE"));
				//[006] End
				
				//if("0".equals(objViewOrderDto.getNoOfCharges()))
				//{
					//continue;//Do not add to list if No Charges
				//}
				if(record_count ==0){
					record_count = rsSelectServiceDetails.getInt("FULL_REC_COUNT");
				}
				objViewdto.getPagingSorting().setRecordCount(record_count);
				//[082] start
				if(rsSelectServiceDetails.getString("BT_DONE_BY_NAME")==null || rsSelectServiceDetails.getString("BT_DONE_BY_NAME").equalsIgnoreCase(" ") || rsSelectServiceDetails.getString("BT_DONE_BY_NAME").equalsIgnoreCase("-"))  
				{
					objViewOrderDto.setBtDoneByName("Not Available");
				}
				else
				{
				objViewOrderDto.setBtDoneByName(Utility.fnCheckNull(rsSelectServiceDetails.getString("BT_DONE_BY_NAME")));
				}
				
				
				//[082] End
				alSelectServiceDetails.add(objViewOrderDto);
			}
			
		
		
		} catch (Exception e) {
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,"Exception"+msg1, logToFile, logToConsole);//added by nagarjuna
			//e.printStackTrace();
			throw new Exception("No Record Found");
		} finally {
			DbConnection .closeResultset(rsSelectServiceDetails);
			DbConnection.closeCallableStatement(csSelectServiceDetails);
			if(optionalConnection==null)
			{
				DbConnection.freeConnection(conn);
			}
			
		}
		return alSelectServiceDetails;
	}
	public ArrayList<ViewOrderDto> getOSNLIST(ArrayList ServieIds) throws Exception{
		//	Nagarjuna
		String methodName="getOSNLIST", className=this.getClass().getName(), msg1="";
		boolean logToFile=true, logToConsole=true;
		//end Nagarjuna	
		ArrayList<ViewOrderDto> alGetOSNLists = null;
		Connection conn = null;
		ResultSet rsGetOSNLists = null;
		CallableStatement csGetOSNLists =null;
		
		String str="";
		for(int i=0;i<ServieIds.size();i++)
		{
			str=str+","+ServieIds.get(i);
		}
		
		
		Utility.SysOut(str);
		if(str.length()>0)
		{
			str=str.substring(1);
		}
		
		logger.info("getOSNLIST method of ViewOrderDao class have been called");
		
		try {
			alGetOSNLists = new ArrayList<ViewOrderDto>();
			ViewOrderDto objdto=null;
			conn = DbConnection.getConnectionObject();
			csGetOSNLists= conn.prepareCall(sqlGet_Osn_List);
			csGetOSNLists.setString(1,str);
			rsGetOSNLists=csGetOSNLists.executeQuery();
			
			while(rsGetOSNLists.next())
			{
				objdto=new ViewOrderDto();
				objdto.setServiceId(rsGetOSNLists.getString("SERVICEID"));
				objdto.setEventids(rsGetOSNLists.getString("EVENTID"));
				alGetOSNLists.add(objdto);
				
		    }
			
		
			
			
			
		     }
		

	 catch (Exception e) {
		 Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,"Exception"+msg1, logToFile, logToConsole);//added by nagarjuna
		//e.printStackTrace();
		throw new Exception("No Record Found");
	   } 
	 finally
		{
			try {
				DbConnection.closeResultset(rsGetOSNLists);
				DbConnection.closeCallableStatement(csGetOSNLists);
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,"Exception"+msg1, logToFile, logToConsole);//added by nagarjuna
			}
		}
	return alGetOSNLists;
}
	
	/*public ArrayList getSelectChargeDetails(long orderNo) throws Exception{
		ArrayList<ViewOrderDto> alSelectChargeDetails= null;
		Connection conn = null;
		ResultSet rsSelectChargeDetails = null;
		CallableStatement csSelectChargeDetails=null;
		
		logger.info("View Order Interface and getTaskListOfOrder method of ViewOrderDao class have been called");
		
		try {
			alSelectChargeDetails = new ArrayList<ViewOrderDto>();
			
			conn = DbConnection.getConnectionObject();
			csSelectChargeDetails = conn.prepareCall(sqlGet_SelectChargeDetails);
			csSelectChargeDetails.setLong(1,orderNo);
			rsSelectChargeDetails=csSelectChargeDetails.executeQuery();
			
			while (rsSelectChargeDetails.next()) {
				ViewOrderDto objViewOrderDto= new ViewOrderDto();
				objViewOrderDto.setChargeType(Utility.fnCheckNull(rsSelectChargeDetails.getString("CHARGETYPE")));
				objViewOrderDto.setChargeName(Utility.fnCheckNull(rsSelectChargeDetails.getString("CHARGENAME")));
				objViewOrderDto.setChargePeriod(Utility.fnCheckNull(rsSelectChargeDetails.getString("CHARGEPERIOD")));
				objViewOrderDto.setChargeAmt(Utility.fnCheckNull(rsSelectChargeDetails.getString("CHARGEAMT")));     
				objViewOrderDto.setOrderNo(Utility.fnCheckNull(rsSelectChargeDetails.getString("ORDERNO")));
				objViewOrderDto.setServiceProductID(Utility.fnCheckNull(rsSelectChargeDetails.getString("SERVICEPRODUCTID")));
				objViewOrderDto.setViewId(Utility.fnCheckNull(rsSelectChargeDetails.getString("VIEWID")));
				objViewOrderDto.setChargeStatus(Utility.fnCheckNull(rsSelectChargeDetails.getString("CHARGESTATUS")));
				alSelectChargeDetails.add(objViewOrderDto);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("No Record Found");
		} finally {
			try {
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("No Record Found");
			}
		}
		return alSelectChargeDetails;
	}*/
	
//	For checking the Billing Trigger submit or not
	/**
	 * 
	 * 
	 *billing_trigger_status field
	 * 0 no data uet enteres
	10 data saved,
	8:data invalid because modification failed , validate and reenter data
	20 data in secondary tables
	25 pushed to fx but failed
	30 success in pushing to fx
	 */
/*	public int getBillingTriggerResult(long orderNo,String strBillingTrigger,String dataChanged,ViewOrderDto dto) throws Exception
	{
		logger.info("View Order Interface and getTaskListOfOrder method of ViewOrderModel class have been called");
		int publishResult=0;
		int errorCode=0;
		long longServiceProductId=0;
		long longBillingOrderNo=0;
		int arrLen=0;
		String locDate=null;
		String BillingTriggerDate=null;
		String locNo=null;
		Connection conn = null;
		CallableStatement csPublishOrder=null;
		//CallableStatement csChargeInfo=null;
		//CallableStatement csAccountInfo=null;
		//CallableStatement csServiceInfo=null;
		CallableStatement csServiceAndChargesInfo=null;
		CallableStatement csChargesInfo_AccontLevel=null;
		CallableStatement csUpdateBilling_Trigger_Status=null;
		CallableStatement csServiceAndComponentsInfo=null;
		try 
		{
			 conn 			= DbConnection.getConnectionObject();
			 csPublishOrder = conn.prepareCall(sqlUpdate_LocDate);
			// csChargeInfo 	= conn.prepareCall(sqlInsert_ChargeInfoFx);
			// csServiceInfo 	= conn.prepareCall(sqlInsert_ServiceInfoFx);
			 //csServiceAndChargesInfo = conn.prepareCall(sqlInsert_ServiceAndChargesInfoFx);
			 csServiceAndChargesInfo = conn.prepareCall(Utility.getAppConfigValue("SQLINSERT_SERVICEANDCHARGESINFOFX")); 
			 csChargesInfo_AccontLevel = conn.prepareCall(sqlInsert_Charges_AccountLevelInfoFx);
			 csUpdateBilling_Trigger_Status = conn.prepareCall(sqlUpdateBilling_Trigger_Status);
			 
			 csServiceAndComponentsInfo= conn.prepareCall(sqlInsert_ServiceAndComponentsInfoFx); 
			 
			 conn.setAutoCommit(false);
			 
			 longBillingOrderNo=orderNo;
			 
				 arrLen=strBillingTrigger.split("@@").length;
				 //fetch serviceType/productType for each selected seviceProduct
				 
				 
				 for(int i=1;i<=arrLen;i++)
				 {	 
					 //setautocommit=false
					 //STEP-1
					 //find out whether to save data or not
					 //if save=yes , call save ,and move to next STEP-2, on exception,rollback, skip all steps 
					 			//and log exception on step and move to next iteration 
					 //if save = no , move to next step
					 
					 //STEP-2
					 //if fresh data saved in above, then insert/update and move to STEP -3.
					 		//on exception , rollback,skip all STEPS and log exception step and move to next iteration
					 //if no fresh data , then move to STEP -3
					 //commit connection
					 
					 //STEP-3
					 //if data commited above ie(isDataValidForFX==1) then
					 //push data to FX and store result
					 // on exception rollback(ccancel order in FX) and log exception in IOES and next iteration
					 
					 
					 
					 String str=strBillingTrigger.split("@@")[i-1];
					 String individual[]=str.split("~");
					 longServiceProductId=Long.parseLong(individual[0]);
					 locNo=individual[1];
					 locDate=individual[2];
					 BillingTriggerDate=individual[3];
					 
					 ViewOrderDto info=getServiceProductInfo(conn,longServiceProductId);
					 String productType=info.getProductType();
					 String  billType = info.getProductBillType();
					  
					 int isDataValidForFX=0;
					 
					 //STEP-1
					 String isSP_DataChanged=dataChanged.split("@@")[i-1];
					 if("1".equals(isSP_DataChanged))
					 {
						 try
						 {
							 
							 //For LOC update
							 csPublishOrder.setLong(1,longServiceProductId);
							 csPublishOrder.setString(2,locDate);
							 csPublishOrder.setString(3,BillingTriggerDate);
							 csPublishOrder.setString(4,locNo);
							 
							 csPublishOrder.execute();			
							 //errorCode=csPublishOrder.getInt(6);
							 
							 //STEP-2
							 //since data is changed , insert/update secondary tables for this serviceProduct
							 String msgCode=null;
							 String msg=null;
							 int sqlCode=0;
							 if(AppConstants.Product_Bill_Type_Flat.equalsIgnoreCase(billType))
							 {
								 if(AppConstants.ProductType_Hardware.equalsIgnoreCase(productType) )
								 {
									 csChargesInfo_AccontLevel.setLong(1, longServiceProductId);
									 
									 csChargesInfo_AccontLevel.registerOutParameter(2, java.sql.Types.INTEGER);
									 csChargesInfo_AccontLevel.registerOutParameter(3, java.sql.Types.VARCHAR);
									 csChargesInfo_AccontLevel.registerOutParameter(4, java.sql.Types.VARCHAR);
									
									 csChargesInfo_AccontLevel.setLong(5,orderNo);
									 
									 csChargesInfo_AccontLevel.execute();
									 
									 sqlCode=csChargesInfo_AccontLevel.getInt(2);
									 msgCode= csChargesInfo_AccontLevel.getString(3);
									 msg= csChargesInfo_AccontLevel.getString(4);
								 }
								 else if(AppConstants.ProductType_Bandwidth.equalsIgnoreCase(productType)|| AppConstants.ProductType_Service.equalsIgnoreCase(productType))
								 {
									 csServiceAndChargesInfo.clearParameters();
									 csServiceAndChargesInfo.setLong(1, longServiceProductId);
									 
									 csServiceAndChargesInfo.registerOutParameter(2, java.sql.Types.INTEGER);
									 csServiceAndChargesInfo.registerOutParameter(3, java.sql.Types.VARCHAR);
									 csServiceAndChargesInfo.registerOutParameter(4, java.sql.Types.VARCHAR);
									 //csServiceAndChargesInfo.setInt(2, 0);
									 //csServiceAndChargesInfo.setString(3, "");
									 //csServiceAndChargesInfo.setString(4, "");
									 csServiceAndChargesInfo.setLong(5,orderNo);
									 
									 csServiceAndChargesInfo.execute();
									 sqlCode=csServiceAndChargesInfo.getInt(2);
									 msgCode= csServiceAndChargesInfo.getString(3);
									 msg= csServiceAndChargesInfo.getString(4);								 
								 }
							 }
							 else if(AppConstants.Product_Bill_Type_Usage.equalsIgnoreCase(billType))
							 {
								 if(AppConstants.ProductType_Bandwidth.equalsIgnoreCase(productType)
										 || 
										 AppConstants.ProductType_Service.equalsIgnoreCase(productType)) {
									 
									 //TODO: 
									 csServiceAndComponentsInfo.clearParameters();
									 csServiceAndComponentsInfo.setLong(1, longServiceProductId);
									 
									 csServiceAndComponentsInfo.registerOutParameter(2, java.sql.Types.INTEGER);
									 csServiceAndComponentsInfo.registerOutParameter(3, java.sql.Types.VARCHAR);
									 csServiceAndComponentsInfo.registerOutParameter(4, java.sql.Types.VARCHAR);
									 //csServiceAndChargesInfo.setInt(2, 0);
									 //csServiceAndChargesInfo.setString(3, "");
									 //csServiceAndChargesInfo.setString(4, "");
									 csServiceAndComponentsInfo.setLong(5,orderNo);
									 
									 csServiceAndComponentsInfo.execute();
									 sqlCode=csServiceAndComponentsInfo.getInt(2);
									 msgCode= csServiceAndComponentsInfo.getString(3);
									 msg= csServiceAndComponentsInfo.getString(4);		
								 }
							 }
							 
							 
							 if("0".equals(msgCode))
							 {
								 conn.commit();
								 isDataValidForFX=1;	 
							 }
							 else
							 {
								 throw new Exception("errors in proc : IOE.SP_INSERT_UPDATE_SERVICE_N_CHARGES_FX or SP_INSERT_UPDATE_ACCOUNTLEVEL_CHARGES , msgcode="+msgCode+"  msg="+msg+" sqlcode:"+sqlCode);
							 }
							 
							 
						 }
						 catch(Exception ex)
						 {
							 isDataValidForFX=0;
							 conn.rollback();
							 String msg="Exception ins STEP-1 or STEP-2 on Billin gtrigger for longServiceProductId="+longServiceProductId;
							 Utility.onEx_LOG_RET_NEW_EX(ex, "","", msg, true,true);
							 //update billing_trigger_status to 8
							 csUpdateBilling_Trigger_Status.clearParameters();
							 csUpdateBilling_Trigger_Status.setLong(1, longServiceProductId);
							 csUpdateBilling_Trigger_Status.setLong(2, AppConstants.Billing_Trigger_Status_RENTER_DATA_FROM_USER);
							 csUpdateBilling_Trigger_Status.execute();
							 continue;
						 }
						 
					 }
					 else
					 {
						 isDataValidForFX=1;
					 }
					 
					 isDataValidForFX=0;// Now charges will not be send to FX as flag is 0 , they will be sent by scgedular
					 //STEP-3
					 if(isDataValidForFX==1)
					 {
						 //Substeps
						 //1.read data in bean from secondary tables
						 	//1.2 if success next substep->2 
						 	//1.4 if exception update Billing_Trigger_Status and contiue with next serviceproduct(iteration)
						 
						 //2.send bean to FX
						 	//2.2 if success,then commit order , and update Billing_Trigger_Status 
						 	//2.4 if failure or exception then cancel order and update Billing_Trigger_Status
						 
						 //3. continue with next 
						 
						 //Impl
						 //1.
						 ServiceDTO serDto = null;
						 ChargesDto chargesDto = null;
						 //rEADIN dto FROM LOCAL FX TABLES
						 try
						 {
							 if(AppConstants.ProductType_Hardware.equals(productType) )
							 {
								 chargesDto=fetchNextAccountLevelChargesData(conn,longServiceProductId);
							 }
							 else if(AppConstants.ProductType_Bandwidth.equals(productType)|| AppConstants.ProductType_Service.equals(productType))
							 {
								 serDto=fetchNextServiceNChargesData(conn,longServiceProductId);	 
							 }
							 
						 }
						 catch(Exception ex)
						 {
							 Utility.LOG(true, true, "Exception ins STEP-3 on Billing trigger :populating bean for longServiceProductId="+longServiceProductId);
							 //update billing_trigger_status to 8
							 csUpdateBilling_Trigger_Status.clearParameters();
							 csUpdateBilling_Trigger_Status.setLong(1, longServiceProductId);
							 csUpdateBilling_Trigger_Status.setLong(2, AppConstants.Billing_Trigger_Status_FX_FAILURE_LOCAL_BEAN_READ);
							 csUpdateBilling_Trigger_Status.execute();
							 continue;
						 }
						 
						 
						 if(AppConstants.ProductType_Hardware.equals(productType) )
						 {
							 if(chargesDto==null)
							 {
								 String msg="chargesDto==null for longServiceProductId="+longServiceProductId;
								 Utility.LOG(true, true,msg );
								 continue;
							 }
						 }
						 else if(AppConstants.ProductType_Bandwidth.equals(productType)|| AppConstants.ProductType_Service.equals(productType))
						 {
							 if(serDto==null)
							 {
								 String msg="serDto==null for longServiceProductId="+longServiceProductId;
								 Utility.LOG(true, true,msg );
								 continue;
							 } 
						 }
						 
						 
						 //2.PUSHING DTO INFO TO FX
						 if(AppConstants.ProductType_Hardware.equals(productType))
						 {
							 sendToFx(chargesDto);//TODO
						 }
						 else if(AppConstants.ProductType_Bandwidth.equals(productType) || AppConstants.ProductType_Service.equals(productType))
						 {
							 sendToFx(serDto);	 
						 }
						 
						 int fx_status=0;
						 
						 //SAVING THE RETURNED VALUES//TODO 
						 try
						 {
							 if(AppConstants.ProductType_Hardware.equals(productType) )
							 {
								 fx_status=chargesDto.getReturnStatus();
								 setFxOperationStatusForAccountLevelCharges(conn, chargesDto, fx_status);
							 }
							 else if(AppConstants.ProductType_Bandwidth.equals(productType)|| AppConstants.ProductType_Service.equals(productType))
							 {
								 fx_status=serDto.getReturnStatus();
								 setFxOperationStatusForServiceLevelCharges(conn, serDto, fx_status);	 
							 }
							 
						 }
						 catch(Exception ex)
						 {
							 Utility.onEx_LOG_RET_NEW_EX(ex, "", "","", true,true);
							 if(fx_status==1)
							 {
								 //log for unupdation of fx details in IOES , as a error and warning
								 String msg="FX services and charges created but not updated in IOES for serviceproductid:"+longServiceProductId;
								 Utility.LOG(true,true,msg);
							 }
							 continue;
						 }
						 
					 }
				 }
			
			 	
			 
			 
			 System.out.println(" getBillingTriggerResult of Inside Model of View Order Interface");
		} 
		catch (Exception ex) 
		{
			logger.error(ex.getMessage()+ " Exception occured in getBillingTriggerResult method of "+ this.getClass().getSimpleName());
			ex.printStackTrace();
			publishResult=1;
			conn.rollback();
			throw Utility.onEx_LOG_RET_NEW_EX(ex, "", "", "", true, true);
		} 
		finally
		{
			try 
			{
				
				DbConnection.freeConnection(conn);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				throw new Exception("No Record Found");
			}
		}
		return publishResult;
	 }	*/
	//created by manisha for change billing trigger
	
	public ArrayList<ViewOrderDto> getBillingTriggerResultForChange(long orderNo,String strBillingTrigger,String dataChanged,ViewOrderDto dto) throws Exception
	{
		//	Nagarjuna
		String methodName="getBillingTriggerResultForChange", className=this.getClass().getName(), msg1="";
		boolean logToFile=true, logToConsole=true;
		//end Nagarjuna	
		
		logger.info("View Order Interface and getTaskListOfOrder method of ViewOrderModel class have been called");
		
		
		long longServiceProductId=0;
		long longBillingOrderNo=0;
		int arrLen=0;
		String locDate=null;
		String BillingTriggerDate=null;
		String locNo=null;
		Connection conn = null;
		String str1="";
        CallableStatement csChargesInfo_ForChange=null;
	 ArrayList alSelectChargeDetails_forcahnge=null;
	 ResultSet rsChargeDetails_forChange=null;
		
		
		try 
		{
			 conn = DbConnection.getConnectionObject();
			  longBillingOrderNo=orderNo;
			 
				 arrLen=strBillingTrigger.split("@@").length;
				 
				 for(int i=1;i<=arrLen;i++)
				 {	 
					 
					 
					 String str=strBillingTrigger.split("@@")[i-1];
					 String individual[]=str.split("~");
					 longServiceProductId=Long.parseLong(individual[0]);
					 locNo=individual[1];
					 locDate=individual[2];
					 BillingTriggerDate=individual[3];
					 					 
			        str1=str1 + ',' + longServiceProductId ;
					 
				 }
				 if(str1.length()>0)
					{
						str1=str1.substring(1);
					}
				System.out.print(str1);
				  alSelectChargeDetails_forcahnge = new ArrayList<ViewOrderDto>();
						
                    csChargesInfo_ForChange= conn.prepareCall(sqlGet_SelectServiceDetails_InFx_ForChange);
						csChargesInfo_ForChange.setString(1,str1);
						rsChargeDetails_forChange=csChargesInfo_ForChange.executeQuery();
						
						while (rsChargeDetails_forChange.next()) {
							
							
							
						ViewOrderDto objViewOrderDto=new ViewOrderDto();
						objViewOrderDto.setChargeTypeName(rsChargeDetails_forChange.getInt("CHARGESTYPE"));
						objViewOrderDto.setRcId(rsChargeDetails_forChange.getInt("RCID"));
						objViewOrderDto.setNrcId(rsChargeDetails_forChange.getInt("NRCID"));
						objViewOrderDto.setChargeDisconnectDate(rsChargeDetails_forChange.getTimestamp("EFFECTIVEDATE"));
						objViewOrderDto.setFx_Status(rsChargeDetails_forChange.getInt("FX_STATUS"));
						objViewOrderDto.setCharge_info_id(rsChargeDetails_forChange.getInt("CHARGEINFOID"));
						
						alSelectChargeDetails_forcahnge.add(objViewOrderDto);
								
							
							
						}
					
					} catch (Exception e) {
						 Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,"Exception"+msg1, logToFile, logToConsole);//added by nagarjuna
						//e.printStackTrace();
						throw new Exception("No Record Found");
					} finally {
						try {
							DbConnection.closeResultset(rsChargeDetails_forChange);
							DbConnection .closeCallableStatement(csChargesInfo_ForChange);
							DbConnection.freeConnection(conn);
						} catch (Exception e) {
							 Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,"Exception"+msg1, logToFile, logToConsole);//added by nagarjuna
							//e.printStackTrace();
							throw new Exception("No Record Found");
						}
					}
					return alSelectChargeDetails_forcahnge;
				 
				 
				 
				 
	}
		
		
	//added to get fx_status	
	public int getfx_status(String str)
	{
		//	Nagarjuna
		String methodName="getfx_status", className=this.getClass().getName(), msg1="";
		boolean logToFile=true, logToConsole=true;
		//end Nagarjuna	
		int count=0;
		
		logger.info("View Order Interface and getTaskListOfOrder method of ViewOrderModel class have been called");
		Connection conn = null;
		 CallableStatement csFx_status_ForChange=null;
		  ResultSet rsFx_Status_forChange=null;
			
			
			try 
			{
				 conn = DbConnection.getConnectionObject();
				 csFx_status_ForChange= conn.prepareCall(sqlGet_SelectFx_Sttus_InFx_ForChange);
				 csFx_status_ForChange.setString(1,str);
				 rsFx_Status_forChange=csFx_status_ForChange.executeQuery();
				 if(rsFx_Status_forChange.next())
				 {
					 count=rsFx_Status_forChange.getInt("fx_status");
					 
				 }
				 
				 
				 
				 
		
		
	}
			catch (Exception e) {
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,"Exception"+msg1, logToFile, logToConsole);//added by nagarjuna
				//e.printStackTrace();
			
			} finally {
				try {
					DbConnection.closeResultset(rsFx_Status_forChange);
					DbConnection .closeCallableStatement(csFx_status_ForChange);
					DbConnection.freeConnection(conn);
				} catch (Exception e) {
					//e.printStackTrace();
					Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,"Exception"+msg1, logToFile, logToConsole);//added by nagarjuna
					
				}
			}
	
	

	return count;
	
	
	}
	
	
	
	public void sendToFx(ChargesDto chargesDto)  throws Exception{
		
		//	Nagarjuna
		String methodName="sendToFx", className=this.getClass().getName(), msg1="";
		boolean logToFile=true, logToConsole=true;
		//end Nagarjuna	
		try
		{
			IOESKenanManager API=new IOESKenanManager();
			API.loginKenan();
			try
			{
				//API.createService1(serDto);
				API.sendToFxAccount_Level_RCs_or_NRCs(chargesDto);
			}
			finally
			{
				API.close();
			}
		}
		catch (Exception ex) {
			Utility.onEx_LOG_RET_NEW_EX(ex, "sendToFx", this.getClass().getName(), null, true, true);
		}
		
	}

	public ViewOrderDto getServiceProductInfo(Connection conn, long longServiceProductId) throws Exception{
		ViewOrderDto dto = null;
		String methodName="getServiceProductInfo", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;
		logger.info(methodName+" method of "+className+" class have been called");	
		ResultSet rs=null;
		CallableStatement cstmt = null ;
		try
		{
			cstmt=conn.prepareCall(sqlInfo_ServiceProduct);
			cstmt.setLong(1, longServiceProductId);
			rs=cstmt.executeQuery();
			if(rs.next())
			{
				dto = new ViewOrderDto();
				dto.setProductType(rs.getString("SERVICETYPE"));
			//	dto.setProductBillType(rs.getString("PRODUCT_BILLING_TYPE"));
			
			}
		}
		catch(Exception ex)
		{
			throw Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);
		}
		finally
		{
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(cstmt);
		}
		return dto;
}

	

public ArrayList<ChargeSummaryServiceDto> getChargeSummaryServices(Connection conn, long orderNo) throws IOESException{
		
		ArrayList<ChargeSummaryServiceDto> services = null;
		String methodName="getChargeSummaryServices", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;
		logger.info(methodName+" method of "+className+" class have been called");
		CallableStatement csChargeSummaryServicesList = null ;
		//lawkush Start
		Utility utility=new Utility();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		//lawkush End
		
		try
		{
			services = new ArrayList<ChargeSummaryServiceDto>();
			
			conn = DbConnection.getConnectionObject();
			csChargeSummaryServicesList = conn.prepareCall(sqlGet_ChargeSummaryServicesList);
			
			csChargeSummaryServicesList.setLong(1,orderNo);
			ResultSet rsChargeSummaryServicesList=csChargeSummaryServicesList.executeQuery();
			ChargeSummaryServiceDto dto=null;
			
			while (rsChargeSummaryServicesList.next()) 
			{
				dto=new ChargeSummaryServiceDto();
				
				dto.setServiceId(rsChargeSummaryServicesList.getString("SERVICEID"));
				dto.setCustLogicalSINo(rsChargeSummaryServicesList.getString("CUSTLOGICALSINO"));
				dto.setLogicalSINo(rsChargeSummaryServicesList.getString("LOGICALSINO"));
				dto.setEffectiveEndDate(rsChargeSummaryServicesList.getString("EFFECTIVEENDDATE"));
				dto.setEffectiveStartDate(rsChargeSummaryServicesList.getString("EFFECTIVESTARTDATE"));
				dto.setRemarks(rsChargeSummaryServicesList.getString("REMARKS"));
				dto.setServiceStage(rsChargeSummaryServicesList.getString("SERVICESTAGE"));
				dto.setServiceSubType(rsChargeSummaryServicesList.getString("SERVICESUBTYPE"));
				dto.setServiceType(rsChargeSummaryServicesList.getString("SERVICETYPE"));
				//lawkush start
				
				dto.setProductType(rsChargeSummaryServicesList.getString("PRODUCTNAME"));
				dto.setRfsDate(rsChargeSummaryServicesList.getString("RFS_DATE"));
				
				if (rsChargeSummaryServicesList.getString("RFS_DATE") != null && !"".equals(rsChargeSummaryServicesList.getString("RFS_DATE")))
				{
					dto.setRfsDate((utility.showDate_Report(new Date(rsChargeSummaryServicesList.getTimestamp("RFS_DATE").getTime()))).toUpperCase());
				}
				
				
				//lawkush End
				services.add(dto);
			}
		}
		catch(Exception ex)
		{
			throw Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);
		}
		finally
		{
			try {
				
				DbConnection .closeCallableStatement(csChargeSummaryServicesList);
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return services;
	}

		public ArrayList<ChargeSummaryProductCatelog> getChargeProductCatelogs(Connection conn, long orderNo) throws IOESException {
			
			ArrayList<ChargeSummaryProductCatelog> list = null;
			String methodName="getChargeProductCatelogs", className=this.getClass().getName(), msg="";
			logger.info(methodName+" method of "+className+" class have been called");
			boolean logToFile=true, logToConsole=true;
			
			CallableStatement cstmt = null ;
			ResultSet rs=null;
			try
			{
				list = new ArrayList<ChargeSummaryProductCatelog>();
				
				conn = DbConnection.getConnectionObject();
				cstmt = conn.prepareCall(sqlGet_ChargeSummaryProductCatelogList);
				
				cstmt.setLong(1,orderNo);
				rs=cstmt.executeQuery();
				ChargeSummaryProductCatelog dto=null;
				
				while (rs.next()) 
				{
					dto=new ChargeSummaryProductCatelog();
					
					dto.setServiceId(rs.getString("SERVICEID"));
					dto.setServiceproductid(rs.getString("SERVICEPRODUCTID"));
					dto.setProductName(rs.getString("PRODUCTNAME"));
					dto.setServiceType(rs.getString("SERVICETYPE"));
					
					//billing information
					dto.setCustPODetailNo(rs.getString("CUSTPODETAILNO"));
					dto.setPoDate(rs.getString("PODATE"));
					dto.setContractPeriod(rs.getString("CONTRACTPERIOD"));
					dto.setAcNo(rs.getString("ACNO"));
					dto.setCreditPeriod(rs.getString("CREDITPERIOD"));
					dto.setCurrency(rs.getString("CURRENCY"));
					dto.setLegalEntity(rs.getString("LEGALENTITY"));
					dto.setBillingMode(rs.getString("BILLINGMODE"));
					dto.setBillFormat(rs.getString("BILLFORMAT"));
					dto.setLicenceCo(rs.getString("LICENCECO"));
					dto.setBillingType(rs.getString("BILLINGTYPE"));
					dto.setTaxation(rs.getString("TAXATION"));
					dto.setCommitmentPeriod(rs.getString("COMMITMENTPERIOD"));
					dto.setBillingLevel(rs.getString("BILLINGLEVEL"));
					dto.setPenaltyClause(rs.getString("PENALTYCLAUSE"));
					//billing address
					dto.setBcpDetails_BillingAddress(rs.getString("BCPDETAILS_BILLINGADDRESS"));
					dto.setAddress1_BillingAddress(rs.getString("ADDRESS1_BILLINGADDRESS"));
					dto.setAddress2_BillingAddress(rs.getString("ADDRESS2_BILLINGADDRESS"));
					dto.setAddress3_BillingAddress(rs.getString("ADDRESS3_BILLINGADDRESS"));
					dto.setAddress4_BillingAddress(rs.getString("ADDRESS4_BILLINGADDRESS"));
					dto.setCity_BillingAddress(rs.getString("CITY_BillingAddress"));
					dto.setState_BillingAddress(rs.getString("STATE_BILLINGADDRESS"));
					dto.setPostalCode_BillingAddress(rs.getString("POSTALCODE_BILLINGADDRESS"));
					dto.setCountry_BillingAddress(rs.getString("COUNTRY_BILLINGADDRESS"));
					
					
					//service Location
					dto.setLocType_Primary(rs.getString("LOCTYPE_PRIMARY"));
					dto.setLocationCode_Primary(rs.getString("LOCATIONCODE_PRIMARY"));
					dto.setAddress_Primary(rs.getString("ADDRESS_PRIMARY"));
					dto.setBcpDetails_Primary(rs.getString("BCPDETAILS_PRIMARY"));
					dto.setAddress1_Primary(rs.getString("ADDRESS1_PRIMARY"));
					dto.setAddress2_Primary(rs.getString("ADDRESS2_PRIMARY"));
					dto.setAddress3_Primary(rs.getString("ADDRESS3_PRIMARY"));
					dto.setAddress4_Primary(rs.getString("ADDRESS4_PRIMARY"));
					dto.setCity_Primary(rs.getString("CITY_PRIMARY"));
					dto.setState_Primary(rs.getString("STATE_PRIMARY"));
					dto.setPostalCode_Primary(rs.getString("POSTALCODE_PRIMARY"));
					dto.setCountry_Primary(rs.getString("COUNTRY_PRIMARY"));
					
					dto.setLocType_Secondary(rs.getString("LOCTYPE_SECONDARY"));
					dto.setLocationCode_Secondary(rs.getString("LOCATIONCODE_SECONDARY"));
					dto.setAddress_Secondary(rs.getString("ADDRESS_SECONDARY"));
					dto.setBcpDetails_Secondary(rs.getString("BCPDETAILS_SECONDARY"));
					dto.setAddress1_Secondary(rs.getString("ADDRESS1_SECONDARY"));
					dto.setAddress2_Secondary(rs.getString("ADDRESS2_SECONDARY"));
					dto.setAddress3_Secondary(rs.getString("ADDRESS3_SECONDARY"));
					dto.setAddress4_Secondary(rs.getString("ADDRESS4_SECONDARY"));
					dto.setCity_Secondary(rs.getString("CITY_SECONDARY"));
					dto.setState_Secondary(rs.getString("STATE_SECONDARY"));
					dto.setPostalCode_Secondary(rs.getString("POSTALCODE_SECONDARY"));
					dto.setCountry_Secondary(rs.getString("COUNTRY_SECONDARY"));
					
					//hardware
					dto.setStore(rs.getString("STORE"));
					dto.setHardwareType(rs.getString("HARDWARETYPE"));
					dto.setFormAvailable(rs.getString("FORMAVAILABLE"));
					dto.setNatureOfSale(rs.getString("NATUREOFSALE"));
					dto.setTypeOfSale(rs.getString("TYPEOFSALE"));
					
					//dispatch address
					dto.setDispatchAddressCode(rs.getString("DISPATCHADDRESSCODE"));
					dto.setAddress(rs.getString("ADDRESS"));
					dto.setCityName(rs.getString("CITYNAME"));
					dto.setStateName(rs.getString("STATENAME"));
					dto.setPinCode(rs.getString("PINCODE"));
					dto.setPhoneNo(rs.getString("PHONENO"));
					dto.setCountryName(rs.getString("COUNTRYNAME"));
					//[010] start
					dto.setSiId(rs.getString("SI_ID"));
					//[010] end
					//chargesDetails(rs.getString("//CHARGESDETAILS"));
					/*dto.setType(rs.getString("TYPE"));
					dto.setName(rs.getString("NAME"));
					dto.setChargePeriod(rs.getString("CHARGEPERIOD"));
					dto.setTotalAmount(rs.getString("TOTALAMOUNT"));
					dto.setFrequency(rs.getString("FREQUENCY"));
					dto.setFrequencyAmount(rs.getString("FREQUENCYAMOUNT"));
					dto.setStartDateLogic(rs.getString("STARTDATELOGIC"));
					dto.setEndDateLogic(rs.getString("ENDDATELOGIC"));*/



					
					/*dto.setCustLogicalSINo(rs.getString("CUSTLOGICALSINO"));
					dto.setEffectiveEndDate(rs.getString("EFFECTIVEENDDATE"));
					dto.setEffectiveStartDate(rs.getString("EFFECTIVESTARTDATE"));
					dto.setRemarks(rs.getString("REMARKS"));
					dto.setServiceStage(rs.getString("SERVICESTAGE"));
					dto.setServiceSubType(rs.getString("SERVICESUBTYPE"));
					dto.setServiceType(rs.getString("SERVICETYPE"));*/
					
					list.add(dto);
				}
			}
			catch(Exception ex)
			{
				throw Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);
			}
			finally
			{
				try {
					DbConnection.closeResultset(rs);
					DbConnection.closeCallableStatement(cstmt);
					DbConnection.freeConnection(conn);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return list;
		}
// 078 start	
public ArrayList<ChargeSummaryChargeSection> getChargeSummaryChargesSection(Connection conn, long orderNo) throws IOESException{
		
		ArrayList<ChargeSummaryChargeSection> charges = null;
		String methodName="getChargeSummaryChargeSection", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;
		logger.info(methodName+" method of "+className+" class have been called");
		CallableStatement csChargeSummaryChargesList = null ;
		ResultSet rsChargeSummaryChargesList=null;
		try
		{
			charges = new ArrayList<ChargeSummaryChargeSection>();
			
			conn = DbConnection.getConnectionObject();
			csChargeSummaryChargesList = conn.prepareCall(sqlGet_ChargeSummaryChargesList);
			csChargeSummaryChargesList.setLong(1,0);
			csChargeSummaryChargesList.setLong(2,orderNo);
			
			rsChargeSummaryChargesList=csChargeSummaryChargesList.executeQuery();
			ChargeSummaryChargeSection dto=null;
			
			while (rsChargeSummaryChargesList.next()) 
			{
				dto=new ChargeSummaryChargeSection();
				dto.setCharges_type(rsChargeSummaryChargesList.getString("CHARGESTYPE"));
				dto.setCharge_name(rsChargeSummaryChargesList.getString("CHARGE_NAME"));
				dto.setChargeperiod(rsChargeSummaryChargesList.getString("CHARGEPERIOD"));
				dto.setChargeamount(rsChargeSummaryChargesList.getString("CHARGEAMOUNT"));
				dto.setChargestatus(rsChargeSummaryChargesList.getString("CHARGESTATUS"));
				dto.setSpid(rsChargeSummaryChargesList.getString("SERVICEPRODUCTID"));
				dto.setIs_line_dis(rsChargeSummaryChargesList.getString("IS_LINE_DISCONNECTED"));
				dto.setDisconnetion_type(rsChargeSummaryChargesList.getString("DISCONNECTION_TYPE"));
				dto.setBilling_trigger_status(rsChargeSummaryChargesList.getString("BILLING_TRIGGER_STATUS"));
				dto.setBill_period(rsChargeSummaryChargesList.getString("BILL_PERIOD"));
				dto.setBillperiod(rsChargeSummaryChargesList.getString("BILLPERIOD"));
				dto.setChargefrequency(rsChargeSummaryChargesList.getString("CHARGEFREQUENCY"));
				dto.setFrequencyamt(rsChargeSummaryChargesList.getString("CHARGEFREQUENCYAMT"));
				dto.setStartdatelogic(rsChargeSummaryChargesList.getString("STARTDATELOGIC_STRING"));
				dto.setStart_date_days(rsChargeSummaryChargesList.getString("START_DATE_DAYS"));
				dto.setStart_date_month(rsChargeSummaryChargesList.getString("START_DATE_MONTH"));
				dto.setCharge_start_date(rsChargeSummaryChargesList.getString("CHARGE_START_DATE"));
				dto.setEnddatelogic(rsChargeSummaryChargesList.getString("ENDDATELOGIC_STRING"));
				dto.setEnd_date_days(rsChargeSummaryChargesList.getString("END_DATE_DAYS"));
				dto.setEnd_date_month(rsChargeSummaryChargesList.getString("END_DATE_MONTH"));
				dto.setCharge_end_date(rsChargeSummaryChargesList.getString("CHARGE_INITIAL_END_DATE"));
				dto.setAnnual_rate(rsChargeSummaryChargesList.getString("ANNUAL_RATE"));
				dto.setAnnotation(rsChargeSummaryChargesList.getString("ANNOTATION"));
				dto.setCharge_id(rsChargeSummaryChargesList.getString("CHARGE_ID"));
				dto.setFx_view_id(rsChargeSummaryChargesList.getString("FX_VIEW_ID"));
				dto.setCharge_start_status(rsChargeSummaryChargesList.getString("CHARGE_START_STATUS"));
				dto.setStart_token_no(rsChargeSummaryChargesList.getString("START_TOKEN_NO"));
				dto.setStart_fx_status(rsChargeSummaryChargesList.getString("START_FX_STATUS"));
				dto.setStart_fx_no(rsChargeSummaryChargesList.getString("START_FX_NO"));
				dto.setCharge_end_status(rsChargeSummaryChargesList.getString("CHARGE_END_STATUS"));
				dto.setEnd_token_no(rsChargeSummaryChargesList.getString("END_TOKEN_NO"));
				dto.setEnd_fx_status(rsChargeSummaryChargesList.getString("END_FX_STATUS"));
				dto.setEnd_fx_no(rsChargeSummaryChargesList.getString("END_FX_NO"));
				dto.setTax_Rate(rsChargeSummaryChargesList.getString("TAXRATE"));
				//lawkush Start
				dto.setChargeRemarks(rsChargeSummaryChargesList.getString("REMARKS"));
				//lawkush End
				
				charges.add(dto);
			}
		}
		catch(Exception ex)
		{
			throw Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);
		}
		finally
		{
			try {
				DbConnection.closeResultset(rsChargeSummaryChargesList);
				DbConnection.closeCallableStatement(csChargeSummaryChargesList);
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return charges;
	}

// 078 end

// 079 start

public ArrayList<ChargeSummaryComponentSection> getChargeSummaryComponentSection(Connection conn, long orderNo) throws IOESException{
	
	ArrayList<ChargeSummaryComponentSection> components = null;
	String methodName="getChargeSummaryServices", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	logger.info(methodName+" method of "+className+" class have been called");
	CallableStatement csChargeSummaryComponentsList = null ;
	try
	{
		components = new ArrayList<ChargeSummaryComponentSection>();
		
		conn = DbConnection.getConnectionObject();
		csChargeSummaryComponentsList = conn.prepareCall(sqlGet_ChargeSummaryComponentsList);
		csChargeSummaryComponentsList.setLong(1,0);
		csChargeSummaryComponentsList.setLong(2,orderNo);
		
		ResultSet rsChargeSummaryComponentsList=csChargeSummaryComponentsList.executeQuery();
		ChargeSummaryComponentSection dto=null;
		
		while (rsChargeSummaryComponentsList.next()) 
		{
			dto=new ChargeSummaryComponentSection();
			
			dto.setComponent_id(rsChargeSummaryComponentsList.getString("COMPONENT_ID"));
			dto.setComponent_name(rsChargeSummaryComponentsList.getString("COMPONENT_NAME"));
			dto.setPackage_id(rsChargeSummaryComponentsList.getString("PACKAGE_ID"));
			dto.setPackage_name(rsChargeSummaryComponentsList.getString("PACKAGE_NAME"));
			dto.setComponent_status(rsChargeSummaryComponentsList.getString("COMPONENT_STATUS"));
			dto.setServiceproductid(rsChargeSummaryComponentsList.getString("SERVICEPRODUCTID"));
			dto.setCreated_in_orderno(rsChargeSummaryComponentsList.getString("CREATED_IN_ORDER"));
			dto.setDisconnect_in_orderno(rsChargeSummaryComponentsList.getString("DISCONNECTED_IN_ORDER_NO"));
			dto.setComponent_start_logic(rsChargeSummaryComponentsList.getString("COMPONENT_START_LOGIC"));
			dto.setComponent_start_days(rsChargeSummaryComponentsList.getString("START_DAYS"));
			dto.setComponent_start_month(rsChargeSummaryComponentsList.getString("START_MONTHS"));
			dto.setComponent_start_date(rsChargeSummaryComponentsList.getString("COMPONENT_START_DATE"));
			dto.setComponent_end_logic(rsChargeSummaryComponentsList.getString("COMPONENT_END_LOGIC"));
			dto.setComponent_end_days(rsChargeSummaryComponentsList.getString("END_DAYS"));
			dto.setComponent_end_month(rsChargeSummaryComponentsList.getString("END_MONTHS"));
			dto.setComponent_end_date(rsChargeSummaryComponentsList.getString("COMPONENT_END_DATE"));
			dto.setStart_token_no(rsChargeSummaryComponentsList.getString("START_TOKEN_NO"));
			dto.setStart_fx_status(rsChargeSummaryComponentsList.getString("START_FX_STATUS"));
			dto.setStart_fx_no(rsChargeSummaryComponentsList.getString("FX_START_NO"));
			dto.setEnd_token_no(rsChargeSummaryComponentsList.getString("END_TOKEN_NO"));
			dto.setEnd_fx_status(rsChargeSummaryComponentsList.getString("END_FX_STATUS"));
			dto.setEnd_fx_no(rsChargeSummaryComponentsList.getString("FX_END_NO"));
			dto.setFx_start_status(rsChargeSummaryComponentsList.getString("FX_START_STATUS"));
			dto.setFx_end_status(rsChargeSummaryComponentsList.getString("FX_END_STATUS"));
			dto.setComponentinfoid(rsChargeSummaryComponentsList.getString("COMPONENTINFOID"));
			dto.setCominstid(rsChargeSummaryComponentsList.getString("COMPONENT_INST_ID"));
			dto.setCom_dis_in_cu_(rsChargeSummaryComponentsList.getString("COMPONENT_DISCONNECTED_IN_CURRENT_ORDER"));
			dto.setIs_line_dis(rsChargeSummaryComponentsList.getString("IS_LINE_DISCONNECTED"));
			dto.setDis_type(rsChargeSummaryComponentsList.getString("DISCONNECTION_TYPE"));
			dto.setBilling_trigger_status(rsChargeSummaryComponentsList.getString("BILLING_TRIGGER_STATUS"));
			dto.setPackageInstId(rsChargeSummaryComponentsList.getString("PACKAGE_INST_ID"));
			dto.setPackageInstIdServ(rsChargeSummaryComponentsList.getString("PACKAGE_INST_ID_SERV"));
		
			components.add(dto);
		}
	}
	catch(Exception ex)
	{
		throw Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);
	}
	finally
	{
		try {
			DbConnection.freeConnection(conn);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	return components;
}
	
	
	// 079 end
	
	
	
/*	public ServiceDTO fetchNextServiceNChargesData(Connection conn,Long serviceProductId) throws IOESException{
		ServiceDTO serDto = null;
		
		
		try
		{
				
				CallableStatement cstmt_Service=conn.prepareCall(sqlSP_FX_GET_SERVICEDATA);
				cstmt_Service.setLong(1, serviceProductId);
				ResultSet rs=cstmt_Service.executeQuery();
				//Date serviceActiveDate;
				if(rs.next())
				{
					
					serDto=new ServiceDTO();
					//serDto.setAcctInternalId((rs.getInt("acctInternalId")));
					//serDto.setAcctExternalId(rs.getString("acctExternalId"));
					//serDto.setServExtId(rs.getString("servExtId"));
					
					//serDto.setPrename((rs.getString("prename")));
					//serDto.setAddress1((rs.getString("address1")));
					//serDto.setAddress2((rs.getString("address2")));
					//serDto.setAddress3((rs.getString("address3")));
					//serDto.setBillcity((rs.getString("billcity")));
					//serDto.setBillcompany((rs.getString("billcompany")));
					
					//String val=rs.getString("billcountrycode");
					//serDto.setBillcountrycode(val==null?0:Integer.parseInt(val));
					
					//serDto.setBillPeriod((rs.getString("billPeriod")));
					//serDto.setBillstate((rs.getString("billstate")));
					//serDto.setBillzip((rs.getString("billzip")));
					//serDto.setCustfaxno((rs.getString("custfaxno")));
					//serDto.setFName((rs.getString("fName")));
					//serDto.setLName((rs.getString("lName")));
					//serDto.setRateClassDefault((rs.getInt("rateClassDefault")));
					//serDto.setMobNo((rs.getString("mobNo")));
					
					serDto.setServiceProductId(rs.getLong("SERVICEPRODUCTID"));
					
					
					serDto.setEmfConfigId((rs.getString("EMFCONFIGID")==null)?null:rs.getInt("EMFCONFIGID"));
					serDto.setServiceActiveDate(rs.getDate("SERVICEACTIVEDT"));
					//serDto.setServiceExternalIdType((rs.getString("EXTERNALIDTYPE")==null)?null:rs.getInt("EXTERNALIDTYPE"));
					//serDto.setServExtId(rs.getString("SERVEXTID"));
					serDto.setPrivacyLevel((rs.getString("PRIVACYLEVEL")==null)?null:rs.getInt("PRIVACYLEVEL"));
					serDto.setAcctExternalId(rs.getString("ACCTEXTERNALID"));
					serDto.setRevRcvCostCtr((rs.getString("REVRCVCOSTCTR")==null)?null:rs.getInt("REVRCVCOSTCTR"));
					
					serDto.setA_serviceCompany(rs.getString("SERVICE_COMPANY"));
					serDto.setA_serviceFname(rs.getString("SERVICE_FNAME"));
					serDto.setA_serviceMname(rs.getString("SERVICE_MINIT"));
					serDto.setA_serviceLname(rs.getString("SERVICE_LNAME"));
					serDto.setA_serviceAddress1(rs.getString("SERVICE_ADDRESS1"));
					serDto.setA_serviceAddress2(rs.getString("SERVICE_ADDRESS2"));
					serDto.setA_serviceAddress3(rs.getString("SERVICE_ADDRESS3"));
					serDto.setA_serviceCity(rs.getString("SERVICE_CITY"));
					serDto.setA_serviceState(rs.getString("SERVICE_STATE"));
					serDto.setA_serviceCountryCode((rs.getString("SERVICE_COUNTRY_CODE")==null)?null:rs.getShort("SERVICE_COUNTRY_CODE"));
					serDto.setA_serviceZip(rs.getString("SERVICE_ZIP"));
					serDto.setB_serviceCompany(rs.getString("B_SERVICE_COMPANY"));
					serDto.setB_serviceFname(rs.getString("B_SERVICE_FNAME"));
					serDto.setB_serviceMname(rs.getString("B_SERVICE_MINIT"));
					serDto.setB_serviceLname(rs.getString("B_SERVICE_LNAME"));
					serDto.setB_serviceAddress1(rs.getString("B_SERVICE_ADDRESS1"));
					serDto.setB_serviceAddress2(rs.getString("B_SERVICE_ADDRESS2"));
					serDto.setB_serviceAddress3(rs.getString("B_SERVICE_ADDRESS3"));
					serDto.setB_serviceCity(rs.getString("B_SERVICE_CITY"));
					serDto.setB_serviceCountryCode((rs.getString("B_SERVICE_COUNTRY_CODE")==null)?null:rs.getShort("B_SERVICE_COUNTRY_CODE"));
					serDto.setB_serviceZip(rs.getString("B_SERVICE_ZIP"));

					
					
					long id=rs.getLong("ID");
					serDto.setId(id);
					rs.close();
					
					//Fetching ExternalIds for service
					ArrayList<FxExternalIdDto> externalIds = Utility.getFxExternalIds(conn,id);
					serDto.setExternalIds(externalIds);
					
					
					ArrayList<ExtendedData> extendedData = Utility.getFxExtendedData(conn,id,ExtendedData.AssociatedWith_SERVICE);
					serDto.setExtendedData(extendedData);
					
					//fetch RC charges info for the above fetched service :START
					String sql_Charges=sqlGetFX_RC_Charges;
					Connection conn1 =DbConnection.getConnectionObject();
					CallableStatement cstmt=conn1.prepareCall(sql_Charges);
					cstmt.setLong(1, id);
					ResultSet rs_rc_charges=cstmt.executeQuery();
					ArrayList<RcDto> rcs=new ArrayList<RcDto>();
					
					RcDto rcDto=null;
					
					while(rs_rc_charges.next())
					{
						rcDto = new RcDto();
						
						rcDto.setRowId(rs_rc_charges.getLong("ID"));
						rcDto.setElementId((rs_rc_charges.getString("ELEMENTID")==null)?null:rs_rc_charges.getInt("ELEMENTID"));
						rcDto.setOpenItemId((rs_rc_charges.getString("OPENITEMID")==null)?null:rs_rc_charges.getInt("OPENITEMID"));
						//rcDto.setProductActiveDate(rs_rc_charges.getDate("PRODUCTACTIVEDT"));
						rcDto.setBillingActiveDate(rs_rc_charges.getDate("BILLINGACTIVEDT"));
						rcDto.setInArrearsOverride((rs_rc_charges.getString("INARREARSOVERRIDE")==null)?null:rs_rc_charges.getInt("INARREARSOVERRIDE"));
						rcDto.setBillPeriod(rs_rc_charges.getString("BILLPERIOD"));
						//rcDto.setOrderNumber(rs_rc_charges.getString("ORDERNO"));
						rcDto.setOverrideRate(rs_rc_charges.getString("OVERRIDE_RATE"));
						rcDto.setBillingEndDate(rs_rc_charges.getDate("BILLING_END_DATE"));
						
						extendedData = Utility.getFxExtendedData(conn,rcDto.getRowId(),ExtendedData.AssociatedWith_PRODUCT);
						rcDto.setExtendedData(extendedData);
						
						rcs.add(rcDto);
					}
					
					rs_rc_charges.close();
					cstmt.close();
					serDto.setRcs(rcs);
					//fetch RC charges info  :END
					
					//fetch NRC charges info for the above fetched service  :START
					String sql_NRC_Charges=sqlGetFX_NRC_Charges;
					CallableStatement cstmt_NRC=conn1.prepareCall(sql_NRC_Charges);
					cstmt_NRC.setLong(1, id);
					ResultSet rs_nrc_charges=cstmt_NRC.executeQuery();
					ArrayList<NrcDto> nrcs=new ArrayList<NrcDto>();
					
					NrcDto nrcDto=null;
					
					while(rs_nrc_charges.next())
					{
						nrcDto = new NrcDto();
						
						nrcDto.setRowId(rs_nrc_charges.getLong("ID"));
						nrcDto.setTypeidNrc((rs_nrc_charges.getString("TYPEIDNRC")==null)?null:rs_nrc_charges.getInt("TYPEIDNRC"));
						//nrcDto.setCurrencyCode(rs_nrc_charges.getInt("CURRENCYCODE"));
						nrcDto.setEffectiveDate(rs_nrc_charges.getDate("EFFECTIVE_DATE"));
						//nrcDto.setAnnotation(rs_nrc_charges.getString("ANNOTATION"));
						nrcDto.setNrcAmount(rs_nrc_charges.getString("NRC_AMOUNT"));
						
						extendedData = Utility.getFxExtendedData(conn,nrcDto.getRowId(),ExtendedData.AssociatedWith_NRC);
						nrcDto.setExtendedData(extendedData);
						
						nrcs.add(nrcDto);
					}
					cstmt_NRC.close();
					rs_nrc_charges.close();
					serDto.setNrcs(nrcs);
					
					//conn1.close();
					DbConnection.freeConnection(conn1);
					//fetch NRC charges info  :END
					
					String sqlUpdate="Update IOE.TFX_ServiceCreate set lastUpdatedDate=CURRENT TIMESTAMP where ID="+id;
					conn.createStatement().executeUpdate(sqlUpdate);
					
				}
				else
				{
					serDto=null;	
				}
			
			
		}catch (Exception ex) {
			throw Utility.onEx_LOG_RET_NEW_EX(ex, "fetchNextServiceData", "CreateService", null, true, true);
		}
		
		
		return serDto;
	}
*/	public ChargesDto fetchNextAccountLevelChargesData(Connection conn,Long serviceProductId) throws IOESException{
	//Nagarjuna
	String methodName="fetchNextAccountLevelChargesData", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;	
	//end
	ChargesDto chargesDto = new ChargesDto();
		CallableStatement cstmt_NRC=null;
		ResultSet rs_nrc_charges=null;
		
		try
		{
			
			//get external account id of the charges
			CallableStatement cstmt=conn.prepareCall(sqlGetFX_Account_For_CHARGES);
			cstmt.setLong(1, serviceProductId);
			String accountExId=null;
			ResultSet rs=cstmt.executeQuery();
			if(rs.next())
			{
				accountExId=rs.getString("FX_ACCOUNT_EXTERNAL_ID");
			}
			else
			{
				throw new IOESException("No FX_ACCOUNT_EXTERNAL_ID found for spid:"+serviceProductId);
			}
			/*String accountExId=rs.getString("FX_ACCOUNT_EXTERNAL_ID");*/
			cstmt.close();
			chargesDto.setAccountExternalId(accountExId);
			chargesDto.setServiceProductId(serviceProductId);
			
			//*************End*************************/
			ArrayList<ExtendedData> extendedData = null;
			
			chargesDto.setServiceProductId(serviceProductId);
					//fetch RC charges info for the serviceProductId :START
					cstmt=conn.prepareCall(sqlGetFX_Account_Level_RC_Create);
					cstmt.setLong(1, serviceProductId);
					ResultSet rs_rc_charges=cstmt.executeQuery();
					ArrayList<RcDto> rcs=new ArrayList<RcDto>();
					
					RcDto rcDto=null;
					
					while(rs_rc_charges.next())
					{
						rcDto = new RcDto();
						
						rcDto.setRowId(rs_rc_charges.getLong("ID"));
						rcDto.setElementId((rs_rc_charges.getString("ELEMENTID")==null)?null:rs_rc_charges.getInt("ELEMENTID"));
						rcDto.setOpenItemId((rs_rc_charges.getString("OPENITEMID")==null)?null:rs_rc_charges.getInt("OPENITEMID"));
						//rcDto.setProductActiveDate(rs_rc_charges.getDate("PRODUCTACTIVEDT"));
						rcDto.setBillingActiveDate(rs_rc_charges.getDate("BILLINGACTIVEDT"));
						rcDto.setInArrearsOverride((rs_rc_charges.getString("INARREARSOVERRIDE")==null)?null:rs_rc_charges.getInt("INARREARSOVERRIDE"));
						rcDto.setBillPeriod(rs_rc_charges.getString("BILLPERIOD"));
						//rcDto.setOrderNumber(rs_rc_charges.getString("ORDERNO"));
						rcDto.setOverrideRate(rs_rc_charges.getString("OVERRIDE_RATE"));
						rcDto.setBillingEndDate(rs_rc_charges.getDate("BILLING_END_DATE"));
						
						extendedData = Utility.getFxExtendedData(conn,rcDto.getRowId(),ExtendedData.AssociatedWith_PRODUCT);
						rcDto.setExtendedData(extendedData);
						
						rcs.add(rcDto);
					}
					
					rs_rc_charges.close();
					cstmt.close();
					chargesDto.setRcs(rcs);
					//fetch RC charges info  :END
					
					//fetch NRC charges info for the serviceProductId  :START
					cstmt_NRC=conn.prepareCall(sqlGetFX_Account_Level_NRC_Create);
					cstmt_NRC.setLong(1, serviceProductId);
					rs_nrc_charges=cstmt_NRC.executeQuery();
					ArrayList<NrcDto> nrcs=new ArrayList<NrcDto>();
					
					NrcDto nrcDto=null;
					
					while(rs_nrc_charges.next())
					{
						nrcDto = new NrcDto();
						
						nrcDto.setRowId(rs_nrc_charges.getLong("ID"));
						nrcDto.setTypeidNrc((rs_nrc_charges.getString("TYPEIDNRC")==null)?null:rs_nrc_charges.getInt("TYPEIDNRC"));
						//nrcDto.setCurrencyCode(rs_nrc_charges.getInt("CURRENCYCODE"));
						nrcDto.setEffectiveDate(rs_nrc_charges.getDate("EFFECTIVE_DATE"));
						//nrcDto.setAnnotation(rs_nrc_charges.getString("ANNOTATION"));
						nrcDto.setNrcAmount(rs_nrc_charges.getString("NRC_AMOUNT"));
						
						extendedData = Utility.getFxExtendedData(conn,nrcDto.getRowId(),ExtendedData.AssociatedWith_NRC);
						nrcDto.setExtendedData(extendedData);
						
						nrcs.add(nrcDto);
					}
					cstmt_NRC.close();
					chargesDto.setNrcs(nrcs);
					
					//fetch NRC charges info  :END
					
			
		}catch (Exception ex) {
			throw Utility.onEx_LOG_RET_NEW_EX(ex, "fetchNextAccountLevelChargesData", this.getClass().getName(), null, true, true);
		}
		finally
		{
			try
			{
			DbConnection.closeResultset(rs_nrc_charges);
			DbConnection.closeCallableStatement(cstmt_NRC);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		
		
		return chargesDto;
	}
	public void sendToFx(ServiceDTO serDto) throws Exception{
		
		
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
			Utility.onEx_LOG_RET_NEW_EX(ex, "sendToFx", this.getClass().getName(), null, true, true);
		}
		
	}
	
	public void setFxOperationStatusForServiceLevelCharges(Connection conn,ServiceDTO serDto, int status) throws Exception {
		
		try
		{
			conn.setAutoCommit(false);
				//for failure
				//1.status to 2
				
				//for success
				//1.update service internal id and reset 
				//2.for its rcs , update its trackingid and serv
				//3.for its nrcs,update their viewId
				
			
				
				//1.for success update service internal id and reset
				//for failure they will be null
				
				
				if(status==1)
				{
					/*String sqlUpdate="Update IOE.TFX_ServiceCreate " +
					" set processingStatus="+status+" " +
					" , subscrNo='"+serDto.getSubscrNo() +"' " +
					" , lastUpdatedDate=CURRENT TIMESTAMP" +
					" , subscrNoReset='"+serDto.getSubscrNoReset()+"'" +
					" where ID="+serDto.getId();
					conn.createStatement().executeUpdate(sqlUpdate);*/
					
					CallableStatement cstmtService=conn.prepareCall(sqlUpdateFxServiceOnSuccess);
					cstmtService.setLong(1, serDto.getId());
					cstmtService.setInt(2, AppConstants.Billing_Trigger_Status_FX_SUCCESS);
					cstmtService.setInt(3, TFX_SERVICE_CREATE_PS_SUCCESS);
					cstmtService.setLong(4, serDto.getServiceProductId());
					cstmtService.setInt(5, serDto.getSubscrNo());
					cstmtService.setInt(6, serDto.getSubscrNoReset());
					
					cstmtService.execute();
					
					//2.for its rcs , update its trackingid and serv
					ArrayList<RcDto> rcs=serDto.getRcs();
					if(rcs!=null && rcs.size()>0)
					{
						CallableStatement cstmt=conn.prepareCall(sqlUpdateRcCreatoinResponse);
						for (RcDto rc : rcs) {
							cstmt.setLong(1, rc.getRowId());
							cstmt.setString(2, rc.getViewId());
							//cstmt.setInt(3, rc.getTrackingIdServ());
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
				else
				{
					
					CallableStatement cstmt=conn.prepareCall(sqlUpdateFxServiceOnfailure);
					cstmt.setLong(1, serDto.getId());
					cstmt.setInt(2, AppConstants.Billing_Trigger_Status_FX_FAILURE_API);
					cstmt.setInt(3, TFX_SERVICE_CREATE_PS_FAILURE);
					cstmt.setLong(4, serDto.getServiceProductId());
					cstmt.execute();
				}
			
				conn.commit();
		}catch (Exception ex) {
			conn.rollback();
			throw Utility.onEx_LOG_RET_NEW_EX(ex, "setFxOperationStatus", "ViewOrderDao", null, true, true);
		}
		
	}
	
	public void setFxOperationStatusForAccountLevelCharges(Connection conn,ChargesDto chargesDto, int status) throws Exception {
		
		try
		{
				conn.setAutoCommit(false);
				
				if(status==1)
				{
					CallableStatement cstmtService=conn.prepareCall(sqlUpdateFxAccLevelChargesOnSuccessFailure);
					cstmtService.setLong(1, chargesDto.getServiceProductId());
					cstmtService.setInt(2, AppConstants.Billing_Trigger_Status_FX_SUCCESS);
					
					cstmtService.execute();
					
					//2.for its rcs , update its trackingid and serv
					ArrayList<RcDto> rcs=chargesDto.getRcs();
					if(rcs!=null && rcs.size()>0)
					{
						CallableStatement cstmt=conn.prepareCall(sqlUpdateRcCreatoinResponse);
						for (RcDto rc : rcs) {
							cstmt.setLong(1, rc.getRowId());
							cstmt.setString(2, rc.getViewId());
							//cstmt.setInt(3, rc.getTrackingIdServ());
							cstmt.execute();
						}
					}
					
					//3.for its nrcs,update their viewId
					ArrayList<NrcDto> nrcs=chargesDto.getNrcs();
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
				else
				{
					CallableStatement cstmtService=conn.prepareCall(sqlUpdateFxAccLevelChargesOnSuccessFailure);
					cstmtService.setLong(1, chargesDto.getServiceProductId());
					cstmtService.setInt(2, AppConstants.Billing_Trigger_Status_FX_FAILURE_API);
					cstmtService.execute();
					
				}
			
				conn.commit();
		}catch (Exception ex) {
			conn.rollback();
			throw Utility.onEx_LOG_RET_NEW_EX(ex, "setFxOperationStatusForAccountLevelCharges", "ViewOrderDao", null, true, true);
		}
		
	}
	
	/*public ArrayList<ViewOrderDto> getFxAccCreationOperationStatus(Connection conn, Long orderNo) throws Exception {
		ArrayList<ViewOrderDto> fxAccCreationStatus = null;
		String methodName="getFxAccCreationOperationStatus", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;
		logger.info(methodName+" method of "+className+" class have been called");
		CallableStatement cstmt = null ;
		try
		{
			fxAccCreationStatus = new ArrayList<ViewOrderDto>();
			
			conn = DbConnection.getConnectionObject();*/
			/*cstmt = conn.prepareCall(sqlGet_FX_Account_Creation_Status);
			
			cstmt.setLong(1,orderNo);
			ResultSet rs=cstmt.executeQuery();
			ViewOrderDto dto=null;
			
			while (rs.next()) 
			{
				dto=new ViewOrderDto();
				
				//dto.setServiceId(rs.getString("SERVICEID"));
				//dto.setCustLogicalSINo(rs.getString("CUSTLOGICALSINO"));
				//dto.setServiceProductID(rs.getString("ServiceProductID"));
				
				fxAccCreationStatus.add(dto);
			}*/
		/*}
		catch(Exception ex)
		{
			throw Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);
		}
		return fxAccCreationStatus;
	}*/
	
	public ArrayList chargeBilling(String arr[]) throws Exception{
		//Nagarjuna
		String methodName="chargeBilling", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;	
		//end
		ArrayList<ViewOrderDto> list = new ArrayList<ViewOrderDto>();
		Connection conn = null;
		ResultSet rs= null;
		CallableStatement cs =null;
		String str="";
		
		logger.info("View Order Interface and getTaskListOfOrder method of ViewOrderDao class have been called");
		
		try {
			
			
			conn = DbConnection.getConnectionObject();
			cs = conn.prepareCall(sqlGet_BillinmgTriggerResult);
			for(int i=0;i<arr.length;i++)
			{
				str=str+","+arr[i];
			}
			
			
			Utility.SysOut(str);
			if(str.length()>0)
			{
				str=str.substring(1);
			}
			Utility.SysOut(str);
			cs.setString(1,str);
			System.out.println(cs);
			rs=cs.executeQuery();
			
			while (rs.next()) 
			{
				ViewOrderDto objViewOrderDto= new ViewOrderDto();
				objViewOrderDto.setServiceProductID(rs.getString("SERVICEPRODUCTID")); 
				objViewOrderDto.setBillingTriggerStatus(rs.getString("BILLING_TRIGGER_STATUS"));
			     list.add(objViewOrderDto);
			}
			
			
			
		
		} catch (Exception e) {
			throw Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);
			//e.printStackTrace();
			//throw new Exception("No Record Found");
		} finally {
			try {
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(cs);
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				//e.printStackTrace();
				//throw new Exception("No Record Found");
				throw Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);
			}
		}
		return list;
	}
public int getINTERNALID(long OrderNo,Connection conn ) throws Exception{
	
	//Nagarjuna
	String methodName="getINTERNALID", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;	
	//end
	ResultSet rs= null;
	CallableStatement cs =null;
	int count = 0;
	logger.info("getINTERNALID method of ViewOrderDao class have been called");
	
	try {
		
		
		
		cs = conn.prepareCall(sqlGet_INTERNAL_ID);
             cs.setLong(1,OrderNo);
		rs=cs.executeQuery();
		
		if (rs.next()) 
		{
			
		count=	rs.getInt("countpending"); 
			
		     
		}
		
		
		
	
	} catch (Exception e) {
		throw Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		//e.printStackTrace();
		//throw new Exception("No Record Found");
	} 
	finally
	{
		DbConnection.closeResultset(rs);
		DbConnection.closeCallableStatement(cs);
	}
	return count;
}

	//method is used for populate region list from table
	//created by anil
	public ArrayList<NewOrderDto> getRegionList() throws Exception 
	{
		//Nagarjuna
		String methodName="getRegionList", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;	
		//end
		Connection connection =null;
		CallableStatement getRegionDetails =null;
		ResultSet rsRegionDetails = null;
		ArrayList<NewOrderDto> regionList = new ArrayList<NewOrderDto>();
		NewOrderDto objNewOrderDto = null;
		
		try
		{
			connection=DbConnection.getConnectionObject();
			getRegionDetails= connection.prepareCall(sqlGetRegionList);
			rsRegionDetails = getRegionDetails.executeQuery();
			while(rsRegionDetails.next())
			{
				objNewOrderDto =  new NewOrderDto();
				objNewOrderDto.setRegionId(rsRegionDetails.getInt("regionID"));
				objNewOrderDto.setRegionName(rsRegionDetails.getString("regionName"));
				
				regionList.add(objNewOrderDto);
			}
			return regionList;
		}
		catch(Exception ex )
		{
			 Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			//ex.printStackTrace();	
		}
		finally
		{
			try 
			{
				DbConnection.closeResultset(rsRegionDetails);
				DbConnection.closeCallableStatement(getRegionDetails);
				DbConnection.freeConnection(connection);
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
			//	e.printStackTrace();
				 Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			}
		}
		return regionList;
	}	
	//method is used for insert update main through view order page
	//created by anil
	public long updateMainViewOrder(ViewOrderFormBean viewOrderBean,String[] attributeVal,String[] attributeID)
	{
		//Nagarjuna
		String methodName="updateMainViewOrder", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;	
		//end
		long finalStatus=0;
		Connection connection=null;
		CallableStatement setMainDetails=null;
		int MainStatus=0;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(AppConstants.DATE_FORMAT_PROC);
		try
		{
			connection=DbConnection.getConnectionObject();
			connection.setAutoCommit(false);
			
			//updating main details attributes and region,zone through view order page
			for(int i=0;i<viewOrderBean.getAttCount();i++)
			{
				setMainDetails=connection.prepareCall(sqlUpdateMainViewOrder);
				setMainDetails.setInt(1, Integer.valueOf(attributeID[i]));//AttributeID
				setMainDetails.setInt(2,Integer.valueOf(viewOrderBean.getRegionId()));//Region Id
				setMainDetails.setInt(3,Integer.valueOf(viewOrderBean.getZoneId()));// Zone Id
				setMainDetails.setInt(4, Integer.valueOf(viewOrderBean.getPoNumber()));//Order No
				setMainDetails.setInt(5,Integer.valueOf(viewOrderBean.getAccountID()));//Account no
				setMainDetails.setInt(6, 1);//Attribute For
				setMainDetails.setString(7, attributeVal[i]);//Attribute Value
				//setMainDetails.setInt(5, updateType);//Update Type
				setMainDetails.setInt(8,0);//Output Parameter
				setMainDetails.setInt(9,0);//Output Parameter
				setMainDetails.setString(10,"");//Output Parameter												
				setMainDetails.execute();
				MainStatus=setMainDetails.getInt(8);
				if(MainStatus==1)
				{
					break;
				}
			}
			if(MainStatus==0)
			{
				connection.commit();
			}
		}
		catch(Exception ex)
		{
			 Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna	
		}
		finally
		{
			try {
				DbConnection.closeCallableStatement(setMainDetails);
				DbConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				 Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			}
		}
		return finalStatus;
	}


//Function For Charge Details  Neworder
/*public ArrayList<ViewOrderDto> getChargeDetailsForNew(long serviceProductID ,Connection conn ) throws Exception
{
	ArrayList<ViewOrderDto> list = new ArrayList<ViewOrderDto>();
	ResultSet rs= null;
	CallableStatement cs =null;
	
logger.info("getChargeDetailsinFx method of ViewOrderDao class have been called");
	
	try {
		
		
		
		cs = conn.prepareCall(sqlGet_chargeDeatilsForNew);
             cs.setLong(1,serviceProductID);
		rs=cs.executeQuery();
		
	while(rs.next()){
		
		
		ViewOrderDto objViewOrderDto= new ViewOrderDto();
		
		objViewOrderDto.setChargeType(rs.getString("CHARGETYPE"));
		objViewOrderDto.setChargeName(rs.getString("CHARGENAME")); 
		objViewOrderDto.setChargePeriod(rs.getInt("CHARGEPERIOD")); 
		objViewOrderDto.setChargeAmt(rs.getLong("CHARGEAMOUNT")); 
		objViewOrderDto.setChargefrequency(Utility.fnCheckNull(rs.getString("CHARGEFREQUENCY")));
		objViewOrderDto.setChargefrequencyamount(rs.getDouble("CHARGEFREQUENCYAMT")); 
		objViewOrderDto.setStartdatelogic(Utility.fnCheckNull(rs.getString("STARTDATELOGIC_STRING"))); 
		objViewOrderDto.setEnddatelogic(Utility.fnCheckNull(rs.getString("ENDDATELOGIC_STRING"))); 
		objViewOrderDto.setStart_date_days(rs.getInt("START_DATE_DAYS")); 
		objViewOrderDto.setEnd_date_days(rs.getInt("END_DATE_DAYS")); 
		objViewOrderDto.setStart_date_month(rs.getInt("START_DATE_MONTH")); 
		objViewOrderDto.setEnd_date_month(rs.getInt("END_DATE_MONTH")); 
		objViewOrderDto.setChargeStatus(Utility.fnCheckNull(rs.getString("CHARGESTATUS")));
		objViewOrderDto.setFxStatus(Utility.fnCheckNull(rs.getString("FXSTATUS")));
		objViewOrderDto.setFxno(Utility.fnCheckNull(rs.getString("FX_NO")));
		objViewOrderDto.setCharge_createdon(Utility.fnCheckNull(rs.getString("CHARGE_CREATEDON")));
		objViewOrderDto.setCharge_endon(Utility.fnCheckNull(rs.getString("CHARGE_ENDEDON")));
		
		list.add(objViewOrderDto);
		
		
	}
		
		
		
		
	}
	
	
	catch (Exception e) {
		e.printStackTrace();
		throw new Exception("No Record Found");
	} 
	
	
	
	return list;
	
	




	}*/
//Function to Get Charge Details For Disconnect
/*public ArrayList<ViewOrderDto> getChargeDetailsForDiscoonect(long serviceProductID ,Connection conn ) throws Exception
{
	ArrayList<ViewOrderDto> list = new ArrayList<ViewOrderDto>();
	ResultSet rs= null;
	CallableStatement cs =null;
	
logger.info("getChargeDetailsinFx method of ViewOrderDao class have been called");
	
	try {
		
		
		
		cs = conn.prepareCall(sqlGet_chargeDeatilsForDisconnect);
             cs.setLong(1,serviceProductID);
		rs=cs.executeQuery();
		
	while(rs.next()){
		
		
		ViewOrderDto objViewOrderDto= new ViewOrderDto();
		
		objViewOrderDto.setChargeType(rs.getString("CHARGETYPE"));
		objViewOrderDto.setChargeName(rs.getString("CHARGENAME")); 
		objViewOrderDto.setChargePeriod(rs.getInt("CHARGEPERIOD")); 
		objViewOrderDto.setChargeAmt(rs.getLong("CHARGEAMOUNT")); 
		objViewOrderDto.setChargefrequency(Utility.fnCheckNull(rs.getString("CHARGEFREQUENCY")));
		objViewOrderDto.setChargefrequencyamount(rs.getDouble("CHARGEFREQUENCYAMT")); 
		objViewOrderDto.setStartdatelogic(Utility.fnCheckNull(rs.getString("STARTDATELOGIC_STRING"))); 
		objViewOrderDto.setEnddatelogic(Utility.fnCheckNull(rs.getString("ENDDATELOGIC_STRING"))); 
		objViewOrderDto.setStart_date_days(rs.getInt("START_DATE_DAYS")); 
		objViewOrderDto.setEnd_date_days(rs.getInt("END_DATE_DAYS")); 
		objViewOrderDto.setStart_date_month(rs.getInt("START_DATE_MONTH")); 
		objViewOrderDto.setEnd_date_month(rs.getInt("END_DATE_MONTH")); 
		objViewOrderDto.setChargeStatus(Utility.fnCheckNull(rs.getString("CHARGESTATUS")));
		objViewOrderDto.setFxStatus(Utility.fnCheckNull(rs.getString("FXSTATUS")));
		objViewOrderDto.setFxno(Utility.fnCheckNull(rs.getString("FX_NO")));
		objViewOrderDto.setCharge_createdon(Utility.fnCheckNull(rs.getString("CHARGE_CREATEDON")));
		objViewOrderDto.setCharge_endon(Utility.fnCheckNull(rs.getString("CHARGE_ENDEDON")));
		
		list.add(objViewOrderDto);
		
		
	}
		
		
		
		
	}
	
	
	catch (Exception e) {
		e.printStackTrace();
		throw new Exception("No Record Found");
	} 
	
	
	
	return list;
	
	




	}*/
//Function To Get Charge Details For Rate Renewal
/*public ArrayList<ViewOrderDto> getChargeDetailsForRateRenewal(long serviceProductID ,Connection conn ) throws Exception
{
	ArrayList<ViewOrderDto> list = new ArrayList<ViewOrderDto>();
	ResultSet rs= null;
	CallableStatement cs =null;
	
logger.info("getChargeDetailsinFx method of ViewOrderDao class have been called");
	
	try {
		
		
		
		cs = conn.prepareCall(sqlGet_chargeDeatilsForRateRenewal);
             cs.setLong(1,serviceProductID);
		rs=cs.executeQuery();
		
	while(rs.next()){
		
		
		ViewOrderDto objViewOrderDto= new ViewOrderDto();
		
		objViewOrderDto.setChargeType(rs.getString("CHARGETYPE"));
		objViewOrderDto.setChargeName(rs.getString("CHARGENAME")); 
		objViewOrderDto.setChargePeriod(rs.getInt("CHARGEPERIOD")); 
		objViewOrderDto.setChargeAmt(rs.getLong("CHARGEAMOUNT")); 
		objViewOrderDto.setChargefrequency(Utility.fnCheckNull(rs.getString("CHARGEFREQUENCY")));
		objViewOrderDto.setChargefrequencyamount(rs.getDouble("CHARGEFREQUENCYAMT")); 
		objViewOrderDto.setStartdatelogic(Utility.fnCheckNull(rs.getString("STARTDATELOGIC_STRING"))); 
		objViewOrderDto.setEnddatelogic(Utility.fnCheckNull(rs.getString("ENDDATELOGIC_STRING"))); 
		objViewOrderDto.setStart_date_days(rs.getInt("START_DATE_DAYS")); 
		objViewOrderDto.setEnd_date_days(rs.getInt("END_DATE_DAYS")); 
		objViewOrderDto.setStart_date_month(rs.getInt("START_DATE_MONTH")); 
		objViewOrderDto.setEnd_date_month(rs.getInt("END_DATE_MONTH")); 
		objViewOrderDto.setChargeStatus(Utility.fnCheckNull(rs.getString("CHARGESTATUS")));
		objViewOrderDto.setFxStatus(Utility.fnCheckNull(rs.getString("FXSTATUS")));
		objViewOrderDto.setFxno(Utility.fnCheckNull(rs.getString("FX_NO")));
		objViewOrderDto.setCharge_createdon(Utility.fnCheckNull(rs.getString("CHARGE_CREATEDON")));
		objViewOrderDto.setCharge_endon(Utility.fnCheckNull(rs.getString("CHARGE_ENDEDON")));
		
		list.add(objViewOrderDto);
		
		
	}
		
		
		
		
	}
	
	
	catch (Exception e) {
		e.printStackTrace();
		throw new Exception("No Record Found");
	} 
	
	
	
	return list;
	
	




	}*/

//Charge Details For Solution Change
public ArrayList<ViewOrderDto> fnGetChargesAllOrders(long serviceProductID ,Connection conn, long serviceId ,int startIndex,int endIndex,int pagingRequired ,int pageSize) throws Exception
{
	//Nagarjuna
	String methodName="fnGetChargesAllOrders", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;	
	//end
	ArrayList<ViewOrderDto> list = new ArrayList<ViewOrderDto>();
	ResultSet rs= null;
	CallableStatement cs =null;
	int recordCount;
	
logger.info("fnGetChargesAllOrders method of ViewOrderDao class have been called");
	
	try {
		
		
		
		cs = conn.prepareCall(sqlGet_chargeDeatilsForAllOrders);
             cs.setLong(1,serviceProductID);
             cs.setLong(2, serviceId);
             cs.setInt(3, startIndex);
             cs.setInt(4, endIndex);
             cs.setInt(5, pagingRequired);
		rs=cs.executeQuery();
		
	while(rs.next()){
		
		
		ViewOrderDto objViewOrderDto= new ViewOrderDto();
		
		objViewOrderDto.getPagingSorting().setPageRecords(pageSize);
		objViewOrderDto.setChargeType(rs.getString("CHARGESTYPE"));
		objViewOrderDto.setChargeName(rs.getString("CHARGE_NAME")); 
		objViewOrderDto.setChargePeriod(rs.getInt("CHARGEPERIOD")); 
		objViewOrderDto.setChargeAmt(rs.getDouble("CHARGEAMOUNT")); 
		objViewOrderDto.setChargeStatus(rs.getString("CHARGESTATUS"));
		objViewOrderDto.setChargeCreatedOnOrder(rs.getString("START_ORDER_NO"));
		objViewOrderDto.setChargeEndedOnOrder(rs.getString("END_ORDER_NO"));
		
		objViewOrderDto.setIsLineDisconnected(rs.getString("IS_LINE_DISCONNECTED")); 
		
		
		objViewOrderDto.setDisconnectionType(rs.getString("DISCONNECTION_TYPE"));
        objViewOrderDto.setBillingTriggerStatus(rs.getString("BILLING_TRIGGER_STATUS"));
		objViewOrderDto.setBillPeriod(Utility.fnCheckNull(rs.getString("BILL_PERIOD")));
		objViewOrderDto.setChargefrequency(Utility.fnCheckNull(rs.getString("CHARGEFREQUENCY")));
		objViewOrderDto.setChargefrequencyamount(rs.getDouble("CHARGEFREQUENCYAMT")); 
		
		objViewOrderDto.setStartdatelogic(Utility.fnCheckNull(rs.getString("STARTDATELOGIC_STRING"))); 
		objViewOrderDto.setStart_date_days(rs.getInt("START_DATE_DAYS")); 
		objViewOrderDto.setStart_date_month(rs.getInt("START_DATE_MONTH")); 
		objViewOrderDto.setChargeStartDate_String(rs.getString("CHARGE_START_DATE"));
		
		
		objViewOrderDto.setEnddatelogic(Utility.fnCheckNull(rs.getString("ENDDATELOGIC_STRING"))); 
		objViewOrderDto.setEnd_date_days(rs.getInt("END_DATE_DAYS")); 
		objViewOrderDto.setEnd_date_month(rs.getInt("END_DATE_MONTH")); 
		objViewOrderDto.setChargeEndDate_String(rs.getString("CHARGE_INITIAL_END_DATE"));	
		
	    objViewOrderDto.setAnnualRate(rs.getString("ANNUAL_RATE"));
		objViewOrderDto.setAnnotation(rs.getString("ANNOTATION"));
		objViewOrderDto.setChargeId(rs.getString("CHARGE_ID"));
		objViewOrderDto.setFxViewId(rs.getString("FX_VIEW_ID"));
		
		objViewOrderDto.setChargeStartStatus(rs.getString("CHARGE_START_STATUS"));
	    objViewOrderDto.setStartTokenNo(rs.getString("START_TOKEN_NO"));
		objViewOrderDto.setStartFxStatus(rs.getString("START_FX_STATUS"));
		objViewOrderDto.setStartFxNo(rs.getString("START_FX_NO"));
		
		objViewOrderDto.setChargeEndStatus(rs.getString("CHARGE_END_STATUS"));
	    objViewOrderDto.setEndTokenNo(rs.getString("END_TOKEN_NO"));
		objViewOrderDto.setEndFxStatus(rs.getString("END_FX_STATUS"));
		objViewOrderDto.setEndFxNo(rs.getString("END_FX_NO"));
		recordCount=rs.getInt("FULL_REC_COUNT");
		objViewOrderDto.getPagingSorting().setRecordCount(recordCount);	
		objViewOrderDto.setMaxPageNo(objViewOrderDto.getPagingSorting().getMaxPageNumber());
		
		objViewOrderDto.setIsChargeDisconnectedInCurrentOrder(rs.getString("isChargeDisconnectedInCurrentOrder"));
		
		
		
		/*objViewOrderDto.setChargeStatus_otherLabel(Utility.fnCheckNull(rs.getString("CHARGESTATUS_other")));
		
		objViewOrderDto.setAnnotation(Utility.fnCheckNull(rs.getString("ANNOTATION")));*/
		
		/////////////////////////////////////////
		
		/*objViewOrderDto.setFxStatus(Utility.fnCheckNull(rs.getString("FXSTATUS")));
		objViewOrderDto.setFxno(Utility.fnCheckNull(rs.getString("FX_NO")));*/
		//objViewOrderDto.setCharge_createdon(Utility.fnCheckNull(rs.getString("CHARGE_CREATEDON")));
		//objViewOrderDto.setCharge_endon(Utility.fnCheckNull(rs.getString("CHARGE_ENDEDON")));
		
		list.add(objViewOrderDto);
		
		
	}
		
		
		
		
	}
	
	
	catch (Exception e) {
		 Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		//e.printStackTrace();
		//throw new Exception("No Record Found");
	} 
	
	
	
	return list;
	
	




	}




















	//add by anil for fetch Order no from TM6_NEWORDER_STATUS for check publish status
	public Long checkPublishStatus(int OrderNo) throws Exception
	{
		//Nagarjuna
		String methodName="checkPublishStatus", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;	
		//end
		Long TM6OrderNo=null;
		Connection conn = null;
		//ResultSet rsTM6OrderNo = null;
		CallableStatement csTM6OrderNo =null;
		try
		{
			conn=DbConnection.getConnectionObject();
			csTM6OrderNo=conn.prepareCall(sqlGetTM6OrderNo);
			csTM6OrderNo.setLong(1, OrderNo);
			csTM6OrderNo.setLong(2, 0);
			csTM6OrderNo.setInt(3, 0);	
			csTM6OrderNo.execute();
			//while(rsTM6OrderNo.next())
			//{
				//TM6OrderNo=rsTM6OrderNo.getLong("ORDERNO");
				TM6OrderNo=csTM6OrderNo.getLong(2);
			//}
			
			if(csTM6OrderNo.getInt(3)!=0)
			{
				TM6OrderNo=new Long(0);
			}
			
		}
		catch(Exception ex)
		{
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			//ex.printStackTrace();
			//throw new Exception("No Record Found");
		}
		finally
		{
			DbConnection.closeCallableStatement(csTM6OrderNo);
			DbConnection.freeConnection(conn);
		}
		return TM6OrderNo;
	}
	// ADDED BY SANDEEP FOR UPDATING FX STATUS IN CHANGE ORDER
public static boolean  updateFX_Status(int fx_status,int charge_info_id) throws Exception {
	//Nagarjuna
	String methodName="updateFX_Status", msg="";
	boolean logToFile=true, logToConsole=true;	
	//end
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		boolean status=false;
						
		//get the duration
	   // System.out.println("<-----Inside InsertConferenceDetailDao.updateConferenceStatus------->");
		StringBuffer sql = new StringBuffer("UPDATE IOE.TCHARGES_INFO set FX_STATUS=? ");
		
					    	sql.append("where CHARGEINFOID=?");
		
		
		try{
			con=DbConnection.getConnectionObject();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1,fx_status);
			pstmt.setInt(2,charge_info_id);
			
			result = pstmt.executeUpdate();
			con.commit();
			
			if(result>0)
				status=true;
			
		}
		catch (SQLException e) {
			e.getMessage();
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, "ViewOrderDao", msg, logToFile, logToConsole);//nagarjuna
		}finally {
			if(pstmt !=null)
			{
				DbConnection.closePreparedStatement(pstmt);
				pstmt = null;
			}
			
			try {
				DbConnection.freeConnection(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, "ViewOrderDao", msg, logToFile, logToConsole);//nagarjuna
			}
	}	
		return status;

	}

	public OrderHeaderDTO getOrderType(Connection conn, long orderNo) throws Exception{
		//Nagarjuna
		String methodName="getOrderType", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;	
		//end
		OrderHeaderDTO newOrderDto = new OrderHeaderDTO();
		CallableStatement cstmt=null;
		try
		{
		cstmt=conn.prepareCall(sqlGetOrderType);
		cstmt.setLong(1, orderNo);
		cstmt.setNull(2, java.sql.Types.VARCHAR);
		cstmt.setNull(3, java.sql.Types.INTEGER);
		cstmt.setNull(4, java.sql.Types.INTEGER);
		
		cstmt.execute();
		
		String orderType = cstmt.getString(2);
		int changeOrderType = cstmt.getInt(3);
		int changeOrderSubType = cstmt.getInt(4);
		
		newOrderDto.setOrderInfo_OrderType(orderType);
		newOrderDto.setOrderInfo_ChangeType(changeOrderType);
		newOrderDto.setOrderInfo_ChangeSubType(changeOrderSubType);
		}
		catch(Exception ex)
		{
		//	ex.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}
		finally
		{
			if(cstmt!=null)DbConnection.closeCallableStatement(cstmt);
		}
		
		return newOrderDto;
	}
	
	//added for change order
	
	
	
	
	
	

/*	public void updateStatusAndMoveChargesDataToSecondaryTables(Connection conn, long orderNo, String csvServiceProductIds) 
		throws Exception
	{
		 CallableStatement cstmt= conn.prepareCall(sqlFx_CHG_Disconnect_Request);
		 cstmt.setString(1, csvServiceProductIds);
		 cstmt.setLong(2, orderNo);
		 cstmt.execute();
	}*/
	public String pushChargesInFXForDisconnection(Connection conn,long longServiceProductId,String locNo,String locDate,String billingTriggerDate) throws Exception 
	{	
		//Nagarjuna
		String methodName="pushChargesInFXForDisconnection", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;	
		//end
		String status = "FAILED"; 
		CallableStatement cstmt=null;
		try {
			cstmt= conn.prepareCall(sqlGet_Push_Charges_In_FX_For_Disconnection);
			 cstmt.setString(1,longServiceProductId+"");
			 cstmt.setString(2,locDate );
			 cstmt.setString(3,locNo);
			 cstmt.setString(4,billingTriggerDate);
			 cstmt.registerOutParameter(5,java.sql.Types.NUMERIC); // out param
			 cstmt.registerOutParameter(6,java.sql.Types.VARCHAR); // out param
			 cstmt.registerOutParameter(7,java.sql.Types.VARCHAR); // out param
			 cstmt.execute();
			 if("0".equalsIgnoreCase(cstmt.getString(6)));
			 {
				 status = "SUCCESS";
			 }
		 }catch(Exception e)
		 {
			// e.printStackTrace();
			 Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		 }finally {
			 DbConnection.closeCallableStatement(cstmt);
		 }
	return status;
	}
	
	public NewOrderDto pushChargesInSecondaryTablesForChangeOrders(Connection conn,long longServiceProductId,String locNo,String locDate,String billingTriggerDate,String LocRecDate,long orderNo,String clepFlag,long userEmpId) throws Exception 
	{		
		//Nagarjuna
		String methodName="pushChargesInSecondaryTablesForChangeOrders", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;	
		//end
		String status=null;
		CallableStatement cstmt=null;
		NewOrderDto newOrderDto=new NewOrderDto();
		try {
			//DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			//java.util.Date date = df.parse(billingTriggerDate); 
	       //java.sql.Timestamp timest = new java.sql.Timestamp(date.getTime());
			cstmt= conn.prepareCall(sqlGet_Push_charges_In_SecondaryTables_For_Change_Orders);
			 cstmt.setString(1,longServiceProductId+"");
			 cstmt.setString(2,locDate );
			 cstmt.setString(3,locNo);
			 cstmt.setString(4,billingTriggerDate);
			 cstmt.setString(5, LocRecDate);
			 cstmt.setLong(6,orderNo);
			 cstmt.registerOutParameter(7,java.sql.Types.NUMERIC); // out param
			 cstmt.registerOutParameter(8,java.sql.Types.VARCHAR); // out param
			 cstmt.registerOutParameter(9,java.sql.Types.VARCHAR); // out param
			 //cstmt.setLong(10,orderNo);
			 cstmt.setNull(10,java.sql.Types.VARCHAR);
			 cstmt.setLong(11,userEmpId);
			 cstmt.execute();
			 if("0".equalsIgnoreCase(cstmt.getString(8)))
			 {
				 //Below code add by Anil for CLEP requirement
				 if("Y".equalsIgnoreCase(clepFlag)){
					 
				 }else{
					 conn.commit();
				 }
				 status="SUCCESS";
				 newOrderDto.setBillingTriggerStatus(status);
				 //end CLEP
			 }else if("-2".equalsIgnoreCase(cstmt.getString(8))){
				 conn.rollback();
				 status="-2";
				 newOrderDto.setBillingTriggerStatus(status);
			 }
			 else
			 {// print status codes
				 conn.rollback();
				 //changs here by Anil for CLEP
				 	String MSG="errors in proc : IOE.FX_PUSH_CHARGES_IN_SECONDARY_TABLES_FOR_CHANGE_ORDERS , msgcode="+cstmt.getString(8)+"  msg="+cstmt.getString(9)+" sqlcode:"+cstmt.getInt(7)+"  , longServiceProductId="+longServiceProductId;
				 	//throw new Exception("errors in proc : IOE.FX_PUSH_CHARGES_IN_SECONDARY_TABLES_FOR_CHANGE_ORDERS , msgcode="+cstmt.getString(8)+"  msg="+cstmt.getString(9)+" sqlcode:"+cstmt.getInt(7));
				 	Utility.LOG("Billing Trigger Exception Occured"+MSG);
				 	logExceptionForBillingTrigger(conn,MSG,longServiceProductId);
				 	conn.commit();
				 	newOrderDto.setBillingTriggerMsg(MSG);
				 //end CLEP
			 }
		 }catch(Exception e)
		 {
			 //Utility.LOG(true, true, e, "");
			// e.printStackTrace();
			 conn.rollback();
			 Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		 }
		 finally
		 {
			 DbConnection.closeCallableStatement(cstmt);
		 }
		 return newOrderDto;
	
	}
	
	public void logExceptionForBillingTrigger(Connection con,String msg,long longServiceProductId)
	{
		
		CallableStatement cstmt=null;
		try
		{
			cstmt=con.prepareCall(sqlLogException);
			java.sql.Clob clobData = 
					com.ibm.db2.jcc.t2zos.DB2LobFactory.createClob(Utility.fnCheckNull(msg));
			cstmt.setClob(1,clobData);
			cstmt.setLong(2,longServiceProductId);
			
			cstmt.execute();
		}catch (Exception ex) {
			Utility.LOG(true,false,ex,"Exception in exception logging for :"+msg);
		}
		finally
		{
			DbConnection.closeCallableStatement(cstmt);
		}
		
	}
	
	
	
	
	public void fnUpdateLocdetails(Connection conn,long longServiceProductId,String locNo,String locDate,String billingTriggerDate,String locRecDate,long orderNo,long btDoneBy) throws Exception 
	{	
		//Nagarjuna
		String methodName="fnUpdateLocdetails", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;	
		//end
		    CallableStatement cstmt= conn.prepareCall(sql_update_loc_details);
			 cstmt.setString(1,longServiceProductId+"");
			 cstmt.setString(2,locDate );
			 cstmt.setString(3,locNo);
			 cstmt.setString(4,billingTriggerDate);
			 cstmt.setString(5,locRecDate);
			 cstmt.setLong(6,orderNo);
			 cstmt.setLong(7,btDoneBy);
			 
			  cstmt.execute();
			 try
			 {
				 DbConnection.closeCallableStatement(cstmt);
			 }
			 catch(Exception ex)
			 {
				 //ex.printStackTrace();
				 Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			 }
			 finally
			 {
				 DbConnection.closeCallableStatement(cstmt);
			 }
			
			
	
	
	}
	
	
	
	
	// 005 start
public ViewOrderDto saveLocData(ArrayList arr,long btDoneBy, long orderNo)
	
	{
	//Nagarjuna
	String methodName="saveLocData", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;	
	//end
		Connection conn = null;
		CallableStatement csInsertLocData =null;
		 ViewOrderDto objdto=null;
		ArrayList successfullyInserted =new ArrayList();
		ArrayList Failure =new ArrayList();
		ViewOrderDto dto=new ViewOrderDto();
		logger.info("View Order Interface and getSelectServiceDetails method of ViewOrderDao class have been called");
		
		
		
			for(int i=0;i<arr.size();i++)
			{
				try {
					
					    conn = DbConnection.getConnectionObject();
					    conn.setAutoCommit(false);
					    arr.get(0).toString();
						objdto=(ViewOrderDto)arr.get(i);
						csInsertLocData= conn.prepareCall(sql_INSERT_LOC_DATA);
						
						csInsertLocData.setString(1,objdto.getServiceProductID());
						csInsertLocData.setString(2,objdto.getLocNo());
						csInsertLocData.setString(3,objdto.getLocDate());
						csInsertLocData.setString(4,objdto.getLocRecDate());
						csInsertLocData.setString(5,objdto.getLocNo_Status());
						csInsertLocData.setString(6,objdto.getLocDate_Status());
						csInsertLocData.setString(7,objdto.getLocRecDate_Status());
						//Start[082]
						csInsertLocData.setLong(8,btDoneBy);
						//End[082]
						csInsertLocData.setLong(9,orderNo);
						csInsertLocData.execute();
					    conn.commit();
					    successfullyInserted.add(objdto.getServiceProductID());
						
						
			     }
				
				catch(Exception ex )
				
					{
						try {
							Failure.add(objdto.getServiceProductID());
							conn.rollback();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							//e.printStackTrace();
							 Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
						}
						//ex.printStackTrace();
						 Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
					}

	
			finally
			{
				try 
				{
					DbConnection.closeCallableStatement(csInsertLocData);
					DbConnection.freeConnection(conn);
				} 
				catch (Exception e) 
				{
					// TODO Auto-generated catch block
					//e.printStackTrace();
					 Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
				}
			}
	
	}
			
			dto.setSuccessfullyLOCDataInserted(successfullyInserted);
			dto.setFailuetoInsertLocData(Failure);
		
			return dto;	
}	

public ViewOrderDto saveLocData_forBulkUpload(Connection con,ViewOrderDto objdto,long btDoneBy) throws Exception

{
	//Nagarjuna
	String methodName="saveLocData_forBulkUpload", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;	
	//end
	
	CallableStatement csInsertLocData =null;
	ArrayList successfullyInserted =new ArrayList();
	ArrayList Failure =new ArrayList();
	ViewOrderDto dto=new ViewOrderDto();
	String locdate=null;
	String locrecdate=null;
	DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
	logger.info("View Order Interface and getSelectServiceDetails method of ViewOrderDao class have been called");
	
			try {
				 	if(!(objdto.getLocDate()==null || "".equals(objdto.getLocDate())))
					{
				 		locdate=Utility.showDate_Report3(df.parse(objdto.getLocDate()));
					}
				 	
				 	if(!(objdto.getLocRecDate()==null || "".equals(objdto.getLocRecDate())))
					{
				 		locrecdate=Utility.showDate_Report3(df.parse(objdto.getLocRecDate()));
					}
				
					csInsertLocData= con.prepareCall(sql_INSERT_LOC_DATA);
					csInsertLocData.setString(1,objdto.getServiceProductID());
					csInsertLocData.setString(2,objdto.getLocNo());
					csInsertLocData.setString(3,locdate);
					csInsertLocData.setString(4,locrecdate);
					csInsertLocData.setString(5,objdto.getLocNo_Status());
					csInsertLocData.setString(6,objdto.getLocDate_Status());
					csInsertLocData.setString(7,objdto.getLocRecDate_Status());
//					Start[082]
					csInsertLocData.setLong(8,btDoneBy);
//					End[082]
					csInsertLocData.setLong(9,Long.parseLong(objdto.getOrderNo()));
					csInsertLocData.execute();
				    con.commit();
				    successfullyInserted.add(objdto.getServiceProductID());
			
		     }
			
			catch(Exception ex )
			
				{
				Utility.LOG_ITER(true, true, ex, "Billing Trigger Exception Occured while saving LOCdetails at LineItem No : "+objdto.getServiceProductID());
					try {
						Failure.add(objdto.getServiceProductID());
						con.rollback();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
						 Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
					}
					//ex.printStackTrace();
					 Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
					 //throw ex;
					
				}


		dto.setSuccessfullyLOCDataInserted(successfullyInserted);
		dto.setFailuetoInsertLocData(Failure);
	
		return dto;	
}	

public void fnUpdateLocdetails_AUTO(Connection conn,ArrayList arr,long btDoneBy, long orderNo) throws Exception

{
	
	//Nagarjuna
	String methodName="fnUpdateLocdetails_AUTO", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;	
	//end
	
	
	CallableStatement csInsertLocData =null;
	ViewOrderDto objdto=null;
	
	 
	logger.info("View Order Interface and getSelectServiceDetails method of ViewOrderDao class have been called");
	
	
	
		for(int i=0;i<arr.size();i++)
		{
			
				
			     
				   
				    arr.get(0).toString();
					objdto=(ViewOrderDto)arr.get(i);
					csInsertLocData= conn.prepareCall(sql_INSERT_LOC_DATA);
					
					csInsertLocData.setString(1,objdto.getServiceProductID());
					csInsertLocData.setString(2,objdto.getLocNo());
					csInsertLocData.setString(3,objdto.getLocDate());
					csInsertLocData.setString(4,objdto.getLocRecDate());
					csInsertLocData.setString(5,objdto.getLocNo_Status());
					csInsertLocData.setString(6,objdto.getLocDate_Status());
					csInsertLocData.setString(7,objdto.getLocRecDate_Status());
//					Start[082]
					csInsertLocData.setLong(8,objdto.getBtDoneBy());
					csInsertLocData.setLong(9,orderNo);
//					End[082]
					csInsertLocData.execute();
	
					
					
		 
	
}
		try
		{
			DbConnection.closeCallableStatement(csInsertLocData);
		}
		catch(Exception ex)
		{
			//ex.printStackTrace();
			 Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}


}
	
// 005 end
	public ArrayList<ViewOrderDto> getListOfChargesForRateRenewalDisconnection(Connection conn,long serviceProductId) throws Exception
	{
		//Nagarjuna
		String methodName="getListOfChargesForRateRenewalDisconnection", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;	
		//end
		
		
		logger.info(" View Order Interface and getTaskListOfOrder method of ViewOrderModel class have been called");
		
		
		
		
		
		logger.info(" View Order Interface and getTaskListOfOrder method of ViewOrderModel class have been called");
		
		
		
		
		
		
        CallableStatement csCharges_Info_For_Disconnection=null;
        ArrayList alSelect_Charge_Details_For_Disconnection=null;
        ResultSet rsCharge_Details_For_Disconnection=null;
		
		
		try 
		{
			 
				
			alSelect_Charge_Details_For_Disconnection = new ArrayList<ViewOrderDto>();
						
			csCharges_Info_For_Disconnection= conn.prepareCall(sqlGet_Select_Charges_For_Disconnection_For_RateRenewal);
			csCharges_Info_For_Disconnection.setLong(1,serviceProductId);
			rsCharge_Details_For_Disconnection=csCharges_Info_For_Disconnection.executeQuery();
						
						while (rsCharge_Details_For_Disconnection.next()) {
							try {
									ViewOrderDto objViewOrderDto=new ViewOrderDto();
									objViewOrderDto.setChargeTypeName(rsCharge_Details_For_Disconnection.getInt("CHARGESTYPE"));
									objViewOrderDto.setRcId(rsCharge_Details_For_Disconnection.getInt("RCID"));
									objViewOrderDto.setNrcId(rsCharge_Details_For_Disconnection.getInt("NRCID"));
									objViewOrderDto.setChargeDisconnectDate(rsCharge_Details_For_Disconnection.getTimestamp("EFFECTIVEDATE"));
									objViewOrderDto.setFx_Status(rsCharge_Details_For_Disconnection.getInt("FX_STATUS"));
									objViewOrderDto.setCharge_info_id(rsCharge_Details_For_Disconnection.getInt("CHARGEINFOID"));
									
									alSelect_Charge_Details_For_Disconnection.add(objViewOrderDto);
							}	
							catch(Exception e)
							{
								//e.printStackTrace();
								Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
							}
								
								
						}
					
					} catch (Exception e) {
						Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
						//e.printStackTrace();
						// throw new Exception("No Record Found");
					} 
					return alSelect_Charge_Details_For_Disconnection;
	}
	
	public ArrayList<ViewOrderDto> getListOfChargesForSolutionChangeDisconnection(Connection conn,long serviceProductId,String billingTriggerDate) throws Exception
	{
		
		//Nagarjuna
		String methodName="getListOfChargesForSolutionChangeDisconnection", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;	
		//end
		
		
		logger.info(" View Order Interface and getTaskListOfOrder method of ViewOrderModel class have been called");
		
		
		
		
		
		
        CallableStatement csCharges_Info_For_Disconnection=null;
        ArrayList alSelect_Charge_Details_For_Disconnection=null;
        ResultSet rsCharge_Details_For_Disconnection=null;
        
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date date = df.parse(billingTriggerDate); 
        java.sql.Timestamp timest = new java.sql.Timestamp(date.getTime()); 


		
		
		try 
		{
			 
				
			alSelect_Charge_Details_For_Disconnection = new ArrayList<ViewOrderDto>();
						
			csCharges_Info_For_Disconnection= conn.prepareCall(sqlGet_Select_Charges_For_SolutionChange_Disconnection);
			csCharges_Info_For_Disconnection.setLong(1,serviceProductId);
			rsCharge_Details_For_Disconnection=csCharges_Info_For_Disconnection.executeQuery();
						
						while (rsCharge_Details_For_Disconnection.next()) {
							try {
									ViewOrderDto objViewOrderDto=new ViewOrderDto();
									objViewOrderDto.setChargeTypeName(rsCharge_Details_For_Disconnection.getInt("CHARGESTYPE"));
									objViewOrderDto.setRcId(rsCharge_Details_For_Disconnection.getInt("RCID"));
									objViewOrderDto.setNrcId(rsCharge_Details_For_Disconnection.getInt("NRCID"));
									objViewOrderDto.setChargeDisconnectDate(timest);
									objViewOrderDto.setFx_Status(rsCharge_Details_For_Disconnection.getInt("FX_STATUS"));
									objViewOrderDto.setCharge_info_id(rsCharge_Details_For_Disconnection.getInt("CHARGEINFOID"));
									
									alSelect_Charge_Details_For_Disconnection.add(objViewOrderDto);
							}	
							catch(Exception e)
							{
								//e.printStackTrace();
								Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
							}
								
								
						}
					
					} catch (Exception e) {
						Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
						//e.printStackTrace();
						// throw new Exception("No Record Found");
					} 
					return alSelect_Charge_Details_For_Disconnection;
	}

	public ArrayList<ViewOrderDto> getBillingTiggerResult_Disconnection(Connection conn, String csvServiceProductIds) throws Exception{
		ArrayList<ViewOrderDto> list = new ArrayList<ViewOrderDto>();
		
		ResultSet rs= null;
		CallableStatement cs =null;
		String str="";
		
		logger.info("getBillingTiggerResult_Disconnection method of ViewOrderDao class have been called");
		
		cs = conn.prepareCall(sqlGet_BillinmgTriggerResult_Disconnection);
		cs.setString(1,csvServiceProductIds);
		rs=cs.executeQuery();
		
		while (rs.next()) 
		{
			ViewOrderDto objViewOrderDto= new ViewOrderDto();
			objViewOrderDto.setServiceProductID(rs.getString("SERVICEPRODUCTID")); 
			objViewOrderDto.setBillingTriggerStatus(rs.getString("BILLING_TRIGGER_DISCONNECT_ALL"));
		    list.add(objViewOrderDto);
		}
		
		return list;
	}
	
	public void setBTEndIfPossible(Long orderNo,Connection optionalConnection){
		//SR
		//Nagarjuna
		String methodName="setBTEndIfPossible", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;	
		//end
		Connection conn = null;
		ResultSet rs= null;
		CallableStatement cs =null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		String save=null;
		try{
				
				ViewOrderDto objViewdto = new ViewOrderDto();
				objViewdto.setPonum(orderNo);
				HashMap hmServiceStatus	=	new HashMap();
				HashMap<String,String> hm=new HashMap<String, String>();
				//below code add by Anil for CLEP requrement
				if(optionalConnection==null){
					conn=DbConnection.getConnectionObject();
				}else{
					conn=optionalConnection;
				}
				//end CLEP
				ArrayList<ViewOrderDto> alSelectLinesDetails = getSELECT_BT_PRODUCTS_FOR_CHANGE_ORDERS(objViewdto,conn);
				
				//fetch all services of order whose M6 End has reached
				cs	=	conn.prepareCall(sqlGet_BT_SERVICES_CHANGE_ORDERS);
				cs.setLong(1,orderNo);
				rs	=	cs.executeQuery();
		
				
				while(rs.next())
				{
					hmServiceStatus.put(rs.getString("SERVICEID"),null);				
				}
				
				for (ViewOrderDto dto1 : alSelectLinesDetails) {
					
					String serviceId=dto1.getServiceId();
					hm.put(serviceId,serviceId);
				}
				
				ArrayList serviceids=new ArrayList(hm.values());
				
				//ArrayList<ViewOrderDto> eventids=getOSNLIST(serviceids);
				OrderHeaderDTO orderInfo = getOrderType(conn,orderNo);
				
				BillingTriggerValidation billingTriggerValidation=null; 
				for (ViewOrderDto dto : alSelectLinesDetails) {
					billingTriggerValidation=new BillingTriggerValidation(); 
					billingTriggerValidation.resetStateToNew();
					billingTriggerValidation.setSourceData(dto,orderInfo,null);
					billingTriggerValidation.computeProperties();
					
					if("required".equalsIgnoreCase(billingTriggerValidation.getBillingTriggerActionStatus()))
					{
						hmServiceStatus.put(dto.getServiceId(),"PENDING");
					}
					
					else
						
						if(("done".equalsIgnoreCase(billingTriggerValidation.getBillingTriggerActionStatus()))&&(dto.getIsAutoBilling()==1)&&("notfilled".equalsIgnoreCase(billingTriggerValidation.getLocdataforhwSale()))){
							hmServiceStatus.put(dto.getServiceId(),"PENDING");
						}
				}
				
				
				
				//Set set = hmServiceStatus.entrySet();
				Iterator itr = null;
				itr = hmServiceStatus.keySet().iterator();
				while(itr.hasNext())
				{	
					String key	=	(String)itr.next();
					if(hmServiceStatus.get(key) == null)
					{
						
						pstmt = conn.prepareStatement(sqlUpdate_BT_STATUS);
						pstmt.setLong(1,new Long(key));
						pstmt.executeUpdate();
					}
				}
				/*pstmt1 = conn.prepareStatement(sqlUpdate_ORDER_STAGE);
				pstmt1.setLong(1,orderNo);
					pstmt1.setString(2,"");
						pstmt1.setLong(3,0);
							pstmt1.setString(4,"");
				pstmt1.executeUpdate(); */
		}catch(Exception e)
		{
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}finally {
				try {
					if(rs!=null)DbConnection.closeResultset(rs);
					if(cs!=null)DbConnection.closeCallableStatement(cs);
					if(pstmt!=null)DbConnection.closePreparedStatement(pstmt);
					if(pstmt!=null)DbConnection.closePreparedStatement(pstmt1);
					if(optionalConnection==null){
						DbConnection.freeConnection(conn);
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
				}
			}
	}
	public static String checkPublishServiceStatus="{call IOE.SP_CANCEL_PUBLISH_SERVICE_STATUS(?,?,?,?)}";	
	public Long checkPublishServiceStatus(int OrderNo, String userid) throws Exception
	{
		//Nagarjuna
		String methodName="checkPublishServiceStatus", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;	
		//end
		Long TM6OrderNo=null;
		Connection conn = null;
		//ResultSet rsTM6OrderNo = null;
		CallableStatement csTM6OrderNo =null;
		try
		{
			conn=DbConnection.getConnectionObject();
			csTM6OrderNo=conn.prepareCall(checkPublishServiceStatus);
			csTM6OrderNo.setLong(1, OrderNo);
			csTM6OrderNo.setLong(2, 0);
			csTM6OrderNo.setInt(3, 0);	
			csTM6OrderNo.setString(4, userid);	
			csTM6OrderNo.execute();
			//while(rsTM6OrderNo.next())
			//{
				//TM6OrderNo=rsTM6OrderNo.getLong("ORDERNO");
				TM6OrderNo=csTM6OrderNo.getLong(2);
			//}
			
			if(csTM6OrderNo.getInt(3)!=0)
			{
				TM6OrderNo=new Long(0);
			}
			
		}
		catch(Exception ex)
		{
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			//ex.printStackTrace();
			//throw new Exception("No Record Found");
		}
		finally
		{
			DbConnection.closeCallableStatement(csTM6OrderNo);
			DbConnection.freeConnection(conn);
		}
		return TM6OrderNo;
	}			
				
	public ArrayList<ComponentsDto> fnGetComponentsDetails(ViewOrderDto dto ,Connection conn ) throws Exception
	{
		//Nagarjuna
		String methodName="fnGetComponentsDetails", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;	
		//end
		ArrayList<ComponentsDto> list = new ArrayList<ComponentsDto>();
		ResultSet rs= null;
		CallableStatement cs =null;
		
	logger.info("fnGetComponentsDetails method of ViewOrderDao class have been called");
		
		try {
			
			
			
			cs = conn.prepareCall(sqlGet_componentsDeatilsForAllOrders);
	             cs.setLong(1, new Long (dto.getServiceProductID()).longValue());
	             cs.setLong(2, new Long (dto.getOrderNo()).longValue());
			rs=cs.executeQuery();
			
		while(rs.next()){
			
			
			ComponentsDto objViewOrderDto= new ComponentsDto();
			
			objViewOrderDto.setComponentID(rs.getInt("COMPONENT_ID"));
			objViewOrderDto.setComponentName(rs.getString("COMPONENT_NAME")); 
			objViewOrderDto.setPackageID(rs.getInt("PACKAGE_ID"));
			objViewOrderDto.setPackageName(rs.getString("PACKAGE_NAME"));
			objViewOrderDto.setComponentStatus(rs.getString("COMPONENT_STATUS")); 
			objViewOrderDto.setCreatedInOrderNo(rs.getString("CREATED_IN_ORDER")); 
			objViewOrderDto.setDisconnectedInOrderNo(rs.getString("DISCONNECTED_IN_ORDER_NO")); 
			objViewOrderDto.setStartLogic(rs.getString("COMPONENT_START_LOGIC")); 
			objViewOrderDto.setStartDate(Utility.IfNullToBlank(rs.getString("COMPONENT_START_DATE"))); 
			objViewOrderDto.setEndLogic(rs.getString("COMPONENT_END_LOGIC")); 
			objViewOrderDto.setDisconnectDate(Utility.IfNullToBlank(rs.getString("COMPONENT_END_DATE"))); 
			objViewOrderDto.setFxStartStatus (rs.getString("START_FX_STATUS")); 
			objViewOrderDto.setFxTokenNo(rs.getString("START_TOKEN_NO")); 
			objViewOrderDto.setFxStartNo(rs.getString("FX_START_NO")); 
			objViewOrderDto.setEndTokenNo(rs.getString("END_TOKEN_NO"));
			objViewOrderDto.setEndFxStatus(rs.getString("END_FX_STATUS"));
			objViewOrderDto.setEndFxNo(rs.getString("FX_END_NO"));
			objViewOrderDto.setStartStatus(rs.getString("FX_START_STATUS"));
			objViewOrderDto.setEndStatus(rs.getString("FX_END_STATUS"));
			objViewOrderDto.setComponent_ID(rs.getInt("COMPONENTINFOID")); 
			objViewOrderDto.setComponentInstanceID(rs.getString("COMPONENT_INST_ID")); 
			objViewOrderDto.setDisconnectionType(rs.getString("DISCONNECTION_TYPE"));
	        objViewOrderDto.setBillingTriggerStatus(rs.getString("BILLING_TRIGGER_STATUS"));
	        objViewOrderDto.setIsLineDisconnected(rs.getString("IS_LINE_DISCONNECTED"));
	        objViewOrderDto.setDisconnectedInCurrentService(rs.getString("COMPONENT_DISCONNECTED_IN_CURRENT_ORDER"));
	        objViewOrderDto.setStartDateDays(rs.getInt("START_DAYS"));
	        objViewOrderDto.setStartDateMonth(rs.getInt("START_MONTHS"));
	        objViewOrderDto.setEndDateDays(rs.getInt("END_DAYS"));
	        objViewOrderDto.setEndDateMonth(rs.getInt("END_MONTHS"));
	        objViewOrderDto.setPackageInstId(rs.getString("PACKAGE_INST_ID"));
	        objViewOrderDto.setPackageInstIdServ(rs.getString("PACKAGE_INST_ID_SERV"));
			
			list.add(objViewOrderDto);
			
			
		}
			
			
			
			
		}
		
		
		catch (Exception e) {
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			//e.printStackTrace();
			//throw new Exception("No Record Found");
		} 
		finally
		{
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(cs);
		}
		
		
		
		return list;
		
		




		}
	
	
	
	
//	 005 start
	
	public NewOrderDto pushComponentsInSecondaryTablesForChangeOrders(Connection conn,long longServiceProductId,String locNo,String locDate,String billingTriggerDate, String locRecDate, long orderNo) throws Exception 
{	
		//Nagarjuna
		String methodName="pushComponentsInSecondaryTablesForChangeOrders", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;	
		//end
	String status=null;
	NewOrderDto newOrderDto=new NewOrderDto();
	 CallableStatement csUpdateLocData=null;
	try {
		//DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		//java.util.Date date = df.parse(billingTriggerDate); 
       //java.sql.Timestamp timest = new java.sql.Timestamp(date.getTime());
		 csUpdateLocData = conn.prepareCall(sqlUpdate_LocDate);
		 csUpdateLocData.setLong(1,longServiceProductId);
		 csUpdateLocData.setString(2,locDate);
		 csUpdateLocData.setString(3,billingTriggerDate);
		 csUpdateLocData.setString(4,locNo);
		 csUpdateLocData.setString(5,locRecDate);
		 
		 csUpdateLocData.execute();			
		 CallableStatement cstmt= conn.prepareCall(sqlInsert_ServiceAndComponentsInfoFx); 
		 cstmt.setLong(1, longServiceProductId);
		 cstmt.registerOutParameter(2, java.sql.Types.INTEGER);
		 cstmt.registerOutParameter(3, java.sql.Types.VARCHAR);
		 cstmt.registerOutParameter(4, java.sql.Types.VARCHAR);
		 cstmt.setLong(5,orderNo);
		 cstmt.execute();
		 if("0".equalsIgnoreCase(cstmt.getString(3)))
		 {
			 conn.commit();
			 status="SUCCESS";
			 //add by Anil for CLEP
			 	newOrderDto.setBillingTriggerStatus(status);
			 //end CLEP
		 }
		 else
		 {// print status codes
			 conn.rollback();
			 //changs here by Anil for CLEP
			 String MSG="errors in proc : IOE.FX_PUSH_CHARGES_IN_SECONDARY_TABLES_FOR_CHANGE_ORDERS , msgcode="+cstmt.getString(8)+"  msg="+cstmt.getString(9)+" sqlcode:"+cstmt.getInt(7)+"  , longServiceProductId="+longServiceProductId;
			 //throw new Exception("errors in proc : IOE.FX_PUSH_CHARGES_IN_SECONDARY_TABLES_FOR_CHANGE_ORDERS , msgcode="+cstmt.getString(3)+"  msg="+cstmt.getString(4)+" sqlcode:"+cstmt.getInt(2));
			 Utility.LOG(MSG);
			 newOrderDto.setBillingTriggerMsg(MSG);
			 //end CLEP
		 }
	 }catch(Exception e)
	 {
		 //Utility.LOG(true, true, e, "");
		 //e.printStackTrace();
		 conn.rollback();
		 Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
	 }
	 finally
	 {
		 DbConnection.closeCallableStatement(csUpdateLocData);
	 }
	 return newOrderDto;

}

	public ArrayList<ViewOrderDto> getServiceslist(long ponum, Connection conn) {
		ArrayList<ViewOrderDto> serviceList = null;
		//Nagarjuna
		String methodName="getServiceslist", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;	
		//end
		CallableStatement cstmt = null;
		ResultSet rs = null;
		ViewOrderDto serviceDto = null;
		try {
			cstmt=conn.prepareCall(sqlGetBTScreenServicesList);
			cstmt.setLong(1, ponum);
			rs=cstmt.executeQuery();
			serviceList=new ArrayList<ViewOrderDto>();
			while(rs.next())
			{
				serviceDto = new ViewOrderDto();
				serviceDto.setServiceId(rs.getString("SERVICEID"));
				serviceDto.setLogicalSino(rs.getString("LOGICAL_SI_NO"));
				serviceDto.setCustLogicalSino(rs.getString("CUSTOMER_LOGICAL_SI_NO"));
				serviceList.add(serviceDto);
			}
		} catch (SQLException e) {
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}finally
		{
			try {
				DbConnection.closeResultset(rs);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			}
			DbConnection.closeCallableStatement(cstmt);
		}
		
		
		return serviceList;
	}
	
	
	
	//080 start
	public ArrayList<ViewOrderDto> getLineDetailsforBTBulkUpload() throws Exception{
	ArrayList<ViewOrderDto> alLineDetails = null;
	//Nagarjuna
	String methodName="getLineDetailsforBTBulkUpload", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;	
	//end
	Connection conn = null;
	ResultSet rsSelectServiceDetails = null;
	CallableStatement csLineDetails =null;
	
	logger.info("getLineDetailsforBTBulkUpload method of ViewOrderDao class have been called");
	
	try {
		alLineDetails = new ArrayList<ViewOrderDto>();
		conn = DbConnection.getConnectionObject();
		csLineDetails= conn.prepareCall(sqlGetLineDetailsforBulkUpload);
		csLineDetails.setString(1,null);
		rsSelectServiceDetails=csLineDetails.executeQuery();
		
			while (rsSelectServiceDetails.next()) {
			ViewOrderDto objViewOrderDto= new ViewOrderDto();
			objViewOrderDto.setLineNumber(Utility.fnCheckNull(rsSelectServiceDetails.getString("LINENUMBER")));    
			objViewOrderDto.setLineName(Utility.fnCheckNull(rsSelectServiceDetails.getString("LINENAME")));
			objViewOrderDto.setLogicalSino(Utility.fnCheckNull(rsSelectServiceDetails.getString("LOGICALSINO")));
			objViewOrderDto.setCustLogicalSino(Utility.fnCheckNull(rsSelectServiceDetails.getString("CUSTLOGICALSINO")));
			objViewOrderDto.setOrderno(rsSelectServiceDetails.getInt("ORDERNO"));
			objViewOrderDto.setOrdertype(Utility.fnCheckNull(rsSelectServiceDetails.getString("ORDERTYPE")));
			objViewOrderDto.setOrder_subtype(Utility.fnCheckNull(rsSelectServiceDetails.getString("CHANGETYPENAME")));
			objViewOrderDto.setSiid(Utility.fnCheckNull(rsSelectServiceDetails.getString("SIID")));
			objViewOrderDto.setFx_ACCOUNT_EXTERNAL_ID(Utility.fnCheckNull(rsSelectServiceDetails.getString("FX_ACCOUNT_EXTERNAL_ID")));     
			objViewOrderDto.setFx_status(Utility.fnCheckNull(rsSelectServiceDetails.getString("FX_STATUS")));
			objViewOrderDto.setLine_status(Utility.fnCheckNull(rsSelectServiceDetails.getString("LINE_STATUS")));
		    objViewOrderDto.setChallen_No(Utility.fnCheckNull(rsSelectServiceDetails.getString("CHALLEN_NO")));
			objViewOrderDto.setChallen_date(Utility.fnCheckNull(rsSelectServiceDetails.getString("CHALLEN_DATE")));
			objViewOrderDto.setIsLineDisconnected(Utility.fnCheckNull(rsSelectServiceDetails.getString("ISLINE_DISCONNECTED")));
			objViewOrderDto.setLineOldOrNew(Utility.fnCheckNull(rsSelectServiceDetails.getString("LINE_OLD_OR_NEW")));
			objViewOrderDto.setServiceId(rsSelectServiceDetails.getString("SERVICEID"));
		    objViewOrderDto.setDuplicate_cktid(rsSelectServiceDetails.getString("DUPLICATE_CKTID"));
		    objViewOrderDto.setCktId(rsSelectServiceDetails.getString("CKTID"));
			objViewOrderDto.setIsAutoBilling(rsSelectServiceDetails.getInt("ISAUTOBILLING"));
			objViewOrderDto.setServiceType(rsSelectServiceDetails.getString("SERVICETYPE"));
			objViewOrderDto.setSendToM6(rsSelectServiceDetails.getInt("SENDTOM6"));
			objViewOrderDto.setLocRecDate(Utility.fnCheckNull(rsSelectServiceDetails.getString("LOC_REC_DATE")));
			objViewOrderDto.setLocNo(Utility.fnCheckNull(rsSelectServiceDetails.getString("LOCNO")));
			objViewOrderDto.setLocDate(Utility.fnCheckNull(rsSelectServiceDetails.getString("LOCDATE")));
			objViewOrderDto.setBillingTriggerDate(Utility.fnCheckNull(rsSelectServiceDetails.getString("BILLINGTRIGGERDATE")));
			objViewOrderDto.setPmProvisioningDate(Utility.fnCheckNull(rsSelectServiceDetails.getString("PMPROVISIONINGDATE")));
			alLineDetails.add(objViewOrderDto);
		}
		
	
	
	} catch (Exception e) {
		Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		//e.printStackTrace();
		//throw new Exception("No Record Found");
	} finally {
		
			DbConnection.freeConnection(conn);
	}
	return alLineDetails;
}
	
//080 end
	
//081 start	
	public ArrayList<ViewOrderDto> getChargeDetailsforBulkUpload() throws Exception{
		ArrayList<ViewOrderDto> alChargeDetails = null;
		//Nagarjuna
		String methodName="getChargeDetailsforBulkUpload", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;	
		//end
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement csSelectChargeDetails =null;
		logger.info("getLineDetailsforBTBulkUpload method of ViewOrderDao class have been called");
		
		try {
			alChargeDetails = new ArrayList<ViewOrderDto>();
			conn = DbConnection.getConnectionObject();
			csSelectChargeDetails= conn.prepareCall(sqlGetChargeDetailsforBulkUpload);
			//parameter removed by manisha
			csSelectChargeDetails.setString(1,null);
			rs=csSelectChargeDetails.executeQuery();
			
				while (rs.next()) {
				ViewOrderDto objViewOrderDto= new ViewOrderDto();
				objViewOrderDto.setChargeType(rs.getString("CHARGESTYPE"));
				objViewOrderDto.setChargeName(rs.getString("CHARGE_NAME")); 
				objViewOrderDto.setChargePeriod(rs.getInt("CHARGEPERIOD")); 
				objViewOrderDto.setChargeAmt(rs.getDouble("CHARGEAMOUNT")); 
				objViewOrderDto.setChargeStatus(rs.getString("CHARGESTATUS"));
				objViewOrderDto.setChargeCreatedOnOrder(rs.getString("START_ORDER_NO"));
				objViewOrderDto.setChargeEndedOnOrder(rs.getString("END_ORDER_NO"));
				objViewOrderDto.setIsLineDisconnected(rs.getString("IS_LINE_DISCONNECTED")); 
				objViewOrderDto.setBillPeriod(Utility.fnCheckNull(rs.getString("BILL_PERIOD")));
				objViewOrderDto.setChargefrequency(Utility.fnCheckNull(rs.getString("CHARGEFREQUENCY")));
				objViewOrderDto.setChargefrequencyamount(rs.getDouble("CHARGEFREQUENCYAMT")); 
				objViewOrderDto.setStartdatelogic(Utility.fnCheckNull(rs.getString("STARTDATELOGIC_STRING"))); 
				objViewOrderDto.setStart_date_days(rs.getInt("START_DATE_DAYS")); 
				objViewOrderDto.setStart_date_month(rs.getInt("START_DATE_MONTH")); 
				objViewOrderDto.setChargeStartDate_String(rs.getString("CHARGE_START_DATE"));
				objViewOrderDto.setEnddatelogic(Utility.fnCheckNull(rs.getString("ENDDATELOGIC_STRING"))); 
				objViewOrderDto.setEnd_date_days(rs.getInt("END_DATE_DAYS")); 
				objViewOrderDto.setEnd_date_month(rs.getInt("END_DATE_MONTH")); 
				objViewOrderDto.setChargeEndDate_String(rs.getString("CHARGE_INITIAL_END_DATE"));	
			    objViewOrderDto.setAnnualRate(rs.getString("ANNUAL_RATE"));
				objViewOrderDto.setAnnotation(rs.getString("ANNOTATION"));
				objViewOrderDto.setChargeId(rs.getString("CHARGE_ID"));
				objViewOrderDto.setFxViewId(rs.getString("FX_VIEW_ID"));
				objViewOrderDto.setChargeStartStatus(rs.getString("CHARGE_START_STATUS"));
			    objViewOrderDto.setStartTokenNo(rs.getString("START_TOKEN_NO"));
				objViewOrderDto.setStartFxStatus(rs.getString("START_FX_STATUS"));
				objViewOrderDto.setStartFxNo(rs.getString("START_FX_NO"));
				objViewOrderDto.setChargeEndStatus(rs.getString("CHARGE_END_STATUS"));
			    objViewOrderDto.setEndTokenNo(rs.getString("END_TOKEN_NO"));
				objViewOrderDto.setEndFxStatus(rs.getString("END_FX_STATUS"));
				objViewOrderDto.setEndFxNo(rs.getString("END_FX_NO"));
				objViewOrderDto.setIsChargeDisconnectedInCurrentOrder(rs.getString("isChargeDisconnectedInCurrentOrder"));
				objViewOrderDto.setDisconnectiondate(rs.getString("CHARGE_DISCONNECTION_DATE"));
				objViewOrderDto.setChargeInfoId(rs.getInt("CHARGEINFOID"));
				objViewOrderDto.setLineNumber(rs.getString("SERVICEPRODUCTID"));
				objViewOrderDto.setDisconnectionType(rs.getString("DISCONNECTION_TYPE"));
				objViewOrderDto.setBillingTriggerStatus(rs.getString("BILLING_TRIGGER_STATUS"));
				alChargeDetails.add(objViewOrderDto);
			}
			
		
		
		} catch (Exception e) {
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			//e.printStackTrace();
			//throw new Exception("No Record Found");
		} finally {
			
				DbConnection.freeConnection(conn);
		}
		return alChargeDetails;
	}
	
//081 end	
	
	public String schedulerForArborUpdate() throws Exception 
	{	
		//Nagarjuna
		String methodName="schedulerForArborUpdate", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;	
		//end
		String status=null;
		ResultSet rs = null;
		NewOrderDto objRetDto = new NewOrderDto();
		ArrayList<NewOrderDto> unprocessedSpids = new ArrayList<NewOrderDto>();
		Connection con = null;
		try {
			
			con = DbConnection.getConnectionObject();
			con.setAutoCommit(false);
			CallableStatement cstmt1= con.prepareCall(sqlUpdateArborDataScheduler);
			PreparedStatement pstmt= con.prepareStatement(sqlGetUnProcessedSpids);
			CallableStatement cstmt2= con.prepareCall(sqlProcessArborDataScheduler);
			cstmt1.execute();//This function is commiting itself
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				objRetDto =  new NewOrderDto();
				objRetDto.setServiceProductID(rs.getInt("SERVICEPRODUCTID"));
				unprocessedSpids.add(objRetDto);
			}
			pstmt.close();
			for (int i=0;i<unprocessedSpids.size();i++) 
			{
				cstmt2.setLong(1,unprocessedSpids.get(i).getServiceProductID());
				cstmt2.execute();//This function is commiting and rollbacking itself
			}
			
		}catch(Exception e)
		{
			con.rollback();
			//Utility.LOG(true, true, e, "");
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}finally{
			DbConnection.freeConnection(con);
		}
		 return status;

	}
	public int updateFullPublish(OrderHeaderDTO objDto, long orderNo,Connection connection,CallableStatement callstmt)
	throws Exception
	{
		//Nagarjuna
		String methodName="updateFullPublish", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;	
		//end
		int errorCode = 0;
		try
		{
			callstmt= connection.prepareCall(sqlinsertFullPublish);	
			callstmt.setLong(1, orderNo);
			callstmt.setString(2, "");
			callstmt.setInt(3, 0);
			callstmt.setString(4, "");
			callstmt.setLong(5, objDto.getPublished_by_empid());
			callstmt.setLong(6, objDto.getPublished_by_roleid());
			callstmt.execute();
			errorCode=callstmt.getInt(3);
		}
		catch(Exception ex )
		{
			errorCode = -1;
			//ex.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}
		return errorCode;
	}
	public int updateViewIsPublish(OrderHeaderDTO objDto, String serviceList,String publishList,Connection connection,CallableStatement callstmt)
	throws Exception
	{
		//Nagarjuna
		String methodName="updateViewIsPublish", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;	
		//end
		int errorCode = 0;
		try
		{
			ArrayList<String> services = new ArrayList<String>();
			ArrayList<String> publishArray = new ArrayList<String>();
			StringTokenizer st = new StringTokenizer( serviceList, ",");
			for (int i =0; st.hasMoreTokens();i++) 
			{
				services.add(st.nextToken());
			}
			StringTokenizer st1 = new StringTokenizer( publishList, ",");
			for (int i =0; st1.hasMoreTokens();i++) 
			{
				publishArray.add(st1.nextToken());
			}
			for(int count=0; count<services.size();count++)
			{
				callstmt= connection.prepareCall(sqlinsertViewPartialPublish);	
				callstmt.setLong(1, Long.valueOf(services.get(count)));
				callstmt.setLong(2, Long.valueOf(publishArray.get(count)));
				callstmt.setString(3, "");
				callstmt.setInt(4, 0);
				callstmt.setString(5, "");
				callstmt.setLong(6, objDto.getPublished_by_empid());
				callstmt.setLong(7, objDto.getPublished_by_roleid());
				callstmt.execute();
				errorCode=callstmt.getInt(3);
			}
		}
		catch(Exception ex )
		{
			errorCode = -1;
			//ex.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}
		return errorCode;
	}
	//Start[082]
	public String validateNOFXSIComponent(long orderno) throws Exception{
		//Nagarjuna
		String methodName="validateNOFXSIComponent", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;	
		//end
		String validationResult="";
		String returnMessage="";
		PreparedStatement cs=null;
		ResultSet rs=null;
		Connection conn=null;	
		ArrayList<ComponentDTO> complist=new ArrayList<ComponentDTO>();
		ComponentDTO objDto=null;
		String currentLineitem="";
		String prevLineitem="";
		String currentServiceId="";
		String prevServiceId="";
		String componentid="";	
		
		try{
			conn=DbConnection.getConnectionObject();
			cs=conn.prepareCall(sqlValidateNOFXSIComponent);
			cs.setLong(1, orderno);
			rs=cs.executeQuery();
			while(rs.next()){				
				objDto=new ComponentDTO();
				objDto.setServiceProductId(rs.getString("SERVICEPRODUCTID"));
				objDto.setComponentid(rs.getString("COMPONENT_ID"));
				objDto.setServiceId(rs.getString("SERVICEID"));
				complist.add(objDto);
			}			
			int lastpending=0;
			for(int index=0;index<complist.size();index++){
				currentLineitem=complist.get(index).getServiceProductId();
				currentServiceId=complist.get(index).getServiceId();
				if(currentLineitem.equalsIgnoreCase(prevLineitem) || index==0){
					if("".equals(componentid)){
						componentid=complist.get(index).getComponentid();
					}
					else{
						componentid=componentid+","+complist.get(index).getComponentid();
					}
					prevLineitem=currentLineitem;
					prevServiceId=currentServiceId;
					lastpending=1;
				}
				else{										
					validationResult=validationResult+"Service Id: "+prevServiceId+", Line Item Id: "+prevLineitem 
					+", Component Id: \t"+componentid+"\n";					
					componentid=complist.get(index).getComponentid();
					prevLineitem=currentLineitem;
					prevServiceId=currentServiceId;
					lastpending=1;
				}				
			}
			if(lastpending==1)
			{
				validationResult=validationResult+"Service Id: "+prevServiceId+", Line Item Id: "+prevLineitem 
				+", Component Id: \t"+componentid+"\n";	
			}
			if(complist.size() > 0){
				returnMessage="Service level component cannot be added in following line items as line items are not having Service in FX. Please Remove These Components\n"+validationResult;
			}
			
		}catch(Exception e){
			returnMessage="Some error occured for No fx si component validation";
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}finally{
			DbConnection.closeResultset(rs);
			DbConnection.closePreparedStatement(cs);
			DbConnection.freeConnection(conn);
		}
		return returnMessage;
	}
	//End[082]
	// added by manisha cust bill exp bfr 14 start 
	private static String sqlGet_ValidatebILLINGtRIGGERDate="{call IOE.GET_DISCONNECTION_DATA_BILLING_TRIGGER(?)}";
	public ViewOrderDto validateDisconnectionDate(long orderNo,Connection optionalConnection ,HashMap<String,String> hmap_SPID_BTDate) throws Exception
	{
		ViewOrderDto  objDto = null;
		
		String activeDateStatus = "SUCCESS";
		CallableStatement csValidateBillingDate =null;
		ArrayList<ViewOrderDto> arrViewOrderDto = new  ArrayList<ViewOrderDto>();
		ViewOrderDto objViewOrderDto = null;
		ViewOrderDto objViewDto = null;
		ViewOrderDto objRetViewOrderDto = null;
		SimpleDateFormat sdf= new SimpleDateFormat(AppConstants.CALENDER_FORMAT_MMDDYY);
		HashMap<String,ViewOrderDto> mapvalidate_dto=new HashMap<String,ViewOrderDto>();
		//HashMap<String,ArrayList<DifferentialCreditNoteReportDTO>> mapvalidate_initiatedatedto=new HashMap<String,ArrayList<DifferentialCreditNoteReportDTO>>();
		Connection conn=null;
		String str_Error=null;
		ResultSet rsValidateBillingDate=null;
		ArrayList<String> errorMsgs=new ArrayList<String>();
		int validationFailure=0;
		try
		{
			if(optionalConnection==null)
			{
				conn = DbConnection.getConnectionObject();
			}
			else
				conn=optionalConnection;
			// added by manisha to calculate initiate date start
			csValidateBillingDate = conn.prepareCall(sqlGet_ValidatebILLINGtRIGGERDate);
			csValidateBillingDate.setLong(1,orderNo);
			rsValidateBillingDate=csValidateBillingDate.executeQuery();
			while(rsValidateBillingDate.next())
			{
				objViewOrderDto=new ViewOrderDto();
				objViewOrderDto.setEnddatelogic(rsValidateBillingDate.getString("ENDDATELOGIC"));
				objViewOrderDto.setBilling_Active_date(rsValidateBillingDate.getString("BILLINGACTIVEDATE"));
				objViewOrderDto.setBilling_End_Date(rsValidateBillingDate.getString("BILLING_END_DATE"));
				objViewOrderDto.setStartdatelogic(rsValidateBillingDate.getString("STARTDATELOGIC"));
				objViewOrderDto.setCharge_info_id(rsValidateBillingDate.getInt("CHARGEINFOID"));
				objViewOrderDto.setServiceProductID(rsValidateBillingDate.getString("SERVICEPRODUCTID"));
				objViewOrderDto.setCom_end_logic(rsValidateBillingDate.getString("COMPONENT_END_LOGIC"));
				objViewOrderDto.setCom_start_logic(rsValidateBillingDate.getString("COMPONENT_START_LOGIC"));
				objViewOrderDto.setSystem_start_date(rsValidateBillingDate.getString("SYSTEM_START_DATE"));
				objViewOrderDto.setSystem_end_date(rsValidateBillingDate.getString("SYSTEM_END_DATE"));
				objViewOrderDto.setComponentinfoid(rsValidateBillingDate.getInt("COMPONENTINFOID"));
				objViewOrderDto.setObject_type(rsValidateBillingDate.getString("OBJECT_TYPE"));
				
				//[013] start
				objViewOrderDto.setFx_schedular_create_status(rsValidateBillingDate.getInt("FX_SCHEDULAR_CREATE_STATUS"));
				//[013] end
				
				arrViewOrderDto.add(objViewOrderDto);
			}	
				if(arrViewOrderDto.size()>0)
				{ 
					for(int i=0;i<arrViewOrderDto.size();i++)
					{
						objRetViewOrderDto=arrViewOrderDto.get(i);
						if(hmap_SPID_BTDate.containsKey(objRetViewOrderDto.getServiceProductID()))
						{
							 String inputBTDate=hmap_SPID_BTDate.get(objRetViewOrderDto.getServiceProductID());
							if("CHARGES".equals(objRetViewOrderDto.getObject_type()))
							{
								//[013] start
								if(objRetViewOrderDto.getFx_schedular_create_status()==3){
									String billing_active_date=objRetViewOrderDto.getBilling_Active_date();
								 	if(sdf.parse(inputBTDate).getTime()<sdf.parse(billing_active_date).getTime())
								 	{
								 		  str_Error="Billing Trigger Date should be Greater Than equal to Charge Active Date: "
												+Utility.showDate_Report(sdf.parse(billing_active_date));
										  
											errorMsgs.add(str_Error
													+" for Line No: "
													+objRetViewOrderDto.getServiceProductID() +" for ChargeInfoId: " + objRetViewOrderDto.getCharge_info_id());
													validationFailure=1;
								 	}
								}
								
								//[013] commented
								 /*if("TD".equals(objRetViewOrderDto.getEnddatelogic()))
								 {
									  	String billing_active_date=objRetViewOrderDto.getBilling_Active_date();
									 	if(sdf.parse(inputBTDate).getTime()<sdf.parse(billing_active_date).getTime())
									 	{
									 		  str_Error="Billing Trigger Date should be Greater Than equal to Charge Active Date: "
													+Utility.showDate_Report(sdf.parse(billing_active_date));
											  
												errorMsgs.add(str_Error
														+" for Line No: "
														+objRetViewOrderDto.getServiceProductID() +" for ChargeInfoId: " + objRetViewOrderDto.getCharge_info_id());
														validationFailure=1;
									 	}
								 }
								 else
								 {
									 String billing_active_date=objRetViewOrderDto.getBilling_Active_date();
									 String billing_end_date=objRetViewOrderDto.getBilling_End_Date();
									 	if((sdf.parse(inputBTDate).getTime()<sdf.parse(billing_active_date).getTime())|| (sdf.parse(inputBTDate).getTime()>sdf.parse(billing_end_date).getTime()))
									 	{
									 		str_Error="Billing Trigger Date should be Greater Than equal to  Charge Active Date: "
												+Utility.showDate_Report(sdf.parse(billing_active_date)) + "and less than to Charge Inactive date: " + Utility.showDate_Report(sdf.parse(billing_end_date));
										  
											errorMsgs.add(str_Error
													+" for Line No: "
													+objRetViewOrderDto.getServiceProductID() +" for ChargeInfoId: " + objRetViewOrderDto.getCharge_info_id());
											validationFailure=1;
									 	}
								 }*/
								//[013] end
							}
							 
							 else
							 {
								 if(objRetViewOrderDto.getSystem_end_date()==null)
								 {
									 String system_active_date=objRetViewOrderDto.getSystem_start_date();
									 	if(sdf.parse(inputBTDate).getTime()<sdf.parse(system_active_date).getTime())
									 	{
									 		  str_Error="Billing Trigger Date should be Greater Than equal to Component Active Date: "
													+Utility.showDate_Report(sdf.parse(system_active_date));
											  
												errorMsgs.add(str_Error
														+" for Line No: "
														+objRetViewOrderDto.getServiceProductID() +" for Componentid: " + objRetViewOrderDto.getComponentinfoid());
												validationFailure=1;
									 	}
									 
								 }
								 else
								 {
									 String system_active_date=objRetViewOrderDto.getSystem_start_date();
									 String system_end_date=objRetViewOrderDto.getSystem_end_date();
									 	if(sdf.parse(inputBTDate).getTime()<sdf.parse(system_active_date).getTime()|| sdf.parse(inputBTDate).getTime()>sdf.parse(system_end_date).getTime())
									 	{
									 		str_Error="Billing Trigger Date should be Greater Than equal to Component Active Date: "
												+Utility.showDate_Report(sdf.parse(system_active_date)) + "and less than to Component Inactive date: " + Utility.showDate_Report(sdf.parse(system_end_date));
										  
											errorMsgs.add(str_Error
													+" for Line No: "
													+objRetViewOrderDto.getServiceProductID() +" for Componentid: " + objRetViewOrderDto.getComponentinfoid());
											validationFailure=1;
									 	}
								 }
							}
						}	
					}
				}
				
				objViewDto=new ViewOrderDto();
				if(str_Error!=null)
				{
					objViewDto.setErrorlog(Utility.fnCheckNull(objViewOrderDto.getErrorlog())+str_Error);
				}
				
				if(validationFailure>0)
				{
					objViewDto.setIfAnyFailValidation("FAILURE");
					objViewDto.setBillingTriggerValidationErrors(errorMsgs);
				}
				else
				{
					objViewDto.setIfAnyFailValidation("SUCCESS");
				}
			
		}		
		
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally{
			DbConnection.closeResultset(rsValidateBillingDate);
			DbConnection.freeConnection(conn);
		}
		
		return objViewDto;
	}
	// added by manisha cust bill exp bfr 14 end
	
	
	public ViewOrderDto validateDisconnectionDate_forBulkUpload(long fileid,Connection optionalConnection,HashMap<String, String> hmap_SPID_BTDate ) throws Exception
	{
		ViewOrderDto  objDto = null;
		
		String activeDateStatus = "SUCCESS";
		CallableStatement csValidateBillingDate =null;
		ArrayList<ViewOrderDto> arrViewOrderDto = new  ArrayList<ViewOrderDto>();
		ViewOrderDto objViewOrderDto = null;
		ViewOrderDto objViewDto = null;
		ViewOrderDto objRetViewOrderDto = null;
		SimpleDateFormat sdf= new SimpleDateFormat(AppConstants.CALENDER_FORMAT_MMDDYY);
		HashMap<String,ViewOrderDto> mapvalidate_dto=new HashMap<String,ViewOrderDto>();
		HashMap<String,ArrayList<DifferentialCreditNoteReportDTO>> mapvalidate_initiatedatedto=new HashMap<String,ArrayList<DifferentialCreditNoteReportDTO>>();
		Connection conn=null;
		String str_Error=null;
		ViewOrderDto objvalidate_returndto=null;
		ResultSet rsValidateBillingDate=null;
		ArrayList<String> errorMsgs=new ArrayList<String>();
		int validationFailure=0;
		try
		{
			if(optionalConnection==null)
			{
				conn = DbConnection.getConnectionObject();
			}
			else
				conn=optionalConnection;
			// added by manisha to calculate initiate date start
			csValidateBillingDate = conn.prepareCall(sqlGet_ValidatebILLINGtRIGGERDate_BULKUPLOAD);
			csValidateBillingDate.setLong(1,fileid);
			rsValidateBillingDate=csValidateBillingDate.executeQuery();
			while(rsValidateBillingDate.next())
			{
				objViewOrderDto=new ViewOrderDto();
				objViewOrderDto.setEnddatelogic(rsValidateBillingDate.getString("ENDDATELOGIC"));
				objViewOrderDto.setBilling_Active_date(rsValidateBillingDate.getString("BILLINGACTIVEDATE"));
				objViewOrderDto.setBilling_End_Date(rsValidateBillingDate.getString("BILLING_END_DATE"));
				objViewOrderDto.setStartdatelogic(rsValidateBillingDate.getString("STARTDATELOGIC"));
				objViewOrderDto.setCharge_info_id(rsValidateBillingDate.getInt("CHARGEINFOID"));
				objViewOrderDto.setServiceProductID(rsValidateBillingDate.getString("SERVICEPRODUCTID"));
				objViewOrderDto.setCom_end_logic(rsValidateBillingDate.getString("COMPONENT_END_LOGIC"));
				objViewOrderDto.setCom_start_logic(rsValidateBillingDate.getString("COMPONENT_START_LOGIC"));
				objViewOrderDto.setSystem_start_date(rsValidateBillingDate.getString("SYSTEM_START_DATE"));
				objViewOrderDto.setSystem_end_date(rsValidateBillingDate.getString("SYSTEM_END_DATE"));
				objViewOrderDto.setComponentinfoid(rsValidateBillingDate.getInt("COMPONENTINFOID"));
				objViewOrderDto.setObject_type(rsValidateBillingDate.getString("OBJECT_TYPE"));
				objViewOrderDto.setLineOldOrNew(rsValidateBillingDate.getString("LINE_OLD_OR_NEW"));
				objViewOrderDto.setIsLineDisconnected(rsValidateBillingDate.getString("ISLINE_DISCONNECTED"));
				//[013] start
				objViewOrderDto.setFx_schedular_create_status(rsValidateBillingDate.getInt("FX_SCHEDULAR_CREATE_STATUS"));
				//[013] end
				arrViewOrderDto.add(objViewOrderDto);
			}	
				if(arrViewOrderDto.size()>0)
				{ 
					for(int i=0;i<arrViewOrderDto.size();i++)
					{
						errorMsgs=new ArrayList<String>();
						objRetViewOrderDto=arrViewOrderDto.get(i);
						if(hmap_SPID_BTDate.containsKey(objRetViewOrderDto.getServiceProductID()))
						{
							if("OLD".equals(objRetViewOrderDto.getLineOldOrNew()))
							{ 
								if("0".equals(objRetViewOrderDto.getIsLineDisconnected()))
								{ 
									String inputBTDate=hmap_SPID_BTDate.get(objRetViewOrderDto.getServiceProductID());
									str_Error=null;
									if("CHARGES".equals(objRetViewOrderDto.getObject_type()))
									{
										
										//[013] start
										if(objRetViewOrderDto.getFx_schedular_create_status()==3){
											String billing_active_date=objRetViewOrderDto.getBilling_Active_date();
										 	if(sdf.parse(inputBTDate).getTime()<sdf.parse(billing_active_date).getTime())
										 	{
										 		  str_Error="Billing Trigger Date should be Greater Than equal to Charge Active Date: "
														+Utility.showDate_Report(sdf.parse(billing_active_date));
												  
													errorMsgs.add(str_Error
															+" for Line No: "
															+objRetViewOrderDto.getServiceProductID() +" for ChargeInfoId: " + objRetViewOrderDto.getCharge_info_id());
															validationFailure=1;
										 	}
										}
										//[013] End
										
										/* if("TD".equals(objRetViewOrderDto.getEnddatelogic()))
										 {
											  	String billing_active_date=objRetViewOrderDto.getBilling_Active_date();
											 	if(sdf.parse(inputBTDate).getTime()<sdf.parse(billing_active_date).getTime())
											 	{
											 		  str_Error="Billing Trigger Date should be Greater Than equal to Charge Active Date: "
															+Utility.showDate_Report(sdf.parse((billing_active_date)));
													  
														errorMsgs.add(str_Error
																+" for Line No: "
																+objRetViewOrderDto.getServiceProductID() +" for ChargeInfoId: " + objRetViewOrderDto.getCharge_info_id());
																validationFailure=1;
											 	}
										 }
										 else
										 {
											 String billing_active_date=objRetViewOrderDto.getBilling_Active_date();
											 String billing_end_date=objRetViewOrderDto.getBilling_End_Date();
											 	if((sdf.parse(inputBTDate).getTime()<sdf.parse(billing_active_date).getTime())|| (sdf.parse(inputBTDate).getTime()>sdf.parse(billing_end_date).getTime()))
											 	{
											 		str_Error="Billing Trigger Date should be Greater Than equal to  Charge Active Date: "
														+Utility.showDate_Report(sdf.parse(billing_active_date)) + "and less than to Charge Inactive date: " + Utility.showDate_Report(sdf.parse(billing_end_date));
												  
													errorMsgs.add(str_Error
															+" for Line No: "
															+objRetViewOrderDto.getServiceProductID() +" for ChargeInfoId: " + objRetViewOrderDto.getCharge_info_id());
													validationFailure=1;
											 	}
										 }*/
									}
									 
									 else
									 {
										 if(objRetViewOrderDto.getSystem_end_date()==null)
										 {
											 String system_active_date=objRetViewOrderDto.getSystem_start_date();
											 	if(sdf.parse(inputBTDate).getTime()<sdf.parse(system_active_date).getTime())
											 	{
											 		  str_Error="Billing Trigger Date should be Greater Than equal to Component Active Date: "
															+Utility.showDate_Report(sdf.parse(system_active_date));
													  
														errorMsgs.add(str_Error
																+" for Line No: "
																+objRetViewOrderDto.getServiceProductID() +" for Componentid: " + objRetViewOrderDto.getComponentinfoid());
														validationFailure=1;
											 	}
											 
										 }
										 else
										 {
											 String system_active_date=objRetViewOrderDto.getSystem_start_date();
											 String system_end_date=objRetViewOrderDto.getSystem_end_date();
											 	if(sdf.parse(inputBTDate).getTime()<sdf.parse(system_active_date).getTime()|| sdf.parse(inputBTDate).getTime()>sdf.parse(system_end_date).getTime())
											 	{
											 		str_Error="Billing Trigger Date should be Greater Than equal to Component Active Date: "
														+Utility.showDate_Report(sdf.parse(system_active_date)) + " and less than to Component Inactive date: " + Utility.showDate_Report(sdf.parse(system_end_date));
												  
													errorMsgs.add(str_Error
															+" for Line No: "
															+objRetViewOrderDto.getServiceProductID() +" for Componentid: " + objRetViewOrderDto.getComponentinfoid());
													validationFailure=1;
											 	}
										 }
									}
									if(str_Error!=null)
									{
										boolean result_validate=mapvalidate_dto.containsKey(objRetViewOrderDto.getServiceProductID());
										ViewOrderDto errForLine = null;
							 		 	if(result_validate==true)
							 		 	{
							 		 		errForLine=mapvalidate_dto.get(objRetViewOrderDto.getServiceProductID());
							 		 		/*if(objvalidate_returndto.getErrorlog()!=null)
							 		 		{
							 		 			//objRetViewOrderDto.setErrorlog((Utility.fnCheckNull(objvalidate_returndto.getErrorlog()))+" "+str_Error);
							 		 		}
							 		 		else
							 		 		{
							 		 			//objRetViewOrderDto.setErrorlog(str_Error);
							 		 			objvalidate_returndto.set
							 		 		}*/
							 		 		errForLine.getBillingTriggerValidationErrors().addAll(errorMsgs);
							 		 	}
							 		 	else
							 		 	{
							 		 		//objRetViewOrderDto.setErrorlog((Utility.fnCheckNull(objRetViewOrderDto.getErrorlog()))+str_Error);
							 		 		errForLine=new ViewOrderDto();
							 		 		errForLine.setBillingTriggerValidationErrors(errorMsgs);
							 		 		mapvalidate_dto.put(objRetViewOrderDto.getServiceProductID(), errForLine);
							 		 	}
							 		 	
									}
									//mapvalidate_dto.put(objRetViewOrderDto.getServiceProductID(), objRetViewOrderDto);
							  }	
						   }
						 }		
					   }
				    }
					
						objViewDto=new ViewOrderDto();
						if(validationFailure>0)
						{
							objViewDto.setIfAnyFailValidation("FAILURE");
							objViewDto.setBillingTriggerValidationErrors(errorMsgs);
						}
						else
						{
							objViewDto.setIfAnyFailValidation("SUCCESS");
						}
						objViewDto.setMap_validate(mapvalidate_dto);
		}		
		
		catch(Exception ex)
		{
			Utility.LOG(ex+"Billing Trigger Exception Occured at LineItem No : "+objRetViewOrderDto.getServiceProductID()+" in fileid : "+fileid);
			//throw ex;
			
		}
		finally{
			DbConnection.closeResultset(rsValidateBillingDate);
		}
		
		return objViewDto;
	}
	//[011] start
	/**
	 * to fetch non valid lines at the time of Billing Trigger
	 * @author gunjan
	 * @param csvServiceProductIds
	 * @return Arraylist
	 * @throws Exception
	 */
	public ArrayList<ViewOrderDto> fetchNonValidLines(String csvServiceProductIds,Connection inputconn) throws Exception {
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		ViewOrderDto objViewOrderDto=null;
		ArrayList<ViewOrderDto> listViewOrderDto=new ArrayList<ViewOrderDto>();
		String sqlGetNonValidMultipleParrellUpgradeLsi="SELECT" 
													 +" PARALLEL_UPGRADE.PARALLEL_UPGRADE_LSI,OLD_PARELLEL_SERVICE.SUBCHANGETYPEID,OLD_PARELLEL_SERVICE.M6_FX_PROGRESS_STATUS,DISCONN_HIS.SERVICE_PRODUCT_ID"
													 +" FROM IOE.TDISCONNECTION_HISTORY DISCONN_HIS "
													 +" INNER JOIN IOE.TPOSERVICEMASTER NEW_SERVICES ON NEW_SERVICES.SERVICEID= DISCONN_HIS.MAIN_SERVICEID AND NEW_SERVICES.IS_CHANGED_LSI=0" 
													 +" INNER JOIN IOE.TPOMASTER TPOMASTER ON TPOMASTER.ORDERNO=NEW_SERVICES.ORDERNO AND TPOMASTER.ORDERTYPE='New' "
													 +" INNER JOIN IOE.TPOSERVICE_PARALLEL_UPGRADE PARALLEL_UPGRADE ON PARALLEL_UPGRADE.SERVICEID=NEW_SERVICES.SERVICEID"
													 +" INNER JOIN IOE.TPOSERVICEMASTER OLD_PARELLEL_SERVICE  ON OLD_PARELLEL_SERVICE.LOGICAL_SI_NO=PARALLEL_UPGRADE.PARALLEL_UPGRADE_LSI"
													 +" AND OLD_PARELLEL_SERVICE.IS_CHANGED_LSI=0 "
													 +" INNER JOIN IOE.TPOMASTER  MASTER_PARALLEL_ORDER ON MASTER_PARALLEL_ORDER.ORDERNO=OLD_PARELLEL_SERVICE.ORDERNO"
													 /*+" AND( NOT(OLD_PARELLEL_SERVICE.SUBCHANGETYPEID=3 AND OLD_PARELLEL_SERVICE.M6_FX_PROGRESS_STATUS='FX_BT_END' AND"
													 +" MASTER_PARALLEL_ORDER.ACCOUNTID=TPOMASTER.ACCOUNTID) OR OLD_PARELLEL_SERVICE.SUBCHANGETYPEID IS NULL OR OLD_PARELLEL_SERVICE.M6_FX_PROGRESS_STATUS IS NULL)"  
													 */
													 +" AND( NOT(OLD_PARELLEL_SERVICE.SUBCHANGETYPEID=3 AND OLD_PARELLEL_SERVICE.M6_FX_PROGRESS_STATUS='FX_BT_END' "
													 +" ) OR OLD_PARELLEL_SERVICE.SUBCHANGETYPEID IS NULL OR OLD_PARELLEL_SERVICE.M6_FX_PROGRESS_STATUS IS NULL)"  
													 +" WHERE DISCONN_HIS.SERVICE_PRODUCT_ID in("+csvServiceProductIds+")";
				
		try {
			if(inputconn==null){
			conn=DbConnection.getConnectionObject();
			}else{
				conn=inputconn;
			}
			pstmt=conn.prepareStatement(sqlGetNonValidMultipleParrellUpgradeLsi);
			rs=pstmt.executeQuery();
			while(rs.next()){
				objViewOrderDto=new ViewOrderDto();
				objViewOrderDto.setParallelUpgradeLSI(rs.getLong("PARALLEL_UPGRADE_LSI"));
				objViewOrderDto.setSubChangeTypeId(rs.getLong("SUBCHANGETYPEID"));
				objViewOrderDto.setM6FxProgressStatus("M6_FX_PROGRESS_STATUS");
				objViewOrderDto.setServiceLineNo(rs.getLong("SERVICE_PRODUCT_ID"));
				
				listViewOrderDto.add(objViewOrderDto);
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			DbConnection.closeResultset(rs);
			DbConnection.closePreparedStatement(pstmt);
			if(inputconn==null){
				DbConnection.freeConnection(conn);
				}
		}
		return listViewOrderDto;
		
	}
	//[011] end
	
}
