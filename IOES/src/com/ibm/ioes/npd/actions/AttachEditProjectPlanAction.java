package com.ibm.ioes.npd.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

import com.ibm.ioes.npd.beans.ProjectPlanInstanceBean;
import com.ibm.ioes.npd.beans.RoleEmployeeBean;
import com.ibm.ioes.npd.beans.TaskInstanceBean;
import com.ibm.ioes.npd.beans.UploadProductPlanBean;
import com.ibm.ioes.npd.beans.ValidationBean;
import com.ibm.ioes.npd.hibernate.beans.SessionObjectsDto;
import com.ibm.ioes.npd.hibernate.beans.TmEmployee;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjecthierarchy;
import com.ibm.ioes.npd.model.AttachEditProjectPlanModel;
import com.ibm.ioes.npd.scheduler.DeleteTempFiles;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.Messages;
import com.ibm.ioes.npd.utilities.Utility;

public class AttachEditProjectPlanAction extends DispatchAction {
	
	private static final Logger log;
	static {
		log = Logger.getLogger(AttachEditProjectPlanAction.class);
	}
	
	public ActionForward attachedProjectPlanHome(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) {
		String forwardMapping=null;
	
		try
		{
			ProjectPlanInstanceBean formBean=(ProjectPlanInstanceBean)form;
			
//			VALIDATION START
			//taskname,stage id,role holder, assigned to
			
			ArrayList errors=new ArrayList();
			
			Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
					("Search Field-Task Name", formBean.getSearchTaskName(), 20),
					Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS,
										Utility.CASE_MAXLENGTH))
					.appendToAndRetNewErrs(errors);
			
			ArrayList newerrors=Utility.validateValue(new ValidationBean().loadValidationBean_String_SingleSelect
					("Stage Name", formBean.getSelectedStageId(), "-1"),
							Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS))
					.appendToAndRetNewErrs(errors);
			if(newerrors==null && formBean.getSelectedStageId()!=null 
					&& !"".equals(formBean.getSelectedStageId()) && !"-1".equals(formBean.getSelectedStageId()))
			{
				Utility.validateValue(new ValidationBean().loadValidationBean_String_SingleSelect
						("Stage Name", formBean.getSelectedStageId(), "-1"),
						""+Utility.CASE_DIGITS_ONLY).appendToAndRetNewErrs(errors);					
			}

			
			
			Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
					("Search Field-Role Holder", formBean.getSearchTask_roleHolder(), 20),
					Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS,
										Utility.CASE_MAXLENGTH))
					.appendToAndRetNewErrs(errors);
			
			Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
					("Search Field-Assigned To", formBean.getSearchTask_assignedTo(), 20),
					Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS,
										Utility.CASE_MAXLENGTH))
					.appendToAndRetNewErrs(errors);
			
			if(errors!=null && errors.size()>0)//During Server Side Validation
			{
				request.setAttribute("validation_errors", errors);
				
				formBean.setSearchTaskName("");
				formBean.setSelectedStageId("-1");
				formBean.setSearchTask_roleHolder("");
				formBean.setSearchTask_assignedTo("");
				
			}
//			VALIDATION END
			
			//String projectId=null;
			//projectId=(String)request.getParameter("projectId");
			
			AttachEditProjectPlanModel model=new AttachEditProjectPlanModel();
			model.setProjectPlanHomeView(formBean);
			
			
			model.setInstanceTasksView(formBean,null);
			//model.sendEmailForFinalized(formBean,"PLAN_NEW");
			//request.setAttribute("projectBean",projectBean);
			
			forwardMapping="viewAttachedProjectPlanHome";	
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in attachedProjectPlanHome method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			forwardMapping = Messages.getMessageValue("errorGlobalForward");
			return mapping.findForward(forwardMapping);
		}
		
	
		return mapping.findForward(forwardMapping);
	}
	
	public ActionForward openFinalizedAsDraft(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			String forwardMapping=null;
		
			try
			{
				ProjectPlanInstanceBean formBean=(ProjectPlanInstanceBean)form;
				//formBean.setAttachMode("editingFinalized");
				//String projectId=null;
				//projectId=(String)request.getParameter("projectId");
				
				AttachEditProjectPlanModel model=new AttachEditProjectPlanModel();
				model.openFinalizedAsDraft(formBean);
				
				
				//request.setAttribute("projectBean",projectBean);
				
				forwardMapping="forwardTo_viewAttachedProjectPlanHome";	
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				AppConstants.NPDLOGGER.error(ex.getMessage()
						+ " Exception occured in attachedProjectPlanHome method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
				forwardMapping = Messages.getMessageValue("errorGlobalForward");
				return mapping.findForward(forwardMapping);
			}
			
		
			return mapping.findForward(forwardMapping);
		}
	
	public ActionForward viewEditStages(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) {
		String forwardMapping=null;
	
		
		try
		{
			ProjectPlanInstanceBean formBean=(ProjectPlanInstanceBean)form;
			
			//Validation starts
			
			ArrayList errors=new ArrayList();
			
			Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
					("Search Field-Stage Name", formBean.getSearchStageName(), 20),
					Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS,
										Utility.CASE_MAXLENGTH))
					.appendToAndRetNewErrs(errors);



			if(errors!=null && errors.size()>0)//During Server Side Validation
			{
				request.setAttribute("validation_errors", errors);
				//resetPage_CreateUpdateStage(form);
				formBean.setSearchStageName("");
				
			}
			
			
			//Validation ends
			
			
			//String projectId=null;
			//projectId=(String)request.getParameter("projectId");
			
			AttachEditProjectPlanModel model=new AttachEditProjectPlanModel();
			//model.setInstanceWorkflowHeader(formBean);
			model.setInstanceStagesView(formBean);
			model.setProductDetails(formBean);
			
			forwardMapping="viewInstanceStages";	
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in attachedProjectPlanHome method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			forwardMapping = Messages.getMessageValue("errorGlobalForward");
			return mapping.findForward(forwardMapping);
		}
		
	
		return mapping.findForward(forwardMapping);
	}
	
	public ActionForward viewEditTasks(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) {
		String forwardMapping=null;
	
		try
		{
			ProjectPlanInstanceBean formBean=(ProjectPlanInstanceBean)form;
			
			//VALIDATION START
			//taskname,stage id,role holder, assigned to
			
			ArrayList errors=new ArrayList();
			
			Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
					("Search Field-Task Name", formBean.getSearchTaskName(), 20),
					Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS,
										Utility.CASE_MAXLENGTH))
					.appendToAndRetNewErrs(errors);
			
			ArrayList newerrors=Utility.validateValue(new ValidationBean().loadValidationBean_String_SingleSelect
					("Stage Name", formBean.getSelectedStageId(), "-1"),
							Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS))
					.appendToAndRetNewErrs(errors);
			if(newerrors==null && formBean.getSelectedStageId()!=null 
					&& !"".equals(formBean.getSelectedStageId()) && !"-1".equals(formBean.getSelectedStageId()))
			{
				Utility.validateValue(new ValidationBean().loadValidationBean_String_SingleSelect
						("Stage Name", formBean.getSelectedStageId(), "-1"),
						""+Utility.CASE_DIGITS_ONLY).appendToAndRetNewErrs(errors);					
			}

			
			
			Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
					("Search Field-Role Holder", formBean.getSearchTask_roleHolder(), 20),
					Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS,
										Utility.CASE_MAXLENGTH))
					.appendToAndRetNewErrs(errors);
			
			Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
					("Search Field-Assigned To", formBean.getSearchTask_assignedTo(), 20),
					Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS,
										Utility.CASE_MAXLENGTH))
					.appendToAndRetNewErrs(errors);
			
			if(errors!=null && errors.size()>0)//During Server Side Validation
			{
				request.setAttribute("validation_errors", errors);
				
				formBean.setSearchTaskName("");
				formBean.setSelectedStageId("-1");
				formBean.setSearchTask_roleHolder("");
				formBean.setSearchTask_assignedTo("");
				
			}
