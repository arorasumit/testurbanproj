package com.ibm.ioes.npd.beans;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

import com.ibm.ioes.npd.utilities.PagingSorting;

public class PerformanceReportBean extends ActionForm {
	private String productManager;

	private String projectID;

	private String projectName;

	private PagingSorting pagingSorting = null;

	private String dateFilter = null;

	private String fromDate = null;

	private String toDate = null;

	private int checkRptData;

	private int reportID;

	private String plndLaunchDate;

	private String actLaunchDate;

	private int deviation;

	private String searchProjectID;

	private String searchDeviation;

	private ArrayList listPerformanceReport;

	private ArrayList listProjectdetailed;

	private int searchProjectStatus=-1;
	
	private String searchFilter;
	
	private int daysInProject;

	public int getDaysInProject() {
		return daysInProject;
	}

	public void setDaysInProject(int daysInProject) {
		this.daysInProject = daysInProject;
	}

	public int getSearchProjectStatus() {
		return searchProjectStatus;
	}

	public void setSearchProjectStatus(int searchProjectStatus) {
		this.searchProjectStatus = searchProjectStatus;
	}

	public PerformanceReportBean() {
		pagingSorting = new PagingSorting();
	}

	public String getActLaunchDate() {
		return actLaunchDate;
	}

	public void setActLaunchDate(String actLaunchDate) {
		this.actLaunchDate = actLaunchDate;
	}

	public int getCheckRptData() {
		return checkRptData;
	}

	public void setCheckRptData(int checkRptData) {
		this.checkRptData = checkRptData;
	}

	public String getDateFilter() {
		return dateFilter;
	}

	public void setDateFilter(String dateFilter) {
		this.dateFilter = dateFilter;
	}

	public int getDeviation() {
		return deviation;
	}

	public void setDeviation(int deviation) {
		this.deviation = deviation;
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

	public String getPlndLaunchDate() {
		return plndLaunchDate;
	}

	public void setPlndLaunchDate(String plndLaunchDate) {
		this.plndLaunchDate = plndLaunchDate;
	}

	public String getProductManager() {
		return productManager;
	}

	public void setProductManager(String productManager) {
		this.productManager = productManager;
	}

	public String getProjectID() {
		return projectID;
	}

	public void setProjectID(String projectID) {
		if (projectID != null) {
			this.projectID = projectID.trim();
		}
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public int getReportID() {
		return reportID;
	}

	public void setReportID(int reportID) {
		this.reportID = reportID;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public ArrayList getListPerformanceReport() {
		return listPerformanceReport;
	}

	public void setListPerformanceReport(ArrayList listPerformanceReport) {
		this.listPerformanceReport = listPerformanceReport;
	}

	public String getSearchProjectID() {
		return searchProjectID;
	}

	public void setSearchProjectID(String searchProjectID) {
		if (searchProjectID != null) {
			this.searchProjectID = searchProjectID.trim();
		}
	}

	public String getSearchDeviation() {
		return searchDeviation;
	}

	public void setSearchDeviation(String searchDeviation) {
		if (searchDeviation != null) {
			this.searchDeviation = searchDeviation.trim();
		}
	}

	public ArrayList getListProjectdetailed() {
		return listProjectdetailed;
	}

	public void setListProjectdetailed(ArrayList listProjectdetailed) {
		this.listProjectdetailed = listProjectdetailed;
	}

	public String getSearchFilter() {
		return searchFilter;
	}

	public void setSearchFilter(String searchFilter) {
		this.searchFilter = searchFilter;
	}

}
