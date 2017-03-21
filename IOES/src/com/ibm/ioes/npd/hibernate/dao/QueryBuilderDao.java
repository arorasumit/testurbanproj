package com.ibm.ioes.npd.hibernate.dao;

import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.ibm.ioes.npd.exception.NpdException;
import com.ibm.ioes.npd.hibernate.beans.ProjectPlanReportDto;
import com.ibm.ioes.npd.hibernate.beans.QueryBuilderDto;
import com.ibm.ioes.npd.hibernate.beans.TtrnProject;
import com.ibm.ioes.npd.model.CommonBaseModel;
import com.ibm.ioes.npd.utilities.NpdConnection;
import com.ibm.ioes.npd.utilities.PagingSorting;
import com.ibm.ioes.npd.utilities.Utility;
import com.ibm.ioes.utilities.DbConnection;

public class QueryBuilderDao {
	private static final Logger logger;
	static 
	{
		logger = Logger.getLogger(QueryBuilderDao.class);
	}

	
	public HashMap ExecuteQueryBuilder(QueryBuilderDto objDto) throws NpdException
	{
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rs = null;
		ArrayList<QueryBuilderDto> listColumns = new ArrayList<QueryBuilderDto>();
		ArrayList<QueryBuilderDto> listData = new ArrayList<QueryBuilderDto>();
		QueryBuilderDto objQueryDto = null;
		HashMap htValue = new HashMap();
		try
		{connection=NpdConnection.getConnectionObject();
			
			  String query=objDto.getQuery();
			
			  connection.setAutoCommit(false);
			  
			  proc=connection.prepareCall(query);
			  rs = proc.executeQuery();
			  ResultSetMetaData md = rs.getMetaData();
			  
			  int iColCount =  md.getColumnCount();
			  int iCount = 1;

			  
			  for(iCount = 1;iCount <=iColCount;iCount++)
			  {
				  	  objQueryDto = new QueryBuilderDto();
					  objQueryDto.setCol1(md.getColumnLabel(iCount));
					  listColumns.add(objQueryDto);
			  }
			  
			  
			  while(rs.next())
			  {
			      objQueryDto = new QueryBuilderDto();
			      
				for(iCount=1;iCount<=iColCount;iCount++)
				{
				    if(iCount==1)
					objQueryDto.setCol1(rs.getString(iCount));
				    if(iCount==2)
					objQueryDto.setCol2(rs.getString(iCount));
				    if(iCount==3)
					objQueryDto.setCol3(rs.getString(iCount));
				    if(iCount==4)
					objQueryDto.setCol4(rs.getString(iCount));
				    if(iCount==5)
					objQueryDto.setCol5(rs.getString(iCount));
				    if(iCount==6)
					objQueryDto.setCol6(rs.getString(iCount));
				    if(iCount==7)
					objQueryDto.setCol7(rs.getString(iCount));
				    if(iCount==8)
					objQueryDto.setCol8(rs.getString(iCount));
				    if(iCount==9)
					objQueryDto.setCol9(rs.getString(iCount));
				    if(iCount==10)
					objQueryDto.setCol10(rs.getString(iCount));
				    if(iCount==11)
					objQueryDto.setCol11(rs.getString(iCount));
				    if(iCount==12)
					objQueryDto.setCol12(rs.getString(iCount));
				    if(iCount==13)
					objQueryDto.setCol13(rs.getString(iCount));
				    if(iCount==14)
					objQueryDto.setCol14(rs.getString(iCount));
				    if(iCount==15)
					objQueryDto.setCol15(rs.getString(iCount));
				    if(iCount==16)
					objQueryDto.setCol16(rs.getString(iCount));
				    if(iCount==17)
					objQueryDto.setCol17(rs.getString(iCount));
				    if(iCount==18)
					objQueryDto.setCol18(rs.getString(iCount));
				    if(iCount==19)
					objQueryDto.setCol19(rs.getString(iCount));
				    if(iCount==20)
					objQueryDto.setCol17(rs.getString(iCount));
				    if(iCount==21)
					objQueryDto.setCol21(rs.getString(iCount));
				    if(iCount==22)
					objQueryDto.setCol22(rs.getString(iCount));
				    if(iCount==23)
					objQueryDto.setCol23(rs.getString(iCount));
				    if(iCount==24)
					objQueryDto.setCol24(rs.getString(iCount));
				    if(iCount==25)
					objQueryDto.setCol25(rs.getString(iCount));
				    if(iCount==26)
					objQueryDto.setCol26(rs.getString(iCount));
				    if(iCount==27)
					objQueryDto.setCol27(rs.getString(iCount));
				    if(iCount==28)
					objQueryDto.setCol28(rs.getString(iCount));
				    if(iCount==29)
					objQueryDto.setCol29(rs.getString(iCount));

				    
				}
			       
			        listData.add(objQueryDto);
			  }
			  htValue.put("Columns", listColumns);
			  htValue.put("Data", listData);
		}
		catch (SQLException sqlEx) {
			logger.error(sqlEx.getMessage()
			+ " SQLException occured while closing database objects in myToDoList method of "
			+ this.getClass().getSimpleName());
			throw new NpdException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
		} catch (Exception ex) {
			logger.error(ex.getMessage()
			+ " Exception occured while closing database objects in myToDoList method of "
			+ this.getClass().getSimpleName());
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
		return htValue;
	}
	
	public HashMap ExecuteQueryBuilderUpdate(QueryBuilderDto objDto) throws NpdException
	{
		Connection connection =null;
		CallableStatement proc =null;
		HashMap htValue = new HashMap();
		try
		{connection=NpdConnection.getConnectionObject();
			
			  String query=objDto.getQuery();
			
			  connection.setAutoCommit(false);
			  QueryBuilderDto objUpdateDto = new QueryBuilderDto();
			  
			  proc=connection.prepareCall(query);
			  int iCountUpdate  = proc.executeUpdate();
			  connection.commit();
			  htValue.put("iCountUpdate", iCountUpdate);
		}
		catch (SQLException sqlEx) {
			logger.error(sqlEx.getMessage()
			+ " SQLException occured while closing database objects in myToDoList method of "
			+ this.getClass().getSimpleName());
			throw new NpdException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
		} catch (Exception ex) {
			logger.error(ex.getMessage()
			+ " Exception occured while closing database objects in myToDoList method of "
			+ this.getClass().getSimpleName());
			throw new NpdException("Exception : " + ex.getMessage(),ex);
		}
		finally
		{
			
			try {
				
				DbConnection.closeCallableStatement(proc);
				NpdConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new NpdException("Exception : " + e.getMessage(),e);
			}
		}
		return htValue;
	}
	public HashMap ExecuteQueryBuilderDDL(QueryBuilderDto objDto) throws NpdException
	{
		Connection connection =null;
		CallableStatement proc =null;
		HashMap htValue = new HashMap();
		String isExecuted = null;
		try
		{connection=NpdConnection.getConnectionObject();
			
			  String query=objDto.getQuery();
			
			  connection.setAutoCommit(false);
			  QueryBuilderDto objUpdateDto = new QueryBuilderDto();
			  
			  proc=connection.prepareCall(query);
			  isExecuted  = String.valueOf(proc.executeUpdate());
			  connection.commit();
			  
		}
		catch (SQLException sqlEx) {
			isExecuted  = sqlEx.getMessage();
			logger.error(sqlEx.getMessage()
			+ " SQLException occured while closing database objects in myToDoList method of "
			+ this.getClass().getSimpleName());
			throw new NpdException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
		} catch (Exception ex) {
			logger.error(ex.getMessage()
			+ " Exception occured while closing database objects in myToDoList method of "
			+ this.getClass().getSimpleName());
			throw new NpdException("Exception : " + ex.getMessage(),ex);
		}
		finally
		{
			
			DbConnection.closeCallableStatement(proc);
			htValue.put("iCountUpdate", isExecuted);
			try {
				NpdConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new NpdException("Exception : " + e.getMessage(),e);
			}
		}
		return htValue;
	}

	
}
