/**
 * 
 */
package com.ibm.ioes.npd.beans;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

import com.ibm.ioes.npd.utilities.PagingSorting;

/**
 * @author Sanjay
 * 
 */
public class ProductCreationBean extends ActionForm {


	public static final String PRD_UPDATE_ENTRYDRAFT="entryDraft";

	public static final String PRD_UPDATE_VIEWDRAFT="viewDraft";
	
	public static final String PRD_UPDATE_ACTIONONDRAFT="actionOnDraft";
	
	public static final String PRD_ACTION_VIEW="viewAction";
	
	
	private static final long serialVersionUID = 7069912811901631712L;

	public static final String APPROVE_OVERRIDE = "approveOverride";

	public static final String REJECT = "reject";

	private int npdCategoryId;

	private ArrayList npdCategoryList;
	
	private String projectName;

	private int prdCategoryId;

	private ArrayList prdCategoryList;

	private String airtelPotential;

	private String capexRequirement;

	private String targetMarket;

	private String totalMktPotential;

	private String launchDate;

	private String prjStartDate;

	private String launchPriority;

	private String productBrief;

	private long projectId;

	private ArrayList projectList;

	private ArrayList projectDetailList;
	
	
	private String searchProjectId=null;
	private String searchProjectName=null;
	private String searchProductCategoryId=null;
	private String searchNpdCategoryId=null;
	private int searchProjectStatus=-1;
	
	private String searchPriority="All";
	
	//private String projectId=null;
	
	private PagingSorting pagingSorting;
	
	
	private ArrayList allClosedProjectListOrderById=null;
	private ArrayList allClosedProjectListOrderByName=null;
	//private ArrayList npdCategoryList=null;
	//private ArrayList prdCategoryList=null;
	private ArrayList filteredProjectList=null;
	
	private Long projectWorkflowId;

	private String createdBy=null;
	
	private String updateMode=null; // "entryDraft","viewDraft","actionOnDraft"
	
	private String updateActionType=null;
	
	private String updationRequestedBy=null;
	private String updationRequestedByName=null;
	private String updationRequestedByRoleId=null;
	private String updationRequestedByRoleName=null;
	private String updationRequestedByEmail=null;
	
	private String productId=null;
	
	public ProductCreationBean()
	{
		pagingSorting=new PagingSorting();
	}
	/**
	 * @return the launchPriority
	 */
	public String getLaunchPriority() {
		return launchPriority;
	}

	/**
	 * @param launchPriority
	 *            the launchPriority to set
	 */
	public void setLaunchPriority(String launchPriority) {
		this.launchPriority = launchPriority;
	}

	/**
	 * @return the productBrief
	 */
	public String getProductBrief() {
		return productBrief;
	}

	/**
	 * @param productBrief
	 *            the productBrief to set
	 */
	public void setProductBrief(String productBrief) {
		this.productBrief = productBrief;
	}

	/**
	 * @return the capexRequirement
	 */
	public String getCapexRequirement() {
		return capexRequirement;
	}

	/**
	 * @param capexRequirement
	 *            the capexRequirement to set
	 */
	public void setCapexRequirement(String capexRequirement) {
		this.capexRequirement = capexRequirement;
	}

	/**
	 * @return the airtelPotential
	 */
	public String getAirtelPotential() {
		return airtelPotential;
	}

	/**
	 * @param airtelPotential
	 *            the airtelPotential to set
	 */
	public void setAirtelPotential(String airtelPotential) {
		this.airtelPotential = airtelPotential;
	}

	/**
	 * @return the prdCategoryId
	 */
	public int getPrdCategoryId() {
		return prdCategoryId;
	}

	/**
	 * @param prdCategoryId
	 *            the prdCategoryId to set
	 */
	public void setPrdCategoryId(int prdCategoryId) {
		this.prdCategoryId = prdCategoryId;
	}

	/**
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * @param projectName
	 *            the projectName to set
	 */
	public void setProjectName(String projectName) {
		if (projectName != null && !projectName.equalsIgnoreCase("")) {
			this.projectName = projectName.trim();
		}

	}

	/**
	 * @return the npdCategoryId
	 */
	public int getNpdCategoryId() {
		return npdCategoryId;
	}

	/**
	 * @param npdCategoryId
	 *            the npdCategoryId to set
	 */
	public void setNpdCategoryId(int npdCategoryId) {
		this.npdCategoryId = npdCategoryId;
	}

	/**
	 * @return the targetMarket
	 */
	public String getTargetMarket() {
		return targetMarket;
	}

	/**
	 * @param targetMarket
	 *            the targetMarket to set
	 */
	public void setTargetMarket(String targetMarket) {
		this.targetMarket = targetMarket;
	}

	/**
	 * @return the totalMktPotential
	 */
	public String getTotalMktPotential() {
		return totalMktPotential;
	}

	/**
	 * @param totalMktPotential
	 *            the totalMktPotential to set
	 */
	public void setTotalMktPotential(String totalMktPotential) {
		this.totalMktPotential = totalMktPotential;
	}

	/**
	 * @return the launchDate
	 */
	public String getLaunchDate() {
		return launchDate;
	}

