//Tag Name Resource Name  Date		CSR No			Description
//[001]	 Manisha Garg	3-Sep-12	00-05422		Created for Auto Disconnection Order
package com.ibm.ioes.schedular;
import java.util.Date;
import java.util.TimerTask;
import com.ibm.ioes.utilities.Utility;

	public class AutoCreationDiscntOrderSchedular extends TimerTask{
		@Override
		public void run() {
			
			try{
			
				if("Y".equalsIgnoreCase(Utility.getAppConfigValue("AUTO_CREATION_DIS_ORDER_SCHEDULER")))
				{
					
				try {
					
					Utility.LOG(true,true,"AUTO_CREATION_DISCONNECT_ORDER_SCHEDULAER job started at : "+new Date());
					//run job
					
					new AutoCreation_DisconnectOrder().RunAutoDisconnectOrderCreation();
					Utility.LOG(true,true,"AUTO_CREATION_DISCONNECT_ORDER_SCHEDULAER job ended at : "+new Date());
					
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
			


