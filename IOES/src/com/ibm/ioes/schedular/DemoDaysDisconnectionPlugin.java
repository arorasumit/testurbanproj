package com.ibm.ioes.schedular;

import javax.servlet.ServletException;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

import com.ibm.ioes.utilities.Utility;

public class DemoDaysDisconnectionPlugin implements PlugIn  {
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void init(ActionServlet arg0, ModuleConfig arg1) throws ServletException {
		// TODO Auto-generated method stub
		System.err.println("Starting Demo Disconnection Order Creation plugin");
		
		DemoDaysDisconnectionMailSchedular demodayalert=new DemoDaysDisconnectionMailSchedular();
		//java.util.Timer timer = new java.util.Timer();			
		
		try {
				//int hours=Integer.parseInt(Utility.getAppConfigValue("DEMO_DAYS_DISCONNECTION_ORDER_HOUR"));
				//int min=Integer.parseInt(Utility.getAppConfigValue("DEMO_DAYS_DISCONNECTION_ORDER_MIN"));
				//int sec=Integer.parseInt(Utility.getAppConfigValue("DEMO_DAYS_DISCONNECTION_ORDER_SEC"));
//Amit Change For PR49350  Demo Period expired - Auto Disconnection Not populated from ib2b
				//long day_1=24*60*60*1000;
				long timePeriod=2*60*60*1000;
				long delay = 1*60*60*1000;
				java.util.Timer timer = new java.util.Timer();
				//timer.schedule(demodayalert,Utility.firstDelay(hours, min, sec),day_1 );
			    timer.schedule(demodayalert,delay,timePeriod );
				
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

