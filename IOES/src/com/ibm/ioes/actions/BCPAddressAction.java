//[001]	 Raveendra Kumar	25-Mar-15	20150225-R1-021110     Order transfer rights required on GUI for AM Role

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

import com.ibm.ioes.beans.BCPAddressBean;
import com.ibm.ioes.beans.NewOrderBean;
import com.ibm.ioes.beans.ValidationBean;
import com.ibm.ioes.forms.BCPAddressDto;
import com.ibm.ioes.forms.LocationDetailDto;
import com.ibm.ioes.forms.NewOrderDto;
import com.ibm.ioes.forms.OrderHeaderDTO;
import com.ibm.ioes.model.BCPAddressModel;
import com.ibm.ioes.model.NewOrderModel;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.AppUtility;
import com.ibm.ioes.utilities.Messages;
import com.ibm.ioes.utilities.PagingSorting;
import com.ibm.ioes.utilities.Utility;

public class BCPAddressAction extends DispatchAction
{
	
	
	//	This Method Displays BCP details init page without any list
	public ActionForward getSearchBCP(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		
		BCPAddressBean formBean=(BCPAddressBean)form;
		ActionMessages messages = new ActionMessages();
		ArrayList<BCPAddressDto> listAccount = new ArrayList<BCPAddressDto>();
		ArrayList<BCPAddressDto> objUserList = new ArrayList<BCPAddressDto>();
		BCPAddressDto objDto = new BCPAddressDto();
		BCPAddressModel objService = new BCPAddressModel();
		objDto.setAccountID("");
		try
		{
			
			objDto.setSearchAccountIdStr("");
			objDto.setSearchBcpIdStr("");
			objDto.setSearchBcpNameStr("");
			PagingSorting pagingSorting=formBean.getPagingSorting();
			
			pagingSorting.setDefaultifNotPresent("accountId",PagingSorting.increment,1);
			objDto.setPagingSorting(pagingSorting);
			
			objUserList=objService.viewBCPList(objDto);
			request.setAttribute("customerList", objUserList);
			formBean.setCustomerList(objUserList);
			formBean.setList(listAccount);
			
			
			
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
	 * initial function for search account
	 */
	public ActionForward fetchAccount(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		
		ActionMessages messages = new ActionMessages();
		try
		{
		    
			forward = mapping.findForward("DisplaySelectAccountBCP");
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		return forward;
	}
	/**
	 * search account for add BCP
	 */
	public ActionForward fetchAccountForBCPAdd(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		
		ActionMessages messages = new ActionMessages();
		try
		{
		
			forward = mapping.findForward("DisplaySelectAccountBCP");
			request.setAttribute("ADD_BCP", "ADD_BCP");
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		return forward;
	}
	/*
	 * initial function for pop up of search bcp
	 */
	public ActionForward fetchBCP(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		BCPAddressBean formBean=(BCPAddressBean)form;
		//BCPAddressDto objDto=new BCPAddressDto();
		//BCPAddressModel objModel=new BCPAddressModel();
		ActionMessages messages = new ActionMessages();
		ArrayList<BCPAddressDto> listBCP = new ArrayList<BCPAddressDto>();
		try
		{
			
			/*if(formBean.getSearchBCP()==null){
				
				
				formBean.setSearchBCP("");
			}*/
			//objDto.setSearchBCP(formBean.getSearchBCP());
			//listBCP = objModel.displayBCPDetails(objDto);
			request.setAttribute("listBCP", listBCP);
			//formBean.setHiddenFlag("2");
			forward = mapping.findForward("DisplaySelectBCP");
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		return forward;
	}

	/**
	 * for bcp details list based on search criterias 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewBCPList (ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{	
		
		String forwardMapping = "";	
		ArrayList<BCPAddressDto> objUserList = new ArrayList<BCPAddressDto>();
		BCPAddressModel objService = new BCPAddressModel();
		BCPAddressBean objForm = (BCPAddressBean)form;
		BCPAddressDto objDto=new BCPAddressDto();
		try
		{
			//Validate form fields
			
			ActionMessages messages=new ActionMessages();
			boolean errorsFound=false;
			
			String accountIdStr=objForm.getAccountIdStr();
			String bcpIdStr=objForm.getBcpIdStr();
			String bcpNameStr=objForm.getBcpNameStr();
			
			ArrayList errors= new ArrayList();
			
			Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
					("A\\C No", accountIdStr, 18),
					Utility.generateCSV(Utility.CASE_MAXLENGTH,Utility.CASE_DIGITS_ONLY))
					.appendToAndRetNewErrs(errors);
			
			Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
					("BCP Code ", bcpIdStr, 18),
					Utility.generateCSV(Utility.CASE_MAXLENGTH,Utility.CASE_DIGITS_ONLY))
					.appendToAndRetNewErrs(errors);
			
			Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
					("BCP Name", bcpNameStr, 200),
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
				objDto.setSearchAccountIdStr(accountIdStr);
				objDto.setSearchBcpIdStr(bcpIdStr);
				objDto.setSearchBcpNameStr(bcpNameStr);
				
				PagingSorting pagingSorting=objForm.getPagingSorting();
				pagingSorting.setDefaultifNotPresent("accountId",PagingSorting.increment,1);
				objDto.setPagingSorting(pagingSorting);
				
				objUserList=objService.viewBCPList(objDto);
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
	
	public ActionForward editBCPUser (ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{			
		//logger.info("-------------editUser-------------");
		String forwardMapping = "";	
		BCPAddressDto objDto=  new BCPAddressDto();
		BCPAddressModel objService = new BCPAddressModel();
		BCPAddressBean objForm = (BCPAddressBean)form;
		//ArrayList<BCPAddressDto> objRoleList = new ArrayList<BCPAddressDto>();
		
		try
		{
				if(objForm.getHiddenBCPId()!=0 )
				{
					objService.loadBcpEditView(objForm);
				}	
				request.setAttribute("MODULE_SECTION", "EDIT_BCP_INIT");
				forwardMapping = "addEditBCP";		
			
		}
		catch(Exception ex)
		{

			return mapping.findForward(Messages.getMessageValue("errorGlobalForward"));
		}
		return mapping.findForward(forwardMapping);
	}
	
	/**
	 * this method is for init page for add new bcp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addBCPUserInit (ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{			
		//logger.info("-------------editUser-------------");
		String forwardMapping = "";	
		BCPAddressDto objDto=  new BCPAddressDto();
		BCPAddressModel objService = new BCPAddressModel();
		BCPAddressBean objForm = (BCPAddressBean)form;
		//ArrayList<BCPAddressDto> objRoleList = new ArrayList<BCPAddressDto>();
		//ArrayList<BCPAddressDto> listAccount = new ArrayList<BCPAddressDto>();
		try
		{
			
				
				objDto.setSearchAccount("");
				//listAccount = objService.displayAccountDetails(objDto);
				//objForm.setCustomerList(listAccount);
				objService.loadNewBCPView(objForm);
				
				
				//re-setting form properties 
				objForm.setBcpId("");
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
				
				
				forwardMapping = "addEditBCP";	
				request.setAttribute("MODULE_SECTION", "NEW_BCP_INIT");
				
				
			
		}
		catch(Exception ex)
		{

			return mapping.findForward(Messages.getMessageValue("errorGlobalForward"));
		}
		return mapping.findForward(forwardMapping);
	}
	
	public ActionForward updateBCPLocation (ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{			
		//logger.info("-------------updateUser-------------");
		String forwardMapping = "";	
		String success="";
		//String userId = "";
		BCPAddressDto objDto=  new BCPAddressDto();
		BCPAddressModel objService = new BCPAddressModel();
		BCPAddressBean objForm = (BCPAddressBean)form;

		ArrayList<LocationDetailDto> objList = new ArrayList<LocationDetailDto>();
		try
		{
	
			if(objForm.getHiddenBCPId()!=0)
			{
				//********** Validate form fields ************************
				
				ActionErrors actionErrors=new ActionErrors();
				ActionMessages messages=new ActionMessages();
				boolean errorsFound=false;
				
				ArrayList errors= new ArrayList();
				validateEditBCP(objForm,errors);
				
				
				if(errors!=null && errors.size()>0)//During Server Side Validation
				{
					errors.add("And Refill the form as Changes are Discared.");
					request.setAttribute("validation_errors", errors);
					editBCPUser(mapping, form, request, response);
					return mapping.findForward("addEditBCP");
				}
				
				objDto.setUpdateFlag(2);
				objDto.setBCPId(objForm.getBcpId());
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
			
				success = objService.updateBCP(objDto);
				if(success!=null && !success.trim().equalsIgnoreCase("") && success.equalsIgnoreCase("false"))
				{
					messages.add("", new ActionMessage("EditBCP.failure"));
					saveMessages(request , messages);
					editBCPUser(mapping, form, request, response);
					
					forwardMapping = "addEditBCP";	
					request.setAttribute("MODULE_SECTION", "EDIT_BCP_INIT");
					return mapping.findForward("addEditBCP");
				}
				messages.add("", new ActionMessage("EditBCP.success"));
				//objForm.setCustomerList(objList);
				viewBCPList(mapping, form, request, response);
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
	
	private void validateEditBCP(BCPAddressBean objForm, ArrayList errors) throws Exception{
		Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
				("BCP Id", objForm.getBcpId(), 18),
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
	 * this method is for saving new bcp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addNewBCPLocation (ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{			
		
//		logger.info("-------------addNewCustomerLocation-------------");
		String forwardMapping = "";	
		ArrayList<BCPAddressDto> objList = new ArrayList<BCPAddressDto>();
		BCPAddressModel objService = new BCPAddressModel();
		BCPAddressBean objForm = (BCPAddressBean)form;
		BCPAddressDto objDto = new BCPAddressDto();
		String userId ="";
		String success="";
		try
		{
			//********** Validate form fields ************************8
			
			ActionMessages messages=new ActionMessages();
			ArrayList errors= new ArrayList();
				
			validateAddNewBCP(objForm,errors);
			
			///////////////
			
			if(errors!=null && errors.size()>0)//During Server Side Validation
			{
				request.setAttribute("validation_errors", errors);
				//addBCPUserInit(mapping, form, request, response);
				objDto.setSearchAccount("");
				ArrayList<BCPAddressDto> listAccount = new ArrayList<BCPAddressDto>();
				listAccount = objService.displayAccountDetails(objDto);
				objForm.setCustomerList(listAccount);
				objForm.setCountry(null);
				objService.loadNewBCPView(objForm);
				
				forwardMapping = "addEditBCP";	
				request.setAttribute("MODULE_SECTION", "NEW_BCP_INIT");
				
				return mapping.findForward(forwardMapping);
			}
			else
			{
				
				
				userId = objForm.getBcpName();
			
				objDto.setAccountID(objForm.getAccountID());
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
			
				success = objService.addNewBCP(objDto);
				if(success!=null && !success.trim().equalsIgnoreCase("") && success.equalsIgnoreCase("false"))
				{
					messages.add("", new ActionMessage("AddBCP.fail"));
					saveMessages(request , messages);
					objDto.setSearchAccount("");
					ArrayList<BCPAddressDto> listAccount = new ArrayList<BCPAddressDto>();
					listAccount = objService.displayAccountDetails(objDto);
					objForm.setCustomerList(listAccount);
					objForm.setCountry(null);
					objService.loadNewBCPView(objForm);
					
					forwardMapping = "addEditBCP";	
					request.setAttribute("MODULE_SECTION", "NEW_BCP_INIT");
					return mapping.findForward("addEditBCP");
				}
				messages.add("", new ActionMessage("AddBCP.success"));
				objForm.setCustomerList(objList);
				//objForm.reset(mapping, request);
				//viewBCPList(mapping, form, request, response);
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

	private void validateAddNewBCP(BCPAddressBean objForm, ArrayList errors)throws Exception {
		Utility.validateValue(new ValidationBean().loadValidationBean_String_SingleSelect
				("A\\c", objForm.getAccountID(), "0"),
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
	
	public ActionForward fetchAccount_ofrreassignpm(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
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
			forward = mapping.findForward("DisplaySelectAccountforReassignPM");
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		return forward;
	}
	
	//Start [001]
	public ActionForward fetchAccount_ofrreassignam(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		NewOrderBean formBean=new NewOrderBean();
		OrderHeaderDTO objDto=new OrderHeaderDTO();
		ActionMessages messages = new ActionMessages();
		try
		{
			objDto.setSearchAccount(formBean.getSearchAccount());
			forward = mapping.findForward("DisplaySelectAccountforReassignAM");
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		return forward;
	}
	
	//End [001]
	
}
