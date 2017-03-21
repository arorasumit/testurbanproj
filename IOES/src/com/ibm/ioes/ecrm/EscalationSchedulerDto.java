package com.ibm.ioes.ecrm;

public class EscalationSchedulerDto {
	
	private String role_resource_id;
	private String ROLE_ID;
	private String resource_number;
	private String user_id;
	private String user_name;
	private String first_name;
	private String SOURCE_LAST_NAME;
	private String phone_no;
	private String EMAIL_ADDRESS;
	private String usrActStatus;
	
	
	private String roleActStatus;
	private String L1_RoleId;
	private String level1_resource_id;
	private String level1_resource_Number;
	private String LEVEL1_source_FIRST_name;
	private String LEVEL1_source_LAST_name;
	private String level1_user_name;
	private String level1_user_id;
	private String level1_resource_phone;
	private String level1_resource_email;
	private String L2_RoleId;
	private String level2_resource_id;
	private String level2_resource_Number;
	private String level2_source_FIRST_name;
	private String level2_source_LAST_name;
	private String level2_user_name;
	private String level2_user_id;
	private String level2_resource_phone;
	private String level2_resource_email;
	private String L3_RoleId;
	private String level3_RESOURCE_ID;
	     
	private String level3_resource_Number;
	private String level3_resource_FIRST_name ;                                              
	private String level3_resource_LAST_name;                                               
	private String level3_user_name ;
	private String level3_user_id ;                                            
	private String level3_resource_phone ;                                             
	private String level3_resource_email;
	
	private int  finalActStatus;
	
	private String isUsrAct;
	
