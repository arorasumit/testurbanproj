//[001] Pankaj 24-03-2015 added  a data member [obYear] 
//[002] Priya 25-01-2016  added 3 columns in ob value report and ob value usage report.
//[003]  Gunjan Singla  6-May-2016  20160418-R1-022266    Added columns
package com.ibm.ioes.newdesign.dto;

import com.ibm.ioes.utilities.PagingSorting;
public class OBValueReportDTO {
	PagingSorting pagingSorting = new PagingSorting();
	private String toDate;
	private String fromDate;
	private String lastApprovalRemarks;
	private String partyName;
	private String toCopcApprovedDate;
	private String fromCopcApprovedDate; 
	private String orderNo;
	private String ChargeinfoID;
	private int ServiceProductID;
	private String copcApproveDate;
	private String logicalCircuitId;
	private int serviceId;
	private String quoteNo;
	private String productName;
	private String subProductName;
	private String primaryLocation;
	private String seclocation;
	private String ordersubtype;
	private String bandwaidth;
	private double chargeAmount;
	private String prjmngname;
	private String prjmgremail;
	private String actmngname;
	private String zoneName;
	private String regionName;
	private String verticalDetails;
	private String act_category;
	private String custPoDetailNo;
	private String custPoDate;
	private String cancellationDate;
	private String orderDate;
	private String pmApproveDate;
	private String amApproveDate;
	private String demo_infrastartdate;
	private String demo_infra_enddate;
	private String rfs_date;
	private String ordercategory;
	private String orderStatus;
	private String line_desc;
	private String linename;
	private String sub_linename;
	private String chargeName;
	private int chargeInfoID;
	private int serviceproductid;
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
	private double orderTotal;
	private String old_contract_period;
	private String ratio;
	private String taxation;
	private String factory_ckt_id;
	private String distance;
	private String accountManager;
	private String currencyCode;
	private String poAmount;
	private String bisource;
	private String orderType;
	private String dispatchAddress1;
	private String parent_name;
	private String opportunityId;	
	private int partyNo;
	private String parentAccountName;
	private int parentAccountNumber;
	private int primarySecondaryFlag;
	private int salesforceId;
	private String offnet;
	private String mediaType;
	private String lineType;
	private String accountName;
	private String loc_date;
	private String chargeRemarks;
	private String orderEnteredBy;
	private String copcApprovedBy;
	private String pmApprovedby;
	private String demoFlag;
	private String cancellationReason;
	private String actualOB;
	private String finalOB;
	private String rRDate;
	private String diffDays;
	private String totalDays;
	private String effectiveDays;
	private String mn;
	private String mnOBValue;
	private int componentID;
	private int oldPKId;
	private String oldAmount;
	private Long  oldPKChargeid;
	private String exchangeRate;
	private String  oldChargeAmount;
	private String obMonth;
	private String obYear;
	private String obValue;
	private String entryType;
	private String componentName;
	private String isNfa;
	private String chargeperiod;
	
	private String actualOBINR;
	private String finalOBINR;
	private String obValueINR;
	
	private String customerSegment;
	private String projectCategory;
	private String serviceRemarks;
	
	//[002] Start
	private String salesForceOpportunityNumber;
	private String networkType;
	private String channelPartner;
	private String partnerId;
	//[002] End
	//[003] start
	private String fieldEngineer;
	private String partnerCode;
	//[003] end
	
