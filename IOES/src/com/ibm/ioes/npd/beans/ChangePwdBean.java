package com.ibm.ioes.npd.beans;

import org.apache.struts.action.ActionForm;


public class ChangePwdBean extends ActionForm 
{
	private String loginId=null;

	private String confirmpassword = null;

	private String newpassword = null;

	private String oldpassword = null;
	
	private String email = null;
	
	private int emailConfirmation;

	public int getEmailConfirmation() {
		return emailConfirmation;
	}

	public void setEmailConfirmation(int emailConfirmation) {
		this.emailConfirmation = emailConfirmation;
	}

	public String getConfirmpassword() {
		return confirmpassword;
	}

	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getNewpassword() {
		return newpassword;
	}

	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}

	public String getOldpassword() {
		return oldpassword;
	}

	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
