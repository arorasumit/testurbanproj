package com.ibm.ioes.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import com.ibm.ioes.dao.EscalationDao;
import com.ibm.ioes.escalation.dto.EscalationDto;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.AppUtility;

public class EscalationAction extends DispatchAction{
	
	    public ActionForward goToEscalationMailDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception,ServletException
	     {
	    	ActionForward forward = new ActionForward(); // return value
			ActionMessages messages = new ActionMessages();
			
			try
			{
				
				forward = mapping.findForward("DisplayEsclationMail");
			}
			catch (Exception e) 
			{
				
				AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
				messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
				e.printStackTrace();
			}
			return forward;
	    		    	
	     }
	    
	   
}
	           