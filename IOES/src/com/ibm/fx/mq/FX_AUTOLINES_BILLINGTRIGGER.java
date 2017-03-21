//Tag Name Resource Name  Date		CSR No			Description
//[001]	 Manisha	26-jul-11	00-05422		File Added for Auto Billing Schedular
//[002] Pankaj Thakur 3-jun-2015   calling a function logExceptionForBillingTrigger  to check the Component Billing Start Date  for Auot Billing Scheduler
package com.ibm.fx.mq;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.ibm.db2.jcc.b.SqlException;
import com.ibm.fx.mq.ApplicationClone.TaskType;
import com.ibm.ioes.beans.ViewOrderDto;
import com.ibm.ioes.dao.NewOrderDao;
import com.ibm.ioes.dao.ViewOrderDao;
import com.ibm.ioes.forms.ChargeSummaryChargeSection;
import com.ibm.ioes.newdesign.dto.AutoBillingLineDto;
import com.ibm.ioes.newdesign.dto.LineItemDto;
import com.ibm.ioes.newdesign.dto.ServiceDto;
import com.ibm.ioes.schedular.LepmLocDataUpdate;
import com.ibm.ioes.service.UtilityService;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Utility;

public class FX_AUTOLINES_BILLINGTRIGGER {
	
	private static final Integer BT_DONE =20;
	
	public static String sqlGet_SELECT_AUTO_BILLING_HWSALE="{CALL IOE.SP_SELECT_DATA_AUTO_BIILING_TRIGGER(?)}";
	public static String sqlGet_SELECT_AUTO_TRIGGER_BILLING_HWSALE="{CALL IOE.SP_AUTO_BIILING_TRIGGER_HARDWARE_SALE(?,?,?,?,?)}";
	private String FXAUTOBILLINGStatusUpdateForLocLater="UPDATE IOE.TAUTO_BILLING_LINE SET STATUS='M6_END' WHERE STATUS='BT_FAILURE' and AUTOTYPE='LOC_LATER'";
	private String FXAUTOBILLINGStatusUpdateForRest="UPDATE IOE.TAUTO_BILLING_LINE SET STATUS='M6_END' WHERE STATUS='BT_FAILURE' and AUTOTYPE<>'LOC_LATER'";
	public enum AutoType{
		RR, DEMO_REG, PARALLEL_UPGRADE,
		PD, ON_LOC_RECEIVE, LOC_LATER,
		LOC_NA, REST
	}
	
	static Lock AutoBillingTableLock = new ReentrantLock();
	static Lock AutoBillingBTProcessPickLock = new ReentrantLock();
	
	// this is a static nested class 11-12-2015 , Shubhranshu
	
	 static class AutoBillingCallable implements Callable<Object>
	 {					 		
		  		CallableStatement cSaUTO_TRIGGER_BILLING=null;
		 		ViewOrderDto orderDto=null;
		 		 ViewOrderDao objViewOrderDao=new ViewOrderDao();
		 		Connection conn=null;
		 		 String msgCode=null;
				 String msg=null;
				 int sqlCode=0;
				 long serviceproductid=0;
				 long orderno=0;
		 		
		 		public AutoBillingCallable(ViewOrderDto orderDto) {
				this.orderDto=orderDto;		
				 }		 			
		 		
				 @Override
					public String call() throws Exception
					{
							return processAutoBilling(this.orderDto);
					// return null;
					}
				 			 	
				 	public String processAutoBilling (ViewOrderDto orderDto) throws Exception {
		 		
		 			try
		 			{     		 			
		 				System.out.println("Now processing SPID : "+orderDto.getSpid()+" for orderno   "+orderDto.getOrderno()+" ");
		 				
		 				conn=DbConnection.getConnectionObject();		 					 				
		 				conn.setAutoCommit(false);
			            ViewOrderDto objdto=orderDto;			           			     		
			            cSaUTO_TRIGGER_BILLING= conn.prepareCall(sqlGet_SELECT_AUTO_TRIGGER_BILLING_HWSALE);
						cSaUTO_TRIGGER_BILLING.setLong(1,objdto.getSpid());
						cSaUTO_TRIGGER_BILLING.setLong(2,objdto.getOrderno());
						cSaUTO_TRIGGER_BILLING.registerOutParameter(3, java.sql.Types.INTEGER);
						cSaUTO_TRIGGER_BILLING.registerOutParameter(4, java.sql.Types.VARCHAR);
						cSaUTO_TRIGGER_BILLING.registerOutParameter(5, java.sql.Types.VARCHAR);
						cSaUTO_TRIGGER_BILLING.execute();			
						sqlCode=cSaUTO_TRIGGER_BILLING.getInt(3);
						msgCode= cSaUTO_TRIGGER_BILLING.getString(4);
						msg= cSaUTO_TRIGGER_BILLING.getString(5);
						serviceproductid=objdto.getSpid();
						orderno=objdto.getOrderno();
						if("-1".equals(msgCode)){
							String logMsg="errors in proc : IOE.SP_AUTO_BIILING_TRIGGER_H/W_SALE="+msgCode+"  msg="+msg+" sqlcode:"+sqlCode +"forserviceproductid="+serviceproductid +" and orderno="+orderno;
							Utility.LOG(logMsg);
							new ViewOrderDao().logExceptionForBillingTrigger(conn,logMsg,serviceproductid);
						}
				
						else{						
						//objViewOrderDao.setBTEndIfPossible(objdto.getOrderno(),conn);
								}
						conn.commit();
		 				}		 
		 				catch (Exception e) {		 
		 				/*e.printStackTrace();		 */	
		 				Utility.LOG(true,true,e,null);		
		 				conn.rollback();
		 				}
		 			finally 
		 			{
		 				try 
						{						
							if(cSaUTO_TRIGGER_BILLING!=null)DbConnection.closeCallableStatement(cSaUTO_TRIGGER_BILLING);					
							if(conn!=null){
								if(conn.isClosed()==false && conn.getAutoCommit()==false){
									conn.rollback();
								}
								DbConnection.freeConnection(conn);
							}
						} 
							catch (Exception e) 
							{
								/*e.printStackTrace();*/
								Utility.LOG(true,true,e,null);
								
							}				
		 			}		 			
		 		 return null;
		 	}
	 } // end of nested class
	public void statusUpdateforAUTOBILLINGFailed(Connection connection, AutoType autoType)
	{
		PreparedStatement pstmt= null;
		 try {
			 if(autoType==AutoType.LOC_LATER){
				 pstmt=connection.prepareStatement(FXAUTOBILLINGStatusUpdateForLocLater);
			 }else if(autoType==AutoType.REST){
				 pstmt=connection.prepareStatement(FXAUTOBILLINGStatusUpdateForRest);
			 }
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// 
			/*e.printStackTrace();*/
			Utility.LOG(e);
		}
		finally
		{
			try
			{
			if(pstmt!=null)DbConnection.closePreparedStatement(pstmt);
			}
			catch(Exception ex)
			{
				/*ex.printStackTrace();*/
				Utility.LOG(ex);
			}
		}
		
	}
	
