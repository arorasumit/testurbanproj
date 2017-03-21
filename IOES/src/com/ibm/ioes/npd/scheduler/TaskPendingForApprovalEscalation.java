package com.ibm.ioes.npd.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import javax.servlet.ServletException;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

import com.ibm.ioes.npd.hibernate.dao.MyToDOListDaoImpl;
import com.ibm.ioes.npd.hibernate.dao.PlrUploadingDaoImpl;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.CommonUtilities;

public class TaskPendingForApprovalEscalation extends TimerTask{

	@Override
	public void run() 	 {
		
		try
		{
		Date curDate = CommonUtilities.getSystemDate();
		SimpleDateFormat sdfCurDate=new SimpleDateFormat("MMMM");
		String str = sdfCurDate.format(curDate);
		
		
		SimpleDateFormat sdfCurday=new SimpleDateFormat("dd");
		int curDay = Integer.parseInt(sdfCurday.format(curDate));
		
		System.err.println(str);
		System.err.println("TaskPendingForApprovalEscalation "+new Date());
		MyToDOListDaoImpl a = new MyToDOListDaoImpl();
		System.err.println("TaskPendingForApprovalEscalation Schedular run at"+new Date());
		//if(String.valueOf(curDay).equalsIgnoreCase(AppConstants.TASK_MAX_ASSIGNED_DAYS))
		  a.MailTaskPendingForApproval();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in run() method :TaskPendingForApprovalEscalation scheduler method: of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
		}
	}

	
}
