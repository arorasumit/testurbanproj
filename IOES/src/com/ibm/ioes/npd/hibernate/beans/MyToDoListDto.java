package com.ibm.ioes.npd.hibernate.beans;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;

import org.apache.struts.upload.FormFile;

import com.ibm.ioes.npd.utilities.PagingSorting;
import com.ibm.ioes.npd.utilities.Utility;

public class MyToDoListDto 
{
	
	private int userId;
	private int roleId;
	private String mailTo;
	private int projectId;
	private int mainprojectid;
	
	//MyToDoList Property
	private String  accountId=null;
	private String accountName;
	private String bcpName;
	private String masterInvoiceNumber;
	private Date masterInvoiceDate;
	private String invoiceOutstandingAmt;
	private String balanceOutstandingAmt;
	private String currentStage;
	private String currentTask;
	private int currentStageId;
	private int currentTaskId;
	private String idCollection;
	private int invoiceAgeing;
	private int taskAgeing;
	private Date plannedStartDate;
	private Date plannedEndDate;
	private int requestId;
	private int status;
	private int statusValue;
	private int taskActionTaken;
	private int actionType;
	private int nextStageId;
	private int nextTaskId;
	private int  rejectionAllowed;
	private String taskDuration = null;

	private Date taskstartdate;
    private Date taskenddate;
    private Date actualtaskstartdate;
    
	/*
	 * paging : for paging
	 */
	PagingSorting paging=null;
	
	
	// Search Property	
	private String invoiceOutstandingAmt_op=null;
	private String balanceOutstandingAmt_op=null;
	
	private String searchAccountId;
	private String searchRequestId;
	private String searchAccountName;
	private String searchBcpName;
	private String searchMasterInvoiceNumber;
	private String searchMasterInvoiceDate;
	private String searchInvoiceOutstandingAmt;
	private String searchBalanceOutstandingAmt;
	private String searchCurrentStage;
	private String searchCurrentTask;
	private String searchInvoiceAgeing;
	private String searchTaskAgeing;
	private String searchPlannedStartDate;
	private String searchPlannedEndDate;
	private String searchInvoiceOutstandingAmt_op=null;
	private String searchBalanceOutstandingAmt_op=null;
	
	private String dateFilter=null;
	private String fromDate=null;
	private String toDate=null;
	
	private String sortBy=null;
	private String sortByOrder=null;  /*values : "increment" ,"decrement"  */
	
	//Task Activity
	private String pickUpDate;
	private String ptpDate;
	private String noContactDate;
	private String callBackDate;
	private FormFile attachedDoc;
	private String remarks=null;
	private String noContactChk;

	private TtrnProject projectDetails;
	private String stageName;
	private String taskName;
	private String taskapproveComments;
	private Blob attachment; 
	
	private String taskrejectComments;
	//=================================
	private String seachprojectId;
	private String stageIdList;
	private String taskIdList;
	private int taskId;
	private int stageId;
	private String rfiFrom;
	
