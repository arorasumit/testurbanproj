//tag		Name       Date			CSR No			Description 

//[001]	 LawKush		10-Feb-11	CSR00-05422     for orders pending in billing and hardware
//[002]	 SUMIT ARORA	03-Mar-11	CSR00-05422     For Service Summary Mandatory and Non Mandatory
//[003]  Vishwa			17-Mar-11	CSR00-05422		Populate Pincode based City and State
//[004]  ANIL KUMAR		21-June-11	CSR00-05422		display Billing Contact and email, as well as lst and cst for dispatch.

//[00055]	 Ashutosh Singh		05-July-11	CSR00-05422     Document tracking for Diffrent Role
//[005]	  Lawkush 	 12-June-12	    CSR00-05422		For making service  summary and service product attributes editable or non editable according to change type , subchange type and SUBCHANGETYPE_NETWORK_CHANGE_EDITABLE flag  in database
package com.ibm.ioes.forms;

//Tag Name Resource Name  Date		CSR No			Description
//[002]	 ROHIT VERMA	07-NOV-11	CSR00-05422		Report for Copy & Cancal 

//[005] Vishwa 09-Nov-2011 CSR00-05422 Servicename added to be used in product catalog page
//[007] Rohit Verma	 22-July-13	CSR-IT-09112	Parallel Upgrade Order No Added on service Level
//[008] Gunjan Singla  12-Dec-13                 Added cancelReason parameter    
//[009] Gunjan Singla   5-Mar-14                 Added cancelReasonId
//[0011]   Neha Maheshwari    added service segment   NORTH STAR ACCOUNT TAGGING
//[0012] Gunjan Singla   27-Dec-14     20141113-R1-020802    ParallelUpgrade-Multiple LSI Selection
//[0014] Gunjan Singla   4-Mar-15     20150225-R1-021111    LSI and Service No Search required in Lines Tab
//[0015] Neha Maheshwari 10-Jun-2015 20141219-R2-020936 Billing Efficiency Program Parallel Upgrade LSI
//[0016] Priya Gupta     19-Jun-2015 20141219-R2-020936 Billing Efficiency Program Ld Clause in Ib2b
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.ibm.ioes.forms.ServiceSubTypeDto;
import com.ibm.ioes.utilities.PagingSorting;

public class NewOrderDto 
{
	
	private String idTextBox=null;
	public String getIdTextBox() {
		return idTextBox;
	}
	public void setIdTextBox(String idTextBox) {
		this.idTextBox = idTextBox;
	}
	public String getValTextBox() {
		return valTextBox;
	}
	public void setValTextBox(String valTextBox) {
		this.valTextBox = valTextBox;
	}
	public String getCounter() {
		return counter;
	}
	public void setCounter(String counter) {
		this.counter = counter;
	}

	private String valTextBox=null;
	private String counter=null;
	// [0014] start
		private int searchLSI;
		private int searchServiceNo;
		// [0014] end
	private int isUsage;
	private long published_by_empid;
	private long published_by_roleid;
	//private long published_date;
	//Meenakshi : seperate string defined for PO coming with charges , used for Chnage orders
	private String poNoOfCharge;
private int isServiceActive;	
private String accountName;
private String shortCode;
private int count1;
private long ponum;
private String ponum1;
private long noticePeriod;
private String order_types;

public String getOrder_types() {
	return order_types;
}
public void setOrder_types(String order_types) {
	this.order_types = order_types;
}

private String[] scenario;



public String[] getScenario() {
	return scenario;
}
public void setScenario(String[] scenario) {
	this.scenario = scenario;
}

private String designation;
private String lst_No;
   private int noofuses;
	private int isUDS;
	private int accountID;
	private int crmAccountId;//Added By Ashutosh
	private String remarks;
	private String accountIDString;
	private String orderNo;
	private String poRecieveDate;
	private String accountManager;
	// To  INcomplete Order
	private String searchCurrencyName;
	private String projectmgremail;
	private String searchSource;
	private String searchAccountName;
	private String rfs_date;
	//[00055] Start (Added By Ashutosh)
	private int fileId;
	private String fileName;
	private String fileIds;
	private String selectDocStatus;
	private String docStatus;
	private String finalTracking;
	//[00055]End
	private long accphoneNo;
	private int defaultDispatchAddForAccID;
	private String projectManager;
	private String party_num;
	
    //[0011] Start
	private String serviceSegment;
	//[0011] End

	private String lob;
//	private int respId;
	private int  excludecheck;
	
	/*public int getRespId() {
		return respId;
	}
	public void setRespId(int respId) {
		this.respId = respId;
		}*/
	private String spFirstname;
	private int employeeid;
	//	start:Anil for Hardware warrenty details
	private String startHWDateLogic;
	private String endHWDateLogic;
	private int warrentyMonths;
	private String startDate = "";
	private String endDate = "";
	private int isReqStartHWDateLogic;
	private int isReqEndHWDateLogic;
	private int isReqWarrentyMonths;
	private int isReqStartDate;
	private int isReqEndDate;
	//end: anil;
	private String lstDate;
	private int lstNo;
	//Rakshika
	private String changeType;
	private int changeTypeId=0;
	private String changeTypeName;
	private long created_serviceid;
//	for incompleteorder
	private String searchCRMOrder;
	private String searchAccountNo;
	private String searchOrderType;
	private String searchfromDate;
	private String searchToDate;
	private String searchSourceName;
	private String searchQuoteNumber;
	private String searchCurrency;
	private String searchStageName;
	private String plocationtype;
	private String slocationtype;
	
	private String newType;
	private int newTypeId;
	private String newTypeName;
	private int subChangeTypeId;
	private String subChangeTypeName;
	private String dispatch_name;
     private String treeViewURL;
	
	private String serviceParentId;
	private String serviceChildId;
	
	private String spLastName;
	private String sourcePartyNo;
	private long sourcePartyID;
	private String transferdate;
	private String accountno;
	private String filterLSIList;
	private String spLEmail;
    private String acmgrPhno;//changed by kalpana from long to string for bug id HYPR11042013001
	private String spLPhno;//changed by kalpana from long to string for bug id HYPR11042013001
	private String acmgrEmail;
	private String downtimeClause;
	//[008] Begin
	private String cancelReason;

	
	//[0010]start
	 private String service_cancelledby;
	 private String serv_cancel_reson;
	 private String serv_cancel_remarks;
	 private String service_cancl_date;
	//[0010] end
	
	//[009]
	private String ordServFlag;

	// ADDED BY MANISHA O2C START
	private String initiated_to;
	private int role_id;
	// ADDED BY MANISHA O2C END
	public String getCancelReason() {
		return cancelReason;
	}
	public String getInitiated_to() {
		return initiated_to;
	}
	public void setInitiated_to(String initiated_to) {
		this.initiated_to = initiated_to;
	}
	public int getRole_id() {
		return role_id;
	}
	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
	
	//[008] end
	//Start by Deepak Kumar for Third Party SCM
	private int  sendToSCM;
	private int prId;
	private int newPrFlag;
	private int prReuseUpadte;
	private int chargeId_Scm;
	private int notSaveInScm;
	private int isPrReuse;
	private int newPrFlagForView;
	private ArrayList<ChargeDetailsSCM> chargeDetailsSCM;
	private ArrayList<Integer> deleteScmList=null;
	private String errorMsgScm;
	private String successMsgScm;
	private String prNumber;
	//end Deepak Kumar
	//satyapan OSP Tagging By nagarjuna
	private String isOSPTagging;
	private String txtOSPRegNo;
	private String txtOSPRegDate;
	private int isReqOSPTagging;
	private int isReqOSPRegNo;
	private int isReqOSPRegDate;
	//satyapan OSP Tagging By nagarjuna
	
	
	public void setChannelPartnerId(long channelPartnerId) {
		this.channelPartnerId = channelPartnerId;
	}

	public long getChannelPartnerId() {
		return channelPartnerId;
	}

	private String channelPartnerIdTest;
	//private int channelPartnerCode;
	private String channelPartnerCodeTest;
	//private String channelPartnerCtgryName;
	private int seId;
	private ArrayList<NewOrderDto> addlist=null;
	private ArrayList<NewOrderDto> editlist=null;
	
	public String getDowntimeClause() {
		return downtimeClause;
	}
	public void setDowntimeClause(String downtimeClause) {
		this.downtimeClause = downtimeClause;
	}
	//WARRANTY CLAUSE ADDED BY MANISHA START
	private String warrantyClause;
	private int isReqTxtWarrantyClause;
	//WARRANTY CLAUSE ADDED BY MANISHA end
	
	//bcp details for services ADDED BY MANISHA start
	private String billingBCPIdService;
	public String getWarrantyClause() {
		return warrantyClause;
	}
	public void setWarrantyClause(String warrantyClause) {
		this.warrantyClause = warrantyClause;
	}
	public int getIsReqTxtWarrantyClause() {
		return isReqTxtWarrantyClause;
	}
	public void setIsReqTxtWarrantyClause(int isReqTxtWarrantyClause) {
		this.isReqTxtWarrantyClause = isReqTxtWarrantyClause;
	}
	public String getBillingBCPIdService() {
		return billingBCPIdService;
	}
	public void setBillingBCPIdService(String billingBCPIdService) {
		this.billingBCPIdService = billingBCPIdService;
	}
	public int getIsReqBillingBCPIdService() {
		return isReqBillingBCPIdService;
	}
	public void setIsReqBillingBCPIdService(int isReqBillingBCPIdService) {
		this.isReqBillingBCPIdService = isReqBillingBCPIdService;
	}
	private int isReqBillingBCPIdService;
	//bcp details for services ADDED BY MANISHA END
	
	private String disconnection_type;
	
	private String disconnected_orderno;
	
	private String region;
	
	private String zone;
	
	private int currencyID;
	
	private String currencyName;
	
	private String currencyCode;
	
	private int maxOrderNo;
	
	private String sourceName;
	
	private int sourceID;
	
	private String orderType;
	
	private String orderDate;
	
	private String quoteNo;
	
	private int orderNumber;
	
	private String stageName;
	
	private int poNumber;
	
	private int updateType;
	
	private String searchAccount;
	
	//for MAIN TAB STARTS
	private String attributeLabel;
	private String attributeValue;
	private String dataType;
	private String alisName;
	private int attributeID;
	private int attMaxLength;
	private String expectedValue;
	private String mandatory;
	//	for MAIN TAB Ends
	
	//for CONTACT TAB STARTS
	private String contactType;
	private String saluation;
	private String firstName;
	private String lastName;
	private String cntEmail;
	private String contactCell;
	private String contactFax;
	private Long contactId;
	private int isReqContactType;
	private int isReqSaluation;
	private int isReqFirstName;
	private int isReqLastName;
	private int isReqCntEmail;
	private int isReqContactCell;
	private int isReqContactFax;
	private int isReqContactId;
	private int isReqAddress1;
	private int isReqAddress2;
	private int isReqAddress3;
	private int isReqCityName;
	private int isReqStateName;
	private int isReqCountyName;
	private int isReqPinNo;
	//	for CONTACT TAB Ends
		
	//for ADDRESS TAB STARTS
	private Long addID;
	private String address1;
	private String address2;
	private String address3;
	private String cityName;
	private String stateName;
	private String countyName;
	//Added by Ashutosh
	private int countyCode;
	private int stateCode;
	private int cityCode;
	private String pinNo;
	//	for ADDRESS TAB Ends
	
	// for PO Details Tab Starts
	private String custPoDetailNo; //by Saurabh
	private String custPoDate;   //by Saurabh 
	private String poDetailNo;
	private String poDate;
	private String poReceiveDate;
	private String contractStartDate;
	private String contractEndDate;
	private String periodsInMonths;
	private String totalPoAmt;
	private String entity;
	//[0016]
	private String ldClause;
	
	private int isReqPODate;
	private int isReqPORcvDt;
	private int isReqcontractStartDate;
	private int isReqcontractEndDate;
	private int isReqPeriodsInMonths;
	private int isReqTotalPoAmt;
	private int isReqEntity;
	private int isReqCustPoDetailNo;//by Saurabh
	private int isReqCustPoDate; //by Saurabh
	private int isReqPoEmailId;//by Saurabh
	//[0016]
	private int isReqLdClause;
	
	
	private String defaultPO;
	private String poIssueBy;
	private String poEmailId;
	private String poRemarks;
	private String poDemoContractPeriod;
	
	private int contactUpdateType;
	private ArrayList contactTypes;
	private String contactTypeId;
	private String contactTypeName;
	private String product_name_for_fx;
	private int productId;
	
	private int subProductId;
	
	private String subProductName;
	private String curShortCode;
//	Raghu
//	added for Pop Location PopUp on product catelog page
		private String popNetLocName;
		private String popNetLocCode;
		private String popNetLocDesc;
		private String popLocAddress1;
		private String sessionid;
		private String popLocationCode;
		private String logicalsi;
	//TODO Created By Sumit On 11-Aug-2010---Starts 
	
	private ArrayList<NewOrderDto> serviceSummary;
	//lawkush start
	private ArrayList<NewOrderDto> serviceSummaryLov;
	//lawkush End
	private ArrayList<ServiceSubTypeDto> serviceSubType;
	
	private int serviceTypeId;
	
	private String serviceTypeName;
	
	private int serviceSubtypeId;
	
	private int serviceId;
	private String serviceId1;
	private String serviceStatus;
	
	private String serviceIdString;
	private String isPublishedString;
	private String hdnOrderNo;
	private int isServiceCreatedAfterCancelCopy;
	private int isNewOrder;
	
	private String serviceSubTypeName;
	
	private int prdAttrEntered = 0;
	
	private int servAttrEntered=0;
	
	private int productCatelogEntered=0;
	
	//TODO Created By Sumit On 11-Aug-2010---Ends	
	
	//ROHIT VERMA-SERVICE ATTRIBUTES STARTS//
	
	private String logicalSINo;
	
	private String custSINo;
	
	private String effStartDate;
	//lawkush
	private String rfsDate;
	
	//
	private String effRRDate; //Shubhranshu , 6-6-2016
	//
	
	private String effEndDate;
	
	
	//Shubhranshu
	public String getEffRRDate() {
		return effRRDate;
	}
	public void setEffRRDate(String effRRDate) {
		this.effRRDate = effRRDate;
	}
//
	private String attRemarks;
	
	private int m6OrderNo=0;
	private String m6OrderNo2="";
	private String m6OrderNoString;
	
	private String LOC_Date ;
	private String LOC_No;
	private String m6_Child_Ser_Key;
	private int party_no;
	private int party_id;
	private String fx_external_acc_id;
	private String companyName;
	private String demo;
	//ROHIT VERMA-SERVICE ATTRIBUTES ENDS//
	
	//ROHIT VERMA SERVICE DETAIL STARTS
	
	private int serviceDetailID;
	private int parent_serviceProductID;
	private int isReqServiceDetailID;
	private int isReqParent_serviceProductID;
	
	private String serviceDetDescription;
	
	private String serviceTypeDescription;
	
	//	ROHIT VERMA SERVICE DETAIL ENDS
	
	//for PRODUCT CATELOG SERVICE ATTRIBUTES STARTS
	
	private String prodAttributeLabel;
	
	private String prodAttributeValue;
	
	private String prodDataType;
	
	private String prodAlisName;
	
	private int prodAttributeID;
	
	private int prodAttMaxLength;
	
	private String prodExpectedValue;
	
	private String prodAttVal;
	private String newProdAttVal;
	private String dispatchContactName;
	//	for PRODUCT CATELOG SERVICE ATTRIBUTES Ends
	
	private int entityID;
	
	private String licCompanyName;
	
	private int licCompanyID;
	
	private int hdnServiceDetailID;
	private String last_pm;
	private int storeID;
	
	private String StoreName;
	private String store;
	private int bcpID;
	
	private String bcpName;
	
	//For Dispatch Address Starts
	public int dispatchAddressID;
	private String dispatchAddress1;
	private String dispatchAddress2;
	private String dispatchAddress3;
	private String dispatchCityName;
	private String dispatchStateName;
	private String dispatchCountyName;
	private String dispatchPinNo;
	private String dispatchPhoneNo;
	public String dispatchAddressName;
	private String dispatchEmail;
	private String dispatchAddress4;
	private String dispatchLSTNo;
	private String dispatchDesignation;
	//For Dispatch Address Ends
	
	//	 for Primary Location Codes
	private int plocationCode;
	private String ptxtPAddress;
	private String paddress1;
	private String paddress2;
	private String paddress3;
	private String paddress4;
	private String pcity;
	private String pstate;
	private String ppincode;
	private String pcountry;
	//
	//billing address
	private String baddress1;
	private String baddress2;
	private String baddress3;
	private String baddress4;
	private String bcity;
	private String bstate;
	private String bpincode;
	private String bcountry;
	private String bcontactNo;
	private String bEmailId;
	private String bemailid;
	private String bfirstname;
	private String blastname;
	// billing address
	private String toaddress;
	private String faddress;
	//	 for Secondary Location Codes
	private int slocationCode;
	private String nwkLocationName;
	private String stxtSAddress;
	private String saddress1;
	private String saddress2;
	private String saddress3;
	private String saddress4;
	private String scity;
	private String sstate;
	private String spincode;
	private String scountry;
	//ends
	private int crmAccountNo;	
	
	//For Product Service Attributes Start
	private int hdnSeriveAttCounter;
	private String[] prodAttID;
	private String[] prodAttValue;
	private String[] prodAttExptdValue;
	private String[] prodAttName;
	private String[] prodAttMandatory;
	private Integer[] prodAttriMaxLength;
	private Integer[] prodServiceAttMaxLength;
	//----[002]-----
	private String[] serviceSummaryMandatory;
	//----[002]-----
	//Added by Ashutosh for Solution Change
	private String[] newProdAttValue = {"ram"};
	private String[] attributeValuIDs;
	//For Product Service Attributes End
	
	//	For Billing Info Starts
	private String poAmount;
	private int podetailID;
	private int contractMonths;
	private String billingPODate;
	private int contractPeriod;
	private String billingMode;
	private String billingformat;
	private int licenceCoID;
	private String taxation;
	private int commitmentPeriod;
	private String billingLevel;
	private String penaltyClause;
	private int creditPeriod;
	private int billingType;
	private int isReqTxtPODetailNo;
	private int isReqTxtBillingAC;
	private int isReqTxtBillingCP;
	private int isReqTxtEntity;
	private int isReqTxtBillingMode;
	private int isReqTxtBillingLvlNo;
	private int isReqTxtBillingformat;
	private int isReqTxtLicenceCo;
	private int isReqTxtTaxation;
	private int isReqTxtCmtPeriod;
	private int isReqTxtBillingLvl;
	private int isReqTxtPenaltyClause;
	//	For Billing Info Ends
    // For STS Reason Combo for Change Order
	private long stdReasonId;
	private String stdReasonName;
	//FOR hARWARE
	private	int selectedDispatchID;
	private	int selectedStoreID;
	private	String formAvailable;
	private	String hardwareType;
	private	String saleNature;
	private	String saleType;
	
	private	int isReqSelectedDispatchID;

	//for service locations
	private int selectedPriLocType;
	private int selectedSecLocType;
	private int selectedPNLocation;
	private int selectedPrimaryBCP;
	private int selectedSNLocation;
	private int selectedSecBCP;
	private Long billingLevelNo;
	private String billingLevelNo1;
	private int isReqSelectedPNLocation;
	private int isReqSelectedPrimaryBCP;
	private int isReqSelectedSNLocation;
	private int isReqSelectedSecBCP;
	private String[] actmgremailid=null;
	private String actmgrname;
	
	private int isReqDdPrimaryLocType;
	private int isReqDdSecondaryLocType;
	
	private String serviceType;
	
	//for Charges Details
	private int frequencyID;
	private int factor;
	private String frequencyName;
	private int chargeTypeID;
	private String chargeTypeName;
	private String chargeName;
	private String chargeAnnotation;
	private int chargePeriod;
	private int chargeType;
	private int rcId;
	private int nrcId;
	
	private int lineItemId;
	private int sublineItemId;
	private String lineItemName;
	private String subLineItemName;
	private double chargeAmount;
	private String chargeAmount_String;
	private String chargeFrequency;
	private double chargeFrequencyAmt;
	private String chargeFrequencyAmt_String;
	private String lineItemLbl;
	private String subLineItemLbl;
	
	ArrayList<ChargesDetailDto> chargesDetails = null;
	private String deletedChargesList=null;
		private int isReqDdCType;
	private int isReqTxtCName;
	private int isReqTxtCPeriod;
	private int isReqTxtCTAmt;
	private int isReqTxtCFrequency;
	private int isReqTxtCFreqAmt;
	private int isReqTxtCStartDate;
	private int isReqTxtCEndDate;
	private int isReqTxtStore;
	private int isReqTxtHtype;
	private int isReqTxtform;
	private int isReqTxtTSale;
	private int isReqTxtNSale;
	private int isReqBbPrimaryBCPId;
	private int isReqPrincipalAmount;
	private int isReqInterestRate;
	private int isReqTxtCAnnotation;
	private int actmgrid;
	// By Saurabh for to and from location validation in product catelog
	private int isReqTxtFromLocation;
	private int isReqTxtToLocation;
	private String txtFAddress;
	private String txtToAddress;
	private int isReqLineItem;
	private int isReqSubLineItem;
	
