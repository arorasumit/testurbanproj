package com.ibm.ioes.beans;
/*
 * Tag Name Resource Name    Date		CSR No			Description
 * [001]     Gunjan Singla                          added interfaceId
 * [002]     Gunjan Singla   5-Aug-14   CSR_20140526_R1_020159     Order Cancellation Post Publish
 * */

public class UserInfoDto {
	
	private String userId;
	private String userRoleId;
	private String userName;
	private String roleName;
	private String phoneNo;
	private String emailId;
	private String employeeId;
	
	private String salesFirstName;
	private String salesLastName;
	private String salesPhoneno;//changed by kalpana from long to string for bug id HYPR11042013001
	private String salesEmailId;
	
	private int pageSizeServiceLine;
//	
	private int pageSizeOrderApprovalHistory;
	private int pagesizeForCopyOrder;
	
	private int pageSizeLSICancellation;
	private int pageSizeBTCharges;
	private int pageSizeBTLines;	
	
	private int pageSizeChargeLines;
	private String regionId;//added by kalpana for copc region change
	//Start[Date:01-04-2013 Paging Size Configurable for Bulkupload LSI Look up]---
	private int pageSizeLookUPLSILines;
	private String reportParameter;//Added for checking if user came from report link
	private String interfaceId;
	private int respId;
	//[002]
	private int pageSizeLSICancelLine;

	public int getRespId() {
		return respId;
	}
	public void setRespId(int respId) {
		this.respId = respId;
	}
	public String getInterfaceId() {
		return interfaceId;
	}
	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}
	public String getReportParameter() {
		return reportParameter;
	}
	public void setReportParameter(String reportParameter) {
		this.reportParameter = reportParameter;
	}
	public String getRegionId() {
		return regionId;
	}
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	public int getPageSizeChargeLines() {
		return pageSizeChargeLines;
	}
	public void setPageSizeChargeLines(int pageSizeChargeLines) {
		this.pageSizeChargeLines = pageSizeChargeLines;
	}
	public int getPageSizeServiceLine() {
		return pageSizeServiceLine;
	}
	public void setPageSizeServiceLine(int pageSizeServiceLine) {
		this.pageSizeServiceLine = pageSizeServiceLine;
	}
	
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserRoleId() {
		return userRoleId;
	}
	public void setUserRoleId(String userRoleId) {
		this.userRoleId = userRoleId;
	}
	public String getSalesEmailId() {
		return salesEmailId;
	}
	public void setSalesEmailId(String salesEmailId) {
		this.salesEmailId = salesEmailId;
	}
	public String getSalesFirstName() {
		return salesFirstName;
	}
	public void setSalesFirstName(String salesFirstName) {
		this.salesFirstName = salesFirstName;
	}
	public String getSalesLastName() {
		return salesLastName;
	}
	public void setSalesLastName(String salesLastName) {
		this.salesLastName = salesLastName;
	}
	public String getSalesPhoneno() {
		return salesPhoneno;
	}
	public void setSalesPhoneno(String salesPhoneno) {
		this.salesPhoneno = salesPhoneno;
	}
	public int getPagesizeForCopyOrder() {
		return pagesizeForCopyOrder;
	}
	public void setPagesizeForCopyOrder(int pagesizeForCopyOrder) {
		this.pagesizeForCopyOrder = pagesizeForCopyOrder;
	}
	public int getPageSizeOrderApprovalHistory() {
		return pageSizeOrderApprovalHistory;
	}
	public void setPageSizeOrderApprovalHistory(int pageSizeOrderApprovalHistory) {
		this.pageSizeOrderApprovalHistory = pageSizeOrderApprovalHistory;
	}
	
	public int getPageSizeBTCharges() {
		return pageSizeBTCharges;
	}
	public void setPageSizeBTCharges(int pageSizeBTCharges) {
		this.pageSizeBTCharges = pageSizeBTCharges;
	}
	public int getPageSizeBTLines() {
		return pageSizeBTLines;
	}
	public void setPageSizeBTLines(int pageSizeBTLines) {
		this.pageSizeBTLines = pageSizeBTLines;
	}
	public int getPageSizeLookUPLSILines() {
		return pageSizeLookUPLSILines;
	}
	public void setPageSizeLookUPLSILines(int pageSizeLookUPLSILines) {
		this.pageSizeLookUPLSILines = pageSizeLookUPLSILines;
	}
	//[002] start
	public int getPageSizeLSICancelLine() {
		return pageSizeLSICancelLine;
	}
	public void setPageSizeLSICancelLine(int pageSizeLSICancelLine) {
		this.pageSizeLSICancelLine = pageSizeLSICancelLine;
	}
	//[002] end
	
	public int getPageSizeLSICancellation() {
		return pageSizeLSICancellation;
	}
	public void setPageSizeLSICancellation(int pageSizeLSICancellation) {
		this.pageSizeLSICancellation = pageSizeLSICancellation;
	}
	

}
