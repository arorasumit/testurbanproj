package com.ibm.ioes.npd.hibernate.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import com.ibm.ioes.npd.beans.ProjectPlanInstanceBean;
import com.ibm.ioes.npd.beans.RoleEmployeeBean;
import com.ibm.ioes.npd.exception.NpdException;
import com.ibm.ioes.npd.hibernate.beans.PerformanceReportDto;
import com.ibm.ioes.npd.hibernate.beans.TmEmployee;
import com.ibm.ioes.npd.hibernate.beans.TmReferencedocs;
import com.ibm.ioes.npd.hibernate.beans.TmRoles;
import com.ibm.ioes.npd.hibernate.beans.TmstProjecthierarchytasksflow;
import com.ibm.ioes.npd.hibernate.beans.TtrnProject;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjecthierarchy;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjectworkflow;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.HibernateBeansConstants;
import com.ibm.ioes.npd.utilities.NpdConnection;
import com.ibm.ioes.npd.utilities.PagingSorting;
import com.ibm.ioes.npd.utilities.Utility;

public class repalceWKRoleEmployeeDao {

	public ArrayList<TtrnProject> fetchProjects(Connection conn)throws NpdException {
		
		ArrayList<TtrnProject> projectList=new ArrayList<TtrnProject>();
		try{
			
			
			String sql="SELECT PROJECTID,PROJECT_NAME,PROJECTSTATUS FROM NPD.TTRN_PROJECT" +
			" WHERE PROJECTSTATUS IN (1,0)";
			ResultSet rs=conn.createStatement().executeQuery(sql);
			while(rs.next())
			{
				TtrnProject dto=new TtrnProject();
				dto.setProjectid(rs.getLong("PROJECTID"));
				dto.setProjectName(rs.getString("PROJECT_NAME"));
				dto.setCSV_id_name(dto.getProjectid()+" ("+dto.getProjectName()+")");
				
				projectList.add(dto);
			}
			  
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in fetchProjects method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in fetchProjects method of "
					+ this.getClass().getSimpleName()) ;
		}
		return projectList;
	}

	public int replaceRoleEmployee(Connection connection, ProjectPlanInstanceBean formBean, ArrayList<RoleEmployeeBean> list) throws NpdException{
		
		int status=0;
		try
		{
			
			CallableStatement cstmt=connection.prepareCall("{call NPD.P_REPLACE_WK_ROLEEMPMAPPING(?,?,?,?)}");
			cstmt.setLong(1, Long.parseLong(formBean.getProjectId()));
			
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

	public HashMap getReplacedTasks(Connection connection, 
			ArrayList<RoleEmployeeBean> list,String projectId) throws NpdException{
		HashMap map=new HashMap();
		ArrayList<ArrayList<TtrnProjecthierarchy>> replacedToDoListTasks=new ArrayList<ArrayList<TtrnProjecthierarchy>>();
		ArrayList<ArrayList<TtrnProject>> replacedPendingRFITasks=new ArrayList<ArrayList<TtrnProject>>();
		CallableStatement cstmt=null;
		try
		{
			
		cstmt=connection.prepareCall("{ call NPD.FETCH_REPLACE_TASK_ASSIGNEE_FOR_PROJECTID(?,?,?) }");
		
		for (RoleEmployeeBean bean : list) {
			
			cstmt.setLong(1, Long.parseLong(bean.getRoleId()));
			cstmt.setLong(2, Long.parseLong(bean.getEmployeeId()));
			cstmt.setLong(3, Long.parseLong(projectId));
			
			ResultSet temp_rs=cstmt.executeQuery();
			ArrayList<TtrnProjecthierarchy> replacedToDoListTasks_forUser=new ArrayList<TtrnProjecthierarchy>();
			while(temp_rs.next())
			{
				TtrnProjecthierarchy temp=new TtrnProjecthierarchy();
				temp.setCurrenttaskid(temp_rs.getLong("CURRENTTASKID"));
				temp.setProjectworkflowid(temp_rs.getLong("PROJECTWORKFLOWID"));
				replacedToDoListTasks_forUser.add(temp);
				
			}
			
											
			replacedToDoListTasks.add(replacedToDoListTasks_forUser);
			cstmt.close();
		}	
			cstmt=connection.prepareCall("{ call NPD.FETCH_REPLACE_RFI_ASSIGNEE_FOR_PROJECTID(?,?,?) }");
			
			for (RoleEmployeeBean bean2 : list) {
				
				cstmt.setLong(1, Long.parseLong(bean2.getRoleId()));
				cstmt.setLong(2, Long.parseLong(bean2.getEmployeeId()));
				cstmt.setLong(3, Long.parseLong(projectId));
				
				ResultSet temp_rs2=cstmt.executeQuery();
				ArrayList<TtrnProject> replacedPendingRFITasks_forUser=new ArrayList<TtrnProject>();
				while(temp_rs2.next())
				{

					TtrnProject temp=new TtrnProject();
					temp.setRfiId(temp_rs2.getString("PROJECTRFIID"));
					replacedPendingRFITasks_forUser.add(temp);
				}
												
				replacedPendingRFITasks.add(replacedPendingRFITasks_forUser);
				cstmt.close();
			
			
		}
			
			map.put("todolist",replacedToDoListTasks );
			map.put("rfilist",replacedPendingRFITasks );
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in getReplacedTasks method of "
					+ this.getClass().getSimpleName()+ ex.getMessage(), ex);
		}
		finally
		{
			try {
				cstmt.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
				AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(ex));
				throw new NpdException("Exception occured in getReplacedTasks method of "
						+ this.getClass().getSimpleName()+ ex.getMessage(), ex);
			}
		}
		return map;
	}

	

}
