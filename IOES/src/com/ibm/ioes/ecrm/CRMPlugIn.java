package com.ibm.ioes.ecrm;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.ServletException;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.Messages;
import com.ibm.ioes.utilities.Utility;





public class CRMPlugIn implements PlugIn{

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void init(ActionServlet arg0, ModuleConfig arg1) throws ServletException {
//		 TODO Auto-generated method stub
		System.err.println("starting M6PlugIn plugin");
		 
		
	     try
		    {
			long period=AppConstants.one_hour_millisec;
			//long period=Long.parseLong(Messages.getMessageValue("M6_SendNewOrderXML_Schedular_Period"));
			long delay=AppConstants.min_5_millisec;
			
			
			java.util.Timer timer = new java.util.Timer();
			//SendXmlToM6 sendXML = new SendXmlToM6();
			//timer.schedule(sendXML,delay,period );
			}
		  catch(Exception ex)
			{
				Utility.onEx_LOG_RET_NEW_EX(ex,"init","CRMPlugIn","starting CRMXML Job",true,true);
			}
		  
		  try
			{	
				//Starting M6 listener in new Thread
				Thread crmListener= new Thread(new CRMListener());
				//m6Listener.start();   //remove
			}
		  catch(Exception ex)
			{
				Utility.onEx_LOG_RET_NEW_EX(ex,"init","M6PlugIn","starting m6Listener Job",true,true);
			}
	}

}
