package com.ibm.ioes.forms;

import java.util.ArrayList;

public class ProductCatelogDTO {
	private int tpDetailID;
	private long attMasterId;
	private String attMasterName;
	private String prodAttributeValue;
	private String expectedValue;
	private ArrayList<FieldAttibuteDTO> serviceSummary;
	private String mandatory;
	private int serviceDetailID;
	private String serviceDetDescription;
	private String serviceTypeDescription;
	private int billingInfoValue;
	private int chargeInfoValue;
	private int hardwareInfoValue;
	private int locationInfoValue;
	private int serviceInfoValue;
	private int componentInfoValue;
	private int bcpID;
	private String bcpName;
	private long taxationId;
	private String taxationName;
	private long billingLevelId;
	private String billingLevelName;
	private long billingFormatId;
	private String billingFormatName;
//	billing address
	private String baddress1;
	private String baddress2;
	private String baddress3;
	private String baddress4;
	private String bcity;
	private String bstate;
	private String bpincode;
	private String bcountry;
	private String bcontactNo;
	private String bEmailId;
	private String bemailid;
	private String bfirstname;
	private String blastname;
	// billing address
	private String toaddress;
	private String faddress;
	private String designation;
	private String lst_No;
	private String lstDate;
	private long billingTypeId;
	private String billingTypeName;
	private long creditPeriodId;
	private String creditPeriodName;
	private String licCompanyName;
	private int licCompanyID;
	private String productlevel = null;
	private int serviceProductID;
	private String link = null;
	private String storeName;
	private int storeID;
	private String chargeTypeName;
	private int chargeTypeID;
	private String frequencyName;
	private int frequencyID;
	private int factor;
	private String chargeName;
	private int chargeNameID;
	private String paddress1;
	private String paddress2;
	private String paddress3;
	private String paddress4;
	private String pcity;
	private String pstate;
	private String ppincode;
	private String pcountry;
	private String pcontactNO;
	private String prodNameAndId;
	private String pemailId;
	private String logicalCircuitId;
	private String infraOderNo;
	private String metasolvCircuitId;
	private String logicalCircuitId_new;
	private String infraOderNo_new;
	private String metasolvCircuitId_new;
	private String attributeLabel;
	private int billingInfoID;
	private int podetailID;
	private String poDate;
	private int accountID;
	private int creditPeriod;
	private String currencyCode;
	private String billingMode;
	private int entityID;
	private String billingformat;
	private int billingType;
	private String taxation;
	private int commitmentPeriod;
	private String billingLevel;
	private String penaltyClause;
	private String billingBCPId;
	private long noticePeriod;
	private Long billingLevelNo;
	private int isNfa;
	private long stdReasonId;
	private int hardwaretypeId;
	private String hardwaretypeName;
	private int formId;
	private String formName;
	private int saleNatureId;
	private String saleNatureName;
	private int saleTypeId;
	private String saleTypeName;
	private String dateLogicValue;
	private int hardwareDetailID;
	private	int selectedDispatchID;
	private	int selectedStoreID;
	private	String formAvailable;
	private	String saleNature;
	private	String hardwareType;
	private	String saleType;
	private String startDateLogic;
	private String endDateLogic;
	private String startDate = "";
	private String endDate = "";
	private int	txtStartMonth;
	private String section;
	private int txtStartDays;
	private int txtEndMonth;
	private int txtEndDays;
	private int txtExtMonth;
	private int txtExtDays;
	private String txtExtDate ="";
	private String taxPkgInstId=null;
	private Double taxRate =null;
	private String dependentOnTax=null;
	private int lstNo;
	private int poNumber;
	private String orderType;
	private String copyProductValues;
	public String dispatchContactName;
	private String prodAttributeMaxlength;
	private int configValue;
	private int  subchangetypeNetworkChangeEditable;
	private String dispatchName;
	private int isUsage;
	private int billingScenario;
	private int fxRedirectionLSI;
	private int fxRedirectionSPID;
	private String custPoDate;
	private String bcontactName;
	private int ServiceNo;
	private String createdBy;
	private String legalEntity;
	private String txtStartDateLogic;
	private String txtEndDateLogic;
	private int WarrentyMonths;
	private Double PrincipalAmount;
	private Double interestRate;
	private String lineManagedBy;
	private String line2DProduct;
	private String lineIndicativeValue;
	private String linePaymentTerms;
	private String lineRemarks;
	private int chargePeriod;
	private Double chargeAmount;
	private Double chargeFrequencyAmount;
	private int chargeStartDays;
	private int chargeStartMonths;
	private int chargeEndDays;
	private int chargeEndMonths;
	private String annotation;
	private Double annualRate;
	private String chargeRemarks;
	private String ChargeTaxRate;
	private String requestedBy;
	private String userId;
	private String spIdString;
	private String entityName;
	private String isFLE;
	private String custPONumber;
	private String poRemarks;
	private String periodInMonths;
	private String billingModeName;
	private String reasonName;
	//WARRANTY CLAUSE ADDED BY MANISHA START
	private String warrantyClause;
	//WARRANTY CLAUSE ADDED BY MANISHA end
	//  bcp details for services ADDED BY MANISHA START
	private String billingBCPIDService;
	private String billingBCPNameService;
	private String bcpAddress1Service;
	private String bcpAddress2Service;
	private String bcpAddress3Service;
	private int commercial;
	//Added for Third Party SCM by Deepak Kumar start
	private int prServiceSummary;
	private int prLineDetail;
	private int serviceID;
//	Added for Third Party SCM by Deepak Kumar end
	//satyapan OSP Tagging By nagarjuna
	private int isOSPTagging;
	private int isReqOSPTagging;
	private String txtOSPRegNo;
	private int isReqOSPRegNo;
	private String txtOSPRegDate;
	private int isReqOSPRegDate;
	//satyapan OSP Tagging By nagarjuna
	public String getBlsi_date_service() {
		return blsi_date_service;
	}

