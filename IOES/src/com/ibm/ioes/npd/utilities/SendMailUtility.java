package com.ibm.ioes.npd.utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.struts.upload.FormFile;

import com.ibm.appsecure.exception.EncryptionException;
import com.ibm.appsecure.util.Encryption;
import com.ibm.ioes.npd.exception.NpdException;

/**
 * This class is used for Sending mails
 * 
 * @author Shantnu Jain
 */
public class SendMailUtility {

	/**
	 * Main method for Sending mail using command line.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SendMailUtility obj = new SendMailUtility();
		obj.sendMessage();
	}

	private String ccMail = "";

	private String messageType = "";

	// LIST OF THE TO WHO HAD TO BE SENT THE MAIL
	private ArrayList oToList = null;

	private ArrayList oCcList = null;

	// MESSAGE BODY OF THE MAIL
	private String strMessage = "";

	// SUBJECT OF THE MAIL
	private String strSubject = "";

	/**
	 * @param ccMail
	 *            The ccMail to set.
	 */
	public void setCcMail(String ccMail) {
		this.ccMail = ccMail;
	}

	/**
	 * @param messageType
	 */
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	/**
	 * @param toList
	 *            The oToList to set.
	 */
	public void setOToList(ArrayList toList) {
		oToList = toList;
	}

	public ArrayList getOCcList() {
		return oCcList;
	}

	public void setOCcList(ArrayList ccList) {
		oCcList = ccList;
	}

	/**
	 * @param strMessage
	 *            The strMessage to set.
	 */
	public void setStrMessage(String strMessage) {
		this.strMessage = strMessage;
	}

	/**
	 * @param strSubject
	 *            The strSubject to set.
	 */
	public void setStrSubject(String strSubject) {
		this.strSubject = strSubject;
	}

	/**
	 * @return Returns the ccMail.
	 */
	public String getCcMail() {
		return ccMail;
	}

	/**
	 * @return Returns the messageType.
	 */
	public String getMessageType() {
		return messageType;
	}

	/**
	 * @return Returns the oToList.
	 */
	public ArrayList getOToList() {
		return oToList;
	}

	/**
	 * @return Returns the strMessage.
	 */
	public String getStrMessage() {
		return strMessage;
	}

	/**
	 * @return Returns the strSubject.
	 */
	public String getStrSubject() {
		return strSubject;
	}

	/**
	 * Method to send email.
	 * 
	 * @param strSendTo
	 */
	public synchronized void sendMessage(String strSendTo) {

		try {
			AppConstants.NPDLOGGER
					.info("GetPropertiesUtility.getProper--"
							+ GetPropertiesUtility.getProperty("SMTP_HOST_IP"));
			Properties props = System.getProperties();
			props.put("mail.smtp.host", GetPropertiesUtility
					.getProperty("SMTP_HOST_IP"));
			props.put("mail.smtp.auth", "true");
			props.put("mail.transport.protocol", "smtp");
			Authenticator auth = new SMTPAuthenticatorUtility();

			// Get a Session object
			Session session = Session.getInstance(props, auth);

			// Define message
			MimeMessage message = new MimeMessage(session);
			Encryption encryption = new Encryption();
			message.setFrom(new InternetAddress(GetPropertiesUtility.getProperty("GENERIC_USERNAME")));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					strSendTo));
			if (!"".equals(ccMail)) {
				message.addRecipient(Message.RecipientType.CC,
						new InternetAddress(ccMail));
			}

			message.setSubject(strSubject);
			message.setText(strMessage);
			if (messageType != null) {
				message
						.setDataHandler(new DataHandler(strMessage,
								"text/plain"));
			}

			// Send message
			Transport.send(message);

