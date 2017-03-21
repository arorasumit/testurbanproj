/**
 * 
 */
package com.ibm.ioes.npd.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;

import com.ibm.ioes.npd.beans.MasterProjectPlan;
import com.ibm.ioes.npd.beans.MeetingBean;
import com.ibm.ioes.npd.beans.UserNpdSpocs;
import com.ibm.ioes.npd.beans.ValidationBean;
import com.ibm.ioes.npd.hibernate.beans.TmEmployee;
import com.ibm.ioes.npd.hibernate.beans.TmRoles;
import com.ibm.ioes.npd.hibernate.beans.TmWorkflow;
import com.ibm.ioes.npd.hibernate.beans.TmWorkflowstage;
import com.ibm.ioes.npd.hibernate.beans.TmWorkflowtasks;
import com.ibm.ioes.npd.model.MasterProjectPlanModel;
import com.ibm.ioes.npd.model.NpdUserModel;
import com.ibm.ioes.npd.utilities.AjaxHelper;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.HibernateBeansConstants;
import com.ibm.ioes.npd.utilities.Messages;
import com.ibm.ioes.npd.utilities.Utility;

/**
 * @author Sanjay
 * 
 */
public class ManageProjectPlanAction extends LookupDispatchAction {
	private static final Logger log;
	static {
		log = Logger.getLogger(ManageNpdSpocsAction.class);
	}

	public ManageProjectPlanAction() {

	}

	protected Map getKeyMethodMap() {
		Map map = new HashMap();

		map.put("link.viewMasterPlan", "viewMasterPlan");
		map.put("submit.saveStage", "saveUpdateStage");
		map.put("submit.updateStage", "saveUpdateStage");
		map.put("submit.saveTask", "saveUpdateTask");
		map.put("submit.updateTask", "saveUpdateTask");
		map.put("submit.saveWorkflow", "saveWorkflow");
		map.put("link.viewMasterProjectPlanHistory",
				"viewMasterProjectPlanHistory");
		map.put("submit.viewHistory", "viewHistory");
		map.put("viewHistory", "viewHistory");
		
		map.put("deleteTask", "deleteTask");
		map.put("viewWorkFlowList", "viewWorkFlowList");
		map.put("viewChangeOrderWorkflow", "viewChangeOrderWorkflow");
		
		

		return map;
	}

