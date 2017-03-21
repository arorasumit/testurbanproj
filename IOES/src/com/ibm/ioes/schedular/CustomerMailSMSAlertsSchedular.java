package com.ibm.ioes.schedular;

//Tag Name 	Resource Name  	Date		CSR No						Description
//[001]	 	VIPIN SAHARIA	13-Apr-15	CBR_20150202-R2-021036		Sending ePCD and SMS alerts to customer at every order life cycle stage

import java.util.Date;
import java.util.TimerTask;

import com.ibm.ioes.utilities.CustomerAlertService;
import com.ibm.ioes.utilities.Utility;



public class CustomerMailSMSAlertsSchedular extends TimerTask{
	
	@Override
	public void run() {
		try{			
			if("Y".equalsIgnoreCase(Utility.getAppConfigValue("ePCD_MAILSMS_SCHEDULER"))){	
			try{
				Utility.LOG(true,true,"*** ePCD_MAILSMS_SCHEDULER job started at : "+new Date());
				new CustomerAlertService().processEPCDCustomerAlert();
				Utility.LOG(true,true,"*** ePCD_MAILSMS_SCHEDULER job ended at : "+new Date());
			}catch (Exception e){
				Utility.LOG(true, true, e, "");
			}catch(Throwable ex)
			{
				Utility.LOG(true, true, ex, "");
			}					
	     }			
		
		}catch(Throwable ex){
			Utility.LOG(true, true, ex, "");
		}	
	}
}
