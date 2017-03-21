//Tag Name Resource Name  Date		CSR No			Description
//[001]	 Manisha Garg	3-Sep-12	00-05422		Created for Auto Disconnection Order
//[002]  Pankaj Thakur  29-June-15     Checking multiple entries  in NPD.TM_AUTO_DISCONNECT_ORDER_DATA table 
//[003] Gunjan Singla   4-Aug-15  20141219_R2_020936  Billing efficiency drop2
package com.ibm.ioes.schedular;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;


import com.ibm.ioes.ecrm.DBConnectionRetriever;
import com.ibm.ioes.forms.DisconnectOrderDto;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Utility;

public class AutoCreation_DisconnectOrder {

public static String spcreateAutoDisOrder  = "{CALL IOE.SP_CREATE_PERMANENT_DISCONNECTION_ORDER_AUTOMATION(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
public static String sqlGet_LSI_For_AutoDisOrder = "{CALL IOE.SP_GET_LSI_DETAILS_FOR_AUTO_DIS_ORDER()}";
public static String UpdateStatus = "{CALL IOE.SP_UPDATE_STATUS_AUTO_DISCONNECT_ORDER()}";
public static String strgetLSIDatafromCRM="SELECT SR_NO,SR_CREATION_DATE,SR_RAISED_DATE,REQUEST_REC_DATE_ER_DESK,DISCON_DATE_REQST_CUST,DUE_DISCONNECTION_DATE,REQUEST_FWD_DATE_CUST,PROCESSING_STATUS,CREATION_DATE,CREATED_BY,LSI_NO,SR_CREATED_BY FROM XXIBM.XXIBM_AES_SI_DIS_ORDER_DATA where trunc(to_date(CREATION_DATE))=trunc(sysdate) and  PROCESSING_STATUS=1";
public void RunAutoDisconnectOrderCreation() throws Exception 
	{
		Connection conn = null;
	    CallableStatement csGetLSIDetails =null;
	    CallableStatement csAutoCreationOrder =null;
	    ResultSet rsGetLSIDetails =null;
	    ArrayList<DisconnectOrderDto> list = new ArrayList<DisconnectOrderDto>();
	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(AppConstants.DATE_FORMAT_PROC);
		DBConnectionRetriever con = new DBConnectionRetriever();
		Connection crmcon = con.getCRMConnection();
	     try {
	    	 	conn = DbConnection.getConnectionObject();
				conn.setAutoCommit(false);
				try
				{
					AppConstants.IOES_LOGGER.info("AutoCreationDisOrderPlugin::inserting CRM Data in IB2B Table ");
					InsertCRMDatainIB2BTAble(conn,crmcon);
				}
				catch(Exception ex)
				{
					Utility.LOG(true,true,ex,null);
				}
				
				csGetLSIDetails= conn.prepareCall(sqlGet_LSI_For_AutoDisOrder);
				rsGetLSIDetails=csGetLSIDetails.executeQuery();
				while (rsGetLSIDetails.next()) 
				{
					DisconnectOrderDto objDisOrderDto= new DisconnectOrderDto();
					objDisOrderDto.setSrno(rsGetLSIDetails.getString("SRNO"));
					objDisOrderDto.setLsi(rsGetLSIDetails.getInt("LSINO"));
					if(!(rsGetLSIDetails.getString("SR_CREATION_DATE")==null || rsGetLSIDetails.getString("SR_CREATION_DATE")==""))
					{
					objDisOrderDto.setSrDate(simpleDateFormat.format(rsGetLSIDetails.getDate("SR_CREATION_DATE")));
					}
					if(!(rsGetLSIDetails.getString("DISCONNECTION_LOGIN_DATE")==null || rsGetLSIDetails.getString("DISCONNECTION_LOGIN_DATE")==""))
					{
					objDisOrderDto.setDisconnection_login_date(simpleDateFormat.format(rsGetLSIDetails.getDate("DISCONNECTION_LOGIN_DATE")));
					}
					if(!(rsGetLSIDetails.getString("DISCONNECTION_REC_DATE")==null || rsGetLSIDetails.getString("DISCONNECTION_REC_DATE")==""))
					{
					objDisOrderDto.setDisconnection_rec_date(simpleDateFormat.format(rsGetLSIDetails.getDate("DISCONNECTION_REC_DATE")));
					}
					if(!(rsGetLSIDetails.getString("DISCONNECTION_DATE_WITH_NP")==null || rsGetLSIDetails.getString("DISCONNECTION_DATE_WITH_NP")==""))
					{
					objDisOrderDto.setDisconnection_date_with_np(simpleDateFormat.format(rsGetLSIDetails.getDate("DISCONNECTION_DATE_WITH_NP")));
					}
					if(!(rsGetLSIDetails.getString("DISCONNECTION_DATE_BY_CUST")==null || rsGetLSIDetails.getString("DISCONNECTION_DATE_BY_CUST")==""))
					{
					objDisOrderDto.setDisconnection_date_bycust(simpleDateFormat.format(rsGetLSIDetails.getDate("DISCONNECTION_DATE_BY_CUST")));
					}
					if(!(rsGetLSIDetails.getString("INTIMATE_DATE")==null || rsGetLSIDetails.getString("INTIMATE_DATE")==""))
					{
					objDisOrderDto.setIntimatedate(simpleDateFormat.format(rsGetLSIDetails.getDate("INTIMATE_DATE")));
					}
					list.add(objDisOrderDto);
				}
			        
			     for(int i=0;i<list.size();i++)
			     {
			        try
			           {	   
				        	DisconnectOrderDto objdto=null;
			        		objdto=list.get(i);
			        		Utility.LOG("In AutoCreationDisOrderPlugin:: in method RunAutoDisconnectOrderCreation() LSI No: "+objdto.getLsi()+" & SR No: "+objdto.getSrno());
			        		csAutoCreationOrder= conn.prepareCall(spcreateAutoDisOrder);
			        		csAutoCreationOrder.setLong(1,(objdto.getLsi()));
			        		csAutoCreationOrder.setString(2,(objdto.getSrno()));
			        		csAutoCreationOrder.setString(3, (objdto.getSrDate()));
			        		csAutoCreationOrder.setString(4, (objdto.getDisconnection_login_date()));
			        		csAutoCreationOrder.setString(5, (objdto.getDisconnection_rec_date()));
			        		csAutoCreationOrder.setString(6, (objdto.getDisconnection_date_bycust()));
			        		csAutoCreationOrder.setString(7, (objdto.getDisconnection_date_with_np()));
			        		csAutoCreationOrder.setString(8, (objdto.getIntimatedate()));
			        		csAutoCreationOrder.setLong(9,0);
			        		csAutoCreationOrder.setString(10,"");
			        		csAutoCreationOrder.setString(11,"");
			        		csAutoCreationOrder.setString(12,"");
			        		csAutoCreationOrder.setLong(13,0);
			        		//[003] start
			        		csAutoCreationOrder.setString(14,"");
			        		//[003] end
			        		csAutoCreationOrder.execute();
			        		String msgCode =csAutoCreationOrder.getString(10);
			        		String msg =csAutoCreationOrder.getString(11);
			        		String status =csAutoCreationOrder.getString(12);
			        		String orderno=csAutoCreationOrder.getString(13);
			        		//[003] start
			        		String remarks =csAutoCreationOrder.getString(14);
			        		//[003] end
			        		
			        		if("-1".equals(msgCode))
			        		{
			        			AppConstants.IOES_LOGGER.info("AutoCreationDisOrderPlugin::errors in proc : IOE.SP_CREATE_PERMANENT_DISCONNECTION_ORDER_AUTOMATION="+msgCode+" for LSI["+objdto.getLsi()+"]");
			        			AppConstants.IOES_LOGGER.info("AutoCreationDisOrderPlugin::"+" Status-["+status+"] Error Message::"+msg);
							}
			        		else
			        		{
			        			AppConstants.IOES_LOGGER.info("AutoCreationDisOrderPlugin::["+orderno + "]Order Created Successfully for LSI["+objdto.getLsi()+"]");
			        		}
			        		AppConstants.IOES_LOGGER.info("AutoCreationDisOrderPlugin:: calling method UpdateDataInCRM ");
			        		UpdateDataInCRM(conn,objdto,msgCode,remarks,status,orderno,crmcon);
			        		conn.commit();			        		
				     }
			      catch (Exception e) 
				    {
			    	  	Utility.LOG(true,false, e,"from AutoCreationDisOrderPlugin:: in method RunAutoDisconnectOrderCreation() in block 1");
			        	conn.rollback();
				    }			
			  }
	       }	     
	     catch (Exception e) 
	     {
	    	 Utility.LOG(true,false, e,"from AutoCreationDisOrderPlugin:: in method RunAutoDisconnectOrderCreation() in block 2");
	     }
	finally{
			try 
				{
					//conn.setAutoCommit(true);
					if(conn!=null && conn.isClosed()==false && conn.getAutoCommit()==false){
						conn.rollback();
					}
					DbConnection.closePreparedStatement(csAutoCreationOrder);
					DbConnection.closeResultset(rsGetLSIDetails);
					DbConnection.closePreparedStatement(csGetLSIDetails);
					DbConnection.freeConnection(conn);					
				} 
				catch (Exception e) 
				{
					Utility.LOG(true,false, e,"from AutoCreationDisOrderPlugin:: in method RunAutoDisconnectOrderCreation() in block 3");
				}
			}	
}

