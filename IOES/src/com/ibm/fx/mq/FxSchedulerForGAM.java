/**
* author : SR
*/
package com.ibm.fx.mq;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ibm.fx.dto.ExtendedData;
import com.ibm.fx.dto.FxExternalIdDto;
import com.ibm.fx.dto.ServiceDTO;
import com.ibm.ioes.beans.GamDetailDto;
import com.ibm.ioes.beans.OrderForGamDto;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Utility;


public class FxSchedulerForGAM
{
	private static final String sql_GAM_SYNC_FOR_ORDER = "call ARBOR.GAM_SYNC_FOR_ORDER(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	//Fetches for FX New Order Schedular , next back or current dated (not future )service which are to be pushed to FX
	private String sqlFX_GAM_SYNC_SCH_GET_ORDER_DATA = "call IOE.FX_GAM_SYNC_SCH_GET_ORDER_DATA(?,?,?)";
	private String sqlFX_GAM_SYNC_SCH_GET_GAM_FOR_ORDER = "call IOE.FX_GAM_SYNC_SCH_GET_GAM_FOR_ORDER(?)";
	
	
	private String sqlGAM_SCHEDULER_SUCCESS_FAILURE="call IOE.FX_GAM_SYNC_SCHEDULER_SUCCESS_FAILURE(?,?,?)";
	
	private String GAM_SYNC_StatusUpdateForFailed="update ioe.tpomaster set GAM_FX_SYNC=1 where GAM_FX_SYNC=4";

	/**	
	*	This function pushes today's or backdated Services to FX for New Order
	*/
	public void pushGAMsToFX()
	{
		try
		{
			Connection fxDbConnection= null ;
			Utility.LOG(true, true, " IN  pushGAMsToFX()");
			Connection ib2b_connection = null;	
			try
			{			
				fxDbConnection=FxDbConnectionRetriever.getFxDbConnection();
				fxDbConnection.setAutoCommit(false);
				ib2b_connection = DbConnection.getConnectionObject();
				
				try
				{
					statusUpdateforGamOrdersFailed(ib2b_connection);
				}
				catch(Exception ex)
				{
					Utility.LOG(true,true,ex,null);
				}				
				
				ib2b_connection.setAutoCommit(false);
				OrderForGamDto gamFxDto = null;
				do
				{
					//fetch one service
					try
					{
						gamFxDto = fetchNextOrderGAM(ib2b_connection);
						/*ib2b_connection.commit();*/
					}
					catch(Exception ex)
					{
						Utility.LOG(true,true,ex,null);
						ib2b_connection.commit();
						//logExceptionForService(service,ex);//--Later
						continue;
					}
					if(gamFxDto!=null) 
					{ 
					//push to FX
						String logIdentifier="GAM FX SCHEDULAR ERRROR , Order No:"+gamFxDto.getOrderNo()+"\n";
//						
						sendGamsToFx(fxDbConnection,gamFxDto,logIdentifier);
						//save results	
						try
						{	saveGAMPushResult(ib2b_connection,gamFxDto);
							ib2b_connection.commit();
						}
						catch(Exception ex)
						{
							String msg=logIdentifier+"Exception in saving FX GAM Schedular final Result for Order No :"+gamFxDto.getOrderNo()
								+" \n Result was returnStatus="+gamFxDto.getReturnStatus()
								//+" \n GAM Ids saved are ="+gamFxDto.getAllGAMIds() //TODO
								+" \n Exception , if any, = "+((gamFxDto.getException()==null)?null:Utility.getStackTrace(gamFxDto.getException()))
								+" \n Exception Message , if any,= "+gamFxDto.getExceptionMessage();
							Utility.LOG(true,true,ex,msg);
							ib2b_connection.rollback();
						}
					}
				}while(gamFxDto!=null);

			}
			finally
			{
				try
				{
					if(ib2b_connection!=null)
					{
						DbConnection.freeConnection(ib2b_connection);
					}
				}
				catch(Exception ex)
				{
					Utility.LOG(true,true,ex,null);
				}
				FxDbConnectionRetriever.freeConnection(fxDbConnection);
				
			}
		}
		catch(Throwable th)
		{
			Utility.LOG(true,true,new Exception(th),null);
		}

		
	}

	

