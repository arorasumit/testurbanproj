package com.ibm.ioes.npd.hibernate.dao;

/**
 * It is used to hold the Constants for the initializing Dao's
 * 
 * @author Varun
 * @version 1.0
 * @see BaseDaoFactory
 * @see CommonBaseDao
 */
public interface DaoConstants {

	int BASE_DAO_CLASS = 1;

	int NPD_USER_DAO = 2;
	
	int MEETING_DAO = 3;
	
	int REFERENCE_DOC_DAO = 4;
	
	int MASTER_PROJECT_PLAN_DAO = 5;
	
	int PRODUCT_CREATION_DAO = 6;
	
	int RFI_DOCUMENT_DAO=7;
	
	int ISSUE_CAPTURE_DAO=8;
	
	int ASSUMPTION_CAPTURE_DAO=9;

	int RISK_CAPTURE_DAO=10;
	
	String PROP_FILE = "DirPath";
	
	// Table names constants with value of hibernate bean name

	String TM_EMPLOYEE = "TmEmployee";

	String TM_MEETINGS = "TmMeetings";

	String TM_REFERENCEDOCS = "TmReferencedocs";

	String TM_ROLES = "TmRoles";

	String TM_WORKFLOW = "TmWorkflow";

	String TTRN_MEETINGATTENDIES = "TtrnMeetingattendies";

	String TTRN_MEETINGMOMS = "TtrnMeetingmoms";

	String TTRN_MEETINGSCHEDULES = "TtrnMeetingschedules";

	String TM_WORKFLOWSTAGE = "TmWorkflowstage";
	
	
	

}
