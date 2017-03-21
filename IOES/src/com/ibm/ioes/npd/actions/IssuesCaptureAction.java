/**
 * 
 */
package com.ibm.ioes.npd.actions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;

import com.ibm.ioes.npd.beans.IssuesCaptureBean;
import com.ibm.ioes.npd.beans.RisksCaptureBean;
import com.ibm.ioes.npd.beans.ValidationBean;
import com.ibm.ioes.npd.hibernate.beans.TmEmployee;
import com.ibm.ioes.npd.hibernate.dao.CommonBaseDao;
import com.ibm.ioes.npd.model.IssuesCaptureModel;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.Messages;
import com.ibm.ioes.npd.utilities.Utility;

/**
 * @author Sanjay
 * 
 */
public class IssuesCaptureAction extends LookupDispatchAction {

	protected Map getKeyMethodMap() {

		Map map = new HashMap();
		map.put("link.viewIssues", "viewIssues");
		map.put("submit.save", "addIssues");
		map.put("link.searchIssues", "initsearchIssues");
		map.put("searchIssues", "searchIssues");
		map.put("link.searchOnlyIssues", "viewOnlyIssues");
		map.put("submit.onlysearch", "searchOnlyIssues");
		return map;
	}

	public ActionForward viewIssues(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = new ActionForward();
		IssuesCaptureBean captureBean = (IssuesCaptureBean) form;
		IssuesCaptureModel model = new IssuesCaptureModel();
		ActionMessages messages = new ActionMessages();
		TmEmployee tmEmployee = (TmEmployee) request.getSession().getAttribute(
				AppConstants.LOGINBEAN);
		try {
			String issueId=request.getParameter("issueId");
			captureBean = model.viewIssues(captureBean,issueId,tmEmployee);
		
		} catch (Exception e) {
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
		}
		if (!messages.isEmpty()) {
			saveErrors(request, messages);
		}

		forward = mapping.findForward("initIssue");

		return forward;
	}

	public ActionForward addIssues(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		ActionForward forward = new ActionForward();
		IssuesCaptureBean captureBean = (IssuesCaptureBean) form;
		IssuesCaptureModel model = new IssuesCaptureModel();
		ActionMessages messages = new ActionMessages();
		TmEmployee tmEmployee = (TmEmployee) request.getSession().getAttribute(AppConstants.LOGINBEAN);
		boolean insert = true;
		boolean errorsFound=false;
		try 
		{
			//Server Side Security Start for Project ID
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(captureBean.getProjectId(),"Project ID",30),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Description
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(captureBean.getDescription(),"Description",30),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Status
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(captureBean.getStatus(),"Status",30),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Priority
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(captureBean.getPriority(),"Priority",30),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_SPECIALCHARACTERS).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Time Frame
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(captureBean.getTimeframe(),"Time Frame",3),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_DIGITS_ONLY+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Resolution Owner
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(captureBean.getResolutionOwner(),"Resolution Owner",2),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Resolution Date
			if(!errorsFound)
			{
				Object obArray[]={""+ValidationBean.VN_DATE_VALID,captureBean.getPlannedReslDate(),"Planned Resolution Date",
						new java.text.SimpleDateFormat(Messages.getMessageValue("calendar_entry_format"))};
				ArrayList errors=Utility.validateValue(new ValidationBean(obArray),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_VN_DATE_VALID).getCompleteMessageStrings();
				
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End for Resolution Date
			
			//Server Side Security Start for Actual Resolution Date
			if(!errorsFound)
			{
				Object obArray[]={""+ValidationBean.VN_DATE_VALID,captureBean.getActualReslDate(),"Actual Resolution Date",
						new java.text.SimpleDateFormat(Messages.getMessageValue("calendar_entry_format"))};
				ArrayList errors=Utility.validateValue(new ValidationBean(obArray),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_VN_DATE_VALID).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End for Actual Resolution Date
			
			//Server Side Security Start for Raised On
			if(!errorsFound)
			{
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(AppConstants.DATE_FORMAT_PROC);
				Object obArray[]={""+ValidationBean.VN_DATE_COMPARISION,captureBean.getRaisedon(),"Raised On Date",
						simpleDateFormat.format(new Date()),"Current Date",ValidationBean.EQUAL,
						new java.text.SimpleDateFormat(Messages.getMessageValue("calendar_entry_format"))};
				ArrayList errors=Utility.validateValue(	new ValidationBean(obArray),
						""+Utility.CASE_DATECOMPARISION_MANDATORY).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End for Actual Raised On
			
			//Server Side Security Start for Resolution Steps
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(captureBean.getReslutionSteps(),"Resolution Steps",1000),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_MAXLENGTH+","+Utility.CASE_SPECIALCHARACTERS).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End

			if(errorsFound)
			{
				saveMessages(request, messages);
				BeanUtils.copyProperties(new IssuesCaptureBean(), captureBean);
				captureBean = model.getIssuesForAProject(captureBean);
				captureBean = model.viewIssues(captureBean, null, tmEmployee);
				forward = mapping.findForward(AppConstants.FAILURE);
				return forward;
			}
			else
			{
				insert = model.addIssues(captureBean, tmEmployee);
				
				if(captureBean.getIssueID() != null && !captureBean.getIssueID().equalsIgnoreCase(AppConstants.INI_VALUE))
				{
					captureBean = model.getIssuesForAProject(captureBean);
					captureBean = model.viewIssues(captureBean,null,tmEmployee);
					forward = mapping.findForward("viewIssue");
				}
				else
				{
					captureBean = model.viewIssues(captureBean,null,tmEmployee);
					forward = mapping.findForward("initIssue");
				}
			}

		} catch (Exception e) {
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
		}
		if (insert) {
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.RECORD_SAVE_SUCCESS));

		} else {
			messages.add(AppConstants.RECORD_SAVE_FAILURE, new ActionMessage(
					AppConstants.RECORD_SAVE_FAILURE));
		}
		if (!messages.isEmpty()) {
			this.saveMessages(request, messages);
		}

