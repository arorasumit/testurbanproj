/**
 * 
 */
package com.ibm.ioes.forms;

import com.ibm.ioes.utilities.PagingSorting;

/**
 * @author Raveendra
 * @
 */
public class OrderForPendingWithAMDto {
	private int crmAccountNo;
	private long accountManagerID;
	private int employeeid;
	private String orderNo;
	private String acmgrEmail;
	private String accountmgremail;
	private String accountManager;
	private String initial_am;
	private String last_am;
	private String last_changed_by;
    private String accountManagerAssigned; 
    private int maxPageNo;
    
    
    
	public int getMaxPageNo() {
		return maxPageNo;
	}
	public void setMaxPageNo(int maxPageNo) {
		this.maxPageNo = maxPageNo;
	}
	public String getAccountManagerAssigned() {
		return accountManagerAssigned;
	}
	public void setAccountManagerAssigned(String accountManagerAssigned) {
		this.accountManagerAssigned = accountManagerAssigned;
	}
	PagingSorting pagingSorting = new PagingSorting();	
	
	
	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}
	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
	}
	public long getAccountManagerID() {
		return accountManagerID;
	}
	public void setAccountManagerID(long accountManagerID) {
		this.accountManagerID = accountManagerID;
	}
	public int getCrmAccountNo() {
		return crmAccountNo;
	}
	public void setCrmAccountNo(int crmAccountNo) {
		this.crmAccountNo = crmAccountNo;
	}
	public int getEmployeeid() {
		return employeeid;
	}
	public void setEmployeeid(int employeeid) {
		this.employeeid = employeeid;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getAcmgrEmail() {
		return acmgrEmail;
	}
	public void setAcmgrEmail(String acmgrEmail) {
		this.acmgrEmail = acmgrEmail;
	}
	public String getAccountmgremail() {
		return accountmgremail;
	}
	public void setAccountmgremail(String accountmgremail) {
		this.accountmgremail = accountmgremail;
	}
	public String getAccountManager() {
		return accountManager;
	}
	public void setAccountManager(String accountManager) {
		this.accountManager = accountManager;
	}
	public String getInitial_am() {
		return initial_am;
	}
	public void setInitial_am(String initial_am) {
		this.initial_am = initial_am;
	}
	public String getLast_am() {
		return last_am;
	}
	public void setLast_am(String last_am) {
		this.last_am = last_am;
	}
	public String getLast_changed_by() {
		return last_changed_by;
	}
	public void setLast_changed_by(String last_changed_by) {
		this.last_changed_by = last_changed_by;
	}
	
	
	

}
