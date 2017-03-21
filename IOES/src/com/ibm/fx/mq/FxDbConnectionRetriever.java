package com.ibm.fx.mq;

import java.io.PrintStream;
import java.sql.*;

import org.apache.log4j.Logger;

import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Messages;
import com.ibm.ioes.utilities.Utility;

public class FxDbConnectionRetriever
{

	
	
	public static Connection getFxDbConnection() throws Exception
	{
		System.out.println("Getting FX Connection.....");
		Connection fx_conn = null;
		Connection ib2b_Db_connection=null;
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			ib2b_Db_connection= DbConnection.getConnectionObject();
			String fx_db_username=Messages.getMessageValueWithEnvironment("FX_DB_USERNAME");//Utility.getAppConfigValue(ib2b_Db_connection, "FX_DB_USERNAME");
			//String fx_db_password=Utility.getAppConfigValue(ib2b_Db_connection, "FX_DB_PASSWORD");
			String keyname = Messages.getMessageValue("PASSWORD_ENCRYPT_KEY");
			String fx_db_password=Utility.getDecryptedPassword(keyname,"FX_DB_PASSWORD");
			//fx_db_password="arbormig";
			String fx_db_sid=Messages.getMessageValueWithEnvironment("FX_DB_SID");//Utility.getAppConfigValue(ib2b_Db_connection, "FX_DB_SID");
			String fx_db_ip=Messages.getMessageValueWithEnvironment("FX_DB_IP");//Utility.getAppConfigValue(ib2b_Db_connection, "FX_DB_IP");
			String fx_db_port=Messages.getMessageValueWithEnvironment("FX_DB_PORT");//Utility.getAppConfigValue(ib2b_Db_connection, "FX_DB_PORT");

/*			ib2b_Db_connection= DbConnection.getConnectionObject();
			//String fx_db_username=Messages.getMessageValueWithEnvironment("FX_DB_USERNAME");//Utility.getAppConfigValue(ib2b_Db_connection, "FX_DB_USERNAME");
			String fx_db_username="AESMIG";
			//String fx_db_password=Utility.getAppConfigValue(ib2b_Db_connection, "FX_DB_PASSWORD");
			String keyname = Messages.getMessageValue("PASSWORD_ENCRYPT_KEY");
			String fx_db_password=Utility.getDecryptedPassword(keyname,"FX_DB_PASSWORD");
			//fx_db_password="arbormig";//91 password
			fx_db_password="jan#2013";//90 password
			//String fx_db_sid=Messages.getMessageValueWithEnvironment("FX_DB_SID");//Utility.getAppConfigValue(ib2b_Db_connection, "FX_DB_SID");
			//String fx_db_ip=Messages.getMessageValueWithEnvironment("FX_DB_IP");//Utility.getAppConfigValue(ib2b_Db_connection, "FX_DB_IP");
			//String fx_db_port=Messages.getMessageValueWithEnvironment("FX_DB_PORT");//Utility.getAppConfigValue(ib2b_Db_connection, "FX_DB_PORT");
			String fx_db_sid="FXLDCUS";//Utility.getAppConfigValue(ib2b_Db_connection, "FX_DB_SID");
			String fx_db_ip="10.13.41.90";//Utility.getAppConfigValue(ib2b_Db_connection, "FX_DB_IP");
			String fx_db_port="1526";//Utility.getAppConfigValue(ib2b_Db_connection, "FX_DB_PORT");
	*/
			fx_conn = DriverManager.getConnection("jdbc:oracle:thin:@"+fx_db_ip+":"+fx_db_port+":"+fx_db_sid, fx_db_username, fx_db_password);


		}
		catch(Exception e)
		{
			System.out.println(e.getMessage() + "ERROR in FX connection");
			e.printStackTrace();
			throw e;
		}finally
		{
			try {
				DbConnection.freeConnection(ib2b_Db_connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw e;
			}
		}
		System.out.println("FX Done.....");
		return fx_conn;
	}
	
	public static void freeConnection(Connection conn) throws Exception {
		try {
			if (conn != null && conn.isClosed() == false) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
}
