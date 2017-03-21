package com.ibm.ioes.npd.model;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import javax.swing.text.StyledEditorKit.BoldAction;

import org.apache.struts.action.ActionMessage;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionMessages;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.apache.struts.upload.FormFile;
import com.ibm.ioes.npd.beans.ProductCreationBean;
import com.ibm.ioes.npd.beans.ProjectPlanInstanceBean;
import com.ibm.ioes.npd.beans.RoleEmployeeBean;
import com.ibm.ioes.npd.beans.TaskInstanceBean;
import com.ibm.ioes.npd.beans.UploadProductPlanBean;
import com.ibm.ioes.npd.exception.NpdException;
import com.ibm.ioes.npd.hibernate.beans.MyToDoListDto;
import com.ibm.ioes.npd.hibernate.beans.PlanExcelUploadDto;
import com.ibm.ioes.npd.hibernate.beans.TmEmployee;
import com.ibm.ioes.npd.hibernate.beans.TmRoles;
import com.ibm.ioes.npd.hibernate.beans.TmstProjecthierarchytasksflow;
import com.ibm.ioes.npd.hibernate.beans.TtrnMeetingattendies;
import com.ibm.ioes.npd.hibernate.beans.TtrnMeetingschedules;
import com.ibm.ioes.npd.hibernate.beans.TtrnProject;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjecthierarchy;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjectworkflow;
import com.ibm.ioes.npd.hibernate.dao.AccessMatrixDao;
import com.ibm.ioes.npd.hibernate.dao.AttachEditProjectPlanDao;
import com.ibm.ioes.npd.hibernate.dao.BaseDaoFactory;
import com.ibm.ioes.npd.hibernate.dao.CommonBaseDao;
import com.ibm.ioes.npd.hibernate.dao.DaoConstants;
import com.ibm.ioes.npd.hibernate.dao.WorkFlowDao;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.ExcelCreator;
import com.ibm.ioes.npd.utilities.FindCycles;
import com.ibm.ioes.npd.utilities.HibernateBeansConstants;
import com.ibm.ioes.npd.utilities.Messages;
import com.ibm.ioes.npd.utilities.NpdConnection;
import com.ibm.ioes.npd.utilities.PagingSorting;
import com.ibm.ioes.npd.utilities.SendMailUtility;
import com.ibm.ioes.npd.utilities.Utility;
//import com.ibm.ws.wim.bridge.assembler.datagraph.SearchDataGraphAssembler;

public class AttachEditProjectPlanModel {

	final public static String TASK_LIST_ASSIGN_USERID_MODE="TASK_LIST_ASSIGN_USERID_MODE";
	 private static final String baseDirPath = Messages.getMessageValue("TEMPORARY_DIR_PATH");
	public void setInstanceTasksView(ProjectPlanInstanceBean formBean,String mode) throws NpdException {

		Session hibernateSession = null;
		Connection connection=null;
		
		try
		{
		connection=NpdConnection.getConnectionObject();
		ArrayList<TtrnProjecthierarchy> stageList=null;
		ArrayList<TtrnProjecthierarchy> tasksView=null;
		
		AttachEditProjectPlanDao daoObj=new AttachEditProjectPlanDao(); 
		
		hibernateSession = CommonBaseDao.beginTrans();
		
		
		
		//fecthing stages
		ProjectPlanInstanceBean stageSearchDto=new ProjectPlanInstanceBean();
		stageSearchDto.setProjectWorkflowId(formBean.getProjectWorkflowId());
		
		PagingSorting stagesPS=new PagingSorting(false,true);//since paging not required hence false
		formBean.setStagesPS(stagesPS);
		
		
		stagesPS.setDefaultifNotPresent("stageName", PagingSorting.increment, 1);
		stageSearchDto.setStagesPS(stagesPS);
		
		stageList=daoObj.getStages(connection,stageSearchDto);
		formBean.setStageList(stageList);
		
		
		//fecthing taskss
		ProjectPlanInstanceBean taskSearchDto=new ProjectPlanInstanceBean();
		taskSearchDto.setProjectWorkflowId(formBean.getProjectWorkflowId());
		
		taskSearchDto.setSearchTaskName(formBean.getSearchTaskName());
		String selectedStageId=formBean.getSelectedStageId();
		if(!(selectedStageId==null || "".equals(selectedStageId) || "-1".equals(selectedStageId)))
		{
			taskSearchDto.setSearchTaskOfStageId(selectedStageId);	
		}
		taskSearchDto.setSearchTask_roleHolder(formBean.getSearchTask_roleHolder());
		taskSearchDto.setSearchTask_assignedTo(formBean.getSearchTask_assignedTo());
		if(formBean.getTaskOption()==null || "".equals(formBean.getTaskOption()))
		{
			formBean.setTaskOption("ALL");
		}
		taskSearchDto.setTaskOption(formBean.getTaskOption());
		
		
		PagingSorting tasksPS=formBean.getTasksPS();
		if(tasksPS==null)
		{
			formBean.setTasksPS(tasksPS);
		}
		tasksPS.setPagingSorting(true, true);
		
		tasksPS.setDefaultifNotPresent("special2", PagingSorting.increment, 1);
		taskSearchDto.setTasksPS(tasksPS);		
		
		tasksView=daoObj.getTasks(connection,taskSearchDto);
		
		if(TASK_LIST_ASSIGN_USERID_MODE.equals(mode))
		{
			Long[] roles=new Long[tasksView.size()];  
			int i=0;
			for (TtrnProjecthierarchy projecthierarchy : tasksView) {
				roles[i++]=projecthierarchy.getTaskstakeholder();
			}
			ArrayList<TmEmployee> employees=null;
			employees=daoObj.getEmployeesRoleList(connection,roles);
			
			HashMap<Long,ArrayList<TmEmployee>> mapRole_Employees=new HashMap<Long, ArrayList<TmEmployee>>();
			for (TmEmployee employee : employees) {
				long roleId=employee.getOneRoleId();
				ArrayList<TmEmployee> temp=mapRole_Employees.get(new Long(roleId));
				if(temp==null)
				{
					temp=new ArrayList<TmEmployee>();
				}
				temp.add(employee);
				mapRole_Employees.put(new Long(roleId), temp);
			}
			
			
			for (TtrnProjecthierarchy task : tasksView) {
				task.setAssignedUserList(mapRole_Employees.get(task.getTaskstakeholder()));
			}
		}
		formBean.setTasksView(tasksView);
		setCheckListView(hibernateSession,formBean);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in setProjectPlanInstanceView method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in setProjectPlanInstanceView method of "
					+ this.getClass().getSimpleName()+"  inserting mapping ") ;
		}
		finally{
			try {
				NpdConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				AppConstants.NPDLOGGER.error(e.getMessage()
						+ " Exception occured in setProjectPlanInstanceView method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(e));
				throw new NpdException("Exception occured in setProjectPlanInstanceView method of "
						+ this.getClass().getSimpleName());
			}finally{
			hibernateSession.close();
			}
		}
	}
	
