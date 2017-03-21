package com.ibm.ioes.npd.beans;

import org.apache.struts.action.ActionForm;

public class UpdateMobileNumberBean extends ActionForm 
{
	private String userMobileNumber = null;

	public String getUserMobileNumber() {
		return userMobileNumber;
	}

	public void setUserMobileNumber(String userMobileNumber) {
		this.userMobileNumber = userMobileNumber;
	}

	
}