	/**
	 * @param launchDate
	 *            the launchDate to set
	 */
	public void setLaunchDate(String launchDate) {
		this.launchDate = launchDate;
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
	 * @return the prdCategoryList
	 */
	public ArrayList getPrdCategoryList() {
		return prdCategoryList;
	}

	/**
	 * @param prdCategoryList
	 *            the prdCategoryList to set
	 */
	public void setPrdCategoryList(ArrayList prdCategoryList) {
		this.prdCategoryList = prdCategoryList;
	}

	/**
	 * @return the prjStartDate
	 */
	public String getPrjStartDate() {
		return prjStartDate;
	}

	/**
	 * @param prjStartDate
	 *            the prjStartDate to set
	 */
	public void setPrjStartDate(String prjStartDate) {
		this.prjStartDate = prjStartDate;
	}

	/**
	 * @return the projectList
	 */
	public ArrayList getProjectList() {
		return projectList;
	}

	/**
	 * @param projectList
	 *            the projectList to set
	 */
	public void setProjectList(ArrayList projectList) {
		this.projectList = projectList;
	}

	/**
	 * @return the projectId
	 */
	public long getProjectId() {
		return projectId;
	}

	/**
	 * @param projectId
	 *            the projectId to set
	 */
	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	/**
	 * @return the projectDetailList
	 */
	public ArrayList getProjectDetailList() {
		return projectDetailList;
	}

	/**
	 * @param projectDetailList
	 *            the projectDetailList to set
	 */
	public void setProjectDetailList(ArrayList projectDetailList) {
		this.projectDetailList = projectDetailList;
	}

	public ArrayList getAllClosedProjectListOrderById() {
		return allClosedProjectListOrderById;
	}

	public void setAllClosedProjectListOrderById(
			ArrayList allClosedProjectListOrderById) {
		this.allClosedProjectListOrderById = allClosedProjectListOrderById;
	}

	public ArrayList getAllClosedProjectListOrderByName() {
		return allClosedProjectListOrderByName;
	}

	public void setAllClosedProjectListOrderByName(
			ArrayList allClosedProjectListOrderByName) {
		this.allClosedProjectListOrderByName = allClosedProjectListOrderByName;
	}

	public ArrayList getFilteredProjectList() {
		return filteredProjectList;
	}

	public void setFilteredProjectList(ArrayList filteredProjectList) {
		this.filteredProjectList = filteredProjectList;
	}

	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}

	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
	}

	public String getSearchNpdCategoryId() {
		return searchNpdCategoryId;
	}

	public void setSearchNpdCategoryId(String searchNpdCategoryId) {
		this.searchNpdCategoryId = searchNpdCategoryId;
	}

	public String getSearchProductCategoryId() {
		return searchProductCategoryId;
	}

	public void setSearchProductCategoryId(String searchProductCategoryId) {
		this.searchProductCategoryId = searchProductCategoryId;
	}

	public String getSearchProjectId() {
		return searchProjectId;
	}

	public void setSearchProjectId(String searchProjectId) {
		this.searchProjectId = searchProjectId;
	}

	public String getSearchProjectName() {
		return searchProjectName;
	}

	public void setSearchProjectName(String searchProjectNameId) {
		this.searchProjectName = searchProjectNameId;
	}
	public Long getProjectWorkflowId() {
		return projectWorkflowId;
	}
	public void setProjectWorkflowId(Long projectWorkflowId) {
		this.projectWorkflowId = projectWorkflowId;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getUpdateMode() {
		return updateMode;
	}
	public void setUpdateMode(String updateMode) {
		this.updateMode = updateMode;
	}
	public String getUpdateActionType() {
		return updateActionType;
	}
	public void setUpdateActionType(String updateActionType) {
		this.updateActionType = updateActionType;
	}
	public String getUpdationRequestedBy() {
		return updationRequestedBy;
	}
	public void setUpdationRequestedBy(String updationRequestedBy) {
		this.updationRequestedBy = updationRequestedBy;
	}
	public String getUpdationRequestedByName() {
		return updationRequestedByName;
	}
	public void setUpdationRequestedByName(String updationRequestedByName) {
		this.updationRequestedByName = updationRequestedByName;
	}
	public String getUpdationRequestedByRoleId() {
		return updationRequestedByRoleId;
	}
	public void setUpdationRequestedByRoleId(String updationRequestedByRoleId) {
		this.updationRequestedByRoleId = updationRequestedByRoleId;
	}
	public String getUpdationRequestedByRoleName() {
		return updationRequestedByRoleName;
	}
	public void setUpdationRequestedByRoleName(String updationRequestedByRoleName) {
		this.updationRequestedByRoleName = updationRequestedByRoleName;
	}
	public String getUpdationRequestedByEmail() {
		return updationRequestedByEmail;
	}
	public void setUpdationRequestedByEmail(String updationRequestedByEmail) {
		this.updationRequestedByEmail = updationRequestedByEmail;
	}
	public int getSearchProjectStatus() {
		return searchProjectStatus;
	}
	public void setSearchProjectStatus(int searchProjectStatus) {
		this.searchProjectStatus = searchProjectStatus;
	}
	public String getSearchPriority() {
		return searchPriority;
	}
	public void setSearchPriority(String searchPriority) {
		this.searchPriority = searchPriority;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
}
