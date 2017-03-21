package com.ibm.fx.mq;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimerTask;

import com.ibm.ioes.actions.FxSchedulerAction;
import com.ibm.ioes.dao.ViewOrderDao;
import com.ibm.ioes.schedular.AccountUpdateSchedular;
import com.ibm.ioes.schedular.BCP_Address_Change;
import com.ibm.ioes.schedular.ComponentDisconnectionSchedular;
import com.ibm.ioes.schedular.DisconnectionSchedular;
import com.ibm.ioes.schedular.ServiceUpdateSchedular;
import com.ibm.ioes.schedular.Service_DisconnectionSchedular;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.MailForDisConnectProvisioning;
import com.ibm.ioes.utilities.MailForProvisioning;
import com.ibm.ioes.utilities.Utility;

public class FXScheduler extends TimerTask{
	
	public static final String AUTO_BILLING_TRIGGER_FOR_AutoTypes_LOC_LATER="AUTO_BILLING_TRIGGER_FOR_AutoTypes_LOC_LATER";
	public static final String AUTO_BILLING_TRIGGER_FOR_AutoTypes_Rest="AUTO_BILLING_TRIGGER_FOR_AutoTypes_Rest";
	
	public static final String NRC="NRC";
	public static final String REST_SCHEDULARS="REST_SCHEDULARS";
	public static final String EXCHANGE_RATE_SCHEDULER="EXCHANGE_RATE_SCHEDULER";
	
	private String schedulerToRun;
	
	/*public FXScheduler() {
	}*/
	
	public FXScheduler(String schedulerToRun){
		this.schedulerToRun=schedulerToRun;
	}
	
	public void run() {
		if(AUTO_BILLING_TRIGGER_FOR_AutoTypes_LOC_LATER.equals(schedulerToRun)){
			runAutoBillingProcessForLocLater();
		}else if(AUTO_BILLING_TRIGGER_FOR_AutoTypes_Rest.equals(schedulerToRun)){
			runAutoBillingProcessForRest();
		}else if(NRC.equals(schedulerToRun)){
			runFxNrcScheduler();
		}else if(REST_SCHEDULARS.equals(schedulerToRun)){
			runFxRestScheduler();
		}else if(EXCHANGE_RATE_SCHEDULER.equals(schedulerToRun)){
			exchangeRateScheduler();
		}
	}
	
	private void runAutoBillingProcessForRest() {
		try
		{
    	  if("Y".equalsIgnoreCase(Utility.getAppConfigValue("AUTO_BILLING_SCHEDULAR_1")))
			{
			Utility.LOG(true,true,"FXScheduler for autoBillingProcessForRest started at : "+new Date());
			new FX_AUTOLINES_BILLINGTRIGGER().autoBillingProcessForRest();
			Utility.LOG(true,true,"FXScheduler for autoBillingProcessForRest ended at: "+new Date());
			}
			
		}
		catch (Throwable ex) {
			Utility.onEx_LOG_RET_NEW_EX(ex,"run","FXScheduler for autoBillingProcessForRest ",null,true,true);
			Utility.LOG(true,true,"FXScheduler autoBillingProcessForRest ended at : "+new Date());
		}
	}

	private void runAutoBillingProcessForLocLater() {
		try
		{
    	  if("Y".equalsIgnoreCase(Utility.getAppConfigValue("AUTO_BILLING_SCHEDULAR_1")))
			{
			Utility.LOG(true,true,"FXScheduler for autoBillingProcessForLocLater started at : "+new Date());
			new FX_AUTOLINES_BILLINGTRIGGER().autoBillingProcessForLocLater();
			Utility.LOG(true,true,"FXScheduler for autoBillingProcessForLocLater ended at: "+new Date());
			}
			
		}
		catch (Throwable ex) {
			Utility.onEx_LOG_RET_NEW_EX(ex,"run","FXScheduler for autoBillingProcessForLocLater ",null,true,true);
			Utility.LOG(true,true,"FXScheduler for autoBillingProcessForLocLater ended at : "+new Date());
		}
		
		try
		{
		  if("Y".equalsIgnoreCase(Utility.getAppConfigValue("AUTO_BILLING_SCHEDULAR_2")))
			{
			Utility.LOG(true,true,"FXScheduler for Push LocDataTo Fx job started at : "+new Date());
			new PushLocDataToFx().PushLocDataIntoFx();
			Utility.LOG(true,true,"FXScheduler for Push LocDataTo Fx  job ended at: "+new Date());
			}
			
		}
		catch (Throwable ex) {
			Utility.onEx_LOG_RET_NEW_EX(ex,"run","FXScheduler for Push LocDataTo Fx job started at ",null,true,true);
			Utility.LOG(true,true,"FXScheduler for Push LocDataTo Fx  job ended at : "+new Date());
		}
	}

