package com.ibm.ioes.schedular;


import javax.servlet.ServletException;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;


import com.ibm.ioes.utilities.Utility;

public class ProvisionMailPlugin implements PlugIn{

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void init(ActionServlet arg0, ModuleConfig arg1) throws ServletException {
		// TODO Auto-generated method stub
		System.err.println("Starting Provisioning  Mail Alert plugin");
		ProvisionMailSchedular provisionMailSchedular=new ProvisionMailSchedular();
		
		try {
			
				java.util.Timer timer = new java.util.Timer();
				long delay = Long.parseLong(Utility.getAppConfigValue("PROVISION_MAIL_START"));
				long period=Long.parseLong(Utility.getAppConfigValue("PROVISION_MAIL_INTERVAL"));
				timer.schedule(provisionMailSchedular, delay, period);
								
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

