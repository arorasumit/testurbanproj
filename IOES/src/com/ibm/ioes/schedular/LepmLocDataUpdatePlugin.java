package com.ibm.ioes.schedular;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;
import com.ibm.ioes.utilities.Utility;

public class LepmLocDataUpdatePlugin implements PlugIn{

	public void destroy() {
		

	}
	
	public static void main(String[] args) {
		Calendar cal= new GregorianCalendar();
		cal.add(GregorianCalendar.HOUR, 1);
		cal.set(GregorianCalendar.MINUTE, 0);
		cal.set(GregorianCalendar.SECOND, 0);
		
		long delay = cal.getTimeInMillis()-new GregorianCalendar().getTimeInMillis();
		System.out.println(delay);
	}

	public void init(ActionServlet arg0, ModuleConfig arg1)
			throws ServletException {
		System.err.println("starting Lepm Data insert in IB2B Table");
		java.util.Timer timer = new java.util.Timer();		
		LepmLocDataUpdateScheduler ordercreationcheduler = new LepmLocDataUpdateScheduler();
		
		try {
			
			/*long delay = Long.parseLong(Utility.getAppConfigValue("LEPM_LOC_DATA_UPDATE_DELAYED_START"));
			long period=Long.parseLong(Utility.getAppConfigValue("LEPM_LOC_DATA_UPDATE_INTERVAL"));
			timer.schedule(ordercreationcheduler, delay, period);*/
			
			
			Calendar cal= new GregorianCalendar();
			cal.add(GregorianCalendar.HOUR, 1);
			cal.set(GregorianCalendar.MINUTE, 0);
			cal.set(GregorianCalendar.SECOND, 0);
			long delay = cal.getTimeInMillis()-new GregorianCalendar().getTimeInMillis();
			
			long day_1=Long.parseLong(Utility.getAppConfigValue("LEPM_LOC_DATA_UPDATE_INTERVAL"));
		    timer.schedule(ordercreationcheduler,delay,day_1 );
		    
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
