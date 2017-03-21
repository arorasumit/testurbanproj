package com.ibm.ioes.forms;

import java.util.Date;

public class OBCalculationDTO {
	
	private int chargeType;
	private double newChargeAmount;
	private double oldChargeAmount;
	private int chargePeriod;
	private double systemOB;
	private String action;
	private long obLinkChargeId;
	private long rowId;
	private long serviceId;
	private long orderNo;
	private String actionType;
	private double currencyRate;
	private Exception exception;
	private String exceptionMessage;
	private int returnStatus;
	private int calculationLogic;
	private long logicalSINumber;
	private String chargeStatus;
	private long netObId;
	private int subChangeTypeId;
	private long newLinkedChargeInfoId;
	private double obValue;
	private Date obStartDate;
	private Date obEndDate;
	private long oldChargeNetOBId;
	private Date copcApprovalDate;
	private int chargeStartDateMonth;
	private long chargeInfoId;
	private long fileId;
	private long componentInfoId;
	private Date oldObStartDate;
	private boolean isObComputed;
	private double oldSystemOB;
	private Date tentativeObStartDate;
	private Date tentativeObEndDate;
	private Date actualObStartDate;
	private Date actualObEndDate;
	private Date oldActualObStartDate;
	private Date oldActualObEndDate;
	private Date chargeStartDate;
	private Date calculatedTentativeObStartDate;
	private Date calculatedTentativeObEndDate;
	private Date calculatedActualObStartDate;
	private Date calculatedActualObEndDate;
	private long initialDurationNetObId;
	private long oldInitialDurationNetObId;
	private long calculatedInitialDurationNetObId;
	private boolean isNewlyGeneratedNetObId;
	private Date orderDate;
	private Date serviceCancelDate;
	private double manualOB;
	private Date initialDurationActualObStartDate;
	private Date initialDurationActualObEndDate;
	private int isChargeDisconnected;
	private int isLineDisconnected;
	private int billingTriggerStatus;
	private String endDateLogic;
	private long disconnectedOrderNo;
	private Date chargeEndDate;
	
