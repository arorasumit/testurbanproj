package com.ibm.ioes.beans;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.ibm.ioes.forms.CityDto;
import com.ibm.ioes.forms.CountryDto;
import com.ibm.ioes.forms.StateDto;
import com.ibm.ioes.utilities.PagingSorting;

public class LocationDetailBean extends ActionForm
{
	PagingSorting pagingSorting=new PagingSorting();
	//****************for search**************8
	private String accountIdStr;
	private String accountIdStr1;
	private String accountNameStr;
	private String accountNameStr1;
	
	private String locationIdStr;
	private String locationNameStr;
	private String searchAccount;
	private String searchLocation;
	
	private String actiontype;
	private int hiddenLocationId;
	private int updateFlag;
	private String hiddenLocationName;
	private String hiddenFlag=null;
	
	private ArrayList customerList;
	private ArrayList list;
	
	
	//***********for Adding new location**************8
	private String locationId;
	private String locationName;
	private String customerName;
	private String accountID;
	private String accountName;
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
	
	//*********8feting country,state,city dor add**************
	ArrayList<CountryDto> countries = null;
	ArrayList<StateDto> states = null;
	ArrayList<CityDto> cities = null;

	private String ddCountry;
	private String ddState;
	private String ddCity;
	private String countryID;
	private String countryName;
	private String cityID;
	private String cityName;
	private String stateID;
	private String stateName;
	
//	*********8feting country,state,city dor edit**************
	private String editddCountry;
	private String editddState;
	private String editddCity;
	private String editcountryID;
	private String editcountryName;
	private String editcityID;
	private String editcityName;
	private String editstateID;
	private String editstateName;
	
//	*********8feting account information**************
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
	
 
	

	//***********for Adding new location**************8
	private String editlocationId;
	private String editlocationName;
	private String editcustomerName;
	private String editaccountID;
	private String editaccountName;
	private String editfirstname;
	private String editlastName;
	private String edittelephonePhno;
	private String editemail_Id;
	
	private String editaddress1;
	private String editaddress2;
	private String editaddress3;
	private String editaddress4;
	private String editfax;
	private String editpin;
	private String editcountryCode;
	private String editpostalCode;
	private String editcity;
	private String editstate;
	private String editcountry;
	private String edittitle;
	private String editstate_Id;

	

	

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
	

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
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

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	/*public String getAccountManager() {
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

	public int getSpLPhno() {
		return spLPhno;
	}

	public void setSpLPhno(int spLPhno) {
		this.spLPhno = spLPhno;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}
*/
	public String getSearchLocation() {
		return searchLocation;
	}

