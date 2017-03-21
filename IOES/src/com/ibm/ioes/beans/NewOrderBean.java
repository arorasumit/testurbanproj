package com.ibm.ioes.beans;

//[00044]	 Ashutosh Singh		23-June-11	CSR00-05422     Creating Change Order From Existing order in Billing Queue
//[001] LAWKUSH		1-July-11	CSR00-05422     Added isurgent checkbox on neworder page 
//[002] Gunjan Singla    10-Dec-2013         Added cancelOrderReason 
//[003] Vipin Saharia 26-Dec-2013 isDisplayRepushBtn Added to get SCMProgressStatus for populating Repush Button
//[004] Gunjan Singla    5-Mar-2013          Added cancelOrderReasonDD
//[005]   Neha Maheshwari    added service segment    NORTH STAR ACCOUNT TAGGING
//[006]	 Raveendra Kumar	03-Mar-15	20150225-R1-021110     Order transfer rights required on GUI for AM Role
//[007] VIPIN SAHARIA 11-May-2015 20150403-R2-021204 Project Satyapan Adding ISP tagging fields to header section
//[008] Priya Gupta  19-Jun-2015 Added ldclause column in po details tab
//[010] RAHUL TANDON 25-JAN-2016 20150508-R2-021322 SalesForce Opportunity number in iB2B
//[226] Satish Kumar   28-Oct-2016  CSR-20160922-R1-022673  EportToExcelButton ON OrderRemovalHistory


import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

import com.ibm.ioes.forms.PagingDto;
import com.ibm.ioes.utilities.PagingSorting;

public class NewOrderBean extends ActionForm
{
	private String attribute_3;
	private String attribute_2;
	//for incompleteorder new&change
	private String searchCRMOrder;
	private String searchAccountNo;
	private String searchOrderType;
	private String searchfromDate;
	private String searchToDate;
	private String searchSourceName;
	private String searchQuoteNumber;
	private String searchCurrency;
	private String searchCurrencyName;
	private String searchStageName;
	private String searchSource;
	private String searchAccountName;
	private String orderOwner;
	private String orderCreationSourceName;
	private String custSegment;
	
		//[007] Start Project Satyapan
		private int ispTagging;
		private int ispLicCtgry;
		private String ispLicDate;
		private String ispLicNo;
		//[007] End Project Satyapan
		
	//[010] starts
	private String channelMasterTagging;
	private long channelPartnerId;
	private String channelPartnerName;
	private String channelpartnerCode;
	private String fieldEngineer;
	private long fieldEngineerId;
	
	private String channelPartnerCtgryName="";
	private long channelPartnerCtgry;
	private String channelPartnerCode;
	
	//[226 starts
	
   private ArrayList  taskOrderList;
   
	public ArrayList getTaskOrderList() {
	return taskOrderList;
}

public void setTaskOrderList(ArrayList taskOrderList) {
	this.taskOrderList = taskOrderList;
}

public int getsLNO() {
	return sLNO;
}

public void setsLNO(int sLNO) {
	this.sLNO = sLNO;
}

	public String getChannelPartnerCode() {
		return channelPartnerCode;
	}

	public void setChannelPartnerCode(String channelPartnerCode) {
		this.channelPartnerCode = channelPartnerCode;
	}

	public String getChannelPartnerCtgryName() {
		return channelPartnerCtgryName;
	}

	public void setChannelPartnerCtgryName(String channelPartnerCtgryName) {
		this.channelPartnerCtgryName = channelPartnerCtgryName;
	}

	public long getChannelPartnerCtgry() {
		return channelPartnerCtgry;
	}

	public void setChannelPartnerCtgry(long channelPartnerCtgry) {
		this.channelPartnerCtgry = channelPartnerCtgry;
	}
	private String isActive;
	private int maxPageNo;
	private PagingDto pagingDto =new PagingDto();
	//[010] ends

	public long getChannelPartnerId() {
		return channelPartnerId;
	}

	public void setChannelPartnerId(long channelPartnerId) {
		this.channelPartnerId = channelPartnerId;
	}

	

	public void setChannelPartnerName(String channelPartnerName) {
		this.channelPartnerName = channelPartnerName;
	}

	public String getChannelPartnerName() {
		return channelPartnerName;
	}

	public String getChannelpartnerCode() {
		return channelpartnerCode;
	}

	public void setChannelpartnerCode(String channelpartnerCode) {
		this.channelpartnerCode = channelpartnerCode;
	}

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

	public String getOrderOwner() {
		return orderOwner;
	}


	public void setOrderOwner(String orderOwner) {
		this.orderOwner = orderOwner;
	}
	PagingSorting pagingSorting = new PagingSorting();
	
	
	private ArrayList orderList=null;
	private String  SPIDUrlforVPC;
	private String orderNo;
	
	//start [006]
	private long accountManagerID;
	private int employeeid;
	private String acmgrEmail;
	private String accountmgremail;
	private String initial_am;
	private String last_am;
	private String last_changed_by;
	private String accountManagerAssigned;
	private ArrayList displayOrderListForPendingWithAM;
	
	
	
	public ArrayList getDisplayOrderListForPendingWithAM() {
		return displayOrderListForPendingWithAM;
	}


	public void setDisplayOrderListForPendingWithAM(
			ArrayList displayOrderListForPendingWithAM) {
		this.displayOrderListForPendingWithAM = displayOrderListForPendingWithAM;
	}