	public void AUTOBILLING(AutoType autoType) throws Exception 
	{
		
		Connection conn = null;
			ApplicationClone apclone=null;			
        CallableStatement csSelectAutoBilling_For_HWSALE =null;
	        //CallableStatement cSaUTO_TRIGGER_BILLING =null;
         ResultSet rsSelectAutoBilling_For_HWSALE =null;
         ArrayList<ViewOrderDto> alSelectServiceDetails = null;
	         //ViewOrderDao objViewOrderDao=new ViewOrderDao();
		try 
		{
			
			
			conn = DbConnection.getConnectionObject();
			
						AutoBillingBTProcessPickLock.lock();
			try
			{
							statusUpdateforAUTOBILLINGFailed(conn,autoType); //
			}
			catch(Exception ex)
			{
				Utility.LOG(true,true,ex,null);
			}
			
			conn.setAutoCommit(false);
			
			csSelectAutoBilling_For_HWSALE= conn.prepareCall(sqlGet_SELECT_AUTO_BILLING_HWSALE);
						csSelectAutoBilling_For_HWSALE.setString(1, autoType.toString());
			rsSelectAutoBilling_For_HWSALE=csSelectAutoBilling_For_HWSALE.executeQuery();
						/* String msgCode=null;
						 String msg=null;
						 int sqlCode=0;
						long serviceproductid=0;
						long orderno=0;*/
				alSelectServiceDetails=new ArrayList<ViewOrderDto>(); 
		        while (rsSelectAutoBilling_For_HWSALE.next())
				 {
		        	
		        	ViewOrderDto objViewOrderDto= new ViewOrderDto();
					objViewOrderDto.setSpid((rsSelectAutoBilling_For_HWSALE.getLong("SPID")));
					objViewOrderDto.setOrderno((rsSelectAutoBilling_For_HWSALE.getLong("ORDERNO")));
					alSelectServiceDetails.add(objViewOrderDto);
					}
		        
			}		
						catch (Exception ex) 
						{
							//e.printStackTrace();
							Utility.LOG(true,true,ex,null);	
							conn.rollback();
						}
						finally
						{
							try{
								if(rsSelectAutoBilling_For_HWSALE!=null){
									DbConnection.closeResultset(rsSelectAutoBilling_For_HWSALE);
									DbConnection.closeCallableStatement(csSelectAutoBilling_For_HWSALE);								
									}						
									//if(cSaUTO_TRIGGER_BILLING!=null)DbConnection.closeCallableStatement(cSaUTO_TRIGGER_BILLING);
									//	if(csSelectAutoBilling_For_HWSALE!=null)													
									if(conn!=null)
									{
											if(conn.isClosed()==false && conn.getAutoCommit()==false)
											{
												//conn.rollback();
												conn.commit();													
											}
											DbConnection.freeConnection(conn);							
									}
							}finally{
								AutoBillingBTProcessPickLock.unlock();
							}
						}			         	
							
						// Shubhranshu 14-12-2015
						// modified on 8-1-16
				          int maxTaskInQueue=AppConstants.MAX_TASKS_IN_QUEUE;				        
				         // int currentTasksInQueue=0;				          
				         TaskType ttype=null;
				         if(autoType==AutoType.LOC_LATER){
				        	 ttype=TaskType.AUTO_BILLING_TRIGGER_LOC_LATER;
				         }else if(autoType==AutoType.REST){
				        	 ttype=TaskType.AUTO_BILLING_TRIGGER_REST;
				         }
				        				         				        				        
				         // ArrayList<Future<String>> statuslist=new ArrayList<Future<String>>(); // to hold future objects				         
				         apclone=ApplicationClone.getInstance(new UtilityService());
				         
				         //System.out.println("got apclone!!");
				          				          				          				          				          				     				          				        
				        // pick from arraylist and push to executer 
				         
				         ListIterator<ViewOrderDto> listitr= alSelectServiceDetails.listIterator(); // get an iterator to main arraylist
				         	
				         //System.out.println(alSelectServiceDetails.size()); // TODO remove this later 
				         
				         do
				         {		
				        	// System.out.println("in do");
				        	 
				        	 ArrayList<Callable<Object>> callablelist= new ArrayList<Callable<Object>>(); // to hold callable objects
				      
				        	 while( (listitr.hasNext()) && (callablelist.size()<=maxTaskInQueue))
						       {
						    	 callablelist.add(new AutoBillingCallable(listitr.next())); // create callable and add to list
						    	   listitr.remove(); // remove the current object from main list 
						       }					     
				        	 	if(!(callablelist.isEmpty()))
				        	 /*	{apclone.submitTaskToPool(callablelist,ttype);}		*/ 				        	 	
				        	 	{	
				        	 		//System.out.println("submitting tasks!!");
				        	 		apclone.submitTaskToPool(callablelist, ttype);
				        	 		}
				        	 	
				         }while(!(alSelectServiceDetails.isEmpty())); // until arraylist is not empty 
		        /*
     		       for(int i=0;i<alSelectServiceDetails.size();i++)
		        		{
			            	//Only maxTaskInQueue tasks should be submitted and then waited for completion and then next 500 submitted
			            	//As we need to implement a Callable , and that new class will be used only here  , => create that class as static nested class	
		            	try
		            	     {
		            		  
				            ViewOrderDto objdto=null;
				            	
				            objdto=alSelectServiceDetails.get(i);
				            		
				            cSaUTO_TRIGGER_BILLING= conn.prepareCall(sqlGet_SELECT_AUTO_TRIGGER_BILLING_HWSALE);
							cSaUTO_TRIGGER_BILLING.setLong(1,objdto.getSpid());
							cSaUTO_TRIGGER_BILLING.setLong(2,objdto.getOrderno());
							cSaUTO_TRIGGER_BILLING.registerOutParameter(3, java.sql.Types.INTEGER);
							cSaUTO_TRIGGER_BILLING.registerOutParameter(4, java.sql.Types.VARCHAR);
							cSaUTO_TRIGGER_BILLING.registerOutParameter(5, java.sql.Types.VARCHAR);
							cSaUTO_TRIGGER_BILLING.execute();
							sqlCode=cSaUTO_TRIGGER_BILLING.getInt(3);
							msgCode= cSaUTO_TRIGGER_BILLING.getString(4);
							msg= cSaUTO_TRIGGER_BILLING.getString(5);
							serviceproductid=objdto.getSpid();
							orderno=objdto.getOrderno();
							if("-1".equals(msgCode)){
								String logMsg="errors in proc : IOE.SP_AUTO_BIILING_TRIGGER_H/W_SALE="+msgCode+"  msg="+msg+" sqlcode:"+sqlCode +"forserviceproductid="+serviceproductid +" and orderno="+orderno;
								Utility.LOG(logMsg);
								new ViewOrderDao().logExceptionForBillingTrigger(conn,logMsg,serviceproductid);
							}
					
						else{
							
							//objViewOrderDao.setBTEndIfPossible(objdto.getOrderno(),conn);
						}
				         conn.commit();
		        }
		        	
		        

		        	 catch (Exception e) {
		        		 
		        		 e.printStackTrace();
		    			 conn.rollback();
		    		 }
		        }
			try 
				{
					if(rsSelectAutoBilling_For_HWSALE!=null)DbConnection.closeResultset(rsSelectAutoBilling_For_HWSALE);
					//if(cSaUTO_TRIGGER_BILLING!=null)DbConnection.closeCallableStatement(cSaUTO_TRIGGER_BILLING);
					if(csSelectAutoBilling_For_HWSALE!=null)DbConnection.closeCallableStatement(csSelectAutoBilling_For_HWSALE);
					if(conn!=null){
						if(conn.isClosed()==false && conn.getAutoCommit()==false){
							conn.rollback();
						}
						DbConnection.freeConnection(conn);
					}
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
			*/		
				
		}
	public void autoBillingProcessForRest() throws Exception {
		
		//Step1. Track of LOC Receive From LEPM
		/*System.out.println("executing here !!");*/
		try{
			
			AutoBillingTableLock.lock();
			/*System.out.println("executing in try block !!");*/
			new LepmLocDataUpdate().processLepmLocData();	
		}finally{
			AutoBillingTableLock.unlock();
			/*System.out.println("executing in finally block !!");*/
		}
		
		
		//Step2. Track LOC Received for Disconnection Order for Paallel Upgrade Cases
		/*try{
			AutoBillingTableLock.lock();
			trackLocReceivedForDisconnectionOfParallelUpgrade();	
		}finally{
			AutoBillingTableLock.unlock();
		}*/
		
		/* Step3. 
		 * Lock Screen for Auto Billing Trigger Process for
		 * 		a. Lines which are waiting for lepm loc and recieved them.
		 * 		b. Disconnection Lines of Parallel Upgrade which have recievce loc for New Order
		 * 	Lock Screen for LOC Updation for Loc_Later cases (ie eg Hw Sale)
		 */
		try{
			/*System.out.println("executing in this try block !!");*/
			AutoBillingTableLock.lock();
			lockAndEnableForActionForLocReceivedLines();	
		}finally{
			AutoBillingTableLock.unlock();
			/*System.out.println("executing in this finaly block ,,yippi!!");*/
		}
		
		try{
			AutoBillingTableLock.lock();
			updateLocAtBtScreenForLocLaterCases();
			/*System.out.println("in this try block !!");*/
		}finally{
			AutoBillingTableLock.unlock();
			/*System.out.println("finally here !!");*/
		}
		
		billingTriggerRestCases();
	}

	
	