	public String getUsrActStatus() {
		return usrActStatus;
	}
	public void setUsrActStatus(String usrActStatus) {
		this.usrActStatus = usrActStatus;
	}
	public String getRoleActStatus() {
		return roleActStatus;
	}
	public void setRoleActStatus(String roleActStatus) {
		this.roleActStatus = roleActStatus;
	}
	
	
	public int getFinalActStatus() {
		return finalActStatus;
	}
	public void setFinalActStatus(int finalActStatus) {
		this.finalActStatus = finalActStatus;
	}
	public String getEMAIL_ADDRESS() {
		return EMAIL_ADDRESS;
	}
	public void setEMAIL_ADDRESS(String email_address) {
		EMAIL_ADDRESS = email_address;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	
	public String getLevel1_resource_email() {
		return level1_resource_email;
	}
	public void setLevel1_resource_email(String level1_resource_email) {
		this.level1_resource_email = level1_resource_email;
	}
	public String getLevel1_resource_id() {
		return level1_resource_id;
	}
	public void setLevel1_resource_id(String level1_resource_id) {
		this.level1_resource_id = level1_resource_id;
	}
	public String getLevel1_resource_Number() {
		return level1_resource_Number;
	}
	public void setLevel1_resource_Number(String level1_resource_Number) {
		this.level1_resource_Number = level1_resource_Number;
	}
	public String getLevel1_resource_phone() {
		return level1_resource_phone;
	}
	public void setLevel1_resource_phone(String level1_resource_phone) {
		this.level1_resource_phone = level1_resource_phone;
	}
	public String getLEVEL1_source_FIRST_name() {
		return LEVEL1_source_FIRST_name;
	}
	public void setLEVEL1_source_FIRST_name(String level1_source_first_name) {
		LEVEL1_source_FIRST_name = level1_source_first_name;
	}
	public String getLEVEL1_source_LAST_name() {
		return LEVEL1_source_LAST_name;
	}
	public void setLEVEL1_source_LAST_name(String level1_source_last_name) {
		LEVEL1_source_LAST_name = level1_source_last_name;
	}
	public String getLevel1_user_id() {
		return level1_user_id;
	}
	public void setLevel1_user_id(String level1_user_id) {
		this.level1_user_id = level1_user_id;
	}
	public String getLevel1_user_name() {
		return level1_user_name;
	}
	public void setLevel1_user_name(String level1_user_name) {
		this.level1_user_name = level1_user_name;
	}
	public String getLevel2_resource_email() {
		return level2_resource_email;
	}
	public void setLevel2_resource_email(String level2_resource_email) {
		this.level2_resource_email = level2_resource_email;
	}
	public String getLevel2_resource_id() {
		return level2_resource_id;
	}
	public void setLevel2_resource_id(String level2_resource_id) {
		this.level2_resource_id = level2_resource_id;
	}
	public String getLevel2_resource_Number() {
		return level2_resource_Number;
	}
	public void setLevel2_resource_Number(String level2_resource_Number) {
		this.level2_resource_Number = level2_resource_Number;
	}
	public String getLevel2_resource_phone() {
		return level2_resource_phone;
	}
	public void setLevel2_resource_phone(String level2_resource_phone) {
		this.level2_resource_phone = level2_resource_phone;
	}
	public String getLevel2_source_FIRST_name() {
		return level2_source_FIRST_name;
	}
	public void setLevel2_source_FIRST_name(String level2_source_FIRST_name) {
		this.level2_source_FIRST_name = level2_source_FIRST_name;
	}
	public String getLevel2_source_LAST_name() {
		return level2_source_LAST_name;
	}
	public void setLevel2_source_LAST_name(String level2_source_LAST_name) {
		this.level2_source_LAST_name = level2_source_LAST_name;
	}
	public String getLevel2_user_id() {
		return level2_user_id;
	}
	public void setLevel2_user_id(String level2_user_id) {
		this.level2_user_id = level2_user_id;
	}
	public String getLevel2_user_name() {
		return level2_user_name;
	}
	public void setLevel2_user_name(String level2_user_name) {
		this.level2_user_name = level2_user_name;
	}
	public String getLevel3_resource_email() {
		return level3_resource_email;
	}
	public void setLevel3_resource_email(String level3_resource_email) {
		this.level3_resource_email = level3_resource_email;
	}
	public String getLevel3_resource_FIRST_name() {
		return level3_resource_FIRST_name;
	}
	public void setLevel3_resource_FIRST_name(String level3_resource_FIRST_name) {
		this.level3_resource_FIRST_name = level3_resource_FIRST_name;
	}
	public String getLevel3_RESOURCE_ID() {
		return level3_RESOURCE_ID;
	}
	public void setLevel3_RESOURCE_ID(String level3_RESOURCE_ID) {
		this.level3_RESOURCE_ID = level3_RESOURCE_ID;
	}
	public String getLevel3_resource_LAST_name() {
		return level3_resource_LAST_name;
	}
	public void setLevel3_resource_LAST_name(String level3_resource_LAST_name) {
		this.level3_resource_LAST_name = level3_resource_LAST_name;
	}
	public String getLevel3_resource_Number() {
		return level3_resource_Number;
	}
	public void setLevel3_resource_Number(String level3_resource_Number) {
		this.level3_resource_Number = level3_resource_Number;
	}
	public String getLevel3_resource_phone() {
		return level3_resource_phone;
	}
	public void setLevel3_resource_phone(String level3_resource_phone) {
		this.level3_resource_phone = level3_resource_phone;
	}
	public String getLevel3_user_id() {
		return level3_user_id;
	}
	public void setLevel3_user_id(String level3_user_id) {
		this.level3_user_id = level3_user_id;
	}
	public String getLevel3_user_name() {
		return level3_user_name;
	}
	public void setLevel3_user_name(String level3_user_name) {
		this.level3_user_name = level3_user_name;
	}
	public String getPhone_no() {
		return phone_no;
	}
	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}
	public String getResource_number() {
		return resource_number;
	}
	public void setResource_number(String resource_number) {
		this.resource_number = resource_number;
	}
	public String getROLE_ID() {
		return ROLE_ID;
	}
	public void setROLE_ID(String role_id) {
		ROLE_ID = role_id;
	}
	public String getRole_resource_id() {
		return role_resource_id;
	}
	public void setRole_resource_id(String role_resource_id) {
		this.role_resource_id = role_resource_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getSOURCE_LAST_NAME() {
		return SOURCE_LAST_NAME;
	}
	public void setSOURCE_LAST_NAME(String source_last_name) {
		SOURCE_LAST_NAME = source_last_name;
	}
	public String getL1_RoleId() {
		return L1_RoleId;
	}
	public void setL1_RoleId(String roleId) {
		L1_RoleId = roleId;
	}
	public String getL2_RoleId() {
		return L2_RoleId;
	}
	public void setL2_RoleId(String roleId) {
		L2_RoleId = roleId;
	}
	public String getL3_RoleId() {
		return L3_RoleId;
	}
	public void setL3_RoleId(String roleId) {
		L3_RoleId = roleId;
	}
	public String getIsUsrAct() {
		return isUsrAct;
	}
	public void setIsUsrAct(String isUsrAct) {
		this.isUsrAct = isUsrAct;
	}

	
	
}
