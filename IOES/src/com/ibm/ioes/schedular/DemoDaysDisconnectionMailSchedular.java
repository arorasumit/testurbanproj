package com.ibm.ioes.schedular;
import java.util.Date;
import java.util.TimerTask;



import com.ibm.ioes.utilities.Utility;

public class DemoDaysDisconnectionMailSchedular extends TimerTask {
	
		public void run() {
				
				try{
				
					if("Y".equalsIgnoreCase(Utility.getAppConfigValue("DEMO_DAYS_DISCONNECTION_ORDER_SCHEDULAR")))
					{
						
					try {
						
						Utility.LOG(true,true,"Demo Disconnection Order Creation Schedular job started at : "+new Date());
						//run job
						
						new DemoDisconnectionOrderDao().DemoDisconnectOrderCreation();
						Utility.LOG(true,true,"Demo Disconnection Order Creation Schedular job ended at : "+new Date());
						
					} catch (Exception e) {
						Utility.LOG(true, true, e, "");
					}
					
					
					catch(Throwable ex)
					{
						Utility.LOG(true, true, ex, "");
					}
							
			     }			
				
			}
				
				catch(Throwable ex)
				{
					Utility.LOG(true, true, ex, "");
				}
						
		}
			
		}

