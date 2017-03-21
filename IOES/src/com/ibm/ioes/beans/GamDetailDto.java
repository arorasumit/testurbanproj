package com.ibm.ioes.beans;

public class GamDetailDto {
	
	public String gamName = null;
	public Long crmEmployeeId = null;
	public Long getCrmEmployeeId() {
		return crmEmployeeId;
	}
	public void setCrmEmployeeId(Long crmEmployeeId) {
		this.crmEmployeeId = crmEmployeeId;
	}
	public String getGamName() {
		return gamName;
	}
	public void setGamName(String gamName) {
		this.gamName = gamName;
	}
	
	
}
