package com.ibm.ioes.newdesign.dto;

import com.ibm.ioes.forms.ComponentsDto;
import com.ibm.ioes.utilities.PagingSorting;

public class BillingWorkQueueReportDTO {
	private int orderNumber;
	PagingSorting pagingSorting = new PagingSorting();
	private String party_num;
	private String ordersubtype;
	private int serviceId;
	private String chargeAnnotation;
	private String logicalSINo;
	private String endDateLogic;
    private int serviceproductid = 0;
	private int accountID;
	private double chargesSumOfLineitem;
	private double annual_rate;
	private String currencyName;
	private String billPeriod;
	private String toDate;
	private String fromDate;
	private int toOrderNo=0;
	private int fromOrderNo=0;
	private int party_no;
	private String partyName;
	private String orderStage;
	private String hardwareType;
	private String formAvailable;
	private String taxation;
	private String serviceName;
	private String billing_trigger_date;
	private int poNumber;
	private String poDate;
	private String chargeEndDate;
	private int contractPeriod;
	private String totalPoAmt;
	private String chargeTypeName;
	private String chargeName;
	private String startDate;
	private String orderDate;
	private String copcapprovaldate;
	private String custSINo;
	private String linename;
	private String advance;
	private String rate_code;
	private String discount;
	private String installRate;
	private int interestRate;
	private int principalAmount;
	private String saleNature;
	private String billingTypeName;
	private String chargeFrequency;
	private String billingformat;
	private String entity;
	private String saleTypeName;
	private String creditPeriodName;
	private String licCompanyName;
	private String store;
	private String billingLevelName;
	private String hardwaretypeName;
	private Long billingLevelNo;
	private String billingTriggerFlag;
	//private int contractPeriod;
	private long noticePeriod;
	private String billingMode;
	private String penaltyClause;
	private int commitmentPeriod;
	private String dispatchAddress1;
	private String primaryLocation;
	private String seclocation;
	private String warrantyStartDate;
	private String warrantyEndDate;
	private String extSuportEndDate;
	private String contractStartDate;
	private String contractEndDate;
	private String challendate;
	private String locDate;
	private String startDateLogic;
	private String pm_pro_date;
	private String challenno;
	private String dnd_Dispatch_And_Delivered;
	private String dnd_Dispatch_But_Not_Delivered;
	private String child_act_no;
	private String child_ac_fxstatus;
	private String orderType;
	private String custPoDetailNo;
	private String custPoDate;
	private double cust_total_poamt;
	private String LOC_No;
	private String billing_address;
	private String m6cktid;
	private String cancelBy;
	private String canceldate;
	private String cancelReason;
	private String demo;
	private String serviceTypeDescription;
	private int chargeTypeID;
	private String fx_sd_charge_status;
	
	private String fx_charge_status;
	private String fx_Ed_Chg_Status;
	
	private String tokenno;
	private String modifiedDate;
	private String billingtrigger_createdate;
	private String ratio;
	private String productName;
	private String subProductName;
	private String serviceStage;
	private String rfsDate;
	private String charge_status;
	private String address1;
	private String region;
	private String bandwaidth;
	private String vertical;
	private String accountManager;
	private String projectManager;
	private String distance;
	private String indicative_value;
	private String toLocation;
	private String fromLocation;
	private String billing_bandwidth;
	
