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

import com.ibm.appsecure.exception.ValidationException;
import com.ibm.ioes.npd.beans.ChangePwdBean;
import com.ibm.ioes.npd.beans.ValidationBean;
import com.ibm.ioes.npd.hibernate.beans.TmEmployee;
import com.ibm.ioes.npd.model.ChangePwdModel;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.CommonUtilities;
import com.ibm.ioes.npd.utilities.Utility;

public class ChangePwdAction extends DispatchAction
{
	ActionForward forward = new ActionForward();
	String forwardMapping = null;
	public ActionForward showInterface(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		HashMap userHashMap = (HashMap) request.getSession().getAttribute(
	 			AppConstants.NPD_USERS_SESSION_NAME);
		if(userHashMap==null)
		{
			forwardMapping = "SessionExpired";
		}
		else
		{
			forwardMapping = "ShowInterface";
		}
		forward = mapping.findForward(forwardMapping);	
		return forward;
	}
	
	public ActionForward changePassword(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		ChangePwdBean changePasswordbean=(ChangePwdBean) form;
		ChangePwdModel changePasswordModel = new ChangePwdModel();
		ActionMessages messages = new ActionMessages();
		HashMap userHashMap = (HashMap) request.getSession().getAttribute(
	 			AppConstants.NPD_USERS_SESSION_NAME);
		boolean errorsFound=false;
		try
		{
			TmEmployee tmEmployee = (TmEmployee) request.getSession().getAttribute(AppConstants.LOGINBEAN);
			changePasswordbean.setOldpassword(AppUtility.decryptPassword(changePasswordbean.getOldpassword()));
			//			Server Side Security Start
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(changePasswordbean.getOldpassword(),"Old Password",30),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_MAXLENGTH+","+Utility.CASE_SPECIALCHARACTERS).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			changePasswordbean.setNewpassword(AppUtility.decryptPassword(changePasswordbean.getNewpassword()));
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(changePasswordbean.getNewpassword(),"New Password",30),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_MAXLENGTH+","+Utility.CASE_SPECIALCHARACTERS).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			changePasswordbean.setConfirmpassword(AppUtility.decryptPassword(changePasswordbean.getConfirmpassword()));
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(changePasswordbean.getConfirmpassword(),"Confirm Password",30),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_MAXLENGTH+","+Utility.CASE_SPECIALCHARACTERS).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			changePasswordbean.setLoginId(String.valueOf(tmEmployee.getNpdempid()));
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(changePasswordbean.getLoginId(),"Login ID"),
						Utility.CASE_DIGITS_ONLY+"").getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			changePasswordbean.setEmail(tmEmployee.getEmail());
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(changePasswordbean.getEmail(),"Email"),
						Utility.CASE_EMAIL+"").getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			
			if(errorsFound)//During Server Side Validation
			{
				saveMessages(request, messages);
				//forward = mapping.findForward(AppConstants.FAILURE);
				changePasswordbean.setOldpassword(null);
				changePasswordbean.setNewpassword(null);
				changePasswordbean.setConfirmpassword(null);
				changePasswordbean.setEmail(null);
				changePasswordbean.setEmailConfirmation(0);
				return mapping.findForward(AppConstants.FAILURE);
			}
			else
			{
				CommonUtilities cmnUtility=new CommonUtilities();
				if(cmnUtility.passwordCheck(changePasswordbean.getEmail().toLowerCase(), changePasswordbean.getNewpassword().toLowerCase()))
				{
					//Validating Login ID	
					changePasswordModel.validateLoginId(changePasswordbean);
					changePasswordModel.changePassword(changePasswordbean);
					changePasswordbean.setOldpassword(null);
					changePasswordbean.setNewpassword(null);
					changePasswordbean.setConfirmpassword(null);
					changePasswordbean.setEmail(null);
					changePasswordbean.setEmailConfirmation(0);
				}
				else
				{
					changePasswordbean.setOldpassword("");
					changePasswordbean.setNewpassword("");
					changePasswordbean.setConfirmpassword("");
					changePasswordbean.setEmail("");
					changePasswordbean.setEmailConfirmation(0);
					
					messages.add("name", new ActionMessage("ErrorPwdChange"));
					this.saveMessages(request, messages);
					forward = mapping.findForward("ErrorPwdChange");
					//return forward;
				}
			}	
		}
		catch (ValidationException e) 
		{
			changePasswordbean.setOldpassword(null);
			changePasswordbean.setNewpassword(null);
			changePasswordbean.setConfirmpassword(null);
			changePasswordbean.setEmail(null);
			changePasswordbean.setEmailConfirmation(0);
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(e.getMessageId(), new ActionMessage(e.getMessageId()));
		} 
		catch (Exception e) 
		{
			changePasswordbean.setOldpassword(null);
			changePasswordbean.setNewpassword(null);
			changePasswordbean.setConfirmpassword(null);
			changePasswordbean.setEmail(null);
			changePasswordbean.setEmailConfirmation(0);
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			// Report the error using the appropriate name and ID.
			if ("WRONG_PASSWORD".equals(e.getMessage())) {
				messages.add("name", new ActionMessage("wrongpwd"));
			} else if ("EXISTS_PASSWORD".equals(e.getMessage())) {
				messages.add("name", new ActionMessage("changepwd"));
			} else if ("USER_NOT_FOUND".equals(e.getMessage())) {
				messages.add("name", new ActionMessage("invalidusr"));
			} else if ("USER_DISABLED".equals(e.getMessage())) {
				messages.add("name", new ActionMessage("disableusr"));
			} else if ("USER_LOCKED".equals(e.getMessage())) {
				messages.add("name", new ActionMessage("msg.security.id014"));
			}
		}

		if (!messages.isEmpty()) {
			this.saveMessages(request, messages);
			forward = mapping.findForward(AppConstants.FAILURE);

		} 
		else 
		{
			if(userHashMap==null)
			{
				forward = mapping.findForward("SessionExpired");
			}
			else
			{
				messages.add("name", new ActionMessage("pwdchange"));
				this.saveMessages(request, messages);
				forward = mapping.findForward("passwordChanged");
			}
		}

		return forward;
	}
}
