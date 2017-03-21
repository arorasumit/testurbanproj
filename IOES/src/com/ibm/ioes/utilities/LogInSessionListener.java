package com.ibm.ioes.utilities;

import java.sql.CallableStatement;
import java.sql.Connection;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;




public class LogInSessionListener implements HttpSessionListener {
	ServletContext context;
	public static String sqlUpdateLoginStatus="{call IOE.SP_UPDATE_LOGIN_STATUS(?,?,?,?,?,?,?)}";	
	/*public LogInSessionListener(ServletContext context) {
		this.context = context;
		//context.setAttribute(AppConstants.APP_SESSION, new HashMap<String, HttpSession>());
	}*/
	
	
	public void sessionDestroyed(HttpSessionEvent sce) {
		HttpSession session = sce.getSession();
		try {
			//updateStatus(); // Code For Updating User status			
			/*ServletContext ctx=session.getServletContext();
			HashMap<String, HttpSession> htMap = (HashMap<String, HttpSession>) ctx.getAttribute(AppConstants.APP_SESSION);
			HttpSession sessionObj=htMap.get(session.getId());
			htMap.remove(session.getId());			
			//System.err.println("Your Session has been expired due to time out!!");			
			*/
			System.err.println(" Session Closed By User Clicking On Logout");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public void sessionCreated(HttpSessionEvent sce) {
		HttpSession session = sce.getSession();
		try
		{
			/*ServletContext ctx=session.getServletContext();
			HashMap<String, HttpSession> htMap = (HashMap<String, HttpSession>) ctx.getAttribute(AppConstants.APP_SESSION);
			HttpSession sessionObj=htMap.get(session.getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			System.err.println(objUserDto.getUserId());

			updateLoginStatus( objUserDto.getUserId() , new Long(objUserDto.getUserRoleId()),"0","0");			
			System.err.println("Session is created...");*/
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}
	 //update login status while user sign in or sign out as well as session automatically expired
	public void updateLoginStatus(String userid, long roleid,String isLogin,String setStatus)
	{
		
		System.err.println("Update Login Status Of User in Database");
		Connection connection =null;
		CallableStatement proc =null;		
		boolean isSuccess=false;
		try
		{
			connection = DbConnection.getConnectionObject();
			connection.setAutoCommit(false);		
	    
			proc=connection.prepareCall(sqlUpdateLoginStatus);
			proc.setLong(1, roleid);
			proc.setString(2, isLogin);		
			proc.setString(3, setStatus);
			proc.setInt(4, 0);
			proc.setInt(5, 0);
			proc.setString(6,"");
			proc.setString(7,userid);
			
			proc.execute();
			if(proc.getInt(5)!=0 && proc.getInt(5)!=100)
			{
				isSuccess=false;
				connection.rollback();
			}
			else
			{
				isSuccess=true;
				connection.commit();
			}
		  
		}
		catch(Exception e){
			System.err.println(e.getMessage());						
		}
		finally
		{
			
			try {
				DbConnection.closeCallableStatement(proc);
				DbConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//throw new IOESException("Exception : " + e.getMessage(),e);
			}
		}
	}
	
}
