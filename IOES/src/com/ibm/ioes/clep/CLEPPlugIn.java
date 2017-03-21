//Tag Name       Resource Name              Date		         CSR No			Description
//[001]          Santosh Srivastava         08 Jan, 2014                        updated code for ServetContext on 
//[003] raveendra      20150403-R1-021203      05-May-2015                 Online Payment fix
package com.ibm.ioes.clep;

import java.util.TimerTask;

import javax.jms.JMSException;
import javax.jms.QueueConnection;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

import com.ibm.fx.mq.SendClepRfbtResponseToMpp;
import com.ibm.ioes.exception.IOESException;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.Utility;





public class CLEPPlugIn implements PlugIn{
	
	public static ActionServlet actionServlet = new ActionServlet();

	public void destroy() {
		try {
			System.err.println("*************** Stoping CLEP plugin ********************");
			QueueConnection queueConnection=(QueueConnection)CLEPPlugIn.actionServlet.getServletContext().getAttribute(AppConstants.CLEP_REQUEST_QUEUE_CONNECTION);
					queueConnection.close();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	  public  void init(ActionServlet servlet, ModuleConfig arg1) throws ServletException {
//		 TODO Auto-generated method stub
		System.err.println("*************** Starting CLEP plugin ********************");
		
		//CLEPPlugIn.actionServlet=actionServlet;
		long period=AppConstants.half_hour_millisec;
		long delay=AppConstants.min_5_millisec;	
		java.util.Timer timer = new java.util.Timer();	
	     try
		    {
	    	
			//long period=Long.parseLong(Messages.getMessageValue("M6_SendNewOrderXML_Schedular_Period"));
	    	 					
			
			
			//SendXmlToM6 sendXML = new SendXmlToM6();
			//timer.schedule(sendXML,delay,period );
			}
		  catch(Exception ex)
			{
				Utility.onEx_LOG_RET_NEW_EX(ex,"init","CLEPPlugIn","starting CLEPListener Job",true,true);
			}
		  
		  try
			{	
				//Starting Clep listener in new Thread
				/*Thread clepListener= new Thread(new CLEPListener());
				clepListener.start(); */
			  //CLEPListener listener= new CLEPListener();
			  //listener.actionServlet=actionServlet;
//[001]  Start here 
			  ServletContext context = servlet.getServletContext();
			  CLEPListener.main(context);
//[001]  End here			  
				//Thread.currentThread().sleep(delay);
			}
		  catch(Exception ex)
			{
				Utility.onEx_LOG_RET_NEW_EX(ex,"init","CLEPPlugIn","starting CLEPListener Job",true,true);
			}
		  
		  
		  
		  try{
			  
			  Utility.LOG("CLEP RFBT_SCHEDULER Scheduler initialise preparing.....");
			  
			  
			  TimerTask rfbtTask=new TimerTask() {
					
					@Override
					public void run() {
						try {
							if("Y".equals(Utility.getAppConfigValue("CLEP_RFBT_SCHEDULER"))){
								Utility.LOG("CLEP RFBT_SCHEDULER Scheduler started");
								new SendClepRfbtResponseToMpp().fxSendClepResponseToMpp(0l);
								Utility.LOG("CLEP RFBT_SCHEDULER Scheduler ended");
							}
						} catch (Exception e) {
							Utility.LOG(true,false,e,"Exception in CLEP RFBT_SCHEDULER Scheduler running");
						}
					}
				}; 
				
				timer.schedule(rfbtTask, delay, period)	;		  
				Utility.LOG("CLEP RFBT_SCHEDULER Scheduler initialised.....");
				
		  }catch(Exception ex)
			{
				Utility.onEx_LOG_RET_NEW_EX(ex,"init","CLEPPlugIn","starting CLEP RFBT Scheduler",true,true);
			}
		  
	}

}
