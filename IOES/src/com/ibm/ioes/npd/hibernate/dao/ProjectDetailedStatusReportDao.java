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
import com.ibm.ioes.npd.hibernate.beans.ProjectDetailedStatusReportDto;
import com.ibm.ioes.npd.hibernate.beans.ProjectPlanReportDto;
import com.ibm.ioes.npd.hibernate.beans.ProjectStatusReportDto;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjectassumptions;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjectisssues;
import com.ibm.ioes.npd.model.CommonBaseModel;
import com.ibm.ioes.npd.utilities.NpdConnection;
import com.ibm.ioes.npd.utilities.PagingSorting;
import com.ibm.ioes.npd.utilities.Utility;
import com.ibm.ioes.utilities.DbConnection;

public class ProjectDetailedStatusReportDao {
	private static final Logger logger;
	static 
	{
	logger = Logger.getLogger(ProjectDetailedStatusReportDao.class);
	}

	public ArrayList<ProjectDetailedStatusReportDto> fetchProjectStatusDetail(ProjectDetailedStatusReportDto objProjectStatus)throws NpdException
	{
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rsViewProjectStatusDetail = null;
		ArrayList<ProjectDetailedStatusReportDto> listProjectStatus = new ArrayList<ProjectDetailedStatusReportDto>();
		ProjectDetailedStatusReportDto objDto = null;
		try
		{
			connection=NpdConnection.getConnectionObject();
			
			String query=null;
			query=" SELECT * FROM NPD.VW_DETAILEDPROJECTPLAN" ;
			//generating condr=tion for where 
			String whereCondition="";
			String condition;
						
			int projtID=objProjectStatus.getSearchByProjectID();
			if(projtID!=0)
			{
				condition=Utility.generateIntCondition(objProjectStatus.getSearchByProjectID(), "PROJECTID");
				whereCondition=Utility.addToCondition(whereCondition,condition);
			}
			
			condition=Utility.generateStringLikeCondition(objProjectStatus.getSearchProjectName(), "PROJECT_NAME");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			condition=Utility.generateStringLikeCondition(objProjectStatus.getStageName(), "STAGENAME");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			condition=Utility.generateStringLikeCondition(objProjectStatus.getTaskName(), "TASKNAME");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			condition=Utility.generateStringLikeCondition(objProjectStatus.getProjectStatus(), "PROJECTSTATUS");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			condition=Utility.generateStringLikeCondition(objProjectStatus.getRoleName(), "ROLENAME");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			condition=Utility.generateStringLikeCondition(objProjectStatus.getDocName(), "DOCNAME");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			condition=Utility.generateStringLikeCondition(objProjectStatus.getDocType(), "DOCMIMETYPE");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			condition=Utility.generateIntCondition(objProjectStatus.getDelays(), "DELAYINDAYS");
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
			if("taskid".equals(sortBy))
			{
				orderByColumn="CURRENTTASKID";
			}
			else if("projectName".equals(sortBy))
			{
				orderByColumn="PROJECT_NAME";
			}
			else if("stageName".equals(sortBy))
			{
				orderByColumn="STAGENAME";
			}
			else if("taskName".equals(sortBy))
			{
				orderByColumn="TASKNAME";
			}
			else if("taskStartDate".equals(sortBy))
			{
				orderByColumn="TASKSTARTDATE";
			}
			else if("taskEndDate".equals(sortBy))
			{
				orderByColumn="TASKENDDATE";
			}
			else if("actualStartDate".equals(sortBy))
			{
				orderByColumn="ACTUALTASKSTARTDATE";
			}
			else if("actualEndDate".equals(sortBy))
			{
				orderByColumn="ACTUALTASKENDDATE";
			}
			else if("projectStatus".equals(sortBy))
			{
				orderByColumn="PROJECTSTATUS";
			}
			else if("roleName".equals(sortBy))
			{
				orderByColumn="ROLENAME";
			}
			else if("delays".equals(sortBy))
			{
				orderByColumn="DELAYINDAYS";
			}
			else if("docName".equals(sortBy))
			{
				orderByColumn="DOCNAME";
			}
			else if("docType".equals(sortBy))
			{
				orderByColumn="DOCMIMETYPE";
			}
			else if("projectID".equals(sortBy))
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
				  objDto =  new ProjectDetailedStatusReportDto();
				  objDto.setProjectID(rsViewProjectStatusDetail.getInt("PROJECTID"));
				  objDto.setProjectName(rsViewProjectStatusDetail.getString("PROJECT_NAME"));
				  objDto.setStageName(rsViewProjectStatusDetail.getString("STAGENAME"));
				  objDto.setTaskName(rsViewProjectStatusDetail.getString("TASKNAME"));
				  objDto.setTaskStartDate(rsViewProjectStatusDetail.getString("TASKSTARTDATE"));
				  objDto.setTaskEndDate(rsViewProjectStatusDetail.getString("TASKENDDATE"));
				  objDto.setActualStartDate(rsViewProjectStatusDetail.getString("ACTUALTASKSTARTDATE"));
				  objDto.setActualEndDate(rsViewProjectStatusDetail.getString("ACTUALTASKENDDATE"));
				  objDto.setProjectStatus(rsViewProjectStatusDetail.getString("PROJECTSTATUS"));
				  objDto.setTaskStakeHolder(rsViewProjectStatusDetail.getString("TASKSTAKEHOLDER"));
				  objDto.setRoleName(rsViewProjectStatusDetail.getString("ROLENAME"));
				  objDto.setDelays(rsViewProjectStatusDetail.getInt("DELAYINDAYS"));
				  objDto.setDocName(rsViewProjectStatusDetail.getString("DOCNAME"));
				  objDto.setDocType(rsViewProjectStatusDetail.getString("DOCMIMETYPE"));
				  objDto.setCurrentTaskId(rsViewProjectStatusDetail.getLong("CURRENTTASKID"));
				  //objDto.setUploadedDoc(rsViewProjectStatusDetail.getString("UPLOADED_DOCUMENT"));
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
	
	public ArrayList<ProjectDetailedStatusReportDto> fetchProjectStatusDetailExcel(ProjectDetailedStatusReportDto objProjectStatus)throws NpdException
	{
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rsViewProjectStatusDetail = null;
		ArrayList<ProjectDetailedStatusReportDto> listProjectStatus = new ArrayList<ProjectDetailedStatusReportDto>();
		ProjectDetailedStatusReportDto objDto = null;
		try
		{connection=NpdConnection.getConnectionObject();
			
			String query=null;
			query=" SELECT * FROM NPD.VW_DETAILEDPROJECTPLAN" ;
			//generating condr=tion for where 
			String whereCondition="";
			String condition;
						
			int projtID=objProjectStatus.getSearchByProjectID();
			if(projtID!=0)
			{
				condition=Utility.generateIntCondition(objProjectStatus.getSearchByProjectID(), "PROJECTID");
				whereCondition=Utility.addToCondition(whereCondition,condition);
			}
			
			condition=Utility.generateStringLikeCondition(objProjectStatus.getSearchProjectName(), "PROJECT_NAME");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			condition=Utility.generateStringLikeCondition(objProjectStatus.getStageName(), "STAGENAME");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			condition=Utility.generateStringLikeCondition(objProjectStatus.getTaskName(), "TASKNAME");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			condition=Utility.generateStringLikeCondition(objProjectStatus.getProjectStatus(), "PROJECTSTATUS");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			condition=Utility.generateStringLikeCondition(objProjectStatus.getRoleName(), "ROLENAME");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			condition=Utility.generateStringLikeCondition(objProjectStatus.getDocName(), "DOCNAME");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			condition=Utility.generateStringLikeCondition(objProjectStatus.getDocType(), "DOCMIMETYPE");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			condition=Utility.generateIntCondition(objProjectStatus.getDelays(), "DELAYINDAYS");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			if(!(whereCondition.trim().equals("")))
			{
				query=query+" WHERE "+whereCondition;
			}
			
			//System.err.println("Query:"+query);
			
			
			  
			  connection.setAutoCommit(false);
			  
			  proc=connection.prepareCall(query);
			  //proc.setLong(1,objProjectPlan.getTaskid());
			  rsViewProjectStatusDetail = proc.executeQuery();
			  while(rsViewProjectStatusDetail.next())
			  {
				  objDto =  new ProjectDetailedStatusReportDto();
				  objDto.setProjectID(rsViewProjectStatusDetail.getInt("PROJECTID"));
				  objDto.setProjectName(rsViewProjectStatusDetail.getString("PROJECT_NAME"));
				  objDto.setStageName(rsViewProjectStatusDetail.getString("STAGENAME"));
				  objDto.setTaskName(rsViewProjectStatusDetail.getString("TASKNAME"));
				  objDto.setTaskStartDate(rsViewProjectStatusDetail.getString("TASKSTARTDATE"));
				  objDto.setTaskEndDate(rsViewProjectStatusDetail.getString("TASKENDDATE"));
				  objDto.setActualStartDate(rsViewProjectStatusDetail.getString("ACTUALTASKSTARTDATE"));
				  objDto.setActualEndDate(rsViewProjectStatusDetail.getString("ACTUALTASKENDDATE"));
				  objDto.setProjectStatus(rsViewProjectStatusDetail.getString("PROJECTSTATUS"));
				  objDto.setTaskStakeHolder(rsViewProjectStatusDetail.getString("TASKSTAKEHOLDER"));
				  objDto.setRoleName(rsViewProjectStatusDetail.getString("ROLENAME"));
				  objDto.setDelays(rsViewProjectStatusDetail.getInt("DELAYINDAYS"));
				  objDto.setDocName(rsViewProjectStatusDetail.getString("DOCNAME"));
				  objDto.setDocType(rsViewProjectStatusDetail.getString("DOCMIMETYPE"));
				  //objDto.setUploadedDoc(rsViewProjectStatusDetail.getString("UPLOADED_DOCUMENT"));
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
	
	public byte[] DownloadProjectDocFile(ProjectDetailedStatusReportDto objProjectStatus) throws NpdException
	{
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rs = null;
		byte[] fileByte = null;
		
		try
		{connection=NpdConnection.getConnectionObject();
			
			String query=null;
			query=" SELECT DOCNAME,UPLOADED_DOCUMENT FROM NPD.VW_DETAILEDPROJECTPLAN " +
					" WHERE PROJECTID="+objProjectStatus.getProjectID()+" AND CURRENTTASKID="+objProjectStatus.getCurrentTaskId() ;
			  connection.setAutoCommit(false);
			  
			  proc=connection.prepareCall(query);
			  //proc.setLong(1,objProjectPlan.getTaskid());
			  rs = proc.executeQuery();
			  
			  //fill docname and filebytes
			  if(rs.next())
			  {
				  CommonBaseModel objModel = new CommonBaseModel(); 
				  fileByte= objModel.blobToByteArray(rs.getBlob("UPLOADED_DOCUMENT"));
				  objProjectStatus.setFileBytes(fileByte);
				  objProjectStatus.setDocName(rs.getString("DOCNAME"));
			  }
//			  return fileByte;
		}
		catch (SQLException sqlEx) {
			logger.error(sqlEx.getMessage()
			+ " SQLException occured while closing database objects in DownloadProjectDocFile method of "
			+ this.getClass().getSimpleName());
			throw new NpdException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
		} catch (Exception ex) {
			logger.error(ex.getMessage()
			+ " Exception occured while closing database objects in DownloadProjectDocFile method of "
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
		return fileByte;
	}
	
}
