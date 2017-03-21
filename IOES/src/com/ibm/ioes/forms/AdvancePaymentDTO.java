package com.ibm.ioes.forms;

import java.util.ArrayList;

public class AdvancePaymentDTO {
	
	private long advanceId;
	private String arcAdvancePaymentExemptd;
	private String arcExemptionReason;
	private String arcCheckNumber;
	private String arcCheckDate;
	private String arcBankName;
	private Double arcCheckamount;
	private Double arcAudjestedAmount;
	//raveendra add
	private String arcReEnterCheckNumber;
	private Double arcReEnterCheckamount;
	private String arcBankAccountNumber;
	private String arcReEnterBankAccountNumber;
	private String arcIfscCode;
	private String arcReEnterIfscCode;
 	//end
	
	private String otcAdvancPaymentExemptd;
	private String otcExemptdReason;
	private String otcCheckDate;
	private Double otcCheckamount;
	private String otcCheckNumber;
	private String otcBankName;
	private Double otcAudjestedAmount;
	
	//raveendra added
	private String otcReEnterCheckNumber;
	private Double otcReEnterCheckamount;
	private String otcBankAccountNumber;
	private String otcReEnterBankAccountNumber;
	private String otcIfscCode;
	private String otcReEnterIfscCode;
	
	//ended
	
	private long sipId;
	private long serviceNo;
	private long lsiNo;
	private long orderNo;
	private long lineItemNo;
	private ArrayList errors;
	private String errorMsg;
	private int errorStatus;
	
	
	
	public ArrayList getErrors() {
		return errors;
	}
	public void setErrors(ArrayList errors) {
		this.errors = errors;
	}
	public long getAdvanceId() {
		return advanceId;
	}
	public void setAdvanceId(long advanceId) {
		this.advanceId = advanceId;
	}
	public String getArcAdvancePaymentExemptd() {
		return arcAdvancePaymentExemptd;
	}
	public void setArcAdvancePaymentExemptd(String arcAdvancePaymentExemptd) {
		this.arcAdvancePaymentExemptd = arcAdvancePaymentExemptd;
	}
	public String getArcBankName() {
		return arcBankName;
	}
	public void setArcBankName(String arcBankName) {
		this.arcBankName = arcBankName;
	}
	
	public String getArcCheckDate() {
		return arcCheckDate;
	}
	public void setArcCheckDate(String arcCheckDate) {
		this.arcCheckDate = arcCheckDate;
	}
	public String getArcCheckNumber() {
		return arcCheckNumber;
	}
	public void setArcCheckNumber(String arcCheckNumber) {
		this.arcCheckNumber = arcCheckNumber;
	}
	
	public String getArcExemptionReason() {
		return arcExemptionReason;
	}
	public void setArcExemptionReason(String arcExemptionReason) {
		this.arcExemptionReason = arcExemptionReason;
	}
	public String getOtcAdvancPaymentExemptd() {
		return otcAdvancPaymentExemptd;
	}
	public void setOtcAdvancPaymentExemptd(String otcAdvancPaymentExemptd) {
		this.otcAdvancPaymentExemptd = otcAdvancPaymentExemptd;
	}

	public Double getArcCheckamount() {
		return arcCheckamount;
	}
	public void setArcCheckamount(Double arcCheckamount) {
		this.arcCheckamount = arcCheckamount;
	}
	public Double getArcAudjestedAmount() {
		return arcAudjestedAmount;
	}
	public void setArcAudjestedAmount(Double arcAudjestedAmount) {
		this.arcAudjestedAmount = arcAudjestedAmount;
	}
	public Double getOtcCheckamount() {
		return otcCheckamount;
	}
	public void setOtcCheckamount(Double otcCheckamount) {
		this.otcCheckamount = otcCheckamount;
	}
	public Double getOtcAudjestedAmount() {
		return otcAudjestedAmount;
	}
	public void setOtcAudjestedAmount(Double otcAudjestedAmount) {
		this.otcAudjestedAmount = otcAudjestedAmount;
	}
	public String getOtcCheckDate() {
		return otcCheckDate;
	}
	public void setOtcCheckDate(String otcCheckDate) {
		this.otcCheckDate = otcCheckDate;
	}
	public String getOtcCheckNumber() {
		return otcCheckNumber;
	}
	public void setOtcCheckNumber(String otcCheckNumber) {
		this.otcCheckNumber = otcCheckNumber;
	}
	
