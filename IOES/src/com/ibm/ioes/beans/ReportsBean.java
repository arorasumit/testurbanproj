package com.ibm.ioes.beans;
//Tag Name Resource Name  	Date		CSR No			Description
//[0012]	Rohit Verma		5-Mar-13	00-08440	Report for Hardware Cancel Line Item
//[013]		Shourya Mishra  30-Nov-2013 CSR-09463   aAdvance Payment Report/Otc Report. Add some fields for this report
import java.util.ArrayList;
import java.util.Date;

import org.apache.struts.action.ActionForm;

import com.ibm.ioes.forms.ArchivalReportDto;
import com.ibm.ioes.forms.CountryDto;
import com.ibm.ioes.forms.ReportsDto;
import com.ibm.ioes.utilities.PagingSorting;
//[101010] Rampratap  added from date and to date on LEPM Reports
//[202020] Rampratap  added from date and to date on LEPM Reports
//[303030] Rampratap  added from date and to date on LEPMcancelreport Reports
//[HYPR22032013001]    Vijay    30-March-2013          Billing Work Queue Report. Add some fields for this report //
//[404040]    Shourya Mishra    04-Dov-2013 activeline report
//[001] Priya Gupta
public class ReportsBean extends ActionForm {

	private static final long serialVersionUID = -3998346555255256067L;

	PagingSorting pagingSorting = new PagingSorting();
	private String hdnOrderNo;
	private String accountIdStr;

	private String accountNameStr;

	private String networkLocationIdStr;

	private String bcpIdStr;

	private String bcpNameStr;

	private ArrayList customerList;
	private ArrayList orderStageList;
	private ArrayList bcpExcelList;
	private ArrayList orderExcelList;

	private ArrayList networkLocsExcelList;
	private ArrayList dispatchAddressExcelList;
	private ArrayList list;

	private String toExcel;
	
	private String dispatchAddressId;
	private String dispatchAddressName;

	private String dispatchAddressStr;
	
	ArrayList<CountryDto> countries = null;

	private String locationIdStr;
	private String approvalType;
	private String locationNameStr;
	private String reason;
	private String created_Date;
	private String crmAccountNo;
	private String dispatchAddressIdStr;
	
private String contactTypeStr;

	private String contactNameStr;

	private String orderNoStr;
	private String orderNo;
	private String m6OrderNo;
	private ArrayList orderList;
	private String orderStatusStr;
	private String fromDate;
	private String toDate;
	private String masterValue;
	private String selectedMaster;
	private ArrayList<ReportsDto> historyList;
	private String attribiuteId;
	private String columnName;
	private ArrayList<ReportsDto> historyValueList;

	private String masterName;
	private String modifiedBy;
	private String modifiedDate;
	private String newValues;
	private String oldValues;
	private String operationName;
	private String changeType;
	private Integer changeTypeId;
	private String orderType;
	private int fromOrderNo ;
	private int toOrderNo ;
	private int fromAccountNo ;
	private int toAccountNo ;
	private String ordersubtype;
	//Ramana
	private int accountID;
	private String poRecieveDate;
	private String amapprovaldate;
	private String pmapprovaldate;
	private String copcapprovaldate;
	//[101010] START
	private String  copcapprovalfromdate;
	private String  copcapprovaltodate;
	// [101010]END
	
	//Meenakshi
	private String logical_SI_No;
	private String companyName;
	private String demo;
	
	//Saurabh
	private String toCopcApprovedDate;
	private String fromCopcApprovedDate;
	private String locDate;
		//lawkush
	// [001]	Start 
    private String party;
    private String fromOrderDate;
    private String toOrderDate;
    private String fromCrmOrderid;
    private String toCrmOrderid;
    private String accountNumber;
    private String ordermonth;
    private String orderyear;
	// [001]	End

    private int orderNumber;
	private String orderStatus;
	private String orderDate;
	private String regionName;
	private String zoneName;
	private String accountName;
	private String accountManager;
	private String projectManager;
	private String effStartDate;
	private String publishedDate;
	private String serviceDetDescription;
	private String partyName;
	private String billingStatus;
	private String serviceStage;
	private String amName;
	private String pmName;
	private String copcName;
	private String standardReason;
	private String m6OrderDate;
	private String amApproveDate;
	private String pmApproveDate;
	private String copcApproveDate;
	private String circuitStatus;
	private String orderProvision;
	private String verticalDetails;
	private int serviceId;
	private int custLogicalSI;
	private int partyNo;
	private int m6OrderNumber;
	private String parentOrderSubType;
	private String date_of_installation;
	private String date_of_installation_to;
	private String date_of_installation_from;
	
	private long accountId;
	private String order_type;
	private String custPoDate;
	private long poAmountSum;
    private String canceldate;
    private int logicalsi_no;
    private String servicename;
	// [303030] START
    private String canceldatefrom;
    private String canceldateto;
	//[303030] END
//	lawkush start
	
	
	private String actmngname;
	private String prjmngname;
	private String vertical;
	private String cus_segment;
	private String act_category;
	private String linename;
	private String challenno;
	private String challendate;
	private String m6cktid;
	private int orderLineNumber;
	private String cancelflag;
	private String provisionBandWidth;
	private String uom;
	private String billingBandWidth;
	private String billUom;
	private String categoryOfOrder;
	private String customerRfsDate;
	private String customerServiceRfsDate;
	private String customerPoDate;
	private String customerPoNumber;
	private String cyclicNonCyclic;
	private String fromSite;
	private String toSite;
	private int itemQuantity;
	private String kmsDistance;
	private String lineItemDescription;
	private String amReceiveDate;
	private double orderTotal;
	private String orderEntryDate;
	private String licenceCoName;
	private String paymentTerm;
	private double oldLineitemAmount;
	private String demoType;
	private String orderStageDescription;
	private String serviceStageDescription;
	private String chargeEndDate;
	private String newOrderRemark;
	private String serviceOrderType;
	private String crmACcountNO;
	private double poAmounts;
	private String storeName;
	private String logicalCircuitNumber;
	private String billingPODate;
	private String chargeName;
	private String industrySegment;
	private String subtype;
	private String orderApproveDate;
	private String taskName;
	private String actualStartDate;
	private String actualEndDate;
	private int taskNumber;
	private String owner;
	private String userName;
	private String outCome;
	private String remarks;
	private String totalDays;
	private String emailId;
	private long accphoneNo;
	private String custPoDetailNo; 
	private String poDetailNo;
	private int entityID;
	private String contractStartDate;
	private String contractEndDate;
	private String periodsInMonths;
	private String poIssueBy;
	private String poEmailId;
	private String createdBy;
	private String createdDate;
	private int isDefaultPO;
	private String sourceName;
	private String poDemoContractPeriod;
	private String logicalSINo;
	String fromLocation;
	String toLocation;
	private String tokenNO;
	private String fxSiId;
	private String chargeAnnotation;
	private String poEndDate;
	private String lastUpdatedDate;
	private String lastUpdatedBy;
	private String billingtrigger_createdate;
	//MANISHA START
	private String srNo;
	private ArrayList pendingOrderList;
	// MANISHA END
	
	//lawkush end
	private String osp;
	
	//[0011] Start
	private String redLSiNumber;
	private String mappedLSINumber;
	//[0011] End
	
	/*
	 * //-[HYPR22032013001] -start 
	 * for billing work queue reprot
	 */
	private String sourcePartyName;
	private String isHardware;
	/*//-[HYPR22032013001] - end */
	//[0012] Start
	private ArrayList HardwareLineItemList;
	private String searchLineItemNo;
	//[0012] End
	
	//lawkush Start 21 May
	
	private String serviceTypeName;
	
	private int serviceTypeId;
	
	private String serviceType;
	
	//lawkush End 21 May
	
	private String reportProcessForLimit=null;
	private String reportCurrentCount=null;
	private String interfaceId = null;
	private String isDumpAvailable = null;
	private String dumpFileName = null;
	
	private String copcEndDate;    // Anadi 21 Oct
	private String serviceProductId;
	private String billingTriggStatus;
	private String chargeInfoId;
	private String changeServiceId;
	
