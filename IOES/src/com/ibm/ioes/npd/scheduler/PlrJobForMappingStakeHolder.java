package com.ibm.ioes.npd.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import com.ibm.ioes.npd.exception.NpdException;
import com.ibm.ioes.npd.hibernate.dao.PlrUploadingDaoImpl;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.CommonUtilities;
import com.ibm.ioes.npd.utilities.SendMail;

public class PlrJobForMappingStakeHolder extends TimerTask{

	@Override
	public void run() {
		try
		{
		Date curDate = CommonUtilities.getSystemDate();
		SimpleDateFormat sdfCurDate=new SimpleDateFormat("MMMM");
		String str = sdfCurDate.format(curDate);
		
		
		SimpleDateFormat sdfCurday=new SimpleDateFormat("dd");
		int curDay = Integer.parseInt(sdfCurday.format(curDate));
		System.err.println("PlrJobForMappingStakeHolder:"+new java.util.Date());
		System.err.println(str);

		PlrUploadingDaoImpl a = new PlrUploadingDaoImpl();
		if(String.valueOf(curDay).equalsIgnoreCase(AppConstants.PLR_MAIL_FOR_DOC))
		{
			a.getMappedStakeholderDetails(String.valueOf(curDay));
		}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in run() method :PlrJobForMappingStakeHolder scheduler method: of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
		}
	}

}
