/**
 * 
 */
package com.ibm.ioes.npd.model;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Session;

import com.ibm.ioes.npd.beans.RfiBean;
import com.ibm.ioes.npd.beans.UserNpdSpocs;
import com.ibm.ioes.npd.exception.NpdException;
import com.ibm.ioes.npd.hibernate.beans.MyToDoListDto;
import com.ibm.ioes.npd.hibernate.beans.TmEmployee;
import com.ibm.ioes.npd.hibernate.beans.TmRoleempmapping;
import com.ibm.ioes.npd.hibernate.beans.TmRoles;
import com.ibm.ioes.npd.hibernate.beans.TtrnProject;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjecthierarchy;
import com.ibm.ioes.npd.hibernate.dao.BaseDaoFactory;
import com.ibm.ioes.npd.hibernate.dao.CommonBaseDao;
import com.ibm.ioes.npd.hibernate.dao.DaoConstants;
import com.ibm.ioes.npd.hibernate.dao.NpdUserDao;
import com.ibm.ioes.npd.hibernate.dao.RoleSelectionDao;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.HibernateBeansConstants;
import com.ibm.ioes.npd.utilities.Messages;
import com.ibm.ioes.npd.utilities.NpdConnection;
import com.ibm.ioes.npd.utilities.SendMail;

/**
 * @author Disha
 * 
 */
public class NpdUserModel extends CommonBaseModel {

	/**
	 * This method gets the list of all employees and all Roles from the
	 * database.
	 * 
	 * @param UserNpdSpocs
	 *            Bean
	 * @return
	 * @throws Exception
	 */

