package com.ibm.fx.dto;

import java.util.ArrayList;
import java.util.Date;

public class NrcDto {
	
	private String token_id = null; // added by sandeep on 22-jun-2011
	private Long rowId = null;
	
	private Integer typeidNrc = null;
	private Integer currencyCode = null;
	private Date effectiveDate = null;
	private String annotation= null;
	private String nrcAmount=null;
	private String viewId = null;
	private Integer chargeinfoid = null;
	private String fx_Ext_Account_No=null;
	private String fx_Level=null;
	private String subScrNo=null;
	private Integer returnStatus=null;
	
	private Exception exception;
	private String exceptionMessage = null;
	
	ArrayList<ExtendedData> extendedData=null;
	
	ArrayList<ExtendedData> orderExtendedData=null;
	
	private String extId;
	
	
    private boolean isDataIssueException;


	public boolean isDataIssueException() {
		return isDataIssueException;
	}

	public void setDataIssueException(boolean isDataIssueException) {
		this.isDataIssueException = isDataIssueException;
	}
	
	
	public String getExtId() {
		return extId;
	}
	public void setExtId(String extId) {
		this.extId = extId;
	}
	public ArrayList<ExtendedData> getExtendedData() {
		return extendedData;
	}
	public void setExtendedData(ArrayList<ExtendedData> extendedData) {
		this.extendedData = extendedData;
	}
	public String getAnnotation() {
		return annotation;
	}
	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}
	public Integer getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(Integer currencyCode) {
		this.currencyCode = currencyCode;
	}
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public String getNrcAmount() {
		return nrcAmount;
	}
	public void setNrcAmount(String nrcAmount) {
		this.nrcAmount = nrcAmount;
	}
	
	public Integer getTypeidNrc() {
		return typeidNrc;
	}
	public void setTypeidNrc(Integer typeidNrc) {
		this.typeidNrc = typeidNrc;
	}
	public String getViewId() {
		return viewId;
	}
	public void setViewId(String viewId) {
		this.viewId = viewId;
	}
	public Long getRowId() {
		return rowId;
	}
	public void setRowId(Long rowId) {
		this.rowId = rowId;
	}
	public Integer getChargeinfoid() {
		return chargeinfoid;
	}
	public void setChargeinfoid(Integer chargeinfoid) {
		this.chargeinfoid = chargeinfoid;
	}
	public String getFx_Ext_Account_No() {
		return fx_Ext_Account_No;
	}
	public void setFx_Ext_Account_No(String fx_Ext_Account_No) {
		this.fx_Ext_Account_No = fx_Ext_Account_No;
	}
	public String getFx_Level() {
		return fx_Level;
	}
	public void setFx_Level(String fx_Level) {
		this.fx_Level = fx_Level;
	}
	public String getSubScrNo() {
		return subScrNo;
	}
	public void setSubScrNo(String subScrNo) {
		this.subScrNo = subScrNo;
	}
	public Integer getReturnStatus() {
		return returnStatus;
	}
	public void setReturnStatus(Integer returnStatus) {
		this.returnStatus = returnStatus;
	}
	public Exception getException() {
		return exception;
	}
	public void setException(Exception exception) {
		this.exception = exception;
	}
	public String getExceptionMessage() {
		return exceptionMessage;
	}
	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}
	public String getToken_id() {
		return token_id;
	}
	public void setToken_id(String token_id) {
		this.token_id = token_id;
	}
	public ArrayList<ExtendedData> getOrderExtendedData() {
		return orderExtendedData;
	}
	public void setOrderExtendedData(ArrayList<ExtendedData> orderExtendedData) {
		this.orderExtendedData = orderExtendedData;
	}	
	
	
}
