package com.ibm.ioes.npd.beans;

import org.apache.struts.action.ActionForm;

import com.ibm.ioes.npd.utilities.PagingSorting;

public class NPDResourceListBean extends ActionForm {
	private String projectName;

	private String projectID;

	private String roleName;

	private String empName;

	private String searchProjectID;

	private String methodName;

	private int checkRptData;

	private int reportID;

	private int searchProjectStatus=-1;

	private PagingSorting pagingSorting = null;

	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}

	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
	}

	public NPDResourceListBean() {
		pagingSorting = new PagingSorting();
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getProjectID() {
		return projectID;
	}

	public void setProjectID(String projectID) {
		if (projectID != null) {
			this.projectID = projectID.trim();
		}
	}

	public String getSearchProjectID() {
		return searchProjectID;
	}

	public void setSearchProjectID(String searchProjectID) {
		if (searchProjectID != null) {
			this.searchProjectID = searchProjectID.trim();
		}
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public int getCheckRptData() {
		return checkRptData;
	}

	public void setCheckRptData(int checkRptData) {
		this.checkRptData = checkRptData;
	}

	public int getReportID() {
		return reportID;
	}

	public void setReportID(int reportID) {
		this.reportID = reportID;
	}

	public int getSearchProjectStatus() {
		return searchProjectStatus;
	}

	public void setSearchProjectStatus(int searchProjectStatus) {
		this.searchProjectStatus = searchProjectStatus;
	}

}
