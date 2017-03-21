package com.ibm.ioes.npd.utilities;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

import com.ibm.appsecure.exception.EncryptionException;
import com.ibm.appsecure.util.Encryption;

/**
 * This class is used for authentication with SMTP server.
 * 
 * @author Shantnu Jain
 */
public class SMTPAuthenticatorUtility extends Authenticator {
	/**
	 * Automatically generated constructor: SMTPAuthenticatorUtility
	 */
	public SMTPAuthenticatorUtility() {
	}

	/**
	 * @see javax.mail.Authenticator#getPasswordAuthentication()
	 */
	public PasswordAuthentication getPasswordAuthentication() {
		PasswordAuthentication pwd = null;
		try {
			Encryption encryption = new Encryption();
			pwd = new PasswordAuthentication(GetPropertiesUtility.getProperty("GENERIC_USERNAME"), 
					GetPropertiesUtility.getProperty("GENERIC_PASSWORD"));
		} catch (Exception e) {
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
		}
		return pwd;
	}
}
