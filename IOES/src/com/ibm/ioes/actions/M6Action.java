package com.ibm.ioes.actions;
//Tag Name Resource Name  Date		CSR No			Description
//[001]	 SAURABH SINGH	10-Apr-13	00-05422		Adding Cancel Hardware Line Scheduler
//[002]  SANTOSHSRIVASTAVA 8 JAN, 2014                      Updated code for ServletContext 
//[003] raveendra      20150403-R1-021203      05-May-2015                 Online Payment fix
import java.util.Date;

import javax.jms.QueueConnection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import com.ibm.fx.mq.SendClepRfbtResponseToMpp;
import com.ibm.ioes.clep.CLEPListener;
import com.ibm.ioes.clep.CLEPPlugIn;
import com.ibm.ioes.clep.CLEPUtility;
import com.ibm.ioes.ei.m6.M6Listener;
import com.ibm.ioes.ei.m6.M6PlugIn;
import com.ibm.ioes.ei.m6.ProcessResponseXML;
import com.ibm.ioes.ei.m6.SendCancellationXmlToM6;
import com.ibm.ioes.ei.m6.SendChangeOrderXmlToM6;
import com.ibm.ioes.ei.m6.sendToM6;
import com.ibm.ioes.ei.scm.ProcessSCMResponseXML;
import com.ibm.ioes.ei.scm.SCMListener;
import com.ibm.ioes.ei.scm.SendToSCM;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.Utility;

public class M6Action extends DispatchAction {
	
