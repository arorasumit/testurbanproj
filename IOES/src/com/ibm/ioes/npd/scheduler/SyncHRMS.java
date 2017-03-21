package com.ibm.ioes.npd.scheduler;

import java.io.File;
import java.io.FileFilter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import javax.servlet.ServletException;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

import com.ibm.ioes.npd.hibernate.dao.MyToDOListDaoImpl;
import com.ibm.ioes.npd.hibernate.dao.PlrUploadingDaoImpl;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.CommonUtilities;
import com.ibm.ioes.npd.utilities.Messages;
import com.ibm.ioes.npd.utilities.NpdConnection;
import com.ibm.ioes.utilities.DbConnection;
//import com.ibm.ws.webservices.xml.wassysapp.systemApp;

public class SyncHRMS extends TimerTask{

	@Override
	public void run() 	 {

	System.err.println("Starting Syncing with HRMS Schedular at "+new Date());
	
	Connection conn=null;
	CallableStatement cstmt=null;
	ResultSet rs=null;
	try
	{
			conn=NpdConnection.getConnectionObject();
			
			rs=conn.createStatement().executeQuery("SELECT APPCONFIGID, KEYNAME, KEYVALUE " +
					"FROM NPD.TM_APPCONFIG WHERE KEYNAME='SWITCH_JOB_SYNC_HRMS'");
			if(rs.next())
			{
				if(!rs.getString("KEYVALUE").equalsIgnoreCase(AppConstants.SWITCH_ON))
				{
					String str="Syncing with HRMS Schedular switch is off";
					System.err.println(str);
					AppConstants.NPDLOGGER.error(str);
					return;
					
				}
			}
			
			System.err.println("Syncing with HRMS at "+new Date());
			conn.setAutoCommit(false);
		   cstmt=conn.prepareCall("{call NPD.P_SYNC_HRMS(?,?)}");
		   cstmt.setString(1, "");
		   cstmt.setString(2, "");
		   cstmt.execute();
		   
		   String status=cstmt.getString(1);
		   System.err.println("SYN status:"+status);
		   if("00000".equals(status))
		   {
			   conn.commit();
		   }
		   else
		   {
			   conn.rollback();
			   String str="HMS Sync schedular rolledback at:"+new Date();
			   AppConstants.NPDLOGGER.error(str);
			   System.err.println(str);
		   }

	}
	catch(Exception ex)
	{
		ex.printStackTrace();
		AppConstants.NPDLOGGER.error(ex.getMessage()
				+ " Exception occured in run() method (schedular SyncHRMS) of "
				+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
	}
	finally
	{
		try {
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(cstmt);
			NpdConnection.freeConnection(conn);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	System.err.println(":Synced with HRMS.");
	
	}

	
}
