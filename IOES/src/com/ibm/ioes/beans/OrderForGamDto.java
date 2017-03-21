package com.ibm.ioes.beans;

import java.util.ArrayList;

public class OrderForGamDto {
	public long orderNo ;
	private Exception exception = null;
	private String exceptionMessage = null;
	private int returnStatus=0;
	private long creditSharingModuleId ;
	private long fxGamOrderMappingId;
	
	ArrayList<GamDetailDto> gamDetails = null; 
	

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public int getReturnStatus() {
		return returnStatus;
	}

	public void setReturnStatus(int returnStatus) {
		this.returnStatus = returnStatus;
	}

	public long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(long orderNo) {
		this.orderNo = orderNo;
	}

	public ArrayList<GamDetailDto> getGamDetails() {
		return gamDetails;
	}

	public void setGamDetails(ArrayList<GamDetailDto> gamDetails) {
		this.gamDetails = gamDetails;
	}

	public long getCreditSharingModuleId() {
		return creditSharingModuleId;
	}

	public void setCreditSharingModuleId(long creditSharingModuleId) {
		this.creditSharingModuleId = creditSharingModuleId;
	}

	public long getFxGamOrderMappingId() {
		return fxGamOrderMappingId;
	}

	public void setFxGamOrderMappingId(long fxGamOrderMappingId) {
		this.fxGamOrderMappingId = fxGamOrderMappingId;
	}
}
