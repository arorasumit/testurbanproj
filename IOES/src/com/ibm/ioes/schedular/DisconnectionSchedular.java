//tag		Name       Date			CSR No			Description 
//[001]   Raveendra    28-Nov-2014                  Set processing count 10 in case of data issue exception is generated when scheduler is in progress
package com.ibm.ioes.schedular;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.sql.Date;
import java.util.TimerTask;

import com.csgsystems.fx.security.util.FxException;
import com.ibm.fx.dto.DisconnectionDto;
import com.ibm.fx.dto.ExtendedData;
import com.ibm.fx.dto.RcDto;
import com.ibm.fx.mq.IOESKenanManager;
import com.ibm.ioes.beans.ViewOrderDto;
import com.ibm.ioes.exception.IOESException;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Utility;

/**
 * @author SANDEEP AGGARWAL
 * Date    10-FEB-2011
 * FUNCTIONALITY Schedular is for the disconnection of charges
 *
 */



public class DisconnectionSchedular {
	ArrayList<String> dataIssueException=null;
	private static String sqlGet_Select_Charges_For_Disconnection_Schedular="{CALL IOE.FX_GET_CHARGES_FOR_DISCONNECTON_SCHEDULAR(?)}";
	private final int  PUSHED_IN_FX  = 3;
	private final int  SUCCESS	   = 3;
	private final int  FAILURE	   = 4;
	private final int  CHARGE_NOT_YET_PUSHED = 5;
	private final int  PRODUCT_FOUND = 10;
	private final int  PRODUCT_NOT_FOUND = 30;
	private final int  PRODUCT_DISCONNECTED = 20;
	private final int  PRODUCT_UPDATED = 20;
	private final int  PRODUCT_NOT_DISCONNECTED = 40;
	private final int  PRODUCT_FOUND_BUT_NOT_DISCONNECTED = 50;
	private int fx_login_flag = 0;
	private final int CASE0	= 0;	
	private final int CASE1 = 1;
	private final int CASE2 = 2;
	private final int CASE3 = 3;
	private final int CASE4 = 4;
	private final int CASE5 = 5;
	private final int CASE6 = 6;
	
	IOESKenanManager API = null;
	DisconnectionDto objDisconnectionDto =null;
	Connection conn = null;
	ArrayList arrayChargeList = null;
	
