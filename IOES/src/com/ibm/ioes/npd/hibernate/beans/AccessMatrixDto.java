package com.ibm.ioes.npd.hibernate.beans;

public class AccessMatrixDto implements java.io.Serializable
{
	private int roleID;
	
	private String roleName;
	
	private int interfaceID;
	
	private String interfaceName;
	
	private int accessFlag;
	
	private int modID;
	
	private String moduleName;

	public int getRoleID() {
		return roleID;
	}

	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public int getAccessFlag() {
		return accessFlag;
	}

	public void setAccessFlag(int accessFlag) {
		this.accessFlag = accessFlag;
	}

	public int getInterfaceID() {
		return interfaceID;
	}

	public void setInterfaceID(int interfaceID) {
		this.interfaceID = interfaceID;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public int getModID() {
		return modID;
	}

	public void setModID(int modID) {
		this.modID = modID;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
}
