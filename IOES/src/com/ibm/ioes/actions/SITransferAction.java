package com.ibm.ioes.actions;
//Tag Name Resource Name  Date		CSR No			Description
//[001]	MANISHA GARG	28-MAR-11					To Fecth Account List
//[005]	MANISHA GARG	22-feb-11					To remove duplicate data
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Date;
import java.util.HashSet;

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

//import com.ibm.icu.util.Calendar;
import com.ibm.ioes.beans.SITransferBean;
import com.ibm.ioes.beans.DefaultBean;
import com.ibm.ioes.beans.UserInfoDto;
import com.ibm.ioes.beans.ValidationBean;
import com.ibm.ioes.exception.IOESException;
import com.ibm.ioes.forms.NewOrderDto;
import com.ibm.ioes.forms.SITransferDto;
import com.ibm.ioes.model.NewOrderModel;
import com.ibm.ioes.model.SITransferModel;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.AppUtility;
import com.ibm.ioes.utilities.PagingSorting;
import com.ibm.ioes.utilities.Utility;

public class SITransferAction extends DispatchAction {

	private static final Logger logger;
	static {
		logger = Logger.getLogger(SITransferAction.class);
	}
	public ActionForward goToSITransfer(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		ActionMessages messages = new ActionMessages();
		
		try
		{
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
						
			request.setAttribute("userroleid", objUserDto.getUserRoleId());
			
			forward = mapping.findForward("DisplaySITransferPage");
		}
		catch (Exception e) 
		{
			logger.info("goToSITransfer() method execute  !");
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
			e.printStackTrace();
		}
		return forward;
	}
	
	
	public ActionForward fetchParty(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		SITransferModel objModel=new SITransferModel();
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
			logger.info("fetchParty() method execute  !");
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		return forward;
	}
	
	
	
