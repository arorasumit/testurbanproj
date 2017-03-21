package com.ibm.ioes.schedular;
import java.util.Date;
import java.util.Hashtable;
import java.util.TimerTask;

import com.ibm.ioes.ecrm.CRMLogger;
import com.ibm.ioes.ecrm.DBConnectionRetriever;
import com.ibm.ioes.ecrm.TransactionBatch;
import com.ibm.ioes.utilities.Utility;

public class ECRM_SingleViewTask extends TimerTask {
	
	public static Hashtable<String, String> htCRMLastUpdatedValue = new Hashtable<String, String>();
	public static Hashtable<String, String> htCRMLastInsertedValue = new Hashtable<String, String>();
	
	DBConnectionRetriever con=null;
	@Override
	public void run() {
		Utility.LOG(true, true, "ECRM Single View Schedular call at :" + new Date());
		CRMLogger.SysErr("ECRM Single View Schedular call at :" + new Date());
		try {
			// Inserting Data from CRM into IOMS database..
			if (TransactionBatch.getFlagForSchedular().equalsIgnoreCase("Y")) {
				CRMLogger.SysErr("Fetching Data from CRM database and inserting into Ib2b database....");
				TransactionBatch.getLatestDateForTable(htCRMLastInsertedValue,htCRMLastUpdatedValue);
				try{
					if("Y".equalsIgnoreCase(Utility.getAppConfigValue("SINGLE_VIEW_INSERTION_FLAG"))){
						CRMLogger.SysErr("Single View Data Insertion Started....");
						TransactionBatch.InsertSingleViewDataInCRM();
						CRMLogger.SysErr("Single View Insertion Completed....");
					}else{
						CRMLogger.SysErr("Single View Data Insertion Stoped....");
					}
					if("Y".equalsIgnoreCase(Utility.getAppConfigValue("SINGLE_VIEW_UPDATION_FLAG"))){
						CRMLogger.SysErr("Single View Data Updation Started....");
						TransactionBatch.UpdateSingleViewDataInCRM();
						CRMLogger.SysErr("Single View Updation Completed....");
					}else{
						CRMLogger.SysErr("Single View Data Updation Stoped....");
					}
				}catch (Exception ex){
					CRMLogger.LOG(true, false, ex, ":from Single view scheduler block()");
				}
				
				
				
			}
		} catch (Exception ex) {
			Utility.onEx_LOG_RET_NEW_EX(ex, "run", "ECRM_SignleViewTask", "", true, true);
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
