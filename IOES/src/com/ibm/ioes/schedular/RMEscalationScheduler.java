package com.ibm.ioes.schedular;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

import com.ibm.ioes.dao.EscalationDao;
import com.ibm.ioes.escalation.dto.EscalationMailDTO;
import com.ibm.ioes.utilities.Utility;


public class RMEscalationScheduler extends TimerTask{
	
	 
	 public RMEscalationScheduler() {
	        
	    }


	/**	
	*	This function send mail to reporting manager for respective role id
	*/
	public void run()
	{
		/*DBConnectionRetriever con = new DBConnectionRetriever();
		Connection crmcon = con.getCRMConnection();*/
		
		try
		{
			Calendar calendar = Calendar.getInstance();
			int day = calendar.get(Calendar.DAY_OF_WEEK);
			Utility.LOG(true,false,"day   : "+day);
			Utility.LOG(true,false,"ESCALATION_MAIL_INSERT_START   : "+Integer.parseInt(Utility.getAppConfigValue("ESCALATION_MAIL_INSERT_START")));
			if(Integer.parseInt(Utility.getAppConfigValue("ESCALATION_MAIL_INSERT_START"))==1 && day!=1 && day!=7){
				Utility.LOG(true,true,"Inserting   : ");
				ArrayList<EscalationMailDTO> lst=new ArrayList<EscalationMailDTO>();
				ArrayList<EscalationMailDTO> mailLst=new ArrayList<EscalationMailDTO>();
				EscalationDao escalationDaoObj=new EscalationDao();
				Map<String,String> tmpMap=new HashMap<String,String>();
				//get key value from app config table here
				tmpMap=escalationDaoObj.getAppConfigVal();
				//get escalation details
				lst=escalationDaoObj.makeEscalationMailDetails(tmpMap);
				Utility.LOG(true,false,lst.size()+"");
				if(lst!=null && lst.size()>0){
					Utility.LOG(true,false,mailLst.size()+"");
					mailLst=escalationDaoObj.makeEscalationConsolidatedMail(lst,tmpMap);

					if(mailLst!=null && mailLst.size()>0){
						escalationDaoObj.insertMailDetails(mailLst);
					}
				}
				if(Integer.parseInt(Utility.getAppConfigValue("ESCALATION_MAIL_SENDING_START"))==1){
					Utility.LOG(true,true,"Sending   : ");
					escalationDaoObj.getEscalationMailDetails();
					Utility.LOG(true,true,"SendMailForEscalation job ended at : "+new Date());
				}
			}
		}catch(Exception ex)
			{
				Utility.LOG(true,true,ex,null);
				ex.printStackTrace();
			}
		
		

	}
	

			
}
