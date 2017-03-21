//[001]   Raveendra    28-Nov-2014                  Set processing count 10 in case of data issue exception is generated when scheduler is in progress
/**
* author : Sita Ram
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
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Utility;


public class FxSchedulerTasks
{
	//Fetches for FX New Order Schedular , next back or current dated (not future )service which are to be pushed to FX 
	private String sqlFX_NO_SCH_GETSERVICEDATA = "call IOE.FX_NO_SCH_GETSERVICEDATA(?)";
	//Fetched Exxtenal Ids for the servics fetched in sqlFX_NO_SCH_GETSERVICESDATA (ie above)
	private String sqlFX_NO_SCH_GETSERVICES_ID_DATA = "call FX_NO_SCH_GETSERVICE_ID_DATA(?)";
	//Fetched ExtendedData for the service fetched in sqlFX_NO_SCH_GETSERVICESDATA (ie above)
	private String sqlFX_NO_SCH_GETSERVICES_EXT_DATA = "call FX_NO_SCH_GETSERVICE_EXT_DATA(?)";
	private String sqlFX_SCHEDULER_SUCCESS="call IOE.FX_SCHEDULER_SUCCESS(?,?,?,?)";
	private String sqlFX_SCHEDULER_FAILURE="call IOE.FX_SCHEDULER_FAILURE(?,?,?,?)";
	private String FXServiceStatusUpdate="UPDATE IOE.TFX_SERVICECREATE SET FX_SCHEDULAR_CREATE_STATUS=1 WHERE FX_SCHEDULAR_CREATE_STATUS=4 ";

	/**	
	*	This function pushes today's or backdated Services to FX for New Order
	*/
	public void pushServicesToFX(long orderno)
	{
		try
		{
			IOESKenanManager API= null ;
			Utility.LOG(true, true, " IN  pushServicesToFX()");
			Connection connection = null;
			int prev_status = 0;
			String logIdentifier = null;
			try
			{			
				API=new IOESKenanManager();
				API.loginKenan();
				
				connection = DbConnection.getConnectionObject();
				
				try
				{
					statusUpdateforServiceFailed(connection);
				}
				catch(Exception ex)
				{
					Utility.LOG(true,true,ex,null);
				}				
				
				connection.setAutoCommit(false);
				ServiceDTO service = null;
				//Start [001]
				 ArrayList<String> dataIssueException=Utility.getdataIssueException(connection);
				//End [001]
				do
				{
					//fetch one service
					try
					{
						service = fetchNextService(connection,orderno);
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
						
						prev_status = 	FxOrderTrackerTask.checkPreviousStatus(connection,API,service,FxOrderTrackerConst.SERVICE);						
						if(prev_status == FxOrderTrackerConst.CANELLED || prev_status == FxOrderTrackerConst.NoPreviousLOG ) 
						{	
						//push to FX
							logIdentifier ="Service SCHEDULAR ERRROR , SERVICEPRODUCTID:"+service.getServiceProductId()+"\n";//						
							API.sendServiceToFx(service,logIdentifier);
						}
						//save results	
						try
						{
							saveServicePushResult(connection,service,dataIssueException);
							connection.commit();
						}
						catch(Exception ex)
						{
							String msg=logIdentifier+"Exception in saving FX New Order Service Schedular final Result for serviceproductId :"+service.getServiceProductId()
								+" \n Result was returnStatus="+service.getReturnStatus()
								+" \n service.getSubscrNo() , if any ,="+service.getSubscrNo()
								+" \n service.getSubscrNooReset()= , if any ,="+service.getSubscrNoReset()
								+" \n Exception , if any, = "+((service.getException()==null)?null:Utility.getStackTrace(service.getException()))
								+" \n Exception Message , if any,= "+service.getExceptionMessage();
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

	
	public void statusUpdateforServiceFailed(Connection connection)
	{
		PreparedStatement pstmt=null;
		 try {
			pstmt=connection.prepareStatement(FXServiceStatusUpdate);
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
	
	ServiceDTO fetchNextService(Connection connection,long Orderno) throws Exception
	{
		ServiceDTO serDto=null;
		ResultSet rs=null;
		CallableStatement cstmt_Service = null;
		CallableStatement cstmt_ServiceExternalIds = null;
		CallableStatement cstmt_ServiceExtendedData = null;
		try
		{
			cstmt_Service=connection.prepareCall(sqlFX_NO_SCH_GETSERVICEDATA);
			cstmt_Service.setLong(1,Orderno);
			rs=cstmt_Service.executeQuery();
			if(rs.next())
			{
				serDto=new ServiceDTO();

				serDto.setRowId(rs.getLong("ID"));
				serDto.setServiceProductId(rs.getLong("SERVICEPRODUCTID"));
			
			
				serDto.setEmfConfigId((rs.getString("EMFCONFIGID")==null)?null:rs.getInt("EMFCONFIGID"));
				serDto.setServiceActiveDate(rs.getDate("SERVICEACTIVEDT"));
				//serDto.setServiceDisplayExternalIdType((rs.getString("EXTERNALIDTYPE")==null)?null:rs.getInt("EXTERNALIDTYPE"));
				//serDto.setServExtId(rs.getString("SERVEXTID"));
				serDto.setPrivacyLevel((rs.getString("PRIVACYLEVEL")==null)?null:rs.getInt("PRIVACYLEVEL"));
				serDto.setAcctExternalId(rs.getString("ACCTEXTERNALID"));
				//serDto.setAcctExternalId("101-1-1-2-1-1-1-8-0-3390-0-214-0-0-0-0-0");
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
				serDto.setRateClass((rs.getString("RATE_CLASS")==null)?null:rs.getInt("RATE_CLASS"));
				serDto.setCurrencyCode((rs.getString("CURRENCY_CODE")==null)?null:rs.getInt("CURRENCY_CODE"));
				
				
				
				ArrayList<FxExternalIdDto> externalIds = Utility.getFxExternalIds(connection,serDto.getRowId(),FxExternalIdDto.AssociatedWith_SERVICE);
				serDto.setExternalIds(externalIds);
				
				
				ArrayList<ExtendedData> extendedData = Utility.getFxExtendedData(connection,serDto.getRowId(),ExtendedData.AssociatedWith_SERVICE);
				serDto.setExtendedData(extendedData);
				
				extendedData = Utility.getFxExtendedData(connection,serDto.getRowId(),ExtendedData.AssociatedWith_ORDER_FOR_SERVICE);
				serDto.setOrderExtendedData(extendedData);

				//foreach service set read their extrenal Ids and Extended Data
		
				/*//fetch all external ids for service
				cstmt_ServiceExternalIds=connection.prepareCall(sqlFX_NO_SCH_GETSERVICE_ID_DATA);
				cstmt_ServiceExternalIds.setLong(serDto.getRowId());
				ResultSet rsExternalIds=cstmt_ServiceExternalIds.executeQuery();

				ArrayList<FxExternalId> externalIds=new ArrayList<FxExternalId>();
				while(rsExternalIds.next())
				{
					FxExternalId currExternalId = new FxExternalId();
					currExternalId.setExternalIdType(rsExternalIds.getInt("EXTERNALIDTYPE"));
					currExternalId.setExternalId(rsExternalIds.getInt("EXTERNALID"));
					externalIds.add(currExternalId);
				}

				serDto.setExternalIds(externalIds);

				//fetch all extended ids for service
				cstmt_ServiceExtendedData=connection.prepareCall(sqlFX_NO_SCH_GETSERVICE_EXT_DATA);
				cstmt_ServiceExtendedData.setLong(serDto.getRowId());
				ResultSet rsExtendedData=cstmt_ServiceExtendedData.executeQuery();

				ArrayList<FxExtendedData> extendedData=new ArrayList<FxExtendedData>();
				while(rsExtendedData.next())
				{
					FxExtendedData currExtendedData = new FxExtendedData();
					currExtendedData.setParamId(rsExtendedData.getInt("PARAM_ID"));
					currExtendedData.setParamValue(rsExtendedData.getString("PARAM_VALUE"));
					currExtendedData.setDataType(rsExtendedData.getString("DATA_TYPE"));
					extendedData.add(currExtendedData);
				}

				serDto.setExtendedData(extendedData);*/
			}

		}
		
		finally
		{
			if(rs!=null)DbConnection.closeResultset(rs);
			if(cstmt_Service!=null)DbConnection.closeCallableStatement(cstmt_Service) ;
			if(cstmt_ServiceExternalIds!=null) DbConnection.closeCallableStatement(cstmt_ServiceExternalIds);
			if(cstmt_ServiceExtendedData!=null) DbConnection.closeCallableStatement(cstmt_ServiceExtendedData);
		}
		return serDto;
	}

	public void saveServicePushResult(Connection connection,ServiceDTO service,ArrayList<String> dataIssueException) throws Exception
	{
		
		if(service.getReturnStatus()==1)//SUCCESS
		{
					
			CallableStatement cstmt_Service_Success = null;
			try
			{
				cstmt_Service_Success=connection.prepareCall(sqlFX_SCHEDULER_SUCCESS);
				cstmt_Service_Success.setLong(1, service.getRowId());
				cstmt_Service_Success.setLong(2, service.getSubscrNo());
				cstmt_Service_Success.setString(3, service.getToken_id());
				cstmt_Service_Success.setString(4, service.getViewId());
				cstmt_Service_Success.executeUpdate();
				

			}
			finally
			{
				if(cstmt_Service_Success!=null)DbConnection.closeCallableStatement(cstmt_Service_Success) ;
			}
			//return serDto;
			//update FX_STATUS in TPOSERVICEDETIALS
			//UPDATE subscr no , reset in TFX_SERVICE create
		}
		else
		{
			//start [001]
		    
			boolean isDataIssueException=Utility.isDataIssueException(dataIssueException,service.getException());
			service.setDataIssueException(isDataIssueException);
			
			//end [001]
			CallableStatement cstmt_Service_Failure = null;
			try
			{
				cstmt_Service_Failure=connection.prepareCall(sqlFX_SCHEDULER_FAILURE);
				cstmt_Service_Failure.setLong(1, service.getRowId());
				java.sql.Clob clobData = 
					com.ibm.db2.jcc.t2zos.DB2LobFactory.createClob(Utility.getStackTrace(service.getException()));
				cstmt_Service_Failure.setClob(2, clobData );
				cstmt_Service_Failure.setString(3, service.getExceptionMessage());
				
				
				
				//start [001] 
				if(service.isDataIssueException()){
					cstmt_Service_Failure.setLong(4, 1);
				}else{
					cstmt_Service_Failure.setLong(4, 0);
				}
				
				//end [001]
				
				cstmt_Service_Failure.executeUpdate();
				

			}
			finally
			{
				if(cstmt_Service_Failure!=null)DbConnection.closeCallableStatement(cstmt_Service_Failure) ;
			}
			//update FX_STATUS in TPOSERVICEDETIALS
			//LOG exception and errmsg//-Later
			//update TFX_SERVICE.EXCEPTION_HISTORY_ID//-Later
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