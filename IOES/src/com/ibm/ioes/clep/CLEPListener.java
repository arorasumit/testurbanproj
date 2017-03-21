//Tag Name       Resource Name              Date		         CSR No			Description
//[001]          Santosh Srivastava         08 Jan, 2014                        updated code for ServetContext on 
package com.ibm.ioes.clep;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.QueueConnection;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionServlet;

import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Messages;
import com.ibm.ioes.utilities.Utility;
import com.ibm.mq.jms.MQQueueConnectionFactory;




public class CLEPListener implements MessageListener  /*,Runnable*/{
	private static final Logger logger = Logger.getLogger(CLEPListener.class);
	public static Hashtable<String, String> queueConFigureList = new Hashtable<String, String>();
	public static String processFlag=null;
	public static ActionServlet actionServlet =  new ActionServlet();
	//public static int orderCreationStatus=1;
	//Getting queue configuration from database
	final static String sql_get_Qproperties="{call IOE.SPCLEP_GETQPROPERTIES(?,?,?,?,?,?,?,?,?,?,?)}";
	final static String sql_get_usrnm_n_pswd="{call IOE.GETUSERNAME_PASSWORD(?,?,?,?,?)}";
	
	public CLEPListener() {
	}

	public void onMessage(Message message) {
		
		//ApplicationCLEPLock.ReadMessageFromQueueLock.lock();
		
		CLEPUtility.SysErr("In CLEPListener : on Message called at "+new Date());	
		
		TextMessage txtmsg =null;
		String inmsg=null;		
		
			
						
		try {		
			txtmsg=(TextMessage) message;	
			inmsg = txtmsg.getText();
			
			CLEPUtility.SysErr("saving message start >>>>>>>>>>>");
				String jmsMsgId=txtmsg.getJMSMessageID();
				String fileName=saveRequestMessage(txtmsg);
			CLEPUtility.SysErr("---------- End-Saving message -----------");
		
			CLEPUtility.SysErr("InComing Message >>>>>>>>>");
			CLEPUtility.SysErr(inmsg);
					
			CLEPUtility.SysErr("Order creation process starting >>>>>>>>>>>>>");
			if(Utility.switchOn(AppConstants.SWITCH_JOB_CLEP_XML_PROCESS))
			{
								
				
					//jmsCon.stop();
				
					CLEPXmlDto clepXMLDto=new CLEPXmlDto();
					clepXMLDto.setJmsMessageID(jmsMsgId);	
					//clepXMLDto.setJMSConnection(jmsCon);
					processXMLFileForOrderCreate(fileName,clepXMLDto);				
			}    
				CLEPUtility.SysErr("----------- Order creation process end ------------");
			//}
			
		} catch (JMSException ex) {
			ex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//ApplicationCLEPLock.ReadMessageFromQueueLock.unlock();
		}
	}
	
	//[001] Start here change main method argument to 	ServletContext
	public static void main(ServletContext servletContext) throws IOException {
					
		Connection conn=null;
		MQQueueConnectionFactory qcf = null;
		QueueSession session = null;
		QueueConnection jmsCon = null;
		javax.jms.Queue requestQ = null;
		javax.jms.QueueReceiver queueReceiver = null;
		CallableStatement cs=null;
		CallableStatement csClepQProperties=null;
		String host = null;
		String channel = null;
		int port = 0;
		String qmgr = null;
		String queueReq = null;
		String queueRes=null;
		String username="";
		String password="";
		try {
			CLEPUtility.SysErr("----- connection start for getting queue configuration >>>>>>>>>>>");
			conn=DbConnection.getConnectionObject();
			csClepQProperties = conn.prepareCall(sql_get_Qproperties);
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
						
			queueConFigureList.put(AppConstants.CLEPQHOST, host);
			queueConFigureList.put(AppConstants.CLEPQCHANNEL, channel);
			queueConFigureList.put(AppConstants.CLEPQPORT, csClepQProperties.getString(3));
			queueConFigureList.put(AppConstants.CLEPQMNGR, qmgr);
			queueConFigureList.put(AppConstants.CLEPQREQ, queueReq);
			queueConFigureList.put(AppConstants.CLEPQRESP, queueRes);
			
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
			CLEPUtility.SysErr("Creating a connection to " + host + "/" + qmgr + "/" + port);	
			
			jmsCon = qcf.createQueueConnection(username,password);
			
			//jmsCon = qcf.createQueueConnection();
			CLEPUtility.SysErr("Connection created \n");
									
			session = jmsCon.createQueueSession(false,1);			
			requestQ = session.createQueue(queueConFigureList.get(AppConstants.CLEPQREQ));
			
			queueReceiver = session.createReceiver(requestQ);
			CLEPListener queueListener = new CLEPListener();
			CLEPUtility.SysErr("Before setting listener");								
			queueReceiver.setMessageListener(queueListener);
			CLEPUtility.SysErr("After setting listener");			
									
			CLEPUtility.SysErr("Waiting for the message");			
			
			//[001] Use servletContext here			
			servletContext.setAttribute(AppConstants.CLEP_REQUEST_QUEUE_CONNECTION, jmsCon);
			servletContext.setAttribute(AppConstants.CLEP_LISTENER_STATE, "START");
			//[001] End here
			jmsCon.start();			
			
		} catch (JMSException je) {
			je.printStackTrace();
			CLEPUtility.SysErr("Couldn't create connection to "
					+ host
					+ "/"
					+ qmgr
					+ "/"
					+ port);
			CLEPUtility.SysErr(je.getMessage());			
		} catch (Exception ex) {
			ex.printStackTrace();
		}finally{
			try{
				DbConnection.closeCallableStatement(csClepQProperties);
				DbConnection.closeCallableStatement(cs);
				DbConnection.freeConnection(conn);
			}catch(Exception e){
				Utility.onEx_LOG_RET_NEW_EX(e, "main", "CLEPListener", null, true, true);  
			}
		}

	}
		
