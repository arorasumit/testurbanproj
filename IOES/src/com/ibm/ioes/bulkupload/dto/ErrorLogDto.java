package com.ibm.ioes.bulkupload.dto;

public class ErrorLogDto {
	private String errorSheet;
	private String errorLogValue;
	private String resultSheet;
	private String resultLogValue;
	private String orderNo;
	
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getErrorLogValue() {
		return errorLogValue;
	}
	public void setErrorLogValue(String errorLogValue) {
		this.errorLogValue = errorLogValue;
	}
	public String getErrorSheet() {
		return errorSheet;
	}
	public void setErrorSheet(String errorSheet) {
		this.errorSheet = errorSheet;
	}
	public String getResultLogValue() {
		return resultLogValue;
	}
	public void setResultLogValue(String resultLogValue) {
		this.resultLogValue = resultLogValue;
	}
	public String getResultSheet() {
		return resultSheet;
	}
	public void setResultSheet(String resultSheet) {
		this.resultSheet = resultSheet;
	}
}