	//
	private static final String sqlAblStatusToBtManual= " UPDATE ioe.TAUTO_BILLING_LINE " +
			                                                                                     " SET STATUS='BT_MANUAL' " +
			                                                                                     "  WHERE SPID=?" +
			                                                                                     "   AND ORDERNO=?  ";

	/*private static final String	sqlAblLockTimeAndStatus= " UPDATE ioe.TAUTO_BILLING_LINE " +
		                                                                                            " SET LOCK_TIME=CURRENT TIMESTAMP,STATUS='PREP_LOCK' " +
                                                                                                    " WHERE SPID=? " +
                                                                                                    "  AND ORDERNO=? ";*/
	
	private static final String	sqlAblUpdateLockTime= " UPDATE ioe.TAUTO_BILLING_LINE " +
            																				   " SET LOCK_TIME=CURRENT TIMESTAMP " +
            																				   " WHERE SPID=? " +
            																				   "  AND ORDERNO=? ";
	
	private static final String sqlUpdateLineAutoBillingFlag= " UPDATE IOE.TPOSERVICEDETAILS "+
                                                                                                         " SET ISAUTOBILLING=? "+
                                                                                                         " WHERE SERVICEPRODUCTID=? ";

	private static final String sqlAblStatusToInit= " UPDATE ioe.TAUTO_BILLING_LINE " +
                                                                                      " SET STATUS='INIT' " +
                                                                                      " WHERE SPID=? " +
                                                                                      "  AND ORDERNO=? ";

    private static final String sqlAblLockTimeAndStatusLocLaterCase= " UPDATE ioe.TAUTO_BILLING_LINE " +
             					                                                                                          "  SET LOCK_TIME=CURRENT TIMESTAMP, " +
             					                                                                                          "  LOC_UPDATE_AT_BT_STATUS=? " +
                                                                                                                          "  WHERE SPID=? " +
                                                                                                                          "   AND ORDERNO=? ";

    private static final String AblUpdateStatusLocLaterCase=" UPDATE ioe.TAUTO_BILLING_LINE " +				                                                                                           
                                                                                                          " SET LOC_UPDATE_AT_BT_STATUS=? " +
                                                                                                          " WHERE SPID=? " +
                                                                                                          "  AND ORDERNO=? ";
    
