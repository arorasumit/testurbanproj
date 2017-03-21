package com.ibm.ioes.forms;

//Tag Name Resource Name  Date		CSR No			Description
//[001]    Priya Gupta    19-Jun-15      Added ldclause column in podetails tab
public class PoDetailsDTO {
	
	
	private String custPoDetailNo; //by Saurabh
	private String custPoDate;   //by Saurabh 
	private int legalEntity;  //by Saurabh
	private String totalPoAmt;
	private String poReceiveDate;
	private String contractStartDate;
	private String contractEndDate;
	private String periodsInMonths;
	private String defaultPO;
	private String poIssueBy;
	private String poEmailId;
	private String poRemarks;
	private String poDemoContractPeriod;
	private int poNumber;
	private int podetailID;
	private String poDetailNo;
	private String poDate;
	private int noofuses;
	private int accountID;
	private Long contactId;
	private int entityID;
	private String entity;
	private long ponum;
	private int count1;
	private int isDefaultPO;
	private int isFLEFlag;
	private int serviceDetailID;
	//[001]
	private String ldClause;
	
	/*private String changeType;
	private int subChangeTypeId;
	private String subChangeTypeName;
	private String ib2bWorkflowAttachedId;
	private String ib2bWorkflowAttachedName;
	private String projectWorkflowId;
	private String changeorderstatus;*/
	//private String order_type;	//by Saurabh
	
	//private String orderType;
	

	


	
/*	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}*/

	public int getServiceDetailID() {
		return serviceDetailID;
	}


	public void setServiceDetailID(int serviceDetailID) {
		this.serviceDetailID = serviceDetailID;
	}


	public int getIsFLEFlag() {
		return isFLEFlag;
	}


	public void setIsFLEFlag(int isFLEFlag) {
		this.isFLEFlag = isFLEFlag;
	}


	/*public String getOrder_type() {
		return order_type;
	}


	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}*/
	/*public String getProjectWorkflowId() {
		return projectWorkflowId;
	}


	public void setProjectWorkflowId(String projectWorkflowId) {
		this.projectWorkflowId = projectWorkflowId;
	}
	public String getIb2bWorkflowAttachedName() {
		return ib2bWorkflowAttachedName;
	}


	public void setIb2bWorkflowAttachedName(String ib2bWorkflowAttachedName) {
		this.ib2bWorkflowAttachedName = ib2bWorkflowAttachedName;
	}
	public String getIb2bWorkflowAttachedId() {
		return ib2bWorkflowAttachedId;
	}


	public void setIb2bWorkflowAttachedId(String ib2bWorkflowAttachedId) {
		this.ib2bWorkflowAttachedId = ib2bWorkflowAttachedId;
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
	public String getChangeType() {
		return changeType;
	}

	*//**
	 * @param changeType the changeType to set
	 *//*
	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}*/
	public int getIsDefaultPO() {
		return isDefaultPO;
	}


	public void setIsDefaultPO(int isDefaultPO) {
		this.isDefaultPO = isDefaultPO;
	}

	public int getCount1() {
		return count1;
	}


	public void setCount1(int count1) {
		this.count1 = count1;
	}
	public long getPonum() {
		return ponum;
	}


	public void setPonum(long ponum) {
		this.ponum = ponum;
	}
	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}
	public int getEntityID() {
		return entityID;
	}

	public void setEntityID(int entityID) {
		this.entityID = entityID;
	}
	public Long getContactId() {
		return contactId;
	}

	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}
	public int getAccountID() {
		return accountID;
	}
	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}
	
	public int getNoofuses() {
		return noofuses;
	}


	public void setNoofuses(int noofuses) {
		this.noofuses = noofuses;
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
		this.poDetailNo = poDetailNo;
	}
	public void setPodetailID(int podetailID) {
		this.podetailID = podetailID;
	}
	public int getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(int poNumber) {
		this.poNumber = poNumber;
	}
	public String getPoDemoContractPeriod() {
		return poDemoContractPeriod;
	}

	public void setPoDemoContractPeriod(String poDemoContractPeriod) {
		this.poDemoContractPeriod = poDemoContractPeriod;
	}
	public String getPoRemarks() {
		return poRemarks;
	}

	public void setPoRemarks(String poRemarks) {
		this.poRemarks = poRemarks;
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
	public String getDefaultPO() {
		return defaultPO;
	}

	public void setDefaultPO(String defaultPO) {
		this.defaultPO = defaultPO;
	}
	public String getPeriodsInMonths() {
		return periodsInMonths;
	}

	public void setPeriodsInMonths(String periodsInMonths) {
		this.periodsInMonths = periodsInMonths;
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
	public String getPoReceiveDate() {
		return poReceiveDate;
	}

	public void setPoReceiveDate(String poReceiveDate) {
		this.poReceiveDate = poReceiveDate;
	}
	public String getTotalPoAmt() {
		return totalPoAmt;
	}

	public void setTotalPoAmt(String totalPoAmt) {
		this.totalPoAmt = totalPoAmt;
	}

	public int getLegalEntity() {
		return legalEntity;
	}


	public void setLegalEntity(int legalEntity) {
		this.legalEntity = legalEntity;
	}
	public String getCustPoDetailNo() {
		return custPoDetailNo;
	}
		public void setCustPoDetailNo(String custPoDetailNo) {
		this.custPoDetailNo = custPoDetailNo;
	}
		public String getCustPoDate() {
			return custPoDate;
		}


		public void setCustPoDate(String custPoDate) {
			this.custPoDate = custPoDate;
		}

		public int getPodetailID() {
			return podetailID;
		}

		//[001] starts
		public String getLdClause() {
			return ldClause;
		}


		public void setLdClause(String ldClause) {
			this.ldClause = ldClause;
		}


		
		
		//[001] ends
}
