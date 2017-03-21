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

import com.ibm.ioes.beans.NetworkLocationBean;
import com.ibm.ioes.beans.ValidationBean;
import com.ibm.ioes.forms.LocationDetailDto;
import com.ibm.ioes.forms.NetworkLocationDto;
import com.ibm.ioes.model.NetworkLocationModel;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.AppUtility;
import com.ibm.ioes.utilities.Messages;
import com.ibm.ioes.utilities.PagingSorting;
import com.ibm.ioes.utilities.Utility;

public class NetworkLocationAction extends DispatchAction
{
	
	
	//	This Method Displays NetworkLocation details init page without any list
	public ActionForward getSearchNetworkLocation(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		NetworkLocationDto objDto = new NetworkLocationDto();
		NetworkLocationModel objService = new NetworkLocationModel();
		NetworkLocationBean formBean=(NetworkLocationBean)form;
		ActionMessages messages = new ActionMessages();
		ArrayList<NetworkLocationDto> objUserList = new ArrayList<NetworkLocationDto>();
		
		try
		{
			objDto.setSearchNetworkLocationIdStr("");
			objDto.setSearchNetworkLocationNameStr("");
			
			PagingSorting pagingSorting=formBean.getPagingSorting();
			pagingSorting.setDefaultifNotPresent("LOCATION_CODE",PagingSorting.increment,1);
			objDto.setPagingSorting(pagingSorting);
			
			objUserList=objService.viewNetworkLocationList(objDto);
			request.setAttribute("customerList", objUserList);
			formBean.setCustomerList(objUserList);
			
			forward = mapping.findForward("DisplaySearch");
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
			e.printStackTrace();
		}
		return forward;
	}
	

