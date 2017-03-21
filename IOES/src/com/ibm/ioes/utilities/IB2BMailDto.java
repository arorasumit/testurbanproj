package com.ibm.ioes.utilities;
//Tag Name Resource Name  Date		  		Description
//[001]	  LAWKUSH	    07-Feb-2013				Alert not generated post COPC approval
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class IB2BMailDto {

	final public static String NEWORDER="VALIDATENEWORDER";
	final public static String SAVEACTION="SAVEACTIONMAIL";
	final public static String SAVEACTIONMAILONREJECTION="SAVEACTIONMAILONREJECTION";
	//start[001]
	final public static String SAVEACTIONMAILONREJECTION_CHANGE="SAVEACTIONMAILONREJECTION_CHANGE";
	final public static String NEWMAILTEMPLATE="NEWMAILTEMPLATE";
	final public static String PIEMAILTEMPLATE="PIEMAILTEMPLATE";
	final public static String NEWMAILTEMPLATE_CHANGE="NEWMAILTEMPLATE_CHANGE";
	//end[001]
	final public static String SAVEACTION_SMS="SAVEACTION_SMS";
	final public static String NEWORDER_SMS="VALIDATENEWORDER_SMS";
	
	final public static String ATTENDED_DIAL_IN="ATTENDED_DIAL_IN";
	final public static String PASSWORD_RESET="PASSWORD_RESET";
	final public static String PASSWORD_RESET_SMS="PASSWORD_RESET_SMS";
	final public static String AUTO_REMINDER_MAIL="AUTO_REMINDER_MAIL";
	final public static String CONFIRMATION_ON_USER_REGISTRATION="CONFIRMATION_ON_USER_REGISTRATION";
	final public static String CONFIRMATION_EMAIL_ON_USER_REGISTRATION="CONFIRMATION_EMAIL_ON_USER_REGISTRATION";
//  ADDED BY MANISHA START TO SEND MAIL FOR PENDING ORDERS 
	public static final String PENDINGORDERTEMPLATE = "SENDACTIONMAILONPENDINGORDER";
	// added by manisha defect no 15032013010 
	public static final String REASSIGNPMTEMPLATE = "SENDACTIONMAILONREASSIGNEDPM";
	// end
	public static final String THIRD_PARTY_CHANGE_ORDER_MAIL = "THIRD_PARTY_CHANGE_ORDER_MAIL";
	private String accountno;
//  ADDED BY MANISHA END
	
//	Pass For mail
	private String mailTemplateType=null;
	
	
	private String attachmentPath=null;
	private String to[]=null;
	private String cc[]=null;
	private String bcc[]=null;
	private String reassigned_pm;
	private String last_pm;
	
	
	//End: Pass For mail
	
	
	private String mailTemplateHeader=null;
	private String mailTemplateFooter=null;
	private String mailTemplateBody=null;
	private String mailTemplateSubject=null;
	
	
	private ArrayList<String> sms_mobileNo=null;
	/*
	 * 		IOES VARIABLES
	 */
	
	private String accountMgrEmail=null;
	private String accountName=null;
	private String creatorEmail=null;
	private String orderNo=null;
	private String codinatorEmailId=null;
	private String nextTaskAssignedEmail="";
	//lawkush start
	private String taskName;
	private String isApproved;
	private String taskStartDate;
	private String taskEndDate;
	private String orderType;
	private String nextOwner;
	private String taskOwner;
	private String remarks;
	private String productName;
	private String nextTaskName;
	private String smsText;
	private String prevTaskAssignedEmail="";
	private String currentTaskOwner="";
	private String islasttask;
	private String nfaNumber;
	private String prNumber;
	private String serviceID;
	
	
	public String getServiceID() {
		return serviceID;
	}
	public void setServiceID(String serviceID) {
		this.serviceID = serviceID;
	}
	public String getIslasttask() {
		return islasttask;
	}
	public void setIslasttask(String islasttask) {
		this.islasttask = islasttask;
	}
	public String getCurrentTaskOwner() {
		return currentTaskOwner;
	}
	public void setCurrentTaskOwner(String currentTaskOwner) {
		this.currentTaskOwner = currentTaskOwner;
	}
	private String pMEmailId;//------------15032013008------Start---------
	
	//------------15032013008------Start---------
	public String getpMEmailId() {
		return pMEmailId;
	}
	public void setpMEmailId(String pMEmailId) {
		this.pMEmailId = pMEmailId;
	}
	//------------15032013008------End---------
	
	
	
	
	   public String getSmsText() {
			return smsText;
		}
		public void setSmsText(String smsText) {
			this.smsText = smsText;
		}
	
	public String getIsApproved() {
		return isApproved;
	}
	public void setIsApproved(String isApproved) {
		this.isApproved = isApproved;
	}
	public String getNextOwner() {
		return nextOwner;
	}
	public void setNextOwner(String nextOwner) {
		this.nextOwner = nextOwner;
	}
	public String getNextTaskName() {
		return nextTaskName;
	}
	public void setNextTaskName(String nextTaskName) {
		this.nextTaskName = nextTaskName;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getTaskEndDate() {
		return taskEndDate;
	}
	public void setTaskEndDate(String taskEndDate) {
		this.taskEndDate = taskEndDate;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskOwner() {
		return taskOwner;
	}
	public void setTaskOwner(String taskOwner) {
		this.taskOwner = taskOwner;
	}
	public String getTaskStartDate() {
		return taskStartDate;
	}
	public void setTaskStartDate(String taskStartDate) {
		this.taskStartDate = taskStartDate;
	}
	//lawkush end
	public String getAccountMgrEmail() {
		return accountMgrEmail;
	}
	public void setAccountMgrEmail(String accountMgrEmail) {
		this.accountMgrEmail = accountMgrEmail;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getCreatorEmail() {
		return creatorEmail;
	}
	public void setCreatorEmail(String creatorEmail) {
		this.creatorEmail = creatorEmail;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getMailTemplateType() {
		return mailTemplateType;
	}
	public void setMailTemplateType(String mailTemplateType) {
		this.mailTemplateType = mailTemplateType;
	}
	public String getMailTemplateBody() {
		return mailTemplateBody;
	}
	public void setMailTemplateBody(String mailTemplateBody) {
		this.mailTemplateBody = mailTemplateBody;
	}
	public String getMailTemplateSubject() {
		return mailTemplateSubject;
	}
	public void setMailTemplateSubject(String mailTemplateSubject) {
		this.mailTemplateSubject = mailTemplateSubject;
	}
	public String getAttachmentPath() {
		return attachmentPath;
	}
	public void setAttachmentPath(String attachmentPath) {
		this.attachmentPath = attachmentPath;
	}
	public String[] getBcc() {
		return bcc;
	}
	public void setBcc(String[] bcc) {
		this.bcc = bcc;
	}
	public String[] getCc() {
		return cc;
	}
	public void setCc(String[] cc) {
		this.cc = cc;
	}
	public String[] getTo() {
		return to;
	}
	public void setTo(String[] to) {
		this.to = to;
	}
	public String getMailTemplateFooter() {
		return mailTemplateFooter;
	}
	public void setMailTemplateFooter(String mailTemplateFooter) {
		this.mailTemplateFooter = mailTemplateFooter;
	}
	public String getMailTemplateHeader() {
		return mailTemplateHeader;
	}
	public void setMailTemplateHeader(String mailTemplateHeader) {
		this.mailTemplateHeader = mailTemplateHeader;
	}
	
	public String getCodinatorEmailId() {
		return codinatorEmailId;
	}
	public void setCodinatorEmailId(String codinatorEmailId) {
		this.codinatorEmailId = codinatorEmailId;
	}
	public ArrayList<String> getSms_mobileNo() {
		return sms_mobileNo;
	}
	public void setSms_mobileNo(ArrayList<String> sms_mobileNo) {
		this.sms_mobileNo = sms_mobileNo;
	}
	public String getNextTaskAssignedEmail() {
		return nextTaskAssignedEmail;
	}
	public void setNextTaskAssignedEmail(String nextTaskAssignedEmail) {
		this.nextTaskAssignedEmail = nextTaskAssignedEmail;
	}
	public String getAccountno() {
		return accountno;
	}
	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}
	public String getLast_pm() {
		return last_pm;
	}
	public void setLast_pm(String last_pm) {
		this.last_pm = last_pm;
	}
	public String getReassigned_pm() {
		return reassigned_pm;
	}
	public void setReassigned_pm(String reassigned_pm) {
		this.reassigned_pm = reassigned_pm;
	}
	public String getPrevTaskAssignedEmail() {
		return prevTaskAssignedEmail;
	}
	public void setPrevTaskAssignedEmail(String prevTaskAssignedEmail) {
		this.prevTaskAssignedEmail = prevTaskAssignedEmail;
	}
	public String getNfaNumber() {
		return nfaNumber;
	}
	public void setNfaNumber(String nfaNumber) {
		this.nfaNumber = nfaNumber;
	}
	public String getPrNumber() {
		return prNumber;
	}
	public void setPrNumber(String prNumber) {
		this.prNumber = prNumber;
	}
	
	
	
}
