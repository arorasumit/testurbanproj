// [001] Shourya Mishra	05-Nov-13 New Field are added 
package com.ibm.ioes.newdesign.dto;

import com.ibm.ioes.forms.ComponentsDto;
import com.ibm.ioes.utilities.PagingSorting;

public class RateRenewalReportDTO {
	PagingSorting pagingSorting = new PagingSorting();
	private String toDate;
	 private String fromDate;
	 private int isUsage;
	 private double poAmounts;
	 private int logicalSINumber;
	 private double orderTotal;
	 private String fx_external_acc_id;
	 private int fxInternalId;
	 private String child_account_creation_status;
	 private int packageID;
	 private String packageName;
	 private int componentID;
	 private int componentInfoID;
	 private String componentName;
	
	 private double chargeAmount;
	 private ComponentsDto componentDto;
	 private String custSINo;
	 private String toCopcApprovedDate; 
	 private String fromCopcApprovedDate; 
	 private int toOrderNo;
	private int fromOrderNo;
	private int toAccountNo=0;
	private int fromAccountNo=0;
	private String demo;
	private int partyNo;
	private String partyName;
	private String CrmACcountNO;
	private int ServiceProductID;
	private int crmAccountNo;
	private String accountManager;
	private String cus_segment;
	private String verticalDetails;
	private String regionName;
	private String serviceDetDescription;
	private String billing_bandwidth;
	private String billing_uom;
	private String changeTypeName;
	private String orderType;
	private String companyName;
	private String createdDate;
	private String currencyCode;
	private String poDate;
	private String custPoDetailNo;
	private String chargeName;
	private String chargeTypeName;
	private String fromLocation;
	private String distance;
	private String serviceSegment;
	private String toLocation;
	private String lineItemDescription;
	private String logicalCircuitNumber;
	private String LOC_Date;
	private String copcApproveDate;
	private String logicalCircuitId;
	private String paymentTerm;
	private String ratio;
	private String taxationName;
	private String lcompanyname;
	private String order_type;
	private String serviceStage;
	private String stageName;
	private String newOrderRemark;
	private String remarks;
	private String productName;
	private String subProductName;
	private String billing_trigger_date;
	private String billingTriggerFlag;
	private String linename;
	private String startDate;
	private String endDate;
	private String endHWDateLogic;
	private String charge_status;
	private String startDaysLogic;
	private String startMonthsLogic;
	private String zoneName;
	private String salesCoordinator;
	private String poAmount;
	private int contractPeriod;
	private int chargePeriod;
	private int itemQuantity;
	private String totalPoAmt;
	private int serviceId;
	private int m6OrderNo;
	private String logicalSINo;
	private String m6cktid;
	private double annual_rate;
	private int serviceproductid = 0;
	private String pk_charge_id;
	private String orderNo;
	private int oldOrderNo;
	private String oldLineitemAmount;
	private int	txtStartMonth;
	//[001] Start 
	private String mocn_no;
	//[001] End
	
	private String chargeAmount_String;
	private int txtStartDays;
	private String standardReason;
	
