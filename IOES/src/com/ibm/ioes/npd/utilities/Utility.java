package com.ibm.ioes.npd.utilities;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;

import com.ibm.appsecure.exception.EncryptionException;
import com.ibm.appsecure.util.Encryption;
import com.ibm.appsecure.util.IEncryption;
import com.ibm.ioes.npd.beans.AttachmentDto;
import com.ibm.ioes.npd.beans.ValidationBean;
import com.ibm.ioes.npd.exception.NpdException;
import com.ibm.ioes.npd.hibernate.beans.SessionObjectsDto;
import com.ibm.ioes.npd.hibernate.dao.CommonBaseDao;



public class Utility {
	
	private static final Logger logger;

	static {

		logger = Logger.getLogger(Utility.class);
	}
	
	final static char escapeChar='/';
	final static String specialChars="%_/";

	public static String addToCondition(String whereCondition, String condition) {
		
		if(condition!=null && !"".equals(condition.trim()))
		{
				if(whereCondition==null)
				{
					whereCondition="";
				}
				if("".equals(whereCondition.trim()))
				{
					return " ("+condition+") ";
				}
				else
				{
					return whereCondition+" AND ("+condition+") ";
				}
		
		}
		return whereCondition;
	}
	
	public static String generateDateRelativeCondition  (String operator,String op1,String op2,String columnName,SimpleDateFormat sdf)
	throws Exception
	{
		String Condition=null;
		if(op1==null || "".equals(op1.trim()))
			return Condition;
		
		
		SimpleDateFormat queryFormat=new SimpleDateFormat("yyyy-MM-dd");
		try {
			if(operator==null || "".equals(operator.trim()))
			{
				return Condition;
			}
			if("BETWEEN".equals(operator))
			{
				Condition=" "+columnName+" BETWEEN date('"+queryFormat.format(sdf.parse(op1))+"') AND date('"+queryFormat.format(sdf.parse(op2))+"') ";
			}
		} catch (ParseException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
			throw new NpdException("Exception : " + ex.getMessage(), ex);
			
			
		}
		/*
		else if("GREATER".equals(operator))
			{
				//Condition=" "+columnName+" > "+op1+" ";
			}else
			if("GEQUAL".equals(operator))
			{
				//Condition=" "+columnName+" >= "+op1+" ";
			}else
			if("LESS".equals(operator))
			{
				//Condition=" "+columnName+" < "+op1+" ";
			}else
			if("LEQUAL".equals(operator))
			{
				//Condition=" "+columnName+" <= "+op1+" ";
			}else
			if("EQUAL".equals(operator))
			{
				//Condition=" "+columnName+" = "+op1+" ";
			}
			*/
					
		return Condition;
	}
	
	public static String generateTimeStamp_DateRelativeCondition  (String operator,String op1,String op2,String columnName,SimpleDateFormat sdf)
	throws Exception
	{
		String Condition=null;
		if(op1==null || "".equals(op1.trim()))
			return Condition;
		
		
		SimpleDateFormat queryFormat=new SimpleDateFormat("yyyy-MM-dd");
		try {
			if(operator==null || "".equals(operator.trim()))
			{
				return Condition;
			}
			if("BETWEEN".equals(operator))
			{
				Condition=" date("+columnName+") BETWEEN date('"+queryFormat.format(sdf.parse(op1))+"') AND date('"+queryFormat.format(sdf.parse(op2))+"') ";
			}
		} catch (ParseException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
			throw new NpdException("Exception : " + ex.getMessage(), ex);
			
			
		}
		/*
		else if("GREATER".equals(operator))
			{
				//Condition=" "+columnName+" > "+op1+" ";
			}else
			if("GEQUAL".equals(operator))
			{
				//Condition=" "+columnName+" >= "+op1+" ";
			}else
			if("LESS".equals(operator))
			{
				//Condition=" "+columnName+" < "+op1+" ";
			}else
			if("LEQUAL".equals(operator))
			{
				//Condition=" "+columnName+" <= "+op1+" ";
			}else
			if("EQUAL".equals(operator))
			{
				//Condition=" "+columnName+" = "+op1+" ";
			}
			*/
					
		return Condition;
	}
	
	
	
	
	//TODO Chnage Made For In Operator By Sumit on 11-Mar-2010 
	public static String generateRelativeCondition(String operator,String op1,String op2,String columnName)
	{
		String Condition=null;
		
		if(op1==null || "".equals(op1.trim()))
			return Condition;
		
			
	
			if("GREATER".equals(operator))
			{
				Condition=" "+columnName+" > "+op1+" ";
			}else
			if("GEQUAL".equals(operator))
			{
				Condition=" "+columnName+" >= "+op1+" ";
			}else
			if("LESS".equals(operator))
			{
				Condition=" "+columnName+" < "+op1+" ";
			}else
			if("LEQUAL".equals(operator))
			{
				Condition=" "+columnName+" <= "+op1+" ";
			}else
			if("EQUAL".equals(operator))
			{
				Condition=" "+columnName+" = "+op1+" ";
			}else
			if("BETWEEN".equals(operator))
			{
				Condition=" "+columnName+" BETWEEN "+op1+" AND "+op2+" ";
			}else
			if("IN".equals(operator))
			{
				Condition=" "+columnName+" IN ("+op1+" )";
			}
			
		
		return Condition;
	}
	
