package com.ibm.ioes.utilities;

import java.util.TimerTask;

import javax.servlet.ServletException;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

import com.ibm.ioes.schedular.SharepointScheduler;


public class Sharepoint_Plugin implements PlugIn {
	
	public static ActionServlet actionServlet = new ActionServlet();

	public void destroy() {

	}
	public void init(ActionServlet arg0, ModuleConfig arg1)
			throws ServletException {
		System.err.println("*************** Starting Sharepoint plugin ********************");
		Utility.SPT_LOG(true, true, "***********Starting Sharepoint Plugin*********");

		java.util.Timer timer = new java.util.Timer();
		SharepointScheduler task= new SharepointScheduler();
		try {
			long delay = Long.parseLong(Utility.getAppConfigValue("SHAREPOINT_SCHEDULER_DELAYED_START"));
		    long period= Long.parseLong(Utility.getAppConfigValue("SHAREPOINT_SCHEDULER_INTERVAL"));
			timer.schedule(task, delay, period);
		}
		catch (NumberFormatException e) {
		
		//	e.printStackTrace();
			Utility.SPT_LOG(true, true, e, "");
		}

		catch (Exception e) {
			
		//	e.printStackTrace();
			Utility.SPT_LOG(true, true, e, "");
		}
	}

}