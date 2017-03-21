package com.ibm.ioes.npd.actions;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import com.ibm.ioes.npd.beans.ResetPasswordBean;
import com.ibm.ioes.npd.beans.ValidationBean;
import com.ibm.ioes.npd.model.ResetPasswordModel;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.Utility;



public class ResetPasswordAction extends DispatchAction
{
	public ActionForward resetPassword(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
			throws Exception 
	{
		ActionMessages messages = new ActionMessages();
		ActionForward forward = new ActionForward();
		boolean errorsFound=false;
		try 
		{
			ResetPasswordBean resetPasswordBean = (ResetPasswordBean) form;
			ResetPasswordModel resetPasswordModel = new ResetPasswordModel();
			
			
			//Server Side Security Start
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(resetPasswordBean.getEmailId(),"User ID",30),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_MAXLENGTH+","+Utility.CASE_EMAIL+","+Utility.CASE_SPECIALCHARACTERS).getCompleteMessageStrings();
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
				resetPasswordBean.setEmailId(null);
				return mapping.findForward(AppConstants.FAILURE);
			}
			else
			{
				resetPasswordModel.updateAndResetPassword(resetPasswordBean.getEmailId());
				resetPasswordBean.setEmailId(null);
			}

		} catch (Exception e) {
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
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
			} else if ("SEND_MAIL_FAILED".equals(e.getMessage())) {
				messages.add("name", new ActionMessage("sendmailfailed"));
			} else {
				messages.add("passwordResetError", new ActionMessage(
						"passwordResetError"));
			}
			this.saveMessages(request, messages);
		}

		if (!messages.isEmpty()) {
			AppConstants.NPDLOGGER
					.error("Errors : " + messages.toString());
			this.saveMessages(request, messages);
			forward = mapping.findForward(AppConstants.FAILURE);
		} else {
					
			messages.add("name", new ActionMessage("resetPwdEmailSent"));
			this.saveMessages(request, messages);
			forward = mapping.findForward("success");
		}
		return forward;
		
	}
}
