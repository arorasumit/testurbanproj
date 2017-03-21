package com.ibm.ioes.newdesign.dto;

import com.ibm.ioes.forms.ComponentsDto;
import com.ibm.ioes.utilities.PagingSorting;
//[01]  opportunityId added  by Lawkush in reports "ACTIVE LINE ITEMS REPORT   ","PERFORMANCE SUMMARY REPORT  ","PERFORMANCE DETAIL REPORT","ORDER DETAIL REPORT","ORDER REPORT NEW DETAIL CWN","LEPM ORDER DETAIL REPORT"
//[02] RE_LOGGED_LSI_NO,PARALLEL_UPGRADE_LSI_NO,CHARGEDISCONNECTIONSTATUS added by shourya in ORDER DETAIL NEW REPORT CWN report 
//[03] Gunjan Singla           added column customer segment description
//[04]  oldLsi added by shourya in ORDER DETAIL NEW REPORT CWN report 
//[05]  Gunjan Singla   22-Jan-15   20141113-R1-020802     ParallelUpgrade-Multiple LSI Selection
//[06]  Priya Gupta		27-Jan-15	20150508-R2-021322   Added 3 columns 
//[07]  Gunjan Singla	4-May-15    20160418-R1-022266  Added columns 
public class OrderReportNewDetailCwnDTO {
	PagingSorting pagingSorting = new PagingSorting();
	private String toDate;
	 private String fromDate;
	 private String fx_external_acc_id;
	 
	 private int fxInternalId;
	 private String child_account_creation_status;
	 private int packageID;
	 private String packageName;
	 private int componentID;
	 private int componentInfoID;
	 private String componentName;
	 private String lastApprovalRemarks;
	 private ComponentsDto componentDto;
	 private int isUsage;
	private String partyName;
	private String toCopcApprovedDate;
	private String fromCopcApprovedDate; 
	private String Primarylocation;
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
	private int chargeInfoID;
	private int serviceproductid = 0;
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
	private String osp;
	private String chargeRemarks;
	//	[01] Start  	 
	 private String opportunityId;	
	 //[02] shourya start
	 private String RE_LOGGED_LSI_NO;
	 //[05] start
	private String PARALLEL_UPGRADE_LSI_NO;
	 private String parallellUpgradeLSINo1;
	 private String parallellUpgradeLSINo2;
	 private String parallellUpgradeLSINo3;
	 //[05] start
	private String CHARGEDISCONNECTIONSTATUS;
	private String  subchange_type;
	private String  fxAccountExternalId;
//	shourya end 
	//[04] Start 
	private String oldLsi;
//	[04] End
	//lawkush start
	private int partyNo;
	//lawkush End
	private String obValue;	
	private String obValueLastUpdateDate;
	//<!--GlobalDataBillingEfficiency BFR7  -->
	private String taxExcemption_Reason;
	//[06] Start
	private String salesForceOpportunityNumber;
	private String networkType;
	private String channelPartner;
	private String partnerId;
	//[06] End
	//[07] start
	private String fieldEngineer;
	private String partnerCode;
	//[07] end
	
	//NANCY START
	private String ePCNNo;
	//NANCY END
	private String billingTriggerCreateDate;
    
		//RAHEEM START
	private String isDifferential;
	private long linkedOldCharge;
	//RAHEEM END
	
	
	public long getLinkedOldCharge() {
		return linkedOldCharge;
	}
	public void setLinkedOldCharge(long linkedOldCharge) {
		this.linkedOldCharge = linkedOldCharge;
	}
	public String getIsDifferential() {
		return isDifferential;
	}
	public void setIsDifferential(String isDifferential) {
		this.isDifferential = isDifferential;
	}
	
	public String getObValue() {
		return obValue;
	}
	public void setObValue(String obValue) {
		this.obValue = obValue;
	}
	public String getObValueLastUpdateDate() {
		return obValueLastUpdateDate;
	}
	public void setObValueLastUpdateDate(String obValueLastUpdateDate) {
		this.obValueLastUpdateDate = obValueLastUpdateDate;
	}
	private String custSeg_Description;
	
	public String getCustSeg_Description() {
		return custSeg_Description;
	}
	public void setCustSeg_Description(String custSeg_Description) {
		this.custSeg_Description = custSeg_Description;
	}
	
	public int getPartyNo() {
		return partyNo;
	}
	public void setPartyNo(int partyNo) {
		this.partyNo = partyNo;
	}
	
