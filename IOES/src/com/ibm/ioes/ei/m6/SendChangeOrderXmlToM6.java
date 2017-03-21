package com.ibm.ioes.ei.m6;

import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.jms.JMSException;

import com.ibm.ioes.dao.ViewOrderDao;
import com.ibm.ioes.ei.MessageHandler;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Messages;
import com.ibm.ioes.utilities.Utility;

public class SendChangeOrderXmlToM6 {
	

	
	private static final int Fetch_Status_DataFound=1;
	private static final int Fetch_Status_DataNotFound=2;
	private static final int Fetch_Status_OrderLine_NotFound=3;
	
	private static final int XML_Sent_Success=3;
	private static final int XML_Sent_Failure=2;
	private static final int XML_Sent_OrderLine_Not_Found=4;
	
	//private static final String host=Messages.getMessageValue("m6Queue_host"); 
	//private static final String channel=Messages.getMessageValue("m6Queue_channel"); 
	//private static final int port=Integer.parseInt(Messages.getMessageValue("m6Queue_port")); 
	//private static final String qmgrName=Messages.getMessageValue("m6Queue_qmgrName"); 
	
	//private static String reqQName=Messages.getMessageValue("m6Queue_changeReqQName"); 
	//private static String respQName=Messages.getMessageValue("m6Queue_respQName"); 
	private static long timeOut=Long.parseLong(Messages.getMessageValue("m6Queue_timeOut")); 
	
	
	final static String sql_UpdateStatus="{call IOE.spUpdateM6StatusOnSend(?,?,?)}";
	//final static String sql_getNextM6_NewOrder_XML="{call IOE.getNextM6_NewOrder_XML_CLOB(?,?,?,?)}";
	final static String sql_getNextM6_ChangeOrder_XML="{call IOE.GETNEXTM6_CHANGEORDER_XML_NONMPLS(?,?,?,?,?,?,?,?)}";
	final static String sql_get_properties="{call IOE.GETPROPERTIES(?,?,?,?,?,?,?,?,?,?,?)}";
	
	public void sendToChangeOrderM6Job()
	{
		AppConstants.IOES_LOGGER.error("Info -  In sendToChangeOrderM6Job ");
		System.err.println("in sendToChangeOrderM6Job"); 
		
		int fetchChangeOrderXMLStatus=0;
		FetchM6XMLDto m6dto =new FetchM6XMLDto();
		Connection conn=null;
		ResultSet rs=null;
		Statement stmt=null;
		try
		{
			conn=DbConnection.getConnectionObject();
			
			do
			{
				
				fetchChangeOrderXMLStatus=fetchNextChangeOrderXMLData(conn,m6dto);
				if(fetchChangeOrderXMLStatus==Fetch_Status_DataFound)
				{
					sendChangeOrderXML(m6dto);
					int sendStatus=m6dto.getSend_status();
					if(sendStatus==1)
					{
						//success
						setStatus(conn,m6dto,XML_Sent_Success);
					}else
					{
						setStatus(conn,m6dto,XML_Sent_Failure);	
					}
				}
				else if(fetchChangeOrderXMLStatus==Fetch_Status_OrderLine_NotFound)
				{
					setStatus(conn,m6dto,XML_Sent_OrderLine_Not_Found);	
					
//					call FX function , m6Dto.crmOrderId
					stmt=conn.createStatement();
					Long orderid=null;
					rs=stmt.executeQuery("select CRMORDERID from IOE.TM6_NEWORDER_STATUS where ID="+m6dto.getXmlId());
					if(rs.next())
					{
						orderid=rs.getLong("CRMORDERID");
					}
					stmt.close();
					//new ViewOrderDao().setBTEndIfPossible(orderid,null);
				}
			}while(fetchChangeOrderXMLStatus==Fetch_Status_DataFound || fetchChangeOrderXMLStatus==Fetch_Status_OrderLine_NotFound );
		}
		catch(Exception ex)
		{
			Utility.onEx_LOG_RET_NEW_EX(ex, "sendToChangeOrderM6Job", "sendToM6", null, true, true);  
		}
		finally{
			try {
				DbConnection.closeResultset(rs);
				DbConnection.closeStatement(stmt);
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Utility.onEx_LOG_RET_NEW_EX(e, "sendToChangeOrderM6Job", "sendToM6", null, true, true);  
			}
		}
		
		
	}
	 
