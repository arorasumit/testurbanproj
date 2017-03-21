package com.ibm.ioes.ei;
//Tag Name Resource Name  Date		CSR No			Description
//[001]	 Kalpana	11-January-2014			New File to send request to SCM for third party
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;

import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Messages;
import com.ibm.mq.jms.MQQueueConnectionFactory;

public class MessageHandlerSCM extends MessageHandler{
	
	private QueueSession session = null;
	private QueueConnection jmsCon = null;
	private Queue requestQ = null;
	private Queue responseQ = null;
	private long timeOut = 0;
	private MQQueueConnectionFactory qcf;
	final static String sql_get_usrnm_n_pswd="{call IOE.GETUSERNAME_PASSWORD(?,?,?,?,?)}";
	
	public void initJMSConnection(
			String host,
			String channel,
			int port,
			String qmgrName)
			throws Exception, JMSException {
			Connection conn=null;
			String Username=null;
			String Password=null;
			CallableStatement cs =null;
			try {
				conn=DbConnection.getConnectionObject();
				qcf = new MQQueueConnectionFactory();
				qcf.setHostName(host);
				qcf.setChannel(channel);
				qcf.setPort(port);
				qcf.setQueueManager(qmgrName);
				qcf.setTransportType(1);

				
				//Start Saurabh for getting username and pasword from database
				cs= conn.prepareCall(sql_get_usrnm_n_pswd);
				cs.setString(1, "");
				cs.setString(2, "");
				cs.setString(3, "");
				cs.setInt(4, 0);
				cs.setString(5, "");
				cs.execute();
				Username=(cs.getString(1));
				
				//Password=(cs.getString(2));
				//End Saurabh
				String keyname = Messages.getMessageValue("PASSWORD_ENCRYPT_KEY");
				//Password=Utility.getDecryptedPassword(keyname,"jmsPassword");
				
				//System.out.println("Creating a connection to "+host+"/"+channel+"/"+qmgrName+"/"+port);
				//jmsCon = qcf.createQueueConnection("ib2busr","feb@2011");
				
				//jmsCon = qcf.createQueueConnection(Username,Password);
				jmsCon = qcf.createQueueConnection();
				//System.out.println("Starting a connection");
				jmsCon.start();
				System.out.println("Connection created \n");
				session =
					jmsCon.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

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



	
	public String requestResponseSCM(
			String msg,
			String reqQName,
			String respQName,
			long timeOut)
			throws JMSException, Exception {
			Message inMessage = null;
			String messageID = null;
			QueueSender sender = null;
			String jmsType = null;
			Map<String, String> properties = new HashMap();
			try {
				System.out.println("in requestResponse");
				requestQ = session.createQueue(reqQName);
				responseQ = session.createQueue(respQName);
				
				
				sender = session.createSender(requestQ);
				// Create a message to send to the queue...
				TextMessage message = session.createTextMessage(msg);

				jmsType = "mcd://xmlns";

				message.setJMSType(jmsType);
				message.setJMSReplyTo(responseQ);
				message.setJMSExpiration(timeOut);
				message.setJMSDeliveryMode(DeliveryMode.PERSISTENT);
				//setMessageHeader(message, properties);
				sender.send(message);
				// store the message id 
				messageID = message.getJMSMessageID();
				
				System.out.println("MessageID : "+messageID);
				//  Create a receiver
				if (messageID == null) {
					System.out.println("MessageID is NULL");

				}
				
			} catch (JMSException je) {
				messageID="Connection broken";
				System.out.println("\n Connection broken");
			} finally {
				// Ensure that the Connection always gets closed
				
			}
			return messageID;
		}

	public void closeConnection() {
		try {
			//			  System.out.println("closeConnection");
			if (session != null)
				session.close();
			if (jmsCon != null)
				jmsCon.close();
			jmsCon = null;
		} catch (JMSException e) {
			System.out.println("closeConnection Exception" + e);
			e.printStackTrace();
		}
	}




	/**
	 * @return
	 */
	public QueueConnectionFactory getQcf() {
		return qcf;
	}

	/**
	 * @return
	 */
	public Queue getRequestQ() {
		return requestQ;
	}

	/**
	 * @return
	 */
	public Queue getResponseQ() {
		return responseQ;
	}

	/**
	 * @return
	 */
	public QueueSession getSession() {
		return session;
	}

	/**
	 * @return
	 */
	public long getTimeOut() {
		return timeOut;
	}

	/**
	 * @param factory
	 */
	public void setQcf(MQQueueConnectionFactory factory) {
		qcf = factory;
	}

	/**
	 * @param queue
	 */
	public void setRequestQ(Queue queue) {
		requestQ = queue;
	}

	/**
	 * @param queue
	 */
	public void setResponseQ(Queue queue) {
		responseQ = queue;
	}

	/**
	 * @param session
	 */
	public void setSession(QueueSession session) {
		this.session = session;
	}

	/**
	 * @param l
	 */
	public void setTimeOut(long l) {
		timeOut = l;
	}
	
}
