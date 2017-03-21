package com.ibm.ioes.newdesign.dto;

import com.ibm.ioes.utilities.PagingSorting;

public class HardwareLineItemCancelReportDTO {
	
	PagingSorting pagingSorting = new PagingSorting();
	private String srno;
	private int accountID;
	private String accountName;
	private int orderLineNumber;
	private String lineItemName;
	private String orderNo;
	private String serviceName;
	private String logicalSINo;
	private int m6OrderNo;
	private String remarks;
	private String createdBy;
	private String userId;
	private String srDate;
	private String searchfromDate;
	private String searchToDate;
	private String searchSRNO;
	private String searchLSI;
	private String SearchAccount;
	private String searchAccountName;
	private String searchLineNo;

	
	
	public int getAccountID() {
		return accountID;
	}
	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getLineItemName() {
		return lineItemName;
	}
	public void setLineItemName(String lineItemName) {
		this.lineItemName = lineItemName;
	}
	public String getLogicalSINo() {
		return logicalSINo;
	}
	public void setLogicalSINo(String logicalSINo) {
		this.logicalSINo = logicalSINo;
	}
	public int getM6OrderNo() {
		return m6OrderNo;
	}
	public void setM6OrderNo(int orderNo) {
		m6OrderNo = orderNo;
	}
	public int getOrderLineNumber() {
		return orderLineNumber;
	}
	public void setOrderLineNumber(int orderLineNumber) {
		this.orderLineNumber = orderLineNumber;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}
	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getSrDate() {
		return srDate;
	}
	public void setSrDate(String srDate) {
		this.srDate = srDate;
	}
	public String getSrno() {
		return srno;
	}
	public void setSrno(String srno) {
		this.srno = srno;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSearchAccount() {
		return SearchAccount;
	}
	public void setSearchAccount(String searchAccount) {
		SearchAccount = searchAccount;
	}
	public String getSearchAccountName() {
		return searchAccountName;
	}
	public void setSearchAccountName(String searchAccountName) {
		this.searchAccountName = searchAccountName;
	}
	public String getSearchfromDate() {
		return searchfromDate;
	}
	public void setSearchfromDate(String searchfromDate) {
		this.searchfromDate = searchfromDate;
	}
	public String getSearchLineNo() {
		return searchLineNo;
	}
	public void setSearchLineNo(String searchLineNo) {
		this.searchLineNo = searchLineNo;
	}
	public String getSearchLSI() {
		return searchLSI;
	}
	public void setSearchLSI(String searchLSI) {
		this.searchLSI = searchLSI;
	}
	public String getSearchSRNO() {
		return searchSRNO;
	}
	public void setSearchSRNO(String searchSRNO) {
		this.searchSRNO = searchSRNO;
	}
	public String getSearchToDate() {
		return searchToDate;
	}
	public void setSearchToDate(String searchToDate) {
		this.searchToDate = searchToDate;
	}
	
	

	

	

	



	
	

}