	private String seachprojectName;
	private String searchPriorityOfLaunch;
	private String searchProductBrief;
	private String targetmarket;
	private String productcategory;
	private String npscategory;
	private ArrayList<TmEmployee> tmployeelst;
	private String employeeId;
	private String currentTaskStatus;
	private String searchCurrentTaskStatus;
	private String withCurrentOwner;
	private String isTaskMandatory;
	private FormFile taskattachment;
	private String docname;
	private String projectworkflowid;
	private String searchtaskid;
	private String roleid;;
	//=================================
	
	
	public String getNpscategory() {
		return npscategory;
	}
	public void setNpscategory(String npscategory) {
		this.npscategory = npscategory;
	}
	public String getProductcategory() {
		return productcategory;
	}
	public void setProductcategory(String productcategory) {
		this.productcategory = productcategory;
	}
	public String getSeachprojectName() {
		return seachprojectName;
	}
	public void setSeachprojectName(String seachprojectName) {
		this.seachprojectName = seachprojectName;
	}
	public String getSearchPriorityOfLaunch() {
		return searchPriorityOfLaunch;
	}
	public void setSearchPriorityOfLaunch(String searchPriorityOfLaunch) {
		this.searchPriorityOfLaunch = searchPriorityOfLaunch;
	}
	public String getSearchProductBrief() {
		return searchProductBrief;
	}
	public void setSearchProductBrief(String searchProductBrief) {
		this.searchProductBrief = searchProductBrief;
	}
	public String getTargetmarket() {
		return targetmarket;
	}
	public void setTargetmarket(String targetmarket) {
		this.targetmarket = targetmarket;
	}
	public String getSeachprojectId() {
		return seachprojectId;
	}
	public void setSeachprojectId(String seachprojectId) {
		this.seachprojectId = seachprojectId;
	}
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public TtrnProject getProjectDetails() {
		return projectDetails;
	}
	public void setProjectDetails(TtrnProject projectDetails) {
		this.projectDetails = projectDetails;
	}
	public String getNoContactChk() {
		return noContactChk;
	}
	public void setNoContactChk(String noContactChk) {
		this.noContactChk = noContactChk;
	}
	public void setNoContactDate(String noContactDate) {
		this.noContactDate = noContactDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public FormFile getAttachedDoc() {
		return attachedDoc;
	}
	public void setAttachedDoc(FormFile attachedDoc) {
		this.attachedDoc = attachedDoc;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getBalanceOutstandingAmt() {
		return balanceOutstandingAmt;
	}
	public void setBalanceOutstandingAmt(String balanceOutstandingAmt) {
		this.balanceOutstandingAmt = balanceOutstandingAmt;
	}
	public String getBcpName() {
		return bcpName;
	}
	public void setBcpName(String bcpName) {
		this.bcpName = bcpName;
	}
	public String getCurrentStage() {
		return currentStage;
	}
	public void setCurrentStage(String currentStage) {
		this.currentStage = currentStage;
	}
	public String getCurrentTask() {
		return currentTask;
	}
	public void setCurrentTask(String currentTask) {
		this.currentTask = currentTask;
	}
	public String getInvoiceOutstandingAmt() {
		return invoiceOutstandingAmt;
	}
	public void setInvoiceOutstandingAmt(String invoiceOutstandingAmt) {
		this.invoiceOutstandingAmt = invoiceOutstandingAmt;
	}
	public Date getMasterInvoiceDate() {
		return masterInvoiceDate;
	}
	public void setMasterInvoiceDate(Date masterInvoiceDate) {
		this.masterInvoiceDate = masterInvoiceDate;
	}
	public String getMasterInvoiceNumber() {
		return masterInvoiceNumber;
	}
	public void setMasterInvoiceNumber(String masterInvoiceNumber) {
		this.masterInvoiceNumber = masterInvoiceNumber;
	}
	public Date getPlannedEndDate() {
		return plannedEndDate;
	}
	public void setPlannedEndDate(Date plannedEndDate) {
		this.plannedEndDate = plannedEndDate;
	}
	public Date getPlannedStartDate() {
		return plannedStartDate;
	}
	public void setPlannedStartDate(Date plannedStartDate) {
		this.plannedStartDate = plannedStartDate;
	}
	public int getTaskAgeing() {
		return taskAgeing;
	}
	public void setTaskAgeing(int taskAgeing) {
		this.taskAgeing = taskAgeing;
	}
	public String getBalanceOutstandingAmt_op() {
		return balanceOutstandingAmt_op;
	}
	public void setBalanceOutstandingAmt_op(String balanceOutstandingAmt_op) {
		this.balanceOutstandingAmt_op = balanceOutstandingAmt_op;
	}
	public String getDateFilter() {
		return dateFilter;
	}
	public void setDateFilter(String dateFilter) {
		this.dateFilter = dateFilter;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getInvoiceOutstandingAmt_op() {
		return invoiceOutstandingAmt_op;
	}
	public void setInvoiceOutstandingAmt_op(String invoiceOutstandingAmt_op) {
		this.invoiceOutstandingAmt_op = invoiceOutstandingAmt_op;
	}
	public String getSortBy() {
		return sortBy;
	}
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
	public String getSortByOrder() {
		return sortByOrder;
	}
	public void setSortByOrder(String sortByOrder) {
		this.sortByOrder = sortByOrder;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getSearchAccountId() {
		return searchAccountId;
	}
	public void setSearchAccountId(String searchAccountId) {
		this.searchAccountId = searchAccountId;
	}
	public String getSearchAccountName() {
		return searchAccountName;
	}
	public void setSearchAccountName(String searchAccountName) {
		this.searchAccountName = searchAccountName;
	}
	public String getSearchBalanceOutstandingAmt() {
		return searchBalanceOutstandingAmt;
	}
	public void setSearchBalanceOutstandingAmt(String searchBalanceOutstandingAmt) {
		this.searchBalanceOutstandingAmt = searchBalanceOutstandingAmt;
	}
	public String getSearchBalanceOutstandingAmt_op() {
		return searchBalanceOutstandingAmt_op;
	}
	public void setSearchBalanceOutstandingAmt_op(
			String searchBalanceOutstandingAmt_op) {
		this.searchBalanceOutstandingAmt_op = searchBalanceOutstandingAmt_op;
	}
	public String getSearchBcpName() {
		return searchBcpName;
	}
	public void setSearchBcpName(String searchBcpName) {
		this.searchBcpName = searchBcpName;
	}
	public String getSearchCurrentStage() {
		return searchCurrentStage;
	}
	public void setSearchCurrentStage(String searchCurrentStage) {
		this.searchCurrentStage = searchCurrentStage;
	}
	public String getSearchCurrentTask() {
		return searchCurrentTask;
	}
	public void setSearchCurrentTask(String searchCurrentTask) {
		this.searchCurrentTask = searchCurrentTask;
	}
	public String getSearchInvoiceAgeing() {
		return searchInvoiceAgeing;
	}
	public void setSearchInvoiceAgeing(String searchInvoiceAgeing) {
		this.searchInvoiceAgeing = searchInvoiceAgeing;
	}
	public String getSearchInvoiceOutstandingAmt() {
		return searchInvoiceOutstandingAmt;
	}
	public void setSearchInvoiceOutstandingAmt(String searchInvoiceOutstandingAmt) {
		this.searchInvoiceOutstandingAmt = searchInvoiceOutstandingAmt;
	}
	public String getSearchInvoiceOutstandingAmt_op() {
		return searchInvoiceOutstandingAmt_op;
	}
	public void setSearchInvoiceOutstandingAmt_op(
			String searchInvoiceOutstandingAmt_op) {
		this.searchInvoiceOutstandingAmt_op = searchInvoiceOutstandingAmt_op;
	}
	public String getSearchMasterInvoiceDate() {
		return searchMasterInvoiceDate;
	}
	public void setSearchMasterInvoiceDate(String searchMasterInvoiceDate) {
		this.searchMasterInvoiceDate = searchMasterInvoiceDate;
	}
	public String getSearchMasterInvoiceNumber() {
		return searchMasterInvoiceNumber;
	}
	public void setSearchMasterInvoiceNumber(String searchMasterInvoiceNumber) {
		this.searchMasterInvoiceNumber = searchMasterInvoiceNumber;
	}
	public String getSearchPlannedEndDate() {
		return searchPlannedEndDate;
	}
	public void setSearchPlannedEndDate(String searchPlannedEndDate) {
		this.searchPlannedEndDate = searchPlannedEndDate;
	}
	public String getSearchPlannedStartDate() {
		return searchPlannedStartDate;
	}
	public void setSearchPlannedStartDate(String searchPlannedStartDate) {
		this.searchPlannedStartDate = searchPlannedStartDate;
	}
	public String getSearchTaskAgeing() {
		return searchTaskAgeing;
	}
	public void setSearchTaskAgeing(String searchTaskAgeing) {
		this.searchTaskAgeing = searchTaskAgeing;
	}
	public int getRequestId() {
		return requestId;
	}
	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getCallBackDate() {
		return callBackDate;
	}
	public void setCallBackDate(String callBackDate) {
		this.callBackDate = callBackDate;
	}
		
	public String getNoContactDate() {
		return noContactDate;
	}
	
	public String getPickUpDate() {
		return pickUpDate;
	}
	public void setPickUpDate(String pickUpDate) {
		this.pickUpDate = pickUpDate;
	}
	public String getPtpDate() {
		return ptpDate;
	}
	public void setPtpDate(String ptpDate) {
		this.ptpDate = ptpDate;
	}
	public int getCurrentStageId() {
		return currentStageId;
	}
	public void setCurrentStageId(int currentStageId) {
		this.currentStageId = currentStageId;
	}
	public int getCurrentTaskId() {
		return currentTaskId;
	}
	public void setCurrentTaskId(int currentTaskId) {
		this.currentTaskId = currentTaskId;
	}
	public String getIdCollection() {
		return idCollection;
	}
	public void setIdCollection(String idCollection) {
		this.idCollection = idCollection;
	}
	public String getSearchRequestId() {
		return searchRequestId;
	}
	public void setSearchRequestId(String searchRequestId) {
		this.searchRequestId = searchRequestId;
	}
	public int getTaskActionTaken() {
		return taskActionTaken;
	}
	public void setTaskActionTaken(int taskActionTaken) {
		this.taskActionTaken = taskActionTaken;
	}
	public int getActionType() {
		return actionType;
	}
	public void setActionType(int actionType) {
		this.actionType = actionType;
	}
	public PagingSorting getPaging() {
		return paging;
	}
	public void setPaging(PagingSorting paging) {
		this.paging = paging;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getNextStageId() {
		return nextStageId;
	}
	public void setNextStageId(int nextStageId) {
		this.nextStageId = nextStageId;
	}
	public int getNextTaskId() {
		return nextTaskId;
	}
	public void setNextTaskId(int nextTaskId) {
		this.nextTaskId = nextTaskId;
	}
	public String getMailTo() {
		return mailTo;
	}
	public void setMailTo(String mailTo) {
		this.mailTo = mailTo;
	}
	public int getStatusValue() {
		return statusValue;
	}
	public void setStatusValue(int statusValue) {
		this.statusValue = statusValue;
	}
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getStageName() {
		return stageName;
	}
	public void setStageName(String stageName) {
		this.stageName = stageName;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public Blob getAttachment() {
		return attachment;
	}
	public void setAttachment(Blob attachment) {
		this.attachment = attachment;
	}
	public String getTaskapproveComments() {
		return taskapproveComments;
	}
	public void setTaskapproveComments(String taskapproveComments) {
		this.taskapproveComments = taskapproveComments;
	}
	public int getStageId() {
		return stageId;
	}
	public void setStageId(int stageId) {
		this.stageId = stageId;
	}
	public String getStageIdList() {
		return stageIdList;
	}
	public void setStageIdList(String stageIdList) {
		this.stageIdList = stageIdList;
	}
	public String getTaskIdList() {
		return taskIdList;
	}
	public void setTaskIdList(String taskIdList) {
		this.taskIdList = taskIdList;
	}
	public String getRfiFrom() {
		return rfiFrom;
	}
	public void setRfiFrom(String rfiFrom) {
		this.rfiFrom = rfiFrom;
	}
	public ArrayList<TmEmployee> getTmployeelst() {
		return tmployeelst;
	}
	public void setTmployeelst(ArrayList<TmEmployee> tmployeelst) {
		this.tmployeelst = tmployeelst;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getCurrentTaskStatus() {
		return currentTaskStatus;
	}
	public void setCurrentTaskStatus(String currentTaskStatus) {
		this.currentTaskStatus = currentTaskStatus;
	}
	public String getSearchCurrentTaskStatus() {
		return searchCurrentTaskStatus;
	}
	public void setSearchCurrentTaskStatus(String searchCurrentTaskStatus) {
		this.searchCurrentTaskStatus = searchCurrentTaskStatus;
	}
	public String getWithCurrentOwner() {
		return withCurrentOwner;
	}
	public void setWithCurrentOwner(String withCurrentOwner) {
		this.withCurrentOwner = withCurrentOwner;
	}
	public String getIsTaskMandatory() {
		return isTaskMandatory;
	}
	public void setIsTaskMandatory(String isTaskMandatory) {
		this.isTaskMandatory = isTaskMandatory;
	}
	public FormFile getTaskattachment() {
		return taskattachment;
	}
	public void setTaskattachment(FormFile taskattachment) {
		this.taskattachment = taskattachment;
	}
	public String getDocname() {
		return docname;
	}
	public void setDocname(String docname) {
		this.docname = docname;
	}
	public String getProjectworkflowid() {
		return projectworkflowid;
	}
	public void setProjectworkflowid(String projectworkflowid) {
		this.projectworkflowid = projectworkflowid;
	}
	public String getSearchtaskid() {
		return searchtaskid;
	}
	public void setSearchtaskid(String searchtaskid) {
		this.searchtaskid = searchtaskid;
	}
	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	public int getRejectionAllowed() {
		return rejectionAllowed;
	}
	public void setRejectionAllowed(int rejectionAllowed) {
		this.rejectionAllowed = rejectionAllowed;
	}
	public String getTaskrejectComments() {
		return taskrejectComments;
	}
	public void setTaskrejectComments(String taskrejectComments) {
		this.taskrejectComments = taskrejectComments;
	}
	public int getMainprojectid() {
		return mainprojectid;
	}
	public void setMainprojectid(int mainprojectid) {
		this.mainprojectid = mainprojectid;
	}
	public String getTaskDuration() {
		return taskDuration;
	}
	public void setTaskDuration(String taskDuration) {
		this.taskDuration = taskDuration;
	}
	public Date getActualtaskstartdate() {
		return actualtaskstartdate;
	}
	public String getActualtaskstartdateString() {
		return Utility.showDate_Report(actualtaskstartdate);
	}
	public void setActualtaskstartdate(Date actualtaskstartdate) {
		this.actualtaskstartdate = actualtaskstartdate;
	}
	public Date getTaskenddate() {
		return taskenddate;
	}
	public String getTaskenddateString() {
		return Utility.showDate_Report(taskenddate);
	}
	public void setTaskenddate(Date taskenddate) {
		this.taskenddate = taskenddate;
	}
	public Date getTaskstartdate() {
		return taskstartdate;
	}
	public String getTaskstartdateString() {
		return Utility.showDate_Report(taskstartdate);
	}
	public void setTaskstartdate(Date taskstartdate) {
		this.taskstartdate = taskstartdate;
	}	
}
