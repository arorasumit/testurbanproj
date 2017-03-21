/**
 * 
 */
package com.ibm.ioes.npd.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ibm.ioes.npd.beans.MasterProjectPlan;
import com.ibm.ioes.npd.beans.MeetingBean;
import com.ibm.ioes.npd.beans.ReferenceDocBean;
import com.ibm.ioes.npd.exception.NpdException;
import com.ibm.ioes.npd.hibernate.beans.TmEmployee;
import com.ibm.ioes.npd.hibernate.beans.TmMultipleTaskMapping;
import com.ibm.ioes.npd.hibernate.beans.TmReferencedocs;
import com.ibm.ioes.npd.hibernate.beans.TmRoles;
import com.ibm.ioes.npd.hibernate.beans.TmWorkflow;
import com.ibm.ioes.npd.hibernate.beans.TmWorkflowstage;
import com.ibm.ioes.npd.hibernate.beans.TmWorkflowtasks;
import com.ibm.ioes.npd.hibernate.beans.TtrnMeetingschedules;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjecthierarchy;
import com.ibm.ioes.npd.hibernate.dao.BaseDaoFactory;
import com.ibm.ioes.npd.hibernate.dao.CommonBaseDao;
import com.ibm.ioes.npd.hibernate.dao.DaoConstants;
import com.ibm.ioes.npd.hibernate.dao.MasterProjectPlanDao;
import com.ibm.ioes.npd.hibernate.dao.MeetingDao;
import com.ibm.ioes.npd.hibernate.dao.NpdUserDao;
import com.ibm.ioes.npd.hibernate.dao.ReferenceDocDao;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.HibernateBeansConstants;
import com.ibm.ioes.npd.utilities.NpdConnection;

/**
 * @author Disha
 * 
 */
public class MasterProjectPlanModel extends CommonBaseModel {

	/**
	 * This method calls the method to initialise the Master Project Plan page.
	 * 
	 * @param MasterProjectPlan
	 * @return
	 * @throws Exception
	 */

