package com.ibm.ioes.npd.scheduler;

import javax.servlet.ServletException;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.Messages;
import com.ibm.ioes.npd.utilities.Utility;

public class NpdSchedulerDeleteTempFilesPlugIn implements PlugIn{

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void init(ActionServlet arg0, ModuleConfig arg1) throws ServletException {
//		 TODO Auto-generated method stub
		System.err.println("starting NpdSchedulerDeleteTempFilesPlugIn plugin");
		 
		
		try
		{
		int hours=Integer.parseInt(Messages.getMessageValue("schedular.deleteTempFiles.hour"));
		int min=Integer.parseInt(Messages.getMessageValue("schedular.deleteTempFiles.min"));
		int sec=Integer.parseInt(Messages.getMessageValue("schedular.deleteTempFiles.sec"));
		
		long day_1=24*60*60*1000;
		
		
		java.util.Timer timer_deleteTempFiles = new java.util.Timer();
		DeleteTempFiles deleteTempFiles = new DeleteTempFiles();
		timer_deleteTempFiles.schedule(deleteTempFiles,Utility.firstDelay(hours, min, sec),day_1 );
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in initialising delete temp files scheduler method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
		}

	}

}
