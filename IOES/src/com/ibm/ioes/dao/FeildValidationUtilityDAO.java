package com.ibm.ioes.dao;
//Tag   Name Resource Name  Date		 CSR No			Description
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Utility;
public class FeildValidationUtilityDAO {
	


	protected static final Logger log = AppConstants.IOES_LOGGER;
	final static String className="FeildValidationUtilityDAO";
	
	public static String sqlGetValidateFeild="{call IOE.ROLEWISE_VALIDATE_FEILDS(?,?,?,?,?,?)}";
	public String feildValidation(long orderNo,int RoleId) throws Exception
	{
//		Added by Nagarjuna
		String methodName="feildValidation",  msg="";
		boolean logToFile=true, logToConsole=true;
		//End Nagarjuna
		Connection connection =null;
		CallableStatement callstmt =null;
		String str=null;
		try
		{
			connection=DbConnection.getConnectionObject();
			connection.setAutoCommit(false);
			callstmt= connection.prepareCall(sqlGetValidateFeild);	
			callstmt.setLong(1, orderNo);	
			callstmt.setString(2,null );	
			callstmt.setString(3, null);	
			callstmt.setString(4, null);
			callstmt.setString(5, null);
			callstmt.setInt(6, RoleId);
			callstmt.executeUpdate();
			str=callstmt.getString(2);
		}
		catch(Exception ex )
		{
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, str, logToFile, logToConsole);//added by nagarjuna 
			connection.rollback();
			//ex.printStackTrace();	
		}
		finally
		{
			try 
			{
				DbConnection.closeCallableStatement(callstmt);
				DbConnection.freeConnection(connection);
			} 
			catch (SQLException e) 
			{
				Utility.LOG(true,true,"error while closing connection   : "+e);//added by Nagarjuna
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		return str;
	}

}
