package com.ibm.ioes.npd.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.text.StyledEditorKit.BoldAction;

import org.apache.struts.action.ActionMessages;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.ibm.ioes.npd.beans.MeetingBean;
import com.ibm.ioes.npd.beans.ProductCreationBean;
import com.ibm.ioes.npd.beans.ProjectPlanInstanceBean;
import com.ibm.ioes.npd.beans.RfiBean;
import com.ibm.ioes.npd.beans.RoleEmployeeBean;
import com.ibm.ioes.npd.beans.TaskInstanceBean;
import com.ibm.ioes.npd.beans.UserNpdSpocs;

import com.ibm.ioes.npd.exception.NpdException;
import com.ibm.ioes.npd.hibernate.beans.LocationDto;
import com.ibm.ioes.npd.hibernate.beans.MyToDoListDto;
import com.ibm.ioes.npd.hibernate.beans.TmEmployee;
import com.ibm.ioes.npd.hibernate.beans.TmRoles;
import com.ibm.ioes.npd.hibernate.beans.TmstProjecthierarchytasksflow;
import com.ibm.ioes.npd.hibernate.beans.TtrnMeetingattendies;
import com.ibm.ioes.npd.hibernate.beans.TtrnMeetingschedules;
import com.ibm.ioes.npd.hibernate.beans.TtrnProject;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjecthierarchy;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjectworkflow;
import com.ibm.ioes.npd.hibernate.dao.AttachEditProjectPlanDao;
import com.ibm.ioes.npd.hibernate.dao.BaseDaoFactory;
import com.ibm.ioes.npd.hibernate.dao.CommonBaseDao;
import com.ibm.ioes.npd.hibernate.dao.DaoConstants;
import com.ibm.ioes.npd.hibernate.dao.MeetingDao;
import com.ibm.ioes.npd.hibernate.dao.NpdUserDao;
import com.ibm.ioes.npd.hibernate.dao.RoleSelectionDao;
import com.ibm.ioes.npd.hibernate.dao.WorkFlowDao;
import com.ibm.ioes.npd.hibernate.dao.repalceWKRoleEmployeeDao;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.HibernateBeansConstants;
import com.ibm.ioes.npd.utilities.Messages;
import com.ibm.ioes.npd.utilities.NpdConnection;
import com.ibm.ioes.npd.utilities.PagingSorting;
import com.ibm.ioes.npd.utilities.SendMail;
import com.ibm.ioes.npd.utilities.SendMailUtility;
import com.ibm.ioes.npd.utilities.Utility;
//import com.ibm.ws.wim.bridge.assembler.datagraph.SearchDataGraphAssembler;

public class repalceWKRoleEmployeeModel {

	
	public ProjectPlanInstanceBean init_updateRoleEmployee(ProjectPlanInstanceBean bean)
	throws NpdException {
		
		
		Connection conn=null;
		
		try {
		
			ArrayList<TtrnProject> projectList=new ArrayList<TtrnProject>();
		
			conn=NpdConnection.getConnectionObject();
			repalceWKRoleEmployeeDao dao=new repalceWKRoleEmployeeDao();
			projectList=dao.fetchProjects(conn);
			
			
			bean.setProductList(projectList);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in init_updateRoleEmployee method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in init_updateRoleEmployee method of "
					+ this.getClass().getSimpleName()) ;
		}
		finally{
			try {
				NpdConnection.freeConnection(conn);
			} catch (Exception ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();ex.printStackTrace();
				AppConstants.NPDLOGGER.error(ex.getMessage()
						+ " Exception occured in init_updateRoleEmployee method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
				throw new NpdException("Exception occured in init_updateRoleEmployee method of "
						+ this.getClass().getSimpleName()) ;
			}
			
		}
		return bean;
		
		}

