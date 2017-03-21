package com.ibm.ioes.npd.beans;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

/**
 * Form bean for a Struts application.
 * 
 * @author Disha
 * @version 1.0
 */
public class LoginBean extends ValidatorForm {

	private static final long serialVersionUID = 8627758272118844378L;

	private String loginId = null;

	private String password = null;
	
	private ArrayList roleList = null;
	
	private String ssfUserID=null;

	@Override
	public ActionErrors validate(ActionMapping arg0, HttpServletRequest arg1) {
		// TODO Auto-generated method stub
		return new ActionErrors();
	}
	/**
	 * @return the roleList
	 */
	public ArrayList getRoleList() {
		return roleList;
	}

	/**
	 * @param roleList the roleList to set
	 */
	public void setRoleList(ArrayList roleList) {
		this.roleList = roleList;
	}

	/**
	 * Automatically generated constructor: LoginBean
	 */
	public LoginBean() {
	}

	/**
	 * @return Returns the loginId.
	 */
	public String getLoginId() {
		return loginId;
	}

	/**
	 * @return Returns the password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param loginId
	 *            The loginId to set.
	 */
	public void setLoginId(String loginId) {
		if (loginId != null) {
			this.loginId = loginId.trim();
		}
	}

	/**
	 * @param password
	 *            The password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public String getSsfUserID() {
		return ssfUserID;
	}

	public void setSsfUserID(String ssfUserID) {
		this.ssfUserID = ssfUserID;
	}

}