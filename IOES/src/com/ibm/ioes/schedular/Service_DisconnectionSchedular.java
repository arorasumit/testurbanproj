/*
 * [001]   Gunjan Singla     19-May-14     regarding disconnection of FX service having pending charges
 * [002]   Raveendra    28-Nov-2014        Set processing count 10 in case of data issue exception is generated when scheduler is in progress
 * 
 * */

package com.ibm.ioes.schedular;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ibm.fx.dto.ExtendedData;
import com.ibm.fx.dto.ServiceDisconnectionDto;
import com.ibm.fx.mq.IOESKenanManager;

import com.ibm.ioes.exception.IOESException;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Utility;

public class Service_DisconnectionSchedular {
		
	private static String sqlGet_Select_Service_For_Disconnection_Schedular="{CALL IOE.FX_GET_SERVICE_FOR_DISCONNECTON_SCHEDULAR(?)}";
	
	private String FXServiceDisconnectionStatusUpdate = "UPDATE IOE.TFX_SERVICE_DISCONNECTION DIS SET PROCESSING_STATUS = 1,LAST_UPDATED_DATE = CURRENT TIMESTAMP "+ 
														"WHERE DIS.PROCESSING_STATUS in (4)";

	private String sqlFX_SCHEDULER_SUCCESS	=	"{CALL IOE.FX_SCHEDULER_SUCCESS_FOR_SERVICE_DISCONNECTION(?,?,?)}";
	//Add by [002]
	private String sqlFX_SCHEDULER_FAILURE  =   "{CALL IOE.FX_SCHEDULER_FAILURE_FOR_SERVICE_DISCONNECTION(?,?,?,?)}";

	private static String sqlUpdateOrder="{CALL IOE.FX_DISCONNECTION_STATUS_OF_ORDER(?)}";
	
