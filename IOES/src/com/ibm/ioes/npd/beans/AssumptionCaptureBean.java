package com.ibm.ioes.npd.beans;

import java.util.ArrayList;
import java.util.Date;

import org.apache.struts.action.ActionForm;

import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.PagingSorting;

public class AssumptionCaptureBean extends ActionForm {
	private String projectId;

	private String assumptionID;

	private String raisedon;

	private String description;

	private ArrayList projectList;

	private String impact;

	private ArrayList assumptionDetailList;

	private String fromDate;

	private String toDate;

	private String projectName;

	private PagingSorting pagingSorting;
	
	private Long[] projectidlist;

	private ArrayList searchProjectList;
	
	public Long[] getProjectidlist() {
		return projectidlist;
	}

	public void setProjectidlist(Long[] projectidlist) {
		this.projectidlist = projectidlist;
	}

	public AssumptionCaptureBean() {
		pagingSorting = new PagingSorting();
	}

	public String getImpact() {
		return impact;
	}

	public void setImpact(String impact) {
		this.impact = impact;
	}

	public ArrayList getProjectList() {
		return projectList;
	}

	public void setProjectList(ArrayList projectList) {
		this.projectList = projectList;
	}

	public String getAssumptionID() {
		return assumptionID;
	}

	public void setAssumptionID(String assumptionID) {
		this.assumptionID = assumptionID;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		if (projectId != null) {
			this.projectId = projectId.trim();
		}
	}

	public String getRaisedon() {
		return raisedon;
	}

	public void setRaisedon(String raisedon) {
		this.raisedon = raisedon;
	}

	public ArrayList getAssumptionDetailList() {
		return assumptionDetailList;
	}

	public void setAssumptionDetailList(ArrayList assumptionDetailList) {
		this.assumptionDetailList = assumptionDetailList;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}

	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		if (projectName != null) {
			this.projectName = projectName.trim();
		}
	}

	public ArrayList getSearchProjectList() {
		return searchProjectList;
	}

	public void setSearchProjectList(ArrayList searchProjectList) {
		this.searchProjectList = searchProjectList;
	}

}
