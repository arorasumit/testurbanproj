package com.ibm.ioes.newdesign.dto;

import com.ibm.ioes.forms.PagingDto;

public class StandardReason {
	private Long stdReasonId;
	private String stdReasonName;
	private Integer stdReasonStatus;
	private Integer interfaceType;
	private int maxPageNo;
	private PagingDto pagingDto =new PagingDto();
	
	public int getMaxPageNo() {
		return maxPageNo;
	}
	public void setMaxPageNo(int maxPageNo) {
		this.maxPageNo = maxPageNo;
	}
	public PagingDto getPagingDto() {
		return pagingDto;
	}
	public void setPagingDto(PagingDto pagingDto) {
		this.pagingDto = pagingDto;
	}
	public Long getStdReasonId() {
		return stdReasonId;
	}
	public void setStdReasonId(Long stdReasonId) {
		this.stdReasonId = stdReasonId;
	}
	public String getStdReasonName() {
		return stdReasonName;
	}
	public void setStdReasonName(String stdReasonName) {
		this.stdReasonName = stdReasonName;
	}
	public Integer getStdReasonStatus() {
		return stdReasonStatus;
	}
	public void setStdReasonStatus(Integer stdReasonStatus) {
		this.stdReasonStatus = stdReasonStatus;
	}
	public Integer getInterfaceType() {
		return interfaceType;
	}
	public void setInterfaceType(Integer interfaceType) {
		this.interfaceType = interfaceType;
	}
	

}
