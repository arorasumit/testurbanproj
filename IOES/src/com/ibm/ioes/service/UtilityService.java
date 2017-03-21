package com.ibm.ioes.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ibm.fx.mq.PoolConfigData;
import com.ibm.ioes.clep.CLEPUtility;
import com.ibm.ioes.ecrm.CRMLogger;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Utility;
import com.ibm.ioes.utilities.AjaxHelper.MyHttpServletResponseWrapper;

public class UtilityService {

	private String getPoolConfigData=" SELECT TASK_TYPE, CLONE_NAME, MIN_THREAD, MAX_THREAD " +
			"   FROM ioe.PARALLEL_TASK_CONFIG " +
			"   WHERE (CLONE_NAME=? ) AND ISACTIVE=1 " ;	

	
	public String getSchedulerName() throws IOException
	{
		File  file = new File(System.getProperty("user.install.root").concat("/").concat("logs")+"/"+com.ibm.websphere.runtime.ServerName.getDisplayName()+"/SchedularDetails.txt" );
		AppConstants.IOES_LOGGER.info("--------------------------------------Schedulers are starting  ---------------------------------------");
		AppConstants.IOES_LOGGER.info("*******                               Server Name is: "+com.ibm.websphere.runtime.ServerName.getDisplayName()+"                              ***********");
		AppConstants.IOES_LOGGER.info("------------------------------------------------------------------------------------------------------");
		FileInputStream fstream = new FileInputStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		 String strLine;
		 String fileData = "";
		 while ((strLine = br.readLine()) != null)   {
				   fileData = fileData + strLine;
		 }
		 fstream.close(); 
		
		return fileData;
	}
	
	
	public String getAppConfigValue(String key) throws Exception{
		
		return Utility.getAppConfigValue(key);
	}


	public void LOG(Exception ex) {
		Utility.LOG(ex);
	}

	public ArrayList<PoolConfigData> getPoolConfig(String schedulername) throws Exception{			
		//String scheduler=schedulername;
		Connection connection=null;			
		PreparedStatement psmt=null;			
		ResultSet rst=null;					
		PoolConfigData configdata=null; 					
		ArrayList<PoolConfigData> configdatalist= new ArrayList<PoolConfigData>();
				
		try
		{								
			connection =DbConnection.getConnectionObject();			
			psmt= connection.prepareStatement(getPoolConfigData );					
			psmt.setString(1, schedulername);					
			rst=psmt.executeQuery();
																															
						while(rst.next())
								
						{
							configdata=new PoolConfigData();  

							configdata.setTask_type(rst.getString("TASK_TYPE")) ;

							configdata.setCloneName(rst.getString("CLONE_NAME"));

							configdata.setMinThread(rst.getInt("MIN_THREAD"));

							configdata.setMaxThread(rst.getInt("MAX_THREAD"));

							configdatalist.add(configdata);
										
						}											
			} catch (Exception e)	{e.printStackTrace();}
		finally 
		{
			if(rst!=null )
			{
				DbConnection.closeResultset(rst);
				DbConnection.closePreparedStatement(psmt);
			}
			if(connection.isClosed()==false && connection.getAutoCommit()==false)
			{
				connection.rollback();								
				DbConnection.freeConnection(connection);							
			}
		}																
		return configdatalist;
	}
	
	public void setAttributes(HashMap<String,Object> attributes, HttpServletRequest request) {
		Set<Map.Entry<String,Object>> entrySet = attributes.entrySet();
		for (Map.Entry<String,Object> entry : entrySet) {
			request.setAttribute((String)entry.getKey(), entry.getValue());
		}
	}

	public String getJspOutput(HttpServletRequest request,HttpSession session,String jsp) throws Exception{
		
		java.lang.reflect.InvocationHandler handler = new java.lang.reflect.InvocationHandler() {
			
			@Override
			public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {
				if(method.getName().equals("containsHeader")){
					return false;
				}else if(method.getName().equals("getStatus")){
					return 200;
				}else if(method.getName().equals("isCommitted")){
					return new Boolean(false);
				}else 
					return null;
			}
		};
		HttpServletResponse proxyResponse=(HttpServletResponse)Proxy.newProxyInstance(HttpServletResponse.class.getClassLoader(), new Class[]{HttpServletResponse.class}, handler );
		//proxyResponse.
		MyHttpServletResponseWrapper responseWrapper = new MyHttpServletResponseWrapper(proxyResponse);
		
		request.getRequestDispatcher(jsp).include(request, responseWrapper);

		String content = responseWrapper.getData();//responseWrapper.getOutputStream().toString();
		//System.out.println("Output : " + content);
	    return content;
	}

	public static void syncDataUtilityFunction(Connection sourceConn,
			Connection targetConn,			
			String sourcefetchquery,
			String targetsavequery) throws Exception{

		ResultSet rs=null;
		PreparedStatement pstmtSource=null;
		PreparedStatement pstmtTarget=null;
		int count=0;
		final int batchSize=25000;
		int colType=0;
				
		try{
			pstmtSource=sourceConn.prepareStatement(sourcefetchquery);
			rs=pstmtSource.executeQuery();
			pstmtTarget=targetConn.prepareStatement(targetsavequery);
			int totCols=rs.getMetaData().getColumnCount();
			
			while(rs.next()){
				
						ResultSetMetaData rsmd=rs.getMetaData();
				for(int i=1;i<=totCols;i++)
				{
					String str=rs.getString(i);
								colType=rsmd.getColumnType(i);
								if(str==null)						
									pstmtTarget.setNull(i,colType);								
								else{
										if(colType==java.sql.Types.BIGINT || colType==java.sql.Types.SMALLINT)
											pstmtTarget.setInt(i, Integer.parseInt(str));
										else if(colType==java.sql.Types.DOUBLE)
											pstmtTarget.setDouble(i, Double.valueOf(str));
										else if(colType==java.sql.Types.DATE)
											pstmtTarget.setDate(i, Date.valueOf(str));
										else if(colType==java.sql.Types.TIMESTAMP)
											pstmtTarget.setTimestamp(i, Timestamp.valueOf(str));
										else 
											pstmtTarget.setString(i, str);
									}
									
				}
				pstmtTarget.addBatch();
				count++;
				if(count==batchSize){
					pstmtTarget.executeBatch();
					count=0;
				}
			}
			if(count>0)
			{
				pstmtTarget.executeBatch();
			}
			
		}finally{
			DbConnection.closeResultset(rs);
			DbConnection.closePreparedStatement(pstmtSource);
			DbConnection.closePreparedStatement(pstmtTarget);
		}
	}

}
