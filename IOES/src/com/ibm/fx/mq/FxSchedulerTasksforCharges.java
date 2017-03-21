//tag		Name       Date			CSR No			Description 
//[001]   Raveendra    02-Dec-2014                  Set processing count 10 in case of data issue exception is generated when scheduler is in progress

package com.ibm.fx.mq;

import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import com.ibm.fx.dto.DisconnectionDto;
import com.ibm.fx.dto.ExtendedData;
import com.ibm.fx.dto.NrcDto;
import com.ibm.fx.dto.RcDto;
import com.ibm.fx.dto.ServiceDTO;
import com.ibm.fx.mq.ApplicationClone.TaskType;
import com.ibm.ioes.beans.ViewOrderDto;
import com.ibm.ioes.service.UtilityService;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Utility;

public class FxSchedulerTasksforCharges {


	//Fetches for FX New Order Schedular , next back or current dated (not future )service which are to be pushed to FX 
	private String sqlFX_NO_SCH_GETRCCHARGESDATA = "call IOE.FX_NO_SCH_GETRCCHARGESDATA(?)";
	//Fetched Exxtenal Ids for the servics fetched in sqlFX_NO_SCH_GETSERVICESDATA (ie above)
	private String sqlFX_NO_SCH_GETSERVICES_ID_DATA = "call FX_NO_SCH_GETSERVICE_ID_DATA(?)";
	//Fetched ExtendedData for the service fetched in sqlFX_NO_SCH_GETSERVICESDATA (ie above)
	private String sqlFX_NO_SCH_GETSERVICES_EXT_DATA = "call FX_NO_SCH_GETSERVICE_EXT_DATA(?)";
	//private String sqlFX_SCHEDULER_CHARGES_RC_SUCCESS="call IOE.FX_SCHEDULER_CHARGES_RC_SUCCESS(?,?,?)";
	private String sqlFX_SCHEDULER_CHARGES_RC_SUCCESS=
			"UPDATE IOE.TFX_RC_CREATE SET FX_SCHEDULAR_CREATE_STATUS = 3,TOKEN_ID = ?,VIEWID = ?,LAST_MODIFIED_DATE=CURRENT TIMESTAMP," +
			"TRACKING_ID=?,TRACKING_ID_SERV=? WHERE ID=?";
	// Start [001]
	private String sqlFX_SCHEDULER_CHARGES_RC_FAILURE="call IOE.FX_SCHEDULER_CHARGES_RC_FAILURE(?,?,?,?)";
	// End [001]
	private String sqlFX_NO_SCH_GETNRCCHARGESDATA = "call IOE.FX_NO_SCH_GETNRCCHARGESDATA(?)";
	private String sqlFX_SCHEDULER_CHARGES_NRC_SUCCESS="call IOE.FX_SCHEDULER_CHARGES_NRC_SUCCESS(?,?,?)";
	// Start [001]
	private String sqlFX_SCHEDULER_CHARGES_NRC_FAILURE="call IOE.FX_SCHEDULER_CHARGES_NRC_FAILURE(?,?,?,?)";
	// End [001]
	private String FXRCStatusUpdate="UPDATE IOE.TFX_RC_CREATE SET FX_SCHEDULAR_CREATE_STATUS=1 WHERE FX_SCHEDULAR_CREATE_STATUS=4 ";
	private String FXNRCStatusUpdate="UPDATE IOE.TFX_NRC_CREATE SET FX_SCHEDULAR_CREATE_STATUS=1 WHERE FX_SCHEDULAR_CREATE_STATUS=4 ";
	
