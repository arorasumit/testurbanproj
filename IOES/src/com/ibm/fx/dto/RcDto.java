package com.ibm.fx.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class RcDto {
	
	private String token_id = null; // added by sandeep on 22-jun-2011
	private Long rowId = null;
	
	private Integer elementId = null;
	private Integer openItemId = null;
	//private Date productActiveDate = null;
	private Date billingActiveDate = null;
	private Date billingEndDate = null;
	private Integer inArrearsOverride = null;
	private String billPeriod = null;
	private String orderNumber= null;
	private String overrideRate= null;
	private String fx_Ext_Account_No;
	private String fx_Level;
	private Integer chargeinfoid = null;
	private Integer subScrNo = null;
	//private Integer trackingId = null;
	//private Integer trackingIdServ = null;
	private Integer returnStatus=null;
	
	ArrayList<FxExternalIdDto> externalIds = null;
	
	private String extId;
	
	private Exception exception;
	private String exceptionMessage = null;
	
	private String viewId=null;
	private Integer trackingId=null;
	private Integer trackingIdServ=null;
	
	int saveStatus;
	Integer accountInternalId;
	
	ArrayList<ExtendedData> extendedData=null;
	
	ArrayList<ExtendedData> orderExtendedData=null;
	
	private Integer isDifferentialForFX = null;
	DisconnectionDto linkedDisconnectionCharge = null;
	private boolean isDifferentialHitInFX ;
	private boolean isDataIssueException;

	public boolean isDataIssueException() {
		return isDataIssueException;
	}

	public void setDataIssueException(boolean isDataIssueException) {
		this.isDataIssueException = isDataIssueException;
	}
	
	
	public ArrayList<ExtendedData> getExtendedData() {
		return extendedData;
	}
	public void setExtendedData(ArrayList<ExtendedData> extendedData) {
		this.extendedData = extendedData;
	}
	public Integer getAccountInternalId() {
		return accountInternalId;
	}
	public void setAccountInternalId(Integer accountInternalId) {
		this.accountInternalId = accountInternalId;
	}
	public int getSaveStatus() {
		return saveStatus;
	}
	public void setSaveStatus(int saveStatus) {
		this.saveStatus = saveStatus;
	}
	public String getViewId() {
		return viewId;
	}
	public void setViewId(String viewId) {
		this.viewId = viewId;
	}
	public Date getBillingActiveDate() {
		return billingActiveDate;
	}
	public void setBillingActiveDate(Date billingActiveDate) {
		this.billingActiveDate = billingActiveDate;
	}
	public String getBillPeriod() {
		return billPeriod;
	}
	public void setBillPeriod(String billPeriod) {
		this.billPeriod = billPeriod;
	}
	public Integer getElementId() {
		return elementId;
	}
	public void setElementId(Integer elementId) {
		this.elementId = elementId;
	}
	public Integer getInArrearsOverride() {
		return inArrearsOverride;
	}
	public void setInArrearsOverride(Integer inArrearsOverride) {
		this.inArrearsOverride = inArrearsOverride;
	}
	public Integer getOpenItemId() {
		return openItemId;
	}
	public void setOpenItemId(Integer openItemId) {
		this.openItemId = openItemId;
	}
	
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	/*public Date getProductActiveDate() {
		return productActiveDate;
	}
	public void setProductActiveDate(Date productActiveDate) {
		this.productActiveDate = productActiveDate;
	}*/
	public String getOverrideRate() {
		return overrideRate;
	}
	public void setOverrideRate(String overrideRate) {
		this.overrideRate = overrideRate;
	}
	
	public Long getRowId() {
		return rowId;
	}
	public void setRowId(Long rowId) {
		this.rowId = rowId;
	}
	public Date getBillingEndDate() {
		return billingEndDate;
	}
	public void setBillingEndDate(Date billingEndDate) {
		this.billingEndDate = billingEndDate;
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
	public Integer getChargeinfoid() {
		return chargeinfoid;
	}
	public void setChargeinfoid(Integer chargeinfoid) {
		this.chargeinfoid = chargeinfoid;
	}
	public Integer getSubScrNo() {
		return subScrNo;
	}
	public void setSubScrNo(Integer subScrNo) {
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
	public ArrayList<FxExternalIdDto> getExternalIds() {
		return externalIds;
	}
	public void setExternalIds(ArrayList<FxExternalIdDto> externalIds) {
		this.externalIds = externalIds;
	}
	public String getExtId() {
		return extId;
	}
	public void setExtId(String extId) {
		this.extId = extId;
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
	public Integer getTrackingId() {
		return trackingId;
	}
	public void setTrackingId(Integer trackingId) {
		this.trackingId = trackingId;
	}
	public Integer getTrackingIdServ() {
		return trackingIdServ;
	}
	public void setTrackingIdServ(Integer trackingIdServ) {
		this.trackingIdServ = trackingIdServ;
	}
	public DisconnectionDto getLinkedDisconnectionCharge() {
		return linkedDisconnectionCharge;
	}
	public void setLinkedDisconnectionCharge(
			DisconnectionDto linkedDisconnectionCharge) {
		this.linkedDisconnectionCharge = linkedDisconnectionCharge;
	}
	public Integer getIsDifferentialForFX() {
		return isDifferentialForFX;
	}
	public void setIsDifferentialForFX(Integer isDifferentialForFX) {
		this.isDifferentialForFX = isDifferentialForFX;
	}
	public boolean isDifferentialHitInFX() {
		return isDifferentialHitInFX;
	}
	public void setDifferentialHitInFX(boolean isDifferentialHitInFX) {
		this.isDifferentialHitInFX = isDifferentialHitInFX;
	}
	
	
}
