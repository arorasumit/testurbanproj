package com.ibm.ioes.forms;
//Tag Name 		Resource Name  	Date		CSR No			Description
//[001]	 	Rohit Verma		5-Mar-13	00-08440	Report for Hardware Cancel Line Item
//[002]     Neha Maheshwari 14-Apr-15     CSR-20150212-R1-021088  Addition of Column in Active and Pending Line Item Reports
//[003]     Neha Maheshwari 07-Sep-2015 20150819-XX-021651 TimeStamp Reports
import java.util.ArrayList;

import com.ibm.ioes.utilities.PagingSorting;
//[101010] Rampratap added for start date and end date in lepm report
//[202020]  Rampratap added for start date and end date in lepm report
//[303030]  Rampratap added for start date and end date in lepm report
//[HYPR22032013001]    Vijay    30-March-2013          Billing Work Queue Report. Add some fields for this report //
//[RPT7052013025]    Mohit   15-May-2013   Added for Performance report
//[404040]  opportunityId added  by Lawkush in reports "ACTIVE LINE ITEMS REPORT   ","PERFORMANCE SUMMARY REPORT  ","PERFORMANCE DETAIL REPORT","ORDER DETAIL REPORT","ORDER REPORT NEW DETAIL CWN","LEPM ORDER DETAIL REPORT"
//[505050] Vipin Saharia added servOrderCategory, media, onnetOffnet variables  
//[001]     Gunjan Singla        Added interfaceId,actionType
//[606060]      Gunjan Singla    6-May-2016   20160418-R1-022266    Added columns
public class ReportsDto{
	PagingSorting pagingSorting = new PagingSorting();
	private int fromAccountNo=0;
	//by Saurabh
	private String poIssueBy;
	private long noticePeriod;
	private int principalAmount;
	private String remarks;
	private String orderyear;
	private String fromCopcApprovedDate;  //by Saurabh
	private String fromDate;
	private String prodAlisName;
	private String region;
	private String rfsDate;
	private int storeID;
	private int fromOrderNo=0;  //by Saurabh
	private String contractStartDate;
	private String contractEndDate;
	private String custPoDetailNo; //by Saurabh
	private String demo;
	private long sourcePartyID;
	private String fromOrderDate;
	private Long billingLevelNo;
	private String billingLevelNo1;
	private String billingMode;
	private int logicalSINumber;
	private String entity;
	private int newOrderNo;
	private String periodsInMonths;
	private String endDate = "";
	private String searchToDate;
	private String actmgrname;
	private String lst_No;
	private String lstDate;
	private int contractMonths;
	private String startDate = "";
	private String createdBy;
	private String endHWDateLogic;
	private long billingLevelId;
	private String subChangeTypeName;
	private int accountID;
	private String billingFormatName;
	private String billingLevelName;
	private String billingLevel;
	private String logicalSINo;
	private String productName;
	private String quoteNo;
	private String poDate;
	private String poDetailNo;
	private String poReceiveDate;
	private String poAmount;
	private String pmapprovaldate;
	private String penaltyClause;
	private String party_num;
	private String packageName;
	private int packageID;
	private String orderStage;
	private int oldOrderNo;
	
	private String toCrmOrderid;
	private String accountno;
	private String address1;
	private String address2;
	private String address3;
	private int billingInfoID;
	private String chargeAmount_String;
	private String chargeAnnotation;
	private int chargeInfoID;
	private String chargeName;
	private int chargeTypeID;
	private String cityName;
	private String companyName;
	private int componentID;
	private int componentInfoID;
	private String contactName;
	private String countyName;
	private String designation;
	private String dispatchAddress2;
	private String dispatchAddress3;
	private String dispatchAddressName;
	private String dispatchCityName;
	private String party;
	private String acmgrEmail;
	private String billingformat;
	private String chargeFrequency;
	private String currencyCode;
	private String custSINo;
	private String dispatchPhoneNo;
	private String dispatchStateName;
	private int fxInternalId;
	private String licCompanyName;
	
	private String toOrderDate;
	private String billingPODate;
	private String billingTypeName;
	private String changeTypeName;
	private String chargeTypeName;
	private int commitmentPeriod;
	private ComponentsDto componentDto;
	private String componentName;
	private int contractPeriod;
	private String createdDate;
	private String creditPeriodName;
	private int crmAccountNo;
	private String currencyName;
	private int customer_logicalSINumber;
	private String dispatchAddress1;
	private int endDateDays;
	private String endDateLogic;
	private int endDateMonth;
	private String formAvailable;
	private String frequencyName;
	private String fromCrmOrderid;
	private String fromLocation;
	private String fx_external_acc_id;
	private String hardwaretypeName;
	private String LOC_No;
	private String logicalCircuitId;
	private String osp;
	private String LOC_Date;
	private int party_no;
	private String searchfromDate;
	private int toAccountNo=0;	//by Saurabh
	private String toCopcApprovedDate;  //by Saurabh
	private String toDate;
	private int toOrderNo=0;	//by Saurabh
	private String orderNo;
	private String searchCRMOrder;
	private String serviceName;
	private int tokenID;
	private String child_account_creation_status;
	private String charge_status;
	private String fx_sd_charge_status; 
	private String fx_charge_status;
	private String billing_trigger_date;
	private String blSource;
	private double chargesSumOfLineitem;
	private String attribiuteId;
	private String columnName;
	private String masterName;
	private String masterValue;
	private String modifiedBy;
	private String modifiedDate;
	private String newValues;
	private String oldValues;
	private String operationName;
	private ArrayList<String> columnList;
	private ArrayList<String> oldValueList;
	private ArrayList<String> newValueList;
	private int orderNumber;
	private String orderType;
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
	//[303030] START
	private String copcApproveFromDate;
	private String copcApproveToDate;
	//[303030]END
	private String circuitStatus;
	private String orderProvision;
	private String verticalDetails;
	private int serviceId;
	private int m6OrderNumber;
	private int custLogicalSI;
	private int partyNo;
	private String parentOrderSubType;
	private double annual_rate;
	private String annual_rateString;
	private String dayInSED;
	private String totalDays;
	private String plocationtype;
	private String slocationtype;
	private String actmngname;
	private String prjmngname;
	private String billPeriod;
	private String primaryLocation;
	private String secondaryLocation;
	private String fxStatus;	
	private String billingTriggerDate;
	private String ChildAccountFXSataus;
	private String extSuportEndDate;
	private String hardwareFlag;
	private String billingAddress;	
	private String dnd_Dispatch_But_Not_Delivered;
	private String dnd_Dispatch_And_Delivered;
	private String dnd_Disp_Del_Not_Installed;
	private String dnd_Disp_Delivered_Installed;	
	private String recordStatus;
	private String frequencyAmt;
	private String postalCode;
	private String warrantyStartDateLogic;
	private String warrantyPeriodMonths;
	private String warrantyPeriodDays;
	private String warrantyStartDate;	
	private String warrantyEndDateLogic;	
	private String warrantyEndPeriodMonths;	
	private String warrantyEndPeriodDays;	
	private String warrantyEndDate;
	private String extndSupportPeriodMonths;
	private String extndSupportPeriodDays;
	private String poExclude;
	private String Address4;
	private String fax;
	private String telePhoneNo;
	private String dispatchPostalCode;
	private String dispatchPersonName;
	private String dispatchLstNumber;
	private String dispatchLstDate;
	private String Fx_St_Chg_Status;
	private String Fx_Ed_Chg_Status;    
    private String Billing_Trigger_Flag;
    private String cancelBy; 
    private String cancelReason;
    private String opms_Account_Id;
    private String lineItemAmount;
    private String coll_Manager;
    private String coll_Manager_Mail;
    private String coll_Manager_Phone; 
    private String tokenNoEd;
	private String vertical;
	private String cus_segment;
	private String act_category;
	private String linename;
	private String challenno;
	private String challendate;
	private String m6cktid;
	private String prjmgremail;
	private String crmAccountNoString;
	private long accountId;
	private String order_type;
	private String custPoDate;
	private String poRecieveDate;
	private long poAmountSum;
	private String poAmountSumString;
	private String industrySegment;
	private String dayInAM;
	private String dayInPM;
	private String dayInCOPC;
	private String ordersubtype;
	private String lcompanyname;
	private String storename;
	private String line_desc;
	private String effDate;
	private String rfs_date;
	private String amapprovaldate;
	private double chargeAmount;
	private String contactCell;
	private String copcapprovaldate;
	private int crmAccountId;
	private String entityId;
	private String hardwareType;
	private int interestRate;
	private int isDefaultPO;
	private String nio_approve_date;
	private String demo_infrastartdate;
	private String demo_infra_enddate;
	private String ordersubline;
	private String oldordertotal;
	private String oldlineamt;
	private String from_city;
	private String to_city;
	private String parent_name;
	private String crm_productname;
	private String ordercategory;
	private String billing_uom;
	private String bandwaidth;
	private long ponum;
	private String ponum1;
	private int poNumber;
	private int creditPeriod;
	private String poDemoContractPeriod;
	private String poEmailId;
	private double chargeAmount_Double;
	
	private String billing_bandwidth;
	private String distance;
	private String ratio;
	private String po_recpt_delay;
	private Double po_recpt_delay_double;
	private String po_logging_delay;
	private Double po_logging_delay_double;
	private String order_completion_date;
    private Double order_completion_date_double;
	private String appr_delay_in_region;
	private Double appr_delay_in_region_double;
	private String days_in_copc;
	private Double days_in_copc_double;
	private String days_for_order;
	private Double days_for_order_double;
	private String canceldate;
	//[202020] START
	private String canceldatefrom;
	private String canceldateto;
	//[202020] END
	private String ordertype_demo;
	private String bisource;
	private double lineamt;
	private String lineamtString;
	private String hardware_flag;
	private String primarylocation;
	private String seclocation;
	private String child_ac_fxstatus;
	private int lineno;
	private String ckt_id;
	private String bill_period;
	private String location_from;
	private String location_to;
	private String annitation;
	private int party_id;
	private double cust_total_poamt;
	private String cust_total_poamtString;
	private String cancelServiceReason;
	private String tokenno;
	private String fx_status;
	private String fx_sd_status;
	private String fx_ed_status;
	private String billing_address;
	private String cust_name;
	private String factory_ckt_id;
	private String billingtrigger_createdate;
	private int pre_crmorderid;
	private String disconnection_remarks;
	private int serviceproductid = 0;
	private String saleTypeName;
	private String stateName;
	private String store;
	private String taxation;
	private int txtStartDays;
	private int newServiceNo;
	private int oldServiceNo;
	private String serviceTypeDescription;
	private int	txtStartMonth;
	private String zone;
	private String toLocation;
	private String StoreName;
	private String subProductName;
	private String taxationName;
	private double totalAmountIncludingCurrent;
	private String totalAmountIncludingCurrentString;
	private String totalPoAmt;
	
	private String startDateLogic;
	private int startDateMonth;
	private String serviceIdString;
	private int rootOrderNo;
	private	String saleNature;
	private	String saleType;
	
