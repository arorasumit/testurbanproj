//[001]   Neha Maheshwari 10-Jun-2015 20141219-R2-020936 Billing Efficiency Program Reports
package com.ibm.ioes.newdesign.dto;

import com.ibm.ioes.utilities.PagingSorting;

public class PerformanceReportDTO {
	
	
	PagingSorting pagingSorting = new PagingSorting();
	private String productManager;
	private  int  projectID;
	private String  projectName;
	private String plndLaunchDate;
	private String actLaunchDate;
	private  int  deviation;
	
	private String toDate;
	private String fromDate;
	private int toOrderNo=0;
	private int fromOrderNo=0; 
	private int toAccountNo=0;
	private int fromAccountNo=0;
	private String osp;
	private int orderNumber;
	private String orderDate;
	private String productName;
	private String regionName;
	private String zoneName;
	private String crmAccountNoString;
	private long accountId;
	private String partyName;
	private String verticalDetails;
	private String accountManager;
	private String projectManager;
	private String orderType;
	private String order_type;
	private String custPoDate;
	private String poRecieveDate;
	private long poAmountSum;
	private String po_recpt_delay;
	private String po_logging_delay;
	private String order_completion_date;
	private String appr_delay_in_region;
	private String days_in_copc;
	private String days_for_order;
	private String amApproveDate;
	private String pmApproveDate;
	private String copcApproveDate;
	private String cus_segment;
	private String orderStatus;
	
	//nagarjuna
	private Double po_recpt_delay_double;  
	private Double po_logging_delay_double;
    private Double order_completion_date_double;
    private Double appr_delay_in_region_double;
    private Double days_in_copc_double;
	private Double days_for_order_double;
	private int partyNo;
    private String copcStartDate;
	private String copcEndDate;
	//[001] Start
	private String standardReason;
	//[001] End
    
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
	public int getPartyNo() {
		return partyNo;
	}
	public void setPartyNo(int partyNo) {
		this.partyNo = partyNo;
	}
	private  int daysInProject;
	
	private String groupName; 
	 
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}	
	
	public String getActLaunchDate() {
		return actLaunchDate;
	}
	public void setActLaunchDate(String actLaunchDate) {
		this.actLaunchDate = actLaunchDate;
	}
	public int getDaysInProject() {
		return daysInProject;
	}
	public void setDaysInProject(int daysInProject) {
		this.daysInProject = daysInProject;
	}
	public int getDeviation() {
		return deviation;
	}
	public void setDeviation(int deviation) {
		this.deviation = deviation;
	}
	public String getPlndLaunchDate() {
		return plndLaunchDate;
	}
	public void setPlndLaunchDate(String plndLaunchDate) {
		this.plndLaunchDate = plndLaunchDate;
	}
	public String getProductManager() {
		return productManager;
	}
	public void setProductManager(String productManager) {
		this.productManager = productManager;
	}
	public int getProjectID() {
		return projectID;
	}
	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
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
	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}
	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
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
	public long getAccountId() {
		return accountId;
	}
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}
	public String getAccountManager() {
		return accountManager;
	}
	public void setAccountManager(String accountManager) {
		this.accountManager = accountManager;
	}
	public String getAmApproveDate() {
		return amApproveDate;
	}
	public void setAmApproveDate(String amApproveDate) {
		this.amApproveDate = amApproveDate;
	}
	public String getAppr_delay_in_region() {
		return appr_delay_in_region;
	}
	public void setAppr_delay_in_region(String appr_delay_in_region) {
		this.appr_delay_in_region = appr_delay_in_region;
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
	public String getCustPoDate() {
		return custPoDate;
	}
	public void setCustPoDate(String custPoDate) {
		this.custPoDate = custPoDate;
	}
	public String getDays_for_order() {
		return days_for_order;
	}
	public void setDays_for_order(String days_for_order) {
		this.days_for_order = days_for_order;
	}
	public String getDays_in_copc() {
		return days_in_copc;
	}
	public void setDays_in_copc(String days_in_copc) {
		this.days_in_copc = days_in_copc;
	}
	public String getOrder_completion_date() {
		return order_completion_date;
	}
	public void setOrder_completion_date(String order_completion_date) {
		this.order_completion_date = order_completion_date;
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
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getPartyName() {
		return partyName;
	}
	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}
	public String getPmApproveDate() {
		return pmApproveDate;
	}
	public void setPmApproveDate(String pmApproveDate) {
		this.pmApproveDate = pmApproveDate;
	}
	public String getPo_logging_delay() {
		return po_logging_delay;
	}
	public void setPo_logging_delay(String po_logging_delay) {
		this.po_logging_delay = po_logging_delay;
	}
	public String getPo_recpt_delay() {
		return po_recpt_delay;
	}
	public void setPo_recpt_delay(String po_recpt_delay) {
		this.po_recpt_delay = po_recpt_delay;
	}
	public long getPoAmountSum() {
		return poAmountSum;
	}
	public void setPoAmountSum(long poAmountSum) {
		this.poAmountSum = poAmountSum;
	}
	public String getPoRecieveDate() {
		return poRecieveDate;
	}
	public void setPoRecieveDate(String poRecieveDate) {
		this.poRecieveDate = poRecieveDate;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProjectManager() {
		return projectManager;
	}
	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getVerticalDetails() {
		return verticalDetails;
	}
	public void setVerticalDetails(String verticalDetails) {
		this.verticalDetails = verticalDetails;
	}
	public String getZoneName() {
		return zoneName;
	}
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	public Double getPo_recpt_delay_double() {
		return po_recpt_delay_double;
	}
	public void setPo_recpt_delay_double(Double po_recpt_delay_double) {
		this.po_recpt_delay_double = po_recpt_delay_double;
	}
	public Double getPo_logging_delay_double() {
		return po_logging_delay_double;
	}
	public void setPo_logging_delay_double(Double po_logging_delay_double) {
		this.po_logging_delay_double = po_logging_delay_double;
	}
	public Double getOrder_completion_date_double() {
		return order_completion_date_double;
	}
	public void setOrder_completion_date_double(Double order_completion_date_double) {
		this.order_completion_date_double = order_completion_date_double;
	}
	public Double getAppr_delay_in_region_double() {
		return appr_delay_in_region_double;
	}
	public void setAppr_delay_in_region_double(Double appr_delay_in_region_double) {
		this.appr_delay_in_region_double = appr_delay_in_region_double;
	}
	public Double getDays_in_copc_double() {
		return days_in_copc_double;
	}
	public void setDays_in_copc_double(Double days_in_copc_double) {
		this.days_in_copc_double = days_in_copc_double;
	}
	public Double getDays_for_order_double() {
		return days_for_order_double;
	}
	public void setDays_for_order_double(Double days_for_order_double) {
		this.days_for_order_double = days_for_order_double;
	}
	//[001] Start
	public String getStandardReason() {
		return standardReason;
	}
	public void setStandardReason(String standardReason) {
		this.standardReason = standardReason;
	}
	//[001] End
	

}
