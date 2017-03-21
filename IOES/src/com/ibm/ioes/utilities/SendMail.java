package com.ibm.ioes.utilities;

import javax.activation.DataHandler;
//import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
//import javax.mail.util.ByteArrayDataSource;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;


import com.ibm.ioes.exception.IOESException;

import java.util.*;
import java.io.*;
public class SendMail {
	private static final Logger logger;
	static 
	{
		logger = Logger.getLogger(SendMail.class);
	}

	  static private  String SMTP_HOST_NAME =null;//"10.96.9.132";//9.182.227.145";//10.96.9.132//"10.5.128.146";//"10.39.6.42";//" 10.14.97.36"//9.182.227.161
	 // final  private static  String SMTP_AUTH_USER = "appl@bharti.com";
	    private static  String SMTP_AUTH_USER = null;//"";
	  private static  String SMTP_AUTH_PWD  = null;//"";
	  private static final String emailFromAddress = "IB2B@in.airtel.com";
	  
	  private static final String baseDirPath = Messages.getMessageValue("TEMPORARY_DIR_PATH");
	  
	  static{
		  try {
			SMTP_HOST_NAME=Utility.getAppConfigValue("IB2B_SMTP_HOST_NAME");
		  } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }
		  try {
			  SMTP_AUTH_USER=Utility.getAppConfigValue("IB2B_SMTP_AUTH_USER");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  try {
			  SMTP_AUTH_PWD=Utility.getAppConfigValue("IB2B_SMTP_AUTH_PWD");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	  }

	  // Add List of Email address to who email needs to be sent to
	  /* Method to send mail
	   * return 0 for fail
	   * return 1 for success
	   */
	  public int postMail( String to[ ],String cc[ ],String bcc[ ],String subject,
	                            String message,String from,String attachmentPath )throws IOESException
	  {
	      
		  int retutnMsg=0;//Failed
		  to=newList(to);
		  cc=newList(cc);
		  bcc=newList(bcc);
		  
		  //bcc=addToBcc(bcc);
		  
		  
		  try{
			
			  boolean debug = false;
			  if(from==null || from.equalsIgnoreCase("")){
			    	from=emailFromAddress;
			    	
			    }
			   //Set the host smtp address
			     Properties props = new Properties();
			     props.put("mail.smtp.host", SMTP_HOST_NAME);
			     props.put("mail.transport.protocol", "smtp");
			     props.put("mail.smtp.port", 25);
			     
			    Authenticator auth = new SMTPAuthenticator();
			    
			    Session session = Session.getInstance(props);
			    session.setDebug(debug);
			    Message msg = new MimeMessage(session);
			    InternetAddress addressFrom = new InternetAddress(from);
			    msg.setFrom(addressFrom);
			    //Attach the files to the message
			    if(attachmentPath!=null)
			    {
			    FileDataSource fds = new FileDataSource(attachmentPath);
			    /*Checking attachment existence
			     * If file don't exist then don't send the mail;
			     */ 
			    if(fds.getFile().exists()){
			    //TO
			    }
			    }
			    
			    if(to!=null){
			    	InternetAddress[] addressTo = new InternetAddress[to.length];
			    	for (int i = 0; i < to.length; i++)
				    {
				        addressTo[i] = new InternetAddress(to[i]);
				    }
			    	if(addressTo.length>0){
				    	 msg.setRecipients(Message.RecipientType.TO, addressTo);
				    }
			    }

			    //CC
			    if(cc!=null){
			    	 InternetAddress[] addressCc = new InternetAddress[cc.length];
			    	for (int i = 0; i < cc.length; i++)
				    {
				        addressCc[i] = new InternetAddress(cc[i]);
				    }
			    	 if(addressCc.length>0){
						   msg.setRecipients(Message.RecipientType.CC, addressCc); 	
					    }
			    }
			   //BCC
			    if(bcc!=null){
			    	InternetAddress[] addressBcc = new InternetAddress[bcc.length];
			    for (int i = 0; i < bcc.length; i++)
			     {
			      addressBcc[i] = new InternetAddress(bcc[i]);
		          }
				    if(addressBcc.length>0){
				     msg.setRecipients(Message.RecipientType.BCC, addressBcc); 	
				    }	
			    }
			    msg.setSubject("Auto Mail: "+subject);
			    Multipart mp = new MimeMultipart();
			    BodyPart mbp = new MimeBodyPart();
			    mbp.setContent(message, "text/html");
			    mp.addBodyPart(mbp);
			    //mbp = new MimeBodyPart();
			    //mbp.setDataHandler(new DataHandler(fds));
			    //mbp.setFileName(fds.getName());
			    //mbp.setText(message);
			    //mp.addBodyPart(mbp);
			    // Add the Multipart to the message
			    msg.setContent(mp);
			    // Send the message;
			    System.err.println("Sending Mail For IB2B...........");
			    Transport.send(msg);
			    retutnMsg=1;
			    System.err.println("Mail Sent IB2B...........");
			   
			    
		  }
		  catch(Exception ex)
		  {
				logger.error(ex.getMessage()
						+ " Exception occured in outStandingSummaryList method of "
						+ this.getClass().getSimpleName());
						throw new IOESException();
					}
		  
   return retutnMsg;
	 }
private String[] newList(String[] to) {
		String[] newList=null;
		ArrayList list=new ArrayList();
		if(to!=null)
		{
			for(int i=0;i<to.length;i++)
			{
				//ADDED BY KALPANA TO SEPARATE LIST OF copc region wise
				if(to[i]!=null && !"".equals(to[i].trim()))
				{
					if(to[i].contains(",")){
						String[] temp;
						temp=to[i].split(",");
						for(int j=0;j<temp.length;j++)
						{
							list.add(temp[j]);
						}
					}else{
						list.add(to[i]);
					}
						
				}
			}
			newList=new String[list.size()];
			int index=0;
			for (Object object : list) {
				newList[index]=(String)object;
				index++;
			}
		}
		return newList;
	}
	/* private String[] addToBcc(String[] bcc) {
		 String newbcc[]=null;
		 String emailId="testmail1";
		 String email2Id="testmail2";
		 if(bcc==null)
		  {
			 newbcc=new String[2];
			  newbcc[0]=emailId;
			  newbcc[1]=email2Id;
		  }
		  else if(bcc.length==0)
		  {
			  newbcc=new String[2];
			  newbcc[0]=emailId;
			  newbcc[1]=email2Id;
		  }
		  else 
		  {
			  newbcc=new String[bcc.length+2];
			  int l=0;
			  for(;l<bcc.length;l++)
			  {
				  newbcc[l]=bcc[l];
			  }
			  newbcc[l]=emailId;
			  newbcc[l+1]=email2Id;
		  }
		 return newbcc;
	}*/

	 public int  postMailWithAttachment( String to[ ],String cc[ ],String bcc[ ],String subject,
              String message,String from,FormFile attachmentPath )throws IOESException
	{
	
		int retutnMsg=0;//Failed
		//bcc=addToBcc(bcc);
		to=newList(to);
		  cc=newList(cc);
		  bcc=newList(bcc);		  
		try{
		
		boolean debug = false;
		if(from==null || from.equalsIgnoreCase("")){
			from=emailFromAddress;
			
		}
		//Set the host smtp address
		Properties props = new Properties();
		props.put("mail.smtp.host", SMTP_HOST_NAME);
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.port", 25);
		
		Authenticator auth = new SMTPAuthenticator();
		
		Session session = Session.getInstance(props);
		session.setDebug(debug);
		Message msg = new MimeMessage(session);
		InternetAddress addressFrom = new InternetAddress(from);
		msg.setFrom(addressFrom);
		//Attach the files to the message
		
		FileDataSource fds =null;
		File file =null;
		if(attachmentPath!=null){
		if(attachmentPath.getFileName()!=null && !"".equals(attachmentPath.getFileName()))
		{
			 file = new File(baseDirPath+attachmentPath.getFileName()+System.currentTimeMillis());
			 OutputStream os = new FileOutputStream(file);
			 InputStream is = new BufferedInputStream(attachmentPath.getInputStream());
			 int count;
			 byte buf[] = new byte[4096];

			 while ((count = is.read(buf)) > -1) {
			 os.write(buf, 0, count);
			 }

			 is.close();
			 os.close();
			 
			 String str=file.getAbsolutePath();
			fds = new FileDataSource(file);
		
		
		
		/*Checking attachment existence
		* If file don't exist then don't send the mail;
		*/ 
		if(!fds.getFile().exists()){
			return -1;
		}
		}
		}
		
				//------------========================================================================================================================================
		//------------Changes Made By Sumit on 20-Mar-2013 for Sending Mail to Comma Seperated Mail id's For COPC and SED Group
		//------------Previously Code was Sending Mail to Single Email id and if a Comma Seperated Email id is Given it was failing
		//------------========================================================================================================================================
		//TO
		String recipientsEmail_TO="";
		String recipientsEmail_CC="";
		String recipientsEmail_BCC="";
		if(to!=null)
		{
			  String[] groupTo = null;
			  int groupToLength=0;
			  if(to.length>2)
			  {
				  groupTo = to[2].split(",");
				  groupToLength=(groupTo.length-1);
			  }
			  			  
			  InternetAddress[] addressTo = new InternetAddress[to.length + (groupToLength)];
			  for (int i = 0; i < to.length; i++)
			  {
				  if(to[i].indexOf(",", 0)==-1)	
					  addressTo[i] = new InternetAddress(to[i]);
				  else
				  {
					  groupTo = to[i].split(",");
					  for(int toCount=0;toCount<groupTo.length;toCount++)
					  {
						  addressTo[i+toCount] = new InternetAddress(groupTo[toCount]);
					  }				  
				  }
				  recipientsEmail_TO=recipientsEmail_TO+","+addressTo[i];
			  }
			//------------========================================================================================================================================
			//------------Changes Made By Sumit on 20-Mar-2013 for Sending Mail to Comma Seperated Mail id's For COPC and SED Group
			//------------========================================================================================================================================


			if(addressTo.length>0){
				msg.setRecipients(Message.RecipientType.TO, addressTo);
			}
		}
		
		//CC
		if(cc!=null){
			 InternetAddress[] addressCc = new InternetAddress[cc.length];
			 for (int i = 0; i < cc.length; i++)
			 {
				 addressCc[i] = new InternetAddress(cc[i]);
				 recipientsEmail_CC=recipientsEmail_CC+","+addressCc[i];
			 }
			 if(addressCc.length>0){
				   msg.setRecipients(Message.RecipientType.CC, addressCc); 	
			 }
		}
		//BCC
		if(bcc!=null){
			InternetAddress[] addressBcc = new InternetAddress[bcc.length];
			for (int i = 0; i < bcc.length; i++)
			{
				addressBcc[i] = new InternetAddress(bcc[i]);
				recipientsEmail_BCC=recipientsEmail_BCC+""+addressBcc[i];
			}
			if(addressBcc.length>0){
				msg.setRecipients(Message.RecipientType.BCC, addressBcc); 	
			}		
		}
		msg.setSubject("Auto Mail: "+subject);
		Multipart multiPart = new MimeMultipart();
		BodyPart bodyPart = new MimeBodyPart();
		bodyPart.setContent(message, "text/html");
		multiPart.addBodyPart(bodyPart);
		bodyPart = new MimeBodyPart();
		bodyPart.setDataHandler(new DataHandler(fds));

		//bodyPart.setFileName(attachmentPath.getFileName());

		//multiPart.addBodyPart(bodyPart);
		// Add the Multipart to the message
		msg.setContent(multiPart);
		// Send the message;
		
		//Anil Kumar:Changes write in ib2b logger for mail recipents deatails
		logger.info("========================== [Sending mail to following recipients start:] ====================");
		System.err.println("Sending mail to following recipients start:");
		System.err.println("to:"+recipientsEmail_TO);
		logger.info("   [to: "+recipientsEmail_TO+" ]");
		System.err.println("cc:"+recipientsEmail_CC); 
		logger.info("   [cc: "+recipientsEmail_CC+" ]");
		System.err.println("bcc:"+recipientsEmail_BCC);
		logger.info("   [bcc:"+recipientsEmail_BCC+" ]");
		
		Transport.send(msg);
		logger.info("================================= [Sending mail end:] ==========================================");
		retutnMsg=1;
				
		/*if(fds.getFile().exists())
		{
			file.delete();
		}*/
		
		}
		catch(Exception ex)
		{
		logger.error(ex.getMessage()
				+ " Exception occured in postMailWithAttachment(String to[ ],String cc[ ],String bcc[ ],String subject,"
              +"String message,String from,FormFile attachmentPath) method of "
				+ this.getClass().getSimpleName());
				throw new IOESException();
			}
		
		return retutnMsg;
	}
	 
	  public int  postMailWithAttachmentPath( String to[ ],String cc[ ],String bcc[ ],String subject,
	             String message,String from,String[] attachment,String[] fileNames )throws IOESException
		{
		
			int retutnMsg=0;//Failed
			//bcc=addToBcc(bcc);
			to=newList(to);
			  cc=newList(cc);
			  bcc=newList(bcc);
			try{
			
			boolean debug = false;
			if(from==null || from.equalsIgnoreCase("")){
				from=emailFromAddress;
				
			}
			//Set the host smtp address
			Properties props = new Properties();
			props.put("mail.smtp.host", SMTP_HOST_NAME);
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.port", 25);
			
			Authenticator auth = new SMTPAuthenticator();
			
			Session session = Session.getInstance(props);
			session.setDebug(debug);
			Message msg = new MimeMessage(session);
			InternetAddress addressFrom = new InternetAddress(from);
			msg.setFrom(addressFrom);
			
			
			if(to!=null){
				InternetAddress[] addressTo = new InternetAddress[to.length];
				for (int i = 0; i < to.length; i++)
			  {
			      addressTo[i] = new InternetAddress(to[i]);
			  }
				if(addressTo.length>0){
			  	 msg.setRecipients(Message.RecipientType.TO, addressTo);
			  }
			}
			
			//CC
			if(cc!=null){
				 InternetAddress[] addressCc = new InternetAddress[cc.length];
				for (int i = 0; i < cc.length; i++)
			  {
			      addressCc[i] = new InternetAddress(cc[i]);
			  }
				 if(addressCc.length>0){
					   msg.setRecipients(Message.RecipientType.CC, addressCc); 	
				    }
			}
			//BCC
			if(bcc!=null){
				InternetAddress[] addressBcc = new InternetAddress[bcc.length];
			for (int i = 0; i < bcc.length; i++)
			{
			addressBcc[i] = new InternetAddress(bcc[i]);
			}
			  if(addressBcc.length>0){
			   msg.setRecipients(Message.RecipientType.BCC, addressBcc); 	
			  }	
			}
			msg.setSubject("Auto Mail: "+subject);
			Multipart multiPart = new MimeMultipart();
			BodyPart bodyPart = new MimeBodyPart();
			bodyPart.setContent(message, "text/html");
			multiPart.addBodyPart(bodyPart);
			
//	Attach the files to the message
			
			FileDataSource fds =null;
			File file =null;
			if(attachment!=null && attachment.length!=0)
			{
				for(int i=0;i<attachment.length;i++)
				{
					if(attachment[i]!=null && !"".equals(attachment[i]))
					{
						file = new File(attachment[i]);
						 
						String str=file.getAbsolutePath();
						fds = new FileDataSource(file);
						

						/*Checking attachment existence
						* If file don't exist then don't send the mail;
						*/ 
						if(!fds.getFile().exists()){
							throw new IOESException("Could not find file to temporary location:"+file.getAbsolutePath());
						}
					
						bodyPart = new MimeBodyPart();
						bodyPart.setDataHandler(new DataHandler(fds));
						if(fileNames!=null && fileNames.length>i && fileNames[i]!=null && !"".equals(fileNames[i]))
						{
							bodyPart.setFileName(fileNames[i]);
						}
						else
						{
							bodyPart.setFileName(attachment[i]);
						}
						multiPart.addBodyPart(bodyPart);
					}
				}
			}
			
			
			
			// Add the Multipart to the message
			msg.setContent(multiPart);
			// Send the message;
			Transport.send(msg);
			retutnMsg=1;
			
			
			}
			catch(Exception ex)
			{
			logger.error(ex.getMessage()
					+ " Exception occured in outStandingSummaryList method of "
					+ this.getClass().getSimpleName());
					throw new IOESException();
				}
			
			return retutnMsg;
		}
	  public int  postMailWithAttachment( String to[ ],String cc[ ],String bcc[ ],String subject,
             String message,String from,FormFile []attachment,File files[] )throws IOESException
	{
	
		int retutnMsg=0;//Failed
		//bcc=addToBcc(bcc);
		to=newList(to);
		  cc=newList(cc);
		  bcc=newList(bcc);
		try{
		
		boolean debug = false;
		if(from==null || from.equalsIgnoreCase("")){
			from=emailFromAddress;
			
		}
		//Set the host smtp address
		Properties props = new Properties();
		props.put("mail.smtp.host", SMTP_HOST_NAME);
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.port", 25);
		
		Authenticator auth = new SMTPAuthenticator();
		
		Session session = Session.getInstance(props);
		session.setDebug(debug);
		Message msg = new MimeMessage(session);
		InternetAddress addressFrom = new InternetAddress(from);
		msg.setFrom(addressFrom);
		
		
		if(to!=null){
			InternetAddress[] addressTo = new InternetAddress[to.length];
			for (int i = 0; i < to.length; i++)
		  {
		      addressTo[i] = new InternetAddress(to[i]);
		  }
			if(addressTo.length>0){
		  	 msg.setRecipients(Message.RecipientType.TO, addressTo);
		  }
		}
		
		//CC
		if(cc!=null){
			 InternetAddress[] addressCc = new InternetAddress[cc.length];
			for (int i = 0; i < cc.length; i++)
		  {
		      addressCc[i] = new InternetAddress(cc[i]);
		  }
			 if(addressCc.length>0){
				   msg.setRecipients(Message.RecipientType.CC, addressCc); 	
			    }
		}
		//BCC
		if(bcc!=null){
			InternetAddress[] addressBcc = new InternetAddress[bcc.length];
		for (int i = 0; i < bcc.length; i++)
		{
		addressBcc[i] = new InternetAddress(bcc[i]);
		}
		  if(addressBcc.length>0){
		   msg.setRecipients(Message.RecipientType.BCC, addressBcc); 	
		  }	
		}
		msg.setSubject("Auto Mail: "+subject);
		Multipart multiPart = new MimeMultipart();
		BodyPart bodyPart = new MimeBodyPart();
		bodyPart.setContent(message, "text/html");
		multiPart.addBodyPart(bodyPart);
		
//Attach the files to the message
		
		FileDataSource fds =null;
		File file =null;
		if(attachment!=null && attachment.length!=0)
		{
			for(int i=0;i<attachment.length;i++)
			{
				if(attachment[i]!=null && !"".equals(attachment[i].getFileName()))
				{
					file = new File(baseDirPath+attachment[i].getFileName()+System.currentTimeMillis());
					 OutputStream os = new FileOutputStream(file);
					 InputStream is = new BufferedInputStream(attachment[i].getInputStream());
					 int count;
					 byte buf[] = new byte[4096];

					 while ((count = is.read(buf)) > -1) {
					 os.write(buf, 0, count);
					 }

					 is.close();
					 os.close();
					 
					 String str=file.getAbsolutePath();
					fds = new FileDataSource(file);
					

					/*Checking attachment existence
					* If file don't exist then don't send the mail;
					*/ 
					if(!fds.getFile().exists()){
						throw new IOESException("Could not save file to temporary location:"+file.getAbsolutePath());
					}
				
					bodyPart = new MimeBodyPart();
					bodyPart.setDataHandler(new DataHandler(fds));
					bodyPart.setFileName(attachment[i].getFileName());
					multiPart.addBodyPart(bodyPart);
				}
			}
		}
		
		if(files!=null && files.length!=0)
		{
			for(int i=0;i<files.length;i++)
			{
				if(files[i]!=null && files[i].exists())
				{
					file = files[i];
					 
					 String str=file.getAbsolutePath();
					fds = new FileDataSource(file);
					

					/*Checking attachment existence
					* If file don't exist then don't send the mail;
					*/ 
					if(!fds.getFile().exists()){
						throw new IOESException("Could not save file to temporary location:"+file.getAbsolutePath());
					}
				
					bodyPart = new MimeBodyPart();
					bodyPart.setDataHandler(new DataHandler(fds));
					bodyPart.setFileName("Meeting.ics");
					multiPart.addBodyPart(bodyPart);
				}
			}
		}
		
		// Add the Multipart to the message
		msg.setContent(multiPart);
		// Send the message;
		Transport.send(msg);
		retutnMsg=1;
		
		
		}
		catch(Exception ex)
		{
		logger.error(ex.getMessage()
				+ " Exception occured in outStandingSummaryList method of "
				+ this.getClass().getSimpleName());
				throw new IOESException();
			}
		
		return retutnMsg;
	}
	/**
	* SimpleAuthenticator is used to do simple authentication
	* when the SMTP server requires it.
	*/
	private class SMTPAuthenticator extends javax.mail.Authenticator
	{

	    public PasswordAuthentication getPasswordAuthentication()
	    {
	        String username = SMTP_AUTH_USER;
	        String password = SMTP_AUTH_PWD;
	    Utility.SysOut(">>username=" + username + " password=" + password);
	        
	        return new PasswordAuthentication(username, password);
	        
	    }
	}


}
