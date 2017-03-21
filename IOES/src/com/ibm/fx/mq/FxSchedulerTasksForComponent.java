//tag		Name       Date			CSR No			Description 
//[001]   Raveendra    28-Nov-2014                  Set processing count 10 in case of data issue exception is generated when scheduler is in progress

package com.ibm.fx.mq;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ibm.fx.dto.ComponentDTO;
import com.ibm.fx.dto.ExtendedData;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Utility;


public class FxSchedulerTasksForComponent {
	


	
	

//		Fetches for FX New Order Schedular , next back or current dated (not future )Package which are to be pushed to FX 
		private String sqlFX_NO_SCH_GETCOMPONENTDATA = "call IOE.FX_NO_SCH_GETCOMPONENTDATA(?)";
		
		private String sqlFX_SCHEDULER_SUCCESS="call IOE.FX_SCHEDULER_SUCCESS_FOR_COMPONENT(?,?,?,?)";
		//Start [001]
		private String sqlFX_SCHEDULER_FAILURE="call IOE.FX_SCHEDULER_FAILURE_FOR_COMPONENT(?,?,?,?)";
		//End [001]
		private String FXComponentStatusUpdate="UPDATE IOE.TFX_COMPONENT_CREATE SET FX_SCHEDULAR_CREATE_STATUS=1 WHERE FX_SCHEDULAR_CREATE_STATUS=4 ";
		public static final String COMPONENT_LEVEL_ACCOUNT="ACCOUNT";
		public static final String COMPONENT_LEVEL_SERVICE="SERVICE";
		