	//[013] Start
	private String	currencyofOrder;
    private String	customerSegment;
    private String	circle;
    private String	orderCreationDate;
    private String	amApprovalDate;
    private String pmApprovalDate;
    private String	orderApprovalDate;
    private String	lsi;
    private String	serviceNo;
    private String	product;
    private String	licenseCompany;
    private String	fxChildAccount;
    private String	billingTriggerDate;
    private String	arcChqNo;
    private String	arcChqDate;
    private String	otcChqNo;
    private String	otcChqDate;
    private String	otcBankName;
    private String	fromorderCreationDate;
	private String 	toorderCreationDate;
	private String  toChqDate;
	private String  datetype;
	private String	fromotcDate;
	private String  toOtcDate;
	private double	arcChqAmt;
	private double	arcAmtAjd;
	private double	otcChqAmt;
	private double	otcAmtAjd;
	private String arcExempted;
	private String arcExpreason;
	private String otcExempted;
	private String otcExpreason;
	private String arcBankName;
	private String productName;
	private String subproductName;
	private String	billingTriggerActionDate;
	//[013] End
	
//[404040] Start
	private int business_serial_n;
	private String opms_Account_Id;
	private String mocn_no;
	private String oldLsi;
	private String opms_lineItemNumber;
	private String RE_LOGGED_LSI_NO;
	private String PARALLEL_UPGRADE_LSI_NO;
	private String CHARGEDISCONNECTIONSTATUS;
	private String  subchange_type;
	private String  fxAccountExternalId;
	private String  fxAccountInternallId;
	private String  lineitemstatus;
	private ArrayList serviceNameList;

	// Varun Start

	private int sNo;
	private String ACCOUNT_ID;
	private String account_manager;
	private String ACCOUNT_NUMBER;
	private String PO_AMOUNT;
	private String CUSTOMER_SEGMENT;
	private String VERTICAL;
	private String BILLING_CHARGE_START_DATE;
	private String SERVICE_NAME;
	private String ORDER_LINE_NO;
	private String CANCEL_FLAG;
	private String UOM;
	private String BILLING_BANDWIDTH;
	private String BILL_UOM;
	private String CATEGORY_OF_ORDER;
	private String CONTRACT_PERIOD;
	private String ORDER_CREATION_DATE;
	private String CUSTOMER_RFS_DATE;
	private String CUSTOMER_SERVICE_RFS_DATE;
	private String CURRENCY;
	private String CUSTOMER_PO_DATE;
	private String CUSTOMER_PO_NUMBER;
	private String CYCLIC_OR_NON_CYCLIC;
	private String FROM_SITE;
	private String ITEM_QUANTITY;
	private String LINE_ITEM_AMOUNT;
	private String COPC_APPROVED_DATE;
	private String LINE_ITEM_DESCRIPTION;
	private String LOC_DATE;
	private String ACCOUNT_MANAGER_RECEIVE_DATE;
	private String ORDER_TOTAL;
	private String ORDER_ENTRY_DATE;
	private String TAXATION;
	private String TAXEXEMPTION_REASON;
	private String LICENCE_COMPANY;
	private String LOGICAL_CIRCUIT_ID;
	private String ORDER_TYPE;
	private String PAYMENT_TERM;
	private String PROJECT_MGR;
	private String REGION_NAME;
	private String OLD_LINE_ITEM_AMOUNT;
	private String DEMO_TYPE;
	private String PARTY_NAME;
	private String ORDER_STAGE_DESCRIPTION;
	private String SERVICE_STAGE_DESCRIPTION;
	private String CHARGE_END_DATE;
	private String NEW_ORDER_REMARK;
	private String REMARKS;
	private String SERVICE_ORDER_TYPE;
	private String OSP;
	private String OPPORTUNITY_ID;
	private String ACCOUNT_CATEGORY;
	private String PROVISION_BANDWIDTH;
	private String COMPANY_NAME;
	private String CHARGE_NAME;
	private String CHALLEN_NO;
	private String ORDER_NO;
	private String TO_SITE;
	private String KMS_DISTANCE;
	private String END_DATE_LOGIC;
	private String STORE_ADDRESS;
	private String FROMDATE;
	private String TODATE;
	private String m6orderno;
	private String LOGICALSINO;
	private String LINE_IT_NO;
	private String LOGICAL_SI_NO;
	private String LOC_Date;
    private String ckt_id;
    private String CIRCUIT_ID;
    private String CUST_LOGICAL_SI_NO;
    private String ORDER_LINE_ID;
    private String DISPATCH_ADDRESS;
    private String LINE_NAME;
    private String PO_NUMBER;
    private String BILLING_TRIG_DATE;
    private String line_name;
    private String cancel_reason;
    
    //[001] 
    private int actiontype;
    //priya
    private int fromServiceNo;
    private int toServiceNo;
    private String excludeCompOrders;
    
    


	public String getCancel_reason() {
		return cancel_reason;
	}

	public void setCancel_reason(String cancel_reason) {
		this.cancel_reason = cancel_reason;
	}

	public String getLine_name() {
		return line_name;
	}

	public void setLine_name(String line_name) {
		this.line_name = line_name;
	}

	public String getDISPATCH_ADDRESS() {
		return DISPATCH_ADDRESS;
	}

	public void setDISPATCH_ADDRESS(String dISPATCH_ADDRESS) {
		DISPATCH_ADDRESS = dISPATCH_ADDRESS;
	}

	public String getPO_NUMBER() {
		return PO_NUMBER;
	}

	public void setPO_NUMBER(String pO_NUMBER) {
		PO_NUMBER = pO_NUMBER;
	}

	public String getBILLING_TRIG_DATE() {
		return BILLING_TRIG_DATE;
	}

	public void setBILLING_TRIG_DATE(String bILLING_TRIG_DATE) {
		BILLING_TRIG_DATE = bILLING_TRIG_DATE;
	}

	public String getORDER_LINE_ID() {
		return ORDER_LINE_ID;
	}

	public void setORDER_LINE_ID(String oRDER_LINE_ID) {
		ORDER_LINE_ID = oRDER_LINE_ID;
	}

	public String getCUST_LOGICAL_SI_NO() {
		return CUST_LOGICAL_SI_NO;
	}

	public void setCUST_LOGICAL_SI_NO(String cUST_LOGICAL_SI_NO) {
		CUST_LOGICAL_SI_NO = cUST_LOGICAL_SI_NO;
	}

	public String getCIRCUIT_ID() {
		return CIRCUIT_ID;
	}

	public void setCIRCUIT_ID(String cIRCUIT_ID) {
		CIRCUIT_ID = cIRCUIT_ID;
	}

	public String getCkt_id() {
		return ckt_id;
	}

	public void setCkt_id(String ckt_id) {
		this.ckt_id = ckt_id;
	}

	public String getM6orderno() {
		return m6orderno;
	}

	public void setM6orderno(String m6orderno) {
		this.m6orderno = m6orderno;
	}



	public String getFROMDATE() {
		return FROMDATE;
	}

	public void setFROMDATE(String fROMDATE) {
		FROMDATE = fROMDATE;
	}

	public String getTODATE() {
		return TODATE;
	}

	public void setTODATE(String tODATE) {
		TODATE = tODATE;
	}


	public String getLOC_Date() {
		return LOC_Date;
	}

	public void setLOC_Date(String lOC_Date) {
		LOC_Date = lOC_Date;
	}

	public int getsNo() {
		return sNo;
	}

	public void setsNo(int sNo) {
		this.sNo = sNo;
	}

	public String getACCOUNT_ID() {
		return ACCOUNT_ID;
	}

	public void setACCOUNT_ID(String aCCOUNT_ID) {
		ACCOUNT_ID = aCCOUNT_ID;
	}

	public String getAccount_manager() {
		return account_manager;
	}

	public void setAccount_manager(String account_manager) {
		this.account_manager = account_manager;
	}

	public String getACCOUNT_NUMBER() {
		return ACCOUNT_NUMBER;
	}

	public void setACCOUNT_NUMBER(String aCCOUNT_NUMBER) {
		ACCOUNT_NUMBER = aCCOUNT_NUMBER;
	}

	public String getPO_AMOUNT() {
		return PO_AMOUNT;
	}

	public void setPO_AMOUNT(String pO_AMOUNT) {
		PO_AMOUNT = pO_AMOUNT;
	}

	public String getCUSTOMER_SEGMENT() {
		return CUSTOMER_SEGMENT;
	}

	public void setCUSTOMER_SEGMENT(String cUSTOMER_SEGMENT) {
		CUSTOMER_SEGMENT = cUSTOMER_SEGMENT;
	}

	public String getVERTICAL() {
		return VERTICAL;
	}

	public void setVERTICAL(String vERTICAL) {
		VERTICAL = vERTICAL;
	}

	public String getBILLING_CHARGE_START_DATE() {
		return BILLING_CHARGE_START_DATE;
	}

	public void setBILLING_CHARGE_START_DATE(String bILLING_CHARGE_START_DATE) {
		BILLING_CHARGE_START_DATE = bILLING_CHARGE_START_DATE;
	}

	public String getSERVICE_NAME() {
		return SERVICE_NAME;
	}

	public void setSERVICE_NAME(String sERVICE_NAME) {
		SERVICE_NAME = sERVICE_NAME;
	}

	public String getORDER_LINE_NO() {
		return ORDER_LINE_NO;
	}

	public void setORDER_LINE_NO(String oRDER_LINE_NO) {
		ORDER_LINE_NO = oRDER_LINE_NO;
	}

	public String getLINE_NAME() {
		return LINE_NAME;
	}

