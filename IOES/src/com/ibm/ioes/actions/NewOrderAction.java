package com.ibm.ioes.actions;
//Tag Name Resource Name  Date		CSR No			Description
//[001]	 ROHIT VERMA	18-Feb-11	00-05422		Zone and Region will be Fetched From Account Level
//[004]	 Rohit Verma		CSR00-05422		21-Feb-2011	System testing Defect
//[005]	 Lawkush			00-05422		4-March-11	In order to make nom mandatory field white and mandatory yellow 
//[006]	 Rohit verma		00-05422		3-Mar-11    Created By And Modified by for each Transaction
//[007]  Rakshika			00-05422  		4-Mar-11	File Download
//[008]	 Manisha		14-Mar-11	00-05422		Draft New Search
//[009] Vishwa			17-Mar-2011	00-05422		Populate Pincode based City and State
//[010]	 SAURABH SINGH	14-MAR-11	00-05422		To Copy services from one Order into Another
//[012]	 SAURABH SINGH	25-MAR-11	00-05422		To Create Partial Publish
//[00123]	 ASHUTOSH SINGH	 26-APR-11	00-05422		After Validation while saving main tab Appoval List is Not showing.
//[250511AKS] ASHUTOSH  	25-MAY-11   00-05422        Defects: Validate PO During Aciton close and Publish 
//[015]		Vishwa		17-Jul-2012	CSR00-05422		Code Changes for adding extra Extended Attributes in change Order
//[016]	 Manisha		06-Feb-13	00-05422		Reassign to pm (auto suggest drop down) defect no 98
//[017]		Roht Verma	28-Feb-13	CSR00-08440		Code Changes for New Method for Hardware Line item Cnacelation
//[018]		Neelesh		6-June-13	TRNG22032013037     For Mulitple Opportunity
//[019]		SANTOSH SRIVASTAVA	16-Nov-2013	UPDATE THE ADVANCEPAYMENT DETAILS METHOD
//[021]   Gunjan Singla      09-Dec-2013              Added a function orderCancelReason()
//[020]		Roht Verma			06-Feb-14	Rate Revision Bulk upload		Adding Export functionality for BCP
// [021] VIPIN SAHARIA 26-DEC-2013 	validation for populating repush button for SCM updation page in getTProductLineAttmasterSCMForUpdate
//[022]  Neha Maheshwari     17 Feb 2014            To enable Action Button for partial initiated case after save button is pressed.

//[220]  Anoop Tiwari        01 March 2013            Added if condition in incompleteOrder for O2C drop 2
//[221]  Anoop Tiwari        04 March 2013            setting value of Attribute isView in productCatalogforUpdate for O2C drop 2
//[222]	 Raveendra Kumar	25-Mar-15	20150225-R1-021110     Order transfer rights required on GUI for AM Role
//[223] VIPIN SAHARIA 12-May-2015 20150403-R2-021204 Project Satyapan Adding ISP tagging fields to header section
//[224] Gunjan Singla  29-May-15   SR1712831    User unable to cancel Order pending at AM
//[225] Priya Gupta   19-Jun-15    Added ldclause column 
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.LabelValueBean;