		/**	
		*	This function pushes today's or backdated Packages to FX for New Order
		*/
		public void pushComponentToFX(long orderno)
		{
			try
			{
				IOESKenanManager API= null ;
				Utility.LOG(true, true, " IN  pushComponentsToFX()");
				Connection connection = null;
				int prev_status = 0;
				
				
				try
				{			
					API=new IOESKenanManager();
					API.loginKenan();
					
					connection = DbConnection.getConnectionObject();
					
					try
					{
						statusUpdateforComponentFailed(connection);
					}
					catch(Exception ex)
					{
						Utility.LOG(true,true,ex,null);
					}				
					
					connection.setAutoCommit(false);
					//Start [001]
					ArrayList<String> dataIssueException=Utility.getdataIssueException(connection);
					//End [001]
					ComponentDTO Component = null;
					do
					{
						//fetch one Component
						try
						{
							Component = fetchNextComponent(connection,orderno);
							connection.commit();
						}
						catch(Exception ex)
						{
							Utility.LOG(true,true,ex,null);
							connection.commit();
							//logExceptionForComponent(Component,ex);//--Later
							continue;
						}
						if(Component!=null) 
						{ 
						//push to FX
//							push to FX
							//if service level and subscr no null
							//set flag = service level subscr no null
							int flag=1;
							if(Component.getPackageid()==null||"".equals(Component.getPackageid())||
							   Component.getPackage_inst_id_serv()==null||"".equals(Component.getPackage_inst_id_serv())||
							   Component.getComponentid()==null||"".equals(Component.getComponentid())||
							   Component.getPackage_inst_id()==null||"".equals(Component.getPackage_inst_id())||
							   "".equals(Component.getBillingActiveDate()))
							{
								
								flag = 0;
								
								
							}else 
							{
								if(COMPONENT_LEVEL_SERVICE.equals(Component.getFx_Level()) &&
										(Component.getSubScrNo()==null || "".equals(Component.getSubScrNo())))
												
										 		
								{
									flag=0;
								}
							}
								
							
							String logIdentifier="Component SCHEDULAR ERRROR , ComponentID:"+Component.getComponentid()+"\n";
							if(flag == 1){
								
								
								prev_status = 	FxOrderTrackerTask.checkPreviousStatus(connection,API,Component,FxOrderTrackerConst.COMPONENT);						
								if(prev_status == FxOrderTrackerConst.CANELLED || prev_status == FxOrderTrackerConst.NoPreviousLOG ) 
								{
									API.sendComponentToFx(Component,"");
								}
							
								
							}
							else
							{
							//else
								String msg=logIdentifier+"Cannot create component since service(subscr==null) or package is not present in FX . " +
								"\n  -For ComponentID :"+Component.getComponentid()+
								",\n  -Package Id was :"+Component.getPackageid()+
								",\n  -Row Id was :"+Component.getRowId();
								AppConstants.IOES_LOGGER.info(msg);
								Component.setReturnStatus(-1);
								Component.setException(new Exception(msg));
							}
							//save results
							
							try
							{	
								//[001] Change method saveComponentPushResult to add new parameter dataIssueException object
								saveComponentPushResult(connection,Component,dataIssueException);
								connection.commit();
							}
							catch(Exception ex)
							{
								String msg=logIdentifier+"Exception in saving FX New Order Component Schedular final " +
										"Result for ComponentID :"+Component.getComponentid()
									+" \n Result was returnStatus="+Component.getReturnStatus()
									+" \n Component.getComponent_inst_id()= , if any ,="+Component.getComponent_inst_id()
									+" \n Row Id was :"+Component.getRowId()
									+" \n Component.getComponent_inst_id_serv()= , if any ,="+Component.getComponent_inst_id_serv()
									+" \n Exception , if any, = "+((Component.getException()==null)?null:Utility.getStackTrace(Component.getException()))
									+" \n Exception Message , if any,= "+Component.getExceptionMessage();
								Utility.LOG(true,true,ex,msg);
								connection.rollback();
							}
						}
					}while(Component!=null);

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

		
		public void statusUpdateforComponentFailed(Connection connection)
		{
			PreparedStatement pstmt=null;
			 try {
				pstmt=connection.prepareStatement(FXComponentStatusUpdate);
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
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}
			
		}
		
	    public 	ComponentDTO fetchNextComponent(Connection connection,long orderno) throws Exception
		{
			ComponentDTO comDto=null;
			
			CallableStatement cstmt_Component = null;
			ResultSet rs=null;
			
			try
			{
				cstmt_Component=connection.prepareCall(sqlFX_NO_SCH_GETCOMPONENTDATA);
				cstmt_Component.setLong(1,orderno);
				rs=cstmt_Component.executeQuery();
				if(rs.next())
				{
					comDto=new ComponentDTO();
					comDto.setRowId(rs.getLong("ID"));
					comDto.setBillingActiveDate(rs.getDate("BILLINGACTIVEDT"));
					comDto.setComponentid(rs.getString("COMPONENT_ID"));
					comDto.setPackageid(rs.getString("PACKAGE_ID"));
					comDto.setPackage_inst_id(rs.getString("PACKAGE_INST_ID"));
					comDto.setPackage_inst_id_serv(rs.getString("PACKAGE_INST_ID_SERV"));
					comDto.setSubScrNo(rs.getString("SUBSCRNO"));
					comDto.setSubScrNoReset(rs.getInt("SUBSCRNORESET"));
					comDto.setFx_Level(rs.getString("FX_LEVEL"));
					comDto.setAccountExternalId(rs.getString("FX_EXT_ACCOUNT_NO"));
					
					ArrayList<ExtendedData> extendedData = Utility.getFxExtendedData(connection,comDto.getRowId(),ExtendedData.AssociatedWith_ORDER_FOR_COMPONENT);
					comDto.setOrderExtendedData(extendedData);
				}

			}
			
			finally
			{
				if(rs!=null)DbConnection.closeResultset(rs);
				if(cstmt_Component!=null) DbConnection.closeCallableStatement(cstmt_Component);
				
			}
			return comDto;
		}

		public void saveComponentPushResult(Connection connection,ComponentDTO Component, ArrayList<String> dataIssueException) throws Exception
		{
			
			if(Component.getReturnStatus()==1)//SUCCESS
			{
						
				CallableStatement cstmt_Component_Success = null;
				try
				{
					cstmt_Component_Success=connection.prepareCall(sqlFX_SCHEDULER_SUCCESS);
					cstmt_Component_Success.setLong(1, Component.getRowId());
					cstmt_Component_Success.setLong(2, new Long(Component.getComponent_inst_id()));
					cstmt_Component_Success.setLong(3, new Long (Component.getComponent_inst_id_serv()));
					cstmt_Component_Success.setString(4,Component.getTokenid());
					cstmt_Component_Success.executeUpdate();					

				}
				finally
				{
					if(cstmt_Component_Success!=null)DbConnection.closeCallableStatement(cstmt_Component_Success);
				}
				//return serDto;
				//update FX_STATUS in TPOComponentDETIALS
				//UPDATE subscr no , reset in TFX_Component create
			}
			else
			{
			
				//Start [001]
				boolean isDataIssueException=Utility.isDataIssueException(dataIssueException,Component.getException());
				Component.setDataIssueException(isDataIssueException);
				//End [001]
				CallableStatement cstmt_Component_Failure = null;
				try
				{
					cstmt_Component_Failure=connection.prepareCall(sqlFX_SCHEDULER_FAILURE);
					cstmt_Component_Failure.setLong(1, Component.getRowId());
					java.sql.Clob clobData = 
						com.ibm.db2.jcc.t2zos.DB2LobFactory.createClob(Utility.getStackTrace(Component.getException()));
					cstmt_Component_Failure.setClob(2, clobData );
					cstmt_Component_Failure.setString(3, Component.getExceptionMessage());
					
					//Start [001]
					if(Component.isDataIssueException()){
						cstmt_Component_Failure.setLong(4, 1);
					}else{
						cstmt_Component_Failure.setLong(4, 0);
					}
					//End [001]
					
					
					cstmt_Component_Failure.executeUpdate();
					
					

				}
				finally
				{
					if(cstmt_Component_Failure!=null) DbConnection.closeCallableStatement(cstmt_Component_Failure);
				}
				//update FX_STATUS in TPOComponentDETIALS
				//LOG exception and errmsg//-Later
				//update TFX_Component.EXCEPTION_HISTORY_ID//-Later
			}
		}
		
}
		
		
		


	