		/*Shubhranshu, 2-aug-2016*/

public void lockAndEnableForActionForLocReceivedLines() throws Exception 
{
	Connection connection=null;							
	ArrayList<AutoBillingLineDto>pendingDataArrayList=null;
	AutoBillingLineDto item=null;
	Long serviceId=null;
	Long orderNo=null;
	Long serviceProductId=null;
	String autoType=null;
	Integer statusBT=null;							
	String statusAutoBilling=null;
	String m6FxProgressStatus=null;
	String statusLocUpdateAtBT=null;
	String locDate=null;
	String locNumber=null;
	String locReceivedDate=null;

	PreparedStatement	pstAblStatusToBtManual=null;
	PreparedStatement pstAblUpdateLockTime=null;
	PreparedStatement pstUpdateLineAutoBillingFlag=null;
	PreparedStatement pstAblStatusToInit=null;																
	PreparedStatement pstAblLockTimeAndStatusLocLaterCase=null;	
	PreparedStatement pstAblUpdateStatusLocLaterCase=null;

	connection=DbConnection.getConnectionObject();
	connection.setAutoCommit(false);

	pstAblStatusToBtManual=connection.prepareStatement(sqlAblStatusToBtManual);
	pstAblUpdateLockTime=connection.prepareStatement(sqlAblUpdateLockTime);
	pstUpdateLineAutoBillingFlag=connection.prepareStatement(sqlUpdateLineAutoBillingFlag);
	pstAblStatusToInit=connection.prepareStatement(sqlAblStatusToInit);									
	pstAblLockTimeAndStatusLocLaterCase=connection.prepareStatement(sqlAblLockTimeAndStatusLocLaterCase);
	pstAblUpdateStatusLocLaterCase=connection.prepareStatement(AblUpdateStatusLocLaterCase);

	//create a hashmap containing PST and a flag
	HashMap<PreparedStatement, Boolean> pstExecuteControlMap =new HashMap<PreparedStatement, Boolean>();
	 	
	pstExecuteControlMap.put(pstAblStatusToBtManual, false);
	pstExecuteControlMap.put(pstAblUpdateLockTime, false);
	pstExecuteControlMap.put(pstUpdateLineAutoBillingFlag, false);
	pstExecuteControlMap.put(pstAblStatusToInit, false);
	pstExecuteControlMap.put(pstAblLockTimeAndStatusLocLaterCase, false);
	pstExecuteControlMap.put(pstAblUpdateStatusLocLaterCase, false);
																
	do
	{
		pendingDataArrayList=new NewOrderDao().getAllDataPendingForAutoBilling();
			ListIterator<AutoBillingLineDto> itr=pendingDataArrayList.listIterator();			
				while(itr.hasNext())
				{
							item=itr.next();
								serviceProductId=item.getServiceProductId();
									serviceId=item.getServiceId();
										orderNo=item.getOrderNo();
											autoType=item.getAutoType();
												statusBT=item.getBt_Status();
											statusAutoBilling=item.getAutoBillingStatus();		
										m6FxProgressStatus=item.getM6_Fx_Progress_Status();
									statusLocUpdateAtBT=item.getStatusLocUpdateAtBT();		
								locDate=item.getLocDate();
							locNumber=item.getLocNo();
						locReceivedDate=item.getLocReceivedDate();
						
						if(statusAutoBilling.equalsIgnoreCase("PREP") && (autoType.equalsIgnoreCase("PARALLEL_UPGRADE") || autoType.equalsIgnoreCase("ON_LOC_RECEIVE")))
						{
							if(statusBT.equals(BT_DONE) || "FX_BT_END".equalsIgnoreCase(m6FxProgressStatus))
							{
									/*call pst to update status=bt_manual*/		
								pstAblStatusToBtManual.setLong(1,serviceProductId); 						 																					
								pstAblStatusToBtManual.setLong(2, orderNo);			
								
								/*// update isAB flag to 1;
								pstUpdateLineAutoBillingFlag.setLong(2, serviceProductId);	
								pstUpdateLineAutoBillingFlag.setInt(1,1);	*/
								
							/*	updateFlagForExecution(pstUpdateLineAutoBillingFlag,pstExecuteControlMap);		*/
								updateFlagForExecution(pstAblStatusToBtManual,pstExecuteControlMap);																																																																						
							} 																				
							else
							{															
									//pstAblLockTimeAndStatus.setLong(1, serviceProductId);/*update LOC_TIME to current timestamp , status=PREP_LOCK and isAutoBilling flag=104/190*/
									//pstAblLockTimeAndStatus.setLong(2,orderNo);
								
										pstUpdateLineAutoBillingFlag.setLong(2, serviceProductId);	
										
												if(autoType.equalsIgnoreCase("PARALLEL_UPGRADE"))
												{
													pstUpdateLineAutoBillingFlag.setInt(1,104);
												}
											else if(autoType.equalsIgnoreCase("ON_LOC_RECEIVE"))
												{	
													pstUpdateLineAutoBillingFlag.setInt(1,190);
												}	
												//update status to INIT in TABL												
												pstAblStatusToInit.setLong(1, serviceProductId);																														
												pstAblStatusToInit.setLong(2, orderNo);
												
												// Update LOCK_TIME
												pstAblUpdateLockTime.setLong(1, serviceProductId);
												pstAblUpdateLockTime.setLong(2, orderNo);
												
												updateFlagForExecution(pstAblStatusToInit,pstExecuteControlMap);																																						
												updateFlagForExecution(pstUpdateLineAutoBillingFlag,pstExecuteControlMap);	
												updateFlagForExecution(pstAblUpdateLockTime,pstExecuteControlMap);	
												
							}											
						}
						/*else if (statusAutoBilling.equalsIgnoreCase("PREP_LOCK") && (autoType.equalsIgnoreCase("PARALLEL_UPGRADE") || autoType.equalsIgnoreCase("ON_LOC_RECEIVE")))
						{
								if(statusBT.equals(BT_DONE) || "FX_BT_END".equals(m6FxProgressStatus))
								{																	 																																																	 																					 
									pstAblStatusToBtManual.setLong(1,serviceProductId);call pst to update status=bt_manual 																																																			
									pstAblStatusToBtManual.setLong(2,orderNo);
									
									pstUpdateLineAutoBillingFlag.setLong(1, 0); Update isAutoBilling Flag to zero
									pstUpdateLineAutoBillingFlag.setLong(2, serviceProductId);	
									
									updateFlagForExecution(pstAblStatusToBtManual,pstExecuteControlMap);
									updateFlagForExecution(pstUpdateLineAutoBillingFlag,pstExecuteControlMap);
								}																													 																				
								else
								{																														
									update status to INIT 													
									pstAblStatusToInit.setLong(1, serviceProductId);																														
									pstAblStatusToInit.setLong(2, orderNo);
									updateFlagForExecution(pstAblStatusToInit,pstExecuteControlMap);
								}
						}*/
													
						
						// autoType=LOC_LATER cases
						if(("PREP").equalsIgnoreCase(statusLocUpdateAtBT) && autoType.equalsIgnoreCase("LOC_LATER"))
						{
								if(isLOCFieldEmpty(locDate,locNumber,locReceivedDate))
								{
									pstAblLockTimeAndStatusLocLaterCase.setString(1, "PREP_LOCK");		/*call pst to update status to prep_lock and isAutoBilling to 191*/
									pstAblLockTimeAndStatusLocLaterCase.setLong(2, serviceProductId);
									pstAblLockTimeAndStatusLocLaterCase.setLong(3, orderNo);
																						
									pstUpdateLineAutoBillingFlag.setLong(1,191);
									pstUpdateLineAutoBillingFlag.setLong(2, serviceProductId);
									
									updateFlagForExecution(pstAblLockTimeAndStatusLocLaterCase,pstExecuteControlMap);
									updateFlagForExecution(pstUpdateLineAutoBillingFlag,pstExecuteControlMap);
								}
							else
								{
									pstAblUpdateStatusLocLaterCase.setString(1, "MANUAL_DONE");		/*call pst to update status to manual_done*/
									pstAblUpdateStatusLocLaterCase.setLong(2, serviceProductId);
									pstAblUpdateStatusLocLaterCase.setLong(3, orderNo);
									
									updateFlagForExecution(pstAblUpdateStatusLocLaterCase,pstExecuteControlMap);										
								}			
						}
						
						else if(("PREP_LOCK").equalsIgnoreCase(statusLocUpdateAtBT) && autoType.equalsIgnoreCase("LOC_LATER"))
						{
								if(isLOCFieldEmpty(locDate,locNumber,locReceivedDate))
								{
									pstAblUpdateStatusLocLaterCase.setString(1, "INIT");		//call pst to update status to init
									pstAblUpdateStatusLocLaterCase.setLong(2, serviceProductId);																																
									pstAblUpdateStatusLocLaterCase.setLong(3, orderNo);
									
									updateFlagForExecution(pstAblUpdateStatusLocLaterCase,pstExecuteControlMap);	
								}
								else
								{
									pstAblUpdateStatusLocLaterCase.setString(1, "MANUAL_DONE");		//call pst to update status to manual_done and revert AB flag 
									pstAblUpdateStatusLocLaterCase.setLong(2, serviceProductId);																																
									pstAblUpdateStatusLocLaterCase.setLong(3, orderNo);	
									
									pstUpdateLineAutoBillingFlag.setLong(1,1);
									pstUpdateLineAutoBillingFlag.setLong(2,serviceProductId);
									
									updateFlagForExecution(pstAblUpdateStatusLocLaterCase,pstExecuteControlMap);	
									updateFlagForExecution(pstUpdateLineAutoBillingFlag,pstExecuteControlMap);
								}					
						}																						
																					
						try
							{
								executeBatchUpdateProcess(pstExecuteControlMap);
								connection.commit();
								resetExecutionControlMap(pstExecuteControlMap);
							}
							catch(SQLException sqe)
							{
							connection.rollback();
							}																		
			}// end of while 																																																																																												
	}while(pendingDataArrayList.size()>0);					
		try
			{
					DbConnection.closePreparedStatement(pstUpdateLineAutoBillingFlag);
						DbConnection.closePreparedStatement(pstAblUpdateLockTime);
							DbConnection.closePreparedStatement(pstAblLockTimeAndStatusLocLaterCase);
							DbConnection.closePreparedStatement(pstAblStatusToInit);
						DbConnection.closePreparedStatement(pstAblStatusToBtManual);													
					DbConnection.freeConnection(connection);					
			}
		catch(SQLException sqe)
		{
		Utility.LOG(sqe);
		}
	}// End of Method

/**************************************************************************************************/
	
