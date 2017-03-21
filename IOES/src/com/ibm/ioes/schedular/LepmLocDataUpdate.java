package com.ibm.ioes.schedular;

/*
 * Tag Name Resource Name  Date		      CSR No			Description
 * [002]    Gunjan Singla  28-July-14  20140717_R1_020274   Change in Commissioning date on Invoice
 * */ 
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

import com.ibm.db2.jcc.b.ob;
import com.ibm.ioes.beans.ViewOrderDto;
import com.ibm.ioes.ecrm.DBConnectionRetriever;
import com.ibm.ioes.forms.DisconnectOrderDto;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Utility;

public class LepmLocDataUpdate {

public static String sqlGetLocDataTempTable = "{CALL IOE.SP_GET_LOC_DATA_TEMP_TABLE()}";
public static String sqlInsert_LocData = "{CALL IOE.SP_INSERT_LOC_LEPM_DATA(?,?,?,?,?,?,?,?,?,?)}";
public static String strgetLOCDatafromCRM="select \"Order No\" as ORDERNO,OrderLineNo,\"Billing Trigger Date Stage\" as STAGE ,\"Service No\" as SERVICENO, \"LOC No\" as LOCNO, \"LOC Date\" as LOCDATE,\"LOC Recvd Date\" as LOC_REC_DATE from ViewLOCIB2BDetail";

public void updateLepmLocData() throws Exception 
	{
		Connection conn = null;
	    CallableStatement csGetLOCDetails =null;
	    ResultSet rsGetLOCDetails =null;
	    
	    PreparedStatement ps=null;
	    PreparedStatement psAutoBilling = null;
	    PreparedStatement psCheckAutoBillingLine = null;
	    PreparedStatement psFindNoChargesAutoBillingLine = null;
	    PreparedStatement psUpdateAutoBilingDataFromSibling = null;
	    PreparedStatement ps_updatetemp=null;
		ArrayList<String> lineno = new ArrayList<String>();
		ArrayList<ViewOrderDto> lstlocdetails = null;
		String output ="";
		Connection crmcon=DBConnectionRetriever.getSqLServerConnection();	
	     try {
	    	 	conn = DbConnection.getConnectionObject();
				conn.setAutoCommit(false);
				try
				{
					System.out.println("inserting CRM Data in IB2B Table ");
					InsertLEPMDatainIB2BTAble(conn,crmcon);
				}
				catch(Exception ex)
				{
					Utility.LOG(true,true,ex,null);
				}
     		}		
	           
     catch (Exception e) 
     {
		   e.printStackTrace();
		   conn.rollback();
     }
     
	 finally{
			try 
				{
					DbConnection.freeConnection(conn);
					DbConnection.freeConnection(crmcon);
				} 
				catch (Exception e) 
				{
				e.printStackTrace();
				}
			}	
	}


public void processLepmLocData(){
	Connection conn = null;
    CallableStatement csGetLOCDetails =null;
    ResultSet rsGetLOCDetails =null;
    
    PreparedStatement ps=null;
    PreparedStatement psAutoBilling = null;
    PreparedStatement psCheckAutoBillingLine = null;
    PreparedStatement psCheckIsServiceInProgress = null;
    PreparedStatement psFindNoNewChargesAutoBillingLine = null;
    PreparedStatement psUpdateAutoBilingDataFromSibling = null;
    PreparedStatement ps_updatetemp=null;
    PreparedStatement psLogLocReceiveTime=null;
	ArrayList<String> lineno = new ArrayList<String>();
	ArrayList<ViewOrderDto> lstlocdetails = null;
	String output ="";
	 	
     try {
    	 	conn = DbConnection.getConnectionObject();
			conn.setAutoCommit(false);
			
			
			do{
				lstlocdetails = new ArrayList<ViewOrderDto>();
				
				csGetLOCDetails= conn.prepareCall(sqlGetLocDataTempTable);
				rsGetLOCDetails=csGetLOCDetails.executeQuery();
				while (rsGetLOCDetails.next()) 
				{
					ViewOrderDto objLocDto= new ViewOrderDto();		
					objLocDto.setSno(rsGetLOCDetails.getLong("SNO"));
					objLocDto.setLocNo(rsGetLOCDetails.getString("LOCNO"));
					objLocDto.setLocDate(rsGetLOCDetails.getString("LOCDATE"));
					objLocDto.setLocRecDate(rsGetLOCDetails.getString("LOC_REC_DATE"));
					objLocDto.setBillingTriggerDate(rsGetLOCDetails.getString("BILLINGTDATE"));
					objLocDto.setServiceId(rsGetLOCDetails.getString("SERVICENO"));
					objLocDto.setOrderNo(rsGetLOCDetails.getString("ORDERNO"));
					objLocDto.setServiceProductID(rsGetLOCDetails.getString("LINENO"));
					lstlocdetails.add(objLocDto);
				}
				rsGetLOCDetails.close();
				csGetLOCDetails.close();
				conn.commit();
				
				Collections.sort(lstlocdetails, new Comparator<ViewOrderDto>() {

					@Override
					public int compare(ViewOrderDto arg0, ViewOrderDto arg1) {
						return arg0.getServiceId().compareTo(arg1.getServiceId());
					}
				});
				
				if(lstlocdetails.size()>0)
				{
					
					String strCheckIsServiceInProgress = "SELECT M6_FX_PROGRESS_STATUS,IS_SERVICE_INACTIVE FROM ioe.TPOSERVICEMASTER WHERE SERVICEID=?";
					psCheckIsServiceInProgress = conn.prepareStatement(strCheckIsServiceInProgress);
					
					String query="UPDATE IOE.TPOSERVICEDETAILS SET LOCNO=?,LOCDATE=?,LOC_REC_DATE=?,BILLINGTRIGGERDATE=?,LOCDATAUPDATE=1 WHERE SERVICEPRODUCTID=? AND SERVICEID=? and BILLING_TRIGGER_STATUS!=20 and ISAUTOBILLING not in(1,2)";
					ps = conn.prepareStatement(query);
					
					
					String qCheckAcutoBilling = "SELECT count(*) FROM ioe.tauto_billing_line WHERE serviceid=? AND spid=? AND LOC_RECEIVE_STATUS='PENDING' and AUTOTYPE in ('ON_LOC_RECEIVE','LOC_LATER')";
					psCheckAutoBillingLine=conn.prepareStatement(qCheckAcutoBilling);
					
					String qFindNoNewChargesAutoBillingLine = 
							" SELECT au.spid FROM ioe.tauto_billing_line au "+
							" 	LEFT JOIN ioe.tcharges_info tci ON au.spid=tci.serviceproductid and tci.CREATEDIN_SERVICEID=au.serviceid"+
							" WHERE au.serviceid=? AND LOC_RECEIVE_STATUS='PENDING' and AUTOTYPE in ('ON_LOC_RECEIVE','LOC_LATER') AND tci.serviceproductid IS null";
					psFindNoNewChargesAutoBillingLine=conn.prepareStatement(qFindNoNewChargesAutoBillingLine);
					
					
					String query_AutoBilling = "UPDATE ioe.TAUTO_BILLING_LINE SET LOC_RECORD_ID=?, LOC_RECEIVE_STATUS='DONE' WHERE SERVICEID=? AND SPID=? and LOC_RECEIVE_STATUS='PENDING' and AUTOTYPE in ('ON_LOC_RECEIVE','LOC_LATER')";
					psAutoBilling = conn.prepareStatement(query_AutoBilling);
					
					String query2="UPDATE IOE.TM_TEMP_LOC_DATA SET STATUS=2,PROCESS_DATE=CURRENT TIMESTAMP WHERE LINENO=? AND SERVICEID=? AND ORDERNO=? AND STATUS=1 ";
					ps_updatetemp = conn.prepareStatement(query2);
					
					String qLogLocReceiveTime = "update ioe.tposervicemaster set LEPM_LOC_RECEIVED_DATE=current timestamp where serviceid=? and LEPM_LOC_RECEIVED_DATE is null";
					psLogLocReceiveTime=conn.prepareStatement(qLogLocReceiveTime);
					
					
					int j=0;
					boolean isBatchAutoBilling = false;
					HashSet<Long> locFoundLines = new HashSet<Long>();
					
					boolean isBatchPs = false;
					boolean isBatchLogLocReceiveTime = false;
					
					for( int i=0;i<lstlocdetails.size();i++)
					{
						 ViewOrderDto objdto=null;
					     objdto=lstlocdetails.get(i);
					     
					     boolean isServiceInProgress  = checkIsServiceInProgress(Long.parseLong(objdto.getServiceId()),psCheckIsServiceInProgress);
					     
					     if(isServiceInProgress){
					    	 ps.setString(1,objdto.getLocNo());
						     ps.setString(2,objdto.getLocDate());
						     ps.setString(3,objdto.getLocRecDate());
						     ps.setString(4,objdto.getBillingTriggerDate());
						     ps.setInt(5,Integer.parseInt(objdto.getServiceProductID()));
						     ps.setInt(6,Integer.parseInt(objdto.getServiceId()));
						     ps.addBatch();
						     isBatchPs=true;
						     
						     //Check if line on auto trigger loc pending 
						     	// if yes then add batch for update query
						    
						     int count = checkIfAutoBillingLine(psCheckAutoBillingLine,objdto);
						     if(count>0){
						    	 psAutoBilling.setLong(1, objdto.getSno());
							     psAutoBilling.setLong(2, Long.parseLong(objdto.getServiceId()));
							     psAutoBilling.setLong(3, Long.parseLong(objdto.getServiceProductID()));
							     psAutoBilling.addBatch();	
							     isBatchAutoBilling=true;
							     locFoundLines.add(Long.parseLong(objdto.getServiceProductID()));
						     }
						     
						     
						     if((i<lstlocdetails.size()-1 && !objdto.getServiceId().equals(lstlocdetails.get(i+1).getServiceId()))
						    		 ||(i==lstlocdetails.size()-1)){
						    	 //find lines which are autoTrigger with no charges 
						    	 //check if these are not in setOfLines, then prepare its update query based on sibling data
						    	 ArrayList<Long> NoNewChargeLines = getNoNewChargeLines(psFindNoNewChargesAutoBillingLine,objdto);
						    	 
						    	 for (Long spId : NoNewChargeLines) {
									if(!locFoundLines.contains(spId)){
										 psAutoBilling.setLong(1, objdto.getSno());
									     psAutoBilling.setLong(2, Long.parseLong(objdto.getServiceId()));
									     psAutoBilling.setLong(3, spId);
									     psAutoBilling.addBatch();	
									     isBatchAutoBilling=true;
									}
								}
						     }	 
						     
						     psLogLocReceiveTime.setLong(1, Long.parseLong(objdto.getServiceId()));
						     psLogLocReceiveTime.addBatch();
						     isBatchLogLocReceiveTime=true;
					     }
					     
					     
					     ps_updatetemp.setInt(1,Integer.parseInt(objdto.getServiceProductID()));
					     ps_updatetemp.setInt(2,Integer.parseInt(objdto.getServiceId()));
					     ps_updatetemp.setInt(3,Integer.parseInt(objdto.getOrderNo()));
					     ps_updatetemp.addBatch();
					     
					     j++;
				     }
					
					//for each service which have been autoblling
						//find autobilling line of that service which is also loc pending line with no charges
						//if found line not covered above then prepare its update query based on sibling data
					
					
					if(isBatchPs){
						int[] recordsAffected = ps.executeBatch();
						Utility.throwExceptionIfBatchFailed(recordsAffected);
					}
				    
				    if(isBatchAutoBilling){
				    	int[] recordsAffectedAutoBilling = psAutoBilling.executeBatch();
				    	Utility.throwExceptionIfBatchFailed(recordsAffectedAutoBilling);
				    }
				    
				    if(isBatchLogLocReceiveTime){
				    	int[] recordsLogLocReceiveTime = psLogLocReceiveTime.executeBatch();
				    	Utility.throwExceptionIfBatchFailed(recordsLogLocReceiveTime);
				    }
				    
				    int[] updateTemp = ps_updatetemp.executeBatch();
				    Utility.throwExceptionIfBatchFailed(updateTemp);
				    
				    System.out.println("LOC Data Updated Successfully");
					conn.commit();
					
					/*int[] updateTemp = ps_updatetemp.executeBatch();
					for(int index = 0 ; index < updateTemp.length; index++)
				    {
				    	if(updateTemp[index] == Statement.EXECUTE_FAILED )
				    	{
				    		throw new Exception();
				    	}
						else 
						{
							System.out.println("LOC Data Updated Successfully");
							conn.commit();
						}
				    }*/
					
					
					DbConnection.closePreparedStatement(psCheckAutoBillingLine);
					DbConnection.closePreparedStatement(psFindNoNewChargesAutoBillingLine);
					DbConnection.closePreparedStatement(psLogLocReceiveTime);					
					DbConnection.closePreparedStatement(ps);
					DbConnection.closePreparedStatement(psAutoBilling);
					DbConnection.closePreparedStatement(ps_updatetemp);
				}
			}while(lstlocdetails.size()>0);
			
 		}		
           
 catch (Exception e) 
 {
	   e.printStackTrace();
	   try {
		conn.rollback();
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
 }
 
 finally{
		try 
			{
				DbConnection.closePreparedStatement(ps_updatetemp);
				DbConnection.closePreparedStatement(ps);
				DbConnection.closePreparedStatement(psAutoBilling);
				DbConnection.freeConnection(conn);
			} 
			catch (Exception e) 
			{
			e.printStackTrace();
			}
		}	
}

private ArrayList<Long> getNoNewChargeLines(PreparedStatement psFindNoNewChargesAutoBillingLine, ViewOrderDto objdto) throws Exception{
	psFindNoNewChargesAutoBillingLine.setLong(1, Long.parseLong(objdto.getServiceId()));
	 ResultSet rsFindNoNewChargesAutoBillingLine = psFindNoNewChargesAutoBillingLine.executeQuery();
	 ArrayList<Long> lines = new ArrayList<Long>();
	 while(rsFindNoNewChargesAutoBillingLine.next()){
		 lines.add(rsFindNoNewChargesAutoBillingLine.getLong("SPID"));
	 }
	 DbConnection.closeResultset(rsFindNoNewChargesAutoBillingLine);
	 return lines;
}


private int checkIfAutoBillingLine(PreparedStatement psCheckAutoBillingLine,
		ViewOrderDto objdto) throws Exception{
	 psCheckAutoBillingLine.setLong(1, Long.parseLong(objdto.getServiceId()));
     psCheckAutoBillingLine.setLong(2, Long.parseLong(objdto.getServiceProductID()));
     ResultSet rsCheckAutoBillingLine=psCheckAutoBillingLine.executeQuery();
     rsCheckAutoBillingLine.next();
     int count = rsCheckAutoBillingLine.getInt(1);
     rsCheckAutoBillingLine.close();
     return count;
}


private boolean checkIsServiceInProgress(Long serviceId,
		PreparedStatement psCheckIsServiceInProgress) throws Exception{
	psCheckIsServiceInProgress.setLong(1, serviceId);
	ResultSet rs = psCheckIsServiceInProgress.executeQuery();
	if(rs.next()){
		String progressStatus=rs.getString("M6_FX_PROGRESS_STATUS");
		int inActiveStatus=rs.getInt("IS_SERVICE_INACTIVE");
		if("FX_BT_END".equals(progressStatus)||(progressStatus!=null && progressStatus.contains("CANCEL"))){
			return false;
		}else if(inActiveStatus!=0){
			return false;
		}else{
			return true;
		}
	}
	return false;
}


public static void InsertLEPMDatainIB2BTAble(Connection conn,Connection lepmcon) throws SQLException
	{
		PreparedStatement pstmt = null;
		CallableStatement csInsertData=null;
		ResultSet rset = null;
		try 
		{
						
			pstmt = lepmcon.prepareStatement(strgetLOCDatafromCRM);
			rset = pstmt.executeQuery();
			int errorCode = 0;
			int i = 0;
			ArrayList<ViewOrderDto> lstlsidetails = new ArrayList<ViewOrderDto>();
			while (rset.next()) 
			{
				ViewOrderDto objLocDataDto= new ViewOrderDto();						
				objLocDataDto.setLocNo(rset.getString("LOCNO"));
				objLocDataDto.setLocDate(rset.getString("STAGE"));//[002]
				objLocDataDto.setLocRecDate(rset.getString("LOC_REC_DATE"));
				objLocDataDto.setServiceId(rset.getString("SERVICENO"));
				objLocDataDto.setOrderNo(rset.getString("ORDERNO"));
				objLocDataDto.setServiceProductID(rset.getString("OrderLineNo"));
				objLocDataDto.setBillingTriggerDate(rset.getString("STAGE"));
				lstlsidetails.add(objLocDataDto);
				
			 }
				rset.close();
				pstmt.close();
				
				if(lstlsidetails.size()>0)
				{
					for( i=0;i<lstlsidetails.size();i++)
					{
						 ViewOrderDto objdto=null;
					     objdto=lstlsidetails.get(i);
					     csInsertData = conn.prepareCall(sqlInsert_LocData);
					     csInsertData.setString(1,objdto.getOrderNo());
					     csInsertData.setString(2,objdto.getServiceId());
					     csInsertData.setString(3,objdto.getServiceProductID());
					     csInsertData.setString(4,objdto.getLocNo());
					     csInsertData.setString(5,objdto.getLocDate());
					     csInsertData.setString(6,objdto.getLocRecDate());
					     csInsertData.setString(7,objdto.getBillingTriggerDate());
					     csInsertData.setInt(8, 0);
					     csInsertData.setInt(9, 0);
					     csInsertData.setString(10, "");
					     csInsertData.execute();			
						 errorCode=csInsertData.getInt(9);
						 if(errorCode==0)
						 {
							 System.out.println("Data inserted in IB2B Table");
							 conn.commit();
						 }
						 else
						 {
							 System.out.println("failed to insert data in ib2b table");
							 conn.rollback();
						 }
						 DbConnection.closeCallableStatement(csInsertData);
				     }
				}	
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			conn.rollback();
		}	
		finally 
		{
			try {
				DbConnection.closeCallableStatement(csInsertData);
				
				} catch (Exception e) {
					System.out.println("exeption due to : " + e.getMessage());
				}
		}
	}
//Added By Nagarjuna for OBValue 
public static String  strgetLOCChargeDatafromCRM="select ServiceNumber,OrderLineNo,LineItemNumber from ViewChargeIB2BDetail";
public static String sqlInsert_LocChargeData = "{CALL IOE.SP_INSERT_LOCCHARGE_LEPM_DATA(?,?,?,?,?,?)}";
public static void InsertLEPMChargeDatainIB2BTAble() throws SQLException
	{
	
	
		PreparedStatement pstmt = null;
		CallableStatement csInsertData=null;
		ResultSet rset = null;
		Connection conn=null;
		Connection lepmcon=DBConnectionRetriever.getSqLServerConnection();
		try 
		{
						
			pstmt = lepmcon.prepareStatement(strgetLOCChargeDatafromCRM);
			rset = pstmt.executeQuery();
			int errorCode = 0;
			int i = 0;
			ArrayList<ViewOrderDto> lstlsidetails = new ArrayList<ViewOrderDto>();
			while (rset.next()) 
			{
				ViewOrderDto objLocDataDto= new ViewOrderDto();						
				
				objLocDataDto.setServiceId(rset.getString("ServiceNumber"));
				objLocDataDto.setServiceProductID(rset.getString("OrderLineNo"));
				objLocDataDto.setCharge_info_id(rset.getInt("LineItemNumber"));
				
				lstlsidetails.add(objLocDataDto);
				
			 }
				rset.close();
				pstmt.close();
				
				if(lstlsidetails.size()>0)
				{
					conn = DbConnection.getConnectionObject();
					for( i=0;i<lstlsidetails.size();i++)
					{
						 ViewOrderDto objdto=null;
					     objdto=lstlsidetails.get(i);
					     csInsertData = conn.prepareCall(sqlInsert_LocChargeData);
					     csInsertData.setString(1,objdto.getServiceProductID());
					     csInsertData.setString(2,objdto.getServiceId());
					     csInsertData.setLong(3,objdto.getCharge_info_id());
					     
					     csInsertData.setInt(4, 0);
					     csInsertData.setInt(5, 0);
					     csInsertData.setString(6, "");
					     csInsertData.execute();			
						 errorCode=csInsertData.getInt(5);
						 if(errorCode==0)
						 {
							 System.out.println("Data inserted in IB2B Table");
							 conn.commit();
						 }
						 else
						 {
							 System.out.println("failed to insert data in ib2b table");
							 conn.rollback();
						 }
				     }
					pstmt=conn.prepareStatement(" UPDATE IOE.TTRACKINSERTDATE SET INSERTDATE = current date , UPDATEDATE= current date where TABLENAME='TCHARGES_INFO' ");
					pstmt.executeUpdate();
					pstmt.close();
				}	
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			conn.rollback();
		}	
		finally 
		{
			try {
				lepmcon.close();
				DbConnection.closeCallableStatement(csInsertData);
				conn.close();
				
				} catch (Exception e) {
					System.out.println("exeption due to : " + e.getMessage());
				}
		}
	}
//END ( Nagarjuna for OBValue) 
public static void main(String args[]) throws Exception
{	
	//Connection crmcon=DBConnectionRetriever.getSqLServerConnection();	
    //Connection	conn = DbConnection.getConnectionObject();
//	LepmLocDataUpdate l1=new LepmLocDataUpdate();
//	l1.updateLepmLocData();
		
}
}
