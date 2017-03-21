package com.ibm.ioes.schedular;
import java.util.Date;
import java.util.Hashtable;
import java.util.TimerTask;

import com.ibm.ioes.ecrm.CRMLogger;
import com.ibm.ioes.ecrm.DBConnectionRetriever;
import com.ibm.ioes.ecrm.TransactionBatch;
import com.ibm.ioes.utilities.Utility;

public class ECRM_Task extends TimerTask {
	
	public static Hashtable<String, String> htCRMLastUpdatedValue = new Hashtable<String, String>();
	public static Hashtable<String, String> htCRMLastInsertedValue = new Hashtable<String, String>();
	
	DBConnectionRetriever con=null;
	@Override
	public void run() {
		Utility.LOG(true, true, "ECRM Schedular call at :" + new Date());
		CRMLogger.SysErr("ECRM Schedular call at :" + new Date());
		try {
			// Inserting Data from CRM into IOMS database..
			if (TransactionBatch.getFlagForSchedular().equalsIgnoreCase("Y")) {
				CRMLogger.SysErr("Fetching Data from CRM database and inserting into Ib2b database....");
				TransactionBatch.getLatestDateForTable(htCRMLastInsertedValue,htCRMLastUpdatedValue);
				try{
					if(Integer.parseInt(Utility.getAppConfigValue("INSERT_USER_CRM_SCHEDULER"))==1){
						CRMLogger.SysErr("User Info Insert....Started");
						TransactionBatch.InsertUserInfoInIOMS(htCRMLastInsertedValue);  
						CRMLogger.SysErr("User Info Insert....Completed");
					}else{
						CRMLogger.SysErr("User Info Insert Stoped");
					}
					if(Integer.parseInt(Utility.getAppConfigValue("UPDATE_USER_CRM_SCHEDULER"))==1){
						CRMLogger.SysErr("User Info Update....Started");
						TransactionBatch.UpdateUserInfoInIOMS(htCRMLastUpdatedValue);
						CRMLogger.SysErr("User Info Update....Completed");
					}else{
						CRMLogger.SysErr("User Info Update Stoped");
					}
				}catch(Exception e){
					CRMLogger.LOG(true, false, e, ":User Info Insertion and Updation Scheduler Block()");
				}
				
				CRMLogger.SysErr("Account Inserting Started.....");
				TransactionBatch.processParty("inserting",htCRMLastInsertedValue);
				CRMLogger.SysErr("Account Inserting Completed.....");
				
				CRMLogger.SysErr("Account Update Started.....");
				TransactionBatch.processParty("update",htCRMLastUpdatedValue);
				CRMLogger.SysErr("Account Update Completed.....");
				
				CRMLogger.SysErr("BCP Address Insert Started....");
				TransactionBatch.InsertECRMBCPAddressInIOMS(htCRMLastInsertedValue, 1);
				CRMLogger.SysErr("BCP Address Insert Completed....");
				
				CRMLogger.SysErr("bcp Address Update Started....");
				TransactionBatch.InsertECRMBCPAddressInIOMS(htCRMLastUpdatedValue, 2);
				CRMLogger.SysErr("bcp Address Update Completed....");
			
				CRMLogger.SysErr("Dispatch Address Insert started....");
				TransactionBatch.InsertECRMDisptachAddressInIOMS(htCRMLastInsertedValue,1);
				CRMLogger.SysErr("Dispatch Address Insert Completed....");
				
				CRMLogger.SysErr("Dispatch Address Update started....");
				TransactionBatch.InsertECRMDisptachAddressInIOMS(htCRMLastInsertedValue,2);
				CRMLogger.SysErr("Dispatch Address Update Completed....");
				
				CRMLogger.SysErr("Network Location insertion Started....");
				TransactionBatch.InsertECRMNetworkLocationInIOMS(htCRMLastInsertedValue, 1);
				CRMLogger.SysErr("Network Location insertion Completed....");

				CRMLogger.SysErr("Network Location Updation Started....");
				TransactionBatch.InsertECRMNetworkLocationInIOMS(htCRMLastUpdatedValue, 2);
				CRMLogger.SysErr("Network Location Updation Completed....");
				
				CRMLogger.SysErr("Quotes Insertion Started....");
				TransactionBatch.UpdateECRMQuotesInIOMS(0l,htCRMLastInsertedValue);
				CRMLogger.SysErr("Quotes Insertion Completed....");
				
				
				CRMLogger.SysErr("GAM  Info Insert....Started");
				TransactionBatch.InsertGamInfoInIOMS(htCRMLastInsertedValue);
			  	CRMLogger.SysErr("GAM  Info Insert....End");				

			 	CRMLogger.SysErr("GAM Info update....Started");
			 	TransactionBatch.updateGamInfoInIOMS(htCRMLastUpdatedValue);
			 	CRMLogger.SysErr("GAM Info update....End");
								
			    CRMLogger.SysErr("GAM Quotes Info Insert....Started");
			    TransactionBatch.insertECRMGAMQuotesInIOMS(htCRMLastInsertedValue);
			  	CRMLogger.SysErr("GAM Quotes Info Insert....End");
			  	
			  	CRMLogger.SysErr("OpportunityId Insert....Started");
			  	TransactionBatch.InsertUpdateOpportunityIdInIOMS(htCRMLastInsertedValue);  
				CRMLogger.SysErr("OpportunityId Insert....Completed");
				
				CRMLogger.SysErr("DD Values Insert....Started");
				TransactionBatch.InsertUpdateProductDDValuesInIOMS(1,htCRMLastInsertedValue);
				CRMLogger.SysErr("DD Values Insert....Completed");
				
				CRMLogger.SysErr("DD Values Update....Started");
				TransactionBatch.InsertUpdateProductDDValuesInIOMS(2,htCRMLastUpdatedValue);
				CRMLogger.SysErr("DD Values Update....Completed");
				
				try{
					if(Integer.parseInt(Utility.getAppConfigValue("ESCALATION_CRM_SCHEDULER"))==1){
						CRMLogger.SysErr("Escalation Level Data Update....Started");
						TransactionBatch.updateEscalationLevelInfoInIOMS(htCRMLastUpdatedValue);
						CRMLogger.SysErr("Escalation Level Data Update....Completed");
					}else{
						CRMLogger.SysErr("Escalation Level Data Update Stoped");
					}
				}catch(Exception e){
					CRMLogger.LOG(true, false, e, ":from Escalation Level Data Update Block()");
				}
			}
		} catch (Exception ex) {
			Utility.onEx_LOG_RET_NEW_EX(ex, "run", "ECRM_Task", "", true, true);
		}
		finally
		{
			try {
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		CRMLogger.SysErr("ECRM Schedular call END at :" + new Date());
		Utility.LOG(true, true, "ECRM Schedular call END at :" + new Date());

	}

}