	// To initialize the page of Creating Master Plan
	public ActionForward viewMasterPlan(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MasterProjectPlan masterProjectPlan = (MasterProjectPlan) form;
		MasterProjectPlanModel masterProjectPlanModel = new MasterProjectPlanModel();
		ActionMessages messages = new ActionMessages();
		ActionForward actionForward = new ActionForward();
		TmEmployee tmEmployee = (TmEmployee) request.getSession().getAttribute(
				AppConstants.LOGINBEAN);
		try {
			masterProjectPlan = masterProjectPlanModel
			.viewMasterProjectPlan(masterProjectPlan);
			
			if (request.getParameter("finalize") != null
					&& !request.getParameter("finalize").equalsIgnoreCase(
							AppConstants.INI_VALUE)) {
				
				//start:validation for finalize
				boolean errorsFound=false;
				String taskListLength=masterProjectPlan.getTaskListLength();
				ArrayList errors=new ArrayList();
				if("0".equals(taskListLength))
				{
					
					errors.add("Cannot Finalize - At Least One task should be present");
					errorsFound=true;
				}
				//Server Side Security Start for Stage
				if(!errorsFound)
				{
					if(masterProjectPlan.getFirstTask()==null)
					{
						errors.add("No First Task Present");
					}
					if(masterProjectPlan.getLastTask()==null)
					{
						errors.add("No Last Task Present");
					}
					ArrayList<TmWorkflowtasks> noPrevTaskList= masterProjectPlan.getNoPreviousDefinedTasks();
					if(noPrevTaskList!=null && noPrevTaskList.size()>0)
					{
						errors.add("No Previous Task Defined For Following Tasks :-");
						for (TmWorkflowtasks workflowtasks : noPrevTaskList) {
							errors.add("  -"+workflowtasks.getTaskname());
						}
					}
				}
				//Server Side Security End
				if(errors!=null && errors.size()>0)//During Server Side Validation
				{
					saveMessages(request, messages);
					request.setAttribute("validation_errors", errors);
					
					//resetPage_CreateUpdateStage(form);
					
					return mapping.findForward("initEditWorkflow");
				}
				
				
				
				//end:validation for finalize
				masterProjectPlanModel.finalize(masterProjectPlan,
						tmEmployee);
				request.setAttribute("MASTER_PLAN_FINALIZED", "MASTER_PLAN_FINALIZED");
				actionForward = mapping.findForward("finalWorkflow");
			}
			if (request.getParameter("editFinalizedWorkflow") != null
					&& !request.getParameter("editFinalizedWorkflow").equalsIgnoreCase(
							AppConstants.INI_VALUE)) {
				masterProjectPlanModel.editFinalizedWorkflow(masterProjectPlan,
						tmEmployee);
				actionForward = mapping.findForward("initWorkflow");
			}
			
			

			if (masterProjectPlan.getWorkflowId() != null
					&& !masterProjectPlan.getWorkflowId().equalsIgnoreCase(
							AppConstants.INI_VALUE)) {
				
				if (request.getParameter("editFinalizedWorkflow") != null
						&& !request.getParameter("editFinalizedWorkflow")
								.equalsIgnoreCase(AppConstants.INI_VALUE)) {
					actionForward = mapping.findForward("initEditWorkflow");
				}
				if (request.getParameter("editWorkflow") != null
						&& !request.getParameter("editWorkflow")
								.equalsIgnoreCase(AppConstants.INI_VALUE)) {
					actionForward = mapping.findForward("initEditWorkflow");
				}
				if (request.getParameter("addStage") != null
						&& !request.getParameter("addStage").equalsIgnoreCase(
								AppConstants.INI_VALUE)) {
					actionForward = mapping.findForward("initStage");
				}
				if (request.getParameter("addTask") != null
						&& !request.getParameter("addTask").equalsIgnoreCase(
								AppConstants.INI_VALUE)) {
					actionForward = mapping.findForward("initTask");
				}
				if (request.getParameter("updateTask") != null
						&& !request.getParameter("updateTask")
								.equalsIgnoreCase(AppConstants.INI_VALUE)) {
					actionForward = mapping.findForward("initUpdateTask");
				}

			} else {
				actionForward = mapping.findForward("initWorkflow");
			}
		} catch (Exception ex) {
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(ex));
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in viewMasterPlan method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			String forwardMapping = Messages.getMessageValue("errorGlobalForward");
			return mapping.findForward(forwardMapping);
		}
		if (!messages.isEmpty()) {
			this.saveMessages(request, messages);
		}
		return actionForward;
	}

	// To initialize the page of Master Project Plan History

	public ActionForward viewMasterProjectPlanHistory(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		MasterProjectPlan masterProjectPlan = (MasterProjectPlan) form;
		MasterProjectPlanModel masterProjectPlanModel = new MasterProjectPlanModel();
		ActionMessages messages = new ActionMessages();

		try {
			masterProjectPlan = masterProjectPlanModel
					.viewMasterProjectPlanHistory(masterProjectPlan);
		} catch (Exception ex) {
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(ex));
			
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in viewMasterProjectPlanHistory method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			String forwardMapping = Messages.getMessageValue("errorGlobalForward");
			return mapping.findForward(forwardMapping);
		}
		if (!messages.isEmpty()) {
			this.saveMessages(request, messages);
		}
		return mapping.findForward("initMasterPlanHistory");
	}
	
	// To initialize the page of Master Project Plan History

	public ActionForward viewWorkFlowList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		MasterProjectPlan masterProjectPlan = (MasterProjectPlan) form;
		MasterProjectPlanModel masterProjectPlanModel = new MasterProjectPlanModel();
		ActionMessages messages = new ActionMessages();

		try {
			masterProjectPlan = masterProjectPlanModel
					.viewMasterProjectPlan(masterProjectPlan);
			/*masterProjectPlan = masterProjectPlanModel
					.viewMasterProjectPlanHistory(masterProjectPlan);*/
		} catch (Exception ex) {
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(ex));
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in viewWorkFlowList method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			String forwardMapping = Messages.getMessageValue("errorGlobalForward");
			return mapping.findForward(forwardMapping);
		}
		if (!messages.isEmpty()) {
			this.saveMessages(request, messages);
		}
		return mapping.findForward("masterWorkflowList");
	}

	// To save the Workflow for project plan in the DB.  //sh_rav : Edit :fn called whrn user clicks "Add workflow"
	public ActionForward saveWorkflow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MasterProjectPlan masterProjectPlan = (MasterProjectPlan) form;
		ActionForward actionForward = new ActionForward();
		ActionMessages messages = new ActionMessages();
		boolean insert = false;
		MasterProjectPlanModel masterProjectPlanModel = new MasterProjectPlanModel();
		TmEmployee tmEmployee = (TmEmployee) request.getSession().getAttribute(
				AppConstants.LOGINBEAN);
		TmWorkflow tmWorkflow = new TmWorkflow();
		try {
			if (masterProjectPlan != null)
			{	
				//VALIDATION STARTS
				ArrayList errors=new ArrayList();

				ArrayList newerrors;
				newerrors=Utility.validateValue(new ValidationBean().loadValidationBean_String_SingleSelect
										("NPD Category", masterProjectPlan.getNpdCategoryId(), "-1"),
												Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS,
												Utility.CASE_MANDATORY_SINGLESELECT_STRING))
										.appendToAndRetNewErrs(errors);
								if(newerrors==null)
								{
									Utility.validateValue(new ValidationBean().loadValidationBean_String_SingleSelect
											("NPD Category", masterProjectPlan.getNpdCategoryId(), "-1"),
											""+Utility.CASE_DIGITS_ONLY).appendToAndRetNewErrs(errors);					
								}


				Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
										("Workflow Description", masterProjectPlan.getWorkflowDesc(), 100),
										Utility.generateCSV(Utility.CASE_MANDATORY,Utility.CASE_SPECIALCHARACTERS,
															Utility.CASE_MAXLENGTH))
										.appendToAndRetNewErrs(errors);



				if(errors!=null && errors.size()>0)//During Server Side Validation
				{
					saveMessages(request, messages);
					
					request.setAttribute("validation_errors", errors);
					//resetPage_CreateUpdateStage(form);
					masterProjectPlan = masterProjectPlanModel.viewMasterProjectPlan(masterProjectPlan);
					return mapping.findForward("initWorkflow");
				}
				
				
				//end
				
				tmWorkflow = masterProjectPlanModel.saveWorkflow(
						masterProjectPlan, tmEmployee);
			}
			if (tmWorkflow != null && tmWorkflow.getWorkflowid() != 0) {
				insert = true;
				masterProjectPlan.setWorkflowId(new Integer(tmWorkflow
						.getWorkflowid()).toString());
				masterProjectPlan = masterProjectPlanModel
						.viewMasterProjectPlan(masterProjectPlan);
			}

		} catch (Exception ex) {
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(ex));
			
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in saveWorkflow method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			String forwardMapping = Messages.getMessageValue("errorGlobalForward");
			return mapping.findForward(forwardMapping);
		}
		if (insert) {
			messages.add(AppConstants.RECORD_SAVE_SUCCESS, new ActionMessage(
					AppConstants.RECORD_SAVE_SUCCESS));
			actionForward = mapping.findForward("initEditWorkflow");

		} else {
			messages.add(AppConstants.RECORD_SAVE_FAILURE, new ActionMessage(
					AppConstants.RECORD_SAVE_FAILURE));
			//actionForward = mapping.findForward("initWorkflow");//initStage
			actionForward = mapping.findForward("initStage");
		}
		if (!messages.isEmpty()) {
			this.saveMessages(request, messages);
		}
		return actionForward;

	}

	private ActionForm resetPage_CreateUpdateStage(ActionForm form)
	{
		MasterProjectPlan masterProjectPlan = (MasterProjectPlan) form;
		
		masterProjectPlan.setStage(null);
		masterProjectPlan.setStageDescription(null);
		
		return form;
	}
	// To save the Stage for project plan in the DB.
	public ActionForward saveUpdateStage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MasterProjectPlan masterProjectPlan = (MasterProjectPlan) form;
		ActionMessages messages = new ActionMessages();
		boolean insert = false;
		MasterProjectPlanModel masterProjectPlanModel = new MasterProjectPlanModel();
		TmEmployee tmEmployee = (TmEmployee) request.getSession().getAttribute(
				AppConstants.LOGINBEAN);
		String saveUpdate=null;
		try {
			
			if(masterProjectPlan.getStageId()==null || "".equals(masterProjectPlan.getStageId()) || "-1".equals(masterProjectPlan.getStageId()))
			{
				saveUpdate="save";	
				masterProjectPlan.setStageId("");
			}
			else
			{
				saveUpdate="update";

			}
			
//			do validation
			//stage name ,desc : maxlenngth, special chars, mandatory and
			//for stage name extra check for no matching to other existing stage names
			boolean errorsFound=false;
//			Server Side Security Start for Stage
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(masterProjectPlan.getStage(),"Stage Name",50),
						""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MANDATORY+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
				else
				{
					//chk for duplicate stage name entry
					AjaxHelper helper=new AjaxHelper(null);
					TmWorkflowstage result=helper.checkDuplicateStage(masterProjectPlan.getStage(),masterProjectPlan.getWorkflowId());
					if( result !=null)
					{
						
						if(!masterProjectPlan.getStageId().equals(""+result.getStageid()))
						{
							errors=new ArrayList();
							errors.add("Stage with same Name already exists");
							request.setAttribute("validation_errors", errors);
							errorsFound=true;
						}
					}//if result
					
				}
			}
			//Server Side Security End
