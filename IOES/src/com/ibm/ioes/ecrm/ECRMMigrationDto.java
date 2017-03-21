package com.ibm.ioes.ecrm;

import java.sql.Date;
import java.sql.Timestamp;

public class ECRMMigrationDto {
	
	private String doc_num; //M6 Order No for migrating Manually punched orders 

	private String crmOrderId; // OrderNo
	private Integer crmOrderId1;
	private Timestamp creationDate;
	private String createdBy;
	private Timestamp lastUpdatedDate;
	private String lastUpdatedBy;
	private String creationDate1;
	private String lastUpdatedDate1;
	private String isDisconnected;
	private String isSuspended;
	private String isDisconnectedInFX;
	private String isDisconnectedInM6;
	private String isSuspendedInFX;
	private String isSuspendedInM6;
	private String lineStatus;
	private String oldServiceProductId;
	private String changeOrderNo;
	private String subChangeTypeId;
	private String createdDate;
	private String modifiedDate;
	private String modifiedBy;
	private String changeServiceId;
	private String changeParentSPId;
	private String additionalNode;
	private String forBillingTrigger;
	private String m6_ATT_FX_CHANGED;
	private String serviceActiveDT;
	private String parentServiceKey;
	private String childServiceKey;
	private String changeServiceProductId;
	private String disconnectionInCurrentOrder;
	private String modifiedAt;
	private String resumeInCurrentOrder;
	private String changeTypeId;
	private String serviceProductId;
	
	//Order Header
	private String orderType; // New or Change
	private String orderSource; //CRMQUOTE or CRMORDER
	private String quoteNumber; //Quote No
	private String currency; 
	private String m6OrderStatus; 
	private String orderStage; // New, AM,PM, etc
	private String custAccountId; //Account No
	private String pon; //Product
	private String orderSubType; //ChangeTypeId
	private String zoneId; //
	private String projectManagerId;
	private String demoType;
	private Timestamp orderDate;
	private String orderDate1;
	private String regionId;
	private Timestamp publishDate;
	private String publishDate1;
	private String lobId;
	private String processId;
	private String salesFirstName;
	private String salesLastName;
	private String salesPhoneNo;
	private String salesEmail;
	private String employeeId;
	
	//Header Attributes
	private String labelName;
	private String labelValue;
	
	//Contact Details
	private String orderContactId;
	private String contactType;
	private String formofAddress;
	private String givenName;
	private String familyName;
	private String eMail;
	private long cellPhoneNo;
	private String faxNo;
	private String accountNo;
	private String cellPhoneNo1;
	
	//Address Details
	private String contactAddressId;
	private String address1;
	private String address2;
	private String address3;
	private String pin;
	private String cityId;
	private String stateId;
	private String countryId;
	
	//PO Details
	private String poId;
	private String legalEntityCode;
	private String totalPOAmount;
	private String contractPeriod;
	private Timestamp custPOReceiveDate;
	private String defaultFlag;
	private String poIssuingPersonName;
	private String poIssuingPersonEmail;
	private String demoContractPeriod;
	private Timestamp contractStartDate;
	private Timestamp contactEndDate;
	private Timestamp custPODate;
	private String custPONo;
	private String poPaymentTerms;
	private String stdReasonId;
	
	//Service Lines
	private String serviceNo;
	private String oldServiceNo;
	private Integer serviceNo1;
	private String serviceType;
	private String serviceStage;
	private Timestamp effectiveStartDate;
	private String effectiveStartDate1;
	private String customerLogicalSIId;
	private Timestamp effectiveEndDate;
	private String effectiveEndDate1;
	private String provisioningPlanId;
	private String remarks;
	private String m6OrderId;
	private String preM6OrderId;
	private String eventTypeId;
	private String cancelReason;
	private String serviceOrderType;
	private String customerLogicalSINo;
	private String status;
	//private String processIdServiceLevel;
	private String logicalSINo;
	private String serviceState;
	private Timestamp rfsDate;
	private String rfsDate1;
	private String crmProductName;
	private String crmSubProductName;
	private String isInHistory;
	private String mainServiceId;
	private String newServiceListId;
	
	//Service Level and Order Level Attributes 
	private String m6LabelName;
	private String m6LabelValue;
	private String isLov;
	private String attNewValue;
	private String attValue;
	
	//Order Line
	private String orderLineId;
	private String productName;
	private String locDate;
	private String locNo;
	private String billingTriggerDate;
	private String parentServiceId;
	private String billingTriggerFlag;
	private String circuitId;
	private String priLocId;
	private String hubLocation;
	private String uom;
	private String billingTriggerCreateDate;
	private String challenNo;
	private String challenDate;
	private String m6ProductId;
	private String childAccountNo;
	private String m6ChildServiceKey;
	private String m6ParentServiceKey;
	private String lineState;
	private String parentSpecId;
	private String childSpecId;
	private String locRecDate;
	private String pmProvisioningDate;
	
	
	//Billing Info
	private String billingInfoId;
	private String creditPeriod;
	private String billingMode;
	private String billFormat;
	private String licenceCompany;
	private String taxation;
	private String billingLevel;
	private String commitmentPeriod;
	private String penaltyCause;
	private String billType;
	private String billingAddressId;
	private String billingLevelNo;
	private String noticePeriod;
	private String orderNo;
	private String letestSelectedPoDetailID;
	
	//Location Info
	private String priLocationType;
	private String priLocationId;
	private String secLocationType;
	private String secLocationId;
	
