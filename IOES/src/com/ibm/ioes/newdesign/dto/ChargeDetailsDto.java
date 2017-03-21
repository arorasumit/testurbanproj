package com.ibm.ioes.newdesign.dto;

import java.util.Date;

/**
 * 
 * @author IBM_ADMIN
 *@since June 2014
 */
public class ChargeDetailsDto {

	/* Declare all properties related to TCHARGES_INFO */
	
	Long chargeInfoId;
	Long serviceProductId;
	Long accountId;
	Long chargesType;
	String chargeName;
	Long chargePeriod;
	Double chargeAmount;
	String chargeFrequency;
	Double chargeFrequencyAmt;
	String startDateLogic;
	String endDateLogic;
	Integer isDisconnectedInFx;
	Integer isDisconnectedInM6;
	Integer isSuspendedInFx;
	Integer isSuspendedInM6;
	Integer isDisconnected;
	Integer isSuspended;
	Long oldChargeInfoId;
	Integer startDateDays;
	Integer startDateMonth;
	Integer endDateDays;
	Integer endDateMonths;
	Long chargeNameId;
	Date createdDate1;
	Date modifiedDate1;
	Integer fxStatus;
	Integer fxSchedDisconnectStatus;
	String annotation;
	Date createdDate2;
	Long createdBy;
	Date modfiedDate2;
	Long mididfiedBy;
	Long disconnectedInOrderNo;
	Integer billingTriggerDisconnectStatus;
	String chargeDisconnectionDate;
	Long createdInServiceId;
	String disconnectionType;
	Long exclude;
	Integer poDetailNo;
	Double annualRate;
	Double tchargesInfoHistory;
	String remarks;
	String startDate;
	String endDate;
	Long paymentTerm1;
	Long paymentTerm2;
	Long paymentTerm3;
	Long paymentTerm4;
	String taxRate;
	String chargesStatus;
	Long lineItemId;
	Long subLineItemId;
	String chargeCreateSource;
	Date ldDateClause;
	Integer delayedTimeIndays;
	Integer ldPercentage;
	Integer maxPenaltyPercentage;
	Integer isDifferential;
	Long differentialChargeId;
	Double obValue;
	Double systemOb;
	String lepmYN;
	Long businessSerialNo;
	String opmsAccountId;
	String lineItemNumber;
	String mocnNumber;
	String opmsLineItemNumber;
	Date obValueLastUpdateDate;
	Long obLinkChargeId;
	Long netOb;
	Long netObId;
	public Long getChargeInfoId() {
		return chargeInfoId;
	}
	public void setChargeInfoId(Long chargeInfoId) {
		this.chargeInfoId = chargeInfoId;
	}
	public Long getServiceProductId() {
		return serviceProductId;
	}
	public void setServiceProductId(Long serviceProductId) {
		this.serviceProductId = serviceProductId;
	}
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public Long getChargesType() {
		return chargesType;
	}
	public void setChargesType(Long chargesType) {
		this.chargesType = chargesType;
	}
	public String getChargeName() {
		return chargeName;
	}
	public void setChargeName(String chargeName) {
		this.chargeName = chargeName;
	}
	public Long getChargePeriod() {
		return chargePeriod;
	}
	public void setChargePeriod(Long chargePeriod) {
		this.chargePeriod = chargePeriod;
	}
	public Double getChargeAmount() {
		return chargeAmount;
	}
	public void setChargeAmount(Double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}
	public String getChargeFrequency() {
		return chargeFrequency;
	}
	public void setChargeFrequency(String chargeFrequency) {
		this.chargeFrequency = chargeFrequency;
	}
	public Double getChargeFrequencyAmt() {
		return chargeFrequencyAmt;
	}
	public void setChargeFrequencyAmt(Double chargeFrequencyAmt) {
		this.chargeFrequencyAmt = chargeFrequencyAmt;
	}
	public String getStartDateLogic() {
		return startDateLogic;
	}
	public void setStartDateLogic(String startDateLogic) {
		this.startDateLogic = startDateLogic;
	}
	public String getEndDateLogic() {
		return endDateLogic;
	}
	public void setEndDateLogic(String endDateLogic) {
		this.endDateLogic = endDateLogic;
	}
	public Integer getIsDisconnectedInFx() {
		return isDisconnectedInFx;
	}
	public void setIsDisconnectedInFx(Integer isDisconnectedInFx) {
		this.isDisconnectedInFx = isDisconnectedInFx;
	}
	public Integer getIsDisconnectedInM6() {
		return isDisconnectedInM6;
	}
	public void setIsDisconnectedInM6(Integer isDisconnectedInM6) {
		this.isDisconnectedInM6 = isDisconnectedInM6;
	}
	public Integer getIsSuspendedInFx() {
		return isSuspendedInFx;
	}
	public void setIsSuspendedInFx(Integer isSuspendedInFx) {
		this.isSuspendedInFx = isSuspendedInFx;
	}
	public Integer getIsSuspendedInM6() {
		return isSuspendedInM6;
	}
	public void setIsSuspendedInM6(Integer isSuspendedInM6) {
		this.isSuspendedInM6 = isSuspendedInM6;
	}
	public Integer getIsDisconnected() {
		return isDisconnected;
	}
	public void setIsDisconnected(Integer isDisconnected) {
		this.isDisconnected = isDisconnected;
	}
	public Integer getIsSuspended() {
		return isSuspended;
	}
	public void setIsSuspended(Integer isSuspended) {
		this.isSuspended = isSuspended;
	}
	public Long getOldChargeInfoId() {
		return oldChargeInfoId;
	}
	public void setOldChargeInfoId(Long oldChargeInfoId) {
		this.oldChargeInfoId = oldChargeInfoId;
	}
	public Integer getStartDateDays() {
		return startDateDays;
	}
	public void setStartDateDays(Integer startDateDays) {
		this.startDateDays = startDateDays;
	}
	public Integer getStartDateMonth() {
		return startDateMonth;
	}
	public void setStartDateMonth(Integer startDateMonth) {
		this.startDateMonth = startDateMonth;
	}
	public Integer getEndDateDays() {
		return endDateDays;
	}
	public void setEndDateDays(Integer endDateDays) {
		this.endDateDays = endDateDays;
	}
	public Integer getEndDateMonths() {
		return endDateMonths;
	}
	public void setEndDateMonths(Integer endDateMonths) {
		this.endDateMonths = endDateMonths;
	}
	public Long getChargeNameId() {
		return chargeNameId;
	}
	public void setChargeNameId(Long chargeNameId) {
		this.chargeNameId = chargeNameId;
	}
	public Date getCreatedDate1() {
		return createdDate1;
	}
	public void setCreatedDate1(Date createdDate1) {
		this.createdDate1 = createdDate1;
	}
	public Date getModifiedDate1() {
		return modifiedDate1;
	}
	public void setModifiedDate1(Date modifiedDate1) {
		this.modifiedDate1 = modifiedDate1;
	}
	public Integer getFxStatus() {
		return fxStatus;
	}
	public void setFxStatus(Integer fxStatus) {
		this.fxStatus = fxStatus;
	}
	public Integer getFxSchedDisconnectStatus() {
		return fxSchedDisconnectStatus;
	}
	public void setFxSchedDisconnectStatus(Integer fxSchedDisconnectStatus) {
		this.fxSchedDisconnectStatus = fxSchedDisconnectStatus;
	}
	public String getAnnotation() {
		return annotation;
	}
	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}
	public Date getCreatedDate2() {
		return createdDate2;
	}
	public void setCreatedDate2(Date createdDate2) {
		this.createdDate2 = createdDate2;
	}
	public Long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	public Date getModfiedDate2() {
		return modfiedDate2;
	}
	public void setModfiedDate2(Date modfiedDate2) {
		this.modfiedDate2 = modfiedDate2;
	}
	public Long getMididfiedBy() {
		return mididfiedBy;
	}
	public void setMididfiedBy(Long mididfiedBy) {
		this.mididfiedBy = mididfiedBy;
	}
	public Long getDisconnectedInOrderNo() {
		return disconnectedInOrderNo;
	}
	public void setDisconnectedInOrderNo(Long disconnectedInOrderNo) {
		this.disconnectedInOrderNo = disconnectedInOrderNo;
	}
	public Integer getBillingTriggerDisconnectStatus() {
		return billingTriggerDisconnectStatus;
	}
	public void setBillingTriggerDisconnectStatus(
			Integer billingTriggerDisconnectStatus) {
		this.billingTriggerDisconnectStatus = billingTriggerDisconnectStatus;
	}
	public String getChargeDisconnectionDate() {
		return chargeDisconnectionDate;
	}
	public void setChargeDisconnectionDate(String chargeDisconnectionDate) {
		this.chargeDisconnectionDate = chargeDisconnectionDate;
	}
	public Long getCreatedInServiceId() {
		return createdInServiceId;
	}
	public void setCreatedInServiceId(Long createdInServiceId) {
		this.createdInServiceId = createdInServiceId;
	}
	public String getDisconnectionType() {
		return disconnectionType;
	}
	public void setDisconnectionType(String disconnectionType) {
		this.disconnectionType = disconnectionType;
	}
	public Long getExclude() {
		return exclude;
	}
	public void setExclude(Long exclude) {
		this.exclude = exclude;
	}
	public Integer getPoDetailNo() {
		return poDetailNo;
	}
	public void setPoDetailNo(Integer poDetailNo) {
		this.poDetailNo = poDetailNo;
	}
	public Double getAnnualRate() {
		return annualRate;
	}
	public void setAnnualRate(Double annualRate) {
		this.annualRate = annualRate;
	}
	public Double getTchargesInfoHistory() {
		return tchargesInfoHistory;
	}
	public void setTchargesInfoHistory(Double tchargesInfoHistory) {
		this.tchargesInfoHistory = tchargesInfoHistory;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public Long getPaymentTerm1() {
		return paymentTerm1;
	}
	public void setPaymentTerm1(Long paymentTerm1) {
		this.paymentTerm1 = paymentTerm1;
	}
	public Long getPaymentTerm2() {
		return paymentTerm2;
	}
	public void setPaymentTerm2(Long paymentTerm2) {
		this.paymentTerm2 = paymentTerm2;
	}
	public Long getPaymentTerm3() {
		return paymentTerm3;
	}
	public void setPaymentTerm3(Long paymentTerm3) {
		this.paymentTerm3 = paymentTerm3;
	}
	public Long getPaymentTerm4() {
		return paymentTerm4;
	}
	public void setPaymentTerm4(Long paymentTerm4) {
		this.paymentTerm4 = paymentTerm4;
	}
	public String getTaxRate() {
		return taxRate;
	}
	public void setTaxRate(String taxRate) {
		this.taxRate = taxRate;
	}
	public String getChargesStatus() {
		return chargesStatus;
	}
	public void setChargesStatus(String chargesStatus) {
		this.chargesStatus = chargesStatus;
	}
	public Long getLineItemId() {
		return lineItemId;
	}
	public void setLineItemId(Long lineItemId) {
		this.lineItemId = lineItemId;
	}
	public Long getSubLineItemId() {
		return subLineItemId;
	}
	public void setSubLineItemId(Long subLineItemId) {
		this.subLineItemId = subLineItemId;
	}
	public String getChargeCreateSource() {
		return chargeCreateSource;
	}
	public void setChargeCreateSource(String chargeCreateSource) {
		this.chargeCreateSource = chargeCreateSource;
	}
	public Date getLdDateClause() {
		return ldDateClause;
	}
	public void setLdDateClause(Date ldDateClause) {
		this.ldDateClause = ldDateClause;
	}
	public Integer getDelayedTimeIndays() {
		return delayedTimeIndays;
	}
	public void setDelayedTimeIndays(Integer delayedTimeIndays) {
		this.delayedTimeIndays = delayedTimeIndays;
	}
	public Integer getLdPercentage() {
		return ldPercentage;
	}
	public void setLdPercentage(Integer ldPercentage) {
		this.ldPercentage = ldPercentage;
	}
	public Integer getMaxPenaltyPercentage() {
		return maxPenaltyPercentage;
	}
	public void setMaxPenaltyPercentage(Integer maxPenaltyPercentage) {
		this.maxPenaltyPercentage = maxPenaltyPercentage;
	}
	public Integer getIsDifferential() {
		return isDifferential;
	}
	public void setIsDifferential(Integer isDifferential) {
		this.isDifferential = isDifferential;
	}
	public Long getDifferentialChargeId() {
		return differentialChargeId;
	}
	public void setDifferentialChargeId(Long differentialChargeId) {
		this.differentialChargeId = differentialChargeId;
	}
	public Double getObValue() {
		return obValue;
	}
	public void setObValue(Double obValue) {
		this.obValue = obValue;
	}
	public Double getSystemOb() {
		return systemOb;
	}
	public void setSystemOb(Double systemOb) {
		this.systemOb = systemOb;
	}
	public String getLepmYN() {
		return lepmYN;
	}
	public void setLepmYN(String lepmYN) {
		this.lepmYN = lepmYN;
	}
	public Long getBusinessSerialNo() {
		return businessSerialNo;
	}
	public void setBusinessSerialNo(Long businessSerialNo) {
		this.businessSerialNo = businessSerialNo;
	}
	public String getOpmsAccountId() {
		return opmsAccountId;
	}
	public void setOpmsAccountId(String opmsAccountId) {
		this.opmsAccountId = opmsAccountId;
	}
	public String getLineItemNumber() {
		return lineItemNumber;
	}
	public void setLineItemNumber(String lineItemNumber) {
		this.lineItemNumber = lineItemNumber;
	}
	public String getMocnNumber() {
		return mocnNumber;
	}
	public void setMocnNumber(String mocnNumber) {
		this.mocnNumber = mocnNumber;
	}
	public String getOpmsLineItemNumber() {
		return opmsLineItemNumber;
	}
	public void setOpmsLineItemNumber(String opmsLineItemNumber) {
		this.opmsLineItemNumber = opmsLineItemNumber;
	}
	public Date getObValueLastUpdateDate() {
		return obValueLastUpdateDate;
	}
	public void setObValueLastUpdateDate(Date obValueLastUpdateDate) {
		this.obValueLastUpdateDate = obValueLastUpdateDate;
	}
	public Long getObLinkChargeId() {
		return obLinkChargeId;
	}
	public void setObLinkChargeId(Long obLinkChargeId) {
		this.obLinkChargeId = obLinkChargeId;
	}
	public Long getNetOb() {
		return netOb;
	}
	public void setNetOb(Long netOb) {
		this.netOb = netOb;
	}
	public Long getNetObId() {
		return netObId;
	}
	public void setNetObId(Long netObId) {
		this.netObId = netObId;
	}
    
    /* Declare all properties related to TCHARGES_INFO */
	
}
