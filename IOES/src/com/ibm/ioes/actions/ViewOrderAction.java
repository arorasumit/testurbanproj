//Tag Name Resource Name  Date		CSR No			Description
//[001]	 Manisha Garg	22-jun-11	00-05422		Billing Trigger for New Order
//[002]	 Manisha Garg	22-jun-11	00-05422		Billing Trigger for Change Order
//[003]	 Saurabh Singh	11-Jan-12	00-05422		Billing Trigger enable disable based on role
//[15032013013] Vijay     12-Feb-13                   For disable the billing trigger button when copc login, because for billing trigger a separated role woule be created.
////[210]Anoop Tiwari   28 -feb-14  O2C drop 2      for disabling partial initiator Role for clep Order
package com.ibm.ioes.actions;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.StringTokenizer;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.util.LabelValueBean;

import com.ibm.fx.mq.CreateAccount;
import com.ibm.ioes.beans.NewOrderBean;
import com.ibm.ioes.beans.UserInfoDto;
import com.ibm.ioes.beans.ViewOrderDto;
import com.ibm.ioes.dao.NewOrderDaoExt;
import com.ibm.ioes.dao.ViewOrderDao;
import com.ibm.ioes.forms.ContactDTO;
import com.ibm.ioes.forms.FieldAttibuteDTO;
import com.ibm.ioes.forms.NewOrderDto;
import com.ibm.ioes.forms.OrderHeaderDTO;
import com.ibm.ioes.forms.PoDetailsDTO;
import com.ibm.ioes.forms.ViewOrderFormBean;
import com.ibm.ioes.model.NewOrderModel;
import com.ibm.ioes.model.ViewOrderModel;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.ApplicationFlags;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.PagingSorting;
import com.ibm.ioes.utilities.Utility;

public class ViewOrderAction extends DispatchAction{