	/*public void runFxAutoBillingTrigger(){
		try
		{
    	  if("Y".equalsIgnoreCase(Utility.getAppConfigValue("AUTO_BILLING_SCHEDULAR_1")))
			{
			Utility.LOG(true,true,"FXScheduler for H/W Sale Auto Billing job started at : "+new Date());
			//new FX_AUTOLINES_BILLINGTRIGGER().autoBillingProcess();
			Utility.LOG(true,true,"FXScheduler for H/W Sale Auto Billing job ended at: "+new Date());
			}
			
		}
		catch (Throwable ex) {
			Utility.onEx_LOG_RET_NEW_EX(ex,"run","FXScheduler for H/W Sale Auto Billing ",null,true,true);
			Utility.LOG(true,true,"FXScheduler for H/W Sale Auto Billing job ended at : "+new Date());
		}
		
		try
		{
		  if("Y".equalsIgnoreCase(Utility.getAppConfigValue("AUTO_BILLING_SCHEDULAR_2")))
			{
			Utility.LOG(true,true,"FXScheduler for Push LocDataTo Fx job started at : "+new Date());
			new PushLocDataToFx().PushLocDataIntoFx();
			Utility.LOG(true,true,"FXScheduler for Push LocDataTo Fx  job ended at: "+new Date());
			}
			
		}
		catch (Throwable ex) {
			Utility.onEx_LOG_RET_NEW_EX(ex,"run","FXScheduler for Push LocDataTo Fx job started at ",null,true,true);
			Utility.LOG(true,true,"FXScheduler for Push LocDataTo Fx  job ended at : "+new Date());
		}
	}*/
	
	public void runFxNrcScheduler(){
		try{
			if("Y".equalsIgnoreCase(Utility.getAppConfigValue("FX_CHARGES_PUSH_SCHEDULAR")))
			{
				try
				{
					Utility.LOG(true,true,"FXScheduler Testing NRC Charge job started at : "+new Date());
					
					//run job
					if("Y".equalsIgnoreCase(Utility.getAppConfigValue("FX_NRC_SCHEDULAR_ON_M6CLONE"))){
						new FxSchedulerTasksforCharges().pushNRCChargesToFX(0);
					}else{
						FxSchedulerAction.main(null);
					}
					
					
					Utility.LOG(true,true,"FXScheduler Testing NRC Charge job ended at : "+new Date());
				}
				catch (Exception ex) {
					Utility.onEx_LOG_RET_NEW_EX(ex,"run","FXScheduler Testing NRC Charge",null,true,true);
					Utility.LOG(true,true,"FXScheduler Testing NRC Charge job ended at : "+new Date());
				}	
			}	
		}
		catch(Throwable ex)
		{
			Utility.LOG(true, true, ex, "");
		}
	}
	