 public  void UpdateDataInCRM(Connection conn,DisconnectOrderDto objdto,String msgcode,String remarks,String ORDER_STATUS,String Orderno,Connection crmcon)
	{
				PreparedStatement pstmt = null;
				String updateQuery="";
				try 
				{					
					if("-1".equals(msgcode))
					{
						updateQuery = "UPDATE XXIBM.XXIBM_AES_SI_DIS_ORDER_DATA  SET PROCESSING_STATUS=4, ORDER_STATUS='"+ORDER_STATUS+"',REMARKS='"+remarks+"' WHERE LSI_NO='"+objdto.getLsi()+"' and SR_NO='"+objdto.getSrno()+"'";
					}
					else
					{
						updateQuery = "UPDATE XXIBM.XXIBM_AES_SI_DIS_ORDER_DATA  SET ORDER_NO='"+Orderno+"', PROCESSING_STATUS=3,ORDER_STATUS='"+ORDER_STATUS+"',REMARKS='"+remarks+"',ORDER_CREATION_DATE=SYSDATE WHERE LSI_NO='"+objdto.getLsi()+"' and SR_NO='"+objdto.getSrno()+"'";
					}
					pstmt = crmcon.prepareStatement(updateQuery);
				    pstmt.executeUpdate(updateQuery);
				    AppConstants.IOES_LOGGER.info("AutoCreationDisOrderPlugin::Order Details updated in CRM Table for update query::"+updateQuery);
							
				}					
				catch (Exception e) 
				{
					Utility.LOG(true,false, e,"from AutoCreationDisOrderPlugin:: in method UpdateDataInCRM() in block 1");					
				} 
				finally 
				{
					try 
					{
						DbConnection.closePreparedStatement(pstmt);
					
					} 
					catch (Exception e) 
					{
						Utility.LOG(true,false, e,"from AutoCreationDisOrderPlugin:: in method UpdateDataInCRM() in block 2");
					}
				}
	     }
 
 
 public static void InsertCRMDatainIB2BTAble(Connection conn,Connection crmcon) throws SQLException
	{
		PreparedStatement pstmt = null;
		PreparedStatement ps=null;
		PreparedStatement psLSIofCRM=null;
		ResultSet rset = null;
		ResultSet rsetLSIofCRM=null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(AppConstants.DATE_FORMAT_PROC);
		//SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(AppConstants.DATE_FORMAT_mmddyyyy);
		try 
		{
						
			pstmt = crmcon.prepareStatement(strgetLSIDatafromCRM);
			rset = pstmt.executeQuery();
			int i = 0;
			ArrayList<DisconnectOrderDto> lstlsidetails = new ArrayList<DisconnectOrderDto>();
			while (rset.next()) 
			{
				DisconnectOrderDto objDisOrderDto= new DisconnectOrderDto();						
				objDisOrderDto.setSrno(rset.getString("SR_NO"));
				if(!(rset.getString("SR_CREATION_DATE")==null || rset.getString("SR_CREATION_DATE")==""))
				{
					objDisOrderDto.setCrm_srDate(rset.getTimestamp("SR_CREATION_DATE"));
				}
				if(!(rset.getString("SR_RAISED_DATE")==null || rset.getString("SR_RAISED_DATE")==""))
				{
				objDisOrderDto.setCrm_disconnection_login_date(rset.getTimestamp("SR_RAISED_DATE"));
				}
				if(!(rset.getString("REQUEST_REC_DATE_ER_DESK")==null || rset.getString("REQUEST_REC_DATE_ER_DESK")==""))
				{
				objDisOrderDto.setCrm_disconnection_rec_date(rset.getTimestamp("REQUEST_REC_DATE_ER_DESK"));
				}
				if(!(rset.getString("DISCON_DATE_REQST_CUST")==null || rset.getString("DISCON_DATE_REQST_CUST")==""))
				{
				objDisOrderDto.setCrm_disconnection_date_bycust(rset.getTimestamp("DISCON_DATE_REQST_CUST"));
				}
				if(!(rset.getString("DUE_DISCONNECTION_DATE")==null || rset.getString("DUE_DISCONNECTION_DATE")==""))
				{
				objDisOrderDto.setCrm_disconnection_date_with_np(rset.getTimestamp("DUE_DISCONNECTION_DATE"));
				}
				if(!(rset.getString("REQUEST_FWD_DATE_CUST")==null || rset.getString("REQUEST_FWD_DATE_CUST")==""))
				{
				objDisOrderDto.setCrm_intimatedate(rset.getTimestamp("REQUEST_FWD_DATE_CUST"));
				}
				objDisOrderDto.setProcessing_status(rset.getInt("PROCESSING_STATUS"));
				objDisOrderDto.setCrm_created_date(rset.getTimestamp("CREATION_DATE"));
				objDisOrderDto.setCreated_by(rset.getInt("CREATED_BY"));
				objDisOrderDto.setLsi(rset.getInt("LSI_NO"));
				//[003] start
				objDisOrderDto.setSr_raised_by(rset.getString("SR_CREATED_BY"));
				//[003] end
				lstlsidetails.add(objDisOrderDto);
			 }
			
			Iterator<DisconnectOrderDto> lsi_val= lstlsidetails.iterator();
			psLSIofCRM=conn.prepareStatement("Select LSINO,SRNO from NPD.TM_AUTO_DISCONNECT_ORDER_DATA where LSINO=? and SRNO=?");
			
			while (lsi_val.hasNext()) {
				try {
					DisconnectOrderDto dto=(DisconnectOrderDto)lsi_val.next(); 
					psLSIofCRM.setString(1,String.valueOf(dto.getLsi()));
					psLSIofCRM.setString(2,dto.getSrno());
					rsetLSIofCRM=psLSIofCRM.executeQuery();
					int count=0;
					while(rsetLSIofCRM.next()){
						count++;	
						break;
					}
					if(count>0){
						lsi_val.remove();
					}
				}
				finally{
					DbConnection.closeResultset(rsetLSIofCRM);
				}
			}
		
			if(lstlsidetails.size()>0)
				{
					String query="INSERT INTO NPD.TM_AUTO_DISCONNECT_ORDER_DATA" +
							"(LSINO,SRNO,SR_CREATION_DATE,DISCONNECTION_LOGIN_DATE,DISCONNECTION_REC_DATE,DISCONNECTION_DATE_BY_CUST,DISCONNECTION_DATE_WITH_NP,INTIMATE_DATE,PROCESSING_STATUS,LSI_CREATED_DATE,CREATED_BY,SR_RAISED_BY) " +
							"values(?,?,?,?,?,?,?,?,?,?,?,?)";
					ps = conn.prepareStatement(query);
					int j=0;
					for( i=0;i<lstlsidetails.size();i++)
					{
					
					 DisconnectOrderDto objdto=null;
				     objdto=lstlsidetails.get(i);
				     ps.setString(1, new Integer(objdto.getLsi()).toString());
				     ps.setString(2,(objdto.getSrno()));
				     if(objdto.getCrm_srDate()==null || objdto.getCrm_srDate().toString()=="")
				     {
				    	 ps.setNull(3, java.sql.Types.DATE);
				    	 
				     }
				     else
				     { 
				     ps.setDate(3,new java.sql.Date((objdto.getCrm_srDate()).getTime()));
				     }
				     if(objdto.getCrm_disconnection_login_date()==null || objdto.getCrm_disconnection_login_date().toString()=="")
				     {
				    	 ps.setNull(4, java.sql.Types.DATE);
				    	 
				     }
				     else
				     {
				     ps.setDate(4,new java.sql.Date((objdto.getCrm_disconnection_login_date()).getTime()));
				     }
				     if(objdto.getCrm_disconnection_rec_date()==null || objdto.getCrm_disconnection_rec_date().toString()=="")
				     {
				    	 ps.setNull(5, java.sql.Types.DATE);
				    	 
				     }
				     else
				     {
				     ps.setDate(5,new java.sql.Date((objdto.getCrm_disconnection_rec_date()).getTime()));
				     }
				     if(objdto.getCrm_disconnection_date_bycust()==null || objdto.getCrm_disconnection_date_bycust().toString()=="")
				     {
				    	 ps.setNull(6, java.sql.Types.DATE);
				    	 
				     }
				     else
				     {
				     ps.setDate(6,new java.sql.Date((objdto.getCrm_disconnection_date_bycust()).getTime()));
				     }
				     if(objdto.getCrm_disconnection_date_with_np()==null || objdto.getCrm_disconnection_date_with_np().toString()=="")
				     {
				    	 ps.setNull(7, java.sql.Types.DATE);
				    	 
				     }
				     else
				     {
				     ps.setDate(7,new java.sql.Date((objdto.getCrm_disconnection_date_with_np()).getTime()));
				     }
				     if(objdto.getCrm_intimatedate()==null || objdto.getCrm_intimatedate().toString()=="")
				     {
				    	 ps.setNull(8, java.sql.Types.DATE);
				    	 
				     }
				     else
				     {
				     ps.setDate(8,new java.sql.Date((objdto.getCrm_intimatedate()).getTime()));
				     }
				     ps.setLong(9,objdto.getProcessing_status());
				     ps.setDate(10,new java.sql.Date((objdto.getCrm_created_date()).getTime()));
				     ps.setInt(11,objdto.getCreated_by());
				     ps.setString(12,objdto.getSr_raised_by());
				     ps.addBatch();
				     j++;
				     }
					int[] updateCounts = ps.executeBatch();
					if (updateCounts.length == j) 
					{
						conn.commit();
						 AppConstants.IOES_LOGGER.info("AutoCreationDisOrderPlugin::All CRM Data inserted in IB2B Table");
					} 
					else 
					{
						 AppConstants.IOES_LOGGER.info("AutoCreationDisOrderPlugin::failed to insert please retry in method InsertCRMDatainIB2BTAble()");
						conn.rollback();
					}
					
			 }	
		}
		catch (Exception e) 
		{
			Utility.LOG(true,false, e,"from AutoCreationDisOrderPlugin:: in method InsertCRMDatainIB2BTAble() in block 1");
			conn.rollback();
		}	
			finally {
				try {
						DbConnection.closePreparedStatement(ps);
						DbConnection.closePreparedStatement(psLSIofCRM);
						DbConnection.closeResultset(rset);
						DbConnection.closePreparedStatement(pstmt);
										
					} catch (Exception e) {
						Utility.LOG(true,false, e,"from AutoCreationDisOrderPlugin:: in method InsertCRMDatainIB2BTAble() in block 2");
					}
				}
	}
 
 
 public void updateStatus(Connection conn)
 {
	 CallableStatement cupdatestatus =null;
	 
	 try
	 {
		 cupdatestatus= conn.prepareCall(UpdateStatus);
		 cupdatestatus.executeUpdate();

	 }
	 
	 catch(Exception e)
	 {
		 Utility.LOG(true,false, e,"from AutoCreationDisOrderPlugin:: in method updateStatus() in block 1");
	 }
	 
	 
 }
 

}
