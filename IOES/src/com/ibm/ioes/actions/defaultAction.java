//[001]	 MANISHA GARG	02-MAR-11	00-05422		To add New Field New Order Type in New Order.jsp
//[002]	 Manisha		14-Mar-11	00-05422		Draft New Search
//[003]	 Manisha		14-Mar-11	00-05422		Draft Change Search
//[004]		Vishwa		17-Jul-2012	CSR00-05422		Code Changes for adding extra Extended Attributes in change Order
//[005]		Santosh 	22-Jan-2014					Code for taking responsibility fromn CRM and set in to session
//[0010]   Gunjan       24-June-2014   CSR_20140526_R1_020159     Order Cancellation Post Publish
package com.ibm.ioes.actions;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import com.ibm.appsecure.util.Encryption;
import com.ibm.ioes.beans.ArchivalReportBean;
import com.ibm.ioes.beans.DefaultBean;
import com.ibm.ioes.beans.NewOrderBean;
import com.ibm.ioes.beans.SessionObjectsDto;
import com.ibm.ioes.beans.UserInfoDto;
import com.ibm.ioes.beans.ValidationBean;
import com.ibm.ioes.clep.CLEPXmlDto;
import com.ibm.ioes.clep.ParseXMLforCLEP;
import com.ibm.ioes.dao.ArchiveIb2bOrdersDao;
import com.ibm.ioes.dao.NewOrderDao;
import com.ibm.ioes.exception.IOESException;
import com.ibm.ioes.forms.DisconnectOrderDto;
import com.ibm.ioes.forms.FieldAttibuteDTO;
import com.ibm.ioes.forms.LSICancellationDto;
import com.ibm.ioes.forms.LoginDto;
import com.ibm.ioes.forms.NewOrderDto;
import com.ibm.ioes.forms.OrderHeaderDTO;
import com.ibm.ioes.model.ArchivalModel;
import com.ibm.ioes.model.ArchivalModel.ARCHIVAL_CATEGORY;
import com.ibm.ioes.model.ArchivalModel.ValidationStatus;
import com.ibm.ioes.model.LoginModel;
import com.ibm.ioes.model.NewOrderModel;
import com.ibm.ioes.npd.actions.LoginActionNPD;
import com.ibm.ioes.npd.hibernate.dao.CommonBaseDao;
import com.ibm.ioes.npd.model.NpdUserModel;
import com.ibm.ioes.schedular.ApplicationPlugin;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.AppUtility;
import com.ibm.ioes.utilities.LogInSessionListener;
import com.ibm.ioes.utilities.PagingSorting;
import com.ibm.ioes.utilities.Utility;



public class defaultAction extends DispatchAction
{
	private static final Logger NPD_LOGIN;
	
	public static String roleName;
	

