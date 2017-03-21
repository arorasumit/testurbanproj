package com.ibm.ioes.bulkupload.dto;

import com.ibm.ioes.utilities.PagingSorting;
public class TransactionTemplateDto {
	private int sheetNo;
	private int totalrows;
	private int totalRowsSheet1;
	private int totalRowsSheet2;
	private int totalRowsSheet3;
	//==================================
	private Long orderNo;
	private Long logicalsiNo;
	private Long serviceid;
	private Long lineItemID;
	private String serviceName;
	private String lineItemName;
	private Long chargeID;
	private Long chargeAmount;
	private String chargeFrequency;
	//==================================
	private int contactType;
	private String salutation;
	private String firstName;
	private String lastName;
	private String email;
	private String cellno;
	private String faxno;
	private String address1;
	private String address2;
	private String address3;
	private String countrycode;
	private String stateid;
	private String cityid;
	private String pincode;
	//==================================
	private String accountID;
	private String source;
	private String currencyID;
	private String quoteNo;
	private String projectMangerID;
	private String RFSDate;
	private String actMngrPhoneNo;
	private String actMngrEmailID;
	private String IRUOrderYN;
	private String freePeriodYN;
	private String ordExclusiveTax;
	private String CAFDate;
	private String opportunityId;
	//====================================
	private String custPONumber;
	private String custPODate;
	private String isDefaultPO;
	private String legalEntity;
	private String totalPOAmount;
	private String periodInMonths;
	private String contractStartDate;
	private String contractEndDate;
	private String poRemarks;
	private String poEmailId;
	//====================================
	private Long creditPeriodID;
	private String creditPeriodName;
	private Long legealEntityID;
	private String legalEntityName;
	private Long licenseCompanyID;
	private String licenseCompanyName;
	private Long billingModeID;
	private String billingModeName;
	private Long billingFormatID;
	private String billingFormatName;
	private Long billingTypeID;
	private String billingTypeName;
	private Long taxationID;
	private String taxationName;
	private Long billingLevelID;
	private String billingLevelName;
	private Long bcpID;
	private String bcpName;
	private String standardReasonId;
	private String standardReasonName;
	private int noticePeriod;
	private String penaltyClause;
	private int commitPeriod;
	private int isNfa;
	//====================================
	private int chargeType;
	private Long chargeNameID;
	private String annotation;
	private int chargePeriod;
	private Long chargeTotalAmount;
	private String startDateLogic;
	private int startDays;
	private int startMonth;
	private String endDateLogic;
	private int endDays;
	private int endMonth;
	private String strChargeType;
	private String strChargeName;
	private Long frequncyAmount;	
	//Created By Saurabh for Search Bulk Upload
	//Start
	private String partyId;
	private String partyName;
	private String accountName;
	//End