	public MasterProjectPlan viewMasterProjectPlan(
			MasterProjectPlan masterProjectPlan) throws Exception {

		ArrayList npdCategoryList = null;
		ArrayList stageList = null;
		ArrayList taskList = null;
		ArrayList roleList = null;
		TmWorkflow tmWorkflow = new TmWorkflow();
		MasterProjectPlanDao masterProjectPlanDao = (MasterProjectPlanDao) BaseDaoFactory
				.getInstance(DaoConstants.MASTER_PROJECT_PLAN_DAO);
		CommonBaseDao commonBaseDao = (CommonBaseDao) BaseDaoFactory
				.getInstance(DaoConstants.BASE_DAO_CLASS);
		Session hibernateSession = null;
		try
		{
		hibernateSession = CommonBaseDao.beginTrans();

		npdCategoryList = commonBaseDao.getAllEntriesInATable(hibernateSession,
				HibernateBeansConstants.HBM_NPD_CATEGORY);

		CommonBaseDao.closeTrans(hibernateSession);
		hibernateSession = CommonBaseDao.beginTrans();
		if (npdCategoryList != null && npdCategoryList.size() > 0) {
			masterProjectPlan.setNpdCategoryList(npdCategoryList);
		}
		//masterProjectPlan.setExistingWorkflowList(masterProjectPlanDao
			//	.getActiveWorkflow(masterProjectPlan));
		masterProjectPlan.setExistingWorkflowList(masterProjectPlanDao
				.getWorkingWorkflows(hibernateSession,masterProjectPlan));

		if (masterProjectPlan.getWorkflowId() != null
				&& !masterProjectPlan.getWorkflowId().equalsIgnoreCase(
						AppConstants.INI_VALUE)) {
			tmWorkflow = (TmWorkflow) (commonBaseDao.findById(Integer
					.parseInt(masterProjectPlan.getWorkflowId()),
					HibernateBeansConstants.HBM_WORKFLOW, hibernateSession));
			masterProjectPlan.setWorkflowDesc(tmWorkflow.getWorkflowdesc());
			if (tmWorkflow != null)
				masterProjectPlan.setNpdCategoryId(new Integer(tmWorkflow
						.getNpdCategory().getNpdcatid()).toString());
			stageList = masterProjectPlanDao.getStageList(masterProjectPlan,hibernateSession);
			if (stageList != null && stageList.size() > 0) {
				masterProjectPlan.setStageList(stageList);
			}
			
			
			taskList =  masterProjectPlanDao.getTaskListForACategory(null,
					new Integer(tmWorkflow.getWorkflowid())
			.toString(),hibernateSession);
			CommonBaseDao.closeTrans(hibernateSession);
			
			if(taskList!=null&&taskList.size()>0)
			{
				masterProjectPlan.setTaskList(taskList);
				masterProjectPlan.setTaskListLength(String.valueOf(taskList.size()));
			}else
			{
				masterProjectPlan.setTaskListLength("0");
			}
			
			//set first task
			
			//set last task
			
			TmWorkflowtasks first=null;
			TmWorkflowtasks last=null;
			
			ArrayList<TmWorkflowtasks> tasks=masterProjectPlanDao.fetchFirstAndLastTask(masterProjectPlan.getWorkflowId());
			if(tasks!=null)
			{
				TmWorkflowtasks temp=tasks.get(0);
				if(temp!=null)
				{
					first=temp;
				}
				temp=tasks.get(1);
				if(temp!=null)
				{
					last=temp;
				}
			}
			masterProjectPlan.setFirstTask(first);
			masterProjectPlan.setLastTask(last);
			
			//fetching no prevous defined tasks
			ArrayList<TmWorkflowtasks> noPrevDefinedTasks=null;
			noPrevDefinedTasks=masterProjectPlanDao.fetchNoPrevDefinedTasks(masterProjectPlan.getWorkflowId());
			masterProjectPlan.setNoPreviousDefinedTasks(noPrevDefinedTasks);
			
			
			
			//fetch from alloted roles the roles who hae no employees int it.
			ArrayList<TmRoles> emptyRoles=null;
			//Comment:Below code commented it is not in use
			//emptyRoles=masterProjectPlanDao.fetchEmptyRoles(masterProjectPlan.getWorkflowId());
			//masterProjectPlan.setEmptyRoles(emptyRoles);
			//Comment:Above code commented it is not in use
			
			hibernateSession = CommonBaseDao.beginTrans();
			/*roleList =
				 commonBaseDao.getAllEntriesInATable(hibernateSession,
				 HibernateBeansConstants.HBM_ROLE);*/
			Criteria ce=hibernateSession.createCriteria(TmRoles.class);
			//ce.add(Restrictions.eq("isactive", 1));
			ce.addOrder(Order.asc("rolename"));
			ArrayList<TmRoles> list=(ArrayList<TmRoles>)ce.list();
			if(list!=null)
			{
				roleList=list;
			}
			
			
			CommonBaseDao.closeTrans(hibernateSession);
			if (roleList != null && roleList.size() > 0) {

				masterProjectPlan.setStakeHolderList(roleList);
			}
		

		}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in viewMasterProjectPlan method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException(ex);
		}finally
		{
			try{CommonBaseDao.closeTrans(hibernateSession);}
			catch(Exception ex){ex.printStackTrace();}
		}
		
		/*
		 * stageList = masterProjectPlanDao.getStageList(masterProjectPlan);
		 * taskList = commonBaseDao.getAllEntriesInATable(hibernateSession,
		 * HibernateBeansConstants.HBM_WORKFLOW_TASK); employeeList =
		 * commonBaseDao.getAllEntriesInATable(hibernateSession,
		 * HibernateBeansConstants.HBM_EMPLOYEE); if (stageList != null &&
		 * stageList.size() > 0) { masterProjectPlan.setStageList(stageList); if
		 * (taskList != null && taskList.size() > 0) {
		 * masterProjectPlan.setTaskList(taskList); } if (employeeList != null &&
		 * employeeList.size() > 0) {
		 * masterProjectPlan.setStakeHolderList(employeeList); } }
		 * masterProjectPlan.setTask(AppConstants.INI_VALUE);
		 * masterProjectPlan.setTaskDescription(AppConstants.INI_VALUE);
		 * masterProjectPlan.setPlannedDuration(AppConstants.INI_VALUE);
		 * masterProjectPlan.setRemarks(AppConstants.INI_VALUE);
		 * masterProjectPlan.setStageDescription(AppConstants.INI_VALUE);
		 * masterProjectPlan.setAttachment(false);
		 * masterProjectPlan.setFirst(false); masterProjectPlan.setLast(false);
		 * masterProjectPlan.setStageId(AppConstants.SELECT_OPTION);
		 * masterProjectPlan.setStakeHolderId(AppConstants.SELECT_OPTION);
		 * masterProjectPlan.setTaskId(AppConstants.SELECT_OPTION);
		 */

		return masterProjectPlan;
	}

