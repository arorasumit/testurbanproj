package com.ibm.ioes.ei.m6;
//Tag Name Resource Name  Date		         CSR No			Description
//[001]	 SAURABH SINGH	10-Apr-13	         00-05422		Adding Cancel Hardware Line Scheduler
//[002]  SANTOSHSRIVASTAVA 8 JAN, 2014                      Updated code for ServletContext 
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.jms.JMSException;
import javax.jms.QueueConnection;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.Messages;
import com.ibm.ioes.utilities.Utility;
import com.ibm.ioes.ei.scm.SCMListener;
import com.ibm.ioes.ei.scm.SendXmlToSCM;

public class M6PlugIn implements PlugIn{
	public static ActionServlet actionServlet=null;
	public void destroy() {
		try {
			
			System.err.println("*************** Stoping M6 plugin ********************");
			QueueConnection queueConnection=(QueueConnection)M6PlugIn.actionServlet.getServletContext().getAttribute(AppConstants.M6_REQUEST_QUEUE_CONNECTION);
					queueConnection.close();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
		System.err.println("*************** Stoping SCM plugin ********************");
		QueueConnection queueConnection=(QueueConnection)M6PlugIn.actionServlet.getServletContext().getAttribute(AppConstants.SCM_REQUEST_QUEUE_CONNECTION);
				queueConnection.close();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	public void init(ActionServlet servlet, ModuleConfig arg1) throws ServletException {
//		 TODO Auto-generated method stub
		System.err.println("starting M6PlugIn plugin");
	//	M6PlugIn.actionServlet=arg0;
		
	     try
		    {
			long period=Long.parseLong(Utility.getAppConfigValue("M6_SCHEDULER_INTERVAL"));
			//long period=Long.parseLong(Messages.getMessageValue("M6_SendNewOrderXML_Schedular_Period"));
			long delay=Long.parseLong(Utility.getAppConfigValue("M6_SCHEDULER_DELAYED_START"));
			
			
			java.util.Timer timer = new java.util.Timer();
			SendXmlToM6 sendXML = new SendXmlToM6();
			timer.schedule(sendXML,delay,period );
			}
		  catch(Exception ex)
			{
				Utility.onEx_LOG_RET_NEW_EX(ex,"init","M6PlugIn","starting SendXmlToM6 Job",true,true);
			}
		  //Start[001]
		  try
		    {
			long period=Long.parseLong(Utility.getAppConfigValue("M6_SCHEDULER_INTERVAL"));
			long delay=Long.parseLong(Utility.getAppConfigValue("M6_SCHEDULER_DELAYED_START"));
			
			java.util.Timer timer = new java.util.Timer();
			SchedulerForLineCancellation sendCancellLineXML = new SchedulerForLineCancellation();
			timer.schedule(sendCancellLineXML,delay,period );
			}
		  catch(Exception ex)
			{
				Utility.onEx_LOG_RET_NEW_EX(ex,"init","M6PlugIn","starting SchedulerForLineCancellation Job",true,true);
			}
		  //End[001]
		  try
			{	
				//Starting M6 listener in new Thread
				//Thread m6Listener= new Thread(new M6Listener());
				//m6Listener.start();   //remove
			 // M6Listener m6Listener = new M6Listener();
			//  M6Listener.actionServlet=arg0;
			  //[002] Start here
			  ServletContext context = servlet.getServletContext();
			  M6Listener.main(context);
			  //[002] End
				
			
			}
		  catch(Exception ex)
			{
				Utility.onEx_LOG_RET_NEW_EX(ex,"init","M6PlugIn","starting m6Listener Job",true,true);
			}
		  try
		    {
				long period=Long.parseLong(Utility.getAppConfigValue("M6_SCHEDULER_INTERVAL"));
				long delay=Long.parseLong(Utility.getAppConfigValue("M6_SCHEDULER_DELAYED_START"));
				java.util.Timer timer = new java.util.Timer();
				SendXmlToSCM sendScmXML = new SendXmlToSCM();
				timer.schedule(sendScmXML,delay,period );
	}
		  catch(Exception ex)
			{
				Utility.onEx_LOG_RET_NEW_EX(ex,"init","M6PlugIn","starting SendXmlToSCM Job",true,true);
			}
		   try
			{	
			  //SCMListener scmListener1Obj=new SCMListener();
			  //scmListener1Obj.actionServlet=servlet;
			  //scmListener1Obj.main(null);
			  ServletContext context = servlet.getServletContext();
			  SCMListener.main(context);
			}
		  catch(Exception ex)
			{
				Utility.onEx_LOG_RET_NEW_EX(ex,"init","M6PlugIn","starting scmListener Job",true,true);
			}
		   //Schedule Run for XML processing , Shubhranshu , 3/2/2016
		   try
		   {
			   long period=Long.parseLong(Utility.getAppConfigValue("PROCESS_XML_INTERVAL"));
				long delay=Long.parseLong(Utility.getAppConfigValue("PROCESS_XML_DELAYED_START"));
				java.util.Timer timer = new java.util.Timer();
				ProcessXML processXML = new ProcessXML();
				timer.schedule(processXML, delay, period);
				
				//timer.schedule(processXML, 100, 10000);
				Utility.LOG("Started processing XML Job...");
				
		   }
		   catch(Exception ex)
		   {
				Utility.onEx_LOG_RET_NEW_EX(ex,"init","M6PlugIn","starting ProcessXML Job",true,true);
		   }
		   
	}

}