	private static String sqlUpdateOrder="{CALL IOE.FX_DISCONNECTION_STATUS_OF_ORDER(?)}";
	//Start [001] add one more parameter for dataissueflag
	private static String sqlFX_SCHEDULER_FAILURE="{CALL IOE.FX_SCHEDULDER_FAILURE_FOR_CHARGE_DISCONNECTION(?,?,?,?)}";
	//End [001]
	public void run(long orderno) {
		try {
			this.process_charges_for_disconnection(orderno);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}// end of the function
	
	
	
	/*   	********************************************************************************
	 *		Function Name:- get_charge_list_for_disconnection
	 *		Created By:-    Sandeep Aggarwal
	 * 		Created On:-    10-FEB-2011
	 * 		Purpose:-		To Get All The Charge List For Disconnection
	 *      ********************************************************************************
	 */ 

	private  ArrayList get_charges_for_disconnection(Connection conn,long Orderno) throws Exception{
		
		
		Utility.LOG(true, true, " IN  get_charges_for_disconnection() function of DisconnectionSchedular class");
        CallableStatement csCharges_Info_For_Disconnection=null;
        ArrayList alSelect_Charge_Details_For_Disconnection=null;
        ResultSet rsCharge_Details_For_Disconnection=null;
		
		
		try 
		{
						
			alSelect_Charge_Details_For_Disconnection = new ArrayList<ViewOrderDto>();
						
			csCharges_Info_For_Disconnection= conn.prepareCall(sqlGet_Select_Charges_For_Disconnection_Schedular);
			csCharges_Info_For_Disconnection.setLong(1,Orderno);
			
			rsCharge_Details_For_Disconnection=csCharges_Info_For_Disconnection.executeQuery();
			Utility.LOG(true, true, "Fetch charges for disconnection ");
						while (rsCharge_Details_For_Disconnection.next())/*fetch rc from for disconnection*/ {
							try {
									objDisconnectionDto	=	new DisconnectionDto();
									objDisconnectionDto.setIndex_key(rsCharge_Details_For_Disconnection.getInt("INDEX_KEY"));
									objDisconnectionDto.setCharge_disconnection_date(rsCharge_Details_For_Disconnection.getTimestamp("CHARGE_DISCONNECTION_DATE"));
									objDisconnectionDto.setCharge_active_date(rsCharge_Details_For_Disconnection.getDate("CHARGE_ACTIVE_DATE"));
									objDisconnectionDto.setView_id(rsCharge_Details_For_Disconnection.getString("VIEWID"));									
									objDisconnectionDto.setCharge_inactive_date(rsCharge_Details_For_Disconnection.getDate("CHARGE_INACTIVE_DATE"));
									objDisconnectionDto.setSchedular_status(rsCharge_Details_For_Disconnection.getInt("FX_SCHEDULAR_CREATE_STATUS"));
									objDisconnectionDto.setCharge_info_id(rsCharge_Details_For_Disconnection.getInt("CHARGEINFOID"));
									objDisconnectionDto.setOrderno(rsCharge_Details_For_Disconnection.getInt("ORDERNO"));
									objDisconnectionDto.setFxAccountInternalId(rsCharge_Details_For_Disconnection.getInt("INTERNAL_ID"));
									
									ArrayList<ExtendedData> extendedData = Utility.getFxExtendedData(conn,objDisconnectionDto.getIndex_key(),ExtendedData.AssociatedWith_ORDER_FOR_CHARGE_DISCONNECTION);
									objDisconnectionDto.setOrderExtendedData(extendedData);
									
									alSelect_Charge_Details_For_Disconnection.add(objDisconnectionDto);
									
									String msg = "Charge details to be disconnected are "
										+"\n INDEX_KEY "+rsCharge_Details_For_Disconnection.getInt("INDEX_KEY");
									Utility.LOG(true, true, msg);	
									
									
							}	
							catch(Exception ex)
							{
								
								String msg="Exception in retrieving charges for DISCONNECTION  Schedular. for index value " +objDisconnectionDto.getIndex_key()
								+" \n Exception , if any, = "+((objDisconnectionDto.getException()==null)?null:Utility.getStackTrace(objDisconnectionDto.getException()))
								+" \n Exception Message , if any,= "+objDisconnectionDto.getExceptionMessage();
								
								Utility.LOG(true,true,ex,msg);
							}
								
								
						}// end of the while loop
					
			}catch (Exception e) {
				
				Utility.LOG(true,true,e,null);
						 
			} 
			finally
			{
				DbConnection.closeResultset(rsCharge_Details_For_Disconnection);
				DbConnection.closeCallableStatement(csCharges_Info_For_Disconnection);
			}
			return alSelect_Charge_Details_For_Disconnection;
		
	}// end of the function
	
	
	/*   	********************************************************************************
	 *		Function Name:- process_charges_for_disconnection
	 *		Created By:-    Sandeep Aggarwal
	 * 		Created On:-    10-FEB-2011
	 * 		Purpose:-		Process the charges for disconnection
	 *      ********************************************************************************
	 */
	private  void process_charges_for_disconnection(long Orderno) {
		
		
		try {
			Utility.LOG(true, true, " IN  process_charges_for_disconnection() function of DisconnectionSchedular CLASS");
			arrayChargeList=null;			
			
			conn = DbConnection.getConnectionObject();
			conn.setAutoCommit(false);
			//[001]
			dataIssueException=Utility.getdataIssueException(conn);
			
			try {
				
				update_ib2b_Status(conn,-1,-1,CASE0,null,-1,null,0,null);
							arrayChargeList = get_charges_for_disconnection(conn,Orderno);
				conn.commit();			
				
			}catch(Exception e) {
				
				Utility.LOG(true, true,e,null);
				
			}
			
			while(arrayChargeList!=null&&arrayChargeList.size()>0)
			{	
					Utility.LOG(true, true,"Total No charges to be disconnected are "+arrayChargeList.size() );
					for(int i=0;i<arrayChargeList.size();i++)
					{
						
						
						objDisconnectionDto = (DisconnectionDto)arrayChargeList.get(i);
						String logIdentifier="DISCONNECTION SCHEDULAR , CHARGEINFOID:"+objDisconnectionDto.getCharge_info_id()+"\n";
						String msgLogIdentifier = logIdentifier+"Details for the charge to be disconnected are "
							+"\n INDEX_KEY "+objDisconnectionDto.getIndex_key()
							+"\n CHARGE_DISCONNECTION_DATE "+objDisconnectionDto.getCharge_disconnection_date()
							+"\n CHARGE_ACTIVE_DATE "+objDisconnectionDto.getCharge_active_date()
							+"\n VIEWID "+objDisconnectionDto.getView_id()
							+"\n CHARGE_INACTIVE_DATE "+objDisconnectionDto.getCharge_inactive_date()
							+"\n CHARGEINFOID "+objDisconnectionDto.getCharge_info_id();
						//Utility.LOG(true, true, msg);
						
							try {
									if(objDisconnectionDto.getCharge_inactive_date()==null)
									{
										objDisconnectionDto.setCharge_inactive_date(new Date(8099,1,1));
									}
									if(PUSHED_IN_FX == objDisconnectionDto.getSchedular_status())/*when charges are already pushed in fx*/ {
										//case 1
										// when disconnection date is less then charge active date
										if(objDisconnectionDto.getCharge_disconnection_date().before(objDisconnectionDto.getCharge_active_date())) {
											
											
											//Utility.LOG(true, true,"Case when disconnection date is less the charge active date for charge already pushed in fx is implemented!");
											//call api and pass charge active date as disconnetion date
											//Utility.LOG(true, true,"Charge Disconnection initiated.....");
											//if( PRODUCT_UPDATED == updateCharges(objDisconnectionDto.getView_id(), new Timestamp(objDisconnectionDto.getCharge_active_date().getTime()))) 
											//{
												if(PRODUCT_DISCONNECTED == disconnectCharges(conn,objDisconnectionDto.getView_id(), new Timestamp(objDisconnectionDto.getCharge_active_date().getTime()),msgLogIdentifier,objDisconnectionDto.getIndex_key(),objDisconnectionDto.getOrderExtendedData()))/*if success*/
												{
													//Utility.LOG(true, true,"Charge Disconnected successfully");
													update_ib2b_Status(conn,SUCCESS,objDisconnectionDto.getIndex_key(),CASE1,null,-1,msgLogIdentifier,Orderno,objDisconnectionDto);							
													
													
												}else /*in case of failure*/{
													
													//Utility.LOG(true, true,"Charge Disconnection Failed");
													update_ib2b_Status(conn,FAILURE,objDisconnectionDto.getIndex_key(),CASE1,null,-1,msgLogIdentifier,Orderno,objDisconnectionDto);
												}
											//}else /*in case of failure*/{
												
												//Utility.LOG(true, true,"Charge Disconnection Failed");
											//	update_ib2b_Status(conn,FAILURE,objDisconnectionDto.getIndex_key(),CASE1,null,-1,msgLogIdentifier,Orderno,objDisconnectionDto);
											//}                                                                                                                                                                     
										}else if (objDisconnectionDto.getCharge_disconnection_date().getTime()>=objDisconnectionDto.getCharge_active_date().getTime() & objDisconnectionDto.getCharge_disconnection_date().getTime()<=objDisconnectionDto.getCharge_inactive_date().getTime()) {
											// case 2 when disconnection date is less then charge end date
											// call api and pass the charge disconnection date as disconnection date
											//Utility.LOG(true, true,"Case when disconnection date is less the charge inactive date for charge already pushed in fx is implemented!");
											//Utility.LOG(true, true,"Charge Disconnection initiated.....");
											//if( PRODUCT_UPDATED == updateCharges(objDisconnectionDto.getView_id(), objDisconnectionDto.getCharge_disconnection_date())) {
												
												if(PRODUCT_DISCONNECTED == disconnectCharges(conn,objDisconnectionDto.getView_id(), objDisconnectionDto.getCharge_disconnection_date(),msgLogIdentifier,objDisconnectionDto.getIndex_key(),objDisconnectionDto.getOrderExtendedData()))/*if success*/
												{
													//Utility.LOG(true, true,"Charge Disconnected successfully");
													update_ib2b_Status(conn,SUCCESS,objDisconnectionDto.getIndex_key(),CASE2,null,-1,msgLogIdentifier,Orderno,objDisconnectionDto);							
													
												}else /*in case of failure*/{
													
													//Utility.LOG(true, true,"Charge Disconnection Failed!");
													update_ib2b_Status(conn,FAILURE,objDisconnectionDto.getIndex_key(),CASE2,null,-1,msgLogIdentifier,Orderno,objDisconnectionDto);
												}
												
											//}else /*in case of failure*/{
												
											//update_ib2b_Status(conn,FAILURE,objDisconnectionDto.getIndex_key(),CASE2,null,-1,msgLogIdentifier,Orderno,objDisconnectionDto);
										//	}
											
											 
										}else if (objDisconnectionDto.getCharge_disconnection_date().after(objDisconnectionDto.getCharge_inactive_date())) {
												
											// case 3 when disconnection date is greater then charge inactive date 
											//Utility.LOG(true, true,"Case when disconnection date is greater then charge inactive date for charge already pushed in fx is implemented!");
											//Utility.LOG(true, true,"Charge Disconnection initiated.....");
											//Utility.LOG(true, true,"Charge Disconnected successfully");
											update_ib2b_Status(conn,SUCCESS,objDisconnectionDto.getIndex_key(),CASE3,null,-1,msgLogIdentifier,Orderno,objDisconnectionDto);
										}
										
									}else  if(1==objDisconnectionDto.getSchedular_status()||4==objDisconnectionDto.getSchedular_status()) /*CASE NOT PUSHED IN FX*/{
										
										if(objDisconnectionDto.getCharge_disconnection_date().before(objDisconnectionDto.getCharge_active_date())) {
		//									 case 4 when disconnection date is less then charge active date
											//Utility.LOG(true, true,"Case when disconnection date is less then charge active date for charge already not  pushed in fx is implemented!");
											//Utility.LOG(true, true,"Charge Disconnection initiated.....");
											//Utility.LOG(true, true,"Charge Disconnected successfully");
											update_ib2b_Status(conn,SUCCESS,objDisconnectionDto.getIndex_key(),CASE4,null,objDisconnectionDto.getCharge_info_id(),msgLogIdentifier,Orderno,objDisconnectionDto);								
												
										
											                                                                                                                                                                     
										}else if (objDisconnectionDto.getCharge_disconnection_date().getTime()>= objDisconnectionDto.getCharge_active_date().getTime() & objDisconnectionDto.getCharge_disconnection_date().getTime()<=objDisconnectionDto.getCharge_inactive_date().getTime()) {
											// case 5 when disconnection date is less then charge end date
											//Utility.LOG(true, true,"Case when disconnection date is less then charge inactive date for charge already not  pushed in fx is implemented!");
											//Utility.LOG(true, true,"Charge Disconnection initiated.....");
											//Utility.LOG(true, true,"Charge Disconnected successfully");
											update_ib2b_Status(conn,CHARGE_NOT_YET_PUSHED,objDisconnectionDto.getIndex_key(),CASE5,new Date(objDisconnectionDto.getCharge_disconnection_date().getTime()),objDisconnectionDto.getCharge_info_id(),msgLogIdentifier,Orderno,objDisconnectionDto);
											
											 
										}else if (objDisconnectionDto.getCharge_disconnection_date().after(objDisconnectionDto.getCharge_inactive_date())) {
												
											// case 6 when disconnection date is greater then charge enddate 
											//Utility.LOG(true, true,"Case when disconnection date is greater then charge inactive date for charge already not  pushed in fx is implemented!");
											//Utility.LOG(true, true,"Charge Disconnection initiated.....");
											//Utility.LOG(true, true,"Charge Disconnected successfully");
											update_ib2b_Status(conn,SUCCESS,objDisconnectionDto.getIndex_key(),CASE6,null,-1,msgLogIdentifier,Orderno,objDisconnectionDto);
										}
										 
										
									}// end of the else if block
									
								}catch (Exception e) {
							Utility.LOG(true, true, msgLogIdentifier);
							String msgstr="Exception in charges for DISCONNECTION  Schedular. for index value " +objDisconnectionDto.getIndex_key();
							Utility.LOG(true, true, e, msgstr);
						}      
						
						 
						
					}// end of the for loop
					
					// to do
				
				arrayChargeList = null;
				arrayChargeList = get_charges_for_disconnection(conn,Orderno);
				conn.commit();
				
			}// end of while loop
			
			
			conn.commit();	
		}catch(Throwable e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
		}
		finally 
		{
			try {
				DbConnection.freeConnection(conn);
				if(fx_login_flag == 1) {
					 
					 API.close();
					 fx_login_flag = 0;
				 }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
	}// end of the fucntion
	
	public  boolean  update_ib2b_Status(Connection conn,int processing_status,int index_key,int disconnection_case,
			Date disconnection_date,int chargeinfoid, String msgLogIdentifier , long orderNo,DisconnectionDto objDisconnectionDto) throws Exception {
		
		
		//Utility.LOG(true, true, " IN  update_ib2b_Status()of ");
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement updateOrderStatus = null;
		PreparedStatement updateFailureStatus = null;
		
		int result = 0;
		boolean status=false;
						
		StringBuffer sql_push_failed_trasactions_again = new StringBuffer("UPDATE IOE.TFX_DISCONNECTION DIS SET PROCESSING_STATUS = 1,LAST_UPDATED_DATE = CURRENT TIMESTAMP");
					 sql_push_failed_trasactions_again.append(" WHERE DIS.PROCESSING_STATUS in (4,5)");
		
		StringBuffer sql_update_fx_schedular_status = new StringBuffer("UPDATE IOE.TFX_RC_CREATE RC SET FX_SCHEDULAR_STATUS = 5");
					 sql_update_fx_schedular_status.append(" WHERE RC.CHARGEINFOID = ?");
		
					 
		StringBuffer sql_update_tfx_disconnection = new StringBuffer("UPDATE IOE.TFX_DISCONNECTION DIS SET DISCONNECTION_STEP = ?,PROCESSING_STATUS = ?,LAST_UPDATED_DATE = CURRENT TIMESTAMP");
					 sql_update_tfx_disconnection.append(" WHERE DIS.INDEX_KEY = ?");			 
			
//					commented by Ravneet
		//StringBuffer sql_update_ib2b_charge_inactive_date = new StringBuffer("UPDATE IOE.TFX_RC_CREATE RC SET BILLING_END_DATE=? WHERE RC.CHARGEINFOID = ?");
		
		
		try{
			//
			//con.setAutoCommit(false);
			switch (disconnection_case) {
					
				case 0:
					
					pstmt = conn.prepareStatement(sql_push_failed_trasactions_again.toString());
					
					//Utility.LOG(true, true, sql_push_failed_trasactions_again.toString());
					break;
					
					
				case 1:
					
					pstmt = conn.prepareStatement(sql_update_tfx_disconnection.toString());
					pstmt.setInt(1, disconnection_case);
					pstmt.setInt(2, processing_status);
					pstmt.setInt(3, index_key);
					
					//Utility.LOG(true, true, sql_update_tfx_disconnection.toString());
					break;
					
				case 2:
					
					pstmt = conn.prepareStatement(sql_update_tfx_disconnection.toString());
					pstmt.setInt(1, disconnection_case);
					pstmt.setInt(2, processing_status);
					pstmt.setInt(3, index_key);
					//Utility.LOG(true, true, sql_update_tfx_disconnection.toString());
					break;
					
				case 3:
					
					pstmt = conn.prepareStatement(sql_update_tfx_disconnection.toString());
					pstmt.setInt(1, disconnection_case);
					pstmt.setInt(2, processing_status);
					pstmt.setInt(3, index_key);
					//Utility.LOG(true, true, sql_update_tfx_disconnection.toString());
					break;
				
				case 4:
					
					
					pstmt1 = conn.prepareStatement(sql_update_fx_schedular_status.toString());
					pstmt1.setInt(1, chargeinfoid);
					
					//Utility.LOG(true, true, sql_update_fx_schedular_status.toString());
					
					pstmt = conn.prepareStatement(sql_update_tfx_disconnection.toString());
					pstmt.setInt(1, disconnection_case);
					pstmt.setInt(2, processing_status);
					pstmt.setInt(3, index_key);
					
					//Utility.LOG(true, true, sql_update_tfx_disconnection.toString());
					
					
					break;
					
				case 5:
					
					
					pstmt = conn.prepareStatement(sql_update_tfx_disconnection.toString());
					pstmt.setInt(1, disconnection_case);
					pstmt.setInt(2, processing_status);
					pstmt.setInt(3, index_key);
					
					//Utility.LOG(true, true, sql_update_tfx_disconnection.toString());
					
//					commented by Ravneet
					/*pstmt1 = conn.prepareStatement(sql_update_ib2b_charge_inactive_date.toString());
					pstmt1.setDate(1, disconnection_date);
					pstmt1.setInt(2, chargeinfoid);
					Utility.LOG(true, true, sql_update_ib2b_charge_inactive_date.toString());*/
					break;
					
				case 6:
					
					pstmt = conn.prepareStatement(sql_update_tfx_disconnection.toString());
					pstmt.setInt(1, disconnection_case);
					pstmt.setInt(2, processing_status);
					pstmt.setInt(3, index_key);
					
					//Utility.LOG(true, true, sql_update_tfx_disconnection.toString());
					break;
					
					
					
			}// end of the switch block
			
			
			result = pstmt.executeUpdate();
			//TODO : My Code goes here
			if(processing_status == SUCCESS)
			{
				if(objDisconnectionDto!=null){
					updateOrderStatus = conn.prepareStatement(sqlUpdateOrder);
					updateOrderStatus.setLong(1, objDisconnectionDto.getOrderno());
					updateOrderStatus.executeUpdate();	
				}
			}
			//commented by Ravneet
			if(disconnection_case == CASE4/*|| disconnection_case == CASE5*/)
			{
				pstmt1.executeUpdate();
			}
			
			if(disconnection_case == CASE1 || disconnection_case == CASE2 )
			{
				
				if(processing_status == FAILURE)
				{
					//Start [001]
					boolean isDataIssueException=Utility.isDataIssueException(dataIssueException,objDisconnectionDto.getException());
					objDisconnectionDto.setDataIssueException(isDataIssueException);
					//End [001]
					updateFailureStatus = conn.prepareStatement(sqlFX_SCHEDULER_FAILURE);
					updateFailureStatus.setInt(1,index_key );
					if(objDisconnectionDto.getException()!=null)
					{java.sql.Clob clobData = 
						com.ibm.db2.jcc.t2zos.DB2LobFactory.createClob(Utility.getStackTrace(objDisconnectionDto.getException()));
						updateFailureStatus.setClob(2, clobData );
					}
					else
					{
						java.sql.Clob clobData = 
							com.ibm.db2.jcc.t2zos.DB2LobFactory.createClob("Exception Object is null");
							updateFailureStatus.setClob(2, clobData );
					}
					updateFailureStatus.setString(3,objDisconnectionDto.getExceptionMessage() );
					
                    //Start [001]
					if(objDisconnectionDto.isDataIssueException()){
						updateFailureStatus.setLong(4, 1);
					}else{
						updateFailureStatus.setLong(4, 0);
					}
					//End [001]				
					updateFailureStatus.executeUpdate();
					
					
				}
				
			}	
			
			
			if(result>0)
			{
				status=true;
			}
			
			conn.commit();
			
		}catch (Exception e) {
			
			Utility.LOG(true, true, e, msgLogIdentifier);
			conn.rollback();
		}
		finally
		{
			if(pstmt !=null)
			{
				pstmt.close();
				pstmt = null;
			}
			if(pstmt1 !=null)
			{
				pstmt1.close();
				pstmt1 = null;
			}
			
		}	
		return status;

	}
	/*   	********************************************************************************
	 *		Function Name:- disconnectCharges
	 *		Created By:-    Sandeep Aggarwal
	 * 		Created On:-    10-FEB-2011
	 * 		Purpose:-		Disconnect Charges in FX
	 *      ********************************************************************************
	 */
	public  int disconnectCharges(Connection conn,String viewid,Timestamp chargeDisactiveDate, String msgLogIdentifier,int index_key, ArrayList<ExtendedData> orderExtendedData) throws IOESException{
		int  saveStatus=0;
		String message;
		
		//Utility.LOG(true, true, " IN  disconnectCharges()");
		Date inActiveDate = new Date(chargeDisactiveDate.getTime());
		//Date  inActiveDate = new Date();
		
		try
		{
			if(fx_login_flag == 0)/*if already not loged in*/ {
				
				API=new IOESKenanManager();
				API.loginKenan();
				fx_login_flag = 1; /* set flag to 1 for first time login*/
			}
			try
			{
//				RcDto rcDto  = API.findProduct(viewid);
				RcDto rcDto  = new RcDto();
				
				if(viewid==null)
				{
					saveStatus=PRODUCT_NOT_FOUND;
				}
				else
				{
					saveStatus=PRODUCT_FOUND;
					rcDto.setAccountInternalId(objDisconnectionDto.getFxAccountInternalId());
				}
				rcDto.setSaveStatus(saveStatus);
				
				if(saveStatus==PRODUCT_FOUND)/*product found*/{
					
					int operationValue=API.disconnectProduct(viewid,inActiveDate,rcDto,objDisconnectionDto,orderExtendedData);
					  
					if(operationValue==PRODUCT_DISCONNECTED)/*product disconnected*/
					{
						  saveStatus = PRODUCT_DISCONNECTED;
						  update_orderid(conn,rcDto,index_key); 
					}	  
					else if(operationValue==PRODUCT_NOT_DISCONNECTED)/*product not disconnected*/
					{	
						  saveStatus = PRODUCT_FOUND_BUT_NOT_DISCONNECTED;
					}	  
					
				}else if(saveStatus == PRODUCT_NOT_FOUND)/*product not found*/
					saveStatus = PRODUCT_NOT_FOUND;
				
			}catch(Exception e){
				e.printStackTrace();
				Utility.LOG(true, true, e, msgLogIdentifier);
				objDisconnectionDto.setException(e);
				message="Exception for Index_key :"+objDisconnectionDto.getIndex_key()+"and For Row ID  :"+ objDisconnectionDto.getView_id();
				objDisconnectionDto.setExceptionMessage(message);
				
				
			}
			
		}
		catch (Exception ex) {
			
			Utility.LOG(true, true, ex, msgLogIdentifier);
			objDisconnectionDto.setException(ex);
			message="Exception for Index_key :"+objDisconnectionDto.getIndex_key()+"and For Row ID  :"+ objDisconnectionDto.getView_id();
			objDisconnectionDto.setExceptionMessage(message);
		}
		
		return saveStatus;
	}
	
	public int updateCharges(String viewid,Timestamp chargeDisactiveDate) {
		int  saveStatus=0;
		boolean operationValue = false;
		String message;
		try 
		{
			Date inActiveDate = new Date(chargeDisactiveDate.getTime());
			if(fx_login_flag == 0)/*if already not loged in*/ 
			{			
				API=new IOESKenanManager();
				API.loginKenan();
				fx_login_flag = 1; /* set flag to 1 for first time login*/
			}
			try
			{
				RcDto rcDto  = API.findProduct(viewid);
				saveStatus=rcDto.getSaveStatus();
				
				if(saveStatus==PRODUCT_FOUND)/*product found*/
				{
					operationValue=API.updateProduct(viewid, inActiveDate);
					if(operationValue)
						saveStatus = PRODUCT_DISCONNECTED;/*here product disconnected means product
						 									updated succesfully*/
					
				}else if(saveStatus == PRODUCT_NOT_FOUND)/*product not found*/
					saveStatus = PRODUCT_NOT_FOUND;	  
				
			}catch(Exception e){
				
				
				objDisconnectionDto.setException(e);
				message="Exception for Index_key :"+objDisconnectionDto.getIndex_key()+"and For Row ID  :"+ objDisconnectionDto.getView_id();
				objDisconnectionDto.setExceptionMessage(message);
				e.printStackTrace();
				//Utility.LOG(true, true, e, msgLogIdentifier);
			}	
			
		}catch(Exception e) {
			
			objDisconnectionDto.setException(e);
			message="Exception for Index_key :"+objDisconnectionDto.getIndex_key()+"and For Row ID  :"+ objDisconnectionDto.getView_id();
			objDisconnectionDto.setExceptionMessage(message);
			e.printStackTrace();
		}
		return saveStatus;
	}// end of the function
public  boolean  update_orderid(Connection conn,RcDto rcDto,int index_key) throws Exception {		
		
	
		PreparedStatement pstmt = null;		
		int result = 0;
		boolean status=false;
						
		String  sql_update_tokenid = "UPDATE IOE.TFX_DISCONNECTION DIS SET TOKEN_ID = ?,LAST_UPDATED_DATE = CURRENT TIMESTAMP" +
					 				 " WHERE DIS.INDEX_KEY = ?";		

		
		try{
					
			pstmt = conn.prepareStatement(sql_update_tokenid);					
			pstmt.setString(1,rcDto.getToken_id());
			pstmt.setInt(2,index_key);
			result = pstmt.executeUpdate();
			
			if(result>0)
			{
				status=true;
			}
			
		}catch (Exception e) {
			
			e.printStackTrace();
		}
		finally
		{
			if(pstmt !=null)
			{
				pstmt.close();
				pstmt = null;
			}
		}	
		return status;

	}
	

}
