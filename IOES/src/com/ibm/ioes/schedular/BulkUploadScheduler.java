package com.ibm.ioes.schedular;

import java.util.Date;
import java.util.TimerTask;

import com.ibm.ioes.beans.UserInfoDto;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.Utility;

public class BulkUploadScheduler extends TimerTask{

	enum BulkUploadSchedulerType {
			BillingTrigger,
			SITransfer,
			REST,
			CurrencyTransfer
		}
	
	BulkUploadSchedulerType schedularToRun = null;
	
	public BulkUploadScheduler(BulkUploadSchedulerType schedularToRun) {
		this.schedularToRun=schedularToRun;
	}
	
	@Override
	public void run() {
		
		if(schedularToRun==BulkUploadSchedulerType.BillingTrigger){
			try{
				if("Y".equalsIgnoreCase(Utility.getAppConfigValue("BILLING_TRIGGER_BULKUPLOAD_SCHEDULER")))
				{
					Utility.LOG(true,true,"Billing_Trigger_BulkUpload_Scheduler job started at : "+new Date());
					new BulkUpload().processFileBillingTrigger();
					Utility.LOG(true,true,"Billing_Trigger_BulkUpload_Scheduler job ended at : "+new Date());
				}
			}			
			catch(Throwable ex)
			{
				Utility.LOG(true, true, ex, "");
			}	
		}
			
		
		if (schedularToRun == BulkUploadSchedulerType.REST) {
			try {

				if ("Y".equalsIgnoreCase(Utility
						.getAppConfigValue("PER_DIS__BULKUPLOAD_ORDER_SCHEDULER"))) {
					Utility.LOG(true, true,
							"PER_DIS__BULKUPLOAD_ORDER_SCHEDULER job started at : "
									+ new Date());
					new BulkUpload().processFilePermanentDisconnection();
					Utility.LOG(true, true,
							"PER_DIS__BULKUPLOAD_ORDER_SCHEDULER job ended at : "
									+ new Date());
				}

			}

			catch (Throwable ex) {
				Utility.LOG(true, true, ex, "");
			}

			// Suspension
			try {

				if ("Y".equalsIgnoreCase(Utility
						.getAppConfigValue("SUSPENSION_BULKUPLOAD_ORDER_SCHEDULER"))) {
					Utility.LOG(true, true,
							"SUSPENSION_BULKUPLOAD_ORDER_SCHEDULER job started at : "
									+ new Date());
					new BulkUpload().processFileSuspension();
					Utility.LOG(true, true,
							"SUSPENSION_BULKUPLOAD_ORDER_SCHEDULER job ended at : "
									+ new Date());

				}
			} catch (Throwable ex) {
				Utility.LOG(true, true, ex, "");
			}

			// resumption

			try {

				if ("Y".equalsIgnoreCase(Utility
						.getAppConfigValue("RESUMPTION_BULKUPLOAD_ORDER_SCHEDULER"))) {
					Utility.LOG(true, true,
							"RESUMPTION_BULKUPLOAD_ORDER_SCHEDULER job started at : "
									+ new Date());
					new BulkUpload().processFilesResumption();
					Utility.LOG(true, true,
							"RESUMPTION_BULKUPLOAD_ORDER_SCHEDULER job ended at : "
									+ new Date());
				}
			}

			catch (Throwable ex) {
				Utility.LOG(true, true, ex, "");
			}

			// OB Value

			try {

				if ("Y".equalsIgnoreCase(Utility
						.getAppConfigValue("OB_VALUE_BULKUPLOAD_ORDER_SCHEDULER"))) {
					Utility.LOG(true, true,
							"OB VALUE_BULKUPLOAD_ORDER_SCHEDULER job started at : "
									+ new Date());
					new BulkUpload().processFiles_forOBValueBulkUpload();
					Utility.LOG(true, true,
							"OB VALUE_BULKUPLOAD_ORDER_SCHEDULER job ended at : "
									+ new Date());
				}
			} catch (Throwable ex) {
				Utility.LOG(true, true, ex, "");
			}
			// Rate Revision
			try {
				if ("Y".equalsIgnoreCase(Utility
						.getAppConfigValue("RATERENEWAL_BULKUPLOAD_ORDER_SCHEDULER"))) {
					Utility.LOG(true, true,
							"RATERENEWAL_BULKUPLOAD_ORDER_SCHEDULER job started at : "
									+ new Date());
					new BulkUpload().processFilesRaterenewal();
					Utility.LOG(true, true,
							"RATERENEWAL_BULKUPLOAD_ORDER_SCHEDULER job ended at : "
									+ new Date());
				}
			} catch (Throwable ex) {

			Utility.LOG(true, true, ex, "");
		}
		// OB Value Usage
		try {
			if ("Y".equalsIgnoreCase(Utility
					.getAppConfigValue("OBVALUE_USAGE_ORDER_BULKUPLOAD_SCHEDULER"))) {
				Utility.LOG(true, true,
						"OBVALUE_USAGE_ORDER_BULKUPLOAD_SCHEDULER job started at : "
								+ new Date());
				new BulkUpload().processFiles_forOBValueUsage_BulkUpload();
				Utility.LOG(true, true,
						"OBVALUE_USAGE_ORDER_BULKUPLOAD_SCHEDULER job ended at : "
								+ new Date());
			}
		} catch (Throwable ex) {
			Utility.LOG(true, true, ex, "");
		}


		//Charge Redirection
		try {                                                   
			if ("Y".equalsIgnoreCase(Utility.getAppConfigValue("CHARGE_REDIRECTION_SCHEDULER"))) {
				Utility.LOG(true, true,"CHARGE_REDIRECTION_SCHEDULER job started at : "+ new Date());
				new BulkUpload().processFiles_forChargeRedirection();
				Utility.LOG(true, true,"CHARGE_REDIRECTION_SCHEDULER job ended at : "+ new Date());
			}
		} catch (Throwable ex) {
			Utility.LOG(true, true, ex, "");
		}
		
		
		    //SI TRANSFER
			try{
				if("Y".equalsIgnoreCase(Utility.getAppConfigValue("SITRANSFER_BULKUPLOAD_SCHEDULER")))
				{
					Utility.LOG(true,true,"SITransfer_BulkUpload_Scheduler job started at : "+new Date());
						BulkUpload.processBulkSITrasnferJob();
					Utility.LOG(true,true,"SITransfer_BulkUpload_Scheduler job ended at : "+new Date());
				}
			}			
			catch(Throwable ex)
			{
				Utility.LOG(true, true, ex, "");
			}	
		
		
			//CURRENCY TRANSFER
			try{
				if("Y".equalsIgnoreCase(Utility.getAppConfigValue("CURRENCY_TRANSFER_BULKUPLOAD_SCHEDULER")))
				{
					Utility.LOG(true,true,"CurrencyTransfer_BulkUpload_Scheduler job started at : "+new Date());
						BulkUpload.processBulkCurrencyTrasnferJob();
					Utility.LOG(true,true,"CurrencyTransfer_BulkUpload_Scheduler job ended at : "+new Date());
				}
			}			
			catch(Throwable ex)
			{
				Utility.LOG(true, true, ex, "");
			}	
	
		
		}
		
		/*if(schedularToRun==BulkUploadSchedulerType.SITransfer){
			try{
				if("Y".equalsIgnoreCase(Utility.getAppConfigValue("SITRANSFER_BULKUPLOAD_SCHEDULER")))
				{
					Utility.LOG(true,true,"SITransfer_BulkUpload_Scheduler job started at : "+new Date());
						BulkUpload.processBulkSITrasnferJob();
					Utility.LOG(true,true,"SITransfer_BulkUpload_Scheduler job ended at : "+new Date());
				}
			}			
			catch(Throwable ex)
			{
				Utility.LOG(true, true, ex, "");
			}	
		}
		
		if(schedularToRun==BulkUploadSchedulerType.CurrencyTransfer){
			try{
				if("Y".equalsIgnoreCase(Utility.getAppConfigValue("CURRENCY_TRANSFER_BULKUPLOAD_SCHEDULER")))
				{
					Utility.LOG(true,true,"CurrencyTransfer_BulkUpload_Scheduler job started at : "+new Date());
						BulkUpload.processBulkCurrencyTrasnferJob();
					Utility.LOG(true,true,"CurrencyTransfer_BulkUpload_Scheduler job ended at : "+new Date());
				}
			}			
			catch(Throwable ex)
			{
				Utility.LOG(true, true, ex, "");
			}	
		}*/
	}
}	
		
