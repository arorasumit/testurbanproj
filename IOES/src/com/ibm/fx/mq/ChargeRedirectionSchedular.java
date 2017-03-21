package com.ibm.fx.mq;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibm.fx.dto.ChargeRedirectDTO;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Utility;

public class ChargeRedirectionSchedular {

	// update AccountNo,subscriberno,balance account no DETAILS
	private String sqlFX_UPDATE_ACCOUNT_DETAILS_FOR_REDIRECTION = "call IOE.FX_UPDATE_ACCOUNT_DETAILS_FOR_REDIRECTION()";
	//FETCH ACCOUNTS FOR CHARGE REDIRECTION in sqlFX_GET_ACCOUNT_FOR_REDIRECTION
	private String sqlFX_GET_ACCOUNT_FOR_REDIRECTION = "call IOE.FX_NO_SCH_GET_ACCOUNT_FOR_REDIRECTION()";
	
	private String sqlFX_SCH_REDIRECT_SUCCESS="call IOE.FX_SCHEDULER_SUCCESS_FOR_REDIRECTION(?,?,?)";
	private String sqlFX_SCH_REDIRECT_FAILURE="call IOE.FX_SCHEDULER_FAILURE_FOR_REDIRECTION(?,?,?)";
	

	/**	
	*	FUNCTOIN PUSH MAPPINGS IN FX FOR REDIRECTION OF CHARGES ON ACCOUNT
	*/
	public void redirectCharges()
	{
		try
		{
			IOESKenanManager API= null ;
			Utility.LOG(true, true, " IN  redirectCharges()");
			Connection connection = null;	
			try
			{			
				API=new IOESKenanManager();
				API.loginKenan();
				
				connection = DbConnection.getConnectionObject();
				
				try
				{
					updateAccountDetailsForRedirection(connection);
					
				}
				catch(Exception ex)
				{
					Utility.LOG(true,true,ex,null);
				}				
				
				connection.setAutoCommit(false);
				ChargeRedirectDTO chargeRedirectdto = null;
				do
				{
					//fetch next account for redirection
					try
					{
						chargeRedirectdto = fetchNextAccount(connection);
						connection.commit();
					}
					catch(Exception ex)
					{
						Utility.LOG(true,true,ex,null);
						connection.commit();
						//logExceptionForService(service,ex);//--Later
						continue;
					}
					if(chargeRedirectdto!=null) 
					{ 
					//push to FX
						String logIdentifier="CHARGE REDIRECTION SCHEDULAR ERROR , Rowid:"+chargeRedirectdto.getRowId()+"\n";
//						
						API.redirectCharges(chargeRedirectdto,logIdentifier);
						//save results	
						try
						{	saveTransactionResult(connection,chargeRedirectdto);
							connection.commit();
						}
						catch(Exception ex)
						{
							String msg=logIdentifier+"Exception in Pushing Charge Redirection For Accnt in FX. Final Result for  Account No "+chargeRedirectdto.getAccountExternalid()
								+" \n Result was returnStatus="+chargeRedirectdto.getReturnStatus()
								+" \n Balance Accnt External id="+chargeRedirectdto.getBalanceAccountExternalid()
								+" \n Exception , if any, = "+((chargeRedirectdto.getException()==null)?null:Utility.getStackTrace(chargeRedirectdto.getException()))
								+" \n Exception Message , if any,= "+chargeRedirectdto.getExceptionMessage();
							Utility.LOG(true,true,ex,msg);
							connection.rollback();
						}
					}
				}while(chargeRedirectdto!=null);

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

	
	public void updateAccountDetailsForRedirection(Connection connection)
	{
		 try {
			 CallableStatement cstmt=connection.prepareCall(sqlFX_UPDATE_ACCOUNT_DETAILS_FOR_REDIRECTION);
			 cstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	ChargeRedirectDTO fetchNextAccount(Connection connection) throws Exception
	{
		ChargeRedirectDTO chargeRedirectDto=null;
		
		CallableStatement cstmt = null;
		
		try
		{
			cstmt=connection.prepareCall(sqlFX_GET_ACCOUNT_FOR_REDIRECTION);			
			ResultSet rs=cstmt.executeQuery();
			if(rs.next())
			{
				chargeRedirectDto=new ChargeRedirectDTO();

				chargeRedirectDto.setRowId(rs.getLong("TFX_CHARGE_REDIRECTION_ID"));				
				chargeRedirectDto.setAccountno(rs.getInt("ACCOUNT_INTERNAL_ID"));
				chargeRedirectDto.setAccountExternalid(rs.getString("ACCOUNT_EXTERNAL_ID"));
				chargeRedirectDto.setBalanceAccountNo(rs.getInt("BALANCE_ACCOUNT_INTERNAL_ID"));
				chargeRedirectDto.setBalanceAccountExternalid(rs.getString("BALANCE_ACCOUNT_EXTERNAL_ID"));
				chargeRedirectDto.setSubscrNo(rs.getInt("SUBSCR_NO"));
				chargeRedirectDto.setSubscrNoReset(rs.getInt("SUBSCR_NO_RESETS"));
				chargeRedirectDto.setRedirectionActiveDate(rs.getDate("ACTIVE_DT"));
				
			}
			
		}
		
		finally
		{
			if(cstmt!=null) 
				cstmt.close();
			
		}
		return chargeRedirectDto;
	}

	public void saveTransactionResult(Connection connection,ChargeRedirectDTO chargeRedirectdto) throws Exception
	{
		
		if(chargeRedirectdto.getReturnStatus()==1)//SUCCESS
		{
					
			CallableStatement cstmt_Redirect_Success = null;
			try
			{
				cstmt_Redirect_Success=connection.prepareCall(sqlFX_SCH_REDIRECT_SUCCESS);
				cstmt_Redirect_Success.setLong(1,chargeRedirectdto.getRowId());
				cstmt_Redirect_Success.setLong(2,chargeRedirectdto.getTrackingid());
				cstmt_Redirect_Success.setLong(3,chargeRedirectdto.getTrackingIdReset());
				cstmt_Redirect_Success.executeUpdate();
				

			}
			finally
			{
				if(cstmt_Redirect_Success!=null) cstmt_Redirect_Success.close();
			}
			
		}
		else
		{
			CallableStatement cstmt_Redirect_Failure = null;
			try
			{
				cstmt_Redirect_Failure=connection.prepareCall(sqlFX_SCH_REDIRECT_FAILURE);
				cstmt_Redirect_Failure.setLong(1, chargeRedirectdto.getRowId());
				java.sql.Clob clobData = 
					com.ibm.db2.jcc.t2zos.DB2LobFactory.createClob(Utility.getStackTrace(chargeRedirectdto.getException()));
				cstmt_Redirect_Failure.setClob(2, clobData );
				cstmt_Redirect_Failure.setString(3, chargeRedirectdto.getExceptionMessage());
				cstmt_Redirect_Failure.executeUpdate();
				

			}
			finally
			{
				if(cstmt_Redirect_Failure!=null) cstmt_Redirect_Failure.close();
			}
			
		}
	}
	
	
	
}