	public void setSearchLocation(String searchLocation) {
		this.searchLocation = searchLocation;
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

	public String getHiddenFlag() {
		return hiddenFlag;
	}

	public void setHiddenFlag(String hiddenFlag) {
		this.hiddenFlag = hiddenFlag;
	}

	public ArrayList getCustomerList() {
		return customerList;
	}

	public void setCustomerList(ArrayList customerList) {
		this.customerList = customerList;
	}

	public int getHiddenLocationId() {
		return hiddenLocationId;
	}

	public void setHiddenLocationId(int hiddenLocationId) {
		this.hiddenLocationId = hiddenLocationId;
	}

	public String getHiddenLocationName() {
		return hiddenLocationName;
	}

	public void setHiddenLocationName(String hiddenLocationName) {
		this.hiddenLocationName = hiddenLocationName;
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

	public String getActiontype() {
		return actiontype;
	}

	public void setActiontype(String actiontype) {
		this.actiontype = actiontype;
	}

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

	public String getLocationIdStr() {
		return locationIdStr;
	}

	public void setLocationIdStr(String locationIdStr) {
		this.locationIdStr = locationIdStr;
	}

	public String getLocationNameStr() {
		return locationNameStr;
	}

	public void setLocationNameStr(String locationNameStr) {
		this.locationNameStr = locationNameStr;
	}

	public String getEditaddress1() {
		return editaddress1;
	}

	public void setEditaddress1(String editaddress1) {
		this.editaddress1 = editaddress1;
	}

	public String getEditaddress2() {
		return editaddress2;
	}

	public void setEditaddress2(String editaddress2) {
		this.editaddress2 = editaddress2;
	}

	public String getEditaddress3() {
		return editaddress3;
	}

	public void setEditaddress3(String editaddress3) {
		this.editaddress3 = editaddress3;
	}

	public String getEditaddress4() {
		return editaddress4;
	}

	public void setEditaddress4(String editaddress4) {
		this.editaddress4 = editaddress4;
	}

	public String getEditcity() {
		return editcity;
	}

	public void setEditcity(String editcity) {
		this.editcity = editcity;
	}

	public String getEditcountry() {
		return editcountry;
	}

	public void setEditcountry(String editcountry) {
		this.editcountry = editcountry;
	}

	public String getEditcountryCode() {
		return editcountryCode;
	}

	public void setEditcountryCode(String editcountryCode) {
		this.editcountryCode = editcountryCode;
	}

	public String getEditemail_Id() {
		return editemail_Id;
	}

	public void setEditemail_Id(String editemail_Id) {
		this.editemail_Id = editemail_Id;
	}

	public String getEditfax() {
		return editfax;
	}

	public void setEditfax(String editfax) {
		this.editfax = editfax;
	}

	public String getEditfirstname() {
		return editfirstname;
	}

	public void setEditfirstname(String editfirstname) {
		this.editfirstname = editfirstname;
	}

	public String getEditlastName() {
		return editlastName;
	}

	public void setEditlastName(String editlastName) {
		this.editlastName = editlastName;
	}

	public String getEditpin() {
		return editpin;
	}

	public void setEditpin(String editpin) {
		this.editpin = editpin;
	}

	public String getEditpostalCode() {
		return editpostalCode;
	}

	public void setEditpostalCode(String editpostalCode) {
		this.editpostalCode = editpostalCode;
	}

	public String getEditstate() {
		return editstate;
	}

	public void setEditstate(String editstate) {
		this.editstate = editstate;
	}

	public String getEditstate_Id() {
		return editstate_Id;
	}

	public void setEditstate_Id(String editstate_Id) {
		this.editstate_Id = editstate_Id;
	}

	public String getEdittelephonePhno() {
		return edittelephonePhno;
	}

	public void setEdittelephonePhno(String edittelephonePhno) {
		this.edittelephonePhno = edittelephonePhno;
	}

	public String getEdittitle() {
		return edittitle;
	}

	public void setEdittitle(String edittitle) {
		this.edittitle = edittitle;
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

	public String getEditaccountID() {
		return editaccountID;
	}

	public void setEditaccountID(String editaccountID) {
		this.editaccountID = editaccountID;
	}

	public String getEditaccountName() {
		return editaccountName;
	}

	public void setEditaccountName(String editaccountName) {
		this.editaccountName = editaccountName;
	}

	public String getEditcustomerName() {
		return editcustomerName;
	}

	public void setEditcustomerName(String editcustomerName) {
		this.editcustomerName = editcustomerName;
	}

	public String getEditlocationId() {
		return editlocationId;
	}

	public void setEditlocationId(String editlocationId) {
		this.editlocationId = editlocationId;
	}

	public String getEditlocationName() {
		return editlocationName;
	}

	public void setEditlocationName(String editlocationName) {
		this.editlocationName = editlocationName;
	}

	@Override
	public void reset(ActionMapping arg0, HttpServletRequest arg1) {
		
		
		this.accountIdStr=null;
		 this.accountNameStr=null;
		 this.locationIdStr=null;
		 this.locationNameStr=null;
		
		 this.actiontype=null;
		 this.hiddenLocationId=0;
		 this.hiddenLocationName=null;
		 this.hiddenFlag=null;
		 this.customerList=null;
		 this.locationId=null;
		 this.searchAccount=null;
		 this.searchLocation=null;
		 this.list=null;
		 this.customerName=null;
		 this.locationName=null;
		 this.updateFlag=0;
		
		 this.accountID=null;
		
		 this.accountName=null;

		 this.accountManager=null;
			
		 this.accphoneNo=0;
			
		 this.projectManager=null;
			
		 this.lob=null;
			
		 this.spFirstname=null;
			
		 this.spLastName=null;
			
		 this.spLPhno=null;
			
		 this.spLEmail=null;
			
		 this.region=null;
			
		 this.zone=null;

		 this.firstname=null;
		
		 this.lastName=null;
		
		 this.telephonePhno=null;
		
		 this.email_Id=null;
		
		 this.address1=null;
		 this.address2=null;
		 this.address3=null;
		 this.address4=null;
		
		 this.fax=null;
		 this.pin=null;
		 this.countryCode=null;
		 this.postalCode=null;
		 this.city=null;
		 this.state=null;
		 this.country=null;
		

		 this.title=null;
		 this.state_Id=null;
		this.editlocationId=null;
		this.editlocationName=null;
		this.editcustomerName=null;
		this.editaccountID=null;
		this.editaccountName=null;
		this.editfirstname=null;
		this.editlastName=null;
		this.edittelephonePhno=null;
		this.editemail_Id=null;
			
		this.editaddress1=null;
		this.editaddress2=null;
		this.editaddress3=null;
		this.editaddress4=null;
		this.editfax=null;
		this.editpin=null;
		this.editcountryCode=null;
		this.editpostalCode=null;
		this.editcity=null;
		this.editstate=null;
		this.editcountry=null;
		this.edittitle=null;
		this.editstate_Id=null;
		
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

	public String getDdCountry() {
		return ddCountry;
	}

	public void setDdCountry(String ddCountry) {
		this.ddCountry = ddCountry;
	}

	public String getDdCity() {
		return ddCity;
	}

	public void setDdCity(String ddCity) {
		this.ddCity = ddCity;
	}

	public String getDdState() {
		return ddState;
	}

	public void setDdState(String ddState) {
		this.ddState = ddState;
	}

	public String getEditddCity() {
		return editddCity;
	}

	public void setEditddCity(String editddCity) {
		this.editddCity = editddCity;
	}

	public String getEditddCountry() {
		return editddCountry;
	}

	public void setEditddCountry(String editddCountry) {
		this.editddCountry = editddCountry;
	}

	public String getEditddState() {
		return editddState;
	}

	public void setEditddState(String editddState) {
		this.editddState = editddState;
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

	public String getEditcityID() {
		return editcityID;
	}

	public void setEditcityID(String editcityID) {
		this.editcityID = editcityID;
	}

	public String getEditcityName() {
		return editcityName;
	}

	public void setEditcityName(String editcityName) {
		this.editcityName = editcityName;
	}

	public String getEditstateID() {
		return editstateID;
	}

	public void setEditstateID(String editstateID) {
		this.editstateID = editstateID;
	}

	public String getEditstateName() {
		return editstateName;
	}

	public void setEditstateName(String editstateName) {
		this.editstateName = editstateName;
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

	public String getAccountIdStr1() {
		return accountIdStr1;
	}

	public void setAccountIdStr1(String accountIdStr1) {
		this.accountIdStr1 = accountIdStr1;
		/*if (accountIdStr1 != null && accountIdStr1 != ""){
			this.accountIdStr = accountIdStr1;
		}*/
	}

	public String getAccountNameStr1() {
		return accountNameStr1;
	}

	public void setAccountNameStr1(String accountNameStr1) {
		this.accountNameStr1 = accountNameStr1;
	}

}
	

	

	

	
	




	