/*
 * Created on Nov 9, 2010
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.ioes.ecrm;

import java.sql.Timestamp;

/**
 * @author Ravish
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ECRMRoleDto 
{
	String roleId;
	String roleName;
	String roleCode;
	String roleDesc;
	String isActive;
	String createdBy;
	private Timestamp createdDate;
	String modifiedBy;
	private Timestamp modifiedDate;
	
	
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}



	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	


	

	/**
	 * @return
	 */
	public String getRoleCode() {
		return roleCode;
	}

	/**
	 * @return
	 */
	public String getRoleDesc() {
		return roleDesc;
	}

	/**
	 * @return
	 */
	public String getRoleId() {
		return roleId;
	}

	/**
	 * @return
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param string
	 */
	public void setRoleCode(String string) {
		roleCode = string;
	}

	/**
	 * @param string
	 */
	public void setRoleDesc(String string) {
		roleDesc = string;
	}

	/**
	 * @param string
	 */
	public void setRoleId(String string) {
		roleId = string;
	}

	/**
	 * @param string
	 */
	public void setRoleName(String string) {
		roleName = string;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

}
