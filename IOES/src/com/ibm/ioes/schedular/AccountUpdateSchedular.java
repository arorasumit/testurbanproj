package com.ibm.ioes.schedular;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.ibm.fx.dto.ExtendedData;
import com.ibm.fx.dto.AcctDTO;
import com.ibm.fx.mq.IOESKenanManager;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Utility;

public class AccountUpdateSchedular {
	
	private String sqlFX_NO_SCH_GETACCOUNT_UPDATE_DATA = "call IOE.FX_NO_SCH_GETACCOUNT_UPDATE_DATA(?)";
	
	private String sqlFX_SCHEDULER_UPDATE_STATUS="call IOE.FX_SCHEDULER_UPDATE_STATUS_FOR_ACCOUNTUPDATE(?,?,?,?)";
	private String sqlFX_UPDATE_FAILED_TRANSACTION = "UPDATE IOE.TFX_ACCOUNTUPDATE SET PROCESSING_STATUS=1  WHERE PROCESSING_STATUS IN (4,5) ";

	/**	
	*	This function pushes today's or backdated Accounts to FX for Change Order
	*/
	public void pushUpdatedAccountsToFX(long orderno)
	{
		try
		{
			IOESKenanManager API= null ;
			Utility.LOG(true, true, " IN  pushAccountsUpdateToFX()");
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
				AcctDTO account = null;
				do
				{
					//fetch one account
					try
					{
						account = fetchNextAccountForUpdation(connection,orderno);
						connection.commit();
					}
					catch(Exception ex)
					{
						Utility.LOG(true,true,ex,null);
						connection.commit();
						//logExceptionForAccount(account,ex);//--Later
						continue;
					}
					if(account!=null) 
					{ 
						//push to FX 
						String logIdentifier="IN AccountUpdateSchedular() for OrderNo "+account.getOrderNo()+" and Acc External ID"+account.getAcctExternalId() +"\n";
						
						API.updateAccount(account,logIdentifier);
						//save results	
						try
						{	saveAccountPushResult(connection,account);
							connection.commit();
						}
						catch(Exception ex)
						{
							String msg=logIdentifier+"Exception in Account Update Schedular "+account.getException_message();
							Utility.LOG(true,true,ex,msg);
							connection.rollback();
						}
					}
				}while(account!=null);

			}
			finally 
			{
				try
				{
					if(connection!=null)
					{
						connection.close();
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
	{
		 try {
			PreparedStatement pstmt=connection.prepareStatement(sqlFX_UPDATE_FAILED_TRANSACTION);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	AcctDTO fetchNextAccountForUpdation(Connection connection,long Orderno) throws Exception
	{
		AcctDTO acctDto=null;
		
		CallableStatement cstmt_Account = null;
		
		CallableStatement cstmt_AccountExtendedData = null;
		try
		{
			cstmt_Account=connection.prepareCall(sqlFX_NO_SCH_GETACCOUNT_UPDATE_DATA);
			cstmt_Account.setLong(1, Orderno);
			ResultSet rs=cstmt_Account.executeQuery();
			if(rs.next())
			{
				acctDto=new AcctDTO();
				acctDto.setId(rs.getLong("ID"));
				acctDto.setAcctExternalId(rs.getString("ACCEXTERNALID"));
				acctDto.setOrderNo(rs.getInt("ORDERNO"));
				
				ArrayList<ExtendedData> extendedData = Utility.getFxExtendedData(connection,acctDto.getId(),ExtendedData.AssociatedWith_ACCOUNTUPDATE);
				acctDto.setExtendedData(extendedData);
			}
		}
		finally
		{
			if(cstmt_Account!=null) cstmt_Account.close();
			
			if(cstmt_AccountExtendedData!=null) cstmt_AccountExtendedData.close();
		}
		return acctDto;
	}

	public void saveAccountPushResult(Connection connection,AcctDTO account) throws Exception
	{
			CallableStatement cstmt_Account_Status = null;
			//account.setException_message("Manual Exception_message has been added");
			//account.setReturnStatus(2);
			try
			{
				cstmt_Account_Status=connection.prepareCall(sqlFX_SCHEDULER_UPDATE_STATUS);
				cstmt_Account_Status.setLong(1, account.getId());
				cstmt_Account_Status.setInt(2, account.getReturnStatus());
				cstmt_Account_Status.setNull(3,java.sql.Types.CLOB);
				cstmt_Account_Status.setNull(4,java.sql.Types.VARCHAR);
				
				if(account.getReturnStatus()!=1)
				{
					if(account.getException()!=null)
					{
					java.sql.Clob clobData = 
						com.ibm.db2.jcc.t2zos.DB2LobFactory.createClob(Utility.getStackTrace(account.getException()));
						cstmt_Account_Status.setClob(3, clobData );
					}
					
					cstmt_Account_Status.setString(4, account.getException_message());
				}
				cstmt_Account_Status.executeUpdate();
			}
			finally
			{
				if(cstmt_Account_Status!=null) cstmt_Account_Status.close();
			}
	}

	
	
}
