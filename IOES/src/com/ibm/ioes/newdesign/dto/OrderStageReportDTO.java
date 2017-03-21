package com.ibm.ioes.newdesign.dto;
//[001] VIPIN SAHARIA 24/02/2014 BFR-01 Addition of 14 new columns CBR : 20140424-00-020010
//[002]   Neha Maheshwari 10-Jun-2015 20141219-R2-020936 Billing Efficiency Program Reports
//[003]	Priya Gupta		  22-Jan-2015 201508-R2-021322 SalesForce Opportunity Number in IB2B
//[004]  Gunjan Singla  6-May-2016  20160418-R1-022266    Added columns
import com.ibm.ioes.utilities.PagingSorting;

public class OrderStageReportDTO {
	PagingSorting pagingSorting = new PagingSorting();
	private int orderNumber;
     private String toDate;
     private String accountName;
	private String fromDate;
	private int serviceId;
	private String partyName;
	private String accountManager;
	private String orderDate;
	private String amApproveDate;
	private String amName;
	private String projectManager;
	private String pmApproveDate;
	private String pmName;
	private String copcApproveDate;
	private String copcName;
	private String orderType;
	private String serviceDetDescription;
	private int m6OrderNumber;
	private String m6OrderDate;
	private String orderProvision;
	private String billingStatus;
	private String publishedDate;
	private String effStartDate;
	private String orderStatus;
	private String serviceStage;
	private int partyNo;
	private String circuitStatus;
	private String regionName;
	private String zoneName;
	private String standardReason;
	private String verticalDetails;
	private String parentOrderSubType;
	private int custLogicalSI;
	private String osp;
	private String poReceiveDate;
	private String poDate;
	//[001] START
	private String custPoNumber;
	private int poDetailNumber;
	private String poContractCnd;
	private String productName;
	private String customerSegment;
	private String serviceStatus;
	private String media;
	private String onnetOffnet;
	private String projectCategory;
	private String pmRemarks;
	private String servSubTypeName;
	private String orderStage;
	private String totalChargeAmount;
	private String demoType;
	//[001] END
	//[002] Start
	private String ldClause;
	//[002] End
	//[003] Start
	private String salesForceOpportunityNumber;
	private String channelPartner;
	private String partnerId;
	//[003] end
	//[004] start
	private String fieldEngineer;
	private String partnerCode;
	//[004] end
	
	public String getPoReceiveDate() {
		return poReceiveDate;
	}
	public void setPoReceiveDate(String poReceiveDate) {
		this.poReceiveDate = poReceiveDate;
	}
	public String getPoDate() {
		return poDate;
	}
	public void setPoDate(String poDate) {
		this.poDate = poDate;
	}
	public String getAccountManager() {
		return accountManager;
	}
	public void setAccountManager(String accountManager) {
		this.accountManager = accountManager;
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
	public String getOsp() {
		return osp;
	}
	public void setOsp(String osp) {
		this.osp = osp;
	}
	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}
	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
	}
	public String getParentOrderSubType() {
		return parentOrderSubType;
	}
	public void setParentOrderSubType(String parentOrderSubType) {
		this.parentOrderSubType = parentOrderSubType;
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
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getCustPoNumber() {
		return custPoNumber;
	}
	public void setCustPoNumber(String custPoNumber) {
		this.custPoNumber = custPoNumber;
	}
	public int getPoDetailNumber() {
		return poDetailNumber;
	}
	public void setPoDetailNumber(int poNumber) {
		this.poDetailNumber = poNumber;
	}
	public String getPoContractCnd() {
		return poContractCnd;
	}
	public void setPoContractCnd(String poContractCnd) {
		this.poContractCnd = poContractCnd;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getCustomerSegment() {
		return customerSegment;
	}
	public void setCustomerSegment(String customerSegment) {
		this.customerSegment = customerSegment;
	}
	public String getServiceStatus() {
		return serviceStatus;
	}
	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
	public String getMedia() {
		return media;
	}
	public void setMedia(String media) {
		this.media = media;
	}
	public String getOnnetOffnet() {
		return onnetOffnet;
	}
	public void setOnnetOffnet(String onnetOffnet) {
		this.onnetOffnet = onnetOffnet;
	}
	public String getProjectCategory() {
		return projectCategory;
	}
	public void setProjectCategory(String projectCategory) {
		this.projectCategory = projectCategory;
	}
	public String getPmRemarks() {
		return pmRemarks;
	}
	public void setPmRemarks(String pmRemarks) {
		this.pmRemarks = pmRemarks;
	}
	public String getServSubTypeName() {
		return servSubTypeName;
	}
	public void setServSubTypeName(String servSubTypeName) {
		this.servSubTypeName = servSubTypeName;
	}
	public String getOrderStage() {
		return orderStage;
	}
	public void setOrderStage(String orderStage) {
		this.orderStage = orderStage;
	}
	public String getTotalChargeAmount() {
		return totalChargeAmount;
	}
	public void setTotalChargeAmount(String totalChargeAmount) {
		this.totalChargeAmount = totalChargeAmount;
	}
	public String getDemoType() {
		return demoType;
	}
	public void setDemoType(String demoType) {
		this.demoType = demoType;
	}
	//[002] Start
	public String getLdClause() {
		return ldClause;
	}
	public void setLdClause(String ldClause) {
		this.ldClause = ldClause;
	}
	//[002] End
	//[003] Start
	public String getSalesForceOpportunityNumber() {
		return salesForceOpportunityNumber;
	}
	public void setSalesForceOpportunityNumber(String salesForceOpportunityNumber) {
		this.salesForceOpportunityNumber = salesForceOpportunityNumber;
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
	// [003] end

	
	//[004] start
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
	//[004] end

}
