package com.ibm.ioes.npd.utilities;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.ibm.ioes.npd.exception.NpdException;
import com.ibm.ioes.npd.hibernate.beans.SessionObjectsDto;
import com.ibm.ioes.npd.hibernate.dao.CommonBaseDao;

public class PagingSorting 
{
	CommonBaseDao objDao=new CommonBaseDao();
	private static final Logger logger;
	public static final String increment="increment";
	public static final String decrement="decrement";
	public static final String hibernate="hibernate";
	public static final String jdbc="jdbc";

	static {

		logger = Logger.getLogger(PagingSorting.class);
	}
	
	private boolean pagingToBeDone;
	private boolean sortingToBeDone;
	
	private int pageNumber;
	//private int pageRecords=Integer.parseInt(Messages.getMessageValue("paging.records"));
	//private int pageRecords=objDao.getPageSize();
	private SessionObjectsDto sessionObjectsDto;
	{
		if(SessionObjectsDto.getInstance() != null){
		sessionObjectsDto = SessionObjectsDto.getInstance();
		}
	}
	private int pageRecords = sessionObjectsDto.getPageSize();
	private int startRecordId=1;
	private int endRecordId=pageRecords-1;
	private int recordCount;
	private int maxPageNumber=1;
	
	private String sortByColumn;
	private String sortByOrder;
	
	
	private String mode=jdbc;
	
	
	public PagingSorting()
	{
		
	}
	
	public PagingSorting(int noOfRecords)
	{
		pageRecords=noOfRecords;
	}
	public PagingSorting(boolean pagingToBeDone,boolean sortingToBeDone)
	{
		this.pagingToBeDone=pagingToBeDone;
		this.sortingToBeDone=sortingToBeDone;
	}
	
	public void setPagingSorting(boolean pagingToBeDone,boolean sortingToBeDone)
	{
		this.pagingToBeDone=pagingToBeDone;
		this.sortingToBeDone=sortingToBeDone;
	}
	
	public int getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
		int maxpageNo=(int)Math.ceil((double)this.getRecordCount()/(double)this.getPageRecords());
		this.maxPageNumber = (1>maxpageNo)?1:maxpageNo;
	}
	public int getStartRecordId() {
		return startRecordId;
	}
	public void setStartRecordId(int startRecordId) {
		this.startRecordId = startRecordId;
		this.endRecordId = startRecordId+pageRecords-1;
	}
	public int getEndRecordId() {
		return endRecordId;
	}
	public int getPageRecords() {
		return pageRecords;
	}
	
	public void setMode(String type)
	{
		if("jdbc".equals(type))
		{
			this.startRecordId = 1+(pageNumber-1)*pageRecords;
			this.mode=jdbc;
		}
		else if("hibernate".equals(type))
		{
			this.startRecordId = (pageNumber-1)*pageRecords;
			this.mode=hibernate;
		}
		this.endRecordId = startRecordId+pageRecords-1;
	}
	
	public void sync(String type)
	{
		if("jdbc".equals(type))
		{
			this.startRecordId = 1+(pageNumber-1)*pageRecords;
			this.mode=jdbc;
		}
		else if("hibernate".equals(type))
		{
			this.startRecordId = (pageNumber-1)*pageRecords;
			this.mode=hibernate;
		}
		this.endRecordId = startRecordId+pageRecords-1;
	}
	
	public String query(String normalQuery,String FullOrderBy,String type)
	{
		if("jdbc".equals(type))
		{
			this.startRecordId = 1+(pageNumber-1)*pageRecords;
			this.mode=jdbc;
		}
		else if("hibernate".equals(type))
		{
			this.startRecordId = (pageNumber-1)*pageRecords;
			this.mode=hibernate;
		}
		this.endRecordId = startRecordId+pageRecords-1;
		
		String str=" select TAB2.* from (SELECT ROW_NUMBER() OVER( "+FullOrderBy+") AS ROWNUMBER,TAB.* from ( "
			+normalQuery
			+" ) AS TAB )AS TAB2 where TAB2.ROWNUMBER BETWEEN "+this.startRecordId+" AND "+this.endRecordId+" ";
		return str;
	}
	
	public String query_v2(String normalQuery,String FullOrderBy,String type)
	{
		if("jdbc".equals(type))
		{
			this.startRecordId = 1+(pageNumber-1)*pageRecords;
			this.mode=jdbc;
		}
		else if("hibernate".equals(type))
		{
			this.startRecordId = (pageNumber-1)*pageRecords;
			this.mode=hibernate;
		}
		this.endRecordId = startRecordId+pageRecords-1;
		
		String str=" WITH  TAB AS( SELECT ROW_NUMBER() OVER("+FullOrderBy+") AS SNO,TEMP.* FROM("
			+normalQuery
			+" ) AS TEMP ) SELECT TAB.*,(SELECT COUNT(*) from TAB) FULL_REC_COUNT from TAB WHERE SNO BETWEEN "+this.startRecordId+" AND "+this.endRecordId+" ";
			//+" ) AS TEMP ) SELECT TAB.*,(SELECT COUNT(*) from TAB) PAGING_RECCOUNT from TABWHERE SNO BETWEEN "+this.startRecordId+" AND "+this.endRecordId+" ";
			//+" ) AS TAB )AS TAB2 where TAB2.ROWNUMBER BETWEEN "+this.startRecordId+" AND "+this.endRecordId+" ";
		return str;
	}
	public int getPageNumber() {
		return pageNumber;
	}