	public static String generateStringLikeCondition(String str,String columnName)
	{
		String Condition=null;
		
		if(str!=null && !"".equals(str.trim()))
		{
			Condition=" upper("+columnName+") LIKE upper('%"+formatSpecialCharacters(str.trim())+"%') { escape '"+escapeChar+"' } ";
		}
		
		return Condition;
	}

	public static String generateStringExactCondition(String str,String columnName)
	{
		String Condition=null;
		
		if(str!=null && !"".equals(str.trim()))
		{
			Condition=" upper("+columnName+") LIKE upper('"+formatSpecialCharacters(str.trim())+"') { escape '"+escapeChar+"' } ";
		}
		
		return Condition;
	}

	public static String generateIntCondition(int str,String columnName)
	{
		String Condition=null;
		
		if(str!=0)
		{
			Condition=" "+columnName+" = " + str;
		}
		
		return Condition;
	}

	public static String formatSpecialCharacters(String str)
	{
		String format=str;
		if(format==null || "".equals(format.trim()))
		{
			return format;
		}
		
		format=format.replaceAll("'", "''");
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<format.length();i++)
		{
			char ch=format.charAt(i);
			if(spChar(ch))
			{
				sb.append(String.valueOf(escapeChar)+String.valueOf(ch));
			}
			else
			{
				sb.append(ch);
			}
			
		}
		
		return sb.toString();
		//return str;
	}

	public static boolean spChar(char ch)
	{
		int i=specialChars.indexOf(ch);
		if(i<0)
		{
			return false;
		}
		return true;
	}

	public static String trimIfNotNull(String str)
	{
		if(str!=null)
		{
			str=str.trim();
		}
		return str;
	}

	public static String IfNullToBlank(String str)
	{
		if(str==null)
			return "";
		else
			return str.trim();
	}

	public static String getContentType(String docName) {
		String contentType="";
		String extension=docName.substring(docName.lastIndexOf(".")+1);
		
		if("txt".equals(extension))
		{
			contentType="application/txt";
		}
		else if("jpeg".equals(extension))
		{
			contentType="image/jpeg";
		} 
		else if("doc".equals(extension))
		{
			contentType="application/msword";
		} 
		
		
		return contentType;
	}

	public static boolean IsAttachmentTypeAllowed(String fileName) {
		String extension=fileName.substring(fileName.lastIndexOf(".")+1);
		String csvAllowedTypes=Messages.getMessageValue("attachemnt.Types.allowed");
		StringTokenizer st=new StringTokenizer(csvAllowedTypes,",.");
		String types[]=new String[st.countTokens()];
		int i=0;
		while(st.hasMoreElements())
		{
			types[i++]=st.nextToken();
		}
		
		for(int j=0;j<types.length;j++)
		{
			if(types[j].equalsIgnoreCase(extension) )
			{
				return true;
			}	
		}
		
		
		return false;
	}
	
	public static void showDocument(AttachmentDto attachmentDto,HttpServletResponse response) throws Exception
	{
		try {
			//String contentType=null;
			//contentType=Utility.getContentType(attachmentDto.getDocName());
			response.setHeader("Title",attachmentDto.getDocName());
			//response.setContentType(contentType);
			//String conDisp="inline; filename="+removeExtension(attachmentDto.getDocName());
			//response.setHeader("Content-Disposition",conDisp);
			response.setHeader("Content-Disposition","attachment;filename=\""+attachmentDto.getDocName()+"\"");
			InputStream inputStream=attachmentDto.getInputStream();
			
			 byte[] bytearray = new byte[1048576];
			 int size=0;
			 
			 while((size=inputStream.read(bytearray))!= -1 ){

			       response.getOutputStream().write(bytearray,0,size);
			  }
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage()
					+ " Exception occured in showDocument method of "
					+ "Utility class");
			
		}
	}
	
	static String removeExtension(String str)
	{
		int i=str.lastIndexOf('.');
		if(i>0)
		{
			return str.substring(0,i);
		}
		else
			return str; 
	}
	
