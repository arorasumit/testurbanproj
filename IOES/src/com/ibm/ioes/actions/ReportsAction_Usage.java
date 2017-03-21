package com.ibm.ioes.actions;

import java.util.ArrayList;
import java.util.HashMap;

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

import com.ibm.ioes.beans.ReportsBean;
import com.ibm.ioes.beans.UserInfoDto;
import com.ibm.ioes.beans.ValidationBean;
import com.ibm.ioes.forms.BCPAddressDto;
import com.ibm.ioes.forms.DispatchAddressDto;
import com.ibm.ioes.forms.LocationDetailDto;
import com.ibm.ioes.forms.M6OrderStatusDto;
import com.ibm.ioes.forms.NetworkLocationDto;
import com.ibm.ioes.forms.NewOrderDto;
import com.ibm.ioes.forms.ReportsDto;
import com.ibm.ioes.model.NewOrderModel;
import com.ibm.ioes.model.ReportsModel_Usage;
import com.ibm.ioes.newdesign.dto.ActiveLineItemReportsDTO;
import com.ibm.ioes.newdesign.dto.BillingTriggerDoneButFailedInFXDTO;
import com.ibm.ioes.newdesign.dto.BillingWorkQueueReportDTO;
import com.ibm.ioes.newdesign.dto.CancelledFailedLineReportDTO;
import com.ibm.ioes.newdesign.dto.CustomerBaseReportsDTO;
import com.ibm.ioes.newdesign.dto.DisconnectChangeOrdeReportDTO;
import com.ibm.ioes.newdesign.dto.DisconnectLineReportDTO;
import com.ibm.ioes.newdesign.dto.LogicalSIDataReportDTO;
import com.ibm.ioes.newdesign.dto.MigratedApprovedNewOrderDetailReportDTO;
import com.ibm.ioes.newdesign.dto.NonAPP_APPChangeOrderDetailsDTO;
import com.ibm.ioes.newdesign.dto.OBValueReportDTO;
import com.ibm.ioes.newdesign.dto.OrderReportNewDetailCwnDTO;
import com.ibm.ioes.newdesign.dto.PendingOrderAndBillingReportDTO;
import com.ibm.ioes.newdesign.dto.RateRenewalReportDTO;
import com.ibm.ioes.newdesign.dto.RestPendingLineReportDTO;
import com.ibm.ioes.newdesign.dto.StartChargeNotPushedInFXDTO;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.AppUtility;
import com.ibm.ioes.utilities.ApplicationFlags;
import com.ibm.ioes.utilities.Messages;
import com.ibm.ioes.utilities.PagingSorting;
import com.ibm.ioes.utilities.Utility;


