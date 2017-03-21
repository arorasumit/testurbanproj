package com.ibm.ioes.newdesign.dto;

import com.ibm.ioes.utilities.PagingSorting;
//[01]  opportunityId added  by Lawkush in reports "ACTIVE LINE ITEMS REPORT   ","PERFORMANCE SUMMARY REPORT  ","PERFORMANCE DETAIL REPORT","ORDER DETAIL REPORT","ORDER REPORT NEW DETAIL CWN","LEPM ORDER DETAIL REPORT"
//[02] Vipin Saharia 15 Nov 2013 added cancelDate, cancelMonth in report PERFORMANCE SUMMARY REPORT
public class PerformanceSummaryReportDTO {
	
	PagingSorting pagingSorting = new PagingSorting();
	//[02] Start
	private String cancelDate;
	private String cancelMonth;
	//[02] End
	private int party_no;
	private String crmAccountNoString;
	private String partyName;
	private String cus_segment;
	private String industrySegment;
	private String regionName;
	private String zoneName;								
	private String accountManager;
	private String projectManager;
	private String order_type;
	private String orderType;
	private String subChangeTypeName;
	private String orderStage;
	private String orderDate;
	private String copcApproveDate;
	private String publishedDate;
	private int orderNumber;
	private String dayInAM;
	private String dayInPM;
	private String dayInCOPC;
	private String dayInSED;
	private String totalDays;
	private long poAmountSum;
	private String toDate;
	private String fromDate;
	private int toOrderNo;
	private int fromOrderNo;
	private int toAccountNo;
	private int fromAccountNo;
	private String osp;
//	[01] Start  	 
	private String copcStartDate;
	private String copcEndDate;
	private String amDelayReason;
	private String copcDelayReason;
	
    public String getAmDelayReason() {
		return amDelayReason;
	}
	public void setAmDelayReason(String amDelayReason) {
		this.amDelayReason = amDelayReason;
	}
	public String getCopcDelayReason() {
		return copcDelayReason;
	}
	public void setCopcDelayReason(String copcDelayReason) {
		this.copcDelayReason = copcDelayReason;
	}
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
	public String getCopcApproveDate() {
		return copcApproveDate;
	}
	public void setCopcApproveDate(String copcApproveDate) {
		this.copcApproveDate = copcApproveDate;
	}
	public String getCrmAccountNoString() {
		return crmAccountNoString;
	}
	public void setCrmAccountNoString(String crmAccountNoString) {
		this.crmAccountNoString = crmAccountNoString;
	}
	public String getCus_segment() {
		return cus_segment;
	}
	public void setCus_segment(String cus_segment) {
		this.cus_segment = cus_segment;
	}
	public String getDayInAM() {
		return dayInAM;
	}
	public void setDayInAM(String dayInAM) {
		this.dayInAM = dayInAM;
	}
	public String getDayInCOPC() {
		return dayInCOPC;
	}
	public void setDayInCOPC(String dayInCOPC) {
		this.dayInCOPC = dayInCOPC;
	}
	public String getDayInPM() {
		return dayInPM;
	}
	public void setDayInPM(String dayInPM) {
		this.dayInPM = dayInPM;
	}
	public String getDayInSED() {
		return dayInSED;
	}
	public void setDayInSED(String dayInSED) {
		this.dayInSED = dayInSED;
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
	public String getOrderStage() {
		return orderStage;
	}
	public void setOrderStage(String orderStage) {
		this.orderStage = orderStage;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}
	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
	}
	public int getParty_no() {
		return party_no;
	}
	public void setParty_no(int party_no) {
		this.party_no = party_no;
	}
	public String getPartyName() {
		return partyName;
	}
	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}
	public long getPoAmountSum() {
		return poAmountSum;
	}
	public void setPoAmountSum(long poAmountSum) {
		this.poAmountSum = poAmountSum;
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
	public String getSubChangeTypeName() {
		return subChangeTypeName;
	}
	public void setSubChangeTypeName(String subChangeTypeName) {
		this.subChangeTypeName = subChangeTypeName;
	}
	public String getTotalDays() {
		return totalDays;
	}
	public void setTotalDays(String totalDays) {
		this.totalDays = totalDays;
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
	public String getOsp() {
		return osp;
	}
	public void setOsp(String osp) {
		this.osp = osp;
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
	//[02] Start
	public String getCancelDate() {
		return cancelDate;
	}

	public String getCancelMonth() {
		return cancelMonth;
	}

	public void setCancelDate(String cancelDate) {
		this.cancelDate = cancelDate;
	}

	public void setCancelMonth(String cancelMonth) {
		this.cancelMonth = cancelMonth;
	}
	//[02] End
	
}
