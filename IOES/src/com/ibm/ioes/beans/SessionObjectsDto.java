package com.ibm.ioes.beans;

import com.ibm.ioes.dao.CommonBaseDao;



public class SessionObjectsDto {

	private static SessionObjectsDto sessionObjectsDto;

	private int attachmentSize;

	private int pageSize;

	private String specialCharacter;
	
	private String userId;
	private String userRoleId;
	private String userName;
	private String roleName;
	

	public static SessionObjectsDto getSessionObjectsDto() {
		return sessionObjectsDto;
	}

	public static void setSessionObjectsDto(SessionObjectsDto sessionObjectsDto) {
		SessionObjectsDto.sessionObjectsDto = sessionObjectsDto;
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

	private SessionObjectsDto() {

	}

	// For lazy initialization
	public static synchronized SessionObjectsDto getInstance() {
		if (sessionObjectsDto == null) {
			sessionObjectsDto = new SessionObjectsDto();
/*			CommonBaseDao commonBaseDao = new CommonBaseDao();
			int attachmentSize = commonBaseDao.getAttachemntSize();
			int pageSize = commonBaseDao.getPageSize();
			String specialCharacter = commonBaseDao.getSpecialChar();
			
			sessionObjectsDto.setAttachmentSize(attachmentSize);
			sessionObjectsDto.setPageSize(pageSize);
			sessionObjectsDto.setSpecialCharacter(specialCharacter);
*/		}
		return sessionObjectsDto;
	}

	public int getAttachmentSize() {
		return attachmentSize;
	}

	public void setAttachmentSize(int attachmentSize) {
		this.attachmentSize = attachmentSize;
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



}
