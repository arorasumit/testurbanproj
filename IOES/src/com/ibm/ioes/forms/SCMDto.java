package com.ibm.ioes.forms;

public class SCMDto {
	private	int  scmAttMasterid;                                   
	private		String  attDescription ;                                                
	private		String attDefaultVal;                                          
	private		String attDatatype;                                                
	private		String alisname ;                                               
	private		String expectedValue ;                                         
	private		String maxlength  ;                                         
	private		String serviceDetailid;                                          
	private		String mandatory ;                                      
	private		String isactive    ;                                  
	private		String sendToOracle ;                                
	private		String attribute_key;                                         
	private		String orderBy ;                                       
	private		String receiveFromOracle;
	private     String attValue;
	private     String serviceProductId;
	private int createdBy;
	private String modifyDate;
	private String modifyBy;
	private String lastInsertHelperId;
	private String stringValue;
	private String stringAttmasterId;
	private String deliveryLocation;
	private String subInventory;
	private String budgetHead1;
	private String budgetHead2;
	private String chargeType;
	private int chargeTypeId;
	private int    aop1_Id;
	private int    aop2_Id;
	private int itemCode_Id;
	private String itemCode;
	private int subInventryId;
	private int deliveryLocationId;
	private int chargeId_Scm;
	private int quntity;
	private int cahrgeValue;
	private String  aopYear;
	private String poNumer;
	private String poDtae;
	private Double poAmount;
	private String isActive;
	private String scmMessage;
	private String scmProgress_Status;
	private int pr_Id; 
	private String pr_number;
	private String serDetIdAskeyValue;
	private int prId;
	private int attmasterid_scm;
	private int prnumber;
	private String attvalue;
	private String displayText;
	private int orderNo;
	private String serviceId;
	private String descLabel;
	private int is_Pr_Reuse;
	private String nfaNumber;
	private String prValidationMsg;
	private String attributeValue;
	private String productName;
	private String preparer;
	private String deliverRequester;
	private int changeServiceId;
	private Integer thirdPartyFlag;
	
	public Integer getThirdPartyFlag() {
		return thirdPartyFlag;
	}
	public void setThirdPartyFlag(Integer thirdPartyFlag) {
		this.thirdPartyFlag = thirdPartyFlag;
	}
	public String getBudgetHead1() {
		return budgetHead1;
	}
	public void setBudgetHead1(String budgetHead1) {
		this.budgetHead1 = budgetHead1;
	}
	public String getSubInventory() {
		return subInventory;
	}
	public void setSubInventory(String subInventory) {
		this.subInventory = subInventory;
	}
	public String getDeliveryLocation() {
		return deliveryLocation;
	}
	public void setDeliveryLocation(String deliveryLocation) {
		this.deliveryLocation = deliveryLocation;
	}
		public int getScmAttMasterid() {
			return scmAttMasterid;
		}
		public String getAttDescription() {
			return attDescription;
		}
		public String getAttDefaultVal() {
			return attDefaultVal;
		}
		public String getAttDatatype() {
			return attDatatype;
		}
		public String getAlisname() {
			return alisname;
		}
		public String getExpectedValue() {
			return expectedValue;
		}
		public String getMaxlength() {
			return maxlength;
		}
		public String getServiceDetailid() {
			return serviceDetailid;
		}
		public String getMandatory() {
			return mandatory;
		}
		public String getIsactive() {
			return isactive;
		}
		public String getSendToOracle() {
			return sendToOracle;
		}
		public String getAttribute_key() {
			return attribute_key;
		}
		public String getOrderBy() {
			return orderBy;
		}
		public String getReceiveFromOracle() {
			return receiveFromOracle;
		}
	 
		public void setScmAttMasterid(int scmAttMasterid) {
			this.scmAttMasterid = scmAttMasterid;
		}
		public void setAttDescription(String attDescription) {
			this.attDescription = attDescription;
		}
		public void setAttDefaultVal(String attDefaultVal) {
			this.attDefaultVal = attDefaultVal;
		}
		public void setAttDatatype(String attDatatype) {
			this.attDatatype = attDatatype;
		}
		public void setAlisname(String alisname) {
			this.alisname = alisname;
		}
		public void setExpectedValue(String expectedValue) {
			this.expectedValue = expectedValue;
		}
		public void setMaxlength(String maxlength) {
			this.maxlength = maxlength;
		}
		public void setServiceDetailid(String serviceDetailid) {
			this.serviceDetailid = serviceDetailid;
		}
		public void setMandatory(String mandatory) {
			this.mandatory = mandatory;
		}
		public void setIsactive(String isactive) {
			this.isactive = isactive;
		}
		public void setSendToOracle(String sendToOracle) {
			this.sendToOracle = sendToOracle;
		}
		public void setAttribute_key(String attribute_key) {
			this.attribute_key = attribute_key;
		}
		public void setOrderBy(String orderBy) {
			this.orderBy = orderBy;
		}
		public void setReceiveFromOracle(String receiveFromOracle) {
			this.receiveFromOracle = receiveFromOracle;
		}
	 
