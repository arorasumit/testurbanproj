package com.ibm.fx.mq;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.Map;


import com.ibm.fx.dto.UpdateAccountDto;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Utility;

public class FXSchedulerForAccountUpdate {

	
	private static final  String SQL_UPDATECHILDACCT = "call ioe.FX_UPDATE_PARENT_N_CHILD_ACCNT()";
	
	
	private String sqlFX_SCHEDULER_UPDATE_STATUS     = "call IOE.FX_SCHEDULER_UPDATE_ACCT_STATUS(?,?,?,?)";
	
	private String sqlFX_GET_ACCNT_UPDATE_DATA		 = "CALL IOE.FX_GET_ACCNT_UPDATE_DATA()";
	/**
	 * @param args
	 */
	public  void updateAccount() {
		
		
		try{
			
		
			Connection conn = null;
			IOESKenanManager API= null ;
			UpdateAccountDto updateAccount = null;
			String logIdentifier = null;
			
				
			try {	
				API=new IOESKenanManager();
				API.loginKenan();
				conn = DbConnection.getConnectionObject();
				updateChildAccount(conn);// update child account and parent account
				conn.setAutoCommit(false);
				
				do {
					try
					{
						updateAccount = fetchNextAccount(conn);
						conn.commit();
					}
					catch(Exception ex)
					{
						Utility.LOG(true,true,ex,null);
						conn.commit();
						//logExceptionForService(service,ex);//--Later
						continue;
					}
					if(updateAccount!=null) 
					{ 					
						//push to FX
						logIdentifier ="Update External Account No: "+updateAccount.getAcctExternalId()+"  SCHEDULAR ERRROR" +"\n";
						API.updateAccountData(updateAccount,logIdentifier);					
						//save results	
						try
						{	saveUpdateAccountResult(conn,updateAccount);
							conn.commit();
						}
						catch(Exception ex)
						{	
							String msg = logIdentifier +"Exception in saving result of Updating Account no  "+updateAccount.getAcctExternalId();
							Utility.LOG(true,true,ex,msg);
							conn.rollback();
						}
					}			
					
				}while(updateAccount!=null);
							
			}finally
			{
				try
				{
					if(conn!=null)
					{
						DbConnection.freeConnection(conn);
					}
				}
				catch(Exception ex)
				{
					Utility.LOG(true,true,ex,null);
				}
				API.close();
			}
		}catch (Throwable th){
			Utility.LOG(true,true,new Exception(th),null);
		}
	}
	
	
	public void saveUpdateAccountResult(Connection connection,UpdateAccountDto updateAccount) throws Exception
	{
		
				
			CallableStatement cs = null;
			try
			{
				cs=connection.prepareCall(sqlFX_SCHEDULER_UPDATE_STATUS);
				cs.setLong(1, updateAccount.getId());
				cs.setInt(2, updateAccount.getReturnStatus());
				cs.setNull(3,java.sql.Types.CLOB);
				cs.setNull(4,java.sql.Types.VARCHAR);

				if(updateAccount.getReturnStatus()!=1)
				{
					if(updateAccount.getException()!=null)
					{
					java.sql.Clob clobData = 
						com.ibm.db2.jcc.t2zos.DB2LobFactory.createClob(Utility.getStackTrace(updateAccount.getException()));
					cs.setClob(3, clobData );
					}
					
					cs.setString(4, updateAccount.getException_message());
				}
				cs.executeUpdate();

			}
			finally
			{
				if(cs!=null)DbConnection.closeCallableStatement(cs) ;
			}			
	}
		
	
	public static void updateChildAccount(Connection conn) {
		
		PreparedStatement ps = null;
		try {
			
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(SQL_UPDATECHILDACCT);
			ps.executeUpdate();
			conn.commit();
			
		}catch(SQLException e) {
			Utility.LOG(true,true,e,null);
			
		}finally {
			if(ps!=null)
				try {
					DbConnection.closePreparedStatement(ps);
				}catch(Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
	}
	
	UpdateAccountDto fetchNextAccount(Connection conn) throws Exception
	{
		UpdateAccountDto updateAccount  =null;		
		CallableStatement cstmt_Account = null;		
		ResultSet rs = null;
		Map extendedData = null;
		
		
		try
		{
			cstmt_Account=conn.prepareCall(sqlFX_GET_ACCNT_UPDATE_DATA);			
			rs=cstmt_Account.executeQuery();
			if(rs.next())
			{
				updateAccount = new UpdateAccountDto();
				updateAccount.setId(rs.getLong("ID"));
				updateAccount.setAcctExternalId(rs.getString("ACCEXTERNALID"));
				updateAccount.setAccountCaegory(rs.getString("FX_ACCOUNT_CATEGORYID"));				
				updateAccount.setPartyName(rs.getString("ACCOUNTNAME"));
				updateAccount.setRegionId(rs.getString("FX_ACC_SEGMENTID"));
				extendedData = new HashMap();
				extendedData.put(1007, rs.getInt("FX_ORG_TYPE_ENUM_ID"));
				extendedData.put(1017, rs.getString("FX_BUSINESS_SEGMENT_ENUM_ID"));
							
				updateAccount.setExtendedData(extendedData);
			}
		}
		finally
		{
			if(cstmt_Account!=null) DbConnection.closePreparedStatement(cstmt_Account);				
		}
		return updateAccount;
	}
	
	
}