	private void sendGamsToFx(Connection fxDbConnection, OrderForGamDto gamFxDto, String logIdentifier) {
		
		int returnStatus = 0;
		try {
			CallableStatement cstmt= fxDbConnection.prepareCall(sql_GAM_SYNC_FOR_ORDER);
			cstmt.setLong(1, gamFxDto.getOrderNo());
			cstmt.setLong(2, gamFxDto.getFxGamOrderMappingId());
			cstmt.setLong(3, gamFxDto.getCreditSharingModuleId());
			int i=4,j=17;
			
			for (GamDetailDto gamDetailDto: gamFxDto.getGamDetails()) {
				cstmt.setString(i, String.valueOf(gamDetailDto.getCrmEmployeeId()));
				i++;
				cstmt.setString(i, gamDetailDto.getGamName());
				i++;
			}
			
			Object a=null;
			for(;i<=j;i++)
			{
				cstmt.setNull(i, java.sql.Types.VARCHAR);
			}				
			cstmt.registerOutParameter(18, java.sql.Types.BIGINT);
			cstmt.execute();
			Long mappingId=cstmt.getLong(18);
			if(gamFxDto.getFxGamOrderMappingId()==0)
			{
				gamFxDto.setFxGamOrderMappingId(mappingId);
			}
			
			returnStatus = 1;
		} catch (SQLException e) {
			
			returnStatus = -1;
			Utility.LOG(true,true,e,null);
		}
		gamFxDto.setReturnStatus(returnStatus);
	}


