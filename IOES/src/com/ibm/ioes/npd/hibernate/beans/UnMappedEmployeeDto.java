package com.ibm.ioes.npd.hibernate.beans;

import java.util.ArrayList;

import com.ibm.ioes.npd.utilities.PagingSorting;

public class UnMappedEmployeeDto implements java.io.Serializable
{
	int employeeId;
	
	String employeeName=null;
	
	PagingSorting pagingSorting=null;
	
	ArrayList empList=null;

	public ArrayList getEmpList() {
		return empList;
	}

	public void setEmpList(ArrayList empList) {
		this.empList = empList;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}

	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
	}
}
