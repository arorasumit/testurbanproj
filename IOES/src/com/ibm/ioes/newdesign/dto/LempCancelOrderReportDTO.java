package com.ibm.ioes.newdesign.dto;

import com.ibm.ioes.utilities.PagingSorting;

public class LempCancelOrderReportDTO {
	PagingSorting pagingSorting = new PagingSorting();
	private String partyName;
	private String orderNo;
	private String copcApproveDate;
	private String logicalCircuitId;
	private int serviceId;
	private String quoteNo;
	private String productName;
	private String subProductName;
	private String primarylocation;
	private String seclocation;
	private String ordersubtype;
	private String bandwaidth;
	private String lineItemAmount;
	private String prjmngname;
	private String prjmgremail;
	private String actmngname;
	private String zoneName;
	private String regionName;
	private String verticalDetails;
	private String act_category;
	private String custPoDetailNo;
	private String custPoDate;
	private String orderDate;
	private String pmApproveDate;
    private String amApproveDate;
	private String nio_approve_date;
	private String rfs_date;
	private String ordercategory;
	private String orderStatus;
	private String line_desc; 
	private String linename;
	private String sub_linename;
	private String chargeName;
	private String chargeinfoID;
	private int serviceProductID;
	private String serviceName;
	private int accountID;
	private String entity;
	private String licCompanyName;
	private int contractPeriod;
	private String frequencyName;
	private String chargeTypeName;
    private String serviceType;
	private String uom;
	private String billing_bandwidth;
	private String billing_uom;
	private String from_city;
	private String to_city;
	private String ratio;
	private String taxation;

	private String distance;
	private String accountManager;
	private String currencyCode;
	private double orderTotal;
	private String poAmount;
	private String orderType;
	private String dispatchAddress1;
	private String serviceStage;
	private String canceldate;
	private String canceldatefrom;
	private String canceldateto;
	 private String pm_approval_remarks;
	 private String copc_approval_remarks;
	 private String am_approval_remarks;
//nagarjuna
	 private String charge_Disconnection_Status;
	 private String subchange_type;
	 
//end 	nagarjuna
	 //gunjan
	 private String ord_cancel_reason;
	 private String srv_cancel_reason;
	 
//sada
	
	 private String service_cancelledby;
	 private String serv_cancel_reson;
	 private String serv_cancel_remarks;
	 private String service_cancl_date;
//end sada
	 
	 private String opportunityId;	
	 
