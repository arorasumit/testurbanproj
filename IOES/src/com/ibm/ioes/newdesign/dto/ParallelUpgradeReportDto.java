package com.ibm.ioes.newdesign.dto;

import com.ibm.ioes.utilities.PagingSorting;

public class ParallelUpgradeReportDto {

	
	PagingSorting pagingSorting = new PagingSorting();
	private String customerSegment;
	private String orderType;
	private String changeType;
	private Integer changeTypeId;
	private String customername;
	private int crm_order_id;
	private String fromDate;
	private String toDate;
	private int logical_si_no ;
	private String service_stage;
	private String attribute_remarks;
	private String exclude_comp_orders;
	private int fromServiceNo;
	private int toServiceNo;
	private String orderDate;
	private int serviceNo;
	private String bin;
	private int old_lsi;
	private String old_lsi_lateststage;
	private String copcApprovalDate;
	private String changeReason;
	private String lsi_disconnection_SRNO;
	private String serviceBTActionDate;
	private String old_lsi_BT_ActionDate;
	private String old_lsi_disconnection_SRno;
	
	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}
	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
	}
	public String getCustomerSegment() {
		return customerSegment;
	}
	public void setCustomerSegment(String customerSegment) {
		this.customerSegment = customerSegment;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getChangeType() {
		return changeType;
	}
	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}
	public String getCustomername() {
		return customername;
	}
	public void setCustomername(String customername) {
		this.customername = customername;
	}
	public int getCrm_order_id() {
		return crm_order_id;
	}
	public void setCrm_order_id(int crm_order_id) {
		this.crm_order_id = crm_order_id;
	}
	public int getLogical_si_no() {
		return logical_si_no;
	}
	public void setLogical_si_no(int logical_si_no) {
		this.logical_si_no = logical_si_no;
	}
	public String getService_stage() {
		return service_stage;
	}
	public void setService_stage(String service_stage) {
		this.service_stage = service_stage;
	}
	public String getAttribute_remarks() {
		return attribute_remarks;
	}
	public void setAttribute_remarks(String attribute_remarks) {
		this.attribute_remarks = attribute_remarks;
	}
	public String getExclude_comp_orders() {
		return exclude_comp_orders;
	}
	public void setExclude_comp_orders(String exclude_comp_orders) {
		this.exclude_comp_orders = exclude_comp_orders;
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
	public int getFromServiceNo() {
		return fromServiceNo;
	}
	public void setFromServiceNo(int fromServiceNo) {
		this.fromServiceNo = fromServiceNo;
	}
	public int getToServiceNo() {
		return toServiceNo;
	}
	public void setToServiceNo(int toServiceNo) {
		this.toServiceNo = toServiceNo;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public int getServiceNo() {
		return serviceNo;
	}
	public void setServiceNo(int serviceNo) {
		this.serviceNo = serviceNo;
	}
	public String getBin() {
		return bin;
	}
	public void setBin(String bin) {
		this.bin = bin;
	}
	public int getOld_lsi() {
		return old_lsi;
	}
	public void setOld_lsi(int old_lsi) {
		this.old_lsi = old_lsi;
	}
	public String getOld_lsi_lateststage() {
		return old_lsi_lateststage;
	}
	public void setOld_lsi_lateststage(String old_lsi_lateststage) {
		this.old_lsi_lateststage = old_lsi_lateststage;
	}
	public Integer getChangeTypeId() {
		return changeTypeId;
	}
	public void setChangeTypeId(Integer changeTypeId) {
		this.changeTypeId = changeTypeId;
	}
	public String getCopcApprovalDate() {
		return copcApprovalDate;
	}
	public void setCopcApprovalDate(String copcApprovalDate) {
		this.copcApprovalDate = copcApprovalDate;
	}
	public String getChangeReason() {
		return changeReason;
	}
	public void setChangeReason(String changeReason) {
		this.changeReason = changeReason;
	}
	public String getLsi_disconnection_SRNO() {
		return lsi_disconnection_SRNO;
	}
	public void setLsi_disconnection_SRNO(String lsi_disconnection_SRNO) {
		this.lsi_disconnection_SRNO = lsi_disconnection_SRNO;
	}
	public String getServiceBTActionDate() {
		return serviceBTActionDate;
	}
	public void setServiceBTActionDate(String serviceBTActionDate) {
		this.serviceBTActionDate = serviceBTActionDate;
	}
	public String getOld_lsi_BT_ActionDate() {
		return old_lsi_BT_ActionDate;
	}
	public void setOld_lsi_BT_ActionDate(String old_lsi_BT_ActionDate) {
		this.old_lsi_BT_ActionDate = old_lsi_BT_ActionDate;
	}
	public String getOld_lsi_disconnection_SRno() {
		return old_lsi_disconnection_SRno;
	}
	public void setOld_lsi_disconnection_SRno(String old_lsi_disconnection_SRno) {
		this.old_lsi_disconnection_SRno = old_lsi_disconnection_SRno;
	}
}
