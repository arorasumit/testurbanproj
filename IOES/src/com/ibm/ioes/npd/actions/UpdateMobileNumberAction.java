package com.ibm.ioes.npd.actions;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import com.ibm.ioes.npd.beans.UpdateMobileNumberBean;
import com.ibm.ioes.npd.beans.ValidationBean;
import com.ibm.ioes.npd.hibernate.beans.TmEmployee;
import com.ibm.ioes.npd.model.UpdateMobileNumberModel;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.Utility;

public class UpdateMobileNumberAction extends DispatchAction
{
	ActionForward forward = new ActionForward();
	String forwardMapping = null;
	
	public ActionForward showInterface(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		UpdateMobileNumberBean bean=(UpdateMobileNumberBean)form;
		UpdateMobileNumberModel model = new UpdateMobileNumberModel();
		ActionMessages messages = new ActionMessages();
		HashMap userHashMap = (HashMap) request.getSession().getAttribute(AppConstants.NPD_USERS_SESSION_NAME);
		String fetchMobileNumber=null;
		try
		{
			if(userHashMap==null)
			{
				forwardMapping = "SessionExpired";
			}
			else
			{
				TmEmployee tmEmployee = (TmEmployee) request.getSession().getAttribute(AppConstants.LOGINBEAN);
				fetchMobileNumber=model.fetchMobileNumber(tmEmployee,bean);
				bean.setUserMobileNumber(fetchMobileNumber);
				forwardMapping = "success";
			}
		}
		catch (Exception e) 
		{
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		if (!messages.isEmpty()) 
		{
			saveErrors(request, messages);
		}
		forward = mapping.findForward(forwardMapping);
		
		return forward;
	}
	
	public ActionForward updateMobileNumber(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		UpdateMobileNumberBean bean=(UpdateMobileNumberBean)form;
		UpdateMobileNumberModel model = new UpdateMobileNumberModel();
		ActionMessages messages = new ActionMessages();
		HashMap userHashMap = (HashMap) request.getSession().getAttribute(AppConstants.NPD_USERS_SESSION_NAME);
		boolean errorsFound=false;
		boolean insert = true;
		String mobileNumber=null;
		try
		{
			if(userHashMap==null)
			{
				forwardMapping = "SessionExpired";
			}
			else
			{
				TmEmployee tmEmployee = (TmEmployee) request.getSession().getAttribute(AppConstants.LOGINBEAN);
				mobileNumber=bean.getUserMobileNumber();
				
				//Server Side Security Start for Mobile Number
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(mobileNumber,"Mobile Number",10),
							""+Utility.CASE_MANDATORY+","+Utility.CASE_MAXLENGTH+","+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;
					}
				}
				//Server Side Security End
				
				//Server Side Security Start for Employee ID
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(String.valueOf(tmEmployee.getNpdempid()),"Employee ID",10),
							""+Utility.CASE_MANDATORY+","+Utility.CASE_MAXLENGTH+","+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
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
					bean.setUserMobileNumber(mobileNumber);
					return mapping.findForward("success");
				}
				else
				{
					insert =model.updateMobileNumer(mobileNumber, String.valueOf(tmEmployee.getNpdempid()));
					bean.setUserMobileNumber(mobileNumber);
					forwardMapping="success";
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

		forward = mapping.findForward(forwardMapping);

		return forward;

		
	}
}
