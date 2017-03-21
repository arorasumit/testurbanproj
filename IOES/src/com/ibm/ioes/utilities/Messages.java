package com.ibm.ioes.utilities;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author sukeshkr
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Messages {
	private static final String APPLICATION_PROPERTIES  = "ApplicationResources"; 
		
	private static final ResourceBundle mainResourceBundle  =  ResourceBundle.getBundle(APPLICATION_PROPERTIES);
	private static final ResourceBundle RESOURCE_BUNDLE = mainResourceBundle;

	private Messages() {
	}

	/** 
	 * @param key
	 * @return
	 */
	public static String getMessageValue(String key) {
		try {
			return mainResourceBundle.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
	public static String getMessageValueWithEnvironment(String key) {
		try {
			return mainResourceBundle.getString(key+"_"+ApplicationFlags.IB2B_ENVIRONMENT);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
	/**
	 * 
	 * @param key
	 * @return
	 */
	public static String getString(String key) {
		// TODO Auto-generated method stub
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}