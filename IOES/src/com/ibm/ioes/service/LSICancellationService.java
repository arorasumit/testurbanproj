package com.ibm.ioes.service;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;import org.apache.struts.action.ActionMessages;

import com.ibm.ioes.model.NewOrderModel;
import com.ibm.ioes.service.LSICancellationService.LsiCancellationTableResult.StatusCode;
import com.ibm.ioes.service.UtilityService;
import com.ibm.ioes.utilities.AjaxHelper;import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.PagingSorting;
import com.ibm.ioes.utilities.Utility;


import com.ibm.ioes.beans.DefaultBean;
import com.ibm.ioes.beans.UserInfoDto;
import com.ibm.ioes.beans.ValidationBean;
import com.ibm.ioes.forms.LSICancellationDto;

public class LSICancellationService {

	public static class LsiCancellationTableResult{
		public static enum StatusCode {OK , UNEXCEPTED_ERROR}
		
		private String statusCode;
		private String tableData ;
		
		public LsiCancellationTableResult(){
			
		}
		
		public LsiCancellationTableResult(String statusCode,String tableData){
			this.statusCode = statusCode;
			this.tableData = tableData;
		}
		
		public String getStatusCode() {
			return statusCode;
		}
		public void setStatusCode(String statusCode) {
			this.statusCode = statusCode;
		}
		public String getTableData() {
			return tableData;
		}
		public void setTableData(String tableData) {
			this.tableData = tableData;
		}
		
		
	}

	public  DefaultBean processGetLsiCancellationTable(HttpServletRequest request, HttpSession session, 			LSICancellationDto formFilledByUser, int startIndex, int endIndex, 			int pagingRequired, int pageSize) throws Exception{
		ArrayList<LSICancellationDto> eligibleLSIForCancelList = new ArrayList<LSICancellationDto>();
	//	DefaultBean objForm=(DefaultBean)objForms;
		//ActionForward forward = new ActionForward(); 
		NewOrderModel objModel = new NewOrderModel();
		LSICancellationDto objDto=new LSICancellationDto(pageSize);
		
		HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
		HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
		UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
		/*PagingSorting pagingSorting = formFilledByUser.getPagingSorting();
		pagingSorting.setDefaultifNotPresent("LOGICAL_SI_NO",
					PagingSorting.increment, 1);
		objDto.setPagingSorting(pagingSorting);*/
		//String fromPageSubmit=request.getParameter("fromPageSubmit");				
		//request.setAttribute("fromPageSubmit", fromPageSubmit);
		ActionMessages messages = new ActionMessages();
		String searchCRMOrder=formFilledByUser.getSearchCRMOrder();
		String searchLSI=formFilledByUser.getSearchLSI();
		String searchAccountNo=formFilledByUser.getSearchAccountNo();
		String searchAccountName=formFilledByUser.getSearchAccountName();
		searchAccountName = (searchAccountName != null) ? searchAccountName.trim() : searchAccountName;
		String searchServiceNo=formFilledByUser.getSearchServiceNo();
		String searchFromOrdDate=formFilledByUser.getSearchFromOrd_Date();
		String searchToOrdDate=formFilledByUser.getSearchToOrd_Date();
		String searchLSIScenario=formFilledByUser.getSearchLSIScenario(); //all waste
		
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
						"Serv No",searchServiceNo , 18),
				Utility.generateCSV(Utility.CASE_MAXLENGTH,
						Utility.CASE_DIGITS_ONLY)).appendToAndRetNewErrs(
				errors);
		
		Utility.validateValue(
				new ValidationBean().loadValidationBean_Maxlength(
						"Logical SI",searchLSI , 18),
				Utility.generateCSV(Utility.CASE_MAXLENGTH,
						Utility.CASE_DIGITS_ONLY)).appendToAndRetNewErrs(
				errors);
		    if (errors != null && errors.size() > 0)// During Server Side
					
			{
				request.setAttribute("EligibleLSIForCancelList", eligibleLSIForCancelList);
				formFilledByUser.setEligibleLSIForCancelList(eligibleLSIForCancelList);
					request.setAttribute("validation_errors", errors);
					//saveMessages(request, messages);				System.out.println("eligibleLSIForCancelList.size()1111111"+eligibleLSIForCancelList.size());				//System.out.println("		eligibleLSIForCancelList.get(0);11111111111)"+		eligibleLSIForCancelList.get(0));
					
			}
			// ******* Validation Ends Here
			// *************************************
			else {
				objDto.setSearchCRMOrder(searchCRMOrder);
				objDto.setSearchAccountNo(searchAccountNo);
				objDto.setSearchAccountName(searchAccountName);
				objDto.setSearchServiceNo(searchServiceNo);
		        objDto.setSearchLSI(searchLSI);
		        objDto.setSearchFromOrderDate(searchFromOrdDate);
		        objDto.setSearchToOrderDate(searchToOrdDate);
		        objDto.setSearchLSIScenario(searchLSIScenario);
		        
		        //pagingSorting.setPagingSorting(true, false);
				//pagingSorting.setPageRecords(pageSize);
				//objDto.getPagingSorting().setPageRecords(pageSize);
				//pagingSorting.setPagingSorting(true, true);
				
				eligibleLSIForCancelList=objModel.viewEligibleLSICancelList(objDto,startIndex,endIndex,pagingRequired,pageSize);			}
		
		request.setAttribute("EligibleLSIForCancelList", eligibleLSIForCancelList);
		formFilledByUser.setEligibleLSIForCancelList(eligibleLSIForCancelList);		//System.out.println("eligibleLSIForCancelList.size()"+eligibleLSIForCancelList.size());		//System.out.println("		eligibleLSIForCancelList.get(0);)"+		eligibleLSIForCancelList.get(0));		formFilledByUser.setJspRendered("/workqueue/LSICancellationSearchedData.jsp");
		request.setAttribute("viewPage", "/workqueue/LSICancellationSearchedData.jsp");		request.setAttribute("eligibleLSIForCancelList", eligibleLSIForCancelList);		request.setAttribute("formBean", formFilledByUser);
		
		return formFilledByUser;   // will add try catch later
		
	}

	public LsiCancellationTableResult GetLsiCancellationTable(HttpServletRequest request,
			HttpSession session, LSICancellationDto lsiCancellationDto, UtilityService utilityService, int startIndex, int endIndex, int pagingRequired, int pageSize) {
		
		try {
			new LSICancellationService().processGetLsiCancellationTable(request,session,lsiCancellationDto, startIndex, endIndex, pagingRequired , pageSize);
			//utilityService.setAttributes(attributes,request);   // confusing     both obj in same class then y using utility
			//String jspName = (String)attributes.get("viewPage");
			String jspOutput = utilityService.getJspOutput(request,session,lsiCancellationDto.getJspRendered());
			ArrayList<LSICancellationDto> eligibleLSIForCancelList = lsiCancellationDto.getEligibleLSIForCancelList();
			if( eligibleLSIForCancelList.size()==0)
				return new LsiCancellationTableResult(StatusCode.OK.toString(), "NO RECORDS FOUND");
			else
				return new LsiCancellationTableResult(StatusCode.OK.toString(),jspOutput);
		} catch (Exception e) {
			Utility.LOG(e);
		}
			return new LsiCancellationTableResult(StatusCode.UNEXCEPTED_ERROR.toString(), "SOME ERROR HAS OCCURED");
	 }

}
