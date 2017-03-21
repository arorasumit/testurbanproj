package com.ibm.ioes.schedular;

import java.util.Date;
import java.util.TimerTask;

import com.ibm.ioes.utilities.MailForDisConnectProvisioning;
import com.ibm.ioes.utilities.MailForProvisioning;
import com.ibm.ioes.utilities.Utility;

public class ProvisionMailSchedular extends TimerTask {
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			
							
			try {
				
				Utility.LOG(true,true,"PROVISIONING_MAIL_SCHEDULER job started at : "+new Date());
				//run job
				new MailForProvisioning().sendACSProvisiongMail();
				new MailForProvisioning().sendMultipleProvisiongMail("PROVISIONING_MAIL_OVCC");
				new MailForProvisioning().sendMultipleProvisiongMail("PROVISIONING_MAIL_VCS");
				new MailForDisConnectProvisioning().sendDisACSProvisiongMail();
				new MailForDisConnectProvisioning().sendDisVCSProvisiongMail("VCS_DISCONNECTION_MAIL");
				Utility.LOG(true,true,"PROVISIONING_MAIL_SCHEDULER job ended at : "+new Date());
				
			} catch (Exception e) {
				Utility.LOG(true, true, e, "PROVISIONING_MAIL_SCHEDULER");
			}
			
			
			catch(Throwable ex)
			{
				Utility.LOG(true, true, ex, "PROVISIONING_MAIL_SCHEDULER");
			}
					
		}
		
		catch(Throwable ex)
		{
			Utility.LOG(true, true, ex, "");
		}
	}
	

}
