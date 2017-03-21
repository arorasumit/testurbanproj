package com.ibm.ioes.utilities;
//Tag Name Resource Name  Date		  		Description
//[001]	  LAWKUSH	    07-Feb-2013				Alert not generated post COPC approval
//[002]   Gunjan Singla                         Added constants ACTION_SEARCH,ACTION_EXCEL,ACTION_DUMP
//[003]	 Kalpana	11-January-2014			For third party
//[004]  Gunjan Singla 5-Mar-14				Added constants ACTION_ORDER and ACTION_SERVICE
//[005] raveendra      20150403-R1-021203      05-May-2015                 Online Payment fix
//[006] Priya Gupta CSR-20160301-XX-022139 -Auto billing for RR orders & other Enhancements
import org.apache.log4j.Logger;



/**
 * @author Varun
 * @version 1.0 This Class containing common Application constants for
 * 
 */
public interface AppConstants {

	public static final Logger SPT_LOGGER = Logger.getLogger("com.ibm.ioes.scheduler");   //NANCY
	
	public static final Logger IOES_LOGGER = Logger.getLogger("com.ibm.ioes");
	
	public static final String MESSAGE_NAME = "name";
	
	public static final String MESSAGE_ID = "id";
	
	public static final String CALENDER_FORMAT_MMDDYY = "dd/MM/yyyy";
	
	public static final String DATE_FORMAT_PROC = "dd/MM/yyyy";
	public static final String DATE_FORMAT_YYYYMMDD = "yyyy-MM-dd";
	
	public static final String FAILURE = "failure";
	
	public static final String SERVICE_TYPE = "SERVICE_TYPE";
	
	public static final String OPEN = "Open";
	
	public static final String CLOSE = "Close";
	
	public static final String APP_SESSION = "APPSESSION";
	
	public static final String APP_SESSION_USER_INFO = "APPSESSIONUSERINFO";

	public static final String SWITCH_ON="ON";
	
	public static final String SWITCH_OFF="OFF";
	
	public static final long one_hour_millisec=1*60*1000;//REMOVE 60*60*1000
	//added by [004]
	public static final long half_hour_millisec=30*60*1000;
	
	public static final long min_5_millisec=5*60*1000;//REMOVE 5*60*1000

	public static final String SWITCH_JOB_M6_XML_SEND = "SWITCH_JOB_M6_XML_SEND";

	public static final String SWITCH_JOB_M6_XML_PROCESS = "SWITCH_JOB_M6_XML_PROCESS";
	
	public static final String SWITCH_LISTENER_M6_XML_RECEIVE = "SWITCH_LISTENER_M6_XML_RECEIVE";

	public static final long SERVER_START_DELAY = 1*60*1000;//REMOVE 5*60*1000
	
	public static final String MAIN_TAB = "1";
	 
	public static final String CONATCT_TAB = "2";
	
	public static final String PODETAIL_TAB = "3";
	
	public static final String PROD_CAT_SERVICE_SUMMARY_TAB = "4";

	public static final String PROD_CAT_BILLING_INFO_TAB = "5";

	public static final String PROD_CAT_CHARGE_TAB = "8";

	public static final String PROD_SERVICE_LOCATION_TAB = "7";

	public static final String PROD_HARDWARE_INFO_TAB = "6";
	
	
	 


/*****START********worksheet Ids for product catelog excel template**************************/
public static final String prdCatelog_Service_Summary = "Service_Summary";

public static final String prdCatelog_Billing_Information_Address = "Billing_Information_Address";

public static final String prdCatelog_Hardware_Info = "Hardware_Info";

public static final String prdCatelog_ServiceLocation_Details = "ServiceLocation_Details";

public static final String prdCatelog_Charges_Details = "Charges_Details";

/*****END********worksheet Ids for product catelog excel template**************************/


public static final String UploadPrdCatelogExcelTemplateFileName="UploadExcelTemplate_PrdCatelog.xls";

public static final String UploadPrdCatelogExcelTemplateFilePath=Messages.getMessageValue("templateDirPath")+UploadPrdCatelogExcelTemplateFileName;

public static final int loadExcelProductConfig_status_Success=1;
public static final int loadExcelProductConfig_status_ValidationError=-1;
public static final int loadExcelProductConfig_status_InsertionError=-2;

