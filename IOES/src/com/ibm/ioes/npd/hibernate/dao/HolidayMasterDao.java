package com.ibm.ioes.npd.hibernate.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.ibm.ioes.npd.exception.NpdException;
import com.ibm.ioes.npd.hibernate.beans.HolidayMasterDto;
import com.ibm.ioes.npd.hibernate.beans.PerformanceReportDto;
import com.ibm.ioes.npd.hibernate.beans.ProductCategoryDto;
import com.ibm.ioes.npd.utilities.NpdConnection;
import com.ibm.ioes.npd.utilities.PagingSorting;
import com.ibm.ioes.npd.utilities.Utility;
import com.ibm.ioes.utilities.DbConnection;

public class HolidayMasterDao extends CommonBaseDao
{
	public ArrayList<HolidayMasterDto> getHolidayList(HolidayMasterDto holidayDto) 
	{
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rsViewPerformanceDetail = null;
		ArrayList<HolidayMasterDto> listHoliday = new ArrayList<HolidayMasterDto>();
		HolidayMasterDto objDto = null;
		
		try
		{
			connection=NpdConnection.getConnectionObject();
			String getPerformanceReport = null;
			getPerformanceReport= "select * FROM NPD.TM_HOLIDAY";
			
			String whereCondition="";
			String condition;
			String dateFilter=holidayDto.getDateFilter();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
			
			if((!holidayDto.getFromDate().equals("")) && (!holidayDto.getToDate().equals("")))
			{
				try 
				{
					condition=Utility.generateDateRelativeCondition("BETWEEN",holidayDto.getFromDate(),holidayDto.getToDate(),"HOLIDAY_DATE",new SimpleDateFormat("dd/MM/yyyy"));
					whereCondition=Utility.addToCondition(whereCondition,condition);
				} 
				catch (Exception ex) 
				{
					// TODO Auto-generated catch block
					ex.printStackTrace();
					throw new NpdException("Exception : " + ex.getMessage(),ex);
				}
			}
						
			condition=Utility.generateStringLikeCondition(holidayDto.getSearchHolidayName(), "HOLIDAY_NAME");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			if(!(whereCondition.trim().equals("")))
			{
				getPerformanceReport=getPerformanceReport+" WHERE "+whereCondition;
			}
						
			//Generating Order By clause
			String orderByColumn="";
			String ASC_DESC="DESC";
			PagingSorting pagingSorting =holidayDto.getPagingSorting();
			String sortBy=pagingSorting.getSortByColumn();
			
			if("holidayName".equals(sortBy))
			{
				orderByColumn="HOLIDAY_NAME";
			}
			else if("holidayDate".equals(sortBy))
			{
				orderByColumn="HOLIDAY_DATE";
			}
									
			ASC_DESC="DESC";
			String sortByOrder=pagingSorting.getSortByOrder();
			if("increment".equals(sortByOrder))
			{
				ASC_DESC="ASC";
			}
			
			if(orderByColumn!=null && !(orderByColumn.trim().equals("")))
			{
				String FullOrderBy=" ORDER BY "+orderByColumn+" "+ASC_DESC+" ";
				getPerformanceReport=getPerformanceReport+FullOrderBy;
				
//				For paging
				
				if(pagingSorting!=null)
				{
					if(pagingSorting.isPagingToBeDone())
					{
						pagingSorting.storeRecordCount(connection,getPerformanceReport);
						getPerformanceReport=pagingSorting.query(getPerformanceReport, FullOrderBy,PagingSorting.jdbc);
					}
					
				}
			}
			//System.err.println("Query:"+getPerformanceReport);
			connection.setAutoCommit(false);
			  
			//connection=NpdConnection.getConnectionObject();
			proc=connection.prepareCall(getPerformanceReport);
			rsViewPerformanceDetail = proc.executeQuery();
			  while(rsViewPerformanceDetail.next())
			  {
				  objDto =  new HolidayMasterDto();
				  objDto.setHolidayName(rsViewPerformanceDetail.getString("HOLIDAY_NAME"));
				  objDto.setHolidayDate(rsViewPerformanceDetail.getString("HOLIDAY_DATE"));
				  objDto.setHolidayID(rsViewPerformanceDetail.getInt("HOLIDAYID"));
				  
				  listHoliday.add(objDto);
			  }
			  return listHoliday;
		}
		catch(Exception ex )
		{
			ex.printStackTrace();	
		}
		finally
		{
			try 
			{
				
					DbConnection.closeResultset(rsViewPerformanceDetail);
					DbConnection.closeCallableStatement(proc);
					DbConnection.freeConnection(connection);
				
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return listHoliday;
	}	

	public int addHoliday(HolidayMasterDto holidayDto, Connection connection) throws NpdException 
	{
		int addStatus = 0;
		CallableStatement proc =null;
		String Msg="";
		 String Msg1="";
		try 
		{
			connection.setAutoCommit(false);
			if(holidayDto.getActionType()==1)
			{
				proc=connection.prepareCall(" {CALL NPD.P_INSERT_HOLIDAY(?,?,?,?)} ");
				proc.setString(1, holidayDto.getHolidayName());
				SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
				proc.setDate(2, new java.sql.Date(sdf.parse(holidayDto.getHolidayDate()).getTime()));
				proc.setString(3,"");
				proc.setString(4,"");
				proc.execute();
				Msg=proc.getString(3);
				Msg1=proc.getString(4);
				connection.commit();
				addStatus=1;
			}
			else
			{
				proc=connection.prepareCall(" {CALL NPD.P_UPDATE_HOLIDAY(?,?,?,?,?)} ");
				proc.setString(1, holidayDto.getHolidayName());
				SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
				proc.setDate(2, new java.sql.Date(sdf.parse(holidayDto.getHolidayDate()).getTime()));
				proc.setInt(3, holidayDto.getHolidayID());
				proc.setString(4,"");
				proc.setString(5,"");
				proc.execute();
				Msg=proc.getString(4);
				Msg1=proc.getString(5);
				connection.commit();
				addStatus=1;
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new NpdException(e);
		}
		finally
		{
			DbConnection.closeCallableStatement(proc);
		}

		return addStatus;
	}

	public int checkDuplicateHolidayDate(String holidayDate,int holidayID) throws NpdException
	{
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rs = null;
		int count = 0;
		try
		{
			connection=NpdConnection.getConnectionObject();
			
			SimpleDateFormat in_sdf=new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat out_sdf=new SimpleDateFormat("MM-dd-yyyy");
			String newHoliDate=out_sdf.format(in_sdf.parse((String)holidayDate));
			
			String query=null;
			if(holidayID==0)
			{
				query=" SELECT COUNT(1) AS count FROM NPD.TM_HOLIDAY WHERE HOLIDAY_DATE=DATE('"+newHoliDate+"')";
			}
			else
			{
				query=" SELECT COUNT(1) AS count FROM NPD.TM_HOLIDAY WHERE HOLIDAY_DATE=DATE('"+newHoliDate+"') AND HOLIDAYID NOT IN ("+holidayID+")";
			}
			connection.setAutoCommit(false);
				  
			proc=connection.prepareCall(query);
			rs = proc.executeQuery();
			while(rs.next())
			{
			  count=rs.getInt("count");
			}	  
		}
		catch (Exception ex) 
		{
			//logger.error(ex.getMessage();
			throw new NpdException("Exception : " + ex.getMessage(),ex);
		}
		finally
		{
			
			try {
				
					DbConnection.closeResultset(rs);
					DbConnection.closeCallableStatement(proc);
					DbConnection.freeConnection(connection);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new NpdException("Exception : " + e.getMessage(),e);
			}
		}
		return count;
	}
	
	public int checkDuplicateHolidayName(String holidayName,int holidayID) throws NpdException
	{
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rs = null;
		int count = 0;
		try
		{
			connection=NpdConnection.getConnectionObject();
				
			String query=null;
			if(holidayID==0)
			{
				query=" SELECT COUNT(1) AS count FROM NPD.TM_HOLIDAY WHERE HOLIDAY_NAME='"+holidayName+"'";
			}
			else
			{
				query=" SELECT COUNT(1) AS count FROM NPD.TM_HOLIDAY WHERE HOLIDAY_NAME='"+holidayName+"'AND HOLIDAYID NOT IN ("+holidayID+")";
			}
				
			connection.setAutoCommit(false);
				  
			proc=connection.prepareCall(query);
			rs = proc.executeQuery();
			while(rs.next())
			{
			  count=rs.getInt("count");
			}	  
		}
		catch (Exception ex) 
		{
			//logger.error(ex.getMessage();
			throw new NpdException("Exception : " + ex.getMessage(),ex);
		}
		finally
		{
			
			try {
					DbConnection.closeResultset(rs);
					DbConnection.closeCallableStatement(proc);
					DbConnection.freeConnection(connection);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new NpdException("Exception : " + e.getMessage(),e);
			}
		}
		return count;
	}
}
