package com.ibm.ioes.schedular;
import java.util.Date;
import java.util.TimerTask;

import com.ibm.ioes.model.DemoDaysMailAlertModel;
import com.ibm.ioes.utilities.SharepointMigration;
//import com.ibm.ioes.utilities.SharepointMigration;
import com.ibm.ioes.utilities.Utility;
public class SharepointScheduler extends TimerTask{

	@Override
	public void run() {
     System.out.println("entered in run method of scheduler");
		try{
			if("Y".equalsIgnoreCase(Utility.getAppConfigValue("UPLOAD_TO_SHAREPOINT_SCHEDULER")))
			{
				try {
					Utility.SPT_LOG(true,true,"-------------UPLOAD_TO_SHAREPOINT_SCHEDULER STARTED AT :--------------- "+new Date());
					
					new SharepointMigration().uploadAttachmentToDocLib();
                      
					Utility.SPT_LOG(true,true,"-------------UPLOAD_TO_SHAREPOINT_SCHEDULER ENDED AT :----------------- "+new Date());
				} 
				catch (Exception e) {
					Utility.SPT_LOG(true, true, e, "");
				}
				catch(Throwable ex)
				{
					Utility.SPT_LOG(true, true, ex, "");
				}

			}
		}
		catch(Throwable ex)
		{
			Utility.SPT_LOG(true, true, ex, "");
		}
	}
	public static void main(String[] args) {
      
		System.out.println("main started");
		SharepointScheduler spt = new SharepointScheduler();
		spt.run();
		System.out.println("main ended");
	}



}
