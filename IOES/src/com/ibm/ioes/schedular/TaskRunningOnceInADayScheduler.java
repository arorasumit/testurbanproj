package com.ibm.ioes.schedular;

//Tag Name 	Resource Name  	Date		CSR No						Description
//[001]	 	VIPIN SAHARIA	13-Apr-15	CBR_20150202-R2-021036		Sending ePCD and SMS alerts to customer at every order life cycle stage

import java.util.Date;
import java.util.TimerTask;

import com.ibm.fx.mq.FX_AUTOLINES_BILLINGTRIGGER;
import com.ibm.ioes.utilities.CustomerAlertService;
import com.ibm.ioes.utilities.Utility;



public class TaskRunningOnceInADayScheduler extends TimerTask{
	
	@Override
	public void run() {
		try{			
			if("Y".equalsIgnoreCase(Utility.getAppConfigValue("LSI_DIS_STATUS_SCH_ON")))
			{	
			try{
				Utility.LOG(true,true,"*** LSI_DIS_STATUS_SCHEDULER job started at : "+new Date());
				new CustomerAlertService().processLsiDisconnectionStatusUpdation();
				Utility.LOG(true,true,"*** LSI_DIS_STATUS_SCHEDULER job ended at : "+new Date());
			}catch (Exception e){
				Utility.LOG(true, true, e, "");
			}catch(Throwable ex)
			{
				Utility.LOG(true, true, ex, "");
			}					
	     }	
			/**Shubhranshu,4-8-2016*/
			
			if("Y".equalsIgnoreCase(Utility.getAppConfigValue("UPDATE_STATUS_LOC_NOT_RECEIVED_ON")))
			{	
			try{
				Utility.LOG(true,true,"*** UPDATE_STATUS_LOC_NOT_RECEIVED_SCHEDULER job started at : "+new Date());
				new FX_AUTOLINES_BILLINGTRIGGER().updateStatusLocNotReceivedCases();
				Utility.LOG(true,true,"*** UPDATE_STATUS_LOC_NOT_RECEIVED_SCHEDULER job ended at : "+new Date());
			}catch (Exception e){
				Utility.LOG(true, true, e, "");
			}catch(Throwable ex)
			{
				Utility.LOG(true, true, ex, "");
			}					
	     }	

		}catch(Throwable ex){
			Utility.LOG(true, true, ex, "");
		}	
	}
}
