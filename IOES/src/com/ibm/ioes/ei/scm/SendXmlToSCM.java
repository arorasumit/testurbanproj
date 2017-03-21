package com.ibm.ioes.ei.scm;
//Tag Name Resource Name  Date		CSR No			Description
//[001]	 Kalpana	11-January-2014			New File to send request to SCM for third party
import java.util.Date;
import java.util.TimerTask;

import com.ibm.ioes.utilities.Utility;

public class SendXmlToSCM extends TimerTask {
	
	public void run() {
		// TODO Auto-generated method stub
		try
		{
			Utility.LOG(true,true,"SendXmlToSCM job started at : "+new Date());
			/*new SendToPRCreationSCM().sendToSCMJob();
			new SendToPRReuseSCM().sendToSCMJob();*/
			new SendToSCM().sendToSCMJob();
		}catch (Exception ex) {
			Utility.onEx_LOG_RET_NEW_EX(ex,"run","SendXmlToSCM",null,true,true);
			Utility.LOG(true,true,"SendXmlToSCM job ended at : "+new Date());
		}
	}

}
