
package com.ibm.ioes.ecrm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.QueueConnection;
import javax.jms.QueueSession;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Messages;
import com.ibm.ioes.utilities.Utility;
import com.ibm.mq.jms.MQQueueConnectionFactory;



// Referenced classes of package com.ibm:
//            SendMTHTTP

public class CRMListener implements MessageListener ,Runnable{
	private static final Logger logger = Logger.getLogger(CRMListener.class);
	
	public CRMListener() {
	}

	public void onMessage(Message message) {
		System.err.println("CRMListener : on Message called at "+new Date());
		logger.info("CRMListener:In the onMessage()========");
		TextMessage txtmsg = (TextMessage) message;
		try {
			String inmsg = txtmsg.getText();
			Utility.LOG(true, true, "CRMListener :saving message ...");
			saveResponse(txtmsg);
			Utility.LOG(true, true, "CRMListener :End-Saving message .");
			System.err.println("In the onMessage()========  : "+inmsg);
			//save inmsg to db
			if(!Utility.switchOn(AppConstants.SWITCH_JOB_M6_XML_PROCESS))
			{
				return;
			}
//			run job
			Utility.LOG(true, true, "CRMListener :Calling for processing of M6 Received XMl .");
			try
			{
				//new ProcessResponseXML().processResponseJob();
			}
			catch(Exception ex)
			{
				Utility.onEx_LOG_RET_NEW_EX(ex,"onMessage","CRMListener","exception while processing xml",true,true);
			}
			Utility.LOG(true, true, "CRMListener :End : Calling for processing of CRM Received XMl .");
			
		} catch (JMSException ex) {
			ex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String args[]) throws IOException {
			
		String host = Messages.getMessageValue("crmQueue_host");
		String channel = Messages.getMessageValue("crmQueue_channel");
		int port = Integer.parseInt(Messages.getMessageValue("crmQueue_port"));
		String qmgr = Messages.getMessageValue("crmQueue_qmgrName");
		String queue = Messages.getMessageValue("crmQueue_reqPartyUpdateQName");
		
		MQQueueConnectionFactory qcf = null;
		QueueSession session = null;
		QueueConnection jmsCon = null;
		javax.jms.Queue requestQ = null;
		javax.jms.QueueReceiver queueReceiver = null;
		try {
			qcf = new MQQueueConnectionFactory();
			qcf.setHostName(host);
			qcf.setChannel(channel);
			qcf.setPort(port);
			qcf.setQueueManager(qmgr);
			qcf.setTransportType(1);
			logger.info("Creating a connection to " + host + "/" + qmgr + "/" + port);
			jmsCon = qcf.createQueueConnection();
			logger.info("Connection created \n");
			session = jmsCon.createQueueSession(false, 1);
			requestQ = session.createQueue(queue);
			
			 queueReceiver = session.createReceiver(requestQ);
			CRMListener queueListener = new CRMListener();
			logger.info("Before setting listener");
			queueReceiver.setMessageListener(queueListener);
			logger.info("After setting listener");
			
			jmsCon.start();
			logger.info("Waiting for the message");
			String currentState="STOP";
			
			
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

	}
	
	/*public static void main(String[] args) {
		
		new SBAStartListner().saveResponse(null);
	}
	*/
	public void saveResponse(TextMessage txtmsg) {

		Connection conn=null;
		String msg=null;
		String jmsMessageID=null;
		PreparedStatement pstmt=null;
		try {
			msg=txtmsg.getText();
			jmsMessageID=txtmsg.getJMSMessageID();
			
			String fileName=jmsMessageID.substring(3)+".xml";
			conn=DbConnection.getConnectionObject();
			String sql="call IOE.SPSAVE_CRMRESPONSE(?,?)";//Insert into IOE.tM6_NewOrder_Response(JMS_MESSAGEID,FILENAME) values(?,?)";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, jmsMessageID);
			pstmt.setString(2, fileName);
			
			
			saveFile(fileName,msg);
			//pstmt.setString(3, msg);
			
			pstmt.execute();
			
			
		} catch (Exception ex) {
			Utility.onEx_LOG_RET_NEW_EX(ex, "saveToDB", "CRMListener", "jmsMessageID:"+jmsMessageID+" Message String:"+msg, true, true); 
		}finally
		{
			try {
				DbConnection.closePreparedStatement(pstmt);
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				Utility.onEx_LOG_RET_NEW_EX(e, "saveToDB", "CRMListener", null, true, true);  
			}
		}
		
		
		
		
	}

	private void saveFile(String fileName, String msg) {
		
		File f=new File(Messages.getMessageValue("CRM_RESPONSE_PATH")+fileName);
		
		try {
			f.createNewFile();
			FileOutputStream outputStream=new FileOutputStream(f);
			byte[] data= msg.getBytes();
			outputStream.write(data);
			outputStream.close();
			
		} catch (Exception e) {
			Utility.onEx_LOG_RET_NEW_EX(e,"saveFile","CRMListener",null,true,true);
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
		} catch (IOException ex) {
			Utility.onEx_LOG_RET_NEW_EX(ex,"run","CRMListener",null,true,true);
		}
	}

}