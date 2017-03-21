package com.ibm.ioes.npd.utilities;

import java.sql.*;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.TestConnection;

public class NpdConnection {
	private static final String DATASOURCE_NAME = "jdbc/ioes";
	
	private static final String IOES_DATASOURCE = "jdbc/ioes";
	private static InitialContext ioesinitialContext;
	private static DataSource ioesdataSource = null;
	
	private static InitialContext initialContext;

	private static DataSource dataSource = null;

	private static Connection connection;

	private static Statement statement;

	private static ResultSet resultSet;
	// RT
	static {

		// logger = Logger.getLogger(TransactionProcessFormBean.class);
	}

	public static Connection getConnectionObject() throws Exception {
		Connection conn = null;
		try {
			StackTraceElement[] stackTraces = Thread.currentThread().getStackTrace();
			int i=0;
			for(StackTraceElement stackTraceElement : stackTraces){
				i++;
				if(stackTraceElement.getClassName().equalsIgnoreCase("com.ibm.ioes.npd.utilities.NpdConnection")){
					break;
				}
			}
			if (dataSource == null) {
				// logger.error("Datasource object is null. Performing
				// InitialContext Lookup.");
				initialContext = new InitialContext();
				dataSource = (DataSource) initialContext
						.lookup(DATASOURCE_NAME);
			}
			conn = dataSource.getConnection();
			TestConnection.putInStore(conn,
					stackTraces[i].getClassName() + "-" + stackTraces[i].getMethodName() + "-" + stackTraces[i].getLineNumber());
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return conn;
	}

	public static Connection getIOESConnectionObject() throws Exception {
		Connection conn = null;
		try {
			StackTraceElement[] stackTraces = Thread.currentThread().getStackTrace();
			int i=0;
			for(StackTraceElement stackTraceElement : stackTraces){
				i++;
				if(stackTraceElement.getClassName().equalsIgnoreCase("com.ibm.ioes.npd.utilities.NpdConnection")){
					break;
				}
			}
			if (ioesdataSource == null) {
				// logger.error("Datasource object is null. Performing
				// InitialContext Lookup.");
			ioesinitialContext= new InitialContext();
				ioesdataSource = (DataSource) ioesinitialContext
						.lookup(IOES_DATASOURCE);
			}
			conn = ioesdataSource.getConnection();
			TestConnection.putInStore(conn,
					stackTraces[i].getClassName() + "-" + stackTraces[i].getMethodName() + "-" + stackTraces[i].getLineNumber());
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return conn;
	}
	
	public static void freeConnection(Connection conn) throws Exception {
		try {
			if (conn != null && conn.isClosed() == false) {
				conn.close();
				TestConnection.removeFromStore(conn);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public static void closePreparedStatement(
			PreparedStatement preparedStatement) throws Exception {
		try {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
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

	public static ResultSet getResultSet111(String query) throws Exception {
		try {
			connection = getConnectionObject();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
		} catch (SQLException se) {
			se.printStackTrace();
			throw se;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return resultSet;
	}

	public static int executeUpdate111(String query) throws Exception {
		int update = 0;
		try {
			connection = getConnectionObject();
			statement = connection.createStatement();
			update = statement.executeUpdate(query);
		} catch (SQLException se) {
			se.printStackTrace();
			throw se;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return update;
	}
}
