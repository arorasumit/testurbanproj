//tag		Name       Date			CSR No			Description 
//[001]   Lawkush	 30 March		00-05422		Added one case of mandatory check
//[002]	  Lawkush 	 12-June-12	    CSR00-05422		For making service  summary and service product attributes editable or non editable according to change type , subchange type and SUBCHANGETYPE_NETWORK_CHANGE_EDITABLE flag  in database
//[003]   Vijay      07-Feb-2013                    Message was not proper, so make a proper error message. 
//[005]   Raveendra  28-Nov-2014                  add method getdataIssueException in case of data issue exception is generated when scheduler is in progress
//[005]   Gunjan 	 7-Jan-2015     20141113-R1-020802		ParallelUpgrade-Multiple LSI Selection
//[006]  Pankaj Thakur [27-april-15]  made SimpleDateFormat Thread Safe
//[007]   Pankaj Thakur  [3-jun-2015]  Added a method getOrderCreationSource() for Approval tab to get the OrderCreationSource
//[008] VIPIN SAHARIA 11th Aug 2015 CBR_20141219-R2-020936-Billing Efficiency Program Drop 3 (BFR 17) Reverting MBIC CC mapping as per L3-VCS mapping
//[009] Priya Gupta  CSR-20160301-XX-022139-Auto Billing 
package com.ibm.ioes.utilities;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;

import com.ibm.appsecure.exception.EncryptionException;
import com.ibm.appsecure.util.Encryption;
import com.ibm.appsecure.util.IEncryption;
import com.ibm.fx.dto.ExtendedData;
import com.ibm.fx.dto.FxExternalIdDto;
import com.ibm.fx.mq.CreateAccount;
import com.ibm.ioes.actions.FxSchedulerAction;
import com.ibm.ioes.beans.ReportsBean;
import com.ibm.ioes.beans.UserInfoDto;
import com.ibm.ioes.beans.ValidationBean;
import com.ibm.ioes.beans.ViewOrderDto;
import com.ibm.ioes.bulkupload.formbean.TransactionUploadFormBean;
import com.ibm.ioes.ecrm.ECRMMigration;
import com.ibm.ioes.ecrm.TransactionBatch;
import com.ibm.ioes.exception.IOESException;
import com.ibm.ioes.forms.ServiceLineDTO;
import com.ibm.ioes.newdesign.dto.ServiceLinkingDTO;
import com.ibm.ioes.dao.NewOrderDao;
import com.ibm.ioes.dao.NewOrderDaoExt;
import com.ibm.ioes.dao.ViewOrderDao;


public class Utility {
	
	private static final Logger logger;

	static {

		logger = Logger.getLogger(Utility.class);
	}
	
	final static char escapeChar='/';
	final static String specialChars="%_/";
	
	private static final String sqlGetExtendedData = "call IOE.FX_GET_EXTENDED_DATA(?,?)";
	private static final String sqlGetFxExternalIds = "{call IOE.FX_GET_EXTERNAL_IDS(?,?)}";;
	private static final String sqlLogException="{call IOE.SP_LOG_EXCEPTION(?,?,?,?,?,?)}";
	SimpleDateFormat showStdFmt=new SimpleDateFormat("dd/MM/yyyy");

	
	public static void SysErr(String msg)
	{
		System.err.println(msg);
		
		logger.info(msg);
	}

	public static void SysOut(Object msg)
	{
		//System.out.println(msg);
		
		logger.info(msg);
	}
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
			throw new IOESException("Exception : " + ex.getMessage(), ex);
			
			
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
			throw new IOESException("Exception : " + ex.getMessage(), ex);
			
			
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
	
	/*public static void showDocument(AttachmentDto attachmentDto,HttpServletResponse response) throws Exception
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
	}*/
	
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
	/*public static boolean validateAttachment(FormFile file, ActionMessages messages) {
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
	}*/
	
	 SimpleDateFormat showReportPattern=new SimpleDateFormat("dd-MMM-yy");
	 SimpleDateFormat showReportPattern2=new SimpleDateFormat("dd-MMM-yyyy");
	 SimpleDateFormat inPattern_1=new SimpleDateFormat("yyyy-MM-dd");
	 SimpleDateFormat showReportPattern3=new SimpleDateFormat("dd/MM/yyyy");
	 SimpleDateFormat showReportPattern5=new SimpleDateFormat("dd/MMM/yyyy hh:mm:ss aa"); // 003 start
	public static String showDate_Report(java.util.Date date)//dd-MMM-yy
	{
		if(date==null)
		{
			return "";
		}
		else
			return new Utility().showReportPattern.format(date.getTime());
		
	}
	public static String showDate_Report(java.sql.Date date)//dd-MMM-yy
	{
		if(date==null)
		{
			return "";
		}
		else
			return new Utility().showReportPattern.format(date);
		
	}
	
	// added by manisha start
	
	public static String showDate_Report4(Date date)//dd-MMM-yyyy
	{
		if(date==null)
		{
			return "";
		}
		else
			return new Utility().showReportPattern2.format(date);
		
	}
	
	

	
	public static String showDate_Report3(Date date)//dd-MMM-yyyy
	{
		if(date==null)
		{
			return "";
		}
		else
			return new Utility().showReportPattern3.format(date);
		
	}
//	 added by manisha end	
	
	
	// 003 start
	
	public static String showDate_Report5(Date date)//dd-MMM-yyyy
	{
		if(date==null)
		{
			return "";
		}
		else
			return new Utility().showReportPattern5.format(date);
		
	}
	
	// 003 end
	
