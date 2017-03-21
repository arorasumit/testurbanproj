package com.ibm.ioes.forms;

public class PrdCatelogTemplateExcelDto {



	private String serviceId = null;
	
	private String productName = null;
	private String serviceType = null;
	
//	Service Summary
	private String serviceProductAttValue = null;
	private long serviceProductId ;

//	billing information
	private String custPODetailNo = null;
	private String poDate = null;
	private String contractPeriod = null;
	private String acNo = null;
	private String creditPeriod = null;
	private String currency = null;
	private String legalEntity = null;
	private String billingMode = null;
	private String billFormat = null;
	private String licenceCo = null;
	private String billingType = null;
	private String taxation = null;
	private String commitmentPeriod = null;
	private String billingLevel = null;
	private String penaltyClause = null;
	//billing address
	private String bcpDetails_BillingAddress = null;
	private String address1_BillingAddress = null;
	private String address2_BillingAddress = null;
	private String address3_BillingAddress = null;
	private String address4_BillingAddress = null;
	private String city_BillingAddress = null;
	private String state_BillingAddress = null;
	private String postalCode_BillingAddress = null;
	private String country_BillingAddress = null;


//	service Location
	private String locType_Primary = null;
	private String locationCode_Primary = null;
	private String address_Primary = null;
	private String bcpDetails_Primary = null;
	private String address1_Primary = null;
	private String address2_Primary = null;
	private String address3_Primary = null;
	private String address4_Primary = null;
	private String city_Primary = null;
	private String state_Primary = null;
	private String postalCode_Primary = null;
	private String country_Primary = null;

	private String locType_Secondary = null;
	private String locationCode_Secondary = null;
	private String address_Secondary = null;
	private String bcpDetails_Secondary = null;
	private String address1_Secondary = null;
	private String address2_Secondary = null;
	private String address3_Secondary = null;
	private String address4_Secondary = null;
	private String city_Secondary = null;
	private String state_Secondary = null;
	private String postalCode_Secondary = null;
	private String country_Secondary = null;

//	hardware
	private String store = null;
	private String hardwareType = null;
	private String formAvailable = null;
	private String natureOfSale = null;
	private String typeOfSale = null;

//	dispatch address
	private String dispatchAddressCode = null;
	private String address = null;
	private String cityName = null;
	private String stateName = null;
	private String pinCode = null;
	private String phoneNo = null;
	private String countryName = null;

//	chargesDetails
	private String type = null;
	private String name = null;
	private String chargePeriod = null;
	private String totalAmount = null;
	private String frequency = null;
	private String frequencyAmount = null;
	private String startDateLogic = null;
	private String endDateLogic = null;
	
