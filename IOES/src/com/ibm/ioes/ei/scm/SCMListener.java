package com.ibm.ioes.ei.scm;
//Tag Name Resource Name  Date		CSR No			Description
//[001]	 Kalpana	11-January-2014			New File to get response( xml)  from SCM third party

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.QueueConnection;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionServlet;

import com.ibm.ioes.ei.scm.dto.SCMXMLDto;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Messages;
import com.ibm.ioes.utilities.Utility;
import com.ibm.mq.jms.MQQueueConnectionFactory;

public class SCMListener implements MessageListener ,Runnable{
	private static final Logger logger = Logger.getLogger(SCMListener.class);
	final static String strGetAppConfigValue ="{call IOE.GET_TP_APPCONFIG_VALUE(?)}";
	public static ActionServlet actionServlet = null;
	private static Map<String,String> appConfig=new HashMap<String,String>();
	
	public SCMListener() {
	}
	
	public void onMessage(Message message) {
		System.err.println("SCMListener : on Message called at "+new Date());
		logger.info("SCMListener:In the onMessage()========");
		try {
			
			TextMessage txtmsg = (TextMessage) message;
			String inmsg = txtmsg.getText();
			Utility.LOG(true, true, "SCMListener :saving message ...");
			saveResponse(txtmsg);
			Utility.LOG(true, true, "SCMListener :End-Saving message .");
			System.err.println("In the onMessage()========  : "+inmsg);
			//save inmsg to db
			/*if(!Utility.switchOn(AppConstants.SWITCH_JOB_M6_XML_PROCESS))
			{
				return;
			}*/
//			run job
			Utility.LOG(true, true, "SCMListener :Calling for processing of SCM Received XMl .");
			try
			{
				new ProcessSCMResponseXML().processScmXml();
			}
			catch(Exception ex)
			{
				Utility.onEx_LOG_RET_NEW_EX(ex,"onMessage","SCMListener","exception while processing xml",true,true);
			}
			Utility.LOG(true, true, "SCMListener :End : Calling for processing of SCM Received XMl .");
			
		} catch (JMSException ex) {
			ex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		try {
			//Wait till server has started
			try {
				Thread.sleep(AppConstants.SERVER_START_DELAY);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			main(null);
		}  catch (IOException ex) {
			Utility.onEx_LOG_RET_NEW_EX(ex,"run","SCMListener",null,true,true);
		}catch (Exception ex) {
			Utility.onEx_LOG_RET_NEW_EX(ex,"run","SCMListener",null,true,true);
		}
	}
	
	public static void initiateConnection(ServletContext servletContext) throws IOException {
		String host=null;
		String channel=null;
		int port=0;
		String qmgr=null;
		String queuePrReuse=null;
		String queueEiResp=null;
		String queueScmResp=null;
		
		MQQueueConnectionFactory qcf = null;
		QueueSession session = null;
		QueueConnection jmsCon = null;
		javax.jms.Queue prReuseQ = null;
		javax.jms.Queue eiRespQ = null;
		javax.jms.Queue scmRespQ = null;
		javax.jms.QueueReceiver prReuseQReceiver = null;
		javax.jms.QueueReceiver eiRespQReceiver = null;
		javax.jms.QueueReceiver scmRespQReceiver = null;
		
		try {
			
			host=appConfig.get("scmQueue_host");
			channel=appConfig.get("scmQueue_channel");
			port=Integer.parseInt(appConfig.get("scmQueue_port"));
			qmgr=appConfig.get("scmQueue_qmgrName");
			
			queuePrReuse=appConfig.get("scmQueue_respQNamePRReuse");
			
			queueEiResp=appConfig.get("scmQueue_respQNameEiResp");
			
			queueScmResp=appConfig.get("scmQueue_respQNameSCMResp");
			
			
			logger.info("Creating a connection to queuePrReuse" + queuePrReuse);
			logger.info("Creating a connection to getM6Queue_host()" + host);
			logger.info("Creating a connection to getM6Queue_channel()" + channel);
			logger.info("Creating a connection to getM6Queue_port()" + port);
			logger.info("Creating a connection to getM6Queue_qmgrName()" + qmgr);
			logger.info("Creating a connection to queueEiResp" + queueEiResp);
			logger.info("Creating a connection to queueScmResp" + queueScmResp);
			
			qcf = new MQQueueConnectionFactory();
			qcf.setHostName(host);
			qcf.setChannel(channel);
			qcf.setPort(port);
			qcf.setQueueManager(qmgr);
			qcf.setTransportType(1);
			logger.info("Creating a connection to queue" + queuePrReuse);
			logger.info("Creating a connection to " + host + "/" + qmgr + "/" + port);
			jmsCon = qcf.createQueueConnection();
			logger.info("Connection created \n");
			session = jmsCon.createQueueSession(false, 1);
			
			SCMListener queueListener = new SCMListener();
			
			if(Integer.parseInt(Utility.getAppConfigValue("PR_REUSE_LISTENER_ON"))==1){
				prReuseQ = session.createQueue(queuePrReuse);
				prReuseQReceiver = session.createReceiver(prReuseQ);
				logger.info("Before setting listener prReuseQ");
				prReuseQReceiver.setMessageListener(queueListener);
				logger.info("After setting listener prReuseQ");
			}
			
			if(Integer.parseInt(Utility.getAppConfigValue("EI_RESPONSE_LISTENER_ON"))==1){
				eiRespQ = session.createQueue(queueEiResp);
				eiRespQReceiver = session.createReceiver(eiRespQ);
				logger.info("Before setting listener eiRespQReceiver");
				eiRespQReceiver.setMessageListener(queueListener);
				logger.info("After setting listener eiRespQReceiver");
			}
			
			if(Integer.parseInt(Utility.getAppConfigValue("SCM_RESPONSE_LISTENER_ON"))==1){
				scmRespQ = session.createQueue(queueScmResp);
				scmRespQReceiver = session.createReceiver(scmRespQ);
				logger.info("Before setting listener scmRespQReceiver");
				scmRespQReceiver.setMessageListener(queueListener);
				logger.info("After setting listener scmRespQReceiver");
			}
			
			jmsCon.start();
			servletContext.setAttribute(AppConstants.SCM_REQUEST_QUEUE_CONNECTION, jmsCon);
			servletContext.setAttribute(AppConstants.SCM_LISTENER_STATE, "START");
			logger.info("Waiting for the message");
			//logger.info("Waiting for the message"+jmsCon);
			
		} catch (JMSException je) {
			je.printStackTrace();
			logger.info(
				"Couldn't create connection to "
					+ host
					+ "/"
					+ qmgr
					+ "/"
					+ port);
			logger.info(je);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		finally
		{
			try {
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void saveResponse(TextMessage txtmsg) {

		Connection conn=null;
		String msg=null;
		String jmsMessageID=null;
		PreparedStatement pstmt=null;
		try {
			msg=txtmsg.getText();
			jmsMessageID=txtmsg.getJMSMessageID();
			
			String fileName=jmsMessageID.substring(3)+".xml";
			saveFile(fileName,msg);
			logger.info("After setting listener jmsMessageID!!"+jmsMessageID);
			logger.info("After setting listener jmsMessageID correalation!!"+txtmsg.getJMSCorrelationID());
			conn=DbConnection.getConnectionObject();
			String sql="call IOE.SPSAVE_SCMRESPONSE(?,?,?)";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, jmsMessageID);
			pstmt.setString(2, fileName);
			pstmt.setString(3, txtmsg.getJMSCorrelationID());
			pstmt.execute();
			
			
		} catch (Exception ex) {
			Utility.onEx_LOG_RET_NEW_EX(ex, "saveToDB", "SCMListener", "jmsMessageID:"+jmsMessageID+" Message String:"+msg, true, true); 
		}finally
		{
			try {
				DbConnection.closePreparedStatement(pstmt);
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				Utility.onEx_LOG_RET_NEW_EX(e, "saveToDB", "SCMListener", null, true, true);  
			}
		}
		
		
	}
	
		//to get required values from tm_appconfig table
	public static SCMXMLDto getAppConfigVal()throws Exception {
		Utility.LOG(true,false,"Entering getAppConfigVal");
		Connection connection =null;
		PreparedStatement getList =null;
		Map<String,String> tmpMap=new HashMap<String,String>();
		ResultSet rsAppConfig = null;
		SCMXMLDto tmpScmDto =new SCMXMLDto();
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
			appConfig.put("scmQueue_changeReqQName",tmpMap.get("scmQueue_changeReqQName"));
			appConfig.put("scmQueue_reqQName",tmpMap.get("scmQueue_reqQName"));
			appConfig.put("scmQueue_respQNamePRReuse",tmpMap.get("scmQueue_respQNamePRReuse"));
			appConfig.put("scmQueue_respQNameEiResp",tmpMap.get("scmQueue_respQNameEiResp"));
			appConfig.put("scmQueue_respQNameSCMResp",tmpMap.get("scmQueue_respQNameSCMResp"));
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
		return tmpScmDto;
	}

	public static void main(ServletContext servletContext) throws IOException {
		try{
				getAppConfigVal();
				initiateConnection(servletContext);
			} catch (Exception ex) {
				Utility.onEx_LOG_RET_NEW_EX(ex,"run","SCMListener",null,true,true);
			}
	}
	
	private void saveFile(String fileName, String msg) {
		
		File f=new File(Messages.getMessageValue("SCM_RESPONSE_PATH")+fileName);
		
		try {
			f.createNewFile();
			FileOutputStream outputStream=new FileOutputStream(f);
			byte[] data= msg.getBytes();
			outputStream.write(data);
			outputStream.close();
			
		}catch (IOException ex) {
			Utility.onEx_LOG_RET_NEW_EX(ex,"run","SCMListener",null,true,true);
		} catch (Exception e) {
			Utility.onEx_LOG_RET_NEW_EX(e,"saveFile","SCMListener",null,true,true);
		}
		
		
	}


}