	public ActionForward SendxmlToM6(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		AppConstants.IOES_LOGGER.error("Info -  In M6Action ");
		Utility.LOG(true,true,"Manual SendXmlToM6 job started at : "+new Date());
		ActionMessages messages = new ActionMessages();
		String schedularFlag = request.getParameter("flag");		
		String M6ListnerState=(String)getServlet().getServletContext().getAttribute(AppConstants.M6_LISTENER_STATE);
		String CLEPListnerState=(String)getServlet().getServletContext().getAttribute(AppConstants.CLEP_LISTENER_STATE);
		String scmListnerState=(String)getServlet().getServletContext().getAttribute(AppConstants.SCM_LISTENER_STATE);
		
//		run job
		if(schedularFlag.equals("new"))
		{
		  new sendToM6().sendToM6Job();//REMOVE
		}
		else if(schedularFlag.equals("change"))
		{
			new SendChangeOrderXmlToM6().sendToChangeOrderM6Job();//REMOVE
		}
		else if(schedularFlag.equals("processXmls"))
		{
			/*new ProcessResponseXML().processResponseJob();*/ // 25-01-2016
			new ProcessResponseXML().processM6Response();  // Shubhranshu 
		}
		else if(schedularFlag.equals("rfbtSchedular"))//added by [003]
		{
		  new SendClepRfbtResponseToMpp().fxSendClepResponseToMpp(null);
		}
		else if(schedularFlag.equals("m6ListnerStart"))
		{
			System.out.println( "M6 Listner State before Start = "+M6ListnerState);
			if(M6ListnerState==null || M6ListnerState.equalsIgnoreCase("STOP"))
			{
				 getServlet().getServletContext().removeAttribute(AppConstants.M6_LISTENER_STATE);	
				 //Thread m6Listener= new Thread(new M6Listener());
				// m6Listener.start();   //remove
				  //M6Listener m6Listener = new M6Listener();
				  //M6Listener.actionServlet=arg0;
				 //[002] Start here
				  M6Listener.main(this.getServlet().getServletContext());
				  //[002]End here
			}
			System.out.println( "M6 Listner State After Start= "+(String)getServlet().getServletContext().getAttribute(AppConstants.M6_LISTENER_STATE));
		}
		else if(schedularFlag.equals("m6ListnerStop"))
		{
			
			System.out.println( "M6 Listner State before Stop= "+M6ListnerState);
			if(M6ListnerState.equalsIgnoreCase("START"))
			 {
				QueueConnection queueConnection=(QueueConnection)getServlet().getServletContext().getAttribute(AppConstants.M6_REQUEST_QUEUE_CONNECTION);
				getServlet().getServletContext().removeAttribute(AppConstants.M6_REQUEST_QUEUE_CONNECTION);		
				getServlet().getServletContext().setAttribute(AppConstants.M6_LISTENER_STATE, "STOP");
				queueConnection.close();
				
			 }
			System.out.println( "M6 Listner State After Stop= "+getServlet().getServletContext().getAttribute(AppConstants.M6_LISTENER_STATE));
		}
		//Below functionality added for start and stop clep listener from user interface
		else if(schedularFlag.equals("clepListnerStart"))
		{
			System.err.println( "CLEP Listner State before Start = "+CLEPListnerState);
			CLEPUtility.SysErr("CLEP Listner State before Start = "+CLEPListnerState);
		
			if(CLEPListnerState==null || CLEPListnerState.equalsIgnoreCase("STOP"))
			{
				  getServlet().getServletContext().removeAttribute(AppConstants.CLEP_LISTENER_STATE);					 
				 				  
				  CLEPListener.main(getServlet().getServletContext());
			}
			System.err.println( "CLEP Listner State After Start= "+(String)getServlet().getServletContext().getAttribute(AppConstants.CLEP_LISTENER_STATE));
			CLEPUtility.SysErr( "CLEP Listner State After Start= "+(String)getServlet().getServletContext().getAttribute(AppConstants.CLEP_LISTENER_STATE));
		}
		else if(schedularFlag.equals("clepListnerStop"))
		{
			
			System.out.println( "CLEP Listner State before Stop= "+CLEPListnerState);
			CLEPUtility.SysErr( "CLEP Listner State before Stop= "+CLEPListnerState);
			if(CLEPListnerState.equalsIgnoreCase("START"))
			 {
				QueueConnection queueConnection=(QueueConnection)getServlet().getServletContext().getAttribute(AppConstants.CLEP_REQUEST_QUEUE_CONNECTION);
				getServlet().getServletContext().removeAttribute(AppConstants.CLEP_REQUEST_QUEUE_CONNECTION);		
				getServlet().getServletContext().setAttribute(AppConstants.CLEP_LISTENER_STATE, "STOP");
				try{
					queueConnection.close();
					System.err.println("***** CLEP Listner STOP *******");
					CLEPUtility.SysErr( "***** CLEP Listner STOP *******");
				}catch(Exception e){System.err.println(e.getMessage());}
		}
			System.out.println( "CLEP Listner State After Stop= "+getServlet().getServletContext().getAttribute(AppConstants.CLEP_LISTENER_STATE));
			CLEPUtility.SysErr( "CLEP Listner State After Stop= "+getServlet().getServletContext().getAttribute(AppConstants.CLEP_LISTENER_STATE));
		}
		//Above functionality added for start and stop clep listener from user interface
		//Start [001]
		else if(schedularFlag.equals("lineScheduler"))
		{
		  new SendCancellationXmlToM6().sendToCancellationM6Job();
		}
		//End[001]
		else if(schedularFlag.equals("sendToScm")){
		  new SendToSCM().sendToSCMJob();
		}else if(schedularFlag.equals("processSCMXmls")){
			new ProcessSCMResponseXML().processScmXml();
		}else if(schedularFlag.equals("scmListnerStart")){
			System.err.println( "SCM Listner State before Start = "+scmListnerState);
			CLEPUtility.SysErr("SCM Listner State before Start = "+scmListnerState);
			if(scmListnerState==null || scmListnerState.equalsIgnoreCase("STOP"))
			{
				  getServlet().getServletContext().removeAttribute(AppConstants.SCM_LISTENER_STATE);					 
				  //SCMListener scmListener = new SCMListener();				  
				  SCMListener.main(this.getServlet().getServletContext());
			}
			System.err.println( "SCM Listner State After Start= "+(String)getServlet().getServletContext().getAttribute(AppConstants.SCM_LISTENER_STATE));
		}
		else if(schedularFlag.equals("scmListnerStop")){
			System.out.println( "SCM Listner State before Stop= "+scmListnerState);
			if(scmListnerState.equalsIgnoreCase("START"))
			 {
				//QueueConnection queueConnection=(QueueConnection)M6PlugIn.actionServlet.getServletContext().getAttribute(AppConstants.SCM_REQUEST_QUEUE_CONNECTION);
				QueueConnection queueConnection=(QueueConnection)getServlet().getServletContext().getAttribute(AppConstants.SCM_REQUEST_QUEUE_CONNECTION);
				getServlet().getServletContext().removeAttribute(AppConstants.SCM_REQUEST_QUEUE_CONNECTION);		
				getServlet().getServletContext().setAttribute(AppConstants.SCM_LISTENER_STATE, "STOP");
				try{
					queueConnection.close();
					System.err.println("***** SCM Listner STOP *******");
				}catch(Exception e){System.err.println(e.getMessage());}
		}
			System.out.println( "SCM Listner State After Stop= "+getServlet().getServletContext().getAttribute(AppConstants.SCM_LISTENER_STATE));
		}
		messages.add("",new ActionMessage("customMessage","Done"));
		saveMessages(request, messages);
		Utility.LOG(true,true,"Manual job ended at : "+new Date());
		return mapping.findForward("success");
	}
}