	//
	public static final int Billing_Trigger_Status_NoData=0;
	public static final int Billing_Trigger_Status_RENTER_DATA_FROM_USER=8;//for primary table and hence sec table also
	public static final int Billing_Trigger_Status_DATA_IN_PRIMARY_TABLES_SAVED=10;
	public static final int Billing_Trigger_Status_DATA_IN_SEC_TABLES=20;
	public static final int Billing_Trigger_Status_FX_FAILURE_LOCAL_BEAN_READ=24;
	public static final int Billing_Trigger_Status_FX_FAILURE_API=25;
	public static final int Billing_Trigger_Status_FX_SUCCESS=30;
	
	public static final String ProductType_Service="Service";
	public static final String ProductType_Hardware="Hardware";
	public static final String ProductType_Bandwidth="Bandwidth";
	
	
	
	public static final String AM_ROLE="1";
	public static final String PM_ROLE="2";
	public static final String COPC_ROLE="3";
	public static final String SED_ROLE="4";
	
	
	/*   NPD Constancts
	 * 
	 */
	public static final String APP_NAME = "NPD";
	public static final Integer TRUE = new Integer(1);
	public static final Integer FALSE = new Integer(0);
	public static final String ACTIVE = "Y";
	public static final String INACTIVE = "N";
	public static final String CHANGE_PASS = "changepwd";
	public static final String LOGINBEAN = "LoginBean";
	public static final int INACTIVE_FLAG = 0;
	public static final int ACTIVE_FLAG = 1;
	public static final int DRAFT_FLAG = 2;
	public static final String DATE_FORMAT = "M-dd-yyyy";
	public static final String TIME_FORMAT = "HH:mm";
	public static final String DATE_FORMAT_MMDDYY = "MM/dd/yyyy";
	public static final String DATE_FORMAT_FILE = "ddMMyyyy";
	public static final String DEFAULT_PROPFILE = "DirPath.properties";
	public static final String EMAIL_SEPRATOR = ",";
	public static final String ENCRYPTION_KEY = GetPropertiesUtility
	.getProperty("forecast.encryptionKey");
	public static final String ERRORPAGE = "ErrorPageAction";
	public static final Logger NPDLOGGER = Logger.getLogger("com.ibm.ioes.npd");
	public static final String PROP_FILE = "DirPath";
	public static final String SUCCESS = "success";
	public static final String MEETING_TYPE_CFT = "CFT";
	public static final String MEETING_TYPE_NPSC = "NPSC";
	public static final String MANDATORY_LIST_TYPE = "M";
	public static final String OPTIONAL_LIST_TYPE = "O";
	public static final String INI_VALUE = "";
	public static final String SUBJECT_MEETINGSCHEDULE = "Subject.MeetingSchedule";
	public static final String MSG_MEETINGSCHEDULE = "message.MeetingSchedule";
	public static final String MSG_MEETINGSCHEDULE1 = "message.MeetingSchedule1";
	public static final String MSG_MEETINGSCHEDULE_SMS = "message.MeetingSchedule_SMS";
	public static final String TASK_ASSIGNED = "message.TaskAssigned";
	public static final String RECORD_SAVE_SUCCESS = "recordInsertUpdateSuccess";
	public static final String RECORD_SAVE_FAILURE = "recordInsertFailure";
	public static final String RECORD_UPDATE_SUCCESS = "recordUpdateSuccess";
	public static final String RECORD_UPDATE_FAILURE = "recordUpdateFailure";
	public static final String SUBJECT_MEETINGMOM = "Subject.MeetingMOM";
	public static final String MSG_MEETINGMOM = "message.MeetingMOM";
	public static final String SELECT_OPTION = "-1";
	public static final String NO_RECORD = "noRecordExists";
	public static final String NO_PREVIOUS_OPTION = "0";
	public static final int FILE_SIZE=10485760;
	public static final String EXCEEDED_FILE_SIZE = "filesize.exceed";
	public static final int TASK_CLOSED_STATUS=2;
	public static final String TYPE = "1";
	public static final String ATTACH_NEW = "attachNew";
	public static final String EDITING_FINALIZED = "editingFinalized";
	public static final String ADMIN_USERID="50";
	public static final String SUBJECT_TASKASSIGNED = "Subject.TaskAssigned";
	public static final String INITIAL_SERVLET_REQUESTED = "INITIAL_SERVLET_REQUESTED";
	public static final String SAME_DATA = "SAMEDATA";
	public static final String PROJECT_MANAGER = "Product Manager";
	public static final String TECH_HEAD = "Technical Head";
	public static final String TASK_MAX_ASSIGNED_DAYS = "10";
	public static final String MY_TO_DO_LIST_FIRST_LEVEL_ESCALATION = "1";
	public static final String MY_TO_DO_LIST_SECOND_LEVEL_ESCALATION = "7";
	public static final String PLR_MAIL_FOR_DOC = "1";
	public static final String PLR_FIRST_LEVEL_ESCALATION = "4";
	public static final String PLR_SECOND_LEVEL_ESCALATION = "10";
	public static final String TIMEPICKER_FORMAT = "h:mm a";
	public static final String Type_of_employee_new="NEW";
	public static final String Type_of_employee_old="OLD";
	public static final String ESCALATION_LEVEL_1="LEVEL1ESC";
	public static final String ESCALATION_LEVEL_2="LEVEL2ESC";
	public static final String RECORD_DELETE_SUCCESS = "recordDeleteSuccess";
	public static final String UploadExcelPlanTemplateFileName="UploadExcelPlanTemplate.xls"; 
	public static final String UploadExcelPlanTemplateFilePath=Messages.getMessageValue("TEMPLATE_DIR_PATH") + AppConstants.UploadExcelPlanTemplateFileName;
	public static final String NPD_USERS_SESSION_NAME = "UserBean";
	public static final String DATE_TIME_FORMAT = "dd-MMM-yyyy HH:mm:ss a";
	public static final String APPCONTEXTPATH = "MenuContextPath";
	public static final String SSFID="SSFID";
	//priya 
	public static final String ARCHIVED_FAILED = "archivefailed";
	/*   NPD Constancts
	 *   =======================================
	 */
	
