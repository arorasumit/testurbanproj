//Tag Name       Resource Name              Date		         CSR No			Description
//[001]          Santosh Srivastava         08 Jan, 2014                        updated code for ServetContext on 
// [002]			Shubhranshu Srivastava Modifications for Multi threading support 
package com.ibm.ioes.ei.m6;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.QueueConnection;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionServlet;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Messages;
import com.ibm.ioes.utilities.Utility;
import com.ibm.mq.jms.MQQueueConnectionFactory;



// Referenced classes of package com.ibm:
//            SendMTHTTP

public class M6Listener implements MessageListener ,Runnable{
	private static final Logger logger = Logger.getLogger(M6Listener.class);
	final static String sql_get_properties="{call IOE.GETPROPERTIES(?,?,?,?,?,?,?,?,?,?,?)}";
	
	public M6Listener() {
	}

	public void onMessage(Message message) {
		System.err.println("M6Listener : on Message called at "+new Date());
		logger.info("M6Listener:In the onMessage()========");
		try {
			
			TextMessage txtmsg = (TextMessage) message;
			String inmsg = txtmsg.getText();
			Utility.LOG(true, true, "M6Listener :saving message ...");
			saveResponse(txtmsg);
			Utility.LOG(true, true, "M6Listener :End-Saving message .");
			//System.err.println("In the onMessage()========  : "+inmsg);
			//save inmsg to db
			if(!Utility.switchOn(AppConstants.SWITCH_JOB_M6_XML_PROCESS))
			{
				return;
			}
//			run job
			//Utility.LOG(true, true, "M6Listener :Calling for processing of M6 Received XMl .");
			try
			{
	/*		new ProcessResponseXML().processResponseJob();*/
			//new ProcessResponseXML().processM6Response(); // [002] 21-01-2016
			}
			catch(Exception ex)
			{
				Utility.onEx_LOG_RET_NEW_EX(ex,"onMessage","M6Listener","exception while processing xml",true,true);
			}
			//Utility.LOG(true, true, "M6Listener :End : Calling for processing of M6 Received XMl .");
			
		} catch (JMSException ex) {
			ex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//[001] Start here change main method argument to 	ServletContext
	public static void main(ServletContext servletContext) throws IOException {
			
		//String host = Messages.getMessageValue("m6Queue_host");
		//String channel = Messages.getMessageValue("m6Queue_channel");
		//int port = Integer.parseInt(Messages.getMessageValue("m6Queue_port"));
		//String qmgr = Messages.getMessageValue("m6Queue_qmgrName");
		//String queue = Messages.getMessageValue("m6Queue_respQName");
		String host=null;
		String channel=null;
		int port=0;
		String qmgr=null;
		String queue=null;
		
		MQQueueConnectionFactory qcf = null;
		QueueSession session = null;
		QueueConnection jmsCon = null;
		javax.jms.Queue requestQ = null;
		javax.jms.QueueReceiver queueReceiver = null;
		Connection conn=null;
		CallableStatement cs=null;
		
		try {
			
			conn=DbConnection.getConnectionObject();
			 cs= conn.prepareCall(sql_get_properties);
			cs.setString(1, "");
			cs.setString(2, "");
			cs.setString(3, "");
			cs.setString(4, "");
			cs.setString(5, "");
			cs.setString(6, "");
			cs.setString(7, "");
			cs.setString(8, "");
			cs.setString(9, "");
			cs.setInt(10, 0);
			cs.setString(11, "");
			cs.execute();
			host=cs.getString(1);
			channel=cs.getString(2);
			port=Integer.parseInt(cs.getString(3));
			qmgr=cs.getString(4);
			queue=(cs.getString(7));
			
			
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
			M6Listener queueListener = new M6Listener();
			logger.info("Before setting listener");
			queueReceiver.setMessageListener(queueListener);
			logger.info("After setting listener");

			//[001] Use servletContext here
			servletContext.setAttribute(AppConstants.M6_REQUEST_QUEUE_CONNECTION, jmsCon);
			servletContext.setAttribute(AppConstants.M6_LISTENER_STATE, "START");
			//[001] End here
			
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
		finally
		{
			try {
				DbConnection.closeCallableStatement(cs);
				DbConnection.freeConnection(conn);
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
			
			
			String eventTypeId=getEventTypeId(msg);
			
			conn=DbConnection.getConnectionObject();
			String sql="call IOE.SPSAVE_M6RESPONSE(?,?,?)";//Insert into IOE.tM6_NewOrder_Response(JMS_MESSAGEID,FILENAME) values(?,?)";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, jmsMessageID);
			pstmt.setString(2, fileName);
			pstmt.setString(3, eventTypeId);
			
			
			saveFile(fileName,msg);
			//pstmt.setString(3, msg);
			
			pstmt.execute();
			
			
		} catch (Exception ex) {
			Utility.onEx_LOG_RET_NEW_EX(ex, "saveToDB", "M6Listener", "jmsMessageID:"+jmsMessageID+" Message String:"+msg, true, true); 
		}finally
		{
			try {
				DbConnection.closePreparedStatement(pstmt);
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				Utility.onEx_LOG_RET_NEW_EX(e, "saveToDB", "M6Listener", null, true, true);  
			}
		}
		
		
		
		
	}

	private String getEventTypeId(String msg ) {
		// TODO Auto-generated method stub
		//File file = new File(Messages.getMessageValue("M6_RESPONSE_PATH")+fileName);
		String eventTypeIdStr = null;
		try{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		//Document doc = db.parse(new InputSource(new ByteArrayInputStream(msg.getBytes("utf-8")))); 
		
		Document doc = db.parse(new InputSource(new StringReader(msg)));
		
		doc.getDocumentElement().normalize();
		
		NodeList nodeList = doc.getElementsByTagName("OFEvent");
		Node fstNode = nodeList.item(0);
		if (fstNode.getNodeType() == Node.ELEMENT_NODE){
		Element fstElmnt = (Element) fstNode;
		
		NodeList eventTypeIdElmntLst = fstElmnt.getElementsByTagName("eventTypeId");
	    Element eventTypeIdElmnt = (Element) eventTypeIdElmntLst.item(0);
	    NodeList eventTypeId = eventTypeIdElmnt.getChildNodes();
	    eventTypeIdStr = ((Node) eventTypeId.item(0)).getNodeValue().trim();
	      
	      
		}
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return eventTypeIdStr;
	}

	private void saveFile(String fileName, String msg) {
		
		File f=new File(Messages.getMessageValue("M6_RESPONSE_PATH")+fileName);
		
		try {
			f.createNewFile();
			FileOutputStream outputStream=new FileOutputStream(f);
			byte[] data= msg.getBytes();
			outputStream.write(data);
			outputStream.close();
			
		} catch (Exception e) {
			Utility.onEx_LOG_RET_NEW_EX(e,"saveFile","M6Listener",null,true,true);
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
			Utility.onEx_LOG_RET_NEW_EX(ex,"run","M6Listener",null,true,true);
		}
	}

}