package com.ibm.ioes.newdesign.dto;

import com.ibm.ioes.utilities.PagingSorting;

public class CopyCancelReportDTO {
	PagingSorting pagingSorting = new PagingSorting();
	private int oldOrderNo;
	private int newOrderNo;
	private int rootOrderNo;
	private int oldServiceNo;
	private int newServiceNo;
	private String createdBy;
	private String createdDate;
	 private String toDate;
	 private String fromDate;
	 private int fromOrderNo;
	 private int toOrderNo;
	public String getFromDate() {
		return fromDate;
	}
	public int getFromOrderNo() {
		return fromOrderNo;
	}
	public String getToDate() {
		return toDate;
	}
	public int getToOrderNo() {
		return toOrderNo;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public int getNewOrderNo() {
		return newOrderNo;
	}
	public void setNewOrderNo(int newOrderNo) {
		this.newOrderNo = newOrderNo;
	}
	public int getNewServiceNo() {
		return newServiceNo;
	}
	public void setNewServiceNo(int newServiceNo) {
		this.newServiceNo = newServiceNo;
	}
	public int getOldOrderNo() {
		return oldOrderNo;
	}
	public void setOldOrderNo(int oldOrderNo) {
		this.oldOrderNo = oldOrderNo;
	}
	public int getOldServiceNo() {
		return oldServiceNo;
	}
	public void setOldServiceNo(int oldServiceNo) {
		this.oldServiceNo = oldServiceNo;
	}
	public int getRootOrderNo() {
		return rootOrderNo;
	}
	public void setRootOrderNo(int rootOrderNo) {
		this.rootOrderNo = rootOrderNo;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
		
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
		
	}
	public void setFromOrderNo(int fromOrderNo) {
		this.fromOrderNo = fromOrderNo;
		
	}
	public void setToOrderNo(int toOrderNo) {
		this.toOrderNo = toOrderNo;
		
	}
	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}
	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
	}

	

}