	public TtrnProjectworkflow getActiveProjectWorkflowId(String projectId) 
		throws NpdException {
			
			
			Connection conn=null;
			TtrnProjectworkflow pw=null;
			
			try {
			
				ArrayList<TtrnProject> projectList=new ArrayList<TtrnProject>();
			
				conn=NpdConnection.getConnectionObject();
				
				AttachEditProjectPlanDao dao=new AttachEditProjectPlanDao();
				
				pw=dao.getActiveProjectWorkflowId(conn, projectId);
				
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				AppConstants.NPDLOGGER.error(ex.getMessage()
						+ " Exception occured in getActiveProjectWorkflowId method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
				throw new NpdException("Exception occured in getActiveProjectWorkflowId method of "
						+ this.getClass().getSimpleName()) ;
			}
			finally{
				try {
					NpdConnection.freeConnection(conn);
				} catch (Exception ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();ex.printStackTrace();
					AppConstants.NPDLOGGER.error(ex.getMessage()
							+ " Exception occured in getActiveProjectWorkflowId method of "
							+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
					throw new NpdException("Exception occured in getActiveProjectWorkflowId method of "
							+ this.getClass().getSimpleName()) ;
				}
				
			}
			return pw;
			
	}
	
	public int replaceRoleEmployee(ProjectPlanInstanceBean formBean, ArrayList<RoleEmployeeBean> list) throws NpdException{
		Connection connection=null;
		int status=0;
		try{
			
			//call update method
			repalceWKRoleEmployeeDao dao=new repalceWKRoleEmployeeDao();
			connection=NpdConnection.getConnectionObject();
			
			//ArrayList<ArrayList<TtrnProjecthierarchy>> replacedToDoListTasks=new ArrayList<ArrayList<TtrnProjecthierarchy>>();
			//HashMap map=dao.getReplacedTasks(connection,list,formBean.getProjectId());
			
			//replacedToDoListTasks=(ArrayList<ArrayList<TtrnProjecthierarchy>>)map.get("todolist");
			//ArrayList<ArrayList<TtrnProject>> replacedPendingRFITasks=null;
			//replacedPendingRFITasks=(ArrayList<ArrayList<TtrnProject>>)map.get("rfilist");
			
			connection.setAutoCommit(false);
			
			int op_status=dao.replaceRoleEmployee(connection,formBean,list);
			
			if(op_status==1)
			{
				//success
				status=1;
				//formBean.setReplacedToDoListTasks(replacedToDoListTasks);
				//formBean.setReplacedPendingRFITasks(replacedPendingRFITasks);
			}
			else if(op_status==-1)
			{
				//failure
				status=-1;
				connection.rollback();
			}

			connection.commit();
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in updateRoleEmployee method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			try {
				connection.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new NpdException("Exception occured in updateRoleEmployee method of "
						+ this.getClass().getSimpleName()) ;
			}
			throw new NpdException("Exception occured in updateRoleEmployee method of "
					+ this.getClass().getSimpleName()) ;
		}
		finally{
			try {
				NpdConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				AppConstants.NPDLOGGER.error(e.getMessage()
						+ " Exception occured in updateRoleEmployee method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(e));
				throw new NpdException("Exception occured in updateRoleEmployee method of "
						+ this.getClass().getSimpleName()) ;
			}
		}
		return status;
	}

	
	public int sendMailForTaskNewAssigns(ProjectPlanInstanceBean formBean,ArrayList<RoleEmployeeBean> list) {
		RoleSelectionDao roleSelectionDao = new RoleSelectionDao();
		Connection connection=null;
		try
		{
			connection=NpdConnection.getConnectionObject();	
			//fill removed roles
			//fill replaced users
			
			if(list==null || list.size()==0)
			{
				return 1;
			}
			
			//String[] replacedUsers = selectedUsers.split(",");
			//String[] replacedRoles =userNpdSpocs.getUserReplacedRoleIds().split(",");
			
			if(list!=null && list.size()>0)
			{
			//-- mail to users for new task assignment
				
				String selectedUsers="";
				for (RoleEmployeeBean bean : list) {
					selectedUsers=selectedUsers+","+bean.getEmployeeId();
				}
				if(selectedUsers.length()>0)
				{
					selectedUsers=selectedUsers.substring(1);
				}
			
				ArrayList<TmEmployee> employeeList= null;
				HashMap<Long,TmEmployee> idToEmployeeBean=new HashMap<Long, TmEmployee>();
				employeeList = roleSelectionDao.getEmployeeList(selectedUsers,connection); 
				for (TmEmployee employee : employeeList) {
					Long npdempid=employee.getNpdempid();
					idToEmployeeBean.put(npdempid, employee);
				}
				
				
				String selectedRoles="";
				for (RoleEmployeeBean bean : list) {
					selectedRoles=selectedRoles+","+bean.getRoleId();
				}
				if(selectedRoles.length()>0)
				{
					selectedRoles=selectedRoles.substring(1);
				}
			
				ArrayList<TmRoles> roles= null;
				HashMap<Integer,TmRoles> idToRoleBean=new HashMap<Integer, TmRoles>();
				roles = roleSelectionDao.getRoles(selectedRoles,connection); 
				for (TmRoles role : roles) {
					int roleId=role.getRoleid();
					idToRoleBean.put(roleId, role);
				}
				
				
				//ArrayList<String> temp= roleSelectionDao.getRoleNames(replacedRoles[i],connection);
			//loop throgh each new user with different roleId
				//find tasks which were new assigned
				//generate mail for user
				//send mail
			//end:loop
				
				//ArrayList<ArrayList<TtrnProjecthierarchy>> replacedToDoListTasks=null;
				//ArrayList<ArrayList<TtrnProject>> replacedPendingRFITasks=null;
				
				//replacedToDoListTasks=formBean.getReplacedToDoListTasks();
				//replacedPendingRFITasks=formBean.getReplacedPendingRFITasks();
				
				SendMail mail=new SendMail();
				String[] colors={"\"#FFEBC6\"","\"#ffcf9f\""};
				String[] replacedUsers = selectedUsers.split(",");
				String[] replacedRoles = selectedRoles.split(",");
				
				for(int i=0;i<replacedUsers.length;i++)
				{
					//check whether any new tasks are assigned
					
					//ArrayList<TtrnProjecthierarchy> replacedToDoListTasks_forUser=null;
					//ArrayList<TtrnProject> replacedPendingRFITasks_forUser=null;
					
					//replacedToDoListTasks_forUser=replacedToDoListTasks.get(i);
					//replacedPendingRFITasks_forUser=replacedPendingRFITasks.get(i);
					
					/*if((replacedPendingRFITasks_forUser==null 
							|| replacedPendingRFITasks_forUser.size()==0) 
							&& (replacedToDoListTasks_forUser==null 
									|| replacedToDoListTasks_forUser.size()==0))
					{
						continue;//skip for this as no change
					}*/
					
					
					TmEmployee employee=idToEmployeeBean.get(new Long(replacedUsers[i]));
					String emailId=employee.getEmail();
					String []to=new String[]{emailId};
					String subject="New Tasks Assigned";
					//String from=Messages.getMessageValue("MAIL_FROM");
					String from=null;
					StringBuffer sb=new StringBuffer();
					
					sb.append("<HTML>");
					sb.append("<HEAD>");
					sb.append("</HEAD>");
					sb.append("<BODY>");
					sb.append("<TABLE>");
					
					String roleName= ((TmRoles)idToRoleBean.get(new Integer(replacedRoles[i]))).getRolename();
					if(roleName==null )
					{
						//set status;
						continue;
					}
					
					sb.append("<TR><TD>"+Messages.getMessageValue("Mail_Header")+"<BR>");
					sb.append("</TD></TR>");
					
					sb.append("<TR><TD>You are assigned following Tasks for the Role - "+roleName);
					sb.append("</TD></TR>");
					
					sb.append("<BR><BR>");
					
					//if(replacedToDoListTasks_forUser!=null && replacedToDoListTasks_forUser.size()!=0)
					//{
						int anyInfo=0;
						
						MyToDoListServicesImpl objServices = new MyToDoListServicesImpl();
						MyToDoListDto searchDto =new MyToDoListDto();
						searchDto.setUserId(new Integer(String.valueOf(employee.getNpdempid())));
						searchDto.setRoleId(Integer.parseInt(replacedRoles[i]));
						
						ArrayList<MyToDoListDto> objAccountList = new ArrayList<MyToDoListDto>();
						objAccountList =objServices.myToDoListNpdSpocMail(searchDto);
						
						StringBuffer hd=new StringBuffer();
						hd.append("<TR><TD>In To Do list :");
						hd.append("</TD></TR>");
						hd.append("<TR><TD><TABLE width=\"100%\" border=\"1\"  cellpadding=\"3\" cellspacing=\"1\" align=\"center\"><TR bgcolor=\"#FF9255\">");
						hd.append("<TD nowrap >S.No</TD>");
						hd.append("<TD nowrap >ProjectId</TD>");
						hd.append("<TD nowrap >Project Name</TD>");
						hd.append("<TD nowrap >Priority Of Launch</TD>");
						hd.append("<TD nowrap >Product Brief</TD>");
						hd.append("<TD nowrap >Target Market</TD>");
						hd.append("<TD nowrap >Product Category</TD>");
						hd.append("<TD nowrap >NPD Category</TD>");
						hd.append("<TD nowrap >Airtel Potential</TD>");
						hd.append("<TD nowrap >Total Market Potential</TD>");
						hd.append("<TD nowrap >Capex Requirement</TD>");
						hd.append("<TD nowrap >Stage Name</TD>");
						hd.append("<TD nowrap >Task Name</TD>");
						hd.append("<TD nowrap >Task Status</TD></TR>");
						
						int flag=0;
						
						/*HashSet<String> taskSet=new HashSet<String>();
						for (TtrnProjecthierarchy task : replacedToDoListTasks_forUser) {
							taskSet.add(task.getProjectworkflowid()+"-"+task.getCurrenttaskid());
						}*/
						int index=1;
						
						
						for (MyToDoListDto dto : objAccountList) {
							//if(taskSet.contains(dto.getProjectDetails().getProjectworkflowid()+"-"+dto.getTaskId()))
							if(formBean.getProjectId().equals(""+dto.getProjectDetails().getProjectid()))
							{
								if(flag==0)
								{
									sb.append(hd);
									flag++;
								}
								anyInfo++;
								TtrnProject row=dto.getProjectDetails();
								sb.append("<TR bgcolor="+colors[(index)%2]+"><TD nowrap >"+index+"</TD>");
								index++;
								sb.append("<TD nowrap >"+row.getProjectid()+"</TD>");
								sb.append("<TD nowrap >"+row.getProjectName()+"</TD>");
								sb.append("<TD nowrap >"+row.getLaunchPriority()+"</TD>");
								sb.append("<TD nowrap >"+row.getProductBrief()+"</TD>");
								sb.append("<TD nowrap >"+row.getTargetMkt()+"</TD>");
								sb.append("<TD nowrap >"+row.getPrdCategory().getProdcatdesc()+"</TD>");
								sb.append("<TD nowrap >"+row.getNpdCategory().getNpdcatdesc()+"</TD>");
								sb.append("<TD nowrap >"+row.getAirtelPotential()+"</TD>");
								sb.append("<TD nowrap >"+row.getTotalMktPotential()+"</TD>");
								sb.append("<TD nowrap >"+row.getCapexReq()+"</TD>");
								sb.append("<TD nowrap >"+dto.getStageName()+"</TD>");
								sb.append("<TD nowrap >"+dto.getTaskName()+"</TD>");
								sb.append("<TD nowrap >"+dto.getCurrentTaskStatus()+"</TD></TR>");
							}
						}
						
						if(flag!=0)
						{
							sb.append("</TABLE></TD></TR>");
							sb.append("<TR><TD>&nbsp;");
							sb.append("</TD></TR>");
						}
					//}
					
					//if(replacedPendingRFITasks_forUser!=null && replacedPendingRFITasks_forUser.size()!=0)
					//{
						
						
						RfiModel serv = new RfiModel();
						//MyToDoListDto searchDto =new MyToDoListDto();
						//searchDto.setUserId(new Integer(String.valueOf(employee.getNpdempid())));
						//searchDto.setRoleId(Integer.parseInt(replacedRoles[i]));
						
						RfiBean rfiBean=new RfiBean();
						serv.rfiDetailsNpdSpocMail(rfiBean, employee);
						
						flag=0;
						hd=new StringBuffer();
						
						hd.append("<TR><TD>In RFI list :");
						hd.append("</TD></TR>");
						hd.append("<TR><TD><TABLE width=\"100%\" border=\"1\"  cellpadding=\"3\" cellspacing=\"1\" align=\"center\"><TR bgcolor=\"#FF9255\">");
						hd.append("<TD nowrap >S.No</TD>");
						hd.append("<TD nowrap >ProjectId</TD>");
						hd.append("<TD nowrap >Project Name</TD>");
						hd.append("<TD nowrap >Priority Of Launch</TD>");
						hd.append("<TD nowrap >Product Brief</TD>");
						hd.append("<TD nowrap >Target Market</TD>");
						hd.append("<TD nowrap >Product Category</TD>");
						hd.append("<TD nowrap >NPD Category</TD>");
						hd.append("<TD nowrap >Airtel Potential</TD>");
						hd.append("<TD nowrap >Total Market Potential</TD>");
						hd.append("<TD nowrap >Capex Requirement</TD>");
						hd.append("<TD nowrap >Current Stage</TD>");
						hd.append("<TD nowrap >Current Task</TD>");
						hd.append("<TD nowrap >No of Days With Current User</TD></TR>");
						
						
						/*HashSet<String> rfiSet=new HashSet<String>();
						for (TtrnProject rfi : replacedPendingRFITasks_forUser) {
							rfiSet.add(rfi.getRfiId());
						}*/
						index=1;
						ArrayList<TtrnProject> rfiList=rfiBean.getProjectDetailList();
						
						for (TtrnProject dto : rfiList) {
							if(dto.getRoleId()==Long.parseLong(replacedRoles[i]) && dto.getProjectid()==Long.parseLong(formBean.getProjectId()))
							{
								if(flag==0)
								{
									sb.append(hd);
									flag++;
								}
								anyInfo++;
								sb.append("<TR bgcolor="+colors[(index)%2]+"><TD nowrap >"+index+"</TD>");
								index++;
								sb.append("<TD nowrap >"+dto.getProjectid()+"</TD>");
								sb.append("<TD nowrap >"+dto.getProjectName()+"</TD>");
								sb.append("<TD nowrap >"+dto.getLaunchPriority()+"</TD>");
								sb.append("<TD nowrap >"+dto.getProductBrief()+"</TD>");
								sb.append("<TD nowrap >"+dto.getTargetMkt()+"</TD>");
								sb.append("<TD nowrap >"+dto.getPrdCategory().getProdcatdesc()+"</TD>");
								sb.append("<TD nowrap >"+dto.getNpdCategory().getNpdcatdesc()+"</TD>");
								sb.append("<TD nowrap >"+dto.getAirtelPotential()+"</TD>");
								sb.append("<TD nowrap >"+dto.getTotalMktPotential()+"</TD>");
								sb.append("<TD nowrap >"+dto.getCapexReq()+"</TD>");
								sb.append("<TD nowrap >"+dto.getCurrentstatus()+"</TD>");
								sb.append("<TD nowrap >"+dto.getCurrentTask()+"</TD>");
								sb.append("<TD nowrap >"+dto.getDaysWithCurrentUser()+"</TD></TR>");
							}
						}
						
						if(flag!=0)
						{
							sb.append("</TABLE></TD></TR>");
						}
					//}
						
					sb.append("<BR><BR>");
					
					sb.append("<TR><TD>"+Messages.getMessageValue("Mail_Footer")+"</TD></TR>");
					
					sb.append("</TABLE>");
					sb.append("</BODY>");
					sb.append("</HTML>");
					
					
					if(anyInfo>0)
					{
					
					String message=sb.toString();
					//System.err.println("Mail Sent :"+to[0]+"\n"+message);
					try
					{
					mail.postMail(to, new String[0], new String[0], subject, message, from, null);
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
						
					}
					}
					
				}
			}
		
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			
		}
		finally {

			try {
				NpdConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}
		return 1;
	}	
}
