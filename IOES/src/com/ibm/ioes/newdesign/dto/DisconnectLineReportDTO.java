// [001] Shourya Mishra 12-Dec-13 New Field are added 
package com.ibm.ioes.newdesign.dto;

import com.ibm.ioes.forms.ComponentsDto;
import com.ibm.ioes.utilities.PagingSorting;

public class DisconnectLineReportDTO {
	
	PagingSorting pagingSorting = new PagingSorting();
	private int partyNo;
	private int isUsage;
	private int accountID;
	private String poRecieveDate;
	private String cust_name;
    private String verticalDetails;
    private String fx_external_acc_id;
    private String creditPeriodName;
    private int fxInternalId;
    private String child_account_creation_status;
    private int packageID;
    private String packageName;
    private int componentID;
    private int componentInfoID;
    private String componentName;
    private ComponentsDto componentDto;
	private String regionName;
	private int orderNumber;
	private String order_type;
	private int m6OrderNo;
	private String orderNo;
	private String serviceName;
	private int customer_logicalSINumber;
	private String linename;
	private String ordersubtype;
	private String ckt_id;
	private String bandwaidth;
	private String billing_bandwidth;
	private String billing_uom;
	private String distance;
	private String ratio;
	private String location_from;
	private String location_to;
	private String primarylocation;
	private String seclocation;
	private String productName;
	private String subProductName;
	private String entity;
	private String lcompanyname;
	private String currencyName;
	private int creditPeriod;
	private String billingTypeName;
	private String billingFormatName;
	private String billingLevel;
	private long billingLevelNo;
	private String taxation;
	private String hardwaretypeName;
	private String hardware_flag;
	private String storename;
	private String saleType;
	private String bill_period;
	private String billingMode;
	private int poNumber;
	private String poDate;
	private int contractPeriod;
	private double totalAmountIncludingCurrent;
	private String poReceiveDate;
	private String custPoDetailNo;
	private String custPoDate;
	private double cust_total_poamt;
	private String chargeTypeName;
	private String chargeName;
	private String chargeEndDate;
	private double chargeAmount;
	
	private String annitation;
	private String locDate;
	private String locno;
	private String billing_trigger_date;
	private String billingtrigger_createdate;
	private String pmapprovaldate;
	private String billing_Trigger_Flag;
	private String tokenno;
	private String fx_sd_status;
	private String fx_status;
	private String business_serial_no;
	private String opms_act_id;
	private int serviceId;
	private int pre_crmorderid;
	private String disconnection_remarks;
	private String stageName;
	private String serviceStage;
	private String neworder_remarks;
	private String copcapprovaldate;
	private String request_rec_date;
	private String standard_reason;
	private String orderDate;
	private String ordermonth;
	private String toDate;
	private String fromDate;
	private String uom;
	private long Lineamt;
	private int lineno;
	private String child_account_number;
	private String line_no;
	private double frequencyAmount;
	
	public double getFrequencyAmount() {
		return frequencyAmount;
	}
	public void setFrequencyAmount(double frequencyAmount) {
		this.frequencyAmount = frequencyAmount;
	}
	public String getLine_no() {
		return line_no;
	}
	public void setLine_no(String line_no) {
		this.line_no = line_no;
	}
	private String PmApproveDate;
	

