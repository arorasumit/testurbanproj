package com.ibm.ioes.npd.hibernate.beans;

import java.util.ArrayList;

import com.ibm.ioes.npd.utilities.PagingSorting;

public class FunnelReportDto implements java.io.Serializable
{
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
	
	private String targetMarket;
	
	private String searchStageName;
	
	private String dateFilter=null;
	
	private String fromDate=null;
	
	private String toDate=null;
	
	private int reportID;
	
	private int projectID;
	
	private String workflowid;
	
	private int projectStatus;
	
	PagingSorting pagingSorting=null;
	
	private String searchlaunchstatus;
	
	private int daysInProject;
	
	private ArrayList<TmWorkflowstage> projectstageList;
	
	private ArrayList<TmWorkflowstage> allstages;

	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}

	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
	}

	public String getTargetMarket() {
		return targetMarket;
	}

	public void setTargetMarket(String targetMarket) {
		this.targetMarket = targetMarket;
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

	public String getSearchStageName() {
		return searchStageName;
	}

	public void setSearchStageName(String searchStageName) {
		this.searchStageName = searchStageName;
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

	public int getReportID() {
		return reportID;
	}

	public void setReportID(int reportID) {
		this.reportID = reportID;
	}

	public int getProjectID() {
		return projectID;
	}

	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}

	public ArrayList<TmWorkflowstage> getProjectstageList() {
		return projectstageList;
	}

	public void setProjectstageList(ArrayList<TmWorkflowstage> projectstageList) {
		this.projectstageList = projectstageList;
	}

	public String getWorkflowid() {
		return workflowid;
	}

	public void setWorkflowid(String workflowid) {
		this.workflowid = workflowid;
	}

	public ArrayList<TmWorkflowstage> getAllstages() {
		return allstages;
	}

	public void setAllstages(ArrayList<TmWorkflowstage> allstages) {
		this.allstages = allstages;
	}

	public int getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(int projectStatus) {
		this.projectStatus = projectStatus;
	}

	public String getSearchlaunchstatus() {
		return searchlaunchstatus;
	}

	public void setSearchlaunchstatus(String searchlaunchstatus) {
		this.searchlaunchstatus = searchlaunchstatus;
	}

	public int getDaysInProject() {
		return daysInProject;
	}

	public void setDaysInProject(int daysInProject) {
		this.daysInProject = daysInProject;
	}
}
