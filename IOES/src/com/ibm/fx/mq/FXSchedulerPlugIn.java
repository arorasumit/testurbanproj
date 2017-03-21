package com.ibm.fx.mq;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

import com.ibm.ioes.utilities.Utility;

public class FXSchedulerPlugIn implements PlugIn{

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void init(ActionServlet arg0, ModuleConfig arg1)
			throws ServletException {
		System.err.println("starting FXscheduler plugin");
				
		
		
		//commented by Ravneet 
		//FXSchedulerforDisconnection fxSchedulerforDisconnection = new FXSchedulerforDisconnection();
		try {
			// Scheduler for Service and Charges
			java.util.Timer timer = new java.util.Timer();
			FXScheduler fXScheduler = new FXScheduler(FXScheduler.REST_SCHEDULARS);
			long delay = Long.parseLong(Utility.getAppConfigValue("FX_SCHEDULER_SERVICE_AND_CHARGES_DELAYED_START"));
			long period=Long.parseLong(Utility.getAppConfigValue("FX_SCHEDULER_SERVICE_AND_CHARGES_INTERVAL"));
			timer.schedule(fXScheduler, delay, period);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			// Scheduler for Service and Charges
			java.util.Timer timer = new java.util.Timer();
			FXScheduler fXScheduler = new FXScheduler(FXScheduler.AUTO_BILLING_TRIGGER_FOR_AutoTypes_LOC_LATER);
			long delay = Long.parseLong(Utility.getAppConfigValue("FX_SCHEDULER_SERVICE_AND_CHARGES_DELAYED_START"));
			long period=Long.parseLong(Utility.getAppConfigValue("FX_SCHEDULER_SERVICE_AND_CHARGES_INTERVAL"));
			timer.schedule(fXScheduler, delay+(70*1000), period);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			// Scheduler for Service and Charges
			java.util.Timer timer = new java.util.Timer();
			FXScheduler fXScheduler = new FXScheduler(FXScheduler.AUTO_BILLING_TRIGGER_FOR_AutoTypes_Rest);
			long delay = Long.parseLong(Utility.getAppConfigValue("FX_SCHEDULER_SERVICE_AND_CHARGES_DELAYED_START"));
			long period=Long.parseLong(Utility.getAppConfigValue("FX_SCHEDULER_SERVICE_AND_CHARGES_INTERVAL"));
			timer.schedule(fXScheduler, delay+(100*1000), period);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			// Scheduler for Service and Charges
			java.util.Timer timer = new java.util.Timer();
			EnableDisabledEntriesScheduler enableDisabledEntriesScheduler = new EnableDisabledEntriesScheduler();
			long delay = Utility.minutesInMilliSec(2*60);
			long period=Utility.minutesInMilliSec(8*60);
			timer.schedule(enableDisabledEntriesScheduler, delay, period);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*try {
			// Scheduler for Service and Charges
			java.util.Timer timer = new java.util.Timer();
			FXScheduler fXScheduler = new FXScheduler(FXScheduler.NRC);
			long delay = Long.parseLong(Utility.getAppConfigValue("FX_SCHEDULER_SERVICE_AND_CHARGES_DELAYED_START"));
			long period=Long.parseLong(Utility.getAppConfigValue("FX_SCHEDULER_SERVICE_AND_CHARGES_INTERVAL"));
			timer.schedule(fXScheduler, delay+(2*80*1000), period);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	public void init_NRC(ActionServlet arg0, ModuleConfig arg1)
			throws ServletException {
		System.err.println("starting FX NRC scheduler plugin");
				
		try {
			// Scheduler for Service and Charges
			java.util.Timer timer = new java.util.Timer();
			FXScheduler fXScheduler = new FXScheduler(FXScheduler.NRC);
			long delay = Long.parseLong(Utility.getAppConfigValue("FX_SCHEDULER_SERVICE_AND_CHARGES_DELAYED_START"));
			long period=Long.parseLong(Utility.getAppConfigValue("FX_SCHEDULER_SERVICE_AND_CHARGES_INTERVAL"));
			timer.schedule(fXScheduler, delay+(2*80*1000), period);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public void init_Currency(ActionServlet arg0, ModuleConfig arg1)
			throws ServletException {
		System.err.println("starting FX Currency scheduler plugin");
				
		try {
			// Scheduler for Service and Charges
			java.util.Timer timer = new java.util.Timer();
			FXScheduler fXScheduler = new FXScheduler(FXScheduler.EXCHANGE_RATE_SCHEDULER);
			long delay = Long.parseLong(Utility.getAppConfigValue("EXCHANGERATE_CURRENCY_SCHEDULER_DELAYED_START"));
			long period=Long.parseLong(Utility.getAppConfigValue("EXCHANGERATE_CURRENCY_SCHEDULER_INTERVAL"));
			
			 
			System.out.println("After Checking  Condition for 1st Date: ");
			timer.schedule(fXScheduler, delay, period);
			
			
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * This method is added to bifurcate component based scheduler from main schedulers
	*/
	public void init_Component(ActionServlet arg0, ModuleConfig arg1)
			throws ServletException {
		System.err.println("starting FX Component Scheduler Plugin");
				
		try {
			// Scheduler for Service and Charges
			java.util.Timer timer = new java.util.Timer();
			FXScheduler_Component fXScheduler_Component = new FXScheduler_Component();
			long delay = Long.parseLong(Utility.getAppConfigValue("FX_SCHEDULER_SERVICE_AND_CHARGES_DELAYED_START"));
			long period=Long.parseLong(Utility.getAppConfigValue("FX_SCHEDULER_SERVICE_AND_CHARGES_INTERVAL"));
			timer.schedule(fXScheduler_Component, delay+(3*80*1000), period);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