	public void setLINE_NAME(String lINE_NAME) {
		LINE_NAME = lINE_NAME;
	}

	public String getCANCEL_FLAG() {
		return CANCEL_FLAG;
	}

	public void setCANCEL_FLAG(String cANCEL_FLAG) {
		CANCEL_FLAG = cANCEL_FLAG;
	}

	public String getUOM() {
		return UOM;
	}

	public void setUOM(String uOM) {
		UOM = uOM;
	}

	public String getBILLING_BANDWIDTH() {
		return BILLING_BANDWIDTH;
	}

	public void setBILLING_BANDWIDTH(String bILLING_BANDWIDTH) {
		BILLING_BANDWIDTH = bILLING_BANDWIDTH;
	}

	public String getBILL_UOM() {
		return BILL_UOM;
	}

	public void setBILL_UOM(String bILL_UOM) {
		BILL_UOM = bILL_UOM;
	}

	public String getCATEGORY_OF_ORDER() {
		return CATEGORY_OF_ORDER;
	}

	public void setCATEGORY_OF_ORDER(String cATEGORY_OF_ORDER) {
		CATEGORY_OF_ORDER = cATEGORY_OF_ORDER;
	}

	public String getCONTRACT_PERIOD() {
		return CONTRACT_PERIOD;
	}

	public void setCONTRACT_PERIOD(String cONTRACT_PERIOD) {
		CONTRACT_PERIOD = cONTRACT_PERIOD;
	}

	public String getORDER_CREATION_DATE() {
		return ORDER_CREATION_DATE;
	}

	public void setORDER_CREATION_DATE(String oRDER_CREATION_DATE) {
		ORDER_CREATION_DATE = oRDER_CREATION_DATE;
	}

	public String getCUSTOMER_RFS_DATE() {
		return CUSTOMER_RFS_DATE;
	}

	public void setCUSTOMER_RFS_DATE(String cUSTOMER_RFS_DATE) {
		CUSTOMER_RFS_DATE = cUSTOMER_RFS_DATE;
	}

	public String getCUSTOMER_SERVICE_RFS_DATE() {
		return CUSTOMER_SERVICE_RFS_DATE;
	}

	public void setCUSTOMER_SERVICE_RFS_DATE(String cUSTOMER_SERVICE_RFS_DATE) {
		CUSTOMER_SERVICE_RFS_DATE = cUSTOMER_SERVICE_RFS_DATE;
	}

	public String getCURRENCY() {
		return CURRENCY;
	}

	public void setCURRENCY(String cURRENCY) {
		CURRENCY = cURRENCY;
	}

	public String getCUSTOMER_PO_DATE() {
		return CUSTOMER_PO_DATE;
	}

	public void setCUSTOMER_PO_DATE(String cUSTOMER_PO_DATE) {
		CUSTOMER_PO_DATE = cUSTOMER_PO_DATE;
	}

	public String getCUSTOMER_PO_NUMBER() {
		return CUSTOMER_PO_NUMBER;
	}

	public void setCUSTOMER_PO_NUMBER(String cUSTOMER_PO_NUMBER) {
		CUSTOMER_PO_NUMBER = cUSTOMER_PO_NUMBER;
	}

	public String getCYCLIC_OR_NON_CYCLIC() {
		return CYCLIC_OR_NON_CYCLIC;
	}

	public void setCYCLIC_OR_NON_CYCLIC(String cYCLIC_OR_NON_CYCLIC) {
		CYCLIC_OR_NON_CYCLIC = cYCLIC_OR_NON_CYCLIC;
	}

	public String getFROM_SITE() {
		return FROM_SITE;
	}

	public void setFROM_SITE(String fROM_SITE) {
		FROM_SITE = fROM_SITE;
	}

	public String getITEM_QUANTITY() {
		return ITEM_QUANTITY;
	}

	public void setITEM_QUANTITY(String iTEM_QUANTITY) {
		ITEM_QUANTITY = iTEM_QUANTITY;
	}

	public String getLINE_ITEM_AMOUNT() {
		return LINE_ITEM_AMOUNT;
	}

	public void setLINE_ITEM_AMOUNT(String lINE_ITEM_AMOUNT) {
		LINE_ITEM_AMOUNT = lINE_ITEM_AMOUNT;
	}

	public String getCOPC_APPROVED_DATE() {
		return COPC_APPROVED_DATE;
	}

	public void setCOPC_APPROVED_DATE(String cOPC_APPROVED_DATE) {
		COPC_APPROVED_DATE = cOPC_APPROVED_DATE;
	}

	public String getLINE_ITEM_DESCRIPTION() {
		return LINE_ITEM_DESCRIPTION;
	}

	public void setLINE_ITEM_DESCRIPTION(String lINE_ITEM_DESCRIPTION) {
		LINE_ITEM_DESCRIPTION = lINE_ITEM_DESCRIPTION;
	}

	public String getLOC_DATE() {
		return LOC_DATE;
	}

	public void setLOC_DATE(String lOC_DATE) {
		LOC_DATE = lOC_DATE;
	}

	public String getACCOUNT_MANAGER_RECEIVE_DATE() {
		return ACCOUNT_MANAGER_RECEIVE_DATE;
	}

	public void setACCOUNT_MANAGER_RECEIVE_DATE(
			String aCCOUNT_MANAGER_RECEIVE_DATE) {
		ACCOUNT_MANAGER_RECEIVE_DATE = aCCOUNT_MANAGER_RECEIVE_DATE;
	}

	public String getORDER_TOTAL() {
		return ORDER_TOTAL;
	}

	public void setORDER_TOTAL(String oRDER_TOTAL) {
		ORDER_TOTAL = oRDER_TOTAL;
	}

	public String getORDER_ENTRY_DATE() {
		return ORDER_ENTRY_DATE;
	}

	public void setORDER_ENTRY_DATE(String oRDER_ENTRY_DATE) {
		ORDER_ENTRY_DATE = oRDER_ENTRY_DATE;
	}

	public String getTAXATION() {
		return TAXATION;
	}

	public void setTAXATION(String tAXATION) {
		TAXATION = tAXATION;
	}

	public String getTAXEXEMPTION_REASON() {
		return TAXEXEMPTION_REASON;
	}

	public void setTAXEXEMPTION_REASON(String tAXEXEMPTION_REASON) {
		TAXEXEMPTION_REASON = tAXEXEMPTION_REASON;
	}

	public String getLICENCE_COMPANY() {
		return LICENCE_COMPANY;
	}

	public void setLICENCE_COMPANY(String lICENCE_COMPANY) {
		LICENCE_COMPANY = lICENCE_COMPANY;
	}

	public String getLOGICAL_CIRCUIT_ID() {
		return LOGICAL_CIRCUIT_ID;
	}

	public void setLOGICAL_CIRCUIT_ID(String lOGICAL_CIRCUIT_ID) {
		LOGICAL_CIRCUIT_ID = lOGICAL_CIRCUIT_ID;
	}

	public String getORDER_TYPE() {
		return ORDER_TYPE;
	}

	public void setORDER_TYPE(String oRDER_TYPE) {
		ORDER_TYPE = oRDER_TYPE;
	}

	public String getPAYMENT_TERM() {
		return PAYMENT_TERM;
	}

	public void setPAYMENT_TERM(String pAYMENT_TERM) {
		PAYMENT_TERM = pAYMENT_TERM;
	}

	public String getPROJECT_MGR() {
		return PROJECT_MGR;
	}

	public void setPROJECT_MGR(String pROJECT_MGR) {
		PROJECT_MGR = pROJECT_MGR;
	}

	public String getREGION_NAME() {
		return REGION_NAME;
	}

	public void setREGION_NAME(String rEGION_NAME) {
		REGION_NAME = rEGION_NAME;
	}

	public String getOLD_LINE_ITEM_AMOUNT() {
		return OLD_LINE_ITEM_AMOUNT;
	}

	public void setOLD_LINE_ITEM_AMOUNT(String oLD_LINE_ITEM_AMOUNT) {
		OLD_LINE_ITEM_AMOUNT = oLD_LINE_ITEM_AMOUNT;
	}

	public String getDEMO_TYPE() {
		return DEMO_TYPE;
	}

	public void setDEMO_TYPE(String dEMO_TYPE) {
		DEMO_TYPE = dEMO_TYPE;
	}

	public String getPARTY_NAME() {
		return PARTY_NAME;
	}

	public void setPARTY_NAME(String pARTY_NAME) {
		PARTY_NAME = pARTY_NAME;
	}

	public String getORDER_STAGE_DESCRIPTION() {
		return ORDER_STAGE_DESCRIPTION;
	}

	public void setORDER_STAGE_DESCRIPTION(String oRDER_STAGE_DESCRIPTION) {
		ORDER_STAGE_DESCRIPTION = oRDER_STAGE_DESCRIPTION;
	}

	public String getSERVICE_STAGE_DESCRIPTION() {
		return SERVICE_STAGE_DESCRIPTION;
	}

