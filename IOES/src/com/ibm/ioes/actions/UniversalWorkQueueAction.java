/**
 * 
 */
// [00044]	 Ashutosh Singh		23-June-11	CSR00-05422     Creating Change Order From Existing order in Billing Queue
// [00055]	 Ashutosh Singh		13-July-11	CSR00-05422     Document Tracking for COPC
// [00066]   Neha Maheshwari    added service segment     NORTH STAR ACCOUNT TAGGING
// [00077]   Raveendra    Reassign to AM      Order Transfer

package com.ibm.ioes.actions;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import org.apache.struts.actions.DispatchAction;
import org.apache.log4j.Logger;

import com.ibm.ioes.beans.NewOrderBean;
import com.ibm.ioes.beans.ReportsBean;
import com.ibm.ioes.beans.UniversalWorkQueueDto;
import com.ibm.ioes.beans.UserInfoDto;
import com.ibm.ioes.beans.ValidationBean;
import com.ibm.ioes.forms.NetworkLocationDto;
import com.ibm.ioes.forms.NewOrderDto;
import com.ibm.ioes.forms.OrderForPendingWithAMDto;
import com.ibm.ioes.forms.SessionObjectsDto;
import com.ibm.ioes.beans.UserInfoDto;
import com.ibm.ioes.dao.UniversalWorkQueueDao;
import com.ibm.ioes.forms.UniversalWorkQueueFormBean;
import com.ibm.ioes.model.CopyOrderModel;
import com.ibm.ioes.model.NewOrderModel;
import com.ibm.ioes.model.ReportsModel;
import com.ibm.ioes.model.UniversalWorkQueueModel;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.Messages;
import com.ibm.ioes.utilities.PagingSorting;
import com.ibm.ioes.utilities.Utility;


/**
 * @author sandpkum
 *
 */


public class UniversalWorkQueueAction extends DispatchAction{

	
	private static final Logger logger;
	static {
		logger = Logger.getLogger(UniversalWorkQueueAction.class);
	}
	
	//Main method for giving the Universal Work Queue List
	
