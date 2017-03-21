package com.ibm.ioes.forms;

import java.util.Map;

public class SessionObjectsDto {

	private static SessionObjectsDto sessionObjectsDto;

	private int attachmentSize;
	private int pageSize;
	private String specialCharacter;
	private String global_path;
	private String customerCareEmailId;
	private String userId;
	private String userRoleId;
	private String userName;
	private String roleName;

	private SessionObjectsDto() {

	}

	// For lazy initialization
	public static synchronized SessionObjectsDto getInstance() {
		if (sessionObjectsDto == null) {
			sessionObjectsDto = new SessionObjectsDto();
		}
		return sessionObjectsDto;
	}

	public static SessionObjectsDto getSessionObjectsDto() {
		return sessionObjectsDto;
	}

	public static void setSessionObjectsDto(SessionObjectsDto sessionObjectsDto) {
		SessionObjectsDto.sessionObjectsDto = sessionObjectsDto;
	}

	public int getAttachmentSize() {
		return attachmentSize;
	}

	public void setAttachmentSize(int attachmentSize) {
		this.attachmentSize = attachmentSize;
	}

	public String getCustomerCareEmailId() {
		return customerCareEmailId;
	}

	public void setCustomerCareEmailId(String customerCareEmailId) {
		this.customerCareEmailId = customerCareEmailId;
	}

	public String getGlobal_path() {
		return global_path;
	}

	public void setGlobal_path(String global_path) {
		this.global_path = global_path;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getSpecialCharacter() {
		return specialCharacter;
	}

	public void setSpecialCharacter(String specialCharacter) {
		this.specialCharacter = specialCharacter;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(String userRoleId) {
		this.userRoleId = userRoleId;
	}

	

}