	public void setSERVICE_STAGE_DESCRIPTION(String sERVICE_STAGE_DESCRIPTION) {
		SERVICE_STAGE_DESCRIPTION = sERVICE_STAGE_DESCRIPTION;
	}

	public String getCHARGE_END_DATE() {
		return CHARGE_END_DATE;
	}

	public void setCHARGE_END_DATE(String cHARGE_END_DATE) {
		CHARGE_END_DATE = cHARGE_END_DATE;
	}

	public String getNEW_ORDER_REMARK() {
		return NEW_ORDER_REMARK;
	}

	public void setNEW_ORDER_REMARK(String nEW_ORDER_REMARK) {
		NEW_ORDER_REMARK = nEW_ORDER_REMARK;
	}

	public String getREMARKS() {
		return REMARKS;
	}

	public void setREMARKS(String rEMARKS) {
		REMARKS = rEMARKS;
	}

	public String getSERVICE_ORDER_TYPE() {
		return SERVICE_ORDER_TYPE;
	}

	public void setSERVICE_ORDER_TYPE(String sERVICE_ORDER_TYPE) {
		SERVICE_ORDER_TYPE = sERVICE_ORDER_TYPE;
	}

	public String getOSP() {
		return OSP;
	}

	public void setOSP(String oSP) {
		OSP = oSP;
	}

	public String getOPPORTUNITY_ID() {
		return OPPORTUNITY_ID;
	}

	public void setOPPORTUNITY_ID(String oPPORTUNITY_ID) {
		OPPORTUNITY_ID = oPPORTUNITY_ID;
	}

	public String getACCOUNT_CATEGORY() {
		return ACCOUNT_CATEGORY;
	}

	public void setACCOUNT_CATEGORY(String aCCOUNT_CATEGORY) {
		ACCOUNT_CATEGORY = aCCOUNT_CATEGORY;
	}

	public String getPROVISION_BANDWIDTH() {
		return PROVISION_BANDWIDTH;
	}

	public void setPROVISION_BANDWIDTH(String pROVISION_BANDWIDTH) {
		PROVISION_BANDWIDTH = pROVISION_BANDWIDTH;
	}

	public String getCOMPANY_NAME() {
		return COMPANY_NAME;
	}

	public void setCOMPANY_NAME(String cOMPANY_NAME) {
		COMPANY_NAME = cOMPANY_NAME;
	}

	public String getCHARGE_NAME() {
		return CHARGE_NAME;
	}

	public void setCHARGE_NAME(String cHARGE_NAME) {
		CHARGE_NAME = cHARGE_NAME;
	}

	public String getCHALLEN_NO() {
		return CHALLEN_NO;
	}

	public void setCHALLEN_NO(String cHALLEN_NO) {
		CHALLEN_NO = cHALLEN_NO;
	}

	public String getORDER_NO() {
		return ORDER_NO;
	}

	public void setORDER_NO(String oRDER_NO) {
		ORDER_NO = oRDER_NO;
	}

	public String getTO_SITE() {
		return TO_SITE;
	}

	public void setTO_SITE(String tO_SITE) {
		TO_SITE = tO_SITE;
	}

	public String getKMS_DISTANCE() {
		return KMS_DISTANCE;
	}

	public void setKMS_DISTANCE(String kMS_DISTANCE) {
		KMS_DISTANCE = kMS_DISTANCE;
	}

	public String getEND_DATE_LOGIC() {
		return END_DATE_LOGIC;
	}

	public void setEND_DATE_LOGIC(String eND_DATE_LOGIC) {
		END_DATE_LOGIC = eND_DATE_LOGIC;
	}

	public String getSTORE_ADDRESS() {
		return STORE_ADDRESS;
	}

	public void setSTORE_ADDRESS(String sTORE_ADDRESS) {
		STORE_ADDRESS = sTORE_ADDRESS;
	}

	public String getLOGICALSINO() {
		return LOGICALSINO;
	}

	public void setLOGICALSINO(String lOGICALSINO) {
		LOGICALSINO = lOGICALSINO;
	}

	public String getLINE_IT_NO() {
		return LINE_IT_NO;
	}

	public void setLINE_IT_NO(String lINE_IT_NO) {
		LINE_IT_NO = lINE_IT_NO;
	}

	public String getLOGICAL_SI_NO() {
		return LOGICAL_SI_NO;
	}

	public void setLOGICAL_SI_NO(String lOGICAL_SI_NO) {
		LOGICAL_SI_NO = lOGICAL_SI_NO;
	}

	// Varun End

	private String custSegment;
	public String getCustSegment() {
		return custSegment;
	}
	public void setCustSegment(String custSegment) {
		this.custSegment = custSegment;
	}
	public String getM6cktid() {
		return m6cktid;
	}
	public String getRE_LOGGED_LSI_NO() {
		return RE_LOGGED_LSI_NO;
	}
	public void setRE_LOGGED_LSI_NO(String rE_LOGGED_LSI_NO) {
		RE_LOGGED_LSI_NO = rE_LOGGED_LSI_NO;
	}
	public String getPARALLEL_UPGRADE_LSI_NO() {
		return PARALLEL_UPGRADE_LSI_NO;
	}
	public void setPARALLEL_UPGRADE_LSI_NO(String pARALLEL_UPGRADE_LSI_NO) {
		PARALLEL_UPGRADE_LSI_NO = pARALLEL_UPGRADE_LSI_NO;
	}
	public String getCHARGEDISCONNECTIONSTATUS() {
		return CHARGEDISCONNECTIONSTATUS;
	}
	public void setCHARGEDISCONNECTIONSTATUS(String cHARGEDISCONNECTIONSTATUS) {
		CHARGEDISCONNECTIONSTATUS = cHARGEDISCONNECTIONSTATUS;
	}
	public String getSubchange_type() {
		return subchange_type;
	}
	public void setSubchange_type(String subchange_type) {
		this.subchange_type = subchange_type;
	}
	public String getFxAccountExternalId() {
		return fxAccountExternalId;
	}
	public void setFxAccountExternalId(String fxAccountExternalId) {
		this.fxAccountExternalId = fxAccountExternalId;
	}
	public void setM6cktid(String m6cktid) {
		this.m6cktid = m6cktid;
	}
//	[404040] Start  
	 
	 private String opportunityId;		 
	 
	 public String getOpportunityId() {
		return opportunityId;
	}
	public void setOpportunityId(String opportunityId) {
		this.opportunityId = opportunityId;
	}
//	[404040] end  
	
		private String groupName;	
	 
	 
	 
	 public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
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
	public String getOsp() {
		return osp;
	}
	public void setOsp(String osp) {
		this.osp = osp;
	}
	
	
	private int isUsage;

	private String srrequest;
	public int getIsUsage() {
		return isUsage;
	}