//			Server Side Security Start for Stage
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(masterProjectPlan.getStageDescription(),"Stage Description",100),
						""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MANDATORY+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			if(errorsFound)//During Server Side Validation
			{
				saveMessages(request, messages);
				resetPage_CreateUpdateStage(form);
				masterProjectPlan = masterProjectPlanModel
				.viewMasterProjectPlan(masterProjectPlan);
				return mapping.findForward("initStage");
			}
			
			
			if (masterProjectPlan != null)
				insert = masterProjectPlanModel.saveUpdateStage(masterProjectPlan,
						tmEmployee);
			masterProjectPlan = masterProjectPlanModel
					.viewMasterProjectPlan(masterProjectPlan);
		} catch (Exception ex) {
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(ex));
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in saveUpdateStage method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			String forwardMapping = Messages.getMessageValue("errorGlobalForward");
			return mapping.findForward(forwardMapping);
		}
		if("save".equals(saveUpdate))
		{
			if (insert) {
				messages.add(AppConstants.RECORD_SAVE_SUCCESS, new ActionMessage(
						AppConstants.RECORD_SAVE_SUCCESS));
			} else {
				messages.add(AppConstants.RECORD_SAVE_FAILURE, new ActionMessage(
						AppConstants.RECORD_SAVE_FAILURE));
			}
		}else if("update".equals(saveUpdate))
		{
			if (insert) {
				messages.add("STAGE_UPDATION", new ActionMessage(
						AppConstants.RECORD_UPDATE_SUCCESS));
			} else {
				messages.add("STAGE_UPDATION", new ActionMessage(
						AppConstants.RECORD_UPDATE_FAILURE));
			}
		}
		if (!messages.isEmpty()) {
			this.saveMessages(request, messages);
		}
		return mapping.findForward("initStage");

	}
	
	/*public ActionForward updateStage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MasterProjectPlan masterProjectPlan = (MasterProjectPlan) form;
		ActionMessages messages = new ActionMessages();
		boolean insert = false;
		MasterProjectPlanModel masterProjectPlanModel = new MasterProjectPlanModel();
		TmEmployee tmEmployee = (TmEmployee) request.getSession().getAttribute(
				AppConstants.LOGINBEAN);

		try {
			if (masterProjectPlan != null)
				insert = masterProjectPlanModel.updateStage(masterProjectPlan,
						tmEmployee);
			masterProjectPlan = masterProjectPlanModel
					.viewMasterProjectPlan(masterProjectPlan);
		} catch (Exception e) {
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
		}
		if (insert) {
			messages.add("STAGE_UPDATION", new ActionMessage(
					AppConstants.RECORD_UPDATE_SUCCESS));
		} else {
			messages.add("STAGE_UPDATION", new ActionMessage(
					AppConstants.RECORD_UPDATE_FAILURE));
		}
		if (!messages.isEmpty()) {
			this.saveMessages(request, messages);
		}
		return mapping.findForward("initStage");

	}*/

	// To save the Task and its attributes for project plan in the DB.
	public ActionForward saveUpdateTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MasterProjectPlan masterProjectPlan = (MasterProjectPlan) form;
		ActionMessages messages = new ActionMessages();
		boolean insert = false;
		MasterProjectPlanModel masterProjectPlanModel = new MasterProjectPlanModel();
		TmEmployee tmEmployee = (TmEmployee) request.getSession().getAttribute(
				AppConstants.LOGINBEAN);
		try {
			if (masterProjectPlan != null)
			{
				//validation : start
				
				ArrayList errors=new ArrayList();
				
				Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
										("Task", masterProjectPlan.getTask(), 500),
										Utility.generateCSV(Utility.CASE_MANDATORY,Utility.CASE_SPECIALCHARACTERS,
															Utility.CASE_MAXLENGTH))
										.appendToAndRetNewErrs(errors);	
				
				/*Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
						("Task Name", masterProjectPlan.getTask(), 100),
						Utility.generateCSV(Utility.CASE_MANDATORY,Utility.CASE_SPECIALCHARACTERS,
											Utility.CASE_MAXLENGTH))
						.appendToAndRetNewErrs(errors);*/
				
				
				Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
						("Task Description", masterProjectPlan.getTaskDescription(), 500),
						Utility.generateCSV(Utility.CASE_MANDATORY,Utility.CASE_SPECIALCHARACTERS,
											Utility.CASE_MAXLENGTH))
						.appendToAndRetNewErrs(errors);
				
				if(!masterProjectPlan.isFirst())
				{
					Utility.validateValue(new ValidationBean().loadValidationBean_String_MultipleSelect
							("Previous Task", masterProjectPlan.getPreviousTaskId()),
							Utility.generateCSV(Utility.CASE_MANDATORY_MULTIPLE_STRING))
							.appendToAndRetNewErrs(errors);
				}
				else
				{
					masterProjectPlan.setPreviousTaskId(null);
				}
				
				ArrayList newerrors=Utility.validateValue(new ValidationBean().loadValidationBean_String_SingleSelect
						("StakeHolder", masterProjectPlan.getStakeHolderId(), "-1"),
								Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS,
								Utility.CASE_MANDATORY_SINGLESELECT_STRING))
						.appendToAndRetNewErrs(errors);
				if(newerrors==null)
				{
					Utility.validateValue(new ValidationBean().loadValidationBean_String_SingleSelect
							("StakeHolder", masterProjectPlan.getStakeHolderId(), "-1"),
							""+Utility.CASE_DIGITS_ONLY).appendToAndRetNewErrs(errors);					
				}
					
				Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
						("Remarks", masterProjectPlan.getRemarks(), 1000),
						Utility.generateCSV(Utility.CASE_MANDATORY,Utility.CASE_SPECIALCHARACTERS,
											Utility.CASE_MAXLENGTH))
						.appendToAndRetNewErrs(errors);
				
				
				
				//getDuplicateTask(taskName,document.getElementById("stageId").value);
				AjaxHelper helper=new AjaxHelper(null);
				
				if(errors==null || errors.size()==0)
				{
					TmWorkflowtasks result=helper.getDuplicateTask(masterProjectPlan.getTask(), masterProjectPlan.getStageId());
					if( result !=null)
					{	
						if(!String.valueOf(result.getTaskid()).equals(masterProjectPlan.getTaskId()))
						{
							errors.add("Task with same Name for the selected stage already exists");
						}
					}
				}
				if(errors==null || errors.size()==0)
				{
					ArrayList<TmWorkflowtasks> result=helper.fetchFirstAndLastTask(masterProjectPlan.getWorkflowId());
					if( result !=null)
					{
						if(masterProjectPlan.isFirst())
						{
							if(result.get(0)!=null)
							{
								TmWorkflowtasks bean=result.get(0);
								if(!String.valueOf(bean.getTaskid()).equals(masterProjectPlan.getTaskId()))
								{
									errors.add("First Task already exists for this Workflow");
								}
							}
						}
						
						if(masterProjectPlan.isLast())
						{
							if(result.get(1)!=null)
							{
								TmWorkflowtasks bean=result.get(1);
								if(!String.valueOf(bean.getTaskid()).equals(masterProjectPlan.getTaskId()))
								{
									errors.add("Last Task already exists for this Workflow");
								}
							}
						}
					}
				}
								
				if(errors!=null && errors.size()>0)//During Server Side Validation
				{
					saveMessages(request, messages);
					errors.add("AND PLEASE REFILL THE FORM.");
					request.setAttribute("validation_errors", errors);
					//resetPage_CreateUpdateStage(form);
					masterProjectPlan = masterProjectPlanModel.viewMasterProjectPlan(masterProjectPlan);
					return mapping.findForward("initTask");
				}
				