	public static final String CHARGE_LEVEL_ACCOUNT="ACCOUNT";
	public static final String CHARGE_LEVEL_SERVICE="SERVICE";
	private final String sqlChargeDisconnectUpdateion = 
			"UPDATE IOE.TFX_DISCONNECTION DIS SET PROCESSING_STATUS = 3,LAST_UPDATED_DATE = CURRENT TIMESTAMP,TOKEN_ID=? where INDEX_KEY=?";
	private String getIds_NRC=" SELECT ID CHARGE_ID FROM IOE.TFX_NRC_CREATE TFX_NRC_CREATE"+
													" WHERE (TFX_NRC_CREATE.ORDERNO=? or 0=?) AND "+ 
                                                    " TFX_NRC_CREATE.FX_SCHEDULAR_CREATE_STATUS=1"+
													" AND TFX_NRC_CREATE.EFFECTIVE_DATE<=(Current date)"+
                                                    " AND (PROCESSING_COUNT) < (SELECT BIGINT(KEYVALUE)"+
													" FROM IOE.TM_APPCONFIG WHERE KEYNAME='PROCESSING_COUNT')"+
                                                    " order by ID fetch first <rowcount> rows only" +
                                                    " with ur 	";	
	
	// static class to create Callable for NRCs
	// Shubhranshu
	static class NRCcallable implements Callable<Object> {
		
		private long id=0;
		public NRCcallable(long id)
		{
			this.id=id;			
		}
		@Override
		public String call() throws Exception
		{
			(new FxSchedulerTasksforCharges()).pushNRCChargesToFXById(this.id);
			return "0";
		};
	}
// end of ststic class
	
