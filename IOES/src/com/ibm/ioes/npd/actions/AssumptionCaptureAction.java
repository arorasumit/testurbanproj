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

import com.ibm.ioes.npd.beans.AssumptionCaptureBean;
import com.ibm.ioes.npd.beans.IssuesCaptureBean;
import com.ibm.ioes.npd.beans.RisksCaptureBean;
import com.ibm.ioes.npd.beans.ValidationBean;
import com.ibm.ioes.npd.hibernate.beans.TmEmployee;
import com.ibm.ioes.npd.hibernate.dao.CommonBaseDao;
import com.ibm.ioes.npd.model.AssumptionCaptureModel;
import com.ibm.ioes.npd.model.IssuesCaptureModel;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.Messages;
import com.ibm.ioes.npd.utilities.Utility;

/**
 * @author	Rohit Verma
 * @Date	1-FEB-10	
 * @Purpose	Used for Defining Action Class for Assumptions
 */

public class AssumptionCaptureAction extends LookupDispatchAction
{
	protected Map getKeyMethodMap() 
	{
		Map map = new HashMap();
		map.put("link.viewAssumption", "viewAssumption");
		map.put("submit.save","addAssumption");
		map.put("link.initsearchAssumption", "initsearchAssumption");
		map.put("submit.searchAssumpion", "searchAssumption");
		map.put("link.searchOnlyAssumption", "viewOnlyAssumptions");
		map.put("submit.onlysearchAssumption","searchOnlyAssumption");
		return map;
	}
	
	public ActionForward viewAssumption(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
	{
		ActionForward forward = new ActionForward();
		AssumptionCaptureBean captureBean=(AssumptionCaptureBean) form;
		AssumptionCaptureModel model = new AssumptionCaptureModel();
		ActionMessages messages = new ActionMessages();
		TmEmployee tmEmployee = (TmEmployee) request.getSession().getAttribute(
				AppConstants.LOGINBEAN);
		try 
		{
			String assumptionId=request.getParameter("assumptionId");
			captureBean = model.viewAssumption(captureBean,assumptionId,tmEmployee);
		} 
		catch (Exception e) 
		{
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
		}
		if (!messages.isEmpty()) {
			//saveErrors(request, messages);
		}

		forward = mapping.findForward("initAssumption");

		return forward;
	}

	public ActionForward initsearchAssumption(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) 
	{
		ActionForward forward = new ActionForward();
		AssumptionCaptureBean captureBean = (AssumptionCaptureBean) form;
		AssumptionCaptureModel model = new AssumptionCaptureModel();
		ActionMessages messages = new ActionMessages();
		TmEmployee tmEmployee = (TmEmployee) request.getSession().getAttribute(
				AppConstants.LOGINBEAN);
		//boolean insert = true;
		try 
		{
			captureBean = model.viewAssumption(captureBean,null,tmEmployee);
		} 
		catch (Exception e) 
		{
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		/*if (insert) {
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.RECORD_SAVE_SUCCESS));

		} else {
			messages.add(AppConstants.RECORD_SAVE_FAILURE, new ActionMessage(
					AppConstants.RECORD_SAVE_FAILURE));
		}*/
		if (!messages.isEmpty()) 
		{
			this.saveMessages(request, messages);
		}
		forward = mapping.findForward("viewAssumption");

		return forward;
	}

	public ActionForward addAssumption(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) 
	{
		ActionForward forward = new ActionForward();
		AssumptionCaptureBean captureBean = (AssumptionCaptureBean) form;
		AssumptionCaptureModel model = new AssumptionCaptureModel();
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
			
			//Server Side Security Start for Project ID
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(captureBean.getImpact(),"Impact",30),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
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
			
			if(errorsFound)
			{
				saveMessages(request, messages);
				BeanUtils.copyProperties(new AssumptionCaptureBean(), captureBean);
				captureBean = model.getAssumptionForAProject(captureBean);
				captureBean = model.viewAssumption(captureBean, null, tmEmployee);
				forward = mapping.findForward(AppConstants.FAILURE);
				return forward;
			}
			else
			{
				insert = model.addAssumption(captureBean, tmEmployee);
				if(captureBean.getAssumptionID()!= null && !captureBean.getAssumptionID().equalsIgnoreCase(AppConstants.INI_VALUE))
				{
					captureBean = model.getAssumptionForAProject(captureBean);
					captureBean = model.viewAssumption(captureBean,null,tmEmployee);
					forward = mapping.findForward("viewAssumption");
				}
				else
				{
					captureBean = model.viewAssumption(captureBean,null,tmEmployee);
					forward = mapping.findForward("initAssumption");
				}
			}
		} 
		catch (Exception e) 
		{
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

		//forward = mapping.findForward("initAssumption");
		//forward = mapping.findForward("viewAssumption");
		return forward;
	}
	
	public ActionForward searchAssumption(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) 
	{
		ActionForward forward = new ActionForward();
		AssumptionCaptureBean captureBean = (AssumptionCaptureBean) form;
		AssumptionCaptureModel model = new AssumptionCaptureModel();
		ActionMessages messages = new ActionMessages();
		TmEmployee tmEmployee = (TmEmployee) request.getSession().getAttribute(AppConstants.LOGINBEAN);
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
				BeanUtils.copyProperties(new AssumptionCaptureBean(), captureBean);
				return mapping.findForward("viewAssumption");
			}
			else
			{
				captureBean = model.getAssumptionForAProject(captureBean);
				captureBean = model.viewAssumption(captureBean,null,tmEmployee);
			}
		}
		catch(Exception e)
		{
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		if (captureBean.getAssumptionDetailList() == null
				|| captureBean.getAssumptionDetailList().size() <= 0) {
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.NO_RECORD));

		}
		if (!messages.isEmpty()) {
			this.saveMessages(request, messages);
		}

