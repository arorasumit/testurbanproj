package com.ibm.ioes.actions;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import com.csgsystems.bp.data.AccountXIDObjectData;
import com.csgsystems.om.data.NrcObjectBaseKeyData;
import com.csgsystems.om.data.NrcObjectData;
import com.csgsystems.om.data.ProductObjectBaseKeyData;
import com.csgsystems.om.data.ProductObjectData;
import com.csgsystems.om.session.NrcBean;
import com.csgsystems.om.session.ProductBean;
import com.ibm.fx.dto.ExtendedData;
import com.ibm.fx.mq.CreateAccount;
import com.ibm.fx.mq.FX_AUTOLINES_BILLINGTRIGGER;
import com.ibm.fx.mq.FX_AUTOLINES_BILLINGTRIGGER.AutoType;
import com.ibm.fx.mq.FxSchedulerForCumulative;
import com.ibm.fx.mq.FxSchedulerForGAM;
import com.ibm.fx.mq.FxSchedulerTasks;
import com.ibm.fx.mq.FxSchedulerTasksForComponent;
import com.ibm.fx.mq.FxSchedulerTasksForPackage;
import com.ibm.ioes.model.NewOrderModel;
import com.ibm.fx.mq.FxSchedulerTasksforCharges;
import com.ibm.fx.mq.IOESKenanManager;
import com.ibm.ioes.dao.ViewOrderDao;
import com.ibm.fx.mq.PushLocDataToFx;
import com.ibm.ioes.dao.ViewOrderDao;
import com.ibm.ioes.model.NewOrderModel;
import com.ibm.ioes.schedular.BCP_Address_Change;
import com.ibm.ioes.schedular.BulkUpload;
import com.ibm.ioes.schedular.ComponentDisconnectionSchedular;
import com.ibm.ioes.schedular.DisconnectionSchedular;
import com.ibm.ioes.schedular.LepmLocDataUpdate;
import com.ibm.ioes.schedular.ServiceUpdateSchedular;
import com.ibm.ioes.schedular.Service_DisconnectionSchedular;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.CustomerAlertService;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.MailForDisConnectProvisioning;
import com.ibm.ioes.utilities.MailForProvisioning;
import com.ibm.ioes.utilities.Utility;
import com.ibm.fx.mq.ChargeRedirectionSchedular;
import com.ibm.ioes.schedular.AccountUpdateSchedular;
public class FxSchedulerAction extends DispatchAction{
	
	private static int fx_login_flag = 0; 
	
	// service update
	public ActionForward FXSchedulerForServiceUpdate(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		AppConstants.IOES_LOGGER.error("Info ");
		Utility.LOG(true,true,"Manual FXScheduler job started at : "+new Date());
		ActionMessages messages = new ActionMessages();
		long orderno=Long.parseLong(request.getParameter("serviceupdateorderno"));
//		run job
		new ServiceUpdateSchedular().pushUpdatedServicesToFX(orderno);
		messages.add("",new ActionMessage("customMessage","Done"));
		saveMessages(request, messages);
		Utility.LOG(true,true,"Manual job ended at : "+new Date());
		return mapping.findForward("success");
		
	}
	public ActionForward FXScheduler(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		AppConstants.IOES_LOGGER.error("Info ");
		Utility.LOG(true,true,"Manual FXScheduler job started at : "+new Date());
		ActionMessages messages = new ActionMessages();
		long orderno=Long.parseLong(request.getParameter("servicecreationorderno"));
//		run job
		new FxSchedulerTasks().pushServicesToFX(orderno);
		messages.add("",new ActionMessage("customMessage","Done"));
		saveMessages(request, messages);
		Utility.LOG(true,true,"Manual job ended at : "+new Date());
		return mapping.findForward("success");
		
	}
	//push locdata tyo fx
	
	public ActionForward pushLocDataToFx(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		AppConstants.IOES_LOGGER.error("Info ");
		Utility.LOG(true,true,"Manual FXScheduler job started at : "+new Date());
		ActionMessages messages = new ActionMessages();
		
//		run job
		new PushLocDataToFx().PushLocDataIntoFx();
		messages.add("",new ActionMessage("customMessage","Done"));
		saveMessages(request, messages);
		Utility.LOG(true,true,"Manual job ended at : "+new Date());
		return mapping.findForward("pushLocDataToFx");
		
	}
	
