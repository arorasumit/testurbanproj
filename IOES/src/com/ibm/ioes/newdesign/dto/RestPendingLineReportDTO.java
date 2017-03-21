// [001] Shourya Mishra 12-Dec-13 New Field are added 
// [002] Neha Maheshwari 14-Apr-15     CSR-20150212-R1-021088  Addition of Column in Active and Pending Line Item Reports
//[003]   Neha Maheshwari 10-Jun-2015 20141219-R2-020936 Billing Efficiency Program Reports
package com.ibm.ioes.newdesign.dto;

import com.ibm.ioes.forms.ComponentsDto;
import com.ibm.ioes.utilities.PagingSorting;

public class RestPendingLineReportDTO {

	PagingSorting pagingSorting = new PagingSorting();
	private int crmAccountNo;
	private int partyNo;
	private int isUsage;
	private ComponentsDto componentDto;
	private long fx_internal_acc_id;
	private String copcApproveDate;
	 private String cancelBy; 
	 private String creditPeriodName;
	 private String m6_prod_id;
	 private int packageID;
	 private String packageName;
	 private int componentID;
	 private int componentInfoID;
	 private String componentName;
	 private String fx_external_acc_id;
	private String partyName;
	private String projectManager;
	private String accountManager;
	//private String orderDate;
	private String dispatchAddress1;
	private String chargeEndDate;
	private String poDate;
	private String amapprovaldate;
	private String copcapprovaldate;
	private String orderDate;
	private String poReceiveDate;
	private String custPoDate;
	private String canceldate;
	private String rate_code;
	private String last_mile_media;
	private String last_mile_remarks;
	private String link_type;
	private String indicative_value;
	private String uom;
	private String last_mile_provider;
	//private String location_from;
	//private String location_to;
	private String hardware_flag;
	//private String chargeTypeID;
	private int chargeTypeID;
	private String charge_status;
	private String billingMode;
	private String formAvailable;
	private String chargeName;
	private int poNumber;
	private String custPoDetailNo;
	private String cancelflag;
	private String primarylocation;
	private String seclocation;
	private String billing_address;
	private String fx_status;
	private String fx_sd_status;
	private String fx_ed_status;
	private String child_ac_fxstatus;
	private String rfs_date;
	private String opms_act_id;
	private String productName;
	private String subProductName;
	private int lineno;
	private String frequencyName;
	private String mocn_no;
	private String crm_productname;
	private long billingLevelNo;
	private String logicalCircuitId;
	//private String customer_logicalSINumber;
	private String serviceName;
	private String linename;
	private int customer_logicalSINumber;
	private String ckt_id;
	private String location_from;
	private String location_to;
	private String serviceStage;
	private String bisource;
	private String hardwaretypeName;
	private String tokenno;
	private String billingLevel;
	private String saleNature;
	private String demoType;
	private String saleType;
	private String billing_bandwidth;
	private String billing_uom;
	private String distance;
	private String ratio;
	private String stageName;
	private String annitation;
	private String child_act_no;
	private String cancelServiceReason;
	private String chargeTypeName;
	private String order_type;
	private String ordersubtype;
	private String verticalDetails;
	private String regionName;
	private int creditPeriod;
	private String currencyName;
	private String entity;
	private String billingTypeName;
	private String billingFormatName;
	private String lcompanyname;
	private String taxation;
	private String storename;
	private String bill_period;
	private int orderNumber;
	private double chargeAmount;
	private double lineamt;
	private long poAmountSum;
	private int contractPeriod;
	private double totalAmountIncludingCurrent;
	private int party_id;
	private int accountID;
	private String m6cktid;
	private double cust_total_poamt;
	private int serviceId;
	private int serviceproductid=0;
	private String chargeinfoID;
	private double annual_rate;
	private String toDate;
	private String fromDate;
	private String subChangeTypeName;
	private String pmapprovaldate;
	private String address1;
	private String business_serial_no;
	private String lb_service_id;
	private String lb_pk_charge_id;
	//001] Start 
	private String oldLsi;
	//<!--GlobalDataBillingEfficiency BFR4  -->
	private String taxExcemption_Reason; 
	
	private String opms_Account_Id;
	
	 //[001] End 
	
	private String bandWidth;
	
