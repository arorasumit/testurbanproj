package com.ibm.ioes.schedular;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;


import com.ibm.ioes.utilities.Utility;

public class DemoDaysAlertPlugin implements PlugIn{

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void init(ActionServlet arg0, ModuleConfig arg1) throws ServletException {
		// TODO Auto-generated method stub
		System.err.println("Starting Demo Days Mail Alert plugin");
		DemoDaysAlertMailSchedular demodayalert=new DemoDaysAlertMailSchedular();
		
		try {
			
			int hours=Integer.parseInt(Utility.getAppConfigValue("DEMO_DAYS_MAIL_HOUR"));
			int min=Integer.parseInt(Utility.getAppConfigValue("DEMO_DAYS_MAIL_MIN"));
			int sec=Integer.parseInt(Utility.getAppConfigValue("DEMO_DAYS_MAIL_SEC"));
			
			long day_1=24*60*60*1000;
			java.util.Timer timer = new java.util.Timer();
		    timer.schedule(demodayalert,Utility.firstDelay(hours, min, sec),day_1 );
			
					
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

