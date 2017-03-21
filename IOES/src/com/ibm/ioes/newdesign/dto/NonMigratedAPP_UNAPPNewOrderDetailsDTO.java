//[01]   Shourya   04-Dec-2013   Added New Column
package com.ibm.ioes.newdesign.dto;

import com.ibm.ioes.utilities.PagingSorting;

public class NonMigratedAPP_UNAPPNewOrderDetailsDTO {

	
	PagingSorting pagingSorting = new PagingSorting();
	private int party_no;
	private String crmAccountNoString;
	private String partyName;
	private String verticalDetails;
	private String regionName;
	private int orderNumber;
	private String orderType;
	private String m6OrderNo2;	
	private String serviceName;	
	private int customer_logicalSINumber;
	private String serviceDetDescription;
	private String serviceOrderType;
	private String logicalCircuitId;
	private String bandwaidth;
	private String billing_bandwidth;
	private String billUom;
	private String kmsDistance;
	private String ratio;
	private String fromLocation;
	private String toLocation;
	private String primaryLocation;
	private String secondaryLocation;
	private String productName;
	private String subProductName;
	private String entity;
	private String lcompanyname;
	private String currencyName;
	private String creditPeriodName;
	private String billingTypeName;
	private String billingFormatName;
	private String billingLevelName;
	private long billingLevelNo;
	private String taxation;
	private String hardwaretypeName;							
	private String hardwareFlag;
	private String storename;
	private String saleType;
	private String billPeriod;
	private String billingMode;
	private long ponum;
	private String poDate;
	private int contractPeriod;
	private String totalPoAmt;
	private String poRecieveDate;
	private String custPoDetailNo;
	private String custPoDate;
	private String poAmount;
	private String chargeTypeName;
	private String chargeName;
	private String startDate;
	private String chargeAmount_String;
	private String lineItemAmount;
	private String chargeAnnotation;
	private String locDate;
	private String LOC_No;							
	private String billingTriggerDate;
											
	private String pmApproveDate;
	private String billing_Trigger_Flag;
	private String tokenNO;
	private String fx_St_Chg_Status;
	private String fxStatus;
	
