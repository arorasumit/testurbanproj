package com.ibm.ioes.npd.utilities;

import java.io.File;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import com.ibm.appsecure.util.Encryption;

public class EmailUtility {
	
	
	private static final Logger LogmonLOGGER_EMAIL;

	static {
		LogmonLOGGER_EMAIL = Logger.getLogger(EmailUtility.class);
	}
	
	private Session session = null;
	private InternetAddress from = null;
	private List ccList = null;
	
	public EmailUtility() throws Exception {
		
		try {
			Properties props = System.getProperties();
			props.put("mail.smtp.host", GetPropertiesUtility
						.getProperty("SMTP_HOST_IP"));
			props.put("mail.smtp.auth", "true");
			props.put("mail.transport.protocol", "smtp");
			// Set password.
			Authenticator auth = new SMTPAuthenticatorUtility();
			this.session = Session.getInstance(props, auth);
			Encryption encryption = new Encryption();
			from = new InternetAddress(GetPropertiesUtility.getProperty("GENERIC_USERNAME"));
		}
		catch(Exception e) {
			//TODO  - error
			throw e;
		}
	}
	
	/**
	 * 
	 * @param sendTo
	 * @param subject
	 * @param textBody
	 * @param csvAttachment
	 */
	public void sendEmail(String sendTo, String subject, String textBody, File csvAttachment) {
		try {

			MimeMessage message = new MimeMessage(session);
			
			//Set From. It is common for all.
			message.setFrom(from);
			
			// Add recipient
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(sendTo));
			
			// Add CC List
			if (ccList != null && ccList.size() > 0) {
				for(int i=0; i<ccList.size(); i++) {
					String ccEmail = (String)ccList.get(i);
					if(ccEmail != null && ccEmail.length() > 0) {
						message.addRecipient(Message.RecipientType.CC,
								new InternetAddress(ccEmail));
					}
				}	
			}
			
			message.setSubject(subject);
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(textBody);			

			Transport.send(message);
			
			LogmonLOGGER_EMAIL.info("Email Sent Successfully to Email :" + sendTo);

		}catch (Exception e) {
			LogmonLOGGER_EMAIL.error("Send Email Fails : " + AppUtility.getStackTrace(e));
		}
	}
	
	/**
	 * Without attachment Email
	 * @param sendTo
	 * @param subject
	 * @param textBody
	 */
	public void sendEmail(String sendTo, String subject, String textBody) {
		sendEmail(sendTo, subject, textBody, null);
	}
	

	public List getCcList() {
		return ccList;
	}

	public void setCcList(List ccList) {
		this.ccList = ccList;
	}

}
