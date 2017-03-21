package com.ibm.ioes.npd.beans;

import java.util.ArrayList;
import org.apache.struts.validator.ValidatorForm;

import com.ibm.ioes.npd.utilities.PagingSorting;

public class RoleSelectionBean extends ValidatorForm
{	
	private ArrayList roleList;
	
	private String roleID;
	
	private String roleName;
	
	private String roleFilter;
	
	private ArrayList menuList;
	
	private PagingSorting pagingSorting=null;

	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}

	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
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

	public ArrayList getRoleList() {
		return roleList;
	}

	public void setRoleList(ArrayList roleList) {
		this.roleList = roleList;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public ArrayList getMenuList() {
		return menuList;
	}

	public void setMenuList(ArrayList menuList) {
		this.menuList = menuList;
	}
}