	// Shubhranshu

	private void resetExecutionControlMap(HashMap<PreparedStatement, Boolean> pstExecuteControlMap)
	{
			Set<Entry<PreparedStatement, Boolean>> entrySet=pstExecuteControlMap.entrySet();
				for(Entry<PreparedStatement, Boolean> entry : entrySet)
				{
					entry.setValue(false);
			}						
	}

	private void executeBatchUpdateProcess(HashMap<PreparedStatement, Boolean> pstExecuteControlMap) throws SQLException 
	{
		Set<PreparedStatement> keySet=pstExecuteControlMap.keySet();
			Iterator<PreparedStatement> itr=keySet.iterator();
				while(itr.hasNext())
				{	
					PreparedStatement pstAsKey=itr.next();
						if(pstExecuteControlMap.get(pstAsKey)) // if value corresponding to Key in Map is true , then execute batch
						{
							pstAsKey.executeBatch();							
						}
				}	
	}
	
	private void updateFlagForExecution(PreparedStatement pst ,HashMap<PreparedStatement, Boolean> pecm) throws Exception 
	{				
		Set<Entry<PreparedStatement, Boolean>> entrySet=pecm.entrySet();				
			for(Entry<PreparedStatement, Boolean> entry : entrySet)
				{
					if(pst.equals(entry.getKey()))
					{
						entry.setValue(true);
						entry.getKey().addBatch(); // add Prepared Statement For Batch Processing
					}				
				}		
	}
	//This Method search for empty LOC Fields for serviceId 
			// returns true if all three LOCfield are empty
			private boolean isLOCFieldEmpty(String locDate,String locNumber,String locReceivedDate)
			{
				if( (locDate==null || locDate.length()==0) && (locNumber==null || locNumber.length()==0) && (locReceivedDate==null || locReceivedDate.length()==0) )
				{
					return true;
				}
				return false;			
			}// end of method
			
			
			
			private static final String sqlgetLocFieldDataPendingToBeFilled=
					" SELECT tabl.ORDERNO,tabl.SERVICEID,tabl.SPID,loc.LOCNO,loc.LOCDATE,loc.LOC_REC_DATE " +
						" FROM ioe.TAUTO_BILLING_LINE tabl "+ 
						" INNER JOIN ioe.TM_TEMP_LOC_DATA loc ON loc.SNO=tabl.LOC_RECORD_ID "+
						" WHERE tabl.AUTOTYPE='LOC_LATER' AND tabl.LOC_RECEIVE_STATUS='DONE' " +
						" AND LOC_UPDATE_AT_BT_STATUS='INIT' ";


			private static final String sqlUpdateLocField=	" UPDATE IOE.TPOSERVICEDETAILS " +
																								" SET LOCDATE=?, LOCNO=?, LOC_REC_DATE=? " +
																								" WHERE SERVICEPRODUCTID=? ";
			
			private static final String sqlAblUpdateStatusAfterLocFill=" UPDATE IOE.TAUTO_BILLING_LINE " +
																												  "  SET LOC_UPDATE_AT_BT_STATUS='DONE' " +
																												  "	  WHERE "+
																												  "	  SPID=? " +
																												  "  AND ORDERNO= ? ";

	//fills loc data with the values received in the table
	
