package com.ibm.ioes.ei.m6;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.jms.JMSException;
import com.ibm.ioes.ei.MessageHandler;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Messages;
import com.ibm.ioes.utilities.Utility;

public class SendCancellationXmlToM6 {
	
	private static final int Fetch_Status_DataFound=1;
	private static final int Fetch_Status_DataNotFound=2;
	private static final int Fetch_Status_OrderLine_NotFound=3;
	
	private static final int XML_Sent_Failure=2;
	private static final int XML_Sent_Success=3;
	private static final int XML_Sent_OrderLine_Not_Found=4;
	
	private static long timeOut=Long.parseLong(Messages.getMessageValue("m6Queue_timeOut")); 
	
	final static String sql_UpdateStatus="{call IOE.SPUPDATEM6STATUSONSEND_CANCELLEDLINE(?,?,?)}";
	final static String sql_getNextM6_LineItem_XML="{call IOE.GETNEXTM6_CANCELLEDLINE_XML(?,?,?,?,?)}";
	final static String sql_get_properties="{call IOE.GETPROPERTIES(?,?,?,?,?,?,?,?,?,?,?,?)}";
	
	public void sendToCancellationM6Job()
	{
		AppConstants.IOES_LOGGER.error("Info -  In sendToCancellationM6Job for cancelled line");
		System.err.println("in sendToCancellationM6Job for cancelled line"); 
		
		int fetchLineXMLStatus=0;
		FetchM6XMLDto m6dto =new FetchM6XMLDto();
		Connection conn=null;
		try
		{
			conn=DbConnection.getConnectionObject();
			do
			{
				fetchLineXMLStatus=fetchNextLineXMLData(conn,m6dto);
				if(fetchLineXMLStatus==Fetch_Status_DataFound)
				{
					sendXML(m6dto);
					int sendStatus=m6dto.getSend_status();
					if(sendStatus==1)
					{
						setStatus(conn,m6dto,XML_Sent_Success);//success
					}
					else
					{
						setStatus(conn,m6dto,XML_Sent_Failure);//failure
					}
				}
				else if(fetchLineXMLStatus==Fetch_Status_OrderLine_NotFound)
				{
					setStatus(conn,m6dto,XML_Sent_OrderLine_Not_Found);//line not found
				}
			}while(fetchLineXMLStatus==Fetch_Status_DataFound || fetchLineXMLStatus==Fetch_Status_OrderLine_NotFound );
		}
		catch(Exception ex)
		{
			Utility.onEx_LOG_RET_NEW_EX(ex, "sendToM6Job", "sendToM6", null, true, true);  
		}
		finally{
			try {
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Utility.onEx_LOG_RET_NEW_EX(e, "sendToM6Job", "sendToM6", null, true, true);  
			}
		}
	}
	 
	private void setStatus(Connection conn, FetchM6XMLDto m6dto, int sent_Success) 
	{
		try {
			PreparedStatement pstmt=conn.prepareStatement(sql_UpdateStatus);
			pstmt.setShort(1, (short)sent_Success);
			pstmt.setString(2, m6dto.getMessageID());
			pstmt.setLong(3, m6dto.getXmlId());
			pstmt.execute();  
			
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			Utility.onEx_LOG_RET_NEW_EX(ex, "setStatus", "sendToM6", null, true, true);  
		}
		
	}

