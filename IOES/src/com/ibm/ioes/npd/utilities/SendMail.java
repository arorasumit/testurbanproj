package com.ibm.ioes.npd.utilities;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;

import com.ibm.ioes.npd.exception.NpdException;
import com.ibm.ioes.npd.hibernate.dao.MyToDOListDaoImpl;
import com.ibm.ioes.utilities.Utility;

import java.util.*;
import java.io.*;
public class SendMail {
	private static final Logger logger;
	static 
	{
		logger = Logger.getLogger(MyToDOListDaoImpl.class);
	}

	  static private   String SMTP_HOST_NAME = null;//"10.5.128.146";//" 10.14.97.36";
	 // final  private static  String SMTP_AUTH_USER = "appl@bharti.com";
	    private static  String SMTP_AUTH_USER = null;//"";
	  private static  String SMTP_AUTH_PWD  = null;//"";
	  private static final String emailFromAddress = "IB2B";
	  
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
	                            String message,String from,String attachmentPath )throws NpdException
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
			    Transport.send(msg);
			    retutnMsg=1;
			   
			    
		  }
		  catch(Exception ex)
		  {
				logger.error(ex.getMessage()
						+ " Exception occured in outStandingSummaryList method of "
						+ this.getClass().getSimpleName());
						throw new NpdException();
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
				if(to[i]!=null && !"".equals(to[i].trim()))
				{
					list.add(to[i]);	
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
              String message,String from,FormFile attachmentPath )throws NpdException
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
		bodyPart = new MimeBodyPart();
		bodyPart.setDataHandler(new DataHandler(fds));

		bodyPart.setFileName(attachmentPath.getFileName());

		multiPart.addBodyPart(bodyPart);
		// Add the Multipart to the message
		msg.setContent(multiPart);
		// Send the message;
		Transport.send(msg);
		retutnMsg=1;
		/*if(fds.getFile().exists())
		{
			file.delete();
		}*/
		
		}
		catch(Exception ex)
		{
		logger.error(ex.getMessage()
				+ " Exception occured in outStandingSummaryList method of "
				+ this.getClass().getSimpleName());
				throw new NpdException();
			}
		
		return retutnMsg;
	}
	 
	  public int  postMailWithAttachmentPath( String to[ ],String cc[ ],String bcc[ ],String subject,
	             String message,String from,String[] attachment,String[] fileNames )throws NpdException
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
							throw new NpdException("Could not find file to temporary location:"+file.getAbsolutePath());
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
					throw new NpdException();
				}
			
			return retutnMsg;
		}
	  public int  postMailWithAttachment( String to[ ],String cc[ ],String bcc[ ],String subject,
             String message,String from,FormFile []attachment,File files[] )throws NpdException
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
						throw new NpdException("Could not save file to temporary location:"+file.getAbsolutePath());
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
						throw new NpdException("Could not save file to temporary location:"+file.getAbsolutePath());
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
				throw new NpdException();
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
	        System.out.println(">>username=" + username + " password=" + password);
	        
	        return new PasswordAuthentication(username, password);
	        
	    }
	}


}
