package com.ibm.ioes.npd.hibernate.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.ibm.ioes.npd.beans.ProjectPlanInstanceBean;
import com.ibm.ioes.npd.beans.RoleEmployeeBean;
import com.ibm.ioes.npd.beans.TaskInstanceBean;
import com.ibm.ioes.npd.exception.NpdException;
import com.ibm.ioes.npd.hibernate.beans.TmstProjecthierarchytasksflow;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjecthierarchy;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjectworkflow;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;

public class WorkFlowDao {

	public long getWorkFlowIdForNpdId(Connection conn, long npdCatgeotyId) throws NpdException {
		long workFlowId=0;
		try
		{
			String query=null;
			query="SELECT  WORKFLOWID, WORKFLOWNAME, WORKFLOWDESC, APPLICATIONNAME, APPLICATIONCODE, CREATEDBY, " +
					"CREATEDDATE, MODIFIEDBY, MODIFIEDDATE, ISACTIVE, NPDCATEGORY FROM NPD.TM_WORKFLOW " +
					"WHERE ISACTIVE=1 AND NPDCATEGORY="+npdCatgeotyId;
			
			ResultSet rs=conn.createStatement().executeQuery(query);
			if(rs.next())
			{
				workFlowId=rs.getLong("WORKFLOWID");
			}
			else
			{
				workFlowId=-1;
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in getWorkFlowIdForNpdId method of "
					+ this.getClass().getSimpleName()+ ex.getMessage(), ex);
		}	
		return workFlowId;
	}
	
