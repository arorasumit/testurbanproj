package com.ibm.ioes.npd.beans;

import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;

public class UploadProductPlanBean {
	
	public static final String ACTION_INITPAGE="ACTION_INITPAGE";
	
	public static final String ACTION_NEW_LOAD="ACTION_NEW_LOAD";
	public static final String ACTION_VALIDATE="ACTION_VALIDATE";
	public static final String ACTION_REPLACE="ACTION_REPLACE";
	public static final String ACTION_FINALIZE="ACTION_FINALIZE";
	
	private Long productId;
	private FormFile uploadFile = null;
	private String actionName = null;
	ActionMessages messages=null;
	Long createdBy=0l;
	String uploadFileName=null;
	String fileId=null;
	
	/*
	 * status>0: success , success:=0:iniital , success<0: failure
	 */
	int loadExcelProductPlan_status;// status>0: success , success:=0:iniital , success<0: failure
	public static final int loadExcelProductPlan_status_Success=1;
	public static final int loadExcelProductPlan_status_ValidationError=-1;
	public static final int loadExcelProductPlan_status_InsertionError=-2;
	
	
	/*
	 * status>0: success , success:=0:iniital , success<0: failure
	 */
	int validateExcelProductPlan_status;// status>0: success , success:=0:iniital , success<0: failure
	public static final int validateExcelProductPlan_status_Success=1;
	public static final int validateExcelProductPlan_status_ProcValiError=-1;
	public static final int validateExcelProductPlan_status_CyclicError=-2;
	
	
	/*
	 * status>0: success , success:=0:iniital , success<0: failure
	 */
	int replaceExcelProductPlan_status;// status>0: success , success:=0:iniital , success<0: failure
	public static final int replaceExcelProductPlan_status_Success=1;
	public static final int replaceExcelProductPlan_status_Failure=-1;
	
	/*
	 * status>0: success , success:=0:iniital , success<0: failure
	 */
	int finalizeExcelProductPlan_status;// status>0: success , success:=0:iniital , success<0: failure
	public static final int finalizeExcelProductPlan_status_Success=1;
	public static final int finalizeExcelProductPlan_status_Failure=-1;
	
	
	public static final int  PROGRESS_INIT=10;
	public static final int  PROGRESS_UPLOAD_FAILURE=15;
	public static final int  PROGRESS_UPLOAD_SUCCESS=20;
	public static final int  PROGRESS_VALIDATE_FAILURE=25;
	public static final int  PROGRESS_VALIDATE_SUCCESS=30;
	public static final int  PROGRESS_REPLACE_FAILURE=35;
	public static final int  PROGRESS_REPLACE_SUCCESS=40;
	public static final int  PROGRESS_FINALIZE_SUCCESS=45;
	public static final int  PROGRESS_FINALIZE_FAILURE=50;
	
	public static final int  DB_INIT=10;
	public static final int  DB_UPLOAD_FAILURE=15;
	public static final int  DB_UPLOAD_SUCCESS=20;
	public static final int  DB_VALIDATE_FAILURE=25;
	public static final int  DB_VALIDATE_SUCCESS=30;
	public static final int  DB_REPLACE_FAILURE=35;
	public static final int  DB_REPLACE_SUCCESS=40;
	public static final int  DB_FINALIZE_SUCCESS=45;
	public static final int  DB_FINALIZE_FAILURE=50;
	
	private int progress=0;
	
	public FormFile getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(FormFile uploadFile) {
		this.uploadFile = uploadFile;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public int getLoadExcelProductPlan_status() {
		return loadExcelProductPlan_status;
	}

	public void setLoadExcelProductPlan_status(int loadExcelProductPlan_status) {
		this.loadExcelProductPlan_status = loadExcelProductPlan_status;
	}

	public int getReplaceExcelProductPlan_status() {
		return replaceExcelProductPlan_status;
	}

	public void setReplaceExcelProductPlan_status(int replaceExcelProductPlan_status) {
		this.replaceExcelProductPlan_status = replaceExcelProductPlan_status;
	}

	public int getValidateExcelProductPlan_status() {
		return validateExcelProductPlan_status;
	}

	public void setValidateExcelProductPlan_status(
			int validateExcelProductPlan_status) {
		this.validateExcelProductPlan_status = validateExcelProductPlan_status;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public ActionMessages getMessages() {
		return messages;
	}

	public void setMessages(ActionMessages messages) {
		this.messages = messages;
	}



	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public int getFinalizeExcelProductPlan_status() {
		return finalizeExcelProductPlan_status;
	}

	public void setFinalizeExcelProductPlan_status(
			int finalizeExcelProductPlan_status) {
		this.finalizeExcelProductPlan_status = finalizeExcelProductPlan_status;
	}

	
	
	

}