			return forward;
	}

	public ActionForward initsearchIssues(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ActionForward forward = new ActionForward();
		IssuesCaptureBean captureBean = (IssuesCaptureBean) form;
		IssuesCaptureModel model = new IssuesCaptureModel();
		ActionMessages messages = new ActionMessages();
		TmEmployee tmEmployee = (TmEmployee) request.getSession().getAttribute(
				AppConstants.LOGINBEAN);
		try {
			captureBean = model.viewIssues(captureBean,null,tmEmployee);
		} catch (Exception e) {
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
		}
		if (!messages.isEmpty()) {
			this.saveMessages(request, messages);
		}
		forward = mapping.findForward("viewIssue");

		return forward;
	}

	public ActionForward searchIssues(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{

		ActionForward forward = new ActionForward();
		IssuesCaptureBean captureBean = (IssuesCaptureBean) form;
		IssuesCaptureModel model = new IssuesCaptureModel();
		ActionMessages messages = new ActionMessages();
		TmEmployee tmEmployee = (TmEmployee) request.getSession().getAttribute(
				AppConstants.LOGINBEAN);
		boolean errorsFound=false;
		try 
		{
			//Server Side Security Start for Project ID
			if((captureBean.getProjectId().equalsIgnoreCase("") || captureBean.getProjectId()==null)
					&& (captureBean.getProjectName().equalsIgnoreCase("") || captureBean.getProjectName()==null)
					&& ((captureBean.getFromDate().equalsIgnoreCase("") || captureBean.getFromDate()==null)
							|| (captureBean.getToDate().equalsIgnoreCase("") || captureBean.getToDate()==null)))
			{
				request.setAttribute("validation_errors1", "Please Select Project ID, Proje Name or From and To Date to Search");
				errorsFound=true;
			}
			else
			{
				if(captureBean.getProjectId()!=null && (!captureBean.getProjectId().equalsIgnoreCase("")))
				{
					if(!errorsFound)
					{
						ArrayList errors=Utility.validateValue(new ValidationBean(captureBean.getProjectId(),"Project ID",5),
								""+Utility.CASE_DIGITS_ONLY+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
						if(errors!=null)
						{
							request.setAttribute("validation_errors", errors);
							errorsFound=true;
						}
					}
				}
				if(captureBean.getProjectName()!=null && (!captureBean.getProjectName().equalsIgnoreCase("")))
				{
					if(!errorsFound)
					{
						ArrayList errors=Utility.validateValue(new ValidationBean(captureBean.getProjectName(),"Project Name",20),
								""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
						if(errors!=null)
						{
							request.setAttribute("validation_errors", errors);
							errorsFound=true;
						}
					}
				}
				if((!captureBean.getFromDate().equalsIgnoreCase("") && captureBean.getFromDate()!=null)
						|| (!captureBean.getToDate().equalsIgnoreCase("") && captureBean.getToDate()!=null))
				{
					if(!errorsFound)
					{
						Object obArray[]={""+ValidationBean.VN_DATE_COMPARISION,captureBean.getFromDate(),"From Date",
								captureBean.getToDate(),"To Date",ValidationBean.LESS_EQUAL,
								new java.text.SimpleDateFormat(Messages.getMessageValue("calendar_entry_format"))};
						ArrayList errors=Utility.validateValue(	new ValidationBean(obArray),
								""+Utility.CASE_DATECOMPARISION_NONMANDATORY).getCompleteMessageStrings();
						if(errors!=null)
						{
							request.setAttribute("validation_errors", errors);
							errorsFound=true;
						}
					}
				}
			}
			//Server Side Security End
			
			if(errorsFound)//During Server Side Validation
			{
				saveMessages(request, messages);
				BeanUtils.copyProperties(new IssuesCaptureBean(), captureBean);
				return mapping.findForward("viewIssue");
			}
			else
			{
				captureBean = model.getIssuesForAProject(captureBean);
				captureBean = model.viewIssues(captureBean,null,tmEmployee);
			}
		}
		catch(Exception e)
		{
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
		}
		if (captureBean.getIssueDetailList() == null
				|| captureBean.getIssueDetailList().size() <= 0) {
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.NO_RECORD));

		}
		if (!messages.isEmpty()) {
			this.saveMessages(request, messages);
		}

		forward = mapping.findForward("viewIssue");

		return forward;
	}

	public ActionForward viewOnlyIssues(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ActionForward forward = new ActionForward();
		IssuesCaptureBean captureBean = (IssuesCaptureBean) form;
		IssuesCaptureModel model = new IssuesCaptureModel();
		ActionMessages messages = new ActionMessages();
		TmEmployee tmEmployee = (TmEmployee) request.getSession().getAttribute(
				AppConstants.LOGINBEAN);
		try {
			captureBean = model.viewIssues(captureBean,null,tmEmployee);
		} catch (Exception e) {
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
		}
		if (!messages.isEmpty()) {
			this.saveMessages(request, messages);
		}
		forward = mapping.findForward("viewOnlyIssue");

		return forward;
	}

	public ActionForward searchOnlyIssues(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = new ActionForward();
		IssuesCaptureBean captureBean = (IssuesCaptureBean) form;
		IssuesCaptureModel model = new IssuesCaptureModel();
		ActionMessages messages = new ActionMessages();
		TmEmployee tmEmployee = (TmEmployee) request.getSession().getAttribute(
				AppConstants.LOGINBEAN);
		boolean errorsFound=false;
		try 
		{
			//Server Side Security Start for Project ID
			if((captureBean.getProjectId().equalsIgnoreCase("") || captureBean.getProjectId()==null)
					&& (captureBean.getProjectName().equalsIgnoreCase("") || captureBean.getProjectName()==null)
					&& ((captureBean.getFromDate().equalsIgnoreCase("") || captureBean.getFromDate()==null)
							|| (captureBean.getToDate().equalsIgnoreCase("") || captureBean.getToDate()==null)))
			{
				request.setAttribute("validation_errors1", "Please Select Project ID, Proje Name or From and To Date to Search");
				errorsFound=true;
			}
			else
			{
				if(captureBean.getProjectId()!=null && (!captureBean.getProjectId().equalsIgnoreCase("")))
				{
					if(!errorsFound)
					{
						ArrayList errors=Utility.validateValue(new ValidationBean(captureBean.getProjectId(),"Project ID",5),
								""+Utility.CASE_DIGITS_ONLY+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
						if(errors!=null)
						{
							request.setAttribute("validation_errors", errors);
							errorsFound=true;
						}
					}
				}
				if(captureBean.getProjectName()!=null && (!captureBean.getProjectName().equalsIgnoreCase("")))
				{
					if(!errorsFound)
					{
						ArrayList errors=Utility.validateValue(new ValidationBean(captureBean.getProjectName(),"Project Name",20),
								""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
						if(errors!=null)
						{
							request.setAttribute("validation_errors", errors);
							errorsFound=true;
						}
					}
				}
				if((!captureBean.getFromDate().equalsIgnoreCase("") && captureBean.getFromDate()!=null)
						|| (!captureBean.getToDate().equalsIgnoreCase("") && captureBean.getToDate()!=null))
				{
					if(!errorsFound)
					{
						Object obArray[]={""+ValidationBean.VN_DATE_COMPARISION,captureBean.getFromDate(),"From Date",
								captureBean.getToDate(),"To Date",ValidationBean.LESS_EQUAL,
								new java.text.SimpleDateFormat(Messages.getMessageValue("calendar_entry_format"))};
						ArrayList errors=Utility.validateValue(	new ValidationBean(obArray),
								""+Utility.CASE_DATECOMPARISION_NONMANDATORY).getCompleteMessageStrings();
						if(errors!=null)
						{
							request.setAttribute("validation_errors", errors);
							errorsFound=true;
						}
					}
				}
			}
			//Server Side Security End
			
			if(errorsFound)//During Server Side Validation
			{
				saveMessages(request, messages);
				BeanUtils.copyProperties(new IssuesCaptureBean(), captureBean);
				return mapping.findForward("viewOnlyIssue");
			}
			else
			{
				captureBean = model.getIssuesForAProject(captureBean);
				captureBean = model.viewIssues(captureBean,null,tmEmployee);
			}
		}
		catch(Exception e)
		{
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
		}
		if (captureBean.getIssueDetailList() == null
				|| captureBean.getIssueDetailList().size() <= 0) {
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.NO_RECORD));
	
		}
		if (!messages.isEmpty()) {
			this.saveMessages(request, messages);
		}
	
		forward = mapping.findForward("viewOnlyIssue");
	
		return forward;
	}
}