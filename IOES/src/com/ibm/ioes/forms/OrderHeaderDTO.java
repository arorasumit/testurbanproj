//[0001]   Neha Maheshwari    added service segment    NORTH STAR ACCOUNT TAGGING
//[002] VIPIN SAHARIA 11-May-2015 20150403-R2-021204 Project Satyapan Adding ISP tagging fields to header section
//[003] Raveendra 06-july-2015 20141219-R2-020936-Billing Efficiency Program_drop2  Auto Billing Trigger
//[004] Rahul Tandon  22/jan/2016   20150508-R2-021322 SalesForce Opportunity number in iB2B

package com.ibm.ioes.forms;

import java.util.ArrayList;

public class OrderHeaderDTO extends IOESPagingDto {
	
	
	public final String ORDER_TYPE_CHANGE= "CHANGE";
	public final int CHANGE_TYPE_RATERENEWAL= 5;
	public final int CHANGE_TYPE_DISCONNECTION= 3;
	//Vijay add variable for demo regularize
	public final int CHANGE_TYPE_DEMO= 4;
	public final int SUB_CHANGE_TYPE_REGULARIZE= 12;
	//Vijay end
	
	private String accountName;
	private int accountID;
	private String lob;
	//[0001] Start
	private String serviceSegment;
    //[0001] End
	private int crmAccountId;//Added By Ashutosh
	private String accountManager;
	private String projectManager;
	private String spFirstname;
	private String spLastName;
	private String acmgrPhno;//changed by kalpana from long to string for bug id HYPR11042013001
	private String spLPhno;//changed by kalpana from long to string for bug id HYPR11042013001
	private String spLEmail;
	public String getChannelPartnerCode() {
		return channelPartnerCode;
	}

	public void setChannelPartnerCode(String channelPartnerCode) {
		this.channelPartnerCode = channelPartnerCode;
	}

	public String getFieldEngineer() {
		return fieldEngineer;
	}

	public void setFieldEngineer(String fieldEngineer) {
		this.fieldEngineer = fieldEngineer;
	}

	public long getFieldEngineerId() {
		return fieldEngineerId;
	}

	public void setFieldEngineerId(long fieldEngineerId) {
		this.fieldEngineerId = fieldEngineerId;
	}

	public void setChannelPartnerCtgry(long channelPartnerCtgry) {
		this.channelPartnerCtgry = channelPartnerCtgry;
	}
	private int regionId;
	private int zoneId;
	private String region=" ";
	private String m6ShortCode;
	private String acmgrEmail;
	private long accphoneNo;
	private String zone;
	private String searchAccount;
	private String orderType;
	private String quoteNo;
	private String orderNo;
	private String orderDate;
	private int status=0;
	private String osp;
	private String regionIdNew;
	private String collectionMgr;
	private long projectManagerID;
	private String zoneName;
	private int PARTNER_ID;
	private String PARTNER_NAME;

	private String opportunityId;
	private int crmAccountNo;	
	private String sourceName;
	private String txtquotesNo;
	private String currencyCode;
	private String curShortCode;
	private int currencyID;
	private int poNumber;
	private String stageName;
	private String orderStatusValue;
	private int changeTypeId=0;
	private String changeTypeName;
	private int subChangeTypeId;
	private String subChangeTypeName;
	private String chkIsDemo;
	private String noOfDaysForDemo;
	private int isUrgent;
	private int isPMPresent;
	private String orderStageAnnotationName;
	private int hdnCreatedBy;
	private long contactCount=0;
	private long poCount = 0;
	private String order_creation_source="";
	private String searchCurrencyName;
	private String regionName;
	private String searchSourceName;
	private int orderNumber;
	private String createdBy;
	private String mode = null;
	private String changeType;
	private String fileIds;
	private String docStatus;
	private String roleId;
	private int fileId;
	private String selectDocStatus;
	private String finalTracking;
	private String fileName;
	private String roleName;
	private int isLastTask;
	private int noOfServiceInOrder; 
	
	
	private String orderInfo_OrderType = null;
	private Integer orderInfo_ChangeType = null;
	private Integer orderInfo_ChangeSubType = null;
	
	private long published_by_empid;
	private long published_by_roleid;
	
	
	private String ib2bWorkflowAttachedId;
	private String ib2bWorkflowAttachedName;
	private String projectWorkflowId;
	private String changeorderstatus;
	//Added by Ashutosh Migrated order
	private String orderCreationSourceName;
	
	private String order_type;	//by Saurabh
	
	private String circle="";//added on 09-jan-2013,CIRCLE Work
	
	private String category;
	
	//lawkush Start
	
	private String userID = null;
	private String userName = null;
	