/*public static boolean validateAttachment(FormFile file, ActionMessages messages) {
		boolean validation_error=false; 
		if(file!=null && !"".equals(file.getFileName()))
		{
			//int size=file.getFileSize();
			CommonBaseDao objDao=new CommonBaseDao();
			if(file!=null && !"".equals(file.getFileName()))
			{
				int size=file.getFileSize();
				//if(size>Integer.parseInt(Messages.getMessageValue("attachment.maxSize")))
				if(size>objDao.getAttachemntSize())
				{
					messages.add("attachment",new ActionMessage("error.attachment.size"));
					validation_error=true;
				}
			}
		}
		return validation_error;
	}
*/
	public static boolean validateAttachment(FormFile file, ActionMessages messages) {
		boolean validation_error=false; 
		SessionObjectsDto sessionObjectsDto = SessionObjectsDto.getInstance();
		
		if(file!=null && !"".equals(file.getFileName()))
		{
			//int size=file.getFileSize();
			CommonBaseDao objDao=new CommonBaseDao();
			if(file!=null && !"".equals(file.getFileName()))
			{
				int size=file.getFileSize();
				//if(size>Integer.parseInt(Messages.getMessageValue("attachment.maxSize")))
				if(size>sessionObjectsDto.getAttachmentSize())
				{
					messages.add("attachment",new ActionMessage("error.attachment.size"));
					validation_error=true;
				}
				if(size==0)
				{
					messages.add("attachment",new ActionMessage("error.attachment.size.empty"));
					validation_error=true;
				}
				if(!Utility.IsAttachmentTypeAllowed(file.getFileName()))
				{
					messages.add("attachment",new ActionMessage("error.attachment.type.notAllowed"));
					validation_error=true;
				}
			}
		}
		return validation_error;
	}
	
	static SimpleDateFormat showReportPattern=new SimpleDateFormat("dd-MMM-yy");
	static SimpleDateFormat showReportPattern2=new SimpleDateFormat("dd-MMM-yyyy");
	static SimpleDateFormat inPattern_1=new SimpleDateFormat("yyyy-MM-dd");
	public static String showDate_Report(java.util.Date date)//dd-MMM-yy
	{
		if(date==null)
		{
			return "";
		}
		else
			return showReportPattern.format(date.getTime());
		
	}
	public static String showDate_Report(java.sql.Date date)//dd-MMM-yy
	{
		if(date==null)
		{
			return "";
		}
		else
			return showReportPattern.format(date);
		
	}
	public static String showDate_Report2(java.sql.Date date)//dd-MMM-yyyy
	{
		if(date==null)
		{
			return "";
		}
		else
			return showReportPattern2.format(date);
		
	}
	public static String showDate_Report(String str)//dd-MMM-yy
	{
		try {
			if(str==null)
			{
				return "";
			}
			else
			{
				return showReportPattern.format(inPattern_1.parse(str));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		
	}
	
	public static String showDate_Report(java.sql.Timestamp ts)// Output pattern dd-MMM-yy
	{
		if(ts==null)
		{
			return "";
		}
		else
			return showReportPattern.format(ts);
		
	}
	
	public static boolean isAttachementBlank(FormFile file, ActionMessages messages,String isTaskMand) {
		boolean validation_error=false; 
		CommonBaseDao objDao=new CommonBaseDao();
		if(isTaskMand.equalsIgnoreCase("1"))
		{
			if("".equals(file.getFileName()))
			{
					messages.add("attachment",new ActionMessage("error.attachment.blank"));
					validation_error=true;
			}
		}
		return validation_error;
	}
	
	public static String decryptString(String str)// Output pattern dd-MMM-yy
	{
		IEncryption objenrcypt = new Encryption();
		String strencodedValue = null;
		try
		{
		    	
			strencodedValue =objenrcypt.decrypt(str);
		}
		catch(EncryptionException ex)
		{
			
		}
		catch(Exception e)
		{
		    
		}
		return strencodedValue;
	}
	
	//Server Side Validations
	final public static String CASE_DIGITS_ONLY="1";
	final public static String CASE_DECIMALNUBER="2";
	final public static String CASE_MANDATORY="3";
	final public static String CASE_SPECIALCHARACTERS="4";
	final public static String CASE_EMAIL="5";
	final public static String CASE_MAXLENGTH="6";
	final public static String CASE_DATECOMPARISION_MANDATORY="7";
	final public static String CASE_DATECOMPARISION_NONMANDATORY="8";
	final public static String CASE_VN_DATE_VALID="9";
	final public static String CASE_MANDATORY_MULTIPLE_STRING="10";
	final public static String CASE_TIME_PATTERN="11";
	final public static String CASE_DATECOMPARISION_NONMANDATORY_v2="12";
	final public static String CASE_MANDATORY_SINGLESELECT_STRING="13";
	
	public static ValidationBean validateValue(ValidationBean bean, String validateCase)  throws Exception
	{
		if(validateCase==null || "".equals(validateCase.trim()))
		{
			return null;
		}
		
		String[] cases= validateCase.split(",");
		String value=bean.getValue();
		String displayLabelvalue=bean.getDisplayLabel();
		for (String iterCase : cases) 
		{
			if(iterCase==null || "".equals(iterCase.trim()))
			{
				continue;
			}
			int intCase=Integer.parseInt(iterCase);
			switch(intCase)
			{
				case 1 ://CASE_DIGITS_ONLY="1";
				{
					if(value!=null)
					{
			 			if(!Pattern.matches("[0-9]*", value))
			 			{
			 				bean.addToMessageStrings("Only numeric values are allowed in "+displayLabelvalue,"CASE_DIGITS_ONLY");
			 			}
					}
		 			break;
				}
				case 2 : //CASE_DECIMALNUBER="2"; 	
				{
					if(value!=null)
					{
						if(!Pattern.matches("[0-9]*\\.[0-9]*", value))
			 			{
			 				bean.addToMessageStrings("Decimal values are not allowed in "+displayLabelvalue,"CASE_DECIMALNUBER");
			 			}
					}
		 			break;
				}
				case 3 ://CASE_MANDATORY="3"; 	
				{
		 			if((value==null) || ("".equalsIgnoreCase(value.trim())))
		 			{
		 				bean.addToMessageStrings("Please enter values in "+displayLabelvalue,"CASE_MANDATORY");
		 			}
		 			break;
					
				}
				case 4 : //CASE_SPECIALCHARACTERS="4";
				{
					SessionObjectsDto sessionObjectsDto =SessionObjectsDto.getInstance();
					String specialCharaters= sessionObjectsDto.getSpecialCharacter();

					if(value!=null && !value.trim().equals("") && specialCharaters!=null)
					{
						specialCharaters=specialCharaters.trim();
						String [] spec=specialCharaters.split(",");
						for(int i=0;i<spec.length;i++)
						{						
							if(spec[i]!=null && !"".equals(spec[i]) && value.indexOf(Integer.parseInt(spec[i]))>=0)
							{
								bean.addToMessageStrings("Special characters are not allowed in "+displayLabelvalue,"CASE_SPECIALCHARACTERS");
								break;
							}
						}
					}
		 			break;
				}
				case 5 : //CASE_EMAIL="5";	
				{
					if(value!=null && !"".equals(value.trim()))
					{
						if(!java.util.regex.Pattern.matches("[a-zA-z0-9\\.\\_\\-]+\\@[a-zA-z0-9\\.\\_\\-]+\\.[a-zA-z0-9]+", value))
						{
							bean.addToMessageStrings("Invalid Email entered in "+displayLabelvalue,"CASE_EMAIL");
						}
					}
					break;
				}
				case 6 ://CASE_MAXLENGTH="6"; 	
				{
		 			if(value!=null && value.length()>bean.getMaxlength())
		 			{
		 				bean.addToMessageStrings("Maximum length allowed in "+displayLabelvalue+" is "+bean.getMaxlength(),"CASE_MAXLENGTH");
		 			}
		 			break;	
				}
				case 7 ://CASE_DATECOMPARISION_MANDATORY
				{
					if(bean!=null)
					{
						try {
							
							dateComparision(bean,CASE_DATECOMPARISION_MANDATORY);
							
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							//throw new Exception(e);
							bean.addToMessageStrings("Format of either "+bean.getDisplayDateLabel1()+" or "+bean.getDisplayDateLabel2()+" is Incorrect","CASE_MAXLENGTH");
						}
						
						
					}
					break;
					
				}
				case 8 ://CASE_DATECOMPARISION_NONMANDATORY
				{
					if(bean!=null)
					{
						try {
							
							dateComparision(bean,CASE_DATECOMPARISION_NONMANDATORY);
							
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							//throw new Exception(e);
							bean.addToMessageStrings("Format of either "+bean.getDisplayDateLabel1()+" or "+bean.getDisplayDateLabel2()+" is Incorrect","CASE_MAXLENGTH");
						}
						
						
					}
					break;
					
				}
				case 9 ://DATE_FORAMAT_CHECK="9";
				{
									
					try
					{
						bean.getDateFormat_in().parse(bean.getDatevalue1());
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
						bean.addToMessageStrings("*Please enter "+bean.getDisplayDateLabel1()+" in Correct Format","DATE_FORAMAT_CHECK");
					}
					
		 			break;
									
				}
				case 10 :
				{
		 			if(bean.getMultipleSelect()==null || bean.getMultipleSelect().length==0)
		 			{
		 				bean.addToMessageStrings("Please select values in "+bean.getDisplayLabel(),"CASE_MANDATORY");
		 			}
		 			break;
					
				}
				case 11:
				{
					try
					{
						new SimpleDateFormat(bean.getPattern()).parse(bean.getValue());
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
						bean.addToMessageStrings("*Please enter "+bean.getDisplayLabel()+" in Correct Format","DATE_FORAMAT_CHECK");
					}
					break;
				}
				case 12 ://CASE_DATECOMPARISION_NONMANDATORY_v2
				{
					if(bean!=null)
					{
						try {
							
							dateComparision(bean,CASE_DATECOMPARISION_NONMANDATORY_v2);
							
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							//throw new Exception(e);
							bean.addToMessageStrings("Format of either "+bean.getDisplayDateLabel1()+" or "+bean.getDisplayDateLabel2()+" is Incorrect","CASE_MAXLENGTH");
						}
						
						
					}
					break;
					
				}
				case 13:
				{//CASE_MANDATORY_SINGLESELECT_STRING
					if(bean.getValue()==null || "".equals(bean.getValue()) || bean.getNoSelectValue().equals(bean.getValue()))
					{
						bean.addToMessageStrings("Please select "+bean.getDisplayLabel(),"CASE_MANDATORY");
					}
					break;
				}
			}
			
		}
		return bean;
	}
	public static String generateCSV(String... cases)
	{
		String str="";
		if(cases==null)
		{
			return "";
		}
		if(cases.length==0)
		{
			return "";
		}
		for (String i : cases) {
			str=str+","+i;
		}
		
		return str.substring(1);
	}
	private static void dateComparision(ValidationBean bean,String caseV) throws Exception
	{
		int flagProceed=0;
		if(caseV.equals((CASE_DATECOMPARISION_MANDATORY)))
		{
			
			if(bean.getDatevalue1()==null || "".equals(bean.getDatevalue1()))
			{
				bean.addToMessageStrings("Please enter "+bean.getDisplayDateLabel1(),"CASE_MANDATORY");
				flagProceed=1;
			}
			if(bean.getDatevalue2()==null || "".equals(bean.getDatevalue2()))
			{
				bean.addToMessageStrings("Please enter "+bean.getDisplayDateLabel2(),"CASE_MANDATORY");
				flagProceed=1;
			}
			if(flagProceed==1)
			{
				return;
			}
			
		}else 
		if(caseV.equals(CASE_DATECOMPARISION_NONMANDATORY))
		{
			if(bean.getDatevalue1()==null || "".equals(bean.getDatevalue1()) || bean.getDatevalue2()==null || "".equals(bean.getDatevalue2()))
			{
				bean.addToMessageStrings("Please enter both "+bean.getDisplayDateLabel1()+" and "+bean.getDisplayDateLabel2(),"CASE_DATECOMPARISION_NONMANDATORY");
				return;
			}
			
		}else if(caseV.equals(CASE_DATECOMPARISION_NONMANDATORY_v2))
		{
			if(((bean.getDatevalue1()==null || "".equals(bean.getDatevalue1())) 
					&& bean.getDatevalue2()!=null && !"".equals(bean.getDatevalue2()))
					||
					((bean.getDatevalue2()==null || "".equals(bean.getDatevalue2())) 
							&& bean.getDatevalue1()!=null && !"".equals(bean.getDatevalue1())))
			{
				bean.addToMessageStrings("Please enter both "+bean.getDisplayDateLabel1()+" and "+bean.getDisplayDateLabel2(),"CASE_DATECOMPARISION_NONMANDATORY");
				return;
			}
			else if(bean.getDatevalue1()==null || "".equals(bean.getDatevalue1()) || bean.getDatevalue2()==null || "".equals(bean.getDatevalue2()))
			{
				return;
			}
		}
		
		long comparision=bean.getDateFormat_in().parse(bean.getDatevalue1()).getTime()-bean.getDateFormat_in().parse(bean.getDatevalue2()).getTime();
		if(bean.getOperator().equals(bean.GREATER))
		{
			if(!(comparision>0))
			{
				bean.addToMessageStrings(bean.getDisplayDateLabel1()+" has to be greater than "+bean.getDisplayDateLabel2(),"");
			}
		}
		else if(bean.getOperator().equals(bean.GREATER_EQUAL))
		{
			if(!(comparision>=0))
			{
				bean.addToMessageStrings(bean.getDisplayDateLabel1()+" cannot be greater than "+bean.getDisplayDateLabel2(),"");
			}
		}
		else if(bean.getOperator().equals(bean.LESS))
		{
			if(!(comparision<0))
			{
				bean.addToMessageStrings(bean.getDisplayDateLabel1()+" has to be less than "+bean.getDisplayDateLabel2(),"");
			}
		}
		else if(bean.getOperator().equals(bean.LESS_EQUAL))
		{
			if(!(comparision<=0))
			{
				bean.addToMessageStrings(bean.getDisplayDateLabel1()+" cannot be greater than "+bean.getDisplayDateLabel2(),"");
			}	
		}
		else if(bean.getOperator().equals(bean.EQUAL))
		{
			if(!(comparision==0))
			{
				bean.addToMessageStrings(bean.getDisplayDateLabel1()+" has to be equal to"+bean.getDisplayDateLabel2(),"");
			}
		}
		
	}
	public static String ERROR_APPEND_MODE_SUM_ALL="ERROR_APPEND_MODE_SUM_ALL";
	public static String ERROR_APPEND_MODE_SUM_IF_NO_ERROR="ERROR_APPEND_MODE_SUM_IF_NO_ERROR";
	public static ArrayList appendErrors(ArrayList allErrors,ArrayList newErrors,String mode)
	{
		if(allErrors==null) return null;
		
		if(newErrors==null)  {
			return allErrors;
		}
		else 	{
			allErrors.addAll(newErrors);
			return allErrors;
		}
	}
	
	public static long firstDelay(int hours,int min , int sec)
	{
		
		long day_1=24*60*60*1000;
		
		java.util.Date currDate=new java.util.Date(System.currentTimeMillis());
		currDate.setHours(0);
		currDate.setMinutes(0);
		currDate.setSeconds(0);   
		long scheduledFirstTime=currDate.getTime()+ (hours*60*60*1000+min*60*1000+sec*1000);
		long firstDelay=scheduledFirstTime-System.currentTimeMillis();
		while(firstDelay<0)
		{
			firstDelay=firstDelay+day_1;
		}
		return firstDelay;
	}
	public static String shortForReport(String str,int size)
	{
		if(str==null)
		{
			return str;
		}
		else
		{
			if(size==0)
			{
				size=150;
			}
			if(str.length()>size)
			{
				return str.substring(0, size)+" ...";
			}
			else
			{
				return str;
			}
		}
		
	}
	
	public static ArrayList<String> monthYear(Date startDate,Date endDate)
	{
		SimpleDateFormat outputFormt=new SimpleDateFormat("MMM-yy");
		ArrayList<String> output=new ArrayList<String>();
		
		Calendar endCal=java.util.GregorianCalendar.getInstance();
		endCal.setTime(endDate);
		
		Calendar cal=java.util.GregorianCalendar.getInstance();
		cal.setTime(startDate);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		while(cal.getTime().before(endDate))
		{
			output.add(outputFormt.format(cal.getTime()));
			cal.add(Calendar.MONTH, 1);
		}
		if(cal.get(Calendar.MONTH)==endCal.get(Calendar.MONTH))
		{
			output.add(outputFormt.format(endDate));
		}
		
		return output;
	}
	
	public static ArrayList<String> firstDate(Date startDate,Date endDate)
	{
		SimpleDateFormat outputFormt=new SimpleDateFormat("dd/MM/yyyy");
		ArrayList<String> output=new ArrayList<String>();
		
		Calendar endCal=java.util.GregorianCalendar.getInstance();
		endCal.setTime(endDate);
		
		Calendar cal=java.util.GregorianCalendar.getInstance();
		cal.setTime(startDate);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		while(cal.getTime().before(endDate))
		{
			output.add(outputFormt.format(cal.getTime()));
			cal.add(Calendar.MONTH, 1);
		}
		if(cal.get(Calendar.MONTH)==endCal.get(Calendar.MONTH))
		{
			output.add(outputFormt.format(cal.getTime()));
		}
		
		return output;
	}
	
	
	/*public static void main(String[] args) {
		ArrayList<String> al=lastDate(new Date(2007-1900,1-1,13), new Date(2009-1900,11-1,20));
	}*/
	
	
	public static ArrayList<String> lastDate(Date startDate,Date endDate)
	{
		SimpleDateFormat outputFormt=new SimpleDateFormat("dd/MM/yyyy");
		ArrayList<String> output=new ArrayList<String>();
		
		Calendar endCal=java.util.GregorianCalendar.getInstance();
		endCal.setTime(endDate);
		
		Calendar cal=java.util.GregorianCalendar.getInstance();
		cal.setTime(startDate);
		/*int lastdate=cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		cal.set(Calendar.DATE, lastdate);*/ 
		while(cal.getTime().before(endDate))
		{
			cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE)) ;
			output.add(outputFormt.format(cal.getTime()));
			cal.set(Calendar.DATE, 1) ;
			cal.add(Calendar.MONTH, 1);
		}
		if(cal.get(Calendar.MONTH)==endCal.get(Calendar.MONTH))
		{
			cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE)) ;
			output.add(outputFormt.format(cal.getTime()));
		}
		
		return output;
	}

	public static void main(String[] args) {
		String s=convertWithOutE_WithOutDotZero("9.87654321698745E+24");System.err.println(s);
		s=convertWithOutE_WithOutDotZero("9.87654321698745E24");System.err.println(s);
		s=convertWithOutE_WithOutDotZero("9.87654321698745E-24");System.err.println(s);
		s=convertWithOutE_WithOutDotZero("00.0006000");System.err.println(s);
		s=convertWithOutE_WithOutDotZero("111");System.err.println(s);
		s=convertWithOutE_WithOutDotZero("111.0");System.err.println(s);
		s=convertWithOutE_WithOutDotZero("111.00");System.err.println(s);
		s=convertWithOutE_WithOutDotZero("111.010");System.err.println(s);
		s=convertWithOutE_WithOutDotZero("111.001");System.err.println(s);
		s=convertWithOutE_WithOutDotZero(".001");System.err.println(s);
		s=convertWithOutE_WithOutDotZero("001.");System.err.println(s);
	}
	public static String convertWithOutE_WithOutDotZero(String valstr) {
		
		String temp=null;
		if(valstr.indexOf('E')>=0 || valstr.indexOf('e')>=0)
		{
			BigDecimal bd=new BigDecimal(valstr);
			temp= bd.toPlainString();
		}
		else
		{
			temp= valstr;
		}
		
		int indexOfDot=temp.indexOf('.');
		if(indexOfDot>=0)
		{
			for(int i=indexOfDot+1;i<temp.length();i++)
			{
				if(temp.charAt(i)!='0')
				{
					return temp;
				}
			}
			return temp.substring(0,indexOfDot); 
		}
		else
		{
			return temp;
		}
		
	}

	
	public static final String [] colorCodes={"0099FF","FF00FF","6600FF","FF6633","000000","333333","CCCCCC","663366",
			"FFFFFF","33FFFF","CC6600","663333","000000","FF0000","FFFF33"};
}
