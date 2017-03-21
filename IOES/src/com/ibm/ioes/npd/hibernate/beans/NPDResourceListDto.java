package com.ibm.ioes.npd.hibernate.beans;

import java.util.ArrayList;
import com.ibm.ioes.npd.utilities.PagingSorting;

public class NPDResourceListDto implements java.io.Serializable
{
	private ArrayList listNPDResourceList;
	
	private int projectID;
	
	private String projectName;
	
	private String roleID;
	
	private String roleName;
	
	private String empID;
	
	private String empName;
	
	private int searchProjectID;
	
	private String searchProjectName;
	
	private ArrayList listNPDResourceListExport;
	
	private int reportID;
	
	private String methodName;
	
	private int projectStatus;
	
	PagingSorting pagingSorting=null;

	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}

	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
	}

	public String getEmpID() {
		return empID;
	}

	public void setEmpID(String empID) {
		this.empID = empID;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public ArrayList getListNPDResourceList() {
		return listNPDResourceList;
	}

	public void setListNPDResourceList(ArrayList listNPDResourceList) {
		this.listNPDResourceList = listNPDResourceList;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getRoleID() {
		return roleID;
	}

	public void setRoleID(String roleID) {
		this.roleID = roleID;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getSearchProjectName() {
		return searchProjectName;
	}

	public void setSearchProjectName(String searchProjectName) {
		this.searchProjectName = searchProjectName;
	}

	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}

	public int getSearchProjectID() {
		return searchProjectID;
	}

	public void setSearchProjectID(int searchProjectID) {
		this.searchProjectID = searchProjectID;
	}

	public int getProjectID() {
		return projectID;
	}

	public ArrayList getListNPDResourceListExport() {
		return listNPDResourceListExport;
	}

	public void setListNPDResourceListExport(ArrayList listNPDResourceListExport) {
		this.listNPDResourceListExport = listNPDResourceListExport;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public int getReportID() {
		return reportID;
	}

	public void setReportID(int reportID) {
		this.reportID = reportID;
	}

	public int getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(int projectStatus) {
		this.projectStatus = projectStatus;
	}
}
