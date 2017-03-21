//[077]    ASHUTOSH SINGH  01-DEC-11  00-05422        Changes in PO AMOUNT(Decimal validation) 
//[001]    Lawkush  30-MAR-13  BUG ID TRNG22032013005 fixed
//[002]	   Rohit Verma	5-Sep-13	CSR-9112	PO Expiry and BT Action Date
//[004]  Gunjan Singla  20-Jan-15    20141113-R1-020802		ParallelUpgrade-Multiple LSI Selection
//[0010]  Gunjan SIngla  20-Oct-15    						Charge End Date Modification
//[0012] Priya Gupta 
package com.ibm.ioes.beans;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ibm.fx.dto.ServiceDTO;
import com.ibm.ioes.forms.OrderHeaderDTO;
import com.ibm.ioes.forms.ServiceLineDTO;
import com.ibm.ioes.utilities.PagingSorting;
import java.util.HashMap;

public class ViewOrderDto {
	private String success;
	private String mode;
	private String isFirstTask;
	private String m6No;
	private int serviceID;
	private int count;
    private int accountdisconnected;
	private int chargeTypeName;
	private int rcId;
	private int nrcId;
    private long spid;
	private long orderno;
	private String locNo_Status;
	private String locDate_Status;
	private ArrayList<ViewOrderDto> successfullyLOCDataInserted=null;
	private ArrayList<ViewOrderDto> failuetoInsertLocData=null;
	private String  checkbox_status;
	private ArrayList<ViewOrderDto> locArrayList = null;
	private long isAutoBilling;
	private String taskID=null; 
	private String taskType=null;
	private String ownerType=null; 
	private String taskStatus=null;
	private String taskStartDate=null;
	private String taskEndDate=null;
	private String createdBy;
	private String createdDate;
	private String notesType;
	private String notesMeaning;
	private String msgOut;
	private String actionId;	
	private String taskNoteId;
	private ArrayList errors;
	private ArrayList lSIS;
	private ArrayList serviceids;
	private ArrayList servicetypes;
	private ArrayList cusLSIResult;
	private ArrayList productList;
	private String rejectionSendTo;
	private String ownerTypeId;
    private String ownerId;
    private ArrayList osnlists; 
    private String historyId;
    private String historyActionRoleName;
    private String historyRejectionSendTo;
    private String historyActionDate;
    private String historyAction;
    private String actionRemraks;
    private String historyRemraks;
    private int chargePeriod;
    private String disconnectiondate;
    private String serviceType;
    private String eventids;
    private long sendToM6;
    private String remarks;
    private String line_status;
	private long ponum;
	private String taskSendTo;
	//For Charge Summary List
    private String chargeSummaryOrderNo;
    private String chargeSummaryPartyName;
    private String chargeSummaryServiceNo;
    private String chargeSummaryLogicalCircuitID;
    private String chargeSummaryStartDateLogic;
    private String chargeSummaryEndDateLogic;
    private String chargeSummaryTotalAmount;
    private String searchLSI;
    //Used for Sekect Service and Charge List
    private String pmProvisioningDate;
    private String locNo;
    private int searchLogicalSI;
    private String locDate;
    private String billingTriggerDate;
    private String lineNumber;
    private String lineName;
    private String logicalSino;
    private String custLogicalSino;
    private String fxSiid;
    private String siid;
    private String fxId;
    private String fxAccNo;
    private String fxStatus;
    private String serviceId;
    private String billingOrderNo;
    private String serviceDetailId;
private String customerlsisresult;
     private String fx_status;
    private String  chargeType;
    private String  chargeName;     
    private String chargefrequency;
    private Double chargefrequencyamount;
    private String startdatelogic;
    private String enddatelogic;
    private int start_date_days;
    private int end_date_days;
    private int start_date_month;
    private int end_date_month;
    private String fx_Token_no;
    private String fx_Response;
    private String billing_Active_date = null;
    private String billing_End_Date = null;
    private String challen_No;
    private String challen_date;
  private String biling_status;
    private String fxno;
    private String charge_createdon;
    private String charge_endon;
    private double  chargeAmt;     
    private String  orderNo;     
    private String  serviceProductID;
    private String  viewId;     
    private String  chargeStatus;   
    
    private String billingTriggerStatus;
    
    private String billingTriggerString;
    private String dataChanged;
    
    private String productType = null;
    
    private String allAccountsCreated;
    private String fx_ACCOUNT_EXTERNAL_ID;
    private String fx_Internal_ID;
    private int accountPending;
private Timestamp chargeDisconnectDate = null; // added by sandeep for change order in fx
    private int    fx_Status;                   // added by sandeep for change order in fx
    private int    charge_info_id; 	
    private String changeorderstatus;
    private String billingTriggerRateRenewalStatus=null;
    
    private String m6_att_fxchanged;
    private String fxAccNoStatus;
    private String billingTriggerDisconnectStatus = null;
   // added by manisha
    private String orderInfo_OrderType = null;
	private Integer orderInfo_ChangeType = null;
	private Integer orderInfo_ChangeSubType = null;
	
	public final String ORDER_TYPE_NEW= "NEW";
	public final String ORDER_TYPE_CHANGE= "CHANGE";
	
	public final int CHANGE_TYPE_DISCONNECTION= 3;
	public final int CHANGE_TYPE_RATERENEWAL= 5;
	public final int CHANGE_TYPE_SOLUTION_CHANGE=2;
	public final int CHANGE_TYPE_NETWORK_CHANGE=1;
	public final int CHANGE_TYPE_DEMO=4;
	
    private String projectmanagerassignedId;
    private String actionTakenByName;
    private String actionTakenById;
    private int reasonID;
    private String reasonName;
    private String nextRoleId;
    private Boolean saveActionCalled = false;
    private Integer lastTask;
    private String IsLastTask;
    private Long newTaskId;
    private boolean isApprovalMail=false;
    private boolean isRejectionMail=false;
    private boolean isPMWelcomeMail=false;
    private int isPartialInitiateMail=0;
    private String partialInitiateTaskID;
    //[0010] start
    private int fx_schedular_create_status=0;
    //[0010] end
    
    private long sno;
    
	public long getSno() {
		return sno;
	}
	public void setSno(long sno) {
		this.sno = sno;
	}
	 //[0012]
    private Boolean isavailableforbt = false; 
	public boolean getIsApprovalMail() {
		return isApprovalMail;
	}
	public void setIsApprovalMail(boolean isApprovalMail) {
		this.isApprovalMail = isApprovalMail;
	}
	public boolean getIsRejectionMail() {
		return isRejectionMail;
	}
	public void setIsRejectionMail(boolean isRejectionMail) {
		this.isRejectionMail = isRejectionMail;
	}
	public boolean getIsPMWelcomeMail() {
		return isPMWelcomeMail;
	}
	public void setIsPMWelcomeMail(boolean isPMWelcomeMail) {
		this.isPMWelcomeMail = isPMWelcomeMail;
	}
	public int getIsPartialInitiateMail() {
		return isPartialInitiateMail;
	}
	public void setIsPartialInitiateMail(int isPartialInitiateMail) {
		this.isPartialInitiateMail = isPartialInitiateMail;
	}
	public String getPartialInitiateTaskID() {
		return partialInitiateTaskID;
	}
	public void setPartialInitiateTaskID(String partialInitiateTaskID) {
		this.partialInitiateTaskID = partialInitiateTaskID;
	}
	public Long getNewTaskId() {
		return newTaskId;
	}
	public void setNewTaskId(Long newTaskId) {
		this.newTaskId = newTaskId;
	}
    
    
	public String getIsLastTask() {
		return IsLastTask;
	}
	public void setIsLastTask(String isLastTask) {
		IsLastTask = isLastTask;
	}
	public Integer getLastTask() {
		return lastTask;
	}
	public void setLastTask(Integer lastTask) {
		this.lastTask = lastTask;
	}
	public Boolean getSaveActionCalled() {
		return saveActionCalled;
	}
	public void setSaveActionCalled(Boolean saveActionCalled) {
		this.saveActionCalled = saveActionCalled;
	}
	public String getNextRoleId() {
		return nextRoleId;
	}
	public void setNextRoleId(String nextRoleId) {
		this.nextRoleId = nextRoleId;
	}
    //Meenakshi
    private String noOfComponents = null;
    private String productBillType = null;
     private String serviceno_m6;
    /**
     * Values: OLD_LINE,NEW_LINE
     */
    private String lineProp = null;
    /**
     * No of Charges in Line
     */
    private String noOfCharges = null;
    private String noOfChargesForDisconnectForOldLine = null;
    
