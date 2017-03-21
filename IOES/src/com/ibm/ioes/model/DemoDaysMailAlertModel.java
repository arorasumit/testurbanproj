package com.ibm.ioes.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;



import com.ibm.ioes.dao.DemoDaysMailAlertDao;
import com.ibm.ioes.demodays.dto.DemoDaysMailAlertDto;
import com.ibm.ioes.forms.NewOrderDto;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Messages;
import com.ibm.ioes.utilities.SendMail;

public class DemoDaysMailAlertModel {

	public int sendDemoMailAlert(String flag)  throws Exception {
		int status=0;
		Connection conn= null;
		String ccList[]=null;
		String bcc[]=null;
		String from=null;
		
		
		try
		{	
			conn = DbConnection.getConnectionObject();
			SendMail sendMail=new SendMail();
			PreparedStatement ps = null;
			String updateQuery="";
			String output ="";
			DemoDaysMailAlertDao daoObj=new DemoDaysMailAlertDao(); 
			DemoDaysMailAlertDto objdto=null;
			ArrayList<DemoDaysMailAlertDto> demodaysDetailsdto=new ArrayList<DemoDaysMailAlertDto>();
			ArrayList<DemoDaysMailAlertDto> actmgrdetailslist=new ArrayList<DemoDaysMailAlertDto>();
			actmgrdetailslist=daoObj.getActmgrDetailsforDemoOrder(conn,flag);  
			for (DemoDaysMailAlertDto actmgridList : actmgrdetailslist) {
			try{
				demodaysDetailsdto=daoObj.getDemoDaysMailAlertDetails(conn,actmgridList.getActmgrid(),flag);
				if(demodaysDetailsdto.size()>0)
				{
					String toList[]={actmgridList.getAm_emailid(),actmgridList.getPm_emailid(),actmgridList.getColMgr_emailid()};	
					StringBuffer eBody=new StringBuffer();
					String eSubject=Messages.getMessageValue("Demo days expiry Mail ");
					eBody.append("<HTML><BODY>");
					eBody.append("Dear All,<BR><BR>");
					eBody.append("With reference to following demo orders, please note that their closure date is approaching as mentioned below, pl arrange to regularize the order else they will be disconnected after the end date specified<BR><BR>");
					eBody.append("<TABLE border=\"1\">");
					eBody.append("<TR font-size:16px bgcolor=#F7F7E7>");
	                eBody.append("<TH>");
	                eBody.append("Order No");
	                eBody.append("</TH>");		                    
	                eBody.append("<TH>");
	                eBody.append("Account No");
	                eBody.append("</TH>");	
	                eBody.append("<TH>");
	                eBody.append("Account Name");
	                eBody.append("</TH>");	
	                eBody.append("<TH>");
	                eBody.append("LSI No");
	                eBody.append("</TH>");	
	                eBody.append("<TH>");
	                eBody.append("Demo End Date");
	                eBody.append("</TH>");	
	                eBody.append("</TR>");   
	                for (DemoDaysMailAlertDto demdayDetailsList : demodaysDetailsdto) { 
			       			eBody.append("<TR font-size: 15px>");
			       			eBody.append("<TD>");
				        	eBody.append(demdayDetailsList.getOrderno());
				        	eBody.append("&nbsp");
					        eBody.append("</TD>");		                    
					        eBody.append("<TD>");
				        	eBody.append(demdayDetailsList.getAccountno());
				        	eBody.append("&nbsp");
					        eBody.append("</TD>");	
					        eBody.append("<TD>");
				        	eBody.append(demdayDetailsList.getAccountname());
				        	eBody.append("&nbsp");
					        eBody.append("</TD>");	
					        eBody.append("<TD>");
					        eBody.append(demdayDetailsList.getLogical_si_no());
					        eBody.append("&nbsp");
					        eBody.append("</TD>");
					        eBody.append("<TD>");
					        eBody.append(demdayDetailsList.getDemo_end_date());
					        eBody.append("&nbsp");
					        eBody.append("</TD>");	
					       
				        	eBody.append("</TR>");      
		        		} 
	                    eBody.append("</TABLE>");
						eBody.append("</BODY></HTML>");
						
						System.out.println(eBody);
 						int retStatus=sendMail.postMailWithAttachment(toList,ccList, bcc, eSubject, eBody.toString(), from,null);
					    if(retStatus==1)
						{
							String success= " Demo days expiry Mail Sent Successfully to Managers: "+ toList;
							/*String query="update ioe.TPOSERVICEMASTER set DEMO_MAIL_SENT=?  where LOGICAL_SI_NO = ?";
							ps = conn.prepareStatement(query);
						    for(int count=0; count<demodaysDetailsdto.size();count++)
						    {
						    		objdto=demodaysDetailsdto.get(count);
								    ps.setInt(1,2);
								    ps.setInt(2,Integer.parseInt(objdto.getLogical_si_no()));
								    ps.addBatch();

						    }
						    	int[] recordsAffected = ps.executeBatch();
							    if(recordsAffected.length != demodaysDetailsdto.size())
								 {
									 	conn.rollback();
									 //return;
								 } 
							     else
								 {
									 	conn.commit();
								 }*/
						}	    
						
					    else
					    {
							String failure= " Demo days expiry Mail Sending Failed  to Managers : \n EmailId : "+ toList[0]
							                   + "\n	EBODY :"+eBody;                                                            		
							com.ibm.ioes.utilities.Utility.LOG(failure);
						}
				}
			}	// try               
		catch (Exception ex) {
		
			String exc= " Exception occured in Generating Mail to Manager: ";
			com.ibm.ioes.utilities.Utility.LOG(exc);
			com.ibm.ioes.utilities.Utility.LOG(ex);
			
		}		

			}}// for				

 // try 

	catch (Exception ex) {
		String exc= " Exception occured in Sending Mail to Manager: ";
		com.ibm.ioes.utilities.Utility.LOG(exc);
		com.ibm.ioes.utilities.Utility.LOG(ex);
		
	}
	
	finally
	{
		try 
		{	DbConnection.freeConnection(conn);
			
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	return status;
	
}


}