	public static final String ACCOUNTMANAGER = "ACCOUNT_MANAGER";
	public static final String PROJECTMANAGER = "PROJECT_MANAGER";
	

	public static final String TSTATE_MASTER = "TSTATE_MASTER";
	public static final String TCITY_MASTER = "TCITY_MASTER";
	public static final String TQUOTES_MASTER = "TQUOTES_MASTER";
	public static final String TM_ACCOUNTROLE = "TM_ACCOUNTROLE";
	public static final String BCP_ADDRESS = "BCP_ADDRESS";
	public static final String TM_ACCOUNTROLEDETAILS = "TM_ACCOUNTROLEDETAILS";
	public static final String TM_ACCOUNT= "TM_ACCOUNT";
	public static final String DISPATCH_ADDRESS = "DISPATCH_ADDRESS";
	public static final String NETWORK_LOCATION = "NETWORK_LOCATION";
	public static final String GAMDETAILS = "GAMDETAILS";
	public static final String TGAM_MASTER = "TGAM_MASTER";
	public static final String TGAM_QUOTES_MASTER = "TGAM_QUOTES_MASTER";
	public static final String TOPPORTUNITY_MASTER = "TOPPORTUNITY_MASTER";
	public static final String TPRODUCTDDVALUES = "TPRODUCTDDVALUES";
	public static final String TM_ACCROLEDETAILS_LEVEL = "TM_ACCROLEDETAILS_LEVEL";
	
	public static final String FETCH_LEVEL_GUI = "GUI";
	public static final String FETCH_LEVEL_CLEP = "CLEP";
	public static final String FX_IB2B_CURRENCY = "CURRENCY";
	
	
	
	/*   
	 * 		Mail Constants
	 *   =======================================
	 */
	public static final String NEWORDERTEMPLATE = "VALIDATENEWORDER";
	public static final String SAVEACTIONMAIL = "SAVEACTIONMAIL";
	public static final String SAVEACTIONMAILONREJECTION = "SAVEACTIONMAILONREJECTION";
//Start[001]
	public static final String SAVEACTIONMAILONREJECTION_CHANGE = "SAVEACTIONMAILONREJECTION_CHANGE";
	final public static String NEWMAILTEMPLATE="NEWMAILTEMPLATE";
	final public static String NEWMAILTEMPLATE_CHANGE="NEWMAILTEMPLATE_CHANGE";
	final public static String PIEMAILTEMPLATE="PIEMAILTEMPLATE";
//	End[001]	
	public static final String IB2B_DB_CONNECTION_LOGGER ="IB2B_DB_CONNECTION_LOGGER";