	public void updateEmptyLocField() throws Exception
		{		
			Connection connection=null;				
				ArrayList<AutoBillingLineDto> listOfServiceIdWithLocFieldDataToBeFilled=null;
					connection=DbConnection.getConnectionObject();	
						try
							{
								do
									{
											listOfServiceIdWithLocFieldDataToBeFilled= getDataForEmptyLocField(connection);				
											fillDataInLocFields(connection,listOfServiceIdWithLocFieldDataToBeFilled);								
									}
								while(listOfServiceIdWithLocFieldDataToBeFilled.size()>0);
							}			
						finally 
					{									
						DbConnection.freeConnection(connection);
				}																
		}
	
	private ArrayList<AutoBillingLineDto> getDataForEmptyLocField(Connection connection) throws Exception	
	{	
		Statement	stmt=null;
			ResultSet rst=null;	
				ArrayList<AutoBillingLineDto> objRetDtoList=new ArrayList<AutoBillingLineDto>();
						stmt=connection.createStatement();		
							stmt.setMaxRows(5000);
								rst=stmt.executeQuery(sqlgetLocFieldDataPendingToBeFilled);									
									try
										{
											while(rst.next())
												{
													AutoBillingLineDto dataDto=new AutoBillingLineDto();
														dataDto.setServiceId(rst.getLong("SERVICEID"));
															dataDto.setOrderNo(rst.getLong("ORDERNO"));
																dataDto.setLocDate(rst.getString("LOCDATE"));
																dataDto.setServiceProductId(rst.getLong("SPID"));
															dataDto.setLocNo(rst.getString("LOCNO"));
														dataDto.setLocReceivedDate(rst.getString("LOC_REC_DATE"));
													objRetDtoList.add(dataDto);
												}
										}
							finally
						{
								DbConnection.closeStatement(stmt);
							DbConnection.closeResultset(rst);			
			}																						
			return objRetDtoList;
	}
		
		private void fillDataInLocFields(Connection con,ArrayList<AutoBillingLineDto>locFieldDataList) throws Exception		
		{	
							PreparedStatement pstUpdateLocField=null;
							PreparedStatement pstAblUpdateStatusAfterLocFill=null;
							
					if(locFieldDataList.size()>0)
				{
							pstUpdateLocField=con.prepareStatement(sqlUpdateLocField);
							pstAblUpdateStatusAfterLocFill=con.prepareStatement(sqlAblUpdateStatusAfterLocFill);
							
							ListIterator<AutoBillingLineDto> itr=locFieldDataList.listIterator();
						
							while(itr.hasNext())
								{
								AutoBillingLineDto item=itr.next();	
										pstUpdateLocField.setString(1,item.getLocDate());
											pstUpdateLocField.setString(2, item.getLocNo());
												pstUpdateLocField.setString(3, item.getLocReceivedDate());	
													pstUpdateLocField.setLong(4,item.getServiceProductId());
													
												pstAblUpdateStatusAfterLocFill.setLong(1, item.getServiceProductId());
											pstAblUpdateStatusAfterLocFill.setLong(2,item.getOrderNo());
											
										pstUpdateLocField.addBatch();	
									pstAblUpdateStatusAfterLocFill.addBatch();
								}										
											// execute the batch update in a transaction
											con.setAutoCommit(false);
									try
										{																							
												Utility.throwExceptionIfBatchFailed(pstUpdateLocField.executeBatch()); // an Exception is thrown if Batch Updation fails
													Utility.throwExceptionIfBatchFailed(pstAblUpdateStatusAfterLocFill.executeBatch());
												con.commit();
										}
									catch(Exception sqe)
										{
											con.rollback();
											Utility.LOG(sqe);
										}
									finally
									{	
										con.setAutoCommit(true);
										DbConnection.closePreparedStatement( pstUpdateLocField);
										DbConnection.closePreparedStatement(pstAblUpdateStatusAfterLocFill);
									}	
					}//end of if 
		}
		
		//Shubhranshu,4-8-2016
			
			/*private static final String sqlupdateStatusLocNotReceivedCases=" UPDATE IOE.TAUTO_BILLING_LINE " +
																															 " SET STATUS='BT_MANUAL', LOC_RECEIVE_STATUS='NOT_REQ' , " +
																															 " LOC_UPDATE_AT_BT_STATUS='MANUAL_DONE' " +
																															 " WHERE SERVICEID=? AND ORDERNO=? ";*/
			
		public void updateStatusLocNotReceivedCases() throws Exception
		{
			ArrayList<AutoBillingLineDto> dataDtoList=null;
				//PreparedStatement pstUpdateStatusLocNotReceivedCases=null;
					Connection con=null;		
						con=DbConnection.getConnectionObject();
							//pstUpdateStatusLocNotReceivedCases=con.prepareStatement(sqlupdateStatusLocNotReceivedCases);																			
								dataDtoList = new NewOrderDao().getDataForLocNotReceivedCases();		
									try
										{
											updateFieldLocNotReceivedCases(dataDtoList,con);
										}
										catch(Exception ex)
										{
											Utility.LOG(ex);
										}
										finally
									{
									DbConnection.freeConnection(con);
									}
		}
		
		//
		private static final String query_1= " UPDATE IOE.TAUTO_BILLING_LINE " +
                                                                    	" SET STATUS='NOT_REQ' WHERE ID=? " ;
		
		private static final String query_2= " UPDATE IOE.TAUTO_BILLING_LINE " +
                														" SET LOC_RECEIVE_STATUS='NOT_REQ' WHERE ID=? " ;
		
		private static final String query_3= " UPDATE IOE.TAUTO_BILLING_LINE " +
                														" SET LOC_UPDATE_AT_BT_STATUS='NOT_REQ' WHERE ID=? " ;
		