	public UserNpdSpocs viewNpdSpocs(UserNpdSpocs npdSpocs) throws Exception {

		NpdUserDao npdUserDao = (NpdUserDao) BaseDaoFactory
				.getInstance(DaoConstants.NPD_USER_DAO);
		//Session hibernateSession = null;
		//hibernateSession = CommonBaseDao.beginTrans();
		Connection conn=null;
		ArrayList selectedRoleList = null;
		TmRoleempmapping tmRoleempmapping = new TmRoleempmapping();
		CommonBaseDao commonBaseDao = BaseDaoFactory
				.getInstance(DaoConstants.BASE_DAO_CLASS);
		
		String previousRoleId = "";
		try {
			conn=NpdConnection.getConnectionObject();
			ArrayList<TmRoles> allRoles=npdUserDao.fetchAllRoles(conn);
			if (npdSpocs.getEmployeeId() != null
					&& !npdSpocs.getEmployeeId().equals("") && !"-1".equals(npdSpocs.getEmployeeId())) {
				
				String temp=npdSpocs.getEmployeeId();
				BeanUtils.copyProperties(npdSpocs,new UserNpdSpocs() );
				npdSpocs.setEmployeeId(temp);
				//Check if it exist in TmEmployee 
				//if yes fetch details
				
				String hrmsid=npdSpocs.getEmployeeId();
				
				TmEmployee employee=npdUserDao.fetchEmpByOracleIdInTmEmployee(conn,hrmsid);
				
				if(employee==null)
				{
					//employee exist only in hrms
					//fill unselected role list,l1,l2
					npdSpocs.setTypeOfEmployee(AppConstants.Type_of_employee_new);
					npdSpocs.setUnselectedRoles(allRoles);
					npdSpocs.setLevel1List(allRoles);
					npdSpocs.setLevel2List(allRoles);
					
					npdSpocs.setLevel1Id("-1");
					npdSpocs.setLevel2Id("-1");
					npdSpocs.setLevel1EmployeeId("-1");
					npdSpocs.setLevel2EmployeeId("-1");
					npdSpocs.setNpdempid(0);
				}
				else
				{
					npdSpocs.setTypeOfEmployee(AppConstants.Type_of_employee_old);	
					npdSpocs.setNpdempid(employee.getNpdempid());
					
					//fill unselected,selected,l1,l2,l1e,l2e list and set values of l1,l2,l1e,l2e
					ArrayList<TmRoles> roleOfEmployee=npdUserDao.fetchRolesOfEmployee(conn,employee);
					npdSpocs.setSelectedRoles(roleOfEmployee);
					
					HashMap<Integer,TmRoles> rolemap_id_to_bean=new HashMap<Integer, TmRoles>();
					for (TmRoles roles : allRoles) {
						rolemap_id_to_bean.put(roles.getRoleid(), roles);
					}
					
					if(roleOfEmployee!=null)
					{
						for (TmRoles roles : roleOfEmployee) {
							rolemap_id_to_bean.remove(roles.getRoleid());
							previousRoleId = previousRoleId + roles.getRoleid() + ",";
						}
					}
					if(!"".equals(previousRoleId))
					{
						npdSpocs.setPreviousRoleId(previousRoleId.substring(0,
								previousRoleId.length() - 1));
					}
					else
					{
						npdSpocs.setPreviousRoleId(null);
					}
					
					ArrayList<TmRoles> unselectedRoles=new ArrayList<TmRoles>(rolemap_id_to_bean.values());
					Collections.sort(unselectedRoles, new RoleNameComparator());
					
					npdSpocs.setUnselectedRoles(unselectedRoles);
					
					npdSpocs.setLevel1List(allRoles);
					npdSpocs.setLevel2List(allRoles);
					
					if(employee.getLevel1id()!=null && !"".equals(employee.getLevel1id()))
					{
						npdSpocs.setLevel1Id(employee.getLevel1id());
						TmEmployee employeeDto=new TmEmployee();
						employeeDto.setCurrentRoleId(Long.parseLong(employee.getLevel1id()));
						npdSpocs.setLevel1EmployeeList(npdUserDao.fetchEmployeesOfRoleNpdSpoc(conn,employeeDto,employee.getNpdempid()));
					}
					else
					{
						npdSpocs.setLevel1Id("-1");
					}
					
					if(employee.getLevel2id()!=null && !"".equals(employee.getLevel2id()))
					{
						npdSpocs.setLevel2Id(employee.getLevel2id());
						TmEmployee employeeDto=new TmEmployee();
						employeeDto.setCurrentRoleId(Long.parseLong(employee.getLevel2id()));
						npdSpocs.setLevel2EmployeeList(npdUserDao.fetchEmployeesOfRoleNpdSpoc(conn,employeeDto,employee.getNpdempid()));
					}
					else
					{
						npdSpocs.setLevel2Id("-1");
					}
					
					if (employee.getLevel1employeeid() != 0) 
						npdSpocs.setLevel1EmployeeId(String.valueOf(employee.getLevel1employeeid()));
					
					if (employee.getLevel2employeeid() != 0)
						npdSpocs.setLevel2EmployeeId(new Long(employee.getLevel2employeeid()).toString());
					
					
				}
				
				
				
				
				/*selectedRoleList = npdUserDao.getRolesOfEmployee(npdSpocs
						.getEmployeeId(), hibernateSession);
				if (selectedRoleList != null && selectedRoleList.size() > 0) {
					String roleIds[] = new String[selectedRoleList.size()];

					for (int x = 0; x < selectedRoleList.size(); x++) {
						tmRoleempmapping = (TmRoleempmapping) selectedRoleList
								.get(x);
						roleIds[x] = new Integer(tmRoleempmapping.getTmRoles()
								.getRoleid()).toString();
						previousRoleId = previousRoleId + roleIds[x] + ",";
					}
					npdSpocs.setRoleId(roleIds);
					npdSpocs.setPreviousRoleId(previousRoleId.substring(0,
							previousRoleId.length() - 1));
				} else {
					npdSpocs.setRoleId(null);
					npdSpocs.setPreviousRoleId(null);
				}

				tmEmployee = (TmEmployee) commonBaseDao.findById(Long
						.parseLong(npdSpocs.getEmployeeId()),
						HibernateBeansConstants.HBM_EMPLOYEE, hibernateSession);

				if (npdUserDao.getRolesForEscalationLevel(npdSpocs.getRoleId(),
						"-1", hibernateSession).size() > 0)
					npdSpocs.setLevel1List(npdUserDao
							.getRolesForEscalationLevel(npdSpocs.getRoleId(),
									"-1", hibernateSession));

				if (tmEmployee != null && tmEmployee.getLevel1employeeid() != 0) 
					npdSpocs.setLevel1EmployeeId(String.valueOf(tmEmployee.getLevel1employeeid()));
				if (tmEmployee != null && tmEmployee.getLevel2employeeid() != 0)
					npdSpocs.setLevel2EmployeeId(new Long(tmEmployee.getLevel2employeeid()).toString());
				
				if (tmEmployee != null && tmEmployee.getLevel1() != null) {
					npdSpocs.setLevel1Id(new Integer(tmEmployee.getLevel1()
							.getRoleid()).toString());
					
				

					//set employee list of role 
					
					TmEmployee searchDto=new TmEmployee();
					searchDto.setCurrentRoleId(Long.parseLong(npdSpocs.getLevel1Id()));
					
					npdSpocs.setLevel1EmployeeList(npdUserDao.fetchEmployeesOfRoleNpdSpoc(hibernateSession,searchDto));
					
					if (npdUserDao.getRolesForEscalationLevel(
							npdSpocs.getRoleId(), npdSpocs.getLevel2Id(),
							hibernateSession).size() > 0)
						npdSpocs.setLevel2List(npdUserDao
								.getRolesForEscalationLevel(npdSpocs
										.getRoleId(), npdSpocs.getLevel1Id(),
										hibernateSession));

					if (tmEmployee != null && tmEmployee.getLevel2() != null)
						npdSpocs.setLevel2Id(new Integer(tmEmployee.getLevel2()
								.getRoleid()).toString());
					
					searchDto=new TmEmployee();
					searchDto.setCurrentRoleId(Long.parseLong(npdSpocs.getLevel2Id()));
					npdSpocs.setLevel2EmployeeList(npdUserDao.fetchEmployeesOfRoleNpdSpoc(hibernateSession,searchDto));
				} else {
					npdSpocs.setLevel1Id("-1");
					npdSpocs.setLevel2Id("-1");
				}*/
			}

			npdSpocs.setEmployeeList(npdUserDao.fetchAllEmployees(conn));
			
			/*if (commonBaseDao.getAllEntriesInATable(hibernateSession,
					HibernateBeansConstants.HBM_ROLE).size() > 0)
				npdSpocs.setRoleList(commonBaseDao.getAllEntriesInATableOrder(
						hibernateSession, HibernateBeansConstants.HBM_ROLE,"rolename","asc"));*/
			npdSpocs.setButtonClicked("S");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			NpdConnection.freeConnection(conn);
			//CommonBaseDao.closeTrans(hibernateSession);
		}

		return npdSpocs;
	}
	
	
	/*public UserNpdSpocs viewNpdSpocs1(UserNpdSpocs npdSpocs) throws Exception {

		NpdUserDao npdUserDao = (NpdUserDao) BaseDaoFactory
				.getInstance(DaoConstants.NPD_USER_DAO);
		Session hibernateSession = null;
		hibernateSession = CommonBaseDao.beginTrans();
		ArrayList selectedRoleList = null;
		TmRoleempmapping tmRoleempmapping = new TmRoleempmapping();
		CommonBaseDao commonBaseDao = BaseDaoFactory
				.getInstance(DaoConstants.BASE_DAO_CLASS);
		TmEmployee tmEmployee = new TmEmployee();
		String previousRoleId = "";
		try {
			if (npdSpocs.getEmployeeId() != null
					&& !npdSpocs.getEmployeeId().equals("")) {
				selectedRoleList = npdUserDao.getRolesOfEmployee(npdSpocs
						.getEmployeeId(), hibernateSession);
				if (selectedRoleList != null && selectedRoleList.size() > 0) {
					String roleIds[] = new String[selectedRoleList.size()];

					for (int x = 0; x < selectedRoleList.size(); x++) {
						tmRoleempmapping = (TmRoleempmapping) selectedRoleList
								.get(x);
						roleIds[x] = new Integer(tmRoleempmapping.getTmRoles()
								.getRoleid()).toString();
						previousRoleId = previousRoleId + roleIds[x] + ",";
					}
					npdSpocs.setRoleId(roleIds);
					npdSpocs.setPreviousRoleId(previousRoleId.substring(0,
							previousRoleId.length() - 1));
				} else {
					npdSpocs.setRoleId(null);
					npdSpocs.setPreviousRoleId(null);
				}

				tmEmployee = (TmEmployee) commonBaseDao.findById(Long
						.parseLong(npdSpocs.getEmployeeId()),
						HibernateBeansConstants.HBM_EMPLOYEE, hibernateSession);

				if (npdUserDao.getRolesForEscalationLevel(npdSpocs.getRoleId(),
						"-1", hibernateSession).size() > 0)
					npdSpocs.setLevel1List(npdUserDao
							.getRolesForEscalationLevel(npdSpocs.getRoleId(),
									"-1", hibernateSession));

				if (tmEmployee != null && tmEmployee.getLevel1employeeid() != 0) 
					npdSpocs.setLevel1EmployeeId(String.valueOf(tmEmployee.getLevel1employeeid()));
				if (tmEmployee != null && tmEmployee.getLevel2employeeid() != 0)
					npdSpocs.setLevel2EmployeeId(new Long(tmEmployee.getLevel2employeeid()).toString());
				
				if (tmEmployee != null && tmEmployee.getLevel1() != null) {
					npdSpocs.setLevel1Id(new Integer(tmEmployee.getLevel1()
							.getRoleid()).toString());
					
				

					//set employee list of role 
					
					TmEmployee searchDto=new TmEmployee();
					searchDto.setCurrentRoleId(Long.parseLong(npdSpocs.getLevel1Id()));
					
					npdSpocs.setLevel1EmployeeList(npdUserDao.fetchEmployeesOfRoleNpdSpoc(hibernateSession,searchDto));
					
					if (npdUserDao.getRolesForEscalationLevel(
							npdSpocs.getRoleId(), npdSpocs.getLevel2Id(),
							hibernateSession).size() > 0)
						npdSpocs.setLevel2List(npdUserDao
								.getRolesForEscalationLevel(npdSpocs
										.getRoleId(), npdSpocs.getLevel1Id(),
										hibernateSession));

					if (tmEmployee != null && tmEmployee.getLevel2() != null)
						npdSpocs.setLevel2Id(new Integer(tmEmployee.getLevel2()
								.getRoleid()).toString());
					
					searchDto=new TmEmployee();
					searchDto.setCurrentRoleId(Long.parseLong(npdSpocs.getLevel2Id()));
					npdSpocs.setLevel2EmployeeList(npdUserDao.fetchEmployeesOfRoleNpdSpoc(hibernateSession,searchDto));
				} else {
					npdSpocs.setLevel1Id("-1");
					npdSpocs.setLevel2Id("-1");
				}
			}

			if (commonBaseDao.getAllEntriesInATable(hibernateSession,
					HibernateBeansConstants.HBM_EMPLOYEE).size() > 0)
				npdSpocs
						.setEmployeeList(commonBaseDao.getAllEntriesInATableOrder(
								hibernateSession,
								HibernateBeansConstants.HBM_EMPLOYEE,"empname","asc"));

			if (commonBaseDao.getAllEntriesInATable(hibernateSession,
					HibernateBeansConstants.HBM_ROLE).size() > 0)
				npdSpocs.setRoleList(commonBaseDao.getAllEntriesInATableOrder(
						hibernateSession, HibernateBeansConstants.HBM_ROLE,"rolename","asc"));
			npdSpocs.setButtonClicked("S");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CommonBaseDao.closeTrans(hibernateSession);
		}

		return npdSpocs;
	}*/

