package com.ibm.ioes.escalation.dto;

public class EscalationMailDTO {
	
	private int roleId;
	private String poRecieveDate;
	private int orderNo;
	private String accountName;
	private String productName;
	private String poAmount;
	private String levelDetailsTo;
	private String levelDetailsCC;
	private String binOwnnerName;
	private String agingInCurrentBin;
	private String agingFromPORecDate;
	private int regionId;
	private int chgTypId;
	private String levelDetailsToDB;
	private String escalationLevel;
	private String copcMail;
	private int numOfRows;
	private String finalMail;
	private String subject;
	private int isActive;
	
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getPoRecieveDate() {
		return poRecieveDate;
	}
	public void setPoRecieveDate(String poRecieveDate) {
		this.poRecieveDate = poRecieveDate;
	}
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getPoAmount() {
		return poAmount;
	}
	public void setPoAmount(String poAmount) {
		this.poAmount = poAmount;
	}
	public String getLevelDetailsTo() {
		return levelDetailsTo;
	}
	public void setLevelDetailsTo(String levelDetailsTo) {
		this.levelDetailsTo = levelDetailsTo;
	}
	public String getLevelDetailsCC() {
		return levelDetailsCC;
	}
	public void setLevelDetailsCC(String levelDetailsCC) {
		this.levelDetailsCC = levelDetailsCC;
	}
	public String getBinOwnnerName() {
		return binOwnnerName;
	}
	public void setBinOwnnerName(String binOwnnerName) {
		this.binOwnnerName = binOwnnerName;
	}
	public String getAgingInCurrentBin() {
		return agingInCurrentBin;
	}
	public void setAgingInCurrentBin(String agingInCurrentBin) {
		this.agingInCurrentBin = agingInCurrentBin;
	}
	public String getAgingFromPORecDate() {
		return agingFromPORecDate;
	}
	public void setAgingFromPORecDate(String agingFromPORecDate) {
		this.agingFromPORecDate = agingFromPORecDate;
	}
	public int getRegionId() {
		return regionId;
	}
	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}
	public int getChgTypId() {
		return chgTypId;
	}
	public void setChgTypId(int chgTypId) {
		this.chgTypId = chgTypId;
	}
	
	public String getLevelDetailsToDB() {
		return levelDetailsToDB;
	}
	public void setLevelDetailsToDB(String levelDetailsToDB) {
		this.levelDetailsToDB = levelDetailsToDB;
	}
	public String getEscalationLevel() {
		return escalationLevel;
	}
	public void setEscalationLevel(String escalationLevel) {
		this.escalationLevel = escalationLevel;
	}

	public String getCopcMail() {
		return copcMail;
	}
	public void setCopcMail(String copcMail) {
		this.copcMail = copcMail;
	}
	public int getNumOfRows() {
		return numOfRows;
	}
	public void setNumOfRows(int numOfRows) {
		this.numOfRows = numOfRows;
	}
	public String getFinalMail() {
		return finalMail;
	}
	public void setFinalMail(String finalMail) {
		this.finalMail = finalMail;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public int hashCode(){
		
		return roleId+levelDetailsTo.hashCode()+levelDetailsTo.length();
	}
	
	public boolean equals(Object obj){
		if(this.roleId==((EscalationMailDTO)obj).getRoleId()  &&
				this.levelDetailsTo.equalsIgnoreCase(((EscalationMailDTO)obj).getLevelDetailsTo())){
			return true;
		}else{
			return false;
		}
	}

}