		//
	private void updateFieldLocNotReceivedCases(ArrayList<AutoBillingLineDto> dtolist,Connection con) throws Exception
	{	
		PreparedStatement updateStatusField=con.prepareStatement(query_1);
		PreparedStatement updateField_LocReceiveStatus=con.prepareStatement(query_2);
			PreparedStatement updateField_LocUpdateAtBtStatus=con.prepareStatement(query_3);
				Long id=null;
					String statusAbl=null;
						String statusLocReceive=null;
							String statusLocUpdateAtBt=null;	
							try
							{																			
								for(AutoBillingLineDto item:dtolist)
									{
													id=item.getId();
													statusAbl=item.getAutoBillingStatus();
													statusLocReceive=item.getStatusLocReceived();
													statusLocUpdateAtBt=item.getStatusLocUpdateAtBT();
													
													if("PENDING".equalsIgnoreCase(statusLocReceive))
													{
														updateField_LocReceiveStatus.setLong(1, id);
														updateField_LocReceiveStatus.execute();
													}
													if((statusLocUpdateAtBt==null || statusLocUpdateAtBt.length()==0)||"PREP".equalsIgnoreCase(statusLocUpdateAtBt) || "PREP_LOCK".equalsIgnoreCase(statusLocUpdateAtBt))
													{
														updateField_LocUpdateAtBtStatus.setLong(1, id);
														updateField_LocUpdateAtBtStatus.execute();
													}
													if("PREP".equalsIgnoreCase(statusAbl) || "PREP_LOCK".equalsIgnoreCase(statusAbl)||"INIT".equalsIgnoreCase(statusAbl))
													{
														updateStatusField.setLong(1, id);
														updateStatusField.execute();
													}
									}
	
							}
							catch(Exception ex)
						{
								con.rollback();
							Utility.LOG(ex);
						}			
		finally
		{
			DbConnection.closePreparedStatement(updateStatusField);
			DbConnection.closePreparedStatement(updateField_LocUpdateAtBtStatus);
			DbConnection.closePreparedStatement(updateField_LocReceiveStatus);
		}
	}
		//Shubhranshu
	
	
	/*public void trackLocReceivedForDisconnectionOfParallelUpgrade() {
		try{
			ArrayList<DisconnectionLineParallelUpgradedInfo> lines = null;
			//do{
			lines = fetchNextParallelUpgradeDiscLines();
			
			if(lines.size()>0){
				lines = filterAndFillNewServiceDetails(lines);
			}
			
			if(lines.size()>0){
				lines = fillNewServiceLocDetails(lines);
				updateTriggerDetails(lines);
			}
				
			//}while(lines!=null && lines.size()>0);	
		}catch(Exception ex){
			Utility.LOG(ex);
		}
	}*/
	