	public void statusUpdateforGamOrdersFailed(Connection connection)
	{
		PreparedStatement pstmt=null;
		 try {
			pstmt=connection.prepareStatement(GAM_SYNC_StatusUpdateForFailed);
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
	
	OrderForGamDto fetchNextOrderGAM(Connection connection) throws Exception
	{
		OrderForGamDto gamFxDTO=null;
		
		
		CallableStatement cstmt_gam_order = null;
		CallableStatement cstmt_GAMs = null;
		try
		{
			cstmt_gam_order=connection.prepareCall(sqlFX_GAM_SYNC_SCH_GET_ORDER_DATA);
			cstmt_gam_order.registerOutParameter(1, java.sql.Types.BIGINT);
			cstmt_gam_order.registerOutParameter(2, java.sql.Types.BIGINT);
			cstmt_gam_order.registerOutParameter(3, java.sql.Types.BIGINT);
			cstmt_gam_order.execute();
			long orderno=cstmt_gam_order.getLong(1);
			if(orderno!=0)
			{
				gamFxDTO=new OrderForGamDto();
				gamFxDTO.setOrderNo(orderno);
				gamFxDTO.setFxGamOrderMappingId(cstmt_gam_order.getLong(2));
				gamFxDTO.setCreditSharingModuleId(cstmt_gam_order.getLong(3));
				
				cstmt_GAMs=connection.prepareCall(sqlFX_GAM_SYNC_SCH_GET_GAM_FOR_ORDER);
				cstmt_GAMs.setLong(1, gamFxDTO.getOrderNo());
				ResultSet rs_gams=cstmt_GAMs.executeQuery();
				ArrayList<GamDetailDto> gamDetails = new ArrayList<GamDetailDto>();
				GamDetailDto gam = null;
				while(rs_gams.next())
				{
					gam=new GamDetailDto();
					gam.setCrmEmployeeId(rs_gams.getLong("CRM_EMPLOYEE_ID"));
					gam.setGamName(rs_gams.getString("GAM_NAME"));
					gamDetails.add(gam);
				}				
				gamFxDTO.setGamDetails(gamDetails);
			}

		}
		
		finally
		{
			if(cstmt_gam_order!=null) cstmt_gam_order.close();
			if(cstmt_GAMs!=null) cstmt_GAMs.close();
		}
		/*ServiceDTO serDto=null;
		
		CallableStatement cstmt_Service = null;
		CallableStatement cstmt_ServiceExternalIds = null;
		CallableStatement cstmt_ServiceExtendedData = null;
		try
		{
			cstmt_Service=connection.prepareCall(sqlFX_NO_SCH_GETSERVICEDATA);
			ResultSet rs=cstmt_Service.executeQuery();
			if(rs.next())
			{
				serDto=new ServiceDTO();

				serDto.setRowId(rs.getLong("ID"));
				serDto.setServiceProductId(rs.getLong("SERVICEPRODUCTID"));
			
			
				serDto.setEmfConfigId((rs.getString("EMFCONFIGID")==null)?null:rs.getInt("EMFCONFIGID"));
				serDto.setServiceActiveDate(rs.getDate("SERVICEACTIVEDT"));
				serDto.setPrivacyLevel((rs.getString("PRIVACYLEVEL")==null)?null:rs.getInt("PRIVACYLEVEL"));
				serDto.setAcctExternalId(rs.getString("ACCTEXTERNALID"));
				serDto.setRevRcvCostCtr((rs.getString("REVRCVCOSTCTR")==null)?null:rs.getInt("REVRCVCOSTCTR"));
			
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
				
				
				
				ArrayList<FxExternalIdDto> externalIds = Utility.getFxExternalIds(connection,serDto.getRowId());
				serDto.setExternalIds(externalIds);
				
				
				ArrayList<ExtendedData> extendedData = Utility.getFxExtendedData(connection,serDto.getRowId(),ExtendedData.AssociatedWith_SERVICE);
				serDto.setExtendedData(extendedData);
				
				extendedData = Utility.getFxExtendedData(connection,serDto.getRowId(),ExtendedData.AssociatedWith_ORDER_FOR_SERVICE);
				serDto.setOrderExtendedData(extendedData);

				
			}

		}
		
		finally
		{
			if(cstmt_Service!=null) cstmt_Service.close();
			if(cstmt_ServiceExternalIds!=null) cstmt_ServiceExternalIds.close();
			if(cstmt_ServiceExtendedData!=null) cstmt_ServiceExtendedData.close();
		}
		return serDto;*/
		return gamFxDTO;
	}

	public void saveGAMPushResult(Connection ib2b_connection, OrderForGamDto gamFxDto) throws Exception
	{
		
		if(gamFxDto.getReturnStatus()==1)//SUCCESS
		{
					
			CallableStatement cstmt = null;
			try
			{
				cstmt=ib2b_connection.prepareCall(sqlGAM_SCHEDULER_SUCCESS_FAILURE);
				cstmt.setLong(1, gamFxDto.getOrderNo());
				cstmt.setString(2, "SUCCESS");
				cstmt.setLong(3, gamFxDto.getFxGamOrderMappingId());
				cstmt.executeUpdate();
			}
			finally
			{
				if(cstmt!=null) cstmt.close();
				if(cstmt!=null)DbConnection.closeCallableStatement(cstmt);
			}
		}
		else
		{
			CallableStatement cstmt = null;
			try
			{
				cstmt=ib2b_connection.prepareCall(sqlGAM_SCHEDULER_SUCCESS_FAILURE);
				cstmt.setLong(1, gamFxDto.getOrderNo());
				cstmt.setString(2, "FAILURE");
				cstmt.setNull(3, java.sql.Types.BIGINT);
				cstmt.executeUpdate();
			}
			finally
			{
				if(cstmt!=null) cstmt.close();
				if(cstmt!=null)DbConnection.closeCallableStatement(cstmt);
			}
		}
	}
/*
SEQUENCE - SEQ_EXCEPTION_HISTORY_ID

TABLE : TFX_EXCEPTION_MASTER
1)EXCEPTION_HISTORY_ID(BIGINT)
2)EXCEPTION - CLOB
3)MESSAGE VARCHAR(1000)
*/
/*
in serviceDto 
ArrayList<FxExternalId> externalIds = null ;
add getters and setters


and
class FxExternalId
{
	private Integer externalIdType= null;
	private String externalId = null;

	//add getters and setters
}


Table FxExternaIds

table name : TFX_EXTERNALIDS
columns :
1)EXTERNALIDTYPE integer
2)EXTERNALID string
3)ParentEntityType : 1=Account/2=Service
4)ParentEntityId : BIGINT/Row Id
*/
/*
 FX_NO_SCH_GETSERVICES_ID_DATA(id) :
	select * from TFX_EXTERNALIDS where ParentEntityType=2 and ParentEntityId =id

----------------------------------------
FX_NO_SCH_GETSERVICES_EXT_DATA(id :
	select * from TFX_EXTENDED_DATA where toupper(ASSOCIATED_WITH)=toupper('Service') and PARENT_ID =id

---------------------
make FK of TABLE TFX_EXTENDED_DATA fro two fields

already defined :
CREATE TABLE TFX_EXTENDED_DATA ( 
	ID             	BIGINT GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1, CACHE 20) NOT NULL,
	PARAM_ID       	INTEGER,
	PARAM_VALUE    	VARCHAR(50),
	DATA_TYPE      	VARCHAR(10),
	PARENT_ID      	BIGINT,
	ASSOCIATED_WITH	VARCHAR(25),
	CREATED_DATE   	TIMESTAMP DEFAULT CURRENT TIMESTAMP 
	)
GO
already defined :
CREATE TABLE TFX_EXTENDED_DATA_TYPE ( 
	DATA_TYPE  	VARCHAR(10) NOT NULL,
	DESCRIPTION	VARCHAR(50) 
	)
GO
define this:
CREATE TABLE TFX_EXTENDED_ASSOCIATED_WITH ( 
	ASSOCIATED_WITH	VARCHAR(25) NOT NULL,
	DESCRIPTION	VARCHAR(50) 
	)
*/


}

/*NCREMENT BY 1, CACHE 20) NOT NULL,
	PARAM_ID       	INTEG*/