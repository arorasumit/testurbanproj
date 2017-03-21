package com.ibm.ioes.dao;

/*Tag Name Resource Name  Date		CSR No			Description
[001]	 SAURABH SINGH	14-MAR-11	00-05422		To Copy services from one Order into Another
[002]	ANIL KUMAR		22-MAR-11					To Fecth Account List with paging and sorting
[004]	SAURABH SINGH	14-MAR-11	00-05422		To Create Partial Publish
[003]	ANIL KUMAR		23-June-11					To Fecth BCP List with paging and sorting
//[00055]	 Ashutosh Singh		05-July-11	CSR00-05422     Document tracking for Diffrent Role
[005]   ASHUTOSH        14-SEP-11   00-05422        To Create currency Change On Change Order and FX Changes

[006]   ASHUTOSH        21-SEP-11   00-05422        To Create SI Transfer  On Change Order and FX Changes
[007]	SAURABH SINGH	10-Nov-11	CSR00-05422		Check For Hardware LineItem , Not Allow More Than 1 per service
[008]	 SAURABH SINGH	27-Dec-2011	CSR00-05422		Restricting User from Selecting Network Location For Non M6 Line Item
[009]	 SAURABH SINGH	28-Dec-2011	CSR00-05422		Showing ServiceId and ServiceName at the top of Product Catelog
[012]	 ASHUTOSH SINGH	19-JUN-2012	CSR00-05422		Validation for Child node 17 parameter should be different Than Existing Child Node
//[013]	 Manisha	    13-Feb-13	00-05422		Send Mail to Act Mgr pm TO REASSIGN pm defect no
 //[014]	 Manisha Garg	14-Mar-2013	CSR00-07480		Select Account to reassign PM defect no 15032013010
[015]	 Anil Kumar	20-Mar-2013			Bypass PO Level check for foreign legal entity of arbor migration data
//[HYPR11042013001]	Kalpana 18-April-2013 display sales phone number
[016]	 Rohit Verma    1-Mar-2013	CSR00-08440	New Interface to show Hardware Lines for Cancelation
//[089]	 Neelesh		24-May-13	CSR00-08463     For Addition of Category
//[017]	 Rohit Verma				CSR-IT-09112    Validation For Parallel Upgrade LSI No
[090]	 Kalpana		13-JUN-13	CBR-CSR-20130404-XX-08714     For M6 Editable fields ,setting isServiceSummReadonly by fetching READONLY in procedure SPGET_SERVICE_ATT_DETAILS
[091]   Santosh         22-Jan, 2024          Addeda method for fetch as parties as target   
[092]    Gunjan 							added method checkCopiedNode for displaying alert while validating change order without saving line
[093]     pankaj thakur    26-nov-14         added method getSpecialChar
[094]   Neha Maheshwari    added service segment description
[215] Raveendra      23-Jan-2015    validation for selected services 
[216]	 Raveendra Kumar	25-Mar-15	20150225-R1-021110     Order transfer rights required on GUI for AM Role
[217]	 Anil Kumar	14-May-15	20141219_R2_020936     Fetch Standard reason, Insert Standard reason and Update Standard reason
[218] Raveendra 06-july-2015 20141219-R2-020936-Billing Efficiency Program_drop2  Auto Billing Trigger
//[219] VIPIN SAHARIA 11th Aug 2015 CBR_20141219-R2-020936-Billing Efficiency Program Drop 3 (BFR 17) Reverting MBIC CC mapping as per L3-VCS mapping
//[220] Priya Gupta CSR-20160301-XX-022139 -Auto billing for RR orders & other Enhancements
[221]   Gunjan Singla  20160219-XX-022117   CBR-ANG bandwidth correction in iB2B and M6
*/
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.ibm.fx.dto.AcctDTO;
import com.ibm.fx.dto.ComponentDTO;
import com.ibm.ioes.beans.NewOrderBean;
import com.ibm.ioes.beans.ViewOrderDto;
import com.ibm.ioes.dao.NewOrderDaoExt.SITransferResult.ResultType;
import com.ibm.ioes.exception.IOESException;
import com.ibm.ioes.forms.ChannelPartnerDto;
import com.ibm.ioes.forms.ChargeComponentDTO;
import com.ibm.ioes.forms.ChargesDetailDto;
import com.ibm.ioes.forms.CommonDTO;
import com.ibm.ioes.forms.ComponentsDto;
import com.ibm.ioes.forms.CurrencyChangeDto;
import com.ibm.ioes.forms.DelayReasonDTO;
import com.ibm.ioes.forms.FieldAttibuteDTO;
import com.ibm.ioes.forms.FieldEnginnerDto;
import com.ibm.ioes.forms.LineItemDTO;
import com.ibm.ioes.forms.M6OrderStatusDto;
import com.ibm.ioes.forms.NewOrderDto;
import com.ibm.ioes.forms.OrderForPendingWithAMDto;
import com.ibm.ioes.forms.OrderHeaderDTO;
import com.ibm.ioes.forms.PagingDto;
import com.ibm.ioes.forms.PoDetailsDTO;
import com.ibm.ioes.forms.ProductCatelogDTO;
import com.ibm.ioes.forms.RoleSectionDetailDTO;
import com.ibm.ioes.forms.SITransferDto;
import com.ibm.ioes.forms.ServiceLineDTO;
import com.ibm.ioes.model.NewOrderModel;
import com.ibm.ioes.model.NewOrderModel.RRAutoTriggerClass;
import com.ibm.ioes.newdesign.dto.ServiceLinkingDTO;
import com.ibm.ioes.newdesign.dto.StandardReason;
import com.ibm.ioes.utilities.AjaxValidation;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.ApplicationFlags;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.IB2BMail;
import com.ibm.ioes.utilities.IB2BMailDto;
import com.ibm.ioes.utilities.Messages;
import com.ibm.ioes.utilities.PagingSorting;
import com.ibm.ioes.utilities.Utility;
public class NewOrderDaoExt extends NewOrderDao {
private static final String sqlspDisconnectAllComponent = "{call IOE.SP_DISCONNECT_ALL_COMPONENTS_IN_CHANGE_ORDER(?,?,?)}";
	private final String sqlValidateBillingLevelForOrder = "call IOE.SP_VALIDATEBILLINGLEVEL_FOR_COMPLETE_ORDER(?,?)";
	private String sqlSp_getAllowedSectionNames  = "{call IOE.SP_GET_SECTIONS_INFO(?)}";
	private String sqlSp_getAllMasters =  "{call IOE.SP_GET_ALL_MASTERS()}";
	private String sp_getAllDropDownAttributes =  "{call IOE.SP_GET_ALL_DROP_DOWN_ATTRIBUTES(?)}";
	private String sp_getDataForEachDropDown = "{call IOE.SP_GET_DATA_FOR_DROP_DOWN(?)}";
	
	private String sqlspGetSubChangeTypeID = "{call IOE.SP_GET_SUB_CHANGE_TYPE_ID(?,?)}";
	private String sqlGetDownTimeClause="{call IOE.SP_GETDOWNTIMECLAUSE(?,?)}";
	
	private String sqlspDeleteCharge = "{call IOE.SP_DISCONNECT_CHARGE_IN_CHANGE_ORDER(?,?,?,?)}";
	private static String sqlGetSITransferDetails ="{call IOE.SPGETVIEWSITRANSFERDETAILS(?,?,?,?,?,?,?,?)}";
	
//Meenakshi : Added for CBR_20120806-XX-07984
	private String sqlspDisconnectProducts = "{call IOE.SP_DISCONNECT_PRODUCTS(?,?,?,?,?)}";
	private String sqlInsertUpdateSIDetails="{call IOE.SP_INSERT_UPDATE_SI_TRANSFER_DETAILS(?,?,?,?)}";
	private String sqlGetSubchangeTypeID = "{call IOE.SP_GET_SUBCHANGE_TYPE_ID(?,?)}";
	
	//Meenakshi : Added for CBR_20120806-XX-07984
	private String sqlspSuspendProducts = "{call IOE.SP_SUSPEND_PRODUCTS(?,?,?,?,?)}";
	private String sqlspResumeProducts = "{call IOE.SP_RESUME_PRODUCTS(?,?,?,?)}";
	private String sqlSpFetchBCPDetails  = "{call IOE.GET_BILLING_INFO(?)}";
	private String sqlSpFetchDispatchDetails  = "{call IOE.GET_DISPATCH_INFO(?)}";
	private String sqlSpGetSITransferBatchDetails = "{call IOE.GET_SI_TRANSFER_BATCH_DETAILS(?)}";
	private String sqlSpgETsItRANSFERdETAILS = "{call IOE.GET_SI_TRANSFER_DETAILS(?)}";
	private String sqlGetLogicalSINumber = "{call IOE.SP_GET_LOGICAL_SINUMBER_FOR_SI_TRANSFER(?)}";
	public static String sqlSaveFileInfo= "{call IOE.SP_INSERT_EXCEL_FILE_INFO(?,?,?,?,?,?,?,?,?)}";
	public static String sqlSaveBillingInfo= "{call IOE.SP_INSERT_EXCEL_BILLING_DATA(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	public static String  sqlSaveChargeInfo= "{call IOE.SP_INSERT_EXCEL_CHARGE_DATA(?,?,?,?,?,?,?,?,?,?,?,?)}";
	public static String sqlSaveServiceLocationInfo= "{call IOE.SP_INSERT_EXCEL_SERVICE_LOCATION_DATA(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	public static String sqlSaveHardwareInfo= "{call IOE.SP_INSERT_EXCEL_HARDWARE_INFO_DATA(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	
	public static String sqlSaveServiceSummaryInfo= "{call IOE.SP_INSERT_EXCEL_SERVICE_SUMMARY_DATA(?,?,?,?,?,?)}";
	public static String sqlGetSITransferProductList="{call IOE.GET_SI_TRANSFER_PRODUCTS_DETAILS(?)}";
	
	public static String sqlSaveFileData= "{call IOE.SP_INSERT_FILE_DATA(?,?,?,?,?,?,?,?)}";
	
	public static String sqlFetchServiceDetails="{call IOE.SP_GET_SERVICE_DETAILS(?)}";//To insert update Service Attribute
	
	//public static String sqlupdateChargesForRenewal= "{call IOE.SP_UPDATE_CHARGES_FOR_RENEWAL(?,?,?,?,?,?)}";
	
	
	public static String sqlupdateChargesForRenewal= "{call IOE.SP_UPDATE_CHARGES_FOR_RENEWAL(?,?,?,?,?,?,?,?,?,?)}";
	
	public static String sqlSpGetRoleWiseFieldMappingForChangeOrder= "{call IOE.GET_FIELD_MAPPING_FOR_CHANGE_ORDER(?,?)}";//To Fetch Attribute Label Values dropDown values
	
	
	public static String sqlSpValidatePODetailForDisconnectionOrders= "{call IOE.SP_VALIDATE_PODETAIL_FOR_DISCONNECTION_ORDERS(?,?)}";//To Validate PO and update Status
	
	public static String sqlGetLogicalSiNumberForSITransfer= "{call IOE.SP_GET_LOGICALSINO_FOR_SI_TRANSFER(?,?,?,?,?)}";//To fetch Product Access
	public static String sqlGetLogicalSiNumberForSITransfer_paging= "{call IOE.SP_GET_LOGICALSINO_FOR_SI_TRANSFER_PAGING(?,?,?,?,?,?,?,?,?)}";//To fetch Product Access
	public static String sqlGetLogicalSiNumberForMBIC= "{call IOE.SP_GET_LSI_NO_FOR_MBIC_TO_CC(?,?)}";
	public static String sqlAttachDettachMBIC_LSI= "{call IOE.SP_ATTACH_DETTACH_MBIC_LSI(?,?,?,?,?)}";
	public static String sqlvalidateMBIC_To_CC= "{call IOE.SP_VALIDATE_MBIC_TO_CC_MAPPING(?,?,?,?,?,?,?)}";
	public static String sqlGet_CC_MBIC_SERVICEID= "{call IOE.SP_GET_MBIC_TO_CC_SERVICEID(?,?,?,?)}";
		// [001] Start	
	private String sqlspGetAllNewOrders = "{call IOE.SP_GET_ORDERS(?)}";
	private String sqlspGetServiceForOrder = "{call IOE.SP_GET_SERVICES_PAGING(?,?,?,?,?)}";
	private String sqlspCopyOrder = "{call IOE.SP_COPY_ORDER(?,?,?,?,?,?,?,?)}";
	// [001] End	

	private String sqlSpFetchPartyDetails  = "{call IOE.SP_GET_PARTY_DETAILS()}";
	private String sqlSpFetchDestinationPartyDetails_Paging  = "{call IOE.SP_GET_PARTY_DETAILS_PAGING(?,?,?,?,?,?,?)}";
//	private String sqlSpFetchPartyDetails_Paging  = "{call IOE.SP_GET_PARTY_DETAILS_PAGING(?,?,?,?,?,?,?)}";
	private String sqlSpFetchPartyDetails_Paging = "{call IOE.SP_GET_PARTY_RESP_SEGMENT_MAPPING(?,?,?,?,?,?,?, ?)}";
	private String sqlSpFetchAccountDetails_Paging  = "{call IOE.GET_ACCOUNT_NO_PAGING(?,?,?,?,?,?,?)}";
	
		private String sqlspGetAccountWithSorting = "{call IOE.SP_GETACCOUNTLIST_SORTING(?,?,?,?,?,?,?,?)}";
		private String sqlspGetAccountWithSortingforreassignpm = "{call IOE.SP_GETACCOUNTLISTFORPM_SORTING(?,?,?,?,?,?,?,?,?)}";
		//[216] proc 
		private String sqlspGetAccountWithSortingforreassignam = "{call IOE.SP_GETACCOUNTLISTFORAM_SORTING(?,?,?,?,?,?,?,?,?)}";
		//Added By Ashutosh for fetching all cust logical si for account specific.
		private String sqlspGetLogicalSIWithSorting = "{call IOE.SP_GET_CUSTLOGICALSI_LIST_SORTING(?,?,?,?,?,?)}";
		//raghu for network pop location
	private String sqlspGetPopNLocation = "{call IOE.GETPOPNLOCATION(?,?,?,?,?,?,?,?)}";
	
	private String sqlsprevertDisconnectCharge = "{call IOE.SP_REVERT_DISCONNECT_CHARGE_IN_CHANGE_ORDER(?,?,?,?)}";
		
		private String sqlGetProductInfoForSITransfer = "{call IOE.SP_GET_PRODUCT_DETAILS(?)}";
		
		private String sqlSpFetchAccountDetails  = "{call IOE.GET_ACCOUNT_NO(?)}";
		
		
		private String sqlSpInsertSITransferData  = "{call IOE.SP_INSERT_SI_TRANSFER_DATA(?,?,?,?,?,?,?,?,?,?,?,?)}";
		private String sqlSpCopyChangeOrderData  = "{call IOE.SP_COPY_CHANGE_ORDER_DATA(?,?,?,?,?,?,?,?,?,?,?,?)}";
		
		private String sqlSpGetAllSPIDsForService  = "{call IOE.SP_GET_SPIDS(?)}";
		
		
		//private String sqlSpCopyServiceToNewOrder  = "{call IOE.SP_COPY_SERVICE_TO_NEW_ORDER(?,?,?,?,?,?)}";
		private String sqlspCopyOrderForSITransfer = "{call IOE.SP_COPY_ORDER_SI_TRANSFER(?,?,?,?,?,?,?)}";
		
		private String sqlspUpdateSITransfer = "{call IOE.SP_UPDATE_SI_TRANSFER_DATA(?,?,?,?,?,?)}";
		// [005] Star
		private String sqlspUpdateFXData = "{call IOE.SP_CALL_FX_FOR_SI_TRANSFER(?,?,?,?,?)}";
		//[005] End
		// Start [004]
		public static String sqlGetServiceNameAndId="{call IOE.SP_GETSERVICE_N_ID(?)}";
		public static String sqlGetSubServiceNameAndId="{call IOE.SP_GETSUBSERVICE_N_ID(?)}";
		public static String sqlinsertUpdatePartialPublish="{call IOE.SP_INSERT_PARTIALPUBLISH(?,?,?,?,?,?,?)}";
		public static String sqlGetlistForPartialPublish="{call IOE.SP_GET_PARTIAL_LIST(?)}";
		public static String sqlGetlistForPartialPublish_dmx="{call IOE.SP_GET_PARTIAL_LIST_DMX(?,?)}";
		public static String sqlspGetServiceListForOrder = "{call IOE.SP_GET_SERVICELIST(?)}";
		//vijay start. add a new procedure based on this proc "IOE.SP_GET_SERVICELIST(?)"
		public static String sqlspGetServiceListForOrder_paging = "{call IOE.SP_GET_SERVICELIST_PAGING(?,?,?,?,?,?)}";
		//vijay end
		
		//currency Change
		//Start
		//public static String sqlGetLogicalSiNumberForCurrencyChange= "{call IOE.SP_GET_LOGICAL_SINO_FOR_CURRENCY_CHANGE_TEST(?,?)}";//To fetch Product Access
		public static String sqlGetLogicalSiNumberForCurrencyChange= "{call IOE.SP_GET_LOGICAL_SINO_FOR_CURRENCY_CHANGE(?,?,?,?,?,?,?,?)}";
		private String sqlSpInsertCurrencyChangeData  = "{call IOE.SP_INSERT_CURRENCY_CHANGE_DATA(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		private String sqlspUpdateCurrencyChange = "{call IOE.SP_UPDATE_CURRENCY_CHANGE_DATA(?,?,?,?,?,?)}";
		private static String sqlGetCurrencyChangeDetails ="{call IOE.SP_GET_VIEWCURRENCYCHANGEDETAILS(?,?,?,?,?,?,?,?)}";
		private String sqlSpGetCurrencyChangeBatchDetails = "{call IOE.SP_GET_CURRENCY_CHANGE_BATCH_DETAILS(?)}";
		private String sqlSpGetCurrencyChangeDetails = "{call IOE.SP_GET_CURRENCY_CHANGE_DETAILS(?)}";
		private String sqlGetSourceInfoForCurrencyChange = "{call IOE.SP_GET_SOURCE_INFO_FOR_CURRENCY_CHANAGE(?)}";
		// [005] Start
		private String sqlspCopyOrderForCurrencyChange = "{call IOE.SP_COPY_ORDER_CURRENCY_CHANGE(?,?,?,?,?,?,?,?)}";
		private String sqlspUpdateCurrencyChangeError = "{call IOE.SP_UPDATE_CURRENCY_CHANGE_ERROR(?,?,?,?,?)}";
		private String sqlspGetSPIDSDetailsForDISConnectOrder = "{call IOE.SP_GET_SPIDS_DETAILS_FOR_CURRENCY_CHANGE(?)}";
		//[005 end]
		//End
		public static String sqlGetTM6SpidNo="{call IOE.SP_GETSPID_TM6_NEWORDER_STATUS(?,?)}";
		public static String sqlspGetServiceListForOrderInM6 = "{call IOE.SP_GET_SERVICELIST_INM6(?,?)}";
		public static String sqlGetTM6ServiceIdNo = "{call IOE.SP_GETSERVICE_TM6_NEWORDER_STATUS(?,?)}";
		//End [004]
		public static String sqlGetServiceInactiveFlagCheck = "{call IOE.SP_GETSERVICE_INACTIVE_FLAG(?)}";
		
		public static String sqlGetOrderDetailsForSearching="{call IOE.SP_GETORDERDETAIL(?)}";
		public static String sqlGetOrderDetailsForLoggedUser="{call IOE.SP_GETORDERDETAIL_FORLOGGEDUSER(?,?)}";
		//start[003]
		private String sqlGetBCPWithSorting = "{call IOE.SP_VIEW_BCPDETAILS(?,?,?,?,?,?,?)}";
		//end[003]
		//	[Start] Added By Ashutosh
		private String sqlGetUploadedList = "{call IOE.SP_GET_UPLOADED_FILES_LIST(?,?)}";
		private String sqlinsertUploadedCHKList = "{call IOE.SP_INSERT_UPLOADED_FILES_CHK_LIST(?,?,?,?)}";
		private String sqlGetALLSelectedFileCHKList = "{call IOE.SP_GET_ALL_SELECTED_FILES_CHK_LIST(?)}";
		private String sqlGetListOfRole = "{call IOE.SP_GET_LIST_OF_ROLES_FROM_WORKFLOW(?)}";
		
		//[End]
		private String sqlspGetComponentWithSorting = "{call IOE.SP_GET_COMPONENTS(?,?,?,?,?,?,?,?)}";
		private String sqlFetchComponentsDetails = "{call IOE.SP_GET_COMPONENTS_INFO(?,?)}";
		private String sqlspGetPackageWithSorting = "{call IOE.SP_GET_PACKAGES(?,?,?,?,?,?,?)}";
		public static String sqlgetValidationDataForComponents= "{call IOE.GET_VALIDATION_DATA_FOR_COMPONENT(?)}";
			private String sqlspDisconnectComponent = "{call IOE.SP_DISCONNECT_COMPONENTS_IN_CHANGE_ORDER(?,?,?,?,?,?,?,?)}";
		//By Saurabh
		private String sqlGetShortCode = "{call IOE.GETSHORTCODE(?)}";
		private String sqlspCompareChildAndParent = "{call IOE.SP_COMPARE_CHILD_FX_PARAMETERS(?,?,?,?)}";
		//private String sqlGetAdditionalNode = "{call IOE.GETADDITIONALNODE(?)}";[012]
		private String sqlPopulateProductList = "{call IOE.GETSERVICEPRODUCTLIST(?)}";
		
		private String sqlPopulatePoListForCopyOrder = "{call IOE.SP_GET_VALIDPO_FOR_COPYORDER(?,?,?)}";
		private String sqlPopulateLicenseCompanyListForCopyOrder = "{call IOE.SP_GET_LICENSECOMPANY_FOR_COPYORDER(?,?)}";
		private String sqlPopulateStoreListForCopyOrder = "{call IOE.SP_GET_STORE_FOR_COPYORDER(?,?)}";
		private String sqlUpdateValuesAfterCopyOrder = "{call IOE.SP_COPY_ORDER_UPDATE_VALUES(?,?,?,?,?,?,?)}";
				
		private String sqlValidateBillingLevel = "{call IOE.SP_VALIDATEBILLINGLEVEL(?)}";
		//End By Saurabh
		//Start[000777]
		private String sqlPopulateLineItem = "{call IOE.SP_GET_LINE_ITEM()}";
		private String sqlPopulateSubLineItem = "{call IOE.SP_GET_SUB_LINE_ITEM()}";
		//End [000777]
		
		//Start [007]
		private String sqlGethardwareCount = "{call IOE.SP_GETHARDWARECOUNT(?)}";
		//End [007]
			public static String sqlGetHardwareDetails="{call IOE.SP_GET_HARDWARE_DETAILS(?)}";
		
			private String sqlNonM6Check = "{call IOE.SP_GETM6FLAG(?)}";
			private String sqlValidatedemodays = "{call IOE.VALIDATE_DEMO_DAYS_FOR_CHANGE_ORDER(?,?)}";

		

//			start by Anil
			public static String sqlGetGAMList= "{call IOE.SP_GETGAMLIST(?,?)}";//To fetch GAM list
			public static String sqlSaveGAMList= "{call IOE.SP_SAVE_GAMLIST(?,?,?,?,?)}";//To save GAM list
			public static String sqldeleteGAMList= "{call IOE.SP_DELETE_GAMLIST(?,?,?,?)}";//To delete GAM list
			public static String sqlValidateGAMList= "{call IOE.SP_VALIDATE_BEFORE_SAVE_GAMLIST(?,?,?,?,?,?,?)}";//To validate GAM list before save
			public static String sqlGetGAMFormulaList= "{call IOE.SP_GETGAMFORMULALIST(?,?)}";//To fetch GAM Formula list
			public static String sqlSaveGAMFormula= "{call IOE.SP_SAVE_GAMFORMULA(?,?,?,?,?)}";//To save GAM Formula
			public static String sqldeleteGAMFormula= "{call IOE.SP_DELETE_GAMFORMULA(?,?,?)}";//To DELEET GAM formula
			public static String sqlGetAttachFormulaStatus= "{call IOE.SP_GET_ATTCHEDFORMULASTATUS(?,?)}";//To get attach GAM formula status
			public static String sqlGetValdOrderNoStatus= "{call IOE.SP_GET_VALIDATE_ORDERNO(?,?)}";//To get valid orderno status
			//end by Anil
			//lawkush Start
			public static String sqlGetGamOrderList= "{call IOE.SP_GET_GAM_ORDER_LIST(?)}";//To fetch ORDER LIST ACCORDING TO GAM 
			public static String sqlGetAllGAMList= "{call IOE.SP_GETALLGAMLIST(?,?,?,?,?,?,?)}";//To fetch all  GAM list and for searching and sorting
			public static String  sqldissociateGAMOrderList = "{call IOE.SP_DISSOCIATE_GAM_ORDERLIST(?,?,?)}";//For dissociating a GAM with particular Order
			public static String  sqlReplaceGAMOrderList = "{call IOE.SP_REPLACE_GAM_ORDERLIST(?,?,?,?)}";//For Replacing a GAM with particular Order with Other GAM
			public static String  sqlGetOrdersAlreadyAttached = "{call IOE.VALIDATE_REPLACE_GAM(?,?)}";//For validating  a GAM whether it attached to target GAM
			public static String sqlGetValdFormulaStatus= "{call IOE.SP_GET_VALIDATE_ATTACHED_FORMULA_ID(?,?)}";//To get whether formula attached with order
			public static String sqlGetGamNFormulaAttachedWithOrderStatus= "{call IOE.SP_GET_VALIDATE_ORDERNO_ATTACHED_WITH_GAM(?,?)}";//To get whether GAM attached with order
			//lawkush End
			
			


			private String sqlGetServiceName = "{call IOE.SP_GETSERVICENAME(?)}";
			
			private String sqlSpFetchBCPDetails_Paging = "{call IOE.GET_BILLING_INFO_PAGING(?,?,?,?,?,?,?)}";
			private String sqlSpDispatchDetails_Paging = "{call IOE.GET_DISPATCH_INFO_PAGING(?,?,?,?,?,?,?)}";
			
			private String sqlGetMplsLineCount= "{call IOE.SP_GET_MPLS_LINECOUNT(?,?,?,?)}";
			
			private String sqlGetDropDownDependentList= "{call IOE.SP_GET_DROPDOWNDEPENDENTLABEL(?,?)}";
			private String sqlGetAllDropDownDependentList= "{call IOE.SP_GET_AllDROPDOWNDEPENDENTLABEL(?)}";
public static String sqlInsertComponentsDetails="call IOE.SP_UPDATE_COMPONENT_INFO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			public static String sqlDeleteComponents="call IOE.SP_DELETE_COMPONENT_DETAILS(?,?,?,?,?)";
			
						private String sqlPopulateFileType = "{call IOE.SP_GET_FILE_TYPE(?)}";

			//add by Anil for CLEP
			private String sqlGetOrderStateForCLEP="{call IOE.SPCLEP_GETORDERDETAILS(?,?)}";
			//end CLEP
			
			public static String sqlGetOrderListPendingwithPM = "{call IOE.GET_ORDER_LIST_PENDING_WITH_PM(?,?,?,?)}";
			//Start [216]
			public static String sqlGetOrderListPendingwithAM = "{call IOE.GET_ORDER_LIST_PENDING_WITH_AM(?,?,?,?,?,?,?,?,?)}";
			//End [216]
			private String sqlGetUsageBasedLineCount= "{call IOE.SP_GET_USAGEBASED_LINECOUNT(?,?,?,?,?)}";
			private String sqlGetisAddSubNodeAllowStatus= "{call IOE.SP_GET_ADDSUBNODEALLOWSTATUS(?,?)}";
			//private String sqlGetPOList= "{call IOE.SP_GET_POLIST(?)}";
//			Changes done in fetching of PO details in procedure for performance tuning
			public static String sqlGetPOList_MODIFIED= "{call IOE.SP_GET_POLIST_MODIFIED(?)}";//To Insert Task Notes
			private String sqlGetConfigValue= "{call IOE.SP_GETCONFIGVALUES(?,?,?)}";
			private String sqlGetBillingMode="{call IOE.SP_GETBILLINGMODE(?)}";
			private String sqlGetConfigAttributeValue= "{call IOE.SP_GET_CONFIG_ATTRIBUTEVALUE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
			private String sqlspGetArborLSIListWithSorting="{call IOE.SP_GET_ARBOR_LSI_LIST(?,?,?,?,?,?,?,?,?,?,?,?)}";
			private String sqlGetCompStartDateLogic="{CALL IOE.SP_GETCOMPSTARTDATELOGIC()}";
			private String sqlGetCompEndDateLogic="{call IOE.SP_GETCOMPENDDATELOGIC()}";
			private String sqlGetAccountUpdateStatusList="{call IOE.SPGET_ACCOUNT_UPDATE_STATUS(?)}";
			private String sqlGetProductCount= "{call IOE.SP_GETPRODUCTCOUNT(?)}";
			private String sqlspGetArborRedirectedLSIsWithSorting="{call IOE.SP_GET_REDIRECTED_ARBOR_LSI_LIST(?)}";
			private String sqlspGetVCS_BridgeLSIListWithSorting="{call IOE.SP_GET_VCS_BRIDGE_LSI_LIST_FOR_ISP_L3MPLS(?,?,?,?,?,?,?)}";
			
			private String sqlspGetContactDetails = "{call IOE.SPGETCONTACTDETAILS_PAGING(?,?,?,?,?,?,?,?,?,?,?,?)}";
			private String sqlFetchRoleWiseSectionDetail = "select * from IOE.ROLE_SECTION_MAPPING where ROLEID = ?";
			private String sqlFetchServiceAttribute = "select * from IOE.TPRODUCTLINEATTMASTER where SERVICEDETAILID = ?";
			private String sqlspCheckCopiedNode = "{call IOE.SP_COMPARE_COPIED_LINE_FOR_CHANGE_ORDER(?,?)}";
			//Nagarjuna for Dispatch Address Validation on copy order
			
			private String sqlGetDispatchAddressCount="{call IOE.SP_COPY_ORDER_DISPATCHADDRESS_CHECK(?,?,?)}";
			private static String sqlGetStandardReasonDetails="{call IOE.SP_VIEW_STANDARDREASON(?,?,?,?)}";
			
			private String sqlGetDispatchWithSorting = "{call IOE.SP_VIEW_DISPATCHDETAILS(?,?,?,?,?,?)}";
			
			public static String sqlPurgingData = "{call IOE.SP_PURGE_TEMP_DATA()}";
			//[220] start
			private static String sqlRateRenewalAutoTriggerServices = "{call IOE.COMPUTE_RATE_RENEWAL_AUTO_TRIGGER_SERVICES(?,?)}";
			private static String sqlCheckValidLsiForChangeOrder="{call IOE.SP_VALIDATE_SELECTED_LSI_FOR_CHANGE_ORDER(?,?,?,?,?,?)}";
	/**
	 * Method to get all data for Masters Download
	 * @param productID
	 * @return
	 */
public HSSFWorkbook downloadMasters(long productID){
		
		String methodName="downloadMasters", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;
		AppConstants.IOES_LOGGER.info(methodName+" method of "+className+" class have been called");
		
		Connection connection =null;
		ArrayList<String> allowedSections = new ArrayList<String>();
		HSSFWorkbook wb = new HSSFWorkbook();
		CallableStatement getAllDropDownAttributes = null;
		CallableStatement getDetailForEachdropDown = null;
		ResultSet rsForAllDropDowns = null;
		ResultSet rsForEachDropDown = null;
		CallableStatement getAllMasters = null;
		ResultSet rsAllMasters= null;
		try {
			connection=DbConnection.getConnectionObject();
			connection.setAutoCommit(false);
			allowedSections = getAllowedSections(productID);
			
			//for Service Summary
			if(allowedSections.contains(new String("SERVICE_SUMMARY")))
			{
				getAllDropDownAttributes = connection.prepareCall(sp_getAllDropDownAttributes);
				getAllDropDownAttributes.setLong(1,productID);
				rsForAllDropDowns = getAllDropDownAttributes.executeQuery();
				while(rsForAllDropDowns.next()){
					
					//makeSheetForServiceSummary(wb,rsForAllDropDowns.getInt("ATTMASTERID"));
					HSSFSheet sheet = wb.createSheet(rsForAllDropDowns.getString("ATTDESCRIPTION") );
					HSSFCellStyle headerCellStyle = wb.createCellStyle();
					HSSFFont boldFont = wb.createFont();
					boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
					headerCellStyle.setFont(boldFont);
					HSSFRow excelRow = null;
			        HSSFCell excelCell = null;
			        excelRow = sheet.createRow(0);
			        excelCell = excelRow.createCell(0);
			        excelCell.setCellStyle(headerCellStyle);
			        excelCell.setCellValue(new HSSFRichTextString(rsForAllDropDowns.getString("ATTDESCRIPTION") ));
			        
			        
			        excelRow = sheet.createRow(1);
			        
			        excelCell = excelRow.createCell(0);
			        excelCell.setCellStyle(headerCellStyle);
			        excelCell.setCellValue(new HSSFRichTextString("ID"));
			        
			        excelCell = excelRow.createCell(1);
			        excelCell.setCellStyle(headerCellStyle);
			        excelCell.setCellValue(new HSSFRichTextString("VALUE"));
			        
					getDetailForEachdropDown = connection.prepareCall(sp_getDataForEachDropDown);
					getDetailForEachdropDown.setLong(1,rsForAllDropDowns.getInt("ATTMASTERID"));
					rsForEachDropDown = getDetailForEachdropDown.executeQuery();
					 int rowNo = 2;
					while(rsForEachDropDown.next()){
						//create a sheet
							excelRow = sheet.createRow(rowNo);
						 	for(int cell = 0 , col= 1; cell <2; cell++,col++ ){
						 		 excelCell = excelRow.createCell(cell);
								 excelCell.setCellValue(new HSSFRichTextString(rsForEachDropDown.getString(col)));
						 	}
						 	rowNo= rowNo+1;
					
				        
					}
				}
			}
			
			
			//for all other Sections
			
			getAllMasters= connection.prepareCall(sqlSp_getAllMasters);
			rsAllMasters = getAllMasters.executeQuery();
			while(rsAllMasters.next()) {
				String sectionName = rsAllMasters.getString("SECTION_NAME");
				if(allowedSections.contains(sectionName)){
					
					String columns =  rsAllMasters.getString("COLUMN_NAMES");
					String[] columnNames = columns.split(",");
					
					HSSFSheet sheet = wb.createSheet(rsAllMasters.getString("MASTER_NAME"));
					HSSFCellStyle headerCellStyle = wb.createCellStyle();
					HSSFFont boldFont = wb.createFont();
					boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
					headerCellStyle.setFont(boldFont);
					HSSFRow excelRow = null;
			        HSSFCell excelCell = null;
			        excelRow = sheet.createRow(0);
			        for (int count = 0;count< columnNames.length;count++){
			        	excelCell = excelRow.createCell(count);
				        excelCell.setCellStyle(headerCellStyle);
				        excelCell.setCellValue(new HSSFRichTextString(columnNames[count]));
				     }
			        PreparedStatement getMasterData = null;
					ResultSet rsMasterData= null;
					getMasterData= connection.prepareStatement(rsAllMasters.getString("QUERY"));
					rsMasterData = getMasterData.executeQuery();
					int rowNo = 1;
					while (rsMasterData.next()) {
						 excelRow = sheet.createRow(rowNo);
						 	for(int cell = 0 , col= 1; cell <columnNames.length; cell++,col++ ){
						 		 excelCell = excelRow.createCell(cell);
						 		 excelCell.setCellValue(new HSSFRichTextString(rsMasterData.getString(col)));
						 	}
						 	rowNo= rowNo+1;
					}
				}
				
		        
		       
			}
			
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				Utility.onEx_LOG_RET_NEW_EX(e1, methodName, className, msg, logToFile, logToConsole);
			}
		}finally
		{
			try {
				DbConnection.closeResultset(rsAllMasters);
				DbConnection .closeResultset(rsForAllDropDowns);
				DbConnection.closeCallableStatement(getAllDropDownAttributes);
				DbConnection.closeCallableStatement(getAllMasters);
				DbConnection.closeCallableStatement(getDetailForEachdropDown);
				DbConnection.closeCallableStatement(getAllMasters);
				DbConnection.freeConnection(connection);
			} catch (Exception e) {
			}
		}
		
		return wb;
	}
/**
 * Method to get all allowed section for this product on Product Catalog Page.
 * @param connection
 * @param productID
 * @return
 */
public ArrayList<String> getAllowedSections(long productID){
	//Nagarjuna
	String methodName="getAllowedSections", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	ArrayList<String> allowedSections = new ArrayList<String>();
	Connection connection = null;
	CallableStatement getAllowedSections = null;
	ResultSet rsAllowedSections= null;
	try {
	
	connection =DbConnection.getConnectionObject();
	getAllowedSections= connection.prepareCall(sqlSp_getAllowedSectionNames);
	getAllowedSections.setLong(1,productID);
	
	
	rsAllowedSections = getAllowedSections.executeQuery();

	while(rsAllowedSections.next()){
		if(rsAllowedSections.getInt("CHARGEINFO") == 1){
			allowedSections.add(new String("CHARGE_INFO"));
		}
		if(rsAllowedSections.getInt("HARDWAREINFO") == 1){
			allowedSections.add(new String("HARDWARE_INFO"));
		}
		if(rsAllowedSections.getInt("BILLINGINFO") == 1){
			allowedSections.add(new String("BILLING_INFO"));
		}
		if(rsAllowedSections.getInt("SERVICESUMMARY") == 1){
			allowedSections.add(new String("SERVICE_SUMMARY"));
		}
		if(rsAllowedSections.getInt("SERVICELOCATION") == 1){
			allowedSections.add(new String("SERVICE_LOCATION"));
		}
	}
	} catch (Exception e) {
		try {
			connection.rollback();
		} catch (SQLException e1) {
			Utility.onEx_LOG_RET_NEW_EX(e1, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}
		Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
	} 
	finally
	{
		try {
			DbConnection.closeResultset(rsAllowedSections);
			DbConnection.closeCallableStatement(getAllowedSections);
			DbConnection.freeConnection(connection);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return allowedSections;
}


public int saveUploadedFileToTemporaryTable(ArrayList<Object[][]>excelData,int productID,String fileName) 
{
	//Nagarjuna
	String methodName="saveUploadedFileToTemporaryTable", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement saveFileInfo =null;
	CallableStatement saveFileData =null;
	
	CallableStatement saveFileBillingInfo =null;

	CallableStatement saveFileHardwareInfo =null;

	CallableStatement saveFileChargeInfo =null;

	CallableStatement saveFileServiceLocation =null;

	CallableStatement saveFileServiceSummary =null;
	ResultSet rs =null;
	int retCode = 0;
	AppConstants.IOES_LOGGER.info(" saveUploadedFileToTemporaryTable() started");
	int chargeID =0;
	int billingID =0;
	int hardwareID =0;
	int serviceLocationID =0;
	
	
	try
	{
		connection=DbConnection.getConnectionObject();
		saveFileInfo= connection.prepareCall(sqlSaveFileInfo);
		saveFileInfo.setString(1,fileName);
		saveFileInfo.setInt(2,productID);
		saveFileInfo.setInt(3,0); //For storing FileId 
		saveFileInfo.setInt(4,0);//insert status
		saveFileInfo.setInt(5,0);//insert status
		saveFileInfo.setInt(6,0);//insert status
		saveFileInfo.setInt(7,0);//insert status
		saveFileInfo.setInt(8,0);//insert status
		saveFileInfo.setInt(9,0);//insert status
		saveFileInfo.execute();
		int count = 0;
		int fileID =saveFileInfo.getInt(3);
		
		int isServiceSummary =saveFileInfo.getInt(4);
		int isBillingInfo =saveFileInfo.getInt(5);
		int isHardwareInfo =saveFileInfo.getInt(6);
		int isChargeInfo =saveFileInfo.getInt(7);
		int isServiceLocation =saveFileInfo.getInt(8);
		int status =saveFileInfo.getInt(9);
		if (status == 1) {
			//failure
			return 1;
		}
		
		
		//ResultSet rsFileServiceSummary =null;
		
		int rowCount = ((Object[][]) excelData.get(0)).length;
			for(int row = 0; row <rowCount;row++) {
				int serviceSummaryID =0;
				if (isServiceSummary == 1) {
					//count++;
					
					Object[][] summaryData = (Object[][]) excelData.get(count);
;					saveFileServiceSummary = connection.prepareCall(sqlSaveServiceSummaryInfo);
rs= fetchServiceSummaryHeaderForExcel(productID);
					ArrayList<String> colNames = new ArrayList<String>();
					while(rs.next()){
						colNames.add(rs.getString("ATTDESCRIPTION"));
					}
					String service_prod_id = summaryData[row][0].toString();
					saveFileServiceSummary.setString(1,service_prod_id);
					saveFileServiceSummary.setString(2, "ID");
					saveFileServiceSummary.setString(3 ,service_prod_id);
					saveFileServiceSummary.setInt(4,fileID );
					saveFileServiceSummary.setInt(5,-1 );
					saveFileServiceSummary.setInt(6,0 );
					
					saveFileServiceSummary.execute();
					serviceSummaryID = saveFileServiceSummary.getInt(5);
					int serviceSummarySaveStatus = saveFileServiceSummary.getInt(6);
					if(serviceSummarySaveStatus !=  0 ){
						break;
						//TODO: do something meaningful;
					}
					for(int index  =0, val = 0 ;index <colNames.size();index++,val++){
						saveFileServiceSummary = connection.prepareCall(sqlSaveServiceSummaryInfo);
						saveFileServiceSummary.setString(1,service_prod_id);
						saveFileServiceSummary.setString(2, colNames.get(val));
						saveFileServiceSummary.setString(3 ,summaryData[row][val+1].toString());
						saveFileServiceSummary.setInt(4,fileID );
						saveFileServiceSummary.setInt(5,serviceSummaryID );
						saveFileServiceSummary.setInt(6,0 );
						
						saveFileServiceSummary.execute();
						serviceSummaryID = saveFileServiceSummary.getInt(5);
						serviceSummarySaveStatus = saveFileServiceSummary.getInt(6);
						if(serviceSummarySaveStatus !=  0 ){
							break;
							//TODO: do something meaningful;
						}
					}
					
				}
				
				if(isBillingInfo == 1){
					Object[][] billingData = (Object[][]) excelData.get(count);
					saveFileBillingInfo = connection.prepareCall(sqlSaveBillingInfo);
//					Saving Billing tab info
					for(int col=1,index = 0;col<=25;col++,index++)
					{
						saveFileBillingInfo.setString(col,  new String(billingData[row][index].toString()));	
					} 
					saveFileBillingInfo.setInt(26, fileID);
					saveFileBillingInfo.setInt(27, 0);
					saveFileBillingInfo.setInt(28, 0);
					saveFileBillingInfo.execute();
					billingID =saveFileBillingInfo.getInt(27);
					int billingSaveStatus =saveFileBillingInfo.getInt(28);
					if (billingSaveStatus != 0){
						//TODO: set some return value.
						break;
					}
				} 
				
				if (isHardwareInfo == 1) {
					count++;
					Object[][] hardwareData = (Object[][]) excelData.get(count);
					
					saveFileHardwareInfo = connection.prepareCall(sqlSaveHardwareInfo);
				for(int col=1,index = 0;col<=13;col++,index++)
					{
					saveFileHardwareInfo.setString(col,  new String(hardwareData[row][index].toString()));	
					} 
					saveFileHardwareInfo.setInt(14, fileID);
					saveFileHardwareInfo.setInt(15, 0);
					saveFileHardwareInfo.setInt(16, 0);
					saveFileHardwareInfo.execute();
					hardwareID =saveFileHardwareInfo.getInt(15);
					int hardwareSaveStatus =saveFileHardwareInfo.getInt(16);
					if (hardwareSaveStatus != 0){
						//TODO: set some return value.
						break;
					}
				}
				if(isServiceLocation == 1) {
					count++;
					
					Object[][] locationData = (Object[][]) excelData.get(count);
					saveFileServiceLocation = connection.prepareCall(sqlSaveServiceLocationInfo);
				for(int col=1,index = 0;col<=25;col++,index++)
					{
					saveFileServiceLocation.setString(col,  new String(locationData[row][index].toString()));	
					} 
						saveFileServiceLocation.setInt(26, fileID);
						saveFileServiceLocation.setInt(27, 0);
						saveFileServiceLocation.setInt(28, 0);
						saveFileServiceLocation.execute();
					serviceLocationID =saveFileServiceLocation.getInt(27);
					int locationSaveStatus =saveFileServiceLocation.getInt(28);
					if (locationSaveStatus != 0){
						//TODO: set some return value.
						break;
					}
				}
				if(isChargeInfo == 1) {
					count++;
					Object[][] chargeData = (Object[][]) excelData.get(count);
					saveFileChargeInfo = connection.prepareCall(sqlSaveChargeInfo);
//					Saving Charge tab info
					for(int col=1,index = 0;col<=9;col++,index++)
					{
						saveFileChargeInfo.setString(col,  new String(chargeData[row][index].toString()));	
					} 
					saveFileChargeInfo.setInt(10, fileID);
					saveFileChargeInfo.setInt(11, 0);
					saveFileChargeInfo.setInt(12, 0);
					saveFileChargeInfo.execute();
					chargeID =saveFileChargeInfo.getInt(11);
					int chargeSaveStatus =saveFileChargeInfo.getInt(12);
					if (chargeSaveStatus != 0){
						//TODO: set some return value.
						break;
					}
				}
				count= 0;
					// save other Tabs here
				//saving file data	
				saveFileData = connection.prepareCall(sqlSaveFileData);
				
				saveFileData.setInt(1,fileID);
				saveFileData.setString(2,""+productID);
				saveFileData.setInt(3,billingID); // Billing ID
				saveFileData.setInt(4,hardwareID); // Hardware ID
				saveFileData.setInt(5,serviceLocationID); // SERVICE_LOCATION_INFO_ID
				saveFileData.setInt(6,chargeID); // CHARGES_INFO_ID
				saveFileData.setInt(7,serviceSummaryID); // SERVICE_SUMMARY_ID
				saveFileData.setInt(8,0); // out param
				saveFileData.execute();
				
				int dataSaveStatus =saveFileData.getInt(8);
				if (dataSaveStatus == 1) {
					//failure
					//TODO : return something meaningful
					return 1;
				}	
			}
	//	}
		
		
		
		//saveFileData= connection.prepareCall(sqlSaveFileData);
		//saveFileInfo.setInt(1,productID);
		//saveFileInfo.setInt(3,0); //For storing FileId 
		//saveFileInfo.setInt(4,0);//insert status
		//saveFileInfo.execute();
		
		
		
		
		
		return retCode;
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(saveFileInfo);
			DbConnection.closeCallableStatement(saveFileData);
			DbConnection.closeCallableStatement(saveFileBillingInfo);
			DbConnection.closeCallableStatement(saveFileHardwareInfo);
			DbConnection.closeCallableStatement(saveFileChargeInfo);
			DbConnection.closeCallableStatement(saveFileServiceLocation);
			DbConnection.closeCallableStatement(saveFileServiceSummary);
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return retCode;
}
public ResultSet fetchServiceSummaryHeaderForExcel(int productID){
	//Nagarjuna
	String methodName="fetchServiceSummaryHeaderForExcel", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	ResultSet rs = null;
	Connection con=null;
	try {
		con = DbConnection.getConnectionObject();
		rs = fetchServiceSummaryHeaderForExcel(con, new Long(productID).toString());
	} catch (Exception e) {
		// TODO Auto-generated catch block
		//e.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
	}finally
	{
		try {
			DbConnection.freeConnection(con);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}
	}
	
	return rs;
}



public ArrayList<NewOrderDto> getServiceData(NewOrderDto objDto)  throws Exception
{
	//Nagarjuna
	String methodName="getServiceData", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement clbStmt =null;
	NewOrderDto objNewOrderDto = null;
	ArrayList<NewOrderDto> retList = new ArrayList<NewOrderDto>();
	//NewOrderDto listServiceTypes = new NewOrderDto();
	ResultSet rs = null;
	try
	{
		connection=DbConnection.getConnectionObject();
		clbStmt= connection.prepareCall(sqlFetchServiceDetails);
		clbStmt.setLong(1,objDto.getServiceId());
		rs = clbStmt.executeQuery();
		while (rs.next()) 
		{
			objNewOrderDto =  new NewOrderDto();
			//objNewOrderDto.setServiceDetailID(rs.getInt("SERVICEDETAILID"));
			objNewOrderDto.setServiceDetDescription(rs.getString("SERVICESUBTYPENAME"));
			objNewOrderDto.setServiceName(rs.getString("SERVICEDETDESCRIPTION"));
			objNewOrderDto.setServiceTypeId(rs.getInt("servicetypeid"));
			objNewOrderDto.setServiceId(objDto.getServiceId());
			retList.add(objNewOrderDto);
		}
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
	try{
		DbConnection.closeResultset(rs);
		DbConnection.closeCallableStatement(clbStmt);
		DbConnection.freeConnection(connection);
		} 
	catch (SQLException e) 
	{
		//e.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
	}
	}
	return retList;
}


/*public int updateChargesForRenewal(ArrayList<ChargesDetailDto> objDtoList) throws Exception 
{
	Connection connection =null;
	CallableStatement callstmt =null;
	ResultSet rs = null;
	boolean isUpdateSuccess = true;
	//SimpleDateFormat simpleDateFormat = new SimpleDateFormat(AppConstants.DATE_FORMAT_PROC);
	try
	{
		connection=DbConnection.getConnectionObject();
		connection.setAutoCommit(false);
		for(int count =0;count < objDtoList.size();count++){
			ChargesDetailDto objDto = objDtoList.get(count);
			callstmt= connection.prepareCall(sqlupdateChargesForRenewal_1);	
			callstmt.setLong(1, objDto.getOrderNo());
			callstmt.setLong(2, objDto.getServiceProductID());
			callstmt.setLong(3, objDto.getChargeInfoID());
			callstmt.setLong(5, objDto.getNewChargeAmount());
			callstmt.setString(4, objDto.getEffectiveDateForRenewal());
			callstmt.setString(6, objDto.getReasonForRenewal());
			//callstmt.setLong(7, objDto.getServiceID());
			callstmt.setLong(7, 9); //OutParam
			callstmt.setLong(8, 9); //OutParam
			callstmt.setString(9, ""); //OutParam
			callstmt.execute();
			int err = callstmt.getInt(7);
			if(err != 0){
				isUpdateSuccess = false;
				break;
			}
		}
		if(isUpdateSuccess)
		{
			//objRetDto.setServiceId(callstmt.getInt(5));
			connection.commit();
		} else {
			connection.rollback();
		}
		
	}
	catch(Exception ex )
	{
		connection.rollback();
		ex.printStackTrace();	
	}
	finally
	{
		try 
		{
			DbConnection.freeConnection(connection);
			
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	return (isUpdateSuccess)?1:0; 
}
*/
public int updateChargesForRenewal(NewOrderDto objDtoList) throws Exception 
{
	//Nagarjuna
	String methodName="updateChargesForRenewal", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement callstmt =null;
	ResultSet rs = null;
	boolean isUpdateSuccess = true;
	
	ArrayList<ChargesDetailDto> chargesList  =  objDtoList.getChargesDetails();
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(AppConstants.DATE_FORMAT_PROC);
	long previous_spid ;
	long modified_spid;
	long prev_spid =0;
	try
	{
		connection=DbConnection.getConnectionObject();
		connection.setAutoCommit(false);
		previous_spid = chargesList.get(0).getServiceProductID();
		for(int count =0;count < chargesList.size();count++){
			ChargesDetailDto objDto = chargesList.get(count);
			modified_spid = objDto.getServiceProductID();
			callstmt= connection.prepareCall(sqlupdateChargesForRenewal);	
			callstmt.setLong(1, objDto.getOrderNo());
			//callstmt.setLong(2, objDto.getServiceProductID());
			callstmt.setLong(2, objDto.getChargeInfoID());
			callstmt.setLong(3, objDto.getNewChargeAmount());
			callstmt.setTimestamp(4, new Timestamp(simpleDateFormat.parse(objDto.getEffectiveDateForRenewal()).getTime()));
			callstmt.setString(5, objDto.getReasonForRenewal());
			//callstmt.setLong(7, objDto.getServiceID());
			//------------- FOR multiple charges---------
			// 6th param  indicates wether a new SPID needs to be created or not.
			// 0 says dont create. 1 says create.
			if(count >0){
				if(modified_spid == previous_spid){
					callstmt.setInt(6, 0);
					callstmt.setLong(7, new Long(prev_spid)); 
				}else {
					callstmt.setInt(6, 1);
					callstmt.setInt(7, 0); 
				}
			} else if(count == 0){
				callstmt.setInt(6, 1); 
				callstmt.setInt(7, 0); 
			}
			
			////-------------------------------
			callstmt.setInt(8, 9); //OutParam
			callstmt.setInt(9, 9); //OutParam
			callstmt.setString(10, ""); //OutParam
			callstmt.execute();
		
			int err = callstmt.getInt(8);
			String errMsg = callstmt.getString(10);
			if(count == 0) {
				prev_spid = callstmt.getInt(7);
			} else {
				if(modified_spid == previous_spid){
				//	callstmt.setInt(6, 0);
				}else {
					prev_spid = callstmt.getInt(7);
				}
			}
			previous_spid = modified_spid;
			System.out.println(errMsg);
			if(err != 0){
				isUpdateSuccess = false;
				break;
			}
		}
		if(isUpdateSuccess)
		{
			//objRetDto.setServiceId(callstmt.getInt(5));
			connection.commit();
		} else {
			connection.rollback();
		}
		
	}
	catch(Exception ex )
	{
		connection.rollback();
		//ex.printStackTrace();	
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(callstmt);
			DbConnection.freeConnection(connection);
			
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return (isUpdateSuccess)?0:1; 
}

//Change Order Methods for field role Mapping

public ArrayList getFieldValidationForChangeOrder(int role,int subChangeTypeID) 
{
	//Nagarjuna
	String methodName="getFieldValidationForChangeOrder", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	PreparedStatement ps =null;
	ResultSet rs = null;
	NewOrderDto objNewOrderDto = null;
	ArrayList lstDto = new ArrayList();
	
	ArrayList<NewOrderDto> lstContactTab =new ArrayList<NewOrderDto>();
	ArrayList<NewOrderDto> lstPODetailTab =new ArrayList<NewOrderDto>();
	ArrayList<NewOrderDto> lstMainTab =new ArrayList<NewOrderDto>();
	ArrayList<NewOrderDto> lstLinesTab =new ArrayList<NewOrderDto>();
	ArrayList<NewOrderDto> lstServiceSummary =new ArrayList<NewOrderDto>();
	
	try
	{
		connection=DbConnection.getConnectionObject();
		
		ps= connection.prepareCall(sqlSpGetRoleWiseFieldMappingForChangeOrder);
		ps.setInt(1, role);
		ps.setInt(2, subChangeTypeID);
		rs = ps.executeQuery();
		
		while(rs.next())
		{
		 objNewOrderDto =  new NewOrderDto();
		 
		 objNewOrderDto.setFeildName(rs.getString("FIELDNAME"));
		 objNewOrderDto.setIsMand(rs.getString("ISMANDATORY"));
		 objNewOrderDto.setIsReadOnly(rs.getString("ISACTIVE"));
		 objNewOrderDto.setFieldLabel(rs.getString("FIELDLABEL"));

		 if(rs.getString("TABID").equalsIgnoreCase(AppConstants.MAIN_TAB ))
			 lstMainTab.add(objNewOrderDto);			 
		 else
		 if(rs.getString("TABID").equalsIgnoreCase(AppConstants.CONATCT_TAB ))
			 lstContactTab.add(objNewOrderDto);
		 else
		 if(rs.getString("TABID").equalsIgnoreCase(AppConstants.PODETAIL_TAB))
			 lstPODetailTab.add(objNewOrderDto);
		 else
		 if(rs.getString("TABID").equalsIgnoreCase(AppConstants.PROD_CAT_BILLING_INFO_TAB))
			 lstLinesTab.add(objNewOrderDto);
		else
		if(rs.getString("TABID").equalsIgnoreCase(AppConstants.PROD_CAT_CHARGE_TAB ))
			 lstLinesTab.add(objNewOrderDto);
		else
			if(rs.getString("TABID").equalsIgnoreCase(AppConstants.PROD_HARDWARE_INFO_TAB ))
				 lstLinesTab.add(objNewOrderDto);
		else
			if(rs.getString("TABID").equalsIgnoreCase(AppConstants.PROD_SERVICE_LOCATION_TAB))
				 lstLinesTab.add(objNewOrderDto);
		else
			if(rs.getString("TABID").equalsIgnoreCase(AppConstants.PROD_CAT_SERVICE_SUMMARY_TAB))
				lstServiceSummary.add(objNewOrderDto);
				 
			}
		lstDto.add(lstMainTab);
		lstDto.add(lstContactTab);
		lstDto.add(lstPODetailTab);
		lstDto.add(lstLinesTab);
		lstDto.add(lstServiceSummary);
		
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rs);
			DbConnection.closePreparedStatement(ps);
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return lstDto;
}


public int getChangeSubTypeID(NewOrderDto objDto)throws Exception
{
	//Nagarjuna
	String methodName="getChangeSubTypeID", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement callstmt =null;
	ResultSet rs = null;
	int changeSubTypeID=0;
	try
	{
		connection=DbConnection.getConnectionObject();
		connection.setAutoCommit(false);
		callstmt= connection.prepareCall(sqlspGetSubChangeTypeID);	
		callstmt.setLong(1, Long.valueOf(objDto.getServiceProductID()));			
		callstmt.setLong(2, Long.valueOf(objDto.getPoNumber()));
		rs = callstmt.executeQuery();
		while(rs.next())
		{
			changeSubTypeID=rs.getInt("SUBCHANGETYPEID");
		}
	}
	catch(Exception ex )
	{
		connection.rollback();
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(callstmt);
			DbConnection.freeConnection(connection);
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return changeSubTypeID;
}



public int disconnectCharge(String chargeID, String changeOrderNo,String disconnectionType)throws Exception
{
	//Nagarjuna
	String methodName="disconnectCharge", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement callstmt =null;
	int status=0;
	try
	{
		connection=DbConnection.getConnectionObject();
		connection.setAutoCommit(false);
		callstmt= connection.prepareCall(sqlspDeleteCharge);	
		callstmt.setLong(1, Long.valueOf(chargeID));
		callstmt.setLong(2, Long.valueOf(changeOrderNo));
		callstmt.setString(4,disconnectionType);
		callstmt.setNull(3, java.sql.Types.INTEGER);
		callstmt.execute();
		status=callstmt.getInt(3);
		if(status==0){
		connection.commit();
		}
		
	}
	catch(Exception ex )
	{
		connection.rollback();
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeCallableStatement(callstmt);
			DbConnection.freeConnection(connection);
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return status;
}



public int disconnectProducts(String spidList,String orderno,String subChangeType,String serviceid)throws Exception
{
	//Nagarjuna
	String methodName="disconnectProducts", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement callstmt =null;
	CallableStatement getSubChangeType =null;
	int status=0;
	
	//StringBuffer sBuf = new StringBuffer(spidList);
	try
	{
		connection=DbConnection.getConnectionObject();
		connection.setAutoCommit(false);
	//	if(subChangeType.equals("0")) {
			getSubChangeType = connection.prepareCall(sqlGetSubchangeTypeID);
			getSubChangeType.setLong(1,Long.valueOf(serviceid));
			getSubChangeType.setInt(2,0);
			getSubChangeType.execute();
			subChangeType = ""+getSubChangeType.getInt(2);
	//	}
		//Meenakshi : Changed for CBR_20120806-XX-07984
			if(subChangeType.equals("3") || subChangeType.equals("4") || subChangeType.equals("15") || subChangeType.equals("16")){
				callstmt= connection.prepareCall(sqlspDisconnectProducts);	
			}else if(subChangeType.equals("6") || subChangeType.equals("13")) {
				callstmt= connection.prepareCall(sqlspSuspendProducts);	
			}else if(subChangeType.equals("7") || subChangeType.equals("14")) {
				callstmt= connection.prepareCall(sqlspResumeProducts);	
			}
			
		
		
		
		
		//String SPIDs = sBuf.deleteCharAt(spidList.length()-1).toString();
		callstmt.setString(1, spidList);	
		callstmt.setLong(2, Long.valueOf(orderno));	
		callstmt.setLong(3, Long.valueOf(subChangeType));	
		callstmt.setNull(4, java.sql.Types.INTEGER);
		if(subChangeType.equals("6") || subChangeType.equals("13") || subChangeType.equals("3") || subChangeType.equals("4") || subChangeType.equals("15") || subChangeType.equals("16")){
		callstmt.setLong(5, Long.valueOf(serviceid));
		}
		callstmt.execute();
		status=callstmt.getInt(4);
		if(status==0){
		connection.commit();
		}
		
	}
	catch(Exception ex )
	{
		connection.rollback();
		//ex.printStackTrace();	
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			
			DbConnection.closeCallableStatement(callstmt);
			DbConnection.closeCallableStatement(getSubChangeType);
			DbConnection.freeConnection(connection);
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return status;
}
public String ValidateDisconnectionPO(OrderHeaderDTO objDto,Connection optionalConnection) throws Exception 
{
	//Nagarjuna
	String methodName="ValidateDisconnectionPO", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement callstmt =null;
	String retString = "";
	int count = -1;
	try
	{
		//below code add by Anil for CLEP
		if(optionalConnection==null){
			connection=DbConnection.getConnectionObject();
		}else{
			connection=optionalConnection;
		}
		//end CLEP
		
		connection.setAutoCommit(false);
		callstmt= connection.prepareCall(sqlSpValidatePODetailForDisconnectionOrders);	
		callstmt.setLong(1, objDto.getPoNumber());
		callstmt.setInt(2, 0);
		callstmt.execute();
		count = callstmt.getInt(2);
		if(count > 0)
		{
			//retString = "Validate Successfully";
		}else {
			retString = "Validate Failed. Please work on either of Products";
		}
	}
	catch(Exception ex )
	{
		connection.rollback();
		//ex.printStackTrace();	
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeCallableStatement(callstmt);
			if(optionalConnection==null){
				DbConnection.freeConnection(connection);
			}
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return retString;
}
// [001] Start
public ArrayList<OrderHeaderDTO> getAllNewOrders(OrderHeaderDTO objDto) throws Exception
{
	//Nagarjuna
	String methodName="getAllNewOrders", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement callstmt =null;
	ResultSet rs = null;
	ArrayList<OrderHeaderDTO> ordersList = new ArrayList<OrderHeaderDTO>();
	OrderHeaderDTO dto=null;
	//int recordCount=0;
	try
	{
		connection=DbConnection.getConnectionObject();
		connection.setAutoCommit(false);
		callstmt= connection.prepareCall(sqlspGetAllNewOrders);	
		if(objDto.getOrderNo().equals(null) || objDto.getOrderNo().equals("")){
			callstmt.setLong(1, java.sql.Types.INTEGER);
		} 
		else
		{
			callstmt.setLong(1, Long.valueOf(objDto.getOrderNo()));	
		}
		rs = callstmt.executeQuery();
		while(rs.next())
		{
			dto = new OrderHeaderDTO();
			dto.setAccountID(rs.getInt("ACCOUNTID"));
			dto.setOrderNo("" + rs.getInt("ORDERNO"));
			dto.setOrderType(rs.getString("ORDERTYPE"));
			dto.setCurrencyCode(rs.getString("CURNAME"));
			dto.setQuoteNo(rs.getString("QUOTENO"));
			dto.setProjectManager(rs.getString("PROJECTMGR"));
			dto.setAccountManager(rs.getString("ACCOUNTMGR"));
			ordersList.add(dto);
		}
	}
	catch(Exception ex )
	{
		connection.rollback();
		//ex.printStackTrace();	
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(callstmt);
			DbConnection.freeConnection(connection);
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return ordersList;
}
//[001] End
//[001] Start
public ArrayList<ServiceLineDTO> getServicesForTheOrder(OrderHeaderDTO objDto)throws Exception
{
	//Nagarjuna
	String methodName="getServicesForTheOrder", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement callstmt =null;
	ResultSet rs = null;
	ServiceLineDTO dto;// = new NewOrderDto();
	ArrayList<ServiceLineDTO> dtoList = new ArrayList<ServiceLineDTO>();
	int recordCount=0;
	try
	{
		connection=DbConnection.getConnectionObject();
		connection.setAutoCommit(false);
		callstmt= connection.prepareCall(sqlspGetServiceForOrder);	
		callstmt.setLong(1, Long.valueOf(objDto.getOrderNo()));	
		callstmt.setString(2,objDto.getSortBycolumn());
		callstmt.setString(3,objDto.getSortByOrder());
		callstmt.setInt(4,objDto.getStartIndex());
		callstmt.setInt(5,objDto.getEndIndex());
		rs = callstmt.executeQuery();
		while(rs.next())
		{
			dto = new ServiceLineDTO();
			dto.getPagingSorting().setPageRecords(objDto.getPageRecords());
			dto.setServiceId(rs.getInt("SERVICEID"));
			dto.setServiceTypeName(rs.getString("SERVICETYPENAME"));
			dto.setIsDummy(rs.getInt("ISDUMMY"));
			recordCount=rs.getInt("FULL_REC_COUNT");
			dto.getPagingSorting().setRecordCount(recordCount);	
			dto.setMaxPageNo(dto.getPagingSorting().getMaxPageNumber());
			dtoList.add(dto);

			
		}
	}
	catch(Exception ex )
	{
		connection.rollback();
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(callstmt);
			DbConnection.freeConnection(connection);
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return dtoList;
}
//[001] End
//[001] Start
//Vijay 
public String copyOrder(long orderNo, long enteredOrderNo, String serviceList,String serviceProductList,String poList,String licenseCompanyList,String storeList, String noOfCopy, long roleID)throws Exception
{
	//Nagarjuna
	String methodName="copyOrder", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement callstmt =null;
	CallableStatement callstmt1 =null;
	String message =null;
	String mappings = null;
	String errorMsg=null;
	String new_old_spids_mapped[] = null;
	String new_old_spids_fromProc[] = null;
	int status = 0;
	try
	{
		connection=DbConnection.getConnectionObject();
		connection.setAutoCommit(false);
		ArrayList<String> services = new ArrayList<String>();
		
		String gui_old_spids[]=serviceProductList.split(",");
		String poIds[]=poList.split(",");
		String licenseCompanyIds[]=licenseCompanyList.split(",");
		String storeIds[]=storeList.split(",");
		
		HashMap<String,NewOrderDto> hashMap=new HashMap<String, NewOrderDto>();
		if(enteredOrderNo!=orderNo)
		{
			for (int l = 0 ; l<gui_old_spids.length;l++) 
			{
				String key=gui_old_spids[l];
				NewOrderDto dto=new NewOrderDto();
				dto.setPodetailID_String(String.valueOf(poIds[l]));
				dto.setLicCompanyName(String.valueOf(licenseCompanyIds[l]));
				dto.setStore(String.valueOf(storeIds[l]));
				hashMap.put(key, dto);
			}
		}
		
		StringTokenizer st  = new StringTokenizer( serviceList, ",");
		for (int i =0; st.hasMoreTokens();i++) 
		{
			services.add(st.nextToken());
		}
		
		String[] copyNo = noOfCopy.split(",");
		/*
		 * Checking For Dispatch Address Availability based on orders Account ID
		 */
		PreparedStatement psmt=null;
		ResultSet rscout = null;
		int a=0;
		int dispatchCounter=0;
		int noofSevicesFlag=0;
		for(int count=0; count<services.size();count++)
		{
			System.out.println("service are::::"+services.get(count));
			callstmt= connection.prepareCall(sqlGetDispatchAddressCount);
			callstmt.setLong(1, Long.valueOf(services.get(count)));
			callstmt.setLong(2, Long.valueOf(orderNo));
			callstmt.setInt(3, 0);
			callstmt.execute();
			a=callstmt.getInt(3);
			callstmt.close();
				if( a != 0 )
				{
			
			 for(int loop=0; loop<Integer.parseInt(copyNo[count]); loop++){
			callstmt= connection.prepareCall(sqlspCopyOrder);	
			callstmt.setLong(1, Long.valueOf(orderNo));
			callstmt.setLong(2, Long.valueOf(services.get(count)));
			callstmt.setInt(3, 0);
			callstmt.setString(4, "");
			callstmt.setString(5, "");
			callstmt.setString(6, "");
			callstmt.setString(7, "");	
			callstmt.setLong(8, roleID);
			callstmt.execute();
			status=callstmt.getInt(4);
			mappings = callstmt.getString(6);
			errorMsg= callstmt.getString(5);
			//match and update entries for serviceProducts By Saurabh
			if(enteredOrderNo!=orderNo)
			{
				if(mappings!=null || mappings!="")
				{	
					new_old_spids_mapped = mappings.split(",");
					for(int l = 0 ; l<new_old_spids_mapped.length;l++)
					{
						new_old_spids_fromProc=new_old_spids_mapped[l].split("#");
						String oldSpid=new_old_spids_fromProc[1];
						String newSpid=new_old_spids_fromProc[0];
						NewOrderDto dto=new NewOrderDto();
						dto=hashMap.get(oldSpid);
						callstmt1= connection.prepareCall(sqlUpdateValuesAfterCopyOrder);
						callstmt1.setLong(1, Long.valueOf(newSpid));
						callstmt1.setLong(2, Long.valueOf(dto.getPodetailID_String()));
						callstmt1.setLong(3, Long.valueOf(dto.getLicCompanyName()));
						callstmt1.setInt(4, Integer.valueOf(dto.getStore()));
						callstmt1.setInt(5, 0);
						callstmt1.setInt(6, 0);
						callstmt1.setString(7, "");
						callstmt1.execute();
						int err = callstmt1.getInt(6);
						if(err==0)
						{
							message="success";
						}
						else
						{
							message="Some Error Occured Please Try Again";
							System.out.println(callstmt1.getString(7));
							break;
						}
					}
				}
			}
			if(status==-1)
			{
				message=callstmt.getString(6);
				connection.rollback();
				break;
			}
			else
			{
				message="success";
				noofSevicesFlag++;
				
			}
			}
				}else{
						message="Dispatch Address is Not Available to this account..!";
						//System.out.println(callstmt1.getString(7));
						dispatchCounter++;
						//break;
						}
					/*
					 * End of Dispatch Address Availability Check.
					 */
		}
		
		
		if(message.equals("success") || noofSevicesFlag > 0)
		{
			if(dispatchCounter>0){
			message="Some Services has been copied , rest of the services doesn't have Dispatchaddress for Hardwarelineitem";
			}else{
				message="Selected Services has been copied"; 
			}
			
			connection.commit();
		}
	}
	catch(Exception ex )
	{
		message="Some Error Occurred \n Please try again";
		connection.rollback();
		//ex.printStackTrace();	
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			
			DbConnection.closeCallableStatement(callstmt);
			DbConnection.closeCallableStatement(callstmt1);
			DbConnection.freeConnection(connection);
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return message;
}
//[001] End


//start [002]
//[004] Start
//By Saurabh For Populating list of products for partial Publish 
	public ArrayList<ServiceLineDTO> getProductAndId(long getServiceId) 
	{
		//Nagarjuna
		String methodName="getProductAndId", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;
		//end Nagarjuna
		Connection connection =null;
		PreparedStatement getlistDC =null;

		ResultSet rsProdList = null;
		ServiceLineDTO objNewOrderDto = null;
		ArrayList<ServiceLineDTO> listDC = new ArrayList<ServiceLineDTO>();
		try
		{
			connection=DbConnection.getConnectionObject();
			getlistDC= connection.prepareCall(sqlGetServiceNameAndId);
			getlistDC.setLong(1,getServiceId);
			rsProdList = getlistDC.executeQuery();
			while(rsProdList.next())
			{
				objNewOrderDto =  new ServiceLineDTO();
				objNewOrderDto.setServiceTypeId(rsProdList.getInt("SERVICETYPEID"));
				objNewOrderDto.setServiceName(rsProdList.getString("SERVICESTAGE"));
				objNewOrderDto.setProduct_name_for_fx(rsProdList.getString("PRODUCT_NAME_FOR_FX"));
				listDC.add(objNewOrderDto);
			}
			
		}
		catch(Exception ex )
		{
			//ex.printStackTrace();	
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}
		finally
		{
			try 
			{
				DbConnection.closeResultset(rsProdList);
				DbConnection.closePreparedStatement(getlistDC);
				DbConnection.freeConnection(connection);
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				//e.printStackTrace();
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			}
		}
		return listDC;
	}
	//[004] end

//	[004] Start
//	By Saurabh For Populating list of products for partial Publish 
		public ArrayList<ServiceLineDTO> getSubProductAndId(long getServiceTypeId) 
		{
			//Nagarjuna
			String methodName="getSubProductAndId", className=this.getClass().getName(), msg="";
			boolean logToFile=true, logToConsole=true;
			//end Nagarjuna
			Connection connection =null;
			PreparedStatement getlistDC =null;

			ResultSet rsProdList = null;
			ServiceLineDTO objNewOrderDto = null;
			ArrayList<ServiceLineDTO> listDC = new ArrayList<ServiceLineDTO>();
			try
			{
				connection=DbConnection.getConnectionObject();
				getlistDC= connection.prepareCall(sqlGetSubServiceNameAndId);
				getlistDC.setLong(1,getServiceTypeId);
				rsProdList = getlistDC.executeQuery();
				while(rsProdList.next())
				{
					objNewOrderDto =  new ServiceLineDTO();
					objNewOrderDto.setServiceSubtypeId(rsProdList.getInt("SERVICESUBTYPEID"));
					objNewOrderDto.setServiceSubTypeName(rsProdList.getString("SERVICESUBTYPENAME"));
					listDC.add(objNewOrderDto);
				}
				
			}
			catch(Exception ex )
			{
				//ex.printStackTrace();	
				Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			}
			finally
			{
				try 
				{
					DbConnection.closeResultset(rsProdList);
					DbConnection.closePreparedStatement(getlistDC);
					DbConnection.freeConnection(connection);
				} 
				catch (Exception e) 
				{
					// TODO Auto-generated catch block
					//e.printStackTrace();
					Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
				}
			}
			return listDC;
		}
		//[004] end

		//[004] Start
			
		public String savePublish(int serviceId, int serviceTypeId,int serviceSubtypeId,int isPublished)throws Exception
		{
			//Nagarjuna
			String methodName="savePublish", className=this.getClass().getName(), msg="";
			boolean logToFile=true, logToConsole=true;
			//end Nagarjuna
			String message =null;
			int status = 0;
			Connection connection=null;
			CallableStatement cs=null;
			try
			{	
				connection=DbConnection.getConnectionObject();
				connection.setAutoCommit(false);
				cs= connection.prepareCall(sqlinsertUpdatePartialPublish);
				cs.setInt(1, serviceId);
				cs.setInt(2, serviceTypeId);
				cs.setInt(3, serviceSubtypeId);
				if(isPublished==2)
				{
					cs.setInt(4, 0);
				}
				else
				{
					cs.setInt(4, isPublished);
				}
				cs.setString(5, "");
				cs.setInt(6, 0);
				cs.setString(7, "");
				cs.execute();
				status=cs.getInt(5);
				if(status==-1)
				{
					message=cs.getString(7);
					connection.rollback();
					return message;
				}
				else
				{
					message="Data Saved Successfully";
					connection.commit();
				}
							
			}
			catch(Exception ex)
			{
				//ex.printStackTrace();
				Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			}
			finally
			{
				try 
				{
					DbConnection.closeCallableStatement(cs);
					DbConnection.freeConnection(connection);
				} 
				catch (SQLException e) 
				{
					// TODO Auto-generated catch block
					//e.printStackTrace();
					Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
				}		
			}	
			return message;
		}		
		//[004] End
		//[004] Start
		public ArrayList<ServiceLineDTO> getDataForPartialPublish(long getServiceId) 
		{
			//Nagarjuna
			String methodName="getDataForPartialPublish", className=this.getClass().getName(), msg="";
			boolean logToFile=true, logToConsole=true;
			//end Nagarjuna
			Connection connection =null;
			PreparedStatement getlistDC =null;

			ResultSet rsProdList = null;
			ServiceLineDTO objNewOrderDto = null;
			ArrayList<ServiceLineDTO> listDC = new ArrayList<ServiceLineDTO>();
			try
			{
				connection=DbConnection.getConnectionObject();
				getlistDC= connection.prepareCall(sqlGetlistForPartialPublish);
				getlistDC.setLong(1,getServiceId);
				rsProdList = getlistDC.executeQuery();
				while(rsProdList.next())
				{
					objNewOrderDto =  new ServiceLineDTO();
					objNewOrderDto.setServiceTypeId(rsProdList.getInt("PRODUCTID"));
					objNewOrderDto.setServiceSubtypeId(rsProdList.getInt("SUBPRODUCTID"));
				     objNewOrderDto.setRfs_date(rsProdList.getString("RFS_DATE"));
				     objNewOrderDto.setDowntimeClause(rsProdList.getString("DOWNTIME_CLAUSE"));
					if(rsProdList.getInt("ISPUBLISHED")==0)
					{
						objNewOrderDto.setIsPublished(2);
					}
					else
					{
						objNewOrderDto.setIsPublished(rsProdList.getInt("ISPUBLISHED"));
					}
					listDC.add(objNewOrderDto);
				}
				
			}
			catch(Exception ex )
			{
				//ex.printStackTrace();	
				Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			}
			finally
			{
				try 
				{
					DbConnection.closeResultset(rsProdList);
					DbConnection.closePreparedStatement(getlistDC);
					DbConnection.freeConnection(connection);
				} 
				catch (Exception e) 
				{
					// TODO Auto-generated catch block
					//e.printStackTrace();
					Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
				}
			}
			return listDC;
		}
		public String getDataForPartialPublish1(int orderNo) 
		{
			//Nagarjuna
			String methodName="getDataForPartialPublish1", className=this.getClass().getName(), msg="";
			boolean logToFile=true, logToConsole=true;
			//end Nagarjuna
			Connection connection =null;
			CallableStatement getlistDC =null;

			ResultSet rsProdList = null;
			NewOrderDto objNewOrderDto = null;
			String ServiceId=null;
			//ArrayList<NewOrderDto> listDC = new ArrayList<NewOrderDto>();
			try
			{
				connection=DbConnection.getConnectionObject();
				getlistDC= connection.prepareCall(sqlGetlistForPartialPublish_dmx);
				getlistDC.setLong(1,orderNo);
				getlistDC.setString(2,"");
				getlistDC.execute();
				ServiceId=getlistDC.getString(2);
				System.err.println(ServiceId);
				/*while(rsProdList.next())
				{
					objNewOrderDto =  new NewOrderDto();
					objNewOrderDto.setServiceTypeId(rsProdList.getInt("PRODUCTID"));
					objNewOrderDto.setServiceSubtypeId(rsProdList.getInt("SUBPRODUCTID"));
				     objNewOrderDto.setRfs_date(rsProdList.getString("RFS_DATE"));
					if(rsProdList.getInt("ISPUBLISHED")==0)
					{
						objNewOrderDto.setIsPublished(2);
					}
					else
					{
						objNewOrderDto.setIsPublished(rsProdList.getInt("ISPUBLISHED"));
					}
					listDC.add(objNewOrderDto);
				}*/
				
			}
			catch(Exception ex )
			{
				//ex.printStackTrace();	
				Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			}
			finally
			{
				try 
				{
					DbConnection.freeConnection(connection);
				} 
				catch (Exception e) 
				{
					// TODO Auto-generated catch block
					//e.printStackTrace();
					Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
				}
			}
			return ServiceId;
		}
		//[004] End
		
		//[004] Start
		public ArrayList<ServiceLineDTO> getServiceListForTheOrder(OrderHeaderDTO objDto)throws Exception
		{
			//Nagarjuna
				String methodName="getServiceListForTheOrder", className=this.getClass().getName(), msg="";
			boolean logToFile=true, logToConsole=true;
			//end
			Connection connection =null;
			CallableStatement callstmt =null;
			ResultSet rs = null;
			ServiceLineDTO dto;// = new NewOrderDto();
			ArrayList<ServiceLineDTO> dtoList = new ArrayList<ServiceLineDTO>();
			int recordCount = 0;
			try
			{
				connection=DbConnection.getConnectionObject();
				connection.setAutoCommit(false);
				callstmt= connection.prepareCall(sqlspGetServiceListForOrder_paging);	
				callstmt.setLong(1, Long.valueOf(objDto.getOrderNo()));

				callstmt.setString(2,objDto.getSortBycolumn());

				callstmt.setString(3,objDto.getSortByOrder());

				callstmt.setInt(4,objDto.getStartIndex());

				callstmt.setInt(5,objDto.getEndIndex());

				callstmt.setInt(6,1);
				
				rs = callstmt.executeQuery();
				while(rs.next())
				{
					dto = new ServiceLineDTO();
					dto.setServiceName(rs.getString("SERVICESTAGE"));
					dto.setIsPublished(rs.getInt("ISPUBLISHED"));
					dto.setServiceId(rs.getInt("SERVICEID"));
					dto.setDummyServiceId(rs.getInt("DUMMY_SERVICENO"));
					dto.setIsDummyServicePublished(rs.getInt("ISDUMMYSERVICEPUBLISHED"));
					dto.setDummyOrderNo(rs.getInt("DUMMYORDERNO"));
					//dto.setMbicServiceId(rs.getString("MBIC_SERVICE_ID")); //[219] START
					dto.setServiceTypeId(rs.getInt("SERVICETYPEID"));
					//dto.setTgno_Number(rs.getString("TGNO_NUMBER"));
					//dto.setIsMbicLineDummy(rs.getString("IS_MBIC_LINE_DUMMY"));
					//dto.setCc_M6_Progress_status(rs.getString("CC_M6_PROGRESS_STATUS"));
					//vijay start. add paging functionality 
					dto.getPagingSorting().setRecordCount(rs.getInt("FULL_REC_COUNT"));	
					dto.setMaxPageNo(dto.getPagingSorting().getMaxPageNumber());
					//vijay end
					
					dtoList.add(dto);
				}
			}
			catch(Exception ex )
			{
				connection.rollback();
				//ex.printStackTrace();
					Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			}
			finally
			{
				try 
				{
					DbConnection.closeResultset(rs);
					DbConnection.closeCallableStatement(callstmt);
					DbConnection.freeConnection(connection);
				} 
				catch (SQLException e) 
				{
					// TODO Auto-generated catch block
					//e.printStackTrace();
					Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
				}
			}
			return dtoList;
		}
//vijay start 
public ArrayList<ServiceLineDTO> getServiceListForTheOrderWithoutPaging(OrderHeaderDTO objDto)throws Exception
{
			//Nagarjuna
				String methodName="getServiceListForTheOrderWithoutPaging", className=this.getClass().getName(), msg="";
			boolean logToFile=true, logToConsole=true;
			//end
	Connection connection =null;
	CallableStatement callstmt =null;
	ResultSet rs = null;
	ServiceLineDTO dto;// = new NewOrderDto();
	ArrayList<ServiceLineDTO> dtoList = new ArrayList<ServiceLineDTO>();
	int recordCount = 0;
	try
	{
		connection=DbConnection.getConnectionObject();
		connection.setAutoCommit(false);
				callstmt= connection.prepareCall(sqlspGetServiceListForOrder);	
				callstmt.setLong(1, Long.valueOf(objDto.getOrderNo()));			
				rs = callstmt.executeQuery();
				while(rs.next())
				{
					dto = new ServiceLineDTO();
					dto.setServiceName(rs.getString("SERVICESTAGE"));
					dto.setIsPublished(rs.getInt("ISPUBLISHED"));
					dto.setServiceId(rs.getInt("SERVICEID"));
					dto.setDummyServiceId(rs.getInt("DUMMY_SERVICENO"));
					dto.setIsDummyServicePublished(rs.getInt("ISDUMMYSERVICEPUBLISHED"));
					dto.setDummyOrderNo(rs.getInt("DUMMYORDERNO"));
					//dto.setMbicServiceId(rs.getString("MBIC_SERVICE_ID")); //[219] START
					dto.setServiceTypeId(rs.getInt("SERVICETYPEID"));
					//dto.setTgno_Number(rs.getString("TGNO_NUMBER"));
					//dto.setIsMbicLineDummy(rs.getString("IS_MBIC_LINE_DUMMY"));
					//dto.setCc_M6_Progress_status(rs.getString("CC_M6_PROGRESS_STATUS"));
					//[219] END
					dtoList.add(dto);
				}
			}
			catch(Exception ex )
			{
				connection.rollback();
				//ex.printStackTrace();
				Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			}
			finally
			{
				try 
				{
					DbConnection.closeResultset(rs);
					DbConnection.closeCallableStatement(callstmt);
					DbConnection.freeConnection(connection);
				} 
				catch (SQLException e) 
				{
					// TODO Auto-generated catch block
					//e.printStackTrace();
					Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
				}
			}
			return dtoList;
		}
//Vijay end
		
public ArrayList<NewOrderDto> getAccountDetailsWithSorting(PagingDto objDto) 
{
	//Nagarjuna
	String methodName="getAccountDetailsWithSorting", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement getAllAccountsReport =null;
	ResultSet rsAccountDetails = null;
	ArrayList<NewOrderDto> listAccountDetails = new ArrayList<NewOrderDto>();
	NewOrderDto objNewOrderDto = null;
	int recordCount;
	try
	{
		connection=DbConnection.getConnectionObject();
		getAllAccountsReport= connection.prepareCall(sqlspGetAccountWithSorting);
		String searchAcc=objDto.getAccountName();
		String accountId=objDto.getAccountIDString();
		int osp=0;
		
		if(accountId==null || accountId.equalsIgnoreCase("") || "%".equalsIgnoreCase(accountId))
		{
			accountId="0";
		}
		if(searchAcc=="")
		{
			searchAcc=null;
		}
		objDto.getAccountID();
		if(("").equalsIgnoreCase(searchAcc))
		{
			searchAcc=null;
		}
		if(("").equalsIgnoreCase(objDto.getShortCode()))
		{
			objDto.setShortCode(null);
		}
		if(objDto.getOsp()==null || objDto.getOsp().equalsIgnoreCase(""))
		{
			osp=0;
		}
		else
		{
			osp=Integer.parseInt(objDto.getOsp());
		}
		getAllAccountsReport.setString(1,searchAcc);
		getAllAccountsReport.setString(2,accountId );
		getAllAccountsReport.setString(3,objDto.getShortCode());
		getAllAccountsReport.setString(4,objDto.getSortBycolumn());
		getAllAccountsReport.setString(5,objDto.getSortByOrder());
		getAllAccountsReport.setInt(6,objDto.getStartIndex());
		getAllAccountsReport.setInt(7,objDto.getEndIndex());
		getAllAccountsReport.setInt(8,osp);
		rsAccountDetails = getAllAccountsReport.executeQuery();
		while(rsAccountDetails.next())
		{
			objNewOrderDto =  new NewOrderDto();
			objNewOrderDto.setAccountID(rsAccountDetails.getInt("accountID"));
			objNewOrderDto.setAccountName(rsAccountDetails.getString("accountName"));
			objNewOrderDto.setAccphoneNo(rsAccountDetails.getLong("PhoneNo"));
			objNewOrderDto.setLob(rsAccountDetails.getString("LOB"));
            //[094] Start
			objNewOrderDto.setServiceSegment(rsAccountDetails.getString("SERVICESEGMENT"));
            //[094] End
			objNewOrderDto.setCrmAccountId(rsAccountDetails.getInt("CRMACCOUNTNO"));//Added By Ashutosh
			objNewOrderDto.setAccountManager(rsAccountDetails.getString("AccountManager"));
			objNewOrderDto.setProjectManager(rsAccountDetails.getString("ProjManager"));
			objNewOrderDto.setProjectManagerID(rsAccountDetails.getLong("PROJECTMGRID"));
			objNewOrderDto.setSpFirstname(rsAccountDetails.getString("SPFirstname"));
			objNewOrderDto.setSpLastName(rsAccountDetails.getString("SLastName"));
			objNewOrderDto.setAcmgrPhno(rsAccountDetails.getString("acmgrPhone"));//changed by kalpana from long to string for bug id HYPR11042013001
			objNewOrderDto.setAcmgrEmail(rsAccountDetails.getString("acmgrEmail"));
			objNewOrderDto.setSpLPhno(rsAccountDetails.getString("SLPhno"));//changed by kalpana from long to string for bug id HYPR11042013001
			objNewOrderDto.setSpLEmail(rsAccountDetails.getString("emailID"));
			objNewOrderDto.setRegion(rsAccountDetails.getString("Region"));
			objNewOrderDto.setZone(rsAccountDetails.getString("ACCZONE"));
			//LAWKUSH START
			objNewOrderDto.setOsp(rsAccountDetails.getString("OSP"));
			//LAWKUSH END
			//objNewOrderDto.setZoneId(rsAccountDetails.getInt("ZONEID"));
			objNewOrderDto.setM6ShortCode(rsAccountDetails.getString("M6SHORTCODE"));
			objNewOrderDto.setRegionIdNew(rsAccountDetails.getString("REGIONID"));
			objNewOrderDto.setCollectionMgr(rsAccountDetails.getString("CollectionMgr"));
			objNewOrderDto.setCircle(rsAccountDetails.getString("CIRCLE"));//added on 9-jan-2013, Circle work
			objNewOrderDto.setCategory(rsAccountDetails.getString("CATEGORY"));//added on 30-APR-2013
			objNewOrderDto.setGroupName(rsAccountDetails.getString("GROUP_DESC"));
			recordCount=rsAccountDetails.getInt("FULL_REC_COUNT");
			objNewOrderDto.getPagingSorting().setRecordCount(recordCount);	
			objNewOrderDto.setMaxPageNo(objNewOrderDto.getPagingSorting().getMaxPageNumber());
			listAccountDetails.add(objNewOrderDto);			
		}
		return listAccountDetails;
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rsAccountDetails);
			DbConnection.closeCallableStatement(getAllAccountsReport);
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return listAccountDetails;
}
//end [002]
public ArrayList<NewOrderDto> getCustLogicalSIDetailsWithSorting(PagingDto objDto) 
{
	//Nagarjuna
	String methodName="getCustLogicalSIDetailsWithSorting", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement getAllCustLogicalSIList =null;
	ResultSet rsCustLogicalSIDetails = null;
	ArrayList<NewOrderDto> listCustLogicalDetails = new ArrayList<NewOrderDto>();
	NewOrderDto objNewOrderDto = null;
	int recordCount;;
	try
	{
		connection=DbConnection.getConnectionObject();
		getAllCustLogicalSIList= connection.prepareCall(sqlspGetLogicalSIWithSorting);
		String searchCustLSI=objDto.getServiceIdString();
		String custLogicalSINo=objDto.getCustSINo();
		
		if(custLogicalSINo==null || custLogicalSINo.equalsIgnoreCase(""))
		{
			custLogicalSINo="0";
		}
		if(searchCustLSI=="")
		{
			searchCustLSI=null;
		}
		objDto.getAccountID();
		if(searchCustLSI.equalsIgnoreCase(""))
		{
			searchCustLSI=null;
		}
		
		
		getAllCustLogicalSIList.setInt(1,Integer.parseInt(searchCustLSI));
		getAllCustLogicalSIList.setInt(2,Integer.parseInt(custLogicalSINo));		
		getAllCustLogicalSIList.setString(3,objDto.getSortBycolumn());
		getAllCustLogicalSIList.setString(4,objDto.getSortByOrder());
		getAllCustLogicalSIList.setInt(5,objDto.getStartIndex());
		getAllCustLogicalSIList.setInt(6,objDto.getEndIndex());
		rsCustLogicalSIDetails = getAllCustLogicalSIList.executeQuery();
		while(rsCustLogicalSIDetails.next())
		{
			objNewOrderDto =  new NewOrderDto();
			objNewOrderDto.setCustomer_logicalSINumber(rsCustLogicalSIDetails.getInt("CUSTOMER_LOGICAL_SI_NO"));
			objNewOrderDto.setLogicalSINumber(rsCustLogicalSIDetails.getInt("LOGICAL_SI_NO"));
			objNewOrderDto.setServiceId(rsCustLogicalSIDetails.getInt("SERVICEID"));
			recordCount=rsCustLogicalSIDetails.getInt("FULL_REC_COUNT");
			objNewOrderDto.getPagingSorting().setRecordCount(recordCount);	
			objNewOrderDto.setMaxPageNo(objNewOrderDto.getPagingSorting().getMaxPageNumber());
			
			listCustLogicalDetails.add(objNewOrderDto);			
		}
		return listCustLogicalDetails;
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rsCustLogicalSIDetails);
			DbConnection.closeCallableStatement(getAllCustLogicalSIList);
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return listCustLogicalDetails;
}
//Raghu for network pop location
public ArrayList<NewOrderDto> populateNPLocationAddress1(PagingDto objDto) 
{
	//Nagarjuna
	String methodName="populateNPLocationAddress1", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement getAllAccountsReport =null;
	ResultSet rsAccountDetails = null;
	ArrayList<NewOrderDto> listAccountDetails = new ArrayList<NewOrderDto>();
	NewOrderDto objNewOrderDto = null;
	int recordCount;
	try
	{
		connection=DbConnection.getConnectionObject();
		getAllAccountsReport= connection.prepareCall(sqlspGetPopNLocation);
		String PopNetLocName=objDto.getPopNetLocName();
		String PopNetLocCode=objDto.getPopNetLocCode();
		String PopNetLocDesc=objDto.getPopNetLocDesc();
		
		if(PopNetLocCode.equalsIgnoreCase(""))
		{
			PopNetLocCode=null;
		}
		if(PopNetLocName=="")
		{
			PopNetLocName=null;
		}
		if(PopNetLocName.equalsIgnoreCase(""))
		{
			PopNetLocName=null;
		}
		if(PopNetLocDesc.equalsIgnoreCase(""))
		{
			PopNetLocDesc=null;
		}
		
		getAllAccountsReport.setString(1,PopNetLocName);
		getAllAccountsReport.setString(2,PopNetLocCode);
		//getAllAccountsReport.setString(3,objDto.getShortCode());
		getAllAccountsReport.setString(3,objDto.getSortBycolumn());
		getAllAccountsReport.setString(4,objDto.getSortByOrder());
		getAllAccountsReport.setInt(5,objDto.getStartIndex());
		getAllAccountsReport.setInt(6,objDto.getEndIndex());
		getAllAccountsReport.setString(7,objDto.getSearchAlphabet());
		getAllAccountsReport.setString(8,PopNetLocDesc);//parameter Added by Saurabh for fixing training point 28/1/13
		rsAccountDetails = getAllAccountsReport.executeQuery();
		while(rsAccountDetails.next())
		{
			objNewOrderDto =  new NewOrderDto();
			rsAccountDetails.getString("LOCATION_NAME");
			if(rsAccountDetails.getString("LOCATION_NAME")==null)
			{
				objNewOrderDto.setPopNetLocName("");
			}
			else
			{
				objNewOrderDto.setPopNetLocName(rsAccountDetails.getString("LOCATION_NAME"));
			}
			objNewOrderDto.setPopNetLocCode(rsAccountDetails.getString("POP_LOC_CODE"));
			objNewOrderDto.setPopLocationCode(rsAccountDetails.getString("LOCATION_CODE"));
			objNewOrderDto.setPopNetLocDesc(rsAccountDetails.getString("NWKLOCATIONNAME"));
			objNewOrderDto.setPopLocAddress1(rsAccountDetails.getString("ADDRESS1"));
			
			recordCount=rsAccountDetails.getInt("FULL_REC_COUNT");
			objNewOrderDto.getPagingSorting().setRecordCount(recordCount);	
			objNewOrderDto.setMaxPageNo(objNewOrderDto.getPagingSorting().getMaxPageNumber());
			listAccountDetails.add(objNewOrderDto);			
		}
		return listAccountDetails;
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();	
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rsAccountDetails);
			DbConnection.closeCallableStatement(getAllAccountsReport);
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return listAccountDetails;
}

//start [003]
public ArrayList<NewOrderDto> getBCPDetailsWithSorting(PagingDto objDto) 
{
	//Nagarjuna
	String methodName="getBCPDetailsWithSorting", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement getBCPReport =null;
	ResultSet rsBCPDetails = null;
	ArrayList<NewOrderDto> listBCPDetails = new ArrayList<NewOrderDto>();
	NewOrderDto objNewOrderDto = null;
	int recordCount;
	try
	{
		connection=DbConnection.getConnectionObject();
		getBCPReport= connection.prepareCall(sqlGetBCPWithSorting);
		String searchBcpName=objDto.getBcpName();
		String bcpId=String.valueOf(objDto.getBcpID());
		
		if(bcpId==null || bcpId.equalsIgnoreCase(""))
		{
			bcpId="0";
		}
		if(searchBcpName=="")
		{
			searchBcpName=null;
		}					
				
		getBCPReport.setInt(1,objDto.getAccountID());
		getBCPReport.setString(2,"");
		getBCPReport.setInt(3,objDto.getBcpID());		
		getBCPReport.setString(4,objDto.getSortBycolumn());
		getBCPReport.setString(5,objDto.getSortByOrder());
		getBCPReport.setInt(6,objDto.getStartIndex());
		getBCPReport.setInt(7,objDto.getEndIndex());
		rsBCPDetails = getBCPReport.executeQuery();
		while(rsBCPDetails.next())
		{
			objNewOrderDto =  new NewOrderDto();
			objNewOrderDto.setAccountID(rsBCPDetails.getInt("ACCOUNTID"));
			objNewOrderDto.setBcpID(rsBCPDetails.getInt("BCP_ID"));
			objNewOrderDto.setBfirstname(rsBCPDetails.getString("FNAME"));
			objNewOrderDto.setBlastname(rsBCPDetails.getString("LNAME"));
			objNewOrderDto.setBaddress1(rsBCPDetails.getString("ADDRESS1"));
			objNewOrderDto.setBaddress2(rsBCPDetails.getString("ADDRESS2"));
			objNewOrderDto.setBaddress3(rsBCPDetails.getString("ADDRESS3"));
			objNewOrderDto.setBaddress4(rsBCPDetails.getString("ADDRESS4"));
			objNewOrderDto.setBcontactNo(rsBCPDetails.getString("TELEPHONENO"));
			objNewOrderDto.setBemailID(rsBCPDetails.getString("EMAILID"));
			objNewOrderDto.setBpincode(rsBCPDetails.getString("PINCODE"));
			objNewOrderDto.setBcity(rsBCPDetails.getString("CITYNAME"));
			objNewOrderDto.setBstate(rsBCPDetails.getString("STATE"));
			objNewOrderDto.setBcountry(rsBCPDetails.getString("COUNTRY"));
			recordCount=rsBCPDetails.getInt("FULL_REC_COUNT");
			objNewOrderDto.getPagingSorting().setRecordCount(recordCount);	
			objNewOrderDto.setMaxPageNo(objNewOrderDto.getPagingSorting().getMaxPageNumber());
			listBCPDetails.add(objNewOrderDto);			
		}
		return listBCPDetails;
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rsBCPDetails);
			DbConnection.closeCallableStatement(getBCPReport);
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return listBCPDetails;
}


//end[003]

public ArrayList<SITransferDto> fetchPartywithSorting(PagingDto objDto) 
{
	//Nagarjuna
	String methodName="fetchPartywithSorting", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement getPartyDetails =null;
	ResultSet rsPartyDetails = null;
	ArrayList<SITransferDto> listPartyDetails = new ArrayList<SITransferDto>();
	SITransferDto objNewDto = null;
	int recordCount;
	try
	{
		connection=DbConnection.getConnectionObject();
		getPartyDetails= connection.prepareCall(sqlSpFetchPartyDetails_Paging);
		getPartyDetails.setString(1,objDto.getParty_num());
		getPartyDetails.setString(2,objDto.getPartyName());		
		getPartyDetails.setLong(3,objDto.getSourcePartyID());		
		getPartyDetails.setString(4,objDto.getSortBycolumn());
		getPartyDetails.setString(5,objDto.getSortByOrder());
		getPartyDetails.setInt(6,objDto.getStartIndex());
		getPartyDetails.setInt(7,objDto.getEndIndex());
		getPartyDetails.setInt(8, objDto.getRespId());
		AppConstants.IOES_LOGGER.info("##################  LOB from Object  :  "+objDto.getRespId()+"       ##############################");
		rsPartyDetails = getPartyDetails.executeQuery();
		while(rsPartyDetails.next())
		{
			objNewDto =  new SITransferDto();
			objNewDto.setSourcePartyNo(rsPartyDetails.getString("PARTY_NO"));
			objNewDto.setSourcePartyName(rsPartyDetails.getString("PARTYNAME"));
			recordCount=rsPartyDetails.getInt("FULL_REC_COUNT");
			objNewDto.getPagingSorting().setRecordCount(recordCount);	
			objNewDto.setMaxPageNo(objNewDto.getPagingSorting().getMaxPageNumber());
			listPartyDetails.add(objNewDto);
		}
	}
	
	catch(Exception ex )
	{
		//ex.printStackTrace();	
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rsPartyDetails);
			DbConnection.closeCallableStatement(getPartyDetails);
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return listPartyDetails;
}




public ArrayList<SITransferDto> getLogicalSINumber(SITransferDto objDto) 
{
	//Nagarjuna
	String methodName="getLogicalSINumber", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement clbStmt =null;
	ResultSet rs = null;
	ArrayList<SITransferDto> listLogicalSINumber = new ArrayList<SITransferDto>();
	SITransferDto objDto1 = null;
	
	try
	{
		connection=DbConnection.getConnectionObject();
		clbStmt= connection.prepareCall(sqlGetLogicalSiNumberForSITransfer);
		clbStmt.setString(1, objDto.getSourcePartyNo());
		if(objDto.getLogicalSINo().equals("")){
			clbStmt.setLong(2,0 );
		}else {
		clbStmt.setLong(2,Integer.parseInt(objDto.getLogicalSINo()) );
		}
		clbStmt.setString(3, objDto.getTransferdate());
		clbStmt.setLong(4, Long.parseLong(objDto.getAccountno().trim()));
		clbStmt.setString(5, objDto.getFilterLSIList());
		rs = clbStmt.executeQuery();
		while(rs.next())
		{
			objDto1 =  new SITransferDto();
			objDto1.setCustSINo(rs.getString("LOGICAL_SI_NO"));
			objDto1.setServiceName(rs.getString("SERVICESTAGE"));
			objDto1.setServiceId(rs.getInt("SERVICEID"));
			objDto1.setAccountIDString(rs.getString("ACCOUNTID"));
			objDto1.setAccountno(rs.getString("CRMACCOUNTNO"));
			objDto1.setShortCode(rs.getString("M6SHORT_CODE"));
			listLogicalSINumber.add(objDto1);
		}
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(clbStmt);
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return listLogicalSINumber;
}	


public ArrayList<SITransferDto> fetchProductList(String serviceID) 
{
	//Nagarjuna
	String methodName="fetchProductList", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement clbStmt =null;
	ResultSet rs = null;
	ArrayList<SITransferDto> productList = new ArrayList<SITransferDto>();
	SITransferDto objDto1 = null;
	
	try
	{
		connection=DbConnection.getConnectionObject();
		clbStmt= connection.prepareCall(sqlGetProductInfoForSITransfer);
		clbStmt.setString(1, serviceID);
		rs = clbStmt.executeQuery();
		while(rs.next())
		{
			objDto1 =  new SITransferDto();
			objDto1.setServiceProductID(rs.getInt("SERVICEPRODUCTID"));
			objDto1.setServiceName(rs.getString("SERVICEDETDESCRIPTION"));
			objDto1.setHardwareAllowed(rs.getInt("HARDWAREINFO"));
			objDto1.setBcpId(rs.getInt("IS_BILLING_BUTTON_ENABLE"));//For SI Tansfer Bug as on Date 14-Jun-2012
			/*objDto1.setAccountIDString(rs.getString("ACCOUNTID"));
			objDto1.setShortCode(rs.getString("M6SHORT_CODE")); */
			productList.add(objDto1);
		}
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(clbStmt);
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return productList;
}	
//003 start
public ArrayList<SITransferDto> fetchAccount(SITransferDto objdto) 
{
	//Nagarjuna
	String methodName="fetchAccount", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement getAccountDetails =null;
	ResultSet rsAccountDetails = null;
	ArrayList<SITransferDto> listAccountDetails = new ArrayList<SITransferDto>();
	SITransferDto objDto = null;
	
	try
	{
		connection=DbConnection.getConnectionObject();
		getAccountDetails= connection.prepareCall(sqlSpFetchAccountDetails);
		getAccountDetails.setString(1,objdto.getTargetPartyNo());
		
		rsAccountDetails = getAccountDetails.executeQuery();
		while(rsAccountDetails.next())
		{
			objDto =  new SITransferDto();
			objDto.setAccountIDString(rsAccountDetails.getString("ACCOUNTID"));
			objDto.setAccountno(rsAccountDetails.getString("CRMACCOUNTNO"));
			objDto.setM6ShortCode(rsAccountDetails.getString("M6SHORT_CODE"));
			listAccountDetails.add(objDto);
		}
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rsAccountDetails);
			DbConnection.closeCallableStatement(getAccountDetails);
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return listAccountDetails;
}	
//003 end

public static class SITransferResult{
	
	ArrayList<SITransferDto> guiProcessResult = null;
	public ResultType resultType;
	Map<String,SITransferDto> mapLsiToResult;
	Exception exception = null;
	
	public enum ResultType{
		BATCH_CREATION_ERROR,PROCESSED,EXC_IN_MIDWAY
	}

	public ArrayList<SITransferDto> getGuiProcessResult() {
		return guiProcessResult;
	}

	public void setGuiProcessResult(ArrayList<SITransferDto> guiProcessResult) {
		this.guiProcessResult = guiProcessResult;
	}

	public ResultType getResultType() {
		return resultType;
	}

	public void setResultType(ResultType resultType) {
		this.resultType = resultType;
	}

	public Map<String, SITransferDto> getMapLsiToResult() {
		return mapLsiToResult;
	}

	public void setMapLsiToResult(Map<String, SITransferDto> mapLsiToResult) {
		this.mapLsiToResult = mapLsiToResult;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}
	
}

/**
 * 
 * @param objDto 
 * @return SITransferResult <br>
 * SITransferResult have following different properties:<br>
 * 	a) ArrayList<SITransferDto> guiProcessResult : This property will be use for return List of SITransferDto object to the existing SI Transfer AjaxHelper.<br>
 * 	b) Map<String,SITransferDto> mapLsiToResult : This property will be use in SI Transfer bulkupload, this properties is map of LSI and Resultant Object.<br>
 *  c) ResultType resultType : This is enum property which will use for Constant value of Result Type in SI Transfer Bulkupload eg:BATCH_CREATION_ERROR,PROCESSED,EXC_IN_MIDWAY.<br>
 *  d) Exception exception : This property will use for Message handling for midway exception in SI Transfer bulkupload processing.<br>
 */
public SITransferResult processSITransfer(SITransferDto objDto) 
{
	//Nagarjuna
	Utility.LOG("--------------------   SITRANSFER METHOD processSITransfer() START ----------------------------------- ");
	String methodName="processSITransfer", className=this.getClass().getName(), msg="SITRANSFER";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement insertSIdata =null;
	CallableStatement copyChangeOrderData = null;
	CallableStatement copyNewOrderData = null;
	CallableStatement disconnectService = null;
	CallableStatement getSPIDs = null;
	ResultSet rs= null;
	CallableStatement disconnectProduct = null;
	CallableStatement copyServiceToNewOrder = null;
	CallableStatement updateSITransferData = null;
	CallableStatement updateSITransferDataForFX = null;
	int batchID =0;
	ArrayList<SITransferDto> returnDto = new ArrayList<SITransferDto>();
	ArrayList<Long> generatedNewOrderNoList = new ArrayList<Long>();
	
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(AppConstants.DATE_FORMAT_PROC);
	String result = "";
	boolean errorFound = false;
	//[006] Start
	ResultSet rs1= null;
	CallableStatement selectSPIDSDetailsforDISConnectOrder=null;
	ViewOrderDto viewObjDto=new ViewOrderDto();
	String currencyChangeBillingTriggerValidationErrors="";
	/*Following varialbe errorMessageForDisplay is using for contain error message that is going to display to USER on GUI
	 * in case of LSI transfer fail */
	StringBuilder errorMessageForDisplay = resetErrorMessageForDisplay();
	//[006] End
	
	SITransferResult transferResult = new SITransferResult();
	Map<String, SITransferDto> mapLsiToResult= new HashMap<String, SITransferDto>();
	transferResult.setMapLsiToResult(mapLsiToResult);
	
	
	try
	{
		connection=DbConnection.getConnectionObject();
		connection.setAutoCommit(false);
		//step 1. insert into TM_SI_TRANSFER
		String strLsi = objDto.getLogicalSistr();
		StringTokenizer st  = new StringTokenizer(strLsi,",");
		ArrayList list = new ArrayList();
		for (int i =0; st.hasMoreTokens();i++) {
			list.add(st.nextToken());
		}
		String strDate = objDto.getDateOfTransfers();
		StringTokenizer st11  = new StringTokenizer(strDate,",");
		ArrayList listDateTransfer = new ArrayList();
		for (int i =0; st11.hasMoreTokens();i++) {
			listDateTransfer.add(st11.nextToken());
		}
		for(int count = 0;count <list.size();count++) {
				//int batch =0;
		
				insertSIdata= connection.prepareCall(sqlSpInsertSITransferData);
				insertSIdata.setInt(1,batchID);
				insertSIdata.setInt(2,Integer.parseInt(list.get(count).toString()));
				//insertSIdata.setDate(3,new java.sql.Date(simpleDateFormat.parse(objDto.getDateOfTransfer()).getTime()));
				insertSIdata.setDate(3,new java.sql.Date(simpleDateFormat.parse(listDateTransfer.get(count).toString()).getTime()));
				insertSIdata.setString(4,objDto.getStatusOfSITransfer());
				insertSIdata.setString(5,objDto.getSourcePartyNo());
				insertSIdata.setString(6,objDto.getTargetPartyNo());
				insertSIdata.setLong(7,Long.parseLong(objDto.getAccountno().trim()));
				insertSIdata.setString(8,objDto.getRemarks());
				insertSIdata.setString(9,objDto.getError());
				insertSIdata.setInt(10,0);
				insertSIdata.setString(11,"");
				insertSIdata.setString(12,"");
			//	insertSIdata.setDate(13,new java.sql.Date(simpleDateFormat.parse(listDateTransfer.get(count).toString()).getTime()));
				insertSIdata.execute();				
				batchID = insertSIdata.getInt(1);
				if(insertSIdata.getInt(10) != 0)
				{
					Utility.LOG("--------------------   SITRANSFER_ERROR::Method[processSITransfer()]::Found in execute procedure IOE.SP_INSERT_SI_TRANSFER_DATA ----------------------------------- ");
					Utility.LOG("SITRANSFER_ERROR::"+insertSIdata.getString(12));
					errorFound = true;
					break;
				}
				//batchID = batch;
			}		
		
		//if The existing method call from SI Transfer bulkupload then transaction will be release for each LSI.
		if("BULKSITRANSFER".equalsIgnoreCase(objDto.getSourceModule()) && errorFound==false){
			connection.commit();
		}
		
		if(!errorFound){
//			copy order for disconnection
			// for every LSI create a new CHANGE oRDER.
			String strServiceID = objDto.getServiceIdString();
			StringTokenizer st1  = new StringTokenizer(strServiceID,",");
			ArrayList serviceList = new ArrayList();
			for (int i =0; st1.hasMoreTokens();i++) {
				serviceList.add(st1.nextToken());
			}
			String strAccountNo = objDto.getAccountstr();
			StringTokenizer st2  = new StringTokenizer(strAccountNo,",");
			ArrayList<Long> accountList = new ArrayList<Long>();
			for (int i =0; st2.hasMoreTokens();i++) {
				accountList.add(new Long(st2.nextToken()));
			}
			SITransferDto siDto = new SITransferDto();
			for(int count = 0;count <serviceList.size();count++) {
				
					// copying new order data. Main,po,contact
					copyNewOrderData= connection.prepareCall(sqlSpCopyChangeOrderData);
					long generatedNewOrderNo = 0 ;
					copyNewOrderData.setLong(1, generatedNewOrderNo);
					copyNewOrderData.setLong(2, Long.parseLong(serviceList.get(count).toString()));
					copyNewOrderData.setString(3, "New");
					copyNewOrderData.setInt(4, 141);
					copyNewOrderData.setString(5, listDateTransfer.get(count).toString());//(new java.sql.Date(simpleDateFormat.parse(listDateTransfer.get(count).toString()).getTime())).toString());
					copyNewOrderData.setLong(6, 0);
					copyNewOrderData.setString(7, "");
					copyNewOrderData.setString(8, "");
					copyNewOrderData.setString(9, "");
					copyNewOrderData.setLong(10, Long.parseLong(objDto.getAccountno().trim()));
					copyNewOrderData.setInt(11, 0);
					copyNewOrderData.setInt(12, objDto.getEmployeeid());//Added By rahul T
					copyNewOrderData.execute();
					generatedNewOrderNo = copyNewOrderData.getLong(1);
					if(copyNewOrderData.getLong(7) == -1) {						
						Utility.LOG("--------------------   SITRANSFER_ERROR::Method[processSITransfer()]::Found in execute procedure IOE.SP_COPY_CHANGE_ORDER_DATA ----------------------------------- ");
						Utility.LOG("SITRANSFER_ERROR:"+copyNewOrderData.getString(9));
						errorFound = true;
						break;
						//connection.rollback();
					}
					generatedNewOrderNoList.add(new Long(generatedNewOrderNo));
					//copy service and all its product to this order.
					
					copyServiceToNewOrder= connection.prepareCall(sqlspCopyOrderForSITransfer);
					copyServiceToNewOrder.setLong(1, generatedNewOrderNo);
					copyServiceToNewOrder.setLong(2, Long.valueOf(serviceList.get(count).toString()));
					copyServiceToNewOrder.setInt(3, 0);
					copyServiceToNewOrder.setInt(4, 0);
					copyServiceToNewOrder.setString(5, "");
					copyServiceToNewOrder.setString(6, "");
					copyServiceToNewOrder.setDate(7, new java.sql.Date(simpleDateFormat.parse(listDateTransfer.get(count).toString()).getTime()));					
					copyServiceToNewOrder.execute();
					if(copyServiceToNewOrder.getInt(4) == -1){						
						Utility.LOG("--------------------   SITRANSFER_ERROR::Method[processSITransfer()]::Found in execute procedure IOE.SP_COPY_ORDER_SI_TRANSFER ----------------------------------- ");
						Utility.LOG("SITRANSFER_ERROR:"+copyServiceToNewOrder.getString(6));
						errorFound = true;
						break;
					}
					
					//[006] Start For Creating SI Transfer on Change Order 
					//Change as on 26 july for change service id
					//retrive all spid for this service and send them for disconnection.
					String sb = new String();
					getSPIDs = connection.prepareCall(sqlSpGetAllSPIDsForService);
					getSPIDs.setLong(1, Long.parseLong(serviceList.get(count).toString()));
					rs = getSPIDs.executeQuery();
					while (rs.next()) {
						sb = sb + (rs.getLong(1)) + ",";
					}
					//[006] End
					//copying change order 
					copyChangeOrderData= connection.prepareCall(sqlSpCopyChangeOrderData);
					long generatedOrderNo = 0 ;
					copyChangeOrderData.setLong(1, generatedOrderNo);
					copyChangeOrderData.setLong(2, Long.parseLong(serviceList.get(count).toString()));
					copyChangeOrderData.setString(3, "Change");
					copyChangeOrderData.setInt(4, 3);
					copyChangeOrderData.setString(5, listDateTransfer.get(count).toString());//(new java.sql.Date(simpleDateFormat.parse(listDateTransfer.get(count).toString()).getTime())).toString());
					copyChangeOrderData.setLong(6, 0);
					copyChangeOrderData.setString(7, "");
					copyChangeOrderData.setString(8, "");
					copyChangeOrderData.setString(9, "");
					copyChangeOrderData.setLong(10, accountList.get(count).longValue());
					copyChangeOrderData.setInt(11, 0);//Added By Ashutosh
					copyChangeOrderData.setInt(12, objDto.getEmployeeid());//Added By rahul T

					copyChangeOrderData.execute();
					generatedOrderNo = copyChangeOrderData.getLong(1);
					if(copyChangeOrderData.getLong(7) == -1) {						
						Utility.LOG("--------------------   SITRANSFER_ERROR::Method[processSITransfer()]::Found in execute procedure IOE.SP_COPY_CHANGE_ORDER_DATA ----------------------------------- ");
						Utility.LOG("SITRANSFER_ERROR:"+copyChangeOrderData.getString(9));
						errorFound = true;
						break;
						//connection.rollback();
					}
					
						disconnectService = connection.prepareCall(sqlSpDisconnectServiceProduct);
						disconnectService.setLong(2, generatedOrderNo);
						disconnectService.setLong(1, Long.parseLong(serviceList.get(count).toString()));
						disconnectService.setTimestamp(3, new Timestamp(simpleDateFormat.parse(listDateTransfer.get(count).toString()).getTime()));
						disconnectService.setString(4, "SI TRANSFER");
						disconnectService.setInt(5, 0);
						disconnectService.setInt(6, 0);
						disconnectService.setString(7, "");
						disconnectService.setLong(8, 3);//Permanent Disconnection
						disconnectService.setString(9, "SI TRANSFER");//Added by Ashutosh For Remarks and StdReason As on Date 31-oct-2011
						disconnectService.setLong(10, 0);//Added by Ashutosh For Remarks and StdReason As on Date 31-oct-2011
						//vijay start adding a parameter
						disconnectService.setInt(11,0);
						//vijay end
						disconnectService.setInt(12,objDto.getRole_id()); /*OTC changes:: Pass Role id for current user role lookup LSI*/
						disconnectService.execute();
						int err1 = disconnectService.getInt(5);
						if(err1!= 0) {
							Utility.LOG("--------------------   SITRANSFER_ERROR::Method[processSITransfer()]::Found in execute procedure IOE.SP_DISCONNECT_SERVICE_PRODUCT ----------------------------------- ");
							Utility.LOG("SITRANSFER_ERROR:"+disconnectService.getString(7));
							errorFound = true;
							break;
						}
						
						//retrive all spid for this service and send them for disconnection.
						/*String sb = new String();
						getSPIDs = connection.prepareCall(sqlSpGetAllSPIDsForService);
						getSPIDs.setLong(1, Long.parseLong(serviceList.get(count).toString()));
						rs = getSPIDs.executeQuery();
						while (rs.next()) {
							sb = sb + (rs.getLong(1)) + ",";
							
						}*/
						if(sb.length() >0){			
						// call proc for actually diconnecting products.
						disconnectProduct =connection.prepareCall(sqlspDisconnectProducts); 
						disconnectProduct.setString(1, sb.substring(0, sb.length()-1));
						disconnectProduct.setLong(2, generatedOrderNo );
						disconnectProduct.setLong(3, 3);
						disconnectProduct.setInt(4, 0);
							//Meenakshi: Adding Another Param
						disconnectProduct.setLong(5, Long.parseLong(serviceList.get(count).toString()));
						disconnectProduct.execute();
						int err= disconnectProduct.getInt(4);
							//Meenakshi: Adding Another Condition
						if(err != 0 && err != 5){
							Utility.LOG("--------------------   SITRANSFER_ERROR::Method[processSITransfer()]::Found in execute procedure IOE.SP_DISCONNECT_PRODUCTS ----------------------------------- ");
							Utility.LOG("SITRANSFER_ERROR:code:"+err);							
							errorFound = true;
							break;
						}
						
						}else {
							
							result = "No Products found to be disconnected";
						}
						
						siDto.setBdisconorderno(generatedOrderNo+"");
						siDto.setBatchID(batchID);
						siDto.setNeworderno(generatedNewOrderNo+"");
						siDto.setLogicalSINo(list.get(count).toString());
//					update change orderno, new order no, service id for new and disconnection 0rder.
					updateSITransferData= connection.prepareCall(sqlspUpdateSITransfer);
					updateSITransferData.setLong(1, generatedNewOrderNo);
					updateSITransferData.setLong(2, generatedOrderNo);
					updateSITransferData.setLong(3, Long.parseLong(list.get(count).toString()));
					updateSITransferData.setLong(4, batchID);
					updateSITransferData.setLong(5, Long.parseLong(serviceList.get(count).toString()));
					updateSITransferData.setInt(6, 0);
				
					updateSITransferData.execute();
					if(updateSITransferData.getInt(6) != 0){
						System.out.println();
						Utility.LOG("--------------------   SITRANSFER_ERROR::Method[processSITransfer()]::Found in execute procedure IOE.SP_UPDATE_SI_TRANSFER_DATA ----------------------------------- ");
						Utility.LOG("SITRANSFER_ERROR:Code:"+updateSITransferData.getInt(6));	
						errorFound = true;
						break;
					}
					
				returnDto.add(siDto);
				
				//call fx procs
				//[006] Start
				//Added By Ashutosh As on Date 07.10.2011
				//Select Locno, Locdate for all serviceproductid in disconnect orderno
				String strBillingTrigger="";
				String ServiceProductId="";
				String LOCNo="";
				String LOCdate="";
				String dataChanged="";
				String BillingTriggerDate=listDateTransfer.get(count).toString();
				selectSPIDSDetailsforDISConnectOrder=connection.prepareCall(sqlspGetSPIDSDetailsForDISConnectOrder);
				selectSPIDSDetailsforDISConnectOrder.setLong(1, generatedOrderNo);//Disconnect Order NO
				rs1 =selectSPIDSDetailsforDISConnectOrder.executeQuery();
				while(rs1.next())
				{
					ServiceProductId=rs1.getString(1);
					LOCNo=rs1.getString(2);
					LOCdate=rs1.getString(3);
					if(strBillingTrigger=="")
					{
						strBillingTrigger=ServiceProductId+"~"+LOCNo+"~"+LOCdate+"~"+BillingTriggerDate;
						dataChanged="1";
					}
					else
					{
						strBillingTrigger=strBillingTrigger+"@@"+ServiceProductId+"~"+LOCNo+"~"+LOCdate+"~"+BillingTriggerDate;
						dataChanged=dataChanged+"@@"+"1";
					}
				}
				viewObjDto.setOrderNo(String.valueOf(generatedOrderNo));
				viewObjDto.setBillingTriggerString(strBillingTrigger);
				//call validation proc
				AjaxValidation objValidate = new AjaxValidation();
				viewObjDto=objValidate.validateActiveDate(viewObjDto,connection,"N",null,0);
				//if validate succesfully
				currencyChangeBillingTriggerValidationErrors="";
				int validationErrorSize=0;
				if(viewObjDto !=null && viewObjDto.getCurrencyChangeBillingTriggerValidationErrors()!=null)
					validationErrorSize=viewObjDto.getCurrencyChangeBillingTriggerValidationErrors().size();
				if(validationErrorSize>0)
				{
					for(int i=0; i<validationErrorSize; i++)
					{
						currencyChangeBillingTriggerValidationErrors=currencyChangeBillingTriggerValidationErrors+" ** "+viewObjDto.getCurrencyChangeBillingTriggerValidationErrors().get(i);
					}
				}
				if(viewObjDto !=null && viewObjDto.getIfAnyFailValidation().equalsIgnoreCase("SUCCESS"))
				{
					//call fx procs
					//NEED TO CHECK FOR  CURRENCY CHANGE
				
				updateSITransferDataForFX= connection.prepareCall(sqlspUpdateFXData);
				updateSITransferDataForFX.setLong(1, generatedNewOrderNo);
				updateSITransferDataForFX.setLong(2, generatedOrderNo);
				updateSITransferDataForFX.setString(3,listDateTransfer.get(count).toString());
				updateSITransferDataForFX.setNull(4,java.sql.Types.VARCHAR);
					updateSITransferDataForFX.setNull(5,java.sql.Types.VARCHAR);
				
			
				updateSITransferDataForFX.execute();
					if( ! "0".equals(updateSITransferDataForFX.getString(4)) ){
						Utility.LOG("--------------------  SITRANSFER_ERROR::Method[processSITransfer()]::Found in execute procedure IOE.SP_CALL_FX_FOR_SI_TRANSFER ----------------------------------- ");
						Utility.LOG("SITRANSFER_ERROR::"+updateSITransferDataForFX.getString(5));	
					errorFound = true;
					break;
				} 
				}
				else{					
					Utility.LOG("SITRANSFER_ERROR::Method[processSITransfer()]::"+currencyChangeBillingTriggerValidationErrors);	
					errorMessageForDisplay.append(currencyChangeBillingTriggerValidationErrors);
					errorFound = true;
				}
			
			//dto.getAccountPending();
				if("BULKSITRANSFER".equalsIgnoreCase(objDto.getSourceModule())){
					if(!errorFound){
						connection.commit();
						//prepare map entry
						SITransferDto siTransferDto = new SITransferDto();
						siTransferDto.setProgress(SITransferDto.SuccessFailure.SUCCESS.toString());
						siTransferDto.setBdisconorderno(generatedOrderNo+"");
						siTransferDto.setBatchID(batchID);
						siTransferDto.setNeworderno(generatedNewOrderNo+"");
						siTransferDto.setLogicalSINo(list.get(count).toString()); 
						mapLsiToResult.put(siTransferDto.getLogicalSINo(),siTransferDto);
					}else{
						connection.rollback();
						//prepare map entry
						SITransferDto siTransferDto = new SITransferDto();
						siTransferDto.setProgress(SITransferDto.SuccessFailure.FAILURE.toString());
						siTransferDto.setLogicalSINo(list.get(count).toString()); 
						siTransferDto.setUserSpecificError(setSITransferErrorsForGUIDisplay(errorMessageForDisplay.toString()).getUserSpecificError());
						mapLsiToResult.put(siTransferDto.getLogicalSINo(),siTransferDto);
					}
					errorFound=false;
					errorMessageForDisplay=resetErrorMessageForDisplay();
				}
				
			}
		}
		
		if("BULKSITRANSFER".equalsIgnoreCase(objDto.getSourceModule())){
			if(mapLsiToResult.size()==0){
				transferResult.setResultType(ResultType.BATCH_CREATION_ERROR);	
			}else{
				transferResult.setResultType(ResultType.PROCESSED);
			}
			
		}else{
			if(!errorFound) {
				
				connection.commit();
				//result = "SI Transfer Successful!!";
				
			} else {
				connection.rollback();
				// make return obj null
				returnDto  = new ArrayList<SITransferDto>();
				if(! "".equals(errorMessageForDisplay.toString())){
					returnDto.add(setSITransferErrorsForGUIDisplay(errorMessageForDisplay.toString()));
					
				}
				//result = "SI Transfer Failed!! Please Retry";
				
			}
			//result = "SI Transfer Failed!! Please Retry";
		}
	}
	catch(Exception ex )
	{
		if("BULKSITRANSFER".equalsIgnoreCase(objDto.getSourceModule())){
			transferResult.setResultType(ResultType.EXC_IN_MIDWAY);
			transferResult.setException(ex);
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);
		}else{
			returnDto  = new ArrayList<SITransferDto>();
			if(! "".equals(errorMessageForDisplay.toString())){
				returnDto.add(setSITransferErrorsForGUIDisplay(errorMessageForDisplay.toString()));
			}
			
			try {
				connection.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			}
			// make return obj null
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			//ex.printStackTrace();	

		}
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(insertSIdata);
			DbConnection.closeCallableStatement(copyChangeOrderData);
			DbConnection.closeCallableStatement(copyNewOrderData);
			DbConnection.closeCallableStatement(disconnectService);
			DbConnection.closeCallableStatement(getSPIDs);
			DbConnection.closeCallableStatement(disconnectProduct);
			DbConnection.closeCallableStatement(copyServiceToNewOrder);
			DbConnection.closeCallableStatement(updateSITransferData);
			DbConnection.closeCallableStatement(updateSITransferDataForFX);
			
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}
	}
	
	
		/*for(int i = 0;i<generatedNewOrderNoList.size();i++ )
		{
			Long generatedNewOrderNo = generatedNewOrderNoList.get(i);
			try{
			ViewOrderDto dto =  
				(new ViewOrderAction().fnReTryAccountCreateInFx(generatedNewOrderNo));
		}
			catch (Throwable ex){
				//System.out.println(ex.getStackTrace());
				Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			}
		} */
	
	transferResult.setGuiProcessResult(returnDto);
	return transferResult;
}	


private StringBuilder resetErrorMessageForDisplay() {
	return new StringBuilder("");
}
//Currency Change 
//Start

public static class CurrencyTransferResult{
	
	ArrayList<CurrencyChangeDto> guiProcessResult = null;
	public ResultType resultType;
	Map<String,CurrencyChangeDto> mapCurrencyToResult;
	Exception exception = null;
	
	public enum ResultType{
		BATCH_CREATION_ERROR,PROCESSED,EXC_IN_MIDWAY
	}

	public ArrayList<CurrencyChangeDto> getGuiProcessResult() {
		return guiProcessResult;
	}

	public void setGuiProcessResult(ArrayList<CurrencyChangeDto> guiProcessResult) {
		this.guiProcessResult = guiProcessResult;
	}

	public ResultType getResultType() {
		return resultType;
	}

	public void setResultType(ResultType resultType) {
		this.resultType = resultType;
	}

	public Map<String, CurrencyChangeDto> getMapCurrencyToResult() {
		return mapCurrencyToResult;
	}

	public void setMapCurrencyToResult(Map<String, CurrencyChangeDto> mapCurrencyToResult) {
		this.mapCurrencyToResult = mapCurrencyToResult;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}
	
}


public CurrencyTransferResult processCurrencyChange(CurrencyChangeDto objDto) 
{
	//Nagarjuna
	Utility.LOG("--------------------   CURRENCYCHANGE METHOD processCurrencyChange() START ----------------------------------- ");
	String methodName="processCurrencyChange", className=this.getClass().getName(), msg="CURRENCYCHANGE";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement insertSIdata =null;
	CallableStatement copyChangeOrderData = null;
	CallableStatement copyNewOrderData = null;
	CallableStatement disconnectService = null;
	CallableStatement getSPIDs = null;
	ResultSet rs= null;
	CallableStatement disconnectProduct = null;
	CallableStatement copyServiceToNewOrder = null;
	CallableStatement updateSITransferData = null;
	CallableStatement updateSITransferDataForFX = null;
	CallableStatement updateCurrencyChangeError = null;
	int batchID =0;
	ArrayList<CurrencyChangeDto> returnDto = new ArrayList<CurrencyChangeDto>();
	ArrayList<Long> generatedNewOrderNoList = new ArrayList<Long>();
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(AppConstants.DATE_FORMAT_PROC);
	String result = "";
	boolean errorFound = false;
	//[005] Start
	ResultSet rs1= null;
	CallableStatement selectSPIDSDetailsforDISConnectOrder=null;
	ViewOrderDto viewObjDto=new ViewOrderDto();
	String currencyChangeBillingTriggerValidationErrors="";
	//[005] End
	CurrencyTransferResult transferResult = new CurrencyTransferResult();
	Map<String, CurrencyChangeDto> mapCurrencyToResult= new HashMap<String, CurrencyChangeDto>();
	transferResult.setMapCurrencyToResult(mapCurrencyToResult);
	StringBuilder errorMessageForDisplay = new StringBuilder("");

	try
	{
		connection=DbConnection.getConnectionObject();
		connection.setAutoCommit(false);
		//step 1. insert into TM_SI_TRANSFER
		String strLsi = objDto.getLogicalSistr();
		StringTokenizer st  = new StringTokenizer(strLsi,",");
		ArrayList list = new ArrayList();
		for (int i =0; st.hasMoreTokens();i++) {
			list.add(st.nextToken());
		}
		//For accNo
		String strAccNo = objDto.getAccountno();
		StringTokenizer stAccNo  = new StringTokenizer(strAccNo,",");
		ArrayList listAccNo = new ArrayList();
		for (int i =0; stAccNo.hasMoreTokens();i++) {
			listAccNo.add(stAccNo.nextToken().trim());
		}
		
//		For Old Currency
		String strOldCurrency = objDto.getOldCurIds();
		StringTokenizer stOldCurrency  = new StringTokenizer(strOldCurrency,",");
		ArrayList listOldCurrency = new ArrayList();
		for (int i =0; stOldCurrency.hasMoreTokens();i++) {
			listOldCurrency.add(stOldCurrency.nextToken());
		}
		
//		For Rate Conversion
		String strRate = objDto.getRateStr();
		StringTokenizer stRate  = new StringTokenizer(strRate,",");
		ArrayList listRate = new ArrayList();
		for (int i =0; stRate.hasMoreTokens();i++) {
			listRate.add(stRate.nextToken());
		}
		
//		For New Currency
		String strNewCurrency = objDto.getNewCurIds();
		StringTokenizer stNewCurrency  = new StringTokenizer(strNewCurrency,",");
		ArrayList listNewCurrency = new ArrayList();
		for (int i =0; stNewCurrency.hasMoreTokens();i++) {
			listNewCurrency.add(stNewCurrency.nextToken());
		}

//		For Date Of Tranfer
		String strTransferDate = objDto.getDateOfTransfer();
		StringTokenizer stTransferDate  = new StringTokenizer(strTransferDate,",");
		ArrayList listTransferDate = new ArrayList();
		for (int i =0; stTransferDate.hasMoreTokens();i++) {
			listTransferDate.add(stTransferDate.nextToken());
		}
		
		// CURRENCY CHANGE PROCESS START FROM Here.
		Utility.LOG("Step 1: Currency Change Insert data in TM_CURRENCY_CHANGE_INFO : "+batchID);
		for(int count = 0;count <list.size();count++) {	
				
				//Insert data in TM_CURRENCY_CHANGE_INFO
				insertSIdata= connection.prepareCall(sqlSpInsertCurrencyChangeData);
				insertSIdata.setInt(1,batchID);
				insertSIdata.setInt(2,Integer.parseInt(list.get(count).toString()));
				insertSIdata.setDate(3,new java.sql.Date(simpleDateFormat.parse(listTransferDate.get(count).toString()).getTime()));
				insertSIdata.setString(4,objDto.getStatusOfCurrencyChange());
				insertSIdata.setString(5,objDto.getSourcePartyNo());				
				insertSIdata.setInt(6,Integer.parseInt(listAccNo.get(count).toString().trim()));
				insertSIdata.setString(7,objDto.getRemarks());
				insertSIdata.setString(8,objDto.getError());
				insertSIdata.setInt(9,Integer.parseInt(listOldCurrency.get(count).toString()));
				insertSIdata.setInt(10,Integer.parseInt(listNewCurrency.get(count).toString()));
				insertSIdata.setDouble(11,Double.parseDouble(listRate.get(count).toString()));
				insertSIdata.setInt(12,0);
				insertSIdata.setString(13,"");
				insertSIdata.setString(14,"");
				insertSIdata.execute();
				batchID = insertSIdata.getInt(1);
				if(insertSIdata.getInt(12) != 0)
				{
					Utility.LOG("--------------------   CURRENCYCHANGE_ERROR::Method[processCurrencyChange()]::Found in execute procedure IOE.SP_INSERT_CURRENCY_CHANGE_DATA ----------------------------------- ");
					Utility.LOG("CURRENCYCHANGE_ERROR::"+insertSIdata.getString(14));
					errorFound = true;
					connection.rollback();
					break;
				}else
				{
					connection.commit();
				}
				//batchID = batch;
			}
		Utility.LOG("Step 1 END: Currency Change Insert data in TM_CURRENCY_CHANGE_INFO  END: "+batchID);
		if(!errorFound){
			
			
//			copy order for disconnection
			// for every LSI create a new CHANGE oRDER.
			String strServiceID = objDto.getServiceIdString();
			StringTokenizer st1  = new StringTokenizer(strServiceID,",");
			ArrayList serviceList = new ArrayList();
			for (int i =0; st1.hasMoreTokens();i++) {
				serviceList.add(st1.nextToken());
			}
			CurrencyChangeDto siDto = new CurrencyChangeDto();
			Utility.LOG("Step 2 Start: Currency Change copying new order data. Main,po,contact  : "+batchID);
			for(int count = 0;count <serviceList.size();count++) {
				
				errorMessageForDisplay=new StringBuilder("");
				errorFound=false;
				try{	
					long generatedNewOrderNo = 0 ;
					//FOR CURRENCY CHANGE
					// copying new order data. Main,po,contact
					copyNewOrderData= connection.prepareCall(sqlSpCopyChangeOrderData);					
					copyNewOrderData.setLong(1, generatedNewOrderNo);
					copyNewOrderData.setLong(2, Long.parseLong(serviceList.get(count).toString()));
					copyNewOrderData.setString(3, "New");
					copyNewOrderData.setInt(4, 141);
					copyNewOrderData.setString(5, listTransferDate.get(count).toString());
					copyNewOrderData.setLong(6, 0);
					copyNewOrderData.setString(7, "");
					copyNewOrderData.setString(8, "");
					copyNewOrderData.setString(9, "");
					copyNewOrderData.setLong(10, Long.parseLong(listAccNo.get(count).toString().trim()));
					copyNewOrderData.setInt(11, Integer.parseInt(listNewCurrency.get(count).toString()));
					copyNewOrderData.setInt(12, objDto.getEmployeeid());//Added By rahul T
					copyNewOrderData.execute();
					generatedNewOrderNo = copyNewOrderData.getLong(1);
					if(copyNewOrderData.getLong(7) == -1) {						

						currencyChangeBillingTriggerValidationErrors="** NEW ORDER **"+copyNewOrderData.getString(9);
						Utility.LOG("--------------------   CURRENCYCHANGE_ERROR::Method[processCurrencyChange()]::Found in execute procedure IOE.SP_COPY_CHANGE_ORDER_DATA ----------------------------------- ");
						Utility.LOG("CURRENCYCHANGE_ERROR::"+currencyChangeBillingTriggerValidationErrors);
						errorFound = true;
						//break;
						//connection.rollback();
					}
					//FOR CURRENCY CHANGE  
					Utility.LOG("Step 2 END 3 Start: Currency Change copy service and all its product to this order newly  Genereated OrderNO: "+generatedNewOrderNo);
					//copy service and all its product to this order.
					
					copyServiceToNewOrder= connection.prepareCall(sqlspCopyOrderForCurrencyChange);
					copyServiceToNewOrder.setLong(1, generatedNewOrderNo);
					copyServiceToNewOrder.setLong(2, Long.valueOf(serviceList.get(count).toString()));
					copyServiceToNewOrder.setInt(3, 0);
					copyServiceToNewOrder.setInt(4, 0);
					copyServiceToNewOrder.setString(5, "");
					copyServiceToNewOrder.setString(6, "");
					copyServiceToNewOrder.setDate(7, new java.sql.Date(simpleDateFormat.parse(listTransferDate.get(count).toString()).getTime()));					
					copyServiceToNewOrder.setDouble(8,Double.parseDouble(listRate.get(count).toString()));
					copyServiceToNewOrder.execute();
					if(copyServiceToNewOrder.getInt(4) == -1){						
						currencyChangeBillingTriggerValidationErrors="**Service & Product** "+copyServiceToNewOrder.getString(6);
						Utility.LOG("--------------------   CURRENCYCHANGE_ERROR::Method[processCurrencyChange()]::Found in execute procedure IOE.SP_COPY_ORDER_CURRENCY_CHANGE ----------------------------------- ");
						Utility.LOG("CURRENCYCHANGE_ERROR::"+currencyChangeBillingTriggerValidationErrors);
						errorFound = true;
						//break;
					}
					//[005] Start
					//Change as on 26 july for change service id
					Utility.LOG("Step 3 END 4 Start: Currency Change retrive all spid for this service and send them for disconnection Genereated OrderNO: "+generatedNewOrderNo);
					//retrive all spid for this service and send them for disconnection.
					String sb = new String();
					getSPIDs = connection.prepareCall(sqlSpGetAllSPIDsForService);
					getSPIDs.setLong(1, Long.parseLong(serviceList.get(count).toString()));
					rs = getSPIDs.executeQuery();
					while (rs.next()) {
						sb = sb + (rs.getLong(1)) + ",";
						
					}
					//[005] End
					//FOR CURRENCY CHANGE
					Utility.LOG("Step 4 END 5 Start: Currency Change copying change orderNO: "+generatedNewOrderNo);
					//copying change order 
					copyChangeOrderData= connection.prepareCall(sqlSpCopyChangeOrderData);
					long generatedOrderNo = 0 ;
					copyChangeOrderData.setLong(1, generatedOrderNo);
					copyChangeOrderData.setLong(2, Long.parseLong(serviceList.get(count).toString()));
					copyChangeOrderData.setString(3, "Change");
					copyChangeOrderData.setInt(4, 3);
					copyChangeOrderData.setString(5, listTransferDate.get(count).toString());
					copyChangeOrderData.setLong(6, 0);
					copyChangeOrderData.setString(7, "");
					copyChangeOrderData.setString(8, "");
					copyChangeOrderData.setString(9, "");
					copyChangeOrderData.setLong(10, Long.parseLong(listAccNo.get(count).toString().trim()));
					copyChangeOrderData.setInt(11, Integer.parseInt(listOldCurrency.get(count).toString()));
					copyChangeOrderData.setInt(12, objDto.getEmployeeid());//Added By rahul T
					copyChangeOrderData.execute();
					generatedOrderNo = copyChangeOrderData.getLong(1);
					if(copyChangeOrderData.getLong(7) == -1) {						
						currencyChangeBillingTriggerValidationErrors="** Change ** "+copyChangeOrderData.getString(9);
						Utility.LOG("--------------------   CURRENCYCHANGE_ERROR::Method[processCurrencyChange()]::Found in execute procedure IOE.SP_COPY_CHANGE_ORDER_DATA ----------------------------------- ");
						Utility.LOG("CURRENCYCHANGE_ERROR::"+currencyChangeBillingTriggerValidationErrors);
						errorFound = true;
						//break;
						//connection.rollback();
					}
					Utility.LOG("Step 5 END 6 Start: Currency Change Permanent Disconnection change orderNO: "+generatedNewOrderNo);
						//FOR CURRENCY CHANGE
						disconnectService = connection.prepareCall(sqlSpDisconnectServiceProduct);
						disconnectService.setLong(2, generatedOrderNo);
						disconnectService.setLong(1, Long.parseLong(serviceList.get(count).toString()));
						disconnectService.setTimestamp(3, new Timestamp(simpleDateFormat.parse( listTransferDate.get(count).toString()).getTime()));
						disconnectService.setString(4, "CURRENCY CHANGE");
						disconnectService.setInt(5, 0);
						disconnectService.setInt(6, 0);
						disconnectService.setString(7, "");
						disconnectService.setLong(8, 3);//Permanent Disconnection
						disconnectService.setString(9, "CURRENCY CHANGE");//Added by Ashutosh For Remarks and StdReason As on Date 31-oct-2011
						disconnectService.setLong(10, 0);//Added by Ashutosh For Remarks and StdReason As on Date 31-oct-2011
						//vijay start adding a parameter
						disconnectService.setInt(11,0);
						//vijay end
						disconnectService.setInt(12,objDto.getRole_id());/*OTC Changes:: pass current user role id who lookup lsi*/						
						disconnectService.execute();
						int err1 = disconnectService.getInt(5);
						if(err1!= 0) {
							errorFound = true;							
							currencyChangeBillingTriggerValidationErrors="** Error in sqlSpDisconnectServiceProduct proc** ";
							Utility.LOG("CURRENCYCHANGE_ERROR:Code:"+err1);
							//break;
						}
						//FOR CURRENCY CHANGE
						//retrive all spid for this service and send them for disconnection.
						/*String sb = new String();
						getSPIDs = connection.prepareCall(sqlSpGetAllSPIDsForService);
						getSPIDs.setLong(1, Long.parseLong(serviceList.get(count).toString()));
						rs = getSPIDs.executeQuery();
						while (rs.next()) {
							sb = sb + (rs.getLong(1)) + ",";
							
						}*/
						if(sb.length() >0){
						//FOR CURRENCY CHANGE	
							Utility.LOG("Step 5 END 6.1 Start: Currency Change Permanent Disconnection change orderNO: "+generatedNewOrderNo);
						// call proc for actually diconnecting products.
						disconnectProduct =connection.prepareCall(sqlspDisconnectProducts); 
						disconnectProduct.setString(1, sb.substring(0, sb.length()-1));
						disconnectProduct.setLong(2, generatedOrderNo );
						disconnectProduct.setLong(3, 3);
						disconnectProduct.setInt(4, 0);
						//Meenakshi: Adding Another Param
						disconnectProduct.setLong(5, Long.parseLong(serviceList.get(count).toString()));
						disconnectProduct.execute();
						int err= disconnectProduct.getInt(4);
						//Meenakshi: Adding Another Condition
						if(err != 0 && err != 5){							
							errorFound = true;							
							currencyChangeBillingTriggerValidationErrors="** Error in sqlspDisconnectProducts proc ** ";
							Utility.LOG("CURRENCYCHANGE_ERROR:Code:"+err1);
							//break;
						}
						
						}else {
							
							result = "No Products found to be disconnected";
						}
						
						siDto.setBdisconorderno(generatedOrderNo+"");
						siDto.setBatchID(batchID);
						siDto.setNeworderno(generatedNewOrderNo+"");
						siDto.setLogicalSINo(list.get(count).toString());
						//FOR CURRENCY CHANGE
						Utility.LOG("Step 6.1 END 7 Start: Currency Change Permanent Disconnection change orderNO: "+generatedOrderNo+"New OrderNo"+generatedNewOrderNo);
						//update change orderno, new order no, service id for new and disconnection 0rder.
						updateSITransferData= connection.prepareCall(sqlspUpdateCurrencyChange);
						updateSITransferData.setLong(1, generatedNewOrderNo);
						updateSITransferData.setLong(2, generatedOrderNo);
						updateSITransferData.setLong(3, Long.parseLong(list.get(count).toString()));
						updateSITransferData.setLong(4, batchID);
						updateSITransferData.setLong(5, Long.parseLong(serviceList.get(count).toString()));
						updateSITransferData.setInt(6, 0);
					
						updateSITransferData.execute();
						if(updateSITransferData.getInt(6) != 0){							
							errorFound = true;							
							currencyChangeBillingTriggerValidationErrors="** Disconnect Product ** "+disconnectService.getString(9);
							Utility.LOG("CURRENCYCHANGE_ERROR:Code:"+updateSITransferData.getInt(6));
							//break;
						}
					
					returnDto.add(siDto);
					if(!errorFound)
					{
					//[005] Start
					//Added By Ashutosh As on Date 07.10.2011
					//Select Locno, Locdate for all serviceproductid in disconnect orderno
					Utility.LOG("Step 7 END 8 Start: Currency Change  Locno, Locdate for all serviceproductid for Disconnection change orderNO: "+generatedOrderNo);
					String strBillingTrigger="";
					String ServiceProductId="";
					String LOCNo="";
					String LOCdate="";
					String dataChanged="";
					String BillingTriggerDate= listTransferDate.get(count).toString();
					selectSPIDSDetailsforDISConnectOrder=connection.prepareCall(sqlspGetSPIDSDetailsForDISConnectOrder);
					selectSPIDSDetailsforDISConnectOrder.setLong(1, generatedOrderNo);//Disconnect Order NO
					rs1 =selectSPIDSDetailsforDISConnectOrder.executeQuery();
					while(rs1.next())
					{
						ServiceProductId=rs1.getString(1);
						LOCNo=rs1.getString(2);
						LOCdate=rs1.getString(3);
						if(strBillingTrigger=="")
						{
							strBillingTrigger=ServiceProductId+"~"+LOCNo+"~"+LOCdate+"~"+BillingTriggerDate;
							dataChanged="1";
						}
						else
						{
							strBillingTrigger=strBillingTrigger+"@@"+ServiceProductId+"~"+LOCNo+"~"+LOCdate+"~"+BillingTriggerDate;
							dataChanged=dataChanged+"@@"+"1";
						}
					}
					viewObjDto.setOrderNo(String.valueOf(generatedOrderNo));
					viewObjDto.setBillingTriggerString(strBillingTrigger);
					//call validation proc
					AjaxValidation objValidate = new AjaxValidation();
					viewObjDto=objValidate.validateActiveDate(viewObjDto,connection,"N",null,0);
					//if validate succesfully
					currencyChangeBillingTriggerValidationErrors="";
					int validationErrorSize=0;
					if(viewObjDto.getCurrencyChangeBillingTriggerValidationErrors()!=null)
						validationErrorSize=viewObjDto.getCurrencyChangeBillingTriggerValidationErrors().size();
					if(validationErrorSize>0)
					{
						for(int i=0; i<validationErrorSize; i++)
						{
							currencyChangeBillingTriggerValidationErrors=currencyChangeBillingTriggerValidationErrors+" ** "+viewObjDto.getCurrencyChangeBillingTriggerValidationErrors().get(i);
							errorMessageForDisplay=new StringBuilder(currencyChangeBillingTriggerValidationErrors);
						}
					}
					if(viewObjDto.getIfAnyFailValidation().equalsIgnoreCase("SUCCESS"))
					{
					//call fx procs
					//NEED TO CHECK FOR  CURRENCY CHANGE
						Utility.LOG("Step 8 END 9 Start: Currency Change  sqlspUpdateFXData change orderNO: "+generatedOrderNo);
					updateSITransferDataForFX= connection.prepareCall(sqlspUpdateFXData);
					updateSITransferDataForFX.setLong(1, generatedNewOrderNo);
					updateSITransferDataForFX.setLong(2, generatedOrderNo);
						updateSITransferDataForFX.setString(3,listTransferDate.get(count).toString());
					updateSITransferDataForFX.setNull(4,java.sql.Types.VARCHAR);				
						updateSITransferDataForFX.setNull(5,java.sql.Types.VARCHAR);
					
					updateSITransferDataForFX.execute();					
						if( ! "0".equals(updateSITransferDataForFX.getString(4)) ){							
								currencyChangeBillingTriggerValidationErrors="* Error in sqlspUpdateFXData Proc *"+updateSITransferDataForFX.getString(5);
								errorMessageForDisplay=new StringBuilder(currencyChangeBillingTriggerValidationErrors);
								Utility.LOG("CURRENCYCHANGE_ERROR::"+currencyChangeBillingTriggerValidationErrors);
						errorFound = true;
						//break;
						}						
					}
						else
						{
							Utility.LOG("CURRENCYCHANGE_ERROR::Method[processCurrencyChange()]::"+currencyChangeBillingTriggerValidationErrors);
						
						errorFound = true;
					}
					//[005] END
					}
					if(!errorFound) {
						
						connection.commit();
						generatedNewOrderNoList.add(new Long(generatedNewOrderNo));
						
						if("BULKCURRENCYTRANSFER".equalsIgnoreCase(objDto.getSourceModule())){
							//prepare map entry
							CurrencyChangeDto currencyChangeDto = new CurrencyChangeDto();
							currencyChangeDto.setProgress(CurrencyChangeDto.SuccessFailure.SUCCESS.toString());
							currencyChangeDto.setBdisconorderno(generatedOrderNo+"");
							currencyChangeDto.setBatchID(batchID);
							currencyChangeDto.setNeworderno(generatedNewOrderNo+"");
							currencyChangeDto.setLogicalSINo(list.get(count).toString()); 
							mapCurrencyToResult.put(currencyChangeDto.getLogicalSINo(),currencyChangeDto);
						}
						
					} else {
						connection.rollback();
						//Update Failure message in TM_CURRENCY_CHANGE_INFO
						Utility.LOG("Step 11: Currency Change  Data rollback in DB: "+batchID);
						updateCurrencyChangeError=connection.prepareCall(sqlspUpdateCurrencyChangeError);
						updateCurrencyChangeError.setLong(1,batchID);
						updateCurrencyChangeError.setLong(2,Long.parseLong(list.get(count).toString()));						
						updateCurrencyChangeError.setString(3,"Failure");
						updateCurrencyChangeError.setLong(4,Long.parseLong(serviceList.get(count).toString()));
						updateCurrencyChangeError.setString(5,currencyChangeBillingTriggerValidationErrors);
						updateCurrencyChangeError.execute();
						connection.commit();
						//result = "SI Transfer Failed!! Please Retry";
						if("BULKCURRENCYTRANSFER".equalsIgnoreCase(objDto.getSourceModule())){
							//prepare map entry
							CurrencyChangeDto currencyChangeDto = new CurrencyChangeDto();
							currencyChangeDto.setProgress(CurrencyChangeDto.SuccessFailure.FAILURE.toString());
							currencyChangeDto.setLogicalSINo(list.get(count).toString()); 
							currencyChangeDto.setUserSpecificError(setSITransferErrorsForGUIDisplay(errorMessageForDisplay.toString()).getUserSpecificError());
							mapCurrencyToResult.put(currencyChangeDto.getLogicalSINo(),currencyChangeDto);
						}
					}
				}
				catch(Exception e)
				{
					connection.rollback();
					//Update Failure message in TM_CURRENCY_CHANGE_INFO
					Utility.LOG("Step 11: Currency Change  Data rollback in DB: "+batchID);
					updateCurrencyChangeError=connection.prepareCall(sqlspUpdateCurrencyChangeError);
					updateCurrencyChangeError.setLong(1,batchID);
					updateCurrencyChangeError.setLong(2,Long.parseLong(list.get(count).toString()));
					updateCurrencyChangeError.setString(3,"Failure");
					updateCurrencyChangeError.setLong(4,Long.parseLong(serviceList.get(count).toString()));
					updateCurrencyChangeError.setString(5,currencyChangeBillingTriggerValidationErrors);
					updateCurrencyChangeError.execute();
					connection.commit();
					//result = "SI Transfer Failed!! Please Retry";
					
					if("BULKCURRENCYTRANSFER".equalsIgnoreCase(objDto.getSourceModule())){
						//prepare map entry
						CurrencyChangeDto currencyChangeDto = new CurrencyChangeDto();
						currencyChangeDto.setProgress(CurrencyChangeDto.SuccessFailure.FAILURE.toString());
						currencyChangeDto.setLogicalSINo(list.get(count).toString()); 
						currencyChangeDto.setUserSpecificError(
								setSITransferErrorsForGUIDisplay(
										"Internal Error:["+Utility.getStackTrace(e)+"]"
										)
								.getUserSpecificError());
						mapCurrencyToResult.put(currencyChangeDto.getLogicalSINo(),currencyChangeDto);
					}
				}
				
			}
			//commit or rollback
			//list of all new orders

			
			if("BULKCURRENCYTRANSFER".equalsIgnoreCase(objDto.getSourceModule())){
				transferResult.setResultType(CurrencyTransferResult.ResultType.PROCESSED);
			}
			
		}else {
			if("BULKCURRENCYTRANSFER".equalsIgnoreCase(objDto.getSourceModule())){
				transferResult.setResultType(CurrencyTransferResult.ResultType.BATCH_CREATION_ERROR);	
			}
		}
		
		
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		if("BULKCURRENCYTRANSFER".equalsIgnoreCase(objDto.getSourceModule())){
			transferResult.setResultType(CurrencyTransferResult.ResultType.EXC_IN_MIDWAY);
			transferResult.setException(ex);
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);
		}
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rs);
			DbConnection.closeResultset(rs1);
			DbConnection.closeCallableStatement(insertSIdata);
			DbConnection.closeCallableStatement(copyChangeOrderData);
			DbConnection.closeCallableStatement(copyNewOrderData);
			DbConnection.closeCallableStatement(disconnectService);
			DbConnection.closeCallableStatement(getSPIDs);
			DbConnection.closeCallableStatement(disconnectProduct);
			DbConnection.closeCallableStatement(copyServiceToNewOrder);
			DbConnection.closeCallableStatement(updateSITransferData);
			DbConnection.closeCallableStatement(updateSITransferDataForFX);
			DbConnection.closeCallableStatement(updateCurrencyChangeError);
			
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}
	}
	
//	 call APi for account creation
	/*for(int i = 0;i<generatedNewOrderNoList.size();i++ )
	{
		Long generatedNewOrderNo = generatedNewOrderNoList.get(i);
		try{
		ViewOrderDto dto =  
			(new ViewOrderAction().fnReTryAccountCreateInFx(generatedNewOrderNo));
	}
		catch (Throwable ex){
			//System.out.println(ex.getStackTrace());
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}
	}*/ 
	//return returnDto;
	transferResult.setGuiProcessResult(returnDto);
	return transferResult;

}
//Currency Change
//END


//CURRENCY CHANGE
	//START
	public ArrayList<CurrencyChangeDto> ViewCurrencyChangeList(CurrencyChangeDto objDto) throws Exception 
	{
		//Nagarjuna
		String methodName="ViewCurrencyChangeList", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;
		//end Nagarjuna
		Connection conn = null;
		CallableStatement getSITransferList = null;
		ResultSet rs = null;
		ArrayList<CurrencyChangeDto> objCurrencyChangeList = new ArrayList<CurrencyChangeDto>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		try {

			conn = DbConnection.getConnectionObject();

			getSITransferList = conn.prepareCall(sqlGetCurrencyChangeDetails);
			String searchBatchId = objDto.getSearchBatchId();
			String searchfromDate = objDto.getSearchfromDate();
			String searchToDate = objDto.getSearchToDate();

			if (searchBatchId == null || searchBatchId.trim().equals("")) {
				getSITransferList.setNull(1, java.sql.Types.BIGINT);
			} else {
				getSITransferList.setLong(1, Long.parseLong(searchBatchId));
			}

			if (searchfromDate == null || searchfromDate.trim().equals("")) {
				getSITransferList.setNull(2, java.sql.Types.VARCHAR);
			} else {
				getSITransferList.setString(2, searchfromDate);
			}

			if (searchToDate == null || searchToDate.trim().equals("")) {
				getSITransferList.setNull(3, java.sql.Types.VARCHAR);
			} else {
				getSITransferList.setString(3, searchToDate);
			}

			PagingSorting pagingSorting = objDto.getPagingSorting();
			pagingSorting.sync();// To calculate start index and Enc Index

			getSITransferList.setString(4, pagingSorting.getSortByColumn());// columnName
			getSITransferList.setString(5, PagingSorting
					.DB_Asc_Desc(pagingSorting.getSortByOrder()));// sort
			// order
			getSITransferList.setInt(6, pagingSorting.getStartRecordId());// start
			// index
			getSITransferList.setInt(7, pagingSorting.getEndRecordId());// end
			// index
			getSITransferList.setInt(8, (pagingSorting.isPagingToBeDone() ? 1
					: 0));// end

			rs = getSITransferList.executeQuery();

			int countFlag = 0;
			int recordCount = 0;
			while (rs.next() != false) {

				countFlag++;

				objDto = new CurrencyChangeDto();
				objDto.setSearchBatchId(rs.getString("BATCH_ID"));
				objDto.setTransferdate(sdf.format(rs.getDate("TRANSFER_DATE")));
				objDto.setProgress(rs.getString("STATUS"));

				if (pagingSorting.isPagingToBeDone()) {
					recordCount = rs.getInt("FULL_REC_COUNT");
				}

				objCurrencyChangeList.add(objDto);
			}
			pagingSorting.setRecordCount(recordCount);
		} catch (Exception ex) {
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			//ex.printStackTrace();
			//throw new Exception("SQL Exception : " + ex.getMessage(), ex);
		} finally {
			try {
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(getSITransferList);
				DbConnection.freeConnection(conn);

			} catch (Exception e) {
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
				//e.printStackTrace();
				//throw new Exception("Exception : " + e.getMessage(), e);
			}
		}
		return objCurrencyChangeList;
	}
// end


// CURRENCY CHANGE
// START
public HashMap<String,ArrayList> getCurrencyChangeDetails(String arr) 
{
	//Nagarjuna
	String methodName="getCurrencyChangeDetails", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement getCurrencyChangeDetails =null;
	CallableStatement getCurrencyChangeBatchDetails =null;
	CallableStatement getLogicalSINODetails =null;
	CallableStatement getProductsDetails =null;
	ResultSet rsCurrencyChangeDetails = null;
	ResultSet rsCurrencyChangeBatchDetails = null;
	ResultSet rsLogicalSINODetails = null;
	ResultSet rsProductsDetails = null;
	
	ArrayList<CurrencyChangeDto> listCurrencyChangeBatchDetails = new ArrayList<CurrencyChangeDto>();
	ArrayList<CurrencyChangeDto> listCurrencyChangeDetails = new ArrayList<CurrencyChangeDto>();
	ArrayList<CurrencyChangeDto> listLogicalSIDetails = new ArrayList<CurrencyChangeDto>();
	ArrayList<CurrencyChangeDto> listproductsDetails = new ArrayList<CurrencyChangeDto>();
	CurrencyChangeDto objDto = null;
	CurrencyChangeDto objBatchDto = null;
	HashMap<String ,ArrayList> currencyChangeDetails=new HashMap<String,ArrayList> ();	
	 SimpleDateFormat sdf  = new SimpleDateFormat("dd-MM-yyyy");
	try
	{
		connection=DbConnection.getConnectionObject();
	
		getCurrencyChangeBatchDetails= connection.prepareCall(sqlSpGetCurrencyChangeBatchDetails);
		
		getCurrencyChangeBatchDetails.setString(1,arr);
		
		rsCurrencyChangeBatchDetails = getCurrencyChangeBatchDetails.executeQuery();
		while(rsCurrencyChangeBatchDetails.next())
		{
			objBatchDto =  new CurrencyChangeDto();
			objBatchDto.setBatchID(rsCurrencyChangeBatchDetails.getInt("BATCHID"));
			objBatchDto.setStatusOfCurrencyChange(rsCurrencyChangeBatchDetails.getString("STATUS"));
			//objBatchDto.setDateOfTransfer(sdf.format(rsCurrencyChangeBatchDetails.getDate("TRANSFERDATE")));
			objBatchDto.setSourcePartyNo(rsCurrencyChangeBatchDetails.getString("SOURCEPARTYNO"));			
			objBatchDto.setSourcePartyName(rsCurrencyChangeBatchDetails.getString("SOURCEPARTYNAME"));
			
			listCurrencyChangeBatchDetails.add(objBatchDto);
			
			
		}
		
		getCurrencyChangeDetails= connection.prepareCall(sqlSpGetCurrencyChangeDetails);
		
		getCurrencyChangeDetails.setString(1,arr);
		
		rsCurrencyChangeDetails = getCurrencyChangeDetails.executeQuery();
		while(rsCurrencyChangeDetails.next())
		{
			objDto =  new CurrencyChangeDto();
			objDto.setNeworderno(rsCurrencyChangeDetails.getString("NEWORDERNO"));
			objDto.setBdisconorderno(rsCurrencyChangeDetails.getString("DISCONNECTIONNO"));
			objDto.setError(rsCurrencyChangeDetails.getString("ERROR"));
			objDto.setRemarks(rsCurrencyChangeDetails.getString("REMARKS"));			
			objDto.setTransferdate(sdf.format(rsCurrencyChangeDetails.getDate("TRANSFERDATE"))); 
			listCurrencyChangeDetails.add(objDto);
			
			
			
		}	
		
		
		getLogicalSINODetails= connection.prepareCall(sqlGetSourceInfoForCurrencyChange);
		
		getLogicalSINODetails.setString(1,arr);
		
		rsLogicalSINODetails = getLogicalSINODetails.executeQuery();
		while(rsLogicalSINODetails.next())
		{
			objDto =  new CurrencyChangeDto();
			objDto.setLogicalSistr(rsLogicalSINODetails.getString("LSI"));
			objDto.setServiceId1(rsLogicalSINODetails.getString("SERVICEID"));
			objDto.setServiceName(rsLogicalSINODetails.getString("SERVICENAME"));			
			objDto.setAccountno(rsLogicalSINODetails.getString("ACCOUNTNO"));
			objDto.setShortCode(rsLogicalSINODetails.getString("SHORTCODE"));
			objDto.setOldCurIds(rsLogicalSINODetails.getString("OLDCURRENCYID"));
			objDto.setOldCurrencyName(rsLogicalSINODetails.getString("OLDCURRENCYNAME"));
			objDto.setNewCurIds(rsLogicalSINODetails.getString("NEWCURRENCYID"));
			objDto.setNewCurrencyName(rsLogicalSINODetails.getString("NEWCURRENCYNAME"));
			objDto.setRate(rsLogicalSINODetails.getDouble("RATE"));
			listLogicalSIDetails.add(objDto);
			
			
			
		}

		  /*getProductsDetails= connection.prepareCall(sqlGetCurrencyChangeProductList);
          getProductsDetails.setString(1,arr);
		
          rsProductsDetails = getProductsDetails.executeQuery();
		while(rsProductsDetails.next())
		{
			objDto =  new CurrencyChangeDto();
			objDto.setLogicalSistr(rsProductsDetails.getString("LSI"));
			objDto.setSpid(rsProductsDetails.getInt("PRODUCTID"));
			objDto.setProductname(rsProductsDetails.getString("PRODUCTNAME"));
			objDto.setBcpId(rsProductsDetails.getInt("BCPID"));
			objDto.setDispatchId(rsProductsDetails.getInt("DISPATCHID"));
			
			listproductsDetails.add(objDto);
			
		}	*/
			
		
		
		
		
		
  	
	currencyChangeDetails.put("listCurrencyChangeBatchDetails", listCurrencyChangeBatchDetails);
	currencyChangeDetails.put("listCurrencyChangeDetails", listCurrencyChangeDetails);
	currencyChangeDetails.put("listLogicalSIDetails", listLogicalSIDetails);
	//currencyChangeDetails.put("listproductsDetails", listproductsDetails);
	
  
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rsCurrencyChangeDetails);
			DbConnection.closeResultset(rsCurrencyChangeBatchDetails);
			DbConnection.closeResultset(rsLogicalSINODetails);
			DbConnection.closeResultset(rsProductsDetails);
			DbConnection.closeCallableStatement(getCurrencyChangeDetails);
			DbConnection.closeCallableStatement(getCurrencyChangeBatchDetails);
			DbConnection.closeCallableStatement(getLogicalSINODetails);
			DbConnection.closeCallableStatement(getProductsDetails);
			
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return currencyChangeDetails;
}
//END

//currency Change :geting Logical si
//start
public ArrayList<CurrencyChangeDto> getLogicalSINO(CurrencyChangeDto objDto) 
{
	//Nagarjuna
	String methodName="getLogicalSINO", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement clbStmt =null;
	ResultSet rs = null;
	ArrayList<CurrencyChangeDto> listLogicalSINumber = new ArrayList<CurrencyChangeDto>();
	CurrencyChangeDto objDto1 = null;
	int recordCount;
	try
	{
		connection=DbConnection.getConnectionObject();
		clbStmt= connection.prepareCall(sqlGetLogicalSiNumberForCurrencyChange);
		clbStmt.setString(1, objDto.getSourcePartyNo());
		if(objDto.getLogicalSINo().equals("")){
			clbStmt.setLong(2,0 );
		}else {
		clbStmt.setLong(2,Integer.parseInt(objDto.getLogicalSINo()) );
		}
		SimpleDateFormat sdf  = new SimpleDateFormat("dd/MM/yyyy");
		clbStmt.setString(3, sdf.format(new Date(System.currentTimeMillis())));
		clbStmt.setString(4,objDto.getFilterLSIList() );
		clbStmt.setString(5,objDto.getSortBycolumn());
		clbStmt.setString(6,objDto.getSortByOrder());
		clbStmt.setInt(7,objDto.getStartIndex());
		clbStmt.setInt(8,objDto.getEndIndex());
		
		rs = clbStmt.executeQuery();
		while(rs.next())
		{
			objDto1 =  new CurrencyChangeDto();
			objDto1.setCustSINo(rs.getString("LOGICAL_SI_NO"));
			objDto1.setServiceName(rs.getString("SERVICESTAGE"));//product name
			objDto1.setServiceId(rs.getInt("SERVICEID"));
			objDto1.setAccountIDString(rs.getString("ACCOUNTID"));
			objDto1.setAccountno(rs.getString("CRMACCOUNTNO"));
			objDto1.setShortCode(rs.getString("M6SHORT_CODE"));
			objDto1.setCurrencyCode(rs.getString("CURRENCYID"));
			objDto1.setCurrencyName(rs.getString("CURNAME"));
			recordCount=rs.getInt("FULL_REC_COUNT");
			objDto1.getPagingSorting().setRecordCount(recordCount);	
			objDto1.setMaxPageNo(objDto1.getPagingSorting().getMaxPageNumber());
			listLogicalSINumber.add(objDto1);
		}
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();	
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(clbStmt);
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return listLogicalSINumber;
}	
//end
//Method used for Fetching Account and Main Details from Database
public ArrayList<SITransferDto> fetchParty() 
{
	//Nagarjuna
	String methodName="fetchParty", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement getPartyDetails =null;
	ResultSet rsPartyDetails = null;
	ArrayList<SITransferDto> listPartyDetails = new ArrayList<SITransferDto>();
	SITransferDto objDto = null;
	
	try
	{
		connection=DbConnection.getConnectionObject();
		getPartyDetails= connection.prepareCall(sqlSpFetchPartyDetails);
		rsPartyDetails = getPartyDetails.executeQuery();
		while(rsPartyDetails.next())
		{
			objDto =  new SITransferDto();
			objDto.setSourcePartyNo(rsPartyDetails.getString("PARTY_NO"));
			objDto.setSourcePartyName(rsPartyDetails.getString("PARTYNAME"));
			listPartyDetails.add(objDto);
		}
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rsPartyDetails);
			DbConnection .closeCallableStatement(getPartyDetails);
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return listPartyDetails;
}	
public ArrayList<SITransferDto> ViewSITransferList(SITransferDto objDto)
throws Exception {
	//Nagarjuna
	String methodName="ViewSITransferList", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	                Connection conn =null;
	                CallableStatement getSITransferList =null;
	                 ResultSet rs = null;
	                 ArrayList<SITransferDto> objSITransferList = new ArrayList<SITransferDto>();
	                 SimpleDateFormat sdf  = new SimpleDateFormat("dd-MM-yyyy");
try {

                conn = DbConnection.getConnectionObject();

               getSITransferList = conn.prepareCall(sqlGetSITransferDetails);
               String searchBatchId = objDto.getSearchBatchId();
                String searchfromDate=objDto.getSearchfromDate();
                String searchToDate = objDto.getSearchToDate();





if (searchBatchId == null || searchBatchId.trim().equals("")) {
	getSITransferList.setNull(1, java.sql.Types.BIGINT);
} else {
	getSITransferList.setLong(1, Long.parseLong(searchBatchId));
}


if (searchfromDate == null || searchfromDate.trim().equals("")) {
	getSITransferList.setNull(2, java.sql.Types.VARCHAR);
} else {
	getSITransferList.setString(2, searchfromDate);
}

if (searchToDate == null || searchToDate.trim().equals("")) {
	getSITransferList.setNull(3, java.sql.Types.VARCHAR);
} else {
	getSITransferList.setString(3, searchToDate);
}







PagingSorting pagingSorting = objDto.getPagingSorting();
pagingSorting.sync();// To calculate start index and Enc Index

getSITransferList.setString(4, pagingSorting.getSortByColumn());// columnName
getSITransferList.setString(5, PagingSorting.DB_Asc_Desc(pagingSorting
		.getSortByOrder()));// sort order
getSITransferList.setInt(6, pagingSorting.getStartRecordId());// start index
getSITransferList.setInt(7, pagingSorting.getEndRecordId());// end index
getSITransferList.setInt(8, (pagingSorting.isPagingToBeDone() ? 1 : 0));// end


rs = getSITransferList.executeQuery();

int countFlag = 0;
int recordCount = 0;
while (rs.next() != false) {

	countFlag++;
	

	objDto = new SITransferDto();
	objDto.setSearchBatchId(rs.getString("BATCH_ID"));
	objDto.setTransferdate(sdf.format(rs.getDate("CREATE_TIME")));
	objDto.setProgress(rs.getString("STATUS"));

	
	if (pagingSorting.isPagingToBeDone()) {
		recordCount = rs.getInt("FULL_REC_COUNT");
	}

	objSITransferList.add(objDto);
}
pagingSorting.setRecordCount(recordCount);
} catch (Exception ex) {
	Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
//ex.printStackTrace();
//throw new Exception("SQL Exception : " + ex.getMessage(), ex);
} finally {
try {
	DbConnection.closeResultset(rs);
	DbConnection.closeCallableStatement(getSITransferList);
	DbConnection.freeConnection(conn);

} catch (Exception e) {
	Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
	//e.printStackTrace();
	//throw new Exception("Exception : " + e.getMessage(), e);
}
}
return objSITransferList;
}
public HashMap<String,ArrayList> getSITransferDetails(String arr) 
{
	//Nagarjuna
	String methodName="getSITransferDetails", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement getSITransferDetails =null;
	CallableStatement getSITransferBatchDetails =null;
	CallableStatement getLogicalSINODetails =null;
	CallableStatement getProductsDetails =null;
	ResultSet rsSITransferDetails = null;
	ResultSet rsSITransferBatchDetails = null;
	ResultSet rsLogicalSINODetails = null;
	ResultSet rsProductsDetails = null;
	
	ArrayList<SITransferDto> listSITransferBatchDetails = new ArrayList<SITransferDto>();
	ArrayList<SITransferDto> listSITransferDetails = new ArrayList<SITransferDto>();
	ArrayList<SITransferDto> listLogicalSIDetails = new ArrayList<SITransferDto>();
	ArrayList<SITransferDto> listproductsDetails = new ArrayList<SITransferDto>();
	SITransferDto objDto = null;
	SITransferDto objBatchDto = null;
	HashMap<String ,ArrayList> SITransferDetails=new HashMap<String,ArrayList> ();	
	 SimpleDateFormat sdf  = new SimpleDateFormat("dd-MM-yyyy");
	try
	{
		connection=DbConnection.getConnectionObject();
	
		getSITransferBatchDetails= connection.prepareCall(sqlSpGetSITransferBatchDetails);
		
		getSITransferBatchDetails.setString(1,arr);
		
		rsSITransferBatchDetails = getSITransferBatchDetails.executeQuery();
		while(rsSITransferBatchDetails.next())
		{
			objBatchDto =  new SITransferDto();
			objBatchDto.setBatchID(rsSITransferBatchDetails.getInt("BATCHID"));
			objBatchDto.setStatusOfSITransfer(rsSITransferBatchDetails.getString("STATUS"));
			objBatchDto.setDateOfTransfer(sdf.format(rsSITransferBatchDetails.getDate("CREATE_TIME")));
			objBatchDto.setSourcePartyNo(rsSITransferBatchDetails.getString("SOURCEPARTYNO"));
			objBatchDto.setTargetPartyNo(rsSITransferBatchDetails.getString("TARGETPARTYNO"));
			objBatchDto.setSourcePartyName(rsSITransferBatchDetails.getString("SOURCEPARTYNAME"));
			objBatchDto.setTargetPartyName(rsSITransferBatchDetails.getString("TARGETPARTYNAME"));
			listSITransferBatchDetails.add(objBatchDto);
			
			
		}
		
		getSITransferDetails= connection.prepareCall(sqlSpgETsItRANSFERdETAILS);
		
		getSITransferDetails.setString(1,arr);
		
		rsSITransferDetails = getSITransferDetails.executeQuery();
		while(rsSITransferDetails.next())
		{
			objDto =  new SITransferDto();
			objDto.setAccountno(rsSITransferDetails.getString("ACCOUNTNO"));
			objDto.setError(rsSITransferDetails.getString("ERROR"));
			objDto.setRemarks(rsSITransferDetails.getString("REMARKS"));
			
		//	objDto.setTransferdate(sdf.format(rsSITransferDetails.getDate("TRANSFERDATE"))); 
			listSITransferDetails.add(objDto);
			
			
			
		}	
		
		
		getLogicalSINODetails= connection.prepareCall(sqlGetLogicalSINumber);
		
		getLogicalSINODetails.setString(1,arr);
		
		rsLogicalSINODetails = getLogicalSINODetails.executeQuery();
		while(rsLogicalSINODetails.next())
		{
			objDto =  new SITransferDto();
			objDto.setLogicalSistr(rsLogicalSINODetails.getString("LSI"));
			objDto.setServiceName(rsLogicalSINODetails.getString("SERVICENAME"));
			objDto.setNeworderno(rsLogicalSINODetails.getString("NEWORDERNO"));
			objDto.setBdisconorderno(rsLogicalSINODetails.getString("DISCONNECTIONNO"));
			objDto.setAccountno(rsLogicalSINODetails.getString("ACCOUNTNO"));
			objDto.setShortCode(rsLogicalSINODetails.getString("SHORTCODE"));
			objDto.setTransferdate(sdf.format(rsLogicalSINODetails.getDate("TRANSFER_DATE")));
			
			listLogicalSIDetails.add(objDto);
			
			
			
		}

		  getProductsDetails= connection.prepareCall(sqlGetSITransferProductList);
          getProductsDetails.setString(1,arr);
		
          rsProductsDetails = getProductsDetails.executeQuery();
		while(rsProductsDetails.next())
		{
			objDto =  new SITransferDto();
			objDto.setLogicalSistr(rsProductsDetails.getString("LSI"));
			objDto.setSpid(rsProductsDetails.getInt("PRODUCTID"));
			objDto.setProductname(rsProductsDetails.getString("PRODUCTNAME"));
			objDto.setBcp_details(rsProductsDetails.getString("BCPID"));
			objDto.setDispatch_details(rsProductsDetails.getString("DISPATCHID"));
			objDto.setCktid(rsProductsDetails.getString("CKTID"));
			
			listproductsDetails.add(objDto);
			
		}	
			
		
		
		
		
		
  	
  SITransferDetails.put("listSITransferBatchDetails", listSITransferBatchDetails);
  SITransferDetails.put("listSITransferDetails", listSITransferDetails);
  SITransferDetails.put("listLogicalSIDetails", listLogicalSIDetails);
  SITransferDetails.put("listproductsDetails", listproductsDetails);
	
  
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rsSITransferDetails);
			DbConnection.closeResultset(rsSITransferBatchDetails);
			DbConnection.closeResultset(rsLogicalSINODetails);
			DbConnection.closeResultset(rsProductsDetails);
			DbConnection.freeConnection(connection);
			DbConnection.closeCallableStatement(getSITransferDetails);
			DbConnection.closeCallableStatement(getSITransferBatchDetails);
			DbConnection.closeCallableStatement(getLogicalSINODetails);
			DbConnection.closeCallableStatement(getProductsDetails);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return SITransferDetails;
}
public SITransferDto InsertUpdateSIDetails(SITransferDto objdto) throws Exception 
{
	//Nagarjuna
	String methodName="InsertUpdateSIDetails", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	int retCode = 0;
	
	Connection con = null;		
	CallableStatement csInsertUpdateSIDetails = null;
	
	SITransferDto objNewOrderDto = new SITransferDto();
	try
	{
		
			
			
			
			
		con=DbConnection.getConnectionObject();
		csInsertUpdateSIDetails = con.prepareCall(sqlInsertUpdateSIDetails);			
		
		
		

		csInsertUpdateSIDetails.setInt(1,objdto.getLogicalSINumber());
		csInsertUpdateSIDetails.setInt(2,(objdto.getSpid()));
		csInsertUpdateSIDetails.setInt(3,(objdto.getBcpId()));
		csInsertUpdateSIDetails.setInt(4,(objdto.getDispatchId()));
		csInsertUpdateSIDetails.execute();
		
		con.commit();
		retCode=1;
	}
	catch(Exception ex)
	{
		con.rollback();
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeCallableStatement(csInsertUpdateSIDetails);
			DbConnection.freeConnection(con);
			//csChangeUserAccess.close();
			//DbConnection.freeConnection(conn);
		} 
		catch (Exception e) 
		{
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return objNewOrderDto;
	
}	
public ArrayList<SITransferDto> fetchDispatchInfo(SITransferDto objdto) 
{
	//Nagarjuna
	String methodName="fetchDispatchInfo", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement getDispatchDetails =null;
	ResultSet rsDispatchDetails = null;
	ArrayList<SITransferDto> listDispatchDetails = new ArrayList<SITransferDto>();
	SITransferDto objDto = null;
	
	try
	{
		connection=DbConnection.getConnectionObject();
		getDispatchDetails= connection.prepareCall(sqlSpFetchDispatchDetails);
		getDispatchDetails.setString(1,objdto.getAccountno().trim());
		
		rsDispatchDetails = getDispatchDetails.executeQuery();
		while(rsDispatchDetails.next())
		{
			objDto =  new SITransferDto();
			objDto.setDispatchId(rsDispatchDetails.getInt("DISPATCH_ADDRESS_CODE"));
			objDto.setDispatchName(rsDispatchDetails.getString("NAME"));
			objDto.setDispatch_details(rsDispatchDetails.getString("DISPATCH_DETAILS"));
			listDispatchDetails.add(objDto);
		}
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();	
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rsDispatchDetails);
			DbConnection.closeCallableStatement(getDispatchDetails);
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return listDispatchDetails;
}	
public ArrayList<SITransferDto> fetchBCPDetails(SITransferDto objdto) 
{
	//Nagarjuna
	String methodName="fetchBCPDetails", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement getBCPDetails =null;
	ResultSet rsBCPDetails = null;
	ArrayList<SITransferDto> listBCPDetails = new ArrayList<SITransferDto>();
	SITransferDto objDto = null;
	
	try
	{
		connection=DbConnection.getConnectionObject();
		getBCPDetails= connection.prepareCall(sqlSpFetchBCPDetails);
			getBCPDetails.setString(1,objdto.getAccountno().trim());
		
		rsBCPDetails = getBCPDetails.executeQuery();
		while(rsBCPDetails.next())
		{
			objDto =  new SITransferDto();
			objDto.setBcpId(rsBCPDetails.getInt("BCP_ID"));
			objDto.setBcpname(rsBCPDetails.getString("NAME"));
			objDto.setBcp_details(rsBCPDetails.getString("BCP_DETAIL"));
			listBCPDetails.add(objDto);
		}
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rsBCPDetails);
			DbConnection.closeCallableStatement(getBCPDetails);
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return listBCPDetails;
}

//[004] Start
public Long newOrderStatus(int spid) throws Exception
{
	//Nagarjuna
	String methodName="newOrderStatus", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Long TM6OrderNo=null;
	Connection conn = null;
	ResultSet rsTM6OrderNo = null;
	CallableStatement csTM6OrderNo =null;
	try
	{
		conn=DbConnection.getConnectionObject();
		csTM6OrderNo=conn.prepareCall(sqlGetTM6SpidNo);
		csTM6OrderNo.setLong(1, spid);
		csTM6OrderNo.setInt(2, 0);	
		rsTM6OrderNo=csTM6OrderNo.executeQuery();
		while(rsTM6OrderNo.next())
		{
			TM6OrderNo=rsTM6OrderNo.getLong("SERVICEID");
		}
		

		if(csTM6OrderNo.getInt(2)!=0)
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
		DbConnection.closeResultset(rsTM6OrderNo);
		DbConnection.closeCallableStatement(csTM6OrderNo);
		DbConnection.freeConnection(conn);
	}
	return TM6OrderNo;
}
//[004] End
//[004] Start
public ArrayList<NewOrderDto> getServiceListForTheOrderInM6(NewOrderDto objDto)throws Exception
{
	//Nagarjuna
	String methodName="getServiceListForTheOrderInM6", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement callstmt =null;
	ResultSet rs = null;
	NewOrderDto dto;// = new NewOrderDto();
	ArrayList<NewOrderDto> dtoList = new ArrayList<NewOrderDto>();
	try
	{
		connection=DbConnection.getConnectionObject();
		connection.setAutoCommit(false);
		callstmt= connection.prepareCall(sqlspGetServiceListForOrderInM6);	
		callstmt.setLong(1, Long.valueOf(objDto.getOrderNo()));
		callstmt.setInt(2,0);
		rs = callstmt.executeQuery();
		while(rs.next())
		{
			dto = new NewOrderDto();
			dto.setServiceId(rs.getInt("SERVICEID"));
			dtoList.add(dto);
		}
	}
	catch(Exception ex )
	{
		connection.rollback();
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(callstmt);
			DbConnection.freeConnection(connection);
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return dtoList;
}
//[004] End
//[004] Start
public Long newOrderStatusServiceId(int serviceId) throws Exception
{
	//Nagarjuna
	String methodName="newOrderStatusServiceId", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Long TM6OrderNo=null;
	Connection conn = null;
	ResultSet rsTM6OrderNo = null;
	CallableStatement csTM6OrderNo =null;
	try
	{
		conn=DbConnection.getConnectionObject();
		csTM6OrderNo=conn.prepareCall(sqlGetTM6ServiceIdNo);
		csTM6OrderNo.setLong(1, serviceId);
		csTM6OrderNo.setInt(2, 0);	
		rsTM6OrderNo=csTM6OrderNo.executeQuery();
		while(rsTM6OrderNo.next())
		{
			TM6OrderNo=rsTM6OrderNo.getLong("SERVICEID");
		}
		

		if(csTM6OrderNo.getInt(2)!=0)
		{
			TM6OrderNo=new Long(0);
		}
		
	}
	catch(Exception ex)
	{
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, "No Record Found"+msg, logToFile, logToConsole);//nagarjuna
		//ex.printStackTrace();
		//throw new Exception("No Record Found");
	}
	finally
	{
		DbConnection.closeResultset(rsTM6OrderNo);
		DbConnection.closeCallableStatement(csTM6OrderNo);
		DbConnection.freeConnection(conn);
	}
	return TM6OrderNo;
}
//[004] End

//Ramana start
public Long serviceInactiveFlagCheck(int serviceId) throws Exception
{
	//Nagarjuna
	String methodName="serviceInactiveFlagCheck", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Long serviceInactiveFlagCheck =null;
	Connection conn = null;
	ResultSet rsServiceInactiveFlagCheck = null;
	CallableStatement csServiceInactiveFlagCheck=null;
	try
	{
		conn=DbConnection.getConnectionObject();
		csServiceInactiveFlagCheck=conn.prepareCall(sqlGetServiceInactiveFlagCheck);
		csServiceInactiveFlagCheck.setLong(1, serviceId);		
		rsServiceInactiveFlagCheck=csServiceInactiveFlagCheck.executeQuery();
		while(rsServiceInactiveFlagCheck.next())
		{
			serviceInactiveFlagCheck=rsServiceInactiveFlagCheck.getLong("IS_SERVICE_INACTIVE");
		}	
		
	}
	catch(Exception ex)
	{
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, "No Record Found"+msg, logToFile, logToConsole);//nagarjuna
		//ex.printStackTrace();
		//throw new Exception("No Record Found");
	}
	finally
	{
		DbConnection.closeResultset(rsServiceInactiveFlagCheck);
		DbConnection.closeCallableStatement(csServiceInactiveFlagCheck);
		DbConnection.freeConnection(conn);
	}
	return serviceInactiveFlagCheck ;
}
//Ramana End
	
//Added by Saurabh for Order Searching using ctrl + f 11
public String getShortCode(long userRoleId ) throws Exception
{
	//Nagarjuna
	String methodName="getShortCode", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement callstmt =null;
	ResultSet rs = null;
	String shortCode=null;
	try
	{
		connection=DbConnection.getConnectionObject();
		callstmt= connection.prepareCall(sqlGetShortCode);
		callstmt.setLong(1, userRoleId);
		rs = callstmt.executeQuery();
		while(rs.next())
		{
			shortCode = rs.getString("SHORT_CODE");
		}
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();	
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(callstmt);
			DbConnection.freeConnection(connection);
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return shortCode;
}
//[00055] Start    (Added by Ashutosh)
public ArrayList<OrderHeaderDTO> getUploadedFileList(long orderNo ,long roleId) throws Exception
{
	//Nagarjuna
	String methodName="getUploadedFileList", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement callstmt =null;
	ResultSet rs = null;
	ArrayList<OrderHeaderDTO> ordersList = new ArrayList<OrderHeaderDTO>();
	OrderHeaderDTO dto;
	try
	{
		connection=DbConnection.getConnectionObject();
		connection.setAutoCommit(false);
		callstmt= connection.prepareCall(sqlGetUploadedList);	
		callstmt.setLong(1, orderNo);
		callstmt.setLong(2, roleId);
		rs = callstmt.executeQuery();
		while(rs.next())
		{
			dto = new OrderHeaderDTO();
			dto.setFileId(rs.getInt("DOCID"));			
			dto.setFileName(rs.getString("DOCUMENTNAME"));
			dto.setRoleId(rs.getString("ROLE_ID"));
			dto.setSelectDocStatus(rs.getString("STATUS"));
			ordersList.add(dto);
		}
	}
	catch(Exception ex )
	{
		connection.rollback();
		//ex.printStackTrace();	
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(callstmt);
			DbConnection.freeConnection(connection);
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return ordersList;
}

// saveUploadedFileCHKList
public String saveUploadedFileCHKList(OrderHeaderDTO objDto) throws Exception
{
	//Nagarjuna
	String methodName="saveUploadedFileCHKList", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement callstmt =null;
	String status="";
	boolean result=false;
	ArrayList<String> selectedFileIds = new ArrayList<String>();
	StringTokenizer st = new StringTokenizer( objDto.getFileIds(), ",");
	for (int i =0; st.hasMoreTokens();i++) 
	{
		selectedFileIds.add(st.nextToken());
	}
	ArrayList<String> selectedFileIdStatus = new ArrayList<String>();
	StringTokenizer st1 = new StringTokenizer( objDto.getDocStatus(), ",");
	for (int i =0; st1.hasMoreTokens();i++) 
	{
		selectedFileIdStatus.add(st1.nextToken());
	}
	try
	{
		connection=DbConnection.getConnectionObject();
		//connection.setAutoCommit(false);
		for(int i=0; i<selectedFileIds.size();i++)
		{
			callstmt= connection.prepareCall(sqlinsertUploadedCHKList);	
			callstmt.setLong(1, objDto.getOrderNumber());
			callstmt.setLong(2, Long.parseLong(selectedFileIds.get(i)));
			callstmt.setInt(3, Integer.parseInt(objDto.getRoleId()));
			callstmt.setInt(4, Integer.parseInt(selectedFileIdStatus.get(i)));
			result=callstmt.execute();
		}
		if(result==false)
			status="Data inserted successfuly!!!";
		else
			status="Data is not inserted ???";
		
	}
	catch(Exception ex )
	{
		connection.rollback();
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			
			DbConnection.closeCallableStatement(callstmt);
			DbConnection.freeConnection(connection);
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return status;
}

public ArrayList<OrderHeaderDTO> getALLSelectedFileCHKList(long orderNo ) throws Exception
{
	//Nagarjuna
	String methodName="getALLSelectedFileCHKList", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement callstmt =null;
	ResultSet rs = null;
	ArrayList<OrderHeaderDTO> ordersList = new ArrayList<OrderHeaderDTO>();
	OrderHeaderDTO dto;
	try
	{
		connection=DbConnection.getConnectionObject();
		connection.setAutoCommit(false);
		callstmt= connection.prepareCall(sqlGetALLSelectedFileCHKList);	
		callstmt.setLong(1, orderNo);		
		rs = callstmt.executeQuery();
		while(rs.next())
		{
			dto = new OrderHeaderDTO();
			dto.setOrderNumber(rs.getInt("ORDERNO"));
			dto.setFileId(rs.getInt("FILE_ID"));
			dto.setRoleId(rs.getString("ROLE_ID"));
			dto.setSelectDocStatus(rs.getString("STATUS"));
			dto.setFinalTracking(rs.getString("FINAL_TRACKING"));
			ordersList.add(dto);
		}
	}
	catch(Exception ex )
	{
		connection.rollback();
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(callstmt);
			DbConnection.freeConnection(connection);
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return ordersList;
}

public ArrayList<OrderHeaderDTO> getListOfRoleFromWorkflow(long orderNo ) throws Exception
{
	//Nagarjuna
	String methodName="getListOfRoleFromWorkflow", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement callstmt =null;
	ResultSet rs = null;
	ArrayList<OrderHeaderDTO> roleList = new ArrayList<OrderHeaderDTO>();
	OrderHeaderDTO dto;
	try
	{
		connection=DbConnection.getConnectionObject();
		connection.setAutoCommit(false);
		callstmt= connection.prepareCall(sqlGetListOfRole);	
		callstmt.setLong(1, orderNo);		
		rs = callstmt.executeQuery();
		while(rs.next())
		{
			dto = new OrderHeaderDTO();						
			dto.setRoleId(rs.getString("OWNERTYPE_ID"));			
			dto.setRoleName(rs.getString("ROLENAME"));
			//Added for enable publish btn on last task owner approvle
			dto.setIsLastTask(rs.getInt("IS_LAST_TASK"));
			roleList.add(dto);
		}
	}
	catch(Exception ex )
	{
		connection.rollback();
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(callstmt);
			DbConnection.freeConnection(connection);
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return roleList;
}




//[00055]End


//Added by Saurabh for Order Searching using ctrl + f 11
public ArrayList<OrderHeaderDTO> getOrderDetailForSearching(long orderNo,long userRoleId,String userId) throws Exception
{
	//Nagarjuna
	String methodName="getOrderDetailForSearching", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement callstmt =null;
	CallableStatement callstmt1 =null;
	ResultSet rs = null;
	ResultSet rs1 = null;
	String tableUserId = "ok";
	ArrayList<OrderHeaderDTO> ordersList = new ArrayList<OrderHeaderDTO>();
	OrderHeaderDTO dto;
	try
	{
		connection=DbConnection.getConnectionObject();
		//connection.setAutoCommit(false);
		dto = new OrderHeaderDTO();
		callstmt= connection.prepareCall(sqlGetOrderDetailsForSearching);	
		callstmt.setLong(1, orderNo);	
		rs = callstmt.executeQuery();
		while(rs.next())
		{
			dto.setOrderNumber(rs.getInt("ORDERNO"));
			dto.setStageName(rs.getString("STAGE"));
			dto.setOrderType(rs.getString("ORDERTYPE"));
			dto.setCreatedBy(rs.getString("CREATEDBY"));
			//lawkush Start
			dto.setUserID(rs.getString("USER_ID"));
			dto.setRoleName(rs.getString("ROLENAME"));
			dto.setUserName(rs.getString("USER_NAME"));
			//lawkush End
			ordersList.add(dto);
			if(orderNo==0)
			{
				orderNo=rs.getInt("ORDERNO");
			}
		}
		/*Below Code is used to get roleId from database 
		 * to decide whether the logged user can have access 
		 * to specific order in Order Entry Mode or View Mode */
		callstmt1= connection.prepareCall(sqlGetOrderDetailsForLoggedUser);	
		callstmt1.setLong(1, orderNo);
		callstmt1.setLong(2, userRoleId);
		rs1 = callstmt1.executeQuery();
		while(rs1.next())
		{
			tableUserId=rs1.getString("USER_ID");
		
			if(tableUserId.equalsIgnoreCase(userId) || (tableUserId==null && userRoleId==2))
			{
				dto.setMode("Write");
				ordersList.add(dto);
			}
			else
			{
				dto.setMode("Read");
				ordersList.add(dto);
			}
		}
		
		//[218] Start
		NewOrderModel newOrderModel=new NewOrderModel();
        boolean singleApprovalMode = newOrderModel.isViewModeForPDSingleApproval(orderNo);
		dto.setSingleApprovalMode(singleApprovalMode);
		ordersList.add(dto);
	   //[218] End
	}
	catch(Exception ex )
	{
		connection.rollback();
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rs);
			DbConnection.closeResultset(rs1);
			DbConnection.closeCallableStatement(callstmt);
			
			DbConnection.closeCallableStatement(callstmt1);
			DbConnection.freeConnection(connection);
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return ordersList;
}

//start Anil
public String getExpectedValueForQueryForm(int queryNameId) throws Exception
{
	//Nagarjuna
	String methodName="getExpectedValueForQueryForm", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement callstmt =null;
	ResultSet rs = null;
	String expectedValue=null;
	try
	{
		connection=DbConnection.getConnectionObject();
		callstmt= connection.prepareCall("SELECT trim(EXPECTEDVALUE) AS EXPECTEDVALUE FROM IOE.TQUERYMASTER WHERE ISACTIVE=1 AND QUERYID=?");
		callstmt.setInt(1, queryNameId);
		rs = callstmt.executeQuery();
		while(rs.next())
		{
			expectedValue = rs.getString("EXPECTEDVALUE");
		}
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(callstmt);
			DbConnection.freeConnection(connection);
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return expectedValue;
}
//end anil
public int reattachworkflowcheck(NewOrderDto ordDto) throws Exception
{
	//Nagarjuna
	String methodName="reattachworkflowcheck", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement callstmt =null;
	ResultSet rs = null;
	int poNumber1 = 0;
	try
	{
		connection=DbConnection.getConnectionObject();
		callstmt= connection.prepareCall("SELECT count(TASKID) AS TASKID FROM IOE.TPOWORKFLOWTASK WHERE ORDERNO=?");
		callstmt.setInt(1, ordDto.getPoNumber());
		rs = callstmt.executeQuery();
		while(rs.next())
		{
			poNumber1 = rs.getInt("TASKID");
		}
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{

			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(callstmt);
			DbConnection.freeConnection(connection);
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return poNumber1;
}
public static String sqlGetValidateFeild_Sass="{call IOE.SASS_FEILD_VALDATION(?,?,?,?,?)}";
public String validateSassProduct(long orderNo,int RoleId) throws Exception
{
	//Nagarjuna
	String methodName="validateSassProduct", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement callstmt =null;
	String str=null;
	try
	{
		connection=DbConnection.getConnectionObject();
		connection.setAutoCommit(false);
		callstmt= connection.prepareCall(sqlGetValidateFeild_Sass);	
		callstmt.setLong(1,orderNo );//6468);	
		callstmt.setString(2,null );	
		callstmt.setString(3, null);	
		callstmt.setString(4, null);
		callstmt.setInt(5, RoleId);
		callstmt.executeUpdate();
		str=callstmt.getString(2);
	}
	catch(Exception ex )
	{
		connection.rollback();
		//ex.printStackTrace();	
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{

			
			DbConnection.closeCallableStatement(callstmt);
			DbConnection.freeConnection(connection);
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return str;
}
private String sqlSpGetRoleWiseFieldMappingforService = "{call IOE.SPGET_SERVICE_ATT_DETAILS(?,?)}";
public ArrayList<FieldAttibuteDTO> getFieldValidationForService(int role,int serviceid) 
{
	//Nagarjuna
	String methodName="getFieldValidationForService", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	PreparedStatement ps =null;
	ResultSet rs = null;
	FieldAttibuteDTO objNewOrderDto = null;
	ArrayList<FieldAttibuteDTO> lstServiceTab =new ArrayList<FieldAttibuteDTO>();
	
	
	try
	{
		connection=DbConnection.getConnectionObject();
		
		ps= connection.prepareCall(sqlSpGetRoleWiseFieldMappingforService);
		ps.setInt(1, role);
		ps.setInt(2, serviceid);
		rs = ps.executeQuery();
		
		while(rs.next())
		{
		 objNewOrderDto =  new FieldAttibuteDTO();
		 
		 objNewOrderDto.setFeildName(rs.getString("ATTDESCRIPTION"));
		 objNewOrderDto.setIsMand(rs.getString("ISMANDATORY"));
		 objNewOrderDto.setIsReadOnly("0");
		 objNewOrderDto.setFieldLabel(rs.getString("ATTDESCRIPTION"));
		 objNewOrderDto.setExpectedValue(rs.getString("EXPECTEDVALUE"));
		 objNewOrderDto.setIsServiceSummReadonly(rs.getInt("ISREADONLY"));
		 if(rs.getString("EXPECTEDVALUE").equalsIgnoreCase("DROPDOWN"))
			{
			// objNewOrderDto.setty
			}

		 lstServiceTab.add(objNewOrderDto);
		
		}		 
		
		
		
		
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{

			DbConnection.closeResultset(rs);
			DbConnection.closePreparedStatement(ps);
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return lstServiceTab;
}
//[012] START : commented for Extra Hit into DB
/*
//Added by Saurabh for Change Order to get Child Node
public int getAdditionalNode(long serviceProductId ) throws Exception
{
	Connection connection =null;
	CallableStatement pstmt =null;
	ResultSet rs = null;
	int flagNode=0;
	try
	{
		connection=DbConnection.getConnectionObject();
		pstmt= connection.prepareCall(sqlGetAdditionalNode);
		pstmt.setLong(1, serviceProductId);
		rs = pstmt.executeQuery();
		while(rs.next())
		{
			flagNode = rs.getInt("ADDITIONALNODE");
		}
	}
	catch(Exception ex )
	{
		ex.printStackTrace();	
	}
	finally
	{
		try 
		{
			rs.close();
			pstmt.close();
			DbConnection.freeConnection(connection);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	return flagNode;
}
*/
//END
//Added by Saurabh for Change Order to check17 parameter's of Child Node with Parent Node
public String checkAdditionalNode(long serviceProductId ) throws Exception
{
	//Nagarjuna
	String methodName="checkAdditionalNode", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement callstmt =null;
	String compareResult=null;
	try
	{
		connection=DbConnection.getConnectionObject();
		callstmt= connection.prepareCall(sqlspCompareChildAndParent);
		callstmt.setLong(1,serviceProductId);
		callstmt.setInt(2,0);
		callstmt.setInt(3,0);
		callstmt.setString(4,"");
		callstmt.execute();
		compareResult=callstmt.getString(4);
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();	
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeCallableStatement(callstmt);
			DbConnection.freeConnection(connection);
		} 
		catch (SQLException e) 
		{
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return compareResult;
}
//Added By saurabh for change Order to get product list to check validation of 17 parameters
public ArrayList<ServiceLineDTO> populateProductList(long serviceid) 
{
	//Nagarjuna
	String methodName="populateProductList", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement pstmt =null;
	ResultSet rs = null;
	ServiceLineDTO objNewOrderDto = null;
	ArrayList<ServiceLineDTO> lstProduct =new ArrayList<ServiceLineDTO>();
	try
	{
		connection=DbConnection.getConnectionObject();
		pstmt= connection.prepareCall(sqlPopulateProductList);
		pstmt.setLong(1, serviceid);
		rs = pstmt.executeQuery();
		while(rs.next())
		{
		 objNewOrderDto =  new ServiceLineDTO();
		 objNewOrderDto.setServiceProductID(rs.getInt("SERVICEPRODUCTID"));
		 objNewOrderDto.setIsServiceActive(rs.getInt("IS_HARDWARE"));
		 objNewOrderDto.setServiceName(rs.getString("SERVICEDETDESCRIPTION"));
		 objNewOrderDto.setSubLineItemLbl(rs.getString("ADDITIONALNODE"));//Fetching Additonal node in current order
		 //lawkush start
		 objNewOrderDto.setStoreID(rs.getInt("STOREID"));
		 objNewOrderDto.setLicCompanyID(rs.getInt("LICENCECOID"));
		 //lawkush End
		 lstProduct.add(objNewOrderDto);
		}		 
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
		
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(pstmt);
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return lstProduct;
}
//Added By Saurabh to get PO details of New Order for Product's mapped against them
public ArrayList<PoDetailsDTO> populatePODetailForCopyOrder(long orderNo,long enteredOrderNo,long serviceProductId) 
{
	//Nagarjuna
	String methodName="populatePODetailForCopyOrder", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement pstmt =null;
	ResultSet rs = null;
	PoDetailsDTO objNewOrderDto = null;
	ArrayList<PoDetailsDTO> lstPODetail =new ArrayList<PoDetailsDTO>();
	try
	{
		connection=DbConnection.getConnectionObject();
		pstmt= connection.prepareCall(sqlPopulatePoListForCopyOrder);
		pstmt.setLong(1, orderNo);
		pstmt.setLong(2, enteredOrderNo);
		pstmt.setLong(3, serviceProductId);
		rs = pstmt.executeQuery();
		while(rs.next())
		{
		 objNewOrderDto =  new PoDetailsDTO();
		 objNewOrderDto.setPodetailID(rs.getInt("PODETAILNUMBER"));
		 objNewOrderDto.setIsDefaultPO(rs.getInt("ISDEFAULTPO"));
		 lstPODetail.add(objNewOrderDto);
		}		 
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();	
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{

			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(pstmt);
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return lstPODetail;
}
//Added by saurabh to get license Company for product's during Copy Order
public ArrayList<ProductCatelogDTO> populateLicenseCompanyForCopyOrder(long serviceProductId,long poId) 
{
	//Nagarjuna
	String methodName="populateLicenseCompanyForCopyOrder", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement pstmt =null;
	ResultSet rs = null;
	ProductCatelogDTO objNewOrderDto = null;
	ArrayList<ProductCatelogDTO> lstLCDetail =new ArrayList<ProductCatelogDTO>();
	try
	{
		connection=DbConnection.getConnectionObject();
		pstmt= connection.prepareCall(sqlPopulateLicenseCompanyListForCopyOrder);
		pstmt.setLong(1, serviceProductId);
		pstmt.setLong(2, poId);
		rs = pstmt.executeQuery();
		while(rs.next())
		{
			objNewOrderDto =  new ProductCatelogDTO();
			objNewOrderDto.setLicCompanyID(rs.getInt("LCOMPANYID"));
			objNewOrderDto.setLicCompanyName(rs.getString("LCOMPANYNAME"));
			lstLCDetail.add(objNewOrderDto);
		}		 
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();	
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{

			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(pstmt);
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return lstLCDetail;
}
//Added by saurabh to get Store for product's during Copy Order
public ArrayList<ProductCatelogDTO> populateStoreForCopyOrder(long serviceProductId,long licComp) 
{
	//Nagarjuna
	String methodName="populateStoreForCopyOrder", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement pstmt =null;
	ResultSet rs = null;
	ProductCatelogDTO objNewOrderDto = null;
	ArrayList<ProductCatelogDTO> lstStoreDetail =new ArrayList<ProductCatelogDTO>();
	try
	{
		connection=DbConnection.getConnectionObject();
		pstmt= connection.prepareCall(sqlPopulateStoreListForCopyOrder);
		pstmt.setLong(1, serviceProductId);
		pstmt.setLong(2, licComp);
		rs = pstmt.executeQuery();
		while(rs.next())
		{
			objNewOrderDto =  new ProductCatelogDTO();
			objNewOrderDto.setStoreID(rs.getInt("STOREID"));
			objNewOrderDto.setStoreName(rs.getString("STORENAME"));
			lstStoreDetail.add(objNewOrderDto);
		}		 
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(pstmt);
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return lstStoreDetail;
}

//Added By Saurabh for billing level check in product catelog no 24-Sept-11
public ArrayList<NewOrderDto> checkBillingLevel(long serviceIdNo) 
{
	//Nagarjuna
	String methodName="checkBillingLevel", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement pstmt =null;
	ResultSet rs = null;
	NewOrderDto objNewOrderDto = null;
	ArrayList<NewOrderDto> lstBillingDetail =new ArrayList<NewOrderDto>();
	try
	{
		connection=DbConnection.getConnectionObject();
		pstmt= connection.prepareCall(sqlValidateBillingLevel);
		pstmt.setLong(1, serviceIdNo);
		rs = pstmt.executeQuery();
		while(rs.next())
		{
		 objNewOrderDto =  new NewOrderDto();
		 objNewOrderDto.setServiceProductID(rs.getInt("SERVICEPRODUCTID"));
		 objNewOrderDto.setBillingLevelId(rs.getLong("BILLINGLEVEL"));
		 lstBillingDetail.add(objNewOrderDto);
		}		 
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(pstmt);
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return lstBillingDetail;
}
//End By Saurabh
//Start [000777]Added by Ashutosh to get Line Item And Sub Line Item
public ArrayList<LineItemDTO> populateLineItem() 
{
	//Nagarjuna
	String methodName="populateLineItem", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement pstmt =null;
	ResultSet rs = null;
	LineItemDTO objNewOrderDto = null;
	ArrayList<LineItemDTO> lstLineItemDetail =new ArrayList<LineItemDTO>();
	try
	{
		connection=DbConnection.getConnectionObject();
		pstmt= connection.prepareCall(sqlPopulateLineItem);
		rs = pstmt.executeQuery();
		while(rs.next())
		{
			objNewOrderDto =  new LineItemDTO();
			objNewOrderDto.setLineItemId(rs.getInt("LINEITEMID"));
			objNewOrderDto.setLineItemName(rs.getString("LINEITEMNAME"));
			lstLineItemDetail.add(objNewOrderDto);
		}		 
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(pstmt);
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return lstLineItemDetail;
}
public ArrayList<LineItemDTO> populateSubLineItem() 
{
	//Nagarjuna
	String methodName="populateSubLineItem", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement pstmt =null;
	ResultSet rs = null;
	LineItemDTO objNewOrderDto = null;
	ArrayList<LineItemDTO> lstSubLineItemDetail =new ArrayList<LineItemDTO>();
	try
	{
		connection=DbConnection.getConnectionObject();
		pstmt= connection.prepareCall(sqlPopulateSubLineItem);
		rs = pstmt.executeQuery();
		while(rs.next())
		{
			objNewOrderDto =  new LineItemDTO();
			objNewOrderDto.setSublineItemId(rs.getInt("SUBLINEITEMITD"));
			objNewOrderDto.setSubLineItemName(rs.getString("SUBLINEITEMNAME"));
			lstSubLineItemDetail.add(objNewOrderDto);
		}		 
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();	
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(pstmt);
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return lstSubLineItemDetail;
}
//End [000777]Added by Ashutosh 

public ArrayList<NewOrderDto> getComponentsWithSorting(PagingDto objDto) 
{
	//Nagarjuna
	String methodName="getComponentsWithSorting", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement getAllComponents =null;
	ResultSet rsComponents = null;
	ArrayList<NewOrderDto> listComponents = new ArrayList<NewOrderDto>();
	NewOrderDto objNewOrderDto = null;
	int recordCount;
	try
	{
		connection=DbConnection.getConnectionObject();
		getAllComponents= connection.prepareCall(sqlspGetComponentWithSorting);
		String searchComponent=objDto.getComponentName();
		int componentID=objDto.getComponentID();
		String selectedPackage = objDto.getSelectedPackageList();
		
		//String selectedPackage = "";
		/*for(int count = 0 ; count < selectedPackages.size();count++) {
			if(! "".equals(selectedPackage))
			selectedPackage = selectedPackage + selectedPackages.get(count);
			else 
				selectedPackage = selectedPackages.get(count).toString();
		} */
		if(searchComponent.equalsIgnoreCase(""))
		{
			searchComponent=null;
		}
				
		getAllComponents.setString(1,searchComponent);
		getAllComponents.setInt(2,new Integer(componentID));
		getAllComponents.setString(3,objDto.getSortBycolumn());
		getAllComponents.setString(4,objDto.getSortByOrder());
		getAllComponents.setInt(5,objDto.getStartIndex());
		getAllComponents.setInt(6,objDto.getEndIndex());
		getAllComponents.setString(7,selectedPackage);
		getAllComponents.setInt(8,objDto.getPoNumber());
		rsComponents = getAllComponents.executeQuery();
		while(rsComponents.next())
		{
			objNewOrderDto =  new NewOrderDto();
			objNewOrderDto.setComponentID(rsComponents.getInt("COMPONENT_ID"));
			objNewOrderDto.setComponentName(rsComponents.getString("COMPONENT_NAME"));
			objNewOrderDto.setPackageID(rsComponents.getInt("PACKAGE_ID"));
			objNewOrderDto.setPackageName(rsComponents.getString("PACKAGE_NAME"));
			recordCount=rsComponents.getInt("FULL_REC_COUNT");
			objNewOrderDto.getPagingSorting().setRecordCount(recordCount);	
			objNewOrderDto.setMaxPageNo(objNewOrderDto.getPagingSorting().getMaxPageNumber());
			listComponents.add(objNewOrderDto);			
		}
		//return listComponents;
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rsComponents);
			DbConnection.closeCallableStatement(getAllComponents);
			DbConnection.freeConnection(connection);
			
			
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
		catch(Exception ex)
		{
			//ex.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return listComponents;
}

public ArrayList<ComponentsDto> populateComponentsDetails(NewOrderDto objDto) 
{
	//Nagarjuna
	String methodName="populateComponentsDetails", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement getComponents=null;
	ResultSet rs = null;
	ArrayList<ComponentsDto> listComponents = new ArrayList<ComponentsDto>();
	ComponentsDto objCDto = null;
	//SimpleDateFormat simpleDateFormat = new SimpleDateFormat(AppConstants.DATE_FORMAT_PROC);
	try
	{
		connection=DbConnection.getConnectionObject();
		getComponents= connection.prepareCall(sqlFetchComponentsDetails);	
		getComponents.setInt(1, objDto.getServiceProductID());
		getComponents.setInt(2, objDto.getServiceId());
		rs = getComponents.executeQuery();
		while(rs.next())
		{
			objCDto =  new ComponentsDto();
			
			objCDto.setComponentInfoID(rs.getInt("COMPONENTINFOID"));
			objCDto.setComponentID(rs.getInt("COMPONENT_ID"));
			objCDto.setComponentName(rs.getString("COMPONENT_NAME"));
			objCDto.setPackageID(rs.getInt("PACKAGE_ID"));
			objCDto.setPackageName(rs.getString("PACKAGE_NAME"));
			objCDto.setIsDisconnected(rs.getInt("ISDISCONNECTED"));
			objCDto.setCreated_serviceid(rs.getInt("CREATEDIN_SERVICEID"));
			objCDto.setDisconnectedInCurrentService(rs.getString("DISCONNECTED_IN_SERVICEID"));
			objCDto.setInactive(rs.getInt("INACTIVE"));
			objCDto.setInactiveDate(rs.getString("SYSTEM_START_DATE"));
			objCDto.setComponentStatus(rs.getString("COMPONENT_STATUS"));
			objCDto.setStartDateLogic(rs.getString("COMPONENT_START_LOGIC"));
			objCDto.setEndDateLogic(rs.getString("COMPONENT_END_LOGIC"));			
			if((rs.getString("COMPONENT_START_DATE"))==null)
			{
			objCDto.setStart_date("");
			
			}
			else{
					objCDto.setStart_date(rs.getString("COMPONENT_START_DATE"));
			    }
			if((rs.getString("COMPONENT_END_DATE"))==null)
			{
				objCDto.setEnd_date("");
			
			}
			else{
				objCDto.setEnd_date(rs.getString("COMPONENT_END_DATE"));
			
			
			   }
			//----------[START:component start days and end days start months and end months]------------------------
			objCDto.setStartDateDays(rs.getInt("START_DAYS"));
			objCDto.setStartDateMonth(rs.getInt("START_MONTHS"));
			objCDto.setEndDateDays(rs.getInt("END_DAYS"));
			objCDto.setEndDateMonth(rs.getInt("END_MONTHS"));
			objCDto.setStartDateLogicName(rs.getString("COMPONENT_START_LOGIC_NAME"));
			objCDto.setEndDateLogicName(rs.getString("COMPONENT_END_LOGIC_NAME"));
			objCDto.setIsclose(rs.getInt("ISCLOSE"));
			//----------[END:component start days and end days start months and end months]------------------------
			
			
			
			//objCDto.setActiveDate(simpleDateFormat.format(rs.getInt("ACTIVE_DATE")).toString());
			//objCDto.setInactiveDate(simpleDateFormat.format(rs.getInt("INACTIVE_DATE")).toString());
			listComponents.add(objCDto);
		}
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(getComponents);
			DbConnection.freeConnection(connection);
			
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
		catch(Exception ex)
		{
			//ex.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return listComponents;
}
public ArrayList<NewOrderDto> getPackagesWithSorting(PagingDto objDto) 
{
	//Nagarjuna
	String methodName="getPackagesWithSorting", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement getAllPackages =null;
	ResultSet rsPackages = null;
	ArrayList<NewOrderDto> listPackages = new ArrayList<NewOrderDto>();
	NewOrderDto objNewOrderDto = null;
	int recordCount;
	try
	{
		connection=DbConnection.getConnectionObject();
		getAllPackages= connection.prepareCall(sqlspGetPackageWithSorting);
		String searchPackage=objDto.getPackageName();
		
		int packageID=objDto.getPackageID();
		
		if(searchPackage.equalsIgnoreCase(""))
		{
			searchPackage=null;
		}
				
		getAllPackages.setString(1,searchPackage);
		getAllPackages.setInt(2,new Integer(packageID));
		getAllPackages.setString(3,objDto.getSortBycolumn());
		getAllPackages.setString(4,objDto.getSortByOrder());
		getAllPackages.setInt(5,objDto.getStartIndex());
		getAllPackages.setInt(6,objDto.getEndIndex());
		getAllPackages.setInt(7,objDto.getServiceDetailID());
		rsPackages = getAllPackages.executeQuery();
		while(rsPackages.next())
		{
			objNewOrderDto =  new NewOrderDto();
			objNewOrderDto.setPackageName(rsPackages.getString("PACKAGE_NAME"));
			objNewOrderDto.setPackageID(rsPackages.getInt("PACKAGE_ID"));
			recordCount=rsPackages.getInt("FULL_REC_COUNT");
			objNewOrderDto.getPagingSorting().setRecordCount(recordCount);	
			objNewOrderDto.setMaxPageNo(objNewOrderDto.getPagingSorting().getMaxPageNumber());
			listPackages.add(objNewOrderDto);			
		}
		//return listComponents;
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rsPackages);
			DbConnection.closeCallableStatement(getAllPackages);
			DbConnection.freeConnection(connection);
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
		catch(Exception ex)
		{
			//ex.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return listPackages;
}

//Meenakhi : account from session start
public static String sqlGetAccountData= "{call IOE.SP_GET_ACCOUNT_DATA(?)}";
public OrderHeaderDTO getAccountData(String accountId) 
{
	//Nagarjuna
	String methodName="getAccountData", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement getAccountDetails =null;
	ResultSet rsAccountDetails = null;
	OrderHeaderDTO objNewOrderDto = null;
	
	try
	{
		connection=DbConnection.getConnectionObject();
		getAccountDetails= connection.prepareCall(sqlGetAccountData);
		getAccountDetails.setLong(1, Long.parseLong(accountId));
		rsAccountDetails = getAccountDetails.executeQuery();
		while(rsAccountDetails.next())
		{
			objNewOrderDto =  new OrderHeaderDTO();
			objNewOrderDto.setAccountID(rsAccountDetails.getInt("accountID"));
			objNewOrderDto.setAccountName(rsAccountDetails.getString("accountName"));
			objNewOrderDto.setAccphoneNo(rsAccountDetails.getLong("PhoneNo"));
			objNewOrderDto.setLob(rsAccountDetails.getString("LOB"));
			objNewOrderDto.setCrmAccountId(rsAccountDetails.getInt("CRMACCOUNTNO"));//Added By Ashutosh
			objNewOrderDto.setAccountManager(rsAccountDetails.getString("AccountManager"));
			objNewOrderDto.setProjectManager(rsAccountDetails.getString("ProjManager"));
			objNewOrderDto.setSpFirstname(rsAccountDetails.getString("SPFirstname"));
			objNewOrderDto.setSpLastName(rsAccountDetails.getString("SLastName"));
			objNewOrderDto.setAcmgrPhno(rsAccountDetails.getString("acmgrPhone"));//changed by kalpana from long to string for bug id HYPR11042013001
			objNewOrderDto.setAcmgrEmail(rsAccountDetails.getString("acmgrEmail"));
			objNewOrderDto.setSpLPhno(rsAccountDetails.getString("SLPhno"));//changed by kalpana from long to string for bug id HYPR11042013001
			objNewOrderDto.setSpLEmail(rsAccountDetails.getString("emailID"));
			objNewOrderDto.setRegion(rsAccountDetails.getString("Region"));
			objNewOrderDto.setZone(rsAccountDetails.getString("ACCZONE"));
			//objNewOrderDto.setZoneId(rsAccountDetails.getInt("ZONEID"));
			objNewOrderDto.setM6ShortCode(rsAccountDetails.getString("M6SHORTCODE"));
			objNewOrderDto.setRegionId(rsAccountDetails.getInt("REGIONID"));
			
		}
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();	
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rsAccountDetails);
			DbConnection.closeCallableStatement(getAccountDetails);
			
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
		//	e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return objNewOrderDto;
}	
//Meenakhi : account from session end

//Start[007]
public int getHardwareCount(long serviceId,long serviceDetailId ) throws Exception
{
	//Nagarjuna
	String methodName="getHardwareCount", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement pstmt =null;
	ResultSet rs = null;
	int hardwareCount=0;
	try
	{
		connection=DbConnection.getConnectionObject();
		pstmt= connection.prepareCall(sqlGethardwareCount);
		pstmt.setLong(1, serviceId);
		rs = pstmt.executeQuery();
		while(rs.next())
		{
			hardwareCount = rs.getInt("HARDWARECOUNT");
		}
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			rs.close();
			pstmt.close();
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(pstmt);
			
			DbConnection.freeConnection(connection);
		} 
		catch (SQLException e) 
		{
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return hardwareCount;
}
//End [007]
public int getDataForHardware(long getServiceId) 
{
	//Nagarjuna
	String methodName="getDataForHardware", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	PreparedStatement getlistDC =null;

	ResultSet rsProdList = null;
	NewOrderDto objNewOrderDto = null;
	int isHardwareValid =0;
	try
	{
		connection=DbConnection.getConnectionObject();
		getlistDC= connection.prepareCall(sqlGetHardwareDetails);
		getlistDC.setLong(1,getServiceId);
		rsProdList = getlistDC.executeQuery();
		while(rsProdList.next())
		{
			
			isHardwareValid = rsProdList.getInt(1);
			
		}
		
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();	
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rsProdList);
			DbConnection.closePreparedStatement(getlistDC);
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
		//	e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return isHardwareValid;
}



//Add by Anil for GAM related method
/*Function:getGAMList
 * Purpose:To fetch already attached gam with order
 * Created by:Anil Kumar
 * Date:27-Dec-2011
 * */
public ArrayList<NewOrderDto> getGAMList(PagingDto objDto) 
{
	//Nagarjuna
	String methodName="getGAMList", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement clbStmt =null;
	ResultSet rs = null;
	ArrayList<NewOrderDto> GAMList = new ArrayList<NewOrderDto>();
	NewOrderDto objNewOrderDto = null;	
	
	try
	{
		connection=DbConnection.getConnectionObject();
		
		clbStmt= connection.prepareCall(sqlGetGAMList);	
		String searchGam=objDto.getGam_name();
		if(searchGam.equalsIgnoreCase(""))
		{
			searchGam=null;
		}
		
			if("".equalsIgnoreCase(objDto.getQuoteNo()))
				{
					clbStmt.setString(1, "0");
				}
				else
				{
					clbStmt.setString(1, objDto.getQuoteNo());
				}
		
		
			clbStmt.setInt(2,objDto.getOrderNumber());



		rs = clbStmt.executeQuery();
		
		while(rs.next())
		{
			objNewOrderDto =  new NewOrderDto();
			objNewOrderDto.setGam_id(rs.getInt("GAMEMPID"));
			objNewOrderDto.setGam_name(rs.getString("GAMNAME"));
			objNewOrderDto.setGam_emailid(rs.getString("GAMEMAILID"));
			objNewOrderDto.setGam_contact(rs.getString("GAMCONTACT"));						
			
			GAMList.add(objNewOrderDto);
		}
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
			try {
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(clbStmt);
				DbConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
			}
		}
	
	return GAMList;
}
/*Function:saveGamList
 *Purpose:To save GAM List with Order and quotes with transaction table
 * Created by:Anil Kumar
 * Date:27-Dec-2011
 */
public int saveGamList(String strGAMIds,int orderno,String quote,String sessionId) 
{
	//Nagarjuna
	String methodName="saveGamList", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement clbStmt =null;				
	int successMsg=-1;
	try
	{
		connection=DbConnection.getConnectionObject();
		
		clbStmt= connection.prepareCall(sqlSaveGAMList);								
		clbStmt.setString(1,strGAMIds);
		clbStmt.setInt(2,orderno);		
		clbStmt.setString(3,quote);
		clbStmt.setInt(4, 0);
		clbStmt.setString(5, "");						


		clbStmt.execute();	
		
		successMsg=clbStmt.getInt(4);
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{

			try {
				DbConnection.closeCallableStatement(clbStmt);
				DbConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
			}
		
	}
	return successMsg;
}
//Start[008]
public int getM6LineItemCheck(long serviceDetailId ) throws Exception
{
	//Nagarjuna
	String methodName="getM6LineItemCheck", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement pstmt =null;
	ResultSet rs = null;
	int isM6LineItem=-1;
	try
	{
		connection=DbConnection.getConnectionObject();
		pstmt= connection.prepareCall(sqlNonM6Check);
		pstmt.setLong(1, serviceDetailId);
		rs = pstmt.executeQuery();
		while(rs.next())
		{
			isM6LineItem = rs.getInt("SENDTOM6");
		}
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();	
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(pstmt);
			DbConnection.freeConnection(connection);
		} 
		catch (SQLException e) 
		{
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return isM6LineItem;
}
//End[008]
/*Function:deleteGamList
 *Purpose:To delete GAM List with Order from  transaction table
 * Created by:Anil Kumar
 * Date:03-Jan-2012
 */
public int deleteGamList(String strGAMIds,int orderno,String sessionId) 
{
	//Nagarjuna
	String methodName="deleteGamList", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement clbStmt =null;				
	int successMsg=-1;
	try
	{
		connection=DbConnection.getConnectionObject();
		
		clbStmt= connection.prepareCall(sqldeleteGAMList);								
		clbStmt.setString(1,strGAMIds);
		clbStmt.setInt(2,orderno);				
		clbStmt.setInt(3, 0);
		clbStmt.setString(4, "");						

		clbStmt.execute();	
		
		successMsg=clbStmt.getInt(3);
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
			try {
				DbConnection.closeCallableStatement(clbStmt);
				DbConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
			//	e.printStackTrace();
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
			}
		
	}
	return successMsg;
}
/*Function:validateGAMBeforeSave
 * Purpose:To validate GAM before save,is GAM already attached,maximum GAM reached
 * Created By:Anil Kumar
 * Date:27-Dec-2011
 * */
public NewOrderDto validateGAMBeforeSave(String strGAMIds,int orderno) 
{
	//Nagarjuna
	String methodName="validateGAMBeforeSave", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement clbStmt =null;				
	NewOrderDto msgList=new NewOrderDto();
	try
	{
		connection=DbConnection.getConnectionObject();
		
		clbStmt= connection.prepareCall(sqlValidateGAMList);								
		clbStmt.setString(1,strGAMIds);
		clbStmt.setInt(2,orderno );
		clbStmt.setInt(3, 0);//validatestatus
		clbStmt.setString(4, "");//validatemessage
		clbStmt.setInt(5, 0);
		clbStmt.setString(6, "");
		clbStmt.setInt(7, 0);//validatestatus
		
		clbStmt.execute();	
				
		msgList.setMsgOut(clbStmt.getString(4));
		msgList.setGam_validate_status(clbStmt.getInt(3));
		msgList.setMax_allowed_gam_validate(clbStmt.getInt(7));
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		
			try {
				DbConnection.closeCallableStatement(clbStmt);
				DbConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
			}
		
	}
	return msgList;
}
/*Function:getGAMListAll
 * Purpose:To fetch already attached gam with order
 * Created by:Anil Kumar
 * Date:30-Dec-2011
 * */
public ArrayList<NewOrderDto> getGAMListAll(PagingDto objDto) 
{
	//Nagarjuna
	String methodName="getGAMListAll", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement clbStmt =null;
	ResultSet rs = null;
	ArrayList<NewOrderDto> GAMList = new ArrayList<NewOrderDto>();
	NewOrderDto objNewOrderDto = null;	
	
	try
	{
		connection=DbConnection.getConnectionObject();
		
		clbStmt= connection.prepareCall(sqlGetAllGAMList);	
		
		String searchGam=objDto.getGam_name();
		
		if(searchGam.equalsIgnoreCase(""))
		{
			searchGam=null;
		}
		
		clbStmt.setString(1,searchGam);
		if("".equalsIgnoreCase(objDto.getQuoteNo()))
		{
			clbStmt.setString(2, "0");
		}
		else
		{
			clbStmt.setString(2, objDto.getQuoteNo());
		}
		clbStmt.setInt(3,objDto.getOrderNumber());
	//	clbStmt.setInt(4, 0);//is viewGamList 0 for view all new GAM list
		clbStmt.setString(4,objDto.getSortBycolumn());
		clbStmt.setString(5,objDto.getSortByOrder());
		clbStmt.setInt(6,objDto.getStartIndex());
		clbStmt.setInt(7,objDto.getEndIndex());
		
		rs = clbStmt.executeQuery();
		
		while(rs.next())
		{
			objNewOrderDto =  new NewOrderDto();
			objNewOrderDto.setGam_id(rs.getInt("GAMEMPID"));
			objNewOrderDto.setGam_name(rs.getString("GAMNAME"));
			objNewOrderDto.setGam_emailid(rs.getString("GAMEMAILID"));
			objNewOrderDto.setGam_contact(rs.getString("GAMCONTACT"));						
			
			GAMList.add(objNewOrderDto);
		}
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
	
			try {
				DbConnection.closeCallableStatement(clbStmt);
				DbConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
			}
		
	}
	return GAMList;
}
//End by Anil:GAM related methods

//Start[009]
public String getServiceName(long serviceNo) throws Exception
{
	//Nagarjuna
	String methodName="getServiceName", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement cstmt =null;
	ResultSet rs = null;
	String serviceName="";
	try
	{
		connection=DbConnection.getConnectionObject();
		cstmt= connection.prepareCall(sqlGetServiceName);
		cstmt.setLong(1, serviceNo);
		rs = cstmt.executeQuery();
		while(rs.next())
		{
			serviceName = rs.getString("SERVICESTAGE");
		}
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(cstmt);
			DbConnection.freeConnection(connection);
		} 
		catch (SQLException e) 
		{
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return serviceName;
}
//End[009]

/*Function:getGAMListAll
 * Purpose:To fetch order as per GAM
 * Created by:lawkush
 * Date:5-Jan-2011
 * */
public ArrayList<NewOrderDto> getGamOrderList(PagingDto objDto) 
{
	//Nagarjuna
	String methodName="getGamOrderList", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement clbStmt =null;
	ResultSet rs = null;
	ArrayList<NewOrderDto> GamOrderList = new ArrayList<NewOrderDto>();
	NewOrderDto objNewOrderDto = null;	
	
	try
	{
		connection=DbConnection.getConnectionObject();
		
		clbStmt= connection.prepareCall(sqlGetGamOrderList);										
		clbStmt.setInt(1, objDto.getGam_id());
				
		rs = clbStmt.executeQuery();
		
		while(rs.next())
		{
			objNewOrderDto =  new NewOrderDto();
			objNewOrderDto.setOrderNumber(rs.getInt("ORDERNO"));
			objNewOrderDto.setOrderDate(rs.getString("ORDERDATE"));
			objNewOrderDto.setStageName(rs.getString("STAGE"));
			objNewOrderDto.setQuoteNo(rs.getString("QUOTENO"));
			objNewOrderDto.setAccountName(rs.getString("ACCOUNTNAME"));						
			objNewOrderDto.setCrmAccountNo(rs.getInt("CRMACCOUNTNO"));					
			objNewOrderDto.setGam_id(rs.getInt("ID"));		
			GamOrderList.add(objNewOrderDto);
		}
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		
			try {
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(clbStmt);
				DbConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
			}
		
	}
	return GamOrderList;
}
/*Function:getGAMListAll
 * Purpose:To fetch already attached gam with order
 * Created by:Anil Kumar
 * Date:30-Dec-2011
 * */
public ArrayList<NewOrderDto> getGAMFormulaList(int orderno) 
{
	//Nagarjuna
	String methodName="getGAMFormulaList", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement clbStmt =null;
	ResultSet rs = null;
	ArrayList<NewOrderDto> GAMFormulaList = new ArrayList<NewOrderDto>();
	NewOrderDto objNewOrderDto = null;	
	
	try
	{
		connection=DbConnection.getConnectionObject();
		
		clbStmt= connection.prepareCall(sqlGetGAMFormulaList);										
		clbStmt.setInt(1, orderno);		
		clbStmt.setInt(2, 0);//is viewGamList 0 for view all new GAM list
				
		rs = clbStmt.executeQuery();
		
		while(rs.next())
		{
			objNewOrderDto =  new NewOrderDto();
			objNewOrderDto.setFormulaId(rs.getInt("FORMULAID"));
			objNewOrderDto.setFormulaName(rs.getString("FORMULANAME"));
			objNewOrderDto.setBaseCredit(rs.getString("BASECREDITINPERCENTAGE"));
			objNewOrderDto.setKamCredit(rs.getString("KAMCREDITINPERCENTAGE"));
			objNewOrderDto.setGamCredit(rs.getString("GAMCREDITINPERCENTAGE"));	
			objNewOrderDto.setCreditDistribution(rs.getString("CREDITDISTRIBUTION"));	
			
			GAMFormulaList.add(objNewOrderDto);
		}
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		
			try {
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(clbStmt);
				
				DbConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
			}
	}
	return GAMFormulaList;
}

/*Function:getFormulaNameList
 * Purpose:To fetch Formula Name for fill combo
 * Created by:Anil Kumar
 * Date:4-Jan-2012
 * */
public ArrayList<NewOrderDto> getFormulaNameList() 
{
	//Nagarjuna
	String methodName="getFormulaNameList", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement clbStmt =null;
	ResultSet rs = null;
	ArrayList<NewOrderDto> GAMFormulaNameList = new ArrayList<NewOrderDto>();
	NewOrderDto objNewOrderDto = null;	
	
	try
	{
		connection=DbConnection.getConnectionObject();
		
		clbStmt= connection.prepareCall(sqlGetGAMFormulaList);										
		clbStmt.setInt(1, 0);		
		clbStmt.setInt(2, 0);//is viewGamList 0 for view all new GAM list
				
		rs = clbStmt.executeQuery();
		
		while(rs.next())
		{
			objNewOrderDto =  new NewOrderDto();
			objNewOrderDto.setFormulaId(rs.getInt("FORMULAID"));
			objNewOrderDto.setFormulaName(rs.getString("FORMULANAME"));						
			GAMFormulaNameList.add(objNewOrderDto);
		}
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{			
			rs.close();
			clbStmt.close();
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
		finally
		{
			try {
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(clbStmt);
				
				DbConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
			}
		}
	}
	return GAMFormulaNameList;
}

/*Function:saveGamFormula
 *Purpose:To save GAM Formula with order
 * Created by:Anil Kumar
 * Date:04-Jan-2012
 */
public int saveGamFormula(int formulaId,int orderno,String sessionId) 
{
	//Nagarjuna
	String methodName="saveGamFormula", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement clbStmt =null;				
	int successMsg=-1;
	try
	{
		connection=DbConnection.getConnectionObject();
		
		clbStmt= connection.prepareCall(sqlSaveGAMFormula);								
		clbStmt.setInt(1,formulaId);
		clbStmt.setInt(2,orderno);	
		clbStmt.setInt(3,0);
		clbStmt.setInt(4, 0);
		clbStmt.setString(5, "");						

		clbStmt.execute();	
		
		successMsg=clbStmt.getInt(3);
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{						
			clbStmt.close();
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
		finally
		{
			try {
			
				DbConnection.closeCallableStatement(clbStmt);
				
				DbConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
			}
		}
	}
	return successMsg;
}

//End[008]
/*Function:deleteGamFormula
 *Purpose:To delete GAM Formula List with Order from  transaction table
 * Created by:Anil Kumar
 * Date:04-Jan-2012
 */
public int deleteGamFormula(int orderno,String sessionId) 
{
	//Nagarjuna
	String methodName="deleteGamFormula", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement clbStmt =null;				
	int successMsg=-1;
	try
	{
		connection=DbConnection.getConnectionObject();
		
		clbStmt= connection.prepareCall(sqldeleteGAMFormula);										
		clbStmt.setInt(1,orderno);						
		clbStmt.setInt(2,0);		
		clbStmt.setString(3, "");

		clbStmt.execute();	
		
		successMsg=clbStmt.getInt(2);
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{						
			clbStmt.close();
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
		finally
		{
			try {
				
				DbConnection.closeCallableStatement(clbStmt);
				
				DbConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
			}
		}
	}
	return successMsg;
}

//End[008]
/*Function:getFormulaAttachStatus
 *Purpose:To get formula attached status, formula already attached with order not
 * Created by:Anil Kumar
 * Date:04-Jan-2012
 */
public NewOrderDto getFormulaAttachStatus(int orderno) 
{
	//Nagarjuna
	String methodName="getFormulaAttachStatus", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement clbStmt =null;	
	ResultSet rs=null;
	NewOrderDto attachFormulaStatus=new NewOrderDto();	
	try
	{
		connection=DbConnection.getConnectionObject();
		
		clbStmt= connection.prepareCall(sqlGetAttachFormulaStatus);										
		clbStmt.setInt(1,orderno);						
		clbStmt.setInt(2,0);				

		rs = clbStmt.executeQuery();
		attachFormulaStatus.setAttachFormulaStatus(clbStmt.getInt(2));
		while(rs.next())
		{			
			attachFormulaStatus.setFormulaId(rs.getInt("FORMULAID"));
			attachFormulaStatus.setFormulaName(rs.getString("FORMULANAME"));			
		}
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{						
			clbStmt.close();
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
		finally
		{
			try {
				
				DbConnection.closeCallableStatement(clbStmt);
			
				DbConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
			}
		}
	}
	return attachFormulaStatus;
}

/*Function:isValidOrderNoForGAM
 *Purpose:To get valid OrderNo status
 * Created by:Anil Kumar
 * Date:04-Jan-2012
 */
public int isValidOrderNoForGAM(int orderno) 
{
	//Nagarjuna
	String methodName="isValidOrderNoForGAM", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement clbStmt =null;	
	int validOrderStatus=0;	
	try
	{
		connection=DbConnection.getConnectionObject();
		
		clbStmt= connection.prepareCall(sqlGetValdOrderNoStatus);										
		clbStmt.setInt(1,orderno);						
		clbStmt.setInt(2,0);				
		
		clbStmt.execute();
		validOrderStatus =clbStmt.getInt(2);
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{						
			clbStmt.close();
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
		finally
		{
			try {
			
				DbConnection.closeCallableStatement(clbStmt);
				
				DbConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
			}
		}
	}
	return validOrderStatus;
}
//lawkush start

//End[008]
/*Function:dissociateOrderGam
 *Purpose:To dissociate Order 
 * Created by:Lawkush
 * Date:12-Jan-2012
 */
public int dissociateOrderGam(String strGAMIds) 
{
	//Nagarjuna
	String methodName="dissociateOrderGam", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement clbStmt =null;				
	int successMsg=-1;
	try
	{
		connection=DbConnection.getConnectionObject();
		
		clbStmt= connection.prepareCall(sqldissociateGAMOrderList);								
		clbStmt.setString(1,strGAMIds);				
		
		clbStmt.setInt(2,0);		
		clbStmt.setString(3, "");

		clbStmt.execute();	
		
		successMsg=clbStmt.getInt(2);

		
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();	
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{						
			clbStmt.close();
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
		finally
		{
			try {
			
				DbConnection.closeCallableStatement(clbStmt);
				
				DbConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
			//	e.printStackTrace();
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
			}
		}
	}
	return successMsg;
}

//lawkush End

//lawkush start

//End[008]
/*Function:replaceOrderGam
*Purpose:To replace GAM attached with Order 
* Created by:Lawkush 
* Date:12-Jan-2012
*/
public int replaceOrderGam(String strGAMIds,int gam_id1) 
{
	//Nagarjuna
	String methodName="replaceOrderGam", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement clbStmt =null;				
	int successMsg=-1;
	try
	{
		connection=DbConnection.getConnectionObject();
		
		clbStmt= connection.prepareCall(sqlReplaceGAMOrderList);								
		clbStmt.setString(1,strGAMIds);				
		clbStmt.setInt(2,gam_id1);
		clbStmt.setInt(3,0);		
		clbStmt.setString(4, "");
		
		clbStmt.execute();	
		
		successMsg=clbStmt.getInt(3);

		
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{						
			clbStmt.close();
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
		finally
		{
			try {
				
				DbConnection.closeCallableStatement(clbStmt);
			
				DbConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
			}
		}
	}
	return successMsg;
}

public ArrayList<SITransferDto> fetchAccountwithsorting(PagingDto objDto) 
{
	//	Nagarjuna
	String methodName="fetchAccountwithsorting", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement getaccountDetails =null;
	ResultSet rsaccountDetails = null;
	ArrayList<SITransferDto> listaccountDetails = new ArrayList<SITransferDto>();
	SITransferDto objNewDto = null;
	int recordCount;
	try
	{
		connection=DbConnection.getConnectionObject();
		getaccountDetails= connection.prepareCall(sqlSpFetchAccountDetails_Paging);
		getaccountDetails.setString(1,objDto.getParty());
		getaccountDetails.setString(2,objDto.getAccountIDString());
		getaccountDetails.setString(3,objDto.getM6ShortCode());		
		getaccountDetails.setString(4,objDto.getSortBycolumn());
		getaccountDetails.setString(5,objDto.getSortByOrder());
		getaccountDetails.setInt(6,objDto.getStartIndex());
		getaccountDetails.setInt(7,objDto.getEndIndex());
		rsaccountDetails = getaccountDetails.executeQuery();
		while(rsaccountDetails.next())
		{
			objNewDto =  new SITransferDto();
			objNewDto.setAccountIDString(rsaccountDetails.getString("ACCOUNTID"));
			objNewDto.setAccountno(rsaccountDetails.getString("CRMACCOUNTNO"));
			objNewDto.setM6ShortCode(rsaccountDetails.getString("M6SHORT_CODE"));
			recordCount=rsaccountDetails.getInt("FULL_REC_COUNT");
			objNewDto.getPagingSorting().setRecordCount(recordCount);	
			objNewDto.setMaxPageNo(objNewDto.getPagingSorting().getMaxPageNumber());
			listaccountDetails.add(objNewDto);
		}
	}
	
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rsaccountDetails);
			DbConnection.closeCallableStatement(getaccountDetails);
			DbConnection.freeConnection(connection);
			
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return listaccountDetails;
}

public ArrayList<SITransferDto> getLogicalSINumberswithSorting(PagingDto objDto) 
{
	//	Nagarjuna
	String methodName="getLogicalSINumberswithSorting", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement getlogicalSIDetails =null;
	ResultSet rslogicalSIDetails = null;
	ArrayList<SITransferDto> listlogicalSIDetails = new ArrayList<SITransferDto>();
	SITransferDto objNewDto = null;
	int recordCount;
	try
	{
		connection=DbConnection.getConnectionObject();
		getlogicalSIDetails= connection.prepareCall(sqlGetLogicalSiNumberForSITransfer_paging);
		getlogicalSIDetails.setString(1, objDto.getSourcePartyNo());
		if(objDto.getLogicalSINo().equals("")){
			getlogicalSIDetails.setLong(2,0 );
		}else {
			getlogicalSIDetails.setLong(2,Integer.parseInt(objDto.getLogicalSINo()) );
		}
		getlogicalSIDetails.setString(3, objDto.getTransferdate());
		getlogicalSIDetails.setLong(4, Long.parseLong(objDto.getAccountno().trim()));
		getlogicalSIDetails.setString(5, objDto.getFilterLSIList());	
		getlogicalSIDetails.setString(6,objDto.getSortBycolumn());
		getlogicalSIDetails.setString(7,objDto.getSortByOrder());
		getlogicalSIDetails.setInt(8,objDto.getStartIndex());
		getlogicalSIDetails.setInt(9,objDto.getEndIndex());
		rslogicalSIDetails = getlogicalSIDetails.executeQuery();
		while(rslogicalSIDetails.next())
		{
			objNewDto =  new SITransferDto();
			objNewDto.setCustSINo(rslogicalSIDetails.getString("LOGICAL_SI_NO"));
			objNewDto.setServiceName(rslogicalSIDetails.getString("SERVICESTAGE"));
			objNewDto.setServiceId(rslogicalSIDetails.getInt("SERVICEID"));
			objNewDto.setAccountIDString(rslogicalSIDetails.getString("ACCOUNTID"));
			objNewDto.setAccountno(rslogicalSIDetails.getString("CRMACCOUNTNO"));
			objNewDto.setShortCode(rslogicalSIDetails.getString("M6SHORT_CODE"));
			recordCount=rslogicalSIDetails.getInt("FULL_REC_COUNT");
			objNewDto.getPagingSorting().setRecordCount(recordCount);	
			objNewDto.setMaxPageNo(objNewDto.getPagingSorting().getMaxPageNumber());
			listlogicalSIDetails.add(objNewDto);
		}
	}
	
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rslogicalSIDetails);
			DbConnection.closeCallableStatement(getlogicalSIDetails);
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return listlogicalSIDetails;
}

public ArrayList<SITransferDto> fetchBCPDetailswithsorting(PagingDto objDto) 
{
	//	Nagarjuna
	String methodName="fetchBCPDetailswithsorting", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement getBcpDetails =null;
	ResultSet rsBcpDetails = null;
	ArrayList<SITransferDto> listBcpDetails = new ArrayList<SITransferDto>();
	SITransferDto objNewDto = null;
	int recordCount;
	try
	{
		connection=DbConnection.getConnectionObject();
		getBcpDetails= connection.prepareCall(sqlSpFetchBCPDetails_Paging);
		getBcpDetails.setInt(1,Integer.parseInt(objDto.getAccountno()));
		getBcpDetails.setInt(2,objDto.getBcpID());
		getBcpDetails.setString(3,objDto.getBcpName());
	    getBcpDetails.setString(4,objDto.getSortBycolumn());
		getBcpDetails.setString(5,objDto.getSortByOrder());
		getBcpDetails.setInt(6,objDto.getStartIndex());
		getBcpDetails.setInt(7,objDto.getEndIndex());
		rsBcpDetails = getBcpDetails.executeQuery();
		while(rsBcpDetails.next())
		{
			objNewDto =  new SITransferDto();
			objNewDto.setBcpId(rsBcpDetails.getInt("BCP_ID"));
			objNewDto.setBcpname(rsBcpDetails.getString("NAME"));
			objNewDto.setBcp_details(rsBcpDetails.getString("BCP_DETAIL"));
		    recordCount=rsBcpDetails.getInt("FULL_REC_COUNT");
			objNewDto.getPagingSorting().setRecordCount(recordCount);	
			objNewDto.setMaxPageNo(objNewDto.getPagingSorting().getMaxPageNumber());
			listBcpDetails.add(objNewDto);
		}
	}
	
	catch(Exception ex )
	{
		//ex.printStackTrace();	
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rsBcpDetails);
			DbConnection.closeCallableStatement(getBcpDetails);
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return listBcpDetails;
}

public ArrayList<SITransferDto> fetchDispatchInfowithsorting(PagingDto objDto) 
{
	//	Nagarjuna
	String methodName="fetchDispatchInfowithsorting", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement getDispatchDetails =null;
	ResultSet rsDispatchDetails = null;
	ArrayList<SITransferDto> listDispatchDetails = new ArrayList<SITransferDto>();
	SITransferDto objNewDto = null;
	int recordCount;
	try
	{
		connection=DbConnection.getConnectionObject();
		getDispatchDetails= connection.prepareCall(sqlSpDispatchDetails_Paging);
		getDispatchDetails.setString(1,objDto.getAccountno().trim());
		getDispatchDetails.setInt(2,objDto.getSelectedDispatchID());
		getDispatchDetails.setString(3,objDto.getDispatch_name());
		getDispatchDetails.setString(4,objDto.getSortBycolumn());
		getDispatchDetails.setString(5,objDto.getSortByOrder());
		getDispatchDetails.setInt(6,objDto.getStartIndex());
		getDispatchDetails.setInt(7,objDto.getEndIndex());
		rsDispatchDetails = getDispatchDetails.executeQuery();
		while(rsDispatchDetails.next())
		{
			objNewDto =  new SITransferDto();
			objNewDto.setDispatchId(rsDispatchDetails.getInt("DISPATCH_ADDRESS_CODE"));
			objNewDto.setDispatchName(rsDispatchDetails.getString("NAME"));
			objNewDto.setDispatch_details(rsDispatchDetails.getString("DISPATCH_DETAILS"));
		    recordCount=rsDispatchDetails.getInt("FULL_REC_COUNT");
			objNewDto.getPagingSorting().setRecordCount(recordCount);	
			objNewDto.setMaxPageNo(objNewDto.getPagingSorting().getMaxPageNumber());
			listDispatchDetails.add(objNewDto);
		}
	}
	
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rsDispatchDetails);
			DbConnection.closeCallableStatement(getDispatchDetails);
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return listDispatchDetails;
}


/*Function:getFormulaNameList
 * Purpose:To fetch Gam orders That are already attached to the target GAM
 * Created by:Lawkush
 * Date:20-Jan-2012
 * */
public ArrayList<NewOrderDto> gamOrdersAlreadyAttachedWithTarget(String strGAMIds,int gam_id1) 
{
	//	Nagarjuna
	String methodName="gamOrdersAlreadyAttachedWithTarget", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement clbStmt =null;
	ResultSet rs = null;
	ArrayList<NewOrderDto> orderNumberList = new ArrayList<NewOrderDto>();
	NewOrderDto objNewOrderDto = null;	
	
	try
	{
		connection=DbConnection.getConnectionObject();
		
		clbStmt= connection.prepareCall(sqlGetOrdersAlreadyAttached);										
		clbStmt.setString(1, strGAMIds);		
		clbStmt.setInt(2, gam_id1);//is viewGamList 0 for view all new GAM list
				
		rs = clbStmt.executeQuery();
		
		while(rs.next())
		{
			objNewOrderDto =  new NewOrderDto();
			objNewOrderDto.setOrderNumber(rs.getInt("ORDERNO"));
			orderNumberList.add(objNewOrderDto);
		}
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();	
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{			
			rs.close();
			clbStmt.close();
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
		finally
		{
			try {
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(clbStmt);
				DbConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
			}
		}
	}
	return orderNumberList;

}
//filling dropdown dependent label for MPLS line Item
public ArrayList<FieldAttibuteDTO> fillDropDownDependentlabel(ProductCatelogDTO objDto) throws Exception 
{
	//	Nagarjuna
	String methodName="fillDropDownDependentlabel", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement callstmt =null;
	ResultSet rs = null;
	FieldAttibuteDTO objRetDto = null;
	ArrayList<FieldAttibuteDTO> DropDownDependentlabelList = new ArrayList<FieldAttibuteDTO>();
	try
	{
		connection=DbConnection.getConnectionObject();
		
		callstmt= connection.prepareCall(sqlGetDropDownDependentList);				
		callstmt.setLong(1, objDto.getAttMasterId());
		callstmt.setString(2, objDto.getAttributeLabel());
		rs = callstmt.executeQuery();
		while(rs.next())
		{
			objRetDto =  new FieldAttibuteDTO();
			objRetDto.setPlanName(rs.getString("PLAN"));
			objRetDto.setDestAttId(rs.getInt("DESTATTID"));
			objRetDto.setIsReadOnly(rs.getString("IS_READONLY"));
			//[013] Start
			objRetDto.setMandatory(rs.getString("IS_MANDATORY"));
			//[013] End
			objRetDto.setDestText(rs.getString("TEXT"));
			DropDownDependentlabelList.add(objRetDto);
		}
		
	}
	catch(Exception ex )
	{
		connection.rollback();
		//ex.printStackTrace();	
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(callstmt);
			DbConnection.freeConnection(connection);
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return DropDownDependentlabelList;

}

//filling dropdown dependent label for MPLS line Item
public ArrayList<FieldAttibuteDTO> fillAllDropDownDependentlabel(String[] attMasterIDLabels) throws Exception{
	//	Nagarjuna
	String methodName="fillAllDropDownDependentlabel", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement callstmt =null;
	ResultSet rs = null;
	ArrayList<FieldAttibuteDTO> DropDownDependentlabelList = null;
	try{
		connection=DbConnection.getConnectionObject();
		callstmt= connection.prepareCall(sqlGetAllDropDownDependentList);
		//creating , separated input
		String commaSepAttMasterId = null;
		for(String attMasterIDLabel : attMasterIDLabels){
			if(null == commaSepAttMasterId)
				commaSepAttMasterId = attMasterIDLabel.substring(0, attMasterIDLabel.indexOf("-"));
			else
				commaSepAttMasterId = commaSepAttMasterId + ", " + attMasterIDLabel.substring(0, attMasterIDLabel.indexOf("-"));
		}
		callstmt.setString(1, commaSepAttMasterId);
		rs = callstmt.executeQuery();
		String respAttMasterIdLabel;
		//generating a map containing the key as attMasterID-AttLabel and value as list of dto containing the required details
		Map<String, FieldAttibuteDTO> attIDLabelDepDropDown = null;
		FieldAttibuteDTO objRetDto = null;
		FieldAttibuteDTO depObjRetDto = null;
		while(rs.next()){
			if(null == attIDLabelDepDropDown)
				attIDLabelDepDropDown = new HashMap<String, FieldAttibuteDTO>();
			//getting the attribute id and label
			respAttMasterIdLabel = rs.getString("ATTMASTERID") + "-" + rs.getString("ATTLABEL");
			//checking whether this attlabel has been asked for
			for(String attMasterId : attMasterIDLabels){
				if(null != respAttMasterIdLabel && respAttMasterIdLabel.trim().equals(attMasterId)){
					//Getting the field attribute dto
					objRetDto = null;
					objRetDto = attIDLabelDepDropDown.get(respAttMasterIdLabel);
					if(null == objRetDto){
						objRetDto =  new FieldAttibuteDTO();
						objRetDto.setFieldLabel(rs.getString("ATTLABEL"));
						objRetDto.setAttributeID(Integer.valueOf(rs.getString("ATTMASTERID")));
						attIDLabelDepDropDown.put(respAttMasterIdLabel, objRetDto);
					}
					//getting the service summary list
					ArrayList<FieldAttibuteDTO> attMasterDepDropDown = objRetDto.getServiceSummary();
					//checking if null
					if(null == attMasterDepDropDown){
						attMasterDepDropDown = new ArrayList<FieldAttibuteDTO>();
						objRetDto.setServiceSummary(attMasterDepDropDown);
					}
					depObjRetDto = new FieldAttibuteDTO();
					depObjRetDto.setPlanName(rs.getString("PLAN"));
					depObjRetDto.setDestAttId(rs.getInt("DESTATTID"));
					depObjRetDto.setIsReadOnly(rs.getString("IS_READONLY"));
					depObjRetDto.setMandatory(rs.getString("IS_MANDATORY"));
					depObjRetDto.setDestText(rs.getString("TEXT"));
					objRetDto.getServiceSummary().add(depObjRetDto);
					break;
				}
			}
		}
		if(null != attIDLabelDepDropDown && !attIDLabelDepDropDown.isEmpty()){
			if(null == DropDownDependentlabelList){
				DropDownDependentlabelList = new ArrayList<FieldAttibuteDTO>();
			}
			DropDownDependentlabelList.addAll(attIDLabelDepDropDown.values());
		}
	}catch(Exception ex){
		connection.rollback();
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}finally{
		try{
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(callstmt);
			DbConnection.freeConnection(connection);
		}catch (SQLException e){
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return DropDownDependentlabelList;
}

/*Function:isFormulaAttachedWithOrder
 *Purpose:To get whether Formula attached with order or not
 * Created by:Lawkush
 * Date:30-Jan-2012
 */
public int isFormulaAttachedWithOrder(int orderno) 
{
	//	Nagarjuna
	String methodName="isFormulaAttachedWithOrder", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement clbStmt =null;	
	int isFormulaAttachedWithOrder=0;	
	try
	{
		connection=DbConnection.getConnectionObject();
		
		clbStmt= connection.prepareCall(sqlGetValdFormulaStatus);										
		clbStmt.setInt(1,orderno);						
		clbStmt.setInt(2,0);				
		
		clbStmt.execute();
		isFormulaAttachedWithOrder =clbStmt.getInt(2);
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{						
			clbStmt.close();
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
		finally
		{
			try {
				
				DbConnection.closeCallableStatement(clbStmt);
			
				DbConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
			}
		}
	}
	return isFormulaAttachedWithOrder;
}

//lawkush 

/*Function:isGamAttachedWithOrder
 *Purpose:To get whether Formula attached with order or not
 * Created by:Lawkush
 * Date:30-Jan-2012
 */
public int isGamNFormulaAttachedWithOrder(int orderno) 
{
	//	Nagarjuna
	String methodName="isGamNFormulaAttachedWithOrder", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement clbStmt =null;	
	int isGamNFormulaAttachedWithOrder=1;	
	ResultSet rs =null;
	int totalGam=0;
	try
	{
		connection=DbConnection.getConnectionObject();
//This validation part moved to procedure  && commented by Lawkush	
	/*	
	 	clbStmt=connection.prepareCall("SELECT COUNT(1) AS TOTALGAM FROM IOE.TGAM_ORDER_MAPPING  WHERE ORDERNO=?");
	 	clbStmt.setInt(1,orderno);	
		
	 	rs=clbStmt.executeQuery();
		
		
	 	while(rs.next())
	 	{
	 		totalGam=rs.getInt("TOTALGAM");
	 	}
		
	 	if(totalGam!=0)
	 	{
		*/
			clbStmt= connection.prepareCall(sqlGetGamNFormulaAttachedWithOrderStatus);										
			clbStmt.setInt(1,orderno);						
			clbStmt.setInt(2,0);				
			
			clbStmt.execute();
			isGamNFormulaAttachedWithOrder =clbStmt.getInt(2);
	//	}
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{						
			clbStmt.close();
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
		finally
		{
			try {
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(clbStmt);
				DbConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
			}
		}
	}
	return isGamNFormulaAttachedWithOrder;
}

//lawkush 




public LineItemDTO getMplsLineCount(NewOrderDto objDto) throws Exception 
{
	//	Nagarjuna
	String methodName="getMplsLineCount", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement clblstmt =null;
	LineItemDTO objNewOrderDto = new LineItemDTO();
	try
	{
		connection=DbConnection.getConnectionObject();			
		clblstmt= connection.prepareCall(sqlGetMplsLineCount);
		clblstmt.setInt(1,objDto.getServiceId());
		clblstmt.setInt(2,0);
		clblstmt.setInt(3,0);
		clblstmt.setInt(4,0);
		clblstmt.execute();
		objNewOrderDto.setUserBasedCount(clblstmt.getInt(2));
		objNewOrderDto.setSiteBasedCount(clblstmt.getInt(3));
		objNewOrderDto.setFlexiCount(clblstmt.getInt(4));
	}
	catch(Exception ex )
	{
		connection.rollback();
		//ex.printStackTrace();	
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
		
			DbConnection.closeCallableStatement(clblstmt);
			
			DbConnection.freeConnection(connection);
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return objNewOrderDto;
}

/*Function:getOrderStateforClep
 * Created by:Anil Kumar
 * Purpose:to fetch order state for enable disable for clep order
 * Date:20-Mar-2012
 * */
public int getOrderStateforClep(long orderNo) throws Exception
{
	//	Nagarjuna
	String methodName="getOrderStateforClep", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement cstmt =null;
	
	int orderState=0;
	try
	{
		connection=DbConnection.getConnectionObject();
		cstmt= connection.prepareCall(sqlGetOrderStateForCLEP);
		cstmt.setLong(1, orderNo);
		cstmt.setInt(2, 0);
		cstmt.execute();
		
		orderState=cstmt.getInt(2);
				
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		orderState=0;
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
		DbConnection.closeCallableStatement(cstmt);
		
			DbConnection.freeConnection(connection);
		} 
		catch (SQLException e) 
		{
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return orderState;
}
//end Function:getOrderStateforClep
public ArrayList<String>  fnGetValidationDataForComponents(long componentInfoId)
{
	//	Nagarjuna
	String methodName="fnGetValidationDataForComponents", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	
	Connection connection =null;
	CallableStatement cs =null;
	String startDate = null;
	String endDate=null;
	//String compStartLogic=null;
	//String compEndLogic=null;
	ResultSet rs = null;
	//SimpleDateFormat simpleDateFormat = new SimpleDateFormat(AppConstants.DATE_FORMAT_PROC);
	ArrayList<String> startAndEndDate=new ArrayList<String>();

	try{
		connection=DbConnection.getConnectionObject();
		connection.setAutoCommit(false);
		cs=connection.prepareCall(sqlgetValidationDataForComponents);
		cs.setLong(1, new Long(componentInfoId));
		rs = cs.executeQuery();
		
		while(rs.next())
		{
			
			//startDate = (simpleDateFormat.format(new java.util.Date(rs.getString("SYSTEM_START_DATE"))));
			startDate = rs.getString("SYSTEM_START_DATE");
			endDate=rs.getString("SYSTEM_END_DATE");
			
			startAndEndDate.add(startDate);
			startAndEndDate.add(endDate);
			
		     
		}
	}
	catch(Exception ex)
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return startAndEndDate;		

	
}

public static String sqlInsertDisconnectionDateForComponent= "{call IOE.SP_INSERT_DISCONNECTION_DATE_FOR_COMPONENTS(?,?,?,?,?)}";
public String  fnInsertDisconnectionDateForComponent(String disconnectionDate, String componentInfoId)
{
	//	Nagarjuna
	String methodName="fnInsertDisconnectionDateForComponent", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement cs =null;
	String result = null;
	int finalStatus=0;
	
	
	try{
		connection=DbConnection.getConnectionObject();
		connection.setAutoCommit(false);
		cs=connection.prepareCall(sqlInsertDisconnectionDateForComponent);
		cs.setLong(1, new Long(componentInfoId));
		cs.setString(2,disconnectionDate);
		/*cs.setInt(3,0);//Output Parameter
		cs.setInt(4,0);//Output Parameter
		cs.setString(5,"");//Output Parameter	
*/		
		cs.registerOutParameter(3,java.sql.Types.BIGINT);
		cs.registerOutParameter(4,java.sql.Types.BIGINT);
		cs.registerOutParameter(5,java.sql.Types.VARCHAR);
		cs.execute();
		finalStatus=cs.getInt(4);//Output Parameter
		
			 if(finalStatus==0)
				{
				 result="Data Updated SuccessFully!!";
				connection.commit();
				}
				else
				{
					
					connection.rollback();
				}
	}
	catch(Exception ex)
	{
		//ex.printStackTrace();	
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	
	
	return result;
	
}
public ComponentDTO disconnectComponent(String componentinfoid,String disconnectionType,String isRevertDisconnection)throws Exception
{
	//	Nagarjuna
	String methodName="disconnectComponent", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement callstmt =null;
	int status=-1;
	//ArrayList<ComponentDTO> disconnstatus=new ArrayList<ComponentDTO>();
	ComponentDTO objCompDto=new ComponentDTO();
	try
	{
		connection=DbConnection.getConnectionObject();
		connection.setAutoCommit(false);
		callstmt= connection.prepareCall(sqlspDisconnectComponent);	
		callstmt.setLong(1, Long.valueOf(componentinfoid));
		callstmt.setString(2,disconnectionType);
		callstmt.setNull(3, java.sql.Types.INTEGER);
		callstmt.setString(4, isRevertDisconnection);
		callstmt.setString(5, "");
		callstmt.setNull(6, java.sql.Types.INTEGER);
		callstmt.setNull(7, java.sql.Types.INTEGER);
		callstmt.setString(8, "");
		
		callstmt.execute();
		status=callstmt.getInt(3);
		objCompDto.setReturnStatus(status);
		objCompDto.setComponentStatus(callstmt.getString(5));
		objCompDto.setCompEndDays(callstmt.getInt(6));
		objCompDto.setCompEndMonths(callstmt.getInt(7));
		objCompDto.setCompEndLogic(callstmt.getString(8));
		//disconnstatus.add(objCompDto);
		
		if(status==0){
			connection.commit();
		}else{
			connection.rollback();
		}
		
	}
	catch(Exception ex )
	{		
		connection.rollback();
		//ex.printStackTrace();	
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeCallableStatement(callstmt);
			DbConnection.freeConnection(connection);
			
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return objCompDto;
}
//010 START

public String deleteComponents(NewOrderDto objDto) throws Exception 
{
	//	Nagarjuna
	String methodName="deleteComponents", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement callstmt =null;
	ResultSet rs = null;
	String result=null;
	
	
	try
	{
		connection=DbConnection.getConnectionObject();
		connection.setAutoCommit(false);
		callstmt= connection.prepareCall(sqlDeleteComponents);
		callstmt.setString(1, objDto.getComponentIDList());
		callstmt.setInt(2, 0);
		callstmt.setInt(3, 0);
		callstmt.setString(4, "");
		callstmt.execute();
		int err = callstmt.getInt(3);
		
		if(err==0)
		{
			result="Component Detail Deleted Successfully";
			connection.commit();
		}
		
	}
	catch(Exception ex )
	{
		connection.rollback();
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.freeConnection(connection);
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return result;
}
//010 END


//011 START

public String  savecompoenentdata(NewOrderDto objDto) throws Exception 
{
	//	Nagarjuna
	String methodName="savecompoenentdata", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement cs =null;
	String result = null;
	int finalStatus=0;
	int successcount=0;
	
	
	try{
		connection=DbConnection.getConnectionObject();
		connection.setAutoCommit(false);
		cs=connection.prepareCall(sqlInsertComponentsDetails);
		cs.clearParameters();
		cs.setInt(1, objDto.getAccountID());
		cs.setInt(2, objDto.getServiceProductID());
		cs.setInt(3, objDto.getComponentID());
		cs.setNull(4,java.sql.Types.INTEGER);
		cs.setNull(5,java.sql.Types.INTEGER);
		cs.setNull(6,java.sql.Types.VARCHAR);
		if(objDto.getComponentInfoID() == 0)
		{
			cs.setInt(7, 1);
			cs.setInt(8, 0);
			
		}else
		{
			cs.setInt(7, 2);
			cs.setInt(8, objDto.getComponentInfoID());
		}
		if(objDto.getStartDateLogic()==null){
			objDto.setStartDateLogic("BTD");
		}
		if(objDto.getEndDateLogic()==null){
			objDto.setEndDateLogic("TD");
		}
				
		cs.setInt(9, objDto.getPackageID());
		cs.setInt(10, objDto.getServiceId());
		cs.setNull(11, java.sql.Types.INTEGER);
		cs.setString(12, objDto.getStartDateLogic());
		cs.setString(13, objDto.getEndDateLogic());
		cs.setInt(14, objDto.getStartDateDays());//Start Date Days
		cs.setInt(15, objDto.getStartDateMonth());//Start Date Month
		cs.setInt(16, objDto.getEndDateDays());//End Date Days
		cs.setInt(17, objDto.getEndDateMonth());//End Date Month
		cs.execute();
		finalStatus=cs.getInt(5);
		
		
		if(finalStatus==0)
				{
				 result="Data Updated SuccessFully!!";
				
				connection.commit();
				}
				else
				{
					result="Some error has occurred!!";
					connection.rollback();
				}
	}
	catch(Exception ex)
	{
		result="Some error has occurred!!";
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
			//e.printStackTrace();
		}
	}
	
	return result;
	
	
}
//lawkush start

public ArrayList<NewOrderDto> fileAttachmentType(int docType) 
{
	//	Nagarjuna
	String methodName="fileAttachmentType", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement pstmt =null;
	ResultSet rs = null;
	NewOrderDto objNewOrderDto = null;
	ArrayList<NewOrderDto> fileAttachmentTypeList =new ArrayList<NewOrderDto>();
	try
	{
		connection=DbConnection.getConnectionObject();
		pstmt= connection.prepareCall(sqlPopulateFileType);
		pstmt.setInt(1, docType);
		rs = pstmt.executeQuery();
		while(rs.next())
		{
			objNewOrderDto =  new NewOrderDto();
			objNewOrderDto.setFileTypeId(rs.getInt("DOCID"));
			objNewOrderDto.setFileType(rs.getString("DOCUMENTNAME"));
			fileAttachmentTypeList.add(objNewOrderDto);
		}		 
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			rs.close();
			pstmt.close();
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(pstmt);
			
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return fileAttachmentTypeList;
}

//lawkush End
public ArrayList<NewOrderDto> getOrderListForPendingWithPM(NewOrderDto dto)
{
	//	Nagarjuna
	String methodName="getOrderListForPendingWithPM", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement pstmt =null;
	ResultSet rs = null;
	ArrayList<NewOrderDto> listOrder = new ArrayList<NewOrderDto>();
	try
	{
		connection=DbConnection.getConnectionObject();
		pstmt= connection.prepareCall(sqlGetOrderListPendingwithPM);
		
		
		if (dto.getCrmAccountNo()!= 0) {
			pstmt.setLong(1,dto.getCrmAccountNo());
		} else {
			pstmt.setNull(1, java.sql.Types.BIGINT);
		}	
		if (dto.getOrderNo() == null || dto.getOrderNo().trim().equals("")) {
			pstmt.setNull(2, java.sql.Types.BIGINT);
		} else {
			pstmt.setLong(2,Long.parseLong(dto.getOrderNo()));
		}
		if (dto.getProjectManagerID()!= 0) {
			pstmt.setLong(3,dto.getProjectManagerID());
		} else {
			pstmt.setNull(3, java.sql.Types.BIGINT);
		}
		
		pstmt.setInt(4,dto.getEmployeeid());
		
		rs = pstmt.executeQuery();
		NewOrderDto returnDto = null;
		while(rs.next())
		{
			returnDto = new NewOrderDto();
			returnDto.setOrderNo(rs.getString("ORDERNO"));
			returnDto.setAcmgrEmail(rs.getString("ACTMGREMAILID"));
			returnDto.setProjectmgremail(rs.getString("PRJMGREMAILID"));
			returnDto.setProjectManager(rs.getString("PRJMGRID"));
			returnDto.setInitial_pm(rs.getString("INITIALPM"));
			returnDto.setLast_pm(Utility.fnCheckNull(rs.getString("LASTPM")));
			returnDto.setLast_changed_by(Utility.fnCheckNull(rs.getString("LAST_CHANGED_BY")));
			listOrder.add(returnDto);
		}
		
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(pstmt);
			
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return listOrder;
}

public NewOrderDto getUsageBasedLineCount(NewOrderDto objDto) throws Exception 
{
	//	Nagarjuna
	String methodName="getUsageBasedLineCount", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement clblstmt =null;
	NewOrderDto objNewOrderDto = new NewOrderDto();
	try
	{
		connection=DbConnection.getConnectionObject();			
		clblstmt= connection.prepareCall(sqlGetUsageBasedLineCount);
		clblstmt.setInt(1,objDto.getServiceId());
		clblstmt.setInt(2,objDto.getServiceDetailID());
		clblstmt.setInt(3,objDto.getServiceProductID());
		clblstmt.setInt(4,objDto.getIsUsage());
		clblstmt.setInt(5,0);
		clblstmt.execute();
		objNewOrderDto.setUsageBasedLineCount(clblstmt.getInt(5));
	}
	catch(Exception ex )
	{
		connection.rollback();
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.freeConnection(connection);
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return objNewOrderDto;
}	
public String reassignPM(String reassignpmList,String reassignpmnameList,String orderNoList,int roleid,String lastpmidList,String lastpmemailList,String actmgrlist) throws SQLException
{
	//	Nagarjuna
	String methodName="reassignPM", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	ArrayList<String> reassignpm = new ArrayList<String>();
	ArrayList<String> reassignpm_emailid = new ArrayList<String>();
	ArrayList<String> order = new ArrayList<String>();
	ArrayList<String> actmgremailid = new ArrayList<String>();
	ArrayList<String> lastpmemailid = new ArrayList<String>();
	ArrayList<String> lastpmid = new ArrayList<String>();
	ArrayList<String> reassignpmname = new ArrayList<String>();
	Statement statement = null;
	String output ="";
	String output_insert="";
	int isMailSend = 0;
	NewOrderDao objDao = new NewOrderDao();
	PreparedStatement ps=null;
	IB2BMailDto objdto=new IB2BMailDto();
	int k=0;
	int arrLen=0;
	try
	{
		connection=DbConnection.getConnectionObject();
		connection.setAutoCommit(false);
		 // 013 start
		
		// to get reassign manager ids , emailid 
		arrLen=reassignpmList.split(",").length;
		String[] eachLine=reassignpmList.split(",");
		for(int i=1;i<=arrLen;i++)
		{	 
			String str=eachLine[i-1];
			String individual[]=str.split(":");
			reassignpm.add(individual[0]);
			reassignpm_emailid.add(individual[1]);
		}
		
		// to get orderno
		StringTokenizer st_order  = new StringTokenizer( orderNoList, ",");
		for (int i =0; st_order.hasMoreTokens();i++) 
		{
			order.add(st_order.nextToken());
		}
		
		StringTokenizer st_actmgr  = new StringTokenizer( actmgrlist, ",");
		for (int i =0; st_actmgr.hasMoreTokens();i++) 
		{
			actmgremailid.add(st_actmgr.nextToken());
		}
		
		StringTokenizer st_lastpmemailid  = new StringTokenizer( lastpmemailList, ",");
		for (int i =0; st_lastpmemailid.hasMoreTokens();i++) 
		{
			lastpmemailid.add(st_lastpmemailid.nextToken());
		}
		
		StringTokenizer st_lastpmid  = new StringTokenizer( lastpmidList, ",");
		for (int i =0; st_lastpmid.hasMoreTokens();i++) 
		{
			lastpmid.add(st_lastpmid.nextToken());
		}
		
		// reassignpm name list
		
		StringTokenizer st_reassignpmname  = new StringTokenizer( reassignpmnameList, ",");
		for (int i =0; st_reassignpmname.hasMoreTokens();i++) 
		{
			reassignpmname.add(st_reassignpmname.nextToken());
		}
		
		
	    statement = connection.createStatement();
	    for(int count=0; count<order.size();count++)
	    {
		    statement.addBatch("update ioe.TPOWORKFLOWTASK set TASK_ASSIGNED_TO = "  + reassignpm.get(count) + ", LAST_ASSGINED_BY=" +roleid+ " where orderno = " + order.get(count) + " and OWNERTYPE_ID = 2");
		   
	    }
	    int[] recordsAffected = statement.executeBatch();
	    for(int index = 0 ; index < recordsAffected.length; index++)
	    {
	    	if(recordsAffected[index] == 0 )
	    	{
	    		if(output.equals(""))
			    	output= output+ order.get(index) ;
	    		else
	    			output= output+ ","+ order.get(index) ;
	    	}
	    		
	    }
		   if(output.equals(""))
		   {
			  
			   String query="INSERT INTO IOE.TREASSIGN_PM_HISTORY" +
				"(ORDERNO,LAST_PM,REASSIGNED_PM,REASSIGN_DATE,REASSIGNED_BY) " +
				"values(?,?,?,?,?)";
			   ps = connection.prepareStatement(query);
			   for(int count=0; count<order.size();count++)
			    {
				   	ps.setInt(1,Integer.parseInt((order.get(count))));
			    	ps.setInt(2,Integer.parseInt(lastpmid.get(count)));
				    ps.setInt(3,Integer.parseInt((reassignpm.get(count))));
				    java.util.Date date= new java.util.Date();
				    ps.setTimestamp(4,new Timestamp(date.getTime()));
				    ps.setInt(5,roleid);
			    	ps.addBatch();
			    	k++;
				   
			    }
			    int[] updateCounts = ps.executeBatch();
			    for(int index = 0 ; index < updateCounts.length; index++)
			    {
			    	if(updateCounts[index] == 0 )
			    	{
			    		if(output_insert.equals(""))
			    			output_insert= output_insert+ order.get(index) ;
			    		else
			    			output_insert= output_insert+ ","+ order.get(index) ;
			    	}
			    		
			    }
			    if(output_insert.equals(""))
			    {
					connection.commit();
					output="PM Update Successfully";					
			    	 if(Utility.getAppConfigValue("IS_SEND_MAIL_TO_REASSIGNPM").equalsIgnoreCase("Y"))
						{
			    		 	Utility.LOG("--Sending mail to re-assign pm start--->");
			    		 	objdto.setMailTemplateType(AppConstants.REASSIGNPMTEMPLATE);
			    		 	for(int count=0; count<order.size();count++)
						    {
			    		 		objdto.setTo(new String[]{actmgremailid.get(count),reassignpm_emailid.get(count),lastpmemailid.get(count)});
			    		 		objdto.setOrderNo(order.get(count));
			    		 		objdto.setReassigned_pm(reassignpmname.get(count));
								isMailSend = IB2BMail.sendiB2BMail(objdto, connection,true,true);
								if(isMailSend==1)
								{
									String success= "Reassign PM Mail Sent Successfully for OrderNumber: "+ order.get(count);
									com.ibm.ioes.utilities.Utility.LOG(success);
								}
							    else
							    {
									String failure= " Reassign PM Mail Sending Failed  for OrderNumber: " + order.get(count);
									com.ibm.ioes.utilities.Utility.LOG(failure);
								}
			    			}	
						}
				} 
				else 
				{
					   output= "Update of " + output_insert + "failed. Retry";
					   connection.rollback();
				}
			    
		   }
		   else
		   {
			   output= "Update of " + output + "failed. Retry";
			   connection.rollback();
		   }
	// 013 end
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		connection.rollback();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeStatement(statement);
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			connection.rollback();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return output;
}
public ArrayList<PoDetailsDTO> getPOListForOrder(long orderNo) throws Exception 
{
	//	Nagarjuna
	String methodName="getPOListForOrder", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement callstmt =null;
	ResultSet rs = null;
	PoDetailsDTO objRetDto = null;
	ArrayList<PoDetailsDTO> poList = new ArrayList<PoDetailsDTO>();
	try
	{
		connection=DbConnection.getConnectionObject();
		//Modified the procedure for performance tuning - Puneet
		//Now instead of case statement in where clause, two different statements will be executed
		callstmt= connection.prepareCall(sqlGetPOList_MODIFIED);
		callstmt.setLong(1,orderNo);
		rs = callstmt.executeQuery();
		while(rs.next())
		{
			objRetDto =  new PoDetailsDTO();
			objRetDto.setPodetailID(rs.getInt("PODETAILID"));
			poList.add(objRetDto);
		}
		
	}
	catch(Exception ex )
	{
		connection.rollback();
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(callstmt);
			DbConnection.freeConnection(connection);
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return poList;

}
public String checkBillingLevelForOrder(long orderNo) {
	//	Nagarjuna
	String methodName="checkBillingLevelForOrder", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement pstmt =null;
	String billingLevelCheck_ServiceNos=null;
	
	try
	{
		connection=DbConnection.getConnectionObject();
		pstmt= connection.prepareCall(sqlValidateBillingLevelForOrder);
		pstmt.setLong(1, orderNo);
		pstmt.registerOutParameter(2, java.sql.Types.VARCHAR);
		pstmt.execute();
		billingLevelCheck_ServiceNos=pstmt.getString(2);
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			pstmt.close();
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return billingLevelCheck_ServiceNos;
}
public M6OrderStatusDto fetchConfigValue(M6OrderStatusDto objDto) throws Exception 
{
	//	Nagarjuna
	String methodName="fetchConfigValue", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement callstmt =null;
	CallableStatement csConfigAttvalue =null;
	ResultSet rs = null;	
	M6OrderStatusDto configValueDto = new M6OrderStatusDto();
	long configvalue=0;
	try
	{
		connection=DbConnection.getConnectionObject();
		configvalue=objDto.getConfigValue();
		if(objDto.getConfigValue()==0)
		{
			callstmt= connection.prepareCall(sqlGetConfigValue);
			callstmt.setLong(1,objDto.getServiceDetailID());
			callstmt.setLong(2, objDto.getM6AttributeId());
			callstmt.setString(3, objDto.getM6AttributeValue());
			
			rs = callstmt.executeQuery();
			while(rs.next())
			{						
				configValueDto.setConfigValue(rs.getInt("CONFIG_ID"));
				//configValueDto.setM6AttributeId(rs.getInt("SERVICE_SUMMARY_ATT_ID"));
				//configValueDto.setM6AttributeValue(rs.getString("SERVICE_SUMMARY_ATT_VALUE"));			
			}
			configvalue=configValueDto.getConfigValue();
		}
		
		csConfigAttvalue= connection.prepareCall(sqlGetConfigAttributeValue);
		csConfigAttvalue.setLong(1,objDto.getServiceDetailID());//SERVICEDETAILID
		csConfigAttvalue.setLong(2, configvalue);//CONFIGID
		csConfigAttvalue.setLong(3, objDto.getServiceId());//SERVICEID
		csConfigAttvalue.setLong(4, objDto.getM6AttributeId());//SERVICE_SUMMARY_ATT_ID
		csConfigAttvalue.setString(5, objDto.getM6AttributeValue());//SERVICE_SUMMARY_ATT_VALUE
		csConfigAttvalue.setLong(6, objDto.getServiceProductID());//SERVICEPRODUCTID
		csConfigAttvalue.setInt(7, objDto.getOldConfigValue());//OLD CONFIGID
		csConfigAttvalue.setInt(8, objDto.getToComputeAlert());//ALERT CONFIGURATION
		csConfigAttvalue.setInt(9,0);//OUT @TOTALLINEITEM
		csConfigAttvalue.setInt(10,0);//OUT @MAXLINEALLOW
		csConfigAttvalue.setString(11,"");//OUT @ISUSAGEALLOW
		csConfigAttvalue.setInt(12,0);//OUT @COMPINFO
		csConfigAttvalue.setInt(13,0);//OUT @CHARGEINFO
		csConfigAttvalue.setString(14,"");//OUT @NEW CONFIG ALERT
		csConfigAttvalue.setInt(15,0);//OUT DUMMY FLAG
		csConfigAttvalue.setInt(16,0);//OUT DUMMY COUNT
		csConfigAttvalue.setInt(17,0);//OUT NON DUMMY COUNT
		csConfigAttvalue.setString(18,"");//OUT FXCHARGE REDIRECTION TYPE
		csConfigAttvalue.setString(19,"");//OUT FXCHARGE REDIRECTION TYPE:CUMU
		//start [015]
		csConfigAttvalue.registerOutParameter(20,java.sql.Types.VARCHAR);//OUT FLI_PO_DISABLE 
		//end [015]
		csConfigAttvalue.execute();
		
		configValueDto.setTotalLineItemAttached(csConfigAttvalue.getInt(9));
		configValueDto.setMaxLineItemAllow(csConfigAttvalue.getInt(10));
		configValueDto.setIsUsageAllow(csConfigAttvalue.getString(11));
		configValueDto.setCompInfo(csConfigAttvalue.getInt(12));
		configValueDto.setChargeInfo(csConfigAttvalue.getInt(13));
		if(csConfigAttvalue.getString(14)!=null)
		{
			configValueDto.setNewConfigAlert(Messages.getMessageValue(csConfigAttvalue.getString(14)));
		}
		configValueDto.setIsDummy(csConfigAttvalue.getInt(15));
		configValueDto.setDummyLineItem(csConfigAttvalue.getInt(16));
		configValueDto.setNonDummyLineItem(csConfigAttvalue.getInt(17));
		configValueDto.setFxChargeRedirectionType(csConfigAttvalue.getString(18));
		configValueDto.setFxChargeRedirectionTypeCumulative(csConfigAttvalue.getString(19));
		//start[015]
		configValueDto.setFli_po_disable(csConfigAttvalue.getString(20));
		//end[015]
	}
	catch(Exception ex )
	{
		connection.rollback();
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.freeConnection(connection);
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return configValueDto;
}

private final String sqlMplsValidationForOrder = "call IOE.SP_VALIDATE_MPLS_FOR_COMPLETE_ORDER(?,?)";

public String checkMplsValidationForOrder(long orderNo) {
	//	Nagarjuna
	String methodName="checkMplsValidationForOrder", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement cstmt =null;
	String mplsValidationCheck_ServiceNos=null;
	
	try
	{
		connection=DbConnection.getConnectionObject();
		cstmt= connection.prepareCall(sqlMplsValidationForOrder);
		cstmt.setLong(1, orderNo);
		cstmt.registerOutParameter(2, java.sql.Types.VARCHAR);
		cstmt.execute();
		mplsValidationCheck_ServiceNos=cstmt.getString(2);
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeCallableStatement(cstmt);
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return mplsValidationCheck_ServiceNos;
}
public ArrayList<M6OrderStatusDto> fetchBillingMode(int configValue) throws Exception 
{
	//	Nagarjuna
	String methodName="fetchBillingMode", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement callstmt =null;
	ResultSet rs = null;	
	M6OrderStatusDto objDto=null;
	ArrayList<M6OrderStatusDto> billingModeList = new ArrayList<M6OrderStatusDto>();
	try
	{
		connection=DbConnection.getConnectionObject();
		
		callstmt= connection.prepareCall(sqlGetBillingMode);
		callstmt.setLong(1,configValue);				
		rs = callstmt.executeQuery();
		
		while(rs.next())
		{					
			objDto=new M6OrderStatusDto();
			objDto.setBillingModeId(rs.getInt("BILLINGMODEID"));			
			objDto.setBillingModeName(rs.getString("BILLINGMODENAME"));	
			billingModeList.add(objDto);
		}
		
	}
	catch(Exception ex )
	{
		connection.rollback();
		//ex.printStackTrace();	
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.freeConnection(connection);
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return billingModeList;
}
public String fetchAddSubNodeAllowStatus(NewOrderDto objDto) throws Exception 
{
	//	Nagarjuna
	String methodName="fetchAddSubNodeAllowStatus", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement clblstmt =null;
	String isAllowSubnode="";
	try
	{
		connection=DbConnection.getConnectionObject();			
		clblstmt= connection.prepareCall(sqlGetisAddSubNodeAllowStatus);		
		clblstmt.setInt(1,objDto.getServiceProductID());		
		clblstmt.setString(2,"");//OUT isAllowSubnode
		clblstmt.execute();
		isAllowSubnode=clblstmt.getString(2);
	}
	catch(Exception ex )
	{
		connection.rollback();
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.freeConnection(connection);
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return isAllowSubnode;
}

public ArrayList<NewOrderDto> getVCS_BridgeLSIListWithSorting(PagingDto objDto) 
{
	//	Nagarjuna
	String methodName="getVCS_BridgeLSIListWithSorting", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement cstmt =null;
	ResultSet rs = null;
	ArrayList<NewOrderDto> listArborLSIDetails = new ArrayList<NewOrderDto>();
	NewOrderDto objNewOrderDto = null;
	int recordCount;
	try
	{
		connection=DbConnection.getConnectionObject();
		cstmt= connection.prepareCall(sqlspGetVCS_BridgeLSIListWithSorting);		
		cstmt.setInt(1,objDto.getOrderNumber());
		cstmt.setInt(2,objDto.getLogicalSINumber());//LSI entered from search textbox		
		cstmt.setString(3,objDto.getSortBycolumn());
		cstmt.setString(4,objDto.getSortByOrder());
		cstmt.setInt(5,objDto.getStartIndex());
		cstmt.setInt(6,objDto.getEndIndex());
	System.out.println("OVCC   "+objDto.getServiceDetailID());
		cstmt.setInt(7,objDto.getServiceDetailID());//VCS-ISP MApping as on date 12-feb-2013 : Ashutosh
		rs = cstmt.executeQuery();
		while(rs.next())
		{
			objNewOrderDto =  new NewOrderDto();
		objNewOrderDto.setServiceName(rs.getString("SERVICESTAGE"));
			objNewOrderDto.setLogicalSINumber(rs.getInt("LOGICAL_SI_NO"));
			objNewOrderDto.setServiceId(rs.getInt("SERVICEID"));
			objNewOrderDto.setOrderNumber(rs.getInt("ORDERNO"));
			recordCount=rs.getInt("FULL_REC_COUNT");
			objNewOrderDto.getPagingSorting().setRecordCount(recordCount);	
			objNewOrderDto.setMaxPageNo(objNewOrderDto.getPagingSorting().getMaxPageNumber());
			listArborLSIDetails.add(objNewOrderDto);			
		}
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(cstmt);
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return listArborLSIDetails;
}
public ArrayList<NewOrderDto> getArborLSIListWithSorting(PagingDto objDto,Connection connection) 
{
	//	Nagarjuna
	String methodName="getArborLSIListWithSorting", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	//Connection connection =null;
	CallableStatement cstmt =null;
	ResultSet rs = null;
	ArrayList<NewOrderDto> listArborLSIDetails = new ArrayList<NewOrderDto>();
	NewOrderDto objNewOrderDto = null;
	int recordCount;
	try
	{
		//connection=DbConnection.getConnectionObject();
		cstmt= connection.prepareCall(sqlspGetArborLSIListWithSorting);
		cstmt.setInt(1,objDto.getServiceDetailID());
		cstmt.setInt(2,objDto.getOrderNumber());
		cstmt.setInt(3,objDto.getLogicalSINumber());//LSI entered from search textbox
		cstmt.setInt(4,Integer.parseInt(objDto.getLogicalSINo()));//CurrentLSI from where we are doing the lookup
		cstmt.setInt(5,objDto.getFxInternalId());
		cstmt.setInt(6,objDto.getIsDummy());
		cstmt.setLong(7,objDto.getConfigValue());
		cstmt.setInt(8,objDto.getNoofuses());
		cstmt.setString(9,objDto.getSortBycolumn());
		cstmt.setString(10,objDto.getSortByOrder());
		cstmt.setInt(11,objDto.getStartIndex());
		cstmt.setInt(12,objDto.getEndIndex());
		rs = cstmt.executeQuery();
		while(rs.next())
		{
			objNewOrderDto =  new NewOrderDto();
			objNewOrderDto.setLogicalSINo(rs.getString("LOGICAL_SI_NO"));
			objNewOrderDto.setFxInternalId(rs.getInt("INTERNAL_ID"));
			objNewOrderDto.setServiceProductID(rs.getInt("SERVICEPRODUCTID"));
			recordCount=rs.getInt("FULL_REC_COUNT");
			objNewOrderDto.getPagingSorting().setRecordCount(recordCount);	
			objNewOrderDto.setMaxPageNo(objNewOrderDto.getPagingSorting().getMaxPageNumber());
			listArborLSIDetails.add(objNewOrderDto);			
		}
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(cstmt);
			//DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return listArborLSIDetails;
}
public ArrayList<M6OrderStatusDto> fillComponentStartDateLogic() throws Exception 
{
	//	Nagarjuna
	String methodName="fillComponentStartDateLogic", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement callstmt =null;
	ResultSet rs = null;	
	M6OrderStatusDto objDto=null;
	ArrayList<M6OrderStatusDto> startDateLogicList = new ArrayList<M6OrderStatusDto>();
	try
	{
		connection=DbConnection.getConnectionObject();
		
		callstmt= connection.prepareCall(sqlGetCompStartDateLogic);					
		rs = callstmt.executeQuery();
		
		while(rs.next())
		{					
			objDto=new M6OrderStatusDto();
			objDto.setComponentStartDateLogicId((rs.getString("ID")));			
			objDto.setComponentStartDateLogicDesc((rs.getString("DESCRIPTION")));	
			startDateLogicList.add(objDto);
		}
		
	}
	catch(Exception ex )
	{
		connection.rollback();
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.freeConnection(connection);
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return startDateLogicList;
}
public ArrayList<M6OrderStatusDto> fillComponentEndDateLogic() throws Exception 
{
	//	Nagarjuna
	String methodName="fillComponentEndDateLogic", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement callstmt =null;
	ResultSet rs = null;	
	M6OrderStatusDto objDto=null;
	ArrayList<M6OrderStatusDto> endDateLogicList = new ArrayList<M6OrderStatusDto>();
	try
	{
		connection=DbConnection.getConnectionObject();
		
		callstmt= connection.prepareCall(sqlGetCompEndDateLogic);					
		rs = callstmt.executeQuery();
		
		while(rs.next())
		{					
			objDto=new M6OrderStatusDto();
			objDto.setComponentEndDateLogicId((rs.getString("ID")));			
			objDto.setComponentEndDateLogicDesc((rs.getString("DESCRIPTION")));	
			endDateLogicList.add(objDto);
		}
		
	}
	catch(Exception ex )
	{
		connection.rollback();
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.freeConnection(connection);
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return endDateLogicList;
}
public ArrayList<AcctDTO> getUpdatedAccountList(Long orderNo) throws Exception 
{
	//	Nagarjuna
	String methodName="getUpdatedAccountList", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement cstmt =null;
	ResultSet rs = null;	
	AcctDTO objDto=null;
	ArrayList<AcctDTO> accountUpdateStatusList = new ArrayList<AcctDTO>();
	try
	{
		connection=DbConnection.getConnectionObject();
		cstmt= connection.prepareCall(sqlGetAccountUpdateStatusList);
		cstmt.setLong(1, orderNo);
		rs = cstmt.executeQuery();
		
		while(rs.next())
		{					
			objDto=new AcctDTO();
			objDto.setAcctExternalId(rs.getString("ACCEXTERNALID"));			
			objDto.setAcctInternalId(rs.getString("INTERNAL_ID"));
			objDto.setAccountUpdateStatus(rs.getString("STATUS"));
			accountUpdateStatusList.add(objDto);
		}
	}
	catch(Exception ex )
	{
		connection.rollback();
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(cstmt);
			DbConnection.freeConnection(connection);
		} 
		catch (SQLException e) 
		{
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return accountUpdateStatusList;
}
public String disconnectAllComponent(long serviceId,long spid)throws Exception
{
	//	Nagarjuna
	String methodName="disconnectAllComponent", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement callstmt =null;
	int status=0;
	String result = "";
	try
	{
		connection=DbConnection.getConnectionObject();
		connection.setAutoCommit(false);
		callstmt= connection.prepareCall(sqlspDisconnectAllComponent);	
		callstmt.setLong(1, serviceId);
		callstmt.setLong(2,spid);
		callstmt.setNull(3, java.sql.Types.INTEGER);
		callstmt.execute();
		if(callstmt.getInt(3)==0){
		connection.commit();
		result = "All Components Disconnected.";
		} else {
			result = "Error In Disconnecting Components";
		}
		
	}
	catch(Exception ex )
	{
		connection.rollback();
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.freeConnection(connection);
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return result;
}

//lawkush Start

public ArrayList<NewOrderDto> getArborRedirectedLSIs(PagingDto objDto,Connection connection) 
{
	//	Nagarjuna
	String methodName="getArborRedirectedLSIs", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	//Connection connection =null;
	CallableStatement cstmt =null;
	ResultSet rs = null;
	ArrayList<NewOrderDto> listArborRedirectedLSIs = new ArrayList<NewOrderDto>();
	NewOrderDto objNewOrderDto = null;
	int recordCount;
	try
	{
		//connection=DbConnection.getConnectionObject();
		cstmt= connection.prepareCall(sqlspGetArborRedirectedLSIsWithSorting);
		cstmt.setInt(1,Integer.parseInt(objDto.getLogicalSINo()));//CurrentLSI from where we are doing the lookup
		rs = cstmt.executeQuery();
		while(rs.next())
		{
			objNewOrderDto =  new NewOrderDto();
			objNewOrderDto.setLogicalSINo(rs.getString("LOGICAL_SI_NO"));
			
			listArborRedirectedLSIs.add(objNewOrderDto);			
		}
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();	
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(cstmt);
		//	DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return listArborRedirectedLSIs;
}

//lawkush End
public ArrayList<NewOrderDto> fetchLSINoForMBIC(int attMasterId,int orderNo) 
{
	//	Nagarjuna
	String methodName="fetchLSINoForMBIC", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement getlogicalSIDetails =null;
	ResultSet rslogicalSIDetails = null;
	ArrayList<NewOrderDto> listlogicalSIDetails = new ArrayList<NewOrderDto>();
	NewOrderDto objNewDto = null;
	int recordCount;
	try
	{
		connection=DbConnection.getConnectionObject();
		getlogicalSIDetails= connection.prepareCall(sqlGetLogicalSiNumberForMBIC);
		getlogicalSIDetails.setInt(1, orderNo);
		getlogicalSIDetails.setInt(2, attMasterId);
		rslogicalSIDetails = getlogicalSIDetails.executeQuery();
		while(rslogicalSIDetails.next())
		{
			objNewDto =  new NewOrderDto();
			objNewDto.setCustSINo(rslogicalSIDetails.getString("LOGICAL_SI_NO"));//Clear Channel LSI			
			objNewDto.setServiceId(rslogicalSIDetails.getInt("SERVICEID"));			
			objNewDto.setShortCode(rslogicalSIDetails.getString("ORDERNO"));
			objNewDto.setShortCode(rslogicalSIDetails.getString("SERVICETYPEID"));
			objNewDto.setMbicServiceId(rslogicalSIDetails.getString("MBIC_SERVICE_ID"));
			objNewDto.setMbicServiceProductId(rslogicalSIDetails.getString("MBIC_SERVICEPRODUCTID"));
			objNewDto.setIsCCMapWithMBIC(rslogicalSIDetails.getString("IS_CC_MAP_WITH_MBIC"));
			objNewDto.setIsServiceActive(rslogicalSIDetails.getInt("IS_SERVICE_INACTIVE"));			
			listlogicalSIDetails.add(objNewDto);
		}
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return listlogicalSIDetails;
}
public String dettachMBICLSI(NewOrderDto objDto) 
{
	//	Nagarjuna
	String methodName="dettachMBICLSI", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	PreparedStatement getlogicalSIDetails =null;
	ResultSet rslogicalSIDetails = null;
	boolean isDettach=false;
	NewOrderDto objNewDto = null;
	int recordCount;
	try
	{
		connection=DbConnection.getConnectionObject();
		getlogicalSIDetails= connection.prepareStatement(sqlAttachDettachMBIC_LSI);
		getlogicalSIDetails.setInt(1, objDto.getOrderNumber());
		getlogicalSIDetails.setInt(2, Integer.parseInt(objDto.getMbicServiceId().trim()));
		getlogicalSIDetails.setInt(3,Integer.parseInt(objDto.getMbicServiceProductId().trim()));
		getlogicalSIDetails.setInt(4,Integer.parseInt(objDto.getCustSINo().trim()));// MBIC LSI NO
		getlogicalSIDetails.setInt(5,Integer.parseInt(objDto.getIsAttach().trim()));
		isDettach = getlogicalSIDetails.execute();
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return "";
}
public String validateMBIC_To_CC(int orderno)
{
	//	Nagarjuna
	String methodName="validateMBIC_To_CC", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement pStmt =null;		
	boolean isDettach=false;
	NewOrderDto objNewDto = null;
	int recordCount;
	String message="";
	try
	{
		connection=DbConnection.getConnectionObject();
		pStmt= connection.prepareCall(sqlvalidateMBIC_To_CC);
		pStmt.setInt(1, orderno);
		pStmt.setInt(2,0);
		pStmt.setString(3,"");
		pStmt.setString(4,"");
		pStmt.setInt(5,0);
		pStmt.setString(6,"");
		pStmt.setString(7,"");
		pStmt.execute();
		int errStatus=pStmt.getInt(2);
		message=pStmt.getString(7);
		if(message==null)
			message="";
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return message;
}
public String selectCC_MBIC_Maping(NewOrderDto objDto) 
{
	//	Nagarjuna
	String methodName="selectCC_MBIC_Maping", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement callStmt =null;
	String ccMbicServiceId="";
	try
	{
		connection=DbConnection.getConnectionObject();
		callStmt= connection.prepareCall(sqlGet_CC_MBIC_SERVICEID);
		callStmt.setInt(1,objDto.getOrderNumber());
		callStmt.setInt(2,objDto.getServiceId());
		callStmt.setInt(3,objDto.getServiceTypeId());
		callStmt.setInt(4,0);
		callStmt.execute();
		ccMbicServiceId=String.valueOf(callStmt.getInt(4));
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return ccMbicServiceId;
}

private String sqlvalidateVCS_BridgeServiceBundled="{call IOE.SP_VALIDATE_VCS_BRIDGE_BUNLED_WITH_L3MPLS(?,?,?,?,?,?,?,?)}";
public String validateVCS_BridgeServiceBundled(int orderno)
{
	//	Nagarjuna
	String methodName="validateVCS_BridgeServiceBundled", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement pStmt =null;	
	boolean isDettach=false;
	NewOrderDto objNewDto = null;
	int recordCount;
	String message="";
	try
	{
		connection=DbConnection.getConnectionObject();
		pStmt= connection.prepareCall(sqlvalidateVCS_BridgeServiceBundled);
		pStmt.setInt(1, orderno);
		pStmt.setInt(2,0);
		pStmt.setString(3,"");
		pStmt.setString(4,"");
		pStmt.setInt(5,0);
		pStmt.setString(6,"");
		pStmt.setString(7,"");
		pStmt.setString(8, "");
		pStmt.execute();
		int errStatus=pStmt.getInt(2);
		message=pStmt.getString(7);
		if(message==null)
			message="";
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();	
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return message;
}
private String sqlvalidateVCS_L3MPLSForDisconnectOrder="{call IOE.SP_VALIDATE_VCS_L3MPLS_FOR_DIS_ORDER(?)}";
private String sqlGetVCS_MAP_L3MPLSForDisconnectOrder="{call IOE.SP_GET_VCS_MAP_L3MPLS_FOR_DIS_ORDER(?,?,?,?,?)}";
//private String sqlGetVCS_JUST_UNMAP_L3MPLSForDisconnectOrder="{call IOE.SP_GET_VCS_JUST_UNMAP_L3MPLS_FOR_DIS_ORDER(?,?,?,?,?)}";
public String validateVCS_L3MPLSForDisconnectOrder(int orderno)
{
	//	Nagarjuna
	String methodName="validateVCS_L3MPLSForDisconnectOrder", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement pStmt =null;	
	ServiceLineDTO Objdto=null;//added by Anoop for OVCC
	boolean isDettach=false;
	int recordCount=0, lineCount=0;
	ResultSet rs=null;
	String mappedMsg1="",unMappedMsg1="",mappedMsg2="",unMappedMsg2="",mappedMsg3="",unMappedMsg3="",messageVCSBridge="";
	String logicalLSIStr="";
	String serviceProductId="";
	String attValue="";
	String serviceTypeName="";//VCS-ISP MApping as on date 12-feb-2013 : Ashutosh
	NewOrderDto objNewDto = null;
	ArrayList<ServiceLinkingDTO> arrList=null;
	HashMap<Integer,Integer> hmSecServices=new HashMap<Integer,Integer>();
	HashMap<Integer,ArrayList> hmPriServiceLineData=new HashMap<Integer,ArrayList>();
	HashMap<Integer,ServiceLineDTO > disServiceStageHasMap=new HashMap<Integer,ServiceLineDTO>();
	ServiceLinkingDTO nonValidDataDto=null;
	try
	{
		connection=DbConnection.getConnectionObject();
		//Brings details of Secondary services, pertains to a particular order.
		pStmt= connection.prepareCall(sqlvalidateVCS_L3MPLSForDisconnectOrder);
		pStmt.setInt(1, orderno);
		rs=pStmt.executeQuery();
		while(rs.next())
		{   Objdto=new ServiceLineDTO();//added by Anoop for OVCC
		    Objdto.setServiceName(rs.getString("SERVICESTAGE"));//added by Anoop for OVCC
		    disServiceStageHasMap.put(rs.getInt("SERVICEID"), Objdto);
			hmSecServices.put(rs.getInt("SERVICEID"), rs.getInt("LOGICAL_SI_NO")) ;
			if(logicalLSIStr.equals(""))			
				logicalLSIStr=rs.getString("LOGICAL_SI_NO");			
			else
				logicalLSIStr=logicalLSIStr+","+rs.getString("LOGICAL_SI_NO");
		}
		try{
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(pStmt);
			DbConnection.freeConnection(connection);
		}catch(Exception e){
			Utility.LOG(true, true, e, "While closing db attributes in validateVCS_L3MPLSForDisconnectOrder of NewOrderDaoExt");
		}
		if(!logicalLSIStr.equals(""))
		{
			connection=DbConnection.getConnectionObject();
			pStmt= connection.prepareCall(sqlGetVCS_MAP_L3MPLSForDisconnectOrder);
			pStmt.setString(1, logicalLSIStr);
			pStmt.setInt(2, orderno);
			pStmt.setInt(3,0 );
			pStmt.setInt(4, 0);
			pStmt.setString(5, "");
			rs=pStmt.executeQuery();
			while(rs.next())
			{
				nonValidDataDto=new ServiceLinkingDTO();
				//objNewDto=new NewOrderDto();
				arrList=new ArrayList<ServiceLinkingDTO>();
				//objNewDto.setServiceId(rs.getInt("L3MPLSE_SERVICEID"));
				//objNewDto.setLogicalSINumber(rs.getInt("VCS_BUNDLED_SERVICE"));
				//objNewDto.setServiceDetailID(rs.getInt("SERVICEDETAILID"));//added for ISP and L3MPLS specific validaion Display
				//objNewDto.setServiceTypeName(rs.getString("SERVICESTAGE"));
				nonValidDataDto.setValidationScenario(rs.getString("SCENARIO"));
				nonValidDataDto.setSecServiceProductId(rs.getLong("SEC_LINE_NO"));
				nonValidDataDto.setSecLogicalSiNo(rs.getLong("LOGICAL_SI_NO"));
				nonValidDataDto.setPriServiceProductId(rs.getLong("PRI_LINE_NO"));
				nonValidDataDto.setPriOrderNo(rs.getLong("ORDERNO"));
				nonValidDataDto.setPriServiceTypeName(rs.getString("PRI_SERV_TYPE_NAME"));
				nonValidDataDto.setSecServiceTypeName(rs.getString("SEC_SERV_TYPE_NAME"));
				nonValidDataDto.setPriServiceId(rs.getLong("SERVICEID"));
				arrList.add(nonValidDataDto);
				//l3MPLShashMap.put(rs.getInt("SERVICEPRODUCTID"), arrList) ;
				hmPriServiceLineData.put(rs.getInt("PRI_LINE_NO"), arrList) ;
				recordCount++;
			}
			try{
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(pStmt);
				DbConnection.freeConnection(connection);
			}catch(Exception e){
				Utility.LOG(true, true, e, "While closing db attributes in validateVCS_L3MPLSForDisconnectOrder of NewOrderDaoExt");
			}
			if(recordCount>0)
			{
				Set disVcsSet=hmSecServices.keySet();
				Iterator itr =disVcsSet.iterator();				
				ArrayList<ServiceLinkingDTO> list=new ArrayList<ServiceLinkingDTO>();
				ServiceLinkingDTO newObj=null;
				int logicalSiNo=0;
				int vcsServiceId=0;
				int l3MPLSSpId=0;
				while(itr.hasNext())
				{
					vcsServiceId=(Integer)itr.next();
					logicalSiNo=(Integer)hmSecServices.get(vcsServiceId);
					//System.out.println("key :"+vcsServiceId);
					Set l3MplsSet=hmPriServiceLineData.keySet();
					Iterator itr2 =l3MplsSet.iterator();
					while(itr2.hasNext())
					{
						l3MPLSSpId=(Integer)itr2.next();
						//System.out.println("key :"+l3MPLSSpId);
						list =hmPriServiceLineData.get(l3MPLSSpId);
						for(int i=0; i<list.size(); i++)
						{
							new ServiceLinkingDTO();
							newObj =list.get(i);
							//[219] START
							/*//change for ISP name Display
							if(newObj.getServiceDetailID()==100000)
								serviceTypeName="ISP";
							else
								serviceTypeName="L3MPLS";*/
							//serviceTypeName=newObj.getServiceTypeName();
							if(newObj.getValidationScenario().equalsIgnoreCase("MAPPED") && newObj.getSecLogicalSiNo()==logicalSiNo)
							{
								mappedMsg1=mappedMsg1+"\t**"+   disServiceStageHasMap.get(vcsServiceId).getServiceName().substring(0,4)+ "Line Item :"+l3MPLSSpId+" Under "+newObj.getPriServiceTypeName()+" Service :"+newObj.getPriServiceId()+"\n ";
								lineCount++;
							}
							if(newObj.getValidationScenario().equalsIgnoreCase("JUST_UNMAP") && newObj.getSecLogicalSiNo()==logicalSiNo)
							{
								unMappedMsg1=unMappedMsg1+"\t**"+   disServiceStageHasMap.get(vcsServiceId).getServiceName().substring(0,4)+ "Line Item :"+l3MPLSSpId+" Under "+newObj.getPriServiceTypeName()+" Service :"+newObj.getPriServiceId()+"\n ";
								lineCount++;
							}
						}
					}
					if(lineCount>0)
					{
						mappedMsg2=(mappedMsg1.equalsIgnoreCase(""))?mappedMsg2:(mappedMsg2+"*" +disServiceStageHasMap.get(vcsServiceId).getServiceName()+  " Service No :"+vcsServiceId+" having LSI : "+logicalSiNo+" Are Mapped with  \n"+mappedMsg1);
						mappedMsg1="";
						unMappedMsg2=(unMappedMsg1.equalsIgnoreCase(""))?unMappedMsg2:(unMappedMsg2+"*" +disServiceStageHasMap.get(vcsServiceId).getServiceName()+  " Service No :"+vcsServiceId+" having LSI : "+logicalSiNo+" Are just Unmapped with  \n"+unMappedMsg1);
						unMappedMsg1="";
						lineCount=0;
					}
				}
				mappedMsg3=(mappedMsg2.equalsIgnoreCase(""))?"":("Completely Disconnect or Unmap All Line Item Where :\n"+mappedMsg2);
				unMappedMsg3=(unMappedMsg2.equalsIgnoreCase(""))?"":("Please First Complete Services in Change Order :\n"+unMappedMsg2);
				messageVCSBridge=messageVCSBridge + mappedMsg3+"\n"+unMappedMsg3;				
			}
			/*else
			{//For not completed L3 MPLS
				connection=DbConnection.getConnectionObject();
				pStmt= connection.prepareCall(sqlGetVCS_JUST_UNMAP_L3MPLSForDisconnectOrder);
				pStmt.setString(1, logicalLSIStr);
				pStmt.setInt(2, orderno);
				pStmt.setInt(3,0 );
				pStmt.setInt(4, 0);
				pStmt.setString(5, "");
				rs=pStmt.executeQuery();
				while(rs.next())
				{
					objNewDto=new NewOrderDto();
					arrList=new ArrayList<NewOrderDto>();
					objNewDto.setServiceId(rs.getInt("L3MPLSE_SERVICEID"));
					objNewDto.setLogicalSINumber(rs.getInt("VCS_BUNDLED_SERVICE"));
					if(!rs.getString("ATTVALUE").equals(""))
					{
						attValue=rs.getString("ATTVALUE");
					}						
					objNewDto.setServiceTypeId(rs.getInt("SERVICETYPEID"));//added for ISP and L3MPLS specific validaion Display
					objNewDto.setServiceTypeName(rs.getString("PRI_SERV_TYPE_NAME"));
					objNewDto.setServiceName(rs.getString("SEC_SERV_TYPE_NAME"));
					arrList.add(objNewDto);
					hmPriServiceLineData.put(rs.getInt("SERVICEPRODUCTID"), arrList) ;
					recordCount++;
				}
				try{
					DbConnection.closeResultset(rs);
					DbConnection.closeCallableStatement(pStmt);
					DbConnection.freeConnection(connection);
				}catch(Exception e){
					Utility.LOG(true, true, e, "While closing db attributes in validateVCS_L3MPLSForDisconnectOrder of NewOrderDaoExt");
				}
				if(recordCount>0)
				{
					Set disVcsSet=hmSecServices.keySet();
					Iterator itr =disVcsSet.iterator();				
					ArrayList<ServiceLinkingDTO> list=new ArrayList<ServiceLinkingDTO>();
					ServiceLinkingDTO newObj=null;
					int logicalSiNo=0;
					int vcsServiceId=0;
					int l3MPLSSpId=0;
					while(itr.hasNext())
					{
						vcsServiceId=(Integer)itr.next();
						logicalSiNo=(Integer)hmSecServices.get(vcsServiceId);
						Set l3MplsSet=hmPriServiceLineData.keySet();
						Iterator itr2 =l3MplsSet.iterator();
						while(itr2.hasNext())
						{
							l3MPLSSpId=(Integer)itr2.next();
							//System.out.println("key :"+l3MPLSSpId);
							list =hmPriServiceLineData.get(l3MPLSSpId);
							for(int i=0; i<list.size(); i++)
							{
								new ServiceLinkingDTO();
								newObj =list.get(i);
								//change for ISP name Display
								if(newObj.getServiceTypeId()==221)
									serviceTypeName="ISP";
								else
									serviceTypeName="L3MPLS";
								serviceTypeName=newObj.getServiceTypeName();
								String secServTypeName=newObj.getServiceName();
								if(newObj.getLogicalSINumber()==logicalSiNo)
								{
									message=message+"\t\t**  "+secServTypeName+" Line Item :"+l3MPLSSpId+" Under "+serviceTypeName+" Service :"+newObj.getServiceId()+"\n ";
									lineCount++;
								}
							}
						}
						if(lineCount>0)
						{
							messageVCSBridge=messageVCSBridge+"\t* "+disServiceStageHasMap.get(vcsServiceId).getServiceName()+" No :"+vcsServiceId+" having LSI : "+logicalSiNo+" Are just Unmapped with  \n"+message;
							message="";
							lineCount=0;
						}
					}
					messageVCSBridge="Please First Complete Services in Change Order :\n"+messageVCSBridge;
				}//[219] END
			}*/
		}
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(pStmt);
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return messageVCSBridge;
}
//Function that called for Disconnection validation
private String getVCS_L3MPLS_Map_Unmapp(String mapUnmap)
{
	String message="",messageVCSBridge="";
	HashMap<Integer,Integer> disVCShashMap=new HashMap<Integer,Integer>();
	HashMap<Integer,ArrayList> l3MPLShashMap=new HashMap<Integer,ArrayList>();
	Set disVcsSet=disVCShashMap.keySet();
	Iterator itr =disVcsSet.iterator();				
	ArrayList<NewOrderDto> list=new ArrayList<NewOrderDto>();
	NewOrderDto newObj=null;
	int logicalSiNo=0;
	int vcsServiceId=0;
	int l3MPLSSpId=0;
	while(itr.hasNext())
	{
		vcsServiceId=(Integer)itr.next();
		logicalSiNo=(Integer)disVCShashMap.get(vcsServiceId);
		System.out.println("key :"+vcsServiceId);
		Set l3MplsSet=l3MPLShashMap.keySet();
		Iterator itr2 =l3MplsSet.iterator();
		while(itr2.hasNext())
		{
			l3MPLSSpId=(Integer)itr2.next();
			System.out.println("key :"+l3MPLSSpId);
			list =l3MPLShashMap.get(l3MPLSSpId);
			for(int i=0; i<list.size(); i++)
			{
				new NewOrderDto();
				newObj =list.get(i);
				if(newObj.getLogicalSINumber()==logicalSiNo)
				{
					message=message+"\t\t**  VCS Line Item :"+l3MPLSSpId+" Under L3MPLS Service :"+newObj.getServiceId()+"\n ";
					System.out.println("VCS Bridge Service No: "+vcsServiceId+" Are Mapped with VCS Line Item :"+l3MPLSSpId+" Under L3MPLS Service :"+newObj.getServiceId());
				}
			}
		}
		if(mapUnmap.equals("map"))
			messageVCSBridge=messageVCSBridge+"\t* VCS Bridge Service No :"+vcsServiceId+" having LSI : "+logicalSiNo+" Are Mapped with  \n"+message;
		else
			messageVCSBridge=messageVCSBridge+"\t* VCS Bridge Service No :"+vcsServiceId+" having LSI : "+logicalSiNo+" Are just Unmapped with  \n"+message;
		message="";
	}
	return messageVCSBridge;
}
private String sqlvalidateMBIC_ON_CC_Discoonnect="{call IOE.SP_VALIDATE_MBIC_ON_CC_ALL_PRODUCT_CANCELLED(?,?,?)}";
public String validateMBIC_ON_CC_Discoonnect(int orderno)
{
	//	Nagarjuna
	String methodName="validateMBIC_ON_CC_Discoonnect", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement pStmt =null;
	ResultSet rs=null;
	boolean isDettach=false;
	NewOrderDto objNewDto = null;
	int recordCount;
	String message=""; 
	String MBIC_serviceId="";
	String CC_serviceId="";
	String serviceProductId="";
	try
	{
		connection=DbConnection.getConnectionObject();
		pStmt= connection.prepareCall(sqlvalidateMBIC_ON_CC_Discoonnect);
		pStmt.setInt(1, orderno);		
		pStmt.setString(2,"");	
		pStmt.setString(3,"");
		pStmt.execute();
		MBIC_serviceId=pStmt.getString(2);
		CC_serviceId=pStmt.getString(3);
		if(!MBIC_serviceId.equals("") && !CC_serviceId.equals(""))
		{
			message="Disconnect Line Item of MBIC Service :"+ MBIC_serviceId +" because It's mapped with Clear channel service :"+CC_serviceId + " having all Line item are Disconected ";
		}
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return message;
}
private String sqlGetL3MplsMappingCount="{call IOE.SP_GET_L3MPLS_BUNDLED_WITH_VCSBRIDGE(?,?)}";
public String getL3MplsMappingCount(int serviceId)
{
	//	Nagarjuna
	String methodName="getL3MplsMappingCount", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement pStmt =null;
	ResultSet rs=null;
	boolean isDettach=false;
	NewOrderDto objNewDto = null;
	int recordCount=0;
	String message="";
	String serviceProductId="";
	try
	{
		connection=DbConnection.getConnectionObject();
		pStmt= connection.prepareCall(sqlGetL3MplsMappingCount);
		pStmt.setInt(1, serviceId);		
		pStmt.setString(2,"");		
		rs=pStmt.executeQuery();
		while(rs.next())
		{
			if(message.equals(""))
			{
				serviceProductId=rs.getString("VCS_SERVICEPRODUCTID");
				message="\n VCS Line Item No : "+serviceProductId +" under L3 MPLS Service No : "+rs.getString("L3MPLS_SERVICEID");
			}
			else
			{
				serviceProductId=rs.getString("VCS_SERVICEPRODUCTID");
				message=message+"\n VCS Line Item  No : "+ serviceProductId+" under L3 MPLS Service No : "+rs.getString("L3MPLS_SERVICEID");
			}
			//recordCount++;
		}
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return message;
}
//isComponentAdded
private String sqlGetComponentAdded="{call IOE.SP_VALIDATE_COMPONENT_ONWARD_COPC(?)}";
public String isComponentAdded(int orderNo)
{
	//	Nagarjuna
	String methodName="isComponentAdded", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement pStmt =null;
	ResultSet rs=null;
	boolean isDettach=false;
	NewOrderDto objNewDto = null;
	int recordCount;
	String message="";
	String serviceProductId="";
	try
	{
		connection=DbConnection.getConnectionObject();
		pStmt= connection.prepareCall(sqlGetComponentAdded);
		pStmt.setInt(1, orderNo);
		rs=pStmt.executeQuery();
		while(rs.next())
		{
			if(message.equals(""))
			{
				serviceProductId=rs.getString("SERVICEPRODUCTID");
				message=" * "+serviceProductId +" under Service No : "+rs.getString("SERVICEID");
			}
			else
			{
				serviceProductId=rs.getString("SERVICEPRODUCTID");
				message=message+"\n * "+ serviceProductId+" under Service No : "+rs.getString("SERVICEID");
			}
		}		
		if(!message.equals(""))	
			message="Please Add Atleast One Component in Each \n Line Item No :\n"+message;
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return message;
}
//isVCS_LSIBundled
private String sqlGetIsVCS_LSIBundled="{call IOE.SP_GET_VCS_BUNDLED_FOR_DUMMYLINEITEM(?,?,?)}";
public String isVCS_LSIBundled(int serviceId,String calledFrom)
{
	//	Nagarjuna
	String methodName="isVCS_LSIBundled", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement pStmt =null;	
	boolean isDettach=false;
	NewOrderDto objNewDto = null;
	int recordCount;
	String message="";
	String serviceProductId="";
	try
	{
		connection=DbConnection.getConnectionObject();
		pStmt= connection.prepareCall(sqlGetIsVCS_LSIBundled);
		pStmt.setInt(1, serviceId);
		pStmt.setString(2, calledFrom);
		pStmt.setString(3, "");
		pStmt.execute();			
		message=pStmt.getString(3);
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return message;
}
//justUnmappedVCSDeletionCheck
private String sqlGetJustUnmappedVCSDeletionCheck="{call IOE.SP_GET_JUSTUNMAPPED_VCS_DELETION_CHECK(?,?,?)}";
public String justUnmappedVCSDeletionCheck(int l3mplsServiceId,int orderNo)
{
	//	Nagarjuna
	String methodName="justUnmappedVCSDeletionCheck", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement pStmt =null;	
	boolean isDettach=false;
	NewOrderDto objNewDto = null;
	int recordCount;
	String message="";
	String serviceProductId="";
	try
	{
		connection=DbConnection.getConnectionObject();
		pStmt= connection.prepareCall(sqlGetJustUnmappedVCSDeletionCheck);
		pStmt.setInt(1, l3mplsServiceId);
		pStmt.setInt(2, orderNo);
		pStmt.setString(3, "");
		pStmt.execute();			
		
		message=pStmt.getString(3);
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return message;
}

//013 START

public int revertDisconnectCharge(String chargeID, String changeOrderNo,String disconnectionType)throws Exception
{
	//	Nagarjuna
	String methodName="revertDisconnectCharge", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement callstmt =null;
	int status=0;
	try
	{
		connection=DbConnection.getConnectionObject();
		connection.setAutoCommit(false);
		callstmt= connection.prepareCall(sqlsprevertDisconnectCharge);	
		callstmt.setLong(1, Long.valueOf(chargeID));
		callstmt.setLong(2, Long.valueOf(changeOrderNo));
		callstmt.setString(4,disconnectionType);
		callstmt.setNull(3, java.sql.Types.INTEGER);
		callstmt.execute();
		status=callstmt.getInt(3);
		if(status==0){
		connection.commit();
		}
		
	}
	catch(Exception ex )
	{
		connection.rollback();
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeCallableStatement(callstmt);
			DbConnection.freeConnection(connection);
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return status;
}
//014 START
public ArrayList<NewOrderDto> getAccountDetailsWithSortingforReassigmPm(PagingDto objDto) 
{
	//	Nagarjuna
	String methodName="getAccountDetailsWithSortingforReassigmPm", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement getAllAccountsReport =null;
	ResultSet rsAccountDetails = null;
	ArrayList<NewOrderDto> listAccountDetails = new ArrayList<NewOrderDto>();
	NewOrderDto objNewOrderDto = null;
	int recordCount;
	try
	{
		connection=DbConnection.getConnectionObject();
		getAllAccountsReport= connection.prepareCall(sqlspGetAccountWithSortingforreassignpm);
		String searchAcc=objDto.getAccountName();
		String accountId=objDto.getAccountIDString();
		int osp=0;
		
		if(accountId==null || accountId.equalsIgnoreCase(""))
		{
			accountId="0";
		}
		if(searchAcc=="")
		{
			searchAcc=null;
		}
		objDto.getAccountID();
		if(("").equalsIgnoreCase(searchAcc))
		{
			searchAcc=null;
		}
		if(("").equalsIgnoreCase(objDto.getShortCode()))
		{
			objDto.setShortCode(null);
		}
		if(objDto.getOsp()==null || objDto.getOsp().equalsIgnoreCase(""))
		{
			osp=0;
		}
		else
		{
			osp=Integer.parseInt(objDto.getOsp());
		}
		getAllAccountsReport.setString(1,searchAcc);
		getAllAccountsReport.setString(2,accountId );
		getAllAccountsReport.setString(3,objDto.getShortCode());
		getAllAccountsReport.setString(4,objDto.getSortBycolumn());
		getAllAccountsReport.setString(5,objDto.getSortByOrder());
		getAllAccountsReport.setInt(6,objDto.getStartIndex());
		getAllAccountsReport.setInt(7,objDto.getEndIndex());
		getAllAccountsReport.setInt(8,osp);
		getAllAccountsReport.setInt(9,objDto.getEmployeeid());
		
		rsAccountDetails = getAllAccountsReport.executeQuery();
		while(rsAccountDetails.next())
		{
			objNewOrderDto =  new NewOrderDto();
			objNewOrderDto.setAccountID(rsAccountDetails.getInt("accountID"));
			objNewOrderDto.setAccountName(rsAccountDetails.getString("accountName"));
			objNewOrderDto.setAccphoneNo(rsAccountDetails.getLong("PhoneNo"));
			objNewOrderDto.setLob(rsAccountDetails.getString("LOB"));
			objNewOrderDto.setCrmAccountId(rsAccountDetails.getInt("CRMACCOUNTNO"));//Added By Ashutosh
			objNewOrderDto.setAccountManager(rsAccountDetails.getString("AccountManager"));
			objNewOrderDto.setProjectManager(rsAccountDetails.getString("ProjManager"));
			objNewOrderDto.setSpFirstname(rsAccountDetails.getString("SPFirstname"));
			objNewOrderDto.setSpLastName(rsAccountDetails.getString("SLastName"));
			objNewOrderDto.setAcmgrPhno(rsAccountDetails.getString("acmgrPhone"));//changed by kalpana from long to string for bug id HYPR11042013001
			objNewOrderDto.setAcmgrEmail(rsAccountDetails.getString("acmgrEmail"));
			objNewOrderDto.setSpLPhno(rsAccountDetails.getString("SLPhno"));//changed by kalpana from long to string for bug id HYPR11042013001
			objNewOrderDto.setSpLEmail(rsAccountDetails.getString("emailID"));
			objNewOrderDto.setRegion(rsAccountDetails.getString("Region"));
			objNewOrderDto.setZone(rsAccountDetails.getString("ACCZONE"));
			//LAWKUSH START
			objNewOrderDto.setOsp(rsAccountDetails.getString("OSP"));
			//LAWKUSH END
			//objNewOrderDto.setZoneId(rsAccountDetails.getInt("ZONEID"));
			objNewOrderDto.setM6ShortCode(rsAccountDetails.getString("M6SHORTCODE"));
			objNewOrderDto.setRegionIdNew(rsAccountDetails.getString("REGIONID"));
			objNewOrderDto.setCollectionMgr(rsAccountDetails.getString("CollectionMgr"));
			objNewOrderDto.setCircle(rsAccountDetails.getString("CIRCLE"));//added on 9-jan-2013, Circle work
			objNewOrderDto.setCategory(rsAccountDetails.getString("CATEGORY"));//added on 30-APR-2013	
			recordCount=rsAccountDetails.getInt("FULL_REC_COUNT");
			objNewOrderDto.getPagingSorting().setRecordCount(recordCount);	
			objNewOrderDto.setMaxPageNo(objNewOrderDto.getPagingSorting().getMaxPageNumber());
			listAccountDetails.add(objNewOrderDto);			
		}
		return listAccountDetails;
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rsAccountDetails);
			DbConnection.closeCallableStatement(getAllAccountsReport);
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return listAccountDetails;
}

//013 END

//[016] Start
public static String sqlValidateHardwareLineItem = "{CALL IOE.VALIDATEHARDWARELINEITEM(?,?)}";
public String ValidateHardwareLineItem(LineItemDTO objDto,Connection optionalConnection) throws Exception 
{
	//	Nagarjuna
	String methodName="ValidateHardwareLineItem", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement callstmt =null;
	String retString = "";
	int count = -1;
	try
	{
		//below code add by Anil for CLEP
		if(optionalConnection==null){
			connection=DbConnection.getConnectionObject();
		}else{
			connection=optionalConnection;
		}
		//end CLEP
		
		connection.setAutoCommit(false);
		callstmt= connection.prepareCall(sqlValidateHardwareLineItem);	
		callstmt.setInt(1, Integer.parseInt(objDto.getLineNo()));
		callstmt.setInt(2, 0);
		callstmt.execute();
		count = callstmt.getInt(2);
		if(count > 0)
		{
			//retString = "Validate Successfully";
		}
		else 
		{
			retString = "Hardware Line Item either has been completed in M6 or doesnt qualify for Cancelation in M6";
		}
	}
	catch(Exception ex )
	{
		connection.rollback();
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeCallableStatement(callstmt);
			if(optionalConnection==null){
				DbConnection.freeConnection(connection);
			}
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return retString;
}
//[016] End

//Method used for Fetching Licence Company on the basis of Entity from the Database through ajax
public static String sqlGetCancelledHardwareLineDetails= "{call IOE.SPGET_CANCELLED_HW_LINEDETAILS(?)}";
public ArrayList<ProductCatelogDTO> getCancelledHardwareLineDetails(long orderNo) 
{
	//	Nagarjuna
	String methodName="getCancelledHardwareLineDetails", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	PreparedStatement getLicCompany =null;
	ResultSet rs = null;
	ProductCatelogDTO objProductCatelogDTO = null;
	ArrayList<ProductCatelogDTO> CancelledhardwareLineItems= new ArrayList<ProductCatelogDTO>();
	try
	{
		connection=DbConnection.getConnectionObject();
		getLicCompany= connection.prepareCall(sqlGetCancelledHardwareLineDetails);
		getLicCompany.setLong(1,orderNo);
		rs = getLicCompany.executeQuery();
		while(rs.next())
		{
			objProductCatelogDTO =  new ProductCatelogDTO();
			objProductCatelogDTO.setServiceProductID(rs.getInt("SERVICEPRODUCTID"));
			objProductCatelogDTO.setServiceDetDescription(rs.getString("SERVICEDETDESCRIPTION"));
			objProductCatelogDTO.setServiceNo(rs.getInt("SERVICEID"));
			objProductCatelogDTO.setRequestedBy(rs.getString("REQUESTEDBY"));
			objProductCatelogDTO.setUserId(rs.getString("USER_ID"));
			objProductCatelogDTO.setCreatedBy(rs.getString("CREATEDBY"));
			objProductCatelogDTO.setLogicalCircuitId(rs.getString("LOGICALCIRCUITID"));
			objProductCatelogDTO.setInfraOderNo(rs.getString("INFRAORDERNO"));
			objProductCatelogDTO.setMetasolvCircuitId(rs.getString("METASOLVCIRCUITID"));
			objProductCatelogDTO.setPodetailID(rs.getInt("PODETAILID"));
			objProductCatelogDTO.setCreditPeriodName(rs.getString("CREDIT_PERIODNAME"));
			objProductCatelogDTO.setLegalEntity(rs.getString("ENTITYNAME"));
			objProductCatelogDTO.setBillingMode(rs.getString("BILLINGMODE"));
			objProductCatelogDTO.setBillingformat(rs.getString("BILLING_FORMATNAME"));
			objProductCatelogDTO.setLicCompanyName(rs.getString("LCOMPANYNAME"));
			objProductCatelogDTO.setTaxationName(rs.getString("TAXATIONVALUE"));
			objProductCatelogDTO.setBillingLevel(rs.getString("BILLING_LEVELNAME"));
			objProductCatelogDTO.setCommitmentPeriod(rs.getInt("COMMITMENTPERIOD"));
			objProductCatelogDTO.setPenaltyClause(rs.getString("PENALTYCLAUSE"));
			objProductCatelogDTO.setBillingTypeName(rs.getString("BILLING_TYPENAME"));
			objProductCatelogDTO.setBcpName(rs.getString("BCP_NAME"));
			objProductCatelogDTO.setBillingLevelNo(Long.parseLong(rs.getString("BILLING_LEVEL_NO")));
			objProductCatelogDTO.setNoticePeriod(rs.getInt("NOTICEPERIOD"));
			objProductCatelogDTO.setIsNfa(rs.getInt("IS_NFA"));
			objProductCatelogDTO.setTaxation(rs.getString("REASONNAME"));
			objProductCatelogDTO.setBillingScenario(rs.getInt("BILLING_SCENARIO"));
			objProductCatelogDTO.setStoreName(rs.getString("STORENAME"));
			objProductCatelogDTO.setHardwareType(rs.getString("HARDWARETYPENAME"));
			objProductCatelogDTO.setFormAvailable(rs.getString("FORMNAME"));
			objProductCatelogDTO.setSaleType(rs.getString("SALETYPENAME"));
			objProductCatelogDTO.setSaleNature(rs.getString("SALENATURENAME"));
			objProductCatelogDTO.setDispatchName(rs.getString("DISPATCHADDNAME"));
			objProductCatelogDTO.setTxtStartDateLogic(rs.getString("STARTDATELOGIC"));
			objProductCatelogDTO.setTxtEndDateLogic(rs.getString("ENDDATELOGIC"));
			objProductCatelogDTO.setTxtStartDays(rs.getInt("START_DAYS"));
			objProductCatelogDTO.setTxtStartMonth(rs.getInt("START_MONTHS"));
			objProductCatelogDTO.setTxtEndDays(rs.getInt("END_DAYS"));
			objProductCatelogDTO.setTxtEndMonth(rs.getInt("END_MONTH"));
			objProductCatelogDTO.setTxtExtDays(rs.getInt("EXT_DAYS"));
			objProductCatelogDTO.setTxtExtMonth(rs.getInt("EXT_MONTHS"));
			objProductCatelogDTO.setWarrentyMonths(rs.getInt("WARRENTYMONTHS"));
			objProductCatelogDTO.setPrincipalAmount(rs.getDouble("PRINCIPAL_AMOUNT"));
			objProductCatelogDTO.setInterestRate(rs.getDouble("INTEREST_RATE"));
			objProductCatelogDTO.setDispatchContactName(rs.getString("DISPATCH_CONTACTNAME"));
			objProductCatelogDTO.setLineManagedBy(rs.getString("MANAGED_BY"));
			objProductCatelogDTO.setLine2DProduct(rs.getString("PRODUCT"));
			objProductCatelogDTO.setLineIndicativeValue(rs.getString("INDICATIVE_VALUE"));
			objProductCatelogDTO.setLinePaymentTerms(rs.getString("PAYMENT_TERMS"));
			objProductCatelogDTO.setLineRemarks(rs.getString("HARDWARE_REMARKS"));
			objProductCatelogDTO.setChargeTypeName(rs.getString("CHARGETYPE"));
			objProductCatelogDTO.setChargeName(rs.getString("CHARGE_NAME"));
			objProductCatelogDTO.setChargePeriod(rs.getInt("CHARGEPERIOD"));
			objProductCatelogDTO.setChargeAmount(rs.getDouble("CHARGEAMOUNT"));
			objProductCatelogDTO.setFrequencyName(rs.getString("FREQUENCYNAME"));
			objProductCatelogDTO.setChargeFrequencyAmount(rs.getDouble("CHARGEFREQUENCYAMT"));
			objProductCatelogDTO.setStartDateLogic(rs.getString("STARTDATELOGIC"));
			objProductCatelogDTO.setEndDateLogic(rs.getString("ENDDATELOGIC"));
			objProductCatelogDTO.setChargeStartDays(rs.getInt("START_DATE_DAYS"));
			objProductCatelogDTO.setChargeStartMonths(rs.getInt("START_DATE_MONTH"));
			objProductCatelogDTO.setChargeEndDays(rs.getInt("END_DATE_DAYS"));
			objProductCatelogDTO.setChargeEndMonths(rs.getInt("END_DATE_MONTH"));
			objProductCatelogDTO.setAnnotation(rs.getString("ANNOTATION"));
			objProductCatelogDTO.setAnnualRate(rs.getDouble("ANNUAL_RATE"));
			objProductCatelogDTO.setChargeRemarks(rs.getString("CHARGE_REMARKS"));
			objProductCatelogDTO.setChargeTaxRate(rs.getString("TAXRATE"));
			CancelledhardwareLineItems.add(objProductCatelogDTO);
		}
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rs);	
			DbConnection.closePreparedStatement(getLicCompany);
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return CancelledhardwareLineItems;
}

public ArrayList<NewOrderDto> getContactDetails(PagingDto objDto) 
{
	//	Nagarjuna
	String methodName="getContactDetails", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement cstmt =null;
	ResultSet rsContactDetails = null;
	NewOrderDto objNewOrderDto = null;
	ArrayList<NewOrderDto> listContactDetails = new ArrayList<NewOrderDto>();
	try
	{
		connection=DbConnection.getConnectionObject();
		cstmt= connection.prepareCall(sqlspGetContactDetails);
		
		if (objDto.getFirstName() != null && !"".equals(objDto.getFirstName())) {
			cstmt.setString(1, objDto.getFirstName().trim());
		} else {
			cstmt.setNull(1, java.sql.Types.VARCHAR);
		}
		
		if (objDto.getLastName() != null && !"".equals(objDto.getLastName())) {
			cstmt.setString(2, objDto.getLastName().trim());
		} else {
			cstmt.setNull(2, java.sql.Types.VARCHAR);
		}
		
		if (objDto.getAddress1() != null && !"".equals(objDto.getAddress1())) {
			cstmt.setString(3, objDto.getAddress1().trim());
		} else {
			cstmt.setNull(3, java.sql.Types.VARCHAR);
		}
		
		if (objDto.getCityName() != null && !"".equals(objDto.getCityName())) {
			cstmt.setString(4, objDto.getCityName().trim());
		} else {
			cstmt.setNull(4, java.sql.Types.VARCHAR);
		}
		
		if (objDto.getStateName() != null && !"".equals(objDto.getStateName())) {
			cstmt.setString(5, objDto.getStateName().trim());
		} else {
			cstmt.setNull(5, java.sql.Types.VARCHAR);
		}
		
		if (objDto.getOrderNumber() != 0 
				&& !"".equals(objDto.getOrderNumber())) {
			cstmt.setInt(6, objDto.getOrderNumber());
		} else {
			cstmt.setNull(6, java.sql.Types.BIGINT);
		}
		
		if (objDto.getAccountno() != null && !"".equals(objDto.getAccountno())) {
			cstmt.setString(7, objDto.getAccountno().trim());
		} else {
			cstmt.setNull(7, java.sql.Types.VARCHAR);
		}
		
		cstmt.setString(8,objDto.getSortBycolumn());
		cstmt.setString(9,objDto.getSortByOrder());
		cstmt.setInt(10,objDto.getStartIndex());
		cstmt.setInt(11,objDto.getEndIndex());
		cstmt.setInt(12,1);
		rsContactDetails = cstmt.executeQuery();
		while(rsContactDetails.next())
		{
			objNewOrderDto =  new NewOrderDto();
			objNewOrderDto.setContactType(rsContactDetails.getString("CONTACTTYPE"));
			objNewOrderDto.setContactTypeId(rsContactDetails.getString("CONTACTTYPEID"));
			objNewOrderDto.setSalutationName(rsContactDetails.getString("SALUTATION"));
			objNewOrderDto.setFirstName(rsContactDetails.getString("FIRSTNAME"));
			objNewOrderDto.setLastName(rsContactDetails.getString("LASTNAME"));
			objNewOrderDto.setCntEmail(rsContactDetails.getString("EMAIL"));
			objNewOrderDto.setContactCell(rsContactDetails.getString("CELLNO"));
			objNewOrderDto.setContactFax(rsContactDetails.getString("FAXNO"));
			objNewOrderDto.setOrderNumber(rsContactDetails.getInt("ORDERNO"));
			
			objNewOrderDto.setAddress1(rsContactDetails.getString("ADDRESS1"));
			objNewOrderDto.setAddress2(rsContactDetails.getString("ADDRESS2"));
			objNewOrderDto.setAddress3(rsContactDetails.getString("ADDRESS3"));
			objNewOrderDto.setCityName(rsContactDetails.getString("CITY"));
			objNewOrderDto.setStateName(rsContactDetails.getString("STATE"));
			objNewOrderDto.setCountyName(rsContactDetails.getString("COUNTRY"));
			objNewOrderDto.setPinNo(rsContactDetails.getString("PINCODE"));
			objNewOrderDto.setCityCode(rsContactDetails.getInt("CITYID"));
			objNewOrderDto.setStateCode(rsContactDetails.getInt("STATEID"));
			objNewOrderDto.setCountyCode(rsContactDetails.getInt("COUNTRYCODE"));
			
			objNewOrderDto.getPagingSorting().setRecordCount(rsContactDetails.getInt("FULL_REC_COUNT"));	
			objNewOrderDto.setMaxPageNo(objNewOrderDto.getPagingSorting().getMaxPageNumber());
			
			listContactDetails.add(objNewOrderDto);			
		}
		return listContactDetails;
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rsContactDetails);
			DbConnection.closeCallableStatement(cstmt);
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return listContactDetails;
}
public static String sqlSpGetServiceDetailsForSwitchingService= "{call IOE.GET_DETAILS_FOR_SWITCHING_SERVICE(?)}";
public ArrayList<NewOrderDto> getServiceDetailsForSwitchingService(long serviceId) 
{
	//	Nagarjuna
	String methodName="getServiceDetailsForSwitchingService", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	PreparedStatement pstmt=null;
	ResultSet rs  = null;
	NewOrderDto objNewOrderDto = null;
	ArrayList<NewOrderDto> listdetails = new ArrayList<NewOrderDto>();
	try
	{
		connection=DbConnection.getConnectionObject();
		pstmt= connection.prepareCall(sqlSpGetServiceDetailsForSwitchingService);
		pstmt.setLong(1,serviceId);
		rs = pstmt.executeQuery();
		while(rs.next())
		{
			objNewOrderDto =  new NewOrderDto();
			objNewOrderDto.setServiceTypeName(rs.getString("SERVICETYPENAME"));
			objNewOrderDto.setServiceProductID(rs.getInt("SERVICEPRODUCTID"));
			objNewOrderDto.setServiceDetDescription(rs.getString("SERVICEDETDESCRIPTION"));
			objNewOrderDto.setServiceDetailID(rs.getInt("SERVICEDETAILID"));
			objNewOrderDto.setLink(rs.getString("LINK"));
			objNewOrderDto.setServiceName(rs.getString("SERVICETYPE"));
			objNewOrderDto.setServiceTypeId(rs.getInt("SERVICETYPEID"));
			
			listdetails.add(objNewOrderDto);
		}
		return listdetails;
		
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rs);
			DbConnection.closePreparedStatement(pstmt);
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return listdetails;
}

public static String sqlspServiceListWithPC= "{call IOE.GET_SERVICE_FOR_NAVIGATION(?)}";
public ArrayList<ServiceLineDTO> poulateServiceListForArrayLoading(long orderNo) throws Exception 
{
	//	Nagarjuna
	String methodName="poulateServiceListForArrayLoading", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement callstmt =null;
	ResultSet rs = null;
	ServiceLineDTO objRetDto = null;
	ArrayList<ServiceLineDTO> lstServiceList = new ArrayList<ServiceLineDTO>();
	try
	{
		connection=DbConnection.getConnectionObject();
		connection.setAutoCommit(false);
		callstmt= connection.prepareCall(sqlspServiceListWithPC);	
		callstmt.setLong(1, Long.valueOf(orderNo));
		rs = callstmt.executeQuery();
		while(rs.next())
		{
			objRetDto =  new ServiceLineDTO();
			objRetDto.setServiceId(rs.getInt("SERVICEID"));
			lstServiceList.add(objRetDto);
		}
		
	}
	catch(Exception ex )
	{
		connection.rollback();
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{

			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(callstmt);
			DbConnection.freeConnection(connection);
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return lstServiceList;
}

//[080]Start : Copy Service product For New and  Chage Order
private static final String sqlSp_copyServiceProductForNewOrder = "{call IOE.SP_COPYSERVICEPRODUCT_FOR_NEWORDER(?,?,?,?,?,?,?,?,?,?)}";
public CommonDTO copyServiceProductForNewOrder(ProductCatelogDTO objDto, String empID) throws IOESException
{
	String methodName="copyServiceProductForNewOrder", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//AppConstants.IOES_LOGGER.info(methodName+" method of "+className+" class have been called");
	
	CommonDTO dto = null;
	Connection connection =null;
	CallableStatement cstmt = null;	
	try {
		long msgCode=0;
		String cslServiceID="";
		connection=DbConnection.getConnectionObject();
		connection.setAutoCommit(false);						
		int numOfCopy = Integer.parseInt(objDto.getCopyProductValues());	
						
			cstmt= connection.prepareCall(sqlSp_copyServiceProductForNewOrder);
			cstmt.setString(1,objDto.getSpIdString());
			cstmt.registerOutParameter(2,java.sql.Types.VARCHAR);
			cstmt.registerOutParameter(3,java.sql.Types.VARCHAR);
			cstmt.registerOutParameter(4,java.sql.Types.BIGINT);
			cstmt.registerOutParameter(5,java.sql.Types.VARCHAR);
			cstmt.registerOutParameter(6,java.sql.Types.BIGINT);
			cstmt.registerOutParameter(7,java.sql.Types.VARCHAR);
			cstmt.setLong(8,Long.parseLong(empID));
			cstmt.setLong(9,numOfCopy);
			cstmt.setLong(10,objDto.getPoNumber());
			
			cstmt.execute();
			dto = new CommonDTO();
			msgCode=cstmt.getLong(4);
			String message=cstmt.getString(2);	
			cslServiceID=cstmt.getString(7);
			
			
			if(msgCode==1)
			{
				//String message=cstmt.getString(2);
				message=" Following LineItem(s) Copied Succesfully \n " +cslServiceID;
				dto.setMessage(message);
				dto.setMessageId(cstmt.getString(3));
				Utility.LOG(true,true,"@errMessage :"+cstmt.getString(5));
				connection.commit();
			}
			else
			{
				message=cstmt.getString(2);
				dto.setMessage(message);
				dto.setMessageId(cstmt.getString(3));
				Utility.LOG(true,true,"Error in Copy Service Product Functionality::::::::::");
				Utility.LOG(true,true,"@message :"+cstmt.getString(2));
				Utility.LOG(true,true,"@messageId :"+cstmt.getString(3));
				Utility.LOG(true,true,"@msgCode :"+cstmt.getLong(4));
				Utility.LOG(true,true,"@errMessage :"+cstmt.getString(5));
				Utility.LOG(true,true,"@sqlCode :"+cstmt.getLong(6));
				connection.rollback();
			}
		
	} catch (Exception e) {
		try {
			connection.rollback();
		} catch (SQLException e1) {
			Utility.onEx_LOG_RET_NEW_EX(e1, methodName, className, msg, logToFile, logToConsole);
		}finally{
		throw Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);
		}
	}finally
	{
		try {
			DbConnection.closeCallableStatement(cstmt);
			DbConnection.freeConnection(connection);
		} catch (Exception e) {
			throw Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);
		}
	}
	
	return dto;
}
//[080] End	
	public static String sqlSpGetChargesForDisconnectionAndCopy= "{call IOE.GET_CHARGES_FOR_DISCONNECTION_AND_COPY(?,?,?,?)}";
	/**
	 * @author Vijay
	 * @param chargeId
	 * @param serviceProductId
	 * @param serviceId
	 * @param orderNo
	 * @param frequency
	 * @return A List that contain all active charges and eligible for disconnection
	 */
	public ArrayList<ChargeComponentDTO> getChargesForDisconnectAndCopy(long chargeId, long serviceProductId, long serviceId, int frequency) {
		String methodName="getChargesForDisconnectAndCopy", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;
		Connection connection =null;
		PreparedStatement pstmt=null;
		ResultSet rs  = null;
		ChargeComponentDTO objChargesDetailDto = null;
		ArrayList<ChargeComponentDTO> listdetails = new ArrayList<ChargeComponentDTO>();
		try{
			connection=DbConnection.getConnectionObject();
			pstmt= connection.prepareCall(sqlSpGetChargesForDisconnectionAndCopy);
			pstmt.setLong(1,chargeId);
			pstmt.setLong(2,serviceProductId);
			pstmt.setLong(3,serviceId);
			pstmt.setInt(4,frequency);
			rs = pstmt.executeQuery();
			while(rs.next()){
				objChargesDetailDto =  new ChargeComponentDTO();
				objChargesDetailDto.setChargeInfoID(rs.getInt("CHARGEINFOID"));
				objChargesDetailDto.setChargeTypeName(rs.getString("CHARGESTYPE_NAME"));//RC or NRC
				objChargesDetailDto.setChargeType(rs.getInt("CHARGESTYPE")); //1 or 2
				objChargesDetailDto.setChargeName(rs.getString("CHARGE_NAME")); //actual charge name
				objChargesDetailDto.setChargePeriod(rs.getInt("CHARGEPERIOD")); 
				objChargesDetailDto.setChargeAmount(rs.getDouble("CHARGEAMOUNT"));
				objChargesDetailDto.setStartDateLogic(rs.getString("STARTDATELOGIC"));
				objChargesDetailDto.setEndDateLogic(rs.getString("ENDDATELOGIC"));
				
				if(rs.getInt("CHARGESTYPE") == 1 && rs.getInt("ISRCACTIVE") == 1)
				{
					
					objChargesDetailDto.setRcActiveDateCrossed("1");
				}else if(rs.getInt("CHARGESTYPE") == 1 && rs.getInt("ISRCACTIVE") == 0){
					objChargesDetailDto.setRcActiveDateCrossed("0");
				}
				
				if(rs.getInt("CHARGESTYPE") == 2 && rs.getInt("ISNRCACTIVE") == 1)
				{
					
					objChargesDetailDto.setNrcActiveDateCrossed("1");
				}else if(rs.getInt("CHARGESTYPE") == 2 && rs.getInt("ISNRCACTIVE") == 0){
					objChargesDetailDto.setNrcActiveDateCrossed("0");
				}
				if(rs.getInt("CHARGESTYPE") == 1 && rs.getInt("ISRCINACTIVE_DATE_MEET") == 1)
				{
					
					objChargesDetailDto.setRcInactiveDateCrossed("1");
				}else if(rs.getInt("CHARGESTYPE") == 1 && rs.getInt("ISRCINACTIVE_DATE_MEET") == 0){
					objChargesDetailDto.setRcInactiveDateCrossed("0");
				}
				
				
				listdetails.add(objChargesDetailDto);
			}
			return listdetails;
		}catch(Exception ex ){
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);
		}finally{
			try{
				DbConnection.closeResultset(rs);
				DbConnection.closePreparedStatement(pstmt);
				DbConnection.freeConnection(connection);
			}catch (Exception e){
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);
			}
		}
		return listdetails;
	}
	private String sqlGetDisconnectedChargeInfo= "{call IOE.GET_DISCONNECTED_CHARGE_INFO(?)}";
	/**@Author Vijay
	 * @param serviceProductId
	 * @return A charge details object of ChargeComponentDTO. The returning object will 
	 * contain the charge related details on the basis of chargeId that is passing in this method as argument
	 */
	public ChargesDetailDto populateChargeForCopyDisconnection(long chargeId){
		String methodName="populateChargeForCopyDisconnection", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;
		Connection connection =null;
		CallableStatement pstmt =null;
		ResultSet rs = null;
		ChargesDetailDto objChargesDetailDto = null;
		try{
			connection=DbConnection.getConnectionObject();
			pstmt = connection.prepareCall(sqlGetDisconnectedChargeInfo);
			pstmt.setLong(1, chargeId);
			rs = pstmt.executeQuery();
			while(rs.next()){
				objChargesDetailDto =  new ChargesDetailDto();
				objChargesDetailDto.setChargeInfoID(rs.getInt("CHARGEINFOID"));
				objChargesDetailDto.setChargeTypeID(rs.getInt("CHARGESTYPE"));
				objChargesDetailDto.setChargeNameID(rs.getInt("CHARGE_NAME_ID"));
				objChargesDetailDto.setChargeName(rs.getString("CHARGE_NAME"));
				objChargesDetailDto.setChargeAnnotation(rs.getString("ANNOTATION"));
				objChargesDetailDto.setTaxRate(rs.getString("TAXRATE"));
				objChargesDetailDto.setChargePeriod(rs.getInt("CHARGEPERIOD"));
				objChargesDetailDto.setChargeAmount(rs.getDouble("CHARGEAMOUNT"));
				objChargesDetailDto.setChargeFrequency(rs.getString("CHARGEFREQUENCY"));
				objChargesDetailDto.setChargeFrequencyAmt(rs.getDouble("CHARGEFREQUENCYAMT"));
				objChargesDetailDto.setStartDateLogic(rs.getString("STARTDATELOGIC"));
				objChargesDetailDto.setEndDateLogic(rs.getString("ENDDATELOGIC"));
				objChargesDetailDto.setPaymentTerm1(rs.getInt("PAYMENTTERM1"));
				objChargesDetailDto.setPaymentTerm2(rs.getInt("PAYMENTTERM2"));
				objChargesDetailDto.setPaymentTerm3(rs.getInt("PAYMENTTERM3"));
				objChargesDetailDto.setPaymentTerm4(rs.getInt("PAYMENTTERM4"));
			}
		}catch (Exception ex){
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);
		}finally{
			try {
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(pstmt);
				DbConnection.freeConnection(connection);
			} catch (Exception e) {
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);
			}
		}
		return objChargesDetailDto;
	}
	
	public Object[] getDisconnectLinkedCharges(long serviceProductId, long serviceId){
		Connection connection =null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		String sql = "SELECT OB_LINK_CHARGEID FROM IOE.TCHARGES_INFO WHERE CREATEDIN_SERVICEID = " + serviceId + " AND SERVICEPRODUCTID = " + serviceProductId +
				" AND OB_LINK_CHARGEID is not null AND OB_LINK_CHARGEID <> 0";
		ArrayList<Integer> disconnectChargeList = new ArrayList();
		try{
			connection=DbConnection.getConnectionObject();
			pstmt = connection.prepareCall(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				disconnectChargeList.add(rs.getInt("OB_LINK_CHARGEID"));
			}
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			DbConnection.closeStatement(pstmt);
			try {
				DbConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return disconnectChargeList.toArray();
		
	}
//vijay start
public static String sqlGetServiceCountCheck =  "{CALL IOE.GET_TOTAL_SERVICE_COUNT_CHECK(?,?,?)}";
public String totalServiceCountCheck(long orderNo, int copyCount) 
{
	Connection connection =null;
	CallableStatement callstmt =null;
	String message = "";
	try{
		connection=DbConnection.getConnectionObject();
		connection.setAutoCommit(false);
		callstmt= connection.prepareCall(sqlGetServiceCountCheck);
		callstmt.setLong(1,orderNo);
		callstmt.setInt(2,copyCount);
		callstmt.setString(3,"");
		callstmt.execute();
		message = callstmt.getString(3);
	}catch(Exception ex ){
		ex.printStackTrace();	
	}finally{
		try{
			DbConnection.closeCallableStatement(callstmt);
			DbConnection.freeConnection(connection);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	return message;
}
//vijay end

//OPS01042013001 added by manisha start 
public String getSpecialCharContact()
{

	PreparedStatement pstmt = null;
	ResultSet rset = null;
	Connection iomsConn = null;
	CallableStatement csIOMS = null;
	String ichar="";
	try {
		Utility.SysOut("Connect with IOMS database For SpeicialChars Flag==>");
		iomsConn = DbConnection.getConnectionObject();
		
		csIOMS=iomsConn.prepareCall("SELECT KEYVALUE FROM IOE.TM_APPCONFIG WHERE KEYNAME = 'SPECIALCHARSCONTACTTAB'");
		rset=csIOMS.executeQuery();
		Utility.SysOut("fetching Flag");
		while(rset.next())
		{
			ichar=rset.getString("KEYVALUE");
						
		}
	
	} catch (Exception e) {
		System.out.println("Error in method SpeicialChars()"
				+ e.getStackTrace());
		e.printStackTrace();
	} finally {
		try {
			DbConnection.closeResultset(rset);
			DbConnection.closePreparedStatement(pstmt);
			DbConnection.freeConnection(iomsConn);
		} catch (Exception e) {
			System.out.println("exeption due to : " + e.getMessage());
		}
	}
	return ichar;
	 
}

//[093] start


public String getSpecialCharValue()
{

	PreparedStatement pstmt = null;
	ResultSet rset = null;
	Connection iomsConn = null;
	CallableStatement csIOMS = null;
	String ichar="";
	try {
		Utility.SysOut("Connect with IOMS database For SpeicialChars Flag==>");
		iomsConn = DbConnection.getConnectionObject();
		
		pstmt=iomsConn.prepareStatement("SELECT KEYVALUE FROM IOE.TM_APPCONFIG WHERE KEYNAME = 'SPECIALCHAR'");
		rset=pstmt.executeQuery();
		Utility.SysOut("fetching Flag");
		while(rset.next())
		{
			ichar=rset.getString("KEYVALUE");
						
		}
	
	} catch (Exception e) {
		System.out.println("Error in method SpeicialCharsValue()"
				+ e.getStackTrace());
		e.printStackTrace();
	} finally {
		try {
			DbConnection.closeResultset(rset);
			DbConnection.closePreparedStatement(pstmt);
			DbConnection.freeConnection(iomsConn);
		} catch (Exception e) {
			System.out.println("exeption due to : " + e.getMessage());
		}
	}
	return ichar;
	 
}


//[093] end
 
//OPS01042013001 added by manisha end 
	public ArrayList<ServiceLineDTO> getDowntimeClause(ServiceLineDTO objDto) 
	{
		Connection connection =null;
		PreparedStatement getlistDC =null;

		ResultSet rsProdList = null;
		ServiceLineDTO objNewOrderDto = null;
		ArrayList<ServiceLineDTO> listDC = new ArrayList<ServiceLineDTO>();
		try
		{
			connection=DbConnection.getConnectionObject();
			getlistDC= connection.prepareCall(sqlGetDownTimeClause);
			getlistDC.setLong(1,objDto.getServiceId());
			getlistDC.setLong(2,objDto.getServiceSubtypeId());
			rsProdList = getlistDC.executeQuery();
			while(rsProdList.next())
			{
				objNewOrderDto =  new ServiceLineDTO();
				objNewOrderDto.setDowntimeClause(rsProdList.getString("DOWNTIMECLAUSE"));
				//objNewOrderDto.setServiceSubTypeName(rsProdList.getString("SERVICESUBTYPENAME"));
				listDC.add(objNewOrderDto);
				System.out.println("list from dao"+listDC);
			}
			
		}
		catch(Exception ex )
		{
			ex.printStackTrace();	
		}
		finally
		{
			try 
			{
				DbConnection.closeResultset(rsProdList);
				DbConnection.closePreparedStatement(getlistDC);
				DbConnection.freeConnection(connection);
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return listDC;
		
		
	}
	// addeed by manisha cust bil exp bfr no 7 start
	public String fnupdatedemoenddate(String logicalsilist,String noofdayslist,String demoendlist,String orderlist,String demodayslist) throws SQLException
	{
		//	Nagarjuna
		String methodName="reassignPM", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;
		//end Nagarjuna
		Connection connection =null;
		ArrayList<String> logicalsi = new ArrayList<String>();
		ArrayList<String> Listorder = new ArrayList<String>();
		ArrayList<String> listdemodays = new ArrayList<String>();
		ArrayList<String> noofdays = new ArrayList<String>();
		ArrayList<String> demoenddate = new ArrayList<String>();
		PreparedStatement statement = null;
		String output ="";
		NewOrderDao objDao = new NewOrderDao();
		PreparedStatement ps=null;
		PreparedStatement ps_update=null;
		int k=0;
		int arrLen=0;
		try
		{
			connection=DbConnection.getConnectionObject();
			connection.setAutoCommit(false);
			
			StringTokenizer st_logicalsi  = new StringTokenizer( logicalsilist, ",");
			for (int i =0; st_logicalsi.hasMoreTokens();i++) 
			{
				logicalsi.add(st_logicalsi.nextToken());
			}
			
			StringTokenizer st_orderList  = new StringTokenizer( orderlist, ",");
			for (int i =0; st_orderList.hasMoreTokens();i++) 
			{
				Listorder.add(st_orderList.nextToken());
			}
			
			StringTokenizer st_demodayslist  = new StringTokenizer( demodayslist, ",");
			for (int i =0; st_demodayslist.hasMoreTokens();i++) 
			{
				listdemodays.add(st_demodayslist.nextToken());
			}
			
			StringTokenizer st_noofdays  = new StringTokenizer( noofdayslist, ",");
			for (int i =0; st_noofdays.hasMoreTokens();i++) 
			{
				noofdays.add(st_noofdays.nextToken());
			}
			
			StringTokenizer st_demodate  = new StringTokenizer( demoendlist, ",");
			for (int i =0; st_demodate.hasMoreTokens();i++) 
			{
				demoenddate.add(st_demodate.nextToken());
			}
			
			String query="update ioe.TPOSERVICEMASTER set DEMO_END_DATE=?  where LOGICAL_SI_NO = ?";
			ps = connection.prepareStatement(query);
		    for(int count=0; count<logicalsi.size();count++)
		    {
		    	
		    		/*SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yy");
		    		java.util.Date date=sdf.parse(demoenddate.get(count));
		    		sdf=new SimpleDateFormat("MM/dd/yyyy");
		    		System.out.println(sdf.format(date));
		    		java.util.Date ddt=addDays(date, Integer.parseInt(noofdays.get(count)));
		    		System.out.println(sdf.format(ddt));
		    		System.out.println(Integer.parseInt(logicalsi.get(count)));
		    		ps.setDate(1,new java.sql.Date((ddt).getTime()));
				    ps.setInt(2,(Integer.parseInt(logicalsi.get(count))));
				    ps.addBatch();*/
				    
				    
				    SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yy");
				    java.util.Date date=sdf.parse(demoenddate.get(count));
		    		sdf=new SimpleDateFormat("MM/dd/yyyy");
		    		Calendar c = Calendar.getInstance();
		    		c.setTime(date); // Now use today date.
		    		c.add(Calendar.DATE, Integer.parseInt(noofdays.get(count))); // Adding 5 days
		    		java.util.Date ddt=c.getTime();
		    		ps.setDate(1, new java.sql.Date((ddt).getTime()));
				    ps.setInt(2,(Integer.parseInt(logicalsi.get(count))));
				    ps.addBatch();
		    }
		    int[] recordsAffected = ps.executeBatch();
		    for(int index = 0 ; index < recordsAffected.length; index++)
		    {
		    	if(recordsAffected[index] == Statement.EXECUTE_FAILED )
		    	{
		    		throw new Exception();
		    	}
		    	else 
				{
					System.out.println("LOC Data Updated Successfully");
					output="Update Successfully";
					connection.commit();
				}
		    	
		    }
			/*String query2="UPDATE IOE.TPOMASTER SET NO_OF_DAYS=? WHERE ORDERNO=?";
			ps_update = connection.prepareStatement(query2);
			int j=0;
			for(int count=0; count<Listorder.size();count++)
			    {
				     ps_update.setInt(1,Integer.parseInt(listdemodays.get(count)));
				     ps_update.setInt(2,Integer.parseInt(Listorder.get(count)));
				     ps_update.addBatch();
				     j++;
				}
				int[] updateTemp = ps_update.executeBatch();
				for(int index = 0 ; index < updateTemp.length; index++)
				    {
				    	if(updateTemp[index] == Statement.EXECUTE_FAILED )
				    	{
				    		throw new Exception();
				    	}*/
		
		}		

		catch(Exception ex )
		{
			//ex.printStackTrace();
			connection.rollback();
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
		finally
		{
			try 
			{
				DbConnection.closeStatement(statement);
				DbConnection.freeConnection(connection);
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				//e.printStackTrace();
				connection.rollback();
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
			}
		}
		return output;
	}
	public static java.util.Date addDays(java.util.Date d, int days)
    {
        d.setTime(d.getTime() + days * 1000 * 60 * 60 * 24);
        return d;
    }
	// addeed by manisha cust bil exp bfr no 7 end
	
	
	
	
	
	public int validateDemoDays(int orderno) 
	{
		Connection connection =null;
		CallableStatement getlistDC =null;
		ResultSet rsProdList = null;
		int status=0;
		try
		{
			connection=DbConnection.getConnectionObject();
			getlistDC= connection.prepareCall(sqlValidatedemodays);
			getlistDC.setInt(1,orderno);
			getlistDC.setInt(2,0);
			getlistDC.execute();
			status=getlistDC.getInt(2);
			
		}
		catch(Exception ex )
		{
			ex.printStackTrace();	
		}
		finally
		{
			try 
			{
				DbConnection.closeResultset(rsProdList);
				DbConnection.closePreparedStatement(getlistDC);
				DbConnection.freeConnection(connection);
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return status;
		
		
	}
	
	//[017] Start
	private final String sqlValidateParallelUpgradeLSINo = "call IOE.SP_PARALLEL_UPGRADE_LSI_VALIDATION(?,?,?)";
	public String parallelUpgradeValidation(int orderNo,String stage) 
	{
		Connection connection =null;
		CallableStatement getValidationResult =null;
		NewOrderDto objNewOrderDto = null;
		String errorMsg=null;
		try
		{
			connection=DbConnection.getConnectionObject();
			getValidationResult= connection.prepareCall(sqlValidateParallelUpgradeLSINo);
			getValidationResult.setLong(1,orderNo);
			getValidationResult.setString(2,stage);//Can be Either Publish or Billing Trigger
			getValidationResult.setString(3,"");
			getValidationResult.execute();
			errorMsg=getValidationResult.getString(3);
			System.err.println(errorMsg);			
		}
		catch(Exception ex )
		{
			ex.printStackTrace();	
		}
		finally
		{
			try 
			{
				DbConnection.freeConnection(connection);
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return errorMsg;
	}
	//[017] End
	public List<RoleSectionDetailDTO> getRoleWiseSectionDetails(int role){
		Connection connection =null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		List<RoleSectionDetailDTO> roleSectionDetails = null;
		try{
			connection=DbConnection.getConnectionObject();
			pstmt = connection.prepareCall(sqlFetchRoleWiseSectionDetail);
			pstmt.setInt(1, role);
			rs = pstmt.executeQuery();
			RoleSectionDetailDTO roleSectionDetailDTO = null;
			while(rs.next()){
				if(null == roleSectionDetails)
					roleSectionDetails = new ArrayList<RoleSectionDetailDTO>();
				roleSectionDetailDTO = new RoleSectionDetailDTO();
				if(rs.getInt("COMMERCIALALLOWED") == 1)
					roleSectionDetailDTO.setCommercialAllowed(true);
				else
					roleSectionDetailDTO.setCommercialAllowed(false);
				if(rs.getInt("SUBSECTIONATTRIBUTECHECK") == 1)
					roleSectionDetailDTO.setSubSectionAttributeCheck(true);
				else
					roleSectionDetailDTO.setSubSectionAttributeCheck(false);
				if(rs.getInt("SECTIONCOMMERCIAL") == 1)
					roleSectionDetailDTO.setSectionCommercial(true);
				else
					roleSectionDetailDTO.setSectionCommercial(false);
				roleSectionDetailDTO.setSection(rs.getString("SECTIONNAME"));
				if (null == roleSectionDetails)
					roleSectionDetails = new ArrayList<RoleSectionDetailDTO>();
				roleSectionDetails.add(roleSectionDetailDTO);
				
			}
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			DbConnection.closeStatement(pstmt);
			try {
				DbConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return roleSectionDetails;
		
	}
	public List<FieldAttibuteDTO> getServiceAttList(int serviceDetailId){
		Connection connection =null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		List<FieldAttibuteDTO> attributeList = null;
		try{
			connection=DbConnection.getConnectionObject();
			pstmt = connection.prepareCall(sqlFetchServiceAttribute);
			pstmt.setInt(1, serviceDetailId);
			rs = pstmt.executeQuery();
			FieldAttibuteDTO attributeDTO = null;
			while(rs.next()){
				if(null == attributeList)
					attributeList = new ArrayList<FieldAttibuteDTO>();
				attributeDTO = new FieldAttibuteDTO();
				attributeDTO.setAttMasterId(rs.getInt("ATTMASTERID"));
				attributeDTO.setAlisName(rs.getString("ALISNAME"));
				attributeDTO.setIsCommercial(rs.getInt("IS_COMMERCIAL"));
				attributeList.add(attributeDTO);
			}
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			DbConnection.closeStatement(pstmt);
			try {
				DbConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return attributeList;
	}
	
	public Boolean isRolePassedEscalation(String orderNo, String role){
		if(null == orderNo || null == role)
			return false;
		Connection connection =null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		String sql = "select ESC.RM1_AGING, WORKFLOWTASK.IS_FIRST_TASK, WORKFLOWTASK.CREATED_DATE from IOE.TM_ESCLATION ESC " +
				"right outer join IOE.TM_ACCOUNT ACCOUNT on Account.CUSTOMERSEGMENT = ESC.CUSTOMER_SEGMENT_ID and " +
				"Account.CRM_REGIONID = ESC.REGION_ID inner join IOE.TPOMASTER TPOMASTER on TPOMASTER.ACCOUNTID = ACCOUNT.ACCOUNTID " +
				"inner join IOE.TPOWORKFLOWTASK WORKFLOWTASK on WORKFLOWTASK.ORDERNO = TPOMASTER.ORDERNO and " +
				"WORKFLOWTASK.OWNERTYPE_ID= ESC.ROLE_ID where TPOMASTER.ORDERNO = ? and ESC.ROLE_ID=?";
		String maxPODate = "SELECT  Max(PORECEIVEDATE) as PORECEIVEDATE FROM IOE.TPODETAILS TPODETAILS where PONUMBER = ?";
		String addedHour = "select KEYVALUE from IOE.TM_APPCONFIG where KEYNAME = 'ESCALATION_PO_DATE_CLOCK_START_HOUR'";
		Boolean flag = false;
		try{
			connection=DbConnection.getConnectionObject();
			pstmt = connection.prepareCall(sql);
			pstmt.setString(1, orderNo);
			pstmt.setString(2, role);
			rs = pstmt.executeQuery();
			Integer aging = null;
			Timestamp createdDate = null;
			Boolean isFirstsTask = null;
			while(rs.next()){
				createdDate = rs.getTimestamp("CREATED_DATE");
				aging = rs.getInt("RM1_AGING");
				isFirstsTask = rs.getBoolean("IS_FIRST_TASK");
			}
			if(null == aging){
				aging = AppConstants.defaultEscalationTime;
			}
			if(null != isFirstsTask &&  isFirstsTask){
				createdDate = null;
				pstmt = connection.prepareCall(maxPODate);
				pstmt.setString(1, orderNo);
				rs = pstmt.executeQuery();
				while(rs.next()){
					createdDate = rs.getTimestamp("PORECEIVEDATE");
				}
				if(null == createdDate){
					//means no po available for the order
					return false;
				}
			}
			
			GregorianCalendar orderCreationDate = new GregorianCalendar();
			orderCreationDate.setTime(new java.util.Date(createdDate.getTime()));
			if(orderCreationDate.get(GregorianCalendar.HOUR_OF_DAY) == 0){
				
				pstmt = connection.prepareCall(addedHour);
				rs = pstmt.executeQuery();
				Integer addedTime = 0;
				while(rs.next()){
					addedTime = rs.getInt("KEYVALUE");
				}
				orderCreationDate.add(GregorianCalendar.HOUR_OF_DAY, addedTime);
			}
			orderCreationDate.add(GregorianCalendar.HOUR_OF_DAY, aging);
			GregorianCalendar currentDate = new GregorianCalendar();
			if(currentDate.after(orderCreationDate)){
				flag = true;
			}
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			DbConnection.closeStatement(pstmt);
			try {
				DbConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return flag;
	}
	/*public Boolean isRolePassedEscalation(String orderNo, String role){
		if(null == orderNo || null == role)
			return false;
		Connection connection =null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		String sql = "select ESC.RM1_AGING, WORKFLOWTASK.IS_FIRST_TASK, WORKFLOWTASK.CREATED_DATE from IOE.TM_ESCLATION ESC " +
				"right outer join IOE.TM_ACCOUNT ACCOUNT on Account.CUSTOMERSEGMENT = ESC.CUSTOMER_SEGMENT_ID and " +
				"Account.CRM_REGIONID = ESC.REGION_ID inner join IOE.TPOMASTER TPOMASTER on TPOMASTER.ACCOUNTID = ACCOUNT.ACCOUNTID " +
				"inner join IOE.TPOWORKFLOWTASK WORKFLOWTASK on WORKFLOWTASK.ORDERNO = TPOMASTER.ORDERNO and " +
				"WORKFLOWTASK.OWNERTYPE_ID= ESC.ROLE_ID where TPOMASTER.ORDERNO = ? and ESC.ROLE_ID=?";
		String maxPODate = "SELECT  Max(PORECEIVEDATE) as PORECEIVEDATE FROM IOE.TPODETAILS TPODETAILS where PONUMBER = ?";
		String addedHour = "select KEYVALUE from IOE.TM_APPCONFIG where KEYNAME = 'ESCALATION_PO_DATE_CLOCK_START_HOUR'";
		Boolean flag = false;
		try{
			connection=DbConnection.getConnectionObject();
			pstmt = connection.prepareCall(sql);
			pstmt.setString(1, orderNo);
			pstmt.setString(2, role);
			rs = pstmt.executeQuery();
			Integer aging = null;
			Timestamp createdDate = null;
			Boolean isFirstsTask = null;
			while(rs.next()){
				createdDate = rs.getTimestamp("CREATED_DATE");
				aging = rs.getInt("RM1_AGING");
				isFirstsTask = rs.getBoolean("IS_FIRST_TASK");
			}
			if(null == aging){
				aging = AppConstants.defaultEscalationTime;
			}
			if(null != isFirstsTask &&  isFirstsTask){
				pstmt = connection.prepareCall(maxPODate);
				pstmt.setString(1, orderNo);
				rs = pstmt.executeQuery();
				while(rs.next()){
					createdDate = rs.getTimestamp("PORECEIVEDATE");
				}
			}
			GregorianCalendar orderCreationDate = new GregorianCalendar();
			orderCreationDate.setTime(new java.util.Date(createdDate.getTime()));
			if(orderCreationDate.get(GregorianCalendar.HOUR_OF_DAY) == 0){
				pstmt = connection.prepareCall(addedHour);
				rs = pstmt.executeQuery();
				Integer addedTime = 0;
				while(rs.next()){
					addedTime = rs.getInt("KEYVALUE");
				}
				orderCreationDate.add(GregorianCalendar.HOUR, addedTime);
			}
			orderCreationDate.add(GregorianCalendar.HOUR, aging);
			GregorianCalendar currentDate = new GregorianCalendar();
			if(currentDate.after(orderCreationDate)){
				flag = true;
			}
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			DbConnection.closeStatement(pstmt);
			try {
				DbConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return flag;
	}*/
	public List<DelayReasonDTO> getDelayReason() {
		Connection connection =null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		String sql = "select * from IOE.DELAY_REASON_MASTER";
		List<DelayReasonDTO> delayReasons = null;
		try{
			connection=DbConnection.getConnectionObject();
			pstmt = connection.prepareCall(sql);
			rs = pstmt.executeQuery();
			DelayReasonDTO delayReason = null;
			while(rs.next()){
				delayReason = new DelayReasonDTO();
				delayReason.setId(rs.getInt("ID"));
				delayReason.setDelayReason(rs.getString("DELAY_REASON_TEXT"));
				if(null == delayReasons)
					delayReasons = new ArrayList<DelayReasonDTO>();
				
				delayReasons.add(delayReason);
			}
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			DbConnection.closeStatement(pstmt);
			try {
				DbConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return delayReasons;
	}
	
	public String getPartialInitiatorRoles() {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from IOE.TM_APPCONFIG where KEYNAME = ?";
		String user = null;
		try {
			connection = DbConnection.getConnectionObject();
			pstmt = connection.prepareCall(sql);
			pstmt.setString(1, AppConstants.partialInitiatorRoles);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				user = rs.getString("KEYVALUE");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbConnection.closeStatement(pstmt);
			try {
				DbConnection.freeConnection(connection);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return user;
	}
	public String getDelayReasonUsers() {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from IOE.TM_APPCONFIG where KEYNAME = ?";
		String user = null;
		try {
			connection = DbConnection.getConnectionObject();
			pstmt = connection.prepareCall(sql);
			pstmt.setString(1, AppConstants.delayReasonUser);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				user = rs.getString("KEYVALUE");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbConnection.closeStatement(pstmt);
			try {
				DbConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return user;
	}
	//[092]
	
	public String  checkCopiedNode(long serviceProductId) throws Exception
	{
		
		String methodName="checkCopiedNode", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;
		
		Connection connection =null;
		CallableStatement callstmt =null;
		ResultSet rs = null;
		String compareResult=null;
		try {
			connection=DbConnection.getConnectionObject();
			callstmt= connection.prepareCall(sqlspCheckCopiedNode);
			callstmt.setLong(1,serviceProductId);
			callstmt.setString(2,"");
			callstmt.execute();
			compareResult=callstmt.getString(2);
			System.out.println("compare result:"+compareResult);
			
		} catch (Exception e) {
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
		}
		finally
		{
			try 
			{
				DbConnection.closeCallableStatement(callstmt);
				DbConnection.freeConnection(connection);
			} 
			catch (SQLException e) 
			{
				//e.printStackTrace();
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
			}
		}
		return compareResult;
	}
	public String sqlCheckClepOrder="{call IOE.SP_CHECK_CLEPORDER(?)}";
	/**
	 * To check whether order is clep order or not.
	 * @Date 28-Feb-2014
	 * @param orderNo
	 * @return clepOrder
	 * @author Anoop Tiwari
	 */
	public boolean isClepOrder(long orderNo){
		String methodName="isClepOrder", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;
		boolean clepOrder=false;
		Connection connection =null;
		CallableStatement callstmt =null;
		ResultSet rs = null;
		try {
			connection=DbConnection.getConnectionObject();
			callstmt= connection.prepareCall(sqlCheckClepOrder);
			callstmt.setLong(1,orderNo);
			rs=callstmt.executeQuery();
			while(rs.next()){
				String orderCreationSource=rs.getString(1);
				if(null!=orderCreationSource && orderCreationSource.trim().equalsIgnoreCase("2") ){
				clepOrder=true;
				}
			}
		}catch(SQLException e){
			e.printStackTrace();
		}catch (Exception e) {
			 e.printStackTrace();
		}finally{
			try {
				DbConnection.closeResultset(rs); 
				DbConnection.closeStatement(callstmt);
				DbConnection.freeConnection(connection);
			}catch (Exception ex){
				Utility.LOG(true, true, " Exception in " +msg+ex);
			}
		}
		return clepOrder;
	}
	public static String sqlspGetServiceListForOrderAndRoleWithPagination = "{call IOE.SP_GET_SERVICELIST_ROLEWISE_WITH_PAGINATION(?,?,?,?,?,?,?)}";
	public ArrayList<ServiceLineDTO> getServiceListForTheOrderAndRoleWithPagination(OrderHeaderDTO objDto)throws Exception
	{
		String methodName="getServiceListForTheOrderAndRoleWithPagination", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;
		Connection connection =null;
		CallableStatement callstmt =null;
		ResultSet rs = null;
		ServiceLineDTO dto;// = new NewOrderDto();
		ArrayList<ServiceLineDTO> dtoList = new ArrayList<ServiceLineDTO>();
		int recordCount = 0;
		try
		{
			connection=DbConnection.getConnectionObject();
			connection.setAutoCommit(false);
			callstmt= connection.prepareCall(sqlspGetServiceListForOrderAndRoleWithPagination);	
			callstmt.setLong(1, Long.valueOf(objDto.getOrderNo()));
			callstmt.setString(2,objDto.getSortBycolumn());
			callstmt.setString(3,objDto.getSortByOrder());
			callstmt.setInt(4,objDto.getStartIndex());
			callstmt.setInt(5,objDto.getEndIndex());
			callstmt.setInt(6, 1);
			callstmt.setLong(7, Long.valueOf(objDto.getRoleId()));
			rs = callstmt.executeQuery();
			while(rs.next())
			{
				dto = new ServiceLineDTO();
				dto.setServiceName(rs.getString("SERVICESTAGE"));
				dto.setServiceId(rs.getInt("serviceno"));
				dto.getPagingSorting().setRecordCount(rs.getInt("FULL_REC_COUNT"));	
				dto.setMaxPageNo(dto.getPagingSorting().getMaxPageNumber());
				dtoList.add(dto);
			}
		}
		catch(Exception ex )
		{
			connection.rollback();
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}
		finally
		{
			try 
			{
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(callstmt);
				DbConnection.freeConnection(connection);
			} 
			catch (SQLException e) 
			{
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			}
		}
		return dtoList;
	}
	public String sqlCheckServicePresent="{call IOE.SP_CHECK_SERVICE_PRESENT(?,?)}";
	/**
	 * CheckServicePresent
	 * @Date 28-Feb-2014
	 * @param  serviceID
	 * @param 	roleID
	 * @return servicePresent
	 * @author Anoop Tiwari
	 */
	public boolean CheckServicePresent(String serviceID,int roleID){
		String methodName="CheckServicePresent", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;
		Connection connection =null;
		CallableStatement callstmt =null;
		ResultSet rs = null;
		boolean servicePresent=false;
		try {
			connection=DbConnection.getConnectionObject();
			callstmt= connection.prepareCall(sqlCheckServicePresent);
			callstmt.setString(1,serviceID);
			callstmt.setInt(2, roleID);
			rs=callstmt.executeQuery();
			while(rs.next()){
				int count=rs.getInt("count");
				if(count>0){
					servicePresent=true;
				}
			}
		}catch(SQLException e){
			e.printStackTrace();
		}catch (Exception e) {
			 e.printStackTrace();
		}finally{
			try {
				DbConnection.closeResultset(rs); 
				DbConnection.closeStatement(callstmt);
				DbConnection.freeConnection(connection);
			}catch (Exception ex){
				Utility.LOG(true, true, " Exception in " +msg+ex);
			}
		}
		return servicePresent;
	}
	public String sqlGetRoleID="{call IOE.SP_GET_ROLE(?,?)}";
	/**
	 * get getRoleID Id
	 * @Date 20th- March-2014
	 * @param  shortcode
	 * @return roleID
	 * @author Anoop Tiwari
	 */
	public String getRoleID(String shortcode){
		String methodName="getRoleID", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;
		Connection connection =null;
		CallableStatement callstmt =null;
		String roleId="";
		try {
			connection=DbConnection.getConnectionObject();
			callstmt= connection.prepareCall(sqlGetRoleID);
			callstmt.setString(1,shortcode);
			callstmt.setInt(2,java.sql.Types.BIGINT);
			callstmt.execute();
			roleId=callstmt.getString(2);
			
		}catch(SQLException e){
			e.printStackTrace();
		}catch (Exception e) {
			 e.printStackTrace();
		}finally{
			try {
				DbConnection.closeStatement(callstmt);
				DbConnection.freeConnection(connection);
			}catch (Exception ex){
				Utility.LOG(true, true, " Exception in " +msg+ex);
			}
		}
		return roleId;
	}
	/**@author Vijay
	 * @param errorString
	 * @return SITransferDto object. This SITransferDto object will contain the user specific error that can be 
	 * used to display on GUI. This method will truncate the error with specific amount of data and then return 
	 */
	SITransferDto  setSITransferErrorsForGUIDisplay(String errorString){
		SITransferDto sITransferDto = new SITransferDto();
		if(! "".equals(errorString)){
			if(errorString.length() > 1400){
				sITransferDto.setUserSpecificError(new String(errorString.substring(0, 1400)).replaceAll(" ** ", "\n"));
			}else{
				sITransferDto.setUserSpecificError(errorString.replace(" ** ", "\n"));
			}
		}
		return sITransferDto;
	}
	
	   //Start [215]
		public static String SERVICE_FOR_VALIDATE="Update IOE.TPOSERVICEMASTER set VALIDATION_STATUS=1 where ORDERNO=? and SERVICEID=?";
		public static String ALL_SERVICE_SELECTED="Select SERVICEID from IOE.TPOSERVICEMASTER where ORDERNO=? and INITIATED_TO=?";
		
		//public static String RESET_VALIDATION_STATUS="Update IOE.TPOSERVICEMASTER set VALIDATION_STATUS=0 where ORDERNO=?";
		public String getServiceForValidate(Long orderNo,Integer[] roleIds, boolean allServicesSelected, String selectedServiceList) {
	 		String methodName="getServiceForValidate", className=this.getClass().getName(), msg="";
			boolean logToFile=true, logToConsole=true;
			Connection connection =null;
			PreparedStatement preparedStatement =null;
			PreparedStatement preparedStatementToGetBinServices=null;
			Statement statement=null;
			ResultSet rs=null;
			String str="";
			try
			{
				connection=DbConnection.getConnectionObject();
				connection.setAutoCommit(false);
				statement=connection.createStatement();
				statement.executeUpdate("Update IOE.TPOSERVICEMASTER set VALIDATION_STATUS=0 where ORDERNO="+orderNo);
				preparedStatementToGetBinServices=connection.prepareStatement(ALL_SERVICE_SELECTED);
			    preparedStatement = connection.prepareStatement(SERVICE_FOR_VALIDATE);
			 	
				if(allServicesSelected==true){
					selectedServiceList="";
					for (Integer roleId : roleIds) {
						
						preparedStatementToGetBinServices.setLong(1, orderNo);
						preparedStatementToGetBinServices.setInt(2, roleId);
					    rs = preparedStatementToGetBinServices.executeQuery();				
					    while (rs.next()) {
							if(selectedServiceList=="")
								selectedServiceList=rs.getString("SERVICEID");
							    else
							    selectedServiceList = selectedServiceList+ "," + rs.getString("SERVICEID");
			 
						 }
					    DbConnection.closeResultset(rs);
					    //rs close
					}
				}
				String[] stringArray=selectedServiceList.split(",");
				str=selectedServiceList;
	            for (String serviceId : stringArray) {
				preparedStatement.setLong(1, orderNo);
				preparedStatement.setLong(2,Long.parseLong(serviceId));
				preparedStatement.addBatch();
	            }
		        preparedStatement.executeBatch();
				connection.commit();
			}catch(SQLException e){
				Utility.LOG(true, true, " Exception in " +msg+e);
				
			}catch (Exception e) {
				Utility.LOG(true, true, " Exception in " +msg+e);
				
			}finally{
				try {
					DbConnection.closeStatement(preparedStatement);
					DbConnection.closeStatement(statement);
					DbConnection.closeStatement(preparedStatementToGetBinServices);
					DbConnection.closeResultset(rs);
			    	DbConnection.freeConnection(connection);
				}catch (Exception ex){
					Utility.LOG(true, true, " Exception in " +msg+ex);
				}
			}
		
			return str;

		}
	
		
	
//End [215]
		
		
		//Start [216] To get getOrderListForPendingWithAM for reassign to another AM
		public ArrayList<OrderForPendingWithAMDto> getOrderListForPendingWithAM(
				OrderForPendingWithAMDto dto) {
			String methodName="getOrderListForPendingWithAM", className=this.getClass().getName(), msg="";
			boolean logToFile=true, logToConsole=true;
			Connection connection =null;
			CallableStatement pstmt =null;
			ResultSet rs = null;
			int recordCount = 0;
			ArrayList<OrderForPendingWithAMDto> listOrder = new ArrayList<OrderForPendingWithAMDto>();
			try
			{
				connection=DbConnection.getConnectionObject();
				pstmt= connection.prepareCall(sqlGetOrderListPendingwithAM);
				
				
				if (dto.getCrmAccountNo()!= 0) {
					pstmt.setLong(1,dto.getCrmAccountNo());
				} else {
					pstmt.setNull(1, java.sql.Types.BIGINT);
				}	
				if (dto.getOrderNo() == null || dto.getOrderNo().trim().equals("")) {
					pstmt.setNull(2, java.sql.Types.BIGINT);
				} else {
					pstmt.setLong(2,Long.parseLong(dto.getOrderNo()));
				}
				if (dto.getAccountManagerID()!= 0) {
					pstmt.setLong(3,dto.getAccountManagerID());
				} else {
					pstmt.setNull(3, java.sql.Types.BIGINT);
				}
				
				pstmt.setInt(4,dto.getEmployeeid());
				
				PagingSorting pagingSorting = dto.getPagingSorting();
				pagingSorting.sync();// To calculate start index and Enc Index

				pstmt.setString(5, pagingSorting.getSortByColumn());// columnName
				pstmt.setString(6, PagingSorting.DB_Asc_Desc1(pagingSorting.getSortByOrder()));// sort order
				pstmt.setInt(7, pagingSorting.getStartRecordId());// start index
				pstmt.setInt(8, pagingSorting.getEndRecordId());// end index
				pstmt.setInt(9, (pagingSorting.isPagingToBeDone() ? 1 : 0));// end

				
				rs = pstmt.executeQuery();
				OrderForPendingWithAMDto returnDto = null;
				while(rs.next())
				{
					returnDto = new OrderForPendingWithAMDto();
					returnDto.setOrderNo(rs.getString("ORDERNO"));
					returnDto.setAcmgrEmail(rs.getString("ACTMGREMAILID"));
					returnDto.setAccountmgremail(rs.getString("ACTMGREMAILID"));
					returnDto.setAccountManager(rs.getString("ACTMGRID"));
					returnDto.setInitial_am(rs.getString("INITIALAM"));
					returnDto.setLast_am(Utility.fnCheckNull(rs.getString("LASTAM")));
					returnDto.setLast_changed_by(Utility.fnCheckNull(rs.getString("LAST_CHANGED_BY")));
					
					listOrder.add(returnDto);
					
					if (pagingSorting.isPagingToBeDone()) {
						recordCount = rs.getInt("FULL_REC_COUNT");
					
				}
					
			  }
				pagingSorting.setRecordCount(recordCount);
			}
			catch(Exception ex )
			{
				Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
			}
			finally
			{
				try 
				{
					DbConnection.closeResultset(rs);
					DbConnection.closeCallableStatement(pstmt);
					
					DbConnection.freeConnection(connection);
				} 
				catch (Exception e) 
				{
					// TODO Auto-generated catch block
					//e.printStackTrace();
					Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
				}
			}
			return listOrder;
		}
		//method to get the list of AM for Autosuggest
		public String reassignToAM(String nm,String attrid) throws Exception {
			String methodName="reassignToAM", className=this.getClass().getName(), msg1="";
			boolean logToFile=true, logToConsole=true;
			Connection connection =null;
			CallableStatement callstmt =null;
			com.ibm.ioes.dbhelper.SQLHelper objSql = new com.ibm.ioes.dbhelper.SQLHelper();
			String jsonVal = "";
			try
			{
				connection=DbConnection.getConnectionObject();
				List<String> labelValues = new ArrayList<String>();
				labelValues.add("ACCOUNTMANAGER_NAME");
					jsonVal = objSql.getJsonLableValueWithQuery(nm, labelValues, "EMPID", "CALL IOE.GET_ACCOUNTMANAGER_LISTALL_TOREASSIGNAM(?)" ,attrid,"",connection,"");
				
			}
			catch(Exception ex )
			{
				connection.rollback();
				Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg1, logToFile, logToConsole);//Nagarjuna
			}
			finally
			{
				try 
				{
					
					DbConnection.closeCallableStatement(callstmt);
					DbConnection.freeConnection(connection);
				} 
				catch (SQLException e) 
				{
					// TODO Auto-generated catch block
					//e.printStackTrace();
					Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg1, logToFile, logToConsole);//Nagarjuna
				}
			}
			return jsonVal;
		}
		
		public ArrayList<NewOrderDto> getAccountDetailsWithSortingforReassignAm(
				PagingDto objDto) {
			
			String methodName="getAccountDetailsWithSortingforReassignAm", className=this.getClass().getName(), msg="";
			boolean logToFile=true, logToConsole=true;
			Connection connection =null;
			CallableStatement getAllAccountsReport =null;
			ResultSet rsAccountDetails = null;
			ArrayList<NewOrderDto> listAccountDetails = new ArrayList<NewOrderDto>();
			NewOrderDto objNewOrderDto = null;
			int recordCount;
			try
			{
				connection=DbConnection.getConnectionObject();
				getAllAccountsReport= connection.prepareCall(sqlspGetAccountWithSortingforreassignam);
				String searchAcc=objDto.getAccountName();
				String accountId=objDto.getAccountIDString();
				int osp=0;
				
				if(accountId==null || accountId.equalsIgnoreCase(""))
				{
					accountId="0";
				}
				if(searchAcc=="")
				{
					searchAcc=null;
				}
				objDto.getAccountID();
				if(("").equalsIgnoreCase(searchAcc))
				{
					searchAcc=null;
				}
				if(("").equalsIgnoreCase(objDto.getShortCode()))
				{
					objDto.setShortCode(null);
				}
				if(objDto.getOsp()==null || objDto.getOsp().equalsIgnoreCase(""))
				{
					osp=0;
				}
				else
				{
					osp=Integer.parseInt(objDto.getOsp());
				}
				getAllAccountsReport.setString(1,searchAcc);
				getAllAccountsReport.setString(2,accountId );
				getAllAccountsReport.setString(3,objDto.getShortCode());
				getAllAccountsReport.setString(4,objDto.getSortBycolumn());
				getAllAccountsReport.setString(5,objDto.getSortByOrder());
				getAllAccountsReport.setInt(6,objDto.getStartIndex());
				getAllAccountsReport.setInt(7,objDto.getEndIndex());
				getAllAccountsReport.setInt(8,osp);
				getAllAccountsReport.setInt(9,objDto.getEmployeeid());
     			rsAccountDetails = getAllAccountsReport.executeQuery();
				while(rsAccountDetails.next())
				{
					objNewOrderDto =  new NewOrderDto();
					objNewOrderDto.setAccountID(rsAccountDetails.getInt("accountID"));
					objNewOrderDto.setAccountName(rsAccountDetails.getString("accountName"));
					objNewOrderDto.setAccphoneNo(rsAccountDetails.getLong("PhoneNo"));
					objNewOrderDto.setLob(rsAccountDetails.getString("LOB"));
					objNewOrderDto.setCrmAccountId(rsAccountDetails.getInt("CRMACCOUNTNO"));//Added By Ashutosh
					objNewOrderDto.setRegion(rsAccountDetails.getString("Region"));
					objNewOrderDto.setZone(rsAccountDetails.getString("ACCZONE"));
					objNewOrderDto.setM6ShortCode(rsAccountDetails.getString("M6SHORTCODE"));
					objNewOrderDto.setRegionIdNew(rsAccountDetails.getString("REGIONID"));
					recordCount=rsAccountDetails.getInt("FULL_REC_COUNT");
					objNewOrderDto.getPagingSorting().setRecordCount(recordCount);	
					objNewOrderDto.setMaxPageNo(objNewOrderDto.getPagingSorting().getMaxPageNumber());
					listAccountDetails.add(objNewOrderDto);			
				}
				return listAccountDetails;
			}
			catch(Exception ex )
			{
				//ex.printStackTrace();
				Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
			}
			finally
			{
				try 
				{
					DbConnection.closeResultset(rsAccountDetails);
					DbConnection.closeCallableStatement(getAllAccountsReport);
					DbConnection.freeConnection(connection);
				} 
				catch (Exception e) 
				{
					// TODO Auto-generated catch block
					//e.printStackTrace();
					Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
				}
			}
			return listAccountDetails;
}
		
		public String reassignAM(String reassignamList,String reassignamnameList,String orderNoList,int roleid,String lastamidList,String lastamemailList,String actmgrlist) throws SQLException
		{
			//	Nagarjuna
			String methodName="reassignPM", className=this.getClass().getName(), msg="";
			boolean logToFile=true, logToConsole=true;
			//end Nagarjuna
			Connection connection =null;
			ArrayList<String> reassignam = new ArrayList<String>();
			ArrayList<String> reassignam_emailid = new ArrayList<String>();
			ArrayList<String> order = new ArrayList<String>();
			ArrayList<String> actmgremailid = new ArrayList<String>();
			ArrayList<String> lastamemailid = new ArrayList<String>();
			ArrayList<String> lastamid = new ArrayList<String>();
			ArrayList<String> reassignamname = new ArrayList<String>();
			PreparedStatement psTpoMaster = null;
			PreparedStatement psTpoWorkFlow = null;
			Statement stageStatment=null;
			ResultSet rs=null;
			String output ="";
			String output_insert="";
			int isMailSend = 0;
			NewOrderDao objDao = new NewOrderDao();
			PreparedStatement ps=null;
			IB2BMailDto objdto=new IB2BMailDto();
			int k=0;
			int arrLen=0;
			try
			{
				connection=DbConnection.getConnectionObject();
				connection.setAutoCommit(false);
				 // 013 start
				
				// to get reassign manager ids , emailid 
				arrLen=reassignamList.split(",").length;
				String[] eachLine=reassignamList.split(",");
				for(int i=1;i<=arrLen;i++)
				{	 
					String str=eachLine[i-1];
					String individual[]=str.split(":");
					reassignam.add(individual[0]);
					reassignam_emailid.add(individual[1]);
				}
				
				// to get orderno
				StringTokenizer st_order  = new StringTokenizer( orderNoList, ",");
				for (int i =0; st_order.hasMoreTokens();i++) 
				{
					order.add(st_order.nextToken());
				}
				
				StringTokenizer st_actmgr  = new StringTokenizer( actmgrlist, ",");
				for (int i =0; st_actmgr.hasMoreTokens();i++) 
				{
					actmgremailid.add(st_actmgr.nextToken());
				}
				
				StringTokenizer st_lastamemailid  = new StringTokenizer( lastamemailList, ",");
				for (int i =0; st_lastamemailid.hasMoreTokens();i++) 
				{
					lastamemailid.add(st_lastamemailid.nextToken());
				}
				
				StringTokenizer st_lastamid  = new StringTokenizer( lastamidList, ",");
				for (int i =0; st_lastamid.hasMoreTokens();i++) 
				{
					lastamid.add(st_lastamid.nextToken());
				}
				
					
				StringTokenizer st_reassignamname  = new StringTokenizer( reassignamnameList, ",");
				for (int i =0; st_reassignamname.hasMoreTokens();i++) 
				{
					reassignamname.add(st_reassignamname.nextToken());
				}
				
				
			    psTpoMaster = connection.prepareStatement("UPDATE IOE.TPOMASTER SET CREATEDBY = ?, EMPLOYEEID=? WHERE ORDERNO=?");
			    psTpoWorkFlow = connection.prepareStatement("UPDATE IOE.TPOWORKFLOWTASK SET TASK_ASSIGNED_TO =?, LAST_ASSGINED_BY=? WHERE ORDERNO=? AND OWNERTYPE_ID = 1");
			    stageStatment = connection.createStatement();
			    rs=stageStatment.executeQuery("SELECT ORDERNO, STAGE FROM IOE.TPOMASTER WHERE STAGE IN ('New','AM') AND ORDERNO IN (" + orderNoList+")");
			    HashMap<Integer, String> ordersWithPendingAM=new HashMap<Integer, String>();
		    	while(rs.next()){
		    		
		    		ordersWithPendingAM.put(rs.getInt("ORDERNO"), rs.getString("STAGE"));
		    		    		
		    	}
		    	
		    	if(ordersWithPendingAM.size()==order.size()){
				    for(int count=0; count<order.size();count++)
				    {
				    	psTpoMaster.setLong(1, Long.parseLong((reassignam.get(count))));
				    	psTpoMaster.setLong(2, Long.parseLong((reassignam.get(count))));
				    	psTpoMaster.setLong(3, Long.parseLong((order.get(count))));
				    	psTpoMaster.addBatch();
				       	if(ordersWithPendingAM.get(Integer.parseInt(order.get(count))).equals("AM")){
				       		psTpoWorkFlow.setLong(1, Long.parseLong((reassignam.get(count))));
				       		psTpoWorkFlow.setLong(2, roleid);
				       		psTpoWorkFlow.setLong(3,Long.parseLong((order.get(count))));
					        psTpoWorkFlow.addBatch();
					    
				    	}
				    	
				    }
				
				    psTpoWorkFlow.executeBatch();
				   
				    int[] recordsAffected = psTpoMaster.executeBatch();
				    for(int index = 0 ; index < recordsAffected.length; index++)
				    {
				    	if(recordsAffected[index] == 0 || recordsAffected[index]==PreparedStatement.EXECUTE_FAILED)
				    	{
				    		if(output.equals(""))
						    	output= output+ order.get(index) ;
				    		else
				    			output= output+ ","+ order.get(index) ;
				    	}
				    		
				    }
				    
					   if(output.equals(""))
					   {
						  
						   String query="INSERT INTO IOE.TREASSIGN_AM_HISTORY" +
							"(ORDERNO,LAST_AM,REASSIGNED_AM,REASSIGN_DATE,REASSIGNED_BY) " +
							"values(?,?,?,?,?)";
						   ps = connection.prepareStatement(query);
						   for(int count=0; count<order.size();count++)
						    {
							   	ps.setLong(1,Long.parseLong((order.get(count))));
						    	ps.setLong(2,Long.parseLong(lastamid.get(count)));
							    ps.setLong(3,Long.parseLong((reassignam.get(count))));
							    java.util.Date date= new java.util.Date();
							    ps.setTimestamp(4,new Timestamp(date.getTime()));
							    ps.setLong(5,roleid);
						    	ps.addBatch();
						    	k++;
							   
						    }
						    int[] updateCounts = ps.executeBatch();
						    for(int index = 0 ; index < updateCounts.length; index++)
						    {
						    	if(updateCounts[index] == 0 || updateCounts[index]==PreparedStatement.EXECUTE_FAILED)
						    	{
						    		if(output_insert.equals(""))
						    			output_insert= output_insert+ order.get(index) ;
						    		else
						    			output_insert= output_insert+ ","+ order.get(index) ;
						    	}
						    		
						    }
						    if(output_insert.equals(""))
						    {
								connection.commit();
								output="AM Update Successfully";					
						    	 /*if(Utility.getAppConfigValue("IS_SEND_MAIL_TO_REASSIGNAM").equalsIgnoreCase("Y"))
									{
						    		 	Utility.LOG("--Sending mail to re-assign pm start--->");
						    		 	//objdto.setMailTemplateType(AppConstants.REASSIGNAMTEMPLATE);
						    		 	for(int count=0; count<order.size();count++)
									    {
						    		 		objdto.setTo(new String[]{actmgremailid.get(count),reassignam_emailid.get(count),lastamemailid.get(count)});
						    		 		objdto.setOrderNo(order.get(count));
						    		 		objdto.setReassigned_pm(reassignamname.get(count));
											isMailSend = IB2BMail.sendiB2BMail(objdto, connection,true,true);
											if(isMailSend==1)
											{
												String success= "Reassign PM Mail Sent Successfully for OrderNumber: "+ order.get(count);
												com.ibm.ioes.utilities.Utility.LOG(success);
											}
										    else
										    {
												String failure= " Reassign PM Mail Sending Failed  for OrderNumber: " + order.get(count);
												com.ibm.ioes.utilities.Utility.LOG(failure);
											}
						    			}	
									}*/
							} 
							else 
							{
								   output= "Update of " + output_insert + "failed. Retry";
								   connection.rollback();
							}
						    
					   }
					   else
					   {
						   output= "Update of " + output + "failed. Retry";
						   connection.rollback();
					   }
				 }
		    	 else{
		    		 for(int count = 0 ; count < order.size(); count++)
					    {
					    	if(ordersWithPendingAM.get(Integer.parseInt(order.get(count)))==null)
					    	{
					    		if(output.equals(""))
							    	output= output+ order.get(count) ;
					    		else
					    			output= output+ ","+ order.get(count) ;
					    	}
					    		
					    }
		    		 
		    		output= "Following Orders are not in New or AM stage: " + output + ". Please unselect them to reassign again.";
				    connection.rollback();
		    		
		    	}
			// 013 end
			}
			catch(Exception ex )
			{
				//ex.printStackTrace();
				output="Update failed. Please try agian!";
				connection.rollback();
				Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,msg, logToFile, logToConsole);//nagarjuna
			}
			finally
			{
				try 
				{
					DbConnection.closePreparedStatement(psTpoMaster);
					DbConnection.closePreparedStatement(psTpoWorkFlow);
					DbConnection.closeStatement(stageStatment);
					DbConnection.closeResultset(rs);
					DbConnection.closePreparedStatement(ps);
					DbConnection.freeConnection(connection);
				} 
				catch (Exception e) 
				{
					// TODO Auto-generated catch block
					//e.printStackTrace();
					connection.rollback();
					Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,msg, logToFile, logToConsole);//nagarjuna
				}
			}
			return output;
		}
		
//End [216]	

		/**
		 * \
		 * @param objDto
		 * @param conn
		 * @return 1 for success , -1 for exception or failure so that calling function need to rollback on this flag
		 * @throws Exception
		 */
		public static String insertNewStandardReason(StandardReason objDto, Connection conn) throws Exception{
			String status="-1";
			PreparedStatement pstmt=null;
			String sqlQuery="INSERT INTO IOE.TSTANDARDREASON(STANDARDREASONID, REASONNAME, ISACTIVE, INTERFACEID,IN_FX_EXT_ACC_CODE,CREATEDATE) VALUES(?,?,?,?,?,current_timestamp)";
			try{
				
				pstmt=conn.prepareStatement(sqlQuery);
				pstmt.setLong(1, objDto.getStdReasonId());
				pstmt.setString(2, objDto.getStdReasonName());
				pstmt.setInt(3, objDto.getStdReasonStatus());
				pstmt.setInt(4, objDto.getInterfaceType());
				pstmt.setString(5, String.valueOf(objDto.getStdReasonId()));
				pstmt.executeUpdate();
				status="1"; 
				
			}finally{
				DbConnection.closePreparedStatement(pstmt);
			}
			return status;
		}
		
		public static StandardReason getStandardReasonByName(
				String stdReasonName,int interfaceType, Connection conn) throws Exception {
			StandardReason stdObj=null;
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			String sqlQuery="SELECT STANDARDREASONID,REASONNAME FROM IOE.TSTANDARDREASON WHERE UPPER(TRIM(REASONNAME))=? AND INTERFACEID=?";
			try{
				pstmt=conn.prepareStatement(sqlQuery);
				pstmt.setString(1, stdReasonName.toUpperCase());
				pstmt.setInt(2, interfaceType);
				rs=pstmt.executeQuery();
				while(rs.next()){
					stdObj=new StandardReason();
					stdObj.setStdReasonId(rs.getLong("STANDARDREASONID"));
					stdObj.setStdReasonName(rs.getString("REASONNAME"));					
				}
				
			}catch(Exception e){
				throw e;
			}finally{
				DbConnection.closeResultset(rs);
				DbConnection.closePreparedStatement(pstmt);
			}
			return stdObj;
		}
		public static Long getNextStandardReasonId(Connection conn) throws Exception {
			Long stdReasonId=null;
			String sqlQuery="SELECT (IOE.SEQ_STDREASONID.NEXTVAL) AS STDREASONID FROM sysibm.SYSDUMMY1";
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			try{
				pstmt=conn.prepareStatement(sqlQuery);
				rs=pstmt.executeQuery();
				while(rs.next()){
					stdReasonId=rs.getLong("STDREASONID");
				}
			}catch(Exception e){
				throw e;
			}finally{
				DbConnection.closeResultset(rs);
				DbConnection.closePreparedStatement(pstmt);
			}
			return stdReasonId;
		}
		public static ArrayList<StandardReason> fetchStandardReason(
				StandardReason objDto) throws Exception {
			ArrayList<StandardReason> listStandardReason=new ArrayList<StandardReason>();
			Connection conn=null;
			CallableStatement cstmt=null;
			ResultSet rs=null;
			StandardReason stdObj=null;
			int recordCount;
			try{
				conn=DbConnection.getConnectionObject();
				cstmt=conn.prepareCall(sqlGetStandardReasonDetails);
				cstmt.setString(1, objDto.getPagingDto().getSortBycolumn());
				cstmt.setString(2, objDto.getPagingDto().getSortByOrder());
				cstmt.setInt(3, objDto.getPagingDto().getStartIndex());
				cstmt.setInt(4, objDto.getPagingDto().getEndIndex());
				rs=cstmt.executeQuery();
				
				while(rs.next()){
					stdObj=new StandardReason();
					
					stdObj.setStdReasonId(rs.getLong("STANDARDREASONID"));
					stdObj.setStdReasonName(rs.getString("REASONNAME"));
					stdObj.setStdReasonStatus(rs.getInt("ISACTIVE"));
					stdObj.setInterfaceType(rs.getInt("INTERFACEID"));
					
					recordCount=rs.getInt("FULL_REC_COUNT");					
					stdObj.getPagingDto().setRecordCount(recordCount);
					stdObj.setMaxPageNo(stdObj.getPagingDto().getMaxPageNumber());
					
					listStandardReason.add(stdObj);
				}
				
			}catch(Exception e){
				throw e;
			}finally{				
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(cstmt);
				DbConnection.freeConnection(conn);
			}
			return listStandardReason;
		}
		
		public static int editStandardReason(String strStdReasonIds,String strStdReasonStatus) throws Exception {
			int status=-1;
			Connection conn=null;
			PreparedStatement pstmt=null;		
			String sqlQuery="UPDATE IOE.TSTANDARDREASON SET ISACTIVE =? WHERE STANDARDREASONID=?";
			int batchCount=0;
			try{
				String[] reasonIds=strStdReasonIds.split(",");
				String[] stdStatus=strStdReasonStatus.split(",");				
				conn=DbConnection.getConnectionObject();				
				pstmt=conn.prepareStatement(sqlQuery);
				for(int i=0;i<reasonIds.length;i++){
					Long reasonid=Long.valueOf(reasonIds[i]);
					Integer stdstatus= Integer.valueOf(stdStatus[i]);
					pstmt.setInt(1, stdstatus);
					pstmt.setLong(2, reasonid);	
					pstmt.addBatch();
					batchCount++;									
				}
				if(batchCount > 0){
					boolean isSuccess=true;
					int[] results=pstmt.executeBatch();
					for(int i:results){
						if(i==PreparedStatement.EXECUTE_FAILED){
							conn.rollback();
							status=-1;
							throw new Exception(" Exception occurred during executing editStandardReason Batch::");
						}
					}
					conn.commit();
					status=1;
				}
			}catch(Exception e){
				throw e;
			}finally{
				DbConnection.closePreparedStatement(pstmt);
				DbConnection.freeConnection(conn);
			}
			return status;
		}
		//End [217]
		
		public ArrayList<NewOrderDto> getDispatchDetailsWithSorting(PagingDto objDto) 
		{
			
			String methodName="getDispatchDetailsWithSorting", className=this.getClass().getName(), msg="";
			boolean logToFile=true, logToConsole=true;
			Connection connection =null;
			CallableStatement getDispatchReport =null;
			ResultSet rsDispatchDetails = null;
			ArrayList<NewOrderDto> listDispatchDetails = new ArrayList<NewOrderDto>();
			NewOrderDto objNewOrderDto = null;
			int recordCount;
			try
			{
				connection=DbConnection.getConnectionObject();
				getDispatchReport= connection.prepareCall(sqlGetDispatchWithSorting);
				
				String dispatchId=String.valueOf(objDto.getDispatchAddressID());
				
				if(dispatchId==null || dispatchId.equalsIgnoreCase(""))
				{
					dispatchId="0";
				}
														
				getDispatchReport.setInt(1,objDto.getAccountID());				
				getDispatchReport.setInt(2,objDto.getDispatchAddressID());		
				getDispatchReport.setString(3,objDto.getSortBycolumn());
				getDispatchReport.setString(4,objDto.getSortByOrder());
				getDispatchReport.setInt(5,objDto.getStartIndex());
				getDispatchReport.setInt(6,objDto.getEndIndex());
				rsDispatchDetails = getDispatchReport.executeQuery();
				while(rsDispatchDetails.next())
				{
					objNewOrderDto =  new NewOrderDto();
					objNewOrderDto.setAccountID(rsDispatchDetails.getInt("ACCOUNTID"));
					objNewOrderDto.setDispatchAddressID(rsDispatchDetails.getInt("DISPATCH_ADDRESS_CODE"));
					objNewOrderDto.setDispatchAddressName(rsDispatchDetails.getString("FNAME") +" "+rsDispatchDetails.getString("LNAME"));					
					objNewOrderDto.setDispatchAddress1(rsDispatchDetails.getString("ADDRESS1"));
					objNewOrderDto.setDispatchAddress2(rsDispatchDetails.getString("ADDRESS2"));
					objNewOrderDto.setDispatchAddress3(rsDispatchDetails.getString("ADDRESS3"));
					objNewOrderDto.setDispatchAddress4(rsDispatchDetails.getString("ADDRESS4"));
					objNewOrderDto.setDispatchPhoneNo(rsDispatchDetails.getString("TELEPHONENO"));
					objNewOrderDto.setDispatchEmail(rsDispatchDetails.getString("EMAILID"));
					objNewOrderDto.setDispatchPinNo(rsDispatchDetails.getString("PINCODE"));
					objNewOrderDto.setDispatchCityName(rsDispatchDetails.getString("CITYNAME"));
					objNewOrderDto.setDispatchStateName(rsDispatchDetails.getString("STATE"));
					objNewOrderDto.setDispatchCountyName(rsDispatchDetails.getString("COUNTRY"));
					objNewOrderDto.setDispatchDesignation(rsDispatchDetails.getString("DESIGNATION"));
					objNewOrderDto.setDispatchLSTNo(rsDispatchDetails.getString("LSTNO"));
					recordCount=rsDispatchDetails.getInt("FULL_REC_COUNT");
					objNewOrderDto.getPagingSorting().setRecordCount(recordCount);	
					objNewOrderDto.setMaxPageNo(objNewOrderDto.getPagingSorting().getMaxPageNumber());
					listDispatchDetails.add(objNewOrderDto);			
				}				
			}
			catch(Exception ex )
			{
				Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);
			}
			finally
			{
				try 
				{
					DbConnection.closeResultset(rsDispatchDetails);
					DbConnection.closeCallableStatement(getDispatchReport);
					DbConnection.freeConnection(connection);
				} 
				catch (Exception e) 
				{
					// TODO Auto-generated catch block
					Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);
				}
			}
			return listDispatchDetails;
		}
		public static int loggTotalTimeTaken(int orderno,java.util.Date start_time,int total_elapsed_time,String actiontype) throws Exception{
			
			Utility.LOG("Start Recording Time Taken for Validation and Publish Activity.");
			Connection conn=null;
			PreparedStatement pstmt=null;
			String sqlQuery="INSERT INTO IOE.TRN_REPORT_USAGE_DETAILS(USERID,INTERFACEID,ACTIONTYPE,ACTIONTIMESTAMP) VALUES(?,?,?,?)";
			int status=-1;
			try{
				if("ON".equalsIgnoreCase(ApplicationFlags.IB2B_LOGGING_TIME_RECORD_VALIDATE_PUBLISH)){
					conn=DbConnection.getConnectionObject();
					pstmt=conn.prepareStatement(sqlQuery);
					pstmt.setString(1, String.valueOf(total_elapsed_time));
					pstmt.setLong(2, orderno);
					pstmt.setString(3, actiontype);
					pstmt.setTimestamp(4, new Timestamp(start_time.getTime()));
					pstmt.execute();
					status=1;
				}else{
					System.out.println("FLAG is OFF");
				}
				
			}finally{
				DbConnection.closePreparedStatement(pstmt);
				DbConnection.freeConnection(conn);
			}
			return status;
		}
		public int performPurging() throws Exception {

			//Nagarjuna
			String methodName="performPurging", className=this.getClass().getName(), msg="";
			boolean logToFile=true, logToConsole=true;
			//end Nagarjuna
			CallableStatement cs = null;
			Connection connection =null;
			ResultSet rs = null;
			int success;
			try
			{
				connection=DbConnection.getConnectionObject();
				cs = connection.prepareCall(sqlPurgingData);	
				cs.executeUpdate();
				success=1;
			}
			catch(Exception ex )
			{
				success=0;
				//ex.printStackTrace();	
				Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			}
			finally
			{
				cs.close();
				DbConnection.freeConnection(connection);
				
			}
			return success; 

		} 
		
			public static String  getNextPartnerIdfromSequence="SELECT NEXTVAL FOR IOE.PARTNER_SEQ AS NEXT_ID FROM SYSIBM.SYSDUMMY1";
		
		private static String sqlGetChannelPartnerDetails = "{call IOE.SP_VIEW_CHANNELPARTNER(?,?,?,?,?,?,?)}";
					
		public static Long getNextChannelPartnerId(Connection conn) throws Exception {
			Long partnerId=null;
			String sqlQuery="SELECT (IOE.SEQ_CHANNELPARTNERID.NEXTVAL) AS partnerId FROM sysibm.SYSDUMMY1";
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			try{
				pstmt=conn.prepareStatement(sqlQuery);
				rs=pstmt.executeQuery();
				while(rs.next()){
					partnerId=rs.getLong("partnerId");
				}
			}catch(Exception e){
				throw e;
			}finally{
				DbConnection.closeResultset(rs);
				DbConnection.closePreparedStatement(pstmt);
			}
			return partnerId;
		}
		
		
		public static ArrayList<NewOrderBean> fetchChannelPartner(
				Long respId, int startIndex,int endIndex,int pagingRequired,int pageRecords, String feId, String partnerName) throws Exception {
			ArrayList<NewOrderBean> listChannelPartner=new ArrayList<NewOrderBean>();
			Connection conn=null;
			CallableStatement cstmt=null;
			ResultSet rs=null;
			NewOrderBean chPartnerObj=null;
			int recordCount;
			try{
				conn=DbConnection.getConnectionObject();
				cstmt=conn.prepareCall(sqlGetChannelPartnerDetails);
				cstmt.setString(1, "PARTNER_NAME");
				cstmt.setString(2, "ASC");
				cstmt.setInt(3, startIndex);
				cstmt.setInt(4, endIndex);
				cstmt.setString(5, feId.trim().toUpperCase());
				cstmt.setString(6, partnerName.trim().toUpperCase());
				cstmt.setLong(7, respId);
				rs=cstmt.executeQuery();
				
				while(rs.next()){
					chPartnerObj=new NewOrderBean();
					
					chPartnerObj.setChannelPartnerCtgry(Long.valueOf(rs.getString("PARTNER_ID")));
					chPartnerObj.setChannelPartnerCtgryName(rs.getString("PARTNER_NAME"));
					chPartnerObj.setStatus(rs.getString("IS_ACTIVE"));
					chPartnerObj.setChannelPartnerCode(rs.getString("PARTNER_CODE"));
					//chPartnerObj.setCustSegment(rs.getString("CUST_SEGMENT_CODE"));
					//chPartnerObj.setCustSINo(rs.getString("CUST_SEGMENT_ID"));
					chPartnerObj.setCurrencyID(rs.getString("USER_ID"));
					
					recordCount=rs.getInt("FULL_REC_COUNT");					
					chPartnerObj.getPagingSorting().setRecordCount(recordCount);
					chPartnerObj.setMaxPageNo(chPartnerObj.getPagingSorting().getMaxPageNumber());
					//objDto.getPagingSorting().setRecordCount(recordCount);
					//objDto.setMaxPageNo(objDto.getPagingSorting().getMaxPageNumber()/*/25*/);
					
					listChannelPartner.add(chPartnerObj);
				}
				
			}catch(Exception e){
				throw e;
			}finally{				
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(cstmt);
				DbConnection.freeConnection(conn);
			}
			return listChannelPartner;
		}
		
		public static String insertNewChannelPartner(NewOrderBean objDto) throws Exception{
			Connection conn=null;
			String status="-1";
			PreparedStatement pstmt=null;
			String sqlQuery="INSERT INTO IOE.TM_CHANNEL_PARTNER(PARTNER_ID,PARTNER_NAME, CREATED_DATE, IS_ACTIVE, PARTNER_CODE) VALUES(?,?,current_timestamp,?,?)";
			try{
				conn=DbConnection.getConnectionObject();
				pstmt=conn.prepareStatement(sqlQuery);
				//fetch next value of partner id from database sequence
				Long partnerId=NewOrderDaoExt.getNextChannelPartnerId(conn);
				//insert new channel partner
				//objDto.setChannelPartnerId(Integer.valueOf(""+partnerId));
				pstmt.setInt(1, Integer.valueOf(""+partnerId));
				pstmt.setString(2, objDto.getChannelPartnerCtgryName());
				if(objDto.getIsActive().equals("on")){
					pstmt.setInt(3, 1);	
				}else if(objDto.getIsActive().equals("off")){
					pstmt.setInt(3, 0);
				}
				pstmt.setString(4, objDto.getChannelPartnerCode());
				
				pstmt.executeUpdate();
				status="1"; 
				
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				DbConnection.closePreparedStatement(pstmt);
				DbConnection.freeConnection(conn);
			}
			return status;
		}
		
		public static String updateChannelPartner(NewOrderBean objDto) throws Exception{
			Connection conn=null;
			String status="-1";
			PreparedStatement pstmt=null;
			String sqlQuery="update ioe.tm_channel_partner set PARTNER_NAME=?,IS_ACTIVE=?,PARTNER_CODE=? where PARTNER_ID=?";
			try{
				conn=DbConnection.getConnectionObject();
				pstmt=conn.prepareStatement(sqlQuery);
				pstmt.setString(1, objDto.getChannelPartnerCtgryName());
				if(objDto.getIsActive().equals("on")){
					pstmt.setInt(2, 1);	
				}else if(objDto.getIsActive().equals("off")){
					pstmt.setInt(2, 0);
				}
				pstmt.setString(3, objDto.getChannelPartnerCode());
				pstmt.setLong(4, Long.valueOf(objDto.getChannelPartnerCtgry()));
				pstmt.executeUpdate();
				status="2"; 
				
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				DbConnection.closePreparedStatement(pstmt);
				DbConnection.freeConnection(conn);
				
			}
			return status;
        }
		public static String addChannelPartner(ChannelPartnerDto channelPartnerDto/*String channelPartnerCtgryName,
				String channelPartnerCode, int changeTypeId, int status,
				ArrayList<FieldEnginnerDto> addList*/) throws Exception {
					 Connection conn=null;
					 ResultSet rs = null;
					// ResultSet rs = null;
					 PreparedStatement psGetId = null,insertPstmtforFE=null,insertPstmt = null,updateforInactiveallFe=null,insertintoPartnerCustMap=null;
					 long nextId = 0;
					 try{
						 String channelPartnerCtgryName=channelPartnerDto.getChannelPartnerName();
						 String channelPartnerCode=channelPartnerDto.getChannelpartnerCode();
						// int changeTypeId=channelPartnerDto.getCust_Segment_Id();
						 int status=channelPartnerDto.getPartner_Is_Active();
						 Long empId=channelPartnerDto.getEmpId();
						 ArrayList<FieldEnginnerDto> addList=channelPartnerDto.getAddList();
						 //ArrayList<FieldEnginnerDto> editList=channelPartnerDto.getEditList();
						 
						 //crrate pstmt
						 conn=DbConnection.getConnectionObject();
						 conn.setAutoCommit(false);
						 psGetId = conn.prepareCall(getNextPartnerIdfromSequence);
						 insertPstmt = conn.prepareCall("INSERT INTO ioe.TM_CHANNEL_PARTNER(PARTNER_ID,PARTNER_NAME,CREATED_DATE,IS_ACTIVE,PARTNER_CODE,LAST_UPDATED_BY) VALUES(?,?,current_timestamp,?,?,?)");
						 insertintoPartnerCustMap = conn.prepareCall("INSERT INTO ioe.TM_PARTNER_CUSTSEG_MAPPING(PARTNER_ID,CUST_SEGMENT_ID) VALUES(?,?)");
						 insertPstmtforFE = conn.prepareCall("INSERT INTO ioe.TM_FIELD_ENG_MAPPING(PARTNER_ID,FIELD_ENGINEER,IS_ACTIVE) VALUES(?,?,?)");
						 	rs = psGetId.executeQuery();
							if (rs.next()) {
								nextId = rs.getLong("NEXT_ID");
							}
							DbConnection.closeResultset(rs);
							
						 for(int count=0;count< channelPartnerDto.getCust_Segment_Id().size();count++){
							 insertintoPartnerCustMap.setLong(1,nextId);
							 insertintoPartnerCustMap.setLong(2, Long.valueOf(""+channelPartnerDto.getCust_Segment_Id().get(count)));
							 insertintoPartnerCustMap.addBatch();
						 }
						 
						 for(int count=0;count <addList.size();count++)	{
							 
						 }
							
						 insertPstmt.setLong(1,nextId);
						 insertPstmt.setString(2,channelPartnerCtgryName);
						 insertPstmt.setInt(3,status);
						 insertPstmt.setString(4,channelPartnerCode);
						 // insertPstmt.setInt(5,changeTypeId);
						 insertPstmt.setLong(5,empId);
						 //insertPstmt.executeUpdate();
						 if(rs!=null && nextId!= 0){
							 for(int count = 0;count <addList.size();count++) {
								 //addlist.get(count);
								 insertPstmtforFE.setLong(1,nextId);
								 insertPstmtforFE.setString(2,""+addList.get(count));
								 insertPstmtforFE.setInt(3,1);
								 insertPstmtforFE.addBatch();
							 }	
						 }
						 if(status==0 && addList.size()>0){
							 updateforInactiveallFe = conn.prepareCall("UPDATE ioe.TM_FIELD_ENG_MAPPING SET IS_ACTIVE=0 WHERE PARTNER_ID=?");
							 updateforInactiveallFe.setLong(1, nextId);
						 }
						 insertintoPartnerCustMap.executeBatch();
						 insertPstmtforFE.executeBatch();
						 insertPstmt.executeUpdate();
						 if(updateforInactiveallFe!=null)
							 updateforInactiveallFe.executeUpdate();
						 //insertPstmt.executeBatch();
						 conn.commit();
					 }catch(Exception e){
							Utility.LOG(true, true,"Some Error Occured in addChannelPartner method of NewOrderDaoExt "+e);
							conn.rollback();
							return "otherFailure";
						}finally{
							DbConnection.closeResultset(rs);
							DbConnection.closePreparedStatement(insertintoPartnerCustMap);
							DbConnection.closePreparedStatement(psGetId);
							DbConnection.closePreparedStatement(insertPstmt);
							DbConnection.closePreparedStatement(insertPstmtforFE);
							DbConnection.closePreparedStatement(updateforInactiveallFe);
							DbConnection.freeConnection(conn);
						}
					return "success";
		}
		
		public static String updateChannelPartner(ChannelPartnerDto channelPartnerDto) throws Exception {
			 
			 Connection conn=null;
			 ResultSet rs = null;
			 PreparedStatement psUpdatePartner = null,psUpdateByFeId=null,psInsertFeId=null,updateforInactiveallFe=null,psUpdatePartnerforPartnerCodeNull=null;
			 Integer runPsUpdateByFeId=0;
			 Integer runPsInsertFeId=0;
			 //.getChannelPartnerName(),,,objDto.,editList
			try{
				 conn= DbConnection.getConnectionObject();
				 
				 String channelPartnerCtgryName=channelPartnerDto.getChannelPartnerName();
				 String channelPartnerCode=channelPartnerDto.getChannelpartnerCode();
				 Long channelPartnerId=channelPartnerDto.getChannelPartnerId();
				 int partnerIsActive=channelPartnerDto.getPartner_Is_Active();
				 ArrayList<FieldEnginnerDto> editList=channelPartnerDto.getEditList();
				 Long empId=channelPartnerDto.getEmpId();
				 conn.setAutoCommit(false);
				 psUpdateByFeId = conn.prepareStatement("UPDATE ioe.TM_FIELD_ENG_MAPPING SET FIELD_ENGINEER=?,IS_ACTIVE=? WHERE FIELD_ENGINEER_ID=?");
				 psInsertFeId = conn.prepareStatement("INSERT INTO ioe.TM_FIELD_ENG_MAPPING(PARTNER_ID,FIELD_ENGINEER,IS_ACTIVE) VALUES(?,?,?)");
				 
				 for(int count = 0;count <editList.size();count++) {
					 //addlist.get(count);
					 if(editList.get(count).getSe_Id()!=null && ""+editList.get(count).getSe_Id()!=""){// FE id already added
						 psUpdateByFeId.setString(1, editList.get(count).getFieldEngineer());
						 psUpdateByFeId.setInt(2, editList.get(count).getField_Engineer_Is_Active());
						 psUpdateByFeId.setLong(3, editList.get(count).getSe_Id());
						 psUpdateByFeId.addBatch();
						 runPsUpdateByFeId=1;	
					 }else{
						 psInsertFeId.setLong(1, channelPartnerId);
						 psInsertFeId.setString(2, editList.get(count).getFieldEngineer());
						 psInsertFeId.setInt(3, editList.get(count).getField_Engineer_Is_Active());
						 psInsertFeId.addBatch();
						 runPsInsertFeId=1;
					 }
				 }
				 psUpdatePartner = conn.prepareCall("UPDATE ioe.TM_CHANNEL_PARTNER SET PARTNER_NAME=?,LAST_UPDATED_BY=?,PARTNER_CODE=?,IS_ACTIVE=? WHERE PARTNER_ID=?");
				 psUpdatePartnerforPartnerCodeNull = conn.prepareCall("UPDATE ioe.TM_CHANNEL_PARTNER SET PARTNER_NAME=?,LAST_UPDATED_BY=?,IS_ACTIVE=? WHERE PARTNER_ID=?");	

				 if(channelPartnerCode.equals("")){
					 psUpdatePartnerforPartnerCodeNull.setString(1,channelPartnerCtgryName);
					 psUpdatePartnerforPartnerCodeNull.setLong(2,empId);
					 psUpdatePartnerforPartnerCodeNull.setInt(3,partnerIsActive);
					 psUpdatePartnerforPartnerCodeNull.setLong(4,channelPartnerId);
					 
				 }else{
					 
					 psUpdatePartner.setString(1,channelPartnerCtgryName);
					 psUpdatePartner.setLong(2,empId);
					 psUpdatePartner.setString(3,channelPartnerCode);
					 psUpdatePartner.setInt(4,partnerIsActive);
					 psUpdatePartner.setLong(5,channelPartnerId);
					
				 }
				 if(partnerIsActive==0 && editList.size()>0){
					 updateforInactiveallFe = conn.prepareCall("UPDATE ioe.TM_FIELD_ENG_MAPPING SET IS_ACTIVE=0 WHERE PARTNER_ID=?");
					 updateforInactiveallFe.setLong(1, channelPartnerId);
				 }
					 
					 if(editList.size()>0){
						 if(runPsUpdateByFeId==1){
							 psUpdateByFeId.executeBatch();
						 }
						 if(runPsInsertFeId==1){
							 psInsertFeId.executeBatch();
						 }
					 }
					 if(channelPartnerCode.equals("")){
						 psUpdatePartnerforPartnerCodeNull.executeUpdate();
					 }else{
						 psUpdatePartner.executeUpdate();
					 }
					 if(partnerIsActive==0 && editList.size()>0){
						 updateforInactiveallFe.executeUpdate();
					 }
					 conn.commit();
				 
			}/*catch(BatchUpdateException batchException){
				batchException.getNextException();	
			}*/catch(Exception e){
				conn.rollback();
				Utility.LOG(true, true,"Some Error Occured in addChannelPartner method of NewOrderDaoExt "+e);
				return "otherFailure";
			}finally{
				DbConnection.closeResultset(rs);
				DbConnection.closePreparedStatement(psUpdatePartner);
				DbConnection.closePreparedStatement(psUpdateByFeId);
				DbConnection.closePreparedStatement(psInsertFeId);
				DbConnection.closePreparedStatement(updateforInactiveallFe);
				DbConnection.closePreparedStatement(psUpdatePartnerforPartnerCodeNull);
				DbConnection.freeConnection(conn);
			}
			return "success";
		}
		/*public ArrayList<NewOrderDto> getProductAndId() {
			// TODO Auto-generated method stub
			return null;
		}*/
		
		public ArrayList<NewOrderDto> getProductAndId() 
		{
			Connection connection =null;
			CallableStatement csProductDetails =null;
			ResultSet rsProductDetails = null;
			ArrayList<NewOrderDto> listProductName = new ArrayList<NewOrderDto>();
			NewOrderDto objDto = null;	
			String sqlFetchProductDetails="SELECT PRODUCTTYPEID,PRODUCTNAME,PRODUCT_NAME_FOR_FX,PRODUCT_NAME_FOR_CRM FROM ioe.TPRODUCTTYPE WHERE ISACTIVE=1";
			try
			{
				connection=DbConnection.getConnectionObject();
				csProductDetails= connection.prepareCall(sqlFetchProductDetails);
				rsProductDetails = csProductDetails.executeQuery();
				while(rsProductDetails.next())
				{
					objDto =  new NewOrderDto();
					objDto.setProductId((rsProductDetails.getInt("PRODUCTTYPEID")));
					objDto.setProductName((rsProductDetails.getString("PRODUCTNAME")));
					objDto.setProduct_name_for_fx((rsProductDetails.getString("PRODUCT_NAME_FOR_FX")));
					listProductName.add(objDto);	
				}
				return listProductName;
			}
			catch(Exception ex )
			{
				Utility.LOG(true, false, ex, "::Exception occured while fetching product  name in method getProductAndId::block1");	
			}
			finally
			{
				try 
				{
					DbConnection.closeResultset(rsProductDetails);
					DbConnection.closeCallableStatement(csProductDetails);
					DbConnection.freeConnection(connection);
				} 
				catch (SQLException e) 
				{
					// TODO Auto-generated catch block
					Utility.LOG(true, true, e, "::Exception occured while fetching product name in method getProductAndId");					
				}
				catch(Exception ex)
				{
					Utility.LOG((ex.getMessage() + " Exception occured while fetching product name in method getProductAndId" ));
				}
			}
			return listProductName;
		}
		
		
		
		public static int isActiveFEId(long seId) throws Exception{
			Connection conn=null;
			ResultSet rs = null;
			int status = 0 ;
			PreparedStatement pstmt=null;
			String sqlQuery="SELECT IS_ACTIVE FROM ioe.TM_FIELD_ENG_MAPPING WHERE FIELD_ENGINEER_ID=?";
			try{
				conn=DbConnection.getConnectionObject();
				pstmt=conn.prepareStatement(sqlQuery);
				pstmt.setLong(1, seId);
				rs=pstmt.executeQuery();
				while(rs.next())
				{
					status=Integer.valueOf(""+rs.getLong("IS_ACTIVE"));
				}
				return status;
				
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				DbConnection.closePreparedStatement(pstmt);
				DbConnection.closeResultset(rs);
				DbConnection.freeConnection(conn);
				
			}
			return status;
        }
		
		public static int isActivePartnerCode(long partnerId) throws Exception{
			Connection conn=null;
			ResultSet rs = null;
			int status = 0 ;
			PreparedStatement pstmt=null;
			String sqlQuery="SELECT IS_ACTIVE FROM ioe.TM_CHANNEL_PARTNER WHERE PARTNER_ID=?";
			try{
				conn=DbConnection.getConnectionObject();
				pstmt=conn.prepareStatement(sqlQuery);
				pstmt.setLong(1, partnerId);
				rs=pstmt.executeQuery();
				while(rs.next())
				{
					status=Integer.valueOf(""+rs.getLong("IS_ACTIVE"));
				}
				return status;
				
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				DbConnection.closePreparedStatement(pstmt);
				DbConnection.closeResultset(rs);
				DbConnection.freeConnection(conn);
				
			}
			return status;
        }
		//[220] start
		public int findRateRenewalAutoTriggerServices(long orderNo) throws Exception{
		
			String methodName="findRateRenewalAutoTriggerServices", className=this.getClass().getName(),msg="";
			Connection connection =null;
			//PreparedStatement pstmt =null;
			CallableStatement callstmt =null;
			int flag = 0;
			
			try{
				connection=DbConnection.getConnectionObject();
				connection.setAutoCommit(false);
				callstmt = connection.prepareCall(sqlRateRenewalAutoTriggerServices);
				callstmt.setLong(1, orderNo);
				callstmt.registerOutParameter(2, java.sql.Types.INTEGER);
				callstmt.execute();
				
				flag = callstmt.getInt(2);
				connection.commit();
			}catch(Exception ex){
				if(connection!=null)	connection.rollback();
			}
			finally{
				DbConnection.closeCallableStatement(callstmt);
				DbConnection.freeConnection(connection);
			}
			
			return flag;
		}
		
	
		public int getCountOfRRAutoTriggerServicesForPreviousMonth(long orderno) throws Exception{
			
			Calendar calendar = Calendar.getInstance();
			calendar.set(calendar.DATE,1);
			java.util.Date firstdateofmonth=calendar.getTime();
			java.sql.Date date = new java.sql.Date(firstdateofmonth.getTime());
			
			String NoOfRRAutoTriggerServices = "SELECT COUNT(1) AS COUNTOFSERVICES FROM IOE.TPOSERVICEMASTER TPOSERVICEMASTER" +
					 " inner join ioe.TCHANGE_ORDER_DETAILS TCHANGE_ORDER_DETAILS on TCHANGE_ORDER_DETAILS.NEWSERVICEID = TPOSERVICEMASTER.SERVICEID" +
				     " inner join IOE.TPOSERVICEMASTER_EXTENDED TPOSERVICEMASTER_EXTENDED on TPOSERVICEMASTER.SERVICEID = TPOSERVICEMASTER_EXTENDED.SERVICEID " +
				     " where TPOSERVICEMASTER.ORDERNO = ? and AUTO_TRIGGER_SPECIFIC_TYPE ='RR' and VALIDATION_STATUS = 1  and EFFECTIVEDATE < ?";
			
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			int countofservices=-1;
			try{
				conn=DbConnection.getConnectionObject();
				
				pstmt = conn.prepareCall(NoOfRRAutoTriggerServices);
				pstmt.setLong(1, orderno);
				pstmt.setDate(2, date);
				rs = pstmt.executeQuery();
				if(rs.next())
				{
					countofservices = Integer.valueOf(rs.getString("COUNTOFSERVICES"));
				}
			}finally{
				DbConnection.closeResultset(rs);
				DbConnection.closePreparedStatement(pstmt);
				DbConnection.freeConnection(conn);
			}
			
			return countofservices;
		}
		
		public int getCountOfApprovalDocument(long orderno,
				int rrAutoTriggerDocumentId) throws Exception{
			
			String documentuploadstatus  = "SELECT COUNT(1) AS FILEUPLOADCOUNT FROM ioe.TFILEUPLOAD WHERE ORDERNO = ? and FILETYPEID = ?" ;
			
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			int countoffileupload=-1;
			try{
				conn=DbConnection.getConnectionObject();
				
				pstmt = conn.prepareCall(documentuploadstatus);
				pstmt.setLong(1, orderno);
				pstmt.setLong(2, rrAutoTriggerDocumentId);
				rs = pstmt.executeQuery();
				
				if(rs.next()){
					countoffileupload = Integer.valueOf(rs.getString("FILEUPLOADCOUNT"));
				}
			}finally{
				DbConnection.closeResultset(rs);
				DbConnection.closePreparedStatement(pstmt);
				DbConnection.freeConnection(conn);
			}
			
			return countoffileupload;
		}
		//[220] end 
		//[221] Start
		public String checkDropAndCarryLSiForPD(String strLogicalSiNo, int subChangeTypeId) throws Exception {
			
			String methodName="checkDropAndCarryLSiForPD",  msg="";
			boolean logToFile=true, logToConsole=true;
			
			Connection conn = null;
			CallableStatement cstmt = null;
			String msgCode=null,strResult="";
			
			try {
				
				conn=DbConnection.getConnectionObject();
				cstmt=conn.prepareCall(sqlCheckValidLsiForChangeOrder);
				cstmt.setString(1, strLogicalSiNo);
				cstmt.setInt(2, subChangeTypeId);
				cstmt.registerOutParameter(3, java.sql.Types.BIGINT);
				cstmt.registerOutParameter(4, java.sql.Types.VARCHAR);
				cstmt.registerOutParameter(5, java.sql.Types.VARCHAR);
				cstmt.registerOutParameter(6, java.sql.Types.VARCHAR);
				cstmt.execute();
				msgCode = cstmt.getString(4);
				
				if("-1".equalsIgnoreCase(msgCode)){
					strResult=cstmt.getString(5);
				}
				
			} catch (Exception e) {
				//conn.rollback();
				Utility.LOG(logToFile, logToConsole, "from method "+methodName+" "+e);
				
			}finally{
				DbConnection.closeCallableStatement(cstmt);
				DbConnection.freeConnection(conn);
			}
			
			
			return strResult;
			
		}
		//[221] End
}