	public String getServiceRemarks() {
		return serviceRemarks;
	}
	public void setServiceRemarks(String serviceRemarks) {
		this.serviceRemarks = serviceRemarks;
	}
	public String getProjectCategory() {
		return projectCategory;
	}
	public void setProjectCategory(String projectCategory) {
		this.projectCategory = projectCategory;
	}
	public String getCustomerSegment() {
		return customerSegment;
	}
	public void setCustomerSegment(String customerSegment) {
		this.customerSegment = customerSegment;
	}
	public String getActualOBINR() {
		return actualOBINR;
	}
	public void setActualOBINR(String actualOBINR) {
		this.actualOBINR = actualOBINR;
	}
	public String getFinalOBINR() {
		return finalOBINR;
	}
	public void setFinalOBINR(String finalOBINR) {
		this.finalOBINR = finalOBINR;
	}
	public String getObValueINR() {
		return obValueINR;
	}
	public void setObValueINR(String obValueINR) {
		this.obValueINR = obValueINR;
	}
	public String getComponentName() {
		return componentName;
	}
	public void setComponentName(String componentName) {
		this.componentName = componentName;
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
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getLastApprovalRemarks() {
		return lastApprovalRemarks;
	}
	public void setLastApprovalRemarks(String lastApprovalRemarks) {
		this.lastApprovalRemarks = lastApprovalRemarks;
	}
	public String getPartyName() {
		return partyName;
	}
	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}
	public String getToCopcApprovedDate() {
		return toCopcApprovedDate;
	}
	public void setToCopcApprovedDate(String toCopcApprovedDate) {
		this.toCopcApprovedDate = toCopcApprovedDate;
	}
	public String getFromCopcApprovedDate() {
		return fromCopcApprovedDate;
	}
	public void setFromCopcApprovedDate(String fromCopcApprovedDate) {
		this.fromCopcApprovedDate = fromCopcApprovedDate;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getChargeinfoID() {
		return ChargeinfoID;
	}
	public void setChargeinfoID(String chargeinfoID) {
		ChargeinfoID = chargeinfoID;
	}
	public int getServiceProductID() {
		return ServiceProductID;
	}
	public void setServiceProductID(int serviceProductID) {
		ServiceProductID = serviceProductID;
	}
	public String getCopcApproveDate() {
		return copcApproveDate;
	}
	public void setCopcApproveDate(String copcApproveDate) {
		this.copcApproveDate = copcApproveDate;
	}
	public String getLogicalCircuitId() {
		return logicalCircuitId;
	}
	public void setLogicalCircuitId(String logicalCircuitId) {
		this.logicalCircuitId = logicalCircuitId;
	}
	public int getServiceId() {
		return serviceId;
	}
	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}
	public String getQuoteNo() {
		return quoteNo;
	}
	public void setQuoteNo(String quoteNo) {
		this.quoteNo = quoteNo;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getSubProductName() {
		return subProductName;
	}
	public void setSubProductName(String subProductName) {
		this.subProductName = subProductName;
	}
	public String getPrimaryLocation() {
		return primaryLocation;
	}
	public void setPrimaryLocation(String primaryLocation) {
		this.primaryLocation = primaryLocation;
	}
	public String getSeclocation() {
		return seclocation;
	}
	public void setSeclocation(String seclocation) {
		this.seclocation = seclocation;
	}
	public String getOrdersubtype() {
		return ordersubtype;
	}
	public void setOrdersubtype(String ordersubtype) {
		this.ordersubtype = ordersubtype;
	}
	public String getBandwaidth() {
		return bandwaidth;
	}
	public void setBandwaidth(String bandwaidth) {
		this.bandwaidth = bandwaidth;
	}
	public double getChargeAmount() {
		return chargeAmount;
	}
	public void setChargeAmount(double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}
	public String getPrjmngname() {
		return prjmngname;
	}
	public void setPrjmngname(String prjmngname) {
		this.prjmngname = prjmngname;
	}
	public String getPrjmgremail() {
		return prjmgremail;
	}
	public void setPrjmgremail(String prjmgremail) {
		this.prjmgremail = prjmgremail;
	}
	public String getActmngname() {
		return actmngname;
	}
	public void setActmngname(String actmngname) {
		this.actmngname = actmngname;
	}
	public String getZoneName() {
		return zoneName;
	}
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getVerticalDetails() {
		return verticalDetails;
	}
	public void setVerticalDetails(String verticalDetails) {
		this.verticalDetails = verticalDetails;
	}
	public String getAct_category() {
		return act_category;
	}
	public void setAct_category(String act_category) {
		this.act_category = act_category;
	}
	public String getCustPoDetailNo() {
		return custPoDetailNo;
	}
	public void setCustPoDetailNo(String custPoDetailNo) {
		this.custPoDetailNo = custPoDetailNo;
	}
	public String getCustPoDate() {
		return custPoDate;
	}
	public void setCustPoDate(String custPoDate) {
		this.custPoDate = custPoDate;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getPmApproveDate() {
		return pmApproveDate;
	}
	public void setPmApproveDate(String pmApproveDate) {
		this.pmApproveDate = pmApproveDate;
	}
	public String getAmApproveDate() {
		return amApproveDate;
	}
	public void setAmApproveDate(String amApproveDate) {
		this.amApproveDate = amApproveDate;
	}
	public String getDemo_infrastartdate() {
		return demo_infrastartdate;
	}
	public void setDemo_infrastartdate(String demo_infrastartdate) {
		this.demo_infrastartdate = demo_infrastartdate;
	}
	public String getDemo_infra_enddate() {
		return demo_infra_enddate;
	}
	public void setDemo_infra_enddate(String demo_infra_enddate) {
		this.demo_infra_enddate = demo_infra_enddate;
	}
	public String getRfs_date() {
		return rfs_date;
	}
	public void setRfs_date(String rfs_date) {
		this.rfs_date = rfs_date;
	}
	public String getOrdercategory() {
		return ordercategory;
	}
	public void setOrdercategory(String ordercategory) {
		this.ordercategory = ordercategory;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
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
	public String getSub_linename() {
		return sub_linename;
	}
	public void setSub_linename(String sub_linename) {
		this.sub_linename = sub_linename;
	}
	public String getChargeName() {
		return chargeName;
	}
	public void setChargeName(String chargeName) {
		this.chargeName = chargeName;
	}
	public int getChargeInfoID() {
		return chargeInfoID;
	}
	public void setChargeInfoID(int chargeInfoID) {
		this.chargeInfoID = chargeInfoID;
	}
	public int getServiceproductid() {
		return serviceproductid;
	}
	public void setServiceproductid(int serviceproductid) {
		this.serviceproductid = serviceproductid;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public int getAccountID() {
		return accountID;
	}
	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}
	public String getEntity() {
		return entity;
	}
	public void setEntity(String entity) {
		this.entity = entity;
	}
	public String getLicCompanyName() {
		return licCompanyName;
	}
	public void setLicCompanyName(String licCompanyName) {
		this.licCompanyName = licCompanyName;
	}
	public int getContractPeriod() {
		return contractPeriod;
	}
	public void setContractPeriod(int contractPeriod) {
		this.contractPeriod = contractPeriod;
	}
	public String getFrequencyName() {
		return frequencyName;
	}
	public void setFrequencyName(String frequencyName) {
		this.frequencyName = frequencyName;
	}
	public String getChargeTypeName() {
		return chargeTypeName;
	}
	public void setChargeTypeName(String chargeTypeName) {
		this.chargeTypeName = chargeTypeName;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
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
	public String getFrom_city() {
		return from_city;
	}
	public void setFrom_city(String from_city) {
		this.from_city = from_city;
	}
	public String getTo_city() {
		return to_city;
	}
	public void setTo_city(String to_city) {
		this.to_city = to_city;
	}
	public String getOldordertotal() {
		return oldordertotal;
	}
	public void setOldordertotal(String oldordertotal) {
		this.oldordertotal = oldordertotal;
	}
	public String getOldlineamt() {
		return oldlineamt;
	}
	public void setOldlineamt(String oldlineamt) {
		this.oldlineamt = oldlineamt;
	}
	public double getOrderTotal() {
		return orderTotal;
	}
	public void setOrderTotal(double orderTotal) {
		this.orderTotal = orderTotal;
	}
	public String getOld_contract_period() {
		return old_contract_period;
	}
	public void setOld_contract_period(String old_contract_period) {
		this.old_contract_period = old_contract_period;
	}
	public String getRatio() {
		return ratio;
	}
	public void setRatio(String ratio) {
		this.ratio = ratio;
	}
	public String getTaxation() {
		return taxation;
	}
	public void setTaxation(String taxation) {
		this.taxation = taxation;
	}
	public String getFactory_ckt_id() {
		return factory_ckt_id;
	}
	public void setFactory_ckt_id(String factory_ckt_id) {
		this.factory_ckt_id = factory_ckt_id;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getAccountManager() {
		return accountManager;
	}
	public void setAccountManager(String accountManager) {
		this.accountManager = accountManager;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getPoAmount() {
		return poAmount;
	}
	public void setPoAmount(String poAmount) {
		this.poAmount = poAmount;
	}
	public String getBisource() {
		return bisource;
	}
	public void setBisource(String bisource) {
		this.bisource = bisource;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getDispatchAddress1() {
		return dispatchAddress1;
	}
	public void setDispatchAddress1(String dispatchAddress1) {
		this.dispatchAddress1 = dispatchAddress1;
	}
	public String getParent_name() {
		return parent_name;
	}
	public void setParent_name(String parent_name) {
		this.parent_name = parent_name;
	}
	public String getOpportunityId() {
		return opportunityId;
	}
	public void setOpportunityId(String opportunityId) {
		this.opportunityId = opportunityId;
	}
	public int getPartyNo() {
		return partyNo;
	}
	public void setPartyNo(int partyNo) {
		this.partyNo = partyNo;
	}
	public String getParentAccountName() {
		return parentAccountName;
	}
	public void setParentAccountName(String parentAccountName) {
		this.parentAccountName = parentAccountName;
	}
	public int getParentAccountNumber() {
		return parentAccountNumber;
	}
	public void setParentAccountNumber(int parentAccountNumber) {
		this.parentAccountNumber = parentAccountNumber;
	}
	public int getPrimarySecondaryFlag() {
		return primarySecondaryFlag;
	}
	public void setPrimarySecondaryFlag(int primarySecondaryFlag) {
		this.primarySecondaryFlag = primarySecondaryFlag;
	}
	public int getSalesforceId() {
		return salesforceId;
	}
	public void setSalesforceId(int salesforceId) {
		this.salesforceId = salesforceId;
	}
	public String getOffnet() {
		return offnet;
	}
	public void setOffnet(String offnet) {
		this.offnet = offnet;
	}
	public String getMediaType() {
		return mediaType;
	}
	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}
	public String getLineType() {
		return lineType;
	}
	public void setLineType(String lineType) {
		this.lineType = lineType;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getLoc_date() {
		return loc_date;
	}
	public void setLoc_date(String loc_date) {
		this.loc_date = loc_date;
	}
	public String getChargeRemarks() {
		return chargeRemarks;
	}
	public void setChargeRemarks(String chargeRemarks) {
		this.chargeRemarks = chargeRemarks;
	}
	public String getOrderEnteredBy() {
		return orderEnteredBy;
	}
	public void setOrderEnteredBy(String orderEnteredBy) {
		this.orderEnteredBy = orderEnteredBy;
	}
	public String getCopcApprovedBy() {
		return copcApprovedBy;
	}
	public void setCopcApprovedBy(String copcApprovedBy) {
		this.copcApprovedBy = copcApprovedBy;
	}
	public String getPmApprovedby() {
		return pmApprovedby;
	}
	public void setPmApprovedby(String pmApprovedby) {
		this.pmApprovedby = pmApprovedby;
	}
	public String getDemoFlag() {
		return demoFlag;
	}
	public void setDemoFlag(String demoFlag) {
		this.demoFlag = demoFlag;
	}
	public String getCancellationReason() {
		return cancellationReason;
	}
	public void setCancellationReason(String cancellationReason) {
		this.cancellationReason = cancellationReason;
	}
	public String getFinalOB() {
		return finalOB;
	}
	public void setFinalOB(String finalOB) {
		this.finalOB = finalOB;
	}
	public String getrRDate() {
		return rRDate;
	}
	public void setrRDate(String rRDate) {
		this.rRDate = rRDate;
	}
	public String getDiffDays() {
		return diffDays;
	}
	public void setDiffDays(String diffDays) {
		this.diffDays = diffDays;
	}
	public String getTotalDays() {
		return totalDays;
	}
	public void setTotalDays(String totalDays) {
		this.totalDays = totalDays;
	}
	public String getEffectiveDays() {
		return effectiveDays;
	}
	public void setEffectiveDays(String effectiveDays) {
		this.effectiveDays = effectiveDays;
	}
	public String getMnOBValue() {
		return mnOBValue;
	}
	public void setMnOBValue(String mnOBValue) {
		this.mnOBValue = mnOBValue;
	}
	public int getComponentID() {
		return componentID;
	}
	public void setComponentID(int componentID) {
		this.componentID = componentID;
	}
	public int getOldPKId() {
		return oldPKId;
	}
	public void setOldPKId(int oldPKId) {
		this.oldPKId = oldPKId;
	}
	public String getOldAmount() {
		return oldAmount;
	}
	public void setOldAmount(String oldAmount) {
		this.oldAmount = oldAmount;
	}
	public String getActualOB() {
		return actualOB;
	}
	public void setActualOB(String actualOB) {
		this.actualOB = actualOB;
	}
	public String getMn() {
		return mn;
	}
	public void setMn(String mn) {
		this.mn = mn;
	}
	public Long getOldPKChargeid() {
		return oldPKChargeid;
	}
	public void setOldPKChargeid(Long oldPKChargeid) {
		this.oldPKChargeid = oldPKChargeid;
	}
	public String getOldChargeAmount() {
		return oldChargeAmount;
	}
	public void setOldChargeAmount(String oldChargeAmount) {
		this.oldChargeAmount = oldChargeAmount;
	}
	public String getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(String exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	public String getObMonth() {
		return obMonth;
	}
	public void setObMonth(String obMonth) {
		this.obMonth = obMonth;
	}
	public String getObValue() {
		return obValue;
	}
	public void setObValue(String obValue) {
		this.obValue = obValue;
	}
	public String getEntryType() {
		return entryType;
	}
	public void setEntryType(String entryType) {
		this.entryType = entryType;
	}
	public String getIsNfa() {
		return isNfa;
	}
	public void setIsNfa(String isNfa) {
		this.isNfa = isNfa;
	}
	public String getChargeperiod() {
		return chargeperiod;
	}
	public void setChargeperiod(String chargeperiod) {
		this.chargeperiod = chargeperiod;
	}
	public String getCancellationDate() {
		return cancellationDate;
	}
	public void setCancellationDate(String cancellationDate) {
		this.cancellationDate = cancellationDate;
	}
	public String getObYear() {
		return obYear;
	}
	public void setObYear(String obYear) {
		this.obYear = obYear;
	}
	//[002] Start
		public String getSalesForceOpportunityNumber() {
			return salesForceOpportunityNumber;
		}
		public void setSalesForceOpportunityNumber(String salesForceOpportunityNumber) {
			this.salesForceOpportunityNumber = salesForceOpportunityNumber;
		}
		public String getNetworkType() {
			return networkType;
		}
		public void setNetworkType(String networkType) {
			this.networkType = networkType;
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
	//[002] End
		//[003] start
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
		//[003] end
		
	
}
