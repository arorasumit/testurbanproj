package com.ibm.ioes.ecrm;

import java.util.ArrayList;

public class FetchCRMXMLDto {

	
	
	private String xml = null;
	private Long xmlId = null;
	private int send_status;
	private String messageID=null;
	
	private String save_status=null;

	private String readResponseXMLId=null;
	
	private String 	acountId = null;
	private String 	accountNo = null;
	private String 	accountName = null;
	private String  accShortName;
	private String  accAccountMangerId;
	private String  accProjectMangerId;
	private String  accAccountMangerName;
	private String  accProjectMangerName;
	private String 	regionId;
	private String 	regionName;
	private String 	accountLob;
	private String  partyId;
	private String  verticalId;
	private String  verticalCode;	
	private String  accountCategoryId;
	private String  accountCategoryCode;
	private String  m6ShortName;
	private String  accountStatus;
	
	
	private int status;

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAcountId() {
		return acountId;
	}

	public void setAcountId(String acountId) {
		this.acountId = acountId;
	}

	public String getMessageID() {
		return messageID;
	}

	public void setMessageID(String messageID) {
		this.messageID = messageID;
	}

	public String getReadResponseXMLId() {
		return readResponseXMLId;
	}

	public void setReadResponseXMLId(String readResponseXMLId) {
		this.readResponseXMLId = readResponseXMLId;
	}

	public String getSave_status() {
		return save_status;
	}

	public void setSave_status(String save_status) {
		this.save_status = save_status;
	}

	public int getSend_status() {
		return send_status;
	}

	public void setSend_status(int send_status) {
		this.send_status = send_status;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public Long getXmlId() {
		return xmlId;
	}

	public void setXmlId(Long xmlId) {
		this.xmlId = xmlId;
	}

	public String getAccShortName() {
		return accShortName;
	}

	public void setAccShortName(String accShortName) {
		this.accShortName = accShortName;
	}

	public String getAccAccountMangerId() {
		return accAccountMangerId;
	}

	public void setAccAccountMangerId(String accAccountMangerId) {
		this.accAccountMangerId = accAccountMangerId;
	}

	public String getAccProjectMangerId() {
		return accProjectMangerId;
	}

	public void setAccProjectMangerId(String accProjectMangerId) {
		this.accProjectMangerId = accProjectMangerId;
	}

	public String getAccAccountMangerName() {
		return accAccountMangerName;
	}

	public void setAccAccountMangerName(String accAccountMangerName) {
		this.accAccountMangerName = accAccountMangerName;
	}

	public String getAccProjectMangerName() {
		return accProjectMangerName;
	}

	public void setAccProjectMangerName(String accProjectMangerName) {
		this.accProjectMangerName = accProjectMangerName;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getAccountLob() {
		return accountLob;
	}

	public void setAccountLob(String accountLob) {
		this.accountLob = accountLob;
	}

	public String getAccountCategoryId() {
		return accountCategoryId;
	}

	public void setAccountCategoryId(String accountCategoryId) {
		this.accountCategoryId = accountCategoryId;
	}

	public String getPartyId() {
		return partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	public String getVerticalId() {
		return verticalId;
	}

	public void setVerticalId(String verticalId) {
		this.verticalId = verticalId;
	}

	public String getM6ShortName() {
		return m6ShortName;
	}

	public void setM6ShortName(String shortName) {
		m6ShortName = shortName;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public String getAccountCategoryCode() {
		return accountCategoryCode;
	}

	public void setAccountCategoryCode(String accountCategoryCode) {
		this.accountCategoryCode = accountCategoryCode;
	}

	public String getVerticalCode() {
		return verticalCode;
	}

	public void setVerticalCode(String verticalCode) {
		this.verticalCode = verticalCode;
	}


	
	
}
