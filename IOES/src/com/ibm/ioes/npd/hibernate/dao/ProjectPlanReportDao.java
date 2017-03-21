package com.ibm.ioes.npd.hibernate.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.ibm.ioes.npd.exception.NpdException;
import com.ibm.ioes.npd.hibernate.beans.ProjectPlanReportDto;
import com.ibm.ioes.npd.hibernate.beans.TtrnProject;
import com.ibm.ioes.npd.model.CommonBaseModel;
import com.ibm.ioes.npd.utilities.NpdConnection;
import com.ibm.ioes.npd.utilities.PagingSorting;
import com.ibm.ioes.npd.utilities.Utility;
import com.ibm.ioes.utilities.DbConnection;

public class ProjectPlanReportDao {
	private static final Logger logger;
	static 
	{
		logger = Logger.getLogger(ProjectPlanReportDao.class);
	}

	//public static String sqlGetProjectPlanDetail = "{call NPD.NPD_REPORT_FETCHPROJECTPLAN(?)}";
	public ArrayList<ProjectPlanReportDto> fetchProjectPlanDetail(ProjectPlanReportDto objProjectPlan) throws NpdException
	{
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rsViewProjectPlanDetail = null;
		ArrayList<ProjectPlanReportDto> listProjectPlan = new ArrayList<ProjectPlanReportDto>();
		ProjectPlanReportDto objDto = null;
		try
		{connection=NpdConnection.getConnectionObject();
			
			String query=null;
			query=" SELECT * FROM NPD.VW_FETCHPROJECTPLAN " ;
			//generating condr=tion for where 
			String whereCondition="";
			String condition;
			String dateFilter=objProjectPlan.getDateFilter();
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
			
			
			if("plannedStartDate".equals(dateFilter))
			{
				try {
					condition=Utility.generateDateRelativeCondition("BETWEEN",objProjectPlan.getFromDate(),objProjectPlan.getToDate(),"TASKSTARTDATE",new SimpleDateFormat("dd/MM/yyyy"));
					whereCondition=Utility.addToCondition(whereCondition,condition);
				} catch (Exception ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
					throw new NpdException("Exception : " + ex.getMessage(),ex);
				}
			}
			
			if("plannedEndDate".equals(dateFilter))
			{
				try {
					condition=Utility.generateDateRelativeCondition("BETWEEN",objProjectPlan.getFromDate(),objProjectPlan.getToDate(),"TASKENDDATE",new SimpleDateFormat("dd/MM/yyyy"));
					whereCondition=Utility.addToCondition(whereCondition,condition);
				} catch (Exception ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
					throw new NpdException("Exception : " + ex.getMessage(),ex);
				}
			}

			if("actualstartdate".equals(dateFilter))
			{
				try {
					condition=Utility.generateDateRelativeCondition("BETWEEN",objProjectPlan.getFromDate(),objProjectPlan.getToDate(),"ACTUALTASKSTARTDATE",new SimpleDateFormat("dd/MM/yyyy"));
					whereCondition=Utility.addToCondition(whereCondition,condition);
				} catch (Exception ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
					throw new NpdException("Exception : " + ex.getMessage(),ex);
				}
			}

			if("actualenddate".equals(dateFilter))
			{
				try {
					condition=Utility.generateDateRelativeCondition("BETWEEN",objProjectPlan.getFromDate(),objProjectPlan.getToDate(),"ACTUALTASKENDDATE",new SimpleDateFormat("dd/MM/yyyy"));
					whereCondition=Utility.addToCondition(whereCondition,condition);
				} catch (Exception ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
					throw new NpdException("Exception : " + ex.getMessage(),ex);
				}
			}


			if(objProjectPlan.getProjectStatusFilter() !=-1)
			{
				condition=Utility.generateRelativeCondition("EQUAL",String.valueOf(objProjectPlan.getProjectStatusFilter()),"","PROJECTSTATUS"); 
				//condition=Utility.generateIntCondition(objProjectPlan.getProjectStatusFilter(), "PROJECTSTATUS");
				whereCondition=Utility.addToCondition(whereCondition,condition);
			}

			
			if(objProjectPlan.getSearchProjectId()!=0)
			{
				condition=Utility.generateIntCondition(objProjectPlan.getSearchProjectId(), "WORKFLOWID");
				whereCondition=Utility.addToCondition(whereCondition,condition);
			}

			if(objProjectPlan.getSearchprojectid()!=null && !objProjectPlan.getSearchprojectid().equalsIgnoreCase(""))
			{
				condition=Utility.generateIntCondition(Integer.parseInt(objProjectPlan.getSearchprojectid()), "PROJECTID");
				whereCondition=Utility.addToCondition(whereCondition,condition);
			}


			condition=Utility.generateStringLikeCondition(objProjectPlan.getSearchprojectname(), "PROJECT_NAME");
			whereCondition=Utility.addToCondition(whereCondition,condition);

			condition=Utility.generateStringLikeCondition(objProjectPlan.getSearchStageName(), "STAGENAME");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			condition=Utility.generateStringLikeCondition(objProjectPlan.getSearchTaskName(), "TASKNAME");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			if((objProjectPlan.getTaskStatus() !=null) && !objProjectPlan.getTaskStatus().equalsIgnoreCase(""))
			{
				condition=Utility.generateStringLikeCondition(objProjectPlan.getTaskStatus(), "CURRENTTASKSTATUS");
				whereCondition=Utility.addToCondition(whereCondition,condition);
			}
			
 			if((objProjectPlan.getTaskDelayValue()!=null) && (!objProjectPlan.getTaskDelayValue().equalsIgnoreCase("")))
			{
				String DelayOPP=null;
				int delayDay=0;
				if(objProjectPlan.getDelayindays()==0)
				{
					DelayOPP="LEQUAL";
				}
				if(objProjectPlan.getDelayindays()==1)
				{
					DelayOPP="GREATER";
				}
				condition=Utility.generateRelativeCondition(DelayOPP, String.valueOf(delayDay), "", "DELAYINDAYS");
				whereCondition=Utility.addToCondition(whereCondition,condition);
			}
 			
 			if((objProjectPlan.getMonth_Name()!=null) && (!objProjectPlan.getMonth_Name().equalsIgnoreCase("")))
			{
 				condition=Utility.generateStringLikeCondition(objProjectPlan.getMonth_Name(), "(TRIM(CHAR(SUBSTR(MONTHNAME(TASKSTARTDATE),1,3))) ||'-'||TRIM(SUBSTR(CHAR(YEAR(TASKSTARTDATE)),3,2)))");
				whereCondition=Utility.addToCondition(whereCondition,condition);
			}
			
			if(!(whereCondition.trim().equals("")))
			{
				query=query+" WHERE "+whereCondition;
			}
			
			
			//Generating Order By clause
			String orderByColumn="";
			String ASC_DESC="DESC";
			PagingSorting pagingSorting =objProjectPlan.getPagingSorting();
			String sortBy=pagingSorting.getSortByColumn();
			if("taskid".equals(sortBy))
			{
				orderByColumn="CURRENTTASKID";
			}
			else if("stageName".equals(sortBy))
			{
				orderByColumn="STAGENAME";
			}
			else if("projectid".equals(sortBy))
			{
				orderByColumn="PROJECTID";
			}
			else if("projectname".equals(sortBy))
			{
				orderByColumn="PROJECT_NAME";
			}

			else if("taskName".equals(sortBy))
			{
				orderByColumn="TASKNAME";
			}
			else if("plannedstartdate".equals(sortBy))
			{
				orderByColumn="TASKSTARTDATE";
			}
			else if("plannedenddate".equals(sortBy))
			{
				orderByColumn="TASKENDDATE";
			}
			else if("actualstartdate".equals(sortBy))
			{
				orderByColumn="ACTUALTASKSTARTDATE";
			}
			else if("actualenddate".equals(sortBy))
			{
				orderByColumn="ACTUALTASKENDDATE";
			}
			else if("stakeholderrole".equals(sortBy))
			{
				orderByColumn="ROLENAME";
			}
			else if("stakeholdername".equals(sortBy))
			{
				orderByColumn="TASKSTAKEHOLDER";
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
			  rsViewProjectPlanDetail = proc.executeQuery();
			  TtrnProject project = null;
			  while(rsViewProjectPlanDetail.next())
			  {
				  objDto =  new ProjectPlanReportDto();
				  project = new TtrnProject();
				  objDto.setStagename(rsViewProjectPlanDetail.getString("STAGENAME"));
				  objDto.setTaskname(rsViewProjectPlanDetail.getString("TASKNAME"));
				  objDto.setPlannedstartdate(rsViewProjectPlanDetail.getDate("TASKSTARTDATE"));
				  objDto.setPlannedenddate(rsViewProjectPlanDetail.getDate("TASKENDDATE"));
				  objDto.setActualstartdate(rsViewProjectPlanDetail.getDate("ACTUALTASKSTARTDATE"));
				  objDto.setActualenddate(rsViewProjectPlanDetail.getDate("ACTUALTASKENDDATE"));
				  objDto.setStakeholdername(rsViewProjectPlanDetail.getString("TASKSTAKEHOLDER"));
				  objDto.setStakeholderrole(rsViewProjectPlanDetail.getString("ROLENAME"));			  
				  objDto.setDelayindays(rsViewProjectPlanDetail.getInt("DELAYINDAYS"));
				  objDto.setDocName(rsViewProjectPlanDetail.getString("DOCNAME"));
				  objDto.setProjectActionId(rsViewProjectPlanDetail.getInt("PROJECTACTIONDOCID"));
				  objDto.setCurrentTaskStatus(rsViewProjectPlanDetail.getString("CURRENTTASKSTATUS"));
				  project.setProjectName(rsViewProjectPlanDetail.getString("PROJECT_NAME"));
				  project.setProjectid(rsViewProjectPlanDetail.getInt("PROJECTID"));
				  objDto.setProjectStatus(rsViewProjectPlanDetail.getInt("PROJECTSTATUS"));
				  objDto.setDaysInProject(rsViewProjectPlanDetail.getInt("DAYSINPROJECT"));
				  objDto.setProject(project);
				  //
				  
				  listProjectPlan.add(objDto);
			  }
//			  return listProjectPlan;
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
				DbConnection.closeResultset(rsViewProjectPlanDetail);
				DbConnection.closeCallableStatement(proc);
				NpdConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new NpdException("Exception : " + e.getMessage(),e);
			}
		}
		return listProjectPlan;
	}
	
