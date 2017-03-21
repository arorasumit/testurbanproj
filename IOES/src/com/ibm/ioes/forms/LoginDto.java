package com.ibm.ioes.forms;

import java.util.ArrayList;


public class LoginDto {

	String moduleId=null;
	String roleId=null;
	String interfaceName=null;
	String moduleName=null;
	String interfaceId=null;
	String url=null;
	ArrayList<LoginDto> listInterfaceDetails= new ArrayList<LoginDto>();
	ArrayList<LoginDto> listModuleDetails= new ArrayList<LoginDto>();
	
	public ArrayList<LoginDto> getListInterfaceDetails() {
		return listInterfaceDetails;
	}
	public void setListInterfaceDetails(ArrayList<LoginDto> listInterfaceDetails) {
		this.listInterfaceDetails = listInterfaceDetails;
	}
	public ArrayList<LoginDto> getListModuleDetails() {
		return listModuleDetails;
	}
	public void setListModuleDetails(ArrayList<LoginDto> listModuleDetails) {
		this.listModuleDetails = listModuleDetails;
	}
	public String getInterfaceId() {
		return interfaceId;
	}
	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}
	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
}
