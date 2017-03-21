package com.ibm.ioes.schedular;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ibm.fx.dto.ExtendedData;
import com.ibm.fx.dto.FxExternalIdDto;
import com.ibm.fx.dto.ServiceDTO;
import com.ibm.fx.mq.IOESKenanManager;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Utility;

public class ServiceUpdateSchedular {
	
	private String sqlFX_NO_SCH_GETSERVICE_UPDATE_DATA = "call IOE.FX_NO_SCH_GETSERVICE_UPDATE_DATA(?)";
	
	private String sqlFX_SCHEDULER_SUCCESS="call IOE.FX_SCHEDULER_SUCCESS_FOR_SERVICEUPDATE(?,?)";
	private String sqlFX_SCHEDULER_FAILURE="call IOE.FX_SCHEDULER_FAILURE_FOR_SERIVCEUPDATE(?,?,?,?)";
	private String sqlFX_UPDATE_FAILED_TRANSACTION = "UPDATE IOE.TFX_SERVICEUPDATE SET FX_SCHEDULAR_UPDATE_STATUS=1  WHERE FX_SCHEDULAR_UPDATE_STATUS IN (4) ";

	/**	
	*	This function pushes today's or backdated Services to FX for New Order
	*/
	public void pushUpdatedServicesToFX(long orderno)
	{
		try
		{
			IOESKenanManager API= null ;
			Utility.LOG(true, true, " IN  pushServicesUpdateToFX()");
			Connection connection = null;	
			try
			{			
				API=new IOESKenanManager();
				API.loginKenan();
				
				connection = DbConnection.getConnectionObject();
				
				try
				{
					statusUpdateForFailedTransaction(connection);
				}
				catch(Exception ex)
				{
					Utility.LOG(true,true,ex,null);
				}				
				
				connection.setAutoCommit(false);
				ServiceDTO service = null;
				do
				{
					//fetch one service
					try
					{
						service = fetchNextServiceForUpdation(connection,orderno);
						connection.commit();
					}
					catch(Exception ex)
					{
						Utility.LOG(true,true,ex,null);
						connection.commit();
						//logExceptionForService(service,ex);//--Later
						continue;
					}
					if(service!=null) 
					{ 
					//push to FX
						String logIdentifier="IN ServiceUpdateSchedular() for Service Product ID "+service.getServiceProductId()+"\n";
//						
						API.updateService(service,logIdentifier);
						//save results	
						try
						{	saveServicePushResult(connection,service);
							connection.commit();
						}
						catch(Exception ex)
						{
							String msg=logIdentifier+"Exception in Service Update Schedular "+service.getExceptionMessage();
							Utility.LOG(true,true,ex,msg);
							connection.rollback();
						}
					}
				}while(service!=null);

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

	
	public void statusUpdateForFailedTransaction(Connection connection)
	{PreparedStatement pstmt=null;
		 try {
			pstmt=connection.prepareStatement(sqlFX_UPDATE_FAILED_TRANSACTION);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try
			{
			DbConnection.closePreparedStatement(pstmt);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		
	}
	
	ServiceDTO fetchNextServiceForUpdation(Connection connection,long Orderno) throws Exception
	{
		ServiceDTO serDto=null;
		
		CallableStatement cstmt_Service = null;
		
		CallableStatement cstmt_ServiceExtendedData = null;
		try
		{
			cstmt_Service=connection.prepareCall(sqlFX_NO_SCH_GETSERVICE_UPDATE_DATA);
			cstmt_Service.setLong(1, Orderno);
			ResultSet rs=cstmt_Service.executeQuery();
			if(rs.next())
			{
				serDto=new ServiceDTO();

				serDto.setRowId(rs.getLong("ID"));
				//serDto.setServiceProductId(rs.getLong("SERVICEPRODUCTID"));
			
			
				
			
				serDto.setA_serviceCompany(rs.getString("SERVICE_COMPANY"));
				serDto.setA_serviceFname(rs.getString("SERVICE_FNAME"));
				serDto.setA_serviceMname(rs.getString("SERVICE_MINIT"));
				serDto.setA_serviceLname(rs.getString("SERVICE_LNAME"));
				serDto.setA_serviceAddress1(rs.getString("SERVICE_ADDRESS1"));
				serDto.setA_serviceAddress2(rs.getString("SERVICE_ADDRESS2"));
				serDto.setA_serviceAddress3(rs.getString("SERVICE_ADDRESS3"));
				serDto.setA_serviceCity(rs.getString("SERVICE_CITY"));
				serDto.setA_serviceState(rs.getString("SERVICE_STATE"));
				serDto.setA_serviceCountryCode((rs.getString("SERVICE_COUNTRY_CODE")==null)?null:rs.getShort("SERVICE_COUNTRY_CODE"));
				serDto.setA_serviceZip(rs.getString("SERVICE_ZIP"));
				serDto.setB_serviceCompany(rs.getString("B_SERVICE_COMPANY"));
				serDto.setB_serviceFname(rs.getString("B_SERVICE_FNAME"));
				serDto.setB_serviceMname(rs.getString("B_SERVICE_MINIT"));
				serDto.setB_serviceLname(rs.getString("B_SERVICE_LNAME"));
				serDto.setB_serviceAddress1(rs.getString("B_SERVICE_ADDRESS1"));
				serDto.setB_serviceAddress2(rs.getString("B_SERVICE_ADDRESS2"));
				serDto.setB_serviceAddress3(rs.getString("B_SERVICE_ADDRESS3"));
				serDto.setB_serviceCity(rs.getString("B_SERVICE_CITY"));
				serDto.setB_serviceCountryCode((rs.getString("B_SERVICE_COUNTRY_CODE")==null)?null:rs.getShort("B_SERVICE_COUNTRY_CODE"));
				serDto.setB_serviceZip(rs.getString("B_SERVICE_ZIP"));
				serDto.setServiceActiveDate(rs.getDate("SERVICEACTIVEDT"));
				serDto.setViewId(rs.getString("VIEW_ID"));
				serDto.setServiceUpdateFlag(rs.getString("FIELDS_FOR_VALIDATION"));
				serDto.setSubscrNo(Integer.parseInt(rs.getString("SUBSCRNO")));
				serDto.setServiceProductId(rs.getLong("SERVICEPRODUCTID"));
				serDto.setSubFxSchdUpdateStatus(rs.getInt("SUB_FX_SCHEDULAR_UPDATE_STATUS"));
						
								
				ArrayList<FxExternalIdDto> externalIds = Utility.getFxExternalIds(connection,serDto.getRowId(),FxExternalIdDto.AssociatedWith_SERVICEUPDATE);
				serDto.setExternalIds(externalIds);
				
				
				ArrayList<ExtendedData> extendedData = Utility.getFxExtendedData(connection,serDto.getRowId(),ExtendedData.AssociatedWith_SERVICEUPDATE);
				serDto.setExtendedData(extendedData);

			}

		}
		
		finally
		{
			if(cstmt_Service!=null) cstmt_Service.close();
			
			if(cstmt_ServiceExtendedData!=null) cstmt_ServiceExtendedData.close();
		}
		return serDto;
	}

	public void saveServicePushResult(Connection connection,ServiceDTO service) throws Exception
	{
		
		if(service.getReturnStatus()==1)//SUCCESS
		{
					
			CallableStatement cstmt_Service_Success = null;
			try
			{
				cstmt_Service_Success=connection.prepareCall(sqlFX_SCHEDULER_SUCCESS);
				cstmt_Service_Success.setLong(1, service.getRowId());
				cstmt_Service_Success.setInt(2, service.getSubProcessingStatus());
				cstmt_Service_Success.executeUpdate();
				

			}
			finally
			{
				if(cstmt_Service_Success!=null) cstmt_Service_Success.close();
			}
			//return serDto;
			//update FX_STATUS in TPOSERVICEDETIALS
			//UPDATE subscr no , reset in TFX_SERVICE create
		}
		else
		{
			CallableStatement cstmt_Service_Failure = null;
			try
			{
				cstmt_Service_Failure=connection.prepareCall(sqlFX_SCHEDULER_FAILURE);
				cstmt_Service_Failure.setLong(1, service.getRowId());
				java.sql.Clob clobData = 
					com.ibm.db2.jcc.t2zos.DB2LobFactory.createClob(Utility.getStackTrace(service.getException()));
				cstmt_Service_Failure.setClob(2, clobData );
				cstmt_Service_Failure.setString(3, service.getExceptionMessage());
				cstmt_Service_Failure.setInt(4, service.getSubProcessingStatus());
				cstmt_Service_Failure.executeUpdate();
				

			}
			finally
			{
				if(cstmt_Service_Failure!=null) cstmt_Service_Failure.close();
			}
			//update FX_STATUS in TPOSERVICEDETIALS
			//LOG exception and errmsg//-Later
			//update TFX_SERVICE.EXCEPTION_HISTORY_ID//-Later
		}
	}

	
	
}
