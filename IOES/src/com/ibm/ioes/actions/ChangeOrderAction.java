package com.ibm.ioes.actions;

//Tag Name Resource Name  Date		CSR No			Description
//[001]	 ROHIT VERMA	18-Feb-11	00-05422		Zone and Region will be Fetched From Account Level
//[005]	 	Lawkush		4-March-11	00-05422		In order to make nom mandatory field white and mandatory yellow 
//[002]	 Rohit verma	3-Mar-11	00-05422		Created By And Modified by for each Transaction
//[006]	 Manisha		14-Mar-11	00-05422		Draft Change Search
//[007]	 Rohit Verma	14-Apr-11	00-05422		For Not Displaying PO Details Tab in case of Disconnection
//[00123]	 ASHUTOSH SINGH	 09-JUNE-11	00-05422	After Validation while saving main tab Appoval List is Not showing.
//[00044]	 Ashutosh Singh		23-June-11	CSR00-05422     Creating Change Order From Existing order in Billing Queue
//{008}	 Saurabh Singh	23-Aug-11	00-05422		Copy Order Functionality to be developed for Change Order
//Kalpana	15-Apr-13	HYPR09042013001		to set PM in session after attaching workflow
//[008]		Neelesh		6-June-13	TRNG22032013037     For Mulitple Opportunity
//[009]     Gunjan Singla	12-Dec-13                   Added method orderCancelReason for displaying a popup
//[110]  Anoop Tiwari        01 March 2013            Added if condition in incompleteOrder for O2C drop 2
//[011]  Anoop Tiwari        04 March 2013            setting value of isView in productCatalogforUpdate for O2C drop 2
//[012]  Neha Maheshwari     17 Feb 2014            To enable Action Button for partial initiated case after save button is pressed.
//[013] VIPIN SAHARIA 12-May-2015 20150403-R2-021204 Project Satyapan Adding ISP tagging fields to header section
//[0014] Gunjan Singla  29-May-15   SR1712831    User unable to cancel Order pending at AM
//[0015] Priya Gupta	25-Jun-15              Added ldclause column in podetails tab
//[015] RAHUL TANDON  17-July-15   SR1950983     able to disconnect and revert charge button in View mode 
//[0016] Priya Gupta    30-Jul-2015               user’s customer segment and order’s customer segment needs to match before user can view any order. 

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
import com.ibm.ioes.beans.UserInfoDto;
import com.ibm.ioes.beans.ValidationBean;
import com.ibm.ioes.dao.NewOrderDao;
import com.ibm.ioes.dao.NewOrderDaoExt;
import com.ibm.ioes.exception.IOESException;
import com.ibm.ioes.forms.ContactDTO;
import com.ibm.ioes.forms.DisconnectOrderDto;
import com.ibm.ioes.forms.Entity;
import com.ibm.ioes.forms.FieldAttibuteDTO;
import com.ibm.ioes.forms.NewOrderDto;
import com.ibm.ioes.forms.OrderHeaderDTO;
import com.ibm.ioes.forms.PagingDto;
import com.ibm.ioes.forms.PoDetailsDTO;
import com.ibm.ioes.forms.ProductCatelogDTO;
import com.ibm.ioes.forms.ServiceLineDTO;
import com.ibm.ioes.model.NewOrderModel;
import com.ibm.ioes.model.ViewOrderModel;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.AppUtility;
import com.ibm.ioes.utilities.ExcelValidator;
import com.ibm.ioes.utilities.Messages;
import com.ibm.ioes.utilities.PagingSorting;
import com.ibm.ioes.utilities.Utility;

public class ChangeOrderAction extends DispatchAction{

	
	
	
	