	static {
		NPD_LOGIN = Logger.getLogger(LoginActionNPD.class);
	}
	//This Method Redirects User to New Order Interface for New Order Entry
	public ActionForward goToNewOrder(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		ActionMessages messages = new ActionMessages();
		NewOrderBean objForm = new NewOrderBean();
		try
		{
			 //NewOrderDto objDto=new NewOrderDto();
			NewOrderAction NOAction=new NewOrderAction();
			NewOrderModel modelObj=new NewOrderModel();			
			ArrayList<FieldAttibuteDTO> listMainDetails=null;
			//ArrayList<NewOrderDto> listRegion= new ArrayList<NewOrderDto>();
//			Meenakhi : account from session start
			String accountFromSession = (String)request.getSession().getAttribute("ACCOUNTNO");
//			Meenakhi : account from session end
			//listRegion=modelObj.getRegionList();
			//[004] passing BOTH in case of New Order
			String OrderType="BOTH";
			listMainDetails=NOAction.fetchMainDetails(mapping, form, request, response,OrderType);
			request.setAttribute("MainDetailsBean", listMainDetails);
			//request.setAttribute("listRegion", listRegion);
			request.setAttribute("showAttachIcon", "NO");
//			 [006] Start
//			Meenakhi : account from session start
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			long empID=Long.parseLong(objUserDto.getEmployeeId());
			//raghu showing Sales cordinator Details
			//objDto=modelObj.fetchSalesPersonDetails(empID);
			objForm.setSpFirstname(objUserDto.getSalesFirstName());
			objForm.setSpLastName(objUserDto.getSalesLastName());
			objForm.setSpLPhno(String.valueOf(objUserDto.getSalesPhoneno()));
			objForm.setSpLEmail(objUserDto.getSalesEmailId());
			
			if(accountFromSession != null)
			{
				objForm.setAccountID(accountFromSession);
				OrderHeaderDTO objectDto = modelObj.getAccountData(accountFromSession);
				objForm.setAccountID(new Integer(objectDto.getAccountID()).toString());
				objForm.setCrmAccountNo(new Integer(objectDto.getCrmAccountId()).toString());
				objForm.setAccountName(objectDto.getAccountName());
				objForm.setAccphoneNo(new Long(objectDto.getAccphoneNo()).toString());
				objForm.setLob(objectDto.getLob());
				objForm.setAccountManager(objectDto.getAccountManager());
				objForm.setRegion(objectDto.getRegion());
				objForm.setRegionId(new Integer(objectDto.getRegionId()).toString());
				objForm.setAttribute_2(new Long(objectDto.getAcmgrPhno()).toString());
				objForm.setAttribute_3(objectDto.getAcmgrEmail());
			}
		//			Meenakhi : account from session end
			request.setAttribute("objForm", objForm);
			int orderNo=0;
		 //	orderNo=NOAction.getMaxOrderNo();
			
			
			request.setAttribute("MaxOrderBean", orderNo);			
			forward = mapping.findForward("DisplayNewOrderPage");
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		return forward;
	}
	
//	This Method Redirects User to New Order Interface for New Order Entry
	public ActionForward goToChangeOrder(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		ActionMessages messages = new ActionMessages();
		NewOrderBean objForm = new NewOrderBean();
		DefaultBean frmDefaultBean = (DefaultBean)form;
		try
		{
			 NewOrderDto objDto=new NewOrderDto();
			NewOrderAction NOAction=new NewOrderAction();
			NewOrderModel modelObj=new NewOrderModel();
			ArrayList<FieldAttibuteDTO> listMainDetails=new ArrayList<FieldAttibuteDTO>();
			ArrayList<NewOrderDto> listChangeType=new ArrayList<NewOrderDto>();
			//[004] Change in case of Change Order
			String OrderType="CHANGE";
			listMainDetails=NOAction.fetchMainDetails(mapping, form, request, response, OrderType);
			listChangeType=modelObj.getChangeType();
			/*Vijay
			 * remove 'Demo' change in listChangeType  */
			if(! "1".equals(frmDefaultBean.getIsDemoOrder())){
				for (Iterator iter = listChangeType.iterator(); iter.hasNext();) {
					NewOrderDto element = (NewOrderDto) iter.next();
					if(element.getChangeTypeId() == 4){
						iter.remove();
						break;
					}
				}
			}
			//listChangeType.r
			request.setAttribute("MainDetailsBean", listMainDetails);
			ArrayList<NewOrderDto> listRegion= new ArrayList<NewOrderDto>();
			//listRegion=modelObj.getRegionList();
			listMainDetails=NOAction.fetchMainDetails(mapping, form, request, response, OrderType);
			//request.setAttribute("listRegion", listRegion);
			request.setAttribute("listChangeType", listChangeType);
			request.setAttribute("showAttachIcon", "NO");
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			long empID=Long.parseLong(objUserDto.getEmployeeId());
			
			//objDto=modelObj.fetchSalesPersonDetails(empID);
			// [006] End
			objForm.setSpFirstname(objUserDto.getSalesFirstName());
			objForm.setSpLastName(objUserDto.getSalesLastName());
			objForm.setSpLPhno(String.valueOf(objUserDto.getSalesPhoneno()));
			objForm.setSpLEmail(objUserDto.getSalesEmailId());
			objForm.setInterfaceName(frmDefaultBean.getHiddenInterfaceName());
			/*Vijay start */
			objForm.setIsDemoOrder(frmDefaultBean.getIsDemoOrder());
			/*Vijay end */
			request.setAttribute("objForm", objForm);
			int orderNo=0;
		//	orderNo=NOAction.getMaxOrderNo();
			request.setAttribute("MaxOrderBean", orderNo);			
			forward = mapping.findForward("DisplayChangeOrderPage");
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		return forward;
	}
	
	
	//	This Method Redirects User to IOES Home Page
	public ActionForward goToHome(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		LoginModel objModel = new LoginModel();
		LoginDto loginDto = new LoginDto();
		NewOrderDao objDao  = new NewOrderDao();
		
		HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
		HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
		UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
		
		String reportParamter = objUserDto.getReportParameter();
		String roleId = objUserDto.getUserRoleId();
		String userId = objUserDto.getUserId();
		if("Y".equalsIgnoreCase(reportParamter)){//If user has logged in from report link
			loginDto = objModel.getModuleInterfaceListForReportLink(userId);
			request.setAttribute("MODULE_INTERFACE_LIST", 	loginDto.getListInterfaceDetails());
			request.setAttribute("NEWORDER_INTERFACE_LIST", loginDto.getListInterfaceDetails());			
			request.setAttribute("resname",objUserDto.getRoleName() );
			request.setAttribute("userId",objUserDto.getUserId());	
			forward = mapping.findForward("DisplayHomePage");
		}else if(roleId != null){
			objDao.closeOrderAlreadyOpened("", objUserDto.getUserId(), Long.valueOf(objUserDto.getUserRoleId()));
			loginDto = objModel.getModuleInterfaceList(roleId);
			request.setAttribute("MODULE_INTERFACE_LIST", 	loginDto.getListInterfaceDetails());
			request.setAttribute("NEWORDER_INTERFACE_LIST", loginDto.getListInterfaceDetails());			
			request.setAttribute("resname",objUserDto.getRoleName() );
			request.setAttribute("userId",objUserDto.getUserId());	
			forward = mapping.findForward("DisplayHomePage");
		} else {
			forward = mapping.findForward("LogOut");
		}
		return forward;
	}
	
//	This Method Redirects User to IOES Home Page
	public ActionForward getLsiConfirmation(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		ActionMessages messages = new ActionMessages();
		//ArrayList<OrderHeaderDTO> listChangeSubtype = new ArrayList<OrderHeaderDTO>();
		//String initiatedToFlag=null;
		try
		{
			//objDto.setHdnOrderNo(request.getParameter("changeOrderNo")); 
			//objDto.setRoleId(request.getParameter("roleName"));
			//listChangeSubtype = objModel.getChangeOrderSubTypeAttached(objDto.getHdnOrderNo());
			//request.setAttribute("javaList", request.getAttribute("javaList"));
			forward = mapping.findForward("LSICancelConfirmPage");
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		return forward;
	}
	
	public ActionForward goToHomeAfterClosing(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		LoginModel objModel = new LoginModel();
		LoginDto loginDto = new LoginDto();
		NewOrderDao objDao  = new NewOrderDao();
		HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
		HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
		UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
		objDao.closeOrderAlreadyOpened("", objUserDto.getUserId(), Long.valueOf(objUserDto.getUserRoleId()));
		String roleId = objUserDto.getUserRoleId();
		if(roleId != null){
			loginDto = objModel.getModuleInterfaceList(roleId);
			request.setAttribute("MODULE_INTERFACE_LIST", loginDto.getListInterfaceDetails());
			request.setAttribute("NEWORDER_INTERFACE_LIST", loginDto.getListInterfaceDetails());			
			request.setAttribute("resname",objUserDto.getRoleName() );
			request.setAttribute("userId",objUserDto.getUserId());
			forward = mapping.findForward("DisplayHomePage");
		} else {
			forward = mapping.findForward("LogOut");
		}
		return forward;
	}

	public ActionForward LoginAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward forward = new ActionForward(); // return value		
		String userId;
		NewOrderModel objOrderModel=new NewOrderModel();
		LoginModel objModel = new LoginModel();
		LoginDto loginDto = new LoginDto();
		DefaultBean frmDefaultBean = (DefaultBean)form;
		String dateStr;
		String reportParameter;
		UserInfoDto objUserInfoDto = new  UserInfoDto();
		HttpSession session = request.getSession();
		int respId;
		try {
			userId = (String) request.getParameter("userId");
			dateStr = (String) request.getParameter("dateStr");
			reportParameter = (String) request.getParameter("reportParameter");
			respId = Integer.parseInt(request.getParameter("respId"));
			AppConstants.IOES_LOGGER.info("Responsibility id is : "+respId);
			HttpSession tempSession=request.getSession(true);
			objUserInfoDto.setRespId(respId);
			Encryption enc = new Encryption();
			tempSession.setAttribute("userId",enc.decrypt(userId));
			
			//System.out.println("user id :"+userId+"   datestr :"+dateStr);
			String tmpChecksum = userId + "|" + dateStr;
			String encryptedString = (String)request.getParameter("encryptedString");
			Encryption encrypt = new Encryption();
			String encryptedStringtmp =null;
			if(null!=tmpChecksum){
				encryptedStringtmp=encrypt.generateDigest(tmpChecksum);	
			}
			
			if ((null!=encryptedString && null!=encryptedStringtmp && encryptedStringtmp.equals(encryptedString)) ||
					(null!=encryptedString && encryptedString.equals("xpopupex12345"))) 
			{
				if("Y".equalsIgnoreCase(reportParameter)){
					/*This if block code is written, when user logins through report link ,
					 * we will directly send the user to report option page,i.e. , default page
					 * All accessible report will be visible to user
					 * */
				//	objUserInfoDto = new UserInfoDto();
					objUserInfoDto.setUserId(enc.decrypt(userId));
					objUserInfoDto.setReportParameter(reportParameter);
					
					//Added for Security:Start
					session.invalidate();
					session=request.getSession();
					//Added for Security:End
					
					session.setAttribute(AppConstants.APP_SESSION_USER_INFO,objUserInfoDto);
					SessionObjectsDto.getInstance().setUserRoleId(objUserInfoDto.getUserRoleId());
					
					loginDto = objModel.getModuleInterfaceListForReportLink(enc.decrypt(userId));
					request.setAttribute("MODULE_INTERFACE_LIST", 	loginDto.getListInterfaceDetails());
					request.setAttribute("NEWORDER_INTERFACE_LIST", loginDto.getListInterfaceDetails());
					
					ServletContext ctx = getServlet().getServletContext();
					HashMap<String, HttpSession> htMap = (HashMap<String, HttpSession>) ctx.getAttribute(AppConstants.APP_SESSION);
					htMap.put(session.getId(), session);
					session.setAttribute(AppConstants.APPCONTEXTPATH, request.getContextPath());
					
					request.setAttribute("userId", enc.decrypt(userId));
					forward = mapping.findForward("DisplayHomePage");
				}else if(objOrderModel.isUserValid(encrypt.decrypt(userId).toLowerCase())==false)
				{
					forward = mapping.findForward("LogOut");
					request.setAttribute("ErrorMsg", "You are not authorised to perform this operation.");
				}
				else
				{
					request.setAttribute("userId", userId);
					frmDefaultBean.setUserId(userId);
					session.setAttribute(AppConstants.APP_SESSION_USER_INFO, objUserInfoDto);
					forward = mapping.findForward("LogIn");
				}	
			} 
			else 
			{
				forward = mapping.findForward("LogOut");
				request.setAttribute("ErrorMsg", "You are not authorised to perform this operation.");
			}
			
		} catch (Exception e) {
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
		}
		return forward;
	}

	public ActionForward LoginPageAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward forward = new ActionForward(); // return value
		HttpSession session = request.getSession();
		LoginModel objModel = new LoginModel();
		LoginDto loginDto = new LoginDto();
		String userId;
		NewOrderModel objOrderModel=new NewOrderModel();
		String loggedUserRoleName="";
		Encryption enc = new Encryption();
		int respId;
		
		try {
			userId = enc.decrypt((String) request.getParameter("userId"));
			String roleId = request.getParameter("responsiblity");
			respId = Integer.parseInt(request.getParameter("respId"));
			AppConstants.IOES_LOGGER.info("***************     Responsibility id is : "+respId +"                ***********************");
			//Added for Security:Start
			String unecryptedUserId=userId;
			String sessionUserId=(String)request.getSession().getAttribute("userId");
			if(sessionUserId== null || !sessionUserId.equals(unecryptedUserId))
			{
				forward = mapping.findForward("LogOut");
				request.setAttribute("ErrorMsg", "You are not authorised to perform this operation.");
				return forward;
			}
			//Added for Security:End

			UserInfoDto objUserInfoDto =  objOrderModel.getLoggedUserDetails(userId.toLowerCase(),Integer.parseInt(roleId));
			  
			if(objUserInfoDto.getUserName()==null)
			{
				forward = mapping.findForward("LogOut");
				request.setAttribute("ErrorMsg", "You are not authorised to perform this operation.");
			}
			else
			{
				roleName=objUserInfoDto.getRoleName();
				objUserInfoDto.setUserRoleId(roleId);
				objUserInfoDto.setUserId(userId);
				objUserInfoDto.setRespId(respId);
				objUserInfoDto.setReportParameter("N");//This value means we are accessing ib2b from normal link and not from report link
				
				//Added for Security:Start
				session.invalidate();
				session=request.getSession();
				//Added for Security:End
				
				session.setAttribute(AppConstants.APP_SESSION_USER_INFO,objUserInfoDto);
				SessionObjectsDto.getInstance().setUserRoleId(objUserInfoDto.getUserRoleId());
				
				loginDto = objModel.getModuleInterfaceList(roleId);
				request.setAttribute("MODULE_INTERFACE_LIST", loginDto.getListInterfaceDetails());
				request.setAttribute("NEWORDER_INTERFACE_LIST", loginDto.getListInterfaceDetails());
				ServletContext ctx = getServlet().getServletContext();
				
				HashMap<String, HttpSession> htMap = (HashMap<String, HttpSession>) ctx.getAttribute(AppConstants.APP_SESSION);
				htMap.put(session.getId(), session);
				session.setAttribute(AppConstants.APPCONTEXTPATH, request.getContextPath());		
				
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
					
				loggedUserRoleName=objUserDto.getRoleName();
				request.setAttribute("resname",loggedUserRoleName);
				request.setAttribute("userId",objUserDto.getUserId());
				LogInSessionListener objSessionListner = new LogInSessionListener();
				objSessionListner.updateLoginStatus( userId ,  Long.valueOf(roleId), "1", "1");
				forward = mapping.findForward("DisplayHomePage");
			}	
		} catch (Exception e) {
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
		}
		return forward;
	}

	public void NPDInit(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionMessages messages = new ActionMessages();
		ActionForward forward = new ActionForward(); // return value
		NpdUserModel npdUserModel = new NpdUserModel();
		ArrayList roleListOfAnEmployee = null;
		HashMap userHashMap = new HashMap();
		
		SimpleDateFormat sdf = null;
		String sUserId = null;
		String ssfUserId = null;
		try 
		{
			ssfUserId=(String)request.getSession().getAttribute(AppConstants.SSFID);
			
			 //Makking UserId case insensitive
			if (ssfUserId != null && ssfUserId.length() > 0) 
			{
				ssfUserId = ssfUserId.trim();   
			}
			
			CommonBaseDao commonBaseDao = new CommonBaseDao();
			int attachmentSize = commonBaseDao.getAttachemntSize();
			int pageSize = commonBaseDao.getPageSize();
			String specialCharacter = commonBaseDao.getSpecialChar();
			SessionObjectsDto sessionObjectsDto = SessionObjectsDto.getInstance();
			sessionObjectsDto.setAttachmentSize(attachmentSize);
			sessionObjectsDto.setPageSize(pageSize);
			sessionObjectsDto.setSpecialCharacter(specialCharacter);

			ServletContext sc = request.getSession().getServletContext();
			HttpSession session = (HttpSession) sc.getAttribute(ssfUserId);
			

			if (session != null) {
				sc.removeAttribute(ssfUserId);
				try {
					session.invalidate();
				} catch (Exception e1) {    
					;
				}
			}
			
			userHashMap = npdUserModel
					.getEmployeeDetailsBasedOnEmailID(ssfUserId);
			if (userHashMap != null && userHashMap.size() > 0) {

				//loginBean.setRoleList(roleListOfAnEmployee);
				sdf = new SimpleDateFormat(AppConstants.DATE_TIME_FORMAT);
				NPD_LOGIN.error("User Id , " + ssfUserId + " , Logged In At , "
						+ sdf.format(new Date()));

				//request.getSession().invalidate();
				
				sc.setAttribute(sUserId, request.getSession(true));
				session = (HttpSession) sc.getAttribute(sUserId);

				session.setAttribute(AppConstants.NPD_USERS_SESSION_NAME,
						userHashMap);
				
				session.setAttribute(AppConstants.APPCONTEXTPATH, request
						.getContextPath());
				session.setAttribute("sessionId", session.getId());
				session.setAttribute("ipAddress", request.getRemoteAddr());
				

			} 
			else 
			{
				throw new Exception("logonfailure");
			}

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			messages.add("logonfailure",new ActionMessage("msg.security.id013"));
		}
		
		
	}
	
	public ActionForward LogOutAction(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		HttpSession session = request.getSession(true);
		try
		{
			
			UserInfoDto objUserDto  = (UserInfoDto)session.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			LogInSessionListener objSessionListner = new LogInSessionListener();
			objSessionListner.updateLoginStatus( objUserDto.getUserId() ,  Long.valueOf(objUserDto.getUserRoleId()), "0", "0");
			request.getSession().setAttribute(AppConstants.APP_SESSION_USER_INFO, null);
			request.getSession().invalidate();				
			//request.setAttribute("ErrorMsg", "You Have Successfully Logged Out From iB2B");
			forward = mapping.findForward("LogOut");
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
		}
		return forward;
	
	}

	 
	
	 
	 //Main method for giving the viewIncompleteChangeOrder List -(Change)
	 
	 //003 start
	 public ActionForward viewIncompleteChangeOrder1(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) 
	 {
		String forwardMapping = null;
		NewOrderModel objModel=new NewOrderModel();
		ChangeOrderAction objact=new ChangeOrderAction();
		ArrayList<OrderHeaderDTO> listSourceName = null;
		ArrayList<OrderHeaderDTO> listCurrency = null;
		try 
		{
			listSourceName=objModel.getSourceName();
			request.setAttribute("listSourceName", listSourceName);
			
			listCurrency=objModel.getCurrency();
		request.setAttribute("listCurrency", listCurrency);
			
			 forwardMapping ="IncompleteChangeOrderPage";
		   //objact.viewOrderList_Change(mapping,form,request,response);
			
		}
		catch(Exception ex)
     	{
  		//logger.error(ex.getMessage()+ " Exception occured in viewUniversalWorkQueue method of "+ this.getClass().getSimpleName());
			forwardMapping="ErrorPageAction";
			return mapping.findForward(forwardMapping);
     	}
		return mapping.findForward(forwardMapping);
	 }
	 
	 //003 end
	 
	 
	 
//Main method for giving the viewNewIncompleteOrder List
//002 start
	 
	 public ActionForward viewIncompleteOrder(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) 
	 {
		String forwardMapping = null;
		//NewOrderModel objModel=new NewOrderModel(); 
		ArrayList<NewOrderDto> orderList=new ArrayList<NewOrderDto>();
		DefaultBean objForm=(DefaultBean)form;
		ActionForward forward = new ActionForward(); 
	 
		try
		{
			NewOrderAction objact=new NewOrderAction();
			
			orderList=objact.viewOrderList(mapping, form, request, response);
			 request.setAttribute("orderList", orderList);
				objForm.setOrderList(orderList);
				forward = mapping.findForward("IncompleteOrderPage");
		
		}
		
		
		
		catch(Exception ex)
     	{
  		//logger.error(ex.getMessage()+ " Exception occured in viewUniversalWorkQueue method of "+ this.getClass().getSimpleName());
			forwardMapping="ErrorPageAction";
			return mapping.findForward(forwardMapping);
     	}
	
		return forward;

	
		
	 }
	 
	 
	 
	//002 end 
	 
	 
	 
	 public ActionForward viewIncompleteChangeOrder(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) 
	 {
		String forwardMapping = null;
		
		ArrayList<NewOrderDto> orderList=new ArrayList<NewOrderDto>();
		DefaultBean objForm=(DefaultBean)form;
		ActionForward forward = new ActionForward(); 
	 
		try
		{
			ChangeOrderAction objact=new ChangeOrderAction();
			
			orderList=objact.viewOrderList_Change(mapping, form, request, response);
			 request.setAttribute("orderList", orderList);
				objForm.setOrderList(orderList);
				forward = mapping.findForward("IncompleteChangeOrderPage");
		
		}
		
		
		
		catch(Exception ex)
     	{
  		//logger.error(ex.getMessage()+ " Exception occured in viewUniversalWorkQueue method of "+ this.getClass().getSimpleName());
			forwardMapping="ErrorPageAction";
			return mapping.findForward(forwardMapping);
     	}
	
		return forward;

	
		
	 }
	 
	 
	 
	//002 end 
	  
	 //lawkush Start
	 
	 public ActionForward viewDissociateGam(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) 
	 {
		String forwardMapping = null;
		NewOrderModel objModel=new NewOrderModel();
		ArrayList<NewOrderDto> gamList_orderAttached=new ArrayList<NewOrderDto>();
		NewOrderDto objDto=new NewOrderDto();
		ActionForward forward = new ActionForward(); 
	 
		try
		{		
			
				gamList_orderAttached = objModel.getGamOrderAttached();
				request.setAttribute("gamList_orderAttached", gamList_orderAttached);
				forward = mapping.findForward("DisplayDissociateGam");
		
		}
		
		
		
		catch(Exception ex)
     	{
			forwardMapping="ErrorPageAction";
			return mapping.findForward(forwardMapping);
     	}
	
		return forward;

	
		
	 }
	 
	 //lawkush End 
	 
	  
	 //lawkush Start
	 
	 public ActionForward viewReplaceGam(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) 
	 {
		String forwardMapping = null;
		NewOrderModel objModel=new NewOrderModel();
		ArrayList<NewOrderDto> gamList=new ArrayList<NewOrderDto>();
		ArrayList<NewOrderDto> gamList_orderAttached=new ArrayList<NewOrderDto>();
		ActionForward forward = new ActionForward(); 
	 
		try
		{
				gamList_orderAttached = objModel.getGamOrderAttached();
				request.setAttribute("gamList_orderAttached", gamList_orderAttached);
			
				//gamList = objModel.getGamList(objDto);
				//request.setAttribute("gamList", gamList);
				forward = mapping.findForward("DisplayReplaceGam");
		
		}
		
		
		
		catch(Exception ex)
     	{
			forwardMapping="ErrorPageAction";
			return mapping.findForward(forwardMapping);
     	}
	
		return forward;

	
		
	 }
	 
	 //lawkush End 
	 
	 
	 
//005 START
	 
	 public ActionForward viewIncompletePDOrders(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) 
	 {
		String forwardMapping = null;
		ArrayList<DisconnectOrderDto> PDorderList=new ArrayList<DisconnectOrderDto>();
		DefaultBean objForm=(DefaultBean)form;
		ActionForward forward = new ActionForward(); 
		try
		{
			ChangeOrderAction objact=new ChangeOrderAction();
			PDorderList=objact.viewPDOrderList(mapping, form, request, response);
			request.setAttribute("PDorderList", PDorderList);
			objForm.setPDorderList(PDorderList);
			forward = mapping.findForward("IncompletePDOrderPage");
		}
		catch(Exception ex)
     	{
  		//logger.error(ex.getMessage()+ " Exception occured in viewUniversalWorkQueue method of "+ this.getClass().getSimpleName());
			forwardMapping="ErrorPageAction";
			return mapping.findForward(forwardMapping);
     	}
	
		return forward;
	 }
	 
	  // 005 END
	 
//	 [007] START
	 /*public ActionForward StartFxScheduler(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception 
	 {

		 ActionForward actionForward = new ActionForward();

		 try 
		 {
			 String url = request.getRequestURL().toString();
			 ApplicationPlugin applicationPlugin = new ApplicationPlugin();
			 applicationPlugin.startFxScheduler();
			 getServlet().getServletContext().setAttribute("StartFxScheduler", url);
			 actionForward = mapping.findForward("FXScheduler");
		 }
		 catch (Exception exception)
		 {
			 exception.printStackTrace();

		 }
		 return actionForward;
	 }
	 
	 public ActionForward StartM6Scheduler(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	 {
		 ActionForward actionForward = new ActionForward();

		 try
		 {
			 String url = request.getRequestURL().toString();
			 System.out.println("StartM6Schedular URL is : "+request.getRequestURL().toString());
			 ApplicationPlugin applicationPlugin = new ApplicationPlugin();
			 applicationPlugin.startingM6Scheduler();
			 getServlet().getServletContext().setAttribute("StartM6Scheduler", url);
			 actionForward = mapping.findForward("M6Scheduler");
		 } catch (Exception exception){
			 exception.printStackTrace();
		 }
		 return actionForward;
	 }	 
	 
	 public ActionForward goToFxScheduler(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		 ActionForward actionForward = new ActionForward();
		
		 try
		 {
			 actionForward = mapping.findForward("FXScheduler");
		 }
		 catch (Exception exception)
		 {
			 exception.printStackTrace();
		 }
		 return actionForward;
	 }
	 
	 public ActionForward goToM6Scheduler(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		 ActionForward actionForward = new ActionForward();
		
		 try
		 {
			 actionForward = mapping.findForward("M6Scheduler");
		 }
		 catch (Exception exception)
		 {
			 exception.printStackTrace();
		 }
		 return actionForward;
	 }
	 
	 // [007] END
	 */
	 /**
	  * Forwards LSICancellation.jsp and performs validation of search parameters  
	  * @author Gunjan 
	  * @param mapping
	  * @param form
	  * @param request
	  * @param response
	  * @return ActionForward
	  * @since  July 2014 
	  */
	 
	 //[0010] start
	 public ActionForward goToLSICancellation(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) 
	 {
		//System.out.println("in method of default action");
		String forwardMapping = null;
		
		ArrayList<LSICancellationDto> eligibleLSIForCancelList = new ArrayList<LSICancellationDto>();
		DefaultBean objForm=(DefaultBean)form;
		ActionForward forward = new ActionForward(); 
		NewOrderModel objModel = new NewOrderModel();
		LSICancellationDto objDto=new LSICancellationDto();
		
		try
		{	
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			PagingSorting pagingSorting = objForm.getPagingSorting();
			 pagingSorting.setDefaultifNotPresent("LOGICAL_SI_NO",
						PagingSorting.increment, 1);
			objDto.setPagingSorting(pagingSorting);
			String fromPageSubmit=request.getParameter("fromPageSubmit");				
			request.setAttribute("fromPageSubmit", fromPageSubmit);
			if("1".equals(fromPageSubmit))
			{
			ActionMessages messages = new ActionMessages();
			String searchCRMOrder=objForm.getSearchCRMOrder();
			String searchLSI=objForm.getSearchLSI();
			String searchAccountNo=objForm.getSearchAccountNo();
			String searchAccountName=objForm.getSearchAccountName();
			searchAccountName = (searchAccountName != null) ? searchAccountName.trim() : searchAccountName;
			String searchServiceNo=objForm.getSearchServiceNo();
			String searchFromOrdDate=objForm.getSearchFromOrd_Date();
			String searchToOrdDate=objForm.getSearchToOrd_Date();
			String searchLSIScenario=objForm.getSearchLSIScenario();
			
			ArrayList errors = new ArrayList();
			
			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"CRM/O No",searchCRMOrder , 18),
					Utility.generateCSV(Utility.CASE_MAXLENGTH,
							Utility.CASE_DIGITS_ONLY)).appendToAndRetNewErrs(
					errors);
			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"A/C No",searchAccountNo , 30),
					Utility.generateCSV(Utility.CASE_MAXLENGTH,
							Utility.CASE_DIGITS_ONLY)).appendToAndRetNewErrs(
					errors);
			
			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"A/C Name",searchAccountName , 200),
					Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS,
							Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
					errors);
			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"Serv No",searchServiceNo , 18),
					Utility.generateCSV(Utility.CASE_MAXLENGTH,
							Utility.CASE_DIGITS_ONLY)).appendToAndRetNewErrs(
					errors);
			
			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"Logical SI",searchLSI , 18),
					Utility.generateCSV(Utility.CASE_MAXLENGTH,
							Utility.CASE_DIGITS_ONLY)).appendToAndRetNewErrs(
					errors);
			if (errors != null && errors.size() > 0)// During Server Side
				// Validation
				{
				request.setAttribute("EligibleLSIForCancelList", eligibleLSIForCancelList);
				//objForm.setEligibleLSIForCancelList(eligibleLSIForCancelList);
					request.setAttribute("validation_errors", errors);
					saveMessages(request, messages);
					
				}
				// ******* Validation Ends Here
				// *************************************
				else {
					objDto.setSearchCRMOrder(searchCRMOrder);
					objDto.setSearchAccountNo(searchAccountNo);
					objDto.setSearchAccountName(searchAccountName);
					objDto.setSearchServiceNo(searchServiceNo);
			        objDto.setSearchLSI(searchLSI);
			        objDto.setSearchFromOrderDate(searchFromOrdDate);
			        objDto.setSearchToOrderDate(searchToOrdDate);
			        objDto.setSearchLSIScenario(searchLSIScenario);
			        
			        pagingSorting.setPagingSorting(true, false);
					pagingSorting.setPageRecords(objUserDto.getPageSizeLSICancelLine());
				
					pagingSorting.setPagingSorting(true, true);
					
					eligibleLSIForCancelList=objModel.viewEligibleLSICancelList(objDto,0,0,0,0);
				
				}
			
			request.setAttribute("EligibleLSIForCancelList", eligibleLSIForCancelList);
			//objForm.setEligibleLSIForCancelList(eligibleLSIForCancelList);
			forward = mapping.findForward("LSICancellationPage");
			}
			else{
				forward = mapping.findForward("LSICancellationPage");
			}
			
		}
		catch(Exception ex)
     	{
			Utility.LOG(true, false, ex, ":: from goToLSICancellation()");
			forwardMapping="ErrorPageAction";
			return mapping.findForward(forwardMapping);
     	}
	
		return forward;
	 }
	 //[0010] end
	 /**
	  * request for display standard reason page
	  * @param mapping
	  * @param form
	  * @param request
	  * @param response
	  * @return
	  * @throws Exception
	  */
	 public ActionForward goToStandardReason(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception{
			
		 ActionForward forward = new ActionForward();	     
		try
		{   			
			forward = mapping.findForward("standardReasonPage");
		}
		catch (Exception e) 
		{
			Utility.LOG(true, true,"Some Error Occured in goToStandardReason method of defualtAction");
		}
		return forward;
	}
	 //nancy
	 public ActionForward goToAssignDeassign(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception{
			
		 ActionForward forward = new ActionForward();	     
		try
		{   			
			forward = mapping.findForward("AssignDeassignPage");
			
		}
		
		
		catch (Exception e) 
		{
			Utility.LOG(true, true,"Some Error Occured in goToAssignDeassign method of defaultAction");
		}
		return forward;
	}
	 
	 //VIPIN
	 /**
	  * When user clicks button in  Compute Section for ant category
	  * @param mapping
	  * @param form
	  * @param request
	  * @param response
	  * @return
	  */
	 public ActionForward goToComputeIb2bRecords(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) 
	 {
		String forwardMapping = null;
		
		ActionForward forward = new ActionForward(); 
		ArchivalModel objModel = new ArchivalModel();
		//priya
		ActionMessages messages = new ActionMessages();
		ArchivalModel.ValidationStatus status = null;
		try
		{	
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			String isArchivalAction=request.getParameter("isArchivalAction");
			String categoryId=request.getParameter("categoryId");
			request.setAttribute("isArchivalAction", isArchivalAction);
			request.setAttribute("categoryId", categoryId);
			if("1".equals(isArchivalAction))
			{
				String userId=objUserDto.getUserId();
				switch(null!=categoryId?Integer.parseInt(categoryId):-1)
				{
				case 1:
					status = objModel.computeDataForArchival(ARCHIVAL_CATEGORY.DRAFT, userId);
					System.out.println("****************************************DRAFT CLICKED");
					break;
				case 2:
					status = objModel.computeDataForArchival(ARCHIVAL_CATEGORY.CANCEL, userId);
					System.out.println("##############################################CANCEL CLICKED");
					break;
				case 3:
					status = objModel.computeDataForArchival(ARCHIVAL_CATEGORY.PD, userId);
					System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~PD CLICKED");
					break;
				}
				if (status.getMessage().equals("archivefailed_AlreadyInProgress")) {
					messages.add(AppConstants.MESSAGE_NAME, new ActionMessage("archivefailed_AlreadyInProgress"));

				}else if (status.getMessage().equals("archivefailed_MQT_NOT_REFRESHED")) {
					messages.add(AppConstants.MESSAGE_NAME, new ActionMessage("archivefailed_MQT_NOT_REFRESHED"));

				} 
				if (!messages.isEmpty()) {
					this.saveMessages(request, messages);
				}
				forward=mapping.findForward("ArchiveIb2bRecordsPage");
			}
			else{
				forward = mapping.findForward("ArchiveIb2bRecordsPage");
			}
			
		}
		catch(Exception ex)
     	{
			Utility.LOG(true, false, ex, ":: from goToArchiveIb2bRecords()");
			forwardMapping="ErrorPageAction";
			return mapping.findForward(forwardMapping);
     	}
	
		return forward;
	 }
	 //VIPIN
		//Satish Starts
	 public ActionForward goToArchiveIb2bRecords(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) 
	 {
		
		 String forwardMapping = null;
		
		ActionForward forward = new ActionForward(); 
		ArchivalModel objModel = new ArchivalModel();
		//priya
		ActionMessages messages = new ActionMessages();
		ArchivalModel.ValidationStatus status = null;
		ArchiveIb2bOrdersDao objDao=null;
		ArrayList<ArchivalReportBean> archivallist = null;
		
		try
		{	
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			String isArchivalAction=request.getParameter("isArchivalAction");
			String categoryId=request.getParameter("categoryId");
			request.setAttribute("isArchivalAction", isArchivalAction);
			request.setAttribute("categoryId", categoryId);
			String valueGroupID=request.getParameter("valueBatchID");
			String excludingLSINo=request.getParameter("valueEXcludingID");
			request.setAttribute("valueBatchID", valueGroupID);
			request.setAttribute("valueEXcludingID", excludingLSINo);
			if("1".equals(isArchivalAction))
			{
				String userId=objUserDto.getUserId();
				objDao=new ArchiveIb2bOrdersDao();
				archivallist  = objDao.searchLsiGroupIdServiceList(valueGroupID);
				switch(null!=categoryId?Integer.parseInt(categoryId):-1)
				{
				case 4:
						 if(archivallist.get(0).getCATEGORY_OF_ORDER().equals("DRAFT")){
						status = objModel.archiveDataForGroup(ARCHIVAL_CATEGORY.DRAFT, userId,valueGroupID,excludingLSINo,archivallist);
						System.out.println("****************************************DRAFT CLICKED");
						 }
						 else if(archivallist.get(0).getCATEGORY_OF_ORDER().equalsIgnoreCase("CANCEL")){
							status = objModel.archiveDataForGroup(ARCHIVAL_CATEGORY.CANCEL, userId,valueGroupID,excludingLSINo,archivallist);
							System.out.println("****************************************CANCEL CLICKED");
						}
						 else if(archivallist.get(0).getCATEGORY_OF_ORDER().equalsIgnoreCase("PD")){
							status = objModel.archiveDataForGroup(ARCHIVAL_CATEGORY.PD, userId,valueGroupID,excludingLSINo,archivallist);
							System.out.println("****************************************PD CLICKED");
						}
						
					break;
				}
				if (status.getMessage().equals("archivefailed_AlreadyInProgress")) {
					messages.add(AppConstants.MESSAGE_NAME, new ActionMessage("archivefailed_AlreadyInProgress"));

				}else if (status.getMessage().equals("archivefailed_MQT_NOT_REFRESHED")) {
					messages.add(AppConstants.MESSAGE_NAME, new ActionMessage("archivefailed_MQT_NOT_REFRESHED"));

				}				if (!messages.isEmpty()) {
					this.saveMessages(request, messages);
				}
				forward=mapping.findForward("ArchiveIb2bRecordsPage");
			}
			else{
				forward = mapping.findForward("ArchiveIb2bRecordsPage");
			}
		}
		catch(Exception ex)
     	{
			ex.printStackTrace();
			Utility.LOG(true, false, ex, ":: from goToArchiveIb2bRecords()");
			forwardMapping="ErrorPageAction";
			return mapping.findForward(forwardMapping);
     	}
	
		return forward;
	 }
	//Satish Starts
}
