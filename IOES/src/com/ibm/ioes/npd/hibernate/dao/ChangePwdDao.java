package com.ibm.ioes.npd.hibernate.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;

import com.ibm.ioes.npd.beans.ChangePwdBean;
import com.ibm.ioes.npd.utilities.NpdConnection;
import com.ibm.ioes.utilities.DbConnection;

public class ChangePwdDao extends CommonBaseDao
{
	public boolean updatePassword(String Loginid,String NewPassword,String OldPassword)
	{
		StringBuffer sQuery = new StringBuffer();
		Connection connection=null;
		String Msg="";
		String Msg1="";
		CallableStatement proc =null;
		boolean insert = true;
		try
		{
			connection=NpdConnection.getConnectionObject();
			connection.setAutoCommit(false);
			proc=connection.prepareCall(" {CALL NPD.P_UPDATEPASSWORD(?,?,?,?,?)} ");
			proc.setInt(1, Integer.parseInt(Loginid));
			proc.setString(2, NewPassword);
			proc.setString(3, OldPassword);
			proc.setString(4,"");
			proc.setString(5,"");

			proc.execute();
			
			Msg=proc.getString(5);
			Msg1=proc.getString(4);
			
			if (proc.getString(4).equals("66666")) 
			{
				connection.rollback();
			}	
			else
			{
				connection.commit();
			}
		}
		catch(Exception e)
		{
			insert = false;
			e.printStackTrace();
		}
		finally
		{
			try 
			{
				DbConnection.closeCallableStatement(proc);
				NpdConnection.freeConnection(connection);
			} catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			
			}
		}
		return insert;
	}
}
