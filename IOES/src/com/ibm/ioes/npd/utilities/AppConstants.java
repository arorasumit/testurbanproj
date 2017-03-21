package com.ibm.ioes.npd.utilities;

import org.apache.log4j.Logger;

/**
 * @author Varun
 * @version 1.0 This Class containing common Application constants for
 * 
 */
public interface AppConstants {

	public static final String APP_NAME = "NPD";
	
	public static final Integer TRUE = new Integer(1);

	public static final Integer FALSE = new Integer(0);

	public static final String ACTIVE = "Y";

	public static final String INACTIVE = "N";

	public static final String APPCONTEXTPATH = "MenuContextPath";

	public static final String CHANGE_PASS = "changepwd";
	
	public static final String NPD_USERS_SESSION_NAME = "UserBean";
		
	public static final String LOGINBEAN = "LoginBean";

	public static final int INACTIVE_FLAG = 0;

	public static final int ACTIVE_FLAG = 1;
	
	public static final int DRAFT_FLAG = 2;
	
	public static final String DATE_TIME_FORMAT = "dd-MMM-yyyy HH:mm:ss a";

	public static final String DATE_FORMAT = "M-dd-yyyy";

	public static final String TIME_FORMAT = "HH:mm";

	public static final String DATE_FORMAT_PROC = "dd-MMM-yyyy";

	public static final String DATE_FORMAT_MMDDYY = "MM/dd/yyyy";

	public static final String DATE_FORMAT_FILE = "ddMMyyyy";

	public static final String DEFAULT_PROPFILE = "DirPath.properties";

	public static final String EMAIL_SEPRATOR = ",";

	public static final String ENCRYPTION_KEY = GetPropertiesUtility
			.getProperty("forecast.encryptionKey");

	public static final String ERRORPAGE = "ErrorPageAction";

	public static final String MESSAGE_NAME = "name";

	public static final String MESSAGE_ID = "id";

	public static final String FAILURE = "failure";

	public static final Logger NPDLOGGER = Logger.getLogger("com.ibm.npd");

	public static final String PROP_FILE = "DirPath";

	public static final String SUCCESS = "success";

	public static final String MEETING_TYPE_CFT = "CFT";

	public static final String MEETING_TYPE_NPSC = "NPSC";

	public static final String MANDATORY_LIST_TYPE = "M";

	public static final String OPTIONAL_LIST_TYPE = "O";

	public static final String INI_VALUE = "";

	public static final String SUBJECT_MEETINGSCHEDULE = "Subject.MeetingSchedule";

	public static final String MSG_MEETINGSCHEDULE = "message.MeetingSchedule";
	
	public static final String MSG_MEETINGSCHEDULE1 = "message.MeetingSchedule1";
	
	public static final String MSG_MEETINGSCHEDULE_SMS = "message.MeetingSchedule_SMS";
	
	public static final String TASK_ASSIGNED = "message.TaskAssigned";

	public static final String RECORD_SAVE_SUCCESS = "recordInsertUpdateSuccess";

	public static final String RECORD_SAVE_FAILURE = "recordInsertFailure";
	
	public static final String RECORD_UPDATE_SUCCESS = "recordUpdateSuccess";

	public static final String RECORD_UPDATE_FAILURE = "recordUpdateFailure";

	public static final String SUBJECT_MEETINGMOM = "Subject.MeetingMOM";

	public static final String MSG_MEETINGMOM = "message.MeetingMOM";
	
	public static final String SELECT_OPTION = "-1";
	
	public static final String NO_RECORD = "noRecordExists";
	
	public static final String NO_PREVIOUS_OPTION = "0";
	
	public static final int FILE_SIZE=10485760;
	
	public static final String EXCEEDED_FILE_SIZE = "filesize.exceed";
	
	public static final int TASK_CLOSED_STATUS=2;
	
	public static final String TYPE = "1";

	public static final String ATTACH_NEW = "attachNew";
	
	public static final String EDITING_FINALIZED = "editingFinalized";
	
	public static final String ADMIN_USERID="50";
	
	public static final String SUBJECT_TASKASSIGNED = "Subject.TaskAssigned";

	public static final String INITIAL_SERVLET_REQUESTED = "INITIAL_SERVLET_REQUESTED";
	
	public static final String SAME_DATA = "SAMEDATA";
	
	public static final String PROJECT_MANAGER = "Product Manager";
	
	public static final String TECH_HEAD = "Technical Head";
	
	public static final String TASK_MAX_ASSIGNED_DAYS = "10";
	
	public static final String MY_TO_DO_LIST_FIRST_LEVEL_ESCALATION = "1";
	
	public static final String MY_TO_DO_LIST_SECOND_LEVEL_ESCALATION = "7";
	
	public static final String PLR_MAIL_FOR_DOC = "1";
	
	public static final String PLR_FIRST_LEVEL_ESCALATION = "4";
	
	public static final String PLR_SECOND_LEVEL_ESCALATION = "10";
	
	public static final String TIMEPICKER_FORMAT = "h:mm a";
	
	public static final String Type_of_employee_new="NEW";
	
	public static final String Type_of_employee_old="OLD";
	
	public static final String ESCALATION_LEVEL_1="LEVEL1ESC";
	
	public static final String ESCALATION_LEVEL_2="LEVEL2ESC";
	
	public static final String SWITCH_ON="ON";
	
	public static final String SWITCH_OFF="OFF";
	
	public static final String SSFID="SSFID";
	
	public static final long one_hour_millisec=24*60*60*1000;
	
	//ROHIT VERMA NEW CR
	public static final String RECORD_DELETE_SUCCESS = "recordDeleteSuccess";
	
	public static final String UploadExcelPlanTemplateFileName="UploadExcelPlanTemplate.xls"; 
	
	public static final String UploadExcelPlanTemplateFilePath=Messages.getMessageValue("TEMPLATE_DIR_PATH") + AppConstants.UploadExcelPlanTemplateFileName;
	
}