	public void setCheckListView(ProjectPlanInstanceBean formBean) throws NpdException {

		Session hibernateSession = null;
		
		
		try
		{
		hibernateSession = CommonBaseDao.beginTrans();
		
		setCheckListView(hibernateSession,formBean);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in setCheckListView method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in setCheckListView method of "
					+ this.getClass().getSimpleName()) ;
		}
		finally{
			hibernateSession.close();
		}
	}

	public int finalizeProjectPlanInstance(ProjectPlanInstanceBean formBean) throws NpdException {
		int status=-1;
		Connection conn = null;
		try
		{	
			//genarate start date(target) for each task
			AttachEditProjectPlanDao daoObj=new AttachEditProjectPlanDao(); 
			conn=NpdConnection.getConnectionObject();
			conn.setAutoCommit(false);
			String projectId=formBean.getProjectId();
			//String projectWorkflowId;
			//TtrnProjectworkflow projectWorkFlow=daoObj.getActiveProjectWorkflowId(hibernateSession,formBean.getProjectId());
			//projectWorkflowId=String.valueOf(projectWorkFlow.getProjectworkflowid());
			//projectWorkflowId=daoObj.get0
			int dao_status=0;
			String attachStatus=formBean.getAttachMode();
			
			
			dao_status=daoObj.setTargetDates(conn,projectId);
			if(dao_status==1)
			{
				status=1;
				conn.commit();
			}
			else
			{
				status=-1;
			}
			return status;
		
		}
		catch(Exception ex)
		{
			try {
				conn.rollback();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				AppConstants.NPDLOGGER.error(e.getMessage()
						+ " Exception occured in finalizeProjectPlanInstance method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(e));
				throw new NpdException("Exception occured in finalizeProjectPlanInstance method of "
						+ this.getClass().getSimpleName()+"  inserting mapping ") ;
			}
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in finalizeProjectPlanInstance method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in finalizeProjectPlanInstance method of "
					+ this.getClass().getSimpleName()+"  inserting mapping ") ;
		}
		finally{
			try {
				NpdConnection.freeConnection(conn);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				AppConstants.NPDLOGGER.error(e.getMessage()
						+ " Exception occured in finalizeProjectPlanInstance method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(e));
				throw new NpdException("Exception occured in finalizeProjectPlanInstance method of "
						+ this.getClass().getSimpleName()+"  inserting mapping ") ;
			}
		}
		
	}
	
	public int finalizeAgainProjectPlanInstance(ProjectPlanInstanceBean formBean) throws NpdException {
		int status=-1;
		Connection conn = null;
		try
		{	
			//genarate start date(target) for each task
			AttachEditProjectPlanDao daoObj=new AttachEditProjectPlanDao(); 
			conn=NpdConnection.getConnectionObject();
			conn.setAutoCommit(false);
			String projectId=formBean.getProjectId();
			//String projectWorkflowId;
			//TtrnProjectworkflow projectWorkFlow=daoObj.getActiveProjectWorkflowId(hibernateSession,formBean.getProjectId());
			//projectWorkflowId=String.valueOf(projectWorkFlow.getProjectworkflowid());
			//projectWorkflowId=daoObj.get0
			int dao_status=0;
			dao_status=daoObj.setFewTargetDates(conn,projectId);
			if(dao_status==1)
			{
				status=1;
				conn.commit();
			}
			else
			{
				status=-1;
			}
		}
		catch(Exception ex)
		{
			try {
				conn.rollback();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				AppConstants.NPDLOGGER.error(e.getMessage()
						+ " Exception occured in finalizeProjectPlanInstance method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(e));
				throw new NpdException("Exception occured in finalizeProjectPlanInstance method of "
						+ this.getClass().getSimpleName()+"  inserting mapping ") ;
			}
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in finalizeProjectPlanInstance method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in finalizeProjectPlanInstance method of "
					+ this.getClass().getSimpleName()+"  inserting mapping ") ;
		}
		finally{
			try {
				NpdConnection.freeConnection(conn);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				AppConstants.NPDLOGGER.error(e.getMessage()
						+ " Exception occured in finalizeProjectPlanInstance method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(e));
				throw new NpdException("Exception occured in finalizeProjectPlanInstance method of "
						+ this.getClass().getSimpleName()+"  inserting mapping ") ;
			}
		}
		return status;
	}
	
	public HashMap revertBeforeFinalize(ProjectPlanInstanceBean formBean) throws NpdException{
		HashMap map=new HashMap();
		Connection conn=null;
		try
		{	
			conn=NpdConnection.getConnectionObject();
			conn.setAutoCommit(false);
			AttachEditProjectPlanDao daoObj=new AttachEditProjectPlanDao();
			map=daoObj.revertBeforeFinalize(conn,formBean);
			
			//ArrayList<TtrnProjecthierarchy> list=(ArrayList<TtrnProjecthierarchy>)map.get("REVERTED_TASKS_LIST");
			conn.commit();
		
		}
		catch(Exception ex)
		{
			try {
				conn.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ex.printStackTrace();
			
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in revertBeforeFinalize method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in revertBeforeFinalize method of "
					+ this.getClass().getSimpleName()) ;
		}
		finally{
			try {
				
				NpdConnection.freeConnection(conn);
			} catch (Exception e) {
				e.printStackTrace();
				AppConstants.NPDLOGGER.error(e.getMessage()
						+ " Exception occured in revertBeforeFinalize method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(e));
				throw new NpdException("Exception occured in revertBeforeFinalize method of "
						+ this.getClass().getSimpleName()) ;
			}
		}
		return map;
	}
	
	public void setCreateNewTaskInstanceView(ProjectPlanInstanceBean formBean) throws NpdException{
		Session hibernateSession = null;
		try
		{	
			//genarate start date(target) for each task
			AttachEditProjectPlanDao daoObj=new AttachEditProjectPlanDao(); 
			hibernateSession = CommonBaseDao.beginTrans();
			
			TaskInstanceBean taskInstanceBean=formBean.getTaskInstanceBean();
			
			ArrayList<TtrnProjecthierarchy> stageList=null;
		    ArrayList<TmRoles> taskstakeholderList=null;
		    ArrayList<TmEmployee> taskstakeholderUserList=null;
		    ArrayList<TtrnProjecthierarchy> prevTaskList=null;
		    ArrayList templateList=null;
			
		    
			
			stageList=daoObj.getStages(hibernateSession,formBean.getProjectWorkflowId());
			taskstakeholderList=daoObj.getRoles(hibernateSession);
			taskstakeholderUserList=daoObj.getUsersOfRole(hibernateSession,"-1");
			prevTaskList=getPossiblePreviousTasks(hibernateSession,formBean.getProjectWorkflowId(),"-1");
			templateList=daoObj.getTemplateDocList(hibernateSession);
			
			
			
			taskInstanceBean.setStageList(stageList);
			taskInstanceBean.setTaskstakeholderList(taskstakeholderList);
			taskInstanceBean.setTaskstakeholderUserList(taskstakeholderUserList);
			//taskInstanceBean.setPrevTaskList(prevTaskList);
			taskInstanceBean.setTemplateList(templateList);
			
			taskInstanceBean.setPrevTaskUnSelectedList(prevTaskList);
			
		
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in setProjectPlanTaskInstance method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in setProjectPlanTaskInstance method of "
					+ this.getClass().getSimpleName()) ;
		}
		finally{
			hibernateSession.close();
		}
		
	}
	
	public void setProjectPlanTaskInstance(ProjectPlanInstanceBean formBean,String mode) throws NpdException{
		Session hibernateSession = null;
		try
		{	
			//genarate start date(target) for each task
			AttachEditProjectPlanDao daoObj=new AttachEditProjectPlanDao(); 
			hibernateSession = CommonBaseDao.beginTrans();
			
			String projectWorkflowId=formBean.getProjectWorkflowId();
			String currentTaskId=formBean.getSelectedTaskId();
			TtrnProjecthierarchy ttrnProjecthierarchy=daoObj.getTaskInstance(hibernateSession,projectWorkflowId,currentTaskId);
			
			
			//trabsfer from dto to formBean
			TaskInstanceBean taskInstanceBean=formBean.getTaskInstanceBean();
			if("view".equals(mode))
			{
				taskInstanceBean.setTaskMode("view");
			}
			else if("update".equals(mode))
			{
				taskInstanceBean.setTaskMode("update");	
			}
			
			String currenttaskid=String.valueOf(ttrnProjecthierarchy.getCurrenttaskid());
			String currentstageid=String.valueOf(ttrnProjecthierarchy.getCurrentstageid());
			String taskname=ttrnProjecthierarchy.getTaskname();
			String taskdesc=ttrnProjecthierarchy.getTaskdesc();
			String taskTasktype=null;
			Integer taskType=ttrnProjecthierarchy.getTaskTasktype();
			if(taskType!=null && taskType==1)
			{
				taskTasktype="Y";
			}
			else if(taskType!=null && taskType==0)
			{
				taskTasktype="N";
			}
			
			String rejectionAllowed=null;
			Integer rejectionAllowedInteger=ttrnProjecthierarchy.getRejectionAllowed();
			if(rejectionAllowedInteger!=null && rejectionAllowedInteger==1)
			{
				rejectionAllowed="Y";
			}
			else if(rejectionAllowedInteger!=null && rejectionAllowedInteger==0)
			{
				rejectionAllowed="N";
			}
			
			String isEmailRequired=null;
			Integer meailReq=ttrnProjecthierarchy.getTaskIsemailtemplate();
			if(meailReq!=null && meailReq==1)
			{
				isEmailRequired="Y";
			}
			else if(meailReq!=null && meailReq==0)
			{
				isEmailRequired="N";
			}
			
			String taskstakeholder=String.valueOf(ttrnProjecthierarchy.getTaskstakeholder());
			String assignedtouserid=String.valueOf(ttrnProjecthierarchy.getAssignedtouserid());
			
			String isfirsttask=null;
			int temp_isfirsttask=ttrnProjecthierarchy.getIsfirsttask();
			if(temp_isfirsttask==0)
			{
				isfirsttask="N";
			}
			else if(temp_isfirsttask==1)
			{
				isfirsttask="Y";
			}
			
			String islasttask=null;
			int temp_islasttask=ttrnProjecthierarchy.getIslasttask();
			if(temp_islasttask==0)
			{
				islasttask="N";
			}
			else if(temp_islasttask==1)
			{
				islasttask="Y";
			}
			
			ArrayList<TmstProjecthierarchytasksflow> task_prevTaskList=ttrnProjecthierarchy.getPrevTaskList();
			String[] prevTaskIds=new String[task_prevTaskList.size()];
			int i=0;
			for (TmstProjecthierarchytasksflow projecthierarchytasksflow : task_prevTaskList) {
				prevTaskIds[i++]=String.valueOf(projecthierarchytasksflow.getPrevtaskid());
			}
			
			//for fetching selected Prev Task
			/*HashMap<Long,TmstProjecthierarchytasksflow> Task_id_to_bean=new HashMap<Long, TmstProjecthierarchytasksflow>();
			for (TmstProjecthierarchytasksflow prevTask : task_prevTaskList) 
			{
				Task_id_to_bean.put(prevTask.getPrevtaskid(), prevTask);
			}
						
			ArrayList<TmstProjecthierarchytasksflow> selectedPrevTasks=new ArrayList<TmstProjecthierarchytasksflow>(Task_id_to_bean.values());
			//Collections.sort(selectedPrevTasks, new TaskNameComparator1());
			
			ttrnProjecthierarchy.setCurPrevTasks(selectedPrevTasks);	*/	
						
			String taskduration=String.valueOf(ttrnProjecthierarchy.getTaskduration());
			
			String taskIsattachment=null;
			int temp_taskIsattachment=ttrnProjecthierarchy.getTaskIsattachment();
			if(temp_taskIsattachment==0)
			{
				taskIsattachment="N";
			}
			else if(temp_taskIsattachment==1)
			{
				taskIsattachment="Y";
			}
			String taskReferencedocid=String.valueOf(ttrnProjecthierarchy.getTaskReferencedocid());
			String taskTaskinstructionremarks=ttrnProjecthierarchy.getTaskTaskinstructionremarks();
			
			
			taskInstanceBean.setCurrenttaskid(currenttaskid);
			taskInstanceBean.setCurrentstageid(currentstageid);
			taskInstanceBean.setTaskname(taskname);
			taskInstanceBean.setTaskdesc(taskdesc);
			taskInstanceBean.setTaskTasktype(taskTasktype);
			taskInstanceBean.setRejectionAllowed(rejectionAllowed);
			
			taskInstanceBean.setTaskIsemailtemplate(isEmailRequired);
			taskInstanceBean.setTaskstakeholder(taskstakeholder);
			taskInstanceBean.setAssignedtouserid(assignedtouserid);
			taskInstanceBean.setIsfirsttask(isfirsttask);
			taskInstanceBean.setIslasttask(islasttask);
			//taskInstanceBean.setPrevtaskid(prevTaskIds);
			taskInstanceBean.setPrevtaskid(null);
			taskInstanceBean.setTaskduration(taskduration);
			taskInstanceBean.setTaskIsattachment(taskIsattachment);
			taskInstanceBean.setTaskReferencedocid(taskReferencedocid);
			taskInstanceBean.setTaskTaskinstructionremarks(taskTaskinstructionremarks);
			taskInstanceBean.setCurrentTaskStatus(ttrnProjecthierarchy.getCurrentTaskStatus());
			//taskInstanceBean.setSelectedPrevTask(ttrnProjecthierarchy.getCurPrevTasks());
						
			
			ArrayList<TtrnProjecthierarchy> stageList=null;
		    ArrayList<TmRoles> taskstakeholderList=null;
		    ArrayList<TmEmployee> taskstakeholderUserList=null;
		    ArrayList<TtrnProjecthierarchy> prevTaskList=null;
		    ArrayList templateList=null;
		    
		    ArrayList<TtrnProjecthierarchy> prevTaskUnSelectedList=null;
		    ArrayList<TtrnProjecthierarchy> prevTaskSelectedList=null;
			
			
			String stakeHolderRole=String.valueOf(ttrnProjecthierarchy.getTaskstakeholder());
			
			stageList=daoObj.getStages(hibernateSession,projectWorkflowId);
			taskstakeholderList=daoObj.getRoles(hibernateSession);
			taskstakeholderUserList=daoObj.getUsersOfRole(hibernateSession,stakeHolderRole);
			prevTaskList=getPossiblePreviousTasks(hibernateSession,projectWorkflowId,currentTaskId);
			templateList=daoObj.getTemplateDocList(hibernateSession);
			
			
			//from ArrayList<TtrnProjecthierarchy> prevTaskList and String[] prevTaskIds generating
			//prevTaskUnSelectedList,prevTaskSelectedList
			//putting possibelprev data in hmap for quick retrieval
			HashMap<Long, TtrnProjecthierarchy> map_possiblePrevs=new HashMap<Long, TtrnProjecthierarchy>();
			for (TtrnProjecthierarchy possibleTask : prevTaskList) {
				map_possiblePrevs.put(possibleTask.getCurrenttaskid(), possibleTask);
			}
			
			prevTaskUnSelectedList=new ArrayList<TtrnProjecthierarchy>();
			prevTaskSelectedList=new ArrayList<TtrnProjecthierarchy>();
			//scanning prevTaskIds to seaparate prevTaskList into two lists
			for (String prevTaskId : prevTaskIds) {
				Long keyOfTaskId=Long.parseLong(prevTaskId);
				if(map_possiblePrevs.containsKey(keyOfTaskId))
				{
					prevTaskSelectedList.add(map_possiblePrevs.get(keyOfTaskId));
					map_possiblePrevs.remove(keyOfTaskId);//removig tasks from map which are selected
				}
				/*else
				{
					prevTaskUnSelectedList.add(map_possiblePrevs.get(keyOfTaskId));
				}*/
			}
			//putting remaining tasks in unselected list
			prevTaskUnSelectedList.addAll(map_possiblePrevs.values());
			
			
			
			
			
			
			taskInstanceBean.setStageList(stageList);
			taskInstanceBean.setTaskstakeholderList(taskstakeholderList);
			taskInstanceBean.setTaskstakeholderUserList(taskstakeholderUserList);
			taskInstanceBean.setPrevTaskList(prevTaskList);
			taskInstanceBean.setTemplateList(templateList);
			taskInstanceBean.setPrevTaskSelectedList(prevTaskSelectedList);
			taskInstanceBean.setPrevTaskUnSelectedList(prevTaskUnSelectedList);
			
		
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in setProjectPlanTaskInstance method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in setProjectPlanTaskInstance method of "
					+ this.getClass().getSimpleName()) ;
		}
		finally{
			hibernateSession.close();
		}
		
	}
	
	private class TaskNameComparator1 implements Comparator<TmstProjecthierarchytasksflow>
	{
		public int compare(TmstProjecthierarchytasksflow o1, TmstProjecthierarchytasksflow o2) {
			return o1.getPrevTaskName().compareToIgnoreCase(o2.getPrevTaskName());
		}	
	}
	
	public int saveNewProjectPlanTaskInstance(ProjectPlanInstanceBean formBean) throws NpdException{
		Connection connection=null;
		int status=0;
		try{
			//transfer from formbean to dto
			TtrnProjecthierarchy dto=new TtrnProjecthierarchy();
			
			TaskInstanceBean taskInstanceBean=formBean.getTaskInstanceBean();
			
			String projectworkflowid=formBean.getProjectWorkflowId();
			String currentstageid=taskInstanceBean.getCurrentstageid();
			String taskname=taskInstanceBean.getTaskname();
			String taskdesc=taskInstanceBean.getTaskdesc();
			String taskTasktype= taskInstanceBean.getTaskTasktype();
			String rejectedAllowed= taskInstanceBean.getRejectionAllowed();
			String taskstakeholder=taskInstanceBean.getTaskstakeholder();
			String isemailtemplate= taskInstanceBean.getTaskIsemailtemplate();
			//String assignedtouserid=taskInstanceBean.getAssignedtouserid();
			String isfirsttask=taskInstanceBean.getIsfirsttask();
			String islasttask=taskInstanceBean.getIslasttask();
			String[] prevTaskList=taskInstanceBean.getPrevtaskid();
			String taskduration="0";//taskInstanceBean.getTaskduration();
			String taskIsattachment=taskInstanceBean.getTaskIsattachment();
			String taskReferencedocid=taskInstanceBean.getTaskReferencedocid();
			String taskTaskinstructionremarks=taskInstanceBean.getTaskTaskinstructionremarks();
			
			
			
			dto.setProjectworkflowid(Long.parseLong(projectworkflowid));
			dto.setCurrentstageid(Long.parseLong(currentstageid));
			dto.setTaskname(taskname);
			dto.setTaskdesc(taskdesc);
			
			if("Y".equals(taskTasktype))
			{
				dto.setTaskTasktype(1);
			}
			else
			{
				dto.setTaskTasktype(0);
			}
			
			
			if("Y".equals(rejectedAllowed))
			{
				dto.setRejectionAllowed(1);
			}
			else
			{
				dto.setRejectionAllowed(0);
			}
			
			if("Y".equals(isemailtemplate))
			{
				dto.setTaskIsemailtemplate(1);
			}
			else
			{
				dto.setTaskIsemailtemplate(0);
			}
			
			
			dto.setTaskstakeholder(Long.parseLong(taskstakeholder));
			//dto.setAssignedtouserid(Long.parseLong(assignedtouserid));
			if("Y".equals(isfirsttask))
			{
				dto.setIsfirsttask(1);
			}
			else
			{
				dto.setIsfirsttask(0);
			}
			
			if("Y".equals(islasttask))
			{
				dto.setIslasttask(1);
			}
			else
			{
				dto.setIslasttask(0);
			}
			
			ArrayList<TmstProjecthierarchytasksflow> prevList=new ArrayList<TmstProjecthierarchytasksflow>();
			if(prevTaskList!=null)
			{
				for(int i=0;i<prevTaskList.length;i++)
				{
					TmstProjecthierarchytasksflow temp=new TmstProjecthierarchytasksflow();
					temp.setPrevtaskid(Long.parseLong(prevTaskList[i]));
					prevList.add(temp);
				}
			}
			dto.setPrevTaskList(prevList);
			
			dto.setTaskduration(Long.parseLong(taskduration));
			
			if("Y".equals(taskIsattachment))
			{
				dto.setTaskIsattachment(1);
				dto.setTaskReferencedocid(Integer.parseInt(taskReferencedocid));
			}
			else
			{
				dto.setTaskReferencedocid(0);
				dto.setTaskIsattachment(0);
			}
			
			dto.setTaskTaskinstructionremarks(taskTaskinstructionremarks);
			
			//call update method
			WorkFlowDao daoObj=new WorkFlowDao();
			connection=NpdConnection.getConnectionObject();
			connection.setAutoCommit(false);
			int op_status=daoObj.saveTaskInstance(connection,dto);
			
			if(op_status==1)
			{
				//success
				status=1;
				formBean.getTaskInstanceBean().setCurrenttaskid(String.valueOf(dto.getCurrenttaskid()));
			}
			else if(op_status==-1)
			{
				//failure
				status=-1;
			}
			/*//check for isfirst and islast
			if("Y".equals(isfirsttask))
			{
				//set all other is first to 0;
				daoObj.setAllIsFirstToZeroExcept(connection,dto.getProjectworkflowid(),dto.getCurrenttaskid());
			}
			
			
			if("Y".equals(islasttask))
			{
				//set all other is last to 0;
				daoObj.setAllIsLastToZeroExcept(connection,dto.getProjectworkflowid(),dto.getCurrenttaskid());
			}*/
			
			connection.commit();
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in UpdateProjectPlanTaskInstance method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			try {
				connection.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new NpdException("Exception occured in UpdateProjectPlanTaskInstance method of "
						+ this.getClass().getSimpleName()) ;
			}
			throw new NpdException("Exception occured in UpdateProjectPlanTaskInstance method of "
					+ this.getClass().getSimpleName()) ;
		}
		finally{
			try {
				NpdConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				AppConstants.NPDLOGGER.error(e.getMessage()
						+ " Exception occured in UpdateProjectPlanTaskInstance method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(e));
				throw new NpdException("Exception occured in UpdateProjectPlanTaskInstance method of "
						+ this.getClass().getSimpleName()) ;
			}
		}
		return status;
	}
		
	public ArrayList<TtrnProjecthierarchy> getPossiblePreviousTasks(Session hibernateSession, 
			String projectWorkflowId, String currentTaskId) 
				throws NpdException
	{
		
		TreeSet<TtrnProjecthierarchy> prevTasks=new TreeSet<TtrnProjecthierarchy>(new TaskNameComparator());
		try
		{
		AttachEditProjectPlanDao daoObj=new AttachEditProjectPlanDao(); 
		
		//fetching all tasks
		ArrayList<TtrnProjecthierarchy> list=daoObj.getAllTasks(hibernateSession, projectWorkflowId);
		
		
		//write algo gere
		//ArrayList<TtrnProjecthierarchy> toBeRemovedTasks=new ArrayList<TtrnProjecthierarchy>();
		
		//putting all task in map based on prev task id for quick retrieval
		TreeMap<Long, ArrayList<TtrnProjecthierarchy>> map=new TreeMap<Long, ArrayList<TtrnProjecthierarchy>>();
		for (TtrnProjecthierarchy row_list : list) {
			
			ArrayList<TmstProjecthierarchytasksflow> prevList=row_list.getPrevTaskList();
			if(prevList!=null)
			{
				for (TmstProjecthierarchytasksflow projecthierarchytasksflow : prevList) {
					long prevTaskId=projecthierarchytasksflow.getPrevtaskid();
					ArrayList temp=map.get(prevTaskId);
					if(temp==null)
					{
						temp=new ArrayList();
					}
					temp.add(row_list);
					map.put(prevTaskId,temp);
				}
			}
		}
		
		
		//making queue of all child tasks
		ArrayList<Long> queueToBeRemoveTaskId=new ArrayList<Long>();
		int index_queue=-1;
		queueToBeRemoveTaskId.add(new Long(currentTaskId));
		index_queue++;
		
		/*do
		{
			Long taskId=(Long)queueToBeRemoveTaskId.get(index_queue++);
			ArrayList<TtrnProjecthierarchy> temp=map.get(taskId);
			if(temp!=null)
			{
				for (TtrnProjecthierarchy projecthierarchy : temp) {
					queueToBeRemoveTaskId.add(projecthierarchy.getCurrenttaskid());
				}
			}
		}
		while(index_queue<queueToBeRemoveTaskId.size());*/
		
		HashSet<Long> allReadyInQueue=new HashSet<Long>();  
		allReadyInQueue.add(new Long(currentTaskId));
		do
		{
			Long taskId=(Long)queueToBeRemoveTaskId.get(index_queue++);
			ArrayList<TtrnProjecthierarchy> temp=map.get(taskId);
			if(temp!=null)
			{
				for (TtrnProjecthierarchy projecthierarchy : temp) {
					if(!allReadyInQueue.contains(new Long(projecthierarchy.getCurrenttaskid())))
					{
						queueToBeRemoveTaskId.add(projecthierarchy.getCurrenttaskid());
						allReadyInQueue.add(new Long(projecthierarchy.getCurrenttaskid()));				
					}
					
				}
			}
		}
		while(index_queue<queueToBeRemoveTaskId.size());
		
		//making hashmap of all tasks
		HashMap<Long, TtrnProjecthierarchy> hashMap_prevList=new HashMap<Long, TtrnProjecthierarchy>();
		for (TtrnProjecthierarchy row_list : list) {
			hashMap_prevList.put(row_list.getCurrenttaskid(),row_list);
		}
		
		//remove particular tasks from hashmap
		for (Long temptaskId : queueToBeRemoveTaskId) {
			hashMap_prevList.remove(temptaskId);
		}
		
		//converting from hashmap to Treeset to sort on basis of taskname
		prevTasks.addAll(hashMap_prevList.values());
		
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new NpdException(ex);
		}
		
		// converting to arraylist
		return new ArrayList<TtrnProjecthierarchy>(prevTasks);
		
	}
	
	private class TaskNameComparator implements Comparator<TtrnProjecthierarchy>
	{
		public int compare(TtrnProjecthierarchy o1, TtrnProjecthierarchy o2) {
			return o1.getTaskname().compareToIgnoreCase(o2.getTaskname());
		}	
	}
	
	public int UpdateProjectPlanTaskInstance(ProjectPlanInstanceBean formBean) throws NpdException {
		
		
		Connection connection=null;
		int status=0;
		try{
			//transfer from formbean to dto
			TtrnProjecthierarchy dto=new TtrnProjecthierarchy();
			
			TaskInstanceBean taskInstanceBean=formBean.getTaskInstanceBean();
			
			String projectworkflowid=formBean.getProjectWorkflowId();
			String currenttaskid=taskInstanceBean.getCurrenttaskid();
			
			String taskTasktype= taskInstanceBean.getTaskTasktype();
			String rejectionAllowed= taskInstanceBean.getRejectionAllowed();
			String isemailtemplate= taskInstanceBean.getTaskIsemailtemplate();
			//String taskstakeholder=taskInstanceBean.getTaskstakeholder();
			//String assignedtouserid=taskInstanceBean.getAssignedtouserid();
			String isfirsttask=taskInstanceBean.getIsfirsttask();
			String islasttask=taskInstanceBean.getIslasttask();
			String[] prevTaskList=taskInstanceBean.getPrevtaskid();
			String taskduration="0";
			String taskIsattachment=taskInstanceBean.getTaskIsattachment();
			String taskReferencedocid=taskInstanceBean.getTaskReferencedocid();
			String taskTaskinstructionremarks=taskInstanceBean.getTaskTaskinstructionremarks();
			
			
			
			dto.setProjectworkflowid(Long.parseLong(projectworkflowid));
			dto.setCurrenttaskid(Long.parseLong(currenttaskid));
			
			if("Y".equals(taskTasktype))
			{
				dto.setTaskTasktype(1);
			}
			else
			{
				dto.setTaskTasktype(0);
			}
			//
			if("Y".equals(rejectionAllowed))
			{
				dto.setRejectionAllowed(1);
			}
			else
			{
				dto.setRejectionAllowed(0);
			}
			
			if("Y".equals(isemailtemplate))
			{
				dto.setTaskIsemailtemplate(1);
			}
			else
			{
				dto.setTaskIsemailtemplate(0);
			}
			
			
			//dto.setTaskstakeholder(Long.parseLong(taskstakeholder));
			//dto.setAssignedtouserid(Long.parseLong(assignedtouserid));
			if("Y".equals(isfirsttask))
			{
				dto.setIsfirsttask(1);
			}
			else
			{
				dto.setIsfirsttask(0);
			}
			
			if("Y".equals(islasttask))
			{
				dto.setIslasttask(1);
			}
			else
			{
				dto.setIslasttask(0);
			}
			
			ArrayList<TmstProjecthierarchytasksflow> prevList=new ArrayList<TmstProjecthierarchytasksflow>();
			if(prevTaskList!=null)
			{
				for(int i=0;i<prevTaskList.length;i++)
				{
					TmstProjecthierarchytasksflow temp=new TmstProjecthierarchytasksflow();
					temp.setPrevtaskid(Long.parseLong(prevTaskList[i]));
					prevList.add(temp);
				}
			}
			dto.setPrevTaskList(prevList);
			
			dto.setTaskduration(Long.parseLong(taskduration));
			
			if("Y".equals(taskIsattachment))
			{
				dto.setTaskIsattachment(1);
				dto.setTaskReferencedocid(Integer.parseInt(taskReferencedocid));
			}
			else
			{
				dto.setTaskReferencedocid(0);
				dto.setTaskIsattachment(0);
			}
			
			dto.setTaskTaskinstructionremarks(taskTaskinstructionremarks);
			
			//call update method
			WorkFlowDao daoObj=new WorkFlowDao();
			connection=NpdConnection.getConnectionObject();
			connection.setAutoCommit(false);
			int op_status=daoObj.updateTaskInstance(connection,dto);
			
			if(op_status==1)
			{
				//success
				status=1;
			}
			else if(op_status==-1)
			{
				//failure
				status=-1;
			}
			
			connection.commit();
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in UpdateProjectPlanTaskInstance method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			try {
				connection.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new NpdException("Exception occured in UpdateProjectPlanTaskInstance method of "
						+ this.getClass().getSimpleName()) ;
			}
			throw new NpdException("Exception occured in UpdateProjectPlanTaskInstance method of "
					+ this.getClass().getSimpleName()) ;
		}
		finally{
			try {
				NpdConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				AppConstants.NPDLOGGER.error(e.getMessage()
						+ " Exception occured in UpdateProjectPlanTaskInstance method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(e));
				throw new NpdException("Exception occured in UpdateProjectPlanTaskInstance method of "
						+ this.getClass().getSimpleName()) ;
			}
		}
		return status;
	}

	public void setProjectPlanHomeView(ProjectPlanInstanceBean formBean) throws NpdException {
		
		Connection conn=null;
		Session hibernateSession = null;
		try
		{	
			String projectId=formBean.getProjectId();
			AttachEditProjectPlanDao daoObj=new AttachEditProjectPlanDao(); 
			conn = NpdConnection.getConnectionObject();
			
			
			String projectWorkflowId;
			TtrnProjectworkflow projectWorkFlow=daoObj.getDraftProjectWorkflowId(conn,formBean.getProjectId());
			projectWorkflowId=String.valueOf(projectWorkFlow.getProjectworkflowid());
			formBean.setProjectWorkflowId(projectWorkflowId);
			String attachHistoryStatus=projectWorkFlow.getAttachHistoryStatus();
			if("editingFinalized".equals(attachHistoryStatus))
			{
				formBean.setAttachMode("editingFinalized");
			}
			else if("attachNew".equals(attachHistoryStatus))
			{
				formBean.setAttachMode("attachNew");
			}
			
			
			//fetch info
			//fect project info
			
			CommonBaseDao commonBaseDao = (CommonBaseDao) BaseDaoFactory
														.getInstance(DaoConstants.BASE_DAO_CLASS);
			
			hibernateSession = CommonBaseDao.beginTrans();
			TtrnProject ttrnProject = new TtrnProject();
			
			ttrnProject = (TtrnProject) commonBaseDao.findById(Long.parseLong(formBean.getProjectId()),
					HibernateBeansConstants.HBM_PROJECT, hibernateSession);
			
			
			formBean.setProject(ttrnProject);
			
			setCheckListView(hibernateSession,formBean);
			
			/*TtrnProjecthierarchy firstTask=null;
			TtrnProjecthierarchy lastTask=null;
			
			//fetch isfirst and last info
			firstTask=daoObj.getFirstLastTask(hibernateSession,projectWorkflowId,"first");
			lastTask=daoObj.getFirstLastTask(hibernateSession,projectWorkflowId,"last");
			
			formBean.setFirstTask(firstTask);
			formBean.setLastTask(lastTask);
			
			
			//fetch info prev task info
			ArrayList<TtrnProjecthierarchy> noPrevTaskList=new ArrayList<TtrnProjecthierarchy>();
			ArrayList<TtrnProjecthierarchy> allTaskList=null;
			allTaskList=daoObj.getAllTasks(hibernateSession, projectWorkflowId);
			
			ArrayList temp_prevTaskList=null;
			for (TtrnProjecthierarchy projecthierarchy : allTaskList) {
				temp_prevTaskList=projecthierarchy.getPrevTaskList();
				if((temp_prevTaskList==null || temp_prevTaskList.size()==0 )&& projecthierarchy.getIsfirsttask()!=1)
				{
					noPrevTaskList.add(projecthierarchy);
				}
			}
			
			formBean.setNoPrevTaskList(noPrevTaskList);
		
//			fetch tasks with no user attached
			
			ArrayList<TtrnProjecthierarchy> noStakeUserTaskList=new ArrayList<TtrnProjecthierarchy>();
			for (TtrnProjecthierarchy task : allTaskList) {
				if(task.getAssignedtouserid()==null || "".equals(task.getAssignedtouserid()))
				{
					noStakeUserTaskList.add(task);
				}
			}
			
			formBean.setNoStakeUserList(noStakeUserTaskList);*/
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in setProjectPlanHomeView method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in setProjectPlanHomeView method of "
					+ this.getClass().getSimpleName()) ;
		}
		finally{
			
			try {
				NpdConnection.freeConnection(conn);
			} catch (Exception e) {
				e.printStackTrace();
				AppConstants.NPDLOGGER.error(e.getMessage()
						+ " Exception occured in setProjectPlanHomeView method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(e));
				throw new NpdException("Exception occured in setProjectPlanHomeView method of "
						+ this.getClass().getSimpleName()) ;
			}finally
			{
				hibernateSession.close();
			}
		}
		
	}

	public void setProductDetails(ProjectPlanInstanceBean formBean) throws NpdException {
		
		
		Session hibernateSession = null;
		try
		{	
			String projectId=formBean.getProjectId();
			
			//fect project info
			
			CommonBaseDao commonBaseDao = (CommonBaseDao) BaseDaoFactory
														.getInstance(DaoConstants.BASE_DAO_CLASS);
			hibernateSession = CommonBaseDao.beginTrans();
			TtrnProject ttrnProject = new TtrnProject();
			ttrnProject = (TtrnProject) commonBaseDao.findById(Long.parseLong(formBean.getProjectId()),
					HibernateBeansConstants.HBM_PROJECT, hibernateSession);
			formBean.setProject(ttrnProject);
			
			
			
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in setProductDetails method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in setProductDetails method of "
					+ this.getClass().getSimpleName()) ;
		}
		finally{
			hibernateSession.close();
			try {
				
			} catch (Exception e) {
				e.printStackTrace();
				AppConstants.NPDLOGGER.error(e.getMessage()
						+ " Exception occured in setProductDetails method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(e));
				throw new NpdException("Exception occured in setProductDetails method of "
						+ this.getClass().getSimpleName()) ;
			}
		}
		
	}
	
	public void setCheckListView(Session hibernateSession,ProjectPlanInstanceBean formBean) throws NpdException {
		
		Connection conn=null;
		
		try
		{	
			AttachEditProjectPlanDao daoObj=new AttachEditProjectPlanDao();
			String projectWorkflowId=String.valueOf(formBean.getProjectWorkflowId());
			
			
			TtrnProjecthierarchy firstTask=null;
			TtrnProjecthierarchy lastTask=null;
			
			//fetch isfirst and last info
			firstTask=daoObj.getFirstLastTask(hibernateSession,projectWorkflowId,"first");
			lastTask=daoObj.getFirstLastTask(hibernateSession,projectWorkflowId,"last");
			
			formBean.setFirstTask(firstTask);
			formBean.setLastTask(lastTask);
			
			
			//fetch info prev task info
			ArrayList<TtrnProjecthierarchy> noPrevTaskList=new ArrayList<TtrnProjecthierarchy>();
			ArrayList<TtrnProjecthierarchy> allTaskList=null;
			allTaskList=daoObj.getAllTasks(hibernateSession, projectWorkflowId);
			
			ArrayList temp_prevTaskList=null;
			for (TtrnProjecthierarchy projecthierarchy : allTaskList) {
				temp_prevTaskList=projecthierarchy.getPrevTaskList();
				if((temp_prevTaskList==null || temp_prevTaskList.size()==0 )&& projecthierarchy.getIsfirsttask()!=1)
				{
					noPrevTaskList.add(projecthierarchy);
				}
			}
			
			formBean.setNoPrevTaskList(noPrevTaskList);
		
//			fetch tasks with no user attached
			
			ArrayList<TtrnProjecthierarchy> noStakeUserTaskList=new ArrayList<TtrnProjecthierarchy>();
			for (TtrnProjecthierarchy task : allTaskList) {
				if(task.getAssignedtouserid()==null || "".equals(task.getAssignedtouserid()))
				{
					noStakeUserTaskList.add(task);
				}
			}
			
			formBean.setNoStakeUserList(noStakeUserTaskList);
			
			//fetch whether any task is there or not
			
			if(allTaskList==null ||allTaskList.size()==0)
			{
				formBean.setTotalTasks("0");
			}
			else
			{
				formBean.setTotalTasks(String.valueOf(allTaskList.size()));
			}
			
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in setCheckListView method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in setCheckListView method of "
					+ this.getClass().getSimpleName()) ;
		}
		finally{
			
			try {
				NpdConnection.freeConnection(conn);
			} catch (Exception e) {
				e.printStackTrace();
				AppConstants.NPDLOGGER.error(e.getMessage()
						+ " Exception occured in setCheckListView method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(e));
				throw new NpdException("Exception occured in setCheckListView method of "
						+ this.getClass().getSimpleName()) ;
			}
		}
		
	}

	public void setInstanceStagesView(ProjectPlanInstanceBean formBean)  throws NpdException {
		
		Connection conn=null;
		Session hibernateSession=null;
		try
		{
			conn=NpdConnection.getConnectionObject();
			ArrayList<TtrnProjecthierarchy> stagesView=null;
			
			
			AttachEditProjectPlanDao daoObj=new AttachEditProjectPlanDao(); 
			//StakeHolderDao daoObj = (StakeHolderDao) BaseDaoFactory
				//							.getInstance(DaoConstants.STAKE_HOLDER_DAO);
			
			hibernateSession = CommonBaseDao.beginTrans();
			
			
			//fetch workflowId corersponding to a project
			
			String projectWorkflowId=formBean.getProjectWorkflowId();
			
			//fecthing stages
			ProjectPlanInstanceBean stageSearchDto=new ProjectPlanInstanceBean();
			
			stageSearchDto.setProjectWorkflowId(projectWorkflowId);
			stageSearchDto.setSearchStageName(formBean.getSearchStageName());
			stageSearchDto.setSearchStageDesc(formBean.getSearchStageDesc());
			
			PagingSorting stagesPS=formBean.getStagesPS();
			if(stagesPS==null)
			{
				formBean.setStagesPS(stagesPS);
			}
			stagesPS.setPagingSorting(true,true);
			
			stagesPS.setDefaultifNotPresent("stageName", PagingSorting.increment, 1);
			stageSearchDto.setStagesPS(stagesPS);
			
			
			stagesView=daoObj.getStages(conn,stageSearchDto);
			
			formBean.setStagesView(stagesView);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in setInstanceStagesView method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in setInstanceStagesView method of "
					+ this.getClass().getSimpleName()) ;
		}
		finally{
			try {
				NpdConnection.freeConnection(conn);
				
			} catch (Exception e) {
				e.printStackTrace();
				AppConstants.NPDLOGGER.error(e.getMessage()
						+ " Exception occured in setInstanceStagesView method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(e));
				throw new NpdException("Exception occured in setInstanceStagesView method of "
						+ this.getClass().getSimpleName()) ;
			}finally
			{
				try{hibernateSession.close();}
				catch(Exception e)
				{
					e.printStackTrace();
					AppConstants.NPDLOGGER.error(e.getMessage()
							+ " Exception occured in setInstanceStagesView method of "
							+ this.getClass().getSimpleName()+AppUtility.getStackTrace(e));
					throw new NpdException("Exception occured in setInstanceStagesView method of "
							+ this.getClass().getSimpleName()) ;
				}
			}
		}
		
	}

	public int deleteTaskInstance(ProjectPlanInstanceBean formBean) throws NpdException {
		
		
		Connection connection=null;
		int status=0;
		try{
			//transfer from formbean to dto
			TtrnProjecthierarchy dto=new TtrnProjecthierarchy();
			
			TaskInstanceBean taskInstanceBean=formBean.getTaskInstanceBean();
			
			String projectworkflowid=formBean.getProjectWorkflowId();
			String currenttaskid=formBean.getSelectedTaskId();
			
			dto.setProjectworkflowid(Long.parseLong(projectworkflowid));
			dto.setCurrenttaskid(Long.parseLong(currenttaskid));
			
			
			//call delete method
			WorkFlowDao daoObj=new WorkFlowDao();
			connection=NpdConnection.getConnectionObject();
			connection.setAutoCommit(false);
			int op_status=daoObj.deleteTaskInstance(connection,dto);
			
			if(op_status==1)
			{
				//success
				status=1;
			}
			else if(op_status==-1)
			{
				//failure
				status=-1;
			}
			
			connection.commit();
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in deleteTaskInstance method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			try {
				connection.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new NpdException("Exception occured in deleteTaskInstance method of "
						+ this.getClass().getSimpleName()) ;
			}
			throw new NpdException("Exception occured in deleteTaskInstance method of "
					+ this.getClass().getSimpleName()) ;
		}
		finally{
			try {
				NpdConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				AppConstants.NPDLOGGER.error(e.getMessage()
						+ " Exception occured in deleteTaskInstance method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(e));
				throw new NpdException("Exception occured in deleteTaskInstance method of "
						+ this.getClass().getSimpleName()) ;
			}
		}
		return status;
	}
	
	public int openFinalizedAsDraft(ProjectPlanInstanceBean formBean) throws NpdException {
		int status=-1;
		Connection conn= null;
		try
		{	
			//genarate start date(target) for each task
			AttachEditProjectPlanDao daoObj=new AttachEditProjectPlanDao(); 
			conn=NpdConnection.getConnectionObject();
			conn.setAutoCommit(false);
			String projectId=formBean.getProjectId();
			//String projectWorkflowId;
			//TtrnProjectworkflow projectWorkFlow=daoObj.getActiveProjectWorkflowId(conn,formBean.getProjectId());
			//projectWorkflowId=String.valueOf(projectWorkFlow.getProjectworkflowid());
			
			
			//int dao_status=daoObj.moveToHistoryAndCreateCopy(conn,projectId);
			int dao_status=daoObj.createDraftCopy(conn,projectId);
			if(dao_status==1)
			{
				status=1;
			}
			else
			{
				status=-1;
			}
		
			conn.commit();
		}
		catch(Exception ex)
		{
			try {
				conn.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				AppConstants.NPDLOGGER.error(e.getMessage()
						+ " Exception occured in openFinalizedAsDraft method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(e));
				throw new NpdException("Exception occured in openFinalizedAsDraft method of "
						+ this.getClass().getSimpleName()+"  inserting mapping ") ;
			}
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in openFinalizedAsDraft method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in openFinalizedAsDraft method of "
					+ this.getClass().getSimpleName()+"  inserting mapping ") ;
		}
		finally{
			try {
				NpdConnection.freeConnection(conn);
			} catch (Exception e) {
				e.printStackTrace();
				AppConstants.NPDLOGGER.error(e.getMessage()
						+ " Exception occured in openFinalizedAsDraft method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(e));
				throw new NpdException("Exception occured in openFinalizedAsDraft method of "
						+ this.getClass().getSimpleName()+"  inserting mapping ") ;
			}
		}
		return status;
	}

	public int sendEmailForFinalized(ProjectPlanInstanceBean formBean,String mailOption) throws NpdException{
		int status=-1;
		Connection conn= null;
		Session hibernateSession=null;
		try
		{	
			conn=NpdConnection.getConnectionObject();
			conn.setAutoCommit(false);
			
			CommonBaseDao commonBaseDao = (CommonBaseDao) BaseDaoFactory
			.getInstance(DaoConstants.BASE_DAO_CLASS);
			hibernateSession = CommonBaseDao.beginTrans();
			
			SendMailUtility oSendMail = new SendMailUtility();
			ArrayList toList = new ArrayList();
			ArrayList ccList = new ArrayList();
			
			AttachEditProjectPlanDao daoObj=new AttachEditProjectPlanDao(); 
			
			//get EmailId of employees
			ArrayList<TmEmployee> employeeList=new ArrayList<TmEmployee>();
			employeeList=daoObj.getEmployeesOfProject(conn,formBean.getProjectWorkflowId());
			for (TmEmployee employee : employeeList) {
				toList.add(employee.getEmail());
			}
			
			
			
			
			
			StringBuffer eBody=new StringBuffer();
			
			String eSubject=null;
			if("PLAN_ALTERED".equals(mailOption))
			{
				eSubject="Product Plan Modified.";
			}
			else if("PLAN_NEW".equals(mailOption))
			{
				eSubject="New Product Created.";	
			}
			
			
			
			eBody.append("<HTML><BODY>");
			
			
			
			
			eBody.append("<TABLE>");
			
				eBody.append("<TR><TD>");
					eBody.append("<TABLE>");
						eBody.append("<TR>");
							eBody.append("<TD>");
								eBody.append(Messages.getMessageValue("Mail_Header")+"<BR>");
							eBody.append("</TD>");
						eBody.append("</TR>");
					eBody.append("</TABLE>");
				eBody.append("</TD></TR>");
			
			eBody.append("<TR><TD>");
				eBody.append("<TABLE>");
					eBody.append("<TR>");
						eBody.append("<TD bgcolor=\"#FF9255\">");
							if("PLAN_ALTERED".equals(mailOption))
							{
								eBody.append("Following Product's Plan has been Modified . Details Are as Follows :");
							}
							else if("PLAN_NEW".equals(mailOption))
							{
								eBody.append("A New Product has been Created . Details Are as Follows :");	
							}
							
						eBody.append("</TD>");
					eBody.append("</TR>");
				eBody.append("</TABLE>");
			eBody.append("</TD></TR>");
			//fetch project details and set
			StringBuffer productDetails=new StringBuffer();
			
			
			eBody.append("<TR><TD>");
			
			
			//eBody.append("</TD></TR>");
			
			AttachEditProjectPlanModel aPPModel=new AttachEditProjectPlanModel();
			eBody.append(aPPModel.getProjectDetailString(Long.parseLong(formBean.getProjectId()),hibernateSession));
			
			eBody.append("</TD></TR>");
			//fetch plan details and set
			StringBuffer planDetails=new StringBuffer();
			//StringBuffer taskRow=new StringBuffer();
			eBody.append("<TR><TD>");
				eBody.append("<TABLE>");
					eBody.append("<TR>");
						eBody.append("<TD>");
							eBody.append("Project Plan is as follows :");
						eBody.append("</TD>");
					eBody.append("</TR>");
				eBody.append("</TABLE>");
			eBody.append("</TD></TR>");			
			eBody.append("<TR><TD>");
			
			eBody.append(aPPModel.getProjectPlanMailString(formBean.getProjectWorkflowId(),conn));
			
			
			
		
			eBody.append("</TD></TR>");
			StringBuffer toMessage=new StringBuffer();
			StringBuffer fromMessage=new StringBuffer();
			
			eBody.append("<TR><TD>");
				eBody.append("<TABLE>");
					eBody.append("<TR>");
						eBody.append("<TD>");
							eBody.append("<BR>"+Messages.getMessageValue("Mail_Footer")+"<BR>");
						eBody.append("</TD>");
					eBody.append("</TR>");
				eBody.append("</TABLE>");
			eBody.append("</TD></TR>");
			
			eBody.append("</TABLE>");
			eBody.append("</BODY></HTML>");
			//------------------------
			ExcelCreator excelCreator = new ExcelCreator();
			HSSFWorkbook workbook = excelCreator.createExcelForProjectEdit(formBean,hibernateSession,conn);
			
			
			String filepath = baseDirPath+"AttachProject"+System.currentTimeMillis()+".xls";
			
			FileOutputStream fileOut = new FileOutputStream(filepath);
			workbook.write(fileOut);
			fileOut.close(); 
			File file =null;
			file = new File(filepath);
			//------------------------
			 //InputStream is = new BufferedInputStream(attachmentPath.getInputStream());
			//eBody=toMessage.append(productDetails).append(planDetails).append(fromMessage).toString();
			 
			oSendMail.setOToList(toList);
			oSendMail.setOCcList(ccList);
			oSendMail.setStrSubject(eSubject);
			oSendMail.setStrMessage(eBody.toString());
			oSendMail.sendMessageWithAttachemntsPath(new String[]{file.getAbsolutePath()},new String[]{"Product Plan.xls"});
			
		
			conn.commit();
		}
		catch(Exception ex)
		{
			try {
				conn.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				AppConstants.NPDLOGGER.error(e.getMessage()
						+ " Exception occured in sendEmailForFinalized method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(e));
				throw new NpdException("Exception occured in sendEmailForFinalized method of "
						+ this.getClass().getSimpleName()+"  inserting mapping ") ;
			}
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in sendEmailForFinalized method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			/*throw new NpdException("Exception occured in sendEmailForFinalized method of "
					+ this.getClass().getSimpleName()+"  inserting mapping ") ;*/
		}
		finally{
			try {
				//hibernateSession.close();
				NpdConnection.freeConnection(conn);
			} catch (Exception e) {
				e.printStackTrace();
				AppConstants.NPDLOGGER.error(e.getMessage()
						+ " Exception occured in sendEmailForFinalized method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(e));
				throw new NpdException("Exception occured in sendEmailForFinalized method of "
						+ this.getClass().getSimpleName()) ;
			}finally
			{
				try{hibernateSession.close();}
				catch(Exception e)
				{
					e.printStackTrace();
					AppConstants.NPDLOGGER.error(e.getMessage()
							+ " Exception occured in sendEmailForFinalized method of "
							+ this.getClass().getSimpleName()+AppUtility.getStackTrace(e));
					throw new NpdException("Exception occured in sendEmailForFinalized method of "
							+ this.getClass().getSimpleName()) ;

				}
			}
		}
		return status;
		
	}

	public StringBuffer getProjectDetailString(long projectId, Session hibernateSession) {

		StringBuffer productDetails=new StringBuffer();
		TtrnProject ttrnProject = new TtrnProject();
		CommonBaseDao commonBaseDao=new CommonBaseDao();
		ttrnProject = (TtrnProject) commonBaseDao.findById((projectId),
		HibernateBeansConstants.HBM_PROJECT, hibernateSession);

		
		productDetails.append("<TABLE width=\"100%\" border=\"1\"  cellpadding=\"3\" cellspacing=\"1\" align=\"center\" >");
			productDetails.append("<TR>");
				productDetails.append("<TD nowrap  bgcolor=\"#ffcf9f\">");
					productDetails.append("Project Id :");
				productDetails.append("</TD>");
				productDetails.append("<TD nowrap  bgcolor=\"#FFEBC6\">");
					productDetails.append(ttrnProject.getProjectid());
				productDetails.append("</TD>");
				productDetails.append("<TD nowrap  bgcolor=\"#ffcf9f\">");
					productDetails.append("Project Name :");
				productDetails.append("</TD>");
				productDetails.append("<TD nowrap  bgcolor=\"#FFEBC6\">");
					productDetails.append(ttrnProject.getProjectName());
				productDetails.append("</TD>");
				productDetails.append("<TD nowrap  bgcolor=\"#ffcf9f\">");
					productDetails.append("Priority of Launch :");
				productDetails.append("</TD>");
				productDetails.append("<TD nowrap  bgcolor=\"#FFEBC6\">");
					productDetails.append(ttrnProject.getLaunchPriority());
				productDetails.append("</TD>");					
			productDetails.append("</TR>");

		productDetails.append("<TR>");
			productDetails.append("<TD nowrap  bgcolor=\"#ffcf9f\">");
				productDetails.append("Target Market :");
			productDetails.append("</TD>");
			productDetails.append("<TD nowrap  bgcolor=\"#FFEBC6\">");
				productDetails.append(ttrnProject.getTargetMkt());
			productDetails.append("</TD>");
			productDetails.append("<TD nowrap  bgcolor=\"#ffcf9f\">");
				productDetails.append("Product Category :");
			productDetails.append("</TD>");		
			productDetails.append("<TD nowrap  bgcolor=\"#FFEBC6\">");
				productDetails.append(ttrnProject.getPrdCategory().getProdcatdesc());
			productDetails.append("</TD>");
			productDetails.append("<TD nowrap  bgcolor=\"#ffcf9f\">");
				productDetails.append("NPD Category :");
			productDetails.append("</TD>");
			productDetails.append("<TD nowrap  bgcolor=\"#FFEBC6\">");
				productDetails.append(ttrnProject.getNpdCategory().getNpdcatdesc());
			productDetails.append("</TD>");				
		productDetails.append("</TR>");
		
		productDetails.append("<TR>");
			productDetails.append("<TD nowrap  bgcolor=\"#ffcf9f\">");
				productDetails.append("Airtel Potential :");
			productDetails.append("</TD>");
			productDetails.append("<TD nowrap  bgcolor=\"#FFEBC6\">");
				productDetails.append(ttrnProject.getAirtelPotential());
			productDetails.append("</TD>");
			productDetails.append("<TD nowrap  bgcolor=\"#ffcf9f\">");
				productDetails.append("Total Market Potential :");
			productDetails.append("</TD>");
			productDetails.append("<TD nowrap  bgcolor=\"#FFEBC6\">");
				productDetails.append(ttrnProject.getTotalMktPotential());
			productDetails.append("</TD>");
			productDetails.append("<TD nowrap  bgcolor=\"#ffcf9f\">");
				productDetails.append("Capex Requirement :");
			productDetails.append("</TD>");
			productDetails.append("<TD nowrap  bgcolor=\"#FFEBC6\">");
				productDetails.append(ttrnProject.getCapexReq());
			productDetails.append("</TD>");				
		productDetails.append("</TR>");			

		productDetails.append("<TR>");
			productDetails.append("<TD nowrap  bgcolor=\"#ffcf9f\">");
				productDetails.append("Product Brief :");
			productDetails.append("</TD>");
			productDetails.append("<TD nowrap  bgcolor=\"#FFEBC6\">");
				productDetails.append(ttrnProject.getProductBrief());
			productDetails.append("</TD>");
			productDetails.append("<TD nowrap  bgcolor=\"#ffcf9f\">");
				productDetails.append("Start Date :");
			productDetails.append("</TD>");
			productDetails.append("<TD nowrap  bgcolor=\"#FFEBC6\">");
				productDetails.append(Utility.showDate_Report(ttrnProject.getStartDate()));
			productDetails.append("</TD>");
			productDetails.append("<TD nowrap  bgcolor=\"#ffcf9f\">");
				productDetails.append("Expected Launch Date :");
			productDetails.append("</TD>");
			productDetails.append("<TD nowrap  bgcolor=\"#FFEBC6\">");
				productDetails.append(Utility.showDate_Report(ttrnProject.getExpectedLaunchDate()));
			productDetails.append("</TD>");				
		productDetails.append("</TR>");				
		productDetails.append("</TABLE>");
		
		return productDetails;
	}
	
	public StringBuffer getProjectPlanMailString(String projectWorkflowId,Connection conn) throws NpdException
	{
		StringBuffer eBody=new StringBuffer();
		
		AttachEditProjectPlanDao daoObj=new AttachEditProjectPlanDao(); 
		
		ArrayList<TtrnProjecthierarchy> taskList=new ArrayList<TtrnProjecthierarchy>();
		ProjectPlanInstanceBean taskSearchDto=new ProjectPlanInstanceBean();
		taskSearchDto.setProjectWorkflowId(projectWorkflowId);
		taskSearchDto.setTaskOption("ALL");
		PagingSorting tasksPS=new PagingSorting(false,true);
		tasksPS.setDefaultifNotPresent("special", PagingSorting.increment, 1);
		taskSearchDto.setTasksPS(tasksPS);		
		taskList=daoObj.getTasks(conn,taskSearchDto);
		
		
		HashMap<Long, TtrnProjecthierarchy> mapProject=new HashMap<Long, TtrnProjecthierarchy>();
		for (TtrnProjecthierarchy projecthierarchy : taskList) {
			mapProject.put(projecthierarchy.getCurrenttaskid(), projecthierarchy);
		}
		
		ArrayList<TtrnProjecthierarchy> taskListForNext=taskList;
		HashMap<Long, ArrayList<TmstProjecthierarchytasksflow>> map=new HashMap<Long, ArrayList<TmstProjecthierarchytasksflow>>(); 
		for (TtrnProjecthierarchy projecthierarchy : taskListForNext) {
			map.put(projecthierarchy.getCurrenttaskid(),projecthierarchy.getNextTaskList());
		}
		
		
		eBody.append("<TABLE  border=\"1\"  cellpadding=\"3\" cellspacing=\"1\" align=\"center\" >");
		eBody.append("<TR bgcolor=\"#FF9255\">");
		eBody.append("<TD nowrap >Stage Name</TD>");
		eBody.append("<TD nowrap >Task Name</TD>");
		eBody.append("<TD nowrap >Task Desc.</TD>");
		eBody.append("<TD nowrap >Mandatory</TD>");
		eBody.append("<TD nowrap >Assigned To</TD>");
		eBody.append("<TD nowrap >Role Name</TD>");
		eBody.append("<TD nowrap >Is First Task</TD>");
		eBody.append("<TD nowrap >Is Last Task</TD>");
		eBody.append("<TD nowrap >Next Tasks</TD>");
		eBody.append("<TD nowrap >Planned Duration</TD>");
		eBody.append("<TD nowrap >Document To Upload</TD>");
		eBody.append("<TD nowrap >Remarks</TD>");
		eBody.append("<TD nowrap >Target Start</TD>");
		eBody.append("<TD nowrap >Target End</TD>");
		/*eBody.append("<TD nowrap >Actual Start</TD>");
		eBody.append("<TD nowrap >Actual End</TD>");
		eBody.append("<TD nowrap >Action Taken</td>");*/
		eBody.append("</TR>");
		String[] colors={"\"#FFEBC6\"","\"#ffcf9f\""};
		int i=0;
		
		for (TtrnProjecthierarchy task: taskList) {
			eBody.append("<TR bgcolor="+colors[(i++)%2]+">");
			
			eBody.append("<TD nowrap >"+task.getStagename()+"</TD>");
			eBody.append("<TD nowrap >"+task.getTaskname()+"</TD>");
			eBody.append("<TD nowrap >"+task.getTaskdesc()+"</TD>");
			
			if(task.getTaskTasktype()!=null)
			{
				if(1==task.getTaskTasktype())
				{
					eBody.append("<TD nowrap >Y</TD>");
				}
				else if(0==task.getTaskTasktype())
				{
					eBody.append("<TD nowrap >&nbsp;</TD>");
				}
			}
			eBody.append("<TD nowrap >"+task.getAssignedtouserName()+"</TD>");
			eBody.append("<TD nowrap >"+task.getTaskstakeholderName()+"</TD>");
			if(task.getIsfirsttask()!=null)
			{
				if(1==task.getIsfirsttask())
				{
					eBody.append("<TD nowrap >Y</TD>");
				}
				else if(0==task.getIsfirsttask())
				{
					eBody.append("<TD nowrap >&nbsp;</TD>");
				}
			}
			if(task.getIslasttask()!=null)
			{
				if(1==task.getIslasttask())
				{
					eBody.append("<TD nowrap >Y</TD>");
				}
				else if(0==task.getIslasttask())
				{
					eBody.append("<TD nowrap >&nbsp;</TD>");
				}
			}
			
			
			String csvList="";
			ArrayList<TmstProjecthierarchytasksflow> next=map.get(task.getCurrenttaskid());
			if(next!=null)
			{
				for (TmstProjecthierarchytasksflow row : next) {
					Long nextTaskId=row.getTaskid();
					TtrnProjecthierarchy ttrnProjecthierarchy=mapProject.get(nextTaskId);
					csvList=csvList+","+ttrnProjecthierarchy.getTaskname();
				}
				if(csvList.length()>0)
				{
					csvList=csvList.substring(1);
				}
			}
			eBody.append("<TD nowrap >"+csvList+"</TD>");

			eBody.append("<TD nowrap >"+task.getTaskduration()+"</TD>");
			
			if(task.getTaskIsattachment()!=null)
			{
				if(1==task.getTaskIsattachment())
				{
					eBody.append("<TD nowrap >"+task.getTaskReferencedocname()+"</TD>");
				}
				else if(0==task.getTaskIsattachment())
				{
					eBody.append("<TD nowrap >&nbsp;</TD>");
				}
			}
			
			eBody.append("<TD nowrap >"+task.getTaskTaskinstructionremarks()+"</TD>");
			//eBody.append("<TD nowrap >"+task.getTask+"</TD>");
			java.util.Date date=task.getTaskstartdate();
			if(date==null)
			{
				eBody.append("<TD nowrap >&nbsp;</TD>");	
			}
			else
			{
				eBody.append("<TD nowrap >"+Utility.showDate_Report(date)+"</TD>");	
			}
			
			date=task.getTaskenddate();
			if(date==null)
			{
				eBody.append("<TD nowrap >&nbsp;</TD>");	
			}
			else
			{
				eBody.append("<TD nowrap >"+Utility.showDate_Report(date)+"</TD>");	
			}
			
			/*date=task.getActualtaskstartdate();
			if(date==null)
			{
				eBody.append("<TD nowrap >&nbsp;</TD>");	
			}
			else
			{
				eBody.append("<TD nowrap >"+Utility.showDate_Report(date)+"</TD>");	
			}
			
			date=task.getActualtaskenddate();
			if(date==null)
			{
				eBody.append("<TD nowrap >&nbsp;</TD>");	
			}
			else
			{
				eBody.append("<TD nowrap >"+Utility.showDate_Report(date)+"</TD>");	
			}
			
			
			long task_status=task.getTaskstatus();
			if(task_status==0)
			{
				eBody.append("<TD nowrap >&nbsp;</TD>");
			}else if(task_status==1)
			{
				eBody.append("<TD nowrap >Open</TD>");
			}else if(task_status==2)
			{
				eBody.append("<TD nowrap >Approved</TD>");
			}else if(task_status==3)
			{
				eBody.append("<TD nowrap >In RFI</TD>");	
			}*/
			eBody.append("</TR>");
		}
		eBody.append("</TABLE>");
		
		return eBody;
	}
	
	public void setProjectPlanVersionsView(ProjectPlanInstanceBean formBean)throws NpdException {

		
		Connection connection=null;
		
		try
		{
		connection=NpdConnection.getConnectionObject();
		
		WorkFlowDao workFlowDaoObj=new WorkFlowDao();
		
		//fetch all projectworkflowids for the project
		ArrayList<TtrnProjectworkflow> workflowList= workFlowDaoObj.getAllWorkflows(connection,formBean.getProjectId());
		
		
		//get all task fro all workflow
		ArrayList<TtrnProjecthierarchy> allTasksOfAllWF=workFlowDaoObj.getPlanVersionAllTasks(connection,formBean.getProjectId());
		//get all flow
		ArrayList<TmstProjecthierarchytasksflow> allFlow=workFlowDaoObj.getPlanVersionAllPrevDetails(connection,formBean.getProjectId());
		
		
		//set nextList for each task
		HashMap<String,ArrayList<TmstProjecthierarchytasksflow>> hashMap=new HashMap<String, ArrayList<TmstProjecthierarchytasksflow>>();  
		for (TmstProjecthierarchytasksflow projecthierarchytasksflow : allFlow) {
			long referenceTaskId=projecthierarchytasksflow.getPrevtaskid();
			long workflowId=projecthierarchytasksflow.getProjectworkflowid();
			String key=referenceTaskId+"-"+workflowId;
			ArrayList<TmstProjecthierarchytasksflow> temp=hashMap.get(key);
			if(temp==null)
			{
				temp=new ArrayList<TmstProjecthierarchytasksflow>();
			}
			temp.add(projecthierarchytasksflow);
			hashMap.put(key, temp);
		}
		
		//hashmap of taskId mapped to its bean
		HashMap<String, TtrnProjecthierarchy> taskMap=new HashMap<String, TtrnProjecthierarchy>();
		
		for (TtrnProjecthierarchy task : allTasksOfAllWF) {
			String key=null;
			key=task.getCurrenttaskid()+"-"+task.getProjectworkflowid();
			taskMap.put(key, task);
			task.setNextTaskList(hashMap.get(key));
		}
		//hasmap of projectworkflow id with its data
		HashMap< Long, ArrayList<TtrnProjecthierarchy>> data=new HashMap<Long, ArrayList<TtrnProjecthierarchy>>();
		for (TtrnProjectworkflow temp : workflowList) {
			data.put(temp.getProjectworkflowid(), new ArrayList<TtrnProjecthierarchy>());
		}
		
		
		
		for (TtrnProjecthierarchy task : allTasksOfAllWF) {
			
			Long workflowId=task.getProjectworkflowid();
			
			ArrayList<TtrnProjecthierarchy>temp=data.get(workflowId);
			//set CSV for Prev tasks
			
			String csvNext="";
			ArrayList<TmstProjecthierarchytasksflow> flow=task.getNextTaskList();
			
			if(flow!=null)
			{
				for (TmstProjecthierarchytasksflow projecthierarchytasksflow : flow) {
					String key=projecthierarchytasksflow.getTaskid()+"-"+projecthierarchytasksflow.getProjectworkflowid();
					TtrnProjecthierarchy nextTask=taskMap.get(key);
					if(nextTask!=null)
					{
						String taskname=nextTask.getTaskname();
						csvNext=csvNext+","+taskname;
					}
					
				}
			}
			
			if(csvNext.length()>0)
			{
				csvNext=csvNext.substring(1);
			}
			
			task.setCSV_nextTasksName(csvNext);
			
			temp.add(task);
			
		} 
		
		formBean.setVersionWorkflowsData(data);
		formBean.setVersionWorkflowList(workflowList);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in setProjectPlanVersionsView method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in setProjectPlanVersionsView method of "
					+ this.getClass().getSimpleName()+"  inserting mapping ") ;
		}
		finally{
			try {
				NpdConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				AppConstants.NPDLOGGER.error(e.getMessage()
						+ " Exception occured in setProjectPlanVersionsView method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(e));
				throw new NpdException("Exception occured in setProjectPlanVersionsView method of "
						+ this.getClass().getSimpleName());
			}
			
		}
	}

	public int updateTasks(ProjectPlanInstanceBean formBean, ArrayList<TaskInstanceBean> list) throws NpdException{
		Connection connection=null;
		int status=0;
		try{
			
			//call update method
			WorkFlowDao daoObj=new WorkFlowDao();
			connection=NpdConnection.getConnectionObject();
			connection.setAutoCommit(false);
			int op_status=daoObj.updateTasks(connection,formBean,list);
			
			if(op_status==1)
			{
				//success
				status=1;
			}
			else if(op_status==-1)
			{
				//failure
				status=-1;
			}

			connection.commit();
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in UpdateProjectPlanTaskInstance method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			try {
				connection.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new NpdException("Exception occured in updateTasks method of "
						+ this.getClass().getSimpleName()) ;
			}
			throw new NpdException("Exception occured in updateTasks method of "
					+ this.getClass().getSimpleName()) ;
		}
		finally{
			try {
				NpdConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				AppConstants.NPDLOGGER.error(e.getMessage()
						+ " Exception occured in updateTasks method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(e));
				throw new NpdException("Exception occured in updateTasks method of "
						+ this.getClass().getSimpleName()) ;
			}
		}
		return status;
	}
	
	public int setDiscardDraft(ProjectPlanInstanceBean formBean) throws NpdException {
		int status=-1;
		Connection conn= null;
		try
		{	
			//genarate start date(target) for each task
			AttachEditProjectPlanDao daoObj=new AttachEditProjectPlanDao(); 
			conn=NpdConnection.getConnectionObject();
			conn.setAutoCommit(false);
			String projectId=formBean.getProjectId();
			//String projectWorkflowId;
			//TtrnProjectworkflow projectWorkFlow=daoObj.getActiveProjectWorkflowId(conn,formBean.getProjectId());
			//projectWorkflowId=String.valueOf(projectWorkFlow.getProjectworkflowid());
			
			
			//int dao_status=daoObj.moveToHistoryAndCreateCopy(conn,projectId);
			int dao_status=daoObj.discardDraft(conn,projectId);
			if(dao_status==1)
			{
				status=1;
			}
			else
			{
				status=-1;
			}
		
			conn.commit();
		}
		catch(Exception ex)
		{
			try {
				conn.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				AppConstants.NPDLOGGER.error(e.getMessage()
						+ " Exception occured in setDiscardDraft method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(e));
				throw new NpdException("Exception occured in setDiscardDraft method of "
						+ this.getClass().getSimpleName()+"  inserting mapping ") ;
			}
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in setDiscardDraft method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in setDiscardDraft method of "
					+ this.getClass().getSimpleName()+"  inserting mapping ") ;
		}
		finally{
			try {
				NpdConnection.freeConnection(conn);
			} catch (Exception e) {
				e.printStackTrace();
				AppConstants.NPDLOGGER.error(e.getMessage()
						+ " Exception occured in setDiscardDraft method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(e));
				throw new NpdException("Exception occured in setDiscardDraft method of "
						+ this.getClass().getSimpleName()+"  inserting mapping ") ;
			}
		}
		return status;
	}

	public void setRoleEmployeeView(ProjectPlanInstanceBean formBean) throws NpdException 
	{
		
		Connection connection=null;
		
		try
		{
		connection=NpdConnection.getConnectionObject();
		//fetch all roles
	
		ArrayList<TtrnProjecthierarchy> tasksView=null;
		
		AttachEditProjectPlanDao daoObj=new AttachEditProjectPlanDao(); 
		
		//fecthing taskss
		ProjectPlanInstanceBean taskSearchDto=new ProjectPlanInstanceBean();
		taskSearchDto.setProjectWorkflowId(formBean.getProjectWorkflowId());
		
		/*taskSearchDto.setSearchTaskName(formBean.getSearchTaskName());
		String selectedStageId=formBean.getSelectedStageId();
		if(!(selectedStageId==null || "".equals(selectedStageId) || "-1".equals(selectedStageId)))
		{
			taskSearchDto.setSearchTaskOfStageId(selectedStageId);	
		}*/
		taskSearchDto.setSearchTask_roleHolder(formBean.getSearchTask_roleHolder());
		taskSearchDto.setSearchTask_assignedTo(formBean.getSearchTask_assignedTo());
		if(formBean.getTaskOption()==null || "".equals(formBean.getTaskOption()))
		{
			formBean.setTaskOption("ALL");
		}
		taskSearchDto.setTaskOption(formBean.getTaskOption());
		
		
		PagingSorting tasksPS=formBean.getTasksPS();
		if(tasksPS==null)
		{
			formBean.setTasksPS(tasksPS);
		}
		tasksPS.setPagingSorting(false, true);
		
		tasksPS.setDefaultifNotPresent("taskName", PagingSorting.increment, 1);
		taskSearchDto.setTasksPS(tasksPS);		
		
		tasksView=daoObj.getTasks(connection,taskSearchDto);
		
		//fetch roles from tasksView into roleView
		ArrayList<RoleEmployeeBean> roleView=null;
		Long[] roles=new Long[tasksView.size()];
		int i=0;
		TreeMap<String, RoleEmployeeBean> roleMap=new TreeMap<String, RoleEmployeeBean>();
		for (TtrnProjecthierarchy projecthierarchy : tasksView) {
			String key=projecthierarchy.getTaskstakeholderName()+sep+projecthierarchy.getTaskstakeholder();
			RoleEmployeeBean temp=roleMap.get(key);
			if(temp==null)
			{
				temp=new RoleEmployeeBean();
				temp.setRoleId(String.valueOf(projecthierarchy.getTaskstakeholder()));
				temp.setRoleName(projecthierarchy.getTaskstakeholderName());
			}
			
			if(temp.getEmployeeId()==null || "".equals(temp.getEmployeeId()) || "-1".equals(temp.getEmployeeId()))
			{
				temp.setEmployeeId(String.valueOf(projecthierarchy.getAssignedtouserid()));
			}
			
			roleMap.put(key, temp);
			roles[i++]=projecthierarchy.getTaskstakeholder();
		}
		
		ArrayList<TmEmployee> employees=null;
		employees=daoObj.getEmployeesRoleList(connection,roles);
		
		
		HashMap<Long,ArrayList<TmEmployee>> mapRole_Employees=new HashMap<Long, ArrayList<TmEmployee>>();
		for (TmEmployee employee : employees) {
			long roleId=employee.getOneRoleId();
			ArrayList<TmEmployee> temp=mapRole_Employees.get(new Long(roleId));
			if(temp==null)
			{
				temp=new ArrayList<TmEmployee>();
			}
			temp.add(employee);
			mapRole_Employees.put(new Long(roleId), temp);
		}
		roleView=new ArrayList<RoleEmployeeBean>();
		Set<String> keys=roleMap.keySet();
		for (String key : keys) {
			RoleEmployeeBean temp=roleMap.get(key);
			temp.setRoleUserList(mapRole_Employees.get(Long.parseLong(temp.getRoleId())));
			roleView.add(temp);
		}
		
		
		formBean.setRoleView(roleView);
		
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in setRoleEmployeeView method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in setRoleEmployeeView method of "
					+ this.getClass().getSimpleName());
		}
		finally{
			try {
				NpdConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				AppConstants.NPDLOGGER.error(e.getMessage()
						+ " Exception occured in setRoleEmployeeView method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(e));
				throw new NpdException("Exception occured in setRoleEmployeeView method of "
						+ this.getClass().getSimpleName());
			}
			
		}
	}
	final String sep="~~~~";

	private class MyComparator implements Comparator<String>
	{
//o1 : "roleId~~~~roleName"
		public int compare(String o1, String o2) {
			
			
			String str1=o1.substring(o1.lastIndexOf(sep)+1);
			String str2=o2.substring(o2.lastIndexOf(sep)+1);
			
			return str1.compareTo(str2);
			
			
		}
		
	}

	public int updateRoleEmployee(ProjectPlanInstanceBean formBean, ArrayList<RoleEmployeeBean> list) throws NpdException{
		Connection connection=null;
		int status=0;
		try{
			
			//call update method
			WorkFlowDao daoObj=new WorkFlowDao();
			connection=NpdConnection.getConnectionObject();
			connection.setAutoCommit(false);
			int op_status=daoObj.updateRoleEmployee(connection,formBean,list);
			
			if(op_status==1)
			{
				//success
				status=1;
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
	
	public void setTaskList(ProjectPlanInstanceBean formBean,String mode) throws NpdException {

		Session hibernateSession = null;
		Connection connection=null;
		
		try
		{
		connection=NpdConnection.getConnectionObject();
		ArrayList<TtrnProjecthierarchy> stageList=null;
		ArrayList<TtrnProjecthierarchy> tasksView=null;
		
		AttachEditProjectPlanDao daoObj=new AttachEditProjectPlanDao(); 
		
		hibernateSession = CommonBaseDao.beginTrans();
		
		//fecthing stages
		ProjectPlanInstanceBean stageSearchDto=new ProjectPlanInstanceBean();
		stageSearchDto.setProjectWorkflowId(formBean.getProjectWorkflowId());
		
		PagingSorting stagesPS=new PagingSorting(false,true);//since paging not required hence false
		formBean.setStagesPS(stagesPS);
		
		
		stagesPS.setDefaultifNotPresent("stageName", PagingSorting.increment, 1);
		stageSearchDto.setStagesPS(stagesPS);
		
		stageList=daoObj.getStages(connection,stageSearchDto);
		formBean.setStageList(stageList);
		
		
		//fecthing taskss
		ProjectPlanInstanceBean taskSearchDto=new ProjectPlanInstanceBean();
		taskSearchDto.setProjectWorkflowId(formBean.getProjectWorkflowId());
		
		taskSearchDto.setSearchTaskName(formBean.getSearchTaskName());
		String selectedStageId=formBean.getSelectedStageId();
		if(!(selectedStageId==null || "".equals(selectedStageId) || "-1".equals(selectedStageId)))
		{
			taskSearchDto.setSearchTaskOfStageId(selectedStageId);	
		}
		taskSearchDto.setSearchTask_roleHolder(formBean.getSearchTask_roleHolder());
		taskSearchDto.setSearchTask_assignedTo(formBean.getSearchTask_assignedTo());
		if(formBean.getTaskOption()==null || "".equals(formBean.getTaskOption()))
		{
			formBean.setTaskOption("ALL");
		}
		taskSearchDto.setTaskOption(formBean.getTaskOption());
		
		
		PagingSorting tasksPS=formBean.getTasksPS();
		if(tasksPS==null)
		{
			formBean.setTasksPS(tasksPS);
		}
		tasksPS.setPagingSorting(true, true);
		
		tasksPS.setDefaultifNotPresent("special2", PagingSorting.increment, 1);
		taskSearchDto.setTasksPS(tasksPS);		
		
		tasksView=daoObj.getTasks(connection,taskSearchDto);
		
		if(TASK_LIST_ASSIGN_USERID_MODE.equals(mode))
		{
			Long[] roles=new Long[tasksView.size()];  
			int i=0;
			for (TtrnProjecthierarchy projecthierarchy : tasksView) {
				roles[i++]=projecthierarchy.getTaskstakeholder();
			}
			ArrayList<TmEmployee> employees=null;
			employees=daoObj.getEmployeesRoleList(connection,roles);
			
			HashMap<Long,ArrayList<TmEmployee>> mapRole_Employees=new HashMap<Long, ArrayList<TmEmployee>>();
			for (TmEmployee employee : employees) {
				long roleId=employee.getOneRoleId();
				ArrayList<TmEmployee> temp=mapRole_Employees.get(new Long(roleId));
				if(temp==null)
				{
					temp=new ArrayList<TmEmployee>();
				}
				temp.add(employee);
				mapRole_Employees.put(new Long(roleId), temp);
			}
			
			
			for (TtrnProjecthierarchy task : tasksView) {
				task.setAssignedUserList(mapRole_Employees.get(task.getTaskstakeholder()));
			}
		}
		formBean.setTasksView(tasksView);
		setCheckListView(hibernateSession,formBean);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in setProjectPlanInstanceView method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in setProjectPlanInstanceView method of "
					+ this.getClass().getSimpleName()+"  inserting mapping ") ;
		}
		finally{
			try {
				NpdConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				AppConstants.NPDLOGGER.error(e.getMessage()
						+ " Exception occured in setProjectPlanInstanceView method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(e));
				throw new NpdException("Exception occured in setProjectPlanInstanceView method of "
						+ this.getClass().getSimpleName());
			}finally{
			hibernateSession.close();
			}
		}
	}
	
	public void setTasksViewForDeleteUpdate(ProjectPlanInstanceBean formBean,String mode) throws NpdException 
	{
		Session hibernateSession = null;
		Connection connection=null;
		
		try
		{
		connection=NpdConnection.getConnectionObject();
		ArrayList<TtrnProjecthierarchy> stageList=null;
		ArrayList<TtrnProjecthierarchy> tasksView=null;
		
		AttachEditProjectPlanDao daoObj=new AttachEditProjectPlanDao(); 
		
		hibernateSession = CommonBaseDao.beginTrans();
		
		
		
		//fecthing stages
		ProjectPlanInstanceBean stageSearchDto=new ProjectPlanInstanceBean();
		stageSearchDto.setProjectWorkflowId(formBean.getProjectWorkflowId());
		
		PagingSorting stagesPS=new PagingSorting(false,true);//since paging not required hence false
		formBean.setStagesPS(stagesPS);
		
		
		stagesPS.setDefaultifNotPresent("stageName", PagingSorting.increment, 1);
		stageSearchDto.setStagesPS(stagesPS);
		
		stageList=daoObj.getStages(connection,stageSearchDto);
		formBean.setStageList(stageList);
		
		
		//fecthing taskss
		ProjectPlanInstanceBean taskSearchDto=new ProjectPlanInstanceBean();
		taskSearchDto.setProjectWorkflowId(formBean.getProjectWorkflowId());
		
		taskSearchDto.setSearchTaskName(formBean.getSearchTaskName());
		String selectedStageId=formBean.getSelectedStageId();
		if(!(selectedStageId==null || "".equals(selectedStageId) || "-1".equals(selectedStageId)))
		{
			taskSearchDto.setSearchTaskOfStageId(selectedStageId);	
		}
		
		
		PagingSorting tasksPS=formBean.getTasksPS();
		if(tasksPS==null)
		{
			formBean.setTasksPS(tasksPS);
		}
		tasksPS.setPagingSorting(true, true);
		
		tasksPS.setDefaultifNotPresent("special2", PagingSorting.increment, 1);
		taskSearchDto.setTasksPS(tasksPS);		
		
		tasksView=daoObj.getTaskforRemoval(connection,taskSearchDto);
		
		//ArrayList<TtrnProjecthierarchy> list = daoObj.getAllTasks(hibernateSession, formBean.getProjectWorkflowId());
		/*for (TtrnProjecthierarchy task : tasksView) {
			ArrayList<TtrnProjecthierarchy> possPrevTasks=getPossiblePreviousTasks(String.valueOf(task.getCurrenttaskid()),list);
			if(possPrevTasks==null)
			{
				 possPrevTasks= new ArrayList<TtrnProjecthierarchy>();
			}	
			task.setValidPrevTaskList(possPrevTasks);
		}*/
		
		formBean.setTasksView(tasksView);

		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in setProjectPlanInstanceView method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in setProjectPlanInstanceView method of "
					+ this.getClass().getSimpleName()+"  inserting mapping ") ;
		}
		finally{
			try {
				NpdConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				AppConstants.NPDLOGGER.error(e.getMessage()
						+ " Exception occured in setProjectPlanInstanceView method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(e));
				throw new NpdException("Exception occured in setProjectPlanInstanceView method of "
						+ this.getClass().getSimpleName());
			}finally{
			hibernateSession.close();
			}
		}
	}
	
	//For Multiple Task Deletion-Rohit Verma NEW CR
	public int deleteTasks(ArrayList DeleteTaskIdList, ProjectPlanInstanceBean formBean) throws Exception  
	{
		int listIntefrace = 0;
		Connection connection=null;
		WorkFlowDao daoObj=new WorkFlowDao();  
		try
		{
			connection=NpdConnection.getConnectionObject();
			for(int i=0;i<DeleteTaskIdList.size();i++)
			{
				TtrnProjecthierarchy dto=new TtrnProjecthierarchy();
				
				dto.setProjectworkflowid(Long.parseLong(formBean.getProjectWorkflowId()));
				dto.setCurrenttaskid(Long.parseLong((String)DeleteTaskIdList.get(i)));

				//call delete method			
				connection.setAutoCommit(false);
				listIntefrace=daoObj.deleteTaskInstance(connection,dto);
				connection.commit();
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}finally
		{
			try
			{
				NpdConnection.freeConnection(connection);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return listIntefrace;
	}
	/*
	 * for CR:
	 */
	public ArrayList<TtrnProjecthierarchy> getPossiblePreviousTasks(String currentTaskId,ArrayList<TtrnProjecthierarchy> list) 
				throws NpdException
	{
		
		TreeSet<TtrnProjecthierarchy> prevTasks=new TreeSet<TtrnProjecthierarchy>(new TaskNameComparator());
		try
		{
		AttachEditProjectPlanDao daoObj=new AttachEditProjectPlanDao(); 
		
		
		
		
		//write algo gere
		//ArrayList<TtrnProjecthierarchy> toBeRemovedTasks=new ArrayList<TtrnProjecthierarchy>();
		
		//putting all task in map based on prev task id for quick retrieval
		TreeMap<Long, ArrayList<TtrnProjecthierarchy>> map=new TreeMap<Long, ArrayList<TtrnProjecthierarchy>>();
		for (TtrnProjecthierarchy row_list : list) {
			
			ArrayList<TmstProjecthierarchytasksflow> prevList=row_list.getPrevTaskList();
			if(prevList!=null)
			{
				for (TmstProjecthierarchytasksflow projecthierarchytasksflow : prevList) {
					long prevTaskId=projecthierarchytasksflow.getPrevtaskid();
					ArrayList temp=map.get(prevTaskId);
					if(temp==null)
					{
						temp=new ArrayList();
					}
					temp.add(row_list);
					map.put(prevTaskId,temp);
				}
			}
		}
		
		
		//making queue of all child tasks
		ArrayList<Long> queueToBeRemoveTaskId=new ArrayList<Long>();
		int index_queue=-1;
		queueToBeRemoveTaskId.add(new Long(currentTaskId));
		index_queue++;
		
		/*do
		{
			Long taskId=(Long)queueToBeRemoveTaskId.get(index_queue++);
			ArrayList<TtrnProjecthierarchy> temp=map.get(taskId);
			if(temp!=null)
			{
				for (TtrnProjecthierarchy projecthierarchy : temp) {
					queueToBeRemoveTaskId.add(projecthierarchy.getCurrenttaskid());
				}
			}
		}
		while(index_queue<queueToBeRemoveTaskId.size());*/
		
		HashSet<Long> allReadyInQueue=new HashSet<Long>();  
		allReadyInQueue.add(new Long(currentTaskId));
		do
		{
			Long taskId=(Long)queueToBeRemoveTaskId.get(index_queue++);
			ArrayList<TtrnProjecthierarchy> temp=map.get(taskId);
			if(temp!=null)
			{
				for (TtrnProjecthierarchy projecthierarchy : temp) {
					if(!allReadyInQueue.contains(new Long(projecthierarchy.getCurrenttaskid())))
					{
						queueToBeRemoveTaskId.add(projecthierarchy.getCurrenttaskid());
						allReadyInQueue.add(new Long(projecthierarchy.getCurrenttaskid()));				
					}
					
				}
			}
		}
		while(index_queue<queueToBeRemoveTaskId.size());
		
		//making hashmap of all tasks
		HashMap<Long, TtrnProjecthierarchy> hashMap_prevList=new HashMap<Long, TtrnProjecthierarchy>();
		for (TtrnProjecthierarchy row_list : list) {
			hashMap_prevList.put(row_list.getCurrenttaskid(),row_list);
		}
		
		//remove particular tasks from hashmap
		for (Long temptaskId : queueToBeRemoveTaskId) {
			hashMap_prevList.remove(temptaskId);
		}
		
		//converting from hashmap to Treeset to sort on basis of taskname
		prevTasks.addAll(hashMap_prevList.values());
		
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new NpdException(ex);
		}
		
		// converting to arraylist
		return new ArrayList<TtrnProjecthierarchy>(prevTasks);
		
	}

	public int updatePrevTask(ArrayList<TtrnProjecthierarchy> inputlist)
	{
		int updateStatus = 0;
		Connection connection=null;
		try
		{
			connection=NpdConnection.getConnectionObject();
			connection.setAutoCommit(false);
			WorkFlowDao daoObj=new WorkFlowDao();  
			for(int i=0;i<inputlist.size();i++)
			{
				TtrnProjecthierarchy dto=inputlist.get(i);
				daoObj.updatePrevTask(connection,dto);
			}
			connection.commit();
		}
		catch(Exception ex)
		{
			try {
				connection.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ex.printStackTrace();	
		}finally
		{
			try {
				NpdConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return updateStatus;
	}
	
	//  get Stage Master,Role Master ,Task Master in excel
	public void getMasterExcel(ProjectPlanInstanceBean formBean) throws NpdException {

		
		Session hibernateSession = null;
		Connection connection=null;
		try
		{
			connection=NpdConnection.getConnectionObject();
			hibernateSession = CommonBaseDao.beginTrans();
			//get Stage Master,Role Master ,Task Master in excel
			
			HSSFWorkbook wb = new HSSFWorkbook();

			HSSFSheet projectplansheet = wb.createSheet("ProjectPlan_TaskList");
			HSSFSheet varproductSheet = wb.createSheet("Product");
			HSSFSheet stageSheet = wb.createSheet("Stages List");
			HSSFSheet roleSheet = wb.createSheet("Roles List");
			HSSFSheet employeeSheet = wb.createSheet("Employee Master");
			HSSFSheet referenceDocSheet = wb.createSheet("Reference Doc List");

	        HSSFCellStyle headerCellStyle = wb.createCellStyle();
	        HSSFFont boldFont = wb.createFont();
	        boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	        headerCellStyle.setFont(boldFont);
	        
	        ExcelCreator creator=new ExcelCreator();       
			//START :making sheet from stage data
	        creator.makeStageSheet(connection,formBean.getProjectWorkflowId(),stageSheet,headerCellStyle);
	        //END :making sheet from stage data			
			
	        //	      START :making sheet from role data	
	        creator.makeRoleSheet(hibernateSession,roleSheet,headerCellStyle);
	        //	      END :making sheet from role data
	        
	        //
	        //	      START :making sheet from role data	
	        creator.makeEmployeeSheet(connection,employeeSheet,headerCellStyle);
	        //	      END :making sheet from role data
	        
	        
	        //		      START :making sheet from reference doc data	
	        creator.makeReferenceDocSheet(hibernateSession,referenceDocSheet,headerCellStyle);
	        //
			
			
	        //fetch project plan data
	        TtrnProject ttrnProject = new TtrnProject();
	    	CommonBaseDao commonBaseDao=new CommonBaseDao();
	    	ttrnProject = (TtrnProject) commonBaseDao.findById((Long.parseLong(formBean.getProjectId())),
	    	HibernateBeansConstants.HBM_PROJECT, hibernateSession);
//	      START :making sheet from project data
	    	{
	    		HSSFSheet productSheet = varproductSheet;
		    	
		    	HSSFRow productrow = productSheet.createRow(0);
		        
		        HSSFCell productcell = productrow.createCell(0);
		        productcell.setCellStyle(headerCellStyle);
		        productcell.setCellValue(new HSSFRichTextString("Project Id"));
	
		        productcell = productrow.createCell(1);
		        productcell.setCellStyle(headerCellStyle);
		        productcell.setCellValue(new HSSFRichTextString("Project Name"));
	
		        productrow = productSheet.createRow(1);
		        
		        productcell = productrow.createCell(0);
		        HSSFRichTextString projectid = new HSSFRichTextString(formBean.getProjectId());
		        productcell.setCellValue(projectid);
		        
		        productcell = productrow.createCell(1);
		        HSSFRichTextString projectname = new HSSFRichTextString(ttrnProject.getProjectName());
		        productcell.setCellValue(projectname);
	    	}
//		      END :making sheet from project  data
	        
	    	//		      START :making sheet from project plan data
	    	creator.makeProjectPlanSheetFromTemplate(connection,formBean.getProjectWorkflowId(),projectplansheet,headerCellStyle);
	    	//	      END :making sheet from project plan data	       
			
			formBean.setEditExcelWorkbook(wb);
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in getMasterExcel method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in getMasterExcel method of "
					+ this.getClass().getSimpleName());
		}
		finally{
			try {
				NpdConnection.freeConnection(connection);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				AppConstants.NPDLOGGER.error(e.getMessage()
						+ " Exception occured in getMasterExcel method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(e));
				throw new NpdException("Exception occured in getMasterExcel method of "
						+ this.getClass().getSimpleName());
			}finally
			{
				try{
					CommonBaseDao.closeTrans(hibernateSession);
				}
				catch(Exception e)
				{
					e.printStackTrace();
					AppConstants.NPDLOGGER.error(e.getMessage()
							+ " Exception occured in getMasterExcel method of "
							+ this.getClass().getSimpleName()+AppUtility.getStackTrace(e));
					throw new NpdException("Exception occured in getMasterExcel method of "
							+ this.getClass().getSimpleName());
				}
			}
			
		}
	}

//	getTemplateExcel
	public void getTemplateExcel(ProjectPlanInstanceBean formBean) throws NpdException {

		Connection connection=null;
		try
		{
			connection=NpdConnection.getConnectionObject();
			
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet projectplansheet = wb.createSheet("ProjectPlan_TaskList");
	        HSSFCellStyle headerCellStyle = wb.createCellStyle();
	        HSSFFont boldFont = wb.createFont();
	        boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	        headerCellStyle.setFont(boldFont);
	        
	        ExcelCreator creator=new ExcelCreator();
	        //		      START :making sheet from project plan data
	        creator.makeProjectPlanSheetFromTemplate(connection,formBean.getProjectWorkflowId(),projectplansheet,headerCellStyle);
	    	//	      END :making sheet from project plan data	   
			formBean.setTemplateWorkbook(wb);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in getTemplateExcel method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in getTemplateExcel method of "
					+ this.getClass().getSimpleName());
		}
		finally{
			try {
				NpdConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				AppConstants.NPDLOGGER.error(e.getMessage()
						+ " Exception occured in getTemplateExcel method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(e));
				throw new NpdException("Exception occured in getTemplateExcel method of "
						+ this.getClass().getSimpleName());
			}
			
		}
	}
	
	public void loadExcelProductPlan(ProjectPlanInstanceBean formBean) throws NpdException{
		
		//chk whther file for thios project alreday exist or not
		//if exist delete it and insert new data
		//if not insert new data
		
		Connection connection=null;
		int status_loadExcelProductPlan=0;
		UploadProductPlanBean uploadBean=formBean.getUploadProductPlanBean();
		ActionMessages messages=new ActionMessages();
		try
		{
			connection=NpdConnection.getConnectionObject();
			AttachEditProjectPlanDao daoObj=new AttachEditProjectPlanDao(); 
			
			connection.setAutoCommit(false);
			daoObj.removePrevCurrentEntry(connection,formBean.getProjectId());
			connection.commit();
			
			ExcelCreator creator=new ExcelCreator();
			//Validate Attachment Start
			
			FormFile attachment = uploadBean.getUploadFile();
			String filepath = attachment.getFileName();
			
			int isValid = 1;
			String templateFilePath=Messages.getMessageValue("TEMPLATE_DIR_PATH") + AppConstants.UploadExcelPlanTemplateFileName;
			int status = creator.validateUploadedExcel(attachment,templateFilePath
					,messages);

			if (status == 1) {
				isValid = 0;
				messages.add("sheet_mismatch", new ActionMessage(
						"errors.excel.sheet.mismatch"));
				AppConstants.NPDLOGGER.info("errors.excel.sheet.mismatch");
				
			} else if (status == 2) {
				isValid = 0;
				messages.add("sheetName_mismatch", new ActionMessage(
						"errors.excel.sheetname.mismatch"));
				AppConstants.NPDLOGGER.info("errors.excel.sheetname.mismatch");
				
			} else if (status == 3) {
				isValid = 0;
				messages.add("sheet_blank", new ActionMessage(
						"errors.excel.sheet.blank"));
				AppConstants.NPDLOGGER.info("errors.excel.sheet.blank");
				
			} else if (status == 4) {
				isValid = 0;
				messages.add("colNum_mismatch", new ActionMessage(
						"errors.excel.columnnumber.mismatch"));
				AppConstants.NPDLOGGER.info("errors.excel.columnnumber.mismatch");
				
			} else if (status == 5) {
				isValid = 0;
				messages.add("colNanme_mismatch", new ActionMessage(
						"errors.excel.columnname.mismatch"));
				AppConstants.NPDLOGGER.info("errors.excel.columnname.mismatch");
				
			} else if (status == 7) {
				isValid = 0;
				messages.add("invalid_excel", new ActionMessage(
						"errors.excel.invalid.excel"));
				AppConstants.NPDLOGGER.info("errors.excel.invalid.excel");
				
			} else if (status == 8) {
				isValid = 0;
				messages.add("invalidObjInFile", new ActionMessage(
						"errors.excel.invalid.filehasobject"));
				AppConstants.NPDLOGGER.info("errors.excel.invalid.filehasobject");
				
			} else if (status == 9) {
				isValid = 0;
				
				AppConstants.NPDLOGGER.info("errors.excel.column.length");
				
			}
			if(isValid == 0)
			{
				formBean.getUploadProductPlanBean().setMessages(messages);
				formBean.getUploadProductPlanBean().setLoadExcelProductPlan_status(
											(UploadProductPlanBean.loadExcelProductPlan_status_ValidationError)
										);
				return;
			}
			
			//now file is valid
				
			
			//Validate Attachment End
			
			
			
			
			connection.setAutoCommit(false);
			UploadProductPlanBean dto=new UploadProductPlanBean();
			
			dto.setUploadFile(uploadBean.getUploadFile());
			
			dto.setProductId(Long.parseLong(formBean.getProjectId()));
			long fileId=daoObj.getFileId(connection,dto);
			if(fileId>0)
			{
				connection.commit();
			}
			
			//insert data with fileId
			int statusSave=0;
			statusSave = saveUploadedFileInfo(attachment,connection,fileId,templateFilePath);

			if(statusSave>0)
			{
				messages.add("attachment", new ActionMessage(
											"upload.success"));
				status_loadExcelProductPlan=UploadProductPlanBean.loadExcelProductPlan_status_Success;
				uploadBean.setLoadExcelProductPlan_status(status_loadExcelProductPlan);
				
			}
			else
			{
				messages.add("attachment", new ActionMessage(
												"upload.failure"));
				status_loadExcelProductPlan=UploadProductPlanBean.loadExcelProductPlan_status_InsertionError;
				uploadBean.setLoadExcelProductPlan_status(status_loadExcelProductPlan);
			}
					}
		catch(Exception ex)
		{
			ex.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in loadExcelProductPlan method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in loadExcelProductPlan method of "
					+ this.getClass().getSimpleName());
		}
		finally{
			try {
				
				NpdConnection.freeConnection(connection);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				AppConstants.NPDLOGGER.error(e.getMessage()
						+ " Exception occured in loadExcelProductPlan method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(e));
				throw new NpdException("Exception occured in loadExcelProductPlan method of "
						+ this.getClass().getSimpleName());
			}
			
			
			
		}
		
	}
	
	/**
	 * @param templateStream 
	 * @method saveUploadedFileInfo
	 * @purpose save uploaded file data in staging table in database
	 * @param FormFile,
	 *            filepath, userName
	 * @param excel_uploadPath,
	 *            uploadedFilePath
	 * @return
	 * @throws NpdException
	 */
	public int saveUploadedFileInfo(FormFile attachment,Connection connection,Long fileId, String templateFilePath)
			throws NpdException{
		AppConstants.NPDLOGGER.info("UploadServiceImpl's saveUploadedFileInfo() completed");
		int sheetCol, sheetRow, retCode = 0, checkCode = 0;
		ArrayList<Object[][]> excelDataList = new ArrayList<Object[][]>();
		int thisSaveCode=0;
		int saveStatusCode=0;
		try {
			String fileName = null;
			
				if (attachment != null) {
					fileName = attachment.getFileName();
									}

				if (fileName != null) {
					System.out.println("filename is : " + fileName);

					HSSFWorkbook workbook = null;
					HSSFSheet sheet = null;
					HSSFRow rowInSheet = null;
					HSSFCell cellInSheet = null;

					
					workbook = new HSSFWorkbook(attachment.getInputStream());
					sheet = workbook.getSheetAt(0);
					sheetRow = sheet.getLastRowNum();
					sheetCol = sheet.getRow(1).getLastCellNum();
					int numberOfTemplateRows = 1;
					
					FileInputStream templateStream=new FileInputStream(templateFilePath);
					/*
					 * notSaveColumn contains column noo starting from index 1
					 */
					Set<String> notSaveColumn=new HashSet<String>();
					HSSFWorkbook templateWorkbook = new HSSFWorkbook(templateStream);
					HSSFSheet templateFileSheet=templateWorkbook.getSheetAt(0);
					HSSFRow templateNoReadRow = templateFileSheet.getRow(2);
					if (templateNoReadRow != null) {
						int templateColumnCount=templateNoReadRow.getLastCellNum();
						for(int col=0;col<templateColumnCount;col++)
						{
							HSSFCell templateCell = templateNoReadRow.getCell(col);
							if (templateCell!=null && !"".equals(templateCell.toString().trim()) ) {
								notSaveColumn.add(String.valueOf(col+1));
							}
						}
					}
					
					int columnActualSave=sheetCol-notSaveColumn.size();
					Object excelData[][] = new Object[sheetRow
							- numberOfTemplateRows + 1][columnActualSave];
					for (int r = numberOfTemplateRows; r <= sheetRow; r++) {
						rowInSheet = sheet.getRow(r);
						int columIndex=0;
						for (int c = 1; c < sheetCol + 1; c++) {
							if(notSaveColumn.contains(String.valueOf(c)))
							{
								continue;
							}
							if (rowInSheet != null) {
								cellInSheet = rowInSheet.getCell(c - 1);
								if (cellInSheet != null) {
									if (cellInSheet.getCellType() == 0)
									{
										excelData[r - 1][columIndex++] = Utility.convertWithOutE_WithOutDotZero(String.valueOf(cellInSheet.getNumericCellValue()));
										/*NumberFormat formatter = new DecimalFormat("0");
										excelData[r - 1][columIndex++] = formatter
													.format(cellInSheet.getNumericCellValue());*/
									}else
									{	excelData[r - 1][columIndex++] = cellInSheet
											.toString().trim();
									}
									
								} else {
									excelData[r - 1][columIndex++] = "";
								}
							} else {
								excelData[r - 1][columIndex++] = "";
							}
						}
					}
					excelDataList.add(excelData);
				}
				//if (checkCode == 1) {
					AttachEditProjectPlanDao daoObj=new AttachEditProjectPlanDao();
					saveStatusCode = daoObj.saveUploadedFileToTemporaryTable(
							excelDataList,connection,fileId);
					if(saveStatusCode>0)
					{
						thisSaveCode= 1;
					}
					else
					{
						thisSaveCode= 0;
					}

				/*}
				else
				{
					thisSaveCode= 0;
				}*/

			

			
			AppConstants.NPDLOGGER.info("Completed..");
			return thisSaveCode;
		} catch (Exception ed) {
			ed.printStackTrace();
			AppConstants.NPDLOGGER.error("Error while getting saveUploadedFileInfo "
					+ ed.getMessage());
			throw new NpdException(ed);
		}

		finally {
			AppConstants.NPDLOGGER.info("UploadServiceImpl's saveUploadedFileInfo() completed");
		}

	}

	public void validateExcelProductPlan(ProjectPlanInstanceBean formBean) throws NpdException{
		//call db proc to validate all things
		//if correct then check workflow tree for cyclic

		
		Connection conn=null;
		try
		{	
			conn=NpdConnection.getConnectionObject();
			conn.setAutoCommit(false);
			AttachEditProjectPlanDao daoObj=new AttachEditProjectPlanDao();
//			call db proc to validate all things
			int errorsFound=daoObj.validateExcelProductPlan(conn,formBean);
			conn.commit();
			
			if(errorsFound==1)
			{
				formBean.getUploadProductPlanBean().setValidateExcelProductPlan_status(UploadProductPlanBean.validateExcelProductPlan_status_ProcValiError);
			}
			else//			if correct then check workflow tree for cyclic
			{
				
				int cyclicFound=0;
				//write code here
				
				
				FindCycles find=new FindCycles();
				
				
				HashMap<Long,Integer> map_TaskIds_newIds=new HashMap<Long, Integer>();
				Long[] mapping_NewIds_TaskIds=null;
				
				//basic algo
				//get task data
				//populate both mapping map_TaskIds_newIds and mapping_NewIds_TaskIds
				//populate find.mapping  : in mapping for each task there will be array of adjacent task which are its previous
											// so for each edge "from" will be a task and "to" will be its previous task 
				//call doDFS on find
				//fetch parent and (edgeFrom,edgeTo)  : use these to find cycles
				
				
//				get task data
				ArrayList<PlanExcelUploadDto> uploadedPlan=daoObj.getErrorLogTasks(conn, formBean.getProjectId());
				
//				populate both mapping map_TaskIds_newIds and mapping_NewIds_TaskIds
				
				int vertexIndex=-1;
				mapping_NewIds_TaskIds=new Long[uploadedPlan.size()];
				for (PlanExcelUploadDto dto : uploadedPlan) {
					Long taskId=Long.parseLong(dto.getTaskid().trim());
					int newId=++vertexIndex;
					map_TaskIds_newIds.put(taskId, newId);
					mapping_NewIds_TaskIds[newId]=taskId;
				}
//				populate find.mapping
				Integer mapping[][]=new Integer[uploadedPlan.size()][];
				for (PlanExcelUploadDto dto : uploadedPlan) {
					Long taskId=Long.parseLong(dto.getTaskid().trim());
					String csvPrevTaskIds=dto.getPrev_task_ids();
					if(csvPrevTaskIds==null || csvPrevTaskIds.trim().equals(""))
					{
						mapping[map_TaskIds_newIds.get(taskId)]=new Integer[0];
						continue;
					}
					String [] prevIds=csvPrevTaskIds.split(",");
					ArrayList<Integer> current_prevIds=new ArrayList<Integer>();
					for(int i_prevIds=0;i_prevIds<prevIds.length;i_prevIds++)
					{
						if(!prevIds[i_prevIds].trim().equals(""))
						{
							current_prevIds.add(map_TaskIds_newIds.get(Long.parseLong(prevIds[i_prevIds].trim())));
						}
					}
					//storing from current_prevIds to Integer[]
					Integer[] curr_prevs=new Integer[current_prevIds.size()];
					int index=-1;
					for (Integer prevId : current_prevIds) {
						curr_prevs[++index]=prevId;
					}
					mapping[map_TaskIds_newIds.get(taskId)]=curr_prevs;
					
				}
				
				find.mapping=mapping;
				
//				call doDFS on find
				find.doDFS();
//				fetch parent and (edgeFrom,edgeTo)  : use these to find cycles
				Integer parent[]=find.parent;
				ArrayList<Integer> backedgeFrom = find.backedgeFrom;  
				ArrayList<Integer> backedgeTo = find.backedgeTo;
				//find cycles
				
				//set of cycle :each element in cycle Set is an arraylist of Integer comprosing cycle 
				ArrayList<ArrayList<Long>> cycles=new ArrayList<ArrayList<Long>>();
				if(backedgeFrom.size()>0)
				{
					System.err.println("Cycle Detected");
					cyclicFound=1;
					//populate Set cycles
					for(int i_bachedge=0;i_bachedge<backedgeFrom.size();i_bachedge++)
					{
						Integer from=backedgeFrom.get(i_bachedge);
						Integer to=backedgeTo.get(i_bachedge);
						
						/*//go from to from edge to to using parent array
						ArrayList<Long> cycle=new ArrayList<Long>();
						Integer temp=from;
						Integer oldtemp;
						do
						{
							cycle.add(mapping_NewIds_TaskIds[temp]);
							oldtemp=temp;
							temp=parent[temp];
							
						}while(oldtemp.intValue()!=to.intValue());
						cycles.add(cycle);*/
						
						ArrayList<Long> cycle=new ArrayList<Long>();
						Integer temp=from;
						
						cycle.add(mapping_NewIds_TaskIds[to]);
						while(temp.intValue()!=to.intValue())
						{
							cycle.add(mapping_NewIds_TaskIds[temp]);
							temp=parent[temp];
						}
						cycles.add(cycle);
					}
				}
				else
				{
					cyclicFound=0;
				}

				
				
				if(cyclicFound==1)
				{
					
					formBean.getUploadProductPlanBean().setValidateExcelProductPlan_status(UploadProductPlanBean.validateExcelProductPlan_status_CyclicError);
					daoObj.saveCycleError(conn,formBean,cycles);
					System.err.println(cycles);
				}
				else
				{
					formBean.getUploadProductPlanBean().setValidateExcelProductPlan_status(UploadProductPlanBean.validateExcelProductPlan_status_Success);
				}
								
			}
			if(formBean.getUploadProductPlanBean().getValidateExcelProductPlan_status()>0)
			{
				daoObj.setFileUploadStatus(conn,formBean,UploadProductPlanBean.DB_VALIDATE_SUCCESS);
				conn.commit();
				
			}
			else
			{
				daoObj.setFileUploadStatus(conn,formBean,UploadProductPlanBean.DB_VALIDATE_FAILURE);
				conn.commit();
			}
		}
		catch(Exception ex)
		{
			try {
				conn.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ex.printStackTrace();
			
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in validateExcelProductPlan method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in validateExcelProductPlan method of "
					+ this.getClass().getSimpleName()) ;
		}
		finally{
			try {
				
				NpdConnection.freeConnection(conn);
			} catch (Exception e) {
				e.printStackTrace();
				AppConstants.NPDLOGGER.error(e.getMessage()
						+ " Exception occured in validateExcelProductPlan method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(e));
				throw new NpdException("Exception occured in validateExcelProductPlan method of "
						+ this.getClass().getSimpleName()) ;
			}
		}
		
	}

	public void replaceExcelProductPlan(ProjectPlanInstanceBean formBean) throws NpdException {
		Connection conn=null;
		try
		{	
			conn=NpdConnection.getConnectionObject();
			conn.setAutoCommit(false);
			AttachEditProjectPlanDao daoObj=new AttachEditProjectPlanDao();
//			call db proc to validate all things
			int status=daoObj.replaceExcelProductPlan(conn,formBean);
			conn.commit();
			
			
			if(status==1)
			{
				conn.commit();
				formBean.getUploadProductPlanBean().setReplaceExcelProductPlan_status(UploadProductPlanBean.replaceExcelProductPlan_status_Success);
			}
			else
			{
				conn.rollback();
				formBean.getUploadProductPlanBean().setReplaceExcelProductPlan_status(UploadProductPlanBean.replaceExcelProductPlan_status_Failure);
			}
		}
		catch(Exception ex)
		{
			try {
				conn.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ex.printStackTrace();
			
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in replaceExcelProductPlan method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in replaceExcelProductPlan method of "
					+ this.getClass().getSimpleName()) ;
		}
		finally{
			try {
				
				NpdConnection.freeConnection(conn);
			} catch (Exception e) {
				e.printStackTrace();
				AppConstants.NPDLOGGER.error(e.getMessage()
						+ " Exception occured in replaceExcelProductPlan method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(e));
				throw new NpdException("Exception occured in replaceExcelProductPlan method of "
						+ this.getClass().getSimpleName()) ;
			}
		}
	}

	public void loadUploadExcelProgressDetails(ProjectPlanInstanceBean formBean) throws NpdException {

		Connection conn=null;
		try
		{
			conn=NpdConnection.getConnectionObject();
			AttachEditProjectPlanDao daoObj=new AttachEditProjectPlanDao();
			UploadProductPlanBean dto=daoObj.loadUploadExcelProgressDetails(conn,formBean.getProjectId());
			
			UploadProductPlanBean uploadBean=formBean.getUploadProductPlanBean();
			if(dto!=null)
			{
				uploadBean.setUploadFileName(dto.getUploadFileName());
				uploadBean.setProgress(dto.getProgress());
				uploadBean.setFileId(dto.getFileId());
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in loadUploadExcelProgressDetails method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in loadUploadExcelProgressDetails method of "
					+ this.getClass().getSimpleName()) ;
		}
		finally{
			try {
				
				NpdConnection.freeConnection(conn);
			} catch (Exception e) {
				e.printStackTrace();
				AppConstants.NPDLOGGER.error(e.getMessage()
						+ " Exception occured in loadUploadExcelProgressDetails method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(e));
				throw new NpdException("Exception occured in loadUploadExcelProgressDetails method of "
						+ this.getClass().getSimpleName()) ;
			}
		}
		
	}

	public void getErrorLogExcel(ProjectPlanInstanceBean formBean) throws NpdException {

		Connection connection=null;
		try
		{
			connection=NpdConnection.getConnectionObject();
			
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet projectplansheet = wb.createSheet("ProjectPlan_TaskList");
	        HSSFCellStyle headerCellStyle = wb.createCellStyle();
	        HSSFFont boldFont = wb.createFont();
	        boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	        headerCellStyle.setFont(boldFont);
	        
	        ExcelCreator creator=new ExcelCreator();
	        //		      START :making sheet from project plan data
	        creator.makeErrorLogProjectPlanSheet(connection,formBean,projectplansheet,headerCellStyle);
	    	//	      END :making sheet from project plan data	   
			formBean.setErrorLogWorkbook(wb);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in getTemplateExcel method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in getTemplateExcel method of "
					+ this.getClass().getSimpleName());
		}
		finally{
			try {
				NpdConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				AppConstants.NPDLOGGER.error(e.getMessage()
						+ " Exception occured in getTemplateExcel method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(e));
				throw new NpdException("Exception occured in getTemplateExcel method of "
						+ this.getClass().getSimpleName());
			}
			
		}
	}
	
	public void setProductAndCheckListView(ProjectPlanInstanceBean formBean) throws NpdException {
		
		Connection conn=null;
		Session hibernateSession = null;
		try
		{	
			String projectId=formBean.getProjectId();
			AttachEditProjectPlanDao daoObj=new AttachEditProjectPlanDao(); 
			conn = NpdConnection.getConnectionObject();
			
			if(formBean.getProjectWorkflowId()==null || "".equals(formBean.getProjectWorkflowId().trim()))
			{
				String projectWorkflowId;
				TtrnProjectworkflow projectWorkFlow=daoObj.getDraftProjectWorkflowId(conn,formBean.getProjectId());
				projectWorkflowId=String.valueOf(projectWorkFlow.getProjectworkflowid());
				formBean.setProjectWorkflowId(projectWorkflowId);
				String attachHistoryStatus=projectWorkFlow.getAttachHistoryStatus();
				if("editingFinalized".equals(attachHistoryStatus))
				{
					formBean.setAttachMode("editingFinalized");
				}
				else if("attachNew".equals(attachHistoryStatus))
				{
					formBean.setAttachMode("attachNew");
				}
			}
			
			
			//fetch info
			//fect project info
			
			CommonBaseDao commonBaseDao = (CommonBaseDao) BaseDaoFactory
														.getInstance(DaoConstants.BASE_DAO_CLASS);
			
			hibernateSession = CommonBaseDao.beginTrans();
			TtrnProject ttrnProject = new TtrnProject();
			
			ttrnProject = (TtrnProject) commonBaseDao.findById(Long.parseLong(formBean.getProjectId()),
					HibernateBeansConstants.HBM_PROJECT, hibernateSession);
			
			
			formBean.setProject(ttrnProject);
			
			if(formBean.getUploadProductPlanBean().getProgress()>=UploadProductPlanBean.PROGRESS_REPLACE_SUCCESS)
			{
				setCheckListView(hibernateSession,formBean);
			}
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in setProductAndCheckListView method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in setProductAndCheckListView method of "
					+ this.getClass().getSimpleName()) ;
		}
		finally{
			
			try {
				NpdConnection.freeConnection(conn);
			} catch (Exception e) {
				e.printStackTrace();
				AppConstants.NPDLOGGER.error(e.getMessage()
						+ " Exception occured in setProductAndCheckListView method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(e));
				throw new NpdException("Exception occured in setProductAndCheckListView method of "
						+ this.getClass().getSimpleName()) ;
			}finally{
				hibernateSession.close();
			}
		}
		
	}
	

}
