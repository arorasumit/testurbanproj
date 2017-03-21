package com.ibm.ioes.beans;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

public class ChangeOrderTypeBean extends ActionForm
{
	private String changeTypeId;
	
	private String changeTypeName;
	
	private String status;
	
	private String changeTypeIdList;
	private String statusList;
	private String hiddenFlag;
	
	private String editChangeTypeName;
	private String editStatus;

	public String getEditChangeTypeName() {
		return editChangeTypeName;
	}

	public void setEditChangeTypeName(String editChangeTypeName) {
		this.editChangeTypeName = editChangeTypeName;
	}

	public String getEditStatus() {
		return editStatus;
	}

	public void setEditStatus(String editStatus) {
		this.editStatus = editStatus;
	}

	public String getHiddenFlag() {
		return hiddenFlag;
	}

	public void setHiddenFlag(String hiddenFlag) {
		this.hiddenFlag = hiddenFlag;
	}

	public String getChangeTypeId() {
		return changeTypeId;
	}

	public void setChangeTypeId(String changeTypeId) {
		this.changeTypeId = changeTypeId;
	}

	public String getChangeTypeName() {
		return changeTypeName;
	}

	public void setChangeTypeName(String changeTypeName) {
		this.changeTypeName = changeTypeName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getChangeTypeIdList() {
		return changeTypeIdList;
	}

	public void setChangeTypeIdList(String changeTypeIdList) {
		this.changeTypeIdList = changeTypeIdList;
	}

	public String getStatusList() {
		return statusList;
	}

	public void setStatusList(String statusList) {
		this.statusList = statusList;
	}
	
	
}
