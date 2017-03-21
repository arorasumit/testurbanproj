package com.ibm.ioes.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.CallableStatement;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DbConnection {
	private static final String DATASOURCE_NAME = "jdbc/ioes";

	private static InitialContext initialContext;

	private static DataSource dataSource = null;

	
	private static final String DATASOURCE_NAME_REPORTS = "jdbc/IOES_REPORTS";
	private static InitialContext initialContext_REPORTS;
	private static DataSource dataSource_REPORTS = null;

	

	public static Connection getConnectionObject() throws Exception {
		Connection conn = null;
				/*DriverManager.registerDriver(new com.ibm.db2.jcc.DB2Driver());
				conn = DriverManager.getConnection("jdbc:db2://10.5.153.243:60004/IOES", "a1448525","jun@2013");
				int a=1;
				if(a==1) return conn;*/
				
		
		try {
			
			StackTraceElement[] stackTraces = Thread.currentThread().getStackTrace();
			int i=0;
			for(StackTraceElement stackTraceElement : stackTraces){
				i++;
				if(stackTraceElement.getClassName().equalsIgnoreCase("com.ibm.ioes.utilities.DbConnection")){
					break;
				}
			}
			if (dataSource == null) {
				// logger.error("Datasource object is null. Performing
				// InitialContext Lookup.");
				//System.out.println(" DATASOURCE_NAME 11111:"+DATASOURCE_NAME);
				initialContext = new InitialContext();
				//System.out.println(" DATASOURCE_NAME 22222:"+DATASOURCE_NAME);
				dataSource = (DataSource) initialContext
						.lookup(DATASOURCE_NAME);
				//System.out.println(" DATASOURCE_NAME 33333:"+DATASOURCE_NAME);
			}
			//System.out.println("Getting connection :"+DATASOURCE_NAME);
			conn = dataSource.getConnection();
			if(conn== null)System.out.println("connection null");
			TestConnection.putInStore(conn,
					stackTraces[i].getClassName() + "-" + stackTraces[i].getMethodName() + "-" + stackTraces[i].getLineNumber());
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return conn;
	}
	
		public static Connection getReportsConnectionObject() throws Exception {
		Connection conn = null;
		
		/*conn = DriverManager.getConnection("jdbc:db2://10.14.51.235:60000/IOES", "ib2bdbus","cfeg@2012");
		int a=1;
		if(a==1) return conn;*/
		StackTraceElement[] stackTraces = Thread.currentThread().getStackTrace();
		int i=0;
		for(StackTraceElement stackTraceElement : stackTraces){
			i++;
			if(stackTraceElement.getClassName().equalsIgnoreCase("com.ibm.ioes.utilities.DbConnection")){
				break;
			}
		}
		try {
			if (dataSource_REPORTS == null) {
				// logger.error("Datasource object is null. Performing
				// InitialContext Lookup.");
				initialContext_REPORTS = new InitialContext();
				dataSource_REPORTS = (DataSource) initialContext_REPORTS
						.lookup(DATASOURCE_NAME_REPORTS);
				
			}
			conn = dataSource_REPORTS.getConnection();
			if(conn== null)System.out.println("connection null");
			TestConnection.putInStore(conn,
					stackTraces[i].getClassName() + "-" + stackTraces[i].getMethodName() + "-" + stackTraces[i].getLineNumber());
			// 
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return conn;
	}
	
	public static void freeConnection(Connection conn) throws Exception {
		try {
			if (conn != null) {
				if(!conn.isClosed()){
				conn.close();
				TestConnection.removeFromStore(conn);
				}
				//
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public static void closeStatement(Statement statement) {
		try {
			if (statement != null) {
				statement.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void closePreparedStatement(PreparedStatement preparedStatement) throws Exception {
		try {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public static void closeCallableStatement(CallableStatement statement) {
		try {
			if (statement != null) {
				statement.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void closeResultset(ResultSet resultSet) throws Exception {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}


}
