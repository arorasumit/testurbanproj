package com.ibm.ioes.ecrm;

import java.io.PrintWriter;
import java.io.StringWriter;
import org.apache.log4j.Logger;
import com.ibm.ioes.utilities.AppConstants;

public class CRMLogger {

	private static final Logger logger;
	
	static {

		logger = Logger.getLogger(CRMLogger.class);
	}
	
	public static void SysErr(String msg)
	{		
		logger.info(msg);
	}
	public static void LOG(boolean logToFile, boolean logToConsole,Throwable ex, String msg) {
		if(logToFile) logger.info(msg);
		if(logToFile) logger.info(getStackTrace(ex));
		if(logToConsole) System.out.println(msg);
		if(logToConsole) System.out.println(getStackTrace(ex));
	}
	public static String getStackTrace(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		pw.flush();
		return sw.toString();
	}
}
