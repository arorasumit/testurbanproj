package com.ibm.ioes.actions;
//Tag Name Resource Name  Date		CSR No			Description
//[001]	MANISHA GARG	28-MAR-11					To Fecth Account List
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Date;
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

import com.ibm.ioes.beans.CurrencyChangeBean;
import com.ibm.ioes.beans.DefaultBean;
import com.ibm.ioes.beans.NewOrderBean;
import com.ibm.ioes.beans.UserInfoDto;
import com.ibm.ioes.beans.ValidationBean;
import com.ibm.ioes.exception.IOESException;
import com.ibm.ioes.forms.NewOrderDto;
import com.ibm.ioes.forms.CurrencyChangeDto;
import com.ibm.ioes.model.CurrencyChangeModel;
import com.ibm.ioes.model.NewOrderModel;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.AppUtility;
import com.ibm.ioes.utilities.PagingSorting;
import com.ibm.ioes.utilities.Utility;

public class CurrencyChangeAction extends DispatchAction {

	private static final Logger logger;
	static {
		logger = Logger.getLogger(CurrencyChangeAction.class);
	}
	public ActionForward goToCurrencyChange(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		ActionMessages messages = new ActionMessages();
		
		try
		{
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			
			request.setAttribute("userroleid", objUserDto.getUserRoleId());
			
			forward = mapping.findForward("DisplayCurrencyChangePage");
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
			e.printStackTrace();
		}
		return forward;
	}
	
	
	public ActionForward fetchParty(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		CurrencyChangeModel objModel=new CurrencyChangeModel();
		ActionMessages messages = new ActionMessages();
		ArrayList<CurrencyChangeDto> listParty = new ArrayList<CurrencyChangeDto>();
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
		CurrencyChangeModel objModel=new CurrencyChangeModel();
		ActionMessages messages = new ActionMessages();
		ArrayList<CurrencyChangeDto> listAccount = new ArrayList<CurrencyChangeDto>();
		try
		{
			//String arr=	request.getParameter("targetPartyNo");
			//listAccount = objModel.fetchAccount(arr);
			//request.setAttribute("AccountList", listAccount);
			forward = mapping.findForward("DisplaySelectAccount");
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		return forward;
	}
	public ActionForward fetchBillingInfo(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		CurrencyChangeModel objModel=new CurrencyChangeModel();
		ActionMessages messages = new ActionMessages();
		ArrayList<CurrencyChangeDto> listBillingInfo = new ArrayList<CurrencyChangeDto>();
		try
		{
			
			forward = mapping.findForward("DisplaySelectBcpInfo");
		}
		catch (Exception e) 
		{
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
		CurrencyChangeModel objModel=new CurrencyChangeModel();
		ActionMessages messages = new ActionMessages();
		ArrayList<CurrencyChangeDto> listBillingInfo = new ArrayList<CurrencyChangeDto>();
		try
		{
			
			forward = mapping.findForward("DisplaySelectDispatchInfo");
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		return forward;
	}
	

	// 002 end	
	
	

	// 001 end	
	
	public ActionForward ViewCurrencyChangeList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward forward = new ActionForward(); // return value
		String forwardMapping = "";
		ArrayList<CurrencyChangeDto> CurrencyChangeList = new ArrayList<CurrencyChangeDto>();
		CurrencyChangeModel objService = new CurrencyChangeModel();
		CurrencyChangeBean  objForm = (CurrencyChangeBean) form;
		
		CurrencyChangeDto objDto = new CurrencyChangeDto();
		
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
					request.setAttribute("CurrencyChangeList", CurrencyChangeList);
					objForm.setCurrencyChangeList(CurrencyChangeList);
					request.setAttribute("validation_errors", errors);
					saveMessages(request, messages);
					forward = mapping.findForward("DisplayViewCurrencyChangePage");
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
	
					CurrencyChangeList = objService.ViewCurrencyChangeList(objDto);
					request.setAttribute("CurrencyChangeList", CurrencyChangeList);
					objForm.setCurrencyChangeList(CurrencyChangeList);
					forward = mapping.findForward("DisplayViewCurrencyChangePage");
			
				
			  }
		} catch (Exception ed) {
			ed.printStackTrace();
			AppConstants.IOES_LOGGER.error("Error while getting saveUploadedFileInfo "
					+ ed.getMessage());
			throw new IOESException(ed);
		}

	
		return forward;
		
	}
	public ActionForward goToViewCurrencyChange(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		ActionMessages messages = new ActionMessages();
		CurrencyChangeBean  objForm = (CurrencyChangeBean) form;
		CurrencyChangeDto objDto = new CurrencyChangeDto();
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
			
			forward = mapping.findForward("DisplayViewCurrencyChangePage");
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
			e.printStackTrace();
		}
		return forward;
	}
	public ActionForward ViewCurrencyChangePage(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		ActionMessages messages = new ActionMessages();
		CurrencyChangeModel objService = new CurrencyChangeModel();
		CurrencyChangeBean  objForm = (CurrencyChangeBean) form;
	     ArrayList<CurrencyChangeDto> listCurrencyChangeBatchDetails = new ArrayList<CurrencyChangeDto>();
	 	ArrayList<CurrencyChangeDto> listCurrencyChangeDetails = new ArrayList<CurrencyChangeDto>();
		ArrayList<CurrencyChangeDto> listLogicalSIDetails = new ArrayList<CurrencyChangeDto>();
		ArrayList<CurrencyChangeDto> listproductsDetails = new ArrayList<CurrencyChangeDto>();
		CurrencyChangeDto objDto = new CurrencyChangeDto();
		HashMap<String ,ArrayList> CurrencyChangeDetails=new HashMap<String,ArrayList> ();	
		
		try
		{
			
				String arr=	request.getParameter("BatchId");
				CurrencyChangeDetails = objService.getCurrencyChangeDetails(arr);
				
				listCurrencyChangeBatchDetails=	CurrencyChangeDetails.get("listCurrencyChangeBatchDetails");
				
				listCurrencyChangeDetails=CurrencyChangeDetails.get("listCurrencyChangeDetails");
				
				listLogicalSIDetails=CurrencyChangeDetails.get("listLogicalSIDetails");
				//listproductsDetails=CurrencyChangeDetails.get("listproductsDetails");
				
				request.setAttribute("listCurrencyChangeBatchDetails", listCurrencyChangeBatchDetails);
				
				request.setAttribute("listCurrencyChangeDetails", listCurrencyChangeDetails);
				
				request.setAttribute("listLogicalSIDetails", listLogicalSIDetails);
				//request.setAttribute("listproductsDetails", listproductsDetails);
				
				objForm.setCurrencyChangeBatchDetails(listCurrencyChangeBatchDetails);
				objForm.setCurrencyChangeDetails(listCurrencyChangeDetails);
				objForm.setLogicalSIDetails(listLogicalSIDetails);
			    forward = mapping.findForward("DisplayDraftCurrencyChangePage");
		}
		
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
			e.printStackTrace();
		}
		return forward;
	}	
	//Calling this method for Bulkupload parameter searching for account and LSI	
	public ActionForward fetchAccountWithParty(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value		
		ActionMessages messages = new ActionMessages();		
		try
		{			
			CurrencyChangeBean formBean=(CurrencyChangeBean)form;
			formBean.setPartyId(request.getParameter("partyId"));			
			forward=mapping.findForward("DisplayAccountForBulk");
			return forward;
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		return forward;
	}
	public ActionForward fetchLSIWithAccounts(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value		
		ActionMessages messages = new ActionMessages();		
		try
		{			
			CurrencyChangeBean formBean=(CurrencyChangeBean)form;
			formBean.setAccountIdList(request.getParameter("accountIDsList"));			
			forward=mapping.findForward("DisplayLSIForBulk");
			return forward;
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		return forward;
	}
	//End
}