	private int customerSegmentId;
	private String isDifferential;
	private long oldPkChargeId;
	private String copcApproverName;
	private String effectiveDate;
	private String billingTriggerCreateDate;
	private String isTriggerRequired;
	private String lineTriggered;
	private String triggerProcess;
	private String triggerDoneBy;
	private String automaticTriggerError;
	
	
	public String getStandardReason() {
		return standardReason;
	}
	public void setStandardReason(String standardReason) {
		this.standardReason = standardReason;
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
	public String getBilling_bandwidth() {
		return billing_bandwidth;
	}
	public void setBilling_bandwidth(String billing_bandwidth) {
		this.billing_bandwidth = billing_bandwidth;
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
	public String getBillingTriggerFlag() {
		return billingTriggerFlag;
	}
	public void setBillingTriggerFlag(String billingTriggerFlag) {
		this.billingTriggerFlag = billingTriggerFlag;
	}
	public String getChangeTypeName() {
		return changeTypeName;
	}
	public void setChangeTypeName(String changeTypeName) {
		this.changeTypeName = changeTypeName;
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
	public String getChargeTypeName() {
		return chargeTypeName;
	}
	public void setChargeTypeName(String chargeTypeName) {
		this.chargeTypeName = chargeTypeName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public int getContractPeriod() {
		return contractPeriod;
	}
	public void setContractPeriod(int contractPeriod) {
		this.contractPeriod = contractPeriod;
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
	public int getCrmAccountNo() {
		return crmAccountNo;
	}
	public void setCrmAccountNo(int crmAccountNo) {
		this.crmAccountNo = crmAccountNo;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getCus_segment() {
		return cus_segment;
	}
	public void setCus_segment(String cus_segment) {
		this.cus_segment = cus_segment;
	}
	public String getCustPoDetailNo() {
		return custPoDetailNo;
	}
	public void setCustPoDetailNo(String custPoDetailNo) {
		this.custPoDetailNo = custPoDetailNo;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getEndHWDateLogic() {
		return endHWDateLogic;
	}
	public void setEndHWDateLogic(String endHWDateLogic) {
		this.endHWDateLogic = endHWDateLogic;
	}
	public String getFromLocation() {
		return fromLocation;
	}
	public void setFromLocation(String fromLocation) {
		this.fromLocation = fromLocation;
	}
	public int getItemQuantity() {
		return itemQuantity;
	}
	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}
	public String getLcompanyname() {
		return lcompanyname;
	}
	public void setLcompanyname(String lcompanyname) {
		this.lcompanyname = lcompanyname;
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
	public String getLOC_Date() {
		return LOC_Date;
	}
	public void setLOC_Date(String date) {
		LOC_Date = date;
	}
	public String getLogicalCircuitId() {
		return logicalCircuitId;
	}
	public void setLogicalCircuitId(String logicalCircuitId) {
		this.logicalCircuitId = logicalCircuitId;
	}
	public String getLogicalCircuitNumber() {
		return logicalCircuitNumber;
	}
	public void setLogicalCircuitNumber(String logicalCircuitNumber) {
		this.logicalCircuitNumber = logicalCircuitNumber;
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
	public int getOldOrderNo() {
		return oldOrderNo;
	}
	public void setOldOrderNo(int oldOrderNo) {
		this.oldOrderNo = oldOrderNo;
	}
	public String getOrder_type() {
		return order_type;
	}
	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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
	public String getPaymentTerm() {
		return paymentTerm;
	}
	public void setPaymentTerm(String paymentTerm) {
		this.paymentTerm = paymentTerm;
	}
	public String getPk_charge_id() {
		return pk_charge_id;
	}
	public void setPk_charge_id(String pk_charge_id) {
		this.pk_charge_id = pk_charge_id;
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
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getRatio() {
		return ratio;
	}
	public void setRatio(String ratio) {
		this.ratio = ratio;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getSalesCoordinator() {
		return salesCoordinator;
	}
	public void setSalesCoordinator(String salesCoordinator) {
		this.salesCoordinator = salesCoordinator;
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
	public int getServiceproductid() {
		return serviceproductid;
	}
	public void setServiceproductid(int serviceproductid) {
		this.serviceproductid = serviceproductid;
	}
	public String getServiceSegment() {
		return serviceSegment;
	}
	public void setServiceSegment(String serviceSegment) {
		this.serviceSegment = serviceSegment;
	}
	public String getServiceStage() {
		return serviceStage;
	}
	public void setServiceStage(String serviceStage) {
		this.serviceStage = serviceStage;
	}
	public String getStageName() {
		return stageName;
	}
	public void setStageName(String stageName) {
		this.stageName = stageName;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
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
	public String getDemo() {
		return demo;
	}
	public void setDemo(String demo) {
		this.demo = demo;
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
	public String getCrmACcountNO() {
		return CrmACcountNO;
	}
	public void setCrmACcountNO(String crmACcountNO) {
		CrmACcountNO = crmACcountNO;
	}
	public int getServiceProductID() {
		return ServiceProductID;
	}
	public void setServiceProductID(int serviceProductID) {
		ServiceProductID = serviceProductID;
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
	public String getCustSINo() {
		return custSINo;
	}
	public void setCustSINo(String custSINo) {
		this.custSINo = custSINo;
	}
	public double getChargeAmount() {
		return chargeAmount;
	}
	public void setChargeAmount(double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}
	public double getOrderTotal() {
		return orderTotal;
	}
	public void setOrderTotal(double orderTotal) {
		this.orderTotal = orderTotal;
	}
	public ComponentsDto getComponentDto() {
		return componentDto;
	}
	public void setComponentDto(ComponentsDto componentDto) {
		this.componentDto = componentDto;
	}
	public String getChild_account_creation_status() {
		return child_account_creation_status;
	}
	public void setChild_account_creation_status(
			String child_account_creation_status) {
		this.child_account_creation_status = child_account_creation_status;
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
	public int getLogicalSINumber() {
		return logicalSINumber;
	}
	public void setLogicalSINumber(int logicalSINumber) {
		this.logicalSINumber = logicalSINumber;
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
	public String getFx_external_acc_id() {
		return fx_external_acc_id;
	}
	public void setFx_external_acc_id(String fx_external_acc_id) {
		this.fx_external_acc_id = fx_external_acc_id;
	}
	public double getPoAmounts() {
		return poAmounts;
	}
	public void setPoAmounts(double poAmounts) {
		this.poAmounts = poAmounts;
	}
	public String getMocn_no() {
		return mocn_no;
	}
	public void setMocn_no(String mocn_no) {
		this.mocn_no = mocn_no;
	}
	public int getCustomerSegmentId() {
		return customerSegmentId;
	}
	public void setCustomerSegmentId(int customerSegmentId) {
		this.customerSegmentId = customerSegmentId;
	}
	public String getIsDifferential() {
		return isDifferential;
	}
	public void setIsDifferential(String isDifferential) {
		this.isDifferential = isDifferential;
	}
	public long getOldPkChargeId() {
		return oldPkChargeId;
	}
	public void setOldPkChargeId(long oldPkChargeId) {
		this.oldPkChargeId = oldPkChargeId;
	}
	public String getCopcApproverName() {
		return copcApproverName;
	}
	public void setCopcApproverName(String copcApproverName) {
		this.copcApproverName = copcApproverName;
	}
	public String getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public String getBillingTriggerCreateDate() {
		return billingTriggerCreateDate;
	}
	public void setBillingTriggerCreateDate(String billingTriggerCreateDate) {
		this.billingTriggerCreateDate = billingTriggerCreateDate;
	}
	
	public String getIsTriggerRequired() {
		return isTriggerRequired;
	}
	public void setIsTriggerRequired(String isTriggerRequired) {
		this.isTriggerRequired = isTriggerRequired;
	}
	public String getLineTriggered() {
		return lineTriggered;
	}
	public void setLineTriggered(String lineTriggered) {
		this.lineTriggered = lineTriggered;
	}
	public String getTriggerProcess() {
		return triggerProcess;
	}
	public void setTriggerProcess(String triggerProcess) {
		this.triggerProcess = triggerProcess;
	}
	public String getTriggerDoneBy() {
		return triggerDoneBy;
	}
	public void setTriggerDoneBy(String triggerDoneBy) {
		this.triggerDoneBy = triggerDoneBy;
	}
	public String getAutomaticTriggerError() {
		return automaticTriggerError;
	}
	public void setAutomaticTriggerError(String automaticTriggerError) {
		this.automaticTriggerError = automaticTriggerError;
	}
}
