package com.ibm.ioes.forms;

import java.util.Date;

public class WorkflowTaskDTO {
	
	private int taskId;
	private int taskTypeId;     
	private int  ownerTypeId;
	private int taskStatusId;     
	private Date taskStartDate;
	private Date taskEndDate;
	private int orderNo;
	private int createdBy;
	private Date createdDate;
	private int rejectedBy;
	private Date rejectedDate;
	private int isRejected;
	private int prevTaskId;
	private String actionRemarks;
	private String taskName;
	private int currentTaskId;
	private int isFirstTask;
	private int isLastTask;
	private int changeOrderTaskStatus;
	private int taskAssignedTo;
	private int lastAssginedBy;
	private int rejectionId;
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public int getTaskTypeId() {
		return taskTypeId;
	}
	public void setTaskTypeId(int taskTypeId) {
		this.taskTypeId = taskTypeId;
	}
	public int getOwnerTypeId() {
		return ownerTypeId;
	}
	public void setOwnerTypeId(int ownerTypeId) {
		this.ownerTypeId = ownerTypeId;
	}
	public int getTaskStatusId() {
		return taskStatusId;
	}
	public void setTaskStatusId(int taskStatusId) {
		this.taskStatusId = taskStatusId;
	}
	public Date getTaskStartDate() {
		return taskStartDate;
	}
	public void setTaskStartDate(Date taskStartDate) {
		this.taskStartDate = taskStartDate;
	}
	public Date getTaskEndDate() {
		return taskEndDate;
	}
	public void setTaskEndDate(Date taskEndDate) {
		this.taskEndDate = taskEndDate;
	}
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public int getRejectedBy() {
		return rejectedBy;
	}
	public void setRejectedBy(int rejectedBy) {
		this.rejectedBy = rejectedBy;
	}
	public Date getRejectedDate() {
		return rejectedDate;
	}
	public void setRejectedDate(Date rejectedDate) {
		this.rejectedDate = rejectedDate;
	}
	public int getIsRejected() {
		return isRejected;
	}
	public void setIsRejected(int isRejected) {
		this.isRejected = isRejected;
	}
	public int getPrevTaskId() {
		return prevTaskId;
	}
	public void setPrevTaskId(int prevTaskId) {
		this.prevTaskId = prevTaskId;
	}
	public String getActionRemarks() {
		return actionRemarks;
	}
	public void setActionRemarks(String actionRemarks) {
		this.actionRemarks = actionRemarks;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public int getCurrentTaskId() {
		return currentTaskId;
	}
	public void setCurrentTaskId(int currentTaskId) {
		this.currentTaskId = currentTaskId;
	}
	public int getIsFirstTask() {
		return isFirstTask;
	}
	public void setIsFirstTask(int isFirstTask) {
		this.isFirstTask = isFirstTask;
	}
	public int getIsLastTask() {
		return isLastTask;
	}
	public void setIsLastTask(int isLastTask) {
		this.isLastTask = isLastTask;
	}
	public int getChangeOrderTaskStatus() {
		return changeOrderTaskStatus;
	}
	public void setChangeOrderTaskStatus(int changeOrderTaskStatus) {
		this.changeOrderTaskStatus = changeOrderTaskStatus;
	}
	public int getTaskAssignedTo() {
		return taskAssignedTo;
	}
	public void setTaskAssignedTo(int taskAssignedTo) {
		this.taskAssignedTo = taskAssignedTo;
	}
	public int getLastAssginedBy() {
		return lastAssginedBy;
	}
	public void setLastAssginedBy(int lastAssginedBy) {
		this.lastAssginedBy = lastAssginedBy;
	}
	public int getRejectionId() {
		return rejectionId;
	}
	public void setRejectionId(int rejectionId) {
		this.rejectionId = rejectionId;
	}
	
	
}
