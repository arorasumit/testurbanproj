package com.ibm.ioes.npd.hibernate.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ibm.ioes.exception.IOESException;
import com.ibm.ioes.npd.beans.MasterProjectPlan;
import com.ibm.ioes.npd.exception.NpdException;
import com.ibm.ioes.npd.hibernate.beans.TmEmployee;
import com.ibm.ioes.npd.hibernate.beans.TmMultipleTaskMapping;
import com.ibm.ioes.npd.hibernate.beans.TmNpdcategory;
import com.ibm.ioes.npd.hibernate.beans.TmReferencedocs;
import com.ibm.ioes.npd.hibernate.beans.TmRoles;
import com.ibm.ioes.npd.hibernate.beans.TmWorkflow;
import com.ibm.ioes.npd.hibernate.beans.TmWorkflowHistory;
import com.ibm.ioes.npd.hibernate.beans.TmWorkflowstage;
import com.ibm.ioes.npd.hibernate.beans.TmWorkflowstageHistory;
import com.ibm.ioes.npd.hibernate.beans.TmWorkflowtasks;
import com.ibm.ioes.npd.hibernate.beans.TmWorkflowtasksHistory;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.HibernateBeansConstants;
import com.ibm.ioes.npd.utilities.NpdConnection;
import com.ibm.ioes.utilities.DbConnection;

/**
 * @author Disha
 * @version 1.0
 */

public class MasterProjectPlanDao extends CommonBaseDao {

	// Gives a true value if duplicate stage exists.

	public TmWorkflowstage checkDuplicateStage(String stageName, String workflowId)
			throws Exception {

		TmWorkflowstage stage = null;
		Session hibernateSession = null;
		try
		{
		hibernateSession = CommonBaseDao.beginTrans();
		ArrayList stageList = null;
		if (workflowId != null && stageName != null
				&& !workflowId.equalsIgnoreCase(AppConstants.INI_VALUE)
				&& !stageName.equalsIgnoreCase(AppConstants.INI_VALUE)) {
			Criteria ce = hibernateSession
					.createCriteria(TmWorkflowstage.class);
			ce.createAlias("workflow", "workflow");
			ce = ce.add(Restrictions.eq("stagename", stageName).ignoreCase());
			ce = ce.add(Restrictions.eq("workflow.workflowid", new Integer(
					workflowId)));
			if (ce.list() != null) {
				stageList = (ArrayList) ce.list();
			}
			if (stageList != null && stageList.size() > 0) {
				stage = (TmWorkflowstage)stageList.get(0);
			}
		}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			CommonBaseDao.closeTrans(hibernateSession);
		}
		return stage;

	}

	// Gives a TaskBean value if duplicate Task exists.

	public TmWorkflowtasks checkDuplicateTask(String taskName, String stageId)
			throws Exception {

		boolean duplicate = false;
		Session hibernateSession = null;
		TmWorkflowtasks tmWorkflowtasks =null;
		try
		{
		hibernateSession = CommonBaseDao.beginTrans();
		ArrayList taskList = null;
		
		if (stageId != null && taskName != null
				&& !stageId.equalsIgnoreCase(AppConstants.INI_VALUE)
				&& !taskName.equalsIgnoreCase(AppConstants.INI_VALUE)) {
			Criteria ce = hibernateSession
					.createCriteria(TmWorkflowtasks.class);
			ce.createAlias("stage", "stage");
			ce = ce.add(Restrictions.eq("taskname", taskName).ignoreCase());
			ce = ce.add(Restrictions.eq("stage.stageid", new Integer(stageId)));
			if (ce.list() != null) {
				taskList = (ArrayList) ce.list();
			}
			if (taskList != null && taskList.size() > 0) {
				duplicate = true;
				tmWorkflowtasks = (TmWorkflowtasks) taskList.get(0);
			}
		}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			CommonBaseDao.closeTrans(hibernateSession);
		}
		return tmWorkflowtasks;

	}

	// Gives a true value if already a Task exists at First and Last position.

	public TmWorkflowtasks checkForDuplicateFirstAndLastTask(boolean first,
			boolean last, String stageId) throws Exception {

		boolean duplicate = false;
		Session hibernateSession = null;
		TmWorkflowstage tmWorkflowstage = new TmWorkflowstage();
		TmWorkflowtasks tmWorkflowtasks = new TmWorkflowtasks();
		try
		{
		hibernateSession = CommonBaseDao.beginTrans();
		CommonBaseDao commonBaseDao = (CommonBaseDao) BaseDaoFactory
				.getInstance(DaoConstants.BASE_DAO_CLASS);
		
		ArrayList taskList = null;
		if (stageId != null
				&& !stageId.equalsIgnoreCase(AppConstants.INI_VALUE)) {
			tmWorkflowstage = (TmWorkflowstage) commonBaseDao.findById(Integer
					.parseInt(stageId),
					HibernateBeansConstants.HBM_WORKFLOW_STAGE,
					hibernateSession);

			if (first) {
				Criteria ce = hibernateSession
						.createCriteria(TmWorkflowtasks.class);
				ce.createAlias("stage", "stage");
				if (first)
					ce = ce.add(Restrictions.eq("stage.workflow.workflowid",
							new Integer(tmWorkflowstage.getWorkflow()
									.getWorkflowid())));
				ce = ce.add(Restrictions.eq("isfirsttask", AppConstants.TRUE));

				if (ce.list() != null) {
					taskList = (ArrayList) ce.list();
				}
				if (taskList != null && taskList.size() > 0) {
					tmWorkflowtasks = (TmWorkflowtasks) taskList.get(0);
				}

			}

			if (last) {
				Criteria ce1 = hibernateSession
						.createCriteria(TmWorkflowtasks.class);
				ce1.createAlias("stage", "stage");
				if (last)
					ce1 = ce1.add(Restrictions.eq("islasttask",
							AppConstants.TRUE));
				ce1 = ce1.add(Restrictions.eq("stage.workflow.workflowid",
						new Integer(tmWorkflowstage.getWorkflow()
								.getWorkflowid())));
				if (ce1.list() != null) {
					taskList = (ArrayList) ce1.list();
				}
				if (taskList != null && taskList.size() > 0) {
					tmWorkflowtasks = (TmWorkflowtasks) taskList.get(0);
				}
			}

		}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}finally
		{
			CommonBaseDao.closeTrans(hibernateSession);
		}
		return tmWorkflowtasks;

	}
	
	// Gives a true value if already a Task exists at First and Last position.

	public ArrayList<TmWorkflowtasks> fetchFirstAndLastTask(String workflowId) throws Exception {

		boolean duplicate = false;
		Session hibernateSession = null;
		ArrayList<TmWorkflowtasks> tasks=new ArrayList<TmWorkflowtasks>();
		try
		{
		//hibernateSession = CommonBaseDao.beginTrans();
		CommonBaseDao commonBaseDao = (CommonBaseDao) BaseDaoFactory
				.getInstance(DaoConstants.BASE_DAO_CLASS);
		
		
		//////////////////////
		
		
		hibernateSession = CommonBaseDao.beginTrans();
		

		
		ArrayList taskList = new ArrayList();
		
		Criteria ce = hibernateSession.createCriteria(TmWorkflowtasks.class);
		ce.createAlias("stage", "stage");
		ce.createAlias("stage.workflow", "workflow");
		ce = ce.add(Restrictions.eq("workflow.workflowid", new Integer(workflowId)));
		ce = ce.add(Restrictions.eq("isfirsttask", AppConstants.TRUE));
		
		List l=ce.list();
		if(l==null ||l.size()==0)
		{
			tasks.add(null);
		}
		else
		{
			tasks.add((TmWorkflowtasks)ce.list().get(0));
		}
		
		ce = hibernateSession.createCriteria(TmWorkflowtasks.class);
		ce.createAlias("stage", "stage");
		ce.createAlias("stage.workflow", "workflow");
		ce = ce.add(Restrictions.eq("workflow.workflowid", new Integer(workflowId)));
		ce = ce.add(Restrictions.eq("islasttask", AppConstants.TRUE));
		
		l=ce.list();
		if(l==null ||l.size()==0)
		{
			tasks.add(null);
		}
		else
		{
			tasks.add((TmWorkflowtasks)ce.list().get(0));
		}
		
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			CommonBaseDao.closeTrans(hibernateSession);
		}
		
		return tasks;

	}

