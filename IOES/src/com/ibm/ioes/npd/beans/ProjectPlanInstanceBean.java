package com.ibm.ioes.npd.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;

import com.ibm.ioes.npd.hibernate.beans.TtrnProject;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjecthierarchy;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjectworkflow;
import com.ibm.ioes.npd.utilities.PagingSorting;

public class ProjectPlanInstanceBean extends ActionForm {

	private String attachMode=null;// possible value : "attachNew" , "editingFinalized"
	
	private String projectId=null;
	private String projectWorkflowId=null;
	
	private String selectedTaskId=null;
	private String selectedStageId=null;
	
	private ArrayList stagesView=null;
	private ArrayList tasksView=null;
	
	private PagingSorting stagesPS=null;
	private PagingSorting tasksPS=null;
	
	
	private String searchProjectWorkflowId=null;
	private String searchStageName=null;
	private String searchStageDesc=null;
	private String searchStageId=null;
	
	private String searchTaskName=null;
	private String searchTaskId=null;
	private String searchTaskOfStageId=null;
	private String searchTask_roleHolder=null;
	private String searchTask_assignedTo=null;
	
	String [] prevPageSelectedTaskId;
	
	ArrayList<TtrnProjecthierarchy> stageList=null;
	TaskInstanceBean taskInstanceBean=null;
	
	private ArrayList<RoleEmployeeBean> roleView=null;
	
    
	TtrnProject project=null;
	
	TtrnProjecthierarchy firstTask=null;
	TtrnProjecthierarchy lastTask=null;
	
	ArrayList<TtrnProjecthierarchy> noPrevTaskList=null;
	ArrayList<TtrnProjecthierarchy> noStakeUserList=null;
	HashMap< Long, ArrayList<TtrnProjecthierarchy>> versionWorkflowsData=null;
	ArrayList<TtrnProjectworkflow> versionWorkflowList=null;
	
	String totalTasks;
	
	String []selectedRoleMapping=null;
	
	
	String taskOption=null;
	
	String[] messages=null;
	
	private ArrayList<TtrnProject> productList=null;
	
	String selectProductId=null;
	
	ArrayList<ArrayList<TtrnProjecthierarchy>> replacedToDoListTasks=new ArrayList<ArrayList<TtrnProjecthierarchy>>();
	ArrayList<ArrayList<TtrnProject>> replacedPendingRFITasks=new ArrayList<ArrayList<TtrnProject>>();
	
	private String deleteTaskId;
	
	private String noDeleteTaskId;
	
	String [] select_NewPreviousIds=null;
	
	HSSFWorkbook editExcelWorkbook = null;
	HSSFWorkbook templateWorkbook = null;
	HSSFWorkbook errorLogWorkbook = null;
	
	UploadProductPlanBean uploadProductPlanBean = null;
	
	String[] removeSelectedTaskId=null;
	
	public ProjectPlanInstanceBean()
	{
		stagesPS=new PagingSorting();
		tasksPS=new PagingSorting();
		taskInstanceBean=new TaskInstanceBean();
	}
	
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public PagingSorting getStagesPS() {
		return stagesPS;
	}

	public void setStagesPS(PagingSorting stagesPS) {
		this.stagesPS = stagesPS;
	}

	public PagingSorting getTasksPS() {
		return tasksPS;
	}

	public void setTasksPS(PagingSorting tasksPS) {
		this.tasksPS = tasksPS;
	}

	public ArrayList getStagesView() {
		return stagesView;
	}

	public void setStagesView(ArrayList stagesView) {
		this.stagesView = stagesView;
	}

	public ArrayList getTasksView() {
		return tasksView;
	}

	public void setTasksView(ArrayList tasksView) {
		this.tasksView = tasksView;
	}

	public String getSearchStageDesc() {
		return searchStageDesc;
	}

	public void setSearchStageDesc(String searchStageDesc) {
		this.searchStageDesc = searchStageDesc;
	}

	public String getSearchStageId() {
		return searchStageId;
	}

	public void setSearchStageId(String searchStageId) {
		this.searchStageId = searchStageId;
	}

	public String getSearchStageName() {
		return searchStageName;
	}