	//[002] Start Project Satyapan
	private int ispTagging;
	private int ispLicCtgry;
	private String ispLicDate;
	private String ispLicNo;
	//[002] End Project Satyapan
	//Start [003]
	String permanentDisconnectionSingleThenBulkApproval;
	boolean singleApprovalMode;
	
	
	
	public String getPermanentDisconnectionSingleThenBulkApproval() {
		return permanentDisconnectionSingleThenBulkApproval;
	}

	public void setPermanentDisconnectionSingleThenBulkApproval(
			String permanentDisconnectionSingleThenBulkApproval) {
		this.permanentDisconnectionSingleThenBulkApproval = permanentDisconnectionSingleThenBulkApproval;
	}
	

	public boolean isSingleApprovalMode() {
		return singleApprovalMode;
	}

	public void setSingleApprovalMode(Boolean singleApprovalMode) {
		this.singleApprovalMode = singleApprovalMode;
	}
    //end [003]
	
	//[004] starts
	private String channelMasterTagging;
	private long channelPartnerCtgry;
	private String channelPartnerCtgryName;
	private String channelPartnerCode;
	private String fieldEngineer="";
	private long fieldEngineerId;
	private long channelPartnerId;
	private String channelpartnerCode;
	private String channelPartnerName;
	  
	public long getChannelPartnerId() {
		return channelPartnerId;
	}

	public void setChannelPartnerId(long channelPartnerId) {
		this.channelPartnerId = channelPartnerId;
	}

	public String getChannelpartnerCode() {
		return channelpartnerCode;
	}

	public void setChannelpartnerCode(String channelpartnerCode) {
		this.channelpartnerCode = channelpartnerCode;
	}

	public String getChannelPartnerName() {
		return channelPartnerName;
	}

	public void setChannelPartnerName(String channelPartnerName) {
		this.channelPartnerName = channelPartnerName;
	}

