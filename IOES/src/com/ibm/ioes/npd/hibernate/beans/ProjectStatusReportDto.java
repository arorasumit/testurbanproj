package com.ibm.ioes.npd.hibernate.beans;
// Generated Dec 9, 2009 11:12:38 AM by Hibernate Tools 3.1.0.beta4

import java.util.Date;

import com.ibm.ioes.npd.utilities.PagingSorting;


/**
 * TmAppconfig generated by hbm2java
 */

public class ProjectStatusReportDto  implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private TtrnProjectassumptions projectassumption; 
	private TtrnProjectrisks projectrisk;
	private TtrnProjectisssues projectissues;
	private TtrnProjecthierarchy projecthierarchy; 
	
	private String projectName;
	private String npdCategory;
	private String productCategory;
	private String airtelPotential;
	private String totalMarketPotential;
	private String capexRequirement;	
	private int projectId;
	
	private String dateFilter=null;
	private String fromDate=null;
	private String toDate=null;
	PagingSorting pagingSorting=null;
	private String searchProjectPlanName;

	private String searchNpdCategory;
	private String searchProjectName;
	private int searchProjectid;
	private String searchProductCatId;
    private String idCollection;
    private String projectidlist;
    private String searchprojectid;         //For Project Id
    
    private int daysInProject;
	
	public int getDaysInProject() {
		return daysInProject;
	}
	public void setDaysInProject(int daysInProject) {
		this.daysInProject = daysInProject;
	}
	public String getProjectidlist() {
		return projectidlist;
	}
	public void setProjectidlist(String projectidlist) {
		this.projectidlist = projectidlist;
	}
	public String getIdCollection() {
		return idCollection;
	}
	public void setIdCollection(String idCollection) {
		this.idCollection = idCollection;
	}
	public String getDateFilter() {
		return dateFilter;
	}
	public void setDateFilter(String dateFilter) {
		this.dateFilter = dateFilter;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}
	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
	}
	public TtrnProjectassumptions getProjectassumption() {
		return projectassumption;
	}
	public void setProjectassumption(TtrnProjectassumptions projectassumption) {
		this.projectassumption = projectassumption;
	}
	public TtrnProjecthierarchy getProjecthierarchy() {
		return projecthierarchy;
	}
	public void setProjecthierarchy(TtrnProjecthierarchy projecthierarchy) {
		this.projecthierarchy = projecthierarchy;
	}
	public TtrnProjectisssues getProjectissues() {
		return projectissues;
	}
	public void setProjectissues(TtrnProjectisssues projectissues) {
		this.projectissues = projectissues;
	}
	public TtrnProjectrisks getProjectrisk() {
		return projectrisk;
	}
	public void setProjectrisk(TtrnProjectrisks projectrisk) {
		this.projectrisk = projectrisk;
	}
	public String getSearchProjectPlanName() {
		return searchProjectPlanName;
	}
	public void setSearchProjectPlanName(String searchProjectPlanName) {
		this.searchProjectPlanName = searchProjectPlanName;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getSearchNpdCategory() {
		return searchNpdCategory;
	}
	public void setSearchNpdCategory(String searchNpdCategory) {
		this.searchNpdCategory = searchNpdCategory;
	}
	public String getSearchProjectName() {
		return searchProjectName;
	}
	public void setSearchProjectName(String searchProjectName) {
		this.searchProjectName = searchProjectName;
	}
	public String getAirtelPotential() {
		return airtelPotential;
	}
	public void setAirtelPotential(String airtelPotential) {
		this.airtelPotential = airtelPotential;
	}
	public String getCapexRequirement() {
		return capexRequirement;
	}
	public void setCapexRequirement(String capexRequirement) {
		this.capexRequirement = capexRequirement;
	}
	public String getNpdCategory() {
		return npdCategory;
	}
	public void setNpdCategory(String npdCategory) {
		this.npdCategory = npdCategory;
	}
	public String getProductCategory() {
		return productCategory;
	}
	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getTotalMarketPotential() {
		return totalMarketPotential;
	}
	public void setTotalMarketPotential(String totalMarketPotential) {
		this.totalMarketPotential = totalMarketPotential;
	}
	public int getSearchProjectid() {
		return searchProjectid;
	}
	public void setSearchProjectid(int searchProjectid) {
		this.searchProjectid = searchProjectid;
	}
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public String getSearchProductCatId() {
		return searchProductCatId;
	}
	public void setSearchProductCatId(String searchProductCatId) {
		this.searchProductCatId = searchProductCatId;
	}
	public String getSearchprojectid() {
		return searchprojectid;
	}
	public void setSearchprojectid(String searchprojectid) {
		this.searchprojectid = searchprojectid;
	}	
}