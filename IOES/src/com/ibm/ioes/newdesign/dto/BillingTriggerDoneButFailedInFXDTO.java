

package com.ibm.ioes.newdesign.dto;

import com.ibm.ioes.forms.ComponentsDto;
import com.ibm.ioes.utilities.PagingSorting;

public class BillingTriggerDoneButFailedInFXDTO {
	PagingSorting pagingSorting = new PagingSorting();
	private int logicalSINumber;
	private String toDate;
	private String orderApproveDate;
	private String fromDate;
	private Long billingLevelNo;
	private long ponum;
	private String opms_Account_Id;
	private int componentID;
	private long fx_internal_acc_id;
	private ComponentsDto componentDto;
	private String annualRate;
	private String dnd_Dispatch_But_Not_Delivered;
	private String dnd_Dispatch_And_Delivered;
	private String dnd_Disp_Del_Not_Installed;
	private String dnd_Disp_Delivered_Installed;
	private int componentInfoID;
	private String componentName;
	private String packageName;
	private int packageID;
	private String serviceOrderType;
	private int m6OrderNo;
	private String cancelServiceReason;
	private int isUsage;
	private int toOrderNo;
	private int fromOrderNo;
	private int toAccountNo;
	private int fromAccountNo;
	private String billingLevel;
	private int customer_logicalSINumber;
	private String serviceName;	
	private String serviceDetDescription;															
	private String chargeTypeName;
	private String chargeName;
	private String frequencyName;
	private String billPeriod;
	private String startDateLogic;
	private String endDateLogic;								
	private String endDate;		
	private String startDate;
	private String contractStartDate;
	private String contractEndDate;
	private String crmAccountNoString;
	private String creditPeriodName;
	private String currencyName;
	private String entity;
	private String billingMode;
	private String billingTypeName;
	private String billingFormatName;
	private String lcompanyname;
	private String taxation;
	private String dispatchAddressName;
	private String penaltyClause;
	private String billingLevelName;
	private String billingLevelNo1;
	private String store;
	private String hardwaretypeName;							
	private String formAvailable;
	private String saleNature;
	private String saleType;
	private String primaryLocation;
	private String secondaryLocation;
	private String ponum1;
	private String poDate;
	private String partyName;
	private int party_no;
	private String chargeAnnotation;
	private String fxStatus;
	private String fx_St_Chg_Status;
	private String fx_Ed_Chg_Status;
	private String tokenNO;
	private String billing_Trigger_Flag;
	private String LOC_Date;
	private String locDate;
	private String billingTriggerDate;
	private String challenno;
	private String challendate;
	private String fx_external_acc_id;
	private String childAccountFXSataus;
	private String warrantyStartDate;
	private String warrantyEndDate;
	private String extSuportEndDate;
	private String orderDate;
	//private String copcApproveDate;							
	private String orderType;
	
	private String ordersubtype;
	private String serviceStageDescription;
	private String copcApproveDate;	
	private String billingtrigger_createdate;
	private String hardwareFlag;
	private int chargeTypeID;						
	private String serviceStage;
	private String orderStage;
	private String accountManager;
	private String projectManager;
	private String createdDate;
	private String rfsDate;
	private String poRecieveDate;
	private String tokenNoEd;
	
	private String custPoDetailNo;
	private String custPoDate;																						
	private String charge_status;
	private String LOC_No;							
	private String billingAddress;
	private String fxSiId;
	private String cancelBy;
	private String canceldate;
	private String cancelReason;
	private String regionName;
	private String bandwaidth;
	private String verticalDetails;
	private String toLocation;
	private String fromLocation;
	private String coll_Manager;
	private String coll_Manager_Mail;
	private String coll_Manager_Phone;
	private String billing_bandwidth;
	private String order_type;
	//private String orderNumber;						
	private int orderNumber;		
	private int charge_hdr_id;
	private int orderLineNumber;
	private int chargeInfoID;
	private int serviceId;
	private String chargeAmount_String;
	//private String lineItemAmount;
	private String lineItemAmount;
	
	private int startDateDays;
	private int startDateMonth;
	private int endDateDays;
	private int endDateMonth;
	private double annual_rate;
	
