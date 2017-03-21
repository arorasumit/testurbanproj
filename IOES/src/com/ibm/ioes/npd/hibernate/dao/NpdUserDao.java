package com.ibm.ioes.npd.hibernate.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import com.ibm.ioes.npd.beans.UserNpdSpocs;
import com.ibm.ioes.npd.exception.NpdException;
import com.ibm.ioes.npd.hibernate.beans.TmEmployee;
import com.ibm.ioes.npd.hibernate.beans.TmEmployeeRoleHistory;
import com.ibm.ioes.npd.hibernate.beans.TmRoleempmapping;
import com.ibm.ioes.npd.hibernate.beans.TmRoles;
import com.ibm.ioes.npd.hibernate.beans.TtrnProject;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjecthierarchy;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.HibernateBeansConstants;
import com.ibm.ioes.npd.utilities.NpdConnection;
import com.ibm.ioes.npd.utilities.PagingSorting;
import com.ibm.ioes.npd.utilities.Utility;
import com.ibm.ioes.utilities.DbConnection;

/**
 * @author Disha
 * @version 1.0
 */

public class NpdUserDao extends CommonBaseDao {

	// This method get All the Role List for escalation Level 1 and Level 2
	// from in Database.

	public ArrayList getRolesForEscalationLevel(String[] roleIds,
			String roleIdLevel1, Session hibernateSession) throws Exception {

		ArrayList roleList = new ArrayList();

		Criteria ce = hibernateSession.createCriteria(TmRoles.class);

		// If Level 1 Role List needs to be populated
		if (roleIds != null) {
			for (int x = 0; x < roleIds.length; x++) {
				if (roleIds[x] != null && !roleIds[x].equalsIgnoreCase(""))
					ce = ce.add(Restrictions.ne("roleid", new Integer(
							roleIds[x])));
			}
		}
		// If Level 2 Role List needs to be populated
		if (roleIdLevel1 != null && !(roleIdLevel1.equalsIgnoreCase(""))
				&& !roleIdLevel1.equalsIgnoreCase("-1")) {
			ce = ce.add(Restrictions.ne("roleid", new Integer(roleIdLevel1)));
		}
		ce.addOrder(Order.asc("rolename"));
		roleList = (ArrayList) ce.list();
		if (ce.list() != null) {
			roleList = (ArrayList) ce.list();
		}

		return roleList;

	}

	// This method get Roles mapped to an Employe based on EmployeeId

	public ArrayList getRolesOfEmployee(String empId, Session hibernateSession)
			throws Exception {

		ArrayList roleList = new ArrayList();
		CommonBaseDao commonBaseDao = BaseDaoFactory
				.getInstance(DaoConstants.BASE_DAO_CLASS);
		TmEmployee tmEmployee = new TmEmployee();

		if (empId != null && !empId.equalsIgnoreCase("")) {
			tmEmployee = (TmEmployee) commonBaseDao.findById(Long
					.parseLong(empId), HibernateBeansConstants.HBM_EMPLOYEE,
					hibernateSession);
		}
		Criteria ce = hibernateSession.createCriteria(TmRoleempmapping.class);
		ce = ce.add(Restrictions.eq("tmEmployee", tmEmployee));

		if (ce.list() != null) {
			roleList = (ArrayList) ce.list();
		}
		return roleList;

	}

	// This method get Roles mapped to an Employee based on its EmailId.

	public HashMap getEmployeeRolesBasedOnEmailID(String ssfUserId,
			Session hibernateSession) throws Exception {

		ArrayList employeeList = new ArrayList();
		ArrayList roleList = new ArrayList();
		TmEmployee tmEmployee = new TmEmployee();
		HashMap userHashMap = new HashMap();

		if (ssfUserId != null && !ssfUserId.equalsIgnoreCase("")) {
			Criteria ce = hibernateSession.createCriteria(TmEmployee.class);
			//COMMENTED FOR TESTING
			ce = ce.add(Restrictions.eq("ssfid", ssfUserId).ignoreCase());
			//ce = ce.add(Restrictions.eq("email", ssfUserId).ignoreCase());
			
			if (ce.list() != null) {
				employeeList = (ArrayList) ce.list();
			}
		}

		if (employeeList != null && employeeList.size() > 0) {
			tmEmployee = (TmEmployee) employeeList.get(0);
		}

		Criteria ce = hibernateSession.createCriteria(TmRoleempmapping.class);
		ce = ce.add(Restrictions.eq("tmEmployee", tmEmployee));

		if (ce.list() != null) {
			roleList = (ArrayList) ce.list();
			userHashMap.put(tmEmployee, roleList);
		}
		return userHashMap;

	}

	// This method get Roles mapped to an Employee based on its RoleID.

	public ArrayList getEmployeeRolesBasedOnRoleID(String roleID,
			Session hibernateSession) throws Exception {

		ArrayList employeeRoleList = new ArrayList();

		if (roleID != null && !roleID.equalsIgnoreCase(AppConstants.INI_VALUE)) {
			Criteria ce = hibernateSession
					.createCriteria(TmRoleempmapping.class);
			ce.createAlias("tmEmployee", "tmEmployee");
			ce = ce.add(Restrictions.eq("tmRoles.roleid", new Integer(roleID)));
			ce.add(Restrictions.eq("tmEmployee.isactive", 1));
			if (ce.list() != null) {
				employeeRoleList = (ArrayList) ce.list();
			}
		}

		return employeeRoleList;

	}

