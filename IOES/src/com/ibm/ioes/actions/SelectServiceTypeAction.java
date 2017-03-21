package com.ibm.ioes.actions;

import java.util.ArrayList;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import com.ibm.ioes.beans.NewOrderBean;
import com.ibm.ioes.forms.NewOrderDto;
import com.ibm.ioes.model.NewOrderModel;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.AppUtility;

/**
 * Action Class for managing Login functionality.
 * 
 * @author Disha
 * @version 1.0
 */
public class SelectServiceTypeAction extends DispatchAction {

	private static final Logger IOES_LOGIN;

	static {
		IOES_LOGIN = Logger.getLogger(SelectServiceTypeAction.class);
	}

	@SuppressWarnings("unchecked")
	public ActionForward fetchServiceType(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		NewOrderBean formBean= (NewOrderBean)form;
		NewOrderDto objDto=new NewOrderDto();
		NewOrderModel objModel=new NewOrderModel();
		ActionMessages messages = new ActionMessages();
		ArrayList<NewOrderDto> listServicetype = new ArrayList<NewOrderDto>();
		Hashtable htValue = new Hashtable();
		try
		{
			//htValue = objModel.fetchServiceType();
			//listServicetype = (ArrayList<NewOrderDto>) htValue.get(AppConstants.SERVICE_TYPE);
			//request.setAttribute("serviceType", listServicetype);
			forward = mapping.findForward("SUCCESS");
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		return forward;
	}
	
	public ActionForward goToviewServiceAttribute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		forward = mapping.findForward("goToViewServiceAttribute");
		return forward;
	}
	
	public ActionForward goToServiceAttribute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		forward = mapping.findForward("goToServiceAttribute");
		return forward;
	}
	//Ceated By Saurabh Singh For Partial Publish LookUp on 25-March-2011
	public ActionForward goToPartialPublish(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		forward = mapping.findForward("goToPartialPublish");
		return forward;
	}
	
}