//			VALIDATION END
			
			String projectId=null;
			projectId=(String)request.getParameter("projectId");
			
			
			
			
			AttachEditProjectPlanModel model=new AttachEditProjectPlanModel();
			model.setInstanceTasksView(formBean,model.TASK_LIST_ASSIGN_USERID_MODE);
			model.setProductDetails(formBean);
			
			//model.sendEmailForFinalized(formBean);
			
			
			forwardMapping="viewInstanceTasks";	
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in setProjectPlanInstanceView method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			forwardMapping = Messages.getMessageValue("errorGlobalForward");
			return mapping.findForward(forwardMapping);
		}
		
	
		return mapping.findForward(forwardMapping);
	}
		
	public ActionForward viewTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			String forwardMapping=null;
		
			try
			{
				ProjectPlanInstanceBean formBean=(ProjectPlanInstanceBean)form;
//				fetch details of selected task(currentTaskId,projectWorkflowId) in bean
				AttachEditProjectPlanModel model=new AttachEditProjectPlanModel();
				model.setProjectPlanTaskInstance(formBean,"view");
				formBean.getTaskInstanceBean().setSecondaryTaskMode(TaskInstanceBean.secondaryMode_popUp);
				forwardMapping="editAttachedTask";	
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				AppConstants.NPDLOGGER.error(ex.getMessage()
						+ " Exception occured in setProjectPlanInstanceView method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
				forwardMapping = Messages.getMessageValue("errorGlobalForward");
				return mapping.findForward(forwardMapping);
			}
			
		
			return mapping.findForward(forwardMapping);
		}

	public ActionForward updateTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			String forwardMapping=null;
		
			try
			{
				ProjectPlanInstanceBean formBean=(ProjectPlanInstanceBean)form;
				
				//fetch details of selected task(currentTaskId,projectWorkflowId) in bean
				AttachEditProjectPlanModel model=new AttachEditProjectPlanModel();
				model.setProjectPlanTaskInstance(formBean,"update");
							
				forwardMapping="editAttachedTask";
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				AppConstants.NPDLOGGER.error(ex.getMessage()
						+ " Exception occured in setProjectPlanInstanceView method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
				forwardMapping = Messages.getMessageValue("errorGlobalForward");
				return mapping.findForward(forwardMapping);
			}
			
		
			return mapping.findForward(forwardMapping);
		}
		
	public ActionForward saveUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			String forwardMapping=null;
		
			try
			{
				ProjectPlanInstanceBean formBean=(ProjectPlanInstanceBean)form;
				
//				Validation start
				ArrayList errors=new ArrayList();
				
				TaskInstanceBean taskBean=formBean.getTaskInstanceBean();
				
				ArrayList newerrors=Utility.validateValue(new ValidationBean().loadValidationBean_String_SingleSelect
						("Stage", taskBean.getCurrentstageid(), "-1"),
						""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MANDATORY_SINGLESELECT_STRING)
						.appendToAndRetNewErrs(errors);
				if(newerrors==null)
				{
					Utility.validateValue(new ValidationBean().loadValidationBean_String_SingleSelect
							("Stage", taskBean.getCurrentstageid(), "-1"),
							""+Utility.CASE_DIGITS_ONLY).appendToAndRetNewErrs(errors);					
				}
				
				
				Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
						("Task", taskBean.getTaskname(), 500),
						Utility.generateCSV(Utility.CASE_MANDATORY,Utility.CASE_SPECIALCHARACTERS,
											Utility.CASE_MAXLENGTH))
						.appendToAndRetNewErrs(errors);
				
				Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
						("Task Description", taskBean.getTaskdesc(), 500),
						Utility.generateCSV(Utility.CASE_MANDATORY,Utility.CASE_SPECIALCHARACTERS,
											Utility.CASE_MAXLENGTH))
						.appendToAndRetNewErrs(errors);
				
				
				
				Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
						("Remarks", taskBean.getTaskTaskinstructionremarks(), 1000),
						Utility.generateCSV(Utility.CASE_MANDATORY,Utility.CASE_SPECIALCHARACTERS,
											Utility.CASE_MAXLENGTH))
						.appendToAndRetNewErrs(errors);
				
				
				if(errors!=null && errors.size()>0)//During Server Side Validation
				{
					
					request.setAttribute("validation_errors", errors);
					errors.add("AND PLEASE CHANGE VALUES AGAIN AS THE CHANGES ARE REVERTED.");
					formBean.setSelectedTaskId(formBean.getTaskInstanceBean().getCurrenttaskid());
					updateTask(mapping, form, request, response);
					
					return mapping.findForward("editAttachedTask");
					
				}
				
								
				