	private String zoneId;
	private String crmAccountNo;
	private int maxPageNo;
	//Start[Date:01-04-2013 Paging Size Configurable for Bulkupload LSI Look up]---
	PagingSorting pagingSorting = new PagingSorting();	
	public int getMaxPageNo() {
		return maxPageNo;
	}
	public void setMaxPageNo(int maxPageNo) {
		this.maxPageNo = maxPageNo;
	}
	public String getCrmAccountNo() {
		return crmAccountNo;
	}
	public void setCrmAccountNo(String crmAccountNo) {
		this.crmAccountNo = crmAccountNo;
	}
	public String getZoneId() {
		return zoneId;
	}
	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}
	public String getAnnotation() {
		return annotation;
	}
	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}
	public Long getChargeNameID() {
		return chargeNameID;
	}
	public void setChargeNameID(Long chargeNameID) {
		this.chargeNameID = chargeNameID;
	}
	public int getChargePeriod() {
		return chargePeriod;
	}
	public void setChargePeriod(int chargePeriod) {
		this.chargePeriod = chargePeriod;
	}
	public Long getChargeTotalAmount() {
		return chargeTotalAmount;
	}
	public void setChargeTotalAmount(Long chargeTotalAmount) {
		this.chargeTotalAmount = chargeTotalAmount;
	}
	public int getChargeType() {
		return chargeType;
	}
	public void setChargeType(int chargeType) {
		this.chargeType = chargeType;
	}
	public String getEndDateLogic() {
		return endDateLogic;
	}
	public void setEndDateLogic(String endDateLogic) {
		this.endDateLogic = endDateLogic;
	}
	public int getEndDays() {
		return endDays;
	}
	public void setEndDays(int endDays) {
		this.endDays = endDays;
	}
	public int getEndMonth() {
		return endMonth;
	}
	public void setEndMonth(int endMonth) {
		this.endMonth = endMonth;
	}
	public String getStartDateLogic() {
		return startDateLogic;
	}
	public void setStartDateLogic(String startDateLogic) {
		this.startDateLogic = startDateLogic;
	}
	public int getStartDays() {
		return startDays;
	}
	public void setStartDays(int startDays) {
		this.startDays = startDays;
	}
	public int getStartMonth() {
		return startMonth;
	}
	public void setStartMonth(int startMonth) {
		this.startMonth = startMonth;
	}
	//====================================
	public String getCityid() {
		return cityid;
	}
	public void setCityid(String cityid) {
		this.cityid = cityid;
	}
	public String getCountrycode() {
		return countrycode;
	}
	public void setCountrycode(String countrycode) {
		this.countrycode = countrycode;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public String getStateid() {
		return stateid;
	}
	public void setStateid(String stateid) {
		this.stateid = stateid;
	}
	//==================================
	public int getSheetNo() {
		return sheetNo;
	}
	public void setSheetNo(int sheetNo) {
		this.sheetNo = sheetNo;
	}
	public Long getLineItemID() {
		return lineItemID;
	}
	public void setLineItemID(Long lineItemID) {
		this.lineItemID = lineItemID;
	}
	public Long getLogicalsiNo() {
		return logicalsiNo;
	}
	public void setLogicalsiNo(Long logicalsiNo) {
		this.logicalsiNo = logicalsiNo;
	}
	public Long getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}
	public Long getServiceid() {
		return serviceid;
	}
	public void setServiceid(Long serviceid) {
		this.serviceid = serviceid;
	}
	public int getTotalrows() {
		return totalrows;
	}
	public void setTotalrows(int totalrows) {
		this.totalrows = totalrows;
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
	public String getCellno() {
		return cellno;
	}
	public void setCellno(String cellno) {
		this.cellno = cellno;
	}
	
	public int getContactType() {
		return contactType;
	}
	public void setContactType(int contactType) {
		this.contactType = contactType;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFaxno() {
		return faxno;
	}
	public void setFaxno(String faxno) {
		this.faxno = faxno;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getSalutation() {
		return salutation;
	}
	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}
	
	public int getTotalRowsSheet1() {
		return totalRowsSheet1;
	}
	public void setTotalRowsSheet1(int totalRowsSheet1) {
		this.totalRowsSheet1 = totalRowsSheet1;
	}
	public int getTotalRowsSheet2() {
		return totalRowsSheet2;
	}
	public void setTotalRowsSheet2(int totalRowsSheet2) {
		this.totalRowsSheet2 = totalRowsSheet2;
	}
	public int getTotalRowsSheet3() {
		return totalRowsSheet3;
	}
	public void setTotalRowsSheet3(int totalRowsSheet3) {
		this.totalRowsSheet3 = totalRowsSheet3;
	}
	public String getAccountID() {
		return accountID;
	}
	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}
	public String getActMngrEmailID() {
		return actMngrEmailID;
	}
	public void setActMngrEmailID(String actMngrEmailID) {
		this.actMngrEmailID = actMngrEmailID;
	}
	public String getActMngrPhoneNo() {
		return actMngrPhoneNo;
	}
	public void setActMngrPhoneNo(String actMngrPhoneNo) {
		this.actMngrPhoneNo = actMngrPhoneNo;
	}
	public String getCAFDate() {
		return CAFDate;
	}
	public void setCAFDate(String date) {
		CAFDate = date;
	}
	public String getCurrencyID() {
		return currencyID;
	}
	public void setCurrencyID(String currencyID) {
		this.currencyID = currencyID;
	}
	public String getFreePeriodYN() {
		return freePeriodYN;
	}
	public void setFreePeriodYN(String freePeriodYN) {
		this.freePeriodYN = freePeriodYN;
	}
	public String getIRUOrderYN() {
		return IRUOrderYN;
	}
	public void setIRUOrderYN(String orderYN) {
		IRUOrderYN = orderYN;
	}
	public String getOrdExclusiveTax() {
		return ordExclusiveTax;
	}
	public void setOrdExclusiveTax(String ordExclusiveTax) {
		this.ordExclusiveTax = ordExclusiveTax;
	}
	public String getProjectMangerID() {
		return projectMangerID;
	}
	public void setProjectMangerID(String projectMangerID) {
		this.projectMangerID = projectMangerID;
	}
	public String getQuoteNo() {
		return quoteNo;
	}
	public void setQuoteNo(String quoteNo) {
		this.quoteNo = quoteNo;
	}
	public String getRFSDate() {
		return RFSDate;
	}
	public void setRFSDate(String date) {
		RFSDate = date;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getLineItemName() {
		return lineItemName;
	}
	public void setLineItemName(String lineItemName) {
		this.lineItemName = lineItemName;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getContractEndDate() {
		return contractEndDate;
	}
	public void setContractEndDate(String contractEndDate) {
		this.contractEndDate = contractEndDate;
	}
	public String getContractStartDate() {
		return contractStartDate;
	}
	public void setContractStartDate(String contractStartDate) {
		this.contractStartDate = contractStartDate;
	}
	public String getCustPODate() {
		return custPODate;
	}
	public void setCustPODate(String custPODate) {
		this.custPODate = custPODate;
	}
	public String getCustPONumber() {
		return custPONumber;
	}
	public void setCustPONumber(String custPONumber) {
		this.custPONumber = custPONumber;
	}
	public String getIsDefaultPO() {
		return isDefaultPO;
	}
	public void setIsDefaultPO(String isDefaultPO) {
		this.isDefaultPO = isDefaultPO;
	}
	public String getLegalEntity() {
		return legalEntity;
	}
	public void setLegalEntity(String legalEntity) {
		this.legalEntity = legalEntity;
	}
	public String getPeriodInMonths() {
		return periodInMonths;
	}
	public void setPeriodInMonths(String periodInMonths) {
		this.periodInMonths = periodInMonths;
	}
	public String getPoEmailId() {
		return poEmailId;
	}
	public void setPoEmailId(String poEmailId) {
		this.poEmailId = poEmailId;
	}
	public String getPoRemarks() {
		return poRemarks;
	}
	public void setPoRemarks(String poRemarks) {
		this.poRemarks = poRemarks;
	}
	public String getTotalPOAmount() {
		return totalPOAmount;
	}
	public void setTotalPOAmount(String totalPOAmount) {
		this.totalPOAmount = totalPOAmount;
	}
	public Long getChargeAmount() {
		return chargeAmount;
	}
	public void setChargeAmount(Long chargeAmount) {
		this.chargeAmount = chargeAmount;
	}	
	public String getChargeFrequency() {
		return chargeFrequency;
	}
	public void setChargeFrequency(String chargeFrequency) {
		this.chargeFrequency = chargeFrequency;
	}
	public Long getChargeID() {
		return chargeID;
	}
	public void setChargeID(Long chargeID) {
		this.chargeID = chargeID;
	}
	public Long getBcpID() {
		return bcpID;
	}
	public void setBcpID(Long bcpID) {
		this.bcpID = bcpID;
	}
	public String getBcpName() {
		return bcpName;
	}
	public void setBcpName(String bcpName) {
		this.bcpName = bcpName;
	}
	public Long getBillingFormatID() {
		return billingFormatID;
	}
	public void setBillingFormatID(Long billingFormatID) {
		this.billingFormatID = billingFormatID;
	}
	public String getBillingFormatName() {
		return billingFormatName;
	}
	public void setBillingFormatName(String billingFormatName) {
		this.billingFormatName = billingFormatName;
	}
	public Long getBillingLevelID() {
		return billingLevelID;
	}
	public void setBillingLevelID(Long billingLevelID) {
		this.billingLevelID = billingLevelID;
	}
	public String getBillingLevelName() {
		return billingLevelName;
	}
	public void setBillingLevelName(String billingLevelName) {
		this.billingLevelName = billingLevelName;
	}
	public Long getBillingModeID() {
		return billingModeID;
	}
	public void setBillingModeID(Long billingModeID) {
		this.billingModeID = billingModeID;
	}
	public String getBillingModeName() {
		return billingModeName;
	}
	public void setBillingModeName(String billingModeName) {
		this.billingModeName = billingModeName;
	}
	public Long getBillingTypeID() {
		return billingTypeID;
	}
	public void setBillingTypeID(Long billingTypeID) {
		this.billingTypeID = billingTypeID;
	}
	public String getBillingTypeName() {
		return billingTypeName;
	}
	public void setBillingTypeName(String billingTypeName) {
		this.billingTypeName = billingTypeName;
	}
	public Long getCreditPeriodID() {
		return creditPeriodID;
	}
	public void setCreditPeriodID(Long creditPeriodID) {
		this.creditPeriodID = creditPeriodID;
	}
	public String getCreditPeriodName() {
		return creditPeriodName;
	}
	public void setCreditPeriodName(String creditPeriodName) {
		this.creditPeriodName = creditPeriodName;
	}
	public String getLegalEntityName() {
		return legalEntityName;
	}
	public void setLegalEntityName(String legalEntityName) {
		this.legalEntityName = legalEntityName;
	}
	public Long getLegealEntityID() {
		return legealEntityID;
	}
	public void setLegealEntityID(Long legealEntityID) {
		this.legealEntityID = legealEntityID;
	}
	public Long getLicenseCompanyID() {
		return licenseCompanyID;
	}
	public void setLicenseCompanyID(Long licenseCompanyID) {
		this.licenseCompanyID = licenseCompanyID;
	}
	public String getLicenseCompanyName() {
		return licenseCompanyName;
	}
	public void setLicenseCompanyName(String licenseCompanyName) {
		this.licenseCompanyName = licenseCompanyName;
	}
	public Long getTaxationID() {
		return taxationID;
	}
	public void setTaxationID(Long taxationID) {
		this.taxationID = taxationID;
	}
	public String getTaxationName() {
		return taxationName;
	}
	public void setTaxationName(String taxationName) {
		this.taxationName = taxationName;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getPartyId() {
		return partyId;
	}
	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}
	public String getPartyName() {
		return partyName;
	}
	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}
	public String getOpportunityId() {
		return opportunityId;
	}
	public void setOpportunityId(String opportunityId) {
		this.opportunityId = opportunityId;
	}
	public String getStandardReasonId() {
		return standardReasonId;
	}
	public void setStandardReasonId(String standardReasonId) {
		this.standardReasonId = standardReasonId;
	}
	public String getStandardReasonName() {
		return standardReasonName;
	}
	public void setStandardReasonName(String standardReasonName) {
		this.standardReasonName = standardReasonName;
	}
	public int getCommitPeriod() {
		return commitPeriod;
	}
	public void setCommitPeriod(int commitPeriod) {
		this.commitPeriod = commitPeriod;
	}
	public int getIsNfa() {
		return isNfa;
	}
	public void setIsNfa(int isNfa) {
		this.isNfa = isNfa;
	}
	public int getNoticePeriod() {
		return noticePeriod;
	}
	public void setNoticePeriod(int noticePeriod) {
		this.noticePeriod = noticePeriod;
	}
	public String getPenaltyClause() {
		return penaltyClause;
	}
	public void setPenaltyClause(String penaltyClause) {
		this.penaltyClause = penaltyClause;
	}
	public Long getFrequncyAmount() {
		return frequncyAmount;
	}
	public void setFrequncyAmount(Long frequncyAmount) {
		this.frequncyAmount = frequncyAmount;
	}
	public String getStrChargeName() {
		return strChargeName;
	}
	public void setStrChargeName(String strChargeName) {
		this.strChargeName = strChargeName;
	}
	public String getStrChargeType() {
		return strChargeType;
	}
	public void setStrChargeType(String strChargeType) {
		this.strChargeType = strChargeType;
	}
	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}
	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
	}
	
	

}
