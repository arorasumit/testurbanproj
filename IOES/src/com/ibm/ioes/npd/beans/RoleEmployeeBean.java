package com.ibm.ioes.npd.beans;

import java.util.ArrayList;

import com.ibm.ioes.npd.hibernate.beans.TmEmployee;

public class RoleEmployeeBean {

	
	String roleId=null;
	String employeeId=null;
	String roleName=null;
	
	ArrayList<TmEmployee> roleUserList=null;

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public ArrayList<TmEmployee> getRoleUserList() {
		return roleUserList;
	}

	public void setRoleUserList(ArrayList<TmEmployee> roleUserList) {
		this.roleUserList = roleUserList;
	}
}