	// 001 for Auto Billing start
	public ActionForward FXSchedulerforAutoBilling(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		AppConstants.IOES_LOGGER.error("Info ");
		Utility.LOG(true,true," FXScheduler Auto Billing at : "+new Date());
		ActionMessages messages = new ActionMessages();
		
//		run job
		
		saveMessages(request, messages);
		
		String option = request.getParameter("autoOption");
		if("ALL".equals(option)){
			Thread t1=new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						new FX_AUTOLINES_BILLINGTRIGGER().autoBillingProcessForRest();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			});
			t1.start();
			
			
			Thread t2=new Thread(new Runnable() {
				
				@Override
				public void run() {
					new FX_AUTOLINES_BILLINGTRIGGER().autoBillingProcessForLocLater();
					
				}
			});
			t2.start();
			
			t1.join();
			t2.join();
			
				
		}else if("PRE".equals(option)){
			//Step1. Track of LOC Receive From LEPM
				new LepmLocDataUpdate().processLepmLocData();	
			/*	new FX_AUTOLINES_BILLINGTRIGGER().trackLocReceivedForDisconnectionOfParallelUpgrade();	*/
				new FX_AUTOLINES_BILLINGTRIGGER().lockAndEnableForActionForLocReceivedLines();	
		}else if("BT".equals(option)){
			new FX_AUTOLINES_BILLINGTRIGGER().billingTriggerRestCases();
			new FX_AUTOLINES_BILLINGTRIGGER().billingTriggerLocLaterCases();
		}else if("LOC_UPDATE".equals(option)){
			new FX_AUTOLINES_BILLINGTRIGGER().updateLocAtBtScreenForLocLaterCases();
		}else if("LEPM_DATA_IMPORT".equals(option)){
			new LepmLocDataUpdate().updateLepmLocData();
		}else if("LEPM_LOC_NOT_RECEIVED".equals(option)){
			new FX_AUTOLINES_BILLINGTRIGGER().updateStatusLocNotReceivedCases();
		}
		
		Utility.LOG(true,true,"FXScheduler Auto Billing  ended at : "+new Date());
		return mapping.findForward("autoBilling");
	}
	