	public String getRE_LOGGED_LSI_NO() {
		return RE_LOGGED_LSI_NO;
	}
	public void setRE_LOGGED_LSI_NO(String re_logged_lsi_no) {
		RE_LOGGED_LSI_NO = re_logged_lsi_no;
	}
	public String getCHARGEDISCONNECTIONSTATUS() {
		return CHARGEDISCONNECTIONSTATUS;
	}
	public void setCHARGEDISCONNECTIONSTATUS(String chargedisconnectionstatus) {
		CHARGEDISCONNECTIONSTATUS = chargedisconnectionstatus;
	}
	
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
	public double getChargeAmount() {
		return chargeAmount;
	}
	public void setChargeAmount(double chargeAmount) {
		this.chargeAmount = chargeAmount;
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
	public String getOsp() {
		return osp;
	}
	public void setOsp(String osp) {
		this.osp = osp;
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
	public String getPrimaryLocation() {
		return primaryLocation;
	}
	public void setPrimaryLocation(String primaryLocation) {
		this.primaryLocation = primaryLocation;
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
	public int getServiceproductid() {
		return serviceproductid;
	}
	public void setServiceproductid(int serviceproductid) {
		this.serviceproductid = serviceproductid;
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
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getPrimarylocation() {
		return Primarylocation;
	}
	public void setPrimarylocation(String primarylocation) {
		Primarylocation = primarylocation;
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
	public String getFromCopcApprovedDate() {
		return fromCopcApprovedDate;
	}
	public void setFromCopcApprovedDate(String fromCopcApprovedDate) {
		this.fromCopcApprovedDate = fromCopcApprovedDate;
	}
	public String getToCopcApprovedDate() {
		return toCopcApprovedDate;
	}
	public void setToCopcApprovedDate(String toCopcApprovedDate) {
		this.toCopcApprovedDate = toCopcApprovedDate;
	}
	public int getIsUsage() {
		return isUsage;
	}
	public void setIsUsage(int isUsage) {
		this.isUsage = isUsage;
	}
	public ComponentsDto getComponentDto() {
		return componentDto;
	}
	public void setComponentDto(ComponentsDto componentDto) {
		this.componentDto = componentDto;
	}
	public String getLastApprovalRemarks() {
		return lastApprovalRemarks;
	}
	public void setLastApprovalRemarks(String lastApprovalRemarks) {
		this.lastApprovalRemarks = lastApprovalRemarks;
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
	public String getChargeRemarks() {
		return chargeRemarks;
	}
	public void setChargeRemarks(String chargeRemarks) {
		this.chargeRemarks = chargeRemarks;
	}
	public String getSubchange_type() {
		return subchange_type;
	}
	public void setSubchange_type(String subchange_type) {
		this.subchange_type = subchange_type;
	}
	public String getOldLsi() {
		return oldLsi;
	}
	public void setOldLsi(String oldLsi) {
		this.oldLsi = oldLsi;
	}
	public String getFxAccountExternalId() {
		return fxAccountExternalId;
	}
	public void setFxAccountExternalId(String fxAccountExternalId) {
		this.fxAccountExternalId = fxAccountExternalId;
	}
	public String getTaxExcemption_Reason() {
		return taxExcemption_Reason;
	}
	public void setTaxExcemption_Reason(String taxExcemption_Reason) {
		this.taxExcemption_Reason = taxExcemption_Reason;
	}
	
	/*public String getPARALLEL_UPGRADE_LSI_NO() {
		return PARALLEL_UPGRADE_LSI_NO;
	}
	public void setPARALLEL_UPGRADE_LSI_NO(String pARALLEL_UPGRADE_LSI_NO) {
		PARALLEL_UPGRADE_LSI_NO = pARALLEL_UPGRADE_LSI_NO;
	}*/
	//[05] start
	public String getPARALLEL_UPGRADE_LSI_NO() {
		return PARALLEL_UPGRADE_LSI_NO;
	}
	public void setPARALLEL_UPGRADE_LSI_NO(String pARALLEL_UPGRADE_LSI_NO) {
		PARALLEL_UPGRADE_LSI_NO = pARALLEL_UPGRADE_LSI_NO;
	}
	public String getParallellUpgradeLSINo1() {
		return parallellUpgradeLSINo1;
	}
	public void setParallellUpgradeLSINo1(String parallellUpgradeLSINo1) {
		this.parallellUpgradeLSINo1 = parallellUpgradeLSINo1;
	}
	public String getParallellUpgradeLSINo2() {
		return parallellUpgradeLSINo2;
	}
	public void setParallellUpgradeLSINo2(String parallellUpgradeLSINo2) {
		this.parallellUpgradeLSINo2 = parallellUpgradeLSINo2;
	}
	public String getParallellUpgradeLSINo3() {
		return parallellUpgradeLSINo3;
	}
	public void setParallellUpgradeLSINo3(String parallellUpgradeLSINo3) {
		this.parallellUpgradeLSINo3 = parallellUpgradeLSINo3;
	}
	
	//[05] end
	//[06] Start
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
	
	//[07] start
	
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
	//[07] end
     
	//nancy start
	public String getePCNNo() {
		return ePCNNo;
	}
	public void setePCNNo(String ePCNNo) {
		this.ePCNNo = ePCNNo;
	}
	// nancy end
	public String getBillingTriggerCreateDate() {
		return billingTriggerCreateDate;
	}
	public void setBillingTriggerCreateDate(String billingTriggerCreateDate) {
		this.billingTriggerCreateDate = billingTriggerCreateDate;
	}
}