	public static final String ApplicationFlags = "ApplicationFlags";
	
	public static final String Product_Bill_Type_Usage = "USAGE";
	
	public static final String Product_Bill_Type_Flat = "FLAT";
	
	public static final String ORDERS_FOR_NO_BILL_ACCOUNTS = "ORDERS_FOR_NO_BILL_ACCOUNTS";
	
	public static final Integer FX_TEST_MARKET_CODE = 999;
	
	public static final String BaseDirPathForSavingMail = Messages.getMessageValue("TEMPORARY_FILE_DIR_PATH");

	/******************************************CLEP CONSTANTS DECLARED***************************************************************/
	public static final String SWITCH_JOB_CLEP_XML_SEND = "SWITCH_JOB_CLEP_XML_SEND";

	public static final String SWITCH_JOB_CLEP_XML_PROCESS = "SWITCH_JOB_CLEP_XML_PROCESS";

	public static final String SWITCH_LISTENER_CLEP_XML_RECEIVE = "SWITCH_LISTENER_CLEP_XML_RECEIVE";
	
	public static final String CLEPQHOST = "CLEPQHOST";
	public static final String CLEPQCHANNEL = "CLEPQCHANNEL";
	public static final String CLEPQPORT = "CLEPQPORT";
	public static final String CLEPQREQ = "CLEPQREQ";
	public static final String CLEPQRESP = "CLEPQRESP";
	public static final String CLEPQMNGR = "CLEPQMNGR";
	
	public static final String LAST_ROLE_APROVAL_CLEP_SAAS_ERP = "LAST_ROLE_APROVAL_CLEP_SAAS_ERP";

	public static final String CLEP_REQUEST_QUEUE_CONNECTION = "CLEP_REQUEST_QUEUE_CONNECTION";
	public static final String M6_REQUEST_QUEUE_CONNECTION = "M6_REQUEST_QUEUE_CONNECTION";
	public static final String M6_LISTENER_STATE = "M6_LISTENER_STATE";
	public static final String CLEP_LISTENER_STATE = "CLEP_LISTENER_STATE";
	
//  ADDED BY MANISHA START TO SEND MAIL FOR PENDING ORDERS
	public static final String PENDINGORDERTEMPLATE = "SENDACTIONMAILONPENDINGORDER";
//	 added by manisha defect no 15032013010 
	public static final String REASSIGNPMTEMPLATE = "SENDACTIONMAILONREASSIGNEDPM";
 //  ADDED BY MANISHA END
	
	public static final Integer defaultEscalationTime = 10;
	
	public static final String delayReasonUser = "DELAY_REASON_USERS";
	public static final String partialInitiatorRoles = "PARTIAL_INITIATOR";
	public static final String ACTION_SEARCH="VR";
	public static final String ACTION_EXCEL="EE";
	public static final String ACTION_DUMP="DD";
//  ADDED BY Kalpana  FOR Third Party
	public static final String PR_RENEWAL="PR RENEWAL";
	
	public static final String PR_CREATION="PR CREATION";
	
	public static final String PR_CREATE="PR_CREATE";
	
	public static final String PR_INSERTED="PR_INSERTED";
	
	public static final String PR_INSERTED_SUCCESS="SUCCESS";
	
	public static final String PR_INSERTED_FAILURE="FAILURE";
	
	public static final String PR_EI_SUCCESS="EI_SUCCESS";
	
	public static final String PR_EI_FAILURE="EI_FAILURE";
	public static final String NEW_ORDER="NEW";
	public static final String CHANGE_ORDER="CHANGE";
	
	public static final String PR_PROCESSED_SUCCESS="SUCCESS";
	
	public static final String PR_PROCESSED_FAILURE="VALIDATION FAILED";
	
	public static final String PR_SCM_SUCCESS="SCM_SUCCESS";
	
	public static final String PR_SCM_FAILURE="SCM_FAILURE";
	
	public static final String PR_SCM_VALIDATION_FAILURE="SCM_VALIDATION_FAILURE";
	
	public static final String PR_ACTIVE="1";
	
	public static final String PR_INACTIVE="0";

	public static final String PR_HISTORY_TRUE="1";
	
	public static final String PR_REUSE_STATUS="2";
	
	public static final String PR_HISTORY_FALSE="0";
	
	public static final String MESSAGE_NOT_REC="Message not received";
	
