package com.ibm.ioes.forms;

import java.io.InputStream;

public class AttachmentDto {
	InputStream inputStream=null;
	String docName=null;
	String fileType=null;
	String isDocAttached=null;
	
	
	String moduleId=null;
	String uniqueId=null;
	
	public String getDocName() {
		return docName;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public String getIsDocAttached() {
		return isDocAttached;
	}
	public void setIsDocAttached(String isDocAttached) {
		this.isDocAttached = isDocAttached;
	}
	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

}