	public String getAcNo() {
		return acNo;
	}
	public void setAcNo(String acNo) {
		this.acNo = acNo;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAddress_Primary() {
		return address_Primary;
	}
	public void setAddress_Primary(String address_Primary) {
		this.address_Primary = address_Primary;
	}
	public String getAddress_Secondary() {
		return address_Secondary;
	}
	public void setAddress_Secondary(String address_Secondary) {
		this.address_Secondary = address_Secondary;
	}
	public String getAddress1_Primary() {
		return address1_Primary;
	}
	public void setAddress1_Primary(String address1_Primary) {
		this.address1_Primary = address1_Primary;
	}
	public String getAddress1_Secondary() {
		return address1_Secondary;
	}
	public void setAddress1_Secondary(String address1_Secondary) {
		this.address1_Secondary = address1_Secondary;
	}
	public String getAddress2_Primary() {
		return address2_Primary;
	}
	public void setAddress2_Primary(String address2_Primary) {
		this.address2_Primary = address2_Primary;
	}
	public String getAddress2_Secondary() {
		return address2_Secondary;
	}
	public void setAddress2_Secondary(String address2_Secondary) {
		this.address2_Secondary = address2_Secondary;
	}
	public String getAddress3_Primary() {
		return address3_Primary;
	}
	public void setAddress3_Primary(String address3_Primary) {
		this.address3_Primary = address3_Primary;
	}
	public String getAddress3_Secondary() {
		return address3_Secondary;
	}
	public void setAddress3_Secondary(String address3_Secondary) {
		this.address3_Secondary = address3_Secondary;
	}
	public String getAddress4_Primary() {
		return address4_Primary;
	}
	public void setAddress4_Primary(String address4_Primary) {
		this.address4_Primary = address4_Primary;
	}
	public String getAddress4_Secondary() {
		return address4_Secondary;
	}
	public void setAddress4_Secondary(String address4_Secondary) {
		this.address4_Secondary = address4_Secondary;
	}
	public String getBcpDetails_Primary() {
		return bcpDetails_Primary;
	}
	public void setBcpDetails_Primary(String bcpDetails_Primary) {
		this.bcpDetails_Primary = bcpDetails_Primary;
	}
	public String getBcpDetails_Secondary() {
		return bcpDetails_Secondary;
	}
	public void setBcpDetails_Secondary(String bcpDetails_Secondary) {
		this.bcpDetails_Secondary = bcpDetails_Secondary;
	}
	public String getBillFormat() {
		return billFormat;
	}
	public void setBillFormat(String billFormat) {
		this.billFormat = billFormat;
	}
	public String getBillingLevel() {
		return billingLevel;
	}
	public void setBillingLevel(String billingLevel) {
		this.billingLevel = billingLevel;
	}
	public String getBillingMode() {
		return billingMode;
	}
	public void setBillingMode(String billingMode) {
		this.billingMode = billingMode;
	}
	public String getBillingType() {
		return billingType;
	}
	public void setBillingType(String billingType) {
		this.billingType = billingType;
	}
	public String getChargePeriod() {
		return chargePeriod;
	}
	public void setChargePeriod(String chargePeriod) {
		this.chargePeriod = chargePeriod;
	}
	public String getCity_Primary() {
		return city_Primary;
	}
	public void setCity_Primary(String city_Primary) {
		this.city_Primary = city_Primary;
	}
	public String getCity_Secondary() {
		return city_Secondary;
	}
	public void setCity_Secondary(String city_Secondary) {
		this.city_Secondary = city_Secondary;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCommitmentPeriod() {
		return commitmentPeriod;
	}
	public void setCommitmentPeriod(String commitmentPeriod) {
		this.commitmentPeriod = commitmentPeriod;
	}
	public String getContractPeriod() {
		return contractPeriod;
	}
	public void setContractPeriod(String contractPeriod) {
		this.contractPeriod = contractPeriod;
	}
	public String getCountry_Primary() {
		return country_Primary;
	}
	public void setCountry_Primary(String country_Primary) {
		this.country_Primary = country_Primary;
	}
	public String getCountry_Secondary() {
		return country_Secondary;
	}
	public void setCountry_Secondary(String country_Secondary) {
		this.country_Secondary = country_Secondary;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getCreditPeriod() {
		return creditPeriod;
	}
	public void setCreditPeriod(String creditPeriod) {
		this.creditPeriod = creditPeriod;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getCustPODetailNo() {
		return custPODetailNo;
	}
	public void setCustPODetailNo(String custPODetailNo) {
		this.custPODetailNo = custPODetailNo;
	}
	public String getDispatchAddressCode() {
		return dispatchAddressCode;
	}
	public void setDispatchAddressCode(String dispatchAddressCode) {
		this.dispatchAddressCode = dispatchAddressCode;
	}
	public String getEndDateLogic() {
		return endDateLogic;
	}
	public void setEndDateLogic(String endDateLogic) {
		this.endDateLogic = endDateLogic;
	}
	public String getFormAvailable() {
		return formAvailable;
	}
	public void setFormAvailable(String formAvailable) {
		this.formAvailable = formAvailable;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public String getFrequencyAmount() {
		return frequencyAmount;
	}
	public void setFrequencyAmount(String frequencyAmount) {
		this.frequencyAmount = frequencyAmount;
	}
	public String getHardwareType() {
		return hardwareType;
	}
	public void setHardwareType(String hardwareType) {
		this.hardwareType = hardwareType;
	}
	public String getLegalEntity() {
		return legalEntity;
	}
	public void setLegalEntity(String legalEntity) {
		this.legalEntity = legalEntity;
	}
	public String getLicenceCo() {
		return licenceCo;
	}
	public void setLicenceCo(String licenceCo) {
		this.licenceCo = licenceCo;
	}
	public String getLocationCode_Primary() {
		return locationCode_Primary;
	}
	public void setLocationCode_Primary(String locationCode_Primary) {
		this.locationCode_Primary = locationCode_Primary;
	}
	public String getLocationCode_Secondary() {
		return locationCode_Secondary;
	}
	public void setLocationCode_Secondary(String locationCode_Secondary) {
		this.locationCode_Secondary = locationCode_Secondary;
	}
	public String getLocType_Primary() {
		return locType_Primary;
	}
	public void setLocType_Primary(String locType_Primary) {
		this.locType_Primary = locType_Primary;
	}
	public String getLocType_Secondary() {
		return locType_Secondary;
	}
	public void setLocType_Secondary(String locType_Secondary) {
		this.locType_Secondary = locType_Secondary;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNatureOfSale() {
		return natureOfSale;
	}
	public void setNatureOfSale(String natureOfSale) {
		this.natureOfSale = natureOfSale;
	}
	public String getPenaltyClause() {
		return penaltyClause;
	}
	public void setPenaltyClause(String penaltyClause) {
		this.penaltyClause = penaltyClause;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getPinCode() {
		return pinCode;
	}
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}
	public String getPoDate() {
		return poDate;
	}
	public void setPoDate(String poDate) {
		this.poDate = poDate;
	}
	public String getPostalCode_Primary() {
		return postalCode_Primary;
	}
	public void setPostalCode_Primary(String postalCode_Primary) {
		this.postalCode_Primary = postalCode_Primary;
	}
	public String getPostalCode_Secondary() {
		return postalCode_Secondary;
	}
	public void setPostalCode_Secondary(String postalCode_Secondary) {
		this.postalCode_Secondary = postalCode_Secondary;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getStartDateLogic() {
		return startDateLogic;
	}
	public void setStartDateLogic(String startDateLogic) {
		this.startDateLogic = startDateLogic;
	}
	public String getState_Primary() {
		return state_Primary;
	}
	public void setState_Primary(String state_Primary) {
		this.state_Primary = state_Primary;
	}
	public String getState_Secondary() {
		return state_Secondary;
	}
	public void setState_Secondary(String state_Secondary) {
		this.state_Secondary = state_Secondary;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getStore() {
		return store;
	}
	public void setStore(String store) {
		this.store = store;
	}
	public String getTaxation() {
		return taxation;
	}
	public void setTaxation(String taxation) {
		this.taxation = taxation;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTypeOfSale() {
		return typeOfSale;
	}
	public void setTypeOfSale(String typeOfSale) {
		this.typeOfSale = typeOfSale;
	}
	public String getAddress1_BillingAddress() {
		return address1_BillingAddress;
	}
	public void setAddress1_BillingAddress(String address1_BillingAddress) {
		this.address1_BillingAddress = address1_BillingAddress;
	}
	public String getAddress2_BillingAddress() {
		return address2_BillingAddress;
	}
	public void setAddress2_BillingAddress(String address2_BillingAddress) {
		this.address2_BillingAddress = address2_BillingAddress;
	}
	public String getAddress3_BillingAddress() {
		return address3_BillingAddress;
	}
	public void setAddress3_BillingAddress(String address3_BillingAddress) {
		this.address3_BillingAddress = address3_BillingAddress;
	}
	public String getAddress4_BillingAddress() {
		return address4_BillingAddress;
	}
	public void setAddress4_BillingAddress(String address4_BillingAddress) {
		this.address4_BillingAddress = address4_BillingAddress;
	}
	public String getBcpDetails_BillingAddress() {
		return bcpDetails_BillingAddress;
	}
	public void setBcpDetails_BillingAddress(String bcpDetails_BillingAddress) {
		this.bcpDetails_BillingAddress = bcpDetails_BillingAddress;
	}
	public String getCity_BillingAddress() {
		return city_BillingAddress;
	}
	public void setCity_BillingAddress(String city_BillingAddress) {
		this.city_BillingAddress = city_BillingAddress;
	}
	public String getCountry_BillingAddress() {
		return country_BillingAddress;
	}
	public void setCountry_BillingAddress(String country_BillingAddress) {
		this.country_BillingAddress = country_BillingAddress;
	}
	public String getPostalCode_BillingAddress() {
		return postalCode_BillingAddress;
	}
	public void setPostalCode_BillingAddress(String postalCode_BillingAddress) {
		this.postalCode_BillingAddress = postalCode_BillingAddress;
	}
	public String getState_BillingAddress() {
		return state_BillingAddress;
	}
	public void setState_BillingAddress(String state_BillingAddress) {
		this.state_BillingAddress = state_BillingAddress;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getServiceProductAttValue() {
		return serviceProductAttValue;
	}
	public void setServiceProductAttValue(String serviceProductAttValue) {
		this.serviceProductAttValue = serviceProductAttValue;
	}
	public long getServiceProductId() {
		return serviceProductId;
	}
	public void setServiceProductId(long serviceProductId) {
		this.serviceProductId = serviceProductId;
	}
}
