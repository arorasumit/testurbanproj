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

import com.ibm.fx.dto.ChargeRedirectDTO;
import com.ibm.fx.dto.ExtendedData;
import com.ibm.fx.dto.FxExternalIdDto;
import com.ibm.fx.dto.ServiceDTO;
import com.ibm.ioes.beans.GamDetailDto;
import com.ibm.ioes.beans.OrderForGamDto;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Utility;


public class FxSchedulerForCumulative
{	
	private String sqlFX_GET_ACCOUNT_FOR_CUMULATIVE = "call IOE.FX_NO_SCH_GET_ACCOUNT_FOR_REDIRECTION_CUMULATIVE(?)";
	
	private String sqlCUMULATIVE_SCHEDULER_SUCCESS_FAILURE="call IOE.FX_CUMULATIVE_SYNC_SCHEDULER_SUCCESS_FAILURE(?,?,?,?,?,?,?)";
		
	private String CumulativeStatusUpdateForFailed="update ioe.TFX_CHARGE_REDIRECTION set CUMULATIVE_PROCESS_STATUS=1 where CUMULATIVE_PROCESS_STATUS=4";

	private String sqlInsertCumulativeToFx="INSERT INTO GAMUSER.CUSTOM_CHILD_ACCTS_GROUPING(MAPPING_TYPE,SECOND_LEVEL_PARENT,CHILD_ACCOUNT_NO)VALUES('CUMULATIVE',?,?)";
	private String sqlFetchGeneretedCumulativeIDFromFX="SELECT " +
															"CUSTOM_CHILD_ACCTS_GROUPING_ID " +
														" FROM GAMUSER.CUSTOM_CHILD_ACCTS_GROUPING " +
														" WHERE MAPPING_TYPE='CUMULATIVE' " +
														" AND SECOND_LEVEL_PARENT=? AND CHILD_ACCOUNT_NO=?";
	/**	
	*	This function pushes today's or backdated Services to FX for New Order
	*/
	public void pushCumulativeToFX(long orderNo)
	{
		try
		{
			Connection fxDbConnection= null ;
			Utility.LOG(true, true, " IN  pushCumulativeToFX()");
			Connection ib2b_connection = null;	
			try
			{			
				fxDbConnection=FxDbConnectionRetriever.getFxDbConnection();
				fxDbConnection.setAutoCommit(false);
				ib2b_connection = DbConnection.getConnectionObject();
				
				try
				{
					statusUpdateforCumulativeFailed(ib2b_connection);
				}
				catch(Exception ex)
				{
					Utility.LOG(true,true,ex,null);
				}				
				
				ib2b_connection.setAutoCommit(false);
				ChargeRedirectDTO cumulativeFxDto = null;
				do
				{
					//fetch one service
					try
					{
						cumulativeFxDto = fetchNextCumulativeAccount(ib2b_connection,orderNo);						
						ib2b_connection.commit();
					}
					catch(Exception ex)
					{
						Utility.LOG(true,true,ex,null);
						ib2b_connection.commit();
						//logExceptionForService(service,ex);//--Later
						continue;
					}
					if(cumulativeFxDto!=null) 
					{ 
					//push to FX
						String logIdentifier="CUMULATIVE FX SCHEDULAR ERRROR , Order No:"+cumulativeFxDto.getOrderNo()+"\n Source Account No:"+cumulativeFxDto.getAccountno();
							
						sendCumulativeToFx(fxDbConnection,cumulativeFxDto,logIdentifier);
						//save results	
						try
						{	saveCumulativePushResult(ib2b_connection,cumulativeFxDto);
							ib2b_connection.commit();
						}
						catch(Exception ex)
						{
							String msg=logIdentifier+"Exception in saving FX CUMULATIVE Schedular final Result for Order No :"+cumulativeFxDto.getOrderNo()+"\n Source Account No:"+cumulativeFxDto.getAccountno()
								+" \n Result was returnStatus="+cumulativeFxDto.getReturnStatus()
								//+" \n GAM Ids saved are ="+gamFxDto.getAllGAMIds() //TODO
								+" \n Exception , if any, = "+((cumulativeFxDto.getException()==null)?null:Utility.getStackTrace(cumulativeFxDto.getException()))
								+" \n Exception Message , if any,= "+cumulativeFxDto.getExceptionMessage();
							Utility.LOG(true,true,ex,msg);
							ib2b_connection.rollback();
						}
					}
				}while(cumulativeFxDto!=null);

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

	

	private void sendCumulativeToFx(Connection fxDbConnection, ChargeRedirectDTO cumulativeFxDto, String logIdentifier) {
		
		int returnStatus = 0;
		ResultSet rs=null;
		CallableStatement cstmt=null;
		try {
			cstmt= fxDbConnection.prepareCall(sqlInsertCumulativeToFx);			
			cstmt.setLong(1, cumulativeFxDto.getBalanceAccountNo());
			cstmt.setLong(2, cumulativeFxDto.getAccountno());
			
			cstmt.execute();
			fxDbConnection.commit();
						
			cstmt= fxDbConnection.prepareCall(sqlFetchGeneretedCumulativeIDFromFX);			
			cstmt.setLong(1, cumulativeFxDto.getBalanceAccountNo());
			cstmt.setLong(2, cumulativeFxDto.getAccountno());
			rs=cstmt.executeQuery();
			
			long custom_child_accts_grouping_id=0l;
			while(rs.next()){
				custom_child_accts_grouping_id=rs.getLong("CUSTOM_CHILD_ACCTS_GROUPING_ID");
				cumulativeFxDto.setCustom_child_accts_grouping_id(custom_child_accts_grouping_id);
			}			
			
			returnStatus = 1;
		} catch (SQLException e) {
			try{
				fxDbConnection.rollback();
			}catch(Exception exp){
				exp.printStackTrace();
			}
			returnStatus = -1;
			Utility.LOG(true,true,e,null);
			cumulativeFxDto.setExceptionMessage(logIdentifier);
			cumulativeFxDto.setException(e);
			
		}finally{
			try {
				DbConnection.closeResultset(rs);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DbConnection.closeCallableStatement(cstmt);
		}
		cumulativeFxDto.setReturnStatus(returnStatus);
	}


	public void statusUpdateforCumulativeFailed(Connection connection)
	{
		PreparedStatement pstmt=null;
		 try {
			pstmt=connection.prepareStatement(CumulativeStatusUpdateForFailed);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				DbConnection.closePreparedStatement(pstmt);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	ChargeRedirectDTO fetchNextCumulativeAccount(Connection connection, long orderNo) throws Exception
	{
		ChargeRedirectDTO chargeRedirectDto=null;
		
		CallableStatement cstmt = null;
		
		try
		{
			cstmt=connection.prepareCall(sqlFX_GET_ACCOUNT_FOR_CUMULATIVE);	
			cstmt.setLong(1, orderNo);
			ResultSet rs=cstmt.executeQuery();
			if(rs.next())
			{
				chargeRedirectDto=new ChargeRedirectDTO();

				chargeRedirectDto.setRowId(rs.getLong("TFX_CHARGE_REDIRECTION_ID"));				
				chargeRedirectDto.setAccountno(rs.getInt("ACCOUNT_INTERNAL_ID"));
				//chargeRedirectDto.setAccountExternalid(rs.getString("ACCOUNT_EXTERNAL_ID"));
				chargeRedirectDto.setBalanceAccountNo(rs.getInt("BALANCE_ACCOUNT_INTERNAL_ID"));
				//chargeRedirectDto.setBalanceAccountExternalid(rs.getString("BALANCE_ACCOUNT_EXTERNAL_ID"));
				//chargeRedirectDto.setSubscrNo(rs.getInt("SUBSCR_NO"));
				//chargeRedirectDto.setSubscrNoReset(rs.getInt("SUBSCR_NO_RESETS"));
				//chargeRedirectDto.setRedirectionActiveDate(rs.getDate("ACTIVE_DT"));
				chargeRedirectDto.setOrderNo(rs.getLong("ORDERNO"));
				
			}
			
		}		
		finally
		{
			DbConnection.closeCallableStatement(cstmt);				
			
		}
		return chargeRedirectDto;
	}

	public void saveCumulativePushResult(Connection ib2b_connection, ChargeRedirectDTO cumulativeFxDto) throws Exception
	{				
					
			CallableStatement cstmt = null;
			try
			{
				cstmt=ib2b_connection.prepareCall(sqlCUMULATIVE_SCHEDULER_SUCCESS_FAILURE);
				cstmt.setLong(1, cumulativeFxDto.getRowId());
				if(cumulativeFxDto.getReturnStatus()==1){
					Utility.LOG(true, true, " pushCumulativeToFX Successfully  ");
					cstmt.setInt(2, 3);//SUCCESS
					cstmt.setNull(3, java.sql.Types.VARCHAR);
					cstmt.setNull(4, java.sql.Types.CLOB);
				}else{
					cstmt.setInt(2, 4);//FAILURE
					cstmt.setString(3,cumulativeFxDto.getExceptionMessage());
					if(cumulativeFxDto.getException()!=null)
					{
						java.sql.Clob clobData = com.ibm.db2.jcc.t2zos.DB2LobFactory.createClob(Utility.getStackTrace(cumulativeFxDto.getException()));
						cstmt.setClob(4, clobData);
					}
					else
					{
						cstmt.setNull(4, java.sql.Types.CLOB);
					}
				}	
				cstmt.setLong(5, cumulativeFxDto.getCustom_child_accts_grouping_id());
				cstmt.setLong(6, cumulativeFxDto.getBalanceAccountNo());
				cstmt.setLong(7, cumulativeFxDto.getAccountno());
				cstmt.executeUpdate();
			}
			finally
			{
				DbConnection.closeCallableStatement(cstmt);
			}		
	}

}
