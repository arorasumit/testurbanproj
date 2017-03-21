package com.ibm.ioes.actions;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import com.ibm.ioes.beans.LocationDetailBean;
import com.ibm.ioes.beans.ValidationBean;
import com.ibm.ioes.forms.CountryDto;
import com.ibm.ioes.forms.LocationDetailDto;
import com.ibm.ioes.model.LocationDetailModel;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.AppUtility;
import com.ibm.ioes.utilities.Messages;
import com.ibm.ioes.utilities.PagingSorting;
import com.ibm.ioes.utilities.Utility;
public class CustomerLocationDetailAction extends DispatchAction
{
	//	This Method Displays Accounts for Selection in a Popup
	public ActionForward getSearchCustomerLocation(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		LocationDetailBean formBean=(LocationDetailBean)form;
		LocationDetailModel objService = new LocationDetailModel();
		LocationDetailDto objDto= new LocationDetailDto();
		ArrayList<LocationDetailDto> listAccount = new ArrayList<LocationDetailDto>();
		ArrayList<CountryDto> listCountry = new ArrayList<CountryDto>();
		ArrayList<LocationDetailDto> objUserList = new ArrayList<LocationDetailDto>();
		try
		{
			
			
			formBean.setList(listAccount);
			formBean.setCustomerList(listAccount);
			formBean.setCountries(listCountry);
			// for first time load of page all data should come.
			objDto.setAccountID("");
			objDto.setLocationId("");
			
			PagingSorting pagingSorting=formBean.getPagingSorting();
			pagingSorting.setDefaultifNotPresent("accountId",PagingSorting.increment,1);
			objDto.setPagingSorting(pagingSorting);
			
			objUserList=objService.viewLocationList(objDto);
			request.setAttribute("customerList", objUserList);
			formBean.setCustomerList(objUserList);
			formBean.setHiddenFlag("2");
			forward = mapping.findForward("DisplaySearch");
		}
		catch (Exception e) 
		{
//			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
//			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
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
			request.setAttribute("ADD_CUSTLOC", "ADD_CUSTLOC");
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		return forward;
	}
	public ActionForward fetchLocation(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		ActionMessages messages = new ActionMessages();
	
		try
		{
			
			forward = mapping.findForward("DisplaySelectLocation");
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		return forward;
	}

	public ActionForward viewCustomerLocationList (ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{	
		
		String forwardMapping = "";	
		ArrayList<LocationDetailDto> objUserList = new ArrayList<LocationDetailDto>();
		LocationDetailModel objService = new LocationDetailModel();
		LocationDetailBean objForm = (LocationDetailBean)form;
		LocationDetailDto objDto=new LocationDetailDto();
		try
		{
			//Validate form fields
			
			ActionMessages messages=new ActionMessages();
			boolean errorsFound=false;
			
					
			String accId=objForm.getAccountIdStr();
			String locId=objForm.getLocationIdStr();
			String locName=objForm.getLocationNameStr();
			if(errorsFound)
			{
				saveMessages(request, messages);
				objForm.setHiddenFlag("2");
				return mapping.findForward("addEditLocation");
			}
			//******* Validation Ends Here *************************************
			else
			{
				objDto.setAccountID(accId);
				objDto.setLocationId(locId);
				objDto.setLocationName(locName);
				
				PagingSorting pagingSorting=objForm.getPagingSorting();
				pagingSorting.setDefaultifNotPresent("accountId",PagingSorting.increment,1);
				objDto.setPagingSorting(pagingSorting);
				
				objUserList=objService.viewLocationList(objDto);
				request.setAttribute("customerList", objUserList);
				objForm.setCustomerList(objUserList);
				objService.loadcountries(objForm);
				objService.loadStates(objForm);
				objService.loadCities(objForm);
				objForm.setHiddenFlag("2");
				objForm.setAccountIdStr1("");
				forwardMapping = "addEditLocation";	

			}
		}
		catch(Exception ex)
		{

			return mapping.findForward(Messages.getMessageValue("errorGlobalForward"));
		}
		//objForm.setLocationIdStr("");
		//objForm.setLocationNameStr("");
		return mapping.findForward(forwardMapping);
	}
	
	public ActionForward editUser (ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{			
		//logger.info("-------------editUser-------------");
		String forwardMapping = "";	
		LocationDetailDto objDto=  new LocationDetailDto();
		LocationDetailModel objService = new LocationDetailModel();
		LocationDetailBean objForm = (LocationDetailBean)form;
		ArrayList<LocationDetailDto> listAccount = new ArrayList<LocationDetailDto>();
		ArrayList<CountryDto> listCountry = new ArrayList<CountryDto>();
		try
		{
			
		
			if(objForm.getActiontype().equals("edit"))
			{
				System.err.println("In the edit method");
				if(objForm.getHiddenLocationId()!=0 )
				{
					objDto = objService.viewUserProfile(objForm.getHiddenLocationId());
					objForm.setEditaccountID(objDto.getAccountID());
					objForm.setEditfirstname(objDto.getFirstname());
					objForm.setEditaccountName(objDto.getAccountName());
					objForm.setEditlocationId(objDto.getLocationId());
					objForm.setEditlocationName(objDto.getLocationName());
					objForm.setHiddenLocationName((objDto.getLocationName()));
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
					objForm.setEditddCountry(objDto.getCountryCode());
					objForm.setEditddState(objDto.getState_Id());
					objForm.setEditddCity(objDto.getCityID());
					objForm.setCustomerList(listAccount);
					objForm.setCountries(listCountry);
					objService.loadcountries(objForm);
					//objForm.setStates(listStates);
					objService.loadStates(objForm);
					objService.loadCities(objForm);
					
				}	
				objForm.setHiddenFlag("3");
				request.setAttribute("displayEditTab", "displayEditTab");
				forwardMapping = "addEditLocation";		
			}else if(objForm.getActiontype().equals("add")){
				
				objDto.setSearchAccount("");
				listAccount = objService.displayAccountDetails(objDto);
				objForm.setCustomerList(listAccount);
			
				objService.loadcountries(objForm);
			
				
				//re-setting form properties 
				objForm.setLocationName("");
				objForm.setTitle("");
				objForm.setFirstname("");
				objForm.setLastName("");
				objForm.setTelephonePhno("");
				objForm.setEmail_Id("");
				objForm.setFax("");
				objForm.setPin("");
				objForm.setAddress1("");
				objForm.setAddress2("");
				objForm.setAddress3("");
				objForm.setAddress4("");
				objForm.setPostalCode("");
				objForm.setDdCountry("");
				objForm.setAccountIdStr1("");
				objService.loadcountries(objForm);
				//objForm.setStates(listStates);
				objService.loadStates(objForm);
				objService.loadCities(objForm);
				
				objForm.setHiddenFlag("1");
				forwardMapping = "addEditLocation";	
				
				
			}
		}
		catch(Exception ex)
		{

			return mapping.findForward(Messages.getMessageValue("errorGlobalForward"));
		}
		return mapping.findForward(forwardMapping);
	}
	
	public ActionForward updateCustomerLocation (ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{			
		//logger.info("-------------updateUser-------------");
		String forwardMapping = "";	
		String success="";
		LocationDetailDto objDto=  new LocationDetailDto();
		LocationDetailModel objService = new LocationDetailModel();
		LocationDetailBean objForm = (LocationDetailBean)form;

		ArrayList<LocationDetailDto> objList = new ArrayList<LocationDetailDto>();
		try
		{
	
			if(objForm.getHiddenLocationId()!=0)
			{

//				********** Validate form fields ************************8
				ActionMessages messages=new ActionMessages();
				
				ArrayList errors= new ArrayList();
					
				Utility.validateValue(new ValidationBean().loadValidationBean_String_SingleSelect
						("A\\c No", objForm.getEditaccountID(), "0"),
						""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MANDATORY_SINGLESELECT_STRING+","+
							Utility.CASE_DIGITS_ONLY)
						.appendToAndRetNewErrs(errors);
		
				Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
						("Title", objForm.getEdittitle(), 15),
						Utility.generateCSV(Utility.CASE_MANDATORY,Utility.CASE_SPECIALCHARACTERS,
											Utility.CASE_MAXLENGTH))
						.appendToAndRetNewErrs(errors);
				
				Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
						("First Name", objForm.getEditfirstname(), 100),
						Utility.generateCSV(Utility.CASE_MANDATORY,Utility.CASE_SPECIALCHARACTERS,
											Utility.CASE_MAXLENGTH))
						.appendToAndRetNewErrs(errors);
				
				Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
						("Last Name", objForm.getEditlastName(), 100),
						Utility.generateCSV(Utility.CASE_MANDATORY,Utility.CASE_SPECIALCHARACTERS,
											Utility.CASE_MAXLENGTH))
						.appendToAndRetNewErrs(errors);
				
				Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
						("Telephone Number", objForm.getEdittelephonePhno(), 50),
						Utility.generateCSV(Utility.CASE_MANDATORY,Utility.CASE_SPECIALCHARACTERS,
											Utility.CASE_MAXLENGTH,Utility.CASE_DIGITS_ONLY))
						.appendToAndRetNewErrs(errors);
				
				Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
						("E-mail", objForm.getEditemail_Id(), 150),
						Utility.generateCSV(Utility.CASE_MANDATORY,/*Utility.CASE_SPECIALCHARACTERS,*/
											Utility.CASE_MAXLENGTH,Utility.CASE_EMAIL))
						.appendToAndRetNewErrs(errors);
				
				Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
						("Fax", objForm.getEditfax(), 50),
						Utility.generateCSV(Utility.CASE_MANDATORY,Utility.CASE_SPECIALCHARACTERS,
											Utility.CASE_MAXLENGTH,Utility.CASE_DIGITS_ONLY))
						.appendToAndRetNewErrs(errors);
				///////////////
				Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
						("Address1", objForm.getEditaddress1(), 200),
						Utility.generateCSV(Utility.CASE_MANDATORY,Utility.CASE_SPECIALCHARACTERS,
											Utility.CASE_MAXLENGTH))
						.appendToAndRetNewErrs(errors);
				Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
						("Address2", objForm.getEditaddress2(), 200),
						Utility.generateCSV(Utility.CASE_MANDATORY,Utility.CASE_SPECIALCHARACTERS,
											Utility.CASE_MAXLENGTH))
						.appendToAndRetNewErrs(errors);
				Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
						("Address3", objForm.getEditaddress3(), 200),
						Utility.generateCSV(Utility.CASE_MANDATORY,Utility.CASE_SPECIALCHARACTERS,
											Utility.CASE_MAXLENGTH))
						.appendToAndRetNewErrs(errors);
				Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
						("Address4", objForm.getEditaddress4(), 200),
						Utility.generateCSV(Utility.CASE_MANDATORY,Utility.CASE_SPECIALCHARACTERS,
											Utility.CASE_MAXLENGTH))
						.appendToAndRetNewErrs(errors);
				
				Utility.validateValue(new ValidationBean().loadValidationBean_String_SingleSelect
						("Country", objForm.getEditddCountry(), "0"),
						""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MANDATORY_SINGLESELECT_STRING+","+
							Utility.CASE_DIGITS_ONLY)
						.appendToAndRetNewErrs(errors);
				Utility.validateValue(new ValidationBean().loadValidationBean_String_SingleSelect
						("State", objForm.getEditddState(), "0"),
						""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MANDATORY_SINGLESELECT_STRING+","+
							Utility.CASE_DIGITS_ONLY)
						.appendToAndRetNewErrs(errors);
				Utility.validateValue(new ValidationBean().loadValidationBean_String_SingleSelect
						("City", objForm.getEditddCity(), "0"),
						""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MANDATORY_SINGLESELECT_STRING+","+
							Utility.CASE_DIGITS_ONLY)
						.appendToAndRetNewErrs(errors);
				
				/*Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
						("Postal Code", objForm.getTelephonePhno(), 15),
						Utility.generateCSV(Utility.CASE_MANDATORY,Utility.CASE_SPECIALCHARACTERS,
											Utility.CASE_MAXLENGTH,Utility.CASE_DIGITS_ONLY))
						.appendToAndRetNewErrs(errors);*/
				///////////////
				
				if(errors!=null && errors.size()>0)//During Server Side Validation
				{
					request.setAttribute("validation_errors", errors);
					objForm.setHiddenFlag("3");
					forwardMapping = "addEditLocation";	
					//updateCustomerLocation(mapping, form, request, response);
					return mapping.findForward(forwardMapping);
				}
				else{
				
					objDto.setLocationId(String.valueOf(objForm.getHiddenLocationId()));
					objDto.setLocationName(objForm.getEditlocationName());
					objDto.setAccountID(objForm.getEditaccountID());
					objDto.setUpdateFlag(2);
					objDto.setTitle(objForm.getEdittitle());
					objDto.setFirstname(objForm.getEditfirstname());
					objDto.setLastName(objForm.getEditlastName());
					objDto.setTelephonePhno(objForm.getEdittelephonePhno());
					objDto.setEmail_Id(objForm.getEditemail_Id());
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
	
				
//					******* Validation Ends Here *************

					success = objService.updateCustomerLocation(objDto);
					if(success!=null && !success.trim().equalsIgnoreCase("") && success.equalsIgnoreCase("false"))
					{
						messages.add("", new ActionMessage("EditCustomerLocation.failure"));
						saveMessages(request , messages);
						objForm.setHiddenFlag("3");
						return mapping.findForward("addEditLocation");
					}
					objForm.setHiddenFlag("2");
					objForm.setCustomerList(objList);
					request.setAttribute("displayListTab", "displayListTab");
					objDto.setAccountID(String.valueOf(0));
					objDto.setLocationId(String.valueOf(0));
					viewCustomerLocationList(mapping, form, request, response);
					messages.add("", new ActionMessage("EditCustomerLocation.success"));
					saveMessages(request , messages);
					
				 }				
			}
			forwardMapping = "addEditLocation";				
		}
		catch(Exception ex)
		{
		
			return mapping.findForward(Messages.getMessageValue("errorGlobalForward"));
		}
		return mapping.findForward(forwardMapping);
	}
	
	public ActionForward addNewCustomerLocation (ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{			
		
//		logger.info("-------------addNewCustomerLocation-------------");
		String forwardMapping = "";	
		ArrayList<LocationDetailDto> objList = new ArrayList<LocationDetailDto>();
		LocationDetailModel objService = new LocationDetailModel();
		LocationDetailBean objForm = (LocationDetailBean)form;
		LocationDetailDto objDto = new LocationDetailDto();
		String userId ="";
		String success="";
		try
		{
			
//			********** Validate form fields ************************8
		
			ActionMessages messages=new ActionMessages();
				
			ArrayList errors= new ArrayList();
				
			Utility.validateValue(new ValidationBean().loadValidationBean_String_SingleSelect
					("A\\c No", objForm.getAccountIdStr1(), "0"),
					""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MANDATORY_SINGLESELECT_STRING+","+
						Utility.CASE_DIGITS_ONLY)
					.appendToAndRetNewErrs(errors);
	
			Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
					("Title", objForm.getTitle(), 15),
					Utility.generateCSV(Utility.CASE_MANDATORY,Utility.CASE_SPECIALCHARACTERS,
										Utility.CASE_MAXLENGTH))
					.appendToAndRetNewErrs(errors);
			
			Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
					("First Name", objForm.getFirstname(), 100),
					Utility.generateCSV(Utility.CASE_MANDATORY,Utility.CASE_SPECIALCHARACTERS,
										Utility.CASE_MAXLENGTH))
					.appendToAndRetNewErrs(errors);
			
			Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
					("Last Name", objForm.getLastName(), 100),
					Utility.generateCSV(Utility.CASE_MANDATORY,Utility.CASE_SPECIALCHARACTERS,
										Utility.CASE_MAXLENGTH))
					.appendToAndRetNewErrs(errors);
			
			Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
					("Telephone Number", objForm.getTelephonePhno(), 50),
					Utility.generateCSV(Utility.CASE_MANDATORY,Utility.CASE_SPECIALCHARACTERS,
										Utility.CASE_MAXLENGTH,Utility.CASE_DIGITS_ONLY))
					.appendToAndRetNewErrs(errors);
			
			Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
					("E-mail", objForm.getEmail_Id(), 150),
					Utility.generateCSV(Utility.CASE_MANDATORY,
										Utility.CASE_MAXLENGTH,Utility.CASE_EMAIL))
							.appendToAndRetNewErrs(errors);
			
			Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
					("Fax", objForm.getFax(), 50),
					Utility.generateCSV(Utility.CASE_MANDATORY,Utility.CASE_SPECIALCHARACTERS,
										Utility.CASE_MAXLENGTH,Utility.CASE_DIGITS_ONLY))
					.appendToAndRetNewErrs(errors);
			///////////////
			Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
					("Address1", objForm.getAddress1(), 200),
					Utility.generateCSV(Utility.CASE_MANDATORY,Utility.CASE_SPECIALCHARACTERS,
										Utility.CASE_MAXLENGTH))
					.appendToAndRetNewErrs(errors);
			Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
					("Address2", objForm.getAddress2(), 200),
					Utility.generateCSV(Utility.CASE_MANDATORY,Utility.CASE_SPECIALCHARACTERS,
										Utility.CASE_MAXLENGTH))
					.appendToAndRetNewErrs(errors);
			Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
					("Address3", objForm.getAddress3(), 200),
					Utility.generateCSV(Utility.CASE_MANDATORY,Utility.CASE_SPECIALCHARACTERS,
										Utility.CASE_MAXLENGTH))
					.appendToAndRetNewErrs(errors);
			Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
					("Address4", objForm.getAddress4(), 200),
					Utility.generateCSV(Utility.CASE_MANDATORY,Utility.CASE_SPECIALCHARACTERS,
										Utility.CASE_MAXLENGTH))
					.appendToAndRetNewErrs(errors);
			
			Utility.validateValue(new ValidationBean().loadValidationBean_String_SingleSelect
					("Country", objForm.getDdCountry(), "0"),
					""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MANDATORY_SINGLESELECT_STRING+","+
						Utility.CASE_DIGITS_ONLY)
					.appendToAndRetNewErrs(errors);
			Utility.validateValue(new ValidationBean().loadValidationBean_String_SingleSelect
					("State", objForm.getDdState(), "0"),
					""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MANDATORY_SINGLESELECT_STRING+","+
						Utility.CASE_DIGITS_ONLY)
					.appendToAndRetNewErrs(errors);
			Utility.validateValue(new ValidationBean().loadValidationBean_String_SingleSelect
					("City", objForm.getDdCity(), "0"),
					""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MANDATORY_SINGLESELECT_STRING+","+
						Utility.CASE_DIGITS_ONLY)
					.appendToAndRetNewErrs(errors);
			
			Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
					("Postal Code", objForm.getPostalCode(), 15),
					Utility.generateCSV(Utility.CASE_MANDATORY,Utility.CASE_SPECIALCHARACTERS,
										Utility.CASE_MAXLENGTH,Utility.CASE_DIGITS_ONLY))
					.appendToAndRetNewErrs(errors);
			///////////////
			
			if(errors!=null && errors.size()>0)//During Server Side Validation
			{
				request.setAttribute("validation_errors", errors);
				//addNewCustomerLocation(mapping, form, request, response);
				objForm.setHiddenFlag("1");
				forwardMapping = "addEditLocation";	
				return mapping.findForward(forwardMapping);
			}
			else
			{
				
				
				userId = objForm.getLocationName();
			
				objDto.setAccountID(objForm.getAccountIdStr1());
				objDto.setUpdateFlag(1);
				objDto.setLocationName(userId);
				objDto.setTitle(objForm.getTitle());
				objDto.setFirstname(objForm.getFirstname());
				objDto.setLastName(objForm.getLastName());
				objDto.setTelephonePhno(objForm.getTelephonePhno());
				objDto.setEmail_Id(objForm.getEmail_Id());
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
				objService.loadcountries(objForm);
				objService.loadStates(objForm);
				objService.loadCities(objForm);
			
				success = objService.addNewCustomerLocation(objDto);
				if(success!=null && !success.trim().equalsIgnoreCase("") && success.equalsIgnoreCase("false"))
				{
					messages.add("", new ActionMessage("AddCustomerLocation.fail"));
					saveMessages(request , messages);
					objForm.setHiddenFlag("1");
					return mapping.findForward("addEditLocation");
				}
				objForm.setHiddenFlag("2");
				objForm.setCustomerList(objList);
				request.setAttribute("displayListTab", "displayListTab");

				//objDto.setAccountID(String.valueOf(0));
				//objDto.setLocationId(String.valueOf(0));
				//viewCustomerLocationList(mapping, form, request, response);
				messages.add("", new ActionMessage("AddCustomerLocation.success"));
				saveMessages(request , messages);
			}
			//******* Validation Ends Here ***************************************

			forwardMapping = "DisplaySearchAfterAdd";				
		}
		catch(Exception ex)
		{

			return mapping.findForward(Messages.getMessageValue("errorGlobalForward"));
		}
		return mapping.findForward(forwardMapping);
	}
}