//				Validation end
				
				ActionMessages messages=new ActionMessages();
				//String projectId=null;
				//projectId=(String)request.getParameter("projectId");
				
				
				//fetch details of selected task(currentTaskId,projectWorkflowId) in bean
				AttachEditProjectPlanModel model=new AttachEditProjectPlanModel();
				formBean.setSelectedTaskId(formBean.getTaskInstanceBean().getCurrenttaskid());
				int status=model.UpdateProjectPlanTaskInstance(formBean);
				if(status==1)
				{
					//success
					messages.add("updateMessage",new ActionMessage("workflowInstance.task.update.success"));
				}
				else if(status==-1)
				{
					//failure
					messages.add("updateMessage",new ActionMessage("workflowInstance.task.update.failure"));
				}
				
				model.setProjectPlanTaskInstance(formBean,"view");
				formBean.getTaskInstanceBean().setSecondaryTaskMode(TaskInstanceBean.secondaryMode_mainWindow);
				saveMessages(request, messages);
				//NEW CR start
				//forwardMapping="editAttachedTask";
				viewEditTasks(mapping, form, request, response);
				forwardMapping="viewInstanceTasks";
				//NEW CR End
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				AppConstants.NPDLOGGER.error(ex.getMessage()
						+ " Exception occured in setProjectPlanInstanceView method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
				forwardMapping = Messages.getMessageValue("errorGlobalForward");
				return mapping.findForward(forwardMapping);
			}
			
		
			return mapping.findForward(forwardMapping);
		}
	
	public ActionForward init_updateRoleEmployee(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			String forwardMapping=null;
		
			try
			{
				ProjectPlanInstanceBean formBean=(ProjectPlanInstanceBean)form;
				ActionMessages messages=new ActionMessages();
				AttachEditProjectPlanModel model=new AttachEditProjectPlanModel();
				model.setRoleEmployeeView(formBean);
				
				saveMessages(request, messages);
				forwardMapping="updateRoleEmployee";	
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				AppConstants.NPDLOGGER.error(ex.getMessage()
						+ " Exception occured in init_updateRoleEmployee method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
				forwardMapping = Messages.getMessageValue("errorGlobalForward");
				return mapping.findForward(forwardMapping);
			}
			
		
			return mapping.findForward(forwardMapping);
		}
	
	public ActionForward saveUpdateRoleEmployee(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			String forwardMapping=null;
		
			try
			{
				ProjectPlanInstanceBean formBean=(ProjectPlanInstanceBean)form;
				ActionMessages messages=new ActionMessages();

				//parse
				
				//int count=Integer.parseInt((String)request.getParameter("count"));
				String[] selected=formBean.getSelectedRoleMapping();
				
				ArrayList<RoleEmployeeBean> list=new ArrayList<RoleEmployeeBean>();
				for(int i=0;i<selected.length;i++)
				{
					String name="roleRow["+selected[i]+"].roleId";
					String roleId=(String)request.getParameter(name);
					name="roleRow["+selected[i]+"].employeeId";
					String empId=(String)request.getParameter(name);
					
					RoleEmployeeBean bean=new RoleEmployeeBean();
					bean.setRoleId(roleId);
					bean.setEmployeeId(empId);
					list.add(bean);
				}
				
				//fetch details of selected task(currentTaskId,projectWorkflowId) in bean
				AttachEditProjectPlanModel model=new AttachEditProjectPlanModel();
				int status=model.updateRoleEmployee(formBean,list);
				
				if(status==1)
				{
					//success
					messages.add("updateMessage",new ActionMessage("workflow.employee.mapped.success"));
					request.setAttribute("REFRESHPARENT","REFRESHPARENT");
				}
				else if(status==-1)
				{
					//failure
					messages.add("updateMessage",new ActionMessage("workflow.employee.mapped.failure"));
				}
				
				init_updateRoleEmployee(mapping, form, request, response);
				//model.setInstanceTasksView(formBean,model.TASK_LIST_ASSIGN_USERID_MODE);
				
				saveMessages(request, messages);
				forwardMapping="updateRoleEmployee";	
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				AppConstants.NPDLOGGER.error(ex.getMessage()
						+ " Exception occured in saveUpdateRoleEmployee method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
				forwardMapping = Messages.getMessageValue("errorGlobalForward");
				return mapping.findForward(forwardMapping);
			}
			
		
			return mapping.findForward(forwardMapping);
		}
		
	public ActionForward updateAssignedTo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			String forwardMapping=null;
		
			try
			{
				ProjectPlanInstanceBean formBean=(ProjectPlanInstanceBean)form;
				ActionMessages messages=new ActionMessages();
				//parse
				
				int count=Integer.parseInt((String)request.getParameter("count"));
				ArrayList<TaskInstanceBean> list=new ArrayList<TaskInstanceBean>();
				for(int i=0;i<=count;i++)
				{
					String name="taskRow["+i+"].flag";
					if("1".equals((String)request.getParameter(name)))
					{
						TaskInstanceBean bean=new TaskInstanceBean();
						String currenttaskid=(String)request.getParameter("taskRow["+i+"].currenttaskid");
						//String assignedtouserid=(String)request.getParameter("taskRow["+i+"].assignedtouserid");
						String assignedtouserid=(String)request.getParameter("taskRow["+i+"].newAssigned");
						bean.setCurrenttaskid(currenttaskid);
						bean.setAssignedtouserid(assignedtouserid);
						list.add(bean);	
					}
				}
				
				//fetch details of selected task(currentTaskId,projectWorkflowId) in bean
				AttachEditProjectPlanModel model=new AttachEditProjectPlanModel();
				int status=model.updateTasks(formBean,list);
				
				if(status==1)
				{
					//success
					messages.add("updateMessage",new ActionMessage("workflowInstance.task.update.success"));
				}
				else if(status==-1)
				{
					//failure
					messages.add("updateMessage",new ActionMessage("workflowInstance.task.update.failure"));
				}
				
				model.setInstanceTasksView(formBean,model.TASK_LIST_ASSIGN_USERID_MODE);
				
				saveMessages(request, messages);
				forwardMapping="viewInstanceTasks";	
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				AppConstants.NPDLOGGER.error(ex.getMessage()
						+ " Exception occured in updateAssignedTo method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
				forwardMapping = Messages.getMessageValue("errorGlobalForward");
				return mapping.findForward(forwardMapping);
			}
			
		
			return mapping.findForward(forwardMapping);
		}
		
	//to open new cteate task screen
	public ActionForward addNewTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			String forwardMapping=null;
		
			try
			{
				ProjectPlanInstanceBean formBean=(ProjectPlanInstanceBean)form;
				
				TaskInstanceBean taskInstanceBean=formBean.getTaskInstanceBean();
				AttachEditProjectPlanModel model=new AttachEditProjectPlanModel();
				model.setCreateNewTaskInstanceView(formBean);
				taskInstanceBean.setTaskMode("createNew");
				
				forwardMapping="editAttachedTask";	
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				AppConstants.NPDLOGGER.error(ex.getMessage()
						+ " Exception occured in setProjectPlanInstanceView method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
				forwardMapping = Messages.getMessageValue("errorGlobalForward");
				return mapping.findForward(forwardMapping);
			}
			
		
			return mapping.findForward(forwardMapping);
		}
	
	public ActionForward saveNewTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			String forwardMapping=null;
			ActionMessages messages=new ActionMessages();
			
			try
			{
				ProjectPlanInstanceBean formBean=(ProjectPlanInstanceBean)form;
				
				//Validation start
				ArrayList errors=new ArrayList();
				
				TaskInstanceBean taskBean=formBean.getTaskInstanceBean();
				
				ArrayList newerrors=Utility.validateValue(new ValidationBean().loadValidationBean_String_SingleSelect
						("Stage", taskBean.getCurrentstageid(), "-1"),
						""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MANDATORY_SINGLESELECT_STRING)
						.appendToAndRetNewErrs(errors);
				if(newerrors==null)
				{
					Utility.validateValue(new ValidationBean().loadValidationBean_String_SingleSelect
							("Stage", taskBean.getCurrentstageid(), "-1"),
							""+Utility.CASE_DIGITS_ONLY).appendToAndRetNewErrs(errors);					
				}
				
				
				Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
						("Task", taskBean.getTaskname(), 500),
						Utility.generateCSV(Utility.CASE_MANDATORY,Utility.CASE_SPECIALCHARACTERS,
											Utility.CASE_MAXLENGTH))
						.appendToAndRetNewErrs(errors);
				
				Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
						("Task Description", taskBean.getTaskdesc(), 500),
						Utility.generateCSV(Utility.CASE_MANDATORY,Utility.CASE_SPECIALCHARACTERS,
											Utility.CASE_MAXLENGTH))
						.appendToAndRetNewErrs(errors);
				
				newerrors=Utility.validateValue(new ValidationBean().loadValidationBean_String_SingleSelect
						("Stake Holder", taskBean.getTaskstakeholder(), "-1"),
						""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MANDATORY_SINGLESELECT_STRING)
						.appendToAndRetNewErrs(errors);
				if(newerrors==null)
				{
					Utility.validateValue(new ValidationBean().loadValidationBean_String_SingleSelect
							("Stake Holder", taskBean.getTaskstakeholder(), "-1"),
							""+Utility.CASE_DIGITS_ONLY).appendToAndRetNewErrs(errors);					
				}
				
				
				Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
						("Remarks", taskBean.getTaskTaskinstructionremarks(), 1000),
						Utility.generateCSV(Utility.CASE_MANDATORY,Utility.CASE_SPECIALCHARACTERS,
											Utility.CASE_MAXLENGTH))
						.appendToAndRetNewErrs(errors);
				
				
				if(errors!=null && errors.size()>0)//During Server Side Validation
				{
					saveMessages(request, messages);
					request.setAttribute("validation_errors", errors);
					addNewTask(mapping, form, request, response);
					return mapping.findForward("editAttachedTask");
				}
				
								
				