	/**	
	*	This function pushes today's or backdated RC Charges to FX for New Order
	*/
	public void pushRCChargesToFX(long orderno)
	{
		try
		{
			IOESKenanManager API= null ;
			Utility.LOG(true, true, " IN  pushChargesToFX()");
			Connection connection = null;	
			int prev_status = 0;
			try
			{			
				API=new IOESKenanManager();
				API.loginKenan();
				
				connection = DbConnection.getConnectionObject();
				
				try
				{
					statusUpdateforRCFailed(connection);
				}
				catch(Exception ex)
				{
					Utility.LOG(true,true,ex,null);
				}
				
				connection.setAutoCommit(false);
				RcDto rccharge = null;
				// Start [001]
				ArrayList<String> dataIssueException=Utility.getdataIssueException(connection);
				// End [001]
				do
				{
					//fetch one service
					try
					{
						rccharge = fetchNextRCCharge(connection,orderno);
						connection.commit();
					}
					catch(Exception ex)
					{
						Utility.LOG(true,true,ex,null);
						connection.commit();
						//logExceptionForService(service,ex);//--Later
						continue;
					}
					if(rccharge!=null)
					{
						//push to FX
						//if service level and subscr no null
						//set flag = service level subscr no null
						int flag=1;
						if(CHARGE_LEVEL_SERVICE.equals(rccharge.getFx_Level()) &&
								(rccharge.getSubScrNo()==null || "".equals(rccharge.getSubScrNo()) ))
						{
							flag=0;
						}
						
						String logIdentifier="RC-NRC Charge SCHEDULAR ERRROR , CHARGEINFOID:"+rccharge.getChargeinfoid()+"\n";
//						if flag is off ie no error then
						if(flag==1)
						{	
							prev_status = 	FxOrderTrackerTask.checkPreviousStatus(connection,API,rccharge,FxOrderTrackerConst.PRODUCT);						
							if(prev_status == FxOrderTrackerConst.CANELLED || prev_status == FxOrderTrackerConst.NoPreviousLOG ) 
							{
								API.sendRcToFX(rccharge,logIdentifier);
							}
							
							
						}
						else
						{
						//else
							String msg=logIdentifier+"Cannot create charge since service(subscr==null) is not present in FX . " +
							"\n  -For Chargeinfoid :"+rccharge.getChargeinfoid()+
							",\n  -Account was :"+rccharge.getFx_Ext_Account_No();
							AppConstants.IOES_LOGGER.info(msg);
							rccharge.setReturnStatus(-1);
							rccharge.setException(new Exception(msg));
						}
						//save results	
						try
						{
							// Start [001]
							saveRcPushResult(connection,rccharge,dataIssueException);
							// End [001]
							connection.commit();
						}
						catch(Exception ex)
						{
							String msg=logIdentifier+"Exception in saving FX New Order RC charge Schedular. final Result for chargeinfoid :"+rccharge.getChargeinfoid()
								+" \n Result was returnStatus="+rccharge.getReturnStatus()
								+" \n Exception , if any, = "+((rccharge.getException()==null)?null:Utility.getStackTrace(rccharge.getException()))
								+" \n Exception Message , if any,= "+rccharge.getExceptionMessage();
							Utility.LOG(true,true,ex,msg);
							connection.rollback();
						}
					}
				
				}while(rccharge!=null);

			}
			finally
			{
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
		}
		catch(Throwable th)
		{
			Utility.LOG(true,true,new Exception(th),null);
		}

		
	}
	/**	
	*	This function pushes today's or backdated NRC Charges to FX for New Order
	*/
	public void pushNRCChargesToFX(long orderno)
	{
			try {
				//
				(new FxSchedulerTasksforCharges()).statusUpdateforNRCFailed(); //
				//Close connection afterwareds
				} 
			catch (Exception e) {
				//e.printStackTrace();
				Utility.LOG(e);
				}								
								
									ApplicationClone clone=ApplicationClone.getInstance(new UtilityService()); // Insatance of Singleton class 									
									List<Integer>nrcIds;									
									TaskType tasktypename=ApplicationClone.TaskType.NRC_TO_FX;
									
									do
									{	
											//nrcIds= fetchPendingNRCChargeIds(taskQueueSize,orderno);	
										
											nrcIds= fetchPendingNRCChargeIds(AppConstants.MAX_TASKS_IN_QUEUE,orderno);
											
												if(!(nrcIds.isEmpty()))
												{	
														 List<Callable<Object>> callablelist=new ArrayList<Callable<Object>>(); // to hold callable objects														 																					 
														 for(long listentry:nrcIds) 
														 {
															callablelist.add(new NRCcallable(listentry)); 												 
														 }														 																
														 try {
															 clone.submitTaskToPool(callablelist, tasktypename);															 																								
														} catch (Exception e) {														
															Utility.LOG(e);
														}
												}												
									}while(!(nrcIds.isEmpty()));
	}
	// Added by Shubhranshu on 1-dec-2015
	private ArrayList<Integer> fetchPendingNRCChargeIds(int taskQueueSize,long orderno) 
	{
				PreparedStatement psmt=null;
				ResultSet rst=null;
				Connection connection=null;
				ArrayList<Integer> nrcChargeIDs = new ArrayList<Integer>() ;
				
				try {			
					
							connection = DbConnection.getConnectionObject();
							//stmt = connection.createStatement();							
							psmt=connection.prepareStatement(getIds_NRC.replace("<rowcount>", taskQueueSize+"")) ;	
																					
							psmt.setLong(1, orderno);
							psmt.setLong(2, orderno);					
							rst=psmt.executeQuery();	
												
							while(rst.next())
							{								
								nrcChargeIDs.add(rst.getInt("CHARGE_ID")); 							
						    }
					}				
					catch (Exception e) {								
						Utility.LOG(e);
					}
					finally
					{
							try {
								DbConnection.closeResultset(rst);  
								DbConnection.closePreparedStatement(psmt);									
								DbConnection.freeConnection(connection);
							}catch (Exception e) {
								Utility.LOG(e);
						    }
					}
					return nrcChargeIDs;
	}
	
	/**	
	*	This function pushes today's or backdated NRC Charges to FX for New Order
	*/
	public void pushNRCChargesToFXById(long id)/*long orderno,*/ 
	{
		try
		{
			IOESKenanManager API= null ;
			Utility.LOG(true, true, " IN  pushChargesToFX() For Id :"+id);
			Connection connection = null;	
			try
			{			
				API=new IOESKenanManager();
				API.loginKenan();
				int prev_status = 0;
							
				connection = DbConnection.getConnectionObject();
				
			/*	try
				{
					statusUpdateforNRCFailed(connection); //DONE IN PUSHNRCTFX
				}
				catch(Exception ex)
				{
					Utility.LOG(true,true,ex,null);
				}				
				connection.setAutoCommit(false);*/  // status update is already done.
				
				NrcDto nrccharge = null;
				//Start [001]
				ArrayList<String> dataIssueException=Utility.getdataIssueException(connection);
				//End [001]
				/*do
				{*/
				//fetch one service
				try
				{
					nrccharge = fetchNextNRCCharge(connection,id /*orderno*/);
					connection.commit();
				}
				catch(Exception ex)
				{
					connection.commit();
					Utility.LOG(true,true,ex,null);
					//logExceptionForService(service,ex);//--Later-TODO
					return;
				}
				if(nrccharge!=null)
				{
					//push to FX
					//if service level and subscr no null
					//set flag = service level subscr no null
					
					System.out.println("processing "+id+"for amount "+nrccharge.getNrcAmount()); // TODO to be removed
					
					int flag=1;
					if(CHARGE_LEVEL_SERVICE.equals(nrccharge.getFx_Level()) &&
							(nrccharge.getSubScrNo()==null || "".equals(nrccharge.getSubScrNo()) ))
					{
						flag=0;
					}
					String logIdentifier="RC-NRC Charge SCHEDULAR ERRROR , CHARGEINFOID:"+nrccharge.getChargeinfoid()+"\n";
//						if flag is off ie no error then
						if(flag==1)
						{	prev_status = 	FxOrderTrackerTask.checkPreviousStatus(connection,API,nrccharge,FxOrderTrackerConst.NRC);						
							if(prev_status == FxOrderTrackerConst.CANELLED || prev_status == FxOrderTrackerConst.NoPreviousLOG ){
								API.sendNRcToFX(nrccharge,logIdentifier);
							}
							
						}
						else
						{
							String msg=logIdentifier+"Cannot create charge since service(subscr==null) is not present in FX . " +
							"\n  -For Chargeinfoid :"+nrccharge.getChargeinfoid()+
							",\n  -Account was :"+nrccharge.getFx_Ext_Account_No();
							AppConstants.IOES_LOGGER.info(msg);
							nrccharge.setReturnStatus(-1);
							nrccharge.setException(new Exception(msg));
						}
							//save results	
						try
						{
							//[001] add dataIssueException in saveNrcPushResult
							saveNrcPushResult(connection,nrccharge,dataIssueException);
							connection.commit();
						}
						catch(Exception ex)
						{
							String msg=logIdentifier+"Exception in saving FX New Order NRC charge Schedular. final Result for chargeinfoid :"+nrccharge.getChargeinfoid()
								+" \n Result was returnStatus="+nrccharge.getReturnStatus()
								+" \n Exception , if any, = "+((nrccharge.getException()==null)?null:Utility.getStackTrace(nrccharge.getException()))
								+" \n Exception Message , if any,= "+nrccharge.getExceptionMessage();
							Utility.LOG(true,true,ex,msg);
							connection.rollback();
						}
					}
				
				/*}while(nrccharge!=null);*/

			}
			finally
			{
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
		}
		catch(Throwable th)
		{
			Utility.LOG(true,true,new Exception(th),null);
		}

		
	}

	
	public void statusUpdateforRCFailed(Connection connection)
	{
		PreparedStatement pstmt=null;
		 try {
			pstmt=connection.prepareStatement(FXRCStatusUpdate);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try
			{
			if(pstmt!=null)DbConnection.closePreparedStatement(pstmt);
			}
			catch(Exception ex){ex.printStackTrace();}
		}
		
	}

	RcDto fetchNextRCCharge(Connection connection,long Orderno) throws Exception
	{
		RcDto rcDto=null;
		ResultSet rs_rc_charges=null;
		CallableStatement cstmt_Service = null;
		try
		{
			cstmt_Service=connection.prepareCall(sqlFX_NO_SCH_GETRCCHARGESDATA);
			cstmt_Service.setLong(1,Orderno);
			rs_rc_charges=cstmt_Service.executeQuery();
			if(rs_rc_charges.next())
			{
				rcDto = new RcDto();
				
				rcDto.setRowId(rs_rc_charges.getLong("ID"));
				rcDto.setElementId((rs_rc_charges.getString("ELEMENTID")==null)?null:rs_rc_charges.getInt("ELEMENTID"));
				rcDto.setOpenItemId((rs_rc_charges.getString("OPENITEMID")==null)?null:rs_rc_charges.getInt("OPENITEMID"));
				
				rcDto.setBillingActiveDate(rs_rc_charges.getDate("BILLINGACTIVEDT"));
				//rcDto.setBillingEndDate(rs_rc_charges.getDate("BILLING_END_DATE"));
				rcDto.setInArrearsOverride((rs_rc_charges.getString("INARREARSOVERRIDE")==null)?null:rs_rc_charges.getInt("INARREARSOVERRIDE"));
				rcDto.setBillPeriod(rs_rc_charges.getString("BILLPERIOD"));
				
				rcDto.setOverrideRate(rs_rc_charges.getString("OVERRIDE_RATE"));
				//Commented by Ravneet : Start
				//We will not push charge inactive date to FX
				//	rcDto.setBillingEndDate(rs_rc_charges.getDate("BILLING_END_DATE"));
				//End
				rcDto.setFx_Ext_Account_No(rs_rc_charges.getString("FX_EXT_ACCOUNT_NO"));
				rcDto.setFx_Level(rs_rc_charges.getString("FX_LEVEL"));
				rcDto.setChargeinfoid(rs_rc_charges.getInt("CHARGEINFOID"));
				
				if(rs_rc_charges.getString("SUBSCRNO")!=null)
						rcDto.setSubScrNo(rs_rc_charges.getInt("SUBSCRNO"));
				
				if(rs_rc_charges.getInt("IS_DIFFERENTIAL_FOR_FX")==1){
					
					rcDto.setIsDifferentialForFX((rs_rc_charges.getString("IS_DIFFERENTIAL_FOR_FX")==null)?null:rs_rc_charges.getInt("IS_DIFFERENTIAL_FOR_FX"));
					
					DisconnectionDto objDisconnectionDto = new DisconnectionDto();
					
					objDisconnectionDto.setIndex_key(rs_rc_charges.getInt("DIS_INDEX_KEY"));
					objDisconnectionDto.setCharge_disconnection_date(rs_rc_charges.getTimestamp("DIS_CHARGE_DISCONNECTION_DATE"));
					//objDisconnectionDto.setCharge_active_date(rs_rc_charges.getDate("CHARGE_ACTIVE_DATE"));
					objDisconnectionDto.setView_id(rs_rc_charges.getString("OLD_VIEWID"));									
					//objDisconnectionDto.setCharge_inactive_date(rs_rc_charges.getDate("CHARGE_INACTIVE_DATE"));
					objDisconnectionDto.setSchedular_status(rs_rc_charges.getInt("OLD_FX_SCHEDULAR_CREATE_STATUS"));
					//objDisconnectionDto.setCharge_info_id(rs_rc_charges.getInt("CHARGEINFOID"));
					//objDisconnectionDto.setOrderno(rs_rc_charges.getInt("ORDERNO"));
					//objDisconnectionDto.setFxAccountInternalId(rs_rc_charges.getInt("INTERNAL_ID"));
					
					//ArrayList<ExtendedData> extendedData = Utility.getFxExtendedData(conn,objDisconnectionDto.getIndex_key(),ExtendedData.AssociatedWith_ORDER_FOR_CHARGE_DISCONNECTION);
					//objDisconnectionDto.setOrderExtendedData(extendedData);
					
					objDisconnectionDto.setProcessing_status(rs_rc_charges.getInt("DIS_PROCESSING_STATUS"));
					objDisconnectionDto.setTrackingId(rs_rc_charges.getInt("OLD_TRACKING_ID"));
					objDisconnectionDto.setTrackingIdServ(rs_rc_charges.getInt("OLD_TRACKING_ID_SERV"));
					
					rcDto.setLinkedDisconnectionCharge(objDisconnectionDto);
					
				}
				
				
				ArrayList<ExtendedData> extendedData = Utility.getFxExtendedData(connection,rcDto.getRowId(),ExtendedData.AssociatedWith_PRODUCT);
				rcDto.setExtendedData(extendedData);
				
				extendedData = Utility.getFxExtendedData(connection,rcDto.getRowId(),ExtendedData.AssociatedWith_ORDER_FOR_PRODUCT);
				rcDto.setOrderExtendedData(extendedData);
			}
			
			
		}
		finally
		{
			if(rs_rc_charges!=null)DbConnection.closeResultset(rs_rc_charges);
			if(cstmt_Service!=null)DbConnection.closeCallableStatement(cstmt_Service) ;
			
		}
		return rcDto;
	}

	public void saveRcPushResult(Connection connection,RcDto rccharge,ArrayList<String> dataIssueException) throws Exception
	{
		
		if(rccharge.getReturnStatus()==1)//SUCCESS
		{
//			update FX_SCHEDD..STATUS in tfx_rc_create
			//UPDATE viewid in tfx_rc_create
			
			CallableStatement cstmt_Service_Success = null;
			PreparedStatement pstmtDisconnectionEntryUpdation = null;
			try
			{
				cstmt_Service_Success=connection.prepareCall(sqlFX_SCHEDULER_CHARGES_RC_SUCCESS);
				cstmt_Service_Success.setString(1, rccharge.getToken_id());
				cstmt_Service_Success.setString(2, rccharge.getViewId());
				cstmt_Service_Success.setInt(3, rccharge.getTrackingId());
				cstmt_Service_Success.setInt(4, rccharge.getTrackingIdServ());
				cstmt_Service_Success.setLong(5, rccharge.getRowId());
				cstmt_Service_Success.executeUpdate();
				
				if(rccharge.isDifferentialHitInFX()){
					//update token for disconnection entry
					pstmtDisconnectionEntryUpdation=connection.prepareStatement(sqlChargeDisconnectUpdateion);
					pstmtDisconnectionEntryUpdation.setString(1, rccharge.getToken_id());
					pstmtDisconnectionEntryUpdation.setLong(2, rccharge.getLinkedDisconnectionCharge().getIndex_key());
					pstmtDisconnectionEntryUpdation.executeUpdate();
				}
			}
			finally
			{
				if(cstmt_Service_Success!=null)DbConnection.closeCallableStatement(cstmt_Service_Success) ;
				if(pstmtDisconnectionEntryUpdation!=null)DbConnection.closePreparedStatement(pstmtDisconnectionEntryUpdation) ;
			}
			
			
		}
		else
		{
//			update FX_SCHEDD..STATUS in tfx_rc_create
			//LOG exception and errmsg//-Later
			//update TFX_SERVICE.EXCEPTION_HISTORY_ID//-Later
			
			
			// Start [001]		
			boolean isDataIssueException=Utility.isDataIssueException(dataIssueException,rccharge.getException());
			rccharge.setDataIssueException(isDataIssueException);
			// End [001]		
			CallableStatement cstmt_Service_Failure = null;
			try
			{
				cstmt_Service_Failure=connection.prepareCall(sqlFX_SCHEDULER_CHARGES_RC_FAILURE);
				cstmt_Service_Failure.setLong(1, rccharge.getRowId());

				
				java.sql.Clob clobData = 
						com.ibm.db2.jcc.t2zos.DB2LobFactory.createClob(Utility.getStackTrace(rccharge.getException()));
				cstmt_Service_Failure.setClob(2, clobData);
				cstmt_Service_Failure.setString(3, rccharge.getExceptionMessage());

				
				// Start [001]
				if(rccharge.isDataIssueException()){
					cstmt_Service_Failure.setLong(4, 1);
				}else{
					cstmt_Service_Failure.setLong(4, 0);
				}
				// End [001]
				cstmt_Service_Failure.executeUpdate();
			}
			finally
			{
				if(cstmt_Service_Failure!=null) DbConnection.closeCallableStatement(cstmt_Service_Failure);
			}


		}
	}
	
	public void statusUpdateforNRCFailed()throws Exception
	{
		Connection connection=DbConnection.getConnectionObject();
		PreparedStatement pstmt=null;
		 try {
			pstmt=connection.prepareStatement(FXNRCStatusUpdate);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
					Utility.LOG(e); //e.printStackTrace();
		}
		finally
		{
			try
			{
			DbConnection.closePreparedStatement(pstmt);
					DbConnection.freeConnection(connection);
			}
			catch(Exception ex)
			{
						//ex.printStackTrace();
						Utility.LOG(ex);
			}
		}
		
	}

	NrcDto fetchNextNRCCharge(Connection connection,long id) throws Exception
	{
		NrcDto nrcDto=null;
		
		CallableStatement cstmt_Service = null;
		try
		{
			cstmt_Service=connection.prepareCall(sqlFX_NO_SCH_GETNRCCHARGESDATA);
			cstmt_Service.setLong(1,id); // modified
			ResultSet rs_nrc_charges=cstmt_Service.executeQuery();
			if(rs_nrc_charges.next())
			{
				nrcDto = new NrcDto();
				
				nrcDto.setRowId(rs_nrc_charges.getLong("ID"));
				nrcDto.setTypeidNrc((rs_nrc_charges.getString("TYPEIDNRC")==null)?null:rs_nrc_charges.getInt("TYPEIDNRC"));
				nrcDto.setEffectiveDate(rs_nrc_charges.getDate("EFFECTIVE_DATE"));
				nrcDto.setNrcAmount(rs_nrc_charges.getString("NRC_AMOUNT"));
				nrcDto.setFx_Ext_Account_No(rs_nrc_charges.getString("FX_EXT_ACCOUNT_NO"));
				nrcDto.setFx_Level(rs_nrc_charges.getString("FX_LEVEL"));
				nrcDto.setChargeinfoid(rs_nrc_charges.getInt("CHARGEINFOID"));
				
				nrcDto.setSubScrNo(rs_nrc_charges.getString("SUBSCRNO"));
				
				
				ArrayList<ExtendedData> extendedData = Utility.getFxExtendedData(connection,nrcDto.getRowId(),ExtendedData.AssociatedWith_NRC);
				nrcDto.setExtendedData(extendedData);
				
				extendedData = Utility.getFxExtendedData(connection,nrcDto.getRowId(),ExtendedData.AssociatedWith_ORDER_FOR_NRC);
				nrcDto.setOrderExtendedData(extendedData);
			}
			
			
		}
		finally
		{
			if(cstmt_Service!=null) cstmt_Service.close();
			
		}
		return nrcDto;
	}

	public void saveNrcPushResult(Connection connection, NrcDto nrccharge,ArrayList<String> dataIssueException) throws Exception
	{
		
		if(nrccharge.getReturnStatus()==1)//SUCCESS
		{
			//update FX_SCHEDULAR_CREATE_STATUS in TFX_NRC_CREATE
			//UPDATE VIEWID TFX_NRC_CREATE
			
			CallableStatement cstmt_Service_Success = null;
			try
			{
				cstmt_Service_Success=connection.prepareCall(sqlFX_SCHEDULER_CHARGES_NRC_SUCCESS);
				cstmt_Service_Success.setLong(1, nrccharge.getRowId());
				cstmt_Service_Success.setString(2, nrccharge.getViewId());
				cstmt_Service_Success.setString(3, nrccharge.getToken_id());
				cstmt_Service_Success.executeUpdate();
				

			}
			finally
			{
				if(cstmt_Service_Success!=null) cstmt_Service_Success.close();
			}
			
		}
		else
		{
			//update FX_SCHEDULAR_CREATE_STATUS in TFX_NRC_CREATE
			//start [001]
			boolean isDataIssueException=Utility.isDataIssueException(dataIssueException,nrccharge.getException());
			nrccharge.setDataIssueException(isDataIssueException);
			//end [001]
			
			
			CallableStatement cstmt_Service_Failure = null;
			try
			{
				cstmt_Service_Failure=connection.prepareCall(sqlFX_SCHEDULER_CHARGES_NRC_FAILURE);
				cstmt_Service_Failure.setLong(1, nrccharge.getRowId());
				java.sql.Clob clobData = 
					com.ibm.db2.jcc.t2zos.DB2LobFactory.createClob(Utility.getStackTrace(nrccharge.getException()));
				cstmt_Service_Failure.setClob(2, clobData );
				cstmt_Service_Failure.setString(3, nrccharge.getExceptionMessage());
				
				
				//start [001] 
				if(nrccharge.isDataIssueException()){
					cstmt_Service_Failure.setLong(4, 1);
				}else{
					cstmt_Service_Failure.setLong(4, 0);
				}
				
				//end [001]
				
				cstmt_Service_Failure.executeUpdate();
				

			}
			finally
			{
				if(cstmt_Service_Failure!=null) cstmt_Service_Failure.close();
			}
			
		}
	}
	
}
