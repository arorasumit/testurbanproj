package com.ibm.ioes.npd.hibernate.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import com.ibm.ioes.npd.exception.NpdException;
import com.ibm.ioes.npd.hibernate.beans.ProjectPlanReportDto;
import com.ibm.ioes.npd.hibernate.beans.ProjectStatusReportDto;
import com.ibm.ioes.npd.hibernate.beans.TtrnProject;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjectassumptions;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjectisssues;
import com.ibm.ioes.npd.utilities.NpdConnection;
import com.ibm.ioes.npd.utilities.PagingSorting;
import com.ibm.ioes.npd.utilities.Utility;
import com.ibm.ioes.utilities.DbConnection;

public class ProjectStatusReportDao {
	private static final Logger logger;
	static 
	{
	logger = Logger.getLogger(ProjectStatusReportDao.class);
	}

	//public static String sqlGetProjectPlanDetail = "{call NPD.NPD_REPORT_FETCHPROJECTPLAN(?)}";
	public ArrayList<ProjectStatusReportDto> fetchProjectStatusDetail(ProjectStatusReportDto objProjectStatus)throws NpdException
	{
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rsViewProjectStatusDetail = null;
		ArrayList<ProjectStatusReportDto> listProjectStatus = new ArrayList<ProjectStatusReportDto>();
		ProjectStatusReportDto objDto = null;
		try
		{connection=NpdConnection.getConnectionObject();
			
			String query=null;
			query=" SELECT DISTINCT NPDCATDESC , PROJECTID , PROJECT_NAME , PRODCATDESC,PRODUCT_CATEGORY , AIRTEL_POTENTIAL , ";
			query= query + " CAPEX_REQUIREMENT , TARGET_MARKET ,TOTAL_MKT_POTENTIAL , EXPT_LAUNCH_DATE , LAUNCH_PRIORITY ,";
			query= query + " PRODUCT_BRIEF , WORKFLOWID, DAYSINPROJECT  FROM NPD.V_CPPMYTODOLIST" ;
			//generating condr=tion for where 
			String whereCondition="";
			String condition;
			String dateFilter=objProjectStatus.getDateFilter();
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
			
			
			if("plannedStartDate".equals(dateFilter))
			{
				try {
					condition=Utility.generateDateRelativeCondition("BETWEEN",objProjectStatus.getFromDate(),objProjectStatus.getToDate(),"TASKSTARTDATE",new SimpleDateFormat("dd/MM/yyyy"));
					whereCondition=Utility.addToCondition(whereCondition,condition);
				} catch (Exception ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
					throw new NpdException("Exception : " + ex.getMessage(),ex);
				}
			}
			

			condition=Utility.generateRelativeCondition("EQUAL",objProjectStatus.getSearchprojectid(),"", "PROJECTID");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			condition=Utility.generateStringLikeCondition(objProjectStatus.getSearchProjectName(), "PROJECT_NAME");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			condition=Utility.generateStringLikeCondition(objProjectStatus.getSearchNpdCategory(), "NPDCATDESC");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			condition=Utility.generateStringLikeCondition(objProjectStatus.getSearchProductCatId(), "PRODCATDESC");
			whereCondition=Utility.addToCondition(whereCondition,condition);

			if(!(whereCondition.trim().equals("")))
			{
				query=query+" WHERE "+whereCondition;
			}
			
			
			//Generating Order By clause
			String orderByColumn="";
			String ASC_DESC="DESC";
			PagingSorting pagingSorting =objProjectStatus.getPagingSorting();
			String sortBy=pagingSorting.getSortByColumn();
			
			if("projectName".equals(sortBy))
			{
				orderByColumn="PROJECT_NAME";
			}
			else if("npdCategory".equals(sortBy))
			{
				orderByColumn="NPDCATDESC";
			}
			else if("productCategory".equals(sortBy))
			{
				orderByColumn="PRODCATDESC";
			}
			else if("airtelPotential".equals(sortBy))
			{
				orderByColumn="AIRTEL_POTENTIAL";
			}
			else if("totalMarketPotential".equals(sortBy))
			{
				orderByColumn="TOTAL_MKT_POTENTIAL";
			}
			else if("capexRequirement".equals(sortBy))
			{
				orderByColumn="CAPEX_REQUIREMENT";
			}
			else if("projectid".equals(sortBy))
			{
				orderByColumn="PROJECTID";
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
				query=query+FullOrderBy;
				
//				For paging
				
				if(pagingSorting!=null)
				{
					if(pagingSorting.isPagingToBeDone())
					{
						pagingSorting.storeRecordCount(connection,query);
						query=pagingSorting.query(query, FullOrderBy,PagingSorting.jdbc);
					}
					
				}
			}
			//System.err.println("Query:"+query);
			
			
			  
			  connection.setAutoCommit(false);
			  
			  proc=connection.prepareCall(query);
			  //proc.setLong(1,objProjectPlan.getTaskid());
			  rsViewProjectStatusDetail = proc.executeQuery();
			  while(rsViewProjectStatusDetail.next())
			  {
				  objDto =  new ProjectStatusReportDto();
				  objDto.setProjectName(rsViewProjectStatusDetail.getString("PROJECT_NAME"));
				  objDto.setNpdCategory(rsViewProjectStatusDetail.getString("NPDCATDESC"));
				  objDto.setProductCategory(rsViewProjectStatusDetail.getString("PRODCATDESC"));
				  objDto.setAirtelPotential(rsViewProjectStatusDetail.getString("AIRTEL_POTENTIAL"));
				  objDto.setTotalMarketPotential(rsViewProjectStatusDetail.getString("TOTAL_MKT_POTENTIAL"));
				  objDto.setCapexRequirement(rsViewProjectStatusDetail.getString("CAPEX_REQUIREMENT"));
				  objDto.setProjectId(rsViewProjectStatusDetail.getInt("PROJECTID"));
				  objDto.setDaysInProject(rsViewProjectStatusDetail.getInt("DAYSINPROJECT"));
				  listProjectStatus.add(objDto);
			  }
//			  return listProjectStatus ;
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
				DbConnection.closeResultset(rsViewProjectStatusDetail);
				DbConnection.closeCallableStatement(proc);
				NpdConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new NpdException("Exception : " + e.getMessage(),e);
			}
		}
		return listProjectStatus;
	}
	
	public ArrayList<TtrnProjectisssues> fetchProjectIssueExport(ProjectStatusReportDto objProjectStatus) throws NpdException
	{
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rsViewProjectStatusDetail = null;
		ArrayList<TtrnProjectisssues> listProjectStatus = new ArrayList<TtrnProjectisssues>();
		TtrnProjectisssues objDto = null;
		try
		{connection=NpdConnection.getConnectionObject();
			
			String query=null;
			query=" SELECT NPD.TTRN_PROJECT.PROJECTID ,  PROJECT_NAME , ISSUEDESC , STATUS,PRIORITY FROM NPD.TTRN_PROJECTISSSUES ";
			query+=" INNER JOIN NPD.TTRN_PROJECT ";
			query+=" ON  NPD.TTRN_PROJECTISSSUES.PROJECTID = NPD.TTRN_PROJECT.PROJECTID ";

			//generating condr=tion for where 
			String whereCondition="";
			String condition;
			
			
			if(objProjectStatus.getProjectidlist()!=null && !objProjectStatus.getProjectidlist().equalsIgnoreCase(""))
			{
				condition=Utility.generateRelativeCondition("IN",objProjectStatus.getProjectidlist(),"","NPD.TTRN_PROJECTISSSUES.PROJECTID");
				whereCondition=Utility.addToCondition(whereCondition,condition);
			}
			
			if(!(whereCondition.trim().equals("")))
			{
				query=query+" WHERE "+whereCondition;
			}
			
			
			  connection.setAutoCommit(false);
			  
			  proc=connection.prepareCall(query);
			  rsViewProjectStatusDetail = proc.executeQuery();
			  TtrnProject ttrnProject = null;
			  while(rsViewProjectStatusDetail.next())
			  {
				  objDto =  new TtrnProjectisssues();
				  ttrnProject = new TtrnProject();
				  objDto.setIssuedesc(rsViewProjectStatusDetail.getString("ISSUEDESC"));
				  objDto.setPriority(rsViewProjectStatusDetail.getString("PRIORITY"));
				  ttrnProject.setProjectName(rsViewProjectStatusDetail.getString("PROJECT_NAME"));
				  ttrnProject.setProjectid(rsViewProjectStatusDetail.getInt("PROJECTID"));
				  objDto.setTtrnProject(ttrnProject);
				  listProjectStatus.add(objDto);
			  }
			 // return listProjectStatus ;
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
				DbConnection.closeResultset(rsViewProjectStatusDetail);
				DbConnection.closeCallableStatement(proc);
				NpdConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new NpdException("Exception : " + e.getMessage(),e);
			}
		}
		return listProjectStatus;
	}

	
	public ArrayList<TtrnProjectisssues> fetchProjectIssue(ProjectStatusReportDto objProjectStatus) throws NpdException
	{
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rsViewProjectStatusDetail = null;
		ArrayList<TtrnProjectisssues> listProjectStatus = new ArrayList<TtrnProjectisssues>();
		TtrnProjectisssues objDto = null;
		try
		{connection=NpdConnection.getConnectionObject();
			
			String query=null;
			query=" SELECT ISSUEDESC , STATUS,PRIORITY FROM NPD.TTRN_PROJECTISSSUES ";

			//generating condr=tion for where 
			String whereCondition="";
			String condition;
			
			
			if(objProjectStatus.getSearchProjectid()!=0)
			{
				condition=Utility.generateRelativeCondition("IN",String.valueOf(objProjectStatus.getSearchProjectid()),"","PROJECTID");
				whereCondition=Utility.addToCondition(whereCondition,condition);
			}
			
			if(!(whereCondition.trim().equals("")))
			{
				query=query+" WHERE "+whereCondition;
			}
			
			
			  connection.setAutoCommit(false);
			  
			  proc=connection.prepareCall(query);
			  rsViewProjectStatusDetail = proc.executeQuery();
			  while(rsViewProjectStatusDetail.next())
			  {
				  objDto =  new TtrnProjectisssues();
				  objDto.setIssuedesc(rsViewProjectStatusDetail.getString("ISSUEDESC"));
				  objDto.setPriority(rsViewProjectStatusDetail.getString("PRIORITY"));
				  
				  listProjectStatus.add(objDto);
			  }
			 // return listProjectStatus ;
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
				DbConnection.closeResultset(rsViewProjectStatusDetail);
				DbConnection.closeCallableStatement(proc);
				NpdConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new NpdException("Exception : " + e.getMessage(),e);
			}
		}
		return listProjectStatus;
	}

	public ArrayList<TtrnProjectassumptions> fetchProjectAssumption(ProjectStatusReportDto objProjectStatus)throws NpdException
	{
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rsViewProjectStatusDetail = null;
		ArrayList<TtrnProjectassumptions> listProjectStatus = new ArrayList<TtrnProjectassumptions>();
		TtrnProjectassumptions objDto = null;
		try
		{connection=NpdConnection.getConnectionObject();
			
			String query=null;
			query=" select * from npd.TTRN_PROJECTASSUMPTIONS";

			//generating condr=tion for where 
			String whereCondition="";
			String condition;
			
			condition=Utility.generateIntCondition(objProjectStatus.getSearchProjectid(), "PROJECTID");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			
			if(!(whereCondition.trim().equals("")))
			{
				query=query+" WHERE "+whereCondition;
			}
			
			
			  connection.setAutoCommit(false);
			  
			  proc=connection.prepareCall(query);
			  rsViewProjectStatusDetail = proc.executeQuery();
			  while(rsViewProjectStatusDetail.next())
			  {
				  objDto =  new TtrnProjectassumptions();
				  //objDto.setIssuedesc(rsViewProjectStatusDetail.getString("ISSUEDESC"));
				  //objDto.setPriority(rsViewProjectStatusDetail.getString("PRIORITY"));
				  
				  listProjectStatus.add(objDto);
			  }
			 // return listProjectStatus ;
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
				DbConnection.closeResultset(rsViewProjectStatusDetail);
				DbConnection.closeCallableStatement(proc);
				NpdConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new NpdException("Exception : " + e.getMessage(),e);
			}
		}
		return listProjectStatus;
	}

	
	
}
