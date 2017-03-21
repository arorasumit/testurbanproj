package com.ibm.ioes.dao;
//Tag Name Resource Name  Date		CSR No			Description
//[001]	 Kalpana	20-January-2014			To convert hashset of CC into stringbuilder and then to hashset to get distinct value of CC email id's


import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import com.ibm.ioes.escalation.dto.EscalationDto;
import com.ibm.ioes.escalation.dto.EscalationMailDTO;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.SendMail;
import com.ibm.ioes.utilities.Utility;




public class EscalationDao {
	public static String FetchBusinessSegment="select BUSINESS_SEGMENT,ID from ioe.TM_ESCLATION where ROLE_ID=";
	public static String FetchAging="select ID,RM1_AGING,RM2_AGING,RM3_AGING,RM1_MAILID ,RM2_MAILID, RM3_MAILID from ioe.TM_ESCLATION where ID=";
	public static String UpdateMail3Proc="{call IOE.SP_ESCALATION_LEVEL_AGINGMAILUPDATION(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";	
	public static String sqlUpdateEscStat = "{CALL IOE.SP_UPDATE_ESCALATION_MAIL_STATUS(?,?,?,?,?)}";
	public static String sqlGetRmEscStat = "{CALL IOE.SP_GET_RM_ESCALATION_MAILS(?,?,?,?)}";
	private static String strGetEscalationMailDetails ="{call IOE.SP_GET_ESCALATION_MAIL_DETAILS(?,?,?,?,?,?,?)}";
	private static String strGetEscalationMailDetails_AMBIN ="{call IOE.SP_GET_ESCALATION_MAIL_DETAILS_AM_BIN(?,?,?,?,?,?,?)}";
	private static String strGetEscalationMailDetails_AMINCOMPLETE ="{call IOE.SP_GET_ESCALATION_MAIL_DETAILS_AM_INCOMPLETE(?,?,?,?,?,?,?)}";
	private static String strGetEscalationMailDetails_PMBIN ="{call IOE.SP_GET_ESCALATION_MAIL_DETAILS_PM_BIN(?,?,?,?,?,?,?)}";
	private static String strGetEscalationMailDetails_COPC_PP ="{call IOE.SP_GET_ESCALATION_MAIL_DETAILS_COPC_PAR_PUBLSH(?,?,?,?,?,?,?)}";
	private static String strGetEscalationMailDetails_COPCBIN ="{call IOE.SP_GET_ESCALATION_MAIL_DETAILS_COPC_BIN(?,?,?,?,?,?,?)}";
	private static String strGetEscalationMailDetails_SED_PP ="{call IOE.SP_GET_ESCALATION_MAIL_DETAILS_SED_PAR_PUBLSH(?,?,?,?,?,?,?)}";
	private static String strGetEscalationMailDetails_SEDBIN ="{call IOE.SP_GET_ESCALATION_MAIL_DETAILS_SED_BIN(?,?,?,?,?,?,?)}";
	private static String strGetAppConfigValue ="{call IOE.GET_APPCONFIG_VALUE()}";
	private static String strInsertMailDetails ="{call IOE.INSERT_ESCALATION_MAIL_DETAILS(?,?,?,?)}";
	private boolean logToFile=true;
	private boolean logToConsole=false;  
	  


	
   
  

		@SuppressWarnings("unchecked")
		/*
		 * 
		 * fetchBusinessSegment() will fetch BusinessSegments for selected Role
		 * */
		 
