package com.ibm.ioes.forms;

public class ComponentsDto {
	
	private int componentID;
	private int componentInfoID;
	private String activeDate;
	private String inactiveDate;
	private String componentName;
	private String componentStatus;
	private String componentFXStatus;
	private String componentFXTokenNo;
	private int packageID;
	private String packageName;
	
	private String createdInOrderNo;
	private String disconnectedInOrderNo;
	private String startDate;
	private String disconnectDate;
	private String startLogic;
	private String endLogic;
	private String componentInstanceID;
	private int component_ID;
	private String fxStartStatus;
	private String fxTokenNo;
	private String fxStartNo;
	private String endTokenNo;
	private String endFxStatus;
	private String endFxNo;
	private String startStatus;
	private String endStatus;
	private String disconnectionType;
	private String billingTriggerStatus;
	private String isLineDisconnected;
	private String disconnectedInCurrentService;
	
	private int isDisconnected;
	private int created_serviceid;
	private String  disconnection_type;
	private int inactive;
	private String start_date;
	private String componentInfoID_String;
	private String end_date;
	
//	 start
	private int isReqComponentid;
	private int isReqComponentname;
	private int isReqPackageid;
	private int isReqPackagename;
	private int isReqStatus;
	private int isReqStartdatelogic;
	private int isReqEnddateLogic;
	private int isReqStartDate;
	private int isReqEndDate;
	
	// end
	
	private String startDateLogic;
	private String endDateLogic;
	private int startDateDays;
	private int startDateMonth;
	private int endDateDays;
	private int endDateMonth;
	private String startDateLogicName;
	private String endDateLogicName;
	private int isclose;
	private String componentType;
	private double componentAmount;
	//[Start]**Added by Ashutosh***
	private String packageInstId;
	private String packageInstIdServ;
	
	public String getPackageInstId() {
		return packageInstId;
	}
	public void setPackageInstId(String packageInstId) {
		this.packageInstId = packageInstId;
	}
	public String getPackageInstIdServ() {
		return packageInstIdServ;
	}
	public void setPackageInstIdServ(String packageInstIdServ) {
		this.packageInstIdServ = packageInstIdServ;
	}
	//[End]*********************************
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public String getComponentInfoID_String() {
		return componentInfoID_String;
	}
	public int getComponent_ID() {
		return component_ID;
	}
	public void setComponent_ID(int component_ID) {
		this.component_ID = component_ID;
	}
	
	public String getDisconnectDate() {
		return disconnectDate;
	}
	public void setDisconnectDate(String disconnectDate) {
		this.disconnectDate = disconnectDate;
	}
	public String getEndLogic() {
		return endLogic;
	}
	public void setEndLogic(String endLogic) {
		this.endLogic = endLogic;
	}
	public String getFxStartNo() {
		return fxStartNo;
	}
	public void setFxStartNo(String fxStartNo) {
		this.fxStartNo = fxStartNo;
	}
	public String getFxStartStatus() {
		return fxStartStatus;
	}
	public void setFxStartStatus(String fxStartStatus) {
		this.fxStartStatus = fxStartStatus;
	}
	
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getStartLogic() {
		return startLogic;
	}
	public void setStartLogic(String startLogic) {
		this.startLogic = startLogic;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public int getPackageID() {
		return packageID;
	}
	public void setPackageID(int packageID) {
		this.packageID = packageID;
	}
	public String getComponentName() {
		return componentName;
	}
	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}
	public String getActiveDate() {
		return activeDate;
	}
	public void setActiveDate(String activeDate) {
		this.activeDate = activeDate;
	}
	public int getComponentID() {
		return componentID;
	}
	public void setComponentID(int componentID) {
		this.componentID = componentID;
	}
	public String getInactiveDate() {
		return inactiveDate;
	}
	public void setInactiveDate(String inactiveDate) {
		this.inactiveDate = inactiveDate;
	}
	public int getComponentInfoID() {
		return componentInfoID;
	}
	public void setComponentInfoID(int componentInfoID) {
		this.componentInfoID = componentInfoID;
	}
	public String getComponentFXStatus() {
		return componentFXStatus;
	}
	public void setComponentFXStatus(String componentFXStatus) {
		this.componentFXStatus = componentFXStatus;
	}
	public String getComponentFXTokenNo() {
		return componentFXTokenNo;
	}
	public void setComponentFXTokenNo(String componentFXTokenNo) {
		this.componentFXTokenNo = componentFXTokenNo;
	}
	public String getComponentStatus() {
		return componentStatus;
	}
	public void setComponentStatus(String componentStatus) {
		this.componentStatus = componentStatus;
	}
	
	public String getEndFxNo() {
		return endFxNo;
	}
	public void setEndFxNo(String endFxNo) {
		this.endFxNo = endFxNo;
	}
	public String getEndFxStatus() {
		return endFxStatus;
	}
	public void setEndFxStatus(String endFxStatus) {
		this.endFxStatus = endFxStatus;
	}
	
