package com.ibm.ioes.npd.beans;

import org.apache.struts.action.ActionForm;

public class ResetPasswordBean extends ActionForm 
{
	private String emailId=null;
	
	private int empNPDID;
	
	private String existingPwd;

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getExistingPwd() {
		return existingPwd;
	}

	public void setExistingPwd(String existingPwd) {
		this.existingPwd = existingPwd;
	}

	public int getEmpNPDID() {
		return empNPDID;
	}

	public void setEmpNPDID(int empNPDID) {
		this.empNPDID = empNPDID;
	}
}