	public String getAccountManagerAssigned() {
		return accountManagerAssigned;
	}


	public void setAccountManagerAssigned(String accountManagerAssigned) {
		this.accountManagerAssigned = accountManagerAssigned;
	}


	public long getAccountManagerID() {
		return accountManagerID;
	}


	public void setAccountManagerID(long accountManagerID) {
		this.accountManagerID = accountManagerID;
	}


	public int getEmployeeid() {
		return employeeid;
	}


	public void setEmployeeid(int employeeid) {
		this.employeeid = employeeid;
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
//End [006]
	private String accountID;
	private String crmAccountNo;
	private String accountName;
	
	private String accountManager;
	
	private String accphoneNo;
	
	//Rakshika
	private String serviceName;
	private String changeType;
	
	private String newType;
	
	private String projectManager;
	
	private String projectManagerID;
	private String lob;
	
	//[005] Start
    private String serviceSegment;
	//[005] End
	private String orderType;
	
	private String orderDate;
	
	private String sourceName;
	
	private String quoteNo;
	
	private String curShortCode;
	
	private String currencyID;
	
	private String poNumber;
	
	private String status;
	
	private String stage;
	
	private String stageName;
	
	private String hdnOrderNo;
	
	private String spFirstname;
	
	private String spLastName;
	
	private String spLPhno;
	
	private String spLEmail;
	
	//added by mohit
	private String interfaceName="";
	
	private String region;
	
	private String zone;
	private String regionId;
	
	private String opportunityId;
	//Added by Ashutosh as on date 17 nov 2010
	private String salutationId;
	private String salutationName;
	private String zoneId = "0";
	
	private String currencyName;
	
	private String currencyCode;
	
	private String updateType;
	
	private String searchAccount;
	
	private String changeRate;
	//for MAIN TAB STARTS
	
	private String attributeLabel;
	
	private String attributeValue;
	
	private String dataType;
	
	private String alisName;
	
	private String attributeID;
	
	private String attMaxLength;
	
	private String expectedValue;
	
	private int attCount;
	
	private String mandatory;
	
	private int serviceSubTypeId;
	
	private ArrayList serviceSubType;
	
	private int hdnServiceCounter;
	
	private String contactUpdateType;
	private String[] hdnserviceDetailId;
	private String[] chk_spId;
	private String hdnSelectedServiceDetailId;
	private HSSFWorkbook productCatelogTemplateWorkbook;
	
	private long addID;
	
//	for CONTACT TAB Starts
	private String contactType;
	private String saluation;
	private String firstName;
	private String lastName;
	private String cntEmail;
	private String contactCell;
	private String contactFax;
	private Long contactId;
	//	for CONTACT TAB Ends
		
	//for ADDRESS TAB STARTS
//	private Long addID;
	private String address1;
	private String address2;
	private String address3;
	private String cityName;
	private String stateName;
	private String countryName;
	//Added by Ashutosh
	private int countyCode;
	private int stateCode;
	private int cityCode;
	private String pinNo;
	
	//	for MAIN TAB Ends
	
	//ROHIT VERMA-SERVICE ATTRIBUTES STARTS//
	
	private String logicalSINo;
	
	private String custSINo;
	
	private String effStartDate;
	
	private String effEndDate;
	
	private String attRemarks;
	
	private String serviceID;
	//ROHIT VERMA-SERVICE ATTRIBUTES ENDS//
	
// for PO Details Tab Starts
	
	private String poDate;
	private String poDetailNo = "0";
	private String poReceiveDate;
	private String contractStartDate;
	private String contractEndDate;
	private String periodsInMonths;
	private String totalPoAmt;
	private String entity;
	private String poUpdateType; 
	private String defaultPO;
	private String poIssueBy;
	private String poEmailId;
	private String poRemarks;
	private String poDemoContractPeriod;
	private String selectedPODetails ;
	private int entityId;
		
	private int hdnPOCount;
	private int hdnPOnumbers;
	private String hdnServiceNo;
	//[008]
	private String ldCLause;
				
	
	private String serviceDetailID;
	
	private String parentServiceProductId = null;
	
	private String hdnServiceDetailID;
	
	private int hdnSeriveAttCounter;
	
	private String serviceTypeName;
	
	private String txtquotesNo;
	
	private String chkIsDemo;
	private String noOfDaysForDemo;
	//add by anil for editmode
	private String isOrderPublished;
	private String isBillingTriggerReady;
	private String searchTaskId;
	private String searchTaskOwner;
	private String searchTaskOwnerId;
	
	private int hdnSubchangeType;
	//[00044]start
	private String subChangeTypeId;
	//[00044] end
	private String custPoDetailNo; // Added by Saurabh for PO Details Tab
	private String projectManagerAssigned;
	
	//add by anil for fileAttachment
	private String fileName;
	private String fileRename;
	private String fileSource;
	private FormFile fileAttachment;
	private String isUpload;
	public String isShowAttachIcon;
	//end fileAttachment
	
	//[001]	Start 
	private String isDownload;
    private int sLNO;
    //[001] End 
    
    //[010] Start
    private int changeTypeID;
    //[010] End
    
    //Start[001]
    
    private int isUrgent;

    //End[001]
    
    
    //Meenakshi
    private String cancelServiceReason;
    
    private String orderStatusValue;
    

    private String selectGAMEmpid;
    //lawkush Start
    private String gam_name="";
    private int gam_id=0;
    private String isInView;
	private int hdnCreatedBy;
	
	private String fileType;
	private int fileTypeId;
	
	private ArrayList fileAttachmentTypeList;
	private String osp;
	
	 private String hdnOrderCreationSource="";
	 
	 private String circle="";//added on 09-jan-2013,CIRCLE Work

	 
	 private String category;
	 

	private String isOrderWorkflowRelaunch;
	//Meenakshi : Changes for Performance tunning activity
	private int isFormBlank;
	private String latestTab;
	private String newFormOpened;
	private String counterAttach;
	private String fileTypAttach;
	private String isCopcApproved; // added by Megha
	private int isDisplayRepushBtn;
	/*add a varible regarding LSI screen  */
	private String searchOrderStage;
	
	/* Vijay 
	 * create a variable for Demo order check 
	 */
	private String isDemoOrder;
	  private String toExcel;
	//[002] start
	private String cancelOrderReason;
	public String getCancelOrderReason() {
		return cancelOrderReason;
	}


	public void setCancelOrderReason(String cancelOrderReason) {
		this.cancelOrderReason = cancelOrderReason;
	}
	//[002] end
	//[004]
	private String cancelOrderReasonDD;
	
	public String getCancelOrderReasonDD() {
		return cancelOrderReasonDD;
	}

	public void setCancelOrderReasonDD(String cancelOrderReasonDD) {
		this.cancelOrderReasonDD = cancelOrderReasonDD;
	}
	public String getToExcel() {
		return toExcel;
	}


	public void setToExcel(String toExcel) {
		this.toExcel = toExcel;
	}


	public String getCounterAttach() {
		return counterAttach;
	}


	public void setCounterAttach(String counterAttach) {
		this.counterAttach = counterAttach;
	}


	public String getFileTypAttach() {
		return fileTypAttach;
	}


	public void setFileTypAttach(String fileTypAttach) {
		this.fileTypAttach = fileTypAttach;
	}


	public String getNewFormOpened() {
		return newFormOpened;
	}


	public void setNewFormOpened(String newFormOpened) {
		this.newFormOpened = newFormOpened;
	}
	private String leadNo;
	
	public String getLeadNo() {
		return leadNo;
	}


	public void setLeadNo(String leadNo) {
		this.leadNo = leadNo;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public String getIsOrderWorkflowRelaunch() {
		return isOrderWorkflowRelaunch;
	}


	public void setIsOrderWorkflowRelaunch(String isOrderWorkflowRelaunch) {
		this.isOrderWorkflowRelaunch = isOrderWorkflowRelaunch;
	}
	public String getOsp() {
		return osp;
	}


	public void setOsp(String osp) {
		this.osp = osp;
	}
	
    public ArrayList getFileAttachmentTypeList() {
		return fileAttachmentTypeList;
	}


	public void setFileAttachmentTypeList(ArrayList fileAttachmentTypeList) {
		this.fileAttachmentTypeList = fileAttachmentTypeList;
	}


	public String getFileType() {
		return fileType;
	}


	public void setFileType(String fileType) {
		this.fileType = fileType;
	}


	public int getFileTypeId() {
		return fileTypeId;
	}


	public void setFileTypeId(int fileTypeId) {
		this.fileTypeId = fileTypeId;
	}


	public int getHdnCreatedBy() {
		return hdnCreatedBy;
	}


	public void setHdnCreatedBy(int hdnCreatedBy) {
		this.hdnCreatedBy = hdnCreatedBy;
	}


	//lawkush End

    private String orderStageAnnotationName;
    private String collectionMgr;
    //Used for Bulkupload
    
	private String groupName;
    
    private String partyId;
    
    private int sentMethod;
    
	public int getSentMethod() {
		return sentMethod;
	}


	public void setSentMethod(int sentMethod) {
		this.sentMethod = sentMethod;
	}


	public String getPartyId() {
		return partyId;
	}


	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}
	//End
	public String getOrderStageAnnotationName() {
		return orderStageAnnotationName;
	}


	public void setOrderStageAnnotationName(String orderStageAnnotationName) {
		this.orderStageAnnotationName = orderStageAnnotationName;
	}


	public String getOrderStatusValue() {
		return orderStatusValue;
	}


	public void setOrderStatusValue(String orderStatusValue) {
		this.orderStatusValue = orderStatusValue;
	}


	public String getCancelServiceReason() {
		return cancelServiceReason;
	}


	public void setCancelServiceReason(String cancelServiceReason) {
		this.cancelServiceReason = cancelServiceReason;
	}

	//Meenakshi

	public String getCustPoDetailNo() {
		return custPoDetailNo;
	}


	public void setCustPoDetailNo(String custPoDetailNo) {
		this.custPoDetailNo = custPoDetailNo;
	}


	public String getCrmAccountNo() {
		return crmAccountNo;
	}


	public void setCrmAccountNo(String crmAccountNo) {
		this.crmAccountNo = crmAccountNo;
	}


	public int getHdnSubchangeType() {
		return hdnSubchangeType;
	}

	//[00044]Start
	public String getSubChangeTypeId() {
		return subChangeTypeId;
	}


	public void setSubChangeTypeId(String subChangeTypeId) {
		this.subChangeTypeId = subChangeTypeId;
	}
	//[00044]End

	public void setHdnSubchangeType(int hdnSubchangeType) {
		this.hdnSubchangeType = hdnSubchangeType;
	}


	public String getTxtquotesNo() {
		return txtquotesNo;
	}


	public void setTxtquotesNo(String txtquotesNo) {
		this.txtquotesNo = txtquotesNo;
	}


	public String getServiceTypeName() {
		return serviceTypeName;
	}


	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
	}