	public void runFxRestScheduler(){
	
		
		try
		{
			if("Y".equalsIgnoreCase(Utility.getAppConfigValue("FX_ACCOUNT_CREATION_SCHEDULAR")))
			{
				Connection con =null;
			try {
				
				Utility.LOG(true,true,"FXScheduler Account creation job started at : "+new Date());
				//run job
				con=DbConnection.getConnectionObject();
				new CreateAccount().createAccount(con,null);
				new AccountUpdateSchedular().pushUpdatedAccountsToFX(0);
				Utility.LOG(true,true,"FXScheduler Account creation job ended at : "+new Date());
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally
			{
				DbConnection.freeConnection(con);
			}
		}
			
			if("Y".equalsIgnoreCase(Utility.getAppConfigValue("FX_CHARGES_PUSH_SCHEDULAR")))
			{
				try
				{
					
					Utility.LOG(true,true,"FXScheduler Service job started at : "+new Date());
					//run job
					new FxSchedulerTasks().pushServicesToFX(0);
					Utility.LOG(true,true,"FXScheduler Service job ended at : "+new Date());
				}
				catch (Exception ex) {
					Utility.onEx_LOG_RET_NEW_EX(ex,"run","FXScheduler Service",null,true,true);
					Utility.LOG(true,true,"FXScheduler Service job ended at : "+new Date());
				}
				try
				{
					Utility.LOG(true,true,"FXScheduler RC Charge job started at : "+new Date());
					
					//run job
					new FxSchedulerTasksforCharges().pushRCChargesToFX(0);
					Utility.LOG(true,true,"FXScheduler RC Charge job ended at : "+new Date());
				}
				catch (Exception ex) {
					Utility.onEx_LOG_RET_NEW_EX(ex,"run","FXScheduler RC Charge",null,true,true);
					Utility.LOG(true,true,"FXScheduler RC Charge job ended at : "+new Date());
				}
				try
				{
					if("Y".equalsIgnoreCase(Utility.getAppConfigValue("FX_NRC_SCHEDULAR_ON_FXCLONE"))){
						Utility.LOG(true,true,"FXScheduler NRC Charge job started at : "+new Date());
						
						//run job
						new FxSchedulerTasksforCharges().pushNRCChargesToFX(0);
						Utility.LOG(true,true,"FXScheduler NRC Charge job ended at : "+new Date());
					}
					
				}
				catch (Exception ex) {
					Utility.onEx_LOG_RET_NEW_EX(ex,"run","FXScheduler NRC Charge",null,true,true);
					Utility.LOG(true,true,"FXScheduler NRC Charge job ended at : "+new Date());
				}	
				
				if("N".equalsIgnoreCase(Utility.getAppConfigValue("FX_COMPONENT_NEW_SCHEDULAR")))
				{
					System.out.println("In Old FX Scheduler. Value is N");
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
				//--sandeep
				try
				{
					Utility.LOG(true,true,"ServiceUpdateSchedular started at : "+new Date());
					new ServiceUpdateSchedular().pushUpdatedServicesToFX(0);
					Utility.LOG(true,true,"ServiceUpdateSchedular ended at : "+new Date());
				}
				catch(Exception ex)
				{
					Utility.onEx_LOG_RET_NEW_EX(ex,"run","ServiceUpdateSchedular",null,true,true);
					Utility.LOG(true,true,"ServiceUpdateSchedular job ended at : "+new Date());
				}
				
				//--sandeep
				try
				{
					Utility.LOG(true,true,"BCP_Address_Change Schedular started at : "+new Date());
					new BCP_Address_Change().run();
					Utility.LOG(true,true,"BCP_Address_Change Schedular ended at : "+new Date());
				}
				catch(Exception ex)
				{
					Utility.onEx_LOG_RET_NEW_EX(ex,"run","BCP_Address_Change Schedular",null,true,true);
					Utility.LOG(true,true,"BCP_Address_Change Schedular job ended at : "+new Date());
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
				Utility.LOG(true,true,"FXScheduler for Disconnection job started at : "+new Date());
				//run job
				new DisconnectionSchedular().run(0);
				Utility.LOG(true,true,"FXScheduler for Disconnection  job ended at : "+new Date());
				Utility.LOG(true,true,"FXScheduler for Service Disconnection job started at : "+new Date());
				//run job
				new Service_DisconnectionSchedular().process_service_for_disconnection(0);
				Utility.LOG(true,true,"FXScheduler for Service Disconnection  job ended at : "+new Date());
				
				if("N".equalsIgnoreCase(Utility.getAppConfigValue("FX_COMPONENT_NEW_SCHEDULAR")))
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
		
      /*try
		
		{
    	  if("Y".equalsIgnoreCase(Utility.getAppConfigValue("AUTO_BILLING_SCHEDULAR_1")))
			{
			Utility.LOG(true,true,"FXScheduler for H/W Sale Auto Billing job started at : "+new Date());
			new FX_AUTOLINES_BILLINGTRIGGER().AUTOBILLING();
			Utility.LOG(true,true,"FXScheduler for H/W Sale Auto Billing job ended at: "+new Date());
			}
			
		}
		catch (Throwable ex) {
			Utility.onEx_LOG_RET_NEW_EX(ex,"run","FXScheduler for H/W Sale Auto Billing ",null,true,true);
			Utility.LOG(true,true,"FXScheduler for H/W Sale Auto Billing job ended at : "+new Date());
		}*/
		
		  try
			
			{
			  if("Y".equalsIgnoreCase(Utility.getAppConfigValue("AUTO_BILLING_SCHEDULAR_2")))
				{
				Utility.LOG(true,true,"FXScheduler for Push LocDataTo Fx job started at : "+new Date());
				new PushLocDataToFx().PushLocDataIntoFx();
				Utility.LOG(true,true,"FXScheduler for Push LocDataTo Fx  job ended at: "+new Date());
				}
				
			}
			catch (Throwable ex) {
				Utility.onEx_LOG_RET_NEW_EX(ex,"run","FXScheduler for Push LocDataTo Fx job started at ",null,true,true);
				Utility.LOG(true,true,"FXScheduler for Push LocDataTo Fx  job ended at : "+new Date());
			}
		  try
			
			{
			  if("Y".equalsIgnoreCase(Utility.getAppConfigValue("FX_GAM_SYNC_SCHEDULAR")))
				{
				Utility.LOG(true,true,"FXScheduler for pushGAMsToFX job started at : "+new Date());
				new FxSchedulerForGAM().pushGAMsToFX();
				Utility.LOG(true,true,"FXScheduler for pushGAMsToFX  job ended at: "+new Date());
				}
				
			}
			catch (Throwable ex) {
				Utility.onEx_LOG_RET_NEW_EX(ex,"run","FXScheduler for pushGAMsToFX job started at ",null,true,true);
				Utility.LOG(true,true,"FXScheduler for pushGAMsToFX  job ended at : "+new Date());
			}
			
			try			
			{
			  if("Y".equalsIgnoreCase(Utility.getAppConfigValue("FX_CUMULATIVE_SYNC_SCHEDULAR")))
				{
				Utility.LOG(true,true,"FXScheduler for pushCumulativeToFX job started at : "+new Date());
				new FxSchedulerForCumulative().pushCumulativeToFX(0);
				Utility.LOG(true,true,"FXScheduler for pushCumulativeToFX  job ended at: "+new Date());
				}
				
			}
			catch (Throwable ex) {
				Utility.onEx_LOG_RET_NEW_EX(ex,"run","FXScheduler for pushCumulativeToFX job started at ",null,true,true);
				Utility.LOG(true,true,"FXScheduler for pushCumulativeToFX  job ended at : "+new Date());
			}
					
		
			try{
				if("N".equalsIgnoreCase(Utility.getAppConfigValue("FX_COMPONENT_NEW_SCHEDULAR")))
				{
					Utility.LOG(true,true,"FXScheduler for schedulerForArborUpdate job started at : "+new Date());
					new ViewOrderDao().schedulerForArborUpdate();
					Utility.LOG(true,true,"FXScheduler for schedulerForArborUpdate  job ended at : "+new Date());
				}
			}catch(Throwable ex)
			{
				Utility.onEx_LOG_RET_NEW_EX(ex,"run","FXScheduler for schedulerForArborUpdate job started at ",null,true,true);
				Utility.LOG(true,true,"FXScheduler for schedulerForArborUpdate  job ended at : "+new Date());
			}
						
			try			
			{
			  if("Y".equalsIgnoreCase(Utility.getAppConfigValue("FX_SCH_FOR_ACCNT_UPDATE")))
				{
				Utility.LOG(true,true,"FXScheduler for AccountUpdate job started at : "+new Date());
				new FXSchedulerForAccountUpdate().updateAccount();
				Utility.LOG(true,true,"FXScheduler for AccountUpdate  job ended at: "+new Date());
				}
				
			}
			catch (Exception ex) {
				Utility.onEx_LOG_RET_NEW_EX(ex,"run","FXScheduler for AccountUpdate job started at ",null,true,true);
				Utility.LOG(true,true,"FXScheduler for AccountUpdate  job ended at : "+new Date());
			}
	}
	
	
	public void exchangeRateScheduler(){
		try			
		{
		  if("Y".equalsIgnoreCase(Utility.getAppConfigValue("FX_CURRENCY_EXCHANGERATE_SCHEDULAR")))
			{
			  int day;//, month, year;
		      int hour;// second, minute,
		      Connection conn=null;
		      PreparedStatement psmt=null;
		      GregorianCalendar date = new GregorianCalendar();
		 
		      day = date.get(Calendar.DAY_OF_MONTH);
		      hour = date.get(Calendar.HOUR_OF_DAY);
		      
		      System.out.println("Before Checking  Condition for 1st Date============ ");
		      if(day==1 && (hour==10 || hour==14 )){
					System.out.println("After Checking  Condition for 1st Date============ ");
					
					Date d1=null;
					Date d2= new Date();
					
					conn=DbConnection.getConnectionObject();
					psmt=conn.prepareStatement(" SELECT INSERTDATE FROM IOE.TTRACKINSERTDATE where TABLENAME='TM_EXCHANGE_RATE' ");
					ResultSet rs=psmt.executeQuery();
					while(rs.next()){
									 d1=rs.getDate("INSERTDATE");
						 			}
					
					if(d1.getTime() != d2.getTime()){
						
					Utility.LOG(true,true,"FXScheduler for CUrrencyExchangeRate Updation job started at : "+new Date());
					new FxSchedulerTasksForPackage().updateCurrencyChangeRate();
					Utility.LOG(true,true,"FXScheduler for CUrrencyExchangeRate Updation job ended at: "+new Date());
					
					}
			}
			
		}
		}catch (Throwable ex) {
				Utility.onEx_LOG_RET_NEW_EX(ex,"run","FXScheduler for CUrrencyExchangeRate Updation job started at ",null,true,true);
				Utility.LOG(true,true,"FXScheduler for CUrrencyExchangeRate   Updation job ended at : "+new Date());
			}
	
	}
}
