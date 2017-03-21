package com.ibm.ioes.newdesign.dto;

import com.ibm.ioes.forms.ComponentsDto;
import com.ibm.ioes.utilities.PagingSorting;
/*Tag Name Resource Name  Date		CSR No			Description -->
//[01]  opportunityId added  by Lawkush in reports "ACTIVE LINE ITEMS REPORT   ","PERFORMANCE SUMMARY REPORT  ","PERFORMANCE DETAIL REPORT","ORDER DETAIL REPORT","ORDER REPORT NEW DETAIL CWN","LEPM ORDER DETAIL REPORT"
//[02]   Shourya   04-Dec-2013                     Added New Column
//[004] Gunjan Singla  22-Sept-14 CBR_20140704-XX-020224 Global Data Billing Efficiencies Phase1
//[005]   Neha Maheshwari    04-Dec-2014   CSR20141014-R2-020535-Service Segment Configuration-SMB
//[007]   Neha Maheshwari    14-Apr-15     CSR-20150212-R1-021088  Addition of Column in Active and Pending Line Item Reports
//[008]   Neha Maheshwari 10-Jun-2015 20141219-R2-020936 Billing Efficiency Program Reports
//[009]     Neha Maheshwari 07-Sep-2015 20150819-XX-021651 TimeStamp Reports 
//[010]  Priya Gupta       25-Jan-2016	Added 3 columns
//[011]  Gunjan Singla  6-May-2016  20160418-R1-022266    Added columns
//[012]  Nancy Singla   6-June-2016                       Added Columns
 * */  
public class ActiveLineItemReportsDTO {
	private String logicalSINo;
	private String custSINo;
	private String serviceName;
	private String linename;
	private String chargeTypeName;
	private int chargeTypeID;
	private String toDate;
	private int isUsage;
	private String fromCopcApprovedDate;
	PagingSorting pagingSorting = new PagingSorting();
	private String fromDate;
	private int toOrderNo;
	private int fromOrderNo;
	private String chargeName;
	private String chargeFrequency;
	private String billPeriod;
	private String startDate;
	private long accountID;
	private String creditPeriodName;
	private String currencyName;
	private String entity;
	private String billingMode;
	private String billingTypeName;
	private String billingformat;
	private String licCompanyName;
	private String taxation;
	private String billingLevelName;
	private Long billingLevelNo;
	private String store;
	private String hardwaretypeName;
	private String formAvailable;
	private String saleNature;
	private String saleTypeName;
	private String primaryLocation;
	private String seclocation;
	private int poNumber;
	private int fxInternalId;
	private String minimum_bandwidth;
	private String minimum_bandwidth_UOM;
	private int componentInfoID;
	private int componentID;
	private String componentName;
	private int packageID;
	private String packageName;
	private String poDate;
	private String party_num;
	private String chargeAnnotation;
	private String fx_charge_status;
	private String fx_sd_charge_status;
	private String fx_Ed_Chg_Status;
	private int tokenID;
	private String modifiedDate;
	private String billingTriggerFlag;
	private String pm_pro_date;
	private String locDate;
	private String billing_trigger_date;
	private String challenno;
	private String challendate;
	private String fx_external_acc_id;
	private String billing_location_to;
	private String orderApproveDate;
	private String child_account_creation_status;
	private String orderDate;
	private String copcapprovaldate;
	private String orderType;
	//private String orderApproveDate;
	//private String billing_location_to;
	private String pmapprovaldate;
	//private String copcapprovaldate;
	private String billingtrigger_createdate;
	//private String custSINo;
	private String toCopcApprovedDate;
	private String ratio;
	private String productName;
	private String subProductName;
	private String hardwareType;
	//private int chargeTypeID;
	private String serviceStage;
	private String orderStage;
	//private String orderDate;
	private String rfsDate;
	private String poReceiveDate;
	private String custPoDetailNo;
	private String custPoDate;
	private String charge_status;
	private String LOC_No;
	private String address1;
	private String m6cktid;
	private ComponentsDto componentDto;
	private String ordersubtype;
	
