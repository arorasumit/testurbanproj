package com.ibm.ioes.schedular;

//Tag Name 	Resource Name  	Date		CSR No						Description
//[001]	 	VIPIN SAHARIA	13-Apr-15	CBR_20150202-R2-021036		Sending ePCD and SMS alerts to customer at every order life cycle stage

import javax.servlet.ServletException;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

import com.ibm.ioes.utilities.Utility;

public class CustomerMailSMSAlertsPlugin implements PlugIn{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void init(ActionServlet arg0, ModuleConfig arg1)
			throws ServletException {
		System.err.println("*** Starting CustomerMailSMSAlertsSchedular ");
		java.util.Timer timer = new java.util.Timer();
		CustomerMailSMSAlertsSchedular alertschedular=new CustomerMailSMSAlertsSchedular();
		try{
			long delay = Long.parseLong(Utility.getAppConfigValue("ePCD_MAILSMS_SCH_DELAYED_START"));
			long period=Long.parseLong(Utility.getAppConfigValue("ePCD_MAILSMS_SCH_INTERVAL"));
			timer.schedule(alertschedular, delay, period);
		}catch (NumberFormatException e){
			// TODO Auto-generated catch block
			Utility.LOG(true, true, e, "NumberFormatException in CustomerMailSMSAlertsSchedular");
		}catch (Exception e) {
			// TODO Auto-generated catch block
			Utility.LOG(true, true, e, "Exception in CustomerMailSMSAlertsSchedular");
		}
	}
}