	private int isUsage;
	private String poExpiryDate;
	private double chargeAmount;
	private int m6OrderNo;
	private ComponentsDto componentDto;
	private int fxInternalId;
	private int componentInfoID;
	private int componentID;
	private String componentName;
	private int packageID;
	private String packageName;
	private String opportunityId;	
	public String getOpportunityId() {
		return opportunityId;
	}
	public void setOpportunityId(String opportunityId) {
		this.opportunityId = opportunityId;
	}
	public double getChargeAmount() {
		return chargeAmount;
	}
	public void setChargeAmount(double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}
	public int getM6OrderNo() {
		return m6OrderNo;
	}
	public void setM6OrderNo(int orderNo) {
		m6OrderNo = orderNo;
	}
	public String getPoExpiryDate() {
		return poExpiryDate;
	}
	public void setPoExpiryDate(String poExpiryDate) {
		this.poExpiryDate = poExpiryDate;
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
	public String getBillingtrigger_createdate() {
		return billingtrigger_createdate;
	}
	public void setBillingtrigger_createdate(String billingtrigger_createdate) {
		this.billingtrigger_createdate = billingtrigger_createdate;
	}
	public String getCharge_status() {
		return charge_status;
	}
	public void setCharge_status(String charge_status) {
		this.charge_status = charge_status;
	}
	
	public int getChargeTypeID() {
		return chargeTypeID;
	}
	public void setChargeTypeID(int chargeTypeID) {
		this.chargeTypeID = chargeTypeID;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
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
	public String getFx_sd_charge_status() {
		return fx_sd_charge_status;
	}
	public void setFx_sd_charge_status(String fx_sd_charge_status) {
		this.fx_sd_charge_status = fx_sd_charge_status;
	}
	public String getIndicative_value() {
		return indicative_value;
	}
	public void setIndicative_value(String indicative_value) {
		this.indicative_value = indicative_value;
	}
	
	public String getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
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
	public String getServiceStage() {
		return serviceStage;
	}
	public void setServiceStage(String serviceStage) {
		this.serviceStage = serviceStage;
	}
	public String getSubProductName() {
		return subProductName;
	}
	public void setSubProductName(String subProductName) {
		this.subProductName = subProductName;
	}
	public String getTokenno() {
		return tokenno;
	}
	public void setTokenno(String tokenno) {
		this.tokenno = tokenno;
	}
	public String getToLocation() {
		return toLocation;
	}
	public void setToLocation(String toLocation) {
		this.toLocation = toLocation;
	}
	public String getVertical() {
		return vertical;
	}
	public void setVertical(String vertical) {
		this.vertical = vertical;
	}
	public int getAccountID() {
		return accountID;
	}
	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}
	public String getAdvance() {
		return advance;
	}
	public void setAdvance(String advance) {
		this.advance = advance;
	}
	public double getAnnual_rate() {
		return annual_rate;
	}
	public void setAnnual_rate(double annual_rate) {
		this.annual_rate = annual_rate;
	}
	public String getBilling_address() {
		return billing_address;
	}
	public void setBilling_address(String billing_address) {
		this.billing_address = billing_address;
	}
	public String getBilling_trigger_date() {
		return billing_trigger_date;
	}
	public void setBilling_trigger_date(String billing_trigger_date) {
		this.billing_trigger_date = billing_trigger_date;
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
	public String getChargeAnnotation() {
		return chargeAnnotation;
	}
	public void setChargeAnnotation(String chargeAnnotation) {
		this.chargeAnnotation = chargeAnnotation;
	}
	public String getChargeEndDate() {
		return chargeEndDate;
	}
	public void setChargeEndDate(String chargeEndDate) {
		this.chargeEndDate = chargeEndDate;
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
	public String getChargeTypeName() {
		return chargeTypeName;
	}
	public void setChargeTypeName(String chargeTypeName) {
		this.chargeTypeName = chargeTypeName;
	}
	public String getChild_ac_fxstatus() {
		return child_ac_fxstatus;
	}
	public void setChild_ac_fxstatus(String child_ac_fxstatus) {
		this.child_ac_fxstatus = child_ac_fxstatus;
	}
	public String getChild_act_no() {
		return child_act_no;
	}
	public void setChild_act_no(String child_act_no) {
		this.child_act_no = child_act_no;
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
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getDispatchAddress1() {
		return dispatchAddress1;
	}
	public void setDispatchAddress1(String dispatchAddress1) {
		this.dispatchAddress1 = dispatchAddress1;
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
	public String getEndDateLogic() {
		return endDateLogic;
	}
	public void setEndDateLogic(String endDateLogic) {
		this.endDateLogic = endDateLogic;
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
	public String getHardwaretypeName() {
		return hardwaretypeName;
	}
	public void setHardwaretypeName(String hardwaretypeName) {
		this.hardwaretypeName = hardwaretypeName;
	}
	public String getInstallRate() {
		return installRate;
	}
	public void setInstallRate(String installRate) {
		this.installRate = installRate;
	}
	public int getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(int interestRate) {
		this.interestRate = interestRate;
	}
	public String getLicCompanyName() {
		return licCompanyName;
	}
	public void setLicCompanyName(String licCompanyName) {
		this.licCompanyName = licCompanyName;
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
	public long getNoticePeriod() {
		return noticePeriod;
	}
	public void setNoticePeriod(long noticePeriod) {
		this.noticePeriod = noticePeriod;
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
	public String getPm_pro_date() {
		return pm_pro_date;
	}
	public void setPm_pro_date(String pm_pro_date) {
		this.pm_pro_date = pm_pro_date;
	}
	public String getPoDate() {
		return poDate;
	}
	public void setPoDate(String poDate) {
		this.poDate = poDate;
	}
	public int getPoNumber() {
		return poNumber;
	}
	public void setPoNumber(int poNumber) {
		this.poNumber = poNumber;
	}
	public String getPrimaryLocation() {
		return primaryLocation;
	}
	public void setPrimaryLocation(String primaryLocation) {
		this.primaryLocation = primaryLocation;
	}
	public int getPrincipalAmount() {
		return principalAmount;
	}
	public void setPrincipalAmount(int principalAmount) {
		this.principalAmount = principalAmount;
	}
	public String getRate_code() {
		return rate_code;
	}
	public void setRate_code(String rate_code) {
		this.rate_code = rate_code;
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
	public String getServiceTypeDescription() {
		return serviceTypeDescription;
	}
	public void setServiceTypeDescription(String serviceTypeDescription) {
		this.serviceTypeDescription = serviceTypeDescription;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getStartDateLogic() {
		return startDateLogic;
	}
	public void setStartDateLogic(String startDateLogic) {
		this.startDateLogic = startDateLogic;
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
	public String getTotalPoAmt() {
		return totalPoAmt;
	}
	public void setTotalPoAmt(String totalPoAmt) {
		this.totalPoAmt = totalPoAmt;
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
	public void setToDate(String toDate) {
		this.toDate = toDate;
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
	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}
	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
	}
	public int getToOrderNo() {
		return toOrderNo;
	}
	public void setToOrderNo(int toOrderNo) {
		this.toOrderNo = toOrderNo;
	}
	public String getToDate() {
		return toDate;
	}
	public String getHardwareType() {
		return hardwareType;
	}
	public void setHardwareType(String hardwareType) {
		this.hardwareType = hardwareType;
	}
	public String getOrderStage() {
		return orderStage;
	}
	public void setOrderStage(String orderStage) {
		this.orderStage = orderStage;
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
	public ComponentsDto getComponentDto() {
		return componentDto;
	}
	public void setComponentDto(ComponentsDto componentDto) {
		this.componentDto = componentDto;
	}
	public int getFxInternalId() {
		return fxInternalId;
	}
	public void setFxInternalId(int fxInternalId) {
		this.fxInternalId = fxInternalId;
	}
	public int getComponentInfoID() {
		return componentInfoID;
	}
	public void setComponentInfoID(int componentInfoID) {
		this.componentInfoID = componentInfoID;
	}
	public int getComponentID() {
		return componentID;
	}
	public void setComponentID(int componentID) {
		this.componentID = componentID;
	}
	public String getComponentName() {
		return componentName;
	}
	public void setComponentName(String componentName) {
		this.componentName = componentName;
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
	public int getIsUsage() {
		return isUsage;
	}
	public void setIsUsage(int isUsage) {
		this.isUsage = isUsage;
	}
	
}