	private String region;
	private String bandwaidth;
	private String vertical;
	
	private String projectManager;
	private String accountManager;
	private String rate_code;

	private String lmMedia;
	private String lmRemarks;
	
	private String chargeable_Distance;
	private String Distance;
	private String dispatchAddress1;
	private String indicative_value;
	//private String productName;
	private String partyName;
	private String billing_location_from;
	private String demo;
	private String crm_productname;
	
	private String fromLocation;
	private String toLocation;
	private String billing_bandwidth;
	private String billing_uom;
	private String blSource;
	private int serviceproductid;
	private int orderNumber;
	private double chargeAmount;
	private double lineamt;
	private double chargesSumOfLineitem;
	private int contractPeriod;
	private String totalPoAmt;
	private int party_id;
	private String crmAccountNoString;
	private String externalId;
	private int m6OrderNo;	
	private double cust_total_poamt;
	//private String m6OrderNo;
	
	//private String serviceproductid;
	
	private String pk_charge_id;
	//private String contractPeriod;
	private double annual_rate;
	private int serviceId;
	private String poExpiryDate;
//	[01] Start  	 
	 private String opportunityId;	

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

	//Vijay add a variable for token ID that should be a string
	private String token_ID;
	
	 private String groupName;	
	//[02]  Start
	 private int business_serial_n;
	 
	 private String opms_Account_Id;
	 private String lineItemNumber;
	 private String opms_lineItemNumber;
	 private String oldLsi;
	 private String mocn_no;
	 //[02] End
	 //[004] start
	 private long mainSpid;
	 private String taxExemptReasonName;
	//[004] end
	//[005] Start
	 private String serviceSegment;
	 //[005] End
	 
	//[006] rahul Start
	private String chargeRedirectionLSI;
	//[006] rahul End
	
	//[007] Start
    private String installationFromCity;
	private String installationToCity;
	private String installationFromState;
	private String installationToState;
	private String billingContactName;
	private String billingContactNumber;
	private String billingEmailId;
	//[007] End
	//Satyapan OSP/ISP
	private String isOspRequired;
	private String ospRegistrationNo;
	private String ospRegistrationDate;
	private String isIspRequired;
	private String ispCatageory;
	private String ispLicenseNo;
	private String ispLicenseDate;
	//end Satyapan OSP/ISP
	
	//[008] Start
	private String standardReason;
	private String ldClause;
	//[008] End
	
	//[009] Start
	private String orderCreationDate;
	private String publishedDate;
	//[009] End
	
	//[010] Start
	private String salesForceOpportunityNumber;
	private String networkType;
	private String channelPartner;
	private String partnerId;
	//[010] End
	//[011] start
	private String fieldEngineer;
	private String partnerCode;
	//[011] end

	//[012] start
	private String ePCNNo;
	//[012] end
	
