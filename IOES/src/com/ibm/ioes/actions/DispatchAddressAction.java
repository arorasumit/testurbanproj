package com.ibm.ioes.actions;


import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import com.ibm.ioes.beans.DispatchAddressBean;
import com.ibm.ioes.beans.ValidationBean;
import com.ibm.ioes.forms.CountryDto;
import com.ibm.ioes.forms.DispatchAddressDto;
import com.ibm.ioes.forms.LocationDetailDto;
import com.ibm.ioes.model.DispatchAddressModel;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.AppUtility;
import com.ibm.ioes.utilities.Messages;
import com.ibm.ioes.utilities.PagingSorting;
import com.ibm.ioes.utilities.Utility;

public class DispatchAddressAction extends DispatchAction
{
//	This Method Displays Accounts for Selection in a Popup
	public ActionForward getSearchDispatchAddress(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		DispatchAddressBean formBean=(DispatchAddressBean)form;
		ArrayList<DispatchAddressDto> listAccount = new ArrayList<DispatchAddressDto>();
		ArrayList<CountryDto> listCountry = new ArrayList<CountryDto>();
		DispatchAddressModel objService = new DispatchAddressModel();
		ArrayList<DispatchAddressDto> objUserList = new ArrayList<DispatchAddressDto>();
		DispatchAddressDto objDto = new DispatchAddressDto();
		try
		{
			formBean.setList(listAccount);
			objDto.setAccountID("");
			objDto.setDispatchAddressId("");
			PagingSorting pagingSorting=formBean.getPagingSorting();
			pagingSorting.setDefaultifNotPresent("accountId",PagingSorting.increment,1);
			objDto.setPagingSorting(pagingSorting);
			objUserList=objService.viewDispatchAddressList(objDto);
			request.setAttribute("customerList", objUserList);
			formBean.setCustomerList(objUserList);
			objService.loadcountries(formBean);
			//formBean.setCountries(listCountry);
			formBean.setHiddenFlag("2");
			forward = mapping.findForward("DisplaySearch");
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return forward;
	}
	
	public ActionForward fetchAccount(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		ActionMessages messages = new ActionMessages();
		try
		{
			
			forward = mapping.findForward("DisplaySelectAccount");
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		return forward;
	}
	
	public ActionForward fetchAccountAdd(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		ActionMessages messages = new ActionMessages();
		try
		{
			
			forward = mapping.findForward("DisplaySelectAccount");
			request.setAttribute("ADD_DALOC", "ADD_DALOC");
			
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		return forward;
	}
	public ActionForward fetchDispatchAddress(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		ActionMessages messages = new ActionMessages();
		try
		{
			
			forward = mapping.findForward("DisplaySelectDispatchAddress");
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		return forward;
	}

	public ActionForward viewDispatchAddressList (ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{	
		
		String forwardMapping = "";	
		ArrayList<DispatchAddressDto> objUserList = new ArrayList<DispatchAddressDto>();
		ArrayList<CountryDto> listCountry = new ArrayList<CountryDto>();
		DispatchAddressModel objService = new DispatchAddressModel();
		DispatchAddressBean objForm = (DispatchAddressBean)form;
		DispatchAddressDto objDto=new DispatchAddressDto();
		try
		{
			//Validate form fields
			
			ActionMessages messages=new ActionMessages();
			boolean errorsFound=false;
			//String accId=request.getParameter("accId");
			//String dipId=request.getParameter("dipId");
			
			String accId= objForm.getAccountIdStr();
			String dipId=objForm.getDispatchAddressIdStr();
			String dipName=objForm.getDispatchAddressStr();
			
			if(errorsFound)
			{
				saveMessages(request, messages);
				objForm.setHiddenFlag("2");
				return mapping.findForward("addEditDispatchAddress");
			}
			//******* Validation Ends Here *************************************
			else
			{
				objDto.setAccountID(accId);
				objDto.setDispatchAddressId(dipId);
				objDto.setDispatchAddressName(dipName);
				PagingSorting pagingSorting=objForm.getPagingSorting();
				pagingSorting.setDefaultifNotPresent("accountId",PagingSorting.increment,1);
				objDto.setPagingSorting(pagingSorting);
				objUserList=objService.viewDispatchAddressList(objDto);
				request.setAttribute("customerList", objUserList);
				objForm.setCustomerList(objUserList);
				//objForm.setCountries(listCountry);
				objService.loadcountries(objForm);
				objService.loadStates(objForm);
				objService.loadCities(objForm);
				objForm.setHiddenFlag("2");
				objForm.setAccountIdStr1("");
				forwardMapping = "addEditDispatchAddress";	

			}
		}
		catch(Exception ex)
		{

			return mapping.findForward(Messages.getMessageValue("errorGlobalForward"));
		}
		//objForm.setDispatchAddressIdStr("");
		//objForm.setDispatchAddressStr("");
		return mapping.findForward(forwardMapping);
	}
	
	public ActionForward editDispatchAddress (ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{			
		//logger.info("-------------editUser-------------");
		String forwardMapping = "";	
		DispatchAddressDto objDto=  new DispatchAddressDto();
		DispatchAddressModel objService = new DispatchAddressModel();
		DispatchAddressBean objForm = (DispatchAddressBean)form;
		ArrayList<DispatchAddressDto> objRoleList = new ArrayList<DispatchAddressDto>();
		ArrayList<DispatchAddressDto> listAccount = new ArrayList<DispatchAddressDto>();
		ArrayList<CountryDto> listCountry = new ArrayList<CountryDto>();
		try
		{
			if(objForm.getActiontype().equals("edit"))
			{
				System.err.println("In the edit method");
				if(objForm.getHiddenDispatchAddressId()!=0 )
				{
					objDto = objService.viewDispatchAddressProfile(objForm.getHiddenDispatchAddressId());
					objForm.setEditaccountID(objDto.getAccountID());
					objForm.setEditaccountName(objDto.getAccountName());
					objForm.setEditDispatchAddressId(objDto.getDispatchAddressId());
					objForm.setEditDispatchAddressName(objDto.getDispatchAddressName());
					objForm.setHiddenDispatchAddressName((objDto.getDispatchAddressName()));
					objForm.setEditfirstname(objDto.getFirstname());
					objForm.setEditlastName(objDto.getLastName());
					objForm.setEdittitle(objDto.getTitle());
		
					objForm.setEditaddress1(objDto.getAddress1());
					objForm.setEditaddress2(objDto.getAddress2());
					objForm.setEditaddress3(objDto.getAddress3());
					objForm.setEditaddress4(objDto.getAddress4());
					objForm.setEditfax(objDto.getFax());
					objForm.setEditpin(objDto.getPin());
					objForm.setEditemail_Id(objDto.getEmail_Id());
					objForm.setEdittelephonePhno(objDto.getTelephonePhno());
					objForm.setEditpostalCode((objDto.getPostalCode()));
					objForm.setEditddCountry((objDto.getCountryCode()));
					objForm.setEditddState((objDto.getState_Id()));
					objForm.setEditddCity((objDto.getCityID()));
					objForm.setCustomerList(listAccount);
					//objForm.setCountries(listCountry);
					objService.loadcountries(objForm);
					objService.loadStates(objForm);
					objService.loadCities(objForm);
					
				}	
				objForm.setHiddenFlag("3");
				request.setAttribute("displayEditTab", "displayEditTab");
				forwardMapping = "addEditDispatchAddress";		
			}else if(objForm.getActiontype().equals("add")){
				
				objDto.setSearchAccount("");
				listAccount = objService.displayAccountDetails(objDto);
				objForm.setCustomerList(listAccount);
				objService.loadcountries(objForm);
				
				//re-setting form properties 
				objForm.setDispatchAddressName("");
				objForm.setFirstname("");
				objForm.setLastName("");
				objForm.setTelephonePhno("");
				objForm.setTitle("");
				objForm.setFax("");
				objForm.setPin("");
				objForm.setAddress1("");
				objForm.setAddress2("");
				objForm.setAddress3("");
				objForm.setAddress4("");
				objForm.setPostalCode("");
				objForm.setEmail_Id("");
				objForm.setAccountIdStr1("");
				objForm.setDdCountry("");
				objService.loadcountries(objForm);
				objService.loadStates(objForm);
				objService.loadCities(objForm);
				
				objForm.setHiddenFlag("1");
				forwardMapping = "addEditDispatchAddress";	
				
				
			}
		}
		catch(Exception ex)
		{

			return mapping.findForward(Messages.getMessageValue("errorGlobalForward"));
		}
		return mapping.findForward(forwardMapping);
	}
	
	public ActionForward updateDispatchAddress (ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{			
		//logger.info("-------------updateUser-------------");
		String forwardMapping = "";	
		String success="";
		String userId = "";
		DispatchAddressDto objDto=  new DispatchAddressDto();
		DispatchAddressModel objService = new DispatchAddressModel();
		DispatchAddressBean objForm = (DispatchAddressBean)form;

		ArrayList<DispatchAddressDto> objList = new ArrayList<DispatchAddressDto>();
		try
		{
	
			if(objForm.getHiddenDispatchAddressId()!=0)
			{
				//********** Validate form fields ************************
				
				ActionErrors actionErrors=new ActionErrors();
				ActionMessages messages=new ActionMessages();
				boolean errorsFound=false;
				
				String firstName=objForm.getEditfirstname();
				if(firstName==null)
				{
					firstName="";
					objDto.setFirstname(firstName);
				}else
				{
					firstName=firstName.trim();
					if(!errorsFound)
					{
						ArrayList errors=Utility.validateValue(
								new ValidationBean(firstName,"First Name",200),
								""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH ).getCompleteMessageStrings();
						if(errors!=null)
						{
							request.setAttribute("validation_errors", errors);
							errorsFound=true;
						}
					}
					objDto.setFirstname(firstName);
				}
				String lastName=objForm.getEditlastName();
				if(lastName==null)
				{
					lastName="";
					objDto.setLastName(lastName);
				}else
				{
					lastName=lastName.trim();
					if(!errorsFound)
					{
						ArrayList errors=Utility.validateValue(
								new ValidationBean(lastName,"Last Name",200),
								""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH ).getCompleteMessageStrings();
						if(errors!=null)
						{
							request.setAttribute("validation_errors", errors);
							errorsFound=true;
						}
					}
					objDto.setLastName(lastName);
					
				}
				
				String addUserContactNo=objForm.getEdittelephonePhno();
				if(addUserContactNo==null)
				{
					addUserContactNo="";
					objDto.setTelephonePhno(addUserContactNo);
				}else
				{
					lastName=lastName.trim();
					if(!errorsFound)
					{
						ArrayList errors=Utility.validateValue(
								new ValidationBean(addUserContactNo,"Contact Number",15),
								""+Utility.CASE_DIGITS_ONLY+","+Utility.CASE_MAXLENGTH ).getCompleteMessageStrings();
						if(errors!=null)
						{
							request.setAttribute("validation_errors", errors);
							errorsFound=true;
						}
					}
					objDto.setTelephonePhno(addUserContactNo);
				}
				String email=objForm.getEditemail_Id();
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(
							new ValidationBean(email,"Email",200),
							""+Utility.CASE_EMAIL+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;
					}
				}
				if(errorsFound)
				{
					saveErrors(request, actionErrors);
					objForm.setHiddenFlag("3");
					objForm.setActiontype("edit");
					//editDispatchAddress(mapping, form, request, response);
					return mapping.findForward("addEditDispatchAddress");
					
				}
				objDto.setDispatchAddressId(String.valueOf(objForm.getHiddenDispatchAddressId()));
				objDto.setDispatchAddressName(objForm.getEditDispatchAddressName());
				objDto.setAccountID(objForm.getEditaccountID());
				objDto.setUpdateFlag(2);
				objDto.setTitle(objForm.getEdittitle());
				objDto.setFax(objForm.getEditfax());
				objDto.setPin(objForm.getEditpin());
				objDto.setAddress1(objForm.getEditaddress1());
				objDto.setAddress2(objForm.getEditaddress2());
				objDto.setAddress3(objForm.getEditaddress3());
				objDto.setAddress4(objForm.getEditaddress4());
				objDto.setEmail_Id(objForm.getEditemail_Id().trim());
				objDto.setPostalCode(objForm.getEditpostalCode());
				objDto.setCountryCode(objForm.getEditddCountry());
				objDto.setState_Id(objForm.getEditddState());
				objDto.setCityID(objForm.getEditddCity());
			
				
				
				if(errorsFound)
				{
					saveMessages(request, messages);
					objForm.setHiddenFlag("3");
					return mapping.findForward("addEditDispatchAddress");
				}
				else
				{
//					******* Validation Ends Here *************

					success = objService.updateDispatchAddress(objDto);
					if(success!=null && !success.trim().equalsIgnoreCase("") && success.equalsIgnoreCase("false"))
					{
						messages.add("", new ActionMessage("EditDispatchAddress.failure"));
						saveMessages(request , messages);
						objForm.setHiddenFlag("3");
						return mapping.findForward("addEditDispatchAddress");
					}
					objForm.setHiddenFlag("2");
					objForm.setCustomerList(objList);
					request.setAttribute("displayListTab", "displayListTab");
					objDto.setAccountID(String.valueOf(0));
					objDto.setDispatchAddressId(String.valueOf(0));
					viewDispatchAddressList(mapping, form, request, response);
					messages.add("", new ActionMessage("EditDispatchAddress.success"));
					saveMessages(request , messages);
					
				 }				
			}
			forwardMapping = "addEditDispatchAddress";				
		}
		catch(Exception ex)
		{
		
			return mapping.findForward(Messages.getMessageValue("errorGlobalForward"));
		}
		return mapping.findForward(forwardMapping);
	}
	
	public ActionForward addNewDispatchAddress (ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{			
		
//		logger.info("-------------addNewCustomerLocation-------------");
		String forwardMapping = "";	
		ArrayList<DispatchAddressDto> objList = new ArrayList<DispatchAddressDto>();
		DispatchAddressModel objService = new DispatchAddressModel();
		DispatchAddressBean objForm = (DispatchAddressBean)form;
		DispatchAddressDto objDto = new DispatchAddressDto();
		String userId ="";
		String success="";
		try
		{
			//********** Validate form fields ************************8
			ActionErrors actionErrors=new ActionErrors();
			ActionMessages messages=new ActionMessages();
			boolean errorsFound=false;
			
			
			
			String firstName=objForm.getFirstname();
			if(firstName==null)
			{
				firstName="";
				objDto.setFirstname(firstName);
			}else
			{
				firstName=firstName.trim();
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(
							new ValidationBean(firstName,"First Name",200),
							""+Utility.CASE_MANDATORY+","+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH ).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;
					}
				}
				objDto.setFirstname(firstName);
			}
			String lastName=objForm.getLastName();
			if(lastName==null)
			{
				lastName="";
				objDto.setLastName(lastName);
			}else
			{
				lastName=lastName.trim();
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(
							new ValidationBean(lastName,"Last Name",200),
							""+Utility.CASE_MANDATORY+","+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH ).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;
					}
				}
				objDto.setLastName(lastName);
				
			}
			
			String addUserContactNo=objForm.getTelephonePhno();
			if(addUserContactNo==null)
			{
				addUserContactNo="";
				objDto.setTelephonePhno(addUserContactNo);
			}else
			{
				lastName=lastName.trim();
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(
							new ValidationBean(addUserContactNo,"Contact Number",15),
							""+Utility.CASE_MANDATORY+","+Utility.CASE_DIGITS_ONLY+","+Utility.CASE_MAXLENGTH ).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;
					}
				}
				objDto.setTelephonePhno(addUserContactNo);
			}
			String email=objForm.getEmail_Id();
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(
						new ValidationBean(email,"Email",200),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_EMAIL+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			if(errorsFound)
			{
				saveErrors(request, actionErrors);
				objForm.setHiddenFlag("1");
				return mapping.findForward("addEditDispatchAddress");
			}
			else
			{
				userId = objForm.getDispatchAddressName();
			
				objDto.setAccountID(objForm.getAccountIdStr1());
				objDto.setUpdateFlag(1);
				objDto.setDispatchAddressName(userId);
				objDto.setTitle(objForm.getTitle());
				objDto.setFax(objForm.getFax());
				objDto.setPin(objForm.getPin());
				objDto.setAddress1(objForm.getAddress1());
				objDto.setAddress2(objForm.getAddress2());
				objDto.setAddress3(objForm.getAddress3());
				objDto.setAddress4(objForm.getAddress4());
				objDto.setEmail_Id(objForm.getEmail_Id().trim());
				objDto.setPostalCode(objForm.getPostalCode());
				objDto.setCountryCode(objForm.getDdCountry());
				objDto.setState_Id(objForm.getDdState());
				objDto.setCityID(objForm.getDdCity());
			
				success = objService.addNewDispatchAddress(objDto);
				if(success!=null && !success.trim().equalsIgnoreCase("") && success.equalsIgnoreCase("false"))
				{
					messages.add("", new ActionMessage("AddDispatchAddress.fail"));
					saveMessages(request , messages);
					objForm.setHiddenFlag("1");
					return mapping.findForward("addEditDispatchAddress");
				}
				objForm.setHiddenFlag("2");
				objForm.setCustomerList(objList);
				request.setAttribute("displayListTab", "displayListTab");
				objDto.setAccountID(String.valueOf(0));
				objDto.setDispatchAddressId(String.valueOf(0));
				//viewDispatchAddressList(mapping, form, request, response);
				messages.add("", new ActionMessage("AddDispatchAddress.success"));
				saveMessages(request , messages);
			}

			forwardMapping = "DisplaySearchAfterAdd";				
		}
		catch(Exception ex)
		{

			return mapping.findForward(Messages.getMessageValue("errorGlobalForward"));
		}
		return mapping.findForward(forwardMapping);
	}
}