	/**
	 * This method save the mapping of User with Roles and escalation Level1 and
	 * Level 2 authority
	 * 
	 * @param UserNpdSpocs
	 *            Bean
	 * @return
	 * @throws Exception
	 */

	public boolean saveNpdSpocs(UserNpdSpocs npdSpocs, TmEmployee tmEmployee,
			String selectedUsers) throws Exception {

		NpdUserDao npdUserDao = (NpdUserDao) BaseDaoFactory
				.getInstance(DaoConstants.NPD_USER_DAO);
		boolean insert = npdUserDao.saveRoleAndEscalationLevels(npdSpocs,
				tmEmployee, selectedUsers);
		return insert;
	}

	/**
	 * This method gets the Employee Details along with Roles of an employee
	 * based on its emailId
	 * 
	 * @param email--String
	 * @return
	 * @throws Exception
	 */

	public HashMap getEmployeeDetailsBasedOnEmailID(String ssfUserId)
			throws Exception {
		Session hibernateSession = null;
		HashMap userHashMap = new HashMap();
		try {
			NpdUserDao npdUserDao = (NpdUserDao) BaseDaoFactory
					.getInstance(DaoConstants.NPD_USER_DAO);

			hibernateSession = CommonBaseDao.beginTrans();

			userHashMap = npdUserDao.getEmployeeRolesBasedOnEmailID(ssfUserId,
					hibernateSession);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CommonBaseDao.closeTrans(hibernateSession);
		}

		return userHashMap;
	}

