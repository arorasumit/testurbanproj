package com.ibm.ioes.npd.beans;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

import com.ibm.ioes.npd.utilities.PagingSorting;

import java.util.ArrayList;

public class RepositoryUploadBean extends ActionForm
{
	private ArrayList listNPDcategory;
	
	private String npdCategoryid;
	
	private String npdCategoryFilter;
	
	private ArrayList listProjects;
	
	private String projectName;
	
	private String projectID;
	
	private PagingSorting pagingSorting=null;
	
	private String searchProjectID;
	
	private String methodName;
	
	private ArrayList listStage;
	
	private String stageFilter;
	
	private String docType;
	
	private ArrayList listTask;
	
	private String selectTask;
	
	private String selectedTaskName;
	
	private String selectedTaskID;
	
	private String docName;
	
	private FormFile attachment;
	
	private String stageID;
	
	private String projWorkFlowID;
	
	private String taskName;
	
	private String stageName;
	
	private ArrayList listProjectPlan;
	
	private String selectedProjID;
	
	private String selectedProjName;
	
	private String selectedWorkFlowID;
		
	public ArrayList getListProjectPlan() {
		return listProjectPlan;
	}

	public void setListProjectPlan(ArrayList listProjectPlan) {
		this.listProjectPlan = listProjectPlan;
	}

	public String getStageID() {
		return stageID;
	}

	public void setStageID(String stageID) {
		this.stageID = stageID;
	}

	public String getSelectTask() {
		return selectTask;
	}

	public void setSelectTask(String selectTask) {
		this.selectTask = selectTask;
	}

	public ArrayList getListTask() {
		return listTask;
	}

	public void setListTask(ArrayList listTask) {
		this.listTask = listTask;
	}

	public String getStageFilter() {
		return stageFilter;
	}

	public void setStageFilter(String stageFilter) {
		this.stageFilter = stageFilter;
	}

	public ArrayList getListStage() {
		return listStage;
	}

	public void setListStage(ArrayList listStage) {
		this.listStage = listStage;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getSearchProjectID() {
		return searchProjectID;
	}

	public void setSearchProjectID(String searchProjectID) {
		if (searchProjectID != null) {
			this.searchProjectID = searchProjectID.trim();
		}
	}

	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}

	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
	}

	public RepositoryUploadBean()
	{
		pagingSorting=new PagingSorting();
	}

	public String getNpdCategoryFilter() {
		return npdCategoryFilter;
	}

	public void setNpdCategoryFilter(String npdCategoryFilter) {
		this.npdCategoryFilter = npdCategoryFilter;
	}

	public String getNpdCategoryid() {
		return npdCategoryid;
	}

	public void setNpdCategoryid(String npdCategoryid) {
		this.npdCategoryid = npdCategoryid;
	}

	public ArrayList getListNPDcategory() {
		return listNPDcategory;
	}

	public void setListNPDcategory(ArrayList listNPDcategory) {
		this.listNPDcategory = listNPDcategory;
	}

	public ArrayList getListProjects() {
		return listProjects;
	}

	public void setListProjects(ArrayList listProjects) {
		this.listProjects = listProjects;
	}

	public String getProjectID() {
		return projectID;
	}

	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getSelectedTaskID() {
		return selectedTaskID;
	}

	public void setSelectedTaskID(String selectedTaskID) {
		this.selectedTaskID = selectedTaskID;
	}

	public String getSelectedTaskName() {
		return selectedTaskName;
	}

	public void setSelectedTaskName(String selectedTaskName) {
		this.selectedTaskName = selectedTaskName;
	}

	

	public FormFile getAttachment() {
		return attachment;
	}

	public void setAttachment(FormFile attachment) {
		this.attachment = attachment;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getProjWorkFlowID() {
		return projWorkFlowID;
	}

	public void setProjWorkFlowID(String projWorkFlowID) {
		this.projWorkFlowID = projWorkFlowID;
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

	public String getSelectedProjID() {
		return selectedProjID;
	}

	public void setSelectedProjID(String selectedProjID) {
		this.selectedProjID = selectedProjID;
	}

	public String getSelectedProjName() {
		return selectedProjName;
	}

	public void setSelectedProjName(String selectedProjName) {
		this.selectedProjName = selectedProjName;
	}

	public String getSelectedWorkFlowID() {
		return selectedWorkFlowID;
	}

	public void setSelectedWorkFlowID(String selectedWorkFlowID) {
		this.selectedWorkFlowID = selectedWorkFlowID;
	}


}