	public long getChannelPartnerCtgry() {
		return channelPartnerCtgry;
	  }
	//[004] ends
	
	
	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	//lawkush End
	private String groupName;
	

	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}

	public String getCircle() {
		return circle;
	}


	public void setCircle(String circle) {
		this.circle = circle;
	}


	public String getOrder_type() {
		return order_type;
	}


	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}


	public int getIsLastTask() {
		return isLastTask;
	}


	public void setIsLastTask(int isLastTask) {
		this.isLastTask = isLastTask;
	}


	public long getPublished_by_empid() {
		return published_by_empid;
	}


	public void setPublished_by_empid(long published_by_empid) {
		this.published_by_empid = published_by_empid;
	}


	public long getPublished_by_roleid() {
		return published_by_roleid;
	}


	public void setPublished_by_roleid(long published_by_roleid) {
		this.published_by_roleid = published_by_roleid;
	}


	public Integer getOrderInfo_ChangeSubType() {
		return orderInfo_ChangeSubType;
	}


	public void setOrderInfo_ChangeSubType(Integer orderInfo_ChangeSubType) {
		this.orderInfo_ChangeSubType = orderInfo_ChangeSubType;
	}


	public Integer getOrderInfo_ChangeType() {
		return orderInfo_ChangeType;
	}


	public void setOrderInfo_ChangeType(Integer orderInfo_ChangeType) {
		this.orderInfo_ChangeType = orderInfo_ChangeType;
	}


	public String getOrderInfo_OrderType() {
		return orderInfo_OrderType;
	}


	public void setOrderInfo_OrderType(String orderInfo_OrderType) {
		this.orderInfo_OrderType = orderInfo_OrderType;
	}


	public String getRoleName() {
		return roleName;
	}


	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFinalTracking() {
		return finalTracking;
	}


	public void setFinalTracking(String finalTracking) {
		this.finalTracking = finalTracking;
	}
	public String getSelectDocStatus() {
		return selectDocStatus;
	}


	public void setSelectDocStatus(String selectDocStatus) {
		this.selectDocStatus = selectDocStatus;
	}

	public int getFileId() {
		return fileId;
	}


	public void setFileId(int fileId) {
		this.fileId = fileId;
	}
	public String getRoleId() {
		return roleId;
	}


	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getDocStatus() {
		return docStatus;
	}


	public void setDocStatus(String docStatus) {
		this.docStatus = docStatus;
	}
	public String getFileIds() {
		return fileIds;
	}


	public void setFileIds(String fileIds) {
		this.fileIds = fileIds;
	}
	public String getChangeType() {
		return changeType;
	}

	/**
	 * @param changeType the changeType to set
	 */
	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getSearchSourceName() {
		return searchSourceName;
	}


	public void setSearchSourceName(String searchSourceName) {
		this.searchSourceName = searchSourceName;
	}
	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getSearchCurrencyName() {
		return searchCurrencyName;
	}


	public void setSearchCurrencyName(String searchCurrencyName) {
		this.searchCurrencyName = searchCurrencyName;
	}
	public String getOrder_creation_source() {
		return order_creation_source;
	}


	public void setOrder_creation_source(String order_creation_source) {
		this.order_creation_source = order_creation_source;
	}
	public long getPoCount() {
		return poCount;
	}


	public void setPoCount(long poCount) {
		this.poCount = poCount;
	}
	public long getContactCount() {
		return contactCount;
	}


	public void setContactCount(long contactCount) {
		this.contactCount = contactCount;
	}
	public int getHdnCreatedBy() {
		return hdnCreatedBy;
	}


	public void setHdnCreatedBy(int hdnCreatedBy) {
		this.hdnCreatedBy = hdnCreatedBy;
	}
	public String getOrderStageAnnotationName() {
		return orderStageAnnotationName;
	}
	public void setOrderStageAnnotationName(String orderStageAnnotationName) {
		this.orderStageAnnotationName = orderStageAnnotationName;
	}
	public int getIsPMPresent() {
		return isPMPresent;
	}
	public void setIsPMPresent(int isPMPresent) {
		this.isPMPresent = isPMPresent;
	}
	public int getIsUrgent() {
		return isUrgent;
	}
	public void setIsUrgent(int isUrgent) {
		this.isUrgent = isUrgent;
	}
	public String getNoOfDaysForDemo() {
		return noOfDaysForDemo;
	}


	public void setNoOfDaysForDemo(String noOfDaysForDemo) {
		this.noOfDaysForDemo = noOfDaysForDemo;
	}
	public String getChkIsDemo() {
		return chkIsDemo;
	}


	public void setChkIsDemo(String chkIsDemo) {
		this.chkIsDemo = chkIsDemo;
	}

	public String getSubChangeTypeName() {
		return subChangeTypeName;
	}

	public void setSubChangeTypeName(String subChangeTypeName) {
		this.subChangeTypeName = subChangeTypeName;
	}
	public int getSubChangeTypeId() {
		return subChangeTypeId;
	}

	public void setSubChangeTypeId(int subChangeTypeId) {
		this.subChangeTypeId = subChangeTypeId;
	}
	public String getChangeTypeName() {
		return changeTypeName;
	}

	/**
	 * @param changeTypeName the changeTypeName to set
	 */
	public void setChangeTypeName(String changeTypeName) {
		this.changeTypeName = changeTypeName;
	}
	public int getChangeTypeId() {
		return changeTypeId;
	}

	public void setChangeTypeId(int changeTypeId) {
		this.changeTypeId = changeTypeId;
	}
	public void setOrderStatusValue(String orderStatusValue) {
		this.orderStatusValue = orderStatusValue;
	}
	public String getOrderStatusValue() {
		return orderStatusValue;
	}
	public String getStageName() {
		return stageName;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
	}
	public int getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(int poNumber) {
		this.poNumber = poNumber;
	}
	public int getCurrencyID() {
		return currencyID;
	}

	public void setCurrencyID(int currencyID) {
		this.currencyID = currencyID;
	}
	 public String getCurShortCode() {
			return curShortCode;
		}

		public void setCurShortCode(String curShortCode) {
			this.curShortCode = curShortCode;
		}
	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getTxtquotesNo() {
		return txtquotesNo;
	}


	public void setTxtquotesNo(String txtquotesNo) {
		this.txtquotesNo = txtquotesNo;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	public int getCrmAccountNo() {
		return crmAccountNo;
	}
	public void setCrmAccountNo(int crmAccountNo) {
		this.crmAccountNo = crmAccountNo;
	}
	public String getOpportunityId() {
		return opportunityId;
	}


	public void setOpportunityId(String opportunityId) {
		this.opportunityId = opportunityId;
	}
	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	public long getProjectManagerID() {
		return projectManagerID;
	}

	public void setProjectManagerID(long projectManagerID) {
		this.projectManagerID = projectManagerID;
	}
	public void setCollectionMgr(String collectionMgr) {
		this.collectionMgr = collectionMgr;
	}
	public String getCollectionMgr() {
		return collectionMgr;
	}

	public String getRegionIdNew() {
		return regionIdNew;
	}


	public void setRegionIdNew(String regionIdNew) {
		this.regionIdNew = regionIdNew;
	}

	public String getOsp() {
		return osp;
	}


	public void setOsp(String osp) {
		this.osp = osp;
	}
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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
	public String getQuoteNo() {
		return quoteNo;
	}

	public void setQuoteNo(String quoteNo) {
		this.quoteNo = quoteNo;
	}
	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getSearchAccount() {
		return searchAccount;
	}

	public void setSearchAccount(String searchAccount) {
		this.searchAccount = searchAccount;
	}
	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}
	public long getAccphoneNo() {
		return accphoneNo;
	}


	public void setAccphoneNo(long accphoneNo) {
		this.accphoneNo = accphoneNo;
	}

	public String getAcmgrEmail() {
		return acmgrEmail;
	}


	public void setAcmgrEmail(String acmgrEmail) {
		this.acmgrEmail = acmgrEmail;
	}
	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}
	public String getM6ShortCode() {
		return m6ShortCode;
	}

	public void setM6ShortCode(String shortCode) {
		m6ShortCode = shortCode;
	}
	public int getRegionId() {
		return regionId;
	}

	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}
	public int getZoneId() {
		return zoneId;
	}

	public void setZoneId(int zoneId) {
		this.zoneId = zoneId;
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
	public String getAcmgrPhno() {
		return acmgrPhno;
	}
	public void setAcmgrPhno(String acmgrPhno) {
		this.acmgrPhno = acmgrPhno;
	}

	public String getSpLastName() {
		return spLastName;
	}

	public void setSpLastName(String spLastName) {
		this.spLastName = spLastName;
	}
	public String getSpFirstname() {
		return spFirstname;
	}

	public void setSpFirstname(String spFirstname) {
		this.spFirstname = spFirstname;
	}
	public String getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}
	public String getAccountManager() {
		return accountManager;
	}

	public void setAccountManager(String accountManager) {
		this.accountManager = accountManager;
	}
	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}
	public int getAccountID() {
		return accountID;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAccountName() {
		return accountName;
	}
	public String getLob() {
		return lob;
	}
	public void setLob(String lob) {
		this.lob = lob;
	}
	public int getCrmAccountId() {
		return crmAccountId;
	}


	public void setCrmAccountId(int crmAccountId) {
		this.crmAccountId = crmAccountId;
	}


	public String getChangeorderstatus() {
		return changeorderstatus;
	}


	public void setChangeorderstatus(String changeorderstatus) {
		this.changeorderstatus = changeorderstatus;
	}


	public String getIb2bWorkflowAttachedId() {
		return ib2bWorkflowAttachedId;
	}


	public void setIb2bWorkflowAttachedId(String ib2bWorkflowAttachedId) {
		this.ib2bWorkflowAttachedId = ib2bWorkflowAttachedId;
	}


	public String getIb2bWorkflowAttachedName() {
		return ib2bWorkflowAttachedName;
	}


	public void setIb2bWorkflowAttachedName(String ib2bWorkflowAttachedName) {
		this.ib2bWorkflowAttachedName = ib2bWorkflowAttachedName;
	}


	public String getProjectWorkflowId() {
		return projectWorkflowId;
	}


	public void setProjectWorkflowId(String projectWorkflowId) {
		this.projectWorkflowId = projectWorkflowId;
	}


	public int getCHANGE_TYPE_DISCONNECTION() {
		return CHANGE_TYPE_DISCONNECTION;
	}


	public int getCHANGE_TYPE_RATERENEWAL() {
		return CHANGE_TYPE_RATERENEWAL;
	}


	public int getNoOfServiceInOrder() {
		return noOfServiceInOrder;
	}


	public void setNoOfServiceInOrder(int noOfServiceInOrder) {
		this.noOfServiceInOrder = noOfServiceInOrder;
	}

	public String getORDER_TYPE_CHANGE() {
		return ORDER_TYPE_CHANGE;
	}
	
	public void setOrderCreationSourceName(String orderCreationSourceName) {
		this.orderCreationSourceName = orderCreationSourceName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getOrderCreationSourceName() {
		return orderCreationSourceName;
	}
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	//[0001] Start
	public String getServiceSegment() {
		return serviceSegment;
	}

	public void setServiceSegment(String serviceSegment) {
		this.serviceSegment = serviceSegment;
	}
	//[0001] End
	//[002] Start Project Satyapan
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
	//[002] End Project Satyapan

	public String getChannelMasterTagging() {
		return channelMasterTagging;
	}

	public void setChannelMasterTagging(String channelMasterTagging) {
		this.channelMasterTagging = channelMasterTagging;
	}



	public int getPARTNER_ID() {
		return PARTNER_ID;
	}

	public void setPARTNER_ID(int pARTNER_ID) {
		PARTNER_ID = pARTNER_ID;
	}

	public String getPARTNER_NAME() {
		return PARTNER_NAME;
	}

	public void setPARTNER_NAME(String pARTNER_NAME) {
		PARTNER_NAME = pARTNER_NAME;
	}

	public String getChannelPartnerCtgryName() {
		return channelPartnerCtgryName;
	}

	public void setChannelPartnerCtgryName(String channelPartnerCtgryName) {
		this.channelPartnerCtgryName = channelPartnerCtgryName;
	}
	
	}
