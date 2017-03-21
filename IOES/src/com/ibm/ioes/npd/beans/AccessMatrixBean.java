package com.ibm.ioes.npd.beans;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

public class AccessMatrixBean extends ActionForm
{
	private String roleFilter;
	
	private ArrayList listRole;
	
	private String roleID;
	
	private String roleName;
	
	private ArrayList listInterface;
	
	private String interfaceID;
	
	private String interfaceName;
	
	private String accessFlag;

	private String methodName;
	
	private String allowedInterfaceId;
	
	private String prohibitedInterfaceId;
	
	private String selectedRoleID;
	
	private ArrayList moduleList;
	
	private String moduleFilter;
	
	private String selectedModuleID;
		
	public String getModuleFilter() {
		return moduleFilter;
	}

	public void setModuleFilter(String moduleFilter) {
		this.moduleFilter = moduleFilter;
	}

	public ArrayList getModuleList() {
		return moduleList;
	}

	public void setModuleList(ArrayList moduleList) {
		this.moduleList = moduleList;
	}

	public String getSelectedRoleID() {
		return selectedRoleID;
	}

	public void setSelectedRoleID(String selectedRoleID) {
		this.selectedRoleID = selectedRoleID;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public ArrayList getListRole() {
		return listRole;
	}

	public void setListRole(ArrayList listRole) {
		this.listRole = listRole;
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

	public ArrayList getListInterface() {
		return listInterface;
	}

	public void setListInterface(ArrayList listInterface) {
		this.listInterface = listInterface;
	}

	public String getAccessFlag() {
		return accessFlag;
	}

	public void setAccessFlag(String accessFlag) {
		this.accessFlag = accessFlag;
	}

	public String getInterfaceID() {
		return interfaceID;
	}

	public void setInterfaceID(String interfaceID) {
		this.interfaceID = interfaceID;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getRoleFilter() {
		return roleFilter;
	}

	public void setRoleFilter(String roleFilter) {
		this.roleFilter = roleFilter;
	}

	public String getAllowedInterfaceId() {
		return allowedInterfaceId;
	}

	public void setAllowedInterfaceId(String allowedInterfaceId) {
		this.allowedInterfaceId = allowedInterfaceId;
	}

	public String getProhibitedInterfaceId() {
		return prohibitedInterfaceId;
	}

	public void setProhibitedInterfaceId(String prohibitedInterfaceId) {
		this.prohibitedInterfaceId = prohibitedInterfaceId;
	}

	public String getSelectedModuleID() {
		return selectedModuleID;
	}

	public void setSelectedModuleID(String selectedModuleID) {
		this.selectedModuleID = selectedModuleID;
	}
}