	 //[001] Start 
	private String oldLsi;
	private int business_serial_n;
	private String opms_Account_Id;
	//[001] End 
	
	
	public int getAccountID() {
		return accountID;
	}
	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}
	public String getAnnitation() {
		return annitation;
	}
	public void setAnnitation(String annitation) {
		this.annitation = annitation;
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
	public String getBilling_trigger_date() {
		return billing_trigger_date;
	}
	public void setBilling_trigger_date(String billing_trigger_date) {
		this.billing_trigger_date = billing_trigger_date;
	}
	public String getBilling_Trigger_Flag() {
		return billing_Trigger_Flag;
	}
	public void setBilling_Trigger_Flag(String billing_Trigger_Flag) {
		this.billing_Trigger_Flag = billing_Trigger_Flag;
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
	public String getBillingtrigger_createdate() {
		return billingtrigger_createdate;
	}
	public void setBillingtrigger_createdate(String billingtrigger_createdate) {
		this.billingtrigger_createdate = billingtrigger_createdate;
	}
	public String getBillingTypeName() {
		return billingTypeName;
	}
	public void setBillingTypeName(String billingTypeName) {
		this.billingTypeName = billingTypeName;
	}
	public String getBusiness_serial_no() {
		return business_serial_no;
	}
	public void setBusiness_serial_no(String business_serial_no) {
		this.business_serial_no = business_serial_no;
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
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public String getCust_name() {
		return cust_name;
	}
	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
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
	public String getDisconnection_remarks() {
		return disconnection_remarks;
	}
	public void setDisconnection_remarks(String disconnection_remarks) {
		this.disconnection_remarks = disconnection_remarks;
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
	public String getLcompanyname() {
		return lcompanyname;
	}
	public void setLcompanyname(String lcompanyname) {
		this.lcompanyname = lcompanyname;
	}
	public String getLinename() {
		return linename;
	}
	public void setLinename(String linename) {
		this.linename = linename;
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
	public int getM6OrderNo() {
		return m6OrderNo;
	}
	public void setM6OrderNo(int orderNo) {
		m6OrderNo = orderNo;
	}
	public String getNeworder_remarks() {
		return neworder_remarks;
	}
	public void setNeworder_remarks(String neworder_remarks) {
		this.neworder_remarks = neworder_remarks;
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
	public int getPartyNo() {
		return partyNo;
	}
	public void setPartyNo(int partyNo) {
		this.partyNo = partyNo;
	}
	public String getPmapprovaldate() {
		return pmapprovaldate;
	}
	public void setPmapprovaldate(String pmapprovaldate) {
		this.pmapprovaldate = pmapprovaldate;
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
	public int getPre_crmorderid() {
		return pre_crmorderid;
	}
	public void setPre_crmorderid(int pre_crmorderid) {
		this.pre_crmorderid = pre_crmorderid;
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
	public String getRequest_rec_date() {
		return request_rec_date;
	}
	public void setRequest_rec_date(String request_rec_date) {
		this.request_rec_date = request_rec_date;
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
	public String getStandard_reason() {
		return standard_reason;
	}
	public void setStandard_reason(String standard_reason) {
		this.standard_reason = standard_reason;
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
	public String getChild_account_number() {
		return child_account_number;
	}
	public void setChild_account_number(String child_account_number) {
		this.child_account_number = child_account_number;
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
	public String getOrdermonth() {
		return ordermonth;
	}
	public void setOrdermonth(String ordermonth) {
		this.ordermonth = ordermonth;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}
	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
	}
	public long getLineamt() {
		return Lineamt;
	}
	public void setLineamt(long lineamt) {
		this.Lineamt = lineamt;
	}
	public String getPmApproveDate() {
		return PmApproveDate;
	}
	public void setPmApproveDate(String pmApproveDate) {
		this.PmApproveDate = pmApproveDate;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public int getLineno() {
		return lineno;
	}
	public void setLineno(int lineno) {
		this.lineno = lineno;
	}
	public int getIsUsage() {
		return isUsage;
	}
	public void setIsUsage(int isUsage) {
		this.isUsage = isUsage;
	}
	public String getPoRecieveDate() {
		return poRecieveDate;
	}
	public void setPoRecieveDate(String poRecieveDate) {
		this.poRecieveDate = poRecieveDate;
	}
	public ComponentsDto getComponentDto() {
		return componentDto;
	}
	public void setComponentDto(ComponentsDto componentDto) {
		this.componentDto = componentDto;
	}
	public String getChild_account_creation_status() {
		return child_account_creation_status;
	}
	public void setChild_account_creation_status(
			String child_account_creation_status) {
		this.child_account_creation_status = child_account_creation_status;
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
	public String getFx_external_acc_id() {
		return fx_external_acc_id;
	}
	public void setFx_external_acc_id(String fx_external_acc_id) {
		this.fx_external_acc_id = fx_external_acc_id;
	}
	public int getFxInternalId() {
		return fxInternalId;
	}
	public void setFxInternalId(int fxInternalId) {
		this.fxInternalId = fxInternalId;
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
	public String getCreditPeriodName() {
		return creditPeriodName;
	}
	public void setCreditPeriodName(String creditPeriodName) {
		this.creditPeriodName = creditPeriodName;
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
	public String getOpms_Account_Id() {
		return opms_Account_Id;
	}
	public void setOpms_Account_Id(String opms_Account_Id) {
		this.opms_Account_Id = opms_Account_Id;
	}
	
	
	
	
	
}
