package com.ibm.ioes.schedular;

import java.util.Date;
import java.util.TimerTask;

import com.ibm.ioes.dao.OBCalculationDao;
import com.ibm.ioes.utilities.Utility;

public class OBValueAutomationScheduler extends TimerTask{

	enum OBValueSchedulerType {
		OBValueScheduler,
			REST
		}
	
	OBValueSchedulerType schedularToRun = null;
	
	public OBValueAutomationScheduler(OBValueSchedulerType schedularToRun) {
		this.schedularToRun=schedularToRun;
	}
	
	@Override
	public void run() {
		
		if(schedularToRun==OBValueSchedulerType.OBValueScheduler){
			try{
				if("Y".equalsIgnoreCase(Utility.getAppConfigValue("OBVALUE_AUTOMATION_SCHEDULER")))
				{
					Utility.LOG(true,true,"OBVALUE_AUTOMATION_SCHEDULER job started at : "+new Date());
					OBCalculationDao obCalculationDao = new OBCalculationDao();
					try {

						obCalculationDao.OBValueScheduler(0L);
					} catch (Exception e) {
						e.printStackTrace();
					}
					Utility.LOG(true,true,"OBVALUE_AUTOMATION_SCHEDULER job ended at : "+new Date());
				}
			}			
			catch(Throwable ex)
			{
				Utility.LOG(true, true, ex, "");
			}	
		}
			
		/*
		 * left for future schedulers related to Obvalue.
		 * 
		 */
		if (schedularToRun == OBValueSchedulerType.REST) {
			//TODO
					}
	}
}	
		
