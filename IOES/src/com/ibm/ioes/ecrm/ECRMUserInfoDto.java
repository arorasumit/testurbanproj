package com.ibm.ioes.ecrm;
public class ECRMUserInfoDto {
	private String userid;
	private String email;
	private String roleid;
	private String firstName;
	private String lastName;
	private String phoneNo;
	private String role_resource_id;
	private String activeStartDate;
	private String endDate;
	
	public String getRole_resource_id() {
		return role_resource_id;
	}
	public void setRole_resource_id(String role_resource_id) {
		this.role_resource_id = role_resource_id;
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
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getActiveStartDate() {
		return activeStartDate;
	}
	public void setActiveStartDate(String activeStartDate) {
		this.activeStartDate = activeStartDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