	public ArrayList<ProjectPlanReportDto> ExportProjectPlanDetail(ProjectPlanReportDto objProjectPlan) throws NpdException
	{
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rsViewProjectPlanDetail = null;
		ArrayList<ProjectPlanReportDto> listProjectPlan = new ArrayList<ProjectPlanReportDto>();
		ProjectPlanReportDto objDto = null;
		try
		{connection=NpdConnection.getConnectionObject();
			
			String query=null;
			query=" SELECT * FROM NPD.VW_FETCHPROJECTPLAN " ;
			//generating condr=tion for where 
			String whereCondition="";
			String condition;
			String dateFilter=objProjectPlan.getDateFilter();
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
			
			
			if("plannedStartDate".equals(dateFilter))
			{
				try {
					condition=Utility.generateDateRelativeCondition("BETWEEN",objProjectPlan.getFromDate(),objProjectPlan.getToDate(),"TASKSTARTDATE",new SimpleDateFormat("dd/MM/yyyy"));
					whereCondition=Utility.addToCondition(whereCondition,condition);
				} catch (Exception ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
					throw new NpdException("Exception : " + ex.getMessage(),ex);
				}
			}
			
			if("plannedEndDate".equals(dateFilter))
			{
				try {
					condition=Utility.generateDateRelativeCondition("BETWEEN",objProjectPlan.getFromDate(),objProjectPlan.getToDate(),"TASKENDDATE",new SimpleDateFormat("dd/MM/yyyy"));
					whereCondition=Utility.addToCondition(whereCondition,condition);
				} catch (Exception ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
					throw new NpdException("Exception : " + ex.getMessage(),ex);
				}
			}

			if("actualstartdate".equals(dateFilter))
			{
				try {
					condition=Utility.generateDateRelativeCondition("BETWEEN",objProjectPlan.getFromDate(),objProjectPlan.getToDate(),"ACTUALTASKSTARTDATE",new SimpleDateFormat("dd/MM/yyyy"));
					whereCondition=Utility.addToCondition(whereCondition,condition);
				} catch (Exception ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
					throw new NpdException("Exception : " + ex.getMessage(),ex);
				}
			}

			if("actualenddate".equals(dateFilter))
			{
				try {
					condition=Utility.generateDateRelativeCondition("BETWEEN",objProjectPlan.getFromDate(),objProjectPlan.getToDate(),"ACTUALTASKENDDATE",new SimpleDateFormat("dd/MM/yyyy"));
					whereCondition=Utility.addToCondition(whereCondition,condition);
				} catch (Exception ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
					throw new NpdException("Exception : " + ex.getMessage(),ex);
				}
			}

			if(objProjectPlan.getProjectStatusFilter() !=-1)
			{
				condition=Utility.generateRelativeCondition("EQUAL",String.valueOf(objProjectPlan.getProjectStatusFilter()),"","PROJECTSTATUS"); 
				//condition=Utility.generateIntCondition(objProjectPlan.getProjectStatusFilter(), "PROJECTSTATUS");
				whereCondition=Utility.addToCondition(whereCondition,condition);
			}

			
			if(objProjectPlan.getSearchProjectId()!=0)
			{
				condition=Utility.generateIntCondition(objProjectPlan.getSearchProjectId(), "WORKFLOWID");
				whereCondition=Utility.addToCondition(whereCondition,condition);
			}

			if(objProjectPlan.getSearchprojectid()!=null && !objProjectPlan.getSearchprojectid().equalsIgnoreCase(""))
			{
				condition=Utility.generateRelativeCondition("IN", objProjectPlan.getSearchprojectid(),"", "PROJECTID");
				whereCondition=Utility.addToCondition(whereCondition,condition);
			}


			condition=Utility.generateStringLikeCondition(objProjectPlan.getSearchprojectname(), "PROJECT_NAME");
			whereCondition=Utility.addToCondition(whereCondition,condition);

			condition=Utility.generateStringLikeCondition(objProjectPlan.getSearchStageName(), "STAGENAME");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			condition=Utility.generateStringLikeCondition(objProjectPlan.getSearchTaskName(), "TASKNAME");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			
			if(!(whereCondition.trim().equals("")))
			{
				query=query+" WHERE "+whereCondition;
			}
			
			String FullOrderBy=" ORDER BY PROJECTID,CURRENTTASKID";
			query=query+FullOrderBy;
			  
			  connection.setAutoCommit(false);
			  
			  proc=connection.prepareCall(query);
			  //proc.setLong(1,objProjectPlan.getTaskid());
			  rsViewProjectPlanDetail = proc.executeQuery();
			  TtrnProject project = null;
			  while(rsViewProjectPlanDetail.next())
			  {
				  objDto =  new ProjectPlanReportDto();
				  project = new TtrnProject();
				  objDto.setStagename(rsViewProjectPlanDetail.getString("STAGENAME"));
				  objDto.setTaskname(rsViewProjectPlanDetail.getString("TASKNAME"));
				  objDto.setPlannedstartdate(rsViewProjectPlanDetail.getDate("TASKSTARTDATE"));
				  objDto.setPlannedenddate(rsViewProjectPlanDetail.getDate("TASKENDDATE"));
				  objDto.setActualstartdate(rsViewProjectPlanDetail.getDate("ACTUALTASKSTARTDATE"));
				  objDto.setActualenddate(rsViewProjectPlanDetail.getDate("ACTUALTASKENDDATE"));
				  objDto.setStakeholdername(rsViewProjectPlanDetail.getString("TASKSTAKEHOLDER"));
				  objDto.setStakeholderrole(rsViewProjectPlanDetail.getString("ROLENAME"));			  
				  objDto.setDelayindays(rsViewProjectPlanDetail.getInt("DELAYINDAYS"));
				  project.setProjectName(rsViewProjectPlanDetail.getString("PROJECT_NAME"));
				  project.setProjectid(rsViewProjectPlanDetail.getInt("PROJECTID"));
				  objDto.setDaysInProject(rsViewProjectPlanDetail.getInt("DAYSINPROJECT"));
				  objDto.setProject(project);
				  listProjectPlan.add(objDto);
				  
			  }
			  //return listProjectPlan;
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
				DbConnection.closeResultset(rsViewProjectPlanDetail);
				DbConnection.closeCallableStatement(proc);
				NpdConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new NpdException("Exception : " + e.getMessage(),e);
			}
		}

