package com.ibm.ioes.clep;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.QueueConnection;
import javax.jms.QueueSession;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Messages;
import com.ibm.ioes.utilities.Utility;
import com.ibm.mq.jms.MQQueueConnectionFactory;

import java.sql.CallableStatement;
import java.util.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

public class SendAndRecieveMessage implements MessageListener{

	/**
	 * @param args
	 */
	private static final Logger logger = Logger.getLogger(CLEPListener.class);
	final static String sql_get_Qproperties="{call IOE.SPCLEP_GETQPROPERTIES(?,?,?,?,?,?,?,?,?,?,?)}";
	final static String sql_get_usrnm_n_pswd="{call IOE.GETUSERNAME_PASSWORD(?,?,?,?,?)}";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//for(int i=0; i<7;i++)
		String msg="";
		CLEPXmlDto clepXmldto2=new CLEPXmlDto();
		clepXmldto2.setXmlData("  <NewOrder>	  <OrderType>RFBT</OrderType>      <OrderNo>1001061</OrderNo><!-- This would be Order No Generated at iB2B End-->	  <BillingTriggerDate>31-Aug-2012</BillingTriggerDate><!-- -->	  <LOCNo>01062012</LOCNo><!-- Blank in case of Disconnection-->	  <LOCDate>31-Aug-2012</LOCDate><!-- LOC Date can current date -->	  <LOCRecDate>31-Aug-2012</LOCRecDate><!-- LOC Date -->	  	    </NewOrder>");
		messageProducer_Test(clepXmldto2);
		//String str=replaceWord("<orderHeader><NewOrder><OrderType>SO</OrderType><OrderExtAttributes><OrderId>10001</OrderId><!-- This would be Unique Order id Sent by MPP--><OrderNo>12861</OrderNo><!-- This would be Order No Generated at iB2B End--><FileId>595</FileId><!-- This would be File Id of XML Sent By MPP to iB2B for Creating Order--><MessageId>414d5120514d454947533120202020204e7f171520384703</MessageId><Status>SUCCESS</Status></OrderExtAttributes><ServiceDetails><UniqueServiceId>40001</UniqueServiceId><ServiceId>13681</ServiceId><LogicalSI>9279</LogicalSI><LineItemInfo RequestLineItemId= '50001 ' ><ParentLineItemId>32684</ParentLineItemId><LineItemId FXSIID= 'OECI-421-32685 '>32685</LineItemId><BillableAccountNo LineItemId= '32685 '>BILLABLEACCOUNTID_32685</BillableAccountNo><ChargeInfoId RequestChargeId= '1010 '>32042</ChargeInfoId></LineItemInfo><LineItemInfo RequestLineItemId= '50002 ' ><ParentLineItemId>32684</ParentLineItemId><LineItemId FXSIID= 'OECI-421-32686 '>32686</LineItemId><BillableAccountNo LineItemId='32686'>BILLABLEACCOUNTID_32686</BillableAccountNo><ChargeInfoId RequestChargeId='1010'>32043</ChargeInfoId></LineItemInfo></ServiceDetails></NewOrder></orderHeader>","BILLABLEACCOUNTID_32685","19614485-1-1-1-1-1-1-2-0-0-0-1830115-0-0-0-0-0");
		//System.out.println(str);
	}
	public void onMessage(Message message) {
		
	}
	public static void messageConsumer() {		
			
		Connection conn=null;
		MQQueueConnectionFactory qcf = null;
		QueueSession session = null;
		//Session              session = null;
		QueueConnection jmsCon = null;
		javax.jms.Queue responseQ = null;
		javax.jms.QueueReceiver queueReciever = null;
		
		String host = null;
		String channel = null;
		int port = 0;
		String qmgr = null;
		String queueReq = null;
		String queueRes=null;
		try{
							
		queueRes="IB2B.MPP.ORD.RSP.01";
		qcf = new MQQueueConnectionFactory();
		qcf.setHostName("10.14.107.178");
		qcf.setChannel("SYSTEM.DEF.SVRCONN");
		qcf.setPort(5121);
		qcf.setQueueManager("QMEIGS1");
		qcf.setTransportType(1);
		
	
		jmsCon = qcf.createQueueConnection();
		Utility.SysErr("Queue Connection created \n");
		session = jmsCon.createQueueSession(false, session.AUTO_ACKNOWLEDGE);
		responseQ = session.createQueue(queueRes);
		
		queueReciever=session.createReceiver(responseQ);
				
		Utility.SysErr("jms Conn start >>>>>>>>>");
		jmsCon.start();
		
		Message txtmsg = queueReciever.receive();
		TextMessage msg = (TextMessage)txtmsg;
		
		Utility.SysErr("Reciving message end >>>>>>>>> "+ txtmsg.getJMSMessageID());
		Utility.SysErr("Receive Correlation Id:"+txtmsg.getJMSCorrelationID());
		Utility.SysErr("Read Message: " + msg.getText());
		
		session.close();
		jmsCon.close();
		}catch(Exception e){
			e.printStackTrace();
			try {
				session.close();
			} catch (JMSException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		}
	public static int messageProducer(CLEPXmlDto clepXmldto)
	{
		Connection conn=null;
		MQQueueConnectionFactory qcf = null;
		QueueSession session = null;
		QueueConnection jmsCon = null;
		javax.jms.Queue responseQ = null;
		javax.jms.QueueSender queueSender = null;
		
		String host = null;
		String channel = null;
		int port = 0;
		String qmgr = null;
		String queueReq = null;
		String queueRes=null;
		int sendStatus=-1;
		String username="";
		String password="";
		CallableStatement cs =null;
		CallableStatement csClepQProperties=null;
		try{
			
			CLEPUtility.SysErr("----- connection start for getting queue configuration >>>>>>>>>>>");
			conn=DbConnection.getConnectionObject();
			csClepQProperties= conn.prepareCall(sql_get_Qproperties);
			csClepQProperties.setString(1, "");
			csClepQProperties.setString(2, "");
			csClepQProperties.setString(3, "");
			csClepQProperties.setString(4, "");
			csClepQProperties.setString(5, "");
			csClepQProperties.setString(6, "");
			csClepQProperties.setString(7, "");
			csClepQProperties.setString(8, "");
			csClepQProperties.setString(9, "");
			csClepQProperties.setInt(10, 0);
			csClepQProperties.setString(11, "");
			csClepQProperties.execute();
			
			host=csClepQProperties.getString(1);
			channel=csClepQProperties.getString(2);
			port=Integer.parseInt(csClepQProperties.getString(3));
			qmgr=csClepQProperties.getString(4);
			queueReq=(csClepQProperties.getString(6));
			queueRes=(csClepQProperties.getString(7));
			
			qcf = new MQQueueConnectionFactory();
			qcf.setHostName(host);
			qcf.setChannel(channel);
			qcf.setPort(port);
			qcf.setQueueManager(qmgr);
			qcf.setTransportType(1);
			
			CLEPUtility.SysErr("----- Checking Username and Password -------- ");
			cs= conn.prepareCall(sql_get_usrnm_n_pswd);
			cs.setString(1, "");
			cs.setString(2, "");
			cs.setString(3, "");
			cs.setInt(4, 0);
			cs.setString(5, "");
			cs.execute();
			username=(cs.getString(1));
			//password=(cs.getString(2));			
			String keyname = Messages.getMessageValue("PASSWORD_ENCRYPT_KEY");
			password=Utility.getDecryptedPassword(keyname,"jmsPassword");		
			qcf = new MQQueueConnectionFactory();		
			qcf.setHostName(host);				
			qcf.setChannel(channel);		
			qcf.setPort(port);		
			qcf.setQueueManager(qmgr);
			qcf.setTransportType(1);
		
				
		CLEPUtility.SysErr("Creating a connection to " + host + "/" + qmgr + "/" + port);
		jmsCon = qcf.createQueueConnection(username,password);
		
		//jmsCon = qcf.createQueueConnection();
		CLEPUtility.SysErr("-------- Queue Connection created --------");	
		session = jmsCon.createQueueSession(false, 1);
		responseQ = session.createQueue(queueRes);
		
		queueSender=session.createSender(responseQ);		
			
		TextMessage message = session.createTextMessage(clepXmldto.getXmlData());
				
		CLEPUtility.SysErr("-------- Sending CorelationId is "+clepXmldto.getJmsMessageID());		
		message.setJMSCorrelationID(clepXmldto.getJmsMessageID());
		
		CLEPUtility.SysErr("-------- sending message start >>>>>>>>>");
		
			queueSender.send(message);		
			if(message.getJMSMessageID()!=null){
				
				CLEPUtility.SysErr("-------- sending message id: "+message.getJMSMessageID());
				
				clepXmldto.setSendMessageID(message.getJMSMessageID());
				clepXmldto.setSend_status(1);
				sendStatus=1;
			}else{
				sendStatus=-1;
			}
					
		CLEPUtility.SysErr("-------- jms Conn start >>>>>>>>>");
		jmsCon.start();
		CLEPUtility.SysErr("-------- sending message end ---------");	
		session.close();
		
		}catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
			DbConnection.closeCallableStatement(cs);
			DbConnection.closeCallableStatement(csClepQProperties);
			try {
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return sendStatus;
	}
	public static String messageProducer_Test(CLEPXmlDto clepXmldto)
	{
		Connection conn=null;
		MQQueueConnectionFactory qcf = null;
		QueueSession session = null;
		QueueConnection jmsCon = null;
		javax.jms.Queue responseQ = null;
		javax.jms.QueueSender queueSender = null;
		String sentMsgId="";
		String host = null;
		String channel = null;
		int port = 0;
		String qmgr = null;
		String queueReq = null;
		String queueRes=null;
		int sendStatus=-1;
		try{
			
		queueRes=clepXmldto.getClepQueue_reqQName();
		qcf = new MQQueueConnectionFactory();
		qcf.setHostName(clepXmldto.getClepQueue_host());
		qcf.setChannel(clepXmldto.getClepQueue_channel());
		qcf.setPort(clepXmldto.getClepQueue_port());
		qcf.setQueueManager(clepXmldto.getClepQueue_qmgrName());
			
		/*queueRes="MPP.IB2B.ORD.REQ.01";
		qcf = new MQQueueConnectionFactory();
		qcf.setHostName("10.14.107.178");
		qcf.setChannel("SYSTEM.DEF.SVRCONN");
		qcf.setPort(5121);
		qcf.setQueueManager("QMEIGS1");*/
		qcf.setTransportType(1);
		
		
		Utility.SysErr("Creating a connection to " + host + "/" + qmgr + "/" + port);
		jmsCon = qcf.createQueueConnection();
		Utility.SysErr("Queue Connection created \n");	
		session = jmsCon.createQueueSession(false, 1);
		responseQ = session.createQueue(queueRes);
		
		queueSender=session.createSender(responseQ);		
			
		TextMessage message = session.createTextMessage(clepXmldto.getXmlData());
		
		//message.setJMSReplyTo(responseQ);
		Utility.SysErr("Sending CorelationId is "+clepXmldto.getJmsMessageID());		
		message.setJMSCorrelationID(clepXmldto.getJmsMessageID());
		
		Utility.SysErr("sending message start >>>>>>>>>");
		
			queueSender.send(message);		
			if(message.getJMSMessageID()!=null){
				
				Utility.SysErr("sending message id: "+message.getJMSMessageID());
				sentMsgId=message.getJMSMessageID();
				clepXmldto.setSendMessageID(message.getJMSMessageID());
				clepXmldto.setSend_status(1);
				sendStatus=1;
			}else{
				sentMsgId="Message sending failed";
				sendStatus=-1;
			}
					
		Utility.SysErr("jms Conn start >>>>>>>>>");
		jmsCon.start();
		Utility.SysErr("sending message end >>>>>>>>>");	
		session.close();
		
		}catch(Exception e){
			sentMsgId=e.getMessage();
			e.printStackTrace();
		}
		return sentMsgId;
	}
	static String replaceWord(String original, String find, String replacement) {
	    int i = original.indexOf(find);
	    if (i < 0) {
	        return original;  // return original if 'find' is not in it.
	    }
	  
	    String partBefore = original.substring(0, i);
	    String partAfter  = original.substring(i + find.length());
	  
	    return partBefore + replacement + partAfter;
	}

}
