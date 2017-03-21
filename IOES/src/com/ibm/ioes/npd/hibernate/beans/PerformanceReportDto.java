package com.ibm.ioes.npd.hibernate.beans;

import com.ibm.ioes.npd.utilities.PagingSorting;

public class PerformanceReportDto implements java.io.Serializable
{
	private String searchProductManager;
	
	private String searchProjectName;
	
	private int projectID;
	
	private String projectName;
	
	private String dateFilter=null;
	
	private String fromDate=null;
	
	private String toDate=null;
	
	private String productManager;
	
	PagingSorting pagingSorting=null;
	
	private String plndLaunchDate;
	
	private String actLaunchDate;
	
	private int deviation;
	
	private int projectStatus;
	
	private String searchFilter;

	private int daysInProject;
	
	public int getDaysInProject() {
		return daysInProject;
	}

	public void setDaysInProject(int daysInProject) {
		this.daysInProject = daysInProject;
	}

	public String getSearchFilter() {
		return searchFilter;
	}

	public void setSearchFilter(String searchFilter) {
		this.searchFilter = searchFilter;
	}

	public int getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(int projectStatus) {
		this.projectStatus = projectStatus;
	}

	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}

	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
	}

	public int getProjectID() {
		return projectID;
	}

	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getSearchProductManager() {
		return searchProductManager;
	}

	public void setSearchProductManager(String searchProductManager) {
		this.searchProductManager = searchProductManager;
	}

	public String getSearchProjectName() {
		return searchProjectName;
	}

	public void setSearchProjectName(String searchProjectName) {
		this.searchProjectName = searchProjectName;
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

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getProductManager() {
		return productManager;
	}

	public void setProductManager(String productManager) {
		this.productManager = productManager;
	}

	public String getActLaunchDate() {
		return actLaunchDate;
	}

	public void setActLaunchDate(String actLaunchDate) {
		this.actLaunchDate = actLaunchDate;
	}

	public int getDeviation() {
		return deviation;
	}

	public void setDeviation(int deviation) {
		this.deviation = deviation;
	}

	public String getPlndLaunchDate() {
		return plndLaunchDate;
	}

	public void setPlndLaunchDate(String plndLaunchDate) {
		this.plndLaunchDate = plndLaunchDate;
	}
}
