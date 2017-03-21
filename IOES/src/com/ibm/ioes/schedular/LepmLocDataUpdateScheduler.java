package com.ibm.ioes.schedular;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.TimerTask;

import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Utility;

	public class LepmLocDataUpdateScheduler extends TimerTask{
		@Override
		public void run() {
			
			try{
			
				if("Y".equalsIgnoreCase(Utility.getAppConfigValue("LEPM_LOC_DATA_UPDATE_SCHEDULER")))
				{
					
				try {
					
					Utility.LOG(true,true,"LEPM_LOC_DATA_UPDATE_SCHEDULER job started at : "+new Date());
					//run job
					
					new LepmLocDataUpdate().updateLepmLocData();
					Utility.LOG(true,true,"LEPM_LOC_DATA_UPDATE_SCHEDULER job ended at : "+new Date());
					
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
			
			//LepmCharges Flag Updation by Nagarjuna
			try{
				
				if("Y".equalsIgnoreCase(Utility.getAppConfigValue("LEPM_LOCCHARGE_DATA_UPDATE_SCHEDULER")))
				{
					Connection conn=null;
					PreparedStatement psmt=null;
				try {
					
					Utility.LOG(true,true,"LEPM_CHARGEFLAG_UPDATE_SCHEDULER job started at : "+new Date());
					//run job
					
					int noofdays=0;
					conn=DbConnection.getConnectionObject();
					psmt=conn.prepareStatement(" select (days (current date) - days (date(VARCHAR_FORMAT((SELECT INSERTDATE FROM IOE.TTRACKINSERTDATE where TABLENAME='TCHARGES_INFO'), 'YYYY-MM-DD')))) AS NOOFDAYS from sysibm.sysdummy1 ");
					ResultSet rs=psmt.executeQuery();
					while(rs.next()){
									 noofdays=rs.getInt("NOOFDAYS");
						 			}
					
					if(noofdays >= 2){
					LepmLocDataUpdate.InsertLEPMChargeDatainIB2BTAble();
					Utility.LOG(true,true,"LEPM_CHARGEFLAG_UPDATE_SCHEDULER job ended at : "+new Date());
					}
					
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
			//end
	}
		
	}
			


