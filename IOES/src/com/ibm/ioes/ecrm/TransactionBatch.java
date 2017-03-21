package com.ibm.ioes.ecrm;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.ibm.ioes.clep.CLEPUtility;
import com.ibm.ioes.exception.IOESException;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.AppUtility;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Utility;

// Referenced classes of package com.ibm.ecrm.process:
// DBConnectionRetriever

public class TransactionBatch {

	
	private static final Logger logger;
	static {
		logger = Logger.getLogger(TransactionBatch.class);
	}	
	private static String insertDate=null;
	private static String updateDate=null;
	private static String tableName=null;
	public static String spInsertAccountECRMtoIOMS = "{call IOE.SP_INSERT_ECRM_ACCOUNT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	public static String spInsertECRMRoleToIOMS = "{call IOE.SP_INSERT_ECRM_ROLE(?,?,?,?,?,?,?,?,?,?,?)}";			
	public static String spInsertECRMEmployeeToIOMS = "{call IOE.SP_INSERT_ECRM_EMPLOYEE(?,?,?,?,?,?,?,?)}";		
	public static String spInsertECRMStateToIOMS = "{call IOE.SP_INSERT_ECRM_STATE(?,?,?,?,?,?,?,?,?)}";		
	public static String spInsertECRMCityToIOMS = "{call IOE.SP_INSERT_ECRM_CITY(?,?,?,?,?,?,?,?,?)}";		
	public static String spInsertECRMQuotesToIOMS = "{call IOE.SP_INSERT_IOES_QUOTES(?,?,?,?,?,?,?,?)}";
	public static String spUpdateOpportunityNoToIOMS = "{call IOE.SP_UPDATE_SFOPPORNO_TO_IOMS(?,?,?,?,?,?,?,?)}";
	public static String spUpdateIOMSDataTracker = "{call IOE.SP_UPDATE_TRACK_ECRM_DATA_PULLING(?,?,?,?,?,?,?)}";		
	public static String spInsertECRMUserInfoToIOMS="{call IOE.SP_INSERT_ECRM_USERINFO(?,?,?,?,?,?,?,?,?,?) }";
	public static String spUpdateECRMCityToIOMS="{call IOE.SP_UPDATE_ECRM_CITY(?,?,?,?,?,?,?,?,?)}";
	public static String spUpdateECRMStateToIOMS="{call IOE.SP_UPDATE_ECRM_STATE(?,?,?,?,?,?,?,?,?)}";
	public static String spUpdateECRMQuotesToIOMS = "{call IOE.SP_UPDATE_IOES_QUOTES(?,?,?,?,?,?,?)}";
	public static String spUpdateECRMRoleToIOMS = "{call IOE.SP_UPDATE_ECRM_ROLE(?,?,?,?,?,?,?,?,?,?,?)}";
	public static String spUpdateECRMEmployeeToIOMS = "{call IOE.SP_UPDATE_ECRM_EMPLOYEE(?,?,?,?,?,?,?,?)}";
	public static String spUpdateECRMUserInfoToIOMS="{call IOE.SP_UPDATE_ECRM_USERINFO(?,?,?,?,?,?,?,?,?) }";
	public static String spInsertECRMBCPAddressToIOMS = "{call IOE.SP_INSERT_UPDATE_ECRM_BCPADDRESS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	public static String spInsertECRMdispatchAddressToIOMS = "{call IOE.SP_INSERT_UPDATE_ECRM_DISPATCHADDRESS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";		
	public static String spInsertECRMPopLocationToIOMS = "{call IOE.SP_INSERT_UPDATE_ECRM_NETWORK_LOCATION(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";		
	public static String spInsertUpdateECRMProductDDValuesToIOMS="{call IOE.SP_INSERT_UPDATE_PRODUCT_ATT_VALUES(?,?,?,?,?,?,?,?,?) }";
	//[001]rahulT
	public static String spInsertUpdateOpportunityIDToIOMS="{call IOE.SP_INSERT_UPDATE_OPPORTUNITYID_VALUES(?,?,?,?,?,?)}";
	//[001]rahulT
	public static String spUpdateECRMAccountToIOMS = "{call IOE.SP_UPDATE_ECRM_ACCOUNT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static String strGetUpdateDateFromIOMS=	"SELECT IOE.FORMAT_DATE_REPORT1(UPDATEDATE) AS UPDATEDATE FROM IOE.TTRACKINSERTDATE WHERE TABLENAME=?";	
	public static String spInsertECRMGAMInfoToIOMS="{call IOE.SP_INSERT_ECRM_GAM_INFO(?,?,?,?,?,?,?,?,?,?,?) }";
	public static String spInsertECRMGAMQuotesToIOMS = "{call IOE.SP_INSERT_IOES_GAM_QUOTES(?,?,?,?,?,?)}";
	public static String spUpdateECRMGAMInfoToIOMS="{call IOE.SP_UPDATE_ECRM_GAM_INFO(?,?,?,?,?,?) }";
	public static String spUpdateECRMEscalationInfoToIOMS="{call IOE.SP_UPDATE_ECRM_ACCROLE_LEVELINFO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }";
	public static String spUpdateECRMEscatnLevelInfoToIOMS="{call IOE.SP_UPDATE_ECRM_LEVEL_INFO(?,?,?,?,?,?,?) }";
		
	private static String strGetECRMEmployeeWithRole = "SELECT jtrr.role_resource_id, CASE WHEN jtrr.role_id = 10081 THEN 1 "+
					" WHEN jtrr.role_id = 10114 THEN 2 ELSE jtrr.role_id END AS ROLE_ID , jre.resource_number, fu.EMPLOYEE_ID, "+ 
							  	" fu.EMAIL_ADDRESS, fu.user_id,fu.user_name,JRE.SOURCE_FIRST_NAME AS first_name," +
							  	" JRE.SOURCE_LAST_NAME AS last_name,JRE.SOURCE_PHONE AS phone_no "
							  + " FROM apps.jtf_rs_role_relations jtrr,"
							  + " apps.jtf_rs_resource_extns jre, "
							  + "  apps.fnd_user fu, "
							  + "  apps.jtf_rs_roles_tl jrt "
							  + "  WHERE jre.resource_id =  jtrr.role_resource_id "
							  + " AND jtrr.role_id = jrt.role_id "
							  + " AND fu.user_id = jre.user_id "													 
							  + " AND fu.CREATION_DATE BETWEEN TO_DATE(?) and sysdate ";

	private static String strGetWonQuotes =  "SELECT NVL(ACCOUNT_NUMBER,' ') AS party_id, NVL(asl.lead_number,' ') AS lead_number, NVL(aqha.quote_number,0) AS quote_number  , 1 as STATUS ,  NVL(asl.attribute14,' ') AS salesforce_opportunityno  "
							+ " FROM apps.hz_parties hz , APPS.HZ_CUST_ACCOUNTS HCA , apps.as_leads_all asl, apps.aso_quote_related_objects aso, apps.aso_quote_headers_all aqha"
							+ " WHERE asl.customer_id = hz.party_id AND HCA.PARTY_ID =  HZ.PARTY_ID "
							+ " AND aso.object_id(+) = asl.lead_id "
							+ " AND aso.QUOTE_OBJECT_ID = aqha.quote_header_id(+) "
							+ " AND EXISTS "
							+ " ( SELECT 1 "
							+ " FROM apps.as_lead_lines_all asll "
							+ " WHERE asll.lead_id = asl.lead_id "
							+ " AND asll.attribute8 = 1  ) "
							+ " AND ((asl.CREATION_DATE BETWEEN TO_DATE(?)  AND SYSDATE ) or (asl.LAST_UPDATE_DATE BETWEEN TO_DATE(?)  AND SYSDATE))";

	private static String strGetWonQuotesWithAccount =  "SELECT NVL(ACCOUNT_NUMBER,' ') AS party_id, NVL(asl.lead_number,' ') AS lead_number, NVL(aqha.quote_number,0) AS quote_number  , 1 as STATUS , NVL(asl.attribute14,' ') AS salesforce_opportunityno "
		+ " FROM apps.hz_parties hz , APPS.HZ_CUST_ACCOUNTS HCA , apps.as_leads_all asl, apps.aso_quote_related_objects aso, apps.aso_quote_headers_all aqha "
		+ " WHERE asl.customer_id = hz.party_id AND HCA.PARTY_ID =  HZ.PARTY_ID "
		+ " AND aso.object_id(+) = asl.lead_id "
		+ " AND aso.QUOTE_OBJECT_ID = aqha.quote_header_id(+) "
		+ " AND EXISTS "
		+ " ( SELECT 1 "
		+ " FROM apps.as_lead_lines_all asll "
		+ " WHERE asll.lead_id = asl.lead_id "
		+ " AND asll.attribute8 = 1  ) "
		+ "  AND HCA.CUST_ACCOUNT_ID=?";
	
	private static String strGetWonQuotesModify =  "SELECT NVL(ACCOUNT_NUMBER,' ') AS party_id, NVL(asl.lead_number,' ') AS lead_number, NVL(aqha.quote_number,0) AS quote_number  , 1 as STATUS  "
												+ " FROM apps.hz_parties hz , APPS.HZ_CUST_ACCOUNTS HCA , apps.as_leads_all asl, apps.aso_quote_related_objects aso, apps.aso_quote_headers_all aqha "
												+ " WHERE asl.customer_id = hz.party_id AND HCA.PARTY_ID =  HZ.PARTY_ID "
												+ " AND aso.object_id = asl.lead_id "
												+ " AND aso.QUOTE_OBJECT_ID = aqha.quote_header_id "
												+ " AND EXISTS "
												+ " ( SELECT 1 "
												+ " FROM apps.as_lead_lines_all asll "
												+ " WHERE asll.lead_id = asl.lead_id "
												+ " AND asll.attribute8 = 1  ) "
												+ " AND aso.LAST_UPDATE_DATE BETWEEN TO_DATE(?)  AND SYSDATE";
	//lawkush Start
	
	private static String strGetWonQuotesModifyWithAccount =  "SELECT NVL(ACCOUNT_NUMBER,' ') AS party_id, NVL(asl.lead_number,' ') AS lead_number, NVL(aqha.quote_number,0) AS quote_number  , 1 as STATUS  "
		+ " FROM apps.hz_parties hz , APPS.HZ_CUST_ACCOUNTS HCA , apps.as_leads_all asl, apps.aso_quote_related_objects aso, apps.aso_quote_headers_all aqha "
		+ " WHERE asl.customer_id = hz.party_id AND HCA.PARTY_ID =  HZ.PARTY_ID "
		+ " AND aso.object_id = asl.lead_id "
		+ " AND aso.QUOTE_OBJECT_ID = aqha.quote_header_id "
		+ " AND EXISTS "
		+ " ( SELECT 1 "
		+ " FROM apps.as_lead_lines_all asll "
		+ " WHERE asll.lead_id = asl.lead_id "
		+ " AND asll.attribute8 = 1  ) "
		+ " AND HCA.CUST_ACCOUNT_ID=? ";
	
	private static String strGetECRMEmployeeWithRoleModify = "SELECT distinct jtrr.role_resource_id, jre.resource_number, fu.EMPLOYEE_ID, "+ 
															  	" fu.EMAIL_ADDRESS, fu.user_id,fu.user_name,JRE.SOURCE_FIRST_NAME AS first_name," +
															  	" JRE.SOURCE_LAST_NAME AS last_name,NVL(JRE.SOURCE_PHONE,' ') AS phone_no "
															  + " FROM apps.jtf_rs_role_relations jtrr,"
															  + " apps.jtf_rs_resource_extns jre, "
															  + "  apps.fnd_user fu, "
															  + "  apps.jtf_rs_roles_tl jrt "
															  + "  WHERE jre.resource_id =  jtrr.role_resource_id "
															  + " AND jtrr.role_id = jrt.role_id "
															  + " AND fu.user_id = jre.user_id "													 
															  + " AND fu.LAST_UPDATE_DATE BETWEEN TO_DATE(?) and sysdate ";
	//	fetching BCP Address records from CRM for update in IOMS
	private static String strGetECRMBCPAddress  ="SELECT"  
	    										+" NVL(ADDRESS_ID,0) AS ADDRESS_ID ,NVL(CUST_ACCOUNT_ID,0) AS CUST_ACCOUNT_ID,"
	    										+" NVL(COUNTRY,' ') AS COUNTRY,NVL(ADDRESS1,' ') AS ADDRESS1,NVL(ADDRESS2,' ') AS ADDRESS2,"
	    										+" NVL(ADDRESS3,' ') AS ADDRESS3,NVL(ADDRESS4,' ') AS ADDRESS4,NVL(CITY,' ') AS CITY,"
	    										+" NVL(POSTAL_CODE,' ') AS POSTAL_CODE,NVL(STATE,' ') AS STATE,"
	    										+" ACTIVE_END_DATE,LAST_UPDATE_DATE,CREATION_DATE,LST_DATE,"
	    										+" NVL(LAST_UPDATED_BY,0) AS LAST_UPDATED_BY,NVL(CREATED_BY,0) AS CREATED_BY,"
	    										+" NVL(CONACT_PERSON_NAME,' ') AS CONACT_PERSON_NAME,NVL(CONTACT_PERSON_DESIGNATION,' ') AS CONTACT_PERSON_DESIGNATION,"
	    										+" NVL(CONTACT_PERSON_MOBILE,' ') AS CONTACT_PERSON_MOBILE,NVL(CONTACT_PERSON_EMAIL,' ') AS CONTACT_PERSON_EMAIL,"
	    										+" NVL(CONTACT_PERSON_FAX,' ') AS CONTACT_PERSON_FAX,NVL(LST_NUMBER,' ') AS LST_NUMBER,"    
	    										+" NVL(ADDRESS_TYPE,' ') AS ADDRESS_TYPE,NVL(OLD_ADDRESS_ID,0) AS OLD_ADDRESS_ID,"
	    										+" NVL(CIRCLE_ID,0) AS CIRCLE_ID"
	    										+" FROM" 
	    										+" APPS.IBMOE_ORDER_ADDRESS" 
	    										+" WHERE" 
	    										+" ADDRESS_TYPE = 'BILLING'" ;
		  			
	//	fetching BCP Address records from CRM for update in IOMS
	private static String strGetUpdatedECRMBCPAddress  = "SELECT"  
												+" NVL(ADDRESS_ID,0) AS ADDRESS_ID ,NVL(CUST_ACCOUNT_ID,0) AS CUST_ACCOUNT_ID,"
												+" NVL(COUNTRY,' ') AS COUNTRY,NVL(ADDRESS1,' ') AS ADDRESS1,NVL(ADDRESS2,' ') AS ADDRESS2,"
												+" NVL(ADDRESS3,' ') AS ADDRESS3,NVL(ADDRESS4,' ') AS ADDRESS4,NVL(CITY,' ') AS CITY,"
												+" NVL(POSTAL_CODE,' ') AS POSTAL_CODE,NVL(STATE,' ') AS STATE,"
												+" ACTIVE_END_DATE,LAST_UPDATE_DATE,CREATION_DATE,LST_DATE,"
												+" NVL(LAST_UPDATED_BY,0) AS LAST_UPDATED_BY,NVL(CREATED_BY,0) AS CREATED_BY,"
												+" NVL(CONACT_PERSON_NAME,' ') AS CONACT_PERSON_NAME,NVL(CONTACT_PERSON_DESIGNATION,' ') AS CONTACT_PERSON_DESIGNATION,"
												+" NVL(CONTACT_PERSON_MOBILE,' ') AS CONTACT_PERSON_MOBILE,NVL(CONTACT_PERSON_EMAIL,' ') AS CONTACT_PERSON_EMAIL,"
												+" NVL(CONTACT_PERSON_FAX,' ') AS CONTACT_PERSON_FAX,NVL(LST_NUMBER,' ') AS LST_NUMBER,"    
												+" NVL(ADDRESS_TYPE,' ') AS ADDRESS_TYPE,NVL(OLD_ADDRESS_ID,0) AS OLD_ADDRESS_ID,"
												+" NVL(CIRCLE_ID,0) AS CIRCLE_ID"
												+" FROM" 
												+" APPS.IBMOE_ORDER_ADDRESS" 
												+" WHERE" 
												+" ADDRESS_TYPE = 'BILLING'" 
												+" AND LAST_UPDATE_DATE BETWEEN TO_DATE(?) AND SYSDATE";

	//	fetching Disptach Address records from CRM for update in IOMS
	//private static String strGetECRMDispatchAddress  = " SELECT * FROM APPS.IBMOE_ORDER_ADDRESS WHERE ADDRESS_TYPE = 'GENERAL' AND CREATION_DATE BETWEEN TO_DATE(?) AND SYSDATE";
	private static String strGetECRMDispatchAddress  = "SELECT"
												+" NVL(ADDRESS_ID,0) AS ADDRESS_ID,NVL(CUST_ACCOUNT_ID,0) AS CUST_ACCOUNT_ID,"
												+" NVL(COUNTRY,' ') AS COUNTRY,NVL(ADDRESS1,' ') AS ADDRESS1,"
												+" NVL(ADDRESS2,' ') AS ADDRESS2,NVL(ADDRESS3,' ') AS ADDRESS3,"
												+" NVL(ADDRESS4,' ') AS ADDRESS4,NVL(CITY,' ') AS CITY,NVL(POSTAL_CODE,' ') AS POSTAL_CODE,"
												+" NVL(STATE,' ') AS STATE,NVL(LAST_UPDATED_BY,0) AS LAST_UPDATED_BY,NVL(CREATED_BY,0) AS CREATED_BY,"
												+" NVL(CONACT_PERSON_NAME,' ') AS CONACT_PERSON_NAME,NVL(CONTACT_PERSON_DESIGNATION,' ') AS CONTACT_PERSON_DESIGNATION,"
												+" NVL(CONTACT_PERSON_MOBILE,' ') AS CONTACT_PERSON_MOBILE,NVL(CONTACT_PERSON_EMAIL,' ') AS CONTACT_PERSON_EMAIL,"
												+" NVL(CONTACT_PERSON_FAX,' ') AS CONTACT_PERSON_FAX,NVL(LST_NUMBER,' ') AS LST_NUMBER,"
												+" LST_DATE,ACTIVE_END_DATE,LAST_UPDATE_DATE,CREATION_DATE,"
												+" NVL(ADDRESS_TYPE,' ') AS ADDRESS_TYPE,NVL(OLD_ADDRESS_ID,0) AS OLD_ADDRESS_ID,"
												+" NVL(CIRCLE_ID,0) AS CIRCLE_ID FROM" 
												+" APPS.IBMOE_ORDER_ADDRESS" 
												+" WHERE" 
												+" ADDRESS_TYPE = 'GENERAL'" 
												+" AND CREATION_DATE BETWEEN TO_DATE(?) AND SYSDATE";

	//	fetching Disptach Address  records from CRM for update in IOMS
	//private static String strGetUpdatedECRMDispatchAddress  = " SELECT * FROM APPS.IBMOE_ORDER_ADDRESS WHERE ADDRESS_TYPE = 'GENERAL' AND LAST_UPDATE_DATE BETWEEN TO_DATE(?) AND SYSDATE";
	private static String strGetUpdatedECRMDispatchAddress  = "SELECT"
												+" NVL(ADDRESS_ID,0) AS ADDRESS_ID,NVL(CUST_ACCOUNT_ID,0) AS CUST_ACCOUNT_ID,"
												+" NVL(COUNTRY,' ') AS COUNTRY,NVL(ADDRESS1,' ') AS ADDRESS1,"
												+" NVL(ADDRESS2,' ') AS ADDRESS2,NVL(ADDRESS3,' ') AS ADDRESS3,"
												+" NVL(ADDRESS4,' ') AS ADDRESS4,NVL(CITY,' ') AS CITY,NVL(POSTAL_CODE,' ') AS POSTAL_CODE,"
												+" NVL(STATE,' ') AS STATE,NVL(LAST_UPDATED_BY,0) AS LAST_UPDATED_BY,NVL(CREATED_BY,0) AS CREATED_BY,"
												+" NVL(CONACT_PERSON_NAME,' ') AS CONACT_PERSON_NAME,NVL(CONTACT_PERSON_DESIGNATION,' ') AS CONTACT_PERSON_DESIGNATION,"
												+" NVL(CONTACT_PERSON_MOBILE,' ') AS CONTACT_PERSON_MOBILE,NVL(CONTACT_PERSON_EMAIL,' ') AS CONTACT_PERSON_EMAIL,"
												+" NVL(CONTACT_PERSON_FAX,' ') AS CONTACT_PERSON_FAX,NVL(LST_NUMBER,' ') AS LST_NUMBER,"
												+" LST_DATE,ACTIVE_END_DATE,LAST_UPDATE_DATE,CREATION_DATE,"
												+" NVL(ADDRESS_TYPE,' ') AS ADDRESS_TYPE,NVL(OLD_ADDRESS_ID,0) AS OLD_ADDRESS_ID,"
												+" NVL(CIRCLE_ID,0) AS CIRCLE_ID FROM" 
												+" APPS.IBMOE_ORDER_ADDRESS" 
												+" WHERE" 
												+" ADDRESS_TYPE = 'GENERAL'" 
												+" AND LAST_UPDATE_DATE BETWEEN TO_DATE(?) AND SYSDATE";

	//	fetching Network Location records from CRM for update in IOMS
	//private static String strGetECRMNetworkLocation  = " SELECT * FROM APPS.IBMOE_POP_LOCATIONS WHERE CREATION_DATE BETWEEN TO_DATE(?) AND SYSDATE";
	private static String strGetECRMNetworkLocation  = "SELECT" 
												+" NVL(POP_LOC_ID,0) AS POP_LOC_ID,NVL(POP_LOC_CODE,' ') AS POP_LOC_CODE,"
												+" NVL(POP_LOC_NAME,' ') AS POP_LOC_NAME,NVL(POP_LOC_ADDRESS,' ') AS POP_LOC_ADDRESS,"
												+" NVL(POP_CITY,' ') AS POP_CITY,NVL(POP_STATE,' ') AS POP_STATE,"
												+" START_DATE,LAST_UPDATE_DATE,END_DATE,CREATION_DATE,"    
												+" NVL(CREATED_BY,0) AS CREATED_BY,NVL(LAST_UPDATE_BY,0) AS LAST_UPDATE_BY"  
												+" FROM" 
												+" APPS.IBMOE_POP_LOCATIONS" 
												+" WHERE" 
												+" CREATION_DATE BETWEEN TO_DATE(?) AND SYSDATE";

	//	fetching Network Location records from CRM for update in IOMS
	//private static String strGetUpdatedECRMNetworkLocation  = " SELECT * FROM APPS.IBMOE_POP_LOCATIONS WHERE LAST_UPDATE_DATE BETWEEN TO_DATE(?) AND SYSDATE";
	private static String strGetUpdatedECRMNetworkLocation  = "SELECT" 
												+" NVL(POP_LOC_ID,0) AS POP_LOC_ID,NVL(POP_LOC_CODE,' ') AS POP_LOC_CODE,"
												+" NVL(POP_LOC_NAME,' ') AS POP_LOC_NAME,NVL(POP_LOC_ADDRESS,' ') AS POP_LOC_ADDRESS,"
												+" NVL(POP_CITY,' ') AS POP_CITY,NVL(POP_STATE,' ') AS POP_STATE,"
												+" START_DATE,LAST_UPDATE_DATE,END_DATE,CREATION_DATE,"    
												+" NVL(CREATED_BY,0) AS CREATED_BY,NVL(LAST_UPDATE_BY,0) AS LAST_UPDATE_BY"  
												+" FROM" 
												+" APPS.IBMOE_POP_LOCATIONS" 
												+" WHERE" 
												+" LAST_UPDATE_DATE BETWEEN TO_DATE(?) AND SYSDATE";

//	fetching productDDValues Information from CRM for update in IOMS
	private static String strGetECRMProductDDValues = "SELECT attribute15 ProductName ,attribute15||'-'||lookup_code||'-'||meaning TEXT ,lookup_code, "+
			" CREATION_DATE , LAST_UPDATE_DATE , ENABLED_FLAG FROM apps.fnd_lookup_values a WHERE LOOKUP_TYPE = 'IBMOE_HARDWARE_PRODUCT' "
			+" AND CREATION_DATE BETWEEN TO_DATE(?) AND SYSDATE";
//lawkush Start

	//	fetching GAM employees from CRM for insert in IOMS

	private static String strGetECRMGAMEmployeeWithRole  ="SELECT DISTINCT "
															    +" jtrr.role_resource_id, 111 AS ROLE_ID ,"
															    +" jre.resource_number, fu.EMPLOYEE_ID, "
															    +" fu.EMAIL_ADDRESS, fu.user_id,fu.user_name,"
															    +" JRE.SOURCE_FIRST_NAME AS first_name, "
																+" JRE.SOURCE_LAST_NAME AS last_name,NVL(JRE.SOURCE_PHONE,' ') AS phone_no ,"
																+" GAM.ENABLED_FLAG ," 
																+" GAM.active_date ,"
																+" GAM.End_date"
																+" FROM "
															    +" apps.jtf_rs_role_relations jtrr,"
																+" apps.jtf_rs_resource_extns jre, "
																+" apps.fnd_user fu ,"
																+" ("
															     +"   SELECT meaning Resource_id ,ENABLED_FLAG,"
															     +"  START_DATE_ACTIVE active_date,	"
															      +"   END_DATE_ACTIVE  End_date	"
															     +"   FROM apps.fnd_lookups "
															     +"    WHERE lookup_type = 'XXIBM_GAM_MASTER' "
															  +"  ) GAM "
															  +"  WHERE jre.resource_id =  jtrr.role_resource_id "
															  +"  AND fu.user_id = jre.user_id 	"				
															  +" AND GAM.Resource_id = ROLE_RESOURCE_ID "
															    +" AND ACTIVE_DATE BETWEEN TO_DATE(?) AND SYSDATE"
													            ;
	private static String strUpdateSingleViewDate="SELECT BILLINGTRIGGERDATE as CIRCUIT_START_DATE,CIRCUIT_CONTRACTPERIOD,ioe.CIRCUIT_LAST_DATE(BILLINGTRIGGERDATE,CIRCUIT_CONTRACTPERIOD) as CIRCUIT_END_DATE,ORDERNO,LOGICAL_SI_NO,SERVICEID FROM ioe.VW_LSI_CIRCUIT_DATE " +
	  "	where orderno not in (SELECT ORDERNO FROM ioe.CIRCUIT_ORDER_INSERTION)";

		  			
	//	fetching GAM employees from CRM for insert in IOMS
	
//lawkush End
	
	
//lawkush Start
	
	//	fetching GAM employees from CRM for update in IOMS

	private static String strUpdateECRMGAMEmployeeWithRole  = "SELECT DISTINCT "
															    +" jtrr.role_resource_id, 111 AS ROLE_ID ,"
															    +" jre.resource_number, fu.EMPLOYEE_ID, "
															    +" fu.EMAIL_ADDRESS, fu.user_id,fu.user_name,"
															    +" JRE.SOURCE_FIRST_NAME AS first_name, "
																+" JRE.SOURCE_LAST_NAME AS last_name,NVL(JRE.SOURCE_PHONE, ' ') AS phone_no ,"
																+" GAM.ENABLED_FLAG ," 
																+" GAM.active_date ,"
																+" GAM.End_date"
																+" FROM "
															    +" apps.jtf_rs_role_relations jtrr,"
																+" apps.jtf_rs_resource_extns jre, "
																+" apps.fnd_user fu ,"
																+" ("
															     +"   SELECT meaning Resource_id ,ENABLED_FLAG,"
															     +"  START_DATE_ACTIVE active_date,	"
															      +"   END_DATE_ACTIVE  End_date	"
															     +"   FROM apps.fnd_lookups "
															     +"    WHERE lookup_type = 'XXIBM_GAM_MASTER' "
															  +"  ) GAM "
															  +"  WHERE jre.resource_id =  jtrr.role_resource_id "
															  +"  AND fu.user_id = jre.user_id 	"				
															  +" AND GAM.Resource_id = ROLE_RESOURCE_ID "
															    +" AND GAM.END_DATE BETWEEN TO_DATE(?) AND SYSDATE"
        ;
        

	//	fetching GAM employees from CRM for update in IOMS
	
//lawkush End
	
	//LAWKUSH START

	//	fetching GAM QUOTES from CRM for insert in GAM IOMS

	private static String strGetWonInsertGAMQuotes =  	"SELECT NVL (account_number, ' ') AS party_id,"
													    +" NVL (asl.lead_number, ' ') AS lead_number,gam.resource_id,gam.name,"
													    +" NVL (aqha.quote_number, 0) AS quote_number, 1 AS status , gam.CREATION_DATE , gam.LAST_UPDATE_DATE"
													    +" FROM apps.hz_parties hz,"
													    +" apps.hz_cust_accounts hca,"
													    +" apps.as_leads_all asl,"
													    +" apps.aso_quote_related_objects aso,"
													    +" apps.aso_quote_headers_all aqha,"
													    +" xxibm.xxibm_gam_master  gam"
													    +" WHERE asl.customer_id = hz.party_id"
													    +" AND hca.party_id = hz.party_id"
													    +" AND aso.object_id = asl.lead_id"
													    +" AND aso.quote_object_id = "
													    +" aqha.quote_header_id"
													    +" and asl.LEAD_NUMBER=gam.opportunity_num(+)"
													    +" AND EXISTS (SELECT 1"
													    +" FROM apps.as_lead_lines_all asll"
													    +"   WHERE asll.lead_id = asl.lead_id AND asll.attribute8 = 1)"
													    +"    and gam.CREATION_DATE BETWEEN TO_DATE(?) And Sysdate"
												

	;
	
	//LAWKUSH END 
	
	

	
	private static String strGetUpdateECRMProductDDValues = "SELECT attribute15 ProductName ,attribute15||'-'||lookup_code||'-'||meaning TEXT ,lookup_code, "+
	" CREATION_DATE , LAST_UPDATE_DATE , ENABLED_FLAG FROM apps.fnd_lookup_values a WHERE LOOKUP_TYPE = 'IBMOE_HARDWARE_PRODUCT' "
	+" AND LAST_UPDATE_DATE BETWEEN TO_DATE(?) AND SYSDATE";

	private static String strGetOpportunityId = "select NVL(asl.lead_number,' ') AS lead_number,  Customer_id from apps.as_leads_all asl "+									 
											 	" WHERE asl.CREATION_DATE BETWEEN TO_DATE(?) AND SYSDATE";
	
	private static String strGetSingleViewData=	" SELECT TPOMASTER.ORDERNO AS CRM_ORDER_ID,TPOSERVICEMASTER.SERVICEID AS SERVICE_LIST_ID, TPOSERVICEMASTER.LOGICAL_SI_NO AS CIRCUIT_ID,TPRODUCTTYPE.PRODUCTNAME AS PRODUCT,TSERVICESUBTYPE.SERVICESUBTYPENAME AS SUBPRODUCT ,TPOSERVICEMASTER.CREATEDDATE AS CIRCUIT_DATE, "+ 
	 " PARTY_ID , TPOMASTER.ACCOUNTID AS CUST_ACCOUNT_ID , TPOSERVICEMASTER.SERVICETYPEID AS IB2B_PRODUCT_ID,CURRENT DATE AS CREATION_DATE,TPOMASTER.ORDERTYPE, "+
	" CASE WHEN (TPOMASTER.SUB_CHANGE_TYPE_ID IN (3,4)) AND ((SELECT COUNT(1)  FROM IOE.TPOSERVICEDETAILS WHERE ISDISCONNECTED=0 AND CHANGE_SERVICEID=TPOSERVICEMASTER.SERVICEID AND PARENT_SERVICEPRODUCTID<>0 )=0)THEN 'D' WHEN (TPOMASTER.SUB_CHANGE_TYPE_ID=6) THEN 'S' WHEN (TPOMASTER.SUB_CHANGE_TYPE_ID=7) THEN 'R' "+
   	" WHEN TPOMASTER.ORDER_CREATED_VIA_SI_TRANSFER=1 AND ORDERTYPE='New' then 'SI' "+
   	" WHEN TPOMASTER.ORDER_CREATED_VIA_CURRENCY_CHANGE=1 AND ORDERTYPE='New' then 'CR' "+
       " WHEN(TPOSERVICEMASTER.SERVICETYPEID=181) THEN COALESCE((SELECT SUBSTR(TEXT,1,1) FROM IOE.TPRODUCTDDVALUES TPRODUCTDDVALUES1 WHERE  TPRODUCTDDVALUES1.ATTMASTERID=299 AND TPRODUCTDDVALUES1.VALUE=(SELECT LABELATTVALUE FROM IOE.TPRODUCTATTVALUE TPRODUCTATTVALUE1 "+
           " WHERE TPOSERVICEMASTER.SERVICEID= TPRODUCTATTVALUE1.SERVICEDETAILID AND TPRODUCTATTVALUE1.LABELATTID= '299' FETCH FIRST ROW ONLY)),'N') "+ 

" when(TPOSERVICEMASTER.SERVICETYPEID  IN (141,321,221)) THEN COALESCE((SELECT SUBSTR(TEXT,1,1) FROM ioe.TPRODUCTLINEATTVALUE TPRODUCTLINEATTVALUE "+
" inner join ioe.TPRODUCTDDVALUES TPRODUCTDDVALUES on TPRODUCTLINEATTVALUE.ATTMASTERID=TPRODUCTDDVALUES.ATTMASTERID and TPRODUCTLINEATTVALUE.ATTVALUE=TPRODUCTDDVALUES.value "+
" inner join ioe.TPOSERVICEDETAILS TPOSERVICEDETAILS on TPOSERVICEDETAILS.SERVICEPRODUCTID=TPRODUCTLINEATTVALUE.SERVICEPRODUCTID and TPRODUCTLINEATTVALUE.ATTMASTERID in (1125,3083,2118) "+
   " and TPOSERVICEMASTER.serviceid=TPOSERVICEDETAILS.SERVICEID "+
           " and TPOSERVICEDETAILS.SERVICEDETAILID in (222,768,367) FETCH FIRST ROW ONLY),'N') ELSE 'N'  END AS FLAG, TPOSERVICEMASTER.SERVICETYPEID "+

    " FROM IOE.TPOSERVICEMASTER TPOSERVICEMASTER "+
    " INNER JOIN IOE.TPOMASTER TPOMASTER  ON TPOSERVICEMASTER.ORDERNO = TPOMASTER.ORDERNO "+
    " INNER JOIN IOE.TM_ACCOUNT TM_ACCOUNT ON TM_ACCOUNT.ACCOUNTID = TPOMASTER.ACCOUNTID  "+
	 " INNER JOIN IOE.TPRODUCTTYPE TPRODUCTTYPE ON TPRODUCTTYPE.PRODUCTTYPEID=TPOSERVICEMASTER.PRODUCTID "+
	 " INNER JOIN IOE.TSERVICESUBTYPE TSERVICESUBTYPE ON TPOSERVICEMASTER.SUBPRODUCTID=TSERVICESUBTYPE.SERVICESUBTYPEID "+	
    " left outer join (select t1.serviceid , 5 as exclude_service from ioe.tposervicemaster t1 "+
    " inner join ioe.TPOSERVICEDETAILS t2 on t1.serviceid = t2.serviceid and t1.CKT_PUSHED_INTO_CRM = 'N' "+
                         " and t2.config_id in (@@crmService_configIdForExternalId@@) "+
                       " and t2.fx_si_id is null) tab on TPOSERVICEMASTER.serviceid = tab.serviceid "+
    " where TPOSERVICEMASTER.CKT_PUSHED_INTO_CRM='N'  and  tab.exclude_service is null " +
	"  and ((TPOSERVICEMASTER.M6_FX_PROGRESS_STATUS in('M6_START','M6_END-FX_BT_START','FX_BT_END','M6_SUCCESS','WAIT_ISP_M6_END','M6_FAILED','W_FX_ACC_FOR_M6','WAIT_L3MPLS_M6_END','WAIT_CC_M6_END') " +
	"  and TPOMASTER.SUB_CHANGE_TYPE_ID not IN (3,4) ) or " +
	" (TPOMASTER.SUB_CHANGE_TYPE_ID IN (3,4)  and TPOSERVICEMASTER.M6_FX_PROGRESS_STATUS='FX_BT_END' ))";


	private static String strGetSingleViewData_LineLevel=" SELECT TPOSERVICEMASTER.SERVICEID,SERDET.SERVICEPRODUCTID,SERDET.SERVICEDETAILID, "+
			 " CASE WHEN (TLOCATION_INFO.PRIMARYLOCATIONTYPE=1) THEN 'Customer Location' "+
			 " WHEN (TLOCATION_INFO.PRIMARYLOCATIONTYPE=2) THEN 'Network Pop Location' End As LOCTYPE_PRIMARY, "+
			 " SUBSTR(BCP_PRIM.ADDRESS1,240) AS ADDRESS1_PRIMARY,SUBSTR(BCP_PRIM.ADDRESS2,240) AS ADDRESS2_PRIMARY,SUBSTR(BCP_PRIM.ADDRESS3,240) AS ADDRESS3_PRIMARY,SUBSTR(BCP_PRIM.ADDRESS4,240) AS ADDRESS4_PRIMARY,CIT_PRIM.CITY_NAME AS CITY_PRIMARY,STAT_PRIM.STATE_NAME AS STATE_PRIMARY,COUN_PRIM.COUNTRY_NAME AS COUNTRY_PRIMARY,BCP_PRIM.PIN AS POSTALCODE_PRIMARY, "+
			 " SUBSTR(TRIM(TRIM(TRIM(COALESCE(NWLOC_PRIM.ADDRESS1,'')||' '||COALESCE(NWLOC_PRIM.ADDRESS2,''))||' '||COALESCE(NWLOC_PRIM.ADDRESS3,''))||' '||COALESCE(NWLOC_PRIM.ADDRESS4,'')),1,240) AS ADDRESS_PRIMARY, "+ 
			 " CASE WHEN (TLOCATION_INFO.SECONDARYLOCATIONTYPE=1) THEN 'Customer Location' "+
			 " WHEN (TLOCATION_INFO.SECONDARYLOCATIONTYPE=2) THEN 'Network Pop Location' End As LOCTYPE_SECONDARY, "+
			 " SUBSTR(BCP_SEC.ADDRESS1,240) AS ADDRESS1_SECONDARY,SUBSTR(BCP_SEC.ADDRESS2,240) AS ADDRESS2_SECONDARY,SUBSTR(BCP_SEC.ADDRESS3,240) AS ADDRESS3_SECONDARY,SUBSTR(BCP_SEC.ADDRESS4,240) AS ADDRESS4_SECONDARY,CIT_SEC.CITY_NAME AS CITY_SECONDARY,STAT_SEC.STATE_NAME AS STATE_SECONDARY,COUN_SEC.COUNTRY_NAME AS COUNTRY_SECONDARY,BCP_SEC.PIN AS POSTALCODE_SECONDARY, "+ 
			 " SUBSTR(TRIM(TRIM(TRIM(COALESCE(NWLOC_SEC.ADDRESS1,'')||' '||COALESCE(NWLOC_SEC.ADDRESS2,''))||' '||COALESCE(NWLOC_SEC.ADDRESS3,''))||' '||COALESCE(NWLOC_SEC.ADDRESS4,'')),1,240) AS ADDRESS_SECONDARY, "+ 
			 " ATTVALUE.ATTVALUE AS BANDWIDTH, DDVALUE.TEXT AS   BANDWIDTH_UOM ,ATTVALUE_UOML3.ATTVALUE AS UOM_L3 ,BAND_DDVALUE.TEXT AS VAST ,TP_NAME_DDVALUE.TEXT AS TP_NAME"+
                 " FROM IOE.TPOSERVICEMASTER TPOSERVICEMASTER "+
                 " INNER JOIN IOE.TPOMASTER TPOMASTER  ON TPOSERVICEMASTER.ORDERNO = TPOMASTER.ORDERNO  "+
                 " INNER JOIN IOE.TDISCONNECTION_HISTORY DISHIS ON DISHIS.MAIN_SERVICEID=TPOSERVICEMASTER.SERVICEID AND TPOSERVICEMASTER.IS_IN_HISTORY=0 "+
                 " INNER JOIN IOE.TPOSERVICEDETAILS SERDET ON SERDET.SERVICEPRODUCTID=DISHIS.SERVICE_PRODUCT_ID AND PARENT_SERVICEPRODUCTID<>0 "+
                 " LEFT JOIN IOE.TPRODUCTLINEATTVALUE ATTVALUE ON SERDET.SERVICEPRODUCTID=ATTVALUE.SERVICEPRODUCTID AND ATTVALUE.ATTMASTERID IN(2114,3085,1127,1426,1232)"+
                 " LEFT JOIN IOE.TPRODUCTLINEATTVALUE BAD_ATTVALUE ON SERDET.SERVICEPRODUCTID=BAD_ATTVALUE.SERVICEPRODUCTID AND BAD_ATTVALUE.ATTMASTERID IN(2748) "+
                 " LEFT JOIN IOE.TPRODUCTLINEATTVALUE ATTVALUE_UOM ON SERDET.SERVICEPRODUCTID=ATTVALUE_UOM.SERVICEPRODUCTID AND ATTVALUE_UOM.ATTMASTERID IN(2115,3084,2762,1231,1427) "+ 
                 " LEFT JOIN IOE.TPRODUCTLINEATTVALUE ATTVALUE_UOML3 ON SERDET.SERVICEPRODUCTID=ATTVALUE_UOML3.SERVICEPRODUCTID AND ATTVALUE_UOML3.ATTMASTERID IN(1126) "+ 
                 " LEFT JOIN IOE.TPRODUCTLINEATTVALUE ATTVALUE_TP_NAME ON SERDET.SERVICEPRODUCTID=ATTVALUE_TP_NAME.SERVICEPRODUCTID AND ATTVALUE_TP_NAME.ATTMASTERID IN (5000567,5000241,5000507,5000400,5000450,5000613) "+
                 " LEFT JOIN IOE.TPRODUCTDDVALUES DDVALUE ON DDVALUE.VALUE=ATTVALUE_UOM.ATTVALUE AND DDVALUE.ATTMASTERID=ATTVALUE_UOM.ATTMASTERID "+
						" and DDVALUE.FLAG='PRODUCT' "+
                 " LEFT JOIN IOE.TPRODUCTDDVALUES BAND_DDVALUE ON BAND_DDVALUE.VALUE=BAD_ATTVALUE.ATTVALUE AND BAND_DDVALUE.ATTMASTERID=BAD_ATTVALUE.ATTMASTERID "+  
	  	" and BAND_DDVALUE.FLAG='PRODUCT' "+
                 " LEFT JOIN IOE.TPRODUCTDDVALUES TP_NAME_DDVALUE ON TP_NAME_DDVALUE.VALUE=ATTVALUE_TP_NAME.ATTVALUE AND TP_NAME_DDVALUE.ATTMASTERID=ATTVALUE_TP_NAME.ATTMASTERID "+  
		" and TP_NAME_DDVALUE.FLAG='PRODUCT' "+
                 " LEFT OUTER JOIN IOE.TLOCATION_INFO TLOCATION_INFO ON SERDET.SERVICEPRODUCTID = TLOCATION_INFO.SERVICEPRODUCTID  "+
                 " LEFT OUTER JOIN IOE.TBCP_ADDRESS_MSTR BCP_PRIM on TLOCATION_INFO.PRIMARYLOCATIONTYPE=1 and TLOCATION_INFO.PRIMARYLOCATIONID = BCP_PRIM.BCP_ID "+ 
                 " LEFT OUTER JOIN IOE.TCITY_MASTER CIT_PRIM ON CIT_PRIM.CITY_ID=BCP_PRIM.CITY_ID "+
                 " LEFT OUTER JOIN IOE.TSTATE_MASTER STAT_PRIM ON STAT_PRIM.STATE_ID=CIT_PRIM.STATE_ID "+
                 " LEFT OUTER JOIN IOE.TCOUNTRY_MASTER COUN_PRIM ON COUN_PRIM.COUNTRY_CODE=STAT_PRIM.COUNTRY_ID "+
                 " LEFT OUTER JOIN IOE.TNETWORK_LOCATION_MSTR NWLOC_PRIM  on  TLOCATION_INFO.PRIMARYLOCATIONTYPE=2 and TLOCATION_INFO.PRIMARYLOCATIONID = NWLOC_PRIM.LOCATION_CODE "+ 
                 " LEFT OUTER JOIN IOE.TBCP_ADDRESS_MSTR BCP_SEC on TLOCATION_INFO.SECONDARYLOCATIONTYPE=1 and TLOCATION_INFO.SECONDARYLOCATIONID = BCP_SEC.BCP_ID "+ 
                 " LEFT OUTER JOIN IOE.TCITY_MASTER CIT_SEC ON CIT_SEC.CITY_ID=BCP_SEC.CITY_ID "+
                 " LEFT OUTER JOIN IOE.TSTATE_MASTER STAT_SEC ON STAT_SEC.STATE_ID=CIT_SEC.STATE_ID "+
                 " LEFT OUTER JOIN IOE.TCOUNTRY_MASTER COUN_SEC ON COUN_SEC.COUNTRY_CODE=STAT_SEC.COUNTRY_ID "+
                 " LEFT OUTER JOIN IOE.TNETWORK_LOCATION_MSTR NWLOC_SEC on TLOCATION_INFO.SECONDARYLOCATIONTYPE=2 and TLOCATION_INFO.SECONDARYLOCATIONID = NWLOC_SEC.LOCATION_CODE "+ 
                 " INNER JOIN IOE.TPRODUCTTYPE TPRODUCTTYPE ON TPRODUCTTYPE.PRODUCTTYPEID=TPOSERVICEMASTER.PRODUCTID  "+
				   " INNER JOIN IOE.TSERVICESUBTYPE TSERVICESUBTYPE ON TPOSERVICEMASTER.SUBPRODUCTID=TSERVICESUBTYPE.SERVICESUBTYPEID "+ 	
                 " left outer join (select t1.serviceid , 5 as exclude_service from ioe.tposervicemaster t1 "+  
                                    " inner join ioe.TPOSERVICEDETAILS t2 on t1.serviceid = t2.serviceid "+ 
                                  "  and t1.CKT_PUSHED_INTO_CRM = 'N' "+
                                    " and t2.config_id in (@@crmService_configIdForExternalId@@) "+
                                     " and t2.fx_si_id is null) tab on TPOSERVICEMASTER.serviceid = tab.serviceid "+ 
                  " where tab.exclude_service is null  "+
               	" AND TPOSERVICEMASTER.CKT_PUSHED_INTO_CRM='N'   "+
               	"  and ((TPOSERVICEMASTER.M6_FX_PROGRESS_STATUS in('M6_START','M6_END-FX_BT_START','FX_BT_END','M6_SUCCESS','WAIT_ISP_M6_END','M6_FAILED','W_FX_ACC_FOR_M6','WAIT_L3MPLS_M6_END','WAIT_CC_M6_END') " +
               	"  and TPOMASTER.SUB_CHANGE_TYPE_ID not IN (3,4) ) or " +
               	" (TPOMASTER.SUB_CHANGE_TYPE_ID IN (3,4)  and TPOSERVICEMASTER.M6_FX_PROGRESS_STATUS='FX_BT_END' ))  " +
               	" AND SERDET.SERVICEDETAILID IN (367,768,667,25,304,222,100070,100068,100115,100093,100095,100072)  "+



               	



" union "+


		  	 " SELECT TPOSERVICEMASTER.SERVICEID,SERDET.SERVICEPRODUCTID,SERDET.SERVICEDETAILID, "+
			 " CASE WHEN (TLOCATION_INFO.PRIMARYLOCATIONTYPE=1) THEN 'Customer Location' "+
			 " WHEN (TLOCATION_INFO.PRIMARYLOCATIONTYPE=2) THEN 'Network Pop Location' End As LOCTYPE_PRIMARY, "+
			 " SUBSTR(BCP_PRIM.ADDRESS1,240) AS ADDRESS1_PRIMARY,SUBSTR(BCP_PRIM.ADDRESS2,240) AS ADDRESS2_PRIMARY,SUBSTR(BCP_PRIM.ADDRESS3,240) AS ADDRESS3_PRIMARY,SUBSTR(BCP_PRIM.ADDRESS4,240) AS ADDRESS4_PRIMARY,CIT_PRIM.CITY_NAME AS CITY_PRIMARY,STAT_PRIM.STATE_NAME AS STATE_PRIMARY,COUN_PRIM.COUNTRY_NAME AS COUNTRY_PRIMARY,BCP_PRIM.PIN AS POSTALCODE_PRIMARY, "+
			 " SUBSTR(TRIM(TRIM(TRIM(COALESCE(NWLOC_PRIM.ADDRESS1,'')||' '||COALESCE(NWLOC_PRIM.ADDRESS2,''))||' '||COALESCE(NWLOC_PRIM.ADDRESS3,''))||' '||COALESCE(NWLOC_PRIM.ADDRESS4,'')),1,240) AS ADDRESS_PRIMARY, "+ 
			 " CASE WHEN (TLOCATION_INFO.SECONDARYLOCATIONTYPE=1) THEN 'Customer Location' "+
			 " WHEN (TLOCATION_INFO.SECONDARYLOCATIONTYPE=2) THEN 'Network Pop Location' End As LOCTYPE_SECONDARY, "+
			 " SUBSTR(BCP_SEC.ADDRESS1,240) AS ADDRESS1_SECONDARY,SUBSTR(BCP_SEC.ADDRESS2,240) AS ADDRESS2_SECONDARY,SUBSTR(BCP_SEC.ADDRESS3,240) AS ADDRESS3_SECONDARY,SUBSTR(BCP_SEC.ADDRESS4,240) AS ADDRESS4_SECONDARY,CIT_SEC.CITY_NAME AS CITY_SECONDARY,STAT_SEC.STATE_NAME AS STATE_SECONDARY,COUN_SEC.COUNTRY_NAME AS COUNTRY_SECONDARY,BCP_SEC.PIN AS POSTALCODE_SECONDARY, "+ 
			 " SUBSTR(TRIM(TRIM(TRIM(COALESCE(NWLOC_SEC.ADDRESS1,'')||' '||COALESCE(NWLOC_SEC.ADDRESS2,''))||' '||COALESCE(NWLOC_SEC.ADDRESS3,''))||' '||COALESCE(NWLOC_SEC.ADDRESS4,'')),1,240) AS ADDRESS_SECONDARY, "+ 
			 " ATTVALUE.ATTVALUE AS BANDWIDTH, DDVALUE.TEXT AS   BANDWIDTH_UOM ,ATTVALUE_UOML3.ATTVALUE AS UOM_L3 ,BAND_DDVALUE.TEXT AS VAST,TP_NAME_DDVALUE.TEXT  AS TP_NAME "+
                 " FROM IOE.TPOSERVICEMASTER TPOSERVICEMASTER "+
		                                           " INNER JOIN IOE.TPOMASTER TPOMASTER  ON TPOSERVICEMASTER.ORDERNO = TPOMASTER.ORDERNO  "+
		                                           " INNER JOIN IOE.TDISCONNECTION_HISTORY DISHIS ON DISHIS.MAIN_SERVICEID=TPOSERVICEMASTER.SERVICEID AND TPOSERVICEMASTER.IS_IN_HISTORY=1 "+
		                                           " INNER JOIN IOE.TPOSERVICEDETAILS_HISTORY SERDET ON SERDET.SERVICEPRODUCTID=DISHIS.SERVICE_PRODUCT_ID AND PARENT_SERVICEPRODUCTID<>0 "+
														" and SERDET.MAIN_SERVICEID=DISHIS.MAIN_SERVICEID "+
		                                           " LEFT JOIN IOE.TPRODUCTLINEATTVALUE ATTVALUE ON SERDET.SERVICEPRODUCTID=ATTVALUE.SERVICEPRODUCTID AND ATTVALUE.ATTMASTERID IN(2114,3085,1127,1426,1232) "+
                                                 " LEFT JOIN IOE.TPRODUCTLINEATTVALUE BAD_ATTVALUE ON SERDET.SERVICEPRODUCTID=BAD_ATTVALUE.SERVICEPRODUCTID AND BAD_ATTVALUE.ATTMASTERID IN(2748) "+
		                                           " LEFT JOIN IOE.TPRODUCTLINEATTVALUE ATTVALUE_UOM ON SERDET.SERVICEPRODUCTID=ATTVALUE_UOM.SERVICEPRODUCTID AND ATTVALUE_UOM.ATTMASTERID IN(2115,3084,2762,1231,1427) "+
		                                           " LEFT JOIN IOE.TPRODUCTLINEATTVALUE ATTVALUE_UOML3 ON SERDET.SERVICEPRODUCTID=ATTVALUE_UOML3.SERVICEPRODUCTID AND ATTVALUE_UOML3.ATTMASTERID IN(1126) "+ 
		                                           " LEFT JOIN IOE.TPRODUCTLINEATTVALUE ATTVALUE_TP_NAME ON SERDET.SERVICEPRODUCTID=ATTVALUE_TP_NAME.SERVICEPRODUCTID AND ATTVALUE_TP_NAME.ATTMASTERID IN(5000567,5000241,5000507,5000400,5000450,5000613) "+ 
		                                           " LEFT JOIN IOE.TPRODUCTDDVALUES DDVALUE ON DDVALUE.VALUE=ATTVALUE_UOM.ATTVALUE AND DDVALUE.ATTMASTERID=ATTVALUE_UOM.ATTMASTERID "+
														" and DDVALUE.FLAG='PRODUCT' "+
                                                 " LEFT JOIN IOE.TPRODUCTDDVALUES BAND_DDVALUE ON BAND_DDVALUE.VALUE=BAD_ATTVALUE.ATTVALUE AND BAND_DDVALUE.ATTMASTERID=BAD_ATTVALUE.ATTMASTERID "+  
					  		" and BAND_DDVALUE.FLAG='PRODUCT' "+
                                                 " LEFT JOIN IOE.TPRODUCTDDVALUES TP_NAME_DDVALUE ON TP_NAME_DDVALUE.VALUE=ATTVALUE_TP_NAME.ATTVALUE AND TP_NAME_DDVALUE.ATTMASTERID=ATTVALUE_TP_NAME.ATTMASTERID "+  
		                                           	" and TP_NAME_DDVALUE.FLAG='PRODUCT' "+
													
		                                           " LEFT OUTER JOIN IOE.TLOCATION_INFO_HISTORY TLOCATION_INFO ON SERDET.SERVICEPRODUCTID = TLOCATION_INFO.SERVICEPRODUCTID  "+
														" and TLOCATION_INFO.MAIN_SERVICE_ID=DISHIS.MAIN_SERVICEID "+
		                                           " LEFT OUTER JOIN IOE.TBCP_ADDRESS_MSTR BCP_PRIM on TLOCATION_INFO.PRIMARYLOCATIONTYPE=1 and TLOCATION_INFO.PRIMARYLOCATIONID = BCP_PRIM.BCP_ID "+ 
		                                           " LEFT OUTER JOIN IOE.TCITY_MASTER CIT_PRIM ON CIT_PRIM.CITY_ID=BCP_PRIM.CITY_ID "+
		                                           " LEFT OUTER JOIN IOE.TSTATE_MASTER STAT_PRIM ON STAT_PRIM.STATE_ID=CIT_PRIM.STATE_ID "+
		                                           " LEFT OUTER JOIN IOE.TCOUNTRY_MASTER COUN_PRIM ON COUN_PRIM.COUNTRY_CODE=STAT_PRIM.COUNTRY_ID  "+
		                                           " LEFT OUTER JOIN IOE.TNETWORK_LOCATION_MSTR NWLOC_PRIM  on  TLOCATION_INFO.PRIMARYLOCATIONTYPE=2 and TLOCATION_INFO.PRIMARYLOCATIONID = NWLOC_PRIM.LOCATION_CODE "+ 
		                                           " LEFT OUTER JOIN IOE.TBCP_ADDRESS_MSTR BCP_SEC on TLOCATION_INFO.SECONDARYLOCATIONTYPE=1 and TLOCATION_INFO.SECONDARYLOCATIONID = BCP_SEC.BCP_ID "+ 
		                                           " LEFT OUTER JOIN IOE.TCITY_MASTER CIT_SEC ON CIT_SEC.CITY_ID=BCP_SEC.CITY_ID "+
		                                           " LEFT OUTER JOIN IOE.TSTATE_MASTER STAT_SEC ON STAT_SEC.STATE_ID=CIT_SEC.STATE_ID "+
		                                           " LEFT OUTER JOIN IOE.TCOUNTRY_MASTER COUN_SEC ON COUN_SEC.COUNTRY_CODE=STAT_SEC.COUNTRY_ID "+
		                                           " LEFT OUTER JOIN IOE.TNETWORK_LOCATION_MSTR NWLOC_SEC on TLOCATION_INFO.SECONDARYLOCATIONTYPE=2 and TLOCATION_INFO.SECONDARYLOCATIONID = NWLOC_SEC.LOCATION_CODE "+ 
		                                           " INNER JOIN IOE.TPRODUCTTYPE TPRODUCTTYPE ON TPRODUCTTYPE.PRODUCTTYPEID=TPOSERVICEMASTER.PRODUCTID  "+
												   " INNER JOIN IOE.TSERVICESUBTYPE TSERVICESUBTYPE ON TPOSERVICEMASTER.SUBPRODUCTID=TSERVICESUBTYPE.SERVICESUBTYPEID "+ 	
		                                           " left outer join (select t1.serviceid , 5 as exclude_service from ioe.tposervicemaster t1 "+  
		                                                              " inner join ioe.TPOSERVICEDETAILS t2 on t1.serviceid = t2.serviceid "+ 
		                                                             " and t1.CKT_PUSHED_INTO_CRM = 'N' "+
		                                                               " and ((t2.config_id between 1 and 6) or (t2.config_id between 101 and 106) ) "+ 
		                                                             " and t2.fx_si_id is null) tab on TPOSERVICEMASTER.serviceid = tab.serviceid "+ 
		                                           " where TPOSERVICEMASTER.CKT_PUSHED_INTO_CRM='N'  and  tab.exclude_service is null "+
		                                           "  and ((TPOSERVICEMASTER.M6_FX_PROGRESS_STATUS in('M6_START','M6_END-FX_BT_START','FX_BT_END','M6_SUCCESS','WAIT_ISP_M6_END','M6_FAILED','W_FX_ACC_FOR_M6','WAIT_L3MPLS_M6_END','WAIT_CC_M6_END') " +
												   "  and TPOMASTER.SUB_CHANGE_TYPE_ID not IN (3,4) ) or " +
												   " (TPOMASTER.SUB_CHANGE_TYPE_ID IN (3,4)  and TPOSERVICEMASTER.M6_FX_PROGRESS_STATUS='FX_BT_END' ))  " +
					                                         	  " AND SERDET.SERVICEDETAILID IN (367,768,667,25,304,222,100070,100068,100115,100093,100095,100072) ";
		                                         	  


	//+ " and date(TM6_RESPONSE_HISTORY.CREATED_DATE) BETWEEN DATE(?) and CURRENT DATE fetch first 5 rows only ";
	
//	Nagarjuna Added for UPDATING Levels Data from ECRM to IOMS AccountRoleDetails
//nagarjuna end
//	 Added by Kalpana for UPDATING Levels Data from ECRM to IOMS AccountRoleDetails and also change active/inactive
	private static String strGetECRMEmployeeUpdatedQry =" SELECT NVL(jtrr.role_resource_id,0) AS role_resource_id ,  CASE  WHEN jtrr.role_id = 10081 THEN 1 "
		+" WHEN jtrr.role_id = 10114 THEN 2 ELSE NVL(jtrr.role_id,0) END AS ROLE_ID ,"
		+"  NVL(main.resource_number,' ') AS resource_number ,  NVL(fu.user_id,0) AS user_id,  NVL(fu.user_name,' ') AS user_name,"
	    +"(SELECT ROLE_NAME FROM apps.jtf_rs_roles_tl WHERE role_id = jtrr.role_id ) ROLE_NAME ,"
	    +"  CASE WHEN NVL(jtrr.END_DATE_ACTIVE,SYSDATE+1) > SYSDATE THEN '1' ELSE '0'  END role_status,"
	   +"  NVL((SELECT '1'  FROM apps.jtf_rs_resource_extns m,    apps.fnd_user fu  WHERE m.resource_id  = main.resource_id"
		+" AND NVL(m.END_DATE_ACTIVE,SYSDATE+1) > SYSDATE AND m.user_id =fu.user_id AND NVL(fu.END_DATE,SYSDATE+1) > SYSDATE),'0') active_status,"
		+" NVL(main.SOURCE_FIRST_NAME,' ') AS first_name, NVL(main.SOURCE_LAST_NAME,' ')  AS SOURCE_LAST_NAME , NVL(main.SOURCE_PHONE,' ')  AS phone_no ,"
		+" NVL(fu.EMAIL_ADDRESS,' ')       AS EMAIL_ADDRESS,"
		+" NVL((SELECT jtrr1.role_id FROM apps.jtf_rs_role_relations jtrr1"
		+" WHERE jtrr1.role_resource_id =(SELECT resource_id FROM apps.jtf_rs_resource_extns p WHERE LEVEL = 2"
		+" CONNECT BY NOCYCLE PRIOR TO_NUMBER (p.attribute8) = p.user_id"
		+" START WITH p.resource_id= main.resource_id"
		+" )  AND (END_DATE_ACTIVE IS NULL OR END_DATE_ACTIVE> sysdate) AND rownum=1),0) AS level1_role_id ,"
		+"   NVL((SELECT resource_id FROM apps.jtf_rs_resource_extns p WHERE LEVEL = 2"
		+"    CONNECT BY NOCYCLE PRIOR TO_NUMBER (p.attribute8) = p.user_id"
		+"    START WITH p.resource_id = main.resource_id ),0) AS level1_resource_id,"
		+"  NVL((SELECT RESOURCE_NUMBER FROM apps.jtf_rs_resource_extns p WHERE LEVEL = 2"
		+"    CONNECT BY NOCYCLE PRIOR TO_NUMBER (p.attribute8) = p.user_id"
		+"    START WITH p.resource_id  = main.resource_id ),' ') AS level1_resource_Number,"
		+"  NVL((SELECT source_FIRST_name FROM apps.jtf_rs_resource_extns p"
		+"	WHERE LEVEL= 2 CONNECT BY NOCYCLE PRIOR TO_NUMBER (p.attribute8) = p.user_id"
		+"    START WITH p.resource_id = main.resource_id) ,' ')AS LEVEL1_source_FIRST_name,"
		+"  NVL((SELECT SOURCE_LAST_NAME FROM apps.jtf_rs_resource_extns p WHERE LEVEL = 2"
		+"    CONNECT BY NOCYCLE PRIOR TO_NUMBER (p.attribute8) = p.user_id"
		+"    START WITH p.resource_id= main.resource_id ),' ') AS LEVEL1_source_LAST_name,"
		+"  NVL((SELECT user_name FROM apps.jtf_rs_resource_extns p WHERE LEVEL = 2"
		+"    CONNECT BY NOCYCLE PRIOR TO_NUMBER (p.attribute8) = p.user_id"
		+"    START WITH p.resource_id = main.resource_id),' ') AS level1_user_name,"
		+"  NVL((SELECT fu.user_id FROM apps.jtf_rs_resource_extns p WHERE LEVEL= 2"
		+"    CONNECT BY NOCYCLE PRIOR TO_NUMBER (p.attribute8) = p.user_id "
		+"    START WITH p.resource_id = main.resource_id ),0) AS level1_user_id,"
		+"  NVL((SELECT source_phone FROM apps.jtf_rs_resource_extns p WHERE LEVEL= 2"
		+"    CONNECT BY NOCYCLE PRIOR TO_NUMBER (p.attribute8) = p.user_id"
		+"    START WITH p.resource_id= main.resource_id ),' ') AS level1_resource_phone,"
		+"  NVL((SELECT source_email FROM apps.jtf_rs_resource_extns p WHERE LEVEL = 2 "
		+"    CONNECT BY NOCYCLE PRIOR TO_NUMBER (p.attribute8) = p.user_id"
		+"    START WITH p.resource_id= main.resource_id ),' ') AS level1_resource_email"
		+" FROM apps.jtf_rs_resource_extns main,"
		+"  apps.jtf_rs_role_relations jtrr,"
		+"  apps.fnd_user fu,"
		+"  apps.jtf_rs_roles_tl jrt"
		+" WHERE main.resource_id = jtrr.role_resource_id"
		+" AND jtrr.role_id       = jrt.role_id"
		+" AND fu.user_id         = main.user_id"
		+" AND (main.LAST_UPDATE_DATE between TO_DATE(?) and sysdate or "
		+"	fu.LAST_UPDATE_DATE between TO_DATE(?) and sysdate)";
		
		
		
		
	//end
	Calendar cal;

	Calendar c;

	Date date;

	Timestamp timeStamp;

	String finalTransactionId;
	
	public static Hashtable<String, String> htCRMLastUpdatedValue = new Hashtable<String, String>();
	public static Hashtable<String, String> htCRMLastInsertedValue = new Hashtable<String, String>();
	
	
	public TransactionBatch() {
		cal = Calendar.getInstance();
		c = Calendar.getInstance();
		date = cal.getTime();
		timeStamp = new Timestamp(System.currentTimeMillis());
	}
	
	/*   	********************************************************************************
	 *		Function Name:- InsertECRMBCPAddressInIOMS
	 *		Created By:-    Sumit Arora
	 * 		Created On:-    14-Feb-2011
	 * 		Purpose:-		To Get All the BCP Address From CRM and Insrt them in IOMS
	 *      ********************************************************************************
	 */ 
	
	public static void getLatestDateForTable(Hashtable<String, String> htCRMLastInsertedValue,Hashtable<String, String> htCRMLastUpdatedValue)
	{
		ResultSet rset = null;
		Connection iomsConn = null;
		CallableStatement csIOMS = null;
		try {			
			logger.info("Connect with IOMS database ==>");
			iomsConn = getConnectionObject();
			csIOMS=iomsConn.prepareCall("SELECT IOE.FORMAT_DATE_REPORT1(INSERTDATE) AS INSERTDATE , IOE.FORMAT_DATE_REPORT1(UPDATEDATE) AS UPDATEDATE ,TABLENAME FROM IOE.TTRACKINSERTDATE");
			rset=csIOMS.executeQuery();
			System.out.println("fetching Ttrack insert update date from IOMS history table into hashmap");
			while(rset.next())
			{
				htCRMLastUpdatedValue.put(rset.getString("TABLENAME"), rset.getString("UPDATEDATE"));
				htCRMLastInsertedValue.put(rset.getString("TABLENAME"), rset.getString("INSERTDATE"));
			}
		} catch (Exception e) {
			System.out.println("Error in method getLatestDateForTable() "+ e.getStackTrace());
			e.printStackTrace();
		} finally {
			try {
				DbConnection.closeResultset(rset);
				DbConnection.closeCallableStatement(csIOMS);
				DbConnection.freeConnection(iomsConn);
			} catch (Exception e) {
				System.out.println("exeption due to : " + e.getMessage());
			}
		}
		 
	}

	public static String getFlagForSchedular()
	{
		ResultSet rset = null;
		String flag = "";
		Connection iomsConn = null;
		CallableStatement csIOMS = null;
		try {
			System.out.println("Connect with IOMS database For ECRM Flag==>");
			iomsConn = getConnectionObject();
			csIOMS=iomsConn.prepareCall("SELECT KEYVALUE FROM IOE.TM_APPCONFIG WHERE KEYNAME = 'ECRMFLAG'");
			rset=csIOMS.executeQuery();
			while(rset.next()){
				flag = rset.getString("KEYVALUE");
			}
			System.out.println("Flag Value Fetched is --> "+ flag);
		} catch (Exception e) {
			System.out.println("Error in method getFlagForSchedular() "+ e.getStackTrace());
			e.printStackTrace();
		} finally {
			try {
				DbConnection.closeResultset(rset);
				DbConnection.closeCallableStatement(csIOMS);
				DbConnection.freeConnection(iomsConn);
			} catch (Exception e) {
				System.out.println("exeption due to : " + e.getMessage());
			}
		}
		return flag;
		 
	}
	/*   	********************************************************************************
	 *		Function Name:- InsertECRMBCPAddressInIOMS
	 *		Created By:-    Sumit Arora
	 * 		Created On:-    17-Feb-2011
	 * 		Purpose:-		To Get All the BCP Address From CRM and Insrt them in IOMS
	 * 		Parameters:-	
	 * 						1.  htCRMValue - 	For Getting the Date From Which Data is to be fetched from CRM 
	 * 						2.  insert_update -	If insert_update is '1' Insert is to be made and if '2' than update is to be made
	 *      ********************************************************************************
	 */ 

	public static void InsertECRMBCPAddressInIOMS(Hashtable<String, String> htCRMValue,int insert_update)
	{
				PreparedStatement pstmt = null;
				ResultSet rset = null;
				DBConnectionRetriever con = new DBConnectionRetriever();
				Connection crmcon = null;
				Connection iomsConn = null;
				CallableStatement csIOMS = null;
				try {
					crmcon = con.getCRMConnection();
					if(insert_update ==1)
						pstmt = crmcon.prepareStatement(strGetECRMBCPAddress + " AND CREATION_DATE BETWEEN TO_DATE(?) AND SYSDATE");
					else
						pstmt = crmcon.prepareStatement(strGetECRMBCPAddress +" AND LAST_UPDATE_DATE BETWEEN TO_DATE(?) AND SYSDATE");
					
					pstmt.setString(1, htCRMValue.get(AppConstants.BCP_ADDRESS).toString());
					rset = pstmt.executeQuery();
				
					ArrayList<ECRMBCPAddressDto> lstBcpAddress = new ArrayList<ECRMBCPAddressDto>();
					ECRMBCPAddressDto objDto = null; 

					while (rset.next()) 
					{
						objDto = new ECRMBCPAddressDto();						
						objDto.setAddressId(rset.getLong("ADDRESS_ID"));
						objDto.setCustAccountId(rset.getLong("CUST_ACCOUNT_ID"));
						objDto.setCountryId(rset.getLong("COUNTRY"));						
						objDto.setAddress1(rset.getString("ADDRESS1"));												
						objDto.setAddress2(rset.getString("ADDRESS2"));												
						objDto.setAddress3(rset.getString("ADDRESS3"));												
						objDto.setAddress4(rset.getString("ADDRESS4"));													
						objDto.setCityId(rset.getLong("CITY"));
						objDto.setPostalCode(rset.getString("POSTAL_CODE"));
						objDto.setStateId(rset.getLong("STATE"));
						objDto.setUpdatedDate(rset.getString("LAST_UPDATE_DATE"));
						objDto.setCreatedDate(rset.getString("CREATION_DATE"));
						objDto.setContactPersonName(rset.getString("CONACT_PERSON_NAME"));
						objDto.setContactPersonMobile(rset.getString("CONTACT_PERSON_MOBILE"));
						objDto.setContactPersonEmail(rset.getString("CONTACT_PERSON_EMAIL"));
						objDto.setContactPersonFax(rset.getString("CONTACT_PERSON_FAX"));
						objDto.setLstNo(rset.getString("LST_NUMBER"));
						objDto.setLstDate(rset.getString("LST_DATE"));
						objDto.setDesignation(rset.getString("CONTACT_PERSON_DESIGNATION"));
						objDto.setRevCircle(rset.getLong("CIRCLE_ID"));
						
						lstBcpAddress.add(objDto);
						
					}
					
					CRMLogger.SysErr("Total BCP Fetched:"+lstBcpAddress.size());
					if(lstBcpAddress.size()>0)
					{
						CRMLogger.SysErr("BCP Address Fetched And Stored In ArrayList");
						iomsConn = getConnectionObject();
						CRMLogger.SysErr("Connecting To iB2B Database");
						csIOMS = iomsConn.prepareCall(spInsertECRMBCPAddressToIOMS);
						
						/*If isAllRecordInserted flag contain 'false' value that means some records have not inserted, 
						 * and in this way this method 'UpdateIOMSDataTracker' should not be call
						 */
						for (Iterator<ECRMBCPAddressDto> iter = lstBcpAddress.iterator(); iter.hasNext();) 
						{
							try {
								iomsConn.setAutoCommit(false);
								int iColCount = 0;
								
								ECRMBCPAddressDto element = (ECRMBCPAddressDto) iter.next();
								csIOMS.setLong(++iColCount ,element.getAddressId() );
								csIOMS.setLong(++iColCount ,element.getCustAccountId());
								csIOMS.setLong(++iColCount ,element.getCountryId());
								csIOMS.setString(++iColCount ,element.getAddress1());
								csIOMS.setString(++iColCount ,element.getAddress2());
								csIOMS.setString(++iColCount ,element.getAddress3());
								csIOMS.setString(++iColCount ,element.getAddress4());
								csIOMS.setLong(++iColCount ,element.getCityId());
								csIOMS.setString(++iColCount ,element.getPostalCode());
								csIOMS.setLong(++iColCount ,element.getStateId());
								if(element.getUpdatedDate()==null)
									csIOMS.setTimestamp(++iColCount ,  null);
								else
									csIOMS.setTimestamp(++iColCount ,  AppUtility.stringToTimeStamp(element.getUpdatedDate()));
								
								if(element.getCreatedDate()==null)
									csIOMS.setTimestamp(++iColCount ,  null);
								else
									csIOMS.setTimestamp(++iColCount ,AppUtility.stringToTimeStamp(element.getCreatedDate()));
								
								csIOMS.setString(++iColCount ,element.getContactPersonName());
								csIOMS.setString(++iColCount ,element.getContactPersonMobile());
								csIOMS.setString(++iColCount ,element.getContactPersonEmail());
								csIOMS.setString(++iColCount ,element.getContactPersonFax());
								csIOMS.setString(++iColCount ,element.getLstNo());
								if(element.getLstDate()==null)
									csIOMS.setTimestamp(++iColCount ,null);
								else
									csIOMS.setTimestamp(++iColCount ,AppUtility.stringToTimeStamp(element.getLstDate()));
								csIOMS.setLong(++iColCount ,insert_update);
								csIOMS.setLong(++iColCount ,0);
								csIOMS.setString(++iColCount ,"");
								csIOMS.setString(++iColCount ,"");
								csIOMS.setString(++iColCount ,"");
								csIOMS.setString(++iColCount ,element.getDesignation());
								csIOMS.setLong(++iColCount ,element.getRevCircle());
	
	
								csIOMS.execute();
								CRMLogger.SysErr(csIOMS.getString(22));
								
								/*--Going to commit the transaction if no error found other wise transaction would be rollback--*/
								if (csIOMS.getInt(20) == 0) {
									iomsConn.commit();
								}else{
									iomsConn.rollback();
									CRMLogger.SysErr("Error Inserting BCP Address In IOMS Database:SQLCODE:"+csIOMS.getInt(20));
									CRMLogger.SysErr(csIOMS.getString(23));
								}
							
							}catch(Exception ex){
								iomsConn.rollback();
								CRMLogger.SysErr("Exception occur during inserting the record in method InsertECRMBCPAddressInIOMS()"
										+ ex.getStackTrace());
							}
							
						}
						SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");
						if(insert_update==1)
						{
							UpdateIOMSDataTracker("insert",AppConstants.BCP_ADDRESS,sdf.format(new Date()),sdf.format(new Date()));
							CRMLogger.SysErr("BCP Address Inserted In Ib2b Database and Date tracker updated sucessfully");
						}
						else
						{
							UpdateIOMSDataTracker("update",AppConstants.BCP_ADDRESS,sdf.format(new Date()),sdf.format(new Date()));
							CRMLogger.SysErr("BCP Address Update In Ib2b Database and Date tracker updated sucessfully");
						}
						} else {
							CRMLogger.SysErr("Some BCP Addresses have not inserted properly so Date tracker not updated");
						}
						
				} catch (Exception e) {
					CRMLogger.SysErr("Error in method InsertECRMBCPAddressInIOMS():"
							+ e.getStackTrace());
					e.printStackTrace();
				} finally {
					try {
						DbConnection.closeResultset(rset);
						DbConnection.closePreparedStatement(pstmt);
						DbConnection.closeCallableStatement(csIOMS);
						DbConnection.freeConnection(iomsConn);
						DbConnection.freeConnection(crmcon);
					} catch (Exception e) {
						CRMLogger.SysErr("exeption due to : " + e.getMessage());
					}
				}
	}

		/*   	********************************************************************************
	 *		Function Name:- InsertECRMDisptachAddressInIOMS
	 *		Created By:-    Sumit Arora
	 * 		Created On:-    14-Feb-2011
	 * 		Purpose:-		To Get All the BCP Address From CRM and Insrt them in IOMS
	 * 		Parameters:-	
	 * 						1.  htCRMValue - 	For Getting the Date From Which Data is to be fetched from CRM 
	 * 						2.  insert_update -	If insert_update is '1' Insert is to be made and if '2' than update is to be made
	 *      ********************************************************************************
	 */ 

	public static void InsertECRMDisptachAddressInIOMS(Hashtable<String, String> htCRMValue,int insert_update)
	{
				PreparedStatement pstmt = null;
				ResultSet rset = null;
				DBConnectionRetriever con = new DBConnectionRetriever();
				Connection crmcon = null;
				Connection iomsConn = null;
				CallableStatement csIOMS = null;
				try {
					crmcon = con.getCRMConnection();
					if(insert_update==1)
					{
						pstmt = crmcon.prepareStatement(strGetECRMDispatchAddress);
					}
					else
					{
						pstmt = crmcon.prepareStatement(strGetUpdatedECRMDispatchAddress);
						
					}
					pstmt.setString(1, htCRMValue.get(AppConstants.DISPATCH_ADDRESS).toString());
					rset = pstmt.executeQuery();
					ArrayList<ECRMBCPAddressDto> lstDispatchAddress = new ArrayList<ECRMBCPAddressDto>();
					ECRMBCPAddressDto objDto = null; 
					
					System.err.println("****************** Total No of Dispatch Fetched ()" + String.valueOf(rset.getFetchSize()) + "***************");
					while (rset.next()) 
					{
						objDto = new ECRMBCPAddressDto();
						objDto.setAddressId(rset.getLong("ADDRESS_ID"));
						objDto.setCustAccountId(rset.getLong("CUST_ACCOUNT_ID"));
						objDto.setCountryId(rset.getLong("COUNTRY"));
						objDto.setAddress1(rset.getString("ADDRESS1"));
						objDto.setAddress2(rset.getString("ADDRESS2"));
						objDto.setAddress3(rset.getString("ADDRESS3"));
						objDto.setAddress4(rset.getString("ADDRESS4"));
						objDto.setCityId(rset.getLong("CITY"));
						objDto.setPostalCode(rset.getString("POSTAL_CODE"));
						objDto.setStateId(rset.getLong("STATE"));
						objDto.setUpdatedDate(rset.getString("LAST_UPDATE_DATE"));
						objDto.setCreatedDate(rset.getString("CREATION_DATE"));
						objDto.setContactPersonName(rset.getString("CONACT_PERSON_NAME"));
						objDto.setContactPersonMobile(rset.getString("CONTACT_PERSON_MOBILE"));
						objDto.setContactPersonEmail(rset.getString("CONTACT_PERSON_EMAIL"));
						objDto.setContactPersonFax(rset.getString("CONTACT_PERSON_FAX"));
						objDto.setLstNo(rset.getString("LST_NUMBER"));
						objDto.setLstDate(rset.getString("LST_DATE"));
						objDto.setDesignation(rset.getString("CONTACT_PERSON_DESIGNATION"));
						lstDispatchAddress.add(objDto);
						
					}
					
					CRMLogger.SysErr("Total Dispatch Fetched:"+lstDispatchAddress.size());
					if(lstDispatchAddress.size()==0)
					{
						CRMLogger.SysErr("------------NO DATA FOUND-----------------");
					}
					
					if(lstDispatchAddress.size()>0)
					{
						CRMLogger.SysErr("Dispatch Address Fetched And Stored In ArrayList");
						iomsConn = getConnectionObject();
						CRMLogger.SysErr("Connecting To iB2B Database");
						csIOMS = iomsConn.prepareCall(spInsertECRMdispatchAddressToIOMS);
									
						/*If isAllRecordInserted flag contain 'false' value that means some records have not inserted, 
						 * and in this way this method 'UpdateIOMSDataTracker' should not be call
						 */
						boolean isAllRecordInserted = true;
						for (Iterator<ECRMBCPAddressDto> iter = lstDispatchAddress.iterator(); iter.hasNext();) 
						{
							try {
								iomsConn.setAutoCommit(false);
								int iColCount = 0;
								
								ECRMBCPAddressDto element = (ECRMBCPAddressDto) iter.next();
								csIOMS.setLong(++iColCount ,element.getAddressId() );
								csIOMS.setLong(++iColCount ,element.getCustAccountId());
								csIOMS.setLong(++iColCount ,element.getCountryId());
								csIOMS.setString(++iColCount ,element.getAddress1());
								csIOMS.setString(++iColCount ,element.getAddress2());
								csIOMS.setString(++iColCount ,element.getAddress3());
								csIOMS.setString(++iColCount ,element.getAddress4());
								csIOMS.setLong(++iColCount ,element.getCityId());
								csIOMS.setString(++iColCount ,element.getPostalCode());
								csIOMS.setLong(++iColCount ,element.getStateId());
								if(element.getUpdatedDate()==null)
									csIOMS.setTimestamp(++iColCount ,  null);
								else
									csIOMS.setTimestamp(++iColCount ,  AppUtility.stringToTimeStamp(element.getUpdatedDate()));
								if(element.getCreatedDate()==null)
									csIOMS.setTimestamp(++iColCount ,  null);
								else
								csIOMS.setTimestamp(++iColCount ,AppUtility.stringToTimeStamp(element.getCreatedDate()));
	
								csIOMS.setString(++iColCount ,element.getContactPersonName());
								csIOMS.setString(++iColCount ,element.getContactPersonMobile());
								csIOMS.setString(++iColCount ,element.getContactPersonEmail());
								csIOMS.setString(++iColCount ,element.getContactPersonFax());
								csIOMS.setString(++iColCount ,element.getLstNo());
								if(element.getLstDate()==null)
									csIOMS.setTimestamp(++iColCount ,null);
								else
									csIOMS.setTimestamp(++iColCount ,AppUtility.stringToTimeStamp(element.getLstDate()));
								csIOMS.setLong(++iColCount ,insert_update);
								csIOMS.setLong(++iColCount ,0);
								csIOMS.setString(++iColCount ,"");
								csIOMS.setString(++iColCount ,"");
								csIOMS.setString(++iColCount ,"");
								csIOMS.setString(++iColCount ,element.getDesignation());
	
								csIOMS.execute();
								
								CRMLogger.SysErr(csIOMS.getString(22));
								
								/*--Going to commit the transaction if no error found other wise transaction would be rollback--*/
								if (csIOMS.getInt(20) == 0) {
									iomsConn.commit();
								}else{
									iomsConn.rollback();
									isAllRecordInserted = false;
									CRMLogger.SysErr("Error Inserting Dispatch Address In Ib2b Database");
								}
									
							}catch(Exception ex){
								iomsConn.rollback();
								isAllRecordInserted = false;
								CRMLogger.SysErr("Exception during inserting the record in method InsertECRMDisptachAddressInIOMS()"
										+ ex.getStackTrace());
							}
						}
						//end of for loop
						
						  if (isAllRecordInserted) {
							SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");
							if(insert_update==1){
								UpdateIOMSDataTracker("insert",AppConstants.DISPATCH_ADDRESS,sdf.format(new Date()),sdf.format(new Date()));
								CRMLogger.SysErr("Dispatch Address Insertion In Ib2b Database and Date tracker updated sucessfully");											
								}
							else
							{
								UpdateIOMSDataTracker("update",AppConstants.DISPATCH_ADDRESS,sdf.format(new Date()),sdf.format(new Date()));	
								CRMLogger.SysErr("Dispatch Address Updated In Ib2b Database and Date tracker updated sucessfully");
							}	
							
						} else {
							CRMLogger.SysErr("Some Dispatch Address not inserted properly so Date tracker not updated");
						}
						
					}
				} catch (Exception e) {
					CRMLogger.SysErr("Error in method dispatch Address()"
							+ e.getStackTrace());
					e.printStackTrace();
				} finally {
					try {
						DbConnection.closeResultset(rset);
						DbConnection.closePreparedStatement(pstmt);
						DbConnection.closeCallableStatement(csIOMS);
						DbConnection.freeConnection(crmcon);
						DbConnection.freeConnection(iomsConn);
					} catch (Exception e) {
						CRMLogger.SysErr("exeption due to : " + e.getMessage());
					}
				}
	}
	
	/*   	********************************************************************************
	 *		Function Name:- InsertECRMDisptachAddressInIOMS
	 *		Created By:-    Sumit Arora
	 * 		Created On:-    14-Feb-2011
	 * 		Purpose:-		To Get All the BCP Address From CRM and Insrt them in IOMS
	 * 		Parameters:-	
	 * 						1.  htCRMValue - 	For Getting the Date From Which Data is to be fetched from CRM 
	 * 						2.  insert_update -	If insert_update is '1' Insert is to be made and if '2' than update is to be made
	 *      ********************************************************************************
	 */ 

	public static void InsertECRMNetworkLocationInIOMS(Hashtable<String, String> htCRMValue,int insert_update)
	{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		DBConnectionRetriever con = new DBConnectionRetriever();
		Connection crmcon = null;
		Connection iomsConn = null;
		CallableStatement csIOMS = null;
		try {
			crmcon = con.getCRMConnection();
			if(insert_update==1){
				pstmt = crmcon.prepareStatement(strGetECRMNetworkLocation);
			}else{
				pstmt = crmcon.prepareStatement(strGetUpdatedECRMNetworkLocation);
			}	
			pstmt.setString(1, htCRMValue.get(AppConstants.NETWORK_LOCATION).toString());
			rset = pstmt.executeQuery();
			ArrayList<ECRMNetworkPOPLocationDto> lstNetworkAddress = new ArrayList<ECRMNetworkPOPLocationDto>();
			ECRMNetworkPOPLocationDto objDto = null; 
			System.err.println("************Total No of Location Fetched*****************"+ String.valueOf(rset.getFetchSize()));
			while (rset.next()) 
			{
				objDto = new ECRMNetworkPOPLocationDto();
				objDto.setPopLocId(rset.getLong("POP_LOC_ID"));
				objDto.setPopLocCode(rset.getString("POP_LOC_CODE"));
				objDto.setPopLocationName(rset.getString("POP_LOC_NAME"));
				objDto.setPopLocAddress(rset.getString("POP_LOC_ADDRESS"));
				objDto.setPopLocState(rset.getString("POP_STATE"));
				objDto.setPopLocCity(rset.getString("POP_CITY"));
				objDto.setUpdatedDate(rset.getString("LAST_UPDATE_DATE"));
				objDto.setCreatedDate(rset.getString("CREATION_DATE"));
				lstNetworkAddress.add(objDto);
			}
			
			if(lstNetworkAddress.size()==0)
			{
				CRMLogger.SysErr("NO Network Location DATA FOUND");
			}
			if(lstNetworkAddress.size()>0)
			{
				CRMLogger.SysErr("Network Location Fetched And Stored In ArrayList");
				iomsConn = getConnectionObject();
				CRMLogger.SysErr("Connecting To iB2B Database");
				csIOMS = iomsConn.prepareCall(spInsertECRMPopLocationToIOMS);
				
				/*If isAllRecordInserted flag contain 'false' value that means some records have not inserted, 
				 * and in this way this method 'UpdateIOMSDataTracker' should not be call
				 */
				for (Iterator<ECRMNetworkPOPLocationDto> iter = lstNetworkAddress.iterator(); iter.hasNext();) 
				{
					try {
						iomsConn.setAutoCommit(false);
						int iColCount = 0;
						
						ECRMNetworkPOPLocationDto element = (ECRMNetworkPOPLocationDto) iter.next();
						csIOMS.setLong(++iColCount ,element.getPopLocId());
						csIOMS.setLong(++iColCount ,element.getPopLocId());
						csIOMS.setString(++iColCount ,element.getPopLocCode());
						csIOMS.setString(++iColCount ,element.getPopLocationName());
						csIOMS.setString(++iColCount ,element.getPopLocAddress());
						if(element.getUpdatedDate()==null)
							csIOMS.setTimestamp(++iColCount ,  null);
						else
						csIOMS.setTimestamp(++iColCount ,  AppUtility.stringToTimeStamp(element.getUpdatedDate()));
						
						if(element.getCreatedDate()==null)
							csIOMS.setTimestamp(++iColCount ,null);
						else
							csIOMS.setTimestamp(++iColCount ,AppUtility.stringToTimeStamp(element.getCreatedDate()));
						
						csIOMS.setString(++iColCount ,element.getPopLocCity());
						csIOMS.setString(++iColCount ,element.getPopLocState());
						csIOMS.setLong(++iColCount ,insert_update);
						csIOMS.setLong(++iColCount ,0);
						csIOMS.setString(++iColCount ,"");
						csIOMS.setString(++iColCount ,"");
						csIOMS.setString(++iColCount ,"");

						csIOMS.execute();
						CRMLogger.SysErr(csIOMS.getString(13));
						
						/*--Going to commit the transaction if no error found other wise transaction would be rollback--*/
						if (csIOMS.getInt(11) == 0) {
							iomsConn.commit();
						}else{
							iomsConn.rollback();
							CRMLogger.SysErr("Error Inserting/Updating Network Location In IB2B Database");
						}
						
					}catch(Exception ex){
						iomsConn.rollback();
						CRMLogger.SysErr("Exception occur during inserting the record in method InsertECRMNetworkLocationInIOMS()"
								+ ex.getStackTrace());
					}	
					
				}
					SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");
					if(insert_update==1)
					{
						UpdateIOMSDataTracker("insert",AppConstants.NETWORK_LOCATION,sdf.format(new Date()),sdf.format(new Date()));
						CRMLogger.SysErr("Network Location Inserted In IB2B Database and Date tracker updated sucessfully");
					}
					else
					{
						UpdateIOMSDataTracker("update",AppConstants.NETWORK_LOCATION,sdf.format(new Date()),sdf.format(new Date()));
						CRMLogger.SysErr("Network Location Updated In IB2B Database and Date tracker updated sucessfully");
					}
			}
		} catch (Exception e) {
			CRMLogger.SysErr("Error in method Network Location InsertECRMNetworkLocationInIOMS()"
					+ e.getStackTrace());
			e.printStackTrace();
		} finally {
			try {
				DbConnection.closeResultset(rset);
				DbConnection.closeCallableStatement(csIOMS);
				DbConnection.closePreparedStatement(pstmt);
				DbConnection.freeConnection(crmcon);
				DbConnection.freeConnection(iomsConn);
				} catch (Exception e) {
					CRMLogger.SysErr("exeption due to : " + e.getMessage());
			}
		}
	}

	
	/*   	********************************************************************************
	 *		Function Name:- UpdateECRMCitiesInIOMS
	 *		Created By:-    Sumit Arora
	 * 		Created On:-    10-11-2010
	 * 		Purpose:-		To Get All the Quotes and Update them in IOMS
	 *      ********************************************************************************
	 */ 
	
	public static int UpdateECRMQuotesInIOMS(Long accountId , Hashtable<String, String> htTrackDate)
	{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		DBConnectionRetriever con = new DBConnectionRetriever();
		Connection crmcon = null;
		Connection iomsConn = null;
		CallableStatement csIOMS = null;
		int status=-1;
		try {
			crmcon = con.getCRMConnection();
			if (accountId==0){
				CRMLogger.SysErr("Query For Getting Quotes::" + strGetWonQuotes);
				pstmt = crmcon.prepareStatement(strGetWonQuotes);
				pstmt.setString(1,htTrackDate.get(AppConstants.TQUOTES_MASTER).toString());
				pstmt.setString(2,htTrackDate.get(AppConstants.TQUOTES_MASTER).toString());
			}
			else
			{
				CRMLogger.SysErr("Query For Getting Quotes::" + strGetWonQuotesWithAccount);
				pstmt = crmcon.prepareStatement(strGetWonQuotesWithAccount);
				pstmt.setLong(1,accountId);
			}
			rset = pstmt.executeQuery();
			ArrayList<ECRMQuoteDto> lstQuotes = new ArrayList<ECRMQuoteDto>();
			ECRMQuoteDto objDto = null; 
			while (rset.next()) 
			{
				objDto = new ECRMQuoteDto();
				objDto.setQuoteNo(rset.getString("QUOTE_NUMBER"));
				objDto.setPartyId(rset.getString("PARTY_ID"));
				objDto.setStatus(rset.getString("STATUS"));
				objDto.setLeadNo(rset.getString("lead_number"));
				objDto.setSalesforceOpportunityNo(rset.getString("salesforce_opportunityno"));
				lstQuotes.add(objDto);
			}
			if(lstQuotes.size()==0)
			{
				CRMLogger.SysErr("Quotes not found");
			}
			if(lstQuotes.size()>0)
			{
				CRMLogger.SysErr("*******************Total Quotes Fetched And Stored In ArrayList:" + String.valueOf(lstQuotes.size()));
				CRMLogger.SysErr("Connect with IOMS database ==>");
				iomsConn = getConnectionObject();
				csIOMS = iomsConn.prepareCall(spInsertECRMQuotesToIOMS);
						
				/*If isAllRecordInserted flag contain 'false' value that means some records have not inserted, 
				 * and in this way this method 'UpdateIOMSDataTracker' should not be call
				 */
				int size=lstQuotes.size();
				boolean isAllRecordInserted = true;
				for (Iterator<ECRMQuoteDto> iter = lstQuotes.iterator(); iter.hasNext();) 
				{
					try {
						iomsConn.setAutoCommit(false);
						ECRMQuoteDto element = (ECRMQuoteDto) iter.next();
						csIOMS.setString(1, element.getPartyId());
						csIOMS.setString(2, element.getQuoteNo());
						csIOMS.setString(3, element.getStatus());
						csIOMS.setString(4, element.getLeadNo());
						csIOMS.setString(5, element.getSalesforceOpportunityNo());
						csIOMS.setInt(6, 0);
						csIOMS.setInt(7, 0);
						csIOMS.setString(8, "");
						
						
						size=size-1;
						System.out.println("remainingggggggggggggg::::::::"+size);
						csIOMS.execute();
						
						CRMLogger.SysErr(csIOMS.getString(8));
						
						/*--Going to commit the transaction if no error found other wise transaction would be rollback--*/
						if (csIOMS.getInt(7) == 0) {
							iomsConn.commit();
						}else{
							iomsConn.rollback();
							isAllRecordInserted = false;
							CRMLogger.SysErr("Error Inserting Quotes In IB2B Database");
						}
					}catch(Exception ex){
						iomsConn.rollback();
						isAllRecordInserted = false;
						CRMLogger.SysErr("Exception occur during inserting the record in method UpdateECRMQuotesInIOMS()" + ex.getStackTrace());
					}
				}
				if (accountId==0){
					SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");
					UpdateIOMSDataTracker("insert","TQUOTES_MASTER",sdf.format(new Date()),sdf.format(new Date()));
					CRMLogger.SysErr("Quotes Inserted In IB2B Database For Selected Party and Date tracker updated sucessfully");
				}
				 if (isAllRecordInserted) {
					status=1;
						CRMLogger.SysErr("Quotes Inserted In IB2B Database For Selected Party");
				} else {
					status=-1;
					CRMLogger.SysErr("Some Quotes have not inserted properly");
				}
			}
			else
			{
				CRMLogger.SysErr("No Opportunity Id present with Account ID :"+accountId);
					status=-2;
			}
		} catch (Exception e) {
			CRMLogger.SysErr("Error in method UpdateECRMQuotesInIOMS()"
					+ e.getStackTrace());
			e.printStackTrace();
		} finally {
			try {
				DbConnection.closeResultset(rset);
				DbConnection.closePreparedStatement(pstmt);
				DbConnection.freeConnection(crmcon);
				DbConnection.freeConnection(iomsConn);} catch (Exception e) {
				CRMLogger.SysErr("exeption due to : " + e.getMessage());
			}
		}
		return status;
	}
	
	public static void UpdateIOMSDataTracker(String state , String tabName , String insertDate , String updateDate)
	{
		Connection iomsConn = null;
		CallableStatement csIOMS = null;
		
		try {
			iomsConn = getConnectionObject();
			csIOMS = iomsConn.prepareCall(spUpdateIOMSDataTracker);
			csIOMS.setString(1, tabName);
			csIOMS.setString(2, insertDate);
			csIOMS.setString(3, updateDate);
			csIOMS.setString(4, state);
			csIOMS.setInt(5, 0);
			csIOMS.setInt(6, 0);
			csIOMS.setString(7, "");
			
			csIOMS.execute();
		}
		catch(Exception ex)
		{
			System.err.println(ex.getMessage());
		}
		finally
		{
			try {
				DbConnection.closeCallableStatement(csIOMS);
				DbConnection.freeConnection(iomsConn);
			} catch (Exception e) {
				System.out.println("exeption due to : " + e.getMessage());
			}
		}
	}
	
	


	/*   	********************************************************************************
	 *		Function Name:- UpdateECRMEmployeeInIOMS
	 *		Created By:-    Sumit Arora
	 * 		Created On:-    10-11-2010
	 * 		Purpose:-		To Get All the Employees From ECRM and Update them in IOMS
	 *      ********************************************************************************
	 */ 
	/*public static void UpdateECRMEmployeeInIOMS()
	{
				PreparedStatement pstmt = null;
				ResultSet rset = null;
				DBConnectionRetriever con = new DBConnectionRetriever();
				Connection crmcon = con.getCRMConnection();
				
				Connection iomsConn = null;
				CallableStatement csIOMS = null;
				boolean isInserted = false;
				try {
					System.out.println("Connect with IOMS database");
					iomsConn = getConnectionObject();
					csIOMS=iomsConn.prepareCall(strGetInsertDateFromIOMS);
					tableName="TECRM_EMPLOYEE_MASTER";
					csIOMS.setString(1, tableName);
					rset=csIOMS.executeQuery();
					System.out.println("fetching insert date from IOMS history table");
					while(rset.next())
					{
						insertDate=rset.getString("INSERTDATE");
					}
					System.out.println("processpartyid()- query ::" + strGetECRMEmployee);
					pstmt = crmcon.prepareStatement(strGetECRMEmployee);
					pstmt.setString(1,insertDate);
					rset = pstmt.executeQuery();
					int i = 0;
					ArrayList lstEmployee = new ArrayList();
					ECRMEmployeeDto objDto = null; 
					while (rset.next()) 
					{
						objDto = new ECRMEmployeeDto();
						objDto.setResId(rset.getString("RESOURCE_NUMBER"));
						objDto.setEmpId(rset.getString("EMPLOYEE_ID"));
						objDto.setEmailId(rset.getString("EMAIL_ADDRESS"));
						objDto.setResName(rset.getString("ResName"));
						objDto.setContactNo(rset.getString("CONTACT_NO"));
						
						lstEmployee.add(objDto);
						
					}
					
					System.err.println("Inside Function....");
					if(lstEmployee.size()>0)
					{
					System.err.println("Employee Fetched And Stored In ArrayList");	
					iomsConn.setAutoCommit(false);
					csIOMS = iomsConn.prepareCall(spInsertECRMEmployeeToIOMS);
								
					
					for (Iterator iter = lstEmployee.iterator(); iter.hasNext();) 
					{
						ECRMEmployeeDto element = (ECRMEmployeeDto) iter.next();
						csIOMS.setString(1, element.getResId());
						csIOMS.setString(2, element.getEmpId());
						csIOMS.setString(3, element.getResName());
						csIOMS.setString(4, element.getEmailId());
						csIOMS.setString(5, element.getContactNo());						
						csIOMS.setInt(6, 0);
						csIOMS.setInt(7, 0);
						csIOMS.setString(8, "");
						csIOMS.execute();
						
					}
							 

								if (csIOMS.getInt(7) == 0) {
									iomsConn.commit();
									SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");									
									UpdateIOMSDataTracker("insert","TECRM_EMPLOYEE_MASTER",sdf.format(new Date()),sdf.format(new Date()));
									isInserted = true;
									System.err.println("Employee Updated....");
								} else {
									iomsConn.rollback();
									isInserted = false;
								}
								
					}
					
						// Long ServiceSegment = null;
					System.out.println("Values assigned processPartyIds()");
				} catch (Exception e) {
					System.out.println("Error in method processPartyIds()"
							+ e.getStackTrace());
					e.printStackTrace();
				} finally {
					try {
						pstmt.close();
						rset.close();
						iomsConn.close();
					} catch (Exception e) {
						System.out.println("exeption due to : " + e.getMessage());
					}
				}
	}*/



	//=========
	/*   	********************************************************************************
	 *		Function Name:- UpdateUserInfoInIOMS
	 *		Created By:-    Sumit Arora
	 * 		Created On:-    10-11-2010
	 * 		Purpose:-		To Get All the User info From ECRM and Update them in IOMS
	 *      ********************************************************************************
	 */ 
	public static void InsertUserInfoInIOMS(Hashtable<String, String> htTrackDate)
	{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Connection iomsConn = null;
		CallableStatement csIOMS = null;
		DBConnectionRetriever con = new DBConnectionRetriever();
		Connection crmcon = null;
		try {
			crmcon = con.getCRMConnection();
			CRMLogger.SysErr("query ::" + strGetECRMEmployeeWithRole);
			pstmt = crmcon.prepareStatement(strGetECRMEmployeeWithRole);
			pstmt.setString(1,htTrackDate.get(AppConstants.TM_ACCOUNTROLEDETAILS).toString());
			rset = pstmt.executeQuery();
			
			ArrayList<ECRMUserInfoDto> lstUserInfo = new ArrayList<ECRMUserInfoDto>();
			ECRMUserInfoDto objDto = null; 
			while (rset.next()) 
			{
				objDto = new ECRMUserInfoDto();
				objDto.setRole_resource_id(rset.getString("role_resource_id"));//employeeid
				objDto.setRoleid(rset.getString("role_id"));
				objDto.setUserid(rset.getString("user_name"));
				objDto.setEmail(rset.getString("EMAIL_ADDRESS"));	
				objDto.setFirstName(rset.getString("first_name"));
				objDto.setLastName(rset.getString("last_name"));
				objDto.setPhoneNo(rset.getString("phone_no"));
				lstUserInfo.add(objDto);
			}
																
			if(lstUserInfo.size()>0)
			{
				CRMLogger.SysErr("Connect with IB2B database");					
				iomsConn = getConnectionObject();
				CRMLogger.SysErr("User Info Fetched And Stored In ArrayList");
				csIOMS = iomsConn.prepareCall(spInsertECRMUserInfoToIOMS);
				
				/*If isAllRecordInserted flag contain 'false' value that means some records have not inserted, 
				 * and in this way this method 'UpdateIOMSDataTracker' should not be call
				 */
				boolean isAllRecordInserted = true;
				for (Iterator<ECRMUserInfoDto> iter = lstUserInfo.iterator(); iter.hasNext();) 
				{
					try {
						iomsConn.setAutoCommit(false);
						ECRMUserInfoDto element = (ECRMUserInfoDto) iter.next();
						
						csIOMS.setLong(1, new Long(element.getRoleid()));	
						csIOMS.setLong(2, new Long(element.getRole_resource_id()));
						csIOMS.setString(3, element.getEmail());
						csIOMS.setString(4, element.getUserid());
						csIOMS.setString(5, element.getFirstName());
						csIOMS.setString(6, element.getLastName());						
						csIOMS.setString(7, element.getPhoneNo());						
						csIOMS.setInt(8, 0);
						csIOMS.setInt(9, 0);
						csIOMS.setString(10, "");
						csIOMS.execute();	
					
						/*--Going to commit the transaction if no error found other wise transaction would be rollback--*/
						if (csIOMS.getInt(8) == 0) {
							iomsConn.commit();
						}else{
							iomsConn.rollback();
							isAllRecordInserted = false;
							CRMLogger.SysErr("User Info Insertion failed...."+csIOMS.getString(10));
						}

					}catch(Exception ex){
						iomsConn.rollback();
						isAllRecordInserted = false;
						CRMLogger.SysErr("Exception occur during inserting the record in method UpdateUserInfoInIOMS()"
								+ ex.getStackTrace());
					}
				}
				//end of for loop		 		 
				if (isAllRecordInserted) {	
					SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");									
					UpdateIOMSDataTracker("insert","TM_ACCOUNTROLEDETAILS",sdf.format(new Date()),sdf.format(new Date()));
					CRMLogger.SysErr("User Info Inserted.... and Date tracker updated sucessfully");
				} else {
					CRMLogger.SysErr("Some User Info Insertion failed....so Date tracker not updated");
				}
			}
		} catch (Exception e) {
			CRMLogger.SysErr("Error in method ()"
					+ e.getStackTrace());
			e.printStackTrace();
			CRMLogger.SysErr("Error in User Info:=>"+e.getMessage());
		} finally {
			try {
				DbConnection.closeResultset(rset);
				DbConnection.closePreparedStatement(pstmt);
				DbConnection.freeConnection(crmcon);
				DbConnection.freeConnection(iomsConn);} catch (Exception e) {
				CRMLogger.SysErr("exeption due to : " + e.getMessage());
			}
		}
	}
	
	public static void processParty(String purpose,Hashtable<String, String> htTrackDate) {
		ResultSet rset = null;
		int accountSyncErrCode=0;
		String statusMsg="";
		int insertedAccount=0;
		int totalRowsFetched=0;
		String 	querystr=null;		
		Connection crmcon = null;
		PreparedStatement pstmt = null;		
		try {
			DBConnectionRetriever con = new DBConnectionRetriever();
			crmcon = con.getCRMConnection();
			if(purpose.equalsIgnoreCase("inserting"))
			{
				CRMLogger.SysErr("Fetching data from "+insertDate+" to current date For Insert");
				querystr = getQueryStringUpdatingAccount() 
						+ " and HCA.CREATION_DATE BETWEEN TO_DATE(?)  AND SYSDATE";
				CRMLogger.SysErr("Account fetching querystr For Insert-->"+querystr);
				pstmt = crmcon.prepareStatement(querystr);
				pstmt.setString(1, htTrackDate.get(AppConstants.TM_ACCOUNT).toString());
				rset = pstmt.executeQuery();
			}
			else
			{
				CRMLogger.SysErr("Fetching data from "+insertDate+" to current date For Update");
				querystr = getQueryStringUpdatingAccount() 
						+ " and ((HCA.LAST_UPDATE_DATE  BETWEEN TO_DATE(?)  AND SYSDATE) OR  (p.LAST_UPDATE_DATE BETWEEN TO_DATE(?)  AND SYSDATE))";
				CRMLogger.SysErr("Account fetching querystr For Update-->"+querystr);
				pstmt = crmcon.prepareStatement(querystr);
				pstmt.setString(1, htTrackDate.get(AppConstants.TM_ACCOUNT).toString());
				pstmt.setString(2, htTrackDate.get(AppConstants.TM_ACCOUNT).toString());
				rset = pstmt.executeQuery();
			}
			while (rset.next()) 
			{
				accountSyncErrCode=0;
				totalRowsFetched++;
				String crmAccountNo=rset.getString("CRM_ACCOUNTNO");
				String partyIDS=rset.getString("partyId");
				Long partyId = new Long(rset.getString("partyId"));
				String partyName=rset.getString("partyName");
				String phoneNo=rset.getString("phoneNo");
				CRMLogger.SysErr("***********************************[Start:CRM Account number{"+crmAccountNo+"},Party Id{"+partyIDS+"} ]**************************************************");
				//====================CRM Region Validation in CRM====================
				String regionId=rset.getString("regionid");
				if("".equalsIgnoreCase(regionId)|| regionId==null){
					accountSyncErrCode=-3232;
					statusMsg="Error[-3232]: Region not found of account no ["+crmAccountNo+"] in CRM";
					CRMLogger.SysErr(statusMsg);
				}
				Long accountMgrId = new Long(rset.getLong("accountMgrId"));
				Long projectMgrId = Long.valueOf(rset.getLong("projectmgrid"));
				//=========================Account Manager Id and Project Manager Check==============================				
				if(accountMgrId == 0 && projectMgrId == 0){
					accountSyncErrCode=-3333;
					statusMsg="Error[-3333]: Account Manager and Project Manager not found of account no ["+crmAccountNo+"] in CRM";
					CRMLogger.SysErr(statusMsg);
				}else if(accountMgrId == 0 ){
					accountSyncErrCode=-4444;
					statusMsg="Error[-4444]: Account Manager not found of account no ["+crmAccountNo+"] in CRM";
					CRMLogger.SysErr(statusMsg);
				}else if(projectMgrId == 0){
					accountSyncErrCode=-5555;
					statusMsg="Error[-5555]: Project Manager not found of account no ["+crmAccountNo+"] in CRM";
					CRMLogger.SysErr(statusMsg);
				}					
				
				//----------------------------------------------------------------------------------
								//FX and M6 Completion Flag Validation
				//----------------------------------------------------------------------------------
				String fx_account_complete_flag=rset.getString("FX_ACCOUNT_COMP_FLAG");
				String m6_account_complete_flag=rset.getString("M6_ACCOUNT_COMP_FLAG");
			
				if(!"Completed".equalsIgnoreCase(fx_account_complete_flag) && !"Completed".equalsIgnoreCase(m6_account_complete_flag))
				{
					accountSyncErrCode=-6666;
					statusMsg="Error[-6666]: Account is not completed in FX and M6 of account no ["+crmAccountNo+"]";
					CRMLogger.SysErr(statusMsg);
				}
				else if(!"Completed".equalsIgnoreCase(fx_account_complete_flag))
				{
					accountSyncErrCode=-7777;
					statusMsg="Error[-7777]: Account is not completed in FX of account no ["+crmAccountNo+"]";
					CRMLogger.SysErr(statusMsg);
				}
				else if(!"Completed".equalsIgnoreCase(m6_account_complete_flag))
				{
					accountSyncErrCode=-8888;
					statusMsg="Error[-8888]: Account is not completed in M6 of account no ["+crmAccountNo+"]";
					CRMLogger.SysErr(statusMsg);
				}	
				
				String verticalId =rset.getString("VerticalId");
				Long customerSegment = new Long(rset.getLong("customerSegment"));
				String accountCategoryId = rset.getString("AccountCategory");
				String lstNo = rset.getString("LSTNO");
				String cstNo = rset.getString("CSTNO");
				long collectionMgrId;

				if(rset.getString("collectionMgrId")!=null)
					collectionMgrId = Long.valueOf(rset.getString("collectionMgrId"));
				else
					collectionMgrId = 0;
				
				
				String m6Accountno = rset.getString("M6AccountNo");
				String m6ShortCode=rset.getString("M6ShortCode");
				
				String accountId=rset.getString("accountId");
																														
				Long industrySegment=new Long(rset.getString("industrySegment"));
				Long serviceSegment=new Long(rset.getString("serviceSegment"));
				String zoneId=rset.getString("ZONEID");
				String partyNO="";
				if(rset.getString("PARTYNUMBER")!=null){
					 partyNO=rset.getString("PARTYNUMBER");
				}
				
				String osp=rset.getString("OSP");
				int ospvalue=0;
				if("N".equalsIgnoreCase(osp))
				{
					ospvalue=2;
				}
				else if("Y".equalsIgnoreCase(osp))
				{
					ospvalue=1;
				}
				else if("".equalsIgnoreCase(osp))
				{
					ospvalue=2;
				}
				 
				String circle=rset.getString("CIRCLE"); 
				String category = rset.getString("CATEGORY");
				Long groupID = rset.getLong("GROUPID");
				String groupDesc = rset.getString("GROUP_DESC");
	            String sfdcCustomerId = rset.getString("sfdcCustomerId");
				if(accountSyncErrCode==0)
				{
					CRMLogger.SysErr(">>>>> Calling IB2B Function for Account Insertion for crmaccountno:"+crmAccountNo);
					if(purpose.equalsIgnoreCase("inserting")||purpose.equalsIgnoreCase("insert"))
					{						
						insertAccountInfoInIOMS1(partyId,
								partyName,phoneNo,accountMgrId,projectMgrId,collectionMgrId,
								accountCategoryId,verticalId, lstNo , cstNo,
								m6Accountno,accountId,crmAccountNo,m6ShortCode,regionId,customerSegment,industrySegment,serviceSegment,partyNO,zoneId,ospvalue,"N",circle, category,groupID,groupDesc,sfdcCustomerId);
						insertedAccount++;
					}
					else
					{
						ModifyAccountInfoInIOMS1(partyId,
								partyName,phoneNo,accountMgrId,projectMgrId,collectionMgrId,
								accountCategoryId,verticalId, lstNo , cstNo,
								m6Accountno,accountId,crmAccountNo,m6ShortCode,regionId,customerSegment,industrySegment,serviceSegment,partyNO,zoneId,ospvalue,"N",circle, category,groupID,groupDesc,sfdcCustomerId);
						insertedAccount++;
				}
				}
				CRMLogger.SysErr("***********************************[End]**************************************************");

			}
			CRMLogger.SysErr("Total Records Fetched:"+totalRowsFetched);
			CRMLogger.SysErr("Total Inserted Records: "+insertedAccount);
		} 
		catch (Exception e) 
		{
			CRMLogger.SysErr("Error in method processPartyIds()" + e.getStackTrace());
			e.printStackTrace();
		} 
		finally 
		{
			try 
			{
				DbConnection.closeResultset(rset);
				DbConnection.closePreparedStatement(pstmt);
				DbConnection.freeConnection(crmcon);
			} 
			catch (Exception e) 
			{
				CRMLogger.SysErr("exeption due to : " + e.getMessage());
			}
		}
	}

	public static String getQueryStringUpdatingAccount() 
	{
		
		String querystr="select  distinct " +
		"( SELECT   res.resource_id FROM   apps.jtf_rs_group_members gm,apps.jtf_rs_defresroles_vl rr," +
		" apps.jtf_rs_roles_vl r, apps.jtf_rs_resource_extns res, apps.hz_parties hp  WHERE hp.party_number = p.party_Number " +
	   " AND rr.role_resource_id = gm.group_member_id " +
	   " AND rr.role_id = r.role_id " +
	   " AND gm.resource_id = res.resource_id" +
	   " AND r.role_type_code = 'SALES' " +
	   " AND r.role_code in ('PROJECT_MANAGER') " +
	   " AND TRUNC (SYSDATE) BETWEEN TRUNC (res_rl_start_date) " +
	   " AND NVL (TRUNC (res_rl_end_date), TRUNC (SYSDATE)) " +
	   " AND rr.delete_flag = 'N' " +
	   " AND gm.delete_flag = 'N' " +
	   " AND GROUP_ID = hp.attribute11 " +
		" and rownum=1 ) as projectmgrid ," +
		"NVL(CUST_ACCOUNT_ID,0) as accountId,'AES' as LOB,NVL(O.attribute7,' ') AS regionid,NVL(p.ATTRIBUTE7,' ') AS ZONEID," 
	+" NVL(HCA.GLOBAL_ATTRIBUTE13,' ') as M6ShortCode,NVL(HCA.account_number,' ') as M6AccountNo,NVL(HCA.attribute20,' ') AS LSTNO," 
	+" NVL(O.attribute13,' ') AS CSTNO,NVL(ACCOUNT_NUMBER,' ') AS CRM_ACCOUNTNO,NVL(p.party_id,0) AS partyId ,NVL(p.party_name,' ') AS partyName,NVL(p.party_Number,' ') AS partyNumber,"
	+" NVL(p.primary_phone_number,'0') phoneNo,"			
	+" (nvl((Select JRD.RESOURCE_ID  FROM   apps.JTF_RS_GROUP_MBR_ROLE_VL GRPMEMROLE, xxibm.ibm_btvl_locations ibl, apps.JTF_RS_RESOURCE_EXTNS jrd WHERE"  
	+" jrd.resource_id = GRPMEMROLE.resource_id and jrd.attribute3 = ibl.location_id and GRPMEMROLE.ROLE_NAME='Secondary Account Manager' and" 
	+" GRPMEMROLE.GROUP_ID=to_number(p.attribute11)  and rownum=1),"         
	+" (Select JRD.RESOURCE_ID  FROM   apps.JTF_RS_GROUP_MBR_ROLE_VL GRPMEMROLE, xxibm.ibm_btvl_locations ibl, apps.JTF_RS_RESOURCE_EXTNS jrd"
	+" WHERE  jrd.resource_id = GRPMEMROLE.resource_id and jrd.attribute3 = ibl.location_id and GRPMEMROLE.ROLE_NAME='Account Manager' and" 
	+" GRPMEMROLE.GROUP_ID=to_number(p.attribute11)  and rownum=1))) accountMgrId,"
	+" (nvl((Select JRD.RESOURCE_ID  FROM"   
	+" apps.JTF_RS_GROUP_MBR_ROLE_VL GRPMEMROLE, xxibm.ibm_btvl_locations ibl, apps.JTF_RS_RESOURCE_EXTNS jrd WHERE"  
	+" jrd.resource_id = GRPMEMROLE.resource_id and jrd.attribute3 = ibl.location_id and GRPMEMROLE.ROLE_NAME='Collection Manager' and" 
	+" GRPMEMROLE.GROUP_ID=to_number(p.attribute11)  and rownum=1)," 
	+" (Select JRD.USER_ID  FROM   apps.JTF_RS_GROUP_MBR_ROLE_VL GRPMEMROLE, xxibm.ibm_btvl_locations ibl, apps.JTF_RS_RESOURCE_EXTNS jrd WHERE"  
	+" jrd.resource_id = GRPMEMROLE.resource_id and jrd.attribute3 = ibl.location_id and GRPMEMROLE.ROLE_NAME='Collection Manager'" 
	+" and GRPMEMROLE.GROUP_ID=to_number(p.attribute11)  and rownum=1))) collectionMgrId,"
	+" (nvl((Select JRD.RESOURCE_ID  FROM"   
	+" apps.JTF_RS_GROUP_MBR_ROLE_VL GRPMEMROLE, xxibm.ibm_btvl_locations ibl, apps.JTF_RS_RESOURCE_EXTNS jrd WHERE"  
	+" jrd.resource_id = GRPMEMROLE.resource_id and jrd.attribute3 = ibl.location_id and GRPMEMROLE.ROLE_NAME='Project Manager' and" 
	+" GRPMEMROLE.GROUP_ID=to_number(p.attribute11)  and rownum=1)," 
	+" (Select JRD.USER_ID  FROM   apps.JTF_RS_GROUP_MBR_ROLE_VL GRPMEMROLE, xxibm.ibm_btvl_locations ibl, apps.JTF_RS_RESOURCE_EXTNS jrd WHERE"  
	+" jrd.resource_id = GRPMEMROLE.resource_id and jrd.attribute3 = ibl.location_id and GRPMEMROLE.ROLE_NAME='Project Manager'" 
	+" and GRPMEMROLE.GROUP_ID=to_number(p.attribute11)  and rownum=1))) PRcollectionMgrId,"
	+" NVL((Select JRD.Attribute6 FROM   apps.JTF_RS_GROUP_MBR_ROLE_VL GRPMEMROLE,xxibm.ibm_btvl_locations ibl, apps.JTF_RS_RESOURCE_EXTNS jrd" 
	+" WHERE  jrd.resource_id = GRPMEMROLE.resource_id and jrd.attribute3 = ibl.location_id and GRPMEMROLE.ROLE_NAME='Project Manager'and" 
	+" GRPMEMROLE.GROUP_ID=to_number(p.attribute11)and rownum=1),'0') PartyChannelPartnerId," 
	+" NVL(o.ORGANIZATION_NAME,' ') AS organizationName," 
	+" NVL(p.attribute2,0) AS industrySegment," 
	+" NVL(o.attribute1,' ') AS businessCode," 	
	+" NVL(p.attribute2,0) as VerticalId,"
	+" NVL(p.attribute3,0) as AccountCategory,"
	+" NVL(p.attribute3,0) as serviceSegment,"
	+" NVL(p.attribute1,0) as customerSegment,"		
	+" NVL(p.attribute24,'N') as OSP,"
	+" NVL(HCA.attribute6,' ') as CIRCLE,"
	+" NVL(o.attribute8,' ') as CATEGORY,"
	+" NVL(HCA.GLOBAL_ATTRIBUTE16,' ') as FX_ACCOUNT_COMP_FLAG,"
	+" NVL(HCA.GLOBAL_ATTRIBUTE19,' ') as M6_ACCOUNT_COMP_FLAG,"
	+" NVL(p.attribute11,0) AS GROUPID,"   
	+" NVL((SELECT NVL(GROUP_DESC,'') AS GROUP_DESC FROM APPS.JTF_RS_GROUPS_TL WHERE  GROUP_ID = p.attribute11),' ')  AS GROUP_DESC ,"
	+" NVL(p.attribute17,NVL(p.party_Number,'0')) as SFDCCustomerId "  //added on 28th September 2016
	+" from" 
	+" apps.hz_parties p,apps.HZ_ORGANIZATION_PROFILES o,apps.HZ_PARTY_SITES ps," 
	+" apps.HZ_LOCATIONS l,apps.hz_party_site_uses k,apps.HZ_CUST_ACCOUNTS HCA" 
	+" where"
	+" HCA.PARTY_ID =  p.PARTY_ID AND o.party_id = p.party_id" 
	+" and o.EFFECTIVE_END_DATE is null and ps.party_id = p.party_id" 
	+" and ps.LOCATION_ID = l.LOCATION_ID and k.PARTY_SITE_ID = ps.PARTY_SITE_ID" 
	+" and k.SITE_USE_TYPE ='BILL_TO' and P.party_type ='ORGANIZATION'"
	+" AND ps.STATUS='A' AND k.END_DATE IS NULL";
	//+" AND HCA.GLOBAL_ATTRIBUTE16 = 'Completed' AND HCA.GLOBAL_ATTRIBUTE19 = 'Completed'"	
		return querystr;
	}
	
	
	public static void main(String args[]) throws Exception {
		System.out.println("Starting Procesing");

		//DBConnectionRetriever con = new DBConnectionRetriever();
		//Connection crmcon = con.getCRMConnection();
		
		//Connection conFlag = getConnectionObject();
		
		TransactionBatch.getLatestDateForTable(htCRMLastInsertedValue,
				htCRMLastUpdatedValue);
		
		
		System.err.println("Quotes Insertion Started....");
			TransactionBatch.UpdateECRMQuotesInIOMS(Long.valueOf(43817521), htCRMLastInsertedValue);
		System.err.println("Quotes Insertion Completed....");
		
		/*System.err.println("Account Inserting Started.....");
		processPartyIds(crmcon,1,"inserting");
		System.err.println("Account Inserting Completed.....");

		//System.err.println("Account Update Started.....");
		//processPartyIds(crmcon,1,"update");
		//System.err.println("Account Update Completed.....");
		
				
		System.err.println("BCP Address Inserting Started....");
		TransactionBatch.InsertECRMBCPAddressInIOMS(
				htCRMLastInsertedValue, 1);
		System.err.println("BCP Address Inserting Completed....");
	
		System.err.println("Dispatch Address Insert started....");
		InsertECRMDisptachAddressInIOMS(htCRMLastInsertedValue,1);
		System.err.println("Dispatch Address Insert  Completed....");
		
		System.err.println("bcp Updation Started....");
		TransactionBatch.InsertECRMBCPAddressInIOMS(htCRMLastUpdatedValue, 2);
		System.err.println("bcp Updation Completed....");

		System.err.println("Dispatch Address Update started....");
		InsertECRMDisptachAddressInIOMS(htCRMLastInsertedValue,2);
		System.err.println("Dispatch Address Update Completed....");

		System.err.println("User Info Insert....Started");
		UpdateUserInfoInIOMS();  
		System.err.println("User Info Insert....Completed");
		
		System.err.println("Network Location insertion Started....");
		//TransactionBatch.InsertECRMNetworkLocationInIOMS(htCRMLastInsertedValue, 1);
		System.err.println("Network Location insertion Completed....");

		//System.err.println("Network Location Updation Started....");
		//TransactionBatch.InsertECRMNetworkLocationInIOMS(htCRMLastUpdatedValue, 2);
		//System.err.println("Network Location Updation Completed....");
		
		
		System.err.println("Quotes Insertion Started....");
		TransactionBatch.UpdateECRMQuotesInIOMS();
		System.err.println("Quotes Insertion Completed....");
		
		System.err.println("DD Values Insert....Started");
		int stage=1; //For Insert
		InsertUpdateProductDDValuesInIOMS(stage);  
		System.err.println("DD Values Insert....Completed");
		
		System.err.println("DD Values Update....Started");
		stage=2; //For Update
		InsertUpdateProductDDValuesInIOMS(stage);  
		System.err.println("DD Values Update....Completed");
		
		System.err.println("OpportunityId Insert....Started");
		InsertUpdateOpportunityIdInIOMS();  
		System.err.println("OpportunityId Insert....Completed");*/
		
		
		//InsertSingleViewDataInCRM();
		
		//UpdateSingleViewDataInCRM();
		

		try {
		} catch (Exception e) {
			System.out.println("couldnt fetch" + e.getMessage());
			System.out.println("Error fetch" + e.getStackTrace());
			e.printStackTrace();
		}
	}

	public static Connection getConnectionObject() throws Exception {
		Connection conn = null;
		try {
			//DriverManager.registerDriver(new com.ibm.db2.jcc.DB2Driver());
			//conn = DriverManager.getConnection("jdbc:db2://10.5.153.243:60004/IOES_PRD", "a1448525","jun@2013");
			//conn = getURL();
			conn= DbConnection.getConnectionObject();
			
			// System.err.println("conn2"+conn);
		} catch (Exception e) {
			e.printStackTrace();
			throw (Exception) e;
		}
		return conn;
	}
	
	public static Connection getURL() throws Exception 
	{
		Connection connection =null;
		CallableStatement getRegionDetails =null;
		ResultSet rsRegionDetails = null;
		Connection conn = null;
		try
		{
			connection=DbConnection.getConnectionObject();
			String sQry = "SELECT KEYVALUE  as url , ";
				sQry+="(SELECT KEYVALUE  as users FROM IOE.TM_APPCONFIG WHERE KEYNAME = 'DB2USER') as DB2USER ,";
				sQry+="	(SELECT KEYVALUE  as users FROM IOE.TM_APPCONFIG WHERE KEYNAME = 'DB2PWD') as DB2PWD  FROM IOE.TM_APPCONFIG WHERE KEYNAME = 'DB2URL'";
    		
			getRegionDetails= connection.prepareCall(sQry);
			rsRegionDetails = getRegionDetails.executeQuery();
			while(rsRegionDetails.next())
			{
				conn = DriverManager.getConnection(rsRegionDetails.getString("url"),rsRegionDetails.getString("DB2USER"),rsRegionDetails.getString("DB2PWD"));
				
			}
		}
		catch(Exception ex )
		{
			ex.printStackTrace();	
		}
		finally
		{
			try 
			{
				DbConnection.closeResultset(rsRegionDetails);
				DbConnection.closeCallableStatement(getRegionDetails);
				
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		return conn;

	}
	
	public static void freeConnection(Connection conn) throws Exception {
			try {
				if (conn != null && conn.isClosed() == false) {
					DbConnection.freeConnection(conn);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}


	public static boolean insertAccountInfoInIOMS(Long partyId,
			String partyName,String phoneNo, String organizationName, Long accountMgrId,
			Long industrySegment, Long customerSegment, String businessCode,
			Long ServiceSegment, String address1, String address2, String city,
			String state, String pincode, String partyChannelPartnerId,String lstNo , String cstNo,String m6Accountno) {

		Connection iomsConn = null;
		CallableStatement csIOMS = null;
		boolean isInserted = false;
		try {
			iomsConn = getConnectionObject();
			System.err.println("Inside Function....");
			csIOMS = iomsConn.prepareCall(spInsertAccountECRMtoIOMS);
			
 			
			csIOMS.setLong(1 , Long.parseLong(partyId.toString()));
			csIOMS.setString(2, partyName);
			csIOMS.setString(3 , phoneNo);
			csIOMS.setLong(4, Long.parseLong(accountMgrId.toString()));
			csIOMS.setInt(5, 0);
			csIOMS.setInt(6, 0);
			csIOMS.setString(7, "");
			csIOMS.execute();

 

			if (csIOMS.getInt(6) == 0) {
				iomsConn.commit();
				SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");									
				UpdateIOMSDataTracker("insert","TM_ACCOUNT",sdf.format(new Date()),sdf.format(new Date()));
				isInserted = true;
				System.err.println("Account Updated....");
			} else {
				iomsConn.rollback();
				isInserted = false;
			}

		} catch (Exception e) {
			System.out.println("Error in method processPartyIds()"
					+ e.getStackTrace());
			e.printStackTrace();
		} finally {
			try {
				DbConnection.closeCallableStatement(csIOMS);
				DbConnection.freeConnection(iomsConn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return isInserted;
	}
	
	public static boolean insertAccountInfoInIOMS1(Long partyId,
			String partyName,String phoneNo, Long accountMgrId,Long projectMgrId,Long collectionMgrId,
			String accountCategoryId, String verticalId, String lstNo , String cstNo,
			String m6Accountno,String accountId,String crmAccountNo,String m6ShortCode,String regionId,Long customerSegment,Long industrySegment,Long serviceSegment,String partyNo,String zoneId,int osp,String isFromGUI,String circle, String category,Long groupID,String groupDesc,String sfdcCustomerId) {

		Connection iomsConn = null;
		CallableStatement csIOMS = null;
		boolean isInserted = false;
		try 
		{
			iomsConn = getConnectionObject();
			CRMLogger.SysErr("Inside IB2B Function....For Inserting Account Number > " + crmAccountNo);
			CRMLogger.SysErr("Inside IB2B Function....For Inserting Paty Number > " + partyNo);
			csIOMS = iomsConn.prepareCall(spInsertAccountECRMtoIOMS);
			
 			int iColCount=0;
			csIOMS.setLong(++iColCount , Long.parseLong(accountId));
			csIOMS.setString(++iColCount, partyId.toString());
			csIOMS.setString(++iColCount , crmAccountNo);
			csIOMS.setString(++iColCount, m6Accountno);
			csIOMS.setString(++iColCount, lstNo);
			csIOMS.setString(++iColCount, cstNo);
			csIOMS.setString(++iColCount, accountCategoryId);
			csIOMS.setString(++iColCount, verticalId);
			csIOMS.setString(++iColCount, partyName);
			csIOMS.setString(++iColCount, phoneNo);
			csIOMS.setLong(++iColCount, accountMgrId);
			csIOMS.setLong(++iColCount, projectMgrId);
			csIOMS.setLong(++iColCount, collectionMgrId);
			csIOMS.setString(++iColCount, m6ShortCode);
			csIOMS.setString(++iColCount, regionId);
			csIOMS.setLong(++iColCount, industrySegment);
			csIOMS.setLong(++iColCount, customerSegment);
			csIOMS.setLong(++iColCount, serviceSegment);
			csIOMS.setString(++iColCount, partyNo);
			csIOMS.setInt(++iColCount, 0);
			csIOMS.setInt(++iColCount, 0);
			csIOMS.setString(++iColCount, "");
			csIOMS.setString(++iColCount, zoneId);
			csIOMS.setInt(++iColCount, osp);
			csIOMS.setString(++iColCount, circle);//Added on 9-jan-2013, for circle 
			csIOMS.setString(++iColCount, category);//Added on 30-apr-2013, for category 
			csIOMS.setLong(++iColCount, groupID);//Added on 25-Jun-2013, for groupID 
			csIOMS.setString(++iColCount, groupDesc);
			csIOMS.setString(++iColCount, sfdcCustomerId);//Added on 28th Sept 2016
			csIOMS.execute();

 

			if (csIOMS.getInt(21) == 0) 
			{
				iomsConn.commit();
				if("N".equalsIgnoreCase(isFromGUI))
				{
					SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");									
					UpdateIOMSDataTracker("insert","TM_ACCOUNT",sdf.format(new Date()),sdf.format(new Date()));
					isInserted = true;
					CRMLogger.SysErr("--------------- [ ACCOUNT INSERTION REPORT START ] ---------------");
					CRMLogger.SysErr("Fetching Account Mesg "+csIOMS.getString(22));
					CRMLogger.SysErr("--------------- [ ACCOUNT INSERTION REPORT END ] ---------------");
				}
				else
				{
					isInserted = true;
					CRMLogger.SysErr("--------------- [ ACCOUNT INSERTION REPORT START ] ---------------");
					CRMLogger.SysErr("Fetching Account Mesg "+csIOMS.getString(22));
					CRMLogger.SysErr("--------------- [ ACCOUNT INSERTION REPORT END] ---------------");
				}
				
			} 
			else 
			{
				iomsConn.rollback();
				isInserted = false;
				CRMLogger.SysErr("--------------- [ FAILED ] ---------------");
				CRMLogger.SysErr("Fetching Account Error Code "+csIOMS.getString(21));
				CRMLogger.SysErr("Fetching Account Error Mesg "+csIOMS.getString(22));
			}

		} catch (Exception e) {
			CRMLogger.SysErr("Error[-2222] in method insertAccountInfoInIOMS1()" + crmAccountNo
					+ e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				DbConnection.closeCallableStatement(csIOMS);
				DbConnection.freeConnection(iomsConn);
				
				try{csIOMS.close();}catch(Exception ex){ex.printStackTrace();}
			//	iomsConn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return isInserted;
	}
	/*   	********************************************************************************
	 *		Function Name:- ModifyECRMQuotesInIOMS
	 *		Created By:-    Anil Kumar
	 * 		Created On:-    15-12-2010
	 * 		Purpose:-		To Get All the Quotes and Update them in IOMS
	 *      ********************************************************************************
	 */ 
	public static int ModifyECRMQuotesInIOMS(Long accountId)
	{
				PreparedStatement pstmt = null;
				ResultSet rset = null;
				DBConnectionRetriever con = new DBConnectionRetriever();
				Connection crmcon = con.getCRMConnection();
				
				Connection iomsConn = null;
				CallableStatement csIOMS = null;
				int status=-1;
				try {
					if (accountId==0){
						System.err.println("Connecting To IOMS Database For ECRM MODIFY");
						iomsConn = getConnectionObject();
						csIOMS=iomsConn.prepareCall(strGetUpdateDateFromIOMS);
						tableName="TQUOTES_MASTER";
						csIOMS.setString(1,tableName);
						rset=csIOMS.executeQuery();
						System.out.println("fetching update date from IOMS history table");
						while(rset.next())
						{
							insertDate=rset.getString("UPDATEDATE");
						}
						System.out.println("Query For Getting Cities::" + strGetWonQuotesModify);
						pstmt = crmcon.prepareStatement(strGetWonQuotesModify);
						pstmt.setString(1,insertDate);
						DbConnection.closeCallableStatement(csIOMS);
						DbConnection.freeConnection(iomsConn);
					}
					else
					{
						CRMLogger.SysErr("Query For Getting Quotes::" + strGetWonQuotesModifyWithAccount);
						pstmt = crmcon.prepareStatement(strGetWonQuotesModifyWithAccount);
						pstmt.setLong(1,accountId);
					}
					rset = pstmt.executeQuery();
					ArrayList<ECRMQuoteDto> lstQuotes = new ArrayList<ECRMQuoteDto>();
					ECRMQuoteDto objDto = null; 
					while (rset.next()) 
					{
						objDto = new ECRMQuoteDto();
						objDto.setQuoteNo(rset.getString("QUOTE_NUMBER"));
						objDto.setPartyId(rset.getString("PARTY_ID"));
						objDto.setStatus(rset.getString("STATUS"));
						objDto.setLeadNo(rset.getString("lead_number"));
						lstQuotes.add(objDto);
					}
					
					if(lstQuotes.size()>0)
					{
						System.err.println("Quotes Fetched And Stored In ArrayList");
						System.err.println("Connecting To IOMS Database");
						iomsConn = getConnectionObject();
						csIOMS = iomsConn.prepareCall(spUpdateECRMQuotesToIOMS);
								
						/*If isAllRecordInserted flag contain 'false' value that means some records have not inserted, 
						 * and in this way this method 'UpdateIOMSDataTracker' should not be call
						 */
						for (Iterator<ECRMQuoteDto> iter = lstQuotes.iterator(); iter.hasNext();) 
						{
							try {
								iomsConn.setAutoCommit(false);
								ECRMQuoteDto element = (ECRMQuoteDto) iter.next();
								csIOMS.setString(1, element.getPartyId());
								csIOMS.setString(2, element.getQuoteNo());
								csIOMS.setString(3, element.getStatus());
								csIOMS.setString(4, element.getLeadNo());
								csIOMS.setInt(5, 0);
								csIOMS.setInt(6, 0);
								csIOMS.setString(7, "");
								csIOMS.execute();
								
								/*--Going to commit the transaction if no error found other wise transaction would be rollback--*/
								if (csIOMS.getInt(5) == 0) {
									iomsConn.commit();
								}else{
									iomsConn.rollback();
									CRMLogger.SysErr("Error Updating Quotes In IOMS Database");
								}
							
							}catch(Exception ex){
								iomsConn.rollback();
								CRMLogger.SysErr("Exception occur during inserting the record in method ModifyECRMQuotesInIOMS()"
										+ ex.getStackTrace());
							}
						}
						status=1;
						if (accountId==0){
							SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");
							UpdateIOMSDataTracker("update","TQUOTES_MASTER",sdf.format(new Date()),sdf.format(new Date()));
						}
						System.err.println("Quotes Updated In IOMS Database For Selected Party");
					} else {
						status=-1;
						CRMLogger.SysErr("Some Updating have not updated properly so Date tracker not updated");
					}
			} catch (Exception e) {
				System.out.println("Error in method Quotes()"
						+ e.getStackTrace());
				e.printStackTrace();
			} finally {
				try {
					DbConnection.closeResultset(rset);
					DbConnection.closePreparedStatement(pstmt);
					DbConnection.freeConnection(crmcon);
					DbConnection.freeConnection(iomsConn);
				} catch (Exception e) {
					System.out.println("exeption due to : " + e.getMessage());
				}
			}
			return	status;
	}
		/*   	********************************************************************************
	 *		Function Name:- ModifyECRMUserInfoInIOMS
	 *		Created By:-    Anil Kumar
	 * 		Created On:-    15-12-2010
	 * 		Purpose:-		To Get All the User Info and Update them in IOMS
	 *      ********************************************************************************
	 */ 

	public static void UpdateUserInfoInIOMS(Hashtable<String, String> htTrackDate)
	{
				PreparedStatement pstmt = null;
				ResultSet rset = null;
				DBConnectionRetriever con = new DBConnectionRetriever();
				Connection crmcon = null;
				
				Connection iomsConn = null;
				CallableStatement csIOMS = null;
				try {
					CRMLogger.SysErr("processpartyid()- query ::" + strGetECRMEmployeeWithRoleModify);
					crmcon = con.getCRMConnection();
					pstmt = crmcon.prepareStatement(strGetECRMEmployeeWithRoleModify);
					pstmt.setString(1,htTrackDate.get(AppConstants.TM_ACCOUNTROLEDETAILS).toString());
					rset = pstmt.executeQuery();
					ArrayList<ECRMUserInfoDto> lstUserInfo = new ArrayList<ECRMUserInfoDto>();
					ECRMUserInfoDto objDto = null; 
					while (rset.next()) 
					{
						objDto = new ECRMUserInfoDto();
						objDto.setRole_resource_id(rset.getString("role_resource_id"));//employeeid						
						objDto.setUserid(rset.getString("user_name"));
						objDto.setEmail(rset.getString("EMAIL_ADDRESS"));	
						objDto.setFirstName(rset.getString("first_name"));
						objDto.setLastName(rset.getString("last_name"));
						objDto.setPhoneNo(rset.getString("phone_no"));					
						lstUserInfo.add(objDto);
					}
														
					if(lstUserInfo.size()>0)
					{
						CRMLogger.SysErr("Connect with IB2B database");
						iomsConn = getConnectionObject();
						CRMLogger.SysErr("User Info fetched and stored in Array list");	
						csIOMS = iomsConn.prepareCall("UPDATE IOE.TM_ACCOUNTROLEDETAILS SET UPDATED_DATE= current timestamp,FIRSTNAME=?,LASTNAME=?,PHONE_NO=?,EMAILID=? WHERE EMPLOYEEID =?");
					
						for (Iterator<ECRMUserInfoDto> iter = lstUserInfo.iterator(); iter.hasNext();) 
						{
							iomsConn.setAutoCommit(false);	
							ECRMUserInfoDto element = (ECRMUserInfoDto) iter.next();											
							csIOMS.setString(1, element.getFirstName());
							csIOMS.setString(2, element.getLastName());	
							csIOMS.setString(3, element.getPhoneNo());	
							csIOMS.setString(4, element.getEmail());						
							csIOMS.setLong(5, Long.valueOf(element.getRole_resource_id()));						
							csIOMS.execute();
							iomsConn.commit();
						}
						SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");									
						UpdateIOMSDataTracker("update","TM_ACCOUNTROLEDETAILS",sdf.format(new Date()),sdf.format(new Date()));
						CRMLogger.SysErr("User Info Modified in IB2B database");
					}
					else{								
						CRMLogger.SysErr("........NO Data Found or Updation.....)");
					}
					System.out.println("Values assigned processPartyIds()");
				} catch (Exception e) {
					try {
						iomsConn.rollback();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					CRMLogger.SysErr("Error in method processPartyIds()"+ e.getStackTrace());
					CRMLogger.SysErr("Error in User Info while updating :=>"+e.getMessage());
				} finally {
					try {
						DbConnection.closeResultset(rset);
						DbConnection.closePreparedStatement(pstmt);
						DbConnection.closeCallableStatement(csIOMS);
						DbConnection.freeConnection(crmcon);
						DbConnection.freeConnection(iomsConn);
						} catch (Exception e) {
						CRMLogger.SysErr("exeption due to : " + e.getMessage());
					}
				}
	}
	
	public static boolean ModifyAccountInfoInIOMS1(Long partyId,
			String partyName,String phoneNo, Long accountMgrId,Long projectMgrId,Long collectionMgrId,
			String accountCategoryId, String verticalId, String lstNo , String cstNo,
			String m6Accountno,String accountId,String crmAccountNo,String m6ShortCode,String regionId,Long customerSegment,Long industrySegment,Long serviceSegment,String partyNo,String zoneId,int osp,String isFromGUI,String circle,String category,Long groupID,String groupDesc,String sfdcCustomerId) {

		Connection iomsConn = null;
		CallableStatement csIOMS = null;
		boolean isInserted = false;
		try {
			iomsConn = getConnectionObject();
			CRMLogger.SysErr("******************Inside Function....For Updating Account or Party in iB2B******************");
			csIOMS = iomsConn.prepareCall(spUpdateECRMAccountToIOMS);
			
 			int iColCount=0;
			csIOMS.setLong(++iColCount , Long.parseLong(accountId));
			csIOMS.setString(++iColCount, partyId.toString());
			csIOMS.setString(++iColCount , crmAccountNo);
			csIOMS.setString(++iColCount, m6Accountno);
			csIOMS.setString(++iColCount, lstNo);
			csIOMS.setString(++iColCount, cstNo);
			csIOMS.setString(++iColCount, accountCategoryId);
			csIOMS.setString(++iColCount, verticalId);
			csIOMS.setString(++iColCount, partyName);
			csIOMS.setString(++iColCount, phoneNo);
			csIOMS.setLong(++iColCount, accountMgrId);
			csIOMS.setLong(++iColCount, projectMgrId);
			csIOMS.setLong(++iColCount, collectionMgrId);
			csIOMS.setString(++iColCount, m6ShortCode);
			csIOMS.setString(++iColCount, regionId);
			csIOMS.setLong(++iColCount, industrySegment);
			csIOMS.setLong(++iColCount, customerSegment);
			csIOMS.setLong(++iColCount, serviceSegment);
			csIOMS.setString(++iColCount, partyNo);
			csIOMS.setInt(++iColCount, 0);
			csIOMS.setInt(++iColCount, 0);
			csIOMS.setString(++iColCount, "");
			csIOMS.setString(++iColCount, zoneId);
			csIOMS.setInt(++iColCount, osp);
			csIOMS.setString(++iColCount, circle);//Added on 9-jan-2013, for circle 
			csIOMS.setString(++iColCount, category);//Added on 30-apr-2013, for category
			csIOMS.setLong(++iColCount, groupID);//Added on 25-June-2013, for GroupName
			csIOMS.setString(++iColCount, groupDesc);
			csIOMS.setString(++iColCount, sfdcCustomerId);//Added on 26th September2016
			csIOMS.execute();
			
		
			
			if (csIOMS.getInt(21) == 0) {
				iomsConn.commit();
				if("N".equalsIgnoreCase(isFromGUI)){
					SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");									
					UpdateIOMSDataTracker("update","TM_ACCOUNT",sdf.format(new Date()),sdf.format(new Date()));
					isInserted = true;				
					CRMLogger.SysErr("------------ [Account Modification Status] ------------ ");
					CRMLogger.SysErr(" AccountId:["+accountId+"]"+csIOMS.getString(22));
				}else{
					isInserted = true;				
					CRMLogger.SysErr("------------ [Account Modification Status] ------------ ");
					CRMLogger.SysErr(" AccountId:["+accountId+"]"+csIOMS.getString(22));
				}
			} else {
				iomsConn.rollback();
				isInserted = false;
				CRMLogger.SysErr("******************Error While Modifying ******************" + accountId);
				CRMLogger.SysErr(csIOMS.getString(22));
			}

		} catch (Exception e) {
			CRMLogger.SysErr("Error[-2222]: in method ModifyAccountInfoInIOMS1()"
					+ e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				DbConnection.closeCallableStatement(csIOMS);
				DbConnection.freeConnection(iomsConn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return isInserted;
	}
	
	/*   	********************************************************************************
	 *		Function Name:- InsertUpdateProductDDValuesInIOMS
	 *		Created By:-    Saurabh Singh
	 * 		Created On:-    02-11-2011
	 * 		Purpose:-		To Insert And Update ProductDDValues In IOMS
	 *      ********************************************************************************
	 */ 
	
	public static void InsertUpdateProductDDValuesInIOMS(int stage,Hashtable<String, String> htTrackDate)
	{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		DBConnectionRetriever con = new DBConnectionRetriever();
		Connection crmcon = null;
		Connection iomsConn = null;
		CallableStatement csIOMS = null;
		try 
		{
			crmcon = con.getCRMConnection();
			if(stage==1)
			{
				System.out.println("ddvalues()- query ::" + strGetECRMProductDDValues);
				pstmt = crmcon.prepareStatement(strGetECRMProductDDValues);
			}
			else
			{
				System.out.println("ddvalues()- query ::" + strGetUpdateECRMProductDDValues);
				pstmt = crmcon.prepareStatement(strGetUpdateECRMProductDDValues);
			}
			pstmt.setString(1,htTrackDate.get(AppConstants.TPRODUCTDDVALUES).toString());
			rset = pstmt.executeQuery();
			ArrayList<ECRMProductDDValuesDto> lstUserInfo = new ArrayList<ECRMProductDDValuesDto>();
			ECRMProductDDValuesDto objDto = null; 
			while (rset.next()) 
			{
				objDto = new ECRMProductDDValuesDto();
				objDto.setProductName((rset.getString("ProductName")));
				objDto.setText(rset.getString("TEXT"));
				objDto.setLookUpCode(rset.getString("lookup_code"));
				objDto.setCreation_Date(rset.getString("CREATION_DATE"));
				objDto.setLast_Update_date(rset.getString("LAST_UPDATE_DATE"));
				objDto.setEnabledFlag(rset.getString("ENABLED_FLAG"));
				lstUserInfo.add(objDto);
			}
			if(lstUserInfo.size()>0)
			{
				System.err.println("Product DD Values fetched and stored in Array list");	
				System.out.println("Connect with IOMS database");					
				iomsConn = getConnectionObject();
				csIOMS = iomsConn.prepareCall(spInsertUpdateECRMProductDDValuesToIOMS);
				
				/*If isAllRecordInserted flag contain 'false' value that means some records have not inserted, 
				 * and in this way this method 'UpdateIOMSDataTracker' should not be call
				 */
				for (Iterator<ECRMProductDDValuesDto> iter = lstUserInfo.iterator(); iter.hasNext();) 
				{
					try {
						iomsConn.setAutoCommit(false);
						ECRMProductDDValuesDto element = (ECRMProductDDValuesDto) iter.next();
						csIOMS.setString(1, element.getText());
						csIOMS.setString(2, element.getLookUpCode());
						csIOMS.setInt(3, stage);
						csIOMS.setString(4, element.getProductName());//directly sending the value to proc. No need to convert here
						if(element.getEnabledFlag().equalsIgnoreCase("N"))
							csIOMS.setInt(5, 0);
						else
							csIOMS.setInt(5, 1);
						csIOMS.registerOutParameter(6, java.sql.Types.INTEGER);						
						csIOMS.registerOutParameter(7, java.sql.Types.VARCHAR);						
						csIOMS.registerOutParameter(8, java.sql.Types.VARCHAR);	
						csIOMS.registerOutParameter(9, java.sql.Types.VARCHAR);	
						csIOMS.execute();
						
						/*--Going to commit the transaction if no error found other wise transaction would be rollback--*/
						if (csIOMS.getInt(6) != 1) {
							iomsConn.commit();
						}else{
							iomsConn.rollback();
						}
					}catch(Exception ex){
						iomsConn.rollback();
						CRMLogger.SysErr("Exception occur during inserting the record in method InsertUpdateProductDDValuesInIOMS()" + ex.getStackTrace());
					}
				}
				SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");									
				if(stage==1){
					UpdateIOMSDataTracker("Insert",AppConstants.TPRODUCTDDVALUES,sdf.format(new Date()),sdf.format(new Date()));
				}else{
					UpdateIOMSDataTracker("Update",AppConstants.TPRODUCTDDVALUES,sdf.format(new Date()),sdf.format(new Date()));
				}
			} 
		} 
		catch (Exception e) 
		{
			System.out.println("Error in method InsertUpdateProductDDValuesInIOMS()" + e.getStackTrace());
			System.err.println("Error in User Info while inserting :=>"+e.getMessage());
		} 
		finally 
		{
			try 
			{
				DbConnection.closeResultset(rset);
				DbConnection.closePreparedStatement(pstmt);
				DbConnection.freeConnection(crmcon);
				DbConnection.freeConnection(iomsConn);
			} 
			catch (Exception e) 
			{
				System.out.println("exeption due to : " + e.getMessage());
			}
		}
	}
	
	//=========
	/*   	********************************************************************************
	 *		Function Name:- InsertGamInfoInIOMS
	 *		Created By:-    Lawkush 
	 * 		Created On:-    12-01-2012
	 * 		Purpose:-		To Get All the User info From ECRM and Update them in IOMS
	 *      ********************************************************************************
	 */ 
	public static void InsertGamInfoInIOMS(Hashtable<String, String> htTrackDate)
	{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		DBConnectionRetriever con = new DBConnectionRetriever();
		Connection crmcon = null;
		Connection iomsConn = null;
		CallableStatement csIOMS = null;
		try {
			crmcon = con.getCRMConnection();
			pstmt = crmcon.prepareStatement(strGetECRMGAMEmployeeWithRole);
			pstmt.setString(1,htTrackDate.get(AppConstants.TGAM_MASTER).toString());
			rset = pstmt.executeQuery();
			ArrayList<ECRMUserInfoDto> lstUserInfo = new ArrayList<ECRMUserInfoDto>();
			ECRMUserInfoDto objDto = null; 
			while (rset.next()) 
			{
				objDto = new ECRMUserInfoDto();
				objDto.setRole_resource_id(rset.getString("role_resource_id"));//employeeid
				objDto.setRoleid(rset.getString("role_id"));
				objDto.setUserid(rset.getString("user_name"));
				objDto.setEmail(rset.getString("EMAIL_ADDRESS"));	
				objDto.setFirstName(rset.getString("first_name"));
				objDto.setLastName(rset.getString("last_name"));
				objDto.setPhoneNo(rset.getString("phone_no"));
				objDto.setActiveStartDate(rset.getString("ACTIVE_DATE"));
				lstUserInfo.add(objDto);
			}
			if(lstUserInfo.size()>0 )
			{
				CRMLogger.SysErr("Total GAM Info Fetched And Stored In ArrayList:"+lstUserInfo.size());
				CRMLogger.SysErr("Connect with IB2B database");					
				iomsConn = getConnectionObject();
				csIOMS = iomsConn.prepareCall(spInsertECRMGAMInfoToIOMS);
						
				/*If isAllRecordInserted flag contain 'false' value that means some records have not inserted, 
				 * and in this way this method 'UpdateIOMSDataTracker' should not be call
				 */
				for (Iterator<ECRMUserInfoDto> iter = lstUserInfo.iterator(); iter.hasNext();) 
				{
					try {
						iomsConn.setAutoCommit(false);
						ECRMUserInfoDto element = (ECRMUserInfoDto) iter.next();
						csIOMS.setLong(1, new Long(element.getRoleid()));	
						csIOMS.setLong(2, new Long(element.getRole_resource_id()));
						csIOMS.setString(3, element.getEmail());
						csIOMS.setString(4, element.getUserid());
						csIOMS.setString(5, element.getFirstName());
						csIOMS.setString(6, element.getLastName());						
						csIOMS.setString(7, element.getPhoneNo());						
						csIOMS.setInt(8, 0);
						csIOMS.setInt(9, 0);
						csIOMS.setString(10, "");
						csIOMS.setString(11, element.getActiveStartDate());												
						csIOMS.execute();	
						
						/*--Going to commit the transaction if no error found other wise transaction would be rollback--*/
						if (csIOMS.getInt(8) == 0) {
							iomsConn.commit();
						}else{
							iomsConn.rollback();
							CRMLogger.SysErr("GAM Info Insertion failed...");
						}
					}catch(Exception ex){
						iomsConn.rollback();
						CRMLogger.SysErr("Exception occur during inserting the record in method InsertGamInfoInIOMS()"
								+ ex.getStackTrace());
					}
				}
				SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");									
				UpdateIOMSDataTracker("insert","TGAM_MASTER",sdf.format(new Date()),sdf.format(new Date()));
				CRMLogger.SysErr("GAM Info Inserted....and Date tracker updated successfully");
			}
		} catch (Exception e) {
			CRMLogger.SysErr("Error in method InsertGamInfoInIOMS()"
					+ e.getStackTrace());
			e.printStackTrace();
			CRMLogger.SysErr("Error in GAM Info:=>"+e.getMessage());
		} finally {
			try {
				DbConnection.closeResultset(rset);
				DbConnection.closePreparedStatement(pstmt);
				DbConnection.freeConnection(crmcon);
				DbConnection.freeConnection(iomsConn);
			} catch (Exception e) {
				CRMLogger.SysErr("exeption due to : " + e.getMessage());
			}
		}
	}

	/*   	********************************************************************************
	 *		Function Name:- updateGamInfoInIOMS
	 *		Created By:-    Lawkush 
	 * 		Created On:-    12-01-2012
	 * 		Purpose:-		To update the User info From ECRM and Update them in IOMS
	 *      ********************************************************************************
	 */ 
	public static void updateGamInfoInIOMS(Hashtable<String, String> htTrackDate)
	{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		DBConnectionRetriever con = new DBConnectionRetriever();
		Connection crmcon = null;
		Connection iomsConn = null;
		CallableStatement csIOMS = null;
		try {
			crmcon = con.getCRMConnection();
			pstmt = crmcon.prepareStatement(strUpdateECRMGAMEmployeeWithRole);
			pstmt.setString(1,htTrackDate.get(AppConstants.TGAM_MASTER).toString());
			rset = pstmt.executeQuery();
			ArrayList<ECRMUserInfoDto> lstUserInfo = new ArrayList<ECRMUserInfoDto>();
			ECRMUserInfoDto objDto = null; 
			while (rset.next()) 
			{
				objDto = new ECRMUserInfoDto();
				objDto.setRole_resource_id(rset.getString("role_resource_id"));//employeeid
				objDto.setRoleid(rset.getString("role_id"));
				objDto.setEndDate(rset.getString("END_DATE"));
				lstUserInfo.add(objDto);
			}
			if(lstUserInfo.size()>0 )
			{
				CRMLogger.SysErr("Total GAM Info Fetched And Stored In ArrayList:"+lstUserInfo.size());
				CRMLogger.SysErr("Connect with IB2B database");	
				iomsConn = getConnectionObject();
				csIOMS = iomsConn.prepareCall(spUpdateECRMGAMInfoToIOMS);
							
				/*If isAllRecordInserted flag contain 'false' value that means some records have not inserted, 
				 * and in this way this method 'UpdateIOMSDataTracker' should not be call
				 */
				for (Iterator<ECRMUserInfoDto> iter = lstUserInfo.iterator(); iter.hasNext();) 
				{
					try {
						iomsConn.setAutoCommit(false);
						ECRMUserInfoDto element = (ECRMUserInfoDto) iter.next();
						csIOMS.setLong(1, new Long(element.getRoleid()));	
						csIOMS.setLong(2, new Long(element.getRole_resource_id()));
						csIOMS.setString(3, element.getEndDate());						
						csIOMS.setInt(4, 0);
						csIOMS.setInt(5, 0);
						csIOMS.setString(6, "");
						csIOMS.execute();	
						
						/*--Going to commit the transaction if no error found other wise transaction would be rollback--*/
						if (csIOMS.getInt(4) == 0) {
							iomsConn.commit();
							CRMLogger.SysErr("GAM Info Updated");
						}else{
							iomsConn.rollback();
						}
					}catch(Exception ex){
						iomsConn.rollback();
						CRMLogger.SysErr("Exception occur during inserting the record in method updateGamInfoInIOMS()"
								+ ex.getStackTrace());
					}
				}
				SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");									
				UpdateIOMSDataTracker("update","TGAM_MASTER",sdf.format(new Date()),sdf.format(new Date()));
				CRMLogger.SysErr("GAM Info Updated....with date tracker");
			}
		} catch (Exception e) {
			CRMLogger.SysErr("Error in method updateGamInfoInIOMS()"
					+ e.getStackTrace());
			e.printStackTrace();
			CRMLogger.SysErr("Error in GAM Info:=>"+e.getMessage());
		} finally {
			try {
				DbConnection.closeResultset(rset);
				DbConnection.closePreparedStatement(pstmt);
				DbConnection.freeConnection(crmcon);
				DbConnection.freeConnection(iomsConn);
			} catch (Exception e) {
				CRMLogger.SysErr("exeption due to : " + e.getMessage());
			}
		}
	}

	/*   	********************************************************************************
	 *		Function Name:- insertECRMGAMQuotesInIOMS
	 *		Created By:-    LAWKUSH
	 * 		Created On:-    13-01-2012
	 * 		Purpose:-		To Get All the Quotes and Update them in IOMS
	 *      ********************************************************************************
	 */ 
	public static void insertECRMGAMQuotesInIOMS(Hashtable<String, String> htTrackDate)
	{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		DBConnectionRetriever con = new DBConnectionRetriever();
		Connection crmcon = null;
		Connection iomsConn = null;
		CallableStatement csIOMS = null;
		try {
			crmcon = con.getCRMConnection();
			CRMLogger.SysErr("Query For Getting Quotes::" + strGetWonQuotes);
			pstmt = crmcon.prepareStatement(strGetWonInsertGAMQuotes);
			pstmt.setString(1,htTrackDate.get(AppConstants.TGAM_QUOTES_MASTER).toString());
			rset = pstmt.executeQuery();
			ArrayList<ECRMQuoteDto> lstQuotes = new ArrayList<ECRMQuoteDto>();
			ECRMQuoteDto objDto = null; 
			while (rset.next()) 
			{
				objDto = new ECRMQuoteDto();
				objDto.setQuoteNo(rset.getString("QUOTE_NUMBER"));
				objDto.setLeadNo(rset.getString("LEAD_NUMBER"));
				objDto.setResourceId(rset.getInt("RESOURCE_ID"));
				lstQuotes.add(objDto);
			}
			if(lstQuotes.size()==0)
			{
				CRMLogger.SysErr("GAM  Quotes not Found");
			}
			if(lstQuotes.size()>0)
			{
				CRMLogger.SysErr("*******************Total GAM Quotes Fetched And Stored In ArrayList" + String.valueOf(lstQuotes.size()));
				CRMLogger.SysErr("Connect with IB2B database");
				iomsConn = getConnectionObject();
				csIOMS = iomsConn.prepareCall(spInsertECRMGAMQuotesToIOMS);
				/*If isAllRecordInserted flag contain 'false' value that means some records have not inserted, 
				 * and in this way this method 'UpdateIOMSDataTracker' should not be call
				 */
				for (Iterator<ECRMQuoteDto> iter = lstQuotes.iterator(); iter.hasNext();) 
				{
					try {
						iomsConn.setAutoCommit(false);
						ECRMQuoteDto element = (ECRMQuoteDto) iter.next();
						csIOMS.setString(1, element.getQuoteNo());
						csIOMS.setString(2, element.getLeadNo());
						csIOMS.setInt(3, element.getResourceId());
						csIOMS.setInt(4, 0);
						csIOMS.setInt(5, 0);
						csIOMS.setString(6, "");
						csIOMS.execute();
						CRMLogger.SysErr(csIOMS.getString(6));
						/*--Going to commit the transaction if no error found other wise transaction would be rollback--*/
						if (csIOMS.getInt(5) == 0) {
							iomsConn.commit();
						}else{
							iomsConn.rollback();
						}
					}catch(Exception ex){
						iomsConn.rollback();
						CRMLogger.SysErr("Exception occur during inserting the record in method insertECRMGAMQuotesInIOMS()"
								+ ex.getStackTrace());
					}	
				}
				SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");
				UpdateIOMSDataTracker("insert","TGAM_QUOTES_MASTER",sdf.format(new Date()),sdf.format(new Date()));
				CRMLogger.SysErr("GAM Quotes Inserted In IB2B Database For Selected Party and Date tracker updated successfully");
			}
		} catch (Exception e) {
			CRMLogger.SysErr("Error in method Quotes insertECRMGAMQuotesInIOMS()" + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				DbConnection.closeResultset(rset);
				DbConnection.closePreparedStatement(pstmt);
				DbConnection.freeConnection(crmcon);
				DbConnection.freeConnection(iomsConn);
			} catch (Exception e) {
				CRMLogger.SysErr("exeption due to : " + e.getMessage());
			}
		}
	}
	
	public static int UpdateSFOpportunityIdInIOMS()  
	{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		DBConnectionRetriever con = new DBConnectionRetriever();
		Connection crmcon = null;
		Connection iomsConn = null;
		CallableStatement csIOMS = null;
		int status=-1;
		try {
			crmcon = con.getCRMConnection();
				CRMLogger.SysErr("Query For Getting Quotes::" + strGetWonQuotes);
				pstmt = crmcon.prepareStatement( "SELECT NVL(ACCOUNT_NUMBER,' ') AS party_id, NVL(asl.lead_number,' ') AS lead_number, NVL(asl.attribute14,' ') AS salesforce_opportunityno "
						+ " FROM apps.hz_parties hz , APPS.HZ_CUST_ACCOUNTS HCA , apps.as_leads_all asl, apps.aso_quote_related_objects aso, apps.aso_quote_headers_all aqha"
						+ " WHERE asl.customer_id = hz.party_id AND HCA.PARTY_ID =  HZ.PARTY_ID "
						+ " AND aso.object_id(+) = asl.lead_id "
						+ " AND aso.QUOTE_OBJECT_ID = aqha.quote_header_id(+) "
						+ " AND EXISTS "
						+ " ( SELECT 1 "
						+ " FROM apps.as_lead_lines_all asll "
						+ " WHERE asll.lead_id = asl.lead_id " 
						+ " AND asll.attribute8 = 1 )");
			
			rset = pstmt.executeQuery();
			ArrayList<ECRMQuoteDto> lstQuotes = new ArrayList<ECRMQuoteDto>();
			ECRMQuoteDto objDto = null; 
			while (rset.next()) 
			{
				objDto = new ECRMQuoteDto();
				objDto.setPartyId(rset.getString("PARTY_ID"));
				objDto.setLeadNo(rset.getString("lead_number"));
				objDto.setQuoteNo(rset.getString("QUOTES_NO"));
				objDto.setSalesforceOpportunityNo(rset.getString("salesforce_opportunityno"));
				lstQuotes.add(objDto);
			}
			if(lstQuotes.size()==0)
			{
				CRMLogger.SysErr("Quotes not found");
			}
			if(lstQuotes.size()>0)
			{
				CRMLogger.SysErr("*******************Total records Fetched And Stored In ArrayList:" + String.valueOf(lstQuotes.size()));
				CRMLogger.SysErr("Connect with IOMS database ==>");
				iomsConn = getConnectionObject();
				csIOMS = iomsConn.prepareCall(spUpdateOpportunityNoToIOMS);
						
				/*If isAllRecordInserted flag contain 'false' value that means some records have not inserted, 
				 * and in this way this method 'UpdateIOMSDataTracker' should not be call
				 */
				int size=lstQuotes.size();
				boolean isAllRecordInserted = true;
				for (Iterator<ECRMQuoteDto> iter = lstQuotes.iterator(); iter.hasNext();) 
				{
					try {
						iomsConn.setAutoCommit(false);
						ECRMQuoteDto element = (ECRMQuoteDto) iter.next();
						//System.out.println(String.valueOf(lstQuotes.size())+":sizeof arraylist||remainig"+element. );
						csIOMS.setString(1, element.getPartyId());
						csIOMS.setString(2, element.getQuoteNo());
						csIOMS.setString(4, element.getLeadNo());
						csIOMS.setString(5, element.getSalesforceOpportunityNo());
						csIOMS.setInt(6, 0);
						csIOMS.setString(7, "");
						
						size=size-1;
						System.out.println("remainingggggggggggggg::::::::"+size);
						csIOMS.execute();
						
						CRMLogger.SysErr(csIOMS.getString(7));
						
						/*--Going to commit the transaction if no error found other wise transaction would be rollback--*/
						if (csIOMS.getInt(6) == 0) {
							iomsConn.commit();
						}else{
							iomsConn.rollback();
							isAllRecordInserted = false;
							CRMLogger.SysErr("Error Updating sf opp no In IB2B Database");
						}
					}catch(Exception ex){
						iomsConn.rollback();
						isAllRecordInserted = false;
						CRMLogger.SysErr("Exception occur during updating the record in method UpdateSFOpportunityIdInIOMS()" + ex.getStackTrace());
					}
				}
				
				 if (isAllRecordInserted) {
					status=1;
						CRMLogger.SysErr("Sf opp no updated succesfully for records having sf opp no as null");
				} else {
					status=-1;
					CRMLogger.SysErr("Some sf opp no. have not updated properly");
				}
			}
			else
			{
				CRMLogger.SysErr("No SF Opportunity Id present which is null:");
					status=-2;
			}
		} catch (Exception e) {
			CRMLogger.SysErr("Error in method UpdateSFOpportunityIdInIOMS()"
					+ e.getStackTrace());
			e.printStackTrace();
		} finally {
			try {
				DbConnection.closeResultset(rset);
				DbConnection.closePreparedStatement(pstmt);
				DbConnection.freeConnection(crmcon);
				DbConnection.freeConnection(iomsConn);
				} 
			catch (Exception e) {
				CRMLogger.SysErr("exeption due to : " + e.getMessage());
			}
		}
		return status;
	}
	
	public static int InsertUpdateOpportunityIdInIOMS(Hashtable<String, String> htTrackDate)
	{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		DBConnectionRetriever con = new DBConnectionRetriever();
		Connection crmcon = null;
		Connection iomsConn = null;
		CallableStatement csIOMS = null;
		int status=-1;
		try 
		{
			crmcon = con.getCRMConnection();
			CRMLogger.SysErr("opportunityId()- query ::" + strGetOpportunityId);
			pstmt = crmcon.prepareStatement(strGetOpportunityId);
			pstmt.setString(1,htTrackDate.get(AppConstants.TOPPORTUNITY_MASTER).toString());
			rset = pstmt.executeQuery();
			ArrayList<ECRMProductDDValuesDto> lstUserInfo = new ArrayList<ECRMProductDDValuesDto>();
			ECRMProductDDValuesDto objDto = null; 
			while (rset.next()) 
			{
				objDto = new ECRMProductDDValuesDto();
				objDto.setPartyId((rset.getString("Customer_id")));
				objDto.setOpportunityid(rset.getString("lead_number"));
				lstUserInfo.add(objDto);
			}
			if(lstUserInfo.size()>0)
			{
				CRMLogger.SysErr("Total OppotunityId fetched and stored in Array list:"+lstUserInfo.size());	
				CRMLogger.SysErr("Connect with IB2B database");					
				iomsConn = getConnectionObject();
				csIOMS = iomsConn.prepareCall(spInsertUpdateOpportunityIDToIOMS);
				for (Iterator<ECRMProductDDValuesDto> iter = lstUserInfo.iterator(); iter.hasNext();) 
				{
					iomsConn.setAutoCommit(false);
					ECRMProductDDValuesDto element = (ECRMProductDDValuesDto) iter.next();
					csIOMS.setString(1, element.getPartyId());
					csIOMS.setString(2, element.getOpportunityid());
					csIOMS.registerOutParameter(3, java.sql.Types.INTEGER);						
					csIOMS.registerOutParameter(4, java.sql.Types.VARCHAR);						
					csIOMS.registerOutParameter(5, java.sql.Types.VARCHAR);	
					csIOMS.registerOutParameter(6, java.sql.Types.VARCHAR);	
					csIOMS.execute();
					if (csIOMS.getInt(3) != 1) 
					{
						CRMLogger.SysErr("Opportunity Id Values Inserted with "+csIOMS.getInt(3));
						status=1;
						iomsConn.commit();
						CRMLogger.SysErr("Opportunity Id Values Inserted in IB2B database ");
					} 
					else 
					{
						CRMLogger.SysErr("Opportunity Id Values Insertion failed");
						status=-1;
						iomsConn.rollback();
					}
				}
				SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");									
				UpdateIOMSDataTracker("Insert",AppConstants.TOPPORTUNITY_MASTER,sdf.format(new Date()),sdf.format(new Date()));
				CRMLogger.SysErr("Opportunity Id Values Inserted in IB2B database and TTrack Table Updated");
			}
			else
			{
				CRMLogger.SysErr("No Opportunity Id present");
				status=-2;
				
			}
		} 
		catch (Exception e) 
		{
			CRMLogger.SysErr("Error in method InsertUpdateOpportunityIdInIOMS()" + e.getStackTrace());
			CRMLogger.SysErr("Error in InsertUpdateOpportunityIdInIOMS while inserting :=>"+e.getMessage());
		} 
		finally 
		{
			try 
			{
				DbConnection.closeResultset(rset);
				DbConnection.closePreparedStatement(pstmt);
				DbConnection.closeCallableStatement(csIOMS);
				DbConnection.freeConnection(crmcon);
				DbConnection.freeConnection(iomsConn);
			} 
			catch (Exception e) 
			{
				CRMLogger.SysErr("exeption due to : " + e.getMessage());
			}
		}
		return status;
	}
	
	public static void InsertSingleViewDataInCRM()
	{
				PreparedStatement pstmt = null;
				PreparedStatement pstmtLines = null;
				ResultSet rset = null;
				ResultSet rset1 = null;
				ResultSet rset2 = null;
				ResultSet rset3 = null;
				ResultSet rset4 = null;
				int variable=0;
				int len_pri=0;
				PreparedStatement pstmtForDetails = null;
				PreparedStatement psForDataInsertion  = null;
				ResultSet rsForDetails = null;
				DBConnectionRetriever con = new DBConnectionRetriever();
				Connection crmcon = con.getCRMConnection();
				HashMap<Integer,Integer> map=new HashMap<Integer,Integer>();
				HashMap<Integer,IB2BSingleViewDataDto> map_lines=new HashMap<Integer,IB2BSingleViewDataDto>();
				Integer commitstatus=0;
				String substr1=null;
				String substr2=null;
				String substr3=null;
				String substr4=null;
				String substr5=null;
				String substr6=null;
				String substr7=null;
				String substr8=null;
				Connection iomsConn = null;
				CallableStatement csIOMS = null;
				CallableStatement csIOMS1 = null;
				CallableStatement csIOMS2 = null;
				IB2BSingleViewDataDto objvalidate_returndto=null;
				Statement stmt=null;
				String strGetSingleViewDataforInsert;
				String strGetSingleViewDataforInsertForLines;
				String strGetSingleViewDataforInsertForDetails;
				ArrayList<IB2BSingleViewDataDto> lstUserInfo = new ArrayList<IB2BSingleViewDataDto>();
				IB2BSingleViewDataDto objDto = null;
				IB2BSingleViewDataDto objDto_Lines = null;
				IB2BSingleViewDataDto dto = null;
				IB2BSingleViewDataDto seqDto = null;
				IB2BSingleViewDataDto countDto = null;
				PreparedStatement ps = null;
				PreparedStatement ps1 = null;
				PreparedStatement ps2 = null;
				PreparedStatement psForStaging = null;
				ResultSet rsetForStaging =null;
				int interfaceId;
				StringBuilder tpName=new StringBuilder();
				HashSet<String> tpNameSet=new HashSet<String>();
				String isThirdParty=new String();
				try 
				{
					CRMLogger.SysErr("Connect with IB2B database");
					iomsConn = getConnectionObject();
					
					csIOMS=iomsConn.prepareCall("SELECT KEYVALUE FROM IOE.TM_APPCONFIG WHERE KEYNAME = 'SERVICETYPEIDS_SINGLEVIEW'");
					rset=csIOMS.executeQuery();
					String serviceTypeId = null;
					while(rset.next())
					{
						serviceTypeId = rset.getString("KEYVALUE");
					}
					
					rset.close();
					csIOMS.close();
					
					
					CallableStatement csIOMS_ConfigId=iomsConn.prepareCall("SELECT KEYVALUE FROM IOE.TM_APPCONFIG WHERE KEYNAME = 'CRM_SERVICE_CONFIG_ID_FOR_EXTERNALID'");
					ResultSet rset_ConfigId=csIOMS_ConfigId.executeQuery();
					String crmService_configIdForExternalId = null;
					while(rset_ConfigId.next())
					{
						crmService_configIdForExternalId = rset_ConfigId.getString("KEYVALUE");
					}
					rset_ConfigId.close();
					csIOMS_ConfigId.close();
					
					
					
					//order level data start
					try{
						strGetSingleViewDataforInsert= strGetSingleViewData+" and TPOSERVICEMASTER.SERVICETYPEID in ("+serviceTypeId+") order by TPOSERVICEMASTER.serviceid ";
						strGetSingleViewDataforInsert=strGetSingleViewDataforInsert.replaceAll("@@crmService_configIdForExternalId@@", crmService_configIdForExternalId);
						
						CRMLogger.SysErr("Single View- query ::" + strGetSingleViewDataforInsert);
						pstmt = iomsConn.prepareStatement(strGetSingleViewDataforInsert);
						rset = pstmt.executeQuery();
						while (rset.next()) 
						{
							objDto = new IB2BSingleViewDataDto();
							objDto.setCrmOrderId((rset.getInt("CRM_ORDER_ID")));
							objDto.setServiceListId(rset.getInt("SERVICE_LIST_ID"));
							objDto.setCircuitId(rset.getString("CIRCUIT_ID"));
							objDto.setCircuitDate(rset.getTimestamp("CIRCUIT_DATE"));
							objDto.setPartyId(rset.getInt("PARTY_ID"));
							objDto.setCustAccountId(rset.getInt("CUST_ACCOUNT_ID"));
							objDto.setIB2BProductId(rset.getInt("IB2B_PRODUCT_ID"));
							objDto.setCreationDate(rset.getDate("CREATION_DATE"));
							objDto.setOrderType(rset.getString("ORDERTYPE"));
							objDto.setFlag(rset.getString("FLAG"));
							objDto.setProductid(rset.getString("PRODUCT"));
							objDto.setSubproductid(rset.getString("SUBPRODUCT"));
							objDto.setServicetypeid(rset.getString("SERVICETYPEID"));
							lstUserInfo.add(objDto);
							if(map.containsKey(objDto.getServiceListId()))
							{
								
							}
							else
							{
								map.put(objDto.getServiceListId(), objDto.getServiceListId());
							}
							
						}//order level data end
						CRMLogger.SysErr(lstUserInfo.size()+" row read");
					}finally{
						rset.close();
						pstmt.close();
					}
					// 001 start
					try{
						strGetSingleViewDataforInsertForLines= strGetSingleViewData_LineLevel + " and TPOSERVICEMASTER.SERVICETYPEID in ("+serviceTypeId+") ";
						strGetSingleViewDataforInsertForLines=strGetSingleViewDataforInsertForLines.replaceAll("@@crmService_configIdForExternalId@@", crmService_configIdForExternalId);
						CRMLogger.SysErr("Single View- query ::" + strGetSingleViewDataforInsertForLines);
						pstmtLines = iomsConn.prepareStatement(strGetSingleViewDataforInsertForLines);
						rset = pstmtLines.executeQuery();
						ArrayList<IB2BSingleViewDataDto> lstLineInfo = new ArrayList<IB2BSingleViewDataDto>();
						int rows=0;
						while (rset.next()) 
						{
							rows++;
							objDto_Lines = new IB2BSingleViewDataDto();
							objDto_Lines.setServiceListId(rset.getInt("SERVICEID"));
							objDto_Lines.setServiceproductid(rset.getString("SERVICEPRODUCTID"));
							objDto_Lines.setServicedetailid(rset.getString("SERVICEDETAILID"));
							//location info start
							objDto_Lines.setLoc_primary(rset.getString("LOCTYPE_PRIMARY"));
							if("Customer Location".equals(rset.getString("LOCTYPE_PRIMARY")))
							{
								if (rset.getString("ADDRESS1_PRIMARY") != null && !"".equals(rset.getString("ADDRESS1_PRIMARY").trim()))
								{
									objDto_Lines.setAddress1_primary(rset.getString("ADDRESS1_PRIMARY"));
								}
								else
								{
									objDto_Lines.setAddress1_primary("NA");
								}
								if (rset.getString("ADDRESS2_PRIMARY") != null && !"".equals(rset.getString("ADDRESS2_PRIMARY").trim()))
								{
									objDto_Lines.setAddress2_primary(rset.getString("ADDRESS2_PRIMARY"));
								}
								else
								{
									objDto_Lines.setAddress2_primary("NA");
								}
								if (rset.getString("ADDRESS3_PRIMARY") != null && !"".equals(rset.getString("ADDRESS3_PRIMARY").trim()))
								{
									objDto_Lines.setAddress3_primary(rset.getString("ADDRESS3_PRIMARY"));
								}
								else
								{
									objDto_Lines.setAddress3_primary("NA");
								}
								if (rset.getString("ADDRESS4_PRIMARY") != null && !"".equals(rset.getString("ADDRESS4_PRIMARY").trim()))
								{
									objDto_Lines.setAddress4_primary(rset.getString("ADDRESS4_PRIMARY"));
								}
								else
								{
									objDto_Lines.setAddress4_primary("NA");
								}
								
								if (rset.getString("CITY_PRIMARY") != null && !"".equals(rset.getString("CITY_PRIMARY").trim()))
								{
									objDto_Lines.setCity_primary(rset.getString("CITY_PRIMARY"));
								}
								else
								{
									objDto_Lines.setCity_primary("Not Defined");
								}
								
								if (rset.getString("STATE_PRIMARY") != null && !"".equals(rset.getString("STATE_PRIMARY").trim()))
								{
									objDto_Lines.setState_primary(rset.getString("STATE_PRIMARY"));
								}
								else
								{
									objDto_Lines.setState_primary("Unkown");
								}
								
								if (rset.getString("COUNTRY_PRIMARY") != null && !"".equals(rset.getString("COUNTRY_PRIMARY").trim()))
								{
									objDto_Lines.setCountry_primary(rset.getString("COUNTRY_PRIMARY"));
								}
								else
								{
									objDto_Lines.setCountry_primary("India");
								}
								
								if (rset.getString("POSTALCODE_PRIMARY") != null && !"".equals(rset.getString("POSTALCODE_PRIMARY").trim()))
								{
									objDto_Lines.setPostal_primary(rset.getString("POSTALCODE_PRIMARY"));
								}
								else
								{
									objDto_Lines.setPostal_primary("666666");
								}
							}
							else
							{
								len_pri=rset.getString("ADDRESS_PRIMARY").length();
								if(len_pri<=240)
								{
									objDto_Lines.setAddress1_primary(rset.getString("ADDRESS_PRIMARY"));
									objDto_Lines.setAddress2_primary("NA");
									objDto_Lines.setAddress3_primary("NA");
									objDto_Lines.setAddress4_primary("NA");
								}
								else
								{
									substr1=rset.getString("ADDRESS_PRIMARY").substring(0, 240);
									objDto_Lines.setAddress1_primary(substr1);
									substr2=rset.getString("ADDRESS_PRIMARY").substring(240, len_pri);
									len_pri=substr2.length();
									if(len_pri<=240)
									{
										objDto_Lines.setAddress2_primary(substr2);
									}
									else
									{
										substr3=substr2.substring(0, 240);
										objDto_Lines.setAddress2_primary(substr3);
										substr4=substr2.substring(240, len_pri);
										len_pri=substr4.length();
										if(len_pri<=240)
										{
											objDto_Lines.setAddress3_primary(substr4);
										}
										else
										{
											substr5=substr4.substring(0, 240);
											objDto_Lines.setAddress3_primary(substr5);
											substr6=substr4.substring(240, len_pri);
											len_pri=substr6.length();
											if(len_pri<=240)
											{
												objDto_Lines.setAddress4_primary(substr6);
											}
											
											else
											{
												substr7=substr6.substring(0, 240);
												objDto_Lines.setAddress4_primary(substr7);
												substr8=substr6.substring(240, len_pri);
												len_pri=substr8.length();
												if(len_pri<=240)
												{
													//objDto_Lines.setAddress4_primary(substr8);
													
												}
												
											}
										}
									}
								}
								
								objDto_Lines.setCity_primary("Not Defined");
								objDto_Lines.setState_primary("Unkown");
								objDto_Lines.setCountry_primary("India");
								objDto_Lines.setPostal_primary("666666");
							}
							
							if("Customer Location".equals(rset.getString("LOCTYPE_SECONDARY")))
							{
								if (rset.getString("ADDRESS1_SECONDARY") != null && !"".equals(rset.getString("ADDRESS1_SECONDARY").trim()))
								{
									objDto_Lines.setAddress1_secndry(rset.getString("ADDRESS1_SECONDARY"));
								}
								else
								{
									objDto_Lines.setAddress1_secndry("NA");
								}
								
								if (rset.getString("ADDRESS2_SECONDARY") != null && !"".equals(rset.getString("ADDRESS2_SECONDARY").trim()))
								{
									objDto_Lines.setAddress2_secndry(rset.getString("ADDRESS2_SECONDARY"));
								}
								else
								{
									objDto_Lines.setAddress2_secndry("NA");
								}
								if (rset.getString("ADDRESS3_SECONDARY") != null && !"".equals(rset.getString("ADDRESS3_SECONDARY").trim()))
								{
									objDto_Lines.setAddress3_secndry(rset.getString("ADDRESS3_SECONDARY"));
								}
								else
								{
									objDto_Lines.setAddress3_secndry("NA");
								}
								if (rset.getString("ADDRESS4_SECONDARY") != null && !"".equals(rset.getString("ADDRESS4_SECONDARY").trim()))
								{
									objDto_Lines.setAddress4_secndry(rset.getString("ADDRESS4_SECONDARY"));
								}
								else
								{
									objDto_Lines.setAddress4_secndry("NA");
								}
								if (rset.getString("CITY_SECONDARY") != null && !"".equals(rset.getString("CITY_SECONDARY").trim()))
								{
									objDto_Lines.setCity_secndry(rset.getString("CITY_SECONDARY"));
								}
								else
								{
									objDto_Lines.setCity_secndry("Not Defined");
								}
								
								if (rset.getString("STATE_SECONDARY") != null && !"".equals(rset.getString("STATE_SECONDARY").trim()))
								{
									objDto_Lines.setState_secndry(rset.getString("STATE_SECONDARY"));
								}
								else
								{
									objDto_Lines.setState_secndry("Unkown");
								}
								
								if (rset.getString("COUNTRY_SECONDARY") != null && !"".equals(rset.getString("COUNTRY_SECONDARY").trim()))
								{
									objDto_Lines.setCountry_secndry(rset.getString("COUNTRY_SECONDARY"));
								}
								else
								{
									objDto_Lines.setCountry_secndry("India");
								}
								
								if (rset.getString("POSTALCODE_SECONDARY") != null && !"".equals(rset.getString("POSTALCODE_SECONDARY").trim()))
								{
									objDto_Lines.setPostal_secndry(rset.getString("POSTALCODE_SECONDARY"));
								}
								else
								{
									objDto_Lines.setPostal_secndry("666666");
								}
							}
							else
							{
								if(len_pri<=240)
								{
									objDto_Lines.setAddress1_secndry(rset.getString("ADDRESS_SECONDARY"));
									objDto_Lines.setAddress2_secndry("NA");
									objDto_Lines.setAddress3_secndry("NA");
									objDto_Lines.setAddress4_secndry("NA");
								}
								else
								{
									substr1=rset.getString("ADDRESS_SECONDARY").substring(0, 240);
									objDto_Lines.setAddress1_secndry(substr1);
									substr2=rset.getString("ADDRESS_SECONDARY").substring(240, len_pri);
									len_pri=substr2.length();
									if(len_pri<=240)
									{
										objDto_Lines.setAddress2_secndry(substr2);
									}
									else
									{
										substr3=substr2.substring(0, 240);
										objDto_Lines.setAddress2_secndry(substr3);
										substr4=substr2.substring(240, len_pri);
										len_pri=substr4.length();
										if(len_pri<=240)
										{
											objDto_Lines.setAddress3_secndry(substr4);
										}
										else
										{
											substr5=substr4.substring(0, 240);
											objDto_Lines.setAddress3_secndry(substr5);
											substr6=substr4.substring(240, len_pri);
											len_pri=substr6.length();
											if(len_pri<=240)
											{
												objDto_Lines.setAddress4_secndry(substr6);
											}
											else
											{
												substr7=substr6.substring(0, 240);
												objDto_Lines.setAddress4_secndry(substr7);
												substr8=substr6.substring(240, len_pri);
												len_pri=substr8.length();
												if(len_pri<=240)
												{
													//objDto_Lines.setAddress4_secndry(substr8);
													
												}
												
											}
										}
									}
								}	
									objDto_Lines.setCity_secndry("Not Defined");
									objDto_Lines.setState_secndry("Unkown");
									objDto_Lines.setCountry_secndry("India");
									objDto_Lines.setPostal_secndry("666666");
								
							}
							//location info end
							if("667".equals(objDto_Lines.getServicedetailid()))
							{
								if(rset.getString("VAST")!=null)
								{
									if("Please Select".equals(rset.getString("VAST")))	
									{
										objDto_Lines.setBandwidth(null);
									}
									else
									{
										String str=rset.getString("VAST");
										String[] cktvalue=str.split(" ");
										objDto_Lines.setBandwidth(cktvalue[0]);
									}
								}	
							}
							else
							{
								objDto_Lines.setBandwidth(rset.getString("BANDWIDTH"));
							}
							objDto_Lines.setBandwidth_uom(rset.getString("BANDWIDTH_UOM"));
							objDto_Lines.setUom_l3(rset.getString("UOM_L3"));
							objDto_Lines.setTpName(rset.getString("TP_NAME"));
							lstLineInfo.add(objDto_Lines);
							if(map_lines.containsKey(objDto_Lines.getServiceListId()))
							{
								dto=map_lines.get(objDto_Lines.getServiceListId());
								if(("25".equals(objDto_Lines.getServicedetailid())) || ("304".equals(objDto_Lines.getServicedetailid())))
										{
											if (rset.getString("BANDWIDTH") != null && !"".equals(rset.getString("BANDWIDTH")))
											{
													if(variable==0)
													{
														variable=Integer.parseInt((objDto_Lines.getBandwidth()));
													}
													else
													{
														
														variable=variable + Integer.parseInt(objDto_Lines.getBandwidth());
													}
												dto.setBandwidth(String.valueOf(variable));
												
											}
											
										}
								if(("100070".equals(objDto_Lines.getServicedetailid())) || ("100068".equals(objDto_Lines.getServicedetailid()))
										|| ("100115".equals(objDto_Lines.getServicedetailid())) || ("100093".equals(objDto_Lines.getServicedetailid()))
										|| ("100095".equals(objDto_Lines.getServicedetailid())) || ("100072".equals(objDto_Lines.getServicedetailid()))){
									if (rset.getString("TP_NAME") != null && !"".equals(rset.getString("TP_NAME")))
									{
										tpName=new StringBuilder();
										tpNameSet.add(objDto_Lines.getTpName());
										int count=1;
										int size=tpNameSet.size();
										 // create an iterator
									      Iterator<String> iterator = tpNameSet.iterator(); 
									      // check values
									      while (iterator.hasNext()){
									         tpName.append(iterator.next());
									         if(count!=size){
									        	 tpName.append(";");
									         }
									         count=count+1;
									      }

										dto.setTpName(tpName.toString());
										isThirdParty=AppConstants.ACTIVE;
										dto.setIsThirdParty(isThirdParty);
									}
								}else{
										isThirdParty=AppConstants.INACTIVE;
										dto.setIsThirdParty(isThirdParty);
								}
								
								map_lines.put(objDto_Lines.getServiceListId(), dto);
							}
							else
							{
								variable=0;
								tpNameSet=new HashSet<String>();
								if(("25".equals(objDto_Lines.getServicedetailid())) || ("304".equals(objDto_Lines.getServicedetailid())))
								{
									if (rset.getString("BANDWIDTH") != null && !"".equals(rset.getString("BANDWIDTH")))
									{
											if(variable==0)
											{
												variable=Integer.parseInt((objDto_Lines.getBandwidth()));
											}
											else
											{
												
												variable=variable + Integer.parseInt(objDto_Lines.getBandwidth());
											}
									}
									
								}
								if(("100070".equals(objDto_Lines.getServicedetailid())) || ("100068".equals(objDto_Lines.getServicedetailid()))
										|| ("100115".equals(objDto_Lines.getServicedetailid())) || ("100093".equals(objDto_Lines.getServicedetailid()))
										|| ("100095".equals(objDto_Lines.getServicedetailid())) || ("100072".equals(objDto_Lines.getServicedetailid()))){
									isThirdParty=AppConstants.ACTIVE;
								}else{
									isThirdParty=AppConstants.INACTIVE;
								}
								tpNameSet.add(objDto_Lines.getTpName());
								objDto_Lines.setIsThirdParty(isThirdParty);
								map_lines.put(objDto_Lines.getServiceListId(), objDto_Lines);
							}
							
						} // 001 end
						CRMLogger.SysErr(rows+" row read");
					}finally{
						rset.close();
						pstmtLines.close();
					}
					
					
					//Meenakshi start :
					csIOMS2=iomsConn.prepareCall("SELECT KEYVALUE FROM IOE.TM_APPCONFIG WHERE KEYNAME = 'SERVICEDETAILIDS_SINGLEVIEW1'");
					rset4=csIOMS2.executeQuery();
					String serviceDetailid1 = null;
					while(rset4.next())
					{
						serviceDetailid1 = rset4.getString("KEYVALUE");
					}
					rset4.close();
					csIOMS2.close();
					
					csIOMS1=iomsConn.prepareCall("SELECT KEYVALUE FROM IOE.TM_APPCONFIG WHERE KEYNAME = 'SERVICEDETAILIDS_SINGLEVIEW2'");
					rset3=csIOMS1.executeQuery();
					String serviceDetailid2 = null;
					while(rset3.next())
					{
						serviceDetailid2 = rset3.getString("KEYVALUE");
					}
					rset3.close();
					csIOMS1.close();

					
					
					strGetSingleViewDataforInsertForDetails= "select tsd_l3.SERVICEPRODUCTID,val.ATTVALUE, "+ 
														        " det.SERVICEDETDESCRIPTION,tsm.LOGICAL_SI_NO, tsm.CUSTOMER_LOGICAL_SI_NO, "+ 
														        " bill.FX_REDIRECTION_LSI as Redirected_LSI , "+
														        " case when (tsd_l3.SERVICEDETAILID = 901 OR tsd_l3.SERVICEDETAILID = 1018 OR tsd_l3.SERVICEDETAILID=100089) then  tsd_l3.VCS_BUNDLED_SERVICE  "+
														        " WHEN (tsd_l3.SERVICEDETAILID = 1013) then  (Select CASE WHEN ATTVALUE ='' THEN NULL ELSE  ATTVALUE END from ioe.TPRODUCTLINEATTVALUE where ATTMASTERID=3947 and SERVICEPRODUCTID=tsd_l3.SERVICEPRODUCTID) END as Linked_LSI, "+
														        " tdh.IS_DISCONNECTED  AS FLAG,  "+
														        " tsd_l3.FX_SI_ID as extenalID,  "+
														        " case when (tsd_l3.SERVICEDETAILID = 367 and tsd_l3.CONFIG_ID =1) then  des.EXTERNAL_ID_DESCRIPTION  "+
														                " when (tsd_l3.SERVICEDETAILID = 367 and (tsd_l3.CONFIG_ID is null or tsd_l3.CONFIG_ID =0)) then '' "+ 
														                " when (tsd_l3.SERVICEDETAILID = 1010 and (val.ATTVALUE=1)) then 'IRN No'  "+
														                " when (tsd_l3.SERVICEDETAILID = 1010 and (val.ATTVALUE=2)) then 'Toll Free No' "+ 
														                " else des.EXTERNAL_ID_DESCRIPTION end as EXTERNAL_ID_DESCRIPTION, "+ 
														         " 'N' as PROCESS_FLAG, "+
														         " rol.ID as  CREATED_BY, "+
														         " rol.ID as  UPDATED_BY , "+
														         " tsm.SERVICEID "+
														        " from ioe.tposervicemaster tsm "+ 
														        " inner join ioe.tpomaster mas on tsm.orderno = mas.orderno "+ 
														        " inner join ioe.TM_ACCOUNTROLEDETAILS rol on rol.id = mas.EMPLOYEEID "+ 
															" inner join ioe.TDISCONNECTION_HISTORY tdh on tdh.MAIN_SERVICEID=tsm.serviceid "+ 
														        " inner join ioe.TPOSERVICEDETAILS tsd_l3 on tdh.SERVICE_PRODUCT_ID = tsd_l3.SERVICEPRODUCTID and tsd_l3.PARENT_SERVICEPRODUCTID<>0 "+ 
														        " left outer join ioe.TBILLING_INFO bill on tsd_l3.SERVICEPRODUCTID = bill.SERVICEPRODUCTID  "+
														        " inner join ioe.TSERVICETYPEDETAIL det on det.SERVICEDETAILID = tsd_l3.SERVICEDETAILID  "+
														        " inner join ioe.TM_ENTERNAL_ID_DESC des on des.SERVICE_DETAIL_ID = tsd_l3.SERVICEDETAILID  "+
														        " left outer join  ioe.TPRODUCTLINEATTMASTER lineatt on lineatt.SERVICEDETAILID =tsd_l3.SERVICEDETAILID  and lineatt.ATTMASTERID = 3943 "+ 
														        " left outer join ioe.TPRODUCTLINEATTVALUE val on  lineatt.attmasterid=val.ATTMASTERID  and val.ATTMASTERID = 3943 and val.SERVICEPRODUCTID = tsd_l3.SERVICEPRODUCTID "+ 
														        " left outer join (select distinct t1.serviceid , 5 as exclude_service from ioe.tposervicemaster t1 "+
														        " inner join ioe.TPOSERVICEDETAILS t2 on t1.serviceid = t2.serviceid  "+
														                                " and t1.CKT_PUSHED_INTO_CRM = 'N'  "+
														                                " and t2.config_id in ("+crmService_configIdForExternalId+") "+ 
														                                 " and t2.fx_si_id is null) tab on tsm.serviceid = tab.serviceid "+
														         " where tsm.CKT_PUSHED_INTO_CRM ='N' and tab.exclude_service is null "+
														                " and ((tsd_l3.SERVICEDETAILID IN ("+serviceDetailid1+") and tsd_l3.config_id=1 ) or (tsd_l3.SERVICEDETAILID in("+serviceDetailid2+"))) "+
																" and ((tsm.M6_FX_PROGRESS_STATUS in('M6_START','M6_END-FX_BT_START','FX_BT_END','M6_SUCCESS','WAIT_ISP_M6_END','M6_FAILED','W_FX_ACC_FOR_M6','WAIT_L3MPLS_M6_END','WAIT_CC_M6_END')  "  +
																"  and mas.SUB_CHANGE_TYPE_ID not IN (3,4) ) or " +
															    " (mas.SUB_CHANGE_TYPE_ID IN (3,4)  and tsm.M6_FX_PROGRESS_STATUS='FX_BT_END' ))  ";
			;// " and tsm.ORDERNO in(3025171)   	";
                                                                	
					CRMLogger.SysErr("Single View- query ::" + strGetSingleViewDataforInsertForDetails);
					pstmtForDetails = iomsConn.prepareStatement(strGetSingleViewDataforInsertForDetails);
					rsForDetails = pstmtForDetails.executeQuery();
					ArrayList<IB2BSingleViewDataDto> orderDetails = new ArrayList<IB2BSingleViewDataDto>();
					IB2BSingleViewDataDto orderData;
					String seqForStaging= "SELECT XXIBM.xxibm_ib2b_stage_seq.NEXTVAL FROM DUAL";
					psForStaging = crmcon.prepareStatement(seqForStaging);
					int rows=0;
					while (rsForDetails.next())
					{
						rows++;
						orderData = new IB2BSingleViewDataDto();
						rsetForStaging=psForStaging.executeQuery();
						if(rsetForStaging.next())
						{
							orderData.setInterfaceId((rsetForStaging.getInt("NEXTVAL")));
						}
						rsetForStaging.close();
						
						orderData.setServiceListId((rsForDetails.getInt("SERVICEID")));
						orderData.setLogicalSI(rsForDetails.getString("LOGICAL_SI_NO"));
						orderData.setLineItemID(rsForDetails.getString("SERVICEPRODUCTID"));
						orderData.setLineItemName(rsForDetails.getString("SERVICEDETDESCRIPTION"));
						if("0".equals(rsForDetails.getString("Redirected_LSI")))
						{
							orderData.setRedirected_LSI(null);
						}
						else
						{
							orderData.setRedirected_LSI(rsForDetails.getString("Redirected_LSI"));
						}
						orderData.setLinked_LSI(rsForDetails.getString("Linked_LSI"));
						if("1".equals(rsForDetails.getString("FLAG")))
						{
							orderData.setFlag("Disconnected");
						}
						else
						{
							orderData.setFlag("Active");
						}
						
						orderData.setExternalID(rsForDetails.getString("extenalID"));
						
						orderData.setExternalIDDesc(rsForDetails.getString("EXTERNAL_ID_DESCRIPTION"));
						orderData.setProcessFlag(rsForDetails.getString("PROCESS_FLAG"));
						orderData.setCreatedby(rsForDetails.getString("CREATED_BY"));
						orderData.setCustomerLogicalSI(rsForDetails.getString("CUSTOMER_LOGICAL_SI_NO"));
						orderData.setCreatedby(rsForDetails.getString("UPDATED_BY"));
						if(map.containsKey(orderData.getServiceListId()))
						{
								orderDetails.add(orderData);
						}
					}
					CRMLogger.SysErr(rows+" row read");
					rsForDetails.close();
					pstmtForDetails.close();
					
					// For insertion
					String queryForDetails = "insert into xxibm.xxibm_ib2b_staging (no,lsi_no,redirected_lsi_no,linked_lsi_no,line_no,line_name,status,external_id,EXTERNAL_ID_DESC,PROCESS_FLAG,CREATED_BY,clsi,LAST_UPDATED_BY) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
					psForDataInsertion = crmcon.prepareStatement(queryForDetails);
					for(int i=0;i<orderDetails.size();i++)
					{
						psForDataInsertion.setInt(1,orderDetails.get(i).getInterfaceId());
						psForDataInsertion.setString(2,orderDetails.get(i).getLogicalSI());
						psForDataInsertion.setString(3,orderDetails.get(i).getRedirected_LSI());
						psForDataInsertion.setString(4,orderDetails.get(i).getLinked_LSI());
						psForDataInsertion.setString(5, orderDetails.get(i).getLineItemID());
						psForDataInsertion.setString(6,orderDetails.get(i).getLineItemName());
						psForDataInsertion.setString(7,orderDetails.get(i).getFlag());
						psForDataInsertion.setString(8, orderDetails.get(i).getExternalID());
						psForDataInsertion.setString(9, orderDetails.get(i).getExternalIDDesc());
						psForDataInsertion.setString(10, orderDetails.get(i).getProcessFlag());
						psForDataInsertion.setString(11, orderDetails.get(i).getCreatedby());
						psForDataInsertion.setString(12, orderDetails.get(i).getCustomerLogicalSI());
						psForDataInsertion.setString(13, orderDetails.get(i).getCreatedby());
						psForDataInsertion.addBatch();
					}
					
					 int[] insertCounts = psForDataInsertion.executeBatch();
					 if(insertCounts.length != orderDetails.size() )
					 {
						 crmcon.rollback();
						 //return;
					 } else
					 {
						 commitstatus=1;
					 }
					//Meenakshi : End
					
					if(lstUserInfo.size()>0 && commitstatus==1)
					{
						CRMLogger.SysErr("SingleView fetched and stored in Array list");	
						crmcon.setAutoCommit(false);
						iomsConn.setAutoCommit(false);
						String seq= "SELECT XXIBM.XXIBM_IB2B_INTERFACE_SEQ.NEXTVAL FROM DUAL";
						ps1 = crmcon.prepareStatement(seq);
						String query = "INSERT INTO XXIBM.XXIBM_IB2B_INTERFACE(CRM_ORDER_ID, SERVICE_LIST_ID, CIRCUIT_ID, CIRCUIT_DATE, PARTY_ID, CUST_ACCOUNT_ID, IB2B_PRODUCT_ID, PROCESS_FLAG,INTERFACE_ID,CREATION_DATE,ORDER_TYPE,FLAG_YN,PRODUCT,SUB_PRODUCT,BANDWIDTH,UOM,INSTALL_ADDRESS_A1,INSTALL_ADDRESS_A2,INSTALL_ADDRESS_A3,INSTALL_ADDRESS_A4,CITY_A,STATE_A,PIN_CODE_A,COUNTRY_A,INSTALL_ADDRESS_B1,INSTALL_ADDRESS_B2,INSTALL_ADDRESS_B3,INSTALL_ADDRESS_B4,CITY_B,STATE_B,PIN_CODE_B,COUNTRY_B,third_party_name,third_party_flag)VALUES(?, ?, ?, ?, ?, ? ,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";
						ps = crmcon.prepareStatement(query);
						int j=0;
						
						String countInterface="SELECT count(INTERFACE_ID) AS COUNT FROM XXIBM.XXIBM_IB2B_INTERFACE WHERE CRM_ORDER_ID=? and SERVICE_LIST_ID=? and CIRCUIT_ID=?";
						ps2= crmcon.prepareStatement(countInterface);
						
						String sqlUpdateCktPushFlagInIb2b=
							"UPDATE IOE.TPOSERVICEMASTER SET CKT_PUSHED_INTO_CRM='Y' WHERE SERVICEID=?";
						PreparedStatement pstmtUpdateCktPushFlagInIb2b=iomsConn.prepareStatement(sqlUpdateCktPushFlagInIb2b);
						boolean isBatch_pstmtUpdateCktPushFlagInIb2b=false;
						
						for(int i=0;i<lstUserInfo.size();i++)
						{
							//String count1= "SELECT count(INTERFACE_ID) AS COUNT FROM XXIBM.XXIBM_IB2B_INTERFACE WHERE CRM_ORDER_ID="+lstUserInfo.get(i).getCrmOrderId()+" and SERVICE_LIST_ID="+lstUserInfo.get(i).getServiceListId()+" and CIRCUIT_ID='"+lstUserInfo.get(i).getCircuitId()+"'";
							//ps2 = crmcon.prepareStatement(count1);
							ps2.setInt(1, lstUserInfo.get(i).getCrmOrderId());
							ps2.setInt(2, lstUserInfo.get(i).getServiceListId());
							ps2.setString(3, lstUserInfo.get(i).getCircuitId());
							
							rset1=ps2.executeQuery();
							if(rset1.next())
							{
								countDto = new IB2BSingleViewDataDto();
								countDto.setCount((rset1.getInt("COUNT")));
							}
							if (countDto.getCount()==0)
							{
								rset2=ps1.executeQuery();
								if(rset2.next())
								{
									seqDto = new IB2BSingleViewDataDto();
									seqDto.setInterfaceId((rset2.getInt("NEXTVAL")));
								}
								interfaceId=seqDto.getInterfaceId();
								ps.setInt(1,lstUserInfo.get(i).getCrmOrderId());
								ps.setInt(2,lstUserInfo.get(i).getServiceListId());
								ps.setString(3,lstUserInfo.get(i).getCircuitId());
								ps.setTimestamp(4, lstUserInfo.get(i).getCircuitDate());
								ps.setInt(5,lstUserInfo.get(i).getPartyId());
								ps.setInt(6,lstUserInfo.get(i).getCustAccountId());
								ps.setInt(7, lstUserInfo.get(i).getIB2BProductId());
								ps.setString(8,"N");
								ps.setInt(9, interfaceId);
								ps.setDate(10, lstUserInfo.get(i).getCreationDate());
								ps.setString(11, lstUserInfo.get(i).getOrderType());
								ps.setString(12, lstUserInfo.get(i).getFlag());
								ps.setString(13, lstUserInfo.get(i).getProductid());
								ps.setString(14, lstUserInfo.get(i).getSubproductid());
								// // 001 start
								boolean result_validate=map_lines.containsKey(lstUserInfo.get(i).getServiceListId());
					 		 	if(result_validate==true)
					 		 	{
					 		 		objvalidate_returndto=map_lines.get(lstUserInfo.get(i).getServiceListId());
									ps.setString(15, objvalidate_returndto.getBandwidth());
									if("222".equals(objvalidate_returndto.getServicedetailid()))
									{
										ps.setString(16, objvalidate_returndto.getUom_l3());
									}
									else
									{
										ps.setString(16, objvalidate_returndto.getBandwidth_uom());

									}
									ps.setString(17, objvalidate_returndto.getAddress1_primary());
									ps.setString(18, objvalidate_returndto.getAddress2_primary());
									ps.setString(19, objvalidate_returndto.getAddress3_primary());
									ps.setString(20, objvalidate_returndto.getAddress4_primary());
									ps.setString(21, objvalidate_returndto.getCity_primary());
									ps.setString(22, objvalidate_returndto.getState_primary());
									ps.setString(23, objvalidate_returndto.getPostal_primary());
									ps.setString(24, objvalidate_returndto.getCountry_primary());
									ps.setString(25, objvalidate_returndto.getAddress1_secndry());
									ps.setString(26, objvalidate_returndto.getAddress2_secndry());
									ps.setString(27, objvalidate_returndto.getAddress3_secndry());
									ps.setString(28, objvalidate_returndto.getAddress4_secndry());
									ps.setString(29, objvalidate_returndto.getCity_secndry());
									ps.setString(30, objvalidate_returndto.getState_secndry());
									ps.setString(31, objvalidate_returndto.getPostal_secndry());
									ps.setString(32, objvalidate_returndto.getCountry_secndry());
									ps.setString(33, objvalidate_returndto.getTpName());
									if(objvalidate_returndto.getIsThirdParty()==null){
										ps.setString(34, AppConstants.INACTIVE);
									}else{
										ps.setString(34, objvalidate_returndto.getIsThirdParty());
									}
					 		 	}
					 		 	else
					 		 	{
					 		 		ps.setString(15, null);
					 		 		ps.setString(16,null);
					 		 		ps.setString(17,null);
					 		 		ps.setString(18,null);
					 		 		ps.setString(19,null);
					 		 		ps.setString(20,null);
					 		 		ps.setString(21,null);
					 		 		ps.setString(22,null);
					 		 		ps.setString(23,null);
					 		 		ps.setString(24,null);
					 		 		ps.setString(25,null);
					 		 		ps.setString(26,null);
					 		 		ps.setString(27,null);
					 		 		ps.setString(28,null);
					 		 		ps.setString(29,null);
					 		 		ps.setString(30,null);
					 		 		ps.setString(31,null);
					 		 		ps.setString(32,null);
					 		 		ps.setString(33,null);
					 		 		ps.setString(34,AppConstants.INACTIVE);
					 		 	} // 001 end
								ps.addBatch();
								j++;
								rset2.close();
								CRMLogger.SysErr("Service Details inserted in CRM : "+lstUserInfo.get(i).getServiceListId());
								//String update= "UPDATE IOE.TPOSERVICEMASTER SET CKT_PUSHED_INTO_CRM='Y' WHERE SERVICEID="+lstUserInfo.get(i).getServiceListId();
								//stmt = iomsConn.createStatement();
								//stmt.executeUpdate(update);
								pstmtUpdateCktPushFlagInIb2b.setInt(1, lstUserInfo.get(i).getServiceListId());
								pstmtUpdateCktPushFlagInIb2b.addBatch();
								isBatch_pstmtUpdateCktPushFlagInIb2b=true;
							}
							else
							{
								/*String update= "UPDATE IOE.TPOSERVICEMASTER SET CKT_PUSHED_INTO_CRM='Y' WHERE SERVICEID="+lstUserInfo.get(i).getServiceListId();
								stmt = iomsConn.createStatement();
								stmt.executeUpdate(update);*/
								pstmtUpdateCktPushFlagInIb2b.setInt(1, lstUserInfo.get(i).getServiceListId());
								pstmtUpdateCktPushFlagInIb2b.addBatch();
								isBatch_pstmtUpdateCktPushFlagInIb2b=true;
							}
							rset1.close();
							
						}
						ps2.close();
						ps1.close();
						int[] updateCounts = ps.executeBatch();
						CRMLogger.SysErr("Length:" + updateCounts.length);
						if(isBatch_pstmtUpdateCktPushFlagInIb2b){
							pstmtUpdateCktPushFlagInIb2b.executeBatch();
							pstmtUpdateCktPushFlagInIb2b.close();
						}
						
						if (updateCounts.length == j) 
						{
							crmcon.commit();
							commitstatus=0;
							iomsConn.commit();
							CRMLogger.SysErr("Single View Values Inserted in IB2B database");
						} 
						else 
						{
							crmcon.rollback();
							iomsConn.rollback();
						}
					}
					ps.close();
					if(commitstatus==1)
					 {
						crmcon.commit();
					 }
				} 
				catch (Exception e) 
				{
					CRMLogger.SysErr("Error in method InsertUpdateSingleViewDataInCRM()"
							+ e.getStackTrace());
					e.printStackTrace();
					CRMLogger.SysErr("Error in User Info while inserting :=>"+e.getMessage());
				} 
				finally 
				{
					try 
					{
						try {rsForDetails.close();}catch(Exception ex){ex.printStackTrace();}
						try {rset.close();}catch(Exception ex){ex.printStackTrace();}
						try {pstmt.close();}catch(Exception ex){ex.printStackTrace();}
						try {pstmtForDetails.close();}catch(Exception ex){ex.printStackTrace();}
						try {psForDataInsertion.close();}catch(Exception ex){ex.printStackTrace();}
						try {DbConnection.freeConnection(iomsConn);}catch(Exception ex){ex.printStackTrace();}
						try {DbConnection.freeConnection(crmcon);}catch(Exception ex){ex.printStackTrace();}
					} 
					catch (Exception e) 
					{
						CRMLogger.SysErr("exeption due to : " + e.getMessage());
					}
				}
	}
	
	public static void UpdateSingleViewDataInCRM()
	{
				PreparedStatement pstmt = null;
				ResultSet rset = null;
				ResultSet rset1 = null;
				DBConnectionRetriever con = new DBConnectionRetriever();
				Connection crmcon = con.getCRMConnection();
				Connection iomsConn = null;
				
				CallableStatement csIOMS = null;
				Statement stmt=null;
				try 
				{
					CRMLogger.SysErr("Connect with IB2B database");					
					iomsConn = getConnectionObject();
					
					csIOMS=iomsConn.prepareCall("SELECT KEYVALUE FROM IOE.TM_APPCONFIG WHERE KEYNAME = 'SERVICETYPEIDS_SINGLEVIEW'");
					rset=csIOMS.executeQuery();
					CRMLogger.SysErr("Single View- query ::" + strUpdateSingleViewDate);
					
					pstmt = iomsConn.prepareStatement(strUpdateSingleViewDate);
					rset = pstmt.executeQuery();
					
					ArrayList<IB2BSingleViewDataDto> lstUserInfo = new ArrayList<IB2BSingleViewDataDto>();
					IB2BSingleViewDataDto objDto = null;
					IB2BSingleViewDataDto countDto = null;
					
					PreparedStatement ps2 = null;
					PreparedStatement ps3 = null;
					
					while (rset.next()) 
					{
						objDto = new IB2BSingleViewDataDto();
						objDto.setBillingTriggerDate((rset.getDate("CIRCUIT_START_DATE")));
						objDto.setCircuitContractPeriod(rset.getInt("CIRCUIT_CONTRACTPERIOD"));
						objDto.setCircuitEndDate(rset.getDate("CIRCUIT_END_DATE"));
						objDto.setCrmOrderId(rset.getInt("ORDERNO"));
						objDto.setCircuitId(rset.getString("LOGICAL_SI_NO"));
						objDto.setServiceListId(rset.getInt("SERVICEID"));
						
						lstUserInfo.add(objDto);
					}
					
														
					CRMLogger.SysErr("Inside SingleView Function....");
					if(lstUserInfo.size()>0)
					{
						CRMLogger.SysErr("SingleView fetched and stored in Array list");	
						crmcon.setAutoCommit(false);
						iomsConn.setAutoCommit(false);
						
						int j=0;
						
						for(int i=0;i<lstUserInfo.size();i++)
							{
							
							
							String count1= "SELECT count(INTERFACE_ID) AS COUNT FROM XXIBM.XXIBM_IB2B_INTERFACE WHERE CRM_ORDER_ID="+lstUserInfo.get(i).getCrmOrderId()+" and CIRCUIT_START_DATE is null and CIRCUIT_ID='"+lstUserInfo.get(i).getCircuitId()+"'";
							ps2 = crmcon.prepareStatement(count1);
							rset1=ps2.executeQuery();
							if(rset1.next())
								{
									countDto = new IB2BSingleViewDataDto();
									countDto.setCount((rset1.getInt("COUNT")));
								}
							
							if (countDto.getCount()==1)
									{
									String updateQuery = "UPDATE XXIBM.XXIBM_IB2B_INTERFACE SET CIRCUIT_START_DATE=TO_DATE('"+lstUserInfo.get(i).getBillingTriggerDate()+"','YYYY-MM-DD'), CIRCUIT_END_DATE=TO_DATE('"+lstUserInfo.get(i).getCircuitEndDate()+"','YYYY-MM-DD') WHERE CIRCUIT_ID='"+lstUserInfo.get(i).getCircuitId()+"' AND CIRCUIT_START_DATE is null";
									ps3 = crmcon.prepareStatement(updateQuery);
									ps3.executeUpdate(updateQuery);
									CRMLogger.SysErr("Service Details updated in CRM : "+lstUserInfo.get(i).getServiceListId());
									String insert= "INSERT INTO IOE.CIRCUIT_ORDER_INSERTION(ORDERNO,PROCESSED) SELECT ORDERNO,CASE WHEN ORDERTYPE='New' THEN 'Y' ELSE 'N' END As PROCESSED  FROM IOE.TPOMASTER where ORDERNO="+lstUserInfo.get(i).getCrmOrderId();
									stmt = iomsConn.createStatement();
									stmt.execute(insert);
									j++;
									}
								
							
						
							}
						
					      //int[] updateCounts = ps.executeBatch();
					     // System.out.println("Length:" + updateCounts.length);
					      
					      //System.out.println(ps.getUpdateCount()+"---> Size of PS");
						if (j>0) 
						{
							crmcon.commit();
							iomsConn.commit();
							//SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");									
							//UpdateIOMSDataTracker("Insert","SINGLEVIEW",sdf.format(new Date()),sdf.format(new Date()));
							CRMLogger.SysErr("Single View Values Updated in IB2B database");
						} 
						else 
						{
							crmcon.rollback();
							iomsConn.rollback();
						}
					}
				} 
				catch (Exception e) 
				{
					CRMLogger.SysErr("Error in method UpdateSingleViewDataInCRM()"
							+ e.getStackTrace());
					e.printStackTrace();
					CRMLogger.SysErr("Error in UpdateSingleViewDataInCRM inserting :=>"+e.getMessage());
				} 
				finally 
				{
					try 
					{
						try {pstmt.close();}catch(Exception ex){ex.printStackTrace();}
						try {rset.close();}catch(Exception ex){ex.printStackTrace();}
						try {DbConnection.freeConnection(iomsConn);}catch(Exception ex){ex.printStackTrace();}
					} 
					catch (Exception e) 
					{
						CRMLogger.SysErr("exeption due to : " + e.getMessage());
					}
				}
	}
	//========================================================================================================
	/*Function : fetchAccountFromCRM()
	 * Purpose : insert or update account information from crm to ib2b on user request from GUI.
	 * Error   : Error Message Description
	 * -1111 : No Such Account found in CRM.
	 * -2222 : Some Err has been occured.
	 * 
	 * */
	//========================================================================================================
	public static String  fetchAccountFromCRM(Connection crmcon, long partyID,String purpose,String crmAccNo) throws Exception {
		CallableStatement csIOMS = null;
		ResultSet rset = null;
		int accountSyncErrCode=0;
		String statusMsg="";
		if(crmAccNo !=null && !"".equalsIgnoreCase(crmAccNo)){
			crmAccNo=crmAccNo.trim();			
		}
		String querystr = getQueryStringUpdatingAccount()	+" AND ACCOUNT_NUMBER='"+crmAccNo+"'";
		CRMLogger.SysErr("--Account Fetching Query -->"+querystr);
		PreparedStatement pstmt = null;		
		try {			
			if(purpose.equalsIgnoreCase("inserting")||purpose.equalsIgnoreCase("insert"))
			{
				pstmt = crmcon.prepareStatement(querystr);				
			}else{
				pstmt = crmcon.prepareStatement(querystr);				
			}
			rset = pstmt.executeQuery();
			int i = 0;
			while (rset.next()) {
				i = 1;
				String crmAccountNo=rset.getString("CRM_ACCOUNTNO");
				Long partyId = new Long(rset.getString("partyId"));
				String partyName=rset.getString("partyName");
				String phoneNo=rset.getString("phoneNo");
				
				//----------------------------------------------------------------------------------
					//CRM Region Validation in CRM
				//----------------------------------------------------------------------------------
				String regionId=rset.getString("regionid");
				if("".equalsIgnoreCase(regionId)|| regionId==null){
					accountSyncErrCode=-3232;
					statusMsg="Error[-3232]: Region not found with account in CRM";
					throw new IOESException("------ Error: Region not found with account in CRM -----------");
				}
				//----------------------------------------------------------------------------------
									//Account Manager and Project Manager Validation in CRM
				//----------------------------------------------------------------------------------
				Long accountMgrId = new Long(rset.getLong("accountMgrId"));
				Long projectMgrId = Long.valueOf(rset.getLong("projectmgrid"));
				
				if(accountMgrId == 0 && projectMgrId == 0){
					accountSyncErrCode=-3333;
					statusMsg="Error[-3333]: Account Manager and Project Manager not found with account in CRM";
					throw new IOESException("------ Error: Account Manager and Project Manager not found with account in CRM -----------");
				}else if(accountMgrId == 0 ){
					accountSyncErrCode=-4444;
					statusMsg="Error[-4444]: Account Manager not found with account in CRM";
					throw new IOESException("------ Error: Account Manager not found with account in CRM -----------");
				}else if(projectMgrId == 0){
					accountSyncErrCode=-5555;
					statusMsg="Error[-5555]: Project Manager not found with account in CRM";
					throw new IOESException("------ Error: Project Manager not found with account in CRM -----------");
				}					
				//----------------------------------------------------------------------------------
									//FX and M6 Completion Flag Validation
				//----------------------------------------------------------------------------------
				String fx_account_complete_flag=rset.getString("FX_ACCOUNT_COMP_FLAG");
				String m6_account_complete_flag=rset.getString("M6_ACCOUNT_COMP_FLAG");
					
				if(!"Completed".equalsIgnoreCase(fx_account_complete_flag) && !"Completed".equalsIgnoreCase(m6_account_complete_flag)){
					accountSyncErrCode=-6666;
					statusMsg="Error[-6666]: Account is not completed in FX and M6";
					throw new IOESException("------ Error: Account is not completed in FX and M6 -----------");
				}
				else if(!"Completed".equalsIgnoreCase(fx_account_complete_flag)){
					accountSyncErrCode=-7777;
					statusMsg="Error[-7777]: Account is not completed in FX";
					throw new IOESException("------ Error: Account is not completed in FX  -----------");
				}else if(!"Completed".equalsIgnoreCase(m6_account_complete_flag)){
					accountSyncErrCode=-8888;
					statusMsg="Error[-8888]: Account is not completed in M6";
					throw new IOESException("------ Error: Account is not completed in M6 -----------");
				}					
				//----------------------------------------------------------------------------------
									//Account Manager and Project Manager Validation in iB2B
				//----------------------------------------------------------------------------------
				int isAmFoundInIB2B=0,isPMFoundInIB2B=0;
				isAmFoundInIB2B=findAMResourceFromIb2b(String.valueOf(accountMgrId));
				isPMFoundInIB2B=findPMResourceFromIb2b(String.valueOf(projectMgrId));
				if(isAmFoundInIB2B == 0 && isPMFoundInIB2B == 0){
					accountSyncErrCode=-9999;
					statusMsg="Error[-9999]: Account Manager and Project Manager not found in IB2B";
					throw new IOESException("------ Error: Account Manager["+String.valueOf(accountMgrId)+"] and Project Manager["+String.valueOf(projectMgrId)+"] not found in IB2B -----------");
				}else if(isAmFoundInIB2B == 0){
					accountSyncErrCode=-9191;
					statusMsg="Error[-9191]: Account Manager is not found in IB2B";
					throw new IOESException("------ Error: Account Manager["+String.valueOf(accountMgrId)+"] is not found in IB2B -----------");
				}else if(isPMFoundInIB2B == 0){
					accountSyncErrCode=-9292;
					statusMsg="Error[-9292]: Project Manager is not found in IB2B";
					throw new IOESException("------ Error: Project Manager["+String.valueOf(projectMgrId)+"] is not found in IB2B -----------");
				}
				
				//----------------------------------------------------------------------------------
					
				String verticalId =rset.getString("VerticalId");
				Long customerSegment = new Long(rset.getLong("customerSegment"));
				String accountCategoryId = rset.getString("AccountCategory");
				String lstNo = rset.getString("LSTNO");
				String cstNo = rset.getString("CSTNO");
				long collectionMgrId;
				if(rset.getString("collectionMgrId")!=null)
				{
					collectionMgrId = Long.valueOf(rset.getString("collectionMgrId"));
				}
				else
				{
					collectionMgrId = 0;
				}
							
				String m6Accountno = rset.getString("M6AccountNo");
				String m6ShortCode=rset.getString("M6ShortCode");
				String accountId=rset.getString("accountId");
																													
				Long industrySegment=new Long(rset.getString("industrySegment"));
				Long serviceSegment=new Long(rset.getString("serviceSegment"));
				String zoneId=rset.getString("ZONEID");
				String partyNO="";
				if(rset.getString("PARTYNUMBER")!=null){
					 partyNO=rset.getString("PARTYNUMBER");
				}
			
				String osp=rset.getString("OSP");
				int ospvalue=0;
				if("N".equalsIgnoreCase(osp)){
					ospvalue=2;
				}else if("Y".equalsIgnoreCase(osp)){
					ospvalue=1;
				}else if("".equalsIgnoreCase(osp)){
					ospvalue=2;
				}
				String circle=rset.getString("CIRCLE");//Added on 9-jan-2013, for circle 
				
				String category = rset.getString("CATEGORY");//Added on 30-April-2013, for category
				Long groupID = rset.getLong("GROUPID");//Added on 25-June-2013, for Group Name
				String groupDesc = rset.getString("GROUP_DESC");//Added on 30-Sep-2013, for GROUP Name
				String sfdcCustomerId = rset.getString("SFDCCustomerId");//Added on 28-Sep-2016 for SFDC Customer Id (In Case it is not present Party Number will be stored)
									
				CRMLogger.SysErr("----- Calling iB2B Function .....");
				if(purpose.equalsIgnoreCase("inserting")||purpose.equalsIgnoreCase("insert"))
				{
					boolean iCheck = insertAccountInfoInIOMS1(partyId,
							partyName,phoneNo,accountMgrId,projectMgrId,collectionMgrId,
							accountCategoryId,verticalId, lstNo , cstNo,
							m6Accountno,accountId,crmAccountNo,m6ShortCode,regionId,customerSegment,industrySegment,serviceSegment,partyNO,zoneId,ospvalue,"Y",circle, category,groupID,groupDesc,sfdcCustomerId);
						
						if(iCheck==true){
							accountSyncErrCode=1;
							statusMsg="1";
						}else{
							accountSyncErrCode=-2222;
							statusMsg="Error[-2222]:Some error has occured In method fetchAccountFromCRM";
						}
				}
				else
				{
					boolean iCheck = ModifyAccountInfoInIOMS1(partyId,
							partyName,phoneNo,accountMgrId,projectMgrId,collectionMgrId,
							accountCategoryId,verticalId, lstNo , cstNo,
							m6Accountno,accountId,crmAccountNo,m6ShortCode,regionId,customerSegment,industrySegment,serviceSegment,partyNO,zoneId,ospvalue,"Y",circle, category,groupID,groupDesc,sfdcCustomerId);
					
					if(iCheck==true){
						accountSyncErrCode=1;
						statusMsg="1";
					}else{
						accountSyncErrCode=-2222;
						statusMsg="Error[-2222]:Some error has occured In method fetchAccountFromCRM";
					}
				}				
			}
			if (i == 0) {
				accountSyncErrCode=-1111;
				statusMsg="Error[-1111]: Requesting Account not found in CRM";
				throw new IOESException("------ Error: Requesting Account not found in CRM -----------");
			}			
						
		} catch(IOESException ioesexp){			
			CRMLogger.SysErr("Account Sync Error Code:[ "+accountSyncErrCode+"] "+ioesexp.getMessage());			
			return statusMsg;
		}
		catch (Exception e) {
			accountSyncErrCode=-2222;		
			statusMsg="Error[-2222]:Some error has occured In method fetchAccountFromCRM()";
			CRMLogger.SysErr("In method fetchAccountFromCRM() Account Sync Error Code:[ "+accountSyncErrCode+"] "+e.getMessage());
			e.printStackTrace();
			return statusMsg;
		}
		finally {
			try {
				DbConnection.closeResultset(rset);
				DbConnection.closePreparedStatement(pstmt);
				DbConnection.closeCallableStatement(csIOMS);
			} catch (Exception e) {
				accountSyncErrCode=-2222;		
				statusMsg="Error[-2222]:Some error has occured In method fetchAccountFromCRM()";
				CRMLogger.SysErr("Account Sync Error Code:[ "+accountSyncErrCode+"] "+e.getMessage());
			}
		}
		return statusMsg;
	}
	
	public static String fetchBCPAddressFromCRM(String accountid,int insert_update,String requestSource)
	{
				PreparedStatement pstmt = null;
				ResultSet rset = null;				
				Connection iomsConn = null;
				CallableStatement csIOMS = null;
				String statusMsg="";
				DBConnectionRetriever con = new DBConnectionRetriever();
				String strQry = "";
				Connection crmcon = null;
				if(AppConstants.FETCH_LEVEL_GUI.equalsIgnoreCase(requestSource)){
					CRMLogger.SysErr("--Execute BCP Query CRM=> "+strGetECRMBCPAddress);
				}else{
					CLEPUtility.SysErr("--Execute BCP Query CLEP=> "+strGetECRMBCPAddress);
				}
				try {
					TransactionBatch.getLatestDateForTable(htCRMLastInsertedValue,htCRMLastUpdatedValue);
					crmcon = con.getCRMConnection();
					
					
					strQry = strGetECRMBCPAddress +" and CUST_ACCOUNT_ID=?";
					if(requestSource.equalsIgnoreCase(AppConstants.FETCH_LEVEL_GUI) && insert_update==1)
						strQry+=" AND CREATION_DATE BETWEEN TO_DATE(?) AND SYSDATE";
					if(requestSource.equalsIgnoreCase(AppConstants.FETCH_LEVEL_GUI) && insert_update==2)
						strQry+= " AND LAST_UPDATE_DATE BETWEEN TO_DATE(?) AND SYSDATE";

					pstmt = crmcon.prepareStatement(strQry);
					pstmt.setString(1, accountid);
					
					if(requestSource.equalsIgnoreCase(AppConstants.FETCH_LEVEL_GUI) && insert_update ==1)
						pstmt.setString(2, htCRMLastInsertedValue.get(AppConstants.BCP_ADDRESS).toString());
					if(requestSource.equalsIgnoreCase(AppConstants.FETCH_LEVEL_GUI) && insert_update ==2)
						pstmt.setString(2, htCRMLastUpdatedValue.get(AppConstants.BCP_ADDRESS).toString());
																									
					rset = pstmt.executeQuery();
					ArrayList<ECRMBCPAddressDto> lstBcpAddress = new ArrayList<ECRMBCPAddressDto>();
					ECRMBCPAddressDto objDto = null; 
					
					while (rset.next()) 
					{
						objDto = new ECRMBCPAddressDto();						
						objDto.setAddressId(rset.getLong("ADDRESS_ID"));
						objDto.setCustAccountId(rset.getLong("CUST_ACCOUNT_ID"));
						objDto.setCountryId(rset.getLong("COUNTRY"));						
						objDto.setAddress1(rset.getString("ADDRESS1"));												
						objDto.setAddress2(rset.getString("ADDRESS2"));												
						objDto.setAddress3(rset.getString("ADDRESS3"));												
						objDto.setAddress4(rset.getString("ADDRESS4"));													
						objDto.setCityId(rset.getLong("CITY"));
						objDto.setPostalCode(rset.getString("POSTAL_CODE"));
						objDto.setStateId(rset.getLong("STATE"));
						objDto.setUpdatedDate(rset.getString("LAST_UPDATE_DATE"));
						objDto.setCreatedDate(rset.getString("CREATION_DATE"));
						objDto.setContactPersonName(rset.getString("CONACT_PERSON_NAME"));
						objDto.setContactPersonMobile(rset.getString("CONTACT_PERSON_MOBILE"));
						objDto.setContactPersonEmail(rset.getString("CONTACT_PERSON_EMAIL"));
						objDto.setContactPersonFax(rset.getString("CONTACT_PERSON_FAX"));
						objDto.setLstNo(rset.getString("LST_NUMBER"));
						objDto.setLstDate(rset.getString("LST_DATE"));
						objDto.setDesignation(rset.getString("CONTACT_PERSON_DESIGNATION"));
						objDto.setRevCircle(rset.getLong("CIRCLE_ID"));
						lstBcpAddress.add(objDto);
					}
										
					if(lstBcpAddress.size()>0)
					{
						CRMLogger.SysErr("BCP Address Fetched And Stored In ArrayList");						
						iomsConn = getConnectionObject();
						CRMLogger.SysErr("Connecting To iB2B Database");
						csIOMS = iomsConn.prepareCall(spInsertECRMBCPAddressToIOMS);
									
						for (Iterator<ECRMBCPAddressDto> iter = lstBcpAddress.iterator(); iter.hasNext();) 
						{
							iomsConn.setAutoCommit(false);
							int iColCount = 0;
							ECRMBCPAddressDto element = (ECRMBCPAddressDto) iter.next();
							csIOMS.setLong(++iColCount ,element.getAddressId() );
							csIOMS.setLong(++iColCount ,element.getCustAccountId());
							csIOMS.setLong(++iColCount ,element.getCountryId());
							csIOMS.setString(++iColCount ,element.getAddress1());
							csIOMS.setString(++iColCount ,element.getAddress2());
							csIOMS.setString(++iColCount ,element.getAddress3());
							csIOMS.setString(++iColCount ,element.getAddress4());
							csIOMS.setLong(++iColCount ,element.getCityId());
							csIOMS.setString(++iColCount ,element.getPostalCode());
							csIOMS.setLong(++iColCount ,element.getStateId());
							if(element.getUpdatedDate()==null)
								csIOMS.setTimestamp(++iColCount ,  null);
							else
								csIOMS.setTimestamp(++iColCount ,  AppUtility.stringToTimeStamp(element.getUpdatedDate()));
							
							if(element.getCreatedDate()==null)
								csIOMS.setTimestamp(++iColCount ,  null);
							else
								csIOMS.setTimestamp(++iColCount ,AppUtility.stringToTimeStamp(element.getCreatedDate()));
							
							csIOMS.setString(++iColCount ,element.getContactPersonName());
							csIOMS.setString(++iColCount ,element.getContactPersonMobile());
							csIOMS.setString(++iColCount ,element.getContactPersonEmail());
							csIOMS.setString(++iColCount ,element.getContactPersonFax());
							csIOMS.setString(++iColCount ,element.getLstNo());
							if(element.getLstDate()==null)
								csIOMS.setTimestamp(++iColCount ,null);
							else
								csIOMS.setTimestamp(++iColCount ,AppUtility.stringToTimeStamp(element.getLstDate()));
							csIOMS.setLong(++iColCount ,insert_update);
							csIOMS.setLong(++iColCount ,0);
							csIOMS.setString(++iColCount ,"");
							csIOMS.setString(++iColCount ,"");
							csIOMS.setString(++iColCount ,"");
							csIOMS.setString(++iColCount ,element.getDesignation());
							csIOMS.setLong(++iColCount ,element.getRevCircle());

							csIOMS.execute();
							
							if("GUI".equalsIgnoreCase(requestSource)){
								CRMLogger.SysErr(csIOMS.getString(22));
							}else{
								CLEPUtility.SysErr(csIOMS.getString(22));
							}
							if (csIOMS.getInt(20) == 0) {
								iomsConn.commit();											
								statusMsg="1";
							} else {
								statusMsg="Error[-2222]:some error occurred in fetchBCPAddressFromCRM";
								iomsConn.rollback();
								if("GUI".equalsIgnoreCase(requestSource)){
									CRMLogger.SysErr("Error In method fetchBCPAddressFromCRM()");	
									CRMLogger.SysErr("Error: "+csIOMS.getString(22));
								}else{
									CLEPUtility.SysErr("Error In method fetchBCPAddressFromCRM()");	
									CLEPUtility.SysErr("Error: "+csIOMS.getString(22));
								}
							}
						}
					}else{
						statusMsg="Error[-1111]:No BCP Address found in crm";
					}
				} catch (Exception e) {
					statusMsg="Error[-2222]:in method fetchBCPAddressFromCRM()";
					if("GUI".equalsIgnoreCase(requestSource)){
						CRMLogger.SysErr("Error in method fetchBCPAddressFromCRM()"
							+ e.getMessage());
					}else{
						CLEPUtility.SysErr("Error in method fetchBCPAddressFromCRM()"
								+ e.getMessage());
					}
					e.printStackTrace();
				} finally {
					try {
						DbConnection.closeResultset(rset);
						DbConnection.closePreparedStatement(pstmt);
						DbConnection.closeCallableStatement(csIOMS);
						DbConnection.freeConnection(crmcon);
						DbConnection.freeConnection(iomsConn);
					} catch (Exception e) {
						CRMLogger.SysErr("exeption due to : " + e.getMessage());
					}
				}
				return statusMsg;
		}
	
	public static String fetchDispatchAddressFromCRM(String accountid,int insert_update)
	{
				PreparedStatement pstmt = null;
				ResultSet rset = null;
				Connection iomsConn = null;
				CallableStatement csIOMS = null;
			    String statusMsg="";
				DBConnectionRetriever con = new DBConnectionRetriever();
				Connection crmcon = null;
				String strQry="";

				try {
					TransactionBatch.getLatestDateForTable(htCRMLastInsertedValue,htCRMLastUpdatedValue);
					crmcon = con.getCRMConnection();

					if(insert_update==1){
						strQry = strGetECRMDispatchAddress +" and CUST_ACCOUNT_ID=?";
						pstmt = crmcon.prepareStatement(strQry);
						pstmt.setString(1, htCRMLastInsertedValue.get(AppConstants.DISPATCH_ADDRESS).toString());
					}else{
						strQry = strGetUpdatedECRMDispatchAddress +" and CUST_ACCOUNT_ID=?";
						pstmt = crmcon.prepareStatement(strQry);
						pstmt.setString(1, htCRMLastUpdatedValue.get(AppConstants.DISPATCH_ADDRESS).toString());
					}
					pstmt.setString(2, accountid);
																
					rset = pstmt.executeQuery();
					ArrayList<ECRMBCPAddressDto> lstBcpAddress = new ArrayList<ECRMBCPAddressDto>();
					ECRMBCPAddressDto objDto = null; 
										
					while (rset.next()) 
					{
						objDto = new ECRMBCPAddressDto();
						objDto.setAddressId(rset.getLong("ADDRESS_ID"));
						objDto.setCustAccountId(rset.getLong("CUST_ACCOUNT_ID"));
						objDto.setCountryId(rset.getLong("COUNTRY"));
						objDto.setAddress1(rset.getString("ADDRESS1"));
						objDto.setAddress2(rset.getString("ADDRESS2"));
						objDto.setAddress3(rset.getString("ADDRESS3"));
						objDto.setAddress4(rset.getString("ADDRESS4"));
						objDto.setCityId(rset.getLong("CITY"));
						objDto.setPostalCode(rset.getString("POSTAL_CODE"));
						objDto.setStateId(rset.getLong("STATE"));
						objDto.setUpdatedDate(rset.getString("LAST_UPDATE_DATE"));
						objDto.setCreatedDate(rset.getString("CREATION_DATE"));
						objDto.setContactPersonName(rset.getString("CONACT_PERSON_NAME"));
						objDto.setContactPersonMobile(rset.getString("CONTACT_PERSON_MOBILE"));
						objDto.setContactPersonEmail(rset.getString("CONTACT_PERSON_EMAIL"));
						objDto.setContactPersonFax(rset.getString("CONTACT_PERSON_FAX"));
						objDto.setLstNo(rset.getString("LST_NUMBER"));
						objDto.setLstDate(rset.getString("LST_DATE"));
						objDto.setDesignation(rset.getString("CONTACT_PERSON_DESIGNATION"));
						objDto.setRevCircle(rset.getLong("CIRCLE_ID"));
						lstBcpAddress.add(objDto);
						
					}
										
					if(lstBcpAddress.size()==0)
					{
						statusMsg="Error[-1111]:No Dispatch address found in crm!!";
						CRMLogger.SysErr("********* No Dispatch Address Found for accountid "+accountid+" at CRM *********");
					}
					
					if(lstBcpAddress.size()>0)
					{
						CRMLogger.SysErr("Dispatch Address Fetched And Stored In ArrayList");
						iomsConn = getConnectionObject();
						CRMLogger.SysErr("Connecting To iB2B Database");
						csIOMS = iomsConn.prepareCall(spInsertECRMdispatchAddressToIOMS);
																					
						for (Iterator<ECRMBCPAddressDto> iter = lstBcpAddress.iterator(); iter.hasNext();) 
						{

							iomsConn.setAutoCommit(false);
							int iColCount = 0;
							
							ECRMBCPAddressDto element = (ECRMBCPAddressDto) iter.next();
							csIOMS.setLong(++iColCount ,element.getAddressId() );
							csIOMS.setLong(++iColCount ,element.getCustAccountId());
							csIOMS.setLong(++iColCount ,element.getCountryId());
							csIOMS.setString(++iColCount ,element.getAddress1());
							csIOMS.setString(++iColCount ,element.getAddress2());
							csIOMS.setString(++iColCount ,element.getAddress3());
							csIOMS.setString(++iColCount ,element.getAddress4());
							csIOMS.setLong(++iColCount ,element.getCityId());
							csIOMS.setString(++iColCount ,element.getPostalCode());
							csIOMS.setLong(++iColCount ,element.getStateId());
							if(element.getUpdatedDate()==null)
								csIOMS.setTimestamp(++iColCount ,  null);
							else
								csIOMS.setTimestamp(++iColCount ,  AppUtility.stringToTimeStamp(element.getUpdatedDate()));
							if(element.getCreatedDate()==null)
								csIOMS.setTimestamp(++iColCount ,  null);
							else
							csIOMS.setTimestamp(++iColCount ,AppUtility.stringToTimeStamp(element.getCreatedDate()));

							csIOMS.setString(++iColCount ,element.getContactPersonName());
							csIOMS.setString(++iColCount ,element.getContactPersonMobile());
							csIOMS.setString(++iColCount ,element.getContactPersonEmail());
							csIOMS.setString(++iColCount ,element.getContactPersonFax());
							csIOMS.setString(++iColCount ,element.getLstNo());
							if(element.getLstDate()==null)
								csIOMS.setTimestamp(++iColCount ,null);
							else
								csIOMS.setTimestamp(++iColCount ,AppUtility.stringToTimeStamp(element.getLstDate()));
							csIOMS.setLong(++iColCount ,insert_update);
							csIOMS.setLong(++iColCount ,0);
							csIOMS.setString(++iColCount ,"");
							csIOMS.setString(++iColCount ,"");
							csIOMS.setString(++iColCount ,"");
							csIOMS.setString(++iColCount ,element.getDesignation());

							csIOMS.execute();
							CRMLogger.SysErr(csIOMS.getString(22));
														 
									if (csIOMS.getInt(20) == 0) {
										iomsConn.commit();										
										statusMsg="1";
										
									} else {
										statusMsg="Error[-2222]:some error occurred in fetchDispatchAddressFromCRM";
										iomsConn.rollback();
										CRMLogger.SysErr("Error[-2222]: Inserting Dispatch Address In IB2B Database");
										CRMLogger.SysErr("Error[-2222]: "+csIOMS.getString(22));
									}
						}
					}
				} catch (Exception e) {
					statusMsg="Error[-2222]:Error in method fetchDispatchAddressFromCRM()";
					CRMLogger.SysErr("Error[-2222]: in method dispatch Address()"
							+ e.getMessage());
					e.printStackTrace();
				} finally {
					try {
						DbConnection.closeResultset(rset);
						DbConnection.closePreparedStatement(pstmt);
						DbConnection.closeCallableStatement(csIOMS);
						//DbConnection.freeConnection(crmcon);
						DbConnection.freeConnection(iomsConn);
					} catch (Exception e) {
						CRMLogger.SysErr("Error[-2222]:exeption due to : " + e.getMessage());
					}
				}
				return statusMsg;
	}
	
	public static String  fetchAccountFromCRMForCLEP(Connection crmcon, long partyID,String purpose,String crmAccNo) {
		Connection iomsConn = null;
		CallableStatement csIOMS = null;
		ResultSet rset = null;
		String statusMsg="";
		
		if(crmAccNo !=null && !"".equalsIgnoreCase(crmAccNo)){
			crmAccNo=crmAccNo.trim();			
		}
		
		String querystr=	"select  distinct " +
					"( SELECT   res.resource_id FROM   apps.jtf_rs_group_members gm,apps.jtf_rs_defresroles_vl rr," +
					" apps.jtf_rs_roles_vl r, apps.jtf_rs_resource_extns res, apps.hz_parties hp  WHERE hp.party_number = p.party_Number " +
				   " AND rr.role_resource_id = gm.group_member_id " +
				   " AND rr.role_id = r.role_id " +
				   " AND gm.resource_id = res.resource_id" +
				   " AND r.role_type_code = 'SALES' " +
				   " AND r.role_code in ('PROJECT_MANAGER') " +
				   " AND TRUNC (SYSDATE) BETWEEN TRUNC (res_rl_start_date) " +
				   " AND NVL (TRUNC (res_rl_end_date), TRUNC (SYSDATE)) " +
				   " AND rr.delete_flag = 'N' " +
				   " AND gm.delete_flag = 'N' " +
				   " AND GROUP_ID = hp.attribute11 " +
					" and rownum=1 ) as projectmgrid ," +
					"NVL(CUST_ACCOUNT_ID,0) as accountId,'AES' as LOB,NVL(O.attribute7,' ') AS regionid,NVL(p.ATTRIBUTE7,' ') AS ZONEID," 
				+" NVL(HCA.GLOBAL_ATTRIBUTE13,' ') as M6ShortCode,NVL(HCA.account_number,' ') as M6AccountNo,NVL(HCA.attribute20,' ') AS LSTNO," 
				+" NVL(O.attribute13,' ') AS CSTNO,NVL(ACCOUNT_NUMBER,' ') AS CRM_ACCOUNTNO,NVL(p.party_id,0) AS partyId ,NVL(p.party_name,' ') AS partyName,NVL(p.party_Number,' ') AS partyNumber,"
				+" NVL(p.primary_phone_number,'0') phoneNo,"			
				+" (nvl((Select JRD.RESOURCE_ID  FROM   apps.JTF_RS_GROUP_MBR_ROLE_VL GRPMEMROLE, xxibm.ibm_btvl_locations ibl, apps.JTF_RS_RESOURCE_EXTNS jrd WHERE"  
				+" jrd.resource_id = GRPMEMROLE.resource_id and jrd.attribute3 = ibl.location_id and GRPMEMROLE.ROLE_NAME='Secondary Account Manager' and" 
				+" GRPMEMROLE.GROUP_ID=to_number(p.attribute11)  and rownum=1),"         
				+" (Select JRD.RESOURCE_ID  FROM   apps.JTF_RS_GROUP_MBR_ROLE_VL GRPMEMROLE, xxibm.ibm_btvl_locations ibl, apps.JTF_RS_RESOURCE_EXTNS jrd"
				+" WHERE  jrd.resource_id = GRPMEMROLE.resource_id and jrd.attribute3 = ibl.location_id and GRPMEMROLE.ROLE_NAME='Account Manager' and" 
				+" GRPMEMROLE.GROUP_ID=to_number(p.attribute11)  and rownum=1))) accountMgrId,"
				+" (nvl((Select JRD.RESOURCE_ID  FROM"   
				+" apps.JTF_RS_GROUP_MBR_ROLE_VL GRPMEMROLE, xxibm.ibm_btvl_locations ibl, apps.JTF_RS_RESOURCE_EXTNS jrd WHERE"  
				+" jrd.resource_id = GRPMEMROLE.resource_id and jrd.attribute3 = ibl.location_id and GRPMEMROLE.ROLE_NAME='Collection Manager' and" 
				+" GRPMEMROLE.GROUP_ID=to_number(p.attribute11)  and rownum=1)," 
				+" (Select JRD.USER_ID  FROM   apps.JTF_RS_GROUP_MBR_ROLE_VL GRPMEMROLE, xxibm.ibm_btvl_locations ibl, apps.JTF_RS_RESOURCE_EXTNS jrd WHERE"  
				+" jrd.resource_id = GRPMEMROLE.resource_id and jrd.attribute3 = ibl.location_id and GRPMEMROLE.ROLE_NAME='Collection Manager'" 
				+" and GRPMEMROLE.GROUP_ID=to_number(p.attribute11)  and rownum=1))) collectionMgrId,"
				+" (nvl((Select JRD.RESOURCE_ID  FROM"   
				+" apps.JTF_RS_GROUP_MBR_ROLE_VL GRPMEMROLE, xxibm.ibm_btvl_locations ibl, apps.JTF_RS_RESOURCE_EXTNS jrd WHERE"  
				+" jrd.resource_id = GRPMEMROLE.resource_id and jrd.attribute3 = ibl.location_id and GRPMEMROLE.ROLE_NAME='Project Manager' and" 
				+" GRPMEMROLE.GROUP_ID=to_number(p.attribute11)  and rownum=1)," 
				+" (Select JRD.USER_ID  FROM   apps.JTF_RS_GROUP_MBR_ROLE_VL GRPMEMROLE, xxibm.ibm_btvl_locations ibl, apps.JTF_RS_RESOURCE_EXTNS jrd WHERE"  
				+" jrd.resource_id = GRPMEMROLE.resource_id and jrd.attribute3 = ibl.location_id and GRPMEMROLE.ROLE_NAME='Project Manager'" 
				+" and GRPMEMROLE.GROUP_ID=to_number(p.attribute11)  and rownum=1))) PRcollectionMgrId,"
				+" NVL((Select JRD.Attribute6 FROM   apps.JTF_RS_GROUP_MBR_ROLE_VL GRPMEMROLE,xxibm.ibm_btvl_locations ibl, apps.JTF_RS_RESOURCE_EXTNS jrd" 
				+" WHERE  jrd.resource_id = GRPMEMROLE.resource_id and jrd.attribute3 = ibl.location_id and GRPMEMROLE.ROLE_NAME='Project Manager'and" 
				+" GRPMEMROLE.GROUP_ID=to_number(p.attribute11)and rownum=1),'0') PartyChannelPartnerId," 
				+" NVL(o.ORGANIZATION_NAME,' ') AS organizationName," 
				+" NVL(p.attribute2,0) AS industrySegment," 
				+" NVL(o.attribute1,' ') AS businessCode," 	
				+" NVL(p.attribute2,0) as VerticalId,"
				+" NVL(p.attribute3,0) as AccountCategory,"
				+" NVL(p.attribute3,0) as serviceSegment,"
				+" NVL(p.attribute1,0) as customerSegment,"		
				+" NVL(p.attribute24,'N') as OSP,"
				+" NVL(HCA.attribute6,' ') as CIRCLE," //added on 9-jan-2013, Circle work
				+" NVL(o.attribute8,' ') as CATEGORY," //added on 30-apr-2013, category
				+" NVL(HCA.GLOBAL_ATTRIBUTE16,' ') as FX_ACCOUNT_COMP_FLAG,"
				+" NVL(HCA.GLOBAL_ATTRIBUTE19,' ') as M6_ACCOUNT_COMP_FLAG,"
				+" NVL(p.attribute11,0) AS GROUPID,"   //added on 25-Jun-2013  GROUPID
				+" NVL((SELECT NVL(GROUP_DESC,'') AS GROUP_DESC FROM APPS.JTF_RS_GROUPS_TL WHERE  GROUP_ID = p.attribute11),' ')  AS GROUP_DESC, "
				+" NVL(p.attribute17,NVL(p.party_Number,'0')) as SFDCCustomerId"   //added on 28th-Sep-2016
				+" from" 
				+" apps.hz_parties p,apps.HZ_ORGANIZATION_PROFILES o,apps.HZ_PARTY_SITES ps," 
				+" apps.HZ_LOCATIONS l,apps.hz_party_site_uses k,apps.HZ_CUST_ACCOUNTS HCA" 
				+" where"
				+" HCA.PARTY_ID =  p.PARTY_ID AND o.party_id = p.party_id" 
				+" and o.EFFECTIVE_END_DATE is null and ps.party_id = p.party_id" 
				+" and ps.LOCATION_ID = l.LOCATION_ID and k.PARTY_SITE_ID = ps.PARTY_SITE_ID" 
				+" and k.SITE_USE_TYPE ='BILL_TO' and P.party_type ='ORGANIZATION'"
				+" AND ps.STATUS='A' AND k.END_DATE IS NULL"
				//+" AND HCA.GLOBAL_ATTRIBUTE16 = 'Completed' "
				//+" AND HCA.GLOBAL_ATTRIBUTE19 = 'Completed' "	
				+" AND CUST_ACCOUNT_ID in("+crmAccNo+")";
						
			CLEPUtility.SysErr("--Account Fetching Query -->"+querystr);

			PreparedStatement pstmt = null;		
			try {			

				if(purpose.equalsIgnoreCase("inserting")||purpose.equalsIgnoreCase("insert"))
				{
					pstmt = crmcon.prepareStatement(querystr);				
					rset = pstmt.executeQuery();
				}else{
					pstmt = crmcon.prepareStatement(querystr);				
					rset = pstmt.executeQuery();
				}
				int i = 0;
						
			while (rset.next()) {
				
					i = 1;
					String crmAccountNo=rset.getString("CRM_ACCOUNTNO");
					Long partyId = new Long(rset.getString("partyId"));
					String partyName=rset.getString("partyName");
					String phoneNo=rset.getString("phoneNo");
					
					//----------------------------------------------------------------------------------
										//CRM Region and Zone Validation in CRM
					//----------------------------------------------------------------------------------
						String regionId=rset.getString("regionid");
						if("".equalsIgnoreCase(regionId)|| regionId==null){
							statusMsg="Error[-3232]: Region not found of requested AccountId["+crmAccNo+"] in CRM";
							throw new IOESException("------ Error[-3232]: Region not found of account in CRM -----------");
						}
						String zoneId=rset.getString("ZONEID");
						if(" ".equalsIgnoreCase(zoneId)|| zoneId==null){
							statusMsg="Error[-3434]: Zone not found of requested AccountId["+crmAccNo+"] in CRM";
							throw new IOESException("------ Error[-3434]: Zone not found of account in CRM -----------");
						}
					//----------------------------------------------------------------------------------						
						
					//----------------------------------------------------------------------------------
						//Account Manager and Project Manager Validation in CRM
					//----------------------------------------------------------------------------------
						Long accountMgrId = new Long(rset.getLong("accountMgrId"));
						Long projectMgrId = Long.valueOf(rset.getLong("projectmgrid"));
						
						if(accountMgrId == 0 && projectMgrId == 0){
							statusMsg="Error[-3333]: Account Manager and Project Manager not found of requested AccountId["+crmAccNo+"] in CRM";
							throw new IOESException("------ Error[-3333]: Account Manager and Project Manager not found with account in CRM -----------");
						}else if(accountMgrId == 0 ){
							statusMsg="Error[-4444]: Account Manager not found of requested AccountId["+crmAccNo+"] in CRM";
							throw new IOESException("------ Error[-4444]: Account Manager not found with account in CRM -----------");
						}else if(projectMgrId == 0){
							statusMsg="Error[-5555]: Project Manager not found of requested AccountId["+crmAccNo+"] in CRM";
							throw new IOESException("------ Error[-5555]: Project Manager not found with account in CRM -----------");
						}					
					//----------------------------------------------------------------------------------
										//FX and M6 Completion Flag Validation
					//----------------------------------------------------------------------------------
						String fx_account_complete_flag=rset.getString("FX_ACCOUNT_COMP_FLAG");
												
						if(!"Completed".equalsIgnoreCase(fx_account_complete_flag)){
							statusMsg="Error[-7777]: Requested AccountId["+crmAccNo+"] is not completed in FX";
							throw new IOESException("------ Error[-7777]: Account is not completed in FX  -----------");
						}				
					//----------------------------------------------------------------------------------
										//Account Manager and Project Manager Validation in iB2B
					//----------------------------------------------------------------------------------
						int isAmFoundInIB2B=0,isPMFoundInIB2B=0;
						isAmFoundInIB2B=findAMResourceFromIb2b(String.valueOf(accountMgrId));
						isPMFoundInIB2B=findPMResourceFromIb2b(String.valueOf(projectMgrId));
						if(isAmFoundInIB2B == 0 && isPMFoundInIB2B == 0){
							statusMsg="Error[-9999]: Account Manager and Project Manager not found of requested AccountId["+crmAccNo+"] in IB2B";
							throw new IOESException("------ Error[-9999]: Account Manager["+String.valueOf(accountMgrId)+"] and Project Manager["+String.valueOf(projectMgrId)+"] not found in IB2B -----------");
						}else if(isAmFoundInIB2B == 0){
							statusMsg="Error[-9191]: Account Manager not found of requested AccountId["+crmAccNo+"] in IB2B";
							throw new IOESException("------ Error[-9191]: Account Manager["+String.valueOf(accountMgrId)+"] is not found in IB2B -----------");
						}else if(isPMFoundInIB2B == 0){
							statusMsg="Error[-9292]: Project Manager not found of requested AccountId["+crmAccNo+"] in IB2B";
							throw new IOESException("------ Error[-9292]: Project Manager["+String.valueOf(projectMgrId)+"] is not found in IB2B -----------");
						}
						
					//----------------------------------------------------------------------------------
						
					String verticalId =rset.getString("VerticalId");
					Long customerSegment = new Long(rset.getLong("customerSegment"));
					String accountCategoryId = rset.getString("AccountCategory");
					String lstNo = rset.getString("LSTNO");
					String cstNo = rset.getString("CSTNO");
					long collectionMgrId;
					if(rset.getString("collectionMgrId")!=null)
					{
						collectionMgrId = Long.valueOf(rset.getString("collectionMgrId"));
					}
					else
					{
						collectionMgrId = 0;
					}
								
					String m6Accountno = rset.getString("M6AccountNo");
					String m6ShortCode=rset.getString("M6ShortCode");					
					String accountId=rset.getString("accountId");
																														
					Long industrySegment=new Long(rset.getString("industrySegment"));
					Long serviceSegment=new Long(rset.getString("serviceSegment"));					
					String partyNO="";
					if(rset.getString("PARTYNUMBER")!=null){
						 partyNO=rset.getString("PARTYNUMBER");
					}
				
					String osp=rset.getString("OSP");
					int ospvalue=0;
					if("N".equalsIgnoreCase(osp)){
						ospvalue=2;
					}else if("Y".equalsIgnoreCase(osp)){
						ospvalue=1;
					}else if("".equalsIgnoreCase(osp)){
						ospvalue=2;
					}
					String circle=rset.getString("CIRCLE");//Added on 9-jan-2013, for circle 
					String category=rset.getString("CATEGORY");//Added on 30-apr-2013, for category 
					Long groupID = rset.getLong("GROUPID");//Added on 25-June-2013, for GROUP  ID
					String groupDesc = rset.getString("GROUP_DESC");//Added on 30-Sep-2013, for GROUP Name
					String sfdcCustomerId = rset.getString("sfdcCustomerId");//Added on 28-Sep-2016, For Adding SFDC Customer I
					
					CLEPUtility.SysErr("----- Calling iB2B Function .....");
					if(purpose.equalsIgnoreCase("inserting")||purpose.equalsIgnoreCase("insert"))
					{
						boolean iCheck = insertAccountInfoInIOMS1(partyId,
								partyName,phoneNo,accountMgrId,projectMgrId,collectionMgrId,
								accountCategoryId,verticalId, lstNo , cstNo,
								m6Accountno,accountId,crmAccountNo,m6ShortCode,regionId,customerSegment,industrySegment,serviceSegment,partyNO,zoneId,ospvalue,"Y",circle,category,groupID,groupDesc,sfdcCustomerId);
							
							if(iCheck==true){
								statusMsg="1";
							}else{
								statusMsg="Error[-2222]:Some Internal error has occured In method fetchAccountFromCRM";
							}					
					}
					else
					{
						boolean iCheck = ModifyAccountInfoInIOMS1(partyId,
								partyName,phoneNo,accountMgrId,projectMgrId,collectionMgrId,
								accountCategoryId,verticalId, lstNo , cstNo,
								m6Accountno,accountId,crmAccountNo,m6ShortCode,regionId,customerSegment,industrySegment,serviceSegment,partyNO,zoneId,ospvalue,"Y",circle, category,groupID,groupDesc,sfdcCustomerId);
						
						if(iCheck==true){
							statusMsg="1";
						}else{
							statusMsg="Error[-2222]:Some Internal error has occured In method fetchAccountFromCRM";
						}
					}				
			}
			if (i == 0) {
				statusMsg="Error[-1111]: Requesting Account["+crmAccNo+"] not found in CRM";
				throw new IOESException("------ Error[-1111]: Requesting Account["+crmAccNo+"] not found in CRM -----------");
			}			
						
		} catch(IOESException ioesexp){			
			CLEPUtility.SysErr(ioesexp.getMessage());			
			return statusMsg;
		}
			catch (Exception e) {
			CLEPUtility.SysErr("---- Error in method processPartyIds()"
					+ e.getMessage());
			e.printStackTrace();
			return statusMsg;
		} finally {
			try {
				DbConnection.closeResultset(rset);
				DbConnection.closePreparedStatement(pstmt);
				DbConnection.closeCallableStatement(csIOMS);
				DbConnection.freeConnection(iomsConn);
			} catch (Exception e) {
				CLEPUtility.SysErr("exeption due to : " + e.getMessage());
			}
		}
		return statusMsg;
	}
	//========================================================================================================

	
	public static int findAccountAlreadyInIB2BCLEP(String crmAccountId){
		int isFoundInIB2B=-1;
		Connection ib2bDbConn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try{
			ib2bDbConn=getConnectionObject();
			ps=ib2bDbConn.prepareStatement("SELECT COUNT(1) AS ISFOUND FROM IOE.TBCP_ADDRESS_MSTR WHERE ROW_TYPE='BILLING' AND ACCOUNTID IN (SELECT ACCOUNTID FROM IOE.TM_ACCOUNT WHERE ACCOUNTID=?)");
			ps.setLong(1, Long.valueOf(crmAccountId));
			rs=ps.executeQuery();
			while(rs.next()){
				isFoundInIB2B=rs.getInt("ISFOUND");
			}
		}catch(Exception exp){
			isFoundInIB2B=-1;
			CLEPUtility.SysErr("----Error Msg In findAccountAlreadyInIB2B Method :"+exp.getMessage());
			exp.printStackTrace();			
		}finally{
			try{
				DbConnection.closeResultset(rs);
				DbConnection.closePreparedStatement(ps);
				DbConnection.freeConnection(ib2bDbConn);
			}catch(Exception e){
				isFoundInIB2B=-1;
				e.printStackTrace();
			}
		}
		return isFoundInIB2B;
	}
	
	public static int findAMResourceFromIb2b(String crmAccountManagerID){
		int isFoundInIB2B=-1;
		Connection ib2bDbConn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try{
			ib2bDbConn=getConnectionObject();
			ps=ib2bDbConn.prepareStatement("SELECT count(1) AS IS_AM_FOUND FROM ioe.TM_ACCOUNTROLEDETAILS WHERE EMPLOYEEID=? and ROLEID=1");
			ps.setLong(1, Long.valueOf(crmAccountManagerID));
			rs=ps.executeQuery();
			while(rs.next()){
				isFoundInIB2B=rs.getInt("IS_AM_FOUND");
			}
		}catch(Exception exp){
			isFoundInIB2B=-1;
			CRMLogger.SysErr("----Error Msg In findAMResourceFromIb2b Method :"+exp.getMessage());
			exp.printStackTrace();			
		}finally{
			try{
				DbConnection.closeResultset(rs);
				DbConnection.closePreparedStatement(ps);
				DbConnection.freeConnection(ib2bDbConn);
			}catch(Exception e){
				isFoundInIB2B=-1;
				e.printStackTrace();
			}
		}
		return isFoundInIB2B;
	}
	public static int findPMResourceFromIb2b(String crmProjectManagerID){
		int isFoundInIB2B=-1;
		Connection ib2bDbConn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try{
			ib2bDbConn=getConnectionObject();
			ps=ib2bDbConn.prepareStatement("SELECT count(1) AS IS_PM_FOUND FROM ioe.TM_ACCOUNTROLEDETAILS WHERE EMPLOYEEID=? and ROLEID=2");
			ps.setLong(1, Long.valueOf(crmProjectManagerID));
			rs=ps.executeQuery();
			while(rs.next()){
				isFoundInIB2B=rs.getInt("IS_PM_FOUND");
			}
		}catch(Exception exp){
			isFoundInIB2B=-1;
			CRMLogger.SysErr("----Error Msg In findAMResourceFromIb2b Method :"+exp.getMessage());
			exp.printStackTrace();			
		}finally{
			try{
				DbConnection.closeResultset(rs);
				DbConnection.closePreparedStatement(ps);
				DbConnection.freeConnection(ib2bDbConn);
			}catch(Exception e){
				isFoundInIB2B=-1;
				e.printStackTrace();
			}
		}
		return isFoundInIB2B;
	}
	
	public static int findAccountAlreadyInIB2B(String crmAccountNo){
		int isFoundInIB2B=0;
		Connection ib2bDbConn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try{
			ib2bDbConn=getConnectionObject();
			ps=ib2bDbConn.prepareStatement("SELECT ACCOUNTID AS ACCOUNT_FOUNDIN_IB2B FROM ioe.TM_ACCOUNT WHERE CRMACCOUNTNO='"+crmAccountNo+"'");			
			rs=ps.executeQuery();
			while(rs.next()){
				if(rs.getString("ACCOUNT_FOUNDIN_IB2B")!=null){
					isFoundInIB2B=rs.getInt("ACCOUNT_FOUNDIN_IB2B");
				}else{
					isFoundInIB2B=0;
				}
			}
		}catch(Exception exp){
			isFoundInIB2B=-2222;
			CRMLogger.SysErr("----Error Msg In findAccountAlreadyInIB2B Method :"+exp.getMessage());
			exp.printStackTrace();			
		}finally{
			try{
				DbConnection.closeResultset(rs);
				DbConnection.closePreparedStatement(ps);
				DbConnection.freeConnection(ib2bDbConn);
			}catch(Exception e){
				isFoundInIB2B=-2222;
				e.printStackTrace();
			}
		}
		return isFoundInIB2B;
	}
	
//	=========
	/*   	********************************************************************************
	 *		Function Name:- updateEscalationLevelInfoInIOMS()
	 *		Created By:-    Nagarjuna
	 * 		Created On:-    07-08-2013
	 * 		Purpose:-		To Get All the levels info From ECRM and Update them in IOMS for Escalation
	 *      ********************************************************************************
	 */ 	
	
	public static void updateEscalationLevelInfoInIOMS(Hashtable<String, String> htTrackDate)
	{
				PreparedStatement pstmt = null;
				ResultSet rset = null;
				DBConnectionRetriever con = new DBConnectionRetriever();
				Connection crmcon = null;
				
				Connection iomsConn = null;
				CallableStatement csIOMS = null;
				boolean isInserted = false;
				ArrayList<EscalationSchedulerDto> lstUserInfo = new ArrayList<EscalationSchedulerDto>();
				EscalationSchedulerDto objDto = null; 
				try {
					
					try{
						CRMLogger.SysErr("level details query ::" + strGetECRMEmployeeUpdatedQry);
						CRMLogger.SysErr("level details updateDate :: "+updateDate);
						crmcon = con.getCRMConnection();
						pstmt = crmcon.prepareStatement(strGetECRMEmployeeUpdatedQry);
						pstmt.setString(1,htTrackDate.get(AppConstants.TM_ACCROLEDETAILS_LEVEL).toString());
						pstmt.setString(2,htTrackDate.get(AppConstants.TM_ACCROLEDETAILS_LEVEL).toString());
						rset = pstmt.executeQuery();
						while (rset.next()) 
						{
							objDto = new EscalationSchedulerDto();
							Utility.LOG(true, false, "level details setRole_resource_id :: "+rset.getString("role_resource_id"));
							objDto.setRole_resource_id(rset.getString("role_resource_id"));//EMLOYEEID IN TM_ACCOUNTROLEDETAILS Table of Ib2b
							CRMLogger.SysErr("level details setRole_resource_id :: "+objDto.getRole_resource_id());
							objDto.setROLE_ID(rset.getString("role_id"));
							objDto.setUser_name(rset.getString("USER_NAME"));
							objDto.setFirst_name(rset.getString("first_name"));
							objDto.setSOURCE_LAST_NAME(rset.getString("SOURCE_LAST_NAME"));
							objDto.setPhone_no(rset.getString("phone_no"));
							objDto.setEMAIL_ADDRESS(rset.getString("EMAIL_ADDRESS"));
							objDto.setUsrActStatus(rset.getString("ACTIVE_STATUS"));
							objDto.setRoleActStatus(rset.getString("role_status"));
							//System.out.println("emailid----------"+rset.getString("EMAIL_ADDRESS"));
							
		            	  	objDto.setLevel1_resource_id(rset.getString("level1_resource_id"));
		            	  	objDto.setL1_RoleId(rset.getString("level1_role_id"));
							objDto.setLevel1_user_id(rset.getString("level1_user_name"));
							objDto.setLEVEL1_source_FIRST_name(rset.getString("LEVEL1_source_FIRST_name"));
							objDto.setLEVEL1_source_LAST_name(rset.getString("LEVEL1_source_LAST_name"));
							objDto.setLevel1_resource_phone(rset.getString("level1_resource_phone"));
							objDto.setLevel1_resource_email(rset.getString("level1_resource_email"));
							
							lstUserInfo.add(objDto);
									
						}
					} catch (Exception e) {
										
						Utility.LOG(true, false, "EXception "+e.getStackTrace());
						CRMLogger.SysErr("Error in method updateEscalationLevelInfoInIOMS()"+ e.getStackTrace());
						CRMLogger.SysErr("Error in Escalation level Info:=>"+e.getMessage());
					} finally {
						try {
							DbConnection.closeResultset(rset);
							DbConnection.closePreparedStatement(pstmt);
							DbConnection.freeConnection(crmcon);
						} catch (Exception e) {
							Utility.LOG(true, false, "EXception "+e.getStackTrace());
							CRMLogger.SysErr("exeption due to : " + e.getMessage());
						}
					}
					
					Utility.LOG(true, false, "level details lstUserInfo.size :: "+lstUserInfo.size());
					if(lstUserInfo.size()>0 )
					{
						CRMLogger.SysErr("Total escalation level Info Fetched And Stored In ArrayList:"+lstUserInfo.size());
						CRMLogger.SysErr("Connect with IB2B database updateEscalationLevelInfoInIOMS");					
						iomsConn = getConnectionObject();
						csIOMS = iomsConn.prepareCall(spUpdateECRMEscalationInfoToIOMS);
						for (Iterator<EscalationSchedulerDto> iter = lstUserInfo.iterator(); iter.hasNext();) 
						{
							iomsConn.setAutoCommit(false);
							EscalationSchedulerDto element = (EscalationSchedulerDto) iter.next();
							CRMLogger.SysErr("update resource resource details element.getRole_resource_id :: "+element.getRole_resource_id());
							CRMLogger.SysErr("update resource resource role details element.getRole_resource_id :: "+element.getROLE_ID());
							CRMLogger.SysErr("update resource level details1 element.getRole_resource_id :: "+element.getLevel1_resource_id());
							CRMLogger.SysErr("update resource active status element.getRole_resource_id :: "+element.getUsrActStatus());
							CRMLogger.SysErr("update resource role active status element.getRole_resource_id :: "+element.getRoleActStatus());
							//if user is inactive then make inactive
							if(element.getUsrActStatus().equalsIgnoreCase("0")){
								element.setFinalActStatus(0);
								element.setIsUsrAct("0");
							}//if user is active and role is inactive then make inactive
							else if(element.getUsrActStatus().equalsIgnoreCase("1") && element.getRoleActStatus().equals("0")){
								element.setFinalActStatus(0);
								element.setIsUsrAct("1");
							}//else user is active
							else{
								element.setFinalActStatus(1);
								element.setIsUsrAct("1");
							}
							CRMLogger.SysErr("update resource final active status element.getRole_resource_id :: "+element.getFinalActStatus());
							csIOMS.setLong(1, new Long(element.getRole_resource_id()));
							csIOMS.setLong(2, new Long(element.getROLE_ID()));
							csIOMS.setString(3, element.getUser_name());
							csIOMS.setString(4, element.getFirst_name());
							csIOMS.setString(5, element.getSOURCE_LAST_NAME());
							csIOMS.setString(6, element.getPhone_no());						
							csIOMS.setString(7, element.getEMAIL_ADDRESS());
																		
							csIOMS.setLong(8, new Long(element.getLevel1_resource_id()));
							csIOMS.setLong(9, new Long(element.getL1_RoleId()));
							csIOMS.setString(10, element.getLevel1_user_id());
							csIOMS.setString(11, element.getLEVEL1_source_FIRST_name());
							csIOMS.setString(12, element.getLEVEL1_source_LAST_name());
							csIOMS.setString(13, element.getLevel1_resource_phone());
							csIOMS.setString(14, element.getLevel1_resource_email());
																	
							csIOMS.setLong(15, new Long(element.getFinalActStatus()));	
							csIOMS.setLong(16, new Long(element.getIsUsrAct()));	
							
							csIOMS.setInt(17, 0);
							csIOMS.setInt(18, 0);
							csIOMS.setString(19, "");
							//System.out.println("level3 user_id----------"+element.getLevel3_user_id());	
								
							csIOMS.execute();
							 
							if (csIOMS.getInt(17) == 0) {
								iomsConn.commit();
								isInserted = true;
								CRMLogger.SysErr("Escalation level Info Updated....");
							} else {
								iomsConn.rollback();
								CRMLogger.SysErr("User Info Updation failed...."+csIOMS.getString(31));
							}
						}
						
						if(lstUserInfo.size()>0 && isInserted)
						{
							Utility.LOG(true, false, "Total escalation level Info Fetched And Stored In ArrayList: "+lstUserInfo.size());
							csIOMS = iomsConn.prepareCall(spUpdateECRMEscatnLevelInfoToIOMS);
							for (Iterator<EscalationSchedulerDto> iter = lstUserInfo.iterator(); iter.hasNext();) 
							{
								iomsConn.setAutoCommit(false);
								EscalationSchedulerDto element = (EscalationSchedulerDto) iter.next();
								CRMLogger.SysErr("update level resource details element.getRole_resource_id {} "+element.getRole_resource_id());
								CRMLogger.SysErr("update level level details1 element.getRole_resource_id {} "+element.getLevel1_resource_id());
								csIOMS.setLong(1, new Long(element.getRole_resource_id()));
								csIOMS.setLong(2, new Long(element.getROLE_ID()));
								csIOMS.setLong(3, new Long(element.getLevel1_resource_id()));
								csIOMS.setLong(4, new Long(element.getFinalActStatus()));
								csIOMS.setInt(5, 0);
								csIOMS.setInt(6, 0);
								csIOMS.setString(7, "");
								//System.out.println("level3 user_id----------"+element.getLevel3_user_id());	
								csIOMS.execute();
								if (csIOMS.getInt(5) == 0) {
									iomsConn.commit();
									SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");									
									UpdateIOMSDataTracker("update","TM_ACCROLEDETAILS_LEVEL",sdf.format(new Date()),sdf.format(new Date()));
									isInserted = true;
									CRMLogger.SysErr("Escalation level Info Updated....");
								} else {
									iomsConn.rollback();
									CRMLogger.SysErr("User Info Updation failed...."+csIOMS.getString(31));
								}
							}
							if(isInserted){
								SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");									
								UpdateIOMSDataTracker("update","TM_ACCROLEDETAILS_LEVEL",sdf.format(new Date()),sdf.format(new Date()));
							}	 
						}
					}
				} catch (Exception e) {
					Utility.LOG(true, false, "EXception "+e.getStackTrace());
					CRMLogger.SysErr("Error in method updateEscalationLevelInfoInIOMS()"+ e.getStackTrace());
					CRMLogger.SysErr("Error in Escalation level Info:=>"+e.getMessage());
				} finally {
					try {
						DbConnection.closeCallableStatement(csIOMS);
						DbConnection.freeConnection(iomsConn);
					} catch (Exception e) {
						Utility.LOG(true, false, "EXception "+e.getStackTrace());
						CRMLogger.SysErr("exeption due to : " + e.getMessage());
					}
				}
	}
	}