package com.ibm.ioes.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletOutputStream;
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

import com.ibm.ioes.beans.ArchivalReportBean;
import com.ibm.ioes.beans.NewOrderBean;
import com.ibm.ioes.beans.ReportsBean;
import com.ibm.ioes.beans.UserInfoDto;
import com.ibm.ioes.beans.ValidationBean;
import com.ibm.ioes.dao.ReportsDao;
import com.ibm.ioes.forms.ArchivalReportDto;
import com.ibm.ioes.forms.BCPAddressDto;
import com.ibm.ioes.forms.DispatchAddressDto;
import com.ibm.ioes.forms.LineItemDTO;
import com.ibm.ioes.forms.LocationDetailDto;
import com.ibm.ioes.forms.M6OrderStatusDto;
import com.ibm.ioes.forms.NetworkLocationDto;
import com.ibm.ioes.forms.NewOrderDto;
import com.ibm.ioes.forms.ProductCatelogDTO;
import com.ibm.ioes.forms.ReportsDto;
import com.ibm.ioes.forms.SITransferDto;
import com.ibm.ioes.forms.UserAccessMatrixDto;
import com.ibm.ioes.model.NewOrderModel;
import com.ibm.ioes.model.ReportsModel;
import com.ibm.ioes.newdesign.dto.ActiveLineDemoReportDTO;
import com.ibm.ioes.newdesign.dto.AdvancePaymentReportDTO;
import com.ibm.ioes.newdesign.dto.AttributeDetailsReportDTO;
import com.ibm.ioes.newdesign.dto.BillingTriggerDoneButFailedInFXDTO;
import com.ibm.ioes.newdesign.dto.BillingWorkQueueReportDTO;
import com.ibm.ioes.newdesign.dto.BulkSIUploadReportDto;
import com.ibm.ioes.newdesign.dto.CancelledFailedLineReportDTO;
import com.ibm.ioes.newdesign.dto.DisconnectLineReportDTO;
import com.ibm.ioes.newdesign.dto.DocumentMatrixReportDTO;
import com.ibm.ioes.newdesign.dto.DummyLinesDetailsReportDTO;
import com.ibm.ioes.newdesign.dto.HardwareLineItemCancelReportDTO;
import com.ibm.ioes.newdesign.dto.LempCancelOrderReportDTO;
import com.ibm.ioes.newdesign.dto.LempOrderDetailsReportDTO;
import com.ibm.ioes.newdesign.dto.LempOwnerReportDTO;
import com.ibm.ioes.newdesign.dto.LogicalSIDataReportDTO;
import com.ibm.ioes.newdesign.dto.MigratedApprovedNewOrderDetailReportDTO;
import com.ibm.ioes.newdesign.dto.NonAPP_APPChangeOrderDetailsDTO;
import com.ibm.ioes.newdesign.dto.NonMigratedAPP_UNAPPNewOrderDetailsDTO;
import com.ibm.ioes.newdesign.dto.OBValueReportDTO;
import com.ibm.ioes.newdesign.dto.PendingOrdersAndBillingHardwaresDTO;
import com.ibm.ioes.newdesign.dto.PerformanceReportDTO;
import com.ibm.ioes.newdesign.dto.PerformanceSummaryReportDTO;
import com.ibm.ioes.newdesign.dto.RestPendingLineReportDTO;
import com.ibm.ioes.newdesign.dto.ParallelUpgradeReportDto;
import com.ibm.ioes.newdesign.dto.StartChargeNotPushedInFXDTO;
import com.ibm.ioes.newdesign.dto.ActiveLineItemReportsDTO;
import com.ibm.ioes.newdesign.dto.CopyCancelReportDTO;
import com.ibm.ioes.newdesign.dto.DisconnectChangeOrdeReportDTO;
import com.ibm.ioes.newdesign.dto.M6OrderCancelReportDTO;
import com.ibm.ioes.newdesign.dto.OrderClepReportDTO;
import com.ibm.ioes.newdesign.dto.OrderDetailChangeReportDTO;
import com.ibm.ioes.newdesign.dto.OrderDetailReportDTO;
import com.ibm.ioes.newdesign.dto.OrderReportNewDetailCwnDTO;
import com.ibm.ioes.newdesign.dto.OrderStageReportDTO;
import com.ibm.ioes.newdesign.dto.PendingOrderAndBillingReportDTO;
import com.ibm.ioes.newdesign.dto.PendingServicesReportDTO;
import com.ibm.ioes.newdesign.dto.PerformanceDetailReportDTO;
import com.ibm.ioes.newdesign.dto.RateRenewalReportDTO;
import com.ibm.ioes.newdesign.dto.TelemediaOrderReportDTO;
import com.ibm.ioes.newdesign.dto.ZeroOrderValueReportDTO;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.AppUtility;
import com.ibm.ioes.utilities.ApplicationFlags;
import com.ibm.ioes.utilities.Messages;
import com.ibm.ioes.utilities.PagingSorting;
import com.ibm.ioes.utilities.Utility;
//Tag Name 	Resource Name  Date		CSR No			Description
//[004]  Rohit Verma	8-Feb-13	Arbor			LSI Mapping Interface	
//[006]  Rohit Verma	5-Mar-13	00-08440		Function for Searching Report for Hardware Cancel LIne item
// [101010] Rampratap 								added from date and to date
//[202020] Rampratap 								added from date and to date
//[303030] Rampratap 								added from date and to date
//[HYPR22032013001]  Vijay    30-Mar-2013       	Billing Work Queue Report //
//[RPT7052013031]    Vijay    15-May-2013          	Restrict to fetch the records on First time page loading in Zero Order Value Report//
//[RPT7052013020]    Vijay    15-May-2013          	Restrict to fetch the records on First time page loading in Order Stage Report//
//[RPT7052013020]    Vijay    17-May-2013          	Restrict to fetch the records on First time page loading in Order Stage Report//

//[RPT7052013025]    Mohit   15-May-2013          	Performance Report
//  Mohit   29-May-2013          					Performance Details Report
//[008] Shourya Mishra	30-Nov-13 CSR-09463			Advance Payment / OTC Report
//[009] Rohit Verma		11-Dec-13 CSR-09083			Line Item Dump for Bulk Upload
//[010] Saurabh Singh	02-Apr-14					OB Value Report 
//[0099] Nagarjuna  	10-Mar-2014 PM WELCOME MAIL
//[0011] Gunjan 		1-Dec-2014					Document Matrix Report
//<!-- [002] Raveendra  12/02/2015   add new field in Advance payment for refund process -->
//[0012]  Gunjan Singla  14-Jul-15  20141219_R2_020936  Billing efficiency drop2
//[0013] Varun Gupta   24-Dec-15   CBR_20150626-R2-021462 Archival of iB2B Records
//[0014] Satish Kumar 12-May-2016   enable download Dump in File
//[0015] Priya Gupta 15-Jul-2016	Auto Billing
public class ReportsAction extends DispatchAction {
	private static final Logger logger;
	static {
		logger = Logger.getLogger(ReportsAction.class);
	}
	public ActionForward getBCPAddressReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		ReportsBean formBean = (ReportsBean) form;
		ActionMessages messages = new ActionMessages();

		ArrayList<BCPAddressDto> listAccount = new ArrayList<BCPAddressDto>();
		ArrayList<BCPAddressDto> bcpList = new ArrayList<BCPAddressDto>();

		BCPAddressDto objDto = new BCPAddressDto();