	public String getServiceDetailID() {
		return serviceDetailID;
	}


	public void setServiceDetailID(String serviceDetailID) {
		this.serviceDetailID = serviceDetailID;
	}


	public NewOrderBean()
	{
		attRemarks="";
		effStartDate="";
		logicalSINo="";
		effEndDate="";
	}
	
	
	public ArrayList getServiceSubType() {
		return serviceSubType;
	}

	public void setServiceSubType(ArrayList serviceSubType) {
		this.serviceSubType = serviceSubType;
	}

	public int getServiceSubTypeId() {
		return serviceSubTypeId;
	}

	public void setServiceSubTypeId(int serviceSubTypeId) {
		this.serviceSubTypeId = serviceSubTypeId;
	}

	public String getHdnOrderNo() {
		return hdnOrderNo;
	}

	public void setHdnOrderNo(String hdnOrderNo) {
		this.hdnOrderNo = hdnOrderNo;
	}

	public String getAccountID() {
		return accountID;
	}

	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}

	public String getAccountManager() {
		return accountManager;
	}

	public void setAccountManager(String accountManager) {
		this.accountManager = accountManager;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccphoneNo() {
		return accphoneNo;
	}

	public void setAccphoneNo(String accphoneNo) {
		this.accphoneNo = accphoneNo;
	}

	public String getCurrencyID() {
		return currencyID;
	}

	public void setCurrencyID(String currencyID) {
		this.currencyID = currencyID;
	}

	public String getCurShortCode() {
		return curShortCode;
	}

	public void setCurShortCode(String curShortCode) {
		this.curShortCode = curShortCode;
	}

	public String getLob() {
		return lob;
	}

	public void setLob(String lob) {
		this.lob = lob;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public String getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}

	public String getQuoteNo() {
		return quoteNo;
	}

	public void setQuoteNo(String quoteNo) {
		this.quoteNo = quoteNo;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getSpFirstname() {
		return spFirstname;
	}

	public void setSpFirstname(String spFirstname) {
		this.spFirstname = spFirstname;
	}

	public String getSpLastName() {
		return spLastName;
	}

	public void setSpLastName(String spLastName) {
		this.spLastName = spLastName;
	}

	public String getSpLEmail() {
		return spLEmail;
	}

	public void setSpLEmail(String spLEmail) {
		this.spLEmail = spLEmail;
	}

	public String getSpLPhno() {
		return spLPhno;
	}

	public void setSpLPhno(String spLPhno) {
		this.spLPhno = spLPhno;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getAttributeLabel() {
		return attributeLabel;
	}

	public void setAttributeLabel(String attributeLabel) {
		this.attributeLabel = attributeLabel;
	}

	public String getAttributeValue() {
		return attributeValue;
	}

	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}

	public String getAlisName() {
		return alisName;
	}

	public void setAlisName(String alisName) {
		this.alisName = alisName;
	}

	public String getAttributeID() {
		return attributeID;
	}

	public void setAttributeID(String attributeID) {
		this.attributeID = attributeID;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getAttMaxLength() {
		return attMaxLength;
	}

	public void setAttMaxLength(String attMaxLength) {
		this.attMaxLength = attMaxLength;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public int getAttCount() {
		return attCount;
	}

	public void setAttCount(int attCount) {
		this.attCount = attCount;
	}

	public String getUpdateType() {
		return updateType;
	}

	public void setUpdateType(String updateType) {
		this.updateType = updateType;
	}

	public String getExpectedValue() {
		return expectedValue;
	}

	public void setExpectedValue(String expectedValue) {
		this.expectedValue = expectedValue;
	}

	public String getSearchAccount() {
		return searchAccount;
	}
		public int getHdnServiceCounter() {
			return hdnServiceCounter;
	}
	public void setHdnServiceCounter(int hdnServiceCounter) {
		this.hdnServiceCounter = hdnServiceCounter;
	}
	public void setSearchAccount(String searchAccount) {
		this.searchAccount = searchAccount;
	}
		public String getContactUpdateType() {
		return contactUpdateType;
	}

	public void setContactUpdateType(String contactUpdateType) {
		this.contactUpdateType = contactUpdateType;
	}

	public long getAddID() {
		return addID;
	}

	public void setAddID(long addID) {
		this.addID = addID;
	}

	public Long getContactId() {
		return contactId;
	}

	public void setContactId(long contactId) {
		this.contactId = contactId;
	}

	public String getAttRemarks() {
		return attRemarks;
	}

	public void setAttRemarks(String attRemarks) {
		this.attRemarks = attRemarks;
	}

	public String getCustSINo() {
		return custSINo;
	}

	public void setCustSINo(String custSINo) {
		this.custSINo = custSINo;
	}

	public String getEffEndDate() {
		return effEndDate;
	}

	public void setEffEndDate(String effEndDate) {
		this.effEndDate = effEndDate;
	}

	public String getEffStartDate() {
		return effStartDate;
	}

	public void setEffStartDate(String effStartDate) {
		this.effStartDate = effStartDate;
	}

	public String getLogicalSINo() {
		return logicalSINo;
	}

	public void setLogicalSINo(String logicalSINo) {
		this.logicalSINo = logicalSINo;
	}

	public String getServiceID() {
		return serviceID;
	}

	public void setServiceID(String serviceID) {
		this.serviceID = serviceID;
	}


	public String getContractEndDate() {
		return contractEndDate;
	}


	public void setContractEndDate(String contractEndDate) {
		this.contractEndDate = contractEndDate;
	}


	public String getContractStartDate() {
		return contractStartDate;
	}


	public void setContractStartDate(String contractStartDate) {
		this.contractStartDate = contractStartDate;
	}


	public String getEntity() {
		return entity;
	}


	public void setEntity(String entity) {
		this.entity = entity;
	}


	public int getHdnPOCount() {
		return hdnPOCount;
	}


	public void setHdnPOCount(int hdnPOCount) {
		this.hdnPOCount = hdnPOCount;
	}


	public int getHdnPOnumbers() {
		return hdnPOnumbers;
	}


	public void setHdnPOnumbers(int hdnPOnumbers) {
		this.hdnPOnumbers = hdnPOnumbers;
	}


	public String getPeriodsInMonths() {
		return periodsInMonths;
	}


	public void setPeriodsInMonths(String periodsInMonths) {
		this.periodsInMonths = periodsInMonths;
	}


	public String getPoDate() {
		return poDate;
	}


	public void setPoDate(String poDate) {
		this.poDate = poDate;
	}


	public String getPoDetailNo() {
		return poDetailNo;
	}


	public void setPoDetailNo(String poDetailNo) {
		if(poDetailNo.equals("")){
			this.poDetailNo = "0";
		} else {
			this.poDetailNo = poDetailNo;
		}
	}


	public String getPoReceiveDate() {
		return poReceiveDate;
	}


	public void setPoReceiveDate(String poReceiveDate) {
		this.poReceiveDate = poReceiveDate;
	}


	public String getPoUpdateType() {
		return poUpdateType;
	}


	public void setPoUpdateType(String poUpdateType) {
		this.poUpdateType = poUpdateType;
	}


	public String getTotalPoAmt() {
		return totalPoAmt;
	}


	public void setTotalPoAmt(String totalPoAmt) {
		this.totalPoAmt = totalPoAmt;
	}


	public String getHdnServiceDetailID() {
		return hdnServiceDetailID;
	}


	public void setHdnServiceDetailID(String hdnServiceDetailID) {
		this.hdnServiceDetailID = hdnServiceDetailID;
	}


	public int getHdnSeriveAttCounter() {
		return hdnSeriveAttCounter;
	}


	public void setHdnSeriveAttCounter(int hdnSeriveAttCounter) {
		this.hdnSeriveAttCounter = hdnSeriveAttCounter;
	}
	
		public String getHdnServiceNo() {
		return hdnServiceNo;
	}


	public void setHdnServiceNo(String hdnServiceNo) {
		this.hdnServiceNo = hdnServiceNo;
	}


	public String getAddress1() {
		return address1;
	}


	public void setAddress1(String address1) {
		this.address1 = address1;
	}


	public String getAddress2() {
		return address2;
	}


	public void setAddress2(String address2) {
		this.address2 = address2;
	}


	public String getAddress3() {
		return address3;
	}


	public void setAddress3(String address3) {
		this.address3 = address3;
	}


	public String getCityName() {
		return cityName;
	}


	public void setCityName(String cityName) {
		this.cityName = cityName;
	}


	public String getCntEmail() {
		return cntEmail;
	}


	public void setCntEmail(String cntEmail) {
		this.cntEmail = cntEmail;
	}


	public String getContactCell() {
		return contactCell;
	}


	public void setContactCell(String contactCell) {
		this.contactCell = contactCell;
	}


	public String getContactFax() {
		return contactFax;
	}


	public void setContactFax(String contactFax) {
		this.contactFax = contactFax;
	}


	public String getContactType() {
		return contactType;
	}


	public void setContactType(String contactType) {
		this.contactType = contactType;
	}


	

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}


	public void setMandatory(String mandatory) {
		this.mandatory = mandatory;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getPinNo() {
		return pinNo;
	}


	public void setPinNo(String pinNo) {
		this.pinNo = pinNo;
	}


	public String getSaluation() {
		return saluation;
	}


	public void setSaluation(String saluation) {
		this.saluation = saluation;
	}


	public String getStateName() {
		return stateName;
	}


	public void setStateName(String stateName) {
		this.stateName = stateName;
	}


//	public void setAddID(Long addID) {
//		this.addID = addID;
//	}


	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}


	public String getMandatory() {
		return mandatory;
	}


	/**
	 * @return the serviceName
	 */
	public String getServiceName() {
		return serviceName;
	}


	/**
	 * @param serviceName the serviceName to set
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}


	/**
	 * @return the changeType
	 */
	public String getChangeType() {
		return changeType;
	}


	/**
	 * @param changeType the changeType to set
	 */
	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}
	
	public String getParentServiceProductId() {
		return parentServiceProductId;
	}


	public void setParentServiceProductId(String parentServiceProductId) {
		this.parentServiceProductId = parentServiceProductId;
	}
	public HSSFWorkbook getProductCatelogTemplateWorkbook() {
		return productCatelogTemplateWorkbook;
	}


	public void setProductCatelogTemplateWorkbook(
			HSSFWorkbook productCatelogTemplateWorkbook) {
		this.productCatelogTemplateWorkbook = productCatelogTemplateWorkbook;
	}


	public String[] getHdnserviceDetailId() {
		return hdnserviceDetailId;
	}


	public void setHdnserviceDetailId(String[] hdnserviceDetailId) {
		this.hdnserviceDetailId = hdnserviceDetailId;
	}


	public String[] getChk_spId() {
		return chk_spId;
	}


	public void setChk_spId(String[] chk_spId) {
		this.chk_spId = chk_spId;
	}


	public String getHdnSelectedServiceDetailId() {
		return hdnSelectedServiceDetailId;
	}


	public void setHdnSelectedServiceDetailId(String hdnSelectedServiceDetailId) {
		this.hdnSelectedServiceDetailId = hdnSelectedServiceDetailId;
	}	
	HSSFWorkbook excelWorkbookFormaster = null;
	private long productID;
	FormFile uploadedFile = null;
	int loadExcelProductConfig_status;
	

	public FormFile getUploadedFile() {
		return uploadedFile;
	}


	public void setUploadedFile(FormFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}


	public long getProductID() {
		return productID;
	}


	public void setProductID(long productID) {
		this.productID = productID;
	}


	public HSSFWorkbook getExcelWorkbookFormaster() {
		return excelWorkbookFormaster;
	}


	public void setExcelWorkbookFormaster(HSSFWorkbook excelWorkbookFormaster) {
		this.excelWorkbookFormaster = excelWorkbookFormaster;
	}


	public int getLoadExcelProductConfig_status() {
		return loadExcelProductConfig_status;
	}


	public void setLoadExcelProductConfig_status(int loadExcelProductConfig_status) {
		this.loadExcelProductConfig_status = loadExcelProductConfig_status;
	}


	public String getChangeRate() {
		return changeRate;
	}


	public void setChangeRate(String changeRate) {
		this.changeRate = changeRate;
	}
	public String getRegionId() {
		return regionId;
	}


	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}


	public String getZoneId() {
		return zoneId;
	}


	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}
	public String getDefaultPO() {
		return defaultPO;
	}


	public void setDefaultPO(String defaultPO) {
		this.defaultPO = defaultPO;
	}


	public String getPoDemoContractPeriod() {
		return poDemoContractPeriod;
	}


	public void setPoDemoContractPeriod(String poDemoContractPeriod) {
		this.poDemoContractPeriod = poDemoContractPeriod;
	}


	public String getPoEmailId() {
		return poEmailId;
	}


	public void setPoEmailId(String poEmailId) {
		this.poEmailId = poEmailId;
	}


	public String getPoIssueBy() {
		return poIssueBy;
	}


	public void setPoIssueBy(String poIssueBy) {
		this.poIssueBy = poIssueBy;
	}


	public String getPoRemarks() {
		return poRemarks;
	}


	public void setPoRemarks(String poRemarks) {
		this.poRemarks = poRemarks;
	}


	public String getSelectedPODetails() {
		return selectedPODetails;
	}


	public void setSelectedPODetails(String selectedPODetails) {
		this.selectedPODetails = selectedPODetails;
	}


	public int getEntityId() {
		return entityId;
	}


	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}
	
		String hdnShowTree;

	public String getHdnShowTree() {
		return hdnShowTree;
	}


	public void setHdnShowTree(String hdnShowTree) {
		this.hdnShowTree = hdnShowTree;
	}


	public String getChkIsDemo() {
		return chkIsDemo;
	}


	public void setChkIsDemo(String chkIsDemo) {
		this.chkIsDemo = chkIsDemo;
	}


	public String getSalutationId() {
		return salutationId;
	}


	public void setSalutationId(String salutationId) {
		this.salutationId = salutationId;
	}


	public int getCityCode() {
		return cityCode;
	}


	public void setCityCode(int cityCode) {
		this.cityCode = cityCode;
	}
	public int getCountyCode() {
		return countyCode;
	}


	public void setCountyCode(int countyCode) {
		this.countyCode = countyCode;
	}
	public int getStateCode() {
		return stateCode;
	}


	public void setStateCode(int stateCode) {
		this.stateCode = stateCode;
	}







	public String getNoOfDaysForDemo() {
		return noOfDaysForDemo;
	}


	public void setNoOfDaysForDemo(String noOfDaysForDemo) {
		this.noOfDaysForDemo = noOfDaysForDemo;
	}


	public String getSalutationName() {
		return salutationName;
	}


	public void setSalutationName(String salutationName) {
		this.salutationName = salutationName;
	}


	public String getIsBillingTriggerReady() {
		return isBillingTriggerReady;
	}


	public void setIsBillingTriggerReady(String isBillingTriggerReady) {
		this.isBillingTriggerReady = isBillingTriggerReady;
	}


	public String getIsOrderPublished() {
		return isOrderPublished;
	}


	public void setIsOrderPublished(String isOrderPublished) {
		this.isOrderPublished = isOrderPublished;
	}


	public String getSearchTaskId() {
		return searchTaskId;
	}


	public void setSearchTaskId(String searchTaskId) {
		this.searchTaskId = searchTaskId;
	}


	public String getSearchTaskOwner() {
		return searchTaskOwner;
	}


	public void setSearchTaskOwner(String searchTaskOwner) {
		this.searchTaskOwner = searchTaskOwner;
	}


	public String getSearchTaskOwnerId() {
		return searchTaskOwnerId;
	}


	public void setSearchTaskOwnerId(String searchTaskOwnerId) {
		this.searchTaskOwnerId = searchTaskOwnerId;
	}


	public String getSPIDUrlforVPC() {
		return SPIDUrlforVPC;
	}


	public void setSPIDUrlforVPC(String urlforVPC) {
		SPIDUrlforVPC = urlforVPC;
	}


	public String getProjectManagerAssigned() {
		return projectManagerAssigned;
	}


	public void setProjectManagerAssigned(String projectManagerAssigned) {
		this.projectManagerAssigned = projectManagerAssigned;
	}


	public FormFile getFileAttachment() {
		return fileAttachment;
	}


	public void setFileAttachment(FormFile fileAttachment) {
		this.fileAttachment = fileAttachment;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public String getFileSource() {
		return fileSource;
	}


	public void setFileSource(String fileSource) {
		this.fileSource = fileSource;
	}


	public String getIsUpload() {
		return isUpload;
	}


	public void setIsUpload(String isUpload) {
		this.isUpload = isUpload;
	}


	public String getIsShowAttachIcon() {
		return isShowAttachIcon;
	}


	public void setIsShowAttachIcon(String isShowAttachIcon) {
		this.isShowAttachIcon = isShowAttachIcon;
	}


	public String getFileRename() {
		return fileRename;
	}


	public void setFileRename(String fileRename) {
		this.fileRename = fileRename;
	}


	public String getSearchAccountNo() {
		return searchAccountNo;
	}


	public void setSearchAccountNo(String searchAccountNo) {
		this.searchAccountNo = searchAccountNo;
	}


	public String getSearchCRMOrder() {
		return searchCRMOrder;
	}


	public void setSearchCRMOrder(String searchCRMOrder) {
		this.searchCRMOrder = searchCRMOrder;
	}


	public String getSearchCurrency() {
		return searchCurrency;
	}


	public void setSearchCurrency(String searchCurrency) {
		this.searchCurrency = searchCurrency;
	}


	public String getSearchfromDate() {
		return searchfromDate;
	}


	public void setSearchfromDate(String searchfromDate) {
		this.searchfromDate = searchfromDate;
	}


	public String getSearchOrderType() {
		return searchOrderType;
	}


	public void setSearchOrderType(String searchOrderType) {
		this.searchOrderType = searchOrderType;
	}


	public String getSearchQuoteNumber() {
		return searchQuoteNumber;
	}


	public void setSearchQuoteNumber(String searchQuoteNumber) {
		this.searchQuoteNumber = searchQuoteNumber;
	}


	public String getSearchSourceName() {
		return searchSourceName;
	}


	public void setSearchSourceName(String searchSourceName) {
		this.searchSourceName = searchSourceName;
	}


	public String getSearchStageName() {
		return searchStageName;
	}


	public void setSearchStageName(String searchStageName) {
		this.searchStageName = searchStageName;
	}


	public String getSearchToDate() {
		return searchToDate;
	}


	public void setSearchToDate(String searchToDate) {
		this.searchToDate = searchToDate;
	}


	public String getSearchSource() {
		return searchSource;
	}


	public void setSearchSource(String searchSource) {
		this.searchSource = searchSource;
	}


	public String getSearchCurrencyName() {
		return searchCurrencyName;
	}


	public void setSearchCurrencyName(String searchCurrencyName) {
		this.searchCurrencyName = searchCurrencyName;
	}


	public String getSearchAccountName() {
		return searchAccountName;
	}


	public void setSearchAccountName(String searchAccountName) {
		this.searchAccountName = searchAccountName;
	}


	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}


	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
	}


	public ArrayList getOrderList() {
		return orderList;
	}


	public void setOrderList(ArrayList orderList) {
		this.orderList = orderList;
	}

	public String getIsDownload() {
		return isDownload;
	}


	public void setIsDownload(String isDownload) {
		this.isDownload = isDownload;
	}


	public int getSLNO() {
		return sLNO;
	}


	public void setSLNO(int slno) {
		sLNO = slno;
	}

	public String getNewType() {
		return newType;
	}


	public void setNewType(String newType) 
	{
		this.newType = newType;
	}


	public int getChangeTypeID() {
		return changeTypeID;
	}


	public void setChangeTypeID(int changeTypeID) {
		this.changeTypeID = changeTypeID;
	}

