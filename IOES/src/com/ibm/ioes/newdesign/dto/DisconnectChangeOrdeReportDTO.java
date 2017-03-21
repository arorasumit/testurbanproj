package com.ibm.ioes.newdesign.dto;

import com.ibm.ioes.forms.ComponentsDto;
import com.ibm.ioes.utilities.PagingSorting;

public class DisconnectChangeOrdeReportDTO {
	PagingSorting pagingSorting = new PagingSorting();
	private String toDate;
	private String Primarylocation;
	private String fromDate;
	 private int isUsage;
	 private String tokenNO;
	 private long sourcePartyID;
	 private long fx_internal_acc_id;
	 private int creditPeriod;
	private String ordersubtype;
	private String logicalCircuitId;
	private String serviceName;
	private String linename;
	private String chargeTypeName;
	private ComponentsDto componentDto;
	private String chargeName;
	private String orderStageDescription;
	private String mocn_no;
	private int lineno;
	private String bandwidth_att;
	private String frequencyName;
	private int chargeTypeID;
	private String Tokenno;
	private String bill_period;
	private String startDateLogic;
	private String endDateLogic;
	private long amt;
	private double totalAmountIncludingCurrent;
	private String advance;
	private String m6_prod_id;
	private String m6cktid;
	private String crmAccountNoString;
	//private String creditPeriodName;
	private String billingLevelName;
	private int componentID;
	private int componentInfoID;
	private String componentName;
	private String packageName;
	private int packageID;
	private String chargeEndDate;
	private String startDate;
	private int accountID;
	private String creditPeriodName;
	private String currencyName;
	private String entity;
	private String billingMode;
	private String billingTypeName;
	private String billingformat;
	private String licCompanyName;
	private String taxation;
	private String ChargeinfoID;
	private String penaltyClause;
	private String billingLevel;
	private long billingLevelId;
	private String store;
	private String hardwareType;
	private String formAvailable;
	private String nature_sale;
	private String type_sale;
	private int poNumber;
	private String poDate;
	private String party;
	private String annitation;
	private String business_serial_no;
	private String installation_addressaa1;
	private String locDate;
	private String serviceOrderType;
	private String newOrderRemark;
	private String uom;
	private String locno;
	private int orderLineNumber;
	private String Billing_Trigger_Status;
	private String Billing_Trigger_Flag;
	private String pm_pro_date;
	private String billingTriggerDate;
	private String challenno;
	private String challendate;
	private String child_act_no;
	private String child_ac_fxstatus;
	private String orderDate;
	private String order_type;
	private String copcapprovaldate;
	private String billingtrigger_createdate;
	private int customer_logicalSINumber;
	private String hardware_flag;
	private int chargeInfoID;
	private String serviceStage;
	private String orderStage;
	private String actmgrname;
	private String prjmngname;
	//private String orderDate;
	private String rfs_date;
	private String cust_po_rec_date;
	private String charge_status;
	private String verticalDetails;
	private String region;
	private String demo;
	private String disconnection_remarks;
	private String srno;
	private String request_rec_date;
	private String ordermonth;
	private String ckt_id;
	private String standard_reason;
	private String bandwaidth;
	private String billing_bandwidth;
	private String billing_uom;
	private String rate_code;
	private String last_mile_media;
	private String last_mile_remarks;
	private String chargeable_Distance;
	private String last_mile_provider;
	private String link_type;
	private String dispatchAddress1;
	private String indicative_value;
	private String productName;
	private String custPoDate;
	private String custPoDetailNo;
	private String primaryLocation;
	private String prodAlisName;
	private String ratio;
	private String seclocation;
	private String sub_linename;
	private String orderNo;
	private int charge_hdr_id;
	private int serviceId;
	private String frequencyAmt;
	private String lineItemAmount;
	private String chargeAmount_String;
	private int startDateDays;
	private int startDateMonth;
	private int endDateDays;
	private int endDateMonth;
	private String annualRate;
	private int contractMonths;
	private int commitmentPeriod;
	private long noticePeriod;
	private String periodsInMonths;
	private String totalPoAmt;
	private int party_id;
	private String cust_act_id;
	private String m6_order_id;
	private int pre_crmorderid;
	private String pk_charge_id;
	private String billing_location_from;
	private String billing_location_to;
	private String cus_segment;
	private int customerSegmentId=0;
	