	public static final String ORDER_STAGE_COMPLETED="Completed";
	
	public static final String PR_REUSE="PR_REUSE";
	public static final String DATE_FORMAT_DDMMMyyyy = "dd-MMM-yyyy";
	
	public static final String SCM_BLANK_RESPONSE = "BLANK";
	
	
	public static final String ERROR_CODE_FAIL="-1";
	
	public static final String ERROR_CODE_SUCCESS="0";
	
	public static final String SERVICE_DTL_ID_SEPARATOR=",";
	
	public static final String NFA_SEPARATOR="/";
	public static final int Fetch_Status_Found=1;
	public static final int Fetch_Status_NotFound=0;
	public static final int SCM_XML_PROCESSED_FAILURE=2;
	public static final int SCM_XML_PROCESSED_SUCCESS=3;
	public static final String REPUSH_EVENT="REPUSH";
	
	public static final String PR_SCM_REUSE_FAILURE="SCM_REUSE_FAILURE";

	public static final String TP_LINE_ITEMS_APPCONFIG_KEY="THIRD_PARTY_LINE_ITEM_NO";
	
	public static final String SCM_REQUEST_QUEUE_CONNECTION = "SCM_REQUEST_QUEUE_CONNECTION";
	public static final String SCM_LISTENER_STATE = "SCM_LISTENER_STATE";
	
	public static final String CIRCUIT_ID_UPDATE="CircuitID_update";
	
	public static final String OPEARTION_SEPARATOR=",";
	
	public static final String NFA_NUMBER_ATT_DESC_KEY="NFA_NUMBER_ATT_DESC";
	
	public static final String CIRCUIT_ID_ATT_DESC_KEY="CIRCUIT_ID_ATT_DESC";
	public static final int PERMANENT_DISCONNECTION_ORDER_TYPE=3;
	
	public static final int RATE_RENEWAL_ORDER_TYPE=5;
	
	public static final String PERMANENT_DISCONNECTION="Permanent Disconnection";
	
	public static final String RATE_RENEWAL="Rate Renewal";
	
	public static final String ORDER_STAGE_CANCELLED="CANCELLED";
	
	public static final String ORDER_STAGE_M6_CANCELLED="M6_CANCELLED";
	
	public static final String EventTypeId_2222="2222";
	
	public static final String EventTypeId_9999="9999";
	
	public static final String THIRD_PARTY_CHANGE_ORDER_MAIL = "THIRD_PARTY_CHANGE_ORDER_MAIL";
	public static final String REQUEST_SENT_FOR_PR_CREATION="PR_REQUEST_SENT";
	
	public static final String THIRD_PARTY_PR_REQUEST_LINE_XML="THIRD_PARTY_PR_REQUEST_LINE_XML";
	
	public static final String THIRD_PARTY_PR_REQUEST_HEADER_XML="THIRD_PARTY_PR_REQUEST_HEADER_XML";
	
	public static final String THIRD_PARTY_PR_REQUEST_FOOTER_XML="THIRD_PARTY_PR_REQUEST_FOOTER_XML";
	
	public static final String M6_CANCELLED_PR_INACTIVE="M6_CANCELLED_PR_INACTIVE";
	public static final String THIRD_PARTY_PR_REUSE_HEADER_XML="THIRD_PARTY_PR_REUSE_HEADER_XML";
	
	public static final String THIRD_PARTY_PR_REUSE_FOOTER_XML="THIRD_PARTY_PR_REUSE_FOOTER_XML";
	
	public static final String THIRD_PARTY_PR_REUSE_DATA_XML="THIRD_PARTY_PR_REUSE_DATA_XML";
	
	public static final String THIRD_PARTY_PR_CIRCUIT_HEADER_XML="THIRD_PARTY_PR_CIRCUIT_HEADER_XML";
	
	public static final String THIRD_PARTY_PR_CIRCUIT_FOOTER_XML="THIRD_PARTY_PR_CIRCUIT_FOOTER_XML";
	
	public static final String THIRD_PARTY_PR_CIRCUIT_DATA_XML="THIRD_PARTY_PR_CIRCUIT_DATA_XML";
	
	public static final String NFA_NUMBER_UPDATED_SUCCESS="NFA_NUMBER_UPDATION_SUCCESS";
	
	public static final String NFA_NUMBER_UPDATION_FAILURE="NFA_NUMBER_UPDATION_FAILURE";
	
