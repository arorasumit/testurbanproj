/*Tag Name  Resource Name   Date		    CSR No			Description
 * [001]   Gunjan Singla   8-Jan-15    20141113-R1-020802		ParallelUpgrade-Multiple LSI Selection
 * */
 

package com.ibm.ioes.forms;

import java.util.ArrayList;

import com.ibm.ioes.utilities.PagingSorting;

public class ParallelUpgradeValidationDto {
	
	public ParallelUpgradeValidationDto() {
		// TODO Auto-generated constructor stub
	}
	
	public ParallelUpgradeValidationDto(String logicalSINumber) {
	
		this.logicalSINumber = logicalSINumber;
	}
	private String logicalSINumber;
	private String statusMsg="";
	private int statusFlag=1;
	private long logicalSINo;
	private long serviceTypeId;
	private String crmAccountNo;
	private long subChangeTypeId;
	private String m6FxProgressStatus;
	private int validationFlag=0;
	private int executeFlag=0;
	private String stage;
	
	
	private int customer_logicalSINumber;
	public String getLogicalSINumber() {
		return logicalSINumber;
	}
	public void setLogicalSINumber(String logicalSINumber) {
		this.logicalSINumber = logicalSINumber;
	}
	public String getStatusMsg() {
		return statusMsg;
	}
	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}
	
	public int getStatusFlag() {
		return statusFlag;
	}

	public void setStatusFlag(int statusFlag) {
		this.statusFlag = statusFlag;
	}
	
	public int getCustomer_logicalSINumber() {
		return customer_logicalSINumber;
	}

	public void setCustomer_logicalSINumber(int customer_logicalSINumber) {
		this.customer_logicalSINumber = customer_logicalSINumber;
	}
	
	public long getLogicalSINo() {
		return logicalSINo;
	}

	public void setLogicalSINo(long logicalSINo) {
		this.logicalSINo = logicalSINo;
	}

	public long getServiceTypeId() {
		return serviceTypeId;
	}

	public void setServiceTypeId(long serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}

	public String getCrmAccountNo() {
		return crmAccountNo;
	}

	public void setCrmAccountNo(String crmAccountNo) {
		this.crmAccountNo = crmAccountNo;
	}

	public long getSubChangeTypeId() {
		return subChangeTypeId;
	}

	public void setSubChangeTypeId(long subChangeTypeId) {
		this.subChangeTypeId = subChangeTypeId;
	}
	public int getValidationFlag() {
		return validationFlag;
	}

	public void setValidationFlag(int validationFlag) {
		this.validationFlag = validationFlag;
	}

	public int getExecuteFlag() {
		return executeFlag;
	}

	public void setExecuteFlag(int executeFlag) {
		this.executeFlag = executeFlag;
	}
	
	public String getM6FxProgressStatus() {
		return m6FxProgressStatus;
	}

	public void setM6FxProgressStatus(String m6FxProgressStatus) {
		this.m6FxProgressStatus = m6FxProgressStatus;
	}
	
	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	@Override
	public String toString() {
		return "ParallelUpgradeValidationDto [logicalSINumber="
				+ logicalSINumber + ", statusMsg=" + statusMsg
				+ ", statusFlag=" + statusFlag + ", logicalSINo=" + logicalSINo
				+ ", serviceTypeId=" + serviceTypeId + ", crmAccountNo="
				+ crmAccountNo + ", subChangeTypeId=" + subChangeTypeId
				+ ", m6FxProgressStatus=" + m6FxProgressStatus
				+ ", validationFlag=" + validationFlag + ", executeFlag="
				+ executeFlag + ", stage=" + stage
				+ ", customer_logicalSINumber=" + customer_logicalSINumber
				+ "]";
	}

	
}
