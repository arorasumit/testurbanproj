package com.ibm.ioes.npd.actions;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

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

import com.ibm.ioes.npd.beans.MeetingBean;
import com.ibm.ioes.npd.beans.UserNpdSpocs;
import com.ibm.ioes.npd.beans.ValidationBean;
import com.ibm.ioes.npd.hibernate.beans.TmEmployee;
import com.ibm.ioes.npd.hibernate.dao.MyToDOListDaoImpl;
import com.ibm.ioes.npd.model.NpdUserModel;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.Messages;
import com.ibm.ioes.npd.utilities.Utility;

public class ManageNpdSpocsAction extends LookupDispatchAction {

	private static final Logger log;

	static {
		log = Logger.getLogger(ManageNpdSpocsAction.class);
	}

	public ManageNpdSpocsAction() {

	}

	protected Map getKeyMethodMap() {
		Map map = new HashMap();

		map.put("link.viewNpdSpocs", "viewNpdSpocs");
		map.put("submit.saveNPDSpocs", "saveNpdSpocs");
		map.put("link.viewNpdSpocsRoleHistory","viewNpdSpocsRoleHistory");
		map.put("submit.search", "searchNpdSpocsRoleHistory");
		map.put("submit.export", "exportToExcel");
		map.put("initRoleReport", "initRoleReport");
		map.put("showRoleReport", "showRoleReport");
		

		return map;
	}

	// To initialize the page of Defining NPD spocs and choosing their
	// Excalation Levels.
	public ActionForward viewNpdSpocs(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserNpdSpocs userNpdSpocs = (UserNpdSpocs) form;
		NpdUserModel npdUserModel = new NpdUserModel();
		ActionMessages messages = new ActionMessages();
		try {
			userNpdSpocs = npdUserModel.viewNpdSpocs(userNpdSpocs);
			userNpdSpocs.setUserReplacedRoleIds("");
		} catch (Exception e) {
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
		}
		if (!messages.isEmpty()) {
			saveErrors(request, messages);
		}

		return mapping.findForward("initUserNpdSpocs");
	}

	// To initialize the page of Defining NPD spocs and choosing their
	// Excalation Levels.
	public ActionForward saveNpdSpocs(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserNpdSpocs userNpdSpocs = (UserNpdSpocs) form;

		NpdUserModel npdUserModel = new NpdUserModel();
		ActionMessages messages = new ActionMessages();
		boolean insert = false;
		TmEmployee tmEmployee =	(TmEmployee)request.getSession().getAttribute(AppConstants.LOGINBEAN);
		String selectedUsers=request.getParameter("selectedUsers");
		boolean errorsFound=false;
		try 
		{
			HashMap map=new HashMap();
			String csv=userNpdSpocs.getPreviousRoleId();
			StringTokenizer st=new StringTokenizer(csv,",");
			while(st.hasMoreTokens())
			{
				map.put(st.nextToken(),"1");
			}
			
			String []str=userNpdSpocs.getSelectedRoleId();
			int flag=0;
			int levelflag=0;
			int levelemployeeflag=0;
			if(str!=null)
			{
				for(int i=0;i<str.length;i++)
				{
					String key=str[i];
					String val=(String)map.get(key);
					if(val==null)
					{
						flag=1;//new data
					}
					else
					{
						map.remove(key);
					}
				}
			}
			else
			{
				if(map.isEmpty())
				{
					flag=0;
				}
				else
				{
					flag=1;
				}
			}
			if(!map.keySet().isEmpty())
			{
				flag=1;
			}
			if(!"1".equals(userNpdSpocs.getEscalation()))
			{
				if (!userNpdSpocs.getLevel1Id().equals(userNpdSpocs.getOriginalLevel1Id())) 
				{
					levelflag=1;
				}
				if (!userNpdSpocs.getLevel2Id().equals(userNpdSpocs.getOriginalLevel2Id())) 
				{
					levelflag=1;
				}
				
				if (!userNpdSpocs.getLevel1EmployeeId().equals(userNpdSpocs.getOriginalLevel1EmployeeId())) 
				{
					levelemployeeflag=1;
				}
				if (!userNpdSpocs.getLevel2EmployeeId().equals(userNpdSpocs.getOriginalLevel2EmployeeId())) 
				{
					levelemployeeflag=1;
				}
			}
			else
			{
				userNpdSpocs.setLevel1Id(null);
				userNpdSpocs.setLevel1Id(null);
				userNpdSpocs.setLevel1EmployeeId(null);
				userNpdSpocs.setLevel1EmployeeId(null);
				
				if(!((userNpdSpocs.getOriginalLevel1Id()==null || userNpdSpocs.getOriginalLevel1Id().equals("")
														|| userNpdSpocs.getOriginalLevel1Id().equals("-1"))
						&&
					(userNpdSpocs.getOriginalLevel2Id()==null || userNpdSpocs.getOriginalLevel2Id().equals("")
														|| userNpdSpocs.getOriginalLevel2Id().equals("-1"))
						&&
					(userNpdSpocs.getOriginalLevel1EmployeeId()==null || userNpdSpocs.getOriginalLevel1EmployeeId().equals("")
														|| userNpdSpocs.getOriginalLevel1EmployeeId().equals("-1"))
						&&
					(userNpdSpocs.getOriginalLevel2EmployeeId()==null || userNpdSpocs.getOriginalLevel2EmployeeId().equals("")
														|| userNpdSpocs.getOriginalLevel2EmployeeId().equals("-1")))
				)
				{
					levelflag=1;
					levelemployeeflag=1;
				}
			}
			
			if((flag==1) || (levelflag==1) || (levelemployeeflag==1))
			{
				//Server Side Security Start for Employee ID
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(userNpdSpocs.getEmployeeId(),"Employee ID",1000),
							""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MANDATORY).getCompleteMessageStrings();					
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;
					}
				}
				//Server Side Security End
				
