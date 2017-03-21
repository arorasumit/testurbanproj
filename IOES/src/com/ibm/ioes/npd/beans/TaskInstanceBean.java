package com.ibm.ioes.npd.beans;

import java.util.ArrayList;


import com.ibm.ioes.npd.hibernate.beans.TmEmployee;
import com.ibm.ioes.npd.hibernate.beans.TmRoles;
import com.ibm.ioes.npd.hibernate.beans.TmWorkflowstage;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjecthierarchy;

public class TaskInstanceBean {

	
	private String taskMode=null;
	private String secondaryTaskMode=null;
	final public static String secondaryMode_mainWindow="mainWindow";
	final public static String secondaryMode_popUp="popUp";
	
	private String currentstageid;
    private String currenttaskid;
    //private String prevstageid;
    private String[] prevtaskid;
    private String isfirsttask;
    private String islasttask;
    private String stagestartdate;
    private String stageenddate;
    private String taskstartdate;
    private String taskenddate;
    private String actualstagestartdate;
    private String actualtaskenddate;
    private String actualtaskstartdate;
    private String actualstageenddate;
    private String taskduration;
    private String taskstatus;
    private String taskstakeholder;
    private String taskname;
    private String taskdesc;
    private String taskTaskinstructionremarks;
    private String taskIsattachment;
    private String taskCreatedby;
    private String taskCreateddate;
    private String taskModifiedby;
    private String taskModifieddate;
    private String taskReferencedocid;
    private String taskTaskpriority;
    private String taskIsemailtemplate;
    private String taskIsreminderrequired;
    private String taskTasktype;
    private String rejectionAllowed;
    
    private String taskIstaskdelegable;
    private String assignedtouserid;
    
    private String currentTaskStatus=null;
    
    
    ArrayList<TtrnProjecthierarchy> stageList=null;
    ArrayList<TmRoles> taskstakeholderList=null;
    ArrayList<TmEmployee> taskstakeholderUserList=null;
    ArrayList<TtrnProjecthierarchy> prevTaskList=null;
    ArrayList templateList=null;
    private ArrayList selectedPrevTask=null;
    ArrayList<TtrnProjecthierarchy> prevTaskUnSelectedList=null;
    ArrayList<TtrnProjecthierarchy> prevTaskSelectedList=null;
    
    private String[] unSelectedPrevtaskid=null;
    
	public String getActualstageenddate() {
		return actualstageenddate;
	}
	public void setActualstageenddate(String actualstageenddate) {
		this.actualstageenddate = actualstageenddate;
	}
	public String getActualstagestartdate() {
		return actualstagestartdate;
	}
	public void setActualstagestartdate(String actualstagestartdate) {
		this.actualstagestartdate = actualstagestartdate;
	}
	public String getActualtaskenddate() {
		return actualtaskenddate;
	}
	public void setActualtaskenddate(String actualtaskenddate) {
		this.actualtaskenddate = actualtaskenddate;
	}
	public String getActualtaskstartdate() {
		return actualtaskstartdate;
	}
	public void setActualtaskstartdate(String actualtaskstartdate) {
		this.actualtaskstartdate = actualtaskstartdate;
	}
	public String getAssignedtouserid() {
		return assignedtouserid;
	}
	public void setAssignedtouserid(String assignedtouserid) {
		this.assignedtouserid = assignedtouserid;
	}
	public String getCurrentstageid() {
		return currentstageid;
	}
	public void setCurrentstageid(String currentstageid) {
		this.currentstageid = currentstageid;
	}
	public String getCurrenttaskid() {
		return currenttaskid;
	}
	public void setCurrenttaskid(String currenttaskid) {
		this.currenttaskid = currenttaskid;
	}
	public String getIsfirsttask() {
		return isfirsttask;
	}
	public void setIsfirsttask(String isfirsttask) {
		this.isfirsttask = isfirsttask;
	}
	public String getIslasttask() {
		return islasttask;
	}
	public void setIslasttask(String islasttask) {
		this.islasttask = islasttask;
	}
	