	 //pankaj start
	 private int partyNo;
	 private String customerSegment;
	 //pankaj end
	public int getBusiness_serial_n() {
		return business_serial_n;
	}
	public int getPartyNo() {
		return partyNo;
	}
	public void setPartyNo(int partyNo) {
		this.partyNo = partyNo;
	}
	public String getCustomerSegment() {
		return customerSegment;
	}
	public void setCustomerSegment(String customerSegment) {
		this.customerSegment = customerSegment;
	}
	public void setBusiness_serial_n(int business_serial_n) {
		this.business_serial_n = business_serial_n;
	}
	 public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getOpportunityId() {
		return opportunityId;
	}
	public void setOpportunityId(String opportunityId) {
		this.opportunityId = opportunityId;
	}
//	[01] end  
	public long getAccountID() {
		return accountID;
	}
	public void setAccountID(long accountID) {
		this.accountID = accountID;
	}
	public String getAccountManager() {
		return accountManager;
	}
	public void setAccountManager(String accountManager) {
		this.accountManager = accountManager;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public double getAnnual_rate() {
		return annual_rate;
	}
	public void setAnnual_rate(double annual_rate) {
		this.annual_rate = annual_rate;
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
	public String getBilling_location_from() {
		return billing_location_from;
	}
	public void setBilling_location_from(String billing_location_from) {
		this.billing_location_from = billing_location_from;
	}
	public String getBilling_trigger_date() {
		return billing_trigger_date;
	}
	public void setBilling_trigger_date(String billing_trigger_date) {
		this.billing_trigger_date = billing_trigger_date;
	}
	public String getBilling_uom() {
		return billing_uom;
	}
	public void setBilling_uom(String billing_uom) {
		this.billing_uom = billing_uom;
	}
	public String getBillingformat() {
		return billingformat;
	}
	public void setBillingformat(String billingformat) {
		this.billingformat = billingformat;
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
	public String getBillingMode() {
		return billingMode;
	}
	public void setBillingMode(String billingMode) {
		this.billingMode = billingMode;
	}
	public String getBillingtrigger_createdate() {
		return billingtrigger_createdate;
	}
	public void setBillingtrigger_createdate(String billingtrigger_createdate) {
		this.billingtrigger_createdate = billingtrigger_createdate;
	}
	public String getBillingTriggerFlag() {
		return billingTriggerFlag;
	}
	public void setBillingTriggerFlag(String billingTriggerFlag) {
		this.billingTriggerFlag = billingTriggerFlag;
	}
	public String getBillingTypeName() {
		return billingTypeName;
	}
	public void setBillingTypeName(String billingTypeName) {
		this.billingTypeName = billingTypeName;
	}
	public String getBillPeriod() {
		return billPeriod;
	}
	public void setBillPeriod(String billPeriod) {
		this.billPeriod = billPeriod;
	}
	public String getBlSource() {
		return blSource;
	}
	public void setBlSource(String blSource) {
		this.blSource = blSource;
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
	public String getCharge_status() {
		return charge_status;
	}
	public void setCharge_status(String charge_status) {
		this.charge_status = charge_status;
	}
	public String getChargeable_Distance() {
		return chargeable_Distance;
	}
	public void setChargeable_Distance(String chargeable_Distance) {
		this.chargeable_Distance = chargeable_Distance;
	}
	public double getChargeAmount() {
		return chargeAmount;
	}
	public void setChargeAmount(double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}
	public String getChargeAnnotation() {
		return chargeAnnotation;
	}
	public void setChargeAnnotation(String chargeAnnotation) {
		this.chargeAnnotation = chargeAnnotation;
	}
	public String getChargeFrequency() {
		return chargeFrequency;
	}
	public void setChargeFrequency(String chargeFrequency) {
		this.chargeFrequency = chargeFrequency;
	}
	public String getChargeName() {
		return chargeName;
	}
	public void setChargeName(String chargeName) {
		this.chargeName = chargeName;
	}
	public double getChargesSumOfLineitem() {
		return chargesSumOfLineitem;
	}
	public void setChargesSumOfLineitem(double chargesSumOfLineitem) {
		this.chargesSumOfLineitem = chargesSumOfLineitem;
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
	public String getChild_account_creation_status() {
		return child_account_creation_status;
	}
	public void setChild_account_creation_status(
			String child_account_creation_status) {
		this.child_account_creation_status = child_account_creation_status;
	}
	public int getContractPeriod() {
		return contractPeriod;
	}
	public void setContractPeriod(int contractPeriod) {
		this.contractPeriod = contractPeriod;
	}
	public String getCopcapprovaldate() {
		return copcapprovaldate;
	}
	public void setCopcapprovaldate(String copcapprovaldate) {
		this.copcapprovaldate = copcapprovaldate;
	}
	public String getCreditPeriodName() {
		return creditPeriodName;
	}
	public void setCreditPeriodName(String creditPeriodName) {
		this.creditPeriodName = creditPeriodName;
	}
	public String getCrm_productname() {
		return crm_productname;
	}
	public void setCrm_productname(String crm_productname) {
		this.crm_productname = crm_productname;
	}
	public String getCrmAccountNoString() {
		return crmAccountNoString;
	}
	public void setCrmAccountNoString(String crmAccountNoString) {
		this.crmAccountNoString = crmAccountNoString;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public double getCust_total_poamt() {
		return cust_total_poamt;
	}
	public void setCust_total_poamt(double cust_total_poamt) {
		this.cust_total_poamt = cust_total_poamt;
	}
	public String getCustPoDate() {
		return custPoDate;
	}
	public void setCustPoDate(String custPoDate) {
		this.custPoDate = custPoDate;
	}
	public String getCustPoDetailNo() {
		return custPoDetailNo;
	}
	public void setCustPoDetailNo(String custPoDetailNo) {
		this.custPoDetailNo = custPoDetailNo;
	}
	public String getCustSINo() {
		return custSINo;
	}
	public void setCustSINo(String custSINo) {
		this.custSINo = custSINo;
	}
	public String getDemo() {
		return demo;
	}
	public void setDemo(String demo) {
		this.demo = demo;
	}
	public String getDispatchAddress1() {
		return dispatchAddress1;
	}
	public void setDispatchAddress1(String dispatchAddress1) {
		this.dispatchAddress1 = dispatchAddress1;
	}
	public String getEntity() {
		return entity;
	}
	public void setEntity(String entity) {
		this.entity = entity;
	}
	public String getFormAvailable() {
		return formAvailable;
	}
	public void setFormAvailable(String formAvailable) {
		this.formAvailable = formAvailable;
	}
	public String getFromLocation() {
		return fromLocation;
	}
	public void setFromLocation(String fromLocation) {
		this.fromLocation = fromLocation;
	}
	public String getFx_charge_status() {
		return fx_charge_status;
	}
	public void setFx_charge_status(String fx_charge_status) {
		this.fx_charge_status = fx_charge_status;
	}
	public String getFx_Ed_Chg_Status() {
		return fx_Ed_Chg_Status;
	}
	public void setFx_Ed_Chg_Status(String fx_Ed_Chg_Status) {
		this.fx_Ed_Chg_Status = fx_Ed_Chg_Status;
	}
	public String getFx_external_acc_id() {
		return fx_external_acc_id;
	}
	public void setFx_external_acc_id(String fx_external_acc_id) {
		this.fx_external_acc_id = fx_external_acc_id;
	}
	public String getFx_sd_charge_status() {
		return fx_sd_charge_status;
	}
	public void setFx_sd_charge_status(String fx_sd_charge_status) {
		this.fx_sd_charge_status = fx_sd_charge_status;
	}
	public String getHardwareType() {
		return hardwareType;
	}
	public void setHardwareType(String hardwareType) {
		this.hardwareType = hardwareType;
	}
	public String getHardwaretypeName() {
		return hardwaretypeName;
	}
	public void setHardwaretypeName(String hardwaretypeName) {
		this.hardwaretypeName = hardwaretypeName;
	}
	public String getIndicative_value() {
		return indicative_value;
	}
	public void setIndicative_value(String indicative_value) {
		this.indicative_value = indicative_value;
	}
	public String getLicCompanyName() {
		return licCompanyName;
	}
	public void setLicCompanyName(String licCompanyName) {
		this.licCompanyName = licCompanyName;
	}
	public double getLineamt() {
		return lineamt;
	}
	public void setLineamt(double lineamt) {
		this.lineamt = lineamt;
	}
	public String getLinename() {
		return linename;
	}
	public void setLinename(String linename) {
		this.linename = linename;
	}
	public String getLOC_No() {
		return LOC_No;
	}
	public void setLOC_No(String no) {
		LOC_No = no;
	}
	public String getLocDate() {
		return locDate;
	}
	public void setLocDate(String locDate) {
		this.locDate = locDate;
	}
	public String getLogicalSINo() {
		return logicalSINo;
	}
	public void setLogicalSINo(String logicalSINo) {
		this.logicalSINo = logicalSINo;
	}
	public String getM6cktid() {
		return m6cktid;
	}
	public void setM6cktid(String m6cktid) {
		this.m6cktid = m6cktid;
	}
	public int getM6OrderNo() {
		return m6OrderNo;
	}
	public void setM6OrderNo(int orderNo) {
		m6OrderNo = orderNo;
	}
	public String getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
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
	public String getOrderStage() {
		return orderStage;
	}
	public void setOrderStage(String orderStage) {
		this.orderStage = orderStage;
	}
	public String getOrdersubtype() {
		return ordersubtype;
	}
	public void setOrdersubtype(String ordersubtype) {
		this.ordersubtype = ordersubtype;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public int getParty_id() {
		return party_id;
	}
	public void setParty_id(int party_id) {
		this.party_id = party_id;
	}
	public String getParty_num() {
		return party_num;
	}
	public void setParty_num(String party_num) {
		this.party_num = party_num;
	}
	public String getPartyName() {
		return partyName;
	}
	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}
	public String getPk_charge_id() {
		return pk_charge_id;
	}
	public void setPk_charge_id(String pk_charge_id) {
		this.pk_charge_id = pk_charge_id;
	}
	public String getPm_pro_date() {
		return pm_pro_date;
	}
	public void setPm_pro_date(String pm_pro_date) {
		this.pm_pro_date = pm_pro_date;
	}
	public String getPmapprovaldate() {
		return pmapprovaldate;
	}
	public void setPmapprovaldate(String pmapprovaldate) {
		this.pmapprovaldate = pmapprovaldate;
	}
	public String getPoDate() {
		return poDate;
	}
	public void setPoDate(String poDate) {
		this.poDate = poDate;
	}
	public String getPoExpiryDate() {
		return poExpiryDate;
	}
	public void setPoExpiryDate(String poExpiryDate) {
		this.poExpiryDate = poExpiryDate;
	}
	public int getPoNumber() {
		return poNumber;
	}
	public void setPoNumber(int poNumber) {
		this.poNumber = poNumber;
	}
	public String getPoReceiveDate() {
		return poReceiveDate;
	}
	public void setPoReceiveDate(String poReceiveDate) {
		this.poReceiveDate = poReceiveDate;
	}
	public String getPrimaryLocation() {
		return primaryLocation;
	}
	public void setPrimaryLocation(String primaryLocation) {
		this.primaryLocation = primaryLocation;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProjectManager() {
		return projectManager;
	}
	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}
	public String getRate_code() {
		return rate_code;
	}
	public void setRate_code(String rate_code) {
		this.rate_code = rate_code;
	}
	public String getRatio() {
		return ratio;
	}
	public void setRatio(String ratio) {
		this.ratio = ratio;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getRfsDate() {
		return rfsDate;
	}
	public void setRfsDate(String rfsDate) {
		this.rfsDate = rfsDate;
	}
	public String getSaleNature() {
		return saleNature;
	}
	public void setSaleNature(String saleNature) {
		this.saleNature = saleNature;
	}
	public String getSaleTypeName() {
		return saleTypeName;
	}
	public void setSaleTypeName(String saleTypeName) {
		this.saleTypeName = saleTypeName;
	}
	public String getSeclocation() {
		return seclocation;
	}
	public void setSeclocation(String seclocation) {
		this.seclocation = seclocation;
	}
	public int getServiceId() {
		return serviceId;
	}
	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public int getServiceproductid() {
		return serviceproductid;
	}
	public void setServiceproductid(int serviceproductid) {
		this.serviceproductid = serviceproductid;
	}
	public String getServiceStage() {
		return serviceStage;
	}
	public void setServiceStage(String serviceStage) {
		this.serviceStage = serviceStage;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getStore() {
		return store;
	}
	public void setStore(String store) {
		this.store = store;
	}
	public String getSubProductName() {
		return subProductName;
	}
	public void setSubProductName(String subProductName) {
		this.subProductName = subProductName;
	}
	public String getTaxation() {
		return taxation;
	}
	public void setTaxation(String taxation) {
		this.taxation = taxation;
	}
	public int getTokenID() {
		return tokenID;
	}
	public void setTokenID(int tokenID) {
		this.tokenID = tokenID;
	}
	public String getToLocation() {
		return toLocation;
	}
	public void setToLocation(String toLocation) {
		this.toLocation = toLocation;
	}
	public String getTotalPoAmt() {
		return totalPoAmt;
	}
	public void setTotalPoAmt(String totalPoAmt) {
		this.totalPoAmt = totalPoAmt;
	}
	public String getVertical() {
		return vertical;
	}
	public void setVertical(String vertical) {
		this.vertical = vertical;
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
	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}
	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
	}
	public String getDistance() {
		return Distance;
	}
	public void setDistance(String distance) {
		Distance = distance;
	}
	public String getFromCopcApprovedDate() {
		return fromCopcApprovedDate;
	}
	public void setFromCopcApprovedDate(String fromCopcApprovedDate) {
		this.fromCopcApprovedDate = fromCopcApprovedDate;
	}
	public int getIsUsage() {
		return isUsage;
	}
	public void setIsUsage(int isUsage) {
		this.isUsage = isUsage;
	}
	public String getToCopcApprovedDate() {
		return toCopcApprovedDate;
	}
	public void setToCopcApprovedDate(String toCopcApprovedDate) {
		this.toCopcApprovedDate = toCopcApprovedDate;
	}
	
	public void setOrderApproveDate(String string) {
		// TODO Auto-generated method stub
		
	}
	public String getBilling_location_to() {
		return billing_location_to;
	}
	public void setBilling_location_to(String billing_location_to) {
		this.billing_location_to = billing_location_to;
	}
	public String getOrderApproveDate() {
		return orderApproveDate;
	}
	public ComponentsDto getComponentDto() {
		return componentDto;
	}
	public void setComponentDto(ComponentsDto componentDto) {
		this.componentDto = componentDto;
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
	public String getComponentName() {
		return componentName;
	}
	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}
	public int getFxInternalId() {
		return fxInternalId;
	}
	public void setFxInternalId(int fxInternalId) {
		this.fxInternalId = fxInternalId;
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
	//Vijay start
	public String getToken_ID() {
		return token_ID;
	}
	public void setToken_ID(String token_ID) {
		this.token_ID = token_ID;
	}
	//vijay end
	public String getLmMedia() {
		return lmMedia;
	}
	public void setLmMedia(String lmMedia) {
		this.lmMedia = lmMedia;
	}
	public String getLmRemarks() {
		return lmRemarks;
	}
	public void setLmRemarks(String lmRemarks) {
		this.lmRemarks = lmRemarks;
	}
	public String getOpms_Account_Id() {
		return opms_Account_Id;
	}
	public void setOpms_Account_Id(String opms_Account_Id) {
		this.opms_Account_Id = opms_Account_Id;
	}
	public String getExternalId() {
		return externalId;
	}
		public String getLineItemNumber() {
		return lineItemNumber;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public void setLineItemNumber(String lineItemNumber) {
		this.lineItemNumber = lineItemNumber;
	}
	public String getOldLsi() {
		return oldLsi;
	}
	public void setOldLsi(String oldLsi) {
		this.oldLsi = oldLsi;
	}
	public String getOpms_lineItemNumber() {
		return opms_lineItemNumber;
	}
	public void setOpms_lineItemNumber(String opms_lineItemNumber) {
		this.opms_lineItemNumber = opms_lineItemNumber;
	}
	public String getMocn_no() {
		return mocn_no;
	}
	public void setMocn_no(String mocn_no) {
		this.mocn_no = mocn_no;
	}
	//[004] start
	
	public String getTaxExemptReasonName() {
		return taxExemptReasonName;
	}
	public long getMainSpid() {
		return mainSpid;
	}
	public void setMainSpid(long mainSpid) {
		this.mainSpid = mainSpid;
	}
	public void setTaxExemptReasonName(String taxExemptReasonName) {
		this.taxExemptReasonName = taxExemptReasonName;
	}
	
	//[004] end
	//[005] Start
	public String getServiceSegment() {
		return serviceSegment;
	}
	public void setServiceSegment(String serviceSegment) {
		this.serviceSegment = serviceSegment;
	}
    //[005] End
	
	//[006] rahul Start
	public String getChargeRedirectionLSI() {
		return chargeRedirectionLSI;
	}
	public void setChargeRedirectionLSI(String chargeRedirectionLSI) {
		this.chargeRedirectionLSI = chargeRedirectionLSI;
	}
	//[006] rahul End
	
	//[007] Start
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
	public String getIsOspRequired() {
		return isOspRequired;
	}
	public void setIsOspRequired(String isOspRequired) {
		this.isOspRequired = isOspRequired;
	}
	public String getOspRegistrationNo() {
		return ospRegistrationNo;
	}
	public void setOspRegistrationNo(String ospRegistrationNo) {
		this.ospRegistrationNo = ospRegistrationNo;
	}
	public String getOspRegistrationDate() {
		return ospRegistrationDate;
	}
	public void setOspRegistrationDate(String ospRegistrationDate) {
		this.ospRegistrationDate = ospRegistrationDate;
	}
	public String getIsIspRequired() {
		return isIspRequired;
	}
	public void setIsIspRequired(String isIspRequired) {
		this.isIspRequired = isIspRequired;
	}
	public String getIspCatageory() {
		return ispCatageory;
	}
	public void setIspCatageory(String ispCatageory) {
		this.ispCatageory = ispCatageory;
	}
	public String getIspLicenseNo() {
		return ispLicenseNo;
	}
	public void setIspLicenseNo(String ispLicenseNo) {
		this.ispLicenseNo = ispLicenseNo;
	}
	public String getIspLicenseDate() {
		return ispLicenseDate;
	}
	public void setIspLicenseDate(String ispLicenseDate) {
		this.ispLicenseDate = ispLicenseDate;
	}
		
	//[007] End
	
	//[008] Start
	public String getStandardReason() {
		return standardReason;
	}
	public void setStandardReason(String standardReason) {
		this.standardReason = standardReason;
	}
	public String getLdClause() {
		return ldClause;
	}
	public void setLdClause(String ldClause) {
		this.ldClause = ldClause;
	}
	
	//[008] End
	
	//[009] Start
	public String getOrderCreationDate() {
		return orderCreationDate;
	}
	public void setOrderCreationDate(String orderCreationDate) {
		this.orderCreationDate = orderCreationDate;
	}
	public String getPublishedDate() {
		return publishedDate;
	}
	public void setPublishedDate(String publishedDate) {
		this.publishedDate = publishedDate;
	}
	
	
	//[009] End
	//[010] Start
	public String getSalesForceOpportunityNumber() {
		return salesForceOpportunityNumber;
	}
	public void setSalesForceOpportunityNumber(String salesForceOpportunityNumber) {
		this.salesForceOpportunityNumber = salesForceOpportunityNumber;
	}
	public String getNetworkType() {
		return networkType;
	}
	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}
	public String getChannelPartner() {
		return channelPartner;
	}
	public void setChannelPartner(String channelPartner) {
		this.channelPartner = channelPartner;
	}
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	//[010] End
	//[011] start
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
	//[011] end	
	//[012] start
	public String getePCNNo() {
		return ePCNNo;
	}
	public void setePCNNo(String ePCNNo) {
		this.ePCNNo = ePCNNo;
	} 
	//[012] end
		
}
