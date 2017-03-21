package com.ibm.fx.dto;

import java.util.Date;

public class ChargeRedirectDTO {
	
	
	private Long rowId = null;
	
	private int accountno;
	
	private int balanceAccountNo;
	
	private String accountExternalid = null;
	
	private String balanceAccountExternalid =null;
		
	private int subscrNo;
	
	private int subscrNoReset ;
	
	private Exception exception = null;
	
	private String exceptionMessage = null;
	
	private int returnStatus=0;
	
	private Date redirectionActiveDate=null;
	
	private int trackingid;
	
	private int trackingIdReset;

	public long orderNo ;
	
	public long custom_child_accts_grouping_id;
	public long getCustom_child_accts_grouping_id() {
		return custom_child_accts_grouping_id;
	}

	public void setCustom_child_accts_grouping_id(
			long custom_child_accts_grouping_id) {
		this.custom_child_accts_grouping_id = custom_child_accts_grouping_id;
	}

	public long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(long orderNo) {
		this.orderNo = orderNo;
	}

	public int getTrackingid() {
		return trackingid;
	}

	public void setTrackingid(int trackingid) {
		this.trackingid = trackingid;
	}

	public int getTrackingIdReset() {
		return trackingIdReset;
	}

	public void setTrackingIdReset(int trackingIdReset) {
		this.trackingIdReset = trackingIdReset;
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

	public int getReturnStatus() {
		return returnStatus;
	}

	public void setReturnStatus(int returnStatus) {
		this.returnStatus = returnStatus;
	}

	public int getSubscrNo() {
		return subscrNo;
	}

	public void setSubscrNo(int subscrNo) {
		this.subscrNo = subscrNo;
	}

	public int getSubscrNoReset() {
		return subscrNoReset;
	}

	public void setSubscrNoReset(int subscrNoReset) {
		this.subscrNoReset = subscrNoReset;
	}

	public int getAccountno() {
		return accountno;
	}

	public void setAccountno(int accountno) {
		this.accountno = accountno;
	}

	public String getBalanceAccountExternalid() {
		return balanceAccountExternalid;
	}

	public void setBalanceAccountExternalid(String balanceAccountExternalid) {
		this.balanceAccountExternalid = balanceAccountExternalid;
	}

	public int getBalanceAccountNo() {
		return balanceAccountNo;
	}

	public void setBalanceAccountNo(int balanceAccountNo) {
		this.balanceAccountNo = balanceAccountNo;
	}

	public Date getRedirectionActiveDate() {
		return redirectionActiveDate;
	}

	public void setRedirectionActiveDate(Date redirectionActiveDate) {
		this.redirectionActiveDate = redirectionActiveDate;
	}

	public Long getRowId() {
		return rowId;
	}

	public void setRowId(Long rowId) {
		this.rowId = rowId;
	}

	public String getAccountExternalid() {
		return accountExternalid;
	}

	public void setAccountExternalid(String accountExternalid) {
		this.accountExternalid = accountExternalid;
	}

}
