package com.ibm.ioes.actions;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import com.csgsystems.bp.data.AccountXIDObjectData;
import com.ibm.fx.mq.FX_AUTOLINES_BILLINGTRIGGER;
import com.ibm.fx.mq.FxSchedulerTasks;
import com.ibm.fx.mq.PushLocDataToFx;
import com.ibm.fx.mq.FxSchedulerTasksforCharges;
import com.ibm.fx.mq.IOESKenanManager;
import com.ibm.ioes.schedular.BCP_Address_Change;
import com.ibm.ioes.schedular.DisconnectionSchedular;
import com.ibm.ioes.schedular.Service_DisconnectionSchedular;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.ApplicationFlags;
import com.ibm.ioes.utilities.TestConnection;
import com.ibm.ioes.utilities.Utility;


public class ApplicationUtilityAction extends DispatchAction{
	
	
	
	public ActionForward terminateAllDbConnection(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		AppConstants.IOES_LOGGER.error("Info ");
		Utility.LOG(true,true,"Manual terminateAllDbConnection job started at : "+new Date());
		ActionMessages messages = new ActionMessages();
		
//		run job
		TestConnection.terminateAllActiveConnections(Long.parseLong(request.getParameter("minutes"))*60*1000);
		
		messages.add("",new ActionMessage("customMessage","Done"));
		saveMessages(request, messages);
		Utility.LOG(true,true,"Manual terminateAllDbConnection job ended at : "+new Date());
		return mapping.findForward("success");
		
	}
	public ActionForward displayAllDbConnection(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		AppConstants.IOES_LOGGER.error("Info ");
		Utility.LOG(true,true,"Manual displayAllDbConnection job started at : "+new Date());
		ActionMessages messages = new ActionMessages();
		
//		run job
		TestConnection.printAllActiveConnection(Long.parseLong(request.getParameter("minutes"))*60*1000);
		
		messages.add("",new ActionMessage("customMessage","Done"));
		saveMessages(request, messages);
		Utility.LOG(true,true,"Manual displayAllDbConnection job ended at : "+new Date());
		return mapping.findForward("success");
		
	}	
	public ActionForward change_IB2B_DB_CONNECTION_LOGGER_FLAG(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		AppConstants.IOES_LOGGER.error("Info ");
		Utility.LOG(true,true,"Manual change_IB2B_DB_CONNECTION_LOGGER_FLAG job started at : "+new Date());
		ActionMessages messages = new ActionMessages();
		
//		run job
		ApplicationFlags.IB2B_DB_CONNECTION_LOGGER_FLAG=(String)request.getParameter("IB2B_DB_CONNECTION_LOGGER_FLAG_NEW_VALUE");
		
		messages.add("",new ActionMessage("customMessage","Done"));
		saveMessages(request, messages);
		Utility.LOG(true,true,"Manual change_IB2B_DB_CONNECTION_LOGGER_FLAG job ended at : "+new Date());
		return mapping.findForward("success");
		
	}	
	
	public ActionForward change_IB2B_FX_TEST_ACCOUNT_CREATION(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		AppConstants.IOES_LOGGER.error("Info ");
		Utility.LOG(true,true,"Manual change_IB2B_FX_TEST_ACCOUNT_CREATION job started at : "+new Date());
		ActionMessages messages = new ActionMessages();
		
//		run job
		ApplicationFlags.IB2B_FX_TEST_ACCOUNT_CREATION=(String)request.getParameter("IB2B_FX_TEST_ACCOUNT_CREATION_NEW_VALUE");
		
		messages.add("",new ActionMessage("customMessage","Done"));
		saveMessages(request, messages);
		Utility.LOG(true,true,"Manual change_IB2B_FX_TEST_ACCOUNT_CREATION job ended at : "+new Date());
		return mapping.findForward("success");
		
	}	
	public ActionForward change_IB2B_FX_DB_PASSWORD_CREATION(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		AppConstants.IOES_LOGGER.error("Info ");
		Utility.LOG(true,true,"Manual change_IB2B_FX_DB_PASSWORD_CREATION job started at : "+new Date());
		ActionMessages messages = new ActionMessages();
		
//		run job
		//ApplicationFlags.IB2B_FX_TEST_ACCOUNT_CREATION=(String)request.getParameter("IB2B_FX_TEST_ACCOUNT_CREATION_NEW_VALUE");
		String textToEncrypt=(String)request.getParameter("IB2B_FX_DB_PASSWORD_CREATION_NEW_VALUE");
		System.out.println("Text to encrypt  :"+textToEncrypt);
		messages.add("",new ActionMessage("customMessage","Done"));
		saveMessages(request, messages);
		Utility.LOG(true,true,"Manual change_IB2B_FX_DB_PASSWORD_CREATION job ended at : "+new Date());
		return mapping.findForward("success");
		
	}	
	public ActionForward change_ReportsExcelMaxSize(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		AppConstants.IOES_LOGGER.error("Info ");
		Utility.LOG(true,true,"Manual change_ReportsExcelMaxSize job started at : "+new Date());
		ActionMessages messages = new ActionMessages();
		
		ApplicationFlags.ReportsExcelMaxSize=Integer.parseInt(request.getParameter("ReportsExcelMaxSize"));
		
		messages.add("",new ActionMessage("customMessage","Done"));
		saveMessages(request, messages);
		Utility.LOG(true,true,"Manual change_ReportsExcelMaxSize job ended at : "+new Date());
		return mapping.findForward("success");
		
	}	
}