		public SCMDto(int scmAttMasterid, String attDescription,
				String attDefaultVal, String attDatatype, String alisname,
				String expectedValue, String maxlength, String serviceDetailid,
				String mandatory, String isactive, String sendToOracle,
				String attribute_key, String orderBy, String receiveFromOracle,
				String rECEIVEFROMORACLE2) {
			super();
			this.scmAttMasterid = scmAttMasterid;
			this.attDescription = attDescription;
			this.attDefaultVal = attDefaultVal;
			this.attDatatype = attDatatype;
			this.alisname = alisname;
			this.expectedValue = expectedValue;
			this.maxlength = maxlength;
			this.serviceDetailid = serviceDetailid;
			this.mandatory = mandatory;
			this.isactive = isactive;
			this.sendToOracle = sendToOracle;
			this.attribute_key = attribute_key;
			this.orderBy = orderBy;
			this.receiveFromOracle = receiveFromOracle;
		 
		}
		public SCMDto() {
			super();
		}
		
		public void setCreatedBy(int createdBy) {
			this.createdBy = createdBy;
		}
		public String getAttValue() {
			return attValue;
		}
		public void setAttValue(String attValue) {
			this.attValue = attValue;
		}
		public String getServiceProductId() {
			return serviceProductId;
		}
		public void setServiceProductId(String serviceProductId) {
			this.serviceProductId = serviceProductId;
		}
		public String getModifyDate() {
			return modifyDate;
		}
		public void setModifyDate(String modifyDate) {
			this.modifyDate = modifyDate;
		}
		public String getModifyBy() {
			return modifyBy;
		}
		public void setModifyBy(String modifyBy) {
			this.modifyBy = modifyBy;
		}
		public String getLastInsertHelperId() {
			return lastInsertHelperId;
		}
		public void setLastInsertHelperId(String lastInsertHelperId) {
			this.lastInsertHelperId = lastInsertHelperId;
		}
		public String getStringValue() {
			return stringValue;
		}
		public void setStringValue(String stringValue) {
			this.stringValue = stringValue;
		}
		public String getStringAttmasterId() {
			return stringAttmasterId;
		}
		public void setStringAttmasterId(String stringAttmasterId) {
			this.stringAttmasterId = stringAttmasterId;
		}
		public int getCreatedBy() {
			return createdBy;
		}
		public String getBudgetHead2() {
			return budgetHead2;
		}
		public void setBudgetHead2(String budgetHead2) {
			this.budgetHead2 = budgetHead2;
		}
		public String getChargeType() {
			return chargeType;
		}
		public void setChargeType(String chargeType) {
			this.chargeType = chargeType;
		}
		public int getChargeTypeId() {
			return chargeTypeId;
		}
		public void setChargeTypeId(int chargeTypeId) {
			this.chargeTypeId = chargeTypeId;
		}
		
		public int getAop1_Id() {
			return aop1_Id;
		}
		public void setAop1_Id(int aop1_Id) {
			this.aop1_Id = aop1_Id;
		}
		public int getAop2_Id() {
			return aop2_Id;
		}
		public void setAop2_Id(int aop2_Id) {
			this.aop2_Id = aop2_Id;
		}
		public int getItemCode_Id() {
			return itemCode_Id;
		}
		public void setItemCode_Id(int itemCode_Id) {
			this.itemCode_Id = itemCode_Id;
		}
		public String getItemCode() {
			return itemCode;
		}
		public void setItemCode(String itemCode) {
			this.itemCode = itemCode;
		}
		public int getSubInventryId() {
			return subInventryId;
		}
		public void setSubInventryId(int subInventryId) {
			this.subInventryId = subInventryId;
		}
		public int getDeliveryLocationId() {
			return deliveryLocationId;
		}
		public void setDeliveryLocationId(int deliveryLocationId) {
			this.deliveryLocationId = deliveryLocationId;
		}
		public int getChargeId_Scm() {
			return chargeId_Scm;
		}
		public void setChargeId_Scm(int chargeId_Scm) {
			this.chargeId_Scm = chargeId_Scm;
		}
		public int getQuntity() {
			return quntity;
		}
		public void setQuntity(int quntity) {
			this.quntity = quntity;
		}
		public int getCahrgeValue() {
			return cahrgeValue;
		}
		public void setCahrgeValue(int cahrgeValue) {
			this.cahrgeValue = cahrgeValue;
		}
		public String getAopYear() {
			return aopYear;
		}
		public void setAopYear(String aopYear) {
			this.aopYear = aopYear;
		}
		public String getPoNumer() {
			return poNumer;
		}
		public void setPoNumer(String poNumer) {
			this.poNumer = poNumer;
		}
		public String getPoDtae() {
			return poDtae;
		}
		public void setPoDtae(String poDtae) {
			this.poDtae = poDtae;
		}
		public Double getPoAmount() {
			return poAmount;
		}
		public void setPoAmount(Double poAmount) {
			this.poAmount = poAmount;
		}
		