public class ReportsAction_Usage extends DispatchAction {
	private static final Logger logger;
	static {
		logger = Logger.getLogger(ReportsAction_Usage.class);
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

		try {
			PagingSorting pagingSorting = formBean.getPagingSorting();
			pagingSorting.setPagingSorting(true, true);
			pagingSorting.setDefaultifNotPresent("accountId",
					PagingSorting.increment, 1);
			objDto.setPagingSorting(pagingSorting);

			request.setAttribute("customerList", bcpList);
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

		try {
			PagingSorting pagingSorting = formBean.getPagingSorting();
			pagingSorting.setPagingSorting(true, true);
			pagingSorting.setDefaultifNotPresent("orderNO",
					PagingSorting.increment, 1);
			objDto.setPagingSorting(pagingSorting);

			request.setAttribute("orderList", orderList);
			formBean.setOrderList(orderList);
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
		ReportsModel_Usage objReportModel = new ReportsModel_Usage();
		ArrayList<M6OrderStatusDto> listReponseHistory = new ArrayList<M6OrderStatusDto>();

		ReportsBean objForm = (ReportsBean) form;
		long m6OrderNo = 0;
		//long orderNo = 0;

		ActionForward forward = new ActionForward();
		ActionMessages messages = new ActionMessages();
	
		try {	
			m6OrderNo = Long.parseLong(request.getParameter("m6OrderNo"));
			//orderNo = Long.parseLong(request.getParameter("orderNo"));
			listReponseHistory = objReportModel.viewM6ResponseHistory(objDto,
					m6OrderNo);
			objForm.setM6OrderNoStr(String.valueOf(m6OrderNo));
			request.setAttribute("listReponseHistory", listReponseHistory);
			objForm.setOrderList(listReponseHistory);
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
		ReportsModel_Usage objService = new ReportsModel_Usage();
		ReportsBean objForm = (ReportsBean) form;
		BCPAddressDto objDto = new BCPAddressDto();
		try {
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
					forwardMapping = "displayReportInExcel";
				} else {
					pagingSorting.setPagingSorting(true, true);
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
		ReportsModel_Usage objService = new ReportsModel_Usage();
		ReportsBean objForm = (ReportsBean) form;
		M6OrderStatusDto objDto = new M6OrderStatusDto();
		try {
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
					forwardMapping = "displayReportInExcel";
				} else {
					pagingSorting.setPagingSorting(true, true);
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

		try {
			PagingSorting pagingSorting = formBean.getPagingSorting();
			pagingSorting.setPagingSorting(true, true);
			pagingSorting.setDefaultifNotPresent("LOCATION_CODE",
					PagingSorting.increment, 1);
			objDto.setPagingSorting(pagingSorting);

			request.setAttribute("customerList", locsList);
			formBean.setCustomerList(locsList);
			formBean.setList(listAccount);
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
		ReportsModel_Usage objService = new ReportsModel_Usage();
		ReportsBean objForm = (ReportsBean) form;
		NetworkLocationDto objDto = new NetworkLocationDto();
		try {
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
					forwardMapping = "displayReportInExcel";
				} else {
					pagingSorting.setPagingSorting(true, true);
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

		try {
			PagingSorting pagingSorting = formBean.getPagingSorting();
			pagingSorting.setPagingSorting(true, true);
			pagingSorting.setDefaultifNotPresent("accountId",
					PagingSorting.increment, 1);
			objDto.setPagingSorting(pagingSorting);

			request.setAttribute("customerList", clList);
			formBean.setCustomerList(clList);
			formBean.setList(listAccount);
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
		ReportsModel_Usage objService = new ReportsModel_Usage();
		ReportsBean objForm = (ReportsBean) form;
		LocationDetailDto objDto = new LocationDetailDto();
		try {
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
					forwardMapping = "displayReportInExcel";
				} else {
					pagingSorting.setPagingSorting(true, true);
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
		try {

			PagingSorting pagingSorting = formBean.getPagingSorting();
			pagingSorting.setPagingSorting(true, true);
			pagingSorting.setDefaultifNotPresent("accountId",
					PagingSorting.increment, 1);
			objDto.setPagingSorting(pagingSorting);

			request.setAttribute("customerList", contactList);
			formBean.setCustomerList(contactList);
			formBean.setList(listAccount);
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
		ReportsModel_Usage objService = new ReportsModel_Usage();
		ReportsBean objForm = (ReportsBean) form;
		NewOrderDto objDto = new NewOrderDto();
		try {
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
					forwardMapping = "displayReportInExcel";
				} else {
					pagingSorting.setPagingSorting(true, true);
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

		try {
			PagingSorting pagingSorting = formBean.getPagingSorting();
			pagingSorting.setPagingSorting(true, true);
			pagingSorting.setDefaultifNotPresent("accountId",
					PagingSorting.increment, 1);
			objDto.setPagingSorting(pagingSorting);

			request.setAttribute("customerList", objUserList);
			formBean.setCustomerList(objUserList);
			formBean.setList(listAccount);

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
		ArrayList<ReportsDto> listSearchDetails = new ArrayList<ReportsDto>();
		ReportsModel_Usage objService = new ReportsModel_Usage();
		ReportsBean objForm = (ReportsBean) form;
		ReportsDto objDto = new ReportsDto();
		int isUsage =0;
		try {
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
					forwardMapping = "displayReportInExcelExt";
				}
				else 
				{
					pagingSorting.setPagingSorting(true, true);
					forwardMapping = "displayTelemediaOrderReport";
				}
				pagingSorting.setDefaultifNotPresent("ORDERNO",
						PagingSorting.increment, 1);
				objDto.setPagingSorting(pagingSorting);
				String fromPageSubmit=request.getParameter("fromPageSubmit");				
				request.setAttribute("fromPageSubmit", fromPageSubmit);
				request.setAttribute("isUsage", isUsage);
				if("1".equals(fromPageSubmit))
				{
				listSearchDetails = objService.getTelemediaOrderList(objDto);
				}
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
		} 
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
		ReportsModel_Usage objService = new ReportsModel_Usage();
		ReportsBean objForm = (ReportsBean) form;
		DispatchAddressDto objDto = new DispatchAddressDto();

		try {
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
					forwardMapping = "displayReportInExcel";
				} else {
					pagingSorting.setPagingSorting(true, true);
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

		try {
			PagingSorting pagingSorting = formBean.getPagingSorting();
			pagingSorting.setPagingSorting(true, true);
			pagingSorting.setDefaultifNotPresent("accountId",
					PagingSorting.increment, 1);
			objDto.setPagingSorting(pagingSorting);

			request.setAttribute("customerList", orderList);
			formBean.setCustomerList(orderList);
			formBean.setList(listAccount);
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
		ReportsModel_Usage objService = new ReportsModel_Usage();
		ReportsBean objForm = (ReportsBean) form;
		NewOrderDto objDto = new NewOrderDto();
		try {
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
					forwardMapping = "displayReportInExcel";
				} else {
					pagingSorting.setPagingSorting(true, true);
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
		try
		{
			PagingSorting pagingSorting = formBean.getPagingSorting();
			pagingSorting.setPagingSorting(true, true);
			pagingSorting.setDefaultifNotPresent("masterName",
					PagingSorting.increment, 1);
			//objDto.setPagingSorting(pagingSorting);
			request.setAttribute("masterHistoryList", historyList);
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
		ReportsModel_Usage objModel = new ReportsModel_Usage();
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
		ArrayList<ReportsDto> orderList = new ArrayList<ReportsDto>();
		ReportsModel_Usage objService = new ReportsModel_Usage();
		ReportsBean objForm = (ReportsBean) form;
		ReportsDto objDto = new ReportsDto();
		try {
				objDto.setOrderType(objForm.getOrderType());
				objDto.setToDate(objForm.getToDate());
				objDto.setFromDate(objForm.getFromDate());
				objDto.setOsp(objForm.getOsp());
				
				PagingSorting pagingSorting = objForm.getPagingSorting();

				String inExcel = objForm.getToExcel();
				if ("true".equals(inExcel)) 
				{
					pagingSorting.setPagingSorting(false, true);
					forwardMapping = "displayReportInExcelExt";
				}
				else 
				{
					pagingSorting.setPagingSorting(true, true);
					forwardMapping = "displayOrderStageReport";
				}

				pagingSorting.setDefaultifNotPresent("ORDERNO",
						PagingSorting.increment, 1);
				objDto.setPagingSorting(pagingSorting);

				orderList = objService.viewOrderStageList(objDto);

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
		ArrayList<ReportsDto> listSearchDetails = new ArrayList<ReportsDto>();
		ReportsModel_Usage objService = new ReportsModel_Usage();
		ReportsBean objForm = (ReportsBean) form;
		ReportsDto objDto = new ReportsDto();
		try {
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
					forwardMapping = "displayReportInExcelExt";
				}
				else 
				{
					pagingSorting.setPagingSorting(true, true);
					forwardMapping = "displayPerformanceDetailReport";
				}

				pagingSorting.setDefaultifNotPresent("ORDERNO",
						PagingSorting.increment, 1);
				objDto.setPagingSorting(pagingSorting);

				listSearchDetails = objService.viewPerformanceDetailList(objDto);

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
		ArrayList<ReportsDto> listSearchDetails = new ArrayList<ReportsDto>();
		ReportsModel_Usage objService = new ReportsModel_Usage();
		ReportsBean objForm = (ReportsBean) form;
		ReportsDto objDto = new ReportsDto();
		try {
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
					forwardMapping = "displayReportInExcelExt";
					
				}
				else 
				{
					pagingSorting.setPagingSorting(true, true);
					forwardMapping = "displayPerformanceReport";
				}

				pagingSorting.setDefaultifNotPresent("ORDERNO",
						PagingSorting.increment, 1);
				objDto.setPagingSorting(pagingSorting);

				listSearchDetails = objService.viewPerformanceList(objDto);

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
		ArrayList<ReportsDto> listSearchDetails = new ArrayList<ReportsDto>();
		ReportsModel_Usage objService = new ReportsModel_Usage();
		ReportsBean objForm = (ReportsBean) form;
		ReportsDto objDto = new ReportsDto();
		try {
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
					forwardMapping = "displayReportInExcelExt";
				}
				else 
				{
					pagingSorting.setPagingSorting(true, true);
					forwardMapping = "displayPerformanceSummaryReport";
				}
				pagingSorting.setDefaultifNotPresent("ORDERNO",
						PagingSorting.increment, 1);
				objDto.setPagingSorting(pagingSorting);
				String fromPageSubmit=request.getParameter("fromPageSubmit");				
				request.setAttribute("fromPageSubmit", fromPageSubmit);
				if("1".equals(fromPageSubmit))
				{	
				listSearchDetails = objService.viewPerformanceSummaryReport(objDto);
				}
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
		ArrayList<NonAPP_APPChangeOrderDetailsDTO> listSearchDetails = new ArrayList<NonAPP_APPChangeOrderDetailsDTO>();
		ReportsModel_Usage objService = new ReportsModel_Usage();
		ReportsBean objForm = (ReportsBean) form;
		NonAPP_APPChangeOrderDetailsDTO objDto = new NonAPP_APPChangeOrderDetailsDTO();
		int isUsage =0;
		String userId,interfaceId;
		try {
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				interfaceId=(String) request.getParameter("interfaceId");
				userId=objUserDto.getUserId();
				System.out.println(userId+" "+interfaceId);
			Utility.setFileDumpParams(request,objForm);//Check objForm 
				if(request.getParameter("usage") != null)
						isUsage=Integer.parseInt(request.getParameter("usage"));
				else 
				{
					isUsage=objForm.getIsUsage();
				}
				ActionMessages messages = new ActionMessages();
				objDto.setOrderType(objForm.getOrderType());
				objDto.setApprovalType(objForm.getApprovalType());
				objDto.setToOrderNo(objForm.getToOrderNo());
				objDto.setFromOrderNo(objForm.getFromOrderNo());
				objDto.setServiceOrderType(objForm.getServiceOrderType());
				objDto.setOrdermonth(objForm.getOrdermonth());
				objDto.setOrderyear(objForm.getOrderyear());
				objDto.setVerticalDetails(objForm.getVerticalDetails());
				objDto.setIsUsage(isUsage);
				PagingSorting pagingSorting = objForm.getPagingSorting();
				String inExcel = objForm.getToExcel();
				if ("true".equals(inExcel)) 
				{
					pagingSorting.setPagingSorting(false, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "exlNonMigAppUnAppChangeOrderReport_Usage";
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
				{//No of records limit : start
					if ("true".equals(inExcel)) {
						pagingSorting.setPagingSorting(true, true);
						pagingSorting.setStartRecordId(1);
						pagingSorting.setEndRecordIdForNoOfRecordsCount();
						listSearchDetails = objService.viewNonMigAppUnappNewOrderDetails(objDto);//***************CHANGE HERE*****************
						if(listSearchDetails!=null && objDto.getPagingSorting().getRecordCount()>ApplicationFlags.ReportsExcelMaxSize)
						{	
							forwardMapping = "displayNonAppAppChangeOrderDetails";//***************CHANGE HERE*****************
							objForm.setReportProcessForLimit("1");
							objForm.setReportCurrentCount(String.valueOf(objDto.getPagingSorting().getRecordCount()));
							objDto.getPagingSorting().setRecordCount(0);
							objDto.getPagingSorting().setPageNumber(0);
							listSearchDetails=new ArrayList<NonAPP_APPChangeOrderDetailsDTO>();
						}else if (listSearchDetails!=null && objDto.getPagingSorting().getRecordCount()<=ApplicationFlags.ReportsExcelMaxSize){
							pagingSorting.setPagingSorting(false, true);
							listSearchDetails = objService.viewNonMigAppUnappNewOrderDetails(objDto);
						}else{
							listSearchDetails=null;
						}
					}else {
					//No of records limit : end
 
				listSearchDetails = objService.viewNonMigAppUnappNewOrderDetails(objDto);//this method is  use for both new and change Order.
					}	}				
				request.setAttribute("isUsage", isUsage);
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
		ArrayList<NonAPP_APPChangeOrderDetailsDTO> listSearchDetails = new ArrayList<NonAPP_APPChangeOrderDetailsDTO>();
		ReportsModel_Usage objService = new ReportsModel_Usage();
		ReportsBean objForm = (ReportsBean) form;
		NonAPP_APPChangeOrderDetailsDTO objDto = new NonAPP_APPChangeOrderDetailsDTO();
		int isUsage =0;
		String userId,interfaceId;
		try {Utility.setFileDumpParams(request,objForm);//Check objForm 
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				interfaceId=(String) request.getParameter("interfaceId");
				userId=objUserDto.getUserId();
				System.out.println(userId+" "+interfaceId);
				if(request.getParameter("usage") != null)
						isUsage=Integer.parseInt(request.getParameter("usage"));
				else 
				{
					isUsage=objForm.getIsUsage();
				}
				ActionMessages messages = new ActionMessages();
				objDto.setOrderType(objForm.getOrderType());
				objDto.setApprovalType(objForm.getApprovalType());
				objDto.setToOrderNo(objForm.getToOrderNo());
				objDto.setFromOrderNo(objForm.getFromOrderNo());
				objDto.setOrdermonth(objForm.getOrdermonth());
				objDto.setOrderyear(objForm.getOrderyear());
				objDto.setServiceName(objForm.getServicename());				
				objDto.setIsUsage(isUsage);
				PagingSorting pagingSorting = objForm.getPagingSorting();
				String inExcel = objForm.getToExcel();
				if ("true".equals(inExcel)) 
				{
					pagingSorting.setPagingSorting(false, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "exlNonMigAppUnAppNewOrderReport_Usage";
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
				{//No of records limit : start
					if ("true".equals(inExcel)) {
						pagingSorting.setPagingSorting(true, true);
						pagingSorting.setStartRecordId(1);
						pagingSorting.setEndRecordIdForNoOfRecordsCount();
						listSearchDetails = objService.viewNonMigAppUnappNewOrderDetails(objDto);//***************CHANGE HERE*****************
						if(listSearchDetails!=null && objDto.getPagingSorting().getRecordCount()>ApplicationFlags.ReportsExcelMaxSize)
						{	
							forwardMapping = "displayNonMigAppUnappNewOrderDetails";//***************CHANGE HERE*****************
							objForm.setReportProcessForLimit("1");
							objForm.setReportCurrentCount(String.valueOf(objDto.getPagingSorting().getRecordCount()));
							objDto.getPagingSorting().setRecordCount(0);
							objDto.getPagingSorting().setPageNumber(0);
							listSearchDetails=new ArrayList<NonAPP_APPChangeOrderDetailsDTO>();
						}else if (listSearchDetails!=null && objDto.getPagingSorting().getRecordCount()<=ApplicationFlags.ReportsExcelMaxSize){
							pagingSorting.setPagingSorting(false, true);
							listSearchDetails = objService.viewNonMigAppUnappNewOrderDetails(objDto);
						}else{
							listSearchDetails=null;
						}
					}else {
					//No of records limit : end
					 
				listSearchDetails = objService.viewNonMigAppUnappNewOrderDetails(objDto);
				}}
				request.setAttribute("isUsage", isUsage);
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
		ReportsModel_Usage objService = new ReportsModel_Usage();
		ReportsBean objForm = (ReportsBean) form;
		StartChargeNotPushedInFXDTO objDto = new StartChargeNotPushedInFXDTO();
		int isUsage =0;
		String userId,interfaceId;
		try {
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			interfaceId=(String) request.getParameter("interfaceId");
			userId=objUserDto.getUserId();
			System.out.println(userId+" "+interfaceId);
			Utility.setFileDumpParams(request,objForm);//Check objForm 
				
			if(request.getParameter("usage") != null)
				isUsage=Integer.parseInt(request.getParameter("usage"));
			else 
			{
				isUsage=objForm.getIsUsage();
			}
				ActionMessages messages = new ActionMessages();
				objDto.setOrderType(objForm.getOrderType());
				objDto.setToDate(objForm.getToDate());
				objDto.setFromDate(objForm.getFromDate());
				objDto.setToOrderNo(objForm.getToOrderNo());
				objDto.setFromOrderNo(objForm.getFromOrderNo());
				objDto.setVerticalDetails(objForm.getVerticalDetails());				
				objDto.setIsUsage(isUsage);
				PagingSorting pagingSorting = objForm.getPagingSorting();

				String inExcel = objForm.getToExcel();
				if ("true".equals(inExcel)) 
				{
					pagingSorting.setPagingSorting(false, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "exlStartChargeNotPushInFXReport_Usage";
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
				request.setAttribute("isUsage", isUsage);
				if("1".equals(fromPageSubmit))
				{//No of records limit : start
					if ("true".equals(inExcel)) {
						pagingSorting.setPagingSorting(true, true);
						pagingSorting.setStartRecordId(1);
						pagingSorting.setEndRecordIdForNoOfRecordsCount();
						listSearchDetails  = objService.viewStartChargeNotPushedInFx(objDto);//***************CHANGE HERE*****************
						if(listSearchDetails !=null && objDto.getPagingSorting().getRecordCount()>ApplicationFlags.ReportsExcelMaxSize)
						{	
							forwardMapping = "displayStartChargeNotPushedInFx";//***************CHANGE HERE*****************
							objForm.setReportProcessForLimit("1");
							objForm.setReportCurrentCount(String.valueOf(objDto.getPagingSorting().getRecordCount()));
							objDto.getPagingSorting().setRecordCount(0);
							objDto.getPagingSorting().setPageNumber(0);
							listSearchDetails =new ArrayList<StartChargeNotPushedInFXDTO>();
						}else if (listSearchDetails !=null && objDto.getPagingSorting().getRecordCount()<=ApplicationFlags.ReportsExcelMaxSize){
							pagingSorting.setPagingSorting(false, true);
							listSearchDetails  = objService.viewStartChargeNotPushedInFx(objDto);
						}else{
							listSearchDetails =null;
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
		ReportsModel_Usage objService = new ReportsModel_Usage();
		ReportsBean objForm = (ReportsBean) form;
		LogicalSIDataReportDTO objDto = new LogicalSIDataReportDTO();
	    int isUsage =0;
	    String userId,interfaceId;
		try {
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			interfaceId=(String) request.getParameter("interfaceId");
			userId=objUserDto.getUserId();
			System.out.println(userId+" "+interfaceId);
			Utility.setFileDumpParams(request,objForm);//Check objForm R
			if(request.getParameter("usage") != null)
					isUsage=Integer.parseInt(request.getParameter("usage"));
			else 
			{
				isUsage=objForm.getIsUsage();
			}
				ActionMessages messages = new ActionMessages();
				objDto.setOrderType(objForm.getOrderType());
				objDto.setToDate(objForm.getToDate());
				objDto.setFromDate(objForm.getFromDate());
				objDto.setToOrderNo(objForm.getToOrderNo());
				objDto.setFromOrderNo(objForm.getFromOrderNo());
				objDto.setLogicalSINumber(objForm.getLogicalsi_no());
				objDto.setIsUsage(isUsage);
				PagingSorting pagingSorting = objForm.getPagingSorting();
				String inExcel = objForm.getToExcel();
				if ("true".equals(inExcel)) 
				{
					pagingSorting.setPagingSorting(false, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "exlLogicalLsiDataReport_Usage";
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
				{if ("true".equals(inExcel)) {
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
				}
				}				
				request.setAttribute("isUsage", isUsage);
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
		ReportsModel_Usage objService = new ReportsModel_Usage();
		ReportsBean objForm = (ReportsBean) form;
		BillingTriggerDoneButFailedInFXDTO objDto = new BillingTriggerDoneButFailedInFXDTO();
		int isUsage =0;
		String userId,interfaceId;
		try {
			
			
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				interfaceId=(String) request.getParameter("interfaceId");
				userId=objUserDto.getUserId();
				System.out.println(userId+" "+interfaceId);
			Utility.setFileDumpParams(request,objForm);//Check objForm 
				if(request.getParameter("usage") != null)
						isUsage=Integer.parseInt(request.getParameter("usage"));
				else 
				{
					isUsage=objForm.getIsUsage();
				}
				ActionMessages messages = new ActionMessages();
				objDto.setOrderType(objForm.getOrderType());
				objDto.setToDate(objForm.getToDate());
				objDto.setFromDate(objForm.getFromDate());
				objDto.setToOrderNo(objForm.getToOrderNo());
				objDto.setFromOrderNo(objForm.getFromOrderNo());
				objDto.setToAccountNo(objForm.getToAccountNo());
				objDto.setFromAccountNo(objForm.getFromAccountNo());
				objDto.setIsUsage(isUsage);
				PagingSorting pagingSorting = objForm.getPagingSorting();

				String inExcel = objForm.getToExcel();
				if ("true".equals(inExcel)) 
				{
					pagingSorting.setPagingSorting(false, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "exlBTDoneButComptFailedInFxReport_Usage";
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
//					No of records limit : start
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
					//No of records limit : end	
					
				listSearchDetails = objService.viewBillingTriggerDoneButFailedInFX(objDto);
					}
				}
				
				request.setAttribute("isUsage", isUsage);
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
		ArrayList<ReportsDto> listSearchDetails = new ArrayList<ReportsDto>();
		ReportsModel_Usage objService = new ReportsModel_Usage();
		ReportsBean objForm = (ReportsBean) form;
		ReportsDto objDto = new ReportsDto();
		try {
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
					forwardMapping = "displayReportInExcel";
				}
				else 
				{
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
				listSearchDetails = objService.viewPendingServicesReport(objDto);
				}
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
		ArrayList<ActiveLineItemReportsDTO> orderList = new ArrayList<ActiveLineItemReportsDTO>();
		ReportsModel_Usage objService = new ReportsModel_Usage();
		ReportsBean objForm = (ReportsBean) form;
		ActiveLineItemReportsDTO objDto = new ActiveLineItemReportsDTO();
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
			objDto.setOrderType(objForm.getOrderType());
			objDto.setToDate(objForm.getToDate());
			objDto.setFromDate(objForm.getFromDate());
			objDto.setToOrderNo(objForm.getToOrderNo());
			objDto.setFromOrderNo(objForm.getFromOrderNo());
			objDto.setIsUsage(isUsage);
			objDto.setToCopcApprovedDate(objForm.getToCopcApprovedDate());
			objDto.setFromCopcApprovedDate(objForm.getFromCopcApprovedDate());
			objDto.setPartyNo(objForm.getPartyNo());
			objDto.setCustomerSegment(objForm.getCustomerSegment());
			objDto.setProductName(objForm.getProductName());
			
			PagingSorting pagingSorting = objForm.getPagingSorting();

				String inExcel = objForm.getToExcel();
				if ("true".equals(inExcel)) {
					request.setAttribute("userId",objUserDto.getUserId());
					pagingSorting.setPagingSorting(false, true);
					forwardMapping = "exlActiveLineReport_Usage";
				} else {
					pagingSorting.setPagingSorting(true, true);
					request.setAttribute("userId",objUserDto.getUserId());
					request.setAttribute("product",objForm.getProductName());
					request.setAttribute("custSeg",objForm.getCustomerSegment());
					request.setAttribute("partyNo",objForm.getPartyNo());
					forwardMapping = "orderActiveLineItemsList";
				}

				pagingSorting.setDefaultifNotPresent("ORDERNO",
						PagingSorting.increment, 1);
				objDto.setPagingSorting(pagingSorting);
				String fromPageSubmit=request.getParameter("fromPageSubmit");				
				request.setAttribute("fromPageSubmit", fromPageSubmit);
				if("1".equals(fromPageSubmit))
				{//start
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
//							end
							orderList=null;
						}
					}else {
						orderList = objService.viewActiveLineItemsList(objDto);
					}
				}
				request.setAttribute("isUsage", isUsage);
				if ("true".equals(inExcel)) {
					objForm.setBcpExcelList(orderList);
					request.setAttribute("orderActiveLineItemsList", orderList);
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
	
	
	public ActionForward viewOrderReportNew(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String forwardMapping = "";
		ArrayList<OrderReportNewDetailCwnDTO> orderList = new ArrayList<OrderReportNewDetailCwnDTO>();
		ReportsModel_Usage objService = new ReportsModel_Usage();
		ReportsBean objForm = (ReportsBean) form;
		OrderReportNewDetailCwnDTO objDto = new OrderReportNewDetailCwnDTO();
		int isUsage =0;
		String userId,interfaceId;
		try {
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			interfaceId=(String) request.getParameter("interfaceId");
			userId=objUserDto.getUserId();
			System.out.println(userId+" "+interfaceId);
			Utility.setFileDumpParams(request,objForm);//Check objForm 
			if(request.getParameter("usage") != null)
				isUsage=Integer.parseInt(request.getParameter("usage"));
			else 
			{
				isUsage=objForm.getIsUsage();
			}
			ArrayList errors = new ArrayList();
			objDto.setOrderType(objForm.getOrderType());
			objDto.setToDate(objForm.getToDate());
			objDto.setFromDate(objForm.getFromDate());
			//added by mohit
			objDto.setToCopcApprovedDate(objForm.getToCopcApprovedDate());
			objDto.setFromCopcApprovedDate(objForm.getFromCopcApprovedDate());
			objDto.setVerticalDetails(objForm.getVerticalDetails());
			objDto.setOsp(objForm.getOsp());
			PagingSorting pagingSorting = objForm.getPagingSorting();

				String inExcel = objForm.getToExcel();
				if ("true".equals(inExcel)) {
					pagingSorting.setPagingSorting(false, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "exlOrderReportNew_Usage";
				} else {
					pagingSorting.setPagingSorting(true, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "orderReportNewListUsage";
				}

				pagingSorting.setDefaultifNotPresent("ORDERNO",
						PagingSorting.increment, 1);
				objDto.setPagingSorting(pagingSorting);
				objDto.setIsUsage(isUsage);
				String fromPageSubmit=request.getParameter("fromPageSubmit");				
				request.setAttribute("fromPageSubmit", fromPageSubmit);
				if("1".equals(fromPageSubmit))
				{
//					No of records limit : start
					if ("true".equals(inExcel)) {
						pagingSorting.setPagingSorting(true, true);
						pagingSorting.setStartRecordId(1);
						pagingSorting.setEndRecordIdForNoOfRecordsCount();
						orderList = objService.viewOrderReportNew(objDto);//***************CHANGE HERE*****************
						if(orderList!=null && objDto.getPagingSorting().getRecordCount()>ApplicationFlags.ReportsExcelMaxSize)
						{	
							forwardMapping = "orderReportNewListUsage";//***************CHANGE HERE*****************
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

				
				request.setAttribute("isUsage", isUsage);
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
		ArrayList<NewOrderDto> orderList = new ArrayList<NewOrderDto>();
		ReportsModel_Usage objService = new ReportsModel_Usage();
		ReportsBean objForm = (ReportsBean) form;
		NewOrderDto objDto = new NewOrderDto();
		try {
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
			PagingSorting pagingSorting = objForm.getPagingSorting();

				String inExcel = objForm.getToExcel();
				if ("true".equals(inExcel)) {
					pagingSorting.setPagingSorting(false, true);
					forwardMapping = "displayReportInExcelExt";
				} else {
					pagingSorting.setPagingSorting(true, true);
					forwardMapping = "orderReportChangeList";
				}

				pagingSorting.setDefaultifNotPresent("ORDERNO",
						PagingSorting.increment, 1);
				objDto.setPagingSorting(pagingSorting);

				orderList = objService.viewOrderReportChange(objDto);

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
		ArrayList<ReportsDto> orderList = new ArrayList<ReportsDto>();
		ReportsModel_Usage objService = new ReportsModel_Usage();
		ReportsBean objForm = (ReportsBean) form;
		ReportsDto objDto = new ReportsDto();
		try {
			objDto.setOrderType(objForm.getOrderType());
			objDto.setToDate(objForm.getToDate());
			objDto.setFromDate(objForm.getFromDate());
			objDto.setFromAccountNo(objForm.getFromAccountNo());
			objDto.setToAccountNo(objForm.getToAccountNo());
			objDto.setFromOrderNo(objForm.getFromOrderNo());
			objDto.setToOrderNo(objForm.getToOrderNo());
			objDto.setDemo(objForm.getDemo());
			
			objDto.setOsp(objForm.getOsp());
			PagingSorting pagingSorting = objForm.getPagingSorting();

				String inExcel = objForm.getToExcel();
				if ("true".equals(inExcel)) {
					pagingSorting.setPagingSorting(false, true);
					forwardMapping = "displayReportInExcelExt";
				} else {
					pagingSorting.setPagingSorting(true, true);
					forwardMapping = "orderReportDetailList";
				}

				pagingSorting.setDefaultifNotPresent("ORDERNO",
						PagingSorting.increment, 1);
				objDto.setPagingSorting(pagingSorting);

				orderList = objService.viewOrderReportDetails(objDto);

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
		ReportsModel_Usage objService = new ReportsModel_Usage();
		ReportsBean objForm = (ReportsBean) form;
		PendingOrderAndBillingReportDTO objDto = new PendingOrderAndBillingReportDTO();
		int isUsage =0;
		String userId,interfaceId;
		try {Utility.setFileDumpParams(request,objForm);//Check objForm 
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				interfaceId=(String) request.getParameter("interfaceId");
				userId=objUserDto.getUserId();
				System.out.println(userId+" "+interfaceId);
				if(request.getParameter("usage") != null)
					isUsage=Integer.parseInt(request.getParameter("usage"));
				else 
				{
					isUsage=objForm.getIsUsage();
				}
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
				objDto.setIsUsage(isUsage);
				objDto.setOsp(objForm.getOsp());
				PagingSorting pagingSorting = objForm.getPagingSorting();
				
			
				
				//request.setAttribute("orderStageList", orderList);
				//objForm.setOrderStageList(orderList);
				
				
			
			
				

				

				String inExcel = objForm.getToExcel();
				if ("true".equals(inExcel)) 
				{
					pagingSorting.setPagingSorting(false, true);
					//forwardMapping = "displayPendingOrderAndBillingReportInExcel";
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "exlPendingOrderAndBillingReport_Usage";
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
				request.setAttribute("isUsage", isUsage);
				if("1".equals(fromPageSubmit))
				{//No of records limit : start
					if ("true".equals(inExcel)) {
						pagingSorting.setPagingSorting(true, true);
						pagingSorting.setStartRecordId(1);
						pagingSorting.setEndRecordIdForNoOfRecordsCount();
						orderList = objService.viewPendingOrderAndBillingList(objDto);//***************CHANGE HERE*****************
						if(orderList!=null && objDto.getPagingSorting().getRecordCount()>ApplicationFlags.ReportsExcelMaxSize)
						{	
							forwardMapping = "displayPendingOrderAndBillingReport";//***************CHANGE HERE*****************
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
			ArrayList<ReportsDto> orderList = new ArrayList<ReportsDto>();
			ReportsModel_Usage objService = new ReportsModel_Usage();
			ReportsBean objForm = (ReportsBean) form;
			ReportsDto objDto = new ReportsDto();
			try {
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
						forwardMapping = "displayReportInExcel";
					}
					else 
					{
						pagingSorting.setPagingSorting(true, true);
						forwardMapping = "pendingOrderBillingItemsList";
					}

					pagingSorting.setDefaultifNotPresent("LOGICAL_SI_NO",
							PagingSorting.increment, 1);
					objDto.setPagingSorting(pagingSorting);
					String fromPageSubmit=request.getParameter("fromPageSubmit");				
					request.setAttribute("fromPageSubmit", fromPageSubmit);
					if("1".equals(fromPageSubmit))
					{
						orderList = objService.pendingOrderBill(objDto);
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
			ArrayList<ReportsDto> orderList = new ArrayList<ReportsDto>();
			ReportsModel_Usage objService = new ReportsModel_Usage();
			ReportsBean objForm = (ReportsBean) form;
			ReportsDto objDto = new ReportsDto();
			try {
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
						forwardMapping = "displayReportInExcel";
					} else {
						pagingSorting.setPagingSorting(true, true);
						forwardMapping = "zeroOrderValueReport";
					}

					pagingSorting.setDefaultifNotPresent("ORDERNO",
							PagingSorting.increment, 1);
					objDto.setPagingSorting(pagingSorting);

					orderList = objService.viewZeroOrderValueReportDetails(objDto);

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
			ArrayList<ReportsDto> orderList = new ArrayList<ReportsDto>();
			ReportsModel_Usage objService = new ReportsModel_Usage();
			ReportsBean objForm = (ReportsBean) form;
			ReportsDto objDto = new ReportsDto();
			try {
				ArrayList errors = new ArrayList();
				objDto.setCanceldate(objForm.getCanceldate());
				PagingSorting pagingSorting = objForm.getPagingSorting();

					String inExcel = objForm.getToExcel();
					if ("true".equals(inExcel)) {
						pagingSorting.setPagingSorting(false, true);
						forwardMapping = "displayReportInExcel";
					} else {
						pagingSorting.setPagingSorting(true, true);
						forwardMapping = "DisplayM6OrderCancelReport";
					}

					pagingSorting.setDefaultifNotPresent("ORDERNO",
							PagingSorting.increment, 1);
					objDto.setPagingSorting(pagingSorting);	
					
					String fromPageSubmit=request.getParameter("fromPageSubmit");				
					request.setAttribute("fromPageSubmit", fromPageSubmit);
					if("1".equals(fromPageSubmit))
					{
						orderList = objService.viewM6OrderCancelReport(objDto);
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
			ReportsModel_Usage objService = new ReportsModel_Usage();
			ReportsBean objForm = (ReportsBean) form;
			RateRenewalReportDTO objDto = new RateRenewalReportDTO();
			int isUsage =0;
			String userId,interfaceId;
			try {
				
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				interfaceId=(String) request.getParameter("interfaceId");
				userId=objUserDto.getUserId();
				System.out.println(userId+" "+interfaceId);
				Utility.setFileDumpParams(request,objForm);//Check objForm 
				if(request.getParameter("usage") != null)
					isUsage=Integer.parseInt(request.getParameter("usage"));
				else 
				{
					isUsage=objForm.getIsUsage();
				}
				objDto.setOrderType(objForm.getOrderType());
				objDto.setToDate(objForm.getToDate());
				objDto.setFromDate(objForm.getFromDate());
				objDto.setFromAccountNo(objForm.getFromAccountNo());
				objDto.setToAccountNo(objForm.getToAccountNo());
				objDto.setFromOrderNo(objForm.getFromOrderNo());
				objDto.setToOrderNo(objForm.getToOrderNo());
				objDto.setDemo(objForm.getDemo());
				objDto.setIsUsage(isUsage);
//				added by mohit
				objDto.setToCopcApprovedDate(objForm.getToCopcApprovedDate());
				objDto.setFromCopcApprovedDate(objForm.getFromCopcApprovedDate());				
				
				PagingSorting pagingSorting = objForm.getPagingSorting();

					String inExcel = objForm.getToExcel();
					if ("true".equals(inExcel)) {
						pagingSorting.setPagingSorting(false, true);
						request.setAttribute("userId",objUserDto.getUserId());
						forwardMapping = "exlRateRenwalReport_Usage";
					} else {
						pagingSorting.setPagingSorting(true, true);
						request.setAttribute("userId",objUserDto.getUserId());
						forwardMapping = "rateRenewalReport";
					}

					pagingSorting.setDefaultifNotPresent("ORDERNO",
							PagingSorting.increment, 1);
					objDto.setPagingSorting(pagingSorting);

					///orderList = objService.viewRateRenewalReport(objDto);
					String fromPageSubmit=request.getParameter("fromPageSubmit");				
					request.setAttribute("fromPageSubmit", fromPageSubmit);
					if("1".equals(fromPageSubmit))
					{//No of records limit : start
						if ("true".equals(inExcel)) {
							pagingSorting.setPagingSorting(true, true);
							pagingSorting.setStartRecordId(1);
							pagingSorting.setEndRecordIdForNoOfRecordsCount();
							orderList = objService.viewRateRenewalReport(objDto);//***************CHANGE HERE*****************
							if(orderList!=null && objDto.getPagingSorting().getRecordCount()>ApplicationFlags.ReportsExcelMaxSize)
							{	
								forwardMapping = "rateRenewalReport";//***************CHANGE HERE*****************
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
					 
						orderList = objService.viewRateRenewalReport(objDto);
					}
					}
					request.setAttribute("isUsage", isUsage);
					if ("true".equals(inExcel)) {
						objForm.setBcpExcelList(orderList);
						request.setAttribute("rateRenewalReportListExcel", orderList);
					} else {
						request.setAttribute("rateRenewalReportList", orderList);
						objForm.setCustomerList(orderList);
					}
				
			} catch (Exception ex) {
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
			ReportsModel_Usage objService = new ReportsModel_Usage();
			ReportsBean objForm = (ReportsBean) form;
			RestPendingLineReportDTO objDto = new RestPendingLineReportDTO();
			int isUsage =0;
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
				
				if(request.getParameter("usage") != null)
					isUsage=Integer.parseInt(request.getParameter("usage"));
				else 
				{
					isUsage=objForm.getIsUsage();
				}
				
				objDto.setToDate(objForm.getToDate());
				objDto.setFromDate(objForm.getFromDate());
				objDto.setVerticalDetails(objForm.getVerticalDetails());
				objDto.setServiceName(objForm.getServicename());
				objDto.setSubChangeTypeName(objForm.getSubtype());
				objDto.setIsUsage(isUsage);
				
				PagingSorting pagingSorting = objForm.getPagingSorting();

					String inExcel = objForm.getToExcel();
					if ("true".equals(inExcel)) {
						pagingSorting.setPagingSorting(false, true);
						request.setAttribute("userId",objUserDto.getUserId());
						forwardMapping = "exlRestPendingLineReport_Usage";
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
					{//No of records limit : start
						if ("true".equals(inExcel)) {
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
					}}
					request.setAttribute("isUsage", isUsage);

					if ("true".equals(inExcel)) {
						objForm.setBcpExcelList(orderList);
						request.setAttribute("RestPendingLineReportInExcel", orderList);
					} else {
						request.setAttribute("RestPendingLineReport", orderList);
						objForm.setCustomerList(orderList);
					}
				
			} catch (Exception ex) {
				ex.printStackTrace();
				return mapping.findForward(Messages
						.getMessageValue("errorGlobalForward"));
			}

			return mapping.findForward(forwardMapping);
		}
		
		
// disconnection line report		
		public ActionForward viewDisconnectionLineReport(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {

			String forwardMapping = "";
			ArrayList<DisconnectLineReportDTO> orderList = new ArrayList<DisconnectLineReportDTO>();
			ReportsModel_Usage objService = new ReportsModel_Usage();
			ReportsBean objForm = (ReportsBean) form;
			DisconnectLineReportDTO objDto = new DisconnectLineReportDTO();
			int isUsage =0;
			String userId,interfaceId;
			try {
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				interfaceId=(String) request.getParameter("interfaceId");
				userId=objUserDto.getUserId();
				System.out.println(userId+" "+interfaceId);
				
				Utility.setFileDumpParams(request,objForm);//Check objForm 
				if(request.getParameter("usage") != null)
					isUsage=Integer.parseInt(request.getParameter("usage"));
				else 
				{
					isUsage=objForm.getIsUsage();
				}
				ArrayList errors = new ArrayList();
				objDto.setOrdermonth(objForm.getOrdermonth());
				objDto.setToDate(objForm.getToDate());
				objDto.setFromDate(objForm.getFromDate());
				objDto.setServiceName(objForm.getServicename());
				objDto.setOrdersubtype(objForm.getOrdersubtype());
				objDto.setVerticalDetails(objForm.getVerticalDetails());
			    PagingSorting pagingSorting = objForm.getPagingSorting();
				objDto.setIsUsage(isUsage);

					String inExcel = objForm.getToExcel();
					if ("true".equals(inExcel)) {
						pagingSorting.setPagingSorting(false, true);
						request.setAttribute("userId",objUserDto.getUserId());
						forwardMapping = "exlDisconnLineItemReport_Usage";
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
					request.setAttribute("isUsage", isUsage);
					if("1".equals(fromPageSubmit))
					{
//						No of records limit : start
						if ("true".equals(inExcel)) {
							pagingSorting.setPagingSorting(true, true);
							pagingSorting.setStartRecordId(1);
							pagingSorting.setEndRecordIdForNoOfRecordsCount();
							orderList = objService.viewDisconnectionLineReport(objDto);//***************CHANGE HERE*****************
							if(orderList!=null && objDto.getPagingSorting().getRecordCount()>ApplicationFlags.ReportsExcelMaxSize)
							{	
								forwardMapping = "DisconnectLineReport";//***************CHANGE HERE*****************
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
						 
						 
						orderList = objService.viewDisconnectionLineReport(objDto);
					}}
				 

				

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
			ReportsModel_Usage objService = new ReportsModel_Usage();
			ReportsBean objForm = (ReportsBean) form;
			CancelledFailedLineReportDTO objDto = new CancelledFailedLineReportDTO();
			int isUsage =0;
			String userId,interfaceId;
			try {
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				interfaceId=(String) request.getParameter("interfaceId");
				userId=objUserDto.getUserId();
				System.out.println(userId+" "+interfaceId);
				Utility.setFileDumpParams(request,objForm);//Check objForm 
				if(request.getParameter("usage") != null)
					isUsage=Integer.parseInt(request.getParameter("usage"));
					else 
					{
						isUsage=objForm.getIsUsage();
					}
				ArrayList errors = new ArrayList();
				
				objDto.setToDate(objForm.getToDate());
				objDto.setFromDate(objForm.getFromDate());
				objDto.setServiceName(objForm.getServicename());
				objDto.setIsUsage(isUsage);
				PagingSorting pagingSorting = objForm.getPagingSorting();

					String inExcel = objForm.getToExcel();
					if ("true".equals(inExcel)) {
						pagingSorting.setPagingSorting(false, true);
						request.setAttribute("userId",objUserDto.getUserId());
						forwardMapping = "exlCancelFailedReport_Usage";
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
					request.setAttribute("isUsage", isUsage);
					if("1".equals(fromPageSubmit))
					{	//No of records limit : start
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
						//No of records limit : end
						orderList = objService.viewCancelledFailedLineReport(objDto);
						}
					}

					

					if ("true".equals(inExcel)) {
						objForm.setBcpExcelList(orderList);
						request.setAttribute("CancelledFailedLineReportInExcel", orderList);
					} else {
						request.setAttribute("CancelledFailedLineReport", orderList);
						objForm.setCustomerList(orderList);
					}
				
			} catch (Exception ex) {
				return mapping.findForward(Messages
						.getMessageValue("errorGlobalForward"));
			}

			return mapping.findForward(forwardMapping);
		}
		
		
		public ActionForward viewBulkSIUploadReport(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {

			String forwardMapping = "";
			ArrayList<ReportsDto> orderList = new ArrayList<ReportsDto>();
			ReportsModel_Usage objService = new ReportsModel_Usage();
			ReportsBean objForm = (ReportsBean) form;
			ReportsDto objDto = new ReportsDto();
			try {
				ArrayList errors = new ArrayList();
				objDto.setDate_of_inst(objForm.getDate_of_installation());
				objDto.setServiceName(objForm.getServicename());
				objDto.setParty_no(objForm.getPartyNo());
				objDto.setOrderNo(objForm.getOrderNo());
				objDto.setLogicalSINo(objForm.getLogical_SI_No());
				objDto.setLinename(objForm.getLinename());
				PagingSorting pagingSorting = objForm.getPagingSorting();

					String inExcel = objForm.getToExcel();
					if ("true".equals(inExcel)) {
						pagingSorting.setPagingSorting(false, true);
						forwardMapping = "displayReportInExcel";
					} else {
						pagingSorting.setPagingSorting(true, true);
						forwardMapping = "BulkSIUploadReport";
					}

					pagingSorting.setDefaultifNotPresent("CUSTOMER_LOGICAL_SI_NO",
							PagingSorting.increment, 1);
					objDto.setPagingSorting(pagingSorting);
					String fromPageSubmit=request.getParameter("fromPageSubmit");				
					request.setAttribute("fromPageSubmit", fromPageSubmit);
					if("1".equals(fromPageSubmit))
					{
						orderList = objService.viewBulkSIUploadReport(objDto);
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
			ArrayList<ReportsDto> orderList = new ArrayList<ReportsDto>();
			ReportsModel_Usage objService = new ReportsModel_Usage();
			ReportsBean objForm = (ReportsBean) form;
			ReportsDto objDto = new ReportsDto();
			try {
				ArrayList errors = new ArrayList();
				objDto.setCustLogicalSI(objForm.getCustLogicalSI());
				PagingSorting pagingSorting = objForm.getPagingSorting();

					String inExcel = objForm.getToExcel();
					if ("true".equals(inExcel)) {
						pagingSorting.setPagingSorting(false, true);
						forwardMapping = "displayReportInExcel";
					} else {
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
						orderList = objService.viewAttributeDetailsReport(objDto);
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
			ReportsModel_Usage objService = new ReportsModel_Usage();
			ReportsBean objForm = (ReportsBean) form;
			DisconnectChangeOrdeReportDTO objDto = new DisconnectChangeOrdeReportDTO();
			int isUsage =0;
			String userId,interfaceId;
			try {
				
					HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
					HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
					UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
					interfaceId=(String) request.getParameter("interfaceId");
					userId=objUserDto.getUserId();
					System.out.println(userId+" "+interfaceId);
				Utility.setFileDumpParams(request,objForm);//Check objForm 
					if(request.getParameter("usage") != null)
							isUsage=Integer.parseInt(request.getParameter("usage"));
					else 
					{
						isUsage=objForm.getIsUsage();
					}
				//ArrayList errors = new ArrayList();
				objDto.setOrdermonth(objForm.getOrdermonth());
				objDto.setToDate(objForm.getToDate());
				objDto.setFromDate(objForm.getFromDate());
				objDto.setServiceName(objForm.getServicename());
				objDto.setOrdersubtype(objForm.getOrdersubtype());
				objDto.setVerticalDetails(objForm.getVerticalDetails());
				objDto.setIsUsage(isUsage);
				objDto.setSrrequest(objForm.getSrrequest());
			    PagingSorting pagingSorting = objForm.getPagingSorting();

					String inExcel = objForm.getToExcel();
					if ("true".equals(inExcel)) {
						pagingSorting.setPagingSorting(false, true);
						request.setAttribute("userId",objUserDto.getUserId());
						forwardMapping = "exlDisconnectionChangeOrderReport_Usage";
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
						}else {
						 
						orderList = objService.viewDisconnectionChangeOrderReport(objDto);
					}
					}
				   request.setAttribute("isUsage", isUsage);
					if ("true".equals(inExcel)) {
						objForm.setBcpExcelList(orderList);
						request.setAttribute("DisconnectChangeOrderReportInExcel", orderList);
					} else {
						request.setAttribute("DisconnectChangeOrderReport", orderList);
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
			ReportsModel_Usage objService = new ReportsModel_Usage();
			ReportsBean objForm = (ReportsBean) form;
			MigratedApprovedNewOrderDetailReportDTO objDto = new MigratedApprovedNewOrderDetailReportDTO();
			int isUsage;
			String userId,interfaceId;
			try {
				
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				interfaceId=(String) request.getParameter("interfaceId");
				userId=objUserDto.getUserId();
				System.out.println(userId+" "+interfaceId);
				Utility.setFileDumpParams(request,objForm);
				
				if(request.getParameter("usage") != null){
					isUsage=Integer.parseInt(request.getParameter("usage"));
				}
				else 
				{
					isUsage=objForm.getIsUsage();
				}
					objDto.setIsUsage(isUsage);
					objDto.setOrdermonth(objForm.getOrdermonth());
					objDto.setOrderyear(objForm.getOrderyear());
					objDto.setServiceName(objForm.getServicename());				
					PagingSorting pagingSorting = objForm.getPagingSorting();
					String inExcel = objForm.getToExcel();
					if ("true".equals(inExcel)) 
					{
						pagingSorting.setPagingSorting(false, true);
						request.setAttribute("userId",objUserDto.getUserId());
						forwardMapping = "exlMigAppNewOrderReport_Usage";
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
					request.setAttribute("isUsage", isUsage);
					if("1".equals(fromPageSubmit))
					{//No of records limit : start
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
		ArrayList<ReportsDto> listSearchDetails = new ArrayList<ReportsDto>();
		ReportsModel_Usage objService = new ReportsModel_Usage();
		ReportsBean objForm = (ReportsBean) form;
		ReportsDto objDto = new ReportsDto();
		try {
				objDto.setOrderNo(objForm.getOrderNo());
				objDto.setCopcApproveDate(objForm.getCopcApproveDate());				
				PagingSorting pagingSorting = objForm.getPagingSorting();
				String inExcel = objForm.getToExcel();
				if ("true".equals(inExcel)) 
				{
					pagingSorting.setPagingSorting(false, true);
					forwardMapping = "displayReportInExcelExt";
				}
				else 
				{
					pagingSorting.setPagingSorting(true, true);
					forwardMapping = "LEPMOwnerReport";
				}
				pagingSorting.setDefaultifNotPresent("ORDERNO",
						PagingSorting.increment, 1);
				objDto.setPagingSorting(pagingSorting);
				String fromPageSubmit=request.getParameter("fromPageSubmit");				
				request.setAttribute("fromPageSubmit", fromPageSubmit);
				if("1".equals(fromPageSubmit))
				{
					listSearchDetails = objService.viewLEPMOwnerReport(objDto);
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
			ArrayList<ReportsDto> orderList = new ArrayList<ReportsDto>();
			ReportsModel_Usage objService = new ReportsModel_Usage();
			ReportsBean objForm = (ReportsBean) form;
			ReportsDto objDto = new ReportsDto();
			try {
				
				objDto.setCanceldate(objForm.getCanceldate());
				PagingSorting pagingSorting = objForm.getPagingSorting();

					String inExcel = objForm.getToExcel();
					if ("true".equals(inExcel)) {
						pagingSorting.setPagingSorting(false, true);
						forwardMapping = "displayReportInExcelExt";
					} else {
						pagingSorting.setPagingSorting(true, true);
						forwardMapping = "DisplayLEPMOrderCancelReport";
					}

					pagingSorting.setDefaultifNotPresent("ORDERNO",
							PagingSorting.increment, 1);
					objDto.setPagingSorting(pagingSorting);
					String fromPageSubmit=request.getParameter("fromPageSubmit");				
					request.setAttribute("fromPageSubmit", fromPageSubmit);
					if("1".equals(fromPageSubmit))
					{
						orderList = objService.viewLEPMOrderCancelReport(objDto);
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
			ArrayList<ReportsDto> orderList = new ArrayList<ReportsDto>();
			ReportsModel_Usage objService = new ReportsModel_Usage();
			ReportsBean objForm = (ReportsBean) form;
			ReportsDto objDto = new ReportsDto();
			try {
				objDto.setOrderType(objForm.getOrderType());
				objDto.setCopcApproveDate(objForm.getCopcapprovaldate());
				objDto.setVerticalDetails(objForm.getVerticalDetails());
				objDto.setToOrderNo(objForm.getToOrderNo());
				objDto.setFromOrderNo(objForm.getFromOrderNo());
				PagingSorting pagingSorting = objForm.getPagingSorting();

					String inExcel = objForm.getToExcel();
					if ("true".equals(inExcel)) {
						pagingSorting.setPagingSorting(false, true);
						forwardMapping = "displayReportInExcelExt";
					} else {
						pagingSorting.setPagingSorting(true, true);
						forwardMapping = "DisplayLEPMOrderReport";
					}

					pagingSorting.setDefaultifNotPresent("ORDERNO",
							PagingSorting.increment, 1);
					objDto.setPagingSorting(pagingSorting);
					String fromPageSubmit=request.getParameter("fromPageSubmit");				
					request.setAttribute("fromPageSubmit", fromPageSubmit);
					if("1".equals(fromPageSubmit))
					{
						orderList = objService.viewLEPMOrderDetailReport(objDto);
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
		ReportsModel_Usage objService = new ReportsModel_Usage();
		ReportsDto objDto = new ReportsDto();
		ReportsBean objForm = (ReportsBean) form;
		try 
		{
			objDto.setSearchCRMOrder(objForm.getOrderNo());
			objDto.setSearchfromDate(objForm.getFromDate());
			objDto.setSearchToDate(objForm.getToDate());
			objDto.setSearchSRNO(objForm.getSrNo());
			objDto.setSearchLSI(objForm.getLogical_SI_No());
			PagingSorting pagingSorting = objForm.getPagingSorting();
			String inExcel = objForm.getToExcel();
			if ("true".equals(inExcel)) {
					pagingSorting.setPagingSorting(false, true);
					forwardMapping = "displayReportInExcelExt";
			} else {
					pagingSorting.setPagingSorting(true, true);
					forwardMapping = "DisplayPendingBillingPDOrderReport";
			}

			pagingSorting.setDefaultifNotPresent("ORDERNO",
			PagingSorting.increment, 1);
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

	public ActionForward viewCustomerBaseReport(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {

			String forwardMapping = "";
			ArrayList<CustomerBaseReportsDTO> orderList = new ArrayList<CustomerBaseReportsDTO>();
			ReportsModel_Usage objService = new ReportsModel_Usage();
			ReportsBean objForm = (ReportsBean) form;
			CustomerBaseReportsDTO objDto = new CustomerBaseReportsDTO();
			String userId,interfaceId;
			try 
				{
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				interfaceId=(String) request.getParameter("interfaceId");
				userId=objUserDto.getUserId();
				System.out.println(userId+" "+interfaceId);
			{Utility.setFileDumpParams(request,objForm);//Check objForm 
				objDto.setCrmAccountNoString(objForm.getCrmAccountNo());
				objDto.setToDate(objForm.getToDate());
				objDto.setFromDate(objForm.getFromDate());
				PagingSorting pagingSorting = objForm.getPagingSorting();

				String inExcel = objForm.getToExcel();
				if ("true".equals(inExcel)) {
					pagingSorting.setPagingSorting(false, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "exlCustomerBaseReport";
				} else {
					pagingSorting.setPagingSorting(true, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "CustomerBaseReport";
				}

				pagingSorting.setDefaultifNotPresent("CRMACCOUNTNO",
						PagingSorting.increment, 1);
				objDto.setPagingSorting(pagingSorting);
				String fromPageSubmit=request.getParameter("fromPageSubmit");				
				request.setAttribute("fromPageSubmit", fromPageSubmit);
				if("1".equals(fromPageSubmit))
				{if ("true".equals(inExcel)) {
					pagingSorting.setPagingSorting(true, true);
					pagingSorting.setStartRecordId(1);
					pagingSorting.setEndRecordIdForNoOfRecordsCount();
					orderList = objService.viewCustomerBaseReport(objDto);//***************CHANGE HERE*****************
					if(orderList!=null && objDto.getPagingSorting().getRecordCount()>ApplicationFlags.ReportsExcelMaxSize)
					{	
						forwardMapping = "CustomerBaseReport";//***************CHANGE HERE*****************
						objForm.setReportProcessForLimit("1");
						objForm.setReportCurrentCount(String.valueOf(objDto.getPagingSorting().getRecordCount()));
						objDto.getPagingSorting().setRecordCount(0);
						objDto.getPagingSorting().setPageNumber(0);
						orderList=new ArrayList<CustomerBaseReportsDTO>();
					}else if (orderList!=null && objDto.getPagingSorting().getRecordCount()<=ApplicationFlags.ReportsExcelMaxSize){
						pagingSorting.setPagingSorting(false, true);
						orderList = objService.viewCustomerBaseReport(objDto);
					}else{
						orderList=null;
					}
				}else {
				//No of records limit : end
					orderList = objService.viewCustomerBaseReport(objDto);
				}
				}

				if ("true".equals(inExcel)) {
					objForm.setBcpExcelList(orderList);
					request.setAttribute("CustomerBaseReportInExcel", orderList);
				} else {
					request.setAttribute("CustomerBaseReport", orderList);
					objForm.setCustomerList(orderList);
				}
				
			}
				}catch (Exception ex) {
				return mapping.findForward(Messages
						.getMessageValue("errorGlobalForward"));
			}
			return mapping.findForward(forwardMapping);
		}

	public ActionForward viewBillingWorkQueueReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String forwardMapping = "";
		ArrayList<BillingWorkQueueReportDTO> orderList = new ArrayList<BillingWorkQueueReportDTO>();
		ReportsModel_Usage objService = new ReportsModel_Usage();
		ReportsBean objForm = (ReportsBean) form;
		BillingWorkQueueReportDTO objDto = new BillingWorkQueueReportDTO();
		int isUsage =0;
		String userId,interfaceId;
		try {
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			interfaceId=(String) request.getParameter("interfaceId");
			userId=objUserDto.getUserId();
			System.out.println(userId+" "+interfaceId);
			Utility.setFileDumpParams(request,objForm);//Check objForm
			if(request.getParameter("usage") != null){
				isUsage=Integer.parseInt(request.getParameter("usage"));
			} else {
				isUsage=objForm.getIsUsage();
			}
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
			objDto.setIsUsage(isUsage);

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
					// No of records limit : start
					if ("true".equals(inExcel)) {
						pagingSorting.setPagingSorting(true, true);
						pagingSorting.setStartRecordId(1);
						pagingSorting.setEndRecordIdForNoOfRecordsCount();
						orderList = objService.viewBillingWorkQueueList(objDto);//***************CHANGE HERE*****************
						if(orderList!=null && objDto.getPagingSorting().getRecordCount()>ApplicationFlags.ReportsExcelMaxSize)
						{	
							forwardMapping = "viewBillingWorkQueueReport";//***************CHANGE HERE*****************
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
						orderList = objService.viewBillingWorkQueueList(objDto);
					}
				}
				request.setAttribute("isUsage", isUsage);
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
	
	//Start [010]
		public ActionForward viewOBValueReport_Usage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
				throws Exception {

			String forwardMapping = "";
			ArrayList<OBValueReportDTO> orderList = new ArrayList<OBValueReportDTO>();
			ReportsModel_Usage reportsModel = new ReportsModel_Usage();
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
					forwardMapping = "displayOBReportInExcel_Usage";
				} else {
					pagingSorting.setPagingSorting(true, true);
					request.setAttribute("userId",objUserDto.getUserId());
					forwardMapping = "OBValueReport_Usage";
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
						orderList = reportsModel.viewOBValueReport_Usage(objDto);
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
							orderList = reportsModel.viewOBValueReport_Usage(objDto);
						}else{
							orderList=null;
						}
					}else{
						orderList = reportsModel.viewOBValueReport_Usage(objDto);
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

}
