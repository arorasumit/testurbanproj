package com.ibm.ioes.ei;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;

import com.ibm.ioes.utilities.ApplicationFlags;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Messages;
import com.ibm.ioes.utilities.Utility;
import com.ibm.mq.jms.MQQueueConnectionFactory;

/*
 * Created on Jan 31, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author Rishi
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MessageHandler {

	//	private QueueConnectionFactory qcf = null;
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

		} catch (JMSException je) {
			System.out.println(
				"Couldn't create connection to "
					+ host
					+ "/"
					+ channel
					+ "/"
					+ qmgrName
					+ "/"
					+ port);
			//	  		je.printStackTrace();
			System.out.println("*****************" + je.getLinkedException());

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

	public String requestResponse(
		String msg,
		String reqQName,
		String respQName,
		String isHeaderRequired,
		String orderObjectType,
		String orderType,
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
			
			//Start By Saurabh
			if(isHeaderRequired.equalsIgnoreCase("1"))
			{
				//properties.put("orderObjectType", "IB2B");
				properties.put("orderObjectType", orderObjectType);
				//properties.put("eventVerb", "created");
				properties.put("eventVerb", orderType);//for change order
				properties.put("sourceApplication", "IB2B");
				properties.put("objectName", "AESOrder");
			}
			//End By Saurabh
			
			sender = session.createSender(requestQ);
			// Create a message to send to the queue...
			TextMessage message = session.createTextMessage(msg);

			jmsType = "mcd://xmlns";

			message.setJMSType(jmsType);
			message.setJMSReplyTo(responseQ);
			message.setJMSExpiration(timeOut);
			message.setJMSDeliveryMode(DeliveryMode.PERSISTENT);
			setMessageHeader(message, properties);
			sender.send(message);
			// store the message id 
			messageID = message.getJMSMessageID();
			
			System.out.println("MessageID : "+messageID);
			//  Create a receiver
			if (messageID == null) {
				System.out.println("MessageID is NULL");

			}
//			String selector = "JMSCorrelationID = '" + messageID + "'";
//			QueueReceiver queueReceiver =
//				session.createReceiver(responseQ, selector);
//			inMessage = queueReceiver.receive(timeOut);
//			sender.close();
//			queueReceiver.close();
			// Close the connection (close calls will cascade to other objects)
			closeConnection();
		} catch (JMSException je) {
			System.out.println("\n Connection broken");
		} finally {
			// Ensure that the Connection always gets closed
			closeConnection();
		}
		return messageID;
	}
	
//	By Saurabh For Setting Header in XML
	private void setMessageHeader(Message msg, Map properties) throws JMSException
	{
		if (properties != null)
		{
			for (Iterator itr = properties.keySet().iterator(); itr.hasNext();)
			{
				String key = (String)itr.next();
				Object value = properties.get(key);
				if (value instanceof String)
					msg.setStringProperty(key, (String)value);
				else if (value instanceof Long)
					msg.setLongProperty(key, ((Long)value).longValue());
				else if (value instanceof Integer)
					msg.setIntProperty(key, ((Integer)value).intValue());
				else if (value instanceof Boolean)
					msg.setBooleanProperty(key, ((Boolean)value).booleanValue());
				else 
					msg.setObjectProperty(key, value);
			}
		}
	}
	//End By Saurabh
	
	public Message receiveResponse(
			String msg,
			String reqQName,
			String respQName,
			long timeOut)
			throws JMSException, Exception {
			Message outMessage = null;
			String messageID = null;
			QueueReceiver receiver= null;
			String jmsType = null;
			try {
				System.out.println("in requestResponse");
				requestQ = session.createQueue(reqQName);
				responseQ = session.createQueue(respQName);

				//receiver = session.createReceiver(requestQ);
				receiver = session.createReceiver(responseQ);
				outMessage=receiver.receiveNoWait();
				
				System.err.println(outMessage);
				
				// Create a message to send to the queue...
				/*TextMessage message = session.createTextMessage(msg);

				jmsType = "mcd://xmlns";*/

				/*message.setJMSType(jmsType);
				message.setJMSReplyTo(responseQ);
				message.setJMSExpiration(timeOut);
				message.setJMSDeliveryMode(DeliveryMode.PERSISTENT);
				sender.send(message);
				// store the message id 
				messageID = message.getJMSMessageID();
				
				System.out.println("MessageID id NULL"+messageID);
				//  Create a receiver
				if (messageID == null) {
					System.out.println("MessageID id NULL");

				}*/
//				String selector = "JMSCorrelationID = '" + messageID + "'";
//				QueueReceiver queueReceiver =
//					session.createReceiver(responseQ, selector);
//				inMessage = queueReceiver.receive(timeOut);
//				sender.close();
//				queueReceiver.close();
				// Close the connection (close calls will cascade to other objects)
				closeConnection();
			} catch (JMSException je) {
				System.out.println("\n Connection broken");
			} finally {
				// Ensure that the Connection always gets closed
				closeConnection();
			}
			return outMessage;
		}
	private void closeConnection() {
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
