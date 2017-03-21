package com.ibm.ioes.npd.scheduler;

import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.ServletException;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

import com.ibm.ioes.npd.utilities.Messages;
import com.ibm.ioes.npd.utilities.Utility;

public class NpdSchedulerPlugIn implements PlugIn {

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void init(ActionServlet arg0, ModuleConfig arg1)
			throws ServletException {
//		 TODO Auto-generated method stub
		System.err.println("starting myscheduler plugin");
		long day_1=24*60*60*1000;
		
		/////////////////////////////////////
		int hours=Integer.parseInt(Messages.getMessageValue("schedular.plrJobForMappingStakeHolder.hour"));
		int min=Integer.parseInt(Messages.getMessageValue("schedular.plrJobForMappingStakeHolder.min"));
		int sec=Integer.parseInt(Messages.getMessageValue("schedular.plrJobForMappingStakeHolder.sec"));
		
		java.util.Timer timer1 = new java.util.Timer();
		PlrJobForMappingStakeHolder plrJobForMappingStakeHolder = new PlrJobForMappingStakeHolder();
		timer1.schedule(plrJobForMappingStakeHolder, Utility.firstDelay(hours, min, sec),24*60*60*1000 );
		
		///////////////////////////////
		hours=Integer.parseInt(Messages.getMessageValue("schedular.plrJobForEscalation.hour"));
		min=Integer.parseInt(Messages.getMessageValue("schedular.plrJobForEscalation.min"));
		sec=Integer.parseInt(Messages.getMessageValue("schedular.plrJobForEscalation.sec"));
		
		java.util.Timer timer2 = new java.util.Timer();
		PlrJobForEscalation plrJobForEscalation = new PlrJobForEscalation();
		timer2.schedule(plrJobForEscalation, Utility.firstDelay(hours, min, sec),24*60*60*1000 );
		
		///////////////////////////////		
		hours=Integer.parseInt(Messages.getMessageValue("schedular.TaskPendingForApprovalEscalation.hour"));
		min=Integer.parseInt(Messages.getMessageValue("schedular.TaskPendingForApprovalEscalation.min"));
		sec=Integer.parseInt(Messages.getMessageValue("schedular.TaskPendingForApprovalEscalation.sec"));
		
		java.util.Timer timer3 = new java.util.Timer();
		TaskPendingForApprovalEscalation taskpending = new TaskPendingForApprovalEscalation();
		timer3.schedule(taskpending, Utility.firstDelay(hours, min, sec),24*60*60*1000 );
		
		
		
		
		

	}

}
