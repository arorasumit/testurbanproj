package com.ibm.ioes.newdesign.dto;

import com.ibm.ioes.utilities.PagingSorting;
//[01]  opportunityId added  by Lawkush in reports "ACTIVE LINE ITEMS REPORT   ","PERFORMANCE SUMMARY REPORT  ","PERFORMANCE DETAIL REPORT","ORDER DETAIL REPORT","ORDER REPORT NEW DETAIL CWN","LEPM ORDER DETAIL REPORT"
//[02]   shourya   12-Dec-2013  Old LSI   added by Shourya 
//[03] 	Priya Gupta 28-Jan-2016	Added 2 new columns 
//[04]  Gunjan Singla  6-May-2016  20160418-R1-022266    Added columns
public class PerformanceDetailReportDTO {
	PagingSorting pagingSorting = new PagingSorting();
	private int partyNo;
    private String toDate;
    private String crmACcountNO;
	private String fromDate;
	private int fromAccountNo;
	private int toAccountNo;
	private int fromOrderNo;
	private int toOrderNo;
	private int crmAccountNo;
	private String partyName;
	private String cus_segment;
	private String industrySegment;
	private String regionName;
	private String zoneName;
	private String accountManager;
	private String projectManager;
	private String orderType;
	private String order_type;
	private String ordersubtype;
	private String orderStatus;
	private String orderDate;
	private String publishedDate;
	private String orderApproveDate;
	private String taskName;
	private String actualStartDate;
	private String actualEndDate;
	private int taskNumber;
	private String owner;
	private String userName;
	private String accountMgrPhoneNo;
	private String emailId;
	private int orderNumber;
	private String totalDays;
	private double orderTotal;
	private String outCome;
	private String remarks;
	private String vertical;
	private String osp;
	private int poNumber;
	private String copcStartDate;
	private String copcEndDate;
	//[02] Start 
	private String oldLsi;
//	[02] End
	//[03] Start
	private String salesForceOpportunityNumber;
	private String channelPartner;
	private String partnerId;
	//[03] End
	//[04] start
	private String fieldEngineer;
	private String partnerCode;
	//[04] end
	
	public String getCopcStartDate() {
		return copcStartDate;
	}
	public void setCopcStartDate(String copcStartDate) {
		this.copcStartDate = copcStartDate;
	}
	public String getCopcEndDate() {
		return copcEndDate;
	}
	public void setCopcEndDate(String copcEndDate) {
		this.copcEndDate = copcEndDate;
	}
//	[01] Start  	 
	private String opportunityId;	
	private String groupName; 	 
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}	
	 public String getOpportunityId() {
		return opportunityId;
	}
	public void setOpportunityId(String opportunityId) {
		this.opportunityId = opportunityId;
	}
//	[01] end  
	public String getAccountManager() {
		return accountManager;
	}
	public void setAccountManager(String accountManager) {
		this.accountManager = accountManager;
	}
	public String getAccountMgrPhoneNo() {
		return accountMgrPhoneNo;
	}
	public void setAccountMgrPhoneNo(String accountMgrPhoneNo) {
		this.accountMgrPhoneNo = accountMgrPhoneNo;
	}
	public String getActualEndDate() {
		return actualEndDate;
	}
	public void setActualEndDate(String actualEndDate) {
		this.actualEndDate = actualEndDate;
	}
	public String getActualStartDate() {
		return actualStartDate;
	}
	public void setActualStartDate(String actualStartDate) {
		this.actualStartDate = actualStartDate;
	}
	public int getCrmAccountNo() {
		return crmAccountNo;
	}
	public void setCrmAccountNo(int crmAccountNo) {
		this.crmAccountNo = crmAccountNo;
	}
	public String getCus_segment() {
		return cus_segment;
	}
	public void setCus_segment(String cus_segment) {
		this.cus_segment = cus_segment;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getIndustrySegment() {
		return industrySegment;
	}
	public void setIndustrySegment(String industrySegment) {
		this.industrySegment = industrySegment;
	}
	public String getOrder_type() {
		return order_type;
	}
	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}
	public String getOrderApproveDate() {
		return orderApproveDate;
	}
	public void setOrderApproveDate(String orderApproveDate) {
		this.orderApproveDate = orderApproveDate;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public int getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getOrdersubtype() {
		return ordersubtype;
	}
	public void setOrdersubtype(String ordersubtype) {
		this.ordersubtype = ordersubtype;
	}
	public double getOrderTotal() {
		return orderTotal;
	}
	public void setOrderTotal(double orderTotal) {
		this.orderTotal = orderTotal;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getOsp() {
		return osp;
	}
	public void setOsp(String osp) {
		this.osp = osp;
	}
	public String getOutCome() {
		return outCome;
	}
	public void setOutCome(String outCome) {
		this.outCome = outCome;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}
	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
	}
	public String getPartyName() {
		return partyName;
	}
	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}
	public int getPartyNo() {
		return partyNo;
	}
	public void setPartyNo(int partyNo) {
		this.partyNo = partyNo;
	}
	public int getPoNumber() {
		return poNumber;
	}
	public void setPoNumber(int poNumber) {
		this.poNumber = poNumber;
	}
	public String getProjectManager() {
		return projectManager;
	}
	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}
	public String getPublishedDate() {
		return publishedDate;
	}
	public void setPublishedDate(String publishedDate) {
		this.publishedDate = publishedDate;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	public String getTotalDays() {
		return totalDays;
	}
	public void setTotalDays(String totalDays) {
		this.totalDays = totalDays;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getVertical() {
		return vertical;
	}
	public void setVertical(String vertical) {
		this.vertical = vertical;
	}
	public String getZoneName() {
		return zoneName;
	}
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	public int getFromAccountNo() {
		return fromAccountNo;
	}
	public void setFromAccountNo(int fromAccountNo) {
		this.fromAccountNo = fromAccountNo;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public int getFromOrderNo() {
		return fromOrderNo;
	}
	public void setFromOrderNo(int fromOrderNo) {
		this.fromOrderNo = fromOrderNo;
	}
	public int getToAccountNo() {
		return toAccountNo;
	}
	public void setToAccountNo(int toAccountNo) {
		this.toAccountNo = toAccountNo;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public int getToOrderNo() {
		return toOrderNo;
	}
	public void setToOrderNo(int toOrderNo) {
		this.toOrderNo = toOrderNo;
	}
	public String getCrmACcountNO() {
		return crmACcountNO;
	}
	public void setCrmACcountNO(String crmACcountNO) {
		this.crmACcountNO = crmACcountNO;
	}
	public String getOldLsi() {
		return oldLsi;
	}
	public void setOldLsi(String oldLsi) {
		this.oldLsi = oldLsi;
	}
	//[03] Start
	public String getSalesForceOpportunityNumber() {
		return salesForceOpportunityNumber;
	}
	public void setSalesForceOpportunityNumber(String salesForceOpportunityNumber) {
		this.salesForceOpportunityNumber = salesForceOpportunityNumber;
	}
	public String getChannelPartner() {
		return channelPartner;
	}
	public void setChannelPartner(String channelPartner) {
		this.channelPartner = channelPartner;
	}
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	
	//[03] End
	//[04] start
	public String getFieldEngineer() {
		return fieldEngineer;
	}
	public void setFieldEngineer(String fieldEngineer) {
		this.fieldEngineer = fieldEngineer;
	}
	public String getPartnerCode() {
		return partnerCode;
	}
	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}
	//[04] end	

}
