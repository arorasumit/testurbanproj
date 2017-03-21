package com.ibm.ioes.npd.hibernate.beans;

public class PlanExcelUploadDto {

	private String fileid;
	private String stageid;
	private String stagename;
	private String taskid;
	private String taskname;
	private String taskdesc;
	private String mandatory;
	private String reject_allowed;
	private String stakeholder_roleid;
	private String stakeholder_rolename;
	private String isfirst;
	private String islast;
	private String prev_task_ids;
	private String prev_tasks;
	private String planned_duration;
	private String document_uploaded_id;
	private String document_uploaded;
	private String remarks;
	private String errorlog;
	
	private String cycleSequenceNo;
	
	private String employeeId;
	private String employeeName;
	
	public String getDocument_uploaded() {
		return document_uploaded;
	}
	public void setDocument_uploaded(String document_uploaded) {
		this.document_uploaded = document_uploaded;
	}
	public String getDocument_uploaded_id() {
		return document_uploaded_id;
	}
	public void setDocument_uploaded_id(String document_uploaded_id) {
		this.document_uploaded_id = document_uploaded_id;
	}
	public String getErrorlog() {
		return errorlog;
	}
	public void setErrorlog(String errorlog) {
		this.errorlog = errorlog;
	}
	public String getFileid() {
		return fileid;
	}
	public void setFileid(String fileid) {
		this.fileid = fileid;
	}
	public String getIsfirst() {
		return isfirst;
	}
	public void setIsfirst(String isfirst) {
		this.isfirst = isfirst;
	}
	public String getIslast() {
		return islast;
	}
	public void setIslast(String islast) {
		this.islast = islast;
	}
	public String getMandatory() {
		return mandatory;
	}
	public void setMandatory(String mandatory) {
		this.mandatory = mandatory;
	}
	public String getPlanned_duration() {
		return planned_duration;
	}
	public void setPlanned_duration(String planned_duration) {
		this.planned_duration = planned_duration;
	}
	public String getPrev_task_ids() {
		return prev_task_ids;
	}
	public void setPrev_task_ids(String prev_task_ids) {
		this.prev_task_ids = prev_task_ids;
	}
	public String getPrev_tasks() {
		return prev_tasks;
	}
	public void setPrev_tasks(String prev_tasks) {
		this.prev_tasks = prev_tasks;
	}
	public String getReject_allowed() {
		return reject_allowed;
	}
	public void setReject_allowed(String reject_allowed) {
		this.reject_allowed = reject_allowed;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getStageid() {
		return stageid;
	}
	public void setStageid(String stageid) {
		this.stageid = stageid;
	}
	public String getStagename() {
		return stagename;
	}
	public void setStagename(String stagename) {
		this.stagename = stagename;
	}
	public String getStakeholder_roleid() {
		return stakeholder_roleid;
	}
	public void setStakeholder_roleid(String stakeholder_roleid) {
		this.stakeholder_roleid = stakeholder_roleid;
	}
	public String getStakeholder_rolename() {
		return stakeholder_rolename;
	}
	public void setStakeholder_rolename(String stakeholder_rolename) {
		this.stakeholder_rolename = stakeholder_rolename;
	}
	public String getTaskdesc() {
		return taskdesc;
	}
	public void setTaskdesc(String taskdesc) {
		this.taskdesc = taskdesc;
	}
	public String getTaskid() {
		return taskid;
	}
	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}
	public String getTaskname() {
		return taskname;
	}
	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getCycleSequenceNo() {
		return cycleSequenceNo;
	}
	public void setCycleSequenceNo(String cycleSequenceNo) {
		this.cycleSequenceNo = cycleSequenceNo;
	}
	
	
}
