package com.ibm.ioes.newdesign.dto;

import com.ibm.ioes.utilities.PagingSorting;

public class LempOwnerReportDTO {
	
	PagingSorting pagingSorting = new PagingSorting();
	private int taskNumber;
	private String pmName;
	private String userName;
	private String contactCell;
	private String emailId;
	private String taskName;
	private String pm_pro_date;
	private String pmApproveDate;
	//private String pm_pro_date;
	private String pmapprovaldate;
	private String copcApproveDate;
	private String orderNo;
	private String copcApproveFromDate;
	private String  copcApproveToDate;
	private String fromDate;
	private String toDate;
	public String getContactCell() {
		return contactCell;
	}
	public void setContactCell(String contactCell) {
		this.contactCell = contactCell;
	}
	public String getCopcApproveDate() {
		return copcApproveDate;
	}
	public void setCopcApproveDate(String copcApproveDate) {
		this.copcApproveDate = copcApproveDate;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
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
	public String getPm_pro_date() {
		return pm_pro_date;
	}
	public void setPm_pro_date(String pm_pro_date) {
		this.pm_pro_date = pm_pro_date;
	}
	public String getPmapprovaldate() {
		return pmapprovaldate;
	}
	public void setPmapprovaldate(String pmapprovaldate) {
		this.pmapprovaldate = pmapprovaldate;
	}
	public String getPmApproveDate() {
		return pmApproveDate;
	}
	public void setPmApproveDate(String pmApproveDate) {
		this.pmApproveDate = pmApproveDate;
	}
	public String getPmName() {
		return pmName;
	}
	public void setPmName(String pmName) {
		this.pmName = pmName;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public int getTaskNumber() {
		return taskNumber;
	}
	public void setTaskNumber(int taskNumber) {
		this.taskNumber = taskNumber;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getCopcApproveToDate() {
		return copcApproveToDate;
	}
	public void setCopcApproveToDate(String copcApproveToDate) {
		this.copcApproveToDate = copcApproveToDate;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getCopcApproveFromDate() {
		return copcApproveFromDate;
	}
	public void setCopcApproveFromDate(String copcApproveFromDate) {
		this.copcApproveFromDate = copcApproveFromDate;
	}
	
	
	
	
					
}