	 private String partyid;
	 public String getOpportunityId() {
		return opportunityId;
	}
	public void setOpportunityId(String opportunityId) {
		this.opportunityId = opportunityId;
	}

	
	public String getAm_approval_remarks() {
		return am_approval_remarks;
	}
	public void setAm_approval_remarks(String am_approval_remarks) {
		this.am_approval_remarks = am_approval_remarks;
	}
	public String getCopc_approval_remarks() {
		return copc_approval_remarks;
	}
	public void setCopc_approval_remarks(String copc_approval_remarks) {
		this.copc_approval_remarks = copc_approval_remarks;
	}
	public String getPm_approval_remarks() {
		return pm_approval_remarks;
	}
	public void setPm_approval_remarks(String pm_approval_remarks) {
		this.pm_approval_remarks = pm_approval_remarks;
	}
	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}
	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
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
	public String getAct_category() {
		return act_category;
	}
	public void setAct_category(String act_category) {
		this.act_category = act_category;
	}
	public String getActmngname() {
		return actmngname;
	}
	public void setActmngname(String actmngname) {
		this.actmngname = actmngname;
	}
	public String getAmApproveDate() {
		return amApproveDate;
	}
	public void setAmApproveDate(String amApproveDate) {
		this.amApproveDate = amApproveDate;
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
	public String getBilling_uom() {
		return billing_uom;
	}
	public void setBilling_uom(String billing_uom) {
		this.billing_uom = billing_uom;
	}
	public String getCanceldate() {
		return canceldate;
	}
	public void setCanceldate(String canceldate) {
		this.canceldate = canceldate;
	}
	public String getChargeinfoID() {
		return chargeinfoID;
	}
	public void setChargeinfoID(String chargeinfoID) {
		this.chargeinfoID = chargeinfoID;
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
	public String getDispatchAddress1() {
		return dispatchAddress1;
	}
	public void setDispatchAddress1(String dispatchAddress1) {
		this.dispatchAddress1 = dispatchAddress1;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
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
	public String getFrom_city() {
		return from_city;
	}
	public void setFrom_city(String from_city) {
		this.from_city = from_city;
	}
	public String getLicCompanyName() {
		return licCompanyName;
	}
	public void setLicCompanyName(String licCompanyName) {
		this.licCompanyName = licCompanyName;
	}
	public String getLine_desc() {
		return line_desc;
	}
	public void setLine_desc(String line_desc) {
		this.line_desc = line_desc;
	}
	public String getLineItemAmount() {
		return lineItemAmount;
	}
	public void setLineItemAmount(String lineItemAmount) {
		this.lineItemAmount = lineItemAmount;
	}
	public String getLinename() {
		return linename;
	}
	public void setLinename(String linename) {
		this.linename = linename;
	}
	public String getLogicalCircuitId() {
		return logicalCircuitId;
	}
	public void setLogicalCircuitId(String logicalCircuitId) {
		this.logicalCircuitId = logicalCircuitId;
	}
	public String getNio_approve_date() {
		return nio_approve_date;
	}
	public void setNio_approve_date(String nio_approve_date) {
		this.nio_approve_date = nio_approve_date;
	}
	public String getOrdercategory() {
		return ordercategory;
	}
	public void setOrdercategory(String ordercategory) {
		this.ordercategory = ordercategory;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getOrdersubtype() {
		return ordersubtype;
	}
	public void setOrdersubtype(String ordersubtype) {
		this.ordersubtype = ordersubtype;
	}
	public double getOrderTotal() {
		return orderTotal;
	}
	public void setOrderTotal(double orderTotal) {
		this.orderTotal = orderTotal;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
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
	public String getPrimarylocation() {
		return primarylocation;
	}
	public void setPrimarylocation(String primarylocation) {
		this.primarylocation = primarylocation;
	}
	public String getPrjmgremail() {
		return prjmgremail;
	}
	public void setPrjmgremail(String prjmgremail) {
		this.prjmgremail = prjmgremail;
	}
	public String getPrjmngname() {
		return prjmngname;
	}
	public void setPrjmngname(String prjmngname) {
		this.prjmngname = prjmngname;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getQuoteNo() {
		return quoteNo;
	}
	public void setQuoteNo(String quoteNo) {
		this.quoteNo = quoteNo;
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
	public int getServiceProductID() {
		return serviceProductID;
	}
	public void setServiceProductID(int serviceProductID) {
		this.serviceProductID = serviceProductID;
	}
	public String getServiceStage() {
		return serviceStage;
	}
	public void setServiceStage(String serviceStage) {
		this.serviceStage = serviceStage;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getSub_linename() {
		return sub_linename;
	}
	public void setSub_linename(String sub_linename) {
		this.sub_linename = sub_linename;
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
	public String getTo_city() {
		return to_city;
	}
	public void setTo_city(String to_city) {
		this.to_city = to_city;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
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
	public String getCanceldatefrom() {
		return canceldatefrom;
	}
	public void setCanceldatefrom(String canceldatefrom) {
		this.canceldatefrom = canceldatefrom;
	}
	public String getCanceldateto() {
		return canceldateto;
	}
	public void setCanceldateto(String canceldateto) {
		this.canceldateto = canceldateto;
	}
	public String getCharge_Disconnection_Status() {
		return charge_Disconnection_Status;
	}
	public void setCharge_Disconnection_Status(String charge_Disconnection_Status) {
		this.charge_Disconnection_Status = charge_Disconnection_Status;
	}
	public String getSubchange_type() {
		return subchange_type;
	}
	public void setSubchange_type(String subchange_type) {
		this.subchange_type = subchange_type;
	}
	public String getOrd_cancel_reason() {
		return ord_cancel_reason;
	}
	public void setOrd_cancel_reason(String ord_cancel_reason) {
		this.ord_cancel_reason = ord_cancel_reason;
	}
	public String getSrv_cancel_reason() {
		return srv_cancel_reason;
	}
	public void setSrv_cancel_reason(String srv_cancel_reason) {
		this.srv_cancel_reason = srv_cancel_reason;
	}
	public String getPartyid() {
		return partyid;
	}
	public void setPartyid(String partyid) {
		this.partyid = partyid;
	}
	

	public String getService_cancelledby() {
		return service_cancelledby;
	}
	public void setService_cancelledby(String service_cancelledby) {
		this.service_cancelledby = service_cancelledby;
	}
	public String getServ_cancel_reson() {
		return serv_cancel_reson;
	}
	public void setServ_cancel_reson(String serv_cancel_reson) {
		this.serv_cancel_reson = serv_cancel_reson;
	}
	public String getServ_cancel_remarks() {
		return serv_cancel_remarks;
	}
	public void setServ_cancel_remarks(String serv_cancel_remarks) {
		this.serv_cancel_remarks = serv_cancel_remarks;
	}
	public String getService_cancl_date() {
		return service_cancl_date;
	}
	public void setService_cancl_date(String service_cancl_date) {
		this.service_cancl_date = service_cancl_date;
	}
}