	private static final String sqlDisableEntries = 
			" update ioe.TFX_SERVICE_DISCONNECTION "+
			" set TRANSACTION_REPUSH_COUNT=101 "+
			" WHERE SERVICEPRODUCTID in(select SERVICEPRODUCTID from ioe.TFX_SERVICECREATE where ACCTEXTERNALID=?) "+
			" and PROCESSING_STATUS in (1,4) ";
	
	
	/*   	********************************************************************************
	 *		Function Name:- process_charges_for_disconnection
	 *		Created By:-    Sandeep Aggarwal
	 * 		Created On:-    10-FEB-2011
	 * 		Purpose:-		Process the charges for disconnection
	 *      ********************************************************************************
	 */
	public  void process_service_for_disconnection(long orderno) {
		
		
		try
		{
			IOESKenanManager API= null ;
			Utility.LOG(true, true, " Push.....");
			Connection connection = null;	
			PreparedStatement pstmtCheckPendingActivity = null;
			//arraySericeList=null;			
			try
			{			
				API=new IOESKenanManager();
				API.loginKenan();
				
				connection = DbConnection.getConnectionObject();
				try
				{
						// Raghu : found difference when compared with previous prod ear
					statusUpdateForFailesServiceDisconnection(connection);
				}
				catch(Exception ex)
				{
					Utility.LOG(true,true,ex,null);
				}
				connection.setAutoCommit(false);
				//Start [002]
				ArrayList<String> dataIssueException=Utility.getdataIssueException(connection);
				//End [002]
				
				ServiceDisconnectionDto Service = null;
				//[001]
				String ValidDisconnectionServiceSearch = "select 'RC' as PENDINGCHARGETYPE from ioe.TFX_RC_CREATE RCCREATE where RCCREATE.SERVICEPRODUCTID=?"
						+ " AND  FX_SCHEDULAR_CREATE_STATUS in (1,2,4) and RCCREATE.BILLINGACTIVEDT<?"
						+ " union select 'NRC' as PENDINGCHARGETYPE from ioe.TFX_NRC_CREATE NRCCREATE where NRCCREATE.SERVICEPRODUCTID=?"
						+ " AND  FX_SCHEDULAR_CREATE_STATUS in (1,2,4) and NRCCREATE.EFFECTIVE_DATE< ?"
						+ " union select 'COMPONENT' as PENDINGCHARGETYPE from ioe.TFX_COMPONENT_CREATE COMPCREATE  where COMPCREATE.SERVICEPRODUCTID=?"
						+ " AND  FX_SCHEDULAR_CREATE_STATUS in (1,2,4) and COMPCREATE.BILLINGACTIVEDT<?"
						+ " union select 'RC_Disconnection' as PENDINGCHARGETYPE from ioe.TFX_DISCONNECTION TD WHERE TD.CHARGEINFOID in"
						+ " (SELECT CHARGEINFOID FROM ioe.TCHARGES_INFO WHERE SERVICEPRODUCTID=?)"
						+ " and TD.PROCESSING_STATUS in (1,2,4) and TD.DISCONNECTION_DATE<? "
						+ " union select 'COMPONENT_Disconnection' as PENDINGCHARGETYPE from ioe.TFX_COMPONENT_DISCONNECTION TCD where TCD.COMPONENTINFOID in"
						+ " (SELECT COMPONENTINFOID FROM ioe.TCOMPONENT_INFO WHERE SERVICEPRODUCTID=?)"
						+ " and TCD.PROCESSING_STATUS in(1,2,4) and TCD.DISCONNECTION_DATE< ?";
				pstmtCheckPendingActivity=connection.prepareStatement(ValidDisconnectionServiceSearch);
				do {
					pstmtCheckPendingActivity.clearParameters();
					try
					{
//						 Raghu : found difference when compared with previous prod ear
						Service = get_service_for_disconnection(connection,orderno);
						connection.commit();
					}
					catch(Exception ex)
					{
						Utility.LOG(true,true,ex,null);
						connection.commit();
						//logExceptionForPackage(Package,ex);//--Later
						continue;
					}
					if(Service!=null)			
					{	
							//[001]
								validateService(pstmtCheckPendingActivity,Service);
								if(Service.getReturnStatus()!=-1){
									API.disconnectService(Service,"");
								}
								
								try
								{	// Raghu : found difference when compared with previous prod ear
									
									 //[002] Change method saveDisconnectionStatus to add new parameter dataIssueException object
                                    saveDisconnectionStatus(connection,Service,dataIssueException);
									connection.commit();
								}
								catch(Exception ex)
								{
									Utility.LOG(true,true,ex,null);
									/*String msg=logIdentifier+"Exception in saving FX New Order Package Schedular final " +
											"Result for PackageproductId :"+Package.getPackageid()
										+" \n Result was returnStatus="+Package.getReturnStatus()
										+" \n ackage.getPackage_inst_id()= , if any ,="+Package.getPackage_inst_id()
										+" \n Package.getPackage_inst_id_serv()= , if any ,="+Package.getPackage_inst_id_serv()
										+" \n Exception , if any, = "+((Package.getException()==null)?null:Utility.getStackTrace(Package.getException()))
										+" \n Exception Message , if any,= "+Package.getExceptionMessage();
									Utility.LOG(true,true,ex,msg);*/
									connection.rollback();
									
									
								}finally{
									if(Service.isExceptionInCancel()==true){
										int status=disableEntries(Service,connection);
										if(status==1){
											connection.commit();
										}else{
											connection.rollback();
										}
									}
									
								}
								
							}// end of the for loop
							
					}while(Service!=null);// end of while loop
				}
				finally
				{
					DbConnection.closePreparedStatement(pstmtCheckPendingActivity);
					try
					{
						if(connection!=null)
						{
							DbConnection.freeConnection(connection);
						}
					}
					catch(Exception ex)
					{
						Utility.LOG(true,true,ex,null);
					}
					API.close();
					
				}
				
				
			}catch(Throwable th) {
				
				Utility.LOG(true,true,new Exception(th),null);
				
			}
			
	}	
		//[001]	
	public void validateService(PreparedStatement pstmt,ServiceDisconnectionDto service) throws Exception  {
		
		ResultSet rs = null;
		
		ArrayList<String> listPendingChargeType=new ArrayList<String>();
		try {
			pstmt.setLong(1, service.getServiceproductid());
			pstmt.setDate(2, new Date(service.getService_disconnection_date().getTime()));
			pstmt.setLong(3, service.getServiceproductid());
			pstmt.setDate(4, new Date(service.getService_disconnection_date().getTime()));
			pstmt.setLong(5, service.getServiceproductid());
			pstmt.setDate(6, new Date(service.getService_disconnection_date().getTime()));
			pstmt.setLong(7, service.getServiceproductid());
			pstmt.setTimestamp(8, service.getService_disconnection_date());
			pstmt.setLong(9, service.getServiceproductid());
			pstmt.setTimestamp(10, service.getService_disconnection_date());
			
			
			
			rs = pstmt.executeQuery();
			while (rs.next()) {
				service.setReturnStatus(-1);
				
				listPendingChargeType.add(rs.getString("PENDINGCHARGETYPE"));
				
			}
			DbConnection.closeResultset(rs);
			if(service.getReturnStatus()==-1){
				String msg = "Cannot Disconnect Service since " + listPendingChargeType.toString()+ "  pending";
				service.setException(new Exception(msg));
				service.setExceptionMessage(msg);
			}
			
		} catch (SQLException e) {
			service.setReturnStatus(-1);
			Utility.LOG(true,false,e,null);
			service.setException(e);
		}
		finally{
			DbConnection.closeResultset(rs);
		}
		
				
	}

