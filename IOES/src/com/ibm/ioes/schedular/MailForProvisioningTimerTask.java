package com.ibm.ioes.schedular;

import java.util.Date;
import java.util.TimerTask;
import com.ibm.ioes.utilities.MailForProvisioning;
import com.ibm.ioes.utilities.Utility;

public class MailForProvisioningTimerTask extends TimerTask{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			
			//if("Y".equalsIgnoreCase(Utility.getAppConfigValue("AUTO_PROVISIONING_MAIL_SCHEDULER")))
			//{
				
			try {
				
				Utility.LOG(true,true,"AUTO_PROVISIONING_MAIL_SCHEDULER job started at : "+new Date());
				//run job
				
				new MailForProvisioning().sendACSProvisiongMail();
				Utility.LOG(true,true,"AUTO_PROVISIONING_MAIL_SCHEDULER job ended at : "+new Date());
				
			} catch (Exception e) {
				Utility.LOG(true, true, e, "");
			}
			
			
			catch(Throwable ex)
			{
				Utility.LOG(true, true, ex, "");
			}
					
	    // }			
		
	}
		
		catch(Throwable ex)
		{
			Utility.LOG(true, true, ex, "");
		}
	}
	

}
