package com.ibm.ioes.schedular;
//Tag Name Resource Name  Date		CSR No			Description
//[001]	 MANISHA GARG	13-Feb-13	00-05422		TO Send Mail to Act Mgr for Pending Orders
import java.util.Date;
import java.util.TimerTask;

import com.ibm.ioes.dao.NewOrderDao;
import com.ibm.ioes.model.NewOrderModel;
import com.ibm.ioes.utilities.Utility;

public class DraftOrderMailScheduler  extends TimerTask{

	public void run() {
		
		try{
		
			if("Y".equalsIgnoreCase(Utility.getAppConfigValue("PENDING_ORDER_MAIL_SCHEDULAER")))
			{
				
			try {
				
				Utility.LOG(true,true,"PENDING_ORDER_MAIL_SCHEDULAER job started at : "+new Date());
				//run job
				
				new NewOrderModel().sendPendingOrderMail();
				Utility.LOG(true,true,"PENDING_ORDER_MAIL_SCHEDULAER job ended at : "+new Date());
				
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