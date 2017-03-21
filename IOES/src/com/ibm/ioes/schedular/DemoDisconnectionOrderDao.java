package com.ibm.ioes.schedular;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.ibm.ioes.dao.DemoDaysMailAlertDao;
import com.ibm.ioes.demodays.dto.DemoDaysMailAlertDto;
import com.ibm.ioes.forms.DisconnectOrderDto;
import com.ibm.ioes.model.DemoDaysMailAlertModel;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Messages;
import com.ibm.ioes.utilities.SendMail;
import com.ibm.ioes.utilities.Utility;


public class DemoDisconnectionOrderDao {
	public static String spcreateAutoDisOrder  = "{CALL IOE.SP_CREATE_DEMO_DAYS_DISCONNECTION_ORDER_AUTOMATION(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	public static String sqlGet_LSI_For_AutoDisOrder = "{CALL IOE.SP_GET_DEMO_DISCONNECTION_ORDER()}";
	public void DemoDisconnectOrderCreation() throws Exception 
	{
		Connection conn = null;
	    CallableStatement csGetLSIDetails =null;
	    CallableStatement csAutoCreationOrder =null;
	    ResultSet rsGetLSIDetails =null;
	    DemoDaysMailAlertModel model=new DemoDaysMailAlertModel();
	    ArrayList<DisconnectOrderDto> list = new ArrayList<DisconnectOrderDto>();
		try {
    	 	conn = DbConnection.getConnectionObject();
			conn.setAutoCommit(false);
			csGetLSIDetails= conn.prepareCall(sqlGet_LSI_For_AutoDisOrder);
			rsGetLSIDetails=csGetLSIDetails.executeQuery();
			while (rsGetLSIDetails.next()) 
				{
				DisconnectOrderDto objDisOrderDto= new DisconnectOrderDto();
				objDisOrderDto.setLsi(rsGetLSIDetails.getInt("LOGICAL_SI_NO"));
				objDisOrderDto.setAm_emailid(rsGetLSIDetails.getString("EMAIL_AM"));
				objDisOrderDto.setPm_emailid(rsGetLSIDetails.getString("EMAIL_PM"));
				objDisOrderDto.setColMgr_emailid(rsGetLSIDetails.getString("COLMGR_EMAILID"));
				objDisOrderDto.setDemo_end_date(rsGetLSIDetails.getString("DEMO_END_DATE"));
				objDisOrderDto.setLogical_si_no(rsGetLSIDetails.getString("LOGICAL_SI_NO"));
				objDisOrderDto.setAccountname(rsGetLSIDetails.getString("ACCOUNTNAME"));
				objDisOrderDto.setAccountno(rsGetLSIDetails.getString("ACCOUNTNO"));
				list.add(objDisOrderDto);
				}
				for(int i=0;i<list.size();i++)
			     {
			        try
			           {	   
				        	DisconnectOrderDto objdto=null;
			        		objdto=list.get(i);
			        		csAutoCreationOrder= conn.prepareCall(spcreateAutoDisOrder);
			        		csAutoCreationOrder.setLong(1,(objdto.getLsi()));
			        		csAutoCreationOrder.setString(2,"");
			        		csAutoCreationOrder.setString(3,"");
			        		csAutoCreationOrder.setString(4,"");
			        		csAutoCreationOrder.setString(5, "");
			        		csAutoCreationOrder.setString(6,"");
			        		csAutoCreationOrder.setString(7,"" );
			        		csAutoCreationOrder.setString(8 , "");
			        		csAutoCreationOrder.setLong(9,0);
			        		csAutoCreationOrder.setString(10,"");
			        		csAutoCreationOrder.setString(11,"");
			        		csAutoCreationOrder.setString(12,"");
			        		csAutoCreationOrder.setLong(13,0);
			        		csAutoCreationOrder.execute();
			        		String msgCode =csAutoCreationOrder.getString(10);
			        		String msg =csAutoCreationOrder.getString(11);
			        		String status =csAutoCreationOrder.getString(12);
			        		String orderno=csAutoCreationOrder.getString(13);
			        		
			        		if("-1".equals(msgCode))
			        		{
								System.out.println("errors in proc : IOE.SP_CREATE_DEMO_DAYS_DISCONNECTION_ORDER_AUTOMATION="+msgCode+"for LSI"+objdto.getLsi());
							}
			        		else
			        		{
			        		System.out.println(orderno + " :Disconnection Order Created Successfully for LSI"+objdto.getLsi());
							conn.commit();
			        		sendDemoMailAlert_disorder(objdto,orderno);
						    
			        		}
			        
				     }
		      catch (Exception e) 
			    {
		        	e.printStackTrace();
		        	conn.rollback();
			    }			
		  }
	  }	     
    catch (Exception e) 
    {
	   e.printStackTrace();
    }
    finally{
	try 
		{
			DbConnection.closePreparedStatement(csAutoCreationOrder);
			DbConnection.closeResultset(rsGetLSIDetails);
			DbConnection.closePreparedStatement(csGetLSIDetails);
			DbConnection.freeConnection(conn);
		} 
		catch (Exception e) 
		{
		e.printStackTrace();
		}
	}	
  }
	
	
	public int sendDemoMailAlert_disorder(DisconnectOrderDto objdto,String orderno)  throws Exception {
		int status=0;
		Connection conn= null;
		String ccList[]=null;
		String bcc[]=null;
		String from=null;
		SendMail sendMail=new SendMail();
			try{
					String toList[]={objdto.getAm_emailid(),objdto.getPm_emailid(),objdto.getColMgr_emailid()};	
					StringBuffer eBody=new StringBuffer();
					String eSubject=Messages.getMessageValue("Demo days expiry Mail ");
					eBody.append("<HTML><BODY>");
					eBody.append("Dear All,<BR><BR>");
					eBody.append("Demo Disconnection Order has been generated due to Demo End Date has been Expired for Logical SI NO : <BR><BR>");
					eBody.append("<TABLE border=\"1\">");
					eBody.append("<TR font-size:16px bgcolor=#F7F7E7>");
	                eBody.append("<TH>");
	                eBody.append("Disconnection Order No");
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
	               // content
	       			eBody.append("<TR font-size: 15px>");
	       			eBody.append("<TD>");
		        	eBody.append(orderno);
		        	eBody.append("&nbsp");
			        eBody.append("</TD>");		                    
			        eBody.append("<TD>");
		        	eBody.append(objdto.getAccountno());
		        	eBody.append("&nbsp");
			        eBody.append("</TD>");	
			        eBody.append("<TD>");
		        	eBody.append(objdto.getAccountname());
		        	eBody.append("&nbsp");
			        eBody.append("</TD>");	
			        eBody.append("<TD>");
			        eBody.append(objdto.getLogical_si_no());
			        eBody.append("&nbsp");
			        eBody.append("</TD>");	
			        eBody.append("<TD>");
			        eBody.append(objdto.getDemo_end_date());
			        eBody.append("&nbsp");
			        eBody.append("</TD>");	
		        	eBody.append("</TR>");      
	    	
		            eBody.append("</TABLE>");
					eBody.append("</BODY></HTML>");
						
						/*String mailDetails=" Going to Send AutoRenewal Mail for Account Manager : \n EmailId : "+ toList[0]
         					                   + "\n	EBODY :"+eBody;                                                            		
      					com.ibm.ioes.utilities.Utility.LOG(mailDetails);*/
						System.out.println(eBody);
 						int retStatus=sendMail.postMailWithAttachment(toList,ccList, bcc, eSubject, eBody.toString(), from,null);
				
					    if(retStatus==1)
						{
							String success= " Demo Disconnection Order Creation Mail has been  Sent Successfully to  Manager: "+ toList;
							com.ibm.ioes.utilities.Utility.LOG(success);
						}
					    else
					    {
							String failure= " Demo Disconnection Order Creation Mail has been Failed  to  Manager : \n EmailId : "+ toList[0]
							                   + "\n	EBODY :"+eBody;                                                            		
							com.ibm.ioes.utilities.Utility.LOG(failure);
						}
			}       
		catch (Throwable ex) {
		
			String exc= " Exception occured to Sent  Demo Disconnection Order Mail: ";
			com.ibm.ioes.utilities.Utility.LOG(exc);
			com.ibm.ioes.utilities.Utility.LOG(ex);
			Utility.LOG(ex);
			
		}		

	return status;
	
	}	
	
}