import com.ibm.ioes.beans.DefaultBean;
import com.ibm.ioes.beans.NewOrderBean;
import com.ibm.ioes.beans.ReportsBean;
import com.ibm.ioes.beans.UserInfoDto;
import com.ibm.ioes.beans.ValidationBean;
import com.ibm.ioes.beans.ViewOrderDto;
import com.ibm.ioes.dao.NewOrderDao;
import com.ibm.ioes.dao.NewOrderDaoExt;
import com.ibm.ioes.dao.ViewOrderDao;
import com.ibm.ioes.ecrm.CRMLogger;
import com.ibm.ioes.ecrm.FetchAccountFromCRM;
import com.ibm.ioes.exception.IOESException;
import com.ibm.ioes.forms.AdvancePaymentDTO;
import com.ibm.ioes.forms.Entity;
import com.ibm.ioes.forms.FileAttachmentDto;
import com.ibm.ioes.forms.MigrationOrdersDto;
import com.ibm.ioes.forms.NewOrderDto;
import com.ibm.ioes.forms.OrderForPendingWithAMDto;
import com.ibm.ioes.forms.ProductCatelogDTO;
import com.ibm.ioes.forms.ContactDTO;
import com.ibm.ioes.forms.FieldAttibuteDTO;
import com.ibm.ioes.forms.OrderHeaderDTO;
import com.ibm.ioes.forms.PagingDto;
import com.ibm.ioes.forms.PoDetailsDTO;
import com.ibm.ioes.forms.ServiceLineDTO;
import com.ibm.ioes.model.NewOrderModel;
import com.ibm.ioes.model.ReportsModel;
import com.ibm.ioes.model.ViewOrderModel;
import com.ibm.ioes.newdesign.dto.PendingOrderAndBillingReportDTO;
import com.ibm.ioes.npd.model.CommonBaseModel;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.AppUtility;
import com.ibm.ioes.utilities.ApplicationFlags;
import com.ibm.ioes.utilities.ExcelValidator;
import com.ibm.ioes.utilities.Messages;
import com.ibm.ioes.utilities.PagingSorting;
import com.ibm.ioes.utilities.Utility;
import com.ibm.ioes.ecrm.FetchAccountFromCRM;
import com.ibm.ioes.forms.LineItemValueDTO;
import com.ibm.ioes.forms.SCMDto;
import com.ibm.ioes.forms.PRDetailsDto;
public class NewOrderAction extends DispatchAction
{
	//	This Method Displays Accounts for Selection in a Popup
	public ActionForward fetchAccount(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		NewOrderBean formBean=new NewOrderBean();
		//NewOrderDto objDto=new NewOrderDto();
		OrderHeaderDTO objDto=new OrderHeaderDTO();
		NewOrderModel objModel=new NewOrderModel();
		ActionMessages messages = new ActionMessages();
		ArrayList<NewOrderDto> listAccount = new ArrayList<NewOrderDto>();
		try
		{
			objDto.setSearchAccount(formBean.getSearchAccount());
			//listAccount = objModel.displayAccountDetails(objDto);
			//request.setAttribute("AccountListBean", listAccount);
			forward = mapping.findForward("DisplaySelectAccount");
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		return forward;
	}
	
	//Added By Ashutosh as on Date : 03-Oct-2011	
	public ActionForward fetchCustLogicalSI(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		
		ActionMessages messages = new ActionMessages();
		ArrayList<NewOrderDto> listAccount = new ArrayList<NewOrderDto>();
		try
		{
			//objDto.setSearchAccount(formBean.getSearchAccount());
			//listAccount = objModel.displayCustLogicalSIDetails(objDto);
			//request.setAttribute("CustLogicalSIListBean", listAccount);
			forward = mapping.findForward("DisplaySelectCustLogicalSI");
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		return forward;
	}
	// to get Order List

	// 008 start
	
	
	public ArrayList<NewOrderDto> viewOrderList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String forwardMapping = "";
		ArrayList<NewOrderDto> orderList = new ArrayList<NewOrderDto>();
		NewOrderModel objService = new NewOrderModel();
		DefaultBean objForm = (DefaultBean) form;
		//DefaultBean objForm=new DefaultBean();
		NewOrderDto objDto = new NewOrderDto();
		ArrayList<OrderHeaderDTO> listSourceName =null;
		ArrayList<OrderHeaderDTO> listCurrency = null;
		try {
			// Validate form fields
			
			ActionMessages messages = new ActionMessages();
			String searchCRMOrder =  objForm.getSearchCRMOrder();
			String searchAccountNo = objForm.getSearchAccountNo();
			String searchAccountName=objForm.getSearchAccountName();
			searchAccountName = (searchAccountName != null) ? searchAccountName.trim() : searchAccountName;
			String searchfromDate=objForm.getSearchfromDate();
			String searchToDate=objForm.getSearchToDate();
			String searchSource=objForm.getSearchSource();
			String searchQuoteNumber=objForm.getSearchQuoteNumber();
			String searchCurrency=objForm.getSearchCurrency();
			String searchOrderType=objForm.getSearchOrderType();
			String searchStageName=objForm.getSearchStageName();
		

			ArrayList errors = new ArrayList();

			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"ORDER NO",searchCRMOrder , 200),
					Utility.generateCSV(
							Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
					errors);
			
			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"A/C No",searchAccountNo , 18),
					Utility.generateCSV(Utility.CASE_MAXLENGTH
							)).appendToAndRetNewErrs(
					errors);
			
			


			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"A/C Name",searchAccountName , 200),
					Utility.generateCSV(
							Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
					errors);
			

			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"ORDER TYPE",searchOrderType , 200),
					Utility.generateCSV(
							Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
					errors);
			
			
			
			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"FROM DATE",searchAccountName , 200),
							Utility.generateCSV(
									Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
							errors);

			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"TO DATE",searchToDate , 200),
					Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS,
							Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
					errors);
			
		
			
			
			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"SOURCE NAME",searchSource , 200),
					Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS,
							Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
					errors);

		
			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"QUOTE NUMBER",searchQuoteNumber , 200),
					Utility.generateCSV(
							Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
					errors);

		
		

			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"CURRENCY",searchCurrency , 200),
					Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS,
							Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
					errors);
			
			
			
			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"STAGE",searchStageName, 200),
					Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS,
							Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
					errors);
			
			
			
			
			

			if (errors != null && errors.size() > 0)// During Server Side
			// Validation
			{
				request.setAttribute("orderList", orderList);
				objForm.setOrderList(orderList);
				request.setAttribute("validation_errors", errors);
				saveMessages(request, messages);
				//return mapping.findForward("displayOrderList");
			}
			// ******* Validation Ends Here
			// *************************************
			else {
				objDto.setSearchCRMOrder(searchCRMOrder);
				objDto.setSearchAccountNo(searchAccountNo);
				objDto.setSearchAccountName(searchAccountName);
				objDto.setSearchOrderType(searchOrderType);
				objDto.setSearchfromDate(searchfromDate);
				objDto.setSearchToDate(searchToDate);
			
				objDto.setSearchSource(searchSource);
		        objDto.setSearchCurrency(searchCurrency);
				objDto.setSearchQuoteNumber(searchQuoteNumber);
				objDto.setSearchStageName(searchStageName);
				
				
				listSourceName=objService.getSourceName();
				request.setAttribute("listSourceName", listSourceName);
				
				listCurrency=objService.getCurrency();
				request.setAttribute("listCurrency", listCurrency);

				PagingSorting pagingSorting = objForm.getPagingSorting();

				pagingSorting.setDefaultifNotPresent("ORDERNO",
						PagingSorting.increment, 1);
				pagingSorting.setPagingSorting(true, true);
				objDto.setPagingSorting(pagingSorting);

				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				long empID=Long.parseLong(objUserDto.getEmployeeId()); 
				
				orderList = objService.viewOrderList(objDto, empID);
				
			
			
				
			}
		} catch (Exception ed) {
			ed.printStackTrace();
			AppConstants.IOES_LOGGER.error("Error while getting saveUploadedFileInfo "
					+ ed.getMessage());
			throw new IOESException(ed);
		}

	
		return orderList;
		
	}

	
	
	//008 end
	
	
	//[015] added an additional parameter to pass the type of order
	public ArrayList<FieldAttibuteDTO> fetchMainDetails(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response, String OrderType)	throws Exception 
	{
		//ActionForward forward = new ActionForward(); // return value
		//NewOrderBean formBean=new NewOrderBean();
		NewOrderDto objDto=new NewOrderDto();
		NewOrderModel objModel=new NewOrderModel();
		ActionMessages messages = new ActionMessages();
		//[015] Setting the Order Type received from default action
		objDto.setOrderType(OrderType);
		ArrayList<FieldAttibuteDTO> listMainDetails = null;
		try
		{
			listMainDetails = objModel.displayMainDetails(objDto);
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		return listMainDetails;
	}
//	[018]	Start
	//	This Method Displays Opportunity for Selection in a Popup
	public ActionForward fetchOpportunity(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		
		ActionForward forward = new ActionForward(); // return value
		String orderNo = null;
		String stageName = null;
		ActionMessages messages = new ActionMessages();		
		try
		{						
			orderNo=request.getParameter("orderNo");
			stageName=request.getParameter("stageName");
			
			request.setAttribute("orderNo", orderNo);
			if(stageName.equalsIgnoreCase("new") || stageName.equalsIgnoreCase("valid"))
				forward = mapping.findForward("DisplaySelectOpportunity");
			else
				forward = mapping.findForward("DisplayViewOpportunity");
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		
		return forward;
	}
//	[018]		End
	//	This Method Displays Currency for Selection in a Popup
	public ActionForward fetchCurrency(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		NewOrderBean formBean=new NewOrderBean();
		PagingDto objDto=new PagingDto();
		NewOrderModel objModel=new NewOrderModel();
		ActionMessages messages = new ActionMessages();
		ArrayList<NewOrderDto> listCurrency = new ArrayList<NewOrderDto>();
		int currencyCount=0;
		
		try
		{
			if(!"".equalsIgnoreCase(request.getParameter("currencyCount")  ) && request.getParameter("currencyCount") !=null)
			{
			    currencyCount=Integer.parseInt(request.getParameter("currencyCount"));
				
			}
			//listCurrency = objModel.displayCurrencyDetails(objDto);
			request.setAttribute("CurrencyListBean", listCurrency);
			request.setAttribute("currencyCount", currencyCount);
			forward = mapping.findForward("DisplaySelectCurrency");
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		return forward;
	}
	
	public ActionForward changeFieldEngineer(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		return mapping.findForward("ChangeFieldEngineer");
	}
	
	public ActionForward addFieldEngineer(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		return mapping.findForward("AddFieldEngineer");
	}
	
	public ActionForward displayFieldEngineer(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{

		ActionForward forward = new ActionForward(); // return value
		NewOrderBean formBean=new NewOrderBean();
		PagingDto objDto=new PagingDto();
		NewOrderModel objModel=new NewOrderModel();
		ActionMessages messages = new ActionMessages();
		//ArrayList<NewOrderDto> listCurrency = new ArrayList<NewOrderDto>();
		int partnerId=0;
		
		try
		{
			if(!"".equalsIgnoreCase(request.getParameter("partnerId")  ) && request.getParameter("partnerId") !=null)
			{
				partnerId=Integer.parseInt(request.getParameter("partnerId"));
				
			}
			//listCurrency = objModel.displayCurrencyDetails(objDto);
		//	request.setAttribute("CurrencyListBean", listCurrency);
			request.setAttribute("partnerId", partnerId);
			forward = mapping.findForward("DisplayFE");
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		return forward;
	
		
		/*request.setAttribute("partnerId", request.getParameter("partnerId"));
		return mapping.findForward("DisplayFieldEngineer");*/
	}
	
		public ActionForward fetchChannelPartner(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		NewOrderBean formBean=new NewOrderBean();
		PagingDto objDto=new PagingDto();
		NewOrderModel objModel=new NewOrderModel();
		ActionMessages messages = new ActionMessages();
		ArrayList<NewOrderDto> listPartner = new ArrayList<NewOrderDto>();
		int partnerCount=0;
		
		try
		{
			if(!"".equalsIgnoreCase(request.getParameter("partnerCount")  ) && request.getParameter("partnerCount") !=null)
			{
				partnerCount=Integer.parseInt(request.getParameter("partnerCount"));
				
			}
			//listCurrency = objModel.displayCurrencyDetails(objDto);
			//request.setAttribute("listPartner", listPartner);
			//request.setAttribute("partnerCount", partnerCount);
			forward = mapping.findForward("DisplayChannelPartner");
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		return forward;
	}
	
	// This Method gets Max Order No from Database
	public int getMaxOrderNo()
	{
		int maxOrderNo=0;
		
		NewOrderBean formBean=new NewOrderBean();
		NewOrderDto objDto=new NewOrderDto();
		NewOrderModel objModel=new NewOrderModel();
		ActionMessages messages = new ActionMessages();
		try
		{
			maxOrderNo = objModel.getMaxOrderNo(objDto);
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		//return forward;
		return maxOrderNo;
	}

	public ActionForward fetchSource(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		NewOrderBean formBean=new NewOrderBean();
		NewOrderDto objDto=new NewOrderDto();
		NewOrderModel objModel=new NewOrderModel();
		ActionMessages messages = new ActionMessages();
		ArrayList<NewOrderDto> listSource = new ArrayList<NewOrderDto>();
		try
		{
			listSource = objModel.displaySourceDetails(objDto);
			request.setAttribute("SourceListBean", listSource);
			forward = mapping.findForward("DisplaySelectSource");
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		return forward;
	}

	public ActionForward insertUpdateMain(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception
	{
		ActionForward forward = new ActionForward(); // return value
		NewOrderBean formBean=(NewOrderBean)form;
		NewOrderDto objDto=new NewOrderDto();
		NewOrderModel objModel=new NewOrderModel();
		ActionMessages messages = new ActionMessages();
		ArrayList<OrderHeaderDTO> listAccountDetails = null;
		ArrayList<FieldAttibuteDTO> listMainDetailsWithAttributes = null;
		boolean errorsFound=false;
		try
		{
			
			//setting the new Page
			formBean.setNewFormOpened(request.getParameter("newFormOpened"));
			// [006] Start
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			long empID=Long.parseLong(objUserDto.getEmployeeId());
			// [006] End
			
			//Server Side Security Start for Account ID
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(formBean.getAccountID(),"Account ID",30),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Account Manager
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(formBean.getAccountManager(),"Account Manager",200),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Account Name
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(formBean.getAccountName(),"Party Name",1000),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Project Manager
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(formBean.getProjectManager(),"Project Manager",200),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Phone No
			/*if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(formBean.getAccphoneNo(),"Phone No",30),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}*/
			//Server Side Security End
			
			//Server Side Security Start for Order Type 
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(formBean.getOrderType(),"Order Type",10),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			//[004] Start
			//Server Side Security Start for Stage
			/*
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(formBean.getStage(),"Stage",10),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}*/
			//Server Side Security End
			//[004] Start
			
			//Server Side Security Start for Source
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(formBean.getSourceName(),"Source",20),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for CurCode
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(formBean.getCurShortCode(),"Currency",20),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Quote No
			
			if(formBean.getSourceName().equalsIgnoreCase("CRMQUOTE"))
			if(formBean.getQuoteNo().equalsIgnoreCase("") && !formBean.getTxtquotesNo().equalsIgnoreCase(""))
				formBean.setQuoteNo(formBean.getTxtquotesNo());

			
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(formBean.getQuoteNo(),"Quote No",50),
						""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Order Date
			/*if(!errorsFound)
			{
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(AppConstants.DATE_FORMAT_PROC);
				Object obArray[]={""+ValidationBean.VN_DATE_COMPARISION,formBean.getOrderDate(),"Order Date",
						simpleDateFormat.format(new Date()),"Current Date",ValidationBean.EQUAL,
						new java.text.SimpleDateFormat(Messages.getMessageValue("calendar_entry_format"))};
				ArrayList errors=Utility.validateValue(	new ValidationBean(obArray),
						""+Utility.CASE_DATECOMPARISION_MANDATORY).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}*/
			//Server Side Security End
			
			//[001] Start
			/*
			//Server Side Security Start for Region
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(formBean.getRegionId(),"Region",20),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_DIGITS_ONLY+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Zone
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(formBean.getZoneId(),"Zone",20),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_DIGITS_ONLY+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			*/
			//[001] End
			//Server Side Security Start for Sales Person First Name
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(formBean.getSpFirstname(),"Sales Person First Name",50),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Sales Person Last Name
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(formBean.getSpLastName(),"Sales Person Last Name",50),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
						
			
			/*
			//Server Side Security Start for Sales Person Phone No
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(formBean.getSpLPhno(),"Sales Person Phone No",30),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Sales Person Phone No
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(formBean.getSpLEmail(),"Sales Person Email ID",30),
						""+Utility.CASE_EMAIL+","+Utility.CASE_MANDATORY).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			*/
			String[] attributeVal=new String [formBean.getAttCount()];
			String[] attributeID=new String [formBean.getAttCount()];
			String[] dataType=new String [formBean.getAttCount()];
			String[] attributeName=new String [formBean.getAttCount()];
			String[] attributeExpectedValue=new String [formBean.getAttCount()];
			String[] attributeMandatory=new String [formBean.getAttCount()];
			for(int j=0;j<=(formBean.getAttCount())-1;j++)
			{
				attributeVal[j] =request.getParameter("AttributeVal_"+(j+1));
				attributeID[j]=request.getParameter("hdnAttributeID_"+(j+1));
				attributeExpectedValue[j]=request.getParameter("hdnAttributeExpectedValue_"+(j+1));
				attributeName[j]=request.getParameter("hdnAttributeName_"+(j+1));
				//lawkush
				//Start[005]
				//attributeMandatory[j]=request.getParameter("Mandatory_"+(j+1));
				attributeMandatory[j]=request.getParameter("IsRequired_"+(j+1));
				//End[005]
				
				//Server Side Security Start for Attributes
				//Start[005]
				if(attributeMandatory[j].equalsIgnoreCase("1"))
				{
					//	End[005]
					if(!errorsFound)
					{
						if(attributeExpectedValue[j].equalsIgnoreCase("numeric"))
						{
							ArrayList errors=Utility.validateValue(new ValidationBean(attributeVal[j],attributeName[j],30),
									""+Utility.CASE_DIGITS_ONLY+","+Utility.CASE_MANDATORY).getCompleteMessageStrings();
							if(errors!=null)
							{
								request.setAttribute("validation_errors", errors);
								errorsFound=true;
								//break;
							}
						}
						
						if(attributeExpectedValue[j].equalsIgnoreCase("email"))
						{
							ArrayList errors=Utility.validateValue(new ValidationBean(attributeVal[j],attributeName[j],30),
									""+Utility.CASE_EMAIL+","+Utility.CASE_MANDATORY).getCompleteMessageStrings();
							if(errors!=null)
							{
								request.setAttribute("validation_errors", errors);
								errorsFound=true;
								//break;
							}
						}
						
						if(attributeExpectedValue[j].equalsIgnoreCase("YN"))
						{
							ArrayList errors=Utility.validateValue(new ValidationBean(attributeVal[j],attributeName[j],30),
									""+Utility.CASE_YN+","+Utility.CASE_MANDATORY).getCompleteMessageStrings();
							if(errors!=null)
							{
								request.setAttribute("validation_errors", errors);
								errorsFound=true;
								//break;
							}
						}
						
						if(attributeExpectedValue[j].equalsIgnoreCase("datetime"))
						{
							Object obArray[]={""+ValidationBean.VN_DATE_VALID,attributeVal[j],attributeName[j],
									new java.text.SimpleDateFormat(Messages.getMessageValue("calendar_entry_format"))};
							ArrayList errors=Utility.validateValue(new ValidationBean(obArray),
									""+Utility.CASE_MANDATORY+","+Utility.CASE_VN_DATE_VALID).getCompleteMessageStrings();
							if(errors!=null)
							{
								request.setAttribute("validation_errors", errors);
								errorsFound=true;
							}
						}
					}
				}
				else
				{
					if(!errorsFound)
					{
						if(attributeExpectedValue[j].equalsIgnoreCase("numeric"))
						{
							ArrayList errors=Utility.validateValue(new ValidationBean(attributeVal[j],attributeName[j],30),
									""+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
							if(errors!=null)
							{
								request.setAttribute("validation_errors", errors);
								errorsFound=true;
								//break;
							}
						}
						
						if(attributeExpectedValue[j].equalsIgnoreCase("email"))
						{
							ArrayList errors=Utility.validateValue(new ValidationBean(attributeVal[j],attributeName[j],30),
									""+Utility.CASE_EMAIL).getCompleteMessageStrings();
							if(errors!=null)
							{
								request.setAttribute("validation_errors", errors);
								errorsFound=true;
								//break;
							}
						}
						
						if(attributeExpectedValue[j].equalsIgnoreCase("YN"))
						{
							ArrayList errors=Utility.validateValue(new ValidationBean(attributeVal[j],attributeName[j],30),
									""+Utility.CASE_YN).getCompleteMessageStrings();
							if(errors!=null)
							{
								request.setAttribute("validation_errors", errors);
								errorsFound=true;
								//break;
							}
						}
						
						if(attributeExpectedValue[j].equalsIgnoreCase("datetime"))
						{//start[005]
							ArrayList errors=null;
							if (!"".equals(attributeVal[j]))
							{
								Object obArray[]={""+ValidationBean.VN_DATE_VALID,attributeVal[j],attributeName[j],
										new java.text.SimpleDateFormat(Messages.getMessageValue("calendar_entry_format"))};
								 errors=Utility.validateValue(new ValidationBean(obArray),
										""+Utility.CASE_VN_DATE_VALID).getCompleteMessageStrings();	
							}
							/*else
							{
							Object obArray[]={""+ValidationBean.VN_DATE_VALID,attributeVal[j],attributeName[j],
									new java.text.SimpleDateFormat(Messages.getMessageValue("calendar_entry_format"))};
							 errors=Utility.validateValue(new ValidationBean(obArray),
									""+Utility.CASE_VN_DATE_VALID).getCompleteMessageStrings();
							}*/
							//end[005]
							if(errors!=null)
							{
								request.setAttribute("validation_errors", errors);
								errorsFound=true;
							}
						}
					}
				}
				//Server Side Security End
			}
			if(errorsFound)
			{
				saveMessages(request, messages);
				BeanUtils.copyProperties(new NewOrderBean(), formBean);
				request.setAttribute("MaxOrderBean", Integer.valueOf(formBean.getPoNumber()));
				ArrayList<FieldAttibuteDTO> listMainDetails = new ArrayList<FieldAttibuteDTO>();
				String OrderType=formBean.getOrderType();
				listMainDetails=fetchMainDetails(mapping, form, request, response,OrderType);
				
				for(int j=0; j<listMainDetails.size(); j++)
				{
					listMainDetails.get(j).setAttributeValue(attributeVal[j]);
				}
				 request.setAttribute("MainDetailsBean", listMainDetails);
				forward = mapping.findForward(AppConstants.FAILURE);
				return forward;
			}
			else
			{
				int updateType=Integer.valueOf(formBean.getUpdateType());
				
				//------Changes Made By Sumit For Demo Order---------------------------------------
				if(formBean.getChkIsDemo()==null || formBean.getChkIsDemo().equalsIgnoreCase("") )
					formBean.setChkIsDemo("N");	
				else
				if(formBean.getChkIsDemo().equalsIgnoreCase("on"))
					formBean.setChkIsDemo("D");	
				
				if(formBean.getNoOfDaysForDemo()==null || formBean.getNoOfDaysForDemo().equalsIgnoreCase(""))
					formBean.setNoOfDaysForDemo("0");	

				//------Changes Made By Sumit For Demo Order---------------------------------------
					formBean.setIspLicDate(request.getParameter("ispLicDate"));
					//if(Long.valueOf(request.getParameter("channelMasterTagging"))!=0)
				if(!"0".equals(request.getParameter("channelMasterTagging")))
					{
					formBean.setChannelMasterTagging(request.getParameter("channelMasterTagging"));
					formBean.setChannelPartnerId(Long.valueOf(request.getParameter("channelPartnerId")));
					if(request.getParameter("fieldEngineerId")!=null && !request.getParameter("fieldEngineerId").equalsIgnoreCase(""))
					formBean.setFieldEngineerId(Integer.valueOf(request.getParameter("fieldEngineerId")));
						else
							formBean.setFieldEngineerId(0);
						
					}
				else
					{
					formBean.setChannelPartnerId(0);
					formBean.setFieldEngineerId(0);
					formBean.setChannelpartnerCode("0");
					}	
				//	
					
					//formBean.setChannelPartnerCtgry(listAccountDetails.get(0).getChannelPartnerCtgry());
					
				//[006] Start
				//long orderNo=objModel.insertUpdateMain(formBean,updateType,attributeVal,attributeID);
				long orderNo=objModel.insertUpdateMain(formBean,updateType,attributeVal,attributeID,empID);
				//[006] End
				if(orderNo==0)
				{
					messages.add("saveMain",new ActionMessage("MainInstertedFailed"));
				}
				else
				{
					String modeName=request.getParameter("modeName");
					//ArrayList<NewOrderDto> projectManagerNameList = new ArrayList<NewOrderDto>();
					//ArrayList<NewOrderDto> projectManagerNameListAll = new ArrayList<NewOrderDto>();
					//projectManagerNameList=objModel.getProjectManagerNameList(Long.parseLong(formBean.getAccountID()));
					//projectManagerNameListAll = objModel.getProjectManagerNameList();
					//request.setAttribute("projectManagerNameList", projectManagerNameList);
					//request.setAttribute("projectManagerNameListAll", projectManagerNameListAll);

					ArrayList<NewOrderDto> listRegion= new ArrayList<NewOrderDto>();
					listAccountDetails=objModel.getAccountDetails(objDto,orderNo);
					request.setAttribute("accountDetailsBean", listAccountDetails);		
					
					
					
				  /*//raghu showing Sales cordinator Details
					objDto=objModel.fetchSalesPersonDetails(empID);
					formBean.setSpFirstname(objDto.getSalesFirstName());
					formBean.setSpLastName(objDto.getSalesLastName());
					formBean.setSpLPhno(String.valueOf(objDto.getSalesPhoneno()));
					formBean.setSpLEmail(objDto.getSalesEmailId());*/
					
					//
					//listRegion=objModel.getRegionList();
					//request.setAttribute("listRegion", listRegion);
					listAccountDetails=objModel.getAccountDetails(objDto,orderNo);
					request.setAttribute("accountDetailsBean", listAccountDetails);
					
					/*
					 * set ispmPresent 
					 */
					request.setAttribute("ispmpresent", String.valueOf(listAccountDetails.get(0).getIsPMPresent()));
					
					/*if PM present on approval section then fetch all PM list  */
					if(1==listAccountDetails.get(0).getIsPMPresent()){
						ArrayList<NewOrderDto> projectManagerNameListAll = new ArrayList<NewOrderDto>();
						projectManagerNameListAll = objModel.getProjectManagerNameList();
						request.setAttribute("projectManagerNameListAll", projectManagerNameListAll);
					}
					
					//formBean.setRegionId(String.valueOf((listAccountDetails.get(0).getRegionId())));
					//formBean.setZoneId(String.valueOf((listAccountDetails.get(0).getZoneId())));
					//[223] Start PROJECT SATYAPAN
					formBean.setIspTagging(listAccountDetails.get(0).getIspTagging());
					formBean.setIspLicCtgry(listAccountDetails.get(0).getIspLicCtgry());
					formBean.setIspLicDate(listAccountDetails.get(0).getIspLicDate());
					formBean.setIspLicNo(listAccountDetails.get(0).getIspLicNo());
					//[223] End PROJECT SATYAPAN
					
					formBean.setChannelMasterTagging(listAccountDetails.get(0).getChannelMasterTagging());
					formBean.setChannelPartnerId(listAccountDetails.get(0).getChannelPartnerId());
					formBean.setChannelPartnerName(listAccountDetails.get(0).getChannelPartnerName());
					formBean.setChannelpartnerCode(listAccountDetails.get(0).getChannelpartnerCode());
					formBean.setFieldEngineerId(listAccountDetails.get(0).getFieldEngineerId());
					formBean.setFieldEngineer(listAccountDetails.get(0).getFieldEngineer());
					//pankaj channel partner
					
					formBean.setStageName(String.valueOf(listAccountDetails.get(0).getStageName()));
					listMainDetailsWithAttributes=objModel.getMainDetails(objDto,orderNo);
					request.setAttribute("MainDetailsWithAttributesBean", listMainDetailsWithAttributes);
					if(listAccountDetails.get(0).getContactCount() == 1){
					ArrayList<ContactDTO> listContactDetails= null;
					ArrayList<NewOrderDto> listAddressDetails= new ArrayList<NewOrderDto>();
					listContactDetails=objModel.getContactDetails(Long.parseLong(formBean.getPoNumber()));
					listAddressDetails=objModel.getAddressDetails(Long.parseLong(formBean.getPoNumber()));
					request.setAttribute("listContactDetails", listContactDetails);
					request.setAttribute("listAddressDetails", listAddressDetails);
					}
					/*MAIN TAB PO DETAILS START*/
					if(listAccountDetails.get(0).getPoCount() == 1)
					{
					ArrayList<PoDetailsDTO> listPODetails= null;
					listPODetails=objModel.getPODetails(Long.parseLong(formBean.getPoNumber()));
					request.setAttribute("listPoDetails", listPODetails);
					}
					/*MAIN TAB PO DETAILS ENDS*/
					
					request.setAttribute("approvalEditMode",modeName);
					
					
					messages.add("saveMain",new ActionMessage("MainInstertedSuccess"));
					
					//get Approval List after validation 
					//[00123] start
					ArrayList lstTo = new ArrayList();
					//ViewOrderFormBean objForm=(ViewOrderFormBean)form;
					//ViewOrderFormBean objForm=new ViewOrderFormBean();
					ViewOrderModel objViewOrderModel = new ViewOrderModel();
					lstTo.add(new LabelValueBean("Account Manager","1"));
					lstTo.add(new LabelValueBean("Project Manager","2"));
					lstTo.add(new LabelValueBean("CPOC","3"));
					
					String strOrderPublishBillingTrigger=null;
					HttpSession session = request.getSession();					
					long roleId=Long.parseLong(objUserDto.getUserRoleId());
					strOrderPublishBillingTrigger=objViewOrderModel.fnIsOrderPublishedBillingTrigger(orderNo , roleId);
					
					String[] strArr=new String[1];
					strArr=strOrderPublishBillingTrigger.split("@@");
					formBean.setIsOrderPublished(strArr[0]);
					formBean.setIsBillingTriggerReady(strArr[1]);
					//formBean.setIsOrderWorkflowRelaunch(strArr[2]);
					
					ArrayList aList=objViewOrderModel.getTaskListOfOrder(orderNo);
					String owner = new Utility().getOrderOwnedBy(aList);
					//[022] Start
					if(String.valueOf(listAccountDetails.get(0).getStageName()).equalsIgnoreCase(AppConstants.ORDER_STAGE_PARTIAL_INITIATED)){
						formBean.setOrderOwner(String.valueOf(roleId));
					}
					else{
						formBean.setOrderOwner(owner);
					}
					//[022] End
					ArrayList aListHistory=objViewOrderModel.getTaskListHistory(orderNo);
					request.setAttribute("taskListOfOrder", aList);
					request.setAttribute("taskListHistoryOfOrder", aListHistory);
					request.setAttribute("lstTo", lstTo);
					request.setAttribute("showAttachIcon", "YES");
					String m6Status=request.getParameter("m6Status");
					if("1".equals(m6Status))
					{
						request.setAttribute("m6StatusCode", "1");
					}else if("-1".equals(m6Status))
					{
						request.setAttribute("m6StatusCode", "-1");
					}
					//[00123] END
					int flag=(Integer.parseInt(request.getParameter("flag")));
					request.setAttribute("flag",flag);
					
				}	
			}
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		
		
		forward=mapping.findForward("NewOrderUpdateDisplay");
		return forward;
	}
	
	//	Method used for inserting CONTACT Details in tPOmaster,tPOADDRESS for CONTACT Tab
	public ActionForward insertContactTabDetail(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception
	{
		String forwardMapping = null;
		NewOrderBean formBean=(NewOrderBean)form;
		NewOrderDto objDto=new NewOrderDto();
		NewOrderModel objModel=new NewOrderModel();
		ActionMessages messages = new ActionMessages();
		ArrayList<OrderHeaderDTO> listAccountDetails = null;
		ArrayList<FieldAttibuteDTO> listMainDetailsWithAttributes = null;
		boolean errorsFound=false;
		String modeName=request.getParameter("modeName");
		try
		{
//			setting the new Page
			formBean.setNewFormOpened(request.getParameter("newFormOpened"));
			//[006] Start
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			long empID=Long.parseLong(objUserDto.getEmployeeId());
			// [006] End
			int updateFlag=Integer.parseInt(formBean.getContactUpdateType());
			int count=request.getParameterValues("contactId").length;
			String[] contactId=new String [count];
			String[] contactType=new String [count];
			String[] contactTypeId=new String [count];
			String[] salutationName=new String [count];
			String[] FName=new String [count];
			String[] LName=new String [count];
			String[] CntEmail=new String [count];
			String[] ContactCell=new String [count];
			String[] ContactFax=new String [count];
			
			contactId =request.getParameterValues("contactId");
			contactType =request.getParameterValues("contactType");
			contactTypeId =request.getParameterValues("contactTypeId");
			salutationName =request.getParameterValues("salutationName");
			FName =request.getParameterValues("firstName");
			LName =request.getParameterValues("lastName");
			CntEmail =request.getParameterValues("cntEmail");
			ContactCell =request.getParameterValues("contactCell");
			ContactFax =request.getParameterValues("contactFax");
			System.out.println(LName+" "+CntEmail+" "+ContactCell+" "+ContactFax+" ");
			
			Map<String, String[]> contactMap= new HashMap<String, String[]>();
			contactMap.put("contactId", contactId);
			contactMap.put("contactType", contactType);
			contactMap.put("contactTypeId", contactTypeId);
			contactMap.put("SalutationName", salutationName);
			contactMap.put("FName", FName);
			contactMap.put("LName", LName);
			contactMap.put("CntEmail", CntEmail);
			contactMap.put("ContactCell", ContactCell);
			contactMap.put("ContactFax", ContactFax);
			
			String[] addID=new String [count];
			String[] Address1=new String [count];
			String[] Address2=new String [count];
			String[] Address3=new String [count];
			String[] CityId=new String [count];
			String[] StateId=new String [count];
			String[] CountyCode=new String [count];
			String[] AddPin=new String [count];
			
			addID =request.getParameterValues("addID");
			Address1 =request.getParameterValues("address1");
			Address2 =request.getParameterValues("address2");
			Address3 =request.getParameterValues("address3");
			CityId =request.getParameterValues("cityCode");
			StateId =request.getParameterValues("stateCode");
			CountyCode =request.getParameterValues("countyCode");
			AddPin =request.getParameterValues("pinNo");
			
			Map<String, String[]> addressMap= new HashMap<String, String[]>();
			addressMap.put("addID", addID);
			addressMap.put("Address1", Address1);
			addressMap.put("Address2", Address2);
			addressMap.put("Address3", Address3);
			addressMap.put("CityId", CityId);
			addressMap.put("StateId", StateId);
			addressMap.put("CountyCode", CountyCode);
			addressMap.put("AddPin", AddPin);
			
		     updateFlag=Integer.parseInt(formBean.getContactUpdateType());
		     //[006] Start
//		   ------Changes Made By Sumit For Demo Order---------------------------------------
				if(formBean.getChkIsDemo()==null || formBean.getChkIsDemo().equalsIgnoreCase("") )
					formBean.setChkIsDemo("N");	
				else
				if(formBean.getChkIsDemo().equalsIgnoreCase("on"))
					formBean.setChkIsDemo("D");	
				
				if(formBean.getNoOfDaysForDemo()==null || formBean.getNoOfDaysForDemo().equalsIgnoreCase(""))
					formBean.setNoOfDaysForDemo("0");	

				//------Changes Made By Sumit For Demo Order---------------------------------------
			//int status=objModel.insertContact(formBean,addressMap,contactMap,Address1.length,updateFlag);
				//[223] Start PROJECT SATYAPAN
				formBean.setIspLicDate(request.getParameter("ispLicDate"));
				//if(Long.valueOf(request.getParameter("channelMasterTagging"))!=0)
				if(!"0".equals(request.getParameter("channelMasterTagging")))
				{
					formBean.setChannelMasterTagging(request.getParameter("channelMasterTagging"));
					formBean.setChannelPartnerId(Long.valueOf(request.getParameter("channelPartnerId")));
					if(request.getParameter("fieldEngineerId")!=null && !request.getParameter("fieldEngineerId").equalsIgnoreCase(""))
						formBean.setFieldEngineerId(Integer.valueOf(request.getParameter("fieldEngineerId")));
					else
						formBean.setFieldEngineerId(0);
					
				}
				else
				{
					formBean.setChannelPartnerId(0);
					formBean.setFieldEngineerId(0);
					formBean.setChannelpartnerCode("0");
				}	

				
				//[223] End PROJECT SATYAPAN
			 int status=objModel.insertContact(formBean,addressMap,contactMap,Address1.length,updateFlag,empID);
			//[006] End
			if(status==1)
			{
				/*CONTACTS TAB STARTS*/
				ArrayList<ContactDTO> listContactDetails= null;
				ArrayList<NewOrderDto> listAddressDetails= new ArrayList<NewOrderDto>();
				ArrayList<OrderHeaderDTO> listRegion= null;
				listContactDetails=objModel.getContactDetails(Long.parseLong(formBean.getPoNumber()));
				listAddressDetails=objModel.getAddressDetails(Long.parseLong(formBean.getPoNumber()));
				request.setAttribute("listContactDetails", listContactDetails);
				request.setAttribute("listAddressDetails", listAddressDetails);
				/*CONTACTS TAB CLOSED*/
				/*MAIN TAB STARTS*/
				//ArrayList<NewOrderDto> projectManagerNameList = new ArrayList<NewOrderDto>();
				ArrayList<OrderHeaderDTO> projectManagerNameList = new ArrayList<OrderHeaderDTO>();
				projectManagerNameList=objModel.getProjectManagerNameList(Long.parseLong(formBean.getAccountID()));
				request.setAttribute("projectManagerNameList", projectManagerNameList);
				ArrayList<NewOrderDto> projectManagerNameListAll = new ArrayList<NewOrderDto>();
				projectManagerNameListAll = objModel.getProjectManagerNameList();
				request.setAttribute("projectManagerNameListAll", projectManagerNameListAll);
				
				listAccountDetails=objModel.getAccountDetails(objDto,Long.parseLong(formBean.getPoNumber()));
				formBean.setProjectManager(String.valueOf(listAccountDetails.get(0).getProjectManagerID()));
				formBean.setProjectManagerAssigned(String.valueOf(listAccountDetails.get(0).getProjectManagerID()));

				//[223] Start PROJECT SATYAPAN
				formBean.setIspTagging(listAccountDetails.get(0).getIspTagging());
				formBean.setIspLicCtgry(listAccountDetails.get(0).getIspLicCtgry());
				formBean.setIspLicDate(listAccountDetails.get(0).getIspLicDate());
				formBean.setIspLicNo(listAccountDetails.get(0).getIspLicNo());
				//[223] End PROJECT SATYAPAN
				/*
				 * set ispmPresent 
				 */
				
				System.out.println("listAccountDetails.get(0).getChannelMasterTagging()"+listAccountDetails.get(0).getChannelMasterTagging());
				formBean.setChannelMasterTagging(listAccountDetails.get(0).getChannelMasterTagging());
				formBean.setChannelPartnerId(listAccountDetails.get(0).getChannelPartnerId());
				formBean.setChannelPartnerName(listAccountDetails.get(0).getChannelPartnerName());
				formBean.setChannelpartnerCode(listAccountDetails.get(0).getChannelpartnerCode());
				formBean.setFieldEngineerId(listAccountDetails.get(0).getFieldEngineerId());
				formBean.setFieldEngineer(listAccountDetails.get(0).getFieldEngineer());
				//pankaj channel partner
				
				request.setAttribute("ispmpresent", String.valueOf(listAccountDetails.get(0).getIsPMPresent()));
				
				request.setAttribute("accountDetailsBean", listAccountDetails);
				listRegion=objModel.getRegionList();
				request.setAttribute("listRegion", listRegion);
				listMainDetailsWithAttributes=objModel.getMainDetails(objDto,Long.parseLong(formBean.getPoNumber()));
				request.setAttribute("MainDetailsWithAttributesBean", listMainDetailsWithAttributes);
				/*MAIN TAB CLOSED*/
				/*MAIN TAB PO DETAILS START*/
				ArrayList<PoDetailsDTO> listPODetails= null;
				listPODetails=objModel.getPODetails(Long.parseLong(formBean.getPoNumber()));
				request.setAttribute("listPoDetails", listPODetails);
				/*MAIN TAB PO DETAILS ENDS*/
				
				request.setAttribute("approvalEditMode",modeName);
				
				//get Approval List after validation 
				//[00123] start
				long orderNo=0;
				orderNo=Long.parseLong(request.getParameter("orderNo"));
				ArrayList lstTo = new ArrayList();
				//ViewOrderFormBean objForm=(ViewOrderFormBean)form;
				//ViewOrderFormBean objForm=new ViewOrderFormBean();
				ViewOrderModel objViewOrderModel = new ViewOrderModel();
				lstTo.add(new LabelValueBean("Account Manager","1"));
				lstTo.add(new LabelValueBean("Project Manager","2"));
				lstTo.add(new LabelValueBean("CPOC","3"));
			
				String strOrderPublishBillingTrigger=null;
				HttpSession session = request.getSession();					
				long roleId=Long.parseLong(objUserDto.getUserRoleId());
				strOrderPublishBillingTrigger=objViewOrderModel.fnIsOrderPublishedBillingTrigger(orderNo , roleId);
				
				String[] strArr=new String[1];
				strArr=strOrderPublishBillingTrigger.split("@@");
				formBean.setIsOrderPublished(strArr[0]);
				formBean.setIsBillingTriggerReady(strArr[1]);
				
				ArrayList aList=objViewOrderModel.getTaskListOfOrder(orderNo);
				String owner = new Utility().getOrderOwnedBy(aList);
				//[022] Start
				if(String.valueOf(listAccountDetails.get(0).getStageName()).equalsIgnoreCase(AppConstants.ORDER_STAGE_PARTIAL_INITIATED)){
					formBean.setOrderOwner(String.valueOf(roleId));
				}
				else{
					formBean.setOrderOwner(owner);
				}
				//[022] End
				ArrayList aListHistory=objViewOrderModel.getTaskListHistory(orderNo);
				request.setAttribute("taskListOfOrder", aList);
				request.setAttribute("taskListHistoryOfOrder", aListHistory);
				request.setAttribute("lstTo", lstTo);
				request.setAttribute("showAttachIcon", "YES");
				String m6Status=request.getParameter("m6Status");
				if("1".equals(m6Status))
				{
					request.setAttribute("m6StatusCode", "1");
				}else if("-1".equals(m6Status))
				{
					request.setAttribute("m6StatusCode", "-1");
				}
				//[00123] END
				int flag=(Integer.parseInt(request.getParameter("flag")));
				request.setAttribute("flag",flag);
				
				messages.add("saveMain",new ActionMessage("MainInstertedFailed"));
				forwardMapping="mainOrderEntryPage";
			}
			else
			{
				messages.add("saveMain",new ActionMessage("MainInstertedSuccess"));
				forwardMapping="mainOrderEntryPage";
			}	
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
			forwardMapping="ErrorPageAction";
		}
		return mapping.findForward(forwardMapping);
	}
	
	//	This Method Displays Contact Types
	public ActionForward fetchContactType(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		NewOrderModel objModel=new NewOrderModel();
		ActionMessages messages = new ActionMessages();
		ArrayList<ContactDTO> listContactTypes = new ArrayList<ContactDTO>();
		try
		{
			String count=request.getParameter("counter");
			listContactTypes = objModel.getContactTypeDetail();
			request.setAttribute("count", count);
			request.setAttribute("listContactTypes", listContactTypes);
			String hdnContactTypeScreen = request.getParameter("hdnContactTypeScreen");
			if (hdnContactTypeScreen!=null && hdnContactTypeScreen.equalsIgnoreCase("REPORT"))
			{
				request.setAttribute("hdnContactTypeScreen", hdnContactTypeScreen);
			}
			else
			{
				request.setAttribute("hdnContactTypeScreen", "");
			}
			forward = mapping.findForward("DisplaySelectContactType");
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		return forward;
	}
	//Fetch All Salutation List
	public ActionForward fetchSalutationList(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		NewOrderModel objModel=new NewOrderModel();
		ActionMessages messages = new ActionMessages();
		ArrayList<ContactDTO> listSalutation = new ArrayList<ContactDTO>();
		try
		{
			String count=request.getParameter("counter");
			listSalutation = objModel.getSalutationList();
			request.setAttribute("count", count);
			request.setAttribute("listSalutation", listSalutation);
			String hdnSalutationListScreen = request.getParameter("hdnSalutationListScreen");
			if (hdnSalutationListScreen!=null && hdnSalutationListScreen.equalsIgnoreCase("REPORT"))
			{
				request.setAttribute("hdnSalutationListScreen", hdnSalutationListScreen);
			}
			else
			{
				request.setAttribute("hdnSalutationListScreen", "");
			}
			forward = mapping.findForward("DisplaySelectSalutation");
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		return forward;
	}
	//Fetch Country List
	public ActionForward fetchCountryList(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		NewOrderModel objModel=new NewOrderModel();
		ActionMessages messages = new ActionMessages();
		ArrayList<NewOrderDto> listCountry = new ArrayList<NewOrderDto>();
		NewOrderDto frmDto=new NewOrderDto();
		try
		{
			String count=request.getParameter("counter");
			String stateCode=request.getParameter("stateCode");
			frmDto.setStateCode(Integer.parseInt(request.getParameter("stateCode")));			
			listCountry = objModel.getCountryList(frmDto);
			request.setAttribute("count", count);
			request.setAttribute("listCountry", listCountry);
			String hdnCountryListScreen = request.getParameter("hdnCountryScreen");
			if (hdnCountryListScreen!=null && hdnCountryListScreen.equalsIgnoreCase("REPORT"))
			{
				request.setAttribute("hdnCountryListScreen", hdnCountryListScreen);
			}
			else
			{
				request.setAttribute("hdnCountryListScreen", "");
			}
			forward = mapping.findForward("DisplaySelectCountry");
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		return forward;
	}
//	Fetch State List
	public ActionForward fetchStateList(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		NewOrderModel objModel=new NewOrderModel();
		ActionMessages messages = new ActionMessages();
		NewOrderBean frmBean=(NewOrderBean)form;
		NewOrderDto frmDto=new NewOrderDto();
		ArrayList<NewOrderDto> listState = new ArrayList<NewOrderDto>();
		try
		{
			String count=request.getParameter("counter");
			String cityCode=request.getParameter("cityCode");
			frmDto.setCityCode(Integer.parseInt(request.getParameter("cityCode")));
			listState = objModel.getStateList(frmDto);
			request.setAttribute("count", count);
			request.setAttribute("cityCode", cityCode);
			request.setAttribute("listState", listState);
			String hdnCountryListScreen = request.getParameter("hdnStateScreen");
			if (hdnCountryListScreen!=null && hdnCountryListScreen.equalsIgnoreCase("REPORT"))
			{
				request.setAttribute("hdnStateListScreen", hdnCountryListScreen);
			}
			else
			{
				request.setAttribute("hdnStateListScreen", "");
			}
			forward = mapping.findForward("DisplaySelectState");
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		return forward;
	}
//	Fetch City List
	public ActionForward fetchCityList(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		NewOrderModel objModel=new NewOrderModel();
		ActionMessages messages = new ActionMessages();
		NewOrderBean formBean=(NewOrderBean)form;
		NewOrderDto frmDto=new NewOrderDto();
		ArrayList<NewOrderDto> listCity = new ArrayList<NewOrderDto>();
		try
		{
			String count=request.getParameter("counter");
			//String stateCode=request.getParameter("stateCode");
			//request.setAttribute("stateCode", stateCode);
			//frmDto.setStateCode(Integer.parseInt(request.getParameter("stateCode")));
			listCity = objModel.getCityList();
			request.setAttribute("count", count);
			request.setAttribute("listCity", listCity);
			String hdnCountryListScreen = request.getParameter("hdnCityScreen");
			if (hdnCountryListScreen!=null && hdnCountryListScreen.equalsIgnoreCase("REPORT"))
			{
				request.setAttribute("hdnCityListScreen", hdnCountryListScreen);
			}
			else
			{
				request.setAttribute("hdnCityListScreen", "");
			}
			forward = mapping.findForward("DisplaySelectCity");
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		return forward;
	}
	// [009] Start
//	Fetch Pin List
	public ActionForward fetchPinList(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		NewOrderModel objModel=new NewOrderModel();
		ActionMessages messages = new ActionMessages();
		NewOrderBean formBean=(NewOrderBean)form;
		NewOrderDto frmDto=new NewOrderDto();
		ArrayList<NewOrderDto> listPin = new ArrayList<NewOrderDto>();
		try
		{
			String count=request.getParameter("counter");
			String cityCode=request.getParameter("cityCode");
			Utility.SysOut("cityCode : "+ cityCode);
			request.setAttribute("cityCode", cityCode);
			frmDto.setCityCode(Integer.parseInt(request.getParameter("cityCode")));
			String stateCode=request.getParameter("stateCode");
			Utility.SysOut("stateCode : "+ stateCode);
			request.setAttribute("stateCode", stateCode);
			frmDto.setStateCode(Integer.parseInt(request.getParameter("stateCode")));
			listPin = objModel.getPinList(frmDto);
			request.setAttribute("count", count);
			//request.setAttribute("listPin", listPin);
			String hdnCountryListScreen = request.getParameter("hdnPincodeScreen");
			if (hdnCountryListScreen!=null && hdnCountryListScreen.equalsIgnoreCase("REPORT"))
			{
				request.setAttribute("hdnPincodeListScreen", hdnCountryListScreen);
			}
			else
			{
				request.setAttribute("hdnPincodeListScreen", "");
			}
			forward = mapping.findForward("DisplaySelectPin");
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		return forward;
	}
	//[009] End
	
	//displayProductCatelog
	public ActionForward displayProductCatelog(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		NewOrderModel objModel=new NewOrderModel();
		ActionMessages messages = new ActionMessages();
		ArrayList<ProductCatelogDTO> serviceTypeList = null;
		ServiceLineDTO objDto=new ServiceLineDTO();
		try
		{
			objDto.setServiceTypeId(Integer.parseInt(request.getParameter("serviceTypeID")));
			objDto.setOrderNumber(0);
			serviceTypeList = objModel.getServiceType(objDto);
			request.setAttribute("serviceTypeList", serviceTypeList);
			//TRNG22032013026 added by manisha start
			request.setAttribute("serviceID", request.getParameter("serviceID"));
			//TRNG22032013026 added by manisha end
			request.setAttribute("LogicalSI", request.getParameter("LogicalSI"));
			forward = mapping.findForward("forwardProductCatelog");
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		return forward;
	}
	
	private ArrayList<NewOrderDto> dropdownlist(ArrayList<NewOrderDto> serviceList) throws Exception
	{
		ArrayList<NewOrderDto> retServiceList = new ArrayList<NewOrderDto>();
		try
		{
			retServiceList = null;
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
		}
		return retServiceList;
	}
	
	
	
	
	/**
	 * Method to insert/update the PO Details Tab data.
	 * @param mapping Action Mapping obj extracted from Struts- config file
	 * @param form form object
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return forward depending on success or failure
	 * @throws Exception in case of exception
	 */
	public ActionForward insertUpdatePODetails(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception
	{
		String forwardMapping = null;
		NewOrderBean formBean=(NewOrderBean)form;
		NewOrderModel objModel=new NewOrderModel();
		ActionMessages messages = new ActionMessages();
		formBean.setOrderNo(formBean.getPoNumber());
		NewOrderDto objDto=new NewOrderDto();
		Entity entityData = new Entity();
		try
		{
//			setting the new Page
			formBean.setNewFormOpened(request.getParameter("newFormOpened"));
			//[006] Start
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			long empID=Long.parseLong(objUserDto.getEmployeeId());
			// [006] End
			
			int count=request.getParameterValues("contactId").length;
			String modeName=request.getParameter("modeName");
			String[] contactId=new String [count];
			String[] custPoDetailNo=new String [count];//Added by Saurabh for Customer Po Detail No
			String[] custPoDate=new String [count];//Added by Saurabh for Customer Po date 
			String[] poDetailNo=new String [count];
			String[] poDate=new String [count];
			String[] poReceiveDate=new String [count];
			String[] totalPOAmt=new String [count];
			String[] periodsInMonths=new String [count];
			String[] contractStartDate=new String [count];
			String[] contractEndDate=new String [count];
			String[] entity=new String [count];
			String[] orderNo=new String [count];
			
			String[] poDefault=new String [count];
			String[] poIssuedBy=new String [count];
			String[] poRemarks=new String [count];
			String[] poEmailId=new String [count];
			String[] demoPeriod=new String [count];
			//[225]
			String[] ldClause=new String [count];
			int updateFlag;
			int status;
			if(request.getParameterValues("contactId")!=null)
			{
				contactId = request.getParameterValues("contactId");
				custPoDetailNo = request.getParameterValues("custPoDetailNo");//Added By Saurabh
				custPoDate=request.getParameterValues("custPoDate");// Added By Saurabh
				poDetailNo = request.getParameterValues("poDetailNo");
				poDate = request.getParameterValues("poDate");
				poReceiveDate = request.getParameterValues("poReceiveDate");
				totalPOAmt =request.getParameterValues("totalPoAmt");
				periodsInMonths =request.getParameterValues("periodsInMonths");
				contractStartDate =request.getParameterValues("contractStartDate");
				contractEndDate =request.getParameterValues("contractEndDate");
				entity =request.getParameterValues("entityId");
				//poDefault=request.getParameterValues("defaultPO");
				StringTokenizer st = new StringTokenizer( formBean.getSelectedPODetails(), ",");
				for (int i =0; st.hasMoreTokens();i++) {
					poDefault[i]=st.nextToken();
				}
				
				poIssuedBy=request.getParameterValues("poIssueBy");
				poRemarks=request.getParameterValues("poRemarks");
				poEmailId=request.getParameterValues("poEmailId");
				demoPeriod=request.getParameterValues("poDemoContractPeriod");
				//[225]
				ldClause=request.getParameterValues("ldClause");
				
				for(int iCount = 0 ; iCount<contractEndDate.length;iCount++)
					orderNo[iCount]=formBean.getOrderNo();
				
				Map<String, String[]> poMap= new HashMap<String, String[]>();
				poMap.put("contactId", contactId);
				poMap.put("custPoDetailNo", custPoDetailNo);//Added By Saurabh
				poMap.put("custPoDate", custPoDate);//Added By Saurabh
				poMap.put("poDetailNo", poDetailNo);
				poMap.put("poDate", poDate);
				poMap.put("poReceiveDate", poReceiveDate);
				poMap.put("totalPOAmt", totalPOAmt);
				poMap.put("periodsInMonths", periodsInMonths);
				poMap.put("contractStartDate", contractStartDate);
				poMap.put("contractEndDate", contractEndDate);
				poMap.put("entity", entity);
				poMap.put("orderNo", orderNo);
				
				
				poMap.put("poDefault", poDefault);
				poMap.put("poIssuedBy", poIssuedBy);
				poMap.put("poRemarks", poRemarks);
				poMap.put("poEmailId", poEmailId);
				poMap.put("demoPeriod", demoPeriod);
				//[225]
				poMap.put("ldClause", ldClause);
				updateFlag=Integer.parseInt(formBean.getPoUpdateType());
				//[006] Start
				//status=objModel.insertPO(formBean,poMap,count,updateFlag);
//				------Changes Made By Sumit For Demo Order---------------------------------------
				if(formBean.getChkIsDemo()==null || formBean.getChkIsDemo().equalsIgnoreCase("") )
					formBean.setChkIsDemo("N");	
				else
				if(formBean.getChkIsDemo().equalsIgnoreCase("on"))
					formBean.setChkIsDemo("D");	
				
				if(formBean.getNoOfDaysForDemo()==null || formBean.getNoOfDaysForDemo().equalsIgnoreCase(""))
					formBean.setNoOfDaysForDemo("0");	

				//------Changes Made By Sumit For Demo Order---------------------------------------
				
				//[223] Start PROJECT SATYAPAN
				formBean.setIspLicDate(request.getParameter("ispLicDate"));

			
				if(!"0".equals(request.getParameter("channelMasterTagging")))	
				{
					formBean.setChannelMasterTagging(request.getParameter("channelMasterTagging"));
					formBean.setChannelPartnerId(Long.valueOf(request.getParameter("channelPartnerId")));
					if(request.getParameter("fieldEngineerId")!=null && !request.getParameter("fieldEngineerId").equalsIgnoreCase(""))
						formBean.setFieldEngineerId(Integer.valueOf(request.getParameter("fieldEngineerId")));
					else
						formBean.setFieldEngineerId(0);
					
				}
				else
				{
					formBean.setChannelPartnerId(0);
					formBean.setFieldEngineerId(0);
					formBean.setChannelpartnerCode("0");
				}	
				
				//[223] End PROJECT SATYAPAN
				
				status=objModel.insertPO(formBean,poMap,count,updateFlag,empID);
				//[006] End
			}
			else
			{
				status=0;
			}
			if(status==1 || status==0)
			{
				ArrayList<PoDetailsDTO> listPODetails= null;
				ArrayList<OrderHeaderDTO> listRegion= null;
				request.setAttribute("entityList", objModel.getEntityList(entityData));
				listPODetails=objModel.getPODetails(Long.parseLong(formBean.getOrderNo()));
				request.setAttribute("listPoDetails", listPODetails);
				/*CONTACTS TAB STARTS*/
				ArrayList<ContactDTO> listContactDetails= null;
				ArrayList<NewOrderDto> listAddressDetails= new ArrayList<NewOrderDto>();
				listContactDetails=objModel.getContactDetails(Long.parseLong(formBean.getPoNumber()));
				listAddressDetails=objModel.getAddressDetails(Long.parseLong(formBean.getPoNumber()));
				request.setAttribute("listContactDetails", listContactDetails);
				request.setAttribute("listAddressDetails", listAddressDetails);
				/*CONTACTS TAB CLOSED*/
				/*MAIN TAB STARTS*/
				ArrayList<OrderHeaderDTO> projectManagerNameList = new ArrayList<OrderHeaderDTO>();
				projectManagerNameList=objModel.getProjectManagerNameList(Long.parseLong(formBean.getAccountID()));
				request.setAttribute("projectManagerNameList", projectManagerNameList);

				ArrayList<NewOrderDto> projectManagerNameListAll = new ArrayList<NewOrderDto>();
				projectManagerNameListAll = objModel.getProjectManagerNameList();
				request.setAttribute("projectManagerNameListAll", projectManagerNameListAll);
				
				
				ArrayList<OrderHeaderDTO> listAccountDetails =null;
				ArrayList<FieldAttibuteDTO> listMainDetailsWithAttributes = null;
				listAccountDetails=objModel.getAccountDetails(objDto,Long.parseLong(formBean.getPoNumber()));
				formBean.setProjectManager(String.valueOf(listAccountDetails.get(0).getProjectManagerID()));
				formBean.setProjectManagerAssigned(String.valueOf(listAccountDetails.get(0).getProjectManagerID()));
				
				//[223] Start PROJECT SATYAPAN
				formBean.setIspTagging(listAccountDetails.get(0).getIspTagging());
				formBean.setIspLicCtgry(listAccountDetails.get(0).getIspLicCtgry());
				formBean.setIspLicDate(listAccountDetails.get(0).getIspLicDate());
				formBean.setIspLicNo(listAccountDetails.get(0).getIspLicNo());
				//[223] End PROJECT SATYAPAN
				/*
				 * set ispmPresent 
				 */
				
				formBean.setChannelMasterTagging(listAccountDetails.get(0).getChannelMasterTagging());
				formBean.setChannelPartnerId(listAccountDetails.get(0).getChannelPartnerId());
				formBean.setChannelPartnerName(listAccountDetails.get(0).getChannelPartnerName());
				formBean.setChannelpartnerCode(listAccountDetails.get(0).getChannelpartnerCode());
				formBean.setFieldEngineerId(listAccountDetails.get(0).getFieldEngineerId());
				formBean.setFieldEngineer(listAccountDetails.get(0).getFieldEngineer());
				//pankaj channel partner
				
				request.setAttribute("ispmpresent", String.valueOf(listAccountDetails.get(0).getIsPMPresent()));
				
				request.setAttribute("accountDetailsBean", listAccountDetails);
				listRegion=objModel.getRegionList();
				request.setAttribute("listRegion", listRegion);
				listMainDetailsWithAttributes=objModel.getMainDetails(objDto,Long.parseLong(formBean.getPoNumber()));
				request.setAttribute("MainDetailsWithAttributesBean", listMainDetailsWithAttributes);
				
				
				request.setAttribute("approvalEditMode",modeName);
				
//				get Approval List after validation 
				//[00123] start
				long orderNumber=0;
				orderNumber=Long.parseLong(request.getParameter("orderNo"));
				ArrayList lstTo = new ArrayList();
				//ViewOrderFormBean objForm=(ViewOrderFormBean)form;
				//ViewOrderFormBean objForm=new ViewOrderFormBean();
				ViewOrderModel objViewOrderModel = new ViewOrderModel();
				lstTo.add(new LabelValueBean("Account Manager","1"));
				lstTo.add(new LabelValueBean("Project Manager","2"));
				lstTo.add(new LabelValueBean("CPOC","3"));
			
				String strOrderPublishBillingTrigger=null;
				HttpSession session = request.getSession();					
				long roleId=Long.parseLong(objUserDto.getUserRoleId());
				strOrderPublishBillingTrigger=objViewOrderModel.fnIsOrderPublishedBillingTrigger(orderNumber, roleId);
				
				String[] strArr=new String[1];
				strArr=strOrderPublishBillingTrigger.split("@@");
				formBean.setIsOrderPublished(strArr[0]);
				formBean.setIsBillingTriggerReady(strArr[1]);
				//formBean.setIsOrderWorkflowRelaunch(strArr[2]);
				
				ArrayList aList=objViewOrderModel.getTaskListOfOrder(orderNumber);
				String owner = new Utility().getOrderOwnedBy(aList);
				//[022] Start
				if(String.valueOf(listAccountDetails.get(0).getStageName()).equalsIgnoreCase(AppConstants.ORDER_STAGE_PARTIAL_INITIATED)){
					formBean.setOrderOwner(String.valueOf(roleId));
				}
				else{
					formBean.setOrderOwner(owner);
				}
				//[022] End
				ArrayList aListHistory=objViewOrderModel.getTaskListHistory(orderNumber);
				request.setAttribute("taskListOfOrder", aList);
				request.setAttribute("taskListHistoryOfOrder", aListHistory);
				request.setAttribute("lstTo", lstTo);
				request.setAttribute("showAttachIcon", "YES");
				String m6Status=request.getParameter("m6Status");
				if("1".equals(m6Status))
				{
					request.setAttribute("m6StatusCode", "1");
				}else if("-1".equals(m6Status))
				{
					request.setAttribute("m6StatusCode", "-1");
				}
				int flag=(Integer.parseInt(request.getParameter("flag")));
				request.setAttribute("flag",flag);
				//[00123] END
				
				/*MAIN TAB CLOSED*/		
				messages.add("saveMain",new ActionMessage("MainInstertedSuccess"));
				forwardMapping="mainOrderEntryPage";
			}
			else
			{
				/*CONTACTS TAB STARTS*/
				ArrayList<ContactDTO> listContactDetails= null;
				ArrayList<NewOrderDto> listAddressDetails= new ArrayList<NewOrderDto>();
				ArrayList<OrderHeaderDTO> listRegion= null;
				listContactDetails=objModel.getContactDetails(Long.parseLong(formBean.getPoNumber()));
				listAddressDetails=objModel.getAddressDetails(Long.parseLong(formBean.getPoNumber()));
				request.setAttribute("listContactDetails", listContactDetails);
				request.setAttribute("listAddressDetails", listAddressDetails);
				/*CONTACTS TAB CLOSED*/
				/*MAIN TAB STARTS*/
				ArrayList<OrderHeaderDTO> projectManagerNameList = new ArrayList<OrderHeaderDTO>();
				projectManagerNameList=objModel.getProjectManagerNameList(Long.parseLong(formBean.getAccountID()));
				request.setAttribute("projectManagerNameList", projectManagerNameList);
				ArrayList<NewOrderDto> projectManagerNameListAll = new ArrayList<NewOrderDto>();
				projectManagerNameListAll = objModel.getProjectManagerNameList();
				request.setAttribute("projectManagerNameListAll", projectManagerNameListAll);

				ArrayList<OrderHeaderDTO> listAccountDetails = null;
				ArrayList<FieldAttibuteDTO> listMainDetailsWithAttributes =null;
				listAccountDetails=objModel.getAccountDetails(objDto,Long.parseLong(formBean.getPoNumber()));
				formBean.setProjectManager(String.valueOf(listAccountDetails.get(0).getProjectManagerID()));
				formBean.setProjectManagerAssigned(String.valueOf(listAccountDetails.get(0).getProjectManagerID()));

				request.setAttribute("accountDetailsBean", listAccountDetails);
				listRegion=objModel.getRegionList();
				request.setAttribute("listRegion", listRegion);
				listMainDetailsWithAttributes=objModel.getMainDetails(objDto,Long.parseLong(formBean.getPoNumber()));
				request.setAttribute("MainDetailsWithAttributesBean", listMainDetailsWithAttributes);
				/*MAIN TAB CLOSED*/	
				request.setAttribute("entityList", objModel.getEntityList(entityData));
				messages.add("saveMain",new ActionMessage("MainInstertedFailed"));
				forwardMapping="mainOrderEntryPage";
			}	
					
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
			forwardMapping="ErrorPageAction";
		}
		return mapping.findForward(forwardMapping);
	}
	
	public ActionForward deleteUpdatePODetails(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception
	{
		String forwardMapping = null;
		NewOrderBean formBean=(NewOrderBean)form;
		NewOrderModel objModel=new NewOrderModel();
		ActionMessages messages = new ActionMessages();
		formBean.setOrderNo(formBean.getPoNumber());
		Entity entityData = new Entity();
		NewOrderDto objDto=new NewOrderDto();
		try
		{
			
			ArrayList<PoDetailsDTO> listPODetails= null;
			request.setAttribute("entityList", objModel.getEntityList(entityData));
			listPODetails=objModel.getPODetails(Long.parseLong(formBean.getOrderNo()));
			request.setAttribute("listPoDetails", listPODetails);
			/*CONTACTS TAB STARTS*/
			ArrayList<ContactDTO> listContactDetails= null;
			ArrayList<NewOrderDto> listAddressDetails= new ArrayList<NewOrderDto>();
			listContactDetails=objModel.getContactDetails(Long.parseLong(formBean.getPoNumber()));
			listAddressDetails=objModel.getAddressDetails(Long.parseLong(formBean.getPoNumber()));
			request.setAttribute("listContactDetails", listContactDetails);
			request.setAttribute("listAddressDetails", listAddressDetails);
			/*CONTACTS TAB CLOSED*/
			/*MAIN TAB STARTS*/
			ArrayList<OrderHeaderDTO> projectManagerNameList = new ArrayList<OrderHeaderDTO>();
			projectManagerNameList=objModel.getProjectManagerNameList(Long.parseLong(formBean.getAccountID()));
			request.setAttribute("projectManagerNameList", projectManagerNameList);
			ArrayList<NewOrderDto> projectManagerNameListAll = new ArrayList<NewOrderDto>();
			projectManagerNameListAll = objModel.getProjectManagerNameList();
			request.setAttribute("projectManagerNameListAll", projectManagerNameListAll);

			ArrayList<OrderHeaderDTO> listAccountDetails = null;
			ArrayList<FieldAttibuteDTO> listMainDetailsWithAttributes = null;
			listAccountDetails=objModel.getAccountDetails(objDto,Long.parseLong(formBean.getPoNumber()));
			formBean.setProjectManager(String.valueOf(listAccountDetails.get(0).getProjectManagerID()));
			formBean.setProjectManagerAssigned(String.valueOf(listAccountDetails.get(0).getProjectManagerID()));

			request.setAttribute("accountDetailsBean", listAccountDetails);
			ArrayList<OrderHeaderDTO> listRegion= null;
			listRegion=objModel.getRegionList();
			request.setAttribute("listRegion", listRegion);
			listMainDetailsWithAttributes=objModel.getMainDetails(objDto,Long.parseLong(formBean.getPoNumber()));
			request.setAttribute("MainDetailsWithAttributesBean", listMainDetailsWithAttributes);
			/*MAIN TAB CLOSED*/		
			messages.add("saveMain",new ActionMessage("MainInstertedSuccess"));
			forwardMapping="mainOrderEntryPage";
					
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
			forwardMapping="ErrorPageAction";
		}
		return mapping.findForward(forwardMapping);
	}
	
	/**
	 * Method to return a list of available entities retreived from DB
	 * @return
	 */
	public ActionForward getEntityList(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		NewOrderModel objModel=new NewOrderModel();
		ActionForward forward = new ActionForward();
		Entity entityData = new Entity();
		String count=request.getParameter("counter");
		ArrayList<Entity> list  = objModel.getEntityList(entityData);
		request.setAttribute("count", count);
		request.setAttribute("EntityList",list );
		forward = mapping.findForward("DisplayEntity");
		
		return forward;
	}

	
//	TProductAttDetail
	public ActionForward getTProductAttDetail(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		NewOrderModel objModel=new NewOrderModel();
		ActionMessages messages = new ActionMessages();
		ArrayList<ProductCatelogDTO> productAttDetailList = null;
		try
		{
			long serviceNo=Long.parseLong(request.getParameter("hdnserviceid"));
			long serviceTypeId=Long.parseLong(request.getParameter("hdnserviceTypeId"));
			productAttDetailList = objModel.getTProductAttDetail(serviceNo,serviceTypeId);
			request.setAttribute("serviceTypeId", serviceTypeId); 
			request.setAttribute("productAttDetailList", productAttDetailList); 
			forward = mapping.findForward("serviceAttributeListPage");
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		return forward;
	}
	public ActionForward redirectToHome(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		try
		{
			forward = mapping.findForward("DisplayHomePage");
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
		}
		return forward;
	}

	public ActionForward getToProductAttDetail(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		NewOrderModel objModel=new NewOrderModel();
		ActionMessages messages = new ActionMessages();
		ArrayList<ProductCatelogDTO> productAttDetailList = null;
		try
		{
			long serviceNo=Long.parseLong(request.getParameter("hdnserviceid"));
			long serviceTypeId=Long.parseLong(request.getParameter("hdnserviceTypeId"));
			productAttDetailList = objModel.getTProductAttDetail(serviceNo,serviceTypeId);
			request.setAttribute("productAttDetailList", productAttDetailList);
			forward = mapping.findForward("goToServiceAttributeListPage");
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		return forward;
	}
	
	public ActionForward getServiceTreeview(ActionMapping mapping, ActionForm form
			,HttpServletRequest request, HttpServletResponse response) throws Exception{
		ActionForward forward = new ActionForward(); // return value
		NewOrderModel objModel=new NewOrderModel();
		ActionMessages messages = new ActionMessages();
		ArrayList<NewOrderDto> productAttDetailList = new ArrayList<NewOrderDto>();
		ArrayList<OrderHeaderDTO> listRegion= null;
		NewOrderDto objDto = new NewOrderDto();
		ArrayList<OrderHeaderDTO> listAccountDetails = null;
		ArrayList<FieldAttibuteDTO> listMainDetailsWithAttributes = null;
		NewOrderBean formBean = (NewOrderBean)form;
		long orderNo=Long.valueOf(formBean.getPoNumber());
		formBean.setOrderNo(formBean.getPoNumber());
		String modeName=request.getParameter("modeName");
		String checkedServiceNumber=request.getParameter("checkedServiceNumber");
		try
		{
			listAccountDetails=objModel.getAccountDetails(objDto,orderNo);
			request.setAttribute("accountDetailsBean", listAccountDetails);

			request.setAttribute("ispmpresent", String.valueOf(listAccountDetails.get(0).getIsPMPresent()));
			request.setAttribute("order_creation_source", listAccountDetails.get(0).getOrder_creation_source());
			formBean.setHdnOrderCreationSource(listAccountDetails.get(0).getOrder_creation_source());
			listRegion=objModel.getRegionList();
			request.setAttribute("listRegion", listRegion);

			ArrayList<OrderHeaderDTO> projectManagerNameList = new ArrayList<OrderHeaderDTO>();
			projectManagerNameList=objModel.getProjectManagerNameList(listAccountDetails.get(0).getAccountID());
			request.setAttribute("projectManagerNameList", projectManagerNameList);
			ArrayList<NewOrderDto> projectManagerNameListAll = new ArrayList<NewOrderDto>();
			projectManagerNameListAll = objModel.getProjectManagerNameList();
			request.setAttribute("projectManagerNameListAll", projectManagerNameListAll);

			formBean.setProjectManager(String.valueOf(listAccountDetails.get(0).getProjectManagerID()));
			formBean.setProjectManagerAssigned(String.valueOf(listAccountDetails.get(0).getProjectManagerID()));

			listMainDetailsWithAttributes=objModel.getMainDetails(objDto,orderNo);
			request.setAttribute("MainDetailsWithAttributesBean", listMainDetailsWithAttributes);
			ArrayList<ContactDTO> listContactDetails= null;
			ArrayList<NewOrderDto> listAddressDetails= new ArrayList<NewOrderDto>();
			listContactDetails=objModel.getContactDetails(Long.parseLong(formBean.getPoNumber()));
			listAddressDetails=objModel.getAddressDetails(Long.parseLong(formBean.getPoNumber()));
			request.setAttribute("listContactDetails", listContactDetails);
			request.setAttribute("listAddressDetails", listAddressDetails);
			/*MAIN TAB PO DETAILS START*/
			ArrayList<PoDetailsDTO> listPODetails=null;
			listPODetails=objModel.getPODetails(Long.parseLong(formBean.getPoNumber()));
			request.setAttribute("listPoDetails", listPODetails);
			request.setAttribute("approvalEditMode",modeName);
			/*MAIN TAB PO DETAILS ENDS*/

			//get Approval List after validation 
			//[250511AKS] start
			long orderNumber=0;
			orderNumber=Long.parseLong(request.getParameter("orderNo"));
			ArrayList lstTo = new ArrayList();
			//ViewOrderFormBean objForm=(ViewOrderFormBean)form;
			//ViewOrderFormBean objForm=new ViewOrderFormBean();
			ViewOrderModel objViewOrderModel = new ViewOrderModel();
			lstTo.add(new LabelValueBean("Account Manager","1"));
			lstTo.add(new LabelValueBean("Project Manager","2"));
			lstTo.add(new LabelValueBean("CPOC","3"));
			
			
			String strOrderPublishBillingTrigger=null;
			HttpSession session = request.getSession();	
			UserInfoDto objUserDto  = (UserInfoDto)session.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			long roleId=Long.parseLong(objUserDto.getUserRoleId());			
			strOrderPublishBillingTrigger=objViewOrderModel.fnIsOrderPublishedBillingTrigger(orderNumber, roleId);
			
			String[] strArr=new String[1];
			strArr=strOrderPublishBillingTrigger.split("@@");
			formBean.setIsOrderPublished(strArr[0]);
			formBean.setIsBillingTriggerReady(strArr[1]);
			//formBean.setIsOrderWorkflowRelaunch(strArr[2]);
			
			ArrayList aList=objViewOrderModel.getTaskListOfOrder(orderNumber);
			String owner = new Utility().getOrderOwnedBy(aList);
			formBean.setOrderOwner(owner);
			ArrayList aListHistory=objViewOrderModel.getTaskListHistory(orderNumber);
			request.setAttribute("taskListOfOrder", aList);
			request.setAttribute("taskListHistoryOfOrder", aListHistory);
			request.setAttribute("lstTo", lstTo);
			request.setAttribute("showAttachIcon", "YES");
			String m6Status=request.getParameter("m6Status");
			if("1".equals(m6Status))
			{
				request.setAttribute("m6StatusCode", "1");
			}else if("-1".equals(m6Status))
			{
				request.setAttribute("m6StatusCode", "-1");
			}
			//[250511AKS] END
			
			
			request.setAttribute("checkedServiceNumber",checkedServiceNumber);
			int flag=(Integer.parseInt(request.getParameter("flag")));
			request.setAttribute("flag",flag);
			forward=mapping.findForward("NewOrderUpdateDisplay");
			
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		return forward;
	}
	
	public ActionForward productCatelogforUpdate(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		NewOrderModel objModel=new NewOrderModel();
		ActionMessages messages = new ActionMessages();
		ArrayList<ProductCatelogDTO> serviceTypeList =null;
		ServiceLineDTO objDto=new ServiceLineDTO();
		//[221] start
		String serviceID=request.getParameter("ServiceNO");
		int roleId=Integer.parseInt(request.getParameter("roleId"));
		objModel.CheckServicePresent(serviceID,  roleId);
		boolean servicePrsent=objModel.CheckServicePresent(serviceID,  roleId);
		if(!servicePrsent)
			request.setAttribute("isView",1);
		//[221] end
		try
		{
			Utility.SysOut(request.getParameter("ServiceTypeID"));
			objDto.setServiceTypeId(Integer.parseInt(request.getParameter("ServiceTypeID")));//FOR WHETHER PRODUCT IS ASAT OR ISP
			objDto.setProductName(request.getParameter("ProductName"));
			objDto.setServiceName(request.getParameter("ServiceName"));
			objDto.setCheckedServiceNumber(request.getParameter("checkedServiceNumber"));
			objDto.setOrderNumber(0);
			serviceTypeList = objModel.getServiceType(objDto);
			request.setAttribute("serviceTypeList", serviceTypeList);
			request.setAttribute("LogicalSI", request.getParameter("LogicalSI"));
			forward = mapping.findForward("ProductCatelogForUpdate");
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		return forward;
	}
//	Main method for viewing the incomplete Order List.
	 public ActionForward incompleteOrder(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response){
			//logger.info("View Order Interface and viewOrder method have been called");
			String forwardMapping = null;
			//ActionForward forward = new ActionForward(); // return value
			NewOrderModel objModel=new NewOrderModel();
			//ActionMessages messages = new ActionMessages();
			//ArrayList<NewOrderDto> productAttDetailList = new ArrayList<NewOrderDto>();
			NewOrderDto objDto = new NewOrderDto();
			ArrayList<OrderHeaderDTO> listAccountDetails =null;
			ArrayList<FieldAttibuteDTO> listMainDetailsWithAttributes =null;
			//ArrayList<NewOrderDto> listRegion= new ArrayList<NewOrderDto>();
			NewOrderBean formBean=(NewOrderBean)form;
			long orderNo=0;
			String modeName=null;
			NewOrderDao objDao = new NewOrderDao();
			int copcCount = 0; //added by Megha
			String billingTriggerEnableDisable=null;
			try {
				HttpSession session = request.getSession();
				UserInfoDto objUserDto = (UserInfoDto) session.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				long roleId = Long.parseLong(objUserDto.getUserRoleId());
				
				orderNo=Long.parseLong(request.getParameter("orderNo"));
				modeName=request.getParameter("modeName");
			    listAccountDetails=objModel.getAccountDetails(objDto,orderNo);
				request.setAttribute("accountDetailsBean", listAccountDetails);
				OrderHeaderDTO objNewOrderDto = (OrderHeaderDTO)listAccountDetails.get(0);
				formBean.setChangeTypeID(objNewOrderDto.getChangeTypeId());
				formBean.setStageName(String.valueOf(listAccountDetails.get(0).getStageName()));
				request.setAttribute("subChangeTypeId", objNewOrderDto.getSubChangeTypeId());
				
				//[223] Start PROJECT SATYAPAN
				formBean.setIspTagging(listAccountDetails.get(0).getIspTagging());
				formBean.setIspLicCtgry(listAccountDetails.get(0).getIspLicCtgry());
				formBean.setIspLicDate(listAccountDetails.get(0).getIspLicDate());
				formBean.setIspLicNo(listAccountDetails.get(0).getIspLicNo());
				//[223] End PROJECT SATYAPAN
				
				formBean.setChannelMasterTagging(listAccountDetails.get(0).getChannelMasterTagging());
				formBean.setChannelPartnerId(listAccountDetails.get(0).getChannelPartnerId());
				formBean.setChannelPartnerName(listAccountDetails.get(0).getChannelPartnerName());
				formBean.setChannelpartnerCode(listAccountDetails.get(0).getChannelpartnerCode());
				formBean.setFieldEngineerId(listAccountDetails.get(0).getFieldEngineerId());
				formBean.setFieldEngineer(listAccountDetails.get(0).getFieldEngineer());
				
				//Changes Made By Anil For PM to be Displayed only in Case of PM Present in Workflow ,for CLEP Order this will by pass:: 15-Jan-2013 :: -->
				request.setAttribute("ispmpresent", String.valueOf(listAccountDetails.get(0).getIsPMPresent()));
				request.setAttribute("order_creation_source", listAccountDetails.get(0).getOrder_creation_source());
				//Changes Made By Anil For PM to be Displayed only in Case of PM Present in Workflow, for CLEP Order this will by pass :: 15-Jan-2013 :: -->
				
				//listRegion=objModel.getRegionList();
				//request.setAttribute("listRegion", listRegion);
				
				//ArrayList<NewOrderDto> projectManagerNameList = new ArrayList<NewOrderDto>();
				//projectManagerNameList=objModel.getProjectManagerNameList(listAccountDetails.get(0).getAccountID());
				//request.setAttribute("projectManagerNameList", projectManagerNameList);
				//Meenakshi Start: Tunning for order load
				if (("AM".equalsIgnoreCase(listAccountDetails.get(0).getStageName())) || (roleId==1 && AppConstants.ORDER_STAGE_PARTIAL_INITIATED.equalsIgnoreCase(listAccountDetails.get(0).getStageName()))) {
					ArrayList<NewOrderDto> projectManagerNameListAll = new ArrayList<NewOrderDto>();
					projectManagerNameListAll = objModel.getProjectManagerNameList();
					request.setAttribute("projectManagerNameListAll", projectManagerNameListAll);
				}

				formBean.setCurrencyID(String.valueOf(listAccountDetails.get(0).getCurrencyID()));
				formBean.setProjectManager(String.valueOf(listAccountDetails.get(0).getProjectManagerID()));
				formBean.setProjectManagerID(String.valueOf(listAccountDetails.get(0).getProjectManagerID()));
				formBean.setProjectManagerAssigned(String.valueOf(listAccountDetails.get(0).getProjectManagerID()));
				listMainDetailsWithAttributes=objModel.getMainDetails(objDto,orderNo);
				request.setAttribute("MainDetailsWithAttributesBean", listMainDetailsWithAttributes);
				
				if(listAccountDetails.get(0).getContactCount() == 1){
					ArrayList<ContactDTO> listContactDetails= null;
					ArrayList<NewOrderDto> listAddressDetails= new ArrayList<NewOrderDto>();
					listContactDetails=objModel.getContactDetails(orderNo);
					listAddressDetails=objModel.getAddressDetails(orderNo);
					request.setAttribute("listContactDetails", listContactDetails);
					request.setAttribute("listAddressDetails", listAddressDetails);
				}
				formBean.setRegionId(String.valueOf((listAccountDetails.get(0).getRegionId())));
				formBean.setZoneId(String.valueOf((listAccountDetails.get(0).getZoneId())));
				/*MAIN TAB PO DETAILS START*/
				
				if (listAccountDetails.get(0).getPoCount() == 1) {
					ArrayList<PoDetailsDTO> listPODetails= null;
					listPODetails=objModel.getPODetails(orderNo);
					request.setAttribute("listPoDetails", listPODetails);
				}
				request.setAttribute("approvalEditMode",modeName);
				/*MAIN TAB PO DETAILS ENDS*/
				/*APPROVAL TAB START HERE*/
				ArrayList lstTo = new ArrayList();
				//ViewOrderFormBean objForm=(ViewOrderFormBean)form;
				//ViewOrderFormBean objForm=new ViewOrderFormBean();
				ViewOrderModel objViewOrderModel = new ViewOrderModel();
				lstTo.add(new LabelValueBean("Account Manager","1"));
				lstTo.add(new LabelValueBean("Project Manager","2"));
				lstTo.add(new LabelValueBean("CPOC","3"));
//				[008] Start
				String strOrderPublishBillingTrigger=null;
				//strOrderPublishBillingTrigger=objViewOrderModel.fnIsOrderPublishedBillingTrigger(orderNo , roleId);
				
//				Meenakshi Start: Tunning for order load
				if(!("NEW".equalsIgnoreCase(listAccountDetails.get(0).getStageName()))){
					strOrderPublishBillingTrigger=objViewOrderModel.fnIsOrderPublishedBillingTrigger(orderNo , roleId);			
					String[] strArr=new String[3];
					strArr=strOrderPublishBillingTrigger.split("@@");
					for(int i=0; i<strArr.length;i++){
						if(i == 0)
							formBean.setIsOrderPublished(strArr[0]);
						else if(i == 1){
							billingTriggerEnableDisable= strArr[1];
							if(AppConstants.ORDER_STAGE_PARTIAL_INITIATED.equalsIgnoreCase(listAccountDetails.get(0).getStageName())){
								String btRoleId = new NewOrderDaoExt().getRoleID(AppConstants.BT_SHORT_CODE);
								if(null != btRoleId && !"".equals(btRoleId.trim()) && roleId == Long.valueOf(btRoleId)){
									formBean.setIsBillingTriggerReady(strArr[1]);
								}
							}else{
								formBean.setIsBillingTriggerReady(strArr[1]);
							}
								
						}else if(i == 2)
							formBean.setIsOrderWorkflowRelaunch(strArr[2]);
					}
				}
//				[008]	End
//				Meenakshi Start: Tunning for order load
				//Puneet
					ArrayList<ViewOrderDto> aList=objViewOrderModel.getTaskListOfOrder(orderNo);
					request.setAttribute("taskListOfOrder", aList);
					//Puneet fetching the current owner
					String owner = new Utility().getOrderOwnedBy(aList);
				/*if(!("NEW".equalsIgnoreCase(listAccountDetails.get(0).getStageName()))){
					formBean.setOrderOwner(owner);
				}*/
				//[020] start
				/*if((AppConstants.ORDER_STAGE_PARTIAL_INITIATED.equalsIgnoreCase(listAccountDetails.get(0).getStageName()))){*/
					if(!("NEW".equalsIgnoreCase(listAccountDetails.get(0).getStageName()))){
						boolean servicePrsent=objDao.isServicesPresentInUserBin(orderNo,String.valueOf(roleId));
						boolean roleInWF = Utility.isRolePrsentInWorkFlow(Integer.valueOf(objUserDto.getUserRoleId()), aList);
						boolean editMode_AM_NoService=false;
						//[224] start
						if(servicePrsent==false && "AM".equalsIgnoreCase(listAccountDetails.get(0).getStageName()) && roleId==1 ){
							editMode_AM_NoService=true;
						}
						//[224] end
						//condition=editMode_AM added by [224]
						if(servicePrsent || editMode_AM_NoService){
							formBean.setOrderOwner(String.valueOf(roleId));
						}else{
							if(roleInWF){
								modeName = "viewMode";
								/*
								 * Below code is written, because earlier in case of view order, 
								 * if order stage is partial initiated, then billing trigger button was not showing
								 * even if flag was coming as enable.
								 * Now handled that situation by capturing billingTriggerFlag and setting that value in bean, 
								 * once it is in viewMode
								*/
								if(!billingTriggerEnableDisable.equals(null))
									formBean.setIsBillingTriggerReady(billingTriggerEnableDisable);
							//}else{
								/*
								 * If any of the service is not present in current user's bin and current role is also not present in work flow
								*/
								//modeName = "viewMode";	
							}
								
						}
					}
				//}
				//[020] end
				//request.setAttribute("showAttachIcon", "YES");
				String m6Status=request.getParameter("m6Status");
				if("1".equals(m6Status))
				{
					request.setAttribute("m6StatusCode", "1");
				}else if("-1".equals(m6Status))
				{
					request.setAttribute("m6StatusCode", "-1");
				}
				formBean.setHdnOrderCreationSource(listAccountDetails.get(0).getOrder_creation_source());
				//===========================Checking View or Edit Mode===================================
				String isEditMode = objDao.checkForOrderAlready(orderNo,objUserDto.getUserId(),roleId,0);
				//Meenakshi : changes for performance tunning
				formBean.setIsFormBlank(0);
				if(listAccountDetails.get(0).getNoOfServiceInOrder() > 0){
					formBean.setLatestTab("li5");
				}else if(listAccountDetails.get(0).getPoCount() == 1){
					formBean.setLatestTab("li3");
				}else if(listAccountDetails.get(0).getContactCount() == 1){
					formBean.setLatestTab("li2");
				}else{
					formBean.setLatestTab("li1");
				}
				//===========================Checking View or Edit Mode===================================
				System.err.println("Status of Order Already Opened--->" + isEditMode);	
				System.out.println("return:"+modeName);
				
				if(("viewMode").equalsIgnoreCase(modeName)){
					forwardMapping="ViewOrderUpdateDisplay";
				}else{
					if(!isEditMode.equalsIgnoreCase("0")){// If already opened
						forwardMapping="ViewOrderUpdateDisplay";
					}else{
						objDao.checkForOrderAlready(orderNo,objUserDto.getUserId(),roleId,1);
						forwardMapping="NewOrderUpdateDisplay";
					}	
				}
				    //added by Megha to enable SendToCopc for PM
				    copcCount=objModel.getPrevTaskRoleId(orderNo);	
				    formBean.setIsCopcApproved(Integer.toString(copcCount));
					//Megha
									
				
			}
			catch(Exception ex){
	  		//logger.error(ex.getMessage()+ " Exception occured in viewOrder method of "+ this.getClass().getSimpleName());
				forwardMapping="ErrorPageAction";
				return mapping.findForward(forwardMapping);
			}
			return mapping.findForward(forwardMapping);
		}	
	
	 public ActionForward downloadTemplateExcelForPrdCatelog(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) {
				String forwardMapping=null;
			
				try
				{
					
					NewOrderBean formBean=(NewOrderBean)form;
					NewOrderModel model=new NewOrderModel();
					//String[] serviceProductId=request.getParameterValues("chk_spId");
					
					//make excel
					model.downloadTemplateExcelForPrdCatelog(formBean);
					//send excel in request
					response.setContentType("application/vnd.ms-excel");
					response.setHeader("Content-Disposition", "attachment; filename=ProductCatelogExcel.xls");
					ServletOutputStream out = response.getOutputStream();
					formBean.getProductCatelogTemplateWorkbook().write(out);
					out.flush();
					out.close();
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
					//AppConstants.NPDLOGGER.error(ex.getMessage()
						//	+ " Exception occured in downloadPlanExcelForEdit method of "
						//	+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
					forwardMapping = Messages.getMessageValue("errorGlobalForward");
					return mapping.findForward(forwardMapping);
				}
				
			
				return null;
			}
	
	 /**
	  * Method to get all data for Masters Download
	  * @param mapping
	  * @param form
	  * @param request
	  * @param response
	  * @return
	  */
	 public ActionForward downloadMasters(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
			NewOrderModel objModel=new NewOrderModel();
			NewOrderBean formBean = (NewOrderBean)form;
			//formBean.getProductID()
			HSSFWorkbook wb= objModel.downloadMasters(Long.parseLong(formBean.getHdnSelectedServiceDetailId()));
			formBean.setExcelWorkbookFormaster(wb);
			
			response.setHeader("Content-Disposition", "attachment; filename=Masters.xls");
			
			try {
				ServletOutputStream out = response.getOutputStream();
				formBean.getExcelWorkbookFormaster().write(out);
				out.flush();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			 return null;
	 }
	 
	 
	 public ActionForward uploadPlanExcel(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		  NewOrderBean formBean =(NewOrderBean)form;
		 ActionMessages messages=new ActionMessages();
		 ActionForward forward = null;
		 boolean validation_error = false;
		 FormFile uploadedFile = formBean.getUploadedFile();
		 if(uploadedFile==null || "".equals(uploadedFile.getFileName()))
			{
				messages.add("",new ActionMessage("uploadPlan.error.attachment.notSelected"));
				validation_error = true;
			}
			else if(!(uploadedFile.getFileName().substring(uploadedFile.getFileName().lastIndexOf(".")+1)).equalsIgnoreCase("xls"))
			{
				messages.add("",new ActionMessage("uploadPlan.error.attachment.notXls"));
				validation_error = true;
			}
			/*else if(uploadedFile.getFileSize()>SessionObjectsDto.getInstance().getAttachmentSize())
			{
				messages.add("attachment",new ActionMessage("error.attachment.size"));
				validation_error = true;
			}*/
			
			if(validation_error)
			{
				saveMessages(request, messages);
				//TODO : 
				forward = mapping.findForward("DisplayUploadPage");
				/*uploadPlanExcelInitPage(mapping, form, request, response);
				forwardMapping="uploadPlanExcel";
				return mapping.findForward(forwardMapping);*/
			}
			int isValid = 1;
			int status  = 0;
			ExcelValidator validator = new ExcelValidator();
			String templateFilePath=AppConstants.UploadPrdCatelogExcelTemplateFilePath;
			try {
				//ArrayList<String> dao.getAllowedSections();
				status = validator.validateUploadedExcel(uploadedFile,templateFilePath,messages);
			} catch (IOESException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (status == 1) {
				isValid = 0;
				messages.add("sheet_mismatch", new ActionMessage(
						"errors.excel.sheet.mismatch"));
				AppConstants.IOES_LOGGER.info("errors.excel.sheet.mismatch");
				
			} else if (status == 2) {
				isValid = 0;
				messages.add("sheetName_mismatch", new ActionMessage(
						"errors.excel.sheetname.mismatch"));
				AppConstants.IOES_LOGGER.info("errors.excel.sheetname.mismatch");
				
			} else if (status == 3) {
				isValid = 0;
				messages.add("sheet_blank", new ActionMessage(
						"errors.excel.sheet.blank"));
				AppConstants.IOES_LOGGER.info("errors.excel.sheet.blank");
				
			} else if (status == 4) {
				isValid = 0;
				messages.add("colNum_mismatch", new ActionMessage(
						"errors.excel.columnnumber.mismatch"));
				AppConstants.IOES_LOGGER.info("errors.excel.columnnumber.mismatch");
				
			} else if (status == 5) {
				isValid = 0;
				messages.add("colNanme_mismatch", new ActionMessage(
						"errors.excel.columnname.mismatch"));
				AppConstants.IOES_LOGGER.info("errors.excel.columnname.mismatch");
				
			} else if (status == 7) {
				isValid = 0;
				messages.add("invalid_excel", new ActionMessage(
						"errors.excel.invalid.excel"));
				AppConstants.IOES_LOGGER.info("errors.excel.invalid.excel");
				
			} else if (status == 8) {
				isValid = 0;
				messages.add("invalidObjInFile", new ActionMessage(
						"errors.excel.invalid.filehasobject"));
				AppConstants.IOES_LOGGER.info("errors.excel.invalid.filehasobject");
				
			} else if (status == 9) {
				isValid = 0;
				
				AppConstants.IOES_LOGGER.info("errors.excel.column.length");
				
			}else if (status == 10) {
				isValid = 0;
				
				AppConstants.IOES_LOGGER.info("errors.excel.row.name.mismatch");
				
			}
			else if (status == 11) {
				isValid = 0;
				
				AppConstants.IOES_LOGGER.info("errors.excel.row.count.mismatch");
				
			}
			if(isValid == 0)
			{
				saveMessages(request, messages);
				formBean.setLoadExcelProductConfig_status(AppConstants.loadExcelProductConfig_status_ValidationError);
				//TODO
				forward = mapping.findForward("DisplayUploadPage");
			}else {
				try{
					int statusSave=0;
					HSSFWorkbook uploadedWorkBook =  new HSSFWorkbook(uploadedFile.getInputStream());
					int noOfSheets = uploadedWorkBook.getNumberOfSheets();
					HSSFSheet lastSheet = uploadedWorkBook.getSheetAt(noOfSheets-1);
					//Last sheet of uploaded file have product ID on second row's first cell;
					//int productID = new Double(lastSheet.getRow(1).getCell(0).getNumericCellValue()).intValue();
					int productID = 28;
					if(productID >0){
						statusSave = saveUploadedFileInfo(uploadedFile,productID,templateFilePath);
					}
				}catch(IOESException ex){
					//TODO:
				}catch(FileNotFoundException es){
					
				}catch(IOException ex){
					
				}
				
				
			}
			
		 
			return forward;
	 }
	 
	 /**
		 * @param templateStream 
		 * @method saveUploadedFileInfo
		 * @purpose save uploaded file data in staging table in database
		 * @param FormFile,
		 *            filepath, userName
		 * @param excel_uploadPath,
		 *            uploadedFilePath
		 * @return
		 * @throws NpdException
		 */
		public int saveUploadedFileInfo(FormFile uploadedFile,int productID, String templateFilePath)
				throws IOESException{
		//	AppConstants.IOES_LOGGER.info("saveUploadedFileInfo() started");
			int sheetCol, sheetRow;
			ArrayList<Object[][]> excelDataList = new ArrayList<Object[][]>();
			int thisSaveCode=0;
			int saveStatusCode=0;
			try {
				String fileName = null;
				
					if (uploadedFile != null) {
						fileName = uploadedFile.getFileName();
										}

					if (fileName != null) {
						HSSFWorkbook workbook = null;
						HSSFSheet sheet = null;
						HSSFRow rowInSheet = null;
						HSSFCell cellInSheet = null;

						
						workbook = new HSSFWorkbook(uploadedFile.getInputStream());
						for (int count=0;count<workbook.getNumberOfSheets()-1;count++){
							sheet = workbook.getSheetAt(count);
							sheetRow = sheet.getLastRowNum();
							sheetCol = sheet.getRow(0).getLastCellNum();
							Object excelData[][] = new Object[sheetRow][sheetCol];
							for (int r = 1; r <= sheetRow; r++) {
								rowInSheet = sheet.getRow(r);
								int columIndex=0;
								for (int c = 1; c < sheetCol + 1; c++) {
									if (rowInSheet != null) {
										cellInSheet = rowInSheet.getCell(c - 1);
										if (cellInSheet != null) {
											if (cellInSheet.getCellType() == 0)
											{
												excelData[r - 1][columIndex++] = Utility.convertWithOutE_WithOutDotZero(String.valueOf(cellInSheet.getNumericCellValue()));
												/*NumberFormat formatter = new DecimalFormat("0");
												excelData[r - 1][columIndex++] = formatter
															.format(cellInSheet.getNumericCellValue());*/
											}else
											{	excelData[r - 1][columIndex++] = cellInSheet.toString().trim();
											}
											
										} else {
											excelData[r - 1][columIndex++] = "";
										}
									} else {
										excelData[r - 1][columIndex++] = "";
									}
								}
							}
							excelDataList.add(excelData);
						}
						
						
					}
					//if (checkCode == 1) {
						NewOrderModel model=new NewOrderModel();
						saveStatusCode = model.saveUploadedFileToTemporaryTable(excelDataList,productID,fileName);
						if(saveStatusCode>0)
						{
							thisSaveCode= 1;
						}
						else
						{
							thisSaveCode= 0;
						}

					/*}
					else
					{
						thisSaveCode= 0;
					}*/

				

				
	//			AppConstants.IOES_LOGGER.info("Completed..");
				return thisSaveCode;
			} catch (Exception ed) {
				ed.printStackTrace();
				AppConstants.IOES_LOGGER.error("Error while getting saveUploadedFileInfo "
						+ ed.getMessage());
				throw new IOESException(ed);
			}

			finally {
				AppConstants.IOES_LOGGER.info("saveUploadedFileInfo() completed");
			}

		}

		public ActionForward gotoUploadPage(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
		{
			ActionForward forward = new ActionForward(); // return value
			try
			{
				forward = mapping.findForward("DisplayUploadPage");
			}
			catch (Exception e) 
			{
				AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			}
			return forward;
		}
		/*Function		:goToFileUpload
		 * return type	:ActionForward 
		 * @Author		:Anil Kumar
		 * Date			:17-feb-11
		 * Purpose		:To Uploading file...
		 * */
		public ActionForward goToFileUpload(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse respons) throws Exception
		{
			NewOrderBean formBean=(NewOrderBean)form;
			FormFile file=formBean.getFileAttachment();
			ActionForward forward=new ActionForward();
			Boolean validation_error=false;
			ActionMessages messages=new ActionMessages();
			validation_error=Utility.validateAttachment(file, messages);
			long status=0;
			ArrayList<NewOrderDto> fileAttachmentTypeList = new ArrayList<NewOrderDto>();
			int docType=0;
			NewOrderModel objModel=new NewOrderModel();
			fileAttachmentTypeList=objModel.fileAttachmentType(docType);
			request.setAttribute("fileAttachmentTypeList", fileAttachmentTypeList);
			formBean.setFileAttachmentTypeList(fileAttachmentTypeList);
			if(validation_error)
			{
				//uploadPlr(mapping, form, request, response);
				saveMessages(request, messages);
				forward=mapping.findForward("FileAttachment");				
			}
			else
			{
				FileAttachmentDto fileDto=new FileAttachmentDto();
				fileDto.setHdnOrderNo(formBean.getHdnOrderNo());
				fileDto.setHdnAccountNo(formBean.getAccountID());
				fileDto.setFileName(formBean.getFileAttachment().getFileName());
				fileDto.setFileData(formBean.getFileAttachment());
				fileDto.setFileRename(formBean.getFileRename());
				fileDto.setFileTypeId(Integer.parseInt(request.getParameter("fileTypeId")));
				fileDto.setEmpId(Long.parseLong(request.getParameter("empId")));
				
				
				//NewOrderModel objModel=new NewOrderModel();
				status=objModel.insertFileUpload(fileDto);
				if(status==0)
				{
					formBean.setIsUpload("successUpload");
					forward=mapping.findForward("FileAttachment");
				}
				else
				{
					messages.add("saveUploadFile",new ActionMessage("FileUploadFailed"));
					saveMessages(request, messages);
					forward=mapping.findForward("FileAttachment");	
				}
				
				//forward=mapping.findForward("FileAttachment");
			}
			try
			{
				
			}
			catch(Exception e)
			{
				AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			}
			return forward;
		}
		/*Function		:goToFileAttachmentPage
		 * return type	:ActionForward 
		 * @Author		:Anil Kumar
		 * Date			:17-feb-11
		 * Purpose		:To view file attachment page....
		 * */
		public ActionForward goToFileAttachmentPage(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse respons) throws Exception
		{
			ActionForward forward=new ActionForward();
			NewOrderBean formBean=(NewOrderBean)form;
			NewOrderModel objModel=new NewOrderModel();
			ArrayList<NewOrderDto> fileAttachmentTypeList = new ArrayList<NewOrderDto>();
			int docType=Integer.parseInt(request.getParameter("fileTypeId")); 
			int fileTypeId=docType;
			int sentMethod=Integer.parseInt(request.getParameter("sentMethod")); 
			fileAttachmentTypeList=objModel.fileAttachmentType(docType);
		 	request.setAttribute("fileAttachmentTypeList", fileAttachmentTypeList);
			formBean.setFileAttachmentTypeList(fileAttachmentTypeList);
			formBean.setAccountID(request.getParameter("accountNo"));
			formBean.setHdnOrderNo(request.getParameter("orderNo"));
			formBean.setCounterAttach(request.getParameter("counterbutton"));
			formBean.setFileTypAttach(request.getParameter("fileTypeId"));
			request.setAttribute("fileTypeId", fileTypeId);
			request.setAttribute("sentMethod", sentMethod);
			forward=mapping.findForward("FileAttachment");
			return forward;
		}
		//BY Saurabh for copy charge service
		public ActionForward displayCopyCharge(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
		{
			ActionForward forward = new ActionForward(); // return value
			ActionMessages messages = new ActionMessages();
			try
			{
				forward = mapping.findForward("forwardCopyCharge");
			}
			catch (Exception e) 
			{
				AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
				messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
			}
			return forward;
		}
		
		
		/*[007] Start*/
		public ActionForward goToDownloadedFilePage(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse respons) throws Exception
		{
			ActionForward forward=new ActionForward();
			NewOrderBean formBean=(NewOrderBean)form;
			if( !( request.getParameter("accountNo")==null)){
				formBean.setAccountID(request.getParameter("accountNo"));
			}
			if(! (request.getParameter("orderNo")==null)) {
				formBean.setHdnOrderNo(request.getParameter("orderNo"));
			}
			if(! (request.getParameter("crmAccountNo")==null)) {
				formBean.setCrmAccountNo(request.getParameter("crmAccountNo"));
			}
			int sentMethod=Integer.parseInt(request.getParameter("sentMethod")); 
			int fileTypeId=0;
			int docType=Integer.parseInt(request.getParameter("fileTypeId")); 	
			
			if(sentMethod==0)
			{
				fileTypeId=0;
			}
			else
			{
				fileTypeId=Integer.parseInt(request.getParameter("fileTypeId")); 
			}
			
			ArrayList<FileAttachmentDto> listFileAttached= new ArrayList<FileAttachmentDto>();
			ArrayList<NewOrderDto> fileAttachmentTypeList = new ArrayList<NewOrderDto>();
			forward=mapping.findForward("UploadedFile");
			NewOrderModel objModel=new NewOrderModel();
			FileAttachmentDto fileDto=new FileAttachmentDto();			
			fileDto.setHdnAccountNo(formBean.getAccountID());
			fileDto.setHdnOrderNo(formBean.getHdnOrderNo());
			fileDto.setCrmAccountNo(formBean.getCrmAccountNo());
			listFileAttached=objModel.getUploadedFileName(fileDto,docType);
			fileAttachmentTypeList=objModel.fileAttachmentType(fileTypeId);
			request.setAttribute("fileAttachmentTypeList", fileAttachmentTypeList);
			formBean.setFileAttachmentTypeList(fileAttachmentTypeList);
			request.setAttribute("fileTypeId", fileTypeId);
			request.setAttribute("listFileAttached", listFileAttached);
			request.setAttribute("sentMethod", sentMethod);
			
			return forward;
		}
		
		/*Function		:goToDownloadedFilePage
		 * return type	:ActionForward 
		 * @Author		:Rakshika
		 * Date			:24-feb-11
		 * Purpose		:To view attached file page....
		 * 
		public ActionForward getListofDownloadedFile(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse respons) throws Exception
		{
			ActionForward forward=new ActionForward();
		NewOrderBean formBean=(NewOrderBean)form;
			ArrayList<FileAttachmentDto> listFileAttached= new ArrayList<FileAttachmentDto>();
			formBean.setAccountID(request.getParameter("accountNo"));
			formBean.setHdnOrderNo(request.getParameter("orderNo"));
			NewOrderModel objModel=new NewOrderModel();
			FileAttachmentDto fileDto=new FileAttachmentDto();
			fileDto.setHdnAccountNo(formBean.getAccountID());
			fileDto.setHdnOrderNo(formBean.getHdnOrderNo());
			listFileAttached=objModel.getUploadedFileName(fileDto);
			forward=mapping.findForward("UploadedFile");
			return forward;
		}*/
		
		/*Function		:goToDownloadFile
		 * return type	:ActionForward 
		 * @Author		:Rakshika
		 * Date			:24-feb-11
		 * Purpose		:To Downloading file...
		 * */
		public ActionForward goToDownloadFile(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
		{
			NewOrderBean formBean=(NewOrderBean)form;
			//String fileName=formBean.getFileName();
			ActionForward forward=new ActionForward();
			ActionMessages messages=new ActionMessages();
			
			FileAttachmentDto downloadedFile= new FileAttachmentDto();
			
			CommonBaseModel commonBaseModel = new CommonBaseModel();
			byte[] File = null;
			
			FileAttachmentDto fileDto=new FileAttachmentDto();
			fileDto.setHdnOrderNo(request.getParameter("hdnOrderNo"));
			fileDto.setHdnAccountNo(request.getParameter("accountID"));
			
			//--Added & modified by vijay--//
			/*these code is modify for solving the error 
			  of downloading file whose name contain special characters */
			
			//fileDto.setFileName(request.getParameter("fileName"));
			fileDto.setFileName(request.getParameter("hdnFileName"));
			fileDto.setCreateDate(request.getParameter("createDate"));
			//String slNO=request.getParameter("sLNO");
			//fileDto.setSlno(Integer.parseInt(slNO));
			String slNO=request.getParameter("hdnslno");
			fileDto.setSlno(Integer.parseInt(slNO));
			
			//--end of code--//
			
			NewOrderModel objModel=new NewOrderModel();
			downloadedFile=objModel.getDownloadedFile(fileDto);
			//java.sql.Blob blob=null;
			File = commonBaseModel.blobToByteArray(downloadedFile.getFile());
			String ContentType = commonBaseModel.setContentTypeForFile(downloadedFile.getFileName());//CHANGES FOR SYSTEM TESTING DEFECTS
			response.setContentType(ContentType);
			
			 //---added by vijay--//
			 
			//response.setHeader("Content-Disposition","attachment;filename=" + downloadedFile.getFileName());//CHANGES FOR SYSTEM TESTING DEFECTS
			
			/*
			 * Here file name is encoded and space is replace by special secequence of charcters,
			 * because if file name contain space then bydefault space is converting in a plus sign (+) while downloading,
			 * so for recognize any space in file name, space is replacing by this string @%20@
			 */
			String encodedFileName = java.net.URLEncoder.encode(downloadedFile.getFileName().replace(" ", "@%20@"), "ISO-8859-1");
			System.out.println("encoded file name is - "+ encodedFileName);
			/* 
			 * After encoding file name, for maintaing the space character, again replace this special sequence %40%2520%40% with space character.
			 * This sequence of characters %40%2520%40% means file name containg space space 
			 */
			response.setHeader("Content-Disposition","attachment;filename=" + encodedFileName.replace("%40%2520%40", " "));
			
			//--end of code--//
			
			ServletOutputStream outs = response.getOutputStream();
			outs.write(File);
			outs.flush();
			outs.close();
			
			if(downloadedFile.getIsDownload().equalsIgnoreCase("1"))
			{
				formBean.setIsDownload("successDownload");
				forward=mapping.findForward("");
			}
			else
			{
				messages.add("saveDownloadFile",new ActionMessage("FileDownloadFailed"));
				saveMessages(request, messages);
				forward=mapping.findForward("");	
			}
				
			try
			{
				
			}
			catch(Exception e)
			{
				AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			}
			return forward;
		}
		//[007] End
		
		//lawkush Start Commented
		/*
		public ActionForward DeleteDownLoadFile(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
		
		{
			NewOrderDao objDao = new NewOrderDao();
			NewOrderDto objRetDto = new NewOrderDto();
			NewOrderBean formBean = (NewOrderBean)form;
			NewOrderModel mod=new NewOrderModel();
			ActionForward forward=new ActionForward();
			try {
				
			String arr=	request.getParameter("hdnslno");
			String accountID=	request.getParameter("accountID");
			String orderNo=	request.getParameter("hdnOrderNo");
			formBean.setOrderNo(orderNo);
			formBean.setAccountID(accountID);
			String empId=(request.getParameter("empId"));
			
				objRetDto = mod.DeleteDownLoadFile(arr,empId);
				if( !( request.getParameter("accountNo")==null)){
					formBean.setAccountID(request.getParameter("accountNo"));
				}
				if(! (request.getParameter("orderNo")==null)) {
					formBean.setHdnOrderNo(request.getParameter("orderNo"));
				}
				if(! (request.getParameter("crmAccountNo")==null)) {
					formBean.setCrmAccountNo(request.getParameter("crmAccountNo"));
				}
				int sentMethod=Integer.parseInt(request.getParameter("sentMethod")); 
				int fileTypeId=0;
				if(sentMethod!=0)
				{
				fileTypeId=Integer.parseInt(request.getParameter("fileTypeId")); 	
				}
				ArrayList<FileAttachmentDto> listFileAttached= new ArrayList<FileAttachmentDto>();
				ArrayList<NewOrderDto> fileAttachmentTypeList = new ArrayList<NewOrderDto>();
				forward=mapping.findForward("UploadedFile");
				NewOrderModel objModel=new NewOrderModel();
				FileAttachmentDto fileDto=new FileAttachmentDto();			
				fileDto.setHdnAccountNo(formBean.getAccountID());
				fileDto.setHdnOrderNo(formBean.getHdnOrderNo());
				fileDto.setCrmAccountNo(formBean.getCrmAccountNo());
				listFileAttached=objModel.getUploadedFileName(fileDto,fileTypeId);
				fileAttachmentTypeList=objModel.fileAttachmentType(fileTypeId);
				request.setAttribute("fileAttachmentTypeList", fileAttachmentTypeList);
				formBean.setFileAttachmentTypeList(fileAttachmentTypeList);
				request.setAttribute("fileTypeId", fileTypeId);
				request.setAttribute("listFileAttached", listFileAttached);
				request.setAttribute("sentMethod", sentMethod);
							
			}
			
			catch (Exception e) {
				e.printStackTrace();
			}
			return forward;
			
	
			
			
		}
		
		*/
		//lawkush Comment End
			//  [010]  Start
		public ActionForward getOrderToBeCopied(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
		{
			ActionForward forward = new ActionForward(); // return value
			try
			{
				forward = mapping.findForward("DisplayCopyOrderPage");
			}
			catch (Exception e) 
			{
				AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			}
			return forward;
		}
		//	  [010]  End
		
		//	  [012]  Start
		public ActionForward displayPartailPublish(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
		{
			ActionForward forward = new ActionForward(); // return value
			try
			{
				forward = mapping.findForward("DisplayPartialPublishPage");
			}
			catch (Exception e) 
			{
				AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			}
			return forward;
		}
		//   [012]  End
// Raghu for Network poplocation		
		public ActionForward fetchNetworkPopLocation(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
		{
			ActionForward forward = new ActionForward(); // return value
			ActionMessages messages = new ActionMessages();
			try
			{
				forward = mapping.findForward("DisplaySelectNetPopLoc");
			}
			catch (Exception e) 
			{
				AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
				messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
			}
			return forward;
		}
		
		/*Function		:goToViewBCPDetailsPage
		 * return type	:ActionForward 
		 * @Author		:Anil Kumar
		 * Date			:22-June-11
		 * Purpose		:To view bcp details page....
		 * */
		public ActionForward goToViewBCPDetailsPage(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse respons) throws Exception
		{
			ActionForward forward=new ActionForward();			
			NewOrderBean formBean=(NewOrderBean)form;
			formBean.setAccountID(request.getParameter("accountId"));
		//[020] Start]
		NewOrderDaoExt objDao = new NewOrderDaoExt();
		PagingDto objDto=new PagingDto();
		String inExcel = formBean.getToExcel();
		if ("true".equals(inExcel)) 
		{
			ArrayList<NewOrderDto> bcpDetails = new ArrayList<NewOrderDto>();
			int accountID=Integer.parseInt(request.getParameter("accountId"));
			objDto.setAccountID(accountID);
			objDto.setSortBycolumn("BCPID");
			objDto.setSortByOrder("ASC");
			objDto.setStartIndex(1);
			objDto.setEndIndex(100000);
			bcpDetails = objDao.getBCPDetailsWithSorting(objDto);
			request.setAttribute("bcpDetails", bcpDetails);
			forward=mapping.findForward("viewBCPDetailsPageExport");
		}
		else
		{
			forward=mapping.findForward("viewBCPDetailsPage");
		}
		//[020] End]
			return forward;
		}
	
		/*Function		:goToCOPCPageFromSEDRole
		 * return type	:ActionForward 
		 * @Author		:Saurabh Singh
		 * Date			:29-June-11
		 * Purpose		:To view SendToCOPCPage in SED role to send back the order for changes
		 * */
		public ActionForward goToCOPCPageFromSEDRole(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
		{
			ActionForward forward = new ActionForward(); // return value
			try
			{
				forward = mapping.findForward("DisplaySendToCOPCPage");
			}
			catch (Exception e) 
			{
				AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			}
			return forward;
		}
		public ActionForward serviceCancelReason(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
		{
			ActionForward forward = new ActionForward(); // return value
			try
			{
				forward = mapping.findForward("DisplayServiceCancelReason");
			}
			catch (Exception e) 
			{
				AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			}
			return forward;
		}

		/*Function		:goToAttachGAM
		 * return type	:ActionForward 
		 * @Author		:Anil Kumar
		 * Date			:26-dec-11
		 * Purpose		:To view file select GAM page....
		 * */
		public ActionForward goToAttachGAM(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse respons) throws Exception
		{
			ActionForward forward=new ActionForward();
			NewOrderBean formBean=(NewOrderBean)form;
			formBean.setAccountID(request.getParameter("accountNo"));
			formBean.setHdnOrderNo(request.getParameter("orderNo"));
			formBean.setQuoteNo(request.getParameter("quoteNo"));
			request.setAttribute("quoteN", "formBean.getQuoteNo");
			forward=mapping.findForward("selectGAMPage");
			return forward;
		}
		/*Function		:goToViewGAM
		 * return type	:ActionForward 
		 * @Author		:Anil Kumar
		 * Date			:26-dec-11
		 * Purpose		:To view file selected GAM List page....
		 * */
		public ActionForward goToViewGAM(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse respons) throws Exception
		{
			ActionForward forward=new ActionForward();
			NewOrderBean formBean=(NewOrderBean)form;
			long HorderNo=Long.parseLong(request.getParameter("orderNo"));
			formBean.setAccountID(request.getParameter("accountNo"));
			formBean.setHdnOrderNo(request.getParameter("orderNo"));
			formBean.setQuoteNo(request.getParameter("quoteNo"));
			formBean.setIsInView(request.getParameter("isInView"));
			formBean.setStage(request.getParameter("stage"));
			forward=mapping.findForward("viewGAMPage");
			return forward;
		}

	public ActionForward getAllComponents(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
		{
			ActionForward forward = new ActionForward(); // return value
			try
			{
				forward = mapping.findForward("DisplayCompnentsList");
			}
			catch (Exception e) 
			{
				AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			}
			return forward;
		}
		
			
		public ActionForward getPackages(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
		{
			ActionForward forward = new ActionForward(); // return value
			int serid=0;
			try
			{
				serid=Integer.parseInt(request.getParameter("serdeid"));
				request.setAttribute("serdeid",serid);
				forward = mapping.findForward("DisplayPackagesList");
			}
			catch (Exception e) 
			{
				AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			}
			return forward;
		}
		
		public ActionForward fetchMigrationOrders(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
		{
			ActionForward forward = new ActionForward(); // return value
			NewOrderModel objModel=new NewOrderModel();
			ActionMessages messages = new ActionMessages();
			ArrayList<MigrationOrdersDto> listMigrationOrders = new ArrayList<MigrationOrdersDto>();
			String orderNo=	request.getParameter("migrationOrderNo");
			String serviceType=	request.getParameter("serviceType");
			try
			{
				
				listMigrationOrders = objModel.fetchMigrationOrders(Long.parseLong(orderNo),serviceType);
				
				if(serviceType .equalsIgnoreCase( "dccolo"))
				{
					request.setAttribute("listMigrationOrders", listMigrationOrders);
					forward = mapping.findForward("DisplayMigrationOrderjspdccolo");
				}
				else
				{
				 request.setAttribute("listMigrationOrders", listMigrationOrders);
				  forward = mapping.findForward("DisplayMigrationOrderjspTeleport");
				}
				
			}
			catch (Exception e) 
			{
				AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
				messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
			}
			return forward;
		}	
		
		public ActionForward getOrderListForPendingWithPM(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
		{
			ActionForward forward = new ActionForward(); // return value
			NewOrderModel objModel=new NewOrderModel();
			ActionMessages messages = new ActionMessages();
			ArrayList<NewOrderDto> listPendingOrders = new ArrayList<NewOrderDto>();
			NewOrderBean objBean = (NewOrderBean)form;
			NewOrderDto newOrderDto = new NewOrderDto();
			try
			{
				newOrderDto.setEmployeeid(Integer.parseInt(request.getParameter("Employeeid")));
				newOrderDto.setOrderNo(request.getParameter("orderNo"));
				if(request.getParameter("crmAccountNo").equals("") || request.getParameter("crmAccountNo")== null )
					newOrderDto.setCrmAccountNo(0);	
				else
					newOrderDto.setCrmAccountNo(Integer.parseInt(request.getParameter("crmAccountNo")));
				
				if (request.getParameter("projectManager") != null && !"".equals(request.getParameter("projectManager")))
				{
						newOrderDto.setProjectManagerID(Long.parseLong(request.getParameter("projectManager")));
						request.setAttribute("projectmanager",request.getParameter("projectManager"));
						
				}
				else
				newOrderDto.setProjectManagerID(0);
				listPendingOrders = objModel.getOrderListForPendingWithPM(newOrderDto);
				request.setAttribute("orderList", listPendingOrders);
				//ArrayList<NewOrderDto> projectManagerNameListAll = new ArrayList<NewOrderDto>();
				//projectManagerNameListAll = objModel.getProjectManagerNameList();
				//request.setAttribute("projectManagerNameListAll", projectManagerNameListAll);
				forward = mapping.findForward("ReAssignToPM");
				
				
			}
			catch (Exception e) 
			{
				AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
				messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
			}
			return forward;
		}
		
		//==========================================================================================
		public void populateServiceAttMasterValue(ActionMapping mapping, ActionForm form
				,HttpServletRequest request, HttpServletResponse response) throws Exception{
			//,String sessionid
			NewOrderDao objDao = new NewOrderDao();
			response.setContentType("text/json");			
			PrintWriter obj =  response.getWriter();
			String jsonValue = "";
			try{
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				
				String reqVal = request.getParameter("searchval");				
				String reqAttID=request.getParameter("attributefor");				
				String sourceType=request.getParameter("sourceType");
				String accountId=request.getParameter("param");
				String parntAttId = request.getParameter("parntAttId");
				String serviceId = request.getParameter("serviceId");
				if(parntAttId!=null && parntAttId.equalsIgnoreCase("undefined")){
					if(null == accountId || accountId.equalsIgnoreCase("0")){
					accountId = request.getParameter("accountId");
						if(null == accountId){
							accountId="0";
						}
					}
				}
				String callType = request.getParameter("callType");
				if(null != reqAttID && "AUTOSUGGESTLICENSECO".equalsIgnoreCase(reqAttID.trim())){
					accountId =  accountId + "_" +Long.toString(objDao.getSelectedProductId(Long.parseLong(serviceId)));
				}
				jsonValue = objDao.getAutoSuggestServiceAttribute(reqVal,objUserDto.getUserRoleId(),reqAttID,sourceType,accountId, callType);
				
				//System.err.println(jsonValue);
				obj.write(jsonValue);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		public void populateServiceAttMasterValueValidate(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
		{
			//,String sessionid
			NewOrderDao objDao = new NewOrderDao();
			response.setContentType("text/json");
		
			PrintWriter obj =  response.getWriter();
			String jsonValue = "";
			try 
			{
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);

				
				String reqVal = request.getParameter("searchval");				
				String reqAttID=request.getParameter("attributefor");				
				String sourceType=request.getParameter("sourceType");
				String accountId=request.getParameter("param");
				String parntAttId = request.getParameter("parntAttId");
				if(parntAttId!=null && parntAttId.equalsIgnoreCase("undefined")){
					if(null == accountId || accountId.equalsIgnoreCase("0")){
						accountId = request.getParameter("accountId");
						if(null == accountId){
							accountId="0";
						}
					}
				}
				String callType = request.getParameter("callType");	
				
				jsonValue = objDao.getAutoSuggestServiceAttValidate(reqVal,objUserDto.getUserRoleId(),reqAttID,sourceType,accountId, callType);				
				System.err.println(jsonValue);
				obj.write(jsonValue);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		//=======================================================================================================		
		//===========================================================================================
			/*Fecth Account from CRM on selection accountno from popup window
			 *Date:29-June-2012
			 *
			 * */
		public void fetchCRMAccountBCPDispatch(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
		{					
			response.setContentType("text/json");						
			PrintWriter obj =  response.getWriter();				
			int status=-1;
			String statusMsg="";
			try 
			{										
				String crmAccountNo=request.getParameter("accountno");
				CRMLogger.SysErr("----- In fetchCRMAccountBCPDispatch function -------");
				statusMsg=FetchAccountFromCRM.fetchingAccountBCPFromCRM(crmAccountNo);
				obj.write(statusMsg);	
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		public void refreshCRMAccountBCPDispatch(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
		{					
			response.setContentType("text/json");						
			PrintWriter obj =  response.getWriter();				
			int status=-1;
			String statusMsg="";
			try 
			{										
				String crmAccountNo=request.getParameter("accountno");
				System.err.println("----- In fetchCRMAccountBCPDispatch function -------");
				statusMsg=FetchAccountFromCRM.refreshAccountBCPFromCRM(crmAccountNo);
				obj.write(statusMsg);	
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		//===========================================================================================
		
		// fetch approval order istory
		
		public ActionForward fetchApprovalOrderHistory(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
		{
			ActionForward forward = new ActionForward(); // return value
			ActionMessages messages = new ActionMessages();
			NewOrderBean objBean = (NewOrderBean)form;
			//[226] Starts
			
			NewOrderModel now=new NewOrderModel();
			ViewOrderDao objDao = new ViewOrderDao();
			ArrayList<PagingDto> alTaskListOfOrder = new ArrayList<PagingDto>();
			String userId;
			ViewOrderDto objViewOrderDto= new ViewOrderDto();
			long orderNo=0;
			orderNo=Long.parseLong(request.getParameter("poNumber"));
			
			//PagingDto objDto =new PagingDto();
			//[226] Ends
			
			try
			{
		
				//[226] Starts
				HashMap approvalHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) approvalHashMap.get(request.getSession().getId());
				UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				userId=objUserDto.getUserId();
				System.out.println("" +userId);
				//PagingSorting pagingSorting = objForm.getPagingSorting();
				//alTaskListOfOrder = objDao.getTaskListHistory_all(orderNo,objDto);
				alTaskListOfOrder = objDao.getTaskListHistory(orderNo);
				
				String inExcel = objBean.getToExcel();
				if ("true".equals(inExcel)) 
				{
					request.setAttribute("OrderApprovalList",alTaskListOfOrder);
					objBean.setTaskOrderList(alTaskListOfOrder);
					forward = mapping.findForward("viewOrderApprovalHistoryExcel");
				} else{
				//[226] Ends
				objBean.setPoNumber(request.getParameter("poNumber"));
				objBean.setStage(request.getParameter("stage"));
				forward = mapping.findForward("DisplaySelectOrderHistory");
				}
			}
			catch (Exception e) 
			{
				AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
				messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
			}
			return forward;
		}
		public ActionForward arborLSILookup(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
		{
			ActionForward forward = new ActionForward(); // return value
			try
			{
				forward = mapping.findForward("DisplayArborLSILookupPage");
			}
			catch (Exception e) 
			{
				AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			}
			return forward;
		}
		
		
		// 016 start
		
		public void reassignToPM(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
		{
			//,String sessionid
			NewOrderDao objDao = new NewOrderDao();
			response.setContentType("text/json");			
		
			PrintWriter obj =  response.getWriter();
			String jsonValue = "";
			try 
			{
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);

				
				String reqVal = request.getParameter("searchval");				
				String reqAttID=request.getParameter("attributefor");				
				jsonValue = objDao.reassignToPM(reqVal,reqAttID);
				
				//System.err.println(jsonValue);
				obj.write(jsonValue);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		public void accessMatrixiB2BForUserId(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
		{
			//,String sessionid
			NewOrderDao objDao = new NewOrderDao();
			response.setContentType("text/json");			
		
			PrintWriter obj =  response.getWriter();
			String jsonValue = "";
			try 
			{
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);

				String reqAttID=request.getParameter("attributefor");	
				String reqVal="";
				if(!String.valueOf(request.getParameter("searchval")).equalsIgnoreCase("[object Object]"))
					reqVal = request.getParameter("searchval");		
				
				jsonValue = objDao.getAccessMatrixForUserId(reqVal,reqAttID);
				obj.write(jsonValue);
				
			} catch (Exception e) {
				Utility.LOG(true, true,"Some Error Occured in accessMatrixiB2BForUserId method of NewOrderAction "+e);
			}
		}   //
		
// 016 end		
		public ActionForward fetchLSINoForMBIC(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
		{
			ActionForward forward = new ActionForward(); // return value
			ActionMessages messages = new ActionMessages();
			try
			{
				forward = mapping.findForward("DisplayListOfLSINoForMBIC");
			}
			catch (Exception e) 
			{
				AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
				messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
			}
			return forward;
		}
	
		public ActionForward fetchVCS_BridgeLSI(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
		{
			ActionForward forward = new ActionForward(); // return value
			ActionMessages messages = new ActionMessages();
			try
			{
				forward = mapping.findForward("DisplayListOfLSIOfVCS_BridgeServiceLSI");
			}
			catch (Exception e) 
			{
				AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
				messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
			}
			return forward;
		}

		
		//[017] Start
		public ActionForward viewHardwareLineItems(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
		{
			ActionForward forward = new ActionForward(); // return value
			NewOrderBean formBean=new NewOrderBean();
			//NewOrderDto objDto=new NewOrderDto();
			OrderHeaderDTO objDto=new OrderHeaderDTO();
			NewOrderModel objModel=new NewOrderModel();
			ActionMessages messages = new ActionMessages();
			ArrayList<NewOrderDto> hardwareLineIetmlist = new ArrayList<NewOrderDto>();
			try
			{
				//objDto.setSearchAccount(formBean.getSearchAccount());
				//listAccount = objModel.displayAccountDetails(objDto);
				//request.setAttribute("AccountListBean", listAccount);
				forward = mapping.findForward("DisplayHardwareDetails");
			}
			catch (Exception e) 
			{
				AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
				messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
			}
			return forward;
		}	
		//[017] End
		public ActionForward DisplayCancelledhardwareLineDetails(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
		{
			ActionForward forward = new ActionForward(); // return value
			ActionMessages messages = new ActionMessages();
			try
			{
				forward = mapping.findForward("DisplayCancelledhardwareLineDetails");
			}
			catch (Exception e) 
			{
				AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
				messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
			}
			return forward;
		}
		
		public ActionForward fetchContact(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
		{
			ActionForward forward = new ActionForward(); // return value
			NewOrderBean formBean=new NewOrderBean();
			OrderHeaderDTO objDto=new OrderHeaderDTO();
			ActionMessages messages = new ActionMessages();
			try
			{
				objDto.setSearchAccount(formBean.getSearchAccount());
				forward = mapping.findForward("DisplaySelectContact");
			}
			catch (Exception e) 
			{
				AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
				messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
			}
			return forward;
		}

//addeed by manisha cust bil exp bfr no 7 start
		public ActionForward updateDemoDays(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {

			String forwardMapping = "";
			ArrayList<NewOrderDto> orderList = new ArrayList<NewOrderDto>();
			NewOrderDao objService = new NewOrderDao();
			NewOrderBean objForm = (NewOrderBean) form;
			NewOrderDto objDto = new NewOrderDto();
			try {
					ActionMessages messages = new ActionMessages();
					objDto.setLogicalSINo(objForm.getLogicalSINo());
					objDto.setFromDate(objForm.getSearchfromDate());
					objDto.setToDate(objForm.getSearchToDate());
					PagingSorting pagingSorting = objForm.getPagingSorting();
					pagingSorting.setPagingSorting(true, true);
					pagingSorting.setDefaultifNotPresent("ORDERNO",
							PagingSorting.increment, 1);
					objDto.setPagingSorting(pagingSorting);
					String indemoExcel=request.getParameter("inExcel");
					String fromPageSubmit=request.getParameter("fromPageSubmit");				
					request.setAttribute("fromPageSubmit", fromPageSubmit);
					
					String inExcel = objForm.getToExcel();
					if("1".equals(fromPageSubmit))
					{
						if ("true".equals(inExcel)) 
						{
							orderList = objService.updateDemoDays(objDto);
							request.setAttribute("orderReportNewListExcel", orderList); 
							pagingSorting.setPagingSorting(false, true);
							forwardMapping = "DemoDaysChangeExcel";
							
							
						}
						else 
						{
							orderList = objService.updateDemoDays(objDto);
							request.setAttribute("orderList", orderList);
							pagingSorting.setPagingSorting(true, true);
							forwardMapping = "DemoDaysChange";
						}
					}	
					
					else
					{
						forwardMapping = "DemoDaysChange";
						
					}
					
				
			} 
			catch (Exception ex) {
				return mapping.findForward(Messages
						.getMessageValue("errorGlobalForward"));
			}

			return mapping.findForward(forwardMapping);
		}

//addeed by manisha cust bil exp bfr no 7 end
		
		//[019] Start
		public ActionForward getAdvancePaymentDetails(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
				ActionForward forward = new ActionForward(); 
				AdvancePaymentDTO advancePaymentDTO = new AdvancePaymentDTO();

			try {
					int orderNo = Integer.parseInt(request.getParameter("orderNo"));
					int lineItemNumber = Integer.parseInt(request.getParameter("lineItemNo"));
					int serviceNo = Integer.parseInt(request.getParameter("serviceNo"));
					advancePaymentDTO.setOrderNo(orderNo);
					advancePaymentDTO.setLsiNo(lineItemNumber);
					advancePaymentDTO.setServiceNo(serviceNo);
					request.setAttribute("OrderNo", orderNo);
					request.setAttribute("LineItemNo", lineItemNumber);
					request.setAttribute("ServiceNo", serviceNo);
				
					forward = mapping.findForward("AdvancePayment");

			} catch (Exception e) {

				AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			}
			return forward;
		}
		// [019] end
//[020] start
		public ActionForward orderCancelReason(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		try
		{
			forward = mapping.findForward("DisplayOrderCancelReason");
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
		}
		return forward;
	}
	//[020] end
		/*
	
	/*
	 * Added by Deepak Kumar for Third Party to dispaly  View SCM Attributes
	 */
	public ActionForward getTProductLineAttmasterSCMForUpdate(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward();
		 //[017] Start
		int tpState;
		 String servicePid = request.getParameter("ServiceProductID");
		 NewOrderBean formBean=(NewOrderBean)form;
		 NewOrderModel objModel=new NewOrderModel();
		 LineItemValueDTO objLineDto=null;
		try
		{
			String state=objModel.getThirdPartyState();
			if(null!=state){
				tpState=Integer.parseInt(state);
				if(tpState==1){
					objLineDto=objModel.getSCMProgressStatus(Integer.parseInt(servicePid));
					formBean.setIsDisplayRepushBtn(AppConstants.INACTIVE_FLAG);
					if((objLineDto.getScmProgStatus()!=null)&&(objLineDto.getIsPrReuse()==0)){
						if(objLineDto.getScmProgStatus().equals("EI_FAILURE")||objLineDto.getScmProgStatus().equals("SCM_VALIDATION_FAILURE")||objLineDto.getScmProgStatus().equals("SCM_FAILURE"))
							formBean.setIsDisplayRepushBtn(AppConstants.ACTIVE_FLAG);
					}
				}
			}
		//[017] End
			forward = mapping.findForward("ViewProductCatelogSCMForUpdate");
			
			
		}
		catch (Exception e) 
		{

			Utility.LOG(true, true,"Some Error Occured in getTProductLineAttmasterSCMForUpdate method of NewOrderAction"+e);
		}
		return forward;
		
		
	}
	/*
	 * Added by Deepak Kumar for Third Party to dispaly  PrReuse Numbers
	 */
	public ActionForward getPRDetails(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception{
		
		 ActionForward forward = new ActionForward();
	     
		try
		{   
			
			forward = mapping.findForward("PRDetailsLookUp");
		}
		catch (Exception e) 
		{
			Utility.LOG(true, true,"Some Error Occured in getPRDetails method of NewOrderAction"+e);
		}
		return forward;
	}
	
	//Access Matrix-------------------------------iB2B
		public void accessMatrixiB2B(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
		{
			//,String sessionid
			NewOrderDao objDao = new NewOrderDao();
			response.setContentType("text/json");			
		
			PrintWriter obj =  response.getWriter();
			String jsonValue = "";
			try 
			{
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);

				String reqAttID=request.getParameter("attributefor");	
				String reqVal="";
				if(!String.valueOf(request.getParameter("searchval")).equalsIgnoreCase("[object Object]"))
					reqVal = request.getParameter("searchval");		
				
				jsonValue = objDao.getAccessMatrix(reqVal,reqAttID);
				obj.write(jsonValue);
				
			} catch (Exception e) {
				Utility.LOG(true, true, e, "error in accessMatrixiB2B method");
			}
			
		}   
	//Start [222] To get getOrderListForPendingWithAM for reassign to another AM
	public ActionForward getOrderListForPendingWithAM(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		NewOrderModel objModel=new NewOrderModel();
		ActionMessages messages = new ActionMessages();
		ArrayList<OrderForPendingWithAMDto> listPendingOrders = new ArrayList<OrderForPendingWithAMDto>();
		NewOrderBean objBean = (NewOrderBean)form;
		OrderForPendingWithAMDto orderForPendingWithAMDto = new OrderForPendingWithAMDto();
		try
		{
			orderForPendingWithAMDto.setEmployeeid(Integer.parseInt(request.getParameter("Employeeid")));
			orderForPendingWithAMDto.setOrderNo(request.getParameter("orderNo"));
			if(request.getParameter("crmAccountNo").equals("") || request.getParameter("crmAccountNo")== null )
				orderForPendingWithAMDto.setCrmAccountNo(0);	
			else
				orderForPendingWithAMDto.setCrmAccountNo(Integer.parseInt(request.getParameter("crmAccountNo")));
			
			if (request.getParameter("accountManager") != null && !"".equals(request.getParameter("accountManager")))
			{
				orderForPendingWithAMDto.setAccountManagerID(Long.parseLong(request.getParameter("accountManager")));
					request.setAttribute("accountManager",request.getParameter("accountManager"));
					
			}
			else
				orderForPendingWithAMDto.setAccountManagerID(0);
			
			
			PagingSorting pagingSorting = objBean.getPagingSorting();
			pagingSorting.setPagingSorting(true, true);
			pagingSorting.setDefaultifNotPresent("ORDERNO",
					PagingSorting.increment, 1);
			orderForPendingWithAMDto.setPagingSorting(pagingSorting);
			String fromPageSubmit=request.getParameter("fromPageSubmit");				
			request.setAttribute("fromPageSubmit", fromPageSubmit);
			
			
			listPendingOrders = objModel.getOrderListForPendingWithAM(orderForPendingWithAMDto);
			request.setAttribute("displayOrderListForPendingWithAM", listPendingOrders);
			objBean.setDisplayOrderListForPendingWithAM(listPendingOrders);
			forward = mapping.findForward("ReAssignToAM");
			
			
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		return forward;
	}
	
	//method to get the list of AM for Autosuggest
	public void reassignToAM(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		//,String sessionid
		NewOrderDaoExt objDao = new NewOrderDaoExt();
		response.setContentType("text/json");			
	
		PrintWriter obj =  response.getWriter();
		String jsonValue = "";
		try 
		{
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);

			
			String reqVal = request.getParameter("searchval");				
			String reqAttID=request.getParameter("attributefor");				
			jsonValue = objDao.reassignToAM(reqVal,reqAttID);
			
			//System.err.println(jsonValue);
			obj.write(jsonValue);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	//End [222]
	public ActionForward goToViewDispatchDetailsPage(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse respons) throws Exception
	{
		ActionForward forward=new ActionForward();			
		NewOrderBean formBean=(NewOrderBean)form;
		formBean.setAccountID(request.getParameter("accountId"));
		NewOrderDaoExt objDao = new NewOrderDaoExt();
		PagingDto objDto=new PagingDto();
		String inExcel = formBean.getToExcel();
		try{
		if ("true".equals(inExcel)) 
		{
			ArrayList<NewOrderDto> dispatchDetails = new ArrayList<NewOrderDto>();
			int accountID=Integer.parseInt(request.getParameter("accountId"));
			objDto.setAccountID(accountID);
			objDto.setSortBycolumn("DISPATCH_ADDRESS_CODE");
			objDto.setSortByOrder("ASC");
			objDto.setStartIndex(1);
			objDto.setEndIndex(100000);
			dispatchDetails = objDao.getDispatchDetailsWithSorting(objDto);
			request.setAttribute("dispatchDetails", dispatchDetails);
			forward=mapping.findForward("viewdDispacthDetailsPageExport");
		}
		else
		{
			forward=mapping.findForward("viewDispatchDetailsPage");
		}	
		}catch(Exception e){
			Utility.LOG(true, true,"Some Error Occured in goToViewDispatchDetailsPage method of NewOrderAction"+e);
		}
		return forward;
	}
	
	//Shubhranshu,DnC
	public ActionForward dropNCarrySelection(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse respons) throws Exception
	{
			ActionForward forward = new ActionForward(); // return value
			ActionMessages messages = new ActionMessages();
			try
			{
				forward = mapping.findForward("dropNCarrySelection");
			}
			catch (Exception e) 
			{
				AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
				messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
			}
			return forward;
	}
	public ActionForward selectHeadEndCode(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse respons) throws Exception
	{
			ActionForward forward = new ActionForward(); // return value
	
			ActionMessages messages = new ActionMessages();
			try
			{
				forward = mapping.findForward("selectHeadEndCode");
			}
			catch (Exception e) 
			{
				AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
				messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
			}
			return forward;
	}
	//Shubhranshu,DnC
}