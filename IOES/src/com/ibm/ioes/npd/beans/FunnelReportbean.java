package com.ibm.ioes.npd.beans;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

import com.ibm.ioes.npd.utilities.PagingSorting;

public class FunnelReportbean extends ActionForm {
	private ArrayList listFunnelReport;

	private String projectName;

	private String launchPriority;

	private String productBrief;

	private String productMarket;

	private String productCategory;

	private String npdCategory;

	private String airtelPotential;

	private String TotalMktPotential;

	private String capexRequirement;

	private String productMgr;

	private String techLead;

	private String startMonth;

	private String baselinelaunchmonth;

	private String rvsdLaunchDate;

	private String launchqtr;

	private String launchStatus;

	private String ideaStage;

	private String designStage;

	private String dvptStage;

	private String launchstage;

	private String launched;

	private String currentStage;

	private String cuurentStatusRemarks;

	private String projectID;

	private PagingSorting pagingSorting = null;

	private String dateFilter = null;

	private String fromDate = null;

	private String toDate = null;

	private int checkRptData;

	private int reportID;

	private String searchProjectID;

	private int searchProjectStatus=-1;
	
	
	private int daysInProject;
	
	private String searchlaunchstatus;

	public String getSearchlaunchstatus() {
		return searchlaunchstatus;
	}

	public void setSearchlaunchstatus(String searchlaunchstatus) {
		this.searchlaunchstatus = searchlaunchstatus;
	}

	public int getSearchProjectStatus() {
		return searchProjectStatus;
	}

	public void setSearchProjectStatus(int searchProjectStatus) {
		this.searchProjectStatus = searchProjectStatus;
	}

	public String getSearchProjectID() {
		return searchProjectID;
	}

	public void setSearchProjectID(String searchProjectID) {
		if (searchProjectID != null) {
			this.searchProjectID = searchProjectID.trim();
		}
	}

	public int getReportID() {
		return reportID;
	}

	public void setReportID(int reportID) {
		this.reportID = reportID;
	}

	public int getCheckRptData() {
		return checkRptData;
	}

	public void setCheckRptData(int checkRptData) {
		this.checkRptData = checkRptData;
	}

	public FunnelReportbean() {
		pagingSorting = new PagingSorting();
	}

	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}

	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
	}

	public String getAirtelPotential() {
		return airtelPotential;
	}

	public void setAirtelPotential(String airtelPotential) {
		this.airtelPotential = airtelPotential;
	}

	public String getBaselinelaunchmonth() {
		return baselinelaunchmonth;
	}

	public void setBaselinelaunchmonth(String baselinelaunchmonth) {
		this.baselinelaunchmonth = baselinelaunchmonth;
	}

	public String getCapexRequirement() {
		return capexRequirement;
	}

	public void setCapexRequirement(String capexRequirement) {
		this.capexRequirement = capexRequirement;
	}

	public String getCurrentStage() {
		return currentStage;
	}

	public void setCurrentStage(String currentStage) {
		this.currentStage = currentStage;
	}

	public String getCuurentStatusRemarks() {
		return cuurentStatusRemarks;
	}

	public void setCuurentStatusRemarks(String cuurentStatusRemarks) {
		this.cuurentStatusRemarks = cuurentStatusRemarks;
	}

	public String getDesignStage() {
		return designStage;
	}

	public void setDesignStage(String designStage) {
		this.designStage = designStage;
	}

	public String getDvptStage() {
		return dvptStage;
	}

	public void setDvptStage(String dvptStage) {
		this.dvptStage = dvptStage;
	}

	public String getIdeaStage() {
		return ideaStage;
	}

	public void setIdeaStage(String ideaStage) {
		this.ideaStage = ideaStage;
	}

	public String getLaunched() {
		return launched;
	}

	public void setLaunched(String launched) {
		this.launched = launched;
	}

	public String getLaunchPriority() {
		return launchPriority;
	}

	public void setLaunchPriority(String launchPriority) {
		this.launchPriority = launchPriority;
	}

	public String getLaunchqtr() {
		return launchqtr;
	}

	public void setLaunchqtr(String launchqtr) {
		this.launchqtr = launchqtr;
	}

	public String getLaunchstage() {
		return launchstage;
	}

	public void setLaunchstage(String launchstage) {
		this.launchstage = launchstage;
	}

	public String getLaunchStatus() {
		return launchStatus;
	}

	public void setLaunchStatus(String launchStatus) {
		this.launchStatus = launchStatus;
	}

	public String getNpdCategory() {
		return npdCategory;
	}

	public void setNpdCategory(String npdCategory) {
		this.npdCategory = npdCategory;
	}

	public String getProductBrief() {
		return productBrief;
	}

	public void setProductBrief(String productBrief) {
		this.productBrief = productBrief;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public String getProductMarket() {
		return productMarket;
	}

	public void setProductMarket(String productMarket) {
		this.productMarket = productMarket;
	}

	public String getProductMgr() {
		return productMgr;
	}

	public void setProductMgr(String productMgr) {
		this.productMgr = productMgr;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getRvsdLaunchDate() {
		return rvsdLaunchDate;
	}

	public void setRvsdLaunchDate(String rvsdLaunchDate) {
		this.rvsdLaunchDate = rvsdLaunchDate;
	}

	public String getStartMonth() {
		return startMonth;
	}

	public void setStartMonth(String startMonth) {
		this.startMonth = startMonth;
	}

	public String getTechLead() {
		return techLead;
	}

	public void setTechLead(String techLead) {
		this.techLead = techLead;
	}

	public String getTotalMktPotential() {
		return TotalMktPotential;
	}

	public void setTotalMktPotential(String totalMktPotential) {
		TotalMktPotential = totalMktPotential;
	}

	public ArrayList getListFunnelReport() {
		return listFunnelReport;
	}

	public void setListFunnelReport(ArrayList listFunnelReport) {
		this.listFunnelReport = listFunnelReport;
	}

	public String getProjectID() {
		return projectID;
	}

	public void setProjectID(String projectID) {
		if (projectID != null) {
			this.projectID = projectID.trim();
		}
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

	public int getDaysInProject() {
		return daysInProject;
	}

	public void setDaysInProject(int daysInProject) {
		this.daysInProject = daysInProject;
	}
}