	public static String showDate_Report2(java.sql.Date date)//dd-MMM-yyyy
	{
		if(date==null)
		{
			return "";
		}
		else
			return new Utility().showReportPattern2.format(date);
		
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
				return new Utility().showReportPattern.format(new Utility().inPattern_1.parse(str));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		
	}
	//Added By varun
	public static String showDate_Report2(String str)//
	{
		try {
			if(str==null)
			{
				return "";
			}
			else
			{
				return new Utility().showReportPattern2.format(new Utility().showReportPattern3.parse(str)).toString();
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		
	}
	
	public static String showDate_Report3(String str)//
	{
		try {
			if(str==null)
			{
				return "";
			}
			else
			{
				return new Utility().showReportPattern2.format(new Utility().inPattern_1.parse(str)).toString();
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		
	}
	// End Varun
	public static String showDate_ddmmyyyy(String str)//Input Format: dd-mmm-yyy;Output Format: dd/mm/yyyy
	{
		String dateOut="";
		try {
			if(str==null)
			{
				return dateOut;
			}
			dateOut=new Utility().showReportPattern3.format(new Utility().showReportPattern2.parse(str)).toString();	
			
		} catch (ParseException e) {
			e.printStackTrace();			
		}
		return dateOut;
	}
	
	public static String showDate_Report(java.sql.Timestamp ts)// Output pattern dd-MMM-yy
	{
		if(ts==null)
		{
			return "";
		}
		else
			return new Utility().showReportPattern.format(ts);
		
	}
	
	/*public static boolean isAttachementBlank(FormFile file, ActionMessages messages,String isTaskMand) {
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
	}*/
	
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
	final public static String CASE_YN="14";
	final public static String CASE_DECIMALNUBERWITHE="15";
	//Start [001] 
	final public static String CASE_MANDATORY1="16";
	final public static String CASE_MANDATORY2="17";

	//End [001]
	//[005] start
	final public static String CASE_CSV_LONG="18";
	final public static String CASE_MAXCSV="19";
	
	/**
	 * check special characters which does not allow in system, 
	 * this utility is same as javascript ValidateTextField method of utility.js 
	 */
	final public static String CASE_SPECIALCHARACTERS_V2="20";
	//[005] end
	
	
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
						if(!Pattern.matches("[0-9]*(\\.[0-9]*)?", value))
			 			{
							//--[003]--start//
			 				//bean.addToMessageStrings("Decimal values are not allowed in "+displayLabelvalue,"CASE_DECIMALNUBER");
							bean.addToMessageStrings("Only numeric or Decimal values are allowed in "+displayLabelvalue,"CASE_DECIMALNUBER");
							//--[003]--end//
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
					//SessionObjectsDto sessionObjectsDto =SessionObjectsDto.getInstance();
					//String specialCharaters= sessionObjectsDto.getSpecialCharacter();
					//,64 edit for @ character
					
					//String specialCharaters="33,35,36,94,42,43,61,91,93,39,59,123,125,124,34,60,62,64,38,40,63,60,62,34,92,41";
					String specialCharaters="";
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
					{	if(bean.getValue()!=null && !"".equals(bean.getValue().trim()))
						{
							new SimpleDateFormat(bean.getPattern()).parse(bean.getValue());
						}
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
				case 14:
				{//CASE_YN
					if(bean.getValue()!=null && !bean.getValue().trim().equals(""))
					{
						if(!bean.getValue().equalsIgnoreCase("Y") && !bean.getValue().equalsIgnoreCase("N") && !bean.getValue().equalsIgnoreCase("n") && !bean.getValue().equalsIgnoreCase("y"))
						{
							bean.addToMessageStrings("Invalid data inserted in "+bean.getDisplayLabel(),"CASE_YN");
						}
					}
					break;
				}
				// Start [001]
				case 16://CASE_MANDATORY1="16"
					if(("-1").equalsIgnoreCase(value))
		 			{
		 				bean.addToMessageStrings("Please select values in "+displayLabelvalue,"CASE_MANDATORY1");
		 			}
		 			break;
		 			
				case 17://CASE_MANDATORY2="17"
					if(("0").equalsIgnoreCase(value))
		 			{
		 				bean.addToMessageStrings("Please select values in "+displayLabelvalue,"CASE_MANDATORY2");
		 			}
		 			break;
				//End [001]
		 		//[005] start
				case 18://CASE_CSV_LONG="18"
				
					if(value != null){
						if(Pattern.matches(".*,$", value) 
								|| !("".equals(value) || Pattern.matches("(([0-9]{1,10})(,))*", value+",")  || Pattern.matches("[0-9]{1,10}", value)))  
			 			{
			 				bean.addToMessageStrings("Only comma separated numeric values(of maxlength 10) are allowed in "+displayLabelvalue,"CASE_CSV_LONG");
			 			}
						
					}
					break;
				
				case 19://CASE_MAXCSV="19"
					if(value != null){
						StringTokenizer st=new StringTokenizer(value, ",");
						if(st.countTokens()>21)
							bean.addToMessageStrings("Only 21 LogicalSI Nos are allowed in "+displayLabelvalue,"CASE_MAXCSV");
					}
					break;
				//[005] end
				case 20://SPECIAL CAHARACTERS VERSION 2
					if(value !=null){
						NewOrderDaoExt objDao=new NewOrderDaoExt();
						String specialCharaters=objDao.getSpecialCharContact();
						if(value!=null && !value.trim().equals("") && specialCharaters!=null)
						{
							specialCharaters=specialCharaters.trim();
							String [] spec=specialCharaters.split(",");							
							
							Set<Integer> userEntered=new HashSet<Integer>();
							Set<Integer> allowedSpecChars=new HashSet<Integer>();
							
							for(int i=0;i<value.length();i++){
								Integer code=value.codePointAt(i);
								if((code<65 || code>90) && (code<97 || code>122) && (code<48 || code>57)){
									userEntered.add(value.codePointAt(i));	
								}
							}

							for(int i=0;i<spec.length;i++){
								allowedSpecChars.add(Integer.parseInt(spec[i]));
							}
							
							userEntered.removeAll(allowedSpecChars);
							
							if(userEntered.size()>0){
								String str="";
								for (Integer code : userEntered) {
									str=str+(char)code.intValue();
								}
								bean.addToMessageStrings("Special characters: "+str+" are not allowed in "+displayLabelvalue,"CASE_SPECIALCHARACTERS");
							}
						}
					}
					break;
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
				bean.addToMessageStrings("Please enter Both "+bean.getDisplayDateLabel1()+" and "+bean.getDisplayDateLabel2(),"CASE_DATECOMPARISION_NONMANDATORY");
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
		/*String s=convertWithOutE_WithOutDotZero("9.87654321698745E+24");System.err.println(s);
		s=convertWithOutE_WithOutDotZero("9.87654321698745E24");System.err.println(s);
		s=convertWithOutE_WithOutDotZero("9.87654321698745E-24");System.err.println(s);
		s=convertWithOutE_WithOutDotZero("00.0006000");System.err.println(s);
		s=convertWithOutE_WithOutDotZero("111");System.err.println(s);
		s=convertWithOutE_WithOutDotZero("111.0");System.err.println(s);
		s=convertWithOutE_WithOutDotZero("111.00");System.err.println(s);
		s=convertWithOutE_WithOutDotZero("111.010");System.err.println(s);
		s=convertWithOutE_WithOutDotZero("111.001");System.err.println(s);
		s=convertWithOutE_WithOutDotZero(".001");System.err.println(s);
		s=convertWithOutE_WithOutDotZero("001.");System.err.println(s);*/
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
	
	public static String fnCheckNull(Object paramObject)
	{
		String paramValue="";
		try
		{
			if(paramObject != null)
			{
				paramValue = paramObject.toString();
			}
			else
			{
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return paramValue;
	}
	/*
	 * @author SR
	 * 
	 *  This method log exception in log file , prints it in console and returns a new Exception object with 
	 *  original excpton set as cause.
	 */
	public static IOESException onEx_LOG_RET_NEW_EX(Throwable ex, String methodName, String className, String msg,boolean logToFile, boolean logToConsole) {
		if(logToConsole) ex.printStackTrace();
		String str=ex.getMessage()
			+ " Exception occured in  :"+msg+" :"
			+Utility.getStackTrace(ex);
		if(logToFile) AppConstants.IOES_LOGGER.error(str);
		IOESException newEx=new IOESException(str) ;
		newEx.initCause(ex);
		return newEx;
	}
	public static void LOG(boolean logToFile, boolean logToConsole, String msg) {
		
		if(logToFile) AppConstants.IOES_LOGGER.info(msg);
		if(logToConsole) System.out.println(msg);
	}
	public static void LOG(String msg) {
		
		AppConstants.IOES_LOGGER.info(msg);
		System.out.println(msg);
	}
	public static void LOG(Throwable ex) {
		AppConstants.IOES_LOGGER.info(getStackTrace(ex));
		System.out.println(getStackTrace(ex));
	}
	public static void LOG(boolean logToFile, boolean logToConsole,Throwable ex, String msg) {
		
		if(logToFile) AppConstants.IOES_LOGGER.info(msg);
		if(logToFile) AppConstants.IOES_LOGGER.info(getStackTrace(ex));
		if(logToConsole) System.out.println(msg);
		if(logToConsole) System.out.println(getStackTrace(ex));
	}
	
	/**
	 * Function to print stacktrace to log file
	 * 
	 */
	public static String getStackTrace(Throwable t) {
		if(t==null){
			return "Exception Object is Null.";
		}
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		pw.flush();
		return sw.toString();
	}
	
	public static boolean switchOn(String key) throws Exception{
		
		Connection conn=null;
		ResultSet rs=null;
		try
		{
			conn=DbConnection.getConnectionObject();
			rs=conn.createStatement().executeQuery("SELECT APPCONFIGID,KEYNAME,KEYVALUE FROM IOE.TM_APPCONFIG " +
					"WHERE KEYNAME='"+key+"'");
			if(rs.next())
			{
				if(!rs.getString("KEYVALUE").equalsIgnoreCase(AppConstants.SWITCH_ON))
				{
					String str=key+" switch is off";
					System.err.println(str);
					AppConstants.IOES_LOGGER.error(str);
					return false;
				}
			}
		}
		catch (Exception ex) {
			throw Utility.onEx_LOG_RET_NEW_EX(ex,"switchOn","Utility",null,true,true);
		}finally{
			try {
				DbConnection.closeResultset(rs);
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				throw Utility.onEx_LOG_RET_NEW_EX(e,"switchOn","Utility",null,true,true);
			}
		}
		return true;
	}
	
	public static String getAppConfigValue(String key) throws Exception{
		
		Connection conn=null;
		ResultSet rs=null;
		
		try
		{
			conn=DbConnection.getConnectionObject();
		
			rs=conn.createStatement().executeQuery("SELECT APPCONFIGID,KEYNAME,KEYVALUE FROM IOE.TM_APPCONFIG " +
					"WHERE KEYNAME='"+key+"'");
			if(rs.next())
			{
				return rs.getString("KEYVALUE");
			}
		}
		catch (Exception ex) {
			throw Utility.onEx_LOG_RET_NEW_EX(ex,"getAppConfigValue","Utility",null,true,true);
		}finally{
			try {
				DbConnection.closeResultset(rs);
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				throw Utility.onEx_LOG_RET_NEW_EX(e,"getAppConfigValue","Utility",null,true,true);
			}
		}
		throw new Exception("key not present :"+key);
	}
	
		public static String getAppConfigValue(Connection conn,String key) throws Exception{
		try
		{
			
			ResultSet rs=conn.createStatement().executeQuery("SELECT APPCONFIGID,KEYNAME,KEYVALUE FROM IOE.TM_APPCONFIG " +
					"WHERE KEYNAME='"+key+"'");
			if(rs.next())
			{
				return rs.getString("KEYVALUE");
			}
		}
		catch (Exception ex) {
			throw Utility.onEx_LOG_RET_NEW_EX(ex,"getAppConfigValue","Utility",null,true,true);
		}
		throw new Exception("key not present :"+key);
	}
	/**
	 * convert Blob into Byte Array
	 * 
	 * @param mntFiles
	 * @return Byte Array
	 * @throws Exception
	 */
	public static byte[] blobToByteArray(Blob b) throws Exception {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] File = null;
		byte[] buf = new byte[1048576];
		InputStream is = b.getBinaryStream();
		for (;;) {
			int dataSize = is.read(buf);

			if (dataSize == -1) {
				break;
			}
			baos.write(buf, 0, dataSize);
		}
		File = baos.toByteArray();
		b = null;
		return File;
	}
	public static byte[] clobToByteArray(Clob b) throws Exception {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] File = null;
		byte[] buf = new byte[1048576];
		InputStream is = b.getAsciiStream();
		for (;;) {
			int dataSize = is.read(buf);

			if (dataSize == -1) {
				break;
			}
			baos.write(buf, 0, dataSize);
		}
		File = baos.toByteArray();
		b = null;
		return File;
	}
	
	public static void javascriptExceptionShow(Exception ex) throws Exception
	{
		if("1".equals(Messages.getMessageValue("javascriptExceptionShow")))
   		{
   			throw Utility.onEx_LOG_RET_NEW_EX(ex, "", "", "", true, true);
   		}else
   		{
   			Utility.onEx_LOG_RET_NEW_EX(ex, "", "", "", true, true);
   			throw new Exception();
   		}
	}
	public static boolean isAuthorised( HttpServletRequest request ,  UserInfoDto tmEmployee)throws IOESException
	{
						logger.info("CommonUtils's isAuthorised method............");
						Connection connection =null;
						CallableStatement proc =null;
						CallableStatement npdProc =null;
						ResultSet rs = null;
						ResultSet rsNpd = null;
						boolean isAuthorised = false;
						boolean isRequestMappingNPDExist=false;
						try
						{
						String requestedURI = request.getRequestURI();
						String requestMapping = requestedURI.substring(requestedURI
								.lastIndexOf("/") + 1);
						
						connection = DbConnection.getConnectionObject();
						String query=null;
						String queryFromNPD=null;
						
						queryFromNPD="SELECT COUNT(1) as isRequestMappingExist FROM NPD.TM_INTERFACEMASTER WHERE INTERACE_MAPPING=?";
				
						npdProc=connection.prepareCall(queryFromNPD);
						npdProc.setString(1, requestMapping);
						rsNpd=npdProc.executeQuery();
						while(rsNpd.next())
						{
							if(!rsNpd.getString("isRequestMappingExist").equalsIgnoreCase("0"))
								isRequestMappingNPDExist = true; 
						}
						if(isRequestMappingNPDExist)
						{
							//check requestMapping in NPD Schema for authorization
							query=" SELECT COUNT(1) AS isAuthorised ";
							query=query+ "  FROM NPD.TM_ACCESSMATRIXMAPPING ";
							query=query+ " INNER JOIN NPD.TM_INTERFACEMASTER ";
							query=query+ " ON NPD.TM_ACCESSMATRIXMAPPING.INTERFACEID = NPD.TM_INTERFACEMASTER.INTERFACEID  ";
							query=query+ " WHERE NPD.TM_ACCESSMATRIXMAPPING.ACCESSFLAG =1 and ROLEID = ? AND INTERACE_MAPPING = ? ";
							proc=connection.prepareCall(query);
							proc.setLong(1, Long.parseLong(tmEmployee.getUserRoleId()));
							proc.setString(2, requestMapping);
							rs = proc.executeQuery();
							while(rs.next())
							{
								if(!rs.getString("isAuthorised").equalsIgnoreCase("0"))
								isAuthorised = true; 
							}
						}
						else
						{
							//check requestMapping in IOE Schema for authorization
							query=" SELECT COUNT(1) AS isAuthorised ";
							query=query+ "  FROM IOE.TM_ACCESSMATRIXMAPPING ";
							query=query+ " INNER JOIN IOE.TM_INTERFACEMASTER ";
							query=query+ " ON IOE.TM_ACCESSMATRIXMAPPING.INTERFACEID = IOE.TM_INTERFACEMASTER.INTERFACEID  ";
							query=query+ " WHERE IOE.TM_ACCESSMATRIXMAPPING.ISACTIVE =1 and ROLEID = ? AND INTERACE_MAPPING = ? ";
										 
							proc=connection.prepareCall(query);
							proc.setLong(1, Long.parseLong(tmEmployee.getUserRoleId()));
							proc.setString(2, requestMapping);
							rs = proc.executeQuery();
							while(rs.next())
							{
								if(!rs.getString("isAuthorised").equalsIgnoreCase("0"))
								isAuthorised = true; 
							}					  					
						}
						Utility.SysOut("Is User Authorised...." + isAuthorised+" for Role Id: "+tmEmployee.getUserRoleId());
					}
					catch (Exception ex) {
						//logger.error(ex.getMessage();
						throw new IOESException("Exception : " + ex.getMessage(),ex);
					}
					finally
					{
						
						try {
							DbConnection.closeResultset(rs);
							DbConnection.closeResultset(rsNpd);
							DbConnection.closeCallableStatement(proc);
							DbConnection.closeCallableStatement(npdProc);
							
							DbConnection.freeConnection(connection);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							throw new IOESException("Exception : " + e.getMessage(),e);
						}
					}
					return true;
		
	}

	public static Object getFXExtendedDataObject(String dataType, String value)throws Exception {
		
		
		Object extended = null;
		if(ExtendedData.ExtendedType_Integer.equals(dataType))
		{
			extended=new Integer(value);
		}
		else if(ExtendedData.ExtendedType_String.equals(dataType))
		{
			extended= value;
		}
		else if(ExtendedData.ExtendedType_DateTime.equals(dataType))
		{
			extended= new ExtendedData().Extended_sdf.parse(value);
		}
		return extended;
	}

	public static ArrayList<ExtendedData> getFxExtendedData(Connection conn, long parentId,String asscoiatedWith)throws Exception {
		ArrayList<ExtendedData> extendedData = new ArrayList<ExtendedData>();
		
		CallableStatement cstmt=conn.prepareCall(sqlGetExtendedData);
		cstmt.setLong(1, parentId);
		cstmt.setString(2, asscoiatedWith);
		ResultSet rs=cstmt.executeQuery();
		ExtendedData extendedDto=null;
		
		while(rs.next())
		{
			extendedDto=new ExtendedData();
			
			extendedDto.setParamId(rs.getInt("PARAM_ID"));
			String dataType=rs.getString("DATA_TYPE");
			extendedDto.setParamValue(Utility.getFXExtendedDataObject(dataType,rs.getString("PARAM_VALUE")));
			extendedData.add(extendedDto);
		}
		if(rs!=null)DbConnection.closeResultset(rs);
		if(cstmt!=null)DbConnection.closeCallableStatement(cstmt);
		return extendedData;
		
	}

	public static Map getFxExtendedDataMap(ArrayList<ExtendedData> extendedData) {
		Map apiExtendedData=new HashMap();
		
		for (ExtendedData data : extendedData) {
			apiExtendedData.put(data.getParamId(), data.getParamValue());
		}
		
		return apiExtendedData;
	}

	
	/**
	 * for only service 
	 * @param conn
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<FxExternalIdDto> getFxExternalIds(Connection conn, long id,String asscoiatedWith) throws Exception {
		ArrayList<FxExternalIdDto> externalIds= new ArrayList<FxExternalIdDto>();
		
		CallableStatement cstmt=conn.prepareCall(sqlGetFxExternalIds);
		cstmt.setLong(1, id);
		cstmt.setString(2, asscoiatedWith);
		ResultSet rsExternalIds=cstmt.executeQuery();
		FxExternalIdDto extDto=null;
		while(rsExternalIds.next())
		{
			extDto=new FxExternalIdDto();
			extDto.setExternalId(rsExternalIds.getString("EXTERNAL_ID"));
			extDto.setExternalIdType((short)rsExternalIds.getInt("EXTERNAL_ID_TYPE"));
			int isCurrent=rsExternalIds.getShort("IS_CURRENT");
			if(isCurrent==1)
			{
				extDto.setIsCurrent(true);	
			}
			else
			{
				extDto.setIsCurrent(false);
			}
			externalIds.add(extDto);
		}
		if(rsExternalIds!=null)DbConnection.closeResultset(rsExternalIds);
		if(cstmt!=null)DbConnection.closeCallableStatement(cstmt);
		return externalIds;
	}

	/**
	 * throws exception if at msgCode_Position is not 0 , with exxception msg as concatination of all
	 * @param cstmt
	 * @param sqlcodePosition
	 * @param msgCodePosition
	 * @param msgPosition
	 * @param diagnosticsPosition
	 * @throws Exception
	 */
	public static void throwExceptionIfFound(CallableStatement cstmt, int sqlcodePosition, int msgCodePosition, int msgPosition, int diagnosticsPosition) throws Exception{

			if(!"0".equals(cstmt.getString(msgCodePosition)))
			{
				throw new Exception("Exception is : @sqlcode="+cstmt.getString(sqlcodePosition)
										+",\n  @msgCode="+cstmt.getString(msgCodePosition)
										+",\n  @msg="+cstmt.getString(msgPosition)
										+",\n  @diagnostics="+cstmt.getString(diagnosticsPosition));
			}
		
	}
	/*Function		:validateAttachment
	 * return type	:boolean
	 * @Author		:Anil Kumar
	 * Date			:17-feb-11
	 * Purpose		:To validate attached file..
	 * */
	public static boolean validateAttachment(FormFile file, ActionMessages messages) {
		boolean validation_error=false; 
		
		
		if(file!=null && !"".equals(file.getFileName()))
		{
			//int size=file.getFileSize();
		
			if(file!=null && !"".equals(file.getFileName()))
			{
				int size=file.getFileSize();
				//if(size>Integer.parseInt(Messages.getMessageValue("attachment.maxSize")))
				if(size>Long.parseLong(Messages.getMessageValue("maxAttachmentSize")))
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
				if(file.getFileName().length()>255){
					messages.add("attachment",new ActionMessage("error.attachment.filename.large"));
					validation_error=true;
				}
			}
		}
		return validation_error;
	}
	
	/*function : 	removeCharAt
	 * return type:	String
	 * @Author		:Anil Kumar
	 * Date			:21-march-2011
	 * Purpose		:TO remove charater from string with specified position	 
	 * */
	public static String removeCharAt(String s, int pos) {
		   return s.substring(0,pos)+s.substring(pos+1);
		}
	//start[002]
	public static String getEnableDisableServiceProductAttributes(String propertyType,	int changeType,int subChangeType,int NetworkChangeEditableFlag)
	{
			String result="";
			String returnString="";
			if((changeType==1)&& (subChangeType==20))
			{
				if( (NetworkChangeEditableFlag==1))
				{
					result="enable";
				}
				else
				{
					result="disable";
				}
			}
			else
			{
				result="not_applicable";
			}
			
			if("not_applicable".equals(result))
			{
				returnString="";
				
			}
			else if("disable".equals(result))
			{
				if(propertyType.equals("DISABLE_PROPERTY"))
				{
					returnString="disabled=\"true\"";
				}else if(propertyType.equals("READONLY_PROPERTY"))
				{
					returnString="readOnly=\"true\"";
				}
			}
			else if("enable".equals(result))//for enable 
			{
				if(propertyType.equals("DISABLE_PROPERTY"))
				{
				//	returnString="disabled=\"false\"";
				}else if(propertyType.equals("READONLY_PROPERTY"))
				{
				//	returnString="readOnly=\"false\"";
				}
			}
			return returnString;
		
	}
	
	//End[002]
	
	public static String getDecryptedPassword(String Encryptionkey,String DBKey) throws Exception
	{
		Connection connection =null;
		PreparedStatement proc =null;
		ResultSet rs = null;
		String Fx_Password=null;
		
		try
		{
	
			connection = DbConnection.getConnectionObject();
			String query=null;
			query=" select DECRYPT_CHAR(cast(KEYVALUE AS VARCHAR(24) FOR BIT DATA) ,'"+ Encryptionkey+"') as KEYVALUE ";
			query=query+ "  FROM IOE.TM_APPCONFIG ";
			query=query+ " WHERE IOE.TM_APPCONFIG.KEYNAME =? ";
			proc=connection.prepareStatement(query);
			proc.setString(1, DBKey);
			rs = proc.executeQuery();
			while(rs.next())
			{
				Fx_Password=rs.getString("KEYVALUE");
				
			}
		}
		catch (Exception ex) {
			//logger.error(ex.getMessage();
			throw new IOESException("Exception : " + ex.getMessage(),ex);
		}
		finally
		{
			try 
			{
				DbConnection.closePreparedStatement(proc);
				DbConnection.freeConnection(connection);
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
		return Fx_Password;	
}	
	
	public static String showDate_std_fmt(java.util.Date date)//dd-MM-yy
	{
		if(date==null)
		{
			return "";
		}
		else
			return new Utility().showStdFmt.format(date);

	}

	public static void setFileDumpParams(HttpServletRequest request, ReportsBean objForm) {
		
		
		
		String isDumpAvailable=objForm.getIsDumpAvailable();
		
		
		if(isDumpAvailable==null || "".equals(isDumpAvailable))
		{
			//get Data from tm_interface master
			//set in ActionForm
			Connection conn=null;
			PreparedStatement stmt=null;
			ResultSet rs=null;
			try {
				conn=DbConnection.getConnectionObject();
				String sql="SELECT INTERFACEID,INTERFACENAME,IS_DUMP_AVAILABLE,DUMP_FILE_NAME FROM ioe.TM_INTERFACEMASTER WHERE INTERFACEID=?";
				stmt=conn.prepareStatement(sql);
				stmt.setLong(1,Integer.parseInt(objForm.getInterfaceId() ));
				rs=stmt.executeQuery();
				if(rs.next())
				{
					String rs_isDumpAvailable=rs.getString("IS_DUMP_AVAILABLE");
					if("Y".equalsIgnoreCase(rs_isDumpAvailable))
					{
						objForm.setIsDumpAvailable("Y");
						objForm.setDumpFileName(rs.getString("DUMP_FILE_NAME"));
					}else
					{
						objForm.setIsDumpAvailable("N");
					}
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				try {
					DbConnection.closeResultset(rs);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					DbConnection.closePreparedStatement(stmt);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					DbConnection.freeConnection(conn);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
			
		}
		
	}

//added by mansha bt dump start
public static void setFileDumpParamsforBT(HttpServletRequest request, TransactionUploadFormBean formBean) {
		
		
		
		String isDumpAvailable=formBean.getIsDumpAvailable();
		
		
		if(isDumpAvailable==null || "".equals(isDumpAvailable))
		{
			//get Data from tm_interface master
			//set in ActionForm
			Connection conn=null;
			PreparedStatement stmt=null;
			ResultSet rs=null;
			try {
				conn=DbConnection.getConnectionObject();
				String sql="SELECT INTERFACEID,INTERFACENAME,IS_DUMP_AVAILABLE,DUMP_FILE_NAME FROM ioe.TM_INTERFACEMASTER WHERE INTERFACEID=?";
				stmt=conn.prepareStatement(sql);
				stmt.setLong(1,Integer.parseInt(formBean.getInterfaceId() ));
				rs=stmt.executeQuery();
				if(rs.next())
				{
					String rs_isDumpAvailable=rs.getString("IS_DUMP_AVAILABLE");
					if("Y".equalsIgnoreCase(rs_isDumpAvailable))
					{
						formBean.setIsDumpAvailable("Y");
						formBean.setDumpFileName(rs.getString("DUMP_FILE_NAME"));
					}else
					{
						formBean.setIsDumpAvailable("N");
					}
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				try {
					DbConnection.closeResultset(rs);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					DbConnection.closePreparedStatement(stmt);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					DbConnection.freeConnection(conn);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
			
		}
		
	}

public static Object fnCheckNull2(Timestamp chargeStartDate_date) {
	String paramValue="";
	try
	{
		if(chargeStartDate_date != null)
		{
			return chargeStartDate_date;
		}
		else
		{
			return new Timestamp(-100);
		}
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
	}
	return paramValue;
}
//added by mansha bt dump	end	

public static String getOrderOwnedBy(List<ViewOrderDto> aList){
		for(ViewOrderDto viewOrderDto : aList){
			if(null != viewOrderDto.getChangeorderstatus() && viewOrderDto.getChangeorderstatus().trim().equals("1")){
				return viewOrderDto.getOwnerTypeId();
			}
		}
		return null;
	}

/**
* [004]
 * Santosh Srivastava: Added this method for converting date to String and  dd/MM/yyyy format to MM/dd/yyyy format 
 */
  public static String getReportOrderDate(String dateStr) {
		  
		  String dateInMMDDYY = null;
		  DateFormat fromFormat = new SimpleDateFormat("dd/MM/yyyy");
			fromFormat.setLenient(false);
			DateFormat toFormat = new SimpleDateFormat("MM/dd/yyyy");
			toFormat.setLenient(false);
			
			Date date = new Date();
			try {
				date = fromFormat.parse(dateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			dateInMMDDYY = toFormat.format(date);
			System.out.println(dateInMMDDYY);
			return dateInMMDDYY;
		  
	  }

public static String getCommaSperatedString(List<ServiceLineDTO> lstObj){
	if(lstObj==null)
		return null;
	
	String cps=null;
	for(ServiceLineDTO objDto:lstObj){
		if(cps==null){
			cps=String.valueOf(objDto.getServiceId());
		}else{
			cps=cps+","+String.valueOf(objDto.getServiceId());;
		}
	}
	return cps;
}
public static String getNextOwner(List<ViewOrderDto> aList, String currentOwner) throws Exception{
	String currenttaskId=null, nextOwner=null;
	for(ViewOrderDto objDto:aList){
		if(objDto.getOwnerTypeId().equalsIgnoreCase(currentOwner)){
			currenttaskId=objDto.getTaskID();
			break;
		}
	}
	for(ViewOrderDto objDto:aList){
		if(objDto.getPreviousTaskID().equalsIgnoreCase(currenttaskId)){
			nextOwner=objDto.getOwnerTypeId();
			break;
		}
	}
	return nextOwner;
}
public static String getLastRole(List<ViewOrderDto> aList){
	String lastTaskOwner = null;
	for(ViewOrderDto objDto:aList){
		if(objDto.getLastTask().equals(1)){
			lastTaskOwner=objDto.getOwnerTypeId();
			break;
		}
	}
	return lastTaskOwner;
}

/**
 * Round a double to n decimal places
 * @param value
 * @param places
 * @author Anil Kumar
 */
public static double round(double value, int places) {
    if (places < 0) throw new IllegalArgumentException();

    BigDecimal bd = new BigDecimal(value);
    bd = bd.setScale(places, RoundingMode.HALF_UP);
    return bd.doubleValue();
}
/**
 * compare serviceids
 * @param  orderRoleServicesDB
 * @param  comSepSelectedServiceIDs
 * @author Anoop Tiwari
 */
public static boolean CompareServiceIDs(List<ServiceLineDTO> orderRoleServicesDB,String comSepSelectedServiceIDs) {
	for(ServiceLineDTO objDto:orderRoleServicesDB){
		if(!comSepSelectedServiceIDs.contains(String.valueOf(objDto.getServiceId()))){
			return false;
		}
	}
	return true;
}
/**
 * is role id present in TpoWorkflow task
 * @param  taskList
 * @param  roleID
 * @return boolean
 * @date 13-March-2014
 * @author Anoop Tiwari
 * 
 */
public static boolean isRolePrsentInWorkFlow(int roleID,List<ViewOrderDto> aList){
	for(ViewOrderDto objDto:aList){
		if(String.valueOf(roleID).equalsIgnoreCase(objDto.getOwnerTypeId())){
			return true;
		}
	}
	return false;
}
public static String getTaskId(String roleID,List<ViewOrderDto> aList){
	for(ViewOrderDto objDto:aList){
		if(String.valueOf(roleID).equalsIgnoreCase(objDto.getOwnerTypeId())){
			return objDto.getTaskID();
		}
	}
	return null;
}

	public static long minutesInMilliSec(long mins){
		return mins*60*1000;
	}
	
	public static Boolean checkNullValues(Object value){
		if(null == value){
			return true;
		}else{
			return false;
		}
	}

	public enum NUMERIC {
		INTEGER,LONG,SMALLINT,STRING,DATE,TIMESTAMP,DOUBLE
	}
	
	/**
	 * This method set null value in statement if data is null else set data as same type 
	 * @param preStmnt
	 * @param index
	 * @param data
	 * @param dataType
	 * @throws Exception
	 * @author IBM_ADMIN
	 * @since June 2014
	 */
	public static void dbStatementsetData(PreparedStatement preStmnt,
			int index, Object data, NUMERIC dataType) throws Exception{
		switch(dataType){
		
			case INTEGER :  {	if(data==null){
									preStmnt.setNull(index, java.sql.Types.INTEGER);
								}else{
									preStmnt.setInt(index, ((Integer)data));
								}
								break;
							}
		
			case LONG :  {	if(data==null){
								preStmnt.setNull(index, java.sql.Types.BIGINT);
							}else{
								preStmnt.setLong(index, ((Long)data));
							}
							break;
						}
			
			case SMALLINT : {	if(data==null){
									preStmnt.setNull(index, java.sql.Types.SMALLINT);
								}else{
									preStmnt.setShort(index, ((Short)data));
								}
								break;
							}
			case STRING : {		if(data==null){
									preStmnt.setNull(index, java.sql.Types.VARCHAR);
								}else{
									preStmnt.setString(index, (String)data);
								}
								break;
						  }
			case DATE:{		if(data==null){
								preStmnt.setNull(index, java.sql.Types.DATE);
							}else{
								preStmnt.setDate(index, ((java.sql.Date)data));
							}
							break;
						}
			case TIMESTAMP:{		if(data==null){
										preStmnt.setNull(index, java.sql.Types.TIMESTAMP);
									}else{
										preStmnt.setTimestamp(index, ((Timestamp)data));
									}
									break;
							}
			case DOUBLE:{
								if(data==null){
									preStmnt.setNull(index, java.sql.Types.DOUBLE);
								}else{
									preStmnt.setDouble(index, (Double)data);
								}
								break;
							
						}
		}	
	}

	public static Long[] stringArrayToLongArray(String[] stringArr) {
		Long[] longArr=new Long[stringArr.length];
		
		for (int i = 0; i < stringArr.length; i++){
			longArr[i] = Long.valueOf(stringArr[i]);
		}
		
		return longArr;
	}

	public static Integer[] stringArrayToIntegerArray(String[] stringArr) {
		Integer[] integerArr=new Integer[stringArr.length];
		
		for (int i = 0; i < stringArr.length; i++){
			integerArr[i] = Integer.valueOf(stringArr[i]);
		}
		
		return integerArr;
	}
	
	public static String getCsv(Long[] Ids) {
		
		StringBuffer strngBuffds = new StringBuffer();
		for (Long objServiceId : Ids) {
			/*add comma for service Id separation if string contain the values*/
			if(strngBuffds.length() >0)
				strngBuffds.append(",");
			
			strngBuffds.append(objServiceId);
		}
		return strngBuffds.toString();
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
	
	public static boolean  isDataIssueException(ArrayList<String> dataIssueexception,Exception exception){

		Iterator<String> itr = dataIssueexception.iterator();
	     String throwexception=Utility.getStackTrace(exception);
	    // String throwexceptions=throwexception.trim();
	     String throwexceptions=throwexception.replaceAll("[\\r\\n\\t]+","");
	     
	    
		 String dataexception="";
		 String tdataissueexception="";
		 boolean  isdtatissueexception=false;
		if(throwexceptions!=null && !throwexceptions.isEmpty())
		{
	      while (itr.hasNext()){
	    	dataexception = itr.next();
	    	tdataissueexception=dataexception.replaceAll("[\\r\\n\\t]+","");
	    	if(throwexceptions.contains(tdataissueexception)){
	    		isdtatissueexception=true;	
	    		break;
	    	}
	      }
		}
		return isdtatissueexception;
	}
	
	/** start [005]
	 * @param conn
	 * @return
	 */
	public static ArrayList<String>getdataIssueException(Connection conn){
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		String exception = "";
		ArrayList<String> dataIssueException = new ArrayList<String>();
		String sqlGetDetails="SELECT DATA_ISSUE_EXCEPTION FROM IOE.TFX_DATA_ISSUE_EXCEPTION WHERE ISACTIVE=1";
		try { 
			pstmt = conn.prepareStatement(sqlGetDetails);
			resultSet=pstmt.executeQuery();
			while(resultSet.next()){
				 
				exception=resultSet.getString("DATA_ISSUE_EXCEPTION");

				dataIssueException.add(exception);
			}
		
		} catch (Exception ex) {
			ex.printStackTrace();
			Utility.LOG(true,true,"*****************Problem Occured while fetching Data issue"+ex);
			
		} finally{
			try {
				DbConnection.closeResultset(resultSet);
				DbConnection.closePreparedStatement(pstmt);
			} catch (Exception ex) {
				Utility.LOG(true,true,"*****************Problem Occured while fetching Data issue"+ex);
			}
		}
		return dataIssueException;
	}

	public static boolean isAssignedToAccessMatrixRole(String requestMapping,String requestedMethodNameNew,
			UserInfoDto objUserDto) {
		String roleId =objUserDto.getUserRoleId();
		boolean flag;
		if("universalWorkQueueAction.do".equals(requestMapping) && ("accessMatrixiB2B".equals(requestedMethodNameNew)) && "51192".equals(roleId)){
			flag=true;
		}else{
			flag =false;
		}
		return flag;
	}
	
	public static boolean isRequestAuthorisedforRoleorResp(String requestMapping,String requestedMethodNameNew,
			UserInfoDto objUserDto) {
		String roleId =objUserDto.getUserRoleId();
		Integer respId=objUserDto.getRespId();
		boolean flag;
		if("universalWorkQueueAction.do".equals(requestMapping) && ("channelPartnerMasteriB2B".equals(requestedMethodNameNew)) && AppConstants.CHANNEL_PARTNER_ADMIN.equals(roleId)){
			if(respId==AppConstants.RESPOSIBILITY_IB2B_ALL)
				flag =false;
			else
				flag=true;
		}else
			flag =false;

		return flag;
	}
	
	//[008] START
	/**
	 * To retrieve all service linking related details from TM_SERVICE_LINKING table 
	 * @return ArrayList<ServiceLinkingDTO>
	 * @author VIPIN SAHARIA
	 * @date 21-Jul-2015
	 */
	public static ArrayList<ServiceLinkingDTO> getServiceLinkingDetails(){
		String msg="In Utility's getServiceLinkingDetails method";
		 ArrayList<ServiceLinkingDTO> serviceLinkingList=null;
		Connection conn=null;
		PreparedStatement psLinkingAttDetails=null;
		ResultSet rsLinkingAttDetails=null;
		try {
			conn=DbConnection.getConnectionObject();
			ServiceLinkingDTO servLinkDtoObj=null;
			serviceLinkingList= new ArrayList<ServiceLinkingDTO>();
			psLinkingAttDetails=conn.prepareStatement("SELECT * FROM ioe.TM_SERVICE_LINKING WITH UR");
			rsLinkingAttDetails=psLinkingAttDetails.executeQuery();
			while(rsLinkingAttDetails.next()){
				servLinkDtoObj=new ServiceLinkingDTO();
				servLinkDtoObj.setPrimaryServiceTypeId(rsLinkingAttDetails.getLong("PRIMARY_SERVICETYPEID"));
				servLinkDtoObj.setPrimaryServiceDetailId(rsLinkingAttDetails.getLong("PRIMARY_SERVICEDETAILID"));
				servLinkDtoObj.setSecServiceTypeId(rsLinkingAttDetails.getLong("SEC_SERVICETYPEID"));
				servLinkDtoObj.setSecServiceDetailId(rsLinkingAttDetails.getLong("SEC_SERVICEDETAILID"));
				servLinkDtoObj.setPrimaryAttmasterId(rsLinkingAttDetails.getLong("PRIMARY_ATTMASTERID"));
				servLinkDtoObj.setMandLogic(rsLinkingAttDetails.getString("MANDATORY_LOGIC"));
				servLinkDtoObj.setPublishCondition(rsLinkingAttDetails.getString("PUBLISH_CONDITION"));
				Utility.LOG("INITIALIZING SERVICE LINKING AttMasterId............................... "+ servLinkDtoObj.getPrimaryAttmasterId());
				
				serviceLinkingList.add(servLinkDtoObj);
			}
		}catch(Exception e){
			Utility.LOG(true, true, e, msg);
		}finally{
			try{
				DbConnection.closeResultset(rsLinkingAttDetails);
				DbConnection.closePreparedStatement(psLinkingAttDetails);
				DbConnection.freeConnection(conn);
			}catch (Exception e){
				Utility.LOG(true, true, e, msg);
			}
		}
		return serviceLinkingList;
	}
	//[008] END
	
	public static  String getOrderCreationSource(long orderNo)throws Exception 
	{
		Connection conn=null;
		ResultSet rs=null;
		Statement stmt=null;
		String  sql="SELECT ORDER_CREATION_SOURCE FROM ioe.tpomaster WHERE ORDERNO="+orderNo;
		try
		{
			conn=DbConnection.getConnectionObject();
			stmt = conn.createStatement();
			rs=stmt.executeQuery(sql);
			if(rs.next())
			{
				return rs.getString("ORDER_CREATION_SOURCE");
			}
		}
		catch (Exception ex) {
			throw Utility.onEx_LOG_RET_NEW_EX(ex,"getOrderCreationSource","Utility",null,true,true);
		}finally{
			try {
				DbConnection.closeResultset(rs);
				DbConnection.closeStatement(stmt);
				DbConnection.freeConnection(conn);
				
			} catch (Exception e) {
				throw Utility.onEx_LOG_RET_NEW_EX(e,"getOrderCreationSource","Utility",null,true,true);
			}
		}
		   throw new Exception("Order not present :"+orderNo);
	}
	public static long logErrorToExceptionMaster(ExceptionLogDto errorLogDto) throws Exception{
		
		Connection conn=null;
		CallableStatement cstmt=null;	
		long exceptionId=0l;
		try{
			if(errorLogDto !=null){
				conn=DbConnection.getConnectionObject();
				cstmt=conn.prepareCall(sqlLogException);
				cstmt.setClob(1, errorLogDto.getStackTraceMsg());
				cstmt.setString(2, errorLogDto.getExceptionMessage());
				cstmt.setNull(3, java.sql.Types.BIGINT);
				cstmt.setNull(4, java.sql.Types.BIGINT);
				cstmt.setString(5, errorLogDto.getAssociatedWith());
				cstmt.registerOutParameter(6, java.sql.Types.BIGINT);
				cstmt.execute();
				
				exceptionId=cstmt.getLong(6);
			}
			
		}catch(Exception e){
			Utility.LOG(true, false, e, "Exception Occurred in logErrorToExceptionMaster method of Utility Class.");
		}finally{
			DbConnection.closeCallableStatement(cstmt);
			DbConnection.freeConnection(conn);
		}	
		return exceptionId;
	}
	
	public static class ExceptionLogDto{
		private String exceptionId;
		private String exceptionMessage;
		private java.sql.Clob stackTraceMsg;
		private String associatedWith;
		
		public String getExceptionId() {
			return exceptionId;
		}
		public void setExceptionId(String exceptionId) {
			this.exceptionId = exceptionId;
		}
		public String getExceptionMessage() {
			return exceptionMessage;
		}
		public void setExceptionMessage(String exceptionMessage) {
			this.exceptionMessage = exceptionMessage;
		}
		public java.sql.Clob getStackTraceMsg() {
			return stackTraceMsg;
		}
		public void setStackTraceMsg(java.sql.Clob stackTraceMsg) {
			this.stackTraceMsg = stackTraceMsg;
		}
		public String getAssociatedWith() {
			return associatedWith;
		}
		public void setAssociatedWith(String associatedWith) {
			this.associatedWith = associatedWith;
		}
		
	}
	
	public static int dateCompare(String date1,String date2, String dateformat) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
		Date firstDate=sdf.parse(date1);
		Date secondDate=sdf.parse(date2);
		
		if(firstDate.compareTo(secondDate)==0){
    		return 0;
		}else if(firstDate.compareTo(secondDate)<0){
			return -1;
		}else if(firstDate.compareTo(secondDate)>0){
			return 1;
		}
		return 0;
	}

	public static void throwExceptionIfBatchFailed(int[] res) throws Exception {
	
		for (int i : res) {
			if(i==Statement.EXECUTE_FAILED){
				throw new Exception("Batch result has Statement.EXECUTE_FAILED");
			}
		}
	}
	//[009]
	public static String getDocumentName() throws Exception{
		String docname = null;
		Connection conn=null;
		PreparedStatement pstogetdocumentname=null;
		ResultSet rstogetdocumentname=null;
		try {
			conn=DbConnection.getConnectionObject();
			pstogetdocumentname=conn.prepareStatement("SELECT DOCUMENTNAME FROM ioe.TDOCUMENTMASTER WHERE DOCID = "+AppConstants.RR_AUTO_TRIGGER_DOCUMENT_ID);
			rstogetdocumentname=pstogetdocumentname.executeQuery();
			if(rstogetdocumentname.next())
			{
				docname = rstogetdocumentname.getString("DOCUMENTNAME");
			}
		}
		catch (Exception ex) {
			throw Utility.onEx_LOG_RET_NEW_EX(ex,"getDocumentName","Utility",null,true,true);
		}finally{
			try {
				DbConnection.closeResultset(rstogetdocumentname);
				DbConnection.closePreparedStatement(pstogetdocumentname);
				DbConnection.freeConnection(conn);
				
			} catch (Exception e) {
				throw Utility.onEx_LOG_RET_NEW_EX(e,"getDocumentName","Utility",null,true,true);
			}
		}
		  return docname; 
	}		
	
	
	//NANCY FOR SHAREPOINT LOGS
	
public static void SPT_LOG(boolean logToFile, boolean logToConsole, String msg) {
		
		if(logToFile) AppConstants.SPT_LOGGER.info(msg);
		if(logToConsole) System.out.println(msg);
	}


public static IOESException onEx_LOG_RET_NEW_EX_SHAREPOINT(Throwable ex, String methodName, String className, String msg,boolean logToFile, boolean logToConsole) {
	if(logToConsole) ex.printStackTrace();
	String str=ex.getMessage()
		+ " Exception occured in  :"+msg+" :"
		+Utility.getStackTrace(ex);
	if(logToFile) AppConstants.SPT_LOGGER.error(str);
	IOESException newEx=new IOESException(str) ;
	newEx.initCause(ex);
	return newEx;
}

public static void SPT_LOG(boolean logToFile, boolean logToConsole,Throwable ex, String msg) {
	
	if(logToFile) AppConstants.SPT_LOGGER.info(msg);
	if(logToFile) AppConstants.SPT_LOGGER.info(getStackTrace(ex));
	if(logToConsole) System.out.println(msg);
	if(logToConsole) System.out.println(getStackTrace(ex));
}
	
}
