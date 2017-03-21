package com.ibm.ioes.npd.hibernate.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import com.ibm.ioes.npd.beans.ProjectPlanInstanceBean;
import com.ibm.ioes.npd.beans.UploadProductPlanBean;
import com.ibm.ioes.npd.exception.NpdException;
import com.ibm.ioes.npd.hibernate.beans.IoesProductDto;
import com.ibm.ioes.npd.hibernate.beans.PlanExcelUploadDto;
import com.ibm.ioes.npd.hibernate.beans.TmEmployee;
import com.ibm.ioes.npd.hibernate.beans.TmReferencedocs;
import com.ibm.ioes.npd.hibernate.beans.TmRoles;
import com.ibm.ioes.npd.hibernate.beans.TmstProjecthierarchytasksflow;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjecthierarchy;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjectworkflow;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.NpdConnection;
import com.ibm.ioes.npd.utilities.PagingSorting;
import com.ibm.ioes.npd.utilities.Utility;
import com.ibm.ioes.utilities.DbConnection;

public class AttachEditProjectPlanDao {

	public static final String EXCEL_DOWNLOAD_EDIT="EXCEL_DOWNLOAD_EDIT";
	
	
	public ArrayList<TtrnProjecthierarchy> getStages(Connection conn, ProjectPlanInstanceBean searchDto)throws NpdException {
		
		ArrayList<TtrnProjecthierarchy> stageList = new ArrayList<TtrnProjecthierarchy>();
		try{
			
			
			String sql="SELECT DISTINCT STAGENAME,CURRENTSTAGEID,STAGEDESC " +
					" FROM NPD.TTRN_PROJECTHIERARCHY "; 
			
			String whereCondition="";
			String condition="";
			
			
			condition="PROJECTWORKFLOWID="+searchDto.getProjectWorkflowId();
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			condition=Utility.generateStringLikeCondition(searchDto.getSearchStageName(), "STAGENAME");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			condition=Utility.generateStringLikeCondition(searchDto.getSearchStageDesc(), "STAGEDESC");
			whereCondition=Utility.addToCondition(whereCondition,condition);

			
			if(!(whereCondition.trim().equals("")))
			{
				sql=sql+" WHERE "+whereCondition;
			}
						
			//Generating Order By clause
			String orderByColumn="";
			String ASC_DESC="DESC";
			PagingSorting pagingSorting =searchDto.getStagesPS();
			String sortBy=pagingSorting.getSortByColumn();
			
			if("stageName".equals(sortBy))
			{
				orderByColumn="STAGENAME";
			}
			else if("stageDesc".equals(sortBy))
			{
				orderByColumn="STAGEDESC";
			}
			
						
			ASC_DESC="DESC";
			String sortByOrder=pagingSorting.getSortByOrder();
			if("increment".equalsIgnoreCase(sortByOrder))
			{
				ASC_DESC="ASC";
			}
			int a=1;
			if(orderByColumn!=null && !(orderByColumn.trim().equals("")))
			{
				String FullOrderBy=" ORDER BY "+orderByColumn+" "+ASC_DESC+" ";
				sql=sql+FullOrderBy;
				
//				For paging
				if(pagingSorting.isPagingToBeDone())
				{	
					if(pagingSorting!=null)
					{
						pagingSorting.storeRecordCount(conn,sql);
						sql=pagingSorting.query(sql, FullOrderBy,PagingSorting.jdbc);
					}
				}
			}
			
			
			
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next())
			{
				TtrnProjecthierarchy dto=new TtrnProjecthierarchy();
				dto.setStagename(rs.getString("STAGENAME"));
				dto.setCurrentstageid(rs.getLong("CURRENTSTAGEID"));
				dto.setStagedesc(rs.getString("STAGEDESC"));
				
				stageList.add(dto);
			}
			 rs.close();
			 stmt.close();
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in getStages method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in getStages method of "
					+ this.getClass().getSimpleName()+"  inserting mapping ") ;
		}
		return stageList;
	}

	public ArrayList<TtrnProjecthierarchy> getTasks(Connection conn, ProjectPlanInstanceBean searchDto) throws NpdException{
		
		ArrayList<TtrnProjecthierarchy> taskList = new ArrayList<TtrnProjecthierarchy>();
		try{
			
			String sql="SELECT PH.*," +
					" TM_ROLES.ROLENAME,TM_EMPLOYEE.EMPNAME,DOCS.REFDOCNAME " +
					" FROM NPD.V_PROJECTHIERARCHY PH LEFT OUTER JOIN NPD.TM_EMPLOYEE TM_EMPLOYEE " +
					" ON PH.ASSIGNEDTOUSERID = TM_EMPLOYEE.NPDEMPID LEFT OUTER JOIN NPD.TM_ROLES TM_ROLES " +
					" ON PH.TASKSTAKEHOLDER = TM_ROLES.ROLEID LEFT OUTER JOIN NPD.TM_REFERENCEDOCS DOCS " +
					" ON PH.TASK_REFERENCEDOCID =DOCS.REFDOCID ";
	
			String whereCondition="";
			String condition="";
			
			
			condition="PROJECTWORKFLOWID="+searchDto.getProjectWorkflowId();
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			condition="PH.ISACTIVE=1";
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			condition=null;
			String taskOption=searchDto.getTaskOption();
			if("ALL".equals(taskOption))
			{
				condition=null;
			}else if("NOPREVLIST".equals(taskOption))
			{
				condition="NOT EXISTS ( " +
						"SELECT * FROM NPD.TMST_PROJECTHIERARCHYTASKSFLOW as FLOW " +
						"WHERE FLOW.TASKID=PH.CURRENTTASKID AND FLOW.PROJECTWORKFLOWID=PH.PROJECTWORKFLOWID " +
						")  AND PH.ISFIRSTTASK<>1";
			}else if("FIRST".equals(taskOption))
			{
				condition="PH.ISFIRSTTASK=1";
			}else if("LAST".equals(taskOption))
			{
				condition="PH.ISLASTTASK=1";	
			}else if("NOUSERATTACHED".equals(taskOption))
			{
				condition="PH.ASSIGNEDTOUSERID is Null";	
			}
			
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			condition=Utility.generateStringLikeCondition(searchDto.getSearchTaskName(), "PH.TASKNAME");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			condition=Utility.generateStringLikeCondition(searchDto.getSearchTask_roleHolder(), "TM_ROLES.ROLENAME");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			condition=Utility.generateStringLikeCondition(searchDto.getSearchTask_assignedTo(), "TM_EMPLOYEE.EMPNAME");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			condition=Utility.generateRelativeCondition("EQUAL", searchDto.getSearchTaskOfStageId(),null, "PH.CURRENTSTAGEID");
			whereCondition=Utility.addToCondition(whereCondition,condition);
		
			
			if(!(whereCondition.trim().equals("")))
			{
				sql=sql+" WHERE "+whereCondition;
			}
						
			//Generating Order By clause
			String orderByColumn="";
			String ASC_DESC="DESC";
			PagingSorting pagingSorting =searchDto.getTasksPS();
			String sortBy=pagingSorting.getSortByColumn();
			
			if("taskName".equals(sortBy))
			{
				orderByColumn="PH.TASKNAME";
			}
			else if("roleHolder".equals(sortBy))
			{
				orderByColumn="TM_ROLES.ROLENAME";
			}else if("assignedTo".equals(sortBy))
			{
				orderByColumn="TM_EMPLOYEE.EMPNAME";
			}
			
						
			ASC_DESC="DESC";
			String sortByOrder=pagingSorting.getSortByOrder();
			if("increment".equalsIgnoreCase(sortByOrder))
			{
				ASC_DESC="ASC";
			}
			
			if("special2".equals(sortBy))
			{
				orderByColumn="ISFIRSTTASK DESC,ISLASTTASK ASC,TASKSTARTDATE ASC,CURRENTTASKID ASC";
				ASC_DESC="";
			}
			
			if(AttachEditProjectPlanDao.EXCEL_DOWNLOAD_EDIT.equals(sortBy))
			{
				orderByColumn="ISFIRSTTASK DESC,ISLASTTASK ASC,TASKSTARTDATE ASC,CURRENTTASKID ASC";
				ASC_DESC="";
			}
			
			if("special".equals(sortBy))//called when plan is to be send in mail order by taskstartdate
			{
				sql=sql+" ORDER BY "+"PH.ISFIRSTTASK DESC,PH.ISLASTTASK ASC,PH.TASKSTARTDATE ASC";
			}else 
			if(orderByColumn!=null && !(orderByColumn.trim().equals("")))
			{
				String FullOrderBy=" ORDER BY "+orderByColumn+" "+ASC_DESC+" ";
				sql=sql+FullOrderBy;
				
		//		For paging
				if(pagingSorting.isPagingToBeDone())
				{	
					if(pagingSorting!=null)
					{
						pagingSorting.storeRecordCount(conn,sql);
						sql=pagingSorting.query(sql, removeDOTBefore(FullOrderBy),PagingSorting.jdbc);
					}
				}
			}
			
			
			
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next())
			{
				TtrnProjecthierarchy dto=new TtrnProjecthierarchy();
				
				dto.setCurrenttaskid(rs.getLong("CURRENTTASKID"));
				dto.setTaskname(rs.getString("TASKNAME"));
				dto.setStagename(rs.getString("STAGENAME"));
				dto.setTaskstakeholderName(rs.getString("ROLENAME"));
				dto.setAssignedtouserName(rs.getString("EMPNAME"));
				dto.setAssignedtouserid(rs.getLong("ASSIGNEDTOUSERID"));
				dto.setTaskduration(rs.getLong("TASKDURATION"));
				dto.setTaskstatus(rs.getLong("TASKSTATUS"));
				dto.setTaskReferencedocname(rs.getString("REFDOCNAME"));
				
				dto.setTaskstakeholder(rs.getLong("TASKSTAKEHOLDER"));
				dto.setTaskdesc(rs.getString("TASKDESC"));
				dto.setTaskTasktype(rs.getInt("TASK_TASKTYPE"));
				dto.setIsfirsttask(rs.getInt("ISFIRSTTASK"));
				dto.setIslasttask(rs.getInt("ISLASTTASK"));
				dto.setTaskIsattachment(rs.getInt("TASK_ISATTACHMENT"));
				
				dto.setTaskTaskinstructionremarks(rs.getString("TASK_TASKINSTRUCTIONREMARKS"));
				
				dto.setTaskstartdate(rs.getDate("TASKSTARTDATE"));
				dto.setTaskenddate(rs.getDate("TASKENDDATE"));
				dto.setActualtaskstartdate(rs.getDate("ACTUALTASKSTARTDATE"));
				dto.setActualtaskenddate(rs.getDate("ACTUALTASKENDDATE"));
				dto.setTaskstatus(rs.getLong("TASKSTATUS"));
				dto.setCurrentTaskStatus(rs.getString("CURRENTTASKSTATUS"));
				
				dto.setCurrentstageid(rs.getLong("CURRENTSTAGEID"));
				
				dto.setTaskReferencedocid(rs.getInt("TASK_REFERENCEDOCID"));
				
				dto.setRejectionAllowed(rs.getInt("TASK_REJECTIONALLOWED"));
				
				taskList.add(dto);
			}
			  
			//ce=hibernateSession.createCriteria(TmstProjecthierarchytasksflow.class);
			//ce.add(Restrictions.eq("projectworkflowid", Long.parseLong(projectWorkflowId)));
			
			//ArrayList<TmstProjecthierarchytasksflow> prevList=(ArrayList<TmstProjecthierarchytasksflow>)ce.list();
			ArrayList<TmstProjecthierarchytasksflow> prevList=new ArrayList<TmstProjecthierarchytasksflow>();
			String prevSql="SELECT PREVTASKDETAILS, PROJECTWORKFLOWID, TASKID, PREVTASKID, CREATEDATE, CREADTEDBY " +
					"FROM NPD.TMST_PROJECTHIERARCHYTASKSFLOW WHERE PROJECTWORKFLOWID="+searchDto.getProjectWorkflowId();
			ResultSet prevRS=conn.createStatement().executeQuery(prevSql);
			TmstProjecthierarchytasksflow dto=null;
			while(prevRS.next())
			{
				dto=new TmstProjecthierarchytasksflow();
				dto.setPrevtaskdetails(prevRS.getLong("PREVTASKDETAILS"));
				dto.setPrevtaskid(prevRS.getLong("PREVTASKID"));
				dto.setProjectworkflowid(prevRS.getLong("PROJECTWORKFLOWID"));
				dto.setTaskid(prevRS.getLong("TASKID"));
				
				prevList.add(dto);
			}
			
			
			HashMap<Long,ArrayList<TmstProjecthierarchytasksflow>> hashMap=new HashMap<Long, ArrayList<TmstProjecthierarchytasksflow>>();  
			for (TmstProjecthierarchytasksflow projecthierarchytasksflow : prevList) {
				long taskId=projecthierarchytasksflow.getTaskid();
				ArrayList<TmstProjecthierarchytasksflow> temp=hashMap.get(taskId);
				if(temp==null)
				{
					temp=new ArrayList<TmstProjecthierarchytasksflow>();
				}
				temp.add(projecthierarchytasksflow);
				hashMap.put(taskId, temp);
			}
			for (TtrnProjecthierarchy task : taskList) {
				task.setPrevTaskList(hashMap.get(task.getCurrenttaskid()));
			}
			
			HashMap<Long,ArrayList<TmstProjecthierarchytasksflow>> hashMap2=new HashMap<Long, ArrayList<TmstProjecthierarchytasksflow>>();  
			for (TmstProjecthierarchytasksflow projecthierarchytasksflow : prevList) {
				long taskId=projecthierarchytasksflow.getPrevtaskid();
				ArrayList<TmstProjecthierarchytasksflow> temp=hashMap2.get(taskId);
				if(temp==null)
				{
					temp=new ArrayList<TmstProjecthierarchytasksflow>();
				}
				temp.add(projecthierarchytasksflow);
				hashMap2.put(taskId, temp);
			}
			for (TtrnProjecthierarchy task : taskList) {
				task.setNextTaskList(hashMap2.get(task.getCurrenttaskid()));
			}
			
			DbConnection.closeResultset(rs);
			DbConnection.closeStatement(stmt);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in getTasks method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in getTasks method of "
					+ this.getClass().getSimpleName()) ;
		}
		return taskList;
	}
	
	public ArrayList<TtrnProjecthierarchy> getTaskString(Connection conn, ProjectPlanInstanceBean searchDto) throws NpdException{
		
		ArrayList<TtrnProjecthierarchy> taskList = new ArrayList<TtrnProjecthierarchy>();
		Statement stmt=null;
		ResultSet rs=null;
		try{
			
			String sql="SELECT PH.*," +
					" TM_ROLES.ROLENAME,TM_EMPLOYEE.EMPNAME,DOCS.REFDOCNAME " +
					" FROM NPD.V_PROJECTHIERARCHY PH LEFT OUTER JOIN NPD.TM_EMPLOYEE TM_EMPLOYEE " +
					" ON PH.ASSIGNEDTOUSERID = TM_EMPLOYEE.NPDEMPID LEFT OUTER JOIN NPD.TM_ROLES TM_ROLES " +
					" ON PH.TASKSTAKEHOLDER = TM_ROLES.ROLEID LEFT OUTER JOIN NPD.TM_REFERENCEDOCS DOCS " +
					" ON PH.TASK_REFERENCEDOCID =DOCS.REFDOCID ";
	
			String whereCondition="";
			String condition="";
			
			
			condition="PROJECTWORKFLOWID="+searchDto.getProjectWorkflowId();
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			condition="PH.ISACTIVE=1";
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			condition="PH.CURRENTTASKID="+searchDto.getSearchTaskId();
			whereCondition=Utility.addToCondition(whereCondition,condition);
						
			
			if(!(whereCondition.trim().equals("")))
			{
				sql=sql+" WHERE "+whereCondition;
			}
			
			
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			while(rs.next())
			{
				TtrnProjecthierarchy dto=new TtrnProjecthierarchy();
				
				dto.setCurrenttaskid(rs.getLong("CURRENTTASKID"));
				dto.setTaskname(rs.getString("TASKNAME"));
				dto.setStagename(rs.getString("STAGENAME"));
				dto.setTaskstakeholderName(rs.getString("ROLENAME"));
				dto.setAssignedtouserName(rs.getString("EMPNAME"));
				dto.setAssignedtouserid(rs.getLong("ASSIGNEDTOUSERID"));
				dto.setTaskduration(rs.getLong("TASKDURATION"));
				dto.setTaskstatus(rs.getLong("TASKSTATUS"));
				dto.setTaskReferencedocname(rs.getString("REFDOCNAME"));
				
				dto.setTaskstakeholder(rs.getLong("TASKSTAKEHOLDER"));
				dto.setTaskdesc(rs.getString("TASKDESC"));
				dto.setTaskTasktype(rs.getInt("TASK_TASKTYPE"));
				dto.setIsfirsttask(rs.getInt("ISFIRSTTASK"));
				dto.setIslasttask(rs.getInt("ISLASTTASK"));
				dto.setTaskIsattachment(rs.getInt("TASK_ISATTACHMENT"));
				
				dto.setTaskTaskinstructionremarks(rs.getString("TASK_TASKINSTRUCTIONREMARKS"));
				
				dto.setTaskstartdate(rs.getDate("TASKSTARTDATE"));
				dto.setTaskenddate(rs.getDate("TASKENDDATE"));
				dto.setActualtaskstartdate(rs.getDate("ACTUALTASKSTARTDATE"));
				dto.setActualtaskenddate(rs.getDate("ACTUALTASKENDDATE"));
				dto.setTaskstatus(rs.getLong("TASKSTATUS"));
				dto.setCurrentTaskStatus(rs.getString("CURRENTTASKSTATUS"));
				
				taskList.add(dto);
			}
			  
			
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in getTasks method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in getTasks method of "
					+ this.getClass().getSimpleName()) ;
		}
		finally
		{
			try
			{
			DbConnection.closeResultset(rs);
			DbConnection.closeStatement(stmt);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		
		}
		return taskList;
	}
	
	public String removeDOTBefore(String str)
	{
		
		int dotPos=str.indexOf(".");
		if(dotPos>=0)
		{
			String after=str.substring(dotPos+1);
			String mid=str.substring(0, dotPos);
			String before=mid.substring(0,mid.lastIndexOf(" ")+1);
			return before+after;
		}
		else
		{
			return str;
		}
	}

	public TtrnProjectworkflow getActiveProjectWorkflowId(Connection conn, String projectId) throws NpdException{
		
		TtrnProjectworkflow ttrnProjectworkflow=null; 
		try{
			String sql="SELECT PW.* FROM NPD.TTRN_PROJECTWORKFLOW as PW ,NPD.TTRN_PROJECTWORKFLOWDET as DET " +
					" WHERE PW.PROJECTWORKFLOWID=DET.PROJECTWORKFLOWID AND DET.PROJECTID="+Long.parseLong(projectId)+" AND DET.ISACTIVE=1";
			ResultSet rs=conn.createStatement().executeQuery(sql);
			if(rs.next())
			{
				ttrnProjectworkflow=new TtrnProjectworkflow();
				ttrnProjectworkflow.setProjectworkflowid(rs.getLong("PROJECTWORKFLOWID"));
				ttrnProjectworkflow.setAttachHistoryStatus(rs.getString("ATTACHHISTORYSTATUS"));
			}
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			String msg=ex.getMessage()
			+ " Exception occured in getActiveProjectWorkflowId method of "
			+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex);
			AppConstants.NPDLOGGER.error(msg);
			throw new NpdException(msg) ;
		}
		return ttrnProjectworkflow;
	}

	public int setTargetDates(Connection conn, String projectId) throws NpdException{
		int status=-1; 
		CallableStatement cstmt=null;
		try{
			//call proc to set dates for projectWorkflowId
			
			cstmt = conn.prepareCall(
										"{call NPD.P_DTS_FINALIZE_PROJECTHIERARCHY(?,?,?)}");
			cstmt.setLong(1, Long.parseLong(projectId));
			cstmt.setString(2, "");
			cstmt.setLong(3, 0);
			
			
			boolean b=cstmt.execute();
			
			String msg=cstmt.getString(2);
			Long test=cstmt.getLong(3);
			AppConstants.NPDLOGGER.debug("Finalize proceduew dates set iterartion no of times :"+test);
			if(!msg.equals("Committed"))
			{
				status=-1;	
			}
			else if(msg.equals("Committed"))
			{
				status=1;
			}	
			
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			String msg=ex.getMessage()
			+ " Exception occured in setTargetDates method of "
			+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex);
			AppConstants.NPDLOGGER.error(msg);
			throw new NpdException(msg) ;
		}
		finally
		{
			DbConnection.closeCallableStatement(cstmt);
		}
		
		return status;
	}
	
	public int setFewTargetDates(Connection conn, String projectId) throws NpdException{
		int status=-1; 
		CallableStatement cstmt=null;
		try{
			//call proc to set dates for projectWorkflowId
			
			cstmt = conn.prepareCall(
										"{call NPD.P_DTS_FINALIZE_EXISTING_PROJECTHIERARCHY(?,?,?)}");
			cstmt.setLong(1, Long.parseLong(projectId));
			cstmt.setString(2, null);
			cstmt.setLong(3, 0);
			
			
			boolean b=cstmt.execute();
			
			String msg=cstmt.getString(2);
			Long test=cstmt.getLong(3);
			AppConstants.NPDLOGGER.debug("Finalize proceduew dates set iterartion no of times :"+test);
			if(!msg.equals("Committed"))
			{
				status=-1;	
			}
			else if(msg.equals("Committed"))
			{
				status=1;
			}	
			
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			String msg=ex.getMessage()
			+ " Exception occured in setFewTargetDates method of "
			+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex);
			AppConstants.NPDLOGGER.error(msg);
			throw new NpdException(msg) ;
		}
		finally
		{
			DbConnection.closeCallableStatement(cstmt);
		}
		
		return status;
	}
	
	public TtrnProjecthierarchy getTaskInstance(Session hibernateSession, String projectWorkflowId, String currentTaskId) 
																throws NpdException
	{
		TtrnProjecthierarchy ttrnProjecthierarchy=null; 
		Connection conn=hibernateSession.connection();
		try{
			Criteria ce=hibernateSession.createCriteria(TtrnProjecthierarchy.class);
			ce.add(Restrictions.eq("projectworkflowid", Long.parseLong(projectWorkflowId)));
			ce.add(Restrictions.eq("currenttaskid", Long.parseLong(currentTaskId)));
			
			List list=ce.list();
			if(list==null || list.size()==0)
			{
				ttrnProjecthierarchy=null;
			}
			else
			{
				ttrnProjecthierarchy=(TtrnProjecthierarchy)list.get(0);
				
				String sql=" SELECT PH.*,ROL.ROLENAME,EMP.EMPNAME "+
						   " FROM NPD.V_PROJECTHIERARCHY PH LEFT OUTER JOIN NPD.TM_ROLES ROL "+
						   " ON PH.TASKSTAKEHOLDER=ROL.ROLEID LEFT OUTER JOIN NPD.TM_EMPLOYEE EMP "+
						   " ON PH.ASSIGNEDTOUSERID=EMP.NPDEMPID " +
						   " where PH.PROJECTWORKFLOWID="+projectWorkflowId+" " +
								" AND PH.CURRENTTASKID="+currentTaskId+" AND PH.ISACTIVE=1";
				ResultSet rs=conn.createStatement().executeQuery(sql);
				rs.next();
				ttrnProjecthierarchy.setCurrentTaskStatus(rs.getString("CURRENTTASKSTATUS"));
				ttrnProjecthierarchy.setAssignedtouserName(rs.getString("EMPNAME"));
				ttrnProjecthierarchy.setTaskstakeholderName(rs.getString("ROLENAME"));
				
				ce=hibernateSession.createCriteria(TmstProjecthierarchytasksflow.class);
				ce.add(Restrictions.eq("projectworkflowid", Long.parseLong(projectWorkflowId)));
				ce.add(Restrictions.eq("taskid", Long.parseLong(currentTaskId)));
				ArrayList<TmstProjecthierarchytasksflow> prevList=(ArrayList<TmstProjecthierarchytasksflow>)ce.list();
				ttrnProjecthierarchy.setPrevTaskList(prevList);
				
				//get task state
				/*sql="SELECT CURRENTTASKSTATUS FROM NPD.V_PROJECTHIERARCHY WHERE PROJECTWORKFLOWID="+
						projectWorkflowId+" AND CURRENTTASKID="+currentTaskId+" AND ISACTIVE=1";
				
				rs=conn.createStatement().executeQuery(sql);
				rs.next();
				ttrnProjecthierarchy.setCurrentTaskStatus(rs.getString("CURRENTTASKSTATUS"));*/
			}
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			String msg=ex.getMessage()
			+ " Exception occured in getTaskInstance method of "
			+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex);
			AppConstants.NPDLOGGER.error(msg);
			throw new NpdException(msg) ;
		}
		
		return ttrnProjecthierarchy;
	}

	public ArrayList<TtrnProjecthierarchy> getStages(Session hibernateSession, String projectWorkflowId) throws NpdException{
		ArrayList<TtrnProjecthierarchy> stageList=null; 
		try{

			String sql="SELECT DISTINCT PH.CURRENTSTAGEID as currentstageid , PH.STAGENAME as stagename FROM NPD.TTRN_PROJECTHIERARCHY as PH WHERE PROJECTWORKFLOWID=:projectWorkflowId";
			SQLQuery query=hibernateSession.createSQLQuery(sql);
			
			query.setLong("projectWorkflowId", Long.parseLong(projectWorkflowId));
			query.addScalar("currentstageid",Hibernate.LONG)
			.addScalar("stagename",Hibernate.STRING)
			.setResultTransformer( Transformers.aliasToBean(TtrnProjecthierarchy.class));
			
			stageList=(ArrayList<TtrnProjecthierarchy>)query.list();
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			String msg=ex.getMessage()
			+ " Exception occured in getStages method of "
			+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex);
			AppConstants.NPDLOGGER.error(msg);
			throw new NpdException(msg) ;
		}
		
		return stageList;
	}

	public ArrayList<TmRoles> getRoles(Session hibernateSession) throws NpdException{
		ArrayList<TmRoles> rolesList = new ArrayList<TmRoles>();

		CommonBaseDao commonBaseDao = (CommonBaseDao) BaseDaoFactory
				.getInstance(DaoConstants.BASE_DAO_CLASS);
		try {
			/*rolesList = commonBaseDao
					.getAllEntriesInATable(hibernateSession,
							HibernateBeansConstants.HBM_ROLE);*/
			Criteria ce=hibernateSession.createCriteria(TmRoles.class);
			//ce.add(Restrictions.eq("isactive", 1));
			ce.addOrder(Order.asc("rolename"));
			ArrayList<TmRoles> list=(ArrayList<TmRoles>)ce.list();
			if(list!=null)
			{
				rolesList=list;
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
			String msg=ex.getMessage()
			+ " Exception occured in getRoles method of "
			+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex);
			AppConstants.NPDLOGGER.error(msg);
			throw new NpdException(msg) ;
		}
		return rolesList;
	}
	
	public ArrayList getTemplateDocList(Session hibernateSession) throws Exception {

		ArrayList templateDocList = new ArrayList();

		CommonBaseDao commonBaseDao = (CommonBaseDao) BaseDaoFactory
				.getInstance(DaoConstants.BASE_DAO_CLASS);
		
		
		try {
			/*templateDocList = commonBaseDao
					.getAllEntriesInATable(hibernateSession,
							HibernateBeansConstants.HBM_REFERENCE_DOC);*/
			Criteria ce=hibernateSession.createCriteria(TmReferencedocs.class);
			ce.add(Restrictions.eq("isactive", new Integer(1)));
			templateDocList=(ArrayList)ce.list();
			
		} catch (Exception ex) {
			ex.printStackTrace();
			String msg=ex.getMessage()
			+ " Exception occured in getTemplateDocList method of "
			+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex);
			AppConstants.NPDLOGGER.error(msg);
			throw new NpdException(msg) ;
		}
		return templateDocList;

	}

	public ArrayList<TmEmployee> getUsersOfRole(Session hibernateSession, String roleId) throws NpdException 
	{
		ArrayList<TmEmployee> employeeList=null; 
		try{
			
			String sql="SELECT	DISTINCT TM_EMPLOYEE.NPDEMPID,TM_EMPLOYEE.EMPNAME FROM	NPD.TM_ROLEEMPMAPPING TM_ROLEEMPMAPPING " +
					"INNER JOIN NPD.TM_ROLES TM_ROLES ON TM_ROLEEMPMAPPING.ROLEID = TM_ROLES.ROLEID" +
					"	INNER JOIN NPD.TM_EMPLOYEE TM_EMPLOYEE " +
					"ON TM_ROLEEMPMAPPING.NPDEMPID = TM_EMPLOYEE.NPDEMPID WHERE TM_ROLES.ROLEID=:roleId";
			SQLQuery query=hibernateSession.createSQLQuery(sql);
			
			query.setLong("roleId", Long.valueOf(roleId));
			
			query.addScalar("npdempid",Hibernate.LONG)
					.addScalar("empname",Hibernate.STRING)
					.setResultTransformer( Transformers.aliasToBean(TmEmployee.class));
			
			
			employeeList=(ArrayList<TmEmployee>)query.list();
			
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			String msg=ex.getMessage()
			+ " Exception occured in getUsersOfRole method of "
			+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex);
			AppConstants.NPDLOGGER.error(msg);
			throw new NpdException(msg) ;
		}
		
		return employeeList;
	}

	public ArrayList<TtrnProjecthierarchy> getAllTasks(Session hibernateSession, 
											String projectWorkflowId) 
												throws NpdException
	{
		ArrayList<TtrnProjecthierarchy> list=null;
		try
		{
			//String sql=" ";
			//SQLQuery query=hibernateSession.createSQLQuery(sql);
			
			Criteria ce=hibernateSession.createCriteria(TtrnProjecthierarchy.class);
			ce.add(Restrictions.eq("projectworkflowid", Long.parseLong(projectWorkflowId)));
			ce.add(Restrictions.eq("isactive", 1));
			
			list=(ArrayList<TtrnProjecthierarchy>)ce.list();
			
			ce=hibernateSession.createCriteria(TmstProjecthierarchytasksflow.class);
			ce.add(Restrictions.eq("projectworkflowid", Long.parseLong(projectWorkflowId)));
			ArrayList<TmstProjecthierarchytasksflow> prevList=(ArrayList<TmstProjecthierarchytasksflow>)ce.list();
			
			HashMap<Long,ArrayList<TmstProjecthierarchytasksflow>> hashMap_prevTasks=new HashMap<Long, ArrayList<TmstProjecthierarchytasksflow>>();  
			HashMap<Long,ArrayList<TmstProjecthierarchytasksflow>> hashMap_nextTasks=new HashMap<Long, ArrayList<TmstProjecthierarchytasksflow>>();
			for (TmstProjecthierarchytasksflow projecthierarchytasksflow : prevList) {
				
				//for prev
				long taskId=projecthierarchytasksflow.getTaskid();
				ArrayList<TmstProjecthierarchytasksflow> temp=hashMap_prevTasks.get(taskId);
				if(temp==null)
				{
					temp=new ArrayList<TmstProjecthierarchytasksflow>();
				}
				temp.add(projecthierarchytasksflow);
				hashMap_prevTasks.put(taskId, temp);
				
				
				//for next
				long taskId_fornext=projecthierarchytasksflow.getPrevtaskid();
				ArrayList<TmstProjecthierarchytasksflow> temp_fornext=hashMap_nextTasks.get(taskId_fornext);
				if(temp_fornext==null)
				{
					temp_fornext=new ArrayList<TmstProjecthierarchytasksflow>();
				}
				temp_fornext.add(projecthierarchytasksflow);
				hashMap_nextTasks.put(taskId_fornext, temp_fornext);
			}
			for (TtrnProjecthierarchy task : list) {
				task.setPrevTaskList(hashMap_prevTasks.get(task.getCurrenttaskid()));
				task.setNextTaskList(hashMap_nextTasks.get(task.getCurrenttaskid()));
			}
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			String msg=ex.getMessage()
			+ " Exception occured in getAllTasks method of "
			+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex);
			AppConstants.NPDLOGGER.error(msg);
			throw new NpdException(msg) ;
		}
		return list;
	}

	public TtrnProjecthierarchy getFirstLastTask(Session hibernateSession, String projectWorkflowId,String firstLast) throws NpdException
	{
		TtrnProjecthierarchy ttrnProjecthierarchy=null; 
		try{
			
			
			
			Criteria ce=hibernateSession.createCriteria(TtrnProjecthierarchy.class);
			ce.add(Restrictions.eq("projectworkflowid", Long.parseLong(projectWorkflowId)));
			ce.add(Restrictions.eq("isactive", 1));
			
			if("first".equals(firstLast))
			{
				ce.add(Restrictions.eq("isfirsttask", 1));
			}
			else if("last".equals(firstLast))
			{
				ce.add(Restrictions.eq("islasttask", 1));
			}
			
			List list=ce.list();
			if(list==null || list.size()==0)
			{
				ttrnProjecthierarchy= null;
			}
			else
			{
				ttrnProjecthierarchy=(TtrnProjecthierarchy)list.get(0);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			String msg=ex.getMessage()
			+ " Exception occured in getTaskInstance method of "
			+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex);
			AppConstants.NPDLOGGER.error(msg);
			throw new NpdException(msg) ;
		}
		
		return ttrnProjecthierarchy;
	}

	public int moveToHistoryAndCreateCopy(Connection conn, String projectId)throws NpdException{
		int status=-1; 
		
		try{
			//call proc to set dates for projectWorkflowId
		
			CallableStatement cstmt = conn.prepareCall(
										"{call NPD.P_DTS_EDITFINALIZED_PROJECTHIERARCHY(?,?,?)}");
			cstmt.setLong(1, Long.parseLong(projectId));
			cstmt.setLong(2, 0);
			cstmt.setString(3, "");
			
			
			
			boolean b=cstmt.execute();
			
			String msg=cstmt.getString(3);
			if(!msg.equals("Committed"))
			{
				status=-1;	
			}
			else if(msg.equals("Committed"))
			{
				status=1;
			}	
			
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			String msg=ex.getMessage()
			+ " Exception occured in moveToHistoryAndCreateCopy method of "
			+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex);
			AppConstants.NPDLOGGER.error(msg);
			throw new NpdException(msg) ;
		}
		
		return status;
	}

	public ArrayList<TmEmployee> getEmployeesOfProject(Connection conn,String projectWorkflowId) throws NpdException{
		ArrayList<TmEmployee> employeeList=new ArrayList<TmEmployee>(); 
		ResultSet rs=null;
		try{
			
			/*String sql="SELECT	TM_EMPLOYEE.EMPID,TM_EMPLOYEE.EMPNAME FROM	NPD.TM_ROLEEMPMAPPING TM_ROLEEMPMAPPING " +
					"INNER JOIN NPD.TM_ROLES TM_ROLES ON TM_ROLEEMPMAPPING.ROLEID = TM_ROLES.ROLEID" +
					"	INNER JOIN NPD.TM_EMPLOYEE TM_EMPLOYEE " +
					"ON TM_ROLEEMPMAPPING.NPDEMPID = TM_EMPLOYEE.NPDEMPID WHERE TM_ROLES.ROLEID=:roleId";*/
			
			String sql="SELECT	DISTINCT TM_EMPLOYEE.EMAIL,TM_EMPLOYEE.NPDEMPID,TTRN_PROJECTHIERARCHY.PROJECTWORKFLOWID " +
					"FROM NPD.TTRN_PROJECTHIERARCHY TTRN_PROJECTHIERARCHY INNER JOIN NPD.TM_EMPLOYEE TM_EMPLOYEE " +
					"ON TTRN_PROJECTHIERARCHY.ASSIGNEDTOUSERID = TM_EMPLOYEE.NPDEMPID " +
					"WHERE TTRN_PROJECTHIERARCHY.PROJECTWORKFLOWID = "+projectWorkflowId;
			rs=conn.createStatement().executeQuery(sql);
			while(rs.next())
			{
				TmEmployee emp=new TmEmployee();
				emp.setNpdempid(rs.getLong("NPDEMPID"));
				emp.setEmail(rs.getString("EMAIL"));
				
				employeeList.add(emp);
			}
			
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			String msg=ex.getMessage()
			+ " Exception occured in getEmployeesOfProject method of "
			+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex);
			AppConstants.NPDLOGGER.error(msg);
			throw new NpdException(msg) ;
		}
		finally
		{
			try
			{
				
			DbConnection.closeResultset(rs);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		
		return employeeList;
	}

	public ArrayList<TmEmployee> getEmployeesRoleList(Connection conn,Long[] roles) throws NpdException 
	{
		ArrayList<TmEmployee> employeeList=null;

		ResultSet rs=null;
		try{
			
			String csv="-1";
			for (int i = 0; i < roles.length; i++) {
				csv=csv+","+roles[i];
			}
			
			String sql="SELECT	TM_EMPLOYEE.NPDEMPID,TM_EMPLOYEE.EMPNAME,TM_ROLES.ROLEID FROM	NPD.TM_ROLEEMPMAPPING TM_ROLEEMPMAPPING " +
					"INNER JOIN NPD.TM_ROLES TM_ROLES ON TM_ROLEEMPMAPPING.ROLEID = TM_ROLES.ROLEID" +
					"	INNER JOIN NPD.TM_EMPLOYEE TM_EMPLOYEE " +
					"ON TM_ROLEEMPMAPPING.NPDEMPID = TM_EMPLOYEE.NPDEMPID and TM_EMPLOYEE.ISACTIVE=1 WHERE TM_ROLES.ROLEID IN ("+csv+") ORDER BY TM_EMPLOYEE.EMPNAME";
			
			 rs=conn.createStatement().executeQuery(sql);
			employeeList=new ArrayList<TmEmployee>();
			while(rs.next())
			{
				TmEmployee dto=new TmEmployee();
				
				dto.setEmpname(rs.getString("EMPNAME"));
				dto.setNpdempid(rs.getLong("NPDEMPID"));
				dto.setOneRoleId(rs.getLong("ROLEID"));
				
				employeeList.add(dto);
			}
			
			
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			String msg=ex.getMessage()
			+ " Exception occured in getEmployeesRoleList method of "
			+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex);
			AppConstants.NPDLOGGER.error(msg);
			throw new NpdException(msg) ;
		}
		finally
		{
			try
			{
			DbConnection.closeResultset(rs);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		
		return employeeList;
	}
	
	public TtrnProjectworkflow getDraftProjectWorkflowId(Connection conn, String projectId)  throws NpdException{
		
		TtrnProjectworkflow ttrnProjectworkflow=null; 
		try{
			String sql="SELECT PW.* FROM NPD.TTRN_PROJECTWORKFLOW as PW ,NPD.TTRN_PROJECTWORKFLOWDET as DET " +
					" WHERE PW.PROJECTWORKFLOWID=DET.PROJECTWORKFLOWID AND DET.PROJECTID="+Long.parseLong(projectId)+" AND DET.ISACTIVE=2";
			ResultSet rs=conn.createStatement().executeQuery(sql);
			if(rs.next())
			{
				ttrnProjectworkflow=new TtrnProjectworkflow();
				ttrnProjectworkflow.setProjectworkflowid(rs.getLong("PROJECTWORKFLOWID"));
				ttrnProjectworkflow.setAttachHistoryStatus(rs.getString("ATTACHHISTORYSTATUS"));
			}
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			String msg=ex.getMessage()
			+ " Exception occured in getActiveProjectWorkflowId method of "
			+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex);
			AppConstants.NPDLOGGER.error(msg);
			throw new NpdException(msg) ;
		}
		return ttrnProjectworkflow;
	}
		
	public int createDraftCopy(Connection conn, String projectId) throws NpdException{
		int status=-1; 
		
		try{
			//call proc to set dates for projectWorkflowId
		
			CallableStatement cstmt = conn.prepareCall(
										"{call NPD.P_DTS_EDITFINALIZED_PROJECTHIERARCHY(?,?,?)}");
			cstmt.setLong(1, Long.parseLong(projectId));
			cstmt.setLong(2, 0);
			cstmt.setString(3, "");
			
			
			
			boolean b=cstmt.execute();
			
			String msg=cstmt.getString(3);
			if(!msg.equals("Committed"))
			{
				status=-1;	
			}
			else if(msg.equals("Committed"))
			{
				status=1;
			}	
			
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			String msg=ex.getMessage()
			+ " Exception occured in createDraftCopy method of "
			+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex);
			AppConstants.NPDLOGGER.error(msg);
			throw new NpdException(msg) ;
		}
		
		return status;
	}

	public HashMap revertBeforeFinalize(Connection conn, ProjectPlanInstanceBean formBean) throws NpdException{
		HashMap map=new HashMap(); 
		
		try{
			//call proc to set dates for projectWorkflowId
		
			CallableStatement cstmt = conn.prepareCall(
										"{call NPD.P_DTS_REVERT(?,?,?,?,?,?)}");
			cstmt.setLong(1, Long.parseLong(formBean.getProjectId()));
			cstmt.setString(2, "");
			cstmt.setString(3, "");
			cstmt.setString(4, "");
			cstmt.setString(5, "");
			cstmt.setString(6, "");
			
			
			
			boolean b=cstmt.execute();
			
			String msg=cstmt.getString(6);
			
			if(msg.equals("Committed"))
			{
				String revertedTaskIds=cstmt.getString(2);
				if(revertedTaskIds==null || "".equals(revertedTaskIds))
				{
					map.put("ISREVERTED", new Integer(0));
				}
				else
				{
					map.put("ISREVERTED", new Integer(1));
					//map.put("TASKS_REVERTED_CSV", revertedTaskIds);
					String csv=revertedTaskIds.substring(1);
					
					String sql="SELECT STAGENAME,TASKNAME,CURRENTTASKID FROM NPD.TTRN_PROJECTHIERARCHY " +
							"where PROJECTWORKFLOWID="+formBean.getProjectWorkflowId()+" AND CURRENTTASKID IN (" +
									csv+
									")";
					ResultSet rs=conn.createStatement().executeQuery(sql);
					ArrayList<TtrnProjecthierarchy> list=new ArrayList<TtrnProjecthierarchy>();
					HashMap<Long,TtrnProjecthierarchy> revTaskMap=new HashMap<Long, TtrnProjecthierarchy>();
					while(rs.next())
					{
						TtrnProjecthierarchy dto=new TtrnProjecthierarchy();
						dto.setTaskname(rs.getString("TASKNAME"));
						dto.setStagename(rs.getString("STAGENAME"));
						dto.setCurrenttaskid(rs.getLong("CURRENTTASKID"));
						list.add(dto);
						revTaskMap.put(dto.getCurrenttaskid(),dto);
					}
					map.put("REVERTED_TASKS_LIST", list);
					//retrieve message for each trask and store as a string array
					
					//for closed tasks
					String closedTaskIds=cstmt.getString(3);
					ArrayList<TtrnProjecthierarchy> closedList=new ArrayList<TtrnProjecthierarchy>();
					if(closedTaskIds!=null && !"".equals(closedTaskIds))
					{
						csv=closedTaskIds.substring(1);
						StringTokenizer st=new StringTokenizer(closedTaskIds,",");
						
						while(st.hasMoreTokens())
						{
							closedList.add(revTaskMap.get(Long.parseLong((String)st.nextElement())));
						}
					}
					map.put("REVERTED_CLOSED_TASKS_LIST", closedList);
					
					//for open but action taken tasks
					ArrayList<TtrnProjecthierarchy> openActionTakenList=new ArrayList<TtrnProjecthierarchy>();
					String openActionTakenTaskIds=cstmt.getString(4);
					if(openActionTakenTaskIds!=null && !"".equals(openActionTakenTaskIds))
					{
						csv=openActionTakenTaskIds.substring(1);
						StringTokenizer st=new StringTokenizer(openActionTakenTaskIds,",");
						
						while(st.hasMoreTokens())
						{
							openActionTakenList.add(revTaskMap.get(Long.parseLong((String)st.nextElement())));
						}
					}
					map.put("REVERTED_OPENACTIONTAKEN_TASKS_LIST", openActionTakenList);
					//for open no action taken tasks
					String openNoActionTaskIds=cstmt.getString(5);
					ArrayList<TtrnProjecthierarchy> openNoActionList=new ArrayList<TtrnProjecthierarchy>();
					
					if(openNoActionTaskIds!=null && !"".equals(openNoActionTaskIds))
					{
						csv=openNoActionTaskIds.substring(1);
						StringTokenizer st=new StringTokenizer(openNoActionTaskIds,",");
						
						while(st.hasMoreTokens())
						{
							openNoActionList.add(revTaskMap.get(Long.parseLong((String)st.nextElement())));
						}
					}
					map.put("REVERTED_OPENNOACTION_TASKS_LIST", openNoActionList);
				}
			}	
			
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			String msg=ex.getMessage()
			+ " Exception occured in revertBeforeFinalize method of "
			+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex);
			AppConstants.NPDLOGGER.error(msg);
			throw new NpdException(msg) ;
		}
		
		return map;
	}

	public int discardDraft(Connection conn, String projectId) throws NpdException{
		int status=-1; 
		
		try{
			//call proc to set dates for projectWorkflowId
		
			CallableStatement cstmt = conn.prepareCall(
										"{call NPD.P_DTS_DISCARD_DRAFT(?,?)}");
			cstmt.setLong(1, Long.parseLong(projectId));
			cstmt.setString(2, "");
			
			
			
			boolean b=cstmt.execute();
			
			String msg=cstmt.getString(2);
			if(!msg.equals("Committed"))
			{
				status=-1;	
			}
			else if(msg.equals("Committed"))
			{
				status=1;
			}	
			
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			String msg=ex.getMessage()
			+ " Exception occured in discardDraft method of "
			+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex);
			AppConstants.NPDLOGGER.error(msg);
			throw new NpdException(msg) ;
		}
		
		return status;
	}

	//rohit verma for multiple tas deletion
	public ArrayList<TtrnProjecthierarchy> getTaskforRemoval(Connection conn, ProjectPlanInstanceBean searchDto) throws NpdException
	{
		ArrayList<TtrnProjecthierarchy> taskRemovalList = new ArrayList<TtrnProjecthierarchy>();
		try{
			
			String sql="SELECT PH.*," +
					" TM_ROLES.ROLENAME,TM_EMPLOYEE.EMPNAME,DOCS.REFDOCNAME " +
					" FROM NPD.V_PROJECTHIERARCHY PH LEFT OUTER JOIN NPD.TM_EMPLOYEE TM_EMPLOYEE " +
					" ON PH.ASSIGNEDTOUSERID = TM_EMPLOYEE.NPDEMPID LEFT OUTER JOIN NPD.TM_ROLES TM_ROLES " +
					" ON PH.TASKSTAKEHOLDER = TM_ROLES.ROLEID LEFT OUTER JOIN NPD.TM_REFERENCEDOCS DOCS " +
					" ON PH.TASK_REFERENCEDOCID =DOCS.REFDOCID ";
	
			String whereCondition="";
			String condition="";
			
			
			condition="PROJECTWORKFLOWID="+searchDto.getProjectWorkflowId();
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			condition="PH.ISACTIVE=1";
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			condition=Utility.generateStringLikeCondition(searchDto.getSearchTaskName(), "PH.TASKNAME");
			whereCondition=Utility.addToCondition(whereCondition,condition);
				
			condition=Utility.generateRelativeCondition("EQUAL", searchDto.getSearchTaskOfStageId(),null, "PH.CURRENTSTAGEID");
			whereCondition=Utility.addToCondition(whereCondition,condition);
		
			
			if(!(whereCondition.trim().equals("")))
			{
				sql=sql+" WHERE "+whereCondition;
			}
						
			//Generating Order By clause
			String orderByColumn="";
			String ASC_DESC="DESC";
			PagingSorting pagingSorting =searchDto.getTasksPS();
			String sortBy=pagingSorting.getSortByColumn();
			
			if("taskName".equals(sortBy))
			{
				orderByColumn="PH.TASKNAME";
			}
				
						
			ASC_DESC="DESC";
			String sortByOrder=pagingSorting.getSortByOrder();
			if("increment".equalsIgnoreCase(sortByOrder))
			{
				ASC_DESC="ASC";
			}
			
			if("special2".equals(sortBy))
			{
				orderByColumn="ISFIRSTTASK DESC,ISLASTTASK ASC,TASKSTARTDATE ASC,CURRENTTASKID ASC";
				ASC_DESC="";
			}
			
			if(orderByColumn!=null && !(orderByColumn.trim().equals("")))
			{
				String FullOrderBy=" ORDER BY "+orderByColumn+" "+ASC_DESC+" ";
				sql=sql+FullOrderBy;
				
		//		For paging
				if(pagingSorting.isPagingToBeDone())
				{	
					if(pagingSorting!=null)
					{
						pagingSorting.storeRecordCount(conn,sql);
						pagingSorting.sync(PagingSorting.jdbc);
						
						if(pagingSorting.getStartRecordId()-pagingSorting.getRecordCount()>0 && pagingSorting.getPageNumber()>1)
						{
							pagingSorting.setPageNumber(pagingSorting.getPageNumber()-1);
							pagingSorting.storeRecordCount(conn,sql);
						}
						sql=pagingSorting.query(sql, removeDOTBefore(FullOrderBy),PagingSorting.jdbc);
					}
				}
			}
			
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next())
			{
				TtrnProjecthierarchy dto=new TtrnProjecthierarchy();
				
				dto.setCurrenttaskid(rs.getLong("CURRENTTASKID"));
				dto.setTaskname(rs.getString("TASKNAME"));
				dto.setStagename(rs.getString("STAGENAME"));
				dto.setTaskstakeholderName(rs.getString("ROLENAME"));
				dto.setAssignedtouserName(rs.getString("EMPNAME"));
				dto.setAssignedtouserid(rs.getLong("ASSIGNEDTOUSERID"));
				dto.setTaskduration(rs.getLong("TASKDURATION"));
				dto.setTaskstatus(rs.getLong("TASKSTATUS"));
				dto.setTaskReferencedocname(rs.getString("REFDOCNAME"));
				
				dto.setTaskstakeholder(rs.getLong("TASKSTAKEHOLDER"));
				dto.setTaskdesc(rs.getString("TASKDESC"));
				dto.setTaskTasktype(rs.getInt("TASK_TASKTYPE"));
				dto.setIsfirsttask(rs.getInt("ISFIRSTTASK"));
				dto.setIslasttask(rs.getInt("ISLASTTASK"));
				dto.setTaskIsattachment(rs.getInt("TASK_ISATTACHMENT"));
				
				dto.setTaskTaskinstructionremarks(rs.getString("TASK_TASKINSTRUCTIONREMARKS"));
				
				dto.setTaskstartdate(rs.getDate("TASKSTARTDATE"));
				dto.setTaskenddate(rs.getDate("TASKENDDATE"));
				dto.setActualtaskstartdate(rs.getDate("ACTUALTASKSTARTDATE"));
				dto.setActualtaskenddate(rs.getDate("ACTUALTASKENDDATE"));
				dto.setTaskstatus(rs.getLong("TASKSTATUS"));
				dto.setCurrentTaskStatus(rs.getString("CURRENTTASKSTATUS"));
				
				taskRemovalList.add(dto);
			}
			  	
			ArrayList<TmstProjecthierarchytasksflow> prevList=new ArrayList<TmstProjecthierarchytasksflow>();
			String prevSql=" SELECT FL.PREVTASKDETAILS, FL.PROJECTWORKFLOWID, FL.TASKID, FL.PREVTASKID, FL.CREATEDATE, FL.CREADTEDBY ,PREVTASK.TASKNAME " +
					" FROM NPD.TMST_PROJECTHIERARCHYTASKSFLOW FL INNER JOIN NPD.TTRN_PROJECTHIERARCHY PREVTASK " +
					" ON FL.PREVTASKID=PREVTASK.CURRENTTASKID AND PREVTASK.PROJECTWORKFLOWID="+searchDto.getProjectWorkflowId() +
					" AND PREVTASK.ISACTIVE=1 " +
					" WHERE FL.PROJECTWORKFLOWID="+searchDto.getProjectWorkflowId();
			ResultSet prevRS=conn.createStatement().executeQuery(prevSql);
			TmstProjecthierarchytasksflow dto=null;
			while(prevRS.next())
			{
				dto=new TmstProjecthierarchytasksflow();
				dto.setPrevtaskdetails(prevRS.getLong("PREVTASKDETAILS"));
				dto.setPrevtaskid(prevRS.getLong("PREVTASKID"));
				dto.setProjectworkflowid(prevRS.getLong("PROJECTWORKFLOWID"));
				dto.setTaskid(prevRS.getLong("TASKID"));
				dto.setPrevTaskName(prevRS.getString("TASKNAME"));
				
				prevList.add(dto);
			}
			
			
			HashMap<Long,ArrayList<TmstProjecthierarchytasksflow>> hashMap=new HashMap<Long, ArrayList<TmstProjecthierarchytasksflow>>();  
			for (TmstProjecthierarchytasksflow projecthierarchytasksflow : prevList) {
				long taskId=projecthierarchytasksflow.getTaskid();
				ArrayList<TmstProjecthierarchytasksflow> temp=hashMap.get(taskId);
				if(temp==null)
				{
					temp=new ArrayList<TmstProjecthierarchytasksflow>();
				}
				temp.add(projecthierarchytasksflow);
				hashMap.put(taskId, temp);
			}
			for (TtrnProjecthierarchy task : taskRemovalList) {
				ArrayList<TmstProjecthierarchytasksflow> list=hashMap.get(task.getCurrenttaskid());
				if(list==null)
				{
					list=new ArrayList<TmstProjecthierarchytasksflow>();
				}
				task.setPrevTaskList(list);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in getTasks method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in getTasks method of "
					+ this.getClass().getSimpleName()) ;
		}
		return taskRemovalList;
	}

	public long getFileId(Connection connection, UploadProductPlanBean dto)throws NpdException{
		long fileId=-1; 
		
		try{
			CallableStatement cstmt = connection.prepareCall(
										"{call NPD.P_GET_EXCEL_PLAN_UPLOAD_FILEID(?,?,?,?)}");
			cstmt.setLong(1, dto.getProductId());
			cstmt.setString(2, dto.getUploadFile().getFileName());
			cstmt.setLong(3, dto.getCreatedBy());
			cstmt.setNull(4, java.sql.Types.BIGINT);
			cstmt.registerOutParameter(4, java.sql.Types.BIGINT);
			boolean b=cstmt.execute();
			fileId=cstmt.getLong(4);
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			String msg=ex.getMessage()
			+ " Exception occured in getFileId method of "
			+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex);
			AppConstants.NPDLOGGER.error(msg);
			throw new NpdException(msg) ;
		}
		
		return fileId;
	}
	public int saveUploadedFileToTemporaryTable(
			ArrayList<Object[][]> excelDataList,Connection connection,Long fileId) throws NpdException {

		AppConstants.NPDLOGGER
				.info("UploadDaoImpl's saveUploadedFileToTemporaryTable() started");
		int retCode=0;
		int  colCount = 0, sheetNo = 1;
		String origional_fileName = null;
		
		int count = 0;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			

			// -- FILESTATUSID = 1 for uploaded, 2 for
			// validation error, 3 for validated, 4 for processing, 5 for
			// processed
			
			String SQL_INSERT_INTO_TEMPORARY_TABLE = " INSERT INTO NPD.T_EXCEL_PLAN_DATA( FILEID, STAGEID,STAGENAME, TASKID, TASKNAME, TASKDESC, MANDATORY, REJECT_ALLOWED, STAKEHOLDER_ROLEID, STAKEHOLDER_ROLENAME,ASSIGNEDTO_ID,ASSIGNEDTO_NAME,ISFIRST, ISLAST, PREV_TASK_IDS,PREV_TASKS,  PLANNED_DURATION, DOCUMENT_UPLOADED_ID, DOCUMENT_UPLOADED, REMARKS)"+ 
								" VALUES( "+fileId+", ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ? , ?, ?, ?,?,?)";

			rs = null;
			pstmt = null;
			colCount = 19;
			pstmt = connection
					.prepareStatement(SQL_INSERT_INTO_TEMPORARY_TABLE);
			Object[][] excelData = (Object[][]) excelDataList.get(sheetNo - 1);

			for (int r = 0; r < excelData.length; r++) {
				int check = 0;
				for (int c = 0; c < colCount; c++) {
					if (!(excelData[r][c].toString().trim().equals(""))) {

						pstmt.setString(c + 1, (excelData[r][c]).toString()
								.trim());
						check = 1;
					} else {
						pstmt.setString(c + 1, "");
					}
				}
				if (check == 1) {
					
					pstmt.execute();
				}
			}
			rs = null;
			pstmt = null;

			String SQL_UPDATE_FILE_STATUS_UPLOADED = "UPDATE NPD.T_EXCEL_PLAN_STATUS SET STATUS="+UploadProductPlanBean.DB_UPLOAD_SUCCESS+" WHERE FILEID=?";
			pstmt = connection
					.prepareStatement(SQL_UPDATE_FILE_STATUS_UPLOADED);
			pstmt.setLong(1, fileId);
			pstmt.execute();

			pstmt.close();

			retCode=1;
			
			connection.commit();
		} catch (Exception ex) {
			 ex.printStackTrace();
			try {
				connection.rollback();
				retCode=-1;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			throw new NpdException("SQL Exception : " + ex.getMessage(), ex);
		}

		AppConstants.NPDLOGGER
				.info("UploadDaoImpl's saveUploadedFileToTemporaryTable() completed");
		return retCode;
	}

	public int validateExcelProductPlan(Connection conn, ProjectPlanInstanceBean formBean) throws NpdException{
		int errorsFound=0; 
		
		try{
			//call proc to set dates for projectWorkflowId
		
			CallableStatement cstmt = conn.prepareCall(
										"{call NPD.VALIDATE_UPLOADED_SHEET(?,?)}");
			cstmt.setLong(1, Long.parseLong(formBean.getProjectId()));
			cstmt.setLong(2, 0);
			
			boolean b=cstmt.execute();
			
			errorsFound=new Integer(String.valueOf(cstmt.getLong(2)));
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			String msg=ex.getMessage()
			+ " Exception occured in validateExcelProductPlan method of "
			+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex);
			AppConstants.NPDLOGGER.error(msg);
			throw new NpdException(msg) ;
		}
		
		return errorsFound;
	}

	public void setFileUploadStatus(Connection conn, ProjectPlanInstanceBean formBean, int db_status)
	 throws NpdException{
		try{
			//call proc to set dates for projectWorkflowId
			String query="UPDATE NPD.T_EXCEL_PLAN_STATUS SET STATUS="+db_status+" WHERE PRODUCTID="+formBean.getProjectId()+" and ISCURRENT=1";
			conn.createStatement().execute(query);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			String msg=ex.getMessage()
			+ " Exception occured in setFileUploadStatus method of "
			+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex);
			AppConstants.NPDLOGGER.error(msg);
			throw new NpdException(msg) ;
		}
	}

	public UploadProductPlanBean loadUploadExcelProgressDetails(Connection conn, String projectId) 
		throws NpdException{
		UploadProductPlanBean uBean=null;
		try{
			//call proc to set dates for projectWorkflowId
			String query="SELECT PRODUCTID, FILEID, DOCNAME, STATUS FROM NPD.T_EXCEL_PLAN_STATUS WHERE ISCURRENT=1 and PRODUCTID="+projectId;
			ResultSet rs=conn.createStatement().executeQuery(query);
			while(rs.next())
			{
				uBean=new UploadProductPlanBean();
				uBean.setProgress(rs.getShort("STATUS"));
				uBean.setFileId(String.valueOf(rs.getLong("FILEID")));
				uBean.setUploadFileName(rs.getString("DOCNAME"));
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			String msg=ex.getMessage()
			+ " Exception occured in loadUploadExcelProgressDetails method of "
			+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex);
			AppConstants.NPDLOGGER.error(msg);
			throw new NpdException(msg) ;
		}
		return uBean;
	}

	public int replaceExcelProductPlan(Connection conn, ProjectPlanInstanceBean formBean) throws NpdException{
		int status=0; 
		
		try{
			//call proc to set dates for projectWorkflowId
		
			CallableStatement cstmt = conn.prepareCall(
										"{call NPD.REPLACE_UPLOADED_SHEET(?,?,?)}");
			cstmt.setLong(1, Long.parseLong(formBean.getProjectId()));
			cstmt.setLong(2, 0);
			cstmt.setLong(3, 0);
			
			cstmt.registerOutParameter(2, java.sql.Types.BIGINT);
			cstmt.registerOutParameter(3, java.sql.Types.BIGINT);
			cstmt.execute();
			
			int st=new Integer(String.valueOf(cstmt.getLong(2)));
			if(st>0)
			{
				status=1;
				long newProjectWorkflowId=cstmt.getLong(3);
				formBean.setProjectWorkflowId(String.valueOf(newProjectWorkflowId));
			}
			else
			{
				status=-1;
			}
			
		}
		catch(Exception ex)
		{
			status=-1;
			ex.printStackTrace();
			String msg=ex.getMessage()
			+ " Exception occured in validateExcelProductPlan method of "
			+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex);
			AppConstants.NPDLOGGER.error(msg);
			throw new NpdException(msg) ;
		}
		
		return status;
	}

	public ArrayList<PlanExcelUploadDto> getErrorLogTasks(Connection connection, String projectId)  throws NpdException{
		
		ArrayList<PlanExcelUploadDto> taskList = new ArrayList<PlanExcelUploadDto>();
		try{
			
			String sql=" SELECT ID, FILEID, STAGEID, STAGENAME, TASKID, TASKNAME, TASKDESC, MANDATORY, " +
					" REJECT_ALLOWED, STAKEHOLDER_ROLEID, STAKEHOLDER_ROLENAME, ISFIRST, ISLAST, PREV_TASK_IDS, " +
					" PREV_TASKS, PLANNED_DURATION, DOCUMENT_UPLOADED_ID, DOCUMENT_UPLOADED, REMARKS, ERRORLOG ,ASSIGNEDTO_ID,ASSIGNEDTO_NAME,CYCLE_SQEQUENCE_NO " +
					" FROM NPD.T_EXCEL_PLAN_DATA WHERE FILEID=(SELECT FILEID FROM NPD.T_EXCEL_PLAN_STATUS " +
																" WHERE PRODUCTID="+projectId+" and ISCURRENT=1)";
	
			String FullOrderBy=" ORDER BY ID ASC";
			sql=sql+FullOrderBy;
			
			
			Statement stmt=connection.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next())
			{
				PlanExcelUploadDto dto=new PlanExcelUploadDto();
				
				dto.setDocument_uploaded(rs.getString("DOCUMENT_UPLOADED"));
				dto.setDocument_uploaded_id(rs.getString("DOCUMENT_UPLOADED_ID"));
				dto.setErrorlog(rs.getString("ERRORLOG"));
				dto.setFileid(rs.getString("FILEID"));
				dto.setIsfirst(rs.getString("ISFIRST"));
				dto.setIslast(rs.getString("ISLAST"));
				dto.setMandatory(rs.getString("MANDATORY"));
				dto.setPlanned_duration(rs.getString("PLANNED_DURATION"));
				dto.setPrev_task_ids(rs.getString("PREV_TASK_IDS"));
				dto.setPrev_tasks(rs.getString("PREV_TASKS"));
				dto.setReject_allowed(rs.getString("REJECT_ALLOWED"));
				dto.setRemarks(rs.getString("REMARKS"));
				dto.setStageid(rs.getString("STAGEID"));
				dto.setStagename(rs.getString("STAGENAME"));
				dto.setStakeholder_roleid(rs.getString("STAKEHOLDER_ROLEID"));
				dto.setStakeholder_rolename(rs.getString("STAKEHOLDER_ROLENAME"));
				dto.setTaskdesc(rs.getString("TASKDESC"));
				dto.setTaskid(rs.getString("TASKID"));
				dto.setTaskname(rs.getString("TASKNAME"));
				dto.setEmployeeId(rs.getString("ASSIGNEDTO_ID"));
				dto.setEmployeeName(rs.getString("ASSIGNEDTO_NAME"));
				dto.setCycleSequenceNo(rs.getString("CYCLE_SQEQUENCE_NO"));
				
				taskList.add(dto);
			}
			  
			
			
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in getErrorLogTasks method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in getErrorLogTasks method of "
					+ this.getClass().getSimpleName()) ;
		}
		return taskList;
	}

	public void removePrevCurrentEntry(Connection connection, String projectId) throws NpdException{
		
		try{
			CallableStatement cstmt = connection.prepareCall(
										"{call NPD.P_REMOVE_PREV_EXCEL_PLAN(?)}");
			cstmt.setLong(1, Long.parseLong(projectId));
			
			boolean b=cstmt.execute();
			
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			String msg=ex.getMessage()
			+ " Exception occured in removePrevCurrentEntry method of "
			+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex);
			AppConstants.NPDLOGGER.error(msg);
			throw new NpdException(msg) ;
		}
		
		
	}

	public void saveCycleError(Connection conn, ProjectPlanInstanceBean formBean, ArrayList<ArrayList<Long>> cycles)  throws NpdException{
		try{
			
			
			
			
			ArrayList<Long> cycle=cycles.get(0);
			int sequenceNo=0;
			String sql="UPDATE NPD.T_EXCEL_PLAN_DATA SET  CYCLE_SQEQUENCE_NO=? WHERE " +
					"	FILEID=(SELECT FILEID FROM NPD.T_EXCEL_PLAN_STATUS WHERE PRODUCTID=? AND ISCURRENT=1) " +
					" and TASKID=?";
			PreparedStatement pstmt=conn.prepareStatement(sql);
			pstmt.setLong(2, Long.parseLong(formBean.getProjectId()));
			for (Long taskId : cycle) {
				pstmt.setString(1, String.valueOf(++sequenceNo));
				pstmt.setString(3, ""+taskId);
				pstmt.addBatch();
				//pstmt.execute();
			}
			pstmt.executeBatch();
			
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			String msg=ex.getMessage()
			+ " Exception occured in saveCycleError method of "
			+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex);
			AppConstants.NPDLOGGER.error(msg);
			throw new NpdException(msg) ;
		}
	}
	
	
	
