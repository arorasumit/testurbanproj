package com.ibm.ioes.beans;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

public class RoleAcessMatrixBean extends ActionForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6347569292183052275L;
	private String roleFilter;
	
	private ArrayList listRoles = new ArrayList();
	
	private String roleID;
	
	private String roleName;
	
	private ArrayList listInterface = new ArrayList();
	
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

	public String getAccessFlag() {
		return accessFlag;
	}

	public void setAccessFlag(String accessFlag) {
		this.accessFlag = accessFlag;
	}

	public String getAllowedInterfaceId() {
		return allowedInterfaceId;
	}

	public void setAllowedInterfaceId(String allowedInterfaceId) {
		this.allowedInterfaceId = allowedInterfaceId;
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

	public ArrayList getListInterface() {
		return listInterface;
	}

	public void setListInterface(ArrayList listInterface) {
		this.listInterface = listInterface;
	}

	public ArrayList getListRoles() {
		return listRoles;
	}

	public void setListRoles(ArrayList listRole) {
		this.listRoles = listRole;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

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

	public String getProhibitedInterfaceId() {
		return prohibitedInterfaceId;
	}

	public void setProhibitedInterfaceId(String prohibitedInterfaceId) {
		this.prohibitedInterfaceId = prohibitedInterfaceId;
	}

	public String getRoleFilter() {
		return roleFilter;
	}

	public void setRoleFilter(String roleFilter) {
		this.roleFilter = roleFilter;
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

	public String getSelectedModuleID() {
		return selectedModuleID;
	}

	public void setSelectedModuleID(String selectedModuleID) {
		this.selectedModuleID = selectedModuleID;
	}

	public String getSelectedRoleID() {
		return selectedRoleID;
	}

	public void setSelectedRoleID(String selectedRoleID) {
		this.selectedRoleID = selectedRoleID;
	}
	
}