	/**
	 * This method calls the method to initialise the Master Project Plan
	 * Version History.
	 * 
	 * @param MasterProjectPlan
	 * @return
	 * @throws Exception
	 */

	public MasterProjectPlan viewMasterProjectPlanHistory(
			MasterProjectPlan masterProjectPlan) throws Exception {

		ArrayList npdCategoryList = null;
		CommonBaseDao commonBaseDao = (CommonBaseDao) BaseDaoFactory
				.getInstance(DaoConstants.BASE_DAO_CLASS);
		Session hibernateSession = null;
		try
		{
		hibernateSession = CommonBaseDao.beginTrans();

		npdCategoryList = commonBaseDao.getAllEntriesInATable(hibernateSession,
				HibernateBeansConstants.HBM_NPD_CATEGORY);
		masterProjectPlan.setNpdCategoryList(npdCategoryList);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in viewMasterProjectPlanHistory method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException(ex);
		}
		finally
		{
			CommonBaseDao.closeTrans(hibernateSession);
		}

		return masterProjectPlan;
	}

	/**
	 * This method add the stage in DB.
	 * 
	 * @param MasterProjectPlan
	 * @return
	 * @throws Exception
	 */

	public boolean saveUpdateStage(MasterProjectPlan masterProjectPlan,
			TmEmployee tmEmployee) throws Exception {

		MasterProjectPlanDao masterProjectPlanDao = (MasterProjectPlanDao) BaseDaoFactory
				.getInstance(DaoConstants.MASTER_PROJECT_PLAN_DAO);

		boolean insert = false;
		try{
		insert = masterProjectPlanDao.saveUpdateStage(masterProjectPlan, tmEmployee);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in saveUpdateStage method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException(ex);
		}

		return insert;

	}

	/**
	 * This method update the stage in DB.
	 * 
	 * @param MasterProjectPlan
	 * @return
	 * @throws Exception
	 */

/*	public boolean updateStage(MasterProjectPlan masterProjectPlan,
			TmEmployee tmEmployee) throws Exception {

		MasterProjectPlanDao masterProjectPlanDao = (MasterProjectPlanDao) BaseDaoFactory
				.getInstance(DaoConstants.MASTER_PROJECT_PLAN_DAO);

		boolean insert = false;

		insert = masterProjectPlanDao.saveUpdateStage(masterProjectPlan, tmEmployee);

		return insert;

	}*/
	/**
	 * This method add the Workflow in DB.
	 * 
	 * @param MasterProjectPlan
	 * @return
	 * @throws Exception
	 */
//SH_RAV called from saveWorkflow in Action
	public TmWorkflow saveWorkflow(MasterProjectPlan masterProjectPlan,
			TmEmployee tmEmployee) throws Exception {

		MasterProjectPlanDao masterProjectPlanDao = (MasterProjectPlanDao) BaseDaoFactory
				.getInstance(DaoConstants.MASTER_PROJECT_PLAN_DAO);
		TmWorkflow tmWorkflow = new TmWorkflow();
		try{
		tmWorkflow = masterProjectPlanDao.saveWorkflow(masterProjectPlan,
				tmEmployee);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in saveWorkflow method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException(ex);
		}

		return tmWorkflow;

	}
	
//	SH_RAV called from saveWorkflow in Action
	public TmWorkflow finalize(MasterProjectPlan masterProjectPlan,
			TmEmployee tmEmployee) throws Exception {

		MasterProjectPlanDao masterProjectPlanDao = (MasterProjectPlanDao) BaseDaoFactory
				.getInstance(DaoConstants.MASTER_PROJECT_PLAN_DAO);
		TmWorkflow tmWorkflow = new TmWorkflow();
		try{
		tmWorkflow = masterProjectPlanDao.finalize(masterProjectPlan,
				tmEmployee);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in finalize method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException(ex);
		}

		return tmWorkflow;

	}