	// This method saves Entries of an Employee for escalation Level 1 and Level
	// 2
	// from in Database.

	public boolean saveRoleAndEscalationLevels(UserNpdSpocs userNpdSpocs,
			TmEmployee loginUser, String selectedUsers) throws Exception {

		//Session hibernateSession = null;
		//hibernateSession = CommonBaseDao.beginTrans();
		CommonBaseDao commonBaseDao = BaseDaoFactory
				.getInstance(DaoConstants.BASE_DAO_CLASS);
		TmEmployee tmEmployee = new TmEmployee();
		TtrnProjecthierarchy projecthierarchy = new TtrnProjecthierarchy();
		TmEmployeeRoleHistory employeeRoleHistory = new TmEmployeeRoleHistory();
		TmRoles tmRoles = new TmRoles();
		int roleId = 0;
		ArrayList roleList = new ArrayList();
		ArrayList projectHeriarchyList = null;
		boolean insert = true;
		Query query = null;
		String currentRoles = "";
		String replacedUsers[] = null;
		String replacedRoles[] = null;
		Statement stmt=null;
		ResultSet rs=null;
		ArrayList<ArrayList<TtrnProjecthierarchy>> replacedToDoListTasks=new ArrayList<ArrayList<TtrnProjecthierarchy>>();
		ArrayList<ArrayList<TtrnProject>> replacedPendingRFITasks=new ArrayList<ArrayList<TtrnProject>>();
		Connection conn=null;

		try {
			conn=NpdConnection.getConnectionObject();
			conn.setAutoCommit(false);
			if (userNpdSpocs != null) {
				if (userNpdSpocs.getEmployeeId() != null
						&& !userNpdSpocs.getEmployeeId().equalsIgnoreCase("")) {

					if (selectedUsers != null
							&& !selectedUsers
									.equalsIgnoreCase(AppConstants.INI_VALUE)
							&& userNpdSpocs.getUserReplacedRoleIds() != null
							&& !userNpdSpocs.getUserReplacedRoleIds()
									.equalsIgnoreCase(AppConstants.INI_VALUE))

					{
						String sql="SELECT DET.PROJECTWORKFLOWID FROM NPD.TTRN_PROJECT PRD INNER JOIN "+
									"NPD.TTRN_PROJECTWORKFLOWDET DET ON PRD.PROJECTID=DET.PROJECTID " +
									"WHERE PRD.PROJECTSTATUS=1 AND DET.ISACTIVE=1";
						stmt=conn.createStatement();
						rs=stmt.executeQuery(sql);
						HashSet<Long> projectWorkflowIdsForActiveProjects=new HashSet<Long>(); 
						while(rs.next())
						{
							projectWorkflowIdsForActiveProjects.add(rs.getLong("PROJECTWORKFLOWID"));
						}
						stmt.close();
						replacedUsers = selectedUsers.split(",");
						replacedRoles = userNpdSpocs.getUserReplacedRoleIds()
								.split(",");
						if (replacedUsers != null && replacedRoles != null
								&& replacedUsers.length == replacedRoles.length) {
							for (int j = 0; j < replacedRoles.length; j++) {
								ArrayList<TtrnProjecthierarchy> replacedToDoListTasks_forUser=new ArrayList<TtrnProjecthierarchy>();
								//replacing for not closed tasks
								
								CallableStatement cstmt=conn.prepareCall("{ call NPD.FETCH_REPLACE_TASK_ASSIGNEE(?,?) }");
								
								cstmt.setLong(1, Long.parseLong(replacedRoles[j]));
								//cstmt.setLong(2, Long.parseLong(userNpdSpocs.getEmployeeId()));
								cstmt.setLong(2, new Long(userNpdSpocs.getNpdempid()));
								
								ResultSet temp_rs=cstmt.executeQuery();
								while(temp_rs.next())
								{
									TtrnProjecthierarchy temp=new TtrnProjecthierarchy();
									temp.setCurrenttaskid(temp_rs.getLong("CURRENTTASKID"));
									temp.setProjectworkflowid(temp_rs.getLong("PROJECTWORKFLOWID"));
									replacedToDoListTasks_forUser.add(temp);
									
								}
								stmt.close();
																
								replacedToDoListTasks.add(replacedToDoListTasks_forUser);
								
//								replacing for pending rfi
								String pendinRfiSql="select * from NPD.V_ALL_RFI_DETAIL_LIST " +
										"WHERE ACTIONRFIRAISEDTO="+userNpdSpocs.getNpdempid()+
												" AND ACTIONRFIRAISEDTO_ROLE="+replacedRoles[j];
								Statement rfiStmt=conn.createStatement();
								ResultSet rfiRs=rfiStmt.executeQuery(pendinRfiSql);
								//ArrayList<TtrnProject> pendingRfi=new ArrayList<TtrnProject>();
								TtrnProject project=null;
								String csvRfiId="-10";
								ArrayList<TtrnProject> replacedPendingRFITasks_forUser=new ArrayList<TtrnProject>();
								while(rfiRs.next())
								{
									String projectWorkflowId=rfiRs.getString("PROJECTWORKFLOWID");
									if(rfiRs.getString("RFIRESPONSE")==null)
									{
									if(projectWorkflowIdsForActiveProjects.contains(Long.parseLong(projectWorkflowId)))
									{
										project=new TtrnProject();
										project.setRfiId(rfiRs.getString("PROJECTRFIID"));
										//project.setProjectid(rfiRs.getSt)
										
										
										
										//pendingRfi.add(project);
										replacedPendingRFITasks_forUser.add(project);
										csvRfiId=csvRfiId+","+project.getRfiId();
									}
									}
								}
								replacedPendingRFITasks.add(replacedPendingRFITasks_forUser);
								rfiStmt.close();
								
								String sqlUpdate="{call  NPD.REPLACE_TODO_RFI_PLR_TASK(?,?,?)}";
								
								cstmt=conn.prepareCall(sqlUpdate);
								cstmt.setLong(1, Long.parseLong(replacedRoles[j]));
								//cstmt.setLong(2, Long.parseLong(userNpdSpocs.getEmployeeId()));
								cstmt.setLong(2, new Long(userNpdSpocs.getNpdempid()));
								cstmt.setLong(3, Long.parseLong(replacedUsers[j]));
								cstmt.execute();
								cstmt.close();
								
								
								//replace plr tasks
								
							}

						}
					}
					
					
					userNpdSpocs.setReplacedToDoListTasks(replacedToDoListTasks);
					userNpdSpocs.setReplacedPendingRFITasks(replacedPendingRFITasks);

					String sql="{call NPD.NPD_SPOC_ROLE_CHANGE(?,?,?,?,?,?,?,?,?)}";
					
					CallableStatement cstmt=conn.prepareCall(sql);
					cstmt.setString(1,userNpdSpocs.getTypeOfEmployee() );
					
					if(AppConstants.Type_of_employee_new.equals(userNpdSpocs.getTypeOfEmployee()))
					{
						cstmt.setString(2, userNpdSpocs.getEmployeeId());
						cstmt.setNull(3, java.sql.Types.BIGINT);
					}
					else if(AppConstants.Type_of_employee_old.equals(userNpdSpocs.getTypeOfEmployee()))
					{
						cstmt.setString(2, "");
						cstmt.setLong(3, userNpdSpocs.getNpdempid());
					}
					
					if(!"1".equals(userNpdSpocs.getEscalation()))
					{
						cstmt.setInt(4, Integer.parseInt(userNpdSpocs.getLevel1Id()));
						cstmt.setInt(5, Integer.parseInt(userNpdSpocs.getLevel2Id()));
						
						cstmt.setLong(6, Long.parseLong(userNpdSpocs.getLevel1EmployeeId()));
						cstmt.setLong(7, Long.parseLong(userNpdSpocs.getLevel2EmployeeId()));
						
					}
					else
					{
						cstmt.setNull(4, java.sql.Types.INTEGER);
						cstmt.setNull(5, java.sql.Types.INTEGER);
						
						cstmt.setNull(6, java.sql.Types.BIGINT);
						cstmt.setNull(7, java.sql.Types.BIGINT);
					}
					
					
					cstmt.setLong(8, new Long(loginUser.getNpdempid()));
					
					String csvCurrRole="";
					if(userNpdSpocs.getSelectedRoleId()!=null)
					{
						for (int n = 0; n < userNpdSpocs.getSelectedRoleId().length; n++) {
							csvCurrRole = csvCurrRole+","+userNpdSpocs.getSelectedRoleId()[n];
						}
					}
					if(csvCurrRole.length()>0)
					{
						csvCurrRole=csvCurrRole.substring(1);
					}
					
					cstmt.setString(9, csvCurrRole);
					cstmt.execute();
					
					/*//fetching employee
					tmEmployee = (TmEmployee) commonBaseDao.findById(Long
							.parseLong(userNpdSpocs.getEmployeeId()),
							HibernateBeansConstants.HBM_EMPLOYEE,
							hibernateSession);
					
					//making history
					if ((replacedUsers != null && replacedUsers.length > 0)
							|| tmEmployee.getLevel1().getRoleid() != Integer
									.parseInt(userNpdSpocs.getLevel1Id())
							|| tmEmployee.getLevel2().getRoleid() != Integer
									.parseInt(userNpdSpocs.getLevel2Id())) {
						employeeRoleHistory.setOldroles(userNpdSpocs
								.getPreviousRoleId());
						for (int n = 0; n < userNpdSpocs.getRoleId().length; n++) {
							currentRoles += userNpdSpocs.getRoleId()[n] + ",";
						}

						employeeRoleHistory.setNewroles(currentRoles.substring(
								0, currentRoles.length() - 1));
						if (tmEmployee.getLevel1() != null)
							employeeRoleHistory.setOldlevel1(new Integer(
									tmEmployee.getLevel1().getRoleid()));
						if (tmEmployee.getLevel2() != null)
							employeeRoleHistory.setOldlevel2(new Integer(
									tmEmployee.getLevel2().getRoleid()));
						
						if (tmEmployee.getLevel1employeeid() != 0)
							employeeRoleHistory.setOldlevel1employee(new Long(
									tmEmployee.getLevel1employeeid()));
						if (tmEmployee.getLevel2employeeid() != 0)
							employeeRoleHistory.setOldlevel2employee(new Long(
									tmEmployee.getLevel2employeeid()));
						
						
						if (userNpdSpocs.getLevel1Id() != null
								&& !userNpdSpocs.getLevel1Id()
										.equalsIgnoreCase("")
								&& userNpdSpocs.getLevel2Id() != null
								&& !userNpdSpocs.getLevel1Id()
										.equalsIgnoreCase("")) {
							employeeRoleHistory.setNewlevel1(new Integer(
									userNpdSpocs.getLevel1Id()));
							employeeRoleHistory.setNewlevel2(new Integer(
									userNpdSpocs.getLevel2Id()));
							employeeRoleHistory.setNewlevel1employee(new Long(
									userNpdSpocs.getLevel1EmployeeId()));
							employeeRoleHistory.setNewlevel2employee(new Long(
									userNpdSpocs.getLevel2EmployeeId()));
							

						}
						employeeRoleHistory.setEmployeeid(tmEmployee);
						employeeRoleHistory.setCreateddate(new Date());
						commonBaseDao.attachDirty(employeeRoleHistory,
								hibernateSession);
					}*/

				}
				/*if (userNpdSpocs.getRoleId() != null) {

					//getting roles
					for (int x = 0; x < userNpdSpocs.getRoleId().length; x++) {
						roleId = Integer.parseInt(userNpdSpocs.getRoleId()[x]);
						tmRoles = (TmRoles) commonBaseDao.findById(roleId,
								HibernateBeansConstants.HBM_ROLE,
								hibernateSession);
						roleList.add(tmRoles);

					}
					//deleting previous mapping
					if (tmEmployee != null) {
						query = hibernateSession
								.createQuery("delete from TmRoleempmapping where tmEmployee.npdempid = :employeeId");
						query.setInteger("employeeId", (int) tmEmployee
								.getNpdempid());
						query.executeUpdate();
					}
					//storing new mapping
					for (int y = 0; y < roleList.size(); y++) {
						tmRoles = (TmRoles) roleList.get(y);
						if (tmRoles != null && tmEmployee != null) {
							TmRoleempmapping tmRoleempmapping = new TmRoleempmapping();
							tmRoleempmapping.setTmEmployee(tmEmployee);
							tmRoleempmapping.setTmRoles(tmRoles);
							if (loginUser != null)
								tmRoleempmapping.setCreatedby(loginUser
										.getNpdempid());
							tmRoleempmapping.setCreateddate(new Date());
							tmRoleempmapping
									.setIsactive(AppConstants.ACTIVE_FLAG);

							commonBaseDao.attachDirty(tmRoleempmapping,
									hibernateSession);

						}
					}

				}
				//updating l1 ,l2,...,modified by ,modified date
				if (userNpdSpocs.getLevel1Id() != null
						&& !userNpdSpocs.getLevel1Id().equalsIgnoreCase("")
						&& userNpdSpocs.getLevel2Id() != null
						&& !userNpdSpocs.getLevel1Id().equalsIgnoreCase("")) {

					tmRoles = (TmRoles) commonBaseDao.findById(Integer
							.parseInt(userNpdSpocs.getLevel1Id()),
							HibernateBeansConstants.HBM_ROLE, hibernateSession);
					tmEmployee.setLevel1(tmRoles);
					tmRoles = (TmRoles) commonBaseDao.findById(Integer
							.parseInt(userNpdSpocs.getLevel2Id()),
							HibernateBeansConstants.HBM_ROLE, hibernateSession);
					tmEmployee.setLevel2(tmRoles);
					
					
					
					tmEmployee.setLevel1employeeid(Long.parseLong(userNpdSpocs.getLevel1EmployeeId()));
					tmEmployee.setLevel2employeeid(Long.parseLong(userNpdSpocs.getLevel2EmployeeId()));
					
					tmEmployee.setModifieddate(new Date());
					if (loginUser != null)
						tmEmployee.setModifiedby(new Long(loginUser
								.getNpdempid()));
					commonBaseDao.attachDirty(tmEmployee, hibernateSession);*/

				}

			

		} catch (Exception e) {
			insert = false;
			conn.rollback();
			e.printStackTrace();
		} finally {
			try{
				DbConnection.closeResultset(rs);
				DbConnection.closeStatement(stmt);
				DbConnection.freeConnection(conn);
			
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			
		}

		return insert;

	}

	// This method get All the Role List for escalation Level 1 and Level 2
	// from in Database.

	public ArrayList getUsersForOptionalList(String[] mandatoryIds,String productId)
			throws Exception {

		
		String str[]={productId};
		ArrayList<TmEmployee> list = getUsersForMandatoryList(str);
		
		if(mandatoryIds==null || mandatoryIds.length==0)
		{
			return list;
		}
		
		HashMap map=new HashMap();
		for (String temp : mandatoryIds) {
			map.put(temp, temp);
		}
		
		ArrayList<TmEmployee> opList = new ArrayList<TmEmployee>();
		for (TmEmployee employee : list) {
			if(map.get(String.valueOf(employee.getNpdempid()))==null)
			{
				opList.add(employee);
			}
		}
		return opList;
		

	}
//
	
	public ArrayList<TmEmployee> getUsersForMandatoryList(String[] productIds)throws NpdException {
		
		ArrayList<TmEmployee> list = new ArrayList<TmEmployee>();
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		try{
			
			conn=NpdConnection.getConnectionObject();
			
			String sql="SELECT NPDEMPID, EMPNAME, PROJECTID FROM NPD.USERSOFPROJECT " +
					"	WHERE PROJECTID="+productIds[0]+" ORDER BY EMPNAME"; 
			
			
			
			
			
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			while(rs.next())
			{
				TmEmployee dto=new TmEmployee();
				dto.setEmpname(rs.getString("EMPNAME"));
				dto.setNpdempid(rs.getLong("NPDEMPID"));
				
				list.add(dto);
			}
			  
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in getUsersForMandatoryList method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in getUsersForMandatoryList method of "
					+ this.getClass().getSimpleName()) ;
		}
		finally
		{
			try {
				DbConnection.closeResultset(rs);
				DbConnection.closeStatement(stmt);
				NpdConnection.freeConnection(conn);
			} catch (Exception ex) {
				ex.printStackTrace();
				AppConstants.NPDLOGGER.error(ex.getMessage()
						+ " Exception occured in getUsersForMandatoryList method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
				throw new NpdException("Exception occured in getUsersForMandatoryList method of "
						+ this.getClass().getSimpleName()) ;
			}
		}
		return list;
	}
	// This method get role history of an employee

	public ArrayList viewNpdSpocsRoleHistory(UserNpdSpocs npdSpocs,
			Session hibernateSession) throws Exception {

		ArrayList roleHistoryList = new ArrayList();
		ArrayList employeeRoleMappingList = new ArrayList();
		ArrayList employeeList = new ArrayList();
		TmEmployee tmEmployee = new TmEmployee();
		TmEmployeeRoleHistory employeeRoleHistory = new TmEmployeeRoleHistory();
		TmRoles tmRoles = new TmRoles();
		UserNpdSpocs spocs = new UserNpdSpocs();
		String role[];
		String oldRoleNames = "";
		String newRoleNames = "";

		CommonBaseDao commonBaseDao = BaseDaoFactory
				.getInstance(DaoConstants.BASE_DAO_CLASS);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar = Calendar.getInstance();
		if (npdSpocs.getFromDate()!=null && !"".equalsIgnoreCase(npdSpocs.getFromDate())) {

			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.setTime(sdf.parse(npdSpocs.getFromDate()));
		}
		if (npdSpocs.getToDate()!=null && !"".equalsIgnoreCase(npdSpocs.getToDate())) {

			calendar1.set(Calendar.HOUR_OF_DAY, 23);
			calendar1.set(Calendar.MINUTE, 59);
			calendar1.set(Calendar.SECOND, 59);
			calendar1.setTime(sdf.parse(npdSpocs.getToDate()));
			calendar1.add(Calendar.DATE, 1);
		}
		PagingSorting pagingSorting = npdSpocs.getPagingSorting();
		if (pagingSorting == null) {
			pagingSorting = new PagingSorting();
			npdSpocs.setPagingSorting(pagingSorting);
		}
		pagingSorting.setPagingSorting(true, true);

		pagingSorting.setDefaultifNotPresent("employeeName",
				PagingSorting.increment, 1);

		pagingSorting.setMode("hibernate");

		Criteria ce = hibernateSession.createCriteria(TmEmployee.class);
		ce.add(Restrictions.eq("empname", npdSpocs.getEmployeeName()).ignoreCase());
		employeeList = (ArrayList) ce.list();

		if (employeeList != null && employeeList.size() > 0) {
			for (int y = 0; y < employeeList.size(); y++) {
				tmEmployee = (TmEmployee) employeeList.get(y);
				Criteria criteria = hibernateSession
						.createCriteria(TmEmployeeRoleHistory.class);
				criteria.add(Restrictions.eq("employeeid", tmEmployee));
				if (npdSpocs.getToDate() != null
						&& npdSpocs.getFromDate() != null
						&& !(npdSpocs.getToDate().equalsIgnoreCase("") && npdSpocs
								.getFromDate().equalsIgnoreCase(""))) {

					criteria.add(Restrictions.between("createddate", new Date(
							calendar.getTime().getTime()), new Date(calendar1
							.getTime().getTime())));

				}

				employeeRoleMappingList = (ArrayList) criteria.list();
			
				if (employeeRoleMappingList != null
						&& employeeRoleMappingList.size() > 0) {
					pagingSorting
							.setRecordCount(employeeRoleMappingList.size());
				}
				criteria.setFirstResult(pagingSorting.getStartRecordId());
				criteria.setMaxResults(pagingSorting.getPageRecords());
				employeeRoleMappingList = new ArrayList();
				employeeRoleMappingList = (ArrayList) criteria.list();

				if (employeeRoleMappingList != null
						&& employeeRoleMappingList.size() > 0) {
					for (int i = 0; i < employeeRoleMappingList.size(); i++) {

						employeeRoleHistory = (TmEmployeeRoleHistory) employeeRoleMappingList
								.get(i);
						spocs = new UserNpdSpocs();
						spocs.setEmployeeName(tmEmployee.getEmpname());
						spocs.setEmployeeId(new Long(tmEmployee.getNpdempid())
								.toString());
						if (employeeRoleHistory.getCreateddate() != null)
							spocs.setCreatedDate(sdf.format(employeeRoleHistory
									.getCreateddate()));
						tmRoles=null;
						if(employeeRoleHistory.getOldlevel1()!=null)
						{
						tmRoles = (TmRoles) (commonBaseDao.findById(
								employeeRoleHistory.getOldlevel1().intValue(),
								HibernateBeansConstants.HBM_ROLE,
								hibernateSession));
						}
						TmEmployee tmEmployee2=null;
						
						if(employeeRoleHistory.getOldlevel1employee()!=null)
						{
							tmEmployee2=(TmEmployee)(commonBaseDao.findById(
										employeeRoleHistory.getOldlevel1employee().longValue(),
										HibernateBeansConstants.HBM_EMPLOYEE,
										hibernateSession));
						}
						String str="";
						if(tmEmployee2!=null)
						{
							str="("+tmEmployee2.getEmpname()+")";
						}
						if(tmRoles!=null)
						{
							spocs.setOldLevel1(tmRoles.getRolename()+str);
						}
						else
						{
							spocs.setOldLevel1("");
						}
						
						tmRoles = null;
						if(employeeRoleHistory.getOldlevel2()!=null)
						{
						tmRoles = (TmRoles) (commonBaseDao.findById(
								employeeRoleHistory.getOldlevel2().intValue(),
								HibernateBeansConstants.HBM_ROLE,
								hibernateSession));
						}
						tmEmployee2=null;
						if(employeeRoleHistory.getOldlevel2employee()!=null)
						{
							tmEmployee2=(TmEmployee)(commonBaseDao.findById(
									employeeRoleHistory.getOldlevel2employee().longValue(),
									HibernateBeansConstants.HBM_EMPLOYEE,
									hibernateSession));
						}
						str="";
						if(tmEmployee2!=null)
						{
							str="("+tmEmployee2.getEmpname()+")";
						}
						if(tmRoles!=null)
						{
							spocs.setOldLevel2(tmRoles.getRolename()+str);
						}
						else
						{
							spocs.setOldLevel2("");
						}
						tmRoles = null;
						if(employeeRoleHistory.getNewlevel1()!=null)
						{
						tmRoles = (TmRoles) (commonBaseDao.findById(
								employeeRoleHistory.getNewlevel1().intValue(),
								HibernateBeansConstants.HBM_ROLE,
								hibernateSession));
						}
						tmEmployee2=null;
						if(employeeRoleHistory.getNewlevel1employee()!=null)
						{
							tmEmployee2=(TmEmployee)(commonBaseDao.findById(
									employeeRoleHistory.getNewlevel1employee().longValue(),
									HibernateBeansConstants.HBM_EMPLOYEE,
									hibernateSession));
						}
						str="";
						if(tmEmployee2!=null)
						{
							str="("+tmEmployee2.getEmpname()+")";
						}
						if(tmRoles!=null)
						{
							spocs.setNewLevel1(tmRoles.getRolename()+str);
						}
						else
						{
							spocs.setNewLevel1("");
						}
						
						tmRoles = null;
						if(employeeRoleHistory.getNewlevel2()!=null)
						{
						tmRoles = (TmRoles) (commonBaseDao.findById(
								employeeRoleHistory.getNewlevel2().intValue(),
								HibernateBeansConstants.HBM_ROLE,
								hibernateSession));
						}
						tmEmployee2=null;
						if(employeeRoleHistory.getNewlevel2employee()!=null)
						{
							tmEmployee2=(TmEmployee)(commonBaseDao.findById(
									employeeRoleHistory.getNewlevel2employee().longValue(),
									HibernateBeansConstants.HBM_EMPLOYEE,
									hibernateSession));
						}
						str="";
						if(tmEmployee2!=null)
						{
							str="("+tmEmployee2.getEmpname()+")";
						}
						if(tmRoles!=null)
						{
							spocs.setNewLevel2(tmRoles.getRolename()+str);
						}
						else
						{
							spocs.setNewLevel2("");
						}
						
						role = null;
						role = employeeRoleHistory.getOldroles().split(",");
						oldRoleNames = "";
						for (int h = 0; h < role.length; h++) {
							if (role[h] != null
									&& !role[h]
											.equalsIgnoreCase(AppConstants.INI_VALUE)) {
								tmRoles = (TmRoles) (commonBaseDao.findById(
										Integer.parseInt(role[h]),
										HibernateBeansConstants.HBM_ROLE,
										hibernateSession));

								oldRoleNames += tmRoles.getRolename() + ",";
							}

						}
						if (!oldRoleNames
								.equalsIgnoreCase(AppConstants.INI_VALUE))
							spocs.setOldRoles(oldRoleNames.substring(0,
									oldRoleNames.length() - 1));
						role = null;
						role = employeeRoleHistory.getNewroles().split(",");
						newRoleNames = "";
						for (int h = 0; h < role.length; h++) {
							if (role[h] != null
									&& !role[h]
											.equalsIgnoreCase(AppConstants.INI_VALUE)) {
								tmRoles = (TmRoles) (commonBaseDao.findById(
										Integer.parseInt(role[h]),
										HibernateBeansConstants.HBM_ROLE,
										hibernateSession));

								newRoleNames += tmRoles.getRolename() + ",";
							}

						}
						if(newRoleNames.length()>0)
						{
							newRoleNames=newRoleNames.substring(0,
								newRoleNames.length() - 1);
						}
						spocs.setNewRoles(newRoleNames);

						roleHistoryList.add(spocs);
					}
				}

			}
		}

		return roleHistoryList;

	}
	public ArrayList<TmEmployee> fetchEmployeesOfRoleNpdSpoc(Connection conn, TmEmployee searchDto,long minusNpdEmpiId) throws NpdException {
		ArrayList<TmEmployee> empList=new ArrayList<TmEmployee>();
		CallableStatement cstmt=null;
		ResultSet rs=null;
		try
		{
		
			cstmt = conn.prepareCall(
									"{call NPD.NPD_ROLE_REPORT(?)}");
			cstmt.setLong(1, searchDto.getCurrentRoleId());
			
			rs=cstmt.executeQuery();
			TmEmployee emp=null;
			while(rs.next())
			{
				emp=new TmEmployee();
				emp.setNpdempid(rs.getLong("NPDEMPID"));
				emp.setEmpname(rs.getString("EMPNAME"));
				emp.setEmail(rs.getString("EMAIL"));
				emp.setIsactive(rs.getInt("ISACTIVE"));
				
				if(emp.getNpdempid()==minusNpdEmpiId || emp.getIsactive()==0)
				{
					continue;
				}
				empList.add(emp);
			}
			
			
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
			try
			{
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(cstmt);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		
		return empList;
	}
	
	public ArrayList<TmEmployee> fetchEmployeesOfRoleNpdSpoc(Session hibernateSession, TmEmployee searchDto) throws NpdException {
		ArrayList<TmEmployee> empList=new ArrayList<TmEmployee>();
		Connection conn=null;
		try
		{
			conn=hibernateSession.connection();
			CallableStatement cstmt = conn.prepareCall(
									"{call NPD.NPD_ROLE_REPORT(?)}");
			cstmt.setLong(1, searchDto.getCurrentRoleId());
			
			ResultSet rs=cstmt.executeQuery();
			TmEmployee emp=null;
			while(rs.next())
			{
				emp=new TmEmployee();
				emp.setNpdempid(rs.getLong("NPDEMPID"));
				emp.setEmpname(rs.getString("EMPNAME"));
				emp.setEmail(rs.getString("EMAIL"));
				
				empList.add(emp);
			}
			
			
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
				conn.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return empList;
	}



	public ArrayList<TmEmployee> fetchEmployeesOfRole(Connection conn, TmEmployee searchDto) throws NpdException {
		ArrayList<TmEmployee> empList=new ArrayList<TmEmployee>();
		
		CallableStatement cstmt=null;
		ResultSet rs=null;
		try
		{
		 cstmt= conn.prepareCall(
									"{call NPD.NPD_ROLE_REPORT(?)}");
			cstmt.setLong(1, searchDto.getCurrentRoleId());
			
		rs=cstmt.executeQuery();
			TmEmployee emp=null;
			while(rs.next())
			{
				emp=new TmEmployee();
				emp.setEmpid(rs.getLong("NPDEMPID"));
				emp.setEmpname(rs.getString("EMPNAME"));
				emp.setEmail(rs.getString("EMAIL"));
				
				empList.add(emp);
			}
			
			
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
			try
			{
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(cstmt);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return empList;
	}
	
	public ArrayList<TmEmployee> fetchAllEmployees(Connection conn) throws NpdException
	{
		ResultSet rs=null;
		ArrayList<TmEmployee> employeeList=new ArrayList<TmEmployee>(); 
		try{
			
			String sql="SELECT EMPLOYEE_NUMBER, EMPLOYEE_TYPE, EMAIL_ADDRESS, FIRST_NAME, MIDDLE_NAMES, " +
					"LAST_NAME, FIRST_NAME,APPENDED_FULL_NAME,FULL_NAME, BUSINESS_GROUP_ID, ORGANIZATION_NAME, BUSINESS, ZON, DATE_OF_JOINING, " +
					"SUPERVISOR_ID, SUPER_NAME, SUPER_MAIL, DATE_OF_BIRTH, SEX, CITY, STATE, POSTAL_CODE, " +
					"OFFICE_LOCATION_ADDRESS, MOBILE, NOTIFIED_TERMINATION_DATE, ACTUAL_TERMINATION_DATE, " +
					"LOCATION_CODE, INTERNAL_LOCATION, POSITION_ID, FUNCTION, SUB_FUNCTION, SUB_SUB_FUNCTION, " +
					"MARKET_FACING_TITLE, GRADE_ID, BAND, CREATED, LAST_UPDATED, ISACTIVE " +
					"FROM NPD.V_HRMS_NPD_SPOC ORDER BY APPENDED_FULL_NAME";
			
			rs=conn.createStatement().executeQuery(sql);
			TmEmployee employee=null;
			while(rs.next())
			{
				employee=new TmEmployee();
			
				employee.setHrms_employee_number(rs.getString("EMPLOYEE_NUMBER"));
				employee.setHrms_full_name(rs.getString("APPENDED_FULL_NAME"));
				employee.setHrms_email(rs.getString("EMAIL_ADDRESS"));
				
				employeeList.add(employee);
			}
			
			
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			String msg=ex.getMessage()
			+ " Exception occured in fetchAllEmployees method of "
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

	public ArrayList<TmRoles> fetchAllRoles(Connection conn) throws NpdException
	{
		ResultSet rs=null;
		ArrayList<TmRoles> roleList=new ArrayList<TmRoles>(); 
		try{
			
			String sql="SELECT ROLEID, ROLENAME, ISACTIVE, CREATEDBY, CREATEDDATE, MODIFIEDBY, MODIFIEDDATE " +
					"FROM NPD.TM_ROLES ORDER BY ROLENAME ";
			
			rs=conn.createStatement().executeQuery(sql);
			TmRoles role=null;
			while(rs.next())
			{
				role=new TmRoles();
			
				role.setRoleid(rs.getInt("ROLEID"));
				role.setRolename(rs.getString("ROLENAME"));
				
				roleList.add(role);
			}
			
			
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			String msg=ex.getMessage()
			+ " Exception occured in fetchAllRoles method of "
			+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex);
			AppConstants.NPDLOGGER.error(msg);
			throw new NpdException(msg) ;
		}
		finally
		{
			try
			{
			DbConnection.closeResultset(rs);
			}catch(Exception ex)
			{
				
			}
		}
		
		return roleList;
	}
	
	public TmEmployee fetchEmpByOracleIdInTmEmployee(Connection conn, String hrmsid) throws NpdException
	{
		TmEmployee employee=null; 
		ResultSet rs=null;
		try{
			
			String sql="SELECT NPDEMPID, EMPID, EMPNAME, EMPSOURCE, CREATEDBY, CREATEDDATE, " +
					"MODIFIEDBY, MODIFIEDDATE, ISACTIVE, LEVEL1, LEVEL2, EMAIL, LEVEL1_NPD_EMP_ID," +
					" LEVEL2_NPD_EMP_ID, MOBILE_NO, PASSWORD,  RETRYCOUNT, DISABLEDFLAG " +
					"FROM NPD.TM_EMPLOYEE WHERE EMPID="+hrmsid;
			
			rs=conn.createStatement().executeQuery(sql);
			
			while(rs.next())
			{
				employee=new TmEmployee();
			
				employee.setNpdempid(rs.getLong("NPDEMPID"));
				employee.setLevel1id(rs.getString("LEVEL1"));
				employee.setLevel2id(rs.getString("LEVEL2"));
				
				employee.setLevel1employeeid(rs.getLong("LEVEL1_NPD_EMP_ID"));
				employee.setLevel2employeeid(rs.getLong("LEVEL2_NPD_EMP_ID"));
				
			}
			
			
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			String msg=ex.getMessage()
			+ " Exception occured in fetchEmpByOracleIdInTmEmployee method of "
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
		
		return employee;
	}

	public ArrayList<TmRoles> fetchRolesOfEmployee(Connection conn, TmEmployee employee) throws NpdException{
		ArrayList<TmRoles> roleList=new ArrayList<TmRoles>(); 
		try{
			
			String sql="{call NPD.FETCH_ROLE_OF_EMPLOYEE(?)}";
			CallableStatement cstmt=conn.prepareCall(sql);
			cstmt.setLong(1, employee.getNpdempid());
			
			ResultSet rs=cstmt.executeQuery();
			TmRoles role=null;
			while(rs.next())
			{
				role=new TmRoles();
			
				role.setRoleid(rs.getInt("ROLEID"));
				role.setRolename(rs.getString("ROLENAME"));
				
				roleList.add(role);
			}
			
			
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			String msg=ex.getMessage()
			+ " Exception occured in fetchRolesOfEmployee method of "
			+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex);
			AppConstants.NPDLOGGER.error(msg);
			throw new NpdException(msg) ;
		}
		
		return roleList;
	}

}