	//	This Method Displays Accounts for Selection in a Popup
	public ActionForward fetchAccount(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception{
		ActionForward forward = new ActionForward(); // return value
		NewOrderBean formBean=new NewOrderBean();
		NewOrderDto objDto=new NewOrderDto();
		NewOrderModel objModel=new NewOrderModel();
		ActionMessages messages = new ActionMessages();
		ArrayList<OrderHeaderDTO> listAccount = new ArrayList<OrderHeaderDTO>();
		try{
			objDto.setSearchAccount(formBean.getSearchAccount());
			listAccount = objModel.displayAccountDetails(objDto);
			request.setAttribute("AccountListBean", listAccount);
			forward = mapping.findForward("DisplaySelectAccount");
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		return forward;
	}
	
	
	public ArrayList<FieldAttibuteDTO> fetchMainDetails(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		NewOrderBean formBean=new NewOrderBean();
		NewOrderDto objDto=new NewOrderDto();
		NewOrderModel objModel=new NewOrderModel();
		ActionMessages messages = new ActionMessages();
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
//	[008]	Start
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
//	[008]		End
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
	
	//pankaj channel partner list
	
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
	
	
	//panakj end

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
		formBean.setNewFormOpened(request.getParameter("newFormOpened"));
		NewOrderDto objDto=new NewOrderDto();
		NewOrderModel objModel=new NewOrderModel();
		ActionMessages messages = new ActionMessages();
		ArrayList<OrderHeaderDTO> listAccountDetails = null;
		ArrayList<FieldAttibuteDTO> listMainDetailsWithAttributes = new ArrayList<FieldAttibuteDTO>();
		boolean errorsFound=false;
		try
		{
			if(formBean.getChannelMasterTagging().equalsIgnoreCase("0"))
			{			
				formBean.setChannelPartnerId(0);
				formBean.setFieldEngineerId(0);
				formBean.setChannelpartnerCode("");
				formBean.setChannelPartnerName("");
			}
			//[002] Start
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			long empID=Long.parseLong(objUserDto.getEmployeeId());
			// [002] End
			
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
			
			/*
			//Server Side Security Start for Stage
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(formBean.getStage(),"Stage",10),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			 */
			
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
				//start[005]
				//attributeMandatory[j]=request.getParameter("Mandatory_"+(j+1));
				attributeMandatory[j]=request.getParameter("IsRequired_"+(j+1));
				//end[005]
				//Server Side Security Start for Attributes
				if(attributeMandatory[j].equalsIgnoreCase("Y"))
				{
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
						{
							ArrayList errors=null;
							if (!"".equals(attributeVal[j]))
							{
								Object obArray[]={""+ValidationBean.VN_DATE_VALID,attributeVal[j],attributeName[j],
										new java.text.SimpleDateFormat(Messages.getMessageValue("calendar_entry_format"))};
								 errors=Utility.validateValue(new ValidationBean(obArray),
										""+Utility.CASE_VN_DATE_VALID).getCompleteMessageStrings();	
							}
						/*	Object obArray[]={""+ValidationBean.VN_DATE_VALID,attributeVal[j],attributeName[j],
									new java.text.SimpleDateFormat(Messages.getMessageValue("calendar_entry_format"))};
							ArrayList errors=Utility.validateValue(new ValidationBean(obArray),
									""+Utility.CASE_VN_DATE_VALID).getCompleteMessageStrings();*/
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
				listMainDetails=fetchMainDetails(mapping, form, request, response);
				
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
				/*
				 * Vijay saving Demo order 
				 */
				if(formBean.getIsDemoOrder()==null || formBean.getIsDemoOrder().equalsIgnoreCase("") ){
					formBean.setChkIsDemo("N");
					request.setAttribute("order_Type", "N");
				}
				else if(formBean.getIsDemoOrder() != null && formBean.getIsDemoOrder().equalsIgnoreCase("on")){
					formBean.setChkIsDemo("D");
					request.setAttribute("order_Type", "D");
				}
				/*End of saving Demo order */
				
				//formBean.setSubChangeTypeId(request.getParameter("subChangeTypeId"));
				long orderNo=objModel.insertUpdateMain(formBean,updateType,attributeVal,attributeID,empID);
				if(orderNo==0)
				{
					messages.add("saveMain",new ActionMessage("MainInstertedFailed"));
				}
				else
				{
					String modeName=request.getParameter("modeName");
					//[00044]Strat
					formBean.setSubChangeTypeId(request.getParameter("subChangeTypeId"));
					//[00044]End
					//ArrayList<NewOrderDto> projectManagerNameList = new ArrayList<NewOrderDto>();
					//ArrayList<NewOrderDto> projectManagerNameListAll = new ArrayList<NewOrderDto>();
					//projectManagerNameList=objModel.getProjectManagerNameList(Long.parseLong(formBean.getAccountID()));
					//request.setAttribute("projectManagerNameList", projectManagerNameList);
					
					//projectManagerNameListAll = objModel.getProjectManagerNameList();
					//request.setAttribute("projectManagerNameListAll", projectManagerNameListAll);
					//[007] Start
					listAccountDetails=objModel.getAccountDetails(objDto,Long.parseLong(formBean.getPoNumber()));
//					NewOrderDto objNewOrderDto = (NewOrderDto)listAccountDetails.get(0);
//					formBean.setChangeTypeID(objNewOrderDto.getChangeTypeId());
					//[007] End
					ArrayList<NewOrderDto> listRegion= new ArrayList<NewOrderDto>();
					listAccountDetails=objModel.getAccountDetails(objDto,orderNo);
					request.setAttribute("accountDetailsBean", listAccountDetails);		
					OrderHeaderDTO objNewOrderDto = (OrderHeaderDTO)listAccountDetails.get(0);
					formBean.setChangeTypeID(objNewOrderDto.getChangeTypeId());
					/*//raghu showing Sales cordinator Details
					objDto=objModel.fetchSalesPersonDetails(empID);
					formBean.setSpFirstname(objDto.getSalesFirstName());
					formBean.setSpLastName(objDto.getSalesLastName());
					formBean.setSpLPhno(String.valueOf(objDto.getSalesPhoneno()));
					formBean.setSpLEmail(objDto.getSalesEmailId());*/
					
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
					//[013] Start PROJECT SATYAPAN
					formBean.setIspTagging(listAccountDetails.get(0).getIspTagging());
					formBean.setIspLicCtgry(listAccountDetails.get(0).getIspLicCtgry());
					formBean.setIspLicDate(listAccountDetails.get(0).getIspLicDate());
					formBean.setIspLicNo(listAccountDetails.get(0).getIspLicNo());
					//[013] End PROJECT SATYAPAN
					
					formBean.setChannelMasterTagging(listAccountDetails.get(0).getChannelMasterTagging());
					formBean.setChannelPartnerId(listAccountDetails.get(0).getChannelPartnerId());
					formBean.setChannelPartnerName(listAccountDetails.get(0).getChannelPartnerName());
					formBean.setChannelpartnerCode(listAccountDetails.get(0).getChannelpartnerCode());
					formBean.setFieldEngineer(listAccountDetails.get(0).getFieldEngineer());
					formBean.setFieldEngineerId(listAccountDetails.get(0).getFieldEngineerId());
					
					formBean.setRegionId(String.valueOf((listAccountDetails.get(0).getRegionId())));
					formBean.setZoneId(String.valueOf((listAccountDetails.get(0).getZoneId())));
					listMainDetailsWithAttributes=objModel.getMainDetails(objDto,orderNo);
					request.setAttribute("MainDetailsWithAttributesBean", listMainDetailsWithAttributes);
					if(listAccountDetails.get(0).getContactCount() == 1)
					{
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
					messages.add("saveMain",new ActionMessage("MainInstertedSuccess"));
					
					
					//get Approval List after validation 
					//[00123] start
					request.setAttribute("approvalEditMode",modeName);
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
					//[012] Start
					if(String.valueOf(listAccountDetails.get(0).getStageName()).equalsIgnoreCase(AppConstants.ORDER_STAGE_PARTIAL_INITIATED)){
						formBean.setOrderOwner(String.valueOf(roleId));
					}
					else{
						formBean.setOrderOwner(owner);
					}
					//[012] End
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
		forward=mapping.findForward("ChangeOrderUpdateDisplay");
		return forward;
	}
	
	//	Method used for inserting CONTACT Details in tPOmaster,tPOADDRESS for CONTACT Tab
	public ActionForward insertContactTabDetail(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception
	{
		String forwardMapping = null;
		NewOrderBean formBean=(NewOrderBean)form;
		formBean.setNewFormOpened(request.getParameter("newFormOpened"));
		NewOrderDto objDto=new NewOrderDto();
		NewOrderModel objModel=new NewOrderModel();
		ActionMessages messages = new ActionMessages();
		ArrayList<OrderHeaderDTO> listAccountDetails = null;
		ArrayList<FieldAttibuteDTO> listMainDetailsWithAttributes =null;
		boolean errorsFound=false;
		String modeName=request.getParameter("modeName");
		try
		{
			//[002] Start
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			long empID=Long.parseLong(objUserDto.getEmployeeId());
			// [002] End
			
			int updateFlag=Integer.parseInt(formBean.getContactUpdateType());
			int count=(Integer.parseInt(request.getParameter("hdnAddressCount"))-1);
			String[] contactId=new String [count];
			String[] contactType=new String [count];
			String[] contactTypeId=new String [count];
			String[] SalutationName=new String [count];
			String[] FName=new String [count];
			String[] LName=new String [count];
			String[] CntEmail=new String [count];
			String[] ContactCell=new String [count];
			String[] ContactFax=new String [count];
			
			contactId =request.getParameterValues("contactId");
			contactType =request.getParameterValues("contactType");
			contactTypeId =request.getParameterValues("contactTypeId");
			SalutationName =request.getParameterValues("salutationName");
			FName =request.getParameterValues("firstName");
			LName =request.getParameterValues("lastName");
			CntEmail =request.getParameterValues("cntEmail");
			ContactCell =request.getParameterValues("contactCell");
			ContactFax =request.getParameterValues("contactFax");
			
			
			Map<String, String[]> contactMap= new HashMap<String, String[]>();
			contactMap.put("contactId", contactId);
			contactMap.put("contactType", contactType);
			contactMap.put("contactTypeId", contactTypeId);
			contactMap.put("SalutationName", SalutationName);
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
		     //[00] Start
			//int status=objModel.insertContact(formBean,addressMap,contactMap,Address1.length,updateFlag);
		     
		   //[013] Start PROJECT SATYAPAN
				formBean.setIspLicDate(request.getParameter("ispLicDate"));
				//[013] End PROJECT SATYAPAN
		     
		     int status=objModel.insertContact(formBean,addressMap,contactMap,Address1.length,updateFlag,empID);
		     //[002] End
			if(status==1)
			{
				/*CONTACTS TAB STARTS*/
				ArrayList<ContactDTO> listContactDetails= null;
				ArrayList<NewOrderDto> listAddressDetails= new ArrayList<NewOrderDto>();
				ArrayList<OrderHeaderDTO> listRegion=null;
				listContactDetails=objModel.getContactDetails(Long.parseLong(formBean.getPoNumber()));
				listAddressDetails=objModel.getAddressDetails(Long.parseLong(formBean.getPoNumber()));
				request.setAttribute("listContactDetails", listContactDetails);
				request.setAttribute("listAddressDetails", listAddressDetails);
				/*CONTACTS TAB CLOSED*/
				/*MAIN TAB STARTS*/
				ArrayList<OrderHeaderDTO> projectManagerNameList = new ArrayList<OrderHeaderDTO>();
				projectManagerNameList=objModel.getProjectManagerNameList(Long.parseLong(formBean.getAccountID()));
				request.setAttribute("projectManagerNameList", projectManagerNameList);
				listAccountDetails=objModel.getAccountDetails(objDto,Long.parseLong(formBean.getPoNumber()));
				formBean.setProjectManager(String.valueOf(listAccountDetails.get(0).getProjectManagerID()));
				//lawkush
				formBean.setProjectManagerAssigned(String.valueOf(listAccountDetails.get(0).getProjectManagerID()));
				//lawkush
				//[013] Start PROJECT SATYAPAN
				formBean.setIspTagging(listAccountDetails.get(0).getIspTagging());
				formBean.setIspLicCtgry(listAccountDetails.get(0).getIspLicCtgry());
				formBean.setIspLicDate(listAccountDetails.get(0).getIspLicDate());
				formBean.setIspLicNo(listAccountDetails.get(0).getIspLicNo());
				//[013] End PROJECT SATYAPAN
				request.setAttribute("accountDetailsBean", listAccountDetails);
				ArrayList<NewOrderDto> projectManagerNameListAll = new ArrayList<NewOrderDto>();
				projectManagerNameListAll = objModel.getProjectManagerNameList();
				request.setAttribute("projectManagerNameListAll", projectManagerNameListAll);
				//[007] Start
				OrderHeaderDTO objNewOrderDto = (OrderHeaderDTO)listAccountDetails.get(0);
				formBean.setChangeTypeID(objNewOrderDto.getChangeTypeId());
				//[007] End
				
				
				/*
				 * set ispmPresent 
				 */
				
				//System.out.println("listAccountDetails.get(0).getChannelMasterTagging()"+listAccountDetails.get(0).getChannelMasterTagging());
				formBean.setChannelMasterTagging(listAccountDetails.get(0).getChannelMasterTagging());
				formBean.setChannelPartnerId(listAccountDetails.get(0).getChannelPartnerId());
				formBean.setChannelPartnerName(listAccountDetails.get(0).getChannelPartnerName());
				formBean.setChannelpartnerCode(listAccountDetails.get(0).getChannelpartnerCode());
				formBean.setFieldEngineer(listAccountDetails.get(0).getFieldEngineer());
				formBean.setFieldEngineerId(listAccountDetails.get(0).getFieldEngineerId());
				
				request.setAttribute("ispmpresent", String.valueOf(listAccountDetails.get(0).getIsPMPresent()));
				
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
				
//				get Approval List after validation 
				//[00123] start
				request.setAttribute("approvalEditMode",modeName);
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
				//[012] Start
				if(String.valueOf(listAccountDetails.get(0).getStageName()).equalsIgnoreCase(AppConstants.ORDER_STAGE_PARTIAL_INITIATED)){
					formBean.setOrderOwner(String.valueOf(roleId));
				}
				else{
					formBean.setOrderOwner(owner);
				}
				//[012] End
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
	//	Fetch All Salutation List
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
	//	Fetch Country List
	/*public ActionForward fetchCountryList(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		NewOrderModel objModel=new NewOrderModel();
		ActionMessages messages = new ActionMessages();
		ArrayList<NewOrderDto> listCountry = new ArrayList<NewOrderDto>();
		try
		{
			String count=request.getParameter("counter");
			listCountry = objModel.getCountryList();
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
	}*/
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
			String countyCode=request.getParameter("countyCode");
			frmDto.setCountyCode(Integer.parseInt(request.getParameter("countyCode")));
			listState = objModel.getStateList(frmDto);
			request.setAttribute("count", count);
			request.setAttribute("countyCode", countyCode);
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
	/*public ActionForward fetchCityList(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
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
			String stateCode=request.getParameter("stateCode");
			request.setAttribute("stateCode", stateCode);
			frmDto.setStateCode(Integer.parseInt(request.getParameter("stateCode")));
			listCity = objModel.getCityList(frmDto);
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
	}*/
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
			objDto.setOrderNumber(Integer.parseInt(request.getParameter("poNumber")));
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
		formBean.setNewFormOpened(request.getParameter("newFormOpened"));
		NewOrderModel objModel=new NewOrderModel();
		ActionMessages messages = new ActionMessages();
		formBean.setOrderNo(formBean.getPoNumber());
		NewOrderDto objDto=new NewOrderDto();
		Entity entityData = new Entity();
		try
		{
			//[002] Start
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			long empID=Long.parseLong(objUserDto.getEmployeeId());
			// [002] End
			int count=(Integer.parseInt(request.getParameter("hdnPOCount")));
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
			//[0015]
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
				//[0015]
				ldClause =request.getParameterValues("ldClause");
				
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
				//[0015]
				poMap.put("ldClause", ldClause);
				
				updateFlag=Integer.parseInt(formBean.getPoUpdateType());
				//[002] Start
				//status=objModel.insertPO(formBean,poMap,count,updateFlag);
				
				//[013] Start PROJECT SATYAPAN
				formBean.setIspLicDate(request.getParameter("ispLicDate"));
				//[013] End PROJECT SATYAPAN
				
				status=objModel.insertPO(formBean,poMap,count,updateFlag,empID);
				//[002] End
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
				ArrayList<OrderHeaderDTO> listAccountDetails = null;
				ArrayList<NewOrderDto> projectManagerNameListAll = new ArrayList<NewOrderDto>();
				projectManagerNameListAll = objModel.getProjectManagerNameList();
				request.setAttribute("projectManagerNameListAll", projectManagerNameListAll);
				ArrayList<FieldAttibuteDTO> listMainDetailsWithAttributes = null;
				listAccountDetails=objModel.getAccountDetails(objDto,Long.parseLong(formBean.getPoNumber()));
				formBean.setProjectManager(String.valueOf(listAccountDetails.get(0).getProjectManagerID()));
				//lawkush
				formBean.setProjectManagerAssigned(String.valueOf(listAccountDetails.get(0).getProjectManagerID()));
				//lawkush
				//[013] Start PROJECT SATYAPAN
				formBean.setIspTagging(listAccountDetails.get(0).getIspTagging());
				formBean.setIspLicCtgry(listAccountDetails.get(0).getIspLicCtgry());
				formBean.setIspLicDate(listAccountDetails.get(0).getIspLicDate());
				formBean.setIspLicNo(listAccountDetails.get(0).getIspLicNo());
				//[013] End PROJECT SATYAPAN
				request.setAttribute("accountDetailsBean", listAccountDetails);
				
				/*
				 * set ispmPresent 
				 */
				
				formBean.setChannelMasterTagging(listAccountDetails.get(0).getChannelMasterTagging());
				formBean.setChannelPartnerId(listAccountDetails.get(0).getChannelPartnerId());
				formBean.setChannelPartnerName(listAccountDetails.get(0).getChannelPartnerName());
				formBean.setChannelpartnerCode(listAccountDetails.get(0).getChannelpartnerCode());
				formBean.setFieldEngineer(listAccountDetails.get(0).getFieldEngineer());
				formBean.setFieldEngineerId(listAccountDetails.get(0).getFieldEngineerId());
				
				request.setAttribute("ispmpresent", String.valueOf(listAccountDetails.get(0).getIsPMPresent()));
				
				listRegion=objModel.getRegionList();
				request.setAttribute("listRegion", listRegion);
				listMainDetailsWithAttributes=objModel.getMainDetails(objDto,Long.parseLong(formBean.getPoNumber()));
				request.setAttribute("MainDetailsWithAttributesBean", listMainDetailsWithAttributes);
				/*MAIN TAB CLOSED*/	
				//get Approval List after validation 
				//[00123] start
				request.setAttribute("approvalEditMode",modeName);
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
				strOrderPublishBillingTrigger=objViewOrderModel.fnIsOrderPublishedBillingTrigger(orderNumber , roleId);
				
				String[] strArr=new String[1];
				strArr=strOrderPublishBillingTrigger.split("@@");
				formBean.setIsOrderPublished(strArr[0]);
				formBean.setIsBillingTriggerReady(strArr[1]);
				
				
				ArrayList aList=objViewOrderModel.getTaskListOfOrder(orderNumber);
				String owner = new Utility().getOrderOwnedBy(aList);
				//[012] Start
				if(String.valueOf(listAccountDetails.get(0).getStageName()).equalsIgnoreCase(AppConstants.ORDER_STAGE_PARTIAL_INITIATED)){
					formBean.setOrderOwner(String.valueOf(roleId));
				}
				else{
					formBean.setOrderOwner(owner);
				}
				//[012] End
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
				//[00123] END
				int flag=(Integer.parseInt(request.getParameter("flag")));
				request.setAttribute("flag",flag);
				
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
				ArrayList<OrderHeaderDTO> listAccountDetails = new ArrayList<OrderHeaderDTO>();
				ArrayList<FieldAttibuteDTO> listMainDetailsWithAttributes = null;
				listAccountDetails=objModel.getAccountDetails(objDto,Long.parseLong(formBean.getPoNumber()));
				formBean.setProjectManager(String.valueOf(listAccountDetails.get(0).getProjectManagerID()));
				//lawkush
				formBean.setProjectManagerAssigned(String.valueOf(listAccountDetails.get(0).getProjectManagerID()));
				//lawkush
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
			ArrayList<OrderHeaderDTO> listAccountDetails = null;
			ArrayList<FieldAttibuteDTO> listMainDetailsWithAttributes = null;
			listAccountDetails=objModel.getAccountDetails(objDto,Long.parseLong(formBean.getPoNumber()));
			formBean.setProjectManager(String.valueOf(listAccountDetails.get(0).getProjectManagerID()));
			//lawkush	
			formBean.setProjectManagerAssigned(String.valueOf(listAccountDetails.get(0).getProjectManagerID()));
			//lawkush
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
		String hdnSubChangeTypeID;
		try
		{
			long serviceNo=Long.parseLong(request.getParameter("hdnserviceid"));
			long serviceTypeId=Long.parseLong(request.getParameter("hdnserviceTypeId"));
			productAttDetailList = objModel.getTProductAttDetail(serviceNo,serviceTypeId);
			request.setAttribute("serviceTypeId", serviceTypeId);
			hdnSubChangeTypeID=request.getParameter("hdnSubChangeTypeID");	
			request.setAttribute("hdnSubChangeTypeList", hdnSubChangeTypeID);
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
	
	public ActionForward getServiceTreeview(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
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
			//[007] Start
			OrderHeaderDTO objNewOrderDto = (OrderHeaderDTO)listAccountDetails.get(0);
			formBean.setChangeTypeID(objNewOrderDto.getChangeTypeId());
			//[007] End
			request.setAttribute("ispmpresent", String.valueOf(listAccountDetails.get(0).getIsPMPresent()));//added by kalpana to set PM in session after attaching workflow bug ID HYPR09042013001
			listRegion=objModel.getRegionList();
			request.setAttribute("listRegion", listRegion);

			ArrayList<OrderHeaderDTO> projectManagerNameList = new ArrayList<OrderHeaderDTO>();
			projectManagerNameList=objModel.getProjectManagerNameList(listAccountDetails.get(0).getAccountID());
			request.setAttribute("projectManagerNameList", projectManagerNameList);
			formBean.setProjectManager(String.valueOf(listAccountDetails.get(0).getProjectManagerID()));
			
			//lawkush
			formBean.setProjectManagerAssigned(String.valueOf(listAccountDetails.get(0).getProjectManagerID()));
			//lawkush
			
			listMainDetailsWithAttributes=objModel.getMainDetails(objDto,orderNo);
			ArrayList<NewOrderDto> projectManagerNameListAll = new ArrayList<NewOrderDto>();
			projectManagerNameListAll = objModel.getProjectManagerNameList();
			request.setAttribute("projectManagerNameListAll", projectManagerNameListAll);
			request.setAttribute("MainDetailsWithAttributesBean", listMainDetailsWithAttributes);
			ArrayList<ContactDTO> listContactDetails= null;
			ArrayList<NewOrderDto> listAddressDetails= new ArrayList<NewOrderDto>();
			listContactDetails=objModel.getContactDetails(Long.parseLong(formBean.getPoNumber()));
			listAddressDetails=objModel.getAddressDetails(Long.parseLong(formBean.getPoNumber()));
			request.setAttribute("listContactDetails", listContactDetails);
			request.setAttribute("listAddressDetails", listAddressDetails);
			/*MAIN TAB PO DETAILS START*/
			ArrayList<PoDetailsDTO> listPODetails= null;
			listPODetails=objModel.getPODetails(Long.parseLong(formBean.getPoNumber()));
			request.setAttribute("listPoDetails", listPODetails);
			/*MAIN TAB PO DETAILS ENDS*/
			
//			get Approval List after validation 
			//[00123] start
			request.setAttribute("approvalEditMode",modeName);
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
			strOrderPublishBillingTrigger=objViewOrderModel.fnIsOrderPublishedBillingTrigger(orderNumber , roleId);
			
			String[] strArr=new String[1];
			strArr=strOrderPublishBillingTrigger.split("@@");
			formBean.setIsOrderPublished(strArr[0]);
			formBean.setIsBillingTriggerReady(strArr[1]);
			
			
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
			//[00123] END
			request.setAttribute("checkedServiceNumber",checkedServiceNumber);
			int flag=(Integer.parseInt(request.getParameter("flag")));
			request.setAttribute("flag",flag);
			
			
			forward=mapping.findForward("ChangeOrderUpdateDisplay");
			
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
		ArrayList<ProductCatelogDTO> serviceTypeList = null;
		ServiceLineDTO objDto=new ServiceLineDTO();
		try
		{
			Utility.SysOut(request.getParameter("ServiceTypeID"));
			objDto.setServiceTypeId(Integer.parseInt(request.getParameter("ServiceTypeID")));//FOR WHETHER PRODUCT IS ASAT OR ISP
			objDto.setProductName(request.getParameter("ProductName"));
			objDto.setServiceName(request.getParameter("ServiceName"));
			objDto.setOrderNumber(0);
			serviceTypeList = objModel.getServiceType(objDto);
			request.setAttribute("serviceTypeList", serviceTypeList);
			request.setAttribute("LogicalSI", request.getParameter("LogicalSI"));
			request.setAttribute("subChangeTypeId", request.getParameter("subChangeTypeId"));
			forward = mapping.findForward("ProductCatelogForUpdate");
			/*[011] start*/
			String serviceID=request.getParameter("ServiceNO");
			int roleId=Integer.parseInt(request.getParameter("roleId"));
			boolean servicePrsent=objModel.CheckServicePresent(serviceID,  roleId);
			if(!servicePrsent)
				request.setAttribute("isView",1);
			/*[011] end*/
			
			//Start[015] getting value of isView from requested URl 
			String isViewValueFromURL=request.getParameter("isView");
			if("1".equals(isViewValueFromURL)){
				request.setAttribute("isView",1);
			}
			//End[015]
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
		String forwardMapping = null;
		NewOrderModel objModel=new NewOrderModel();
		NewOrderDto objDto = new NewOrderDto();
		ArrayList<OrderHeaderDTO> listAccountDetails = new ArrayList<OrderHeaderDTO>();
		ArrayList<FieldAttibuteDTO> listMainDetailsWithAttributes = null;
		NewOrderBean formBean=(NewOrderBean)form;
		long orderNo=0;
		String modeName=null;
		NewOrderDao objDao = new NewOrderDao();
		boolean orderViewMode = false;
		String billingTriggerEnableDisable=null;
		try{
			HttpSession session = request.getSession();	
			UserInfoDto objUserDto  = (UserInfoDto)session.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			long roleId=Long.parseLong(objUserDto.getUserRoleId());
			orderNo=Long.parseLong(request.getParameter("orderNo"));
			modeName=request.getParameter("modeName");
			listAccountDetails=objModel.getAccountDetails(objDto,orderNo);
			request.setAttribute("accountDetailsBean", listAccountDetails);
			
			//Vijay. Get order_type for recognize the demo order or not and set object in request 
			formBean.setChkIsDemo(listAccountDetails.get(0).getChkIsDemo());
			request.setAttribute("objFormDemo", formBean);
			//Vijay end.
			
			//[013] Start PROJECT SATYAPAN
			formBean.setIspTagging(listAccountDetails.get(0).getIspTagging());
			formBean.setIspLicCtgry(listAccountDetails.get(0).getIspLicCtgry());
			formBean.setIspLicDate(listAccountDetails.get(0).getIspLicDate());
			formBean.setIspLicNo(listAccountDetails.get(0).getIspLicNo());
			//[013] End PROJECT SATYAPAN
			
			formBean.setChannelMasterTagging(listAccountDetails.get(0).getChannelMasterTagging());
			formBean.setChannelPartnerId(listAccountDetails.get(0).getChannelPartnerId());
			formBean.setChannelPartnerName(listAccountDetails.get(0).getChannelPartnerName());
			formBean.setChannelpartnerCode(listAccountDetails.get(0).getChannelpartnerCode());
			formBean.setFieldEngineer(listAccountDetails.get(0).getFieldEngineer());
			formBean.setFieldEngineerId(listAccountDetails.get(0).getFieldEngineerId());
				
			//Changes Made By Sumit For PM to be Displayed only in Case of PM Present in Workflow :: 20-Oct-2011 :: -->
			request.setAttribute("ispmpresent", String.valueOf(listAccountDetails.get(0).getIsPMPresent()));
			//Changes Made By Sumit For PM to be Displayed only in Case of PM Present in Workflow :: 20-Oct-2011 :: -->

			OrderHeaderDTO objNewOrderDto = (OrderHeaderDTO)listAccountDetails.get(0);
			formBean.setChangeTypeID(objNewOrderDto.getChangeTypeId());
			
			if(("AM".equalsIgnoreCase(listAccountDetails.get(0).getStageName())) || ( AppConstants.ORDER_STAGE_PARTIAL_INITIATED.equalsIgnoreCase(listAccountDetails.get(0).getStageName()) && roleId==1)){
			ArrayList<NewOrderDto> projectManagerNameListAll = new ArrayList<NewOrderDto>();
			projectManagerNameListAll = objModel.getProjectManagerNameList();
			request.setAttribute("projectManagerNameListAll", projectManagerNameListAll);
			}
			request.setAttribute("approvalEditMode",modeName);
			formBean.setProjectManager(String.valueOf(listAccountDetails.get(0).getProjectManagerID()));
			//lawkush
			formBean.setProjectManagerAssigned(String.valueOf(listAccountDetails.get(0).getProjectManagerID()));
			//lawkush
			formBean.setCurrencyID(String.valueOf(listAccountDetails.get(0).getCurrencyID()));
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
			if(listAccountDetails.get(0).getPoCount() == 1){
				ArrayList<PoDetailsDTO> listPODetails= null;
				listPODetails=objModel.getPODetails(orderNo);
				request.setAttribute("listPoDetails", listPODetails);
			}
			/*MAIN TAB PO DETAILS ENDS*/
			List<LabelValueBean> lstTo = new ArrayList<LabelValueBean>();
			ViewOrderModel objViewOrderModel = new ViewOrderModel();
			lstTo.add(new LabelValueBean("Account Manager","1"));
			lstTo.add(new LabelValueBean("Project Manager","2"));
			lstTo.add(new LabelValueBean("CPOC","3"));
	      //[003] Start		
			String strOrderPublishBillingTrigger=null;
			//Meenakshi Start: Tunning for order load
			if(!("NEW".equalsIgnoreCase(listAccountDetails.get(0).getStageName()))){
				strOrderPublishBillingTrigger=objViewOrderModel.fnIsOrderPublishedBillingTrigger(orderNo , roleId);
			
				String[] strArr=new String[1];
				strArr=strOrderPublishBillingTrigger.split("@@");
				formBean.setIsOrderPublished(strArr[0]);
				billingTriggerEnableDisable=strArr[1];
				if(AppConstants.ORDER_STAGE_PARTIAL_INITIATED.equalsIgnoreCase(listAccountDetails.get(0).getStageName())){
					String btRoleId = new NewOrderDaoExt().getRoleID(AppConstants.BT_SHORT_CODE);
					if(null != btRoleId && !"".equals(btRoleId.trim()) && roleId == Long.valueOf(btRoleId)){
						formBean.setIsBillingTriggerReady(strArr[1]);
					}
				}else{
					formBean.setIsBillingTriggerReady(strArr[1]);
				}
			
				//formBean.setIsBillingTriggerReady(strArr[1]);
			}
       //[003] End		
//			Meenakshi Start: Tunning for order load
			ArrayList aList=objViewOrderModel.getTaskListOfOrder(orderNo);
			if(!("NEW".equalsIgnoreCase(listAccountDetails.get(0).getStageName()))){
				//Puneet commenting as it is not getting used
				//ArrayList aListHistory=objViewOrderModel.getTaskListHistory(orderNo);
			request.setAttribute("taskListOfOrder", aList);
			/*	String owner = new Utility().getOrderOwnedBy(aList);
				formBean.setOrderOwner(owner);*/
			}
			//ArrayList aListHistory=objViewOrderModel.getTaskListHistory(orderNo);
			//request.setAttribute("taskListHistoryOfOrder", aListHistory);
			//[110] start
			if(!("NEW".equalsIgnoreCase(listAccountDetails.get(0).getStageName()))){
				boolean servicePrsent=objDao.isServicesPresentInUserBin(orderNo,String.valueOf(roleId));
				boolean editMode_AM_NoService=false;
				//[0014]] start
				if(servicePrsent==false && "AM".equalsIgnoreCase(listAccountDetails.get(0).getStageName()) && roleId==1 ){
					editMode_AM_NoService=true;
				}
				//[0014] end
				//condition=editMode_AM added by [0014]
				if(servicePrsent || editMode_AM_NoService){ 
					formBean.setOrderOwner(String.valueOf(roleId));
				}else{
					boolean roleInWF = Utility.isRolePrsentInWorkFlow(Integer.valueOf(objUserDto.getUserRoleId()), aList);
					if(roleInWF){
						orderViewMode = true;
						/*
						 * Below code is written, because earlier in case of view order, 
						 * if order stage is partial initiated, then billing trigger button was not showing
						 * even if flag was coming as enable.
						 * Now handled that situation by capturing billingTriggerFlag and setting that value in bean, 
						 * once it is in viewMode
						*/
						if(!billingTriggerEnableDisable.equals(null))
							formBean.setIsBillingTriggerReady(billingTriggerEnableDisable);
					}/*else{
						
						 * If any of the service is not present in current user's bin and current role is also not present in work flow
						
						orderViewMode = true;
					}*/
				}
			}
			//[110] end
			request.setAttribute("showAttachIcon", "YES");
			String m6Status=request.getParameter("m6Status");
			if("1".equals(m6Status)){
				request.setAttribute("m6StatusCode", "1");
			}else if("-1".equals(m6Status)){
				request.setAttribute("m6StatusCode", "-1");
			}
			formBean.setHdnOrderCreationSource(listAccountDetails.get(0).getOrder_creation_source());
//			===========================Checking View or Edit Mode===================================

			
			String isEditMode = objDao.checkForOrderAlready(orderNo,objUserDto.getUserId(),roleId,0);
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
			if(!isEditMode.equalsIgnoreCase("0") || orderViewMode)
				forwardMapping="DisplayViewChangeOrder";
			else{
				objDao.checkForOrderAlready(orderNo,objUserDto.getUserId(),roleId,1);
				forwardMapping="ChangeOrderUpdateDisplay";
			}
			
		}catch(Exception ex){
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
//			AppConstants.IOES_LOGGER.info("saveUploadedFileInfo() started");
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
				
				AppConstants.IOES_LOGGER.info("Completed..");
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

	/**
	 * Method to return a list of available entities retreived from DB
	 * @return
	 */
	public ArrayList getEntityList() {
		NewOrderModel objModel=new NewOrderModel();
		Entity entityData = new Entity();
		return objModel.getEntityList(entityData);
	}

	
	//Rakshika
	
	public ActionForward fetchServiceNProduct(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		forward = mapping.findForward("DisplayServiceNProduct");
		NewOrderBean formBean=new NewOrderBean();
		NewOrderDto objDto=new NewOrderDto();
		//NewOrderModel objModel=new NewOrderModel();
		ActionMessages messages = new ActionMessages();
		//ArrayList<NewOrderDto> listAccount = new ArrayList<NewOrderDto>();
		try
		{
			objDto.setLogicalSINo(formBean.getLogicalSINo());
			objDto.setServiceName(formBean.getServiceName());
			objDto.setChangeType(formBean.getChangeType());
			String roleID=request.getParameter("roleID");
			request.setAttribute("roleID", roleID);
		//	listAccount = objModel.displayAccountDetails(objDto);
			//request.setAttribute("AccountListBean", listAccount);
			forward = mapping.findForward("DisplayServiceNProduct");
				
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		return forward;
	}
	//[00044] Start 
	public ActionForward fetchOrderForChange(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		forward = mapping.findForward("displayOrderForChange");		
		return forward;
	}
	//[00044] End

//		This Method Displays LogicalSINumber for Selection in a Popup
		public ActionForward getLogicalSINumber(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
		{
			ActionForward forward = new ActionForward(); // return value
			ActionMessages messages = new ActionMessages();
			ArrayList<NewOrderDto> listLogicalSINumber = new ArrayList<NewOrderDto>();
			ArrayList<NewOrderDto> listOrderStageName = new ArrayList<NewOrderDto>();
			NewOrderModel objModel=new NewOrderModel();
			NewOrderDto objDto=new NewOrderDto();
			NewOrderBean objForm=(NewOrderBean) form;
			try
			{
				String searchOrderStage ="";
				if(objForm !=null && objForm.getSearchOrderStage()!=null
						&& (! objForm.getSearchOrderStage().equals("")) ){
					searchOrderStage=objForm.getSearchOrderStage();
				}
				else
				{
					searchOrderStage = "Completed";
					objForm.setSearchOrderStage("Completed");
				}
				/*String accountID =request.getParameter("accountID") ;
				if(logicalSiNumber.equalsIgnoreCase("")||logicalSiNumber==null )
				{
					logicalSiNumber="";
				}
				listLogicalSINumber = objModel.getLogicalSINumber(Long.parseLong(accountID),logicalSiNumber);*/
				request.setAttribute("listLogicalSINumber", listLogicalSINumber);
				
				/* vijay
				 * addd a list of stage
				 */
				listOrderStageName=objModel.displayOrderStage(objDto);
				
				//Vijay deleting some junk values
				for (Iterator iter = listOrderStageName.iterator(); iter
					.hasNext();) {
					NewOrderDto stageElement = (NewOrderDto) iter.next();
					if(stageElement.getSearchOrderStageName().equals("Complete")){
						iter.remove();
					}
					if(stageElement.getSearchOrderStageName().equals("NEW")){
						iter.remove();
					}
				}
				
				request.setAttribute("listOrderStageName", listOrderStageName);
				
				forward = mapping.findForward("DisplaySINumber");
			}
			catch (Exception e) 
			{
				AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
				messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
			}
			return forward;
		}
	
		public ActionForward rateRenewal(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
		{
			ActionForward forward = new ActionForward(); // return value
			try
			{
				forward = mapping.findForward("RateRenewal");
			}
			catch (Exception e) 
			{
				AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			}
			return forward;
		}
		/*public ActionForward showChargesRenewal(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
		{
			ActionForward forward = new ActionForward(); // return value
			
			try
			{
				
				
				forward = mapping.findForward("ChargesRenewalPage");
			}
			catch (Exception e) 
			{
				AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
				
			}
			return forward;
		}*/
		
		/*
		 * Function Name:- 		attachWorklow
		 * Creation Date:- 		13-Jan-2011
		 * Created By:- 		Sumit Arora
		 * Purpose:- 			To Open A window if more than 2 workflow are attached on the order.
		 * 						And Option for Selecting a single workflow which would be attached with a cahnage order. 
		 * Struts Entry Tag :-  displayAttachWorkflow
		 * Form Bean Used	:- 	NewOrderBean
		 * 
		 */
		public ActionForward attachWorklow(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
		{
			ActionForward forward = new ActionForward(); // return value
			NewOrderBean formBean=new NewOrderBean();
			NewOrderDto objDto=new NewOrderDto();
			NewOrderModel objModel=new NewOrderModel();
			ActionMessages messages = new ActionMessages();
			ArrayList<OrderHeaderDTO> listChangeSubtype = new ArrayList<OrderHeaderDTO>();
			String initiatedToFlag=null;
			try
			{
				objDto.setHdnOrderNo(request.getParameter("changeOrderNo")); 
				objDto.setRoleId(request.getParameter("roleName"));
				listChangeSubtype = objModel.getChangeOrderSubTypeAttached(objDto.getHdnOrderNo());
				request.setAttribute("listChangeSubtype", listChangeSubtype);
				forward = mapping.findForward("displayAttachWorkflow");
			}
			catch (Exception e) 
			{
				AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
				messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
			}
			return forward;
		}
		// for incomplete order List
		//006 start
		
		public ArrayList<NewOrderDto> viewOrderList_Change(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {

			String forwardMapping = "";
			ArrayList<NewOrderDto> orderList = new ArrayList<NewOrderDto>();
			NewOrderModel objService = new NewOrderModel();
			//NewOrderBean objForm = (NewOrderBean) form;
			DefaultBean objForm = (DefaultBean) form;
			ArrayList<OrderHeaderDTO> listSourceName =null;
			ArrayList<OrderHeaderDTO> listCurrency =null;
			NewOrderDto objDto = new NewOrderDto();
			try {
				// Validate form fields
				ActionMessages messages = new ActionMessages();
				String searchCRMOrder = objForm.getSearchCRMOrder();
				String searchAccountNo  = objForm.getSearchAccountNo();
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
								"CRM/O No",searchCRMOrder , 18),
						Utility.generateCSV(Utility.CASE_MAXLENGTH,
								Utility.CASE_DIGITS_ONLY)).appendToAndRetNewErrs(
						errors);
				Utility.validateValue(
						new ValidationBean().loadValidationBean_Maxlength(
								"A/C No",searchAccountNo , 18),
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
								"ORDER TYPE",searchOrderType , 200),
						Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS,
								Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
						errors);
				
				
				
				Utility.validateValue(
						new ValidationBean().loadValidationBean_Maxlength(
								"FROM DATE",searchAccountName , 200),
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
						Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS,
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
					//return mapping.findForward("displayOrderList_Change");
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

					orderList = objService.viewOrderList_change(objDto);
				
				
					
				}
			} catch (Exception ed) {
				ed.printStackTrace();
				AppConstants.IOES_LOGGER.error("Error while getting saveUploadedFileInfo "
						+ ed.getMessage());
				throw new IOESException(ed);
			}

			return orderList;
		}
		
		
		//006 end
		
		//[008]	Start
		public ActionForward getOrderToBeCopied(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
		{
			ActionForward forward = new ActionForward(); // return value
			try
			{
				forward = mapping.findForward("DisplayChangeCopyOrderPage");
			}
			catch (Exception e) 
			{
				AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			}
			return forward;
		}
		//[010]  End
		
		

		
		public ArrayList<DisconnectOrderDto> viewPDOrderList(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {

			ArrayList<DisconnectOrderDto> PDorderList = new ArrayList<DisconnectOrderDto>();
			NewOrderModel objService = new NewOrderModel();
			DefaultBean objForm = (DefaultBean) form;
			DisconnectOrderDto objDto = new DisconnectOrderDto();
			//[0016] starts
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			
			//[0016] ends
			try {
				// Validate form fields
				ActionMessages messages = new ActionMessages();
				String searchCRMOrder = objForm.getSearchCRMOrder();
			
				String searchAccountNo  = objForm.getSearchAccountNo();
				String searchAccountName=objForm.getSearchAccountName();
				searchAccountName = (searchAccountName != null) ? searchAccountName.trim() : searchAccountName;
				String searchfromDate=objForm.getSearchfromDate();
				String searchToDate=objForm.getSearchToDate();
				String searchSrNo=objForm.getSearchSRNO();
				String searchLSI=objForm.getSearchLSI();
				String searchfromdisdate=objForm.getSearchfromdisc_date();
				String searchTodisdate=objForm.getSearchTodisc_date();
				
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
								"SR NO",searchSrNo , 100),
								Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS,
										Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
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
					request.setAttribute("PDorderList", PDorderList);
					objForm.setPDorderList(PDorderList);
					request.setAttribute("validation_errors", errors);
					saveMessages(request, messages);
					//return mapping.findForward("displayOrderList_Change");
				}
				// ******* Validation Ends Here
				// *************************************
				else {
					objDto.setSearchCRMOrder(searchCRMOrder);
				
					objDto.setSearchAccountNo(searchAccountNo);
					objDto.setSearchAccountName(searchAccountName);
					objDto.setSearchfromDate(searchfromDate);
					objDto.setSearchToDate(searchToDate);
					objDto.setSearchSRNO(searchSrNo);
			        objDto.setSearchLSI(searchLSI);
			        objDto.setSearch_from_dis_date(searchfromdisdate);
			        objDto.setSearch_to_dis_date(searchTodisdate);
			        PagingSorting pagingSorting = objForm.getPagingSorting();
			        pagingSorting.setDefaultifNotPresent("ORDERNO",
							PagingSorting.increment, 1);
					pagingSorting.setPagingSorting(true, true);
					//[0016]
					pagingSorting.setPageRecords(Integer.parseInt(Utility.getAppConfigValue("PAGESIZE_DRAFT_PD_ORDER")));
					objDto.setPagingSorting(pagingSorting);
					//PDorderList = objService.viewPDOrderList(objDto);
					//[0016]
					PDorderList = objService.viewPDOrderList(objDto,objUserDto);
				
				}
			} catch (Exception ed) {
				ed.printStackTrace();
				AppConstants.IOES_LOGGER.error("Error while getting Permanent Disconnect Orders "
						+ ed.getMessage());
				throw new IOESException(ed);
			}

			return PDorderList;
		}
		
		
		//0011 end
		/**@author Vijay
		 * 
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return a page mapping that show only disconnection charge list where user can copy a disconnected charge
		 * @throws Exception
		 */
		public ActionForward copyDisconnectedCharge(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
		{
			ActionForward forward = new ActionForward(); // return value
			ActionMessages messages = new ActionMessages();
			try
			{
				forward = mapping.findForward("DisplayDisconnectedCharge");
			}
			catch (Exception e) 
			{
				AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
				messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
			}
			return forward;
		}
		
		//[009]
		public ActionForward orderCancelReason(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
		{
			ActionForward forward = new ActionForward(); // return value
			System.out.println("in orderCancelReason action ");
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
		
}