	private void setStatus(Connection conn, FetchM6XMLDto m6dto, int sent_Success) {

		PreparedStatement pstmt=null;
		try {
				pstmt=conn.prepareStatement(sql_UpdateStatus);
				pstmt.setShort(1, (short)sent_Success);
				pstmt.setString(2, m6dto.getMessageID());
				pstmt.setLong(3, m6dto.getXmlId());
				pstmt.execute();  
			
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			Utility.onEx_LOG_RET_NEW_EX(ex, "setStatus", "sendChangeOrderXML", null, true, true);  
		}
		finally
		{
			try
			{
			DbConnection.closePreparedStatement(pstmt);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		
	}

	private void sendChangeOrderXML(FetchM6XMLDto m6dto){
		
		int sendStatus=0;
		MessageHandler handler=new MessageHandler();
		String messageID=null;
		try {
			AppConstants.IOES_LOGGER.error("Info -  In sendChangeOrderXML ");
			handler.initJMSConnection(m6dto.getM6Queue_host(), m6dto.getM6Queue_channel(), Integer.parseInt(m6dto.getM6Queue_port()), m6dto.getM6Queue_qmgrName());
			AppConstants.IOES_LOGGER.error("Info - Sending Change Order Xml to M6 : "+m6dto.getXml());
			messageID=handler.requestResponse(m6dto.getXml(), m6dto.getM6Queue_changeReqQName(), m6dto.getM6Queue_respQName(), m6dto.getIsHeaderRequired(), m6dto.getOrderObjectType() ,m6dto.getOrderType(),timeOut);
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

	private int fetchNextChangeOrderXMLData(Connection conn, FetchM6XMLDto m6dto) throws Exception {
		
		
		int returnStatus=0;
		CallableStatement cstmt=null;
		CallableStatement cs=null;
		try {
			    AppConstants.IOES_LOGGER.error("Info -  In fetchNextChangeOrderXMLData ");
				cstmt=conn.prepareCall(sql_getNextM6_ChangeOrder_XML); 

				cstmt.setNull(1, java.sql.Types.CLOB);
				cstmt.setLong(2, 0);
				cstmt.setString(3, ""); 
				cstmt.setString(4, null); 
				cstmt.setString(5, null); 
				cstmt.setString(6, null); 
				cstmt.setString(7, null);
				cstmt.setString(8, null);
				cstmt.execute();
				
				//String xml=cstmt.getString(1);
				Long xmlId=cstmt.getLong(2);
				String msgCode=cstmt.getString(4);
				String orderLineFlag=cstmt.getString(5);
				m6dto.setIsHeaderRequired(cstmt.getString(6));
				m6dto.setOrderObjectType(cstmt.getString(7));
				m6dto.setOrderType(cstmt.getString(8));
				
				//Start Saurabh for properties file into database
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
				m6dto.setM6Queue_host(cs.getString(1));
				m6dto.setM6Queue_channel(cs.getString(2));
				m6dto.setM6Queue_port(cs.getString(3));
				m6dto.setM6Queue_qmgrName(cs.getString(4));
				m6dto.setM6Queue_changeReqQName(cs.getString(5));
				m6dto.setM6Queue_reqQName(cs.getString(6));
				m6dto.setM6Queue_respQName(cs.getString(7));
				m6dto.setM6_Response_Path(cs.getString(8));
				//End Saurabh
				
				if("1".equals(msgCode)) 
				{
					if(orderLineFlag.equalsIgnoreCase("1"))
					{
						Clob b=cstmt.getClob(1);
						byte byteArr[]=Utility.clobToByteArray(b);
						String xml=new String(byteArr);
						m6dto.setXml(xml);
						m6dto.setXmlId(xmlId);
						returnStatus=Fetch_Status_OrderLine_NotFound;
					}
					else
					{
						Clob b=cstmt.getClob(1);
						byte byteArr[]=Utility.clobToByteArray(b);
						String xml=new String(byteArr);
						m6dto.setXml(xml);
						m6dto.setXmlId(xmlId);
						returnStatus=Fetch_Status_DataFound;
					}
				}
				else
				{
					returnStatus=Fetch_Status_DataNotFound;	
				}
			
			
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			throw Utility.onEx_LOG_RET_NEW_EX(ex, "fetchNextChangeOrderXMLData", "sendChangeOrderXML", null, true, true); 
		}
		finally
		{
			DbConnection.closeCallableStatement(cs);
			DbConnection.closeCallableStatement(cstmt);
			DbConnection.closeCallableStatement(cs);
			DbConnection.closeStatement(cstmt);
		}
	return returnStatus;
	}

	public static void main(String[] args) {
		new SendChangeOrderXmlToM6().sendToChangeOrderM6Job();
	}


}