    private String dateValidationStatus = null;
    private String accountActiveDate = null;
    private ArrayList arrViewOrderDto = null;
    private String ifAnyFailValidation = null;
    
    private String isInHistory = null;
    private Integer noOfNewCharges = null;
	private Integer noOfChangedCharges = null;
    private Integer noOfDisconnectClose = null;
    private Integer noOfDisconnectInactive = null;
    private Integer noOfCharges_integer=null;
    private String lineOldOrNew = null;
    private String isLineDisconnected = null;
    
    
    //private String isDisconInCurrOrderForNonDisconLine=null;
    private String disconnectionType = null;
    private Date chargeStartDate = null;
    private Date chargeEndDate = null;
    private String chargeCreatedOnOrder = null;
    private String chargeEndedOnOrder = null;
    private String chargeStatus_otherLabel = null;
    private String annualRate = null;
    private String annotation = null;
    private String billPeriod = null;
    private String chargeId = null;
    private String fxViewId = null;
    private long chargeInfoId;

    private String startTokenNo = null;
    private String startFxStatus = null;
    private String startFxNo = null;
    private String endTokenNo = null;
    private String endFxStatus = null;
    private String endFxNo = null;
    private String chargeStartStatus = null;
    private String chargeEndStatus = null;
    private String cktId;
    
    private ArrayList<String> billingTriggerValidationErrors=null;
    
    //[005]
    //Added by Ashutosh for Currency Change
    private ArrayList<String> currencyChangeBillingTriggerValidationErrors=null;
    //[005] End
    
    private String billingTriggerConfirmed = null;
    private String ifAnyBillingTriggerConfirmationRequired = null;
    private String billingTriggerProcess = null;

    private int taskOwnerId;
    
    private String chargeStartDate_String  = null;
    private String chargeEndDate_String = null;
    private String locRecDate;
	private String locRecDate_Status;
	
	private String fxServiceUpdateStatus;
	private String sessionid=null;
	private String userid=null;
	
	private String isChargeDisconnectedInCurrentOrder = null;
	
	
	/* add by Anil for return servicType and Order source for clep order	 
	 */
	private long serviceTypeID=0l;
	private String order_sourceId="";
	private String responseClepOrderMsgAfterAproval="";
	private long clepFileId=0l;
	private String clepJMSReqMsgId="";
	private Long Tm6OrderNo=0l;
	private String order_creation_source="";
	private int isBTDone=-1;
	private int isSuccessApproved=0;
	private int isMailSentSuccess=0;
	private int isCOPCSentMsgToMPP=0;
	
	// added by manisha
	private String ordertype;
    private String order_subtype;
    private int showline;
    private String locnocolor;
    private String locdatecolor;
    private String loc_rec_datecolor;
    private String btDatecolor;
    private String billingtriggerError;
    private String accountid;
    private String errorlog=null;
    private String errorlog_charges=null;
    HashMap<String,ViewOrderDto> map_validate=null;
    private String isNewOrDisconnectedInCurrentService = null;
    private String old_start_date;
    private String old_chargeinfoid;
    
    public int getComponentinfoid() {
		return componentinfoid;
	}


	public void setComponentinfoid(int componentinfoid) {
		this.componentinfoid = componentinfoid;
	}
	// added by manisha start
    private String system_start_date;
    private String system_end_date;
    private String com_start_logic;
    private String com_end_logic;
    private int componentinfoid;
    private String object_type;
    private int delayReason;
    private String delayReasonValue;
    
    private String serviceList;
    private boolean  partialInitiator;
    private boolean  saved;
    private boolean  allServicesSelectd;
 
	private String  previousTaskID;
//  added by manisha
	
    
	public String getDelayReasonValue() {
		return delayReasonValue;
	}
	public String getPreviousTaskID() {
		return previousTaskID;
	}
	public void setPreviousTaskID(String previousTaskID) {
		this.previousTaskID = previousTaskID;
	}


	public void setDelayReasonValue(String delayReasonValue) {
		this.delayReasonValue = delayReasonValue;
	}


	public int getDelayReason() {
		return delayReason;
	}


	public void setDelayReason(int delayReason) {
		this.delayReason = delayReason;
	}
	//vijay adding for contain order stage 
    private String orderStage;
    //vijay end
    
	public String getObject_type() {
		return object_type;
	}


	public void setObject_type(String object_type) {
		this.object_type = object_type;
	}


	public String getSystem_start_date() {
		return system_start_date;
	}


	public void setSystem_start_date(String system_start_date) {
		this.system_start_date = system_start_date;
	}


	public String getSystem_end_date() {
		return system_end_date;
	}


	public void setSystem_end_date(String system_end_date) {
		this.system_end_date = system_end_date;
	}


	public String getCom_start_logic() {
		return com_start_logic;
	}


	public void setCom_start_logic(String com_start_logic) {
		this.com_start_logic = com_start_logic;
	}


	public String getCom_end_logic() {
		return com_end_logic;
	}


	public void setCom_end_logic(String com_end_logic) {
		this.com_end_logic = com_end_logic;
	}
	public String getOld_start_date() {
		return old_start_date;
	}


	public void setOld_start_date(String old_start_date) {
		this.old_start_date = old_start_date;
	}


	public String getOld_chargeinfoid() {
		return old_chargeinfoid;
	}


	public void setOld_chargeinfoid(String old_chargeinfoid) {
		this.old_chargeinfoid = old_chargeinfoid;
	}

	OrderHeaderDTO orderInfo = null;
	//end clep
	private BillingTriggerValidation billingTriggerAllowDenyLogic = null;
	private String searchLineTriggerStatus = null; 
	PagingSorting pagingSorting = new PagingSorting();	
		private int totalRecord;
		private int maxPageNo;
		
		private String redirectInternalAc;
		private String redirectExternalAc;
		private String redirect_status;
		private String redirect_status_desc;
		private String cumulative_status;
		private String cumulative_status_desc;
		private String redirectedLSI;

		private String isUsage = null;
//start	[001]
		private long btDoneBy;
		private String btDoneByName;
		private int isdifferentialchargeflag;
	
		
		//[002] Start
		private String btDoneDate;
		private String poExpiryDate;
		//[002] End	
		
