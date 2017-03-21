package com.ibm.ioes.forms;

import com.ibm.ioes.utilities.PagingSorting;

public class CommonDTO {
	private String roleName;
	private String roleId;
	private String msgOut;
	public String messageId = null;//CHILD_PRESENT,ENTRY_COPIED,FIRST_ENTRY
	public String message = null;
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getRoleId() {
		return roleId;
	}


	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}


	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getMsgOut() {
		return msgOut;
	}

	public void setMsgOut(String msgOut) {
		this.msgOut = msgOut;
	}



}