	public String getOtcBankName() {
		return otcBankName;
	}
	public void setOtcBankName(String otcBankName) {
		this.otcBankName = otcBankName;
	}
	public String getOtcExemptdReason() {
		return otcExemptdReason;
	}
	public void setOtcExemptdReason(String otcExemptdReason) {
		this.otcExemptdReason = otcExemptdReason;
	}

	public long getSipId() {
		return sipId;
	}
	public void setSipId(long sipId) {
		this.sipId = sipId;
	}
	public long getLsiNo() {
		return lsiNo;
	}
	public void setLsiNo(long lsiNo) {
		this.lsiNo = lsiNo;
	}
	public long getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(long orderNo) {
		this.orderNo = orderNo;
	}
	public long getServiceNo() {
		return serviceNo;
	}
	public void setServiceNo(long serviceNo) {
		this.serviceNo = serviceNo;
	}
	public long getLineItemNo() {
		return lineItemNo;
	}
	public void setLineItemNo(long lineItemNo) {
		this.lineItemNo = lineItemNo;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public int getErrorStatus() {
		return errorStatus;
	}
	public void setErrorStatus(int errorStatus) {
		this.errorStatus = errorStatus;
	}
	
	
	//Raveendra added
	public String getArcReEnterCheckNumber() {
		return arcReEnterCheckNumber;
	}
	public void setArcReEnterCheckNumber(String arcReEnterCheckNumber) {
		this.arcReEnterCheckNumber = arcReEnterCheckNumber;
	}
	
	public Double getArcReEnterCheckamount() {
		return arcReEnterCheckamount;
	}
	public void setArcReEnterCheckamount(Double arcReEnterCheckamount) {
		this.arcReEnterCheckamount = arcReEnterCheckamount;
	}
	public String getArcBankAccountNumber() {
		return arcBankAccountNumber;
	}
	public void setArcBankAccountNumber(String arcBankAccountNumber) {
		this.arcBankAccountNumber = arcBankAccountNumber;
	}
	public String getArcReEnterBankAccountNumber() {
		return arcReEnterBankAccountNumber;
	}
	public void setArcReEnterBankAccountNumber(String arcReEnterBankAccountNumber) {
		this.arcReEnterBankAccountNumber = arcReEnterBankAccountNumber;
	}
	public String getArcIfscCode() {
		return arcIfscCode;
	}
	public void setArcIfscCode(String arcIfscCode) {
		this.arcIfscCode = arcIfscCode;
	}
	public String getArcReEnterIfscCode() {
		return arcReEnterIfscCode;
	}
	public void setArcReEnterIfscCode(String arcReEnterIfscCode) {
		this.arcReEnterIfscCode = arcReEnterIfscCode;
	}
	public String getOtcReEnterCheckNumber() {
		return otcReEnterCheckNumber;
	}
	public void setOtcReEnterCheckNumber(String otcReEnterCheckNumber) {
		this.otcReEnterCheckNumber = otcReEnterCheckNumber;
	}
	
	public Double getOtcReEnterCheckamount() {
		return otcReEnterCheckamount;
	}
	public void setOtcReEnterCheckamount(Double otcReEnterCheckamount) {
		this.otcReEnterCheckamount = otcReEnterCheckamount;
	}
	public String getOtcBankAccountNumber() {
		return otcBankAccountNumber;
	}
	public void setOtcBankAccountNumber(String otcBankAccountNumber) {
		this.otcBankAccountNumber = otcBankAccountNumber;
	}
	public String getOtcReEnterBankAccountNumber() {
		return otcReEnterBankAccountNumber;
	}
	public void setOtcReEnterBankAccountNumber(String otcReEnterBankAccountNumber) {
		this.otcReEnterBankAccountNumber = otcReEnterBankAccountNumber;
	}
	public String getOtcIfscCode() {
		return otcIfscCode;
	}
	public void setOtcIfscCode(String otcIfscCode) {
		this.otcIfscCode = otcIfscCode;
	}
	public String getOtcReEnterIfscCode() {
		return otcReEnterIfscCode;
	}
	public void setOtcReEnterIfscCode(String otcReEnterIfscCode) {
		this.otcReEnterIfscCode = otcReEnterIfscCode;
	}
	
	
//ended

}
