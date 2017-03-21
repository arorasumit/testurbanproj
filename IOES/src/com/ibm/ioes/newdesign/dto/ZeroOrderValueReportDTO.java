// [001] Shourya Mishra 12-Dec-13 New Field are added 
package com.ibm.ioes.newdesign.dto;

import com.ibm.ioes.utilities.PagingSorting;

public class ZeroOrderValueReportDTO {


	PagingSorting pagingSorting = new PagingSorting();
	private String partyName;
	private int orderNumber;
	private String poDetailNo;
	private int accountID;
	private String entityId;
	private String custPoDetailNo;
	private String custPoDate;
	private double poAmounts;
	private String paymentTerm;
	private String lastUpdatedDate;
	private String lastUpdatedBy;
	private String poEndDate;
	private String toLocation;
	private String lineItemDescription;
	private String fxSiId;
	private String contractStartDate;
	private String contractEndDate;
	private String billingBandWidth;
	private String uom;
	
	private int contractPeriod;
	private String poRecieveDate;
	private String poIssueBy;
	private String poEmailId;
	private String createdDate;
	private String createdBy;
	private int crmAccountNo;
	private int isDefaultPO;
	private int partyNo;
	private String sourceName;
	private String poDemoContractPeriod;
	private String logicalSINo;
	private int serviceId;
	private String accountManager;
	private String fromLocation;
	private String tokenno;
	private String regionName;
	private int m6OrderNo;
	private String chargeAnnotation;
	private String order_type;
	private String orderType;
	private String toDate;
	private String fromDate;
	private int fromAccountNo=0;
	private int toAccountNo=0;
	private int fromOrderNo=0; 
	private int toOrderNo=0;
	private String crmACcountNO;
	private String tokenNO;
	
	 //[001] Start 
	private String oldLsi;
	private int business_serial_n;
	private String mocn_no;
//	[001] End 
	
	
	
	public String getCrmACcountNO() {
		return crmACcountNO;
	}
	public void setCrmACcountNO(String crmACcountNO) {
		this.crmACcountNO = crmACcountNO;
	}
	public String getTokenNO() {
		return tokenNO;
	}
	public void setTokenNO(String tokenNO) {
		this.tokenNO = tokenNO;
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
	public String getBillingBandWidth() {
		return billingBandWidth;
	}
	public void setBillingBandWidth(String billingBandWidth) {
		this.billingBandWidth = billingBandWidth;
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
	public int getCrmAccountNo() {
		return crmAccountNo;
	}
	public void setCrmAccountNo(int crmAccountNo) {
		this.crmAccountNo = crmAccountNo;
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
	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
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
	public String getLineItemDescription() {
		return lineItemDescription;
	}
	public void setLineItemDescription(String lineItemDescription) {
		this.lineItemDescription = lineItemDescription;
	}
	public String getLogicalSINo() {
		return logicalSINo;
	}
	public void setLogicalSINo(String logicalSINo) {
		this.logicalSINo = logicalSINo;
	}
	public int getM6OrderNo() {
		return m6OrderNo;
	}
	public void setM6OrderNo(int orderNo) {
		m6OrderNo = orderNo;
	}
	public String getOrder_type() {
		return order_type;
	}
	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}
	public int getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
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
	public double getPoAmounts() {
		return poAmounts;
	}
	public void setPoAmounts(double poAmounts) {
		this.poAmounts = poAmounts;
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
	public String getPoEndDate() {
		return poEndDate;
	}
	public void setPoEndDate(String poEndDate) {
		this.poEndDate = poEndDate;
	}
	public String getPoIssueBy() {
		return poIssueBy;
	}
	public void setPoIssueBy(String poIssueBy) {
		this.poIssueBy = poIssueBy;
	}
	public String getPoRecieveDate() {
		return poRecieveDate;
	}
	public void setPoRecieveDate(String poRecieveDate) {
		this.poRecieveDate = poRecieveDate;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public int getServiceId() {
		return serviceId;
	}
	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}
	public String getSourceName() {
		return sourceName;
	}
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
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
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
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
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
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
	public String getOldLsi() {
		return oldLsi;
	}
	public void setOldLsi(String oldLsi) {
		this.oldLsi = oldLsi;
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
}
