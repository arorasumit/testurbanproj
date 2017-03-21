package com.ibm.ioes.ei.scm;
//Tag Name Resource Name  Date		CSR No			Description
//[001]	 Kalpana	11-January-2014			New File to send request to SCM for third party

import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.jms.JMSException;

import com.ibm.ioes.ei.MessageHandlerSCM;
import com.ibm.ioes.ei.scm.dto.SCMXMLDto;
import com.ibm.ioes.forms.LineItemValueDTO;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Messages;
import com.ibm.ioes.utilities.Utility;
import com.ibm.ioes.ei.scm.BL.SCMBL;


public class SendToSCM {
	
	final static String strGetAppConfigValue ="{call IOE.GET_TP_APPCONFIG_VALUE(?)}";
	final static StringBuilder sql_getNextSCM_XML=new StringBuilder();
	private static long timeOut=Long.parseLong(Messages.getMessageValue("m6Queue_timeOut"));
	private static final int XML_Sent_Success=3;
	private static final int XML_Sent_Failure=5;
	private static final int JMS_Connection_Success=2;
	private static final int JMS_Connection_Failure=4;
	final static String sql_UpdateStatusSCM="update IOE.TSCM_XMLSEND_STATUS set status=?,messageId=?, lastUpdatedDate=CURRENT TIMESTAMP where id=? ";
	private MessageHandlerSCM handler=new MessageHandlerSCM();
	private Map<String,String> appConfig=new HashMap<String,String>();
	private HashMap<SCMXMLDto,SCMXMLDto> xmlMapReuse=new HashMap<SCMXMLDto,SCMXMLDto>();
	private HashMap<SCMXMLDto,SCMXMLDto> xmlMapCreate=new HashMap<SCMXMLDto,SCMXMLDto>();
	final static StringBuilder operString=new StringBuilder();
	private static  SCMBL scmBLObj=new SCMBL();
	
	static{
		sql_getNextSCM_XML.append(" select ID,XML,ORDERNO,SERVICEPRODUCTID,PR_OPERATION,SERVICEID from  IOE.TSCM_XMLSEND_STATUS");  
		sql_getNextSCM_XML.append(" where STATUS=0 and PR_OPERATION  in (?) order by ORDERNO,SERVICEID,SERVICEPRODUCTID, LASTUPDATEDDATE asc with ur");
		
		operString.append(AppConstants.PR_REUSE);
		operString.append(",");
		operString.append(AppConstants.CIRCUIT_ID_UPDATE);
		
	}
	
	
	public void sendToSCMJob(){
		Utility.LOG(true,false,"Info -  In SendToPRReuseSCM ");
		System.err.println("in sendToSCMJob"); 
		SCMXMLDto scmDto =new SCMXMLDto();
		try{
			getAppConfigVal();
			fetchNextXMLData();
			if(xmlMapReuse.size()>0 || xmlMapCreate.size()>0 ){
				if(xmlMapCreate.size()>0){
					createAndUpdateXmlPRCreate(xmlMapCreate);
				}
				if(xmlMapReuse.size()>0){
					createAndUpdateXmlPRReuse(xmlMapReuse);
				}
				initConnection(scmDto);
				int sendStatus=scmDto.getSend_status();
				if(sendStatus==JMS_Connection_Success){
					//connection success
					sendXML(handler,scmDto);
					setStatus(JMS_Connection_Success);
				}else{
					//connection failure
					setStatus(JMS_Connection_Failure);	
				}
			}
		}
		catch(Exception ex){
			Utility.onEx_LOG_RET_NEW_EX(ex, "sendToSCMJob", "SendToSCM", null, true, true);  
		}
		
		
	}

