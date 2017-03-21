package com.ibm.ioes.newdesign.dto;

import java.sql.Date;

public class AdvancePaymentDTO {
	
	Integer advanceId;
	String arcExempted;
	String arcExpReason;
	String arcChqNo;
	String arcChqDate;
	String arcBankName;
	Double arcChqAmt;
	Double arcAmtAjd;
	String otcExempted;
	String otcExpReason;
	String otcChqNo;
	String otcChqDate;
	String otcBankName;
	Double otcChqAmt;
	Double otcAmtAjd;
	Integer spId;
	Integer orderNo;
	Integer lsiNo;
	Integer createdBy;
	Date createdDate;
	Long serviceNo;
	Long lastInsertHelperId;
	
	//raveendra add
	String arcReEnterCheckNumber;
	Double arcReEnterCheckamount;
	String arcBankAccountNumber;
	String arcReEnterBankAccountNumber;
	String arcIfscCode;
	String arcReEnterIfscCode;
	String otcReEnterCheckNumber;
	Double otcReEnterCheckamount;
	String otcBankAccountNumber;
	String otcReEnterBankAccountNumber;
	String otcIfscCode;
	String otcReEnterIfscCode;
	//ended
	
	public Integer getAdvanceId() {
		return advanceId;
	}
	public void setAdvanceId(Integer advanceId) {
		this.advanceId = advanceId;
	}
	public String getArcExempted() {
		return arcExempted;
	}
	public void setArcExempted(String arcExempted) {
		this.arcExempted = arcExempted;
	}
	public String getArcExpReason() {
		return arcExpReason;
	}
	public void setArcExpReason(String arcExpReason) {
		this.arcExpReason = arcExpReason;
	}
	public String getArcChqNo() {
		return arcChqNo;
	}
	public void setArcChqNo(String arcChqNo) {
		this.arcChqNo = arcChqNo;
	}
	public String getArcChqDate() {
		return arcChqDate;
	}
	public void setArcChqDate(String arcChqDate) {
		this.arcChqDate = arcChqDate;
	}
	public String getArcBankName() {
		return arcBankName;
	}
	public void setArcBankName(String arcBankName) {
		this.arcBankName = arcBankName;
	}
	public Double getArcChqAmt() {
		return arcChqAmt;
	}
	public void setArcChqAmt(Double arcChqAmt) {
		this.arcChqAmt = arcChqAmt;
	}
	public Double getArcAmtAjd() {
		return arcAmtAjd;
	}
	public void setArcAmtAjd(Double arcAmtAjd) {
		this.arcAmtAjd = arcAmtAjd;
	}
	public String getOtcExempted() {
		return otcExempted;
	}
	public void setOtcExempted(String otcExempted) {
		this.otcExempted = otcExempted;
	}
	public String getOtcExpReason() {
		return otcExpReason;
	}
	public void setOtcExpReason(String otcExpReason) {
		this.otcExpReason = otcExpReason;
	}
	public String getOtcChqNo() {
		return otcChqNo;
	}
	public void setOtcChqNo(String otcChqNo) {
		this.otcChqNo = otcChqNo;
	}
	public String getOtcChqDate() {
		return otcChqDate;
	}
	public void setOtcChqDate(String otcChqDate) {
		this.otcChqDate = otcChqDate;
	}
	public String getOtcBankName() {
		return otcBankName;
	}
	public void setOtcBankName(String otcBankName) {
		this.otcBankName = otcBankName;
	}
	public Double getOtcChqAmt() {
		return otcChqAmt;
	}
	public void setOtcChqAmt(Double otcChqAmt) {
		this.otcChqAmt = otcChqAmt;
	}
	public Double getOtcAmtAjd() {
		return otcAmtAjd;
	}
	public void setOtcAmtAjd(Double otcAmtAjd) {
		this.otcAmtAjd = otcAmtAjd;
	}
	public Integer getSpId() {
		return spId;
	}
	public void setSpId(Integer spId) {
		this.spId = spId;
	}
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	public Integer getLsiNo() {
		return lsiNo;
	}
	public void setLsiNo(Integer lsiNo) {
		this.lsiNo = lsiNo;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Long getServiceNo() {
		return serviceNo;
	}
	public void setServiceNo(Long serviceNo) {
		this.serviceNo = serviceNo;
	}
	public Long getLastInsertHelperId() {
		return lastInsertHelperId;
	}
	public void setLastInsertHelperId(Long lastInsertHelperId) {
		this.lastInsertHelperId = lastInsertHelperId;
	}
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
	
	
}
