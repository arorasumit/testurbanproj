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

import com.ibm.ioes.npd.beans.RisksCaptureBean;
import com.ibm.ioes.npd.beans.ValidationBean;
import com.ibm.ioes.npd.hibernate.beans.TmEmployee;
import com.ibm.ioes.npd.model.RisksCaptureModel;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.Messages;
import com.ibm.ioes.npd.utilities.Utility;

/**
 * @author Sanjay
 * 
 */
public class RisksCaptureAction extends LookupDispatchAction {

	protected Map getKeyMethodMap() {

		Map map = new HashMap();
		map.put("link.viewRisks", "viewRisks");
		map.put("submit.save", "addRisks");
		map.put("link.searchRisks", "initsearchRisks");
		map.put("submit.searchRisk", "searchRisks");
		map.put("link.searchOnlyRisk", "viewOnlyRisks");
		map.put("submit.onlysearchRisk","searchOnlyRisks");
		return map;
	}

	public ActionForward viewRisks(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ActionForward forward = new ActionForward();
		RisksCaptureBean risksCaptureBean = (RisksCaptureBean) form;
		RisksCaptureModel risksCaptureModel = new RisksCaptureModel();
		ActionMessages messages = new ActionMessages();
		TmEmployee tmEmployee = (TmEmployee) request.getSession().getAttribute(
				AppConstants.LOGINBEAN);
		try {
			String riskId = request.getParameter("riskId");
			risksCaptureBean = risksCaptureModel.viewRisks(risksCaptureBean,
					riskId, tmEmployee);
		} catch (Exception e) {
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
		}
		if (!messages.isEmpty()) {
			saveErrors(request, messages);
		}

		forward = mapping.findForward("initRisk");

		return forward;
	}

