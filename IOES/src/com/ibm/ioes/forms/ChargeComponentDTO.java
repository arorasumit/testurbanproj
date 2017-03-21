// [001] VIPIN SAHARIA 04-06-2014 Added fxChargeId required for extra logic DC_COLO_SERVICE_TAX Charge (Hardware DC)
// [002] Gunjan Singla  2-sept-15 20150603-R2-021385    Charge End Date and Short Billing
package com.ibm.ioes.forms;

public class ChargeComponentDTO {

	private int sourceProductId;
	private int destinationProductId;
	private int chargeInfoID;
	private String chargeName;
	private String chargeAnnotation;
	private int chargePeriod;
	private int chargeType;
	private double chargeAmount;
	private String chargeFrequency;
	private double chargeFrequencyAmt;
	private String startDateLogic;
	private String endDateLogic;
	private int startDateDays;
	private int startDateMonth;
	private int endDateDays;
	private int endDateMonth;
	private int chargeNameID;
	private long created_serviceid;
	private int  excludecheck;
	private String chargeRemarks;
	private String startDate = "";
	private String endDate = "";
	private int paymentTerm1;
	private int paymentTerm2;
	private int paymentTerm3;
	private int paymentTerm4;
	private int lineItemId;
	private int sublineItemId;
	private String charge_creation_source="";
	private String ldDateClause;
	private String delayedTimeInDayes;
	private String ldPercentage;
	private String maxPercentage;
	private String chargeTypeName;
	private int chargeTypeID;
	private int maxPageNo;
	private double totalChargeAmount=0l;
	//Puneet for line item & subline item type
	private String lineItemType;
	private String subLineItemType;
	private String taxRate;
	private String chargeNameVal;
	private int isdisconnected = 0; 
	private String disconnected_orderno;
	private int isDifferential;
	private long differentialChargeId;
	private long obLinkChargeId;
	public long getObLinkChargeId() {
		return obLinkChargeId;
	}
	public void setObLinkChargeId(long obLinkChargeId) {
		this.obLinkChargeId = obLinkChargeId;
	}
	private String rcActiveDateCrossed;
	private String nrcActiveDateCrossed;
	private String rcInactiveDateCrossed;
	private String disconnection_type;
	private String poNoOfCharge;
	private int fxChargeId;
	//[002] start
	private int isRedisconnected = 0; 
	//[002] end
	public String getPoNoOfCharge() {
		return poNoOfCharge;
	}
	public void setPoNoOfCharge(String poNoOfCharge) {
		this.poNoOfCharge = poNoOfCharge;
	}
	public int getIsdisconnected() {
		return isdisconnected;
	}
	public void setIsdisconnected(int isdisconnected) {
		this.isdisconnected = isdisconnected;
	}
	public String getDisconnected_orderno() {
		return disconnected_orderno;
	}
	public void setDisconnected_orderno(String disconnected_orderno) {
		this.disconnected_orderno = disconnected_orderno;
	}
	public int getIsDifferential() {
		return isDifferential;
	}
	public void setIsDifferential(int isDifferential) {
		this.isDifferential = isDifferential;
	}
	public long getDifferentialChargeId() {
		return differentialChargeId;
	}
	public void setDifferentialChargeId(long differentialChargeId) {
		this.differentialChargeId = differentialChargeId;
	}
	public String getRcActiveDateCrossed() {
		return rcActiveDateCrossed;
	}
	public void setRcActiveDateCrossed(String rcActiveDateCrossed) {
		this.rcActiveDateCrossed = rcActiveDateCrossed;
	}
	public String getNrcActiveDateCrossed() {
		return nrcActiveDateCrossed;
	}
	public void setNrcActiveDateCrossed(String nrcActiveDateCrossed) {
		this.nrcActiveDateCrossed = nrcActiveDateCrossed;
	}
	public String getRcInactiveDateCrossed() {
		return rcInactiveDateCrossed;
	}
	public void setRcInactiveDateCrossed(String rcInactiveDateCrossed) {
		this.rcInactiveDateCrossed = rcInactiveDateCrossed;
	}
	public String getDisconnection_type() {
		return disconnection_type;
	}
	public void setDisconnection_type(String disconnection_type) {
		this.disconnection_type = disconnection_type;
	}
	public String getChargeNameVal() {
		return chargeNameVal;
	}
	public void setChargeNameVal(String chargeNameVal) {
		this.chargeNameVal = chargeNameVal;
	}
	public String getTaxRate() {
		return taxRate;
	}
	public void setTaxRate(String taxRate) {
		this.taxRate = taxRate;
	}
	public String getLineItemType() {
		return lineItemType;
	}
	public void setLineItemType(String lineItemType) {
		this.lineItemType = lineItemType;
	}
	public String getSubLineItemType() {
		return subLineItemType;
	}
	public void setSubLineItemType(String subLineItemType) {
		this.subLineItemType = subLineItemType;
	}
	public int getMaxPageNo() {
		return maxPageNo;
	}
	public void setMaxPageNo(int maxPageNo) {
		this.maxPageNo = maxPageNo;
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

	public String getMaxPercentage() {
		return maxPercentage;
	}
	public void setMaxPercentage(String maxPercentage) {
		this.maxPercentage = maxPercentage;
	}
	public String getLdPercentage() {
		return ldPercentage;
	}
	public void setLdPercentage(String ldPercentage) {
		this.ldPercentage = ldPercentage;
	}
	public String getDelayedTimeInDayes() {
		return delayedTimeInDayes;
	}
	public void setDelayedTimeInDayes(String delayedTimeInDayes) {
		this.delayedTimeInDayes = delayedTimeInDayes;
	}
	public String getLdDateClause() {
		return ldDateClause;
	}
	public void setLdDateClause(String ldDateClause) {
		this.ldDateClause = ldDateClause;
	}
	public String getCharge_creation_source() {
		return charge_creation_source;
	}


	public void setCharge_creation_source(String charge_creation_source) {
		this.charge_creation_source = charge_creation_source;
	}
	public int getSublineItemId() {
		return sublineItemId;
	}
	public void setSublineItemId(int sublineItemId) {
		this.sublineItemId = sublineItemId;
	}
	public int getLineItemId() {
		return lineItemId;
	}
	public void setLineItemId(int lineItemId) {
		this.lineItemId = lineItemId;
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
	public int getPaymentTerm2() {
		return paymentTerm2;
	}


	public void setPaymentTerm2(int paymentTerm2) {
		this.paymentTerm2 = paymentTerm2;
	}

	public int getPaymentTerm1() {
		return paymentTerm1;
	}


	public void setPaymentTerm1(int paymentTerm1) {
		this.paymentTerm1 = paymentTerm1;
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

	public void setChargeRemarks(String chargeRemarks) {
		this.chargeRemarks = chargeRemarks;
	}
	public String getChargeRemarks() {
		return chargeRemarks;
	}
	public int getExcludecheck() {
		return excludecheck;
	}


	public void setExcludecheck(int excludecheck) {
		this.excludecheck = excludecheck;
	}

	public long getCreated_serviceid() {
		return created_serviceid;
	}


	public void setCreated_serviceid(long created_serviceid) {
		this.created_serviceid = created_serviceid;
	}
	public int getChargeNameID() {
		return chargeNameID;
	}


	public void setChargeNameID(int chargeNameID) {
		this.chargeNameID = chargeNameID;
	}
	public int getEndDateMonth() {
		return endDateMonth;
	}


	public void setEndDateMonth(int endDateMonth) {
		this.endDateMonth = endDateMonth;
	}
	public int getEndDateDays() {
		return endDateDays;
	}


	public void setEndDateDays(int endDateDays) {
		this.endDateDays = endDateDays;
	}

	public int getStartDateMonth() {
		return startDateMonth;
	}


	public void setStartDateMonth(int startDateMonth) {
		this.startDateMonth = startDateMonth;
	}
	public int getStartDateDays() {
		return startDateDays;
	}


	public void setStartDateDays(int startDateDays) {
		this.startDateDays = startDateDays;
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

	public double getChargeFrequencyAmt() {
		return chargeFrequencyAmt;
	}

	public void setChargeFrequencyAmt(double chargeFrequencyAmt) {
		this.chargeFrequencyAmt = chargeFrequencyAmt;
	}
	public String getChargeFrequency() {
		return chargeFrequency;
	}

	public void setChargeFrequency(String chargeFrequency) {
		this.chargeFrequency = chargeFrequency;
	}
	public double getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}
	public int getChargeType() {
		return chargeType;}
	

	public void setChargeType(int chargeType) {
		this.chargeType = chargeType;}
	public int getChargePeriod() {
		return chargePeriod;
	}

	public void setChargePeriod(int chargePeriod) {
		this.chargePeriod = chargePeriod;
	}
	public String getChargeAnnotation() {
		return chargeAnnotation;
	}


	public void setChargeAnnotation(String chargeAnnotation) {
		this.chargeAnnotation = chargeAnnotation;
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
	public int getDestinationProductId() {
		return destinationProductId;
	}


	public void setDestinationProductId(int destinationProductId) {
		this.destinationProductId = destinationProductId;
	}


	public int getSourceProductId() {
		return sourceProductId;


	}


	public void setSourceProductId(int sourceProductId) {
		this.sourceProductId = sourceProductId;
	}
	public double getTotalChargeAmount() {
		return totalChargeAmount;
	}
	public void setTotalChargeAmount(double totalChargeAmount) {
		this.totalChargeAmount = totalChargeAmount;
	}
	public int getFxChargeId() {
		return fxChargeId;
	}
	public void setFxChargeId(int fxChargeId) {
		this.fxChargeId = fxChargeId;
	}
	//[002] start
	public int getIsRedisconnected() {
		return isRedisconnected;
	}
	public void setIsRedisconnected(int isRedisconnected) {
		this.isRedisconnected = isRedisconnected;
	}
	//[002] end
}
