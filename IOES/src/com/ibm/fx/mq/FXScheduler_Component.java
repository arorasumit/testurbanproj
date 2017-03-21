package com.ibm.fx.mq;

import java.util.Date;
import java.util.TimerTask;

import com.csgsystems.bp.data.AccountXIDObjectData;
import com.ibm.ioes.dao.ViewOrderDao;
import com.ibm.ioes.schedular.ComponentDisconnectionSchedular;
import com.ibm.ioes.utilities.Utility;
import com.ibm.ioes.utilities.AppConstants;

public class FXScheduler_Component extends TimerTask{

	public void run(){
		try
		{
			if("Y".equalsIgnoreCase(Utility.getAppConfigValue("IS_ACCOUNTHIT_REQUIRED")))
			{
				dummyHit();
			}
			if("Y".equalsIgnoreCase(Utility.getAppConfigValue("FX_CHARGES_PUSH_SCHEDULAR")))
			{
				if("Y".equalsIgnoreCase(Utility.getAppConfigValue("FX_COMPONENT_NEW_SCHEDULAR")))
				{
					System.out.println("In New FX Component Scheduler. Value is Y");
					try
					{
						new ChargeRedirectionSchedular().redirectCharges();
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
	
					try
					{
						new FxSchedulerTasksForPackage().pushPackagesToFX(0);
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
					
					try
					{
						new FxSchedulerTasksForComponent().pushComponentToFX(0);
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
				}
			}
		}
		catch(Throwable ex)
		{
			Utility.LOG(true, true, ex, "");
		}

		try
		{
			if("Y".equalsIgnoreCase(Utility.getAppConfigValue("FX_CHARGES_DISCONNECT_SCHEDULAR")))
			{
				if("Y".equalsIgnoreCase(Utility.getAppConfigValue("FX_COMPONENT_NEW_SCHEDULAR")))
				{
					Utility.LOG(true,true,"FXScheduler for Component Disconnection job started at : "+new Date());
					//run job for component --sandeep
					new  ComponentDisconnectionSchedular().process_component_for_disconnection(0);;
					Utility.LOG(true,true,"FXScheduler for Component Disconnection  job ended at : "+new Date());
				}
			}
		}
		catch (Throwable ex) {
			Utility.onEx_LOG_RET_NEW_EX(ex,"run","FXScheduler for Disconnection",null,true,true);
			Utility.LOG(true,true,"FXScheduler for Disconnection job ended at : "+new Date());
		}

		try{
			if("Y".equalsIgnoreCase(Utility.getAppConfigValue("FX_COMPONENT_NEW_SCHEDULAR")))
			{
				Utility.LOG(true,true,"FXScheduler for schedulerForArborUpdate job started at : "+new Date());
				new ViewOrderDao().schedulerForArborUpdate();
				Utility.LOG(true,true,"FXScheduler for schedulerForArborUpdate  job ended at : "+new Date());
			}
		}
		catch(Throwable ex)
		{
			Utility.onEx_LOG_RET_NEW_EX(ex,"run","FXScheduler for schedulerForArborUpdate job started at ",null,true,true);
			Utility.LOG(true,true,"FXScheduler for schedulerForArborUpdate  job ended at : "+new Date());
		}
	}
	
	public static void dummyHit() {
		IOESKenanManager API = null;
		AppConstants.IOES_LOGGER.error("Info ");
		Utility.LOG(true,true,"Manual dummyHit job started at : "+new Date());
		String findaccount="19309094";

		try {
					API	=	new IOESKenanManager();
					API.loginKenan();
					Utility.LOG(findaccount);
				AccountXIDObjectData aod = API.accountFind(findaccount);
				if(aod!=null)
				{
					Utility.LOG("Acount found and its  intrernalid" +aod.getAccountInternalId());
				}
				API.close();
		}catch(Exception e) {
			Utility.LOG(true,true,e,"");
			e.printStackTrace();
		}finally {
			try {
				API.close();
			} catch (Exception e) {
				Utility.LOG(true,true,e,"");
				e.printStackTrace();
			}
		}
		Utility.LOG(true,true,"Manual Dummy Hit ended at : "+new Date());		
	}
}