	/*
	 * initial function for pop up of search NetworkLocation
	 */
	public ActionForward fetchNetworkLocation(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		NetworkLocationBean formBean=(NetworkLocationBean)form;
		//NetworkLocationDto objDto=new NetworkLocationDto();
		//NetworkLocationAddressModel objModel=new NetworkLocationAddressModel();
		ActionMessages messages = new ActionMessages();
		ArrayList<NetworkLocationDto> listNetworkLocation = new ArrayList<NetworkLocationDto>();
		try
		{
			
			/*if(formBean.getSearchNetworkLocation()==null){
				
				
				formBean.setSearchNetworkLocation("");
			}*/
			//objDto.setSearchNetworkLocation(formBean.getSearchNetworkLocation());
			//listNetworkLocation = objModel.displayNetworkLocationDetails(objDto);
			request.setAttribute("listNetworkLocation", listNetworkLocation);
			//formBean.setHiddenFlag("2");
			forward = mapping.findForward("DisplaySelectNetworkLocation");
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		return forward;
	}

	/**
	 * for NetworkLocation details list based on search criterias 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewNetworkLocationList (ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{	
		
		String forwardMapping = "";	
		ArrayList<NetworkLocationDto> objUserList = new ArrayList<NetworkLocationDto>();
		NetworkLocationModel objService = new NetworkLocationModel();
		NetworkLocationBean objForm = (NetworkLocationBean)form;
		NetworkLocationDto objDto=new NetworkLocationDto();
		try
		{
			//Validate form fields
			
			ActionMessages messages=new ActionMessages();
			boolean errorsFound=false;
			
			
			String networkLocationIdStr=objForm.getNetworkLocationIdStr();
			String networkLocationNameStr=objForm.getNetworkLocationNameStr();
			
			ArrayList errors= new ArrayList();
			
			
			
			Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
					("Network Location Code ", networkLocationIdStr, 18),
					Utility.generateCSV(Utility.CASE_MAXLENGTH,Utility.CASE_DIGITS_ONLY))
					.appendToAndRetNewErrs(errors);
			
			Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
					("Network Location Name", networkLocationNameStr, 200),
					Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS,
										Utility.CASE_MAXLENGTH))
					.appendToAndRetNewErrs(errors);
			
			if(errors!=null && errors.size()>0)//During Server Side Validation
			{
				request.setAttribute("validation_errors", errors);
				saveMessages(request, messages);
				return mapping.findForward("DisplaySearch");
			}
			//******* Validation Ends Here *************************************
			else
			{
				
				objDto.setSearchNetworkLocationIdStr(networkLocationIdStr);
				objDto.setSearchNetworkLocationNameStr(networkLocationNameStr);
				
				PagingSorting pagingSorting=objForm.getPagingSorting();
				pagingSorting.setDefaultifNotPresent("LOCATION_CODE",PagingSorting.increment,1);
				objDto.setPagingSorting(pagingSorting);
				
				objUserList=objService.viewNetworkLocationList(objDto);
				request.setAttribute("customerList", objUserList);
				objForm.setCustomerList(objUserList);

				forwardMapping = "DisplaySearch";	

			}
		}
		catch(Exception ex)
		{

			return mapping.findForward(Messages.getMessageValue("errorGlobalForward"));
		}

		return mapping.findForward(forwardMapping);
	}
	
	public ActionForward editNetworkLocationUser (ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{			
		//logger.info("-------------editUser-------------");
		String forwardMapping = "";	
		NetworkLocationDto objDto=  new NetworkLocationDto();
		NetworkLocationModel objService = new NetworkLocationModel();
		NetworkLocationBean objForm = (NetworkLocationBean)form;
		//ArrayList<NetworkLocationDto> objRoleList = new ArrayList<NetworkLocationDto>();
		
		try
		{
				if(objForm.getHiddenNetworkLocationId()!=0 )
				{
					objService.loadNetworkLocationEditView(objForm);
				}	
				request.setAttribute("MODULE_SECTION", "EDIT_NetworkLocation_INIT");
				forwardMapping = "addEditNetworkLocation";		
			
		}
		catch(Exception ex)
		{

			return mapping.findForward(Messages.getMessageValue("errorGlobalForward"));
		}
		return mapping.findForward(forwardMapping);
	}
	
	/**
	 * this method is for init page for add new NetworkLocation
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addNetworkLocationUserInit (ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{			
		//logger.info("-------------editUser-------------");
		String forwardMapping = "";	
		NetworkLocationDto objDto=  new NetworkLocationDto();
		NetworkLocationModel objService = new NetworkLocationModel();
		NetworkLocationBean objForm = (NetworkLocationBean)form;
		//ArrayList<NetworkLocationDto> objRoleList = new ArrayList<NetworkLocationDto>();
		
		try
		{
			
				
				
				objService.loadNewNetworkLocationView(objForm);
				
				
				//re-setting form properties 
				objForm.setNetworkLocationId("");
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
				objForm.setCity("");
				
				
				forwardMapping = "addEditNetworkLocation";	
				request.setAttribute("MODULE_SECTION", "NEW_NetworkLocation_INIT");
				
				
			
		}
		catch(Exception ex)
		{

			return mapping.findForward(Messages.getMessageValue("errorGlobalForward"));
		}
		return mapping.findForward(forwardMapping);
	}
	
	public ActionForward updateNetworkLocationLocation (ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{			
		//logger.info("-------------updateUser-------------");
		String forwardMapping = "";	
		String success="";
		//String userId = "";
		NetworkLocationDto objDto=  new NetworkLocationDto();
		NetworkLocationModel objService = new NetworkLocationModel();
		NetworkLocationBean objForm = (NetworkLocationBean)form;

		ArrayList<LocationDetailDto> objList = new ArrayList<LocationDetailDto>();
		try
		{
	
			if(objForm.getHiddenNetworkLocationId()!=0)
			{
				//********** Validate form fields ************************
				
				ActionErrors actionErrors=new ActionErrors();
				ActionMessages messages=new ActionMessages();
				boolean errorsFound=false;
				
				ArrayList errors= new ArrayList();
				validateEditNetworkLocation(objForm,errors);
				
				
				if(errors!=null && errors.size()>0)//During Server Side Validation
				{
					errors.add("And Refill the form as Changes are Discared.");
					request.setAttribute("validation_errors", errors);
					editNetworkLocationUser(mapping, form, request, response);
					return mapping.findForward("addEditNetworkLocation");
				}
				
				objDto.setUpdateFlag(2);
				objDto.setNetworkLocationId(objForm.getNetworkLocationId());
				objDto.setFirstname(objForm.getFirstname());
				objDto.setLastName(objForm.getLastName());
				objDto.setTelephonePhno(objForm.getTelephonePhno());
				objDto.setTitle(objForm.getTitle());
				objDto.setFax(objForm.getFax());
				objDto.setPin(objForm.getPin());
				objDto.setAddress1(objForm.getAddress1());
				objDto.setAddress2(objForm.getAddress2());
				objDto.setAddress3(objForm.getAddress3());
				objDto.setAddress4(objForm.getAddress4());
				objDto.setEmail_Id(objForm.getEmail_Id().trim());
				objDto.setPostalCode(objForm.getPostalCode());
				objDto.setState_Id(objForm.getState());
				objDto.setCity_Id(objForm.getCity());
				objDto.setCountryCode(objForm.getCountry());
			
				success = objService.updateNetworkLocation(objDto);
				if(success!=null && !success.trim().equalsIgnoreCase("") && success.equalsIgnoreCase("false"))
				{
					messages.add("", new ActionMessage("EditNetworkLocation.failure"));
					saveMessages(request , messages);
					editNetworkLocationUser(mapping, form, request, response);
					
					forwardMapping = "addEditNetworkLocation";	
					request.setAttribute("MODULE_SECTION", "EDIT_NetworkLocation_INIT");
					return mapping.findForward("addEditNetworkLocation");
				}
				messages.add("", new ActionMessage("EditNetworkLocation.success"));
				//objForm.setCustomerList(objList);
				viewNetworkLocationList(mapping, form, request, response);
				saveMessages(request, messages);
				
				
				 			
			}
			forwardMapping = "DisplaySearch";				
		}
		catch(Exception ex)
		{
		
			return mapping.findForward(Messages.getMessageValue("errorGlobalForward"));
		}
		return mapping.findForward(forwardMapping);
	}
	
	private void validateEditNetworkLocation(NetworkLocationBean objForm, ArrayList errors) throws Exception{
		Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
				("Network Location Id", objForm.getNetworkLocationId(), 18),
				Utility.generateCSV(Utility.CASE_MANDATORY,
						Utility.CASE_MAXLENGTH,Utility.CASE_DIGITS_ONLY))
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
				("Country", objForm.getCountry(), "0"),
				""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MANDATORY_SINGLESELECT_STRING+","+
					Utility.CASE_DIGITS_ONLY)
				.appendToAndRetNewErrs(errors);
		Utility.validateValue(new ValidationBean().loadValidationBean_String_SingleSelect
				("State", objForm.getState(), "0"),
				""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MANDATORY_SINGLESELECT_STRING+","+
					Utility.CASE_DIGITS_ONLY)
				.appendToAndRetNewErrs(errors);
		Utility.validateValue(new ValidationBean().loadValidationBean_String_SingleSelect
				("City", objForm.getCity(), "0"),
				""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MANDATORY_SINGLESELECT_STRING+","+
					Utility.CASE_DIGITS_ONLY)
				.appendToAndRetNewErrs(errors);
		
		Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
				("Postal Code", objForm.getPostalCode(), 15),
				Utility.generateCSV(Utility.CASE_MANDATORY,Utility.CASE_SPECIALCHARACTERS,
									Utility.CASE_MAXLENGTH,Utility.CASE_DIGITS_ONLY))
				.appendToAndRetNewErrs(errors);
	}

	/**
	 * this method is for saving new NetworkLocation
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addNewNetworkLocationLocation (ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{			
		
//		logger.info("-------------addNewCustomerLocation-------------");
		String forwardMapping = "";	
		ArrayList<NetworkLocationDto> objList = new ArrayList<NetworkLocationDto>();
		NetworkLocationModel objService = new NetworkLocationModel();
		NetworkLocationBean objForm = (NetworkLocationBean)form;
		NetworkLocationDto objDto = new NetworkLocationDto();
		String userId ="";
		String success="";
		try
		{
			//********** Validate form fields ************************8
			
			ActionMessages messages=new ActionMessages();
			ArrayList errors= new ArrayList();
				
			validateAddNewNetworkLocation(objForm,errors);
			
			///////////////
			
			if(errors!=null && errors.size()>0)//During Server Side Validation
			{
				request.setAttribute("validation_errors", errors);
				//addNetworkLocationUserInit(mapping, form, request, response);
			
				
				
				objForm.setCountry(null);
				objService.loadNewNetworkLocationView(objForm);
				
				forwardMapping = "addEditNetworkLocation";	
				request.setAttribute("MODULE_SECTION", "NEW_NetworkLocation_INIT");
				
				return mapping.findForward(forwardMapping);
			}
			else
			{
				
				
				userId = objForm.getNetworkLocationName();
			
				
				objDto.setUpdateFlag(1);
				objDto.setFirstname(objForm.getFirstname());
				objDto.setLastName(objForm.getLastName());
				objDto.setTelephonePhno(objForm.getTelephonePhno());
				objDto.setTitle(objForm.getTitle());
				objDto.setFax(objForm.getFax());
				objDto.setPin(objForm.getPin());
				objDto.setAddress1(objForm.getAddress1());
				objDto.setAddress2(objForm.getAddress2());
				objDto.setAddress3(objForm.getAddress3());
				objDto.setAddress4(objForm.getAddress4());
				objDto.setEmail_Id(objForm.getEmail_Id().trim());
				objDto.setPostalCode(objForm.getPostalCode());
				objDto.setState_Id(objForm.getState());
				objDto.setCity_Id(objForm.getCity());
				objDto.setCountryCode(objForm.getCountry());
			
				success = objService.addNewNetworkLocation(objDto);
				if(success!=null && !success.trim().equalsIgnoreCase("") && success.equalsIgnoreCase("false"))
				{
					messages.add("", new ActionMessage("AddNetworkLocation.fail"));
					saveMessages(request , messages);
					
					
					
					
					objForm.setCountry(null);
					objService.loadNewNetworkLocationView(objForm);
					
					forwardMapping = "addEditNetworkLocation";	
					request.setAttribute("MODULE_SECTION", "NEW_NetworkLocation_INIT");
					return mapping.findForward("addEditNetworkLocation");
				}
				messages.add("", new ActionMessage("AddNetworkLocation.success"));
				objForm.setCustomerList(objList);
				
				//viewNetworkLocationList(mapping, form, request, response);
				saveMessages(request, messages);
			}
			

			forwardMapping = "DisplaySearchAfterAdd";				
		}
		catch(Exception ex)
		{

			return mapping.findForward(Messages.getMessageValue("errorGlobalForward"));
		}
		return mapping.findForward(forwardMapping);
	}

	private void validateAddNewNetworkLocation(NetworkLocationBean objForm, ArrayList errors)throws Exception {
		
		
		
		
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
				("Country", objForm.getCountry(), "0"),
				""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MANDATORY_SINGLESELECT_STRING+","+
					Utility.CASE_DIGITS_ONLY)
				.appendToAndRetNewErrs(errors);
		Utility.validateValue(new ValidationBean().loadValidationBean_String_SingleSelect
				("State", objForm.getState(), "0"),
				""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MANDATORY_SINGLESELECT_STRING+","+
					Utility.CASE_DIGITS_ONLY)
				.appendToAndRetNewErrs(errors);
		Utility.validateValue(new ValidationBean().loadValidationBean_String_SingleSelect
				("City", objForm.getCity(), "0"),
				""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MANDATORY_SINGLESELECT_STRING+","+
					Utility.CASE_DIGITS_ONLY)
				.appendToAndRetNewErrs(errors);
		
		Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
				("Postal Code", objForm.getPostalCode(), 15),
				Utility.generateCSV(Utility.CASE_MANDATORY,Utility.CASE_SPECIALCHARACTERS,
									Utility.CASE_MAXLENGTH,Utility.CASE_DIGITS_ONLY))
				.appendToAndRetNewErrs(errors);
		
	}
}
