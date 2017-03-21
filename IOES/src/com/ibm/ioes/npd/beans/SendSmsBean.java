package com.ibm.ioes.npd.beans;

import org.apache.struts.action.ActionForm;

public class SendSmsBean extends ActionForm
{
	private String txtMessage;

	private String mobileNumber;
	
	private String emailId;
	
	private String methodName;
	
	private String emailMessage;
	

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getTxtMessage() {
		return txtMessage;
	}

	public void setTxtMessage(String txtMessage) {
		this.txtMessage = txtMessage;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getEmailMessage() {
		return emailMessage;
	}

	public void setEmailMessage(String emailMessage) {
		this.emailMessage = emailMessage;
	}
}
