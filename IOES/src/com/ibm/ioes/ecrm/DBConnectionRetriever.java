package com.ibm.ioes.ecrm;

import java.io.PrintStream;
import java.sql.*;
import org.apache.log4j.Logger;

import com.ibm.ioes.utilities.Messages;
import com.ibm.ioes.utilities.Utility;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

public class DBConnectionRetriever
{

	private static final String CRM_POOL = "crm";
	protected Logger logger;
	private Connection conn;
	private PreparedStatement stmtUpdate;

	public DBConnectionRetriever()
	{
		logger = null;
		conn = null;
		stmtUpdate = null;
	}

	public Connection getCRMConnection()
	{
		CRMLogger.SysErr("Getting Connection.....");
		Connection con = null;
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			CRMLogger.SysErr("Loading Oracle Driver.....");
			String keyname = Messages.getMessageValue("PASSWORD_ENCRYPT_KEY");
			String CRM_APP_PASSWORD=Utility.getDecryptedPassword(keyname,"CRM_APP_PASSWORD");
			
			//con = DriverManager.getConnection("jdbc:oracle:thin:@10.14.7.19:1521:ecrmp4", "noapps", "changed#123");
			//con = DriverManager.getConnection("jdbc:oracle:thin:@10.14.5.100:1526:AESCRMP1", "ib2b_crm", CRM_APP_PASSWORD);
			//con = DriverManager.getConnection("jdbc:oracle:thin:@10.13.41.29:1531:AESCRMP", "apps", CRM_APP_PASSWORD);			 
			//con = DriverManager.getConnection("jdbc:oracle:thin:@(DESCRIPTION=(LOAD_BALANCE=YES)(FAILOVER=YES)(ADDRESS_LIST=(ADDRESS=(PROTOCOL=tcp)(HOST=CNDAB2BCDBOP01-v.airtelworld.in)(PORT=1521))(ADDRESS=(PROTOCOL=tcp)(HOST=CNDAB2BCDBOP02-v.airtelworld.in)(PORT=1521)))(CONNECT_DATA=(SERVICE_NAME=AESCRMP)))", "apps", "tiger#123");
			//con = DriverManager.getConnection("jdbc:oracle:thin:@(DESCRIPTION=(LOAD_BALANCE=YES)(FAILOVER=YES)(ADDRESS_LIST=(ADDRESS=(PROTOCOL=tcp)(HOST=CNDAB2BCDBOP01-v.airtelworld.in)(PORT=1521))(ADDRESS=(PROTOCOL=tcp)(HOST=CNDAB2BCDBOP02-v.airtelworld.in)(PORT=1521)))(CONNECT_DATA=(SERVICE_NAME=AESCRMP)))", "noapps", "changed#123");
		}
		catch(SQLException e)
		{
			CRMLogger.SysErr(e.getMessage() + "ERROR in connection");
			e.printStackTrace();
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage() + "ERROR in connection");
			e.printStackTrace();
		}
		CRMLogger.SysErr("Done.....");
		return con;
	}

	public Connection getCRMConnection2()
	{
		System.out.println("Getting Connection...in conn2..");
		Connection con = null;
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("Getting Connection..in conn2...");
			con = DriverManager.getConnection("jdbc:oracle:thin:@(DESCRIPTION=(LOAD_BALANCE=YES)(FAILOVER=YES)(ADDRESS_LIST=(ADDRESS=(PROTOCOL=tcp)(HOST=CNDAB2BCDBOP01-v.airtelworld.in)(PORT=1521))(ADDRESS=(PROTOCOL=tcp)(HOST=CNDAB2BCDBOP02-v.airtelworld.in)(PORT=1521)))(CONNECT_DATA=(SERVICE_NAME=AESCRMP)))", "noapps", "changed#123");
			//con = DriverManager.getConnection("jdbc:oracle:thin:@10.13.41.96:1521:TRNG", "apps", "apps");
			System.out.println("Getting Connection..in conn2...");
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage() + "ERROR in connection");
			e.printStackTrace();
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		System.out.println("Done.....");
		return con;
	}

	public Connection getSQLConnection()
	{
		System.out.println("Getting Connection...in SQLServer..");
		Connection con = null;
		try
		{
			Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
		
		con = DriverManager.getConnection("jdbc:microsoft:sqlserver://10.14.6.135:4445;database=BillingSoftware_ProjectMana" +
		"gement;User=crmmap;Password=crmmap123"
		);
		
		/*
		con = DriverManager.getConnection("jdbc:microsoft:sqlserver://172.26.5.18:1433;database=BillingSoftware_ProjectMana" +
		"gement;User=crmmap;Password=crmmap123"
		);
		*/		
		/*
		con = DriverManager.getConnection("jdbc:microsoft:sqlserver://10.6.72.1:4445;database=BillingSoftware_ProjectManagement_STSNIO;" +
		"User=crmuser;Password=crm#123");
		*/
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage() + "ERROR in connection");
			e.printStackTrace();
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		System.out.println("Done.....");
		return con;
	}
	
	public static Connection getSqLServerConnection(){
		Connection con = null;
		try {
				// Establish the connection. 
				String keyname = Messages.getMessageValue("PASSWORD_ENCRYPT_KEY");
				String LEPM_APP_PASSWORD=Utility.getDecryptedPassword(keyname,"LEPM_APP_PASSWORD");
				SQLServerDataSource ds = new SQLServerDataSource();
				ds.setIntegratedSecurity(false);
				ds.setServerName("10.13.5.119");
				ds.setPortNumber(9443); 
				ds.setDatabaseName("dbLEPM");
				ds.setUser("ib2blepm");
				ds.setPassword(LEPM_APP_PASSWORD);//"nov@2013"
				con = ds.getConnection();
				System.out.println("SQL Server DB Connected");		        	
	        }		
	    	catch (Exception e) {
	    		e.printStackTrace();
	    	}
	    	return con;
	}
}