	//private double chargeFrequencyAmtDouble;
	private long projectManagerID;
	private String startDateLogic;
	private String endDateLogic;
	private int isReqStartDateLogic;
	private int isReqEndDateLogic;
	private long attMasterId;
	private String attMasterName;
	private String msgOut;
	private ArrayList errors;
	private String[] prodAttributeLabelArray;
	private String[] prodAttributeValueArray;
	private String[] prodAttributeDataTypeArray;
	private String[] prodAttributeDisplayLabelArray;
	private String[] prodAttributeIsMandatory;
	private String poSearchDetailNumber;
	private String entityId;
	private ArrayList<TreeViewDto> lstTreeItems;
	
	private String productName;
	private String serviceName;
	private int serviceProductID;
	private String[] serviceProductIds;
	private String[] effectiveDate;
	private String[] rateChange;
	private String[] stdReason;
	
	private String effDate;
	private String componentIDList;
	private String contactIDList;
	private String addressIDList;
	private int chargeInfoID;
	private int billingInfoID;
	private int locationInfoID;
	private int hardwareDetailID;
	//for Region and Zone Details
	private int regionId;
	private String regionIdNew;
	private String regionName;
	private int zoneId;
	private String zoneName;
	
	private int salutationId;
	private String salutationName;
//	for Taxation Details
	private long taxationId;
	private String taxationName;
//	for BillingLevel Details
	private long billingLevelId;
	private String billingLevelName;
//	for BillingFormat Details
	private long billingFormatId;
	private String billingFormatName;
//	for BillingType Details
	private long billingTypeId;
	private String billingTypeName;
//	for CreditPeriod Details
	private long creditPeriodId;
	private String creditPeriodName;
	private int isChargesValid;
	private double totalAmountIncludingCurrent;	///For Total Amount For All Products
	
	private int productCount;
	
	//FOR PRODUCT ACCESS STARTS
	private int billingInfoValue;
	private int chargeInfoValue;
	private int locationInfoValue;
	private int hardwareInfoValue;
	private int serviceInfoValue;
	//FOR PRODUCT ACCESS ENDS
	private String billingBCPId;
	private int isReqBillingBCPId;
	
		//for Contact Report
	private String contactName;
	 PagingSorting pagingSorting = new PagingSorting();	
//	for Order Report
	private String toDate;
	private String fromDate;
	private String orderStatus;
	private String orderStage;
	private String productlevel = null;
	ArrayList<NewOrderDto> serviceProducts = null;
	private String link = null;
	
	private String parentServiceProductId = null;
	private int isReqParentServiceProductId ;
	private String mode = null;
	
	public static final String MODE_INSERT="INSERT";
	public static final String MODE_UPDATE="UPDATE";
	
	public String message = null;
	public String messageId = null;//CHILD_PRESENT,ENTRY_COPIED,FIRST_ENTRY

	
	// ServiceSummary DropDown Values
	private String serviceSummaryText = null;
	private String serviceSummaryValues;
	//for copy functinality in product catelog
	private String copyProductValues;
	
	private String m6ShortCode;
	
	private String collectionMgr;
	
	private String txtquotesNo;
//-----------For Field Role Wise Validation------------New Order Changes-
	String isReadOnly;
	String isMand;
	String currentRole;
	String feildName;
	String fieldLabel;
	//-----------For Field Role Wise Validation------------New Order Changes-
	
	//20-jan-2010	rohit verma inputing From and To Location Start
	String isFromLocationRequired;
	String isToLocationRequired;
	String fromLocation;
	String toLocation;
	//20-jan-2010	rohit verma inputing From and To Location End
	
	//	18-feb-2010	Saurabh Singh inputing From and To Location For Copy Charge Start
	private String prodNameAndId;
	private int sourceProductId;
	private int destinationProductId;
	//	18-feb-2010	Saurabh Singh inputing From and To Location For Copy Charge End
	
	private String hardwaretypeName;
	private int hardwaretypeId;
	
	private String formName;
	private int formId;

	private String saleNatureName;
	private int saleNatureId;
	
	private String saleTypeName;
	private int saleTypeId;
	
	private int principalAmount;
	private int interestRate;
	
	private int startDateDays;
	private int startDateMonth;
	private int endDateDays;
	private int endDateMonth;
	
	private String chkIsDemo;
	private String noOfDaysForDemo;
	
	//Meenakshi : new fileds for SI no
	private int logicalSINumber;
	private int customer_logicalSINumber;
	private int issuspended = 0; // Added by Saurabh
	private int isdisconnected = 0; // Added by Saurabh
	ArrayList customer_logicalSINumbers = new ArrayList() ;
	private String roleId;
	private String roleName;
	private String userId;
	private int serviceproductid = 0;//Added by Saurabh
	
		//
	
	private String initial_pm;
	private String last_changed_by;
	
	
	private int chargeNameID;
	
	private String ib2bWorkflowAttachedId;
	private String ib2bWorkflowAttachedName;
	private String projectWorkflowId;
	private String changeorderstatus;
	private String partyName;       //by Saurabh
	private String m6CreateDate;    //by Saurabh
	private String amApproveDate;	//by Saurabh
	private String pmApproveDate;	//by Saurabh
	private String copcApproveDate; //by Saurabh
	private String order_type;	//by Saurabh
	private int fromOrderNo=0;  //by Saurabh
	private int toOrderNo=0;	//by Saurabh
	private int fromAccountNo=0;	//by Saurabh
	private int toAccountNo=0;	//by Saurabh
	private String toCopcApprovedDate;  //by Saurabh
	private String fromCopcApprovedDate;  //by Saurabh
	private int legalEntity;  //by Saurabh
	private int isFLEFlag;
	private int  similartaxrate;

	
	//added by Ravneet
	private String orderInfo_OrderType = null;
	private Integer orderInfo_ChangeType = null;
	private Integer orderInfo_ChangeSubType = null;
	
	public final String ORDER_TYPE_NEW= "NEW";
	public final String ORDER_TYPE_CHANGE= "CHANGE";
	
	public final int CHANGE_TYPE_DISCONNECTION= 3;
	public final int CHANGE_TYPE_RATERENEWAL= 5;
	public final int CHANGE_TYPE_SOLUTION_CHANGE= 2;
		//added by lawkush for pending order bill
// [001]	Start 
	private String fromOrderDate;
    private String toOrderDate;
    private String fromCrmOrderid;
    private String toCrmOrderid;
	private String party;
	private Integer accountNo;
	// [001]	End
	
	
	private int isServiceSummMandatory;
	private int isPublished;   // By Saurabh for Partial Publish
	//Meenakshi
	//added for storing child and sub child spids for all spids
	private Map<String,String> parentChildRelationMap = new HashMap<String, String>();
//[003] Start	
	private int pinCodeId;
	private int pinCode;
	
	private String taxPkgInstId=null;
	private Double taxRate =null;
	private String dependentOnTax=null;
	
	
	//start[004]
	private String cst;		
	private String pcontactNO;
	private String pemailId;
	private String scontactNO;
	private String semailId;
	private String bemailID;
	//end[004]
	
	private String podetailID_String;
	private int isDefaultPO;
	//lawkush
	private int isNfa;
	
	private int isUrgent;
	
	//anil
	private int queryNameID;
	private String queryFormValue;
	 //Meenakshi
	private String rcActiveDateCrossed;
	private String nrcActiveDateCrossed;
	private String rcInactiveDateCrossed;
    private String cancelServiceReason;
    
    // added by Raghu fro croslinkage
	private String logicalCircuitId;
	private String infraOderNo;
	private String metasolvCircuitId;
	private String logicalCircuitId_new;
	private String infraOderNo_new;
	private String metasolvCircuitId_new;
	private String linkageInfoFlag;
	private String salesEmailId;
	private String salesFirstName;
	private String salesLastName;
	private long salesPhoneno;
	
    //lawkush start
    private String dateLogicValue;
	private String section;
	
	private String orderStatusValue;
	private String chargeRemarks;
	
	private int paymentTerm1;
	private int paymentTerm2;
	private int paymentTerm3;
	private int paymentTerm4;
	// ---Change Made By Sumit For PM Present InWorkflow
	private int isPMPresent;
	
	//ROHIT START
	private String defValue;
	//ROHIT END
	
	//SAURABH START FOR MPLS
	private int flexiCount;
	private int siteBasedCount;
	private int userBasedCount;
	private int destAttId;
	private String planName;
	//SAURABH END FOR MPLS
	//[002] Start
	private int oldOrderNo;
	private int newOrderNo;
	private int rootOrderNo;
	private int oldServiceNo;
	private int newServiceNo;
	private String createdBy;
	private String createdDate;
	private int componentInfoID;
	//[002] End
	//Meenakshi : added for components
	private int componentID;
	private String componentName;
	private int packageID;
	private String packageName;
	private int componentInfoValue;
	ArrayList<ComponentsDto> componentDetails = null;
	int haveComponent ;
	private String selectedPackageList = null;
	private ComponentsDto componentDto;
	 //Meenakshi
	 	private String checkedServiceNumber;
//	[005]Start
	private String attServiceName;
	private String opportunityId;
	private int usageBasedLineCount;
	

	//Add by Anil for GAM attributes
	private String gam_name="";
	private String gam_emailid="";
	private String gam_contact="";
	private int gam_id=0;
	private int gam_validate_status=-1;
	private String formulaName="";
	private int formulaId=0;
	private String baseCredit="";
	private String kamCredit="";
	private String gamCredit="";
	private String creditDistribution="";
	private int attachFormulaStatus=0;
	//lawkush Start
	
	private int hdnCreatedBy;
	private int max_allowed_gam_validate=-1;
	private String fileType;
	private int fileTypeId;
	private long contactCount=0;
//Start[005]
	private int  subchangetypeNetworkChangeEditable;
	private long configValue;
	private int changetypeSolutionChangeEditable;
	private String lookupmode="";
	private String IRN_Number;
	private String guiAlert;
	private int fxInternalId;
	private int isDummy;
	private String attributeKey;
	private String linkPopUpId;
	private int dummyServiceId;
	private int isDummyServicePublished;
	private int dummyOrderNo;
	private int billingScenario;
	private int fxRedirectionSPID;
	private int fxRedirectionLSI;
	
	ArrayList<NewOrderDto> arborLSIList = null;
	private String isCCMapWithMBIC;
	private String mbicServiceId;
	private String mbicServiceProductId;
	private String isAttach;
	private String cc_M6_Progress_status;
	private String tgno_Number;
	private String productIdString ;
	
	private String custSegmentCode;
	private String accountCategory;
	private String chargeStartDate;
	private String billingBandwidth;
	private String billingBandwidthUOM;
	private String distance;
	private String lineItemAmount;
	private String ratio;
	private String verticalDetails;
	private String cancelFlag;
	private String orderTotalAmount;
	private String lineItemQuantity;
	
	//[006] Start
	private String cancelledOrderReference ;
	//[006] End
	//[007] Start
	private long parallelUpgradeLSINo ;
	//[007] End
	//[0012] start
	private String parallelUpgradeLSINo1;
	private String parallelUpgradeLSINo2;
	private String parallelUpgradeLSINo3;
	
	//[0012] end
		//added for m6 fail and success status
	private int total_m6_failed;
	private int total_m6_success;
	
	//Vijay add a variable that is related to publish All button on publish page
	//Here key is always would be "SERVICE_ID"
	private HashMap<String, Integer> serviceIdMap;
	private String groupName;
	private String demoStartDate;
	private String demoEndDate;
	private String demoDays;
	
	//Vijay add variables for order stage list
	private String searchOrderStageName;
	
	private String searchOrderStageCode;
	//Vijay end 
	
	private int respId;
	// Vijay end

	//adding one more field 'isDemo' for show the demo flag 
	private int isDemo;
	
	//[0015] Start
	private int parallelUpgradeLSIRequire;
	private String remarksParallelUpgrade;
	//[0015] End
	
	//[0017] start
	private String partnerCode;
	private String channelPartner;
	
	//pankaj start
	private String channelPartnerCode="";
	private String fieldEngineer="";
	private String channelPartnerCtgryName="";
	private String channelPartnerName;
	private String channelpartnerCode;
	private long channelPartnerId;
	private long fieldEngineerId;
	private int cust_Segment_Id;
	
	
	public int getCust_Segment_Id() {
		return cust_Segment_Id;
	}
	public void setCust_Segment_Id(int cust_Segment_Id) {
		this.cust_Segment_Id = cust_Segment_Id;
	}
	public String getChannelPartnerCode() {
		return channelPartnerCode;
	}
	public void setChannelPartnerCode(String channelPartnerCode) {
		this.channelPartnerCode = channelPartnerCode;
	}
	public String getChannelPartnerName() {
		return channelPartnerName;
	}
	public void setChannelPartnerName(String channelPartnerName) {
		this.channelPartnerName = channelPartnerName;
	}
	public String getChannelpartnerCode() {
		return channelpartnerCode;
	}
	public void setChannelpartnerCode(String channelpartnerCode) {
		this.channelpartnerCode = channelpartnerCode;
	}
	public long getFieldEngineerId() {
		return fieldEngineerId;
	}
	public void setFieldEngineerId(long fieldEngineerId) {
		this.fieldEngineerId = fieldEngineerId;
	}
	
	//pankaj end
	//[0017] end
	public int getIsDemo() {
		return isDemo;
	}
	public void setIsDemo(int isDemo) {
		this.isDemo = isDemo;
	}
	//[009]
	private int cancelReasonId;
	
	public int getCancelReasonId() {
		return cancelReasonId;
	}

	public void setCancelReasonId(int cancelReasonId) {
		this.cancelReasonId = cancelReasonId;
	}
	public int getRespId() {
		return respId;
	}

	public void setRespId(int respId) {
		this.respId = respId;
	}

	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public int getTotal_m6_failed() {
		return total_m6_failed;
	}
	public void setTotal_m6_failed(int total_m6_failed) {
		this.total_m6_failed = total_m6_failed;
	}
	public int getTotal_m6_success() {
		return total_m6_success;
	}
	public void setTotal_m6_success(int total_m6_success) {
		this.total_m6_success = total_m6_success;
	}
	public String getProductIdString() {
		return productIdString;
	}
	public void setProductIdString(String productIdString) {
		this.productIdString = productIdString;
	}
	public String getCc_M6_Progress_status() {
		return cc_M6_Progress_status;
	}
	public void setCc_M6_Progress_status(String cc_M6_Progress_status) {
		this.cc_M6_Progress_status = cc_M6_Progress_status;
	}
	public String getTgno_Number() {
		return tgno_Number;
	}
	public void setTgno_Number(String tgno_Number) {
		this.tgno_Number = tgno_Number;
	}
	public String getIsAttach() {
		return isAttach;
	}
	public void setIsAttach(String isAttach) {
		this.isAttach = isAttach;
	}
	public String getMbicServiceId() {
		return mbicServiceId;
	}
	public void setMbicServiceId(String mbicServiceId) {
		this.mbicServiceId = mbicServiceId;
	}
	public String getMbicServiceProductId() {
		return mbicServiceProductId;
	}
	public void setMbicServiceProductId(String mbicServiceProductId) {
		this.mbicServiceProductId = mbicServiceProductId;
	}
	public String getLookupmode() {
		return lookupmode;
	}
	public void setLookupmode(String lookupmode) {
		this.lookupmode = lookupmode;
	}
	public int getChangetypeSolutionChangeEditable() {
		return changetypeSolutionChangeEditable;
	}
	public void setChangetypeSolutionChangeEditable(
			int changetypeSolutionChangeEditable) {
		this.changetypeSolutionChangeEditable = changetypeSolutionChangeEditable;
	}
	public int getSubchangetypeNetworkChangeEditable() {
		return subchangetypeNetworkChangeEditable;
	}


	public void setSubchangetypeNetworkChangeEditable(
			int subchangetypeNetworkChangeEditable) {
		this.subchangetypeNetworkChangeEditable = subchangetypeNetworkChangeEditable;
	}
//	End[005]
	private long poCount = 0;
	public String getFileType() {
		return fileType;
	}


	public void setFileType(String fileType) {
		this.fileType = fileType;
	}


	public int getFileTypeId() {
		return fileTypeId;
	}


	public void setFileTypeId(int fileTypeId) {
		this.fileTypeId = fileTypeId;
	}


	//lawkush End
	private String osp;
	
	//Added  for  billing Efficiency
	private String ldDateClause;
	private String delayedTimeInDayes;
	private String ldPercentage;
	private String maxPercentage;
	
	public String getOsp() {
		return osp;
	}


	public void setOsp(String osp) {
		this.osp = osp;
	}
	
	//add by Anil for CLEP requirement
	private String billingTriggerStatus="";
	private String order_creation_source="";
	private String charge_creation_source="";
	private String billingTriggerMsg="";	
	//end CLEP
	
	//PAGING-SERVICE-LINE-14-10-2012
	private int total_billing_trigger;
	private int total_cancel_copy;
	private int total_m6_processing;
	private int total_billing_trigger_end;
	private int total_cancel_m6;
	private int total_cancel_crm;
	private int total_services;
	private int total_new_services;
	//PAGING-SERVICE-LINE-14-10-2012
	
	//added by ashutosh for charge details paging
	private double totalChargeAmount=0l;
	public int getGam_id() {
		return gam_id;
	}


	public int getGam_validate_status() {
		return gam_validate_status;
	}


	public void setGam_validate_status(int gam_validate_status) {
		this.gam_validate_status = gam_validate_status;
	}


	public void setGam_id(int gam_id) {
		this.gam_id = gam_id;
	}	
	

	private String orderStageAnnotationName;
	
	public String getOpportunityId() {
		return opportunityId;
	}


	public void setOpportunityId(String opportunityId) {
		this.opportunityId = opportunityId;
	}


	public String getAttServiceName() {
		return attServiceName;
	}


	public void setAttServiceName(String attServiceName) {
		this.attServiceName = attServiceName;
	}

//	[005]End
	public int getIsPMPresent() {
		return isPMPresent;
	}


	public void setIsPMPresent(int isPMPresent) {
		this.isPMPresent = isPMPresent;
	}
// ---Change Made By Sumit For PM Present InWorkflow
	public int getPaymentTerm1() {
		return paymentTerm1;
	}


	public void setPaymentTerm1(int paymentTerm1) {
		this.paymentTerm1 = paymentTerm1;
	}


	public int getPaymentTerm2() {
		return paymentTerm2;
	}


	public void setPaymentTerm2(int paymentTerm2) {
		this.paymentTerm2 = paymentTerm2;
	}


	public int getLineItemId() {
		return lineItemId;
	}
	public void setLineItemId(int lineItemId) {
		this.lineItemId = lineItemId;
	}
	public String getLineItemName() {
		return lineItemName;
	}
	public void setLineItemName(String lineItemName) {
		this.lineItemName = lineItemName;
	}
	public int getSublineItemId() {
		return sublineItemId;
	}
	public void setSublineItemId(int sublineItemId) {
		this.sublineItemId = sublineItemId;
	}
	public String getSubLineItemName() {
		return subLineItemName;
	}
	public void setSubLineItemName(String subLineItemName) {
		this.subLineItemName = subLineItemName;
	}
	public int getIsReqLineItem() {
		return isReqLineItem;
	}
	public void setIsReqLineItem(int isReqLineItem) {
		this.isReqLineItem = isReqLineItem;
	}
	public int getIsReqSubLineItem() {
		return isReqSubLineItem;
	}
	public void setIsReqSubLineItem(int isReqSubLineItem) {
		this.isReqSubLineItem = isReqSubLineItem;
	}
	public int getPaymentTerm3() {
		return paymentTerm3;
	}


	public void setPaymentTerm3(int paymentTerm3) {
		this.paymentTerm3 = paymentTerm3;
	}


	public int getPaymentTerm4() {
		return paymentTerm4;
	}


	public void setPaymentTerm4(int paymentTerm4) {
		this.paymentTerm4 = paymentTerm4;
	}


	public String getSalesEmailId() {
		return salesEmailId;
	}


	public void setSalesEmailId(String salesEmailId) {
		this.salesEmailId = salesEmailId;
	}

	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	public String getSalesFirstName() {
		return salesFirstName;
	}


	public void setSalesFirstName(String salesFirstName) {
		this.salesFirstName = salesFirstName;
	}


	public String getSalesLastName() {
		return salesLastName;
	}


	public void setSalesLastName(String salesLastName) {
		this.salesLastName = salesLastName;
	}


	public long getSalesPhoneno() {
		return salesPhoneno;
	}


	public void setSalesPhoneno(long salesPhoneno) {
		this.salesPhoneno = salesPhoneno;
	}


	public String getCancelServiceReason() {
		return cancelServiceReason;
	}

	private int	txtStartMonth;

	private int txtStartDays;

	private int txtEndMonth;

	private int txtEndDays;

