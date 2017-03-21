package com.ibm.ioes.npd.hibernate.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import com.ibm.ioes.npd.beans.ResetPasswordBean;
import com.ibm.ioes.npd.utilities.NpdConnection;
import com.ibm.ioes.npd.utilities.Utility;
import com.ibm.ioes.utilities.DbConnection;

public class ResetPasswordDao extends CommonBaseDao
{
	private static java.util.Random rdom = new java.util.Random();
	public ResetPasswordBean fetchUserDetails(String LoginID)
	{
		ResetPasswordBean Bean=new ResetPasswordBean();
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rsFetchUserDetails = null;
		try
		{
			connection=NpdConnection.getConnectionObject();
			String getUserDetails = null;
			getUserDetails= "select NPDEMPID,PASSWORD FROM NPD.TM_EMPLOYEE";
			
			String whereCondition="";
			String condition;
			
			condition=Utility.generateStringLikeCondition(LoginID, "EMAIL");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			if(!(whereCondition.trim().equals("")))
			{
				getUserDetails=getUserDetails+" WHERE "+whereCondition;
			}
			
			connection.setAutoCommit(false);
			proc=connection.prepareCall(getUserDetails);
			rsFetchUserDetails = proc.executeQuery();
			while(rsFetchUserDetails.next())
			{
				Bean.setEmpNPDID(rsFetchUserDetails.getInt("NPDEMPID"));
				Bean.setExistingPwd(rsFetchUserDetails.getString("PASSWORD"));
			}
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		finally
		{
			try
			{
				DbConnection.closeResultset(rsFetchUserDetails);
				DbConnection.closeCallableStatement(proc);
				DbConnection.freeConnection(connection);
			}
			catch(Exception e)
			{
				e.printStackTrace();	
			}
		}
		return Bean;
	}

	public static String generatePassword()
	{
		String password="";
		String    chars1     = "abcdefghijklmonpqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String    chars2	 = "abcdefghijklmonpqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String    chars3 	 = "0123456789$#_*@~-";
		password=getUniqueID(1,chars1).concat(getUniqueID(7,chars2)).concat(getUniqueID(1,chars3)).concat(getUniqueID(1,chars1));
		return password;
	}
 
 	private static String getUniqueID(int NUM_CHARS, String chars) {  
 		char[] buf = new char[NUM_CHARS]; 
 		for (int i = 0; i < buf.length; i++) {
 			buf[i] = chars.charAt(rdom.nextInt(chars.length()));  
 		} 
 		return new String(buf);  
	}

}
