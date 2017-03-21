package com.ibm.ioes.npd.utilities;

import java.io.File;
import java.util.ArrayList;

import org.apache.struts.upload.FormFile;

import com.ibm.ioes.npd.utilities.*;;
public class TransactionDto {
	
	
	private  ArrayList <ParameterBean> paramList= null;
	private String actionEevent=null;
	private String saveMode=null;
	private String reqType=null;
	private int moduleId=0;
	
	private Integer poroject=0;//ProjectId
	private Integer stage=0;//StageID
	private Integer workflow=0;//WorkFlowId
	private Integer task=0;//TaskId
	private String taskOwner="0";
	
	private Integer actionType=0;//Type of Action...1-Approve,2--Reject,3-SenfRFI,4-GiveRFI
	private Integer nextStageId=0;
	private Integer nextTaskId=0;
	private Integer requestId=0;//
	
	
	private String remarks="";
	private int isDocAttached=0;
	private FormFile document=null;
	private String docName="";
	private String docType="";// set the extension of Document like doc,xls etc.
	private double netAmount=0;
	private double verifyAmount=0;
	private String rfiFrom="0";
	
	
	private int isActive=1;
	private String currentDate="";
	private String ctreatedBy="0";
	private String modifiedBy="0";
	
	// FOR EMAIL
	private int isMail=0;
	private int fromId=0;
	private String toId="";
	private String ccId="";
	private String bccId="";
	private String subject="";
	private String body="";
	private String header="";
	private String footer="";
	//private int sendBy=0;
	private int TaskActionTaken=0;//As per Varun's requirement 
	private String masterInvoiceNo="";
	private int billCycleId=0;
	
  public String toString(){
		
		return "ActionEevent= "+this.actionEevent+"  SaveMode= "+this.saveMode+" ReqType= "+this.reqType+
		        "  ModuleId= "+this.moduleId+" Poroject="+this.poroject+" Stage= "+this.stage+" Task= "+this.task+
		        "  TaskOwner="+this.taskOwner+" Workflow= "+this.workflow +"isDocAttached= "+this.isDocAttached;
		
	}



public String getActionEevent() {
	return actionEevent;
}



public void setActionEevent(String actionEevent) {
	this.actionEevent = actionEevent;
}



public Integer getActionType() {
	return actionType;
}



public void setActionType(Integer actionType) {
	this.actionType = actionType;
}



public String getCtreatedBy() {
	return ctreatedBy;
}



public void setCtreatedBy(String ctreatedBy) {
	this.ctreatedBy = ctreatedBy;
}



public String getCurrentDate() {
	return currentDate;
}



public void setCurrentDate(String currentDate) {
	this.currentDate = currentDate;
}



public int getIsActive() {
	return isActive;
}



public void setIsActive(int isActive) {
	this.isActive = isActive;
}



public int getIsDocAttached() {
	return isDocAttached;
}



public void setIsDocAttached(int isDocAttached) {
	this.isDocAttached = isDocAttached;
}



public String getModifiedBy() {
	return modifiedBy;
}



public void setModifiedBy(String modifiedBy) {
	this.modifiedBy = modifiedBy;
}



public int getModuleId() {
	return moduleId;
}



public void setModuleId(int moduleId) {
	this.moduleId = moduleId;
}



public Integer getNextStageId() {
	return nextStageId;
}



public void setNextStageId(Integer nextStageId) {
	this.nextStageId = nextStageId;
}



public Integer getNextTaskId() {
	return nextTaskId;
}



public void setNextTaskId(Integer nextTaskId) {
	this.nextTaskId = nextTaskId;
}



public ArrayList<ParameterBean> getParamList() {
	return paramList;
}



public void setParamList(ArrayList<ParameterBean> paramList) {
	this.paramList = paramList;
}



public Integer getPoroject() {
	return poroject;
}



public void setPoroject(Integer poroject) {
	this.poroject = poroject;
}



public String getRemarks() {
	return remarks;
}



public void setRemarks(String remarks) {
	this.remarks = remarks;
}



public String getReqType() {
	return reqType;
}



public void setReqType(String reqType) {
	this.reqType = reqType;
}



public String getSaveMode() {
	return saveMode;
}



public void setSaveMode(String saveMode) {
	this.saveMode = saveMode;
}



public Integer getStage() {
	return stage;
}



public void setStage(Integer stage) {
	this.stage = stage;
}



public Integer getTask() {
	return task;
}



public void setTask(Integer task) {
	this.task = task;
}



public String getTaskOwner() {
	return taskOwner;
}



public void setTaskOwner(String taskOwner) {
	this.taskOwner = taskOwner;
}



public Integer getWorkflow() {
	return workflow;
}



public void setWorkflow(Integer workflow) {
	this.workflow = workflow;
}



public FormFile getDocument() {
	return document;
}



public void setDocument(FormFile document) {
	this.document = document;
}



public double getNetAmount() {
	return netAmount;
}



public void setNetAmount(double netAmount) {
	this.netAmount = netAmount;
}



public Integer getRequestId() {
	return requestId;
}



public void setRequestId(Integer requestId) {
	this.requestId = requestId;
}



public String getRfiFrom() {
	return rfiFrom;
}



public void setRfiFrom(String rfiFrom) {
	this.rfiFrom = rfiFrom;
}



public double getVerifyAmount() {
	return verifyAmount;
}



public void setVerifyAmount(double verifyAmount) {
	this.verifyAmount = verifyAmount;
}



public String getDocName() {
	return docName;
}



public void setDocName(String docName) {
	this.docName = docName;
}



public String getDocType() {
	return docType;
}



public void setDocType(String docType) {
	this.docType = docType;
}



public String getBccId() {
	return bccId;
}



public void setBccId(String bccId) {
	this.bccId = bccId;
}



public String getBody() {
	return body;
}



public void setBody(String body) {
	this.body = body;
}



public String getCcId() {
	return ccId;
}



public void setCcId(String ccId) {
	this.ccId = ccId;
}



public String getFooter() {
	return footer;
}



public void setFooter(String footer) {
	this.footer = footer;
}



public int getFromId() {
	return fromId;
}



public void setFromId(int fromId) {
	this.fromId = fromId;
}



public String getHeader() {
	return header;
}



public void setHeader(String header) {
	this.header = header;
}



public int getIsMail() {
	return isMail;
}



public void setIsMail(int isMail) {
	this.isMail = isMail;
}



public String getSubject() {
	return subject;
}



public void setSubject(String subject) {
	this.subject = subject;
}



public String getToId() {
	return toId;
}



public void setToId(String toId) {
	this.toId = toId;
}



public int getTaskActionTaken() {
	return TaskActionTaken;
}



public void setTaskActionTaken(int taskActionTaken) {
	TaskActionTaken = taskActionTaken;
}




public int getBillCycleId() {
	return billCycleId;
}



public void setBillCycleId(int billCycleId) {
	this.billCycleId = billCycleId;
}



public String getMasterInvoiceNo() {
	return masterInvoiceNo;
}



public void setMasterInvoiceNo(String masterInvoiceNo) {
	this.masterInvoiceNo = masterInvoiceNo;
}



}
