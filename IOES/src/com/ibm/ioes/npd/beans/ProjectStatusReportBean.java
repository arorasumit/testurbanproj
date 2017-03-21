package com.ibm.ioes.npd.beans;
// Generated Dec 9, 2009 11:12:38 AM by Hibernate Tools 3.1.0.beta4

import java.util.Date;

import org.apache.struts.action.ActionForm;

import com.ibm.ioes.npd.hibernate.beans.TtrnProject;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjectassumptions;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjecthierarchy;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjectisssues;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjectrisks;
import com.ibm.ioes.npd.utilities.PagingSorting;


/**
 * TmAppconfig generated by hbm2java
 */

public class ProjectStatusReportBean extends ActionForm  implements java.io.Serializable {

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

	
	private String dateFilter=null;
	private String fromDate=null;
	private String toDate=null;
	PagingSorting pagingSorting=null;
	private String searchProjectPlanName;
	private String searchNpdCategory;
	private String searchProjectName;
	private String searchProjectid;
	private String searchProductCatId;
	private String idCollection;
	private String searchprojectid;
	
	private int daysInProject;
	private int checkRptData;
	
	private String startDate;
	private String endDate;
	
	private String searchStagename;
	
	//rohit verma
	private String file_Name_Chart;
	
	public String getFile_Name_Chart() {
		return file_Name_Chart;
	}



	public void setFile_Name_Chart(String file_Name_Chart) {
		this.file_Name_Chart = file_Name_Chart;
	}



	public int getCheckRptData() {
		return checkRptData;
	}



	public void setCheckRptData(int checkRptData) {
		this.checkRptData = checkRptData;
	}



	public int getDaysInProject() {
		return daysInProject;
	}



	public void setDaysInProject(int daysInProject) {
		this.daysInProject = daysInProject;
	}



	public String getSearchprojectid() {
		return searchprojectid;
	}



	public void setSearchprojectid(String searchprojectid) {
		this.searchprojectid = searchprojectid;
	}



	public String getIdCollection() {
		return idCollection;
	}



	public void setIdCollection(String idCollection) {
		this.idCollection = idCollection;
	}



	public ProjectStatusReportBean()
	{
		//System.err.println("in constructor");
		pagingSorting=new PagingSorting();
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



	public String getSearchProjectid() {
		return searchProjectid;
	}



	public void setSearchProjectid(String searchProjectid) {
		this.searchProjectid = searchProjectid;
	}



	public String getSearchProductCatId() {
		return searchProductCatId;
	}



	public void setSearchProductCatId(String searchProductCatId) {
		this.searchProductCatId = searchProductCatId;
	}



	public String getEndDate() {
		return endDate;
	}



	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}



	public String getStartDate() {
		return startDate;
	}



	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}



	public String getSearchStagename() {
		return searchStagename;
	}



	public void setSearchStagename(String searchStagename) {
		this.searchStagename = searchStagename;
	}
	
}