		public String getScmMessage() {
			return scmMessage;
		}
		public void setScmMessage(String scmMessage) {
			this.scmMessage = scmMessage;
		}
		public String getScmProgress_Status() {
			return scmProgress_Status;
		}
		public void setScmProgress_Status(String scmProgress_Status) {
			this.scmProgress_Status = scmProgress_Status;
		}
		public int getPr_Id() {
			return pr_Id;
		}
		public void setPr_Id(int pr_Id) {
			this.pr_Id = pr_Id;
		}
		public String getSerDetIdAskeyValue() {
			return serDetIdAskeyValue;
		}
		public void setSerDetIdAskeyValue(String serDetIdAskeyValue) {
			this.serDetIdAskeyValue = serDetIdAskeyValue;
		}
		public String getPr_number() {
			return pr_number;
		}
		public void setPr_number(String pr_number) {
			this.pr_number = pr_number;
		}
		public String getIsActive() {
			return isActive;
		}
		public void setIsActive(String isActive) {
			this.isActive = isActive;
		}
		public int getPrId() {
			return prId;
		}
		public void setPrId(int prId) {
			this.prId = prId;
		}
		public int getAttmasterid_scm() {
			return attmasterid_scm;
		}
		public void setAttmasterid_scm(int attmasterid_scm) {
			this.attmasterid_scm = attmasterid_scm;
		}
		public int getPrnumber() {
			return prnumber;
		}
		public void setPrnumber(int prnumber) {
			this.prnumber = prnumber;
		}
		public String getAttvalue() {
			return attvalue;
		}
		public void setAttvalue(String attvalue) {
			this.attvalue = attvalue;
		}
		public String getDisplayText() {
			return displayText;
		}
		public void setDisplayText(String displayText) {
			this.displayText = displayText;
		}
		public int getOrderNo() {
			return orderNo;
		}
		public void setOrderNo(int orderNo) {
			this.orderNo = orderNo;
		}
		public String getServiceId() {
			return serviceId;
		}
		public void setServiceId(String serviceId) {
			this.serviceId = serviceId;
		}
		public String getDescLabel() {
			return descLabel;
		}
		public void setDescLabel(String descLabel) {
			this.descLabel = descLabel;
		}
		public int getIs_Pr_Reuse() {
			return is_Pr_Reuse;
		}
		public void setIs_Pr_Reuse(int is_Pr_Reuse) {
			this.is_Pr_Reuse = is_Pr_Reuse;
		}
		public String getNfaNumber() {
			return nfaNumber;
		}
		public void setNfaNumber(String nfaNumber) {
			this.nfaNumber = nfaNumber;
		}
		public String getPrValidationMsg() {
			return prValidationMsg;
		}
		public void setPrValidationMsg(String prValidationMsg) {
			this.prValidationMsg = prValidationMsg;
		}
		public String getAttributeValue() {
			return attributeValue;
		}
		public void setAttributeValue(String attributeValue) {
			this.attributeValue = attributeValue;
		}
		public String getProductName() {
			return productName;
		}
		public void setProductName(String productName) {
			this.productName = productName;
		}
		public String getPreparer() {
			return preparer;
		}
		public void setPreparer(String preparer) {
			this.preparer = preparer;
		}
		public String getDeliverRequester() {
			return deliverRequester;
		}
		public void setDeliverRequester(String deliverRequester) {
			this.deliverRequester = deliverRequester;
		}
		public int getChangeServiceId() {
			return changeServiceId;
		}
		public void setChangeServiceId(int changeServiceId) {
			this.changeServiceId = changeServiceId;
		}
}