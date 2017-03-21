package com.ibm.ioes.schedular;

import javax.servlet.ServletException;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;
import com.ibm.ioes.utilities.Utility;

public class OBValueAutomationPlugin implements PlugIn{

public void destroy() {
		

	}

	public void init(ActionServlet arg0, ModuleConfig arg1)	throws ServletException {
		
						System.err.println("Initiating OB Value Bulk Upload Schedulers");
						
						try {
							
							java.util.Timer timer = new java.util.Timer();			
							OBValueAutomationScheduler ordercreationcheduler = new OBValueAutomationScheduler(OBValueAutomationScheduler.OBValueSchedulerType.OBValueScheduler);
							long delay = Long.parseLong(Utility.getAppConfigValue("OBVALUE_AUTOMATION_SCHEDULER_DELAYED_START"));
							long period=Long.parseLong(Utility.getAppConfigValue("OBVALUE_AUTOMATION_SCHEDULER_INTERVAL"));
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
						
		
						
						
		}

}
