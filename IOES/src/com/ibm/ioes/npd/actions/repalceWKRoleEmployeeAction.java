package com.ibm.ioes.npd.actions;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import com.ibm.ioes.npd.beans.ProjectPlanInstanceBean;
import com.ibm.ioes.npd.beans.RoleEmployeeBean;
import com.ibm.ioes.npd.beans.TaskInstanceBean;
import com.ibm.ioes.npd.beans.ValidationBean;
import com.ibm.ioes.npd.hibernate.beans.TmEmployee;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjecthierarchy;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjectworkflow;
import com.ibm.ioes.npd.hibernate.dao.AttachEditProjectPlanDao;
import com.ibm.ioes.npd.model.AttachEditProjectPlanModel;
import com.ibm.ioes.npd.model.repalceWKRoleEmployeeModel;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.Messages;
import com.ibm.ioes.npd.utilities.NpdConnection;
import com.ibm.ioes.npd.utilities.PagingSorting;
import com.ibm.ioes.npd.utilities.Utility;

public class repalceWKRoleEmployeeAction extends DispatchAction {
	
	private static final Logger log;
	static {
		log = Logger.getLogger(repalceWKRoleEmployeeAction.class);
	}
	
	final String sep="~~~~";
	
	//
	public ActionForward init_updateRoleEmployee(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			String forwardMapping=null;
		
			try
			{
				ProjectPlanInstanceBean formBean=(ProjectPlanInstanceBean)form;
				ActionMessages messages=new ActionMessages();
				
				repalceWKRoleEmployeeModel model=new repalceWKRoleEmployeeModel(); 
				
				model.init_updateRoleEmployee(formBean);
				
				//AttachEditProjectPlanModel model=new AttachEditProjectPlanModel();
				//model.setRoleEmployeeView(formBean);
				
				
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
	
	public ActionForward initLoadRoleEmployeeMapping(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			String forwardMapping=null;
			Connection connection=null;
			try
			{
				connection=NpdConnection.getConnectionObject();
				ProjectPlanInstanceBean formBean=(ProjectPlanInstanceBean)form;
				
				//validation start
				ArrayList errors=new ArrayList();
				
				ArrayList newerrors=Utility.validateValue(new ValidationBean().loadValidationBean_String_SingleSelect
						("Product", formBean.getProjectId(), "-1"),
						""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MANDATORY_SINGLESELECT_STRING)
						.appendToAndRetNewErrs(errors);
				if(newerrors==null)
				{
					Utility.validateValue(new ValidationBean().loadValidationBean_String_SingleSelect
							("Product", formBean.getProjectId(), "-1"),
							""+Utility.CASE_DIGITS_ONLY).appendToAndRetNewErrs(errors);					
				}
				
				if(errors!=null && errors.size()>0)//During Server Side Validation
				{
					
					request.setAttribute("validation_errors", errors);
					formBean.setProjectId(null);
					init_updateRoleEmployee(mapping, form, request, response);
					return mapping.findForward("updateRoleEmployee");
				}

				
				//validation end
				
				
				ActionMessages messages=new ActionMessages();
				
				AttachEditProjectPlanModel attachEditProjectPlanModel=new AttachEditProjectPlanModel();
				attachEditProjectPlanModel.setProductDetails(formBean);
				
				
				repalceWKRoleEmployeeModel model=new repalceWKRoleEmployeeModel(); 
				//fetch projectworkflowid for both current 
				TtrnProjectworkflow pw=model.getActiveProjectWorkflowId(formBean.getProjectId());
				
				
				formBean.setProjectWorkflowId(String.valueOf(pw.getProjectworkflowid()));
				
				//set role employee list
				
//				fetch all roles
				
				
				
				
				ArrayList<TtrnProjecthierarchy> tasksView=null;
				AttachEditProjectPlanDao daoObj=new AttachEditProjectPlanDao(); 
				//fecthing taskss
				ProjectPlanInstanceBean taskSearchDto=new ProjectPlanInstanceBean();
				taskSearchDto.setProjectWorkflowId(formBean.getProjectWorkflowId());
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
			}finally
			{
				try
				{
					NpdConnection.freeConnection(connection);
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
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
//					Validate start
					ArrayList errors=new ArrayList();
					Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
							("Checkbox", selected[i], 15),
							Utility.generateCSV(Utility.CASE_MANDATORY,Utility.CASE_DIGITS_ONLY,
												Utility.CASE_MAXLENGTH))
							.appendToAndRetNewErrs(errors);
					if(errors!=null && errors.size()>0)//During Server Side Validation
					{
						saveMessages(request, messages);
						request.setAttribute("validation_errors", errors);
						initLoadRoleEmployeeMapping(mapping, form, request, response);
						return mapping.findForward("updateRoleEmployee");
					}
					//Validate end
					
					String roleId=(String)request.getParameter(name);
					name="roleRow["+selected[i]+"].employeeId";
					String empId=(String)request.getParameter(name);
					
					RoleEmployeeBean bean=new RoleEmployeeBean();
					bean.setRoleId(roleId);
					bean.setEmployeeId(empId);
					
					//Validate start
					
					errors=new ArrayList();
					ArrayList newerrors=Utility.validateValue(new ValidationBean().loadValidationBean_String_SingleSelect
							("Assigned To", empId, "-1"),
							""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MANDATORY_SINGLESELECT_STRING)
							.appendToAndRetNewErrs(errors);
					if(newerrors==null)
					{
						Utility.validateValue(new ValidationBean().loadValidationBean_String_SingleSelect
								("Assigned To", empId, "-1"),
								""+Utility.CASE_DIGITS_ONLY).appendToAndRetNewErrs(errors);					
					}
					
					
					if(errors!=null && errors.size()>0)//During Server Side Validation
					{
						saveMessages(request, messages);
						request.setAttribute("validation_errors", errors);
						initLoadRoleEmployeeMapping(mapping, form, request, response);
						return mapping.findForward("updateRoleEmployee");
					}
					
					
					
					//Validate end
					list.add(bean);
					
					
				}
				
				//fetch details of selected task(currentTaskId,projectWorkflowId) in bean
				repalceWKRoleEmployeeModel model=new repalceWKRoleEmployeeModel(); 
				
				int status=model.replaceRoleEmployee(formBean,list);
				
				if(status==1)
				{
					//success
					messages.add("updateMessage",new ActionMessage("workflow.employee.mapped.success"));
					model.sendMailForTaskNewAssigns(formBean,list);
				}
				else if(status==-1)
				{
					//failure
					messages.add("updateMessage",new ActionMessage("workflow.employee.mapped.failure"));
				}
				
				initLoadRoleEmployeeMapping(mapping, form, request, response);
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
	
	public ActionForward viewProjectStatusDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			String forwardMapping=null;
		
			try
			{
				forwardMapping="viewProjectStatusDetail";	
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				AppConstants.NPDLOGGER.error(ex.getMessage()
						+ " Exception occured in viewProjectStatusDetail method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
				forwardMapping = Messages.getMessageValue("errorGlobalForward");
				return mapping.findForward(forwardMapping);
			}
			
		
			return mapping.findForward(forwardMapping);
		}
	
}
