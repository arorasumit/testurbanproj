package com.ibm.ioes.schedular;


import java.util.Date;
import java.util.TimerTask;

import com.ibm.ioes.model.NewOrderModel;
import com.ibm.ioes.utilities.Utility;

public class AutoRenewalMailSchedular extends TimerTask{
	@Override
	public void run() {
		
		try{
		
			if("Y".equalsIgnoreCase(Utility.getAppConfigValue("AUTO_RENEWAL_MAIL_SCHEDULAER")))
			{
				
			try {
				
				Utility.LOG(true,true,"AUTO_RENEWAL_MAIL_SCHEDULAER job started at : "+new Date());
				//run job
				
				new NewOrderModel().sendAutoRenewalMail();
				Utility.LOG(true,true,"AUTO_RENEWAL_MAIL_SCHEDULAER job ended at : "+new Date());
				
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
		

