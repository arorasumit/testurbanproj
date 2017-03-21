package com.ibm.ioes.forms;

import java.util.ArrayList;
import com.ibm.ioes.forms.ServiceSubTypeDto;
import com.ibm.ioes.utilities.PagingSorting;

public class DispatchAddressDto 
{
	private String dispatchAddressId;
	ArrayList errors_Validation=new ArrayList();
	PagingSorting pagingSorting=new PagingSorting();
	private int updateFlag;
	private ArrayList list;
	private String customerName;
	private String searchAccount;
	private String searchDispatchAddress="";
	private String dispatchAddressName;
	
	private String accountID;
	
	private String accountName;
	private String countryID;
	
	private String countryName;
	private String cityID;
	
	private String cityName;
	private String stateID;
	
	private String stateName;
	private String editcountryID;
	
	private String editcountryName;
	
    private String accountManager;
	
	private int accphoneNo;
	
	private String projectManager;
	
	private String lob;
	
	private String spFirstname;
	
	private String spLastName;
	
	private String spLPhno;//changed by kalpana from long to string for bug id HYPR11042013001
	
	private String spLEmail;
	
	private String region;
	
	private String zone;

	private String firstname;
	
	private String lastName;
	
	private String telephonePhno;
	
	private String email_Id;
	
	private String address1;
	private String address2;
	private String address3;
	private String address4;
	
	private String fax;
	private String pin;
	private String countryCode;
	private String postalCode;
	

	private String title;
	private String state_Id;
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
	public int getAccphoneNo() {
		return accphoneNo;
	}
	public void setAccphoneNo(int accphoneNo) {
		this.accphoneNo = accphoneNo;
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
	public String getAddress4() {
		return address4;
	}
	public void setAddress4(String address4) {
		this.address4 = address4;
	}
	public String getCityID() {
		return cityID;
	}
	public void setCityID(String cityID) {
		this.cityID = cityID;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getCountryID() {
		return countryID;
	}
	public void setCountryID(String countryID) {
		this.countryID = countryID;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getDispatchAddressId() {
		return dispatchAddressId;
	}
	public void setDispatchAddressId(String dispatchAddressId) {
		this.dispatchAddressId = dispatchAddressId;
	}
	public String getDispatchAddressName() {
		return dispatchAddressName;
	}
	public void setDispatchAddressName(String dispatchAddressName) {
		this.dispatchAddressName = dispatchAddressName;
	}
	public String getEditcountryID() {
		return editcountryID;
	}
	public void setEditcountryID(String editcountryID) {
		this.editcountryID = editcountryID;
	}
	public String getEditcountryName() {
		return editcountryName;
	}
	public void setEditcountryName(String editcountryName) {
		this.editcountryName = editcountryName;
	}
	public String getEmail_Id() {
		return email_Id;
	}
	public void setEmail_Id(String email_Id) {
		this.email_Id = email_Id;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public ArrayList getList() {
		return list;
	}
	public void setList(ArrayList list) {
		this.list = list;
	}
	public String getLob() {
		return lob;
	}
	public void setLob(String lob) {
		this.lob = lob;
	}
	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}
	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getProjectManager() {
		return projectManager;
	}
	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getSearchAccount() {
		return searchAccount;
	}
	public void setSearchAccount(String searchAccount) {
		this.searchAccount = searchAccount;
	}
	public String getSearchDispatchAddress() {
		return searchDispatchAddress;
	}
	public void setSearchDispatchAddress(String searchDispatchAddress) {
		this.searchDispatchAddress = searchDispatchAddress;
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
	public String getState_Id() {
		return state_Id;
	}
	public void setState_Id(String state_Id) {
		this.state_Id = state_Id;
	}
	public String getStateID() {
		return stateID;
	}
	public void setStateID(String stateID) {
		this.stateID = stateID;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getTelephonePhno() {
		return telephonePhno;
	}
	public void setTelephonePhno(String telephonePhno) {
		this.telephonePhno = telephonePhno;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getUpdateFlag() {
		return updateFlag;
	}
	public void setUpdateFlag(int updateFlag) {
		this.updateFlag = updateFlag;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public ArrayList getErrors_Validation() {
		return errors_Validation;
	}
	public void setErrors_Validation(ArrayList errors_Validation) {
		this.errors_Validation = errors_Validation;
	}
}
