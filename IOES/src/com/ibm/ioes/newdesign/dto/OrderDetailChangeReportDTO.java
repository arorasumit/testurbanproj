package com.ibm.ioes.newdesign.dto;

import com.ibm.ioes.utilities.PagingSorting;

public class OrderDetailChangeReportDTO {
	PagingSorting pagingSorting = new PagingSorting();
	private int fromAccountNo;
	private  int toAccountNo;
	private  int fromOrderNo;
	private String  BillingBandwidth;
	private int toOrderNo;
	private String companyName;
	private String fromOrderDate;
	private  String toOrderDate;
	private String OrderTotalAmount;
	private String ServiceSubTypeName;
	private String CancelFlag;
	private String toDate;
	 private String fromDate;
	private int accountID;
	private String accountManager;
	private String accountno;
	private String serviceDetDescription;
	private String entity;
	private int serviceproductid = 0;
	private int contractPeriod;
	private String orderDate;
	private int orderNumber;
	private String currencyCode;
	private String custPoDate;
	private String distance;
	private String lineItemAmount;
	private String chargeAnnotation;
	private String LOC_Date;
	private String copcApproveDate;
	private String rfs_date;
	private String rfsDate;
	private String custPoDetailNo;
	private String poAmount;
	private String amApproveDate;
	private String logicalSINo;
	private String startDate;
	private String endDate;
	private String orderType;
	private String frequencyName;
	private String projectManager;
	private String regionName;
	private String ratio;
	private String verticalDetails;
	private String osp;
	private String custSegmentCode;
	private String accountCategory;
	private String chargeStartDate;
	private String cancelflag;
	private String lineItemQuantity;
	private String billingBandWidth;
	private String billingBandwidthUOM;
	private double orderTotalAmount;
	private String copcEndDate;
	public String getAccountCategory() {
		return accountCategory;
	}
	public void setAccountCategory(String accountCategory) {
		this.accountCategory = accountCategory;
	}
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
	public String getAccountno() {
		return accountno;
	}
	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}
	public String getAmApproveDate() {
		return amApproveDate;
	}
	public void setAmApproveDate(String amApproveDate) {
		this.amApproveDate = amApproveDate;
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
	public String getCancelflag() {
		return cancelflag;
	}
	public void setCancelflag(String cancelflag) {
		this.cancelflag = cancelflag;
	}
	public String getChargeAnnotation() {
		return chargeAnnotation;
	}
	public void setChargeAnnotation(String chargeAnnotation) {
		this.chargeAnnotation = chargeAnnotation;
	}
	public String getChargeStartDate() {
		return chargeStartDate;
	}
	public void setChargeStartDate(String chargeStartDate) {
		this.chargeStartDate = chargeStartDate;
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
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
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
	public String getFrequencyName() {
		return frequencyName;
	}
	public void setFrequencyName(String frequencyName) {
		this.frequencyName = frequencyName;
	}
	public String getLineItemAmount() {
		return lineItemAmount;
	}
	public void setLineItemAmount(String lineItemAmount) {
		this.lineItemAmount = lineItemAmount;
	}
	public String getLineItemQuantity() {
		return lineItemQuantity;
	}
	public void setLineItemQuantity(String lineItemQuantity) {
		this.lineItemQuantity = lineItemQuantity;
	}
	public String getLOC_Date() {
		return LOC_Date;
	}
	public void setLOC_Date(String date) {
		LOC_Date = date;
	}
	public String getLogicalSINo() {
		return logicalSINo;
	}
	public void setLogicalSINo(String logicalSINo) {
		this.logicalSINo = logicalSINo;
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
	public double getOrderTotalAmount() {
		return orderTotalAmount;
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
	public String getPoAmount() {
		return poAmount;
	}
	public void setPoAmount(String poAmount) {
		this.poAmount = poAmount;
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
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getRfs_date() {
		return rfs_date;
	}
	public void setRfs_date(String rfs_date) {
		this.rfs_date = rfs_date;
	}
	public String getRfsDate() {
		return rfsDate;
	}
	public void setRfsDate(String rfsDate) {
		this.rfsDate = rfsDate;
	}
	public String getServiceDetDescription() {
		return serviceDetDescription;
	}
	public void setServiceDetDescription(String serviceDetDescription) {
		this.serviceDetDescription = serviceDetDescription;
	}
	public int getServiceproductid() {
		return serviceproductid;
	}
	public void setServiceproductid(int serviceproductid) {
		this.serviceproductid = serviceproductid;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getVerticalDetails() {
		return verticalDetails;
	}
	public void setVerticalDetails(String verticalDetails) {
		this.verticalDetails = verticalDetails;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}
	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public int getFromAccountNo() {
		return fromAccountNo;
	}
	public void setFromAccountNo(int fromAccountNo) {
		this.fromAccountNo = fromAccountNo;
	}
	public String getFromOrderDate() {
		return fromOrderDate;
	}
	public void setFromOrderDate(String fromOrderDate) {
		this.fromOrderDate = fromOrderDate;
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
	public String getToOrderDate() {
		return toOrderDate;
	}
	public void setToOrderDate(String toOrderDate) {
		this.toOrderDate = toOrderDate;
	}
	public int getToOrderNo() {
		return toOrderNo;
	}
	public void setToOrderNo(int toOrderNo) {
		this.toOrderNo = toOrderNo;
	}
	public void setOrderTotalAmount(double orderTotalAmount) {
		this.orderTotalAmount = orderTotalAmount;
	}
	public void setOrderTotalAmount(String orderTotalAmount) {
		OrderTotalAmount = orderTotalAmount;
	}
	public String getServiceSubTypeName() {
		return ServiceSubTypeName;
	}
	public void setServiceSubTypeName(String serviceSubTypeName) {
		ServiceSubTypeName = serviceSubTypeName;
	}
	public String getCancelFlag() {
		return CancelFlag;
	}
	public void setCancelFlag(String cancelFlag) {
		CancelFlag = cancelFlag;
	}
	public String getBillingBandwidth() {
		return BillingBandwidth;
	}
	public void setBillingBandwidth(String billingBandwidth) {
		BillingBandwidth = billingBandwidth;
	}
	public String getCopcEndDate() {
		return copcEndDate;
	}
	public void setCopcEndDate(String copcEndDate) {
		this.copcEndDate = copcEndDate;
	}
	
	

	
}
