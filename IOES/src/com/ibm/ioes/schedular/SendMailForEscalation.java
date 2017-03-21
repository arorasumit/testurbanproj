package com.ibm.ioes.schedular;

import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;

import com.ibm.ioes.dao.EscalationDao;
import com.ibm.ioes.utilities.Utility;

public class SendMailForEscalation extends TimerTask{
	public SendMailForEscalation( ) {
        
    }
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			
				
			try {
				//Random random = new Random();
				Utility.LOG(true,true,"SendMailForEscalation job started at : "+new Date());
				//run job
				System.err.println("send "+Integer.parseInt(Utility.getAppConfigValue("ESCALATION_MAIL_SENDING_START")));
				Calendar calendar = Calendar.getInstance();
				int day = calendar.get(Calendar.DAY_OF_WEEK);
				Utility.LOG(true,true,"day   : "+day);
				Utility.LOG(true,true,"ESCALATION_MAIL_SENDING_START   : "+Integer.parseInt(Utility.getAppConfigValue("ESCALATION_MAIL_SENDING_START")));
				
				if(Integer.parseInt(Utility.getAppConfigValue("ESCALATION_MAIL_SENDING_START"))==1  && day!=1 && day!=7){
					Utility.LOG(true,true,"Sending   : ");
					EscalationDao escalationDaoObj=new EscalationDao();
					escalationDaoObj.getEscalationMailDetails();
					Utility.LOG(true,true,"SendMailForEscalation job ended at : "+new Date());
				}
				
		        
			} catch (Exception e) {
				Utility.LOG(true, true, e, "");
				e.printStackTrace();
			}
			
			
			catch(Throwable ex)
			{
				Utility.LOG(true, true, ex, "");
				ex.printStackTrace();
			}
					
	    // }			
		
	}
		
		catch(Throwable ex)
		{
			Utility.LOG(true, true, ex, "");
			ex.printStackTrace();
		}
		
        

	}
	

}