//				validation : end
				
				insert = masterProjectPlanModel.saveUpdateTask(masterProjectPlan,
						tmEmployee);
			}
			masterProjectPlan = masterProjectPlanModel.viewMasterProjectPlan(masterProjectPlan);
		} catch (Exception ex) {
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(ex));
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in saveUpdateTask method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			String forwardMapping = Messages.getMessageValue("errorGlobalForward");
			return mapping.findForward(forwardMapping);
		}
		if (insert) {
			messages.add(AppConstants.RECORD_SAVE_SUCCESS, new ActionMessage(
					AppConstants.RECORD_SAVE_SUCCESS));
		} else {
			messages.add(AppConstants.RECORD_SAVE_FAILURE, new ActionMessage(
					AppConstants.RECORD_SAVE_FAILURE));
		}
		if (!messages.isEmpty()) {
			this.saveMessages(request, messages);
		}
		return mapping.findForward("initTask");

	}

	// To delete the Task and its attributes for project plan in the DB.
	public ActionForward deleteTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		MasterProjectPlan masterProjectPlan = (MasterProjectPlan) form;
		ActionMessages messages = new ActionMessages();
		boolean delete = false;
		MasterProjectPlanModel masterProjectPlanModel = new MasterProjectPlanModel();
		TmEmployee tmEmployee = (TmEmployee) request.getSession().getAttribute(AppConstants.LOGINBEAN);
		try 
		{
			ArrayList<String> deleteTaskIdList = new ArrayList<String>();
			String deleteTaskIdStr = masterProjectPlan.getDeleteTaskId();
			String noDeleteTaskIdStr = masterProjectPlan.getNoDeleteTaskId();
			String[] DeleteTaskIdArr = deleteTaskIdStr.split("&");
			String[] NodeleteTaskIdArr = noDeleteTaskIdStr.split("&");
			if (masterProjectPlan != null)
			{
				for(int i=1;i<DeleteTaskIdArr.length;i++)
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
				}
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
					delete = masterProjectPlanModel.deleteTask(masterProjectPlan,deleteTaskIdList);
					
					if (delete) 
					{
						messages.add(AppConstants.RECORD_DELETE_SUCCESS, new ActionMessage(
								AppConstants.RECORD_DELETE_SUCCESS));
					} 
					else 
					{
						messages.add(AppConstants.RECORD_SAVE_FAILURE, new ActionMessage(
								AppConstants.RECORD_SAVE_FAILURE));
					}
					if (!messages.isEmpty()) {
						this.saveMessages(request, messages);
					}
				}
				masterProjectPlan = masterProjectPlanModel.viewMasterProjectPlan(masterProjectPlan);
			}
		} 
		catch (Exception ex) 
		{
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(ex));
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in deleteTask method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			String forwardMapping = Messages.getMessageValue("errorGlobalForward");
			return mapping.findForward(forwardMapping);
		}
		
		return mapping.findForward("initTask");

	}
	// To get the history of Workflow versions.

	public ActionForward viewHistory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MasterProjectPlan masterProjectPlan = (MasterProjectPlan) form;
		ActionMessages messages = new ActionMessages();
		MasterProjectPlanModel masterProjectPlanModel = new MasterProjectPlanModel();		
		try {
			if (masterProjectPlan != null) {
				//validation
				ArrayList errors=new ArrayList();
				ArrayList newerrors;				
				newerrors=Utility.validateValue(new ValidationBean().loadValidationBean_String_SingleSelect
						("NPD Category", masterProjectPlan.getNpdCategoryId(), "-1"),
								Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS,
								Utility.CASE_MANDATORY_SINGLESELECT_STRING))
						.appendToAndRetNewErrs(errors);
				if(newerrors==null)
				{
					Utility.validateValue(new ValidationBean().loadValidationBean_String_SingleSelect
							("NPD Category", masterProjectPlan.getNpdCategoryId(), "-1"),
							""+Utility.CASE_DIGITS_ONLY).appendToAndRetNewErrs(errors);					
				}
				
				if(errors!=null && errors.size()>0)//During Server Side Validation
				{
					saveMessages(request, messages);
					request.setAttribute("validation_errors", errors);
					//resetPage_CreateUpdateStage(form);
					masterProjectPlan = masterProjectPlanModel
					.viewMasterProjectPlanHistory(masterProjectPlan);
					
					return mapping.findForward("initMasterPlanHistory");
				}
//				validation end
				
				
				masterProjectPlan = masterProjectPlanModel
						.viewVersionHistory(masterProjectPlan);
				masterProjectPlan = masterProjectPlanModel
						.viewMasterProjectPlanHistory(masterProjectPlan);
			}

		} catch (Exception ex) {
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(ex));
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in viewHistory method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			String forwardMapping = Messages.getMessageValue("errorGlobalForward");
			return mapping.findForward(forwardMapping);
		}
		if (masterProjectPlan.getTaskList() == null
				|| masterProjectPlan.getTaskList().size() <= 0) {
			messages.add(AppConstants.NO_RECORD, new ActionMessage(
					AppConstants.NO_RECORD));
		}
		if (!messages.isEmpty()) {
			this.saveMessages(request, messages);
		}
		return mapping.findForward("initMasterPlanHistory");

	}
	