	//Hardware Info
	private String store;
	private String harwareType;
	private String form;
	private String typeofSale;
	private String natureofSale;
	private String dispatchAddId;
	private String hwWarrantyStartDateLogic;
	private String hwWarrantyEndDateLogic;
	private String hwWarrantyPeriodMonths;
	private String hwWarrantyEndPeriodMonths;	//THARDWARE_INFO
	private String hwWarrantyExtPeriodMonths;	//THARDWARE_INFO
	private String hwWarrantyStartDate;
	private String hwWarrantyEndDate;
	private String hwWarrantyExtDate;		//THARDWARE_INFO
	private String principalAmount;
	private String interestRate;
	private String hwWarrantyStartDays;		//THARDWARE_INFO
	private String hwWarrantyEndDays;		//THARDWARE_INFO
	private String hwWarrantyExtDays;		//THARDWARE_INFO
	private String extndSupportEndDate;
	
	//Charges
	private String chargeId;
	private String chargetype;
	private String contractPeriodMonths;
	private Double totalAmount;
	private String frequency;
	private Double chargeValue;
	private String startDatLogic;
	private String endDateLogic;
	private String startDateDays;
	private String startDateMonths;
	private String endDateDays;
	private String endDateMonths;
	private String fxStatus;
	private String annotation;
	private int crmChargeId;
	private String taxRate;
	private String charge_end_date;
	private String charge_start_date;
	private String exclude;
	private String annual_rate;
	private String charge_status;
	private String crmChargeId1;
	
	//Approval Tab
	private String taskId;
	private String previousTaskId;
	private String taskName;
	private String taskStatusId;
	private String ownerId;
	private String plannedStartDate;
	private String reasonCode;
	private String actualEndDate;
	private String changeReasonId;
	
	private String rejectedDate;
	private String rejectedBy;
	private String isRejected;
	private String isFirstTask;
	private String isLastTask;
	private String taskAssignedTo;
	private String rejectionSendTo;
	
	//FX
	private String subScrNo;
	private String subScrNoReset;
	private String accExternalId;
	private String tokenId;
	
	//RC and NRC
	private String viewId;
	
	// ADDED BY MANISHA start
	private int autobilling;
	private int fx_scheduler_status;
	private int emfconfigid;
	private String elementid;
	private String bill_period;
	private String override_rate;
	private String disconnection_date;
	private String disconnection_step;
	private String field_for_validation;
	private String disc_orderno;
//	 ADDED BY MANISHA end
	
