package com.ibm.ioes.schedular;

import javax.servlet.ServletException;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

import com.ibm.ioes.utilities.Utility;

public class BulkUploadPlugin implements PlugIn{

public void destroy() {
		

	}

	public void init(ActionServlet arg0, ModuleConfig arg1)	throws ServletException {
		
						System.err.println("Initiating Bulk Upload Schedulers");
						
						try {
							
							java.util.Timer timer = new java.util.Timer();			
							BulkUploadScheduler ordercreationcheduler = new BulkUploadScheduler(BulkUploadScheduler.BulkUploadSchedulerType.BillingTrigger);
							long delay = Long.parseLong(Utility.getAppConfigValue("BILLINGTRIGGER_BULKUPLOAD_DELAYED_START"));
							long period=Long.parseLong(Utility.getAppConfigValue("BILLINGTRIGGER_BULKUPLOAD_INTERVAL"));
							timer.schedule(ordercreationcheduler, delay, period);
							
							}
						
						catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
						
						
						try {
							
							java.util.Timer timer = new java.util.Timer();			
							BulkUploadScheduler ordercreationcheduler = new BulkUploadScheduler(BulkUploadScheduler.BulkUploadSchedulerType.REST);
							long delay = Long.parseLong(Utility.getAppConfigValue("BILLINGTRIGGER_BULKUPLOAD_DELAYED_START"));
							long period=Long.parseLong(Utility.getAppConfigValue("BILLINGTRIGGER_BULKUPLOAD_INTERVAL"));
							timer.schedule(ordercreationcheduler, delay+Utility.minutesInMilliSec(2), period);
							
							}
						
						catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						/*
						try {
							
							java.util.Timer timer = new java.util.Timer();			
							BulkUploadScheduler ordercreationcheduler = new BulkUploadScheduler(BulkUploadScheduler.BulkUploadSchedulerType.SITransfer);
							long delay = Long.parseLong(Utility.getAppConfigValue("BILLINGTRIGGER_BULKUPLOAD_DELAYED_START"));
							long period=Long.parseLong(Utility.getAppConfigValue("BILLINGTRIGGER_BULKUPLOAD_INTERVAL"));
							timer.schedule(ordercreationcheduler, delay+Utility.minutesInMilliSec(2), period);
							
							}
						
						catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						try {
							
							java.util.Timer timer = new java.util.Timer();			
							BulkUploadScheduler ordercreationcheduler = new BulkUploadScheduler(BulkUploadScheduler.BulkUploadSchedulerType.CurrencyTransfer);
							long delay = Long.parseLong(Utility.getAppConfigValue("BILLINGTRIGGER_BULKUPLOAD_DELAYED_START"));
							long period=Long.parseLong(Utility.getAppConfigValue("BILLINGTRIGGER_BULKUPLOAD_INTERVAL"));
							timer.schedule(ordercreationcheduler, delay+Utility.minutesInMilliSec(2), period);
							
							}
						
						catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}*/
						
						
						
		}
}