		forward = mapping.findForward("viewAssumption");

		return forward;
	}

	public ActionForward viewOnlyAssumptions(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) 
	{
		ActionForward forward = new ActionForward();
		AssumptionCaptureBean captureBean = (AssumptionCaptureBean) form;
		AssumptionCaptureModel model = new AssumptionCaptureModel();
		ActionMessages messages = new ActionMessages();
		TmEmployee tmEmployee = (TmEmployee) request.getSession().getAttribute(
				AppConstants.LOGINBEAN);
		try 
		{
			captureBean = model.viewAssumption(captureBean,null,tmEmployee);
		} 
		catch (Exception e) 
		{
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		if (!messages.isEmpty()) 
		{
			this.saveMessages(request, messages);
		}
		forward = mapping.findForward("viewOnlyAssumptions");

		return forward;
	}

	
	public ActionForward searchOnlyAssumption(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) 
	{
		ActionForward forward = new ActionForward();
		AssumptionCaptureBean captureBean = (AssumptionCaptureBean) form;
		AssumptionCaptureModel model = new AssumptionCaptureModel();
		ActionMessages messages = new ActionMessages();
		TmEmployee tmEmployee = (TmEmployee) request.getSession().getAttribute(AppConstants.LOGINBEAN);
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
				BeanUtils.copyProperties(new AssumptionCaptureBean(), captureBean);
				return mapping.findForward("viewOnlyAssumptions");
			}
			else
			{
				captureBean = model.getAssumptionForAProject(captureBean);
				captureBean = model.viewAssumption(captureBean,null,tmEmployee);
			}
		}
		catch(Exception e)
		{
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		if (captureBean.getAssumptionDetailList() == null
				|| captureBean.getAssumptionDetailList().size() <= 0) {
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.NO_RECORD));

		}
		if (!messages.isEmpty()) {
			this.saveMessages(request, messages);
		}

		forward = mapping.findForward("viewOnlyAssumptions");

		return forward;
	}
}
