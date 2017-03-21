package com.ibm.ioes.schedular;
//Tag Name Resource Name  Date		CSR No			Description
//[001]	 MANISHA GARG	13-Feb-13	00-05422		TO Send Mail to Act Mgr for Pending Orders
import javax.servlet.ServletException;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

import com.ibm.ioes.utilities.Utility;

public class DraftOrderMailPlugin implements PlugIn{


	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void init(ActionServlet arg0, ModuleConfig arg1)
			throws ServletException {
		System.err.println("starting Pending Order Mail plugin");
				
		
		DraftOrderMailScheduler mailscheduler = new DraftOrderMailScheduler();
		try {
			
			int hours=Integer.parseInt(Utility.getAppConfigValue("PENDING_ORDER_MAIL_HOURS"));
			int min=Integer.parseInt(Utility.getAppConfigValue("PENDING_ORDER_MAIL_MIN"));
			int sec=Integer.parseInt(Utility.getAppConfigValue("PENDING_ORDER_MAIL_SEC"));
			
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
		
	}

}