	public void setBlsi_date_service(String blsi_date_service) {
		this.blsi_date_service = blsi_date_service;
	}

	public String getBlsi_no_service() {
		return blsi_no_service;
	}

	public void setBlsi_no_service(String blsi_no_service) {
		this.blsi_no_service = blsi_no_service;
	}

	private String bcpAddress4Service;
	public String getBcpAddress1Service() {
		return bcpAddress1Service;
	}

	public void setBcpAddress1Service(String bcpAddress1Service) {
		this.bcpAddress1Service = bcpAddress1Service;
	}

	public String getBcpAddress2Service() {
		return bcpAddress2Service;
	}

	public void setBcpAddress2Service(String bcpAddress2Service) {
		this.bcpAddress2Service = bcpAddress2Service;
	}

	public String getBcpAddress3Service() {
		return bcpAddress3Service;
	}

	public void setBcpAddress3Service(String bcpAddress3Service) {
		this.bcpAddress3Service = bcpAddress3Service;
	}

	public String getBcpAddress4Service() {
		return bcpAddress4Service;
	}

	public void setBcpAddress4Service(String bcpAddress4Service) {
		this.bcpAddress4Service = bcpAddress4Service;
	}

	public String getBcpcityservice() {
		return bcpcityservice;
	}

	private String bcpcityservice;
	private String bcpstateservice;
	private String bcpcountryservice;
	private String bcontactname_service;
	private String bemailid_service;
	private String bpin_code_service;
	public String getBpin_code_service() {
		return bpin_code_service;
	}

	public void setBpin_code_service(String bpin_code_service) {
		this.bpin_code_service = bpin_code_service;
	}

	private String bdesignation_service;
	private String bcontact_no_service;
	private String blsi_date_service;
	private String blsi_no_service;
	private String bcircle_service;
	//  bcp details for services ADDED BY MANISHA END
	
	public String getBillingBCPNameService() {
		return billingBCPNameService;
	}

	public void setBillingBCPNameService(String billingBCPNameService) {
		this.billingBCPNameService = billingBCPNameService;
	}

	

	public void setBcpcityservice(String bcpcityservice) {
		this.bcpcityservice = bcpcityservice;
	}

	public String getBcpstateservice() {
		return bcpstateservice;
	}

	public void setBcpstateservice(String bcpstateservice) {
		this.bcpstateservice = bcpstateservice;
	}

	public String getBcpcountryservice() {
		return bcpcountryservice;
	}

	public void setBcpcountryservice(String bcpcountryservice) {
		this.bcpcountryservice = bcpcountryservice;
	}

	public String getBcontactname_service() {
		return bcontactname_service;
	}

	public void setBcontactname_service(String bcontactname_service) {
		this.bcontactname_service = bcontactname_service;
	}

	public String getBemailid_service() {
		return bemailid_service;
	}

	public void setBemailid_service(String bemailid_service) {
		this.bemailid_service = bemailid_service;
	}

	public String getBdesignation_service() {
		return bdesignation_service;
	}

	public void setBdesignation_service(String bdesignation_service) {
		this.bdesignation_service = bdesignation_service;
	}

	public String getBcontact_no_service() {
		return bcontact_no_service;
	}

	public void setBcontact_no_service(String bcontact_no_service) {
		this.bcontact_no_service = bcontact_no_service;
	}

	public String getBcircle_service() {
		return bcircle_service;
	}

	public void setBcircle_service(String bcircle_service) {
		this.bcircle_service = bcircle_service;
	}

	public String getBillingBCPIDService() {
		return billingBCPIDService;
	}

	public void setBillingBCPIDService(String billingBCPIDService) {
		this.billingBCPIDService = billingBCPIDService;
	}

	public String getWarrantyClause() {
		return warrantyClause;
	}

	public void setWarrantyClause(String warrantyClause) {
		this.warrantyClause = warrantyClause;
	}
	public String getSpIdString() {
		return spIdString;
	}

	public void setSpIdString(String spIdString) {
		this.spIdString = spIdString;
	}

	public String getReasonName() {
		return reasonName;
	}

	public void setReasonName(String reasonName) {
		this.reasonName = reasonName;
	}

	public String getBillingModeName() {
		return billingModeName;
	}

	public void setBillingModeName(String billingModeName) {
		this.billingModeName = billingModeName;
	}

	public String getPeriodInMonths() {
		return periodInMonths;
	}

