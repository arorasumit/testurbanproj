package com.ibm.ioes.ei.m6;

import java.util.Date;
import java.util.TimerTask;

import com.ibm.ioes.utilities.Utility;

public class ProcessXML extends TimerTask {

	public void run() {

		try
		{
			Utility.LOG(true,true,"ProcessXML  job started at : "+new Date());			
			new ProcessResponseXML().processM6Response();
			
		}catch (Exception ex) {
			Utility.onEx_LOG_RET_NEW_EX(ex,"run","ProcessXML",null,true,true);
			Utility.LOG(true,true,"ProcessXml job ended at : "+new Date());
		}
	}
}
