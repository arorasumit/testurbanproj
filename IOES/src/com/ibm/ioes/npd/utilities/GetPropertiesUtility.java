package com.ibm.ioes.npd.utilities;

import java.io.InputStream;
import java.util.Properties;


public class GetPropertiesUtility {

	// Get the static inputstream value
	private static InputStream oInputStream = null;

	// Create a new instance of the properties file
	private static Properties oProps = new Properties();
	private static InputStream oInputStreamquery = null;

	private static final String PROPFILE = "DirPath.properties";
	private static final String PROPFILEQUERY = "DBQuery.properties";
	// Static block for getting the properties file
	static {
		try {
			oInputStream = (GetPropertiesUtility.class).getClassLoader()
					.getResourceAsStream(PROPFILE);
			
			oInputStreamquery = (GetPropertiesUtility.class).getClassLoader()
			.getResourceAsStream(PROPFILEQUERY);
			
			if (oInputStream == null) {
				// In case the input steram is null throw an exception
				throw new Exception(
						"Unable to load the properties file. Make sure it is in the CLASSPATH.");
			}
			
			if (oInputStreamquery == null) {		// 2587 change
				// In case the input steram is null throw an exception
				throw new Exception(
						"Unable to load the DBQuery.properties file. Make sure it is in the CLASSPATH.");
			}
			
			
		} catch (Exception oException) {
			AppConstants.NPDLOGGER.error(AppUtility
					.getStackTrace(oException));
		}
		try {
			oProps.load(oInputStream);
			oProps.load(oInputStreamquery);
		} catch (Exception oException) {
			AppConstants.NPDLOGGER.error(AppUtility
					.getStackTrace(oException));
		}
	}

	/**
	 * Function is used for getting the property of the variable
	 * 
	 * @param strPropertyName
	 * @return String Porerty value from the Properties file
	 */
	public static String getProperty(String strPropertyName) {
		return oProps.getProperty(strPropertyName);
	}
}
