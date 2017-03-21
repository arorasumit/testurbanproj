package com.ibm.ioes.forms;

/*Tag Name Resource Name  Date		CSR No			Description
 [001]	 Kalpana		13-JUN-13	CBR-CSR-20130404-XX-08714     For M6 Editable fields ,added isServiceSummReadonly to save READONLY flag of attributes
 * 
 */
import java.util.ArrayList;

public class FieldAttibuteDTO {
	private String attLabel;
	private int attMaxLength;

	private String attributeLabel;

	private String dataType;

	private String alisName;

	private String attributeValue;

	private int attributeID;

	private String expectedValue;

	private String mandatory;

	private String region;

	// -----------For Field Role Wise Validation------------New Order Changes-
	String isReadOnly;

	String isMand;

	String currentRole;

	String feildName;

	String fieldLabel;
	private int prodAttributeID;
	private String prodAttributeLabel;
	private String prodDataType;
	private String prodAlisName;
	private int isServiceSummMandatory;
	private ArrayList<FieldAttibuteDTO> serviceSummary;
	private String prodExpectedValue;
	private ArrayList<FieldAttibuteDTO> serviceSummaryLov;
	private int prodAttMaxLength;
	private String defValue;
	private String prodAttVal;
	private String newProdAttVal;
	private String attServiceName;
	private int  subchangetypeNetworkChangeEditable;
	private String planName;
	private int destAttId;
	private String zone;
	
	private long attMasterId;
	private String attributeKey;
	private String linkPopUpId;
	private int changetypeSolutionChangeEditable;
	private String guiAlert;
	private String destText;
	private String for_validation;
	private int isServiceSummReadonly;
    private int isCommercial;
    //start Deepak for Third Party(SCM)
	private int parntAttId;
	private int isParentAtt;
	private int sendToSCM;
	private int notSaveInScm;
	private String attDefaultVal;
	private String rfsDate;
	//end Deepak
	public int getIsCommercial() {
		return isCommercial;
	}
	public void setIsCommercial(int isCommercial) {
		this.isCommercial = isCommercial;
	}
	
	public String getFor_validation() {
		return for_validation;
	}


	public void setFor_validation(String for_validation) {
		this.for_validation = for_validation;
	}


	public String getZone() {
		return zone;
	}


	public void setZone(String zone) {
		this.zone = zone;
	}


	public int getDestAttId() {
		return destAttId;
	}


	public void setDestAttId(int destAttId) {
		this.destAttId = destAttId;
	}

	public String getPlanName() {
		return planName;
	}


	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public int getSubchangetypeNetworkChangeEditable() {
		return subchangetypeNetworkChangeEditable;
	}


	public void setSubchangetypeNetworkChangeEditable(
			int subchangetypeNetworkChangeEditable) {
		this.subchangetypeNetworkChangeEditable = subchangetypeNetworkChangeEditable;
	}
	public String getAttServiceName() {
		return attServiceName;
	}


	public void setAttServiceName(String attServiceName) {
		this.attServiceName = attServiceName;
	}

	public String getNewProdAttVal() {
		return newProdAttVal;
	}


	public void setNewProdAttVal(String newProdAttVal) {
		this.newProdAttVal = newProdAttVal;
	}

	public String getProdAttVal() {
		return prodAttVal;
	}

	public void setProdAttVal(String prodAttVal) {
		this.prodAttVal = prodAttVal;
	}
	public String getDefValue() {
		return defValue;
	}


	public void setDefValue(String defValue) {
		this.defValue = defValue;
	}

	public int getProdAttMaxLength() {
		return prodAttMaxLength;
	}

	public void setProdAttMaxLength(int prodAttMaxLength) {
		this.prodAttMaxLength = prodAttMaxLength;
	}
	public ArrayList<FieldAttibuteDTO> getServiceSummaryLov() {
		return serviceSummaryLov;
	}


	public void setServiceSummaryLov(ArrayList<FieldAttibuteDTO> serviceSummaryLov) {
		this.serviceSummaryLov = serviceSummaryLov;
	}
	public String getProdExpectedValue() {
		return prodExpectedValue;
	}

	public void setProdExpectedValue(String prodExpectedValue) {
		this.prodExpectedValue = prodExpectedValue;
	}
	public ArrayList<FieldAttibuteDTO> getServiceSummary() {
		return serviceSummary;
	}

	public void setServiceSummary(ArrayList<FieldAttibuteDTO> serviceSummary) {
		this.serviceSummary = serviceSummary;
	}
	public int getIsServiceSummMandatory() {
		return isServiceSummMandatory;
	}


	public void setIsServiceSummMandatory(int isServiceSummMandatory) {
		this.isServiceSummMandatory = isServiceSummMandatory;
	}
	public String getProdAlisName() {
		return prodAlisName;
	}

	public void setProdAlisName(String prodAlisName) {
		this.prodAlisName = prodAlisName;
	}
	public String getProdDataType() {
		return prodDataType;
	}

	public void setProdDataType(String prodDataType) {
		this.prodDataType = prodDataType;
	}
	public String getProdAttributeLabel() {
		return prodAttributeLabel;
	}