	  private int disableEntries(ServiceDisconnectionDto service,
		Connection connection) {
			int status=0;
			PreparedStatement pstmt = null;
			try{
				pstmt = connection.prepareStatement(sqlDisableEntries);
				pstmt.setString(1, service.getAcctExternalId());
				pstmt.executeUpdate();
				
				status=1;
			}catch(Exception ex){
				Utility.LOG(true,true,ex,null);
			}finally{
				try {
					DbConnection.closePreparedStatement(pstmt);
				} catch (Exception e) {
					Utility.LOG(true,true,e,null);
				}
			}
			return status;
		}	
	  
		/*   	********************************************************************************
		 *		Function Name:- get_charge_list_for_disconnection
		 *		Created By:-    Sandeep Aggarwal
		 * 		Created On:-    10-FEB-2011
		 * 		Purpose:-		To Get All The Charge List For Disconnection
		 *      ********************************************************************************
		 */ 

		  ServiceDisconnectionDto get_service_for_disconnection(Connection connection,long Orderno) throws Exception{
			
			
			Utility.LOG(true, true, " IN  get_service_for_disconnection() function of DisconnectionSchedular class");
	        CallableStatement csService_Info_For_Disconnection=null;
	        //ArrayList alSelect_Service_Details_For_Disconnection=null;
	        ResultSet rsService_Details_For_Disconnection=null;
	        ServiceDisconnectionDto objDisconnectionDto = null;
			
			
			try 
			{
							
				
							
				csService_Info_For_Disconnection= connection.prepareCall(sqlGet_Select_Service_For_Disconnection_Schedular);
				
				csService_Info_For_Disconnection.setLong(1,Orderno);
				rsService_Details_For_Disconnection=csService_Info_For_Disconnection.executeQuery();
				Utility.LOG(true, true, "Fetch charges for disconnection ");
							if(rsService_Details_For_Disconnection.next())/*fetch rc from for disconnection*/ {
								try {
										objDisconnectionDto	=	new ServiceDisconnectionDto();
										objDisconnectionDto.setIndex_key(rsService_Details_For_Disconnection.getInt("INDEX_KEY"));
										objDisconnectionDto.setService_disconnection_date((rsService_Details_For_Disconnection.getTimestamp("SERVICE_DISCONNECTION_DATE")));									
										objDisconnectionDto.setView_id(rsService_Details_For_Disconnection.getString("VIEWID"));									
										objDisconnectionDto.setAcctExternalId(rsService_Details_For_Disconnection.getString("ACCTEXTERNALID"));
										objDisconnectionDto.setOrderNo(rsService_Details_For_Disconnection.getInt("ORDERNO"));
										//[001]
										objDisconnectionDto.setServiceproductid(rsService_Details_For_Disconnection.getLong("SERVICEPRODUCTID"));
										
										ArrayList<ExtendedData> extendedData = Utility.getFxExtendedData(connection,objDisconnectionDto.getIndex_key(),ExtendedData.AssociatedWith_ORDER_FOR_SERVICE_DISCONNECTION);
										objDisconnectionDto.setOrderExtendedData(extendedData);
										//alSelect_Service_Details_For_Disconnection.add(objDisconnectionDto);
										
										String msg = "Service details to be disconnected are "
											+"\n INDEX_KEY "+rsService_Details_For_Disconnection.getInt("INDEX_KEY");
										Utility.LOG(true, true, msg);	
										
										
								}	
								catch(Exception ex)
								{
									
									String msg="Exception in retrieving charges for DISCONNECTION  Schedular. for index value " +objDisconnectionDto.getIndex_key()
									+" \n Exception , if any, = "+((objDisconnectionDto.getException()==null)?null:Utility.getStackTrace(objDisconnectionDto.getException()))
									+" \n Exception Message , if any,= "+objDisconnectionDto.getExceptionMessage();
									
									Utility.LOG(true,true,ex,msg);
								}
								finally
								{
									DbConnection.closeResultset(rsService_Details_For_Disconnection);
									DbConnection.closeCallableStatement(csService_Info_For_Disconnection);
								}
									
									
							}// end of the while loop
						
				}catch (Exception e) {
					
					Utility.LOG(true,true,e,null);
							 
				} 
				return objDisconnectionDto;
			
		}// end of the function

		
		
