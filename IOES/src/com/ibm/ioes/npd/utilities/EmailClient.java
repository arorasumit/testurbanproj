package com.ibm.ioes.npd.utilities;

import java.io.File;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

import com.ibm.appsecure.util.Encryption;

public class EmailClient {
	private static final Logger LogmonLOGGER_EMAIL;

	static {
		LogmonLOGGER_EMAIL = Logger.getLogger(EmailClient.class);
	}
	
	private Session session = null;
	private InternetAddress from = null;
	private List ccList = null;
	
	public EmailClient() throws Exception {
		
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
	public void sendEmail1(String sendTo, String subject, String textBody, File csvAttachment) {
		LogmonLOGGER_EMAIL.info("Enter EmailUtility.sendEmail() ");
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
	
			// Set Subject and Body of Email message.
			message.setSubject(subject);
			message.setText(textBody);
			message.setDataHandler(new DataHandler(textBody, "text/html"));
			
			//Add attchment.
			if(csvAttachment != null) {
				
				Multipart mixedMultiPart = new MimeMultipart("mixed");
			    BodyPart csvBodyPart = new MimeBodyPart();
			    csvBodyPart.setDisposition(javax.mail.Part.ATTACHMENT);
			    DataSource fileDataSource = new FileDataSource(csvAttachment);
			    csvBodyPart.setDataHandler(new DataHandler(fileDataSource));
			    csvBodyPart.setHeader("Content-Type", "text/csv");
			    csvBodyPart.setFileName(fileDataSource.getName());
			    mixedMultiPart.addBodyPart(csvBodyPart);
			    message.setContent(mixedMultiPart);
			}

			// Send message
			Transport.send(message);

			LogmonLOGGER_EMAIL.info("Email sent successfully.");
		
		}catch (Exception e) {
			LogmonLOGGER_EMAIL.info("Send Email Fails : " + e.getStackTrace());
			LogmonLOGGER_EMAIL.error("Send Email Fails : " + AppUtility.getStackTrace(e));
			e.printStackTrace();
			System.out.println("SOP - Could Not send Email : " + e.getStackTrace());
		}
		LogmonLOGGER_EMAIL.info("Exit EmailUtility.sendEmail() ");
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
/*			
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(textBody);
			
			
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			
			if(csvAttachment != null) {
				messageBodyPart = new MimeBodyPart();
				DataSource source = new FileDataSource(csvAttachment);
				messageBodyPart.setDataHandler(new DataHandler(source));
				messageBodyPart.setFileName(csvAttachment.getName());
				multipart.addBodyPart(messageBodyPart);
			}
			message.setContent(multipart);
*/
		
	
			// Set Subject and Body of Email message.
			message.setSubject(subject);
			message.setText(textBody);
			message.setDataHandler(new DataHandler(textBody, "text/html"));
			
			Multipart mixedMultiPart = new MimeMultipart("mixed");
			//Add attchment.
			if(csvAttachment != null) {
			    BodyPart csvBodyPart = new MimeBodyPart();
			    csvBodyPart.setDisposition(javax.mail.Part.ATTACHMENT);
			    DataSource fileDataSource = new FileDataSource(csvAttachment);
			    csvBodyPart.setDataHandler(new DataHandler(fileDataSource));
			    csvBodyPart.setHeader("Content-Type", "text/csv");
			    csvBodyPart.setFileName(fileDataSource.getName());
			    mixedMultiPart.addBodyPart(csvBodyPart);
			    message.setContent(mixedMultiPart);
			}
			// Send message
			Transport.send(message);

			System.out.println("Mail was sent successfully.");
		
		}catch (Exception e) {
			LogmonLOGGER_EMAIL.error("Send Email Fails : " + AppUtility.getStackTrace(e));
			e.printStackTrace();
			System.out.println("SOP - Could Not send Email : " + e.getStackTrace());
		}
	}
	
	public void sendEmail2(String sendTo, String subject, String textBody, File csvAttachment) {
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
			
			BodyPart messageBodyPart1 = null;
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(textBody);
			
			
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			
			if(csvAttachment != null) {
				messageBodyPart1 = new MimeBodyPart();
				DataSource source = new FileDataSource(csvAttachment);
				messageBodyPart1.setDataHandler(new DataHandler(source));
				messageBodyPart1.setFileName(csvAttachment.getName());
				multipart.addBodyPart(messageBodyPart1);
			}
			message.setContent(multipart);

			Transport.send(message);
			
			LogmonLOGGER_EMAIL.info("Email Sent Successfully to Email :" + sendTo);

		}catch (Exception e) {
			LogmonLOGGER_EMAIL.error("Send Email Fails : " + AppUtility.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	public void sendEmail() { 
		String sendTo = "yogeshkhatkar@gmail.com";
		String subject = "Email test";
		String textBody = "Dis is a test Email";
		File csvAttachment = new File("C:/LOGMON/csv/sent/Administration Activity.csv");
		
		LogmonLOGGER_EMAIL.info("Sending First..... ");
		sendEmail(sendTo, subject+"NO", textBody, csvAttachment);
		
		LogmonLOGGER_EMAIL.info("Sending 2nd..... ");
		sendEmail1(sendTo, subject+"1", textBody, csvAttachment);
		
		LogmonLOGGER_EMAIL.info("Sending 3rd..... ");
		sendEmail2(sendTo, subject+"2", textBody, csvAttachment);
		
	}


}
