//[15032013011]	 VIJAY	        27-Feb-2013				Added a another column(upload by) on 'view document for download' page
package com.ibm.ioes.forms;
import java.sql.Blob;

import org.apache.struts.upload.FormFile;

public class FileAttachmentDto {

	private String fileName;
	private String createDate;
	private String description;
	private String fileValue;
	private FormFile fileData;
	private Blob file;
	private String hdnAccountNo;
	private String hdnOrderNo;
	private String isUpload;
	private String isDownload;
	private int slno;
	private String fileRename;
	private String crmAccountNo;
	private int fileTypeId;
	private String docName;
	private long empId;
	//--[15032013011]--start--//
	private String uploadedBy;
	//--[15032013011]--end--//
	private String emailId;
    private String sharepointUrl;                    //satish for view document
	public String getSharepointUrl() {
		return sharepointUrl;
	}
	public void setSharepointUrl(String sharepointUrl) {
		this.sharepointUrl = sharepointUrl;
	}
	
	
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getCrmAccountNo() {
		return crmAccountNo;
	}
	public void setCrmAccountNo(String crmAccountNo) {
		this.crmAccountNo = crmAccountNo;
	}
	public String getFileRename() {
		return fileRename;
	}
	public void setFileRename(String fileRename) {
		this.fileRename = fileRename;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileValue() {
		return fileValue;
	}
	public void setFileValue(String fileValue) {
		this.fileValue = fileValue;
	}
	public String getHdnAccountNo() {
		return hdnAccountNo;
	}
	public void setHdnAccountNo(String hdnAccountNo) {
		this.hdnAccountNo = hdnAccountNo;
	}
	public String getHdnOrderNo() {
		return hdnOrderNo;
	}
	public void setHdnOrderNo(String hdnOrderNo) {
		this.hdnOrderNo = hdnOrderNo;
	}
	public String getIsUpload() {
		return isUpload;
	}
	public void setIsUpload(String isUpload) {
		this.isUpload = isUpload;
	}
	public FormFile getFileData() {
		return fileData;
	}
	public void setFileData(FormFile fileData) {
		this.fileData = fileData;
	}
	
	public String getIsDownload() {
		return isDownload;
	}
	public void setIsDownload(String isDownload) {
		this.isDownload = isDownload;
	}
	public int getSlno() {
		return slno;
	}
	public void setSlno(int slno) {
		this.slno = slno;
	}
	public Blob getFile() {
		return file;
	}
	public void setFile(Blob file) {
		this.file = file;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getFileTypeId() {
		return fileTypeId;
	}
	public void setFileTypeId(int fileTypeId) {
		this.fileTypeId = fileTypeId;
	}
	public String getDocName() {
		return docName;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}
	public long getEmpId() {
		return empId;
	}
	public void setEmpId(long empId) {
		this.empId = empId;
	}
//	--[15032013011]--start--//
	public String getUploadedBy() {
		return uploadedBy;
	}
	public void setUploadedBy(String uploadedBy) {
		this.uploadedBy = uploadedBy;
	}
  //--[15032013011]--start--//
	
}