	private static final Logger logger;
	static {
		logger = Logger.getLogger(UniversalWorkQueueAction.class);
	}
	//Main method for viewing the Task List of particular order from Universal Work Queue List.
	 public ActionForward viewOrder(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) 
	 {
		logger.info("View Order Interface and viewOrder method have been called");
		String forwardMapping = null;
		NewOrderDto objDto=new NewOrderDto();
		ViewOrderModel objViewOrderModel = new ViewOrderModel();
		ArrayList<OrderHeaderDTO> listAccountDetails = null;
		ArrayList<FieldAttibuteDTO> listMainDetailsWithAttributes = null;
		ArrayList<ContactDTO> listContactDetails= null;
		ArrayList<NewOrderDto> listAddressDetails= new ArrayList<NewOrderDto>();
		ArrayList<PoDetailsDTO> listPODetails= null;
		ArrayList<NewOrderDto> listRegion= new ArrayList<NewOrderDto>();		
		ViewOrderFormBean objForm=(ViewOrderFormBean)form;
		NewOrderBean newOrderForm=new NewOrderBean();
		NewOrderModel objModel=new NewOrderModel();
		long orderNo=0;
			ArrayList lstTo = new ArrayList();
		try 
		{
				lstTo.add(new LabelValueBean("Account Manager","1"));
				lstTo.add(new LabelValueBean("Project Manager","2"));
				lstTo.add(new LabelValueBean("CPOC","3"));
			orderNo=Long.parseLong(request.getParameter("orderNo"));
			listAccountDetails=objModel.getAccountDetails(objDto,orderNo);
			listRegion=objViewOrderModel.getRegionList();
			request.setAttribute("accountDetailsBean", listAccountDetails);
			
			newOrderForm.setProjectManager(listAccountDetails.get(0).getProjectManager());
			request.setAttribute("PMBean", newOrderForm);
			
			listMainDetailsWithAttributes=objModel.getMainDetails(objDto,orderNo);
			request.setAttribute("MainDetailsWithAttributesBean", listMainDetailsWithAttributes);
			
			listContactDetails=objModel.getContactDetails(orderNo);
			listAddressDetails=objModel.getAddressDetails(orderNo);
			request.setAttribute("listContactDetails", listContactDetails);
			request.setAttribute("listAddressDetails", listAddressDetails);
			listPODetails=objModel.getPODetails(orderNo);
			request.setAttribute("listPoDetails", listPODetails);
			objForm.setOrderNo(String.valueOf(orderNo));
			request.setAttribute("listRegion", listRegion);
			
			newOrderForm.setProjectManager(listAccountDetails.get(0).getProjectManager());
			//Commented by Anil (It was used for Edit Mode)
			//newOrderForm.setRegionId(String.valueOf((listAccountDetails.get(0).getRegionId())));
			//newOrderForm.setZoneId(String.valueOf((listAccountDetails.get(0).getZoneId())));
			
			request.setAttribute("PMBean", newOrderForm);
			
			
			
			
			
			//Check Whether the order has been published or not and ready for billing trigger or not
			String strOrderPublishBillingTrigger=null;
			HttpSession session = request.getSession();	
			UserInfoDto objUserDto  = (UserInfoDto)session.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			long roleId=Long.parseLong(objUserDto.getUserRoleId());
			strOrderPublishBillingTrigger=objViewOrderModel.fnIsOrderPublishedBillingTrigger(orderNo,roleId);
			
			String[] strArr=new String[1];
			strArr=strOrderPublishBillingTrigger.split("@@");
			objForm.setIsOrderPublished(strArr[0]);
			//objForm.setIsBillingTriggerReady(strArr[1]);
			if(AppConstants.ORDER_STAGE_PARTIAL_INITIATED.equalsIgnoreCase(listAccountDetails.get(0).getStageName())){
				String btRoleId = new NewOrderDaoExt().getRoleID(AppConstants.BT_SHORT_CODE);
				if(null != btRoleId && !"".equals(btRoleId.trim()) && roleId == Long.valueOf(btRoleId)){
					objForm.setIsBillingTriggerReady(strArr[1]);
				}
			}else{
				objForm.setIsBillingTriggerReady(strArr[1]);
			}
			
			
		ArrayList aList=objViewOrderModel.getTaskListOfOrder(orderNo);
				ArrayList aListHistory=objViewOrderModel.getTaskListHistory(orderNo);
				request.setAttribute("taskListOfOrder", aList);
				request.setAttribute("taskListHistoryOfOrder", aListHistory);
				request.setAttribute("lstTo", lstTo);
			forwardMapping ="ViewOrderPage";
		}
		catch(Exception ex)
		{
   		logger.error(ex.getMessage()+ " Exception occured in viewOrder method of "+ this.getClass().getSimpleName());
			forwardMapping="ErrorPageAction";
			return mapping.findForward(forwardMapping);
		}
		return mapping.findForward(forwardMapping);
	 }
	 public ActionForward fetchNotes(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) 
	 {
		 	logger.info("View Order Interface and viewOrder method have been called");
		 	String forwardMapping = null;
		
			ViewOrderFormBean formbean = (ViewOrderFormBean) form;
			SimpleDateFormat sdf  = new SimpleDateFormat("dd/MM/yyyy");
			Date date = new Date();
			try 
			{
				HttpSession session = request.getSession();	
				UserInfoDto objUserDto  = (UserInfoDto)session.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				String employeeId=objUserDto.getEmployeeId();
				formbean.setCreatedBy(employeeId);
				formbean.setCreatedDate(sdf.format(date));
				
				forwardMapping ="ViewNotes";
			}
			catch(Exception ex)
			{
	   		logger.error(ex.getMessage()+ " Exception occured in viewOrder method of "+ this.getClass().getSimpleName());
				forwardMapping="ErrorPageAction";
				return mapping.findForward(forwardMapping);
			}
		return mapping.findForward(forwardMapping);
	 }
	 
	 
	 public ActionForward ViewDisconnectionDatePage(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) 
	 {
		 	logger.info("View Order Interface and viewOrder method have been called");
		 	String forwardMapping = null;
		
		
			ViewOrderModel objViewOrderModel = new ViewOrderModel();
			ViewOrderFormBean formbean = (ViewOrderFormBean) form;
			long taskId=0;
			
			
			try{
				forwardMapping ="ViewDisconnectionPage";
			}
			catch(Exception ex)
			{
	   		logger.error(ex.getMessage()+ " Exception occured in viewOrder method of "+ this.getClass().getSimpleName());
				forwardMapping="ErrorPageAction";
				return mapping.findForward(forwardMapping);
			}
		return mapping.findForward(forwardMapping);
	 }
	 
	 public ActionForward TaskAction(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response){
		 	logger.info("View Order Interface and viewOrder method have been called");
		 	String forwardMapping = null;
		 	String partialInitiatorRole=null;
			ViewOrderModel objViewOrderModel = new ViewOrderModel();
			String orderNo=request.getParameter("orderNo");
			String role = request.getParameter("role");
			String delayReasonRole = objViewOrderModel.getDelayReasonUsers();
			//[210] start
			if(!objViewOrderModel.isClepOrder(new Long(orderNo)))
				partialInitiatorRole = objViewOrderModel.getPartialInitiatorRoles();
			//[210] end
			Boolean rolePassedEscalationFlag = false;
			boolean rolePartialInitiator = false;
			ArrayList aList=objViewOrderModel.getTaskListOfOrder(Long.valueOf(orderNo));
			String owner = new Utility().getOrderOwnedBy(aList);
			request.setAttribute("ORDEROWNER", owner);
			if(null == delayReasonRole){
				rolePassedEscalationFlag = true;
			}else{
				for(String delayReasonRoleVal : delayReasonRole.split("_")){
					if(null != delayReasonRoleVal && delayReasonRoleVal.trim().equals(role.trim()) && role.equalsIgnoreCase(owner)){
						rolePassedEscalationFlag = true;
						break;
					}
				}
			}
			if(null == partialInitiatorRole){
				rolePartialInitiator = false;
			}else{
				for(String partInitRoleVal : partialInitiatorRole.split("_")){
					if(null != partInitRoleVal && partInitRoleVal.trim().equals(role.trim())){
						rolePartialInitiator = true;
						break;
					}
				}
			}
			if(rolePassedEscalationFlag)
				request.setAttribute("ROLEPASSEDESCALATION", objViewOrderModel.isRolePassedEscalation(orderNo, role));
			else
				request.setAttribute("ROLEPASSEDESCALATION", false);
			if(rolePartialInitiator)
				request.setAttribute("ROLEPARTIALINITIATOR", true);
			else
				request.setAttribute("ROLEPARTIALINITIATOR", false);
			
			try{
				forwardMapping ="ViewAction";
			}catch(Exception ex){
				logger.error(ex.getMessage()+ " Exception occured in viewOrder method of "+ this.getClass().getSimpleName());
				forwardMapping="ErrorPageAction";
				return mapping.findForward(forwardMapping);
			}
		return mapping.findForward(forwardMapping);
	 }
	 