	private String neworder_remarks;
	private String request_rec_date;
	private String standard_reason;
	private String pm_pro_date;
	private String billing_location_from;
	private String billing_location_to;
	private String rate_code;
	private String last_mile_media;
	private String last_mile_remarks;
	private String link_type;
	private String indicative_value;
	private String last_mile_provider;
	private String billing_location;
	private String opms_act_id;
	private String mocn_no;
	private String child_act_no;
	private String approvalType;
	private String business_serial_no;
	private String date_of_installation_from;
	private String date_of_installation_to;
	private String lb_service_id;
	private String lb_pk_charge_id;
	private String pk_charge_id;
	private String old_contract_period;
	private String sub_linename;
	private String installation_addressaa1;
	private String installation_addressaa2;
	private String installation_addressaa3;
	private String installation_addressab1;
	private String installation_addressab2;
	private String installation_addressab3;
	private String from_state;
	private int chargePeriod;
	private String from_country;
	
	private String m6OrderNo2;
	private int m6OrderNo;
	private int startDateDays;
	private String stageName;
	private String sourceName;
	private String serviceType;
	private int serviceProductID;
	private String to_state;
	private String to_country;
	private String date_of_act;
	private String date_of_inst;
	private String parent_circuit;
	private String ipls;
	private String tl;
	private String service_provider;
	private String hub_location;
	private String commited_sla;
	private String platform;
	private String managed_yes_no;
	private String lob;
	private String circle;
	private int si_id;
	private String crm_att;
	private String m6_label_name;
	private String m6_label_value;
	private int crm_order_id;
	private String ordermonth;
	private String nature_sale;
	private String type_sale;
	private String cust_po_rec_date;
	private String srno;
	private String bandwidth_att;
	private String chargeable_Distance;
	private int charge_hdr_id;
	private long amt;
	private String advance;
	private String annualRate;
	private String cust_act_id;
	private String m6_prod_id;
	private String m6_order_id;
     private String minimum_bandwidth;
     private String minimum_bandwidth_UOM;
     private String activeDate;
     private String inActiveDate;
     private String attributeLabel;
     private String attributeValue;
  //-------Below properties added by Anil --------------      
     	private String componentType;
     	private double componentRCNRCAmount;    
     	private long fx_internal_acc_id;
     //----------------------------------------------------
     private String accountSegment;;
     private String billCompany;
     private String billing_address2;
     private String billCity;
     private String billState;
     private String contact1_Phone;
     private String contact2_Phone;
     private String billZip;
     private String contactPersonEmail;
     private String chairPersonName;
     private String chairPersonPhone;
     private String chairPersonEmail;
     private String componentActiveDate;
     private String businessSegment;
     private String accountMgrPhoneNo;
	 private String am_approval_remarks;
	 private String pm_approval_remarks;
	 private String copc_approval_remarks;
	 private int isUsage;
	 private String chargeinfoID;
	 private String poExpiryDate;
	 private String start_fx_no;
	 private String end_fx_no;
		
//	 manisha start
		private String searchSRNO;
		private String  searchLSI;
		private String srDate;
		 private String disdate;
		// manisha end
		 
	/*
	 * [HYPR22032013001] start
	 * for billing work queue report
	 */	 
    private String discount;
    
    private String installRate;
    
    private String searchAccount;
    private String searchAccountName;
    private String lineItemName;
    private String userId;
	
    /*[HYPR22032013001] end */
    
    //added by Anil on 25-April-13
    private String lastApprovalRemarks;
	
	    /*Vijay. add two more fields accroding to CRM Zero order value report */
    private String contractStartDateCal;
	private String contractEndDateCal;
    /*Endf of adding field */
    private int serviceTypeId;
    private String oldLsi;
	private String serviceTypeName;
	    		 //[001]  Start
		 private String searchLineNo;
		 private ArrayList<String> requestHistoryList;
		 //[001]  End
		 private String  taxExcemption_Reason;
		 
//[404040] Start  
		 
		 private String opportunityId;		 
		 private String interfaceId;
		 private String actionType;
	//[002] Start
	private String installationFromCity;
    private String installationToCity;
	private String installationFromState;
	private String installationToState;
	private String billingContactName;
	private String billingContactNumber;
	private String billingEmailId;
	//[002] End
	//[003] Start
	private String orderCreationDate;
	//[003] End
	//[606060] start
	private String fieldEngineer;
	private String partnerCode;
	private String channelPartner;
	//[606060] end
	
		 public String getActionType() {
			return actionType;
		}
		public void setActionType(String actionType) {
			this.actionType = actionType;
		}
			public String getInterfaceId() {
			return interfaceId;
		}
		public void setInterfaceId(String interfaceId) {
			this.interfaceId = interfaceId;
			}
		 public String getOpportunityId() {
			return opportunityId;
		}
		public void setOpportunityId(String opportunityId) {
			this.opportunityId = opportunityId;
		}
//		[404040] end  
		 //[505050] Start
		 private String servOrderCategory;
		 private String media;
		 private String OnnetOffnet;
		 //[505050] End

		public String getSearchLineNo() {
			return searchLineNo;
		}
		public void setSearchLineNo(String searchLineNo) {
			this.searchLineNo = searchLineNo;
		}
		public ArrayList<String> getRequestHistoryList() {
		return requestHistoryList;
		}
		public void setRequestHistoryList(ArrayList<String> requestHistoryList) {
			this.requestHistoryList = requestHistoryList;
		}
	//rohit end
	 public String getPoExpiryDate() {
		return poExpiryDate;
	}
	public void setPoExpiryDate(String poExpiryDate) {
		this.poExpiryDate = poExpiryDate;
	}
	 public int getIsUsage() {
		return isUsage;
	}
	public void setIsUsage(int isUsage) {
		this.isUsage = isUsage;
	}
	
	private String crm_service_opms_id;
 public String getCrm_service_opms_id() {
		return crm_service_opms_id;
	}
	public void setCrm_service_opms_id(String crm_service_opms_id) {
		this.crm_service_opms_id = crm_service_opms_id;
	}
public String getRecordStatus() {
		return recordStatus;
	}
	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}
public String getWarrantyPeriodDays() {
		return warrantyPeriodDays;
	}
	public void setWarrantyPeriodDays(String warrantyPeriodDays) {
		this.warrantyPeriodDays = warrantyPeriodDays;
	}
public String getWarrantyEndDate() {
		return warrantyEndDate;
	}
	public void setWarrantyEndDate(String warrantyEndDate) {
		this.warrantyEndDate = warrantyEndDate;
	}
	public String getWarrantyEndDateLogic() {
		return warrantyEndDateLogic;
	}
	public void setWarrantyEndDateLogic(String warrantyEndDateLogic) {
		this.warrantyEndDateLogic = warrantyEndDateLogic;
	}
	public String getWarrantyEndPeriodDays() {
		return warrantyEndPeriodDays;
	}
	public void setWarrantyEndPeriodDays(String warrantyEndPeriodDays) {
		this.warrantyEndPeriodDays = warrantyEndPeriodDays;
	}
	public String getWarrantyEndPeriodMonths() {
		return warrantyEndPeriodMonths;
	}
	public void setWarrantyEndPeriodMonths(String warrantyEndPeriodMonths) {
		this.warrantyEndPeriodMonths = warrantyEndPeriodMonths;
	}
	public String getWarrantyPeriodMonths() {
		return warrantyPeriodMonths;
	}
	public void setWarrantyPeriodMonths(String warrantyPeriodMonths) {
		this.warrantyPeriodMonths = warrantyPeriodMonths;
	}
	public String getWarrantyStartDate() {
		return warrantyStartDate;
	}
	public void setWarrantyStartDate(String warrantyStartDate) {
		this.warrantyStartDate = warrantyStartDate;
	}
	public String getWarrantyStartDateLogic() {
		return warrantyStartDateLogic;
	}
	public void setWarrantyStartDateLogic(String warrantyStartDateLogic) {
		this.warrantyStartDateLogic = warrantyStartDateLogic;
	}
public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
public String getFrequencyAmt() {
		return frequencyAmt;
	}
	public void setFrequencyAmt(String frequencyAmt) {
		this.frequencyAmt = frequencyAmt;
	}
public int getSi_id() {
		return si_id;
	}
	public void setSi_id(int si_id) {
		this.si_id = si_id;
	}
public String getCircle() {
		return circle;
	}
	public void setCircle(String circle) {
		this.circle = circle;
	}
public String getTelePhoneNo() {
		return telePhoneNo;
	}
	public void setTelePhoneNo(String telePhoneNo) {
		this.telePhoneNo = telePhoneNo;
	}