	public String getDisc_orderno() {
		return disc_orderno;
	}
	public void setDisc_orderno(String disc_orderno) {
		this.disc_orderno = disc_orderno;
	}
	public String getField_for_validation() {
		return field_for_validation;
	}
	public void setField_for_validation(String field_for_validation) {
		this.field_for_validation = field_for_validation;
	}
	public String getDisconnection_date() {
		return disconnection_date;
	}
	public void setDisconnection_date(String disconnection_date) {
		this.disconnection_date = disconnection_date;
	}
	public String getElementid() {
		return elementid;
	}
	public void setElementid(String elementid) {
		this.elementid = elementid;
	}
	public int getEmfconfigid() {
		return emfconfigid;
	}
	public void setEmfconfigid(int emfconfigid) {
		this.emfconfigid = emfconfigid;
	}
	public int getAutobilling() {
		return autobilling;
	}
	public void setAutobilling(int autobilling) {
		this.autobilling = autobilling;
	}
	public int getFx_scheduler_status() {
		return fx_scheduler_status;
	}
	public void setFx_scheduler_status(int fx_scheduler_status) {
		this.fx_scheduler_status = fx_scheduler_status;
	}
	public String getViewId() {
		return viewId;
	}
	public void setViewId(String viewId) {
		this.viewId = viewId;
	}
	public String getAccExternalId() {
		return accExternalId;
	}
	public void setAccExternalId(String accExternalId) {
		this.accExternalId = accExternalId;
	}
	public String getSubScrNo() {
		return subScrNo;
	}
	public void setSubScrNo(String subScrNo) {
		this.subScrNo = subScrNo;
	}
	public String getSubScrNoReset() {
		return subScrNoReset;
	}
	public void setSubScrNoReset(String subScrNoReset) {
		this.subScrNoReset = subScrNoReset;
	}
	public String getTokenId() {
		return tokenId;
	}
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}
	public String getBillingTriggerDate() {
		return billingTriggerDate;
	}
	public void setBillingTriggerDate(String billingTriggerDate) {
		this.billingTriggerDate = billingTriggerDate;
	}
	public String getBillingTriggerFlag() {
		return billingTriggerFlag;
	}
	public void setBillingTriggerFlag(String billingTriggerFlag) {
		this.billingTriggerFlag = billingTriggerFlag;
	}
	public String getChallenDate() {
		return challenDate;
	}
	public void setChallenDate(String challenDate) {
		this.challenDate = challenDate;
	}
	public String getChallenNo() {
		return challenNo;
	}
	public void setChallenNo(String challenNo) {
		this.challenNo = challenNo;
	}
	public String getCircuitId() {
		return circuitId;
	}
	public void setCircuitId(String circuitId) {
		this.circuitId = circuitId;
	}
	public String getHubLocation() {
		return hubLocation;
	}
	public void setHubLocation(String hubLocation) {
		this.hubLocation = hubLocation;
	}
	public String getLocDate() {
		return locDate;
	}
	public void setLocDate(String locDate) {
		this.locDate = locDate;
	}
	public String getLocNo() {
		return locNo;
	}
	public void setLocNo(String locNo) {
		this.locNo = locNo;
	}
	public String getM6ProductId() {
		return m6ProductId;
	}
	public void setM6ProductId(String productId) {
		m6ProductId = productId;
	}
	public String getOrderLineId() {
		return orderLineId;
	}
	public void setOrderLineId(String orderLineId) {
		this.orderLineId = orderLineId;
	}
	public String getParentServiceId() {
		return parentServiceId;
	}
	public void setParentServiceId(String parentServiceId) {
		this.parentServiceId = parentServiceId;
	}
	public String getPriLocId() {
		return priLocId;
	}
	public void setPriLocId(String priLocId) {
		this.priLocId = priLocId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public String getM6LabelName() {
		return m6LabelName;
	}
	public void setM6LabelName(String labelName) {
		m6LabelName = labelName;
	}
	public String getM6LabelValue() {
		return m6LabelValue;
	}
	public void setM6LabelValue(String labelValue) {
		m6LabelValue = labelValue;
	}
	public String getPoPaymentTerms() {
		return poPaymentTerms;
	}
	public void setPoPaymentTerms(String poPaymentTerms) {
		this.poPaymentTerms = poPaymentTerms;
	}
	public Timestamp getContactEndDate() {
		return contactEndDate;
	}
	public void setContactEndDate(Timestamp contactEndDate) {
		this.contactEndDate = contactEndDate;
	}
	public String getContractPeriod() {
		return contractPeriod;
	}
	public void setContractPeriod(String contractPeriod) {
		this.contractPeriod = contractPeriod;
	}
	public Timestamp getContractStartDate() {
		return contractStartDate;
	}
	public void setContractStartDate(Timestamp contractStartDate) {
		this.contractStartDate = contractStartDate;
	}
	public Timestamp getCustPODate() {
		return custPODate;
	}
	public void setCustPODate(Timestamp custPODate) {
		this.custPODate = custPODate;
	}
	public String getCustPONo() {
		return custPONo;
	}
	public void setCustPONo(String custPONo) {
		this.custPONo = custPONo;
	}
	public Timestamp getCustPOReceiveDate() {
		return custPOReceiveDate;
	}
	public void setCustPOReceiveDate(Timestamp custPOReceiveDate) {
		this.custPOReceiveDate = custPOReceiveDate;
	}
	public String getDefaultFlag() {
		return defaultFlag;
	}
	public void setDefaultFlag(String defaultFlag) {
		this.defaultFlag = defaultFlag;
	}
	public String getDemoContractPeriod() {
		return demoContractPeriod;
	}
	public void setDemoContractPeriod(String demoContractPeriod) {
		this.demoContractPeriod = demoContractPeriod;
	}
	public String getLegalEntityCode() {
		return legalEntityCode;
	}
	public void setLegalEntityCode(String legalEntityCode) {
		this.legalEntityCode = legalEntityCode;
	}
	public String getPoId() {
		return poId;
	}
	public void setPoId(String poId) {
		this.poId = poId;
	}
	public String getPoIssuingPersonEmail() {
		return poIssuingPersonEmail;
	}
	public void setPoIssuingPersonEmail(String poIssuingPersonEmail) {
		this.poIssuingPersonEmail = poIssuingPersonEmail;
	}
	public String getPoIssuingPersonName() {
		return poIssuingPersonName;
	}
	public void setPoIssuingPersonName(String poIssuingPersonName) {
		this.poIssuingPersonName = poIssuingPersonName;
	}
	public String getTotalPOAmount() {
		return totalPOAmount;
	}
	public void setTotalPOAmount(String totalPOAmount) {
		this.totalPOAmount = totalPOAmount;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getAddress3() {
		return address3;
	}
	public void setAddress3(String address3) {
		this.address3 = address3;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getContactAddressId() {
		return contactAddressId;
	}
	public void setContactAddressId(String contactAddressId) {
		this.contactAddressId = contactAddressId;
	}
	public String getCountryId() {
		return countryId;
	}
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public String getStateId() {
		return stateId;
	}
	public void setStateId(String stateId) {
		this.stateId = stateId;
	}
	public String getLabelName() {
		return labelName;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	public String getLabelValue() {
		return labelValue;
	}
	public void setLabelValue(String labelValue) {
		this.labelValue = labelValue;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Timestamp getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}
	public String getCrmOrderId() {
		return crmOrderId;
	}
	public void setCrmOrderId(String crmOrderId) {
		this.crmOrderId = crmOrderId;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getCustAccountId() {
		return custAccountId;
	}
	public void setCustAccountId(String custAccountId) {
		this.custAccountId = custAccountId;
	}
	public String getDemoType() {
		return demoType;
	}
	public void setDemoType(String demoType) {
		this.demoType = demoType;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public Timestamp getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	public String getLobId() {
		return lobId;
	}
	public void setLobId(String lobId) {
		this.lobId = lobId;
	}
	public String getM6OrderStatus() {
		return m6OrderStatus;
	}
	public void setM6OrderStatus(String orderStatus) {
		m6OrderStatus = orderStatus;
	}
	public Timestamp getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Timestamp orderDate) {
		this.orderDate = orderDate;
	}
	public String getOrderSource() {
		return orderSource;
	}
	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}
	public String getOrderStage() {
		return orderStage;
	}
	public void setOrderStage(String orderStage) {
		this.orderStage = orderStage;
	}
	public String getOrderSubType() {
		return orderSubType;
	}
	public void setOrderSubType(String orderSubType) {
		this.orderSubType = orderSubType;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getPon() {
		return pon;
	}
	public void setPon(String pon) {
		this.pon = pon;
	}
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	public String getProjectManagerId() {
		return projectManagerId;
	}
	public void setProjectManagerId(String projectManagerId) {
		this.projectManagerId = projectManagerId;
	}
	public Timestamp getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Timestamp publishDate) {
		this.publishDate = publishDate;
	}
	public String getQuoteNumber() {
		return quoteNumber;
	}
	public void setQuoteNumber(String quoteNumber) {
		this.quoteNumber = quoteNumber;
	}
	public String getRegionId() {
		return regionId;
	}
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	public String getZoneId() {
		return zoneId;
	}
	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}
	public long getCellPhoneNo() {
		return cellPhoneNo;
	}
	public void setCellPhoneNo(long cellPhoneNo) {
		this.cellPhoneNo = cellPhoneNo;
	}
	public String getContactType() {
		return contactType;
	}
	public void setContactType(String contactType) {
		this.contactType = contactType;
	}
	public String getEMail() {
		return eMail;
	}
	public void setEMail(String mail) {
		eMail = mail;
	}
	public String getFamilyName() {
		return familyName;
	}
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
	public String getFaxNo() {
		return faxNo;
	}
	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}
	public String getFormofAddress() {
		return formofAddress;
	}
	public void setFormofAddress(String formofAddress) {
		this.formofAddress = formofAddress;
	}
	public String getGivenName() {
		return givenName;
	}
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}
	public String getOrderContactId() {
		return orderContactId;
	}
	public void setOrderContactId(String orderContactId) {
		this.orderContactId = orderContactId;
	}
	public String getCustomerLogicalSINo() {
		return customerLogicalSINo;
	}
	public void setCustomerLogicalSINo(String customerLogicalSINo) {
		this.customerLogicalSINo = customerLogicalSINo;
	}
	public Timestamp getEffectiveEndDate() {
		return effectiveEndDate;
	}
	public void setEffectiveEndDate(Timestamp effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
	}
	public Timestamp getEffectiveStartDate() {
		return effectiveStartDate;
	}
	public void setEffectiveStartDate(Timestamp effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}
	public String getLogicalSINo() {
		return logicalSINo;
	}
	public void setLogicalSINo(String logicalSINo) {
		this.logicalSINo = logicalSINo;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getServiceNo() {
		return serviceNo;
	}
	public void setServiceNo(String serviceNo) {
		this.serviceNo = serviceNo;
	}
	public String getOldServiceNo() {
		return oldServiceNo;
	}
	public void setOldServiceNo(String oldServiceNo) {
		this.oldServiceNo = oldServiceNo;
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
	public String getAnnotation() {
		return annotation;
	}
	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}
	public String getBillFormat() {
		return billFormat;
	}
	public void setBillFormat(String billFormat) {
		this.billFormat = billFormat;
	}
	public String getBillingAddressId() {
		return billingAddressId;
	}
	public void setBillingAddressId(String billingAddressId) {
		this.billingAddressId = billingAddressId;
	}
	public String getBillingLevel() {
		return billingLevel;
	}
	public void setBillingLevel(String billingLevel) {
		this.billingLevel = billingLevel;
	}
	public String getBillingLevelNo() {
		return billingLevelNo;
	}
	public void setBillingLevelNo(String billingLevelNo) {
		this.billingLevelNo = billingLevelNo;
	}
	public String getBillingMode() {
		return billingMode;
	}
	public void setBillingMode(String billingMode) {
		this.billingMode = billingMode;
	}
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	public String getChargetype() {
		return chargetype;
	}
	public void setChargetype(String chargetype) {
		this.chargetype = chargetype;
	}
	public Double getChargeValue() {
		return chargeValue;
	}
	public void setChargeValue(Double chargeValue) {
		this.chargeValue = chargeValue;
	}
	public String getCommitmentPeriod() {
		return commitmentPeriod;
	}
	public void setCommitmentPeriod(String commitmentPeriod) {
		this.commitmentPeriod = commitmentPeriod;
	}
	public String getContractPeriodMonths() {
		return contractPeriodMonths;
	}
	public void setContractPeriodMonths(String contractPeriodMonths) {
		this.contractPeriodMonths = contractPeriodMonths;
	}
	public String getCreditPeriod() {
		return creditPeriod;
	}
	public void setCreditPeriod(String creditPeriod) {
		this.creditPeriod = creditPeriod;
	}
	public String getDispatchAddId() {
		return dispatchAddId;
	}
	public void setDispatchAddId(String dispatchAddId) {
		this.dispatchAddId = dispatchAddId;
	}
	public String getEndDateDays() {
		return endDateDays;
	}
	public void setEndDateDays(String endDateDays) {
		this.endDateDays = endDateDays;
	}
	public String getEndDateLogic() {
		return endDateLogic;
	}
	public void setEndDateLogic(String endDateLogic) {
		this.endDateLogic = endDateLogic;
	}
	public String getEndDateMonths() {
		return endDateMonths;
	}
	public void setEndDateMonths(String endDateMonths) {
		this.endDateMonths = endDateMonths;
	}
	public String getForm() {
		return form;
	}
	public void setForm(String form) {
		this.form = form;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public String getFxStatus() {
		return fxStatus;
	}
	public void setFxStatus(String fxStatus) {
		this.fxStatus = fxStatus;
	}
	public String getHarwareType() {
		return harwareType;
	}
	public void setHarwareType(String harwareType) {
		this.harwareType = harwareType;
	}
	public String getHwWarrantyEndDate() {
		return hwWarrantyEndDate;
	}
	public void setHwWarrantyEndDate(String hwWarrantyEndDate) {
		this.hwWarrantyEndDate = hwWarrantyEndDate;
	}
	public String getHwWarrantyExtDate() {
		return hwWarrantyExtDate;
	}
	public void setHwWarrantyExtDate(String hwWarrantyExtDate) {
		this.hwWarrantyExtDate = hwWarrantyExtDate;
	}
	public String getHwWarrantyEndDateLogic() {
		return hwWarrantyEndDateLogic;
	}
	public void setHwWarrantyEndDateLogic(String hwWarrantyEndDateLogic) {
		this.hwWarrantyEndDateLogic = hwWarrantyEndDateLogic;
	}
	public String getHwWarrantyPeriodMonths() {
		return hwWarrantyPeriodMonths;
	}
	public void setHwWarrantyPeriodMonths(String hwWarrantyPeriodMonths) {
		this.hwWarrantyPeriodMonths = hwWarrantyPeriodMonths;
	}
	public String getHwWarrantyEndPeriodMonths() {
		return hwWarrantyEndPeriodMonths;
	}
	public void setHwWarrantyEndPeriodMonths(String hwWarrantyEndPeriodMonths) {
		this.hwWarrantyEndPeriodMonths = hwWarrantyEndPeriodMonths;
	}
	public String getHwWarrantyExtPeriodMonths() {
		return hwWarrantyExtPeriodMonths;
	}
	public void setHwWarrantyExtPeriodMonths(String hwWarrantyExtPeriodMonths) {
		this.hwWarrantyExtPeriodMonths = hwWarrantyExtPeriodMonths;
	}
	public String getHwWarrantyStartDate() {
		return hwWarrantyStartDate;
	}
	public void setHwWarrantyStartDate(String hwWarrantyStartDate) {
		this.hwWarrantyStartDate = hwWarrantyStartDate;
	}
	public String getHwWarrantyStartDateLogic() {
		return hwWarrantyStartDateLogic;
	}
	public void setHwWarrantyStartDateLogic(String hwWarrantyStartDateLogic) {
		this.hwWarrantyStartDateLogic = hwWarrantyStartDateLogic;
	}
	public String getHwWarrantyStartDays() {
		return hwWarrantyStartDays;
	}
	public void setHwWarrantyStartDays(String hwWarrantyStartDays) {
		this.hwWarrantyStartDays = hwWarrantyStartDays;
	}
	public String getHwWarrantyEndDays() {
		return hwWarrantyEndDays;
	}
	public void setHwWarrantyEndDays(String hwWarrantyEndDays) {
		this.hwWarrantyEndDays = hwWarrantyEndDays;
	}
	public String getHwWarrantyExtDays() {
		return hwWarrantyExtDays;
	}
	public void setHwWarrantyExtDays(String hwWarrantyExtDays) {
		this.hwWarrantyExtDays = hwWarrantyExtDays;
	}
	public String getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(String interestRate) {
		this.interestRate = interestRate;
	}
	public String getLicenceCompany() {
		return licenceCompany;
	}
	public void setLicenceCompany(String licenceCompany) {
		this.licenceCompany = licenceCompany;
	}
	public String getNatureofSale() {
		return natureofSale;
	}
	public void setNatureofSale(String natureofSale) {
		this.natureofSale = natureofSale;
	}
	public String getNoticePeriod() {
		return noticePeriod;
	}
	public void setNoticePeriod(String noticePeriod) {
		this.noticePeriod = noticePeriod;
	}
	public String getPenaltyCause() {
		return penaltyCause;
	}
	public void setPenaltyCause(String penaltyCause) {
		this.penaltyCause = penaltyCause;
	}
	public String getPriLocationId() {
		return priLocationId;
	}
	public void setPriLocationId(String priLocationId) {
		this.priLocationId = priLocationId;
	}
	public String getPriLocationType() {
		return priLocationType;
	}
	public void setPriLocationType(String priLocationType) {
		this.priLocationType = priLocationType;
	}
	public String getPrincipalAmount() {
		return principalAmount;
	}
	public void setPrincipalAmount(String principalAmount) {
		this.principalAmount = principalAmount;
	}
	public String getSecLocationId() {
		return secLocationId;
	}
	public void setSecLocationId(String secLocationId) {
		this.secLocationId = secLocationId;
	}
	public String getSecLocationType() {
		return secLocationType;
	}
	public void setSecLocationType(String secLocationType) {
		this.secLocationType = secLocationType;
	}
	public String getStartDateDays() {
		return startDateDays;
	}
	public void setStartDateDays(String startDateDays) {
		this.startDateDays = startDateDays;
	}
	public String getStartDateMonths() {
		return startDateMonths;
	}
	public void setStartDateMonths(String startDateMonths) {
		this.startDateMonths = startDateMonths;
	}
	public String getStartDatLogic() {
		return startDatLogic;
	}
	public void setStartDatLogic(String startDatLogic) {
		this.startDatLogic = startDatLogic;
	}
	public String getStore() {
		return store;
	}
	public void setStore(String store) {
		this.store = store;
	}
	public String getTaxation() {
		return taxation;
	}
	public void setTaxation(String taxation) {
		this.taxation = taxation;
	}
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getTypeofSale() {
		return typeofSale;
	}
	public void setTypeofSale(String typeofSale) {
		this.typeofSale = typeofSale;
	}
	public String getBillingTriggerCreateDate() {
		return billingTriggerCreateDate;
	}
	public void setBillingTriggerCreateDate(String billingTriggerCreateDate) {
		this.billingTriggerCreateDate = billingTriggerCreateDate;
	}
	public String getChildAccountNo() {
		return childAccountNo;
	}
	public void setChildAccountNo(String childAccountNo) {
		this.childAccountNo = childAccountNo;
	}
	public String getM6ChildServiceKey() {
		return m6ChildServiceKey;
	}
	public void setM6ChildServiceKey(String childServiceKey) {
		m6ChildServiceKey = childServiceKey;
	}
	public String getM6ParentServiceKey() {
		return m6ParentServiceKey;
	}
	public void setM6ParentServiceKey(String parentServiceKey) {
		m6ParentServiceKey = parentServiceKey;
	}
	public String getLineState() {
		return lineState;
	}
	public void setLineState(String lineState) {
		this.lineState = lineState;
	}
	public String getServiceState() {
		return serviceState;
	}
	public void setServiceState(String serviceState) {
		this.serviceState = serviceState;
	}
	public String getCreationDate1() {
		return creationDate1;
	}
	public void setCreationDate1(String creationDate1) {
		this.creationDate1 = creationDate1;
	}
	public String getLastUpdatedDate1() {
		return lastUpdatedDate1;
	}
	public void setLastUpdatedDate1(String lastUpdatedDate1) {
		this.lastUpdatedDate1 = lastUpdatedDate1;
	}
	public String getCancelReason() {
		return cancelReason;
	}
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
	public String getCustomerLogicalSIId() {
		return customerLogicalSIId;
	}
	public void setCustomerLogicalSIId(String customerLogicalSIId) {
		this.customerLogicalSIId = customerLogicalSIId;
	}
	public String getEventTypeId() {
		return eventTypeId;
	}
	public void setEventTypeId(String eventTypeId) {
		this.eventTypeId = eventTypeId;
	}
	public String getM6OrderId() {
		return m6OrderId;
	}
	public void setM6OrderId(String orderId) {
		m6OrderId = orderId;
	}
	public String getPreM6OrderId() {
		return preM6OrderId;
	}
	public void setPreM6OrderId(String preM6OrderId) {
		this.preM6OrderId = preM6OrderId;
	}
	public String getProvisioningPlanId() {
		return provisioningPlanId;
	}
	public void setProvisioningPlanId(String provisioningPlanId) {
		this.provisioningPlanId = provisioningPlanId;
	}
	public Timestamp getRfsDate() {
		return rfsDate;
	}
	public void setRfsDate(Timestamp rfsDate) {
		this.rfsDate = rfsDate;
	}
	public String getServiceOrderType() {
		return serviceOrderType;
	}
	public void setServiceOrderType(String serviceOrderType) {
		this.serviceOrderType = serviceOrderType;
	}
	public String getBillingInfoId() {
		return billingInfoId;
	}
	public void setBillingInfoId(String billingInfoId) {
		this.billingInfoId = billingInfoId;
	}
	public String getChargeId() {
		return chargeId;
	}
	public void setChargeId(String chargeId) {
		this.chargeId = chargeId;
	}
	public String getActualEndDate() {
		return actualEndDate;
	}
	public void setActualEndDate(String actualEndDate) {
		this.actualEndDate = actualEndDate;
	}
	public String getPlannedStartDate() {
		return plannedStartDate;
	}
	public void setPlannedStartDate(String plannedStartDate) {
		this.plannedStartDate = plannedStartDate;
	}
	public String getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getTaskStatusId() {
		return taskStatusId;
	}
	public void setTaskStatusId(String taskStatusId) {
		this.taskStatusId = taskStatusId;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public String getOrderDate1() {
		return orderDate1;
	}
	public void setOrderDate1(String orderDate1) {
		this.orderDate1 = orderDate1;
	}
	public String getPublishDate1() {
		return publishDate1;
	}
	public void setPublishDate1(String publishDate1) {
		this.publishDate1 = publishDate1;
	}
	public String getEffectiveEndDate1() {
		return effectiveEndDate1;
	}
	public void setEffectiveEndDate1(String effectiveEndDate1) {
		this.effectiveEndDate1 = effectiveEndDate1;
	}
	public String getEffectiveStartDate1() {
		return effectiveStartDate1;
	}
	public void setEffectiveStartDate1(String effectiveStartDate1) {
		this.effectiveStartDate1 = effectiveStartDate1;
	}
	public String getRfsDate1() {
		return rfsDate1;
	}
	public void setRfsDate1(String rfsDate1) {
		this.rfsDate1 = rfsDate1;
	}
	public Integer getCrmOrderId1() {
		return crmOrderId1;
	}
	public void setCrmOrderId1(Integer crmOrderId1) {
		this.crmOrderId1 = crmOrderId1;
	}
	public Integer getServiceNo1() {
		return serviceNo1;
	}
	public void setServiceNo1(Integer serviceNo1) {
		this.serviceNo1 = serviceNo1;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getDoc_num() {
		return doc_num;
	}
	public void setDoc_num(String doc_num) {
		this.doc_num = doc_num;
	}
	public String getChildSpecId() {
		return childSpecId;
	}
	public void setChildSpecId(String childSpecId) {
		this.childSpecId = childSpecId;
	}
	public String getParentSpecId() {
		return parentSpecId;
	}
	public void setParentSpecId(String parentSpecId) {
		this.parentSpecId = parentSpecId;
	}
	public String getIsLov() {
		return isLov;
	}
	public void setIsLov(String isLov) {
		this.isLov = isLov;
	}
	public int getCrmChargeId() {
		return crmChargeId;
	}
	public void setCrmChargeId(int crmChargeId) {
		this.crmChargeId = crmChargeId;
	}
	public String getTaxRate() {
		return taxRate;
	}
	public void setTaxRate(String taxRate) {
		this.taxRate = taxRate;
	}
	public String getCrmProductName() {
		return crmProductName;
	}
	public void setCrmProductName(String crmProductName) {
		this.crmProductName = crmProductName;
	}
	public String getCrmSubProductName() {
		return crmSubProductName;
	}
	public void setCrmSubProductName(String crmSubProductName) {
		this.crmSubProductName = crmSubProductName;
	}
	public String getIsInHistory() {
		return isInHistory;
	}
	public void setIsInHistory(String isInHistory) {
		this.isInHistory = isInHistory;
	}
	public String getIsDisconnected() {
		return isDisconnected;
	}
	public void setIsDisconnected(String isDisconnected) {
		this.isDisconnected = isDisconnected;
	}
	public String getIsDisconnectedInFX() {
		return isDisconnectedInFX;
	}
	public void setIsDisconnectedInFX(String isDisconnectedInFX) {
		this.isDisconnectedInFX = isDisconnectedInFX;
	}
	public String getIsDisconnectedInM6() {
		return isDisconnectedInM6;
	}
	public void setIsDisconnectedInM6(String isDisconnectedInM6) {
		this.isDisconnectedInM6 = isDisconnectedInM6;
	}
	public String getIsSuspended() {
		return isSuspended;
	}
	public void setIsSuspended(String isSuspended) {
		this.isSuspended = isSuspended;
	}
	public String getIsSuspendedInFX() {
		return isSuspendedInFX;
	}
	public void setIsSuspendedInFX(String isSuspendedInFX) {
		this.isSuspendedInFX = isSuspendedInFX;
	}
	public String getIsSuspendedInM6() {
		return isSuspendedInM6;
	}
	public void setIsSuspendedInM6(String isSuspendedInM6) {
		this.isSuspendedInM6 = isSuspendedInM6;
	}
	public String getLineStatus() {
		return lineStatus;
	}
	public void setLineStatus(String lineStatus) {
		this.lineStatus = lineStatus;
	}
	public String getStdReasonId() {
		return stdReasonId;
	}
	public void setStdReasonId(String stdReasonId) {
		this.stdReasonId = stdReasonId;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getExtndSupportEndDate() {
		return extndSupportEndDate;
	}
	public void setExtndSupportEndDate(String extndSupportEndDate) {
		this.extndSupportEndDate = extndSupportEndDate;
	}
	public String getCharge_end_date() {
		return charge_end_date;
	}
	public void setCharge_end_date(String charge_end_date) {
		this.charge_end_date = charge_end_date;
	}
	public String getExclude() {
		return exclude;
	}
	public void setExclude(String exclude) {
		this.exclude = exclude;
	}
	public String getAnnual_rate() {
		return annual_rate;
	}
	public void setAnnual_rate(String annual_rate) {
		this.annual_rate = annual_rate;
	}
	public String getCharge_start_date() {
		return charge_start_date;
	}
	public void setCharge_start_date(String charge_start_date) {
		this.charge_start_date = charge_start_date;
	}
	public String getCharge_status() {
		return charge_status;
	}
	public void setCharge_status(String charge_status) {
		this.charge_status = charge_status;
	}
	public String getCrmChargeId1() {
		return crmChargeId1;
	}
	public void setCrmChargeId1(String crmChargeId1) {
		this.crmChargeId1 = crmChargeId1;
	}
	public String getMainServiceId() {
		return mainServiceId;
	}
	public void setMainServiceId(String mainServiceId) {
		this.mainServiceId = mainServiceId;
	}
	public String getIsFirstTask() {
		return isFirstTask;
	}
	public void setIsFirstTask(String isFirstTask) {
		this.isFirstTask = isFirstTask;
	}
	public String getIsLastTask() {
		return isLastTask;
	}
	public void setIsLastTask(String isLastTask) {
		this.isLastTask = isLastTask;
	}
	public String getIsRejected() {
		return isRejected;
	}
	public void setIsRejected(String isRejected) {
		this.isRejected = isRejected;
	}
	public String getRejectedBy() {
		return rejectedBy;
	}
	public void setRejectedBy(String rejectedBy) {
		this.rejectedBy = rejectedBy;
	}
	public String getRejectedDate() {
		return rejectedDate;
	}
	public void setRejectedDate(String rejectedDate) {
		this.rejectedDate = rejectedDate;
	}
	public String getTaskAssignedTo() {
		return taskAssignedTo;
	}
	public void setTaskAssignedTo(String taskAssignedTo) {
		this.taskAssignedTo = taskAssignedTo;
	}
	public String getPreviousTaskId() {
		return previousTaskId;
	}
	public void setPreviousTaskId(String previousTaskId) {
		this.previousTaskId = previousTaskId;
	}
	public String getRejectionSendTo() {
		return rejectionSendTo;
	}
	public void setRejectionSendTo(String rejectionSendTo) {
		this.rejectionSendTo = rejectionSendTo;
	}
	public String getSalesFirstName() {
		return salesFirstName;
	}
	public void setSalesFirstName(String salesFirstName) {
		this.salesFirstName = salesFirstName;
	}
	public String getSalesLastName() {
		return salesLastName;
	}
	public void setSalesLastName(String salesLastName) {
		this.salesLastName = salesLastName;
	}
	public String getSalesPhoneNo() {
		return salesPhoneNo;
	}
	public void setSalesPhoneNo(String salesPhoneNo) {
		this.salesPhoneNo = salesPhoneNo;
	}
	public String getSalesEmail() {
		return salesEmail;
	}
	public void setSalesEmail(String salesEmail) {
		this.salesEmail = salesEmail;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getOldServiceProductId() {
		return oldServiceProductId;
	}
	public void setOldServiceProductId(String oldServiceProductId) {
		this.oldServiceProductId = oldServiceProductId;
	}
	public String getChangeOrderNo() {
		return changeOrderNo;
	}
	public void setChangeOrderNo(String changeOrderNo) {
		this.changeOrderNo = changeOrderNo;
	}
	public String getSubChangeTypeId() {
		return subChangeTypeId;
	}
	public void setSubChangeTypeId(String subChangeTypeId) {
		this.subChangeTypeId = subChangeTypeId;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getChangeServiceId() {
		return changeServiceId;
	}
	public void setChangeServiceId(String changeServiceId) {
		this.changeServiceId = changeServiceId;
	}
	public String getChangeParentSPId() {
		return changeParentSPId;
	}
	public void setChangeParentSPId(String changeParentSPId) {
		this.changeParentSPId = changeParentSPId;
	}
	public String getForBillingTrigger() {
		return forBillingTrigger;
	}
	public void setForBillingTrigger(String forBillingTrigger) {
		this.forBillingTrigger = forBillingTrigger;
	}
	public String getM6_ATT_FX_CHANGED() {
		return m6_ATT_FX_CHANGED;
	}
	public void setM6_ATT_FX_CHANGED(String m6_att_fx_changed) {
		m6_ATT_FX_CHANGED = m6_att_fx_changed;
	}
	public String getServiceActiveDT() {
		return serviceActiveDT;
	}
	public void setServiceActiveDT(String serviceActiveDT) {
		this.serviceActiveDT = serviceActiveDT;
	}
	public String getAdditionalNode() {
		return additionalNode;
	}
	public void setAdditionalNode(String additionalNode) {
		this.additionalNode = additionalNode;
	}
	public String getChildServiceKey() {
		return childServiceKey;
	}
	public void setChildServiceKey(String childServiceKey) {
		this.childServiceKey = childServiceKey;
	}
	public String getParentServiceKey() {
		return parentServiceKey;
	}
	public void setParentServiceKey(String parentServiceKey) {
		this.parentServiceKey = parentServiceKey;
	}
	public String getChangeServiceProductId() {
		return changeServiceProductId;
	}
	public void setChangeServiceProductId(String changeServiceProductId) {
		this.changeServiceProductId = changeServiceProductId;
	}
	public String getDisconnectionInCurrentOrder() {
		return disconnectionInCurrentOrder;
	}
	public void setDisconnectionInCurrentOrder(String disconnectionInCurrentOrder) {
		this.disconnectionInCurrentOrder = disconnectionInCurrentOrder;
	}
	public String getModifiedAt() {
		return modifiedAt;
	}
	public void setModifiedAt(String modifiedAt) {
		this.modifiedAt = modifiedAt;
	}
	public String getResumeInCurrentOrder() {
		return resumeInCurrentOrder;
	}
	public void setResumeInCurrentOrder(String resumeInCurrentOrder) {
		this.resumeInCurrentOrder = resumeInCurrentOrder;
	}
	public String getChangeTypeId() {
		return changeTypeId;
	}
	public void setChangeTypeId(String changeTypeId) {
		this.changeTypeId = changeTypeId;
	}
	public String getServiceProductId() {
		return serviceProductId;
	}
	public void setServiceProductId(String serviceProductId) {
		this.serviceProductId = serviceProductId;
	}
	public String getAttNewValue() {
		return attNewValue;
	}
	public void setAttNewValue(String attNewValue) {
		this.attNewValue = attNewValue;
	}
	public String getAttValue() {
		return attValue;
	}
	public void setAttValue(String attValue) {
		this.attValue = attValue;
	}
	public String getBill_period() {
		return bill_period;
	}
	public void setBill_period(String bill_period) {
		this.bill_period = bill_period;
	}
	public String getOverride_rate() {
		return override_rate;
	}
	public void setOverride_rate(String override_rate) {
		this.override_rate = override_rate;
	}
	public String getDisconnection_step() {
		return disconnection_step;
	}
	public void setDisconnection_step(String disconnection_step) {
		this.disconnection_step = disconnection_step;
	}
	public String getCellPhoneNo1() {
		return cellPhoneNo1;
	}
	public void setCellPhoneNo1(String cellPhoneNo1) {
		this.cellPhoneNo1 = cellPhoneNo1;
	}
	public String getChangeReasonId() {
		return changeReasonId;
	}
	public void setChangeReasonId(String changeReasonId) {
		this.changeReasonId = changeReasonId;
	}
	public String getLetestSelectedPoDetailID() {
		return letestSelectedPoDetailID;
	}
	public void setLetestSelectedPoDetailID(String letestSelectedPoDetailID) {
		this.letestSelectedPoDetailID = letestSelectedPoDetailID;
	}
	public String getLocRecDate() {
		return locRecDate;
	}
	public void setLocRecDate(String locRecDate) {
		this.locRecDate = locRecDate;
	}
	public String getNewServiceListId() {
		return newServiceListId;
	}
	public void setNewServiceListId(String newServiceListId) {
		this.newServiceListId = newServiceListId;
	}
	public String getPmProvisioningDate() {
		return pmProvisioningDate;
	}
	public void setPmProvisioningDate(String pmProvisioningDate) {
		this.pmProvisioningDate = pmProvisioningDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
