package com.ibm.ioes.npd.hibernate.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import javax.sql.rowset.serial.SerialBlob;

import org.apache.struts.upload.FormFile;

import com.ibm.ioes.npd.beans.UpdateMobileNumberBean;
import com.ibm.ioes.npd.hibernate.beans.TmEmployee;
import com.ibm.ioes.npd.hibernate.beans.UpdateMobileNumberDto;
import com.ibm.ioes.npd.utilities.NpdConnection;
import com.ibm.ioes.utilities.DbConnection;

public class UpdateMobileNumberDao extends CommonBaseDao
{
	
	public String fetchMobileNumber(TmEmployee employeebean,UpdateMobileNumberBean bean)
	{	
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rsViewMobileList = null;
		UpdateMobileNumberDto dto=new UpdateMobileNumberDto();
		String mobNumber = null;
		try
		{
			connection=NpdConnection.getConnectionObject();
			String getMobileNumber = null;
			getMobileNumber= "select MOBILE_NO FROM NPD.TM_EMPLOYEE WHERE NPDEMPID="+employeebean.getNpdempid();
			
			//System.err.println("Query:"+getMobileNumber);
			connection.setAutoCommit(false);
			proc=connection.prepareCall(getMobileNumber);
			rsViewMobileList = proc.executeQuery();
			while(rsViewMobileList.next())
			{
				mobNumber=rsViewMobileList.getString("MOBILE_NO");
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
				DbConnection.closeResultset(rsViewMobileList);
				DbConnection.closeCallableStatement(proc);
				DbConnection.freeConnection(connection);
			}
			catch(Exception e)
			{
				e.printStackTrace();	
			}
		}
		return mobNumber;
	}
	
	public boolean updateMobileNumber(String mobileNumber,String empID)
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
			proc=connection.prepareCall(" {CALL NPD.P_UPDATE_MOBILENUMBER(?,?,?,?)} ");
			proc.setString(1, mobileNumber);
			proc.setString(2, empID);			
			proc.setString(3,"");
			proc.setString(4,"");
	
			proc.execute();
			
			Msg=proc.getString(4);
			Msg1=proc.getString(3);
			 
			if(proc.getString(3).equals("66666"))
			{
				connection.rollback();	
			}
			else
			{
				connection.commit();
			}
		}
		catch(Exception EX)
		{
			insert = false;
			EX.printStackTrace();
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