	//[002] Start
	private String installationFromCity;
	private String installationToCity;
	private String installationFromState;
	private String installationToState;
	private String billingContactName;
	private String billingContactNumber;
	private String billingEmailId;
	//[002] End
	//[003] Start
	private String standardReason;
	//[003] End
	
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getSubChangeTypeName() {
		return subChangeTypeName;
	}
	public void setSubChangeTypeName(String subChangeTypeName) {
		this.subChangeTypeName = subChangeTypeName;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
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
	public String getAmapprovaldate() {
		return amapprovaldate;
	}
	public void setAmapprovaldate(String amapprovaldate) {
		this.amapprovaldate = amapprovaldate;
	}
	public String getAnnitation() {
		return annitation;
	}
	public void setAnnitation(String annitation) {
		this.annitation = annitation;
	}
	public double getAnnual_rate() {
		return annual_rate;
	}
	public void setAnnual_rate(double annual_rate) {
		this.annual_rate = annual_rate;
	}
	public String getBill_period() {
		return bill_period;
	}
	public void setBill_period(String bill_period) {
		this.bill_period = bill_period;
	}
	public String getBilling_address() {
		return billing_address;
	}
	public void setBilling_address(String billing_address) {
		this.billing_address = billing_address;
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
	public String getBillingFormatName() {
		return billingFormatName;
	}
	public void setBillingFormatName(String billingFormatName) {
		this.billingFormatName = billingFormatName;
	}
	public String getBillingLevel() {
		return billingLevel;
	}
	public void setBillingLevel(String billingLevel) {
		this.billingLevel = billingLevel;
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
	public String getBillingTypeName() {
		return billingTypeName;
	}
	public void setBillingTypeName(String billingTypeName) {
		this.billingTypeName = billingTypeName;
	}
	public String getBisource() {
		return bisource;
	}
	public void setBisource(String bisource) {
		this.bisource = bisource;
	}
	public String getCanceldate() {
		return canceldate;
	}
	public void setCanceldate(String canceldate) {
		this.canceldate = canceldate;
	}
	public String getCancelflag() {
		return cancelflag;
	}
	public void setCancelflag(String cancelflag) {
		this.cancelflag = cancelflag;
	}
	public String getCancelServiceReason() {
		return cancelServiceReason;
	}
	public void setCancelServiceReason(String cancelServiceReason) {
		this.cancelServiceReason = cancelServiceReason;
	}
	public String getCharge_status() {
		return charge_status;
	}
	public void setCharge_status(String charge_status) {
		this.charge_status = charge_status;
	}
	public double getChargeAmount() {
		return chargeAmount;
	}
	public void setChargeAmount(double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}
	public String getChargeEndDate() {
		return chargeEndDate;
	}
	public void setChargeEndDate(String chargeEndDate) {
		this.chargeEndDate = chargeEndDate;
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
	public int getChargeTypeID() {
		return chargeTypeID;
	}
	public void setChargeTypeID(int chargeTypeID) {
		this.chargeTypeID = chargeTypeID;
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
	public int getContractPeriod() {
		return contractPeriod;
	}
	public void setContractPeriod(int contractPeriod) {
		this.contractPeriod = contractPeriod;
	}
	public String getCopcapprovaldate() {
		return copcapprovaldate;
	}
	public void setCopcapprovaldate(String copcapprovaldate) {
		this.copcapprovaldate = copcapprovaldate;
	}
	public int getCreditPeriod() {
		return creditPeriod;
	}
	public void setCreditPeriod(int creditPeriod) {
		this.creditPeriod = creditPeriod;
	}
	public String getCrm_productname() {
		return crm_productname;
	}
	public void setCrm_productname(String crm_productname) {
		this.crm_productname = crm_productname;
	}
	public int getCrmAccountNo() {
		return crmAccountNo;
	}
	public void setCrmAccountNo(int crmAccountNo) {
		this.crmAccountNo = crmAccountNo;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public double getCust_total_poamt() {
		return cust_total_poamt;
	}
	public void setCust_total_poamt(double cust_total_poamt) {
		this.cust_total_poamt = cust_total_poamt;
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
	public String getDemoType() {
		return demoType;
	}
	public void setDemoType(String demoType) {
		this.demoType = demoType;
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
	public String getFormAvailable() {
		return formAvailable;
	}
	public void setFormAvailable(String formAvailable) {
		this.formAvailable = formAvailable;
	}
	public String getFrequencyName() {
		return frequencyName;
	}
	public void setFrequencyName(String frequencyName) {
		this.frequencyName = frequencyName;
	}
	public String getFx_ed_status() {
		return fx_ed_status;
	}
	public void setFx_ed_status(String fx_ed_status) {
		this.fx_ed_status = fx_ed_status;
	}
	public String getFx_sd_status() {
		return fx_sd_status;
	}
	public void setFx_sd_status(String fx_sd_status) {
		this.fx_sd_status = fx_sd_status;
	}
	public String getFx_status() {
		return fx_status;
	}
	public void setFx_status(String fx_status) {
		this.fx_status = fx_status;
	}
	public String getHardware_flag() {
		return hardware_flag;
	}
	public void setHardware_flag(String hardware_flag) {
		this.hardware_flag = hardware_flag;
	}
	public String getHardwaretypeName() {
		return hardwaretypeName;
	}
	public void setHardwaretypeName(String hardwaretypeName) {
		this.hardwaretypeName = hardwaretypeName;
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
	public String getLcompanyname() {
		return lcompanyname;
	}
	public void setLcompanyname(String lcompanyname) {
		this.lcompanyname = lcompanyname;
	}
	public double getLineamt() {
		return lineamt;
	}
	public void setLineamt(double lineamt) {
		this.lineamt = lineamt;
	}
	public String getLinename() {
		return linename;
	}
	public void setLinename(String linename) {
		this.linename = linename;
	}
	public int getLineno() {
		return lineno;
	}
	public void setLineno(int lineno) {
		this.lineno = lineno;
	}
	public String getLink_type() {
		return link_type;
	}
	public void setLink_type(String link_type) {
		this.link_type = link_type;
	}
	public String getLocation_from() {
		return location_from;
	}
	public void setLocation_from(String location_from) {
		this.location_from = location_from;
	}
	public String getLocation_to() {
		return location_to;
	}
	public void setLocation_to(String location_to) {
		this.location_to = location_to;
	}
	public String getLogicalCircuitId() {
		return logicalCircuitId;
	}
	public void setLogicalCircuitId(String logicalCircuitId) {
		this.logicalCircuitId = logicalCircuitId;
	}
	public String getM6cktid() {
		return m6cktid;
	}
	public void setM6cktid(String m6cktid) {
		this.m6cktid = m6cktid;
	}
	public String getMocn_no() {
		return mocn_no;
	}
	public void setMocn_no(String mocn_no) {
		this.mocn_no = mocn_no;
	}
	public String getOpms_act_id() {
		return opms_act_id;
	}
	public void setOpms_act_id(String opms_act_id) {
		this.opms_act_id = opms_act_id;
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
	public int getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
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
	public int getParty_id() {
		return party_id;
	}
	public void setParty_id(int party_id) {
		this.party_id = party_id;
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
	public long getPoAmountSum() {
		return poAmountSum;
	}
	public void setPoAmountSum(long poAmountSum) {
		this.poAmountSum = poAmountSum;
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
	public String getPoReceiveDate() {
		return poReceiveDate;
	}
	public void setPoReceiveDate(String poReceiveDate) {
		this.poReceiveDate = poReceiveDate;
	}
	public String getPrimarylocation() {
		return primarylocation;
	}
	public void setPrimarylocation(String primarylocation) {
		this.primarylocation = primarylocation;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProjectManager() {
		return projectManager;
	}
	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
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
	public String getSaleNature() {
		return saleNature;
	}
	public void setSaleNature(String saleNature) {
		this.saleNature = saleNature;
	}
	public String getSaleType() {
		return saleType;
	}
	public void setSaleType(String saleType) {
		this.saleType = saleType;
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
	public int getServiceproductid() {
		return serviceproductid;
	}
	public void setServiceproductid(int serviceproductid) {
		this.serviceproductid = serviceproductid;
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
	public String getTokenno() {
		return tokenno;
	}
	public void setTokenno(String tokenno) {
		this.tokenno = tokenno;
	}
	public double getTotalAmountIncludingCurrent() {
		return totalAmountIncludingCurrent;
	}
	public void setTotalAmountIncludingCurrent(double totalAmountIncludingCurrent) {
		this.totalAmountIncludingCurrent = totalAmountIncludingCurrent;
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
	public String getPmapprovaldate() {
		return pmapprovaldate;
	}
	public void setPmapprovaldate(String pmapprovaldate) {
		this.pmapprovaldate = pmapprovaldate;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getBusiness_serial_no() {
		return business_serial_no;
	}
	public void setBusiness_serial_no(String business_serial_no) {
		this.business_serial_no = business_serial_no;
	}
	public String getLb_pk_charge_id() {
		return lb_pk_charge_id;
	}
	public void setLb_pk_charge_id(String lb_pk_charge_id) {
		this.lb_pk_charge_id = lb_pk_charge_id;
	}
	public String getLb_service_id() {
		return lb_service_id;
	}
	public void setLb_service_id(String lb_service_id) {
		this.lb_service_id = lb_service_id;
	}
	public int getIsUsage() {
		return isUsage;
	}
	public void setIsUsage(int isUsage) {
		this.isUsage = isUsage;
	}
	public String getCancelBy() {
		return cancelBy;
	}
	public void setCancelBy(String cancelBy) {
		this.cancelBy = cancelBy;
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
	public String getCreditPeriodName() {
		return creditPeriodName;
	}
	public void setCreditPeriodName(String creditPeriodName) {
		this.creditPeriodName = creditPeriodName;
	}
	public String getFx_external_acc_id() {
		return fx_external_acc_id;
	}
	public void setFx_external_acc_id(String fx_external_acc_id) {
		this.fx_external_acc_id = fx_external_acc_id;
	}
	public String getM6_prod_id() {
		return m6_prod_id;
	}
	public void setM6_prod_id(String m6_prod_id) {
		this.m6_prod_id = m6_prod_id;
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
	public long getFx_internal_acc_id() {
		return fx_internal_acc_id;
	}
	public void setFx_internal_acc_id(long fx_internal_acc_id) {
		this.fx_internal_acc_id = fx_internal_acc_id;
	}
	public String getCopcApproveDate() {
		return copcApproveDate;
	}
	public void setCopcApproveDate(String copcApproveDate) {
		this.copcApproveDate = copcApproveDate;
	}
	public ComponentsDto getComponentDto() {
		return componentDto;
	}
	public void setComponentDto(ComponentsDto componentDto) {
		this.componentDto = componentDto;
	}
	public String getBandWidth() {
		return bandWidth;
	}
	public void setBandWidth(String bandWidth) {
		this.bandWidth = bandWidth;
	}
public String getOldLsi() {
		return oldLsi;
	}
	public void setOldLsi(String oldLsi) {
		this.oldLsi = oldLsi;
	}
	public String getOpms_Account_Id() {
		return opms_Account_Id;
	}
	public void setOpms_Account_Id(String opms_Account_Id) {
		this.opms_Account_Id = opms_Account_Id;
	}
	public String getTaxExcemption_Reason() {
		return taxExcemption_Reason;
	}
	public void setTaxExcemption_Reason(String taxExemption_Reason) {
		this.taxExcemption_Reason = taxExemption_Reason;
	}
	
	//[002] Start
	public String getInstallationFromCity() {
		return installationFromCity;
	}
	public void setInstallationFromCity(String installationFromCity) {
		this.installationFromCity = installationFromCity;
	}
	public String getInstallationToCity() {
		return installationToCity;
	}
	public void setInstallationToCity(String installationToCity) {
		this.installationToCity = installationToCity;
	}
	public String getInstallationFromState() {
		return installationFromState;
	}
	public void setInstallationFromState(String installationFromState) {
		this.installationFromState = installationFromState;
	}
	public String getInstallationToState() {
		return installationToState;
	}
	public void setInstallationToState(String installationToState) {
		this.installationToState = installationToState;
	}
	public String getBillingContactName() {
		return billingContactName;
	}
	public void setBillingContactName(String billingContactName) {
		this.billingContactName = billingContactName;
	}
	public String getBillingContactNumber() {
		return billingContactNumber;
	}
	public void setBillingContactNumber(String billingContactNumber) {
		this.billingContactNumber = billingContactNumber;
	}
	public String getBillingEmailId() {
		return billingEmailId;
	}
	public void setBillingEmailId(String billingEmailId) {
		this.billingEmailId = billingEmailId;
	}
	//[003] Start
	public String getStandardReason() {
		return standardReason;
	}
	public void setStandardReason(String standardReason) {
		this.standardReason = standardReason;
	}
	//[003] End
	
	

}
