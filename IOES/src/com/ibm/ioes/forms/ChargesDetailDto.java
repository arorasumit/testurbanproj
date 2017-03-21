//tag		Name       Date			CSR No			Description 

//[001]	 LawKush		30-March-11	CSR00-05422     Added variables for isrequired
package com.ibm.ioes.forms;

public class ChargesDetailDto {

	private int frequencyID;
	private int factor;
	private String frequencyName;
	private int chargeTypeID;
	private String chargeTypeName;
	private String chargeName;
	private String chargeAnnotation;
	private int chargePeriod;
	private int chargeType;
	private double chargeAmount;
	private String chargeAmount_String;
	private String chargeFrequency;
	private double chargeFrequencyAmt;
	private String chargeFrequencyAmt_String;
	private String startDateLogic;
	private String endDateLogic;
	private int chargeInfoID;
	private String chargeInfoID_String;
	private int  excludecheck;
	
	private long newChargeAmount;
	private String effectiveDateForRenewal;
	private String reasonForRenewal;
	private long serviceProductID;
	private long orderNo;
	private long serviceID;
	
	
	private int startDateDays;
	private int startDateMonth;
	private int endDateDays;
	private int endDateMonth;
	
	private int isReqDdCType;
	private int isReqTxtCName;
	private int isReqTxtCPeriod;
	private int isReqTxtCTAmt;
	private int isReqTxtCFrequency;
	private int isReqTxtCFreqAmt;
	private int isReqTxtCStartDate;
	private int isReqTxtCEndDate;
	private int isReqTxtCAnnotation;
	private int chargeNameID;
	//lawkush
	//Start [001]
	private int isReqTxtCStartDays;
	private int isReqTxtCStartMonths;
	private int isReqTxtCEndDays;
	private int isReqTxtCEndMonths;
	//End [001]
	
	private String chargeRemarks;
	private int isReqTxtRemarks;
	
	private int lineItemId;
	private String lineItemName;
	private int subLineItemId;
	private String subLineItemName;
	private int isReqLineItem;
	private int isReqSubLineItem;
	private int paymentTerm1;
	private int paymentTerm2;
	private int paymentTerm3;
	private int paymentTerm4;
	private String taxRate;
	