	private int txtExtMonth;

	private int txtExtDays;

	private String txtExtDate ="";
	public void setCancelServiceReason(String cancelServiceReason) {
		this.cancelServiceReason = cancelServiceReason;
	}

	//Meenakshi

	//Raghu: added for Product catelog update
	ArrayList<Integer> listChargeInfoIdList = null;
	ArrayList<Integer> listComponent = null;
	//Raghu

	private String circle="";//added on 09-jan-2013,CIRCLE Work
	
	private String category; //added on 30-apr-2013

	/*----By:Anil Kumar::Date::12-Mar-14::Added is partial_initiated flag]----*/
	private int is_partial_initiated;
	/*----Added is partial_initiated flag----*/
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCircle() {
		return circle;
	}


	public void setCircle(String circle) {
		this.circle = circle;
	}


	public int getCrmAccountNo() {
		return crmAccountNo;
	}


	public void setCrmAccountNo(int crmAccountNo) {
		this.crmAccountNo = crmAccountNo;
	}
	public String getDelayedTimeInDayes() {
		return delayedTimeInDayes;
	}
	public void setDelayedTimeInDayes(String delayedTimeInDayes) {
		this.delayedTimeInDayes = delayedTimeInDayes;
	}
	public String getLdDateClause() {
		return ldDateClause;
	}
	public void setLdDateClause(String ldDateClause) {
		this.ldDateClause = ldDateClause;
	}
	public String getLdPercentage() {
		return ldPercentage;
	}
	public void setLdPercentage(String ldPercentage) {
		this.ldPercentage = ldPercentage;
	}
	public String getMaxPercentage() {
		return maxPercentage;
	}
	public void setMaxPercentage(String maxPercentage) {
		this.maxPercentage = maxPercentage;
	}


	public int getIsUrgent() {
		return isUrgent;
	}


	public void setIsUrgent(int isUrgent) {
		this.isUrgent = isUrgent;
	}


	/*public String getBemailID() {
		return bemailID;
	}


	public void setBemailID(String bemailID) {
		this.bemailID = bemailID;
	}*/


	public String getDependentOnTax() {
		return dependentOnTax;
	}


	public void setDependentOnTax(String dependentOnTax) {
		this.dependentOnTax = dependentOnTax;
	}


	public String getTaxPkgInstId() {
		return taxPkgInstId;
	}


	public void setTaxPkgInstId(String taxPkgInstId) {
		this.taxPkgInstId = taxPkgInstId;
	}


	public Double getTaxRate() {
		return taxRate;
	}


	public void setTaxRate(Double taxRate) {
		this.taxRate = taxRate;
	}
	public Map<String, String> getParentChildRelationMap() {
		return parentChildRelationMap;
	}


	public void setParentChildRelationMap(Map<String, String> parentChildRelationMap) {
		this.parentChildRelationMap = parentChildRelationMap;
	}
	public int getPinCode() {
		return pinCode;
	}


	public void setPinCode(int pinCode) {
		this.pinCode = pinCode;
	}


	public int getPinCodeId() {
		return pinCodeId;
	}


	public void setPinCodeId(int pinCodeId) {
		this.pinCodeId = pinCodeId;
	}

//[003] End
	public String getChangeorderstatus() {
		return changeorderstatus;
	}