			AppConstants.NPDLOGGER.info("Mail sent to - " + strSendTo);
			AppConstants.NPDLOGGER.info("Mail sent to in cc - " + ccMail);
			AppConstants.NPDLOGGER.info("Mail was sent successfully.");

		} catch (Exception oException) {
			AppConstants.NPDLOGGER.error(AppUtility
					.getStackTrace(oException));
		}

	}

	/**
	 * Method for sending mail to contacts in ArrayList
	 */
	public synchronized void sendMessage() {

		try {
			if ((oToList != null) && (oToList.size() > 0)) {

				AppConstants.NPDLOGGER
						.info("GetPropertiesUtility.getProper--"
								+ GetPropertiesUtility
										.getProperty("SMTP_HOST_IP"));
				Properties props = System.getProperties();
				props.put("mail.smtp.host", GetPropertiesUtility
						.getProperty("SMTP_HOST_IP"));
				props.put("mail.smtp.auth", "true");
				props.put("mail.transport.protocol", "smtp");
				Authenticator auth = new SMTPAuthenticatorUtility();

				// Get a Session object
				Session session = Session.getInstance(props, auth);

				// Define message
				MimeMessage message = new MimeMessage(session);
				Encryption encryption = new Encryption();
				message.setFrom(new InternetAddress(encryption.decrypt(
						GetPropertiesUtility.getProperty("GENERIC_USERNAME"),
						AppConstants.ENCRYPTION_KEY)));
				Address[] toAdd = new Address[oToList.size()];
				String sendTo = "";
				String ccTo = "";
				for (int i = 0; i < oToList.size(); i++) {
					toAdd[i] = new InternetAddress(oToList.get(i).toString());
					sendTo = sendTo + toAdd[i] + ",";
				}

				message.addRecipients(Message.RecipientType.TO, toAdd);
				Address[] ccAdd = null;
				if ((oCcList != null) && (oCcList.size() > 0)) {
					ccAdd = new Address[oCcList.size()];
					for (int i = 0; i < oCcList.size(); i++) {
						ccAdd[i] = new InternetAddress(oCcList.get(i)
								.toString());
						ccTo = ccTo + ccAdd[i] + ",";
					}
					message.addRecipients(Message.RecipientType.CC, ccAdd);
				}

				message.setSubject(strSubject);
				message.setText(strMessage);
				if (messageType != null) {
					message.setDataHandler(new DataHandler(strMessage,
							"text/plain"));
				}

				// Send message
				Transport.send(message);

				AppConstants.NPDLOGGER.info("Mail sent to - " + sendTo);
				AppConstants.NPDLOGGER.info("Mail sent to in cc - "
						+ ccTo);
				AppConstants.NPDLOGGER
						.info("Mail was sent successfully.");
			}
		} catch (Exception oException) {
			AppConstants.NPDLOGGER.error(AppUtility
					.getStackTrace(oException));
		}

	}

	/**
	 * Method for sending mail to contacts in ArrayList
	 */
	public synchronized void sendMessageWithAttachment(FormFile fileAttachment) {

		SendMail mail=new SendMail();
		
		String []to=new String[oToList.size()];
		int i=0;
		for (Object ob: oToList) {
			to[i++]=(String)ob;
		}
		
		String []cc=new String[oCcList.size()];
		i=0;
		for (Object ob: oCcList) {
			cc[i++]=(String)ob;
		}
		
		String []bcc=new String[0];
		
		String subject=getStrSubject();
		
		String from="";
		
		String message=getStrMessage();
		
		try {
			mail.postMail(to, cc, bcc, subject, message, from, null);
		} catch (NpdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	public synchronized void sendMessageWithFormFile(FormFile fileAttachment) {

		SendMail mail=new SendMail();
		
		String []to=new String[oToList.size()];
		int i=0;
		for (Object ob: oToList) {
			to[i++]=(String)ob;
		}
		
		String []cc=new String[oCcList.size()];
		i=0;
		for (Object ob: oCcList) {
			cc[i++]=(String)ob;
		}
		
		String []bcc=new String[0];
		
		String subject=getStrSubject();
		
		String from="";
		
		String message=getStrMessage();
		
		//saving fileAttachment to directory
		
		//send directry path
		
		
		
		try {
			mail.postMailWithAttachment(to, cc, bcc, subject, message, from, fileAttachment);
			
			//delte file
		} catch (NpdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	/**
	 * Method for sending mail to contacts in ArrayList
	 */
	public synchronized void sendMessageWithFormFile(FormFile[] fileAttachment,File files[]) {

		SendMail mail=new SendMail();
		
		String []to=new String[oToList.size()];
		int i=0;
		for (Object ob: oToList) {
			to[i++]=(String)ob;
		}
		
		String []cc=new String[oCcList.size()];
		i=0;
		for (Object ob: oCcList) {
			cc[i++]=(String)ob;
		}
		
		String []bcc=new String[0];
		
		String subject=getStrSubject();
		
		String from="";
		
		String message=getStrMessage();
		
		try {
			mail.postMailWithAttachment(to, cc, bcc, subject, message, from, fileAttachment,files);
		} catch (NpdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
		public synchronized void sendMessageWithAttachemntsPath(String[] fileAttachment,String[] fileNames) {

		SendMail mail=new SendMail();
		
		String []to=new String[oToList.size()];
		int i=0;
		for (Object ob: oToList) {
			to[i++]=(String)ob;
		}
		
		String []cc=new String[oCcList.size()];
		i=0;
		for (Object ob: oCcList) {
			cc[i++]=(String)ob;
		}
		
		String []bcc=new String[0];
		
		String subject=getStrSubject();
		
		String from="";
		
		String message=getStrMessage();
		
		try {
			mail.postMailWithAttachmentPath(to, cc, bcc, subject, message, from, fileAttachment,fileNames);
		} catch (NpdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	public synchronized void sendMessageWithAttachment_prev(FormFile fileAttachment) {

		Address[] toAdd = null;
		Address[] ccAdd = null;
		String sendTo = "";
		String ccTo = "";
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		Multipart multipart = new MimeMultipart();
		FileOutputStream outStream = null;
		DataSource source = null;

		try {
			if ((oToList != null) && (oToList.size() > 0)) {
				toAdd = new Address[oToList.size()];

				try {
					outStream = new FileOutputStream(fileAttachment
							.getFileName());
					outStream.write(fileAttachment.getFileData());
					outStream.flush();
				} catch (FileNotFoundException e) {
					AppConstants.NPDLOGGER.error(AppUtility
							.getStackTrace(e));
				} catch (IOException e) {
					AppConstants.NPDLOGGER.error(AppUtility
							.getStackTrace(e));
				}

				AppConstants.NPDLOGGER.info("SMTP IP: "
						+ GetPropertiesUtility.getProperty("SMTP_HOST_IP"));
				Properties props = System.getProperties();
				Authenticator auth = new SMTPAuthenticatorUtility();
				Encryption encryption = new Encryption();

				props.put("mail.smtp.host", GetPropertiesUtility
						.getProperty("SMTP_HOST_IP"));
				props.put("mail.smtp.auth", "true");
				props.put("mail.transport.protocol", "smtp");

				// Get a Session object
				Session session = Session.getInstance(props, auth);
				MimeMessage message = new MimeMessage(session);

				// Define message
				message.setFrom(new InternetAddress(encryption.decrypt(
						GetPropertiesUtility.getProperty("GENERIC_USERNAME"),
						AppConstants.ENCRYPTION_KEY)));
				for (int i = 0; i < oToList.size(); i++) {
					toAdd[i] = new InternetAddress(oToList.get(i).toString());
					sendTo = sendTo + toAdd[i] + ",";
				}

				message.addRecipients(Message.RecipientType.TO, toAdd);
				if ((oCcList != null) && (oCcList.size() > 0)) {
					ccAdd = new Address[oCcList.size()];
					for (int i = 0; i < oCcList.size(); i++) {
						ccAdd[i] = new InternetAddress(oCcList.get(i)
								.toString());
						ccTo = ccTo + ccAdd[i] + ",";
					}
					message.addRecipients(Message.RecipientType.CC, ccAdd);
				}

				message.setSubject(strSubject);

				messageBodyPart.setText(strMessage);
				multipart.addBodyPart(messageBodyPart);

				// Part two is attachment
				messageBodyPart = new MimeBodyPart();
				source = new FileDataSource(fileAttachment.getFileName());
				messageBodyPart.setDataHandler(new DataHandler(source));
				messageBodyPart.setFileName(fileAttachment.getFileName());
				multipart.addBodyPart(messageBodyPart);

				// Put parts in message
				message.setContent(multipart);
				// Send message
				Transport.send(message);

				AppConstants.NPDLOGGER.info("Mail sent to - " + sendTo);
				AppConstants.NPDLOGGER.info("Mail sent to in cc - "
						+ ccTo);
				AppConstants.NPDLOGGER
						.info("Mail was sent successfully.");
			}
		} catch (AddressException e) {
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
		} catch (MessagingException e) {
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
		} catch (EncryptionException e) {
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
		} catch (Exception e) {
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
		} finally {
			try {
				if (outStream != null) {
					outStream.close();
					File tempFile = new File(fileAttachment.getFileName());
					if (tempFile != null) {
						tempFile.delete();
					}
				}
			} catch (IOException e) {
				AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			} catch (SecurityException e) {
				AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			}
		}

	}

	/**
	 * Method for sending mail to contacts in ArrayList One at a time.
	 */
	public synchronized void sendMessageToArrayListOneAtTime() {

		try {
			if ((oToList != null) && (oToList.size() > 0)) {

				AppConstants.NPDLOGGER
						.info("GetPropertiesUtility.getProper--"
								+ GetPropertiesUtility
										.getProperty("SMTP_HOST_IP"));
				Properties props = System.getProperties();
				props.put("mail.smtp.host", GetPropertiesUtility
						.getProperty("SMTP_HOST_IP"));
				props.put("mail.smtp.auth", "true");
				props.put("mail.transport.protocol", "smtp");
				Authenticator auth = new SMTPAuthenticatorUtility();
				// Get a Session object
				Session session = Session.getInstance(props, auth);

				Address[] toAdd = new Address[oToList.size()];
				for (int i = 0; i < oToList.size(); i++) {
					toAdd[i] = new InternetAddress(oToList.get(i).toString());
					AppConstants.NPDLOGGER.info("Sending mail to - "
							+ toAdd[i]);
					try {
						// Define message
						MimeMessage message = new MimeMessage(session);
						Encryption encryption = new Encryption();
						message.setFrom(new InternetAddress(encryption.decrypt(
								GetPropertiesUtility
										.getProperty("GENERIC_USERNAME"),
								AppConstants.ENCRYPTION_KEY)));
						message
								.addRecipient(Message.RecipientType.TO,
										toAdd[i]);
						message.setSubject(strSubject);
						message.setText(strMessage);
						if (messageType != null) {
							message.setDataHandler(new DataHandler(strMessage,
									"text/plain"));
						}

						// Send message
						Transport.send(message);
						AppConstants.NPDLOGGER
								.info("Mail sent successfully to - " + toAdd[i]);
					} catch (Exception e) {
						AppConstants.NPDLOGGER
								.error("Error occured while sending mail to - "
										+ toAdd[i]);
						AppConstants.NPDLOGGER.error("Error is - "
								+ e.getMessage());
					}
				}
			}
		} catch (Exception oException) {
			AppConstants.NPDLOGGER.error(AppUtility
					.getStackTrace(oException));
		}

	}

	public static String getMessage(Object[] parameters, String bundleName,
			String propertyName) {

		ResourceBundle bundle = null;
		MessageFormat formatter = new MessageFormat("");

		String pattern = AppConstants.INI_VALUE;
		String temporalMessage = AppConstants.INI_VALUE;
		bundle = ResourceBundle.getBundle(bundleName);
		pattern = bundle.getString(propertyName);
		formatter.applyPattern(pattern);
		temporalMessage = formatter.format(parameters);

		return temporalMessage;

	}
	
	public synchronized void sendMessageWithoutAttachment() {

		Address[] toAdd = null;
		Address[] ccAdd = null;
		String sendTo = "";
		String ccTo = "";
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		Multipart multipart = new MimeMultipart();
		FileOutputStream outStream = null;
		DataSource source = null;

		try {
			if ((oToList != null) && (oToList.size() > 0)) {
				toAdd = new Address[oToList.size()];

				AppConstants.NPDLOGGER.info("SMTP IP: "
						+ GetPropertiesUtility.getProperty("SMTP_HOST_IP"));
				Properties props = System.getProperties();
				Authenticator auth = new SMTPAuthenticatorUtility();
				Encryption encryption = new Encryption();

				props.put("mail.smtp.host", GetPropertiesUtility
						.getProperty("SMTP_HOST_IP"));
				props.put("mail.smtp.auth", "true");
				props.put("mail.transport.protocol", "smtp");

				// Get a Session object
				Session session = Session.getInstance(props, auth);
				MimeMessage message = new MimeMessage(session);

				// Define message
				message.setFrom(new InternetAddress(encryption.decrypt(
						GetPropertiesUtility.getProperty("GENERIC_USERNAME"),
						AppConstants.ENCRYPTION_KEY)));
				for (int i = 0; i < oToList.size(); i++) {
					toAdd[i] = new InternetAddress(oToList.get(i).toString());
					sendTo = sendTo + toAdd[i] + ",";
				}

				message.addRecipients(Message.RecipientType.TO, toAdd);
				if ((oCcList != null) && (oCcList.size() > 0)) {
					ccAdd = new Address[oCcList.size()];
					for (int i = 0; i < oCcList.size(); i++) {
						ccAdd[i] = new InternetAddress(oCcList.get(i)
								.toString());
						ccTo = ccTo + ccAdd[i] + ",";
					}
					message.addRecipients(Message.RecipientType.CC, ccAdd);
				}

				message.setSubject(strSubject);

				messageBodyPart.setText(strMessage);
				multipart.addBodyPart(messageBodyPart);

				// Part two is attachment
				messageBodyPart = new MimeBodyPart();
				messageBodyPart.setDataHandler(new DataHandler(source));
				multipart.addBodyPart(messageBodyPart);

				// Put parts in message
				message.setContent(multipart);
				// Send message
				Transport.send(message);

				AppConstants.NPDLOGGER.info("Mail sent to - " + sendTo);
				AppConstants.NPDLOGGER.info("Mail sent to in cc - "
						+ ccTo);
				AppConstants.NPDLOGGER
						.info("Mail was sent successfully.");
			}
		} catch (AddressException e) {
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
		} catch (MessagingException e) {
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
		} catch (EncryptionException e) {
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
		} catch (Exception e) {
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
		} 

	}

	
}