//Start[001]
	public int getIsUrgent() {
		return isUrgent;
	}


	public void setIsUrgent(int isUrgent) {
		this.isUrgent = isUrgent;
	}
//End[001]

		public String getStageName() {
			if(stageName==null)
			{
				stageName="";
			}
		return stageName;
	}


	public void setStageName(String stageName) {
		this.stageName = stageName;
	}


	public String getAttribute_2() {
		return attribute_2;
	}


	public void setAttribute_2(String attribute_2) {
		this.attribute_2 = attribute_2;
	}


	public String getAttribute_3() {
		return attribute_3;
	}


	public void setAttribute_3(String attribute_3) {
		this.attribute_3 = attribute_3;
	}


	public String getProjectManagerID() {
		return projectManagerID;
	}


	public void setProjectManagerID(String projectManagerID) {
		this.projectManagerID = projectManagerID;
	}


	public String getSelectGAMEmpid() {
		return selectGAMEmpid;
	}


	public void setSelectGAMEmpid(String selectGAMEmpid) {
		this.selectGAMEmpid = selectGAMEmpid;
	}


	public int getGam_id() {
		return gam_id;
	}


	public void setGam_id(int gam_id) {
		this.gam_id = gam_id;
	}


	public String getGam_name() {
		return gam_name;
	}

	public void setGam_name(String gam_name) {
		this.gam_name = gam_name;
	}

	public String getIsInView() {
		return isInView;
	}

	public void setIsInView(String isInView) {
		this.isInView = isInView;
	}

	public String getOpportunityId() {
		return opportunityId;
	}

	public void setOpportunityId(String opportunityId) {
		this.opportunityId = opportunityId;
	}

	public String getCollectionMgr() {
		return collectionMgr;
	}

	public void setCollectionMgr(String collectionMgr) {
		this.collectionMgr = collectionMgr;
	}

	public String getHdnOrderCreationSource() {
		return hdnOrderCreationSource;
	}

	public void setHdnOrderCreationSource(String hdnOrderCreationSource) {
		this.hdnOrderCreationSource = hdnOrderCreationSource;
	}

	public String getCircle() {
		return circle;
	}

	public void setCircle(String circle) {
		this.circle = circle;
	}

	public int getIsFormBlank() {
		return isFormBlank;
	}

	public void setIsFormBlank(int isFormBlank) {
		this.isFormBlank = isFormBlank;
	}

	public String getLatestTab() {
		return latestTab;
	}

	public void setLatestTab(String latestTab) {
		this.latestTab = latestTab;
	}

	public String getInterfaceName() {
		return interfaceName;
	}


	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}


	public String getIsDemoOrder() {
		return isDemoOrder;
	}


	public void setIsDemoOrder(String isDemoOrder) {
		this.isDemoOrder = isDemoOrder;
	}


