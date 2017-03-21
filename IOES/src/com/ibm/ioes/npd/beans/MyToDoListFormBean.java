package com.ibm.ioes.npd.beans;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

import com.ibm.ioes.npd.hibernate.beans.TmEmployee;
import com.ibm.ioes.npd.hibernate.beans.TmRoles;

public class MyToDoListFormBean  extends ActionForm
{
	private String accountId;
	private String projectId;
	private String hiddenProjectId;
	private String accountName;
	private String bcpName;
	private String masterInvoiceNumber;
	private String masterInvoiceDate;
	private String invoiceOutstandingAmt;
	private String balanceOutstandingAmt;
	private String currentStage;
	private String currentTask;
	private String currentStageId;
	private String currentTaskId;
	private String idCollection;
	private String invoiceAgeing;
	private String taskAgeing;
	private String plannedStartDate;
	private String plannedEndDate;
	private String requestId;
	private String requestid;
	private String status="1";
	private String changeStatus="0";
	private String changeStatusValue="1";
	private String param;
	private String strNextTaskIds=null;
	private String strProjectIds=null;
	private String currentTaskStatus;
	private String searchCurrentTaskStatus;
	
	private String hiddenReturnFlag=null;
	
	
	private String mainprojectid=null;
	
	
	String buttonClicked=null;
	
	private String popUpSearch="false";
		
	private String invoiceOutstandingAmt_op=null;
	private String balanceOutstandingAmt_op=null;
	
	private String dateFilter=null;
	private String fromDate=null;
	private String toDate=null;
	
	private String sortBy=null;
	private String sortByOrder=null;  /*values : "increment" ,"decrement"  */	
	
	// Task Activity
	private String pickUpDate;
	private String ptpDate;
	private String noContactDate;
	private String callBackDate;
	private String noContactChk;
	private String ptpChk;
	private String callBackChk;
	private String pickUpChk;
	private FormFile noteAttachement;
	private String noteFile;
	private String closeRemarks=null;
	private String noteRemarks=null;
	private FormFile clouserAttachement;
	private String taskActivityRB;
	private FormFile attachment;
	private String taskapproveComments;
	private String taskrejectComments;
	
	//=====================================================
	private String seachprojectId;
	private String seachprojectName;
	private String searchPriorityOfLaunch;
	private String searchProductBrief;
	private String targetmarket;
	private String productcategory;
	private String npscategory;
	private String searchCurrentStage;
	private String searchCurrentTask;
	private String taskId;
	private String stageId;
	private String searchWorkflowid;
	private String projectworkflowid;
	private String searchprojectid;
	private ArrayList<TmEmployee> lstEmployee;
	private String employeeId;
	private String isTaskMandatory;
	private String docname;
	private String searchtaskid;
	private String searchprojectworkflowid;
	private String fileName;
	private String rolemapped;
	private ArrayList<TmRoles> roleList;
	private String roleid;
	//=====================================================
	
	
	//for paging
	String pageNumber="1";
	String maxPageNumber=null;
	String pageRecords=null;
	
