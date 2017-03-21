package com.ibm.ioes.npd.beans;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

import com.ibm.ioes.npd.utilities.PagingSorting;

public class UnMappedEmployeeBean  extends ActionForm
{
	String employeeId;
	
	String employeeName;
	
	ArrayList empList=null;
	
	private int checkRptData;
	
	private int reportID;
	
	private String methodName;
	
	private PagingSorting pagingSorting = null;
	
	public UnMappedEmployeeBean() {
		pagingSorting = new PagingSorting();
	}

	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}

	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
	}

	public ArrayList getEmpList() {
		return empList;
	}

	public void setEmpList(ArrayList empList) {
		this.empList = empList;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
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

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}


}