		String userId,interfaceId;
		try {
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			interfaceId=(String) request.getParameter("interfaceId");
			userId=objUserDto.getUserId();
			System.out.println(userId+" "+interfaceId);
			PagingSorting pagingSorting = formBean.getPagingSorting();
			pagingSorting.setPagingSorting(true, true);
			pagingSorting.setDefaultifNotPresent("accountId",
					PagingSorting.increment, 1);
			objDto.setPagingSorting(pagingSorting);

			request.setAttribute("customerList", bcpList);
			request.setAttribute("userId",objUserDto.getUserId());
			formBean.setCustomerList(bcpList);
			formBean.setList(listAccount);
			forward = mapping.findForward("displayBCPReport");
		} catch (Exception e) {
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
			e.printStackTrace();
		}
		return forward;
	}
	public ActionForward getM6OrderStatusReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		ReportsBean formBean = (ReportsBean) form;
		ActionMessages messages = new ActionMessages();

		ArrayList<M6OrderStatusDto> orderList = new ArrayList<M6OrderStatusDto>();

		M6OrderStatusDto objDto = new M6OrderStatusDto();
		String userId,interfaceId;

		try {
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				interfaceId=(String) request.getParameter("interfaceId");
				userId=objUserDto.getUserId();
				System.out.println(userId+" "+interfaceId);
			PagingSorting pagingSorting = formBean.getPagingSorting();
			pagingSorting.setPagingSorting(true, true);
			pagingSorting.setDefaultifNotPresent("orderNO",
					PagingSorting.increment, 1);
			objDto.setPagingSorting(pagingSorting);

			request.setAttribute("orderList", orderList);
			formBean.setOrderList(orderList);
			request.setAttribute("userId",objUserDto.getUserId());
			forward = mapping.findForward("displayM6OrderStatusReport");
		} catch (Exception e) {
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
			e.printStackTrace();
		}
		return forward;
	}
	
	public ActionForward viewM6RspnsHistory(ActionMapping mapping, 
			ActionForm form,HttpServletRequest request, 
			HttpServletResponse response) throws Exception
	 {
		logger
				.info("View M6 Order Response History have been called");
		M6OrderStatusDto objDto = new M6OrderStatusDto();
		ReportsModel objReportModel = new ReportsModel();
		ArrayList<M6OrderStatusDto> listReponseHistory = new ArrayList<M6OrderStatusDto>();

		ReportsBean objForm = (ReportsBean) form;
		long m6OrderNo = 0;
		//long orderNo = 0;

		ActionForward forward = new ActionForward();
		ActionMessages messages = new ActionMessages();
		String userId,interfaceId;
		try {	
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			interfaceId=(String) request.getParameter("interfaceId");
			userId=objUserDto.getUserId();
			System.out.println(userId+" "+interfaceId);
			m6OrderNo = Long.parseLong(request.getParameter("m6OrderNo"));
			//orderNo = Long.parseLong(request.getParameter("orderNo"));
			listReponseHistory = objReportModel.viewM6ResponseHistory(objDto,
					m6OrderNo);
			objForm.setM6OrderNoStr(String.valueOf(m6OrderNo));
			request.setAttribute("listReponseHistory", listReponseHistory);
			objForm.setOrderList(listReponseHistory);
			request.setAttribute("userId",objUserDto.getUserId());
			forward = mapping.findForward( "ViewM6ResponseHistory");
		} catch (Exception e) {
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
			e.printStackTrace();
		}
		return forward;
	}


	
	public ActionForward viewBCPList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String forwardMapping = "";
		ArrayList<BCPAddressDto> bcpList = new ArrayList<BCPAddressDto>();
		ReportsModel objService = new ReportsModel();
		ReportsBean objForm = (ReportsBean) form;
		BCPAddressDto objDto = new BCPAddressDto();
		String userId,interfaceId;
		try {
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			interfaceId=(String) request.getParameter("interfaceId");
			userId=objUserDto.getUserId();
			System.out.println(userId+" "+interfaceId);
			// Validate form fields
			ActionMessages messages = new ActionMessages();
			String accountIdStr = objForm.getCrmAccountNo();
			String accountNameStr = objForm.getAccountNameStr();
			accountNameStr = (accountNameStr != null) ? accountNameStr.trim() : accountNameStr;
			String bcpIdStr = objForm.getBcpIdStr();
			String bcpNameStr = objForm.getBcpNameStr();
			bcpNameStr = (bcpNameStr != null) ? bcpNameStr.trim() : bcpNameStr;

			ArrayList errors = new ArrayList();

			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"A/C No", accountIdStr, 18),
					Utility.generateCSV(Utility.CASE_MAXLENGTH,
							Utility.CASE_DIGITS_ONLY)).appendToAndRetNewErrs(
					errors);

			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"A/C Name", accountNameStr, 200),
					Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS,
							Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
					errors);

			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"BCP Code", bcpIdStr, 18),
					Utility.generateCSV(Utility.CASE_MAXLENGTH,
							Utility.CASE_DIGITS_ONLY)).appendToAndRetNewErrs(
					errors);

			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"BCP Name", bcpNameStr, 200),
					Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS,
							Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
					errors);

			if (errors != null && errors.size() > 0)// During Server Side
			// Validation
			{
				request.setAttribute("customerList", bcpList);
				objForm.setCustomerList(bcpList);
				request.setAttribute("validation_errors", errors);
				saveMessages(request, messages);
				request.setAttribute("userId",objUserDto.getUserId());
				return mapping.findForward("displayBCPReport");
			}
			// ******* Validation Ends Here
			// *************************************
			else {
				objDto.setSearchAccountIdStr(accountIdStr);
				objDto.setSearchAccountNameStr(accountNameStr);
				objDto.setSearchBcpIdStr(bcpIdStr);
				objDto.setSearchBcpNameStr(bcpNameStr);

				PagingSorting pagingSorting = objForm.getPagingSorting();

				String inExcel = objForm.getToExcel();
				if ("true".equals(inExcel)) {
					pagingSorting.setPagingSorting(false, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "displayReportInExcel";
				} else {
					pagingSorting.setPagingSorting(true, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "displayBCPReport";
				}

				pagingSorting.setDefaultifNotPresent("accountId",
						PagingSorting.increment, 1);
				objDto.setPagingSorting(pagingSorting);

				bcpList = objService.viewBCPList(objDto);

				if ("true".equals(inExcel)) {
					objForm.setBcpExcelList(bcpList);
					request.setAttribute("bcpExcelList", bcpList);
				} else {
					request.setAttribute("customerList", bcpList);
					objForm.setCustomerList(bcpList);
				}
			}
		} catch (Exception ex) {
			return mapping.findForward(Messages
					.getMessageValue("errorGlobalForward"));
		}

		return mapping.findForward(forwardMapping);
	}

	public ActionForward viewM6OrderList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String forwardMapping = "";
		ArrayList<M6OrderStatusDto> orderList = new ArrayList<M6OrderStatusDto>();
		ReportsModel objService = new ReportsModel();
		ReportsBean objForm = (ReportsBean) form;
		M6OrderStatusDto objDto = new M6OrderStatusDto();
		String userId,interfaceId;
		try {
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			interfaceId=(String) request.getParameter("interfaceId");
			userId=objUserDto.getUserId();
			System.out.println(userId+" "+interfaceId);
			// Validate form fields
			ActionMessages messages = new ActionMessages();
			String accountIdStr = objForm.getCrmAccountNo();
			String accountNameStr = objForm.getAccountNameStr();
			accountNameStr = (accountNameStr != null) ? accountNameStr.trim()
					: accountNameStr;
			String orderNoStr = objForm.getOrderNoStr();
			ArrayList errors = new ArrayList();

			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength("A/C No",
							accountIdStr, 18),
					Utility.generateCSV(Utility.CASE_MAXLENGTH,
							Utility.CASE_DIGITS_ONLY)).appendToAndRetNewErrs(
					errors);

			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"A/C Name", accountNameStr, 200),
					Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS,
							Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
					errors);

			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"Order Number", orderNoStr, 18),
					Utility.generateCSV(Utility.CASE_MAXLENGTH,
							Utility.CASE_DIGITS_ONLY)).appendToAndRetNewErrs(
					errors);

			
			if (errors != null && errors.size() > 0)// During Server Side
			// Validation
			{
				request.setAttribute("orderList", orderList);
				objForm.setOrderList(orderList);
				request.setAttribute("validation_errors", errors);
				saveMessages(request, messages);
				return mapping.findForward("displayM6OrderStatusReport");
			}
			// ******* Validation Ends Here
			// *************************************
			else {
				objDto.setSearchAccountIdStr(accountIdStr);
				objDto.setSearchAccountNameStr(accountNameStr);
				objDto.setSearchOrderNoStr(orderNoStr);
			

				PagingSorting pagingSorting = objForm.getPagingSorting();

				String inExcel = objForm.getToExcel();
				if ("true".equals(inExcel)) {
					pagingSorting.setPagingSorting(false, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "displayReportInExcel";
				} else {
					pagingSorting.setPagingSorting(true, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "displayM6OrderStatusReport";
				}

				pagingSorting.setDefaultifNotPresent("orderNO",
						PagingSorting.increment, 1);
				objDto.setPagingSorting(pagingSorting);

				orderList = objService.viewM6OrderList(objDto);
					
			
				if ("true".equals(inExcel)) {
					objForm.setOrderExcelList(orderList);
					request.setAttribute("m6OrderExcelList", orderList);
				} else {
					request.setAttribute("orderList", orderList);
					objForm.setOrderList(orderList);
					
				
				}
			}
		} catch (Exception ex) {
			return mapping.findForward(Messages
					.getMessageValue("errorGlobalForward"));
		}
		Utility.SysOut("orderList            + " + orderList);
		return mapping.findForward(forwardMapping);
	}

	public ActionForward getNetworkLocsReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		ReportsBean formBean = (ReportsBean) form;
		ActionMessages messages = new ActionMessages();

		ArrayList<NetworkLocationDto> listAccount = new ArrayList<NetworkLocationDto>();
		ArrayList<NetworkLocationDto> locsList = new ArrayList<NetworkLocationDto>();

		NetworkLocationDto objDto = new NetworkLocationDto();
		String userId,interfaceId;

		try {
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			interfaceId=(String) request.getParameter("interfaceId");
			userId=objUserDto.getUserId();
			System.out.println(userId+" "+interfaceId);
			PagingSorting pagingSorting = formBean.getPagingSorting();
			pagingSorting.setPagingSorting(true, true);
			pagingSorting.setDefaultifNotPresent("LOCATION_CODE",
					PagingSorting.increment, 1);
			objDto.setPagingSorting(pagingSorting);

			request.setAttribute("customerList", locsList);
			formBean.setCustomerList(locsList);
			formBean.setList(listAccount);
			request.setAttribute("userId",objUserDto.getUserId());
			forward = mapping.findForward("displayNetworkLocsReport");
		} catch (Exception e) {
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
			e.printStackTrace();
		}
		return forward;
	}

	public ActionForward viewNetworkLocsList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String forwardMapping = "";
		ArrayList<NetworkLocationDto> locsList = new ArrayList<NetworkLocationDto>();
		ReportsModel objService = new ReportsModel();
		ReportsBean objForm = (ReportsBean) form;
		NetworkLocationDto objDto = new NetworkLocationDto();
		String userId,interfaceId;
		try {
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			interfaceId=(String) request.getParameter("interfaceId");
			userId=objUserDto.getUserId();
			System.out.println(userId+" "+interfaceId);
			// Validate form fields
			ActionMessages messages = new ActionMessages();
			String networkLocationIdStr = objForm.getNetworkLocationIdStr();
			String contactNameStr = objForm.getContactNameStr();
			contactNameStr = (contactNameStr != null) ? contactNameStr.trim() : contactNameStr;

			ArrayList errors = new ArrayList();

			Utility
					.validateValue(
							new ValidationBean().loadValidationBean_Maxlength(
									"Network Location Code ",
									networkLocationIdStr, 18),
							Utility.generateCSV(Utility.CASE_MAXLENGTH,
									Utility.CASE_DIGITS_ONLY))
					.appendToAndRetNewErrs(errors);

			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"Contact Name", contactNameStr, 200),
					Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS,
							Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
					errors);

			if (errors != null && errors.size() > 0)// During Server Side
			// Validation
			{
				request.setAttribute("customerList", locsList);
				objForm.setCustomerList(locsList);
				request.setAttribute("validation_errors", errors);
				saveMessages(request, messages);
				request.setAttribute("userId",objUserDto.getUserId());
				return mapping.findForward("displayNetworkLocsReport");
			}
			// ******* Validation Ends Here
			// *************************************
			else {
				objDto.setSearchNetworkLocationIdStr(networkLocationIdStr);
				objDto.setSearchContactNameStr(contactNameStr);
				PagingSorting pagingSorting = objForm.getPagingSorting();

				String inExcel = objForm.getToExcel();
				if ("true".equals(inExcel)) {
					pagingSorting.setPagingSorting(false, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "displayReportInExcel";
				} else {
					pagingSorting.setPagingSorting(true, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "displayNetworkLocsReport";
				}

				pagingSorting.setDefaultifNotPresent("LOCATION_CODE",
						PagingSorting.increment, 1);
				objDto.setPagingSorting(pagingSorting);

				locsList = objService.viewNetworkLocsList(objDto);

				if ("true".equals(inExcel)) {
					objForm.setNetworkLocsExcelList(locsList);
					request.setAttribute("networkLocsExcelList", locsList);
				} else {
					request.setAttribute("customerList", locsList);
					objForm.setCustomerList(locsList);
				}
			}
		} catch (Exception ex) {
			return mapping.findForward(Messages
					.getMessageValue("errorGlobalForward"));
		}

		return mapping.findForward(forwardMapping);
	}

	public ActionForward getCustomerLocationReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ActionForward forward = new ActionForward();
		ReportsBean formBean = (ReportsBean) form;
		ActionMessages messages = new ActionMessages();
		ArrayList<LocationDetailDto> listAccount = new ArrayList<LocationDetailDto>();
		ArrayList<LocationDetailDto> clList = new ArrayList<LocationDetailDto>();

		LocationDetailDto objDto = new LocationDetailDto();
		String userId,interfaceId;
		try {
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			interfaceId=(String) request.getParameter("interfaceId");
			userId=objUserDto.getUserId();
			System.out.println(userId+" "+interfaceId);
			PagingSorting pagingSorting = formBean.getPagingSorting();
			pagingSorting.setPagingSorting(true, true);
			pagingSorting.setDefaultifNotPresent("accountId",
					PagingSorting.increment, 1);
			objDto.setPagingSorting(pagingSorting);

			request.setAttribute("customerList", clList);
			formBean.setCustomerList(clList);
			formBean.setList(listAccount);
			request.setAttribute("userId",objUserDto.getUserId());
			forward = mapping.findForward("displayCustomerLocationReport");
		} catch (Exception e) {
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
			e.printStackTrace();
		}
		return forward;
	}

	public ActionForward viewCustomerLocationList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String forwardMapping = "";
		ArrayList<LocationDetailDto> objUserList = new ArrayList<LocationDetailDto>();
		ReportsModel objService = new ReportsModel();
		ReportsBean objForm = (ReportsBean) form;
		LocationDetailDto objDto = new LocationDetailDto();
		String userId,interfaceId;
		try {
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			interfaceId=(String) request.getParameter("interfaceId");
			userId=objUserDto.getUserId();
			System.out.println(userId+" "+interfaceId);
			// Validate form fields

			ActionMessages messages = new ActionMessages();

			String accId = objForm.getAccountIdStr();
			String locId = objForm.getLocationIdStr();
			String locName = objForm.getLocationNameStr();
			String accountName = objForm.getAccountNameStr();
			ArrayList errors = new ArrayList();

			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"A/C No", accId, 18),
					Utility.generateCSV(Utility.CASE_MAXLENGTH,
							Utility.CASE_DIGITS_ONLY)).appendToAndRetNewErrs(
					errors);

			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"A/C Name", accountName, 200),
					Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS,
							Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
					errors);

			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"Location Code ", locId, 18),
					Utility.generateCSV(Utility.CASE_MAXLENGTH,
							Utility.CASE_DIGITS_ONLY)).appendToAndRetNewErrs(
					errors);

			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"Location Name", locName, 200),
					Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS,
							Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
					errors);

			if (errors != null && errors.size() > 0)// During Server Side
			// Validation
			{
				request.setAttribute("customerList", objUserList);
				objForm.setCustomerList(objUserList);
				request.setAttribute("validation_errors", errors);
				saveMessages(request, messages);
				request.setAttribute("userId",objUserDto.getUserId());
				return mapping.findForward("displayCustomerLocationReport");
			}
			// ******* Validation Ends Here
			// *************************************
			else {
				objDto.setAccountID(accId);
				objDto.setAccountName(accountName);
				objDto.setLocationId(locId);
				objDto.setLocationName(locName);

				PagingSorting pagingSorting = objForm.getPagingSorting();

				String inExcel = objForm.getToExcel();
				if ("true".equals(inExcel)) {
					pagingSorting.setPagingSorting(false, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "displayReportInExcel";
				} else {
					pagingSorting.setPagingSorting(true, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "displayCustomerLocationReport";
				}

				pagingSorting.setDefaultifNotPresent("accountId",
						PagingSorting.increment, 1);
				objDto.setPagingSorting(pagingSorting);

				objUserList = objService.viewCustomerLocationList(objDto);

				if ("true".equals(inExcel)) {
					objForm.setBcpExcelList(objUserList);
					request.setAttribute("clExcelList", objUserList);
				} else {
					request.setAttribute("customerList", objUserList);
					objForm.setCustomerList(objUserList);
				}
			}
		} catch (Exception ex) {
			return mapping.findForward(Messages
					.getMessageValue("errorGlobalForward"));
		}

		return mapping.findForward(forwardMapping);

	}

	public ActionForward getContactReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ActionForward forward = new ActionForward();
		ReportsBean formBean = (ReportsBean) form;
		ActionMessages messages = new ActionMessages();
		ArrayList<NewOrderDto> listAccount = new ArrayList<NewOrderDto>();
		ArrayList<NewOrderDto> contactList = new ArrayList<NewOrderDto>();

		NewOrderDto objDto = new NewOrderDto();
		String userId,interfaceId;
		try {
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			interfaceId=(String) request.getParameter("interfaceId");
			userId=objUserDto.getUserId();
			System.out.println(userId+" "+interfaceId);

			PagingSorting pagingSorting = formBean.getPagingSorting();
			pagingSorting.setPagingSorting(true, true);
			pagingSorting.setDefaultifNotPresent("accountId",
					PagingSorting.increment, 1);
			objDto.setPagingSorting(pagingSorting);

			request.setAttribute("customerList", contactList);
			formBean.setCustomerList(contactList);
			formBean.setList(listAccount);
			request.setAttribute("userId",objUserDto.getUserId());
			forward = mapping.findForward("displayContactReport");
		} catch (Exception e) {
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
			e.printStackTrace();
		}
		return forward;
	}

	public ActionForward viewContactList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String forwardMapping = "";
		ArrayList<NewOrderDto> objUserList = new ArrayList<NewOrderDto>();
		ReportsModel objService = new ReportsModel();
		ReportsBean objForm = (ReportsBean) form;
		NewOrderDto objDto = new NewOrderDto();
		String userId,interfaceId;
		try {
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			interfaceId=(String) request.getParameter("interfaceId");
			userId=objUserDto.getUserId();
			System.out.println(userId+" "+interfaceId);
			// Validate form fields
			ActionMessages messages = new ActionMessages();

			String accId = objForm.getAccountIdStr();
			String contactType = objForm.getContactTypeStr();
			contactType = (contactType != null) ? contactType.trim() : contactType;
			String contactName = objForm.getContactNameStr();
			contactName = (contactName != null) ? contactName.trim() : contactName;
			String accountName = objForm.getAccountNameStr();
			accountName = (accountName != null) ? accountName.trim() : accountName;
			String orderNo = objForm.getOrderNoStr();
			ArrayList errors = new ArrayList();

			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"A/C No", accId, 18),
					Utility.generateCSV(Utility.CASE_MAXLENGTH,
							Utility.CASE_DIGITS_ONLY)).appendToAndRetNewErrs(
					errors);

			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"Order No", orderNo, 18),
					Utility.generateCSV(Utility.CASE_MAXLENGTH,
							Utility.CASE_DIGITS_ONLY)).appendToAndRetNewErrs(
					errors);

			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"A/C Name", accountName, 200),
					Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS,
							Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
					errors);

			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"Contact Name", contactName, 200),
					Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS,
							Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
					errors);

			if (errors != null && errors.size() > 0)// During Server Side
			// Validation
			{
				request.setAttribute("customerList", objUserList);
				objForm.setCustomerList(objUserList);
				request.setAttribute("validation_errors", errors);
				saveMessages(request, messages);
				request.setAttribute("userId",objUserDto.getUserId());
				return mapping.findForward("displayContactReport");
			}
			// ******* Validation Ends Here
			// *************************************
			else {

				objDto.setAccountName(accountName);
				objDto.setContactType(contactType);
				objDto.setContactName(contactName);
				if (null == orderNo || "".equals(orderNo)) {
					objDto.setOrderNumber(0);
				} else {
					objDto.setOrderNumber((new Integer(orderNo)).intValue());
				}
				if (null == accId || "".equals(accId)) {
					objDto.setAccountID(0);

				} else {
					objDto.setAccountID((new Integer(accId)).intValue());
				}

				PagingSorting pagingSorting = objForm.getPagingSorting();

				String inExcel = objForm.getToExcel();
				if ("true".equals(inExcel)) {
					pagingSorting.setPagingSorting(false, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "displayReportInExcel";
				} else {
					pagingSorting.setPagingSorting(true, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "displayContactReport";
				}

				pagingSorting.setDefaultifNotPresent("accountId",
						PagingSorting.increment, 1);
				objDto.setPagingSorting(pagingSorting);

				objUserList = objService.viewContactList(objDto);

				if ("true".equals(inExcel)) {
					objForm.setBcpExcelList(objUserList);
					request.setAttribute("contactExcelList", objUserList);
				} else {
					request.setAttribute("customerList", objUserList);
					objForm.setCustomerList(objUserList);
				}
			}
		} catch (Exception ex) {
			return mapping.findForward(Messages
					.getMessageValue("errorGlobalForward"));
		}

		return mapping.findForward(forwardMapping);

	}

	public ActionForward getDispatchAddressReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ActionForward forward = new ActionForward();
		ReportsBean formBean = (ReportsBean) form;
		ActionMessages messages = new ActionMessages();

		ArrayList<DispatchAddressDto> listAccount = new ArrayList<DispatchAddressDto>();
		ArrayList<DispatchAddressDto> objUserList = new ArrayList<DispatchAddressDto>();
		DispatchAddressDto objDto = new DispatchAddressDto();
		String userId,interfaceId;
		try {
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				interfaceId=(String) request.getParameter("interfaceId");
				userId=objUserDto.getUserId();
				System.out.println(userId+" "+interfaceId);
			PagingSorting pagingSorting = formBean.getPagingSorting();
			pagingSorting.setPagingSorting(true, true);
			pagingSorting.setDefaultifNotPresent("accountId",
					PagingSorting.increment, 1);
			objDto.setPagingSorting(pagingSorting);

			request.setAttribute("customerList", objUserList);
			formBean.setCustomerList(objUserList);
			formBean.setList(listAccount);

			request.setAttribute("userId",objUserDto.getUserId());
			forward = mapping.findForward("displayDispatchAddressReport");

		} catch (Exception e) {
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
			e.printStackTrace();
		}
		return forward;
	}

	public ActionForward getTelemediaOrderReport(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forwardMapping = "";		
		ArrayList<TelemediaOrderReportDTO> listSearchDetails = new ArrayList<TelemediaOrderReportDTO>();
		ReportsModel objService = new ReportsModel();
		ReportsBean objForm = (ReportsBean) form;
		TelemediaOrderReportDTO objDto = new TelemediaOrderReportDTO();
		int isUsage =0;
		String userId,interfaceId;
		try {
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			interfaceId=(String) request.getParameter("interfaceId");
			userId=objUserDto.getUserId();
			System.out.println(userId+" "+interfaceId);
			Utility.setFileDumpParams(request,objForm);
			if(request.getParameter("usage") != null)
				isUsage=Integer.parseInt(request.getParameter("usage"));
			else 
			{
				isUsage=objForm.getIsUsage();
			}
				ActionMessages messages = new ActionMessages();
				objDto.setFromCopcApprovedDate(objForm.getFromOrderDate());
				objDto.setToCopcApprovedDate(objForm.getToOrderDate());
				objDto.setIsUsage(isUsage);
				PagingSorting pagingSorting = objForm.getPagingSorting();
				String inExcel = objForm.getToExcel();
				if ("true".equals(inExcel)) 
				{
					pagingSorting.setPagingSorting(false, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "displayReportInExcelExt";
				}
				else 
				{
					pagingSorting.setPagingSorting(true, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "displayTelemediaOrderReport";
				}
				pagingSorting.setDefaultifNotPresent("ORDERNO",
						PagingSorting.increment, 1);
				objDto.setPagingSorting(pagingSorting);
				String fromPageSubmit=request.getParameter("fromPageSubmit");				
				request.setAttribute("fromPageSubmit", fromPageSubmit);
				request.setAttribute("isUsage", isUsage);
				if("1".equals(fromPageSubmit))
				{{
					//No of records limit : start
					if ("true".equals(inExcel)) {
						pagingSorting.setPagingSorting(true, true);
						pagingSorting.setStartRecordId(1);
						pagingSorting.setEndRecordIdForNoOfRecordsCount();
						listSearchDetails = objService.getTelemediaOrderList(objDto);//***************CHANGE HERE*****************
						if(listSearchDetails!=null && objDto.getPagingSorting().getRecordCount()>ApplicationFlags.ReportsExcelMaxSize)
						{	
							forwardMapping = "displayTelemediaOrderReport";
							objForm.setReportProcessForLimit("1");
							objForm.setReportCurrentCount(String.valueOf(objDto.getPagingSorting().getRecordCount()));
							objDto.getPagingSorting().setRecordCount(0);
							objDto.getPagingSorting().setPageNumber(0);
							listSearchDetails=new ArrayList<TelemediaOrderReportDTO>();
						}else if (listSearchDetails!=null && objDto.getPagingSorting().getRecordCount()<=ApplicationFlags.ReportsExcelMaxSize){
							pagingSorting.setPagingSorting(false, true);
							listSearchDetails = objService.getTelemediaOrderList(objDto);
						}else{
							listSearchDetails=null;
						}
					}else {
				listSearchDetails = objService.getTelemediaOrderList(objDto);
				}}
				if ("true".equals(inExcel)) 
				{
					objForm.setBcpExcelList(listSearchDetails);
					request.setAttribute("telemediaOrderExcelList", listSearchDetails);
				}
				else 
				{
					request.setAttribute("telemediaOrderReportList", listSearchDetails);
					objForm.setCustomerList(listSearchDetails);
				}
				}}
		catch (Exception ex) {
			return mapping.findForward(Messages
					.getMessageValue("errorGlobalForward"));
		}
		return mapping.findForward(forwardMapping);
	}
	public ActionForward viewDispatchAddressList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String forwardMapping = "";
		ArrayList<DispatchAddressDto> objUserList = new ArrayList<DispatchAddressDto>();
		ReportsModel objService = new ReportsModel();
		ReportsBean objForm = (ReportsBean) form;
		DispatchAddressDto objDto = new DispatchAddressDto();
		String userId,interfaceId;
		try {
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				interfaceId=(String) request.getParameter("interfaceId");
				userId=objUserDto.getUserId();
				System.out.println(userId+" "+interfaceId);
			// Validate form fields

			ActionMessages messages = new ActionMessages();
			String accId = objForm.getCrmAccountNo();
			String accountName = objForm.getAccountNameStr();
			accountName = (accountName != null) ? accountName.trim() : accountName;
			String dispatchIdStr = objForm.getDispatchAddressIdStr();
			String dispatchNameStr = objForm.getDispatchAddressName();
			dispatchNameStr = (dispatchNameStr != null) ? dispatchNameStr.trim() : dispatchNameStr;
			ArrayList errors = new ArrayList();

			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"A/C No", accId, 18),
					Utility.generateCSV(Utility.CASE_MAXLENGTH,
							Utility.CASE_DIGITS_ONLY)).appendToAndRetNewErrs(
					errors);

			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"A/C Name", accountName, 200),
					Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS,
							Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
					errors);

			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"Dispatch Address Code ", dispatchIdStr, 18),
					Utility.generateCSV(Utility.CASE_MAXLENGTH,
							Utility.CASE_DIGITS_ONLY)).appendToAndRetNewErrs(
					errors);

			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"Dispatch Address Name", dispatchNameStr, 200),
					Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS,
							Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
					errors);

			if (errors != null && errors.size() > 0)// During Server Side
			// Validation
			{
				request.setAttribute("customerList", objUserList);
				objForm.setCustomerList(objUserList);
				request.setAttribute("validation_errors", errors);
				saveMessages(request, messages);
				request.setAttribute("userId",objUserDto.getUserId());
				return mapping.findForward("displayDispatchAddressReport");
			}
			// ******* Validation Ends Here
			// *************************************
			else {
				objDto.setAccountID(accId);
				objDto.setAccountName(accountName);
				objDto.setDispatchAddressId(dispatchIdStr);
				objDto.setSearchDispatchAddress(dispatchNameStr);

				PagingSorting pagingSorting = objForm.getPagingSorting();

				String inExcel = objForm.getToExcel();
				if ("true".equals(inExcel)) {
					pagingSorting.setPagingSorting(false, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "displayReportInExcel";
				} else {
					pagingSorting.setPagingSorting(true, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "displayDispatchAddressReport";
				}

				pagingSorting.setDefaultifNotPresent("accountId",
						PagingSorting.increment, 1);
				objDto.setPagingSorting(pagingSorting);

				objUserList = objService.viewDispatchAddressList(objDto);

				if ("true".equals(inExcel)) {
					objForm.setDispatchAddressExcelList(objUserList);
					request.setAttribute("dispatchAddressExcelList",
							objUserList);
				} else {
					request.setAttribute("customerList", objUserList);
					objForm.setCustomerList(objUserList);
				}
			}
		} catch (Exception ex) {
			return mapping.findForward(Messages
					.getMessageValue("errorGlobalForward"));
		}

		return mapping.findForward(forwardMapping);

	}

	public ActionForward getOrderStatusReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ActionForward forward = new ActionForward();
		ReportsBean formBean = (ReportsBean) form;
		ActionMessages messages = new ActionMessages();

		ArrayList<NewOrderDto> listAccount = new ArrayList<NewOrderDto>();
		ArrayList<NewOrderDto> orderList = new ArrayList<NewOrderDto>();

		NewOrderDto objDto = new NewOrderDto();
		String userId,interfaceId;

		try {
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			interfaceId=(String) request.getParameter("interfaceId");
			userId=objUserDto.getUserId();
			System.out.println(userId+" "+interfaceId);
			PagingSorting pagingSorting = formBean.getPagingSorting();
			pagingSorting.setPagingSorting(true, true);
			pagingSorting.setDefaultifNotPresent("accountId",
					PagingSorting.increment, 1);
			objDto.setPagingSorting(pagingSorting);

			request.setAttribute("customerList", orderList);
			formBean.setCustomerList(orderList);
			formBean.setList(listAccount);
			request.setAttribute("userId",objUserDto.getUserId());
			forward = mapping.findForward("displayOrderReport");
		} catch (Exception e) {
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
			e.printStackTrace();
		}
		return forward;
	}

	public ActionForward viewOrderStatusList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String forwardMapping = "";
		ArrayList<NewOrderDto> orderList = new ArrayList<NewOrderDto>();
		ReportsModel objService = new ReportsModel();
		ReportsBean objForm = (ReportsBean) form;
		NewOrderDto objDto = new NewOrderDto();
		String userId,interfaceId;
		try {
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			interfaceId=(String) request.getParameter("interfaceId");
			userId=objUserDto.getUserId();
			System.out.println(userId+" "+interfaceId);
			// Validate form fields
			ActionMessages messages = new ActionMessages();
			String accountIdStr = objForm.getCrmAccountNo();
			String accountNameStr = objForm.getAccountNameStr();
			String orderNo = objForm.getOrderNoStr();
			String orderStatus = objForm.getOrderStatusStr();
			String toDate = objForm.getToDate();
			String fromDate = objForm.getFromDate();

			ArrayList errors = new ArrayList();

			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"A/C No", accountIdStr, 18),
					Utility.generateCSV(Utility.CASE_MAXLENGTH,
							Utility.CASE_DIGITS_ONLY)).appendToAndRetNewErrs(
					errors);

			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"A/C Name", accountNameStr, 200),
					Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS,
							Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
					errors);

			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"Order No ", orderNo, 18),
					Utility.generateCSV(Utility.CASE_MAXLENGTH,
							Utility.CASE_DIGITS_ONLY)).appendToAndRetNewErrs(
					errors);
		
			if (errors != null && errors.size() > 0)// During Server Side
			// Validation
			{
				request.setAttribute("customerList", orderList);
				objForm.setCustomerList(orderList);
				request.setAttribute("validation_errors", errors);
				saveMessages(request, messages);
				request.setAttribute("userId",objUserDto.getUserId());
				return mapping.findForward("displayOrderReport");
			}
			// ******* Validation Ends Here
			// *************************************
			else {
				
				objDto.setAccountName(accountNameStr);
				objDto.setOrderType(orderStatus);
				objDto.setToDate(toDate);
				objDto.setFromDate(fromDate);
				if (null == orderNo || "".equals(orderNo)) {
					objDto.setOrderNumber(0);
				} else {
					objDto.setOrderNumber((new Integer(orderNo)).intValue());
				}
				if (null == accountIdStr || "".equals(accountIdStr)) {
					objDto.setAccountID(0);
				} else {
					objDto.setAccountID(new Integer(accountIdStr).intValue());
				}

				PagingSorting pagingSorting = objForm.getPagingSorting();

				String inExcel = objForm.getToExcel();
				if ("true".equals(inExcel)) {
					pagingSorting.setPagingSorting(false, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "displayReportInExcel";
				} else {
					pagingSorting.setPagingSorting(true, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "displayOrderReport";
				}

				pagingSorting.setDefaultifNotPresent("accountId",
						PagingSorting.increment, 1);
				objDto.setPagingSorting(pagingSorting);

				orderList = objService.viewOrderStatusList(objDto);

				if ("true".equals(inExcel)) {
					objForm.setBcpExcelList(orderList);
					request.setAttribute("orderExcelList", orderList);
				} else {
					request.setAttribute("customerList", orderList);
					objForm.setCustomerList(orderList);
				}
			}
		} catch (Exception ex) {
			return mapping.findForward(Messages
					.getMessageValue("errorGlobalForward"));
		}

		return mapping.findForward(forwardMapping);
	}
	
	
	public ActionForward fetchStatus(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		
		ActionMessages messages = new ActionMessages();
		try
		{
		
			forward = mapping.findForward("DisplaySelectOrderStatus");
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		return forward;
	}
	public ActionForward getMastersList(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		ActionMessages messages = new ActionMessages();
		ReportsBean formBean = (ReportsBean)form;
		ArrayList<ReportsDto> historyList = new ArrayList<ReportsDto>();
		//ReportsDto objDto = new ReportsDto();
		String userId,interfaceId;
		try
		{
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			interfaceId=(String) request.getParameter("interfaceId");
			userId=objUserDto.getUserId();
			System.out.println(userId+" "+interfaceId);
			PagingSorting pagingSorting = formBean.getPagingSorting();
			pagingSorting.setPagingSorting(true, true);
			pagingSorting.setDefaultifNotPresent("masterName",
					PagingSorting.increment, 1);
			//objDto.setPagingSorting(pagingSorting);
			request.setAttribute("masterHistoryList", historyList);
			request.setAttribute("userId",objUserDto.getUserId());
			forward = mapping.findForward("DisplayMasterReports");
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		return forward;
	}
	public ActionForward viewMasterHistory(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		ArrayList<ReportsDto> historyList = new ArrayList<ReportsDto>();
		ReportsModel objModel = new ReportsModel();
		ReportsBean objForm = (ReportsBean) form;
		ReportsDto objDto = new ReportsDto();

		ActionMessages messages = new ActionMessages();

		try {
			String masterValue = objForm.getMasterValue();
			PagingSorting pagingSorting = objForm.getPagingSorting();

			String inExcel = objForm.getToExcel();
			if ("true".equals(inExcel)) {
				pagingSorting.setPagingSorting(false, true);
				forward = mapping.findForward("displayReportInExcel");
			} else {
				pagingSorting.setPagingSorting(true, true);
				forward = mapping.findForward("DisplayMasterReports");
			}

			pagingSorting.setDefaultifNotPresent("masterName",
					PagingSorting.increment, 1);
			objDto.setPagingSorting(pagingSorting);
			objDto.setMasterValue(masterValue);
			historyList = objModel.viewMasterHistory(objDto);
			request.setAttribute("masterHistoryList", historyList);

		} catch (Exception e) {
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
		}
		return forward;
	}
	public ActionForward viewOrderStageList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String forwardMapping = "";
		ArrayList<OrderStageReportDTO> orderList = new ArrayList<OrderStageReportDTO>();
		ReportsModel objService = new ReportsModel();
		ReportsBean objForm = (ReportsBean) form;
		OrderStageReportDTO objDto = new OrderStageReportDTO();
		String userId,interfaceId;
		try {
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				interfaceId=(String) request.getParameter("interfaceId");
				userId=objUserDto.getUserId();
				System.out.println(userId+" "+interfaceId);
				Utility.setFileDumpParams(request,objForm);//Check objForm 
				objDto.setOrderType(objForm.getOrderType());
				objDto.setToDate(objForm.getToDate());
				objDto.setFromDate(objForm.getFromDate());
				objDto.setOsp(objForm.getOsp());
				
				PagingSorting pagingSorting = objForm.getPagingSorting();

				String inExcel = objForm.getToExcel();
				if ("true".equals(inExcel)) 
				{
					pagingSorting.setPagingSorting(false, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "displayReportInExcelExt";
				}
				else 
				{
					pagingSorting.setPagingSorting(true, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "displayOrderStageReport";
				}

				pagingSorting.setDefaultifNotPresent("ORDERNO",
						PagingSorting.increment, 1);
				objDto.setPagingSorting(pagingSorting);
				/*Vijay.  */
				String fromPageSubmit=request.getParameter("fromPageSubmit");
				request.setAttribute("fromPageSubmit", fromPageSubmit);
				if("1".equals(fromPageSubmit))
				{if ("true".equals(inExcel)) {
					pagingSorting.setPagingSorting(true, true);
					pagingSorting.setStartRecordId(1);
					pagingSorting.setEndRecordIdForNoOfRecordsCount();
					orderList = objService.viewOrderStageList(objDto);//***************CHANGE HERE*****************
					if(orderList!=null && objDto.getPagingSorting().getRecordCount()>ApplicationFlags.ReportsExcelMaxSize)
					{	
						forwardMapping = "displayOrderStageReport";//***************CHANGE HERE*****************
						objForm.setReportProcessForLimit("1");
						objForm.setReportCurrentCount(String.valueOf(objDto.getPagingSorting().getRecordCount()));
						objDto.getPagingSorting().setRecordCount(0);
						objDto.getPagingSorting().setPageNumber(0);
						orderList=new ArrayList<OrderStageReportDTO>();
					}else if (orderList!=null && objDto.getPagingSorting().getRecordCount()<=ApplicationFlags.ReportsExcelMaxSize){
						pagingSorting.setPagingSorting(false, true);
						orderList = objService.viewOrderStageList(objDto);
					}else{
						orderList=null;
					}
				}else {	
					orderList = objService.viewOrderStageList(objDto);
				}	}
				/*Vijay. end */
				
				if ("true".equals(inExcel)) 
				{
					objForm.setBcpExcelList(orderList);
					request.setAttribute("orderStageExcelList", orderList);
				}
				else 
				{
					request.setAttribute("orderStageList", orderList);
					objForm.setCustomerList(orderList);
				}
		} 
		catch (Exception ex) {
			return mapping.findForward(Messages
					.getMessageValue("errorGlobalForward"));
		}

		return mapping.findForward(forwardMapping);
	}

	public ActionForward viewPerformanceDetailReport(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		String forwardMapping = "";
		ArrayList<PerformanceDetailReportDTO> listSearchDetails = new ArrayList<PerformanceDetailReportDTO>();
		ReportsModel objService = new ReportsModel();
		ReportsBean objForm = (ReportsBean) form;
		PerformanceDetailReportDTO objDto = new PerformanceDetailReportDTO();
		String userId,interfaceId;
		try {
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				interfaceId=(String) request.getParameter("interfaceId");
				userId=objUserDto.getUserId();
				System.out.println(userId+" "+interfaceId);
			Utility.setFileDumpParams(request,objForm);//Check objForm 

				ActionMessages messages = new ActionMessages();
				objDto.setOrderType(objForm.getOrderType());
				objDto.setToDate(objForm.getToDate());
				objDto.setFromDate(objForm.getFromDate());
				objDto.setToOrderNo(objForm.getToOrderNo());
				objDto.setFromOrderNo(objForm.getFromOrderNo());
				objDto.setToAccountNo(objForm.getToAccountNo());
				objDto.setFromAccountNo(objForm.getFromAccountNo());
				objDto.setOsp(objForm.getOsp());
				PagingSorting pagingSorting = objForm.getPagingSorting();

				String inExcel = objForm.getToExcel();
				if ("true".equals(inExcel)) 
				{
					pagingSorting.setPagingSorting(false, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "displayReportInExcelExt";
				}
				else 
				{
					pagingSorting.setPagingSorting(true, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "displayPerformanceDetailReport";
				}

				pagingSorting.setDefaultifNotPresent("ORDERNO",
						PagingSorting.increment, 1);
				objDto.setPagingSorting(pagingSorting);

				String fromPageSubmit=request.getParameter("fromPageSubmit");				
				request.setAttribute("fromPageSubmit", fromPageSubmit);
				if("1".equals(fromPageSubmit))
				{//No of records limit : start
					if ("true".equals(inExcel)) {
						pagingSorting.setPagingSorting(true, true);
						pagingSorting.setStartRecordId(1);
						pagingSorting.setEndRecordIdForNoOfRecordsCount();
						listSearchDetails = objService.viewPerformanceDetailList(objDto);
						if(listSearchDetails!=null && objDto.getPagingSorting().getRecordCount()>ApplicationFlags.ReportsExcelMaxSize)
						{	
							forwardMapping = "displayPerformanceDetailReport";
							objForm.setReportProcessForLimit("1");
							objForm.setReportCurrentCount(String.valueOf(objDto.getPagingSorting().getRecordCount()));
							objDto.getPagingSorting().setRecordCount(0);
							objDto.getPagingSorting().setPageNumber(0);
							listSearchDetails=new ArrayList<PerformanceDetailReportDTO>();
						}else if (listSearchDetails!=null && objDto.getPagingSorting().getRecordCount()<=ApplicationFlags.ReportsExcelMaxSize){
							pagingSorting.setPagingSorting(false, true);
							listSearchDetails = objService.viewPerformanceDetailList(objDto);
						}else{
							listSearchDetails=null;
						}
					}else {
					//No of records limit : end
					listSearchDetails = objService.viewPerformanceDetailList(objDto);
				}
		}

				if ("true".equals(inExcel)) 
				{
					objForm.setBcpExcelList(listSearchDetails);
					request.setAttribute("performanceDetailExcelList", listSearchDetails);
				}
				else 
				{
					request.setAttribute("performanceDetailList", listSearchDetails);
					objForm.setCustomerList(listSearchDetails);
				}
		} 
		catch (Exception ex) {
			return mapping.findForward(Messages
					.getMessageValue("errorGlobalForward"));
		}

		return mapping.findForward(forwardMapping);
	}

	//	Ramana
	public ActionForward viewPerformanceList(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		String forwardMapping = "";
		ArrayList<PerformanceReportDTO> listSearchDetails = new ArrayList<PerformanceReportDTO>();
		ReportsModel objService = new ReportsModel();
		ReportsBean objForm = (ReportsBean) form;
		PerformanceReportDTO objDto = new PerformanceReportDTO();
		String userId,interfaceId;
		try {
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				interfaceId=(String) request.getParameter("interfaceId");
				userId=objUserDto.getUserId();
				System.out.println(userId+" "+interfaceId);
			Utility.setFileDumpParams(request,objForm);//Check objForm 
				ActionMessages messages = new ActionMessages();
				objDto.setOrderType(objForm.getOrderType());
				objDto.setToDate(objForm.getToDate());
				objDto.setFromDate(objForm.getFromDate());
				objDto.setToOrderNo(objForm.getToOrderNo());
				objDto.setFromOrderNo(objForm.getFromOrderNo());
				objDto.setToAccountNo(objForm.getToAccountNo());
				objDto.setFromAccountNo(objForm.getFromAccountNo());
				objDto.setOsp(objForm.getOsp());
				PagingSorting pagingSorting = objForm.getPagingSorting();

				String inExcel = objForm.getToExcel();
				if ("true".equals(inExcel)) 
				{
					pagingSorting.setPagingSorting(false, true);
					//forwardMapping = "displayReportInExcel";
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "displayReportInExcelExt";
					
				}
				else 
				{
					pagingSorting.setPagingSorting(true, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "displayPerformanceReport";
				}

				pagingSorting.setDefaultifNotPresent("ORDERNO",
						PagingSorting.increment, 1);
				objDto.setPagingSorting(pagingSorting);

				String fromPageSubmit=request.getParameter("fromPageSubmit");				
					request.setAttribute("fromPageSubmit", fromPageSubmit);
					if("1".equals(fromPageSubmit))
					{

						//No of records limit : start
						if ("true".equals(inExcel)) {
							pagingSorting.setPagingSorting(true, true);
							pagingSorting.setStartRecordId(1);
							pagingSorting.setEndRecordIdForNoOfRecordsCount();
							listSearchDetails = objService.viewPerformanceList(objDto);
							if(listSearchDetails!=null && objDto.getPagingSorting().getRecordCount()>ApplicationFlags.ReportsExcelMaxSize)
							{	
								forwardMapping = "displayPerformanceReport";
								objForm.setReportProcessForLimit("1");
								objForm.setReportCurrentCount(String.valueOf(objDto.getPagingSorting().getRecordCount()));
								objDto.getPagingSorting().setRecordCount(0);
								objDto.getPagingSorting().setPageNumber(0);
								listSearchDetails=new ArrayList<PerformanceReportDTO>();
							}else if (listSearchDetails!=null && objDto.getPagingSorting().getRecordCount()<=ApplicationFlags.ReportsExcelMaxSize){
								pagingSorting.setPagingSorting(false, true);
								listSearchDetails = objService.viewPerformanceList(objDto);
							}else{
								listSearchDetails=null;
							}
						}else {
						//No of records limit : end
						listSearchDetails = objService.viewPerformanceList(objDto);
					}
		}

				if ("true".equals(inExcel)) 
				{
					objForm.setBcpExcelList(listSearchDetails);
					request.setAttribute("performanceExcelList", listSearchDetails);
				}
				else 
				{
					request.setAttribute("performanceList", listSearchDetails);
					objForm.setCustomerList(listSearchDetails);
				}
		} 
		catch (Exception ex) {
			return mapping.findForward(Messages
					.getMessageValue("errorGlobalForward"));
		}

		return mapping.findForward(forwardMapping);
	}
	//Ramana
	//Created By Ashutosh
	public ActionForward viewPerformanceSummaryReport(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		String forwardMapping = "";
		ArrayList<PerformanceSummaryReportDTO> listSearchDetails = new ArrayList<PerformanceSummaryReportDTO>();
		ReportsModel objService = new ReportsModel();
		ReportsBean objForm = (ReportsBean) form;
		PerformanceSummaryReportDTO objDto = new PerformanceSummaryReportDTO();
		String userId,interfaceId;
		try {
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				interfaceId=(String) request.getParameter("interfaceId");
				userId=objUserDto.getUserId();
				System.out.println(userId+" "+interfaceId);
				ActionMessages messages = new ActionMessages();
				Utility.setFileDumpParams(request,objForm);
				objDto.setOrderType(objForm.getOrderType());
				objDto.setToDate(objForm.getToDate());
				objDto.setFromDate(objForm.getFromDate());
				objDto.setToOrderNo(objForm.getToOrderNo());
				objDto.setFromOrderNo(objForm.getFromOrderNo());
				objDto.setToAccountNo(objForm.getToAccountNo());
				objDto.setFromAccountNo(objForm.getFromAccountNo());
				objDto.setOsp(objForm.getOsp());
				PagingSorting pagingSorting = objForm.getPagingSorting();

				String inExcel = objForm.getToExcel();
				if ("true".equals(inExcel)) 
				{
					pagingSorting.setPagingSorting(false, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "displayReportInExcelExt";
				}
				else 
				{
					pagingSorting.setPagingSorting(true, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "displayPerformanceSummaryReport";
				}
				pagingSorting.setDefaultifNotPresent("ORDERNO",
						PagingSorting.increment, 1);
				objDto.setPagingSorting(pagingSorting);
				String fromPageSubmit=request.getParameter("fromPageSubmit");				
				request.setAttribute("fromPageSubmit", fromPageSubmit);
				if("1".equals(fromPageSubmit))
				{//No of records limit : start
					if ("true".equals(inExcel)) {
						pagingSorting.setPagingSorting(true, true);
						pagingSorting.setStartRecordId(1);
						pagingSorting.setEndRecordIdForNoOfRecordsCount();
						listSearchDetails = objService.viewPerformanceSummaryReport(objDto);//***************CHANGE HERE*****************
						if(listSearchDetails!=null && objDto.getPagingSorting().getRecordCount()>ApplicationFlags.ReportsExcelMaxSize)
						{	
							forwardMapping = "displayPerformanceSummaryReport";//***************CHANGE HERE*****************
							objForm.setReportProcessForLimit("1");
							objForm.setReportCurrentCount(String.valueOf(objDto.getPagingSorting().getRecordCount()));
							objDto.getPagingSorting().setRecordCount(0);
							objDto.getPagingSorting().setPageNumber(0);
							listSearchDetails=new ArrayList<PerformanceSummaryReportDTO>();
						}else if (listSearchDetails!=null && objDto.getPagingSorting().getRecordCount()<=ApplicationFlags.ReportsExcelMaxSize){
							pagingSorting.setPagingSorting(false, true);
							listSearchDetails = objService.viewPerformanceSummaryReport(objDto);
						}else{
							listSearchDetails=null;
						}
					}else {	
				listSearchDetails = objService.viewPerformanceSummaryReport(objDto);
				}}
				if ("true".equals(inExcel)) 
				{
					objForm.setBcpExcelList(listSearchDetails);
					request.setAttribute("performanceSummaryReportExcelList", listSearchDetails);
				}
				else 
				{
					request.setAttribute("performanceSummaryReportList", listSearchDetails);
					objForm.setCustomerList(listSearchDetails);
				}
		} 
		catch (Exception ex) {
			return mapping.findForward(Messages
					.getMessageValue("errorGlobalForward"));
		}
		return mapping.findForward(forwardMapping);
	}
	public ActionForward viewNonAppAppChangeOrderDetails(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		String forwardMapping = "";
		ArrayList<NonMigratedAPP_UNAPPNewOrderDetailsDTO> listSearchDetails = new ArrayList<NonMigratedAPP_UNAPPNewOrderDetailsDTO>();
		ReportsModel objService = new ReportsModel();
		ReportsBean objForm = (ReportsBean) form;
		NonMigratedAPP_UNAPPNewOrderDetailsDTO objDto = new NonMigratedAPP_UNAPPNewOrderDetailsDTO();
		String userId,interfaceId;
		try {
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				interfaceId=(String) request.getParameter("interfaceId");
				userId=objUserDto.getUserId();
				System.out.println(userId+" "+interfaceId);
			Utility.setFileDumpParams(request,objForm);//Check objForm 
				ActionMessages messages = new ActionMessages();
				objDto.setOrderType(objForm.getOrderType());
				objDto.setApprovalType(objForm.getApprovalType());
				objDto.setToOrderNo(objForm.getToOrderNo());
				objDto.setFromOrderNo(objForm.getFromOrderNo());
				objDto.setServiceOrderType(objForm.getServiceOrderType());
				objDto.setOrdermonth(objForm.getOrdermonth());
				objDto.setVerticalDetails(objForm.getVerticalDetails());
				objDto.setOrderyear(objForm.getOrderyear());
				PagingSorting pagingSorting = objForm.getPagingSorting();
				String inExcel = objForm.getToExcel();
				if ("true".equals(inExcel)) 
				{
					pagingSorting.setPagingSorting(false, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "displayReportInExcel";
				}
				else 
				{
					pagingSorting.setPagingSorting(true, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "displayNonAppAppChangeOrderDetails";
				}
				pagingSorting.setDefaultifNotPresent("ORDERNO",
						PagingSorting.increment, 1);
				objDto.setPagingSorting(pagingSorting);
				String fromPageSubmit=request.getParameter("fromPageSubmit");				
				request.setAttribute("fromPageSubmit", fromPageSubmit);
				if("1".equals(fromPageSubmit))
				{

					//No of records limit : start
					if ("true".equals(inExcel)) {
						pagingSorting.setPagingSorting(true, true);
						pagingSorting.setStartRecordId(1);
						pagingSorting.setEndRecordIdForNoOfRecordsCount();
						listSearchDetails = objService.viewNonMigAppUnappNewOrderDetails(objDto);
						if(listSearchDetails!=null && objDto.getPagingSorting().getRecordCount()>ApplicationFlags.ReportsExcelMaxSize)
						{	
							forwardMapping = "displayNonAppAppChangeOrderDetails";
							objForm.setReportProcessForLimit("1");
							objForm.setReportCurrentCount(String.valueOf(objDto.getPagingSorting().getRecordCount()));
							objDto.getPagingSorting().setRecordCount(0);
							objDto.getPagingSorting().setPageNumber(0);
							listSearchDetails=new ArrayList<NonMigratedAPP_UNAPPNewOrderDetailsDTO>();
						}else if (listSearchDetails!=null && objDto.getPagingSorting().getRecordCount()<=ApplicationFlags.ReportsExcelMaxSize){
							pagingSorting.setPagingSorting(false, true);
							listSearchDetails = objService.viewNonMigAppUnappNewOrderDetails(objDto);
						}else{
							listSearchDetails=null;
						}
					}else {
					//No of records limit : end
				listSearchDetails = objService.viewNonMigAppUnappNewOrderDetails(objDto);//this method is  use for both new and change Order.
				}	
		}
				if ("true".equals(inExcel)) 
				{
					objForm.setBcpExcelList(listSearchDetails);
					request.setAttribute("nonAppAppChangeOrderDetailsExcelList", listSearchDetails);
				}
				else 
				{
					request.setAttribute("nonAppAppChangeOrderDetailsList", listSearchDetails);
					objForm.setCustomerList(listSearchDetails);
				}
		} 
		catch (Exception ex) {
			return mapping.findForward(Messages
					.getMessageValue("errorGlobalForward"));
		}
		return mapping.findForward(forwardMapping);
	}
	public ActionForward viewNonMigAppUnappNewOrderDetails(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		String forwardMapping = "";
		ArrayList<NonMigratedAPP_UNAPPNewOrderDetailsDTO> listSearchDetails = new ArrayList<NonMigratedAPP_UNAPPNewOrderDetailsDTO>();
		ReportsModel objService = new ReportsModel();
		ReportsBean objForm = (ReportsBean) form;
		NonMigratedAPP_UNAPPNewOrderDetailsDTO objDto = new NonMigratedAPP_UNAPPNewOrderDetailsDTO();
		String userId,interfaceId;
		try {
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				interfaceId=(String) request.getParameter("interfaceId");
				userId=objUserDto.getUserId();
				System.out.println(userId+" "+interfaceId);
			
			Utility.setFileDumpParams(request,objForm);//Check objForm 
				ActionMessages messages = new ActionMessages();
				objDto.setOrderType(objForm.getOrderType());
				objDto.setApprovalType(objForm.getApprovalType());
				objDto.setToOrderNo(objForm.getToOrderNo());
				objDto.setFromOrderNo(objForm.getFromOrderNo());
				objDto.setOrdermonth(objForm.getOrdermonth());
				objDto.setOrderyear(objForm.getOrderyear());
				objDto.setServiceName(objForm.getServicename());				
				PagingSorting pagingSorting = objForm.getPagingSorting();
				String inExcel = objForm.getToExcel();
				if ("true".equals(inExcel)) 
				{
					pagingSorting.setPagingSorting(false, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "displayReportInExcel";
				}
				else 
				{
					pagingSorting.setPagingSorting(true, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "displayNonMigAppUnappNewOrderDetails";
				}
				pagingSorting.setDefaultifNotPresent("ORDERNO",
						PagingSorting.increment, 1);
				objDto.setPagingSorting(pagingSorting);
				String fromPageSubmit=request.getParameter("fromPageSubmit");				
				request.setAttribute("fromPageSubmit", fromPageSubmit);
				if("1".equals(fromPageSubmit))
				{

					//No of records limit : start
					if ("true".equals(inExcel)) {
						pagingSorting.setPagingSorting(true, true);
						pagingSorting.setStartRecordId(1);
						pagingSorting.setEndRecordIdForNoOfRecordsCount();
						listSearchDetails = objService.viewNonMigAppUnappNewOrderDetails(objDto);
						if(listSearchDetails!=null && objDto.getPagingSorting().getRecordCount()>ApplicationFlags.ReportsExcelMaxSize)
						{	
							forwardMapping = "displayNonMigAppUnappNewOrderDetails";
							objForm.setReportProcessForLimit("1");
							objForm.setReportCurrentCount(String.valueOf(objDto.getPagingSorting().getRecordCount()));
							objDto.getPagingSorting().setRecordCount(0);
							objDto.getPagingSorting().setPageNumber(0);
							listSearchDetails=new ArrayList<NonMigratedAPP_UNAPPNewOrderDetailsDTO>();
						}else if (listSearchDetails!=null && objDto.getPagingSorting().getRecordCount()<=ApplicationFlags.ReportsExcelMaxSize){
							pagingSorting.setPagingSorting(false, true);
							listSearchDetails = objService.viewNonMigAppUnappNewOrderDetails(objDto);
						}else{
							listSearchDetails=null;
						}
					}else {
					//No of records limit : end
					
				listSearchDetails = objService.viewNonMigAppUnappNewOrderDetails(objDto);
				}
		}
				if ("true".equals(inExcel)) 
				{
					objForm.setBcpExcelList(listSearchDetails);
					request.setAttribute("nonMigAppUnappNewOrderDetailsExcelList", listSearchDetails);
				}
				else 
				{
					request.setAttribute("nonMigAppUnappNewOrderDetailsList", listSearchDetails);
					objForm.setCustomerList(listSearchDetails);
				}
		} 
		catch (Exception ex) {
			return mapping.findForward(Messages
					.getMessageValue("errorGlobalForward"));
		}
		return mapping.findForward(forwardMapping);
	}
	public ActionForward viewStartChargeNotPushedInFx(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		String forwardMapping = "";
		ArrayList<StartChargeNotPushedInFXDTO> listSearchDetails = new ArrayList<StartChargeNotPushedInFXDTO>();
		ReportsModel objService = new ReportsModel();
		ReportsBean objForm = (ReportsBean) form;
		StartChargeNotPushedInFXDTO objDto = new StartChargeNotPushedInFXDTO();
		String userId,interfaceId;
		try {
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				interfaceId=(String) request.getParameter("interfaceId");
				userId=objUserDto.getUserId();
				System.out.println(userId+" "+interfaceId);
			Utility.setFileDumpParams(request,objForm);//Check objForm 
				ActionMessages messages = new ActionMessages();
				objDto.setOrderType(objForm.getOrderType());
				objDto.setToDate(objForm.getToDate());
				objDto.setFromDate(objForm.getFromDate());
				objDto.setToOrderNo(objForm.getToOrderNo());
				objDto.setFromOrderNo(objForm.getFromOrderNo());
				objDto.setVerticalDetails(objForm.getVerticalDetails());				
				
				PagingSorting pagingSorting = objForm.getPagingSorting();

				String inExcel = objForm.getToExcel();
				if ("true".equals(inExcel)) 
				{
					pagingSorting.setPagingSorting(false, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "displayReportInExcelExt";
				}
				else 
				{
					pagingSorting.setPagingSorting(true, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "displayStartChargeNotPushedInFx";
				}

				pagingSorting.setDefaultifNotPresent("ORDERNO",
						PagingSorting.increment, 1);
				objDto.setPagingSorting(pagingSorting);
				String fromPageSubmit=request.getParameter("fromPageSubmit");				
				request.setAttribute("fromPageSubmit", fromPageSubmit);
				if("1".equals(fromPageSubmit))
				{

					//No of records limit : start
					if ("true".equals(inExcel)) {
						pagingSorting.setPagingSorting(true, true);
						pagingSorting.setStartRecordId(1);
						pagingSorting.setEndRecordIdForNoOfRecordsCount();
						listSearchDetails = objService.viewStartChargeNotPushedInFx(objDto);
						if(listSearchDetails!=null && objDto.getPagingSorting().getRecordCount()>ApplicationFlags.ReportsExcelMaxSize)
						{	
							forwardMapping = "displayStartChargeNotPushedInFx";
							objForm.setReportProcessForLimit("1");
							objForm.setReportCurrentCount(String.valueOf(objDto.getPagingSorting().getRecordCount()));
							objDto.getPagingSorting().setRecordCount(0);
							objDto.getPagingSorting().setPageNumber(0);
							listSearchDetails=new ArrayList<StartChargeNotPushedInFXDTO>();
						}else if (listSearchDetails!=null && objDto.getPagingSorting().getRecordCount()<=ApplicationFlags.ReportsExcelMaxSize){
							pagingSorting.setPagingSorting(false, true);
							listSearchDetails = objService.viewStartChargeNotPushedInFx(objDto);
						}else{
							listSearchDetails=null;
						}
					}else {
					//No of records limit : end
				listSearchDetails = objService.viewStartChargeNotPushedInFx(objDto);
				}				
			}
				if ("true".equals(inExcel)) 
				{
					objForm.setBcpExcelList(listSearchDetails);
					request.setAttribute("startChargeNotPushedInFxExcelList", listSearchDetails);
				}
				else 
				{
					request.setAttribute("startChargeNotPushedInFxList", listSearchDetails);
					objForm.setCustomerList(listSearchDetails);
				}
		} 
		catch (Exception ex) {
			return mapping.findForward(Messages
					.getMessageValue("errorGlobalForward"));
		}

		return mapping.findForward(forwardMapping);
	}

	public ActionForward viewLogicalSIDataReport(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		String forwardMapping = "";
		ArrayList<LogicalSIDataReportDTO> listSearchDetails = new ArrayList<LogicalSIDataReportDTO>();
		ReportsModel objService = new ReportsModel();
		ReportsBean objForm = (ReportsBean) form;
		LogicalSIDataReportDTO objDto = new LogicalSIDataReportDTO();
		String userId,interfaceId;
		try {
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				interfaceId=(String) request.getParameter("interfaceId");
				userId=objUserDto.getUserId();
				System.out.println(userId+" "+interfaceId);
			Utility.setFileDumpParams(request,objForm);
				ActionMessages messages = new ActionMessages();
				objDto.setOrderType(objForm.getOrderType());
				objDto.setToDate(objForm.getToDate());
				objDto.setFromDate(objForm.getFromDate());
				objDto.setToOrderNo(objForm.getToOrderNo());
				objDto.setFromOrderNo(objForm.getFromOrderNo());
				objDto.setLogicalSINumber(objForm.getLogicalsi_no());
				PagingSorting pagingSorting = objForm.getPagingSorting();
				String inExcel = objForm.getToExcel();
				if ("true".equals(inExcel)) 
				{
					pagingSorting.setPagingSorting(false, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "displayReportInExcel";
				}
				else 
				{
					pagingSorting.setPagingSorting(true, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "displayLogicalSIDataReport";
				}
				pagingSorting.setDefaultifNotPresent("ORDERNO",
						PagingSorting.increment, 1);
				objDto.setPagingSorting(pagingSorting);
				String fromPageSubmit=request.getParameter("fromPageSubmit");				
				 request.setAttribute("fromPageSubmit", fromPageSubmit);
				if("1".equals(fromPageSubmit))
				{	//No of records limit : start
					if ("true".equals(inExcel)) {
						pagingSorting.setPagingSorting(true, true);
						pagingSorting.setStartRecordId(1);
						pagingSorting.setEndRecordIdForNoOfRecordsCount();
						listSearchDetails = objService.viewLogicalSIDataReport(objDto);//***************CHANGE HERE*****************
						if(listSearchDetails!=null && objDto.getPagingSorting().getRecordCount()>ApplicationFlags.ReportsExcelMaxSize)
						{	
							forwardMapping = "displayLogicalSIDataReport";//***************CHANGE HERE*****************
							objForm.setReportProcessForLimit("1");
							objForm.setReportCurrentCount(String.valueOf(objDto.getPagingSorting().getRecordCount()));
							objDto.getPagingSorting().setRecordCount(0);
							objDto.getPagingSorting().setPageNumber(0);
							listSearchDetails=new ArrayList<LogicalSIDataReportDTO>();
						}else if (listSearchDetails!=null && objDto.getPagingSorting().getRecordCount()<=ApplicationFlags.ReportsExcelMaxSize){
							pagingSorting.setPagingSorting(false, true);
							listSearchDetails = objService.viewLogicalSIDataReport(objDto);
						}else{
							listSearchDetails=null;
						}
					}else {
				listSearchDetails = objService.viewLogicalSIDataReport(objDto);
				}	}			
				if ("true".equals(inExcel)) 
				{
					objForm.setBcpExcelList(listSearchDetails);
					request.setAttribute("logicalSIDataReportExcelList", listSearchDetails);
				}
				else 
				{
					request.setAttribute("logicalSIDataReportList", listSearchDetails);
					objForm.setCustomerList(listSearchDetails);
				}
		} 
		catch (Exception ex) {
			return mapping.findForward(Messages
					.getMessageValue("errorGlobalForward"));
		}
		return mapping.findForward(forwardMapping);
	}
	public ActionForward viewBillingTriggerDoneButFailedInFX(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		String forwardMapping = "";
		ArrayList<BillingTriggerDoneButFailedInFXDTO> listSearchDetails = new ArrayList<BillingTriggerDoneButFailedInFXDTO>();
		ReportsModel objService = new ReportsModel();
		ReportsBean objForm = (ReportsBean) form;
		BillingTriggerDoneButFailedInFXDTO objDto = new BillingTriggerDoneButFailedInFXDTO();
		String userId,interfaceId;
		try {
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				interfaceId=(String) request.getParameter("interfaceId");
				userId=objUserDto.getUserId();
				System.out.println(userId+" "+interfaceId);
			Utility.setFileDumpParams(request,objForm);
				ActionMessages messages = new ActionMessages();
				objDto.setOrderType(objForm.getOrderType());
				objDto.setToDate(objForm.getToDate());
				objDto.setFromDate(objForm.getFromDate());
				objDto.setToOrderNo(objForm.getToOrderNo());
				objDto.setFromOrderNo(objForm.getFromOrderNo());
				objDto.setToAccountNo(objForm.getToAccountNo());
				objDto.setFromAccountNo(objForm.getFromAccountNo());
				
				PagingSorting pagingSorting = objForm.getPagingSorting();

				String inExcel = objForm.getToExcel();
				if ("true".equals(inExcel)) 
				{
					pagingSorting.setPagingSorting(false, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "displayReportInExcelExt";
				}
				else 
				{
					pagingSorting.setPagingSorting(true, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "displayBillingTriggerDoneButFailedInFX";
				}

				pagingSorting.setDefaultifNotPresent("ORDERNO",
						PagingSorting.increment, 1);
				objDto.setPagingSorting(pagingSorting);
				String fromPageSubmit=request.getParameter("fromPageSubmit");				
				request.setAttribute("fromPageSubmit", fromPageSubmit);
				if("1".equals(fromPageSubmit))
				{
					//No of records limit : start
					if ("true".equals(inExcel)) {
						pagingSorting.setPagingSorting(true, true);
						pagingSorting.setStartRecordId(1);
						pagingSorting.setEndRecordIdForNoOfRecordsCount();
						listSearchDetails = objService.viewBillingTriggerDoneButFailedInFX(objDto);//***************CHANGE HERE*****************
						if(listSearchDetails!=null && objDto.getPagingSorting().getRecordCount()>ApplicationFlags.ReportsExcelMaxSize)
						{	
							forwardMapping = "displayBillingTriggerDoneButFailedInFX";//***************CHANGE HERE*****************
							objForm.setReportProcessForLimit("1");
							objForm.setReportCurrentCount(String.valueOf(objDto.getPagingSorting().getRecordCount()));
							objDto.getPagingSorting().setRecordCount(0);
							objDto.getPagingSorting().setPageNumber(0);
							listSearchDetails=new ArrayList<BillingTriggerDoneButFailedInFXDTO>();
						}else if (listSearchDetails!=null && objDto.getPagingSorting().getRecordCount()<=ApplicationFlags.ReportsExcelMaxSize){
							pagingSorting.setPagingSorting(false, true);
							listSearchDetails = objService.viewBillingTriggerDoneButFailedInFX(objDto);
						}else{
							listSearchDetails=null;
						}
					}else {
				listSearchDetails = objService.viewBillingTriggerDoneButFailedInFX(objDto);
				}}
				if ("true".equals(inExcel)) 
				{
					objForm.setBcpExcelList(listSearchDetails);
					request.setAttribute("billingTriggerDoneButFailedInFXExcelList", listSearchDetails);
				}
				else 
				{
					request.setAttribute("billingTriggerDoneButFailedInFXList", listSearchDetails);
					objForm.setCustomerList(listSearchDetails);
				}
		} 
		catch (Exception ex) {
			return mapping.findForward(Messages
					.getMessageValue("errorGlobalForward"));
		}

		return mapping.findForward(forwardMapping);
	}
	
	public ActionForward viewPendingServicesReport(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		String forwardMapping = "";
		ArrayList<PendingServicesReportDTO> listSearchDetails = new ArrayList<PendingServicesReportDTO>();
		ReportsModel objService = new ReportsModel();
		ReportsBean objForm = (ReportsBean) form;
		PendingServicesReportDTO objDto = new PendingServicesReportDTO();
		String userId,interfaceId;
		try {
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			
			interfaceId=(String) request.getParameter("interfaceId");
			
			userId=objUserDto.getUserId();
			
			System.out.println(userId+" "+interfaceId);
			Utility.setFileDumpParams(request,objForm);
				ActionMessages messages = new ActionMessages();
				objDto.setOrderType(objForm.getOrderType());
				objDto.setToDate(objForm.getToDate());
				objDto.setFromDate(objForm.getFromDate());
				objDto.setToOrderNo(objForm.getToOrderNo());
				objDto.setFromOrderNo(objForm.getFromOrderNo());
				objDto.setToAccountNo(objForm.getToAccountNo());
				objDto.setFromAccountNo(objForm.getFromAccountNo());
				PagingSorting pagingSorting = objForm.getPagingSorting();
				String inExcel = objForm.getToExcel();
				if ("true".equals(inExcel)) 
				{
					request.setAttribute("userId",objUserDto.getUserId());
					pagingSorting.setPagingSorting(false, true);
					forwardMapping = "displayReportInExcel";
				}
				else 
				{
					request.setAttribute("userId",objUserDto.getUserId());
					pagingSorting.setPagingSorting(true, true);
					forwardMapping = "displayPendingServicesReport";
				}
				pagingSorting.setDefaultifNotPresent("ORDERNO",
						PagingSorting.increment, 1);
				objDto.setPagingSorting(pagingSorting);
				String fromPageSubmit=request.getParameter("fromPageSubmit");				
				request.setAttribute("fromPageSubmit", fromPageSubmit);
				if("1".equals(fromPageSubmit))
				{
					//No of records limit : start
					if ("true".equals(inExcel)) {
						pagingSorting.setPagingSorting(true, true);
						pagingSorting.setStartRecordId(1);
						pagingSorting.setEndRecordIdForNoOfRecordsCount();
						listSearchDetails = objService.viewPendingServicesReport(objDto);//***************CHANGE HERE*****************
						if(listSearchDetails!=null && objDto.getPagingSorting().getRecordCount()>ApplicationFlags.ReportsExcelMaxSize)
						{	
							forwardMapping = "displayPendingServicesReport";//***************CHANGE HERE*****************
							objForm.setReportProcessForLimit("1");
							objForm.setReportCurrentCount(String.valueOf(objDto.getPagingSorting().getRecordCount()));
							objDto.getPagingSorting().setRecordCount(0);
							objDto.getPagingSorting().setPageNumber(0);
							listSearchDetails=new ArrayList<PendingServicesReportDTO>();
						}else if (listSearchDetails!=null && objDto.getPagingSorting().getRecordCount()<=ApplicationFlags.ReportsExcelMaxSize){
							pagingSorting.setPagingSorting(false, true);
							listSearchDetails = objService.viewPendingServicesReport(objDto);
						}else{
							listSearchDetails=null;
						}
					}else {
					
				listSearchDetails = objService.viewPendingServicesReport(objDto);
				}}
				if ("true".equals(inExcel)) 
				{
					objForm.setBcpExcelList(listSearchDetails);
					request.setAttribute("pendingServicesReportExcelList", listSearchDetails);
				}
				else 
				{
					request.setAttribute("pendingServicesReportList", listSearchDetails);
					objForm.setCustomerList(listSearchDetails);
				}
		} 
		catch (Exception ex) {
			return mapping.findForward(Messages
					.getMessageValue("errorGlobalForward"));
		}
		return mapping.findForward(forwardMapping);
	}
	//END 
	public ActionForward viewActiveLineItemsList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String forwardMapping = "";
		String userId,interfaceId;		
		ArrayList<ActiveLineItemReportsDTO> orderList = new ArrayList<ActiveLineItemReportsDTO>();
		ReportsModel objService = new ReportsModel();
		ReportsBean objForm = (ReportsBean) form;
		//NewOrderDto objDto = new NewOrderDto();
		ActiveLineItemReportsDTO objDto=new ActiveLineItemReportsDTO();
		try {
			
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			interfaceId=(String) request.getParameter("interfaceId");
			userId=objUserDto.getUserId();
			System.out.println(userId+" "+interfaceId);
				Utility.setFileDumpParams(request,objForm);
			ArrayList errors = new ArrayList();
			objDto.setOrderType(objForm.getOrderType());
			objDto.setToDate(objForm.getToDate());
			objDto.setFromDate(objForm.getFromDate());
			objDto.setToOrderNo(objForm.getToOrderNo());
			objDto.setFromOrderNo(objForm.getFromOrderNo());
			objDto.setPartyNo(objForm.getPartyNo());
			objDto.setCustomerSegment(objForm.getCustomerSegment());
			objDto.setProductName(objForm.getProductName());
			PagingSorting pagingSorting = objForm.getPagingSorting();

				String inExcel = objForm.getToExcel();
				if ("true".equals(inExcel)) {
					pagingSorting.setPagingSorting(false, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "displayReportInExcelExt";
				} else {
					pagingSorting.setPagingSorting(true, true);
					request.setAttribute("userId",objUserDto.getUserId());
					request.setAttribute("product",objForm.getProductName());
					request.setAttribute("custSeg",objForm.getCustomerSegment());
					request.setAttribute("partyNo",objForm.getPartyNo());
					forwardMapping = "orderActiveLineItemsList";
				}

				pagingSorting.setDefaultifNotPresent("accountId",
						PagingSorting.increment, 1);
				objDto.setPagingSorting(pagingSorting);
				String fromPageSubmit=request.getParameter("fromPageSubmit");				
				request.setAttribute("fromPageSubmit", fromPageSubmit);
				if("1".equals(fromPageSubmit))
				{
					
					//No of records limit : start
					if ("true".equals(inExcel)) {
						pagingSorting.setPagingSorting(true, true);
						pagingSorting.setStartRecordId(1);
						pagingSorting.setEndRecordIdForNoOfRecordsCount();
						orderList = objService.viewActiveLineItemsList(objDto);
						if(orderList!=null && objDto.getPagingSorting().getRecordCount()>ApplicationFlags.ReportsExcelMaxSize)
						{	
							forwardMapping = "orderActiveLineItemsList";
							objForm.setReportProcessForLimit("1");
							objForm.setReportCurrentCount(String.valueOf(objDto.getPagingSorting().getRecordCount()));
							objDto.getPagingSorting().setRecordCount(0);
							objDto.getPagingSorting().setPageNumber(0);
							orderList=new ArrayList<ActiveLineItemReportsDTO>();
						}else if (orderList!=null && objDto.getPagingSorting().getRecordCount()<=ApplicationFlags.ReportsExcelMaxSize){
							pagingSorting.setPagingSorting(false, true);
							orderList = objService.viewActiveLineItemsList(objDto);
						}else{
							orderList=null;
						}
					}else {
					//No of records limit : end
				
						orderList = objService.viewActiveLineItemsList(objDto);
				}
				}
				if ("true".equals(inExcel)) {
					objForm.setBcpExcelList(orderList);
					request.setAttribute("orderActiveLineItemsList", orderList);
				} else {
					request.setAttribute("customerList", orderList);
					objForm.setCustomerList(orderList);
				}
			
			
		}catch (Exception ex) {
			return mapping.findForward(Messages
					.getMessageValue("errorGlobalForward"));
		}

		return mapping.findForward(forwardMapping);
	}
	
	
	public ActionForward viewOrderReportNew(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String forwardMapping = "";
		ArrayList<OrderReportNewDetailCwnDTO> orderList = new ArrayList<OrderReportNewDetailCwnDTO>();
		ReportsModel objService = new ReportsModel();
		ReportsBean objForm = (ReportsBean) form;
		OrderReportNewDetailCwnDTO objDto = new OrderReportNewDetailCwnDTO();
		String userId,interfaceId;
		try {
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			interfaceId=(String) request.getParameter("interfaceId");
			userId=objUserDto.getUserId();
			System.out.println(userId+" "+interfaceId);
			Utility.setFileDumpParams(request,objForm);//Check objForm 
			ArrayList errors = new ArrayList();
			Utility.setFileDumpParams(request,objForm);
			objDto.setOrderType(objForm.getOrderType());
			objDto.setToDate(objForm.getToDate());
			objDto.setFromDate(objForm.getFromDate());
			objDto.setVerticalDetails(objForm.getVerticalDetails());
			objDto.setOsp(objForm.getOsp());
			PagingSorting pagingSorting = objForm.getPagingSorting();

				String inExcel = objForm.getToExcel();
				if ("true".equals(inExcel)) {
					pagingSorting.setPagingSorting(false, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "displayReportInExcelExt";
				} else {
					pagingSorting.setPagingSorting(true, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "orderReportNewList";
				}

				pagingSorting.setDefaultifNotPresent("ORDERNO",
						PagingSorting.increment, 1);
				objDto.setPagingSorting(pagingSorting);
				String fromPageSubmit=request.getParameter("fromPageSubmit");				
				request.setAttribute("fromPageSubmit", fromPageSubmit);
				if("1".equals(fromPageSubmit))
				{
//					No of records limit : start
					if ("true".equals(inExcel)) {
						pagingSorting.setPagingSorting(true, true);
						pagingSorting.setStartRecordId(1);
						pagingSorting.setEndRecordIdForNoOfRecordsCount();
						orderList = objService.viewOrderReportNew(objDto);
						if(orderList!=null && objDto.getPagingSorting().getRecordCount()>ApplicationFlags.ReportsExcelMaxSize)
						{	
							forwardMapping = "orderReportNewList";
							objForm.setReportProcessForLimit("1");
							objForm.setReportCurrentCount(String.valueOf(objDto.getPagingSorting().getRecordCount()));
							objDto.getPagingSorting().setRecordCount(0);
							objDto.getPagingSorting().setPageNumber(0);
							orderList=new ArrayList<OrderReportNewDetailCwnDTO>();
						}else if (orderList!=null && objDto.getPagingSorting().getRecordCount()<=ApplicationFlags.ReportsExcelMaxSize){
							pagingSorting.setPagingSorting(false, true);
							orderList = objService.viewOrderReportNew(objDto);
						}else{
							orderList=null;
						}
					}else {
					//No of records limit : end
					orderList = objService.viewOrderReportNew(objDto);
				}
		}

				

				if ("true".equals(inExcel)) {
					objForm.setBcpExcelList(orderList);
					request.setAttribute("orderReportNewListExcel", orderList);
				} else {
					request.setAttribute("orderReportNewList", orderList);
					objForm.setCustomerList(orderList);
				}
			
		} catch (Exception ex) {
			return mapping.findForward(Messages
					.getMessageValue("errorGlobalForward"));
		}

		return mapping.findForward(forwardMapping);
	}
	
	
	public ActionForward viewOrderReportChange(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String forwardMapping = "";
		ArrayList<OrderDetailChangeReportDTO> orderList = new ArrayList<OrderDetailChangeReportDTO>();
		ReportsModel objService = new ReportsModel();
		ReportsBean objForm = (ReportsBean) form;
		OrderDetailChangeReportDTO objDto = new OrderDetailChangeReportDTO();
		String userId,interfaceId;
		try {
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			interfaceId=(String) request.getParameter("interfaceId");
			userId=objUserDto.getUserId();
			System.out.println(userId+" "+interfaceId);
			Utility.setFileDumpParams(request,objForm);//Check objForm 
			ArrayList errors = new ArrayList();
			objDto.setOrderType(objForm.getOrderType());
			objDto.setToDate(objForm.getToDate());
			objDto.setFromDate(objForm.getFromDate());
			objDto.setFromAccountNo(objForm.getFromAccountNo());
			objDto.setToAccountNo(objForm.getToAccountNo());
			objDto.setFromOrderNo(objForm.getFromOrderNo());
			objDto.setToOrderNo(objForm.getToOrderNo());
			objDto.setLogicalSINo(objForm.getLogical_SI_No());
			objDto.setCompanyName(objForm.getCompanyName());
			objDto.setOsp(objForm.getOsp());
			objDto.setFromOrderDate(objForm.getFromOrderDate());
			objDto.setToOrderDate(objForm.getToOrderDate());
			PagingSorting pagingSorting = objForm.getPagingSorting();

				String inExcel = objForm.getToExcel();
				if ("true".equals(inExcel)) {
					pagingSorting.setPagingSorting(false, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "displayReportInExcelExt";
				} else {
					pagingSorting.setPagingSorting(true, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "orderReportChangeList";
				}

				pagingSorting.setDefaultifNotPresent("ORDERNO",
						PagingSorting.increment, 1);
				objDto.setPagingSorting(pagingSorting);
				
				String fromPageSubmit=request.getParameter("fromPageSubmit");				
				request.setAttribute("fromPageSubmit", fromPageSubmit);
				if("1".equals(fromPageSubmit))
				{
//					No of records limit : start
					if ("true".equals(inExcel)) {
						pagingSorting.setPagingSorting(true, true);
						pagingSorting.setStartRecordId(1);
						pagingSorting.setEndRecordIdForNoOfRecordsCount();
						orderList = objService.viewOrderReportChange(objDto);
						if(orderList!=null && objDto.getPagingSorting().getRecordCount()>ApplicationFlags.ReportsExcelMaxSize)
						{	
							forwardMapping = "orderReportChangeList";
							objForm.setReportProcessForLimit("1");
							objForm.setReportCurrentCount(String.valueOf(objDto.getPagingSorting().getRecordCount()));
							objDto.getPagingSorting().setRecordCount(0);
							objDto.getPagingSorting().setPageNumber(0);
							orderList=new ArrayList<OrderDetailChangeReportDTO>();
						}else if (orderList!=null && objDto.getPagingSorting().getRecordCount()<=ApplicationFlags.ReportsExcelMaxSize){
							pagingSorting.setPagingSorting(false, true);
							orderList = objService.viewOrderReportChange(objDto);
						}else{
							orderList=null;
						}
					}else {
					//No of records limit : end
					orderList = objService.viewOrderReportChange(objDto);
				}
				}

				if ("true".equals(inExcel)) {
					objForm.setBcpExcelList(orderList);
					request.setAttribute("orderReportChangeListExcel", orderList);
				} else {
					request.setAttribute("orderReportChangeList", orderList);
					objForm.setCustomerList(orderList);
				}
			
		} catch (Exception ex) {
			return mapping.findForward(Messages
					.getMessageValue("errorGlobalForward"));
		}

		return mapping.findForward(forwardMapping);
	}
	
	
	public ActionForward viewOrderReportDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String forwardMapping = "";
		ArrayList<OrderDetailReportDTO> orderList = new ArrayList<OrderDetailReportDTO>();
		ReportsModel objService = new ReportsModel();
		ReportsBean objForm = (ReportsBean) form;
		OrderDetailReportDTO objDto = new OrderDetailReportDTO();
		String userId,interfaceId;
		try {
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			interfaceId=(String) request.getParameter("interfaceId");
			userId=objUserDto.getUserId();
			System.out.println(userId+" "+interfaceId);
			Utility.setFileDumpParams(request,objForm);//Check objForm 
			objDto.setOrderType(objForm.getOrderType());
			objDto.setToDate(objForm.getToDate());
			objDto.setFromDate(objForm.getFromDate());
			objDto.setFromAccountNo(objForm.getFromAccountNo());
			objDto.setToAccountNo(objForm.getToAccountNo());
			objDto.setFromOrderNo(objForm.getFromOrderNo());
			objDto.setToOrderNo(objForm.getToOrderNo());
			objDto.setDemo(objForm.getDemo());
			objDto.setFromOrderDate(objForm.getFromOrderDate());
			objDto.setToOrderDate(objForm.getToOrderDate());
			
			objDto.setOsp(objForm.getOsp());
			PagingSorting pagingSorting = objForm.getPagingSorting();

				String inExcel = objForm.getToExcel();
				if ("true".equals(inExcel)) {
					pagingSorting.setPagingSorting(false, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "displayReportInExcelExt";
				} else {
					pagingSorting.setPagingSorting(true, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "orderReportDetailList";
				}

				pagingSorting.setDefaultifNotPresent("ORDERNO",
						PagingSorting.increment, 1);
				objDto.setPagingSorting(pagingSorting);

				String fromPageSubmit=request.getParameter("fromPageSubmit");				
				request.setAttribute("fromPageSubmit", fromPageSubmit);
				if("1".equals(fromPageSubmit))
				{
					//No of records limit : start
					if ("true".equals(inExcel)) {
						pagingSorting.setPagingSorting(true, true);
						pagingSorting.setStartRecordId(1);
						pagingSorting.setEndRecordIdForNoOfRecordsCount();
						orderList = objService.viewOrderReportDetails(objDto);
						if(orderList!=null && objDto.getPagingSorting().getRecordCount()>ApplicationFlags.ReportsExcelMaxSize)
						{	
							forwardMapping = "orderReportDetailList";
							objForm.setReportProcessForLimit("1");
							objForm.setReportCurrentCount(String.valueOf(objDto.getPagingSorting().getRecordCount()));
							objDto.getPagingSorting().setRecordCount(0);
							objDto.getPagingSorting().setPageNumber(0);
							orderList=new ArrayList<OrderDetailReportDTO>();
						}else if (orderList!=null && objDto.getPagingSorting().getRecordCount()<=ApplicationFlags.ReportsExcelMaxSize){
							pagingSorting.setPagingSorting(false, true);
							orderList = objService.viewOrderReportDetails(objDto);
						}else{
							orderList=null;
						}
					}else {
					//No of records limit : end
					orderList = objService.viewOrderReportDetails(objDto);
				}	
				}

				if ("true".equals(inExcel)) {
					objForm.setBcpExcelList(orderList);
					request.setAttribute("orderReportDetailListExcel", orderList);
				} else {
					request.setAttribute("orderReportDetailList", orderList);
					objForm.setCustomerList(orderList);
				}
			
		} catch (Exception ex) {
			return mapping.findForward(Messages
					.getMessageValue("errorGlobalForward"));
		}

		return mapping.findForward(forwardMapping);
	}
	
		public ActionForward viewPendingOrderAndBillingList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String forwardMapping = "";
		ArrayList<PendingOrderAndBillingReportDTO> orderList = new ArrayList<PendingOrderAndBillingReportDTO>();
		ReportsModel objService = new ReportsModel();
		ReportsBean objForm = (ReportsBean) form;
		PendingOrderAndBillingReportDTO objDto = new PendingOrderAndBillingReportDTO();
		String userId,interfaceId;
		try {
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				interfaceId=(String) request.getParameter("interfaceId");
				userId=objUserDto.getUserId();
				System.out.println(userId+" "+interfaceId);
			Utility.setFileDumpParams(request,objForm);
				ActionMessages messages = new ActionMessages();
				objDto.setOrderType(objForm.getOrderType());
				objDto.setToCopcApprovedDate(objForm.getToCopcApprovedDate());
				objDto.setFromCopcApprovedDate(objForm.getFromCopcApprovedDate());
				objDto.setLOC_Date(objForm.getLocDate());
				objDto.setToAccountNo(objForm.getToAccountNo());
				objDto.setFromAccountNo(objForm.getFromAccountNo());
				objDto.setToOrderNo(objForm.getToOrderNo());
				objDto.setFromOrderNo(objForm.getFromOrderNo());
				objDto.setActmngname(objForm.getActmngname());
				
				objDto.setOsp(objForm.getOsp());
				PagingSorting pagingSorting = objForm.getPagingSorting();
				
			
				
				//request.setAttribute("orderStageList", orderList);
				//objForm.setOrderStageList(orderList);
			

				String inExcel = objForm.getToExcel();
				if ("true".equals(inExcel)) 
				{
					pagingSorting.setPagingSorting(false, true);
					request.setAttribute("userId",objUserDto.getUserId());
					//forwardMapping = "displayPendingOrderAndBillingReportInExcel";
					forwardMapping = "displayReportInExcelExt";
				}
				else 
				{
					pagingSorting.setPagingSorting(true, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "displayPendingOrderAndBillingReport";
				}

				pagingSorting.setDefaultifNotPresent("ORDERNO",
						PagingSorting.increment, 1);
				objDto.setPagingSorting(pagingSorting);
				String fromPageSubmit=request.getParameter("fromPageSubmit");				
				request.setAttribute("fromPageSubmit", fromPageSubmit);
				if("1".equals(fromPageSubmit))
				{
					//No of records limit : start
					if ("true".equals(inExcel)) {
						pagingSorting.setPagingSorting(true, true);
						pagingSorting.setStartRecordId(1);
						pagingSorting.setEndRecordIdForNoOfRecordsCount();
						orderList = objService.viewPendingOrderAndBillingList(objDto);
						if(orderList!=null && objDto.getPagingSorting().getRecordCount()>ApplicationFlags.ReportsExcelMaxSize)
						{	
							forwardMapping = "displayPendingOrderAndBillingReport";
							objForm.setReportProcessForLimit("1");
							objForm.setReportCurrentCount(String.valueOf(objDto.getPagingSorting().getRecordCount()));
							objDto.getPagingSorting().setRecordCount(0);
							objDto.getPagingSorting().setPageNumber(0);
							orderList=new ArrayList<PendingOrderAndBillingReportDTO>();
						}else if (orderList!=null && objDto.getPagingSorting().getRecordCount()<=ApplicationFlags.ReportsExcelMaxSize){
							pagingSorting.setPagingSorting(false, true);
							orderList = objService.viewPendingOrderAndBillingList(objDto);
						}else{
							orderList=null;
						}
					}else {
					//No of records limit : end
					orderList = objService.viewPendingOrderAndBillingList(objDto);
				}
				}

				

				if ("true".equals(inExcel)) 
				{
					objForm.setBcpExcelList(orderList);
					request.setAttribute("pendingOrderAndBillingExcelList", orderList);
				}
				else 
				{
					request.setAttribute("pendingOrderList", orderList);
					objForm.setCustomerList(orderList);
				}
		} 
		catch (Exception ex) {
			return mapping.findForward(Messages
					.getMessageValue("errorGlobalForward"));
		}

		return mapping.findForward(forwardMapping);
	}
	
		
//		lawkush
//		[001]	START 
		/**
		 * pendingOrderBill method to retreive the data for the report
		 * meant to fetch data of pending orders in billing and hardware.
		 */
		public ActionForward pendingOrderBill(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {

			String forwardMapping = "";
			ArrayList<PendingOrdersAndBillingHardwaresDTO> orderList = new ArrayList<PendingOrdersAndBillingHardwaresDTO>();
			ReportsModel objService = new ReportsModel();
			ReportsBean objForm = (ReportsBean) form;
			PendingOrdersAndBillingHardwaresDTO objDto = new PendingOrdersAndBillingHardwaresDTO();
			String userId,interfaceId;
			try {
					HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
					HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
					UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
					interfaceId=(String) request.getParameter("interfaceId");
					userId=objUserDto.getUserId();
					System.out.println(userId+" "+interfaceId);
				Utility.setFileDumpParams(request,objForm);
					objDto.setOrderType(objForm.getOrderType());
				    objDto.setFromAccountNo(objForm.getFromAccountNo());
				    objDto.setToAccountNo(objForm.getToAccountNo());
				    objDto.setFromOrderDate(objForm.getFromOrderDate());
				    objDto.setToOrderDate(objForm.getToOrderDate());
				    objDto.setFromCrmOrderid(objForm.getFromCrmOrderid());
				    objDto.setToCrmOrderid(objForm.getToCrmOrderid());
				    objDto.setParty(objForm.getPartyName());
					
					PagingSorting pagingSorting = objForm.getPagingSorting();
					String inExcel = objForm.getToExcel();
					if ("true".equals(inExcel)) 
					{
						pagingSorting.setPagingSorting(false, true);
						request.setAttribute("userId",objUserDto.getUserId());
						forwardMapping = "displayReportInExcel";
					}
					else 
					{
						pagingSorting.setPagingSorting(true, true);
						request.setAttribute("userId",objUserDto.getUserId());
						forwardMapping = "pendingOrderBillingItemsList";
					}

					pagingSorting.setDefaultifNotPresent("LOGICAL_SI_NO",
							PagingSorting.increment, 1);
					objDto.setPagingSorting(pagingSorting);
					String fromPageSubmit=request.getParameter("fromPageSubmit");				
					request.setAttribute("fromPageSubmit", fromPageSubmit);
					if("1".equals(fromPageSubmit))
					{
//						No of records limit : start
						if ("true".equals(inExcel)) {
							pagingSorting.setPagingSorting(true, true);
							pagingSorting.setStartRecordId(1);
							pagingSorting.setEndRecordIdForNoOfRecordsCount();
							orderList = objService.pendingOrderBill(objDto);
							if(orderList!=null && objDto.getPagingSorting().getRecordCount()>ApplicationFlags.ReportsExcelMaxSize)
							{	
								forwardMapping = "pendingOrderBillingItemsList";
								objForm.setReportProcessForLimit("1");
								objForm.setReportCurrentCount(String.valueOf(objDto.getPagingSorting().getRecordCount()));
								objDto.getPagingSorting().setRecordCount(0);
								objDto.getPagingSorting().setPageNumber(0);
								orderList=new ArrayList<PendingOrdersAndBillingHardwaresDTO>();
							}else if (orderList!=null && objDto.getPagingSorting().getRecordCount()<=ApplicationFlags.ReportsExcelMaxSize){
								pagingSorting.setPagingSorting(false, true);
								orderList = objService.pendingOrderBill(objDto);
							}else{
								orderList=null;
							}
						}else {
						//No of records limit : end
						orderList = objService.pendingOrderBill(objDto);
					}
					}
					

					if ("true".equals(inExcel)) 
					{
						objForm.setBcpExcelList(orderList);
						request.setAttribute("pendingOrderBillingExcelList", orderList);
					}
					else 
					{
						request.setAttribute("pendingOrderBill", orderList);
						objForm.setCustomerList(orderList);
					}
			} 
			catch (Exception ex) {
				return mapping.findForward(Messages
						.getMessageValue("errorGlobalForward"));
			}

			return mapping.findForward(forwardMapping);
		}
//		[001]	END
		
		public ActionForward DisplayCancalCopyReport(ActionMapping mapping,ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			ActionForward forward = new ActionForward();
			ReportsBean formBean = (ReportsBean) form;
			ActionMessages messages = new ActionMessages();
			try 
			{
				forward = mapping.findForward("DisplayCancalCopyReport");
			} 
			catch (Exception e) 
			{
				AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
				messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
				e.printStackTrace();
			}
			return forward;
		}
		//LAWKUSH START
		public ActionForward viewZeroOrderValueReportDetails(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {

			String forwardMapping = "";
			ArrayList<ZeroOrderValueReportDTO> orderList = new ArrayList<ZeroOrderValueReportDTO>();
			ReportsModel objService = new ReportsModel();
			ReportsBean objForm = (ReportsBean) form;
			ZeroOrderValueReportDTO objDto = new ZeroOrderValueReportDTO();
			String userId,interfaceId;
			try {
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				interfaceId=(String) request.getParameter("interfaceId");
				userId=objUserDto.getUserId();
				System.out.println(userId+" "+interfaceId);
				Utility.setFileDumpParams(request,objForm);//Check objForm 
				objDto.setOrderType(objForm.getOrderType());
				objDto.setToDate(objForm.getToDate());
				objDto.setFromDate(objForm.getFromDate());
				objDto.setFromAccountNo(objForm.getFromAccountNo());
				objDto.setToAccountNo(objForm.getToAccountNo());
				objDto.setFromOrderNo(objForm.getFromOrderNo());
				objDto.setToOrderNo(objForm.getToOrderNo());
				objDto.setCustPoDetailNo(objForm.getCustPoDetailNo());
				
				
				PagingSorting pagingSorting = objForm.getPagingSorting();

					String inExcel = objForm.getToExcel();
					if ("true".equals(inExcel)) {
						pagingSorting.setPagingSorting(false, true);
						request.setAttribute("userId",objUserDto.getUserId());
						forwardMapping = "displayReportInExcel";
					} else {
						pagingSorting.setPagingSorting(true, true);
						request.setAttribute("userId",objUserDto.getUserId());
						forwardMapping = "zeroOrderValueReport";
					}

					pagingSorting.setDefaultifNotPresent("ORDERNO",
							PagingSorting.increment, 1);
					objDto.setPagingSorting(pagingSorting);
					String fromPageSubmit=request.getParameter("fromPageSubmit");
					request.setAttribute("fromPageSubmit", fromPageSubmit);
					if("1".equals(fromPageSubmit))
					{
//						No of records limit : start
						if ("true".equals(inExcel)) {
							pagingSorting.setPagingSorting(true, true);
							pagingSorting.setStartRecordId(1);
							pagingSorting.setEndRecordIdForNoOfRecordsCount();
							orderList = objService.viewZeroOrderValueReportDetails(objDto);
							if(orderList!=null && objDto.getPagingSorting().getRecordCount()>ApplicationFlags.ReportsExcelMaxSize)
							{	
								forwardMapping = "zeroOrderValueReport";
								objForm.setReportProcessForLimit("1");
								objForm.setReportCurrentCount(String.valueOf(objDto.getPagingSorting().getRecordCount()));
								objDto.getPagingSorting().setRecordCount(0);
								objDto.getPagingSorting().setPageNumber(0);
								orderList=new ArrayList<ZeroOrderValueReportDTO>();
							}else if (orderList!=null && objDto.getPagingSorting().getRecordCount()<=ApplicationFlags.ReportsExcelMaxSize){
								pagingSorting.setPagingSorting(false, true);
								orderList = objService.viewZeroOrderValueReportDetails(objDto);
							}else{
								orderList=null;
							}
						}else {
						//No of records limit : end
						orderList = objService.viewZeroOrderValueReportDetails(objDto);
					}
			}
					if ("true".equals(inExcel)) {
						objForm.setBcpExcelList(orderList);
						request.setAttribute("zeroOrderValueReportListExcel", orderList);
					} else {
						request.setAttribute("zeroOrderValueReportList", orderList);
						objForm.setCustomerList(orderList);
					}
				
			} catch (Exception ex) {
				return mapping.findForward(Messages
						.getMessageValue("errorGlobalForward"));
			}

			return mapping.findForward(forwardMapping);
		}
		

			

		
		//LAWKUSH END
		

		public ActionForward viewM6OrderCancelReport(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {

			String forwardMapping = "";
			ArrayList<M6OrderCancelReportDTO> orderList = new ArrayList<M6OrderCancelReportDTO>();
			ReportsModel objService = new ReportsModel();
			ReportsBean objForm = (ReportsBean) form;
			M6OrderCancelReportDTO objDto = new M6OrderCancelReportDTO();
			String userId,interfaceId;
			try {
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				interfaceId=(String) request.getParameter("interfaceId");
				userId=objUserDto.getUserId();
				System.out.println(userId+" "+interfaceId);
				Utility.setFileDumpParams(request,objForm);
				ArrayList errors = new ArrayList();
				objDto.setToDate(objForm.getToDate());
				objDto.setFromDate(objForm.getFromDate());
				PagingSorting pagingSorting = objForm.getPagingSorting();

					String inExcel = objForm.getToExcel();
					if ("true".equals(inExcel)) {
						pagingSorting.setPagingSorting(false, true);
						request.setAttribute("userId",objUserDto.getUserId());
						forwardMapping = "displayReportInExcel";
					} else {
						pagingSorting.setPagingSorting(true, true);
						request.setAttribute("userId",objUserDto.getUserId());
						forwardMapping = "DisplayM6OrderCancelReport";
					}

					pagingSorting.setDefaultifNotPresent("ORDERNO",
							PagingSorting.increment, 1);
					objDto.setPagingSorting(pagingSorting);	
					
					String fromPageSubmit=request.getParameter("fromPageSubmit");				
					 request.setAttribute("fromPageSubmit", fromPageSubmit);
					if("1".equals(fromPageSubmit))
					{
						//No of records limit : start
						if ("true".equals(inExcel)) {
							pagingSorting.setPagingSorting(true, true);
							pagingSorting.setStartRecordId(1);
							pagingSorting.setEndRecordIdForNoOfRecordsCount();
							orderList = objService.viewM6OrderCancelReport(objDto);//***************CHANGE HERE*****************
							if(orderList!=null && objDto.getPagingSorting().getRecordCount()>ApplicationFlags.ReportsExcelMaxSize)
							{	
								forwardMapping = "DisplayM6OrderCancelReport";//***************CHANGE HERE*****************
								objForm.setReportProcessForLimit("1");
								objForm.setReportCurrentCount(String.valueOf(objDto.getPagingSorting().getRecordCount()));
								objDto.getPagingSorting().setRecordCount(0);
								objDto.getPagingSorting().setPageNumber(0);
								orderList=new ArrayList<M6OrderCancelReportDTO>();
							}else if (orderList!=null && objDto.getPagingSorting().getRecordCount()<=ApplicationFlags.ReportsExcelMaxSize){
								pagingSorting.setPagingSorting(false, true);
								orderList = objService.viewM6OrderCancelReport(objDto);
							}else{
								orderList=null;
							}
						}else {
						//No o
						orderList = objService.viewM6OrderCancelReport(objDto);
					}
					}
				    if ("true".equals(inExcel)) {
						objForm.setBcpExcelList(orderList);
						request.setAttribute("M6orderCancelReportExcel", orderList);
					} else {
						request.setAttribute("M6OrderCancelReport", orderList);
						objForm.setCustomerList(orderList);
					}
				
			} catch (Exception ex) {
				return mapping.findForward(Messages
						.getMessageValue("errorGlobalForward"));
			}

			return mapping.findForward(forwardMapping);
		}
		
		
		//LAWKUSH START
		public ActionForward viewRateRenewalReport(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {

			String forwardMapping = "";
			ArrayList<RateRenewalReportDTO> orderList = new ArrayList<RateRenewalReportDTO>();
			ReportsModel objService = new ReportsModel();
			ReportsBean objForm = (ReportsBean) form;
			RateRenewalReportDTO objDto = new RateRenewalReportDTO();
			String userId,interfaceId;
			try {
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				interfaceId=(String) request.getParameter("interfaceId");
				userId=objUserDto.getUserId();
				System.out.println(userId+" "+interfaceId);
				Utility.setFileDumpParams(request,objForm);
				objDto.setOrderType(objForm.getOrderType());
				objDto.setToDate(objForm.getToDate());
				objDto.setFromDate(objForm.getFromDate());
				objDto.setFromAccountNo(objForm.getFromAccountNo());
				objDto.setToAccountNo(objForm.getToAccountNo());
				objDto.setFromOrderNo(objForm.getFromOrderNo());
				objDto.setToOrderNo(objForm.getToOrderNo());
				objDto.setCus_segment(objForm.getCus_segment());
				objDto.setDemo(objForm.getDemo());
				
				
				
				PagingSorting pagingSorting = objForm.getPagingSorting();

					String inExcel = objForm.getToExcel();
					if ("true".equals(inExcel)) {
						pagingSorting.setPagingSorting(false, true);
						request.setAttribute("userId",objUserDto.getUserId());
						forwardMapping = "displayReportInExcel";
					} else {
						pagingSorting.setPagingSorting(true, true);
						request.setAttribute("userId",objUserDto.getUserId());
						forwardMapping = "rateRenewalReport";
					}

					pagingSorting.setDefaultifNotPresent("ORDERNO",
							PagingSorting.increment, 1);
					objDto.setPagingSorting(pagingSorting);

				//[RPT7052013027] --start	
					String fromPageSubmit=request.getParameter("fromPageSubmit");
					request.setAttribute("fromPageSubmit", fromPageSubmit);
					if("1".equals(fromPageSubmit))
					{
						//No of records limit : start
						if ("true".equals(inExcel)) {
							pagingSorting.setPagingSorting(true, true);
							pagingSorting.setStartRecordId(1);
							pagingSorting.setEndRecordIdForNoOfRecordsCount();
							orderList = objService.viewRateRenewalReport(objDto);
							if(orderList!=null && objDto.getPagingSorting().getRecordCount()>ApplicationFlags.ReportsExcelMaxSize)
							{	
								forwardMapping = "rateRenewalReport";
								objForm.setReportProcessForLimit("1");
								objForm.setReportCurrentCount(String.valueOf(objDto.getPagingSorting().getRecordCount()));
								objDto.getPagingSorting().setRecordCount(0);
								objDto.getPagingSorting().setPageNumber(0);
								orderList=new ArrayList<RateRenewalReportDTO>();
							}else if (orderList!=null && objDto.getPagingSorting().getRecordCount()<=ApplicationFlags.ReportsExcelMaxSize){
								pagingSorting.setPagingSorting(false, true);
								orderList = objService.viewRateRenewalReport(objDto);
							}else{
								orderList=null;
							}
						}else {
						//No of records limit : end
						orderList = objService.viewRateRenewalReport(objDto);
					}
					}
				// [RPT7052013027] --end

					if ("true".equals(inExcel)) {
						objForm.setBcpExcelList(orderList);
						request.setAttribute("rateRenewalReportListExcel", orderList);
					} else {
						request.setAttribute("rateRenewalReportList", orderList);
						request.setAttribute("segment", objDto.getCus_segment());
						objForm.setCustomerList(orderList);
					}
			}
			 catch (Exception ex) {
				return mapping.findForward(Messages
						.getMessageValue("errorGlobalForward"));
			}

			return mapping.findForward(forwardMapping);
		}
		

			

		
		//LAWKUSH END
		
		public ActionForward viewRestPendingLineReport(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {

			String forwardMapping = "";
			ArrayList<RestPendingLineReportDTO> orderList = new ArrayList<RestPendingLineReportDTO>();
			ReportsModel objService = new ReportsModel();
			ReportsBean objForm = (ReportsBean) form;
			RestPendingLineReportDTO objDto = new RestPendingLineReportDTO();
			String userId,interfaceId;
			try {
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				interfaceId=(String) request.getParameter("interfaceId");
				userId=objUserDto.getUserId();
				System.out.println(userId+" "+interfaceId);
				ArrayList errors = new ArrayList();
				Utility.setFileDumpParams(request,objForm);
				objDto.setToDate(objForm.getToDate());
				objDto.setFromDate(objForm.getFromDate());
				objDto.setVerticalDetails(objForm.getVerticalDetails());
				objDto.setServiceName(objForm.getServicename());
				objDto.setSubChangeTypeName(objForm.getSubtype());
				
				PagingSorting pagingSorting = objForm.getPagingSorting();

					String inExcel = objForm.getToExcel();
					if ("true".equals(inExcel)) {
						pagingSorting.setPagingSorting(false, true);
						request.setAttribute("userId",objUserDto.getUserId());
						forwardMapping = "displayReportInExcel";
					} else {
						pagingSorting.setPagingSorting(true, true);
						request.setAttribute("userId",objUserDto.getUserId());
						forwardMapping = "RestPendingLineReport";
					}

					pagingSorting.setDefaultifNotPresent("ORDERNO",
							PagingSorting.increment, 1);
					objDto.setPagingSorting(pagingSorting);
					String fromPageSubmit=request.getParameter("fromPageSubmit");				
					request.setAttribute("fromPageSubmit", fromPageSubmit);
					if("1".equals(fromPageSubmit))
					{if ("true".equals(inExcel)) {
						pagingSorting.setPagingSorting(true, true);
						pagingSorting.setStartRecordId(1);
						pagingSorting.setEndRecordIdForNoOfRecordsCount();
						orderList = objService.viewRestPendingLineReport(objDto);//***************CHANGE HERE*****************
						if(orderList!=null && objDto.getPagingSorting().getRecordCount()>ApplicationFlags.ReportsExcelMaxSize)
						{	
							forwardMapping = "RestPendingLineReport";//***************CHANGE HERE*****************
							objForm.setReportProcessForLimit("1");
							objForm.setReportCurrentCount(String.valueOf(objDto.getPagingSorting().getRecordCount()));
							objDto.getPagingSorting().setRecordCount(0);
							objDto.getPagingSorting().setPageNumber(0);
							orderList=new ArrayList<RestPendingLineReportDTO>();
						}else if (orderList!=null && objDto.getPagingSorting().getRecordCount()<=ApplicationFlags.ReportsExcelMaxSize){
							pagingSorting.setPagingSorting(false, true);
							orderList = objService.viewRestPendingLineReport(objDto);
						}else{
							orderList=null;
						}
					}else {
						orderList = objService.viewRestPendingLineReport(objDto);
					}

					}
					

					if ("true".equals(inExcel)) {
						objForm.setBcpExcelList(orderList);
						request.setAttribute("RestPendingLineReportInExcel", orderList);
					} else {
						request.setAttribute("RestPendingLineReport", orderList);
						objForm.setCustomerList(orderList);
					}
				
			}catch (Exception ex) {
				return mapping.findForward(Messages
						.getMessageValue("errorGlobalForward"));
			}

			return mapping.findForward(forwardMapping);
		}

	/**Vijay
	 * New method for Pending Line Report
	 */
		public ActionForward viewPendingLineReport(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {

			String forwardMapping = "";
			ArrayList<ReportsDto> orderList = new ArrayList<ReportsDto>();
			ReportsModel objService = new ReportsModel();
			ReportsBean objForm = (ReportsBean) form;
			ReportsDto objDto = new ReportsDto();
			String userId,interfaceId;
			try {
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				interfaceId=(String) request.getParameter("interfaceId");
				userId=objUserDto.getUserId();
				System.out.println(userId+" "+interfaceId);
				ArrayList errors = new ArrayList();
				Utility.setFileDumpParams(request,objForm);
				objDto.setToDate(objForm.getToDate());
				objDto.setFromDate(objForm.getFromDate());
				objDto.setVerticalDetails(objForm.getVerticalDetails());
				objDto.setServiceName(objForm.getServicename());
				objDto.setSubChangeTypeName(objForm.getSubtype());
				
				PagingSorting pagingSorting = objForm.getPagingSorting();

					String inExcel = objForm.getToExcel();
					if ("true".equals(inExcel)) {
						pagingSorting.setPagingSorting(false, true);
						request.setAttribute("userId",objUserDto.getUserId());
						forwardMapping = "displayReportInExcel";
					} else {
						pagingSorting.setPagingSorting(true, true);
						request.setAttribute("userId",objUserDto.getUserId());
						forwardMapping = "PendingLineReport";
					}

					pagingSorting.setDefaultifNotPresent("ORDERNO",
							PagingSorting.increment, 1);
					objDto.setPagingSorting(pagingSorting);
					String fromPageSubmit=request.getParameter("fromPageSubmit");				
					request.setAttribute("fromPageSubmit", fromPageSubmit);
					if("1".equals(fromPageSubmit))
					{
						orderList = objService.viewPendingLineReport(objDto);
					}

					if ("true".equals(inExcel)) {
						objForm.setBcpExcelList(orderList);
						request.setAttribute("PendingLineReportInExcel", orderList);
					} else {
						request.setAttribute("PendingLineReport", orderList);
						objForm.setCustomerList(orderList);
					}
				
			} catch (Exception ex) {
				return mapping.findForward(Messages
						.getMessageValue("errorGlobalForward"));
			}

			return mapping.findForward(forwardMapping);
		}
		
		/*End of method for pending line report */
// disconnection line report		
		public ActionForward viewDisconnectionLineReport(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {

			String forwardMapping = "";
			ArrayList<DisconnectLineReportDTO> orderList = new ArrayList<DisconnectLineReportDTO>();
			ReportsModel objService = new ReportsModel();
			ReportsBean objForm = (ReportsBean) form;
			DisconnectLineReportDTO objDto = new DisconnectLineReportDTO();
			String userId, interfaceId;
			try {
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				interfaceId=(String) request.getParameter("interfaceId");
				userId=objUserDto.getUserId();
				System.out.println(userId+" "+interfaceId);
				Utility.setFileDumpParams(request,objForm);//Check objForm 
				ArrayList errors = new ArrayList();
				objDto.setOrdermonth(objForm.getOrdermonth());
				objDto.setToDate(objForm.getToDate());
				objDto.setFromDate(objForm.getFromDate());
				objDto.setServiceName(objForm.getServicename());
				objDto.setOrdersubtype(objForm.getOrdersubtype());
				objDto.setVerticalDetails(objForm.getVerticalDetails());
			    PagingSorting pagingSorting = objForm.getPagingSorting();

					String inExcel = objForm.getToExcel();
					if ("true".equals(inExcel)) {
						pagingSorting.setPagingSorting(false, true);
						request.setAttribute("userId",objUserDto.getUserId());
						forwardMapping = "displayReportInExcelExt";
					} else {
						pagingSorting.setPagingSorting(true, true);
						request.setAttribute("userId",objUserDto.getUserId());
						forwardMapping = "DisconnectLineReport";
					}

					pagingSorting.setDefaultifNotPresent("ORDERNO",
							PagingSorting.increment, 1);
					objDto.setPagingSorting(pagingSorting);
					String fromPageSubmit=request.getParameter("fromPageSubmit");				
					request.setAttribute("fromPageSubmit", fromPageSubmit);
					if("1".equals(fromPageSubmit))
					{//No of records limit : start
						if ("true".equals(inExcel)) {
							pagingSorting.setPagingSorting(true, true);
							pagingSorting.setStartRecordId(1);
							pagingSorting.setEndRecordIdForNoOfRecordsCount();
							orderList = objService.viewDisconnectionLineReport(objDto);
							if(orderList!=null && objDto.getPagingSorting().getRecordCount()>ApplicationFlags.ReportsExcelMaxSize)
							{	
								forwardMapping = "DisconnectLineReport";
								objForm.setReportProcessForLimit("1");
								objForm.setReportCurrentCount(String.valueOf(objDto.getPagingSorting().getRecordCount()));
								objDto.getPagingSorting().setRecordCount(0);
								objDto.getPagingSorting().setPageNumber(0);
								orderList=new ArrayList<DisconnectLineReportDTO>();
							}else if (orderList!=null && objDto.getPagingSorting().getRecordCount()<=ApplicationFlags.ReportsExcelMaxSize){
								pagingSorting.setPagingSorting(false, true);
								orderList = objService.viewDisconnectionLineReport(objDto);
							}else{
								orderList=null;
							}
						}else {
						//No of records limit : end
						orderList = objService.viewDisconnectionLineReport(objDto);
					
						}
						}
			

					if ("true".equals(inExcel)) {
						objForm.setBcpExcelList(orderList);
						request.setAttribute("DisconnectionLineReportInExcel", orderList);
					} else {
						request.setAttribute("DisconnectionLineReport", orderList);
						objForm.setCustomerList(orderList);
					}
				
			} catch (Exception ex) {
				return mapping.findForward(Messages
						.getMessageValue("errorGlobalForward"));
			}

			return mapping.findForward(forwardMapping);
		}
		
// cancelled line report
		
		
		public ActionForward viewCancelledFailedLineReport(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {

			String forwardMapping = "";
			ArrayList<CancelledFailedLineReportDTO> orderList = new ArrayList<CancelledFailedLineReportDTO>();
			ReportsModel objService = new ReportsModel();
			ReportsBean objForm = (ReportsBean) form;
			CancelledFailedLineReportDTO objDto = new CancelledFailedLineReportDTO();
			String userId,interfaceId;
			try {
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				interfaceId=(String) request.getParameter("interfaceId");
				userId=objUserDto.getUserId();
				System.out.println(userId+" "+interfaceId);
				ArrayList errors = new ArrayList();
				Utility.setFileDumpParams(request,objForm);
				objDto.setToDate(objForm.getToDate());
				objDto.setFromDate(objForm.getFromDate());
				objDto.setServiceName(objForm.getServicename());
				
				PagingSorting pagingSorting = objForm.getPagingSorting();

					String inExcel = objForm.getToExcel();
					if ("true".equals(inExcel)) {
						pagingSorting.setPagingSorting(false, true);
						request.setAttribute("userId",objUserDto.getUserId());
						forwardMapping = "displayReportInExcelExt";
					} else {
						pagingSorting.setPagingSorting(true, true);
						request.setAttribute("userId",objUserDto.getUserId());
						forwardMapping = "CancelledFailedLineReport";
					}

					pagingSorting.setDefaultifNotPresent("ORDERNO",
							PagingSorting.increment, 1);
					objDto.setPagingSorting(pagingSorting);
					String fromPageSubmit=request.getParameter("fromPageSubmit");				
					request.setAttribute("fromPageSubmit", fromPageSubmit);
					if("1".equals(fromPageSubmit))
					{
						//No of records limit : start
						if ("true".equals(inExcel)) {
							pagingSorting.setPagingSorting(true, true);
							pagingSorting.setStartRecordId(1);
							pagingSorting.setEndRecordIdForNoOfRecordsCount();
							orderList = objService.viewCancelledFailedLineReport(objDto);//***************CHANGE HERE*****************
							if(orderList!=null && objDto.getPagingSorting().getRecordCount()>ApplicationFlags.ReportsExcelMaxSize)
							{	
								forwardMapping = "CancelledFailedLineReport";//***************CHANGE HERE*****************
								objForm.setReportProcessForLimit("1");
								objForm.setReportCurrentCount(String.valueOf(objDto.getPagingSorting().getRecordCount()));
								objDto.getPagingSorting().setRecordCount(0);
								objDto.getPagingSorting().setPageNumber(0);
								orderList=new ArrayList<CancelledFailedLineReportDTO>();
							}else if (orderList!=null && objDto.getPagingSorting().getRecordCount()<=ApplicationFlags.ReportsExcelMaxSize){
								pagingSorting.setPagingSorting(false, true);
								orderList = objService.viewCancelledFailedLineReport(objDto);
							}else{
								orderList=null;
							}
						}else {
						orderList = objService.viewCancelledFailedLineReport(objDto);
					}}

					

					if ("true".equals(inExcel)) {
						objForm.setBcpExcelList(orderList);
						request.setAttribute("CancelledFailedLineReportInExcel", orderList);
					} else {
						request.setAttribute("CancelledFailedLineReport", orderList);
						objForm.setCustomerList(orderList);
					}
				
			}catch (Exception ex) {
				return mapping.findForward(Messages
						.getMessageValue("errorGlobalForward"));
			}

			return mapping.findForward(forwardMapping);
		}
		
		
		public ActionForward viewBulkSIUploadReport(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {

			String forwardMapping = "";
			ArrayList<BulkSIUploadReportDto> orderList = new ArrayList<BulkSIUploadReportDto>();
			ReportsModel objService = new ReportsModel();
			ReportsBean objForm = (ReportsBean) form;
			BulkSIUploadReportDto objDto = new BulkSIUploadReportDto();
			String userId,interfaceId;
			try {
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				interfaceId=(String) request.getParameter("interfaceId");
				userId=objUserDto.getUserId();
				System.out.println(userId+" "+interfaceId);
				Utility.setFileDumpParams(request,objForm);//Check objForm 
				ArrayList errors = new ArrayList();
				objDto.setDate_of_installation_from(objForm.getDate_of_installation_from());
				objDto.setDate_of_installation_to(objForm.getDate_of_installation_to());
				objDto.setServiceName(objForm.getServicename());
				objDto.setParty_no(objForm.getPartyNo());
				objDto.setOrderNo(objForm.getOrderNo());
				objDto.setLogicalSINo(objForm.getLogical_SI_No());
				objDto.setLinename(objForm.getLinename());
				PagingSorting pagingSorting = objForm.getPagingSorting();

					String inExcel = objForm.getToExcel();
					if ("true".equals(inExcel)) {
						pagingSorting.setPagingSorting(false, true);
						request.setAttribute("userId",objUserDto.getUserId());
						forwardMapping = "displayReportInExcel";
					} else {
						pagingSorting.setPagingSorting(true, true);
						request.setAttribute("userId",objUserDto.getUserId());
						forwardMapping = "BulkSIUploadReport";
					}

					pagingSorting.setDefaultifNotPresent("CUSTOMER_LOGICAL_SI_NO",
							PagingSorting.increment, 1);
					objDto.setPagingSorting(pagingSorting);
					String fromPageSubmit=request.getParameter("fromPageSubmit");				
					request.setAttribute("fromPageSubmit", fromPageSubmit);
					if("1".equals(fromPageSubmit))
					{
						//No of records limit : start
						if ("true".equals(inExcel)) {
							pagingSorting.setPagingSorting(true, true);
							pagingSorting.setStartRecordId(1);
							pagingSorting.setEndRecordIdForNoOfRecordsCount();
							orderList = objService.viewBulkSIUploadReport(objDto);
							if(orderList!=null && objDto.getPagingSorting().getRecordCount()>ApplicationFlags.ReportsExcelMaxSize)
							{	
								forwardMapping = "BulkSIUploadReport";
								objForm.setReportProcessForLimit("1");
								objForm.setReportCurrentCount(String.valueOf(objDto.getPagingSorting().getRecordCount()));
								objDto.getPagingSorting().setRecordCount(0);
								objDto.getPagingSorting().setPageNumber(0);
								orderList=new ArrayList<BulkSIUploadReportDto>();
							}else if (orderList!=null && objDto.getPagingSorting().getRecordCount()<=ApplicationFlags.ReportsExcelMaxSize){
								pagingSorting.setPagingSorting(false, true);
								orderList = objService.viewBulkSIUploadReport(objDto);
							}else{
								orderList=null;
							}
						}else {
						//No of records limit : end
						orderList = objService.viewBulkSIUploadReport(objDto);
					}
			}

					

					if ("true".equals(inExcel)) {
						objForm.setBcpExcelList(orderList);
						request.setAttribute("BulkSIUploadReportInExcel", orderList);
					} else {
						request.setAttribute("BulkSIUploadReport", orderList);
						objForm.setCustomerList(orderList);
					}
				
			} catch (Exception ex) {
				return mapping.findForward(Messages
						.getMessageValue("errorGlobalForward"));
			}

			return mapping.findForward(forwardMapping);
		}
		
		
		public ActionForward viewAttributeDetailsReport(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {

			String forwardMapping = "";
			ArrayList<AttributeDetailsReportDTO> orderList = new ArrayList<AttributeDetailsReportDTO>();
			ReportsModel objService = new ReportsModel();
			ReportsBean objForm = (ReportsBean) form;
			AttributeDetailsReportDTO objDto = new AttributeDetailsReportDTO();
			String userId,interfaceId;
			try {
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				
				interfaceId=(String) request.getParameter("interfaceId");
				userId=objUserDto.getUserId();
				
				System.out.println(userId+" "+interfaceId);
				Utility.setFileDumpParams(request,objForm);//Check objForm 
				ArrayList errors = new ArrayList();
				objDto.setCustLogicalSI(objForm.getCustLogicalSI());
				
				//lawkush start
				
				objDto.setServiceTypeId(objForm.getServiceTypeId());
				objDto.setServiceTypeName(objForm.getServiceTypeName());
				//objDto.setServiceType(objForm.getServiceType());
				objDto.setFromDate(objForm.getFromDate());
				objDto.setToDate(objForm.getToDate());
				
				//lawkush end
				
				
				PagingSorting pagingSorting = objForm.getPagingSorting();

				//lawkush Start
				
				NewOrderModel modelObj=new NewOrderModel();
							
				ArrayList<NewOrderDto> listServiceNameType=new ArrayList<NewOrderDto>();
				listServiceNameType=modelObj.getServiceNameType();
				request.setAttribute("listServiceNameType", listServiceNameType);
				
				//lawkush End
				
					String inExcel = objForm.getToExcel();
					if ("true".equals(inExcel)) {
						pagingSorting.setPagingSorting(false, true);
						request.setAttribute("userId",objUserDto.getUserId());
						forwardMapping = "displayReportInExcel";
					} else {
						request.setAttribute("userId",objUserDto.getUserId());
						pagingSorting.setPagingSorting(true, true);
						forwardMapping = "AttributeDetailsReport";
					}

					pagingSorting.setDefaultifNotPresent("CUSTOMER_LOGICAL_SI_NO",
							PagingSorting.increment, 1);
					objDto.setPagingSorting(pagingSorting);
					String fromPageSubmit=request.getParameter("fromPageSubmit");				
					request.setAttribute("fromPageSubmit", fromPageSubmit);
					if("1".equals(fromPageSubmit))
					{

						//No of records limit : start
						if ("true".equals(inExcel)) {
							pagingSorting.setPagingSorting(true, true);
							pagingSorting.setStartRecordId(1);
							pagingSorting.setEndRecordIdForNoOfRecordsCount();
							orderList = objService.viewAttributeDetailsReport(objDto);
							if(orderList!=null && objDto.getPagingSorting().getRecordCount()>ApplicationFlags.ReportsExcelMaxSize)
							{	
								forwardMapping = "AttributeDetailsReport";
								objForm.setReportProcessForLimit("1");
								objForm.setReportCurrentCount(String.valueOf(objDto.getPagingSorting().getRecordCount()));
								objDto.getPagingSorting().setRecordCount(0);
								objDto.getPagingSorting().setPageNumber(0);
								orderList=new ArrayList<AttributeDetailsReportDTO>();
							}else if (orderList!=null && objDto.getPagingSorting().getRecordCount()<=ApplicationFlags.ReportsExcelMaxSize){
								pagingSorting.setPagingSorting(false, true);
								orderList = objService.viewAttributeDetailsReport(objDto);
							}else{
								orderList=null;
							}
						}else {
						//No of records limit : end
						
						orderList = objService.viewAttributeDetailsReport(objDto);
					}
			}
					
					if ("true".equals(inExcel)) {
						objForm.setBcpExcelList(orderList);
						request.setAttribute("AttributeDetailsReportInExcel", orderList);
					} else {
						request.setAttribute("AttributeDetailsReport", orderList);
						objForm.setCustomerList(orderList);
					}
				
			} catch (Exception ex) {
				return mapping.findForward(Messages
						.getMessageValue("errorGlobalForward"));
			}

			return mapping.findForward(forwardMapping);
		}			
		
		public ActionForward viewDisconnectionChangeOrderReport(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {

			String forwardMapping = "";
			ArrayList<DisconnectChangeOrdeReportDTO> orderList = new ArrayList<DisconnectChangeOrdeReportDTO>();
			ReportsModel objService = new ReportsModel();
			ReportsBean objForm = (ReportsBean) form;
			//ReportsDto objDto = new ReportsDto();
			DisconnectChangeOrdeReportDTO objDto=new DisconnectChangeOrdeReportDTO ();
			int isUsage =0;
			String userId,interfaceId;
			try {
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				interfaceId=(String) request.getParameter("interfaceId");
				userId=objUserDto.getUserId();
				System.out.println(userId+" "+interfaceId);
				Utility.setFileDumpParams(request,objForm);	
				ArrayList errors = new ArrayList();
				objDto.setOrdermonth(objForm.getOrdermonth());
				objDto.setToDate(objForm.getToDate());
				objDto.setFromDate(objForm.getFromDate());
				objDto.setServiceName(objForm.getServicename());
				objDto.setOrdersubtype(objForm.getOrdersubtype());
				objDto.setVerticalDetails(objForm.getVerticalDetails());
				objDto.setCus_segment(objForm.getCus_segment());
				objDto.setSrrequest(objForm.getSrrequest());
				
			    PagingSorting pagingSorting = objForm.getPagingSorting();

					String inExcel = objForm.getToExcel();
					if ("true".equals(inExcel)) {
						pagingSorting.setPagingSorting(false, true);
						request.setAttribute("userId",objUserDto.getUserId());
						forwardMapping = "displayReportInExcelExt";
					} else {
						pagingSorting.setPagingSorting(true, true);
						request.setAttribute("userId",objUserDto.getUserId());
						forwardMapping = "DisconnectChangeOrderReport";
					}

					pagingSorting.setDefaultifNotPresent("ORDERNO",
							PagingSorting.increment, 1);
					objDto.setPagingSorting(pagingSorting);
					String fromPageSubmit=request.getParameter("fromPageSubmit");				
					request.setAttribute("fromPageSubmit", fromPageSubmit);
					if("1".equals(fromPageSubmit))
					{//No of records limit : start
						if ("true".equals(inExcel)) {
							pagingSorting.setPagingSorting(true, true);
							pagingSorting.setStartRecordId(1);
							pagingSorting.setEndRecordIdForNoOfRecordsCount();
							orderList = objService.viewDisconnectionChangeOrderReport(objDto);//***************CHANGE HERE*****************
							if(orderList!=null && objDto.getPagingSorting().getRecordCount()>ApplicationFlags.ReportsExcelMaxSize)
							{	
								forwardMapping = "DisconnectChangeOrderReport";//***************CHANGE HERE*****************
								objForm.setReportProcessForLimit("1");
								objForm.setReportCurrentCount(String.valueOf(objDto.getPagingSorting().getRecordCount()));
								objDto.getPagingSorting().setRecordCount(0);
								objDto.getPagingSorting().setPageNumber(0);
								orderList=new ArrayList<DisconnectChangeOrdeReportDTO>();
							}else if (orderList!=null && objDto.getPagingSorting().getRecordCount()<=ApplicationFlags.ReportsExcelMaxSize){
								pagingSorting.setPagingSorting(false, true);
								orderList = objService.viewDisconnectionChangeOrderReport(objDto);
							}else{
								orderList=null;
							}
						}else {orderList = objService.viewDisconnectionChangeOrderReport(objDto);
					}}

				   request.setAttribute("isUsage", isUsage);
					if ("true".equals(inExcel)) {
						objForm.setBcpExcelList(orderList);
						request.setAttribute("DisconnectChangeOrderReportInExcel", orderList);
					} else {
						request.setAttribute("DisconnectChangeOrderReport", orderList);
						request.setAttribute("custsegment", objDto.getCus_segment());
						objForm.setCustomerList(orderList);
					}
				
			} catch (Exception ex) {
				return mapping.findForward(Messages
						.getMessageValue("errorGlobalForward"));
			}

			return mapping.findForward(forwardMapping);
		}			
		
		public ActionForward viewMigAppNewOrderDetails(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
		{
			String forwardMapping = "";
			ArrayList<MigratedApprovedNewOrderDetailReportDTO> listSearchDetails = new ArrayList<MigratedApprovedNewOrderDetailReportDTO>();
			ReportsModel objService = new ReportsModel();
			ReportsBean objForm = (ReportsBean) form;
			MigratedApprovedNewOrderDetailReportDTO objDto = new MigratedApprovedNewOrderDetailReportDTO();
			String userId,interfaceId;
			try {
					HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
					HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
					UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
					interfaceId=(String) request.getParameter("interfaceId");
					userId=objUserDto.getUserId();
					System.out.println(userId+" "+interfaceId);
					Utility.setFileDumpParams(request,objForm);
					objDto.setOrdermonth(objForm.getOrdermonth());
					objDto.setServiceName(objForm.getServicename());				
					objDto.setOrderyear(objForm.getOrderyear());
					PagingSorting pagingSorting = objForm.getPagingSorting();
					String inExcel = objForm.getToExcel();
					if ("true".equals(inExcel)) 
					{
						pagingSorting.setPagingSorting(false, true);
						request.setAttribute("userId",objUserDto.getUserId());
						forwardMapping = "displayReportInExcelExt";
					}
					else 
					{
						pagingSorting.setPagingSorting(true, true);
						request.setAttribute("userId",objUserDto.getUserId());
						forwardMapping = "displayMigAppNewOrderDetails";
					}
					pagingSorting.setDefaultifNotPresent("ORDERNO",
							PagingSorting.increment, 1);
					objDto.setPagingSorting(pagingSorting);
					String fromPageSubmit=request.getParameter("fromPageSubmit");				
					request.setAttribute("fromPageSubmit", fromPageSubmit);
					if("1".equals(fromPageSubmit))
					{
						//No of records limit : start
						if ("true".equals(inExcel)) {
							pagingSorting.setPagingSorting(true, true);
							pagingSorting.setStartRecordId(1);
							pagingSorting.setEndRecordIdForNoOfRecordsCount();
							listSearchDetails = objService.viewMigAppNewOrderDetails(objDto);//***************CHANGE HERE*****************
							if(listSearchDetails!=null && objDto.getPagingSorting().getRecordCount()>ApplicationFlags.ReportsExcelMaxSize)
							{	
								forwardMapping = "displayMigAppNewOrderDetails";//***************CHANGE HERE*****************
								objForm.setReportProcessForLimit("1");
								objForm.setReportCurrentCount(String.valueOf(objDto.getPagingSorting().getRecordCount()));
								objDto.getPagingSorting().setRecordCount(0);
								objDto.getPagingSorting().setPageNumber(0);
								listSearchDetails=new ArrayList<MigratedApprovedNewOrderDetailReportDTO>();
							}else if (listSearchDetails!=null && objDto.getPagingSorting().getRecordCount()<=ApplicationFlags.ReportsExcelMaxSize){
								pagingSorting.setPagingSorting(false, true);
								listSearchDetails = objService.viewMigAppNewOrderDetails(objDto);
							}else{
								listSearchDetails=null;
							}
						}else {
						listSearchDetails = objService.viewMigAppNewOrderDetails(objDto);
					}	}			
					if ("true".equals(inExcel)) 
					{
						objForm.setBcpExcelList(listSearchDetails);
						request.setAttribute("MigAppNewOrderDetailsExcelList", listSearchDetails);
					}
					else 
					{
						request.setAttribute("MigAppNewOrderDetailsList", listSearchDetails);
						objForm.setCustomerList(listSearchDetails);
					}
			} 
			catch (Exception ex) {
				return mapping.findForward(Messages
						.getMessageValue("errorGlobalForward"));
			}
			return mapping.findForward(forwardMapping);
		}
		
	/**
	 * Meenakshi  : 
	 * Method to check all input values and fetch data for LEPM Owner. 
	 * and return forward appropriately.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewLEPMOwnerReport(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		String forwardMapping = "";
		ArrayList<LempOwnerReportDTO> listSearchDetails = new ArrayList<LempOwnerReportDTO>();
		ReportsModel objService = new ReportsModel();
		ReportsBean objForm = (ReportsBean) form;
		LempOwnerReportDTO objDto = new LempOwnerReportDTO();
		String userId,interfaceId;
		try {
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				interfaceId=(String) request.getParameter("interfaceId");
				userId=objUserDto.getUserId();
				System.out.println(userId+" "+interfaceId);
			Utility.setFileDumpParams(request,objForm);//Check objForm 
				objDto.setOrderNo(objForm.getOrderNo());
				//objDto.setCopcApproveDate(objForm.getCopcApproveDate());
				//[303030]START
				objDto.setCopcApproveFromDate(objForm.getCopcapprovalfromdate());
				objDto.setCopcApproveToDate(objForm.getCopcapprovaltodate());
				objDto.setFromDate(objForm.getFromDate());
				objDto.setToDate(objForm.getToDate());
				//[303030]END
				PagingSorting pagingSorting = objForm.getPagingSorting();
				String inExcel = objForm.getToExcel();
				if ("true".equals(inExcel)) 
				{
					pagingSorting.setPagingSorting(false, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "displayReportInExcelExt";
				}
				else 
				{
					pagingSorting.setPagingSorting(true, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "LEPMOwnerReport";
				}
				pagingSorting.setDefaultifNotPresent("ORDERNO",
						PagingSorting.increment, 1);
				objDto.setPagingSorting(pagingSorting);
				String fromPageSubmit=request.getParameter("fromPageSubmit");				
				request.setAttribute("fromPageSubmit", fromPageSubmit);
				if("1".equals(fromPageSubmit))
				{//No of records limit : start
					if ("true".equals(inExcel)) {
						pagingSorting.setPagingSorting(true, true);
						pagingSorting.setStartRecordId(1);
						pagingSorting.setEndRecordIdForNoOfRecordsCount();
						listSearchDetails = objService.viewLEPMOwnerReport(objDto);
						if(listSearchDetails!=null && objDto.getPagingSorting().getRecordCount()>ApplicationFlags.ReportsExcelMaxSize)
						{	
							forwardMapping = "LEPMOwnerReport";
							objForm.setReportProcessForLimit("1");
							objForm.setReportCurrentCount(String.valueOf(objDto.getPagingSorting().getRecordCount()));
							objDto.getPagingSorting().setRecordCount(0);
							objDto.getPagingSorting().setPageNumber(0);
							listSearchDetails=new ArrayList<LempOwnerReportDTO>();
						}else if (listSearchDetails!=null && objDto.getPagingSorting().getRecordCount()<=ApplicationFlags.ReportsExcelMaxSize){
							pagingSorting.setPagingSorting(false, true);
							listSearchDetails = objService.viewLEPMOwnerReport(objDto);
						}else{
							listSearchDetails=null;
						}
					}else {
					//No of records limit : end
					listSearchDetails = objService.viewLEPMOwnerReport(objDto);
				}
		}
				if ("true".equals(inExcel)) 
				{
					objForm.setBcpExcelList(listSearchDetails);
					request.setAttribute("LEPMOwnerExcelList", listSearchDetails);
				}
				else 
				{
					request.setAttribute("LEPMOwnerList", listSearchDetails);
					objForm.setCustomerList(listSearchDetails);
				}
		} 
		catch (Exception ex) {
			return mapping.findForward(Messages
					.getMessageValue("errorGlobalForward"));
		}
		return mapping.findForward(forwardMapping);
	}

// 	001 start
		
	    /**
	     * Create a Report to generate LEPM Order Cancel Report
	     
	     * @param obj    Action parameters
	     * @return      Redirect to LEPMOrderCancelReport.jsp
	     * @exception   Redirect to Error page if forward string does not exist
	     *            object
	     */		
		
		
		public ActionForward viewLEPMOrderCancelReport(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {

			String forwardMapping = "";
			ArrayList<LempCancelOrderReportDTO> orderList = new ArrayList<LempCancelOrderReportDTO>();
			ReportsModel objService = new ReportsModel();
			ReportsBean objForm = (ReportsBean) form;
			LempCancelOrderReportDTO objDto = new LempCancelOrderReportDTO();
			String userId,interfaceId;
			try {
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				interfaceId=(String) request.getParameter("interfaceId");
				userId=objUserDto.getUserId();
				System.out.println(userId+" "+interfaceId);
				Utility.setFileDumpParams(request,objForm);//Check objForm 
				//objDto.setCanceldate(objForm.getCanceldate());
				// [101010] START
				objDto.setCanceldatefrom(objForm.getCanceldatefrom());
				objDto.setCanceldateto(objForm.getCanceldateto());
				//[101010] END
				PagingSorting pagingSorting = objForm.getPagingSorting();

					String inExcel = objForm.getToExcel();
					if ("true".equals(inExcel)) {
						pagingSorting.setPagingSorting(false, true);
						request.setAttribute("userId",objUserDto.getUserId());
						forwardMapping = "displayReportInExcelExt";
					} else {
						pagingSorting.setPagingSorting(true, true);
						request.setAttribute("userId",objUserDto.getUserId());
						forwardMapping = "DisplayLEPMOrderCancelReport";
					}

					pagingSorting.setDefaultifNotPresent("ORDERNO",
							PagingSorting.increment, 1);
					objDto.setPagingSorting(pagingSorting);
					String fromPageSubmit=request.getParameter("fromPageSubmit");				
					request.setAttribute("fromPageSubmit", fromPageSubmit);
					if("1".equals(fromPageSubmit))
					{
//						No of records limit : start
						if ("true".equals(inExcel)) {
							pagingSorting.setPagingSorting(true, true);
							pagingSorting.setStartRecordId(1);
							pagingSorting.setEndRecordIdForNoOfRecordsCount();
							orderList = objService.viewLEPMOrderCancelReport(objDto);
							if(orderList!=null && objDto.getPagingSorting().getRecordCount()>ApplicationFlags.ReportsExcelMaxSize)
							{	
								forwardMapping = "DisplayLEPMOrderCancelReport";
								objForm.setReportProcessForLimit("1");
								objForm.setReportCurrentCount(String.valueOf(objDto.getPagingSorting().getRecordCount()));
								objDto.getPagingSorting().setRecordCount(0);
								objDto.getPagingSorting().setPageNumber(0);
								orderList=new ArrayList<LempCancelOrderReportDTO>();
							}else if (orderList!=null && objDto.getPagingSorting().getRecordCount()<=ApplicationFlags.ReportsExcelMaxSize){
								pagingSorting.setPagingSorting(false, true);
								orderList = objService.viewLEPMOrderCancelReport(objDto);
							}else{
								orderList=null;
							}
						}else {
						//No of records limit : end
						orderList = objService.viewLEPMOrderCancelReport(objDto);
					}
					}

					if ("true".equals(inExcel)) {
						
						request.setAttribute("LEPMOrderCancelReportInExcel", orderList);
					} else {
						request.setAttribute("LEPMOrderCancelReport", orderList);
						
					}
				
			} catch (Exception ex) {
				return mapping.findForward(Messages
						.getMessageValue("errorGlobalForward"));
			}

			return mapping.findForward(forwardMapping);
		}
		
//	 	001 end


//002 start		
	    /**
	     * To get the data of  LEPM Order Detail Report
	     
	     * @param obj    Action parameters
	     * @return      Redirect to LEPMOrderCancelReport.jsp
	     * @exception   Redirect to Error page if forward string does not exist
	     *            object
	     */	
		
		public ActionForward viewLEPMOrderDetailReport(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {

			String forwardMapping = "";
			ArrayList<LempOrderDetailsReportDTO> orderList = new ArrayList<LempOrderDetailsReportDTO>();
			ReportsModel objService = new ReportsModel();
			ReportsBean objForm = (ReportsBean) form;
			LempOrderDetailsReportDTO objDto = new LempOrderDetailsReportDTO();
			String userId,interfaceId;
			try {
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				interfaceId=(String) request.getParameter("interfaceId");
				userId=objUserDto.getUserId();
				System.out.println(userId+" "+interfaceId);
				Utility.setFileDumpParams(request,objForm);//Check objForm 
				objDto.setOrderType(objForm.getOrderType());
				// [202020] START
				//objDto.setCopcApproveDate(objForm.getCopcapprovaldate());
				objDto.setCopcApproveFromDate(objForm.getCopcapprovalfromdate());
				//objDto.setVerticalDetails(objForm.getVerticalDetails());
				objDto.setCopcApproveToDate(objForm.getCopcapprovaltodate());
				//[202020] END
				objDto.setToOrderNo(objForm.getToOrderNo());
				objDto.setFromOrderNo(objForm.getFromOrderNo());
				PagingSorting pagingSorting = objForm.getPagingSorting();

					String inExcel = objForm.getToExcel();
					if ("true".equals(inExcel)) {
						pagingSorting.setPagingSorting(false, true);
						request.setAttribute("userId",objUserDto.getUserId());
						forwardMapping = "displayReportInExcelExt";
					} else {
						pagingSorting.setPagingSorting(true, true);
						request.setAttribute("userId",objUserDto.getUserId());
						forwardMapping = "DisplayLEPMOrderReport";
					}

					pagingSorting.setDefaultifNotPresent("ORDERNO",
							PagingSorting.increment, 1);
					objDto.setPagingSorting(pagingSorting);
					String fromPageSubmit=request.getParameter("fromPageSubmit");				
					request.setAttribute("fromPageSubmit", fromPageSubmit);
					if("1".equals(fromPageSubmit))
					{//No of records limit : start
						if ("true".equals(inExcel)) {
							pagingSorting.setPagingSorting(true, true);
							pagingSorting.setStartRecordId(1);
							pagingSorting.setEndRecordIdForNoOfRecordsCount();
							orderList = objService.viewLEPMOrderDetailReport(objDto);
							if(orderList!=null && objDto.getPagingSorting().getRecordCount()>ApplicationFlags.ReportsExcelMaxSize)
							{	
								forwardMapping = "DisplayLEPMOrderReport";
								objForm.setReportProcessForLimit("1");
								objForm.setReportCurrentCount(String.valueOf(objDto.getPagingSorting().getRecordCount()));
								objDto.getPagingSorting().setRecordCount(0);
								objDto.getPagingSorting().setPageNumber(0);
								orderList=new ArrayList<LempOrderDetailsReportDTO>();
							}else if (orderList!=null && objDto.getPagingSorting().getRecordCount()<=ApplicationFlags.ReportsExcelMaxSize){
								pagingSorting.setPagingSorting(false, true);
								orderList = objService.viewLEPMOrderDetailReport(objDto);
							}else{
								orderList=null;
							}
						}else {
						//No of records limit : end
						orderList = objService.viewLEPMOrderDetailReport(objDto);
					}
					}

					if ("true".equals(inExcel)) {
						request.setAttribute("LEPMOrderDetailReportInExcel", orderList);
					} else {
						request.setAttribute("LEPMOrderDetailList", orderList);
					}
				
			} catch (Exception ex) {
				return mapping.findForward(Messages
						.getMessageValue("errorGlobalForward"));
			}

			return mapping.findForward(forwardMapping);
		}	
// 002 end	

//		003 start		
	    /**
	     * To get the data of  Pending Billing Permanent DisconnectionReport
	     
	     * @param obj    Action parameters
	     * @return      Redirect to PermanentDisconnectionReport.jsp
	     * @exception   Redirect to Error page if forward string does not exist
	     *            object
	     */	
		 
public ActionForward viewPendingBillingPDOrderList(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {

		String forwardMapping = "";
		ArrayList<ReportsDto> PDorderList = new ArrayList<ReportsDto>();
		ReportsModel objService = new ReportsModel();
		ReportsDto objDto = new ReportsDto();
		ReportsBean objForm = (ReportsBean) form;
		String userId,interfaceId;
		try 
		{
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			interfaceId=(String) request.getParameter("interfaceId");
			userId=objUserDto.getUserId();
			System.out.println(userId+" "+interfaceId);
			//[0012] start
			Utility.setFileDumpParams(request, objForm);
			//[0012] start
			objDto.setSearchCRMOrder(objForm.getOrderNo());
			objDto.setSearchfromDate(objForm.getFromDate());
			objDto.setSearchToDate(objForm.getToDate());
			objDto.setSearchSRNO(objForm.getSrNo());
			objDto.setSearchLSI(objForm.getLogical_SI_No());
			PagingSorting pagingSorting = objForm.getPagingSorting();
			String inExcel = objForm.getToExcel();
			if ("true".equals(inExcel)) {
					pagingSorting.setPagingSorting(false, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "displayReportInExcelExt";
			} else {
					pagingSorting.setPagingSorting(true, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "DisplayPendingBillingPDOrderReport";
			}

			/*pagingSorting.setDefaultifNotPresent("ORDERNO",
			PagingSorting.increment, 1);*/
			pagingSorting.setDefaultifNotPresent("LSINO",PagingSorting.increment, 1);
			objDto.setPagingSorting(pagingSorting);
			String fromPageSubmit=request.getParameter("fromPageSubmit");				
			request.setAttribute("fromPageSubmit", fromPageSubmit);
			if("1".equals(fromPageSubmit))
			{
				PDorderList = objService.viewPendingBillingPDOrderList(objDto);
			}

			if ("true".equals(inExcel)) {
					request.setAttribute("pendingBillingPDOrderListInExcel", PDorderList);
			} else {
					request.setAttribute("pendingOrderList", PDorderList);
			}
			
		} catch (Exception ex) {
			return mapping.findForward(Messages
					.getMessageValue("errorGlobalForward"));
		}

		return mapping.findForward(forwardMapping);
	}
//003 end
	//[004] Start
	public ActionForward viewLSIMapping(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		LineItemDTO objDto=new LineItemDTO();
		ActionMessages messages = new ActionMessages();
		ArrayList<LineItemDTO> LSIMappingList = new ArrayList<LineItemDTO>();
		ReportsBean objForm = (ReportsBean) form;
		ReportsModel model=new ReportsModel();
		String userId,interfaceId;
		try
		{
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			interfaceId=(String) request.getParameter("interfaceId");
			userId=objUserDto.getUserId();
			System.out.println(userId+" "+interfaceId);
			String inExcel = objForm.getToExcel();
			if ("true".equals(inExcel)) 
			{
				if(objForm.getAccountNumber()==null)
				{
					objDto.setAccountNo("");
				}
				else
				{
					objDto.setAccountNo(objForm.getAccountNumber());
				}
				objDto.setAccountName(objForm.getAccountName());
				objDto.setLsiNO(objForm.getLogicalSINo());
				objDto.setRedirectedLSINo(objForm.getRedLSiNumber());
				objDto.setMappedLSINo(objForm.getMappedLSINumber());
				objDto.setPagingRequired(0);		
				LSIMappingList = model.viewLSIMapping(objDto);
				request.setAttribute("LSIMappingList", LSIMappingList);
				request.setAttribute("userId",objUserDto.getUserId());
				forward = mapping.findForward("viewLSIMappingExcel");
			} 
			else 
			{
				request.setAttribute("userId",objUserDto.getUserId());
				forward = mapping.findForward("viewLSIMapping");
			}
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		return forward;
	}
	//[004] End
	//[006] Start
	public ActionForward viewHardwareCancelReport(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		String forwardMapping = "";
		ArrayList<HardwareLineItemCancelReportDTO> HardwareLineItemList = new ArrayList<HardwareLineItemCancelReportDTO>();
		ReportsModel objReportModel = new ReportsModel();
		HardwareLineItemCancelReportDTO objDto = new HardwareLineItemCancelReportDTO();
		ReportsBean objForm = (ReportsBean) form;
		String userId,interfaceId;
		try 
		{
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			interfaceId=(String) request.getParameter("interfaceId");
			userId=objUserDto.getUserId();
			System.out.println(userId+" "+interfaceId);
			Utility.setFileDumpParams(request,objForm);//Check objForm 
			objDto.setSearchfromDate(objForm.getFromDate());//Searching From Request Date
			objDto.setSearchToDate(objForm.getToDate());//Searching From-To Request Date
			objDto.setSearchSRNO(objForm.getSrNo());//Searching From-RequestID
			objDto.setSearchLSI(objForm.getLogicalSINo());//Searching From-LSI No
			objDto.setSearchAccount(objForm.getCrmAccountNo());//Searching From-A\c No
			objDto.setSearchAccountName(objForm.getAccountName());//Searching From-A\c Name
			objDto.setSearchLineNo(objForm.getSearchLineItemNo());//Searching From-Line No
			
			PagingSorting pagingSorting = objForm.getPagingSorting();
			String inExcel = objForm.getToExcel();
			if ("true".equals(inExcel)) 
			{
				pagingSorting.setPagingSorting(false, true);
				//forwardMapping = "displayReportInExcelExt";
				request.setAttribute("userId",objUserDto.getUserId());
				forwardMapping = "DisplayCancelledhardwareLineReportExcel";
			} 
			else 
			{
				pagingSorting.setPagingSorting(true, true);
				request.setAttribute("userId",objUserDto.getUserId());
				forwardMapping = "DisplayCancelLineItemReport";
			}

			pagingSorting.setDefaultifNotPresent("REQUESTID",PagingSorting.increment, 1);
			objDto.setPagingSorting(pagingSorting);
			String fromPageSubmit=request.getParameter("fromPageSubmit");				
			request.setAttribute("fromPageSubmit", fromPageSubmit);
			//if("1".equals(fromPageSubmit))
			//{
			

			//No of records limit : start
			if ("true".equals(inExcel)) {
				pagingSorting.setPagingSorting(true, true);
				pagingSorting.setStartRecordId(1);
				pagingSorting.setEndRecordIdForNoOfRecordsCount();
				HardwareLineItemList = objReportModel.viewHardwareCancelReport(objDto);
				if(HardwareLineItemList!=null && objDto.getPagingSorting().getRecordCount()>ApplicationFlags.ReportsExcelMaxSize)
				{	
					forwardMapping = "DisplayCancelLineItemReport";
					objForm.setReportProcessForLimit("1");
					objForm.setReportCurrentCount(String.valueOf(objDto.getPagingSorting().getRecordCount()));
					objDto.getPagingSorting().setRecordCount(0);
					objDto.getPagingSorting().setPageNumber(0);
					HardwareLineItemList=new ArrayList<HardwareLineItemCancelReportDTO>();
				}else if (HardwareLineItemList!=null && objDto.getPagingSorting().getRecordCount()<=ApplicationFlags.ReportsExcelMaxSize){
					pagingSorting.setPagingSorting(false, true);
					HardwareLineItemList = objReportModel.viewHardwareCancelReport(objDto);
				}else{
					HardwareLineItemList=null;
				}
			}else {
			//No of records limit : end
			
				HardwareLineItemList = objReportModel.viewHardwareCancelReport(objDto);
			//}
			}
			if ("true".equals(inExcel)) 
			{
				request.setAttribute("HardwareLineItemList", HardwareLineItemList);
			} 
			else 
			{
				request.setAttribute("cancelHardwareLineItemList", HardwareLineItemList);
			}
			
		} 
		catch (Exception ex) 
		{
			return mapping.findForward(Messages.getMessageValue("errorGlobalForward"));
		}
		return mapping.findForward(forwardMapping);	
	}
	
	public ActionForward fetchRequestHistory(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		ActionMessages messages = new ActionMessages();
		ReportsBean objBean = (ReportsBean)form;
		try
		{
			objBean.setSrNo(request.getParameter("requestID"));
			forward = mapping.findForward("DisplayRequestHistory");
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		return forward;
	}
	//[006] End
//[404040]START
public ActionForward viewOrderClepReport(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

	String forwardMapping = "";
	ArrayList<OrderClepReportDTO> orderList = new ArrayList<OrderClepReportDTO>();
	ReportsModel objService = new ReportsModel();
	ReportsBean objForm = (ReportsBean) form;
	OrderClepReportDTO objDto = new OrderClepReportDTO();
	String userId,interfaceId;
	try {
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			interfaceId=(String) request.getParameter("interfaceId");
			userId=objUserDto.getUserId();
			System.out.println(userId+" "+interfaceId);	
		Utility.setFileDumpParams(request,objForm);
			objDto.setOrderType(objForm.getOrderType());
			objDto.setToDate(objForm.getToDate());
			objDto.setFromDate(objForm.getFromDate());
			objDto.setOsp(objForm.getOsp());
			PagingSorting pagingSorting = objForm.getPagingSorting();
			String inExcel = objForm.getToExcel();
			if ("true".equals(inExcel)) 
			{
				pagingSorting.setPagingSorting(false, true);
				request.setAttribute("userId",objUserDto.getUserId());
				forwardMapping = "displayReportInExcelForClepOrder";
			}
			else 
			{
				pagingSorting.setPagingSorting(true, true);
				request.setAttribute("userId",objUserDto.getUserId());
				forwardMapping = "displayOrderClepReport";
			}

			pagingSorting.setDefaultifNotPresent("ORDERNO",PagingSorting.increment, 1);
			objDto.setPagingSorting(pagingSorting);
			if ("true".equals(inExcel)) {
				pagingSorting.setPagingSorting(true, true);
				pagingSorting.setStartRecordId(1);
				pagingSorting.setEndRecordIdForNoOfRecordsCount();
				orderList = objService.viewClepOrderReport(objDto);//***************CHANGE HERE*****************
				if(orderList!=null && objDto.getPagingSorting().getRecordCount()>ApplicationFlags.ReportsExcelMaxSize)
				{	
					forwardMapping = "displayOrderClepReport";//***************CHANGE HERE*****************
					objForm.setReportProcessForLimit("1");
					objForm.setReportCurrentCount(String.valueOf(objDto.getPagingSorting().getRecordCount()));
					objDto.getPagingSorting().setRecordCount(0);
					objDto.getPagingSorting().setPageNumber(0);
					orderList=new ArrayList<OrderClepReportDTO>();
				}else if (orderList!=null && objDto.getPagingSorting().getRecordCount()<=ApplicationFlags.ReportsExcelMaxSize){
					pagingSorting.setPagingSorting(false, true);
					orderList = objService.viewClepOrderReport(objDto);
				}else{
					orderList=null;
				}
			}else {
			orderList = objService.viewClepOrderReport(objDto);
			}
	
			if ("true".equals(inExcel)) 
			{
				objForm.setBcpExcelList(orderList);
				request.setAttribute("orderClepExcelList", orderList);
			}
			else 
			{
				request.setAttribute("orderClepList", orderList);
				objForm.setCustomerList(orderList);
			}
	}
	catch (Exception ex) {
		return mapping.findForward(Messages
				.getMessageValue("errorGlobalForward"));
	}

	return mapping.findForward(forwardMapping);
}
//[404040]END
	/*[HYPR22032013001] -- start */
	public ActionForward viewBillingWorkQueueReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String forwardMapping = "";
		ArrayList<BillingWorkQueueReportDTO> orderList = new ArrayList<BillingWorkQueueReportDTO>();
		ReportsModel objService = new ReportsModel();
		ReportsBean objForm = (ReportsBean) form;
		BillingWorkQueueReportDTO objDto = new BillingWorkQueueReportDTO();
		String userId,interfaceId;
		try {
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			interfaceId=(String) request.getParameter("interfaceId");
			userId=objUserDto.getUserId();
			System.out.println(userId+" "+interfaceId);
			
			Utility.setFileDumpParams(request,objForm);
			
			ArrayList errors = new ArrayList();
			objDto.setOrderType(objForm.getOrderType());
			objDto.setToDate(objForm.getToDate());
			objDto.setFromDate(objForm.getFromDate());
			objDto.setToOrderNo(objForm.getToOrderNo());
			objDto.setFromOrderNo(objForm.getFromOrderNo());
			/*
			 * add party no and party name
			 */
			objDto.setParty_no(objForm.getPartyNo());
			objDto.setPartyName(objForm.getSourcePartyName());
			objDto.setOrderStage(objForm.getOrderStageDescription());
			/*set hardware type */
			objDto.setHardwareType(objForm.getIsHardware());
			
			PagingSorting pagingSorting = objForm.getPagingSorting();
			ArrayList<NewOrderDto> listChangeType=new ArrayList<NewOrderDto>();
			ArrayList<NewOrderDto> listOfOrderStage = new ArrayList<NewOrderDto>();
			ArrayList<NewOrderDto> listOfServiceStage = new ArrayList<NewOrderDto>();
			NewOrderModel modelObj=new NewOrderModel();
			listChangeType=modelObj.getChangeType();
			listOfOrderStage = modelObj.getOrderStageList();
			listOfServiceStage = modelObj.getServiceStageList();
			
			request.setAttribute("listChangeType", listChangeType);
			request.setAttribute("listOrderStage", listOfOrderStage);
			request.setAttribute("listOfServiceStage", listOfServiceStage);
				String inExcel = objForm.getToExcel();
				if ("true".equals(inExcel)) {
					pagingSorting.setPagingSorting(false, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "displayReportInExcelExt";
				} else {
					pagingSorting.setPagingSorting(true, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "viewBillingWorkQueueReport";
				}

				pagingSorting.setDefaultifNotPresent("accountId",
						PagingSorting.increment, 1);
				objDto.setPagingSorting(pagingSorting);
				String fromPageSubmit=request.getParameter("fromPageSubmit");				
				request.setAttribute("fromPageSubmit", fromPageSubmit);
				if("1".equals(fromPageSubmit))
				{
//					No of records limit : start
					if ("true".equals(inExcel)) {
						pagingSorting.setPagingSorting(true, true);
						pagingSorting.setStartRecordId(1);
						pagingSorting.setEndRecordIdForNoOfRecordsCount();
						orderList = objService.viewBillingWorkQueueList(objDto);
						if(orderList!=null && objDto.getPagingSorting().getRecordCount()>ApplicationFlags.ReportsExcelMaxSize)
						{	
							forwardMapping = "viewBillingWorkQueueReport";
							objForm.setReportProcessForLimit("1");
							objForm.setReportCurrentCount(String.valueOf(objDto.getPagingSorting().getRecordCount()));
							objDto.getPagingSorting().setRecordCount(0);
							objDto.getPagingSorting().setPageNumber(0);
							orderList=new ArrayList<BillingWorkQueueReportDTO>();
						}else if (orderList!=null && objDto.getPagingSorting().getRecordCount()<=ApplicationFlags.ReportsExcelMaxSize){
							pagingSorting.setPagingSorting(false, true);
							orderList = objService.viewBillingWorkQueueList(objDto);
						}else{
							orderList=null;
						}
					}else {
					//No of records limit : end
				//orderList = objService.viewActiveLineItemsList(objDto);
					orderList = objService.viewBillingWorkQueueList(objDto);
					}
				}
				if ("true".equals(inExcel)) {
					objForm.setBcpExcelList(orderList);
					request.setAttribute("viewBillingWorkQueueList", orderList);
				} else {
					request.setAttribute("customerList", orderList);
					objForm.setCustomerList(orderList);
				}
			
		} catch (Exception ex) {
			return mapping.findForward(Messages
					.getMessageValue("errorGlobalForward"));
		}

		return mapping.findForward(forwardMapping);
	}
	/*[HYPR22032013001] -- end */

	/*
	 * [HYPR22032013001] -- start
	 * get the party data for Billing Work queue reports 
	 */
	public ActionForward fetchPartyForBillingWorkQueue(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		ReportsModel objModel=new ReportsModel();
		ActionMessages messages = new ActionMessages();
		ArrayList<SITransferDto> listParty = new ArrayList<SITransferDto>();
		try
		{
			//listParty = objModel.fetchParty();
			//request.setAttribute("PartyList", listParty);
			forward = mapping.findForward("DisplaySelectParty");
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		return forward;
	}
	/*[HYPR22032013001] -- end */
	
	/*Vijay start. Add method for cancelcopy reprot */
	public ActionForward ViewCancelCopyReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String forwardMapping = "";
		ArrayList<CopyCancelReportDTO> orderList = new ArrayList<CopyCancelReportDTO>();
		ReportsModel objService = new ReportsModel();
		ReportsBean objForm = (ReportsBean) form;
		CopyCancelReportDTO objDto = new CopyCancelReportDTO();
		String userId,interfaceId;
		try {
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			interfaceId=(String) request.getParameter("interfaceId");
			userId=objUserDto.getUserId();
			System.out.println(userId+" "+interfaceId);
			Utility.setFileDumpParams(request,objForm);
			objDto.setToDate(objForm.getToDate());
			objDto.setFromDate(objForm.getFromDate());
			objDto.setFromOrderNo(objForm.getFromOrderNo());
			objDto.setToOrderNo(objForm.getToOrderNo());
			
			PagingSorting pagingSorting = objForm.getPagingSorting();

				String inExcel = objForm.getToExcel();
				if ("true".equals(inExcel)) {
					pagingSorting.setPagingSorting(false, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "displayReportInExcel";
				} else {
					pagingSorting.setPagingSorting(true, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "copyCancelReport";
				}

				pagingSorting.setDefaultifNotPresent("ORDERNO",
						PagingSorting.increment, 1);
				objDto.setPagingSorting(pagingSorting);
				String fromPageSubmit=request.getParameter("fromPageSubmit");
				request.setAttribute("fromPageSubmit", fromPageSubmit);
				if("1".equals(fromPageSubmit))
				{//No of records limit : start
					if ("true".equals(inExcel)) {
						pagingSorting.setPagingSorting(true, true);
						pagingSorting.setStartRecordId(1);
						pagingSorting.setEndRecordIdForNoOfRecordsCount();
						orderList = objService.cancelCopyReport(objDto);//***************CHANGE HERE*****************
						if(orderList!=null && objDto.getPagingSorting().getRecordCount()>ApplicationFlags.ReportsExcelMaxSize)
						{	
							forwardMapping = "copyCancelReport";//***************CHANGE HERE*****************
							objForm.setReportProcessForLimit("1");
							objForm.setReportCurrentCount(String.valueOf(objDto.getPagingSorting().getRecordCount()));
							objDto.getPagingSorting().setRecordCount(0);
							objDto.getPagingSorting().setPageNumber(0);
							orderList=new ArrayList<CopyCancelReportDTO>();
						}else if (orderList!=null && objDto.getPagingSorting().getRecordCount()<=ApplicationFlags.ReportsExcelMaxSize){
							pagingSorting.setPagingSorting(false, true);
							orderList = objService.cancelCopyReport(objDto);
						}else{
							orderList=null;
						}
					}else {
					orderList = objService.cancelCopyReport(objDto);
				}}
				if ("true".equals(inExcel)) {
					objForm.setBcpExcelList(orderList);
					request.setAttribute("copyCancelReportListExcel", orderList);
				} else {
					request.setAttribute("copyCancelReportList", orderList);
					objForm.setCustomerList(orderList);
				}
			
		} catch (Exception ex) {
			return mapping.findForward(Messages
					.getMessageValue("errorGlobalForward"));
		}

		return mapping.findForward(forwardMapping);
	}
	/*Vijay end */
	
	public ActionForward getCancelledHardwareLineDetailsInExcel (ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); 
		ReportsModel objModel=new ReportsModel();
		ActionMessages messages = new ActionMessages();
		ArrayList<ProductCatelogDTO> cancelledLineList=null;
		long orderNo;
		try
		{
			orderNo = Long.parseLong(request.getParameter("orderNo"));
			cancelledLineList = objModel.getCancelledHardwareLineDetails(orderNo);
			request.setAttribute("CancelledHardwareLineDetailsList", cancelledLineList);
			forward = mapping.findForward("DisplayCancelledhardwareLineReportExcel");
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		return forward;
	}
	
	/**
	 * Action method to get the dump file. The name of the file to be searched upon will come under the request
	 * This method appends the _timestamp of the file creation in ddmmyyyy format to the name of the file.
	 *   
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getDumpFile(
			ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception {
		ServletOutputStream out = null;
		PrintWriter pw = null;
		FileInputStream fileInputStream = null;
		String fileName = request.getParameter("fileName");
		try{
			ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");
			String dirPath = bundle.getString("DUMP_FILE_PATH");
			File file = null;
			if(null != fileName && null != dirPath)
					file = new File(dirPath + fileName + ".zip");
			 if(null != file && file.length() != 0L){				
				//getting the last modified date
				SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
				String date = sdf.format(new java.util.Date(file.lastModified()));
				response.addHeader("Content-Disposition", "attachment; filename=" + fileName + "_" + date +".zip");
				fileInputStream  = new FileInputStream(file);
				byte[] buf=new byte[8192];
				out = response.getOutputStream(); 
				int bytesread = 0, bytesBuffered = 0;
				while( (bytesread = fileInputStream.read( buf )) > -1 ) {
					out.write( buf, 0, bytesread );
					bytesBuffered += bytesread;
					if (bytesBuffered > 1024 * 1024) { //flush after 1MB
						bytesBuffered = 0;
						out.flush();
					}
				}
			}else
			{
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("content-Disposition","attachment;filename=ErrorFile.xls");
				pw = response.getWriter();
				pw.write("Some Error Has Occured. Pls try again.");
				pw.flush();
			}
		}catch (Exception e){
			logger.error("Error in dump download for file " + fileName + " "+ e.getMessage());
		}finally{
			if(null != out)
				out.close();
			if(null != pw)
				pw.close();
			if(null != fileInputStream)
				fileInputStream.close();
		}
		return null;
	}
	
//[0099] start
	
	public ActionForward changePMWelcomeMail(ActionMapping mapping ,ActionForm form ,HttpServletRequest request,HttpServletResponse response ) throws Exception{
		ActionForward forward = new ActionForward(); // return value
		try
		{
			forward = mapping.findForward("changePMWelcomeMail");
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
		}
		return forward;
		
		
	}
	//[0099] end
	//[008]Start
	/*   	********************************************************************************
	 *		Method Name:- 	viewAdvancePaymentReport()
	 *		Created By:-    Shourya Mishra
	 * 		Created On:-    30-11-2013
	 * 		Purpose:-		Action Method for AdvancePayment Report.
	 *      ********************************************************************************
	 */ 		
	public ActionForward viewAdvancePaymentReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String forwardMapping = "";
		ArrayList<AdvancePaymentReportDTO> orderList = new ArrayList<AdvancePaymentReportDTO>();
		ReportsModel objService = new ReportsModel();
		ReportsBean objForm = (ReportsBean) form;
		AdvancePaymentReportDTO objDto = new AdvancePaymentReportDTO();
		String userId,interfaceId;
		try {
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				interfaceId=(String) request.getParameter("interfaceId");
				userId=objUserDto.getUserId();
				System.out.println(userId+" "+interfaceId);
				//Start [002]
				Utility.setFileDumpParams(request,objForm);
				//End [002]
				objDto.setCrmAccountNo(objForm.getCrmAccountNo());
				objDto.setArcChqNo(objForm.getArcChqNo());
				objDto.setOtcChqNo(objForm.getOtcChqNo());
				objDto.setFromorderCreationDate(objForm.getFromDate());
				objDto.setToorderCreationDate(objForm.getToDate());
				objDto.setFromChqDate(objForm.getFromOrderDate());
				objDto.setToChqDate(objForm.getToOrderDate());
				objDto.setDatetype(objForm.getDatetype());
				
				PagingSorting pagingSorting = objForm.getPagingSorting();

				String inExcel = objForm.getToExcel();
				if ("true".equals(inExcel)) 
				{
					pagingSorting.setPagingSorting(false, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "displayAdvancePaymentInExcelExt";
				}
				else 
				{
					pagingSorting.setPagingSorting(true, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "displayAdvancePaymentReport";
				}

				pagingSorting.setDefaultifNotPresent("CRMACCOUNTNO",PagingSorting.increment, 1);
				objDto.setPagingSorting(pagingSorting);
				String fromPageSubmit=request.getParameter("fromPageSubmit");				
				request.setAttribute("fromPageSubmit", fromPageSubmit);
				if("1".equals(fromPageSubmit))
				{
//					No of records limit : start
					if ("true".equals(inExcel)) {
						pagingSorting.setPagingSorting(true, true);
						pagingSorting.setStartRecordId(1);
						pagingSorting.setEndRecordIdForNoOfRecordsCount();
						orderList = objService.viewAdvancePaymentReport(objDto);
						if(orderList!=null && objDto.getPagingSorting().getRecordCount()>ApplicationFlags.ReportsExcelMaxSize)
						{	
							forwardMapping = "displayAdvancePaymentReport";
							objForm.setReportProcessForLimit("1");
							objForm.setReportCurrentCount(String.valueOf(objDto.getPagingSorting().getRecordCount()));
							objDto.getPagingSorting().setRecordCount(0);
							objDto.getPagingSorting().setPageNumber(0);
							orderList=new ArrayList<AdvancePaymentReportDTO>();
						}else if (orderList!=null && objDto.getPagingSorting().getRecordCount()<=ApplicationFlags.ReportsExcelMaxSize){
							pagingSorting.setPagingSorting(false, true);
							orderList = objService.viewAdvancePaymentReport(objDto);
						}else{
							orderList=null;
						}
					}else {
					//No of records limit : end
					orderList = objService.viewAdvancePaymentReport(objDto);
				}	
					}
				
				
				if ("true".equals(inExcel)) 
				{
					objForm.setBcpExcelList(orderList);
					request.setAttribute("displayAdvancePaymentInExcelExt", orderList);
				}
				else 
				{
					request.setAttribute("displayAdvancePaymentReport", orderList);
					objForm.setCustomerList(orderList);
				}
		} 
		catch (Exception ex) {
			return mapping.findForward(Messages
					.getMessageValue("errorGlobalForward"));
		}

		return mapping.findForward(forwardMapping);
	}
	//	[008]End
	//[009]Start		
	public ActionForward fetchTBTLineItemData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String forwardMapping = "";
		ArrayList<AdvancePaymentReportDTO> orderList = new ArrayList<AdvancePaymentReportDTO>();
		ReportsModel objService = new ReportsModel();
		ReportsBean objForm = (ReportsBean) form;
		AdvancePaymentReportDTO objDto = new AdvancePaymentReportDTO();
		try 
		{
			//[0015] start
			int actionType=Integer.parseInt(request.getParameter("actiontype"));
			objForm.setActiontype(actionType);
			objDto.setActiontype(actionType);
			//[0015] end
			forwardMapping = "displayBTLineItemData";
			orderList = objService.fetchTBTLineItemData(objDto);			
			objForm.setCustomerList(orderList);
			request.setAttribute("displayAdvancePaymentReport", orderList);
			
		} 
		catch (Exception ex) 
		{
			return mapping.findForward(Messages.getMessageValue("errorGlobalForward"));
		}

		return mapping.findForward(forwardMapping);
	}
	//	[009]End
	
	//[008] Start
	public ActionForward viewActiveLineDemoReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String forwardMapping = "";
		String userId,interfaceId;		
		ArrayList<ActiveLineDemoReportDTO> orderList = new ArrayList<ActiveLineDemoReportDTO>();
		ReportsModel objService = new ReportsModel();
		ReportsBean objForm = (ReportsBean) form;
		ActiveLineDemoReportDTO objDto=new ActiveLineDemoReportDTO();
		try {
			
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			interfaceId=(String) request.getParameter("interfaceId");
			userId=objUserDto.getUserId();
			System.out.println(userId+" "+interfaceId);
				Utility.setFileDumpParams(request,objForm);
			ArrayList errors = new ArrayList();
			System.out.println(interfaceId + " "+userId);
			String fromdate = request.getParameter("toDate");
			objDto.setToOrderDate(objForm.getToDate());
			objDto.setFromOrderDate(objForm.getFromDate());
			objDto.setToOrderNo(objForm.getToOrderNo());
			objDto.setFromOrderNo(objForm.getFromOrderNo());
			PagingSorting pagingSorting = objForm.getPagingSorting();

				String inExcel = objForm.getToExcel();
				if ("true".equals(inExcel)) {
					pagingSorting.setPagingSorting(false, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "displayActiveLineDemoReportInExcelExt";
				} else {
					pagingSorting.setPagingSorting(true, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "DisplayActiveLineDemoReport";
				}

				pagingSorting.setDefaultifNotPresent("accountId",
						PagingSorting.increment, 1);
				objDto.setPagingSorting(pagingSorting);
				String fromPageSubmit=request.getParameter("fromPageSubmit");				
				request.setAttribute("fromPageSubmit", fromPageSubmit);
				if("1".equals(fromPageSubmit))
				{
					//No of records limit : start
					if ("true".equals(inExcel)) {
						pagingSorting.setPagingSorting(true, true);
						pagingSorting.setStartRecordId(1);
						pagingSorting.setEndRecordIdForNoOfRecordsCount();
						orderList = objService.viewActiveLineDemoList(objDto);
						if(orderList!=null && objDto.getPagingSorting().getRecordCount()>ApplicationFlags.ReportsExcelMaxSize)
						{	
							forwardMapping = "displayActiveLineDemoReportInExcelExt";
							objForm.setReportProcessForLimit("1");
							objForm.setReportCurrentCount(String.valueOf(objDto.getPagingSorting().getRecordCount()));
							objDto.getPagingSorting().setRecordCount(0);
							objDto.getPagingSorting().setPageNumber(0);
							orderList=new ArrayList<ActiveLineDemoReportDTO>();
						}else if (orderList!=null && objDto.getPagingSorting().getRecordCount()<=ApplicationFlags.ReportsExcelMaxSize){
							pagingSorting.setPagingSorting(false, true);
							orderList = objService.viewActiveLineDemoList(objDto);
						}else{
							orderList=null;
						}
					}else {
					//No of records limit : end
				
						orderList = objService.viewActiveLineDemoList(objDto);
				}
				}
				if ("true".equals(inExcel)) {
					objForm.setBcpExcelList(orderList);
					request.setAttribute("demoReportListInExcel", orderList);
				} else {
					request.setAttribute("demoReportList", orderList);
					objForm.setCustomerList(orderList);
				}
			
			
		}catch (Exception ex) {
			return mapping.findForward(Messages
					.getMessageValue("errorGlobalForward"));
		}

		return mapping.findForward(forwardMapping);
	}
//[008] End	

	
	//Start [010]
	public ActionForward viewOBValueReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
			throws Exception {

		String forwardMapping = "";
		ArrayList<OBValueReportDTO> orderList = new ArrayList<OBValueReportDTO>();
		ReportsModel reportsModel = new ReportsModel();
		ReportsBean objForm = (ReportsBean) form;
		OBValueReportDTO objDto = new OBValueReportDTO();
		String userId,interfaceId;
		try {
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			interfaceId=(String) request.getParameter("interfaceId");
			userId=objUserDto.getUserId();
			System.out.println(userId+" "+interfaceId);
			Utility.setFileDumpParams(request,objForm);
			ArrayList errors = new ArrayList();
			Utility.setFileDumpParams(request,objForm);
			objDto.setOrderType(objForm.getOrderType());
			objDto.setToDate(objForm.getToDate());
			objDto.setFromDate(objForm.getFromDate());
			objDto.setVerticalDetails(objForm.getVerticalDetails());
			PagingSorting pagingSorting = objForm.getPagingSorting();

			String inExcel = objForm.getToExcel();
			if ("true".equals(inExcel)) {
				pagingSorting.setPagingSorting(false, true);
				request.setAttribute("userId",objUserDto.getUserId());
				forwardMapping = "displayOBReportInExcelExt";
			} else {
				pagingSorting.setPagingSorting(true, true);
				request.setAttribute("userId",objUserDto.getUserId());
				forwardMapping = "OBValueReport";
			}

			pagingSorting.setDefaultifNotPresent("ORDERNO", PagingSorting.increment, 1);
			objDto.setPagingSorting(pagingSorting);
			String fromPageSubmit=request.getParameter("fromPageSubmit");				
			request.setAttribute("fromPageSubmit", fromPageSubmit);
			if("1".equals(fromPageSubmit))
			{
				if ("true".equals(inExcel)) {
					pagingSorting.setPagingSorting(true, true);
					pagingSorting.setStartRecordId(1);
					pagingSorting.setEndRecordIdForNoOfRecordsCount();
					orderList = reportsModel.viewOBValueReport(objDto);
					if(orderList!=null && objDto.getPagingSorting().getRecordCount()>ApplicationFlags.ReportsExcelMaxSize)
					{	
						forwardMapping = "OBValueReport";
						objForm.setReportProcessForLimit("1");
						objForm.setReportCurrentCount(String.valueOf(objDto.getPagingSorting().getRecordCount()));
						objDto.getPagingSorting().setRecordCount(0);
						objDto.getPagingSorting().setPageNumber(0);
						orderList=new ArrayList<OBValueReportDTO>();
					}else if (orderList!=null && objDto.getPagingSorting().getRecordCount()<=ApplicationFlags.ReportsExcelMaxSize){
						pagingSorting.setPagingSorting(false, true);
						orderList = reportsModel.viewOBValueReport(objDto);
					}else{
						orderList=null;
					}
				}else{
					orderList = reportsModel.viewOBValueReport(objDto);
				}
			}
			if ("true".equals(inExcel)) {
				objForm.setBcpExcelList(orderList);
				request.setAttribute("displayOBReportInExcelExt", orderList);
			} else {
				request.setAttribute("OBValueReportList", orderList);
				objForm.setCustomerList(orderList);
			}
		} catch (Exception ex) {
			return mapping.findForward(Messages.getMessageValue("errorGlobalForward"));
		}
		return mapping.findForward(forwardMapping);
	}
	//End[010]
	/**
	 * method:viewDummyLineDetailsReport
	 * Purpose: To display all Dummy Lines status in report
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @author Anil Kumar
	 * @throws Exception
	 */
	public ActionForward viewDummyLineDetailsReport(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		String forwardMapping = "";
		ArrayList<DummyLinesDetailsReportDTO> listSearchDetails = new ArrayList<DummyLinesDetailsReportDTO>();
		ReportsModel objService = new ReportsModel();
		ReportsBean objForm = (ReportsBean) form;
		DummyLinesDetailsReportDTO objDto = new DummyLinesDetailsReportDTO();
		String userId,interfaceId;
		ArrayList<DummyLinesDetailsReportDTO> serviceNameList = new ArrayList<DummyLinesDetailsReportDTO>();
		ReportsDao objrtpDao=new ReportsDao();
		try {
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				
				interfaceId=(String) request.getParameter("interfaceId");
				userId=objUserDto.getUserId();
				//System.out.println(userId+" "+interfaceId);
				Utility.setFileDumpParams(request,objForm);
				
				ActionMessages messages = new ActionMessages();
				
				objDto.setLogical_si_no(String.valueOf(objForm.getLogicalsi_no()));
				objDto.setCrmaccountno(objForm.getCrmAccountNo());
				objDto.setLineitemid(objForm.getServiceProductId());
				objDto.setServicetypeid(objForm.getServiceTypeName());
				
				serviceNameList=objrtpDao.fetchServiceTypeName();
				objForm.setServiceNameList(serviceNameList);
				
				PagingSorting pagingSorting = objForm.getPagingSorting();
				String inExcel = objForm.getToExcel();
				
				if ("true".equals(inExcel)) 
				{
					pagingSorting.setPagingSorting(false, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "displayReportInExcel";
				}
				else 
				{
					pagingSorting.setPagingSorting(true, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "displayDummyLineDetailsReport";
				}
				pagingSorting.setDefaultifNotPresent("ORDERNO",
						PagingSorting.increment, 1);
				objDto.setPagingSorting(pagingSorting);
				String fromPageSubmit=request.getParameter("fromPageSubmit");				
				 request.setAttribute("fromPageSubmit", fromPageSubmit);
				if("1".equals(fromPageSubmit))
				{	//No of records limit : start
					if ("true".equals(inExcel)) {
						pagingSorting.setPagingSorting(true, true);
						pagingSorting.setStartRecordId(1);
						pagingSorting.setEndRecordIdForNoOfRecordsCount();
						listSearchDetails = objService.viewDummyLineDetailsReport(objDto);//***************CHANGE HERE*****************
						if(listSearchDetails!=null && objDto.getPagingSorting().getRecordCount()>ApplicationFlags.ReportsExcelMaxSize)
						{	
							forwardMapping = "displayDummyLineDetailsReport";//***************CHANGE HERE*****************
							objForm.setReportProcessForLimit("1");
							objForm.setReportCurrentCount(String.valueOf(objDto.getPagingSorting().getRecordCount()));
							objDto.getPagingSorting().setRecordCount(0);
							objDto.getPagingSorting().setPageNumber(0);
							listSearchDetails=new ArrayList<DummyLinesDetailsReportDTO>();
						}else if (listSearchDetails!=null && objDto.getPagingSorting().getRecordCount()<=ApplicationFlags.ReportsExcelMaxSize){
							pagingSorting.setPagingSorting(false, true);
							listSearchDetails = objService.viewDummyLineDetailsReport(objDto);
						}else{
							listSearchDetails=null;
						}
					}else{
							listSearchDetails = objService.viewDummyLineDetailsReport(objDto);
						}						
				}			
				if ("true".equals(inExcel)) 
				{
					objForm.setBcpExcelList(listSearchDetails);
					request.setAttribute("dummyLinesDetailsReportExcelList", listSearchDetails);
				}
				else 
				{
					request.setAttribute("dummyLinesDetailsReportList", listSearchDetails);
					objForm.setCustomerList(listSearchDetails);
				}
		} 
		catch (Exception ex) {
			return mapping.findForward(Messages
					.getMessageValue("errorGlobalForward"));
		}
		return mapping.findForward(forwardMapping);
	}
	//[0011] start
	public ActionForward viewDocumentMatrixReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String forwardMapping = "";
		
		ReportsModel objService = new ReportsModel();
		ReportsBean objForm = (ReportsBean) form;
		DocumentMatrixReportDTO objDto=new DocumentMatrixReportDTO();
		ArrayList<DocumentMatrixReportDTO> documentList=new ArrayList<DocumentMatrixReportDTO>();
		String userId,interfaceId;
		try {
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			interfaceId=(String) request.getParameter("interfaceId");
			userId=objUserDto.getUserId();
			
			Utility.setFileDumpParams(request,objForm);	
			ArrayList errors = new ArrayList();
			
		    PagingSorting pagingSorting = objForm.getPagingSorting();
		    
		    objDto.setToCOPCDate(objForm.getToDate());
			objDto.setFromCOPCDate(objForm.getFromDate());
			objDto.setCustSegment(objForm.getCustSegment());
			
			
				String inExcel = objForm.getToExcel();
				if ("true".equals(inExcel)) {
					pagingSorting.setPagingSorting(false, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "displayDocMatrixReportInExcel";
				} else {
					pagingSorting.setPagingSorting(true, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "DocumentMatrixReport";
				}
				
				pagingSorting.setDefaultifNotPresent("ORDERNO",
						PagingSorting.increment, 1);
				objDto.setPagingSorting(pagingSorting);
				String fromPageSubmit=request.getParameter("fromPageSubmit");				
				request.setAttribute("fromPageSubmit", fromPageSubmit);
				if("1".equals(fromPageSubmit)){
					if ("true".equals(inExcel)) {
						pagingSorting.setPagingSorting(true, true);
						pagingSorting.setStartRecordId(1);
						pagingSorting.setEndRecordIdForNoOfRecordsCount();
						documentList = objService.viewDocumentMatrixReport(objDto);		
							if(documentList!=null && objDto.getPagingSorting().getRecordCount()>ApplicationFlags.ReportsExcelMaxSize)
							{	
								forwardMapping = "DocumentMatrixReport";
								objForm.setReportProcessForLimit("1");
								objForm.setReportCurrentCount(String.valueOf(objDto.getPagingSorting().getRecordCount()));
								objDto.getPagingSorting().setRecordCount(0);
								objDto.getPagingSorting().setPageNumber(0);
								documentList=new ArrayList<DocumentMatrixReportDTO>();
								
							}else if (documentList!=null && objDto.getPagingSorting().getRecordCount()<=ApplicationFlags.ReportsExcelMaxSize){
									pagingSorting.setPagingSorting(false, true);
									documentList = objService.viewDocumentMatrixReport(objDto);
									
								}else{
									documentList=null;
								}
						
					}else{
						
						documentList=objService.viewDocumentMatrixReport(objDto);
					}
				}
					
	
				if ("true".equals(inExcel)) {
					request.setAttribute("CustSegment", objDto.getCustSegment());
					request.setAttribute("DocumentMatrixReportExcel", documentList);
				} else {
					request.setAttribute("CustSegment", objDto.getCustSegment());
					request.setAttribute("DocumentMatrixReport", documentList);
				
					
				}
				
		} catch (Exception ex) {
			return mapping.findForward(Messages
					.getMessageValue("errorGlobalForward"));
		}

		return mapping.findForward(forwardMapping);
	}	
	//[0011] end
	
	
	//[0011] start
		public ActionForward viewAccessMatrixReport(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {

			String forwardMapping = "";
			
			ReportsModel objService = new ReportsModel();
			ReportsBean objForm = (ReportsBean) form;
			UserAccessMatrixDto objDto=new UserAccessMatrixDto();
			ArrayList<UserAccessMatrixDto> documentList=new ArrayList<UserAccessMatrixDto>();
			String userId,interfaceId;
			try {
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				interfaceId=(String) request.getParameter("interfaceId");
				userId=objUserDto.getUserId();
				
				Utility.setFileDumpParams(request,objForm);	
				ArrayList errors = new ArrayList();
				
			    PagingSorting pagingSorting = objForm.getPagingSorting();
			    
			    objDto.setToCOPCDate(objForm.getToDate());
				objDto.setFromCOPCDate(objForm.getFromDate());
				
				String inExcel = objForm.getToExcel();
					if ("true".equals(inExcel)) {
						pagingSorting.setPagingSorting(false, true);
						request.setAttribute("userId",objUserDto.getUserId());
						forwardMapping = "DisplayAccessMatrixReportInExcel";
					} else {
						pagingSorting.setPagingSorting(true, true);
						request.setAttribute("userId",objUserDto.getUserId());
						forwardMapping = "AccessMatrixReport";
					}
					
					pagingSorting.setDefaultifNotPresent("DATEOFMODIFICATION",
							PagingSorting.increment, 1);
					objDto.setPagingSorting(pagingSorting);	
					String fromPageSubmit=request.getParameter("fromPageSubmit");				
					request.setAttribute("fromPageSubmit", fromPageSubmit);
					if("1".equals(fromPageSubmit)){
						if ("true".equals(inExcel)) {
							pagingSorting.setPagingSorting(true, true);
							pagingSorting.setStartRecordId(1);
							pagingSorting.setEndRecordIdForNoOfRecordsCount();
							documentList = objService.viewAccessMatrixReport(objDto);		
								if(documentList!=null && objDto.getPagingSorting().getRecordCount()>ApplicationFlags.ReportsExcelMaxSize)
								{	
									forwardMapping = "AccessMatrixReport";
									objForm.setReportProcessForLimit("1");
									objForm.setReportCurrentCount(String.valueOf(objDto.getPagingSorting().getRecordCount()));
									objDto.getPagingSorting().setRecordCount(0);
									objDto.getPagingSorting().setPageNumber(0);
									documentList=new ArrayList<UserAccessMatrixDto>();
									
								}else if (documentList!=null && objDto.getPagingSorting().getRecordCount()<=ApplicationFlags.ReportsExcelMaxSize){
										pagingSorting.setPagingSorting(false, true);
										documentList = objService.viewAccessMatrixReport(objDto);
										
									}else{
										documentList=null;
									}
							
						}else{
							
							documentList=objService.viewAccessMatrixReport(objDto);
						}
					}
						
		
					if ("true".equals(inExcel)) {
						request.setAttribute("AccessMatrixReportExcel", documentList);
					} else {
						request.setAttribute("AccessMatrixReport", documentList);
						
					}
					
			} catch (Exception ex) {
				return mapping.findForward(Messages
						.getMessageValue("errorGlobalForward"));
			}

			return mapping.findForward(forwardMapping);
		}	
		//[0011] end
		//[0013] Start
		
		public ActionForward reportDraftOrder(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
						throws Exception {
				String forwardMapping = "";
				ArrayList orderList = new ArrayList();
				ReportsModel objService = new ReportsModel();
				ReportsBean objForm = (ReportsBean) form;
				ArchivalReportDto reportsDto = new ArchivalReportDto();
				String userId,interfaceId;
			try {
					HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
					HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
					UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
					interfaceId=(String) request.getParameter("interfaceId");
					userId=objUserDto.getUserId();
					//[0014]
					Utility.setFileDumpParams(request,objForm);	
					ArrayList errors = new ArrayList();
					//[0014]
					PagingSorting pagingSorting = objForm.getPagingSorting();

					reportsDto.setTodate(objForm.getTODATE());
					reportsDto.setFromdate(objForm.getFROMDATE());
					reportsDto.setAccount_number(objForm.getACCOUNT_NUMBER());
					reportsDto.setOrder_no(objForm.getORDER_NO());
					reportsDto.setLogical_si_no(objForm.getLOGICALSINO());
					reportsDto.setM6orderno(objForm.getM6orderno());
					reportsDto.setLine_it_no(objForm.getLINE_IT_NO());
					reportsDto.setCkt_id(objForm.getCkt_id());
					
					String inExcel = objForm.getToExcel();
					if ("true".equals(inExcel)) 
					{
						pagingSorting.setPagingSorting(false, true);
						request.setAttribute("userId",objUserDto.getUserId());
						request.setAttribute(interfaceId, objUserDto.getInterfaceId());
						
						forwardMapping = "reportDraftOrderInExcelExt";
					}
					else {
						pagingSorting.setPagingSorting(true, true);
						request.setAttribute("userId",objUserDto.getUserId());
						forwardMapping = "reportDraftOrder";
						}
					pagingSorting.setDefaultifNotPresent("ORDER_NO",PagingSorting.increment, 1);
					reportsDto.setPagingSorting(pagingSorting);
					String fromPageSubmit=request.getParameter("fromPageSubmit");				
					request.setAttribute("fromPageSubmit", fromPageSubmit);
					if("1".equals(fromPageSubmit))
					{
						if ("true".equals(inExcel)) {
							pagingSorting.setPagingSorting(true, true);
							pagingSorting.setStartRecordId(1);
							pagingSorting.setEndRecordIdForNoOfRecordsCount();
							orderList = objService.reportDraftOrder(reportsDto);
							if(orderList!=null && reportsDto.getPagingSorting().getRecordCount()>ApplicationFlags.ARCHIVAL_REPORT_LIMIT)
							{
								System.out.println("ApplicationFlags.ARCHIVAL_REPORT_LIMIT draft>>>"+ApplicationFlags.ARCHIVAL_REPORT_LIMIT);
								forwardMapping = "reportDraftOrder";
								objForm.setReportProcessForLimit("1");
								objForm.setReportCurrentCount(String.valueOf(reportsDto.getPagingSorting().getRecordCount()));
								reportsDto.getPagingSorting().setRecordCount(0);
								reportsDto.getPagingSorting().setPageNumber(0);
								orderList=new ArrayList<ArchivalReportDto>();
							}else if (orderList!=null && reportsDto.getPagingSorting().getRecordCount()<=ApplicationFlags.ARCHIVAL_REPORT_LIMIT){
								System.out.println("ApplicationFlags.ARCHIVAL_REPORT_LIMIT draft>>>"+ApplicationFlags.ARCHIVAL_REPORT_LIMIT);
								pagingSorting.setPagingSorting(false, true);
								orderList = objService.reportDraftOrder(reportsDto);
							}else{
								orderList=null;
							}

						}
						else {
							orderList = objService.reportDraftOrder(reportsDto);
						}	
					if ("true".equals(inExcel)) 
					{
						objForm.setBcpExcelList(orderList);
						request.setAttribute("reportDraftOrderExcel",orderList);
					}
					else 
					{
						request.setAttribute("reportDraftOrder",orderList);
						objForm.setCustomerList(orderList);
					}
					}
			}
					catch (Exception ex) {
						return mapping.findForward(Messages
						.getMessageValue("errorGlobalForward"));
					}
					return mapping.findForward(forwardMapping);
		
		}
		
		public ActionForward pdReportOrder(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
						throws Exception {
				String forwardMapping = "";
				ArrayList orderList = new ArrayList();
				ReportsModel objService = new ReportsModel();
				ReportsBean objForm = (ReportsBean) form;
				ArchivalReportDto reportsDto = new ArchivalReportDto();
				String userId,interfaceId;
			try {
					HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
					HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
					UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
					interfaceId=(String) request.getParameter("interfaceId");
					userId=objUserDto.getUserId();
					//[0014]
					Utility.setFileDumpParams(request,objForm);	
					ArrayList errors = new ArrayList();
					//[0014]
					PagingSorting pagingSorting = objForm.getPagingSorting();
					
					reportsDto.setTodate(objForm.getTODATE());
					reportsDto.setFromdate(objForm.getFROMDATE());
					reportsDto.setAccount_id(objForm.getACCOUNT_ID());
					reportsDto.setOrder_no(objForm.getORDER_NO());
					reportsDto.setLogical_si_no(objForm.getLOGICALSINO());
					reportsDto.setM6orderno(objForm.getM6orderno());
					reportsDto.setOrder_line_no(objForm.getORDER_LINE_ID());//
					reportsDto.setCircuit_id(objForm.getCIRCUIT_ID());
					
					String inExcel = objForm.getToExcel();
					if ("true".equals(inExcel)) 
					{
						pagingSorting.setPagingSorting(false, true);
						request.setAttribute("userId",objUserDto.getUserId());
						forwardMapping = "pdReportOrderInExcelExt";
					}
					else {
						pagingSorting.setPagingSorting(true, true);
						request.setAttribute("userId",objUserDto.getUserId());
						forwardMapping = "pdReportOrder";
						}
					pagingSorting.setDefaultifNotPresent("LOGICAL_SI_NO",PagingSorting.increment, 1);
					reportsDto.setPagingSorting(pagingSorting);
					String fromPageSubmit=request.getParameter("fromPageSubmit");				
					request.setAttribute("fromPageSubmit", fromPageSubmit);
					if("1".equals(fromPageSubmit))
					{
						if ("true".equals(inExcel)) {
							pagingSorting.setPagingSorting(true, true);
							pagingSorting.setStartRecordId(1);
							pagingSorting.setEndRecordIdForNoOfRecordsCount();
							orderList = objService.pdReportOrder(reportsDto);
							if(orderList!=null && reportsDto.getPagingSorting().getRecordCount()>ApplicationFlags.ARCHIVAL_REPORT_LIMIT)
							{	
								System.out.println("ApplicationFlags.ARCHIVAL_REPORT_LIMIT pd>>>"+ApplicationFlags.ARCHIVAL_REPORT_LIMIT);
								forwardMapping = "pdReportOrder";
								objForm.setReportProcessForLimit("1");
								objForm.setReportCurrentCount(String.valueOf(reportsDto.getPagingSorting().getRecordCount()));
								reportsDto.getPagingSorting().setRecordCount(0);
								reportsDto.getPagingSorting().setPageNumber(0);
								orderList=new ArrayList<ArchivalReportDto>();
							}else if (orderList!=null && reportsDto.getPagingSorting().getRecordCount()<=ApplicationFlags.ARCHIVAL_REPORT_LIMIT){
								System.out.println("ApplicationFlags.ARCHIVAL_REPORT_LIMIT pd>>>"+ApplicationFlags.ARCHIVAL_REPORT_LIMIT);
								pagingSorting.setPagingSorting(false, true);
								orderList = objService.pdReportOrder(reportsDto);
							}else{
								orderList=null;
							}
							
						}
						else {
							orderList = objService.pdReportOrder(reportsDto);
						}	
					if ("true".equals(inExcel)) 
					{
						objForm.setBcpExcelList(orderList);
						request.setAttribute("pdReportOrderExcel",orderList);
					}
					else 
					{
						request.setAttribute("pdReportOrder",orderList);
						objForm.setCustomerList(orderList);
					}
					}
			}
					catch (Exception ex) {
						return mapping.findForward(Messages
						.getMessageValue("errorGlobalForward"));
					}
					return mapping.findForward(forwardMapping);
		
		}
		
		public ActionForward cancelledReportOrder(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
						throws Exception {
				String forwardMapping = "";
				ArrayList orderList = new ArrayList();
				ReportsModel objService = new ReportsModel();
				ReportsBean objForm = (ReportsBean) form;
				ArchivalReportDto reportsDto = new ArchivalReportDto();
				String userId,interfaceId;
				//int excelSize = Integer.parseInt(Utility.getAppConfigValue("ARCHIVAL_REPORT_LIMIT"));
				//System.out.println("excelSize-cancelledReportOrder>>>>"+excelSize);
			try {
					HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
					HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
					UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
					interfaceId=(String) request.getParameter("interfaceId");
					userId=objUserDto.getUserId();
					//[0014]
					Utility.setFileDumpParams(request,objForm);	
					ArrayList errors = new ArrayList();
					//[0014]
					PagingSorting pagingSorting = objForm.getPagingSorting();
					
					reportsDto.setTodate(objForm.getTODATE());
					reportsDto.setFromdate(objForm.getFROMDATE());
					reportsDto.setAccount_id(objForm.getACCOUNT_ID());
					reportsDto.setOrder_no(objForm.getORDER_NO());
					reportsDto.setLogical_si_no(objForm.getLOGICALSINO());
					reportsDto.setM6orderno(objForm.getM6orderno());
					reportsDto.setLine_it_no(objForm.getLINE_IT_NO());
					reportsDto.setCkt_id(objForm.getCkt_id());
					
					String inExcel = objForm.getToExcel();
					if ("true".equals(inExcel)) 
					{
						pagingSorting.setPagingSorting(false, true);
						request.setAttribute("userId",objUserDto.getUserId());
						forwardMapping = "cancelledReportOrderInExcelExt";
					}
					else {
						pagingSorting.setPagingSorting(true, true);
						request.setAttribute("userId",objUserDto.getUserId());
						forwardMapping = "cancelledReportOrder";
						}
					pagingSorting.setDefaultifNotPresent("ORDER_NO",PagingSorting.increment, 1);
					reportsDto.setPagingSorting(pagingSorting);
					String fromPageSubmit=request.getParameter("fromPageSubmit");				
					request.setAttribute("fromPageSubmit", fromPageSubmit);
					if("1".equals(fromPageSubmit))
					{
						if ("true".equals(inExcel)) {
							pagingSorting.setPagingSorting(true, true);
							pagingSorting.setStartRecordId(1);
							pagingSorting.setEndRecordIdForNoOfRecordsCount();
							orderList = objService.cancelledReportOrder(reportsDto);
							if(orderList!=null && reportsDto.getPagingSorting().getRecordCount()>ApplicationFlags.ARCHIVAL_REPORT_LIMIT)
							{
								System.out.println("ApplicationFlags.ARCHIVAL_REPORT_LIMIT Cancelled>>>"+ApplicationFlags.ARCHIVAL_REPORT_LIMIT);
								forwardMapping = "cancelledReportOrder";
								objForm.setReportProcessForLimit("1");
								objForm.setReportCurrentCount(String.valueOf(reportsDto.getPagingSorting().getRecordCount()));
								reportsDto.getPagingSorting().setRecordCount(0);
								reportsDto.getPagingSorting().setPageNumber(0);
								orderList=new ArrayList<ArchivalReportDto>();
							}else if (orderList!=null && reportsDto.getPagingSorting().getRecordCount()<=ApplicationFlags.ARCHIVAL_REPORT_LIMIT){
								System.out.println("ApplicationFlags.ARCHIVAL_REPORT_LIMIT Cancelled>>>"+ApplicationFlags.ARCHIVAL_REPORT_LIMIT);
								pagingSorting.setPagingSorting(false, true);
								orderList = objService.cancelledReportOrder(reportsDto);
							}else{
								orderList=null;
							}

						}
						else {
							orderList = objService.cancelledReportOrder(reportsDto);
						}	
					if ("true".equals(inExcel)) 
					{
						objForm.setBcpExcelList(orderList);
						request.setAttribute("cancelledReportOrderExcel",orderList);
					}
					else 
					{
						request.setAttribute("cancelledReportOrder",orderList);
						objForm.setCustomerList(orderList);
					}
					}
			}
					catch (Exception ex) {
						return mapping.findForward(Messages
						.getMessageValue("errorGlobalForward"));
					}
					return mapping.findForward(forwardMapping);
		
		}
		//priya
		public ActionForward viewParallelUpgradeReport(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
		{
			String forwardMapping = "";
			ArrayList<ParallelUpgradeReportDto> listSearchDetails = new ArrayList<ParallelUpgradeReportDto>();
			ReportsModel objService = new ReportsModel();
			ReportsBean objForm = (ReportsBean) form;
			ParallelUpgradeReportDto objDto = new ParallelUpgradeReportDto();
			String inExcel = objForm.getToExcel();
			String userId,interfaceId;
			try {
					
					HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
					HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
					UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
					interfaceId=(String) request.getParameter("interfaceId");
					userId=objUserDto.getUserId();
					System.out.println(userId+" "+interfaceId);
				Utility.setFileDumpParams(request,objForm);
					ActionMessages messages = new ActionMessages();
					objDto.setChangeTypeId(objForm.getChangeTypeId());
					objDto.setToDate(objForm.getToDate());
					objDto.setFromDate(objForm.getFromDate());
					objDto.setFromServiceNo(objForm.getFromServiceNo());
					objDto.setToServiceNo(objForm.getToServiceNo());
					objDto.setCustomerSegment(objForm.getCustomerSegment());
					objDto.setExclude_comp_orders(objForm.getExcludeCompOrders());
					PagingSorting pagingSorting = objForm.getPagingSorting();
					if ("true".equals(inExcel)) 
					{
						pagingSorting.setPagingSorting(false, true);
						request.setAttribute("userId",objUserDto.getUserId());
						forwardMapping = null;
					}
					else 
					{
						pagingSorting.setPagingSorting(true, true);
						request.setAttribute("userId",objUserDto.getUserId());
						request.setAttribute("custSeg",objForm.getCustomerSegment());
						forwardMapping = "displayParallelUpgradeReport";
					}
						
					pagingSorting.setDefaultifNotPresent("ORDERNO",
							PagingSorting.increment, 1);
					objDto.setPagingSorting(pagingSorting);
					String fromPageSubmit=request.getParameter("fromPageSubmit");				
					 request.setAttribute("fromPageSubmit", fromPageSubmit);
					 
					 List<NewOrderDto> changeTypes = new NewOrderModel().getChangeTypeForParallelUpgradeReport();
					 
					 
					if("1".equals(fromPageSubmit))
					{
					listSearchDetails = objService.viewParallelUpgradeReport(objDto,inExcel,response);
					}	

					if(!"true".equals(inExcel))
					{
						request.setAttribute("changeTypes", changeTypes);
						request.setAttribute("ParallelUpgradeReportList", listSearchDetails);
						objForm.setCustomerList(listSearchDetails);
					}
			} 
			catch (Exception ex) {
				return mapping.findForward(Messages
						.getMessageValue("errorGlobalForward"));
			}
			
				return mapping.findForward(forwardMapping);
			
		}
		
}