	// Mofified by ramana
	/* public ActionForward viewUniversalWorkQueue(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) 
	 {
		String forwardMapping = null;
		UniversalWorkQueueModel objModel=new UniversalWorkQueueModel();
		UniversalWorkQueueDto objDto=new UniversalWorkQueueDto();
		ArrayList<UniversalWorkQueueDto> listSourceName = new ArrayList<UniversalWorkQueueDto>();
		ArrayList<UniversalWorkQueueDto> listOrderType = new ArrayList<UniversalWorkQueueDto>();
		String universal="universal";
		try 
		{
			listSourceName=objModel.displaySourceDetails(objDto);
			request.setAttribute("listSourceName", listSourceName);			
			listOrderType=objModel.displayOrderTypeDetails(objDto);
			request.setAttribute("listOrderType", listOrderType);
			System.out.println("source name and order type");
			request.setAttribute("universal", universal);
			
			  forwardMapping ="viewUniversalWorkQueue";
			  return mapping.findForward(forwardMapping);
		}
		catch(Exception ex)
     	{
  		//logger.error(ex.getMessage()+ " Exception occured in viewUniversalWorkQueue method of "+ this.getClass().getSimpleName());
			forwardMapping="ErrorPageAction";
			return mapping.findForward(forwardMapping);
     	}		
	 }*/
   // Mofified by ramana
	 
	 
	 
	
	
	
	 //Created by Ramana
	 public ActionForward viewUniversalWorkQueue(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) 
	 {
		logger.info("Universal Work Queue Interface and viewUniversalWorkQueue method has been called");
		String forwardMapping = "";
		
		UniversalWorkQueueFormBean objForm=(UniversalWorkQueueFormBean) form;
		UniversalWorkQueueDto objUniversalWorkQueueDto = new UniversalWorkQueueDto();		
		HttpSession session = request.getSession(true);
		
		UniversalWorkQueueModel objModel=new UniversalWorkQueueModel();
		UniversalWorkQueueDto objDto=new UniversalWorkQueueDto();
		ArrayList<UniversalWorkQueueDto> listSourceName = new ArrayList<UniversalWorkQueueDto>();
		ArrayList<UniversalWorkQueueDto> listOrderType = new ArrayList<UniversalWorkQueueDto>();
		ArrayList<UniversalWorkQueueDto> listSubChangeType = new ArrayList<UniversalWorkQueueDto>();
		ArrayList<UniversalWorkQueueDto> listRegionName = new ArrayList<UniversalWorkQueueDto>();
		ArrayList<UniversalWorkQueueDto> listOrderStageName = new ArrayList<UniversalWorkQueueDto>();
        //[00066] Start
		ArrayList<UniversalWorkQueueDto> listServiceSegmentName = new ArrayList<UniversalWorkQueueDto>();
        //[00066] End
		ArrayList<UniversalWorkQueueDto> listIndustrySegmentName = new ArrayList<UniversalWorkQueueDto>();
		ArrayList<UniversalWorkQueueDto> listCustomerSegmentName = new ArrayList<UniversalWorkQueueDto>();
		try 
		{
			ActionMessages messages = new ActionMessages();
			/*SessionObjectsDto.getInstance().setUserId("1");
			SessionObjectsDto.getInstance().setUserName("Admin");
			SessionObjectsDto.getInstance().setRoleName("Account Manager");
			SessionObjectsDto.getInstance().setUserRoleId("1");
			session.setAttribute(AppConstants.APP_SESSION_USER_INFO, SessionObjectsDto.getInstance());*/
			//ServletContext ctx=getServlet().getServletContext();
			//HashMap<String, HttpSession> htMap = (HashMap<String, HttpSession>) ctx.getAttribute(AppConstants.APP_SESSION);

			/*htMap.put(session.getId(), session);
			HttpSession sessionObj=htMap.get(session.getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			//objForm.setCurrentRole(SessionObjectsDto.getInstance().getUserRoleId());
			objForm.setCurrentRole(objUserDto.getUserRoleId());	
			//objUniversalWorkQueueDto.setCurrentRole(SessionObjectsDto.getInstance().getUserRoleId());
			objUniversalWorkQueueDto.setCurrentRole(objUserDto.getUserRoleId());

			htMap.put(session.getId(), session);*/
			
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(session.getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);

			objForm.setCurrentRole(objUserDto.getUserRoleId());
			objUniversalWorkQueueDto.setCurrentRole(objUserDto.getUserRoleId());
			//RAMANA
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
			String searchOrderStage=objForm.getSearchOrderStage();
			String searchSubChangeType=objForm.getSearchSubChangeType();
			String searchRegion= objForm.getSearchRegion();
			String searchDemoType=objForm.getSearchDemoType();
			String searchPartyNumber=objForm.getSearchPartyNumber();
            //[00066] Start
			String searchServiceSegment=objForm.getSearchServiceSegment();
            //[00066] End
			String searchIndustrySegment=objForm.getSearchIndustrySegment();
			String searchAmFromDate=objForm.getSearchAmFromDate();
			String searchAmToDate=objForm.getSearchAmToDate();
			String searchPmFromDate=objForm.getSearchPmFromDate();
			String searchPmToDate=objForm.getSearchPmToDate();
			String searchCopcFromDate=objForm.getSearchCopcToDate();
			String searchCopcToDate=objForm.getSearchCopcToDate();
			String searchCustomerSegment=objForm.getSearchCustomerSegment();
			
			ArrayList errors = new ArrayList();

			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"CRM/O No",searchCRMOrder , 18),
					Utility.generateCSV(Utility.CASE_MAXLENGTH
							)).appendToAndRetNewErrs(
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
							"FROM DATE",searchfromDate , 200),
					Utility.generateCSV(
							Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
					errors);
			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"TO DATE",searchToDate , 200),
					Utility.generateCSV(
							Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
					errors);
			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"SOURCE NAME",searchSource , 200),
					Utility.generateCSV(
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
					Utility.generateCSV(
							Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
					errors);
			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"STAGE",searchOrderStage, 200),
					Utility.generateCSV(
							Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
					errors);
			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"SUB CHANGE TYPE",searchSubChangeType , 200),
					Utility.generateCSV(
							Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
					errors);
			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"PARTY NO",searchPartyNumber , 18),
					Utility.generateCSV(Utility.CASE_MAXLENGTH
					)).appendToAndRetNewErrs(
					errors);
			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"AM FROM DATE",searchAmFromDate , 200),
					Utility.generateCSV(
							Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
					errors);
			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"AM TO DATE",searchAmToDate , 200),
					Utility.generateCSV(
							Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
					errors);
			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"PM FROM DATE",searchPmFromDate , 200),
					Utility.generateCSV(
							Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
					errors);
			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"PM TO DATE",searchPmToDate , 200),
					Utility.generateCSV(
							Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
					errors);
			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"COPC FROM DATE",searchCopcFromDate , 200),
					Utility.generateCSV(
							Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
					errors);
			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"COPC TO DATE",searchCopcToDate , 200),
					Utility.generateCSV(
							Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
					errors);
			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"REGION",searchRegion , 200),
					Utility.generateCSV(
							Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
					errors);
			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"DEMO TYPE",searchDemoType , 200),
					Utility.generateCSV(
							Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
					errors);
			//[00066] Start
			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"SERVICE SEGMENT",searchServiceSegment , 200),
					Utility.generateCSV(
							Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
					errors);
			//[00066] Start
			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"INDUSTRY SEGMENT",searchIndustrySegment , 200),
					Utility.generateCSV(
							Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
					errors);
			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"CUSTOMER SEGMENT",searchCustomerSegment , 200),
					Utility.generateCSV(
							Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
					errors);
			
			//RAMANA
			UniversalWorkQueueModel objUniversalWorkQueueModel=new UniversalWorkQueueModel();
			objUniversalWorkQueueDto.setIsBillingOrder("0");
			objForm.setIsBillingOrder("0");			
			//ArrayList aList=objUniversalWorkQueueModel.getUniversalWorkQueueList(objUniversalWorkQueueDto,objUserDto);
			if (errors != null && errors.size() > 0)// During Server SideR
				// Validation
				{
					//request.setAttribute("universalWorkQueueList", aList);
					//objForm.setOrderList(aList);
					request.setAttribute("validation_errors", errors);
					saveMessages(request, messages);
					return mapping.findForward("UniversalWorkQueueMainPage");
				}	
			else {
				objUniversalWorkQueueDto.setSearchCRMOrder(searchCRMOrder);
				objUniversalWorkQueueDto.setSearchAccountNo(searchAccountNo);
				objUniversalWorkQueueDto.setSearchAccountName(searchAccountName);
				objUniversalWorkQueueDto.setSearchOrderType(searchOrderType);
				objUniversalWorkQueueDto.setSearchfromDate(searchfromDate);
				objUniversalWorkQueueDto.setSearchToDate(searchToDate);
				objUniversalWorkQueueDto.setSearchSource(searchSource);
				objUniversalWorkQueueDto.setSearchCurrency(searchCurrency);
				objUniversalWorkQueueDto.setSearchQuoteNumber(searchQuoteNumber);
				objUniversalWorkQueueDto.setSearchOrderStageName(searchOrderStage);
				objUniversalWorkQueueDto.setSearchRegionName(searchRegion);
				objUniversalWorkQueueDto.setSearchDemoType(searchDemoType);
				objUniversalWorkQueueDto.setSearchPartyNumber(searchPartyNumber);
                //[00066] Start
				objUniversalWorkQueueDto.setSearchServiceSegmentName(searchServiceSegment);
                //[00066] End
				objUniversalWorkQueueDto.setSearchIndustrySegmentName(searchIndustrySegment);
				objUniversalWorkQueueDto.setSearchAmFromDate(searchAmFromDate);
				objUniversalWorkQueueDto.setSearchAmToDate(searchAmToDate);
				objUniversalWorkQueueDto.setSearchPmFromDate(searchPmFromDate);
				objUniversalWorkQueueDto.setSearchPmToDate(searchPmToDate);
				objUniversalWorkQueueDto.setSearchCopcFromDate(searchCopcFromDate);
				objUniversalWorkQueueDto.setSearchCopcToDate(searchCopcToDate);
				objUniversalWorkQueueDto.setSearchCustomerSegmentName(searchCustomerSegment);
				objUniversalWorkQueueDto.setSearchSubchangeType(searchSubChangeType);

				PagingSorting pagingSorting = objForm.getPagingSorting();

				pagingSorting.setDefaultifNotPresent("ORDERNO",
						PagingSorting.increment, 1);
				pagingSorting.setPagingSorting(true, true);
				objUniversalWorkQueueDto.setPagingSorting(pagingSorting);
				
				listSourceName=objModel.displaySourceDetails(objDto);
				request.setAttribute("listSourceName", listSourceName);			
				
				listOrderType=objModel.displayOrderTypeDetails(objDto);
				request.setAttribute("listOrderType", listOrderType);
				
				listSubChangeType=objModel.displaySubChangeType(objDto);
				request.setAttribute("listSubChangeType", listSubChangeType);
				
				listRegionName=objModel.displayRegion(objDto);
				request.setAttribute("listRegionName", listRegionName);
				
				listOrderStageName=objModel.displayOrderStage(objDto);
				request.setAttribute("listOrderStageName", listOrderStageName);
				
                //[00066] Start
				listServiceSegmentName=objModel.displayServiceSegment(objDto);
				request.setAttribute("listServiceSegmentName", listServiceSegmentName);
				
                //[00066] End
				listIndustrySegmentName=objModel.displayIndustrySegment(objDto);
				request.setAttribute("listIndustrySegmentName", listIndustrySegmentName);
				
				listCustomerSegmentName=objModel.displayCustomerSegment(objDto);
				request.setAttribute("listCustomerSegmentName", listCustomerSegmentName);
				
				String inExcel = objForm.getToExcel();
				   if ("true".equals(inExcel)) {
						pagingSorting.setPagingSorting(false, true);
						forwardMapping = "displayReportInExcel";
					} else {
						pagingSorting.setPagingSorting(true, true);
						forwardMapping = "UniversalWorkQueueMainPage";
					}
				   
				    ArrayList universalWorkQueueList = objUniversalWorkQueueModel.getUniversalWorkQueueList(objUniversalWorkQueueDto,objUserDto);
				    
				    if ("true".equals(inExcel)) {
						objForm.setUniversalWorkQueueList(universalWorkQueueList);
						request.setAttribute("universalExcelList", universalWorkQueueList);
					} else {
						request.setAttribute("universalWorkQueueList", universalWorkQueueList);
						objForm.setUniversalWorkQueueList(universalWorkQueueList);
					}
				   
				   

				  // objForm.setUniversalWorkQueueList(universalWorkQueueList);
	              //UniversalWorkQueueAction  a=new UniversalWorkQueueAction();
	               //a.viewUniversalWorkQueue(mapping,form,request,response);
				
				
				
				return mapping.findForward(forwardMapping);
				//objForm.setOrderList(aList); ramana
               //defaultAction a=new defaultAction(); ramana
               //a.viewIncompleteOrder(mapping,form,request,response); ramana
			
				
			}
		
		}
		catch(Exception ex)
    	{
    		logger.error(ex.getMessage()+ " Exception occured in viewUniversalWorkQueue method of "+ this.getClass().getSimpleName());
			forwardMapping="ErrorPageAction";
			return mapping.findForward(forwardMapping);
    	}
		//return mapping.findForward(forwardMapping);
	 }	
	 //[00044] Start
	 public ActionForward viewBillingOrderForChange(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) 
	 {
		 

			logger.info("Universal Work Queue Interface and viewBillingOrderForChange method has been called");
			String forwardMapping = null;
			
			UniversalWorkQueueFormBean objForm=(UniversalWorkQueueFormBean) form;
			UniversalWorkQueueDto objUniversalWorkQueueDto = new UniversalWorkQueueDto();
			HttpSession session = request.getSession(true);
			
			UniversalWorkQueueModel objModel=new UniversalWorkQueueModel();
			UniversalWorkQueueDto objDto=new UniversalWorkQueueDto();
			ArrayList<UniversalWorkQueueDto> listSourceName = new ArrayList<UniversalWorkQueueDto>();
			ArrayList<UniversalWorkQueueDto> listOrderType = new ArrayList<UniversalWorkQueueDto>();
			ArrayList<UniversalWorkQueueDto> listSubChangeType = new ArrayList<UniversalWorkQueueDto>();
			try 
			{
				ActionMessages messages = new ActionMessages();

				/*SessionObjectsDto.getInstance().setUserId("1");

	/*			SessionObjectsDto.getInstance().setUserId("1");

				SessionObjectsDto.getInstance().setUserName("Admin");
				SessionObjectsDto.getInstance().setRoleName("Account Manager");
				SessionObjectsDto.getInstance().setUserRoleId("1");
				session.setAttribute(AppConstants.APP_SESSION_USER_INFO, SessionObjectsDto.getInstance());*/
				ServletContext ctx=getServlet().getServletContext();
				HashMap<String, HttpSession> htMap = (HashMap<String, HttpSession>) ctx.getAttribute(AppConstants.APP_SESSION);

				/*HttpSession sessionObj=htMap.get(session.getId());
				UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				//objForm.setCurrentRole(SessionObjectsDto.getInstance().getUserRoleId());
				objForm.setCurrentRole(objUserDto.getUserRoleId());
				//objUniversalWorkQueueDto.setCurrentRole(SessionObjectsDto.getInstance().getUserRoleId());
				objUniversalWorkQueueDto.setCurrentRole(objUserDto.getUserRoleId());*/

				
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				
				objForm.setCurrentRole(objUserDto.getUserRoleId());
				objUniversalWorkQueueDto.setCurrentRole(objUserDto.getUserRoleId());
				objUniversalWorkQueueDto.setIsBillingOrder("1");
				objForm.setIsBillingOrder("1");
				
//				RAMANA
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
				String searchSubChangeType=objForm.getSearchSubChangeType();
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
				
				Utility.validateValue(
						new ValidationBean().loadValidationBean_Maxlength(
								"SUB CHANGE TYPE",searchSubChangeType , 200),
						Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS,
								Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
						errors);
				
				//RAMANA
				UniversalWorkQueueModel objUniversalWorkQueueModel=new UniversalWorkQueueModel();
				//ArrayList aList=objUniversalWorkQueueModel.getUniversalWorkQueueList(objUniversalWorkQueueDto,objUserDto);
				
				//Ramana
				if (errors != null && errors.size() > 0)// During Server Side
					// Validation
					{					
						request.setAttribute("validation_errors", errors);
						saveMessages(request, messages);
						return mapping.findForward("viewBillingOrderForChange");
					}	
				else {
					objUniversalWorkQueueDto.setSearchCRMOrder(searchCRMOrder);
					objUniversalWorkQueueDto.setSearchAccountNo(searchAccountNo);
					objUniversalWorkQueueDto.setSearchAccountName(searchAccountName);
					objUniversalWorkQueueDto.setSearchOrderType(searchOrderType);
					objUniversalWorkQueueDto.setSearchfromDate(searchfromDate);
					objUniversalWorkQueueDto.setSearchToDate(searchToDate);
				
					objUniversalWorkQueueDto.setSearchSource(searchSource);
					objUniversalWorkQueueDto.setSearchCurrency(searchCurrency);
					objUniversalWorkQueueDto.setSearchQuoteNumber(searchQuoteNumber);
					objUniversalWorkQueueDto.setSearchStageName(searchStageName);
					objUniversalWorkQueueDto.setSearchSubchangeType(searchSubChangeType);
					

					PagingSorting pagingSorting = objForm.getPagingSorting();

					pagingSorting.setDefaultifNotPresent("ORDERNO",
							PagingSorting.increment, 1);
					pagingSorting.setPagingSorting(true, true);
					objUniversalWorkQueueDto.setPagingSorting(pagingSorting);
					
					listSourceName=objModel.displaySourceDetails(objDto);
					request.setAttribute("listSourceName", listSourceName);			
					listOrderType=objModel.displayOrderTypeDetails(objDto);
					request.setAttribute("listOrderType", listOrderType);	
					listSubChangeType=objModel.displaySubChangeType(objDto);
					request.setAttribute("listSubChangeType", listSubChangeType);
					 
					
					ArrayList billingWorkQueueList = objUniversalWorkQueueModel.getUniversalWorkQueueList(objUniversalWorkQueueDto,objUserDto);
					   request.setAttribute("billingWorkQueueList", billingWorkQueueList);
					   objForm.setBillingWorkQueueList(billingWorkQueueList);
		              //UniversalWorkQueueAction  a=new UniversalWorkQueueAction();
		              // a.viewBillingOrderForChange(mapping,form,request,response);
		               String moduleName=request.getParameter("moduleName");
		               request.setAttribute("ModuleName", moduleName);
		               return mapping.findForward("viewBillingOrderForChange");

					//aList = objUniversalWorkQueueModel.getUniversalWorkQueueList(objUniversalWorkQueueDto,objUserDto);
					//request.setAttribute("universalWorkQueueList", aList);
					//return mapping.findForward("UniversalWorkQueueMainPage");
					//objForm.setOrderList(aList); ramana
	               //defaultAction a=new defaultAction(); ramana
	               //a.viewIncompleteOrder(mapping,form,request,response); ramana
				
					
				}
				//Ramana
				//request.setAttribute("universalWorkQueueList", aList);
				//forwardMapping ="UniversalWorkQueueMainPage";
			}
			catch(Exception ex)
	    	{
	    		logger.error(ex.getMessage()+ " Exception occured in viewBillingOrderForChange method of "+ this.getClass().getSimpleName());
				forwardMapping="ErrorPageAction";
				return mapping.findForward(forwardMapping);
	    	}
	 }
	 //[00044] End
	 
	
	 
	 //[00055] Start 
	 public ActionForward viewBillingOrderForDocumentTracking(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) 
	 {
		 

			logger.info("Universal Work Queue Interface and viewBillingOrderForChange method has been called");
			String forwardMapping = null;
			
			UniversalWorkQueueFormBean objForm=(UniversalWorkQueueFormBean) form;
			UniversalWorkQueueDto objUniversalWorkQueueDto = new UniversalWorkQueueDto();
			HttpSession session = request.getSession(true);
			
			UniversalWorkQueueModel objModel=new UniversalWorkQueueModel();
			UniversalWorkQueueDto objDto=new UniversalWorkQueueDto();
			ArrayList<UniversalWorkQueueDto> listSourceName = new ArrayList<UniversalWorkQueueDto>();
			ArrayList<UniversalWorkQueueDto> listOrderType = new ArrayList<UniversalWorkQueueDto>();
			ArrayList<UniversalWorkQueueDto> listSubChangeType = new ArrayList<UniversalWorkQueueDto>();
			
			try 
			{
				ActionMessages messages = new ActionMessages();

				/*SessionObjectsDto.getInstance().setUserId("1");

	/*			SessionObjectsDto.getInstance().setUserId("1");

				SessionObjectsDto.getInstance().setUserName("Admin");
				SessionObjectsDto.getInstance().setRoleName("Account Manager");
				SessionObjectsDto.getInstance().setUserRoleId("1");
				session.setAttribute(AppConstants.APP_SESSION_USER_INFO, SessionObjectsDto.getInstance());*/
				ServletContext ctx=getServlet().getServletContext();
				HashMap<String, HttpSession> htMap = (HashMap<String, HttpSession>) ctx.getAttribute(AppConstants.APP_SESSION);

				/*HttpSession sessionObj=htMap.get(session.getId());
				UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				//objForm.setCurrentRole(SessionObjectsDto.getInstance().getUserRoleId());
				objForm.setCurrentRole(objUserDto.getUserRoleId());
				//objUniversalWorkQueueDto.setCurrentRole(SessionObjectsDto.getInstance().getUserRoleId());
				objUniversalWorkQueueDto.setCurrentRole(objUserDto.getUserRoleId());*/

				
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				
				objForm.setCurrentRole(objUserDto.getUserRoleId());
				objUniversalWorkQueueDto.setCurrentRole(objUserDto.getUserRoleId());
				objUniversalWorkQueueDto.setIsBillingOrder("1");
				objForm.setIsBillingOrder("1");
				
//				RAMANA
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
				String searchSubChangeType=objForm.getSearchSubChangeType();
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
				
				Utility.validateValue(
						new ValidationBean().loadValidationBean_Maxlength(
								"SUB CHANGE TYPE",searchSubChangeType , 200),
						Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS,
								Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
						errors);
				
				//RAMANA
				UniversalWorkQueueModel objUniversalWorkQueueModel=new UniversalWorkQueueModel();
				//ArrayList aList=objUniversalWorkQueueModel.getUniversalWorkQueueList(objUniversalWorkQueueDto,objUserDto);
				
				//Ramana
				if (errors != null && errors.size() > 0)// During Server Side
					// Validation
					{					
						request.setAttribute("validation_errors", errors);
						saveMessages(request, messages);
						return mapping.findForward("viewBillingOrderForChange");
					}	
				else {
					objUniversalWorkQueueDto.setSearchCRMOrder(searchCRMOrder);
					objUniversalWorkQueueDto.setSearchAccountNo(searchAccountNo);
					objUniversalWorkQueueDto.setSearchAccountName(searchAccountName);
					objUniversalWorkQueueDto.setSearchOrderType(searchOrderType);
					objUniversalWorkQueueDto.setSearchfromDate(searchfromDate);
					objUniversalWorkQueueDto.setSearchToDate(searchToDate);
				
					objUniversalWorkQueueDto.setSearchSource(searchSource);
					objUniversalWorkQueueDto.setSearchCurrency(searchCurrency);
					objUniversalWorkQueueDto.setSearchQuoteNumber(searchQuoteNumber);
					objUniversalWorkQueueDto.setSearchStageName(searchStageName);
					objUniversalWorkQueueDto.setSearchSubchangeType(searchSubChangeType);
					

					PagingSorting pagingSorting = objForm.getPagingSorting();

					pagingSorting.setDefaultifNotPresent("ORDERNO",
							PagingSorting.increment, 1);
					pagingSorting.setPagingSorting(true, true);
					objUniversalWorkQueueDto.setPagingSorting(pagingSorting);
					
					listSourceName=objModel.displaySourceDetails(objDto);
					request.setAttribute("listSourceName", listSourceName);			
					listOrderType=objModel.displayOrderTypeDetails(objDto);
					request.setAttribute("listOrderType", listOrderType);	
					listSubChangeType=objModel.displaySubChangeType(objDto);
					request.setAttribute("listSubChangeType", listSubChangeType);
					 
					
					ArrayList billingWorkQueueList = objUniversalWorkQueueModel.getUniversalWorkQueueList(objUniversalWorkQueueDto,objUserDto);
					   request.setAttribute("billingWorkQueueList", billingWorkQueueList);
					   objForm.setBillingWorkQueueList(billingWorkQueueList);
		              //UniversalWorkQueueAction  a=new UniversalWorkQueueAction();
		              // a.viewBillingOrderForChange(mapping,form,request,response);		              
		               request.setAttribute("ModuleName", "DocumentTracking");
		               return mapping.findForward("viewBillingOrderForChange");

					//aList = objUniversalWorkQueueModel.getUniversalWorkQueueList(objUniversalWorkQueueDto,objUserDto);
					//request.setAttribute("universalWorkQueueList", aList);
					//return mapping.findForward("UniversalWorkQueueMainPage");
					//objForm.setOrderList(aList); ramana
	               //defaultAction a=new defaultAction(); ramana
	               //a.viewIncompleteOrder(mapping,form,request,response); ramana
				
					
				}
				//Ramana
				//request.setAttribute("universalWorkQueueList", aList);
				//forwardMapping ="UniversalWorkQueueMainPage";
			}
			catch(Exception ex)
	    	{
	    		logger.error(ex.getMessage()+ " Exception occured in viewBillingOrderForDocumentTracking method of "+ this.getClass().getSimpleName());
				forwardMapping="ErrorPageAction";
				return mapping.findForward(forwardMapping);
	    	}
	 }
	 public ActionForward viewDocumentTrackingList(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) 
	 {
		 return mapping.findForward("viewDocumentTrackingList");
	 }
	 //[00055] End
	 
	 //ramana
	 
	 //Modified by Ramana
	/*public ActionForward viewBillingOrderQueue(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) 
	 {
		String forwardMapping = null;
		UniversalWorkQueueModel objModel=new UniversalWorkQueueModel();
		UniversalWorkQueueDto objDto=new UniversalWorkQueueDto();
		ArrayList<UniversalWorkQueueDto> listSourceName = new ArrayList<UniversalWorkQueueDto>();
		ArrayList<UniversalWorkQueueDto> listOrderType = new ArrayList<UniversalWorkQueueDto>();
		String billing="billing";
		try 
		{
			listSourceName=objModel.displaySourceDetails(objDto);
			request.setAttribute("listSourceName", listSourceName);			
			listOrderType=objModel.displayOrderTypeDetails(objDto);
			request.setAttribute("listOrderType", listOrderType);	
			request.setAttribute("billing", billing);
			  forwardMapping ="viewBillingOrderQueue";
			  return mapping.findForward(forwardMapping);
		}
		catch(Exception ex)
     	{
  		//logger.error(ex.getMessage()+ " Exception occured in viewUniversalWorkQueue method of "+ this.getClass().getSimpleName());
			forwardMapping="ErrorPageAction";
			return mapping.findForward(forwardMapping);
     	}		
	 }*/
	 // Modified by Ramana
	 
	 // Created by Ramana
	 public ActionForward viewBillingOrderQueue(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) 
	 {
		logger.info("Universal Work Queue Interface and viewUniversalWorkQueue method has been called");
		String forwardMapping = null;
		
		UniversalWorkQueueFormBean objForm=(UniversalWorkQueueFormBean) form;
		UniversalWorkQueueDto objUniversalWorkQueueDto = new UniversalWorkQueueDto();
		HttpSession session = request.getSession(true);
		
		UniversalWorkQueueModel objModel=new UniversalWorkQueueModel();
		UniversalWorkQueueDto objDto=new UniversalWorkQueueDto();
		ArrayList<UniversalWorkQueueDto> listSourceName = new ArrayList<UniversalWorkQueueDto>();
		ArrayList<UniversalWorkQueueDto> listOrderType = new ArrayList<UniversalWorkQueueDto>();
		ArrayList<UniversalWorkQueueDto> listSubChangeType = new ArrayList<UniversalWorkQueueDto>();
		ArrayList<UniversalWorkQueueDto> listRegionName = new ArrayList<UniversalWorkQueueDto>();
		ArrayList<UniversalWorkQueueDto> listOrderStageName = new ArrayList<UniversalWorkQueueDto>();
        //[00066] Start
		ArrayList<UniversalWorkQueueDto> listServiceSegmentName = new ArrayList<UniversalWorkQueueDto>();
        //[00066] End
		ArrayList<UniversalWorkQueueDto> listIndustrySegmentName = new ArrayList<UniversalWorkQueueDto>();
		ArrayList<UniversalWorkQueueDto> listCustomerSegmentName = new ArrayList<UniversalWorkQueueDto>();
		try 
		{
			ActionMessages messages = new ActionMessages();

			/*SessionObjectsDto.getInstance().setUserId("1");

/*			SessionObjectsDto.getInstance().setUserId("1");

			SessionObjectsDto.getInstance().setUserName("Admin");
			SessionObjectsDto.getInstance().setRoleName("Account Manager");
			SessionObjectsDto.getInstance().setUserRoleId("1");
			session.setAttribute(AppConstants.APP_SESSION_USER_INFO, SessionObjectsDto.getInstance());*/
			//ServletContext ctx=getServlet().getServletContext();
			//HashMap<String, HttpSession> htMap = (HashMap<String, HttpSession>) ctx.getAttribute(AppConstants.APP_SESSION);

			/*HttpSession sessionObj=htMap.get(session.getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			//objForm.setCurrentRole(SessionObjectsDto.getInstance().getUserRoleId());
			objForm.setCurrentRole(objUserDto.getUserRoleId());
			//objUniversalWorkQueueDto.setCurrentRole(SessionObjectsDto.getInstance().getUserRoleId());
			objUniversalWorkQueueDto.setCurrentRole(objUserDto.getUserRoleId());*/

			
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			
			objForm.setCurrentRole(objUserDto.getUserRoleId());
			objUniversalWorkQueueDto.setCurrentRole(objUserDto.getUserRoleId());
			objUniversalWorkQueueDto.setIsBillingOrder("1");
			objForm.setIsBillingOrder("1");
			
//			RAMANA
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
			String searchOrderStage=objForm.getSearchOrderStage();
			String searchSubChangeType=objForm.getSearchSubChangeType();
			String searchRegion= objForm.getSearchRegion();
			String searchDemoType=objForm.getSearchDemoType();
			String searchPartyNumber=objForm.getSearchPartyNumber();
            //[00066] Start
			String searchServiceSegment=objForm.getSearchServiceSegment();
            //[00066] End
			String searchIndustrySegment=objForm.getSearchIndustrySegment();
			String searchAmFromDate=objForm.getSearchAmFromDate();
			String searchAmToDate=objForm.getSearchAmToDate();
			String searchPmFromDate=objForm.getSearchPmFromDate();
			String searchPmToDate=objForm.getSearchPmToDate();
			String searchCopcFromDate=objForm.getSearchCopcFromDate();
			String searchCopcToDate=objForm.getSearchCopcToDate();
			String searchCustomerSegment=objForm.getSearchCustomerSegment();
			ArrayList errors = new ArrayList();

			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"CRM/O No",searchCRMOrder , 18),
					Utility.generateCSV(Utility.CASE_MAXLENGTH
							)).appendToAndRetNewErrs(
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
							"FROM DATE",searchfromDate , 200),
					Utility.generateCSV(
							Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
					errors);
			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"TO DATE",searchToDate , 200),
					Utility.generateCSV(
							Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
					errors);
			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"SOURCE NAME",searchSource , 200),
					Utility.generateCSV(
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
					Utility.generateCSV(
							Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
					errors);
			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"STAGE",searchOrderStage, 200),
					Utility.generateCSV(
							Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
					errors);
			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"SUB CHANGE TYPE",searchSubChangeType , 200),
					Utility.generateCSV(
							Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
					errors);
			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"PARTY NO",searchPartyNumber , 18),
					Utility.generateCSV(
							Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
					errors);
			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"AM FROM DATE",searchAmFromDate , 200),
					Utility.generateCSV(
							Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
					errors);
			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"AM TO DATE",searchAmToDate , 200),
					Utility.generateCSV(
							Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
					errors);
			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"PM FROM DATE",searchPmFromDate , 200),
					Utility.generateCSV(
							Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
					errors);
			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"PM TO DATE",searchPmToDate , 200),
					Utility.generateCSV(
							Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
					errors);
			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"COPC FROM DATE",searchCopcFromDate , 200),
					Utility.generateCSV(
							Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
					errors);
			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"COPC TO DATE",searchCopcToDate , 200),
					Utility.generateCSV(
							Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
					errors);
			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"REGION",searchRegion , 200),
					Utility.generateCSV(
							Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
					errors);
			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"DEMO TYPE",searchDemoType , 200),
					Utility.generateCSV(
							Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
					errors);
			//[00066] Start
			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"SERVICE SEGMENT",searchServiceSegment , 200),
					Utility.generateCSV(
							Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
					errors);
			//[00066] End
			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"INDUSTRY SEGMENT",searchIndustrySegment , 200),
					Utility.generateCSV(
							Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
					errors);
			Utility.validateValue(
					new ValidationBean().loadValidationBean_Maxlength(
							"CUSTOMER SEGMENT",searchCustomerSegment , 200),
					Utility.generateCSV(
							Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
					errors);
			
			//RAMANA
			UniversalWorkQueueModel objUniversalWorkQueueModel=new UniversalWorkQueueModel();
			//ArrayList aList=objUniversalWorkQueueModel.getUniversalWorkQueueList(objUniversalWorkQueueDto,objUserDto);
			
			//Ramana
			if (errors != null && errors.size() > 0)// During Server Side
				// Validation
				{					
					request.setAttribute("validation_errors", errors);
					saveMessages(request, messages);
					return mapping.findForward("viewBillingOrderQueueMainPage");
				}	
			else {
				objUniversalWorkQueueDto.setSearchCRMOrder(searchCRMOrder);
				objUniversalWorkQueueDto.setSearchAccountNo(searchAccountNo);
				objUniversalWorkQueueDto.setSearchAccountName(searchAccountName);
				objUniversalWorkQueueDto.setSearchOrderType(searchOrderType);
				objUniversalWorkQueueDto.setSearchfromDate(searchfromDate);
				objUniversalWorkQueueDto.setSearchToDate(searchToDate);
				objUniversalWorkQueueDto.setSearchSource(searchSource);
				objUniversalWorkQueueDto.setSearchCurrency(searchCurrency);
				objUniversalWorkQueueDto.setSearchQuoteNumber(searchQuoteNumber);
				objUniversalWorkQueueDto.setSearchSubchangeType(searchSubChangeType);
				objUniversalWorkQueueDto.setSearchOrderStageName(searchOrderStage);
				objUniversalWorkQueueDto.setSearchRegionName(searchRegion);
				objUniversalWorkQueueDto.setSearchDemoType(searchDemoType);
				objUniversalWorkQueueDto.setSearchPartyNumber(searchPartyNumber);
                //[00066] Start
				objUniversalWorkQueueDto.setSearchServiceSegmentName(searchServiceSegment);
                //[00066] End
				objUniversalWorkQueueDto.setSearchIndustrySegmentName(searchIndustrySegment);
				objUniversalWorkQueueDto.setSearchAmFromDate(searchAmFromDate);
				objUniversalWorkQueueDto.setSearchAmToDate(searchAmToDate);
				objUniversalWorkQueueDto.setSearchPmFromDate(searchPmFromDate);
				objUniversalWorkQueueDto.setSearchPmToDate(searchPmToDate);
				objUniversalWorkQueueDto.setSearchCopcFromDate(searchCopcFromDate);
				objUniversalWorkQueueDto.setSearchCopcToDate(searchCopcToDate);
				objUniversalWorkQueueDto.setSearchCustomerSegmentName(searchCustomerSegment);
				

				PagingSorting pagingSorting = objForm.getPagingSorting();

				pagingSorting.setDefaultifNotPresent("ORDERNO",
						PagingSorting.increment, 1);
				pagingSorting.setPagingSorting(true, true);
				objUniversalWorkQueueDto.setPagingSorting(pagingSorting);
				
				listSourceName=objModel.displaySourceDetails(objDto);
				request.setAttribute("listSourceName", listSourceName);			
				
				listOrderType=objModel.displayOrderTypeDetails(objDto);
				request.setAttribute("listOrderType", listOrderType);	
				
				listSubChangeType=objModel.displaySubChangeType(objDto);
				request.setAttribute("listSubChangeType", listSubChangeType);
				
				listRegionName=objModel.displayRegion(objDto);
				request.setAttribute("listRegionName", listRegionName);
				
				listOrderStageName=objModel.displayOrderStage(objDto);
				request.setAttribute("listOrderStageName", listOrderStageName);
				
                //[00066] Start
				listServiceSegmentName = objModel.displayServiceSegment(objDto);
				request.setAttribute("listServiceSegmentName", listServiceSegmentName);
				
                //[00066] End
				listIndustrySegmentName=objModel.displayIndustrySegment(objDto);
				request.setAttribute("listIndustrySegmentName", listIndustrySegmentName);
				
				listCustomerSegmentName=objModel.displayCustomerSegment(objDto);
				request.setAttribute("listCustomerSegmentName", listCustomerSegmentName);
				
				String inExcel = objForm.getToExcel();
				   if ("true".equals(inExcel)) {
						pagingSorting.setPagingSorting(false, true);
						forwardMapping = "displayReportInExcel";
					} else {
						pagingSorting.setPagingSorting(true, true);
						forwardMapping = "viewBillingOrderQueueMainPage";
					}
				
				ArrayList billingWorkQueueList = objUniversalWorkQueueModel.getUniversalWorkQueueList(objUniversalWorkQueueDto,objUserDto);
				   
	              //UniversalWorkQueueAction  a=new UniversalWorkQueueAction();
	               //a.viewBillingOrderQueue(mapping,form,request,response);
				   if ("true".equals(inExcel)) {
						objForm.setUniversalWorkQueueList(billingWorkQueueList);
						request.setAttribute("billingExcelList", billingWorkQueueList);
					} else {
						request.setAttribute("billingWorkQueueList", billingWorkQueueList);
						   objForm.setBillingWorkQueueList(billingWorkQueueList);
					}
	               
	               return mapping.findForward(forwardMapping);

				//aList = objUniversalWorkQueueModel.getUniversalWorkQueueList(objUniversalWorkQueueDto,objUserDto);
				//request.setAttribute("universalWorkQueueList", aList);
				//return mapping.findForward("UniversalWorkQueueMainPage");
				//objForm.setOrderList(aList); ramana
               //defaultAction a=new defaultAction(); ramana
               //a.viewIncompleteOrder(mapping,form,request,response); ramana
			
				
			}
			//Ramana
			//request.setAttribute("universalWorkQueueList", aList);
			//forwardMapping ="UniversalWorkQueueMainPage";
		}
		catch(Exception ex)
    	{
    		logger.error(ex.getMessage()+ " Exception occured in viewUniversalWorkQueue method of "+ this.getClass().getSimpleName());
			forwardMapping="ErrorPageAction";
			return mapping.findForward(forwardMapping);
    	}
		//return mapping.findForward(forwardMapping);
	 }
	 // Created by Ramana
	 
	 
	 //created BY:Anil Kumar
	 public ActionForward goToCreateNewOrderWithExistingOrder(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) 
	 {
		logger.info("Create NewOrder with existing order Interface and goToCreateNewOrderWithExistingOrder method has been called");
		String forwardMapping = "";
		
		UniversalWorkQueueFormBean objForm=(UniversalWorkQueueFormBean) form;
		UniversalWorkQueueDto objUniversalWorkQueueDto = new UniversalWorkQueueDto();	
		UniversalWorkQueueModel objUniversalWorkQueueModel=new UniversalWorkQueueModel();
		HttpSession session = request.getSession(true);
		
		UniversalWorkQueueModel objModel=new UniversalWorkQueueModel();
		UniversalWorkQueueDto objDto=new UniversalWorkQueueDto();
		ArrayList<UniversalWorkQueueDto> listSourceName = new ArrayList<UniversalWorkQueueDto>();
		ArrayList<UniversalWorkQueueDto> listOrderType = new ArrayList<UniversalWorkQueueDto>();
		
		try 
		{
			ActionMessages messages = new ActionMessages();						
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(session.getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);

			objForm.setCurrentRole(objUserDto.getUserRoleId());
			objUniversalWorkQueueDto.setCurrentRole(objUserDto.getUserRoleId());
			
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
			
			//***************************************************************************************************
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
			//***************************************************************************************************					
			//objUniversalWorkQueueDto.setIsBillingOrder("0");
			//objForm.setIsBillingOrder("0");						
			if (errors != null && errors.size() > 0)// During Server Side				
				{					
					request.setAttribute("validation_errors", errors);
					saveMessages(request, messages);
					return mapping.findForward("createNewOrderWithExistingOrder");
				}	
			else {
					objUniversalWorkQueueDto.setSearchCRMOrder(searchCRMOrder);
					objUniversalWorkQueueDto.setSearchAccountNo(searchAccountNo);
					objUniversalWorkQueueDto.setSearchAccountName(searchAccountName);
					objUniversalWorkQueueDto.setSearchOrderType(searchOrderType);
					objUniversalWorkQueueDto.setSearchfromDate(searchfromDate);
					objUniversalWorkQueueDto.setSearchToDate(searchToDate);
					objUniversalWorkQueueDto.setSearchSource(searchSource);
					objUniversalWorkQueueDto.setSearchCurrency(searchCurrency);
					objUniversalWorkQueueDto.setSearchQuoteNumber(searchQuoteNumber);				
				
					PagingSorting pagingSorting = objForm.getPagingSorting();				
					pagingSorting.setDefaultifNotPresent("ORDERNO",PagingSorting.decrement, 1);
					pagingSorting.setPagingSorting(true, true);
					objUniversalWorkQueueDto.setPagingSorting(pagingSorting);
				
					listSourceName=objModel.displaySourceDetails(objDto);
					request.setAttribute("listSourceName", listSourceName);			
					listOrderType=objModel.displayOrderTypeDetails(objDto);
					request.setAttribute("listOrderType", listOrderType);
				
					
				    ArrayList universalWorkQueueList = objUniversalWorkQueueModel.getAllExistingOrdersDetails(objUniversalWorkQueueDto,objUserDto);
				    request.setAttribute("universalWorkQueueList", universalWorkQueueList);
				    objForm.setUniversalWorkQueueList(universalWorkQueueList);
	              
								
				return mapping.findForward("createNewOrderWithExistingOrder");											
			}
		
		}
		catch(Exception ex)
    	{
    		logger.error(ex.getMessage()+ " Exception occured in goToCreateNewOrderWithExistingOrder method of "+ this.getClass().getSimpleName());
			forwardMapping="ErrorPageAction";
			return mapping.findForward(forwardMapping);
    	}
		//return mapping.findForward(forwardMapping);
	 }	
	 public ActionForward createNewOrderWithExisting(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) 
	 {
		 logger.info("Create NewOrder with existing order Processing... and createNewOrderWithExisting method has been called");
		String forwardMapping = "";
		UniversalWorkQueueFormBean objForm=(UniversalWorkQueueFormBean) form;
		UniversalWorkQueueDto objUniversalWorkQueueDto = new UniversalWorkQueueDto();	
		UniversalWorkQueueModel objUniversalWorkQueueModel=new UniversalWorkQueueModel();
		UniversalWorkQueueDao objUniversalWorkQueueDao =  new UniversalWorkQueueDao();
		CopyOrderModel objCopyOrderModel = new CopyOrderModel();
		HttpSession session = request.getSession(true);
		
		UniversalWorkQueueModel objModel=new UniversalWorkQueueModel();
		UniversalWorkQueueDto objDto=new UniversalWorkQueueDto();
		ArrayList<UniversalWorkQueueDto> listSourceName = new ArrayList<UniversalWorkQueueDto>();
		ArrayList<UniversalWorkQueueDto> listOrderType = new ArrayList<UniversalWorkQueueDto>();
		
		try{
			String OrderNO=request.getParameter("orderNo");
			String OrderType=request.getParameter("ordertype");
			ActionMessages messages = new ActionMessages();						
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(session.getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);

			objForm.setCurrentRole(objUserDto.getUserRoleId());
			objUniversalWorkQueueDto.setCurrentRole(objUserDto.getUserRoleId());
			
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
			
			objUniversalWorkQueueDto.setSearchCRMOrder(searchCRMOrder);
			objUniversalWorkQueueDto.setSearchAccountNo(searchAccountNo);
			objUniversalWorkQueueDto.setSearchAccountName(searchAccountName);
			objUniversalWorkQueueDto.setSearchOrderType(searchOrderType);
			objUniversalWorkQueueDto.setSearchfromDate(searchfromDate);
			objUniversalWorkQueueDto.setSearchToDate(searchToDate);
			objUniversalWorkQueueDto.setSearchSource(searchSource);
			objUniversalWorkQueueDto.setSearchCurrency(searchCurrency);
			objUniversalWorkQueueDto.setSearchQuoteNumber(searchQuoteNumber);				
		
			PagingSorting pagingSorting = objForm.getPagingSorting();				
			pagingSorting.setDefaultifNotPresent("ORDERNO",PagingSorting.increment, 1);
			pagingSorting.setPagingSorting(true, true);
			objUniversalWorkQueueDto.setPagingSorting(pagingSorting);
		
			listSourceName=objModel.displaySourceDetails(objDto);
			request.setAttribute("listSourceName", listSourceName);			
			listOrderType=objModel.displayOrderTypeDetails(objDto);
			request.setAttribute("listOrderType", listOrderType);
		
			
		    ArrayList universalWorkQueueList = objUniversalWorkQueueModel.getAllExistingOrdersDetails(objUniversalWorkQueueDto,objUserDto);
		    request.setAttribute("universalWorkQueueList", universalWorkQueueList);
		    objForm.setUniversalWorkQueueList(universalWorkQueueList);
			
		    String orderCreationSource=Utility.getAppConfigValue("CREATEORDERWITHEXISTORDERKEY");
		    int PONUMBEROUT =-1;
		    if("NEWCODE".equalsIgnoreCase(orderCreationSource)){
		    	PONUMBEROUT=objCopyOrderModel.createOrderWithExistingUserInterfaceProcess(OrderNO,objUserDto.getEmployeeId(),Long.valueOf(objUserDto.getUserRoleId()));
		    }else{
		    	PONUMBEROUT=objUniversalWorkQueueDao.createNewOrderWithExisting(OrderNO,objUserDto.getEmployeeId());
		    }
		    
			
			request.setAttribute("PONUMBEROUT", PONUMBEROUT);
			
			return mapping.findForward("createNewOrderWithExistingOrder");	
		}
		catch(Exception ex)
    	{
    		logger.error(ex.getMessage()+ " Exception occured in createNewOrderWithExisting method of "+ this.getClass().getSimpleName());
			forwardMapping="ErrorPageAction";	
			return mapping.findForward(forwardMapping);
    	}
		
	 }
	 //end Anil Kumar
	 public ActionForward goToQueryForm(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) 
	 {		 
		 logger.info("Create goToQueryForm  Processing... and goToQueryForm method has been called");
			String forwardMapping = "";
			UniversalWorkQueueFormBean objForm=(UniversalWorkQueueFormBean) form;			
			HttpSession session = request.getSession(true);						
			
			UniversalWorkQueueModel objModel=new UniversalWorkQueueModel();			
			ArrayList<UniversalWorkQueueDto> listQueryFormOptions = new ArrayList<UniversalWorkQueueDto>();
			
		 try{
			 listQueryFormOptions=objModel.fetchQueryOptionList();
			 objForm.setQueryOptionList(listQueryFormOptions);
			 return mapping.findForward("QueryFormPage");	
		 }
		 catch(Exception ex)
	    	{
	    		logger.error(ex.getMessage()+ " Exception occured in goToQueryForm method of "+ this.getClass().getSimpleName());
				forwardMapping="ErrorPageAction";	
				return mapping.findForward(forwardMapping);
	    	}
	 }
	 //end Anil Kumar
	 
	 //Meenakshi : 
	 public ActionForward reassignToPM(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) 
	 {		 
		 logger.info("Create reassignToPM  Processing... and reassignToPM method has been called");
		 String forwardMapping = "";
		 NewOrderModel objModel=new NewOrderModel();			
		 try{
			ArrayList<NewOrderDto> projectManagerNameListAll = new ArrayList<NewOrderDto>();
			//projectManagerNameListAll = objModel.getProjectManagerNameList();
			//request.setAttribute("projectManagerNameListAll", projectManagerNameListAll);
			return mapping.findForward("ReAssignToPM");	
		 }
		 catch(Exception ex)
	    	{
	    		logger.error(ex.getMessage()+ " Exception occured in goToQueryForm method of "+ this.getClass().getSimpleName());
				forwardMapping="ErrorPageAction";	
				return mapping.findForward(forwardMapping);
	    	}
		 
		 
	 }
	 //Meenakshi : 

	 
	 //********************* Access Matrix***************************
	 public ActionForward accessMatrixiB2B(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) 
	 {		 
		 logger.info("Create reassignToPM  Processing... and reassignToPM method has been called");
		 String forwardMapping = "";
		 NewOrderModel objModel=new NewOrderModel();			
		 try{
			ArrayList<NewOrderDto> projectManagerNameListAll = new ArrayList<NewOrderDto>();
			return mapping.findForward("Ib2bAccessMatrix");	
		 }
		 catch(Exception ex)
	    	{
	    		logger.error(ex.getMessage()+ " Exception occured in goToQueryForm method of "+ this.getClass().getSimpleName());
				forwardMapping="ErrorPageAction";	
				return mapping.findForward(forwardMapping);
	    	}
	 }
	//********************* Access Matrix***************************

	 
	 /**
	 *
	 * @author [00077] 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward reassignToAM(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) 
	 {		 
		 logger.info("Create reassignToAM  Processing... and reassignToAM method has been called");
		 String forwardMapping = "ReAssignToAM";
		 OrderForPendingWithAMDto orderForPendingWithAMDto = new OrderForPendingWithAMDto();
		 try{
			ArrayList<NewOrderDto> projectManagerNameListAll = new ArrayList<NewOrderDto>();
			//projectManagerNameListAll = objModel.getProjectManagerNameList();
			//request.setAttribute("projectManagerNameListAll", projectManagerNameListAll);
			orderForPendingWithAMDto.getPagingSorting().setPageNumber(1);
			return mapping.findForward(forwardMapping);	
		 }
		 catch(Exception ex)
	    	{
	    		logger.error(ex.getMessage()+ " Exception occured in reassignToAM method of "+ this.getClass().getSimpleName());
				forwardMapping="ErrorPageAction";	
				return mapping.findForward(forwardMapping);
	    	}
		 
		 
	 }
	 
	//********************* Channel Partner Master ***************************
	 public ActionForward channelPartnerMasteriB2B(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) 
	 {		 
		 String forwardMapping = "";
		 try{
			return mapping.findForward("Ib2bChannelPartnerMaster");	
		 }
		 catch(Exception ex)
	    	{
	    		logger.error(ex.getMessage()+ " Exception occured in goToQueryForm method of "+ this.getClass().getSimpleName());
				forwardMapping="ErrorPageAction";	
				return mapping.findForward(forwardMapping);
	    	}
	 }
	//********************* Channel Partner Master ***************************
	 
}
