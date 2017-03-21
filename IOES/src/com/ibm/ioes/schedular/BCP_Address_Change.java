/**
 * 
 */
package com.ibm.ioes.schedular;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;

import com.ibm.fx.dto.BCPAddressChangeDTO;
import com.ibm.fx.dto.DisconnectionDto;
import com.ibm.fx.dto.RcDto;
import com.ibm.fx.mq.IOESKenanManager;
import com.ibm.ioes.beans.ViewOrderDto;
import com.ibm.ioes.exception.IOESException;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Utility;

/**
 * @author Sandeep Aggarwal
 * Date    21-03-2011
 * FUNCTIONALITY Schedular is for the change of the bcp address
 * 
 *
 */
public class BCP_Address_Change {
	
	Connection conn = null;
	BCPAddressChangeDTO objBCPAddressChangeDto = null;
	ArrayList arrayAccountList = null;
	ArrayList alSelect_Address_For_Change	=	null;
	ArrayList alFetch_Address_N_Account_For_Change_In_FX = null;
	Iterator itr = null;
	CallableStatement cstmt	=	null;
	
	private int fx_login_flag = 0; 
	IOESKenanManager API = null;
	
	private final int REQUEST_ALREADY_PENDING 		= 5;
	private final int SUCCESSFULLY_UPDATED_IN_FX    = 3;
	private final int RECORD_NOT_IN_FX 		  		= 6;
	private final int FAILED_TO_UPDATE_IN_FX  		= 4;
	private final int NO_NEED_TO_CHANGE_IN_FX 		= 5;

	
	
	
	private static String sqlFetch_Address_N_Account_Details_For_Update="{CALL IOE.FX_GET_ACCOUNT_N_ADDRESS_DETAILS_FOR_UPDATE()}";
	private static String sqlFetch_Billing_Address_Id_For_Change="{CALL IOE.FX_GET_ADDRESS_DETAILS_FOR_CHANGE()}";
	private static String sql_Move_Data_To_Change_Address_Trans_Table = "{CALL IOE.FX_INSERT_ACCOUNT_INFO_FOR_ADDRESS_CHANGE(?,?)}";			
	private static String sql_Get_Count_N_Processing_Status_For_External_Id = "{CALL IOE.FX_GET_COUNT_N_PROCESSING_STATUS_FOR_EXTERNALID(?,?,?,?,?)}";
	
	
	
	
	
