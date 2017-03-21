package com.ibm.ioes.model;

import java.sql.Connection;
import java.util.ArrayList;
import com.ibm.ioes.dao.PMWelcomeMailDao;
import com.ibm.ioes.newdesign.dto.PMWelcomeMailDto;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Messages;
import com.ibm.ioes.utilities.SendMail;
import com.ibm.ioes.utilities.Utility;

public class PMWelcomeMailModel {

	public int sendPMWelcomeMailAlert(long flag)  throws Exception {
		int status=0;
		Connection conn= null;
		String ccList[]=null;
		String bcc[]=null;
		String from=null;
		String image="data:image/jpeg;base64,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";
		
		try
		{	
			conn = DbConnection.getConnectionObject();
			SendMail sendMail=new SendMail();
			PMWelcomeMailDao daoObj=new PMWelcomeMailDao(); 
			ArrayList<PMWelcomeMailDto> pmWelcomedto=new ArrayList<PMWelcomeMailDto>();
			ArrayList<PMWelcomeMailDto> prjctmgrdetailslist=new ArrayList<PMWelcomeMailDto>();
			prjctmgrdetailslist=daoObj.getPrjctmgrDetailsforWelcomemail(conn,flag);  
			for (PMWelcomeMailDto prjctmgridList : prjctmgrdetailslist) {
			try{
				pmWelcomedto=daoObj.getPMWelcomeMailAlertDetails(conn,prjctmgridList.getActmgrid(),flag);
				if(pmWelcomedto.size()>0)
				{
					String toList[]={prjctmgridList.getAm_emailid(),prjctmgridList.getPm_emailid()};	
					StringBuffer eBody=new StringBuffer();
					String eSubject=Messages.getMessageValue("PM Welcome Mail ");
					
					eBody.append("<!DOCTYPE html><HTML><BODY >");
					eBody.append("<DIV  style='width: 800px; word-wrap: break-word; margin-left:90px;'>");
					eBody.append("<table border= '3px'   style='border-color:#F00; border-style: solid; background-color:#FFFFE0; border-collapse:collapse;'>");
					eBody.append("<tr><td style='border:hidden;padding-left: 30px; padding-right: 30px; '>");
					eBody.append("<table width='100%' >");
					eBody.append("<tr >");
					
					eBody.append("<td align='right'><img  src='"+image+"'  ></td>");
					eBody.append("</tr>");
					eBody.append("<tr><td style='text-align: center; '> <B >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
					eBody.append("<font style='font-family:arial;font-size:18px;'><U> Welcome Letter </U></font></B> </td>");
					eBody.append("</tr><TR> </TR><TR> </TR>");
					eBody.append("</br><TR>");

					eBody.append("<TD  style='align: left; ' ><p ><font style='font-family:arial;font-size:14px;'><B>Ref: &nbsp;&nbsp;&nbsp;</B><U>"+prjctmgridList.getRefNo());

					

					eBody.append("</U></font> &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;  &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;  <font style='font-family:arial;font-size:14px;'>Date:&nbsp;<U>"+prjctmgridList.getOrderdate()+"</U></font></p></TD></TR>");
					eBody.append("<tr><td style='align: left; '><font style='font-family:arial;font-size:14px;'>Dear Mr.&nbsp <U>"+prjctmgridList.getContactname()+"</U><p></p></font></td></tr><TR> </TR>");

					
					eBody.append("<tr><td style='align: left;'>");					
					eBody.append("<font style='font-family:arial;font-size:14px;'><p style='text-align:justify;'>"+Utility.getAppConfigValue("PM_WELCOME_MAIL_BODY_MAILBODY_1") +"</br></p></font></td></tr>" );
					eBody.append("<TR> </TR><TR> </TR><tr><td style='align: left;'><font style='font-family:arial;font-size:14px;'><p style='text-align:justify;'>"+Utility.getAppConfigValue("PM_WELCOME_MAIL_BODY_MAILBODY_2") +"</br></p></font></td></tr>");
					eBody.append("<TR> </TR><TR> </TR><tr><td style='align: left;'>");
					eBody.append("<font style='font-family:arial;font-size:14px;'><p style='text-align:justify;'><B><U>"+prjctmgridList.getPmname());
					eBody.append("</U></B>&nbsp;");
					eBody.append(""+Utility.getAppConfigValue("PM_WELCOME_MAIL_BODY_MAILBODY_3")+"</br></br></p></font></td></tr>");
					
					/*Start of reference address */
					eBody.append("<TR> </TR><TR> </TR><tr><td  style=' align: left; font-family:arial;font-size:14px;'>");
					eBody.append("<TABLE border=\"1\" width='90%'  >");
			 
	                for (PMWelcomeMailDto welcomeMailDetailsList : pmWelcomedto) { 
			       			eBody.append("<TR ><TD width='20%'>");
			       			eBody.append("Name:");
			       			eBody.append("</TD><TD width='70%'>");
			       			eBody.append(welcomeMailDetailsList.getName());
				        	eBody.append("</TD></TR>");	
					        
							eBody.append("<TR ><TD width='20%'>");
			       			eBody.append("Address:");
			       			eBody.append("</TD><TD width='70%'>");
					        
				        	eBody.append(welcomeMailDetailsList.getAddress());
				        	
					        eBody.append("</TD></TR>");	
					        eBody.append("<TR ><TD width='20%'>");
			       			eBody.append("Email:");
			       			eBody.append("</TD><TD width='70%'>");
					        
				        	eBody.append(welcomeMailDetailsList.getEmail());
				        	
					        eBody.append("</TD></TR>");
					        eBody.append("<TR ><TD width='20%'>");
			       			eBody.append("Contact:");
			       			eBody.append("</TD><TD width='70%'>");
					        
					        eBody.append(welcomeMailDetailsList.getContactno());
					       
					        eBody.append("</TD></TR>");
					        eBody.append("<TR ><TD width='20%'>");
			       			eBody.append("FAX:");
			       			eBody.append("</TD><TD width='70%'>");
					        eBody.append(welcomeMailDetailsList.getFaxno());
					        eBody.append("</TD></TR>");	
		        		} 
	                    eBody.append("</TABLE></td></tr>");
					
	                 
					
					   
	                    eBody.append("<tr><td style='align:left;'>");
	                    eBody.append("<p  style='text-align:justify;'></br><font style='font-family:arial;font-size:14px;'>"+Utility.getAppConfigValue("PM_WELCOME_MAIL_BODY_MAILBODY_4")+"</font></br></BR></p></td></tr>");
	                    //eBody.append("<tr><td STYLE='border:none;'></td><td STYLE='border:none;'></td></tr>");
						//eBody.append("<tr><td STYLE='border:none;'></td><td STYLE='border:none;'></td></tr>");
						eBody.append(" <tr><td style='align:left; font-family:arial;font-size:14px;' >");
						eBody.append("<TABLE border=\"1\" width='90%' >");
						eBody.append("<TR  bgcolor=#F7F7E7><TH> ");
						
		                eBody.append(" Escalation Levels");
		                eBody.append("</TH><TH>");
		                
		                eBody.append("Name");
		                eBody.append("</TH><TH>");		                    
		                
		                eBody.append("Email");
		                eBody.append("</TH><TH>");	
		                
		                eBody.append("Contact No");
		                eBody.append("</TH></TR>");	
		                
		                for (PMWelcomeMailDto welcomeescalationDetailsList : pmWelcomedto) { 
				       			eBody.append("<TR ><TD width='20%'>");
				       			
					        	eBody.append("Escalation Level1");
					        	eBody.append("&nbsp </TD><TD width='25%'>");
					        	eBody.append(welcomeescalationDetailsList.getL1name());
					        	eBody.append("&nbsp </TD><TD width='25%'>");
						       
					        	eBody.append(welcomeescalationDetailsList.getL1email());
					        	eBody.append("&nbsp </TD><TD width='20%'>");
						       	eBody.append(welcomeescalationDetailsList.getL1contactno());
					        	eBody.append("&nbsp </TD></TR>");
						        eBody.append("<TR ><TD width='20%'>");
						        
						        eBody.append("Escalation Level2");
						        eBody.append("&nbsp </TD><TD width='25%'>");
						        eBody.append(welcomeescalationDetailsList.getL2name());
						        eBody.append("&nbsp </TD><TD width='25%'>");
						        eBody.append(welcomeescalationDetailsList.getL2email());
						        eBody.append("&nbsp </TD><TD width='20%'>");
						        eBody.append(welcomeescalationDetailsList.getL2contactno());
						        eBody.append("&nbsp </TD></TR>");
						        eBody.append("<TR ><TD width='20%'>");
						        
						        eBody.append("Escalation Level3");
						        eBody.append("&nbsp </TD><TD width='25%'>");
						        eBody.append(welcomeescalationDetailsList.getL3name());
						        eBody.append("&nbsp </TD><TD width='25%'>");
						        eBody.append(welcomeescalationDetailsList.getL3email());
						        eBody.append("&nbsp </TD><TD width='20%'>");
						        eBody.append(welcomeescalationDetailsList.getL3contactno());
						        eBody.append("&nbsp </TD></TR>");
						        
					        	
						        
			        		} 
		                    eBody.append("</TABLE></td></tr>");
							//eBody.append("<tr><td STYLE='border:none;'></td><td STYLE='border:none;'></td></tr>");
							eBody.append("<tr><td style='align:left;font-family:arial;font-size:14px;'>");
		                    eBody.append("<p  style='text-align:justify;'></br>"+Utility.getAppConfigValue("PM_WELCOME_MAIL_BODY_MAILBODY_5")+"</br></br></BR></p>");
		                    eBody.append("</td></tr> ");
							
							eBody.append("<tr><td style='align:left;'><B>Best Regards,</B></td></tr>");
		                    eBody.append("<tr><td style='align:left;'><B>Bharti Airtel Ltd.</B> </td></tr> ");
		                    /*end of reference address */
							eBody.append("</table></td></tr>");
							
							
						eBody.append("</table></DIV></BODY></HTML>");
						
						//System.out.println(eBody);
 						int retStatus=sendMail.postMailWithAttachment(toList,ccList, bcc, eSubject, eBody.toString(), from,null);
					    if(retStatus==1)
					    {
							String success= " PM Welcome Mail Sent Successfully to ProjectManager: "+ toList;
							com.ibm.ioes.utilities.Utility.LOG(success);
						}	    
						
					    else
					    {
							String failure= " PM Welcome Mail  Sending Failed  to Managers : \n EmailId : "+ toList[0]
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
		{	
			DbConnection.freeConnection(conn);
			
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