				if(!"1".equals(userNpdSpocs.getEscalation()))
				{
					
				
				//Server Side Security Start for Level 1 Employee ID
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(userNpdSpocs.getLevel1EmployeeId(),"Level 1 Employee ID",1000),
							""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MANDATORY).getCompleteMessageStrings();					
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;
					}
				}
				//Server Side Security End
				
				//Server Side Security Start for Level 2 Employee ID
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(userNpdSpocs.getLevel2EmployeeId(),"Level 2 Employee ID",1000),
							""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MANDATORY).getCompleteMessageStrings();					
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;
					}
				}
				//Server Side Security End
				
				//Server Side Security Start for Level 2 Employee ID
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(userNpdSpocs.getLevel1Id(),"Escalation Level 1",1000),
							""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MANDATORY).getCompleteMessageStrings();					
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;
					}
				}
				//Server Side Security End
				
				//Server Side Security Start 
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(userNpdSpocs.getLevel2Id(),"Escalation Level 2",1000),
							""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MANDATORY).getCompleteMessageStrings();					
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;
					}
				}
				//Server Side Security End
				
				//Server Side Security Start 
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(userNpdSpocs.getOriginalLevel1EmployeeId(),"Original Level 1 Employee ID",1000),
							""+Utility.CASE_SPECIALCHARACTERS).getCompleteMessageStrings();					
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;
					}
				}
				//Server Side Security End
				
				//Server Side Security Start 
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(userNpdSpocs.getOriginalLevel2EmployeeId(),"Original Level 2 Employee ID",1000),
							""+Utility.CASE_SPECIALCHARACTERS).getCompleteMessageStrings();					
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;
					}
				}
				//Server Side Security End
				
				//Server Side Security Start 
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(userNpdSpocs.getOriginalLevel1Id(),"Original Level 1",1000),
							""+Utility.CASE_SPECIALCHARACTERS).getCompleteMessageStrings();					
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;
					}
				}
				//Server Side Security End
				
				//Server Side Security Start 
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(userNpdSpocs.getOriginalLevel2Id(),"Original Level 2",1000),
							""+Utility.CASE_SPECIALCHARACTERS).getCompleteMessageStrings();					
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;
					}
				}
				//Server Side Security End
				}
				//Server Side Security Start for Selected Roles
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean().loadValidationBean_String_MultipleSelect("Selected Role", userNpdSpocs.getSelectedRoleId()),
							""+Utility.CASE_SPECIALCHARACTERS).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;
					}
				}
				//Server Side Security End
				
				//Server Side Security Start for Selected Roles
				if(!userNpdSpocs.getUserReplacedRoleIds().equalsIgnoreCase("") && userNpdSpocs.getUserReplacedRoleIds()!=null)
				{
					if(!errorsFound)
					{
						ArrayList errors=Utility.validateValue(new ValidationBean(userNpdSpocs.getUserReplacedRoleIds(),"User Replaced",1000),
								""+Utility.CASE_SPECIALCHARACTERS).getCompleteMessageStrings();					
						if(errors!=null)
						{
							request.setAttribute("validation_errors", errors);
							errorsFound=true;
						}
					}
				}
				//Server Side Security End
				
				//Server Side Security Start for Selected Roles
				if(!userNpdSpocs.getPreviousRoleId().equalsIgnoreCase("") && userNpdSpocs.getPreviousRoleId()!=null)
				{
					if(!errorsFound)
					{
						ArrayList errors=Utility.validateValue(new ValidationBean(userNpdSpocs.getPreviousRoleId(),"Previous Role ID",1000),
								""+Utility.CASE_SPECIALCHARACTERS).getCompleteMessageStrings();					
						if(errors!=null)
						{
							request.setAttribute("validation_errors", errors);
							errorsFound=true;
						}
					}
				}
				//Server Side Security End
				
				if(errorsFound)//During Server Side Validation
				{
					saveMessages(request, messages);
					BeanUtils.copyProperties(new MeetingBean(), userNpdSpocs);
					return mapping.findForward("failure");
				}
				else
				{
					insert = npdUserModel.saveNpdSpocs(userNpdSpocs,tmEmployee,selectedUsers);
					if(insert)
					{
						npdUserModel.sendMailForNewRolesAssigned(userNpdSpocs);
						npdUserModel.sendMailForNewTasksAssigned(selectedUsers,userNpdSpocs);
					}
				}
			}
			else
			{
				messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
						AppConstants.SAME_DATA));
				saveMessages(request, messages);
				return mapping.findForward("success");
			}
		} 
		catch (Exception e) 
		{
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		if(insert)
		{
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.RECORD_SAVE_SUCCESS));
			
		}
		else {
			messages.add(AppConstants.RECORD_SAVE_FAILURE, new ActionMessage(
					AppConstants.RECORD_SAVE_FAILURE));
		}
		if (!messages.isEmpty()) {
			this.saveMessages(request, messages);
		}
		return mapping.findForward("success");

	}
	

	// To initialize the page of searching the history of Roles mapped to an Employee
	public ActionForward viewNpdSpocsRoleHistory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserNpdSpocs userNpdSpocs = (UserNpdSpocs) form;
		NpdUserModel npdUserModel = new NpdUserModel();
		ActionMessages messages = new ActionMessages();
		try 
		{
			
		} 
		catch (Exception e) {
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
		}
		if (!messages.isEmpty()) {
			saveErrors(request, messages);
		}

		return mapping.findForward("initUserNpdSpocsRoleHistory");
	}

	// To search the history of Roles mapped to an Employee
	public ActionForward searchNpdSpocsRoleHistory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserNpdSpocs userNpdSpocs = (UserNpdSpocs) form;
		NpdUserModel npdUserModel = new NpdUserModel();
		ActionMessages messages = new ActionMessages();
		try {
			
			//Validation start

			boolean errorsFound=false;
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(userNpdSpocs.getEmployeeName(),"Employee Name",100),
						""+Utility.CASE_MAXLENGTH+","+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MANDATORY)
						.getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			if(!errorsFound)
			{
				Object arr[]=new Object[]{""+ValidationBean.VN_DATE_COMPARISION,
						userNpdSpocs.getFromDate(),"From Date",
						userNpdSpocs.getToDate(),"To Date",ValidationBean.LESS_EQUAL,
						new SimpleDateFormat(Messages.getMessageValue("calendar_entry_format"))};
						
				
				ArrayList errors=Utility.validateValue(new ValidationBean(arr),
						""+Utility.CASE_DATECOMPARISION_NONMANDATORY_v2).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			
			//Server Side Security End
			
			if(errorsFound)//During Server Side Validation
			{
				
				//forward = mapping.findForward(AppConstants.FAILURE);
				//referenceDocBean.setRfDocSearch(null);
				return mapping.findForward("initUserNpdSpocsRoleHistory");
			}
			//validation end
			else
			{
				userNpdSpocs = npdUserModel.searchNpdSpocsRoleHistory(userNpdSpocs);
				if (userNpdSpocs.getRoleHistoryList() == null
						|| userNpdSpocs.getRoleHistoryList().size() <= 0) 
				{
					messages.add(AppConstants.NO_RECORD, new ActionMessage(
							AppConstants.NO_RECORD));
				}
			}
			
			
		} catch (Exception e) {
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
		}
		if (!messages.isEmpty()) {
			saveErrors(request, messages);
		}

		return mapping.findForward("initUserNpdSpocsRoleHistory");
	}

	// To search the history of Roles mapped to an Employee
	public ActionForward exportToExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserNpdSpocs userNpdSpocs = (UserNpdSpocs) form;
		NpdUserModel npdUserModel = new NpdUserModel();
		ActionMessages messages = new ActionMessages();
		try {
			
//			Validation start

			boolean errorsFound=false;
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(userNpdSpocs.getEmployeeName(),"Employee Name",100),
						""+Utility.CASE_MAXLENGTH+","+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MANDATORY)
						.getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			if(!errorsFound)
			{
				Object arr[]=new Object[]{""+ValidationBean.VN_DATE_COMPARISION,
						userNpdSpocs.getFromDate(),"From Date",
						userNpdSpocs.getToDate(),"To Date",ValidationBean.LESS_EQUAL,
						new SimpleDateFormat(Messages.getMessageValue("calendar_entry_format"))};
						
				
				ArrayList errors=Utility.validateValue(new ValidationBean(arr),
						""+Utility.CASE_DATECOMPARISION_NONMANDATORY_v2).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			
			//Server Side Security End
			
			if(errorsFound)//During Server Side Validation
			{
				
				//forward = mapping.findForward(AppConstants.FAILURE);
				//referenceDocBean.setRfDocSearch(null);
				return mapping.findForward("initUserNpdSpocsRoleHistory");
			}
			
			//validation end
			
			
			userNpdSpocs = npdUserModel.searchNpdSpocsRoleHistory(userNpdSpocs);
			if (userNpdSpocs.getRoleHistoryList() == null
					|| userNpdSpocs.getRoleHistoryList().size() <= 0) {
				messages.add(AppConstants.NO_RECORD, new ActionMessage(
						AppConstants.NO_RECORD));
			}
			request.setAttribute("roleHistoryList", userNpdSpocs.getRoleHistoryList());
		} catch (Exception e) {
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
		}
		if (!messages.isEmpty()) {
			saveErrors(request, messages);
		}

		return mapping.findForward("exporttoExcel");
	}

	public ActionForward initRoleReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserNpdSpocs userNpdSpocs = (UserNpdSpocs) form;
		NpdUserModel npdUserModel = new NpdUserModel();
		ActionMessages messages = new ActionMessages();
		try {
			userNpdSpocs = npdUserModel.initRoleReport(userNpdSpocs);
			
		} catch (Exception e) {
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
		}
		if (!messages.isEmpty()) {
			saveErrors(request, messages);
		}

		return mapping.findForward("roleReport");
	}
	
	public ActionForward showRoleReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserNpdSpocs userNpdSpocs = (UserNpdSpocs) form;
		NpdUserModel npdUserModel = new NpdUserModel();
		ActionMessages messages = new ActionMessages();
		boolean errorsFound=false;
		try 
		{
			initRoleReport(mapping, form, request, response);		
			ArrayList<TmEmployee> employeeList=null;
			TmEmployee searchDto=new TmEmployee();
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(userNpdSpocs.getReportRoleId(),"Role ID"),
						Utility.CASE_DIGITS_ONLY+"").getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			if(errorsFound)
			{
				saveMessages(request, messages);
				return mapping.findForward("customerListPage");
			}
			searchDto.setCurrentRoleId(Long.parseLong(userNpdSpocs.getReportRoleId()));
			employeeList=npdUserModel.fetchEmployeesOfRole(searchDto);
			userNpdSpocs.setEmployeeList(employeeList);
			
			if(employeeList==null || employeeList.size()==0)
			{
				messages.add("", new ActionMessage("noRecordFound"));
			}
			
		} catch (Exception ex) {
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(ex));
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in showRoleReport method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			String forwardMapping = Messages.getMessageValue("errorGlobalForward");
			return mapping.findForward(forwardMapping);
		}
		saveMessages(request, messages);

		return mapping.findForward("roleReport");
	}


}
