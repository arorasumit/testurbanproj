//Tag Name Resource Name  Date		CSR No			Description
//[001]	 Manisha Garg	3-Sep-12	00-05422		Created for Auto Disconnection Order
package com.ibm.ioes.schedular;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import com.ibm.ioes.ecrm.DBConnectionRetriever;
import com.ibm.ioes.forms.DisconnectOrderDto;
import com.ibm.ioes.utilities.DbConnection;

public class CancelAutoDisOrder {
public static String SP_GET_CANCEL_AUTO_DIS_ORDER = "{CALL IOE.SP_GET_CANCEL_AUTO_DIS_ORDER()}";

public void UpdateCancelOrderDatainCRM() throws Exception 
		{
			Connection conn = null;
		    CallableStatement csGetCancelOrder =null;
		    ResultSet rsGetCancelOrder =null;
		    ArrayList<DisconnectOrderDto> list = new ArrayList<DisconnectOrderDto>();
		     try {
		    	 	conn = DbConnection.getConnectionObject();
					conn.setAutoCommit(false);
					csGetCancelOrder= conn.prepareCall(SP_GET_CANCEL_AUTO_DIS_ORDER);
					rsGetCancelOrder=csGetCancelOrder.executeQuery();
					while (rsGetCancelOrder.next()) 
					{
						DisconnectOrderDto objDisOrderDto= new DisconnectOrderDto();
						objDisOrderDto.setOrderNumber(rsGetCancelOrder.getInt("ORDERNO"));
						objDisOrderDto.setCancel_flag(rsGetCancelOrder.getInt("CANCEL_FLAG"));
						objDisOrderDto.setCancel_reason(rsGetCancelOrder.getString("CANCEL_REASON"));
						objDisOrderDto.setLast_updated_by(rsGetCancelOrder.getInt("LAST_UPDATED_BY"));
						objDisOrderDto.setLast_updated_date(rsGetCancelOrder.getString("LAST_UPDATE_DATE"));
						list.add(objDisOrderDto);
					}
				        
				     for(int i=0;i<list.size();i++)
				     {
				        try
				           {	   
					        	DisconnectOrderDto objdto=null;
				        		objdto=list.get(i);
				        		UpdateDataInCRM(conn,objdto);
							    conn.commit();
					     }
				      catch (Exception e) 
					    {
				        	e.printStackTrace();
				        	conn.rollback();
					    }			
				  }
		       }	     
		     catch (Exception e) 
		     {
			   e.printStackTrace();
		     }
		finally{
				try 
					{
						DbConnection.closeResultset(rsGetCancelOrder);
						DbConnection.closePreparedStatement(csGetCancelOrder);
						if(conn!=null && conn.isClosed()==false && conn.getAutoCommit()==false){
							conn.rollback();
						}
						DbConnection.freeConnection(conn);
						
					} 
					catch (Exception e) 
					{
					e.printStackTrace();
					}
				}	
	}


public void UpdateDataInCRM(Connection conn,DisconnectOrderDto objdto)
{
			PreparedStatement pstmt = null;
			DBConnectionRetriever con = new DBConnectionRetriever();
			Connection crmcon = con.getCRMConnection();
			String updateQuery="";
			try 
			{
				System.err.println("updating CRM Table");
				updateQuery = "UPDATE XXIBM_AES_SI_DIS_ORDER_DATA SET PROCESSING_STATUS=5,CANCEL_FLAG="+objdto.getCancel_flag()+",CANCEL_REASON='"+objdto.getCancel_reason()+"',LAST_UPDATED_BY="+objdto.getLast_updated_by()+",LAST_UPDATED_DATE=SYSDATE WHERE ORDER_NO='"+objdto.getOrderNumber()+"' and PROCESSING_STATUS=3";
				pstmt = crmcon.prepareStatement(updateQuery);
			    pstmt.executeUpdate(updateQuery);
				System.err.println("Order Details updated in CRM Table for Order No   :  "  + objdto.getOrderNumber());
						
			}					
			catch (Exception e) 
			{
				System.out.println("Exception occured to update Details"
						+ e.getStackTrace());
				e.printStackTrace();
			} 
			finally 
			{
				try 
				{
					try {pstmt.close();}catch(Exception ex){ex.printStackTrace();}
					try {crmcon.close();}catch(Exception ex){ex.printStackTrace();}
					
					
				} 
				catch (Exception e) 
				{
					System.out.println("exeption due to : " + e.getMessage());
				}
			}
     }

}