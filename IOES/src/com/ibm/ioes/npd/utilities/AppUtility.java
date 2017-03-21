package com.ibm.ioes.npd.utilities;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * @author Varun
 * 
 * Class containing common utility functions for the application
 */
public class AppUtility {
	static String pathName = GetPropertiesUtility.getProperty("local.Path");

	static String forecast_months_gap = GetPropertiesUtility
			.getProperty("FORECAST_MONTHS_GAP");

	/**
	 * Method to return arraylist from a comma seperated string
	 * 
	 */
	public static ArrayList commaToArrayList(String str) {

		ArrayList tempList = null;
		if ((str != null) && (str.trim() != "")) {
			tempList = new ArrayList();
			StringTokenizer st = new StringTokenizer(str, ",");
			for (; st.hasMoreTokens();) {
				tempList.add(st.nextToken());
			}
		}
		return tempList;
	}

	/**
	 * Function to print stacktrace to log file
	 * 
	 */
	public static String getStackTrace(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		pw.flush();
		return sw.toString();
	}

	/**
	 * @param coded_string
	 * @return the decrypted password
	 */
	public static String decryptPassword(String coded_string) {

		String password = "";
/*		String [] st = coded_string.split("+");
		for(int i = 0; i < st.length; i++){			
			password += (char) ((Integer.parseInt(st[i])) ^ 10);
		}*/
		StringTokenizer st = new StringTokenizer(coded_string, "+");
		while (st.hasMoreTokens()) {
			password += (char) ((Integer.parseInt(st.nextToken())) ^ 10);
		}
		return password;
	}

	/**
	 * This method will return the ArrayList of String of futire months names
	 * with gap of forecast_months_gap i.e. Nov'09 Dec'09 Jan'10
	 * 
	 * @return
	 */
	public static ArrayList getMonthsNamesList(Date date) {
		
		Calendar rightNow = Calendar.getInstance();
	    rightNow.setTime(date);
	    
		int curr_year = rightNow.get(Calendar.YEAR);
		
		String[] strMonths = new String[]{"Jan", "Feb","Mar","Apr", "May","Jun","Jul","Aug","Sep","Oct","Nov","Dec","Jan", "Feb","Mar","Apr", "May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};  
	 	int month_no = rightNow.get(Calendar.MONTH);
	 	String year = String.valueOf(curr_year).substring(2,4);
	 	
		if(month_no+1>12)
		{
			curr_year = curr_year + 1;
			year = String.valueOf(curr_year).substring(2,4);
	 	}
		String mon1 =  strMonths[month_no++] + "-"+year;
		if(month_no+1>12)
		{
			curr_year = curr_year + 1;
			year = String.valueOf(curr_year).substring(2,4);
	 	}
		String mon2 = strMonths[month_no++]+ "-"+year;
		if(month_no+1>12)
		{
			curr_year = curr_year + 1;
			year = String.valueOf(curr_year).substring(2,4);
	 	}
		String mon3 = strMonths[month_no++]+ "-"+year;
		
	    System.out.println(mon1+" "+mon2+" "+mon3+" " );
	    ArrayList monthsList = new ArrayList();
	    monthsList.add(mon1);
	    monthsList.add(mon2);
	    monthsList.add(mon3);
	    
	    return monthsList;
	}

	/**
	 * 
	 * @param date
	 * @param inPattern
	 * @param outPattern
	 * @return formatted date
	 */
	public static String chageDateForat(String date, String inPattern,
			String outPattern) {
		if (date == null || inPattern == null || outPattern == null) {
			return null;
		} else {
			try {
				SimpleDateFormat sdfIn = new SimpleDateFormat(inPattern);
				sdfIn.applyPattern(inPattern);
				Date dt = sdfIn.parse(date);

				SimpleDateFormat sdfOut = new SimpleDateFormat(outPattern);
				String returndate = sdfOut.format(dt);
				return returndate;

			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

/**
 * This method will convert DD/MM/YYY to Calender Object  
 * 
 * @param required_date
 * @return
 */
public static Calendar formatStringtoCalender(String required_date) 
 {
	StringTokenizer stringTokenizer = new StringTokenizer(required_date,"/");
	int date = Integer.parseInt(stringTokenizer.nextToken());
	int month = Integer.parseInt(stringTokenizer.nextToken());
	int year = Integer.parseInt(stringTokenizer.nextToken());
	
	Calendar calendar = Calendar.getInstance();
	calendar.set(year, month-1, date);
	return calendar;
 }

/**
 * This method will convert Calender Object to DD/MM/YYY 
 * 
 * @param required_date
 * @return
 */
public static String formatCalenderToString(Calendar newcalendar)  
 {
	String formatted_date = null;
	 
	  /*  System.out.println(newcalendar.get(Calendar.MONTH)+1);
	    System.out.println(newcalendar.get(Calendar.DATE));
	    System.out.println(newcalendar.get(Calendar.YEAR));*/
	     String newdate = String.valueOf(newcalendar.get(Calendar.DATE));  
	    String newmonth = String.valueOf(newcalendar.get(Calendar.MONTH)+1);
	    String newyear = String.valueOf(newcalendar.get(Calendar.YEAR));
	    if(newdate.length()==1) newdate = "0"+newdate;
	    if(newmonth.length()==1) newmonth = "0"+newmonth;
	    formatted_date = newdate+"/"+newmonth+"/"+newyear;
 
	return formatted_date;
 }

	public static Date stringToDate(String str_date) {

		DateFormat formatter = null;
		Date date = null;
		try {
			
			formatter = new SimpleDateFormat("dd/MM/yyyy");
			date = (Date) formatter.parse(str_date);
			
		} catch (ParseException e) {
			e.printStackTrace();

		}
		return date;
	}
	
	public static int daysInMonth(int iMonth, int iYear) {
		return 32 - new Date(iYear, iMonth, 32).getDate();
	}
public static String getTodayDate() {
	Calendar rightNow = Calendar.getInstance();
	rightNow.setTime(new Date());
	
	String todayDate = ""+rightNow.get(Calendar.DAY_OF_MONTH) + "/"+(rightNow.get(Calendar.MONTH)+1) +"/"+ rightNow.get(Calendar.YEAR) ;
	
	return todayDate;
}

}