	//private String contractPeriod;							
	private int commitmentPeriod;
	private long noticePeriod;
	
	private int contractPeriod;
	private String totalPoAmt;
	private int party_id;
	private int accountID;
	private String m6_prod_id;
//	private String m6_order_id;
	private String start_fx_no;
	private String end_fx_no;
	private String poAmount;
	private String m6_order_id;
	
	
	public int getAccountID() {
		return accountID;
	}
	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}
	public String getAccountManager() {
		return accountManager;
	}
	public void setAccountManager(String accountManager) {
		this.accountManager = accountManager;
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
	public String getBilling_Trigger_Flag() {
		return billing_Trigger_Flag;
	}
	public void setBilling_Trigger_Flag(String billing_Trigger_Flag) {
		this.billing_Trigger_Flag = billing_Trigger_Flag;
	}
	public String getBillingAddress() {
		return billingAddress;
	}
	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
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
	public String getBillingtrigger_createdate() {
		return billingtrigger_createdate;
	}
	public void setBillingtrigger_createdate(String billingtrigger_createdate) {
		this.billingtrigger_createdate = billingtrigger_createdate;
	}
	public String getBillingTriggerDate() {
		return billingTriggerDate;
	}
	public void setBillingTriggerDate(String billingTriggerDate) {
		this.billingTriggerDate = billingTriggerDate;
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
	public String getCancelBy() {
		return cancelBy;
	}
	public void setCancelBy(String cancelBy) {
		this.cancelBy = cancelBy;
	}
	public String getCanceldate() {
		return canceldate;
	}
	public void setCanceldate(String canceldate) {
		this.canceldate = canceldate;
	}
	public String getCancelReason() {
		return cancelReason;
	}
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
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
	public int getCharge_hdr_id() {
		return charge_hdr_id;
	}
	public void setCharge_hdr_id(int charge_hdr_id) {
		this.charge_hdr_id = charge_hdr_id;
	}
	public String getCharge_status() {
		return charge_status;
	}
	public void setCharge_status(String charge_status) {
		this.charge_status = charge_status;
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
	public String getChargeTypeName() {
		return chargeTypeName;
	}
	public void setChargeTypeName(String chargeTypeName) {
		this.chargeTypeName = chargeTypeName;
	}
	public String getChildAccountFXSataus() {
		return childAccountFXSataus;
	}
	public void setChildAccountFXSataus(String childAccountFXSataus) {
		this.childAccountFXSataus = childAccountFXSataus;
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
	public int getCommitmentPeriod() {
		return commitmentPeriod;
	}
	public void setCommitmentPeriod(int commitmentPeriod) {
		this.commitmentPeriod = commitmentPeriod;
	}
	public String getContractEndDate() {
		return contractEndDate;
	}
	public void setContractEndDate(String contractEndDate) {
		this.contractEndDate = contractEndDate;
	}
	public int getContractPeriod() {
		return contractPeriod;
	}
	public void setContractPeriod(int contractPeriod) {
		this.contractPeriod = contractPeriod;
	}
	public String getContractStartDate() {
		return contractStartDate;
	}
	public void setContractStartDate(String contractStartDate) {
		this.contractStartDate = contractStartDate;
	}
	public String getCopcApproveDate() {
		return copcApproveDate;
	}
	public void setCopcApproveDate(String copcApproveDate) {
		this.copcApproveDate = copcApproveDate;
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
	public int getCustomer_logicalSINumber() {
		return customer_logicalSINumber;
	}
	public void setCustomer_logicalSINumber(int customer_logicalSINumber) {
		this.customer_logicalSINumber = customer_logicalSINumber;
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
	public String getDispatchAddressName() {
		return dispatchAddressName;
	}
	public void setDispatchAddressName(String dispatchAddressName) {
		this.dispatchAddressName = dispatchAddressName;
	}
	public String getEnd_fx_no() {
		return end_fx_no;
	}
	public void setEnd_fx_no(String end_fx_no) {
		this.end_fx_no = end_fx_no;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
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
	public String getEntity() {
		return entity;
	}
	public void setEntity(String entity) {
		this.entity = entity;
	}
	public String getExtSuportEndDate() {
		return extSuportEndDate;
	}
	public void setExtSuportEndDate(String extSuportEndDate) {
		this.extSuportEndDate = extSuportEndDate;
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
	public int getFromAccountNo() {
		return fromAccountNo;
	}
	public void setFromAccountNo(int fromAccountNo) {
		this.fromAccountNo = fromAccountNo;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getFromLocation() {
		return fromLocation;
	}
	public void setFromLocation(String fromLocation) {
		this.fromLocation = fromLocation;
	}
	public int getFromOrderNo() {
		return fromOrderNo;
	}
	public void setFromOrderNo(int fromOrderNo) {
		this.fromOrderNo = fromOrderNo;
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
	public String getFx_St_Chg_Status() {
		return fx_St_Chg_Status;
	}
	public void setFx_St_Chg_Status(String fx_St_Chg_Status) {
		this.fx_St_Chg_Status = fx_St_Chg_Status;
	}
	public String getFxSiId() {
		return fxSiId;
	}
	public void setFxSiId(String fxSiId) {
		this.fxSiId = fxSiId;
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
	public String getHardwaretypeName() {
		return hardwaretypeName;
	}
	public void setHardwaretypeName(String hardwaretypeName) {
		this.hardwaretypeName = hardwaretypeName;
	}
	public String getLcompanyname() {
		return lcompanyname;
	}
	public void setLcompanyname(String lcompanyname) {
		this.lcompanyname = lcompanyname;
	}
	public String getLineItemAmount() {
		return lineItemAmount;
	}
	public void setLineItemAmount(String lineItemAmount) {
		this.lineItemAmount = lineItemAmount;
	}
	public String getLOC_Date() {
		return LOC_Date;
	}
	public void setLOC_Date(String date) {
		LOC_Date = date;
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
	public int getLogicalSINumber() {
		return logicalSINumber;
	}
	public void setLogicalSINumber(int logicalSINumber) {
		this.logicalSINumber = logicalSINumber;
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
	public long getNoticePeriod() {
		return noticePeriod;
	}
	public void setNoticePeriod(long noticePeriod) {
		this.noticePeriod = noticePeriod;
	}
	public String getOrder_type() {
		return order_type;
	}
	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public int getOrderLineNumber() {
		return orderLineNumber;
	}
	public void setOrderLineNumber(int orderLineNumber) {
		this.orderLineNumber = orderLineNumber;
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
	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}
	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
	}
	public int getParty_id() {
		return party_id;
	}
	public void setParty_id(int party_id) {
		this.party_id = party_id;
	}
	public int getParty_no() {
		return party_no;
	}
	public void setParty_no(int party_no) {
		this.party_no = party_no;
	}
	public String getPartyName() {
		return partyName;
	}
	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}
	public String getPenaltyClause() {
		return penaltyClause;
	}
	public void setPenaltyClause(String penaltyClause) {
		this.penaltyClause = penaltyClause;
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
	public String getPonum1() {
		return ponum1;
	}
	public void setPonum1(String ponum1) {
		this.ponum1 = ponum1;
	}
	public String getPoRecieveDate() {
		return poRecieveDate;
	}
	public void setPoRecieveDate(String poRecieveDate) {
		this.poRecieveDate = poRecieveDate;
	}
	public String getPrimaryLocation() {
		return primaryLocation;
	}
	public void setPrimaryLocation(String primaryLocation) {
		this.primaryLocation = primaryLocation;
	}
	public String getProjectManager() {
		return projectManager;
	}
	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
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
	public String getSaleType() {
		return saleType;
	}
	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}
	public String getSecondaryLocation() {
		return secondaryLocation;
	}
	public void setSecondaryLocation(String secondaryLocation) {
		this.secondaryLocation = secondaryLocation;
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
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getServiceStage() {
		return serviceStage;
	}
	public void setServiceStage(String serviceStage) {
		this.serviceStage = serviceStage;
	}
	public String getServiceStageDescription() {
		return serviceStageDescription;
	}
	public void setServiceStageDescription(String serviceStageDescription) {
		this.serviceStageDescription = serviceStageDescription;
	}
	public String getStart_fx_no() {
		return start_fx_no;
	}
	public void setStart_fx_no(String start_fx_no) {
		this.start_fx_no = start_fx_no;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public int getStartDateDays() {
		return startDateDays;
	}
	public void setStartDateDays(int startDateDays) {
		this.startDateDays = startDateDays;
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
	public String getStore() {
		return store;
	}
	public void setStore(String store) {
		this.store = store;
	}
	public String getTaxation() {
		return taxation;
	}
	public void setTaxation(String taxation) {
		this.taxation = taxation;
	}
	public int getToAccountNo() {
		return toAccountNo;
	}
	public void setToAccountNo(int toAccountNo) {
		this.toAccountNo = toAccountNo;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getTokenNO() {
		return tokenNO;
	}
	public void setTokenNO(String tokenNO) {
		this.tokenNO = tokenNO;
	}
	public String getTokenNoEd() {
		return tokenNoEd;
	}
	public void setTokenNoEd(String tokenNoEd) {
		this.tokenNoEd = tokenNoEd;
	}
	public String getToLocation() {
		return toLocation;
	}
	public void setToLocation(String toLocation) {
		this.toLocation = toLocation;
	}
	public int getToOrderNo() {
		return toOrderNo;
	}
	public void setToOrderNo(int toOrderNo) {
		this.toOrderNo = toOrderNo;
	}
	public String getTotalPoAmt() {
		return totalPoAmt;
	}
	public void setTotalPoAmt(String totalPoAmt) {
		this.totalPoAmt = totalPoAmt;
	}
	public String getVerticalDetails() {
		return verticalDetails;
	}
	public void setVerticalDetails(String verticalDetails) {
		this.verticalDetails = verticalDetails;
	}
	public String getWarrantyEndDate() {
		return warrantyEndDate;
	}
	public void setWarrantyEndDate(String warrantyEndDate) {
		this.warrantyEndDate = warrantyEndDate;
	}
	public String getWarrantyStartDate() {
		return warrantyStartDate;
	}
	public void setWarrantyStartDate(String warrantyStartDate) {
		this.warrantyStartDate = warrantyStartDate;
	}
	public int getIsUsage() {
		return isUsage;
	}
	public void setIsUsage(int isUsage) {
		this.isUsage = isUsage;
	}
	public Long getBillingLevelNo() {
		return billingLevelNo;
	}
	public void setBillingLevelNo(Long billingLevelNo) {
		this.billingLevelNo = billingLevelNo;
	}
	public String getOrderApproveDate() {
		return orderApproveDate;
	}
	public void setOrderApproveDate(String orderApproveDate) {
		this.orderApproveDate = orderApproveDate;
	}
	public long getPonum() {
		return ponum;
	}
	public void setPonum(long ponum) {
		this.ponum = ponum;
	}
	public String getCancelServiceReason() {
		return cancelServiceReason;
	}
	public void setCancelServiceReason(String cancelServiceReason) {
		this.cancelServiceReason = cancelServiceReason;
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
	public int getM6OrderNo() {
		return m6OrderNo;
	}
	public void setM6OrderNo(int orderNo) {
		m6OrderNo = orderNo;
	}
	public String getOpms_Account_Id() {
		return opms_Account_Id;
	}
	public void setOpms_Account_Id(String opms_Account_Id) {
		this.opms_Account_Id = opms_Account_Id;
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
	public String getServiceOrderType() {
		return serviceOrderType;
	}
	public void setServiceOrderType(String serviceOrderType) {
		this.serviceOrderType = serviceOrderType;
	}
	public String getAnnualRate() {
		return annualRate;
	}
	public void setAnnualRate(String annualRate) {
		this.annualRate = annualRate;
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
	public ComponentsDto getComponentDto() {
		return componentDto;
	}
	public void setComponentDto(ComponentsDto componentDto) {
		this.componentDto = componentDto;
	}
	public long getFx_internal_acc_id() {
		return fx_internal_acc_id;
	}
	public void setFx_internal_acc_id(long fx_internal_acc_id) {
		this.fx_internal_acc_id = fx_internal_acc_id;
	}
	
	
	

}
