package com.ibm.ioes.utilities;
//Tag Name Resource Name  Date		  		Description
//[001]	  LAWKUSH	    07-Feb-2013				Alert not generated post COPC approval
//[002]	 Manisha	    13-Feb-13	00-05422		Send Mail to Act Mgr which ORDERS ARE PENDING TO COMPLETE
//[003]	 Manisha	    13-Feb-13	00-05422		Send Mail to Act Mgr pm TO REASSIGN pm defect no 15032013010
import java.sql.Connection;
import java.sql.DriverManager;
//lawkush
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
//lawkush

import java.util.ArrayList;

import com.ibm.ioes.exception.IOESException;
import org.apache.log4j.Logger;



public class IB2BMail {
	
	private static final Logger logger;
	static 
	{
		logger = Logger.getLogger(IB2BMail.class);
	}
	public static Connection getConnectionObject() throws Exception {
		Connection conn = null;
		try {
			DriverManager.registerDriver(new com.ibm.db2.jcc.DB2Driver());
			conn = DriverManager.getConnection("jdbc:db2://10.24.62.127:50000/IOES_SEP", "db2admin","db2admin");
			//conn = getURL();
			
			// System.err.println("conn2"+conn);
		} catch (Exception e) {
			e.printStackTrace();
			throw (Exception) e;
		}
		return conn;
	}
	/*
	 * optionalConnection: connection is optional ,if it is passed that one is used,
	 * if not passed a new is created and finally freed.
	 * 
	 * 
	 */
	public static int  sendiB2BMail(IB2BMailDto mailDto,Connection optionalConnection,boolean isSMSAvailable,boolean isMailAvailable) throws IOESException
	{
		
		System.err.println("Start Sending E-Mail to " + mailDto.getTo().toString());
		int status=0;
		//fetch mail type from mailTo and retrieve mailTemplate
		String mailTemplateType=mailDto.getMailTemplateType();
		Connection iomsConn = null;
		SendMail sendMail=new SendMail();
		String to[]=null;
		String cc[]=null;
		String bcc[]=null;
		String subject=null;
        String message=null;
        String from=null;
        String attachmentPath=null;
        
        Connection conn=null;
        
        String mailTemplateBody=null;
        String mailTemplateSubject=null;
        String mailTemplateHeader=null;
        String mailTemplateFooter=null;
        String smsText = null;
        
        try {
        
        	if(optionalConnection==null)
        	{
        		conn=DbConnection.getConnectionObject();
        	}
        	else
        	{
        		conn=optionalConnection;
        	}
        	
	        IB2BMailDaoImpl conferenceMailDaoImpl=new IB2BMailDaoImpl();
	        IB2BMailDto values=conferenceMailDaoImpl.fetchEmailTemplate(conn,mailDto);
	        mailTemplateBody=(values.getMailTemplateBody());
	        mailTemplateSubject=(values.getMailTemplateSubject());
	        mailTemplateHeader=(values.getMailTemplateHeader());
	        mailTemplateFooter=(values.getMailTemplateFooter());	        
	        smsText = (values.getSmsText());
	        
	        to=mailDto.getTo();
	        //to[0]="arorasumit@in.ibm.com";
	        cc=mailDto.getCc();
	        bcc=mailDto.getBcc();
	        
	     // Start[001]
	        
	  		/*    if(IB2BMailDto.NEWORDER.equals(mailTemplateType))
	        {
	    		System.err.println("Fetching Email Template for New Order.....1");
	        	mailTemplateBody=mailTemplateBody.replaceAll("(?i)\\{\\{custName\\}\\}", mailDto.getAccountName());
	        	
	        	message=mailTemplateBody;
	        	mailTemplateSubject=mailTemplateSubject.replaceAll("(?i)\\{\\{orderno\\}\\}",  mailDto.getOrderNo());
	        	subject=mailTemplateSubject;
	        }
	        else*/
	        if(IB2BMailDto.PIEMAILTEMPLATE.equals(mailTemplateType)){
	        	mailTemplateBody=mailTemplateBody.replaceAll("(?i)\\{\\{SERVICEID\\}\\}", mailDto.getServiceID());
        	    message=mailTemplateBody;
	        	subject=mailTemplateSubject;
	        }
	        else if(IB2BMailDto.NEWMAILTEMPLATE.equals(mailTemplateType) || IB2BMailDto.NEWMAILTEMPLATE_CHANGE.equals(mailTemplateType))
	        {
	        	
	    		System.err.println("Fetching Email Template for Save Action");
	        	mailTemplateBody=mailTemplateBody.replaceAll("(?i)\\{\\{taskName\\}\\}", mailDto.getTaskName());
	        	mailTemplateBody=mailTemplateBody.replaceAll("(?i)\\{\\{orderno\\}\\}", mailDto.getOrderNo());
	        	mailTemplateBody=mailTemplateBody.replaceAll("(?i)\\{\\{orderType\\}\\}", mailDto.getOrderType());
	        	mailTemplateBody=mailTemplateBody.replaceAll("(?i)\\{\\{productName\\}\\}", mailDto.getProductName());
	        	mailTemplateBody=mailTemplateBody.replaceAll("(?i)\\{\\{custName\\}\\}", mailDto.getAccountName());
	        	mailTemplateBody=mailTemplateBody.replaceAll("(?i)\\{\\{isApproved\\}\\}", mailDto.getIsApproved());
	        	mailTemplateBody=mailTemplateBody.replaceAll("(?i)\\{\\{remarks\\}\\}", mailDto.getRemarks());
	        	mailTemplateBody=mailTemplateBody.replaceAll("(?i)\\{\\{taskOwner\\}\\}", mailDto.getTaskOwner());
	        	mailTemplateBody=mailTemplateBody.replaceAll("(?i)\\{\\{startDate\\}\\}", mailDto.getTaskStartDate());
	        	mailTemplateBody=mailTemplateBody.replaceAll("(?i)\\{\\{endDate\\}\\}", mailDto.getTaskEndDate());
	        	mailTemplateBody=mailTemplateBody.replaceAll("(?i)\\{\\{nextTaskName\\}\\}", mailDto.getNextTaskName());
	        	mailTemplateBody=mailTemplateBody.replaceAll("(?i)\\{\\{nextOwner\\}\\}", mailDto.getNextOwner());
	        	
	        	if("1".equalsIgnoreCase(mailDto.getIslasttask())){
	        		mailTemplateBody=mailTemplateBody.replaceAll("New Task \"<b>" + mailDto.getNextTaskName().trim() + " Task</b>\" is assigned to \"<b>"+ mailDto.getNextOwner() +" </b>\" for approval", "");
	        	}
	        	
	        	
	        	/*if(isNextTaskPresent==true)
	        		mailTemplateBody=mailTemplateBody.replaceAll("(?i)\\{\\{pendingapproval\\}\\}", " And is Pending For Your Approval");
	        	else
	        		mailTemplateBody=mailTemplateBody.replaceAll("(?i)\\{\\{pendingapproval\\}\\}", "");
	        	*/		        	
	        	message=mailTemplateBody;
	        	mailTemplateSubject=mailTemplateSubject.replaceAll("(?i)\\{\\{orderno\\}\\}", mailDto.getOrderNo());
	        	subject=mailTemplateSubject;
	        }
	        else
		        if(IB2BMailDto.SAVEACTIONMAILONREJECTION.equals(mailTemplateType) || IB2BMailDto.SAVEACTIONMAILONREJECTION_CHANGE.equals(mailTemplateType))
		        {
		        	
		    		System.err.println("Fetching Email Template for Rejection Action");
		    		mailTemplateBody=mailTemplateBody.replaceAll("(?i)\\{\\{taskName\\}\\}", mailDto.getTaskName());
		    		mailTemplateBody=mailTemplateBody.replaceAll("(?i)\\{\\{orderno\\}\\}", mailDto.getOrderNo());
		    		mailTemplateBody=mailTemplateBody.replaceAll("(?i)\\{\\{orderType\\}\\}", mailDto.getOrderType());
		    		mailTemplateBody=mailTemplateBody.replaceAll("(?i)\\{\\{productName\\}\\}", mailDto.getProductName());
		    		mailTemplateBody=mailTemplateBody.replaceAll("(?i)\\{\\{custName\\}\\}", mailDto.getAccountName());
		    		mailTemplateBody=mailTemplateBody.replaceAll("(?i)\\{\\{isApproved\\}\\}", mailDto.getIsApproved());
		    		mailTemplateBody=mailTemplateBody.replaceAll("(?i)\\{\\{remarks\\}\\}", mailDto.getRemarks());
		    		mailTemplateBody=mailTemplateBody.replaceAll("(?i)\\{\\{taskOwner\\}\\}", mailDto.getTaskOwner());
		    		mailTemplateBody=mailTemplateBody.replaceAll("(?i)\\{\\{startDate\\}\\}", mailDto.getTaskStartDate());
		    		mailTemplateBody=mailTemplateBody.replaceAll("(?i)\\{\\{endDate\\}\\}", mailDto.getTaskEndDate());
		    		mailTemplateBody=mailTemplateBody.replaceAll("(?i)\\{\\{nextTaskName\\}\\}", mailDto.getNextTaskName());
		    		mailTemplateBody=mailTemplateBody.replaceAll("(?i)\\{\\{nextOwner\\}\\}", mailDto.getNextOwner());
	        	    message=mailTemplateBody;
		        	mailTemplateSubject=mailTemplateSubject.replaceAll("(?i)\\{\\{orderno\\}\\}", mailDto.getOrderNo());
		        	subject=mailTemplateSubject;
		        			        	
//End [001]
		        }
	        
	            // 002 START
		        else
		        	if(IB2BMailDto.PENDINGORDERTEMPLATE.equals(mailTemplateType))
			        {
			        	
			    		System.err.println("Fetching Email Template for Pending Orders");
			        	mailTemplateBody=mailTemplateBody.replaceAll("(?i)\\{\\{taskName\\}\\}", mailDto.getTaskName());
			        	mailTemplateBody=mailTemplateBody.replaceAll("(?i)\\{\\{orderno\\}\\}", mailDto.getOrderNo());
			        	mailTemplateBody=mailTemplateBody.replaceAll("(?i)\\{\\{orderType\\}\\}", mailDto.getOrderType());
			        	mailTemplateBody=mailTemplateBody.replaceAll("(?i)\\{\\{custName\\}\\}", mailDto.getAccountName());
			        	mailTemplateBody=mailTemplateBody.replaceAll("(?i)\\{\\{startDate\\}\\}", mailDto.getTaskStartDate());
			        	mailTemplateBody=mailTemplateBody.replaceAll("(?i)\\{\\{endDate\\}\\}", mailDto.getTaskEndDate());
			        	message=mailTemplateBody;
			        	mailTemplateSubject=mailTemplateSubject.replaceAll("(?i)\\{\\{orderno\\}\\}", mailDto.getOrderNo());
			        	subject=mailTemplateSubject;
			        }
	       // 002 END 
	        //003 START
		        	else
			        	if(IB2BMailDto.REASSIGNPMTEMPLATE.equals(mailTemplateType))
				        {
				        	
				    		System.err.println("Fetching Email Template for Reassigned Project Manager");
				        	mailTemplateBody=mailTemplateBody.replaceAll("(?i)\\{\\{orderno\\}\\}", mailDto.getOrderNo());
				        	mailTemplateBody=mailTemplateBody.replaceAll("(?i)\\{\\{Reassigned_PM\\}\\}", mailDto.getReassigned_pm());
				        	message=mailTemplateBody;
				        	mailTemplateSubject=mailTemplateSubject.replaceAll("(?i)\\{\\{orderno\\}\\}", mailDto.getOrderNo());
				        	subject=mailTemplateSubject;
				        }
	        
	        //003 END
	        //004 START
			        	else
				        	if(IB2BMailDto.THIRD_PARTY_CHANGE_ORDER_MAIL.equals(mailTemplateType))
					        {
					        	
					    		System.err.println("Fetching Email Template for THIRD_PARTY_CHANGE_ORDER_MAIL");
					        	mailTemplateBody=mailTemplateBody.replaceAll("(?i)\\{\\{orderType\\}\\}", mailDto.getOrderType());
					        	mailTemplateBody=mailTemplateBody.replaceAll("(?i)\\{\\{nfaNumber\\}\\}", mailDto.getNfaNumber());
					        	mailTemplateBody=mailTemplateBody.replaceAll("(?i)\\{\\{prNumber\\}\\}", mailDto.getPrNumber());
					        	message=mailTemplateBody;
					        	mailTemplateSubject=mailTemplateSubject.replaceAll("(?i)\\{\\{orderType\\}\\}", mailDto.getOrderType());
					        	subject=mailTemplateSubject;
					        }
		        
		        //004 END

	        else
	        {
	        	return -2;
	        }
		
	        
	        if(!smsText.equalsIgnoreCase("-"))
        	{
	        	smsText=smsText.replaceAll("(?i)\\{\\{taskName\\}\\}", mailDto.getTaskName());
	        	smsText=smsText.replaceAll("(?i)\\{\\{orderno\\}\\}", mailDto.getOrderNo());
	        	smsText=smsText.replaceAll("(?i)\\{\\{orderType\\}\\}", mailDto.getOrderType());
	        	smsText=smsText.replaceAll("(?i)\\{\\{productName\\}\\}", mailDto.getProductName());
	        	smsText=smsText.replaceAll("(?i)\\{\\{custName\\}\\}", mailDto.getAccountName());
	        	smsText=smsText.replaceAll("(?i)\\{\\{isApproved\\}\\}", mailDto.getIsApproved());
	        	smsText=smsText.replaceAll("(?i)\\{\\{taskOwner\\}\\}", mailDto.getTaskOwner());
	        	smsText=smsText.replaceAll("(?i)\\{\\{nextTaskName\\}\\}", mailDto.getNextTaskName());
	        	smsText=smsText.replaceAll("(?i)\\{\\{nextOwner\\}\\}", mailDto.getNextOwner());
	        	//smsText=smsText.replaceAll("(?i)\\{\\{SERVICEID\\}\\}", mailDto.getServiceID());//Added by Deepak
	        	if(IB2BMailDto.PIEMAILTEMPLATE.equals(mailTemplateType)){
	        		smsText=smsText.replaceAll("<b>.*</b>", mailDto.getServiceID());
	        	}
	        	if("1".equalsIgnoreCase(mailDto.getIslasttask())){
	        		smsText=smsText.replaceAll("New Task \"" + mailDto.getNextTaskName().trim() + " Task\" is assigned to \""+ mailDto.getNextOwner() +" \" for approval", "");
	        	}
        		
        	}
	        
	        message="<HTML><BODY>"+message+"</BODY></HTML>";
	        
	        System.err.println("mailTemplateType:"+mailTemplateType);
	        System.err.println("subject:"+subject);
	        System.err.println("message:"+message);
			int retStatus = 0;
	        logger.info("========================== [Template body Start] ====================");
	        logger.info("mailTemplateType:"+mailTemplateType);
	        logger.info("message:"+message);
	        logger.info("========================== [Template body End] ====================");
	        logger.info("Current Task Action by:"+ mailDto.getTaskName());
	        
	        if(isMailAvailable==true)
	        {
	        	retStatus =sendMail.postMailWithAttachment(to, cc, bcc, subject, message, from,null);
	        }
			if(retStatus==1)
			{
				status=1;
				System.err.println("Mail Sent Successfully.....");
			}
			if(isSMSAvailable==true)
			{
				
				sendIB2BSMS(smsText, 0, mailDto);
			}
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println("Error Sending Mail.....");
			status=-1;
		}
		finally{
			try {
				if(optionalConnection==null)
	        	{
					DbConnection.freeConnection(conn);
	        	}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return status;
	}
	
	
	
	
	public static int sendIB2BSMS(String smsMsg , int flagSwtich,IB2BMailDto smsDto)
	{
		int retSMSStatus = 0;
		ArrayList phoneNos=smsDto.getSms_mobileNo();
		try {
			SMSUtil sms=new SMSUtil();
			if(phoneNos!=null && phoneNos.size()==1)
			{
				for (int i = 0; i < phoneNos.size(); i++) 
				{
					retSMSStatus=sms.sendingSMS((String)phoneNos.get(i), smsMsg);//TODO 
				}
			}
			else if(phoneNos.size()>1)
			{
				sms.sendSMS(phoneNos, smsMsg);//TODO 
				retSMSStatus=-10;//indeterminate
			}
			
		
			
		} catch (Exception ex) {
			ex.printStackTrace();
			retSMSStatus=-1;
		}
		
		return retSMSStatus;
		
	}
	
	/*
	 * optionalConnection: connection is optional ,if it is passed that one is used,
	 * if not passed a new is created and finally freed.
	 * 
	 * 
	 */
	public int sendSMS_for_Template(IB2BMailDto smsDto,Connection optionalConnection) throws IOESException
	{
		int status=0;
		//fetch mail type from mailTo and retrieve mailTemplate
		String SMSTemplateType=smsDto.getMailTemplateType();
		
		SendMail sendMail=new SendMail();
		
		ArrayList phoneNos=smsDto.getSms_mobileNo();
		String message=null;
        
        if(phoneNos==null || phoneNos.size()==0)
        {
        	return -1;
        }
        
        Connection conn=null;
        
        String smsTemplateBody=null;
        
        
        try {
        
        	if(optionalConnection==null)
        	{
        		conn=DbConnection.getConnectionObject();
        	}
        	else
        	{
        		conn=optionalConnection;
        	}
        	
        	IB2BMailDaoImpl conferenceMailDaoImpl=new IB2BMailDaoImpl();
	        IB2BMailDto values=conferenceMailDaoImpl.fetchEmailTemplate(conn,smsDto);
	        
	        smsTemplateBody=(values.getMailTemplateBody());
	        	        
	        if(IB2BMailDto.NEWORDER_SMS.equals(SMSTemplateType))
	        {
	        	smsTemplateBody=smsTemplateBody.replaceAll("(?i)\\{\\{orderno\\}\\}", "101");
	        	message=smsTemplateBody;
	        }
	        else
	        if(IB2BMailDto.SAVEACTION_SMS.equals(SMSTemplateType))
	        {
	        	smsTemplateBody=smsTemplateBody.replaceAll("(?i)\\{\\{orderno\\}\\}", "101");
	        	message=smsTemplateBody;
	        }
	        else
	        {
	        	return -2;
	        }
		
	        
	        
	        System.err.println("SMSTemplateType:"+SMSTemplateType);
	        System.err.println("message:"+message);
	        
			int retStatus=0;
			SMSUtil sms=new SMSUtil();
			if(phoneNos!=null && phoneNos.size()==1)
			{
				retStatus=sms.sendingSMS((String)phoneNos.get(0), message);//TODO 
			}
			else if(phoneNos.size()>1)
			{
				sms.sendSMS(phoneNos, message);//TODO 
				status=-10;//indeterminate
			}
			
			if(retStatus==1)
			{
				
				status=1;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			status=-1;
		}
		finally{
			try {
				if(optionalConnection==null)
	        	{
					DbConnection.freeConnection(conn);
	        	}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return status;
	}
	
	public static void main(String[] args) 
	{
		try 
		{
			IB2BMailDto mailDto =new IB2BMailDto();
			mailDto.setMailTemplateType(com.ibm.ioes.utilities.AppConstants.NEWORDERTEMPLATE);
			mailDto.setTo(new String[] { "arorasumit@in.ibm.com" });
			Connection optionalConnection = getConnectionObject();
			//sendiB2BMail(mailDto, optionalConnection);
		} catch (Exception e) {
			System.out.println("couldnt fetch" + e.getMessage());
			System.out.println("Error fetch" + e.getStackTrace());
			e.printStackTrace();
		}
	}

}
