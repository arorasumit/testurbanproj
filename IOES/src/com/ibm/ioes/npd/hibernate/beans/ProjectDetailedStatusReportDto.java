package com.ibm.ioes.npd.hibernate.beans;
// Generated Dec 9, 2009 11:12:38 AM by Hibernate Tools 3.1.0.beta4

import java.util.Date;

import com.ibm.ioes.npd.utilities.PagingSorting;


/**
 * TmAppconfig generated by hbm2java
 */

public class ProjectDetailedStatusReportDto  implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String projectName;	
	private int projectID;
	PagingSorting pagingSorting=null;
	private String searchProjectPlanName;
	private String searchProjectName;
	private int searchProjectid;
	private String searchProductCatId;
	private int searchByProjectID;

	private String stageName;
	private String taskName;
	private String taskStartDate;
	private String taskEndDate;
	private String actualStartDate;
	private String actualEndDate;
	private String ProjectStatus;
	private String taskStakeHolder;
	private String roleName;
	private int delays;
	private String docName ;
	private String docType;
	private String uploadedDoc;
	private long currentTaskId;
	
	byte[] fileBytes = null;
	
	public int getSearchByProjectID() {
		return searchByProjectID;
	}
	public void setSearchByProjectID(int searchByProjectID) {
		this.searchByProjectID = searchByProjectID;
	}
	
	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}
	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
	}
	
	
	public String getSearchProjectPlanName() {
		return searchProjectPlanName;
	}
	public void setSearchProjectPlanName(String searchProjectPlanName) {
		this.searchProjectPlanName = searchProjectPlanName;
	}
	
	public String getSearchProjectName() {
		return searchProjectName;
	}
	public void setSearchProjectName(String searchProjectName) {
		this.searchProjectName = searchProjectName;
	}
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public int getSearchProjectid() {
		return searchProjectid;
	}
	public void setSearchProjectid(int searchProjectid) {
		this.searchProjectid = searchProjectid;
	}

	public int getProjectID() {
		return projectID;
	}
	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}
	public String getSearchProductCatId() {
		return searchProductCatId;
	}
	public void setSearchProductCatId(String searchProductCatId) {
		this.searchProductCatId = searchProductCatId;
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
	public int getDelays() {
		return delays;
	}
	public void setDelays(int delays) {
		this.delays = delays;
	}
	public String getDocName() {
		return docName;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public String getProjectStatus() {
		return ProjectStatus;
	}
	public void setProjectStatus(String projectStatus) {
		ProjectStatus = projectStatus;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getStageName() {
		return stageName;
	}
	public void setStageName(String stageName) {
		this.stageName = stageName;
	}
	public String getTaskEndDate() {
		return taskEndDate;
	}
	public void setTaskEndDate(String taskEndDate) {
		this.taskEndDate = taskEndDate;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskStakeHolder() {
		return taskStakeHolder;
	}
	public void setTaskStakeHolder(String taskStakeHolder) {
		this.taskStakeHolder = taskStakeHolder;
	}
	public String getTaskStartDate() {
		return taskStartDate;
	}
	public void setTaskStartDate(String taskStartDate) {
		this.taskStartDate = taskStartDate;
	}
	public String getUploadedDoc() {
		return uploadedDoc;
	}
	public void setUploadedDoc(String uploadedDoc) {
		this.uploadedDoc = uploadedDoc;
	}
	public long getCurrentTaskId() {
		return currentTaskId;
	}
	public void setCurrentTaskId(long currentTaskId) {
		this.currentTaskId = currentTaskId;
	}
	public byte[] getFileBytes() {
		return fileBytes;
	}
	public void setFileBytes(byte[] fileBytes) {
		this.fileBytes = fileBytes;
	}	
}