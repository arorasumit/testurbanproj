//Santosh Srivastava added a separate class for ActiveLineDemoReportDTO
package com.ibm.ioes.newdesign.dto;

import com.ibm.ioes.utilities.PagingSorting;

public class ActiveLineDemoReportDTO {

	private String partyName;
	private String partyNo;
	private long crmAccNo;
	private String customerSegment;
	private String industrySegment;
	private String regionName;
	private String zoneName;
	private String acctMgrName;
	private String prjMGRName;
	private String demoOrder;
	private long noOfDays;
	private String orderType;
	private String changeTypeName;
	private String subChangeType;
	private String orderDate;
	private long serviceId;
	private long orderNo;
	private String logicalSINo;
	private String custLogicalSINo;
	private String cktId;
	private String annotation;
	private long m6OrderNo;
	private String locNo;
	private String locDate;
	private String fromAddress;
	private String toAddress;
	private String billingBandWidth;
	private String billingBandwidthUOM;
	private String createDate;
	private String orderApprovalDate;
	private String publishedDate;
	private String serviceClosureDate;
	private String billingTriggerCreateDate;
	private String billingTriggerDate;
	private String chargeCurrentStartDate;
	private String chargeCurrentEndDate;
	private String mstChargeName;
	private String productName;
	private String subTypeName;
	private String stage;
	private String serviceTypeName;
	private String copcApprovalRemark;
	private String orderEntryRemark;
	private String pMRemark;
	private long totalAmount;
	private String curName;
	private long annualRate;
	private String published;
	private String serviceStage;
	private String fromOrderDate;
	private String toOrderDate;
	private long fromOrderNo;
	private long toOrderNo;
	private int lsiDemoType;
	private PagingSorting pagingSorting = new PagingSorting();
	
	
	public String getPartyName() {
		return partyName;
	}
	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}
	public String getPartyNo() {
		return partyNo;
	}
	public void setPartyNo(String partyNo) {
		this.partyNo = partyNo;
	}
	public long getCrmAccNo() {
		return crmAccNo;
	}
	public void setCrmAccNo(long crmAccNo) {
		this.crmAccNo = crmAccNo;
	}
	public String getCustomerSegment() {
		return customerSegment;
	}
	public void setCustomerSegment(String customerSegment) {
		this.customerSegment = customerSegment;
	}
	public String getIndustrySegment() {
		return industrySegment;
	}
	public void setIndustrySegment(String industrySegment) {
		this.industrySegment = industrySegment;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getZoneName() {
		return zoneName;
	}
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	public String getAcctMgrName() {
		return acctMgrName;
	}
	public void setAcctMgrName(String acctMgrName) {
		this.acctMgrName = acctMgrName;
	}
	public String getPrjMGRName() {
		return prjMGRName;
	}
	public void setPrjMGRName(String prjMGRName) {
		this.prjMGRName = prjMGRName;
	}
	public String getDemoOrder() {
		return demoOrder;
	}
	public void setDemoOrder(String demoOrder) {
		this.demoOrder = demoOrder;
	}
	
	public long getNoOfDays() {
		return noOfDays;
	}
	public void setNoOfDays(long noOfDays) {
		this.noOfDays = noOfDays;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getChangeTypeName() {
		return changeTypeName;
	}
	public void setChangeTypeName(String changeTypeName) {
		this.changeTypeName = changeTypeName;
	}
	public String getSubChangeType() {
		return subChangeType;
	}
	public void setSubChangeType(String subChangeType) {
		this.subChangeType = subChangeType;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public long getServiceId() {
		return serviceId;
	}
	public void setServiceId(long serviceId) {
		this.serviceId = serviceId;
	}
	
	public long getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(long orderNo) {
		this.orderNo = orderNo;
	}
	public String getLogicalSINo() {
		return logicalSINo;
	}
	public void setLogicalSINo(String logicalSINo) {
		this.logicalSINo = logicalSINo;
	}
	public String getCustLogicalSINo() {
		return custLogicalSINo;
	}
	public void setCustLogicalSINo(String custLogicalSINo) {
		this.custLogicalSINo = custLogicalSINo;
	}
	public String getCktId() {
		return cktId;
	}
	public void setCktId(String cktId) {
		this.cktId = cktId;
	}
	public String getAnnotation() {
		return annotation;
	}
	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}
	public long getM6OrderNo() {
		return m6OrderNo;
	}
	public void setM6OrderNo(long m6OrderNo) {
		this.m6OrderNo = m6OrderNo;
	}
	public String getLocNo() {
		return locNo;
	}
	public void setLocNo(String locNo) {
		this.locNo = locNo;
	}
	public String getLocDate() {
		return locDate;
	}
	public void setLocDate(String locDate) {
		this.locDate = locDate;
	}
	public String getFromAddress() {
		return fromAddress;
	}
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}
	public String getToAddress() {
		return toAddress;
	}
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}
	public String getBillingBandWidth() {
		return billingBandWidth;
	}
	public void setBillingBandWidth(String billingBandWidth) {
		this.billingBandWidth = billingBandWidth;
	}
	public String getBillingBandwidthUOM() {
		return billingBandwidthUOM;
	}
	public void setBillingBandwidthUOM(String billingBandwidthUOM) {
		this.billingBandwidthUOM = billingBandwidthUOM;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getOrderApprovalDate() {
		return orderApprovalDate;
	}
	public void setOrderApprovalDate(String orderApprovalDate) {
		this.orderApprovalDate = orderApprovalDate;
	}
	public String getPublishedDate() {
		return publishedDate;
	}
	public void setPublishedDate(String publishedDate) {
		this.publishedDate = publishedDate;
	}
	public String getServiceClosureDate() {
		return serviceClosureDate;
	}
	public void setServiceClosureDate(String serviceClosureDate) {
		this.serviceClosureDate = serviceClosureDate;
	}
	public String getBillingTriggerCreateDate() {
		return billingTriggerCreateDate;
	}
	public void setBillingTriggerCreateDate(String billingTriggerCreateDate) {
		this.billingTriggerCreateDate = billingTriggerCreateDate;
	}
	public String getBillingTriggerDate() {
		return billingTriggerDate;
	}
	public void setBillingTriggerDate(String billingTriggerDate) {
		this.billingTriggerDate = billingTriggerDate;
	}
	public String getChargeCurrentStartDate() {
		return chargeCurrentStartDate;
	}
	public void setChargeCurrentStartDate(String chargeCurrentStartDate) {
		this.chargeCurrentStartDate = chargeCurrentStartDate;
	}
	public String getChargeCurrentEndDate() {
		return chargeCurrentEndDate;
	}
	public void setChargeCurrentEndDate(String chargeCurrentEndDate) {
		this.chargeCurrentEndDate = chargeCurrentEndDate;
	}
	public String getMstChargeName() {
		return mstChargeName;
	}
	public void setMstChargeName(String mstChargeName) {
		this.mstChargeName = mstChargeName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getSubTypeName() {
		return subTypeName;
	}
	public void setSubTypeName(String subTypeName) {
		this.subTypeName = subTypeName;
	}
	
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	
	public String getServiceTypeName() {
		return serviceTypeName;
	}
	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
	}
	public String getCopcApprovalRemark() {
		return copcApprovalRemark;
	}
	public void setCopcApprovalRemark(String copcApprovalRemark) {
		this.copcApprovalRemark = copcApprovalRemark;
	}
	public String getOrderEntryRemark() {
		return orderEntryRemark;
	}
	public void setOrderEntryRemark(String orderEntryRemark) {
		this.orderEntryRemark = orderEntryRemark;
	}
	
	public String getpMRemark() {
		return pMRemark;
	}
	public void setpMRemark(String pMRemark) {
		this.pMRemark = pMRemark;
	}
	public long getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(long totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getCurName() {
		return curName;
	}
	public void setCurName(String curName) {
		this.curName = curName;
	}
	public long getAnnualRate() {
		return annualRate;
	}
	public void setAnnualRate(long annualRate) {
		this.annualRate = annualRate;
	}
	public String getPublished() {
		return published;
	}
	public void setPublished(String published) {
		this.published = published;
	}
	public String getServiceStage() {
		return serviceStage;
	}
	public void setServiceStage(String serviceStage) {
		this.serviceStage = serviceStage;
	}
	public String getFromOrderDate() {
		return fromOrderDate;
	}
	public void setFromOrderDate(String fromOrderDate) {
		this.fromOrderDate = fromOrderDate;
	}
	public String getToOrderDate() {
		return toOrderDate;
	}
	public void setToOrderDate(String toOrderDate) {
		this.toOrderDate = toOrderDate;
	}
	public long getFromOrderNo() {
		return fromOrderNo;
	}
	public void setFromOrderNo(long fromOrderNo) {
		this.fromOrderNo = fromOrderNo;
	}
	public long getToOrderNo() {
		return toOrderNo;
	}
	public void setToOrderNo(long toOrderNo) {
		this.toOrderNo = toOrderNo;
	}
	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}
	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
	}
	public int getLsiDemoType() {
		return lsiDemoType;
	}
	public void setLsiDemoType(int lsiDemoType) {
		this.lsiDemoType = lsiDemoType;
	}
	
	
}
