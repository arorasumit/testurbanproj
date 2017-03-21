package com.ibm.ioes.clep;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import com.ibm.ioes.ei.m6.ProcessResponseXML;
import com.ibm.ioes.ei.m6.SendChangeOrderXmlToM6;
import com.ibm.ioes.ei.m6.sendToM6;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.Utility;

public class CLEPQueueAction extends DispatchAction {
	
	public ActionForward SendxmlToCLEP(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		AppConstants.IOES_LOGGER.error("Info -  In CLEPAction ");
		Utility.LOG(true,true,"Manual SendXmlToCLEP job started at : "+new Date());
		ActionMessages messages = new ActionMessages();
		String schedularFlag = request.getParameter("flag");
		
//		run job
		if(schedularFlag.equals("new"))
		{
		  new sendToM6().sendToM6Job();//REMOVE
		}
		else
		{
			new SendChangeOrderXmlToM6().sendToChangeOrderM6Job();//REMOVE
		}
		
		int a=1;
		if(a==2)
		{
			//new ProcessResponseXML().processResponseJob();//REMOVE
		}
		messages.add("",new ActionMessage("customMessage","Done"));
		saveMessages(request, messages);
		Utility.LOG(true,true,"Manual job ended at : "+new Date());
		return mapping.findForward("success");
	}
}