	public ActionForward fetchLogicalSI(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		//SITransferModel objModel=new SITransferModel();
		ActionMessages messages = new ActionMessages();
		//ArrayList<SITransferDto> listParty = new ArrayList<SITransferDto>();
		try
		{
			//listParty = objModel.fetchParty();
			//request.setAttribute("PartyList", listParty);
			forward = mapping.findForward("DisplaySelectLogicalSI");
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		return forward;
	}
	// 001 start
	
	public ActionForward fetchAccount(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		SITransferModel objModel=new SITransferModel();
		ActionMessages messages = new ActionMessages();
		ArrayList<SITransferDto> listAccount = new ArrayList<SITransferDto>();
		try
		{
			//String arr=	request.getParameter("targetPartyNo");
			//listAccount = objModel.fetchAccount(arr);
			//request.setAttribute("AccountList", listAccount);
			forward = mapping.findForward("DisplaySelectAccount");
		}
		catch (Exception e) 
		{
			logger.info("fetchAccount() method execute  !");
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		return forward;
	}
	public ActionForward fetchBillingInfo(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		SITransferModel objModel=new SITransferModel();
		ActionMessages messages = new ActionMessages();
		ArrayList<SITransferDto> listBillingInfo = new ArrayList<SITransferDto>();
		try
		{
			
			forward = mapping.findForward("DisplaySelectBcpInfo");
		}
		catch (Exception e) 
		{
			logger.info("fetchBillingInfo() method execute  !");
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		return forward;
	}
	

	// 002 end	
	
// 002 start
	
	public ActionForward fetchDispatchInfo(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		SITransferModel objModel=new SITransferModel();
		ActionMessages messages = new ActionMessages();
		ArrayList<SITransferDto> listBillingInfo = new ArrayList<SITransferDto>();
		try
		{
			
			forward = mapping.findForward("DisplaySelectDispatchInfo");
		}
		catch (Exception e) 
		{
			logger.info("fetchDispatchInfo() method execute  !");
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		return forward;
	}
	

	// 002 end	
	
	

	// 001 end	
	
	public ActionForward ViewSITransferList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward forward = new ActionForward(); // return value
		String forwardMapping = "";
		ArrayList<SITransferDto> SITransferList = new ArrayList<SITransferDto>();
		SITransferModel objService = new SITransferModel();
	     SITransferBean  objForm = (SITransferBean) form;
		
		SITransferDto objDto = new SITransferDto();
		
		try {
			// Validate form fields
			ActionMessages messages = new ActionMessages();
			String searchBatchId=objForm.getSearchBatchId();
			String searchfromDate=objForm.getSearchfromDate();
			String searchToDate=objForm.getSearchToDate();
		    ArrayList errors = new ArrayList();

			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"Batch Id",searchBatchId , 18),
					Utility.generateCSV(Utility.CASE_MAXLENGTH,
							Utility.CASE_DIGITS_ONLY)).appendToAndRetNewErrs(
					errors);

			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"FROM DATE",searchfromDate , 200),
							Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS,
									Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
							errors);

			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"TO DATE",searchToDate , 200),
					Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS,
							Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
					errors);
			
		
			if (errors != null && errors.size() > 0)// During Server Side
			// Validation
			   {
					request.setAttribute("SITransferList", SITransferList);
					objForm.setSiTransferList(SITransferList);
					request.setAttribute("validation_errors", errors);
					saveMessages(request, messages);
					forward = mapping.findForward("DisplayViewSITransferPage");
					//return mapping.findForward("displayOrderList");
			   	}
		
			else {
					objDto.setSearchBatchId(searchBatchId);
					objDto.setSearchfromDate(searchfromDate);
					objDto.setSearchToDate(searchToDate);
			
					PagingSorting pagingSorting = objForm.getPagingSorting();
	
					pagingSorting.setDefaultifNotPresent("BATCH_ID",
							PagingSorting.increment, 1);
					pagingSorting.setPagingSorting(true, true);
					objDto.setPagingSorting(pagingSorting);
	
					SITransferList = objService.ViewSITransferList(objDto);
					request.setAttribute("SITransferList", SITransferList);
					objForm.setSiTransferList(SITransferList);
					forward = mapping.findForward("DisplayViewSITransferPage");
			
				
			  }
		} catch (Exception ed) {
			logger.info("viewSITransferList() method execute  !");
			ed.printStackTrace();
			AppConstants.IOES_LOGGER.error("Error while getting saveUploadedFileInfo "
					+ ed.getMessage());
			throw new IOESException(ed);
		}

	
		return forward;
		
	}
	public ActionForward goToViewSITransfer(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		ActionMessages messages = new ActionMessages();
		  SITransferBean  objForm = (SITransferBean) form;
			SITransferDto objDto = new SITransferDto();
			 SimpleDateFormat sdf  = new SimpleDateFormat("dd-MM-yyyy");
		try
		{
			String searchfromDate=objForm.getSearchfromDate();
			String searchToDate=objForm.getSearchToDate();
			
			if(objForm.getSearchfromDate()==null)
			{
				objForm.setSearchfromDate(sdf.format(new Date(System.currentTimeMillis())));
			}
			else
			{
			objDto.setSearchfromDate(searchfromDate);
			}
			
			if(objForm.getSearchToDate()==null)
			{

				
		 	 	Calendar c1 = Calendar.getInstance();
		 	 	
		 	 	
		 	 	c1.add(c1.DAY_OF_MONTH, +1);

	
				objForm.setSearchToDate(sdf.format(c1.getTime()));
			}
			else
			{
			objDto.setSearchToDate(searchToDate);
			}
			
			forward = mapping.findForward("DisplayViewSITransferPage");
		}
		catch (Exception e) 
		{
			logger.info("goToViewSITransfer() method execute  !");
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
			e.printStackTrace();
		}
		return forward;
	}
	public ActionForward ViewSITransferPage(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		ActionMessages messages = new ActionMessages();
		SITransferModel objService = new SITransferModel();
	     SITransferBean  objForm = (SITransferBean) form;
	     ArrayList<SITransferDto> listSITransferBatchDetails = new ArrayList<SITransferDto>();
	 	ArrayList<SITransferDto> listSITransferDetails = new ArrayList<SITransferDto>();
		ArrayList<SITransferDto> listLogicalSIDetails = new ArrayList<SITransferDto>();
		ArrayList<SITransferDto> listproductsDetails = new ArrayList<SITransferDto>();
		SITransferDto objDto = new SITransferDto();
		HashMap<String ,ArrayList> SITransferDetails=new HashMap<String,ArrayList> ();	
		ArrayList<SITransferDto> listSITransferbatchList_new = new ArrayList<SITransferDto>();
        SITransferDto dto = null;
 		HashSet<String> hs = new HashSet<String>();
		
		try
		{
			
				String arr=	request.getParameter("BatchId");
				SITransferDetails = objService.getSITransferDetails(arr);
				
				listSITransferBatchDetails=	SITransferDetails.get("listSITransferBatchDetails");
				
				 //005 start
				 for (int i = 0; i < listSITransferBatchDetails.size(); i++) {

        				dto = listSITransferBatchDetails.get(i);
        				String batchid = dto.getSearchBatchId();
        				if ((hs.contains(batchid)) == false) {

        					hs.add(batchid);
        					listSITransferbatchList_new.add(dto);

        				}

             	   }   
             	   
				// 005 end
				listSITransferDetails=SITransferDetails.get("listSITransferDetails");
				
				listLogicalSIDetails=SITransferDetails.get("listLogicalSIDetails");
				listproductsDetails=SITransferDetails.get("listproductsDetails");
				
				request.setAttribute("listSITransferBatchDetails", listSITransferbatchList_new);
				
				request.setAttribute("listSITransferDetails", listSITransferDetails);
				
				request.setAttribute("listLogicalSIDetails", listLogicalSIDetails);
				request.setAttribute("listproductsDetails", listproductsDetails);
				
				objForm.setSiTransferBatchDetails(listSITransferBatchDetails);
				objForm.setSiTransferDetails(listSITransferDetails);
				objForm.setLogicalSIDetails(listLogicalSIDetails);
			    forward = mapping.findForward("DisplayDraftSITransferPage");
		}
		
		catch (Exception e) 
		{
			logger.info("ViewSITransferPage() method execute  !");
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
			e.printStackTrace();
		}
		return forward;
	}	
}