	//to send xml to EI
	private void sendXML(MessageHandlerSCM handler,SCMXMLDto scmDto){
		
		int sendStatus=0;
		String messageID=null;
		SCMXMLDto tmpObj=new SCMXMLDto();
		Utility.LOG(true,false,"Info -  In sendXML ");
		try {
			if(xmlMapReuse.size()>0 && Integer.parseInt(Utility.getAppConfigValue("PR_REUSE_REQUEST_ON"))==1){
				Utility.LOG(true,false,"Rquest Queue Name: "+appConfig.get("scmQueue_reqQNamePRReuse") );
				Utility.LOG(true,false,"Response Queue Name: "+appConfig.get("scmQueue_respQNamePRReuse") );
				for (Map.Entry<SCMXMLDto,SCMXMLDto> entry : xmlMapReuse.entrySet()) {
					System.out.println("xmlMapReuse Key : " + entry.getKey() + " Value : "
						+ entry.getValue());
					try{
						tmpObj=(SCMXMLDto)entry.getValue();
						messageID=handler.requestResponseSCM(tmpObj.getXml(), appConfig.get("scmQueue_reqQNamePRReuse"),  appConfig.get("scmQueue_respQNamePRReuse"),timeOut);
						sendStatus=XML_Sent_Success;
						tmpObj.setSend_status(sendStatus);
						tmpObj.setMessageID(messageID);
						xmlMapReuse.put(entry.getKey(), tmpObj);
					}catch (Throwable e) {
						// TODO Auto-generated catch block
						sendStatus=XML_Sent_Failure;
						tmpObj.setSend_status(sendStatus);
						tmpObj.setMessageID(e.toString());
						xmlMapReuse.put(entry.getKey(), tmpObj);
						e.printStackTrace();
						Utility.LOG(true,false,"Info -  In sendXML :In Throwable Exception ");
					}
				}
			}
			if(xmlMapCreate.size()>0 && Integer.parseInt(Utility.getAppConfigValue("PR_CREATE_REQUEST_ON"))==1){
				Utility.LOG(true,false,"Rquest Queue Name: "+appConfig.get("scmQueue_reqQNamePRCreate") );
				Utility.LOG(true,false,"Response Queue Name: "+appConfig.get("scmQueue_respQNameEiResp") );
				for (Map.Entry<SCMXMLDto,SCMXMLDto> entry : xmlMapCreate.entrySet()) {
					System.out.println("xmlMapCreate Key : " + entry.getKey() + " Value : "
						+ entry.getValue());
					try{
						tmpObj=(SCMXMLDto)entry.getValue();
						messageID=handler.requestResponseSCM(tmpObj.getXml(), appConfig.get("scmQueue_reqQNamePRCreate"),  appConfig.get("scmQueue_respQNameEiResp"),timeOut);
						sendStatus=XML_Sent_Success;
						tmpObj.setSend_status(sendStatus);
						tmpObj.setMessageID(messageID);
						xmlMapCreate.put(entry.getKey(), tmpObj);
					}catch (Throwable e) {
						// TODO Auto-generated catch block
						sendStatus=XML_Sent_Failure;
						tmpObj.setSend_status(sendStatus);
						tmpObj.setMessageID(e.toString());
						xmlMapCreate.put(entry.getKey(), tmpObj);
						e.printStackTrace();
						Utility.LOG(true,false,"Info -  In sendXML :In Throwable Exception ");
					}
				}
			}
		}catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Utility.LOG(true,false,"Info -  In sendXML :In Throwable Exception ");
		}finally{
			Utility.LOG(true,false,"Info -  In sendXML : finally ");
			handler.closeConnection();
		}
		
		
	}
	
	
	//to initialize jms connection
	private void initConnection(SCMXMLDto scmDto){
		
		int sendStatus=0;
		try {
			Utility.LOG(true,false,"Info - host : "+appConfig.get("scmQueue_host"));
			Utility.LOG(true,false,"Info - channel : "+appConfig.get("scmQueue_channel"));
			Utility.LOG(true,false,"Info - Port : "+appConfig.get("scmQueue_port"));
			Utility.LOG(true,false,"Rquest Q Manager: "+appConfig.get("scmQueue_qmgrName") );
			Utility.LOG(true,false,"Info -  In initConnection ");
			//init connection to EI
			handler.initJMSConnection(appConfig.get("scmQueue_host"), appConfig.get("scmQueue_channel"), Integer.parseInt(appConfig.get("scmQueue_port")),
					  appConfig.get("scmQueue_qmgrName"));
			Utility.LOG(true,false,"After initiallizing connection : ");
			sendStatus=JMS_Connection_Success;
		}catch (JMSException je) {
				System.out.println(
					"Couldn't create connection to "
						+ appConfig.get("scmQueue_host")
						+ "/"
						+ appConfig.get("scmQueue_channel")
						+ "/"
						+ appConfig.get("scmQueue_port")
						+ "/"
						+ appConfig.get("scmQueue_qmgrName"));
				//	  		je.printStackTrace();
				System.out.println("*****************" + je.getLinkedException());
				sendStatus=JMS_Connection_Failure;

			}
		catch (Exception e) {
			// TODO Auto-generated catch block
			sendStatus=JMS_Connection_Failure;
			e.printStackTrace();
			Utility.LOG(true,false,"Info -  In initConnection :In Throwable Exception ");
		}finally{
			Utility.LOG(true,false,"Info -  In initConnection : finally ");
			scmDto.setSend_status(sendStatus);
		}
		
		
		
		
	}

	//to set status in TSCM_XMLSEND_STATUS table
	private void setStatus(int jmsConnStatus) {
		SCMXMLDto tmpObj=new SCMXMLDto();
		Connection connection=null;
		CallableStatement pstmt=null;
		Utility.LOG(true,false,"Info -  In setStatus ");
		try {
			int lstSize=xmlMapReuse.size();
			connection=DbConnection.getConnectionObject();
			 pstmt=connection.prepareCall(sql_UpdateStatusSCM);
			 if(lstSize>0){
				 for (Map.Entry<SCMXMLDto,SCMXMLDto> entry : xmlMapReuse.entrySet()) {
						System.out.println("Key : " + entry.getKey() + " Value : "
							+ entry.getValue());
						tmpObj=(SCMXMLDto)entry.getValue();
						if(jmsConnStatus==JMS_Connection_Failure || jmsConnStatus==AppConstants.STATUS_FOR_FAILURE_XML_CREATION){
							pstmt.setShort(1, (short)jmsConnStatus);
							pstmt.setString(2, "No Connection");
						}else{
							pstmt.setShort(1, (short)tmpObj.getSend_status());
							pstmt.setString(2, tmpObj.getMessageID());
						}
						pstmt.setLong(3, tmpObj.getXmlId());
						pstmt.addBatch(); 
					}
				}
			 if(xmlMapCreate.size()>0){
				 for (Map.Entry<SCMXMLDto,SCMXMLDto> entry : xmlMapCreate.entrySet()) {
						System.out.println("Key : " + entry.getKey() + " Value : "
							+ entry.getValue());
						tmpObj=(SCMXMLDto)entry.getValue();
						if(jmsConnStatus==JMS_Connection_Failure || jmsConnStatus==AppConstants.STATUS_FOR_FAILURE_XML_CREATION){
							pstmt.setShort(1, (short)jmsConnStatus);
							pstmt.setString(2, "No Connection");
						}else{
							pstmt.setShort(1, (short)tmpObj.getSend_status());
							pstmt.setString(2, tmpObj.getMessageID());
						}
						pstmt.setLong(3, tmpObj.getXmlId());
						pstmt.addBatch(); 
					}
				}
			 	pstmt.executeBatch();
		}catch (SQLException e){
			// TODO Auto-generated catch block
			Utility.LOG(true,false,"Info -  In setStatus SQLException");
			e.printStackTrace();
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			Utility.LOG(true,false,"Info -  In setStatus Exception");
			Utility.onEx_LOG_RET_NEW_EX(ex, "setStatus", "SendToSCM", null, true, true);  
		}finally{
			try{
				DbConnection.closeCallableStatement(pstmt);
				DbConnection.freeConnection(connection);
				Utility.LOG(true,false,"Info -  In setStatus finally");
			}catch (SQLException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (Exception e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	//to fetch xml from DB to send to scm
	private void fetchNextXMLData() throws Exception {
		
		Utility.LOG(true,false,"Entering fetchNextXMLData");
		CallableStatement getNextSCMXML=null;
		Connection connection=null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		SCMXMLDto scmDto=null;
		try {
			    Utility.LOG(true,false,"Info -  In fetchNextXMLData ");
			    connection=DbConnection.getConnectionObject();
				
			    getNextSCMXML=connection.prepareCall(sql_getNextSCM_XML.toString()); 
			    getNextSCMXML.setString(1,AppConstants.PR_CREATE);
			    rs1=getNextSCMXML.executeQuery();
				while(rs1.next()){
					scmDto=new SCMXMLDto();
					scmDto.setOrderNumber(Integer.parseInt(rs1.getString("ORDERNO")));
					scmDto.setServicePrdId(Integer.parseInt(rs1.getString("SERVICEPRODUCTID")));
					scmDto.setServiceId(Integer.parseInt(rs1.getString("SERVICEID")));
					scmDto.setOperatnExec(rs1.getString("PR_OPERATION"));
					scmDto.setXmlId(rs1.getLong("ID"));
					Clob b=rs1.getClob("XML");
					if(b!=null){
						byte byteArr[]=Utility.clobToByteArray(b);
						String xml=new String(byteArr);
						scmDto.setXml(xml);
					}else{
						scmDto.setXml("");
					}
					xmlMapCreate.put(scmDto, scmDto);
				}
				DbConnection.closeResultset(rs1);
				
				getNextSCMXML=connection.prepareCall(sql_getNextSCM_XML.toString()); 
			    
			    StringTokenizer token=new StringTokenizer(operString.toString(),AppConstants.OPEARTION_SEPARATOR);
			    while(token.hasMoreTokens()){
			    	getNextSCMXML.setString(1,token.nextElement().toString());
					 rs2=getNextSCMXML.executeQuery();
						while(rs2.next()){
							scmDto=new SCMXMLDto();
							scmDto.setOrderNumber(Integer.parseInt(rs2.getString("ORDERNO")));
							scmDto.setServicePrdId(Integer.parseInt(rs2.getString("SERVICEPRODUCTID")));
							scmDto.setServiceId(Integer.parseInt(rs2.getString("SERVICEID")));
							scmDto.setOperatnExec(rs2.getString("PR_OPERATION"));
							scmDto.setXmlId(rs2.getLong("ID"));
							Clob b=rs2.getClob("XML");
							if(b!=null){
								byte byteArr[]=Utility.clobToByteArray(b);
								String xml=new String(byteArr);
								scmDto.setXml(xml);
							}else{
								scmDto.setXml("");
							}
							xmlMapReuse.put(scmDto, scmDto);
						}
					
				}
				DbConnection.closeResultset(rs2);
				Utility.LOG(true,false,"xmlMapCreate size "+xmlMapCreate.size() );
				Utility.LOG(true,false,"xmlMapReuse size "+xmlMapReuse.size() );
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			throw Utility.onEx_LOG_RET_NEW_EX(ex, "fetchNextXMLData", "SendToSCM", null, true, true); 
		}
		finally{
			
			
			DbConnection.closeCallableStatement(getNextSCMXML);
			DbConnection.freeConnection(connection);
		}
		
		Utility.LOG(true,false,"Exiting fetchNextXMLData");
		
	}
	
	//to get required values from tm_appconfig table
	public void  getAppConfigVal()throws Exception {
		Utility.LOG(true,false,"Entering getAppConfigVal");
		Connection connection =null;
		PreparedStatement getList =null;
		Map<String,String> tmpMap=new HashMap<String,String>();
		ResultSet rsAppConfig = null;
		try{
			//Start for properties file into database
			connection=DbConnection.getConnectionObject();
			getList= connection.prepareCall(strGetAppConfigValue);
			getList.setString(1, "Third Party");
			rsAppConfig = getList.executeQuery();
			while(rsAppConfig.next()){
				Utility.LOG(true,true,rsAppConfig.getString("KEYNAME"));
				Utility.LOG(true,true,rsAppConfig.getString("KEYVALUE"));
				tmpMap.put(rsAppConfig.getString("KEYNAME"), rsAppConfig.getString("KEYVALUE"));
			}
			appConfig.put("scmQueue_host", tmpMap.get("scmQueue_host"));
			appConfig.put("scmQueue_channel",tmpMap.get("scmQueue_channel"));
			appConfig.put("scmQueue_port",tmpMap.get("scmQueue_port"));
			appConfig.put("scmQueue_qmgrName",tmpMap.get("scmQueue_qmgrName"));
			appConfig.put("scmQueue_changeReqQName",tmpMap.get("scmQueue_changeReqQNamePRReuse"));
			appConfig.put("scmQueue_reqQNamePRReuse",tmpMap.get("scmQueue_reqQNamePRReuse"));
			appConfig.put("scmQueue_respQNamePRReuse",tmpMap.get("scmQueue_respQNamePRReuse"));
			
			appConfig.put("scmQueue_reqQNamePRCreate",tmpMap.get("scmQueue_reqQNamePRCreate"));
			appConfig.put("scmQueue_respQNameEiResp",tmpMap.get("scmQueue_respQNameEiResp"));

			//scmDto.setM6_Response_Path(tmpMap.get("scmQueue_host"));
			//End 
			
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
		
	}
	
	public static void main(String[] args) {
		new SendToSCM().sendToSCMJob();
		
	}
	
	//to create and update XML in scm send table
	public void  createAndUpdateXmlPRCreate(HashMap<SCMXMLDto,SCMXMLDto> mapParam){
		Utility.LOG(true,false,"Entering createAndUpdateXmlPRCreate");
		Connection connection =null;
		try{
			//Start for properties file into database
			connection=DbConnection.getConnectionObject();
			scmBLObj.createSCMXML(connection, mapParam);
		}catch(Exception ex ){
			ex.printStackTrace();	
		}finally{
			try{
				DbConnection.freeConnection(connection);
			}catch (SQLException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(Exception ex ){
				ex.printStackTrace();	
			}
		}
		Utility.LOG(true,false,"Exiting createAndUpdateXmlPRCreate");
		
	}
	
	//to create and update XML in scm send table
	public void  createAndUpdateXmlPRReuse(HashMap<SCMXMLDto,SCMXMLDto> mapParam){
		Utility.LOG(true,false,"Entering createAndUpdateXmlPRReuse");
		Connection connection =null;
		try{
			//Start for properties file into database
			connection=DbConnection.getConnectionObject();
			scmBLObj.createAndUpdateXmlPRReuse(connection, mapParam);
		}catch(Exception ex ){
			ex.printStackTrace();	
		}finally{
			try{
				DbConnection.freeConnection(connection);
			}catch (SQLException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(Exception ex ){
				ex.printStackTrace();	
			}
		}
		Utility.LOG(true,false,"Exiting createAndUpdateXmlPRReuse");
		
	}
	
	



}