	/**
	 * This method gets the History Details along with Roles of an employee *
	 * 
	 * @param UserNpdSpocs
	 * @return
	 * @throws Exception
	 */

	public UserNpdSpocs searchNpdSpocsRoleHistory(UserNpdSpocs npdSpocs)
			throws Exception {
		Session hibernateSession = null;
		try {
			NpdUserDao npdUserDao = (NpdUserDao) BaseDaoFactory
					.getInstance(DaoConstants.NPD_USER_DAO);

			hibernateSession = CommonBaseDao.beginTrans();

			ArrayList roleHistoryList = new ArrayList();

			roleHistoryList = npdUserDao.viewNpdSpocsRoleHistory(npdSpocs,
					hibernateSession);
			npdSpocs.setRoleHistoryList(roleHistoryList);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CommonBaseDao.closeTrans(hibernateSession);
		}
		return npdSpocs;
	}
	public int sendMailForNewRolesAssigned(UserNpdSpocs userNpdSpocs) throws NpdException {
		
		RoleSelectionDao roleSelectionDao = new RoleSelectionDao();
		Connection connection=null;
		try
		{
			connection=NpdConnection.getConnectionObject();	
			//-- mail to employee about his/her deassigning roles
			ArrayList<TmEmployee> employeeList= null;
			ArrayList<String> roleNames = null;
			employeeList = roleSelectionDao.getEmployeeList(""+userNpdSpocs.getNpdempid(),connection); //
			
			TmEmployee emp=null;
			if(employeeList==null || employeeList.size()==0)
			{
				return -1;
			}
			emp=employeeList.get(0);
			
			int roleDefined=0;
			int levelsDefined=0;
			
			String arr[] = userNpdSpocs.getSelectedRoleId();
			String roleIds = "";
			if(arr!=null)
			{
				for(int id= 0; id < arr.length; id++){
					roleIds = roleIds+","+arr[id];
					roleDefined=1;
				}
			}
			if(roleDefined!=0)
			{
				roleIds = roleIds.substring(0, roleIds.length());
				roleIds = roleIds.substring(1, roleIds.length());
				roleNames = roleSelectionDao.getRoleNames(roleIds,connection);
			}
			
			if(!"1".equals(userNpdSpocs.getEscalation()))
			{
				levelsDefined=1;
			}
			
			
			String level1RoleName=null;
			String level1EmployeeName=null;
			String level1Id=userNpdSpocs.getLevel1Id();
			
			ArrayList<String> temp=null;
			if(levelsDefined==1)
			{
				temp= roleSelectionDao.getRoleNames(level1Id,connection);
				if(temp!=null && temp.size()>0)
				{
					level1RoleName=temp.get(0);
					
					ArrayList<TmEmployee> varemployeeList = roleSelectionDao.getEmployeeList(userNpdSpocs.getLevel1EmployeeId(),connection); //
					
					TmEmployee vartemp=varemployeeList.get(0);
					level1EmployeeName=vartemp.getEmpname();
				}
			}
			
			
			String level2RoleName=null;
			String level2EmployeeName=null;
			String level2Id=userNpdSpocs.getLevel2Id();
			
			if(levelsDefined==1)
			{
				temp= roleSelectionDao.getRoleNames(level2Id,connection);
				if(temp!=null && temp.size()>0)
				{
					level2RoleName=temp.get(0);
					ArrayList<TmEmployee> varemployeeList = roleSelectionDao.getEmployeeList(userNpdSpocs.getLevel2EmployeeId(),connection); //
					
					TmEmployee vartemp=varemployeeList.get(0);
					level2EmployeeName=vartemp.getEmpname();
				}
			}
			
			
			
			SendMail mail=new SendMail();
			String []to=null;
			String []cc=null;
			String []bcc=null;
			String subject=null;
			String from=null;
			String message=null;
			
			
			to=new String[]{emp.getEmail()};
			
			
			subject="Your Roles/Escalation Levels Changed";
			
			StringBuffer sb=new StringBuffer();
			sb.append("<HTML>");
			sb.append("<HEAD>");
			sb.append("</HEAD>");
			sb.append("<BODY>");
			//sb.append("<TABLE>");
			sb.append(Messages.getMessageValue("Mail_Header"));
			sb.append("<br><br>");
			
			sb.append("You Roles/Escalation Levels are modified .");
			sb.append("<br>");
			sb.append("You Roles are :");
			
			if(roleDefined==1)
			{
				int i=1;
				for (String role : roleNames) {
					sb.append("<br> "+i+". "+role);
					i++;
				}
			}
			else
			{
				sb.append("<br> None");
			}
			sb.append("<br><br>");
			if(levelsDefined==1)
			{
				sb.append("Your Escalation Level-1 is "+level1RoleName+"("+level1EmployeeName+")");
			}
			else
			{
				sb.append("Your Escalation Level-1 : None.");
			}
			sb.append("<br>");
			if(levelsDefined==1)
			{
				sb.append("Your Escalation Level-2 is "+level2RoleName+"("+level2EmployeeName+")");
			}
			else
			{
				sb.append("Your Escalation Level-2 : None.");
			}
			sb.append("<br><br>");
			sb.append(Messages.getMessageValue("Mail_Footer"));
			//sb.append("<TABLE>");
			sb.append("</BODY>");
			sb.append("</HTML>");
			
			message=sb.toString();
				//System.err.println("To:"+to[0]+":"+message);
			mail.postMail(to, cc, bcc, subject, message, from, null);
		
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
	public int sendMailForNewTasksAssigned(String selectedUsers, UserNpdSpocs userNpdSpocs) {
		RoleSelectionDao roleSelectionDao = new RoleSelectionDao();
		Connection connection=null;
		try
		{
			connection=NpdConnection.getConnectionObject();	
			//fill removed roles
			//fill replaced users
			
			if(selectedUsers==null || "".equals(selectedUsers))
			{
				return 1;
			}
			
			String[] replacedUsers = selectedUsers.split(",");
			String[] replacedRoles =userNpdSpocs.getUserReplacedRoleIds().split(",");
			
			if(replacedRoles!=null && replacedRoles.length>0)
			{
			//-- mail to users for new task assignment
			
				ArrayList<TmEmployee> employeeList= null;
				HashMap<Long,TmEmployee> idToEmployeeBean=new HashMap<Long, TmEmployee>();
				employeeList = roleSelectionDao.getEmployeeList(selectedUsers,connection); 
				for (TmEmployee employee : employeeList) {
					Long npdempid=employee.getNpdempid();
					idToEmployeeBean.put(npdempid, employee);
				}
				
			//loop throgh each new user with different roleId
				//find tasks which were new assigned
				//group tasks as per project Id
				//generate mail for user
					//loop:for each project: display its header,plan and then all tasks
				//send mail
			//end:loop
				
				ArrayList<ArrayList<TtrnProjecthierarchy>> replacedToDoListTasks=null;
				ArrayList<ArrayList<TtrnProject>> replacedPendingRFITasks=null;
				
				replacedToDoListTasks=userNpdSpocs.getReplacedToDoListTasks();
				replacedPendingRFITasks=userNpdSpocs.getReplacedPendingRFITasks();
				
				SendMail mail=new SendMail();
				String[] colors={"\"#FFEBC6\"","\"#ffcf9f\""};
				for(int i=0;i<replacedUsers.length;i++)
				{
					//check whether any new tasks are assigned
					
					ArrayList<TtrnProjecthierarchy> replacedToDoListTasks_forUser=null;
					ArrayList<TtrnProject> replacedPendingRFITasks_forUser=null;
					
					replacedToDoListTasks_forUser=replacedToDoListTasks.get(i);
					replacedPendingRFITasks_forUser=replacedPendingRFITasks.get(i);
					
					if((replacedPendingRFITasks_forUser==null 
							|| replacedPendingRFITasks_forUser.size()==0) 
							&& (replacedToDoListTasks_forUser==null 
									|| replacedToDoListTasks_forUser.size()==0))
					{
						continue;//skip for this as no change
					}
					
					
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
					
					ArrayList<String> temp= roleSelectionDao.getRoleNames(replacedRoles[i],connection);
					if(temp==null || temp.size()==0)
					{
						//set status;
						continue;
					}
					
					sb.append("<TR><TD>"+Messages.getMessageValue("Mail_Header")+"<BR>");
					sb.append("</TD></TR>");
					
					sb.append("<TR><TD>You are assigned following Tasks for the Role - "+temp.get(0));
					sb.append("</TD></TR>");
					
					sb.append("<BR><BR>");
					
					int anyTask=0;
					
					if(replacedToDoListTasks_forUser!=null && replacedToDoListTasks_forUser.size()!=0)
					{
						
						
						MyToDoListServicesImpl objServices = new MyToDoListServicesImpl();
						MyToDoListDto searchDto =new MyToDoListDto();
						searchDto.setUserId(new Integer(String.valueOf(employee.getNpdempid())));
						searchDto.setRoleId(Integer.parseInt(replacedRoles[i]));
						
						ArrayList<MyToDoListDto> objAccountList = new ArrayList<MyToDoListDto>();
						objAccountList =objServices.myToDoListNpdSpocMail(searchDto);
						
						StringBuffer hd=new StringBuffer();
						hd.append("<TR><TD>In To Do List :");
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
						
						HashSet<String> taskSet=new HashSet<String>();
						for (TtrnProjecthierarchy task : replacedToDoListTasks_forUser) {
							taskSet.add(task.getProjectworkflowid()+"-"+task.getCurrenttaskid());
						}
						int index=1;
						
						
						for (MyToDoListDto dto : objAccountList) {
							if(taskSet.contains(dto.getProjectDetails().getProjectworkflowid()+"-"+dto.getTaskId()))
							{
								if(flag==0)
								{
									sb.append(hd);
									flag++;
								}
								anyTask++;
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
					}
					
					if(replacedPendingRFITasks_forUser!=null && replacedPendingRFITasks_forUser.size()!=0)
					{
						
						
						RfiModel serv = new RfiModel();
						MyToDoListDto searchDto =new MyToDoListDto();
						searchDto.setUserId(new Integer(String.valueOf(employee.getNpdempid())));
						searchDto.setRoleId(Integer.parseInt(replacedRoles[i]));
						
						RfiBean rfiBean=new RfiBean();
						serv.rfiDetailsNpdSpocMail(rfiBean, employee);
						
						int flag=0;
						StringBuffer hd=new StringBuffer();
						
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
						
						
						HashSet<String> rfiSet=new HashSet<String>();
						for (TtrnProject rfi : replacedPendingRFITasks_forUser) {
							rfiSet.add(rfi.getRfiId());
						}
						int index=1;
						ArrayList<TtrnProject> rfiList=rfiBean.getProjectDetailList();
						
						for (TtrnProject dto : rfiList) {
							if(rfiSet.contains(dto.getRfiId()))
							{
								if(flag==0)
								{
									sb.append(hd);
									flag++;
								}
								anyTask++;
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
					}
						
					sb.append("<TR><TD>&nbsp;<BR><BR></TD></TR>");
					
					sb.append("<TR><TD>"+Messages.getMessageValue("Mail_Footer")+"</TD></TR>");
					
					sb.append("</TABLE>");
					sb.append("</BODY>");
					sb.append("</HTML>");
					
					
					
					if(anyTask>0)
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
	public UserNpdSpocs initRoleReport(UserNpdSpocs userNpdSpocs) throws NpdException{
		ArrayList roleList=null;
		Session hibernateSession=null;
		
		try
		{
		hibernateSession=CommonBaseDao.beginTrans();
		CommonBaseDao commonBaseDao=new CommonBaseDao();
		roleList=commonBaseDao.getAllEntriesInATableOrder(
				hibernateSession, HibernateBeansConstants.HBM_ROLE,"rolename","asc");
		userNpdSpocs.setRoleList(roleList);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new NpdException(ex);
		}
		finally
		{
			CommonBaseDao.closeTrans(hibernateSession);
		}
		return userNpdSpocs;
	}

	public ArrayList<TmEmployee> fetchEmployeesOfRole(TmEmployee searchDto) throws NpdException {
		ArrayList<TmEmployee> empList=null;
		
		Connection conn=null;
		try
		{
			
			conn=NpdConnection.getConnectionObject();
			
			NpdUserDao npdUserDao=new NpdUserDao();
			empList=npdUserDao.fetchEmployeesOfRole(conn,searchDto);
		
			
			
		
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(ex));
			
			throw new NpdException("Exception occured in fetchEmployeesOfRole method of "
					+ this.getClass().getSimpleName()+ ex.getMessage(), ex);
		}
		finally
		{
			try {
				NpdConnection.freeConnection(conn);
			} catch (Exception ex) {
				ex.printStackTrace();
				AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(ex));
				
				throw new NpdException("Exception occured in fetchEmployeesOfRole method of "
						+ this.getClass().getSimpleName()+ ex.getMessage(), ex);
			}
		}
		
		
		return empList;

	}
	
}

 class RoleNameComparator implements Comparator<TmRoles>
{

	public int compare(TmRoles o1, TmRoles o2) {
		// TODO Auto-generated method stub
		return o1.getRolename().compareToIgnoreCase(o2.getRolename());
	}
	
}