	private int orderLineNumber;
	private String orderStage;
	private String approvalType;
	private int toOrderNo;
	private int fromOrderNo;
	private String ordermonth;
	private String orderyear;
	private String billingtrigger_createdate;
	private double chargeAmount_Double;
	//private int  orderLineNumber;
	//[01] Start 
	private int business_serial_n;
	private String opms_Account_Id;
	private String lineItemNumber;
	private String opms_lineItemNumber;
	//[01] End
	
	
	public String getApprovalType() {
		return approvalType;
	}
	public void setApprovalType(String approvalType) {
		this.approvalType = approvalType;
	}
	public int getFromOrderNo() {
		return fromOrderNo;
	}
	public void setFromOrderNo(int fromOrderNo) {
		this.fromOrderNo = fromOrderNo;
	}
	public String getOrdermonth() {
		return ordermonth;
	}
	public void setOrdermonth(String ordermonth) {
		this.ordermonth = ordermonth;
	}
	public String getOrderyear() {
		return orderyear;
	}
	public void setOrderyear(String orderyear) {
		this.orderyear = orderyear;
	}
	public int getToOrderNo() {
		return toOrderNo;
	}
	public void setToOrderNo(int toOrderNo) {
		this.toOrderNo = toOrderNo;
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
	public String getBillingFormatName() {
		return billingFormatName;
	}
	public void setBillingFormatName(String billingFormatName) {
		this.billingFormatName = billingFormatName;
	}
	public String getBillingLevelName() {
		return billingLevelName;
	}
	public void setBillingLevelName(String billingLevelName) {
		this.billingLevelName = billingLevelName;
	}
	public long getBillingLevelNo() {
		return billingLevelNo;
	}
	public void setBillingLevelNo(long billingLevelNo) {
		this.billingLevelNo = billingLevelNo;
	}
	public String getBillingMode() {
		return billingMode;
	}
	public void setBillingMode(String billingMode) {
		this.billingMode = billingMode;
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
	public String getBillUom() {
		return billUom;
	}
	public void setBillUom(String billUom) {
		this.billUom = billUom;
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
	public String getChargeName() {
		return chargeName;
	}
	public void setChargeName(String chargeName) {
		this.chargeName = chargeName;
	}
	public String getChargeTypeName() {
		return chargeTypeName;
	}
	public void setChargeTypeName(String chargeTypeName) {
		this.chargeTypeName = chargeTypeName;
	}
	public int getContractPeriod() {
		return contractPeriod;
	}
	public void setContractPeriod(int contractPeriod) {
		this.contractPeriod = contractPeriod;
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
	public String getEntity() {
		return entity;
	}
	public void setEntity(String entity) {
		this.entity = entity;
	}
	public String getFromLocation() {
		return fromLocation;
	}
	public void setFromLocation(String fromLocation) {
		this.fromLocation = fromLocation;
	}
	public String getFx_St_Chg_Status() {
		return fx_St_Chg_Status;
	}
	public void setFx_St_Chg_Status(String fx_St_Chg_Status) {
		this.fx_St_Chg_Status = fx_St_Chg_Status;
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
	public String getKmsDistance() {
		return kmsDistance;
	}
	public void setKmsDistance(String kmsDistance) {
		this.kmsDistance = kmsDistance;
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
	public String getLogicalCircuitId() {
		return logicalCircuitId;
	}
	public void setLogicalCircuitId(String logicalCircuitId) {
		this.logicalCircuitId = logicalCircuitId;
	}
	public String getM6OrderNo2() {
		return m6OrderNo2;
	}
	public void setM6OrderNo2(String orderNo2) {
		m6OrderNo2 = orderNo2;
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
	public String getPmApproveDate() {
		return pmApproveDate;
	}
	public void setPmApproveDate(String pmApproveDate) {
		this.pmApproveDate = pmApproveDate;
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
	public long getPonum() {
		return ponum;
	}
	public void setPonum(long ponum) {
		this.ponum = ponum;
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
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getServiceOrderType() {
		return serviceOrderType;
	}
	public void setServiceOrderType(String serviceOrderType) {
		this.serviceOrderType = serviceOrderType;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getStorename() {
		return storename;
	}
	public void setStorename(String storename) {
		this.storename = storename;
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
	public String getBillingtrigger_createdate() {
		return billingtrigger_createdate;
	}
	public void setBillingtrigger_createdate(String billingtrigger_createdate) {
		this.billingtrigger_createdate = billingtrigger_createdate;
	}
	public double getChargeAmount_Double() {
		return chargeAmount_Double;
	}
	public void setChargeAmount_Double(double chargeAmount_Double) {
		chargeAmount_Double = chargeAmount_Double;
	}
	public void setOrderLineNumber(int orderLineNumber) {
		orderLineNumber = orderLineNumber;
	}
	public int getOrderLineNumber() {
		return orderLineNumber;
	}
	public int getBusiness_serial_n() {
		return business_serial_n;
	}
	public void setBusiness_serial_n(int business_serial_n) {
		this.business_serial_n = business_serial_n;
	}
	
	public String getOpms_Account_Id() {
		return opms_Account_Id;
	}
	public void setOpms_Account_Id(String opms_Account_Id) {
		this.opms_Account_Id = opms_Account_Id;
	}
	public String getLineItemNumber() {
		return lineItemNumber;
	}
	public void setLineItemNumber(String lineItemNumber) {
		this.lineItemNumber = lineItemNumber;
	}
	public String getOpms_lineItemNumber() {
		return opms_lineItemNumber;
	}
	public void setOpms_lineItemNumber(String opms_lineItemNumber) {
		this.opms_lineItemNumber = opms_lineItemNumber;
	}
	
							
					
						
}