		public void saveDisconnectionStatus(Connection connection,ServiceDisconnectionDto Service, ArrayList<String> dataIssueException ) throws Exception
		{
			
			
			if(Service.getReturnStatus()==1)//SUCCESS
			{
						
				CallableStatement cstmt_Service_Success = null;
				PreparedStatement updateOrderStatus = null;
				try
				{
					cstmt_Service_Success=connection.prepareCall(sqlFX_SCHEDULER_SUCCESS);
					cstmt_Service_Success.setLong(1, Service.getIndex_key());
					cstmt_Service_Success.setLong(2, 0);					
					cstmt_Service_Success.setString(3,Service.getTokenid());
					cstmt_Service_Success.executeUpdate();
					
//TODO : My code goes here
				
				
						updateOrderStatus = connection.prepareStatement(sqlUpdateOrder);
						updateOrderStatus.setInt(1, Service.getOrderNo());
						updateOrderStatus.executeUpdate();
				
					
				}
				finally
				{
					if(cstmt_Service_Success!=null) cstmt_Service_Success.close();
				}
				//return serDto;
				//update FX_STATUS in TPOPackageDETIALS
				//UPDATE subscr no , reset in TFX_Package create
			}
			else
			{
				//Start [002]
				boolean isDataIssueException=Utility.isDataIssueException(dataIssueException,Service.getException());
				Service.setDataIssueException(isDataIssueException);
				//End [002]
				CallableStatement cstmt_Service_Failure = null;
				try
				{
					cstmt_Service_Failure=connection.prepareCall(sqlFX_SCHEDULER_FAILURE);
					cstmt_Service_Failure.setLong(1, Service.getIndex_key());
					java.sql.Clob clobData = 
						com.ibm.db2.jcc.t2zos.DB2LobFactory.createClob(Utility.getStackTrace(Service.getException()));
					cstmt_Service_Failure.setClob(2, clobData );
					cstmt_Service_Failure.setString(3, Service.getExceptionMessage());
					//Start [002]
					if(Service.isDataIssueException()){
						cstmt_Service_Failure.setLong(4, 1);
					}else{
						cstmt_Service_Failure.setLong(4, 0);
					}
					//End [002]
     				cstmt_Service_Failure.executeUpdate();
				
				}
				finally
				{
					if(cstmt_Service_Failure!=null) cstmt_Service_Failure.close();
				}
				//update FX_STATUS in TPOPackageDETIALS
				//LOG exception and errmsg//-Later
				//update TFX_Package.EXCEPTION_HISTORY_ID//-Later
			}
		}
		
		public void statusUpdateForFailesServiceDisconnection(Connection connection)
		{PreparedStatement pstmt=null;
			 try {
				pstmt=connection.prepareStatement(FXServiceDisconnectionStatusUpdate);
				pstmt.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				Utility.LOG(true,true,e,null);
			}
			finally
			{
				try
				{
				DbConnection.closePreparedStatement(pstmt);
				}
				catch(Exception ex)
				{
					Utility.LOG(true,true,ex,null);
				}
			}
			
		}
		
		
	

}
	
	
	

