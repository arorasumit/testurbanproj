package com.ibm.ioes.ei.scm.dto;
//Tag Name Resource Name  Date		CSR No			Description
//[001]	 Kalpana	11-January-2014			New File for receive attributes at line level for third party
public class PRCreatnSCMXmlDto {
	private String trdPartyPoNum;
	private String dateOfPOTP;
	private String poAmtToTP;
	
	private String itemCode;
	private String quantity;
	private String itemPrice;
	private String delToLoc;
	private String destSubInv;
	private String aopHead1;
	private String aopHead2;
	private String aopYear;
	private String tpPoNum;
	private String dateOfPo;
	private String poAmt;
	private int trxId;
	private int prId;
    private int scmLineId;
	
	public String getTrdPartyPoNum() {
		return trdPartyPoNum;
	}
	public void setTrdPartyPoNum(String trdPartyPoNum) {
		this.trdPartyPoNum = trdPartyPoNum;
	}
	public String getDateOfPOTP() {
		return dateOfPOTP;
	}
	public void setDateOfPOTP(String dateOfPOTP) {
		this.dateOfPOTP = dateOfPOTP;
	}
	public String getPoAmtToTP() {
		return poAmtToTP;
	}
	public void setPoAmtToTP(String poAmtToTP) {
		this.poAmtToTP = poAmtToTP;
	}
	

	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(String itemPrice) {
		this.itemPrice = itemPrice;
	}
	public String getDelToLoc() {
		return delToLoc;
	}
	public void setDelToLoc(String delToLoc) {
		this.delToLoc = delToLoc;
	}
	public String getDestSubInv() {
		return destSubInv;
	}
	public void setDestSubInv(String destSubInv) {
		this.destSubInv = destSubInv;
	}
	public String getAopHead1() {
		return aopHead1;
	}
	public void setAopHead1(String aopHead1) {
		this.aopHead1 = aopHead1;
	}
	public String getAopHead2() {
		return aopHead2;
	}
	public void setAopHead2(String aopHead2) {
		this.aopHead2 = aopHead2;
	}
	public String getAopYear() {
		return aopYear;
	}
	public void setAopYear(String aopYear) {
		this.aopYear = aopYear;
	}
	public String getTpPoNum() {
		return tpPoNum;
	}
	public void setTpPoNum(String tpPoNum) {
		this.tpPoNum = tpPoNum;
	}
	public String getDateOfPo() {
		return dateOfPo;
	}
	public void setDateOfPo(String dateOfPo) {
		this.dateOfPo = dateOfPo;
	}
	public String getPoAmt() {
		return poAmt;
	}
	public void setPoAmt(String poAmt) {
		this.poAmt = poAmt;
	}
	public int getTrxId() {
		return trxId;
	}
	public void setTrxId(int trxId) {
		this.trxId = trxId;
	}
	public int getPrId() {
		return prId;
	}
	public void setPrId(int prId) {
		this.prId = prId;
	}
	public int getScmLineId() {
		return scmLineId;
	}
	public void setScmLineId(int scmLineId) {
		this.scmLineId = scmLineId;
	}

}