	public void setPeriodInMonths(String periodInMonths) {
		this.periodInMonths = periodInMonths;
	}

	public String getPoRemarks() {
		return poRemarks;
	}

	public void setPoRemarks(String poRemarks) {
		this.poRemarks = poRemarks;
	}

	public String getCustPONumber() {
		return custPONumber;
	}

	public void setCustPONumber(String custPONumber) {
		this.custPONumber = custPONumber;
	}

	public String getIsFLE() {
		return isFLE;
	}

	public void setIsFLE(String isFLE) {
		this.isFLE = isFLE;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public int getServiceNo() {
		return ServiceNo;
	}

	public void setServiceNo(int serviceNo) {
		ServiceNo = serviceNo;
	}
	
	private String revCircle;
	
	public int getSubchangetypeNetworkChangeEditable() {
		return subchangetypeNetworkChangeEditable;
	}

	public void setSubchangetypeNetworkChangeEditable(
			int subchangetypeNetworkChangeEditable) {
		this.subchangetypeNetworkChangeEditable = subchangetypeNetworkChangeEditable;
	}

	public int getConfigValue() {
		return configValue;
	}


	public void setConfigValue(int configValue) {
		this.configValue = configValue;
	}
	public String getProdAttributeMaxlength() {
		return prodAttributeMaxlength;
	}

	public void setProdAttributeMaxlength(String prodAttributeMaxlength) {
		this.prodAttributeMaxlength = prodAttributeMaxlength;
	}

	public String getDispatchContactName() {
		return dispatchContactName;
	}

	public void setDispatchContactName(String dispatchContactName) {
		this.dispatchContactName = dispatchContactName;
	}

	public String getCopyProductValues() {
		return copyProductValues;
	}

	public void setCopyProductValues(String copyProductValues) {
		this.copyProductValues = copyProductValues;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getOrderType() {
		return orderType;
	}

	public int getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(int poNumber) {
		this.poNumber = poNumber;
	}
	public String getDependentOnTax() {
		return dependentOnTax;
	}


	public void setDependentOnTax(String dependentOnTax) {
		this.dependentOnTax = dependentOnTax;
	}


	public String getTaxPkgInstId() {
		return taxPkgInstId;
	}


	public void setTaxPkgInstId(String taxPkgInstId) {
		this.taxPkgInstId = taxPkgInstId;
	}


	public Double getTaxRate() {
		return taxRate;
	}


	public void setTaxRate(Double taxRate) {
		this.taxRate = taxRate;
	}
	public int getLstNo() {
		return lstNo;
	}

	public void setLstNo(int lstNo) {
		this.lstNo = lstNo;
	}
	public int dispatchAddressID;
	private String dispatchAddress1;
	private String dispatchAddress2;
	private String dispatchAddress3;
	private String dispatchCityName;
	private String dispatchStateName;
	private String dispatchCountyName;
	private String dispatchPinNo;
	private String dispatchPhoneNo;
	public String dispatchAddressName;
	public int getDispatchAddressID() {
		return dispatchAddressID;
	}

	public void setDispatchAddressID(int dispatchAddressID) {
		this.dispatchAddressID = dispatchAddressID;
	}

	public String getDispatchAddress1() {
		return dispatchAddress1;
	}

	public void setDispatchAddress1(String dispatchAddress1) {
		this.dispatchAddress1 = dispatchAddress1;
	}

	public String getDispatchAddress2() {
		return dispatchAddress2;
	}

	public void setDispatchAddress2(String dispatchAddress2) {
		this.dispatchAddress2 = dispatchAddress2;
	}

	public String getDispatchAddress3() {
		return dispatchAddress3;
	}

	public void setDispatchAddress3(String dispatchAddress3) {
		this.dispatchAddress3 = dispatchAddress3;
	}

	public String getDispatchCityName() {
		return dispatchCityName;
	}

	public void setDispatchCityName(String dispatchCityName) {
		this.dispatchCityName = dispatchCityName;
	}

	public String getDispatchCountyName() {
		return dispatchCountyName;
	}

	public void setDispatchCountyName(String dispatchCountyName) {
		this.dispatchCountyName = dispatchCountyName;
	}

	public String getDispatchPhoneNo() {
		return dispatchPhoneNo;
	}

	public void setDispatchPhoneNo(String dispatchPhoneNo) {
		this.dispatchPhoneNo = dispatchPhoneNo;
	}

	public String getDispatchPinNo() {
		return dispatchPinNo;
	}

	public void setDispatchPinNo(String dispatchPinNo) {
		this.dispatchPinNo = dispatchPinNo;
	}

	public String getDispatchStateName() {
		return dispatchStateName;
	}

	public void setDispatchStateName(String dispatchStateName) {
		this.dispatchStateName = dispatchStateName;
	}

	public String getTxtExtDate() {
		return txtExtDate;
	}

	public void setTxtExtDate(String txtExtDate) {
		this.txtExtDate = txtExtDate;
	}

	public int getTxtExtDays() {
		return txtExtDays;
	}

	public void setTxtExtDays(int txtExtDays) {
		this.txtExtDays = txtExtDays;
	}
	public int getTxtExtMonth() {
		return txtExtMonth;
	}

	public void setTxtExtMonth(int txtExtMonth) {
		this.txtExtMonth = txtExtMonth;
	}
	public int getTxtEndDays() {
		return txtEndDays;
	}

	public void setTxtEndDays(int txtEndDays) {
		this.txtEndDays = txtEndDays;
	}
	public int getTxtEndMonth() {
		return txtEndMonth;
	}

	public void setTxtEndMonth(int txtEndMonth) {
		this.txtEndMonth = txtEndMonth;
	}
	public int getTxtStartDays() {
		return txtStartDays;
	}

	public void setTxtStartDays(int txtStartDays) {
		this.txtStartDays = txtStartDays;
	}
	public int getTxtStartMonth() {
		return txtStartMonth;
	}

	public void setTxtStartMonth(int txtStartMonth) {
		this.txtStartMonth = txtStartMonth;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDateLogic() {
		return endDateLogic;
	}
	public void setEndDateLogic(String endDateLogic) {
		this.endDateLogic = endDateLogic;
	}

	public String getStartDateLogic() {
		return startDateLogic;
	}

	public void setStartDateLogic(String startDateLogic) {
		this.startDateLogic = startDateLogic;
	}
	public String getSaleType() {
		return saleType;
	}

	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}

	public String getSaleNature() {
		return saleNature;
	}

	public void setSaleNature(String saleNature) {
		this.saleNature = saleNature;
	}
	public String getHardwareType() {
		return hardwareType;
	}

	public void setHardwareType(String hardwareType) {
		this.hardwareType = hardwareType;
	}
	public String getFormAvailable() {
		return formAvailable;
	}

	public void setFormAvailable(String formAvailable) {
		this.formAvailable = formAvailable;
	}
	public int getSelectedStoreID() {
		return selectedStoreID;
	}

	public void setSelectedStoreID(int selectedStoreID) {
		this.selectedStoreID = selectedStoreID;
	}
	public int getSelectedDispatchID() {
		return selectedDispatchID;
	}

	public void setSelectedDispatchID(int selectedDispatchID) {
		this.selectedDispatchID = selectedDispatchID;
	}
	public int getHardwareDetailID() {
		return hardwareDetailID;
	}

	public void setHardwareDetailID(int hardwareDetailID) {
		this.hardwareDetailID = hardwareDetailID;
	}
	public String getSection() {
		return section;
	}


	public void setSection(String section) {
		this.section = section;
	}
	public String getDateLogicValue() {
		return dateLogicValue;
	}


	public void setDateLogicValue(String dateLogicValue) {
		this.dateLogicValue = dateLogicValue;
	}
	public String getSaleTypeName() {
		return saleTypeName;
	}


	public void setSaleTypeName(String saleTypeName) {
		this.saleTypeName = saleTypeName;
	}
	public int getSaleTypeId() {
		return saleTypeId;
	}


	public void setSaleTypeId(int saleTypeId) {
		this.saleTypeId = saleTypeId;
	}
	public String getSaleNatureName() {
		return saleNatureName;
	}


	public void setSaleNatureName(String saleNatureName) {
		this.saleNatureName = saleNatureName;
	}
	public int getSaleNatureId() {
		return saleNatureId;
	}


	public void setSaleNatureId(int saleNatureId) {
		this.saleNatureId = saleNatureId;
	}
	public String getFormName() {
		return formName;
	}


	public void setFormName(String formName) {
		this.formName = formName;
	}
	public int getFormId() {
		return formId;
	}


	public void setFormId(int formId) {
		this.formId = formId;
	}
	public String getHardwaretypeName() {
		return hardwaretypeName;
	}


	public void setHardwaretypeName(String hardwaretypeName) {
		this.hardwaretypeName = hardwaretypeName;
	}
	public int getHardwaretypeId() {
		return hardwaretypeId;
	}


	public void setHardwaretypeId(int hardwaretypeId) {
		this.hardwaretypeId = hardwaretypeId;
	}
	public long getStdReasonId() {
		return stdReasonId;
	}

	public void setStdReasonId(long stdReasonId) {
		this.stdReasonId = stdReasonId;
	}
	public int getIsNfa() {
		return isNfa;
	}


	public void setIsNfa(int isNfa) {
		this.isNfa = isNfa;
	}
	public Long getBillingLevelNo() {
		return billingLevelNo;
	}


	public void setBillingLevelNo(Long billingLevelNo) {
		this.billingLevelNo = billingLevelNo;
	}
	public long getNoticePeriod() {
		return noticePeriod;
	}


	public void setNoticePeriod(long noticePeriod) {
		this.noticePeriod = noticePeriod;
	}
	public String getBillingBCPId() {
		return billingBCPId;
	}

	public void setBillingBCPId(String billingBCPId) {
		this.billingBCPId = billingBCPId;
	}
	public String getPenaltyClause() {
		return penaltyClause;
	}

	public void setPenaltyClause(String penaltyClause) {
		this.penaltyClause = penaltyClause;
	}
	public String getBillingLevel() {
		return billingLevel;
	}

	public void setBillingLevel(String billingLevel) {
		this.billingLevel = billingLevel;
	}
	public int getCommitmentPeriod() {
		return commitmentPeriod;
	}

	public void setCommitmentPeriod(int commitmentPeriod) {
		this.commitmentPeriod = commitmentPeriod;
	}
	public String getTaxation() {
		return taxation;
	}

	public void setTaxation(String taxation) {
		this.taxation = taxation;
	}

	public int getBillingType() {
		return billingType;
	}

	public void setBillingType(int billingType) {
		this.billingType = billingType;
	}
	public String getBillingformat() {
		return billingformat;
	}

	public void setBillingformat(String billingformat) {
		this.billingformat = billingformat;
	}

	public String getBillingMode() {
		return billingMode;
	}

	public void setBillingMode(String billingMode) {
		this.billingMode = billingMode;
	}
	public int getEntityID() {
		return entityID;
	}

	public void setEntityID(int entityID) {
		this.entityID = entityID;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public int getCreditPeriod() {
		return creditPeriod;
	}

	public void setCreditPeriod(int creditPeriod) {
		this.creditPeriod = creditPeriod;
	}

	public int getAccountID() {
		return accountID;
	}

	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}
	public String getPoDate() {
		return poDate;
	}

	public void setPoDate(String poDate) {
		this.poDate = poDate;
	}
	public int getPodetailID() {
		return podetailID;
	}

	public void setPodetailID(int podetailID) {
		this.podetailID = podetailID;
	}
	public int getBillingInfoID() {
		return billingInfoID;
	}

	public void setBillingInfoID(int billingInfoID) {
		this.billingInfoID = billingInfoID;
	}
	

	public String getAttributeLabel() {
		return attributeLabel;
	}
	public void setAttributeLabel(String attributeLabel) {
		this.attributeLabel = attributeLabel;
	}
	public String getMetasolvCircuitId_new() {
		return metasolvCircuitId_new;
	}


	public void setMetasolvCircuitId_new(String metasolvCircuitId_new) {
		this.metasolvCircuitId_new = metasolvCircuitId_new;
	}

	public String getInfraOderNo_new() {
		return infraOderNo_new;
	}


	public void setInfraOderNo_new(String infraOderNo_new) {
		this.infraOderNo_new = infraOderNo_new;
	}
	public String getLogicalCircuitId_new() {
		return logicalCircuitId_new;
	}


	public void setLogicalCircuitId_new(String logicalCircuitId_new) {
		this.logicalCircuitId_new = logicalCircuitId_new;
	}
	public String getMetasolvCircuitId() {
		return metasolvCircuitId;
	}


	public void setMetasolvCircuitId(String metasolvCircuitId) {
		this.metasolvCircuitId = metasolvCircuitId;
	}
	public String getInfraOderNo() {
		return infraOderNo;
	}


	public void setInfraOderNo(String infraOderNo) {
		this.infraOderNo = infraOderNo;
	}
	public String getLogicalCircuitId() {
		return logicalCircuitId;
	}


	public void setLogicalCircuitId(String logicalCircuitId) {
		this.logicalCircuitId = logicalCircuitId;
	}
	public String getProdNameAndId() {
		return prodNameAndId;
	}
	public void setProdNameAndId(String prodNameAndId) {
		this.prodNameAndId = prodNameAndId;
	}
	public String getPcontactNO() {
		return pcontactNO;
	}


	public void setPcontactNO(String pcontactNO) {
		this.pcontactNO = pcontactNO;
	}


	public String getPemailId() {
		return pemailId;
	}


	public void setPemailId(String pemailId) {
		this.pemailId = pemailId;
	}
	public String getPcity() {
		return pcity;
	}

	public void setPcity(String pcity) {
		this.pcity = pcity;
	}

	public String getPcountry() {
		return pcountry;
	}

	public void setPcountry(String pcountry) {
		this.pcountry = pcountry;
	}

	public String getPpincode() {
		return ppincode;
	}

	public void setPpincode(String ppincode) {
		this.ppincode = ppincode;
	}

	public String getPstate() {
		return pstate;
	}

	public void setPstate(String pstate) {
		this.pstate = pstate;
	}
	public String getPaddress1() {
		return paddress1;
	}

	public void setPaddress1(String paddress1) {
		this.paddress1 = paddress1;
	}

	public String getPaddress2() {
		return paddress2;
	}

	public void setPaddress2(String paddress2) {
		this.paddress2 = paddress2;
	}

	public String getPaddress3() {
		return paddress3;
	}

	public void setPaddress3(String paddress3) {
		this.paddress3 = paddress3;
	}

	public String getPaddress4() {
		return paddress4;
	}

	public void setPaddress4(String paddress4) {
		this.paddress4 = paddress4;
	}
	public int getChargeNameID() {
		return chargeNameID;
	}


	public void setChargeNameID(int chargeNameID) {
		this.chargeNameID = chargeNameID;
	}
	public String getChargeName() {
		return chargeName;
	}

	public void setChargeName(String chargeName) {
		this.chargeName = chargeName;
	}
	public int getFactor() {
		return factor;
	}

	public void setFactor(int factor) {
		this.factor = factor;
	}
	public int getFrequencyID() {
		return frequencyID;
	}

	public void setFrequencyID(int frequencyID) {
		this.frequencyID = frequencyID;
	}
	public String getFrequencyName() {
		return frequencyName;
	}

	public void setFrequencyName(String frequencyName) {
		this.frequencyName = frequencyName;
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
	public int getStoreID() {
		return storeID;
	}

	public void setStoreID(int storeID) {
		this.storeID = storeID;
	}
	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	ArrayList<ProductCatelogDTO> serviceProducts = null;
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	public ArrayList<ProductCatelogDTO> getServiceProducts() {
		return serviceProducts;
	}

	public void setServiceProducts(ArrayList<ProductCatelogDTO> serviceProducts) {
		this.serviceProducts = serviceProducts;
	}
	public int getServiceProductID() {
		return serviceProductID;
	}

	public void setServiceProductID(int serviceProductID) {
		this.serviceProductID = serviceProductID;
	}
	public String getProductlevel() {
		return productlevel;
	}

	public void setProductlevel(String productlevel) {
		this.productlevel = productlevel;
	}

	public int getLicCompanyID() {
		return licCompanyID;
	}

	public void setLicCompanyID(int licCompanyID) {
		this.licCompanyID = licCompanyID;
	}
	public String getLicCompanyName() {
		return licCompanyName;
	}

	public void setLicCompanyName(String licCompanyName) {
		this.licCompanyName = licCompanyName;
	}

	public String getCreditPeriodName() {
		return creditPeriodName;
	}

	public void setCreditPeriodName(String creditPeriodName) {
		this.creditPeriodName = creditPeriodName;
	}

	public long getCreditPeriodId() {
		return creditPeriodId;
	}

	public void setCreditPeriodId(long creditPeriodId) {
		this.creditPeriodId = creditPeriodId;
	}
	public String getBillingTypeName() {
		return billingTypeName;
	}

	public void setBillingTypeName(String billingTypeName) {
		this.billingTypeName = billingTypeName;
	}
	public long getBillingTypeId() {
		return billingTypeId;
	}

	public void setBillingTypeId(long billingTypeId) {
		this.billingTypeId = billingTypeId;
	}
	public String getLstDate() {
		return lstDate;
	}

	public void setLstDate(String lstDate) {
		this.lstDate = lstDate;
	}
	public String getLst_No() {
		return lst_No;
	}


	public void setLst_No(String lst_No) {
		this.lst_No = lst_No;
	}
	public String getDesignation() {
		return designation;
	}


	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getBemailid() {
		return bemailid;
	}
	public void setBemailid(String bemailid) {
		this.bemailid = bemailid;
	}
	public void setBcontactNo(String bcontactNo) {
		this.bcontactNo = bcontactNo;
	}


	public String getBEmailId() {
		return bEmailId;
	}


	public void setBEmailId(String emailId) {
		bEmailId = emailId;
	}

	public String getBaddress2() {
		return baddress2;
	}

	public void setBaddress2(String baddress2) {
		this.baddress2 = baddress2;
	}

	public String getBaddress3() {
		return baddress3;
	}

	public void setBaddress3(String baddress3) {
		this.baddress3 = baddress3;
	}

	public String getBaddress4() {
		return baddress4;
	}

	public void setBaddress4(String baddress4) {
		this.baddress4 = baddress4;
	}

	public String getBcity() {
		return bcity;
	}

	public void setBcity(String bcity) {
		this.bcity = bcity;
	}

	public String getBcountry() {
		return bcountry;
	}

	public void setBcountry(String bcountry) {
		this.bcountry = bcountry;
	}

	public String getBpincode() {
		return bpincode;
	}

	public void setBpincode(String bpincode) {
		this.bpincode = bpincode;
	}

	public String getBstate() {
		return bstate;
	}

	public void setBstate(String bstate) {
		this.bstate = bstate;
	}
	public String getBaddress1() {
		return baddress1;
	}

	public void setBaddress1(String baddress1) {
		this.baddress1 = baddress1;
	}
	public String getBillingFormatName() {
		return billingFormatName;
	}

	public void setBillingFormatName(String billingFormatName) {
		this.billingFormatName = billingFormatName;
	}

	public long getBillingFormatId() {
		return billingFormatId;
	}

	public void setBillingFormatId(long billingFormatId) {
		this.billingFormatId = billingFormatId;
	}
	public String getBillingLevelName() {
		return billingLevelName;
	}

	public void setBillingLevelName(String billingLevelName) {
		this.billingLevelName = billingLevelName;
	}
	public long getBillingLevelId() {
		return billingLevelId;
	}

	public void setBillingLevelId(long billingLevelId) {
		this.billingLevelId = billingLevelId;
	}
	public String getTaxationName() {
		return taxationName;
	}

	public void setTaxationName(String taxationName) {
		this.taxationName = taxationName;
	}
	public long getTaxationId() {
		return taxationId;
		}

		public void setTaxationId(long taxationId) {
			this.taxationId = taxationId;
		}

	public String getBcpName() {
		return bcpName;
	}

	public void setBcpName(String bcpName) {
		this.bcpName = bcpName;
	}
	public int getBcpID() {
		return bcpID;
	}

	public void setBcpID(int bcpID) {
		this.bcpID = bcpID;
	}
	public int getComponentInfoValue() {
		return componentInfoValue;
	}


	public void setComponentInfoValue(int componentInfoValue) {
		this.componentInfoValue = componentInfoValue;
	}

	public int getServiceInfoValue() {
		return serviceInfoValue;
	}

	public void setServiceInfoValue(int serviceInfoValue) {
		this.serviceInfoValue = serviceInfoValue;
	}

	public int getLocationInfoValue() {
		return locationInfoValue;
	}

	public void setLocationInfoValue(int locationInfoValue) {
		this.locationInfoValue = locationInfoValue;
	}
	public int getHardwareInfoValue() {
		return hardwareInfoValue;
	}

	public void setHardwareInfoValue(int hardwareInfoValue) {
		this.hardwareInfoValue = hardwareInfoValue;
	}
	public int getChargeInfoValue() {
		return chargeInfoValue;
	}

	public void setChargeInfoValue(int chargeInfoValue) {
		this.chargeInfoValue = chargeInfoValue;
	}
	public int getBillingInfoValue() {
		return billingInfoValue;
	}

	public void setBillingInfoValue(int billingInfoValue) {
		this.billingInfoValue = billingInfoValue;
	}
	public String getServiceTypeDescription() {
		return serviceTypeDescription;
	}

	public void setServiceTypeDescription(String serviceTypeDescription) {
		this.serviceTypeDescription = serviceTypeDescription;
	}
	public String getServiceDetDescription() {
		return serviceDetDescription;
	}

	public void setServiceDetDescription(String serviceDetDescription) {
		this.serviceDetDescription = serviceDetDescription;
	}

	public int getServiceDetailID() {
		return serviceDetailID;
	}

	public void setServiceDetailID(int serviceDetailID) {
		this.serviceDetailID = serviceDetailID;
	}
	public String getMandatory() {
		return mandatory;
	}

	public void setMandatory(String mandatory) {
		this.mandatory = mandatory;
	}
	public ArrayList<FieldAttibuteDTO> getServiceSummary() {
		return serviceSummary;
	}

	public void setServiceSummary(ArrayList<FieldAttibuteDTO> serviceSummary) {
		this.serviceSummary = serviceSummary;
	}
	public String getExpectedValue() {
		return expectedValue;
	}

	public void setExpectedValue(String expectedValue) {
		this.expectedValue = expectedValue;
	}

	public String getProdAttributeValue() {
		return prodAttributeValue;
	}

	public void setProdAttributeValue(String prodAttributeValue) {
		this.prodAttributeValue = prodAttributeValue;
	}
	public long getAttMasterId() {
		return attMasterId;
	}

	public void setAttMasterId(long attMasterId) {
		this.attMasterId = attMasterId;
	}
	public String getAttMasterName() {
		return attMasterName;
	}

	public void setAttMasterName(String attMasterName) {
		this.attMasterName = attMasterName;
	}


	public String getDispatchName() {
		return dispatchName;
	}

	public int getTpDetailID() {
		return tpDetailID;
	}

	public void setTpDetailID(int tpDetailID) {
		this.tpDetailID = tpDetailID;
	}
	public void setDispatchName(String dispatchName) {
		this.dispatchName = dispatchName;
	}
		public int getBillingScenario() {
		return billingScenario;
	}


	public void setBillingScenario(int billingScenario) {
		this.billingScenario = billingScenario;
	}


	public int getFxRedirectionLSI() {
		return fxRedirectionLSI;
	}


	public void setFxRedirectionLSI(int fxRedirectionLSI) {
		this.fxRedirectionLSI = fxRedirectionLSI;
	}


	public int getFxRedirectionSPID() {
		return fxRedirectionSPID;
	}


	public void setFxRedirectionSPID(int fxRedirectionSPID) {
		this.fxRedirectionSPID = fxRedirectionSPID;
	}


	public int getIsUsage() {
		return isUsage;
	}


	public void setIsUsage(int isUsage) {
		this.isUsage = isUsage;
	}

	public String getCustPoDate() {
		return custPoDate;
	}

	public void setCustPoDate(String custPoDate) {
		this.custPoDate = custPoDate;
	}

	public String getBcontactName() {
		return bcontactName;
	}

	public void setBcontactName(String bcontactName) {
		this.bcontactName = bcontactName;
	}

	public String getBcontactNo() {
		return bcontactNo;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLegalEntity() {
		return legalEntity;
	}

	public void setLegalEntity(String legalEntity) {
		this.legalEntity = legalEntity;
	}

	public String getAnnotation() {
		return annotation;
	}

	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}

	public Double getAnnualRate() {
		return annualRate;
	}

	public void setAnnualRate(Double annualRate) {
		this.annualRate = annualRate;
	}

	public Double getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(Double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	public int getChargeEndDays() {
		return chargeEndDays;
	}

	public void setChargeEndDays(int chargeEndDays) {
		this.chargeEndDays = chargeEndDays;
	}

	public int getChargeEndMonths() {
		return chargeEndMonths;
	}

	public void setChargeEndMonths(int chargeEndMonths) {
		this.chargeEndMonths = chargeEndMonths;
	}

	public Double getChargeFrequencyAmount() {
		return chargeFrequencyAmount;
	}

	public void setChargeFrequencyAmount(Double chargeFrequencyAmount) {
		this.chargeFrequencyAmount = chargeFrequencyAmount;
	}

	public int getChargePeriod() {
		return chargePeriod;
	}

	public void setChargePeriod(int chargePeriod) {
		this.chargePeriod = chargePeriod;
	}

	public String getChargeRemarks() {
		return chargeRemarks;
	}

	public void setChargeRemarks(String chargeRemarks) {
		this.chargeRemarks = chargeRemarks;
	}

	public int getChargeStartDays() {
		return chargeStartDays;
	}

	public void setChargeStartDays(int chargeStartDays) {
		this.chargeStartDays = chargeStartDays;
	}

	public int getChargeStartMonths() {
		return chargeStartMonths;
	}

	public void setChargeStartMonths(int chargeStartMonths) {
		this.chargeStartMonths = chargeStartMonths;
	}

	public String getChargeTaxRate() {
		return ChargeTaxRate;
	}

	public void setChargeTaxRate(String chargeTaxRate) {
		ChargeTaxRate = chargeTaxRate;
	}

	public Double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}

	public String getLine2DProduct() {
		return line2DProduct;
	}

	public void setLine2DProduct(String line2DProduct) {
		this.line2DProduct = line2DProduct;
	}

	public String getLineIndicativeValue() {
		return lineIndicativeValue;
	}

	public void setLineIndicativeValue(String lineIndicativeValue) {
		this.lineIndicativeValue = lineIndicativeValue;
	}

	public String getLineManagedBy() {
		return lineManagedBy;
	}

	public void setLineManagedBy(String lineManagedBy) {
		this.lineManagedBy = lineManagedBy;
	}

	public String getLinePaymentTerms() {
		return linePaymentTerms;
	}

	public void setLinePaymentTerms(String linePaymentTerms) {
		this.linePaymentTerms = linePaymentTerms;
	}

	public String getLineRemarks() {
		return lineRemarks;
	}

	public void setLineRemarks(String lineRemarks) {
		this.lineRemarks = lineRemarks;
	}

	public Double getPrincipalAmount() {
		return PrincipalAmount;
	}

	public void setPrincipalAmount(Double principalAmount) {
		PrincipalAmount = principalAmount;
	}

	public String getTxtEndDateLogic() {
		return txtEndDateLogic;
	}

	public void setTxtEndDateLogic(String txtEndDateLogic) {
		this.txtEndDateLogic = txtEndDateLogic;
	}

	public String getTxtStartDateLogic() {
		return txtStartDateLogic;
	}

	public void setTxtStartDateLogic(String txtStartDateLogic) {
		this.txtStartDateLogic = txtStartDateLogic;
	}

	public int getWarrentyMonths() {
		return WarrentyMonths;
	}

	public void setWarrentyMonths(int warrentyMonths) {
		WarrentyMonths = warrentyMonths;
	}

	public String getRequestedBy() {
		return requestedBy;
	}

	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRevCircle() {
		return revCircle;
	}

	public void setRevCircle(String revCircle) {
		this.revCircle = revCircle;
	}

	public int getCommercial() {
		return commercial;
	}
	public void setCommercial(int commercial) {
		this.commercial = commercial;
	}
	public int getPrLineDetail() {
		return prLineDetail;
	}

	public void setPrLineDetail(int prLineDetail) {
		this.prLineDetail = prLineDetail;
	}

	public int getPrServiceSummary() {
		return prServiceSummary;
	}

	public void setPrServiceSummary(int prServiceSummary) {
		this.prServiceSummary = prServiceSummary;
	}

	public int getServiceID() {
		return serviceID;
	}
	public void setServiceID(int serviceID) {
		this.serviceID = serviceID;
	}

	public int getIsOSPTagging() {
		return isOSPTagging;
	}

	public void setIsOSPTagging(int isOSPTagging) {
		this.isOSPTagging = isOSPTagging;
	}

	public int getIsReqOSPTagging() {
		return isReqOSPTagging;
	}

	public void setIsReqOSPTagging(int isReqOSPTagging) {
		this.isReqOSPTagging = isReqOSPTagging;
	}

	public String getTxtOSPRegNo() {
		return txtOSPRegNo;
	}

	public void setTxtOSPRegNo(String txtOSPRegNo) {
		this.txtOSPRegNo = txtOSPRegNo;
	}

	public int getIsReqOSPRegNo() {
		return isReqOSPRegNo;
	}

	public void setIsReqOSPRegNo(int isReqOSPRegNo) {
		this.isReqOSPRegNo = isReqOSPRegNo;
	}

	public String getTxtOSPRegDate() {
		return txtOSPRegDate;
	}

	public void setTxtOSPRegDate(String txtOSPRegDate) {
		this.txtOSPRegDate = txtOSPRegDate;
	}

	public int getIsReqOSPRegDate() {
		return isReqOSPRegDate;
	}

	public void setIsReqOSPRegDate(int isReqOSPRegDate) {
		this.isReqOSPRegDate = isReqOSPRegDate;
	}

	
}