	/**
	 * This method add the Task in DB.
	 * 
	 * @param MasterProjectPlan
	 * @return
	 * @throws Exception
	 */

	public boolean saveUpdateTask(MasterProjectPlan masterProjectPlan,
			TmEmployee tmEmployee) throws Exception {

		MasterProjectPlanDao masterProjectPlanDao = (MasterProjectPlanDao) BaseDaoFactory
				.getInstance(DaoConstants.MASTER_PROJECT_PLAN_DAO);

		boolean insert = false;
		try
		{
		insert = masterProjectPlanDao.saveUpdateTask(masterProjectPlan, tmEmployee);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in saveUpdateTask method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException(ex);
		}

		return insert;

	}
	/**
	 * This method add the Task in DB.
	 * 
	 * @param MasterProjectPlan
	 * @return
	 * @throws Exception
	 */
	
	//rohit verma new cr
	public boolean deleteTask(MasterProjectPlan masterProjectPlan,ArrayList DeleteTaskIdList) throws Exception 
	{

		MasterProjectPlanDao masterProjectPlanDao = (MasterProjectPlanDao) BaseDaoFactory.getInstance(DaoConstants.MASTER_PROJECT_PLAN_DAO);
		boolean delete = false;
		try
		{
			for(int i=0;i<DeleteTaskIdList.size();i++)
			{
				//call delete method	
				String selectedTaskID;
				selectedTaskID=DeleteTaskIdList.get(i).toString();
				delete = masterProjectPlanDao.deleteTask(selectedTaskID);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in deleteTask method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException(ex);
		}

		return delete;

	}	
	
	

	/**
	 * This method outputs a a List of Tasks.
	 * 
	 * @param MasterProjectPlan
	 * @return
	 * @throws Exception
	 */

	public MasterProjectPlan viewVersionHistory(
			MasterProjectPlan masterProjectPlan) throws Exception {

		MasterProjectPlanDao masterProjectPlanDao = (MasterProjectPlanDao) BaseDaoFactory
				.getInstance(DaoConstants.MASTER_PROJECT_PLAN_DAO);
		ArrayList taskList = null;
		try
		{
		taskList = masterProjectPlanDao
				.getMasterProjectPlanVersionForACategory(masterProjectPlan
						.getNpdCategoryId());
		masterProjectPlan.setTaskList(taskList);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in viewVersionHistory method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException(ex);
		}
		return masterProjectPlan;

	}

	public void editFinalizedWorkflow(MasterProjectPlan masterProjectPlan, TmEmployee tmEmployee)  throws Exception {

		//	copy finalized to draft
		//aletr workflowid in masterProjectPlan
		
	
	
		MasterProjectPlanDao masterProjectPlanDao = (MasterProjectPlanDao) BaseDaoFactory
				.getInstance(DaoConstants.MASTER_PROJECT_PLAN_DAO);
		TmWorkflow tmWorkflow = new TmWorkflow();
	
		try
		{
		tmWorkflow = masterProjectPlanDao.createDraftFromFinalized(masterProjectPlan,
				tmEmployee);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in editFinalizedWorkflow method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException(ex);
		}
		

	}
	public ArrayList<TmWorkflowtasks> fetchValidPrevTasks(String workflowId,String taskId) throws NpdException{
		ArrayList<TmWorkflowtasks> validtasks=null;
		validtasks=new ArrayList<TmWorkflowtasks>();
		
		Session hibernateSession = null;
		try {
			hibernateSession = CommonBaseDao.beginTrans();
		MasterProjectPlanDao dao=new MasterProjectPlanDao();
		ArrayList<TmWorkflowtasks> tasks=dao.getTaskListForACategory("", workflowId,hibernateSession);
		ArrayList<TmMultipleTaskMapping> mapp=dao.getAllMapping(workflowId);
		
		HashMap<Long,TmWorkflowtasks> taskMap=new HashMap<Long,TmWorkflowtasks>();
		for (TmWorkflowtasks task : tasks) {
			Long key=task.getTaskid();
			taskMap.put(key, task);
		}
		
		HashMap<Long,ArrayList<TmWorkflowtasks>> taskToNextTasksMap=new HashMap<Long,ArrayList<TmWorkflowtasks>>();
		for (TmMultipleTaskMapping mapping : mapp) {
			long key=mapping.getPreviousTaskId();
			ArrayList<TmWorkflowtasks> temp=taskToNextTasksMap.get(key);
			if(temp==null)
			{
				temp=new ArrayList<TmWorkflowtasks>();
				taskToNextTasksMap.put(key, temp);
			}
			temp.add(taskMap.get(mapping.getTaskid()));
		}
		
		ArrayList<Long> queue=new ArrayList<Long>();
		int index_queue=-1;
		HashMap<Long,Long> toBeRemoved=new HashMap<Long,Long>();
		queue.add(new Long(taskId));
		/*while(index_queue<queue.size()-1)
		{
			index_queue++;
			Long id=(Long)queue.get(index_queue);
			toBeRemoved.put(id, id);
			ArrayList<TmWorkflowtasks> temp=taskToNextTasksMap.get(id);
			if(temp!=null && temp.size()>0)
			{
				for (TmWorkflowtasks workflowtasks : temp) {
					queue.add(workflowtasks.getTaskid());
				}
			}
		}*/
		HashSet<Long> allAlreadyInQueue=new HashSet<Long>(); 
		allAlreadyInQueue.add(new Long(taskId));
		while(index_queue<queue.size()-1)
		{
			index_queue++;
			Long id=(Long)queue.get(index_queue);
			toBeRemoved.put(id, id);
			ArrayList<TmWorkflowtasks> temp=taskToNextTasksMap.get(id);
			if(temp!=null && temp.size()>0)
			{
				for (TmWorkflowtasks workflowtasks : temp) {
					if(!allAlreadyInQueue.contains(new Long(workflowtasks.getTaskid())))
					{
						queue.add(workflowtasks.getTaskid());
						allAlreadyInQueue.add(new Long(workflowtasks.getTaskid()));
					}
				}
			}
		}
		
		for (TmWorkflowtasks task : tasks) {
			Long id=task.getTaskid();
			Long val=toBeRemoved.get(id);
			if(val==null)
			{
				validtasks.add(task);
			}
		}
		
		
		
		
		
		} catch (Exception ex) {
			
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in taskToNextTasksMap method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException(ex);
		}
		finally
		{
			try {
				CommonBaseDao.closeTrans(hibernateSession);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		return validtasks;
	}	
	/*Function		:getPreviousTask
	 * return type	:Long
	 * @Author		:Anil Kumar
	 * Date			:08-02-2011
	 * Purpose		:To fetch previous task id against task id for validation
	 * */
	public Long getPreviousTask(int taskId)throws Exception
	{
		MasterProjectPlanDao objDao=new MasterProjectPlanDao();
		Long prevTaskId=null;
		try{
			prevTaskId=objDao.getPreviousTask(taskId);
		}
		catch(Exception ex)
		{
			System.err.println(ex.getMessage());
		}
		return prevTaskId;
	}
}
