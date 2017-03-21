package com.ibm.ioes.schedular;
import javax.servlet.ServletException;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

import com.ibm.ioes.utilities.Utility;

public class AutoRenewalMailPlugin implements PlugIn{

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void init(ActionServlet arg0, ModuleConfig arg1)
			throws ServletException {
		System.err.println("starting Auto Renwal Mail plugin");
				
		
		AutoRenewalMailSchedular mailscheduler = new AutoRenewalMailSchedular();
		MailForProvisioningTimerTask provisioningMailScheduler= new MailForProvisioningTimerTask();
		
		try {
			
			int hours=Integer.parseInt(Utility.getAppConfigValue("AUTO_RENEWAL_MAIL_HOUR"));
			int min=Integer.parseInt(Utility.getAppConfigValue("AUTO_RENEWAL_MAIL_MIN"));
			int sec=Integer.parseInt(Utility.getAppConfigValue("AUTO_RENEWAL_MAIL_SEC"));
			
			long day_1=24*60*60*1000;
			java.util.Timer timer = new java.util.Timer();
		    timer.schedule(mailscheduler,Utility.firstDelay(hours, min, sec),day_1 );
			
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
			long delay = Long.parseLong(Utility.getAppConfigValue("PROVISIONING_MAIL_DELAYED_START"));
			long period=Long.parseLong(Utility.getAppConfigValue("PROVISIONING_MAIL_INTERVAL"));
			timer.schedule(provisioningMailScheduler, delay, period);
			
			}
		
		catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
