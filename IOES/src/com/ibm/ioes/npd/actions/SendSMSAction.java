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
import com.ibm.ioes.npd.beans.SendSmsBean;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.SMSUtil;
import com.ibm.ioes.npd.utilities.SendMail;

public class SendSMSAction extends DispatchAction
{
	public ActionForward SendSMS(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	   {
			ActionForward forward = new ActionForward();
			String forwardMapping = null;
			ActionMessages messages = new ActionMessages();
			SendSmsBean formBean = (SendSmsBean)form;
			SMSUtil sms=new SMSUtil();
			try 
			{
				String txtMsg=formBean.getTxtMessage();
				String MobNumber=formBean.getMobileNumber();
				sms.sendingSMS(MobNumber, txtMsg);
				forwardMapping = "success";
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
	public ActionForward SendEmail(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	   {
			ActionForward forward = new ActionForward();
			String forwardMapping = null;
			ActionMessages messages = new ActionMessages();
			SendSmsBean formBean = (SendSmsBean)form;
			SendMail mail=new SendMail();
			try 
			{
				String txtMsg=formBean.getEmailMessage();
				
				String []to=formBean.getEmailId().split(",");
				
				mail.postMail(to, null, null, "Test Mail", txtMsg, null, null);
				forwardMapping = "success";
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
}
