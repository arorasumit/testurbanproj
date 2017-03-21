package com.ibm.ioes.forms;

public class ChargeDetailsSCM {
	private String chargeValue;
	private String chargeQuantity;
	private String itemCode;
    private String chargeType;
    //added by Deepak for third party
    private Integer aop1_Id;
    private Integer aop2_Id;
    private String  aopYear;
    private int subInventryId;
    private int deliveryLocationId;
    private Integer chargeId_Scm;
    private String poNumer;
    private String poDtae;
    private Double poAmount;
    private byte isActive;
    private String scmMessage;
    private String isActiveFlag;
    private int chargeIdList;
	public String getChargeNameSCM() {
		return chargeNameSCM;
	}
	public void setChargeNameSCM(String chargeNameSCM) {
		this.chargeNameSCM = chargeNameSCM;
	}
	public int getCRMChargeID() {
		return CRMChargeID;
	}
	public void setCRMChargeID(int cRMChargeID) {
		CRMChargeID = cRMChargeID;
	}
	private String  chargeNameSCM;
	private int CRMChargeID;
    

	public String getChargeValue() {
		return chargeValue;
	}
	public void setChargeValue(String chargeValue) {
		this.chargeValue = chargeValue;
	}
	public String getChargeQuantity() {
		return chargeQuantity;
	}
	public void setChargeQuantity(String chargeQuantity) {
		this.chargeQuantity = chargeQuantity;
	}
	
	public String getChargeType() {
		return chargeType;
	}
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public Integer getAop1_Id() {
		return aop1_Id;
	}
	public void setAop1_Id(Integer aop1_Id) {
		this.aop1_Id = aop1_Id;
	}
	public Integer getAop2_Id() {
		return aop2_Id;
	}
	public void setAop2_Id(Integer aop2_Id) {
		this.aop2_Id = aop2_Id;
	}
	public String getAopYear() {
		return aopYear;
	}
	public void setAopYear(String aopYear) {
		this.aopYear = aopYear;
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
	public Integer getChargeId_Scm() {
		return chargeId_Scm;
	}
	public void setChargeId_Scm(Integer chargeId_Scm) {
		this.chargeId_Scm = chargeId_Scm;
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
	public byte getIsActive() {
		return isActive;
	}
	public void setIsActive(byte isActive) {
		this.isActive = isActive;
	}
	public String getScmMessage() {
		return scmMessage;
	}
	public void setScmMessage(String scmMessage) {
		this.scmMessage = scmMessage;
	}
	public String getIsActiveFlag() {
		return isActiveFlag;
	}
	public void setIsActiveFlag(String isActiveFlag) {
		this.isActiveFlag = isActiveFlag;
	}
	public int getChargeIdList() {
		return chargeIdList;
	}
	public void setChargeIdList(int chargeIdList) {
		this.chargeIdList = chargeIdList;
	}
	
}
