package com.ibm.ioes.npd.hibernate.beans;

import org.apache.struts.upload.FormFile;

import com.ibm.ioes.npd.utilities.PagingSorting;

public class RepositoryUploadDto implements java.io.Serializable
{
	private int npdCategoryID;
	
	private String npdCategoryName;
	
	private int projectID;
	
	private String projectName;
	
	private int projectStatus;
	
	private int searchProjectID;
	
	private String searchProjectName;
	
	private int stageID;
	
	private String stageName;
	
	private int taskID;
	
	private String taskName;
	
	private String docName;
	
	private FormFile attachment;
	
	private int projectWorkflowID;
	
	PagingSorting pagingSorting=null;

	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}

	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
	}

	public int getSearchProjectID() {
		return searchProjectID;
	}

	public void setSearchProjectID(int searchProjectID) {
		this.searchProjectID = searchProjectID;
	}

	public String getSearchProjectName() {
		return searchProjectName;
	}

	public void setSearchProjectName(String searchProjectName) {
		this.searchProjectName = searchProjectName;
	}

	public int getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(int projectStatus) {
		this.projectStatus = projectStatus;
	}

	public String getNpdCategoryName() {
		return npdCategoryName;
	}

	public void setNpdCategoryName(String npdCategoryName) {
		this.npdCategoryName = npdCategoryName;
	}

	public int getNpdCategoryID() {
		return npdCategoryID;
	}

	public void setNpdCategoryID(int npdCategoryID) {
		this.npdCategoryID = npdCategoryID;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public int getProjectID() {
		return projectID;
	}

	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}

	public int getStageID() {
		return stageID;
	}

	public void setStageID(int stageID) {
		this.stageID = stageID;
	}

	public String getStageName() {
		return stageName;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
	}

	public int getTaskID() {
		return taskID;
	}

	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}


	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public FormFile getAttachment() {
		return attachment;
	}

	public void setAttachment(FormFile attachment) {
		this.attachment = attachment;
	}

	public int getProjectWorkflowID() {
		return projectWorkflowID;
	}

	public void setProjectWorkflowID(int projectWorkflowID) {
		this.projectWorkflowID = projectWorkflowID;
	}
	
}