	 public ActionForward ChangeOrderTaskAction(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response){
		 	logger.info("View Order Interface and viewOrder method have been called");
		 	String forwardMapping = null;
		 	String partialInitiatorRole =null;
		 	ViewOrderModel objViewOrderModel = new ViewOrderModel();
			String orderNo=request.getParameter("orderNo");
			String role = request.getParameter("role");
			String delayReasonRole = objViewOrderModel.getDelayReasonUsers();
			Boolean rolePassedEscalationFlag = false;
			boolean rolePartialInitiator = false;
			if(!objViewOrderModel.isClepOrder(new Long(orderNo)))
				partialInitiatorRole = objViewOrderModel.getPartialInitiatorRoles();
			if(null == partialInitiatorRole){
				rolePartialInitiator = false;
			}else{
				for(String partInitRoleVal : partialInitiatorRole.split("_")){
					if(null != partInitRoleVal && partInitRoleVal.trim().equals(role.trim())){
						rolePartialInitiator = true;
						break;
					}
				}
			}
			ArrayList aList=objViewOrderModel.getTaskListOfOrder(Long.valueOf(orderNo));
			String owner = new Utility().getOrderOwnedBy(aList);
			request.setAttribute("ORDEROWNER", owner);
			if(null == delayReasonRole){
				rolePassedEscalationFlag = true;
			}else{
				for(String delayReasonRoleVal : delayReasonRole.split("_")){
					if(null != delayReasonRoleVal && delayReasonRoleVal.trim().equals(role.trim())&& role.equalsIgnoreCase(owner)){
						rolePassedEscalationFlag = true;
						break;
					}
				}
			}
			if(rolePassedEscalationFlag)
				request.setAttribute("ROLEPASSEDESCALATION", objViewOrderModel.isRolePassedEscalation(orderNo, role));
			else
				request.setAttribute("ROLEPASSEDESCALATION", false);
			if(rolePartialInitiator)
				request.setAttribute("ROLEPARTIALINITIATOR", true);
			else
				request.setAttribute("ROLEPARTIALINITIATOR", false);
			try{
				forwardMapping ="ViewChangeOrderAction";
			}catch(Exception ex){
				logger.error(ex.getMessage()+ " Exception occured in viewOrder method of "+ this.getClass().getSimpleName());
				forwardMapping="ErrorPageAction";
				return mapping.findForward(forwardMapping);
			}
			return mapping.findForward(forwardMapping);
	 }
	 
	 
	 public ActionForward fnBillingTrigger(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) 
	 {
	 		return  fnBillingTriggerInfx(mapping, form, request, response);
		 	
		 	/*logger.info("Billing Trigger Interface of View Order Action and fnBillingTrigger method have been called");
		 	String forwardMapping = null;
		
		 	System.out.println("This is Billing Trigger Action");	
//		 	001 start 
		 	ViewOrderFormBean objFormbean = (ViewOrderFormBean) form;//001 end
			ViewOrderModel objViewOrderModel = new ViewOrderModel();
			ViewOrderDto objDto = new ViewOrderDto();
			ArrayList<ViewOrderDto> selectServiceDetailsList=new ArrayList();
			//001 Start 
			ArrayList LSIResult=new ArrayList(); 
			ArrayList custLSIResult=new ArrayList(); //001 end
			long orderNo=0;
			
		
			try 
			{
				
				orderNo=Long.parseLong(request.getParameter("orderNo"));
				//001 start
				String searchLogicalSI =  objFormbean.getSearchLSI();
				String searchCustomerLSI=objFormbean.getSearchCustomerLSI();
				objDto.setSearchLSI(searchLogicalSI);
				objDto.setCustLogicalSino(searchCustomerLSI);
				objDto.setPonum(orderNo);
				ViewOrderDto selectServiceProductDetailsList=objViewOrderModel.getSELECT_BT_PRODUCTS_NEWORDER(objDto);
				selectServiceDetailsList=selectServiceProductDetailsList.getProductList();
				LSIResult=selectServiceProductDetailsList.getLSIS();
				custLSIResult=selectServiceProductDetailsList.getCusLSIResult();
//				001 end
				String allAccountsCreated="true";
				for (ViewOrderDto dto : selectServiceDetailsList) {
					if("".equals(dto.getFxAccNo()))
					{
						allAccountsCreated="false";
					}
				}
				ViewOrderDto dto=new ViewOrderDto();
				dto.setAllAccountsCreated(allAccountsCreated);
				
				//ArrayList selectChargeDetailsList=objViewOrderModel.getSelectChargeDetails(orderNo);
				//001 start
				request.setAttribute("selectServiceDetailsList", selectServiceDetailsList);
				request.setAttribute("LSIResult", LSIResult);
				request.setAttribute("custLSIResult", custLSIResult);
				request.setAttribute("orderData", dto);
                   //001 end
				request.setAttribute("IsUsageType", selectServiceDetailsList);
				request.setAttribute("orderNo", String.valueOf(orderNo));
				objFormbean.setBillingOrderNo(String.valueOf(orderNo));
				forwardMapping ="ViewBillingTrigger";
			}
			catch(Exception ex)
			{
	   		logger.error(ex.getMessage()+ " Exception occured in fnBillingTrigger method of "+ this.getClass().getSimpleName());
				forwardMapping="ErrorPageAction";
				return mapping.findForward(forwardMapping);
			}
		return mapping.findForward(forwardMapping);*/
	 }
	 

