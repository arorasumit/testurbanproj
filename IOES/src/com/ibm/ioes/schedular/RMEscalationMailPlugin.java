package com.ibm.ioes.schedular;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.Utility;

public class RMEscalationMailPlugin implements PlugIn{
	final ActionServlet actionServlet = null;
	public void destroy() {
		// TODO Auto-generated method stub

	}
	
	public void init(ActionServlet arg0, ModuleConfig arg1)
	throws ServletException {
System.err.println("starting RM Escalation Mail plugin");
		
RMEscalationScheduler mailInsertscheduler = new RMEscalationScheduler();
SendMailForEscalation mailScheduler =new SendMailForEscalation();

try {
	
	
    		
			int hours=Integer.parseInt(Utility.getAppConfigValue("ESCALATION_SCHEDULER_MAIL_HOUR"));
			int min=Integer.parseInt(Utility.getAppConfigValue("ESCALATION_SCHEDULER_MAIL_MIN"));
			int repeat=Integer.parseInt(Utility.getAppConfigValue("ESCALATION_SCHEDULER_MAIL_REPEAT_HOUR"));
		
			long day_1=repeat*60*60*1000;
			java.util.Timer timer = new java.util.Timer();
			timer.schedule(mailInsertscheduler,Utility.firstDelay(hours, min, 00),day_1 );
		
	

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
	//actionServlet.getServletContext().setAttribute(AppConstants.MAIL_SEND_LISTENER_STATE, "START");
			
			/*int hours=Integer.parseInt(Utility.getAppConfigValue("ESCALATION_SCHEDULER_MAIL_HOUR"));
			int min=Integer.parseInt(Utility.getAppConfigValue("ESCALATION_SCHEDULER_MAIL_MIN"));
			int repeat=Integer.parseInt(Utility.getAppConfigValue("ESCALATION_SCHEDULER_MAIL_REPEAT_HOUR"));
			int afterInsertSch=Integer.parseInt(Utility.getAppConfigValue("ESCALATION_SCHEDULER_MAIL_SEND_AFTER_INSERT_MIN"));
			long day_1=repeat*60*60*1000;
			java.util.Timer timer = new java.util.Timer();
			timer.schedule(mailScheduler, Utility.firstDelay(hours, (min+afterInsertSch), 0),day_1);*/
		
	
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