//	--------------------------------------IOES Functions---------------------------------------------
	public ArrayList<IoesProductDto> addIOESProduct(String parentproductId) throws NpdException{
		ArrayList<IoesProductDto> lstproduct=new ArrayList<IoesProductDto>(); 
		
		Connection conn=null;
		try{
			
			String sql=" SELECT * FROM IOE.TSERVICETYPEDETAIL where SERVICETYPEID = "+ parentproductId +"";
			
			conn = NpdConnection.getIOESConnectionObject();
			Statement stmt = conn.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next())
			{
				IoesProductDto dto=new IoesProductDto();
				
				dto.setProductId(rs.getString("SERVICEDETAILID"));
				dto.setProductName(rs.getString("SERVICEDETDESCRIPTION"));
				
				lstproduct.add(dto);
			}
			  
			
			
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in getErrorLogTasks method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in getErrorLogTasks method of "
					+ this.getClass().getSimpleName()) ;
		}
		finally
		{
			try 
			{
				NpdConnection.freeConnection(conn);
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return lstproduct;
	}
	
	
	public ArrayList<IoesProductDto> populateProductHeader() throws NpdException{
		ArrayList<IoesProductDto> lstproduct=new ArrayList<IoesProductDto>(); 
		
		Connection conn=null;
		try{
			
			String sql=" SELECT * FROM IOE.TSERVICETYPEDETAIL where SERVICEDETPARENTID = 0";
			
			conn = NpdConnection.getIOESConnectionObject();
			Statement stmt = conn.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next())
			{
				IoesProductDto dto=new IoesProductDto();
				
				dto.setProductId(rs.getString("SERVICETYPEID"));
				dto.setProductName(rs.getString("SERVICEDETDESCRIPTION"));
				
				lstproduct.add(dto);
			}
			  
			
			
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in getErrorLogTasks method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in getErrorLogTasks method of "
					+ this.getClass().getSimpleName()) ;
		}
		finally
		{
			try 
			{
				NpdConnection.freeConnection(conn);
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return lstproduct;
	}
//	--------------------------------------IOES Functions---------------------------------------------
}