	/*20151224-R1-021980 - Changes in Disconnection Report || ROM 
	 *  CHanged by :satish Start*/
	private String DesiredDueDate;
	private String DerivedDisconnectionDate;
	private String IsTriggerRequired;
	private String LineTriggered;
	private String TriggerProcess;
	private String TriggerDoneBy;
	private String AutomaticTriggerError;
	private String srrequest;
	
	
	public String getSrrequest() {
		return srrequest;
	}
	public void setSrrequest(String srrequest) {
		this.srrequest = srrequest;
	}
	public String getDesiredDueDate() {
		return DesiredDueDate;
	}
	public void setDesiredDueDate(String desiredDueDate) {
		DesiredDueDate = desiredDueDate;
	}
	public String getDerivedDisconnectionDate() {
		return DerivedDisconnectionDate;
	}
	public void setDerivedDisconnectionDate(String derivedDisconnectionDate) {
		DerivedDisconnectionDate = derivedDisconnectionDate;
	}
	public String getIsTriggerRequired() {
		return IsTriggerRequired;
	}
	public void setIsTriggerRequired(String isTriggerRequired) {
		IsTriggerRequired = isTriggerRequired;
	}
	public String getLineTriggered() {
		return LineTriggered;
	}
	public void setLineTriggered(String lineTriggered) {
		LineTriggered = lineTriggered;
	}
	public String getTriggerProcess() {
		return TriggerProcess;
	}
	public void setTriggerProcess(String triggerProcess) {
		TriggerProcess = triggerProcess;
	}
	public String getTriggerDoneBy() {
		return TriggerDoneBy;
	}
	public void setTriggerDoneBy(String triggerDoneBy) {
		TriggerDoneBy = triggerDoneBy;
	}
	public String getAutomaticTriggerError() {
		return AutomaticTriggerError;
	}
	public void setAutomaticTriggerError(String automaticTriggerError) {
		AutomaticTriggerError = automaticTriggerError;
	}
	/*20151224-R1-021980 - Changes in Disconnection Report ||ends*/
	
