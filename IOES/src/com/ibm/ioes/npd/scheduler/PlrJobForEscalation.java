package com.ibm.ioes.npd.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import javax.servlet.ServletException;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

import com.ibm.ioes.npd.hibernate.dao.PlrUploadingDaoImpl;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.CommonUtilities;

public class PlrJobForEscalation extends TimerTask{

	@Override
	public void run() {
/*		System.err.println("Running PlrJobForEscalation :"+new Date(System.currentTimeMillis()));
*/		
		try
		{
		Date curDate = CommonUtilities.getSystemDate();
		SimpleDateFormat sdfCurDate=new SimpleDateFormat("MMMM");
		String str = sdfCurDate.format(curDate);
		
		
		SimpleDateFormat sdfCurday=new SimpleDateFormat("dd");
		int curDay = Integer.parseInt(sdfCurday.format(curDate));
		
		System.err.println(str);
		System.err.println("PlrJobForEscalation "+new Date());
		PlrUploadingDaoImpl a = new PlrUploadingDaoImpl();
		if(String.valueOf(curDay).equalsIgnoreCase(AppConstants.PLR_FIRST_LEVEL_ESCALATION) || String.valueOf(curDay).equalsIgnoreCase(AppConstants.PLR_SECOND_LEVEL_ESCALATION))
		{
			a.plrEscalation(String.valueOf(curDay));
		}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in run() method :PlrJobForEscalation scheduler method: of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			
		}
	}

	
}