	 /**
	  * f0LLOWING FUNCTION ADD BY MANISHA FOR CHANGE ORDER BILLING TRRIGER
	    Change Order Type : connection
	  */
	 public ActionForward fnBillingTriggerInfx(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) 
	 {
		 	logger.info("Billing Trigger Interface of View Order Action and fnBillingTrigger method have been called");
 		 	String forwardMapping = null;
		
 			Utility.SysOut("This is Billing Trigger Action");	
			 //002 start
			ViewOrderFormBean objFormbean = (ViewOrderFormBean) form;
			ViewOrderModel objViewOrderModel = new ViewOrderModel();
			ArrayList<ViewOrderDto> selectServiceDetailsList=new ArrayList();
			ArrayList LSIResult=new ArrayList();
			ArrayList custLSIResult=new ArrayList(); 
			//ArrayList<ViewOrderDto> eventids=new ArrayList();
			//ArrayList servicetypes=new ArrayList();
			 //002 end
			long orderNo=0;
			String mode;
		    Connection conn=null;
			ViewOrderDto dto=new ViewOrderDto();
			int billingTriggerEnableFlag=0;
			try 
			{
				conn = DbConnection.getConnectionObject(); 
				ViewOrderDao objViewOrderDao =  new ViewOrderDao();
				
				//[003]start
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				/*
				 * [15032013013] --start//
				 * enable billing trigger functionality only for 'billing trigger role'.
				 */ 
				//if(objUserDto.getUserRoleId().equalsIgnoreCase("3"))
				if(objUserDto.getUserRoleId().equalsIgnoreCase("51184"))
			//--[15032013013] -- end//		
				{
					billingTriggerEnableFlag=1;
				}
				else
				{
					billingTriggerEnableFlag=0;
				}
				request.setAttribute("billingTriggerEnableFlag", billingTriggerEnableFlag);
				//[003]End
				//002 start
				orderNo=Long.parseLong(request.getParameter("orderNo"));
				mode=request.getParameter("mode");
				String searchLogicalSI =  objFormbean.getSearchLSI();
				String searchCustomerLSI=objFormbean.getSearchCustomerLSI();
				dto.setSearchLSI(searchLogicalSI);
				dto.setCustLogicalSino(searchCustomerLSI);
				dto.setPonum(orderNo);
				dto.setMode(mode);
				dto.setSearchLineTriggerStatus(objFormbean.getSearchLineTriggerStatus());
				PagingSorting pagingSorting =  objFormbean.getPagingSorting();
				if(pagingSorting==null)
				{
					pagingSorting=new PagingSorting(true,false,objUserDto.getPageSizeBTLines());
				}else
				{
					pagingSorting.setPagingSorting(true, false);
					pagingSorting.setPageRecords(objUserDto.getPageSizeBTLines());
				}
				pagingSorting.setDefaultifNotPresent(null, null, 1);
				pagingSorting.sync();
				dto.setPagingSorting(pagingSorting);
				 //002 end
				OrderHeaderDTO orderInfo =objViewOrderDao.getOrderType(conn,orderNo);
				String orderType = orderInfo.getOrderInfo_OrderType();
				Integer changeType=orderInfo.getOrderInfo_ChangeType();
				dto.setOrderInfo(orderInfo);
					 
					 if(dto.ORDER_TYPE_NEW.equalsIgnoreCase(orderType) ||
							 (dto.ORDER_TYPE_CHANGE.equalsIgnoreCase(orderType) 
								 &&
							 		(dto.CHANGE_TYPE_RATERENEWAL==changeType 
					 				|| dto.CHANGE_TYPE_SOLUTION_CHANGE==changeType 
					 				|| dto.CHANGE_TYPE_NETWORK_CHANGE==changeType 
					 				|| dto.CHANGE_TYPE_DISCONNECTION==changeType 
					 				|| dto.CHANGE_TYPE_DEMO==changeType)))
					 {
							ViewOrderDto selectServiceProductDetailsList=objViewOrderModel.getSELECT_BT_PRODUCTS_FOR_CHANGE_ORDERS(dto);
							selectServiceDetailsList=selectServiceProductDetailsList.getProductList();
							LSIResult=selectServiceProductDetailsList.getLSIS();
							custLSIResult=selectServiceProductDetailsList.getCusLSIResult();
							
						
							
				         String allAccountsCreated="true";
				         request.setAttribute("success", dto.getSuccess());
				         for (ViewOrderDto dto1 : selectServiceDetailsList) {
							if("".equals(dto1.getFxAccNoStatus()))
							{
								allAccountsCreated="false";
							}
						}
				
				        dto.setAllAccountsCreated(allAccountsCreated);
				   	 //002 start
				        request.setAttribute("selectServiceDetailsList", selectServiceDetailsList);
						request.setAttribute("LSIResult", LSIResult);
						request.setAttribute("custLSIResult", custLSIResult);
						//request.setAttribute("eventids",eventids );
						
						 //002 end
						request.setAttribute("orderData", dto);
						request.setAttribute("ORDER_INFO", orderInfo);
						//request.setAttribute("selectChargeDetailsList", selectChargeDetailsList);
						objFormbean.setBillingOrderNo(String.valueOf(orderNo));
						forwardMapping ="ViewBillingTriggerForChangeOrders";
					 }
					 
					 /*else
					 if(dto.CHANGE_TYPE_DISCONNECTION==changeType)
					 {
						 ArrayList<ViewOrderDto> selectServiceDetailsList=objViewOrderModel.getSELECT_BT_PRODUCTS_DISCONNECTION(orderNo);
					
					     String allAccountsCreated="true";
							     for (ViewOrderDto dto1 : selectServiceDetailsList) {
										if("".equals(dto1.getFxAccNoStatus()))
										{
											allAccountsCreated="false";
										}
									}
				
							 dto.setAllAccountsCreated(allAccountsCreated);
						
						//ArrayList selectChargeDetailsList=objViewOrderModel.getSelectChargeDetails(orderNo);
						
											request.setAttribute("selectServiceDetailsList", selectServiceDetailsList);
											request.setAttribute("orderData", dto);
											//request.setAttribute("selectChargeDetailsList", selectChargeDetailsList);
											objFormbean.setBillingOrderNo(String.valueOf(orderNo));
											forwardMapping ="ViewBillingTriggerInFx";
					}*/
					 
						
	 
						 
					 
			}			 
			catch(Exception ex)
			{
	   		logger.error(ex.getMessage()+ " Exception occured in fnBillingTrigger method of "+ this.getClass().getSimpleName());
				forwardMapping="ErrorPageAction";
				return mapping.findForward(forwardMapping);
			}
			finally
			{
				try {
					DbConnection.freeConnection(conn);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		return mapping.findForward(forwardMapping);
	 }
	 

	 
	
	public ActionForward fnChargeSummary(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) 
	 {
		 	logger.info("Charge Summary Interface of View Order Action and fnChargeSummary method have been called");
		 	String forwardMapping = null;
		
			Utility.SysOut("This is Charge Summary Action");	
		 	ViewOrderFormBean formBean =(ViewOrderFormBean)form;
			ViewOrderModel objViewOrderModel = new ViewOrderModel();
			long orderNo=0;
			orderNo=Long.parseLong(request.getParameter("PONum"));
			try 
			{
				objViewOrderModel.setChargeSummaryData(formBean,orderNo);
				//request.setAttribute("chargeSummaryList", aList);
				forwardMapping ="ViewChargeSummary";
			}
			catch(Exception ex)
			{
	   		logger.error(ex.getMessage()+ " Exception occured in fnChargeSummary method of "+ this.getClass().getSimpleName());
				forwardMapping="ErrorPageAction";
				return mapping.findForward(forwardMapping);
			}
		return mapping.findForward(forwardMapping);
	 }
	 
	 public ActionForward fnPublishOrder(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
	 			throws Exception
	 {
		 	logger.info("Publish Order Interface of View Order Action and fnPublishOrder method have been called");
		 	Date var_start_publish_part2=new Date();
		 	String forwardMapping = null;
		
			Utility.SysOut("This is Publish Order Action");	
			ViewOrderModel objViewOrderModel = new ViewOrderModel();
			ViewOrderFormBean objFormbean = (ViewOrderFormBean) form;
			long orderNo=0;
			int publishResult=0;
			String publishPage=null;
			String serviceList=null;
			String publishList=null;
			String roleid=null;
			String ownerid=null;
			orderNo=Long.parseLong(request.getParameter("PONum"));
			publishPage=request.getParameter("publishChangeOrd");
			serviceList=request.getParameter("serviceList");
			publishList=request.getParameter("publishList");
			roleid=request.getParameter("roleid");
			Connection conn = null;
			try 
			{
				conn = DbConnection.getConnectionObject(); 
				
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(
						AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(
						request.getSession().getId());
				UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(
						AppConstants.APP_SESSION_USER_INFO);
				long empID=Long.parseLong(objUserDto.getEmployeeId());
				long role = Long.parseLong(objUserDto.getUserRoleId());
				ArrayList aList = objViewOrderModel.getTaskListOfOrder(orderNo);
				String owner = new Utility().getOrderOwnedBy(aList);
				//added by Ravneet-----------
				//for the order no fetch the , order type- New or Change 
				ViewOrderDao objViewOrderDao =  new ViewOrderDao();
				OrderHeaderDTO orderTypeDto =objViewOrderDao.getOrderType(conn,orderNo);
				orderTypeDto.setPublished_by_empid(empID);
				orderTypeDto.setPublished_by_roleid(role);
				//---------------------------
				
				//This will send M6 data to local table and FX account create table data
				publishResult=objViewOrderModel.getPublishResult(
						orderNo,publishPage,orderTypeDto,serviceList,publishList,roleid,owner);
				int m6Status=0;
				if(publishResult==0)
				{
					m6Status=1;
					objFormbean.setMessage("Order published successfully !!");
					
					
					//call Account create function for specific order
					// no need to put order type conditions here since we are passing orderNo as input 
					// hence we there is no data in intermediate account tyable for FX for this order no , no account will be craeted
					try
					{
						if("Y".equalsIgnoreCase(Utility.getAppConfigValue("ON_PUBLISH_ACCOUNT_CREATE")))
						{
							new CreateAccount().createAccount(conn ,orderNo);
						}
					}
					catch(Throwable th)
					{
						Utility.LOG(true, true, th, "Calling API for Account Create");
					}
				}
				else
				{
					m6Status=-1;
					objFormbean.setMessage("Due to some problem, Order has not be published!!");
				}
				//viewOrder(mapping,form,request,response);
				if(!"CHANGE".equalsIgnoreCase(publishPage)){
					/*RequestDispatcher requestDispatcher=request.getRequestDispatcher("/NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName=editON&m6Status="+m6Status);
					requestDispatcher.forward(request, response);*/
					
					//viewOrder(mapping,form,request,response);
					if(null != serviceList && "".equalsIgnoreCase(serviceList.trim())){
						RequestDispatcher requestDispatcher=request.getRequestDispatcher("/NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName=viewMode&m6Status="+m6Status);
						requestDispatcher.forward(request, response);
						//forwardMapping ="ViewOrderUpdateDisplay";
					}else{
						StringTokenizer st1 = new StringTokenizer( publishList, ",");
						//Puneet - flag to check whether all services has been manually selected by user instead of
						//selecting select all option
						boolean allPublished = true;
						String nextVal;
						for (int i =0; st1.hasMoreTokens() && allPublished;i++){
							nextVal = st1.nextToken();
							if(null != nextVal && nextVal.trim().equals("0")){
								allPublished = false;
							}
							nextVal=null;
						}
						if(allPublished){
							RequestDispatcher requestDispatcher=request.getRequestDispatcher("/NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName=viewMode&m6Status="+m6Status);
							requestDispatcher.forward(request, response);
						}else{
							RequestDispatcher requestDispatcher=request.getRequestDispatcher("/NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName=editON&m6Status="+m6Status);
							requestDispatcher.forward(request, response);
						}
						//forwardMapping ="NewOrderUpdateDisplay";
					}	
				}else{
					RequestDispatcher requestDispatcher=request.getRequestDispatcher("/ChangeOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName=editON&m6Status="+m6Status);
					requestDispatcher.forward(request, response);
					//viewOrder(mapping,form,request,response);
					//forwardMapping ="ChangeOrderUpdateDisplay";
				}
				try{
					Date var_end_time_publish_part2=new Date();
					int total_elapsed_time=(int)(var_end_time_publish_part2.getTime()-var_start_publish_part2.getTime());
					int orderno=(int)orderNo;
					int status=NewOrderDaoExt.loggTotalTimeTaken(orderno, var_start_publish_part2, total_elapsed_time, "PUBLISH_PART2");
				}catch(Exception e){
					Utility.LOG(e);
				}
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				logger.error(ex.getMessage()+ " Exception occured in fnPublishOrder method of "+ this.getClass().getSimpleName());
				forwardMapping="ErrorPageAction";
				return mapping.findForward(forwardMapping);
			}
			finally
			{
				try {
					DbConnection.freeConnection(conn);
				} catch (Exception e) {
					throw Utility.onEx_LOG_RET_NEW_EX(e, "", "", "", true, true);
				}
			}
		return mapping.findForward(forwardMapping);
	 }

	 public ViewOrderDto fnReTryAccountCreateInFx(Long orderNo)
		throws Exception
	{
		logger.info("In fnReTryAccountCreateInFx");
		ViewOrderDto dto=new ViewOrderDto(); 
		int result=0;
		Connection conn = null;
		try 
		{
			conn=DbConnection.getConnectionObject();
			CreateAccount account=new CreateAccount();
			ViewOrderDao dao=new ViewOrderDao();
			account.createAccount(conn ,orderNo);
			result=dao.getINTERNALID(orderNo,conn);
			dto.setAccountPending(result);
		}
		catch(Exception ex)
		{
			logger.error(ex.getMessage()+ " Exception occured in fnReTryAccountCreateInFx method of "+ this.getClass().getSimpleName());
			throw Utility.onEx_LOG_RET_NEW_EX(ex, "", "", "", true, true);
		}
		finally
		{
			try {
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				throw Utility.onEx_LOG_RET_NEW_EX(e, "", "", "", true, true);
			}
		}
		return dto;
	}
	 
	 //To post the charges on FX
	 public ViewOrderDto fnBillingTriggerSubmit(ViewOrderDto dto) throws Exception
	 {
		 return fnBillingTriggerSubmitForChangeOrders(dto);
		 /*	logger.info("Billing Trigger Interface of View Order Action and fnBillingTriggerSubmit method have been called");
		 	String forwardMapping = null;
		 	System.out.println("This is Billing Trigger Submit Action");	
			ViewOrderModel objViewOrderModel = new ViewOrderModel();
			
			int publishResult=0;
			try 
			{
				long orderNo=0;
				String strBillingTrigger=null;
				orderNo=Long.parseLong(dto.getOrderNo());
				strBillingTrigger=dto.getBillingTriggerString();
				String dataChanged=dto.getDataChanged();
				
				publishResult=objViewOrderModel.getBillingTriggerResult(orderNo,strBillingTrigger,dataChanged, dto);
				
				if(publishResult==0)
				{
					objFormbean.setMessage("Billing Triggered Successfully !!");
				}
				else
				{
					objFormbean.setMessage("Due to some problem, Billing is not Triggered!!");
				}
				
				forwardMapping ="ViewBillingTrigger";
			}
			catch(Exception ex)
			{
		   		logger.error(ex.getMessage()+ " Exception occured in fnBillingTriggerSubmit method of "+ this.getClass().getSimpleName());
		   		Utility.javascriptExceptionShow(ex);
			}
		return dto;*/
	 }
	 
	 //created  For BILLING TRIGGER DISCONNECT
/*	 public ViewOrderDto fnBillingTriggerSubmitForDisConnectHHHHHHHHH(ViewOrderDto dto) throws Exception
	 {
		 	logger.info("Billing Trigger Interface of View Order Action and fnBillingTriggerSubmit method have been called");
		 	String forwardMapping = null;
		 	System.out.println("This is Billing Trigger Submit Action");	
			ViewOrderModel objViewOrderModel = new ViewOrderModel();
			
			ViewOrderDto publishResult=null;
			try 
			{
				long orderNo=0;
				Connection conn=null;
				String strBillingTrigger=null;
				orderNo=Long.parseLong(dto.getOrderNo());
				strBillingTrigger=dto.getBillingTriggerString();
				String dataChanged=dto.getDataChanged();

				ViewOrderDao objViewOrderDao =  new ViewOrderDao();
				 publishResult=objViewOrderModel.fnBillingTriggerSubmitForDisconnect(orderNo,strBillingTrigger,dataChanged, dto);
						
                 forwardMapping ="ViewBillingTriggerInFx";
                
				
				}
			catch(Exception ex)
			{
		   		logger.error(ex.getMessage()+ " Exception occured in fnBillingTriggerSubmit method of "+ this.getClass().getSimpleName());
		   		Utility.javascriptExceptionShow(ex);
			}
		return publishResult;
	 }*/
	 


// For Change Orders excewpt Disconnection ie Soln Change, Rate Renewal, Network Change
public ViewOrderDto fnBillingTriggerSubmitForChangeOrders(ViewOrderDto dto) throws Exception
{
	 	logger.info("Billing Trigger Submit Interface ->  ViewOrderAction-class and fnBillingTriggerSubmitForChangeOrders method have been called");
	 	
		Utility.SysOut("This is Billing Trigger Submit Action");	
		ViewOrderModel objViewOrderModel = new ViewOrderModel();
		
		ViewOrderDto publishResult=null;
		try 
		{
			long orderNo=0;
		
			String strBillingTrigger=null;
			orderNo=Long.parseLong(dto.getOrderNo());
			strBillingTrigger=dto.getBillingTriggerString();
			String dataChanged=dto.getDataChanged();
			
			publishResult=objViewOrderModel.fnBillingTriggerSubmitForChangeOrders(orderNo,strBillingTrigger,dataChanged, dto);
			
			//below code added by Anil for Sending Response To MPP after Successfully Billing Trigger by Manually
			//-------------------------- Sending Response to MPP --------------------------
			
			/*ViewOrderDto objServiceTypeOrdSrcDto=new ViewOrderDto();
			NewOrderDao objDao = new NewOrderDao();
			if(dto.getOrderNo()!=null){
			objServiceTypeOrdSrcDto=objDao.getServiceType_OrderSourceClepErp(Long.valueOf(dto.getOrderNo()),0);
			if(objServiceTypeOrdSrcDto.getServiceTypeID()==2 && "2".equalsIgnoreCase(objServiceTypeOrdSrcDto.getOrder_creation_source())){
				if(objServiceTypeOrdSrcDto.getIsBTDone() > 0)
				{
					CLEPUtility.SysErr("-------- Finding Response for sending to MPP >>>>>>>>>");
					if(objServiceTypeOrdSrcDto.getResponseClepOrderMsgAfterAproval()!=null){
						CLEPXmlDto clepXmlDto=new CLEPXmlDto();
						clepXmlDto.setXmlData(objServiceTypeOrdSrcDto.getResponseClepOrderMsgAfterAproval());
						CLEPUtility.SysErr("Mesaage Request ID Was:- "+objServiceTypeOrdSrcDto.getClepJMSReqMsgId());
						clepXmlDto.setJmsMessageID(objServiceTypeOrdSrcDto.getClepJMSReqMsgId());
						objDao.sendClepResponseToMpp(clepXmlDto,objServiceTypeOrdSrcDto.getClepFileId(),Long.valueOf(dto.getOrderNo()),"BT");							
					}	
				}
			}
			}*/
			//-----------------------------[ End CLEP ]------------------------------------------------
        }
		catch(Exception ex)
		{
	   		logger.error(ex.getMessage()+ " Exception occured in fnBillingTriggerSubmit method of "+ this.getClass().getSimpleName());
	   		Utility.javascriptExceptionShow(ex);
		}
	return publishResult;
}


public ViewOrderDto fnUpdateLocdetails(ViewOrderDto dto) throws Exception
{
	 	logger.info("Billing Trigger Submit Interface ->  ViewOrderAction-class and fnBillingTriggerSubmitForChangeOrders method have been called");
	 	
		Utility.SysOut("This is Billing Trigger Submit Action");	
		ViewOrderModel objViewOrderModel = new ViewOrderModel();
		
		ViewOrderDto publishResult=null;
		try 
		{
			long orderNo=0;
		
			String strBillingTrigger=null;
			orderNo=Long.parseLong(dto.getOrderNo());
			strBillingTrigger=dto.getBillingTriggerString();
			String dataChanged=dto.getDataChanged();
			
			publishResult=objViewOrderModel.fnUpdateLocdetails(orderNo,strBillingTrigger,dataChanged, dto);
        }
		catch(Exception ex)
		{
	   		logger.error(ex.getMessage()+ " Exception occured in fnBillingTriggerSubmit method of "+ this.getClass().getSimpleName());
	   		Utility.javascriptExceptionShow(ex);
		}
	return publishResult;
}

public ActionForward fnExportChargeSummary(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) 
{
	 	logger.info("Charge Summary Interface of View Order Action and fnChargeSummary method have been called");
	 	String forwardMapping = null;
	
		Utility.SysOut("This is Charge Summary Action");	
	 	ViewOrderFormBean formBean =(ViewOrderFormBean)form;
		ViewOrderModel objViewOrderModel = new ViewOrderModel();
		long orderNo=0;
		orderNo=Long.parseLong(request.getParameter("PONum"));
		try 
		{
			objViewOrderModel.setChargeSummaryData(formBean,orderNo);
			//request.setAttribute("chargeSummaryList", aList);
			forwardMapping ="ExportChargeSummary";
		}
		catch(Exception ex)
		{
  		logger.error(ex.getMessage()+ " Exception occured in fnChargeSummary method of "+ this.getClass().getSimpleName());
			forwardMapping="ErrorPageAction";
			return mapping.findForward(forwardMapping);
		}
	return mapping.findForward(forwardMapping);
}

	public ActionForward ViewAccountUpdateStatusPage(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) 
	{
		 	logger.info("View Order Interface and viewOrder method have been called");
		 	String forwardMapping = null;
			try 
			{
				forwardMapping ="ViewAccountUpdateStatusPage";
			}
			catch(Exception ex)
			{
	  		logger.error(ex.getMessage()+ " Exception occured in viewOrder method of "+ this.getClass().getSimpleName());
				forwardMapping="ErrorPageAction";
				return mapping.findForward(forwardMapping);
			}
		return mapping.findForward(forwardMapping);
	}

}