	public String saveRequestMessage(TextMessage txtmsg) {

		Connection conn=null;
		String msg=null;
		String jmsMessageID=null;
		String fileName=null;
		try {
			msg=txtmsg.getText();
			jmsMessageID=txtmsg.getJMSMessageID();
			
			fileName=jmsMessageID.substring(3)+".xml";			
												
			saveFile(fileName,msg);			
									
			
		}catch (Exception ex) {
			Utility.onEx_LOG_RET_NEW_EX(ex, "saveRequestMessage", "CLEPListener", "jmsMessageID:"+jmsMessageID+" Message String:"+msg, true, true);
			CLEPUtility.SysErr("----- Exception:"+ex.getMessage()+" -----------------");
		}
				
		return fileName;		
	}

	private void saveFile(String fileName, String msg) {
		
		CLEPUtility.SysErr("Incoming Message is saving >>>>>>>>>>>>");				
		try {
			File f=new File(Utility.getAppConfigValue("CLEP_RESPONSE_PATH")+fileName);
			f.createNewFile();
			FileOutputStream outputStream=new FileOutputStream(f);
			byte[] data= msg.getBytes();
			outputStream.write(data);
			outputStream.close();
			CLEPUtility.SysErr("------------- Incoming message saving end --------------");
		} catch (Exception e) {
			Utility.onEx_LOG_RET_NEW_EX(e,"saveFile","CLEPListener",null,true,true);
		}
				
	}	

	public static  void processXMLFileForOrderCreate(String fileName,CLEPXmlDto clepXmlDto){
		
		CLEPUtility.SysErr("---------In processXMLFileForOrderCreate in CLEPListener class--------------\n");	

		Connection conn=null;
		String host=null;
		String channel=null;
		int port=0;
		String qmgr=null;
		String reqQueue=null;
		String resQueue=null;
		try{						
			conn=DbConnection.getConnectionObject();
			host=queueConFigureList.get(AppConstants.CLEPQHOST);
			channel=queueConFigureList.get(AppConstants.CLEPQCHANNEL);
			port=Integer.parseInt(queueConFigureList.get(AppConstants.CLEPQPORT));
			qmgr=queueConFigureList.get(AppConstants.CLEPQMNGR);
			reqQueue=queueConFigureList.get(AppConstants.CLEPQREQ);
			resQueue=queueConFigureList.get(AppConstants.CLEPQRESP);					
			
			clepXmlDto.setClepQueue_host(host);
			clepXmlDto.setClepQueue_channel(channel);
			clepXmlDto.setClepQueue_port(port);
			clepXmlDto.setClepQueue_qmgrName(qmgr);
			clepXmlDto.setClepQueue_reqQName(reqQueue);
			clepXmlDto.setClepQueue_respQName(resQueue);
									
			ParseXMLforCLEP.fetchNextXMLDataNewOrder(fileName,clepXmlDto);			
			
		}catch(Exception exp){
			Utility.onEx_LOG_RET_NEW_EX(exp,"processXMLFileForOrderCreate","CLEPListener",null,true,true);
		}finally{
			try{
				DbConnection.freeConnection(conn);
			}catch(Exception e){
				
			}
		}
	}	
}