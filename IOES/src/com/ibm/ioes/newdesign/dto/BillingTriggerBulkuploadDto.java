package com.ibm.ioes.newdesign.dto;

public class BillingTriggerBulkuploadDto {
	private Long lineNumber;
	private Long parrellelUpgradeLsiNo;
	private Long chaneTypeid;
	private String m6_fx_progress_status;
	private Long subchangeTypeid;
	public Long getSubchangeTypeid() {
		return subchangeTypeid;
	}
	public void setSubchangeTypeid(Long subchangeTypeid) {
		this.subchangeTypeid = subchangeTypeid;
	}
	public Long getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(Long lineNumber) {
		this.lineNumber = lineNumber;
	}
	public Long getParrellelUpgradeLsiNo() {
		return parrellelUpgradeLsiNo;
	}
	public void setParrellelUpgradeLsiNo(Long parrellelUpgradeLsiNo) {
		this.parrellelUpgradeLsiNo = parrellelUpgradeLsiNo;
	}
	public Long getChaneTypeid() {
		return chaneTypeid;
	}
	public void setChaneTypeid(Long chaneTypeid) {
		this.chaneTypeid = chaneTypeid;
	}
	public String getM6_fx_progress_status() {
		return m6_fx_progress_status;
	}
	public void setM6_fx_progress_status(String m6_fx_progress_status) {
		this.m6_fx_progress_status = m6_fx_progress_status;
	}
	
	

}