	public String getComponentInstanceID() {
		return componentInstanceID;
	}
	public void setComponentInstanceID(String componentInstanceID) {
		this.componentInstanceID = componentInstanceID;
	}
	public String getCreatedInOrderNo() {
		return createdInOrderNo;
	}
	public void setCreatedInOrderNo(String createdInOrderNo) {
		this.createdInOrderNo = createdInOrderNo;
	}
	public String getDisconnectedInOrderNo() {
		return disconnectedInOrderNo;
	}
	public void setDisconnectedInOrderNo(String disconnectedInOrderNo) {
		this.disconnectedInOrderNo = disconnectedInOrderNo;
	}
	public String getEndTokenNo() {
		return endTokenNo;
	}
	public void setEndTokenNo(String endTokenNo) {
		this.endTokenNo = endTokenNo;
	}
	public String getFxTokenNo() {
		return fxTokenNo;
	}
	public void setFxTokenNo(String fxTokenNo) {
		this.fxTokenNo = fxTokenNo;
	}
	public String getEndStatus() {
		return endStatus;
	}
	public void setEndStatus(String endStatus) {
		this.endStatus = endStatus;
	}
	public String getStartStatus() {
		return startStatus;
	}
	public void setStartStatus(String startStatus) {
		this.startStatus = startStatus;
	}
	public String getBillingTriggerStatus() {
		return billingTriggerStatus;
	}
	public void setBillingTriggerStatus(String billingTriggerStatus) {
		this.billingTriggerStatus = billingTriggerStatus;
	}
	public String getDisconnectedInCurrentService() {
		return disconnectedInCurrentService;
	}
	public void setDisconnectedInCurrentService(String disconnectedInCurrentService) {
		this.disconnectedInCurrentService = disconnectedInCurrentService;
	}
	public String getDisconnectionType() {
		return disconnectionType;
	}
	public void setDisconnectionType(String disconnectionType) {
		this.disconnectionType = disconnectionType;
	}
	public String getIsLineDisconnected() {
		return isLineDisconnected;
	}
	public void setIsLineDisconnected(String isLineDisconnected) {
		this.isLineDisconnected = isLineDisconnected;
	}
	public int getCreated_serviceid() {
		return created_serviceid;
	}
	public void setCreated_serviceid(int created_serviceid) {
		this.created_serviceid = created_serviceid;
	}
	public String getDisconnection_type() {
		return disconnection_type;
	}
	public void setDisconnection_type(String disconnection_type) {
		this.disconnection_type = disconnection_type;
	}
	public int getInactive() {
		return inactive;
	}
	public void setInactive(int inactive) {
		this.inactive = inactive;
	}
	public int getIsDisconnected() {
		return isDisconnected;
	}
	public void setIsDisconnected(int isDisconnected) {
		this.isDisconnected = isDisconnected;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public void setComponentInfoID_String(String componentInfoID_String) {
		this.componentInfoID_String = componentInfoID_String;
	}
	public int getIsReqComponentid() {
		return isReqComponentid;
	}
	public void setIsReqComponentid(int isReqComponentid) {
		this.isReqComponentid = isReqComponentid;
	}
	public int getIsReqComponentname() {
		return isReqComponentname;
	}
	public void setIsReqComponentname(int isReqComponentname) {
		this.isReqComponentname = isReqComponentname;
	}
	public int getIsReqEndDate() {
		return isReqEndDate;
	}
	public void setIsReqEndDate(int isReqEndDate) {
		this.isReqEndDate = isReqEndDate;
	}
	public int getIsReqEnddateLogic() {
		return isReqEnddateLogic;
	}
	public void setIsReqEnddateLogic(int isReqEnddateLogic) {
		this.isReqEnddateLogic = isReqEnddateLogic;
	}
	public int getIsReqPackageid() {
		return isReqPackageid;
	}
	public void setIsReqPackageid(int isReqPackageid) {
		this.isReqPackageid = isReqPackageid;
	}
	public int getIsReqPackagename() {
		return isReqPackagename;
	}
	public void setIsReqPackagename(int isReqPackagename) {
		this.isReqPackagename = isReqPackagename;
	}
	public int getIsReqStartDate() {
		return isReqStartDate;
	}
	public void setIsReqStartDate(int isReqStartDate) {
		this.isReqStartDate = isReqStartDate;
	}
	public int getIsReqStartdatelogic() {
		return isReqStartdatelogic;
	}
	public void setIsReqStartdatelogic(int isReqStartdatelogic) {
		this.isReqStartdatelogic = isReqStartdatelogic;
	}
	public int getIsReqStatus() {
		return isReqStatus;
	}
	public void setIsReqStatus(int isReqStatus) {
		this.isReqStatus = isReqStatus;
	}
	public int getEndDateDays() {
		return endDateDays;
	}
	public void setEndDateDays(int endDateDays) {
		this.endDateDays = endDateDays;
	}
	public String getEndDateLogic() {
		return endDateLogic;
	}
	public void setEndDateLogic(String endDateLogic) {
		this.endDateLogic = endDateLogic;
	}
	public int getEndDateMonth() {
		return endDateMonth;
	}
	public void setEndDateMonth(int endDateMonth) {
		this.endDateMonth = endDateMonth;
	}
	public int getStartDateDays() {
		return startDateDays;
	}
	public void setStartDateDays(int startDateDays) {
		this.startDateDays = startDateDays;
	}
	public String getStartDateLogic() {
		return startDateLogic;
	}
	public void setStartDateLogic(String startDateLogic) {
		this.startDateLogic = startDateLogic;
	}
	public int getStartDateMonth() {
		return startDateMonth;
	}
	public void setStartDateMonth(int startDateMonth) {
		this.startDateMonth = startDateMonth;
	}
	public String getEndDateLogicName() {
		return endDateLogicName;
	}
	public void setEndDateLogicName(String endDateLogicName) {
		this.endDateLogicName = endDateLogicName;
	}
	public String getStartDateLogicName() {
		return startDateLogicName;
	}
	public void setStartDateLogicName(String startDateLogicName) {
		this.startDateLogicName = startDateLogicName;
	}
	public int getIsclose() {
		return isclose;
	}
	public void setIsclose(int isclose) {
		this.isclose = isclose;
	}	
	public String getComponentType() {
		return componentType;
	}
	public void setComponentType(String componentType) {
		this.componentType = componentType;
	}
	public double getComponentAmount() {
		return componentAmount;
	}
	public void setComponentAmount(double componentAmount) {
		this.componentAmount = componentAmount;
	}
}
