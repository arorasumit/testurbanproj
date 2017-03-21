//[001] VIPIN SAHARIA 11th Aug 2015 CBR_20141219-R2-020936-Billing Efficiency Program Drop 3 (BFR 17) Reverting MBIC CC mapping as per L3-VCS mapping
//[002] Priya Gupta  7th Jul 2016  CSR-20160301-XX-022139-Auto Billing 
package com.ibm.ioes.utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.ibm.ioes.newdesign.dto.ServiceLinkingDTO;

public class ApplicationFlags {
	//[002]
	public static String RR_AUTO_TRIGGER_DOCUMENT_NAME = null;
	public static String IB2B_DB_CONNECTION_LOGGER_FLAG ="ON";//this value changes during runtime from config page , which is fxschedular.jsp
	public static String IB2B_FX_TEST_ACCOUNT_CREATION ="OFF"; //this value changes during runtime from config page , which is fxschedular.jsp
	public static String IB2B_ENVIRONMENT ="PROD"; //Possible Values PROD or DEV
	public static Integer FX_AES_CUSTOMER_SERVER_ID=60;
	public static Integer ReportsExcelMaxSize = null;
	//Added by Varun
	public static Integer ARCHIVAL_REPORT_LIMIT = null;
	
	// [001]
	public static ArrayList<ServiceLinkingDTO> SERVICE_LINKING_DETAILS_LIST=null;
	public static Set<Long> SERVICE_LINKING_ATTMASTERIDS_SET = null;
	public static Map<String, ServiceLinkingDTO> SERVICE_LINKING_MAP=null;
	
//Shubhranshu
	public static Integer IB2B_ID_NEW_DISCONNECTION_REASON=null;
	static
	{
			try
			{
				IB2B_ID_NEW_DISCONNECTION_REASON=Integer.parseInt(Utility.getAppConfigValue("IB2B_ID_NEW_DISCONNECTION_REASON"));
			}
			catch(Exception e)
			{Utility.LOG(e);}
	}
//Shubranshu
	
	public static String IB2B_LOGGING_TIME_RECORD_VALIDATE_PUBLISH="";
	static{
		try {
			IB2B_DB_CONNECTION_LOGGER_FLAG=Utility.getAppConfigValue("IB2B_DB_CONNECTION_LOGGER_FLAG");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			IB2B_FX_TEST_ACCOUNT_CREATION=Utility.getAppConfigValue("IB2B_FX_TEST_ACCOUNT_CREATION");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			IB2B_ENVIRONMENT=Utility.getAppConfigValue("IB2B_ENVIRONMENT");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			FX_AES_CUSTOMER_SERVER_ID=Integer.parseInt(Utility.getAppConfigValue("FX_AES_CUSTOMER_SERVER_ID"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ReportsExcelMaxSize=Integer.parseInt(Utility.getAppConfigValue("ReportsExcelMaxSize"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Varun Start
		
		try {
			ARCHIVAL_REPORT_LIMIT=Integer.parseInt(Utility.getAppConfigValue("ARCHIVAL_REPORT_LIMIT"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Varun End 
	// [001] START
		try{
			IB2B_LOGGING_TIME_RECORD_VALIDATE_PUBLISH=Utility.getAppConfigValue("IB2B_LOGGING_TIME_RECORD_VALIDATE_PUBLISH");
			SERVICE_LINKING_DETAILS_LIST=Utility.getServiceLinkingDetails();
			if(SERVICE_LINKING_DETAILS_LIST!=null){
				SERVICE_LINKING_ATTMASTERIDS_SET= new HashSet<Long>();
				SERVICE_LINKING_MAP = new HashMap<String, ServiceLinkingDTO>();
				for(ServiceLinkingDTO servLinkObj : SERVICE_LINKING_DETAILS_LIST){
					SERVICE_LINKING_ATTMASTERIDS_SET.add(servLinkObj.getPrimaryAttmasterId());
					SERVICE_LINKING_MAP.put(servLinkObj.getPrimaryServiceDetailId()+"_"+servLinkObj.getSecServiceTypeId(), servLinkObj);
				}
			}
			//System.out.println("********************************************************************************* "+ SERVICE_LINKING_ATTMASTERIDS_SET.toString());
			// [001] END
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//[002]
		try {
			RR_AUTO_TRIGGER_DOCUMENT_NAME=Utility.getDocumentName();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
