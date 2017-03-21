package com.ibm.ioes.ei.m6;
//Tag Name Resource Name  Date		CSR No			Description
//[001]	 SAURABH SINGH	10-Apr-13	00-05422		Adding Cancel Hardware Line Scheduler
//[002]	 Kalpana		13-JUN-13	CBR-CSR-20130404-XX-08714     For M6 Editable fields ,executing SP_UPDATE_ATTRIBUTE_DETAILS_M6_RESPONSE to update attribute values in order
//[003]	 Kalpana	11-January-2014			Added createSCMXML, to create xml for third party line items
//[004]	 Kalpana	30-January-2014			Added createScmCircuitXml, to create xml for third party line items for circuit
//[005]	 Santosh	20-Feb-2014				Added code for ArrayList iteration
//[006]	 Kalpana	4-March-2014			Added createSCMXmlPRReuse, to create xml for third party line items for PR Reuse
//[007] VIPIN SAHARIA Added Condition to remove trim() as per requirement against bidirectional Attribute while processing 9999. #M6_Enrichment
//[008] Rahul Tandon  CR_20141113-R1-020801  Added one input parameter in IOE.SP_UPDATE_BASE_TABLE_M6_RESPONSE proc for processing creation date
//[009]Pankaj Thakur   12-feb-2015    Added one input parameter in "IOE"."SP_UPDATE_M6_SERKEY" and added on record on  saveDDTaskClosureData() 

//[010] Shubhranshu Srivastava 11-01-2016   Modifications for XML processing using MultiThreading

import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.Callable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.tools.ant.filters.TokenFilter.Trim;
import org.apache.commons.collections.iterators.ArrayListIterator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ibm.fx.mq.ApplicationClone;
import com.ibm.ioes.ei.scm.BL.SCMBL;
import com.ibm.ioes.forms.M6LOCDATADTO;
import com.ibm.ioes.service.UtilityService;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Messages;
import com.ibm.ioes.utilities.Utility;
import java.io.FileInputStream;
import java.io.StringReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import com.ibm.ioes.ei.scm.BL.SCMBL;
import com.ibm.ioes.dao.ViewOrderDao;
import com.ibm.ioes.utilities.AppConstants;

public class ProcessResponseXML {
	
	private static final int Fetch_Status_Found=1;
	private static final int Fetch_Status_NotFound=2;
	
	private static final int XMLData_Processed_Success=3;
	private static final int XMLData_Processed_Failure=2;
	
	private static final String XMLData_M6_Success="SUCCESS";
	private static final String XMLData_M6_Failure="FAILURE";
	
	//Start [001]
	private static final String EventTypeId_1111="1111";//Line item cancellation
	//End [001]
	private static final String EventTypeId_2222="2222"; //Success/Failure in creation of order
	private static final String EventTypeId_2233="2233"; //Order already exist in M6
	private static final String EventTypeId_4444="4444";//Last task(DD Task Closure)
	private static final String EventTypeId_5555="5555";//Order reached for PM enrichment
	private static final String EventTypeId_6666="6666";//Service Closure(Order Comp)
	private static final String EventTypeId_7777="7777";//Hardware DC Details(Order Comp)
	private static final String EventTypeId_8888="8888";//Order Cancellation
	private static final String EventTypeId_9999="9999";//Service Closure(Order Comp)
	
	final static String sql_UpdateStatusInResponse="{call IOE.spUpdateStatusM6Response(?,?)}";//"UPDATE [dbo].[tM6_NewOrder_Response] SET [status]=? ,modifiedDate=getdate() WHERE ID=?";
	//[008] Start
	final static String sql_UpdateBaseTables="{call IOE.SP_UPDATE_BASE_TABLE_M6_RESPONSE(?,?,?,?,?,?,?,?,?,?,?)}";//"UPDATE [dbo].[tPOServiceMaster] SET [m6OrderNo]=?, [preOrderNo]=? WHERE serviceID=? and orderNo=? ";
	//[008] End
	/*final static String sqlGetNextResponseXml="{call IOE.spGetM6ResponseNextXml()}";//" SELECT top 1 [ID], [JMS_MESSAGEID], [FILENAME] FROM [dbo].[tM6_NewOrder_Response] WHERE status=0 order by ID asc";
*/	
	//Shubhranshu
	final static String sqlGetNextResponseXml="SELECT ID,JMS_MESSAGEID, FILENAME " +
			"FROM ioe.tM6_NewOrder_Response WHERE status=0 AND ID=<id>"; 
	//Shubhranshu
	private static final String sql_UpdateM6WorkflowResponse = "{call IOE.UPDATE_M6_WORKFLOW_RESPONSE(?,?,?,?)}";
	
	private static final String sql_DDTaskClosureResponse = "{call IOE.SP_UPDATE_DDTASKCLOSUREDATA_M6_RESPONSE1(?,?,?,?,?,?,?,?)}";
	private static final String sql_DDTaskClosure_M6_SERKEY_Response = "{call IOE.SP_UPDATE_M6_SERKEY(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String sqlUpdateLOCDATE = "{call IOE.UPDATE_LOC_DATE_FOR_M6LINEITEMS(?)}";
	private static final String sql_UpdateSatge = "{call IOE.SP_UPDATE_STAGE_IN_EVENT7777(?,?)}";
	private static final String sql_HardwareOsnRecDate = "UPDATE ioe.TPOSERVICEDETAILS SET HARDWARE_OSN_RECEIVED_DATE=CURRENT timestamp WHERE SERVICEPRODUCTID=? AND HARDWARE_OSN_RECEIVED_DATE IS null";
	
	private static final String sql_CancelLineResponse = "{call IOE.UPDATE_CANCELLED_LINE_RESPONSE(?,?,?,?,?)}";
	
	private static final String sql_AttrValClosureResponse = "{call IOE.SP_UPDATE_ATTRIBUTE_DETAILS_M6_RESPONSE(?,?,?,?,?,?,?,?)}";
	//Shubhranshu
	private static final String sql_GetPendingXMLData = " SELECT ID,EVENT_TYPE_ID " +
			                                                                                      " FROM ioe.TM6_NEWORDER_RESPONSE " +
			                                                                                      " WHERE status=0"+
                                                                                                  " fetch first 500 rows only  "; 
	
	private static final String OsnTypeId_9999 = "9999";
	private static final String OsnTypeId_8888 = "8888";
	//Shubhranshu
	
	private static  HashMap<Integer,String> circuitMap = new HashMap<Integer,String> ();
	private static  SCMBL scmBLObj=new SCMBL();
	
	
	static class XMLData
	{
		int xmlId=0;
		String osntype=null;

		/**
		 * @return the xmlId
		 */
		public int getXmlId() {
			return xmlId;
		}

		/**
		 * @return the osntype
		 */
		public String getOsntype() {
			return osntype;
		}

		/**
		 * @param xmlId the xmlId to set
		 */
		public void setXmlId(int xmlId) {
			this.xmlId = xmlId;
		}

		/**
		 * @param osntype the osntype to set
		 */
		public void setOsntype(String osntype) {
			this.osntype = osntype;
		}	
	}
	
	static class XMLcallable implements Callable<Object>
	{
		long id=0;
		public XMLcallable(long id)
		{
			this.id=id;
		}

		@Override
		public Object call() throws Exception {
			
			new ProcessResponseXML().processResponseJobById(this.id);
			
		
			return null;
		}
	
	}
	
	public void processM6Response() throws Exception
	{	
		//[010]		
		
		List<Integer> list_8888_OSN=new ArrayList<Integer>();
		List<Integer> list_9999_OSN =new ArrayList<Integer>() ;
		List<Integer> list_other_OSN=new ArrayList<Integer>() ;
		
		List<XMLData> xmldatalist;	
		
		ApplicationClone apclone=ApplicationClone.getInstance(new UtilityService());
		
		do
		{								
			 xmldatalist = getPendingXMLData();  // get 500 pending osn in a list 			 
			// Divide  into three separate lists based on osn_type		
			 		
			 		if(!xmldatalist.isEmpty())
			 		{
					 		ListIterator<XMLData> itr=xmldatalist.listIterator();					 		
					 		while(itr.hasNext())
					 		{
							 			XMLData entry=itr.next();
							 			String osnType=entry.getOsntype();							
										int xmlId=entry.getXmlId();	
										
										if(osnType.equals(OsnTypeId_9999))
										{
											 list_9999_OSN.add(xmlId); // Add to separate list 								 								
										}							
										else if (osnType.equals(OsnTypeId_8888))
										{
											list_8888_OSN.add(xmlId);								
										}							
										else
										{
											list_other_OSN.add(xmlId);						
										}						 			
					 		}			 			
								apclone.submitTaskToPool(createCallableList( list_other_OSN), ApplicationClone.TaskType.M6_OSN_PROCESS);					
									apclone.submitTaskToPool(createCallableList( list_8888_OSN), ApplicationClone.TaskType.M6_OSN_PROCESS);					
													apclone.submitTaskToPool(createCallableList( list_9999_OSN), ApplicationClone.TaskType.M6_OSN_PROCESS);									
																							
													list_8888_OSN.clear();  	list_9999_OSN.clear();  list_other_OSN.clear();
			 		}					
			 		
			}	while(!(xmldatalist.isEmpty())); 
															
	   //[010]
	}
	
	
	private List<Callable<Object>> createCallableList(List<Integer> osnidlist) {
	
		List<Callable<Object>> callablelist= new ArrayList<Callable<Object>>();
		
		for(Integer entry: osnidlist)
		{
			callablelist.add(new XMLcallable(entry));
		}
		return callablelist;
	}