		return listProjectPlan;
	}

	public byte[] DownloadFile(ProjectPlanReportDto objProjectPlan) throws NpdException
	{
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rsViewProjectPlanDetail = null;
		byte[] fileByte = null;
		
		try
		{connection=NpdConnection.getConnectionObject();
			
			String query=null;
			query=" SELECT * FROM NPD.VW_FETCHPROJECTPLAN " ;
			//generating condr=tion for where 
			String whereCondition="";
			String condition;
			if(objProjectPlan.getProjectActionId()!=0)
			{
				condition=Utility.generateIntCondition(objProjectPlan.getProjectActionId(), "PROJECTACTIONDOCID");
				whereCondition=Utility.addToCondition(whereCondition,condition);
			}
			
			if(!(whereCondition.trim().equals("")))
			{
				query=query+" WHERE "+whereCondition;
			}
			  connection.setAutoCommit(false);
			  
			  proc=connection.prepareCall(query);
			  //proc.setLong(1,objProjectPlan.getTaskid());
			  rsViewProjectPlanDetail = proc.executeQuery();
			  while(rsViewProjectPlanDetail.next())
			  {
				  CommonBaseModel objModel = new CommonBaseModel(); 
				  fileByte= objModel.blobToByteArray(rsViewProjectPlanDetail.getBlob("UPLOADED_DOCUMENT"));
			  }
//			  return fileByte;
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
				DbConnection.closeResultset(rsViewProjectPlanDetail);
				DbConnection.closeCallableStatement(proc);
				NpdConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new NpdException("Exception : " + e.getMessage(),e);
			}
		}
		return fileByte;
	}

	public byte[] DownloadFileRFI(ProjectPlanReportDto objProjectPlan) throws NpdException
	{
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rsViewProjectPlanDetail = null;
		byte[] fileByte = null;
		
		try
		{connection=NpdConnection.getConnectionObject();
			
			String query=null;
			query=" SELECT * FROM NPD.V_RFI_ACTION_DETAILS" ;
			//generating condr=tion for where 
			String whereCondition="";
			String condition;
			if(objProjectPlan.getProjectActionId()!=0)
			{
				condition=Utility.generateIntCondition(objProjectPlan.getProjectActionId(), "PROJECTACTIONDOCID");
				whereCondition=Utility.addToCondition(whereCondition,condition);
			}
			
			if(!(whereCondition.trim().equals("")))
			{
				query=query+" WHERE "+whereCondition;
			}
			  connection.setAutoCommit(false);
			  
			  proc=connection.prepareCall(query);
			  //proc.setLong(1,objProjectPlan.getTaskid());
			  rsViewProjectPlanDetail = proc.executeQuery();
			  while(rsViewProjectPlanDetail.next())
			  {
				  CommonBaseModel objModel = new CommonBaseModel(); 
				  fileByte= objModel.blobToByteArray(rsViewProjectPlanDetail.getBlob("UPLOADED_DOCUMENT"));
			  }
//			  return fileByte;
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
				DbConnection.closeResultset(rsViewProjectPlanDetail);
				DbConnection.closeCallableStatement(proc);
				NpdConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new NpdException("Exception : " + e.getMessage(),e);
			}
		}
		return fileByte;
	}

	public ArrayList<ProjectPlanReportDto> fetchRfiActionDetails(ProjectPlanReportDto objProjectPlan) throws NpdException
	{
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rsViewProjectPlanDetail = null;
		ArrayList<ProjectPlanReportDto> listProjectrfiaction= new ArrayList<ProjectPlanReportDto>();
		ProjectPlanReportDto objDto = null;

		
		try
		{connection=NpdConnection.getConnectionObject();
			
			String query=null;
			query=" SELECT * FROM NPD.V_RFI_ACTION_DETAILS " ;
			//generating condr=tion for where 
			String whereCondition="";
			String condition;

			condition=Utility.generateRelativeCondition("IN", objProjectPlan.getSearchprojectid(), "", "PROJECT_ID");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			if(!(whereCondition.trim().equals("")))
			{
				query=query+" WHERE "+whereCondition;
			}
			
			query=query+" ORDER BY PROJECT_ID , ACTIONDATE";
			  connection.setAutoCommit(false);
			  
			  proc=connection.prepareCall(query);
			  //proc.setLong(1,objProjectPlan.getTaskid());
			  rsViewProjectPlanDetail = proc.executeQuery();
			  TtrnProject project = null;
			  while(rsViewProjectPlanDetail.next())
			  {
				  objDto = new ProjectPlanReportDto();
				  project = new TtrnProject();
				  objDto.setAssignedtoid(rsViewProjectPlanDetail.getString("ACTIONRFIRAISEDTO"));
				  objDto.setAssignedtoname(rsViewProjectPlanDetail.getString("EMPNAME"));
				  objDto.setAssignedtoroleid(rsViewProjectPlanDetail.getString("ACTIONRFIRAISEDTO_ROLE"));
				  objDto.setAssignedtorolename(rsViewProjectPlanDetail.getString("ROLENAME"));
				  objDto.setActiontaken(rsViewProjectPlanDetail.getString("TASKACTIONTAKEN"));
				  objDto.setActiondate(rsViewProjectPlanDetail.getDate("ACTIONDATE"));
				  objDto.setActionremarks(rsViewProjectPlanDetail.getString("ACTIONREMARKS"));
				  objDto.setRfitaskname(rsViewProjectPlanDetail.getString("TASKNAME"));
				  objDto.setRfidocname(rsViewProjectPlanDetail.getString("DOCNAME"));
				  objDto.setRfidocid(rsViewProjectPlanDetail.getString("PROJECTACTIONDOCID"));
				  project.setProjectName(rsViewProjectPlanDetail.getString("PROJECT_NAME"));
				  project.setProjectid(rsViewProjectPlanDetail.getInt("PROJECT_ID"));
				  objDto.setProject(project);
				  listProjectrfiaction.add(objDto);
			  }
//			  return fileByte;
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
				DbConnection.closeResultset(rsViewProjectPlanDetail);
				DbConnection.closeCallableStatement(proc);
				NpdConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new NpdException("Exception : " + e.getMessage(),e);
			}
		}
		return listProjectrfiaction;
	}

	public ArrayList<ProjectPlanReportDto> fetchProjectDocReport(ProjectPlanReportDto objProjectPlan) throws NpdException
	{
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rsViewProjectPlanDetail = null;
		ArrayList<ProjectPlanReportDto> listProjectPlan = new ArrayList<ProjectPlanReportDto>();
		ProjectPlanReportDto objDto = null;
		try
		{connection=NpdConnection.getConnectionObject();
			
			String query=null;
			query=" SELECT * FROM NPD.VW_PROJECTDOCREPORT " ;
			//generating condr=tion for where 
			String whereCondition="";
			String condition;
			String dateFilter=objProjectPlan.getDateFilter();
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
			
			
			if("plannedStartDate".equals(dateFilter))
			{
				try {
					condition=Utility.generateDateRelativeCondition("BETWEEN",objProjectPlan.getFromDate(),objProjectPlan.getToDate(),"TASKSTARTDATE",new SimpleDateFormat("dd/MM/yyyy"));
					whereCondition=Utility.addToCondition(whereCondition,condition);
				} catch (Exception ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
					throw new NpdException("Exception : " + ex.getMessage(),ex);
				}
			}
			
			if("plannedEndDate".equals(dateFilter))
			{
				try {
					condition=Utility.generateDateRelativeCondition("BETWEEN",objProjectPlan.getFromDate(),objProjectPlan.getToDate(),"TASKENDDATE",new SimpleDateFormat("dd/MM/yyyy"));
					whereCondition=Utility.addToCondition(whereCondition,condition);
				} catch (Exception ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
					throw new NpdException("Exception : " + ex.getMessage(),ex);
				}
			}

			if("actualstartdate".equals(dateFilter))
			{
				try {
					condition=Utility.generateDateRelativeCondition("BETWEEN",objProjectPlan.getFromDate(),objProjectPlan.getToDate(),"ACTUALTASKSTARTDATE",new SimpleDateFormat("dd/MM/yyyy"));
					whereCondition=Utility.addToCondition(whereCondition,condition);
				} catch (Exception ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
					throw new NpdException("Exception : " + ex.getMessage(),ex);
				}
			}

			if("actualenddate".equals(dateFilter))
			{
				try {
					condition=Utility.generateDateRelativeCondition("BETWEEN",objProjectPlan.getFromDate(),objProjectPlan.getToDate(),"ACTUALTASKENDDATE",new SimpleDateFormat("dd/MM/yyyy"));
					whereCondition=Utility.addToCondition(whereCondition,condition);
				} catch (Exception ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
					throw new NpdException("Exception : " + ex.getMessage(),ex);
				}
			}


			if(objProjectPlan.getProjectStatusFilter() !=-1)
			{
				condition=Utility.generateRelativeCondition("EQUAL",String.valueOf(objProjectPlan.getProjectStatusFilter()),"","PROJECTSTATUS"); 
				//condition=Utility.generateIntCondition(objProjectPlan.getProjectStatusFilter(), "PROJECTSTATUS");
				whereCondition=Utility.addToCondition(whereCondition,condition);
			}

			
			if(objProjectPlan.getSearchProjectId()!=0)
			{
				condition=Utility.generateIntCondition(objProjectPlan.getSearchProjectId(), "WORKFLOWID");
				whereCondition=Utility.addToCondition(whereCondition,condition);
			}

			if(objProjectPlan.getSearchprojectid()!=null && !objProjectPlan.getSearchprojectid().equalsIgnoreCase(""))
			{
				condition=Utility.generateIntCondition(Integer.parseInt(objProjectPlan.getSearchprojectid()), "PROJECTID");
				whereCondition=Utility.addToCondition(whereCondition,condition);
			}


			condition=Utility.generateStringLikeCondition(objProjectPlan.getSearchprojectname(), "PROJECT_NAME");
			whereCondition=Utility.addToCondition(whereCondition,condition);

			condition=Utility.generateStringLikeCondition(objProjectPlan.getSearchStageName(), "STAGENAME");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			condition=Utility.generateStringLikeCondition(objProjectPlan.getSearchTaskName(), "TASKNAME");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			
			if(!(whereCondition.trim().equals("")))
			{
				query=query+" WHERE "+whereCondition;
			}
			
			
			//Generating Order By clause
			String orderByColumn="";
			String ASC_DESC="DESC";
			PagingSorting pagingSorting =objProjectPlan.getPagingSorting();
			String sortBy=pagingSorting.getSortByColumn();
			
			
			if("createdate".equals(sortBy))
			{
				orderByColumn="CREATEDDATE";
			}
			else if("stageName".equals(sortBy))
			{
				orderByColumn="STAGENAME";
			}
			else if("projectid".equals(sortBy))
			{
				orderByColumn="PROJECTID";
			}
			else if("projectname".equals(sortBy))
			{
				orderByColumn="PROJECT_NAME";
			}

			else if("taskName".equals(sortBy))
			{
				orderByColumn="TASKNAME";
			}
			else if("plannedstartdate".equals(sortBy))
			{
				orderByColumn="TASKSTARTDATE";
			}
			else if("plannedenddate".equals(sortBy))
			{
				orderByColumn="TASKENDDATE";
			}
			else if("actualstartdate".equals(sortBy))
			{
				orderByColumn="ACTUALTASKSTARTDATE";
			}
			else if("actualenddate".equals(sortBy))
			{
				orderByColumn="ACTUALTASKENDDATE";
			}
			else if("stakeholderrole".equals(sortBy))
			{
				orderByColumn="ROLENAME";
			}
			else if("stakeholdername".equals(sortBy))
			{
				orderByColumn="TASKSTAKEHOLDER";
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
			  rsViewProjectPlanDetail = proc.executeQuery();
			  TtrnProject project = null;
			  while(rsViewProjectPlanDetail.next())
			  {
				  objDto =  new ProjectPlanReportDto();
				  project = new TtrnProject();
				  objDto.setStagename(rsViewProjectPlanDetail.getString("STAGENAME"));
				  objDto.setTaskname(rsViewProjectPlanDetail.getString("TASKNAME"));
				  objDto.setPlannedstartdate(rsViewProjectPlanDetail.getDate("TASKSTARTDATE"));
				  objDto.setPlannedenddate(rsViewProjectPlanDetail.getDate("TASKENDDATE"));
				  objDto.setActualstartdate(rsViewProjectPlanDetail.getDate("ACTUALTASKSTARTDATE"));
				  objDto.setActualenddate(rsViewProjectPlanDetail.getDate("ACTUALTASKENDDATE"));
				  objDto.setStakeholdername(rsViewProjectPlanDetail.getString("TASKSTAKEHOLDER"));
				  objDto.setStakeholderrole(rsViewProjectPlanDetail.getString("ROLENAME"));			  
				  objDto.setDelayindays(rsViewProjectPlanDetail.getInt("DELAYINDAYS"));
				  objDto.setDocName(rsViewProjectPlanDetail.getString("DOCNAME"));
				  objDto.setProjectActionId(rsViewProjectPlanDetail.getInt("PROJECTACTIONDOCID"));
				  objDto.setCurrentTaskStatus(rsViewProjectPlanDetail.getString("CURRENTTASKSTATUS"));
				  project.setProjectName(rsViewProjectPlanDetail.getString("PROJECT_NAME"));
				  project.setProjectid(rsViewProjectPlanDetail.getInt("PROJECTID"));
				  objDto.setProjectStatus(rsViewProjectPlanDetail.getInt("PROJECTSTATUS"));
				  objDto.setCheckFlag(rsViewProjectPlanDetail.getInt("CHECKFLAG"));
				  objDto.setProject(project);
				  //
				  
				  listProjectPlan.add(objDto);
			  }
//			  return listProjectPlan;
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
				DbConnection.closeResultset(rsViewProjectPlanDetail);
				DbConnection.closeCallableStatement(proc);
				NpdConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new NpdException("Exception : " + e.getMessage(),e);
			}
		}
		return listProjectPlan;
	}

	public byte[] DownloadProjectDocFile(ProjectPlanReportDto objProjectPlan) throws NpdException
	{
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rsViewProjectPlanDetail = null;
		byte[] fileByte = null;
		
		try
		{connection=NpdConnection.getConnectionObject();
			
			String query=null;
			query=" SELECT * FROM NPD.VW_PROJECTDOCREPORT" ;
			//generating condr=tion for where 
			String whereCondition="";
			String condition;
			if(objProjectPlan.getProjectActionId()!=0)
			{
				condition=Utility.generateIntCondition(objProjectPlan.getProjectActionId(), "PROJECTACTIONDOCID");
				whereCondition=Utility.addToCondition(whereCondition,condition);
			}
					
			condition=Utility.generateRelativeCondition("EQUAL", String.valueOf(objProjectPlan.getCheckFlag()), "", "CHECKFLAG");
			whereCondition=Utility.addToCondition(whereCondition,condition);

			if(!(whereCondition.trim().equals("")))
			{
				query=query+" WHERE "+whereCondition;
			}
			
			  connection.setAutoCommit(false);
			  
			  proc=connection.prepareCall(query);
			  //proc.setLong(1,objProjectPlan.getTaskid());
			  rsViewProjectPlanDetail = proc.executeQuery();
			  while(rsViewProjectPlanDetail.next())
			  {
				  CommonBaseModel objModel = new CommonBaseModel(); 
				  fileByte= objModel.blobToByteArray(rsViewProjectPlanDetail.getBlob("UPLOADED_DOCUMENT"));
			  }
//			  return fileByte;
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
				DbConnection.closeResultset(rsViewProjectPlanDetail);
				DbConnection.closeCallableStatement(proc);
				NpdConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new NpdException("Exception : " + e.getMessage(),e);
			}
		}
		return fileByte;
	}
	
	public ArrayList<ProjectPlanReportDto> viewChart(ProjectPlanReportDto objProjectPlan,String strPath) throws NpdException
	{
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rsViewProjectPlanDetail = null;
		ArrayList<ProjectPlanReportDto> listProjectPlan = new ArrayList<ProjectPlanReportDto>();
		ArrayList<ProjectPlanReportDto> TaskListMapping=new ArrayList<ProjectPlanReportDto>();
		ProjectPlanReportDto objDto = null;
		String chartStartTag=null;
		String categoriesHeader=null;
		String categoriesMonthTag=null;
		String processesHeaderTag=null;
		String dataTableStartTag=null;
		String dataColumnStartDateStartTag=null;
		String dataColumnStartDateEndTag=null;
		String dataColumnEndDateStartTag=null;
		String dataColumnEndDateEndTag=null;
		String textStartDateTag=null;
		String dataColumnEndTag=null;
		String textEndDateTag=null;
		String dataColumnDurationStartTag=null;
		String dataColumnDurationEndTag=null;
		String textDurationTag=null;
		String dataTableEndTag=null;
		String tasksStartTag=null;
		String tasksEndTag=null;
		String taskTag=null;
		String connectorsStartTag=null;
		String connector=null;
		String connectorsEndTag=null;
		String chartEndTag=null;
		String processesStartTag=null;
		String processesEndTag=null;
		String processTaskNameTag=null;
		String categoryMonthTag=null;
		String categoriesMonthStartTag=null;
		String categoriesMonthEndTag=null;
		String finalXMLStr=null;
		String dataColumnResourceStartTag=null;
		String dataColumnResourceEndTag=null;
		String textResourceTag=null;
		ResultSet rsViewTaskPrevious = null;
		ResultSet rsTaskCount=null;
		ResultSet rsViewDistinctStage=null;
		try
		{
		    connection=NpdConnection.getConnectionObject();
		    ArrayList<String> colorList=new ArrayList<String>();
		
			String distinctStageQuery;
			distinctStageQuery="SELECT distinct(STAGENAME) FROM NPD.VW_FETCHPROJECTPLAN";
			
			String StagewhereCondition="";
			String Stagecondition;
			
			if(objProjectPlan.getProjectPlanId()!=null && !objProjectPlan.getProjectPlanId().equalsIgnoreCase(""))
			{
				Stagecondition=Utility.generateRelativeCondition("IN", objProjectPlan.getProjectPlanId(),"", "PROJECTID");
				StagewhereCondition=Utility.addToCondition(StagewhereCondition,Stagecondition);
			}
			
			if(!(StagewhereCondition.trim().equals("")))
			{
				distinctStageQuery=distinctStageQuery+" WHERE "+StagewhereCondition;
			}
			
			connection.setAutoCommit(false);
			
			proc=connection.prepareCall(distinctStageQuery);
			//proc.setLong(1,objProjectPlan.getTaskid());
			rsViewDistinctStage = proc.executeQuery();
			  
			ArrayList<String> StageList=new ArrayList<String>();
			while(rsViewDistinctStage.next())
			{
				StageList.add(rsViewDistinctStage.getString("STAGENAME"));
			}
			
			String query=null;
			query=" SELECT * FROM NPD.VW_FETCHPROJECTPLAN " ;
			//generating condr=tion for where 
			String whereCondition="";
			String condition;
			
			if(objProjectPlan.getProjectPlanId()!=null && !objProjectPlan.getProjectPlanId().equalsIgnoreCase(""))
			{
				condition=Utility.generateRelativeCondition("IN", objProjectPlan.getProjectPlanId(),"", "PROJECTID");
				whereCondition=Utility.addToCondition(whereCondition,condition);
			}
			
			if(!(whereCondition.trim().equals("")))
			{
				query=query+" WHERE "+whereCondition;
			}
			
			String FullOrderBy=" ORDER BY TASKSTARTDATE,PROJECTID,CURRENTTASKID";
			query=query+FullOrderBy;
			  
			  connection.setAutoCommit(false);
			  
			  proc=connection.prepareCall(query);
			  //proc.setLong(1,objProjectPlan.getTaskid());
			  rsViewProjectPlanDetail = proc.executeQuery();
			  TtrnProject project = null;
			  
			  Date maxDate=new Date(0);
			  Date startDate=new Date(0);
			  
			  int colorCounter=-1;
			  HashMap<String,Integer> map_Stage_Color=new HashMap<String, Integer>(); 
			  
			  while(rsViewProjectPlanDetail.next())
			  {
				  objDto =  new ProjectPlanReportDto();
				  project = new TtrnProject();
				  		  
				  
				  objDto.setStagename(rsViewProjectPlanDetail.getString("STAGENAME"));
				  objDto.setTaskname(rsViewProjectPlanDetail.getString("TASKNAME"));
				  objDto.setPlannedstartdate(rsViewProjectPlanDetail.getDate("TASKSTARTDATE"));
				  objDto.setPlannedenddate(rsViewProjectPlanDetail.getDate("TASKENDDATE"));
				  objDto.setActualstartdate(rsViewProjectPlanDetail.getDate("ACTUALTASKSTARTDATE"));
				  objDto.setActualenddate(rsViewProjectPlanDetail.getDate("ACTUALTASKENDDATE"));
				  objDto.setStakeholdername(rsViewProjectPlanDetail.getString("TASKSTAKEHOLDER"));
				  objDto.setStakeholderrole(rsViewProjectPlanDetail.getString("ROLENAME"));			  
				  objDto.setDelayindays(rsViewProjectPlanDetail.getInt("DELAYINDAYS"));
				  project.setProjectName(rsViewProjectPlanDetail.getString("PROJECT_NAME"));
				  project.setProjectid(rsViewProjectPlanDetail.getInt("PROJECTID"));
				  objDto.setDaysInProject(rsViewProjectPlanDetail.getInt("DAYSINPROJECT"));
				  objDto.setTaskDuration(rsViewProjectPlanDetail.getInt("TASKDURATION"));
				  objDto.setTaskCompDuration(rsViewProjectPlanDetail.getInt("TaskCompDuration"));
				  objDto.setTaskid(rsViewProjectPlanDetail.getInt("CURRENTTASKID"));
				  objDto.setProject(project);
				  listProjectPlan.add(objDto);
				  
				  
				  String stageName=objDto.getStagename();
				  Integer colorIndex=map_Stage_Color.get(stageName);
				  if(colorIndex==null)
				  {
					  colorCounter++;
					  map_Stage_Color.put(stageName, new Integer(colorCounter));
					  colorIndex=colorCounter;
				  }
				  
				  String color=Utility.colorCodes[colorIndex%15];
				  System.err.println("Color "+color +", stage :"+stageName);
				  
				  
				  
				  if(maxDate.before(objDto.getPlannedenddate()))
				  {
					  maxDate=new Date(objDto.getPlannedenddate().getTime());
				  }
				  
				  if(rsViewProjectPlanDetail.getDate("STARTDATE")!= null)
				  {
					  startDate=rsViewProjectPlanDetail.getDate("STARTDATE");
				  }
				 				 
				  if (processTaskNameTag==null)
				  {
					  processTaskNameTag="<process Name='"+objDto.getTaskname()+"' id='"+objDto.getTaskid()+"' fontcolor='"+color+"'/>\n";
				  }
				  else
				  {
					  processTaskNameTag=processTaskNameTag+"<process Name='"+objDto.getTaskname()+"' id='"+objDto.getTaskid()+"' fontcolor='"+color+"'/>\n";
				  }
				  if (textStartDateTag==null)
				  {
					  textStartDateTag="<text label='"+objDto.getPlannedstartdateGanttString()+"'/>\n";
				  }
				  else
				  {
					  textStartDateTag=textStartDateTag+"<text label='"+objDto.getPlannedstartdateGanttString()+"'/>\n";
				  }
				  if (textEndDateTag==null)
				  {
					  textEndDateTag="<text label='"+objDto.getPlannedenddateGanttString()+"'/>\n";
				  }
				  else
				  {
					  textEndDateTag=textEndDateTag+"<text label='"+objDto.getPlannedenddateGanttString()+"'/>\n";
				  }
				  if (textDurationTag==null)
				  {
					  textDurationTag="<text label='"+objDto.getTaskDuration()+"'/>\n";
				  }
				  else
				  {
					  textDurationTag=textDurationTag+"<text label='"+objDto.getTaskDuration()+"'/>\n";
				  }
				  if (textResourceTag==null)
				  {
					  textResourceTag="<text label='"+objDto.getStakeholdername()+"'/>\n";
				  }
				  else
				  {
					  textResourceTag=textResourceTag+"<text label='"+objDto.getStakeholdername()+"'/>\n";
				  }
				  if (taskTag==null)
				  {
					  taskTag="<task name='Planned' processId='"+objDto.getTaskid()+"' start='"+objDto.getPlannedstartdateGanttString()+"' end='"+objDto.getPlannedenddateGanttString()+"' id='"+objDto.getTaskid()+"-1' color='4567aa' height='4' topPadding='2' animation='1'/>\n";
					  if((!objDto.getActualstartdateGanttString().equalsIgnoreCase("")) && (!objDto.getActualenddateGanttString().equalsIgnoreCase("")))
					  {
						  taskTag=taskTag+"<task name='Actual' processId='"+objDto.getTaskid()+"' start='"+objDto.getActualstartdateGanttString()+"' end='"+objDto.getActualenddateGanttString()+"' id='"+objDto.getTaskid()+"' color='000000' alpha='100'  topPadding='8' height='4' />\n";
					  }
					  if((!objDto.getActualstartdateGanttString().equalsIgnoreCase("")) && (objDto.getActualenddateGanttString().equalsIgnoreCase("")))
					  {
						  taskTag=taskTag+"<task name='Actual' processId='"+objDto.getTaskid()+"' start='"+objDto.getActualstartdateGanttString()+"' end='' id='1' color='ffff00' alpha='100'  topPadding='8' height='4' />\n";
					  }
				  }
				  else
				  {
					  taskTag=taskTag+"<task name='Planned' processId='"+objDto.getTaskid()+"' start='"+objDto.getPlannedstartdateGanttString()+"' end='"+objDto.getPlannedenddateGanttString()+"' id='"+objDto.getTaskid()+"-1' color='4567aa' height='4' topPadding='2' animation='1'/>\n";
					  if((!objDto.getActualstartdateGanttString().equalsIgnoreCase("")) && (!objDto.getActualenddateGanttString().equalsIgnoreCase("")))
					  {
						  taskTag=taskTag+"<task name='Actual' processId='"+objDto.getTaskid()+"' start='"+objDto.getActualstartdateGanttString()+"' end='"+objDto.getActualenddateGanttString()+"' id='"+objDto.getTaskid()+"' color='000000' alpha='100'  topPadding='8' height='4' />\n";
					  }
					  if((!objDto.getActualstartdateGanttString().equalsIgnoreCase("")) && (objDto.getActualenddateGanttString().equalsIgnoreCase("")))
					  {
						  taskTag=taskTag+"<task name='Actual' processId='"+objDto.getTaskid()+"' start='"+objDto.getActualstartdateGanttString()+"' end='' id='1' color='ffff00' alpha='100'  topPadding='8' height='4' />\n";
					  }
				  }
			  }
			  
			  objProjectPlan.setMap_Stage_Color(map_Stage_Color);
			  
			  Date tempMaxDate=new Date(maxDate.getTime());
			  Calendar cal=GregorianCalendar.getInstance();
			  cal.setTime(tempMaxDate);
			  int maxDay=cal.getActualMaximum(Calendar.DATE);
			  cal.set(Calendar.DATE, maxDay);
			  
			  
			  String maxDateString=new SimpleDateFormat("dd/MM/yyyy").format(cal.getTime());
			 			  
			  Date tempStartDate=new Date(startDate.getTime());
			  tempStartDate.setDate(1);
			  
			  String startDateString=new SimpleDateFormat("dd/MM/yyyy").format(tempStartDate);
			  
			  ArrayList<String> monthYear=new ArrayList<String>(); 
			  monthYear=Utility.monthYear(startDate, maxDate);
			  ArrayList<String> firstDay=new ArrayList<String>(); 
			  firstDay=Utility.firstDate(startDate, maxDate);
			  ArrayList<String> lastDate=new ArrayList<String>(); 
			  lastDate=Utility.lastDate(startDate, maxDate);
			  for(int i=0;i<monthYear.size();i++)
			  {
				  if(i==0)
				  {
					  categoryMonthTag="<category start='"+firstDay.get(i)+"' end='"+lastDate.get(i)+"' align='center' name='"+monthYear.get(i)+"' isBold='1' />\n";
				  }
				  else
				  {
					  categoryMonthTag=categoryMonthTag + "<category start='"+firstDay.get(i)+"' end='"+lastDate.get(i)+"' align='center' name='"+monthYear.get(i)+"' isBold='1' />\n";
				  }
				  
			  }
			  
			//For fetching Total task in a project Starts
			String taskquery=null;
			taskquery=" SELECT count(1) as TotalProjectTasks FROM NPD.VW_FETCHPROJECTPLAN " ;
			//generating condr=tion for where 
			String whereCondition4="";
			String condition4;
				
			if(objProjectPlan.getProjectPlanId()!=null && !objProjectPlan.getProjectPlanId().equalsIgnoreCase(""))
			{
				condition4=Utility.generateRelativeCondition("IN", objProjectPlan.getProjectPlanId(),"", "PROJECTID");
				whereCondition4=Utility.addToCondition(whereCondition4,condition4);
			}
			
			if(!(whereCondition4.trim().equals("")))
			{
				taskquery=taskquery+" WHERE "+whereCondition4;
			}
			  
			connection.setAutoCommit(false);
			proc=connection.prepareCall(taskquery);
			//proc.setLong(1,objProjectPlan.getTaskid());
			rsTaskCount = proc.executeQuery();
			 
			while(rsTaskCount.next())
			{
				objDto =  new ProjectPlanReportDto();
				objDto.setProjectTaskCount(rsTaskCount.getInt("TotalProjectTasks"));
			}
			objProjectPlan.setProjectTaskCount(objDto.getProjectTaskCount());
			//For fetching Total task in a project Ends
			  
			if(objProjectPlan.getProjectTaskCount()<=100)
			{
				chartStartTag="<chart dateFormat='dd/mm/yyyy' hoverCapBorderColor='2222ff' hoverCapBgColor='e1f5ff' ganttWidthPercent='72' ganttLineAlpha='100' canvasBorderColor='024455' canvasBorderThickness='0' gridBorderColor='4567aa' gridBorderAlpha='20'>\n";
			}
			if((objProjectPlan.getProjectTaskCount()>100) && (objProjectPlan.getProjectTaskCount()<=200))
			{
				chartStartTag="<chart dateFormat='dd/mm/yyyy' hoverCapBorderColor='2222ff' hoverCapBgColor='e1f5ff' ganttWidthPercent='80' ganttLineAlpha='100' canvasBorderColor='024455' canvasBorderThickness='0' gridBorderColor='4567aa' gridBorderAlpha='20'>\n";
			}
			if(objProjectPlan.getProjectTaskCount()>200)
			{
				chartStartTag="<chart dateFormat='dd/mm/yyyy' hoverCapBorderColor='2222ff' hoverCapBgColor='e1f5ff' ganttWidthPercent='86.7' ganttLineAlpha='100' canvasBorderColor='024455' canvasBorderThickness='0' gridBorderColor='4567aa' gridBorderAlpha='20'>\n";
			}
			  
			  categoriesHeader="<categories  bgColor='009999'><category start='"+startDateString+"' end='"+maxDateString+"' align='left' name='Gantt Chart for "+project.getProjectName()+"'  fontColor='ffffff' isBold='1' fontSize='16' /></categories>\n";
			  categoriesMonthTag="<categories  bgColor='4567aa' fontColor='ff0000'><category start='"+startDateString+"' end='"+maxDateString+"' align='center' name='Months'  alpha='' font='Verdana' fontColor='ffffff' isBold='1' fontSize='16' /></categories><categories  bgColor='ffffff' fontColor='1288dd' fontSize='10' >\n";
			  processesStartTag="</categories><processes headerText='Tasks' fontColor='ffffff' fontSize='8' isBold='1' isAnimated='1' bgColor='CCCCCC'  headerVAlign='right' headerbgColor='4567aa' headerFontColor='ffffff' headerFontSize='16' width='295' align='left'>\n";
			  processesEndTag="</processes>\n";
			  dataTableStartTag="<dataTable showProcessName='1' nameAlign='left' fontColor='000000' fontSize='8' isBold='1' headerBgColor='00ffff' headerFontColor='4567aa' headerFontSize='11' vAlign='middle' align='left'>\n";
			  dataColumnStartDateStartTag="<dataColumn width='75' headerfontcolor='ffffff' headerBgColor='4567aa' bgColor='eeeeee'  headerColor='ffffff' headerText='Start' isBold='0'>";
			  dataColumnStartDateEndTag="</dataColumn>\n";
			  dataColumnEndDateStartTag="<dataColumn width='75' headerfontcolor='ffffff'  bgColor='eeeeee' headerbgColor='4567aa'  fontColor='000000' headerText='Finish' isBold='0'>\n";
			  dataColumnEndDateEndTag="</dataColumn>\n";
			  dataColumnEndTag="</dataColumn>\n";
			  dataColumnDurationStartTag="<dataColumn align='center' headerfontcolor='ffffff'  headerbgColor='4567aa'  bgColor='eeeeee' headerText='Dur.' width='50' isBold='0'>\n";
			  dataColumnDurationEndTag="</dataColumn>\n";
			  dataColumnResourceStartTag="<dataColumn align='center' headerfontcolor='ffffff'  headerbgColor='4567aa'  bgColor='eeeeee' headerText='Resource' width='50' isBold='0'>\n";
			  dataColumnResourceEndTag="</dataColumn>\n";
			  dataTableEndTag="</dataTable>\n";
			  tasksStartTag="<tasks  width='10'>\n";
			  tasksEndTag="</tasks>\n";
			  connectorsStartTag="<connectors>\n";
			  connectorsEndTag="</connectors>\n";
			  chartEndTag="</chart>\n";
			  
			  //TO FECTH TASKING MAPPING I.E. TASK ID AND PREV TASK ID
			  String queryPrevTask=null;
			  queryPrevTask=" SELECT * FROM NPD.VW_PROJECTPREVTASK " ;
				//generating condr=tion for where 
				String whereConditionPrevTask="";
				String conditionPrevTask;
				
				if(objProjectPlan.getProjectPlanId()!=null && !objProjectPlan.getProjectPlanId().equalsIgnoreCase(""))
				{
					conditionPrevTask=Utility.generateRelativeCondition("IN", objProjectPlan.getProjectPlanId(),"", "PROJECTID");
					whereConditionPrevTask=Utility.addToCondition(whereConditionPrevTask,conditionPrevTask);
				}
				
				if(!(whereConditionPrevTask.trim().equals("")))
				{
					queryPrevTask=queryPrevTask+" WHERE "+whereConditionPrevTask;
				}
				
								  
			    connection.setAutoCommit(false);
			  
			    proc=connection.prepareCall(queryPrevTask);
			   //proc.setLong(1,objProjectPlan.getTaskid());
			    rsViewTaskPrevious = proc.executeQuery();
			  
				while(rsViewTaskPrevious.next())
				{
					objDto =  new ProjectPlanReportDto();
					objDto.setCurTaskID(rsViewTaskPrevious.getString("TASKID"));
					objDto.setPrvTaskID(rsViewTaskPrevious.getString("PREVTASKID"));
					TaskListMapping.add(objDto);
					if(connector==null)
					{
						connector="<connector fromTaskId='"+objDto.getCurTaskID()+"-1' toTaskId='"+objDto.getPrvTaskID()+"-1' color='4567aa' thickness='2' fromTaskConnectStart='1'/>\n";
					}
					else
					{
						connector=connector + "<connector fromTaskId='"+objDto.getCurTaskID()+"-1' toTaskId='"+objDto.getPrvTaskID()+"-1' color='4567aa' thickness='2' fromTaskConnectStart='1'/>\n";
					}
				}

			  if (finalXMLStr==null)
			  {
				  finalXMLStr=chartStartTag+categoriesHeader+categoriesMonthTag+categoryMonthTag+	
				  processesStartTag+processTaskNameTag+processesEndTag+dataTableStartTag+
				  dataColumnStartDateStartTag+textStartDateTag+dataColumnStartDateEndTag+dataColumnEndDateStartTag+textEndDateTag+
				  dataColumnEndDateEndTag+dataColumnDurationStartTag+textDurationTag+dataColumnDurationEndTag+
				  dataColumnResourceStartTag+textResourceTag+dataColumnResourceEndTag+dataTableEndTag+tasksStartTag+
				  taskTag+tasksEndTag+connectorsStartTag+connector+connectorsEndTag+chartEndTag;
			  }
			  String fileName;
			  fileName="Gantt-"+objProjectPlan.getProjectPlanId()+"-"+System.currentTimeMillis()+".xml";
			  
			  File f=new File(strPath+"/"+fileName);
			  System.out.println(f);
			  try 
			  {
					f.createNewFile();
					FileOutputStream outputStream=new FileOutputStream(f);
					byte[] data= finalXMLStr.getBytes();
					outputStream.write(data);
					outputStream.close();
					objProjectPlan.setFile_Name_Chart(fileName);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  //return listProjectPlan;
			  
			  //Fetching Task Status Count for TASK Status PIE Chart
			  String query1=null;
			  query1=" SELECT * FROM NPD.VW_TASKSTATUS_PIECHART" ;
			  //generating condr=tion for where 
			  String whereCondition1="";
			  String condition1;
			  ArrayList<ProjectPlanReportDto> taskStatuslist = new ArrayList<ProjectPlanReportDto>();
			  if(objProjectPlan.getProjectPlanId()!=null && !objProjectPlan.getProjectPlanId().equalsIgnoreCase(""))
			  {
				condition1=Utility.generateRelativeCondition("IN", objProjectPlan.getProjectPlanId(),"", "PROJECTID");
				whereCondition1=Utility.addToCondition(whereCondition1,condition1);
			  }
				
			  if(!(whereCondition1.trim().equals("")))
			  {
				  query1=query1+" WHERE "+whereCondition1;
			  }
				
			  connection.setAutoCommit(false);
			  
			  proc=connection.prepareCall(query1);
			  ResultSet rsViewTaskCount = null;
			  //proc.setLong(1,objProjectPlan.getTaskid());
			  rsViewTaskCount = proc.executeQuery();
			  
			  while(rsViewTaskCount.next())
			  {
				  objDto=new ProjectPlanReportDto();
				  objDto.setTaskCount(rsViewTaskCount.getString("TaskCount"));
				  objDto.setTaskStatusString(rsViewTaskCount.getString("TaskStatus"));
				  objDto.setTaskStatusQuery("javascript:TaskStatusPieChart('Closed'),javascript:TaskStatusPieChart('Open'),javascript:TaskStatusPieChart('Not Started')");
				  taskStatuslist.add(objDto);
			  }
			  objProjectPlan.setTaskStatusList(taskStatuslist);
			  
			  //Fetching Task Delay Count for Closed Task-PIE Chart
			  String query2=null;
			  query2=" SELECT * FROM NPD.VW_TASKDELAY_PIECHART" ;
			  //generating condr=tion for where 
			  String whereCondition2="";
			  String condition2;
			  ArrayList<ProjectPlanReportDto> taskDelaylist = new ArrayList<ProjectPlanReportDto>();
			  if(objProjectPlan.getProjectPlanId()!=null && !objProjectPlan.getProjectPlanId().equalsIgnoreCase(""))
			  {
				condition2=Utility.generateRelativeCondition("IN", objProjectPlan.getProjectPlanId(),"", "PROJECTID");
				whereCondition2=Utility.addToCondition(whereCondition2,condition2);
			  }
				
			  if(!(whereCondition2.trim().equals("")))
			  {
				  query2=query2+" WHERE "+whereCondition2;
			  }
				
			  connection.setAutoCommit(false);
			  
			  proc=connection.prepareCall(query2);
			  ResultSet rsViewTaskDelayCount = null;
			  rsViewTaskDelayCount = proc.executeQuery();
			  
			  while(rsViewTaskDelayCount.next())
			  {
				  objDto=new ProjectPlanReportDto();
				  objDto.setTaskDelayCount(rsViewTaskDelayCount.getString("TaskDelayCount"));
				  objDto.setTaskDelayString(rsViewTaskDelayCount.getString("TaskDelayStatus"));
				  objDto.setTaskDelayQuery("javascript:TaskDelayPieChart('Delay'),javascript:TaskDelayPieChart('No Delay')");
				  taskDelaylist.add(objDto);
			  }
			  objProjectPlan.setTaskDelayList(taskDelaylist);
			  
			  //Fetching Task Delay Count for Closed Task-PIE Chart
			  String query3=null;
			  query3=" SELECT * FROM NPD.VW_TASKINMONTH_PIECHART" ;
			  //generating condr=tion for where 
			  String whereCondition3="";
			  String condition3;
			  ArrayList<ProjectPlanReportDto> taskInMonthlist = new ArrayList<ProjectPlanReportDto>();
			  if(objProjectPlan.getProjectPlanId()!=null && !objProjectPlan.getProjectPlanId().equalsIgnoreCase(""))
			  {
				condition3=Utility.generateRelativeCondition("IN", objProjectPlan.getProjectPlanId(),"", "PROJECTID");
				whereCondition3=Utility.addToCondition(whereCondition3,condition3);
			  }
				
			  if(!(whereCondition3.trim().equals("")))
			  {
				  query3=query3+" WHERE "+whereCondition3+" ORDER BY MONTH_NUM,YEAR_NO";
			  }
				
			  connection.setAutoCommit(false);
			  
			  proc=connection.prepareCall(query3);
			  ResultSet rsViewTaskInMonthCount = null;
			  rsViewTaskInMonthCount = proc.executeQuery();
			  
			  while(rsViewTaskInMonthCount.next())
			  {
				  objDto=new ProjectPlanReportDto();
				  objDto.setTotalTaskCount(rsViewTaskInMonthCount.getString("TOTALTASK"));
				  objDto.setMonth_Name(rsViewTaskInMonthCount.getString("Month_Name"));
				  objDto.setMonthQuery("javascript:TaskMonthTaskPieChart('"+rsViewTaskInMonthCount.getString("Month_Name")+"')");
				  taskInMonthlist.add(objDto);
			  }
			  objProjectPlan.setTaskInMonthList(taskInMonthlist);
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
				DbConnection.closeResultset(rsViewProjectPlanDetail);
				DbConnection.closeCallableStatement(proc);
				NpdConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new NpdException("Exception : " + e.getMessage(),e);
			}
		}

		return listProjectPlan;
	}
	
	public ArrayList<ProjectPlanReportDto> viewStageGanttChart(ProjectPlanReportDto objProjectPlan,String strPath) throws NpdException
	{
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rsViewProjectPlanDetail = null;
		ArrayList<ProjectPlanReportDto> listProjectPlan = new ArrayList<ProjectPlanReportDto>();
		ArrayList<ProjectPlanReportDto> TaskListMapping=new ArrayList<ProjectPlanReportDto>();
		ProjectPlanReportDto objDto = null;
		String chartStartTag=null;
		String categoriesHeader=null;
		String categoriesMonthTag=null;
		String processesHeaderTag=null;
		String dataTableStartTag=null;
		String dataColumnStartDateStartTag=null;
		String dataColumnStartDateEndTag=null;
		String dataColumnEndDateStartTag=null;
		String dataColumnEndDateEndTag=null;
		String textStartDateTag=null;
		String dataColumnEndTag=null;
		String textEndDateTag=null;
		String dataColumnDurationStartTag=null;
		String dataColumnDurationEndTag=null;
		String textDurationTag=null;
		String dataTableEndTag=null;
		String tasksStartTag=null;
		String tasksEndTag=null;
		String taskTag=null;
		String connectorsStartTag=null;
		String connector=null;
		String connectorsEndTag=null;
		String chartEndTag=null;
		String processesStartTag=null;
		String processesEndTag=null;
		String processTaskNameTag=null;
		String categoryMonthTag=null;
		String categoriesMonthStartTag=null;
		String categoriesMonthEndTag=null;
		String finalXMLStr=null;
		String dataColumnResourceStartTag=null;
		String dataColumnResourceEndTag=null;
		String textResourceTag=null;
		ResultSet rsViewTaskPrevious = null;
		ResultSet rsTaskCount=null;
		ResultSet rsViewDistinctStage=null;
		try
		{
		    connection=NpdConnection.getConnectionObject();
		    
			String query=null;
			query=" SELECT * FROM NPD.VW_FETCHPROJECTPLAN " ;
			//generating condr=tion for where 
			String whereCondition="";
			String condition;
			
			if(objProjectPlan.getProjectPlanId()!=null && !objProjectPlan.getProjectPlanId().equalsIgnoreCase(""))
			{
				condition=Utility.generateRelativeCondition("IN", objProjectPlan.getProjectPlanId(),"", "PROJECTID");
				whereCondition=Utility.addToCondition(whereCondition,condition);
			}
			
			condition=Utility.generateStringLikeCondition(objProjectPlan.getStagename(), "STAGENAME");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			if(!(whereCondition.trim().equals("")))
			{
				query=query+" WHERE "+whereCondition;
			}
			
			String FullOrderBy=" ORDER BY TASKSTARTDATE,PROJECTID,CURRENTTASKID";
			query=query+FullOrderBy;
			  
			  connection.setAutoCommit(false);
			  
			  proc=connection.prepareCall(query);
			  //proc.setLong(1,objProjectPlan.getTaskid());
			  rsViewProjectPlanDetail = proc.executeQuery();
			  TtrnProject project = null;
			  
			  Date maxDate=new Date(0);
			  Date startDate=null;
			  
			  
			  
			  while(rsViewProjectPlanDetail.next())
			  {
				  objDto =  new ProjectPlanReportDto();
				  project = new TtrnProject();
				  		  
				  
				  objDto.setStagename(rsViewProjectPlanDetail.getString("STAGENAME"));
				  objDto.setTaskname(rsViewProjectPlanDetail.getString("TASKNAME"));
				  objDto.setPlannedstartdate(rsViewProjectPlanDetail.getDate("TASKSTARTDATE"));
				  objDto.setPlannedenddate(rsViewProjectPlanDetail.getDate("TASKENDDATE"));
				  objDto.setActualstartdate(rsViewProjectPlanDetail.getDate("ACTUALTASKSTARTDATE"));
				  objDto.setActualenddate(rsViewProjectPlanDetail.getDate("ACTUALTASKENDDATE"));
				  objDto.setStakeholdername(rsViewProjectPlanDetail.getString("TASKSTAKEHOLDER"));
				  objDto.setStakeholderrole(rsViewProjectPlanDetail.getString("ROLENAME"));			  
				  objDto.setDelayindays(rsViewProjectPlanDetail.getInt("DELAYINDAYS"));
				  project.setProjectName(rsViewProjectPlanDetail.getString("PROJECT_NAME"));
				  project.setProjectid(rsViewProjectPlanDetail.getInt("PROJECTID"));
				  objDto.setDaysInProject(rsViewProjectPlanDetail.getInt("DAYSINPROJECT"));
				  objDto.setTaskDuration(rsViewProjectPlanDetail.getInt("TASKDURATION"));
				  objDto.setTaskCompDuration(rsViewProjectPlanDetail.getInt("TaskCompDuration"));
				  objDto.setTaskid(rsViewProjectPlanDetail.getInt("CURRENTTASKID"));
				  objDto.setProject(project);
				  listProjectPlan.add(objDto);
				 				  
				  if(maxDate.before(objDto.getPlannedenddate()))
				  {
					  maxDate=new Date(objDto.getPlannedenddate().getTime());
				  }
				  
				  if(startDate==null)
				  {
					  startDate=objDto.getPlannedstartdate();
				  }else
				  if(startDate.after(objDto.getPlannedstartdate()))
				  {
					  startDate=objDto.getPlannedstartdate();
				  }
				  
				 				 
				  if (processTaskNameTag==null)
				  {
					  processTaskNameTag="<process Name='"+objDto.getTaskname()+"' id='"+objDto.getTaskid()+"' />\n";
				  }
				  else
				  {
					  processTaskNameTag=processTaskNameTag+"<process Name='"+objDto.getTaskname()+"' id='"+objDto.getTaskid()+"' />\n";
				  }
				  if (textStartDateTag==null)
				  {
					  textStartDateTag="<text label='"+objDto.getPlannedstartdateGanttString()+"' valigh='middle'/>\n";
				  }
				  else
				  {
					  textStartDateTag=textStartDateTag+"<text label='"+objDto.getPlannedstartdateGanttString()+"'/>\n";
				  }
				  if (textEndDateTag==null)
				  {
					  textEndDateTag="<text label='"+objDto.getPlannedenddateGanttString()+"'/>\n";
				  }
				  else
				  {
					  textEndDateTag=textEndDateTag+"<text label='"+objDto.getPlannedenddateGanttString()+"'/>\n";
				  }
				  if (textDurationTag==null)
				  {
					  textDurationTag="<text label='"+objDto.getTaskDuration()+"'/>\n";
				  }
				  else
				  {
					  textDurationTag=textDurationTag+"<text label='"+objDto.getTaskDuration()+"'/>\n";
				  }
				  if (textResourceTag==null)
				  {
					  textResourceTag="<text label='"+objDto.getStakeholdername()+"'/>\n";
				  }
				  else
				  {
					  textResourceTag=textResourceTag+"<text label='"+objDto.getStakeholdername()+"'/>\n";
				  }
				  if (taskTag==null)
				  {
					  taskTag="<task name='Planned' processId='"+objDto.getTaskid()+"' start='"+objDto.getPlannedstartdateGanttString()+"' end='"+objDto.getPlannedenddateGanttString()+"' id='"+objDto.getTaskid()+"-1' color='4567aa' height='4' topPadding='2' animation='1'/>\n";
					  if((!objDto.getActualstartdateGanttString().equalsIgnoreCase("")) && (!objDto.getActualenddateGanttString().equalsIgnoreCase("")))
					  {
						  taskTag=taskTag+"<task name='Actual' processId='"+objDto.getTaskid()+"' start='"+objDto.getActualstartdateGanttString()+"' end='"+objDto.getActualenddateGanttString()+"' id='"+objDto.getTaskid()+"' color='000000' alpha='100'  topPadding='8' height='4' />\n";
					  }
					  if((!objDto.getActualstartdateGanttString().equalsIgnoreCase("")) && (objDto.getActualenddateGanttString().equalsIgnoreCase("")))
					  {
						  taskTag=taskTag+"<task name='Actual' processId='"+objDto.getTaskid()+"' start='"+objDto.getActualstartdateGanttString()+"' end='' id='1' color='ffff00' alpha='100'  topPadding='8' height='4' />\n";
					  }
				  }
				  else
				  {
					  taskTag=taskTag+"<task name='Planned' processId='"+objDto.getTaskid()+"' start='"+objDto.getPlannedstartdateGanttString()+"' end='"+objDto.getPlannedenddateGanttString()+"' id='"+objDto.getTaskid()+"-1' color='4567aa' height='4' topPadding='2' animation='1'/>\n";
					  if((!objDto.getActualstartdateGanttString().equalsIgnoreCase("")) && (!objDto.getActualenddateGanttString().equalsIgnoreCase("")))
					  {
						  taskTag=taskTag+"<task name='Actual' processId='"+objDto.getTaskid()+"' start='"+objDto.getActualstartdateGanttString()+"' end='"+objDto.getActualenddateGanttString()+"' id='"+objDto.getTaskid()+"' color='000000' alpha='100'  topPadding='8' height='4' />\n";
					  }
					  if((!objDto.getActualstartdateGanttString().equalsIgnoreCase("")) && (objDto.getActualenddateGanttString().equalsIgnoreCase("")))
					  {
						  taskTag=taskTag+"<task name='Actual' processId='"+objDto.getTaskid()+"' start='"+objDto.getActualstartdateGanttString()+"' end='' id='1' color='ffff00' alpha='100'  topPadding='8' height='4' />\n";
					  }
				  }
			  }
			  
			  
			  
			  Date tempMaxDate=new Date(maxDate.getTime());
			  Calendar cal=GregorianCalendar.getInstance();
			  cal.setTime(tempMaxDate);
			  int maxDay=cal.getActualMaximum(Calendar.DATE);
			  cal.set(Calendar.DATE, maxDay);
			  
			  
			  String maxDateString=new SimpleDateFormat("dd/MM/yyyy").format(cal.getTime());
			 			  
			  Date tempStartDate=new Date(startDate.getTime());
			  tempStartDate.setDate(1);
			  
			  String startDateString=new SimpleDateFormat("dd/MM/yyyy").format(tempStartDate);
			  
			  ArrayList<String> monthYear=new ArrayList<String>(); 
			  monthYear=Utility.monthYear(startDate, maxDate);
			  ArrayList<String> firstDay=new ArrayList<String>(); 
			  firstDay=Utility.firstDate(startDate, maxDate);
			  ArrayList<String> lastDate=new ArrayList<String>(); 
			  lastDate=Utility.lastDate(startDate, maxDate);
			  for(int i=0;i<monthYear.size();i++)
			  {
				  if(i==0)
				  {
					  categoryMonthTag="<category start='"+firstDay.get(i)+"' end='"+lastDate.get(i)+"' align='center' name='"+monthYear.get(i)+"' isBold='1' />\n";
				  }
				  else
				  {
					  categoryMonthTag=categoryMonthTag + "<category start='"+firstDay.get(i)+"' end='"+lastDate.get(i)+"' align='center' name='"+monthYear.get(i)+"' isBold='1' />\n";
				  }
				  
			  }
			  
			//For fetching Total task in a project Starts
			String taskquery=null;
			taskquery=" SELECT count(1) as TotalProjectTasks FROM NPD.VW_FETCHPROJECTPLAN " ;
			//generating condr=tion for where 
			String whereCondition4="";
			String condition4;
				
			if(objProjectPlan.getProjectPlanId()!=null && !objProjectPlan.getProjectPlanId().equalsIgnoreCase(""))
			{
				condition4=Utility.generateRelativeCondition("IN", objProjectPlan.getProjectPlanId(),"", "PROJECTID");
				whereCondition4=Utility.addToCondition(whereCondition4,condition4);
			}
			
			condition=Utility.generateStringLikeCondition(objProjectPlan.getStagename(), "STAGENAME");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			if(!(whereCondition4.trim().equals("")))
			{
				taskquery=taskquery+" WHERE "+whereCondition4;
			}
			  
			connection.setAutoCommit(false);
			proc=connection.prepareCall(taskquery);
			//proc.setLong(1,objProjectPlan.getTaskid());
			rsTaskCount = proc.executeQuery();
			 
			while(rsTaskCount.next())
			{
				objDto =  new ProjectPlanReportDto();
				objDto.setProjectTaskCount(rsTaskCount.getInt("TotalProjectTasks"));
			}
			objProjectPlan.setProjectTaskCount(objDto.getProjectTaskCount());
			//For fetching Total task in a project Ends
			  
			if(objProjectPlan.getProjectTaskCount()<=100)
			{
				chartStartTag="<chart dateFormat='dd/mm/yyyy' hoverCapBorderColor='2222ff' hoverCapBgColor='e1f5ff' ganttWidthPercent='72' ganttLineAlpha='100' canvasBorderColor='024455' canvasBorderThickness='0' gridBorderColor='4567aa' gridBorderAlpha='20'>\n";
			}
			if((objProjectPlan.getProjectTaskCount()>100) && (objProjectPlan.getProjectTaskCount()<=200))
			{
				chartStartTag="<chart dateFormat='dd/mm/yyyy' hoverCapBorderColor='2222ff' hoverCapBgColor='e1f5ff' ganttWidthPercent='80' ganttLineAlpha='100' canvasBorderColor='024455' canvasBorderThickness='0' gridBorderColor='4567aa' gridBorderAlpha='20'>\n";
			}
			if(objProjectPlan.getProjectTaskCount()>200)
			{
				chartStartTag="<chart dateFormat='dd/mm/yyyy' hoverCapBorderColor='2222ff' hoverCapBgColor='e1f5ff' ganttWidthPercent='86.7' ganttLineAlpha='100' canvasBorderColor='024455' canvasBorderThickness='0' gridBorderColor='4567aa' gridBorderAlpha='20'>\n";
			}
			  
			  categoriesHeader="<categories  bgColor='009999'><category start='"+startDateString+"' end='"+maxDateString+"' align='left' name='Gantt Chart for "+project.getProjectName()+".Stage Name:"+objProjectPlan.getStagename()+"'  fontColor='ffffff' isBold='1' fontSize='16' /></categories>\n";
			  categoriesMonthTag="<categories  bgColor='4567aa' fontColor='ff0000'><category start='"+startDateString+"' end='"+maxDateString+"' align='center' name='Months'  alpha='' font='Verdana' fontColor='ffffff' isBold='1' fontSize='16' /></categories><categories  bgColor='ffffff' fontColor='1288dd' fontSize='10' >\n";
			  processesStartTag="</categories><processes headerText='Tasks' fontColor='000000' fontSize='8' isBold='1' isAnimated='1' bgColor='CCCCCC'  headerVAlign='right' headerbgColor='4567aa' headerFontColor='ffffff' headerFontSize='16' width='295' align='left'>\n";
			  processesEndTag="</processes>\n";
			  dataTableStartTag="<dataTable showProcessName='1' nameAlign='left' fontColor='000000' fontSize='8' isBold='1' headerBgColor='00ffff' headerFontColor='4567aa' headerFontSize='11' vAlign='middle' align='left'>\n";
			  dataColumnStartDateStartTag="<dataColumn width='75' headerfontcolor='ffffff' headerBgColor='4567aa' bgColor='eeeeee'  headerColor='ffffff' headerText='Start' isBold='0'>";
			  dataColumnStartDateEndTag="</dataColumn>\n";
			  dataColumnEndDateStartTag="<dataColumn width='75' headerfontcolor='ffffff'  bgColor='eeeeee' headerbgColor='4567aa'  fontColor='000000' headerText='Finish' isBold='0'>\n";
			  dataColumnEndDateEndTag="</dataColumn>\n";
			  dataColumnEndTag="</dataColumn>\n";
			  dataColumnDurationStartTag="<dataColumn align='center' headerfontcolor='ffffff'  headerbgColor='4567aa'  bgColor='eeeeee' headerText='Dur.' width='50' isBold='0'>\n";
			  dataColumnDurationEndTag="</dataColumn>\n";
			  dataColumnResourceStartTag="<dataColumn align='center' headerfontcolor='ffffff'  headerbgColor='4567aa'  bgColor='eeeeee' headerText='Resource' width='50' isBold='0'>\n";
			  dataColumnResourceEndTag="</dataColumn>\n";
			  dataTableEndTag="</dataTable>\n";
			  tasksStartTag="<tasks  width='10'>\n";
			  tasksEndTag="</tasks>\n";
			  connectorsStartTag="<connectors>\n";
			  connectorsEndTag="</connectors>\n";
			  chartEndTag="</chart>\n";
			  
			  //TO FECTH TASKING MAPPING I.E. TASK ID AND PREV TASK ID
			  String queryPrevTask=null;
			  queryPrevTask=" SELECT * FROM NPD.VW_PROJECTPREVTASK " ;
				//generating condr=tion for where 
				String whereConditionPrevTask="";
				String conditionPrevTask;
				
				if(objProjectPlan.getProjectPlanId()!=null && !objProjectPlan.getProjectPlanId().equalsIgnoreCase(""))
				{
					conditionPrevTask=Utility.generateRelativeCondition("IN", objProjectPlan.getProjectPlanId(),"", "PROJECTID");
					whereConditionPrevTask=Utility.addToCondition(whereConditionPrevTask,conditionPrevTask);
				}
				
				condition=Utility.generateStringLikeCondition(objProjectPlan.getStagename(), "STAGENAME");
				whereCondition=Utility.addToCondition(whereCondition,condition);
				
				if(!(whereConditionPrevTask.trim().equals("")))
				{
					queryPrevTask=queryPrevTask+" WHERE "+whereConditionPrevTask;
				}
				
								  
			    connection.setAutoCommit(false);
			  
			    proc=connection.prepareCall(queryPrevTask);
			   //proc.setLong(1,objProjectPlan.getTaskid());
			    rsViewTaskPrevious = proc.executeQuery();
			  
				while(rsViewTaskPrevious.next())
				{
					objDto =  new ProjectPlanReportDto();
					objDto.setCurTaskID(rsViewTaskPrevious.getString("TASKID"));
					objDto.setPrvTaskID(rsViewTaskPrevious.getString("PREVTASKID"));
					TaskListMapping.add(objDto);
					if(connector==null)
					{
						connector="<connector fromTaskId='"+objDto.getCurTaskID()+"-1' toTaskId='"+objDto.getPrvTaskID()+"-1' color='4567aa' thickness='2' fromTaskConnectStart='1'/>\n";
					}
					else
					{
						connector=connector + "<connector fromTaskId='"+objDto.getCurTaskID()+"-1' toTaskId='"+objDto.getPrvTaskID()+"-1' color='4567aa' thickness='2' fromTaskConnectStart='1'/>\n";
					}
				}

			  if (finalXMLStr==null)
			  {
				  finalXMLStr=chartStartTag+categoriesHeader+categoriesMonthTag+categoryMonthTag+	
				  processesStartTag+processTaskNameTag+processesEndTag+dataTableStartTag+
				  dataColumnStartDateStartTag+textStartDateTag+dataColumnStartDateEndTag+dataColumnEndDateStartTag+textEndDateTag+
				  dataColumnEndDateEndTag+dataColumnDurationStartTag+textDurationTag+dataColumnDurationEndTag+
				  dataColumnResourceStartTag+textResourceTag+dataColumnResourceEndTag+dataTableEndTag+tasksStartTag+
				  taskTag+tasksEndTag+connectorsStartTag+connector+connectorsEndTag+chartEndTag;
			  }
			  String fileName;
			  fileName="Gantt-"+objProjectPlan.getProjectPlanId()+"-"+objProjectPlan.getStagename()+"-"+System.currentTimeMillis()+".xml";
			  
			  File f=new File(strPath+"/"+fileName);
			  System.out.println(f);
			  try 
			  {
					f.createNewFile();
					FileOutputStream outputStream=new FileOutputStream(f);
					byte[] data= finalXMLStr.getBytes();
					outputStream.write(data);
					outputStream.close();
					objProjectPlan.setFile_Name_Chart(fileName);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  //return listProjectPlan;
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
				NpdConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new NpdException("Exception : " + e.getMessage(),e);
			}
		}

		return listProjectPlan;
	}

}
