/**
 * 
 */
package com.ibm.ioes.npd.beans;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

import com.ibm.ioes.npd.hibernate.beans.TmRoles;
import com.ibm.ioes.npd.hibernate.beans.TmWorkflowtasks;

/**
 * @author Sanjay
 * 
 */
public class MasterProjectPlan extends ActionForm {

	private static final long serialVersionUID = 7069912811901631712L;

	private String npdCategoryId;
	
	private String  npdCategoryId_stage;

	private ArrayList npdCategoryList;

	private String stage;

	private String stageDescription;

	private String stageId;

	private ArrayList stageList;

	private String taskId;
	
	private String[] previousTaskId;

	private ArrayList taskList;
	
	private boolean taskTasktype;
	
	private boolean rejectionAllowed;
	
	private String task;
	
	private String task_update;

	private String taskDescription;

	private String taskDescription_update;
	
	private boolean first;

	private boolean last;

	private boolean attachment;

	private String templateId;

	private ArrayList templateList;

	private String stakeHolderId;
	
	private String stakeHolderId_update;

	private ArrayList stakeHolderList;

	private String remarks;
	
	private String remarks_update;
	
	private String plannedDuration;
	
	private String plannedDuration_update;
	
	private long updatedTaskID;
	
	private String npdCategoryId_update = null;
	
	private String workflowDesc;

	private String taskListLength = null;
	
	private String taskNo = null;

	//new
	
	private ArrayList existingWorkflowList = null;
	
	private String workflowId = null;
	
	private String opType = null;
	
	
	private TmWorkflowtasks firstTask=null;
	private TmWorkflowtasks lastTask=null;
	
	ArrayList<TmWorkflowtasks> noPreviousDefinedTasks=null;
	
	ArrayList<TmRoles> emptyRoles=null;
	
	//rohit verma new cr
	private String deleteTaskId;
	
	private String noDeleteTaskId;
	
