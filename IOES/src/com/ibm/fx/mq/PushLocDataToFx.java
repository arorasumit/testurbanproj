package com.ibm.fx.mq;

import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ibm.fx.dto.ExtendedData;
import com.ibm.fx.dto.FxExternalIdDto;
import com.ibm.fx.dto.LOCDATADTO;
import com.ibm.fx.dto.ServiceDTO;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Utility;

public class PushLocDataToFx {
	
	
	private String FXAUTOBILLINGStatusUpdate="{call IOE.SP_UPDATE_AUTO_BILLING_STATUS()}";
	
	public static String sqlfetch_locdata="{call IOE.SP_GET_LOC_DATA_TO_PUSH_FX()}";
	
	public static String sqlfetch_NrcData="{call IOE.SP_GET_NRC_DATA_TO_PUSH_FX(?)}";
	
	public static String sqlfetch_RcData="{call IOE.SP_GET_RC_DATA_TO_PUSH_FX(?)}";
	
	public static String sqlfetch_ServiceData="{call IOE.SP_GET_SERVICE_DATA_TO_PUSH_FX(?)}";
	
	public static String sql_uopdate_nrc_locdata="{call IOE.SP_UPDATE_NRC_LOC_DATA_LOCALLY(?,?,?)}";
	public static String sql_uopdate_rc_locdata="{call IOE.SP_UPDATE_RC_LOC_DATA_LOCALLY(?,?,?)}";
	public static String sql_uopdate_servicelocdata="{call IOE.SP_UPDATE_SERVICE_LOC_DATA_LOCALLY(?,?,?,?)}";
	public static String sql_uopdate_success_FAILURE_status="{call IOE.SP_UPDATE_SUCCESS__FAILURE_STATUS(?,?,?)}";
	public static String sql_uopdate_nrc_success_FAILURE_status="{call IOE.SP_UPDATE_NRC_SUCCESS__FAILURE_STATUS(?,?)}";
	public static String sql_uopdate_rc_success_FAILURE_status="{call IOE.SP_UPDATE_RC_SUCCESS__FAILURE_STATUS(?,?)}";
	public static String sql_uopdate_service_success_FAILURE_status="{call IOE.SP_UPDATE_SERVICE_SUCCESS__FAILURE_STATUS (?,?)}";
	
		
		public void PushLocDataIntoFx()
		{
			try
			{
				IOESKenanManager API= null ;
				Utility.LOG(true, true, " IN  pushLocDataToFX()");
				Connection connection = null;	
				try
				{			
					API=new IOESKenanManager();
					API.loginKenan();
					//ApplicationLocks.lock1.lock();
					connection = DbConnection.getConnectionObject();
					
					try
					{
						statusUpdateforAutoBillingFailed(connection);
					}
					catch(Exception ex)
					{
						Utility.LOG(true,true,ex,null);
					}				
					
					connection.setAutoCommit(false);
					LOCDATADTO loclist = null;
					do
					{
						//fetch one service
						try
						{
							loclist = fetchNextLocData(connection);
							connection.commit();
						}
						catch(Exception ex)
						{
							Utility.LOG(true,true,ex,null);
							connection.commit();
							//logExceptionForService(service,ex);//--Later
							continue;
						}
						if(loclist!=null) 
						{ 
						//push to FX
							String logIdentifier="LOCDATA SCHEDULAR ERRROR , SERVICEPRODUCTID:"+loclist.getServiceProductId()+"\n";
//							
							updateLocdata(connection,loclist,API,logIdentifier);
							//save results	
							try
							{	saveSuccessFailureStatus(connection,loclist,logIdentifier);//COMMIT IS DONE IN THIS FUNCTION ALSO
								//connection.commit();
							}
							catch(Exception ex)
							{
								
								
								connection.rollback();
							}
						}
					}while(loclist!=null);

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
					try{
					     API.close();
					}catch(Exception ex)
					{
						Utility.LOG(true,true,ex,null);
						
					}
					//ApplicationLocks.lock1.unlock();
					
				}
			}
			catch(Throwable th)
			{
				Utility.LOG(true,true,new Exception(th),null);
			}

			
		}
		
		
		public void statusUpdateforAutoBillingFailed(Connection connection)
		{
			PreparedStatement pstmt=null;
			 try {
			pstmt=connection.prepareStatement(FXAUTOBILLINGStatusUpdate);
				pstmt.execute();
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
		
		LOCDATADTO fetchNextLocData(Connection connection) throws Exception
		{
			LOCDATADTO LDto=null;
			LOCDATADTO NrcLDto=null;
			LOCDATADTO RcLDto=null;
			LOCDATADTO ServiceLDto=null;
			ResultSet rs_spid=null;
			CallableStatement cstmt_SPID = null;
			CallableStatement cstmt_NrcData = null;
			CallableStatement cstmt_RcData = null;
			CallableStatement cstmt_ServiceData = null;
		    ArrayList<LOCDATADTO>  alSelectNrcData=new ArrayList<LOCDATADTO>();
		    ArrayList<LOCDATADTO>  alSelectRcData=new ArrayList<LOCDATADTO>();
		    ArrayList<LOCDATADTO>  alSelectServiceData=new ArrayList<LOCDATADTO>();
			LOCDATADTO objldto=new LOCDATADTO();
			ResultSet rs_Rc_data=null;
			ResultSet rs_nrc_data=null;
			ResultSet rs_Service_data=null;
			
			try
			{
				cstmt_SPID=connection.prepareCall(sqlfetch_locdata);
				rs_spid=cstmt_SPID.executeQuery();
				
				if(rs_spid.next())
				{
					LDto=new LOCDATADTO();
					LDto.setServiceProductId(rs_spid.getLong("SPID"));
					LDto.setLocno(rs_spid.getString("LOCNO"));
					LDto.setLocdate(rs_spid.getString("LOCDATE"));
					LDto.setServiceType(rs_spid.getString("SERVICETYPE"));
					LDto.setOrderNo(rs_spid.getLong("ORDERNO"));
				}
				
				else
				{
					
					return null;
				}
				
				// to fetch NRC DATA

						cstmt_NrcData=connection.prepareCall(sqlfetch_NrcData);
						cstmt_NrcData.setLong(1,LDto.getServiceProductId());
						rs_nrc_data=cstmt_NrcData.executeQuery();	
							
						while(rs_nrc_data.next()){
							
							NrcLDto=new LOCDATADTO();
							NrcLDto.setNrcid(rs_nrc_data.getLong("ID"));
							NrcLDto.setNrc_viewid(rs_nrc_data.getString("VIEWID"));
							NrcLDto.setNrc_fx_schedular_create_status(rs_nrc_data.getInt("FX_SCHEDULAR_CREATE_STATUS"));
							alSelectNrcData.add(NrcLDto);
							}
						
						  LDto.setAlselectNrclocdata(alSelectNrcData);
						
//				 to fetch RC DATA

						cstmt_RcData=connection.prepareCall(sqlfetch_RcData);
						cstmt_RcData.setLong(1,LDto.getServiceProductId());
						rs_Rc_data=cstmt_RcData.executeQuery();	
					
						while(rs_Rc_data.next()){
							
							RcLDto=new LOCDATADTO();
							RcLDto.setRcid(rs_Rc_data.getLong("ID"));
							RcLDto.setRc_viewid(rs_Rc_data.getString("VIEWID"));
							RcLDto.setRc_fx_schedular_create_status(rs_Rc_data.getInt("FX_SCHEDULAR_CREATE_STATUS"));
							alSelectRcData.add(RcLDto);
							}
				
								LDto.setAlselectRclocdata(alSelectRcData);
				
//				 to fetch SERVICE DATA

								cstmt_ServiceData=connection.prepareCall(sqlfetch_ServiceData);
								cstmt_ServiceData.setLong(1,LDto.getServiceProductId());
								rs_Service_data=cstmt_ServiceData.executeQuery();	
								
								while(rs_Service_data.next()){
								
									ServiceLDto=new LOCDATADTO();
									ServiceLDto.setServiceid(rs_Service_data.getLong("ID"));
									ServiceLDto.setService_viewid(rs_Service_data.getString("VIEW_ID"));
									ServiceLDto.setService_fx_schedular_create_status(rs_Service_data.getInt("FX_SCHEDULAR_CREATE_STATUS"));
									ServiceLDto.setSubcno(rs_Service_data.getInt("SUBSCRNO"));
									ServiceLDto.setServiceactdate(rs_Service_data.getDate(("SERVICEACTIVEDT")));
									ServiceLDto.setService_parentid(rs_Service_data.getString("PARENT_ID"));
									alSelectServiceData.add(ServiceLDto);
								}
							
							LDto.setAlselectServicelocdata(alSelectServiceData);
			}
			
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();}
			finally
			{
				if(rs_Rc_data!=null)DbConnection.closeResultset(rs_Rc_data);
				if(rs_nrc_data!=null)DbConnection.closeResultset(rs_nrc_data);
				if(rs_Service_data!=null)DbConnection.closeResultset(rs_Service_data);
				if(cstmt_SPID!=null)DbConnection.closeCallableStatement(cstmt_SPID);
				if(cstmt_NrcData!=null)DbConnection.closeCallableStatement(cstmt_NrcData);
				if(cstmt_RcData!=null)DbConnection.closeCallableStatement(cstmt_RcData);
				if(cstmt_ServiceData!=null)DbConnection.closeCallableStatement(cstmt_ServiceData);
				
			}
			return LDto;
		}
		
		void updateLocdata(Connection connection,LOCDATADTO objdto,IOESKenanManager API,String logIdentifier)
		{
			ArrayList<LOCDATADTO> Arr_Nrc=new ArrayList<LOCDATADTO>();
			ArrayList<LOCDATADTO> Arr_Rc=new ArrayList<LOCDATADTO>();
			ArrayList<LOCDATADTO> Arr_Service=new ArrayList<LOCDATADTO>();
			LOCDATADTO Nrcdto=new LOCDATADTO();
			LOCDATADTO Rcdto=new LOCDATADTO();
			LOCDATADTO Servicedto=new LOCDATADTO();
			int isfailureflag=0;
			Arr_Nrc=objdto.getAlselectNrclocdata();
			Arr_Rc=objdto.getAlselectRclocdata();
			Arr_Service=objdto.getAlselectServicelocdata();
			
			// to update Nrc Data
			
			if(Arr_Nrc.size()>0){
			for(int i=0;i<Arr_Nrc.size();i++){
				
				Nrcdto=Arr_Nrc.get(i);
				
				if(Nrcdto.getNrc_fx_schedular_create_status()==3){
					
					API.updateNRCLocData(Nrcdto,objdto,logIdentifier); 				
					
				}
				
				else{
					
					locallyUpdateNrcLocdata(connection,Nrcdto,objdto,logIdentifier);
				
				}//else
				
				
				if("failure".equals(Nrcdto.getNrc_Status())){
					
					isfailureflag=1;
				}
					
					
										}//for
				
			}//if
			
			// to update Rc Data
			
			
			if(Arr_Rc.size()>0){
				for(int i=0;i<Arr_Rc.size();i++){
					
					Rcdto=Arr_Rc.get(i);
					
					if(Rcdto.getRc_fx_schedular_create_status()==3){
						
						API.updateRCLocData(Rcdto,objdto,logIdentifier); 				
						
					}
					
					else{
						
						locallyUpdateRcLocdata(connection,Rcdto,objdto,logIdentifier);
					}//else
					
					
					if("failure".equals(Rcdto.getRc_Status())){
						
						isfailureflag=1;
					}
						
						
											}//for
					
				}//if
			
			// TO Update Service Data
			
			if(Arr_Service.size()>0){
				for(int i=0;i<Arr_Service.size();i++){
					
					Servicedto=Arr_Service.get(i);
					
					if(Servicedto.getService_fx_schedular_create_status()==3){
						
						API.updateServiceLocData(Servicedto,objdto,logIdentifier); 				
						
					}
					
					else{
						
						locallyUpdateServiceLocdata(connection,Servicedto,objdto,logIdentifier);
					
					}//else
					
					
					if("failure".equals(Servicedto.getService_Status())){
						
						isfailureflag=1;
					}
						
											}//for
					
				}//if
			
			
			if(isfailureflag==1){
				
				objdto.setSucfailstatus("failure");
				
			}
			
			else{
				
				objdto.setSucfailstatus("success");
				
			}
	}
		
		
		// locally update nrc data
		
		
		void locallyUpdateNrcLocdata(Connection connection,LOCDATADTO Nrcdto,LOCDATADTO objdto,String logIdentifier){
			CallableStatement csmt_updatedata = null;
			
			try{
				
				
				csmt_updatedata=connection.prepareCall(sql_uopdate_nrc_locdata);
				csmt_updatedata.setLong(1,Nrcdto.getNrcid());
				csmt_updatedata.setString(2,objdto.getLocno());
				csmt_updatedata.setString(3,objdto.getLocdate());
			    csmt_updatedata.execute();
			    Nrcdto.setNrc_Status("success");
			    connection.commit();
				
			}
			
			
			 catch(Exception e)
			 {
				 Nrcdto.setNrc_Status("failure");
					try {
						connection.rollback();
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						String message=logIdentifier+"error in Locally Update Data";
						objdto.setExceptionMessage(message);
						e1.printStackTrace();
				}
					finally
					{
					if(csmt_updatedata!=null) DbConnection.closeCallableStatement(csmt_updatedata);	
					}
			 }
		}
			

		// locally update rc data
		
		void locallyUpdateRcLocdata(Connection connection,LOCDATADTO Rcdto,LOCDATADTO objdto,String logIdentifier){
			
				
			CallableStatement csmt_updatedata = null;
			
			
			try{
				
				csmt_updatedata=connection.prepareCall(sql_uopdate_rc_locdata);
				csmt_updatedata.setLong(1,Rcdto.getRcid());
				csmt_updatedata.setString(2,objdto.getLocno());
				csmt_updatedata.setString(3,objdto.getLocdate());
			    csmt_updatedata.execute();
			    Rcdto.setRc_Status("success");
			    connection.commit();
				
			}
			 catch(Exception e)
			 {
				 Rcdto.setRc_Status("failure");
					try {
						connection.rollback();
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						String message=logIdentifier+"error in Locally Update Data";
						objdto.setExceptionMessage(message);
						e1.printStackTrace();
				}
					finally
					{
						if(csmt_updatedata!=null)DbConnection.closeCallableStatement(csmt_updatedata);
					}
				 
			 }
		}
		
		// locally update service data
		
		void locallyUpdateServiceLocdata(Connection connection,LOCDATADTO Servicedto,LOCDATADTO objdto,String logIdentifier){
			
			
			CallableStatement csmt_updatedata = null;
			try{
				csmt_updatedata=connection.prepareCall(sql_uopdate_servicelocdata);
				csmt_updatedata.setLong(1,Servicedto.getServiceid());
				csmt_updatedata.setString(2,objdto.getLocno());
				csmt_updatedata.setString(3,objdto.getLocdate());
				csmt_updatedata.setString(4,Servicedto.getService_parentid());
			    csmt_updatedata.execute();
			    Servicedto.setService_Status("success");
			    connection.commit();
				}
			 catch(Exception e)
			 {
				 Servicedto.setService_Status("failure");
					try {
						connection.rollback();
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						String message=logIdentifier+"error in Locally Update Data";
						objdto.setExceptionMessage(message);
						e1.printStackTrace();
				}
					finally
					{
						if(csmt_updatedata!=null)DbConnection.closeCallableStatement(csmt_updatedata);
					}
			 }
		}
		
		void saveSuccessFailureStatus(Connection connection,LOCDATADTO objdto,String logIdentifier){
			
			
				
			CallableStatement csmt_updatesuccessfailurestatus = null;
			CallableStatement csmt_update_Nrc_successfailurestatus = null;
			CallableStatement csmt_update_Rc_successfailurestatus = null;
			CallableStatement csmt_update_Service_successfailurestatus = null;
			ArrayList<LOCDATADTO> ArrNrc=new ArrayList<LOCDATADTO>();
			ArrayList<LOCDATADTO> ArrRc=new ArrayList<LOCDATADTO>();
			ArrayList<LOCDATADTO> ArrService=new ArrayList<LOCDATADTO>();
			LOCDATADTO nrcdto=new LOCDATADTO();
			LOCDATADTO rcdto=new LOCDATADTO();
			LOCDATADTO servicedto=new LOCDATADTO();
			ArrNrc=objdto.getAlselectNrclocdata();
			ArrRc=objdto.getAlselectRclocdata();
			ArrService=objdto.getAlselectServicelocdata();
				try{
				
							
							csmt_updatesuccessfailurestatus=connection.prepareCall(sql_uopdate_success_FAILURE_status);
							csmt_updatesuccessfailurestatus.setLong(1,objdto.getServiceProductId());
							csmt_updatesuccessfailurestatus.setString(2,objdto.getSucfailstatus());
							csmt_updatesuccessfailurestatus.setLong(3,objdto.getOrderNo());
							csmt_updatesuccessfailurestatus.execute();
							connection.commit();
							
				   }
				catch(Exception e){
					
					
					try {
						connection.rollback();
						
					} catch (SQLException e1) {
						String message=logIdentifier+"to Update Success Failure Status";
						objdto.setExceptionMessage(message);
						e1.printStackTrace();
					
				}
				
			}
				finally
				{
					if(csmt_updatesuccessfailurestatus!=null)DbConnection.closeCallableStatement(csmt_updatesuccessfailurestatus);
				}
				 // update nrc status
				
				if(ArrNrc.size()>0){
					for(int i=0;i<ArrNrc.size();i++){
						
						nrcdto=ArrNrc.get(i);
				try{		
					csmt_update_Nrc_successfailurestatus=connection.prepareCall(sql_uopdate_nrc_success_FAILURE_status);
					csmt_update_Nrc_successfailurestatus.setLong(1,nrcdto.getNrcid());
					csmt_update_Nrc_successfailurestatus.setString(2,nrcdto.getNrc_Status());
					csmt_update_Nrc_successfailurestatus.execute();	
					connection.commit();
			
					}
				catch(Exception e){
					
					
					try {
						connection.rollback();
						
					} catch (SQLException e1) {
						String message=logIdentifier+"to Update Success Failure Status";
						objdto.setExceptionMessage(message);
		                  e1.printStackTrace();
					
				}
			}
				finally
				{
					if(csmt_update_Nrc_successfailurestatus!=null)DbConnection.closeCallableStatement(csmt_update_Nrc_successfailurestatus);
				}
					
	   }//for	
			
			
			
   }//if
				//update rc status
				
				if(ArrRc.size()>0){
					for(int i=0;i<ArrRc.size();i++){
						
						rcdto=ArrRc.get(i);
				try{		
					csmt_update_Rc_successfailurestatus=connection.prepareCall(sql_uopdate_rc_success_FAILURE_status);
					csmt_update_Rc_successfailurestatus.setLong(1,rcdto.getRcid());
					csmt_update_Rc_successfailurestatus.setString(2,rcdto.getRc_Status());
					csmt_update_Rc_successfailurestatus.execute();	
					connection.commit();
			
					}
				
				
				catch(Exception e){
					
					
					try {
						connection.rollback();
						
					} catch (SQLException e1) {
						String message=logIdentifier+"to Update Success Failure Status";
						objdto.setExceptionMessage(message);
		                  e1.printStackTrace();
				}
			}
				finally
				{
					if(csmt_update_Rc_successfailurestatus!=null)DbConnection.closeCallableStatement(csmt_update_Rc_successfailurestatus);
				}
					
	   }//for	
			
			
			} // end if
   
				
			// to update service status
				
				
				if(ArrService.size()>0){
					for(int i=0;i<ArrService.size();i++){
						
						servicedto=ArrService.get(i);
				try{		
					csmt_update_Service_successfailurestatus=connection.prepareCall(sql_uopdate_service_success_FAILURE_status);
					csmt_update_Service_successfailurestatus.setLong(1,servicedto.getServiceid());
					csmt_update_Service_successfailurestatus.setString(2,servicedto.getService_Status());
					csmt_update_Service_successfailurestatus.execute();	
					connection.commit();
			
					}
				catch(Exception e){
					
					
					try {
						connection.rollback();
						
					} catch (SQLException e1) {
						String message=logIdentifier+"to Update Success Failure Status";
						objdto.setExceptionMessage(message);
		                  e1.printStackTrace();
				}
				
			}
				finally
				{
					if(csmt_update_Service_successfailurestatus!=null)DbConnection.closeCallableStatement(csmt_update_Service_successfailurestatus);
				}
					
	   }//for	
   }//if
		
}

}	
		