package com.ibm.ioes.npd.utilities;

import java.net.URLEncoder;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import com.ibm.appsecure.util.Encryption;
import com.ibm.appsecure.util.IEncryption;
import com.ibm.ioes.npd.exception.NpdException;
import com.ibm.ioes.npd.hibernate.beans.IoesProductDto;
import com.ibm.ioes.npd.hibernate.beans.SessionObjectsDto;
import com.ibm.ioes.npd.hibernate.beans.TmEmployee;
import com.ibm.ioes.npd.hibernate.beans.TmRoles;
import com.ibm.ioes.npd.hibernate.beans.TmWorkflow;
import com.ibm.ioes.npd.hibernate.beans.TmWorkflowstage;
import com.ibm.ioes.npd.hibernate.beans.TmWorkflowtasks;
import com.ibm.ioes.npd.hibernate.beans.TtrnMeetingschedules;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjecthierarchy;
import com.ibm.ioes.npd.hibernate.dao.AttachEditProjectPlanDao;
import com.ibm.ioes.npd.hibernate.dao.BaseDaoFactory;
import com.ibm.ioes.npd.hibernate.dao.CommonBaseDao;
import com.ibm.ioes.npd.hibernate.dao.DaoConstants;
import com.ibm.ioes.npd.hibernate.dao.HolidayMasterDao;
import com.ibm.ioes.npd.hibernate.dao.IssuesCaptureDao;
import com.ibm.ioes.npd.hibernate.dao.MasterProjectPlanDao;
import com.ibm.ioes.npd.hibernate.dao.MyToDOListDaoImpl;
import com.ibm.ioes.npd.hibernate.dao.NpdUserDao;
import com.ibm.ioes.npd.hibernate.dao.ReferenceDocDao;
import com.ibm.ioes.npd.hibernate.dao.RisksCaptureDao;
import com.ibm.ioes.npd.model.AttachEditProjectPlanModel;
import com.ibm.ioes.npd.model.MasterProjectPlanModel;
import com.ibm.ioes.npd.model.MeetingModel;

public class AjaxHelper {
	private static Logger logger = Logger.getLogger(AjaxHelper.class);

	ServletContext context;

	public AjaxHelper(ServletContext context) {
		this.context = context;
	}

	public ArrayList getRolesForEscalation(String[] roleIds, String roleIdLevel1)
			throws Exception {

		ArrayList roleList = new ArrayList();

		NpdUserDao npdUserDao = (NpdUserDao) BaseDaoFactory
				.getInstance(DaoConstants.NPD_USER_DAO);
		Session hibernateSession = null;
		hibernateSession = CommonBaseDao.beginTrans();
		try {
			roleList = npdUserDao.getRolesForEscalationLevel(roleIds,
					roleIdLevel1, hibernateSession);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CommonBaseDao.closeTrans(hibernateSession);
		}
		return roleList;

	}