	public void setSearchStageName(String searchStageName) {
		this.searchStageName = searchStageName;
	}

	public String getSearchTaskId() {
		return searchTaskId;
	}

	public void setSearchTaskId(String searchTaskId) {
		this.searchTaskId = searchTaskId;
	}

	public String getSearchTaskName() {
		return searchTaskName;
	}

	public void setSearchTaskName(String searchTaskName) {
		this.searchTaskName = searchTaskName;
	}

	public String getSearchProjectWorkflowId() {
		return searchProjectWorkflowId;
	}

	public void setSearchProjectWorkflowId(String searchProjectWorkflowId) {
		this.searchProjectWorkflowId = searchProjectWorkflowId;
	}

	public String getSearchTaskOfStageId() {
		return searchTaskOfStageId;
	}

	public void setSearchTaskOfStageId(String searchTaskOfStageId) {
		this.searchTaskOfStageId = searchTaskOfStageId;
	}

	public String getSelectedStageId() {
		return selectedStageId;
	}

	public void setSelectedStageId(String selectedStageId) {
		this.selectedStageId = selectedStageId;
	}

	public String getSelectedTaskId() {
		return selectedTaskId;
	}

	public void setSelectedTaskId(String selectedTaskId) {
		this.selectedTaskId = selectedTaskId;
	}

	public String getProjectWorkflowId() {
		return projectWorkflowId;
	}

	public void setProjectWorkflowId(String projectWorkflowId) {
		this.projectWorkflowId = projectWorkflowId;
	}

	public TaskInstanceBean getTaskInstanceBean() {
		return taskInstanceBean;
	}

	public void setTaskInstanceBean(TaskInstanceBean taskInstanceBean) {
		this.taskInstanceBean = taskInstanceBean;
	}

	public ArrayList<TtrnProjecthierarchy> getStageList() {
		return stageList;
	}

	public void setStageList(ArrayList<TtrnProjecthierarchy> stageList) {
		this.stageList = stageList;
	}

	public String getSearchTask_assignedTo() {
		return searchTask_assignedTo;
	}

	public void setSearchTask_assignedTo(String searchTask_assignedTo) {
		this.searchTask_assignedTo = searchTask_assignedTo;
	}

	public String getSearchTask_roleHolder() {
		return searchTask_roleHolder;
	}

	public void setSearchTask_roleHolder(String searchTask_roleHolder) {
		this.searchTask_roleHolder = searchTask_roleHolder;
	}

	public TtrnProject getProject() {
		return project;
	}

	public void setProject(TtrnProject project) {
		this.project = project;
	}

	public TtrnProjecthierarchy getFirstTask() {
		return firstTask;
	}

	public void setFirstTask(TtrnProjecthierarchy firstTask) {
		this.firstTask = firstTask;
	}

	public TtrnProjecthierarchy getLastTask() {
		return lastTask;
	}

	public void setLastTask(TtrnProjecthierarchy lastTask) {
		this.lastTask = lastTask;
	}

	public ArrayList<TtrnProjecthierarchy> getNoPrevTaskList() {
		return noPrevTaskList;
	}

	public void setNoPrevTaskList(ArrayList<TtrnProjecthierarchy> noPrevTaskList) {
		this.noPrevTaskList = noPrevTaskList;
	}

	public String getTaskOption() {
		return taskOption;
	}

	public void setTaskOption(String taskOption) {
		this.taskOption = taskOption;
	}

	public String getAttachMode() {
		return attachMode;
	}

	public void setAttachMode(String attachMode) {
		this.attachMode = attachMode;
	}

	public ArrayList<TtrnProjecthierarchy> getNoStakeUserList() {
		return noStakeUserList;
	}

	public void setNoStakeUserList(ArrayList<TtrnProjecthierarchy> noStakeUserList) {
		this.noStakeUserList = noStakeUserList;
	}

	
	public ArrayList<TtrnProjectworkflow> getVersionWorkflowList() {
		return versionWorkflowList;
	}

	public void setVersionWorkflowList(
			ArrayList<TtrnProjectworkflow> versionWorkflowList) {
		this.versionWorkflowList = versionWorkflowList;
	}

