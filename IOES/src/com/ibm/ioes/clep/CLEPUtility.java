package com.ibm.ioes.clep;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.ibm.ioes.forms.NewOrderDto;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Utility;

public class CLEPUtility {

	private static final Logger logger;

	static {

		logger = Logger.getLogger(CLEPUtility.class);
	}
	
	public static String spGetChangeOrderSubType = "{call IOE.GET_CHANGE_ORDER_SUBTYPE(?)}";// To Fetch Change Order Sub type
	public static String spAttachWorkflowForChangeOrder = "{call IOE.SP_ATTACH_CHANGE_ORDER_WORKFLOW(?,?,?,?,?)}";// To Fetch Change Order Sub type
	
	
	
	
	public static String replaceWord(String original, String find, String replacement) {
	    int i = original.indexOf(find);
	    if (i < 0) {
	        return original;  // return original if 'find' is not in it.
	    }
	  
	    String partBefore = original.substring(0, i);
	    String partAfter  = original.substring(i + find.length());
	  
	    return partBefore + replacement + partAfter;
	}
	
	/*function : 	isEmailValid
	 * return type:	String
	 * @Author		:Anil Kumar
	 * Date			:21-oct-2011
	 * Purpose		:TO validate email
	 * */
	public static boolean isEmailValid(String email){  
		boolean isvalid = false;  
		  
		  	
		String input = email;
	      //Checks for email addresses starting with
	      //inappropriate symbols like dots or @ signs.
		  Pattern p=null;Matcher m=null;
		  
		  //p = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		  p = Pattern.compile("[a-zA-z0-9\\.\\_\\-]+\\@[a-zA-z0-9\\.\\_\\-]+\\.[a-zA-z0-9]+");
	      m = p.matcher(input);
	      if (m.find()) {
	    	  isvalid=true;
	      }else{
	    	  isvalid=false;
	      }
	      	      
	      return isvalid;
	}
	
	/*function : 	isValidNONNegativeNumeric
	 * return type:	String
	 * @Author		:Anil Kumar
	 * Date			:11-nov-2011
	 * Purpose		:TO validate numeric
	 * */
	public static boolean isValidNONNegativeNumeric(String value){  
		boolean isvalid = false;
				
		CharSequence inputStr = value;
		String expression = "[0-9]+";
		Pattern pattern = Pattern.compile(expression);
	    Matcher matcher = pattern.matcher(inputStr);
	    if(matcher.matches()){
	    	isvalid = true;
	    }
		return isvalid;
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
	public static void LOG_ITER(boolean logToFile, boolean logToConsole,Throwable ex, String msg) {
		if(Utility.fnCheckNull(ex.getLocalizedMessage()).contains("getNextException()")){
			SQLException sqlexc=(SQLException)ex;
			do{
				if(logToFile) logger.info(msg);
				if(logToFile) logger.info(getStackTrace(sqlexc));
				if(logToConsole) System.out.println(msg);
				if(logToConsole) System.out.println(getStackTrace(sqlexc));
				sqlexc=sqlexc.getNextException();
				
			}while(sqlexc!=null);
		}else{
			if(logToFile) logger.info(msg);
			if(logToFile) logger.info(getStackTrace(ex));
			if(logToConsole) System.out.println(msg);
			if(logToConsole) System.out.println(getStackTrace(ex));
		}
	}
	public static String getStackTrace(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		pw.flush();
		return sw.toString();
	}
}
