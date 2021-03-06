package com.ibm.ioes.npd.hibernate.beans;

// Generated Dec 9, 2009 11:12:39 AM by Hibernate Tools 3.1.0.beta4

import java.util.Date;

/**
 * TmWorkflowtasks generated by hbm2java
 */

public class TmWorkflowtasks implements java.io.Serializable {

	// Fields

	private long taskid;

	private TmWorkflowstage stage;

	private String taskname;

	private String taskdesc;

	private Integer planneddurationdays;

	private TmRoles stakeholder;

	private Integer tasktype;

	private Integer istaskdelegable;

	private Integer isfirsttask;

	private String taskinstructionremarks;

	private Integer taskpriority;

	private Integer isattachment;
	
	private Integer isrejectionallowed;

	private Integer isemailtemplate;

	private Integer isreminderrequired;

	private int createdby;

	private Date createddate;

	private Integer modifiedby;

	private Date modifieddate;

	private int isactive;

	private Integer islasttask;

	private Long prevtaskid;
	
	//private TmReferencedocs referenceDoc ;
	private Long referenceDocId;
	// Constructors

	/** default constructor */
	public TmWorkflowtasks() {
	}

	/** minimal constructor */
	public TmWorkflowtasks(long taskid, TmWorkflowstage stage, TmRoles stakeholder,
			int createdby, Date createddate, int isactive,TmReferencedocs referenceDoc ) {
		this.taskid = taskid;
		this.stage = stage;
		this.stakeholder = stakeholder;
		this.createdby = createdby;
		this.createddate = createddate;
		this.isactive = isactive;
		//this.referenceDoc = referenceDoc;
	}

	/** full constructor */
	public TmWorkflowtasks(long taskid, TmWorkflowstage stage, String taskname,
			String taskdesc, Integer planneddurationdays, TmRoles stakeholder,
			Integer tasktype, Integer istaskdelegable, Integer isfirsttask,
			String taskinstructionremarks, Integer taskpriority,
			Integer isattachment, Integer isemailtemplate,
			Integer isreminderrequired, int createdby, Date createddate,
			Integer modifiedby, Date modifieddate, int isactive,
			Integer islasttask, Long prevtaskid,TmReferencedocs referenceDoc ) {
		this.taskid = taskid;
		this.stage = stage;
		this.taskname = taskname;
		this.taskdesc = taskdesc;
		this.planneddurationdays = planneddurationdays;
		this.stakeholder = stakeholder;
		this.tasktype = tasktype;
		this.istaskdelegable = istaskdelegable;
		this.isfirsttask = isfirsttask;
		this.taskinstructionremarks = taskinstructionremarks;
		this.taskpriority = taskpriority;
		this.isattachment = isattachment;
		this.isemailtemplate = isemailtemplate;
		this.isreminderrequired = isreminderrequired;
		this.createdby = createdby;
		this.createddate = createddate;
		this.modifiedby = modifiedby;
		this.modifieddate = modifieddate;
		this.isactive = isactive;
		this.islasttask = islasttask;
		this.prevtaskid = prevtaskid;
		//this.referenceDoc = referenceDoc;
	}

	// Property accessors

	public long getTaskid() {
		return this.taskid;
	}

	public void setTaskid(long taskid) {
		this.taskid = taskid;
	}

	/**
	 * @return the stage
	 */
	public TmWorkflowstage getStage() {
		return stage;
	}

	/**
	 * @param stage
	 *            the stage to set
	 */
	public void setStage(TmWorkflowstage stage) {
		this.stage = stage;
	}

	public String getTaskname() {
		return this.taskname;
	}

	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}

	public String getTaskdesc() {
		return this.taskdesc;
	}

	public void setTaskdesc(String taskdesc) {
		this.taskdesc = taskdesc;
	}

	public Integer getPlanneddurationdays() {
		return this.planneddurationdays;
	}

	public void setPlanneddurationdays(Integer planneddurationdays) {
		this.planneddurationdays = planneddurationdays;
	}

	


	public TmRoles getStakeholder() {
		return stakeholder;
	}

	public void setStakeholder(TmRoles stakeholder) {
		this.stakeholder = stakeholder;
	}

	public Integer getTasktype() {
		return this.tasktype;
	}

	public void setTasktype(Integer tasktype) {
		this.tasktype = tasktype;
	}

	public Integer getIstaskdelegable() {
		return this.istaskdelegable;
	}

	public void setIstaskdelegable(Integer istaskdelegable) {
		this.istaskdelegable = istaskdelegable;
	}

	public Integer getIsfirsttask() {
		return this.isfirsttask;
	}

	public void setIsfirsttask(Integer isfirsttask) {
		this.isfirsttask = isfirsttask;
	}

	public String getTaskinstructionremarks() {
		return this.taskinstructionremarks;
	}

	public void setTaskinstructionremarks(String taskinstructionremarks) {
		this.taskinstructionremarks = taskinstructionremarks;
	}

	public Integer getTaskpriority() {
		return this.taskpriority;
	}

	public void setTaskpriority(Integer taskpriority) {
		this.taskpriority = taskpriority;
	}

	public Integer getIsattachment() {
		return this.isattachment;
	}

	public void setIsattachment(Integer isattachment) {
		this.isattachment = isattachment;
	}

	public Integer getIsemailtemplate() {
		return this.isemailtemplate;
	}

	public void setIsemailtemplate(Integer isemailtemplate) {
		this.isemailtemplate = isemailtemplate;
	}

	public Integer getIsreminderrequired() {
		return this.isreminderrequired;
	}

	public void setIsreminderrequired(Integer isreminderrequired) {
		this.isreminderrequired = isreminderrequired;
	}

	public int getCreatedby() {
		return this.createdby;
	}

	public void setCreatedby(int createdby) {
		this.createdby = createdby;
	}

	public Date getCreateddate() {
		return this.createddate;
	}

	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}

	public Integer getModifiedby() {
		return this.modifiedby;
	}

	public void setModifiedby(Integer modifiedby) {
		this.modifiedby = modifiedby;
	}

	public Date getModifieddate() {
		return this.modifieddate;
	}

	public void setModifieddate(Date modifieddate) {
		this.modifieddate = modifieddate;
	}

	public int getIsactive() {
		return this.isactive;
	}

	public void setIsactive(int isactive) {
		this.isactive = isactive;
	}

	public Integer getIslasttask() {
		return this.islasttask;
	}

	public void setIslasttask(Integer islasttask) {
		this.islasttask = islasttask;
	}

	public Long getPrevtaskid() {
		return this.prevtaskid;
	}

	public void setPrevtaskid(Long prevtaskid) {
		this.prevtaskid = prevtaskid;
	}

	public Long getReferenceDocId() {
		return referenceDocId;
	}

	public void setReferenceDocId(Long referenceDocId) {
		this.referenceDocId = referenceDocId;
	}

	public Integer getIsrejectionallowed() {
		return isrejectionallowed;
	}

	public void setIsrejectionallowed(Integer isrejectionallowed) {
		this.isrejectionallowed = isrejectionallowed;
	}


}