	private List<XMLData> getPendingXMLData() throws Exception
	{
				List<XMLData> datalist=new ArrayList<ProcessResponseXML.XMLData>();
				Connection conn=null;
				Statement stmt=null;
				ResultSet rst=null;
				
				try{
					
					 conn=DbConnection.getConnectionObject();
					 stmt=conn.createStatement();
					 rst=stmt.executeQuery(sql_GetPendingXMLData);
								
							while(rst.next())
							{
							XMLData xmldata=new XMLData();		
							
							xmldata.setXmlId(rst.getInt("ID"));
							
							xmldata.setOsntype(rst.getString("EVENT_TYPE_ID"));
							
							datalist.add(xmldata);
							}
							
							if(rst!=null)
								DbConnection.closeResultset(rst);
					} 
					catch (Exception e)
					{   e.printStackTrace(); }	
				
					finally
					{
								if(rst!=null)	
								{	
									DbConnection.closeResultset(rst); 
									DbConnection.closeStatement(stmt); 
								}
								if(conn!=null)
									{  
										if( !(conn.isClosed()) )
										{
										DbConnection.freeConnection(conn);
										}
									}
					}
						
		return datalist;
	}
	public void processResponseJobById(long id)
	{
		System.err.println("in processResponseJob"); 
		
		int fetchXMLStatus=0;
		FetchM6XMLDto m6dto =new FetchM6XMLDto();
		Connection conn=null;
		try
		{
			conn=DbConnection.getConnectionObject();
			
			/*do
			{*/
				fetchXMLStatus=fetchNextXMLData(conn,m6dto,id); // [010] introduces the parameter id 
				
				if(fetchXMLStatus==Fetch_Status_Found)
				{
				String str= m6dto.getServiceProductId();
				
				
					if(!validateLineItem(str)){
						setStatus(conn,m6dto,XMLData_Processed_Failure);
						return;
					}
					
					if(EventTypeId_2222.equals(m6dto.getEventTypeId()) || EventTypeId_2233.equals(m6dto.getEventTypeId()) || EventTypeId_9999.equals(m6dto.getEventTypeId()) || EventTypeId_8888.equals(m6dto.getEventTypeId()))
					{
						if("SUCCESS".equalsIgnoreCase(m6dto.getOrderStatus()))
						{
							saveXMLData(conn,m6dto,XMLData_M6_Success);
						}
						else
						{
							saveXMLData(conn,m6dto,XMLData_M6_Failure);
						}
						
					
						
						//neha
						if(EventTypeId_9999.equals(m6dto.getEventTypeId())){
							saveDesiredDueDate(conn,m6dto);
						}
						//neha
						
						
						int saveStatus=m6dto.getSend_status();
						
						if(saveStatus==1)
						{
							//success
							setStatus(conn,m6dto,XMLData_Processed_Success);
							if(EventTypeId_9999.equals(m6dto.getEventTypeId()))
							{
//								call FX function , m6Dto.crmOrderId
								//new ViewOrderDao().setBTEndIfPossible(Long.parseLong(m6dto.getCrmorderId()),null);
								saveAttrValClosureData(conn, m6dto);
								if(Integer.parseInt(Utility.getAppConfigValue(AppConstants.IS_THIRD_PARTY_ACTIVE))==1){
									//to insert entries for spId's for third party line items for PR reuse
									insertInScmTablePRReuse(conn,m6dto.getCrmorderId(),m6dto.getServiceListId(),m6dto.getEventTypeId(),m6dto.getM6OrderId());
									if(circuitMap!=null && circuitMap.size()>0){
										//to insert entries for spId's for third party line items for circuit update
										insertInScmTableCircuitUpdate(conn, m6dto.getCrmorderId(), m6dto.getServiceListId(), m6dto.getEventTypeId(), circuitMap, m6dto.getM6OrderId());
									}
								}
							}
							if(EventTypeId_2222.equals(m6dto.getEventTypeId()) && m6dto.getOrderStatus()!=null && "SUCCESS".equalsIgnoreCase(m6dto.getOrderStatus()))
							{
								//to updateNfaNumber for third party line items
								System.out.println("@@"+Utility.getAppConfigValue(AppConstants.IS_THIRD_PARTY_ACTIVE));
								if(Integer.parseInt(Utility.getAppConfigValue(AppConstants.IS_THIRD_PARTY_ACTIVE))==1){
									updateNfaAndInsertInScmTable(conn,m6dto.getCrmorderId(),m6dto.getServiceListId(),m6dto.getEventTypeId(),m6dto.getM6OrderId());
								}
							}
						}else
						{
							setStatus(conn,m6dto,XMLData_Processed_Failure);	
						}
					}
					
					//Start [001]
					else if (EventTypeId_1111.equals(m6dto.getEventTypeId()))
					{
						saveLineCancelationXmlData(conn,m6dto);
						int saveStatus=m6dto.getSend_status();
						if(saveStatus==1)
						{
							setStatus(conn,m6dto,XMLData_Processed_Success);
						}
						else
						{
							setStatus(conn,m6dto,XMLData_Processed_Failure);	
						}
					}
					//End [001]
					else 
					{
						if(EventTypeId_5555.equals(m6dto.getEventTypeId()))
						{
							saveXMLWorkflowData(conn,m6dto);
							if(Integer.parseInt(Utility.getAppConfigValue(AppConstants.IS_THIRD_PARTY_ACTIVE))==1){
								updateNfaAndInsertInScmTable(conn,m6dto.getCrmorderId(),m6dto.getServiceListId(),m6dto.getEventTypeId(),m6dto.getM6OrderId());
							}
						}
						
						else if (EventTypeId_4444.equals(m6dto.getEventTypeId()) || EventTypeId_6666.equals(m6dto.getEventTypeId()) || EventTypeId_7777.equals(m6dto.getEventTypeId()))
						{
							saveDDTaskClosureData(conn,m6dto);
						}
						int saveStatus=m6dto.getStatus();
						if(saveStatus==1)
						{
							//success
							setStatus(conn,m6dto,XMLData_Processed_Success);
							
						}else
						{
							setStatus(conn,m6dto,XMLData_Processed_Failure);	
						}
						
					}
				}
		/*	}while(fetchXMLStatus==Fetch_Status_Found);*/
		}
		catch(Exception ex)
		{
			try {
				conn.rollback();
			} catch (SQLException e) {
				Utility.onEx_LOG_RET_NEW_EX(ex, "processResponseJob", "processResponseXML", null, true, true);  
			}
			    Utility.onEx_LOG_RET_NEW_EX(ex, "processResponseJob", "processResponseXML", null, true, true);  
		}
		finally{
			try {
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Utility.onEx_LOG_RET_NEW_EX(e, "processResponseJob", "processResponseXML", null, true, true);  
			}
		}
		
	}
	/*
	public void processResponseJob()
	{
		System.err.println("in processResponseJob"); 
		
		int fetchXMLStatus=0;
		FetchM6XMLDto m6dto =new FetchM6XMLDto();
		Connection conn=null;
		try
		{
			conn=DbConnection.getConnectionObject();
			
			do
			{
				
				fetchXMLStatus=fetchNextXMLData(conn,m6dto);
				if(fetchXMLStatus==Fetch_Status_Found)
				{
					
					if(EventTypeId_2222.equals(m6dto.getEventTypeId()) || EventTypeId_2233.equals(m6dto.getEventTypeId()) || EventTypeId_9999.equals(m6dto.getEventTypeId()) || EventTypeId_8888.equals(m6dto.getEventTypeId()))
					{
						if("SUCCESS".equalsIgnoreCase(m6dto.getOrderStatus()))
						{
							saveXMLData(conn,m6dto,XMLData_M6_Success);
						}
						else
						{
							saveXMLData(conn,m6dto,XMLData_M6_Failure);
						}
						
					
						
						//neha
						if(EventTypeId_9999.equals(m6dto.getEventTypeId())){
							saveDesiredDueDate(conn,m6dto);
						}
						//neha
						
						
						int saveStatus=m6dto.getSend_status();
						if(saveStatus==1)
						{
							//success
							setStatus(conn,m6dto,XMLData_Processed_Success);
							if(EventTypeId_9999.equals(m6dto.getEventTypeId()))
							{
//								call FX function , m6Dto.crmOrderId
								//new ViewOrderDao().setBTEndIfPossible(Long.parseLong(m6dto.getCrmorderId()),null);
								saveAttrValClosureData(conn, m6dto);
								if(Integer.parseInt(Utility.getAppConfigValue(AppConstants.IS_THIRD_PARTY_ACTIVE))==1){
									//to insert entries for spId's for third party line items for PR reuse
									insertInScmTablePRReuse(conn,m6dto.getCrmorderId(),m6dto.getServiceListId(),m6dto.getEventTypeId(),m6dto.getM6OrderId());
									if(circuitMap!=null && circuitMap.size()>0){
										//to insert entries for spId's for third party line items for circuit update
										insertInScmTableCircuitUpdate(conn, m6dto.getCrmorderId(), m6dto.getServiceListId(), m6dto.getEventTypeId(), circuitMap, m6dto.getM6OrderId());
									}
								}
							}
							if(EventTypeId_2222.equals(m6dto.getEventTypeId()) && m6dto.getOrderStatus()!=null && "SUCCESS".equalsIgnoreCase(m6dto.getOrderStatus()))
							{
								//to updateNfaNumber for third party line items
								System.out.println("@@"+Utility.getAppConfigValue(AppConstants.IS_THIRD_PARTY_ACTIVE));
								if(Integer.parseInt(Utility.getAppConfigValue(AppConstants.IS_THIRD_PARTY_ACTIVE))==1){
									updateNfaAndInsertInScmTable(conn,m6dto.getCrmorderId(),m6dto.getServiceListId(),m6dto.getEventTypeId(),m6dto.getM6OrderId());
								}
							}
						}else
						{
							setStatus(conn,m6dto,XMLData_Processed_Failure);	
						}
					}
					//Start [001]
					else if (EventTypeId_1111.equals(m6dto.getEventTypeId()))
					{
						saveLineCancelationXmlData(conn,m6dto);
						int saveStatus=m6dto.getSend_status();
						if(saveStatus==1)
						{
							setStatus(conn,m6dto,XMLData_Processed_Success);
						}
						else
						{
							setStatus(conn,m6dto,XMLData_Processed_Failure);	
						}
					}
					//End [001]
					else 
					{
						if(EventTypeId_5555.equals(m6dto.getEventTypeId()))
						{
							saveXMLWorkflowData(conn,m6dto);
							if(Integer.parseInt(Utility.getAppConfigValue(AppConstants.IS_THIRD_PARTY_ACTIVE))==1){
								updateNfaAndInsertInScmTable(conn,m6dto.getCrmorderId(),m6dto.getServiceListId(),m6dto.getEventTypeId(),m6dto.getM6OrderId());
							}
						}
						
						else if (EventTypeId_4444.equals(m6dto.getEventTypeId()) || EventTypeId_6666.equals(m6dto.getEventTypeId()) || EventTypeId_7777.equals(m6dto.getEventTypeId()))
						{
							saveDDTaskClosureData(conn,m6dto);
						}
						int saveStatus=m6dto.getStatus();
						if(saveStatus==1)
						{
							//success
							setStatus(conn,m6dto,XMLData_Processed_Success);
							
						}else
						{
							setStatus(conn,m6dto,XMLData_Processed_Failure);	
						}
						
					}
				}
			}while(fetchXMLStatus==Fetch_Status_Found);
		}
		catch(Exception ex)
		{
			/*try {
				conn.rollback();
			} catch (SQLException e) {
				Utility.onEx_LOG_RET_NEW_EX(ex, "processResponseJob", "processResponseXML", null, true, true);  
			}
			    Utility.onEx_LOG_RET_NEW_EX(ex, "processResponseJob", "processResponseXML", null, true, true);  
		}
		finally{
			try {
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Utility.onEx_LOG_RET_NEW_EX(e, "processResponseJob", "processResponseXML", null, true, true);  
			}
		}
		
		
	}	
	*/
	
	 
	private void saveXMLWorkflowData(Connection conn, FetchM6XMLDto m6dto) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt=null;
		try {
				pstmt=conn.prepareStatement(sql_UpdateM6WorkflowResponse);
				pstmt.setLong(1, Long.parseLong(m6dto.getCrmorderId()));
				pstmt.setLong(2, Long.parseLong(m6dto.getServiceListId()));
				pstmt.setString(3, m6dto.getEventTypeId());
				pstmt.setString(4, m6dto.getReason());
				//pstmt.setString(5, m6dto.getM6OrderId());
				
				pstmt.execute();  
				m6dto.setStatus(1);
			
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			Utility.onEx_LOG_RET_NEW_EX(ex, "setStatus", "processResponseXML", null, true, true);
			m6dto.setStatus(-1);
		}finally
		{
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				Utility.onEx_LOG_RET_NEW_EX(e, "setStatus", "processResponseXML", null, true, true);  
			}
		}
	}



	private void setStatus(Connection conn, FetchM6XMLDto m6dto, int save_Success) {

		PreparedStatement pstmt=null;
		try {
				pstmt=conn.prepareStatement(sql_UpdateStatusInResponse);
				pstmt.setShort(1, (short)save_Success);
				pstmt.setString(2, m6dto.getReadResponseXMLId());
				pstmt.execute();  
			
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			Utility.onEx_LOG_RET_NEW_EX(ex, "setStatus", "processResponseXML", null, true, true);  
		}finally
		{
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				Utility.onEx_LOG_RET_NEW_EX(e, "setStatus", "processResponseXML", null, true, true);  
			}
		}
		
	}

	private void saveXMLData(Connection conn, FetchM6XMLDto m6dto, String data_M6_status){
		
		int sendStatus=0;
		
		String messageID=null;
		//PreparedStatement pstmt=null;
		CallableStatement pstmt=null;	
		
		try {
			String sql=sql_UpdateBaseTables;
			//pstmt=conn.prepareStatement(sql);
			pstmt=conn.prepareCall(sql);
			pstmt.setString(1, m6dto.getM6OrderId());
			pstmt.setString(2, m6dto.getPreOrderNo());
			pstmt.setLong(3, Long.parseLong(m6dto.getServiceListId()));
			pstmt.setLong(4, Long.parseLong(m6dto.getCrmorderId()));
			pstmt.setLong(5, Long.parseLong(m6dto.getReadResponseXMLId()));
			pstmt.setString(6, data_M6_status);
			pstmt.setLong(7, Long.parseLong(m6dto.getReadResponseXMLId()));
			pstmt.setString(8, m6dto.getEventTypeId());
			pstmt.setString(9, m6dto.getReason());
			pstmt.setString(10, "");//out parameter	
			
			//[008] Start
			if(m6dto.getCreationDate()!=null){
				 	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
					Date date = formatter.parse(m6dto.getCreationDate());
					java.sql.Timestamp ts = new Timestamp(date.getTime());
					pstmt.setTimestamp(11, ts);
			}
			else
				    pstmt.setNull(11,java.sql.Types.TIMESTAMP);
		    //[008]  End	
			pstmt.execute();
			
			if(pstmt.getString(10) != null && (! pstmt.getString(10).contains("Procedure Ends At Last(Success)"))){
				Utility.LOG(true, true, "After execution Logs for proc : IOE.SP_UPDATE_BASE_TABLE_M6_RESPONSE for service Id - "+m6dto.getServiceListId()+" is - "+pstmt.getString(10));
			}
			
			sendStatus=1;
		} catch (Exception e) {
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e,"setStatus","processResponseXML",	"Error during execution of proc - IOE.SP_UPDATE_BASE_TABLE_M6_RESPONSE for service Id - "
							+ m6dto.getServiceListId(), true, true);
			sendStatus=-1;
		}finally
		{
			try {
				pstmt.close();
			} catch (SQLException e) {
				 Utility.onEx_LOG_RET_NEW_EX(e, "setStatus", "processResponseXML", null, true, true);  
			}
		}
		
		m6dto.setSend_status(sendStatus);
		
	}
	
