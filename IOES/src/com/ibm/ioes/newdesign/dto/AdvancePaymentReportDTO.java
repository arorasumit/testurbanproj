package com.ibm.ioes.newdesign.dto;

//[001] Priya Gupta

import java.sql.Date;

import com.ibm.ioes.utilities.PagingSorting;

public class AdvancePaymentReportDTO {
PagingSorting pagingSorting = new PagingSorting();
private String 	crmAccountNo;
private int	orderNo;
private String	currencyofOrder;
private String 	customerSegment;
private String	circle;
private String	orderCreationDate;
private String	amApprovalDate;
private String pmApprovalDate;
private String	orderApprovalDate;
private int	lsi;
private int serviceNo;

private String	product;
private String	licenseCompany;
private String	fxChildAccount;
private String	locDate;
private String	billingTriggerDate;
private String	arcChqNo;
private String	arcChqDate;
private String arcBankName;
private double	arcChqAmt;
private double	arcAmtAjd;
private String	otcChqNo;
private String	otcChqDate;
private String	otcBankName;
private double	otcChqAmt;
private double	otcAmtAjd;
private String	orderStatus;

//raveendra add arc
private String arcReEnterCheckNumber;
private Double arcReEnterCheckamount;
private String arcBankAccountNumber;
private String arcReEnterBankAccountNumber;
private String arcIfscCode;
private String arcReEnterIfscCode;

//end

//raveendra added otc
private String otcReEnterCheckNumber;
private Double otcReEnterCheckamount;
private String otcBankAccountNumber;
private String otcReEnterBankAccountNumber;
private String otcIfscCode;
private String otcReEnterIfscCode;

//ended

private String	fromorderCreationDate;
private String toorderCreationDate;
private String	fromChqDate;
private String  toChqDate;
private String datetype;
private String	fromotcDate;
private String  toOtcDate;
private String fromOrderDate;
private String toOrderDate;
private String arcExempted;
private String arcExpreason;
private String otcExempted;
private String otcExpreason;
private String lineNo;
private String lineName;
private int	clsi;
private String orderType;
private String siID;
private String fxStatus;
private String lineStatus;
private String challanNo;
private String locNo;
private String loc_Recv_Date;
private String change_OrderType;
private String challanDate;
private String partyNo;
private String accountName;
private String productName;
private String subproductName;
private java.sql.Timestamp	billingTriggerActionDate;
private String	billingTriggerActionDate_string;
private String party_id;
private java.sql.Date podate;
private java.sql.Date poRecieveDate;
private String podate_String;
private String poRecieveDate_String;
private String zoneName;
//[001] start
private String triggerprocess;
private String automatictriggereror;
private int actiontype;
private String locupdateprocess;
private java.sql.Timestamp triggerprocesspendencystart;
private String triggerprocesspendencystart_String;
private java.sql.Timestamp locupdateprocesspendencystart;
private String locupdateprocesspendencystart_String;
//[001] end

public String getZoneName() {
	return zoneName;
}
public void setZoneName(String zoneName) {
	this.zoneName = zoneName;
}
public double getArcAmtAjd() {
	return arcAmtAjd;
}
public void setArcAmtAjd(double arcAmtAjd) {
	this.arcAmtAjd = arcAmtAjd;
}
public double getArcChqAmt() {
	return arcChqAmt;
}
public void setArcChqAmt(double arcChqAmt) {
	this.arcChqAmt = arcChqAmt;
}
public double getOtcAmtAjd() {
	return otcAmtAjd;
}
public void setOtcAmtAjd(double otcAmtAjd) {
	this.otcAmtAjd = otcAmtAjd;
}
public double getOtcChqAmt() {
	return otcChqAmt;
}
public void setOtcChqAmt(double otcChqAmt) {
	this.otcChqAmt = otcChqAmt;
}
public String getArcExempted() {
	return arcExempted;
}
public void setArcExempted(String arcExempted) {
	this.arcExempted = arcExempted;
}
public String getArcExpreason() {
	return arcExpreason;
}
public void setArcExpreason(String arcExpreason) {
	this.arcExpreason = arcExpreason;
}
public String getOtcExempted() {
	return otcExempted;
}
public void setOtcExempted(String otcExempted) {
	this.otcExempted = otcExempted;
}
public String getOtcExpreason() {
	return otcExpreason;
}
public void setOtcExpreason(String otcExpreason) {
	this.otcExpreason = otcExpreason;
}

public void setOtcAmtAjd(long otcAmtAjd) {
	this.otcAmtAjd = otcAmtAjd;
}
public String getCustomerSegment() {
	return customerSegment;
}
public void setCustomerSegment(String customerSegment) {
	this.customerSegment = customerSegment;
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




public void setOtcChqAmt(long otcChqAmt) {
	this.otcChqAmt = otcChqAmt;
}

public int getLsi() {
	return lsi;
}
public void setLsi(int lsi) {
	this.lsi = lsi;
}
public int getOrderNo() {
	return orderNo;
}
public void setOrderNo(int orderNo) {
	this.orderNo = orderNo;
}
public int getServiceNo() {
	return serviceNo;
}
public void setServiceNo(int serviceNo) {
	this.serviceNo = serviceNo;
}
public String getFromotcDate() {
	return fromotcDate;
}
public void setFromotcDate(String fromotcDate) {
	this.fromotcDate = fromotcDate;
}
public String getToOtcDate() {
	return toOtcDate;
}
public void setToOtcDate(String toOtcDate) {
	this.toOtcDate = toOtcDate;
}
public String getDatetype() {
	return datetype;
}
public void setDatetype(String datetype) {
	this.datetype = datetype;
}
public String getFromChqDate() {
	return fromChqDate;
}
public void setFromChqDate(String fromChqDate) {
	this.fromChqDate = fromChqDate;
}
public String getFromorderCreationDate() {
	return fromorderCreationDate;
}
public void setFromorderCreationDate(String fromorderCreationDate) {
	this.fromorderCreationDate = fromorderCreationDate;
}
public String getToChqDate() {
	return toChqDate;
}
public void setToChqDate(String toChqDate) {
	this.toChqDate = toChqDate;
}
public String getToorderCreationDate() {
	return toorderCreationDate;
}
public void setToorderCreationDate(String toorderCreationDate) {
	this.toorderCreationDate = toorderCreationDate;
}
public String getAmApprovalDate() {
	return amApprovalDate;
}
public void setAmApprovalDate(String amApprovalDate) {
	this.amApprovalDate = amApprovalDate;
}


public String getArcBankName() {
	return arcBankName;
}
public void setArcBankName(String arcBankName) {
	this.arcBankName = arcBankName;
}

public String getArcChqDate() {
	return arcChqDate;
}
public void setArcChqDate(String arcChqDate) {
	this.arcChqDate = arcChqDate;
}
public String getArcChqNo() {
	return arcChqNo;
}
public void setArcChqNo(String arcChqNo) {
	this.arcChqNo = arcChqNo;
}
public String getBillingTriggerDate() {
	return billingTriggerDate;
}
public void setBillingTriggerDate(String billingTriggerDate) {
	this.billingTriggerDate = billingTriggerDate;
}
public String getCircle() {
	return circle;
}
public void setCircle(String circle) {
	this.circle = circle;
}
public String getCrmAccountNo() {
	return crmAccountNo;
}
public void setCrmAccountNo(String crmAccountNo) {
	this.crmAccountNo = crmAccountNo;
}
public String getCurrencyofOrder() {
	return currencyofOrder;
}
public void setCurrencyofOrder(String currencyofOrder) {
	this.currencyofOrder = currencyofOrder;
}

public String getFxChildAccount() {
	return fxChildAccount;
}
public void setFxChildAccount(String fxChildAccount) {
	this.fxChildAccount = fxChildAccount;
}
public String getLicenseCompany() {
	return licenseCompany;
}
public void setLicenseCompany(String licenseCompany) {
	this.licenseCompany = licenseCompany;
}
public String getLocDate() {
	return locDate;
}
public void setLocDate(String locDate) {
	this.locDate = locDate;
}

public String getOrderApprovalDate() {
	return orderApprovalDate;
}
public void setOrderApprovalDate(String orderApprovalDate) {
	this.orderApprovalDate = orderApprovalDate;
}
public String getOrderCreationDate() {
	return orderCreationDate;
}
public void setOrderCreationDate(String orderCreationDate) {
	this.orderCreationDate = orderCreationDate;
}

public String getOrderStatus() {
	return orderStatus;
}
public void setOrderStatus(String orderStatus) {
	this.orderStatus = orderStatus;
}

public String getOtcBankName() {
	return otcBankName;
}
public void setOtcBankName(String otcBankName) {
	this.otcBankName = otcBankName;
}

public String getOtcChqDate() {
	return otcChqDate;
}
public void setOtcChqDate(String otcChqDate) {
	this.otcChqDate = otcChqDate;
}
public String getOtcChqNo() {
	return otcChqNo;
}
public void setOtcChqNo(String otcChqNo) {
	this.otcChqNo = otcChqNo;
}
public String getPmApprovalDate() {
	return pmApprovalDate;
}
public void setPmApprovalDate(String pmApprovalDate) {
	this.pmApprovalDate = pmApprovalDate;
}
public String getProduct() {
	return product;
}
public void setProduct(String product) {
	this.product = product;
}

public PagingSorting getPagingSorting() {
	return pagingSorting;
}
public void setPagingSorting(PagingSorting pagingSorting) {
	this.pagingSorting = pagingSorting;
}
public String getLineNo() {
	return lineNo;
}
public void setLineNo(String lineNo) {
	this.lineNo = lineNo;
}
public String getLineName() {
	return lineName;
}
public void setLineName(String lineName) {
	this.lineName = lineName;
}
public int getClsi() {
	return clsi;
}
public void setClsi(int clsi) {
	this.clsi = clsi;
}
public String getOrderType() {
	return orderType;
}
public void setOrderType(String orderType) {
	this.orderType = orderType;
}
public String getSiID() {
	return siID;
}
public void setSiID(String siID) {
	this.siID = siID;
}
public String getFxStatus() {
	return fxStatus;
}
public void setFxStatus(String fxStatus) {
	this.fxStatus = fxStatus;
}
public String getLineStatus() {
	return lineStatus;
}
public void setLineStatus(String lineStatus) {
	this.lineStatus = lineStatus;
}
public String getChallanNo() {
	return challanNo;
}
public void setChallanNo(String challanNo) {
	this.challanNo = challanNo;
}
public String getLocNo() {
	return locNo;
}
public void setLocNo(String locNo) {
	this.locNo = locNo;
}
public String getLoc_Recv_Date() {
	return loc_Recv_Date;
}
public void setLoc_Recv_Date(String loc_Recv_Date) {
	this.loc_Recv_Date = loc_Recv_Date;
}
public String getChange_OrderType() {
	return change_OrderType;
}
public void setChange_OrderType(String change_OrderType) {
	this.change_OrderType = change_OrderType;
}
public String getChallanDate() {
	return challanDate;
}
public void setChallanDate(String challanDate) {
	this.challanDate = challanDate;
}
public String getPartyNo() {
	return partyNo;
}
public void setPartyNo(String partyNo) {
	this.partyNo = partyNo;
}
public String getAccountName() {
	return accountName;
}
public void setAccountName(String accountName) {
	this.accountName = accountName;
}
public String getProductName() {
	return productName;
}
public void setProductName(String productName) {
	this.productName = productName;
}
public String getSubproductName() {
	return subproductName;
}
public void setSubproductName(String subproductName) {
	this.subproductName = subproductName;
}
public String getParty_id() {
	return party_id;
}
public void setParty_id(String party_id) {
	this.party_id = party_id;
}
public java.sql.Date getPodate() {
	return podate;
}
public void setPodate(java.sql.Date podate) {
	this.podate = podate;
}
public java.sql.Date getPoRecieveDate() {
	return poRecieveDate;
}
public void setPoRecieveDate(java.sql.Date poRecieveDate) {
	this.poRecieveDate = poRecieveDate;
}
public String getPodate_String() {
	return podate_String;
}
public void setPodate_String(String podate_String) {
	this.podate_String = podate_String;
}
public String getPoRecieveDate_String() {
	return poRecieveDate_String;
}
public void setPoRecieveDate_String(String poRecieveDate_String) {
	this.poRecieveDate_String = poRecieveDate_String;
}
public java.sql.Timestamp getBillingTriggerActionDate() {
	return billingTriggerActionDate;
}
public void setBillingTriggerActionDate(
		java.sql.Timestamp billingTriggerActionDate) {
	this.billingTriggerActionDate = billingTriggerActionDate;
}
public String getBillingTriggerActionDate_string() {
	return billingTriggerActionDate_string;
}
public void setBillingTriggerActionDate_string(
		String billingTriggerActionDate_string) {
	this.billingTriggerActionDate_string = billingTriggerActionDate_string;
}


//Raveendra added
public String getArcReEnterCheckNumber() {
	return arcReEnterCheckNumber;
}
public void setArcReEnterCheckNumber(String arcReEnterCheckNumber) {
	this.arcReEnterCheckNumber = arcReEnterCheckNumber;
}


public Double getArcReEnterCheckamount() {
	return arcReEnterCheckamount;
}
public void setArcReEnterCheckamount(Double arcReEnterCheckamount) {
	this.arcReEnterCheckamount = arcReEnterCheckamount;
}
public String getArcBankAccountNumber() {
	return arcBankAccountNumber;
}
public void setArcBankAccountNumber(String arcBankAccountNumber) {
	this.arcBankAccountNumber = arcBankAccountNumber;
}
public String getArcReEnterBankAccountNumber() {
	return arcReEnterBankAccountNumber;
}
public void setArcReEnterBankAccountNumber(String arcReEnterBankAccountNumber) {
	this.arcReEnterBankAccountNumber = arcReEnterBankAccountNumber;
}
public String getArcIfscCode() {
	return arcIfscCode;
}
public void setArcIfscCode(String arcIfscCode) {
	this.arcIfscCode = arcIfscCode;
}
public String getArcReEnterIfscCode() {
	return arcReEnterIfscCode;
}
public void setArcReEnterIfscCode(String arcReEnterIfscCode) {
	this.arcReEnterIfscCode = arcReEnterIfscCode;
}
public String getOtcReEnterCheckNumber() {
	return otcReEnterCheckNumber;
}
public void setOtcReEnterCheckNumber(String otcReEnterCheckNumber) {
	this.otcReEnterCheckNumber = otcReEnterCheckNumber;
}

public Double getOtcReEnterCheckamount() {
	return otcReEnterCheckamount;
}
public void setOtcReEnterCheckamount(Double otcReEnterCheckamount) {
	this.otcReEnterCheckamount = otcReEnterCheckamount;
}
public String getOtcBankAccountNumber() {
	return otcBankAccountNumber;
}
public void setOtcBankAccountNumber(String otcBankAccountNumber) {
	this.otcBankAccountNumber = otcBankAccountNumber;
}
public String getOtcReEnterBankAccountNumber() {
	return otcReEnterBankAccountNumber;
}
public void setOtcReEnterBankAccountNumber(String otcReEnterBankAccountNumber) {
	this.otcReEnterBankAccountNumber = otcReEnterBankAccountNumber;
}
public String getOtcIfscCode() {
	return otcIfscCode;
}
public void setOtcIfscCode(String otcIfscCode) {
	this.otcIfscCode = otcIfscCode;
}
public String getOtcReEnterIfscCode() {
	return otcReEnterIfscCode;
}
public void setOtcReEnterIfscCode(String otcReEnterIfscCode) {
	this.otcReEnterIfscCode = otcReEnterIfscCode;
}


//ended
//[001] Start 
public String getTriggerprocess() {
	return triggerprocess;
}
public void setTriggerprocess(String triggerprocess) {
	this.triggerprocess = triggerprocess;
}

public String getAutomatictriggereror() {
	return automatictriggereror;
}
public void setAutomatictriggereror(String automatictriggereror) {
	this.automatictriggereror = automatictriggereror;
}
public int getActiontype() {
	return actiontype;
}
public void setActiontype(int actiontype) {
	this.actiontype = actiontype;
}
public String getLocupdateprocess() {
	return locupdateprocess;
}
public void setLocupdateprocess(String locupdateprocess) {
	this.locupdateprocess = locupdateprocess;
}
public java.sql.Timestamp getTriggerprocesspendencystart() {
	return triggerprocesspendencystart;
}
public void setTriggerprocesspendencystart(
		java.sql.Timestamp triggerprocesspendencystart) {
	this.triggerprocesspendencystart = triggerprocesspendencystart;
}
public String getTriggerprocesspendencystart_String() {
	return triggerprocesspendencystart_String;
}
public void setTriggerprocesspendencystart_String(
		String triggerprocesspendencystart_String) {
	this.triggerprocesspendencystart_String = triggerprocesspendencystart_String;
}
public java.sql.Timestamp getLocupdateprocesspendencystart() {
	return locupdateprocesspendencystart;
}
public void setLocupdateprocesspendencystart(
		java.sql.Timestamp locupdateprocesspendencystart) {
	this.locupdateprocesspendencystart = locupdateprocesspendencystart;
}
public String getLocupdateprocesspendencystart_String() {
	return locupdateprocesspendencystart_String;
}
public void setLocupdateprocesspendencystart_String(
		String locupdateprocesspendencystart_String) {
	this.locupdateprocesspendencystart_String = locupdateprocesspendencystart_String;
}


//[001] end
	
}
