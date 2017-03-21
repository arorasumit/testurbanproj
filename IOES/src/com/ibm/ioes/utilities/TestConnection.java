package com.ibm.ioes.utilities;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class TestConnection {
	
	private long creationTimestamp;
		
	static public Map<Connection,String> connectionStore=Collections.synchronizedMap(new LinkedHashMap<Connection, String>());

	public TestConnection(StackTraceElement[] stackTraceElement,long creationTimestamp) {
		this.creationTimestamp=creationTimestamp;
	}
	
	public TestConnection() {
	
	}
	

	public long getCreationTimestamp() {
		return creationTimestamp;
	}



	public void setCreationTimestamp(long creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}
	
	public static void main(String[] args) {
		new TestConnection().func1();
	}
	
	void func1()
	{
		func2();
	}
	void func2()
	{
		Throwable th=new Throwable();
		th.printStackTrace();
		System.out.println("Length :"+th.getStackTrace().length);
		System.out.println("Length :"+th.getStackTrace()[0]);
		System.out.println("Length :"+th.getStackTrace()[1]);
		System.out.println("Length :"+th.getStackTrace()[2]);
		System.out.println("SRSR :"+th.getStackTrace());
	}
	private static String[] getConnectionCreationDetails(String connString){
		if(null == connString)
			return null;
		String[] connStringDetails = connString.split("-");
		if(connStringDetails.length != 4){
			return null;
		}else{
			return connStringDetails;
		}
	}
	static public void terminateAllActiveConnections(long oldTime)
	{
		ArrayList<Connection> keys= new ArrayList<Connection>(connectionStore.keySet());
		try {
		for (Connection connection : keys) {
			String temp=connectionStore.get(connection);
			String[] connCreationDetails = getConnectionCreationDetails(temp);
			if(null == connCreationDetails || connCreationDetails.length != 4){
				System.err.println("Error in connection termination, No connection terminated");
			}else{
				String connCreationTime = connCreationDetails[3];
				if(Long.valueOf(connCreationTime)<(System.currentTimeMillis()-oldTime)){
					connection.close();
					connectionStore.remove(connection);
					System.out.println("Connection Discarded : Creation Time:"+new Timestamp(Long.valueOf(connCreationTime)));
					System.out.println("Discarded connection created by Class - " + connCreationDetails[0] + " in " +
							"Method - " + connCreationDetails[1] + " at Line no - " + connCreationDetails[2]);
				/*System.out.println("\t\t Stacktrace :");
				int count=0;
				for (StackTraceElement stackTraceElement : temp.getStackTraceElement()) {
					count++;
					if(count>i){
						break;
					}
					System.out.println("\t\t\t\t"+stackTraceElement);
				}*/
				}
			}
		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static public void printAllActiveConnection(long oldTime){
		ArrayList<Connection> keys= new ArrayList<Connection>(connectionStore.keySet());
		for (Connection connection : keys) {
			String temp=connectionStore.get(connection);
			String[] connCreationDetails = getConnectionCreationDetails(temp);
			if(null == connCreationDetails || connCreationDetails.length != 4){
				System.err.println("Error in connection termination, No connection terminated");
				return;
			}else{
				String connCreationTime = connCreationDetails[3];
				if(Long.valueOf(connCreationTime)<(System.currentTimeMillis()-oldTime))
				{
					System.out.println("Connection Entry : Creation Time:"+new Timestamp(Long.valueOf(connCreationTime)));
					System.out.println("Connection created by Class - " + connCreationDetails[0] + " in " +
							"Method - " + connCreationDetails[1] + " at Line no - " + connCreationDetails[2]);
					/*int count=0;
					for (StackTraceElement stackTraceElement : temp.getStackTraceElement()) {count++;
						if(count>i){
							break;
						}
						System.out.println("\t\t\t\t"+stackTraceElement);
					}*/
				}
			}
		}
		/*try {
			DbConnection.getConnectionObject();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
	}

	public static void putInStore(Connection conn, String classMethodLine) {
		if("ON".equals(ApplicationFlags.IB2B_DB_CONNECTION_LOGGER_FLAG))
		{
			TestConnection.connectionStore.put(conn, classMethodLine + "-" + System.currentTimeMillis());
		}
	}

	public static void removeFromStore(Connection conn) {
		if("ON".equals(ApplicationFlags.IB2B_DB_CONNECTION_LOGGER_FLAG))
		{
			TestConnection.connectionStore.remove(conn);
		}
	}

}