	public void run() {
		try {
			this.process_address_for_change();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}	
		
		/*   	********************************************************************************
		 *		Function Name:- process_address_for_change
		 *		Created By:-    Sandeep Aggarwal
		 * 		Created On:-    10-FEB-2011
		 * 		Purpose:-		Process the address for change
		 *      ********************************************************************************
		 */
		
		
		public void process_address_for_change() {
		
			try {
				
					Utility.LOG(true, true, " process_address_for_change() function");
					alSelect_Address_For_Change	=	null;			
					
					conn = DbConnection.getConnectionObject();
					//conn.setAutoCommit(false);
				
					    push_failure_transactions_again(conn);
						
						// call function to select addressess pending  for change
						alSelect_Address_For_Change = fetch_billing_address_id_for_change(conn);
						itr	=	alSelect_Address_For_Change.iterator();
						while(itr.hasNext()) 
						{
							
							try {
								
								objBCPAddressChangeDto	= 	(BCPAddressChangeDTO)itr.next();																	
								moveDataToChangeAddressTransTable(conn,objBCPAddressChangeDto.getCrm_address_id(),objBCPAddressChangeDto.getHistory_bcp_id());
								
							}catch(Exception e)
							{
								e.printStackTrace();
							}
							
						
						}// end of while loop
						alSelect_Address_For_Change = null;
						try {
								
							alSelect_Address_For_Change = 	getAddress_N_Account_Details(conn);
							while(alSelect_Address_For_Change !=null && alSelect_Address_For_Change.size()>0) {
							
								itr = null;
								itr = alSelect_Address_For_Change.iterator();
								while (itr.hasNext())
								{
									try {
										
										objBCPAddressChangeDto 	 =  null;
										objBCPAddressChangeDto	 = (BCPAddressChangeDTO)itr.next();
										
										CallableStatement cstmt= conn.prepareCall(sql_Get_Count_N_Processing_Status_For_External_Id);
										cstmt.setLong(1,Long.parseLong(objBCPAddressChangeDto.getRowid()));
										cstmt.setString(2,objBCPAddressChangeDto.getExternal_id());								 
										cstmt.registerOutParameter(3,java.sql.Types.BIGINT); // out param
										cstmt.registerOutParameter(4,java.sql.Types.BIGINT); // out param
										cstmt.registerOutParameter(5,java.sql.Types.VARCHAR); // 
										cstmt.execute();
										
										/*if(cstmt.getLong(3) != 0) check count{
											// update status to 5
											update_status(conn,REQUEST_ALREADY_PENDING,objBCPAddressChangeDto.getRowid());
											// same account req is pending
										}else 
										{*/
												/*
												 * case when account exist in fx
												 * then call api and update billing address info
												 * 
												 */
											
												if (cstmt.getLong(4) == 3)/*check fx status*/ {
													
													// call api
													if(fx_login_flag == 0)/*if already not loged in*/ {
														
														API	=	new IOESKenanManager();
														API.loginKenan();
														fx_login_flag = 1; /* set flag to 1 for first time login*/
													}
													
													objBCPAddressChangeDto.setInternal_id(cstmt.getString(5));
													
													boolean  status = API.updateAccount(objBCPAddressChangeDto);
													
													if(status) {
															// if successfully updated in fx
														
														update_status(conn,SUCCESSFULLY_UPDATED_IN_FX,objBCPAddressChangeDto.getRowid());
														
													}else {
														
														// if failed to update in fx for any reason
														update_status(conn,FAILED_TO_UPDATE_IN_FX,objBCPAddressChangeDto.getRowid());
													}
													
												}else
												{
													// case when account doesnot exist in fx
													// update the table locally ,set status to 6
													
														update_status(conn,RECORD_NOT_IN_FX,objBCPAddressChangeDto.getRowid());
													 
												}
										//}									
										
										
									}catch (Exception e) 
									{
										Utility.LOG(true, true, e,null);
										e.printStackTrace();
										
									}
											
								}
								
								alSelect_Address_For_Change = null;
								alSelect_Address_For_Change = 	getAddress_N_Account_Details(conn);
								
																
							}
								
						}catch (Exception e) {
							Utility.LOG(true, true, e,null);
							e.printStackTrace();
						}finally {
							
							try {
								//DbConnection.freeConnection(conn);
								if(fx_login_flag == 1) {
									 
									 API.close();
									 fx_login_flag = 0;
								 }
								DbConnection.freeConnection(conn);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
						
					
			
			}catch(Exception e) {
				
				e.printStackTrace();
			}
		}// end of the function
		
		
		private  ArrayList fetch_billing_address_id_for_change(Connection conn) throws Exception{
			
			
			Utility.LOG(true, true, " IN  fetch_billing_address_id_for_change()");
	        CallableStatement csAddress_Details_For_Change	 =	null;	        
	        ResultSet rsAddress_Details_For_Change=null;
			
			
			try 
			{
				 
				alSelect_Address_For_Change		=	new ArrayList();							
				csAddress_Details_For_Change 	=	conn.prepareCall(sqlFetch_Billing_Address_Id_For_Change);				
				rsAddress_Details_For_Change	=	csAddress_Details_For_Change.executeQuery();
							
							while (rsAddress_Details_For_Change.next())/*fetch next BCP address for the processing*/ 
							{
								try
								{
										objBCPAddressChangeDto	=	new BCPAddressChangeDTO();
										objBCPAddressChangeDto.setCrm_address_id(rsAddress_Details_For_Change.getString("CRM_ADDRESS_ID"));
										objBCPAddressChangeDto.setHistory_bcp_id(rsAddress_Details_For_Change.getString("HISTORY_BCP_ID"));
										
										alSelect_Address_For_Change.add(objBCPAddressChangeDto);
								}	
								catch(Exception ex)
								{
									ex.printStackTrace();
									String msg = "Exception in fetch_billing_address_id_for_change() function"
									+"\n CRM_ADDRESS_ID ="+rsAddress_Details_For_Change.getString("CRM_ADDRESS_ID")
									+"\n HISTORY_BCP_ID ="+rsAddress_Details_For_Change.getString("HISTORY_BCP_ID");  
									Utility.LOG(true, true,ex,msg );									
									
									// put exception logging
									
								}
									
									
							}// end of the while loop
						
				}catch (Exception e) {
					
					throw e;
							 
				} 
				finally
				{
					DbConnection.closeResultset(rsAddress_Details_For_Change);
					DbConnection.closeCallableStatement(csAddress_Details_For_Change);
				}
				return alSelect_Address_For_Change;
			
		}// end of the function
		
		
		
		public  boolean  update_status(Connection conn,int processing_status,String row_id) throws Exception {
			
			
			Utility.LOG(true, true, " IN  update_status()");
			PreparedStatement pstmt = null;
			
			
			int result = 0;
			boolean status=false;
							
			
			//StringBuffer sql_update_tbcpid_fx_status = new StringBuffer("UPDATE IOE.TBCP_ADDRESS_HISTORY TBCP_ADDRESS_HISTORY SET FX_CHANGE_STATUS = ?");
			//			 sql_update_tbcpid_fx_status.append(" WHERE TBCP_ADDRESS_HISTORY.HISTORY_BCP_ID = ?");
			
			StringBuffer sql_update_bcp_address_change_fx_status = new StringBuffer("UPDATE IOE.TFX_BCP_ADDRESS_CHANGE TBAC SET STATUS = ?,LAST_UPDATED_DATE= CURRENT TIMESTAMP");			 
						 sql_update_bcp_address_change_fx_status.append(" WHERE TBAC.ROW_ID = ?");
						 
			try{
				
				Utility.LOG(true, true,sql_update_bcp_address_change_fx_status.toString());
				
					pstmt = conn.prepareStatement(sql_update_bcp_address_change_fx_status.toString());
				
					pstmt.setLong(1, processing_status);
					pstmt.setLong(2, new Long(row_id));
					
					result = pstmt.executeUpdate();
					
					if(result>0)
						status=true;
				
			}
			catch (Exception e) {
				String msg = "Exception occured in the update_status() function of the ";
				Utility.LOG(true, true,toString());
			}finally
			{
				if(pstmt !=null)
				{
					pstmt.close();
					pstmt = null;
				}
				
				
			}	
			return status;

		}
		
public  boolean  push_failure_transactions_again(Connection conn) throws Exception {
			
			
			Utility.LOG(true, true, " IN  push_failure_transactions_again()");
			PreparedStatement pstmt = null;
			
			
			int result = 0;
			boolean status=false;
							
			
			
			StringBuffer sql_push_failed_transacations_again = new StringBuffer("UPDATE IOE.TFX_BCP_ADDRESS_CHANGE TBAC SET STATUS = 1,LAST_UPDATED_DATE= CURRENT TIMESTAMP");			 
						 sql_push_failed_transacations_again.append(" WHERE TBAC.STATUS = 4");
						 
			try{
				
				Utility.LOG(true, true,sql_push_failed_transacations_again.toString());
					pstmt = conn.prepareStatement(sql_push_failed_transacations_again.toString());
					
					result = pstmt.executeUpdate();
					
					if(result>0)
						status=true;
				
			}
			catch (Exception e) {
				Utility.LOG(true, true, e,"Exception  IN  push_failure_transactions_again()");
			}finally
			{
				if(pstmt !=null)
				{
					pstmt.close();
					pstmt = null;
				}
				
				
			}	
			return status;

		}

		
		
		
		
		public void  moveDataToChangeAddressTransTable(Connection conn,String crm_address_id,String history_bcp_id) throws Exception
		{
			
			int status = 0;
			CallableStatement csFetch_Accounts	 	=	null;	        
	        
			
	        try 
			{
				 	        								
	        	csFetch_Accounts 	=	conn.prepareCall(sql_Move_Data_To_Change_Address_Trans_Table);
	        	csFetch_Accounts.setLong(1,new Long(crm_address_id));
	        	csFetch_Accounts.setLong(2,new Long(history_bcp_id));
	        	csFetch_Accounts.execute();
										
						
			}catch (Exception e) 
			{					
				String msg = "Exception in function moveDataToChangeAddressTransTable() of BCP_Address_Change CLASS"
							+"\n parameters passed are"
							+"\nCRM ADDRESS ID  = "+crm_address_id
							+"\nHISTORY BCP ID  = "+history_bcp_id;
				
				Utility.LOG(true, true,e,msg);					 
			}finally
			{
				if(csFetch_Accounts !=null)
				{
					csFetch_Accounts.close();
					csFetch_Accounts = null;
				}
				
				
			}	 
				
						
		}// end of the function
		
		
		public ArrayList getAddress_N_Account_Details(Connection conn) throws IOESException{
			
			Utility.LOG(true, true, "IN getAddress_N_Account_Details");
			CallableStatement csFetch_Address_N_Account_For_Change_In_FX	 =	null;	        
	        ResultSet rsFetch_Address_N_Account_For_Change_In_FX=null;
	        alFetch_Address_N_Account_For_Change_In_FX = null;

			 
			alFetch_Address_N_Account_For_Change_In_FX		=	new ArrayList();		
			try {
				
				csFetch_Address_N_Account_For_Change_In_FX 	=	conn.prepareCall(sqlFetch_Address_N_Account_Details_For_Update);				
				
				rsFetch_Address_N_Account_For_Change_In_FX	=	csFetch_Address_N_Account_For_Change_In_FX.executeQuery();
							
							while (rsFetch_Address_N_Account_For_Change_In_FX.next())/*fetch next BCP address for the processing*/ 
							{
								try
								{
										objBCPAddressChangeDto	=	new BCPAddressChangeDTO();
										objBCPAddressChangeDto.setRowid(rsFetch_Address_N_Account_For_Change_In_FX.getString("ROW_ID"));
										objBCPAddressChangeDto.setCrm_address_id(rsFetch_Address_N_Account_For_Change_In_FX.getString("CRM_ADDRESS_ID"));
										objBCPAddressChangeDto.setHistory_bcp_id(rsFetch_Address_N_Account_For_Change_In_FX.getString("HISTORY_BCP_ID"));
										objBCPAddressChangeDto.setOld_title(rsFetch_Address_N_Account_For_Change_In_FX.getString("OLD_TITLE"));
										objBCPAddressChangeDto.setOld_fname(rsFetch_Address_N_Account_For_Change_In_FX.getString("OLD_FNAME"));
										objBCPAddressChangeDto.setOld_lname(rsFetch_Address_N_Account_For_Change_In_FX.getString("OLD_LNAME"));
										objBCPAddressChangeDto.setOld_address1(rsFetch_Address_N_Account_For_Change_In_FX.getString("OLD_ADDRESS1"));
										objBCPAddressChangeDto.setOld_address2(rsFetch_Address_N_Account_For_Change_In_FX.getString("OLD_ADDRESS2"));
										objBCPAddressChangeDto.setOld_address3(rsFetch_Address_N_Account_For_Change_In_FX.getString("OLD_ADDRESS3"));
										objBCPAddressChangeDto.setOld_address4(rsFetch_Address_N_Account_For_Change_In_FX.getString("OLD_ADDRESS4"));
										objBCPAddressChangeDto.setOld_postal_code(rsFetch_Address_N_Account_For_Change_In_FX.getString("OLD_POSTAL_CODE"));
										objBCPAddressChangeDto.setNew_title(rsFetch_Address_N_Account_For_Change_In_FX.getString("NEW_TITLE"));
										objBCPAddressChangeDto.setNew_fname(rsFetch_Address_N_Account_For_Change_In_FX.getString("NEW_FNAME"));
										objBCPAddressChangeDto.setNew_lname(rsFetch_Address_N_Account_For_Change_In_FX.getString("NEW_LNAME"));										
										objBCPAddressChangeDto.setNew_address1(rsFetch_Address_N_Account_For_Change_In_FX.getString("NEW_ADDRESS1"));
										objBCPAddressChangeDto.setNew_address2(rsFetch_Address_N_Account_For_Change_In_FX.getString("NEW_ADDRESS2"));
										objBCPAddressChangeDto.setNew_address3(rsFetch_Address_N_Account_For_Change_In_FX.getString("NEW_ADDRESS3"));
										//objBCPAddressChangeDto.setNew_address4(rsFetch_Address_N_Account_For_Change_In_FX.getString("NEW_ADDRESS4"));
										objBCPAddressChangeDto.setNew_postal_code(rsFetch_Address_N_Account_For_Change_In_FX.getString("NEW_POSTAL_CODE"));										
										
										objBCPAddressChangeDto.setOld_state_name(rsFetch_Address_N_Account_For_Change_In_FX.getString("OLD_STATE_NAME"));
										objBCPAddressChangeDto.setOld_telephone_no(rsFetch_Address_N_Account_For_Change_In_FX.getString("OLD_TELEPHONENO"));
										objBCPAddressChangeDto.setOld_city_name(rsFetch_Address_N_Account_For_Change_In_FX.getString("OLD_CITY_NAME"));
										objBCPAddressChangeDto.setOld_country_code(rsFetch_Address_N_Account_For_Change_In_FX.getString("OLD_COUNTRY_CODE"));
										objBCPAddressChangeDto.setOld_emailid(rsFetch_Address_N_Account_For_Change_In_FX.getString("OLD_EMAIL_ID"));
										objBCPAddressChangeDto.setNew_city_name(rsFetch_Address_N_Account_For_Change_In_FX.getString("NEW_CITY_NAME"));
										objBCPAddressChangeDto.setNew_state_name(rsFetch_Address_N_Account_For_Change_In_FX.getString("NEW_STATE_NAME"));
										objBCPAddressChangeDto.setNew_country_code(rsFetch_Address_N_Account_For_Change_In_FX.getString("NEW_COUNTRY_CODE"));
										objBCPAddressChangeDto.setNew_designation(rsFetch_Address_N_Account_For_Change_In_FX.getString("NEW_DESIGNATION"));
										objBCPAddressChangeDto.setOld_designation(rsFetch_Address_N_Account_For_Change_In_FX.getString("OLD_DESIGNATION"));
										objBCPAddressChangeDto.setNew_telephone_no(rsFetch_Address_N_Account_For_Change_In_FX.getString("NEW_TELEPHONENO"));
										objBCPAddressChangeDto.setNew_emailid(rsFetch_Address_N_Account_For_Change_In_FX.getString("NEW_EMAIL_ID"));
										objBCPAddressChangeDto.setExternal_id(rsFetch_Address_N_Account_For_Change_In_FX.getString("EXTERNAL_ID"));
										//objBCPAddressChangeDto.setInternal_id(rsFetch_Address_N_Account_For_Change_In_FX.getString("INTERNAL_ID"));
										objBCPAddressChangeDto.setNew_rev_circle(rsFetch_Address_N_Account_For_Change_In_FX.getString("NEW_REV_CIRCLE"));
										objBCPAddressChangeDto.setOld_rev_circle(rsFetch_Address_N_Account_For_Change_In_FX.getString("OLD_REV_CIRCLE"));
										
										
										alFetch_Address_N_Account_For_Change_In_FX.add(objBCPAddressChangeDto);
										
								}catch(Exception ex)
								{
									String msg = "Exception occured in getAddress_N_Account_Details() function of BCP_Address_Change class"
									+"\n ROW_ID = "+rsFetch_Address_N_Account_For_Change_In_FX.getString("ROW_ID")
									+"\n CRM_ADDRESS_ID = "+rsFetch_Address_N_Account_For_Change_In_FX.getString("CRM_ADDRESS_ID")
									+"\n HISTORY_BCP_ID = "+rsFetch_Address_N_Account_For_Change_In_FX.getString("HISTORY_BCP_ID")
									+"\n EXTERNAL_ID = "+ rsFetch_Address_N_Account_For_Change_In_FX.getString("EXTERNAL_ID");
									
									Utility.LOG(true, true, ex, msg);
								}
							}
					}catch (Exception e)
					{
									
						e.printStackTrace();
									
					}finally
					{
						if(csFetch_Address_N_Account_For_Change_In_FX !=null)
						{
							try {
								csFetch_Address_N_Account_For_Change_In_FX.close();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
						if(rsFetch_Address_N_Account_For_Change_In_FX !=null)
						{
							try {
								rsFetch_Address_N_Account_For_Change_In_FX.close();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
						
						
					}	 
					
					return alFetch_Address_N_Account_For_Change_In_FX;
		}
		
		
		 
	}		
			
				

	
	
	