		//[004] start
		private long subChangeTypeId;
		private String m6FxProgressStatus;
		private long parallelUpgradeLSI;
		private long serviceLineNo;
		//[004] end
		
	public int getIsdifferentialchargeflag() {
			return isdifferentialchargeflag;
		}


		public void setIsdifferentialchargeflag(int isdifferentialchargeflag) {
			this.isdifferentialchargeflag = isdifferentialchargeflag;
		}


	public long getBtDoneBy() {
			return btDoneBy;
		}


		public void setBtDoneBy(long btDoneBy) {
			this.btDoneBy = btDoneBy;
		}
//End	[001]	

	public String getIsUsage() {
			return isUsage;
		}


		public void setIsUsage(String isUsage) {
			this.isUsage = isUsage;
		}


	public int getMaxPageNo() {
			return maxPageNo;
		}


		public void setMaxPageNo(int maxPageNo) {
			this.maxPageNo = maxPageNo;
		}


		public int getTotalRecord() {
			return totalRecord;
		}


		public void setTotalRecord(int totalRecord) {
			this.totalRecord = totalRecord;
		}


	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}


	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
	}
	
	public String getIsChargeDisconnectedInCurrentOrder() {
		return isChargeDisconnectedInCurrentOrder;
	}


	public void setIsChargeDisconnectedInCurrentOrder(
			String isChargeDisconnectedInCurrentOrder) {
		this.isChargeDisconnectedInCurrentOrder = isChargeDisconnectedInCurrentOrder;
	}


	public String getUserid() {
		return userid;
	}


	public void setUserid(String userid) {
		this.userid = userid;
	}


	public String getTaskSendTo() {
		return taskSendTo;
	}
	public void setTaskSendTo(String taskSendTo) {
		this.taskSendTo = taskSendTo;
	}
	public String getSessionid() {
		return sessionid;
	}


	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}


	public String getChargeEndDate_String() {
		return chargeEndDate_String;
	}


	public void setChargeEndDate_String(String chargeEndDate_String) {
		this.chargeEndDate_String = chargeEndDate_String;
	}


	public String getChargeStartDate_String() {
		return chargeStartDate_String;
	}


	public void setChargeStartDate_String(String chargeStartDate_String) {
		this.chargeStartDate_String = chargeStartDate_String;
	}


	public String getBillingTriggerConfirmed() {
		return billingTriggerConfirmed;
	}


	public void setBillingTriggerConfirmed(String billingTriggerConfirmed) {
		this.billingTriggerConfirmed = billingTriggerConfirmed;
	}


	public String getIfAnyBillingTriggerConfirmationRequired() {
		return ifAnyBillingTriggerConfirmationRequired;
	}


	public void setIfAnyBillingTriggerConfirmationRequired(
			String ifAnyBillingTriggerConfirmationRequired) {
		this.ifAnyBillingTriggerConfirmationRequired = ifAnyBillingTriggerConfirmationRequired;
	}


	public ArrayList<String> getBillingTriggerValidationErrors() {
		return billingTriggerValidationErrors;
	}


	public void setBillingTriggerValidationErrors(
			ArrayList<String> billingTriggerValidationErrors) {
		this.billingTriggerValidationErrors = billingTriggerValidationErrors;
	}


	public String getChargeEndStatus() {
		return chargeEndStatus;
	}


	public void setChargeEndStatus(String chargeEndStatus) {
		this.chargeEndStatus = chargeEndStatus;
	}


	public String getChargeStartStatus() {
		return chargeStartStatus;
	}


	public void setChargeStartStatus(String chargeStartStatus) {
		this.chargeStartStatus = chargeStartStatus;
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


	public String getEndTokenNo() {
		return endTokenNo;
	}


	public void setEndTokenNo(String endTokenNo) {
		this.endTokenNo = endTokenNo;
	}


	public String getStartFxNo() {
		return startFxNo;
	}


	public void setStartFxNo(String startFxNo) {
		this.startFxNo = startFxNo;
	}


	public String getStartFxStatus() {
		return startFxStatus;
	}


	public void setStartFxStatus(String startFxStatus) {
		this.startFxStatus = startFxStatus;
	}


	public String getStartTokenNo() {
		return startTokenNo;
	}


	public void setStartTokenNo(String startTokenNo) {
		this.startTokenNo = startTokenNo;
	}


	public String getAnnotation() {
		return annotation;
	}


	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}








	public String getAnnualRate() {
		return annualRate;
	}


	public void setAnnualRate(String annualRate) {
		this.annualRate = annualRate;
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


	public String getIfAnyFailValidation() {
		return ifAnyFailValidation;
	}


	public void setIfAnyFailValidation(String ifAnyFailValidation) {
		this.ifAnyFailValidation = ifAnyFailValidation;
	}


	public String getDateValidationStatus() {
		return dateValidationStatus;
	}


	public void setDateValidationStatus(String dateValidationStatus) {
		this.dateValidationStatus = dateValidationStatus;
	}


	public int getCHANGE_TYPE_DISCONNECTION() {
		return CHANGE_TYPE_DISCONNECTION;
	}


	public int getCHANGE_TYPE_SOLUTION_CHANGE() {
		return CHANGE_TYPE_SOLUTION_CHANGE;
	}


	public int getCHANGE_TYPE_RATERENEWAL() {
		return CHANGE_TYPE_RATERENEWAL;
	}


	public String getORDER_TYPE_CHANGE() {
		return ORDER_TYPE_CHANGE;
	}


	public String getORDER_TYPE_NEW() {
		return ORDER_TYPE_NEW;
	}


	public Integer getOrderInfo_ChangeSubType() {
		return orderInfo_ChangeSubType;
	}


	public void setOrderInfo_ChangeSubType(Integer orderInfo_ChangeSubType) {
		this.orderInfo_ChangeSubType = orderInfo_ChangeSubType;
	}


	public Integer getOrderInfo_ChangeType() {
		return orderInfo_ChangeType;
	}


	public void setOrderInfo_ChangeType(Integer orderInfo_ChangeType) {
		this.orderInfo_ChangeType = orderInfo_ChangeType;
	}


	public String getOrderInfo_OrderType() {
		return orderInfo_OrderType;
	}


	public void setOrderInfo_OrderType(String orderInfo_OrderType) {
		this.orderInfo_OrderType = orderInfo_OrderType;
	}
	public String getChangeorderstatus() {
		return changeorderstatus;
	}


	public void setChangeorderstatus(String changeorderstatus) {
		this.changeorderstatus = changeorderstatus;
	}
    
    private ArrayList<ViewOrderDto> billingTriggerResult = null;
    
    private String[] line_prop = null;
    
    
	public String getAllAccountsCreated() {
		return allAccountsCreated;
	}
	public void setAllAccountsCreated(String allAccountsCreated) {
		this.allAccountsCreated = allAccountsCreated;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public ArrayList getErrors() {
		return errors;
	}
	public void setErrors(ArrayList errors) {
		this.errors = errors;
	}
	public String getMsgOut() {
		return msgOut;
	}
	public void setMsgOut(String msgOut) {
		this.msgOut = msgOut;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getNotesMeaning() {
		return notesMeaning;
	}
	public void setNotesMeaning(String notesMeaning) {
		this.notesMeaning = notesMeaning;
	}
	public String getNotesType() {
		return notesType;
	}
	public void setNotesType(String notesType) {
		this.notesType = notesType;
	}
	public String getOwnerType() {
		return ownerType;
	}
	public void setOwnerType(String ownerType) {
		this.ownerType = ownerType;
	}
	public String getTaskEndDate() {
		return taskEndDate;
	}
	public void setTaskEndDate(String taskEndDate) {
		this.taskEndDate = taskEndDate;
	}
	public String getTaskID() {
		return taskID;
	}
	public void setTaskID(String taskID) {
		this.taskID = taskID;
	}
	public String getTaskStartDate() {
		return taskStartDate;
	}
	public void setTaskStartDate(String taskStartDate) {
		this.taskStartDate = taskStartDate;
	}
	public String getTaskStatus() {
		return taskStatus;
	}
	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public String getActionId() {
		return actionId;
	}
	public void setActionId(String actionId) {
		this.actionId = actionId;
	}
	public String getTaskNoteId() {
		return taskNoteId;
	}
	public void setTaskNoteId(String taskNoteId) {
		this.taskNoteId = taskNoteId;
	}
	public String getRejectionSendTo() {
		return rejectionSendTo;
	}
	public void setRejectionSendTo(String rejectionSendTo) {
		this.rejectionSendTo = rejectionSendTo;
	}
	public String getOwnerTypeId() {
		return ownerTypeId;
	}
	public void setOwnerTypeId(String ownerTypeId) {
		this.ownerTypeId = ownerTypeId;
	}
	public String getHistoryAction() {
		return historyAction;
	}
	public void setHistoryAction(String historyAction) {
		this.historyAction = historyAction;
	}
	public String getHistoryActionDate() {
		return historyActionDate;
	}
	public void setHistoryActionDate(String historyActionDate) {
		this.historyActionDate = historyActionDate;
	}
	public String getHistoryActionRoleName() {
		return historyActionRoleName;
	}
	public void setHistoryActionRoleName(String historyActionRoleName) {
		this.historyActionRoleName = historyActionRoleName;
	}
	public String getHistoryId() {
		return historyId;
	}
	public void setHistoryId(String historyId) {
		this.historyId = historyId;
	}
	public String getHistoryRejectionSendTo() {
		return historyRejectionSendTo;
	}
	public void setHistoryRejectionSendTo(String historyRejectionSendTo) {
		this.historyRejectionSendTo = historyRejectionSendTo;
	}
	public String getActionRemraks() {
		return actionRemraks;
	}
	public void setActionRemraks(String actionRemraks) {
		this.actionRemraks = actionRemraks;
	}
	public String getHistoryRemraks() {
		return historyRemraks;
	}
	public void setHistoryRemraks(String historyRemraks) {
		this.historyRemraks = historyRemraks;
	}
	public String getChargeSummaryEndDateLogic() {
		return chargeSummaryEndDateLogic;
	}
	public void setChargeSummaryEndDateLogic(String chargeSummaryEndDateLogic) {
		this.chargeSummaryEndDateLogic = chargeSummaryEndDateLogic;
	}
	public String getChargeSummaryLogicalCircuitID() {
		return chargeSummaryLogicalCircuitID;
	}
	public void setChargeSummaryLogicalCircuitID(
			String chargeSummaryLogicalCircuitID) {
		this.chargeSummaryLogicalCircuitID = chargeSummaryLogicalCircuitID;
	}
	public String getChargeSummaryOrderNo() {
		return chargeSummaryOrderNo;
	}
	public void setChargeSummaryOrderNo(String chargeSummaryOrderNo) {
		this.chargeSummaryOrderNo = chargeSummaryOrderNo;
	}
	public String getChargeSummaryPartyName() {
		return chargeSummaryPartyName;
	}
	public void setChargeSummaryPartyName(String chargeSummaryPartyName) {
		this.chargeSummaryPartyName = chargeSummaryPartyName;
	}
	public String getChargeSummaryServiceNo() {
		return chargeSummaryServiceNo;
	}
	public void setChargeSummaryServiceNo(String chargeSummaryServiceNo) {
		this.chargeSummaryServiceNo = chargeSummaryServiceNo;
	}
	public String getChargeSummaryTotalAmount() {
		return chargeSummaryTotalAmount;
	}
	public void setChargeSummaryTotalAmount(String chargeSummaryTotalAmount) {
		this.chargeSummaryTotalAmount = chargeSummaryTotalAmount;
	}
	public String getChargeSummaryStartDateLogic() {
		return chargeSummaryStartDateLogic;
	}
	public void setChargeSummaryStartDateLogic(String chargeSummaryStartDateLogic) {
		this.chargeSummaryStartDateLogic = chargeSummaryStartDateLogic;
	}
		
	public String getChargeName() {
		return chargeName;
	}
	public void setChargeName(String chargeName) {
		this.chargeName = chargeName;
	}
	
	public String getChargeStatus() {
		return chargeStatus;
	}
	public void setChargeStatus(String chargeStatus) {
		this.chargeStatus = chargeStatus;
	}
	public String getChargeType() {
		return chargeType;
	}
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}
	public String getCustLogicalSino() {
		return custLogicalSino;
	}
	public void setCustLogicalSino(String custLogicalSino) {
		this.custLogicalSino = custLogicalSino;
	}
	public String getLocDate() {
		return locDate;
	}
	public void setLocDate(String locDate) {
		this.locDate = locDate;
	}
	public String getLocNo() {
		return locNo;
	}
	public void setLocNo(String locNo) {
		this.locNo = locNo;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	public String getViewId() {
		return viewId;
	}
	public void setViewId(String viewId) {
		this.viewId = viewId;
	}
	public String getBillingOrderNo() {
		return billingOrderNo;
	}
	public void setBillingOrderNo(String billingOrderNo) {
		this.billingOrderNo = billingOrderNo;
	}
	public String getBillingTriggerDate() {
		return billingTriggerDate;
	}
	public void setBillingTriggerDate(String billingTriggerDate) {
		this.billingTriggerDate = billingTriggerDate;
	}
	public String getFxAccNo() {
		return fxAccNo;
	}
	public void setFxAccNo(String fxAccNo) {
		this.fxAccNo = fxAccNo;
	}
	public String getFxId() {
		return fxId;
	}
	public void setFxId(String fxId) {
		this.fxId = fxId;
	}
	public String getFxSiid() {
		return fxSiid;
	}
	public void setFxSiid(String fxSiid) {
		this.fxSiid = fxSiid;
	}
	public String getFxStatus() {
		return fxStatus;
	}
	public void setFxStatus(String fxStatus) {
		this.fxStatus = fxStatus;
	}
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	public String getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}
	public String getLogicalSino() {
		return logicalSino;
	}
	public void setLogicalSino(String logicalSino) {
		this.logicalSino = logicalSino;
	}
	public String getPmProvisioningDate() {
		return pmProvisioningDate;
	}
	public void setPmProvisioningDate(String pmProvisioningDate) {
		this.pmProvisioningDate = pmProvisioningDate;
	}
	public String getServiceDetailId() {
		return serviceDetailId;
	}
	public void setServiceDetailId(String serviceDetailId) {
		this.serviceDetailId = serviceDetailId;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getBillingTriggerStatus() {
		return billingTriggerStatus;
	}
	public void setBillingTriggerStatus(String billingTriggerStatus) {
		this.billingTriggerStatus = billingTriggerStatus;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getBillingTriggerString() {
		return billingTriggerString;
	}
	public void setBillingTriggerString(String billingTriggerString) {
		this.billingTriggerString = billingTriggerString;
	}
	public String getDataChanged() {
		return dataChanged;
	}
	public void setDataChanged(String dataChanged) {
		this.dataChanged = dataChanged;
	}
	public ArrayList<ViewOrderDto> getBillingTriggerResult() {
		return billingTriggerResult;
	}
	public void setBillingTriggerResult(ArrayList<ViewOrderDto> billingTriggerResult) {
		this.billingTriggerResult = billingTriggerResult;
	}
	public String getFx_ACCOUNT_EXTERNAL_ID() {
		return fx_ACCOUNT_EXTERNAL_ID;
	}
	public void setFx_ACCOUNT_EXTERNAL_ID(String fx_ACCOUNT_EXTERNAL_ID) {
		this.fx_ACCOUNT_EXTERNAL_ID = fx_ACCOUNT_EXTERNAL_ID;
	}
	public String getFx_Internal_ID() {
		return fx_Internal_ID;
	}
	public void setFx_Internal_ID(String fx_Internal_ID) {
		this.fx_Internal_ID = fx_Internal_ID;
	}
	public int getAccountPending() {
		return accountPending;
	}
	public void setAccountPending(int accountPending) {
		this.accountPending = accountPending;
	}
	public void setServiceProductID(String serviceProductID) {
		this.serviceProductID = serviceProductID;
	}
	public void setChargePeriod(int chargePeriod) {
		this.chargePeriod = chargePeriod;
	}
	public void setChargeAmt(double chargeAmt) {
		this.chargeAmt = chargeAmt;
	}
	public String getChargefrequency() {
		return chargefrequency;
	}
	public void setChargefrequency(String chargefrequency) {
		this.chargefrequency = chargefrequency;
	}
	public double getChargeAmt() {
		return chargeAmt;
	}
	public int getChargePeriod() {
		return chargePeriod;
	}
	public String getServiceProductID() {
		return serviceProductID;
	}
	
	public Double getChargefrequencyamount() {
		return chargefrequencyamount;
	}


	public void setChargefrequencyamount(Double chargefrequencyamount) {
		this.chargefrequencyamount = chargefrequencyamount;
	}


	public int getEnd_date_days() {
		return end_date_days;
	}
	public void setEnd_date_days(int end_date_days) {
		this.end_date_days = end_date_days;
	}
	public int getEnd_date_month() {
		return end_date_month;
	}
	public void setEnd_date_month(int end_date_month) {
		this.end_date_month = end_date_month;
	}
	public String getEnddatelogic() {
		return enddatelogic;
	}
	public void setEnddatelogic(String enddatelogic) {
		this.enddatelogic = enddatelogic;
	}
	public int getStart_date_days() {
		return start_date_days;
	}
	public void setStart_date_days(int start_date_days) {
		this.start_date_days = start_date_days;
	}
	public int getStart_date_month() {
		return start_date_month;
	}
	public void setStart_date_month(int start_date_month) {
		this.start_date_month = start_date_month;
	}
	public String getStartdatelogic() {
		return startdatelogic;
	}
	public void setStartdatelogic(String startdatelogic) {
		this.startdatelogic = startdatelogic;
	}
	public String getCharge_createdon() {
		return charge_createdon;
	}
	public void setCharge_createdon(String charge_createdon) {
		this.charge_createdon = charge_createdon;
	}
	public String getCharge_endon() {
		return charge_endon;
	}
	public void setCharge_endon(String charge_endon) {
		this.charge_endon = charge_endon;
	}
	public String getFxno() {
		return fxno;
	}
	public void setFxno(String fxno) {
		this.fxno = fxno;
	}
	public int getChargeTypeName() {
		return chargeTypeName;
	}
	public void setChargeTypeName(int chargeTypeName) {
		this.chargeTypeName = chargeTypeName;
	}
	public int getNrcId() {
		return nrcId;
	}
	public void setNrcId(int nrcId) {
		this.nrcId = nrcId;
	}
	public int getRcId() {
		return rcId;
	}
	public void setRcId(int rcId) {
		this.rcId = rcId;
	}
	public Timestamp getChargeDisconnectDate() {
		return chargeDisconnectDate;
	}
	public void setChargeDisconnectDate(Timestamp chargeDisconnectDate) {
		this.chargeDisconnectDate = chargeDisconnectDate;
	}
	public int getFx_Status() {
		return fx_Status;
	}
	public void setFx_Status(int fx_Status) {
		this.fx_Status = fx_Status;
	}
	public int getCharge_info_id() {
		return charge_info_id;
	}
	public void setCharge_info_id(int charge_info_id) {
		this.charge_info_id = charge_info_id;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getAccountdisconnected() {
		return accountdisconnected;
	}
	public void setAccountdisconnected(int accountdisconnected) {
		this.accountdisconnected = accountdisconnected;
	}
	public String getSiid() {
		return siid;
	}
	public void setSiid(String siid) {
		this.siid = siid;
	}


	public String getBillingTriggerDisconnectStatus() {
		return billingTriggerDisconnectStatus;
	}


	public void setBillingTriggerDisconnectStatus(
			String billingTriggerDisconnectStatus) {
		this.billingTriggerDisconnectStatus = billingTriggerDisconnectStatus;
	}


	public String getFxAccNoStatus() {
		return fxAccNoStatus;
	}


	public void setFxAccNoStatus(String fxAccNoStatus) {
		this.fxAccNoStatus = fxAccNoStatus;
	}
	public String getBillingTriggerRateRenewalStatus() {
		return billingTriggerRateRenewalStatus;
	}


	public void setBillingTriggerRateRenewalStatus(
			String billingTriggerRateRenewalStatus) {
		this.billingTriggerRateRenewalStatus = billingTriggerRateRenewalStatus;
	}

	//[005] Start
	public ArrayList<String> getCurrencyChangeBillingTriggerValidationErrors() {
		return currencyChangeBillingTriggerValidationErrors;
	}


	public void setCurrencyChangeBillingTriggerValidationErrors(
			ArrayList<String> currencyChangeBillingTriggerValidationErrors) {
		this.currencyChangeBillingTriggerValidationErrors = currencyChangeBillingTriggerValidationErrors;
	}
	//[005] End

	public String getProjectmanagerassignedId() {
		return projectmanagerassignedId;
	}


	public void setProjectmanagerassignedId(String projectmanagerassignedId) {
		this.projectmanagerassignedId = projectmanagerassignedId;
	}


	public String getActionTakenById() {
		return actionTakenById;
	}


	public void setActionTakenById(String actionTakenById) {
		this.actionTakenById = actionTakenById;
	}


	public String getActionTakenByName() {
		return actionTakenByName;
	}


	public void setActionTakenByName(String actionTakenByName) {
		this.actionTakenByName = actionTakenByName;
	}


	


	public String getSuccess() {
		return success;
	}


	public void setSuccess(String success) {
		this.success = success;
	}


	public String getLineProp() {
		return lineProp;
	}


	public void setLineProp(String lineProp) {
		this.lineProp = lineProp;
	}


	public String getNoOfCharges() {
		return noOfCharges;
	}


	public void setNoOfCharges(String noOfCharges) {
		this.noOfCharges = noOfCharges;
	}


	public String getNoOfChargesForDisconnectForOldLine() {
		return noOfChargesForDisconnectForOldLine;
	}


	public void setNoOfChargesForDisconnectForOldLine(
			String noOfChargesForDisconnectForOldLine) {
		this.noOfChargesForDisconnectForOldLine = noOfChargesForDisconnectForOldLine;
	}


	public String[] getLine_prop() {
		return line_prop;
	}


	public void setLine_prop(String[] line_prop) {
		this.line_prop = line_prop;
	}


	public String getAccountActiveDate() {
		return accountActiveDate;
	}


	public void setAccountActiveDate(String accountActiveDate) {
		this.accountActiveDate = accountActiveDate;
	}


	public ArrayList getArrViewOrderDto() {
		return arrViewOrderDto;
	}


	public void setArrViewOrderDto(ArrayList arrViewOrderDto) {
		this.arrViewOrderDto = arrViewOrderDto;
	}
	public String getSearchLSI() {
		return searchLSI;
	}


	public void setSearchLSI(String searchLSI) {
		this.searchLSI = searchLSI;
	}


	public int getSearchLogicalSI() {
		return searchLogicalSI;
	}


	public void setSearchLogicalSI(int searchLogicalSI) {
		this.searchLogicalSI = searchLogicalSI;
	}


	public long getPonum() {
		return ponum;
	}


	public void setPonum(long ponum) {
		this.ponum = ponum;
	}


	public ArrayList getLSIS() {
		return lSIS;
	}


	public void setLSIS(ArrayList lsis) {
		lSIS = lsis;
	}


	public ArrayList getProductList() {
		return productList;
	}


	public void setProductList(ArrayList productList) {
		this.productList = productList;
	}
	public String getChallen_date() {
		return challen_date;
	}


	public void setChallen_date(String challen_date) {
		this.challen_date = challen_date;
	}


	public String getChallen_No() {
		return challen_No;
	}


	public void setChallen_No(String challen_No) {
		this.challen_No = challen_No;
	}


	public String getCustomerlsisresult() {
		return customerlsisresult;
	}


	public void setCustomerlsisresult(String customerlsisresult) {
		this.customerlsisresult = customerlsisresult;
	}


	public ArrayList getCusLSIResult() {
		return cusLSIResult;
	}


	public void setCusLSIResult(ArrayList cusLSIResult) {
		this.cusLSIResult = cusLSIResult;
	}


	


	public String getFx_Response() {
		return fx_Response;
	}


	public void setFx_Response(String fx_Response) {
		this.fx_Response = fx_Response;
	}


	public String getFx_Token_no() {
		return fx_Token_no;
	}


	public void setFx_Token_no(String fx_Token_no) {
		this.fx_Token_no = fx_Token_no;
	}


	public String getFx_status() {
		return fx_status;
	}


	public void setFx_status(String fx_status) {
		this.fx_status = fx_status;
	}
	
	
	public String getNoOfComponents() {
		return noOfComponents;
	}


	public void setNoOfComponents(String noOfComponents) {
		this.noOfComponents = noOfComponents;
	}


	public String getProductBillType() {
		return productBillType;
	}


	public void setProductBillType(String productBillType) {
		this.productBillType = productBillType;
	}


	public String getIsInHistory() {
		return isInHistory;
	}


	public void setIsInHistory(String isInHistory) {
		this.isInHistory = isInHistory;
	}





	public Integer getNoOfCharges_integer() {
		return noOfCharges_integer;
	}


	public void setNoOfCharges_integer(Integer noOfCharges_integer) {
		this.noOfCharges_integer = noOfCharges_integer;
	}


	public Integer getNoOfDisconnectClose() {
		return noOfDisconnectClose;
	}


	public void setNoOfDisconnectClose(Integer noOfDisconnectClose) {
		this.noOfDisconnectClose = noOfDisconnectClose;
	}


	public Integer getNoOfDisconnectInactive() {
		return noOfDisconnectInactive;
	}


	public void setNoOfDisconnectInactive(Integer noOfDisconnectInactive) {
		this.noOfDisconnectInactive = noOfDisconnectInactive;
	}


	public Integer getNoOfNewCharges() {
		return noOfNewCharges;
	}


	public void setNoOfNewCharges(Integer noOfNewCharges) {
		this.noOfNewCharges = noOfNewCharges;
	}

	public Integer getNoOfChangedCharges() {
		return noOfChangedCharges;
	}


	public void setNoOfChangedCharges(Integer noOfChangedCharges) {
		this.noOfChangedCharges = noOfChangedCharges;
	}

	public String getLineOldOrNew() {
		return lineOldOrNew;
	}


	public void setLineOldOrNew(String lineOldOrNew) {
		this.lineOldOrNew = lineOldOrNew;
	}


	public Date getChargeEndDate() {
		return chargeEndDate;
	}


	public void setChargeEndDate(Date chargeEndDate) {
		this.chargeEndDate = chargeEndDate;
	}


	public Date getChargeStartDate() {
		return chargeStartDate;
	}


	public void setChargeStartDate(Date chargeStartDate) {
		this.chargeStartDate = chargeStartDate;
	}


	


	public String getChargeCreatedOnOrder() {
		return chargeCreatedOnOrder;
	}


	public void setChargeCreatedOnOrder(String chargeCreatedOnOrder) {
		this.chargeCreatedOnOrder = chargeCreatedOnOrder;
	}


	public String getChargeEndedOnOrder() {
		return chargeEndedOnOrder;
	}


	public void setChargeEndedOnOrder(String chargeEndedOnOrder) {
		this.chargeEndedOnOrder = chargeEndedOnOrder;
	}


	public String getChargeStatus_otherLabel() {
		return chargeStatus_otherLabel;
	}


	public void setChargeStatus_otherLabel(String chargeStatus_otherLabel) {
		this.chargeStatus_otherLabel = chargeStatus_otherLabel;
	}


	public String getBillPeriod() {
		return billPeriod;
	}


	public void setBillPeriod(String billPeriod) {
		this.billPeriod = billPeriod;
	}


	public String getChargeId() {
		return chargeId;
	}


	public void setChargeId(String chargeId) {
		this.chargeId = chargeId;
	}


	public String getFxViewId() {
		return fxViewId;
	}


	public void setFxViewId(String fxViewId) {
		this.fxViewId = fxViewId;
	}


	public String getBillingTriggerProcess() {
		return billingTriggerProcess;
	}


	public void setBillingTriggerProcess(String billingTriggerProcess) {
		this.billingTriggerProcess = billingTriggerProcess;
	}	
	public long getChargeInfoId() {
		return chargeInfoId;
	}


	public void setChargeInfoId(long chargeInfoId) {
		this.chargeInfoId = chargeInfoId;
	}


	public String getBilling_Active_date() {
		return billing_Active_date;
	}


	public void setBilling_Active_date(String billing_Active_date) {
		this.billing_Active_date = billing_Active_date;
	}


	public String getBilling_End_Date() {
		return billing_End_Date;
	}


	public void setBilling_End_Date(String billing_End_Date) {
		this.billing_End_Date = billing_End_Date;
	}


	public String getDisconnectiondate() {
		return disconnectiondate;
	}


	public void setDisconnectiondate(String disconnectiondate) {
		this.disconnectiondate = disconnectiondate;
	}


	public long getIsAutoBilling() {
		return isAutoBilling;
	}


	public void setIsAutoBilling(long isAutoBilling) {
		this.isAutoBilling = isAutoBilling;
	}

	public String getCheckbox_status() {
		return checkbox_status;
	}


	public void setCheckbox_status(String checkbox_status) {
		this.checkbox_status = checkbox_status;
	}


	public ArrayList<ViewOrderDto> getLocArrayList() {
		return locArrayList;
	}


	public void setLocArrayList(ArrayList<ViewOrderDto> locArrayList) {
		this.locArrayList = locArrayList;
	}


	public ArrayList<ViewOrderDto> getFailuetoInsertLocData() {
		return failuetoInsertLocData;
	}


	public void setFailuetoInsertLocData(
			ArrayList<ViewOrderDto> failuetoInsertLocData) {
		this.failuetoInsertLocData = failuetoInsertLocData;
	}


	public ArrayList<ViewOrderDto> getSuccessfullyLOCDataInserted() {
		return successfullyLOCDataInserted;
	}


	public void setSuccessfullyLOCDataInserted(
			ArrayList<ViewOrderDto> successfullyLOCDataInserted) {
		this.successfullyLOCDataInserted = successfullyLOCDataInserted;
	}


	public String getLocDate_Status() {
		return locDate_Status;
	}


	public void setLocDate_Status(String locDate_Status) {
		this.locDate_Status = locDate_Status;
	}


	public String getLocNo_Status() {
		return locNo_Status;
	}


	public void setLocNo_Status(String locNo_Status) {
		this.locNo_Status = locNo_Status;
	}


	public long getOrderno() {
		return orderno;
	}


	public void setOrderno(long orderno) {
		this.orderno = orderno;
	}


	public long getSpid() {
		return spid;
	}


	public void setSpid(long spid) {
		this.spid = spid;
	}
	public String getServiceType() {
		return serviceType;
	}


	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}


	public ArrayList getServiceids() {
		return serviceids;
	}


	public void setServiceids(ArrayList serviceids) {
		this.serviceids = serviceids;
	}


	public ArrayList getServicetypes() {
		return servicetypes;
	}


	public void setServicetypes(ArrayList servicetypes) {
		this.servicetypes = servicetypes;
	}


	public String getEventids() {
		return eventids;
	}


	public void setEventids(String eventids) {
		this.eventids = eventids;
	}


	public ArrayList getOsnlists() {
		return osnlists;
	}


	public void setOsnlists(ArrayList osnlists) {
		this.osnlists = osnlists;
	}


	public long getSendToM6() {
		return sendToM6;
	}


	public void setSendToM6(long sendToM6) {
		this.sendToM6 = sendToM6;
	}


	public String getRemarks() {
		return remarks;
	}


	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getLine_status() {
		return line_status;
	}


	public void setLine_status(String line_status) {
		this.line_status = line_status;
	}


	public int getTaskOwnerId() {
		return taskOwnerId;
	}


	public void setTaskOwnerId(int taskOwnerId) {
		this.taskOwnerId = taskOwnerId;
	}
	public String getServiceno_m6() {
		return serviceno_m6;
	}


	public void setServiceno_m6(String serviceno_m6) {
		this.serviceno_m6 = serviceno_m6;
	}

	public String getReasonName() {
		return reasonName;
	}


	public void setReasonName(String reasonName) {
		this.reasonName = reasonName;
	}


	public int getReasonID() {
		return reasonID;
	}


	public void setReasonID(int reasonID) {
		this.reasonID = reasonID;
	}
	
	public String getBiling_status() {
		return biling_status;
	}


	public void setBiling_status(String biling_status) {
		this.biling_status = biling_status;
	}


	public String getLocRecDate() {
		return locRecDate;
	}


	public void setLocRecDate(String locRecDate) {
		this.locRecDate = locRecDate;
	}


	public String getLocRecDate_Status() {
		return locRecDate_Status;
	}


	public void setLocRecDate_Status(String locRecDate_Status) {
		this.locRecDate_Status = locRecDate_Status;
	}


	public String getMode() {
		return mode;
	}


	public void setMode(String mode) {
		this.mode = mode;
	}


	public String getM6_att_fxchanged() {
		return m6_att_fxchanged;
	}


	public void setM6_att_fxchanged(String m6_att_fxchanged) {
		this.m6_att_fxchanged = m6_att_fxchanged;
	}


	public long getClepFileId() {
		return clepFileId;
	}


	public void setClepFileId(long clepFileId) {
		this.clepFileId = clepFileId;
	}


	public String getClepJMSReqMsgId() {
		return clepJMSReqMsgId;
	}


	public void setClepJMSReqMsgId(String clepJMSReqMsgId) {
		this.clepJMSReqMsgId = clepJMSReqMsgId;
	}


	public int getIsBTDone() {
		return isBTDone;
	}


	public void setIsBTDone(int isBTDone) {
		this.isBTDone = isBTDone;
	}


	public int getIsMailSentSuccess() {
		return isMailSentSuccess;
	}


	public void setIsMailSentSuccess(int isMailSentSuccess) {
		this.isMailSentSuccess = isMailSentSuccess;
	}


	public int getIsSuccessApproved() {
		return isSuccessApproved;
	}


	public void setIsSuccessApproved(int isSuccessApproved) {
		this.isSuccessApproved = isSuccessApproved;
	}


	public String getOrder_creation_source() {
		return order_creation_source;
	}


	public void setOrder_creation_source(String order_creation_source) {
		this.order_creation_source = order_creation_source;
	}


	public String getOrder_sourceId() {
		return order_sourceId;
	}


	public void setOrder_sourceId(String order_sourceId) {
		this.order_sourceId = order_sourceId;
	}


	public long getServiceTypeID() {
		return serviceTypeID;
	}


	public void setServiceTypeID(long serviceTypeID) {
		this.serviceTypeID = serviceTypeID;
	}


	public Long getTm6OrderNo() {
		return Tm6OrderNo;
	}


	public void setTm6OrderNo(Long tm6OrderNo) {
		Tm6OrderNo = tm6OrderNo;
	}


	public String getResponseClepOrderMsgAfterAproval() {
		return responseClepOrderMsgAfterAproval;
	}


	public void setResponseClepOrderMsgAfterAproval(
			String responseClepOrderMsgAfterAproval) {
		this.responseClepOrderMsgAfterAproval = responseClepOrderMsgAfterAproval;
	}


	public int getIsCOPCSentMsgToMPP() {
		return isCOPCSentMsgToMPP;
	}


	public void setIsCOPCSentMsgToMPP(int isCOPCSentMsgToMPP) {
		this.isCOPCSentMsgToMPP = isCOPCSentMsgToMPP;
	}
	
	
	
		private String duplicate_cktid;
	
	public String getDuplicate_cktid() {
		return duplicate_cktid;
	}


	public void setDuplicate_cktid(String duplicate_cktid) {
		this.duplicate_cktid = duplicate_cktid;
	}	
	
	public String getCktId() {
		return cktId;
	}


	public void setCktId(String cktId) {
		this.cktId = cktId;
	}	
	
	
	
public String getM6No() {
		return m6No;
	}


	public void setM6No(String no) {
		m6No = no;
	}


	public int getServiceID() {
		return serviceID;
	}


	public void setServiceID(int serviceID) {
		this.serviceID = serviceID;
	}


	public OrderHeaderDTO getOrderInfo() {
		return orderInfo;
	}


	public void setOrderInfo(OrderHeaderDTO orderInfo) {
		this.orderInfo = orderInfo;
	}


	public BillingTriggerValidation getBillingTriggerAllowDenyLogic() {
		return billingTriggerAllowDenyLogic;
	}


	public void setBillingTriggerAllowDenyLogic(
			BillingTriggerValidation billingTriggerAllowDenyLogic) {
		this.billingTriggerAllowDenyLogic = billingTriggerAllowDenyLogic;
	}


	public String getSearchLineTriggerStatus() {
		return searchLineTriggerStatus;
	}


	public void setSearchLineTriggerStatus(String searchLineTriggerStatus) {
		this.searchLineTriggerStatus = searchLineTriggerStatus;
	}


	public String getBillingtriggerError() {
		return billingtriggerError;
	}


	public void setBillingtriggerError(String billingtriggerError) {
		this.billingtriggerError = billingtriggerError;
	}


	public String getBtDatecolor() {
		return btDatecolor;
	}


	public void setBtDatecolor(String btDatecolor) {
		this.btDatecolor = btDatecolor;
	}


	public String getLoc_rec_datecolor() {
		return loc_rec_datecolor;
	}


	public void setLoc_rec_datecolor(String loc_rec_datecolor) {
		this.loc_rec_datecolor = loc_rec_datecolor;
	}


	public String getLocdatecolor() {
		return locdatecolor;
	}


	public void setLocdatecolor(String locdatecolor) {
		this.locdatecolor = locdatecolor;
	}


	public String getLocnocolor() {
		return locnocolor;
	}


	public void setLocnocolor(String locnocolor) {
		this.locnocolor = locnocolor;
	}


	public String getOrder_subtype() {
		return order_subtype;
	}


	public void setOrder_subtype(String order_subtype) {
		this.order_subtype = order_subtype;
	}


	public String getOrdertype() {
		return ordertype;
	}


	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}


	public int getShowline() {
		return showline;
	}


	public void setShowline(int showline) {
		this.showline = showline;
	}


	public String getAccountid() {
		return accountid;
	}


	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}


	public String getErrorlog() {
		return errorlog;
	}


	public void setErrorlog(String errorlog) {
		this.errorlog = errorlog;
	}
	
	

	public String getIsFirstTask() {
		return isFirstTask;
	}
	public void setIsFirstTask(String isFirstTask) {
		this.isFirstTask = isFirstTask;
	}
	public String getErrorlog_charges() {
		return errorlog_charges;
	}


	public void setErrorlog_charges(String errorlog_charges) {
		this.errorlog_charges = errorlog_charges;
	}


	public HashMap<String, ViewOrderDto> getMap_validate() {
		return map_validate;
	}


	public void setMap_validate(HashMap<String, ViewOrderDto> map_validate) {
		this.map_validate = map_validate;
	}


	public String getIsNewOrDisconnectedInCurrentService() {
		return isNewOrDisconnectedInCurrentService;
	}


	public void setIsNewOrDisconnectedInCurrentService(
			String isNewOrDisconnectedInCurrentService) {
		this.isNewOrDisconnectedInCurrentService = isNewOrDisconnectedInCurrentService;
	}


	public String getCumulative_status() {
		return cumulative_status;
	}


	public void setCumulative_status(String cumulative_status) {
		this.cumulative_status = cumulative_status;
	}


	public String getCumulative_status_desc() {
		return cumulative_status_desc;
	}


	public void setCumulative_status_desc(String cumulative_status_desc) {
		this.cumulative_status_desc = cumulative_status_desc;
	}


	public String getFxServiceUpdateStatus() {
		return fxServiceUpdateStatus;
	}


	public void setFxServiceUpdateStatus(String fxServiceUpdateStatus) {
		this.fxServiceUpdateStatus = fxServiceUpdateStatus;
	}


	public String getRedirect_status() {
		return redirect_status;
	}


	public void setRedirect_status(String redirect_status) {
		this.redirect_status = redirect_status;
	}


	public String getRedirect_status_desc() {
		return redirect_status_desc;
	}


	public void setRedirect_status_desc(String redirect_status_desc) {
		this.redirect_status_desc = redirect_status_desc;
	}


	public String getRedirectedLSI() {
		return redirectedLSI;
	}


	public void setRedirectedLSI(String redirectedLSI) {
		this.redirectedLSI = redirectedLSI;
	}


	public String getRedirectExternalAc() {
		return redirectExternalAc;
	}


	public void setRedirectExternalAc(String redirectExternalAc) {
		this.redirectExternalAc = redirectExternalAc;
	}


	public String getRedirectInternalAc() {
		return redirectInternalAc;
	}


	public void setRedirectInternalAc(String redirectInternalAc) {
		this.redirectInternalAc = redirectInternalAc;
	}

//start	[001]
	public String getBtDoneByName() {
		return btDoneByName;
	}


	public void setBtDoneByName(String btDoneByName) {
		this.btDoneByName = btDoneByName;
	}
	//End	[001]


	public String getBtDoneDate() {
		return btDoneDate;
	}


	public void setBtDoneDate(String btDoneDate) {
		this.btDoneDate = btDoneDate;
	}


	public String getPoExpiryDate() {
		return poExpiryDate;
	}


	public void setPoExpiryDate(String poExpiryDate) {
		this.poExpiryDate = poExpiryDate;
	}
	
	//vijay start
	public String getOrderStage() {
		return orderStage;
	}


	public void setOrderStage(String orderStage) {
		this.orderStage = orderStage;
	}
	//vijay end
	public String getServiceList() {
		return serviceList;
	}
	public void setServiceList(String serviceList) {
		this.serviceList = serviceList;
	}
	public boolean isSaved() {
		return saved;
	}
	public void setSaved(boolean saved) {
		this.saved = saved;
	}
	public boolean isAllServicesSelectd() {
		return allServicesSelectd;
	}
	public void setAllServicesSelectd(boolean allServicesSelectd) {
		this.allServicesSelectd = allServicesSelectd;
	}
	public boolean isPartialInitiator() {
		return partialInitiator;
	}

	public void setPartialInitiator(boolean partialInitiator) {
		this.partialInitiator = partialInitiator;
	}
	//[004] start
	public long getSubChangeTypeId() {
		return subChangeTypeId;
	}
	public void setSubChangeTypeId(long subChangeTypeId) {
		this.subChangeTypeId = subChangeTypeId;
	}
	public String getM6FxProgressStatus() {
		return m6FxProgressStatus;
	}
	public void setM6FxProgressStatus(String m6FxProgressStatus) {
		this.m6FxProgressStatus = m6FxProgressStatus;
	}
	public long getParallelUpgradeLSI() {
		return parallelUpgradeLSI;
	}
	public void setParallelUpgradeLSI(long parallelUpgradeLSI) {
		this.parallelUpgradeLSI = parallelUpgradeLSI;
	}
	public long getServiceLineNo() {
		return serviceLineNo;
	}
	public void setServiceLineNo(long serviceLineNo) {
		this.serviceLineNo = serviceLineNo;
	}
	
	//[004] end
	//[0010] start
	public int getFx_schedular_create_status() {
		return fx_schedular_create_status;
	}
	public void setFx_schedular_create_status(int fx_schedular_create_status) {
		this.fx_schedular_create_status = fx_schedular_create_status;
	}
	//[0010] end
		//[0012]
	public Boolean getIsavailableforbt() {
		return isavailableforbt;
	}
	public void setIsavailableforbt(Boolean isavailableforbt) {
		this.isavailableforbt = isavailableforbt;
	}
	
}