//				Validation end
				
				//fetch details of selected task(currentTaskId,projectWorkflowId) in bean
				AttachEditProjectPlanModel model=new AttachEditProjectPlanModel();
				int status=model.saveNewProjectPlanTaskInstance(formBean);
				formBean.setSelectedTaskId(formBean.getTaskInstanceBean().getCurrenttaskid());
				model.setProjectPlanTaskInstance(formBean, "view");
				formBean.getTaskInstanceBean().setSecondaryTaskMode(TaskInstanceBean.secondaryMode_mainWindow);
				if(status==1)
				{
					//success
					messages.add("createMessage",new ActionMessage("workflowInstance.task.create.success"));
				}
				else if(status==-1)
				{
					//failure
					messages.add("createMessage",new ActionMessage("workflowInstance.task.create.failure"));
				}
				
				//ROHIT VERMA NEW CR START
				//forwardMapping="editAttachedTask";	
				viewEditTasks(mapping, form, request, response);
				forwardMapping="viewInstanceTasks";
				//ROHIT VERMA NEW CR END
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				AppConstants.NPDLOGGER.error(ex.getMessage()
						+ " Exception occured in setProjectPlanInstanceView method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
				forwardMapping = Messages.getMessageValue("errorGlobalForward");
				return mapping.findForward(forwardMapping);
			}
			
			saveMessages(request, messages);
			return mapping.findForward(forwardMapping);
		}	
	
	//Changes Made for New CR ROHIT VERMA
	public ActionForward removeTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
			String forwardMapping=null;
			try
			{
				ProjectPlanInstanceBean formBean=(ProjectPlanInstanceBean)form;
				ActionMessages messages=new ActionMessages();
				AttachEditProjectPlanModel model=new AttachEditProjectPlanModel();
				model.setTasksViewForDeleteUpdate(formBean,null);	
				model.setProductDetails(formBean);
				forwardMapping="removeTask";	
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				AppConstants.NPDLOGGER.error(ex.getMessage()
						+ " Exception occured in setProjectPlanInstanceView method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
				forwardMapping = Messages.getMessageValue("errorGlobalForward");
				return mapping.findForward(forwardMapping);
			}
			return mapping.findForward(forwardMapping);
		}
	
	public ActionForward finalize(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			String forwardMapping=null;
			ActionMessages messages=new ActionMessages();
			try
			{
				ProjectPlanInstanceBean formBean=(ProjectPlanInstanceBean)form;
				AttachEditProjectPlanModel model=new AttachEditProjectPlanModel();
				
				
				//VALIDATE: START
				model.setCheckListView(formBean);
				ArrayList errors=new ArrayList();
				
				if("".equals(formBean.getTotalTasks()))
				{
					errors.add("Plan Cannot Be Finalized As No Tasks Present");
				}
				if(errors.size()==0)
				{
					if(formBean.getFirstTask()==null)
					{
						errors.add("-No First Task Defined");
					}
					if(formBean.getLastTask()==null)
					{
						errors.add("-No First Task Defined");
					}
					if(formBean.getNoPrevTaskList().size()>0)
					{
						errors.add("-Previous Task of Some Tasks Not Defined");
					}
					if(errors.size()>0)
					{
						ArrayList str=new ArrayList();
						str.add("Plan Cannot Be Finalized Because :");
						str.addAll(errors);
						errors=str;
					}
				}
				
				if(errors!=null && errors.size()>0)//During Server Side Validation
				{
					saveMessages(request, messages);
					request.setAttribute("validation_errors", errors);
					return mapping.findForward("forwardTo_viewAttachedProjectPlanHome");
				}
				
				
				
				
				
				
				
				//VALIDATE: END				
				
				
				
				//update ProjectHierarchy with whatever finalize actions
				
				
				String attachStatus=formBean.getAttachMode();
				
				if(AppConstants.ATTACH_NEW.equals(attachStatus))
				{
					int status=model.finalizeProjectPlanInstance(formBean);
					if(status==1)
					{
						AppConstants.NPDLOGGER.debug("product's plan finalized ,productId: "+formBean.getProjectId());
						messages.add("finalized",new ActionMessage("productPlanAttached.finalized.success",formBean.getProjectId()));
						
						//send email
						//model.sendEmailForFinalized(formBean,"PLAN_NEW");
						request.setAttribute("finalized", "finalized");
						forwardMapping="projectPlanAttachMessage";	
					}
					else if(status==-1)
					{
						AppConstants.NPDLOGGER.error("product's plan finalized error,productId: "+formBean.getProjectId());
						messages.add("finalized",new ActionMessage("productPlanAttached.finalized.failure",formBean.getProjectId()));
						request.setAttribute("finalized", "finalized");
						forwardMapping="projectPlanAttachMessage";	
					}
				}
				else if(AppConstants.EDITING_FINALIZED.equals(attachStatus))
				{
					HashMap map=model.revertBeforeFinalize(formBean);
					int isReverted=(Integer)map.get("ISREVERTED");
					if(isReverted==1)
					{
						//show revert message to user;
						ArrayList<TtrnProjecthierarchy> list=(ArrayList<TtrnProjecthierarchy>)map.get("REVERTED_TASKS_LIST"); 
						
						ArrayList<TtrnProjecthierarchy> closedList=(ArrayList<TtrnProjecthierarchy>)map.get("REVERTED_CLOSED_TASKS_LIST");
						ArrayList<TtrnProjecthierarchy> openActionTakenList=(ArrayList<TtrnProjecthierarchy>)map.get("REVERTED_OPENACTIONTAKEN_TASKS_LIST");
						ArrayList<TtrnProjecthierarchy> openNoActionList=(ArrayList<TtrnProjecthierarchy>)map.get("REVERTED_OPENNOACTION_TASKS_LIST");
						
						request.setAttribute("finalizingAgainMessage", "finalizingAgainMessage");
						request.setAttribute("allRevertedTasks", list);
						
						request.setAttribute("closedList", closedList);
						request.setAttribute("openActionTakenList", openActionTakenList);
						request.setAttribute("openNoActionList", openNoActionList);
						forwardMapping="forwardTo_viewAttachedProjectPlanHome";	
					}
					else if(isReverted==0) 
					{
						int status=model.finalizeAgainProjectPlanInstance(formBean);
						if(status==1)
						{
							AppConstants.NPDLOGGER.debug("product's plan finalized ,productId: "+formBean.getProjectId());
							messages.add("finalized",new ActionMessage("productPlanAttached.finalized.success",formBean.getProjectId()));
							
							//send email
							model.sendEmailForFinalized(formBean,"PLAN_ALTERED");
							request.setAttribute("finalized", "finalized");
							forwardMapping="projectPlanAttachMessage";	
						}
						else if(status==-1)
						{
							AppConstants.NPDLOGGER.error("product's plan finalized error,productId: "+formBean.getProjectId());
							messages.add("finalized",new ActionMessage("productPlanAttached.finalized.failure",formBean.getProjectId()));
							request.setAttribute("finalized", "finalized");
							forwardMapping="projectPlanAttachMessage";	
						}
					}
					
				}
				/*else if(status==2)
				{
					String [] msgs=formBean.getMessages();
					request.setAttribute("againFinalizationFailure", msgs);
					forwardMapping="forwardTo_viewAttachedProjectPlanHome";	
				}*/
				
				
				
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				AppConstants.NPDLOGGER.error(ex.getMessage()
						+ " Exception occured in finalize method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
				forwardMapping = Messages.getMessageValue("errorGlobalForward");
				return mapping.findForward(forwardMapping);
			}
			
			saveMessages(request, messages);
			return mapping.findForward(forwardMapping);
		}	
	
	public ActionForward draft(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			String forwardMapping=null;
			ActionMessages messages=new ActionMessages();
			
			try
			{
				ProjectPlanInstanceBean formBean=(ProjectPlanInstanceBean)form;
				request.setAttribute("draft", "draft");
				messages.add("draft",new ActionMessage("productPlanAttached.drafted",formBean.getProjectId()));
				forwardMapping="projectPlanAttachMessage";	
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				AppConstants.NPDLOGGER.error(ex.getMessage()
						+ " Exception occured in draft method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
				forwardMapping = Messages.getMessageValue("errorGlobalForward");
				return mapping.findForward(forwardMapping);
			}
			
			saveMessages(request, messages);
			return mapping.findForward(forwardMapping);
		}		
			
	public ActionForward viewProductPlanVersions(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			String forwardMapping=null;
		
			try
			{
				ProjectPlanInstanceBean formBean=(ProjectPlanInstanceBean)form;
				AttachEditProjectPlanModel model=new AttachEditProjectPlanModel();
				model.setProjectPlanVersionsView(formBean);
				
				request.setAttribute("draft", "draft");
				forwardMapping="viewVersions";	
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				AppConstants.NPDLOGGER.error(ex.getMessage()
						+ " Exception occured in viewProductPlanVersion method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
				forwardMapping = Messages.getMessageValue("errorGlobalForward");
				return mapping.findForward(forwardMapping);
			}
			
		
			return mapping.findForward(forwardMapping);
		}

	public ActionForward discardDraft(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			String forwardMapping=null;
		
			try
			{
				ActionMessages messages=new ActionMessages();
				ProjectPlanInstanceBean formBean=(ProjectPlanInstanceBean)form;
				AttachEditProjectPlanModel model=new AttachEditProjectPlanModel();
				int status=model.setDiscardDraft(formBean);
				if(status==1)
				{
				messages.add("discardDraft", new ActionMessage("productPlanAttached.discardDraft.success",formBean.getProjectId()));
				}
				else if(status==-1)
				{
					messages.add("discardDraft", new ActionMessage("productPlanAttached.discardDraft.failure",formBean.getProjectId()));	
				}
				
				
				saveMessages(request,messages);
				request.setAttribute("discardDraft","discardDraft");
				forwardMapping="projectPlanAttachMessage";	
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				AppConstants.NPDLOGGER.error(ex.getMessage()
						+ " Exception occured in draft method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
				forwardMapping = Messages.getMessageValue("errorGlobalForward");
				return mapping.findForward(forwardMapping);
			}
			
		
			return mapping.findForward(forwardMapping);
		}	
		/*public ActionForward attachProjectPlan(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) {
	
	String projectId=request.getParameter("projectId");
	
	
	return null;
	}
	
	//"ATTACH_NEW" ,"ALTER"	
	
	
	
	
	public ActionForward initAttachProjectPlan(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) {
	ProductCreationBean productCreationBean = (ProductCreationBean) form;
	ProductCreationModel creationModel = new ProductCreationModel();
	try {
		
	String projectId="1";
	String projectWorkflowId="";//fetch on basis of projectId
	String currentStatus="";//values : "DRAFT","FINAL","NOTSET"
	
	String taskToDo="";// "ATTACH_NEW" ,""
	
	if("NOTSET".equals(currentStatus))
	{
		//if not set ,import and set to draft
		
		
		
		
		//change db to "DRAFT"
		//currentStatus="DRAFT";
	}
	
	if("DRAFT".equals(currentStatus))
	{
		//prepare data & give to presenation
		
	}
	
	
		if (request.getParameter("projectId") != null
				&& !request.getParameter("projectId").equalsIgnoreCase(
						AppConstants.INI_VALUE))
			productCreationBean = creationModel.initUpdateProject(new Long(
					request.getParameter("projectId")).longValue());
		request.setAttribute("productCreationBean", productCreationBean);
		
	} catch (Exception e) {
	
		e.printStackTrace();
	}
	
	return mapping.findForward("viewProductProjectPlan");
	}	
	
	//adding new tasks
	public ActionForward modifyProjectPlan(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) {
	String forwardMapping=null;
	
	try
	{
		
		ArrayList<TtrnProject> projectListIdWise=null;
		ArrayList<TtrnProject> projectListNameWise=new ArrayList<TtrnProject>();
		
		
		UtilityModel commonModel=new UtilityModel(); 
		//Fetch all lists
		
		TtrnProject searchProjectDto=new TtrnProject();
		PagingSorting tempSorting=new PagingSorting(false,true);
		tempSorting.setSortByColumn("projectId");
		tempSorting.setSortByOrder(tempSorting.increment);
		searchProjectDto.setPagingSorting(tempSorting);
		
		projectListIdWise=commonModel.fetchProjects(searchProjectDto);
		
		
	//	TreeSet<TtrnProject> temp=new TreeSet<TtrnProject>(new ProjectNameComparator());
	//	temp.addAll(projectListIdWise);
	//	projectListNameWise.addAll(temp);
		
		request.setAttribute("projectListIdWise", projectListIdWise);
		request.setAttribute("projectListNameWise", projectListNameWise);
		forwardMapping="viewPage";	
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
		log.error(ex.getMessage()
				+ " Exception occured in initSearch method of "
				+ this.getClass().getSimpleName());
		forwardMapping = Messages.getMessageValue("errorGlobalForward");
		return mapping.findForward(forwardMapping);
	}
	
	
	return mapping.findForward(forwardMapping);
	}
	
	
	public ActionForward draftFinalizeProjectPlan(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) {
	String forwardMapping=null;
	
	try
	{
		
		ArrayList<TtrnProject> projectListIdWise=null;
		ArrayList<TtrnProject> projectListNameWise=new ArrayList<TtrnProject>();
		
		
		UtilityModel commonModel=new UtilityModel(); 
		//Fetch all lists
		
		TtrnProject searchProjectDto=new TtrnProject();
		PagingSorting tempSorting=new PagingSorting(false,true);
		tempSorting.setSortByColumn("projectId");
		tempSorting.setSortByOrder(tempSorting.increment);
		searchProjectDto.setPagingSorting(tempSorting);
		
		projectListIdWise=commonModel.fetchProjects(searchProjectDto);
		
		
	//	TreeSet<TtrnProject> temp=new TreeSet<TtrnProject>(new ProjectNameComparator());
		//temp.addAll(projectListIdWise);
		//projectListNameWise.addAll(temp);
		
		request.setAttribute("projectListIdWise", projectListIdWise);
		request.setAttribute("projectListNameWise", projectListNameWise);
		forwardMapping="viewPage";	
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
		log.error(ex.getMessage()
				+ " Exception occured in initSearch method of "
				+ this.getClass().getSimpleName());
		forwardMapping = Messages.getMessageValue("errorGlobalForward");
		return mapping.findForward(forwardMapping);
	}
	
	
	return mapping.findForward(forwardMapping);
	}*/
	
	//	For Multiple Task Deletion-Rohit Verma NEW CR
	//rohit verma new cr
	public ActionForward removeSelectedTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
			String forwardMapping=null;
			try
			{
				ArrayList<String> deleteTaskIdList = new ArrayList<String>();
				ProjectPlanInstanceBean formBean=(ProjectPlanInstanceBean)form;
				//String deleteTaskIdStr = formBean.getDeleteTaskId();
				//String noDeleteTaskIdStr = formBean.getNoDeleteTaskId();
				//String[] DeleteTaskIdArr = deleteTaskIdStr.split("&");
				//String[] NodeleteTaskIdArr = noDeleteTaskIdStr.split("&");
				ActionMessages messages=new ActionMessages();
				AttachEditProjectPlanModel model=new AttachEditProjectPlanModel();
				/*for(int i=1;i<DeleteTaskIdArr.length;i++)
				{
					deleteTaskIdList.add(DeleteTaskIdArr[i]);
				}
				for(int i=1;i<NodeleteTaskIdArr.length;i++)
				{
					if(deleteTaskIdList.contains(NodeleteTaskIdArr[i]))
					{
						deleteTaskIdList.remove(NodeleteTaskIdArr[i]);
					}	
					else
					{
						deleteTaskIdList.add(NodeleteTaskIdArr[i]);
					}	
				}*/
				
				String[] removeSelectedTaskIds = formBean.getRemoveSelectedTaskId();
				if(removeSelectedTaskIds==null)
				{
					removeSelectedTaskIds=new String[0];
				}
				deleteTaskIdList=new ArrayList<String>(Arrays.asList(removeSelectedTaskIds));
				
				if(deleteTaskIdList.size()==0&&deleteTaskIdList.size()==0)
				{	
					log.info("No Task Selected For Deletion");
					messages.add("",new ActionMessage("customMessage","No Task Selected For Deletion"));
					if (!messages.isEmpty()) {
						this.saveMessages(request, messages);
					}
				}
				else
				{
					int status=model.deleteTasks(deleteTaskIdList, formBean);
					if(status==0)
					{
						log.info("Error while Deleting Task");
						messages.add(AppConstants.RECORD_SAVE_FAILURE, new ActionMessage(
								AppConstants.RECORD_SAVE_FAILURE));
					}
					else
					{
						log.info("Task Deletion SucessFully Done");
						messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
								AppConstants.RECORD_DELETE_SUCCESS));
					}
					if (!messages.isEmpty()) {
						this.saveMessages(request, messages);
					}
				}	

				model.setTasksViewForDeleteUpdate(formBean,null);	
				model.setProductDetails(formBean);
				forwardMapping="removeTask";
				
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				AppConstants.NPDLOGGER.error(ex.getMessage()
						+ " Exception occured in setProjectPlanInstanceView method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
				forwardMapping = Messages.getMessageValue("errorGlobalForward");
				return mapping.findForward(forwardMapping);
			}
			return mapping.findForward(forwardMapping);
		}
	
	public ActionForward prevTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		String forwardMapping=null;
		try
		{
			ProjectPlanInstanceBean formBean=(ProjectPlanInstanceBean)form;
			ActionMessages messages=new ActionMessages();
			AttachEditProjectPlanModel model=new AttachEditProjectPlanModel();
			model.setTasksViewForDeleteUpdate(formBean,null);	
			model.setProductDetails(formBean);
			forwardMapping="prevTask";	
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in setProjectPlanInstanceView method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			forwardMapping = Messages.getMessageValue("errorGlobalForward");
			return mapping.findForward(forwardMapping);
		}
		return mapping.findForward(forwardMapping);
	}

	public ActionForward updatePrevTask(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
	{
		String forwardMapping=null;
		try
		{
			ProjectPlanInstanceBean formBean=(ProjectPlanInstanceBean)form;
			String[] indexes=formBean.getPrevPageSelectedTaskId();
			AttachEditProjectPlanModel model=new AttachEditProjectPlanModel();
			ArrayList<TtrnProjecthierarchy> inputList=new ArrayList<TtrnProjecthierarchy>();
			TtrnProjecthierarchy dto=null;
			for (String index : indexes) 
			{
				dto=new TtrnProjecthierarchy();
				String currentTaskId=request.getParameter("updatePrevTaskRow["+index+"].currenttaskid");
				//String name_prevTaskId="updatePrevTaskRow["+index+"].prevTaskIds";
				String []prevTaskIds=formBean.getSelect_NewPreviousIds();
				if(prevTaskIds==null)
				{
					prevTaskIds=new String[0];
				}
				dto.setCurrenttaskid(Long.parseLong(currentTaskId));
				dto.setSelectedPrevTaskIds(prevTaskIds);
				dto.setProjectworkflowid(Long.parseLong(formBean.getProjectWorkflowId()));
				inputList.add(dto);
			}
			model.updatePrevTask(inputList);
			prevTask(mapping, form, request, response);
			forwardMapping="prevTask";	
		}
		
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in setProjectPlanInstanceView method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			forwardMapping = Messages.getMessageValue("errorGlobalForward");
			return mapping.findForward(forwardMapping);
		}
		return mapping.findForward(forwardMapping);
	}
	public ActionForward downloadPlanExcelForEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			String forwardMapping=null;
		
			try
			{
				ActionMessages messages=new ActionMessages();
				ProjectPlanInstanceBean formBean=(ProjectPlanInstanceBean)form;
				AttachEditProjectPlanModel model=new AttachEditProjectPlanModel();
				
				//make excel
				model.getMasterExcel(formBean);
				//send excel in request
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition", "attachment; filename=ProjectPlanMaster_"+formBean.getProjectId()+".xls");
				ServletOutputStream out = response.getOutputStream();
				formBean.getEditExcelWorkbook().write(out);
				out.flush();
				out.close();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				AppConstants.NPDLOGGER.error(ex.getMessage()
						+ " Exception occured in downloadPlanExcelForEdit method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
				forwardMapping = Messages.getMessageValue("errorGlobalForward");
				return mapping.findForward(forwardMapping);
			}
			
		
			return null;
		}	
	
	//
	public ActionForward downloadTemplateExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			String forwardMapping=null;
		
			try
			{
				ActionMessages messages=new ActionMessages();
				ProjectPlanInstanceBean formBean=(ProjectPlanInstanceBean)form;
				AttachEditProjectPlanModel model=new AttachEditProjectPlanModel();
				
				//make excel
				model.getTemplateExcel(formBean);
				//model.getPlanExcelForEdit(formBean);
				//send excel in request
				//Error-Rohit Start
				response.setContentType("application/vnd.ms-excel");
				//Error-Rohit End
				response.setHeader("Content-Disposition", "attachment; filename=ProjectPlanTemplate_"+formBean.getProjectId()+".xls");
				ServletOutputStream out = response.getOutputStream();
				formBean.getTemplateWorkbook().write(out);
				out.flush();
				out.close();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				AppConstants.NPDLOGGER.error(ex.getMessage()
						+ " Exception occured in downloadPlanExcelForEdit method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
				forwardMapping = Messages.getMessageValue("errorGlobalForward");
				return mapping.findForward(forwardMapping);
			}
			
		
			return null;
		}	
	
	public ActionForward uploadPlanExcelInitPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			String forwardMapping=null;
			
			try
			{
				
				ProjectPlanInstanceBean formBean=(ProjectPlanInstanceBean)form;
				AttachEditProjectPlanModel model=new AttachEditProjectPlanModel();
				
				//load initial state ->progress,file name , incase of vali error link to file download
				model.loadUploadExcelProgressDetails(formBean);
				model.setProductAndCheckListView(formBean);
				
				forwardMapping="uploadPlanExcel";
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				AppConstants.NPDLOGGER.error(ex.getMessage()
						+ " Exception occured in uploadPlanExcel method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
				forwardMapping = Messages.getMessageValue("errorGlobalForward");
				return mapping.findForward(forwardMapping);
			}
			
			//do not put messges ie saveMessgaes as this func is called from other actions and will overwrite their messages
			return mapping.findForward(forwardMapping);
		}	
	
	public ActionForward uploadPlanExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			String forwardMapping=null;
			ActionMessages messages=new ActionMessages();
			try
			{
				
				ProjectPlanInstanceBean formBean=(ProjectPlanInstanceBean)form;
				AttachEditProjectPlanModel model=new AttachEditProjectPlanModel();
				Long npduserid=((TmEmployee) request.getSession().getAttribute(
						AppConstants.LOGINBEAN)).getNpdempid();
				
				//old comments
				//get input action type
				// it can be : upload ,validate , replace
				//if upload , then chk whther for same project no file id is present in current=1
				//if yes delete the data table , add new entries
				//return info bean with , file name , 
				
				//if validate , validate data and return info
				
				//if replace then replace and return info
				
				UploadProductPlanBean uploadProductPlanBean=formBean.getUploadProductPlanBean();
				
				int progress=-1;
				String actionName=uploadProductPlanBean.getActionName();
				if(UploadProductPlanBean.ACTION_NEW_LOAD.equals(actionName))
				{
					
					//start : validation fro file existence of type xls
					boolean validation_error = false;
					FormFile attachment = uploadProductPlanBean.getUploadFile();
					
					if(attachment==null || "".equals(attachment.getFileName()))
					{
						messages.add("",new ActionMessage("uploadPlan.error.attachment.notSelected"));
						validation_error = true;
					}
					else if(!(attachment.getFileName().substring(attachment.getFileName().lastIndexOf(".")+1)).equalsIgnoreCase("xls"))
					{
						messages.add("",new ActionMessage("uploadPlan.error.attachment.notXls"));
						validation_error = true;
					}else if(attachment.getFileSize()>SessionObjectsDto.getInstance().getAttachmentSize())
					{
						messages.add("attachment",new ActionMessage("error.attachment.size"));
						validation_error = true;
					}
					
					if(validation_error)
					{
						saveMessages(request, messages);
						uploadPlanExcelInitPage(mapping, form, request, response);
						forwardMapping="uploadPlanExcel";
						return mapping.findForward(forwardMapping);
					}
					
//					end : validation fro file existence of type xls
					
					//also remove from db any prev uploaded temp file 
					
					formBean.getUploadProductPlanBean().setCreatedBy(npduserid);
					model.loadExcelProductPlan(formBean);
					int status=formBean.getUploadProductPlanBean().getLoadExcelProductPlan_status();
					if(status>0)//success
					{
						ActionMessages newLoadMessgaes=formBean.getUploadProductPlanBean().getMessages();
						messages.add("upload.success",new ActionMessage("upload.success"));
						//messages.add(newLoadMessgaes);
						progress=UploadProductPlanBean.PROGRESS_UPLOAD_SUCCESS;
						formBean.getUploadProductPlanBean().setUploadFile(null);
					}
					else//failure
					{
						ActionMessages newLoadMessgaes=formBean.getUploadProductPlanBean().getMessages();
						messages.add("upload.failure.because",new ActionMessage("upload.failure.because"));
						messages.add(newLoadMessgaes);
						progress=UploadProductPlanBean.PROGRESS_UPLOAD_FAILURE;
					}
				}
				else if(UploadProductPlanBean.ACTION_VALIDATE.equals(actionName))
				{
					model.validateExcelProductPlan(formBean);
					int status=formBean.getUploadProductPlanBean().getValidateExcelProductPlan_status();
					if(status>0)//success
					{
						messages.add("upload.validation.success",new ActionMessage("upload.validation.success"));
						progress=UploadProductPlanBean.PROGRESS_VALIDATE_SUCCESS;
					}
					else//failure
					{
						messages.add("upload.validation.failure",new ActionMessage("upload.validation.failure"));
						progress=UploadProductPlanBean.PROGRESS_VALIDATE_FAILURE;
					}
				}
				else if(UploadProductPlanBean.ACTION_REPLACE.equals(actionName))
				{
					model.replaceExcelProductPlan(formBean);
					int status=formBean.getUploadProductPlanBean().getReplaceExcelProductPlan_status();
					if(status>0)//success
					{
						messages.add("upload.replace.success",new ActionMessage("upload.replace.success"));
						//progress=UploadProductPlanBean.PROGRESS_REPLACE_SUCCESS;
						/*request.setAttribute("PLAN_UPDATED", "PLAN_UPDATED");
						String projectWorkflowId=formBean.getProjectWorkflowId();
						request.setAttribute("NEW_PROJECT_WORKFLOWID", projectWorkflowId);*/
					}
					else//failure
					{
						messages.add("upload.replace.failure",new ActionMessage("upload.replace.failure"));
						progress=UploadProductPlanBean.PROGRESS_REPLACE_FAILURE;
					}
				}
				else if(UploadProductPlanBean.ACTION_FINALIZE.equals(actionName))
				{
					int status=model.finalizeProjectPlanInstance(formBean);
					if(status==1)
					{
						AppConstants.NPDLOGGER.debug("product's plan finalized ,productId: "+formBean.getProjectId());
						messages.add("finalized",new ActionMessage("productPlanAttached.finalized.success",formBean.getProjectId()));
						
						//send email
						model.sendEmailForFinalized(formBean,"PLAN_NEW");
						
						request.setAttribute("finalized", "finalized");
						forwardMapping="projectPlanAttachMessage";	
						
						saveMessages(request, messages);
						return mapping.findForward(forwardMapping);
					}
					else if(status==-1)
					{
						AppConstants.NPDLOGGER.error("product's plan finalized error,productId: "+formBean.getProjectId());
						messages.add("finalized",new ActionMessage("productPlanAttached.finalized.failure",formBean.getProjectId()));
						request.setAttribute("finalized", "finalized");
						//forwardMapping="projectPlanAttachMessage";	
					}
					
				}
				/*else if(UploadProductPlanBean.ACTION_INITPAGE.equals(actionName))
				{
					//load progress details
				}*/
				
				//load stat
				//formBean.getUploadProductPlanBean().setProgress(progress);
				uploadPlanExcelInitPage(mapping, form, request, response);
				
				
				forwardMapping="uploadPlanExcel";
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				AppConstants.NPDLOGGER.error(ex.getMessage()
						+ " Exception occured in uploadPlanExcel method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
				forwardMapping = Messages.getMessageValue("errorGlobalForward");
				return mapping.findForward(forwardMapping);
			}
			
			saveMessages(request, messages);
			return mapping.findForward(forwardMapping);
		}	
	
		
	
	public ActionForward downloadErrorPlan(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			String forwardMapping=null;
		
			try
			{
				ActionMessages messages=new ActionMessages();
				ProjectPlanInstanceBean formBean=(ProjectPlanInstanceBean)form;
				AttachEditProjectPlanModel model=new AttachEditProjectPlanModel();
				
				//make excel
				model.getErrorLogExcel(formBean);
				//model.getPlanExcelForEdit(formBean);
				//send excel in request
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition", "attachment; filename=ProjectPlanErrorLog_"+formBean.getProjectId()+".xls");
				ServletOutputStream out = response.getOutputStream();
				formBean.getErrorLogWorkbook().write(out);
				out.flush();
				out.close();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				AppConstants.NPDLOGGER.error(ex.getMessage()
						+ " Exception occured in downloadPlanExcelForEdit method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
				forwardMapping = Messages.getMessageValue("errorGlobalForward");
				return mapping.findForward(forwardMapping);
			}
			
		
			return null;
		}	
}