private void saveDDTaskClosureData(Connection conn, FetchM6XMLDto m6dto){
		
		int sendStatus=0;
		PreparedStatement pstmt=null;
		PreparedStatement pstmt1=null;
		HashMap hm = new HashMap();
		M6LOCDATADTO m6locdatedto=null;
		
		
		
		try {
		/*	conn.setAutoCommit(false);*/  // 21-1-16
			String sql=sql_DDTaskClosureResponse;
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, m6dto.getM6OrderId());
			pstmt.setString(2, m6dto.getPreOrderNo());
			pstmt.setLong(3, Long.parseLong(m6dto.getServiceListId()));
			pstmt.setLong(4, Long.parseLong(m6dto.getCrmorderId()));
			pstmt.setString(5, m6dto.getOrderStatus());
			pstmt.setString(6, m6dto.getEventTypeId());
			pstmt.setString(7, m6dto.getReason());
			//Ramana //pass 8th param serviceproductid
			pstmt.setString(8, m6dto.getServiceProductId());
			pstmt.execute();
			sendStatus=1;
			if(EventTypeId_7777.equals(m6dto.getEventTypeId()))
			{
				updatestageToGetEvent7777(conn,m6dto);
			}
			
				String sql1=sql_DDTaskClosure_M6_SERKEY_Response;
				for (FetchLastTaskNewOrderXMLDto DDTaskClosureDto : m6dto.getFetchLastTaskNewOrderXMLDtoList())
				{
					if(DDTaskClosureDto.getOrderLineNo() != null)
					{
						if ((hm.containsKey(DDTaskClosureDto.getOrderLineNo())) == false) {
							
							m6locdatedto=new M6LOCDATADTO();
							
							m6locdatedto.setSpid(DDTaskClosureDto.getOrderLineNo());

							hm.put(m6locdatedto.getSpid(),m6locdatedto);
							
						}
						
						pstmt1=conn.prepareStatement(sql1);
						if("M6CHILDSERKEY".equalsIgnoreCase(DDTaskClosureDto.getParamKey()) )
						{
							pstmt1.setLong(1, Long.parseLong(DDTaskClosureDto.getOrderLineNo()));
							pstmt1.setString(2, DDTaskClosureDto.getValue());
							pstmt1.setString(3, null);
							pstmt1.setString(4, null);
							pstmt1.setString(5, null);
							pstmt1.setString(6, null);
							pstmt1.setString(7, null);
							pstmt1.setString(8, null);
							pstmt1.setString(9, null);
							pstmt1.setString(10, null);
							pstmt1.setString(11, null);
							pstmt1.setString(12, null);
							pstmt1.setString(13, null);
							pstmt1.setString(14, null);
							// [009] added below parameter to check correct order line corresponding service id
							pstmt1.setLong(15,Long.parseLong(m6dto.getServiceListId())); 
							pstmt1.execute();
						}
						if("M6PARENTSERKEY".equalsIgnoreCase(DDTaskClosureDto.getParamKey()) )
						{
							pstmt1.setLong(1, Long.parseLong(DDTaskClosureDto.getOrderLineNo()));
							pstmt1.setString(2, null);
							pstmt1.setString(3, DDTaskClosureDto.getValue());
							pstmt1.setString(4, null);
							pstmt1.setString(5, null);
							pstmt1.setString(6, null);
							pstmt1.setString(7, null);
							pstmt1.setString(8, null);
							pstmt1.setString(9, null);
							pstmt1.setString(10, null);
							pstmt1.setString(11, null);
							pstmt1.setString(12, null);
							pstmt1.setString(13, null);
							pstmt1.setString(14, null);
							pstmt1.setLong(15,Long.parseLong(m6dto.getServiceListId()));
							pstmt1.execute();
						}
						if("CKT".equalsIgnoreCase(DDTaskClosureDto.getParamKey()))
						{
							
							pstmt1.setLong(1, Long.parseLong(DDTaskClosureDto.getOrderLineNo()));
							pstmt1.setString(2, null);
							pstmt1.setString(3, null);
							pstmt1.setString(4, DDTaskClosureDto.getValue());
							pstmt1.setString(5, null);
							pstmt1.setString(6, null);
							pstmt1.setString(7, null);
							pstmt1.setString(8, null);
							pstmt1.setString(9, null);
							pstmt1.setString(10, null);
							pstmt1.setString(11, null);
							pstmt1.setString(12, null);
							pstmt1.setString(13, null);
							if(EventTypeId_7777.equals(m6dto.getEventTypeId())){
								String serialNumber=null;
								boolean isValueFound = false;
								
								if(DDTaskClosureDto.getValue()!=null){
									int lastIndex=DDTaskClosureDto.getValue().lastIndexOf("-");
									if(lastIndex!=-1){
										serialNumber=DDTaskClosureDto.getValue().substring(0, lastIndex);
										isValueFound=true;
									}
								}
								
								if(isValueFound){
									pstmt1.setString(14, serialNumber);
								}else{
									pstmt1.setString(14, null);
									}
							
							}else{
								pstmt1.setString(14, null);
							}
							pstmt1.setLong(15,Long.parseLong(m6dto.getServiceListId()));
							pstmt1.execute();
							m6locdatedto=(M6LOCDATADTO)(hm.get(m6locdatedto.getSpid()));
							m6locdatedto.setCktid(DDTaskClosureDto.getValue());
						}
						if("UOM".equalsIgnoreCase(DDTaskClosureDto.getParamKey()))
						{
							pstmt1.setLong(1, Long.parseLong(DDTaskClosureDto.getOrderLineNo()));
							pstmt1.setString(2, null);
							pstmt1.setString(3, null);
							pstmt1.setString(4, null);
							pstmt1.setString(5, DDTaskClosureDto.getValue());
							pstmt1.setString(6, null);
							pstmt1.setString(7, null);
							pstmt1.setString(8, null);
							pstmt1.setString(9, null);
							pstmt1.setString(10, null);
							pstmt1.setString(11, null);
							pstmt1.setString(12, null);
							pstmt1.setString(13, null);
							pstmt1.setString(14, null);
							pstmt1.setLong(15,Long.parseLong(m6dto.getServiceListId()));
							pstmt1.execute();
						}
						if("PRILOC".equalsIgnoreCase(DDTaskClosureDto.getParamKey()))
						{
							pstmt1.setLong(1, Long.parseLong(DDTaskClosureDto.getOrderLineNo()));
							pstmt1.setString(2, null);
							pstmt1.setString(3, null);
							pstmt1.setString(4, null);
							pstmt1.setString(5, null);
							pstmt1.setString(6, DDTaskClosureDto.getValue());
							pstmt1.setString(7, null);
							pstmt1.setString(8, null);
							pstmt1.setString(9, null);
							pstmt1.setString(10, null);
							pstmt1.setString(11, null);
							pstmt1.setString(12, null);
							pstmt1.setString(13, null);
							pstmt1.setString(14, null);
							pstmt1.setLong(15,Long.parseLong(m6dto.getServiceListId()));
							pstmt1.execute();
						}
						if("HUBLOC".equalsIgnoreCase(DDTaskClosureDto.getParamKey()))
						{
							pstmt1.setLong(1, Long.parseLong(DDTaskClosureDto.getOrderLineNo()));
							pstmt1.setString(2, null);
							pstmt1.setString(3, null);
							pstmt1.setString(4, null);
							pstmt1.setString(5, null);
							pstmt1.setString(6, null);
							pstmt1.setString(7, DDTaskClosureDto.getValue());
							pstmt1.setString(8, null);
							pstmt1.setString(9, null);
							pstmt1.setString(10, null);
							pstmt1.setString(11, null);
							pstmt1.setString(12, null);
							pstmt1.setString(13, null);
							pstmt1.setString(14, null);
							pstmt1.setLong(15,Long.parseLong(m6dto.getServiceListId()));
							pstmt1.execute();
						}
						if("LOC DATE".equalsIgnoreCase(DDTaskClosureDto.getParamKey()))
						{
							
							pstmt1.setLong(1, Long.parseLong(DDTaskClosureDto.getOrderLineNo()));
							pstmt1.setString(2, null);
							pstmt1.setString(3, null);
							pstmt1.setString(4, null);
							pstmt1.setString(5, null);
							pstmt1.setString(6, null);
							pstmt1.setString(7, null);
							pstmt1.setString(8, DDTaskClosureDto.getValue());
							pstmt1.setString(9, null);
							pstmt1.setString(10, null);
							pstmt1.setString(11, null);
							pstmt1.setString(12, null);
							pstmt1.setString(13, null);
							pstmt1.setString(14, null);
							pstmt1.setLong(15,Long.parseLong(m6dto.getServiceListId()));
							pstmt1.execute();
							m6locdatedto=(M6LOCDATADTO)(hm.get(m6locdatedto.getSpid()));
							m6locdatedto.setLoc_date(DDTaskClosureDto.getValue());
						}
						if("CHALLEN_DATE".equalsIgnoreCase(DDTaskClosureDto.getParamKey()))
						{
							pstmt1.setLong(1, Long.parseLong(DDTaskClosureDto.getOrderLineNo()));
							pstmt1.setString(2, null);
							pstmt1.setString(3, null);
							pstmt1.setString(4, null);
							pstmt1.setString(5, null);
							pstmt1.setString(6, null);
							pstmt1.setString(7, null);
							pstmt1.setString(8, null);
							pstmt1.setString(9, DDTaskClosureDto.getValue());
							pstmt1.setString(10, null);
							pstmt1.setString(11, null);
							pstmt1.setString(12, null);
							pstmt1.setString(13, null);
							pstmt1.setString(14, null);
							pstmt1.setLong(15,Long.parseLong(m6dto.getServiceListId()));
							pstmt1.execute();
						}
						if("CHALLEN_NO".equalsIgnoreCase(DDTaskClosureDto.getParamKey()))
						{
							pstmt1.setLong(1, Long.parseLong(DDTaskClosureDto.getOrderLineNo()));
							pstmt1.setString(2, null);
							pstmt1.setString(3, null);
							pstmt1.setString(4, null);
							pstmt1.setString(5, null);
							pstmt1.setString(6, null);
							pstmt1.setString(7, null);
							pstmt1.setString(8, null);
							pstmt1.setString(9, null);
							pstmt1.setString(10, DDTaskClosureDto.getValue());
							pstmt1.setString(11, null);
							pstmt1.setString(12, null);
							pstmt1.setString(13, null);
							pstmt1.setString(14, null);
							pstmt1.setLong(15,Long.parseLong(m6dto.getServiceListId()));
							pstmt1.execute();
						}
						if("HWInstallDate".equalsIgnoreCase(DDTaskClosureDto.getParamKey()))
						{
							pstmt1.setLong(1, Long.parseLong(DDTaskClosureDto.getOrderLineNo()));
							pstmt1.setString(2, null);
							pstmt1.setString(3, null);
							pstmt1.setString(4, null);
							pstmt1.setString(5, null);
							pstmt1.setString(6, null);
							pstmt1.setString(7, null);
							pstmt1.setString(8, null);
							pstmt1.setString(9, null);
							pstmt1.setString(10,null);
							pstmt1.setString(11, DDTaskClosureDto.getValue());
							pstmt1.setString(12, null);
							pstmt1.setString(13, null);
							pstmt1.setString(14, null);
							pstmt1.setLong(15,Long.parseLong(m6dto.getServiceListId()));
							pstmt1.execute();
						}
						if("HWInstallStatus".equalsIgnoreCase(DDTaskClosureDto.getParamKey()))
						{
							pstmt1.setLong(1, Long.parseLong(DDTaskClosureDto.getOrderLineNo()));
							pstmt1.setString(2, null);
							pstmt1.setString(3, null);
							pstmt1.setString(4, null);
							pstmt1.setString(5, null);
							pstmt1.setString(6, null);
							pstmt1.setString(7, null);
							pstmt1.setString(8, null);
							pstmt1.setString(9, null);
							pstmt1.setString(10,null);
							pstmt1.setString(11, null);
							pstmt1.setString(12, DDTaskClosureDto.getValue());
							pstmt1.setString(13, null);
							pstmt1.setString(14, null);
							pstmt1.setLong(15,Long.parseLong(m6dto.getServiceListId()));
							pstmt1.execute();
						}
						if("Oracle PR Number".equalsIgnoreCase(DDTaskClosureDto.getParamKey()))
						{
							pstmt1.setLong(1, Long.parseLong(DDTaskClosureDto.getOrderLineNo()));
							pstmt1.setString(2, null);
							pstmt1.setString(3, null);
							pstmt1.setString(4, null);
							pstmt1.setString(5, null);
							pstmt1.setString(6, null);
							pstmt1.setString(7, null);
							pstmt1.setString(8, null);
							pstmt1.setString(9, null);
							pstmt1.setString(10,null);
							pstmt1.setString(11, null);
							pstmt1.setString(12, null);
							pstmt1.setString(13, DDTaskClosureDto.getValue());
							pstmt1.setString(14, null);
							pstmt1.setLong(15,Long.parseLong(m6dto.getServiceListId()));
							pstmt1.execute();
						}
						/*if("Serial Number".equalsIgnoreCase(DDTaskClosureDto.getParamKey()))
						{
							pstmt1.setLong(1, Long.parseLong(DDTaskClosureDto.getOrderLineNo()));
							pstmt1.setString(2, null);
							pstmt1.setString(3, null);
							pstmt1.setString(4, null);
							pstmt1.setString(5, null);
							pstmt1.setString(6, null);
							pstmt1.setString(7, null);
							pstmt1.setString(8, null);
							pstmt1.setString(9, null);
							pstmt1.setString(10,null);
							pstmt1.setString(11, null);
							pstmt1.setString(12, null);
							pstmt1.setString(13, null);
							pstmt1.setString(14, DDTaskClosureDto.getValue());
							pstmt1.setLong(15,Long.parseLong(m6dto.getServiceListId()));
							pstmt1.execute();
						}*/
						
					}
				}
				
				//ArrayList<M6LOCDATADTO>  m6LOCDtoList=(ArrayList<M6LOCDATADTO>)hm.values();
				//for (M6LOCDATADTO m6locdatadto : m6LOCDtoList) 
				
				for (Object obj : hm.values()) {
					M6LOCDATADTO m6locdatadto=new M6LOCDATADTO();
					m6locdatadto=(M6LOCDATADTO)obj; 
					{
						if((!(m6locdatadto.getCktid()==null || m6locdatadto.getCktid()=="")) && (m6locdatadto.getLoc_date()==null || m6locdatedto.getCktid()==""))
						{
							
							pstmt1 = conn.prepareCall(sqlUpdateLOCDATE);
							pstmt1.setLong(1,Long.parseLong(m6locdatadto.getSpid()));
							pstmt1.execute();
							
							
						}
					}
				}
				
				if(sendStatus==1)
				{
					/*conn.commit();*/ //21-1-16
					m6dto.setStatus(sendStatus);
				}
		} catch (Exception e) {
			e.printStackTrace();
			sendStatus=-1;
			m6dto.setStatus(sendStatus);
		}finally
		{
			try {
				pstmt.close();
				pstmt1.close();
			} catch (SQLException e) {
				 Utility.onEx_LOG_RET_NEW_EX(e, "setStatus", "processResponseXML", null, true, true);  
			}
		}
		
		m6dto.setStatus(sendStatus);
		
	}


	public int fetchNextXMLData(Connection conn, FetchM6XMLDto m6dto,long id) throws Exception {
		int returnStatus=0;
		Statement stmt=null;
		PreparedStatement pstmt=null;
		  try {
               // save next data from xml to dto
				//read next xml string in str
			    String eventTypeIdStr=null;
			  
			    //[010]
				String sql=sqlGetNextResponseXml.replace("<id>",id+"");
				//[010]
				stmt=conn.createStatement();
				ResultSet rs=stmt.executeQuery(sql);
				if(!rs.next()) //no further records
				{
					returnStatus=Fetch_Status_NotFound;
				}
				else //record found
				{
					String readResponseXMLId=rs.getString("ID");
					String fileName=rs.getString("FILENAME");
					
					String updateStatus=sql_UpdateStatusInResponse;//"UPDATE [dbo].[tM6_NewOrder_Response] SET [status]=1 ,modifiedDate=getdate() WHERE ID="+readResponseXMLId;
					pstmt=conn.prepareStatement(updateStatus);
					pstmt.setShort(1, (short)1);
					pstmt.setString(2, readResponseXMLId);
					pstmt.execute(); 
					String absFileName=Messages.getMessageValue("M6_RESPONSE_PATH")+fileName;
					//String absFileName="c:\\1863_LastTask_OSN.xml";
					//File file = new File("c:\\1863_LastTask_OSN.xml");
					File file = new File(absFileName);
					DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
					DocumentBuilder db = dbf.newDocumentBuilder();
					Document doc = db.parse(file);
					doc.getDocumentElement().normalize();
		  
					NodeList nodeLst11 = doc.getElementsByTagName("OFEvent");
			  
					Node fstNode1 = nodeLst11.item(0);
			    
				if (fstNode1.getNodeType() == Node.ELEMENT_NODE) 
				{
				  Element fstElmnt = (Element) fstNode1;
				  NodeList crmorderIdElmntLst = fstElmnt.getElementsByTagName("crmorderId");
			      Element crmorderIdElmnt = (Element) crmorderIdElmntLst.item(0);
			      NodeList crmorderId = crmorderIdElmnt.getChildNodes();
			      m6dto.setCrmorderId(((Node) crmorderId.item(0)).getNodeValue().trim());
			      
			      NodeList ServiceListIdElmntLst = fstElmnt.getElementsByTagName("ServiceListId");
			      Element ServiceListIdElmnt = (Element) ServiceListIdElmntLst.item(0);
			      NodeList ServiceListId = ServiceListIdElmnt.getChildNodes();
			      m6dto.setServiceListId(((Node) ServiceListId.item(0)).getNodeValue().trim());
			      
			      NodeList PreOrderNoElmntLst = fstElmnt.getElementsByTagName("PreOrderNo");
			      Element PreOrderNoElmnt = (Element) PreOrderNoElmntLst.item(0);
			      if((Element) PreOrderNoElmntLst.item(0)!=null)
			      {
				      NodeList PreOrderNo = PreOrderNoElmnt.getChildNodes();
				      if(((Node) PreOrderNo.item(0))== null)
				      {
				    	  System.out.println("PreOrderNo : is null");
				      }
				      else
				      {
				    	  m6dto.setPreOrderNo(((Node) PreOrderNo.item(0)).getNodeValue().trim());
				    	 
				      }
			      }
			      else
			      {
			    	  System.out.println("event id is null");
			      }
			      //NodeList PreOrderNo = PreOrderNoElmnt.getChildNodes();
			     // m6dto.setPreOrderNo(((Node) PreOrderNo.item(0)).getNodeValue());
			      
			      NodeList M6OrderIdElmntLst = fstElmnt.getElementsByTagName("M6OrderId");
			      Element M6OrderIdElmnt = (Element) M6OrderIdElmntLst.item(0);
			      if((Element) M6OrderIdElmntLst.item(0)!=null)
			      {
				      NodeList M6OrderId = M6OrderIdElmnt.getChildNodes();
				      if(((Node) M6OrderId.item(0))== null)
				      {
				    	  System.out.println("M6OrderId : is null");
				      }
				      else
				      {
				    	  m6dto.setM6OrderId(((Node) M6OrderId.item(0)).getNodeValue().trim());
				    	 
				      }
			      }
			      else
			      {
			    	  System.out.println("M6OrderId is null");
			      }
			      //NodeList M6OrderId = M6OrderIdElmnt.getChildNodes();
			      //m6dto.setM6OrderId(((Node) M6OrderId.item(0)).getNodeValue());
			      
			      NodeList orderStatusElmntLst = fstElmnt.getElementsByTagName("orderStatus");
			      Element orderStatusElmnt = (Element) orderStatusElmntLst.item(0);
			      NodeList orderStatus = orderStatusElmnt.getChildNodes();
			      m6dto.setOrderStatus(((Node) orderStatus.item(0)).getNodeValue());
			   
			      NodeList eventIdElmntLst = fstElmnt.getElementsByTagName("eventId");
			      Element eventIdElmnt = (Element) eventIdElmntLst.item(0);
			   
			      if((Element) eventIdElmntLst.item(0)!=null)
			      {
				      NodeList eventId = eventIdElmnt.getChildNodes();
				      if(((Node) eventId.item(0))== null)
				      {
				    	  System.out.println("eventId : is null");
				      }
				      else
				      {
				    	  m6dto.setEventId(((Node) eventId.item(0)).getNodeValue().trim());
				    	 
				      }
			      }
			      else
			      {
			    	  System.out.println("event id is null");
			      }
			      NodeList eventTypeIdElmntLst = fstElmnt.getElementsByTagName("eventTypeId");
			      Element eventTypeIdElmnt = (Element) eventTypeIdElmntLst.item(0);
			      NodeList eventTypeId = eventTypeIdElmnt.getChildNodes();
			      m6dto.setEventTypeId(((Node) eventTypeId.item(0)).getNodeValue().trim());
			      eventTypeIdStr = ((Node) eventTypeId.item(0)).getNodeValue().trim();
			      
			      NodeList creationDateElmntLst = fstElmnt.getElementsByTagName("creationDate");
			      Element creationDateElmnt = (Element) creationDateElmntLst.item(0);
			      NodeList creationDate = creationDateElmnt.getChildNodes();
			      m6dto.setCreationDate(((Node) creationDate.item(0)).getNodeValue().trim());
			      
			      NodeList reasonElmntLst = fstElmnt.getElementsByTagName("reason");
			      Element reasonElmnt = (Element) reasonElmntLst.item(0);
			      NodeList reason = reasonElmnt.getChildNodes();
			      
			      if( eventTypeIdStr != null)
			      {
			    	  if(eventTypeIdStr .equalsIgnoreCase("2222"))
			    	  {
			    		m6dto.setReason(((Node) reason.item(0)).getNodeValue()); 
			    	  }
			    	  else if(eventTypeIdStr .equalsIgnoreCase("2233"))
			    	  {
			    		m6dto.setReason(((Node) reason.item(0)).getNodeValue()); 
			    	  }
			    	  else if(eventTypeIdStr .equalsIgnoreCase("4444"))
			    	  {
			    		m6dto.setReason("Sending Circuit Details for New Order."); 
			    	  }
			    	  else if(eventTypeIdStr .equalsIgnoreCase("5555"))
			    	  {
			    		m6dto.setReason("Order reached for PM enrichment."); 
			    	  }
			    	  else if(eventTypeIdStr .equalsIgnoreCase("6666"))
			    	  {
			    		m6dto.setReason("Sending Circuit Details for Change Order."); 
			    	  }
			    	  else if(eventTypeIdStr .equalsIgnoreCase("7777"))
			    	  {
			    		m6dto.setReason("Sending Hardware Details."); 
			    	  }
			    	  else if(eventTypeIdStr .equalsIgnoreCase("8888"))
			    	  {
			    		m6dto.setReason("Sending details when order is canceled."); 
			    	  }
			    	  else if(eventTypeIdStr .equalsIgnoreCase("9999"))
			    	  {
			    		m6dto.setReason("service closure(Order completed)"); 
			    	  }
			    	  //Start [001]
			    	  else if(eventTypeIdStr .equalsIgnoreCase("1111"))
			    	  {
			    		m6dto.setReason(((Node) reason.item(0)).getNodeValue()); 
			    	  }
			    	  //End [001]
				   } 
			      }
			  NodeList nodeLst = doc.getElementsByTagName("OFEventParams");
			  
			if(eventTypeIdStr.equalsIgnoreCase("4444") || eventTypeIdStr.equalsIgnoreCase("6666") || eventTypeIdStr.equalsIgnoreCase("7777") || eventTypeIdStr.equalsIgnoreCase("9999"))
			 {
				  ArrayList<FetchLastTaskNewOrderXMLDto> dto =  new ArrayList<FetchLastTaskNewOrderXMLDto>();
				m6dto.setFetchLastTaskNewOrderXMLDtoList(new ArrayList<FetchLastTaskNewOrderXMLDto>());
			  for (int s = 0; s < nodeLst.getLength(); s++) {

				  Node fstNode = nodeLst.item(s);
			    
			    if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
			  
			      Element Elmnt = (Element) fstNode;
			      FetchLastTaskNewOrderXMLDto obj = new FetchLastTaskNewOrderXMLDto();
			     
			      NodeList verbElmntLst = Elmnt.getElementsByTagName("verb");
			      Element verbElmnt = (Element) verbElmntLst.item(0);
			      NodeList verb = verbElmnt.getChildNodes();
			      obj.setVerb(((Node) verb.item(0)).getNodeValue().trim());
			      
			      NodeList orderLineNoElmntLst = Elmnt.getElementsByTagName("orderLineNo");
			      Element orderLineNoElmnt = (Element) orderLineNoElmntLst.item(0);
			      NodeList orderLineNo = orderLineNoElmnt.getChildNodes();
			      //obj.setOrderLineNo(((Node) orderLineNo.item(0)).getNodeValue().trim());
                  //neha
			      if(orderLineNo.getLength() > 0){
			    	  obj.setOrderLineNo(((Node) orderLineNo.item(0)).getNodeValue().trim());
			      }
			      //neha
			      //Ramana:if orderline of above is not null or empty string   
			      	//if serviceproductid stored in main dto is not null or empty
			      		//update serviceproductid in main dto from orderline info 
			      if(obj.getOrderLineNo()!= null || obj.getOrderLineNo()!= "" )
			      {
			    	  if( m6dto.getServiceProductId()== null || m6dto.getServiceProductId() == "")
			    	  {
			    		  m6dto.setServiceProductId(obj.getOrderLineNo());
			    	  }
			      }
			      
			      NodeList paramKeyElmntLst = Elmnt.getElementsByTagName("paramKey");
			      Element paramKeyElmnt = (Element) paramKeyElmntLst.item(0);
			      NodeList paramKey = paramKeyElmnt.getChildNodes();
			    //[007] START 
			      if(eventTypeIdStr.equalsIgnoreCase("9999"))
			    	  obj.setParamKey(((Node) paramKey.item(0)).getNodeValue());
			       else
			    	  obj.setParamKey(((Node) paramKey.item(0)).getNodeValue().trim());
			    //[007] END
			      NodeList valueElmntLst = Elmnt.getElementsByTagName("value");
			      Element valueElmnt = (Element) valueElmntLst.item(0);
			      NodeList value = valueElmnt.getChildNodes();
			      //obj.setValue(((Node) value.item(0)).getNodeValue().trim());
				  //added null check for value tag
			      if(value.item(0)!=null){
				        if(((Node) value.item(0)).getNodeValue()!=null && (!((Node) value.item(0)).getNodeValue().equalsIgnoreCase(""))){
				    	  obj.setValue(((Node) value.item(0)).getNodeValue().trim());  
				      }else{
				    	  obj.setValue("");
				      }
		    	  }else{
		    		  obj.setValue("");
		    	  }
			      
			      //neha
			      if(obj.getParamKey().equalsIgnoreCase("Desired Due Date")){
			    	  //set in main dto and skip adding in arraylist
			    	  m6dto.setDesiredDueDate(obj.getValue());
			      } else{
			    	  obj.setOrderLineNo(obj.getOrderLineNo().trim());
			    	  m6dto.getFetchLastTaskNewOrderXMLDtoList().add(obj);
			      }
			      
			      //neha
			      
			      
			    }

			  }
			 }
			m6dto.setReadResponseXMLId(readResponseXMLId);
			returnStatus=Fetch_Status_Found;
			}
		  } catch (Exception e) {
		    e.printStackTrace();
		  }
		 finally
		 {
			 DbConnection.closeStatement(stmt);
			 DbConnection.closePreparedStatement(pstmt);
		 }
		  return returnStatus;
		/*
		int returnStatus=0;
		Statement stmt=null;
		try {
			//save next data from xml to dto
				//read next xml string in str
				String sql=sqlGetNextResponseXml;
				stmt=conn.createStatement();
				ResultSet rs=stmt.executeQuery(sql);
				if(!rs.next()) //no further records
				{
					returnStatus=Fetch_Status_NotFound;
				}
				else //record found
				{
					String readResponseXMLId=rs.getString("ID");
					String fileName=rs.getString("FILENAME");
					String updateStatus=sql_UpdateStatusInResponse;//"UPDATE [dbo].[tM6_NewOrder_Response] SET [status]=1 ,modifiedDate=getdate() WHERE ID="+readResponseXMLId;
					PreparedStatement pstmt=conn.prepareStatement(updateStatus);
					pstmt.setShort(1, (short)1);
					pstmt.setString(2, readResponseXMLId);
					pstmt.execute();  
					String absFileName=Messages.getMessageValue("M6_RESPONSE_PATH")+fileName;
					FileInputStream fileInputStream=new FileInputStream(absFileName);
					byte[] data=new byte[4*1024];
					StringBuilder xmlData=new StringBuilder();
					int length=fileInputStream.read(data);
					while(length!=-1)
					{
						xmlData.append(new String(data,0,length));
						data=new byte[4*1024];
						length=fileInputStream.read(data);
					}
					
					String xmlString=xmlData.toString();
					System.err.println(xmlData.toString());
//					 from str start populating dto
					StringReader strRead = null;
					XMLInputFactory xmlif = null;
					XMLStreamReader xmlr = null;
					
					strRead = new StringReader(xmlString);
					
					//System.setProperty("javax.xml.stream.XMLInputFactory","javax.xml.stream.XMLInputFactory");
					
					xmlif = XMLInputFactory.newInstance();
					xmlr = xmlif.createXMLStreamReader(strRead);
					boolean 	crmorderId	=	false;
					boolean 	ServiceListId	=	false;
					boolean 	PreOrderNo	=	false;
					boolean 	M6OrderId	=	false;
					boolean 	orderStatus	=	false;
					boolean 	eventId	=	false;
					boolean 	eventTypeId	=	false;
					boolean 	creationDate	=	false;
					boolean 	reason	=	false;
					//Raghu
					boolean 	verb	=	false;
					boolean 	orderLineNo	=	false;
					boolean 	paramKey	=	false;
					boolean 	value	=	false;
					boolean 	OFEventParams	=	false;
					m6dto.setFetchLastTaskNewOrderXMLDtoList(new ArrayList<FetchLastTaskNewOrderXMLDto>());
					while (xmlr.hasNext()) {
						//System.err.println(" hello "+ xmlr.next());
						int j =xmlr.next();
						switch (j) {
						case XMLStreamReader.CHARACTERS:
							
							if (crmorderId == true) {
								m6dto.setCrmorderId(xmlr.getText());
							}
							if (ServiceListId == true) {
								m6dto.setServiceListId(xmlr.getText());
							}
							if (PreOrderNo == true) {
								m6dto.setPreOrderNo(xmlr.getText());
							}
							if (M6OrderId == true) {
								m6dto.setM6OrderId(xmlr.getText());
							}
							if (orderStatus == true) {
								m6dto.setOrderStatus(xmlr.getText());
							}
							if (eventId == true) {
								m6dto.setEventId(xmlr.getText());
							}
							if (eventTypeId == true) {
								m6dto.setEventTypeId(xmlr.getText());
							}
							if (creationDate == true) {
								m6dto.setCreationDate(xmlr.getText());
							}
							if (reason == true) {
								m6dto.setReason(xmlr.getText());
							}
							//raghu
							if (OFEventParams == true && verb == true) {
								ArrayList<FetchLastTaskNewOrderXMLDto> dto=m6dto.getFetchLastTaskNewOrderXMLDtoList();
								dto.get(dto.size()-1).setVerb(xmlr.getText());
							}
							if (OFEventParams == true && orderLineNo == true) {
								ArrayList<FetchLastTaskNewOrderXMLDto> dto=m6dto.getFetchLastTaskNewOrderXMLDtoList();
								dto.get(dto.size()-1).setOrderLineNo(xmlr.getText());
							}
							if (OFEventParams == true && paramKey == true) {
								ArrayList<FetchLastTaskNewOrderXMLDto> dto=m6dto.getFetchLastTaskNewOrderXMLDtoList();
								dto.get(dto.size()-1).setParamKey(xmlr.getText());
							}
							if (OFEventParams == true && value == true) {
								ArrayList<FetchLastTaskNewOrderXMLDto> dto=m6dto.getFetchLastTaskNewOrderXMLDtoList();
								dto.get(dto.size()-1).setValue(xmlr.getText());
							}
							//System.err.println("Inner Text : "+xmlr.getText());
							break;
						case XMLStreamReader.START_ELEMENT:
							switch (XMLTags.TAGS.valueOf(xmlr.getLocalName())) {
									case OFEvent:		
													break;
									case crmorderId:		
													crmorderId=true;	
													break;
									case ServiceListId :
													ServiceListId=true;	
													break;
									case PreOrderNo :
													PreOrderNo=true;	
													break;
									case M6OrderId :
													M6OrderId=true;	
													break;
									case orderStatus :
													orderStatus=true;	
													break;
									case eventId :
													eventId=true;	
													break;
									case eventTypeId :
													eventTypeId=true;	
													break;
									case creationDate :
													creationDate=true;	
													break;
									case reason :
													reason=true;	
													break;
								//raghu
									case OFEventParams:	
													OFEventParams=true;
													FetchLastTaskNewOrderXMLDto fetchLastTaskDto= new FetchLastTaskNewOrderXMLDto();
													m6dto.getFetchLastTaskNewOrderXMLDtoList().add(fetchLastTaskDto);
													break;
									case verb :
													verb=true;	
													break;
									case orderLineNo :
													orderLineNo=true;	
													break;
									case paramKey :
													paramKey=true;	
													break;
									case value :
													value=true;	
													break;
							}
							//System.err.println("Start Element : "+xmlr.getLocalName());
							break;
							
						case XMLStreamReader.END_ELEMENT:
							
							switch (XMLTags.TAGS.valueOf(xmlr.getLocalName())) {
									case OFEvent:		
													break;
									case crmorderId:		
													crmorderId=false;	
													break;
									case ServiceListId :
													ServiceListId=false;	
													break;
									case PreOrderNo :
													PreOrderNo=false;	
													break;
									case M6OrderId :
													M6OrderId=false;	
													break;
									case orderStatus :
													orderStatus=false;	
													break;
									case eventId :
													eventId=false;	
													break;
									case eventTypeId :
													eventTypeId=false;	
													break;
									case creationDate :
													creationDate=false;	
													break;
									case reason :
													reason=false;	
													break;
													
									//	raghu
									case OFEventParams:	
													OFEventParams=false;
													break;
									case verb :
													verb=true;	
													break;
									case orderLineNo :
													orderLineNo=false;	
													break;
									case paramKey :
													paramKey=false;	
													break;
									case value :
													value=false;	
													break;
							}
							//System.err.println("END_ELEMENT : "+xmlr.getLocalName());	
							break;
							}
					}
					m6dto.setReadResponseXMLId(readResponseXMLId);
					returnStatus=Fetch_Status_Found;
				}
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			throw Utility.onEx_LOG_RET_NEW_EX(ex, "fetchNextXMLData", "processResponseXML", null, true, true); 
		}
		finally
		{
			try {
				stmt.close();
			} catch (SQLException e) {
				throw Utility.onEx_LOG_RET_NEW_EX(e, "fetchNextXMLData", "processResponseXML", null, true, true); 
			}
		}
		
		
		
		return returnStatus;
	*/
		  
	
	}

	public static void main(String[] args) {
		/*new ProcessResponseXML().processResponseJob();*/
	}
	
	private void updatestageToGetEvent7777(Connection conn, FetchM6XMLDto m6dto) throws Exception{
		// TODO Auto-generated method stub
				PreparedStatement pstmt=null;
				PreparedStatement pstmtHwOsnRecDate=null;
				try
				{
					pstmt=conn.prepareStatement(sql_UpdateSatge);
					pstmt.setString(1, m6dto.getEventTypeId());
					pstmt.setLong(2, Long.parseLong(m6dto.getCrmorderId()));
					pstmt.execute();
					
					pstmtHwOsnRecDate=conn.prepareStatement(sql_HardwareOsnRecDate);
					pstmtHwOsnRecDate.setLong(1, Long.parseLong(m6dto.getServiceProductId()));
					pstmtHwOsnRecDate.execute();
				}finally
				{
					DbConnection.closePreparedStatement(pstmtHwOsnRecDate);
					DbConnection.closePreparedStatement(pstmt);
				}
	}
	
	private void saveLineCancelationXmlData(Connection conn, FetchM6XMLDto m6dto) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt=null;
		int i=1;
		try {
				pstmt=conn.prepareStatement(sql_CancelLineResponse);
				pstmt.setLong(i++, Long.parseLong(m6dto.getServiceListId()));//Request Id
				pstmt.setString(i++, m6dto.getCrmorderId());
				pstmt.setString(i++, m6dto.getEventTypeId());
				pstmt.setString(i++, m6dto.getOrderStatus());
				pstmt.setString(i++, m6dto.getReason());
				pstmt.execute();  
				m6dto.setStatus(1);
			
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			Utility.onEx_LOG_RET_NEW_EX(ex, "setStatus", "processCancelLineResponseXML", null, true, true);
			m6dto.setStatus(-1);
			ex.printStackTrace();
		}finally
		{
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				Utility.onEx_LOG_RET_NEW_EX(e, "setStatus", "processCancelLineResponseXML", null, true, true);  
				e.printStackTrace();
			}
		}
	}
	
	private void saveAttrValClosureData(Connection conn, FetchM6XMLDto m6dto){
		int sendStatus=0;
		CallableStatement pstmt=null;
		int sqlState=0;
		int errorCode=0;
		String errMess=null;
		try {
			String circuitIdDesc=Utility.getAppConfigValue(AppConstants.CIRCUIT_ID_ATT_DESC_KEY);
			conn.setAutoCommit(false);
			String sql=sql_AttrValClosureResponse;
			pstmt=conn.prepareCall(sql);
			pstmt.setLong(1, Long.parseLong(m6dto.getCrmorderId()));
			pstmt.setLong(2, Long.parseLong(m6dto.getServiceListId()));
				for (FetchLastTaskNewOrderXMLDto AttrValClosureDto : m6dto.getFetchLastTaskNewOrderXMLDtoList())
				{
					if(AttrValClosureDto.getOrderLineNo() != null)
					{
						Utility.LOG(true,true,"line number  : "+AttrValClosureDto.getOrderLineNo());
						Utility.LOG(true,true,"att name   : "+AttrValClosureDto.getParamKey());
						Utility.LOG(true,true,"att val   : "+AttrValClosureDto.getValue());
						pstmt.setLong(3, Long.parseLong(AttrValClosureDto.getOrderLineNo()));
						pstmt.setString(4, AttrValClosureDto.getParamKey());
						pstmt.setString(5, AttrValClosureDto.getValue());
						pstmt.setNull(6, java.sql.Types.BIGINT);
						pstmt.setNull(7, java.sql.Types.BIGINT);
						pstmt.setNull(8, java.sql.Types.VARCHAR);
						pstmt.execute();
						sqlState=pstmt.getInt(6);
						errorCode=pstmt.getInt(7);
						errMess=pstmt.getString(8);
						Utility.LOG(true,true,"errMess   : "+errMess);
						if(circuitIdDesc!=null && !circuitIdDesc.equalsIgnoreCase("") && circuitIdDesc.equalsIgnoreCase(AttrValClosureDto.getParamKey()) &&
								AttrValClosureDto.getValue()!=null && !AttrValClosureDto.getValue().equalsIgnoreCase("")){
							circuitMap.put(Integer.parseInt(AttrValClosureDto.getOrderLineNo()), AttrValClosureDto.getValue());
						}
					}
				}
				
				sendStatus=1;
				if(sendStatus==1)
				{
					conn.commit();
				}
		} catch (SQLException e) {
			e.printStackTrace();
			sendStatus=-1;
			Utility.onEx_LOG_RET_NEW_EX(e, "saveAttrValClosureData", "processResponseXML", "sqlState"+sqlState+"errorCode"+errorCode+"errMess"+errMess, true, true);
		} catch (Exception e) {
			e.printStackTrace();
			sendStatus=-1;
			Utility.onEx_LOG_RET_NEW_EX(e, "saveAttrValClosureData", "processResponseXML", null, true, true);
		}finally
		{
			try {
				pstmt.close();
				if(conn.getAutoCommit()==false){
					conn.setAutoCommit(true);
				}
			} catch (SQLException e) {
				 Utility.onEx_LOG_RET_NEW_EX(e, "saveAttrValClosureData", "processResponseXML", "sqlState"+sqlState+"errorCode"+errorCode+"errMess"+errMess, true, true);  
			}catch (Exception e) {
				e.printStackTrace();
				Utility.onEx_LOG_RET_NEW_EX(e, "saveAttrValClosureData", "processResponseXML", null, true, true);
				sendStatus=-1;
			}
		}
		
		m6dto.setSend_status(sendStatus);
		
	}
	
	/**
	 * This method will create xml,update nfa number and insert created xml for PR creation in case of 2222 event
	 * @param conn,orderNumber,serviceId,eventTypeId, m6OrderId
	 * @return Nothing.
    */
	private void updateNfaAndInsertInScmTable(Connection conn,String orderNumber,String serviceId,String eventTypeId,String m6OrderId){
		try {
			if(orderNumber!=null && !orderNumber.equalsIgnoreCase("") &&
					serviceId!=null && !serviceId.equalsIgnoreCase("") &&
					m6OrderId!=null && !m6OrderId.equalsIgnoreCase("") ){
				
				scmBLObj.updateNfaAndInsertInScmTable(conn, Integer.parseInt(orderNumber),  Integer.parseInt(serviceId), eventTypeId, Integer.parseInt(m6OrderId));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, "updateNfaAndInsertInScmTable", "processResponseXML", null, true, true);
		}
	}
	
	/**
	 * This method will create and insert created xml for Circuit Update in case of 9999 event
	 * @param conn,orderNumber,serviceId,eventTypeId, m6OrderId
	 * @return Nothing.
    */
	private void insertInScmTableCircuitUpdate(Connection conn,String orderNumber,String serviceId,String eventTypeId,HashMap<Integer,String> circuitMap,String m6OrderId){
		try {
			scmBLObj.insertInScmTableCircuitUpdate(conn, Integer.parseInt(orderNumber),  Integer.parseInt(serviceId), eventTypeId,circuitMap,Integer.parseInt(m6OrderId));
		} catch (Exception e) {
			e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, "insertInScmTableCircuitUpdate", "processResponseXML", null, true, true);
		}
	}
	//[006] Start
	/**
	 * This method will create xml,update nfa number and insert created xml for PR Reuse in case of 9999 event
	 * @param conn,orderNumber,serviceId,eventTypeId, m6OrderId
	 * @return Nothing.
    */
	private void insertInScmTablePRReuse(Connection conn,String orderNumber,String serviceId,String eventTypeId,String m6OrderId){
		try {
			scmBLObj.insertInScmTablePRReuse(conn, Integer.parseInt(orderNumber),  Integer.parseInt(serviceId), eventTypeId,m6OrderId);
		} catch (Exception e) {
			e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, "insertInScmTablePRReuse", "processResponseXML", null, true, true);
		}
	}
	//[006] End
	
	//neha
	private void saveDesiredDueDate(Connection conn, FetchM6XMLDto m6dto){
		
        int sendStatus=0;
        PreparedStatement preparedStatement =null;
        String sql_UpdateDesiredDueDate = "UPDATE IOE.TPOSERVICEMASTER SET DESIRED_DUE_DATE = ? WHERE SERVICEID = ? AND ORDERNO = ?";
        try{
        	preparedStatement = conn.prepareStatement(sql_UpdateDesiredDueDate);
        	preparedStatement.setString(1, m6dto.getDesiredDueDate());
        	preparedStatement.setLong(2, Long.parseLong(m6dto.getServiceListId()));
        	preparedStatement.setLong(3, Long.parseLong(m6dto.getCrmorderId()));
        	
        	preparedStatement.executeUpdate();
        	sendStatus = 1;
        	
        }
        catch(Exception e){
        	Utility.onEx_LOG_RET_NEW_EX(e, "setStatus", "processCancelLineResponseXML", 
        			"CrmOrderId:"+m6dto.getCrmorderId()+",\nServiceListId:"+m6dto.getServiceListId(), true, true);
        	sendStatus = -1;
        }
        finally{
        	DbConnection.closeStatement(preparedStatement);
        }
        
        m6dto.setSend_status(sendStatus);
        
	}
	//neha
	//[011] start
	/**
	 * 
	 * @param str
	 * @return
	 * 	true if validation pass : "str is null or if not null then it should be numeric"
	 * 	false if validation failure
	 */
	public static boolean validateLineItem(String str)
	{

		if(str!=null)
		{					
			if(str.matches("[0-9]+$"))
								return true;	
			else
				return false;
				
			
		}
				return true;		
	}
	//[011] end

}