//vijay start
	public String getSearchOrderStage() {
		return searchOrderStage;
	}


	public void setSearchOrderStage(String searchOrderStage) {
		this.searchOrderStage = searchOrderStage;
	}
//	vijay end	
// added by Megha to enable sendToCopc
	public String getIsCopcApproved() {
		return isCopcApproved;
	}


	public void setIsCopcApproved(String isCopcApproved) {
		this.isCopcApproved = isCopcApproved;
	}
  //Megha
	public String getGroupName() {
		return groupName;
	}


	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
		//[003] Start
	public int getIsDisplayRepushBtn() {
		return isDisplayRepushBtn;
	}
	public void setIsDisplayRepushBtn(int isDisplayRepushBtn) {
		this.isDisplayRepushBtn = isDisplayRepushBtn;
	}


	public String getOrderCreationSourceName() {
		return orderCreationSourceName;
	}


	public void setOrderCreationSourceName(String orderCreationSourceName) {
		this.orderCreationSourceName = orderCreationSourceName;
	}
	//[005] Start
	public String getServiceSegment() {
		return serviceSegment;
	}


	public void setServiceSegment(String serviceSegment) {
		this.serviceSegment = serviceSegment;
	}
    //[005] End


	public String getCustSegment() {
		return custSegment;
	}


	public void setCustSegment(String custSegment) {
		this.custSegment = custSegment;
	}
	//[007] Start Project Satyapan
	public int getIspTagging() {
		return ispTagging;
	}
	public void setIspTagging(int ispTagging) {
		this.ispTagging = ispTagging;
	}
	public int getIspLicCtgry() {
		return ispLicCtgry;
	}
	public void setIspLicCtgry(int ispLicCtgry) {
		this.ispLicCtgry = ispLicCtgry;
	}
	public String getIspLicDate() {
		return ispLicDate;
	}
	public void setIspLicDate(String ispLicDate) {
		this.ispLicDate = ispLicDate;
	}
	public String getIspLicNo() {
		return ispLicNo;
	}
	public void setIspLicNo(String ispLicNo) {
		this.ispLicNo = ispLicNo;
	}
	//[007] End Project Satyapan

	//[008] starts
	public String getLdCLause() {
		return ldCLause;
	}


	public void setLdCLause(String ldCLause) {
		this.ldCLause = ldCLause;
	}




/*
	public String getChannelPartnerCtgry() {
		return channelPartnerCtgry;
	}


	public void setChannelPartnerCtgry(String channelPartnerCtgry) {
		this.channelPartnerCtgry = channelPartnerCtgry;
	}
*/

	public String getChannelMasterTagging() {
		return channelMasterTagging;
	}


	public void setChannelMasterTagging(String channelMasterTagging) {
		this.channelMasterTagging = channelMasterTagging;
	}





	public String getIsActive() {
		return isActive;
	}


	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	public long getFieldEngineerId() {
		return fieldEngineerId;
	}
	public void setFieldEngineerId(long fieldEngineerId) {
		this.fieldEngineerId = fieldEngineerId;
	}
	public String getFieldEngineer() {
		return fieldEngineer;
	}
	public void setFieldEngineer(String fieldEngineer) {
		this.fieldEngineer = fieldEngineer;
	}
	
	


	
	//[008] ends 
}