	private String selectedTaskId=null;
	
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}

	/**
	 * @return the workflowDesc
	 */
	public String getWorkflowDesc() {
		return workflowDesc;
	}

	/**
	 * @param workflowDesc the workflowDesc to set
	 */
	public void setWorkflowDesc(String workflowDesc) {
		this.workflowDesc = workflowDesc;
	}

	/**
	 * @return the updatedTaskID
	 */
	public long getUpdatedTaskID() {
		return updatedTaskID;
	}

	/**
	 * @param updatedTaskID the updatedTaskID to set
	 */
	public void setUpdatedTaskID(long updatedTaskID) {
		this.updatedTaskID = updatedTaskID;
	}

	/**
	 * @return the templateId
	 */
	public String getTemplateId() {
		return templateId;
	}

	/**
	 * @param templateId
	 *            the templateId to set
	 */
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	/**
	 * @return the templateList
	 */
	public ArrayList getTemplateList() {
		return templateList;
	}

	/**
	 * @param templateList
	 *            the templateList to set
	 */
	public void setTemplateList(ArrayList templateList) {
		this.templateList = templateList;
	}

	/**
	 * @return the attachment
	 */
	public boolean isAttachment() {
		return attachment;
	}

	/**
	 * @param attachment
	 *            the attachment to set
	 */
	public void setAttachment(boolean attachment) {
		this.attachment = attachment;
	}

	/**
	 * @return the stage
	 */
	public String getStage() {
		return stage;
	}

	/**
	 * @param stage
	 *            the stage to set
	 */
	public void setStage(String stage) {
		if (stage != null ) {
			this.stage = stage.trim();
		}else
		{
			this.stage=null;
		}
	}

	/**
	 * @return the stageDescription
	 */
	public String getStageDescription() {
		return stageDescription;
	}

	/**
	 * @param stageDescription
	 *            the stageDescription to set
	 */
	public void setStageDescription(String stageDescription) {
		this.stageDescription = stageDescription;
	}

	/**
	 * @return the npdCategoryId
	 */
	public String getNpdCategoryId() {
		return npdCategoryId;
	}

	/**
	 * @param npdCategoryId
	 *            the npdCategoryId to set
	 */
	public void setNpdCategoryId(String npdCategoryId) {
		this.npdCategoryId = npdCategoryId;
	}

	/**
	 * @return the npdCategoryList
	 */
	public ArrayList getNpdCategoryList() {
		return npdCategoryList;
	}

	/**
	 * @param npdCategoryList
	 *            the npdCategoryList to set
	 */
	public void setNpdCategoryList(ArrayList npdCategoryList) {
		this.npdCategoryList = npdCategoryList;
	}

	/**
	 * @return the stageId
	 */
	public String getStageId() {
		return stageId;
	}

	/**
	 * @param stageId
	 *            the stageId to set
	 */
	public void setStageId(String stageId) {
		this.stageId = stageId;
	}

	/**
	 * @return the stageList
	 */
	public ArrayList getStageList() {
		return stageList;
	}

	/**
	 * @param stageList
	 *            the stageList to set
	 */
	public void setStageList(ArrayList stageList) {
		this.stageList = stageList;
	}

	/**
	 * @return the task
	 */
	public String getTask() {
		return task;
	}

	/**
	 * @param task
	 *            the task to set
	 */
	public void setTask(String task) {
		if (task != null && !task.equalsIgnoreCase("")) {
			this.task = task.trim();
		}
	}

	/**
	 * @return the taskDescription
	 */
	public String getTaskDescription() {
		return taskDescription;
	}

	/**
	 * @param taskDescription
	 *            the taskDescription to set
	 */
	public void setTaskDescription(String taskDescription) {
		if (taskDescription != null && !taskDescription.equalsIgnoreCase("")) {
			this.taskDescription = taskDescription.trim();
		}
	}

	
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	/**
	 * @return the taskList
	 */
	public ArrayList getTaskList() {
		return taskList;
	}

	/**
	 * @param taskList
	 *            the taskList to set
	 */
	public void setTaskList(ArrayList taskList) {
		this.taskList = taskList;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *            the remarks to set
	 */
	public void setRemarks(String remarks) {
		if (remarks != null && !remarks.equalsIgnoreCase("")) {
			this.remarks = remarks.trim();
		}
	}

	/**
	 * @return the first
	 */
	public boolean isFirst() {
		return first;
	}

	/**
	 * @param first
	 *            the first to set
	 */
	public void setFirst(boolean first) {
		this.first = first;
	}

	/**
	 * @return the last
	 */
	public boolean isLast() {
		return last;
	}

	/**
	 * @param last
	 *            the last to set
	 */
	public void setLast(boolean last) {
		this.last = last;
	}

	/**
	 * @return the stakeHolderId
	 */
	public String getStakeHolderId() {
		return stakeHolderId;
	}

	/**
	 * @param stakeHolderId
	 *            the stakeHolderId to set
	 */
	public void setStakeHolderId(String stakeHolderId) {
		this.stakeHolderId = stakeHolderId;
	}

	/**
	 * @return the stakeHolderList
	 */
	public ArrayList getStakeHolderList() {
		return stakeHolderList;
	}

	/**
	 * @param stakeHolderList
	 *            the stakeHolderList to set
	 */
	public void setStakeHolderList(ArrayList stakeHolderList) {
		this.stakeHolderList = stakeHolderList;
	}

	/**
	 * @return the plannedDuration
	 */
	public String getPlannedDuration() {
		return plannedDuration;
	}

	/**
	 * @param plannedDuration the plannedDuration to set
	 */
	public void setPlannedDuration(String plannedDuration) {
		this.plannedDuration = plannedDuration;
	}

	/**
	 * @return the plannedDuration_update
	 */
	public String getPlannedDuration_update() {
		return plannedDuration_update;
	}

	/**
	 * @param plannedDuration_update the plannedDuration_update to set
	 */
	public void setPlannedDuration_update(String plannedDuration_update) {
		this.plannedDuration_update = plannedDuration_update;
	}

	/**
	 * @return the remarks_update
	 */
	public String getRemarks_update() {
		return remarks_update;
	}

	/**
	 * @param remarks_update the remarks_update to set
	 */
	public void setRemarks_update(String remarks_update) {
		this.remarks_update = remarks_update;
	}

	/**
	 * @return the stakeHolderId_update
	 */
	public String getStakeHolderId_update() {
		return stakeHolderId_update;
	}

	/**
	 * @param stakeHolderId_update the stakeHolderId_update to set
	 */
	public void setStakeHolderId_update(String stakeHolderId_update) {
		this.stakeHolderId_update = stakeHolderId_update;
	}

	/**
	 * @return the task_update
	 */
	public String getTask_update() {
		return task_update;
	}

	/**
	 * @param task_update the task_update to set
	 */
	public void setTask_update(String task_update) {
		this.task_update = task_update;
	}

	/**
	 * @return the taskDescription_update
	 */
	public String getTaskDescription_update() {
		return taskDescription_update;
	}

	/**
	 * @param taskDescription_update the taskDescription_update to set
	 */
	public void setTaskDescription_update(String taskDescription_update) {
		this.taskDescription_update = taskDescription_update;
	}

	/**
	 * @return the npdCategoryId_update
	 */
	public String getNpdCategoryId_update() {
		return npdCategoryId_update;
	}

	/**
	 * @param npdCategoryId_update the npdCategoryId_update to set
	 */
	public void setNpdCategoryId_update(String npdCategoryId_update) {
		this.npdCategoryId_update = npdCategoryId_update;
	}

	/**
	 * @return the npdCategoryId_stage
	 */
	public String getNpdCategoryId_stage() {
		return npdCategoryId_stage;
	}

	/**
	 * @param npdCategoryId_stage the npdCategoryId_stage to set
	 */
	public void setNpdCategoryId_stage(String npdCategoryId_stage) {
		this.npdCategoryId_stage = npdCategoryId_stage;
	}

	/**
	 * @return the taskListLength
	 */
	public String getTaskListLength() {
		return taskListLength;
	}

	/**
	 * @param taskListLength the taskListLength to set
	 */
	public void setTaskListLength(String taskListLength) {
		this.taskListLength = taskListLength;
	}

	/**
	 * @return the taskNo
	 */
	public String getTaskNo() {
		return taskNo;
	}

	/**
	 * @param taskNo the taskNo to set
	 */
	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}

	public ArrayList getExistingWorkflowList() {
		return existingWorkflowList;
	}

	public void setExistingWorkflowList(ArrayList existingWorkflowList) {
		this.existingWorkflowList = existingWorkflowList;
	}

	public String[] getPreviousTaskId() {
		return previousTaskId;
	}

	public void setPreviousTaskId(String[] previousTaskId) {
		this.previousTaskId = previousTaskId;
	}

	public boolean isTaskTasktype() {
		return taskTasktype;
	}

	public void setTaskTasktype(boolean taskTasktype) {
		this.taskTasktype = taskTasktype;
	}

	public TmWorkflowtasks getFirstTask() {
		return firstTask;
	}

	public void setFirstTask(TmWorkflowtasks firstTask) {
		this.firstTask = firstTask;
	}

	public TmWorkflowtasks getLastTask() {
		return lastTask;
	}

	public void setLastTask(TmWorkflowtasks lastTask) {
		this.lastTask = lastTask;
	}

	public ArrayList<TmWorkflowtasks> getNoPreviousDefinedTasks() {
		return noPreviousDefinedTasks;
	}

	public void setNoPreviousDefinedTasks(
			ArrayList<TmWorkflowtasks> noPreviousDefinedTasks) {
		this.noPreviousDefinedTasks = noPreviousDefinedTasks;
	}

	public boolean isRejectionAllowed() {
		return rejectionAllowed;
	}

	public void setRejectionAllowed(boolean rejectionAllowed) {
		this.rejectionAllowed = rejectionAllowed;
	}

	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	public ArrayList<TmRoles> getEmptyRoles() {
		return emptyRoles;
	}

	public void setEmptyRoles(ArrayList<TmRoles> emptyRoles) {
		this.emptyRoles = emptyRoles;
	}

	public String getDeleteTaskId() {
		return deleteTaskId;
	}

	public void setDeleteTaskId(String deleteTaskId) {
		this.deleteTaskId = deleteTaskId;
	}

	public String getNoDeleteTaskId() {
		return noDeleteTaskId;
	}

	public void setNoDeleteTaskId(String noDeleteTaskId) {
		this.noDeleteTaskId = noDeleteTaskId;
	}

	public String getSelectedTaskId() {
		return selectedTaskId;
	}

	public void setSelectedTaskId(String selectedTaskId) {
		this.selectedTaskId = selectedTaskId;
	}

	

}
