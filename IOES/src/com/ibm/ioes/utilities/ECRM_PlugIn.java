package com.ibm.ioes.utilities;

import javax.servlet.ServletException;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

import com.ibm.ioes.schedular.ECRM_Task;

public class ECRM_PlugIn implements PlugIn {

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void init(ActionServlet arg0, ModuleConfig arg1)
			throws ServletException {

		Utility.LOG(true, true, "ECRM_PlugIn called");
		
		long day_1=24*60*60*1000;
		
		/////////////////////////////////////
		int hours=Integer.parseInt(Messages.getMessageValue("schedular.ecrm.hour"));
		int min=Integer.parseInt(Messages.getMessageValue("schedular.ecrm.min"));
		int sec=Integer.parseInt(Messages.getMessageValue("schedular.ecrm.sec"));
		
		java.util.Timer timer = new java.util.Timer();
		ECRM_Task task= new ECRM_Task();
		try {
		//timer.schedule(task, Utility.firstDelay(hours, min, sec),day_1 );
		long delay = Long.parseLong(Utility.getAppConfigValue("ECRM_SCHEDULER_DELAYED_START"));
		long period=Long.parseLong(Utility.getAppConfigValue("ECRM_SCHEDULER_INTERVAL"));
		timer.schedule(task, delay, period);
}
		
		catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Utility.LOG(true, true, "ECRM_PlugIn call  End");
	}

}
