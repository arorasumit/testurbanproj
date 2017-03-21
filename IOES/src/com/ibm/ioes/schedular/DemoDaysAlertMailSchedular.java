package com.ibm.ioes.schedular;

import java.util.Date;
import java.util.TimerTask;

import com.ibm.ioes.model.DemoDaysMailAlertModel;
import com.ibm.ioes.model.NewOrderModel;
import com.ibm.ioes.utilities.Utility;

public class DemoDaysAlertMailSchedular extends TimerTask {
public void run() {
		
		try{
		
			if("Y".equalsIgnoreCase(Utility.getAppConfigValue("DEMO_DAYS_MAIL_SENT_SCHEDULAR1")))
			{
				
			try {
				
				Utility.LOG(true,true,"Demo Days Mail Sent Schedular1 job started at : "+new Date());
				//run job
				
				new DemoDaysMailAlertModel().sendDemoMailAlert("1");
				Utility.LOG(true,true,"Demo Days Mail Sent Schedular1 job ended at : "+new Date());
				
			} catch (Exception e) {
				Utility.LOG(true, true, e, "");
			}
			
			
			catch(Throwable ex)
			{
				Utility.LOG(true, true, ex, "");
			}
					
	     }
			
			if("Y".equalsIgnoreCase(Utility.getAppConfigValue("DEMO_DAYS_MAIL_SENT_SCHEDULAR2")))
			{
				
			try {
				
				Utility.LOG(true,true,"Demo Days Mail Sent Schedular2 job started at : "+new Date());
				//run job
				
				new DemoDaysMailAlertModel().sendDemoMailAlert("2");
				Utility.LOG(true,true,"Demo Days Mail Sent Schedular2 job  ended at : "+new Date());
				
			} catch (Exception e) {
				Utility.LOG(true, true, e, "");
			}
			
			
			catch(Throwable ex)
			{
				Utility.LOG(true, true, ex, "");
			}
					
	     }		
		
	}
		
		catch(Throwable ex)
		{
			Utility.LOG(true, true, ex, "");
		}
				
	}
	
}
		