	public void setChangeorderstatus(String changeorderstatus) {
		this.changeorderstatus = changeorderstatus;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getServiceproductid() {
		return serviceproductid;
	}


	public void setServiceproductid(int serviceproductid) {
		this.serviceproductid = serviceproductid;
	}

	public String getRoleId() {
		return roleId;
	}


	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	//[00055] Start (Added By Ashutosh)
	public int getFileId() {
		return fileId;
	}


	public void setFileId(int fileId) {
		this.fileId = fileId;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public String getFileIds() {
		return fileIds;
	}


	public void setFileIds(String fileIds) {
		this.fileIds = fileIds;
	}
	
	public String getSelectDocStatus() {
		return selectDocStatus;
	}


	public void setSelectDocStatus(String selectDocStatus) {
		this.selectDocStatus = selectDocStatus;
	}


	public String getDocStatus() {
		return docStatus;
	}


	public void setDocStatus(String docStatus) {
		this.docStatus = docStatus;
	}
	
	public String getFinalTracking() {
		return finalTracking;
	}


	public void setFinalTracking(String finalTracking) {
		this.finalTracking = finalTracking;
	}
	//[00055] End 

public int getIsUDS() {
		return isUDS;
	}


	public void setIsUDS(int isUDS) {
		this.isUDS = isUDS;
	}

	public String getRoleName() {
		return roleName;
	}


	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}


	public int getCustomer_logicalSINumber() {
		return customer_logicalSINumber;
	}


	public void setCustomer_logicalSINumber(int customer_logicalSINumber) {
		this.customer_logicalSINumber = customer_logicalSINumber;
	}


	public int getLogicalSINumber() {
		return logicalSINumber;
	}


	public void setLogicalSINumber(int logicalSINumber) {
		this.logicalSINumber = logicalSINumber;
	}


	public String getNoOfDaysForDemo() {
		return noOfDaysForDemo;
	}


	public void setNoOfDaysForDemo(String noOfDaysForDemo) {
		this.noOfDaysForDemo = noOfDaysForDemo;
	}


	public String getChkIsDemo() {
		return chkIsDemo;
	}


	public void setChkIsDemo(String chkIsDemo) {
		this.chkIsDemo = chkIsDemo;
	}


	public int getHardwaretypeId() {
		return hardwaretypeId;
	}


	public void setHardwaretypeId(int hardwaretypeId) {
		this.hardwaretypeId = hardwaretypeId;
	}


	public String getHardwaretypeName() {
		return hardwaretypeName;
	}


	public void setHardwaretypeName(String hardwaretypeName) {
		this.hardwaretypeName = hardwaretypeName;
	}


	public String getTxtquotesNo() {
		return txtquotesNo;
	}


	public void setTxtquotesNo(String txtquotesNo) {
		this.txtquotesNo = txtquotesNo;
	}

	
	public String getM6ShortCode() {
		return m6ShortCode;
	}

	public void setM6ShortCode(String shortCode) {
		m6ShortCode = shortCode;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public int getRegionId() {
		return regionId;
	}

	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public int getZoneId() {
		return zoneId;
	}

	public void setZoneId(int zoneId) {
		this.zoneId = zoneId;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getBillingBCPId() {
		return billingBCPId;
	}

	public void setBillingBCPId(String billingBCPId) {
		this.billingBCPId = billingBCPId;
	}

	public int getProductCount() {
		return productCount;
	}

	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}
	public int getIsChargesValid() {
		return isChargesValid;
	}

	public void setIsChargesValid(int isChargesValid) {
		this.isChargesValid = isChargesValid;
	}
	public long getTaxationId() {
	return taxationId;
	}

	public void setTaxationId(long taxationId) {
		this.taxationId = taxationId;
	}

	public String getTaxationName() {
		return taxationName;
	}

	public void setTaxationName(String taxationName) {
		this.taxationName = taxationName;
	}

	public long getBillingLevelId() {
		return billingLevelId;
	}

	public void setBillingLevelId(long billingLevelId) {
		this.billingLevelId = billingLevelId;
	}

	public String getBillingLevelName() {
		return billingLevelName;
	}

	public void setBillingLevelName(String billingLevelName) {
		this.billingLevelName = billingLevelName;
	}

	public long getBillingFormatId() {
		return billingFormatId;
	}

	public void setBillingFormatId(long billingFormatId) {
		this.billingFormatId = billingFormatId;
	}

	public String getBillingFormatName() {
		return billingFormatName;
	}

	public void setBillingFormatName(String billingFormatName) {
		this.billingFormatName = billingFormatName;
	}

	public long getBillingTypeId() {
		return billingTypeId;
	}

	public void setBillingTypeId(long billingTypeId) {
		this.billingTypeId = billingTypeId;
	}

	public String getBillingTypeName() {
		return billingTypeName;
	}

	public void setBillingTypeName(String billingTypeName) {
		this.billingTypeName = billingTypeName;
	}

	public long getCreditPeriodId() {
		return creditPeriodId;
	}

	public void setCreditPeriodId(long creditPeriodId) {
		this.creditPeriodId = creditPeriodId;
	}

	public String getCreditPeriodName() {
		return creditPeriodName;
	}

	public void setCreditPeriodName(String creditPeriodName) {
		this.creditPeriodName = creditPeriodName;
	}
	
	public String getAddressIDList() {
		return addressIDList;
	}

	public void setAddressIDList(String addressIDList) {
		this.addressIDList = addressIDList;
	}

	public String getContactIDList() {
		return contactIDList;
	}

	public void setContactIDList(String contactIDList) {
		this.contactIDList = contactIDList;
	}
	
	ArrayList errors_Validation=new ArrayList();
	
	int status=0;
	
	public String[] getProdAttributeLabelArray() {
		return prodAttributeLabelArray;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public void setProdAttributeLabelArray(String[] prodAttributeLabelArray) {
		this.prodAttributeLabelArray = prodAttributeLabelArray;
	}

	public String[] getProdAttributeValueArray() {
		return prodAttributeValueArray;
	}

	public void setProdAttributeValueArray(String[] prodAttributeValueArray) {
		this.prodAttributeValueArray = prodAttributeValueArray;
	}

	public int getHdnSeriveAttCounter() {
		return hdnSeriveAttCounter;
	}

	public void setHdnSeriveAttCounter(int hdnSeriveAttCounter) {
		this.hdnSeriveAttCounter = hdnSeriveAttCounter;
	}

	public int getBcpID() {
		return bcpID;
	}

	public void setBcpID(int bcpID) {
		this.bcpID = bcpID;
	}

	public int getStoreID() {
		return storeID;
	}

	public void setStoreID(int storeID) {
		this.storeID = storeID;
	}

	public String getStoreName() {
		return StoreName;
	}

	public void setStoreName(String storeName) {
		StoreName = storeName;
	}

	public int getHdnServiceDetailID() {
		return hdnServiceDetailID;
	}

	public void setHdnServiceDetailID(int hdnServiceDetailID) {
		this.hdnServiceDetailID = hdnServiceDetailID;
	}

	public NewOrderDto()
	{
		logicalSINo="";
		custSINo="";
		effStartDate="";
		effEndDate="";
		attRemarks="";
	}
	
	public String getExpectedValue() {
		return expectedValue;
	}

	public void setExpectedValue(String expectedValue) {
		this.expectedValue = expectedValue;
	}


	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountManager() {
		return accountManager;
	}

	public void setAccountManager(String accountManager) {
		this.accountManager = accountManager;
	}


	public String getLob() {
		return lob;
	}

	public void setLob(String lob) {
		this.lob = lob;
	}

	public String getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getSpFirstname() {
		return spFirstname;
	}

	public void setSpFirstname(String spFirstname) {
		this.spFirstname = spFirstname;
	}

	public String getSpLastName() {
		return spLastName;
	}

	public void setSpLastName(String spLastName) {
		this.spLastName = spLastName;
	}

	public String getSpLEmail() {
		return spLEmail;
	}

	public void setSpLEmail(String spLEmail) {
		this.spLEmail = spLEmail;
	}


	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getAlisName() {
		return alisName;
	}

	public void setAlisName(String alisName) {
		this.alisName = alisName;
	}

	public int getAttributeID() {
		return attributeID;
	}

	public void setAttributeID(int attributeID) {
		this.attributeID = attributeID;
	}

	public String getAttributeLabel() {
		return attributeLabel;
	}

	public void setAttributeLabel(String attributeLabel) {
		this.attributeLabel = attributeLabel;
	}

	public String getAttributeValue() {
		return attributeValue;
	}

	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}

	public String getDispatchAddressName() {
		return dispatchAddressName;
	}
	public void setDispatchAddressName(String dispatchAddressName) {
		this.dispatchAddressName = dispatchAddressName;
	}
	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public int getAttMaxLength() {
		return attMaxLength;
	}

	public void setAttMaxLength(int attMaxLength) {
		this.attMaxLength = attMaxLength;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public int getCurrencyID() {
		return currencyID;
	}

	public void setCurrencyID(int currencyID) {
		this.currencyID = currencyID;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public int getMaxOrderNo() {
		return maxOrderNo;
	}

	public void setMaxOrderNo(int maxOrderNo) {
		this.maxOrderNo = maxOrderNo;
	}

	public int getSourceID() {
		return sourceID;
	}

	public void setSourceID(int sourceID) {
		this.sourceID = sourceID;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getQuoteNo() {
		return quoteNo;
	}

	public void setQuoteNo(String quoteNo) {
		this.quoteNo = quoteNo;
	}

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getStageName() {
		return stageName;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
	}

	public int getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(int poNumber) {
		this.poNumber = poNumber;
	}

	public int getUpdateType() {
		return updateType;
	}

	public void setUpdateType(int updateType) {
		this.updateType = updateType;
	}

	public String getSearchAccount() {
		return searchAccount;
	}

	public void setSearchAccount(String searchAccount) {
		this.searchAccount = searchAccount;
	}
	public String getCntEmail() {
		return cntEmail;
	}

	public void setCntEmail(String cntEmail) {
		this.cntEmail = cntEmail;
	}

	public String getContactCell() {
		return contactCell;
	}

	public void setContactCell(String contactCell) {
		this.contactCell = contactCell;
	}

	public String getContactFax() {
		return contactFax;
	}

	public void setContactFax(String contactFax) {
		this.contactFax = contactFax;
	}

	public String getContactType() {
		return contactType;
	}

	public void setContactType(String contactType) {
		this.contactType = contactType;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSaluation() {
		return saluation;
	}

	public void setSaluation(String saluation) {
		this.saluation = saluation;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public String getPinNo() {
		return pinNo;
	}

	public void setPinNo(String pinNo) {
		this.pinNo = pinNo;
	}


	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public int getContactUpdateType() {
		return contactUpdateType;
	}

	public void setContactUpdateType(int contactUpdateType) {
		this.contactUpdateType = contactUpdateType;
	}

	public Long getAddID() {
		return addID;
	}

	public void setAddID(Long addID) {
		this.addID = addID;
	}

	public Long getContactId() {
		return contactId;
	}

	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getServiceId() {
		return serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	public ArrayList<ServiceSubTypeDto> getServiceSubType() {
		return serviceSubType;
	}

	public void setServiceSubType(ArrayList<ServiceSubTypeDto> serviceSubType) {
		this.serviceSubType = serviceSubType;
	}

	public int getServiceSubtypeId() {
		return serviceSubtypeId;
	}

	public void setServiceSubtypeId(int serviceSubtypeId) {
		this.serviceSubtypeId = serviceSubtypeId;
	}

	public String getServiceSubTypeName() {
		return serviceSubTypeName;
	}

	public void setServiceSubTypeName(String serviceSubTypeName) {
		this.serviceSubTypeName = serviceSubTypeName;
	}

	public int getServiceTypeId() {
		return serviceTypeId;
	}

	public void setServiceTypeId(int serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}

	public String getServiceTypeName() {
		return serviceTypeName;
	}

	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
	}

	public int getSubProductId() {
		return subProductId;
	}

	public void setSubProductId(int subProductId) {
		this.subProductId = subProductId;
	}

	public String getSubProductName() {
		return subProductName;
	}

	public void setSubProductName(String subProductName) {
		this.subProductName = subProductName;
	}

	public String getAttRemarks() {
		return attRemarks;
	}

	public void setAttRemarks(String attRemarks) {
		this.attRemarks = attRemarks;
	}

	public String getCustSINo() {
		return custSINo;
	}

	public void setCustSINo(String custSINo) {
		this.custSINo = custSINo;
	}

	public String getEffEndDate() {
		return effEndDate;
	}

	public void setEffEndDate(String effEndDate) {
		this.effEndDate = effEndDate;
	}

	public String getEffStartDate() {
		return effStartDate;
	}

	public void setEffStartDate(String effStartDate) {
		this.effStartDate = effStartDate;
	}

	public String getLogicalSINo() {
		return logicalSINo;
	}

	public void setLogicalSINo(String logicalSINo) {
		this.logicalSINo = logicalSINo;
	}

	public String getContactTypeId() {
		return contactTypeId;
	}

	public void setContactTypeId(String contactTypeId) {
		this.contactTypeId = contactTypeId;
	}

	public String getContactTypeName() {
		return contactTypeName;
	}

	public void setContactTypeName(String contactTypeName) {
		this.contactTypeName = contactTypeName;
	}

	public ArrayList getContactTypes() {
		return contactTypes;
	}

	public void setContactTypes(ArrayList contactTypes) {
		this.contactTypes = contactTypes;
	}

	public String getServiceDetDescription() {
		return serviceDetDescription;
	}

	public void setServiceDetDescription(String serviceDetDescription) {
		this.serviceDetDescription = serviceDetDescription;
	}

	public String getServiceTypeDescription() {
		return serviceTypeDescription;
	}

	public void setServiceTypeDescription(String serviceTypeDescription) {
		this.serviceTypeDescription = serviceTypeDescription;
	}

	public int getServiceDetailID() {
		return serviceDetailID;
	}

	public void setServiceDetailID(int serviceDetailID) {
		this.serviceDetailID = serviceDetailID;
	}

	public String getProdAlisName() {
		return prodAlisName;
	}

	public void setProdAlisName(String prodAlisName) {
		this.prodAlisName = prodAlisName;
	}

	public int getProdAttMaxLength() {
		return prodAttMaxLength;
	}

	public void setProdAttMaxLength(int prodAttMaxLength) {
		this.prodAttMaxLength = prodAttMaxLength;
	}

	public int getProdAttributeID() {
		return prodAttributeID;
	}

	public void setProdAttributeID(int prodAttributeID) {
		this.prodAttributeID = prodAttributeID;
	}

	public String getProdAttributeLabel() {
		return prodAttributeLabel;
	}

	public void setProdAttributeLabel(String prodAttributeLabel) {
		this.prodAttributeLabel = prodAttributeLabel;
	}

	public String getProdAttributeValue() {
		return prodAttributeValue;
	}

	public void setProdAttributeValue(String prodAttributeValue) {
		this.prodAttributeValue = prodAttributeValue;
	}

	public String getProdDataType() {
		return prodDataType;
	}

	public void setProdDataType(String prodDataType) {
		this.prodDataType = prodDataType;
	}

	public String getProdExpectedValue() {
		return prodExpectedValue;
	}

	public void setProdExpectedValue(String prodExpectedValue) {
		this.prodExpectedValue = prodExpectedValue;
	}

	public String getContractEndDate() {
		return contractEndDate;
	}

	public void setContractEndDate(String contractEndDate) {
		this.contractEndDate = contractEndDate;
	}

	public String getContractStartDate() {
		return contractStartDate;
	}

	public void setContractStartDate(String contractStartDate) {
		this.contractStartDate = contractStartDate;
	}

	public String getPeriodsInMonths() {
		return periodsInMonths;
	}

	public void setPeriodsInMonths(String periodsInMonths) {
		this.periodsInMonths = periodsInMonths;
	}

	public String getPoDate() {
		return poDate;
	}

	public void setPoDate(String poDate) {
		this.poDate = poDate;
	}

	public String getPoDetailNo() {
		return poDetailNo;
	}

	public void setPoDetailNo(String poDetailNo) {
		this.poDetailNo = poDetailNo;
	}

	public String getPoReceiveDate() {
		return poReceiveDate;
	}

	public void setPoReceiveDate(String poReceiveDate) {
		this.poReceiveDate = poReceiveDate;
	}

	public String getTotalPoAmt() {
		return totalPoAmt;
	}

	public void setTotalPoAmt(String totalPoAmt) {
		this.totalPoAmt = totalPoAmt;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public int getEntityID() {
		return entityID;
	}

	public void setEntityID(int entityID) {
		this.entityID = entityID;
	}

	public int getLicCompanyID() {
		return licCompanyID;
	}

	public void setLicCompanyID(int licCompanyID) {
		this.licCompanyID = licCompanyID;
	}

	public String getLicCompanyName() {
		return licCompanyName;
	}

	public void setLicCompanyName(String licCompanyName) {
		this.licCompanyName = licCompanyName;
	}

	public int getDispatchAddressID() {
		return dispatchAddressID;
	}

	public void setDispatchAddressID(int dispatchAddressID) {
		this.dispatchAddressID = dispatchAddressID;
	}

	public String getDispatchAddress1() {
		return dispatchAddress1;
	}

	public void setDispatchAddress1(String dispatchAddress1) {
		this.dispatchAddress1 = dispatchAddress1;
	}

	public String getDispatchAddress2() {
		return dispatchAddress2;
	}

	public void setDispatchAddress2(String dispatchAddress2) {
		this.dispatchAddress2 = dispatchAddress2;
	}

	public String getDispatchAddress3() {
		return dispatchAddress3;
	}

	public void setDispatchAddress3(String dispatchAddress3) {
		this.dispatchAddress3 = dispatchAddress3;
	}

	public String getDispatchCityName() {
		return dispatchCityName;
	}

	public void setDispatchCityName(String dispatchCityName) {
		this.dispatchCityName = dispatchCityName;
	}

	public String getDispatchCountyName() {
		return dispatchCountyName;
	}

	public void setDispatchCountyName(String dispatchCountyName) {
		this.dispatchCountyName = dispatchCountyName;
	}

	public String getDispatchPhoneNo() {
		return dispatchPhoneNo;
	}

	public void setDispatchPhoneNo(String dispatchPhoneNo) {
		this.dispatchPhoneNo = dispatchPhoneNo;
	}

	public String getDispatchPinNo() {
		return dispatchPinNo;
	}

	public void setDispatchPinNo(String dispatchPinNo) {
		this.dispatchPinNo = dispatchPinNo;
	}

	public String getDispatchStateName() {
		return dispatchStateName;
	}

	public void setDispatchStateName(String dispatchStateName) {
		this.dispatchStateName = dispatchStateName;
	}

	public String getDispatchEmail() {
		return dispatchEmail;
	}
	public void setDispatchEmail(String dispatchEmail) {
		this.dispatchEmail = dispatchEmail;
	}
	public String getDispatchAddress4() {
		return dispatchAddress4;
	}
	public void setDispatchAddress4(String dispatchAddress4) {
		this.dispatchAddress4 = dispatchAddress4;
	}
	
	public String getDispatchLSTNo() {
		return dispatchLSTNo;
	}
	public void setDispatchLSTNo(String dispatchLSTNo) {
		this.dispatchLSTNo = dispatchLSTNo;
	}
	public String getDispatchDesignation() {
		return dispatchDesignation;
	}
	public void setDispatchDesignation(String dispatchDesignation) {
		this.dispatchDesignation = dispatchDesignation;
	}
	public String getPtxtPAddress() {
		return ptxtPAddress;
	}

	public void setPtxtPAddress(String ptxtPAddress) {
		this.ptxtPAddress = ptxtPAddress;
	}

	public String getPaddress1() {
		return paddress1;
	}

	public void setPaddress1(String paddress1) {
		this.paddress1 = paddress1;
	}

	public String getPaddress2() {
		return paddress2;
	}

	public void setPaddress2(String paddress2) {
		this.paddress2 = paddress2;
	}

	public String getPaddress3() {
		return paddress3;
	}

	public void setPaddress3(String paddress3) {
		this.paddress3 = paddress3;
	}

	public String getPaddress4() {
		return paddress4;
	}

	public void setPaddress4(String paddress4) {
		this.paddress4 = paddress4;
	}

	public String getPcity() {
		return pcity;
	}

	public void setPcity(String pcity) {
		this.pcity = pcity;
	}

	public String getPcountry() {
		return pcountry;
	}

	public void setPcountry(String pcountry) {
		this.pcountry = pcountry;
	}

	public String getPpincode() {
		return ppincode;
	}

	public void setPpincode(String ppincode) {
		this.ppincode = ppincode;
	}

	public String getPstate() {
		return pstate;
	}

	public void setPstate(String pstate) {
		this.pstate = pstate;
	}

	public String getSaddress1() {
		return saddress1;
	}

	public void setSaddress1(String saddress1) {
		this.saddress1 = saddress1;
	}

	public String getSaddress2() {
		return saddress2;
	}

	public void setSaddress2(String saddress2) {
		this.saddress2 = saddress2;
	}

	public String getSaddress3() {
		return saddress3;
	}

	public void setSaddress3(String saddress3) {
		this.saddress3 = saddress3;
	}

	public String getSaddress4() {
		return saddress4;
	}

	public void setSaddress4(String saddress4) {
		this.saddress4 = saddress4;
	}

	public String getScity() {
		return scity;
	}

	public void setScity(String scity) {
		this.scity = scity;
	}

	public String getScountry() {
		return scountry;
	}

	public void setScountry(String scountry) {
		this.scountry = scountry;
	}

	public String getSpincode() {
		return spincode;
	}

	public void setSpincode(String spincode) {
		this.spincode = spincode;
	}

	public String getSstate() {
		return sstate;
	}

	public void setSstate(String sstate) {
		this.sstate = sstate;
	}

	public int getPlocationCode() {
		return plocationCode;
	}

	public void setPlocationCode(int plocationCode) {
		this.plocationCode = plocationCode;
	}

	public int getSlocationCode() {
		return slocationCode;
	}

	public void setSlocationCode(int slocationCode) {
		this.slocationCode = slocationCode;
	}

	public String getStxtSAddress() {
		return stxtSAddress;
	}

	public void setStxtSAddress(String stxtSAddress) {
		this.stxtSAddress = stxtSAddress;
	}

	public String[] getProdAttExptdValue() {
		return prodAttExptdValue;
	}

	public void setProdAttExptdValue(String[] prodAttExptdValue) {
		this.prodAttExptdValue = prodAttExptdValue;
	}

	public String[] getProdAttID() {
		return prodAttID;
	}

	public void setProdAttID(String[] prodAttID) {
		this.prodAttID = prodAttID;
	}

	public String[] getNewProdAttValue() {
		return newProdAttValue;
	}


	public void setNewProdAttValue(String[] newProdAttValue) {
		this.newProdAttValue = newProdAttValue;
	}


	public String[] getAttributeValuIDs() {
		return attributeValuIDs;
	}


	public void setAttributeValuIDs(String[] attributeValuIDs) {
		this.attributeValuIDs = attributeValuIDs;
	}


	public String[] getProdAttName() {
		return prodAttName;
	}

	public void setProdAttName(String[] prodAttName) {
		this.prodAttName = prodAttName;
	}

	public String[] getProdAttValue() {
		return prodAttValue;
	}

	public void setProdAttValue(String[] prodAttValue) {
		this.prodAttValue = prodAttValue;
	}

	public String getBillingformat() {
		return billingformat;
	}

	public void setBillingformat(String billingformat) {
		this.billingformat = billingformat;
	}

	public String getBillingLevel() {
		return billingLevel;
	}

	public void setBillingLevel(String billingLevel) {
		this.billingLevel = billingLevel;
	}

	public String getBillingMode() {
		return billingMode;
	}

	public void setBillingMode(String billingMode) {
		this.billingMode = billingMode;
	}

	public String getBillingPODate() {
		return billingPODate;
	}

	public void setBillingPODate(String billingPODate) {
		this.billingPODate = billingPODate;
	}

	public int getCommitmentPeriod() {
		return commitmentPeriod;
	}

	public void setCommitmentPeriod(int commitmentPeriod) {
		this.commitmentPeriod = commitmentPeriod;
	}

	public int getContractMonths() {
		return contractMonths;
	}

	public void setContractMonths(int contractMonths) {
		this.contractMonths = contractMonths;
	}

	public int getContractPeriod() {
		return contractPeriod;
	}

	public void setContractPeriod(int contractPeriod) {
		this.contractPeriod = contractPeriod;
	}

	public int getCreditPeriod() {
		return creditPeriod;
	}

	public void setCreditPeriod(int creditPeriod) {
		this.creditPeriod = creditPeriod;
	}

	public String getFormAvailable() {
		return formAvailable;
	}

	public void setFormAvailable(String formAvailable) {
		this.formAvailable = formAvailable;
	}

	public String getHardwareType() {
		return hardwareType;
	}

	public void setHardwareType(String hardwareType) {
		this.hardwareType = hardwareType;
	}

	public int getLicenceCoID() {
		return licenceCoID;
	}

	public void setLicenceCoID(int licenceCoID) {
		this.licenceCoID = licenceCoID;
	}

	public String getPenaltyClause() {
		return penaltyClause;
	}

	public void setPenaltyClause(String penaltyClause) {
		this.penaltyClause = penaltyClause;
	}

	public int getPodetailID() {
		return podetailID;
	}

	public void setPodetailID(int podetailID) {
		this.podetailID = podetailID;
	}

	public String getSaleNature() {
		return saleNature;
	}

	public void setSaleNature(String saleNature) {
		this.saleNature = saleNature;
	}

	public String getSaleType() {
		return saleType;
	}

	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}

	public int getSelectedDispatchID() {
		return selectedDispatchID;
	}

	public void setSelectedDispatchID(int selectedDispatchID) {
		this.selectedDispatchID = selectedDispatchID;
	}

	public int getSelectedPNLocation() {
		return selectedPNLocation;
	}

	public void setSelectedPNLocation(int selectedPNLocation) {
		this.selectedPNLocation = selectedPNLocation;
	}

	public int getSelectedPriLocType() {
		return selectedPriLocType;
	}

	public void setSelectedPriLocType(int selectedPriLocType) {
		this.selectedPriLocType = selectedPriLocType;
	}

	public int getSelectedPrimaryBCP() {
		return selectedPrimaryBCP;
	}

	public void setSelectedPrimaryBCP(int selectedPrimaryBCP) {
		this.selectedPrimaryBCP = selectedPrimaryBCP;
	}

	public int getSelectedSecBCP() {
		return selectedSecBCP;
	}

	public void setSelectedSecBCP(int selectedSecBCP) {
		this.selectedSecBCP = selectedSecBCP;
	}

	public int getSelectedSecLocType() {
		return selectedSecLocType;
	}

	public void setSelectedSecLocType(int selectedSecLocType) {
		this.selectedSecLocType = selectedSecLocType;
	}

	public int getSelectedSNLocation() {
		return selectedSNLocation;
	}

	public void setSelectedSNLocation(int selectedSNLocation) {
		this.selectedSNLocation = selectedSNLocation;
	}

	public int getSelectedStoreID() {
		return selectedStoreID;
	}

	public void setSelectedStoreID(int selectedStoreID) {
		this.selectedStoreID = selectedStoreID;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getTaxation() {
		return taxation;
	}

	public void setTaxation(String taxation) {
		this.taxation = taxation;
	}


	public String getChargeFrequency() {
		return chargeFrequency;
	}

	public void setChargeFrequency(String chargeFrequency) {
		this.chargeFrequency = chargeFrequency;
	}


	public double getChargeFrequencyAmt() {
		return chargeFrequencyAmt;
	}

	public void setChargeFrequencyAmt(double chargeFrequencyAmt) {
		this.chargeFrequencyAmt = chargeFrequencyAmt;
	}

	public String getChargeName() {
		return chargeName;
	}

	public void setChargeName(String chargeName) {
		this.chargeName = chargeName;
	}

	public int getChargePeriod() {
		return chargePeriod;
	}

	public void setChargePeriod(int chargePeriod) {
		this.chargePeriod = chargePeriod;
	}

	public int getChargeType() {
		return chargeType;}
	

	public void setChargeType(int chargeType) {
		this.chargeType = chargeType;}
	public String getEndDateLogic() {
		return endDateLogic;
	}

	public void setEndDateLogic(String endDateLogic) {
		this.endDateLogic = endDateLogic;
	}

	public String getStartDateLogic() {
		return startDateLogic;
	}

	public void setStartDateLogic(String startDateLogic) {
		this.startDateLogic = startDateLogic;
	}

	public String getMsgOut() {
		return msgOut;
	}

	public void setMsgOut(String msgOut) {
		this.msgOut = msgOut;
	}

	public ArrayList getErrors() {
		return errors;
	}

	public void setErrors(ArrayList errors) {
		this.errors = errors;
	}

	public int getBillingType() {
		return billingType;
	}

	public void setBillingType(int billingType) {
		this.billingType = billingType;
	}

	public long getAttMasterId() {
		return attMasterId;
	}

	public void setAttMasterId(long attMasterId) {
		this.attMasterId = attMasterId;
	}

	public String getAttMasterName() {
		return attMasterName;
	}

	public void setAttMasterName(String attMasterName) {
		this.attMasterName = attMasterName;
	}

	public String getPoSearchDetailNumber() {
		return poSearchDetailNumber;
	}

	public void setPoSearchDetailNumber(String poSearchDetailNumber) {
		this.poSearchDetailNumber = poSearchDetailNumber;
	}

	public ArrayList getErrors_Validation() {
		return errors_Validation;
	}

	public void setErrors_Validation(ArrayList errors_Validation) {
		this.errors_Validation = errors_Validation;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String[] getProdAttributeDataTypeArray() {
		return prodAttributeDataTypeArray;
	}

	public void setProdAttributeDataTypeArray(String[] prodAttributeDataTypeArray) {
		this.prodAttributeDataTypeArray = prodAttributeDataTypeArray;
	}

	public String[] getProdAttributeDisplayLabelArray() {
		return prodAttributeDisplayLabelArray;
	}

	public void setProdAttributeDisplayLabelArray(
			String[] prodAttributeDisplayLabelArray) {
		this.prodAttributeDisplayLabelArray = prodAttributeDisplayLabelArray;
	}

	public int getPrdAttrEntered() {
		return prdAttrEntered;
	}

	public void setPrdAttrEntered(int prdAttrEntered) {
		this.prdAttrEntered = prdAttrEntered;
	}

	public int getServAttrEntered() {
		return servAttrEntered;
	}

	public void setServAttrEntered(int servAttrEntered) {
		this.servAttrEntered = servAttrEntered;
	}

	public ArrayList<TreeViewDto> getLstTreeItems() {
		return lstTreeItems;
	}

	public void setLstTreeItems(ArrayList<TreeViewDto> lstTreeItems) {
		this.lstTreeItems = lstTreeItems;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public int getServiceProductID() {
		return serviceProductID;
	}

	public void setServiceProductID(int serviceProductID) {
		this.serviceProductID = serviceProductID;
	}

	public String getProdAttVal() {
		return prodAttVal;
	}

	public void setProdAttVal(String prodAttVal) {
		this.prodAttVal = prodAttVal;
	}
	
	public String getNewProdAttVal() {
		return newProdAttVal;
	}


	public void setNewProdAttVal(String newProdAttVal) {
		this.newProdAttVal = newProdAttVal;
	}


	public int getBillingInfoID() {
		return billingInfoID;
	}

	public void setBillingInfoID(int billingInfoID) {
		this.billingInfoID = billingInfoID;
	}

	public int getChargeInfoID() {
		return chargeInfoID;
	}

	public void setChargeInfoID(int chargeInfoID) {
		this.chargeInfoID = chargeInfoID;
	}

	public int getHardwareDetailID() {
		return hardwareDetailID;
	}

	public void setHardwareDetailID(int hardwareDetailID) {
		this.hardwareDetailID = hardwareDetailID;
	}

	public int getLocationInfoID() {
		return locationInfoID;
	}

	public void setLocationInfoID(int locationInfoID) {
		this.locationInfoID = locationInfoID;
	}

	public int getChargeTypeID() {
		return chargeTypeID;
	}

	public void setChargeTypeID(int chargeTypeID) {
		this.chargeTypeID = chargeTypeID;
	}

	public String getChargeTypeName() {
		return chargeTypeName;
	}

	public void setChargeTypeName(String chargeTypeName) {
		this.chargeTypeName = chargeTypeName;
	}

	public int getFactor() {
		return factor;
	}

	public void setFactor(int factor) {
		this.factor = factor;
	}

	public int getFrequencyID() {
		return frequencyID;
	}

	public void setFrequencyID(int frequencyID) {
		this.frequencyID = frequencyID;
	}

	public String getFrequencyName() {
		return frequencyName;
	}

	public void setFrequencyName(String frequencyName) {
		this.frequencyName = frequencyName;
	}

	public int getProductCatelogEntered() {
		return productCatelogEntered;
	}

	public void setProductCatelogEntered(int productCatelogEntered) {
		this.productCatelogEntered = productCatelogEntered;
	}
   public String getCurShortCode() {
		return curShortCode;
	}

	public void setCurShortCode(String curShortCode) {
		this.curShortCode = curShortCode;
	}


	

	public double getTotalAmountIncludingCurrent() {
		return totalAmountIncludingCurrent;
	}

	public void setTotalAmountIncludingCurrent(double totalAmountIncludingCurrent) {
		this.totalAmountIncludingCurrent = totalAmountIncludingCurrent;
	}

	public int getBillingInfoValue() {
		return billingInfoValue;
	}

	public void setBillingInfoValue(int billingInfoValue) {
		this.billingInfoValue = billingInfoValue;
	}

	public int getChargeInfoValue() {
		return chargeInfoValue;
	}

	public void setChargeInfoValue(int chargeInfoValue) {
		this.chargeInfoValue = chargeInfoValue;
	}

	public int getLocationInfoValue() {
		return locationInfoValue;
	}

	public void setLocationInfoValue(int locationInfoValue) {
		this.locationInfoValue = locationInfoValue;
	}

	public int getServiceInfoValue() {
		return serviceInfoValue;
	}

	public void setServiceInfoValue(int serviceInfoValue) {
		this.serviceInfoValue = serviceInfoValue;
	}

	public int getHardwareInfoValue() {
		return hardwareInfoValue;
	}

	public void setHardwareInfoValue(int hardwareInfoValue) {
		this.hardwareInfoValue = hardwareInfoValue;
	}

	public String getMandatory() {
		return mandatory;
	}

	public void setMandatory(String mandatory) {
		this.mandatory = mandatory;
	}
	public double getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	public String getChargeAmount_String() {
		return chargeAmount_String;
	}

	public void setChargeAmount_String(String chargeAmount_String) {
		this.chargeAmount_String = chargeAmount_String;
	}

	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}

	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
	}

	public String getChargeFrequencyAmt_String() {
		return chargeFrequencyAmt_String;
	}

	public void setChargeFrequencyAmt_String(String chargeFrequencyAmt_String) {
		this.chargeFrequencyAmt_String = chargeFrequencyAmt_String;
	}

	public String getBaddress1() {
		return baddress1;
	}

	public void setBaddress1(String baddress1) {
		this.baddress1 = baddress1;
	}

	public String getBaddress2() {
		return baddress2;
	}

	public void setBaddress2(String baddress2) {
		this.baddress2 = baddress2;
	}

	public String getBaddress3() {
		return baddress3;
	}

	public void setBaddress3(String baddress3) {
		this.baddress3 = baddress3;
	}

	public String getBaddress4() {
		return baddress4;
	}

	public void setBaddress4(String baddress4) {
		this.baddress4 = baddress4;
	}

	public String getBcity() {
		return bcity;
	}

	public void setBcity(String bcity) {
		this.bcity = bcity;
	}

	public String getBcountry() {
		return bcountry;
	}

	public void setBcountry(String bcountry) {
		this.bcountry = bcountry;
	}

	public String getBpincode() {
		return bpincode;
	}

	public void setBpincode(String bpincode) {
		this.bpincode = bpincode;
	}

	public String getBstate() {
		return bstate;
	}

	public void setBstate(String bstate) {
		this.bstate = bstate;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String[] getProdAttMandatory() {
		return prodAttMandatory;
	}

	public void setProdAttMandatory(String[] prodAttMandatory) {
		this.prodAttMandatory = prodAttMandatory;
	}

	public String[] getProdAttributeIsMandatory() {
		return prodAttributeIsMandatory;
	}

	public void setProdAttributeIsMandatory(String[] prodAttributeIsMandatory) {
		this.prodAttributeIsMandatory = prodAttributeIsMandatory;
	}

	public String getProductlevel() {
		return productlevel;
	}

	public void setProductlevel(String productlevel) {
		this.productlevel = productlevel;
	}

	public ArrayList<NewOrderDto> getServiceProducts() {
		return serviceProducts;
	}

	public void setServiceProducts(ArrayList<NewOrderDto> serviceProducts) {
		this.serviceProducts = serviceProducts;
	}

	public String getParentServiceProductId() {
		return parentServiceProductId;
	}

	public void setParentServiceProductId(String parentServiceProductId) {
		this.parentServiceProductId = parentServiceProductId;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the changeType
	 */
	public String getChangeType() {
		return changeType;
	}

	/**
	 * @param changeType the changeType to set
	 */
	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	

	/**
	 * @return the changeTypeName
	 */
	public String getChangeTypeName() {
		return changeTypeName;
	}

	/**
	 * @param changeTypeName the changeTypeName to set
	 */
	public void setChangeTypeName(String changeTypeName) {
		this.changeTypeName = changeTypeName;
	}

	public int getChangeTypeId() {
		return changeTypeId;
	}

	public void setChangeTypeId(int changeTypeId) {
		this.changeTypeId = changeTypeId;
	}

	public String getAccountIDString() {
		return accountIDString;
	}

	public void setAccountIDString(String accountIDString) {
		this.accountIDString = accountIDString;
	}


	public String[] getServiceProductIds() {
		return serviceProductIds;
	}

	public void setServiceProductIds(String[] serviceProductIds) {
		this.serviceProductIds = serviceProductIds;
	}

	public String getServiceIdString() {
		return serviceIdString;
	}

	public void setServiceIdString(String serviceIdString) {
		this.serviceIdString = serviceIdString;
	}

	public String getHdnOrderNo() {
		return hdnOrderNo;
	}

	public void setHdnOrderNo(String hdnOrderNo) {
		this.hdnOrderNo = hdnOrderNo;
	}

	public int getParent_serviceProductID() {
		return parent_serviceProductID;
	}

	public void setParent_serviceProductID(int parent_serviceProductID) {
		this.parent_serviceProductID = parent_serviceProductID;
	}

	public ArrayList<NewOrderDto> getServiceSummary() {
		return serviceSummary;
	}

	public void setServiceSummary(ArrayList<NewOrderDto> serviceSummary) {
		this.serviceSummary = serviceSummary;
	}

	public String getServiceSummaryText() {
		return serviceSummaryText;
	}

	public void setServiceSummaryText(String serviceSummaryText) {
		this.serviceSummaryText = serviceSummaryText;
	}

	public String getServiceSummaryValues() {
		return serviceSummaryValues;
	}

	public void setServiceSummaryValues(String serviceSummaryValues) {
		this.serviceSummaryValues = serviceSummaryValues;
	}

	public String getBcpName() {
		return bcpName;
	}

	public void setBcpName(String bcpName) {
		this.bcpName = bcpName;
	}

	public String getNwkLocationName() {
		return nwkLocationName;
	}

	public void setNwkLocationName(String nwkLocationName) {
		this.nwkLocationName = nwkLocationName;
	}

	public String getCopyProductValues() {
		return copyProductValues;
	}

	public void setCopyProductValues(String copyProductValues) {
		this.copyProductValues = copyProductValues;
	}

	public String getServiceChildId() {
		return serviceChildId;
	}

	public void setServiceChildId(String serviceChildId) {
		this.serviceChildId = serviceChildId;
	}

	public String getServiceParentId() {
		return serviceParentId;
	}

	public void setServiceParentId(String serviceParentId) {
		this.serviceParentId = serviceParentId;
	}

	public String getTreeViewURL() {
		return treeViewURL;
	}

	public void setTreeViewURL(String treeViewURL) {
		this.treeViewURL = treeViewURL;
	}

	public String getStdReasonName() {
		return stdReasonName;
	}

	public void setStdReasonName(String stdReasonName) {
		this.stdReasonName = stdReasonName;
	}

	public long getStdReasonId() {
		return stdReasonId;
	}

	public void setStdReasonId(long stdReasonId) {
		this.stdReasonId = stdReasonId;
	}

	public String[] getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String[] effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String[] getRateChange() {
		return rateChange;
	}

	public void setRateChange(String[] rateChange) {
		this.rateChange = rateChange;
	}

	public String[] getStdReason() {
		return stdReason;
	}

	public void setStdReason(String[] stdReason) {
		this.stdReason = stdReason;
	}

	public int getSubChangeTypeId() {
		return subChangeTypeId;
	}

	public void setSubChangeTypeId(int subChangeTypeId) {
		this.subChangeTypeId = subChangeTypeId;
	}

	public String getSubChangeTypeName() {
		return subChangeTypeName;
	}

	public void setSubChangeTypeName(String subChangeTypeName) {
		this.subChangeTypeName = subChangeTypeName;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public long getProjectManagerID() {
		return projectManagerID;
	}

	public void setProjectManagerID(long projectManagerID) {
		this.projectManagerID = projectManagerID;
	}

	public String getEndHWDateLogic() {
		return endHWDateLogic;
	}

	public void setEndHWDateLogic(String endHWDateLogic) {
		this.endHWDateLogic = endHWDateLogic;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStartHWDateLogic() {
		return startHWDateLogic;
	}

	public void setStartHWDateLogic(String startHWDateLogic) {
		this.startHWDateLogic = startHWDateLogic;
	}

	public int getWarrentyMonths() {
		return warrentyMonths;
	}

	public void setWarrentyMonths(int warrentyMonths) {
		this.warrentyMonths = warrentyMonths;
	}

	public int getDefaultDispatchAddForAccID() {
		return defaultDispatchAddForAccID;
	}

	public void setDefaultDispatchAddForAccID(int defaultDispatchAddForAccID) {
		this.defaultDispatchAddForAccID = defaultDispatchAddForAccID;
	}
	public ArrayList<ChargesDetailDto> getChargesDetails() {
		return chargesDetails;
	}

	public void setChargesDetails(ArrayList<ChargesDetailDto> chargesDetails) {
		this.chargesDetails = chargesDetails;
	}

	public String getLstDate() {
		return lstDate;
	}

	public void setLstDate(String lstDate) {
		this.lstDate = lstDate;
	}

	public String getDefaultPO() {
		return defaultPO;
	}

	public void setDefaultPO(String defaultPO) {
		this.defaultPO = defaultPO;
	}

	public String getPoDemoContractPeriod() {
		return poDemoContractPeriod;
	}

	public void setPoDemoContractPeriod(String poDemoContractPeriod) {
		this.poDemoContractPeriod = poDemoContractPeriod;
	}

	public String getPoEmailId() {
		return poEmailId;
	}

	public void setPoEmailId(String poEmailId) {
		this.poEmailId = poEmailId;
	}

	public String getPoIssueBy() {
		return poIssueBy;
	}

	public void setPoIssueBy(String poIssueBy) {
		this.poIssueBy = poIssueBy;
	}

	public String getPoRemarks() {
		return poRemarks;
	}

	public void setPoRemarks(String poRemarks) {
		this.poRemarks = poRemarks;
	}
	public int getLstNo() {
		return lstNo;
	}

	public void setLstNo(int lstNo) {
		this.lstNo = lstNo;
	}


	public int getIsReqEndDate() {
		return isReqEndDate;
	}


	public void setIsReqEndDate(int isReqEndDate) {
		this.isReqEndDate = isReqEndDate;
	}


	public int getIsReqEndHWDateLogic() {
		return isReqEndHWDateLogic;
	}


	public void setIsReqEndHWDateLogic(int isReqEndHWDateLogic) {
		this.isReqEndHWDateLogic = isReqEndHWDateLogic;
	}


	public int getIsReqStartDate() {
		return isReqStartDate;
	}


	public void setIsReqStartDate(int isReqStartDate) {
		this.isReqStartDate = isReqStartDate;
	}


	public int getIsReqStartHWDateLogic() {
		return isReqStartHWDateLogic;
	}


	public void setIsReqStartHWDateLogic(int isReqStartHWDateLogic) {
		this.isReqStartHWDateLogic = isReqStartHWDateLogic;
	}


	public int getIsReqWarrentyMonths() {
		return isReqWarrentyMonths;
	}


	public void setIsReqWarrentyMonths(int isReqWarrentyMonths) {
		this.isReqWarrentyMonths = isReqWarrentyMonths;
	}


	public String getCurrentRole() {
		return currentRole;
	}


	public void setCurrentRole(String currentRole) {
		this.currentRole = currentRole;
	}


	public String getFeildName() {
		return feildName;
	}


	public void setFeildName(String feildName) {
		this.feildName = feildName;
	}


	public String getFieldLabel() {
		return fieldLabel;
	}


	public void setFieldLabel(String fieldLabel) {
		this.fieldLabel = fieldLabel;
	}


	public String getIsMand() {
		return isMand;
	}


	public void setIsMand(String isMand) {
		this.isMand = isMand;
	}


	public String getIsReadOnly() {
		return isReadOnly;
	}


	public void setIsReadOnly(String isReadOnly) {
		this.isReadOnly = isReadOnly;
	}


	public int getIsReqAddress1() {
		return isReqAddress1;
	}


	public void setIsReqAddress1(int isReqAddress1) {
		this.isReqAddress1 = isReqAddress1;
	}


	public int getIsReqAddress2() {
		return isReqAddress2;
	}


	public void setIsReqAddress2(int isReqAddress2) {
		this.isReqAddress2 = isReqAddress2;
	}


	public int getIsReqAddress3() {
		return isReqAddress3;
	}


	public void setIsReqAddress3(int isReqAddress3) {
		this.isReqAddress3 = isReqAddress3;
	}


	public int getIsReqBbPrimaryBCPId() {
		return isReqBbPrimaryBCPId;
	}


	public void setIsReqBbPrimaryBCPId(int isReqBbPrimaryBCPId) {
		this.isReqBbPrimaryBCPId = isReqBbPrimaryBCPId;
	}


	public int getIsReqBillingBCPId() {
		return isReqBillingBCPId;
	}


	public void setIsReqBillingBCPId(int isReqBillingBCPId) {
		this.isReqBillingBCPId = isReqBillingBCPId;
	}


	public int getIsReqCityName() {
		return isReqCityName;
	}


	public void setIsReqCityName(int isReqCityName) {
		this.isReqCityName = isReqCityName;
	}


	public int getIsReqCntEmail() {
		return isReqCntEmail;
	}


	public void setIsReqCntEmail(int isReqCntEmail) {
		this.isReqCntEmail = isReqCntEmail;
	}


	public int getIsReqContactCell() {
		return isReqContactCell;
	}


	public void setIsReqContactCell(int isReqContactCell) {
		this.isReqContactCell = isReqContactCell;
	}


	public int getIsReqContactFax() {
		return isReqContactFax;
	}


	public void setIsReqContactFax(int isReqContactFax) {
		this.isReqContactFax = isReqContactFax;
	}


	public int getIsReqContactId() {
		return isReqContactId;
	}


	public void setIsReqContactId(int isReqContactId) {
		this.isReqContactId = isReqContactId;
	}


	public int getIsReqContactType() {
		return isReqContactType;
	}


	public void setIsReqContactType(int isReqContactType) {
		this.isReqContactType = isReqContactType;
	}


	public int getIsReqcontractEndDate() {
		return isReqcontractEndDate;
	}


	public void setIsReqcontractEndDate(int isReqcontractEndDate) {
		this.isReqcontractEndDate = isReqcontractEndDate;
	}


	public int getIsReqcontractStartDate() {
		return isReqcontractStartDate;
	}


	public void setIsReqcontractStartDate(int isReqcontractStartDate) {
		this.isReqcontractStartDate = isReqcontractStartDate;
	}


	public int getIsReqCountyName() {
		return isReqCountyName;
	}


	public void setIsReqCountyName(int isReqCountyName) {
		this.isReqCountyName = isReqCountyName;
	}


	public int getIsReqDdCType() {
		return isReqDdCType;
	}


	public void setIsReqDdCType(int isReqDdCType) {
		this.isReqDdCType = isReqDdCType;
	}


	public int getIsReqDdPrimaryLocType() {
		return isReqDdPrimaryLocType;
	}


	public void setIsReqDdPrimaryLocType(int isReqDdPrimaryLocType) {
		this.isReqDdPrimaryLocType = isReqDdPrimaryLocType;
	}


	public int getIsReqDdSecondaryLocType() {
		return isReqDdSecondaryLocType;
	}


	public void setIsReqDdSecondaryLocType(int isReqDdSecondaryLocType) {
		this.isReqDdSecondaryLocType = isReqDdSecondaryLocType;
	}


	public int getIsReqEndDateLogic() {
		return isReqEndDateLogic;
	}


	public void setIsReqEndDateLogic(int isReqEndDateLogic) {
		this.isReqEndDateLogic = isReqEndDateLogic;
	}


	public int getIsReqEntity() {
		return isReqEntity;
	}


	public void setIsReqEntity(int isReqEntity) {
		this.isReqEntity = isReqEntity;
	}


	public int getIsReqFirstName() {
		return isReqFirstName;
	}


	public void setIsReqFirstName(int isReqFirstName) {
		this.isReqFirstName = isReqFirstName;
	}


	public int getIsReqLastName() {
		return isReqLastName;
	}


	public void setIsReqLastName(int isReqLastName) {
		this.isReqLastName = isReqLastName;
	}


	public int getIsReqParent_serviceProductID() {
		return isReqParent_serviceProductID;
	}


	public void setIsReqParent_serviceProductID(int isReqParent_serviceProductID) {
		this.isReqParent_serviceProductID = isReqParent_serviceProductID;
	}


	public int getIsReqParentServiceProductId() {
		return isReqParentServiceProductId;
	}


	public void setIsReqParentServiceProductId(int isReqParentServiceProductId) {
		this.isReqParentServiceProductId = isReqParentServiceProductId;
	}


	public int getIsReqPeriodsInMonths() {
		return isReqPeriodsInMonths;
	}


	public void setIsReqPeriodsInMonths(int isReqPeriodsInMonths) {
		this.isReqPeriodsInMonths = isReqPeriodsInMonths;
	}


	public int getIsReqPinNo() {
		return isReqPinNo;
	}


	public void setIsReqPinNo(int isReqPinNo) {
		this.isReqPinNo = isReqPinNo;
	}


	public int getIsReqPODate() {
		return isReqPODate;
	}


	public void setIsReqPODate(int isReqPODate) {
		this.isReqPODate = isReqPODate;
	}


	public int getIsReqPORcvDt() {
		return isReqPORcvDt;
	}


	public void setIsReqPORcvDt(int isReqPORcvDt) {
		this.isReqPORcvDt = isReqPORcvDt;
	}


	public int getIsReqSaluation() {
		return isReqSaluation;
	}


	public void setIsReqSaluation(int isReqSaluation) {
		this.isReqSaluation = isReqSaluation;
	}


	public int getIsReqSelectedDispatchID() {
		return isReqSelectedDispatchID;
	}


	public void setIsReqSelectedDispatchID(int isReqSelectedDispatchID) {
		this.isReqSelectedDispatchID = isReqSelectedDispatchID;
	}


	public int getIsReqSelectedPNLocation() {
		return isReqSelectedPNLocation;
	}


	public void setIsReqSelectedPNLocation(int isReqSelectedPNLocation) {
		this.isReqSelectedPNLocation = isReqSelectedPNLocation;
	}


	public int getIsReqSelectedPrimaryBCP() {
		return isReqSelectedPrimaryBCP;
	}


	public void setIsReqSelectedPrimaryBCP(int isReqSelectedPrimaryBCP) {
		this.isReqSelectedPrimaryBCP = isReqSelectedPrimaryBCP;
	}


	public int getIsReqSelectedSecBCP() {
		return isReqSelectedSecBCP;
	}


	public void setIsReqSelectedSecBCP(int isReqSelectedSecBCP) {
		this.isReqSelectedSecBCP = isReqSelectedSecBCP;
	}


	public int getIsReqSelectedSNLocation() {
		return isReqSelectedSNLocation;
	}


	public void setIsReqSelectedSNLocation(int isReqSelectedSNLocation) {
		this.isReqSelectedSNLocation = isReqSelectedSNLocation;
	}


	public int getIsReqServiceDetailID() {
		return isReqServiceDetailID;
	}


	public void setIsReqServiceDetailID(int isReqServiceDetailID) {
		this.isReqServiceDetailID = isReqServiceDetailID;
	}


	public int getIsReqStartDateLogic() {
		return isReqStartDateLogic;
	}


	public void setIsReqStartDateLogic(int isReqStartDateLogic) {
		this.isReqStartDateLogic = isReqStartDateLogic;
	}


	public int getIsReqStateName() {
		return isReqStateName;
	}


	public void setIsReqStateName(int isReqStateName) {
		this.isReqStateName = isReqStateName;
	}


	public int getIsReqTotalPoAmt() {
		return isReqTotalPoAmt;
	}


	public void setIsReqTotalPoAmt(int isReqTotalPoAmt) {
		this.isReqTotalPoAmt = isReqTotalPoAmt;
	}


	public int getIsReqTxtBillingAC() {
		return isReqTxtBillingAC;
	}


	public void setIsReqTxtBillingAC(int isReqTxtBillingAC) {
		this.isReqTxtBillingAC = isReqTxtBillingAC;
	}


	public int getIsReqTxtBillingCP() {
		return isReqTxtBillingCP;
	}


	public void setIsReqTxtBillingCP(int isReqTxtBillingCP) {
		this.isReqTxtBillingCP = isReqTxtBillingCP;
	}


	public int getIsReqTxtBillingformat() {
		return isReqTxtBillingformat;
	}


	public void setIsReqTxtBillingformat(int isReqTxtBillingformat) {
		this.isReqTxtBillingformat = isReqTxtBillingformat;
	}


	public int getIsReqTxtBillingLvl() {
		return isReqTxtBillingLvl;
	}


	public void setIsReqTxtBillingLvl(int isReqTxtBillingLvl) {
		this.isReqTxtBillingLvl = isReqTxtBillingLvl;
	}


	public int getIsReqTxtBillingMode() {
		return isReqTxtBillingMode;
	}


	public void setIsReqTxtBillingMode(int isReqTxtBillingMode) {
		this.isReqTxtBillingMode = isReqTxtBillingMode;
	}


	public int getIsReqTxtCEndDate() {
		return isReqTxtCEndDate;
	}


	public void setIsReqTxtCEndDate(int isReqTxtCEndDate) {
		this.isReqTxtCEndDate = isReqTxtCEndDate;
	}


	public int getIsReqTxtCFreqAmt() {
		return isReqTxtCFreqAmt;
	}


	public void setIsReqTxtCFreqAmt(int isReqTxtCFreqAmt) {
		this.isReqTxtCFreqAmt = isReqTxtCFreqAmt;
	}


	public int getIsReqTxtCFrequency() {
		return isReqTxtCFrequency;
	}


	public void setIsReqTxtCFrequency(int isReqTxtCFrequency) {
		this.isReqTxtCFrequency = isReqTxtCFrequency;
	}


	public int getIsReqTxtCmtPeriod() {
		return isReqTxtCmtPeriod;
	}


	public void setIsReqTxtCmtPeriod(int isReqTxtCmtPeriod) {
		this.isReqTxtCmtPeriod = isReqTxtCmtPeriod;
	}


	public int getIsReqTxtCName() {
		return isReqTxtCName;
	}


	public void setIsReqTxtCName(int isReqTxtCName) {
		this.isReqTxtCName = isReqTxtCName;
	}


	public int getIsReqTxtCPeriod() {
		return isReqTxtCPeriod;
	}


	public void setIsReqTxtCPeriod(int isReqTxtCPeriod) {
		this.isReqTxtCPeriod = isReqTxtCPeriod;
	}


	public int getIsReqTxtCStartDate() {
		return isReqTxtCStartDate;
	}


	public void setIsReqTxtCStartDate(int isReqTxtCStartDate) {
		this.isReqTxtCStartDate = isReqTxtCStartDate;
	}


	public int getIsReqTxtCTAmt() {
		return isReqTxtCTAmt;
	}


	public void setIsReqTxtCTAmt(int isReqTxtCTAmt) {
		this.isReqTxtCTAmt = isReqTxtCTAmt;
	}


	public int getIsReqTxtEntity() {
		return isReqTxtEntity;
	}


	public void setIsReqTxtEntity(int isReqTxtEntity) {
		this.isReqTxtEntity = isReqTxtEntity;
	}


	public int getIsReqTxtform() {
		return isReqTxtform;
	}


	public void setIsReqTxtform(int isReqTxtform) {
		this.isReqTxtform = isReqTxtform;
	}


	public int getIsReqTxtHtype() {
		return isReqTxtHtype;
	}


	public void setIsReqTxtHtype(int isReqTxtHtype) {
		this.isReqTxtHtype = isReqTxtHtype;
	}


	public int getIsReqTxtLicenceCo() {
		return isReqTxtLicenceCo;
	}


	public void setIsReqTxtLicenceCo(int isReqTxtLicenceCo) {
		this.isReqTxtLicenceCo = isReqTxtLicenceCo;
	}


	public int getIsReqTxtNSale() {
		return isReqTxtNSale;
	}


	public void setIsReqTxtNSale(int isReqTxtNSale) {
		this.isReqTxtNSale = isReqTxtNSale;
	}


	public int getIsReqTxtPenaltyClause() {
		return isReqTxtPenaltyClause;
	}


	public void setIsReqTxtPenaltyClause(int isReqTxtPenaltyClause) {
		this.isReqTxtPenaltyClause = isReqTxtPenaltyClause;
	}


	public int getIsReqTxtPODetailNo() {
		return isReqTxtPODetailNo;
	}


	public void setIsReqTxtPODetailNo(int isReqTxtPODetailNo) {
		this.isReqTxtPODetailNo = isReqTxtPODetailNo;
	}


	public int getIsReqTxtStore() {
		return isReqTxtStore;
	}


	public void setIsReqTxtStore(int isReqTxtStore) {
		this.isReqTxtStore = isReqTxtStore;
	}


	public int getIsReqTxtTaxation() {
		return isReqTxtTaxation;
	}


	public void setIsReqTxtTaxation(int isReqTxtTaxation) {
		this.isReqTxtTaxation = isReqTxtTaxation;
	}


	public int getIsReqTxtTSale() {
		return isReqTxtTSale;
	}


	public void setIsReqTxtTSale(int isReqTxtTSale) {
		this.isReqTxtTSale = isReqTxtTSale;
	}


	public Long getBillingLevelNo() {
		return billingLevelNo;
	}


	public void setBillingLevelNo(Long billingLevelNo) {
		this.billingLevelNo = billingLevelNo;
	}


	public int getFormId() {
		return formId;
	}


	public void setFormId(int formId) {
		this.formId = formId;
	}


	public String getFormName() {
		return formName;
	}


	public void setFormName(String formName) {
		this.formName = formName;
	}


	public int getSaleNatureId() {
		return saleNatureId;
	}


	public void setSaleNatureId(int saleNatureId) {
		this.saleNatureId = saleNatureId;
	}


	public String getSaleNatureName() {
		return saleNatureName;
	}


	public void setSaleNatureName(String saleNatureName) {
		this.saleNatureName = saleNatureName;
	}


	public int getSaleTypeId() {
		return saleTypeId;
	}


	public void setSaleTypeId(int saleTypeId) {
		this.saleTypeId = saleTypeId;
	}


	public String getSaleTypeName() {
		return saleTypeName;
	}


	public void setSaleTypeName(String saleTypeName) {
		this.saleTypeName = saleTypeName;
	}


	public int getInterestRate() {
		return interestRate;
	}


	public void setInterestRate(int interestRate) {
		this.interestRate = interestRate;
	}


	public int getPrincipalAmount() {
		return principalAmount;
	}


	public void setPrincipalAmount(int principalAmount) {
		this.principalAmount = principalAmount;
	}


	public int getEndDateDays() {
		return endDateDays;
	}


	public void setEndDateDays(int endDateDays) {
		this.endDateDays = endDateDays;
	}


	public int getEndDateMonth() {
		return endDateMonth;
	}


	public void setEndDateMonth(int endDateMonth) {
		this.endDateMonth = endDateMonth;
	}


	public int getStartDateDays() {
		return startDateDays;
	}


	public void setStartDateDays(int startDateDays) {
		this.startDateDays = startDateDays;
	}


	public int getStartDateMonth() {
		return startDateMonth;
	}


	public void setStartDateMonth(int startDateMonth) {
		this.startDateMonth = startDateMonth;
	}
	public int getSalutationId() {
		return salutationId;
	}

	public void setSalutationId(int salutationId) {
		this.salutationId = salutationId;
	}

	public String getSalutationName() {
		return salutationName;
	}

	public void setSalutationName(String salutationName) {
		this.salutationName = salutationName;
	}

	public int getCityCode() {
		return cityCode;
	}

	public void setCityCode(int cityCode) {
		this.cityCode = cityCode;
	}

	public int getCountyCode() {
		return countyCode;
	}

	public void setCountyCode(int countyCode) {
		this.countyCode = countyCode;
	}

	public int getStateCode() {
		return stateCode;
	}

	public void setStateCode(int stateCode) {
		this.stateCode = stateCode;
	}


	public ArrayList getCustomer_logicalSINumbers() {
		return customer_logicalSINumbers;
	}


	public void setCustomer_logicalSINumbers(ArrayList customer_logicalSINumbers) {
		this.customer_logicalSINumbers = customer_logicalSINumbers;
	}



	public int getIsdisconnected() {
		return isdisconnected;
	}


	public void setIsdisconnected(int isdisconnected) {
		this.isdisconnected = isdisconnected;
	}


	public int getIssuspended() {
		return issuspended;
	}


	public void setIssuspended(int issuspended) {
		this.issuspended = issuspended;
	}


	


	public int getNrcId() {
		return nrcId;
	}


	public void setNrcId(int nrcId) {
		this.nrcId = nrcId;
	}


	public int getRcId() {
		return rcId;
	}


	public void setRcId(int i) {
		this.rcId = i;
	}


	

	public int getChargeNameID() {
		return chargeNameID;
	}


	public void setChargeNameID(int chargeNameID) {
		this.chargeNameID = chargeNameID;
	}


	public String getIb2bWorkflowAttachedId() {
		return ib2bWorkflowAttachedId;
	}


	public void setIb2bWorkflowAttachedId(String ib2bWorkflowAttachedId) {
		this.ib2bWorkflowAttachedId = ib2bWorkflowAttachedId;
	}


	public String getIb2bWorkflowAttachedName() {
		return ib2bWorkflowAttachedName;
	}


	public void setIb2bWorkflowAttachedName(String ib2bWorkflowAttachedName) {
		this.ib2bWorkflowAttachedName = ib2bWorkflowAttachedName;
	}


	public String getProjectWorkflowId() {
		return projectWorkflowId;
	}


	public void setProjectWorkflowId(String projectWorkflowId) {
		this.projectWorkflowId = projectWorkflowId;
	}

	public int getIsReqInterestRate() {
		return isReqInterestRate;
	}


	public void setIsReqInterestRate(int isReqInterestRate) {
		this.isReqInterestRate = isReqInterestRate;
	}


	public int getIsReqPrincipalAmount() {
		return isReqPrincipalAmount;
	}


	public void setIsReqPrincipalAmount(int isReqPrincipalAmount) {
		this.isReqPrincipalAmount = isReqPrincipalAmount;
	}

//ramna
	public String getIsFromLocationRequired() {
		return isFromLocationRequired;
	}


	public void setIsFromLocationRequired(String isFromLocationRequired) {
		this.isFromLocationRequired = isFromLocationRequired;
	}


	public String getIsToLocationRequired() {
		return isToLocationRequired;
	}


	public void setIsToLocationRequired(String isToLocationRequired) {
		this.isToLocationRequired = isToLocationRequired;
	}
	public String getFromLocation() {
		return fromLocation;
	}


	public void setFromLocation(String fromLocation) {
		this.fromLocation = fromLocation;
	}


	public String getToLocation() {
		return toLocation;
	}


	public void setToLocation(String toLocation) {
		this.toLocation = toLocation;
	}


	public String getFaddress() {
		return faddress;
	}


	public void setFaddress(String faddress) {
		this.faddress = faddress;
	}
	public String getToaddress() {
		return toaddress;
	}


	public void setToaddress(String toaddress) {
		this.toaddress = toaddress;
	}
	//ramana
	public Integer getOrderInfo_ChangeSubType() {
		return orderInfo_ChangeSubType;
	}


	public void setOrderInfo_ChangeSubType(Integer orderInfo_ChangeSubType) {
		this.orderInfo_ChangeSubType = orderInfo_ChangeSubType;
	}


	public Integer getOrderInfo_ChangeType() {
		return orderInfo_ChangeType;
	}


	public void setOrderInfo_ChangeType(Integer orderInfo_ChangeType) {
		this.orderInfo_ChangeType = orderInfo_ChangeType;
	}


	public String getOrderInfo_OrderType() {
		return orderInfo_OrderType;
	}


	public void setOrderInfo_OrderType(String orderInfo_OrderType) {
		this.orderInfo_OrderType = orderInfo_OrderType;
	}


	public int getM6OrderNo() {
		return m6OrderNo;
	}


	public void setM6OrderNo(int orderNo) {
		m6OrderNo = orderNo;
	}


	public String getPartyName() {
		return partyName;
	}


	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}



	public String getAcmgrEmail() {
		return acmgrEmail;
	}


	public void setAcmgrEmail(String acmgrEmail) {
		this.acmgrEmail = acmgrEmail;
	}


	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}
	

	public int getCrmAccountId() {
		return crmAccountId;
	}


	public void setCrmAccountId(int crmAccountId) {
		this.crmAccountId = crmAccountId;
	}


	public void setM6CreateDate(String createDate) {
		m6CreateDate = createDate;
	}
	
	public String getM6CreateDate() {
		return m6CreateDate;
	}

	public String getAmApproveDate() {
		return amApproveDate;
	}


	public void setAmApproveDate(String amApproveDate) {
		this.amApproveDate = amApproveDate;
	}


	public String getCopcApproveDate() {
		return copcApproveDate;
	}


	public void setCopcApproveDate(String copcApproveDate) {
		this.copcApproveDate = copcApproveDate;
	}


	public String getPmApproveDate() {
		return pmApproveDate;
	}


	public void setPmApproveDate(String pmApproveDate) {
		this.pmApproveDate = pmApproveDate;
	}


	public String getOrder_type() {
		return order_type;
	}


	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}


	public int getFromAccountNo() {
		return fromAccountNo;
	}


	public void setFromAccountNo(int fromAccountNo) {
		this.fromAccountNo = fromAccountNo;
	}
	public String getOrderStage() {
		return orderStage;
	}


	public void setOrderStage(String orderStage) {
		this.orderStage = orderStage;
	}

	public int getFromOrderNo() {
		return fromOrderNo;
	}


	public void setFromOrderNo(int fromOrderNo) {
		this.fromOrderNo = fromOrderNo;
	}


	public int getToAccountNo() {
		return toAccountNo;
	}


	public void setToAccountNo(int toAccountNo) {
		this.toAccountNo = toAccountNo;
	}


	public int getToOrderNo() {
		return toOrderNo;
	}
public String getLOC_Date() {
		return LOC_Date;
	}

	
	public void setLOC_Date(String date) {
		LOC_Date = date;
	}

	public void setToOrderNo(int toOrderNo) {
		this.toOrderNo = toOrderNo;
	}
    

		public String getLOC_No() {
		return LOC_No;
	}


	public void setLOC_No(String no) {
		LOC_No = no;
	}


		public String getM6_Child_Ser_Key() {
		return m6_Child_Ser_Key;
	}
		public void setM6_Child_Ser_Key(String child_Ser_Key) {
		m6_Child_Ser_Key = child_Ser_Key;
	}
private String fromaccountno;
	private String toaccountno;
	private String fromorderno; 
	private String toorderno; 
	private String fromdate;
	private String todate;
	private String ordertype; 
	private String amapprovaldate; 
	private String pmapprovaldate;  
	private String copcapprovaldate;
	
	private int totalRecord;
	private int maxPageNo;
	public int getMaxPageNo() {
		return maxPageNo;
	}


	public void setMaxPageNo(int maxPageNo) {
		this.maxPageNo = maxPageNo;
	}


	public int getTotalRecord() {
		return totalRecord;
	}


	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}


	public String getAmapprovaldate() {
		return amapprovaldate;
	}

	public void setAmapprovaldate(String amapprovaldate) {
		this.amapprovaldate = amapprovaldate;
	}

	public String getCopcapprovaldate() {
		return copcapprovaldate;
	}

	public void setCopcapprovaldate(String copcapprovaldate) {
		this.copcapprovaldate = copcapprovaldate;
	}

	public String getFromaccountno() {
		return fromaccountno;
	}

	public void setFromaccountno(String fromaccountno) {
		this.fromaccountno = fromaccountno;
	}

	public String getFromdate() {
		return fromdate;
	}

	public void setFromdate(String fromdate) {
		this.fromdate = fromdate;
	}

	public String getFromorderno() {
		return fromorderno;
	}

	public void setFromorderno(String fromorderno) {
		this.fromorderno = fromorderno;
	}

	public String getOrdertype() {
		return ordertype;
	}

	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}

	public String getPmapprovaldate() {
		return pmapprovaldate;
	}

	public void setPmapprovaldate(String pmapprovaldate) {
		this.pmapprovaldate = pmapprovaldate;
	}
	
	

	public String getToaccountno() {
		return toaccountno;
	}

	public void setToaccountno(String toaccountno) {
		this.toaccountno = toaccountno;
	}

	public String getTodate() {
		return todate;
	}

	public void setTodate(String todate) {
		this.todate = todate;
	}

	public String getToorderno() {
		return toorderno;
	}

	public void setToorderno(String toorderno) {
		this.toorderno = toorderno;
	}


	public String getOrderNo() {
		return orderNo;
	}


	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}


	public String getPoRecieveDate() {
		return poRecieveDate;
	}


	public void setPoRecieveDate(String poRecieveDate) {
		this.poRecieveDate = poRecieveDate;
	}
	
		public int getParty_no() {
		return party_no;
	}


	public void setParty_no(int party_no) {
		this.party_no = party_no;
	}


	public String getFx_external_acc_id() {
		return fx_external_acc_id;
	}


	public void setFx_external_acc_id(String fx_external_acc_id) {
		this.fx_external_acc_id = fx_external_acc_id;
	}


	public int getParty_id() {
		return party_id;
	}


	public void setParty_id(int party_id) {
		this.party_id = party_id;
	}


	public String getStore() {
		return store;
	}


	public void setStore(String store) {
		this.store = store;
	}
		public String getCustPoDetailNo() {
		return custPoDetailNo;
	}
		public void setCustPoDetailNo(String custPoDetailNo) {
		this.custPoDetailNo = custPoDetailNo;
	}
public String getPoAmount() {
		return poAmount;
	}


	public void setPoAmount(String poAmount) {
		this.poAmount = poAmount;
	}


	public String getCompanyName() {
		return companyName;
	}


	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	public String getDemo() {
		return demo;
	}


	public void setDemo(String demo) {
		this.demo = demo;
	}
public String getFromCopcApprovedDate() {
			return fromCopcApprovedDate;
		}


		public void setFromCopcApprovedDate(String fromCopcApprovedDate) {
			this.fromCopcApprovedDate = fromCopcApprovedDate;
		}


		public String getToCopcApprovedDate() {
			return toCopcApprovedDate;
		}


		public void setToCopcApprovedDate(String toCopcApprovedDate) {
			this.toCopcApprovedDate = toCopcApprovedDate;
		}


		public int getLegalEntity() {
			return legalEntity;
		}


		public void setLegalEntity(int legalEntity) {
			this.legalEntity = legalEntity;
		}


		public int getIsReqCustPoDetailNo() {
			return isReqCustPoDetailNo;
		}


		public void setIsReqCustPoDetailNo(int isReqCustPoDetailNo) {
			this.isReqCustPoDetailNo = isReqCustPoDetailNo;
		}
				
// [001]	Start 
		public String getParty() {
			return party;
		}


		public void setParty(String party) {
			this.party = party;
		}


		public String getFromCrmOrderid() {
			return fromCrmOrderid;
		}


		public void setFromCrmOrderid(String fromCrmOrderid) {
			this.fromCrmOrderid = fromCrmOrderid;
		}


		public String getFromOrderDate() {
			return fromOrderDate;
		}


		public void setFromOrderDate(String fromOrderDate) {
			this.fromOrderDate = fromOrderDate;
		}


		public String getToCrmOrderid() {
			return toCrmOrderid;
		}


		public void setToCrmOrderid(String toCrmOrderid) {
			this.toCrmOrderid = toCrmOrderid;
		}


		public String getToOrderDate() {
			return toOrderDate;
		}


		public void setToOrderDate(String toOrderDate) {
			this.toOrderDate = toOrderDate;
		}


		public Integer getAccountNo() {
			return accountNo;
		}


		public void setAccountNo(Integer accountNo) {
			this.accountNo = accountNo;
		}
		// [001]	End


		public String getChargeAnnotation() {
			return chargeAnnotation;
		}


		public void setChargeAnnotation(String chargeAnnotation) {
			this.chargeAnnotation = chargeAnnotation;
		}


		public int getIsReqTxtCAnnotation() {
			return isReqTxtCAnnotation;
		}


		public void setIsReqTxtCAnnotation(int isReqTxtCAnnotation) {
			this.isReqTxtCAnnotation = isReqTxtCAnnotation;
		}
		
		
			public int getIsReqTxtFromLocation() {
			return isReqTxtFromLocation;
		}


		public void setIsReqTxtFromLocation(int isReqTxtFromLocation) {
			this.isReqTxtFromLocation = isReqTxtFromLocation;
		}


		public int getIsReqTxtToLocation() {
			return isReqTxtToLocation;
		}


		public void setIsReqTxtToLocation(int isReqTxtToLocation) {
			this.isReqTxtToLocation = isReqTxtToLocation;
		}


		public String getTxtFAddress() {
			return txtFAddress;
		}


		public void setTxtFAddress(String txtFAddress) {
			this.txtFAddress = txtFAddress;
		}


		public String getTxtToAddress() {
			return txtToAddress;
		}


		public void setTxtToAddress(String txtToAddress) {
			this.txtToAddress = txtToAddress;
		}


		public String getCustPoDate() {
			return custPoDate;
		}


		public void setCustPoDate(String custPoDate) {
			this.custPoDate = custPoDate;
		}


		public int getIsReqCustPoDate() {
			return isReqCustPoDate;
		}


		public void setIsReqCustPoDate(int isReqCustPoDate) {
			this.isReqCustPoDate = isReqCustPoDate;
		}


		public int getCount1() {
			return count1;
		}


		public void setCount1(int count1) {
			this.count1 = count1;
		}


		public long getPonum() {
			return ponum;
		}


		public void setPonum(long ponum) {
			this.ponum = ponum;
		}


		public long getNoticePeriod() {
			return noticePeriod;
		}


		public void setNoticePeriod(long noticePeriod) {
			this.noticePeriod = noticePeriod;
		}


		public String getNewType() {
			return newType;
		}


		public String getSearchAccountNo() {
			return searchAccountNo;
		}


		public void setSearchAccountNo(String searchAccountNo) {
			this.searchAccountNo = searchAccountNo;
		}


		public String getSearchCRMOrder() {
			return searchCRMOrder;
		}


		public void setSearchCRMOrder(String searchCRMOrder) {
			this.searchCRMOrder = searchCRMOrder;
		}


		public String getSearchCurrency() {
			return searchCurrency;
		}


		public void setSearchCurrency(String searchCurrency) {
			this.searchCurrency = searchCurrency;
		}


		public String getSearchfromDate() {
			return searchfromDate;
		}


		public void setSearchfromDate(String searchfromDate) {
			this.searchfromDate = searchfromDate;
		}


		public String getSearchOrderType() {
			return searchOrderType;
		}


		public void setSearchOrderType(String searchOrderType) {
			this.searchOrderType = searchOrderType;
		}


		public String getSearchQuoteNumber() {
			return searchQuoteNumber;
		}


		public void setSearchQuoteNumber(String searchQuoteNumber) {
			this.searchQuoteNumber = searchQuoteNumber;
		}


		public String getSearchSourceName() {
			return searchSourceName;
		}


		public void setSearchSourceName(String searchSourceName) {
			this.searchSourceName = searchSourceName;
		}


		public String getSearchStageName() {
			return searchStageName;
		}


		public void setSearchStageName(String searchStageName) {
			this.searchStageName = searchStageName;
		}


		public String getSearchToDate() {
			return searchToDate;
		}


		public void setSearchToDate(String searchToDate) {
			this.searchToDate = searchToDate;
		}


		public String getSearchCurrencyName() {
			return searchCurrencyName;
		}


		public void setSearchCurrencyName(String searchCurrencyName) {
			this.searchCurrencyName = searchCurrencyName;
		}


		public String getSearchAccountName() {
			return searchAccountName;
		}


		public void setSearchAccountName(String searchAccountName) {
			this.searchAccountName = searchAccountName;
		}


		public String getSearchSource() {
			return searchSource;
		}


		public void setSearchSource(String searchSource) {
			this.searchSource = searchSource;
		}


		
		public void setNewTypeName(String newTypeName) {
			this.newTypeName = newTypeName;
		}
		public int getIsServiceSummMandatory() {
			return isServiceSummMandatory;
		}


		public void setIsServiceSummMandatory(int isServiceSummMandatory) {
			this.isServiceSummMandatory = isServiceSummMandatory;
		}


		public String[] getServiceSummaryMandatory() {
			return serviceSummaryMandatory;
		}


		public void setServiceSummaryMandatory(String[] serviceSummaryMandatory) {
			this.serviceSummaryMandatory = serviceSummaryMandatory;
		}



		public String getProdNameAndId() {
			return prodNameAndId;
		}
		public void setProdNameAndId(String prodNameAndId) {
			this.prodNameAndId = prodNameAndId;
		}


		public int getDestinationProductId() {
			return destinationProductId;
		}


		public void setDestinationProductId(int destinationProductId) {
			this.destinationProductId = destinationProductId;
		}


		public int getSourceProductId() {
			return sourceProductId;


		}


		public void setSourceProductId(int sourceProductId) {
			this.sourceProductId = sourceProductId;
		}


		public String getEffDate() {
			return effDate;
		}


		public void setEffDate(String effDate) {
			this.effDate = effDate;
		}


		public String getServiceId1() {
			return serviceId1;
		}


		public void setServiceId1(String serviceId1) {
			this.serviceId1 = serviceId1;
		}
		public String getShortCode() {
			return shortCode;
		}


		public void setShortCode(String shortCode) {
			this.shortCode = shortCode;
		}


		public int getNoofuses() {
			return noofuses;
		}


		public void setNoofuses(int noofuses) {
			this.noofuses = noofuses;
		}


		public int getIsReqPoEmailId() {
			return isReqPoEmailId;
		}


		public void setIsReqPoEmailId(int isReqPoEmailId) {
			this.isReqPoEmailId = isReqPoEmailId;
		}


	public int getIsPublished() {
			return isPublished;
		}


		public void setIsPublished(int isPublished) {
			this.isPublished = isPublished;
		}


		public String getIsPublishedString() {
			return isPublishedString;
		}


		public void setIsPublishedString(String isPublishedString) {
			this.isPublishedString = isPublishedString;
		}


		public int getIsReqTxtBillingLvlNo() {
			return isReqTxtBillingLvlNo;
		}


		public void setIsReqTxtBillingLvlNo(int isReqTxtBillingLvlNo) {
			this.isReqTxtBillingLvlNo = isReqTxtBillingLvlNo;
		}


		public String getPopLocAddress1() {
			return popLocAddress1;
		}


		public void setPopLocAddress1(String popLocAddress1) {
			this.popLocAddress1 = popLocAddress1;
		}


		public String getPopNetLocCode() {
			return popNetLocCode;
		}


		public void setPopNetLocCode(String popNetLocCode) {
			this.popNetLocCode = popNetLocCode;
		}


		public String getPopNetLocDesc() {
			return popNetLocDesc;
		}


		public void setPopNetLocDesc(String popNetLocDesc) {
			this.popNetLocDesc = popNetLocDesc;
		}


		public String getPopNetLocName() {
			return popNetLocName;
		}


		public void setPopNetLocName(String popNetLocName) {
			this.popNetLocName = popNetLocName;
		}


		public String getSessionid() {
			return sessionid;
		}


		public void setSessionid(String sessionid) {
			this.sessionid = sessionid;
		}


		public String getPopLocationCode() {
			return popLocationCode;
		}


		public void setPopLocationCode(String popLocationCode) {
			this.popLocationCode = popLocationCode;
		}


		public String getBcontactNo() {
			return bcontactNo;
		}


		public void setBcontactNo(String bcontactNo) {
			this.bcontactNo = bcontactNo;
		}


		public String getBEmailId() {
			return bEmailId;
		}


		public void setBEmailId(String emailId) {
			bEmailId = emailId;
		}


		public String getCst() {
			return cst;
		}


		public void setCst(String cst) {
			this.cst = cst;
		}


		public String getPcontactNO() {
			return pcontactNO;
		}


		public void setPcontactNO(String pcontactNO) {
			this.pcontactNO = pcontactNO;
		}


		public String getPemailId() {
			return pemailId;
		}


		public void setPemailId(String pemailId) {
			this.pemailId = pemailId;
		}


		public String getScontactNO() {
			return scontactNO;
		}


		public void setScontactNO(String scontactNO) {
			this.scontactNO = scontactNO;
		}


		public String getSemailId() {
			return semailId;
		}


		public void setSemailId(String semailId) {
			this.semailId = semailId;
		}


		public String getBemailID() {
			return bemailID;
		}


		public void setBemailID(String bemailID) {
			this.bemailID = bemailID;
		}


		public String getBfirstname() {
			return bfirstname;
		}


		public void setBfirstname(String bfirstname) {
			this.bfirstname = bfirstname;
		}


		public String getBlastname() {
			return blastname;
		}


		public void setBlastname(String blastname) {
			this.blastname = blastname;
		}


		public String getServiceStatus() {
			return serviceStatus;
		}


		public void setServiceStatus(String serviceStatus) {
			this.serviceStatus = serviceStatus;
		}
	


		public int getIsNfa() {
			return isNfa;
		}


		public void setIsNfa(int isNfa) {
			this.isNfa = isNfa;
		}


		
		public String getQueryFormValue() {
			return queryFormValue;
		}


		public void setQueryFormValue(String queryFormValue) {
			this.queryFormValue = queryFormValue;
		}


		public int getQueryNameID() {
			return queryNameID;
		}


		public void setQueryNameID(int queryNameID) {
			this.queryNameID = queryNameID;
		}

		public String getRfsDate() {
			return rfsDate;
		}


		public void setRfsDate(String rfsDate) {
			this.rfsDate = rfsDate;
		}
		public int getIsServiceActive() {
			return isServiceActive;
		}


		public void setIsServiceActive(int isServiceActive) {
			this.isServiceActive = isServiceActive;
		}
		public long getCreated_serviceid() {
			return created_serviceid;
		}


		public void setCreated_serviceid(long created_serviceid) {
			this.created_serviceid = created_serviceid;
		}


		public int getExcludecheck() {
			return excludecheck;
		}


		public void setExcludecheck(int excludecheck) {
			this.excludecheck = excludecheck;
		}


		public String getNrcActiveDateCrossed() {
			return nrcActiveDateCrossed;
		}


		public void setNrcActiveDateCrossed(String nrcActiveDateCrossed) {
			this.nrcActiveDateCrossed = nrcActiveDateCrossed;
		}


		public String getRcActiveDateCrossed() {
			return rcActiveDateCrossed;
		}


		public void setRcActiveDateCrossed(String rcActiveDateCrossed) {
			this.rcActiveDateCrossed = rcActiveDateCrossed;
		}


		public String getRcInactiveDateCrossed() {
			return rcInactiveDateCrossed;
		}


		public void setRcInactiveDateCrossed(String rcInactiveDateCrossed) {
			this.rcInactiveDateCrossed = rcInactiveDateCrossed;
		}


		public String getSpLPhno() {
			return spLPhno;
		}


		public void setSpLPhno(String spLPhno) {
			this.spLPhno = spLPhno;
		}


		public String getAcmgrPhno() {
			return acmgrPhno;
		}


		public void setAcmgrPhno(String acmgrPhno) {
			this.acmgrPhno = acmgrPhno;
		}


		public int getAccountID() {
			return accountID;
		}


		public long getAccphoneNo() {
			return accphoneNo;
		}


		public void setAccphoneNo(long accphoneNo) {
			this.accphoneNo = accphoneNo;
		}


		public String getInfraOderNo() {
			return infraOderNo;
		}


		public void setInfraOderNo(String infraOderNo) {
			this.infraOderNo = infraOderNo;
		}

	public String getPoNoOfCharge() {
			return poNoOfCharge;
		}


		public void setPoNoOfCharge(String poNoOfCharge) {
			this.poNoOfCharge = poNoOfCharge;
		}
		public String getInfraOderNo_new() {
			return infraOderNo_new;
		}


		public void setInfraOderNo_new(String infraOderNo_new) {
			this.infraOderNo_new = infraOderNo_new;
		}


		public String getLinkageInfoFlag() {
			return linkageInfoFlag;
		}


		public void setLinkageInfoFlag(String linkageInfoFlag) {
			this.linkageInfoFlag = linkageInfoFlag;
		}


		public String getLogicalCircuitId() {
			return logicalCircuitId;
		}


		public void setLogicalCircuitId(String logicalCircuitId) {
			this.logicalCircuitId = logicalCircuitId;
		}


		public String getLogicalCircuitId_new() {
			return logicalCircuitId_new;
		}


		public void setLogicalCircuitId_new(String logicalCircuitId_new) {
			this.logicalCircuitId_new = logicalCircuitId_new;
		}


		public String getMetasolvCircuitId() {
			return metasolvCircuitId;
		}


		public void setMetasolvCircuitId(String metasolvCircuitId) {
			this.metasolvCircuitId = metasolvCircuitId;
		}


		public String getMetasolvCircuitId_new() {
			return metasolvCircuitId_new;
		}


		public void setMetasolvCircuitId_new(String metasolvCircuitId_new) {
			this.metasolvCircuitId_new = metasolvCircuitId_new;
		}

	public String getPodetailID_String() {
			return podetailID_String;
		}


		public void setPodetailID_String(String podetailID_String) {
			this.podetailID_String = podetailID_String;
		}


		public int getIsDefaultPO() {
			return isDefaultPO;
		}


		public void setIsDefaultPO(int isDefaultPO) {
			this.isDefaultPO = isDefaultPO;
		}


		public String getDateLogicValue() {
			return dateLogicValue;
		}


		public void setDateLogicValue(String dateLogicValue) {
			this.dateLogicValue = dateLogicValue;
		}


		public String getSection() {
			return section;
		}


		public void setSection(String section) {
			this.section = section;
		}
		public String getBemailid() {
			return bemailid;
		}


		public void setBemailid(String bemailid) {
			this.bemailid = bemailid;
		}


		public long getPublished_by_empid() {
			return published_by_empid;
		}


		public void setPublished_by_empid(long published_by_empid) {
			this.published_by_empid = published_by_empid;
		}


		public long getPublished_by_roleid() {
			return published_by_roleid;
		}


		public void setPublished_by_roleid(long published_by_roleid) {
			this.published_by_roleid = published_by_roleid;
		}


		public String getOrderStatusValue() {
			return orderStatusValue;
		}
		public int getTxtEndDays() {
			return txtEndDays;
		}

		public void setTxtEndDays(int txtEndDays) {
			this.txtEndDays = txtEndDays;
		}

		public int getTxtEndMonth() {
			return txtEndMonth;
		}

		public void setTxtEndMonth(int txtEndMonth) {
			this.txtEndMonth = txtEndMonth;
		}

		public String getTxtExtDate() {
			return txtExtDate;
		}

		public void setTxtExtDate(String txtExtDate) {
			this.txtExtDate = txtExtDate;
		}

		public int getTxtExtDays() {
			return txtExtDays;
		}

		public void setTxtExtDays(int txtExtDays) {
			this.txtExtDays = txtExtDays;
		}

		public int getTxtExtMonth() {
			return txtExtMonth;
		}

		public void setTxtExtMonth(int txtExtMonth) {
			this.txtExtMonth = txtExtMonth;
		}

		public int getTxtStartDays() {
			return txtStartDays;
		}

		public void setTxtStartDays(int txtStartDays) {
			this.txtStartDays = txtStartDays;
		}

		public int getTxtStartMonth() {
			return txtStartMonth;
		}

		public void setTxtStartMonth(int txtStartMonth) {
			this.txtStartMonth = txtStartMonth;
		}


		public void setOrderStatusValue(String orderStatusValue) {
			this.orderStatusValue = orderStatusValue;
		}

		public String getChargeRemarks() {
			return chargeRemarks;
		}
		public String getDesignation() {
			return designation;
		}


		public void setDesignation(String designation) {
			this.designation = designation;
		}


		public String getLst_No() {
			return lst_No;
		}


		public void setLst_No(String lst_No) {
			this.lst_No = lst_No;
		}


		
		public void setChargeRemarks(String chargeRemarks) {
			this.chargeRemarks = chargeRemarks;
		}


		public String getDisconnection_type() {
			return disconnection_type;
		}


		public void setDisconnection_type(String disconnection_type) {
			this.disconnection_type = disconnection_type;
		}


		public String getRfs_date() {
			return rfs_date;
		}


		public void setRfs_date(String rfs_date) {
			this.rfs_date = rfs_date;
		}


		public String getDefValue() {
			return defValue;
		}


		public void setDefValue(String defValue) {
			this.defValue = defValue;
		}


		public String getCreatedBy() {
			return createdBy;
		}


		public void setCreatedBy(String createdBy) {
			this.createdBy = createdBy;
		}


		public String getCreatedDate() {
			return createdDate;
		}


		public void setCreatedDate(String createdDate) {
			this.createdDate = createdDate;
		}


		public int getNewOrderNo() {
			return newOrderNo;
		}


		public void setNewOrderNo(int newOrderNo) {
			this.newOrderNo = newOrderNo;
		}


		public int getNewServiceNo() {
			return newServiceNo;
		}


		public void setNewServiceNo(int newServiceNo) {
			this.newServiceNo = newServiceNo;
		}


		public int getOldOrderNo() {
			return oldOrderNo;
		}


		public void setOldOrderNo(int oldOrderNo) {
			this.oldOrderNo = oldOrderNo;
		}


		public int getOldServiceNo() {
			return oldServiceNo;
		}


		public void setOldServiceNo(int oldServiceNo) {
			this.oldServiceNo = oldServiceNo;
		}


		public int getRootOrderNo() {
			return rootOrderNo;
		}


		public void setRootOrderNo(int rootOrderNo) {
			this.rootOrderNo = rootOrderNo;
		}


		public int getIsServiceCreatedAfterCancelCopy() {
			return isServiceCreatedAfterCancelCopy;
		}


		public void setIsServiceCreatedAfterCancelCopy(
				int isServiceCreatedAfterCancelCopy) {
			this.isServiceCreatedAfterCancelCopy = isServiceCreatedAfterCancelCopy;
		}


		public int getIsNewOrder() {
			return isNewOrder;
		}


		public void setIsNewOrder(int isNewOrder) {
			this.isNewOrder = isNewOrder;
		}


		public String getParty_num() {
			return party_num;
		}


		public void setParty_num(String party_num) {
			this.party_num = party_num;
		}


		public String getPlocationtype() {
			return plocationtype;
		}


		public void setPlocationtype(String plocationtype) {
			this.plocationtype = plocationtype;
		}


		public String getSlocationtype() {
			return slocationtype;
		}


		public void setSlocationtype(String slocationtype) {
			this.slocationtype = slocationtype;
		}


		public String getGam_contact() {
			return gam_contact;
		}


		public void setGam_contact(String gam_contact) {
			this.gam_contact = gam_contact;
		}


		public String getGam_emailid() {
			return gam_emailid;
		}


		public void setGam_emailid(String gam_emailid) {
			this.gam_emailid = gam_emailid;
		}


		public String getGam_name() {
			return gam_name;
		}


		public void setGam_name(String gam_name) {
			this.gam_name = gam_name;
		}


		
	public ArrayList<ComponentsDto> getComponentDetails() {
			return componentDetails;
		}


		public void setComponentDetails(ArrayList<ComponentsDto> componentDetails) {
			this.componentDetails = componentDetails;
		}


		public int getComponentID() {
			return componentID;
		}


		public void setComponentID(int componentID) {
			this.componentID = componentID;
		}


		public int getComponentInfoValue() {
			return componentInfoValue;
		}


		public void setComponentInfoValue(int componentInfoValue) {
			this.componentInfoValue = componentInfoValue;
		}


		public String getComponentName() {
			return componentName;
		}


		public void setComponentName(String componentName) {
			this.componentName = componentName;
		}


		public int getHaveComponent() {
			return haveComponent;
		}


		public void setHaveComponent(int haveComponent) {
			this.haveComponent = haveComponent;
		}


		public int getPackageID() {
			return packageID;
		}


		public void setPackageID(int packageID) {
			this.packageID = packageID;
		}


		public String getPackageName() {
			return packageName;
		}


		public void setPackageName(String packageName) {
			this.packageName = packageName;
		}


		public String getSelectedPackageList() {
			return selectedPackageList;
		}


		public void setSelectedPackageList(String selectedPackageList) {
			this.selectedPackageList = selectedPackageList;
		}


	
		public String getCreditDistribution() {
			return creditDistribution;
		}


		public void setCreditDistribution(String creditDistribution) {
			this.creditDistribution = creditDistribution;
		}


		public int getFormulaId() {
			return formulaId;
		}


		public void setFormulaId(int formulaId) {
			this.formulaId = formulaId;
		}


		public String getFormulaName() {
			return formulaName;
		}


		public void setFormulaName(String formulaName) {
			this.formulaName = formulaName;
		}


		public String getGamCredit() {
			return gamCredit;
		}


		public void setGamCredit(String gamCredit) {
			this.gamCredit = gamCredit;
		}


		public String getKamCredit() {
			return kamCredit;
		}


		public void setKamCredit(String kamCredit) {
			this.kamCredit = kamCredit;
		}


		public String getBaseCredit() {
			return baseCredit;
		}


		public void setBaseCredit(String baseCredit) {
			this.baseCredit = baseCredit;
		}


		public int getAttachFormulaStatus() {
			return attachFormulaStatus;
		}


		public void setAttachFormulaStatus(int attachFormulaStatus) {
			this.attachFormulaStatus = attachFormulaStatus;
		}


	
		public String getM6OrderNoString() {
			return m6OrderNoString;
		}


		public void setM6OrderNoString(String orderNoString) {
			m6OrderNoString = orderNoString;
		}


		public String getAccountno() {
			return accountno;
		}


		public void setAccountno(String accountno) {
			this.accountno = accountno;
		}


		public String getFilterLSIList() {
			return filterLSIList;
		}


		public void setFilterLSIList(String filterLSIList) {
			this.filterLSIList = filterLSIList;
		}


		public String getSourcePartyNo() {
			return sourcePartyNo;
		}


		public void setSourcePartyNo(String sourcePartyNo) {
			this.sourcePartyNo = sourcePartyNo;
		}


		public String getTransferdate() {
			return transferdate;
		}


		public void setTransferdate(String transferdate) {
			this.transferdate = transferdate;
		}


		public String getDispatch_name() {
			return dispatch_name;
		}


		public void setDispatch_name(String dispatch_name) {
			this.dispatch_name = dispatch_name;
		}
		
		public String getCollectionMgr() {
			return collectionMgr;
		}



		public int getHdnCreatedBy() {
			return hdnCreatedBy;
		}


		public void setHdnCreatedBy(int hdnCreatedBy) {
			this.hdnCreatedBy = hdnCreatedBy;
		}


		public int getMax_allowed_gam_validate() {
			return max_allowed_gam_validate;
		}


		public void setMax_allowed_gam_validate(int max_allowed_gam_validate) {
			this.max_allowed_gam_validate = max_allowed_gam_validate;
		}


	



		public void setCollectionMgr(String collectionMgr) {
			this.collectionMgr = collectionMgr;
		}

		public String getOrderStageAnnotationName() {
			return orderStageAnnotationName;
		}


		public void setOrderStageAnnotationName(String orderStageAnnotationName) {
			this.orderStageAnnotationName = orderStageAnnotationName;
		}
					public String getCheckedServiceNumber() {
			return checkedServiceNumber;
		}


		public void setCheckedServiceNumber(String checkedServiceNumber) {
			this.checkedServiceNumber = checkedServiceNumber;
		}

		public int getFlexiCount() {
			return flexiCount;
		}


		public void setFlexiCount(int flexiCount) {
			this.flexiCount = flexiCount;
		}


		public int getSiteBasedCount() {
			return siteBasedCount;
		}


		public void setSiteBasedCount(int siteBasedCount) {
			this.siteBasedCount = siteBasedCount;
		}


		public int getUserBasedCount() {
			return userBasedCount;
		}


		public void setUserBasedCount(int userBasedCount) {
			this.userBasedCount = userBasedCount;
		}


		public String getPlanName() {
			return planName;
		}


		public void setPlanName(String planName) {
			this.planName = planName;
		}


		public int getDestAttId() {
			return destAttId;
		}


		public void setDestAttId(int destAttId) {
			this.destAttId = destAttId;
		}



		public long getSourcePartyID() {
			return sourcePartyID;
		}


		public void setSourcePartyID(long sourcePartyID) {
			this.sourcePartyID = sourcePartyID;
		}

		public String getRegionIdNew() {
			return regionIdNew;
		}


		public void setRegionIdNew(String regionIdNew) {
			this.regionIdNew = regionIdNew;
		}


		public ArrayList<Integer> getListChargeInfoIdList() {
			return listChargeInfoIdList;
		}
	public int getComponentInfoID() {
			return componentInfoID;
		}

		public void setComponentInfoID(int componentInfoID) {
			this.componentInfoID = componentInfoID;
		}
		public String getComponentIDList() {
			return componentIDList;
		}
		public void setComponentIDList(String componentIDList) {
			this.componentIDList = componentIDList;
		}

		public void setListChargeInfoIdList(ArrayList<Integer> listChargeInfoIdList) {
			this.listChargeInfoIdList = listChargeInfoIdList;
		}


		public ArrayList<Integer> getListComponent() {
			return listComponent;
		}


		public void setListComponent(ArrayList<Integer> listComponent) {
			this.listComponent = listComponent;
		}
		public String getSubLineItemLbl() {
			return subLineItemLbl;
		}


		public void setSubLineItemLbl(String subLineItemLbl) {
			this.subLineItemLbl = subLineItemLbl;
		}
		public String getLineItemLbl() {
			return lineItemLbl;
		}


		public void setLineItemLbl(String lineItemLbl) {
			this.lineItemLbl = lineItemLbl;
		}
		
		
		public int getActmgrid() {
			return actmgrid;
		}


		public void setActmgrid(int actmgrid) {
			this.actmgrid = actmgrid;
		}


		


		public String[] getActmgremailid() {
			return actmgremailid;
		}


		public void setActmgremailid(String[] actmgremailid) {
			this.actmgremailid = actmgremailid;
		}


		public String getActmgrname() {
			return actmgrname;
		}


		public void setActmgrname(String actmgrname) {
			this.actmgrname = actmgrname;
		}
		
		public ComponentsDto getComponentDto() {
			return componentDto;
		}
		public int getIsUsage() {
			return isUsage;
		}
		public void setIsUsage(int isUsage) {
			this.isUsage = isUsage;
		}
		public String getProduct_name_for_fx() {
			return product_name_for_fx;
		}
		public void setProduct_name_for_fx(String product_name_for_fx) {
			this.product_name_for_fx = product_name_for_fx;
		}

		public String getBillingTriggerMsg() {
			return billingTriggerMsg;
		}

		public void setBillingTriggerMsg(String billingTriggerMsg) {
			this.billingTriggerMsg = billingTriggerMsg;
		}

		public String getBillingTriggerStatus() {
			return billingTriggerStatus;
		}

		public void setBillingTriggerStatus(String billingTriggerStatus) {
			this.billingTriggerStatus = billingTriggerStatus;
		}

		public String getCharge_creation_source() {
			return charge_creation_source;
		}

		public void setCharge_creation_source(String charge_creation_source) {
			this.charge_creation_source = charge_creation_source;
		}

		public String getOrder_creation_source() {
			return order_creation_source;
		}

		public void setOrder_creation_source(String order_creation_source) {
			this.order_creation_source = order_creation_source;
		}

		public long getContactCount() {
			return contactCount;
		}

		public void setContactCount(long contactCount) {
			this.contactCount = contactCount;
		}

		public long getPoCount() {
			return poCount;
		}

		public void setPoCount(long poCount) {
			this.poCount = poCount;
		}

		public ArrayList<NewOrderDto> getServiceSummaryLov() {
			return serviceSummaryLov;
		}


		public void setServiceSummaryLov(ArrayList<NewOrderDto> serviceSummaryLov) {
			this.serviceSummaryLov = serviceSummaryLov;
		}

		public int getUsageBasedLineCount() {
			return usageBasedLineCount;
		}

		public void setUsageBasedLineCount(int usageBasedLineCount) {
			this.usageBasedLineCount = usageBasedLineCount;
		}
		public int getTotal_billing_trigger() {
			return total_billing_trigger;
		}


		public void setTotal_billing_trigger(int total_billing_trigger) {
			this.total_billing_trigger = total_billing_trigger;
		}


		public int getTotal_billing_trigger_end() {
			return total_billing_trigger_end;
		}


		public void setTotal_billing_trigger_end(int total_billing_trigger_end) {
			this.total_billing_trigger_end = total_billing_trigger_end;
		}


		public int getTotal_cancel_copy() {
			return total_cancel_copy;
		}


		public void setTotal_cancel_copy(int total_cancel_copy) {
			this.total_cancel_copy = total_cancel_copy;
		}


		public int getTotal_cancel_crm() {
			return total_cancel_crm;
		}


		public void setTotal_cancel_crm(int total_cancel_crm) {
			this.total_cancel_crm = total_cancel_crm;
		}


		public int getTotal_cancel_m6() {
			return total_cancel_m6;
		}


		public void setTotal_cancel_m6(int total_cancel_m6) {
			this.total_cancel_m6 = total_cancel_m6;
		}


		public int getTotal_m6_processing() {
			return total_m6_processing;
		}


		public void setTotal_m6_processing(int total_m6_processing) {
			this.total_m6_processing = total_m6_processing;
		}


		public int getTotal_new_services() {
			return total_new_services;
		}


		public void setTotal_new_services(int total_new_services) {
			this.total_new_services = total_new_services;
		}


		public int getTotal_services() {
			return total_services;
		}


		public void setTotal_services(int total_services) {
			this.total_services = total_services;
		}


		public String getDispatchContactName() {
			return dispatchContactName;
		}


		public void setDispatchContactName(String dispatchContactName) {
			this.dispatchContactName = dispatchContactName;
		}
		
		//PAGING-SERVICE-LINE-14-10-2012
			
		//PAGING-SERVICE-LINE-14-10-2012
		public long getConfigValue() {
			return configValue;
		}


		public void setConfigValue(long configValue) {
			this.configValue = configValue;
		}
		public String getGuiAlert() {
			return guiAlert;
		}
		public void setGuiAlert(String guiAlert) {
			this.guiAlert = guiAlert;
		}
		public String getIRN_Number() {
			return IRN_Number;
		}
		public void setIRN_Number(String number) {
			IRN_Number = number;
		}
		public String getIsCCMapWithMBIC() {
			return isCCMapWithMBIC;
		}
		public void setIsCCMapWithMBIC(String isCCMapWithMBIC) {
			this.isCCMapWithMBIC = isCCMapWithMBIC;
		}
		public int getFxInternalId() {
			return fxInternalId;
		}
		public void setFxInternalId(int fxInternalId) {
			this.fxInternalId = fxInternalId;
		}
		public int getIsDummy() {
			return isDummy;
		}
		public void setIsDummy(int isDummy) {
			this.isDummy = isDummy;
		}
		public String getAttributeKey() {
			return attributeKey;
		}


		public void setAttributeKey(String attributeKey) {
			this.attributeKey = attributeKey;
		}


		public String getLinkPopUpId() {
			return linkPopUpId;
		}


		public void setLinkPopUpId(String linkPopUpId) {
			this.linkPopUpId = linkPopUpId;
		}
		public int getDummyOrderNo() {
			return dummyOrderNo;
		}


		public void setDummyOrderNo(int dummyOrderNo) {
			this.dummyOrderNo = dummyOrderNo;
		}


		public int getDummyServiceId() {
			return dummyServiceId;
		}


		public void setDummyServiceId(int dummyServiceId) {
			this.dummyServiceId = dummyServiceId;
		}
		public int getIsDummyServicePublished() {
			return isDummyServicePublished;
		}


		public void setIsDummyServicePublished(int isDummyServicePublished) {
			this.isDummyServicePublished = isDummyServicePublished;
		}


		public int getBillingScenario() {
			return billingScenario;
		}


		public void setBillingScenario(int billingScenario) {
			this.billingScenario = billingScenario;
		}
		public int getFxRedirectionLSI() {
			return fxRedirectionLSI;
		}


		public void setFxRedirectionLSI(int fxRedirectionLSI) {
			this.fxRedirectionLSI = fxRedirectionLSI;
		}


		public int getFxRedirectionSPID() {
			return fxRedirectionSPID;
		}

		public void setFxRedirectionSPID(int fxRedirectionSPID) {
			this.fxRedirectionSPID = fxRedirectionSPID;
		}


		public void setComponentDto(ComponentsDto componentDto) {
			this.componentDto = componentDto;
		}
		
		public ArrayList<NewOrderDto> getArborLSIList() {
			return arborLSIList;
		}


		public void setArborLSIList(ArrayList<NewOrderDto> arborLSIList) {
			this.arborLSIList = arborLSIList;
		}
		public int getIsFLEFlag() {
			return isFLEFlag;
		}
		public void setIsFLEFlag(int isFLEFlag) {
			this.isFLEFlag = isFLEFlag;
		}
		public int getSimilartaxrate() {
			return similartaxrate;
		}
		public void setSimilartaxrate(int similartaxrate) {
			this.similartaxrate = similartaxrate;
		}
			public String getDeletedChargesList() {
			return deletedChargesList;
		}
		public void setDeletedChargesList(String deletedChargesList) {
			this.deletedChargesList = deletedChargesList;
		}
		public String getDisconnected_orderno() {
			return disconnected_orderno;
		}
		public void setDisconnected_orderno(String disconnected_orderno) {
			this.disconnected_orderno = disconnected_orderno;
		}
		public double getTotalChargeAmount() {
			return totalChargeAmount;
		}
		public void setTotalChargeAmount(double totalChargeAmount) {
			this.totalChargeAmount = totalChargeAmount;
		}
		public String getProjectmgremail() {
			return projectmgremail;
		}
		public void setProjectmgremail(String projectmgremail) {
			this.projectmgremail = projectmgremail;
		}
		public String getInitial_pm() {
			return initial_pm;
		}
		public void setInitial_pm(String initial_pm) {
			this.initial_pm = initial_pm;
		}
		public String getLast_changed_by() {
			return last_changed_by;
		}
		public void setLast_changed_by(String last_changed_by) {
			this.last_changed_by = last_changed_by;
		}
		public String getLast_pm() {
			return last_pm;
		}
		public void setLast_pm(String last_pm) {
			this.last_pm = last_pm;
		}
		public int getEmployeeid() {
			return employeeid;
		}
		public void setEmployeeid(int employeeid) {
			this.employeeid = employeeid;
		}

		public String getPonum1() {
			return ponum1;
		}
		public void setPonum1(String ponum1) {
			this.ponum1 = ponum1;
		}
		public String getBillingLevelNo1() {
			return billingLevelNo1;
		}
		public void setBillingLevelNo1(String billingLevelNo1) {
			this.billingLevelNo1 = billingLevelNo1;
		}
		

		public String getAccountCategory() {
			return accountCategory;
		}
		public void setAccountCategory(String accountCategory) {
			this.accountCategory = accountCategory;
		}
		public String getBillingBandwidth() {
			return billingBandwidth;
		}
		public void setBillingBandwidth(String billingBandwidth) {
			this.billingBandwidth = billingBandwidth;
		}
		public String getBillingBandwidthUOM() {
			return billingBandwidthUOM;
		}
		public void setBillingBandwidthUOM(String billingBandwidthUOM) {
			this.billingBandwidthUOM = billingBandwidthUOM;
		}
		public String getChargeStartDate() {
			return chargeStartDate;
		}
		public void setChargeStartDate(String chargeStartDate) {
			this.chargeStartDate = chargeStartDate;
		}
		public String getCustSegmentCode() {
			return custSegmentCode;
		}
		public void setCustSegmentCode(String custSegmentCode) {
			this.custSegmentCode = custSegmentCode;
		}
		public String getDistance() {
			return distance;
		}
		public void setDistance(String distance) {
			this.distance = distance;
		}
		public String getRatio() {
			return ratio;
		}
		public void setRatio(String ratio) {
			this.ratio = ratio;
		}
		public String getVerticalDetails() {
			return verticalDetails;
		}
		public void setVerticalDetails(String verticalDetails) {
			this.verticalDetails = verticalDetails;
		}
		public String getLineItemAmount() {
			return lineItemAmount;
		}
		public void setLineItemAmount(String lineItemAmount) {
			this.lineItemAmount = lineItemAmount;
		}
		public String getCancelFlag() {
			return cancelFlag;
		}
		public void setCancelFlag(String cancelFlag) {
			this.cancelFlag = cancelFlag;
		}
		public String getOrderTotalAmount() {
			return orderTotalAmount;
		}
		public void setOrderTotalAmount(String orderTotalAmount) {
			this.orderTotalAmount = orderTotalAmount;
		}
		public String getLineItemQuantity() {
			return lineItemQuantity;
		}
		public void setLineItemQuantity(String lineItemQuantity) {
			this.lineItemQuantity = lineItemQuantity;
		}
		public String getM6OrderNo2() {
			return m6OrderNo2;
		}
		public void setM6OrderNo2(String orderNo2) {
			m6OrderNo2 = orderNo2;
		}
		public String getCancelledOrderReference() {
			return cancelledOrderReference;
		}
		public void setCancelledOrderReference(String cancelledOrderReference) {
			this.cancelledOrderReference = cancelledOrderReference;
		}
		/*public long getParallelUpgradeLSINo() {
			return parallelUpgradeLSINo;
		}
		public void setParallelUpgradeLSINo(long parallelUpgradeLSINo) {
			this.parallelUpgradeLSINo = parallelUpgradeLSINo;
		}*/
		public HashMap<String, Integer> getServiceIdMap() {
			return serviceIdMap;
		}
		public void setServiceIdMap(HashMap<String, Integer> serviceIdMap) {
			this.serviceIdMap = serviceIdMap;
		}
		public String getLogicalsi() {
			return logicalsi;
		}
		public void setLogicalsi(String logicalsi) {
			this.logicalsi = logicalsi;
		}
		public String getDemoDays() {
			return demoDays;
		}
		public void setDemoDays(String demoDays) {
			this.demoDays = demoDays;
		}
		public String getDemoEndDate() {
			return demoEndDate;
		}
		public void setDemoEndDate(String demoEndDate) {
			this.demoEndDate = demoEndDate;
		}
		public String getDemoStartDate() {
			return demoStartDate;
		}
		public void setDemoStartDate(String demoStartDate) {
			this.demoStartDate = demoStartDate;
		}
		
		public String getSearchOrderStageName() {
			return searchOrderStageName;
		}
		public void setSearchOrderStageName(String searchOrderStageName) {
			this.searchOrderStageName = searchOrderStageName;
		}
		public String getSearchOrderStageCode() {
			return searchOrderStageCode;
		}
		public void setSearchOrderStageCode(String searchOrderStageCode) {
			this.searchOrderStageCode = searchOrderStageCode;
		}
		public Integer[] getProdAttriMaxLength() {
			return prodAttriMaxLength;
		}
		public void setProdAttriMaxLength(Integer[] prodAttriMaxLength) {
			this.prodAttriMaxLength = prodAttriMaxLength;
		}
		public Integer[] getProdServiceAttMaxLength() {
			return prodServiceAttMaxLength;
		}
		public void setProdServiceAttMaxLength(Integer[] prodServiceAttMaxLength) {
			this.prodServiceAttMaxLength = prodServiceAttMaxLength;
		}
	public ArrayList<ChargeDetailsSCM> getChargeDetailsSCM() {
		return chargeDetailsSCM;
	}
	public void setChargeDetailsSCM(ArrayList<ChargeDetailsSCM> chargeDetailsSCM) {
		this.chargeDetailsSCM = chargeDetailsSCM;
	}
	public int getSendToSCM() {
		return sendToSCM;
	}
	public void setSendToSCM(int sendToSCM) {
		this.sendToSCM = sendToSCM;
	}
	public String getErrorMsgScm() {
		return errorMsgScm;
	}
	public void setErrorMsgScm(String errorMsgScm) {
		this.errorMsgScm = errorMsgScm;
	}
	public String getSuccessMsgScm() {
		return successMsgScm;
	}
	public void setSuccessMsgScm(String successMsgScm) {
		this.successMsgScm = successMsgScm;
	}
	public int getPrId() {
		return prId;
	}
	public void setPrId(int prId) {
		this.prId = prId;
	}
	public String getPrNumber() {
		return prNumber;
	}
	public void setPrNumber(String prNumber) {
		this.prNumber = prNumber;
	}
	public int getNewPrFlag() {
		return newPrFlag;
	}
	public void setNewPrFlag(int newPrFlag) {
		this.newPrFlag = newPrFlag;
	}
	public int getPrReuseUpadte() {
		return prReuseUpadte;
	}
	public void setPrReuseUpadte(int prReuseUpadte) {
		this.prReuseUpadte = prReuseUpadte;
	}
	public int getChargeId_Scm() {
		return chargeId_Scm;
	}
	public void setChargeId_Scm(int chargeId_Scm) {
		this.chargeId_Scm = chargeId_Scm;
	}
	public int getNotSaveInScm() {
		return notSaveInScm;
	}
	public void setNotSaveInScm(int notSaveInScm) {
		this.notSaveInScm = notSaveInScm;
	}
	public ArrayList<Integer> getDeleteScmList() {
		return deleteScmList;
	}
	public void setDeleteScmList(ArrayList<Integer> deleteScmList) {
		this.deleteScmList = deleteScmList;
	}
	public int getIsPrReuse() {
		return isPrReuse;
	}
	public void setIsPrReuse(int isPrReuse) {
		this.isPrReuse = isPrReuse;
	}
	public int getNewPrFlagForView() {
		return newPrFlagForView;
	}
	public void setNewPrFlagForView(int newPrFlagForView) {
		this.newPrFlagForView = newPrFlagForView;
	}
	public int getIs_partial_initiated() {
		return is_partial_initiated;
	}
	public void setIs_partial_initiated(int is_partial_initiated) {
		this.is_partial_initiated = is_partial_initiated;
	}
	//[009]
	public String getOrdServFlag() {
		return ordServFlag;
	}
		public void setOrdServFlag(String ordServFlag) {
	this.ordServFlag = ordServFlag;
	}
		
		
		//[0010]	
		
		public String getService_cancelledby() {
			return service_cancelledby;
		}
		public void setService_cancelledby(String service_cancelledby) {
			this.service_cancelledby = service_cancelledby;
		}
		public String getServ_cancel_reson() {
			return serv_cancel_reson;
		}
		public void setServ_cancel_reson(String serv_cancel_reson) {
			this.serv_cancel_reson = serv_cancel_reson;
		}
		public String getServ_cancel_remarks() {
			return serv_cancel_remarks;
		}
		public void setServ_cancel_remarks(String serv_cancel_remarks) {
			this.serv_cancel_remarks = serv_cancel_remarks;
		}
		public String getService_cancl_date() {
			return service_cancl_date;
		}
		public void setService_cancl_date(String service_cancl_date) {
			this.service_cancl_date = service_cancl_date;
		}
		
//[0010]
//[0011] Start
	public String getServiceSegment() {
		return serviceSegment;
	}
	public void setServiceSegment(String serviceSegment) {
		this.serviceSegment = serviceSegment;
	}
    //[0011] End
	//[0012] start
	public String getParallelUpgradeLSINo1() {
		return parallelUpgradeLSINo1;
	}
	public void setParallelUpgradeLSINo1(String parallelUpgradeLSINo1) {
		this.parallelUpgradeLSINo1 = parallelUpgradeLSINo1;
	}
	public String getParallelUpgradeLSINo2() {
		return parallelUpgradeLSINo2;
	}
	public void setParallelUpgradeLSINo2(String parallelUpgradeLSINo2) {
		this.parallelUpgradeLSINo2 = parallelUpgradeLSINo2;
	}
	public String getParallelUpgradeLSINo3() {
		return parallelUpgradeLSINo3;
	}
	public void setParallelUpgradeLSINo3(String parallelUpgradeLSINo3) {
		this.parallelUpgradeLSINo3 = parallelUpgradeLSINo3;
	}
	
	//[0012] end
		
	// [0014] start
	public int getSearchLSI() {
		return searchLSI;
	}

	public void setSearchLSI(int searchLSI) {
		this.searchLSI = searchLSI;
	}

	public int getSearchServiceNo() {
		return searchServiceNo;
	}

	public void setSearchServiceNo(int searchServiceNo) {
		this.searchServiceNo = searchServiceNo;
	}
	// [0014] end
	public String getIsOSPTagging() {
		return isOSPTagging;
	}
	public void setIsOSPTagging(String isOSPTagging) {
		
		this.isOSPTagging = isOSPTagging;
	}
	
	public String getTxtOSPRegNo() {
		return txtOSPRegNo;
	}
	public void setTxtOSPRegNo(String txtOSPRegNo) {
		
		this.txtOSPRegNo = txtOSPRegNo;
	}
	
	public String getTxtOSPRegDate() {
		return txtOSPRegDate;
	}
	public void setTxtOSPRegDate(String txtOSPRegDate) {
		this.txtOSPRegDate = txtOSPRegDate;
	}
	public int getIsReqOSPTagging() {
		return isReqOSPTagging;
	}
	public void setIsReqOSPTagging(int isReqOSPTagging) {
		this.isReqOSPTagging = isReqOSPTagging;
	}
	public int getIsReqOSPRegNo() {
		return isReqOSPRegNo;
	}
	public void setIsReqOSPRegNo(int isReqOSPRegNo) {
		this.isReqOSPRegNo = isReqOSPRegNo;
	}
	public int getIsReqOSPRegDate() {
		return isReqOSPRegDate;
	}
	public void setIsReqOSPRegDate(int isReqOSPRegDate) {
		this.isReqOSPRegDate = isReqOSPRegDate;
	}
	
	//[0015] Start
	public int getParallelUpgradeLSIRequire() {
		return parallelUpgradeLSIRequire;
	}
	public void setParallelUpgradeLSIRequire(int parallelUpgradeLSIRequire) {
		this.parallelUpgradeLSIRequire = parallelUpgradeLSIRequire;
	}
	public String getRemarksParallelUpgrade() {
		return remarksParallelUpgrade;
	}
	public void setRemarksParallelUpgrade(String remarksParallelUpgrade) {
		this.remarksParallelUpgrade = remarksParallelUpgrade;
	}
	
	
	//[0015] End
	
	//[0016] starts
	
	public int getIsReqLdClause() {
		return isReqLdClause;
	}
	public void setIsReqLdClause(int isReqLdClause) {
		this.isReqLdClause = isReqLdClause;
	}
	public String getldClause() {
		return ldClause;
	}
	public void setldClause(String ldClause) {
		this.ldClause = ldClause;
	}


	public String getChannelPartnerCtgryName() {
		return channelPartnerCtgryName;
	}
	public void setChannelPartnerCtgryName(String channelPartnerCtgryName) {
		this.channelPartnerCtgryName = channelPartnerCtgryName;
	}
	public int getSeId() {
		return seId;
	}
	public void setSeId(int seId) {
		this.seId = seId;
	}
	public ArrayList<NewOrderDto> getAddlist() {
		return addlist;
	}
	public void setAddlist(ArrayList<NewOrderDto> addlist) {
		this.addlist = addlist;
	}
	public ArrayList<NewOrderDto> getEditlist() {
		return editlist;
	}
	public void setEditlist(ArrayList<NewOrderDto> editlist) {
		this.editlist = editlist;
	}
	public String getChannelPartnerCodeTest() {
		return channelPartnerCodeTest;
	}
	public void setChannelPartnerCodeTest(String channelPartnerCodeTest) {
		this.channelPartnerCodeTest = channelPartnerCodeTest;
	}
	public String getChannelPartnerIdTest() {
		return channelPartnerIdTest;
	}
	public void setChannelPartnerIdTest(String channelPartnerIdTest) {
		this.channelPartnerIdTest = channelPartnerIdTest;
	}
	
	//[0016] end
	//[0017] start
	public String getFieldEngineer() {
		return fieldEngineer;
	}
	public void setFieldEngineer(String fieldEngineer) {
		this.fieldEngineer = fieldEngineer;
	}
	public String getPartnerCode() {
		return partnerCode;
	}
	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}
	public String getChannelPartner() {
		return channelPartner;
	}
	public void setChannelPartner(String channelPartner) {
		this.channelPartner = channelPartner;
	}
	
	//[0017] end
	
	
	
}
