package com.ibm.ioes.npd.utilities;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.ibm.ioes.npd.exception.NpdException;
import com.ibm.ioes.npd.hibernate.beans.TmEmployee;
import com.ibm.ioes.utilities.DbConnection;

public class CommonUtilities 
{
	public static Date getSystemDate()
	{
		ResultSet rs = null;
		Date sDate=null;
		Connection conn=null;
		try
		{
			conn=NpdConnection.getConnectionObject();
			rs = conn.createStatement().executeQuery("SELECT CURRENT DATE AS CURRENTDATE FROM SYSIBM.SYSDUMMY1");
			while(rs.next())
			{
				sDate = rs.getDate("CURRENTDATE");
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			try {
				DbConnection.closeResultset(rs);
				NpdConnection.freeConnection(conn);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return sDate;
	}
	
	public static String fnCheckNull(Object paramObject)
	{
		String paramValue="";
		try
		{
			if(paramObject != null)
			{
				paramValue = paramObject.toString();
			}
			else
			{
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return paramValue;
	}
	
	public static Date fnDDMMYYYYDate(String sDate)
	{
		Date objDate=null;
        DateFormat objDateFormat = new SimpleDateFormat("dd/MM/yyyy"); 
        try
        {
        	objDate = objDateFormat.parse(sDate); 
        } 
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return objDate;
	}
	
	public static Date fnMMDDYYYYDate(String sDate)
	{
		Date objDate=null;
        DateFormat objDateFormat = new SimpleDateFormat("MM/dd/yyyy"); 
        try
        {
        	objDate = objDateFormat.parse(sDate); 
        } 
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return objDate;
	}
	
	public static Date fnYYYYMMDDDate(String sDate)
	{
		Date objDate=null;
        DateFormat objDateFormat = new SimpleDateFormat("yyyy/MM/dd"); 
        try
        {
        	objDate = objDateFormat.parse(sDate); 
        } 
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return objDate;
	}
	public static boolean isAuthorised( HttpServletRequest request ,  String roleid, TmEmployee tmEmployee)throws NpdException
	{
			Connection connection =null;
			CallableStatement proc =null;
			ResultSet rs = null;
			boolean isAuthorised = false;
			try
			{
				String requestedURI = request.getRequestURI();
				String requestMapping = requestedURI.substring(requestedURI
						.lastIndexOf("/") + 1);
				request.setAttribute(AppConstants.INITIAL_SERVLET_REQUESTED, requestMapping);
					connection=NpdConnection.getConnectionObject();
				
				if("SendSMS.do".equals(requestMapping))
					{
						return true;
					}
				if("createProduct.do".equals(requestMapping) || "attachEditProjectPlan.do".equals(requestMapping))
				{
					ByPass byPass=new ByPass();
					return byPass.isValid(request,roleid,connection,tmEmployee);
				}
					
				  String query=null;
				  query=" SELECT COUNT(1) AS isAuthorised FROM NPD.TM_ACCESSMATRIXMAPPING ";
				  query=query+ " WHERE INTERFACEID = (SELECT INTERFACEID FROM ";
				  query=query+ " NPD.TM_INTERFACEMASTER WHERE INTERACE_MAPPING = '"+ requestMapping +"')";
				  query=query+ " AND ACCESSFLAG=1 AND ROLEID = "+ roleid ;
				
				  connection.setAutoCommit(false);
				  
				  proc=connection.prepareCall(query);
				  rs = proc.executeQuery();
				  while(rs.next())
				  {
					  if(rs.getString("isAuthorised").equalsIgnoreCase("1"))
						  isAuthorised = true; 
				  }
				  
			}
			catch (Exception ex) {
				//logger.error(ex.getMessage();
				throw new NpdException("Exception : " + ex.getMessage(),ex);
			}
			finally
			{
				
				try {
					NpdConnection.freeConnection(connection);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new NpdException("Exception : " + e.getMessage(),e);
				}
			}
			return isAuthorised;
		
	}

	public static boolean isAuthorised(String roleid,String request,String empid)throws NpdException
	{
			Connection connection =null;
			CallableStatement proc =null;
			ResultSet rs = null;
			boolean isAuthorised = false;
			try
			{
				connection=NpdConnection.getConnectionObject();
	
				String query=null;
				query=" SELECT COUNT(1) AS isAuthorised FROM NPD.TM_ACCESSMATRIXMAPPING ";
				query=query+ " WHERE INTERFACEID = (SELECT INTERFACEID FROM ";
				query=query+ " NPD.TM_INTERFACEMASTER WHERE INTERACE_MAPPING = '"+ request +"')";
				query=query+ " AND ACCESSFLAG=1 AND ROLEID = "+ roleid ;
				
				  connection.setAutoCommit(false);
				  
				  proc=connection.prepareCall(query);
				  rs = proc.executeQuery();
				  while(rs.next())
				  {
					  if(rs.getString("isAuthorised").equalsIgnoreCase("1"))
						  isAuthorised = true; 
				  }
				  
			}
			catch (Exception ex) {
				//logger.error(ex.getMessage();
				throw new NpdException("Exception : " + ex.getMessage(),ex);
			}
			finally
			{
				
				try {
					DbConnection.closeResultset(rs);
					DbConnection.closeCallableStatement(proc);
					NpdConnection.freeConnection(connection);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new NpdException("Exception : " + e.getMessage(),e);
				}
			}
			return isAuthorised;
		
	}
	
	public static boolean passwordCheck(String usrname,String pass)
	{
		int j=3;
		boolean flag=true;
		String passSub="";

		for(int i=0;i<pass.length()-2 && flag;i++)
		{
			passSub=pass.substring(i,j++);
	
			if(usrname.indexOf(passSub)!=-1)
			{
				flag=false;
			}	
		}
		return flag;
	}

}