	public ArrayList getUsersForOptionalList(String[] mandatoryIds,
			String productId) throws Exception {

		ArrayList optionalList = new ArrayList();

		NpdUserDao npdUserDao = (NpdUserDao) BaseDaoFactory
				.getInstance(DaoConstants.NPD_USER_DAO);

		try {
			optionalList = npdUserDao.getUsersForOptionalList(mandatoryIds,
					productId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return optionalList;

	}

	// getMeetingsOfProduct
	public ArrayList getMeetingsOfProduct(String productId) throws Exception {

		ArrayList meetingList = new ArrayList();

		MeetingModel model = new MeetingModel();

		try {
			meetingList = model.getMeetingsOfProduct(productId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return meetingList;

	}

	//
	public TtrnMeetingschedules getMeetingDetailById(String meetId)
			throws Exception {

		TtrnMeetingschedules ttrnMeetingschedules = null;

		MeetingModel model = new MeetingModel();

		try {
			ttrnMeetingschedules = model.getMeetingDetailById(meetId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ttrnMeetingschedules;

	}

	public ArrayList getUsersForMandatoryList(String[] productIds)
			throws Exception {

		ArrayList mandatoryList = new ArrayList();

		NpdUserDao npdUserDao = (NpdUserDao) BaseDaoFactory
				.getInstance(DaoConstants.NPD_USER_DAO);

		try {
			mandatoryList = npdUserDao.getUsersForMandatoryList(productIds);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mandatoryList;
	}

	public ArrayList getUsersForARoleID(String roleId) throws Exception {

		ArrayList optionalList = new ArrayList();

		NpdUserDao npdUserDao = (NpdUserDao) BaseDaoFactory
				.getInstance(DaoConstants.NPD_USER_DAO);
		Session hibernateSession = null;
		hibernateSession = CommonBaseDao.beginTrans();

		try {
			optionalList = npdUserDao.getEmployeeRolesBasedOnRoleID(roleId,
					hibernateSession);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				CommonBaseDao.closeTrans(hibernateSession);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return optionalList;

	}

	public ArrayList getUsersForARoleID_NpdSpocs(String roleId,
			String minusNpdEmpiId) throws Exception {

		ArrayList optionalList = new ArrayList();

		NpdUserDao npdUserDao = (NpdUserDao) BaseDaoFactory
				.getInstance(DaoConstants.NPD_USER_DAO);

		Connection conn = null;
		try {
			conn = NpdConnection.getConnectionObject();

			TmEmployee searchDto = new TmEmployee();
			searchDto.setCurrentRoleId(Long.parseLong(roleId));
			long val = 0;
			if (minusNpdEmpiId != null && !minusNpdEmpiId.equals("")) {
				val = Long.parseLong(minusNpdEmpiId);
			}
			return (npdUserDao
					.fetchEmployeesOfRoleNpdSpoc(conn, searchDto, val));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			NpdConnection.freeConnection(conn);
		}
		return optionalList;

	}

	public TmWorkflowstage checkDuplicateStage(String stage, String categoryId)
			throws Exception {

		MasterProjectPlanDao masterProjectPlanDao = (MasterProjectPlanDao) BaseDaoFactory
				.getInstance(DaoConstants.MASTER_PROJECT_PLAN_DAO);
		TmWorkflowstage tmWorkflowstage = null;

		try {
			tmWorkflowstage = masterProjectPlanDao.checkDuplicateStage(stage,
					categoryId);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return tmWorkflowstage;

	}

	public TmWorkflowtasks getTaskDetails(String taskid) throws Exception {

		MasterProjectPlanDao masterProjectPlanDao = (MasterProjectPlanDao) BaseDaoFactory
				.getInstance(DaoConstants.MASTER_PROJECT_PLAN_DAO);
		TmWorkflowtasks tmWorkflowtasks = new TmWorkflowtasks();
		CommonBaseDao commonBaseDao = (CommonBaseDao) BaseDaoFactory
				.getInstance(DaoConstants.BASE_DAO_CLASS);
		Session hibernateSession = null;
		hibernateSession = CommonBaseDao.beginTrans();

		try {
			if (taskid != null
					&& !taskid.equalsIgnoreCase(AppConstants.INI_VALUE)) {
				tmWorkflowtasks = (TmWorkflowtasks) commonBaseDao.findById(Long
						.parseLong(taskid),
						HibernateBeansConstants.HBM_WORKFLOW_TASK,
						hibernateSession);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				CommonBaseDao.closeTrans(hibernateSession);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return tmWorkflowtasks;

	}

	public TmWorkflowtasks getDuplicateTask(String task, String stageId)
			throws Exception {

		MasterProjectPlanDao masterProjectPlanDao = (MasterProjectPlanDao) BaseDaoFactory
				.getInstance(DaoConstants.MASTER_PROJECT_PLAN_DAO);
		TmWorkflowtasks tmWorkflowtasks = new TmWorkflowtasks();

		try {
			tmWorkflowtasks = masterProjectPlanDao.checkDuplicateTask(task,
					stageId);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return tmWorkflowtasks;

	}

	public boolean deleteTask(String taskId) throws Exception {

		MasterProjectPlanDao masterProjectPlanDao = (MasterProjectPlanDao) BaseDaoFactory
				.getInstance(DaoConstants.MASTER_PROJECT_PLAN_DAO);
		boolean delete = false;

		try {
			delete = masterProjectPlanDao.deleteTask(taskId);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return delete;

	}

	public String[] getPreviousTasks(String taskId) throws Exception {

		MasterProjectPlanDao masterProjectPlanDao = (MasterProjectPlanDao) BaseDaoFactory
				.getInstance(DaoConstants.MASTER_PROJECT_PLAN_DAO);
		String[] previousTask = null;

		try {
			previousTask = masterProjectPlanDao.getPreviousTasks(taskId);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return previousTask;

	}

	public boolean updateTask(String taskID, String remarks, String desc,
			String stakeholderId, String plannedDays, String taskName,
			boolean first, boolean last, String[] prevTaskId) throws Exception {

		MasterProjectPlanDao masterProjectPlanDao = (MasterProjectPlanDao) BaseDaoFactory
				.getInstance(DaoConstants.MASTER_PROJECT_PLAN_DAO);
		boolean update = false;

		try {
			update = masterProjectPlanDao.updateTask(taskID, remarks, desc,
					stakeholderId, plannedDays, taskName, first, last,
					prevTaskId);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return update;

	}

	public TmWorkflowtasks checkForDuplicateFirstAndLastTask(boolean first,
			boolean last, String stageId) throws Exception {

		MasterProjectPlanDao masterProjectPlanDao = (MasterProjectPlanDao) BaseDaoFactory
				.getInstance(DaoConstants.MASTER_PROJECT_PLAN_DAO);

		TmWorkflowtasks tmWorkflowtasks = new TmWorkflowtasks();

		try {
			tmWorkflowtasks = masterProjectPlanDao
					.checkForDuplicateFirstAndLastTask(first, last, stageId);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return tmWorkflowtasks;

	}

	public ArrayList<TmWorkflowtasks> fetchFirstAndLastTask(String wkId)
			throws Exception {

		MasterProjectPlanDao masterProjectPlanDao = (MasterProjectPlanDao) BaseDaoFactory
				.getInstance(DaoConstants.MASTER_PROJECT_PLAN_DAO);

		ArrayList<TmWorkflowtasks> tasks = null;

		try {
			tasks = masterProjectPlanDao.fetchFirstAndLastTask(wkId);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return tasks;

	}

	public ArrayList getTemplateDocList() throws Exception {

		ArrayList templateDocList = new ArrayList();
		HashMap hashMap = new HashMap();

		ReferenceDocDao referenceDocDao = (ReferenceDocDao) BaseDaoFactory
				.getInstance(DaoConstants.REFERENCE_DOC_DAO);
		Session hibernateSession = null;
		hibernateSession = CommonBaseDao.beginTrans();
		try {
			AttachEditProjectPlanDao dao = new AttachEditProjectPlanDao();
			templateDocList = dao.getTemplateDocList(hibernateSession);
			/*
			 * hashMap = referenceDocDao.getActiveRefDoc(null,hibernateSession);
			 * if(hashMap!=null&&hashMap.size()>0) { Set s = hashMap.keySet();
			 * Object a[] = s.toArray(); for(int i=0;i<a.length;i++) {
			 * templateDocList.add((TmReferencedocs)a[i]); } }
			 */

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CommonBaseDao.closeTrans(hibernateSession);
		}
		return templateDocList;

	}

	public ArrayList getTaskListForACategory(String stageId, String workflowId)
			throws Exception {

		MasterProjectPlanDao masterProjectPlanDao = (MasterProjectPlanDao) BaseDaoFactory
				.getInstance(DaoConstants.MASTER_PROJECT_PLAN_DAO);
		ArrayList previousTaskList = new ArrayList();
		Session hibernateSession = null;
		try {
			hibernateSession = CommonBaseDao.beginTrans();
			previousTaskList = masterProjectPlanDao.getTaskListForACategory(
					stageId, workflowId, hibernateSession);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				CommonBaseDao.closeTrans(hibernateSession);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return previousTaskList;

	}

	public ArrayList getPreviousTaskList(String workflowId, String option,
			String taskId) throws Exception {

		MasterProjectPlanDao masterProjectPlanDao = (MasterProjectPlanDao) BaseDaoFactory
				.getInstance(DaoConstants.MASTER_PROJECT_PLAN_DAO);
		ArrayList previousTaskList = new ArrayList();
		Session hibernateSession = null;
		try {
			hibernateSession = CommonBaseDao.beginTrans();
			if ("ALL".equals(option)) {
				previousTaskList = masterProjectPlanDao
						.getTaskListForACategory(null, workflowId,
								hibernateSession);
			} else if ("VALID_FOR_TASK".equals(option)) {
				MasterProjectPlanModel model = new MasterProjectPlanModel();
				previousTaskList = model
						.fetchValidPrevTasks(workflowId, taskId);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				CommonBaseDao.closeTrans(hibernateSession);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return previousTaskList;

	}

	/*
	 * public boolean updateProject(String projectId, String productBrief,
	 * String airtelPotential, String capexRequirement, String targetMarket,
	 * String totalMktPotential, String launchDate) throws Exception {
	 * 
	 * ProductCreationDao productCreationDao = (ProductCreationDao)
	 * BaseDaoFactory .getInstance(DaoConstants.PRODUCT_CREATION_DAO); boolean
	 * update = false;
	 * 
	 * try {
	 * if(projectId!=null&&!projectId.equalsIgnoreCase(AppConstants.INI_VALUE))
	 * update = productCreationDao.updateProject(new
	 * Long(projectId).longValue(),productBrief,airtelPotential,capexRequirement,targetMarket,totalMktPotential,launchDate);
	 *  } catch (Exception e) { e.printStackTrace(); } return update;
	 *  }
	 * 
	 */public ArrayList getUsersOfRole(String roleId) throws Exception {
		ArrayList employeeList = new ArrayList();
		Session hibernateSession = null;

		try {
			hibernateSession = CommonBaseDao.beginTrans();
			String sql = "SELECT	TM_EMPLOYEE.NPDEMPID,TM_EMPLOYEE.EMPNAME FROM	NPD.TM_ROLEEMPMAPPING TM_ROLEEMPMAPPING "
					+ "INNER JOIN NPD.TM_ROLES TM_ROLES ON TM_ROLEEMPMAPPING.ROLEID = TM_ROLES.ROLEID"
					+ "	INNER JOIN NPD.TM_EMPLOYEE TM_EMPLOYEE "
					+ "ON TM_ROLEEMPMAPPING.NPDEMPID = TM_EMPLOYEE.NPDEMPID WHERE TM_ROLES.ROLEID=:roleId";
			SQLQuery query = hibernateSession.createSQLQuery(sql);

			query.setLong("roleId", Long.valueOf(roleId));

			query.addScalar("npdempid", Hibernate.LONG).addScalar("empname",
					Hibernate.STRING).setResultTransformer(
					Transformers.aliasToBean(TmEmployee.class));

			employeeList = (ArrayList) query.list();

		} catch (Exception ex) {
			ex.printStackTrace();
			String msg = ex.getMessage()
					+ " Exception occured in getUsersOfRole method of "
					+ this.getClass().getSimpleName()
					+ AppUtility.getStackTrace(ex);
			AppConstants.NPDLOGGER.error(msg);
			throw new NpdException(msg);
		} finally {
			hibernateSession.close();
		}
		return employeeList;
	}

	/*
	 * Test whether a first is present in hierarchy other than input
	 * currentTaskIdString
	 */
	public ArrayList getInfoProjectInstanceFirstTask(String projectWorkflowId,
			String currentTaskIdString) throws Exception {
		ArrayList messages = new ArrayList();
		Session hibernateSession = null;

		try {
			hibernateSession = CommonBaseDao.beginTrans();
			Criteria ce = hibernateSession
					.createCriteria(TtrnProjecthierarchy.class);
			ce.add(Restrictions.eq("projectworkflowid", Long
					.parseLong(projectWorkflowId)));
			// ce.add(Restrictions.ne("currenttaskid",
			// Long.parseLong(currentTaskIdString)));
			ce.add(Restrictions.eq("isfirsttask", 1));
			ce.add(Restrictions.eq("isactive", 1));

			List list = ce.list();

			if (list.size() == 0) {
				messages.add("noFirstPresent");
			} else if (list.size() == 1) {
				TtrnProjecthierarchy ttrnProjecthierarchy = (TtrnProjecthierarchy) list
						.get(0);
				messages.add("firstAlreadyPresent");
				messages.add(ttrnProjecthierarchy);
				// messages.add("Task Name
				// :"+ttrnProjecthierarchy.getTaskname()+" and Stage Name
				// :"+ttrnProjecthierarchy.getStagename());

			}
		} catch (Exception ex) {
			ex.printStackTrace();
			String msg = ex.getMessage()
					+ " Exception occured in getInfoProjectInstanceFirstTask method of "
					+ this.getClass().getSimpleName()
					+ AppUtility.getStackTrace(ex);
			AppConstants.NPDLOGGER.error(msg);
			throw new NpdException(msg);
		} finally {
			hibernateSession.close();
		}
		return messages;
	}

	public ArrayList getInfoProjectInstanceLastTask(String projectWorkflowId,
			String currentTaskIdString) throws Exception {
		ArrayList messages = new ArrayList();
		Session hibernateSession = null;

		try {
			hibernateSession = CommonBaseDao.beginTrans();
			Criteria ce = hibernateSession
					.createCriteria(TtrnProjecthierarchy.class);
			ce.add(Restrictions.eq("projectworkflowid", Long
					.parseLong(projectWorkflowId)));
			// ce.add(Restrictions.ne("currenttaskid",
			// Long.parseLong(currentTaskIdString)));
			ce.add(Restrictions.eq("islasttask", 1));
			ce.add(Restrictions.eq("isactive", 1));

			List list = ce.list();

			if (list.size() == 0) {
				messages.add("noLastPresent");
			} else if (list.size() == 1) {
				TtrnProjecthierarchy ttrnProjecthierarchy = (TtrnProjecthierarchy) list
						.get(0);
				messages.add("lastAlreadyPresent");
				messages.add(ttrnProjecthierarchy);
				// messages.add("Task Name
				// :"+ttrnProjecthierarchy.getTaskname()+" and Stage Name
				// :"+ttrnProjecthierarchy.getStagename());

			}
		} catch (Exception ex) {
			ex.printStackTrace();
			String msg = ex.getMessage()
					+ " Exception occured in getInfoProjectInstanceLastTask method of "
					+ this.getClass().getSimpleName()
					+ AppUtility.getStackTrace(ex);
			AppConstants.NPDLOGGER.error(msg);
			throw new NpdException(msg);
		} finally {
			hibernateSession.close();
		}
		return messages;
	}

	//
	public TmWorkflow checkAnotherDraftWK(String npdCategoryId)
			throws Exception {

		MasterProjectPlanDao masterProjectPlanDao = (MasterProjectPlanDao) BaseDaoFactory
				.getInstance(DaoConstants.MASTER_PROJECT_PLAN_DAO);
		TmWorkflow tmWorkflow = null;

		try {
			tmWorkflow = masterProjectPlanDao
					.checkAnotherDraftWK(npdCategoryId);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return tmWorkflow;

	}

	/*
	 * public String getSpecialChar() { Connection connObj=null; String
	 * iChar=""; String str="SELECT CHARACTERS FROM NPD.VW_SPECIALCHARACTERS";
	 * Statement stmt = null; ResultSet rs=null; try { connObj =
	 * NpdConnection.getConnectionObject(); stmt = connObj.createStatement();
	 * rs=stmt.executeQuery(str); rs.next(); iChar=rs.getString(1); } catch
	 * (Exception ex) { ex.printStackTrace(); } finally { try { stmt.close(); }
	 * catch (SQLException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } } return iChar; }
	 */

	public String getSpecialChar() {
		SessionObjectsDto sessionObjectsDto = SessionObjectsDto.getInstance();
		String iChar = sessionObjectsDto.getSpecialCharacter();
		return iChar;
	}

	public ArrayList getUsersOfResolution(String projectId) throws Exception {
		ArrayList employeeList = new ArrayList();
		Session hibernateSession = null;
		hibernateSession = CommonBaseDao.beginTrans();
		IssuesCaptureDao dao = new IssuesCaptureDao();
		employeeList = dao.getUsersOfResolution(projectId, hibernateSession);
		CommonBaseDao.closeTrans(hibernateSession);

		return employeeList;
	}

	// TODO Change Made BY Sumit on 09-Mar-2010 (Start) Pending RFI Should Be
	// According to role
	// Start
	public ArrayList getRoleMappedWithEmployee(String empid) throws Exception {
		ArrayList<TmRoles> employeeList = new ArrayList<TmRoles>();
		MyToDOListDaoImpl dao = new MyToDOListDaoImpl();
		employeeList = dao.getRoleMappedWithEmployee(empid);
		return employeeList;
	}

	// End
	public ArrayList getUsersOfMitigation(String projectId) throws Exception {
		ArrayList employeeList = new ArrayList();
		Session hibernateSession = null;
		hibernateSession = CommonBaseDao.beginTrans();
		RisksCaptureDao dao = new RisksCaptureDao();
		employeeList = dao.getUsersOfMitigation(projectId, hibernateSession);
		CommonBaseDao.closeTrans(hibernateSession);

		return employeeList;
	}

	public boolean dashboardAuthorization(String roleID, String request,
			String empID) throws Exception {
		Boolean ABC = false;
		Session hibernateSession = null;
		try {
			hibernateSession = CommonBaseDao.beginTrans();
			CommonUtilities cmn = new CommonUtilities();
			ABC = cmn.isAuthorised(roleID, request, empID);
		} finally {
			CommonBaseDao.closeTrans(hibernateSession);
		}

		return ABC;
	}

	public String encryptString(String var1) throws Exception {

		String encode = "";
		IEncryption objen = new Encryption();

		try {
			encode = URLEncoder.encode(objen.encrypt(var1));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return encode;

	}

	public int checkDuplicateHolidayDate(String holidayDate, int holidayID)
			throws Exception {
		int cnt = 0;
		Session hibernateSession = null;
		try {
			hibernateSession = CommonBaseDao.beginTrans();
			HolidayMasterDao dao = new HolidayMasterDao();
			cnt = dao.checkDuplicateHolidayDate(holidayDate, holidayID);
		} finally {
			CommonBaseDao.closeTrans(hibernateSession);
		}
		return cnt;
	}

	public int checkDuplicateHolidayName(String holidayName, int holidayID)
			throws Exception {
		int cnt = 0;
		Session hibernateSession = null;
		try {
			hibernateSession = CommonBaseDao.beginTrans();
			HolidayMasterDao dao = new HolidayMasterDao();
			cnt = dao.checkDuplicateHolidayName(holidayName, holidayID);
		} finally {
			CommonBaseDao.closeTrans(hibernateSession);
		}
		return cnt;
	}

	// rohit verma new cr
	public ArrayList<TtrnProjecthierarchy> getPrevTaskList(String projectId,
			String projectWorkflowId, String[] taskIds) throws Exception {

		/*
		 * MasterProjectPlanDao masterProjectPlanDao = (MasterProjectPlanDao)
		 * BaseDaoFactory .getInstance(DaoConstants.MASTER_PROJECT_PLAN_DAO);
		 */
		ArrayList<TtrnProjecthierarchy> previousTaskList = null;
		Session hibernateSession = null;
		AttachEditProjectPlanDao daoObj = new AttachEditProjectPlanDao();
		AttachEditProjectPlanModel model = new AttachEditProjectPlanModel();
		try {
			hibernateSession = CommonBaseDao.beginTrans();
			ArrayList<TtrnProjecthierarchy> list = daoObj.getAllTasks(
					hibernateSession, projectWorkflowId);

			Set<TtrnProjecthierarchy> Set_PrevList = null;
			for (String taskId : taskIds) {
				ArrayList<TtrnProjecthierarchy> possPrevTasks = model
						.getPossiblePreviousTasks(taskId, list);
				if (Set_PrevList == null) {
					Set_PrevList = new HashSet<TtrnProjecthierarchy>(
							possPrevTasks);
				}
				Set_PrevList.retainAll(possPrevTasks);
			}
			if (Set_PrevList == null) {
				previousTaskList = new ArrayList<TtrnProjecthierarchy>();
			} else {
				previousTaskList = new ArrayList<TtrnProjecthierarchy>(
						Set_PrevList);
			}
			/*
			 * if("ALL".equals(option)) { previousTaskList =
			 * masterProjectPlanDao.getTaskListForACategory(null, workflowId); }
			 * else if("VALID_FOR_TASK".equals(option)) { MasterProjectPlanModel
			 * model=new MasterProjectPlanModel(); previousTaskList
			 * =model.fetchValidPrevTasks(workflowId, taskId); }
			 */

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CommonBaseDao.closeTrans(hibernateSession);
		}
		return previousTaskList;

	}

	// --------------------------------------IOES
	// Functions---------------------------------------------
	public ArrayList<IoesProductDto> addIOESProduct(String parentproductId)
			throws Exception {
		ArrayList<IoesProductDto> lstioesproduct = new ArrayList<IoesProductDto>();
		AttachEditProjectPlanDao objDao = new AttachEditProjectPlanDao();
		try {
			lstioesproduct = objDao.addIOESProduct(parentproductId);
		} catch (Exception e) {
			e.printStackTrace();

		}
		return lstioesproduct;
	}

	public ArrayList<IoesProductDto> populateProductHeader() throws Exception {
		ArrayList<IoesProductDto> lstioesproduct = new ArrayList<IoesProductDto>();
		AttachEditProjectPlanDao objDao = new AttachEditProjectPlanDao();
		try {
			lstioesproduct = objDao.populateProductHeader();
		} catch (Exception e) {
			e.printStackTrace();

		}
		return lstioesproduct;
	}

	// --------------------------------------IOES
	// Functions---------------------------------------------

}
