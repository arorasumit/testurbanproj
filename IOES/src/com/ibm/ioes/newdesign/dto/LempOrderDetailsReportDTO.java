package com.ibm.ioes.newdesign.dto;

import com.ibm.ioes.utilities.PagingSorting;
//[01]  opportunityId added  by Lawkush in reports "ACTIVE LINE ITEMS REPORT   ","PERFORMANCE SUMMARY REPORT  ","PERFORMANCE DETAIL REPORT","ORDER DETAIL REPORT","ORDER REPORT NEW DETAIL CWN","LEPM ORDER DETAIL REPORT"
//[02]  onnetOffnet, orderCategory, media added by VIPIN in report "LEPM Order Detail Report"
public class LempOrderDetailsReportDTO {
	
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
	private String chargeAmount_String;
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
	private String demo_infrastartdate;
	private String demo_infra_enddate;
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
	private String oldordertotal;
	private String oldlineamt;
	private String old_contract_period;
	private String ratio;
	private String taxation;
	private String factory_ckt_id;
	private String distance;
	private String accountManager;
	private String currencyCode;
	private String totalPoAmt;
	private String poAmount;
	private String bisource;
	private String orderType;
	private String dispatchAddress1;
	private String parent_name;
	private String copcApproveFromDate;
	private String copcApproveToDate;
	private int toOrderNo;
	private int fromOrderNo;
	private String onnetOffnet;
	private String servOrderCategory;
	private String media;
//	[01] Start  	 
	 private String opportunityId;	
	 public String getOpportunityId() {
		return opportunityId;
	}
	public void setOpportunityId(String opportunityId) {
		this.opportunityId = opportunityId;
	}
//	[01] end  
	
	
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
	public String getBisource() {
		return bisource;
	}
	public void setBisource(String bisource) {
		this.bisource = bisource;
	}
	public String getChargeAmount_String() {
		return chargeAmount_String;
	}
	public void setChargeAmount_String(String chargeAmount_String) {
		this.chargeAmount_String = chargeAmount_String;
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
	public String getDemo_infra_enddate() {
		return demo_infra_enddate;
	}
	public void setDemo_infra_enddate(String demo_infra_enddate) {
		this.demo_infra_enddate = demo_infra_enddate;
	}
	public String getDemo_infrastartdate() {
		return demo_infrastartdate;
	}
	public void setDemo_infrastartdate(String demo_infrastartdate) {
		this.demo_infrastartdate = demo_infrastartdate;
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
	public String getFactory_ckt_id() {
		return factory_ckt_id;
	}
	public void setFactory_ckt_id(String factory_ckt_id) {
		this.factory_ckt_id = factory_ckt_id;
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
	public String getOld_contract_period() {
		return old_contract_period;
	}
	public void setOld_contract_period(String old_contract_period) {
		this.old_contract_period = old_contract_period;
	}
	public String getOldlineamt() {
		return oldlineamt;
	}
	public void setOldlineamt(String oldlineamt) {
		this.oldlineamt = oldlineamt;
	}
	public String getOldordertotal() {
		return oldordertotal;
	}
	public void setOldordertotal(String oldordertotal) {
		this.oldordertotal = oldordertotal;
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
	public String getParent_name() {
		return parent_name;
	}
	public void setParent_name(String parent_name) {
		this.parent_name = parent_name;
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
	public String getTotalPoAmt() {
		return totalPoAmt;
	}
	public void setTotalPoAmt(String totalPoAmt) {
		this.totalPoAmt = totalPoAmt;
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
	
	public String getCopcApproveFromDate() {
		return copcApproveFromDate;
	}
	public void setCopcApproveFromDate(String copcApproveFromDate) {
		this.copcApproveFromDate = copcApproveFromDate;
	}
	public String getCopcApproveToDate() {
		return copcApproveToDate;
	}
	public void setCopcApproveToDate(String copcApproveToDate) {
		this.copcApproveToDate = copcApproveToDate;
	}
	public int getFromOrderNo() {
		return fromOrderNo;
	}
	public void setFromOrderNo(int fromOrderNo) {
		this.fromOrderNo = fromOrderNo;
	}
	public int getToOrderNo() {
		return toOrderNo;
	}
	public void setToOrderNo(int toOrderNo) {
		this.toOrderNo = toOrderNo;
	}
	//[02] Start
	public String getOnnetOffnet() {
		return onnetOffnet;
	}
	public String getServOrderCategory() {
		return servOrderCategory;
	}
	public String getMedia() {
		return media;
	}
	public void setOnnetOffnet(String onnetOffnet) {
		this.onnetOffnet = onnetOffnet;
	}
	public void setServOrderCategory(String orderCategory) {
		this.servOrderCategory = orderCategory;
	}
	public void setMedia(String media) {
		this.media = media;
	}
	//[02]End
	
	
	
}
