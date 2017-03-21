
package com.ibm.ioes.utilities;

import javax.servlet.ServletException;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;
import com.ibm.ioes.schedular.ECRM_SingleViewTask;

public class ECRM_SingleView_PlugIn implements PlugIn {

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void init(ActionServlet arg0, ModuleConfig arg1)
			throws ServletException {

		Utility.LOG(true, true, "ECRM_SingleView_PlugIn called");
				
		java.util.Timer timer = new java.util.Timer();
		ECRM_SingleViewTask task= new ECRM_SingleViewTask();
		try {
		long delay = Long.parseLong(Utility.getAppConfigValue("ECRM_SINGLEVIEW_SCHEDULER_DELAYED_START"));
		long period=Long.parseLong(Utility.getAppConfigValue("ECRM_SINGLEVIEW_SCHEDULER_INTERVAL"));
		
		System.err.println("Single View timing for delay: " + String.valueOf(delay)); 	
		
		System.err.println("Single View timing for delay: " + String.valueOf(period)); 	
		
		timer.schedule(task, delay, period);
}
		
		catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Utility.LOG(true, true, "ECRM_Signleview_PlugIn call  End");
	}

}