	/*private ArrayList<DisconnectionLineParallelUpgradedInfo> fillNewServiceLocDetails(
			ArrayList<DisconnectionLineParallelUpgradedInfo> lines) throws Exception{

		Connection connection = null;
		PreparedStatement pstmtFindTriggerDetails = null;
		PreparedStatement pstmtFindTriggerDetailsFromHistory = null;
		
		try{
			connection = DbConnection.getConnectionObject();

			String sqlFindTriggerDetailsNewOrders = 
					" SELECT LOC_REC_DATE,BILLINGTRIGGERDATE FROM ioe.TPOSERVICEDETAILS serdet "+
					" INNER JOIN ioe.TDISCONNECTION_HISTORY tdh ON tdh.SERVICE_PRODUCT_ID=serdet.SERVICEPRODUCTID "+
					" WHERE tdh.MAIN_SERVICEID = ? AND serdet.FOR_BILLINGTRIGGER='Y' AND serdet.PARENT_SERVICEPRODUCTID<>0 AND serdet.BILLING_TRIGGER_STATUS=20 "+
					" FETCH FIRST ROW ONLY ";
			
			String sqlFindTriggerDetailsNewOrdersFromHistory =
					" SELECT LOC_REC_DATE,BILLINGTRIGGERDATE FROM ioe.TPOSERVICEDETAILS_HISTORY serdet "+
					" INNER JOIN ioe.TDISCONNECTION_HISTORY tdh ON tdh.SERVICE_PRODUCT_ID=serdet.SERVICEPRODUCTID AND tdh.MAIN_SERVICEID=serdet.MAIN_SERVICEID "+
					" WHERE tdh.MAIN_SERVICEID = ? AND serdet.FOR_BILLINGTRIGGER='Y' AND serdet.PARENT_SERVICEPRODUCTID<>0 AND serdet.BILLING_TRIGGER_STATUS=20 "+
					" FETCH FIRST ROW ONLY ";

			pstmtFindTriggerDetails = connection.prepareStatement(sqlFindTriggerDetailsNewOrders);
			pstmtFindTriggerDetailsFromHistory = connection.prepareStatement(sqlFindTriggerDetailsNewOrdersFromHistory);
			
			for (DisconnectionLineParallelUpgradedInfo line : lines) {
				PreparedStatement pstmt = null;
				if(line.getNewOrderServiceIsInHistory()==0){
					pstmt = pstmtFindTriggerDetails;
				}else if(line.getNewOrderServiceIsInHistory()==1){
					pstmt = pstmtFindTriggerDetailsFromHistory;
				}
				pstmt.setLong(1, line.getNewOrderServiceId());
				
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()){
					line.setNewOrderLineLocRecDate(rs.getString("LOC_REC_DATE"));
					line.setNewOrderLineBTDate(rs.getString("BILLINGTRIGGERDATE"));
				}
				DbConnection.closeResultset(rs);
			}
		}finally{
			DbConnection.closeStatement(pstmtFindTriggerDetails);
			DbConnection.closeStatement(pstmtFindTriggerDetailsFromHistory);
			DbConnection.freeConnection(connection);
		}
		return lines;
	}

	private ArrayList<DisconnectionLineParallelUpgradedInfo> filterAndFillNewServiceDetails(ArrayList<DisconnectionLineParallelUpgradedInfo> lines) throws Exception {
		
		Connection connection = null;
		PreparedStatement pstmt_checkNewServiceClosed = null;
		
		try{
			connection= DbConnection.getConnectionObject();
			String sqlFindClosedNewOrders = 
					" SELECT tpg.SERVICEID,sermas.IS_IN_HISTORY " + 
					" FROM ioe.TPOSERVICE_PARALLEL_UPGRADE tpg " +
					" 	INNER JOIN ioe.TPOSERVICEMASTER sermas ON sermas.SERVICEID = tpg.SERVICEID "+
					" WHERE sermas.M6_FX_PROGRESS_STATUS='FX_BT_END' AND tpg.PARALLEL_UPGRADE_LSI=?	 ";

			pstmt_checkNewServiceClosed = connection.prepareStatement(sqlFindClosedNewOrders);
			Iterator<DisconnectionLineParallelUpgradedInfo> linesIterator = lines.iterator();
			
			while(linesIterator.hasNext()){
				DisconnectionLineParallelUpgradedInfo line = linesIterator.next();
				pstmt_checkNewServiceClosed.setLong(1, line.getLogicalSiNo());
				ResultSet rs = pstmt_checkNewServiceClosed.executeQuery();
				if(rs.next()){
					line.setNewOrderServiceId(rs.getLong("SERVICEID"));
					line.setNewOrderServiceIsInHistory(rs.getInt("IS_IN_HISTORY"));
				}else{
					linesIterator.remove();
				}
				DbConnection.closeResultset(rs);
			}
				
		}finally{
			DbConnection.closeStatement(pstmt_checkNewServiceClosed);
			DbConnection.freeConnection(connection);
		}
		
		return lines;
	}

	static class DisconnectionLineParallelUpgradedInfo{
		long id;
		long serviceProductId;
		long logicalSiNo;
		long newOrderServiceId;
		int newOrderServiceIsInHistory;
		long newOrderLineId;
		String newOrderLineLocRecDate;
		String newOrderLineBTDate;

		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public long getServiceProductId() {
			return serviceProductId;
		}
		public void setServiceProductId(long serviceProductId) {
			this.serviceProductId = serviceProductId;
		}
		public long getLogicalSiNo() {
			return logicalSiNo;
		}
		public void setLogicalSiNo(long logicalSiNo) {
			this.logicalSiNo = logicalSiNo;
		}
		public long getNewOrderServiceId() {
			return newOrderServiceId;
		}
		public void setNewOrderServiceId(long newOrderServiceId) {
			this.newOrderServiceId = newOrderServiceId;
		}
		public int getNewOrderServiceIsInHistory() {
			return newOrderServiceIsInHistory;
		}
		public void setNewOrderServiceIsInHistory(int newOrderServiceIsInHistory) {
			this.newOrderServiceIsInHistory = newOrderServiceIsInHistory;
		}
		public long getNewOrderLineId() {
			return newOrderLineId;
		}
		public void setNewOrderLineId(long newOrderLineId) {
			this.newOrderLineId = newOrderLineId;
		}
		public String getNewOrderLineLocRecDate() {
			return newOrderLineLocRecDate;
		}
		public void setNewOrderLineLocRecDate(String newOrderLineLocRecDate) {
			this.newOrderLineLocRecDate = newOrderLineLocRecDate;
		}
		public String getNewOrderLineBTDate() {
			return newOrderLineBTDate;
		}
		public void setNewOrderLineBTDate(String newOrderLineBTDate) {
			this.newOrderLineBTDate = newOrderLineBTDate;
		}
	}

	private void updateTriggerDetails(ArrayList<DisconnectionLineParallelUpgradedInfo> lines) throws Exception{
		Connection connection = null;
		PreparedStatement pstmtUpdateBtDetails = null;
		try{
			connection = DbConnection.getConnectionObject();

			String sqlUpdateTriggerDetails = 
					" update ioe.TAUTO_BILLING_LINE "+
					" SET AUTO_REF_LOC_REC_DATE=?,AUTO_REF_BT_DATE=?,LOC_RECEIVE_STATUS='DONE' "+
					" WHERE id=?";
			pstmtUpdateBtDetails = connection.prepareStatement(sqlUpdateTriggerDetails);
			boolean anyInUpdateBatch = false;
			for (DisconnectionLineParallelUpgradedInfo line : lines) {
				pstmtUpdateBtDetails.setString(1, line.getNewOrderLineLocRecDate());
				pstmtUpdateBtDetails.setString(2, line.getNewOrderLineBTDate());
				pstmtUpdateBtDetails.setLong(3, line.getId());
				pstmtUpdateBtDetails.addBatch();
				anyInUpdateBatch = true;
			}
			if(anyInUpdateBatch){
				int[] res = pstmtUpdateBtDetails.executeBatch();
				Utility.throwExceptionIfBatchFailed(res);
			}
		}finally{
			DbConnection.closeStatement(pstmtUpdateBtDetails);
			DbConnection.freeConnection(connection);
		}
	}

	private ArrayList<DisconnectionLineParallelUpgradedInfo> fetchNextParallelUpgradeDiscLines()throws Exception {
		
		ArrayList<DisconnectionLineParallelUpgradedInfo> lines = new ArrayList<FX_AUTOLINES_BILLINGTRIGGER.DisconnectionLineParallelUpgradedInfo>();

		Connection conn = null;
		PreparedStatement pstmt = null ;
		ResultSet rs=null;
		try{
			conn = DbConnection.getConnectionObject();
			pstmt = conn.prepareStatement(
					" SELECT au.id,sermas.LOGICAL_SI_NO FROM ioe.TAUTO_BILLING_LINE au "+
					" 	INNER JOIN ioe.TPOSERVICEMASTER sermas ON sermas.SERVICEID=au.SERVICEID "+
					" WHERE au.AUTOTYPE='PARALLEL_UPGRADE' AND au.STATUS='PREP' AND au.LOC_RECEIVE_STATUS='PENDING' "+
					" --FETCH FIRST 5000 ROWS only");
			
			rs=pstmt.executeQuery();
			while (rs.next()){
				DisconnectionLineParallelUpgradedInfo line=new DisconnectionLineParallelUpgradedInfo();
				line.setId(rs.getLong("ID"));
				line.setLogicalSiNo(rs.getLong("LOGICAL_SI_NO"));
				lines.add(line);
			}
		}finally{
				DbConnection.closeResultset(rs);
				DbConnection.closePreparedStatement(pstmt);
				DbConnection.freeConnection(conn);
		}
		return lines;	
	}
*/
	public void billingTriggerRestCases() {
		try {
			AUTOBILLING(AutoType.REST);
		} catch (Exception e) {
			Utility.LOG(e);
		}
	}

/*	private void lockAndEnableParallelUpgradeDisconnectionCasesForAutoTrigger() {
		// TODO Auto-generated method stub
		
	}

	private void lockAndEnableLocReceivedLinesForLocUpdation() {
		// TODO Auto-generated method stub
		
	}

	private void lockAndEnableLocReceivedLinesForAutoTrigger() {
		// TODO Auto-generated method stub
		
	}

	private void trackLocReceivedFromLepm() {
		// TODO Auto-generated method stub
		
	}
*/
	public void autoBillingProcessForLocLater() {
		try{
			AutoBillingTableLock.lock();
			billingTriggerLocLaterCases();	
		}finally{
			AutoBillingTableLock.unlock();
		}
	}

	public void updateLocAtBtScreenForLocLaterCases() {
		try {
			updateEmptyLocField();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void billingTriggerLocLaterCases() {
		try {
			AUTOBILLING(AutoType.LOC_LATER);
		} catch (Exception e) {
			Utility.LOG(e);
		}
	}
	
	/*public static void main(String[] args) {
		new FX_AUTOLINES_BILLINGTRIGGER().trackLocReceivedForDisconnectionOfParallelUpgrade();
	}*/
}