	// saves the value of Task in the DB.

	public boolean saveUpdateTask(MasterProjectPlan masterProjectPlan,
			TmEmployee loginUser) throws Exception {

		boolean insert = true;
		//System.err.println("for task:"+masterProjectPlan.getTaskId());
		Session hibernateSession = null;
		hibernateSession = CommonBaseDao.beginTrans();
		CommonBaseDao commonBaseDao = (CommonBaseDao) BaseDaoFactory
				.getInstance(DaoConstants.BASE_DAO_CLASS);
		TmWorkflowstage tmWorkflowstage = new TmWorkflowstage();
		TmRoles tmRoles = new TmRoles();
		TmReferencedocs referencedocs = new TmReferencedocs();
		TmWorkflowtasks tmWorkflowtasks = null;
		TmMultipleTaskMapping multipleTaskMapping = new TmMultipleTaskMapping();
		Connection conn=null;
		try {
			if(masterProjectPlan.getTaskId()==null || "".equals(masterProjectPlan.getTaskId()))
			{
				tmWorkflowtasks = new TmWorkflowtasks();
			}
			else
			{
				tmWorkflowtasks = (TmWorkflowtasks) commonBaseDao.findById(Long
						.parseLong(masterProjectPlan.getTaskId()),
						HibernateBeansConstants.HBM_WORKFLOW_TASK,
						hibernateSession);
			}
			
			tmWorkflowstage = (TmWorkflowstage) commonBaseDao.findById(Integer
					.parseInt(masterProjectPlan.getStageId()),
					HibernateBeansConstants.HBM_WORKFLOW_STAGE,
					hibernateSession);
			tmRoles = (TmRoles) commonBaseDao.findById(Integer
					.parseInt(masterProjectPlan.getStakeHolderId()),
					HibernateBeansConstants.HBM_ROLE, hibernateSession);
			if (loginUser != null)
				tmWorkflowtasks.setCreatedby(new Long(loginUser.getNpdempid())
						.intValue());
			tmWorkflowtasks.setCreateddate(new Date());
			tmWorkflowtasks.setStage(tmWorkflowstage);
			tmWorkflowtasks.setIsattachment(AppConstants.FALSE);
			tmWorkflowtasks.setIsactive(AppConstants.TRUE.intValue());
			if (masterProjectPlan.isFirst()) {
				tmWorkflowtasks.setIsfirsttask(AppConstants.TRUE);
			} else {
				tmWorkflowtasks.setIsfirsttask(AppConstants.FALSE);
			}
			if (masterProjectPlan.isLast()) {
				tmWorkflowtasks.setIslasttask(AppConstants.TRUE);
			} else {
				tmWorkflowtasks.setIslasttask(AppConstants.FALSE);
			}
			
			tmWorkflowtasks.setTasktype(AppConstants.TRUE);			
			tmWorkflowtasks.setIsrejectionallowed(AppConstants.TRUE);
			tmWorkflowtasks.setPlanneddurationdays(1);
			tmWorkflowtasks.setStakeholder(tmRoles);
			tmWorkflowtasks.setTaskdesc(masterProjectPlan.getTaskDescription());
			tmWorkflowtasks.setTaskinstructionremarks(masterProjectPlan
					.getRemarks());
			tmWorkflowtasks.setTaskname(masterProjectPlan.getTask());

			
			commonBaseDao.attachDirty(tmWorkflowtasks, hibernateSession);
			//commonBaseDao.closeTrans(hibernateSession);
			
			if (masterProjectPlan.isAttachment()) {
				//update reference docid
				String sql="UPDATE NPD.TM_WORKFLOWTASKS SET REFERENCEDOCID="+masterProjectPlan.getTemplateId()+
				" WHERE TASKID="+tmWorkflowtasks.getTaskid();
				conn=hibernateSession.connection();//NpdConnection.getConnectionObject();
				conn.setAutoCommit(false);
				conn.createStatement().executeUpdate(sql);
				//DbConnection.freeConnection(conn);
				
			} 
			else
			{
				String sql="UPDATE NPD.TM_WORKFLOWTASKS SET REFERENCEDOCID=null WHERE TASKID="+tmWorkflowtasks.getTaskid();
				conn=hibernateSession.connection();//NpdConnection.getConnectionObject();
				conn.setAutoCommit(false);
				conn.createStatement().executeUpdate(sql);
				//DbConnection.freeConnection(conn);
			}
			
			//hibernateSession=commonBaseDao.beginTrans();
			Query query = hibernateSession
						.createQuery("delete from TmMultipleTaskMapping where taskid = :taskid");
			query.setLong("taskid",new Long(tmWorkflowtasks.getTaskid()));
			query.executeUpdate();
			//commonBaseDao.closeTrans(hibernateSession);
			//hibernateSession=commonBaseDao.beginTrans();
			if (masterProjectPlan.getPreviousTaskId() != null) {
				for (int j = 0; j < masterProjectPlan.getPreviousTaskId().length; j++) {
					multipleTaskMapping = new TmMultipleTaskMapping();
					multipleTaskMapping.setTaskid(new Long(tmWorkflowtasks
							.getTaskid()));
					multipleTaskMapping.setPreviousTaskId(new Long(
							masterProjectPlan.getPreviousTaskId()[j]));
					commonBaseDao.attachDirty(multipleTaskMapping,
							hibernateSession);
				}

			}

		} catch (Exception e) {
			insert = false;
			hibernateSession.getTransaction().rollback();
			e.printStackTrace();

		} finally {
			hibernateSession.flush();
			commonBaseDao.closeTrans(hibernateSession);
			if(conn!=null)
			{
				try {
					conn.close();
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
		}

		return insert;

	}

	// Updates the Attributes of already existing Task in the DB.

	public boolean updateTask(String taskID, String remarks, String desc,
			String stakeholderId, String plannedDays, String taskName,
			boolean first, boolean last, String[] prevTaskId) throws Exception {

		boolean insert = true;
		Session hibernateSession = null;
		hibernateSession = CommonBaseDao.beginTrans();
		CommonBaseDao commonBaseDao = (CommonBaseDao) BaseDaoFactory
				.getInstance(DaoConstants.BASE_DAO_CLASS);
		TmRoles tmRoles = new TmRoles();
		TmWorkflowtasks tmWorkflowtasks = null;
		TmMultipleTaskMapping multipleTaskMapping = new TmMultipleTaskMapping();
		try {

			if (taskID != null
					&& !taskID.equalsIgnoreCase(AppConstants.INI_VALUE)) {

				tmWorkflowtasks = (TmWorkflowtasks) commonBaseDao.findById(Long
						.parseLong(taskID),
						HibernateBeansConstants.HBM_WORKFLOW_TASK,
						hibernateSession);
				tmRoles = (TmRoles) commonBaseDao.findById(Integer
						.parseInt(stakeholderId),
						HibernateBeansConstants.HBM_ROLE, hibernateSession);
				if (tmWorkflowtasks != null) {
					if (plannedDays != null
							&& !plannedDays
									.equalsIgnoreCase(AppConstants.INI_VALUE))
						tmWorkflowtasks.setPlanneddurationdays(new Integer(
								plannedDays));
					tmWorkflowtasks.setStakeholder(tmRoles);
					tmWorkflowtasks.setTaskdesc(desc);
					tmWorkflowtasks.setTaskinstructionremarks(remarks);
					tmWorkflowtasks.setTaskname(taskName);
					if (first) {
						tmWorkflowtasks.setIsfirsttask(AppConstants.TRUE);
					} else {
						tmWorkflowtasks.setIsfirsttask(AppConstants.FALSE);
					}
					if (last) {
						tmWorkflowtasks.setIslasttask(AppConstants.TRUE);
					} else {
						tmWorkflowtasks.setIslasttask(AppConstants.FALSE);
					}

				}
			}

			commonBaseDao.attachDirty(tmWorkflowtasks, hibernateSession);

			if (prevTaskId != null) {
				Query query = hibernateSession
						.createQuery("delete from TmMultipleTaskMapping where taskid = :taskid");
				query.setLong("taskid",
						new Long(tmWorkflowtasks.getTaskid()));
				query.executeUpdate();
				for (int j = 0; j < prevTaskId.length; j++) {
					if (prevTaskId[j] != null) {
						multipleTaskMapping = new TmMultipleTaskMapping();
						multipleTaskMapping.setTaskid(new Long(tmWorkflowtasks
								.getTaskid()));
						multipleTaskMapping.setPreviousTaskId(new Long(
								prevTaskId[j]));
						commonBaseDao.attachDirty(multipleTaskMapping,
								hibernateSession);
					}

				}
			}

		} catch (Exception e) {
			insert = false;
			hibernateSession.getTransaction().rollback();
			e.printStackTrace();

		} finally {
			commonBaseDao.closeTrans(hibernateSession);
		}

		return insert;

	}

	// saves the value of stage in the DB.

	public boolean saveUpdateStage(MasterProjectPlan masterProjectPlan,
			TmEmployee tmEmployee) throws Exception {

		boolean insert = true;
		Session hibernateSession = null;
		hibernateSession = CommonBaseDao.beginTrans();
		CommonBaseDao commonBaseDao = (CommonBaseDao) BaseDaoFactory
				.getInstance(DaoConstants.BASE_DAO_CLASS);
		TmWorkflow tmWorkflow = new TmWorkflow();
		TmWorkflowstage tmWorkflowstage =null;
		try {
			if(masterProjectPlan.getStageId()==null || "".equals(masterProjectPlan.getStageId()))
			{
				tmWorkflowstage = new TmWorkflowstage();	
			}
			else
			{
				tmWorkflowstage = (TmWorkflowstage) commonBaseDao.findById(Integer
						.parseInt(masterProjectPlan.getStageId()),
						HibernateBeansConstants.HBM_WORKFLOW_STAGE, hibernateSession);
			}
			
			tmWorkflow = (TmWorkflow) commonBaseDao.findById(Integer
					.parseInt(masterProjectPlan.getWorkflowId()),
					HibernateBeansConstants.HBM_WORKFLOW, hibernateSession);

			if (tmWorkflow != null) {
				tmWorkflowstage.setWorkflow(tmWorkflow);
				tmWorkflowstage.setStagedesc(masterProjectPlan.getStageDescription());
				tmWorkflowstage.setStagename(masterProjectPlan.getStage());
				if (tmEmployee != null)
					tmWorkflowstage.setCreatedby(new Long(tmEmployee
							.getNpdempid()).intValue());
				tmWorkflowstage.setCreateddate(new Date());
				commonBaseDao.attachDirty(tmWorkflowstage, hibernateSession);
			}

		} catch (Exception ex) {
			insert = false;
			hibernateSession.getTransaction().rollback();
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in saveUpdateStage method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException(ex);

		} finally {
			commonBaseDao.closeTrans(hibernateSession);
		}

		return insert;

	}

	
	

	// gets the stage List for adding a task page

	public ArrayList getStageList(MasterProjectPlan masterProjectPlan,Session hibernateSession)

	throws Exception {

		//Session hibernateSession = null;
		//hibernateSession = CommonBaseDao.beginTrans();
		CommonBaseDao commonBaseDao = (CommonBaseDao) BaseDaoFactory
				.getInstance(DaoConstants.BASE_DAO_CLASS);
		ArrayList stageList = null;

		try {
			Criteria criteria = hibernateSession
					.createCriteria(TmWorkflowstage.class);
			criteria.createAlias("workflow", "workflow");
			criteria.add(Restrictions.eq("workflow.workflowid", new Integer(
					masterProjectPlan.getWorkflowId())));
			criteria.addOrder(Order.asc("stagename"));
			stageList = (ArrayList) criteria.list();

		} catch (Exception e) {
			e.printStackTrace();
			//hibernateSession.getTransaction().rollback();
			throw new Exception();

		}

		return stageList;

	}

	// gets the active Workflow List
// SH_RAV :celled from viewMasterProjectPlan finction: class :  MasterProjectPlanModel
	//previos name:getActiveWorkflow
	public ArrayList getWorkingWorkflows(Session hibernateSession,MasterProjectPlan masterProjectPlan)

	throws Exception {

		
		ArrayList workflowList = null;

		try {
			Criteria criteria = hibernateSession
					.createCriteria(TmWorkflow.class);
			ArrayList<Integer> flags=new ArrayList<Integer>();
			flags.add(new Integer(AppConstants.ACTIVE_FLAG));
			flags.add(new Integer(AppConstants.DRAFT_FLAG));
			criteria.add(Restrictions.in("isactive", flags));
			criteria.createAlias("npdCategory", "npdCategory");
			criteria.addOrder(Order.asc("npdCategory.npdcatid"));
			workflowList = (ArrayList) criteria.list();

		} catch (Exception e) {
			e.printStackTrace();
			
			throw new Exception();

		}

		return workflowList;

	}

	// saves the value of Workflow in the DB.

	public TmWorkflow saveWorkflow(MasterProjectPlan masterProjectPlan,
			TmEmployee tmEmployee) throws Exception {

		ArrayList activeWorkflowList = new ArrayList();
		ArrayList stageList = new ArrayList();
		ArrayList taskList = new ArrayList();
		Session hibernateSession = null;
		hibernateSession = CommonBaseDao.beginTrans();
		CommonBaseDao commonBaseDao = (CommonBaseDao) BaseDaoFactory
				.getInstance(DaoConstants.BASE_DAO_CLASS);
		TmWorkflow tmWorkflow = new TmWorkflow();
		TmWorkflow tmWorkflow1 = new TmWorkflow();
		TmWorkflowstage tmWorkflowstage = new TmWorkflowstage();
		TmWorkflowtasks tmWorkflowtasks = new TmWorkflowtasks();
		TmWorkflowstage tmWorkflowstage1 = new TmWorkflowstage();
		TmWorkflowtasks tmWorkflowtasks1 = new TmWorkflowtasks();
		TmWorkflowstageHistory tmWorkflowstageHistory = new TmWorkflowstageHistory();
		TmWorkflowtasksHistory tmWorkflowtasksHistory = new TmWorkflowtasksHistory();
		TmWorkflowHistory tmWorkflowHistory = new TmWorkflowHistory();
		TmNpdcategory npdcategory = new TmNpdcategory();
		Connection conn=null;
		try {
			
			
				npdcategory = (TmNpdcategory) commonBaseDao.findById(Integer
						.parseInt(masterProjectPlan.getNpdCategoryId()),
						HibernateBeansConstants.HBM_NPD_CATEGORY,
						hibernateSession);
				
				
				//delete previous draft for the same npdcategory
				
				ArrayList<TmWorkflow> list=null;
				Criteria ce=hibernateSession.createCriteria(HibernateBeansConstants.HBM_WORKFLOW);
				ce.add(Restrictions.eq("npdCategory", npdcategory));
				ce.add(Restrictions.eq("isactive", new Integer(2)));
				list=(ArrayList<TmWorkflow>)ce.list();
				conn=hibernateSession.connection();
				conn.setAutoCommit(false);
				if(list!=null && list.size()>0)
				{
					//delete it
					TmWorkflow temp=(TmWorkflow)list.get(0);
					deleteWorkFlow(conn,temp.getWorkflowid());
				}
				
				//saving new workflow in raft mode
				tmWorkflow.setApplicationcode(AppConstants.APP_NAME);
				tmWorkflow.setApplicationname(AppConstants.APP_NAME);
				if (tmEmployee != null)
					tmWorkflow.setCreatedby(new Long(tmEmployee.getNpdempid())
							.intValue());
				tmWorkflow.setCreateddate(new Date());
				tmWorkflow.setIsactive(AppConstants.DRAFT_FLAG);
				tmWorkflow.setNpdCategory(npdcategory);
				tmWorkflow.setWorkflowdesc(masterProjectPlan.getWorkflowDesc());
				tmWorkflow.setWorkflowname(masterProjectPlan.getWorkflowDesc());

				commonBaseDao.attachDirty(tmWorkflow, hibernateSession);
				//MasterProjectPlan masterProjectPlan = new MasterProjectPlan();
				masterProjectPlan.setWorkflowId(String.valueOf(tmWorkflow.getWorkflowid()));
				masterProjectPlan.setStageDescription("IOES");
				masterProjectPlan.setStage("IOES");
				saveUpdateStage(masterProjectPlan, tmEmployee);
				
				// calling procedure for duplicating task records
/*
				activeWorkflowList = getActiveWorkflow(masterProjectPlan);
				for (int i = 0; i < activeWorkflowList.size(); i++) {
					tmWorkflow1 = new TmWorkflow();
					if (tmWorkflow1.getNpdCategory() == npdcategory) {
						masterProjectPlan.setWorkflowId(new Integer(tmWorkflow1
								.getWorkflowid()).toString());
						stageList = getStageList(masterProjectPlan);
						for (int k = 0; k < stageList.size(); k++) {
							tmWorkflowstage = (TmWorkflowstage) stageList
									.get(k);
							BeanUtils.copyProperties(tmWorkflowstage1,
									tmWorkflowstage);
							commonBaseDao.attachDirty(tmWorkflowstage1,
									hibernateSession);
						}
						taskList = getTaskListForACategory(null,
								masterProjectPlan.getWorkflowId());
						for (int k = 0; k < taskList.size(); k++) {
							tmWorkflowtasks = (TmWorkflowtasks) taskList.get(k);
							BeanUtils.copyProperties(tmWorkflowtasks1,
									tmWorkflowtasks);
							commonBaseDao.attachDirty(tmWorkflowtasks1,
									hibernateSession);
						}
					}

				}*/

			
		
		} catch (Exception ex) {
			hibernateSession.getTransaction().rollback();
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in saveWorkflow method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException(ex);

		} finally {
			commonBaseDao.closeTrans(hibernateSession);
			if(conn!=null) conn.close();
			
		}

		return tmWorkflow;

	}
	
	public TmWorkflow finalize(MasterProjectPlan masterProjectPlan,
			TmEmployee tmEmployee) throws Exception {

		ArrayList activeWorkflowList = new ArrayList();
		ArrayList stageList = new ArrayList();
		ArrayList taskList = new ArrayList();
		Session hibernateSession = null;
		hibernateSession = CommonBaseDao.beginTrans();
		CommonBaseDao commonBaseDao = (CommonBaseDao) BaseDaoFactory
				.getInstance(DaoConstants.BASE_DAO_CLASS);
		TmWorkflow tmWorkflow = new TmWorkflow();
		TmWorkflow tmWorkflow1 = new TmWorkflow();
		TmWorkflowstage tmWorkflowstage = new TmWorkflowstage();
		TmWorkflowtasks tmWorkflowtasks = new TmWorkflowtasks();
		TmWorkflowstage tmWorkflowstage1 = new TmWorkflowstage();
		TmWorkflowtasks tmWorkflowtasks1 = new TmWorkflowtasks();
		TmWorkflowstageHistory tmWorkflowstageHistory = new TmWorkflowstageHistory();
		TmWorkflowtasksHistory tmWorkflowtasksHistory = new TmWorkflowtasksHistory();
		TmWorkflowHistory tmWorkflowHistory = new TmWorkflowHistory();
		TmNpdcategory npdcategory = new TmNpdcategory();
		Connection conn=null;
		try {
			conn=hibernateSession.connection();
			conn.setAutoCommit(false);
			if (masterProjectPlan.getWorkflowId() != null
					&& !masterProjectPlan.getWorkflowId().equalsIgnoreCase(
							AppConstants.INI_VALUE)) {
				// call procedure to update the history table
				tmWorkflow = (TmWorkflow) commonBaseDao.findById(Integer
						.parseInt(masterProjectPlan.getWorkflowId()),
						HibernateBeansConstants.HBM_WORKFLOW, hibernateSession);
				if (tmWorkflow != null) {
					BeanUtils.copyProperties(tmWorkflowHistory, tmWorkflow);
					commonBaseDao.attachDirty(tmWorkflowHistory,
							hibernateSession);
					masterProjectPlan.setWorkflowId(new Integer(tmWorkflow
							.getWorkflowid()).toString());
					stageList = getStageList(masterProjectPlan,hibernateSession);
					for (int j = 0; j < stageList.size(); j++) {
						tmWorkflowstageHistory = new TmWorkflowstageHistory();
						tmWorkflowstage = (TmWorkflowstage) stageList.get(j);
						BeanUtils.copyProperties(tmWorkflowstageHistory,
								tmWorkflowstage);
						tmWorkflowstageHistory.setWorkflow1(tmWorkflowHistory
								.getWorkflowid());
						tmWorkflowstageHistory
								.setWorkflowhistoryid(tmWorkflowHistory);
						commonBaseDao.attachDirty(tmWorkflowstageHistory,
								hibernateSession);
						
						taskList = getTaskListForACategory(null,
								masterProjectPlan.getWorkflowId(),hibernateSession);
						for (int k = 0; k < taskList.size(); k++) {
							tmWorkflowtasksHistory = new TmWorkflowtasksHistory();
							tmWorkflowtasks = (TmWorkflowtasks) taskList.get(k);
							if (tmWorkflowtasks.getStage().getStageid() == tmWorkflowstage
									.getStageid()) {
								BeanUtils
										.copyProperties(tmWorkflowtasksHistory,
												tmWorkflowtasks);
								tmWorkflowtasksHistory
										.setStage1(tmWorkflowstageHistory
												.getStageid());
								tmWorkflowtasksHistory
										.setStagehistoryid(tmWorkflowstageHistory);
								commonBaseDao.attachDirty(
										tmWorkflowtasksHistory,
										hibernateSession);
								
								//transdfer for mapping table
/*								String sql=" INSERT INTO NPD.TM_MULTIPLETASKMAPPING_HISTORY( TASKID, PREVIOUSTASKID) " +
											" SELECT TASKID, PREVIOUSTASKID FROM NPD.TM_MULTIPLETASKMAPPING " +
											"WHERE TASKID="+tmWorkflowtasks.getTaskid();
								Statement stmt=conn.createStatement();
								stmt.execute(sql);
								stmt.close();
*/							}

							//transdfer for mapping table
						}

					}

				}
				//hibernateSession.getTransaction().commit();
				Query query = hibernateSession
						.createQuery("UPDATE TmWorkflowHistory tmWorkflowHistory set tmWorkflowHistory.isactive=:isactive where tmWorkflowHistory.npdCategory.npdcatid=:npdCatId ");
				query.setInteger("isactive", AppConstants.INACTIVE_FLAG);
				query.setInteger("npdCatId", Integer.parseInt(masterProjectPlan
						.getNpdCategoryId()));
				query.executeUpdate();
				Query query1 = hibernateSession
						.createQuery("UPDATE TmWorkflowHistory tmWorkflowHistory set tmWorkflowHistory.isactive=:isactive where tmWorkflowHistory.id="
								+ "(select max(tmWorkflowHistory.id) from tmWorkflowHistory where tmWorkflowHistory.npdCategory.npdcatid=:npdCatId)");
				query1.setInteger("isactive", AppConstants.ACTIVE_FLAG);
				query1.setInteger("npdCatId", Integer
						.parseInt(masterProjectPlan.getNpdCategoryId()));
				query1.executeUpdate();
				
				//commonBaseDao.closeTrans(hibernateSession);
				
				//hibernateSession=commonBaseDao.beginTrans();
				//get previous active and detelte it as it is alreday in hostory when it was finalised
				ArrayList<TmWorkflow> list=null;
				npdcategory = (TmNpdcategory) commonBaseDao.findById(Integer
						.parseInt(masterProjectPlan.getNpdCategoryId()),
						HibernateBeansConstants.HBM_NPD_CATEGORY,
						hibernateSession);
				Criteria ce=hibernateSession.createCriteria(HibernateBeansConstants.HBM_WORKFLOW);
				ce.add(Restrictions.eq("npdCategory", npdcategory));
				ce.add(Restrictions.eq("isactive", new Integer(1)));
				list=(ArrayList<TmWorkflow>)ce.list();
				
				
				if(list!=null && list.size()>0)
				{
					//delete it
					TmWorkflow temp=(TmWorkflow)list.get(0);
					
					deleteWorkFlow(conn,temp.getWorkflowid());
				}
				
				//set the new workflow from draft to active status
				/*query1 = hibernateSession
					.createQuery("UPDATE TmWorkflow tmWorkflow set tmWorkflow.isactive=:isactive where tmWorkflow.id=:workflowid");
				query1.setInteger("isactive", AppConstants.ACTIVE_FLAG);
				query1.setInteger("workflowid", Integer.parseInt(masterProjectPlan.getWorkflowId()));
				query1.executeUpdate();*/
				
				//delete prious project hierarchy data from workplanhierarchy
				
				
//				 call procedure to update the workflow Heriarchy table.

				
				CallableStatement callableStatement = conn
						.prepareCall("{call NPD.P_DTS_WFHIERARCHY(?,?,?)}");
				callableStatement.setLong(1, Long.parseLong(masterProjectPlan
						.getWorkflowId()));
				callableStatement.setString(2, "");
				callableStatement.setString(3, ""); 
				callableStatement.execute();
				
				

			} 
					
		} catch (Exception ex) {
			hibernateSession.getTransaction().rollback();
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in finalize method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException(ex);

		} finally {
			commonBaseDao.closeTrans(hibernateSession);
			if(conn!=null){
				
				conn.close();
			}
		}

		return tmWorkflow;

	}

	
	public void deleteWorkFlow(Connection conn,int id) throws NpdException
	{
		String sqldeletePrevMapping="DELETE FROM NPD.TM_MULTIPLETASKMAPPING WHERE TASKID IN " +
				"(SELECT TM_WORKFLOWTASKS.TASKID FROM NPD.TM_WORKFLOWTASKS TM_WORKFLOWTASKS INNER JOIN " +
				"NPD.TM_WORKFLOWSTAGE TM_WORKFLOWSTAGE ON TM_WORKFLOWTASKS.STAGEID = TM_WORKFLOWSTAGE.STAGEID " +
				"INNER JOIN NPD.TM_WORKFLOW TM_WORKFLOW ON TM_WORKFLOWSTAGE.WORKFLOWID = TM_WORKFLOW.WORKFLOWID " +
				"WHERE (TM_WORKFLOW.WORKFLOWID ="+id+"))";
		String sqlDeleteTasks="DELETE FROM NPD.TM_WORKFLOWTASKS WHERE TASKID IN " +
				"(SELECT TM_WORKFLOWTASKS.TASKID FROM NPD.TM_WORKFLOWTASKS " +
				"TM_WORKFLOWTASKS INNER JOIN NPD.TM_WORKFLOWSTAGE TM_WORKFLOWSTAGE " +
				"ON TM_WORKFLOWTASKS.STAGEID = TM_WORKFLOWSTAGE.STAGEID INNER JOIN NPD.TM_WORKFLOW TM_WORKFLOW " +
				"ON TM_WORKFLOWSTAGE.WORKFLOWID = TM_WORKFLOW.WORKFLOWID WHERE " +
				"(TM_WORKFLOW.WORKFLOWID ="+id+"))";
		String sqldeleteStages="DELETE FROM NPD.TM_WORKFLOWSTAGE WHERE STAGEID IN " +
				"(SELECT	TM_WORKFLOWSTAGE.STAGEID FROM NPD.TM_WORKFLOWSTAGE TM_WORKFLOWSTAGE " +
				"INNER JOIN NPD.TM_WORKFLOW TM_WORKFLOW ON TM_WORKFLOWSTAGE.WORKFLOWID = TM_WORKFLOW.WORKFLOWID " +
				"WHERE (TM_WORKFLOW.WORKFLOWID ="+id+"))";
		String sqldeleteWK="DELETE FROM NPD.TM_WORKFLOW WHERE WORKFLOWID="+id;
		try {
			conn.createStatement().execute(sqldeletePrevMapping);
			conn.createStatement().execute(sqlDeleteTasks);
			conn.createStatement().execute(sqldeleteStages);
			conn.createStatement().execute(sqldeleteWK);
		} catch (SQLException ex) {
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in deleteWorkFlow method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException(ex.getMessage()
					+ " Exception occured in deleteWorkFlow method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
		}
		
		
	}
	// Gives a list of Previous task available for a Product category/Stage

	public ArrayList getTaskListForACategory(String stageId, String workflowId,Session hibernateSession)
			throws Exception {

		ArrayList previousTaskList = new ArrayList();
		//Session hibernateSession = null;
		if(hibernateSession==null)
		{
			hibernateSession = CommonBaseDao.beginTrans();	
		}
		
		CommonBaseDao commonBaseDao = (CommonBaseDao) BaseDaoFactory
				.getInstance(DaoConstants.BASE_DAO_CLASS);
		TmWorkflowstage tmWorkflowstage = new TmWorkflowstage();
		TmNpdcategory npdcategory = new TmNpdcategory();
		TmWorkflowtasks tmWorkflowtasks = new TmWorkflowtasks();
		ArrayList taskList = new ArrayList();

		Criteria ce = hibernateSession.createCriteria(TmWorkflowtasks.class);
		ce.createAlias("stage", "stage");
		ce.createAlias("stage.workflow", "workflow");
		if (stageId != null
				&& !stageId.equalsIgnoreCase(AppConstants.INI_VALUE)) {
			tmWorkflowstage = (TmWorkflowstage) commonBaseDao.findById(Integer
					.parseInt(stageId),
					HibernateBeansConstants.HBM_WORKFLOW_STAGE,
					hibernateSession);
			ce = ce.add(Restrictions.eq("workflow.workflowid", new Integer(
					tmWorkflowstage.getWorkflow().getWorkflowid())));
			ce = ce.add(Restrictions.eq("islasttask", AppConstants.FALSE));
		} else {
			ce = ce.add(Restrictions.eq("workflow.workflowid", new Integer(
					workflowId)));
			ce = ce.add(Restrictions.eq("isactive", AppConstants.TRUE));

		}

		ce.addOrder(Order.desc("isfirsttask"));
		ce.addOrder(Order.asc("stage.stageid"));
		//ce.addOrder(Order.desc("isfirsttask"));
		ce.addOrder(Order.asc("islasttask"));
		ce.addOrder(Order.asc("taskid"));
		if (ce.list() != null) {
			previousTaskList = (ArrayList) ce.list();
		}
		// for removing the blob reference of reference doc in taskList.
		if (previousTaskList != null && previousTaskList.size() > 0) {

			for (int i = 0; i < previousTaskList.size(); i++) {
				tmWorkflowtasks = (TmWorkflowtasks) previousTaskList.get(i);
				//tmWorkflowtasks.setReferenceDoc(null);
				taskList.add(tmWorkflowtasks);
			}
			previousTaskList = taskList;
		}
		
		return previousTaskList;

	}

	// Gives a list of Previous task available for a Product category/Stage

	public ArrayList getMasterProjectPlanVersionForACategory(
			String npdCategoryId) throws Exception {

		ArrayList previousTaskList = new ArrayList();

		Session hibernateSession = null;
		try
		{
		hibernateSession = CommonBaseDao.beginTrans();
		CommonBaseDao commonBaseDao = (CommonBaseDao) BaseDaoFactory
				.getInstance(DaoConstants.BASE_DAO_CLASS);
		TmNpdcategory npdcategory = new TmNpdcategory();
		TmWorkflowtasksHistory tmWorkflowtasksHistory = new TmWorkflowtasksHistory();
		ArrayList taskList = new ArrayList();

		Criteria ce = hibernateSession
				.createCriteria(TmWorkflowtasksHistory.class);
		ce.createAlias("stagehistoryid", "stagehistoryid");
		ce.createAlias("stagehistoryid.workflowhistoryid", "workflowhistoryid");

		npdcategory = (TmNpdcategory) commonBaseDao.findById(Integer
				.parseInt(npdCategoryId),
				HibernateBeansConstants.HBM_NPD_CATEGORY, hibernateSession);
		ce = ce.add(Restrictions.eq("workflowhistoryid.npdCategory",
				npdcategory));

		ce.addOrder(Order.desc("stagehistoryid.workflowhistoryid.id"));
		ce.addOrder(Order.asc("stagehistoryid.stagename"));
		ce.addOrder(Order.asc("taskid"));
		if (ce.list() != null) {
			previousTaskList = (ArrayList) ce.list();
		}
		// for removing the blob reference of reference doc in taskList.
		if (previousTaskList != null && previousTaskList.size() > 0) {

			for (int i = 0; i < previousTaskList.size(); i++) {
				tmWorkflowtasksHistory = (TmWorkflowtasksHistory) previousTaskList
						.get(i);
				tmWorkflowtasksHistory.setReferenceDoc(null);
				taskList.add(tmWorkflowtasksHistory);
			}
			previousTaskList = taskList;
		}
		} catch (Exception ex) {
			
			hibernateSession.getTransaction().rollback();
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in getMasterProjectPlanVersionForACategory method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException(ex);
			
		} finally {
			CommonBaseDao.closeTrans(hibernateSession);
		}
		return previousTaskList;

	}

	// Deletes the task from DB.

	public boolean deleteTask(String taskID) throws Exception {

		boolean delete = true;
		Session hibernateSession = null;
		hibernateSession = CommonBaseDao.beginTrans();
		CommonBaseDao commonBaseDao = (CommonBaseDao) BaseDaoFactory
				.getInstance(DaoConstants.BASE_DAO_CLASS);
		TmWorkflowtasks tmWorkflowtasks = new TmWorkflowtasks();
		Connection conn=null;
		try {

			if (taskID != null
					&& !taskID.equalsIgnoreCase(AppConstants.INI_VALUE)) {
				
				////////
				
				String sqlDeletePrevMapping1="DELETE FROM NPD.TM_MULTIPLETASKMAPPING WHERE TASKID="+taskID;
				String sqlDeletePrevMapping2="DELETE FROM NPD.TM_MULTIPLETASKMAPPING WHERE PREVIOUSTASKID="+taskID;
				String sqlDeleteTask="DELETE FROM NPD.TM_WORKFLOWTASKS WHERE TASKID="+taskID;
				
				//taskID
				//delete previous 
				conn=hibernateSession.connection();
				conn.createStatement().execute(sqlDeletePrevMapping1);
				conn.createStatement().execute(sqlDeletePrevMapping2);
				conn.createStatement().execute(sqlDeleteTask);
				conn.close();
				delete=true;
				//delete task
				
				/*
				
				
				/////////
				tmWorkflowtasks = (TmWorkflowtasks) commonBaseDao.findById(Long
						.parseLong(taskID),
						HibernateBeansConstants.HBM_WORKFLOW_TASK,
						hibernateSession);
				Query query = hibernateSession
						.createQuery("UPDATE TmWorkflowtasks tmWorkflowtasks SET prevtaskid=:prevtaskid where prevtaskid=:currentTaskId");
				query.setLong("prevtaskid", tmWorkflowtasks.getPrevtaskid()
						.longValue());
				query.setLong("currentTaskId", tmWorkflowtasks.getTaskid());
				//query.executeUpdate();

				if (tmWorkflowtasks != null) {
					commonBaseDao.delete(tmWorkflowtasks, hibernateSession);
					//tmWorkflowtasks.setIsactive(AppConstants.FALSE.intValue());
					//commonBaseDao
						//	.attachDirty(tmWorkflowtasks, hibernateSession);
				} else {
					delete = false;
				}*/

			}
		} catch (Exception ex) {
			delete = false;
			hibernateSession.getTransaction().rollback();
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in deleteTask method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException(ex);
			
		} finally {
			try{commonBaseDao.closeTrans(hibernateSession);}
			finally{
			if(conn!=null) conn.close();
			
		}

		return delete;
	}

	}// Gives a list of Previous task available for a task

	public String[] getPreviousTasks(String taskId) throws Exception {

		String[] previousTask = null;
		ArrayList previousTaskList = new ArrayList();
		Session hibernateSession = null;
		try
		{
		hibernateSession = CommonBaseDao.beginTrans();
		TmMultipleTaskMapping tmMultipleTaskMapping = new TmMultipleTaskMapping();

		Criteria ce = hibernateSession
				.createCriteria(TmMultipleTaskMapping.class);
		ce = ce.add(Restrictions.eq("taskid", new Long(taskId)));

		ce.addOrder(Order.asc("previousTaskId"));
		if (ce.list() != null) {
			previousTaskList = (ArrayList) ce.list();
		}
		// for removing the blob reference of reference doc in taskList.
		if (previousTaskList != null && previousTaskList.size() > 0) {
			previousTask = new String[previousTaskList.size()];

			for (int i = 0; i < previousTaskList.size(); i++) {

				tmMultipleTaskMapping = (TmMultipleTaskMapping) previousTaskList
						.get(i);
				previousTask[i] = new Long(tmMultipleTaskMapping
						.getPreviousTaskId()).toString();
			}
		}
		}
		catch (Exception ex) {
			hibernateSession.getTransaction().rollback();
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in getPreviousTasks method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException(ex);

		} finally {
			CommonBaseDao.closeTrans(hibernateSession);
			
		}
		return previousTask;

	}

	public TmWorkflow checkAnotherDraftWK(String npdCategoryId) throws Exception {

		boolean duplicate = false;
		Session hibernateSession = null;
		try
		{
		hibernateSession = CommonBaseDao.beginTrans();
		ArrayList list = null;
		TmWorkflow tmWorkflow=null;
		
		
			Criteria ce = hibernateSession
					.createCriteria(TmWorkflow.class);
			ce.createAlias("npdCategory","npdCategory" );
			ce.add(Restrictions.eq("isactive",new Integer(2)));
			ce = ce.add(Restrictions.eq("npdCategory.npdcatid", Integer.parseInt(npdCategoryId)));
			
			if (ce.list() != null) {
				list = (ArrayList) ce.list();
			}
			if (list != null && list.size() > 0) {
				duplicate = true;
				tmWorkflow = (TmWorkflow) list.get(0);
			}
			return tmWorkflow;
		}
		catch (Exception ex) {
			hibernateSession.getTransaction().rollback();
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in checkAnotherDraftWK method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException(ex);

		} finally {
			CommonBaseDao.closeTrans(hibernateSession);
			
		}
		
		}

	public TmWorkflow createDraftFromFinalized(MasterProjectPlan masterProjectPlan, TmEmployee tmEmployee) throws Exception {

		ArrayList activeWorkflowList = new ArrayList();
		ArrayList stageList = new ArrayList();
		ArrayList taskList = new ArrayList();
		Session hibernateSession = null;
		hibernateSession = CommonBaseDao.beginTrans();
		CommonBaseDao commonBaseDao = (CommonBaseDao) BaseDaoFactory
				.getInstance(DaoConstants.BASE_DAO_CLASS);
		TmWorkflow tmWorkflow = new TmWorkflow();
		TmWorkflow tmWorkflow1 = new TmWorkflow();
		TmWorkflowstage tmWorkflowstage = new TmWorkflowstage();
		TmWorkflowtasks tmWorkflowtasks = new TmWorkflowtasks();
		TmWorkflowstage tmWorkflowstage1 = new TmWorkflowstage();
		TmWorkflowtasks tmWorkflowtasks1 = new TmWorkflowtasks();
		//TmWorkflowstageHistory tmWorkflowstageHistory = new TmWorkflowstageHistory();
		//TmWorkflowtasksHistory tmWorkflowtasksHistory = new TmWorkflowtasksHistory();
		//TmWorkflowHistory tmWorkflowHistory = new TmWorkflowHistory();
		TmNpdcategory npdcategory = new TmNpdcategory();
		Connection conn=null;
		try {
			
			tmWorkflow = (TmWorkflow) commonBaseDao.findById(Integer
					.parseInt(masterProjectPlan.getWorkflowId()),
					HibernateBeansConstants.HBM_WORKFLOW, hibernateSession);
			
				// delete existing draft
	//			delete previous draft for the same npdcategory
				
				ArrayList<TmWorkflow> list=null;
				Criteria ce=hibernateSession.createCriteria(HibernateBeansConstants.HBM_WORKFLOW);
				ce.add(Restrictions.eq("npdCategory", tmWorkflow.getNpdCategory()));
				ce.add(Restrictions.eq("isactive", new Integer(AppConstants.DRAFT_FLAG)));
				list=(ArrayList<TmWorkflow>)ce.list();
				conn=hibernateSession.connection();
				if(list!=null && list.size()>0)
				{
					//delete it
					TmWorkflow temp=(TmWorkflow)list.get(0);
					deleteWorkFlow(conn,temp.getWorkflowid());
				}
			
				//creating new draft
				tmWorkflow = (TmWorkflow) commonBaseDao.findById(Integer
						.parseInt(masterProjectPlan.getWorkflowId()),
						HibernateBeansConstants.HBM_WORKFLOW, hibernateSession);
				if (tmWorkflow != null) {
					BeanUtils.copyProperties(tmWorkflow1, tmWorkflow);
					tmWorkflow1.setWorkflowid(0);
					tmWorkflow1.setIsactive(AppConstants.DRAFT_FLAG);
					commonBaseDao.attachDirty(tmWorkflow1,
							hibernateSession);
					
					HashMap<Long , Long> previousNewMap=new HashMap<Long, Long>();
					//masterProjectPlan.setWorkflowId(new Integer(tmWorkflow
						//	.getWorkflowid()).toString());
					stageList = getStageList(masterProjectPlan,hibernateSession);
					for (int j = 0; j < stageList.size(); j++) {
						tmWorkflowstage1 = new TmWorkflowstage();
						tmWorkflowstage = (TmWorkflowstage) stageList.get(j);
						BeanUtils.copyProperties(tmWorkflowstage1,
								tmWorkflowstage);
						tmWorkflowstage1.setWorkflow(tmWorkflow1);
						tmWorkflowstage1.setStageid(0);
						tmWorkflowstage1.setCreateddate(new Date(System.currentTimeMillis()));
						commonBaseDao.attachDirty(tmWorkflowstage1,
								hibernateSession);

						taskList = getTaskListForACategory(null,
								masterProjectPlan.getWorkflowId(),hibernateSession);
						for (int k = 0; k < taskList.size(); k++) {
							tmWorkflowtasks1 = new TmWorkflowtasks();
							tmWorkflowtasks = (TmWorkflowtasks) taskList.get(k);
							if (tmWorkflowtasks.getStage().getStageid() == tmWorkflowstage
									.getStageid()) {
								BeanUtils
										.copyProperties(tmWorkflowtasks1,
												tmWorkflowtasks);
								tmWorkflowtasks1
										.setStage(tmWorkflowstage1);
								tmWorkflowtasks1.setTaskid(0);
								
								commonBaseDao.attachDirty(
										tmWorkflowtasks1,
										hibernateSession);
								previousNewMap.put(tmWorkflowtasks.getTaskid(), tmWorkflowtasks1.getTaskid());
								
							}

						}

					}
//					copy mapping for the new taskid
					
					ArrayList<Long> keys=new ArrayList<Long>(previousNewMap.keySet());
					ArrayList<TmMultipleTaskMapping> prevMapping=null;
					ce=hibernateSession.createCriteria(TmMultipleTaskMapping.class);
					
					ce.add(Restrictions.in("taskid", keys));
					prevMapping=(ArrayList<TmMultipleTaskMapping>)ce.list();
					
					TmMultipleTaskMapping multipleTaskMapping=null;
					for (int l = 0; l < prevMapping.size(); l++) {
						multipleTaskMapping = new TmMultipleTaskMapping();
						multipleTaskMapping.setTaskid(previousNewMap.get(prevMapping.get(l).getTaskid()));
						
						multipleTaskMapping.setPreviousTaskId(previousNewMap.get(prevMapping.get(l).getPreviousTaskId()));
						
						
						commonBaseDao.attachDirty(multipleTaskMapping,
								hibernateSession);
					}
				}
				
				
				masterProjectPlan.setWorkflowId(String.valueOf(tmWorkflow1.getWorkflowid()));
			
					
		} catch (Exception ex) {
			hibernateSession.getTransaction().rollback();
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in createDraftFromFinalized method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException(ex);

		} finally {
			try{commonBaseDao.closeTrans(hibernateSession);}
			finally{
			if(conn!=null) conn.close();}
		}

		return tmWorkflow;

	}

	public ArrayList<TmWorkflowtasks> fetchNoPrevDefinedTasks(String workflowId) throws NpdException {
		ArrayList<TmWorkflowtasks> tasks=null;
		Connection conn=null;
		ResultSet rs=null;
		try {
			conn=NpdConnection.getConnectionObject();
			String sql=" SELECT TK.* FROM NPD.TM_WORKFLOWTASKS TK WHERE NOT EXISTS"+
						" (SELECT * FROM NPD.TM_MULTIPLETASKMAPPING MAPP WHERE MAPP.TASKID=TK.TASKID)"+
						" AND STAGEID IN (SELECT STG.STAGEID FROM NPD.TM_WORKFLOWSTAGE STG " +
						" WHERE STG.WORKFLOWID="+workflowId+") AND TK.ISFIRSTTASK<>1";
			
			rs=conn.createStatement().executeQuery(sql);
			TmWorkflowtasks dto=null;
			tasks=new ArrayList<TmWorkflowtasks>();
			while(rs.next())
			{
				dto=new TmWorkflowtasks();
				dto.setTaskid(rs.getLong("TASKID"));
				dto.setTaskname(rs.getString("TASKNAME"));
				tasks.add(dto);
			}
			
		} catch (Exception ex) {
			
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in fetchNoPrevDefinedTasks method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException(ex);
		}
		finally
		{
			try {
				DbConnection.closeResultset(rs);
				NpdConnection.freeConnection(conn);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		return tasks;
	}
	
	/*public ArrayList<TmWorkflowtasks> fetchValidPrevTasks(String workflowId,String taskId) {
		ArrayList<TmWorkflowtasks> tasks=null;
		Connection conn=null;
		
		try {
			conn=NpdConnection.getConnectionObject();
			String sql=" SELECT TK.* FROM NPD.TM_WORKFLOWTASKS TK WHERE NOT EXISTS"+
						" (SELECT * FROM NPD.TM_MULTIPLETASKMAPPING MAPP WHERE MAPP.TASKID=TK.TASKID)"+
						" AND STAGEID IN (SELECT STG.STAGEID FROM NPD.TM_WORKFLOWSTAGE STG " +
						" WHERE STG.WORKFLOWID="+workflowId+") AND TK.ISFIRSTTASK<>1";
			
			ResultSet rs=conn.createStatement().executeQuery(sql);
			TmWorkflowtasks dto=null;
			tasks=new ArrayList<TmWorkflowtasks>();
			while(rs.next())
			{
				dto=new TmWorkflowtasks();
				dto.setTaskid(rs.getLong("TASKID"));
				dto.setTaskname(rs.getString("TASKNAME"));
				tasks.add(dto);
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		finally
		{
			try {
				NpdConnection.freeConnection(conn);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		return tasks;
	}*/

	public ArrayList<TmMultipleTaskMapping> getAllMapping(String workflowId) throws NpdException{
		ArrayList<TmMultipleTaskMapping> mapping=null;
		Connection conn=null;
		ResultSet rs=null;
		try {
			conn=NpdConnection.getConnectionObject();
			String sql=" SELECT MAPP.* FROM NPD.TM_MULTIPLETASKMAPPING MAPP"+
						" WHERE TASKID IN (SELECT TASKID FROM NPD.TM_WORKFLOWTASKS"+ 
						" WHERE STAGEID IN (SELECT STG.STAGEID FROM NPD.TM_WORKFLOWSTAGE STG WHERE WORKFLOWID ="+workflowId+"))";
			
			rs=conn.createStatement().executeQuery(sql);
			TmMultipleTaskMapping dto=null;
			mapping=new ArrayList<TmMultipleTaskMapping>();
			while(rs.next())
			{
				dto=new TmMultipleTaskMapping();
				
				dto.setTaskid(rs.getLong("TASKID"));
				dto.setPreviousTaskId(rs.getLong("PREVIOUSTASKID"));
				
				mapping.add(dto);
			}
			
		} catch (Exception ex) {
			
			
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in getAllMapping method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException(ex);
		}
		finally
		{
			try {
				DbConnection.closeResultset(rs);
				NpdConnection.freeConnection(conn);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		return mapping;
	}

	public ArrayList<TmRoles> fetchEmptyRoles(String workflowId) throws NpdException {
		ArrayList<TmRoles> emptyRoles=null;
		Connection conn=null;
		CallableStatement cstmt=null;
		ResultSet rs=null;
		try {
			conn=NpdConnection.getConnectionObject();
			cstmt=conn.prepareCall("{ call NPD.P_EMPTYROLES(?)}");
			cstmt.setLong(1, Long.parseLong(workflowId));
			rs=cstmt.executeQuery();
			TmRoles dto=null;
			emptyRoles=new ArrayList<TmRoles>();
			while(rs.next())
			{
				dto=new TmRoles();
				dto.setRoleid(rs.getInt("ROLEID"));
				dto.setRolename(rs.getString("ROLENAME"));
				emptyRoles.add(dto);
			}
			
		} catch (Exception ex) {
			
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in emptyRoles method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException(ex);
		}
		finally
		{
			try {
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(cstmt);
				NpdConnection.freeConnection(conn);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		return emptyRoles;
	}	
	/*Function		:getPreviousTask
	 * return type	:Long
	 * @Author		:Anil Kumar
	 * Date			:08-02-2011
	 * Purpose		:To fetch previous task id against task id for validation
	 * */
	public Long getPreviousTask(int taskId)throws IOESException
	{
		Long prevTaskId=null;
		Connection connection =null;
		CallableStatement proc =null;		
		ResultSet rs = null;
		try
		{
			String query="SELECT PREVIOUSTASKID FROM NPD.TM_MULTIPLETASKMAPPING";
			query=query+" WHERE TASKID=?";			
			connection = DbConnection.getConnectionObject();
			proc=connection.prepareCall(query);
			proc.setInt(1,taskId);
			rs = proc.executeQuery();
			while(rs.next())
			{
				prevTaskId=rs.getLong("PREVIOUSTASKID");
			}
		}
		catch(Exception ex)
		{
			throw new IOESException("Exception : " + ex.getMessage(),ex);
		}
		finally
		{
			try {
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(proc);
				DbConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new IOESException("Exception : " + e.getMessage(),e);
			}
		}
		return prevTaskId;
	}
	/*Function		:isWorkflowid
	 * return type	:int
	 * @Author		:Anil Kumar
	 * Date			:09-02-2011
	 * Purpose		:To validation previous task status
	 * */
	public int isWorkflowid(Long workflowid,String flag)throws IOESException
	{
		
		Connection connection =null;
		CallableStatement proc =null;		
		ResultSet rs = null;
		int isValid = 0;
		String query=null;
		try
		{
			if("master".equalsIgnoreCase(flag))
			{
				query="SELECT * FROM NPD.VW_VALIDATE_FIRST_LAST WHERE WORKFLOWID = ?";
			}
			else
			{				
				query="SELECT * FROM NPD.VW_PRJ_WORKFLOW_VALIDATION WHERE PROJECTWORKFLOWID=?";
			}
			
			connection = DbConnection.getConnectionObject();
			proc=connection.prepareCall(query);
			proc.setLong(1,workflowid);
			rs = proc.executeQuery();
			while(rs.next())
			{
				if(rs.getString("ISFIRSTTASK").equalsIgnoreCase("1") && rs.getInt("FIRSTTASKPREVCOUNT") > 0)
					isValid = 1;
				if(rs.getString("ISLASTTASK").equalsIgnoreCase("1") && rs.getInt("LASTTASKNEXTCOUNT") > 0)
					isValid = 1;
				if(rs.getInt("FIRSTTASKPREVCOUNT") > 1 && rs.getString("ISFIRSTTASK").equalsIgnoreCase("0"))
					isValid = 1;
				if(rs.getInt("LASTTASKNEXTCOUNT") > 1 && rs.getString("ISLASTTASK").equalsIgnoreCase("0"))
					isValid = 1; 

			}
		}
		catch(Exception ex)
		{
			throw new IOESException("Exception : " + ex.getMessage(),ex);			
		}
		finally
		{
			try {
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(proc);
				DbConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new IOESException("Exception : " + e.getMessage(),e);
			}
		}
		return isValid;
	}			
}