public String getCommited_sla() {
		return commited_sla;
	}
	public void setCommited_sla(String commited_sla) {
		this.commited_sla = commited_sla;
	}
	public String getAddress4() {
		return Address4;
	}
	public void setAddress4(String address4) {
		Address4 = address4;
	}
	public String getHub_location() {
		return hub_location;
	}
	public void setHub_location(String hub_location) {
		this.hub_location = hub_location;
	}
	public String getExtndSupportPeriodDays() {
		return extndSupportPeriodDays;
	}
	public void setExtndSupportPeriodDays(String extndSupportPeriodDays) {
		this.extndSupportPeriodDays = extndSupportPeriodDays;
	}
	public String getExtndSupportPeriodMonths() {
		return extndSupportPeriodMonths;
	}
	public void setExtndSupportPeriodMonths(String extndSupportPeriodMonths) {
		this.extndSupportPeriodMonths = extndSupportPeriodMonths;
	}
	public String getIpls() {
		return ipls;
	}
	public void setIpls(String ipls) {
		this.ipls = ipls;
	}
	public String getLob() {
		return lob;
	}
	public void setLob(String lob) {
		this.lob = lob;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getManaged_yes_no() {
		return managed_yes_no;
	}
	public void setManaged_yes_no(String managed_yes_no) {
		this.managed_yes_no = managed_yes_no;
	}
	
	public String getParent_circuit() {
		return parent_circuit;
	}
	public void setParent_circuit(String parent_circuit) {
		this.parent_circuit = parent_circuit;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getService_provider() {
		return service_provider;
	}
	public void setService_provider(String service_provider) {
		this.service_provider = service_provider;
	}
	public String getTl() {
		return tl;
	}
	public void setTl(String tl) {
		this.tl = tl;
	}
public String getDate_of_act() {
		return date_of_act;
	}
	public void setDate_of_act(String date_of_act) {
		this.date_of_act = date_of_act;
	}
	public String getDate_of_inst() {
		return date_of_inst;
	}
	public void setDate_of_inst(String date_of_inst) {
		this.date_of_inst = date_of_inst;
	}
public String getSub_linename() {
		return sub_linename;
	}
	public void setSub_linename(String sub_linename) {
		this.sub_linename = sub_linename;
	}
public String getOld_contract_period() {
		return old_contract_period;
	}
	public void setOld_contract_period(String old_contract_period) {
		this.old_contract_period = old_contract_period;
	}
public String getBilling_location_from() {
		return billing_location_from;
	}
	public void setBilling_location_from(String billing_location_from) {
		this.billing_location_from = billing_location_from;
	}
	public String getBilling_location_to() {
		return billing_location_to;
	}
	public void setBilling_location_to(String billing_location_to) {
		this.billing_location_to = billing_location_to;
	}
public String getRequest_rec_date() {
		return request_rec_date;
	}
	public void setRequest_rec_date(String request_rec_date) {
		this.request_rec_date = request_rec_date;
	}
	public String getStandard_reason() {
		return standard_reason;
	}
	public void setStandard_reason(String standard_reason) {
		this.standard_reason = standard_reason;
	}
public String getDisconnection_remarks() {
		return disconnection_remarks;
	}
	public void setDisconnection_remarks(String disconnection_remarks) {
		this.disconnection_remarks = disconnection_remarks;
	}
	public String getNeworder_remarks() {
		return neworder_remarks;
	}
	public void setNeworder_remarks(String neworder_remarks) {
		this.neworder_remarks = neworder_remarks;
	}
	
public int getPre_crmorderid() {
		return pre_crmorderid;
	}
	public void setPre_crmorderid(int pre_crmorderid) {
		this.pre_crmorderid = pre_crmorderid;
	}
public String getApprovalType() {
		return approvalType;
	}
	public void setApprovalType(String approvalType) {
		this.approvalType = approvalType;
	}
public String getBillingtrigger_createdate() {
		return billingtrigger_createdate;
	}
	public void setBillingtrigger_createdate(String billingtrigger_createdate) {
		this.billingtrigger_createdate = billingtrigger_createdate;
	}
public String getCust_name() {
		return cust_name;
	}
	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}
public String getBilling_address() {
		return billing_address;
	}
	public void setBilling_address(String billing_address) {
		this.billing_address = billing_address;
	}
	public String getCancelServiceReason() {
		return cancelServiceReason;
	}
	public void setCancelServiceReason(String cancelServiceReason) {
		this.cancelServiceReason = cancelServiceReason;
	}
	public double getCust_total_poamt() {
		return cust_total_poamt;
	}
	public void setCust_total_poamt(double cust_total_poamt) {
		this.cust_total_poamt = cust_total_poamt;
	}
	public String getFx_ed_status() {
		return fx_ed_status;
	}
	public void setFx_ed_status(String fx_ed_status) {
		this.fx_ed_status = fx_ed_status;
	}
	public String getFx_sd_status() {
		return fx_sd_status;
	}
	public void setFx_sd_status(String fx_sd_status) {
		this.fx_sd_status = fx_sd_status;
	}
	public String getFx_status() {
		return fx_status;
	}
	public void setFx_status(String fx_status) {
		this.fx_status = fx_status;
	}
	public int getParty_id() {
		return party_id;
	}
	public void setParty_id(int party_id) {
		this.party_id = party_id;
	}
	public String getTokenno() {
		return tokenno;
	}
	public void setTokenno(String tokenno) {
		this.tokenno = tokenno;
	}
    public String getAnnitation() {
		return annitation;
	}
	public void setAnnitation(String annitation) {
		this.annitation = annitation;
	}
	public String getBill_period() {
		return bill_period;
	}
	public void setBill_period(String bill_period) {
		this.bill_period = bill_period;
	}
	public String getChild_ac_fxstatus() {
		return child_ac_fxstatus;
	}
	public void setChild_ac_fxstatus(String child_ac_fxstatus) {
		this.child_ac_fxstatus = child_ac_fxstatus;
	}
	public String getCkt_id() {
		return ckt_id;
	}
	public void setCkt_id(String ckt_id) {
		this.ckt_id = ckt_id;
	}
	public String getHardware_flag() {
		return hardware_flag;
	}
	public void setHardware_flag(String hardware_flag) {
		this.hardware_flag = hardware_flag;
	}
	public double getLineamt() {
		return lineamt;
	}
	public void setLineamt(double lineamt) {
		this.lineamt = lineamt;
	}
	public int getLineno() {
		return lineno;
	}
	public void setLineno(int lineno) {
		this.lineno = lineno;
	}
	public String getLocation_from() {
		return location_from;
	}
	public void setLocation_from(String location_from) {
		this.location_from = location_from;
	}
	public String getLocation_to() {
		return location_to;
	}
	public void setLocation_to(String location_to) {
		this.location_to = location_to;
	}
	public String getPrimarylocation() {
		return primarylocation;
	}
	public void setPrimarylocation(String primarylocation) {
		this.primarylocation = primarylocation;
	}
	public String getSeclocation() {
		return seclocation;
	}
	public void setSeclocation(String seclocation) {
		this.seclocation = seclocation;
	}
	public String getBisource() {
		return bisource;
	}
	public void setBisource(String bisource) {
		this.bisource = bisource;
	}
	public String getOrdertype_demo() {
		return ordertype_demo;
	}
	public void setOrdertype_demo(String ordertype_demo) {
		this.ordertype_demo = ordertype_demo;
	}
	public String getBillingTriggerDate() {
		return billingTriggerDate;
	}
	public void setBillingTriggerDate(String billingTriggerDate) {
		this.billingTriggerDate = billingTriggerDate;
	}
	public String getBillPeriod() {
		return billPeriod;
	}
	public void setBillPeriod(String billPeriod) {
		this.billPeriod = billPeriod;
	}
	public String getChildAccountFXSataus() {
		return ChildAccountFXSataus;
	}
	public void setChildAccountFXSataus(String childAccountFXSataus) {
		ChildAccountFXSataus = childAccountFXSataus;
	}
	public String getExtSuportEndDate() {
		return extSuportEndDate;
	}
	public void setExtSuportEndDate(String extSuportEndDate) {
		this.extSuportEndDate = extSuportEndDate;
	}
	public String getFxStatus() {
		return fxStatus;
	}
	public void setFxStatus(String fxStatus) {
		this.fxStatus = fxStatus;
	}
	public String getHardwareFlag() {
		return hardwareFlag;
	}
	public void setHardwareFlag(String hardwareFlag) {
		this.hardwareFlag = hardwareFlag;
	}
	public String getTokenNoEd() {
		return tokenNoEd;
	}
	public void setTokenNoEd(String tokenNoEd) {
		this.tokenNoEd = tokenNoEd;
	}
	public String getBillingAddress() {
		return billingAddress;
	}
	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}
	public String getDnd_Disp_Del_Not_Installed() {
		return dnd_Disp_Del_Not_Installed;
	}
	public void setDnd_Disp_Del_Not_Installed(String dnd_Disp_Del_Not_Installed) {
		this.dnd_Disp_Del_Not_Installed = dnd_Disp_Del_Not_Installed;
	}
	public String getDnd_Disp_Delivered_Installed() {
		return dnd_Disp_Delivered_Installed;
	}
	public void setDnd_Disp_Delivered_Installed(String dnd_Disp_Delivered_Installed) {
		this.dnd_Disp_Delivered_Installed = dnd_Disp_Delivered_Installed;
	}
	public String getDnd_Dispatch_And_Delivered() {
		return dnd_Dispatch_And_Delivered;
	}
	public void setDnd_Dispatch_And_Delivered(String dnd_Dispatch_And_Delivered) {
		this.dnd_Dispatch_And_Delivered = dnd_Dispatch_And_Delivered;
	}
	public String getDnd_Dispatch_But_Not_Delivered() {
		return dnd_Dispatch_But_Not_Delivered;
	}
	public void setDnd_Dispatch_But_Not_Delivered(
			String dnd_Dispatch_But_Not_Delivered) {
		this.dnd_Dispatch_But_Not_Delivered = dnd_Dispatch_But_Not_Delivered;
	}
	public String getBilling_Trigger_Flag() {
		return Billing_Trigger_Flag;
	}
	public void setBilling_Trigger_Flag(String billing_Trigger_Flag) {
		Billing_Trigger_Flag = billing_Trigger_Flag;
	}
	public String getCancelBy() {
		return cancelBy;
	}
	public void setCancelBy(String cancelBy) {
		this.cancelBy = cancelBy;
	}
	public String getCancelReason() {
		return cancelReason;
	}
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
	public String getOpms_Account_Id() {
		return opms_Account_Id;
	}
	public void setOpms_Account_Id(String opms_Account_Id) {
		this.opms_Account_Id = opms_Account_Id;
	}
	public String getLineItemAmount() {
		return lineItemAmount;
	}
	public void setLineItemAmount(String lineItemAmount) {
		this.lineItemAmount = lineItemAmount;
	}
	public String getColl_Manager() {
		return coll_Manager;
	}
	public void setColl_Manager(String coll_Manager) {
		this.coll_Manager = coll_Manager;
	}
	public String getColl_Manager_Mail() {
		return coll_Manager_Mail;
	}
	public void setColl_Manager_Mail(String coll_Manager_Mail) {
		this.coll_Manager_Mail = coll_Manager_Mail;
	}
	public String getColl_Manager_Phone() {
		return coll_Manager_Phone;
	}
	public void setColl_Manager_Phone(String coll_Manager_Phone) {
		this.coll_Manager_Phone = coll_Manager_Phone;
	}
	public String getFx_Ed_Chg_Status() {
		return Fx_Ed_Chg_Status;
	}
	public void setFx_Ed_Chg_Status(String fx_Ed_Chg_Status) {
		Fx_Ed_Chg_Status = fx_Ed_Chg_Status;
	}
	public String getFx_St_Chg_Status() {
		return Fx_St_Chg_Status;
	}
	public void setFx_St_Chg_Status(String fx_St_Chg_Status) {
		Fx_St_Chg_Status = fx_St_Chg_Status;
	}
	public String getPrimaryLocation() {
		return primaryLocation;
	}
	public void setPrimaryLocation(String primaryLocation) {
		this.primaryLocation = primaryLocation;
	}
	public String getSecondaryLocation() {
		return secondaryLocation;
	}
	public void setSecondaryLocation(String secondaryLocation) {
		this.secondaryLocation = secondaryLocation;
	}
	public String getCanceldate() {
		return canceldate;
	}
	public void setCanceldate(String canceldate) {
		this.canceldate = canceldate;
	}

	//lawkush start
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
	private String oldLineitemAmount;
	private String demoType;
	private String orderStageDescription;
	private String serviceStageDescription;
	private String chargeEndDate;
	private String newOrderRemark;
	private String serviceOrderType;
	private String crmACcountNO;
	private double poAmounts;
	private String logicalCircuitNumber;
	private String locDate;
	private String orderApproveDate;
	private String taskName;
	private String actualStartDate;
	private String actualEndDate;
	private int taskNumber;
	private String owner;
	private String userName;
	private String outCome;
	private String emailId;
	private String fxSiId;
	private String tokenNO;
	private String poEndDate;
	private String lastUpdatedDate;
	private String lastUpdatedBy;
	private String serviceSegment;
	private String serviceSubTypeName;
	private String billingTriggerFlag;
	private String startDaysLogic;
	private String startMonthsLogic;
	private String salesCoordinator;
	private String locno;
	//lawkush end
	
	/*Vijay start
	 * add some more fiels for pending line report and active line report
	 */
	private String asiteAdd1;
	private String asiteAdd2;
	private String asiteAdd3;
	private String bsiteName;
	private String bsiteLineAdd1;
	private String bsiteLineAdd2;
	/*Vijay end*/
	
	public String getLocno() {
		return locno;
	}
	public void setLocno(String locno) {
		this.locno = locno;
	}
	public String getServiceSubTypeName() {
		return serviceSubTypeName;
	}
	public void setServiceSubTypeName(String serviceSubTypeName) {
		this.serviceSubTypeName = serviceSubTypeName;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
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
	public String getLocDate() {
		return locDate;
	}
	public void setLocDate(String locDate) {
		this.locDate = locDate;
	}

	public String getBandwaidth() {
		return bandwaidth;
	}
	public void setBandwaidth(String bandwaidth) {
		this.bandwaidth = bandwaidth;
	}
	public String getBilling_bandwidth() {
		return billing_bandwidth;
	}
	public void setBilling_bandwidth(String billing_bandwidth) {
		this.billing_bandwidth = billing_bandwidth;
	}
	public String getBilling_uom() {
		return billing_uom;
	}
	public void setBilling_uom(String billing_uom) {
		this.billing_uom = billing_uom;
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
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public String getCrm_productname() {
		return crm_productname;
	}
	public void setCrm_productname(String crm_productname) {
		this.crm_productname = crm_productname;
	}
	public String getDemo_infra_enddate() {
		return demo_infra_enddate;
	}
	public void setDemo_infra_enddate(String demo_infra_enddate) {
		this.demo_infra_enddate = demo_infra_enddate;
	}
	public String getDemo_infrastartdate() {
		return demo_infrastartdate;
	}
	public void setDemo_infrastartdate(String demo_infrastartdate) {
		this.demo_infrastartdate = demo_infrastartdate;
	}
	public String getFrom_city() {
		return from_city;
	}
	public void setFrom_city(String from_city) {
		this.from_city = from_city;
	}
	public String getLine_desc() {
		return line_desc;
	}
	public void setLine_desc(String line_desc) {
		this.line_desc = line_desc;
	}
	public String getNio_approve_date() {
		return nio_approve_date;
	}
	public void setNio_approve_date(String nio_approve_date) {
		this.nio_approve_date = nio_approve_date;
	}
	public String getOldlineamt() {
		return oldlineamt;
	}
	public void setOldlineamt(String oldlineamt) {
		this.oldlineamt = oldlineamt;
	}
	public String getOldordertotal() {
		return oldordertotal;
	}
	public void setOldordertotal(String oldordertotal) {
		this.oldordertotal = oldordertotal;
	}
	public String getOrdersubline() {
		return ordersubline;
	}
	public void setOrdersubline(String ordersubline) {
		this.ordersubline = ordersubline;
	}
	public String getParent_name() {
		return parent_name;
	}
	public void setParent_name(String parent_name) {
		this.parent_name = parent_name;
	}
	public String getTo_city() {
		return to_city;
	}
	public void setTo_city(String to_city) {
		this.to_city = to_city;
	}
	public String getLcompanyname() {
		return lcompanyname;
	}
	public void setLcompanyname(String lcompanyname) {
		this.lcompanyname = lcompanyname;
	}
	public String getStorename() {
		return storename;
	}
	public void setStorename(String storename) {
		this.storename = storename;
	}
	public String getDayInAM() {
		return dayInAM;
	}
	public void setDayInAM(String dayInAM) {
		this.dayInAM = dayInAM;
	}
	public String getDayInCOPC() {
		return dayInCOPC;
	}
	public void setDayInCOPC(String dayInCOPC) {
		this.dayInCOPC = dayInCOPC;
	}
	public String getDayInSED() {
		return dayInSED;
	}
	public void setDayInSED(String dayInSED) {
		this.dayInSED = dayInSED;
	}
	public String getTotalDays() {
		return totalDays;
	}
	public void setTotalDays(String totalDays) {
		this.totalDays = totalDays;
	}
	public String getDayInPM() {
		return dayInPM;
	}
	public void setDayInPM(String dayInPM) {
		this.dayInPM = dayInPM;
	}

	public String getM6cktid() {
		return m6cktid;
	}
	public void setM6cktid(String m6cktid) {
		this.m6cktid = m6cktid;
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
	public String getCus_segment() {
		return cus_segment;
	}
	public void setCus_segment(String cus_segment) {
		this.cus_segment = cus_segment;
	}
	public String getLinename() {
		return linename;
	}
	public void setLinename(String linename) {
		this.linename = linename;
	}
	public String getPrjmngname() {
		return prjmngname;
	}
	public void setPrjmngname(String prjmngname) {
		this.prjmngname = prjmngname;
	}
	public String getVertical() {
		return vertical;
	}
	public void setVertical(String vertical) {
		this.vertical = vertical;
	}
	public String getParentOrderSubType() {
		return parentOrderSubType;
	}
	public void setParentOrderSubType(String parentOrderSubType) {
		this.parentOrderSubType = parentOrderSubType;
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
	public String getMasterValue() {
		return masterValue;
	}
	public void setMasterValue(String masterValue) {
		this.masterValue = masterValue;
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
	public ArrayList<String> getColumnList() {
		return columnList;
	}
	public void setColumnList(ArrayList<String> columnList) {
		this.columnList = columnList;
	}
	public ArrayList<String> getNewValueList() {
		return newValueList;
	}
	public void setNewValueList(ArrayList<String> newValueList) {
		this.newValueList = newValueList;
	}
	public ArrayList<String> getOldValueList() {
		return oldValueList;
	}
	public void setOldValueList(ArrayList<String> oldValueList) {
		this.oldValueList = oldValueList;
	}
	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}
	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
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
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
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

	public String getAmReceiveDate() {
		return amReceiveDate;
	}
	public void setAmReceiveDate(String amReceiveDate) {
		this.amReceiveDate = amReceiveDate;
	}
	
	public String getBillUom() {
		return billUom;
	}
	public void setBillUom(String billUom) {
		this.billUom = billUom;
	}
	
	public String getCategoryOfOrder() {
		return categoryOfOrder;
	}
	public void setCategoryOfOrder(String categoryOfOrder) {
		this.categoryOfOrder = categoryOfOrder;
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
	public String getNewOrderRemark() {
		return newOrderRemark;
	}
	public void setNewOrderRemark(String newOrderRemark) {
		this.newOrderRemark = newOrderRemark;
	}
	public String getOldLineitemAmount() {
		return oldLineitemAmount;
	}
	public void setOldLineitemAmount(String oldLineitemAmount) {
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
	public String getToSite() {
		return toSite;
	}
	public void setToSite(String toSite) {
		this.toSite = toSite;
	}
	
	public double getPoAmounts() {
		return poAmounts;
	}
	public void setPoAmounts(double poAmounts) {
		this.poAmounts = poAmounts;
	}
	public String getCancelflag() {
		return cancelflag;
	}
	public void setCancelflag(String cancelflag) {
		this.cancelflag = cancelflag;
	}
	public String getProvisionBandWidth() {
		return provisionBandWidth;
	}
	public void setProvisionBandWidth(String provisionBandWidth) {
		this.provisionBandWidth = provisionBandWidth;
	}
	public String getBillingBandWidth() {
		return billingBandWidth;
	}
	public void setBillingBandWidth(String billingBandWidth) {
		this.billingBandWidth = billingBandWidth;
	}
	
	public String getKmsDistance() {
		return kmsDistance;
	}
	public void setKmsDistance(String kmsDistance) {
		this.kmsDistance = kmsDistance;
	}
	public String getLogicalCircuitNumber() {
		return logicalCircuitNumber;
	}
	public void setLogicalCircuitNumber(String logicalCircuitNumber) {
		this.logicalCircuitNumber = logicalCircuitNumber;
	}
	public String getPaymentTerm() {
		return paymentTerm;
	}
	public void setPaymentTerm(String paymentTerm) {
		this.paymentTerm = paymentTerm;
	}

	public long getAccountId() {
		return accountId;
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
	public String getPoRecieveDate() {
		return poRecieveDate;
	}
	public void setPoRecieveDate(String poRecieveDate) {
		this.poRecieveDate = poRecieveDate;
	}
	public long getPoAmountSum() {
		return poAmountSum;
	}
	public void setPoAmountSum(long poAmountSum) {
		this.poAmountSum = poAmountSum;
	}
	public String getCrmAccountNoString() {
		return crmAccountNoString;
	}
	public void setCrmAccountNoString(String crmAccountNoString) {
		this.crmAccountNoString = crmAccountNoString;
	}
	public String getIndustrySegment() {
		return industrySegment;
	}
	public void setIndustrySegment(String industrySegment) {
		this.industrySegment = industrySegment;
	}
	public double getAnnual_rate() {
		return annual_rate;
	}
	public void setAnnual_rate(double annual_rate) {
		this.annual_rate = annual_rate;
	}
	public String getPrjmgremail() {
		return prjmgremail;
	}
	public void setPrjmgremail(String prjmgremail) {
		this.prjmgremail = prjmgremail;
	}
	public String getOrdersubtype() {
		return ordersubtype;
	}
	public void setOrdersubtype(String ordersubtype) {
		this.ordersubtype = ordersubtype;
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
	public String getOrdercategory() {
		return ordercategory;
	}
	public void setOrdercategory(String ordercategory) {
		this.ordercategory = ordercategory;
	}
	public String getAppr_delay_in_region() {
		return appr_delay_in_region;
	}
	public void setAppr_delay_in_region(String appr_delay_in_region) {
		this.appr_delay_in_region = appr_delay_in_region;
	}
	public String getDays_for_order() {
		return days_for_order;
	}
	public void setDays_for_order(String days_for_order) {
		this.days_for_order = days_for_order;
	}
	public String getDays_in_copc() {
		return days_in_copc;
	}
	public void setDays_in_copc(String days_in_copc) {
		this.days_in_copc = days_in_copc;
	}
	public String getOrder_completion_date() {
		return order_completion_date;
	}
	public void setOrder_completion_date(String order_completion_date) {
		this.order_completion_date = order_completion_date;
	}
	public String getPo_logging_delay() {
		return po_logging_delay;
	}
	public void setPo_logging_delay(String po_logging_delay) {
		this.po_logging_delay = po_logging_delay;
	}
	public String getPo_recpt_delay() {
		return po_recpt_delay;
	}
	public void setPo_recpt_delay(String po_recpt_delay) {
		this.po_recpt_delay = po_recpt_delay;
	}
	public String getOrderApproveDate() {
		return orderApproveDate;
	}
	public void setOrderApproveDate(String orderApproveDate) {
		this.orderApproveDate = orderApproveDate;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getOutCome() {
		return outCome;
	}
	public void setOutCome(String outCome) {
		this.outCome = outCome;
	}
	public String getFxSiId() {
		return fxSiId;
	}
	public void setFxSiId(String fxSiId) {
		this.fxSiId = fxSiId;
	}
	public String getTokenNO() {
		return tokenNO;
	}
	public void setTokenNO(String tokenNO) {
		this.tokenNO = tokenNO;
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
	

	

	public String getBilling_trigger_date() {
		return billing_trigger_date;
	}
	public void setBilling_trigger_date(String billing_trigger_date) {
		this.billing_trigger_date = billing_trigger_date;
	}
	public String getBlSource() {
		return blSource;
	}
	public void setBlSource(String blSource) {
		this.blSource = blSource;
	}
	public String getCharge_status() {
		return charge_status;
	}
	public void setCharge_status(String charge_status) {
		this.charge_status = charge_status;
	}
	public double getChargesSumOfLineitem() {
		return chargesSumOfLineitem;
	}
	public void setChargesSumOfLineitem(double chargesSumOfLineitem) {
		this.chargesSumOfLineitem = chargesSumOfLineitem;
	}
	public String getChild_account_creation_status() {
		return child_account_creation_status;
	}
	public void setChild_account_creation_status(
			String child_account_creation_status) {
		this.child_account_creation_status = child_account_creation_status;
	}
	public String getFx_charge_status() {
		return fx_charge_status;
	}
	public void setFx_charge_status(String fx_charge_status) {
		this.fx_charge_status = fx_charge_status;
	}
	public String getFx_sd_charge_status() {
		return fx_sd_charge_status;
	}
	public void setFx_sd_charge_status(String fx_sd_charge_status) {
		this.fx_sd_charge_status = fx_sd_charge_status;
	}
	public int getTokenID() {
		return tokenID;
	}
	public void setTokenID(int tokenID) {
		this.tokenID = tokenID;
	}
	public String getServiceSegment() {
		return serviceSegment;
	}
	public void setServiceSegment(String serviceSegment) {
		this.serviceSegment = serviceSegment;
	}
	public String getBillingTriggerFlag() {
		return billingTriggerFlag;
	}
	public void setBillingTriggerFlag(String billingTriggerFlag) {
		this.billingTriggerFlag = billingTriggerFlag;
	}
	public String getStartDaysLogic() {
		return startDaysLogic;
	}
	public void setStartDaysLogic(String startDaysLogic) {
		this.startDaysLogic = startDaysLogic;
	}
	public String getStartMonthsLogic() {
		return startMonthsLogic;
	}
	public void setStartMonthsLogic(String startMonthsLogic) {
		this.startMonthsLogic = startMonthsLogic;
	}
	public String getSalesCoordinator() {
		return salesCoordinator;
	}
	public void setSalesCoordinator(String salesCoordinator) {
		this.salesCoordinator = salesCoordinator;
	}
	public String getFactory_ckt_id() {
		return factory_ckt_id;
	}
	public void setFactory_ckt_id(String factory_ckt_id) {
		this.factory_ckt_id = factory_ckt_id;
	}
	public String getPm_pro_date() {
		return pm_pro_date;
	}
	public void setPm_pro_date(String pm_pro_date) {
		this.pm_pro_date = pm_pro_date;
	}
	public String getBilling_location() {
		return billing_location;
	}
	public void setBilling_location(String billing_location) {
		this.billing_location = billing_location;
	}
	public String getBusiness_serial_no() {
		return business_serial_no;
	}
	public void setBusiness_serial_no(String business_serial_no) {
		this.business_serial_no = business_serial_no;
	}
	public String getChild_act_no() {
		return child_act_no;
	}
	public void setChild_act_no(String child_act_no) {
		this.child_act_no = child_act_no;
	}
	public String getIndicative_value() {
		return indicative_value;
	}
	public void setIndicative_value(String indicative_value) {
		this.indicative_value = indicative_value;
	}
	public String getLast_mile_media() {
		return last_mile_media;
	}
	public void setLast_mile_media(String last_mile_media) {
		this.last_mile_media = last_mile_media;
	}
	public String getLast_mile_provider() {
		return last_mile_provider;
	}
	public void setLast_mile_provider(String last_mile_provider) {
		this.last_mile_provider = last_mile_provider;
	}
	public String getLast_mile_remarks() {
		return last_mile_remarks;
	}
	public void setLast_mile_remarks(String last_mile_remarks) {
		this.last_mile_remarks = last_mile_remarks;
	}
	public String getLb_pk_charge_id() {
		return lb_pk_charge_id;
	}
	public void setLb_pk_charge_id(String lb_pk_charge_id) {
		this.lb_pk_charge_id = lb_pk_charge_id;
	}
	public String getLb_service_id() {
		return lb_service_id;
	}
	public void setLb_service_id(String lb_service_id) {
		this.lb_service_id = lb_service_id;
	}
	public String getLink_type() {
		return link_type;
	}
	public void setLink_type(String link_type) {
		this.link_type = link_type;
	}
	public String getMocn_no() {
		return mocn_no;
	}
	public void setMocn_no(String mocn_no) {
		this.mocn_no = mocn_no;
	}
	public String getOpms_act_id() {
		return opms_act_id;
	}
	public void setOpms_act_id(String opms_act_id) {
		this.opms_act_id = opms_act_id;
	}
	public String getPk_charge_id() {
		return pk_charge_id;
	}
	public void setPk_charge_id(String pk_charge_id) {
		this.pk_charge_id = pk_charge_id;
	}
	public String getRate_code() {
		return rate_code;
	}
	public void setRate_code(String rate_code) {
		this.rate_code = rate_code;
	}
	public String getInstallation_addressaa1() {
		return installation_addressaa1;
	}
	public void setInstallation_addressaa1(String installation_addressaa1) {
		this.installation_addressaa1 = installation_addressaa1;
	}
	public String getInstallation_addressaa2() {
		return installation_addressaa2;
	}
	public void setInstallation_addressaa2(String installation_addressaa2) {
		this.installation_addressaa2 = installation_addressaa2;
	}
	public String getInstallation_addressaa3() {
		return installation_addressaa3;
	}
	public void setInstallation_addressaa3(String installation_addressaa3) {
		this.installation_addressaa3 = installation_addressaa3;
	}
	public String getInstallation_addressab1() {
		return installation_addressab1;
	}
	public void setInstallation_addressab1(String installation_addressab1) {
		this.installation_addressab1 = installation_addressab1;
	}
	public String getInstallation_addressab2() {
		return installation_addressab2;
	}
	public void setInstallation_addressab2(String installation_addressab2) {
		this.installation_addressab2 = installation_addressab2;
	}
	public String getInstallation_addressab3() {
		return installation_addressab3;
	}
	public void setInstallation_addressab3(String installation_addressab3) {
		this.installation_addressab3 = installation_addressab3;
	}
	public String getFrom_country() {
		return from_country;
	}
	public void setFrom_country(String from_country) {
		this.from_country = from_country;
	}
	public String getFrom_state() {
		return from_state;
	}
	public void setFrom_state(String from_state) {
		this.from_state = from_state;
	}
	public String getTo_country() {
		return to_country;
	}
	public void setTo_country(String to_country) {
		this.to_country = to_country;
	}
	public String getTo_state() {
		return to_state;
	}
	public void setTo_state(String to_state) {
		this.to_state = to_state;
	}
		public String getDispatchLstDate() {
		return dispatchLstDate;
	}
	public void setDispatchLstDate(String dispatchLstDate) {
		this.dispatchLstDate = dispatchLstDate;
	}
	public String getDispatchLstNumber() {
		return dispatchLstNumber;
	}
	public void setDispatchLstNumber(String dispatchLstNumber) {
		this.dispatchLstNumber = dispatchLstNumber;
	}
	public String getDispatchPersonName() {
		return dispatchPersonName;
	}
	public void setDispatchPersonName(String dispatchPersonName) {
		this.dispatchPersonName = dispatchPersonName;
	}
	public String getDispatchPostalCode() {
		return dispatchPostalCode;
	}
	public void setDispatchPostalCode(String dispatchPostalCode) {
		this.dispatchPostalCode = dispatchPostalCode;
	}
	public String getPoExclude() {
		return poExclude;
	}
	public void setPoExclude(String poExclude) {
		this.poExclude = poExclude;
	}
	public String getCrm_att() {
		return crm_att;
	}
	public void setCrm_att(String crm_att) {
		this.crm_att = crm_att;
	}
	public int getCrm_order_id() {
		return crm_order_id;
	}
	public void setCrm_order_id(int crm_order_id) {
		this.crm_order_id = crm_order_id;
	}
	public String getM6_label_name() {
		return m6_label_name;
	}
	public void setM6_label_name(String m6_label_name) {
		this.m6_label_name = m6_label_name;
	}
	public String getM6_label_value() {
		return m6_label_value;
	}
	public void setM6_label_value(String m6_label_value) {
		this.m6_label_value = m6_label_value;
	}
	public String getOrdermonth() {
		return ordermonth;
	}
	public void setOrdermonth(String ordermonth) {
		this.ordermonth = ordermonth;
	}
	public String getAdvance() {
		return advance;
	}
	public void setAdvance(String advance) {
		this.advance = advance;
	}
	public long getAmt() {
		return amt;
	}
	public void setAmt(long amt) {
		this.amt = amt;
	}
	public String getAnnualRate() {
		return annualRate;
	}
	public void setAnnualRate(String annualRate) {
		this.annualRate = annualRate;
	}
	public String getBandwidth_att() {
		return bandwidth_att;
	}
	public void setBandwidth_att(String bandwidth_att) {
		this.bandwidth_att = bandwidth_att;
	}
	public int getCharge_hdr_id() {
		return charge_hdr_id;
	}
	public void setCharge_hdr_id(int charge_hdr_id) {
		this.charge_hdr_id = charge_hdr_id;
	}
	public String getChargeable_Distance() {
		return chargeable_Distance;
	}
	public void setChargeable_Distance(String chargeable_Distance) {
		this.chargeable_Distance = chargeable_Distance;
	}
	public String getCust_act_id() {
		return cust_act_id;
	}
	public void setCust_act_id(String cust_act_id) {
		this.cust_act_id = cust_act_id;
	}
	public String getCust_po_rec_date() {
		return cust_po_rec_date;
	}
	public void setCust_po_rec_date(String cust_po_rec_date) {
		this.cust_po_rec_date = cust_po_rec_date;
	}
	public String getM6_order_id() {
		return m6_order_id;
	}
	public void setM6_order_id(String m6_order_id) {
		this.m6_order_id = m6_order_id;
	}
	public String getM6_prod_id() {
		return m6_prod_id;
	}
	public void setM6_prod_id(String m6_prod_id) {
		this.m6_prod_id = m6_prod_id;
	}
	public String getNature_sale() {
		return nature_sale;
	}
	public void setNature_sale(String nature_sale) {
		this.nature_sale = nature_sale;
	}
	public String getSrno() {
		return srno;
	}
	public void setSrno(String srno) {
		this.srno = srno;
	}
	public String getType_sale() {
		return type_sale;
	}
	public void setType_sale(String type_sale) {
		this.type_sale = type_sale;
	}
	public String getChargeinfoID() {
		return chargeinfoID;
	}
	public void setChargeinfoID(String chargeinfoID) {
		this.chargeinfoID = chargeinfoID;
	}
	public String getSearchLSI() {
		return searchLSI;
	}
	public void setSearchLSI(String searchLSI) {
		this.searchLSI = searchLSI;
	}
	public String getSearchSRNO() {
		return searchSRNO;
	}
	public void setSearchSRNO(String searchSRNO) {
		this.searchSRNO = searchSRNO;
	}
	public String getSrDate() {
		return srDate;
	}
	public void setSrDate(String srDate) {
		this.srDate = srDate;
	}
	public String getDisdate() {
		return disdate;
	}
	public void setDisdate(String disdate) {
		this.disdate = disdate;
	}
	public String getAccountMgrPhoneNo() {
		return accountMgrPhoneNo;
	}
	public void setAccountMgrPhoneNo(String accountMgrPhoneNo) {
		this.accountMgrPhoneNo = accountMgrPhoneNo;
	}
	public String getAccountSegment() {
		return accountSegment;
	}
	public void setAccountSegment(String accountSegment) {
		this.accountSegment = accountSegment;
	}
	public String getActiveDate() {
		return activeDate;
	}
	public void setActiveDate(String activeDate) {
		this.activeDate = activeDate;
	}
	public String getBillCity() {
		return billCity;
	}
	public void setBillCity(String billCity) {
		this.billCity = billCity;
	}
	public String getBillCompany() {
		return billCompany;
	}
	public void setBillCompany(String billCompany) {
		this.billCompany = billCompany;
	}
	public String getBilling_address2() {
		return billing_address2;
	}
	public void setBilling_address2(String billing_address2) {
		this.billing_address2 = billing_address2;
	}
	public String getBillState() {
		return billState;
	}
	public void setBillState(String billState) {
		this.billState = billState;
	}
	public String getBillZip() {
		return billZip;
	}
	public void setBillZip(String billZip) {
		this.billZip = billZip;
	}
	public String getBusinessSegment() {
		return businessSegment;
	}
	public void setBusinessSegment(String businessSegment) {
		this.businessSegment = businessSegment;
	}
	public String getChairPersonEmail() {
		return chairPersonEmail;
	}
	public void setChairPersonEmail(String chairPersonEmail) {
		this.chairPersonEmail = chairPersonEmail;
	}
	public String getChairPersonName() {
		return chairPersonName;
	}
	public void setChairPersonName(String chairPersonName) {
		this.chairPersonName = chairPersonName;
	}
	public String getChairPersonPhone() {
		return chairPersonPhone;
	}
	public void setChairPersonPhone(String chairPersonPhone) {
		this.chairPersonPhone = chairPersonPhone;
	}
	public String getComponentActiveDate() {
		return componentActiveDate;
	}
	public void setComponentActiveDate(String componentActiveDate) {
		this.componentActiveDate = componentActiveDate;
	}
	public double getComponentRCNRCAmount() {
		return componentRCNRCAmount;
	}
	public void setComponentRCNRCAmount(double componentRCNRCAmount) {
		this.componentRCNRCAmount = componentRCNRCAmount;
	}
	public String getComponentType() {
		return componentType;
	}
	public void setComponentType(String componentType) {
		this.componentType = componentType;
	}
	public String getContact1_Phone() {
		return contact1_Phone;
	}
	public void setContact1_Phone(String contact1_Phone) {
		this.contact1_Phone = contact1_Phone;
	}
	public String getContact2_Phone() {
		return contact2_Phone;
	}
	public void setContact2_Phone(String contact2_Phone) {
		this.contact2_Phone = contact2_Phone;
	}
	public String getContactPersonEmail() {
		return contactPersonEmail;
	}
	public void setContactPersonEmail(String contactPersonEmail) {
		this.contactPersonEmail = contactPersonEmail;
	}
	public long getFx_internal_acc_id() {
		return fx_internal_acc_id;
	}
	public void setFx_internal_acc_id(long fx_internal_acc_id) {
		this.fx_internal_acc_id = fx_internal_acc_id;
	}
	public String getInActiveDate() {
		return inActiveDate;
	}
	public void setInActiveDate(String inActiveDate) {
		this.inActiveDate = inActiveDate;
	}
	public String getMinimum_bandwidth() {
		return minimum_bandwidth;
	}
	public void setMinimum_bandwidth(String minimum_bandwidth) {
		this.minimum_bandwidth = minimum_bandwidth;
	}
	public String getMinimum_bandwidth_UOM() {
		return minimum_bandwidth_UOM;
	}
	public void setMinimum_bandwidth_UOM(String minimum_bandwidth_UOM) {
		this.minimum_bandwidth_UOM = minimum_bandwidth_UOM;
	}
	//[101010] START
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
	public String getCopcApproveFromDate() {
		return copcApproveFromDate;
	}
	public void setCopcApproveFromDate(String copcApproveFromDate) {
		this.copcApproveFromDate = copcApproveFromDate;
	}
	public String getCopcApproveToDate() {
		return copcApproveToDate;
	}
	public void setCopcApproveToDate(String copcApproveToDate) {
		this.copcApproveToDate = copcApproveToDate;
	}
// [101010] END
	
//-[HYPR22032013001] -start
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getInstallRate() {
		return installRate;
	}
	public void setInstallRate(String installRate) {
		this.installRate = installRate;
	}
//	-[HYPR22032013001] -end
	public String getLastApprovalRemarks() {
		return lastApprovalRemarks;
	}
	public void setLastApprovalRemarks(String lastApprovalRemarks) {
		this.lastApprovalRemarks = lastApprovalRemarks;
	}
	public Double getPo_recpt_delay_double() {
		return po_recpt_delay_double;
	}
	public void setPo_recpt_delay_double(Double po_recpt_delay_double) {
		this.po_recpt_delay_double = po_recpt_delay_double;
	}
	public Double getAppr_delay_in_region_double() {
		return appr_delay_in_region_double;
	}
	public void setAppr_delay_in_region_double(Double appr_delay_in_region_double) {
		this.appr_delay_in_region_double = appr_delay_in_region_double;
	}
	public Double getDays_for_order_double() {
		return days_for_order_double;
	}
	public void setDays_for_order_double(Double days_for_order_double) {
		this.days_for_order_double = days_for_order_double;
	}
	public Double getDays_in_copc_double() {
		return days_in_copc_double;
	}
	public void setDays_in_copc_double(Double days_in_copc_double) {
		this.days_in_copc_double = days_in_copc_double;
	}
	public Double getOrder_completion_date_double() {
		return order_completion_date_double;
	}
	public void setOrder_completion_date_double(Double order_completion_date_double) {
		this.order_completion_date_double = order_completion_date_double;
	}
	public Double getPo_logging_delay_double() {
		return po_logging_delay_double;
	}
	public void setPo_logging_delay_double(Double po_logging_delay_double) {
		this.po_logging_delay_double = po_logging_delay_double;
	}
	
	public String getContractEndDateCal() {
		return contractEndDateCal;
	}
	public void setContractEndDateCal(String contractEndDateCal) {
		this.contractEndDateCal = contractEndDateCal;
	}
	public String getContractStartDateCal() {
		return contractStartDateCal;
	}
	public void setContractStartDateCal(String contractStartDateCal) {
		this.contractStartDateCal = contractStartDateCal;
	}

public String getAm_approval_remarks() {
		return am_approval_remarks;
	}
	public void setAm_approval_remarks(String am_approval_remarks) {
		this.am_approval_remarks = am_approval_remarks;
	}
	public String getCopc_approval_remarks() {
		return copc_approval_remarks;
	}
	public void setCopc_approval_remarks(String copc_approval_remarks) {
		this.copc_approval_remarks = copc_approval_remarks;
	}
	public String getPm_approval_remarks() {
		return pm_approval_remarks;
	}
	public void setPm_approval_remarks(String pm_approval_remarks) {
		this.pm_approval_remarks = pm_approval_remarks;
	}
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
	public String getEnd_fx_no() {
		return end_fx_no;
	}
	public void setEnd_fx_no(String end_fx_no) {
		this.end_fx_no = end_fx_no;
	}
	public String getStart_fx_no() {
		return start_fx_no;
	}
	public void setStart_fx_no(String start_fx_no) {
		this.start_fx_no = start_fx_no;
	}
	public int getFromAccountNo() {
		return fromAccountNo;
	}
	public void setFromAccountNo(int fromAccountNo) {
		this.fromAccountNo = fromAccountNo;
	}
	public String getFromCopcApprovedDate() {
		return fromCopcApprovedDate;
	}
	public void setFromCopcApprovedDate(String fromCopcApprovedDate) {
		this.fromCopcApprovedDate = fromCopcApprovedDate;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public int getFromOrderNo() {
		return fromOrderNo;
	}
	public void setFromOrderNo(int fromOrderNo) {
		this.fromOrderNo = fromOrderNo;
	}
	public String getOsp() {
		return osp;
	}
	public void setOsp(String osp) {
		this.osp = osp;
	}
	public String getSearchCRMOrder() {
		return searchCRMOrder;
	}
	public void setSearchCRMOrder(String searchCRMOrder) {
		this.searchCRMOrder = searchCRMOrder;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getLOC_Date() {
		return LOC_Date;
	}
	public void setLOC_Date(String date) {
		LOC_Date = date;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public int getParty_no() {
		return party_no;
	}
	public void setParty_no(int party_no) {
		this.party_no = party_no;
	}
	public String getSearchfromDate() {
		return searchfromDate;
	}
	public void setSearchfromDate(String searchfromDate) {
		this.searchfromDate = searchfromDate;
	}
	public int getToAccountNo() {
		return toAccountNo;
	}
	public void setToAccountNo(int toAccountNo) {
		this.toAccountNo = toAccountNo;
	}
	public String getToCopcApprovedDate() {
		return toCopcApprovedDate;
	}
	public void setToCopcApprovedDate(String toCopcApprovedDate) {
		this.toCopcApprovedDate = toCopcApprovedDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
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
	public String getBillingFormatName() {
		return billingFormatName;
	}
	public void setBillingFormatName(String billingFormatName) {
		this.billingFormatName = billingFormatName;
	}
	public String getBillingLevel() {
		return billingLevel;
	}
	public void setBillingLevel(String billingLevel) {
		this.billingLevel = billingLevel;
	}
	public String getBillingLevelName() {
		return billingLevelName;
	}
	public void setBillingLevelName(String billingLevelName) {
		this.billingLevelName = billingLevelName;
	}
	public Long getBillingLevelNo() {
		return billingLevelNo;
	}
	public void setBillingLevelNo(Long billingLevelNo) {
		this.billingLevelNo = billingLevelNo;
	}
	public String getBillingLevelNo1() {
		return billingLevelNo1;
	}
	public void setBillingLevelNo1(String billingLevelNo1) {
		this.billingLevelNo1 = billingLevelNo1;
	}
	public String getBillingMode() {
		return billingMode;
	}
	public void setBillingMode(String billingMode) {
		this.billingMode = billingMode;
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
	public String getCustPoDetailNo() {
		return custPoDetailNo;
	}
	public void setCustPoDetailNo(String custPoDetailNo) {
		this.custPoDetailNo = custPoDetailNo;
	}
	public String getDemo() {
		return demo;
	}
	public void setDemo(String demo) {
		this.demo = demo;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getEntity() {
		return entity;
	}
	public void setEntity(String entity) {
		this.entity = entity;
	}
	public String getFromOrderDate() {
		return fromOrderDate;
	}
	public void setFromOrderDate(String fromOrderDate) {
		this.fromOrderDate = fromOrderDate;
	}
	public int getLogicalSINumber() {
		return logicalSINumber;
	}
	public void setLogicalSINumber(int logicalSINumber) {
		this.logicalSINumber = logicalSINumber;
	}
	public String getSearchToDate() {
		return searchToDate;
	}
	public void setSearchToDate(String searchToDate) {
		this.searchToDate = searchToDate;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getSubChangeTypeName() {
		return subChangeTypeName;
	}
	public void setSubChangeTypeName(String subChangeTypeName) {
		this.subChangeTypeName = subChangeTypeName;
	}
	public String getBillingPODate() {
		return billingPODate;
	}
	public void setBillingPODate(String billingPODate) {
		this.billingPODate = billingPODate;
	}
	public String getBillingTypeName() {
		return billingTypeName;
	}
	public void setBillingTypeName(String billingTypeName) {
		this.billingTypeName = billingTypeName;
	}
	public String getChangeTypeName() {
		return changeTypeName;
	}
	public void setChangeTypeName(String changeTypeName) {
		this.changeTypeName = changeTypeName;
	}
	public String getChargeTypeName() {
		return chargeTypeName;
	}
	public void setChargeTypeName(String chargeTypeName) {
		this.chargeTypeName = chargeTypeName;
	}
	public int getCommitmentPeriod() {
		return commitmentPeriod;
	}
	public void setCommitmentPeriod(int commitmentPeriod) {
		this.commitmentPeriod = commitmentPeriod;
	}
	public ComponentsDto getComponentDto() {
		return componentDto;
	}
	public void setComponentDto(ComponentsDto componentDto) {
		this.componentDto = componentDto;
	}
	public String getComponentName() {
		return componentName;
	}
	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}
	public int getContractPeriod() {
		return contractPeriod;
	}
	public void setContractPeriod(int contractPeriod) {
		this.contractPeriod = contractPeriod;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getCreditPeriodName() {
		return creditPeriodName;
	}
	public void setCreditPeriodName(String creditPeriodName) {
		this.creditPeriodName = creditPeriodName;
	}
	public int getCrmAccountNo() {
		return crmAccountNo;
	}
	public void setCrmAccountNo(int crmAccountNo) {
		this.crmAccountNo = crmAccountNo;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public int getCustomer_logicalSINumber() {
		return customer_logicalSINumber;
	}
	public void setCustomer_logicalSINumber(int customer_logicalSINumber) {
		this.customer_logicalSINumber = customer_logicalSINumber;
	}
	public String getDispatchAddress1() {
		return dispatchAddress1;
	}
	public void setDispatchAddress1(String dispatchAddress1) {
		this.dispatchAddress1 = dispatchAddress1;
	}
	public int getEndDateDays() {
		return endDateDays;
	}
	public void setEndDateDays(int endDateDays) {
		this.endDateDays = endDateDays;
	}
	public String getEndDateLogic() {
		return endDateLogic;
	}
	public void setEndDateLogic(String endDateLogic) {
		this.endDateLogic = endDateLogic;
	}
	public int getEndDateMonth() {
		return endDateMonth;
	}
	public void setEndDateMonth(int endDateMonth) {
		this.endDateMonth = endDateMonth;
	}
	public String getFormAvailable() {
		return formAvailable;
	}
	public void setFormAvailable(String formAvailable) {
		this.formAvailable = formAvailable;
	}
	public String getFrequencyName() {
		return frequencyName;
	}
	public void setFrequencyName(String frequencyName) {
		this.frequencyName = frequencyName;
	}
	public String getFromCrmOrderid() {
		return fromCrmOrderid;
	}
	public void setFromCrmOrderid(String fromCrmOrderid) {
		this.fromCrmOrderid = fromCrmOrderid;
	}
	public String getFromLocation() {
		return fromLocation;
	}
	public void setFromLocation(String fromLocation) {
		this.fromLocation = fromLocation;
	}
	public String getFx_external_acc_id() {
		return fx_external_acc_id;
	}
	public void setFx_external_acc_id(String fx_external_acc_id) {
		this.fx_external_acc_id = fx_external_acc_id;
	}
	public String getHardwaretypeName() {
		return hardwaretypeName;
	}
	public void setHardwaretypeName(String hardwaretypeName) {
		this.hardwaretypeName = hardwaretypeName;
	}
	public String getLOC_No() {
		return LOC_No;
	}
	public void setLOC_No(String no) {
		LOC_No = no;
	}
	public String getLogicalCircuitId() {
		return logicalCircuitId;
	}
	public void setLogicalCircuitId(String logicalCircuitId) {
		this.logicalCircuitId = logicalCircuitId;
	}
	public String getToOrderDate() {
		return toOrderDate;
	}
	public void setToOrderDate(String toOrderDate) {
		this.toOrderDate = toOrderDate;
	}
	public String getLogicalSINo() {
		return logicalSINo;
	}
	public void setLogicalSINo(String logicalSINo) {
		this.logicalSINo = logicalSINo;
	}
	public String getAccountno() {
		return accountno;
	}
	public void setAccountno(String accountno) {
		this.accountno = accountno;
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
	public String getAddress3() {
		return address3;
	}
	public void setAddress3(String address3) {
		this.address3 = address3;
	}
	public int getBillingInfoID() {
		return billingInfoID;
	}
	public void setBillingInfoID(int billingInfoID) {
		this.billingInfoID = billingInfoID;
	}
	public String getChargeAmount_String() {
		return chargeAmount_String;
	}
	public void setChargeAmount_String(String chargeAmount_String) {
		this.chargeAmount_String = chargeAmount_String;
	}
	public String getChargeAnnotation() {
		return chargeAnnotation;
	}
	public void setChargeAnnotation(String chargeAnnotation) {
		this.chargeAnnotation = chargeAnnotation;
	}
	public int getChargeInfoID() {
		return chargeInfoID;
	}
	public void setChargeInfoID(int chargeInfoID) {
		this.chargeInfoID = chargeInfoID;
	}
	public String getChargeName() {
		return chargeName;
	}
	public void setChargeName(String chargeName) {
		this.chargeName = chargeName;
	}
	public int getChargeTypeID() {
		return chargeTypeID;
	}
	public void setChargeTypeID(int chargeTypeID) {
		this.chargeTypeID = chargeTypeID;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public int getComponentID() {
		return componentID;
	}
	public void setComponentID(int componentID) {
		this.componentID = componentID;
	}
	public int getComponentInfoID() {
		return componentInfoID;
	}
	public void setComponentInfoID(int componentInfoID) {
		this.componentInfoID = componentInfoID;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getCountyName() {
		return countyName;
	}
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
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
	public String getDispatchAddressName() {
		return dispatchAddressName;
	}
	public void setDispatchAddressName(String dispatchAddressName) {
		this.dispatchAddressName = dispatchAddressName;
	}
	public String getDispatchCityName() {
		return dispatchCityName;
	}
	public void setDispatchCityName(String dispatchCityName) {
		this.dispatchCityName = dispatchCityName;
	}
	public String getToCrmOrderid() {
		return toCrmOrderid;
	}
	public void setToCrmOrderid(String toCrmOrderid) {
		this.toCrmOrderid = toCrmOrderid;
	}
	public String getAcmgrEmail() {
		return acmgrEmail;
	}
	public void setAcmgrEmail(String acmgrEmail) {
		this.acmgrEmail = acmgrEmail;
	}
	public String getBillingformat() {
		return billingformat;
	}
	public void setBillingformat(String billingformat) {
		this.billingformat = billingformat;
	}
	public String getChargeFrequency() {
		return chargeFrequency;
	}
	public void setChargeFrequency(String chargeFrequency) {
		this.chargeFrequency = chargeFrequency;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getCustSINo() {
		return custSINo;
	}
	public void setCustSINo(String custSINo) {
		this.custSINo = custSINo;
	}
	public String getDispatchPhoneNo() {
		return dispatchPhoneNo;
	}
	public void setDispatchPhoneNo(String dispatchPhoneNo) {
		this.dispatchPhoneNo = dispatchPhoneNo;
	}
	public String getDispatchStateName() {
		return dispatchStateName;
	}
	public void setDispatchStateName(String dispatchStateName) {
		this.dispatchStateName = dispatchStateName;
	}
	public int getFxInternalId() {
		return fxInternalId;
	}
	public void setFxInternalId(int fxInternalId) {
		this.fxInternalId = fxInternalId;
	}
	public String getLicCompanyName() {
		return licCompanyName;
	}
	public void setLicCompanyName(String licCompanyName) {
		this.licCompanyName = licCompanyName;
	}
	public String getParty() {
		return party;
	}
	public void setParty(String party) {
		this.party = party;
	}
	public int getCreditPeriod() {
		return creditPeriod;
	}
	public void setCreditPeriod(int creditPeriod) {
		this.creditPeriod = creditPeriod;
	}
	public long getPonum() {
		return ponum;
	}
	public void setPonum(long ponum) {
		this.ponum = ponum;
	}
	public String getPonum1() {
		return ponum1;
	}
	public void setPonum1(String ponum1) {
		this.ponum1 = ponum1;
	}
	public int getPoNumber() {
		return poNumber;
	}
	public void setPoNumber(int poNumber) {
		this.poNumber = poNumber;
	}

	public int getOldOrderNo() {
		return oldOrderNo;
	}
	public void setOldOrderNo(int oldOrderNo) {
		this.oldOrderNo = oldOrderNo;
	}
	public String getOrderStage() {
		return orderStage;
	}
	public void setOrderStage(String orderStage) {
		this.orderStage = orderStage;
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
	public String getParty_num() {
		return party_num;
	}
	public void setParty_num(String party_num) {
		this.party_num = party_num;
	}
	public String getPenaltyClause() {
		return penaltyClause;
	}
	public void setPenaltyClause(String penaltyClause) {
		this.penaltyClause = penaltyClause;
	}
	public String getPmapprovaldate() {
		return pmapprovaldate;
	}
	public void setPmapprovaldate(String pmapprovaldate) {
		this.pmapprovaldate = pmapprovaldate;
	}
	public String getPoAmount() {
		return poAmount;
	}
	public void setPoAmount(String poAmount) {
		this.poAmount = poAmount;
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
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getQuoteNo() {
		return quoteNo;
	}
	public void setQuoteNo(String quoteNo) {
		this.quoteNo = quoteNo;
	}
	public String getAmapprovaldate() {
		return amapprovaldate;
	}
	public void setAmapprovaldate(String amapprovaldate) {
		this.amapprovaldate = amapprovaldate;
	}
	public double getChargeAmount() {
		return chargeAmount;
	}
	public void setChargeAmount(double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}
	public String getContactCell() {
		return contactCell;
	}
	public void setContactCell(String contactCell) {
		this.contactCell = contactCell;
	}
	public String getCopcapprovaldate() {
		return copcapprovaldate;
	}
	public void setCopcapprovaldate(String copcapprovaldate) {
		this.copcapprovaldate = copcapprovaldate;
	}
	public int getCrmAccountId() {
		return crmAccountId;
	}
	public void setCrmAccountId(int crmAccountId) {
		this.crmAccountId = crmAccountId;
	}
	public String getEffDate() {
		return effDate;
	}
	public void setEffDate(String effDate) {
		this.effDate = effDate;
	}
	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	public String getHardwareType() {
		return hardwareType;
	}
	public void setHardwareType(String hardwareType) {
		this.hardwareType = hardwareType;
	}
	public int getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(int interestRate) {
		this.interestRate = interestRate;
	}
	public int getIsDefaultPO() {
		return isDefaultPO;
	}
	public void setIsDefaultPO(int isDefaultPO) {
		this.isDefaultPO = isDefaultPO;
	}
	public String getRfs_date() {
		return rfs_date;
	}
	public void setRfs_date(String rfs_date) {
		this.rfs_date = rfs_date;
	}
	public int getM6OrderNo() {
		return m6OrderNo;
	}
	public void setM6OrderNo(int orderNo) {
		m6OrderNo = orderNo;
	}
	public String getM6OrderNo2() {
		return m6OrderNo2;
	}
	public void setM6OrderNo2(String orderNo2) {
		m6OrderNo2 = orderNo2;
	}
	public int getServiceProductID() {
		return serviceProductID;
	}
	public void setServiceProductID(int serviceProductID) {
		this.serviceProductID = serviceProductID;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getSourceName() {
		return sourceName;
	}
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	public long getSourcePartyID() {
		return sourcePartyID;
	}
	public void setSourcePartyID(long sourcePartyID) {
		this.sourcePartyID = sourcePartyID;
	}
	public String getStageName() {
		return stageName;
	}
	public void setStageName(String stageName) {
		this.stageName = stageName;
	}
	
	public int getChargePeriod() {
		return chargePeriod;
	}
	public void setChargePeriod(int chargePeriod) {
		this.chargePeriod = chargePeriod;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getEndHWDateLogic() {
		return endHWDateLogic;
	}
	public void setEndHWDateLogic(String endHWDateLogic) {
		this.endHWDateLogic = endHWDateLogic;
	}
	public long getBillingLevelId() {
		return billingLevelId;
	}
	public void setBillingLevelId(long billingLevelId) {
		this.billingLevelId = billingLevelId;
	}
	public String getActmgrname() {
		return actmgrname;
	}
	public void setActmgrname(String actmgrname) {
		this.actmgrname = actmgrname;
	}
	public String getLst_No() {
		return lst_No;
	}
	public void setLst_No(String lst_No) {
		this.lst_No = lst_No;
	}
	public String getLstDate() {
		return lstDate;
	}
	public void setLstDate(String lstDate) {
		this.lstDate = lstDate;
	}
	public int getContractMonths() {
		return contractMonths;
	}
	public void setContractMonths(int contractMonths) {
		this.contractMonths = contractMonths;
	}
	public int getNewOrderNo() {
		return newOrderNo;
	}
	public void setNewOrderNo(int newOrderNo) {
		this.newOrderNo = newOrderNo;
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
	public int getStartDateDays() {
		return startDateDays;
	}
	public void setStartDateDays(int startDateDays) {
		this.startDateDays = startDateDays;
	}
	public int getPrincipalAmount() {
		return principalAmount;
	}
	public void setPrincipalAmount(int principalAmount) {
		this.principalAmount = principalAmount;
	}
	public long getNoticePeriod() {
		return noticePeriod;
	}
	public void setNoticePeriod(long noticePeriod) {
		this.noticePeriod = noticePeriod;
	}
	public String getProdAlisName() {
		return prodAlisName;
	}
	public void setProdAlisName(String prodAlisName) {
		this.prodAlisName = prodAlisName;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getRfsDate() {
		return rfsDate;
	}
	public void setRfsDate(String rfsDate) {
		this.rfsDate = rfsDate;
	}
	public int getRootOrderNo() {
		return rootOrderNo;
	}
	public void setRootOrderNo(int rootOrderNo) {
		this.rootOrderNo = rootOrderNo;
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
	public String getSaleTypeName() {
		return saleTypeName;
	}
	public void setSaleTypeName(String saleTypeName) {
		this.saleTypeName = saleTypeName;
	}
	public String getServiceIdString() {
		return serviceIdString;
	}
	public void setServiceIdString(String serviceIdString) {
		this.serviceIdString = serviceIdString;
	}
	public int getServiceproductid() {
		return serviceproductid;
	}
	public void setServiceproductid(int serviceproductid) {
		this.serviceproductid = serviceproductid;
	}
	public String getStartDateLogic() {
		return startDateLogic;
	}
	public void setStartDateLogic(String startDateLogic) {
		this.startDateLogic = startDateLogic;
	}
	public int getStartDateMonth() {
		return startDateMonth;
	}
	public void setStartDateMonth(int startDateMonth) {
		this.startDateMonth = startDateMonth;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getStore() {
		return store;
	}
	public void setStore(String store) {
		this.store = store;
	}
	public String getStoreName() {
		return StoreName;
	}
	public void setStoreName(String storeName) {
		StoreName = storeName;
	}
	public String getSubProductName() {
		return subProductName;
	}
	public void setSubProductName(String subProductName) {
		this.subProductName = subProductName;
	}
	public String getTaxationName() {
		return taxationName;
	}
	public void setTaxationName(String taxationName) {
		this.taxationName = taxationName;
	}
	public double getTotalAmountIncludingCurrent() {
		return totalAmountIncludingCurrent;
	}
	public void setTotalAmountIncludingCurrent(double totalAmountIncludingCurrent) {
		this.totalAmountIncludingCurrent = totalAmountIncludingCurrent;
	}
	public String getTotalPoAmt() {
		return totalPoAmt;
	}
	public void setTotalPoAmt(String totalPoAmt) {
		this.totalPoAmt = totalPoAmt;
	}
	public String getTaxation() {
		return taxation;
	}
	public void setTaxation(String taxation) {
		this.taxation = taxation;
	}
	public int getTxtStartDays() {
		return txtStartDays;
	}
	public void setTxtStartDays(int txtStartDays) {
		this.txtStartDays = txtStartDays;
	}
	public int getNewServiceNo() {
		return newServiceNo;
	}
	public void setNewServiceNo(int newServiceNo) {
		this.newServiceNo = newServiceNo;
	}
	public int getOldServiceNo() {
		return oldServiceNo;
	}
	public void setOldServiceNo(int oldServiceNo) {
		this.oldServiceNo = oldServiceNo;
	}
	public String getServiceTypeDescription() {
		return serviceTypeDescription;
	}
	public void setServiceTypeDescription(String serviceTypeDescription) {
		this.serviceTypeDescription = serviceTypeDescription;
	}
	public String getToLocation() {
		return toLocation;
	}
	public void setToLocation(String toLocation) {
		this.toLocation = toLocation;
	}
	public int getTxtStartMonth() {
		return txtStartMonth;
	}
	public void setTxtStartMonth(int txtStartMonth) {
		this.txtStartMonth = txtStartMonth;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public String getOrderyear() {
		return orderyear;
	}
	public void setOrderyear(String orderyear) {
		this.orderyear = orderyear;
	}
	public double getChargeAmount_Double() {
		return chargeAmount_Double;
	}
	public void setChargeAmount_Double(double chargeAmount_Double) {
		this.chargeAmount_Double = chargeAmount_Double;
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
	public String getLineItemName() {
		return lineItemName;
	}
	public void setLineItemName(String lineItemName) {
		this.lineItemName = lineItemName;
	}
	public String getSearchAccount() {
		return searchAccount;
	}
	public void setSearchAccount(String searchAccount) {
		this.searchAccount = searchAccount;
	}
	public String getSearchAccountName() {
		return searchAccountName;
	}
	public void setSearchAccountName(String searchAccountName) {
		this.searchAccountName = searchAccountName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getAnnual_rateString() {
		return annual_rateString;
	}
	public void setAnnual_rateString(String annual_rateString) {
		this.annual_rateString = annual_rateString;
	}
	public String getPoAmountSumString() {
		return poAmountSumString;
	}
	public void setPoAmountSumString(String poAmountSumString) {
		this.poAmountSumString = poAmountSumString;
	}
	public String getLineamtString() {
		return lineamtString;
	}
	public void setLineamtString(String lineamtString) {
		this.lineamtString = lineamtString;
	}
	public String getTotalAmountIncludingCurrentString() {
		return totalAmountIncludingCurrentString;
	}
	public void setTotalAmountIncludingCurrentString(
			String totalAmountIncludingCurrentString) {
		this.totalAmountIncludingCurrentString = totalAmountIncludingCurrentString;
	}
	public String getCust_total_poamtString() {
		return cust_total_poamtString;
	}
	public void setCust_total_poamtString(String cust_total_poamtString) {
		this.cust_total_poamtString = cust_total_poamtString;
	}

	public String getAsiteAdd1() {
		return asiteAdd1;
	}
	public void setAsiteAdd1(String asiteAdd1) {
		this.asiteAdd1 = asiteAdd1;
	}
	public String getAsiteAdd2() {
		return asiteAdd2;
	}
	public void setAsiteAdd2(String asiteAdd2) {
		this.asiteAdd2 = asiteAdd2;
	}
	public String getAsiteAdd3() {
		return asiteAdd3;
	}
	public void setAsiteAdd3(String asiteAdd3) {
		this.asiteAdd3 = asiteAdd3;
	}
	public String getBsiteLineAdd1() {
		return bsiteLineAdd1;
	}
	public void setBsiteLineAdd1(String bsiteLineAdd1) {
		this.bsiteLineAdd1 = bsiteLineAdd1;
	}
	public String getBsiteLineAdd2() {
		return bsiteLineAdd2;
	}
	public void setBsiteLineAdd2(String bsiteLineAdd2) {
		this.bsiteLineAdd2 = bsiteLineAdd2;
	}
	public String getBsiteName() {
		return bsiteName;
	}
	public void setBsiteName(String bsiteName) {
		this.bsiteName = bsiteName;
	}
   public String getOldLsi() {
		return oldLsi;
	}
	public void setOldLsi(String oldLsi) {
		this.oldLsi = oldLsi;
	}

	//[505050] Start
	public String getMedia() {
		return media;
	}
	public String getOnnetOffnet() {
		return OnnetOffnet;
	}
	public String getServOrderCategory() {
		return servOrderCategory;
	}
	public void setMedia(String media) {
		this.media = media;
	}
	public void setOnnetOffnet(String onnetOffnet) {
		OnnetOffnet = onnetOffnet;
	}
	public void setServOrderCategory(String servOrderCategory) {
		this.servOrderCategory = servOrderCategory;
	}
	//[505050]
//[0099]
	private String mailSubject;
	private String mailBody;
	private String mailBodyPart2;
	private String mailBodyPart3;
	private String mailBodyPart4;
	private String mailBodyPart5;
	public String getMailSubject() {
		return mailSubject;
	}
	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}
	public String getMailBody() {
		return mailBody;
	}
	public void setMailBody(String mailBody) {
		this.mailBody = mailBody;
	}
	public String getMailBodyPart2() {
		return mailBodyPart2;
	}
	public void setMailBodyPart2(String mailBodyPart2) {
		this.mailBodyPart2 = mailBodyPart2;
	}
	public String getMailBodyPart3() {
		return mailBodyPart3;
	}
	public void setMailBodyPart3(String mailBodyPart3) {
		this.mailBodyPart3 = mailBodyPart3;
	}
	public String getMailBodyPart4() {
		return mailBodyPart4;
	}
	public void setMailBodyPart4(String mailBodyPart4) {
		this.mailBodyPart4 = mailBodyPart4;
	}
	public String getMailBodyPart5() {
		return mailBodyPart5;
	}
	public void setMailBodyPart5(String mailBodyPart5) {
		this.mailBodyPart5 = mailBodyPart5;
	}
////[0099]end
	public String getTaxExcemption_Reason() {
		return taxExcemption_Reason;
	}
	public void setTaxExcemption_Reason(String taxExcemption_Reason) {
		this.taxExcemption_Reason = taxExcemption_Reason;
	}
	
	//[002] Start
	public String getInstallationFromCity() {
		return installationFromCity;
	}
	public void setInstallationFromCity(String installationFromCity) {
		this.installationFromCity = installationFromCity;
	}
	public String getInstallationToCity() {
		return installationToCity;
	}
	public void setInstallationToCity(String installationToCity) {
		this.installationToCity = installationToCity;
	}
	public String getInstallationFromState() {
		return installationFromState;
	}
	public void setInstallationFromState(String installationFromState) {
		this.installationFromState = installationFromState;
	}
	public String getInstallationToState() {
		return installationToState;
	}
	public void setInstallationToState(String installationToState) {
		this.installationToState = installationToState;
	}
	public String getBillingContactName() {
		return billingContactName;
	}
	public void setBillingContactName(String billingContactName) {
		this.billingContactName = billingContactName;
	}
	public String getBillingContactNumber() {
		return billingContactNumber;
	}
	public void setBillingContactNumber(String billingContactNumber) {
		this.billingContactNumber = billingContactNumber;
	}
	public String getBillingEmailId() {
		return billingEmailId;
	}
	public void setBillingEmailId(String billingEmailId) {
		this.billingEmailId = billingEmailId;
	}
	
	//[002] End
	
	//[003] Start
	public String getOrderCreationDate() {
		return orderCreationDate;
	}
	public void setOrderCreationDate(String orderCreationDate) {
		this.orderCreationDate = orderCreationDate;
	}
	
	
	
	//[003] End
	//[606060] start
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
	//[606060] end
	
	
}