	public static final int STATUS_FOR_EXISTING_SPID=1;
	
	public static final int STATUS_FOR_SUCCESS_XML_CREATION=0;
	
	public static final int STATUS_FOR_FAILURE_XML_CREATION=5;
	
	public static final String REQUEST_FAILED_FOR_PR_CREATION="PR_REQUEST_FAILED";
	
	public static final String XML_NOT_CREATED="XML_NOT_CREATED";
	
	public static final String IS_THIRD_PARTY_ACTIVE="IS_THIRD_PARTY_ACTIVE";
//  ADDED BY Kalpana  FOR Third Party End
	public static final String Deliver_To_Requester_Key="Deliver_To_Requester_Att_Dec";
	public static final String Preparer_Key="Preparer_Att_Dec";	
	public static final int Pr_Resue_Falg=1;//Adeed by Deepak Kumar
	public static final int Create_New_PR_Falg=0; //Added by Deeapk Kumar
/* Some new constants for CLEP */	
	public static final String SOLUTIONCHANGE_ORDER = "SOLUTIONCHANGE";
	public static final String DISCONNECTION_ORDER = "DISCONNECTION";
	public static final String RFBT = "RFBT";
	
	public static final String CLEP_SUCCESS = "SUCCESS";
	public static final String TRUE_VALUE = "True";
	public static final String FALSE_VALUE = "False";
	public static final String FAILED_VALUE = "FAILED";
	public static final String CLEP_ORDER_SOURCE = "2";
	
	public static final String CLEP_NEW_LINE_ADD = "1";
	public static final String CLEP_CHARGE_ADD = "2";
	public static final String CLEP_CHARGE_DIS = "3";
	public static final String CLEP_CHARGE_ADD_AND_DIS = "4";
	public static final String CLEP_CHARGE_ADD_AND_NEW_LINE_ADD = "5";
	public static final String CLEP_CHARGE_DIS_AND_NEW_LINE_ADD = "6";
	public static final String CLEP_CHARGE_ADD_AND_DIS_AND_NEW_LINE_ADD  = "7";
	//Kalpana
	public static final String THIRD_PARTY_STATE="IS_THIRD_PARTY_ACTIVE";
	public static final String CIRCLE_UNIT="CIRCLE_ATTRIBUTE_KEY";//Added by Deepak for Third Party
	public static final String AM  = "AM";
	public static final String PM  = "PM";
	public static final String COPC  = "COPC";
	public static final String SED  = "SED";
	
 /*********************************************************************************************************/
	public static final String ORDER_STAGE_PARTIAL_INITIATED="Partial Initiated";
	public static final String ORDER_STAGE_PARTIAL_PUBLISH="Partial Publish";
	public static final int PUBLISH_READY=1;
	public static final int SERVICE_PRESENT_TRUE=1;
	public static final int SERVICE_PRESENT_FALSE=0;
	public static final int DRAFT_STAGE=0;
	public static final String THIRD_PARTY_LINE_ITEM="THIRD_PARTY_LINE_ITEM_NO";// Added by Deepak Kumar for Third Party
	public static final String BT_SHORT_CODE="BT";
	public static final String SED_EMAIL="SED_EMAIL";
	public static final String SED_PHONE="SED_PHONE";
	//[004]
	public static final String ACTION_ORDER= "O";
	public static final String ACTION_SERVICE= "S";
	
	public static final String BATCH_COUNTER = "Batch_Counter";
	public static final int BATCH_SIZE = 20000;
	public static final int FULL_COPY = 1; 
	public static final int PARTIAL_COPY = 2;
	public static final int DO_NO_COPY = 3;
	
	public static String COPY_SERVICE_FEATURE = "COPY_SERVICE";
	public static String CREATE_ORDER_WITH_EXISTING_FEATURE = "CREATE_ORDER_WITH_EXISTING";	
	public static final int ZONE_NOT_AVAILABLE=-100;
	public static final int REVERSE_MIG_ORDER=-101;
	public static final int MAX_TASKS_IN_QUEUE = 500;

	public static final int HUNDRED_CRORE = 1000000000;

	public static final String CHANNEL_PARTNER_ADMIN = "51194";
	public static final int RESPOSIBILITY_IB2B_ALL = 51423;//51423 on production
	//[006]
	public static final int RR_AUTO_TRIGGER_DOCUMENT_ID = 19;	
}
