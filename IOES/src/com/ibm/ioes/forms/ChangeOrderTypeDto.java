package com.ibm.ioes.forms;


public class ChangeOrderTypeDto 
{

	private String changeTypeId;
	
	private String changeTypeName;
	
	private String status;
	private String masterValue;

	public String getMasterValue() {
		return masterValue;
	}

	public void setMasterValue(String masterValue) {
		this.masterValue = masterValue;
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

		
}
