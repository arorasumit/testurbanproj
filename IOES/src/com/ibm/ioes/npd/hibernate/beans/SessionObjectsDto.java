package com.ibm.ioes.npd.hibernate.beans;

import com.ibm.ioes.npd.hibernate.dao.CommonBaseDao;

public class SessionObjectsDto {

	private static SessionObjectsDto sessionObjectsDto;

	private int attachmentSize;

	private int pageSize;

	private String specialCharacter;
	
	private String ganttChartDirPath;

	private SessionObjectsDto() {

	}

	// For lazy initialization
	public static synchronized SessionObjectsDto getInstance() {
		if (sessionObjectsDto == null) {
			sessionObjectsDto = new SessionObjectsDto();
			CommonBaseDao commonBaseDao = new CommonBaseDao();
			int attachmentSize = commonBaseDao.getAttachemntSize();
			int pageSize = commonBaseDao.getPageSize();
			String specialCharacter = commonBaseDao.getSpecialChar();
			
			sessionObjectsDto.setAttachmentSize(attachmentSize);
			sessionObjectsDto.setPageSize(pageSize);
			sessionObjectsDto.setSpecialCharacter(specialCharacter);
		}
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

	public String getGanttChartDirPath() {
		return ganttChartDirPath;
	}

	public void setGanttChartDirPath(String ganttChartDirPath) {
		this.ganttChartDirPath = ganttChartDirPath;
	}

}