//	TODO Changes Made By Sumit For Paging if page no is 0 than initial page no should be 1
	public void setPageNumber(int pageNumber) {
		if(pageNumber==0) 
			this.pageNumber = 1;
		else
			this.pageNumber = pageNumber;
		//default for jdbc
		this.startRecordId = 1+(pageNumber-1)*pageRecords;
		this.endRecordId = startRecordId+pageRecords-1;
	}
	public void storeRecordCount(Connection connObj, String query) 
				throws NpdException
	{
		
		
		String str="select count(*) as COUNT from ("
			+query
			+") as Tab1";
		
		Statement stmt = null;
		ResultSet rs=null;
		try {
			stmt = connObj.createStatement();
			rs=stmt.executeQuery(str);
			rs.next();
			int count=rs.getInt(1);
			setRecordCount(count);
			
		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
			logger.error(sqlEx.getMessage()
					+ " SQLException occured in storeRecordCount method of "
					+ this.getClass().getSimpleName());
			throw new NpdException("SQL Exception : "
					+ sqlEx.getMessage(), sqlEx);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage()
					+ " Exception occured in storeRecordCount method of "
					+ this.getClass().getSimpleName());
			throw new NpdException("Exception : " + ex.getMessage(), ex);
		} finally {
			try {
				stmt.close();
				
			} catch (SQLException sqlEx) {
				logger
						.error(sqlEx.getMessage()
								+ " SQLException occured while closing database objects in recordCount_fetchValidationToDoList method of "
								+ this.getClass().getSimpleName());
				throw new NpdException("SQL Exception : "
						+ sqlEx.getMessage(), sqlEx);
			} catch (Exception ex) {
				logger
						.error(ex.getMessage()
								+ " Exception occured while closing database objects in storeRecordCount method of "
								+ this.getClass().getSimpleName());
				throw new NpdException("Exception : " + ex.getMessage(),
						ex);
			}
		}
		
	}
	public int getMaxPageNumber() {
		return maxPageNumber;
	}
	public String getSortByOrder() {
		return sortByOrder;
	}
	public void setSortByOrder(String sortByOrder) {
		this.sortByOrder = sortByOrder;
	}
	public String getSortByColumn() {
		return sortByColumn;
	}
	public void setSortByColumn(String sortByColumn) {
		this.sortByColumn = sortByColumn;
	}
	public boolean isPagingToBeDone() {
		return pagingToBeDone;
	}
	public boolean isSortingToBeDone() {
		return sortingToBeDone;
	}

	public void setDefaultifNotPresent(String sortByColumn,String sortByOrder,int pageNumber)
	{
		if(this.sortByColumn==null || "".equals(this.sortByColumn))
		{
			this.sortByColumn=sortByColumn;
		}
		if(!(PagingSorting.decrement.equals(this.sortByOrder) || PagingSorting.increment.equals(this.sortByOrder)))
		{
			this.sortByOrder=sortByOrder;
		}
		if(this.pageNumber==0)
		{
			this.pageNumber=pageNumber;
		}
	}
	
	public int getSerialNoStart() {
		if(mode==null || "".equals(mode) || jdbc.equals(mode))
		{
			return startRecordId;
		}
		if(hibernate.equals(mode))
		{
			return startRecordId+1;
		}
		return 1;
		
	}
}
