package com.ibm.ioes.clep;

public class CLEPUtilityDto {

	private int subChangeTypeId;
	private String subChangeTypeName;
	private String ib2bWorkflowAttachedId;
	private String ib2bWorkflowAttachedName;
	private String projectWorkflowId;
	private int poNumber;
	private String changeType;
	
	public String getChangeType() {
		return changeType;
	}
	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}
	public int getPoNumber() {
		return poNumber;
	}
	public void setPoNumber(int poNumber) {
		this.poNumber = poNumber;
	}
	public String getIb2bWorkflowAttachedId() {
		return ib2bWorkflowAttachedId;
	}
	public void setIb2bWorkflowAttachedId(String ib2bWorkflowAttachedId) {
		this.ib2bWorkflowAttachedId = ib2bWorkflowAttachedId;
	}
	public String getIb2bWorkflowAttachedName() {
		return ib2bWorkflowAttachedName;
	}
	public void setIb2bWorkflowAttachedName(String ib2bWorkflowAttachedName) {
		this.ib2bWorkflowAttachedName = ib2bWorkflowAttachedName;
	}
	public String getProjectWorkflowId() {
		return projectWorkflowId;
	}
	public void setProjectWorkflowId(String projectWorkflowId) {
		this.projectWorkflowId = projectWorkflowId;
	}
	public int getSubChangeTypeId() {
		return subChangeTypeId;
	}
	public void setSubChangeTypeId(int subChangeTypeId) {
		this.subChangeTypeId = subChangeTypeId;
	}
	public String getSubChangeTypeName() {
		return subChangeTypeName;
	}
	public void setSubChangeTypeName(String subChangeTypeName) {
		this.subChangeTypeName = subChangeTypeName;
	}
}