	public long importWorkFlow(Connection conn, long workflowId) throws NpdException {
		long projectWorkflowId=0;
		try
		{
			CallableStatement cstmt = conn.prepareCall(
									"{call NPD.P_DTS_PROJECTHIERARCHY(?,?,?,?)}");
			cstmt.setLong(1, workflowId);
			cstmt.setString(2, null);
			cstmt.setString(3, null);
			cstmt.setString(4, null);
			
			
			boolean b=cstmt.execute();
			
			String msg=cstmt.getString(2);
			if(!msg.equals("Committed"))
			{
				projectWorkflowId=-1;	
			}
			else
			{
				projectWorkflowId=cstmt.getLong(4);
			}	
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(ex));
			
			throw new NpdException("Exception occured in importWorkFlow method of "
					+ this.getClass().getSimpleName()+ ex.getMessage(), ex);
		}	
		return projectWorkflowId;
	}

	public int mapProductWithWorkflow(Connection conn, long productId, long projectWorkflowId) throws NpdException{
		try
		{
			String query="INSERT INTO NPD.TTRN_PROJECTWORKFLOWDET    " +
					"(PROJECTTYPE, PROJECTID, CREATEDDATE, CREATEDBY, MODIFIEDDATE,MODIFIEDBY, ISACTIVE, " +
					"PROJECTWORKFLOWID)     " +
					"VALUES        " +
					"(0, "+productId+", CURRENT TIMESTAMP, 1, CURRENT TIMESTAMP, 1, 2, "+projectWorkflowId+")";// isactive 2 for in draft
	
			conn.createStatement().execute(query);
			
			query="UPDATE NPD.TTRN_PROJECTWORKFLOW SET WORKFLOWSATTUS='DRAFT',ISALTERED='NO', ATTACHHISTORYSTATUS='attachNew' WHERE PROJECTWORKFLOWID="+projectWorkflowId;

			conn.createStatement().execute(query);

		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in mapProductWithWorkflow method of "
					+ this.getClass().getSimpleName()+ ex.getMessage(), ex);
		}	
		return 0;
	}

	public int updateTaskInstance(Connection connection, TtrnProjecthierarchy dto) throws NpdException{
		
		int status=0;
		try
		{

			CallableStatement cstmt = connection.prepareCall(
									"{call NPD.P_DTS_UPDATETASKINSTANCE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			
			int parameterIndex=0;
			cstmt.setInt(++parameterIndex, dto.getIsfirsttask());
			cstmt.setInt(++parameterIndex, dto.getIslasttask());
			cstmt.setLong(++parameterIndex, dto.getTaskduration());
			//cstmt.setLong(++parameterIndex, dto.getTaskstakeholder()); 
			cstmt.setLong(++parameterIndex, 0);
			
			cstmt.setString(++parameterIndex, dto.getTaskTaskinstructionremarks());
			cstmt.setInt(++parameterIndex, dto.getTaskIsattachment());
			cstmt.setInt(++parameterIndex, 0);
			
			cstmt.setInt(++parameterIndex, dto.getTaskReferencedocid());
			cstmt.setInt(++parameterIndex, dto.getTaskTasktype());
			cstmt.setInt(++parameterIndex, dto.getRejectionAllowed());
			cstmt.setInt(++parameterIndex, dto.getTaskIsemailtemplate());
			//cstmt.setLong(++parameterIndex, dto.getAssignedtouserid());
			cstmt.setLong(++parameterIndex, 0);
			cstmt.setLong(++parameterIndex, dto.getProjectworkflowid());
			cstmt.setLong(++parameterIndex, dto.getCurrenttaskid());
			cstmt.setString(++parameterIndex, "");
			
			boolean b=cstmt.execute();
			
			String msg=cstmt.getString(15);
			if(!msg.equals("Committed"))
			{
				status=-1;	
			}
			else
			{
				status=1;
			}	
			
			ArrayList<TmstProjecthierarchytasksflow> list=dto.getPrevTaskList();
			long projectWorkFlowId;
			long taskId;
			long prevtaskId;
			cstmt = connection.prepareCall(
			"{call NPD.P_DTS_UPDATETASKFLOW(?,?,?,?)}");
			projectWorkFlowId=dto.getProjectworkflowid();
			taskId=dto.getCurrenttaskid();
			for (TmstProjecthierarchytasksflow projecthierarchytasksflow : list) {
				prevtaskId=projecthierarchytasksflow.getPrevtaskid();
				
				parameterIndex=0;
				
				cstmt.setLong(++parameterIndex, dto.getProjectworkflowid());
				cstmt.setLong(++parameterIndex, dto.getCurrenttaskid());
				cstmt.setLong(++parameterIndex, prevtaskId);
				cstmt.setString(++parameterIndex, "");
				
				b=cstmt.execute();
				
				msg=cstmt.getString(4);
				if(!msg.equals("Committed"))
				{
					status=-1;	
				}
				else
				{
					status=status;
				}	
				
			}
			
			int op_status=setPlanAsAltered(connection,projectWorkFlowId);
			if(op_status==1)
			{
				status=status;
			}
			else if(op_status==-1)
			{
				status=-1;
			}
				

		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in updateTaskInstance method of "
					+ this.getClass().getSimpleName()+ ex.getMessage(), ex);
		}	
		return status;
	}

	private int setPlanAsAltered(Connection connection, long projectWorkFlowId) throws NpdException{
		int status=-1;
		try
		{

			String sql="UPDATE NPD.TTRN_PROJECTWORKFLOW SET ISALTERED='YES' WHERE PROJECTWORKFLOWID="+projectWorkFlowId;
			connection.createStatement().executeUpdate(sql);
			status=1;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in deleteTaskInstance method of "
					+ this.getClass().getSimpleName()+ ex.getMessage(), ex);
		}
		return status;
	}

	public int deleteTaskInstance(Connection connection, TtrnProjecthierarchy dto) throws NpdException{
		
		int status=0;
		try
		{

			CallableStatement cstmt = connection.prepareCall(
									"{call NPD.P_DTS_DELETETASKINSTANCE(?,?,?)}");
			
			int parameterIndex=0;
		
			cstmt.setLong(++parameterIndex, dto.getProjectworkflowid());
			cstmt.setLong(++parameterIndex, dto.getCurrenttaskid());
			cstmt.setString(++parameterIndex, "");
			
			boolean b=cstmt.execute();
			
			String msg=cstmt.getString(3);
			if(!msg.equals("Committed"))
			{
				status=-1;	
			}
			else
			{
				status=1;
			}	
			
			int op_status=setPlanAsAltered(connection,dto.getProjectworkflowid());
			if(op_status==1)
			{
				status=status;
			}
			else if(op_status==-1)
			{
				status=-1;
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in deleteTaskInstance method of "
					+ this.getClass().getSimpleName()+ ex.getMessage(), ex);
		}	
		return status;
	}

	public int saveTaskInstance(Connection connection, TtrnProjecthierarchy dto)throws NpdException{
		
		int status=0;
		try
		{

			CallableStatement cstmt = connection.prepareCall(
									"{call NPD.P_DTS_SAVETASKINSTANCE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			
			int parameterIndex=0;
			
			cstmt.setInt(++parameterIndex, dto.getIsfirsttask());
			cstmt.setInt(++parameterIndex, dto.getIslasttask());
			cstmt.setLong(++parameterIndex, dto.getTaskduration());
			cstmt.setLong(++parameterIndex, dto.getTaskstakeholder()); 
			
			cstmt.setString(++parameterIndex, dto.getTaskTaskinstructionremarks());
			cstmt.setInt(++parameterIndex, dto.getTaskIsattachment());
			cstmt.setInt(++parameterIndex, 0);
			
			cstmt.setInt(++parameterIndex, dto.getTaskReferencedocid());
			cstmt.setInt(++parameterIndex, dto.getTaskTasktype());
			cstmt.setInt(++parameterIndex, dto.getRejectionAllowed());
			cstmt.setInt(++parameterIndex, dto.getTaskIsemailtemplate());
			//cstmt.setLong(++parameterIndex, dto.getAssignedtouserid());
			cstmt.setLong(++parameterIndex, 0);
			cstmt.setLong(++parameterIndex, dto.getProjectworkflowid());
			cstmt.setLong(++parameterIndex, dto.getCurrentstageid());
			cstmt.setString(++parameterIndex, dto.getTaskname());
			cstmt.setString(++parameterIndex, dto.getTaskdesc());
			cstmt.setString(++parameterIndex, "");
			cstmt.setLong(++parameterIndex, 0);
			boolean b=cstmt.execute();
			
			String msg=cstmt.getString(17);
			long newTaskId;
			if(!msg.equals("Committed"))
			{
				status=-1;	
				return -1;
			}
			else
			{
				status=1;
				newTaskId=cstmt.getLong(18);
				dto.setCurrenttaskid(newTaskId);
			}	
			
			ArrayList<TmstProjecthierarchytasksflow> list=dto.getPrevTaskList();
			long projectWorkFlowId;
			long taskId=newTaskId;
			long prevtaskId;
			cstmt = connection.prepareCall(
			"{call NPD.P_DTS_UPDATETASKFLOW(?,?,?,?)}");
			projectWorkFlowId=dto.getProjectworkflowid();
			
			for (TmstProjecthierarchytasksflow projecthierarchytasksflow : list) {
				prevtaskId=projecthierarchytasksflow.getPrevtaskid();
				
				parameterIndex=0;
				
				cstmt.setLong(++parameterIndex, dto.getProjectworkflowid());
				cstmt.setLong(++parameterIndex, taskId);
				cstmt.setLong(++parameterIndex, prevtaskId);
				cstmt.setString(++parameterIndex, "");
				
				b=cstmt.execute();
				
				msg=cstmt.getString(4);
				if(!msg.equals("Committed"))
				{
					status=-1;	
				}
				else
				{
					status=status;
				}	
				
			}
			
			
			int op_status=setPlanAsAltered(connection,projectWorkFlowId);
			if(op_status==1)
			{
				status=status;
			}
			else if(op_status==-1)
			{
				status=-1;
			}

		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in updateTaskInstance method of "
					+ this.getClass().getSimpleName()+ ex.getMessage(), ex);
		}	
		return status;
	}

	public ArrayList<TtrnProjectworkflow> getAllWorkflows(Connection conn,String projectId)  throws NpdException {
		ArrayList<TtrnProjectworkflow> list=new ArrayList<TtrnProjectworkflow>();
		try
		{
			String query="SELECT WF.PROJECTWORKFLOWID,WF.PROJECTWORKFLOWSTATUS,WF.ATTACHHISTORYSTATUS," +
					"WF.ISALTERED,WF.WORKFLOWSATTUS,DET.ISACTIVE,DET.PROJECTID FROM NPD.TTRN_PROJECTWORKFLOWDET DET " +
					"INNER JOIN NPD.TTRN_PROJECTWORKFLOW WF ON DET.PROJECTWORKFLOWID = WF.PROJECTWORKFLOWID " +
					"WHERE DET.PROJECTID="+projectId;
			
			ResultSet rs=conn.createStatement().executeQuery(query);
			TtrnProjectworkflow projectworkflow=null;
			while(rs.next())
			{
				projectworkflow=new TtrnProjectworkflow();
				projectworkflow.setProjectworkflowid(rs.getLong("PROJECTWORKFLOWID"));
				projectworkflow.setIsactive(rs.getInt("ISACTIVE"));
				projectworkflow.setAttachHistoryStatus(rs.getString("ATTACHHISTORYSTATUS"));
				projectworkflow.setWorkflowStatus(rs.getString("WORKFLOWSATTUS"));
				list.add(projectworkflow);
			}
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in getWorkFlowIdForNpdId method of "
					+ this.getClass().getSimpleName()+ ex.getMessage(), ex);
		}	
		return list;
	}

	public ArrayList<TtrnProjecthierarchy> getPlanVersionAllTasks(Connection connection, String projectId) throws NpdException {
		ArrayList<TtrnProjecthierarchy> list=new ArrayList<TtrnProjecthierarchy>();
		try
		{
			
			CallableStatement cstmt=connection.prepareCall(
										"{call NPD.NPD_REPORT_PLANVERSION_MODULE1(?)}");
			cstmt.setLong(1, Long.parseLong(projectId));
			ResultSet rs=cstmt.executeQuery();
			TtrnProjecthierarchy dto=null;
			while(rs.next())
			{
				dto=new TtrnProjecthierarchy();
				
				dto.setCurrenttaskid(rs.getLong("CURRENTTASKID"));
				dto.setTaskname(rs.getString("TASKNAME"));
				dto.setStagename(rs.getString("STAGENAME"));
				dto.setTaskstakeholderName(rs.getString("ROLENAME"));
				dto.setAssignedtouserName(rs.getString("EMPNAME"));
				dto.setTaskduration(rs.getLong("TASKDURATION"));
				dto.setTaskstatus(rs.getLong("TASKSTATUS"));
				dto.setTaskReferencedocname(rs.getString("REFDOCNAME"));
				dto.setProjectworkflowid(rs.getLong("PROJECTWORKFLOWID"));
				
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
				dto.setRejectionAllowed(rs.getInt("TASK_REJECTIONALLOWED"));
				
				list.add(dto);
			}
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in getPlanVersionAllTasks method of "
					+ this.getClass().getSimpleName()+ ex.getMessage(), ex);
		}	
		return list;
	}

	public ArrayList<TmstProjecthierarchytasksflow> getPlanVersionAllPrevDetails(Connection connection, String projectId) throws NpdException {
		ArrayList<TmstProjecthierarchytasksflow> list=new ArrayList<TmstProjecthierarchytasksflow>();
		try
		{
			
			CallableStatement cstmt=connection.prepareCall(
										"{call NPD.NPD_REPORT_PLANVERSION_MODULE2(?)}");
			cstmt.setLong(1, Long.parseLong(projectId));
			ResultSet rs=cstmt.executeQuery();
			TmstProjecthierarchytasksflow dto=null;
			while(rs.next())
			{
				dto=new TmstProjecthierarchytasksflow();
				
				dto.setPrevtaskid(rs.getLong("PREVTASKID"));
				dto.setProjectworkflowid(rs.getLong("PROJECTWORKFLOWID"));
				dto.setTaskid(rs.getLong("TASKID"));
				
				
				list.add(dto);
			}
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in getPlanVersionAllTasks method of "
					+ this.getClass().getSimpleName()+ ex.getMessage(), ex);
		}	
		return list;
	}

	public int updateTasks(Connection connection, ProjectPlanInstanceBean formBean, ArrayList<TaskInstanceBean> list) throws NpdException{
		
		int status=0;
		try
		{
			
			String sql="UPDATE NPD.TTRN_PROJECTHIERARCHY SET ASSIGNEDTOUSERID=? WHERE PROJECTWORKFLOWID="+formBean.getProjectWorkflowId()+" AND CURRENTTASKID=?";
			
			PreparedStatement pstmt=connection.prepareStatement(sql);
			
			for (TaskInstanceBean bean : list) {
				
				if(bean.getAssignedtouserid()==null || "".equals(bean.getAssignedtouserid()) || "-1".equals(bean.getAssignedtouserid()))
				{
					pstmt.setNull(1,java.sql.Types.BIGINT );
				}
				else
				{
					pstmt.setLong(1, Long.parseLong(bean.getAssignedtouserid()) );
				}
				pstmt.setLong(2, Long.parseLong(bean.getCurrenttaskid()));	
				pstmt.execute();
			}
			
			status=1;

		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in updateTasks method of "
					+ this.getClass().getSimpleName()+ ex.getMessage(), ex);
		}	
		return status;
	}

	public int updateRoleEmployee(Connection connection, ProjectPlanInstanceBean formBean, ArrayList<RoleEmployeeBean> list) throws NpdException{
		
		int status=0;
		try
		{
			
			CallableStatement cstmt=connection.prepareCall("{call NPD.P_UPDATE_WK_ROLEEMPMAPPING(?,?,?,?)}");
			cstmt.setLong(1, Long.parseLong(formBean.getProjectWorkflowId()));
			
			cstmt.setString(4, "");
			for (RoleEmployeeBean bean : list) {
				
				if(bean.getEmployeeId()==null || "".equals(bean.getEmployeeId()) || "-1".equals(bean.getEmployeeId()))
				{
					cstmt.setNull(2,java.sql.Types.BIGINT );
				}
				else
				{
					cstmt.setLong(2, Long.parseLong(bean.getEmployeeId()) );
				}
				cstmt.setLong(3, Long.parseLong(bean.getRoleId()));
					
				cstmt.execute();
			}
			
			status=1;

		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in updateTasks method of "
					+ this.getClass().getSimpleName()+ ex.getMessage(), ex);
		}	
		return status;
	}

	//rohit verma new cr
	public int updatePrevTask(Connection connection, TtrnProjecthierarchy dto) throws NpdException
	{
		int finalStatus=0;
		int parameterIndex=0;
		try
		{
			String sqlDelete="DELETE FROM NPD.TMST_PROJECTHIERARCHYTASKSFLOW WHERE TASKID="+dto.getCurrenttaskid()+" AND PROJECTWORKFLOWID="+dto.getProjectworkflowid();
			PreparedStatement pstmt=connection.prepareStatement(sqlDelete);
			pstmt.execute();
			
			String [] prevTaskIds=dto.getSelectedPrevTaskIds();
			long projectWorkFlowId;
			long taskId=dto.getCurrenttaskid();
			long prevtaskId;
			CallableStatement cstmt = connection.prepareCall(
			"{call NPD.P_DTS_UPDATETASKFLOW(?,?,?,?)}");
			projectWorkFlowId=dto.getProjectworkflowid();
			
			for (String varPrevTaskId : prevTaskIds) 
			{
				prevtaskId=Long.parseLong(varPrevTaskId);
				
				parameterIndex=0;
				
				cstmt.setLong(++parameterIndex, dto.getProjectworkflowid());
				cstmt.setLong(++parameterIndex, taskId);
				cstmt.setLong(++parameterIndex, prevtaskId);
				cstmt.setString(++parameterIndex, "");
				
				boolean b=cstmt.execute();
		
				String msg=cstmt.getString(4);
				if(!msg.equals("Committed"))
				{
					finalStatus=-1;	
				}
				else
				{
					finalStatus=finalStatus;
				}	
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in updateTasks method of "
					+ this.getClass().getSimpleName()+ ex.getMessage(), ex);
		}	
		return finalStatus;
	}	
}
