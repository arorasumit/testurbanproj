package com.ibm.ioes.forms;

public class ContactDTO {
	private String contactType;
	private int salutationId;
	private String salutationName;
	private String firstName;
	private String lastName;
	private String cntEmail;
	private String contactCell;
	private String contactFax;
	private int isReqAddress1;
	private int isReqAddress2;
	private int isReqAddress3;
	private int isReqCityName;
	private int isReqStateName;
	private int isReqCountyName;
	private int isReqPinNo;
	private String contactTypeId;
	private String contactTypeName;
	private int cityCode;
	private String cityName;
	private String stateName;
	private int stateCode;
	private int isReqContactType;
	private int isReqSaluation;
	private int isReqFirstName;
	private int isReqLastName;
	private int isReqCntEmail;
	private int isReqContactCell;
	private int isReqContactFax;
	private String address1;
	private String address2;
	private String address3;
	private String countyName;
	private String pinNo;
	private Long contactId;
	//Puneet - added for sending the counter value
	private int count;
	public Long getContactId() {
		return contactId;
	}

	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}
	public String getPinNo() {
		return pinNo;
	}

	public void setPinNo(String pinNo) {
		this.pinNo = pinNo;
	}
	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
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
	public int getIsReqContactCell() {
		return isReqContactCell;
	}


	public void setIsReqContactCell(int isReqContactCell) {
		this.isReqContactCell = isReqContactCell;
	}


	public int getIsReqContactFax() {
		return isReqContactFax;
	}


	public void setIsReqContactFax(int isReqContactFax) {
		this.isReqContactFax = isReqContactFax;
	}

	public int getIsReqCntEmail() {
		return isReqCntEmail;
	}


	public void setIsReqCntEmail(int isReqCntEmail) {
		this.isReqCntEmail = isReqCntEmail;
	}
	public int getIsReqLastName() {
		return isReqLastName;
	}


	public void setIsReqLastName(int isReqLastName) {
		this.isReqLastName = isReqLastName;
	}
	public int getIsReqFirstName() {
		
		return isReqFirstName;
	}


	public void setIsReqFirstName(int isReqFirstName) {
		this.isReqFirstName = isReqFirstName;
	}
	public int getIsReqSaluation() {
		return isReqSaluation;
	}


	public void setIsReqSaluation(int isReqSaluation) {
		this.isReqSaluation = isReqSaluation;
	}
	
	public void setIsReqContactType(int isReqContactType) {
		this.isReqContactType = isReqContactType;
	}

	public int getIsReqContactType() {
		return isReqContactType;
	}

	public int getStateCode() {
		return stateCode;
	}

	public void setStateCode(int stateCode) {
		this.stateCode = stateCode;
	}
	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getCityName() {
		return cityName;
	}
	public int getCityCode() {
		return cityCode;
	}

	public void setCityCode(int cityCode) {
		this.cityCode = cityCode;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	
	public String getContactTypeId() {
		return contactTypeId;
	}

	public void setContactTypeId(String contactTypeId) {
		this.contactTypeId = contactTypeId;
	}

	public String getContactTypeName() {
		return contactTypeName;
	}

	public void setContactTypeName(String contactTypeName) {
		this.contactTypeName = contactTypeName;
	}
	public int getIsReqPinNo() {
		return isReqPinNo;
	}

	public void setIsReqPinNo(int isReqPinNo) {
		this.isReqPinNo = isReqPinNo;
	}
	public int getIsReqCountyName() {
		return isReqCountyName;
	}


	public void setIsReqCountyName(int isReqCountyName) {
		this.isReqCountyName = isReqCountyName;
	}
	public int getIsReqStateName() {
		return isReqStateName;
	}


	public void setIsReqStateName(int isReqStateName) {
		this.isReqStateName = isReqStateName;
	}
	public int getIsReqCityName() {
		return isReqCityName;
	}


	public void setIsReqCityName(int isReqCityName) {
		this.isReqCityName = isReqCityName;
	}

	public int getIsReqAddress1() {
		return isReqAddress1;
	}


	public void setIsReqAddress1(int isReqAddress1) {
		this.isReqAddress1 = isReqAddress1;
	}


	public int getIsReqAddress2() {
		return isReqAddress2;
	}


	public void setIsReqAddress2(int isReqAddress2) {
		this.isReqAddress2 = isReqAddress2;
	}


	public int getIsReqAddress3() {
		return isReqAddress3;
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
	public String getContactType() {
		return contactType;
	}

	public void setContactType(String contactType) {
		this.contactType = contactType;
	}
	public int getSalutationId() {
		return salutationId;
	}

	public void setSalutationId(int salutationId) {
		this.salutationId = salutationId;
	}

	public String getSalutationName() {
		return salutationName;
	}

	public void setSalutationName(String salutationName) {
		this.salutationName = salutationName;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