//	 To get the history of Workflow versions on click of validate order in change order.  By Saurabh

	public ActionForward viewChangeOrderWorkflow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MasterProjectPlan masterProjectPlan = (MasterProjectPlan) form;
		ActionMessages messages = new ActionMessages();
		MasterProjectPlanModel masterProjectPlanModel = new MasterProjectPlanModel();		
		try {
			if (masterProjectPlan != null) {
				//validation
				ArrayList errors=new ArrayList();
				ArrayList newerrors;				
				newerrors=Utility.validateValue(new ValidationBean().loadValidationBean_String_SingleSelect
						("NPD Category", masterProjectPlan.getNpdCategoryId(), "-1"),
								Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS,
								Utility.CASE_MANDATORY_SINGLESELECT_STRING))
						.appendToAndRetNewErrs(errors);
				if(newerrors==null)
				{
					Utility.validateValue(new ValidationBean().loadValidationBean_String_SingleSelect
							("NPD Category", masterProjectPlan.getNpdCategoryId(), "-1"),
							""+Utility.CASE_DIGITS_ONLY).appendToAndRetNewErrs(errors);					
				}
				
				if(errors!=null && errors.size()>0)//During Server Side Validation
				{
					saveMessages(request, messages);
					request.setAttribute("validation_errors", errors);
					//resetPage_CreateUpdateStage(form);
					masterProjectPlan = masterProjectPlanModel
					.viewMasterProjectPlanHistory(masterProjectPlan);
					
					return mapping.findForward("changeOrderWorkflow");
				}
//				validation end
				
				
				masterProjectPlan = masterProjectPlanModel
						.viewVersionHistory(masterProjectPlan);
				masterProjectPlan = masterProjectPlanModel
						.viewMasterProjectPlanHistory(masterProjectPlan);
			}

		} catch (Exception ex) {
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(ex));
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in viewHistory method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			String forwardMapping = Messages.getMessageValue("errorGlobalForward");
			return mapping.findForward(forwardMapping);
		}
		if (masterProjectPlan.getTaskList() == null
				|| masterProjectPlan.getTaskList().size() <= 0) {
			messages.add(AppConstants.NO_RECORD, new ActionMessage(
					AppConstants.NO_RECORD));
		}
		if (!messages.isEmpty()) {
			this.saveMessages(request, messages);
		}
		return mapping.findForward("changeOrderWorkflow");

	}
	
}
