package com.ibm.ioes.beans;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.ibm.ioes.forms.CityDto;
import com.ibm.ioes.forms.CountryDto;
import com.ibm.ioes.forms.StateDto;
import com.ibm.ioes.utilities.PagingSorting;

public class BCPAddressBean extends ActionForm {
	@Override
	public void reset(ActionMapping arg0, HttpServletRequest arg1) {
		// TODO Auto-generated method stub
		super.reset(arg0, arg1);
		reset();
	}

	PagingSorting pagingSorting = new PagingSorting();

	private String accountIdStr;

	private String accountNameStr;

	private String bcpIdStr;

	private String bcpNameStr;

	//private String actiontype;

	private int hiddenBCPId;

	private String hiddenBCPName;

	//private String hiddenFlag = null;

	private ArrayList customerList;

	private String searchAccount;

	private String bcpId;
	
	private String bcpName;

	private String searchBCP;

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

	private String city;

	private String state;

	private String country;

	private String title;

	private String state_Id;
	
	ArrayList<CountryDto> countries = null;
	
	ArrayList<StateDto> states = null;
	
	ArrayList<CityDto> cities = null;

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

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
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

	public String getState_Id() {
		return state_Id;
	}

	public void setState_Id(String state_Id) {
		this.state_Id = state_Id;
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

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public ArrayList getList() {
		return list;
	}

	public void setList(ArrayList list) {
		this.list = list;
	}

	public String getSearchAccount() {
		return searchAccount;
	}

	public void setSearchAccount(String searchAccount) {
		this.searchAccount = searchAccount;
	}

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

	public int getAccphoneNo() {
		return accphoneNo;
	}

	public void setAccphoneNo(int accphoneNo) {
		this.accphoneNo = accphoneNo;
	}

	public String getLob() {
		return lob;
	}

	public void setLob(String lob) {
		this.lob = lob;
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

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	/*public String getHiddenFlag() {
		return hiddenFlag;
	}

	public void setHiddenFlag(String hiddenFlag) {
		this.hiddenFlag = hiddenFlag;
	}*/

	public ArrayList getCustomerList() {
		return customerList;
	}

	public void setCustomerList(ArrayList customerList) {
		this.customerList = customerList;
	}

	
	public String getHiddenBCPName() {
		return hiddenBCPName;
	}

	public void setHiddenBCPName(String hiddenBCPName) {
		this.hiddenBCPName = hiddenBCPName;
	}

	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}

	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
	}

	public int getUpdateFlag() {
		return updateFlag;
	}

	public void setUpdateFlag(int updateFlag) {
		this.updateFlag = updateFlag;
	}

	/*public String getActiontype() {
		return actiontype;
	}

	public void setActiontype(String actiontype) {
		this.actiontype = actiontype;
	}
*/
	public String getAccountIdStr() {
		return accountIdStr;
	}

	public void setAccountIdStr(String accountIdStr) {
		this.accountIdStr = accountIdStr;
	}

	public String getAccountNameStr() {
		return accountNameStr;
	}

	public void setAccountNameStr(String accountNameStr) {
		this.accountNameStr = accountNameStr;
	}

	public void reset() {

		accountIdStr = null;
		accountNameStr = null;

		//actiontype = null;
		
		//hiddenFlag = null;
		customerList = null;

		searchAccount = null;
		list = null;
		customerName = null;

		updateFlag = 0;

		accountID = null;

		accountName = null;

		accountManager = null;

		accphoneNo = 0;

		projectManager = null;

		lob = null;

		spFirstname = null;

		spLastName = null;

		spLPhno = null;

		spLEmail = null;

		region = null;

		zone = null;

		firstname = null;

		lastName = null;

		telephonePhno = null;

		email_Id = null;

		address1 = null;
		address2 = null;
		address3 = null;
		address4 = null;

		fax = null;
		pin = null;
		countryCode = null;
		postalCode = null;
		city = null;
		state = null;
		country = null;

		title = null;
		state_Id = null;

	}

	public String getSearchBCP() {
		return searchBCP;
	}

	public void setSearchBCP(String searchBCP) {
		this.searchBCP = searchBCP;
	}

	public String getBcpId() {
		return bcpId;
	}

	public void setBcpId(String bcpId) {
		this.bcpId = bcpId;
	}

	public String getBcpIdStr() {
		return bcpIdStr;
	}

	public void setBcpIdStr(String bcpIdStr) {
		this.bcpIdStr = bcpIdStr;
	}

	public String getBcpNameStr() {
		return bcpNameStr;
	}

	public void setBcpNameStr(String bcpNameStr) {
		this.bcpNameStr = bcpNameStr;
	}

	public String getBcpName() {
		return bcpName;
	}

	public void setBcpName(String bcpName) {
		this.bcpName = bcpName;
	}

	public int getHiddenBCPId() {
		return hiddenBCPId;
	}

	public void setHiddenBCPId(int hiddenBCPId) {
		this.hiddenBCPId = hiddenBCPId;
	}

	public ArrayList<CountryDto> getCountries() {
		return countries;
	}

	public void setCountries(ArrayList<CountryDto> countries) {
		this.countries = countries;
	}

	public ArrayList<CityDto> getCities() {
		return cities;
	}

	public void setCities(ArrayList<CityDto> cities) {
		this.cities = cities;
	}

	public ArrayList<StateDto> getStates() {
		return states;
	}

	public void setStates(ArrayList<StateDto> states) {
		this.states = states;
	}

}