	public String getMaxPageNumber() {
		return maxPageNumber;
	}
	public void setMaxPageNumber(String maxPageNumber) {
		this.maxPageNumber = maxPageNumber;
	}
	public String getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}
	public String getTaskActivityRB() {
		return taskActivityRB;
	}
	public void setTaskActivityRB(String taskActivityRB) {
		this.taskActivityRB = taskActivityRB;
	}
	
	public FormFile getClouserAttachement() {
		return clouserAttachement;
	}
	public void setClouserAttachement(FormFile clouserAttachement) {
		this.clouserAttachement = clouserAttachement;
	}
	public FormFile getNoteAttachement() {
		return noteAttachement;
	}
	public void setNoteAttachement(FormFile noteAttachement) {
		this.noteAttachement = noteAttachement;
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
	public String getBalanceOutstandingAmt_op() {
		return balanceOutstandingAmt_op;
	}
	public void setBalanceOutstandingAmt_op(String balanceOutstandingAmt_op) {
		this.balanceOutstandingAmt_op = balanceOutstandingAmt_op;
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
	
	public String getInvoiceAgeing() {
		return invoiceAgeing;
	}
	public void setInvoiceAgeing(String invoiceAgeing) {
		this.invoiceAgeing = invoiceAgeing;
	}
	public String getInvoiceOutstandingAmt() {
		return invoiceOutstandingAmt;
	}
	public void setInvoiceOutstandingAmt(String invoiceOutstandingAmt) {
		this.invoiceOutstandingAmt = invoiceOutstandingAmt;
	}
	public String getInvoiceOutstandingAmt_op() {
		return invoiceOutstandingAmt_op;
	}
	public void setInvoiceOutstandingAmt_op(String invoiceOutstandingAmt_op) {
		this.invoiceOutstandingAmt_op = invoiceOutstandingAmt_op;
	}
	public String getMasterInvoiceDate() {
		return masterInvoiceDate;
	}
	public void setMasterInvoiceDate(String masterInvoiceDate) {
		this.masterInvoiceDate = masterInvoiceDate;
	}
	public String getMasterInvoiceNumber() {
		return masterInvoiceNumber;
	}
	public void setMasterInvoiceNumber(String masterInvoiceNumber) {
		this.masterInvoiceNumber = masterInvoiceNumber;
	}
	public String getPlannedEndDate() {
		return plannedEndDate;
	}
	public void setPlannedEndDate(String plannedEndDate) {
		this.plannedEndDate = plannedEndDate;
	}
	public String getPlannedStartDate() {
		return plannedStartDate;
	}
	public void setPlannedStartDate(String plannedStartDate) {
		this.plannedStartDate = plannedStartDate;
	}
	public String getTaskAgeing() {
		return taskAgeing;
	}
	public void setTaskAgeing(String taskAgeing) {
		this.taskAgeing = taskAgeing;
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

	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
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
	public String getCallBackDate() {
		return callBackDate;
	}
	public void setCallBackDate(String callBackDate) {
		this.callBackDate = callBackDate;
	}
	public String getNoContactDate() {
		return noContactDate;
	}
	public void setNoContactDate(String noContactDate) {
		this.noContactDate = noContactDate;
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
	public String getNoContactChk() {
		return noContactChk;
	}
	public void setNoContactChk(String noContactChk) {
		this.noContactChk = noContactChk;
	}
	public String getCallBackChk() {
		return callBackChk;
	}
	public void setCallBackChk(String callBackChk) {
		this.callBackChk = callBackChk;
	}
	public String getPickUpChk() {
		return pickUpChk;
	}
	public void setPickUpChk(String pickUpChk) {
		this.pickUpChk = pickUpChk;
	}
	public String getPtpChk() {
		return ptpChk;
	}
	public void setPtpChk(String ptpChk) {
		this.ptpChk = ptpChk;
	}
	public String getNoteFile() {
		return noteFile;
	}
	public void setNoteFile(String noteFile) {
		this.noteFile = noteFile;
	}
	
	public String getCloseRemarks() {
		return closeRemarks;
	}
	public void setCloseRemarks(String closeRemarks) {
		this.closeRemarks = closeRemarks;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCurrentStageId() {
		return currentStageId;
	}
	public void setCurrentStageId(String currentStageId) {
		this.currentStageId = currentStageId;
	}
	public String getCurrentTaskId() {
		return currentTaskId;
	}
	public void setCurrentTaskId(String currentTaskId) {
		this.currentTaskId = currentTaskId;
	}
	public String getIdCollection() {
		return idCollection;
	}
	public void setIdCollection(String idCollection) {
		this.idCollection = idCollection;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getChangeStatus() {
		return changeStatus;
	}
	public void setChangeStatus(String changeStatus) {
		this.changeStatus = changeStatus;
	}
	public String getChangeStatusValue() {
		return changeStatusValue;
	}
	public void setChangeStatusValue(String changeStatusValue) {
		this.changeStatusValue = changeStatusValue;
	}
	public String getRequestid() {
		return requestid;
	}
	public void setRequestid(String requestid) {
		this.requestid = requestid;
	}
	public String getPopUpSearch() {
		return popUpSearch;
	}
	public void setPopUpSearch(String popUpSearch) {
		this.popUpSearch = popUpSearch;
	}
	public String getNoteRemarks() {
		return noteRemarks;
	}
	public void setNoteRemarks(String noteRemarks) {
		this.noteRemarks = noteRemarks;
	}
	public String getButtonClicked() {
		return buttonClicked;
	}
	public void setButtonClicked(String buttonClicked) {
		this.buttonClicked = buttonClicked;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getHiddenProjectId() {
		return hiddenProjectId;
	}
	public void setHiddenProjectId(String hiddenProjectId) {
		this.hiddenProjectId = hiddenProjectId;
	}	
	public String getHiddenReturnFlag() {
		return hiddenReturnFlag;
	}
	public void setHiddenReturnFlag(String hiddenReturnFlag) {
		this.hiddenReturnFlag = hiddenReturnFlag;
	}
	public String getStrNextTaskIds() {
		return strNextTaskIds;
	}
	public void setStrNextTaskIds(String strNextTaskIds) {
		this.strNextTaskIds = strNextTaskIds;
	}
	public String getStrProjectIds() {
		return strProjectIds;
	}
	public void setStrProjectIds(String strProjectIds) {
		this.strProjectIds = strProjectIds;
	}
	public FormFile getAttachment() {
		return attachment;
	}
	public void setAttachment(FormFile attachment) {
		this.attachment = attachment;
	}
	public String getTaskapproveComments() {
		return taskapproveComments;
	}
	public void setTaskapproveComments(String taskapproveComments) {
		this.taskapproveComments = taskapproveComments;
	}
	public String getSeachprojectId() {
		return seachprojectId;
	}
	public void setSeachprojectId(String seachprojectId) {
		this.seachprojectId = seachprojectId;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getStageId() {
		return stageId;
	}
	public void setStageId(String stageId) {
		this.stageId = stageId;
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
	public String getProjectworkflowid() {
		return projectworkflowid;
	}
	public void setProjectworkflowid(String projectworkflowid) {
		this.projectworkflowid = projectworkflowid;
	}
	public String getSearchWorkflowid() {
		return searchWorkflowid;
	}
	public void setSearchWorkflowid(String searchWorkflowid) {
		this.searchWorkflowid = searchWorkflowid;
	}
	public String getSearchprojectid() {
		return searchprojectid;
	}
	public void setSearchprojectid(String searchprojectid) {
		this.searchprojectid = searchprojectid;
	}
	public ArrayList<TmEmployee> getLstEmployee() {
		return lstEmployee;
	}
	public void setLstEmployee(ArrayList<TmEmployee> lstEmployee) {
		this.lstEmployee = lstEmployee;
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
	public String getIsTaskMandatory() {
		return isTaskMandatory;
	}
	public void setIsTaskMandatory(String isTaskMandatory) {
		this.isTaskMandatory = isTaskMandatory;
	}
	public String getDocname() {
		return docname;
	}
	public void setDocname(String docname) {
		this.docname = docname;
	}
	public String getSearchprojectworkflowid() {
		return searchprojectworkflowid;
	}
	public void setSearchprojectworkflowid(String searchprojectworkflowid) {
		this.searchprojectworkflowid = searchprojectworkflowid;
	}
	public String getSearchtaskid() {
		return searchtaskid;
	}
	public void setSearchtaskid(String searchtaskid) {
		this.searchtaskid = searchtaskid;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public ArrayList<TmRoles> getRoleList() {
		return roleList;
	}
	public void setRoleList(ArrayList<TmRoles> roleList) {
		this.roleList = roleList;
	}
	public String getRolemapped() {
		return rolemapped;
	}
	public void setRolemapped(String rolemapped) {
		this.rolemapped = rolemapped;
	}
	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	public String getTaskrejectComments() {
		return taskrejectComments;
	}
	public void setTaskrejectComments(String taskrejectComments) {
		this.taskrejectComments = taskrejectComments;
	}
	public String getMainprojectid() {
		return mainprojectid;
	}
	public void setMainprojectid(String mainprojectid) {
		this.mainprojectid = mainprojectid;
	}	
	public String getPageRecords() {
		return pageRecords;
	}
	public void setPageRecords(String pageRecords) {
		this.pageRecords = pageRecords;
	}	
}