	public String[] getPrevtaskid() {
		return prevtaskid;
	}
	public void setPrevtaskid(String[] prevtaskid) {
		this.prevtaskid = prevtaskid;
	}
	public String getStageenddate() {
		return stageenddate;
	}
	public void setStageenddate(String stageenddate) {
		this.stageenddate = stageenddate;
	}
	public ArrayList<TtrnProjecthierarchy> getStageList() {
		return stageList;
	}
	public void setStageList(ArrayList<TtrnProjecthierarchy> stageList) {
		this.stageList = stageList;
	}
	public String getStagestartdate() {
		return stagestartdate;
	}
	public void setStagestartdate(String stagestartdate) {
		this.stagestartdate = stagestartdate;
	}
	public String getTaskCreatedby() {
		return taskCreatedby;
	}
	public void setTaskCreatedby(String taskCreatedby) {
		this.taskCreatedby = taskCreatedby;
	}
	public String getTaskCreateddate() {
		return taskCreateddate;
	}
	public void setTaskCreateddate(String taskCreateddate) {
		this.taskCreateddate = taskCreateddate;
	}
	public String getTaskdesc() {
		return taskdesc;
	}
	public void setTaskdesc(String taskdesc) {
		this.taskdesc = taskdesc;
	}
	public String getTaskduration() {
		return taskduration;
	}
	public void setTaskduration(String taskduration) {
		this.taskduration = taskduration;
	}
	public String getTaskenddate() {
		return taskenddate;
	}
	public void setTaskenddate(String taskenddate) {
		this.taskenddate = taskenddate;
	}
	public String getTaskIsattachment() {
		return taskIsattachment;
	}
	public void setTaskIsattachment(String taskIsattachment) {
		this.taskIsattachment = taskIsattachment;
	}
	public String getTaskIsemailtemplate() {
		return taskIsemailtemplate;
	}
	public void setTaskIsemailtemplate(String taskIsemailtemplate) {
		this.taskIsemailtemplate = taskIsemailtemplate;
	}
	public String getTaskIsreminderrequired() {
		return taskIsreminderrequired;
	}
	public void setTaskIsreminderrequired(String taskIsreminderrequired) {
		this.taskIsreminderrequired = taskIsreminderrequired;
	}
	public String getTaskIstaskdelegable() {
		return taskIstaskdelegable;
	}
	public void setTaskIstaskdelegable(String taskIstaskdelegable) {
		this.taskIstaskdelegable = taskIstaskdelegable;
	}
	public String getTaskMode() {
		return taskMode;
	}
	public void setTaskMode(String taskMode) {
		this.taskMode = taskMode;
	}
	public String getTaskModifiedby() {
		return taskModifiedby;
	}
	public void setTaskModifiedby(String taskModifiedby) {
		this.taskModifiedby = taskModifiedby;
	}
	public String getTaskModifieddate() {
		return taskModifieddate;
	}
	public void setTaskModifieddate(String taskModifieddate) {
		this.taskModifieddate = taskModifieddate;
	}
	public String getTaskname() {
		return taskname;
	}
	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}
	public String getTaskReferencedocid() {
		return taskReferencedocid;
	}
	public void setTaskReferencedocid(String taskReferencedocid) {
		this.taskReferencedocid = taskReferencedocid;
	}
	public String getTaskstakeholder() {
		return taskstakeholder;
	}
	public void setTaskstakeholder(String taskstakeholder) {
		this.taskstakeholder = taskstakeholder;
	}
	public ArrayList<TmRoles> getTaskstakeholderList() {
		return taskstakeholderList;
	}
	public void setTaskstakeholderList(ArrayList<TmRoles> taskstakeholderList) {
		this.taskstakeholderList = taskstakeholderList;
	}
	public ArrayList<TmEmployee> getTaskstakeholderUserList() {
		return taskstakeholderUserList;
	}
	public void setTaskstakeholderUserList(
			ArrayList<TmEmployee> taskstakeholderUserList) {
		this.taskstakeholderUserList = taskstakeholderUserList;
	}
	public String getTaskstartdate() {
		return taskstartdate;
	}
	public void setTaskstartdate(String taskstartdate) {
		this.taskstartdate = taskstartdate;
	}
	public String getTaskstatus() {
		return taskstatus;
	}
	public void setTaskstatus(String taskstatus) {
		this.taskstatus = taskstatus;
	}
	public String getTaskTaskinstructionremarks() {
		return taskTaskinstructionremarks;
	}
	public void setTaskTaskinstructionremarks(String taskTaskinstructionremarks) {
		this.taskTaskinstructionremarks = taskTaskinstructionremarks;
	}
	public String getTaskTaskpriority() {
		return taskTaskpriority;
	}
	public void setTaskTaskpriority(String taskTaskpriority) {
		this.taskTaskpriority = taskTaskpriority;
	}
	public String getTaskTasktype() {
		return taskTasktype;
	}
	public void setTaskTasktype(String taskTasktype) {
		this.taskTasktype = taskTasktype;
	}
	public ArrayList<TtrnProjecthierarchy> getPrevTaskList() {
		return prevTaskList;
	}
	public void setPrevTaskList(ArrayList<TtrnProjecthierarchy> prevTaskList) {
		this.prevTaskList = prevTaskList;
	}
	public ArrayList getTemplateList() {
		return templateList;
	}
	public void setTemplateList(ArrayList templateList) {
		this.templateList = templateList;
	}
	public String getSecondaryTaskMode() {
		return secondaryTaskMode;
	}
	public void setSecondaryTaskMode(String secondaryTaskMode) {
		this.secondaryTaskMode = secondaryTaskMode;
	}
	public String getCurrentTaskStatus() {
		return currentTaskStatus;
	}
	public void setCurrentTaskStatus(String currentTaskStatus) {
		this.currentTaskStatus = currentTaskStatus;
	}
	public String getRejectionAllowed() {
		return rejectionAllowed;
	}
	public void setRejectionAllowed(String rejectionAllowed) {
		this.rejectionAllowed = rejectionAllowed;
	}
	public ArrayList getSelectedPrevTask() {
		return selectedPrevTask;
	}
	public void setSelectedPrevTask(ArrayList selectedPrevTask) {
		this.selectedPrevTask = selectedPrevTask;
	}
	public ArrayList<TtrnProjecthierarchy> getPrevTaskSelectedList() {
		return prevTaskSelectedList;
	}
	public void setPrevTaskSelectedList(
			ArrayList<TtrnProjecthierarchy> prevTaskSelectedList) {
		this.prevTaskSelectedList = prevTaskSelectedList;
	}
	public ArrayList<TtrnProjecthierarchy> getPrevTaskUnSelectedList() {
		return prevTaskUnSelectedList;
	}
	public void setPrevTaskUnSelectedList(
			ArrayList<TtrnProjecthierarchy> prevTaskUnSelectedList) {
		this.prevTaskUnSelectedList = prevTaskUnSelectedList;
	}
	public String[] getUnSelectedPrevtaskid() {
		return unSelectedPrevtaskid;
	}
	public void setUnSelectedPrevtaskid(String[] unSelectedPrevtaskid) {
		this.unSelectedPrevtaskid = unSelectedPrevtaskid;
	}
    
	
	
    
}
