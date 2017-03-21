package com.ibm.ioes.ei.m6;

import java.util.Date;
import java.util.TimerTask;

import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.Utility;


public class SendXmlToM6 extends TimerTask {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try
		{
			Utility.LOG(true,true,"SendXmlToM6 job started at : "+new Date());
			if(!Utility.switchOn(AppConstants.SWITCH_JOB_M6_XML_SEND))
			{
				return;
			}
//			run job
			new sendToM6().sendToM6Job();
			Utility.LOG(true,true,"SendXmlToM6 job ended at : "+new Date());
		}
		catch (Exception ex) {
			Utility.onEx_LOG_RET_NEW_EX(ex,"run","SendXmlToM6",null,true,true);
			Utility.LOG(true,true,"SendXmlToM6 job ended at : "+new Date());
		}
		try
		{
			Utility.LOG(true,true,"sendToChangeOrderM6Job job started at : "+new Date());
			if(!Utility.switchOn(AppConstants.SWITCH_JOB_M6_XML_SEND))
			{
				return;
			}
//			run job
			new SendChangeOrderXmlToM6().sendToChangeOrderM6Job();
			Utility.LOG(true,true,"sendToChangeOrderM6Job job ended at : "+new Date());
		}
		catch (Exception ex) {
			Utility.onEx_LOG_RET_NEW_EX(ex,"run","sendToChangeOrderM6Job",null,true,true);
			Utility.LOG(true,true,"sendToChangeOrderM6Job job ended at : "+new Date());
		}
		
		
	}

	

}