	public ActionForward addRisks(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		ActionForward forward = new ActionForward();
		RisksCaptureBean risksCaptureBean = (RisksCaptureBean) form;
		RisksCaptureModel risksCaptureModel = new RisksCaptureModel();
		ActionMessages messages = new ActionMessages();
		TmEmployee tmEmployee = (TmEmployee) request.getSession().getAttribute(AppConstants.LOGINBEAN);
		boolean insert = true;
		boolean errorsFound=false;
		try 
		{
			//Server Side Security Start for Project ID
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(risksCaptureBean.getProjectId(),"Project ID",30),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			//	Server Side Security Start for Description
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(risksCaptureBean.getDescription(),"Description",30),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_MAXLENGTH+","+Utility.CASE_SPECIALCHARACTERS).getCompleteMessageStrings();
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
				ArrayList errors=Utility.validateValue(new ValidationBean(risksCaptureBean.getStatus(),"Status",30),
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
				ArrayList errors=Utility.validateValue(new ValidationBean(risksCaptureBean.getPriority(),"Priority",30),
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
				ArrayList errors=Utility.validateValue(new ValidationBean(risksCaptureBean.getTimeframe(),"Time Frame",3),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_DIGITS_ONLY+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Mitigation Owner
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(risksCaptureBean.getMitigationOwner(),"Mitigation Owner",2),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Risk Owner
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(risksCaptureBean.getRiskOwner(),"Risk Owner",2),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Probability
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(risksCaptureBean.getProbability(),"Probability",2),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_SPECIALCHARACTERS).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
						
			//Server Side Security Start for Impact
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(risksCaptureBean.getImpact(),"Impact",2),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_SPECIALCHARACTERS).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Mitigation Plan
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(risksCaptureBean.getMitigationPlan(),"Mitigation Plan",1000),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_MAXLENGTH+","+Utility.CASE_SPECIALCHARACTERS).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Impact of Risk
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(risksCaptureBean.getImpactOfRisk(),"Impact of Risk",1000),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_MAXLENGTH+","+Utility.CASE_SPECIALCHARACTERS).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Impact
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(risksCaptureBean.getImpactOfRisk(),"Impact of Risk",1000),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_MAXLENGTH+","+Utility.CASE_SPECIALCHARACTERS).getCompleteMessageStrings();
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
				Object obArray[]={""+ValidationBean.VN_DATE_VALID,risksCaptureBean.getPlannedReslDate(),"Planned Resolution Date",
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
				Object obArray[]={""+ValidationBean.VN_DATE_VALID,risksCaptureBean.getActualReslDate(),"Actual Resolution Date",
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
				Object obArray[]={""+ValidationBean.VN_DATE_COMPARISION,risksCaptureBean.getRaisedon(),"Raised On Date",
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
			
//			Server Side Security Start for Raised By
			if(!errorsFound)
			{
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(AppConstants.DATE_FORMAT_PROC);
				Object obArray[]={""+ValidationBean.VN_DATE_COMPARISION,risksCaptureBean.getRaisedon(),"Raised On Date",
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
			//Server Side Security End for Actual Raised By
			
			if(errorsFound)
			{
				saveMessages(request, messages);
				BeanUtils.copyProperties(new RisksCaptureBean(), risksCaptureBean);
				risksCaptureBean = risksCaptureModel.getRisksForAProject(risksCaptureBean);
				risksCaptureBean = risksCaptureModel.viewRisks(risksCaptureBean, null, tmEmployee);
				forward = mapping.findForward(AppConstants.FAILURE);
				return forward;
			}
			else
			{
				insert = risksCaptureModel.addRisks(risksCaptureBean, tmEmployee);
				if (risksCaptureBean.getRiskID() != null && !risksCaptureBean.getRiskID().equalsIgnoreCase(AppConstants.INI_VALUE)) 
				{
					risksCaptureBean = risksCaptureModel.getRisksForAProject(risksCaptureBean);
					risksCaptureBean = risksCaptureModel.viewRisks(risksCaptureBean, null, tmEmployee);
					forward = mapping.findForward("viewRisk");
				} 
				else 
				{
					risksCaptureBean = risksCaptureModel.viewRisks(risksCaptureBean, null, tmEmployee);
					forward = mapping.findForward("initRisk");
				}	
			}

		} catch (Exception e) 
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
		return forward;
	}

	public ActionForward initsearchRisks(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ActionForward forward = new ActionForward();
		RisksCaptureBean risksCaptureBean = (RisksCaptureBean) form;
		RisksCaptureModel risksCaptureModel = new RisksCaptureModel();
		ActionMessages messages = new ActionMessages();
		TmEmployee tmEmployee = (TmEmployee) request.getSession().getAttribute(
				AppConstants.LOGINBEAN);
		try {
			risksCaptureBean = risksCaptureModel.viewRisks(risksCaptureBean,
					null, tmEmployee);
		} catch (Exception e) {
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
		}
		if (!messages.isEmpty()) {
			this.saveMessages(request, messages);
		}
		forward = mapping.findForward("viewRisk");

		return forward;
	}

	public ActionForward searchRisks(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = new ActionForward();
		RisksCaptureBean risksCaptureBean = (RisksCaptureBean) form;
		RisksCaptureModel risksCaptureModel = new RisksCaptureModel();
		ActionMessages messages = new ActionMessages();
		TmEmployee tmEmployee = (TmEmployee) request.getSession().getAttribute(AppConstants.LOGINBEAN);
		boolean errorsFound=false;
		try 
		{
			//Server Side Security Start for Project ID
			if((risksCaptureBean.getProjectId().equalsIgnoreCase("") || risksCaptureBean.getProjectId()==null)
					&& (risksCaptureBean.getProjectName().equalsIgnoreCase("") || risksCaptureBean.getProjectName()==null)
					&& ((risksCaptureBean.getFromDate().equalsIgnoreCase("") || risksCaptureBean.getFromDate()==null)
							|| (risksCaptureBean.getToDate().equalsIgnoreCase("") || risksCaptureBean.getToDate()==null)))
			{
				request.setAttribute("validation_errors1", "Please Select Project ID, Proje Name or From and To Date to Search");
				errorsFound=true;
			}
			else
			{
				if(risksCaptureBean.getProjectId()!=null && (!risksCaptureBean.getProjectId().equalsIgnoreCase("")))
				{
					if(!errorsFound)
					{
						ArrayList errors=Utility.validateValue(new ValidationBean(risksCaptureBean.getProjectId(),"Project ID",5),
								""+Utility.CASE_DIGITS_ONLY+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
						if(errors!=null)
						{
							request.setAttribute("validation_errors", errors);
							errorsFound=true;
						}
					}
				}
				if(risksCaptureBean.getProjectName()!=null && (!risksCaptureBean.getProjectName().equalsIgnoreCase("")))
				{
					if(!errorsFound)
					{
						ArrayList errors=Utility.validateValue(new ValidationBean(risksCaptureBean.getProjectName(),"Project Name",20),
								""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
						if(errors!=null)
						{
							request.setAttribute("validation_errors", errors);
							errorsFound=true;
						}
					}
				}
				if((!risksCaptureBean.getFromDate().equalsIgnoreCase("") && risksCaptureBean.getFromDate()!=null)
						|| (!risksCaptureBean.getToDate().equalsIgnoreCase("") && risksCaptureBean.getToDate()!=null))
				{
					if(!errorsFound)
					{
						Object obArray[]={""+ValidationBean.VN_DATE_COMPARISION,risksCaptureBean.getFromDate(),"From Date",
								risksCaptureBean.getToDate(),"To Date",ValidationBean.LESS_EQUAL,
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
				BeanUtils.copyProperties(new RisksCaptureBean(), risksCaptureBean);
				return mapping.findForward("viewRisk");
			}
			else
			{
				risksCaptureBean = risksCaptureModel.getRisksForAProject(risksCaptureBean);
				risksCaptureBean = risksCaptureModel.viewRisks(risksCaptureBean,null, tmEmployee);
			}
		} 
		catch (Exception e) 
		{
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		if (risksCaptureBean.getRiskDetailList() == null || risksCaptureBean.getRiskDetailList().size() <= 0) 
		{
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.NO_RECORD));
		}
		if (!messages.isEmpty()) 
		{
			this.saveMessages(request, messages);
		}

		forward = mapping.findForward("viewRisk");

		return forward;
	}

	public ActionForward viewOnlyRisks(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ActionForward forward = new ActionForward();
		RisksCaptureBean risksCaptureBean = (RisksCaptureBean) form;
		RisksCaptureModel risksCaptureModel = new RisksCaptureModel();
		ActionMessages messages = new ActionMessages();
		TmEmployee tmEmployee = (TmEmployee) request.getSession().getAttribute(
				AppConstants.LOGINBEAN);
		try {
			risksCaptureBean = risksCaptureModel.viewRisks(risksCaptureBean,
					null, tmEmployee);
		} catch (Exception e) {
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
		}
		if (!messages.isEmpty()) {
			this.saveMessages(request, messages);
		}
		forward = mapping.findForward("viewOnlyRisk");

		return forward;
	}

	public ActionForward searchOnlyRisks(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		ActionForward forward = new ActionForward();
		RisksCaptureBean risksCaptureBean = (RisksCaptureBean) form;
		RisksCaptureModel risksCaptureModel = new RisksCaptureModel();
		ActionMessages messages = new ActionMessages();
		TmEmployee tmEmployee = (TmEmployee) request.getSession().getAttribute(
				AppConstants.LOGINBEAN);
		boolean errorsFound=false;
		try 
		{
			//Server Side Security Start for Project ID
			if((risksCaptureBean.getProjectId().equalsIgnoreCase("") || risksCaptureBean.getProjectId()==null)
					&& (risksCaptureBean.getProjectName().equalsIgnoreCase("") || risksCaptureBean.getProjectName()==null)
					&& ((risksCaptureBean.getFromDate().equalsIgnoreCase("") || risksCaptureBean.getFromDate()==null)
							|| (risksCaptureBean.getToDate().equalsIgnoreCase("") || risksCaptureBean.getToDate()==null)))
			{
				request.setAttribute("validation_errors1", "Please Select Project ID, Proje Name or From and To Date to Search");
				errorsFound=true;
			}
			else
			{
				if(risksCaptureBean.getProjectId()!=null && (!risksCaptureBean.getProjectId().equalsIgnoreCase("")))
				{
					if(!errorsFound)
					{
						ArrayList errors=Utility.validateValue(new ValidationBean(risksCaptureBean.getProjectId(),"Project ID",5),
								""+Utility.CASE_DIGITS_ONLY+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
						if(errors!=null)
						{
							request.setAttribute("validation_errors", errors);
							errorsFound=true;
						}
					}
				}
				if(risksCaptureBean.getProjectName()!=null && (!risksCaptureBean.getProjectName().equalsIgnoreCase("")))
				{
					if(!errorsFound)
					{
						ArrayList errors=Utility.validateValue(new ValidationBean(risksCaptureBean.getProjectName(),"Project Name",20),
								""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
						if(errors!=null)
						{
							request.setAttribute("validation_errors", errors);
							errorsFound=true;
						}
					}
				}
				if((!risksCaptureBean.getFromDate().equalsIgnoreCase("") && risksCaptureBean.getFromDate()!=null)
						|| (!risksCaptureBean.getToDate().equalsIgnoreCase("") && risksCaptureBean.getToDate()!=null))
				{
					if(!errorsFound)
					{
						Object obArray[]={""+ValidationBean.VN_DATE_COMPARISION,risksCaptureBean.getFromDate(),"From Date",
								risksCaptureBean.getToDate(),"To Date",ValidationBean.LESS_EQUAL,
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
				BeanUtils.copyProperties(new RisksCaptureBean(), risksCaptureBean);
				return mapping.findForward("viewOnlyRisk");
			}
			else
			{
				risksCaptureBean = risksCaptureModel.getRisksForAProject(risksCaptureBean);
				risksCaptureBean = risksCaptureModel.viewRisks(risksCaptureBean,null, tmEmployee);
			}
		} 
		catch (Exception e) 
		{
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
		}
		if (risksCaptureBean.getRiskDetailList() == null
				|| risksCaptureBean.getRiskDetailList().size() <= 0) {
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.NO_RECORD));

		}
		if (!messages.isEmpty()) {
			this.saveMessages(request, messages);
		}

		forward = mapping.findForward("viewOnlyRisk");

		return forward;
	}
}