//	 001 for Auto Billing end

	public ActionForward FXSchedulerforRCCharges(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		AppConstants.IOES_LOGGER.error("Info ");
		Utility.LOG(true,true,"Manual FXScheduler charges job started at : "+new Date());
		ActionMessages messages = new ActionMessages();
		
//		run job
		
		saveMessages(request, messages);
		long orderno=Long.parseLong(request.getParameter("pushrcorderno"));
		new FxSchedulerTasksforCharges().pushRCChargesToFX(orderno);
		Utility.LOG(true,true,"Manual job ended at : "+new Date());
		return mapping.findForward("RCChargessuccess");
	}
	
	public ActionForward FXSchedulerforNRCCharges(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		AppConstants.IOES_LOGGER.error("Info ");
		Utility.LOG(true,true,"Manual FXScheduler charges job started at : "+new Date());
		ActionMessages messages = new ActionMessages();
		
//		run job
		
		saveMessages(request, messages);
		long orderno=Long.parseLong(request.getParameter("pushnrcorderno"));
		new FxSchedulerTasksforCharges().pushNRCChargesToFX(orderno);
		Utility.LOG(true,true,"Manual job ended at : "+new Date());
		return mapping.findForward("NRCChargessuccess");
	}
	public ActionForward FXSchedulerforDisconnection(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		AppConstants.IOES_LOGGER.error("Info ");
		Utility.LOG(true,true,"Manual FXScheduler charges job started at : "+new Date());
		ActionMessages messages = new ActionMessages();
		
//		run job
		
		saveMessages(request, messages);
		long orderno=Long.parseLong(request.getParameter("chargedisorderno"));
		new DisconnectionSchedular().run(orderno);
		Utility.LOG(true,true,"Manual job ended at : "+new Date());
		return mapping.findForward("success");
	}
	
	public ActionForward FXSchedulerforServiceDisconnection(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		AppConstants.IOES_LOGGER.error("Info ");
		Utility.LOG(true,true,"Manual FXSchedulerforServiceDisconnection job started at : "+new Date());
		ActionMessages messages = new ActionMessages();
		
//		run job
		
		saveMessages(request, messages);
		long orderno=Long.parseLong(request.getParameter("servicedisorderno"));
		new Service_DisconnectionSchedular().process_service_for_disconnection(orderno);
		Utility.LOG(true,true,"Manual job ended at : "+new Date());
		return mapping.findForward("success");
	}
	
	public ActionForward FXSchedulerforAddressChange(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		AppConstants.IOES_LOGGER.error("Info ");
		Utility.LOG(true,true,"Manual FXScheduler charges job started at : "+new Date());
		ActionMessages messages = new ActionMessages();
		
//		run job
		
		saveMessages(request, messages);
		
		new BCP_Address_Change().run();
		Utility.LOG(true,true,"Manual job ended at : "+new Date());
		return mapping.findForward("success");
	}
	
	public ActionForward setBTEnd(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		AppConstants.IOES_LOGGER.error("Info ");
		Utility.LOG(true,true,"Manual setBTEndjob started at : "+new Date());
		ActionMessages messages = new ActionMessages();
		
//		run job
		//new ViewOrderDao().setBTEndIfPossible(Long.parseLong(request.getParameter("orderno")),null);
		saveMessages(request, messages);
		
		
		Utility.LOG(true,true,"Manual job ended at : "+new Date());
		return mapping.findForward("success");
	}
	
	public ActionForward findAccount(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		
		IOESKenanManager API = null;
		AppConstants.IOES_LOGGER.error("Info ");
		Utility.LOG(true,true,"Manual FXScheduler charges job started at : "+new Date());
		ActionMessages messages = new ActionMessages();
		
//		run job
		
		saveMessages(request, messages);
		Utility.LOG(true,true,"Manual job ended at : "+new Date());
		 

			try {
				System.out.println("1");
					if(fx_login_flag == 0)/*if already not loged in*/ {
						System.out.println("2");
						API	=	new IOESKenanManager();
						System.out.println("3");
						API.loginKenan();
						System.out.println("4");
						fx_login_flag = 1; /* set flag to 1 for first time login*/
					}
					System.err.println(request.getParameter("findaccount"));
					AccountXIDObjectData aod = API.accountFind(request.getParameter("findaccount").toString());
					if(aod!=null)
					{
						System.err.println("Acount found and its  intrernalid" +aod.getAccountInternalId());
						request.setAttribute("accountno", aod.getAccountInternalId()
																+"\n\t Property : Value"
																+"\n\t Market Code : "+aod.getMktCode()
																+"\n\t No Bill : "+aod.getNoBill()
																	);
					}
					else
					{
						request.setAttribute("accountno", "Account Not Found");
					}
					
					
				
			}catch(Exception e) {
				
				e.printStackTrace();
				request.setAttribute("accountno",Utility.getStackTrace(e));
			}finally {
				
				fx_login_flag = 0;
				API.close();
				
				
				
			}
		
		
		
		return mapping.findForward("success");
	}
	public ActionForward sync(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		IOESKenanManager API = null;
		try
		{
			API	=	new IOESKenanManager();
			API.loginKenan();
			syncData(API);	
		}finally{
			API.close();
		}
		return mapping.findForward("success");
	}
	
	class ExtData{
		String objectType=null;
		String objectId=null;
		String paramId=null;
		String paramValue=null;
		String dataType=null;
		String id=null;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getDataType() {
			return dataType;
		}
		public void setDataType(String dataType) {
			this.dataType = dataType;
		}
		public String getObjectId() {
			return objectId;
		}
		public void setObjectId(String objectId) {
			this.objectId = objectId;
		}
		public String getObjectType() {
			return objectType;
		}
		public void setObjectType(String objectType) {
			this.objectType = objectType;
		}
		public String getParamId() {
			return paramId;
		}
		public void setParamId(String paramId) {
			this.paramId = paramId;
		}
		public String getParamValue() {
			return paramValue;
		}
		public void setParamValue(String paramValue) {
			this.paramValue = paramValue;
		}
	}
	public void syncData(IOESKenanManager api) {
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		try {
			conn = DbConnection.getConnectionObject();
			stmt=conn.createStatement();
			rs=stmt.executeQuery("select * from IOE.TFX_SYNC where STATUS in (1,4)");
			ArrayList<ExtData> arrayList=new ArrayList<ExtData>();
			while(rs.next())
			{
				String id=null;
					id=rs.getString("ID");
					String objectType=rs.getString("OBJECT_TYPE");
					String objectId=rs.getString("OBJECT_ID");
					String paramId=rs.getString("PARAM_ID");
					String paramValue=rs.getString("PARAM_VALUE");
					String dataType=rs.getString("DATA_TYPE");
					
					ExtData data=new ExtData();
					
					data.setDataType(dataType);
					data.setObjectId(objectId);
					data.setObjectType(objectType);
					data.setParamId(paramId);
					data.setParamValue(paramValue);
					data.setId(id);
					arrayList.add(data);
			}
			rs.close();
			stmt.close();
					
			for (ExtData data : arrayList) {
				String id=null;
				try
				{
					id=data.getId();
					String objectType=data.getObjectType();
					String objectId=data.getObjectId();
					String paramId=data.getParamId();
					String paramValue=data.getParamValue();
					String dataType=data.getDataType();
					
					if("ACCOUNT".equals(objectType))
					{
						
					}else if("SERVICE".equals(objectType))
					{
						
					}else if("RC".equals(objectType))
					{
						ProductBean productbean = new ProductBean(api.getSettings());
						ProductObjectBaseKeyData key = new ProductObjectBaseKeyData();
						ProductObjectData objectdata = new  ProductObjectData();
						
						Map extendedData = new HashMap();
						
						extendedData.put(Integer.parseInt(paramId), Utility.getFXExtendedDataObject(dataType,paramValue));
						
						key.setViewId(new BigInteger(objectId));			
						objectdata.setKey(key);
						objectdata.extendedData = extendedData;
						productbean.update(api.getContext(),
												 objectdata
												);
						
					}else if("NRC".equals(objectType))
					{
						NrcBean nrcbean = new NrcBean(api.getSettings());
						NrcObjectBaseKeyData key = new NrcObjectBaseKeyData();
						NrcObjectData objectdata = new  NrcObjectData();
						
						Map extendedData = new HashMap();
						
						extendedData.put(Integer.parseInt(paramId), Utility.getFXExtendedDataObject(dataType,paramValue));
						
						key.setViewId(new BigInteger(objectId));			
						objectdata.setKey(key);
						objectdata.extendedData = extendedData;
						nrcbean.update(api.getContext(),
								 objectdata
								);
					}else if("ORDER".equals(objectType))
					{
						
					}else{
						
					}
						
					//update success
					Statement stmt2=conn.createStatement();
					stmt2.executeUpdate("update IOE.TFX_SYNC set STATUS=3 where ID="+id);
					stmt2.close();
				
				}
				catch(Exception ex)
				{
//					update failure
					ex.printStackTrace();
					Statement stmt2=conn.createStatement();
					stmt2.executeUpdate("update IOE.TFX_SYNC set STATUS=4 where ID="+id);
					stmt2.close();
				}
				
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	public ActionForward FXSchedulerForAccountCreate(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		AppConstants.IOES_LOGGER.error("Info ");
		Utility.LOG(true,true,"Manual FXScheduler Account job started at : "+new Date());
		ActionMessages messages = new ActionMessages();
		Connection con =null;
		try {
		con=DbConnection.getConnectionObject();
		long orderno=Long.parseLong(request.getParameter("accountcreationorderno"));
		new CreateAccount().createAccount(con,orderno);
		messages.add("",new ActionMessage("customMessage","Done"));
		saveMessages(request, messages);
		Utility.LOG(true,true,"Manual  Account job ended at : "+new Date());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			DbConnection.freeConnection(con);
		}
		return mapping.findForward("success");
		
	}
	public static void main(String[] args) {
		IOESKenanManager API = null;
		AppConstants.IOES_LOGGER.error("Info ");
		Utility.LOG(true,true,"Manual FXScheduler charges job started at : "+new Date());
		ActionMessages messages = new ActionMessages();
		
//		run job
		
		//saveMessages(request, messages);
		Utility.LOG(true,true,"Manual job ended at : "+new Date());
		 String findaccount="19309094";

			try {
					//if(fx_login_flag == 0)/*if already not loged in*/ {
						
						API	=	new IOESKenanManager();
						API.loginKenan();
					//	fx_login_flag = 1; /* set flag to 1 for first time login*/
					//}
						Utility.LOG(findaccount);
					AccountXIDObjectData aod = API.accountFind(findaccount);
					if(aod!=null)
					{
						Utility.LOG("Acount found and its  intrernalid" +aod.getAccountInternalId());
						/*request.setAttribute("accountno", aod.getAccountInternalId()
																+"\n\t Property : Value"
																+"\n\t Market Code : "+aod.getMktCode()
																+"\n\t No Bill : "+aod.getNoBill()
																	);*/
					}
					else
					{
						//request.setAttribute("accountno", "Account Not Found");
					}
					
					
					API.close();
			}catch(Exception e) {
				Utility.LOG(true,true,e,"");
				e.printStackTrace();
				//request.setAttribute("accountno",Utility.getStackTrace(e));
			}finally {
				//fx_login_flag = 0;
				try {
					API.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					Utility.LOG(true,true,e,"");
					e.printStackTrace();
				}
			}
		
		
		
		//return mapping.findForward("success");

		
	}
	public ActionForward FXSchedulerForPackages(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		AppConstants.IOES_LOGGER.error("Info ");
		Utility.LOG(true,true,"Manual FXSchedulerForPackages job started at : "+new Date());
		ActionMessages messages = new ActionMessages();
		
//		run job

		// FxSchedulerTasksForPkg_Component().pushServicesToFX();

		//new FxSchedulerTasksForPkg_Component().pushServicesToFX();

		messages.add("",new ActionMessage("customMessage","Done"));
		saveMessages(request, messages);
		long orderno=Long.parseLong(request.getParameter("packcreationorderno"));
		new FxSchedulerTasksForPackage().pushPackagesToFX(orderno);
		Utility.LOG(true,true,"Manual FXSchedulerForPackages job ended at : "+new Date());
		return mapping.findForward("success");
		
	}

	public ActionForward FXSchedulerforComponents(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		AppConstants.IOES_LOGGER.error("Info ");
		Utility.LOG(true,true,"Manual FXSchedulerforComponents job started at : "+new Date());
		ActionMessages messages = new ActionMessages();
		
//		run job
		long orderno=Long.parseLong(request.getParameter("compcreationorderno"));
		new FxSchedulerTasksForComponent().pushComponentToFX(orderno);
		saveMessages(request, messages);
		Utility.LOG(true,true,"Manual FXSchedulerforComponents job ended at : "+new Date());
		
		return mapping.findForward("success");
	}
	
	public ActionForward SchedularForGamToFX(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		AppConstants.IOES_LOGGER.error("Info ");
		Utility.LOG(true,true,"Manual FXScheduler:SchedularForGamToFX job started at : "+new Date());
		ActionMessages messages = new ActionMessages();
		
//		run job
		new FxSchedulerForGAM().pushGAMsToFX();
		messages.add("",new ActionMessage("customMessage","Done"));
		saveMessages(request, messages);
		Utility.LOG(true,true,"Manual job:SchedularForGamToFX ended at : "+new Date());
		return mapping.findForward("success");
		
	}
	
	public ActionForward account_modify(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		AppConstants.IOES_LOGGER.error("Info ");
		Utility.LOG(true,true,"Manual account_modify started at : "+new Date());
		String account_modify_message= null;
		
		IOESKenanManager API = null;
		
//		run job
		try {
			API	=	new IOESKenanManager();
			API.loginKenan();

			int retuenStatus = API.AccountModifyForTestPage(request.getParameter("account_for_modify").toString());
			if(retuenStatus==1)
			{
				account_modify_message="Modify Success . Confirm with Find account test utility";
			}
			else
			{
				account_modify_message="Modify Failure";
			}
			}catch(Exception e) {
				
				e.printStackTrace();
				account_modify_message="Exception-"+Utility.getStackTrace(e);
			}finally {
				API.close();
			}
		request.setAttribute("account_modify_message", account_modify_message);
		
		Utility.LOG(true,true,"Manual account_modify ended at : "+new Date());
		return mapping.findForward("success");
	}
	
//	--sandeep
	public ActionForward FXSchedulerForComponentDisconnection(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		AppConstants.IOES_LOGGER.error("Info ");
		Utility.LOG(true,true,"Manual FXScheduler job started at : "+new Date());
		ActionMessages messages = new ActionMessages();
		long orderno=Long.parseLong(request.getParameter("comdiscorderno"));
//		run job
		new ComponentDisconnectionSchedular().process_component_for_disconnection(orderno);
		messages.add("",new ActionMessage("customMessage","Done"));
		saveMessages(request, messages);
		Utility.LOG(true,true,"Manual job ended at : "+new Date());
		return mapping.findForward("success");
		
	}
	
//	 001 for Auto Billing start
	public ActionForward SchedularForAutoRenewalMail(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		AppConstants.IOES_LOGGER.error("Info ");
		Utility.LOG(true,true," FXScheduler Auto Renewal Mail at : "+new Date());
		ActionMessages messages = new ActionMessages();
		
//		run job
		
		saveMessages(request, messages);
		
		new NewOrderModel().sendAutoRenewalMail();
		Utility.LOG(true,true,"FXScheduler Auto Renewal Mail  ended at : "+new Date());
		return mapping.findForward("autoRenewalMail");
	}
	
//	 001 for Auto Billing end

	public ActionForward FXSchedulerForArborUpdate(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		AppConstants.IOES_LOGGER.error("Info ");
		Utility.LOG(true,true,"FXScheduler Arbor job started at : "+new Date());
		ActionMessages messages = new ActionMessages();
		saveMessages(request, messages);
		new ViewOrderDao().schedulerForArborUpdate();
		Utility.LOG(true,true,"FXScheduler Arbor job ended at : "+new Date());
		return mapping.findForward("success");
	}
	public ActionForward FXSchedulerForACSProvisioningMail(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		AppConstants.IOES_LOGGER.error("Info ");
		Utility.LOG(true,true,"FXScheduler For ACS Provisioning Mail job started at : "+new Date());
		ActionMessages messages = new ActionMessages();
		saveMessages(request, messages);
		new MailForProvisioning().sendACSProvisiongMail();
		new MailForProvisioning().sendMultipleProvisiongMail("PROVISIONING_MAIL_OVCC");
		new MailForProvisioning().sendMultipleProvisiongMail("PROVISIONING_MAIL_VCS");

		new MailForDisConnectProvisioning().sendDisACSProvisiongMail();
		new MailForDisConnectProvisioning().sendDisVCSProvisiongMail("VCS_DISCONNECTION_MAIL");
		//end raveendra
		Utility.LOG(true,true,"FXScheduler For ACS Provisioning Mail job ended at : "+new Date());
		return mapping.findForward("success");
		
		
	
		
	}
	
	public ActionForward FXSchedulerForVCSProvisioningMail(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		
	
		
		//lawkush Start VCS mail code 
		AppConstants.IOES_LOGGER.error("Info ");
		Utility.LOG(true,true,"FXScheduler For VCS Provisioning Mail job started at : "+new Date());
		ActionMessages messages = new ActionMessages();
		saveMessages(request, messages);
		new MailForProvisioning().sendMultipleProvisiongMail("PROVISIONING_MAIL_VCS");
		new MailForProvisioning().sendMultipleProvisiongMail("PROVISIONING_MAIL_OVCC");
		new MailForProvisioning().sendACSProvisiongMail();
		Utility.LOG(true,true,"FXScheduler For VCS Provisioning Mail job ended at : "+new Date());
		return mapping.findForward("success");
		//lawkush End VCS mail code End
		
		
	}
	public ActionForward FXSchedulerForAccountUpdate(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		AppConstants.IOES_LOGGER.error("Info ");
		Utility.LOG(true,true,"Manual FXSchedulerForAccountUpdate  job started at : "+new Date());
		ActionMessages messages = new ActionMessages();
		long orderno=Long.parseLong(request.getParameter("accountupdateorderno"));
//		run job
		if(orderno!=999){
			new AccountUpdateSchedular().pushUpdatedAccountsToFX(orderno);
		}else{
			new com.ibm.fx.mq.FXSchedulerForAccountUpdate().updateAccount();
		}
		
		messages.add("",new ActionMessage("customMessage","Done"));
		saveMessages(request, messages);
		Utility.LOG(true,true,"Manual FXSchedulerForAccountUpdate job ended at : "+new Date());
		return mapping.findForward("success");
		
	}

	public ActionForward FXSchedulerForChargeRedirection(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		AppConstants.IOES_LOGGER.error("Info ");
		Utility.LOG(true,true,"Manual FXScheduler job started at : "+new Date());
		ActionMessages messages = new ActionMessages();
		
//		run job
		new ChargeRedirectionSchedular().redirectCharges();
		messages.add("",new ActionMessage("customMessage","Done"));
		saveMessages(request, messages);
		Utility.LOG(true,true,"Manual job ended at : "+new Date());
		return mapping.findForward("success");
		
	}
	public ActionForward FXSchedulerForCumulative(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		AppConstants.IOES_LOGGER.error("Info ");
		Utility.LOG(true,true,"Manual FXScheduler job started at : "+new Date());
		ActionMessages messages = new ActionMessages();
		
//		run job
		long orderno=Long.parseLong(request.getParameter("cumulativeorderno"));
		new FxSchedulerForCumulative().pushCumulativeToFX(orderno);
		messages.add("",new ActionMessage("customMessage","Done"));
		saveMessages(request, messages);
		Utility.LOG(true,true,"Manual job ended at : "+new Date());
		return mapping.findForward("success");
		
	}
	public ActionForward BulkUpload(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		AppConstants.IOES_LOGGER.error("Info ");
		Utility.LOG(true,true,"Manual BulkUpload job started at : "+new Date());
		ActionMessages messages = new ActionMessages();
		
//		run job
		
		saveMessages(request, messages);
		long bulkId=Long.parseLong(request.getParameter("bulkId"));
		//new Service_DisconnectionSchedular().process_service_for_disconnection(orderno);
		BulkUpload bu=new BulkUpload();
		// 1 for Suspension,2 for resump,3 for PD , 4 for BT
		if(bulkId==1){
			bu.processFileSuspension();
		}else if(bulkId==2){
			bu.processFilesResumption();
		}else if(bulkId==3){
			bu.processFilePermanentDisconnection();
		}else if(bulkId==4){
			bu.processFileBillingTrigger();
		}else if(bulkId==5){
			bu.processFilesRaterenewal();
		}else if(bulkId==6){
		bu.processFiles_forChargeRedirection();
		}else if(bulkId==7){
			BulkUpload.processBulkCurrencyTrasnferJob();
		}else if(bulkId==8){
			BulkUpload.processBulkSITrasnferJob();
		}else if(bulkId==9){
			Utility.LOG(true,true,"***Manual LSI_DIS_STATUS_SCHEDULER job started at : "+new Date());
			new CustomerAlertService().processLsiDisconnectionStatusUpdation();
			Utility.LOG(true,true,"***Manual LSI_DIS_STATUS_SCHEDULER job ended at : "+new Date());
		}else if(bulkId==10){
			Utility.LOG(true,true,"***Manual OB_SCHEDULER job started at : "+new Date());
			BulkUpload.processBulkSITrasnferJob();
			Utility.LOG(true,true,"***Manual OB_SCHEDULER job ended at : "+new Date());
		}
		else{
			System.err.println("in all");
			bu.processFileSuspension();
			bu.processFilesResumption();
			bu.processFilePermanentDisconnection();
			bu.processFileBillingTrigger();
			bu.processFilesRaterenewal();
			bu.processFiles_forChargeRedirection();
			BulkUpload.processBulkSITrasnferJob();
			BulkUpload.processBulkCurrencyTrasnferJob();
			BulkUpload.processBulkSITrasnferJob();
		}
		//bu.processFilePermanentDisconnection();
		//bu.processFileSuspension();
		//bu.processFilesResumption();
		
		
		
		Utility.LOG(true,true,"Manual job ended at : "+new Date());
		return mapping.findForward("success");
	}
}
