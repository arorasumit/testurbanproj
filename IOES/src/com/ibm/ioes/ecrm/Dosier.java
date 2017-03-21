package com.ibm.ioes.ecrm;


public class Dosier 
{

	

	private Long PartyId=null ;
	private String PartyNumber = null;
	private String  AccountName=null;
	private Long AccountMgrId=null;
	private Long VerticalId=null;
	private String verticalId=null;
	private Long AccountTypeId=null;
	private String  BBNLSegmentId=null;
	private String  PartyChannelPartnerId=null;
	private String  Address1=null;
	private String  Address2=null;
	private String  City=null;
	private String  State=null;
	private String  Pincode=null;
	private String  Status=null;
	private String  ErrorDesc=null;
	private Long ServiceSegment = null; 
	private String partyName=null;

	public Dosier()
	{
	}
	public Dosier(Long PartyId)
		{
	this.PartyId=PartyId;
		}


	public Dosier(Long PartyId,String PartyNumber,String AccountName,Long AccountMgrId,String verticalId,Long AccountTypeId,String BBNLSegmentId,String Address1,String Address2,String City,String State,String Pincode)
	{
		
		this.AccountMgrId=AccountMgrId;
		this.AccountName=AccountName;
		this.AccountTypeId=AccountTypeId;
		this.Address1=Address1;
		this.Address2=Address2;
		this.BBNLSegmentId=BBNLSegmentId;
		this.City=City;
		this.PartyId=PartyId;
		this.PartyNumber=PartyNumber;
		this.Pincode=Pincode;
		this.verticalId=verticalId;
		this.State=State;
	}


	
	public Long getKey()
	{
		return getPartyId();
	}

	
	


	/**
	 * @return
	 */
	public Long getAccountMgrId() {
		return AccountMgrId;
	}

	/**
	 * @return
	 */
	public String getAccountName() {
		return AccountName;
	}

	/**
	 * @return
	 */
	public Long getAccountTypeId() {
		return AccountTypeId;
	}

	/**
	 * @return
	 */
	public String getAddress1() {
		return Address1;
	}

	/**
	 * @return
	 */
	public String getAddress2() {
		return Address2;
	}

	/**
	 * @return
	 */
	public String getPartyChannelPartnerId() {
		return PartyChannelPartnerId;
	}

	/**
	 * @return
	 */
	public String getBBNLSegmentId() {
		return BBNLSegmentId;
	}
	
	/**
	 * @return
	 */
	public String getCity() {
		return City;
	}

	/**
	 * @return
	 */
	public String getErrorDesc() {
		return ErrorDesc;
	}

	/**
	 * @return
	 */
	public Long getPartyId() {
		return PartyId;
	}

	/**
	 * @return
	 */
	public String getPartyNumber() {
		return PartyNumber;
	}

	/**
	 * @return
	 */
	public String getPincode() {
		return Pincode;
	}

	/**
	 * @return
	 */
	public String getState() {
		return State;
	}

	/**
	 * @return
	 */
	public String getStatus() {
		return Status;
	}

	/**
	 * @return
	 */
	public Long getVerticalId() {
		return VerticalId;
	}

	/**
	 * @param l
	 */
	public void setAccountMgrId(Long l) {
		AccountMgrId = l;
	}

	/**
	 * @param string
	 */
	public void setAccountName(String string) {
		AccountName = string;
	}

	/**
	 * @param l
	 */
	public void setAccountTypeId(Long l) {
		AccountTypeId = l;
	}

	/**
	 * @param string
	 */
	public void setAddress1(String string) {
		Address1 = string;
	}

	/**
	 * @param string
	 */
	public void setAddress2(String string) {
		Address2 = string;
	}

	/**
	 * @param string
	 */
	public void setBBNLSegmentId(String string) {
		BBNLSegmentId = string;
	}
	/**
	 * @param string
	 */
	public void setPartyChannelPartnerId(String string) {
		PartyChannelPartnerId = string;
	}
	
	
	
	
	/**
	 * @param string
	 */
	public void setCity(String string) {
		City = string;
	}

	/**
	 * @param string
	 */
	public void setErrorDesc(String string) {
		ErrorDesc = string;
	}

	/**
	 * @param l
	 */
	public void setPartyId(Long l) {
		PartyId = l;
	}

	/**
	 * @param string
	 */
	public void setPartyNumber(String string) {
		PartyNumber = string;
	}

	/**
	 * @param string
	 */
	public void setPincode(String string) {
		Pincode = string;
	}

	/**
	 * @param string
	 */
	public void setState(String string) {
		State = string;
	}

	/**
	 * @param string
	 */
	public void setStatus(String string) {
		Status = string;
	}

	/**
	 * @param l
	 */
	public void setVerticalId(Long l) {
		VerticalId = l;
	}

	
	/**
	 * @return
	 */
	public Long getServiceSegment() {
		return ServiceSegment;
	}

	/**
	 * @param long1
	 */
	public void setServiceSegment(Long long1) {
		ServiceSegment = long1;
	}
	public void setVerticalId(String verticalId) {
		this.verticalId = verticalId;
	}

}