	public HashMap<Long, ArrayList<TtrnProjecthierarchy>> getVersionWorkflowsData() {
		return versionWorkflowsData;
	}

	public void setVersionWorkflowsData(
			HashMap<Long, ArrayList<TtrnProjecthierarchy>> versionWorkflowsData) {
		this.versionWorkflowsData = versionWorkflowsData;
	}

	public String[] getMessages() {
		return messages;
	}

	public void setMessages(String[] messages) {
		this.messages = messages;
	}

	public String getTotalTasks() {
		return totalTasks;
	}

	public void setTotalTasks(String totalTasks) {
		this.totalTasks = totalTasks;
	}

	public ArrayList<RoleEmployeeBean> getRoleView() {
		return roleView;
	}

	public void setRoleView(ArrayList<RoleEmployeeBean> roleView) {
		this.roleView = roleView;
	}

	public String[] getSelectedRoleMapping() {
		return selectedRoleMapping;
	}

	public void setSelectedRoleMapping(String[] selectedRoleMapping) {
		this.selectedRoleMapping = selectedRoleMapping;
	}

	public ArrayList<TtrnProject> getProductList() {
		return productList;
	}

	public void setProductList(ArrayList<TtrnProject> productList) {
		this.productList = productList;
	}

	public String getSelectProductId() {
		return selectProductId;
	}

	public void setSelectProductId(String selectProductId) {
		this.selectProductId = selectProductId;
	}

	public ArrayList<ArrayList<TtrnProjecthierarchy>> getReplacedToDoListTasks() {
		return replacedToDoListTasks;
	}

	public void setReplacedToDoListTasks(
			ArrayList<ArrayList<TtrnProjecthierarchy>> replacedToDoListTasks) {
		this.replacedToDoListTasks = replacedToDoListTasks;
	}

	public ArrayList<ArrayList<TtrnProject>> getReplacedPendingRFITasks() {
		return replacedPendingRFITasks;
	}

	public void setReplacedPendingRFITasks(
			ArrayList<ArrayList<TtrnProject>> replacedPendingRFITasks) {
		this.replacedPendingRFITasks = replacedPendingRFITasks;
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

	public String[] getPrevPageSelectedTaskId() {
		return prevPageSelectedTaskId;
	}

	public void setPrevPageSelectedTaskId(String[] prevPageSelectedTaskId) {
		this.prevPageSelectedTaskId = prevPageSelectedTaskId;
	}

	public String[] getSelect_NewPreviousIds() {
		return select_NewPreviousIds;
	}

	public void setSelect_NewPreviousIds(String[] select_NewPreviousIds) {
		this.select_NewPreviousIds = select_NewPreviousIds;
	}

	public HSSFWorkbook getEditExcelWorkbook() {
		return editExcelWorkbook;
	}

	public void setEditExcelWorkbook(HSSFWorkbook editExcelWorkbook) {
		this.editExcelWorkbook = editExcelWorkbook;
	}

	public UploadProductPlanBean getUploadProductPlanBean() {
		if(uploadProductPlanBean==null)
			uploadProductPlanBean=new UploadProductPlanBean();
		return uploadProductPlanBean;
	}

	public void setUploadProductPlanBean(UploadProductPlanBean uploadProductPlanBean) {
		this.uploadProductPlanBean = uploadProductPlanBean;
	}

	public HSSFWorkbook getTemplateWorkbook() {
		return templateWorkbook;
	}

	public void setTemplateWorkbook(HSSFWorkbook templateWorkbook) {
		this.templateWorkbook = templateWorkbook;
	}

	public HSSFWorkbook getErrorLogWorkbook() {
		return errorLogWorkbook;
	}

	public void setErrorLogWorkbook(HSSFWorkbook errorLogWorkbook) {
		this.errorLogWorkbook = errorLogWorkbook;
	}

	public String[] getRemoveSelectedTaskId() {
		return removeSelectedTaskId;
	}

	public void setRemoveSelectedTaskId(String[] removeSelectedTaskId) {
		this.removeSelectedTaskId = removeSelectedTaskId;
	}
	


	



	
}
