package com.ibm.fx.mq;

import java.util.Date;
import java.util.TimerTask;

import com.ibm.ioes.schedular.DisconnectionSchedular;
import com.ibm.ioes.utilities.Utility;

public class FXSchedulerforDisconnection extends TimerTask{
	@Override
	public void run() {
		try
		{
			
			Utility.LOG(true,true,"FXScheduler for Disconnection job started at : "+new Date());
			//run job
			new DisconnectionSchedular().run(0);
			
			Utility.LOG(true,true,"FXScheduler for Disconnection  job ended at : "+new Date());
		}
		catch (Exception ex) {
			Utility.onEx_LOG_RET_NEW_EX(ex,"run","FXScheduler for Disconnection",null,true,true);
			Utility.LOG(true,true,"FXScheduler for Disconnection job ended at : "+new Date());
		}
	}
}
