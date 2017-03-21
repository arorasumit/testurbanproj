package com.ibm.ioes.npd.hibernate.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.ibm.ioes.npd.exception.NpdException;
import com.ibm.ioes.npd.hibernate.beans.PerformanceReportDto;
import com.ibm.ioes.npd.utilities.NpdConnection;
import com.ibm.ioes.npd.utilities.PagingSorting;
import com.ibm.ioes.npd.utilities.Utility;
import com.ibm.ioes.utilities.DbConnection;

public class PerformanceReportDao extends CommonBaseDao
{
	public ArrayList<PerformanceReportDto> getPerformanceReport(PerformanceReportDto performanceReportDto) 
	{
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rsViewPerformanceDetail = null;
		ArrayList<PerformanceReportDto> listPerformanceReport = new ArrayList<PerformanceReportDto>();
		PerformanceReportDto objDto = null;
		
		try
		{
			connection=NpdConnection.getConnectionObject();
			String getPerformanceReport = null;
			getPerformanceReport= "select * FROM NPD.VW_PERFORMANCE_REPORT";
			
			String whereCondition="";
			String condition;
			String dateFilter=performanceReportDto.getDateFilter();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
			
			if("plannedLaunchDate".equals(dateFilter))
			{
				try {
					condition=Utility.generateDateRelativeCondition("BETWEEN",performanceReportDto.getFromDate(),performanceReportDto.getToDate(),"EXPT_LAUNCH_DATE",new SimpleDateFormat("dd/MM/yyyy"));
					whereCondition=Utility.addToCondition(whereCondition,condition);
				} 
				catch (Exception ex) 
				{
					// TODO Auto-generated catch block
					ex.printStackTrace();
					throw new NpdException("Exception : " + ex.getMessage(),ex);
				}
			}
			
			if("actualLaunchDate".equals(dateFilter))
			{
				try {
					condition=Utility.generateDateRelativeCondition("BETWEEN",performanceReportDto.getFromDate(),performanceReportDto.getToDate(),"ACT_LAUNCH_DATE",new SimpleDateFormat("dd/MM/yyyy"));
					whereCondition=Utility.addToCondition(whereCondition,condition);
				} 
				catch (Exception ex) 
				{
					// TODO Auto-generated catch block
					ex.printStackTrace();
					throw new NpdException("Exception : " + ex.getMessage(),ex);
				}
			}
						
			condition=Utility.generateStringLikeCondition(performanceReportDto.getProjectName(), "PROJECT_NAME");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			condition=Utility.generateStringLikeCondition(performanceReportDto.getSearchProductManager(), "PRODUCT_MANAGER");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			if(performanceReportDto.getProjectStatus()!=-1)
			{
				condition=Utility.generateRelativeCondition("EQUAL", String.valueOf(performanceReportDto.getProjectStatus()), "", "PROJECTSTATUS");
				whereCondition=Utility.addToCondition(whereCondition,condition);
			}
			
			int projtID=performanceReportDto.getProjectID();
			if(projtID!=0)
			{
				condition=Utility.generateIntCondition(performanceReportDto.getProjectID(), "PROJECTID");
				whereCondition=Utility.addToCondition(whereCondition,condition);
			}
			
			int Deviations=performanceReportDto.getDeviation();
			if(Deviations!=0)
			{
				condition=Utility.generateRelativeCondition(performanceReportDto.getSearchFilter(), String.valueOf(performanceReportDto.getDeviation()), "", "DEVIATION");
				whereCondition=Utility.addToCondition(whereCondition,condition);
			}
			
			if(!(whereCondition.trim().equals("")))
			{
				getPerformanceReport=getPerformanceReport+" WHERE "+whereCondition;
			}
						
			//Generating Order By clause
			String orderByColumn="";
			String ASC_DESC="DESC";
			PagingSorting pagingSorting =performanceReportDto.getPagingSorting();
			String sortBy=pagingSorting.getSortByColumn();
			
			if("projectName".equals(sortBy))
			{
				orderByColumn="PROJECT_NAME";
			}
			else if("projectManager".equals(sortBy))
			{
				orderByColumn="PRODUCT_MANAGER";
			}
			else if("projectID".equals(sortBy))
			{
				orderByColumn="PROJECTID";
			}
			else if("plndLaunchDate".equals(sortBy))
			{
				orderByColumn="EXPT_LAUNCH_DATE";
			}
			else if("actLaunchDate".equals(sortBy))
			{
				orderByColumn="ACT_LAUNCH_DATE";
			}
			else if("deviation".equals(sortBy))
			{
				orderByColumn="DEVIATION";
			}
			else if("projectStatus".equals(sortBy))
			{
				orderByColumn="PROJECTSTATUS";
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
				  objDto =  new PerformanceReportDto();
				  objDto.setProjectName(rsViewPerformanceDetail.getString("PROJECT_NAME"));
				  objDto.setProductManager(rsViewPerformanceDetail.getString("PRODUCT_MANAGER"));
				  objDto.setProjectID(rsViewPerformanceDetail.getInt("PROJECTID"));
				  objDto.setPlndLaunchDate(rsViewPerformanceDetail.getString("EXPT_LAUNCH_DATE"));
				  objDto.setActLaunchDate(rsViewPerformanceDetail.getString("ACT_LAUNCH_DATE"));
				  objDto.setDeviation(rsViewPerformanceDetail.getInt("DEVIATION"));
				  objDto.setProjectStatus(rsViewPerformanceDetail.getInt("PROJECTSTATUS"));
				  objDto.setDaysInProject(rsViewPerformanceDetail.getInt("DAYSINPROJECT"));
				  listPerformanceReport.add(objDto);
			  }
			  return listPerformanceReport;
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
		return listPerformanceReport;
	}	
	
	public ArrayList<PerformanceReportDto> getPerformanceReportExport(PerformanceReportDto performanceReportDto) 
	{
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rsViewPerformanceDetail = null;
		ArrayList<PerformanceReportDto> listPerformanceReport = new ArrayList<PerformanceReportDto>();
		PerformanceReportDto objDto = null;
		
		try
		{
			connection=NpdConnection.getConnectionObject();
			String getPerformanceReport = null;
			getPerformanceReport= "select * FROM NPD.VW_PERFORMANCE_REPORT";
			
			String whereCondition="";
			String condition;
			String dateFilter=performanceReportDto.getDateFilter();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
			
			if("plannedLaunchDate".equals(dateFilter))
			{
				try {
					condition=Utility.generateDateRelativeCondition("BETWEEN",performanceReportDto.getFromDate(),performanceReportDto.getToDate(),"EXPT_LAUNCH_DATE",new SimpleDateFormat("dd/MM/yyyy"));
					whereCondition=Utility.addToCondition(whereCondition,condition);
				} 
				catch (Exception ex) 
				{
					// TODO Auto-generated catch block
					ex.printStackTrace();
					throw new NpdException("Exception : " + ex.getMessage(),ex);
				}
			}
			
			if("actualLaunchDate".equals(dateFilter))
			{
				try {
					condition=Utility.generateDateRelativeCondition("BETWEEN",performanceReportDto.getFromDate(),performanceReportDto.getToDate(),"ACT_LAUNCH_DATE",new SimpleDateFormat("dd/MM/yyyy"));
					whereCondition=Utility.addToCondition(whereCondition,condition);
				} 
				catch (Exception ex) 
				{
					// TODO Auto-generated catch block
					ex.printStackTrace();
					throw new NpdException("Exception : " + ex.getMessage(),ex);
				}
			}
						
			condition=Utility.generateStringLikeCondition(performanceReportDto.getProjectName(), "PROJECT_NAME");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			condition=Utility.generateStringLikeCondition(performanceReportDto.getProductManager(), "PRODUCT_MANAGER");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			if(performanceReportDto.getProjectStatus()!=-1)
			{
				condition=Utility.generateRelativeCondition("EQUAL", String.valueOf(performanceReportDto.getProjectStatus()), "", "PROJECTSTATUS");
				whereCondition=Utility.addToCondition(whereCondition,condition);
			}
			
			int projtID=performanceReportDto.getProjectID();
			if(projtID!=0)
			{
				condition=Utility.generateIntCondition(performanceReportDto.getProjectID(), "PROJECTID");
				whereCondition=Utility.addToCondition(whereCondition,condition);
			}
			
			int Deviations=performanceReportDto.getDeviation();
			if(Deviations!=0)
			{
				condition=Utility.generateRelativeCondition(performanceReportDto.getSearchFilter(), String.valueOf(performanceReportDto.getDeviation()), "", "DEVIATION");
				whereCondition=Utility.addToCondition(whereCondition,condition);
			}
			
			if(!(whereCondition.trim().equals("")))
			{
				getPerformanceReport=getPerformanceReport+" WHERE "+whereCondition;
			}
						
			//Generating Order By clause
			String orderByColumn="";
			String ASC_DESC="DESC";
			PagingSorting pagingSorting =performanceReportDto.getPagingSorting();
			String sortBy=pagingSorting.getSortByColumn();
			
			if("projectName".equals(sortBy))
			{
				orderByColumn="PROJECT_NAME";
			}
			else if("projectManager".equals(sortBy))
			{
				orderByColumn="PRODUCT_MANAGER";
			}
			else if("projectID".equals(sortBy))
			{
				orderByColumn="PROJECTID";
			}
			else if("plndLaunchDate".equals(sortBy))
			{
				orderByColumn="EXPT_LAUNCH_DATE";
			}
			else if("actLaunchDate".equals(sortBy))
			{
				orderByColumn="ACT_LAUNCH_DATE";
			}
			else if("deviation".equals(sortBy))
			{
				orderByColumn="DEVIATION";
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
			}
			//System.err.println("Query:"+getPerformanceReport);
						  
			connection.setAutoCommit(false);
			  
			//connection=NpdConnection.getConnectionObject();
			proc=connection.prepareCall(getPerformanceReport);
			rsViewPerformanceDetail = proc.executeQuery();
			  while(rsViewPerformanceDetail.next())
			  {
				  objDto =  new PerformanceReportDto();
				  objDto.setProjectName(rsViewPerformanceDetail.getString("PROJECT_NAME"));
				  objDto.setProductManager(rsViewPerformanceDetail.getString("PRODUCT_MANAGER"));
				  objDto.setProjectID(rsViewPerformanceDetail.getInt("PROJECTID"));
				  objDto.setPlndLaunchDate(rsViewPerformanceDetail.getString("EXPT_LAUNCH_DATE"));
				  objDto.setActLaunchDate(rsViewPerformanceDetail.getString("ACT_LAUNCH_DATE"));
				  objDto.setDeviation(rsViewPerformanceDetail.getInt("DEVIATION"));
				  objDto.setProjectStatus(rsViewPerformanceDetail.getInt("PROJECTSTATUS"));
				  objDto.setDaysInProject(rsViewPerformanceDetail.getInt("DAYSINPROJECT"));
				  listPerformanceReport.add(objDto);
			  }
			  return listPerformanceReport;
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
		return listPerformanceReport;
	}
	
}