	public String getBilling_Trigger_Status() {
		return Billing_Trigger_Status;
	}
	public void setBilling_Trigger_Status(String billing_Trigger_Status) {
		Billing_Trigger_Status = billing_Trigger_Status;
	}
	public int getAccountID() {
		return accountID;
	}
	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}
	public String getActmgrname() {
		return actmgrname;
	}
	public void setActmgrname(String actmgrname) {
		this.actmgrname = actmgrname;
	}
	public String getAnnitation() {
		return annitation;
	}
	public void setAnnitation(String annitation) {
		this.annitation = annitation;
	}
	public String getAnnualRate() {
		return annualRate;
	}
	public void setAnnualRate(String annualRate) {
		this.annualRate = annualRate;
	}
	public String getBandwaidth() {
		return bandwaidth;
	}
	public void setBandwaidth(String bandwaidth) {
		this.bandwaidth = bandwaidth;
	}
	public String getBill_period() {
		return bill_period;
	}
	public void setBill_period(String bill_period) {
		this.bill_period = bill_period;
	}
	public String getBilling_bandwidth() {
		return billing_bandwidth;
	}
	public void setBilling_bandwidth(String billing_bandwidth) {
		this.billing_bandwidth = billing_bandwidth;
	}
	public String getBilling_location_from() {
		return billing_location_from;
	}
	public void setBilling_location_from(String billing_location_from) {
		this.billing_location_from = billing_location_from;
	}
	public String getBilling_location_to() {
		return billing_location_to;
	}
	public void setBilling_location_to(String billing_location_to) {
		this.billing_location_to = billing_location_to;
	}
	public String getBilling_Trigger_Flag() {
		return Billing_Trigger_Flag;
	}
	public void setBilling_Trigger_Flag(String billing_Trigger_Flag) {
		Billing_Trigger_Flag = billing_Trigger_Flag;
	}
	public String getBilling_uom() {
		return billing_uom;
	}
	public void setBilling_uom(String billing_uom) {
		this.billing_uom = billing_uom;
	}
	public String getBillingformat() {
		return billingformat;
	}
	public void setBillingformat(String billingformat) {
		this.billingformat = billingformat;
	}
	public String getBillingLevel() {
		return billingLevel;
	}
	public void setBillingLevel(String billingLevel) {
		this.billingLevel = billingLevel;
	}
	public long getBillingLevelId() {
		return billingLevelId;
	}
	public void setBillingLevelId(long billingLevelId) {
		this.billingLevelId = billingLevelId;
	}
	public String getBillingMode() {
		return billingMode;
	}
	public void setBillingMode(String billingMode) {
		this.billingMode = billingMode;
	}
	public String getBillingtrigger_createdate() {
		return billingtrigger_createdate;
	}
	public void setBillingtrigger_createdate(String billingtrigger_createdate) {
		this.billingtrigger_createdate = billingtrigger_createdate;
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
	public int getCharge_hdr_id() {
		return charge_hdr_id;
	}
	public void setCharge_hdr_id(int charge_hdr_id) {
		this.charge_hdr_id = charge_hdr_id;
	}
	public String getCharge_status() {
		return charge_status;
	}
	public void setCharge_status(String charge_status) {
		this.charge_status = charge_status;
	}
	public String getChargeable_Distance() {
		return chargeable_Distance;
	}
	public void setChargeable_Distance(String chargeable_Distance) {
		this.chargeable_Distance = chargeable_Distance;
	}
	public String getChargeAmount_String() {
		return chargeAmount_String;
	}
	public void setChargeAmount_String(String chargeAmount_String) {
		this.chargeAmount_String = chargeAmount_String;
	}
	public String getChargeEndDate() {
		return chargeEndDate;
	}
	public void setChargeEndDate(String chargeEndDate) {
		this.chargeEndDate = chargeEndDate;
	}
	public int getChargeInfoID() {
		return chargeInfoID;
	}
	public void setChargeInfoID(int chargeInfoID) {
		this.chargeInfoID = chargeInfoID;
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
	public String getCkt_id() {
		return ckt_id;
	}
	public void setCkt_id(String ckt_id) {
		this.ckt_id = ckt_id;
	}
	public int getCommitmentPeriod() {
		return commitmentPeriod;
	}
	public void setCommitmentPeriod(int commitmentPeriod) {
		this.commitmentPeriod = commitmentPeriod;
	}
	public int getContractMonths() {
		return contractMonths;
	}
	public void setContractMonths(int contractMonths) {
		this.contractMonths = contractMonths;
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
	public String getCust_act_id() {
		return cust_act_id;
	}
	public void setCust_act_id(String cust_act_id) {
		this.cust_act_id = cust_act_id;
	}
	public String getCust_po_rec_date() {
		return cust_po_rec_date;
	}
	public void setCust_po_rec_date(String cust_po_rec_date) {
		this.cust_po_rec_date = cust_po_rec_date;
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
	public String getDemo() {
		return demo;
	}
	public void setDemo(String demo) {
		this.demo = demo;
	}
	public String getDisconnection_remarks() {
		return disconnection_remarks;
	}
	public void setDisconnection_remarks(String disconnection_remarks) {
		this.disconnection_remarks = disconnection_remarks;
	}
	public String getDispatchAddress1() {
		return dispatchAddress1;
	}
	public void setDispatchAddress1(String dispatchAddress1) {
		this.dispatchAddress1 = dispatchAddress1;
	}
	public int getEndDateDays() {
		return endDateDays;
	}
	public void setEndDateDays(int endDateDays) {
		this.endDateDays = endDateDays;
	}
	public String getEndDateLogic() {
		return endDateLogic;
	}
	public void setEndDateLogic(String endDateLogic) {
		this.endDateLogic = endDateLogic;
	}
	public int getEndDateMonth() {
		return endDateMonth;
	}
	public void setEndDateMonth(int endDateMonth) {
		this.endDateMonth = endDateMonth;
	}
	public String getEntity() {
		return entity;
	}
	public void setEntity(String entity) {
		this.entity = entity;
	}
	public String getFormAvailable() {
		return formAvailable;
	}
	public void setFormAvailable(String formAvailable) {
		this.formAvailable = formAvailable;
	}
	public String getFrequencyAmt() {
		return frequencyAmt;
	}
	public void setFrequencyAmt(String frequencyAmt) {
		this.frequencyAmt = frequencyAmt;
	}
	public String getFrequencyName() {
		return frequencyName;
	}
	public void setFrequencyName(String frequencyName) {
		this.frequencyName = frequencyName;
	}
	public String getHardware_flag() {
		return hardware_flag;
	}
	public void setHardware_flag(String hardware_flag) {
		this.hardware_flag = hardware_flag;
	}
	public String getHardwareType() {
		return hardwareType;
	}
	public void setHardwareType(String hardwareType) {
		this.hardwareType = hardwareType;
	}
	public String getIndicative_value() {
		return indicative_value;
	}
	public void setIndicative_value(String indicative_value) {
		this.indicative_value = indicative_value;
	}
	public String getLast_mile_media() {
		return last_mile_media;
	}
	public void setLast_mile_media(String last_mile_media) {
		this.last_mile_media = last_mile_media;
	}
	public String getLast_mile_provider() {
		return last_mile_provider;
	}
	public void setLast_mile_provider(String last_mile_provider) {
		this.last_mile_provider = last_mile_provider;
	}
	public String getLast_mile_remarks() {
		return last_mile_remarks;
	}
	public void setLast_mile_remarks(String last_mile_remarks) {
		this.last_mile_remarks = last_mile_remarks;
	}
	public String getLicCompanyName() {
		return licCompanyName;
	}
	public void setLicCompanyName(String licCompanyName) {
		this.licCompanyName = licCompanyName;
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
	public String getLink_type() {
		return link_type;
	}
	public void setLink_type(String link_type) {
		this.link_type = link_type;
	}
	public String getLocDate() {
		return locDate;
	}
	public void setLocDate(String locDate) {
		this.locDate = locDate;
	}
	public String getLocno() {
		return locno;
	}
	public void setLocno(String locno) {
		this.locno = locno;
	}
	public String getLogicalCircuitId() {
		return logicalCircuitId;
	}
	public void setLogicalCircuitId(String logicalCircuitId) {
		this.logicalCircuitId = logicalCircuitId;
	}
	public String getM6_order_id() {
		return m6_order_id;
	}
	public void setM6_order_id(String m6_order_id) {
		this.m6_order_id = m6_order_id;
	}
	public String getNature_sale() {
		return nature_sale;
	}
	public void setNature_sale(String nature_sale) {
		this.nature_sale = nature_sale;
	}
	public String getNewOrderRemark() {
		return newOrderRemark;
	}
	public void setNewOrderRemark(String newOrderRemark) {
		this.newOrderRemark = newOrderRemark;
	}
	public long getNoticePeriod() {
		return noticePeriod;
	}
	public void setNoticePeriod(long noticePeriod) {
		this.noticePeriod = noticePeriod;
	}
	public String getOrder_type() {
		return order_type;
	}
	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public int getOrderLineNumber() {
		return orderLineNumber;
	}
	public void setOrderLineNumber(int orderLineNumber) {
		this.orderLineNumber = orderLineNumber;
	}
	public String getOrdermonth() {
		return ordermonth;
	}
	public void setOrdermonth(String ordermonth) {
		this.ordermonth = ordermonth;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getOrderStage() {
		return orderStage;
	}
	public void setOrderStage(String orderStage) {
		this.orderStage = orderStage;
	}
	public String getParty() {
		return party;
	}
	public void setParty(String party) {
		this.party = party;
	}
	public int getParty_id() {
		return party_id;
	}
	public void setParty_id(int party_id) {
		this.party_id = party_id;
	}
	public String getPenaltyClause() {
		return penaltyClause;
	}
	public void setPenaltyClause(String penaltyClause) {
		this.penaltyClause = penaltyClause;
	}
	public String getPeriodsInMonths() {
		return periodsInMonths;
	}
	public void setPeriodsInMonths(String periodsInMonths) {
		this.periodsInMonths = periodsInMonths;
	}
	public String getPk_charge_id() {
		return pk_charge_id;
	}
	public void setPk_charge_id(String pk_charge_id) {
		this.pk_charge_id = pk_charge_id;
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
	public int getPre_crmorderid() {
		return pre_crmorderid;
	}
	public void setPre_crmorderid(int pre_crmorderid) {
		this.pre_crmorderid = pre_crmorderid;
	}
	public String getPrimaryLocation() {
		return primaryLocation;
	}
	public void setPrimaryLocation(String primaryLocation) {
		this.primaryLocation = primaryLocation;
	}
	public String getPrjmngname() {
		return prjmngname;
	}
	public void setPrjmngname(String prjmngname) {
		this.prjmngname = prjmngname;
	}
	public String getProdAlisName() {
		return prodAlisName;
	}
	public void setProdAlisName(String prodAlisName) {
		this.prodAlisName = prodAlisName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getRate_code() {
		return rate_code;
	}
	public void setRate_code(String rate_code) {
		this.rate_code = rate_code;
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
	public String getRequest_rec_date() {
		return request_rec_date;
	}
	public void setRequest_rec_date(String request_rec_date) {
		this.request_rec_date = request_rec_date;
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
	public String getServiceOrderType() {
		return serviceOrderType;
	}
	public void setServiceOrderType(String serviceOrderType) {
		this.serviceOrderType = serviceOrderType;
	}
	public String getServiceStage() {
		return serviceStage;
	}
	public void setServiceStage(String serviceStage) {
		this.serviceStage = serviceStage;
	}
	public String getSrno() {
		return srno;
	}
	public void setSrno(String srno) {
		this.srno = srno;
	}
	public String getStandard_reason() {
		return standard_reason;
	}
	public void setStandard_reason(String standard_reason) {
		this.standard_reason = standard_reason;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public int getStartDateDays() {
		return startDateDays;
	}
	public void setStartDateDays(int startDateDays) {
		this.startDateDays = startDateDays;
	}
	public String getStartDateLogic() {
		return startDateLogic;
	}
	public void setStartDateLogic(String startDateLogic) {
		this.startDateLogic = startDateLogic;
	}
	public int getStartDateMonth() {
		return startDateMonth;
	}
	public void setStartDateMonth(int startDateMonth) {
		this.startDateMonth = startDateMonth;
	}
	public String getStore() {
		return store;
	}
	public void setStore(String store) {
		this.store = store;
	}
	public String getSub_linename() {
		return sub_linename;
	}
	public void setSub_linename(String sub_linename) {
		this.sub_linename = sub_linename;
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
	public String getType_sale() {
		return type_sale;
	}
	public void setType_sale(String type_sale) {
		this.type_sale = type_sale;
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
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getOrdersubtype() {
		return ordersubtype;
	}
	public void setOrdersubtype(String ordersubtype) {
		this.ordersubtype = ordersubtype;
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
	public String getChargeinfoID() {
		return ChargeinfoID;
	}
	public void setChargeinfoID(String chargeinfoID) {
		ChargeinfoID = chargeinfoID;
	}
	public String getPrimarylocation() {
		return Primarylocation;
	}
	public void setPrimarylocation(String primarylocation) {
		Primarylocation = primarylocation;
	}
	public int getIsUsage() {
		return isUsage;
	}
	public void setIsUsage(int isUsage) {
		this.isUsage = isUsage;
	}
	public int getCreditPeriod() {
		return creditPeriod;
	}
	public void setCreditPeriod(int creditPeriod) {
		this.creditPeriod = creditPeriod;
	}
	public String getBandwidth_att() {
		return bandwidth_att;
	}
	public void setBandwidth_att(String bandwidth_att) {
		this.bandwidth_att = bandwidth_att;
	}
	public int getLineno() {
		return lineno;
	}
	public void setLineno(int lineno) {
		this.lineno = lineno;
	}
	public String getMocn_no() {
		return mocn_no;
	}
	public void setMocn_no(String mocn_no) {
		this.mocn_no = mocn_no;
	}
	public String getOrderStageDescription() {
		return orderStageDescription;
	}
	public void setOrderStageDescription(String orderStageDescription) {
		this.orderStageDescription = orderStageDescription;
	}
	public String getAdvance() {
		return advance;
	}
	public void setAdvance(String advance) {
		this.advance = advance;
	}
	public long getAmt() {
		return amt;
	}
	public void setAmt(long amt) {
		this.amt = amt;
	}
	public String getBillingLevelName() {
		return billingLevelName;
	}
	public void setBillingLevelName(String billingLevelName) {
		this.billingLevelName = billingLevelName;
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
	public String getCrmAccountNoString() {
		return crmAccountNoString;
	}
	public void setCrmAccountNoString(String crmAccountNoString) {
		this.crmAccountNoString = crmAccountNoString;
	}
	public String getM6_prod_id() {
		return m6_prod_id;
	}
	public void setM6_prod_id(String m6_prod_id) {
		this.m6_prod_id = m6_prod_id;
	}
	public String getM6cktid() {
		return m6cktid;
	}
	public void setM6cktid(String m6cktid) {
		this.m6cktid = m6cktid;
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
	public double getTotalAmountIncludingCurrent() {
		return totalAmountIncludingCurrent;
	}
	public void setTotalAmountIncludingCurrent(double totalAmountIncludingCurrent) {
		this.totalAmountIncludingCurrent = totalAmountIncludingCurrent;
	}
	public long getFx_internal_acc_id() {
		return fx_internal_acc_id;
	}
	public void setFx_internal_acc_id(long fx_internal_acc_id) {
		this.fx_internal_acc_id = fx_internal_acc_id;
	}
	public long getSourcePartyID() {
		return sourcePartyID;
	}
	public void setSourcePartyID(long sourcePartyID) {
		this.sourcePartyID = sourcePartyID;
	}
	public String getBusiness_serial_no() {
		return business_serial_no;
	}
	public void setBusiness_serial_no(String business_serial_no) {
		this.business_serial_no = business_serial_no;
	}
	public int getChargeTypeID() {
		return chargeTypeID;
	}
	public void setChargeTypeID(int chargeTypeID) {
		this.chargeTypeID = chargeTypeID;
	}
	public String getInstallation_addressaa1() {
		return installation_addressaa1;
	}
	public void setInstallation_addressaa1(String installation_addressaa1) {
		this.installation_addressaa1 = installation_addressaa1;
	}
	public ComponentsDto getComponentDto() {
		return componentDto;
	}
	public void setComponentDto(ComponentsDto componentDto) {
		this.componentDto = componentDto;
	}
	public String getTokenNO() {
		return tokenNO;
	}
	public void setTokenNO(String tokenNO) {
		this.tokenNO = tokenNO;
	}
	public String getTokenno() {
		return Tokenno;
	}
	public void setTokenno(String tokenno) {
		Tokenno = tokenno;
	}
	public String getCus_segment() {
		return cus_segment;
	}
	public void setCus_segment(String cus_segment) {
		this.cus_segment = cus_segment;
	}
	public int getCustomerSegmentId() {
		return customerSegmentId;
	}
	public void setCustomerSegmentId(int customerSegmentId) {
		this.customerSegmentId = customerSegmentId;
	}
	
	
	
	

}
