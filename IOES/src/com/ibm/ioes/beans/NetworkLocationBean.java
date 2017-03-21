package com.ibm.ioes.beans;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

import com.ibm.ioes.forms.CityDto;
import com.ibm.ioes.forms.CountryDto;
import com.ibm.ioes.forms.StateDto;
import com.ibm.ioes.utilities.PagingSorting;

public class NetworkLocationBean extends ActionForm {
	PagingSorting pagingSorting = new PagingSorting();

	private String accountIdStr;

	private String accountNameStr;

	private String networkLocationIdStr;

	private String networkLocationNameStr;

	//private String actiontype;

	private int hiddenNetworkLocationId;

	private String hiddenNetworkLocationName;

	//private String hiddenFlag = null;

	private ArrayList customerList;

	private String searchAccount;

	private String networkLocationId;
	
	private String networkLocationName;

	private String searchNetworkLocation;

	private ArrayList list;

	private String customerName;

	private int updateFlag;

	private String accountID;

	private String accountName;

	private String accountManager;

	private int accphoneNo;

	private String projectManager;

	private String lob;

	private String spFirstname;

	private String spLastName;

	private int spLPhno;

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

	private String city;

	private String state;

	private String country;

	private String title;

	private String state_Id;
	
	ArrayList<CountryDto> countries = null;
	
	ArrayList<StateDto> states = null;
	
	ArrayList<CityDto> cities = null;

	public String getAccountID() {
		return accountID;
	}

	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}

	public String getAccountIdStr() {
		return accountIdStr;
	}

	public void setAccountIdStr(String accountIdStr) {
		this.accountIdStr = accountIdStr;
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

	public String getAccountNameStr() {
		return accountNameStr;
	}

	public void setAccountNameStr(String accountNameStr) {
		this.accountNameStr = accountNameStr;
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

	public ArrayList<CityDto> getCities() {
		return cities;
	}

	public void setCities(ArrayList<CityDto> cities) {
		this.cities = cities;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public ArrayList<CountryDto> getCountries() {
		return countries;
	}

	public void setCountries(ArrayList<CountryDto> countries) {
		this.countries = countries;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public ArrayList getCustomerList() {
		return customerList;
	}

	public void setCustomerList(ArrayList customerList) {
		this.customerList = customerList;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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

	public int getHiddenNetworkLocationId() {
		return hiddenNetworkLocationId;
	}

	public void setHiddenNetworkLocationId(int hiddenNetworkLocationId) {
		this.hiddenNetworkLocationId = hiddenNetworkLocationId;
	}

	public String getHiddenNetworkLocationName() {
		return hiddenNetworkLocationName;
	}

	public void setHiddenNetworkLocationName(String hiddenNetworkLocationName) {
		this.hiddenNetworkLocationName = hiddenNetworkLocationName;
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

	public String getNetworkLocationId() {
		return networkLocationId;
	}

	public void setNetworkLocationId(String networkLocationId) {
		this.networkLocationId = networkLocationId;
	}

	public String getNetworkLocationIdStr() {
		return networkLocationIdStr;
	}

	public void setNetworkLocationIdStr(String networkLocationIdStr) {
		this.networkLocationIdStr = networkLocationIdStr;
	}

	public String getNetworkLocationName() {
		return networkLocationName;
	}

	public void setNetworkLocationName(String networkLocationName) {
		this.networkLocationName = networkLocationName;
	}

	public String getNetworkLocationNameStr() {
		return networkLocationNameStr;
	}

	public void setNetworkLocationNameStr(String networkLocationNameStr) {
		this.networkLocationNameStr = networkLocationNameStr;
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

	public String getSearchNetworkLocation() {
		return searchNetworkLocation;
	}

	public void setSearchNetworkLocation(String searchNetworkLocation) {
		this.searchNetworkLocation = searchNetworkLocation;
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

	public int getSpLPhno() {
		return spLPhno;
	}

	public void setSpLPhno(int spLPhno) {
		this.spLPhno = spLPhno;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getState_Id() {
		return state_Id;
	}

	public void setState_Id(String state_Id) {
		this.state_Id = state_Id;
	}

	public ArrayList<StateDto> getStates() {
		return states;
	}

	public void setStates(ArrayList<StateDto> states) {
		this.states = states;
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
}