	public void setProdAttributeLabel(String prodAttributeLabel) {
		this.prodAttributeLabel = prodAttributeLabel;
	}

	private String serviceSummaryText = null;
	public int getProdAttributeID() {
		return prodAttributeID;
	}

	public void setProdAttributeID(int prodAttributeID) {
		this.prodAttributeID = prodAttributeID;
	}

	public String getServiceSummaryText() {
		return serviceSummaryText;
	}

	public void setServiceSummaryText(String serviceSummaryText) {
		this.serviceSummaryText = serviceSummaryText;
	}

	private String serviceSummaryValues;

	public String getServiceSummaryValues() {
		return serviceSummaryValues;
	}

	public void setServiceSummaryValues(String serviceSummaryValues) {
		this.serviceSummaryValues = serviceSummaryValues;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getFieldLabel() {
		return fieldLabel;
	}

	public void setFieldLabel(String fieldLabel) {
		this.fieldLabel = fieldLabel;
	}

	public String getIsReadOnly() {
		return isReadOnly;
	}

	public void setIsReadOnly(String isReadOnly) {
		this.isReadOnly = isReadOnly;
	}

	public String getIsMand() {
		return isMand;
	}

	public void setIsMand(String isMand) {
		this.isMand = isMand;
	}

	public String getFeildName() {
		return feildName;
	}

	public void setFeildName(String feildName) {
		this.feildName = feildName;
	}

	public String getMandatory() {
		return mandatory;
	}

	public void setMandatory(String mandatory) {
		this.mandatory = mandatory;
	}

	public int getAttributeID() {
		return attributeID;
	}

	public void setAttributeID(int attributeID) {
		this.attributeID = attributeID;
	}

	public String getAttributeValue() {
		return attributeValue;
	}

	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}

	public String getAttributeLabel() {
		return attributeLabel;
	}

	public void setAttributeLabel(String attributeLabel) {
		this.attributeLabel = attributeLabel;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public int getAttMaxLength() {
		return attMaxLength;
	}

	public void setAttMaxLength(int attMaxLength) {
		this.attMaxLength = attMaxLength;
	}

	public String getAlisName() {
		return alisName;
	}

	public void setAlisName(String alisName) {
		this.alisName = alisName;
	}

	public String getExpectedValue() {
		return expectedValue;
	}

	public void setExpectedValue(String expectedValue) {
		this.expectedValue = expectedValue;
	}


	public String getCurrentRole() {
		return currentRole;
	}


	public void setCurrentRole(String currentRole) {
		this.currentRole = currentRole;
	}


	public String getAttLabel() {
		return attLabel;
	}


	public void setAttLabel(String attLabel) {
		this.attLabel = attLabel;
	}


	public String getGuiAlert() {
		return guiAlert;
	}


	public void setGuiAlert(String guiAlert) {
		this.guiAlert = guiAlert;
	}


	public String getLinkPopUpId() {
		return linkPopUpId;
	}


	public void setLinkPopUpId(String linkPopUpId) {
		this.linkPopUpId = linkPopUpId;
	}


	public long getAttMasterId() {
		return attMasterId;
	}


	public void setAttMasterId(long attMasterId) {
		this.attMasterId = attMasterId;
	}


	public String getAttributeKey() {
		return attributeKey;
	}


	public void setAttributeKey(String attributeKey) {
		this.attributeKey = attributeKey;
	}


	public int getChangetypeSolutionChangeEditable() {
		return changetypeSolutionChangeEditable;
	}


	public void setChangetypeSolutionChangeEditable(
			int changetypeSolutionChangeEditable) {
		this.changetypeSolutionChangeEditable = changetypeSolutionChangeEditable;
	}


	public String getDestText() {
		return destText;
	}


	public void setDestText(String destText) {
		this.destText = destText;
	}


	public int getIsServiceSummReadonly() {
		return isServiceSummReadonly;
	}


	public void setIsServiceSummReadonly(int isServiceSummReadonly) {
		this.isServiceSummReadonly = isServiceSummReadonly;
	}
	public int getSendToSCM() {
		return sendToSCM;
	}


	public void setSendToSCM(int sendToSCM) {
		this.sendToSCM = sendToSCM;
	}
	public int getNotSaveInScm() {
		return notSaveInScm;
	}


	public void setNotSaveInScm(int notSaveInScm) {
		this.notSaveInScm = notSaveInScm;
	}
	public int getParntAttId() {
		return parntAttId;
	}


	public void setParntAttId(int parntAttId) {
		this.parntAttId = parntAttId;
	}


	public int getIsParentAtt() {
		return isParentAtt;
	}


	public void setIsParentAtt(int isParentAtt) {
		this.isParentAtt = isParentAtt;
	}
	public String getAttDefaultVal() {
		return attDefaultVal;
	}
	public void setAttDefaultVal(String attDefaultVal) {
		this.attDefaultVal = attDefaultVal;
	}
	public String getRfsDate() {
		return rfsDate;
	}
	public void setRfsDate(String rfsDate) {
		this.rfsDate = rfsDate;
	}
}