	public void setIsUsage(int isUsage) {
		this.isUsage = isUsage;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getPoEndDate() {
		return poEndDate;
	}

	public void setPoEndDate(String poEndDate) {
		this.poEndDate = poEndDate;
	}

	public long getAccphoneNo() {
		return accphoneNo;
	}

	public void setAccphoneNo(long accphoneNo) {
		this.accphoneNo = accphoneNo;
	}

	public String getActualEndDate() {
		return actualEndDate;
	}

	public void setActualEndDate(String actualEndDate) {
		this.actualEndDate = actualEndDate;
	}

	public String getActualStartDate() {
		return actualStartDate;
	}

	public void setActualStartDate(String actualStartDate) {
		this.actualStartDate = actualStartDate;
	}

	

	public String getIndustrySegment() {
		return industrySegment;
	}

	public void setIndustrySegment(String industrySegment) {
		this.industrySegment = industrySegment;
	}

	public String getOrderApproveDate() {
		return orderApproveDate;
	}

	public void setOrderApproveDate(String orderApproveDate) {
		this.orderApproveDate = orderApproveDate;
	}

	public String getOrdersubtype() {
		return ordersubtype;
	}

	public void setOrdersubtype(String ordersubtype) {
		this.ordersubtype = ordersubtype;
	}

	public String getApprovalType() {
		return approvalType;
	}
	public void setApprovalType(String approvalType) {
		this.approvalType = approvalType;
	}
	public String getOutCome() {
		return outCome;
	}

	public void setOutCome(String outCome) {
		this.outCome = outCome;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public int getTaskNumber() {
		return taskNumber;
	}

	public void setTaskNumber(int taskNumber) {
		this.taskNumber = taskNumber;
	}

	public String getTotalDays() {
		return totalDays;
	}

	public void setTotalDays(String totalDays) {
		this.totalDays = totalDays;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getChargeName() {
		return chargeName;
	}

	public void setChargeName(String chargeName) {
		this.chargeName = chargeName;
	}

	public String getParentOrderSubType() {
		return parentOrderSubType;
	}

	public void setParentOrderSubType(String parentOrderSubType) {
		this.parentOrderSubType = parentOrderSubType;
	}

	public String getLocDate() {
		return locDate;
	}

	public void setLocDate(String locDate) {
		this.locDate = locDate;
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

	
	
	public String getDemo() {
		return demo;
	}

	public void setDemo(String demo) {
		this.demo = demo;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getLogical_SI_No() {
		return logical_SI_No;
	}

	public void setLogical_SI_No(String logical_SI_No) {
		this.logical_SI_No = logical_SI_No;
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

	public String getPmapprovaldate() {
		return pmapprovaldate;
	}

	public void setPmapprovaldate(String pmapprovaldate) {
		this.pmapprovaldate = pmapprovaldate;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getSelectedMaster() {
		return selectedMaster;
	}

	public void setSelectedMaster(String selectedMaster) {
		this.selectedMaster = selectedMaster;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getOrderStatusStr() {
		return orderStatusStr;
	}

	public void setOrderStatusStr(String orderStatusStr) {
		this.orderStatusStr = orderStatusStr;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getAccountIdStr() {
		return accountIdStr;
	}

	public void setAccountIdStr(String accountIdStr) {
		this.accountIdStr = accountIdStr;
	}

	public String getAccountNameStr() {
		return accountNameStr;
	}

	public void setAccountNameStr(String accountNameStr) {
		this.accountNameStr = accountNameStr;
	}

	public String getBcpIdStr() {
		return bcpIdStr;
	}

	public void setBcpIdStr(String bcpIdStr) {
		this.bcpIdStr = bcpIdStr;
	}

	public String getBcpNameStr() {
		return bcpNameStr;
	}

	public void setBcpNameStr(String bcpNameStr) {
		this.bcpNameStr = bcpNameStr;
	}

	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}

	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
	}

	public ArrayList getCustomerList() {
		return customerList;
	}

	public void setCustomerList(ArrayList customerList) {
		this.customerList = customerList;
	}

	public ArrayList getList() {
		return list;
	}

	public void setList(ArrayList list) {
		this.list = list;
	}

	public ArrayList getBcpExcelList() {
		return bcpExcelList;
	}

	public void setBcpExcelList(ArrayList bcpExcelList) {
		this.bcpExcelList = bcpExcelList;
	}

	public ArrayList getDispatchAddressExcelList() {
		return dispatchAddressExcelList;
	}

	public void setDispatchAddressExcelList(ArrayList dispatchAddressExcelList) {
		this.dispatchAddressExcelList = dispatchAddressExcelList;
	}
	
	public String getToExcel() {
		return toExcel;
	}

	public void setToExcel(String toExcel) {
		this.toExcel = toExcel;
	}

	public String getNetworkLocationIdStr() {
		return networkLocationIdStr;
	}

	public void setNetworkLocationIdStr(String networkLocationIdStr) {
		this.networkLocationIdStr = networkLocationIdStr;
	}

	public ArrayList<CountryDto> getCountries() {
		return countries;
	}

	public void setCountries(ArrayList<CountryDto> countries) {
		this.countries = countries;
	}

	public String getDispatchAddressId() {
		return dispatchAddressId;
	}

	public void setDispatchAddressId(String dispatchAddressId) {
		this.dispatchAddressId = dispatchAddressId;
	}
	public ArrayList getNetworkLocsExcelList() {
		return networkLocsExcelList;
	}

	public void setNetworkLocsExcelList(ArrayList networkLocsExcelList) {
		this.networkLocsExcelList = networkLocsExcelList;
	}

	public String getLocationIdStr() {
		return locationIdStr;
	}

	public void setLocationIdStr(String locationIdStr) {
		this.locationIdStr = locationIdStr;
	}

	public String getLocationNameStr() {
		return locationNameStr;
	}

	public void setLocationNameStr(String locationNameStr) {
		this.locationNameStr = locationNameStr;
	}
	public String getDispatchAddressName() {
		return dispatchAddressName;
	}

	public void setDispatchAddressName(String dispatchAddressName) {
		this.dispatchAddressName = dispatchAddressName;
	}

	public String getDispatchAddressIdStr() {
		return dispatchAddressIdStr;
	}

	public void setDispatchAddressIdStr(String dispatchAddressIdStr) {
		this.dispatchAddressIdStr = dispatchAddressIdStr;
	}

	public String getDispatchAddressStr() {
		return dispatchAddressStr;
	}

	public void setDispatchAddressStr(String dispatchAddressStr) {
		this.dispatchAddressStr = dispatchAddressStr;
	}

public String getContactNameStr() {
		return contactNameStr;
	}

	public void setContactNameStr(String contactNameStr) {
		this.contactNameStr = contactNameStr;
	}

	public String getContactTypeStr() {
		return contactTypeStr;
	}

	public void setContactTypeStr(String contactTypeStr) {
		this.contactTypeStr = contactTypeStr;
	}

	public String getOrderNoStr() {
		return orderNoStr;
	}

	public void setOrderNoStr(String orderNoStr) {
		this.orderNoStr = orderNoStr;
	}

	/**
	 * @return the m6OrderNoStr
	 */
	public String getM6OrderNo() {
		return m6OrderNo;
	}

	/**
	 * @param orderNoStr the m6OrderNoStr to set
	 */
	public void setM6OrderNoStr(String orderNo) {
		m6OrderNo = orderNo;
	}

	/**
	 * @return the orderList
	 */
	public ArrayList getOrderList() {
		return orderList;
	}

	/**
	 * @param orderList the orderList to set
	 */
	public void setOrderList(ArrayList orderList) {
		this.orderList = orderList;
	}

	/**
	 * @return the orderExcelList
	 */
	public ArrayList getOrderExcelList() {
		return orderExcelList;
	}

	/**
	 * @param orderExcelList the orderExcelList to set
	 */
	public void setOrderExcelList(ArrayList orderExcelList) {
		this.orderExcelList = orderExcelList;
	}

	/**
	 * @return the orderNo
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * @param orderNo the orderNo to set
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * @return the created_Date
	 */
	public String getCreated_Date() {
		return created_Date;
	}

	/**
	 * @param created_Date the created_Date to set
	 */
	public void setCreated_Date(String created_Date) {
		this.created_Date = created_Date;
	}

	/**
	 * @return the hdnOrderNo
	 */
	public String getHdnOrderNo() {
		return hdnOrderNo;
	}

	/**
	 * @param hdnOrderNo the hdnOrderNo to set
	 */
	public void setHdnOrderNo(String hdnOrderNo) {
		this.hdnOrderNo = hdnOrderNo;
	}

	
	public String getMasterValue() {
		return masterValue;
	}

	public void setMasterValue(String masterValue) {
		this.masterValue = masterValue;
	}

	public ArrayList<ReportsDto> getHistoryList() {
		return historyList;
	}

	public void setHistoryList(ArrayList<ReportsDto> historyList) {
		this.historyList = historyList;
	}

	public String getAttribiuteId() {
		return attribiuteId;
	}

	public void setAttribiuteId(String attribiuteId) {
		this.attribiuteId = attribiuteId;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getMasterName() {
		return masterName;
	}

	public void setMasterName(String masterName) {
		this.masterName = masterName;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getNewValues() {
		return newValues;
	}

	public void setNewValues(String newValues) {
		this.newValues = newValues;
	}

	public String getOldValues() {
		return oldValues;
	}

	public void setOldValues(String oldValues) {
		this.oldValues = oldValues;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public ArrayList<ReportsDto> getHistoryValueList() {
		return historyValueList;
	}

	public void setHistoryValueList(ArrayList<ReportsDto> historyValueList) {
		this.historyValueList = historyValueList;
	}

	public ArrayList getOrderStageList() {
		return orderStageList;
	}

	public void setOrderStageList(ArrayList orderStageList) {
		this.orderStageList = orderStageList;
	}

	public int getFromAccountNo() {
		return fromAccountNo;
	}

	public void setFromAccountNo(int fromAccountNo) {
		this.fromAccountNo = fromAccountNo;
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

	public void setToOrderNo(int toOrderNo) {
		this.toOrderNo = toOrderNo;
	}

	public int getAccountID() {
		return accountID;
	}

	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}

	public String getPoRecieveDate() {
		return poRecieveDate;
	}

	public void setPoRecieveDate(String poRecieveDate) {
		this.poRecieveDate = poRecieveDate;
	}
	// [001]	Start 

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
	public String getParty() {
		return party;
	}

	public void setParty(String party) {
		this.party = party;
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

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getCrmAccountNo() {
		return crmAccountNo;
	}

	public void setCrmAccountNo(String crmAccountNo) {
		this.crmAccountNo = crmAccountNo;
	}

	public String getAmName() {
		return amName;
	}

	public void setAmName(String amName) {
		this.amName = amName;
	}

	public String getBillingStatus() {
		return billingStatus;
	}

	public void setBillingStatus(String billingStatus) {
		this.billingStatus = billingStatus;
	}

	public String getCircuitStatus() {
		return circuitStatus;
	}

	public void setCircuitStatus(String circuitStatus) {
		this.circuitStatus = circuitStatus;
	}

	public String getCopcApproveDate() {
		return copcApproveDate;
	}

	public void setCopcApproveDate(String copcApproveDate) {
		this.copcApproveDate = copcApproveDate;
	}

	public String getCopcName() {
		return copcName;
	}

	public void setCopcName(String copcName) {
		this.copcName = copcName;
	}

	public int getCustLogicalSI() {
		return custLogicalSI;
	}

	public void setCustLogicalSI(int custLogicalSI) {
		this.custLogicalSI = custLogicalSI;
	}

	public String getEffStartDate() {
		return effStartDate;
	}

	public void setEffStartDate(String effStartDate) {
		this.effStartDate = effStartDate;
	}

	public String getM6OrderDate() {
		return m6OrderDate;
	}

	public void setM6OrderDate(String orderDate) {
		m6OrderDate = orderDate;
	}

	public int getM6OrderNumber() {
		return m6OrderNumber;
	}

	public void setM6OrderNumber(int orderNumber) {
		m6OrderNumber = orderNumber;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getOrderProvision() {
		return orderProvision;
	}

	public void setOrderProvision(String orderProvision) {
		this.orderProvision = orderProvision;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public int getPartyNo() {
		return partyNo;
	}

	public void setPartyNo(int partyNo) {
		this.partyNo = partyNo;
	}

	public String getPmApproveDate() {
		return pmApproveDate;
	}

	public void setPmApproveDate(String pmApproveDate) {
		this.pmApproveDate = pmApproveDate;
	}

	public String getPmName() {
		return pmName;
	}

	public void setPmName(String pmName) {
		this.pmName = pmName;
	}

	public String getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}

	public String getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(String publishedDate) {
		this.publishedDate = publishedDate;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getServiceDetDescription() {
		return serviceDetDescription;
	}

	public void setServiceDetDescription(String serviceDetDescription) {
		this.serviceDetDescription = serviceDetDescription;
	}

	public int getServiceId() {
		return serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceStage() {
		return serviceStage;
	}

	public void setServiceStage(String serviceStage) {
		this.serviceStage = serviceStage;
	}

	public String getStandardReason() {
		return standardReason;
	}

	public void setStandardReason(String standardReason) {
		this.standardReason = standardReason;
	}

	public String getVerticalDetails() {
		return verticalDetails;
	}

	public void setVerticalDetails(String verticalDetails) {
		this.verticalDetails = verticalDetails;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public void setM6OrderNo(String orderNo) {
		m6OrderNo = orderNo;
	}

	public String getAccountManager() {
		return accountManager;
	}

	public void setAccountManager(String accountManager) {
		this.accountManager = accountManager;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAmApproveDate() {
		return amApproveDate;
	}

	public void setAmApproveDate(String amApproveDate) {
		this.amApproveDate = amApproveDate;
	}


	public long getAccountId() {
		return accountId;
	}
	
	
	public String getAct_category() {
		return act_category;
	}

	public void setAct_category(String act_category) {
		this.act_category = act_category;
	}

	public String getActmngname() {
		return actmngname;
	}

	public void setActmngname(String actmngname) {
		this.actmngname = actmngname;
	}

	public String getAmReceiveDate() {
		return amReceiveDate;
	}

	public void setAmReceiveDate(String amReceiveDate) {
		this.amReceiveDate = amReceiveDate;
	}

	public String getBillingBandWidth() {
		return billingBandWidth;
	}

	public void setBillingBandWidth(String billingBandWidth) {
		this.billingBandWidth = billingBandWidth;
	}

	public String getBillUom() {
		return billUom;
	}

	public void setBillUom(String billUom) {
		this.billUom = billUom;
	}

	public String getCancelflag() {
		return cancelflag;
	}

	public void setCancelflag(String cancelflag) {
		this.cancelflag = cancelflag;
	}

	public String getCategoryOfOrder() {
		return categoryOfOrder;
	}

	public void setCategoryOfOrder(String categoryOfOrder) {
		this.categoryOfOrder = categoryOfOrder;
	}

	public String getChallendate() {
		return challendate;
	}

	public void setChallendate(String challendate) {
		this.challendate = challendate;
	}

	public String getChallenno() {
		return challenno;
	}

	public void setChallenno(String challenno) {
		this.challenno = challenno;
	}

	public String getChargeEndDate() {
		return chargeEndDate;
	}

	public void setChargeEndDate(String chargeEndDate) {
		this.chargeEndDate = chargeEndDate;
	}

	public String getCrmACcountNO() {
		return crmACcountNO;
	}

	public void setCrmACcountNO(String crmACcountNO) {
		this.crmACcountNO = crmACcountNO;
	}

	public String getCus_segment() {
		return cus_segment;
	}

	public void setCus_segment(String cus_segment) {
		this.cus_segment = cus_segment;
	}

	public String getCustomerPoDate() {
		return customerPoDate;
	}

	public void setCustomerPoDate(String customerPoDate) {
		this.customerPoDate = customerPoDate;
	}

	public String getCustomerPoNumber() {
		return customerPoNumber;
	}

	public void setCustomerPoNumber(String customerPoNumber) {
		this.customerPoNumber = customerPoNumber;
	}

	public String getCustomerRfsDate() {
		return customerRfsDate;
	}

	public void setCustomerRfsDate(String customerRfsDate) {
		this.customerRfsDate = customerRfsDate;
	}

	public String getCustomerServiceRfsDate() {
		return customerServiceRfsDate;
	}

	public void setCustomerServiceRfsDate(String customerServiceRfsDate) {
		this.customerServiceRfsDate = customerServiceRfsDate;
	}

	public String getCyclicNonCyclic() {
		return cyclicNonCyclic;
	}

	public void setCyclicNonCyclic(String cyclicNonCyclic) {
		this.cyclicNonCyclic = cyclicNonCyclic;
	}

	public String getDemoType() {
		return demoType;
	}

	public void setDemoType(String demoType) {
		this.demoType = demoType;
	}

	public String getFromSite() {
		return fromSite;
	}

	public void setFromSite(String fromSite) {
		this.fromSite = fromSite;
	}

	public int getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	public String getKmsDistance() {
		return kmsDistance;
	}

	public void setKmsDistance(String kmsDistance) {
		this.kmsDistance = kmsDistance;
	}

	public String getLicenceCoName() {
		return licenceCoName;
	}

	public void setLicenceCoName(String licenceCoName) {
		this.licenceCoName = licenceCoName;
	}

	public String getLineItemDescription() {
		return lineItemDescription;
	}

	public void setLineItemDescription(String lineItemDescription) {
		this.lineItemDescription = lineItemDescription;
	}

	public String getLinename() {
		return linename;
	}

	public void setLinename(String linename) {
		this.linename = linename;
	}

	public String getLogicalCircuitNumber() {
		return logicalCircuitNumber;
	}

	public void setLogicalCircuitNumber(String logicalCircuitNumber) {
		this.logicalCircuitNumber = logicalCircuitNumber;
	}

	public String getNewOrderRemark() {
		return newOrderRemark;
	}

	public void setNewOrderRemark(String newOrderRemark) {
		this.newOrderRemark = newOrderRemark;
	}

	public double getOldLineitemAmount() {
		return oldLineitemAmount;
	}

	public void setOldLineitemAmount(double oldLineitemAmount) {
		this.oldLineitemAmount = oldLineitemAmount;
	}

	public String getOrderEntryDate() {
		return orderEntryDate;
	}

	public void setOrderEntryDate(String orderEntryDate) {
		this.orderEntryDate = orderEntryDate;
	}

	public int getOrderLineNumber() {
		return orderLineNumber;
	}

	public void setOrderLineNumber(int orderLineNumber) {
		this.orderLineNumber = orderLineNumber;
	}

	public String getOrderStageDescription() {
		return orderStageDescription;
	}

	public void setOrderStageDescription(String orderStageDescription) {
		this.orderStageDescription = orderStageDescription;
	}

	public double getOrderTotal() {
		return orderTotal;
	}

	public void setOrderTotal(double orderTotal) {
		this.orderTotal = orderTotal;
	}

	public String getPaymentTerm() {
		return paymentTerm;
	}

	public void setPaymentTerm(String paymentTerm) {
		this.paymentTerm = paymentTerm;
	}

	public double getPoAmounts() {
		return poAmounts;
	}

	public void setPoAmounts(double poAmounts) {
		this.poAmounts = poAmounts;
	}

	public String getPrjmngname() {
		return prjmngname;
	}

	public void setPrjmngname(String prjmngname) {
		this.prjmngname = prjmngname;
	}

	public String getProvisionBandWidth() {
		return provisionBandWidth;
	}

	public void setProvisionBandWidth(String provisionBandWidth) {
		this.provisionBandWidth = provisionBandWidth;
	}

	public String getServiceOrderType() {
		return serviceOrderType;
	}

	public void setServiceOrderType(String serviceOrderType) {
		this.serviceOrderType = serviceOrderType;
	}

	public String getServiceStageDescription() {
		return serviceStageDescription;
	}

	public void setServiceStageDescription(String serviceStageDescription) {
		this.serviceStageDescription = serviceStageDescription;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getToSite() {
		return toSite;
	}

	public void setToSite(String toSite) {
		this.toSite = toSite;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public String getVertical() {
		return vertical;
	}

	public void setVertical(String vertical) {
		this.vertical = vertical;
	}

	public String getBillingPODate() {
		return billingPODate;
	}

	public void setBillingPODate(String billingPODate) {
		this.billingPODate = billingPODate;
	}



	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public String getCustPoDate() {
		return custPoDate;
	}

	public void setCustPoDate(String custPoDate) {
		this.custPoDate = custPoDate;
	}

	public String getOrder_type() {
		return order_type;
	}

	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}

	public long getPoAmountSum() {
		return poAmountSum;
	}

	public void setPoAmountSum(long poAmountSum) {
		this.poAmountSum = poAmountSum;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}


	public String getChargeAnnotation() {
		return chargeAnnotation;
	}

	public void setChargeAnnotation(String chargeAnnotation) {
		this.chargeAnnotation = chargeAnnotation;
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

	public String getCustPoDetailNo() {
		return custPoDetailNo;
	}

	public void setCustPoDetailNo(String custPoDetailNo) {
		this.custPoDetailNo = custPoDetailNo;
	}

	public int getEntityID() {
		return entityID;
	}

	public void setEntityID(int entityID) {
		this.entityID = entityID;
	}

	public String getFromLocation() {
		return fromLocation;
	}

	public void setFromLocation(String fromLocation) {
		this.fromLocation = fromLocation;
	}

	public String getFxSiId() {
		return fxSiId;
	}

	public void setFxSiId(String fxSiId) {
		this.fxSiId = fxSiId;
	}

	public int getIsDefaultPO() {
		return isDefaultPO;
	}

	public void setIsDefaultPO(int isDefaultPO) {
		this.isDefaultPO = isDefaultPO;
	}

	public String getLogicalSINo() {
		return logicalSINo;
	}

	public void setLogicalSINo(String logicalSINo) {
		this.logicalSINo = logicalSINo;
	}

	public String getPeriodsInMonths() {
		return periodsInMonths;
	}

	public void setPeriodsInMonths(String periodsInMonths) {
		this.periodsInMonths = periodsInMonths;
	}

	public String getPoDemoContractPeriod() {
		return poDemoContractPeriod;
	}

	public void setPoDemoContractPeriod(String poDemoContractPeriod) {
		this.poDemoContractPeriod = poDemoContractPeriod;
	}

	public String getPoDetailNo() {
		return poDetailNo;
	}

	public void setPoDetailNo(String poDetailNo) {
		this.poDetailNo = poDetailNo;
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

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getTokenNO() {
		return tokenNO;
	}

	public void setTokenNO(String tokenNO) {
		this.tokenNO = tokenNO;
	}

	public String getToLocation() {
		return toLocation;
	}

	public void setToLocation(String toLocation) {
		this.toLocation = toLocation;
	}


	public String getCanceldate() {
		return canceldate;
	}

	public void setCanceldate(String canceldate) {
		this.canceldate = canceldate;
	}

	public int getLogicalsi_no() {
		return logicalsi_no;
	}

	public void setLogicalsi_no(int logicalsi_no) {
		this.logicalsi_no = logicalsi_no;
	}

	public String getServicename() {
		return servicename;
	}

	public void setServicename(String servicename) {
		this.servicename = servicename;
	}

	public String getSubtype() {
		return subtype;
	}

	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}

	public String getOrdermonth() {
		return ordermonth;
	}

	public void setOrdermonth(String ordermonth) {
		this.ordermonth = ordermonth;
	}

	public String getDate_of_installation() {
		return date_of_installation;
	}

	public void setDate_of_installation(String date_of_installation) {
		this.date_of_installation = date_of_installation;
	}

	public String getBillingtrigger_createdate() {
		return billingtrigger_createdate;
	}
	public void setBillingtrigger_createdate(String billingtrigger_createdate) {
		this.billingtrigger_createdate = billingtrigger_createdate;
	}
	public ArrayList getPendingOrderList() {
		return pendingOrderList;
	}
	public void setPendingOrderList(ArrayList pendingOrderList) {
		this.pendingOrderList = pendingOrderList;
	}
	public String getSrNo() {
		return srNo;
	}
	public void setSrNo(String srNo) {
		this.srNo = srNo;
	}
	public String getMappedLSINumber() {
		return mappedLSINumber;
	}
	public void setMappedLSINumber(String mappedLSINumber) {
		this.mappedLSINumber = mappedLSINumber;
	}
	public String getRedLSiNumber() {
		return redLSiNumber;
	}
	public void setRedLSiNumber(String redLSiNumber) {
		this.redLSiNumber = redLSiNumber;
	}

	// [001]	End
		//[202020]START
	public String getCanceldatefrom() {
		return canceldatefrom;
	}
	public void setCanceldatefrom(String canceldatefrom) {
		this.canceldatefrom = canceldatefrom;
	}
	public String getCanceldateto() {
		return canceldateto;
	}
	public void setCanceldateto(String canceldateto) {
		this.canceldateto = canceldateto;
	}
	public String getCopcapprovalfromdate() {
		return copcapprovalfromdate;
	}
	public void setCopcapprovalfromdate(String copcapprovalfromdate) {
		this.copcapprovalfromdate = copcapprovalfromdate;
	}
	public String getCopcapprovaltodate() {
		return copcapprovaltodate;
	}
	public void setCopcapprovaltodate(String copcapprovaltoate) {
		this.copcapprovaltodate = copcapprovaltoate;
	}
 //[202020]END
	// Added by Ramprata regards Billingworkqueuereport
	public String getChangeType() {
		return changeType;
	}
	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}
	// Billingworkqueuereport END
	
//-[HYPR22032013001] -start
	public String getSourcePartyName() {
		return sourcePartyName;
	}
	public void setSourcePartyName(String sourcePartyName) {
		this.sourcePartyName = sourcePartyName;
	}
	public String getIsHardware() {
		return isHardware;
	}
	public void setIsHardware(String isHardware) {
		this.isHardware = isHardware;
	}
//-[HYPR22032013001] -end	
	public String getDate_of_installation_from() {
		return date_of_installation_from;
	}
	public void setDate_of_installation_from(String date_of_installation_from) {
		this.date_of_installation_from = date_of_installation_from;
	}
	public String getDate_of_installation_to() {
		return date_of_installation_to;
	}
	public void setDate_of_installation_to(String date_of_installation_to) {
		this.date_of_installation_to = date_of_installation_to;
	}
	public String getOrderyear() {
		return orderyear;
	}
	public void setOrderyear(String orderyear) {
		this.orderyear = orderyear;
	}

	public ArrayList getHardwareLineItemList() {
	return HardwareLineItemList;
	}
	public void setHardwareLineItemList(ArrayList hardwareLineItemList) {
		HardwareLineItemList = hardwareLineItemList;
	}
	public String getSearchLineItemNo() {
		return searchLineItemNo;
	}
	public void setSearchLineItemNo(String searchLineItemNo) {
		this.searchLineItemNo = searchLineItemNo;
	}
	public String getReportCurrentCount() {
		return reportCurrentCount;
	}
	public void setReportCurrentCount(String reportCurrentCount) {
		this.reportCurrentCount = reportCurrentCount;
	}
	public String getReportProcessForLimit() {
		return reportProcessForLimit;
	}
	public void setReportProcessForLimit(String reportProcessForLimit) {
		this.reportProcessForLimit = reportProcessForLimit;
	}
	public String getInterfaceId() {
		return interfaceId;
	}
	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}
	public String getDumpFileName() {
		return dumpFileName;
	}
	public void setDumpFileName(String dumpFileName) {
		this.dumpFileName = dumpFileName;
	}
	public String getIsDumpAvailable() {
		return isDumpAvailable;
	}
	public void setIsDumpAvailable(String isDumpAvailable) {
		this.isDumpAvailable = isDumpAvailable;
	}
	public String getCopcEndDate() {
		return copcEndDate;
	}
	public void setCopcEndDate(String copcEndDate) {
		this.copcEndDate = copcEndDate;
	}
	public String getChangeServiceId() {
		return changeServiceId;
	}
	public void setChangeServiceId(String changeServiceId) {
		this.changeServiceId = changeServiceId;
	}
	public String getChargeInfoId() {
		return chargeInfoId;
	}
	public void setChargeInfoId(String chargeInfoId) {
		this.chargeInfoId = chargeInfoId;
	}
	public String getServiceProductId() {
		return serviceProductId;
	}
	public void setServiceProductId(String serviceProductId) {
		this.serviceProductId = serviceProductId;
	}
	public String getBillingTriggStatus() {
		return billingTriggStatus;
	}
	public void setBillingTriggStatus(String billingTriggStatus) {
		this.billingTriggStatus = billingTriggStatus;
	}
	public String getCurrencyofOrder() {
		return currencyofOrder;
	}
	public void setCurrencyofOrder(String currencyofOrder) {
		this.currencyofOrder = currencyofOrder;
	}
	public String getCustomerSegment() {
		return customerSegment;
	}
	public void setCustomerSegment(String customerSegment) {
		this.customerSegment = customerSegment;
	}
	public String getCircle() {
		return circle;
	}
	public void setCircle(String circle) {
		this.circle = circle;
	}
	public String getOrderCreationDate() {
		return orderCreationDate;
	}
	public void setOrderCreationDate(String orderCreationDate) {
		this.orderCreationDate = orderCreationDate;
	}
	public String getAmApprovalDate() {
		return amApprovalDate;
	}
	public void setAmApprovalDate(String amApprovalDate) {
		this.amApprovalDate = amApprovalDate;
	}
	public String getPmApprovalDate() {
		return pmApprovalDate;
	}
	public void setPmApprovalDate(String pmApprovalDate) {
		this.pmApprovalDate = pmApprovalDate;
	}
	public String getOrderApprovalDate() {
		return orderApprovalDate;
	}
	public void setOrderApprovalDate(String orderApprovalDate) {
		this.orderApprovalDate = orderApprovalDate;
	}
	public String getLsi() {
		return lsi;
	}
	public void setLsi(String lsi) {
		this.lsi = lsi;
	}
	public String getServiceNo() {
		return serviceNo;
	}
	public void setServiceNo(String serviceNo) {
		this.serviceNo = serviceNo;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getLicenseCompany() {
		return licenseCompany;
	}
	public void setLicenseCompany(String licenseCompany) {
		this.licenseCompany = licenseCompany;
	}
	public String getFxChildAccount() {
		return fxChildAccount;
	}
	public void setFxChildAccount(String fxChildAccount) {
		this.fxChildAccount = fxChildAccount;
	}
	public String getBillingTriggerDate() {
		return billingTriggerDate;
	}
	public void setBillingTriggerDate(String billingTriggerDate) {
		this.billingTriggerDate = billingTriggerDate;
	}
	public String getArcChqNo() {
		return arcChqNo;
	}
	public void setArcChqNo(String arcChqNo) {
		this.arcChqNo = arcChqNo;
	}
	public String getArcChqDate() {
		return arcChqDate;
	}
	public void setArcChqDate(String arcChqDate) {
		this.arcChqDate = arcChqDate;
	}
	public String getOtcChqNo() {
		return otcChqNo;
	}
	public void setOtcChqNo(String otcChqNo) {
		this.otcChqNo = otcChqNo;
	}
	public String getOtcChqDate() {
		return otcChqDate;
	}
	public void setOtcChqDate(String otcChqDate) {
		this.otcChqDate = otcChqDate;
	}
	public String getOtcBankName() {
		return otcBankName;
	}
	public void setOtcBankName(String otcBankName) {
		this.otcBankName = otcBankName;
	}
	public String getFromorderCreationDate() {
		return fromorderCreationDate;
	}
	public void setFromorderCreationDate(String fromorderCreationDate) {
		this.fromorderCreationDate = fromorderCreationDate;
	}
	public String getToorderCreationDate() {
		return toorderCreationDate;
	}
	public void setToorderCreationDate(String toorderCreationDate) {
		this.toorderCreationDate = toorderCreationDate;
	}
	public String getToChqDate() {
		return toChqDate;
	}
	public void setToChqDate(String toChqDate) {
		this.toChqDate = toChqDate;
	}
	public String getDatetype() {
		return datetype;
	}
	public void setDatetype(String datetype) {
		this.datetype = datetype;
	}
	public String getFromotcDate() {
		return fromotcDate;
	}
	public void setFromotcDate(String fromotcDate) {
		this.fromotcDate = fromotcDate;
	}
	public String getToOtcDate() {
		return toOtcDate;
	}
	public void setToOtcDate(String toOtcDate) {
		this.toOtcDate = toOtcDate;
	}
	public double getArcChqAmt() {
		return arcChqAmt;
	}
	public void setArcChqAmt(double arcChqAmt) {
		this.arcChqAmt = arcChqAmt;
	}
	public double getArcAmtAjd() {
		return arcAmtAjd;
	}
	public void setArcAmtAjd(double arcAmtAjd) {
		this.arcAmtAjd = arcAmtAjd;
	}
	public double getOtcChqAmt() {
		return otcChqAmt;
	}
	public void setOtcChqAmt(double otcChqAmt) {
		this.otcChqAmt = otcChqAmt;
	}
	public double getOtcAmtAjd() {
		return otcAmtAjd;
	}
	public void setOtcAmtAjd(double otcAmtAjd) {
		this.otcAmtAjd = otcAmtAjd;
	}
	public String getArcExempted() {
		return arcExempted;
	}
	public void setArcExempted(String arcExempted) {
		this.arcExempted = arcExempted;
	}
	public String getArcExpreason() {
		return arcExpreason;
	}
	public void setArcExpreason(String arcExpreason) {
		this.arcExpreason = arcExpreason;
	}
	public String getOtcExempted() {
		return otcExempted;
	}
	public void setOtcExempted(String otcExempted) {
		this.otcExempted = otcExempted;
	}
	public String getOtcExpreason() {
		return otcExpreason;
	}
	public void setOtcExpreason(String otcExpreason) {
		this.otcExpreason = otcExpreason;
	}
	public String getArcBankName() {
		return arcBankName;
	}
	public void setArcBankName(String arcBankName) {
		this.arcBankName = arcBankName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getSubproductName() {
		return subproductName;
	}
	public void setSubproductName(String subproductName) {
		this.subproductName = subproductName;
	}
	public String getBillingTriggerActionDate() {
		return billingTriggerActionDate;
	}
	public void setBillingTriggerActionDate(String billingTriggerActionDate) {
		this.billingTriggerActionDate = billingTriggerActionDate;
	}
	public int getBusiness_serial_n() {
		return business_serial_n;
	}
	public void setBusiness_serial_n(int business_serial_n) {
		this.business_serial_n = business_serial_n;
	}
	public String getMocn_no() {
		return mocn_no;
	}
	public void setMocn_no(String mocn_no) {
		this.mocn_no = mocn_no;
	}
	public String getOldLsi() {
		return oldLsi;
	}
	public void setOldLsi(String oldLsi) {
		this.oldLsi = oldLsi;
	}
	public String getOpms_Account_Id() {
		return opms_Account_Id;
	}
	public void setOpms_Account_Id(String opms_Account_Id) {
		this.opms_Account_Id = opms_Account_Id;
	}
	public String getOpms_lineItemNumber() {
		return opms_lineItemNumber;
	}
	public void setOpms_lineItemNumber(String opms_lineItemNumber) {
		this.opms_lineItemNumber = opms_lineItemNumber;
	}
	public String getFxAccountInternallId() {
		return fxAccountInternallId;
	}
	public void setFxAccountInternallId(String fxAccountInternallId) {
		this.fxAccountInternallId = fxAccountInternallId;
	}
	public String getLineitemstatus() {
		return lineitemstatus;
	}
	public void setLineitemstatus(String lineitemstatus) {
		this.lineitemstatus = lineitemstatus;
	}
	public ArrayList getServiceNameList() {
		return serviceNameList;
	}
	public void setServiceNameList(ArrayList serviceNameList) {
		this.serviceNameList = serviceNameList;
	}
	public String getSrrequest() {
		return srrequest;
	}
	public void setSrrequest(String srrequest) {
		this.srrequest = srrequest;
	}
	//[001]  
	public int getActiontype() {
		return actiontype;
	}
	public void setActiontype(int actiontype) {
		this.actiontype = actiontype;
	}

	//priya
	public int getFromServiceNo() {
		return fromServiceNo;
	}

	public void setFromServiceNo(int fromServiceNo) {
		this.fromServiceNo = fromServiceNo;
	}

	public int getToServiceNo() {
		return toServiceNo;
	}

	public void setToServiceNo(int toServiceNo) {
		this.toServiceNo = toServiceNo;
	}

	public String getExcludeCompOrders() {
		return excludeCompOrders;
	}

	public void setExcludeCompOrders(String excludeCompOrders) {
		this.excludeCompOrders = excludeCompOrders;
	}

	public Integer getChangeTypeId() {
		return changeTypeId;
	}

	public void setChangeTypeId(Integer changeTypeId) {
		this.changeTypeId = changeTypeId;
	}
	
	
}