	public long getComponentInfoId() {
		return componentInfoId;
	}
	public void setComponentInfoId(long componentInfoId) {
		this.componentInfoId = componentInfoId;
	}
	public long getFileId() {
		return fileId;
	}
	public void setFileId(long fileId) {
		this.fileId = fileId;
	}
	public int getChargeType() {
		return chargeType;
	}
	public void setChargeType(int chargeType) {
		this.chargeType = chargeType;
	}
	public double getNewChargeAmount() {
		return newChargeAmount;
	}
	public void setNewChargeAmount(double newChargeAmount) {
		this.newChargeAmount = newChargeAmount;
	}
	public double getOldChargeAmount() {
		return oldChargeAmount;
	}
	public void setOldChargeAmount(double oldChargeAmount) {
		this.oldChargeAmount = oldChargeAmount;
	}
	public int getChargePeriod() {
		return chargePeriod;
	}
	public void setChargePeriod(int chargePeriod) {
		this.chargePeriod = chargePeriod;
	}
	public double getSystemOB() {
		return systemOB;
	}
	public void setSystemOB(double systemOB) {
		this.systemOB = systemOB;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public long getObLinkChargeId() {
		return obLinkChargeId;
	}
	public void setObLinkChargeId(long obLinkChargeId) {
		this.obLinkChargeId = obLinkChargeId;
	}
	public long getRowId() {
		return rowId;
	}
	public void setRowId(long rowId) {
		this.rowId = rowId;
	}
	public long getServiceId() {
		return serviceId;
	}
	public void setServiceId(long serviceId) {
		this.serviceId = serviceId;
	}
	public long getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(long orderNo) {
		this.orderNo = orderNo;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public double getCurrencyRate() {
		return currencyRate;
	}
	public void setCurrencyRate(double currencyRate) {
		this.currencyRate = currencyRate;
	}
	public Exception getException() {
		return exception;
	}
	public void setException(Exception exception) {
		this.exception = exception;
	}
	public String getExceptionMessage() {
		return exceptionMessage;
	}
	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}
	public int getReturnStatus() {
		return returnStatus;
	}
	public void setReturnStatus(int returnStatus) {
		this.returnStatus = returnStatus;
	}
	public int getCalculationLogic() {
		return calculationLogic;
	}
	public void setCalculationLogic(int calculationLogic) {
		this.calculationLogic = calculationLogic;
	}
	public long getLogicalSINumber() {
		return logicalSINumber;
	}
	public void setLogicalSINumber(long logicalSINumber) {
		this.logicalSINumber = logicalSINumber;
	}
	public String getChargeStatus() {
		return chargeStatus;
	}
	public void setChargeStatus(String chargeStatus) {
		this.chargeStatus = chargeStatus;
	}
	public long getNetObId() {
		return netObId;
	}
	public void setNetObId(long netObId) {
		this.netObId = netObId;
	}
	public int getSubChangeTypeId() {
		return subChangeTypeId;
	}
	public void setSubChangeTypeId(int subChangeTypeId) {
		this.subChangeTypeId = subChangeTypeId;
	}
	public long getNewLinkedChargeInfoId() {
		return newLinkedChargeInfoId;
	}
	public void setNewLinkedChargeInfoId(long newLinkedChargeInfoId) {
		this.newLinkedChargeInfoId = newLinkedChargeInfoId;
	}
	public double getObValue() {
		return obValue;
	}
	public void setObValue(double obValue) {
		this.obValue = obValue;
	}
	public Date getObStartDate() {
		return obStartDate;
	}
	public void setObStartDate(Date obStartDate) {
		this.obStartDate = obStartDate;
	}
	public Date getObEndDate() {
		return obEndDate;
	}
	public void setObEndDate(Date obEndDate) {
		this.obEndDate = obEndDate;
	}
	public long getOldChargeNetOBId() {
		return oldChargeNetOBId;
	}
	public void setOldChargeNetOBId(long oldChargeNetOBId) {
		this.oldChargeNetOBId = oldChargeNetOBId;
	}
	public Date getCopcApprovalDate() {
		return copcApprovalDate;
	}
	public void setCopcApprovalDate(Date copcApprovalDate) {
		this.copcApprovalDate = copcApprovalDate;
	}
	public int getChargeStartDateMonth() {
		return chargeStartDateMonth;
	}
	public void setChargeStartDateMonth(int chargeStartDateMonth) {
		this.chargeStartDateMonth = chargeStartDateMonth;
	}
	public long getChargeInfoId() {
		return chargeInfoId;
	}
	public void setChargeInfoId(long chargeInfoId) {
		this.chargeInfoId = chargeInfoId;
	}
	public Date getOldObStartDate() {
		return oldObStartDate;
	}
	public void setOldObStartDate(Date oldObStartDate) {
		this.oldObStartDate = oldObStartDate;
	}
	public boolean isObComputed() {
		return isObComputed;
	}
	public void setObComputed(boolean isObComputed) {
		this.isObComputed = isObComputed;
	}
	public double getOldSystemOB() {
		return oldSystemOB;
	}
	public void setOldSystemOB(double oldSystemOB) {
		this.oldSystemOB = oldSystemOB;
	}
	public Date getTentativeObStartDate() {
		return tentativeObStartDate;
	}
	public void setTentativeObStartDate(Date tentativeObStartDate) {
		this.tentativeObStartDate = tentativeObStartDate;
	}
	public Date getTentativeObEndDate() {
		return tentativeObEndDate;
	}
	public void setTentativeObEndDate(Date tentativeObEndDate) {
		this.tentativeObEndDate = tentativeObEndDate;
	}
	public Date getActualObStartDate() {
		return actualObStartDate;
	}
	public void setActualObStartDate(Date actualObStartDate) {
		this.actualObStartDate = actualObStartDate;
	}
	public Date getActualObEndDate() {
		return actualObEndDate;
	}
	public void setActualObEndDate(Date actualObEndDate) {
		this.actualObEndDate = actualObEndDate;
	}
	public Date getOldActualObStartDate() {
		return oldActualObStartDate;
	}
	public void setOldActualObStartDate(Date oldActualObStartDate) {
		this.oldActualObStartDate = oldActualObStartDate;
	}
	public Date getOldActualObEndDate() {
		return oldActualObEndDate;
	}
	public void setOldActualObEndDate(Date oldActualObEndDate) {
		this.oldActualObEndDate = oldActualObEndDate;
	}
	public Date getChargeStartDate() {
		return chargeStartDate;
	}
	public void setChargeStartDate(Date chargeStartDate) {
		this.chargeStartDate = chargeStartDate;
	}
	public Date getCalculatedTentativeObStartDate() {
		return calculatedTentativeObStartDate;
	}
	public void setCalculatedTentativeObStartDate(
			Date calculatedTentativeObStartDate) {
		this.calculatedTentativeObStartDate = calculatedTentativeObStartDate;
	}
	public Date getCalculatedTentativeObEndDate() {
		return calculatedTentativeObEndDate;
	}
	public void setCalculatedTentativeObEndDate(Date calculatedTentativeObEndDate) {
		this.calculatedTentativeObEndDate = calculatedTentativeObEndDate;
	}
	public Date getCalculatedActualObStartDate() {
		return calculatedActualObStartDate;
	}
	public void setCalculatedActualObStartDate(Date calculatedActualObStartDate) {
		this.calculatedActualObStartDate = calculatedActualObStartDate;
	}
	public Date getCalculatedActualObEndDate() {
		return calculatedActualObEndDate;
	}
	public void setCalculatedActualObEndDate(Date calculatedActualObEndDate) {
		this.calculatedActualObEndDate = calculatedActualObEndDate;
	}
	public long getInitialDurationNetObId() {
		return initialDurationNetObId;
	}
	public void setInitialDurationNetObId(long initialDurationNetObId) {
		this.initialDurationNetObId = initialDurationNetObId;
	}
	public long getOldInitialDurationNetObId() {
		return oldInitialDurationNetObId;
	}
	public void setOldInitialDurationNetObId(long oldInitialDurationNetObId) {
		this.oldInitialDurationNetObId = oldInitialDurationNetObId;
	}
	public long getCalculatedInitialDurationNetObId() {
		return calculatedInitialDurationNetObId;
	}
	public void setCalculatedInitialDurationNetObId(
			long calculatedInitialDurationNetObId) {
		this.calculatedInitialDurationNetObId = calculatedInitialDurationNetObId;
	}
	public boolean isNewlyGeneratedNetObId() {
		return isNewlyGeneratedNetObId;
	}
	public void setNewlyGeneratedNetObId(boolean isNewlyGeneratedNetObId) {
		this.isNewlyGeneratedNetObId = isNewlyGeneratedNetObId;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public Date getServiceCancelDate() {
		return serviceCancelDate;
	}
	public void setServiceCancelDate(Date serviceCancelDate) {
		this.serviceCancelDate = serviceCancelDate;
	}
	public double getManualOB() {
		return manualOB;
	}
	public void setManualOB(double manualOB) {
		this.manualOB = manualOB;
	}
	public Date getInitialDurationActualObStartDate() {
		return initialDurationActualObStartDate;
	}
	public void setInitialDurationActualObStartDate(
			Date initialDurationActualObStartDate) {
		this.initialDurationActualObStartDate = initialDurationActualObStartDate;
	}
	public Date getInitialDurationActualObEndDate() {
		return initialDurationActualObEndDate;
	}
	public void setInitialDurationActualObEndDate(
			Date initialDurationActualObEndDate) {
		this.initialDurationActualObEndDate = initialDurationActualObEndDate;
	}
	public int getIsChargeDisconnected() {
		return isChargeDisconnected;
	}
	public void setIsChargeDisconnected(int isChargeDisconnected) {
		this.isChargeDisconnected = isChargeDisconnected;
	}
	public int getIsLineDisconnected() {
		return isLineDisconnected;
	}
	public void setIsLineDisconnected(int isLineDisconnected) {
		this.isLineDisconnected = isLineDisconnected;
	}
	public int getBillingTriggerStatus() {
		return billingTriggerStatus;
	}
	public void setBillingTriggerStatus(int billingTriggerStatus) {
		this.billingTriggerStatus = billingTriggerStatus;
	}
	public String getEndDateLogic() {
		return endDateLogic;
	}
	public void setEndDateLogic(String endDateLogic) {
		this.endDateLogic = endDateLogic;
	}
	public long getDisconnectedOrderNo() {
		return disconnectedOrderNo;
	}
	public void setDisconnectedOrderNo(long disconnectedOrderNo) {
		this.disconnectedOrderNo = disconnectedOrderNo;
	}
	public Date getChargeEndDate() {
		return chargeEndDate;
	}
	public void setChargeEndDate(Date chargeEndDate) {
		this.chargeEndDate = chargeEndDate;
	}
		
}