		public ArrayList<EscalationDto> fetchBusinessSegment(EscalationDto objDto) throws Exception
		    {
			String methodName="fetchBusinessSegment", className=this.getClass().getName();
			
			      Connection conn = null;
				try {
					conn=DbConnection.getConnectionObject();
				} catch (ClassNotFoundException ex) {
					Utility.LOG(true, false, ex, "Connection Error....");
					ex.printStackTrace();
				}
				catch (Exception e) {
					Utility.LOG(true, false, e, "Connection Error....");
					e.printStackTrace();
				}
		          ArrayList<EscalationDto> list=new ArrayList<EscalationDto>();
		         
		          EscalationDto escDto=null;
		          Statement stmt = null;
		          ResultSet rs=null;
		          try{          
		          String query1=  FetchBusinessSegment + objDto.getROLE_ID();
		          stmt=conn.createStatement();
		           rs= stmt.executeQuery(query1);
		              while(rs.next())
		              {	            	  	
		            	  	escDto=new EscalationDto();
		            	  	escDto.setID(rs.getInt("ID"));
		            	  	escDto.setBUSINESS_SEGMENT(rs.getString("BUSINESS_SEGMENT"));
		            	  	list.add(escDto);
		                   
		              }
		          }
		          catch (SQLException ex) {
		        	  
		        	  	Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, "SQLException...", logToFile, logToConsole);
						ex.printStackTrace();
					}
		          catch (Exception e) {
		        	  	Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, "Exception...", logToFile, logToConsole);
						e.printStackTrace();
					}finally {
						try {
							DbConnection.closeStatement(stmt);
							DbConnection.closeResultset(rs);
							DbConnection.freeConnection(conn);
							
						} catch (Exception e) {
							Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, "Exception in Final Block", logToFile, logToConsole);
							e.printStackTrace();
						}
				}
		              
					return list;
		              
		          }
		       
		/*
		 * fetchLevelDetails() will fetch Agings and mailids of particular role and business segment 
		 */
		public ArrayList<EscalationDto> fetchLevelDetails(EscalationDto objDto) throws Exception
	    {
			String methodName="fetchLevelDetails", className=this.getClass().getName();
			Connection conn = null;
			try {
				conn=DbConnection.getConnectionObject();
			} catch (ClassNotFoundException ex) {
				Utility.LOG(true, false, ex, "Connection Error....");
				ex.printStackTrace();
			}
			catch (Exception e) {
				Utility.LOG(true, false, e, "Connection Error....");
				e.printStackTrace();
			}
	          ArrayList<EscalationDto> levellist=new ArrayList<EscalationDto>();
	        
	          EscalationDto escDto=null;
	          Statement stmt = null;
	          ResultSet rs=null;
	          try{
	          String query1= FetchAging+objDto.getID();
	          stmt=conn.createStatement();
	           rs= stmt.executeQuery(query1);
	              while(rs.next())
	              {	            	  	
	            	  	escDto=new EscalationDto();
	            	  	escDto.setID(rs.getInt("ID")); 
	            	  	if(rs.getString("RM1_AGING")==null){
	            	  		escDto.setRM1_AGING("0");
	            	  	}else{
	            	  		escDto.setRM1_AGING(rs.getString("RM1_AGING"));
	            	  	}
	            	  	if(rs.getString("RM2_AGING")== null)
	            	  	{
	            	  		escDto.setRM2_AGING("0");
	            	  	}else{
	            	  		escDto.setRM2_AGING(rs.getString("RM2_AGING"));
	            	  	}
	            	  	if(rs.getString("RM3_AGING")==null)
	            	  	{
	            	  		escDto.setRM3_AGING("0");
	            	  	}else{
	            	  		escDto.setRM3_AGING(rs.getString("RM3_AGING"));
	            	  	}
	            	   
	            	   	if(rs.getString("RM1_MAILID")==null){
	            	  		escDto.setRM1_MAILID("");
	            	  	}else{
	            	  		escDto.setRM1_MAILID(rs.getString("RM1_MAILID"));
	            	  	}
	            	   	
	            	   	if(rs.getString("RM2_MAILID")==null)
	            	   	{ 
	            	   		escDto.setRM2_MAILID("");
	            	   	}else{
	            	   		escDto.setRM2_MAILID(rs.getString("RM2_MAILID"));
	            	   	}
	            	  	 if(rs.getString("RM3_MAILID")==null)
	            	  	 {
	            	  		escDto.setRM3_MAILID(""); 
	            	  	 }else
	            	  	 {
	            	  	escDto.setRM3_MAILID(rs.getString("RM3_MAILID"));
	            	  	 }
	            	  	levellist.add(escDto);
	            	  	   
	              }
	          }
	          catch (SQLException ex) {
	        	  Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, "SQLException...", logToFile, logToConsole);
					ex.printStackTrace();
				}
	          catch (Exception e) {
	        	  Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, "Exception...", logToFile, logToConsole);
					e.printStackTrace();
				}finally {
					try {
						DbConnection.closeStatement(stmt);
						DbConnection.closeResultset(rs);
						DbConnection.freeConnection(conn);
						
					} catch (Exception e) {
						Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, "Exception in FinalBlock", logToFile, logToConsole);
						e.printStackTrace();
					}
			}
	              
				return levellist;
	         
			}
		
		
		/*
		 * setchangedMail3() will update Agings and mailids of level1
		 */
		public String setchangedMailId3(EscalationDto objDto) throws Exception {
			String methodName="setchangedMailId3", className=this.getClass().getName();
			int msgcode=0;
			int sqlcode=0;
			String errormsg=null;
			Connection conn = null;
			try {
				conn=DbConnection.getConnectionObject();
			} catch (ClassNotFoundException ex) {
				Utility.LOG(true, false, ex, "Connection Error....");
				ex.printStackTrace();
			}
			catch (Exception e) {
				Utility.LOG(true, false, e, "Connection Error....");
				e.printStackTrace();
			}
			//  String mailid= this.fileReading().getProperty("UpdateMail3Proc");
			CallableStatement updateQuery=null;
			try{
	         updateQuery = conn.prepareCall(UpdateMail3Proc);
			updateQuery.setInt(1, objDto.getID());
			updateQuery.setString(2, objDto.getRM1_MAILID());
			updateQuery.setString(3, objDto.getRM1_AGING());
			updateQuery.setString(4, objDto.getRM2_MAILID());
			updateQuery.setString(5, objDto.getRM2_AGING());
			updateQuery.setString(6, objDto.getRM3_MAILID());
			updateQuery.setString(7, objDto.getRM3_AGING());
			updateQuery.setString(8, objDto.getCREATED_BY());
			updateQuery.setInt(9, objDto.getIsUpdatedLevel1());
			updateQuery.setInt(10, objDto.getIsUpdatedLevel2());
			updateQuery.setInt(11, objDto.getIsUpdatedLevel3());
			updateQuery.setInt(12, 0);
			updateQuery.setInt(13, 0);
			updateQuery.setString(14, "");
			
			updateQuery.execute();
			
			msgcode=updateQuery.getInt(12);
			sqlcode=updateQuery.getInt(13);
			errormsg=updateQuery.getString(14);
			}
			catch (SQLException ex) {
				Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, "@errMessage :"+errormsg, logToFile, logToConsole);
				Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, "@sqlcode :"+sqlcode, logToFile, logToConsole);
				 Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, "@msgcode :"+msgcode, logToFile, logToConsole);
				ex.printStackTrace();
			}
			catch (Exception e) {
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, "@errMessage :"+errormsg, logToFile, logToConsole);
				e.printStackTrace();
			} finally {
				try {
					
					DbConnection.closeCallableStatement(updateQuery);
					DbConnection.freeConnection(conn);
					
				} catch (Exception e) {
					Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, "@errMessage :"+errormsg, logToFile, logToConsole);
					e.printStackTrace();
				}
		}
			return errormsg;	
		
} 

		//This method is used to get details from tm_send mail and send escalation mails accordingly
		public void getEscalationMailDetails() throws Exception
		{
			Utility.LOG(true,false,"Entering getEscalationMailDetails");
			String methodName="getEscalationMailDetails", className=this.getClass().getName();
			ResultSet rs1 = null;
	        String toList[]=null;
			String ccList[]=null;
			String bcc[]=null;
			String from=null;
			StringBuilder mailIdSend=new StringBuilder();
			StringBuilder mailIdNotSend=new StringBuilder();
			SendMail sendMail=new SendMail();
			Connection conn=null;
			int retStatus=0;
			CallableStatement updateEscStat=null;
			CallableStatement getEscDts=null;
			int sqlState=0;
			int errorCode=0;
			String errMess=null;
		   
			try {
				//To get mail details
				conn=DbConnection.getConnectionObject();
				getEscDts= conn.prepareCall(sqlGetRmEscStat);
				getEscDts.setNull(1, java.sql.Types.BIGINT);
				getEscDts.setNull(2, java.sql.Types.BIGINT);
				getEscDts.setNull(3, java.sql.Types.BIGINT);
				getEscDts.setNull(4, java.sql.Types.VARCHAR);
				 rs1=getEscDts.executeQuery();
					sqlState=getEscDts.getInt(2);
					errorCode=getEscDts.getInt(3);
					errMess=getEscDts.getString(4);
					Utility.LOG(true,false,"errMess   : "+errMess);
				 int counterSend=1;
				 int counterNotSend=1;
			while(rs1.next()){
				try {
				Utility.LOG(true,false,"MAIL_ID   : "+rs1.getString("MAIL_ID"));
				toList=new String[]{rs1.getString("TO")};
				ccList=new String[]{rs1.getString("CC")};
				Utility.LOG(true,false,"TO   : "+rs1.getString("TO"));
				Utility.LOG(true,false,"CC   : "+rs1.getString("CC"));
				Utility.LOG(true,false,"MAILBODY   : "+rs1.getString("MAILBODY"));
				if(rs1.getString("CC")!=null && !rs1.getString("CC").equalsIgnoreCase("") && rs1.getString("CC").startsWith(",")){
					ccList=new String[]{rs1.getString("CC").substring(1)};
				}else{
					ccList=new String[]{rs1.getString("CC")};
				}
				if(toList!=null && !rs1.getString("TO").equalsIgnoreCase("") 
						&& ccList!=null 
						&& rs1.getString("MAILBODY")!=null && !rs1.getString("MAILBODY").equalsIgnoreCase("")){
						retStatus=sendMail.postMailWithAttachment(toList, ccList, bcc, rs1.getString("SUBJECT"), rs1.getString("MAILBODY"), from,null);
				}else{
					retStatus=0;
				}
				
			    if(retStatus==1)
				{
			    	if(counterSend!=1){
						mailIdSend.append(",");
						mailIdSend.append(rs1.getString("MAIL_ID"));
					}else {
						mailIdSend.append(rs1.getString("MAIL_ID"));
					}
			    	counterSend=counterSend+1;
					String success= " Escalation Mail Sent Successfully : "+ toList;
					com.ibm.ioes.utilities.Utility.LOG(success);
				}
			    else
			    {
			    	if(counterNotSend!=1){
			    		mailIdNotSend.append(",");
			    		mailIdNotSend.append(rs1.getString("MAIL_ID"));
					}else {
						mailIdNotSend.append(rs1.getString("MAIL_ID"));
					}
			    	counterNotSend=counterNotSend+1;
					String failure= " Escalation Mail Sending Failed  : \n EmailId : "+ toList[0]
					                   + "\n	EBODY :"+rs1.getString("MAILBODY");                                                            		
					com.ibm.ioes.utilities.Utility.LOG(failure);
				}
				}catch (Exception e) {
					if(counterNotSend!=1){
			    		mailIdNotSend.append(",");
			    		mailIdNotSend.append(rs1.getString("MAIL_ID"));
					}else {
						mailIdNotSend.append(rs1.getString("MAIL_ID"));
					}
			    	counterNotSend=counterNotSend+1;
					
					Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, "Exception ", logToFile, logToConsole);
					e.printStackTrace();
					
				}
				
			}
			Utility.LOG(true,false,"retStatus   : "+retStatus);
			Utility.LOG(true,false,"mailId   : "+mailIdSend);
			Utility.LOG(true,false,"mailIdNotSend   : "+mailIdNotSend);
			//To update successful mail status with 1
			 if(mailIdSend!=null && !mailIdSend.toString().equalsIgnoreCase(""))
				{
				 
				 updateEscStat= conn.prepareCall(sqlUpdateEscStat);
				 updateEscStat.setString(1, mailIdSend.toString());
				 updateEscStat.setInt(2, 1);
				 updateEscStat.setNull(3, java.sql.Types.BIGINT);
				 updateEscStat.setNull(4, java.sql.Types.BIGINT);
				 updateEscStat.setNull(5, java.sql.Types.VARCHAR);
				 updateEscStat.execute();
					sqlState=updateEscStat.getInt(3);
					errorCode=updateEscStat.getInt(4);
					errMess=updateEscStat.getString(5);
					Utility.LOG(true,false,"errMess   : "+errMess);
				}
			//To update failure mail status with 2
			 if(mailIdNotSend!=null && !mailIdNotSend.toString().equalsIgnoreCase(""))
				{
				 
				 updateEscStat= conn.prepareCall(sqlUpdateEscStat);
				 updateEscStat.setString(1, mailIdNotSend.toString());
				 updateEscStat.setInt(2, 2);
				 updateEscStat.setNull(3, java.sql.Types.BIGINT);
				 updateEscStat.setNull(4, java.sql.Types.BIGINT);
				 updateEscStat.setNull(5, java.sql.Types.VARCHAR);
				 updateEscStat.execute();
					sqlState=updateEscStat.getInt(3);
					errorCode=updateEscStat.getInt(4);
					errMess=updateEscStat.getString(5);
					Utility.LOG(true,false,"errMess   : "+errMess);
					
				}
				
			}
			
			catch (SQLException e) {
				Utility.LOG(true,false,"sqlState   : "+sqlState);
				Utility.LOG(true,false,"errorCode   : "+errorCode);
				Utility.LOG(true,false,"errMess   : "+errMess);
				e.printStackTrace();
				
			}
			catch (Exception e) {
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, "Exception ", logToFile, logToConsole);
				e.printStackTrace();
				
			}
			
			
			finally
			{
				try 
				{	
					DbConnection.closeResultset(rs1);
					DbConnection.freeConnection(conn);
					
				} 
				catch (SQLException e) {
					Utility.LOG(true,false,"sqlState   : "+sqlState);
					Utility.LOG(true,false,"errorCode   : "+errorCode);
					Utility.LOG(true,false,"errMess   : "+errMess);
					
					e.printStackTrace();

				}catch (Exception e) 
				{
					Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, "Exception IN Final Block ", logToFile, logToConsole);
					e.printStackTrace();
				}
			}
			Utility.LOG(true,false,"Exiting getEscalationMailDetails");
			}
		
		//This method is used to get data for escalation mails 
		/*public ArrayList<EscalationMailDTO> makeEscalationMailDetails(Map<String,String> mapParam) throws Exception
		{
			Utility.LOG(true,false,"Entering makeEscalationMailDetails");
			String methodName="insertEscalationMailDetails", className=this.getClass().getName();
			Connection conn=null;
			int sqlState=0;
			int errorCode=0;
			String errMess=null;
			CallableStatement pstmt=null;
			ResultSet rs = null;
			EscalationMailDTO mailDTOObj=null;
			ArrayList<EscalationMailDTO> lst=new ArrayList<EscalationMailDTO>();

			try{
				conn=DbConnection.getConnectionObject();
				pstmt= conn.prepareCall(strGetEscalationMailDetails);
				pstmt.setLong(1, Integer.parseInt(mapParam.get("ESCALATION_PO_DATE_CLOCK_START_HOUR")));
				pstmt.setLong(2, Integer.parseInt(mapParam.get("ESCALATION_PO_DATE_ESCALATION_DAY")));
				pstmt.setLong(3, Integer.parseInt(mapParam.get("ESCALATION_MAX_ESCALATION_LEVEL")));
				pstmt.setLong(4, Integer.parseInt(mapParam.get("ESCALATION_SCHEDULER_MAIL_HOUR")));
				pstmt.setNull(5, java.sql.Types.BIGINT);
				pstmt.setNull(6, java.sql.Types.BIGINT);
				pstmt.setNull(7, java.sql.Types.VARCHAR);
				rs=pstmt.executeQuery();
				sqlState=pstmt.getInt(5);
				errorCode=pstmt.getInt(6);
				errMess=pstmt.getString(7);
				Utility.LOG(true,false,"errMess   : "+errMess);
				while(rs.next()){
					mailDTOObj=new EscalationMailDTO();
					Utility.LOG(true, false, rs.getInt("ORDERNO")+"");
					mailDTOObj.setNumOfRows(rs.getInt("NUM_OF_ROWS"));
					mailDTOObj.setAccountName(rs.getString("CUSTOMER_NAME"));
					mailDTOObj.setAgingFromPORecDate(rs.getString("AGING_FROM__PO_RECEIVED_DATE"));
					mailDTOObj.setAgingInCurrentBin(rs.getString("AGING_IN_CURRENT_BIN"));
					mailDTOObj.setBinOwnnerName(rs.getString("BIN_OWNER_NAME"));
					mailDTOObj.setLevelDetailsCC(rs.getString("leveldetails"));
					mailDTOObj.setLevelDetailsToDB(rs.getString("leveldetailsTo"));
					mailDTOObj.setOrderNo(rs.getInt("ORDERNO"));
					mailDTOObj.setPoAmount(BigDecimal.valueOf((rs.getDouble("VALUE_OF_ORDER"))).toPlainString());
					mailDTOObj.setPoRecieveDate(rs.getString("PORECEIVEDATE"));
					mailDTOObj.setProductName(rs.getString("PRODUCTNAME"));
					mailDTOObj.setRoleId(rs.getInt("OWNERTYPE_ID"));
					mailDTOObj.setRegionId(rs.getInt("REGIONID"));
					mailDTOObj.setChgTypId(rs.getInt("CHANGETYPEID"));
					mailDTOObj.setIsActive(rs.getInt("ISACTIVE"));
					lst.add(mailDTOObj);
				}
				
			} 
			catch (SQLException e) {
				Utility.LOG(true,false,"sqlState   : "+sqlState);
				Utility.LOG(true,false,"errorCode   : "+errorCode);
				Utility.LOG(true,false,"errMess   : "+errMess);
				
				e.printStackTrace();
	
			}catch (Exception e) 
			{
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, "Exception IN Final Block ", logToFile, logToConsole);
				e.printStackTrace();
			}finally {
				try {
					DbConnection.closeResultset(rs);
					DbConnection.closeCallableStatement(pstmt);
					DbConnection.freeConnection(conn);
					
				} catch (Exception e) {
					Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, "@errMessage :"+errMess, logToFile, logToConsole);
					e.printStackTrace();
				}
			}
			Utility.LOG(true,false,"Exiting makeEscalationMailDetails");
			return lst;	

			
		}*/
		
		//to get distinct value of string passed
		public String getDistinctCommaSepVal(String inputStr) throws Exception{
			StringBuilder outputStr=new StringBuilder();
			int count=1;
			if(inputStr!=null && !"".equalsIgnoreCase(inputStr)){
				ArrayList<String> tmpLst=new ArrayList<String>();
				StringTokenizer token=new StringTokenizer(inputStr,",");
				if(token.hasMoreTokens()){
					while(token.hasMoreTokens()){
						tmpLst.add(token.nextElement().toString());
					}
				}else{
					tmpLst.add(inputStr);
				}

				Set<String> tmpSet=new HashSet<String>(tmpLst);
				for (String temp : tmpSet){
					if(count!=1){
		        		outputStr.append(",");
		        		outputStr.append(temp);
		        	}else{
		        		outputStr.append(temp);
		        	}
		        	count=count+1;
		        }
				return outputStr.toString();
			}else{
				return "";
			}
			
		}
		
		//to create consolidated mails in form of xml
		public EscalationMailDTO makeMailForInsert(Map mapParam,int numOfOrders,Set<String> binOwnnerNameSet,
				String escalationLevel,String mailBody,StringBuilder ccAll,String stage,int roleId,String levelDetailsToDB) throws Exception{
			Utility.LOG(true,false,"Entering makeMailForInsert");
			String escLevel=new String();
			String escName=new String();
			String toEmail=new String();
			EscalationMailDTO escalationMailDTOObj=new EscalationMailDTO();
			String distinctVal=new String();
			String ccFinal=new String();
			if(binOwnnerNameSet!=null){
				distinctVal=getDistinctCommaSepValFrmSet(binOwnnerNameSet);
			}else{
				distinctVal="";
			}
			int indexhash;
			int indexAtRate;
			if(escalationLevel!=null && !"".equalsIgnoreCase(escalationLevel)){
				if(escalationLevel.equalsIgnoreCase("L3")){
					escLevel="Level 3";
				}
				if(escalationLevel.equalsIgnoreCase("L2")){
					escLevel="Level 2";
				}
				if(escalationLevel.equalsIgnoreCase("L1")){
					escLevel="Level 1";
				}
			}
			if(roleId==3 || roleId==4){
				if(levelDetailsToDB!=null && !"".equalsIgnoreCase(levelDetailsToDB)
						&& levelDetailsToDB.contains("@")){
					indexAtRate=levelDetailsToDB.indexOf('@');
					indexhash=levelDetailsToDB.indexOf('#');
					escName=levelDetailsToDB.substring((indexhash+1), (indexAtRate));
					toEmail=levelDetailsToDB.substring(indexhash+1);
					
				}else{
					escName="";
					toEmail="";
				}
			}else{
				if(levelDetailsToDB!=null && !"".equalsIgnoreCase(levelDetailsToDB)
						&& levelDetailsToDB.contains("#")){
					indexhash=levelDetailsToDB.indexOf('#');
					escName=levelDetailsToDB.substring(2, (indexhash));
					toEmail=levelDetailsToDB.substring(indexhash+1);
				}else{
					escName="";
					toEmail="";
				}
				
			}
			if(roleId==3){
				stage="COPC Approval";
			}
			if(roleId==4){
				stage="M6 Publish";
			}
			if(roleId==1){
				stage="AM Approval";
			}
			if(roleId==2){
				stage="PM Approval";
			}

			StringBuilder finalMess=new StringBuilder();
			finalMess.append("<html><body>");
			finalMess.append(mapParam.get("ESCALATION_MAIL_BODY_1"));
			finalMess.append(escName);
			if(escName!=null && !"".equalsIgnoreCase(escName)){
				finalMess.append(",");
			}
			finalMess.append("<br><br>");
			finalMess.append(mapParam.get("ESCALATION_MAIL_BODY_2"));
			finalMess.append(numOfOrders);
			finalMess.append(mapParam.get("ESCALATION_MAIL_BODY_3"));
			if(distinctVal!=null && !"".equalsIgnoreCase(distinctVal) && distinctVal.equalsIgnoreCase("M6 Publish")){
				finalMess.append(" for ");
			}else if(distinctVal!=null && !"".equalsIgnoreCase(distinctVal) && !distinctVal.equalsIgnoreCase("M6 Publish")){
				finalMess.append(" against ");
			}
			finalMess.append(distinctVal);
			finalMess.append("<br><br>");
			finalMess.append(mapParam.get("ESCALATION_MAIL_BODY_4"));
			finalMess.append(escLevel);
			finalMess.append(mapParam.get("ESCALATION_MAIL_BODY_5"));
			finalMess.append("<br><br>");
			StringBuilder finalMail=new StringBuilder();
			finalMail.append(finalMess);
			finalMail.append(mailBody);
			finalMail.append("<br><br><br><br>");
			finalMail.append("<br><br><br><br>");
			finalMail.append(mapParam.get("ESCALATION_MAIL_ENDING_MESSAGE"));
			finalMail.append("</html></body>");
			if(ccAll!=null){
				distinctVal=getDistinctCommaSepVal(ccAll.toString());
			}else{
				distinctVal="";
			}
			if(toEmail!=null && !"".equalsIgnoreCase(toEmail)){
				ccFinal=getCCMailIdDistinct(toEmail,distinctVal);
			}else{
				ccFinal=distinctVal;
			}
			StringBuilder finalSub=new StringBuilder();
			finalSub.append(escLevel);
			finalSub.append(mapParam.get("ESCALATION_MAIL_SUBJECT_MESSAGE1"));
			finalSub.append(numOfOrders);
			finalSub.append(mapParam.get("ESCALATION_MAIL_SUBJECT_MESSAGE2"));
			finalSub.append(stage);
			finalSub.append(")");
			Utility.LOG(true, false, finalSub.toString());
			Utility.LOG(true, false, toEmail);
			Utility.LOG(true, false, ccAll.toString());
			Utility.LOG(true, false, ccFinal.toString());
			Utility.LOG(true, false, finalMail.toString());
			escalationMailDTOObj.setLevelDetailsTo(toEmail);
			escalationMailDTOObj.setLevelDetailsCC(ccFinal);
			escalationMailDTOObj.setSubject(finalSub.toString());
			escalationMailDTOObj.setFinalMail(finalMail.toString());
			Utility.LOG(true,false,"Exiting makeMailForInsert");
			return escalationMailDTOObj;

		}
		
		//to create header for escalation mails
		public StringBuilder createHeader(){
			StringBuilder mailBody=new StringBuilder();
			mailBody.append("<TABLE BORDER=2 cellspacing=0 cellpadding=1 style=\"border-collapse: collapse; border-spacing:1; vertical-align:middle;\">");
		    mailBody.append("<TR style=\"vertical-align:bottom;\">");
		    mailBody.append("<TH style=\"vertical-align:bottom;\"><h5>CUSTOMER NAME</h5></TH>");
		    mailBody.append("<TH style=\"vertical-align:bottom;\"><h5>ORDER NUMBER</h5></TH>");
		    mailBody.append("<TH style=\"vertical-align:bottom;\"><h5>BIN OWNER NAME</h5></TH>");
		    mailBody.append("<TH style=\"vertical-align:bottom;\"><h5>PO RECEIVED DATE</h5></TH>");
		    mailBody.append("<TH style=\"vertical-align:bottom;\"><h5>PRODUCT</h5></TH>");
		    mailBody.append("<TH style=\"vertical-align:bottom;\"><h5>VALUE OF ORDER</h5></TH>");
		    mailBody.append("<TH style=\"vertical-align:bottom;\"><h5>AGING IN CURRENT BIN (DAYS/HOUR)</h5></TH>");
		    mailBody.append("<TH style=\"vertical-align:bottom;\"><h5>AGING FROM PO RECEIVED DATE (DAYS/HOUR)</h5></TH>");
		    mailBody.append("</TR>");
		    return mailBody;

		}

		//to get required values from tm_appconfig table
		public Map<String,String> getAppConfigVal()throws Exception {
			Utility.LOG(true,false,"Entering getAppConfigVal");
			Connection connection =null;
			PreparedStatement getList =null;
			Map<String,String> tmpMap=new HashMap<String,String>();
			ResultSet rsAppConfig = null;
			try{
				connection=DbConnection.getConnectionObject();
				getList= connection.prepareCall(strGetAppConfigValue);
				rsAppConfig = getList.executeQuery();
				while(rsAppConfig.next()){
					Utility.LOG(true,false,rsAppConfig.getString("KEYNAME"));
					Utility.LOG(true,false,rsAppConfig.getString("KEYVALUE"));
					tmpMap.put(rsAppConfig.getString("KEYNAME"), rsAppConfig.getString("KEYVALUE"));
				}
			}catch(Exception ex ){
				ex.printStackTrace();	
			}finally{
				try{
					DbConnection.closeResultset(rsAppConfig);
					DbConnection.closePreparedStatement(getList);
					DbConnection.freeConnection(connection);
				}catch (SQLException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Utility.LOG(true,false,"Exiting getAppConfigVal");
			return tmpMap;
		}
		
		//to insert escalation mails in tm_senmail table
		public void insertMailDetails(ArrayList<EscalationMailDTO> tmpLstParam )throws Exception {
			Utility.LOG(true,false,"Entering insertMailDetails");
			Connection connection =null;
			CallableStatement insertMail =null;
			try{
				int lstSize=tmpLstParam.size();
				EscalationMailDTO tmpObj=new EscalationMailDTO();
				java.sql.Clob myMailClob=null;
				java.sql.Clob myCCClob=null;
				connection=DbConnection.getConnectionObject();
				insertMail= connection.prepareCall(strInsertMailDetails);
				for(int i=0;i<lstSize;i++){
					tmpObj=(EscalationMailDTO)tmpLstParam.get(i);
					Utility.LOG(true, false, tmpObj.getLevelDetailsTo()+"insertMailDetailsTo");
					Utility.LOG(true, false, tmpObj.getLevelDetailsCC()+"insertMailDetailsCC");
					Utility.LOG(true, false, tmpObj.getSubject()+"insertMailDetailsSub");
					Utility.LOG(true, false, tmpObj.getFinalMail()+"insertMailDetailsFinal");
					if(tmpObj.getFinalMail()!=null && !"".equalsIgnoreCase(tmpObj.getFinalMail())){
						myMailClob = com.ibm.db2.jcc.t2zos.DB2LobFactory.createClob(tmpObj.getFinalMail());
					}else{
						tmpObj.setFinalMail("");
						myMailClob = com.ibm.db2.jcc.t2zos.DB2LobFactory.createClob(tmpObj.getFinalMail());
					}
					if(tmpObj.getLevelDetailsCC()!=null && !"".equalsIgnoreCase(tmpObj.getLevelDetailsCC())){
						myCCClob = com.ibm.db2.jcc.t2zos.DB2LobFactory.createClob(tmpObj.getLevelDetailsCC());
					}else{
						tmpObj.setLevelDetailsCC("");
						myCCClob = com.ibm.db2.jcc.t2zos.DB2LobFactory.createClob(tmpObj.getLevelDetailsCC());
					}
					
						
					
					insertMail.setString(1, tmpObj.getLevelDetailsTo());
					insertMail.setClob(2, myCCClob);
					insertMail.setString(3, tmpObj.getSubject());
					insertMail.setClob(4, myMailClob);
					insertMail.executeUpdate();
	
				}
				
				
			}catch (SQLException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(Exception ex ){
				ex.printStackTrace();	
			}finally{
				try{
					DbConnection.closeCallableStatement(insertMail);
					DbConnection.freeConnection(connection);
				}catch (SQLException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Utility.LOG(true,false,"Exiting getAppConfigVal");
		}
		//This method is used to get data for escalation mails 
		public ArrayList<EscalationMailDTO> makeEscalationConsolidatedMail(ArrayList<EscalationMailDTO> maillst,Map<String,String> mapParam) throws Exception
		{
			String methodName="makeEscalationConsolidatedMail", className=this.getClass().getName();
			Utility.LOG(true,false,"Entering makeEscalationConsolidatedMail");
			Connection conn=null;
			EscalationMailDTO mailDTOObj=null;
			EscalationMailDTO mailDTOObjOld=null;
			EscalationMailDTO mailDTOObjNew=null;
			int counter=1;
			StringBuilder mailBody=new StringBuilder();
			EscalationMailDTO escMailDTOObj=new EscalationMailDTO();
			ArrayList<EscalationMailDTO> finalMaillst=new ArrayList<EscalationMailDTO>();

			try{
				mailBody =createHeader();
				//StringBuilder binOwnnerNameAll=new StringBuilder();
				//StringBuilder productNameAll=new StringBuilder();
				StringBuilder ccAll=new StringBuilder();
				Set<String> binOwnnerNameSet=new HashSet<String>(); 
				Set<String> productNameSet=new HashSet<String>(); 
				//Set<String> CC_ALLSet=new HashSet<String>(); 
				String stage=new String();
				String distinctVal=new String();
				int numOfOrders=0;
				int indexhash=0;
			if(maillst.size()!=0){
				int size=maillst.size();
				EscalationMailDTO tmpObj=new EscalationMailDTO();
				for(int i=0;i<size;i++){
					mailDTOObj=(EscalationMailDTO)maillst.get(i);
					Utility.LOG(true, false, mailDTOObj.getOrderNo()+"makeEscalationConsolidatedMail");
					if(mailDTOObj.getLevelDetailsToDB()!=null && !"".equalsIgnoreCase(mailDTOObj.getLevelDetailsToDB())){
						if(mailDTOObj.getLevelDetailsToDB().contains("#")){
							indexhash=mailDTOObj.getLevelDetailsToDB().indexOf('#');
							mailDTOObj.setLevelDetailsTo(mailDTOObj.getLevelDetailsToDB().substring(0,(indexhash)));
						}else{
							mailDTOObj.setLevelDetailsTo(mailDTOObj.getLevelDetailsToDB().substring(0,2));
						}
						mailDTOObj.setEscalationLevel(mailDTOObj.getLevelDetailsToDB().substring(0,2));
					}
					if(counter==1){
						if(mailDTOObj.getBinOwnnerName()!=null && !"".equalsIgnoreCase(mailDTOObj.getBinOwnnerName())){
							binOwnnerNameSet.add(mailDTOObj.getBinOwnnerName());
						}
						if(mailDTOObj.getProductName()!=null && !"".equalsIgnoreCase(mailDTOObj.getProductName())){
							productNameSet.add(mailDTOObj.getProductName());
						}
						if(mailDTOObj.getLevelDetailsCC()!=null && !"".equalsIgnoreCase(mailDTOObj.getLevelDetailsCC())){
							ccAll.append(mailDTOObj.getLevelDetailsCC());
						}
						mailDTOObjOld=mailDTOObj;
					}else{
						mailDTOObjNew=mailDTOObj;
						if(mailDTOObjNew.getRoleId()==mailDTOObjOld.getRoleId() && mailDTOObjNew.getLevelDetailsToDB().equalsIgnoreCase(
								mailDTOObjOld.getLevelDetailsToDB())){
							if(mailDTOObjNew.getBinOwnnerName()!=null && !"".equalsIgnoreCase(mailDTOObjNew.getBinOwnnerName())){
								binOwnnerNameSet.add(mailDTOObjNew.getBinOwnnerName());
							}
							if(mailDTOObjNew.getLevelDetailsCC()!=null && !"".equalsIgnoreCase(mailDTOObjNew.getLevelDetailsCC())){
								//Start [001]
								if(mailDTOObjOld.getLevelDetailsCC()!=null && !"".equalsIgnoreCase(mailDTOObjOld.getLevelDetailsCC())){
									if(!mailDTOObjOld.getLevelDetailsCC().equalsIgnoreCase(mailDTOObjNew.getLevelDetailsCC())){
										if(ccAll!=null && !ccAll.toString().equalsIgnoreCase("")){
											ccAll.append(",");
											ccAll.append(mailDTOObjNew.getLevelDetailsCC());
										}else{
											ccAll.append(mailDTOObjNew.getLevelDetailsCC());
										}
									}
								}else{
									if(ccAll!=null && !ccAll.toString().equalsIgnoreCase("")){
										ccAll.append(",");
										ccAll.append(mailDTOObjNew.getLevelDetailsCC());
									}else{
										ccAll.append(mailDTOObjNew.getLevelDetailsCC());
									}
								}
								//End [001]
							}
							if(mailDTOObjNew.getOrderNo()==mailDTOObjOld.getOrderNo()){
								if(mailDTOObjNew.getProductName()!=null && !"".equalsIgnoreCase(mailDTOObjNew.getProductName())){
									productNameSet.add(mailDTOObjNew.getProductName());
								}
							}else{
								if(productNameSet!=null){
									distinctVal=getDistinctCommaSepValFrmSet(productNameSet);
								}else{
									distinctVal="";
								}
								
								
								  mailBody.append("<TR>");
								  mailBody.append("<TD>");
								  mailBody.append(mailDTOObjOld.getAccountName());
								  mailBody.append("</TD>");
								  mailBody.append("<TD>");
								  mailBody.append(mailDTOObjOld.getOrderNo());
								  mailBody.append("</TD>");
								  mailBody.append("<TD>");
								  mailBody.append(mailDTOObjOld.getBinOwnnerName());
								  mailBody.append("</TD>");
								  mailBody.append("<TD>");
								  mailBody.append(mailDTOObjOld.getPoRecieveDate());
								  mailBody.append("</TD>");
								  mailBody.append("<TD>");
								  mailBody.append(distinctVal);
								  mailBody.append("</TD>");
								  mailBody.append("<TD>");
								  mailBody.append(mailDTOObjOld.getPoAmount());
								  mailBody.append("</TD>");
								  mailBody.append("<TD>");
								  mailBody.append(mailDTOObjOld.getAgingInCurrentBin());
								  mailBody.append("</TD>");
								  mailBody.append("<TD>");
								  mailBody.append(mailDTOObjOld.getAgingFromPORecDate());
								  mailBody.append("</TD>");
								  mailBody.append("</TR>");  
								    

								  productNameSet=new HashSet<String>(); 
								if(mailDTOObjNew.getProductName()!=null && !"".equalsIgnoreCase(mailDTOObjNew.getProductName())){
									productNameSet.add(mailDTOObjNew.getProductName());
								}
								numOfOrders=numOfOrders+1;
							}
							if(counter==size && mailDTOObjNew.getOrderNo()==mailDTOObjOld.getOrderNo() ){
								if(productNameSet!=null){
									distinctVal=getDistinctCommaSepValFrmSet(productNameSet);
								}else{
									distinctVal="";
								}
								
								  mailBody.append("<TR>");
								  mailBody.append("<TD>");
								  mailBody.append(mailDTOObjNew.getAccountName());
								  mailBody.append("</TD>");
								  mailBody.append("<TD>");
								  mailBody.append(mailDTOObjNew.getOrderNo());
								  mailBody.append("</TD>");
								  mailBody.append("<TD>");
								  mailBody.append(mailDTOObjNew.getBinOwnnerName());
								  mailBody.append("</TD>");
								  mailBody.append("<TD>");
								  mailBody.append(mailDTOObjNew.getPoRecieveDate());
								  mailBody.append("</TD>");
								  mailBody.append("<TD>");
								  mailBody.append(distinctVal);
								  mailBody.append("</TD>");
								  mailBody.append("<TD>");
								  mailBody.append(mailDTOObjNew.getPoAmount());
								  mailBody.append("</TD>");
								  mailBody.append("<TD>");
								  mailBody.append(mailDTOObjNew.getAgingInCurrentBin());
								  mailBody.append("</TD>");
								  mailBody.append("<TD>");
								  mailBody.append(mailDTOObjNew.getAgingFromPORecDate());
								  mailBody.append("</TD>");
								  mailBody.append("</TR>");  
								  mailBody.append("</TABLE>");  
								  numOfOrders=numOfOrders+1;
								  if((mailDTOObjNew.getRoleId()==1 || mailDTOObjNew.getRoleId()==2) && mailDTOObjNew.getEscalationLevel().equalsIgnoreCase("L3")){
									  Utility.LOG(true, false, counter+"Not required");
									}else{
									
								//insert here
								  escMailDTOObj=makeMailForInsert(mapParam,numOfOrders,binOwnnerNameSet,
										mailDTOObjOld.getEscalationLevel(),mailBody.toString(),ccAll,stage,
										mailDTOObjOld.getRoleId(),mailDTOObjOld.getLevelDetailsToDB());
								  finalMaillst.add(escMailDTOObj);
									}
								//insert complete
							}
							if(counter==size && mailDTOObjNew.getOrderNo()!=mailDTOObjOld.getOrderNo() ){
								numOfOrders=numOfOrders+1;
								mailBody.append("<TR>");
								  mailBody.append("<TD>");
								  mailBody.append(mailDTOObjNew.getAccountName());
								  mailBody.append("</TD>");
								  mailBody.append("<TD>");
								  mailBody.append(mailDTOObjNew.getOrderNo());
								  mailBody.append("</TD>");
								  mailBody.append("<TD>");
								  mailBody.append(mailDTOObjNew.getBinOwnnerName());
								  mailBody.append("</TD>");
								  mailBody.append("<TD>");
								  mailBody.append(mailDTOObjNew.getPoRecieveDate());
								  mailBody.append("</TD>");
								  mailBody.append("<TD>");
								  mailBody.append(mailDTOObjNew.getProductName());
								  mailBody.append("</TD>");
								  mailBody.append("<TD>");
								  mailBody.append(mailDTOObjNew.getPoAmount());
								  mailBody.append("</TD>");
								  mailBody.append("<TD>");
								  mailBody.append(mailDTOObjNew.getAgingInCurrentBin());
								  mailBody.append("</TD>");
								  mailBody.append("<TD>");
								  mailBody.append(mailDTOObjNew.getAgingFromPORecDate());
								  mailBody.append("</TD>");
								  mailBody.append("</TR>");  

								//insert here
								mailBody.append("</TABLE>");
								 if((mailDTOObjNew.getRoleId()==1 || mailDTOObjNew.getRoleId()==2) && mailDTOObjNew.getEscalationLevel().equalsIgnoreCase("L3")){
									 Utility.LOG(true, false, counter+"Not required");
									}else{
								escMailDTOObj=makeMailForInsert(mapParam,numOfOrders,binOwnnerNameSet,
										mailDTOObjOld.getEscalationLevel(),mailBody.toString(),ccAll,stage,
										mailDTOObjOld.getRoleId(),mailDTOObjOld.getLevelDetailsToDB());
								finalMaillst.add(escMailDTOObj);
									}
								//insert complete
							}
						}else{
							if(productNameSet!=null){
								distinctVal=getDistinctCommaSepValFrmSet(productNameSet);
							}else{
								distinctVal="";
							}
							  mailBody.append("<TR>");
							  mailBody.append("<TD>");
							  mailBody.append(mailDTOObjOld.getAccountName());
							  mailBody.append("</TD>");
							  mailBody.append("<TD>");
							  mailBody.append(mailDTOObjOld.getOrderNo());
							  mailBody.append("</TD>");
							  mailBody.append("<TD>");
							  mailBody.append(mailDTOObjOld.getBinOwnnerName());
							  mailBody.append("</TD>");
							  mailBody.append("<TD>");
							  mailBody.append(mailDTOObjOld.getPoRecieveDate());
							  mailBody.append("</TD>");
							  mailBody.append("<TD>");
							  mailBody.append(distinctVal);
							  mailBody.append("</TD>");
							  mailBody.append("<TD>");
							  mailBody.append(mailDTOObjOld.getPoAmount());
							  mailBody.append("</TD>");
							  mailBody.append("<TD>");
							  mailBody.append(mailDTOObjOld.getAgingInCurrentBin());
							  mailBody.append("</TD>");
							  mailBody.append("<TD>");
							  mailBody.append(mailDTOObjOld.getAgingFromPORecDate());
							  mailBody.append("</TD>");
							  mailBody.append("</TR>");  
							  numOfOrders=numOfOrders+1;
							  mailBody.append("</TABLE>");
							//insert here
							  if((mailDTOObjOld.getRoleId()==1 || mailDTOObjOld.getRoleId()==2) && mailDTOObjOld.getEscalationLevel().equalsIgnoreCase("L3")){
								  Utility.LOG(true, false, counter+"Not required");
								}else{
							  escMailDTOObj=makeMailForInsert(mapParam,numOfOrders,binOwnnerNameSet,
									mailDTOObjOld.getEscalationLevel(),mailBody.toString(),ccAll,stage,
									mailDTOObjOld.getRoleId(),mailDTOObjOld.getLevelDetailsToDB());
							  finalMaillst.add(escMailDTOObj);
								}
							//insert complete
							mailBody=new StringBuilder();
							mailBody =createHeader();
							numOfOrders=0;
							
							binOwnnerNameSet=new HashSet<String>(); 
							productNameSet=new HashSet<String>(); 
							ccAll=new StringBuilder();
							if(mailDTOObjNew.getBinOwnnerName()!=null && !"".equalsIgnoreCase(mailDTOObjNew.getBinOwnnerName())){
								binOwnnerNameSet.add(mailDTOObjNew.getBinOwnnerName());
							}
							
							if(mailDTOObjNew.getProductName()!=null && !"".equalsIgnoreCase(mailDTOObjNew.getProductName())){
								productNameSet.add(mailDTOObjNew.getProductName());
							}
							
							if(mailDTOObjNew.getLevelDetailsCC()!=null && !"".equalsIgnoreCase(mailDTOObjNew.getLevelDetailsCC())){
								ccAll.append(mailDTOObjNew.getLevelDetailsCC());
							}
							
	                       
						}
						mailDTOObjOld=mailDTOObjNew;
					}
					counter=counter+1;
					
					}
				}
				
			 
			}
			catch (Exception e) 
			{
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, "Exception IN Final Block ", logToFile, logToConsole);
				e.printStackTrace();
			}
			Utility.LOG(true,false,"Exiting makeEscalationConsolidatedMail");
			return finalMaillst;	

			
		}

		//to remove values from cc list which is in to list
		public String getCCMailIdDistinct(String toStr,String ccStr) throws Exception{
			//Utility.LOG(true, false, "@"+inputStr+"@");
			StringBuilder outputStr=new StringBuilder();
			int count=1;
			String[] split=null;
			ArrayList<String> ccLst=new ArrayList<String>();
			ArrayList<String> toLst=new ArrayList<String>();
			if(toStr!=null && !"".equalsIgnoreCase(toStr)){
				if(toStr.contains(",")){
					split = toStr.split(",");
				}else{
					split=new String[1];
					split[0]=toStr;
				}
				
				for (String item : split) { 
					if(item!=null && !"".equalsIgnoreCase(item)){
						toLst.add(item);
					}
				} 
				
				if(ccStr!=null && !"".equalsIgnoreCase(ccStr)){
					if(ccStr.contains(",")){
						split = ccStr.split(",");
					}else{
						split=new String[1];
						split[0]=ccStr;
					}
					
					for (String item : split) { 
						if(item!=null && !"".equalsIgnoreCase(item)){
							ccLst.add(item);
							
						}
					} 
				}
				if(toLst.size()!=0 && ccLst.size()!=0){
					ccLst.removeAll(toLst);
				}
				
				for (String temp : ccLst){
					Utility.LOG(true, false, "@"+temp+"@");
		        	if(count!=1){
		        		outputStr.append(",");
		        		outputStr.append(temp);
		        	}else{
		        		outputStr.append(temp);
		        	}
		        	count=count+1;
		        }
				
				return outputStr.toString();
			
				
			}else{
				return ccStr.toString();
			}
			
		}
		
		//to get distinct value of string passed
		public String getDistinctCommaSepValMail(String inputStr) throws Exception{
			StringBuilder outputStr=new StringBuilder();
			int count=1;
			String[] split=null;
			if(inputStr!=null && !"".equalsIgnoreCase(inputStr)){
				if(inputStr.contains(",")){
					split = inputStr.split(",");
				}else{
					split=new String[1];
					split[0]=inputStr;
				}
				ArrayList<String> tmpLst=new ArrayList<String>();
				for (String item : split) { 
					if(item!=null && !"".equalsIgnoreCase(item) && !item.contains(" ") && item.contains("@") && item.indexOf("@")!=0){
						tmpLst.add(item);
					}
				} 
				Set<String> tmpSet=new HashSet<String>(tmpLst);
				for (String temp : tmpSet){
					if(count!=1){
		        		outputStr.append(",");
		        		outputStr.append(temp);
		        	}else{
		        		outputStr.append(temp);
		        	}
		        	count=count+1;
		        }
				return outputStr.toString();
			}else{
				return "";
			}
			
		}



		//This method is used to get data for escalation mails AM BIN
		public ArrayList<EscalationMailDTO> makeEscalationMailDetailsAMBIN(Map<String,String> mapParam,ArrayList<EscalationMailDTO> lstParam,String userStat) throws Exception
		{
			Utility.LOG(true,false,"Entering makeEscalationMailDetailsAMBIN");
			String methodName="makeEscalationMailDetailsAMBIN", className=this.getClass().getName();
			Connection conn=null;
			int sqlState=0;
			int errorCode=0;
			String errMess=null;
			CallableStatement pstmt=null;
			ResultSet rs = null;
			EscalationMailDTO mailDTOObj=null;
			try{
				conn=DbConnection.getConnectionObject();
				pstmt= conn.prepareCall(strGetEscalationMailDetails_AMBIN);
				pstmt.setLong(1, Integer.parseInt(mapParam.get("ESCALATION_PO_DATE_CLOCK_START_HOUR")));
				pstmt.setLong(2, Integer.parseInt(mapParam.get("ESCALATION_PO_DATE_ESCALATION_DAY")));
				pstmt.setLong(3, Integer.parseInt(mapParam.get("ESCALATION_MAX_ESCALATION_LEVEL")));
				pstmt.setLong(4, Integer.parseInt(mapParam.get("ESCALATION_SCHEDULER_MAIL_HOUR")));
				pstmt.setNull(5, java.sql.Types.BIGINT);
				pstmt.setNull(6, java.sql.Types.BIGINT);
				pstmt.setNull(7, java.sql.Types.VARCHAR);
				rs=pstmt.executeQuery();
				sqlState=pstmt.getInt(5);
				errorCode=pstmt.getInt(6);
				errMess=pstmt.getString(7);
				Utility.LOG(true,false,"errMess   : "+errMess);
				while(rs.next()){
					mailDTOObj=new EscalationMailDTO();
					Utility.LOG(true, false, rs.getInt("ORDERNO")+"");
					mailDTOObj.setAccountName(rs.getString("CUSTOMER_NAME"));
					mailDTOObj.setAgingFromPORecDate(rs.getString("AGING_FROM__PO_RECEIVED_DATE"));
					mailDTOObj.setAgingInCurrentBin(rs.getString("AGING_IN_CURRENT_BIN"));
					mailDTOObj.setBinOwnnerName(rs.getString("BIN_OWNER_NAME"));
					mailDTOObj.setLevelDetailsCC(rs.getString("leveldetails"));
					mailDTOObj.setLevelDetailsToDB(rs.getString("leveldetailsTo"));
					mailDTOObj.setOrderNo(rs.getInt("ORDERNO"));
					mailDTOObj.setPoAmount(BigDecimal.valueOf((rs.getDouble("VALUE_OF_ORDER"))).toPlainString());
					mailDTOObj.setPoRecieveDate(rs.getString("PORECEIVEDATE"));
					mailDTOObj.setProductName(rs.getString("PRODUCTNAME"));
					mailDTOObj.setRoleId(rs.getInt("OWNERTYPE_ID"));
					mailDTOObj.setRegionId(rs.getInt("REGIONID"));
					mailDTOObj.setChgTypId(rs.getInt("CHANGETYPEID"));
					mailDTOObj.setIsActive(rs.getInt("ISACTIVE"));
					if(userStat!=null && userStat.equalsIgnoreCase("1")){
						if(mailDTOObj.getIsActive()==1){
							lstParam.add(mailDTOObj);
						}
					}else{
						lstParam.add(mailDTOObj);
					}
					
					
				}
			} 
			catch (SQLException e) {
				Utility.LOG(true,false,"sqlState   : "+sqlState);
				Utility.LOG(true,false,"errorCode   : "+errorCode);
				Utility.LOG(true,false,"errMess   : "+errMess);
				
				e.printStackTrace();
	
			}catch (Exception e) 
			{
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, "Exception IN Final Block ", logToFile, logToConsole);
				e.printStackTrace();
			}finally {
				try {
					DbConnection.closeResultset(rs);
					DbConnection.closeCallableStatement(pstmt);
					DbConnection.freeConnection(conn);
					
				} catch (Exception e) {
					Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, "@errMessage :"+errMess, logToFile, logToConsole);
					e.printStackTrace();
				}
			}
			Utility.LOG(true,false,"Exiting makeEscalationMailDetailsAMBIN");
			return lstParam;	

			
		}
		
		//This method is used to get data for escalation mails AM Incomplete
		public ArrayList<EscalationMailDTO> makeEscalationMailDetailsAMINC(Map<String,String> mapParam,ArrayList<EscalationMailDTO> lstParam,String userStat) throws Exception
		{
			Utility.LOG(true,false,"Entering makeEscalationMailDetailsAMINC");
			String methodName="makeEscalationMailDetailsAMINC", className=this.getClass().getName();
			Connection conn=null;
			int sqlState=0;
			int errorCode=0;
			String errMess=null;
			CallableStatement pstmt=null;
			ResultSet rs = null;
			EscalationMailDTO mailDTOObj=null;
			try{
				conn=DbConnection.getConnectionObject();
				pstmt= conn.prepareCall(strGetEscalationMailDetails_AMINCOMPLETE);
				pstmt.setLong(1, Integer.parseInt(mapParam.get("ESCALATION_PO_DATE_CLOCK_START_HOUR")));
				pstmt.setLong(2, Integer.parseInt(mapParam.get("ESCALATION_PO_DATE_ESCALATION_DAY")));
				pstmt.setLong(3, Integer.parseInt(mapParam.get("ESCALATION_MAX_ESCALATION_LEVEL")));
				pstmt.setLong(4, Integer.parseInt(mapParam.get("ESCALATION_SCHEDULER_MAIL_HOUR")));
				pstmt.setNull(5, java.sql.Types.BIGINT);
				pstmt.setNull(6, java.sql.Types.BIGINT);
				pstmt.setNull(7, java.sql.Types.VARCHAR);
				rs=pstmt.executeQuery();
				sqlState=pstmt.getInt(5);
				errorCode=pstmt.getInt(6);
				errMess=pstmt.getString(7);
				Utility.LOG(true,false,"errMess   : "+errMess);
				while(rs.next()){
					mailDTOObj=new EscalationMailDTO();
					Utility.LOG(true, false, rs.getInt("ORDERNO")+"");
					mailDTOObj.setAccountName(rs.getString("CUSTOMER_NAME"));
					mailDTOObj.setAgingFromPORecDate(rs.getString("AGING_FROM__PO_RECEIVED_DATE"));
					mailDTOObj.setAgingInCurrentBin(rs.getString("AGING_IN_CURRENT_BIN"));
					mailDTOObj.setBinOwnnerName(rs.getString("BIN_OWNER_NAME"));
					mailDTOObj.setLevelDetailsCC(rs.getString("leveldetails"));
					mailDTOObj.setLevelDetailsToDB(rs.getString("leveldetailsTo"));
					mailDTOObj.setOrderNo(rs.getInt("ORDERNO"));
					mailDTOObj.setPoAmount(BigDecimal.valueOf((rs.getDouble("VALUE_OF_ORDER"))).toPlainString());
					mailDTOObj.setPoRecieveDate(rs.getString("PORECEIVEDATE"));
					mailDTOObj.setProductName(rs.getString("PRODUCTNAME"));
					mailDTOObj.setRoleId(rs.getInt("OWNERTYPE_ID"));
					mailDTOObj.setRegionId(rs.getInt("REGIONID"));
					mailDTOObj.setChgTypId(rs.getInt("CHANGETYPEID"));
					mailDTOObj.setIsActive(rs.getInt("ISACTIVE"));
					if(userStat!=null && userStat.equalsIgnoreCase("1") ){
						if(mailDTOObj.getIsActive()==1){
							lstParam.add(mailDTOObj);
						}
					}else{
						lstParam.add(mailDTOObj);
					}
				}
			} 
			catch (SQLException e) {
				Utility.LOG(true,false,"sqlState   : "+sqlState);
				Utility.LOG(true,false,"errorCode   : "+errorCode);
				Utility.LOG(true,false,"errMess   : "+errMess);
				
				e.printStackTrace();
	
			}catch (Exception e) 
			{
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, "Exception IN Final Block ", logToFile, logToConsole);
				e.printStackTrace();
			}finally {
				try {
					DbConnection.closeResultset(rs);
					DbConnection.closeCallableStatement(pstmt);
					DbConnection.freeConnection(conn);
					
				} catch (Exception e) {
					Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, "@errMessage :"+errMess, logToFile, logToConsole);
					e.printStackTrace();
				}
			}
			Utility.LOG(true,false,"Exiting makeEscalationMailDetailsAMINC");
			return lstParam;	

			
		}
		
		//This method is used to get data for escalation mails PM BIN
		public ArrayList<EscalationMailDTO> makeEscalationMailDetailsPMBIN(Map<String,String> mapParam,ArrayList<EscalationMailDTO> lstParam,String userStat) throws Exception
		{
			Utility.LOG(true,false,"Entering makeEscalationMailDetailsPMBIN");
			String methodName="makeEscalationMailDetailsPMBIN", className=this.getClass().getName();
			Connection conn=null;
			int sqlState=0;
			int errorCode=0;
			String errMess=null;
			CallableStatement pstmt=null;
			ResultSet rs = null;
			EscalationMailDTO mailDTOObj=null;
			
			try{
				conn=DbConnection.getConnectionObject();
				pstmt= conn.prepareCall(strGetEscalationMailDetails_PMBIN);
				pstmt.setLong(1, Integer.parseInt(mapParam.get("ESCALATION_PO_DATE_CLOCK_START_HOUR")));
				pstmt.setLong(2, Integer.parseInt(mapParam.get("ESCALATION_PO_DATE_ESCALATION_DAY")));
				pstmt.setLong(3, Integer.parseInt(mapParam.get("ESCALATION_MAX_ESCALATION_LEVEL")));
				pstmt.setLong(4, Integer.parseInt(mapParam.get("ESCALATION_SCHEDULER_MAIL_HOUR")));
				pstmt.setNull(5, java.sql.Types.BIGINT);
				pstmt.setNull(6, java.sql.Types.BIGINT);
				pstmt.setNull(7, java.sql.Types.VARCHAR);
				rs=pstmt.executeQuery();
				sqlState=pstmt.getInt(5);
				errorCode=pstmt.getInt(6);
				errMess=pstmt.getString(7);
				Utility.LOG(true,false,"errMess   : "+errMess);
				while(rs.next()){
					mailDTOObj=new EscalationMailDTO();
					Utility.LOG(true, false, rs.getInt("ORDERNO")+"");
					mailDTOObj.setAccountName(rs.getString("CUSTOMER_NAME"));
					mailDTOObj.setAgingFromPORecDate(rs.getString("AGING_FROM__PO_RECEIVED_DATE"));
					mailDTOObj.setAgingInCurrentBin(rs.getString("AGING_IN_CURRENT_BIN"));
					mailDTOObj.setBinOwnnerName(rs.getString("BIN_OWNER_NAME"));
					mailDTOObj.setLevelDetailsCC(rs.getString("leveldetails"));
					mailDTOObj.setLevelDetailsToDB(rs.getString("leveldetailsTo"));
					mailDTOObj.setOrderNo(rs.getInt("ORDERNO"));
					mailDTOObj.setPoAmount(BigDecimal.valueOf((rs.getDouble("VALUE_OF_ORDER"))).toPlainString());
					mailDTOObj.setPoRecieveDate(rs.getString("PORECEIVEDATE"));
					mailDTOObj.setProductName(rs.getString("PRODUCTNAME"));
					mailDTOObj.setRoleId(rs.getInt("OWNERTYPE_ID"));
					mailDTOObj.setRegionId(rs.getInt("REGIONID"));
					mailDTOObj.setChgTypId(rs.getInt("CHANGETYPEID"));
					mailDTOObj.setIsActive(rs.getInt("ISACTIVE"));
					if(userStat!=null && userStat.equalsIgnoreCase("1")){
						if(mailDTOObj.getIsActive()==1){
							lstParam.add(mailDTOObj);
						}
					}else{
						lstParam.add(mailDTOObj);
					}
				}
			} 
			catch (SQLException e) {
				Utility.LOG(true,false,"sqlState   : "+sqlState);
				Utility.LOG(true,false,"errorCode   : "+errorCode);
				Utility.LOG(true,false,"errMess   : "+errMess);
				
				e.printStackTrace();
	
			}catch (Exception e) 
			{
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, "Exception IN Final Block ", logToFile, logToConsole);
				e.printStackTrace();
			}finally {
				try {
					DbConnection.closeResultset(rs);
					DbConnection.closeCallableStatement(pstmt);
					DbConnection.freeConnection(conn);
					
				} catch (Exception e) {
					Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, "@errMessage :"+errMess, logToFile, logToConsole);
					e.printStackTrace();
				}
			}
			Utility.LOG(true,false,"Exiting makeEscalationMailDetailsPMBIN");
			return lstParam;	

			
		}
		
		//This method is used to get data for escalation mails COCP Partial Publish
		public ArrayList<EscalationMailDTO> makeEscalationMailDetailsCOPCPP(Map<String,String> mapParam,ArrayList<EscalationMailDTO> lstParam) throws Exception
		{
			Utility.LOG(true,false,"Entering makeEscalationMailDetailsCOPCPP");
			String methodName="makeEscalationMailDetailsCOPCPP", className=this.getClass().getName();
			Connection conn=null;
			int sqlState=0;
			int errorCode=0;
			String errMess=null;
			CallableStatement pstmt=null;
			ResultSet rs = null;
			EscalationMailDTO mailDTOObj=null;
			
			try{
				conn=DbConnection.getConnectionObject();
				pstmt= conn.prepareCall(strGetEscalationMailDetails_COPC_PP);
				pstmt.setLong(1, Integer.parseInt(mapParam.get("ESCALATION_PO_DATE_CLOCK_START_HOUR")));
				pstmt.setLong(2, Integer.parseInt(mapParam.get("ESCALATION_PO_DATE_ESCALATION_DAY")));
				pstmt.setLong(3, Integer.parseInt(mapParam.get("ESCALATION_MAX_ESCALATION_LEVEL")));
				pstmt.setLong(4, Integer.parseInt(mapParam.get("ESCALATION_SCHEDULER_MAIL_HOUR")));
				pstmt.setNull(5, java.sql.Types.BIGINT);
				pstmt.setNull(6, java.sql.Types.BIGINT);
				pstmt.setNull(7, java.sql.Types.VARCHAR);
				rs=pstmt.executeQuery();
				sqlState=pstmt.getInt(5);
				errorCode=pstmt.getInt(6);
				errMess=pstmt.getString(7);
				Utility.LOG(true,false,"errMess   : "+errMess);
				while(rs.next()){
					mailDTOObj=new EscalationMailDTO();
					Utility.LOG(true, false, rs.getInt("ORDERNO")+"");
					mailDTOObj.setAccountName(rs.getString("CUSTOMER_NAME"));
					mailDTOObj.setAgingFromPORecDate(rs.getString("AGING_FROM__PO_RECEIVED_DATE"));
					mailDTOObj.setAgingInCurrentBin(rs.getString("AGING_IN_CURRENT_BIN"));
					mailDTOObj.setBinOwnnerName(rs.getString("BIN_OWNER_NAME"));
					mailDTOObj.setLevelDetailsCC(rs.getString("leveldetails"));
					mailDTOObj.setLevelDetailsToDB(rs.getString("leveldetailsTo"));
					mailDTOObj.setOrderNo(rs.getInt("ORDERNO"));
					mailDTOObj.setPoAmount(BigDecimal.valueOf((rs.getDouble("VALUE_OF_ORDER"))).toPlainString());
					mailDTOObj.setPoRecieveDate(rs.getString("PORECEIVEDATE"));
					mailDTOObj.setProductName(rs.getString("PRODUCTNAME"));
					mailDTOObj.setRoleId(rs.getInt("OWNERTYPE_ID"));
					mailDTOObj.setRegionId(rs.getInt("REGIONID"));
					mailDTOObj.setChgTypId(rs.getInt("CHANGETYPEID"));
					mailDTOObj.setIsActive(rs.getInt("ISACTIVE"));
					lstParam.add(mailDTOObj);
				}
			} 
			catch (SQLException e) {
				Utility.LOG(true,false,"sqlState   : "+sqlState);
				Utility.LOG(true,false,"errorCode   : "+errorCode);
				Utility.LOG(true,false,"errMess   : "+errMess);
				
				e.printStackTrace();
	
			}catch (Exception e) 
			{
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, "Exception IN Final Block ", logToFile, logToConsole);
				e.printStackTrace();
			}finally {
				try {
					DbConnection.closeResultset(rs);
					DbConnection.closeCallableStatement(pstmt);
					DbConnection.freeConnection(conn);
					
				} catch (Exception e) {
					Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, "@errMessage :"+errMess, logToFile, logToConsole);
					e.printStackTrace();
				}
			}
			Utility.LOG(true,false,"Exiting makeEscalationMailDetailsCOPCPP");
			return lstParam;	

			
		}
		
		//This method is used to get data for escalation mails COPC BIN
		public ArrayList<EscalationMailDTO> makeEscalationMailDetailsCOPCBIN(Map<String,String> mapParam,ArrayList<EscalationMailDTO> lstParam) throws Exception
		{
			Utility.LOG(true,false,"Entering makeEscalationMailDetailsCOPCBIN");
			String methodName="makeEscalationMailDetailsCOPCBIN", className=this.getClass().getName();
			Connection conn=null;
			int sqlState=0;
			int errorCode=0;
			String errMess=null;
			CallableStatement pstmt=null;
			ResultSet rs = null;
			EscalationMailDTO mailDTOObj=null;
			
			try{
				conn=DbConnection.getConnectionObject();
				pstmt= conn.prepareCall(strGetEscalationMailDetails_COPCBIN);
				pstmt.setLong(1, Integer.parseInt(mapParam.get("ESCALATION_PO_DATE_CLOCK_START_HOUR")));
				pstmt.setLong(2, Integer.parseInt(mapParam.get("ESCALATION_PO_DATE_ESCALATION_DAY")));
				pstmt.setLong(3, Integer.parseInt(mapParam.get("ESCALATION_MAX_ESCALATION_LEVEL")));
				pstmt.setLong(4, Integer.parseInt(mapParam.get("ESCALATION_SCHEDULER_MAIL_HOUR")));
				pstmt.setNull(5, java.sql.Types.BIGINT);
				pstmt.setNull(6, java.sql.Types.BIGINT);
				pstmt.setNull(7, java.sql.Types.VARCHAR);
				rs=pstmt.executeQuery();
				sqlState=pstmt.getInt(5);
				errorCode=pstmt.getInt(6);
				errMess=pstmt.getString(7);
				Utility.LOG(true,false,"errMess   : "+errMess);
				while(rs.next()){
					mailDTOObj=new EscalationMailDTO();
					Utility.LOG(true, false, rs.getInt("ORDERNO")+"");
					mailDTOObj.setAccountName(rs.getString("CUSTOMER_NAME"));
					mailDTOObj.setAgingFromPORecDate(rs.getString("AGING_FROM__PO_RECEIVED_DATE"));
					mailDTOObj.setAgingInCurrentBin(rs.getString("AGING_IN_CURRENT_BIN"));
					mailDTOObj.setBinOwnnerName(rs.getString("BIN_OWNER_NAME"));
					mailDTOObj.setLevelDetailsCC(rs.getString("leveldetails"));
					mailDTOObj.setLevelDetailsToDB(rs.getString("leveldetailsTo"));
					mailDTOObj.setOrderNo(rs.getInt("ORDERNO"));
					mailDTOObj.setPoAmount(BigDecimal.valueOf((rs.getDouble("VALUE_OF_ORDER"))).toPlainString());
					mailDTOObj.setPoRecieveDate(rs.getString("PORECEIVEDATE"));
					mailDTOObj.setProductName(rs.getString("PRODUCTNAME"));
					mailDTOObj.setRoleId(rs.getInt("OWNERTYPE_ID"));
					mailDTOObj.setRegionId(rs.getInt("REGIONID"));
					mailDTOObj.setChgTypId(rs.getInt("CHANGETYPEID"));
					mailDTOObj.setIsActive(rs.getInt("ISACTIVE"));
					lstParam.add(mailDTOObj);
				}
			} 
			catch (SQLException e) {
				Utility.LOG(true,false,"sqlState   : "+sqlState);
				Utility.LOG(true,false,"errorCode   : "+errorCode);
				Utility.LOG(true,false,"errMess   : "+errMess);
				
				e.printStackTrace();
	
			}catch (Exception e) 
			{
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, "Exception IN Final Block ", logToFile, logToConsole);
				e.printStackTrace();
			}finally {
				try {
					DbConnection.closeResultset(rs);
					DbConnection.closeCallableStatement(pstmt);
					DbConnection.freeConnection(conn);
					
				} catch (Exception e) {
					Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, "@errMessage :"+errMess, logToFile, logToConsole);
					e.printStackTrace();
				}
			}
			Utility.LOG(true,false,"Exiting makeEscalationMailDetailsCOPCBIN");
			return lstParam;	

			
		}
		
		//This method is used to get data for escalation mails SED Partial Publish
		public ArrayList<EscalationMailDTO> makeEscalationMailDetailsSEDPP(Map<String,String> mapParam,ArrayList<EscalationMailDTO> lstParam) throws Exception
		{
			Utility.LOG(true,false,"Entering makeEscalationMailDetailsSEDPP");
			String methodName="makeEscalationMailDetailsSEDPP", className=this.getClass().getName();
			Connection conn=null;
			int sqlState=0;
			int errorCode=0;
			String errMess=null;
			CallableStatement pstmt=null;
			ResultSet rs = null;
			EscalationMailDTO mailDTOObj=null;
			
			try{
				conn=DbConnection.getConnectionObject();
				pstmt= conn.prepareCall(strGetEscalationMailDetails_SED_PP);
				pstmt.setLong(1, Integer.parseInt(mapParam.get("ESCALATION_PO_DATE_CLOCK_START_HOUR")));
				pstmt.setLong(2, Integer.parseInt(mapParam.get("ESCALATION_PO_DATE_ESCALATION_DAY")));
				pstmt.setLong(3, Integer.parseInt(mapParam.get("ESCALATION_MAX_ESCALATION_LEVEL")));
				pstmt.setLong(4, Integer.parseInt(mapParam.get("ESCALATION_SCHEDULER_MAIL_HOUR")));
				pstmt.setNull(5, java.sql.Types.BIGINT);
				pstmt.setNull(6, java.sql.Types.BIGINT);
				pstmt.setNull(7, java.sql.Types.VARCHAR);
				rs=pstmt.executeQuery();
				sqlState=pstmt.getInt(5);
				errorCode=pstmt.getInt(6);
				errMess=pstmt.getString(7);
				Utility.LOG(true,false,"errMess   : "+errMess);
				while(rs.next()){
					mailDTOObj=new EscalationMailDTO();
					Utility.LOG(true, false, rs.getInt("ORDERNO")+"");
					mailDTOObj.setAccountName(rs.getString("CUSTOMER_NAME"));
					mailDTOObj.setAgingFromPORecDate(rs.getString("AGING_FROM__PO_RECEIVED_DATE"));
					mailDTOObj.setAgingInCurrentBin(rs.getString("AGING_IN_CURRENT_BIN"));
					mailDTOObj.setBinOwnnerName(rs.getString("BIN_OWNER_NAME"));
					mailDTOObj.setLevelDetailsCC(rs.getString("leveldetails"));
					mailDTOObj.setLevelDetailsToDB(rs.getString("leveldetailsTo"));
					mailDTOObj.setOrderNo(rs.getInt("ORDERNO"));
					mailDTOObj.setPoAmount(BigDecimal.valueOf((rs.getDouble("VALUE_OF_ORDER"))).toPlainString());
					mailDTOObj.setPoRecieveDate(rs.getString("PORECEIVEDATE"));
					mailDTOObj.setProductName(rs.getString("PRODUCTNAME"));
					mailDTOObj.setRoleId(rs.getInt("OWNERTYPE_ID"));
					mailDTOObj.setRegionId(rs.getInt("REGIONID"));
					mailDTOObj.setChgTypId(rs.getInt("CHANGETYPEID"));
					mailDTOObj.setIsActive(rs.getInt("ISACTIVE"));
					lstParam.add(mailDTOObj);
				}
			} 
			catch (SQLException e) {
				Utility.LOG(true,false,"sqlState   : "+sqlState);
				Utility.LOG(true,false,"errorCode   : "+errorCode);
				Utility.LOG(true,false,"errMess   : "+errMess);
				
				e.printStackTrace();
	
			}catch (Exception e) 
			{
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, "Exception IN Final Block ", logToFile, logToConsole);
				e.printStackTrace();
			}finally {
				try {
					DbConnection.closeResultset(rs);
					DbConnection.closeCallableStatement(pstmt);
					DbConnection.freeConnection(conn);
					
				} catch (Exception e) {
					Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, "@errMessage :"+errMess, logToFile, logToConsole);
					e.printStackTrace();
				}
			}
			Utility.LOG(true,false,"Exiting makeEscalationMailDetailsSEDPP");
			return lstParam;	

			
		}
		
		//This method is used to get data for escalation mails SED BIN
		public ArrayList<EscalationMailDTO> makeEscalationMailDetailsSEDBIN(Map<String,String> mapParam,ArrayList<EscalationMailDTO> lstParam) throws Exception
		{
			Utility.LOG(true,false,"Entering makeEscalationMailDetailsSEDBIN");
			String methodName="makeEscalationMailDetailsSEDBIN", className=this.getClass().getName();
			Connection conn=null;
			int sqlState=0;
			int errorCode=0;
			String errMess=null;
			CallableStatement pstmt=null;
			ResultSet rs = null;
			EscalationMailDTO mailDTOObj=null;
			
			try{
				conn=DbConnection.getConnectionObject();
				pstmt= conn.prepareCall(strGetEscalationMailDetails_SEDBIN);
				pstmt.setLong(1, Integer.parseInt(mapParam.get("ESCALATION_PO_DATE_CLOCK_START_HOUR")));
				pstmt.setLong(2, Integer.parseInt(mapParam.get("ESCALATION_PO_DATE_ESCALATION_DAY")));
				pstmt.setLong(3, Integer.parseInt(mapParam.get("ESCALATION_MAX_ESCALATION_LEVEL")));
				pstmt.setLong(4, Integer.parseInt(mapParam.get("ESCALATION_SCHEDULER_MAIL_HOUR")));
				pstmt.setNull(5, java.sql.Types.BIGINT);
				pstmt.setNull(6, java.sql.Types.BIGINT);
				pstmt.setNull(7, java.sql.Types.VARCHAR);
				rs=pstmt.executeQuery();
				sqlState=pstmt.getInt(5);
				errorCode=pstmt.getInt(6);
				errMess=pstmt.getString(7);
				Utility.LOG(true,false,"errMess   : "+errMess);
				while(rs.next()){
					mailDTOObj=new EscalationMailDTO();
					Utility.LOG(true, false, rs.getInt("ORDERNO")+"");
					mailDTOObj.setAccountName(rs.getString("CUSTOMER_NAME"));
					mailDTOObj.setAgingFromPORecDate(rs.getString("AGING_FROM__PO_RECEIVED_DATE"));
					mailDTOObj.setAgingInCurrentBin(rs.getString("AGING_IN_CURRENT_BIN"));
					mailDTOObj.setBinOwnnerName(rs.getString("BIN_OWNER_NAME"));
					mailDTOObj.setLevelDetailsCC(rs.getString("leveldetails"));
					mailDTOObj.setLevelDetailsToDB(rs.getString("leveldetailsTo"));
					mailDTOObj.setOrderNo(rs.getInt("ORDERNO"));
					mailDTOObj.setPoAmount(BigDecimal.valueOf((rs.getDouble("VALUE_OF_ORDER"))).toPlainString());
					mailDTOObj.setPoRecieveDate(rs.getString("PORECEIVEDATE"));
					mailDTOObj.setProductName(rs.getString("PRODUCTNAME"));
					mailDTOObj.setRoleId(rs.getInt("OWNERTYPE_ID"));
					mailDTOObj.setRegionId(rs.getInt("REGIONID"));
					mailDTOObj.setChgTypId(rs.getInt("CHANGETYPEID"));
					mailDTOObj.setIsActive(rs.getInt("ISACTIVE"));
					lstParam.add(mailDTOObj);
				}
			} 
			catch (SQLException e) {
				Utility.LOG(true,false,"sqlState   : "+sqlState);
				Utility.LOG(true,false,"errorCode   : "+errorCode);
				Utility.LOG(true,false,"errMess   : "+errMess);
				
				e.printStackTrace();
	
			}catch (Exception e) 
			{
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, "Exception IN Final Block ", logToFile, logToConsole);
				e.printStackTrace();
			}finally {
				try {
					DbConnection.closeResultset(rs);
					DbConnection.closeCallableStatement(pstmt);
					DbConnection.freeConnection(conn);
					
				} catch (Exception e) {
					Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, "@errMessage :"+errMess, logToFile, logToConsole);
					e.printStackTrace();
				}
			}
			Utility.LOG(true,false,"Exiting makeEscalationMailDetailsSEDBIN");
			return lstParam;	

			
		}
		
		//This method is used to get data for escalation mails 
		public ArrayList<EscalationMailDTO> makeEscalationMailDetails(Map<String,String> mapParam) throws Exception
		{
			Utility.LOG(true,false,"Entering makeEscalationMailDetails");
			String methodName="makeEscalationMailDetails", className=this.getClass().getName();
			ArrayList<EscalationMailDTO> lst=new ArrayList<EscalationMailDTO>();
			try{
				String userStat=mapParam.get("ESCALATION_BIN_OWNER_STATUS");
				
				makeEscalationMailDetailsAMBIN(mapParam,lst,userStat);
				Utility.LOG(true,false,"tmpLst size2 "+lst.size());
				makeEscalationMailDetailsAMINC(mapParam,lst,userStat);
				Utility.LOG(true,false,"tmpLst size3 "+lst.size());
				makeEscalationMailDetailsPMBIN(mapParam,lst,userStat);
				Utility.LOG(true,false,"tmpLst size4 "+lst.size());
				makeEscalationMailDetailsCOPCPP(mapParam,lst);
				Utility.LOG(true,false,"tmpLst size5 "+lst.size());
				makeEscalationMailDetailsCOPCBIN(mapParam,lst);
				Utility.LOG(true,false,"tmpLst size6 "+lst.size());
				makeEscalationMailDetailsSEDPP(mapParam,lst);
				Utility.LOG(true,false,"tmpLst size7 "+lst.size());
				makeEscalationMailDetailsSEDBIN(mapParam,lst);
				Utility.LOG(true,false,"tmpLst size8 "+lst.size());
				
			} 
			catch (Exception e) 
			{
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, "Exception IN Final Block ", logToFile, logToConsole);
				e.printStackTrace();
			}
			Utility.LOG(true,false,"Exiting makeEscalationMailDetails");
			return lst;	

			
		}
		
		//to get comma separated value of set passed
		public String getDistinctCommaSepValFrmSet(Set<String> tmpSet) throws Exception{
			StringBuilder outputStr=new StringBuilder();
			int count=1;
			int setSize=tmpSet.size();
			if(setSize>0){
				for (Object obj : tmpSet) { 
					if(count==setSize){           
					    outputStr.append(obj);
					}else{
						//Append the values and the separator 
						 outputStr.append(obj).append(","); 
					}
					count=count+1;
					} 
				return outputStr.toString();
			}else{
				return "";
			}
			
		}
		
		//to get comma separated value of set passed of mail
		public String getDistinctCommaSepValMailFrmSet(Set<String> tmpSet) throws Exception{
			StringBuilder outputStr=new StringBuilder();
			int count=1;
			int setSize=tmpSet.size();
			String tmpObj=new String();
			if(setSize>0){
				for (Object obj : tmpSet) {
					tmpObj=(String)obj;
					if(tmpObj!=null && !"".equalsIgnoreCase(tmpObj) && !tmpObj.contains(" ") && tmpObj.contains("@") && tmpObj.indexOf("@")!=0){
						if(count==setSize){           
						    outputStr.append(tmpObj);
						}else{
							//Append the values and the separator even if it's the leaving element
							 outputStr.append(tmpObj).append(","); 
						}
					}
					
					count=count+1;
					} 
				return outputStr.toString();
			}else{
				return "";
			}
			
		}
		
		}