	private String ldDateClause;
	private int delayedTimeInDayes;
	private int ldPercentage;
	private int maxPercentage;
	private long obLinkChargeId;
	public long getObLinkChargeId() {
		return obLinkChargeId;
	}
	public void setObLinkChargeId(long obLinkChargeId) {
		this.obLinkChargeId = obLinkChargeId;
	}
	public int getIsReqTxtRemarks() {
		return isReqTxtRemarks;
	}
	public void setIsReqTxtRemarks(int isReqTxtRemarks) {
		this.isReqTxtRemarks = isReqTxtRemarks;
	}
	public int getIsReqDdCType() {
		return isReqDdCType;
	}
	public void setIsReqDdCType(int isReqDdCType) {
		this.isReqDdCType = isReqDdCType;
	}
	public int getLineItemId() {
		return lineItemId;
	}
	public void setLineItemId(int lineItemId) {
		this.lineItemId = lineItemId;
	}
	public String getLineItemName() {
		return lineItemName;
	}
	public void setLineItemName(String lineItemName) {
		this.lineItemName = lineItemName;
	}
	public int getSubLineItemId() {
		return subLineItemId;
	}
	public void setSubLineItemId(int subLineItemId) {
		this.subLineItemId = subLineItemId;
	}
	public String getSubLineItemName() {
		return subLineItemName;
	}
	public void setSubLineItemName(String subLineItemName) {
		this.subLineItemName = subLineItemName;
	}
	public int getIsReqLineItem() {
		return isReqLineItem;
	}
	public void setIsReqLineItem(int isReqLineItem) {
		this.isReqLineItem = isReqLineItem;
	}
	public int getIsReqSubLineItem() {
		return isReqSubLineItem;
	}
	public void setIsReqSubLineItem(int isReqSubLineItem) {
		this.isReqSubLineItem = isReqSubLineItem;
	}
	public int getIsReqTxtCEndDate() {
		return isReqTxtCEndDate;
	}
	public void setIsReqTxtCEndDate(int isReqTxtCEndDate) {
		this.isReqTxtCEndDate = isReqTxtCEndDate;
	}
	public int getIsReqTxtCFreqAmt() {
		return isReqTxtCFreqAmt;
	}
	public void setIsReqTxtCFreqAmt(int isReqTxtCFreqAmt) {
		this.isReqTxtCFreqAmt = isReqTxtCFreqAmt;
	}
	public int getIsReqTxtCFrequency() {
		return isReqTxtCFrequency;
	}
	public void setIsReqTxtCFrequency(int isReqTxtCFrequency) {
		this.isReqTxtCFrequency = isReqTxtCFrequency;
	}
	public int getIsReqTxtCName() {
		return isReqTxtCName;
	}
	public void setIsReqTxtCName(int isReqTxtCName) {
		this.isReqTxtCName = isReqTxtCName;
	}
	public int getIsReqTxtCPeriod() {
		return isReqTxtCPeriod;
	}
	public void setIsReqTxtCPeriod(int isReqTxtCPeriod) {
		this.isReqTxtCPeriod = isReqTxtCPeriod;
	}
	public int getIsReqTxtCStartDate() {
		return isReqTxtCStartDate;
	}
	public void setIsReqTxtCStartDate(int isReqTxtCStartDate) {
		this.isReqTxtCStartDate = isReqTxtCStartDate;
	}
	public int getIsReqTxtCTAmt() {
		return isReqTxtCTAmt;
	}
	public void setIsReqTxtCTAmt(int isReqTxtCTAmt) {
		this.isReqTxtCTAmt = isReqTxtCTAmt;
	}
	public long getServiceID() {
		return serviceID;
	}
	public void setServiceID(long serviceID) {
		this.serviceID = serviceID;
	}
	public long getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(long orderNo) {
		this.orderNo = orderNo;
	}
	public long getServiceProductID() {
		return serviceProductID;
	}
	public void setServiceProductID(long serviceProductID) {
		this.serviceProductID = serviceProductID;
	}
	public String getChargeInfoID_String() {
		return chargeInfoID_String;
	}
	public void setChargeInfoID_String(String chargeInfoID_String) {
		this.chargeInfoID_String = chargeInfoID_String;
	}
	public int getChargeInfoID() {
		return chargeInfoID;
	}
	public void setChargeInfoID(int chargeInfoID) {
		this.chargeInfoID = chargeInfoID;
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
	public double getChargeAmount() {
		return chargeAmount;
	}
	public void setChargeAmount(double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}
	public String getChargeAmount_String() {
		return chargeAmount_String;
	}
	public void setChargeAmount_String(String chargeAmount_String) {
		this.chargeAmount_String = chargeAmount_String;
	}
	public String getChargeFrequency() {
		return chargeFrequency;
	}
	public void setChargeFrequency(String chargeFrequency) {
		this.chargeFrequency = chargeFrequency;
	}
	public double getChargeFrequencyAmt() {
		return chargeFrequencyAmt;
	}
	public void setChargeFrequencyAmt(double chargeFrequencyAmt) {
		this.chargeFrequencyAmt = chargeFrequencyAmt;
	}
	public String getChargeFrequencyAmt_String() {
		return chargeFrequencyAmt_String;
	}
	public void setChargeFrequencyAmt_String(String chargeFrequencyAmt_String) {
		this.chargeFrequencyAmt_String = chargeFrequencyAmt_String;
	}
	public String getChargeName() {
		return chargeName;
	}
	public void setChargeName(String chargeName) {
		this.chargeName = chargeName;
	}
	public int getChargePeriod() {
		return chargePeriod;
	}
	public void setChargePeriod(int chargePeriod) {
		this.chargePeriod = chargePeriod;
	}
	public int getChargeType() {
		return chargeType;
	}
	public void setChargeType(int chargeType) {
		this.chargeType = chargeType;
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
	public String getEffectiveDateForRenewal() {
		return effectiveDateForRenewal;
	}
	public void setEffectiveDateForRenewal(String effectiveDateForRenewal) {
		this.effectiveDateForRenewal = effectiveDateForRenewal;
	}
	public long getNewChargeAmount() {
		return newChargeAmount;
	}
	public void setNewChargeAmount(long newChargeAmount) {
		this.newChargeAmount = newChargeAmount;
	}
	public String getReasonForRenewal() {
		return reasonForRenewal;
	}
	public void setReasonForRenewal(String reasonForRenewal) {
		this.reasonForRenewal = reasonForRenewal;
	}
	public int getEndDateDays() {
		return endDateDays;
	}
	public void setEndDateDays(int endDateDays) {
		this.endDateDays = endDateDays;
	}
	public int getEndDateMonth() {
		return endDateMonth;
	}
	public void setEndDateMonth(int endDateMonth) {
		this.endDateMonth = endDateMonth;
	}
	public int getStartDateDays() {
		return startDateDays;
	}
	public void setStartDateDays(int startDateDays) {
		this.startDateDays = startDateDays;
	}
	public int getStartDateMonth() {
		return startDateMonth;
	}
	public void setStartDateMonth(int startDateMonth) {
		this.startDateMonth = startDateMonth;
	}
	public int getChargeNameID() {
		return chargeNameID;
	}
	public void setChargeNameID(int chargeNameID) {
		this.chargeNameID = chargeNameID;
	}
	public String getChargeAnnotation() {
		return chargeAnnotation;
	}
	public void setChargeAnnotation(String chargeAnnotation) {
		this.chargeAnnotation = chargeAnnotation;
	}
	public int getIsReqTxtCAnnotation() {
		return isReqTxtCAnnotation;
	}
	public void setIsReqTxtCAnnotation(int isReqTxtCAnnotation) {
		this.isReqTxtCAnnotation = isReqTxtCAnnotation;
	}
	//Start[001]
	public int getIsReqTxtCEndDays() {
		return isReqTxtCEndDays;
	}
	public void setIsReqTxtCEndDays(int isReqTxtCEndDays) {
		this.isReqTxtCEndDays = isReqTxtCEndDays;
	}
	public int getIsReqTxtCEndMonths() {
		return isReqTxtCEndMonths;
	}
	public void setIsReqTxtCEndMonths(int isReqTxtCEndMonths) {
		this.isReqTxtCEndMonths = isReqTxtCEndMonths;
	}
	public int getIsReqTxtCStartDays() {
		return isReqTxtCStartDays;
	}
	public void setIsReqTxtCStartDays(int isReqTxtCStartDays) {
		this.isReqTxtCStartDays = isReqTxtCStartDays;
	}
	public int getIsReqTxtCStartMonths() {
		return isReqTxtCStartMonths;
	}
	public void setIsReqTxtCStartMonths(int isReqTxtCStartMonths) {
		this.isReqTxtCStartMonths = isReqTxtCStartMonths;
	}
	public int getExcludecheck() {
		return excludecheck;
	}
	public void setExcludecheck(int excludecheck) {
		this.excludecheck = excludecheck;
	}
	public String getChargeRemarks() {
		return chargeRemarks;
	}
	public void setChargeRemarks(String chargeRemarks) {
		this.chargeRemarks = chargeRemarks;
	}
	public int getPaymentTerm1() {
		return paymentTerm1;
	}
	public void setPaymentTerm1(int paymentTerm1) {
		this.paymentTerm1 = paymentTerm1;
	}
	public int getPaymentTerm2() {
		return paymentTerm2;
	}
	public void setPaymentTerm2(int paymentTerm2) {
		this.paymentTerm2 = paymentTerm2;
	}
	public int getPaymentTerm3() {
		return paymentTerm3;
	}
	public void setPaymentTerm3(int paymentTerm3) {
		this.paymentTerm3 = paymentTerm3;
	}
	public int getPaymentTerm4() {
		return paymentTerm4;
	}
	public void setPaymentTerm4(int paymentTerm4) {
		this.paymentTerm4 = paymentTerm4;
	}
	public int getDelayedTimeInDayes() {
		return delayedTimeInDayes;
	}
	public void setDelayedTimeInDayes(int delayedTimeInDayes) {
		this.delayedTimeInDayes = delayedTimeInDayes;
	}
	public String getLdDateClause() {
		return ldDateClause;
	}
	public void setLdDateClause(String ldDateClause) {
		this.ldDateClause = ldDateClause;
	}
	public int getLdPercentage() {
		return ldPercentage;
	}
	public void setLdPercentage(int ldPercentage) {
		this.ldPercentage = ldPercentage;
	}
	public int getMaxPercentage() {
		return maxPercentage;
	}
	public void setMaxPercentage(int maxPercentage) {
		this.maxPercentage = maxPercentage;
	}
	public String getTaxRate() {
		return taxRate;
	}
	public void setTaxRate(String taxRate) {
		this.taxRate = taxRate;
	}
	
	
	//End[001]
	
}
