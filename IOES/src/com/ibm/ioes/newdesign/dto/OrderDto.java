package com.ibm.ioes.newdesign.dto;
/**
 * 
 * @author IBM_ADMIN
 *@since June 2014
 */
public class OrderDto {

	Long orderNo;
	String order_type;
	Long changeTypeId;
	Long accountId;
	Long bcp_Id;
	Long dispatchAddCode;
	Integer totalCountInWorkflowTask;
	
	Integer copyOrderAlreadyInProgress;
	Double minsForCopyOrder;
	Integer committedServiceCount;
	Integer uncommittedReadServiceCount;
	
	boolean anyException ;
	
	public boolean isAnyException() {
		return anyException;
	}
	public void setAnyException(boolean anyException) {
		this.anyException = anyException;
	}
	public Integer getCommittedServiceCount() {
		return committedServiceCount;
	}
	public void setCommittedServiceCount(Integer committedServiceCount) {
		this.committedServiceCount = committedServiceCount;
	}
	public Integer getUncommittedReadServiceCount() {
		return uncommittedReadServiceCount;
	}
	public void setUncommittedReadServiceCount(Integer uncommittedReadServiceCount) {
		this.uncommittedReadServiceCount = uncommittedReadServiceCount;
	}
	public Integer getCopyOrderAlreadyInProgress() {
		return copyOrderAlreadyInProgress;
	}
	public void setCopyOrderAlreadyInProgress(Integer copyOrderAlreadyInProgress) {
		this.copyOrderAlreadyInProgress = copyOrderAlreadyInProgress;
	}
	public Double getMinsForCopyOrder() {
		return minsForCopyOrder;
	}
	public void setMinsForCopyOrder(Double minsForCopyOrder) {
		this.minsForCopyOrder = minsForCopyOrder;
	}
	public Long getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}
	public String getOrder_type() {
		return order_type;
	}
	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}
	public Long getChangeTypeId() {
		return changeTypeId;
	}
	public void setChangeTypeId(Long changeTypeId) {
		this.changeTypeId = changeTypeId;
	}
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public Long getBcp_Id() {
		return bcp_Id;
	}
	public void setBcp_Id(Long bcp_Id) {
		this.bcp_Id = bcp_Id;
	}
	public Long getDispatchAddCode() {
		return dispatchAddCode;
	}
	public void setDispatchAddCode(Long dispatchAddCode) {
		this.dispatchAddCode = dispatchAddCode;
	}
	public Integer getTotalCountInWorkflowTask() {
		return totalCountInWorkflowTask;
	}
	public void setTotalCountInWorkflowTask(Integer totalCountInWorkflowTask) {
		this.totalCountInWorkflowTask = totalCountInWorkflowTask;
	}
	
}