	private void sendXML(FetchM6XMLDto m6dto)
	{
		int sendStatus=0;
		MessageHandler handler=new MessageHandler();
		String messageID=null;
		try {
			AppConstants.IOES_LOGGER.error("Info -  In sendXML ");
			
			handler.initJMSConnection(m6dto.getM6Queue_host(), m6dto.getM6Queue_channel(), Integer.parseInt(m6dto.getM6Queue_port()), m6dto.getM6Queue_qmgrName());
			
			AppConstants.IOES_LOGGER.error("Info - Channel : "+m6dto.getM6Queue_channel());
			AppConstants.IOES_LOGGER.error("Info - Port : "+m6dto.getM6Queue_port());
			AppConstants.IOES_LOGGER.error("Info - Q Manager : "+m6dto.getM6Queue_qmgrName());
			AppConstants.IOES_LOGGER.error("Rquest Queue Name: "+m6dto.getM6Queue_reqQName());
			AppConstants.IOES_LOGGER.error("Info - Sending Xml to M6 : "+m6dto.getXml());
			
			messageID=handler.requestResponse(m6dto.getXml(), m6dto.getM6Queue_reqQName(), m6dto.getM6Queue_respQName(), m6dto.getIsHeaderRequired(), m6dto.getOrderObjectType(), m6dto.getOrderType(),timeOut);
			sendStatus=1;
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			AppConstants.IOES_LOGGER.error("Info -  In sendXML :In JMSException ");
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			AppConstants.IOES_LOGGER.error("Info -  In sendXML :In Throwable Exception ");
		}finally{
			AppConstants.IOES_LOGGER.error("Info -  In sendXML : finally ");
		}
		
		m6dto.setSend_status(sendStatus);
		m6dto.setMessageID(messageID);
	}

	private int fetchNextLineXMLData(Connection conn, FetchM6XMLDto m6dto) throws Exception 
	{
		int returnStatus=0;
		CallableStatement cstmt=null;
		CallableStatement cs=null;
		try {
		    AppConstants.IOES_LOGGER.error("Info -  In fetchNextLineXMLData ");
		    cstmt=conn.prepareCall(sql_getNextM6_LineItem_XML); 
			cstmt.setString(1, "");
			cstmt.setLong(2, 0);
			cstmt.setString(3, null); 
			cstmt.setString(4, null); 
			cstmt.setString(5, null); 
			cstmt.execute();
			
			String xml=cstmt.getString(1);
			Long xmlId=cstmt.getLong(2);
			String msg= cstmt.getString(3);
			String msgCode=cstmt.getString(4);
			String orderLineFlag=cstmt.getString(5);
			m6dto.setIsHeaderRequired("0");
			m6dto.setOrderObjectType("");
			m6dto.setOrderType("");
			
			//Start Saurabh for properties file into database
			cs = conn.prepareCall(sql_get_properties);
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
			cs.setString(12, "");
			cs.execute();
			
			m6dto.setM6Queue_host(cs.getString(1));
			m6dto.setM6Queue_channel(cs.getString(2));
			m6dto.setM6Queue_port(cs.getString(3));
			m6dto.setM6Queue_qmgrName(cs.getString(4));
			m6dto.setM6Queue_respQName(cs.getString(7));
			m6dto.setM6_Response_Path(cs.getString(8));
			m6dto.setM6Queue_reqQName(cs.getString(12));
			
			if("1".equals(msgCode)) 
			{
				if(orderLineFlag.equalsIgnoreCase("1"))
				{
					m6dto.setXml(xml);
					m6dto.setXmlId(xmlId);
					returnStatus=Fetch_Status_OrderLine_NotFound;
				}
				else
				{
					m6dto.setXml(xml);
					m6dto.setXmlId(xmlId);
					returnStatus=Fetch_Status_DataFound;
				}
			}
			else
			{
				returnStatus=Fetch_Status_DataNotFound;	
				AppConstants.IOES_LOGGER.error("Info -  In fetchNextLineXMLData. Data Not Found. Msg = "+msg);
			}
			
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			throw Utility.onEx_LOG_RET_NEW_EX(ex, "fetchNextXMLData", "sendToM6", null, true, true); 
		}
		finally
		{
			DbConnection.closeCallableStatement(cs);
			DbConnection.closeCallableStatement(cstmt);
		}
		return returnStatus;
	}
	
	public static void main(String[] args) {
		new SendCancellationXmlToM6().sendToCancellationM6Job();
	}
}