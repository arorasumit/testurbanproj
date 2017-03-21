package com.ibm.ioes.npd.actions;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import com.ibm.ioes.npd.beans.AssumptionCaptureBean;
import com.ibm.ioes.npd.beans.ProjectPlanReportBean;
import com.ibm.ioes.npd.beans.ProjectStatusReportBean;
import com.ibm.ioes.npd.beans.RisksCaptureBean;
import com.ibm.ioes.npd.beans.ValidationBean;
import com.ibm.ioes.npd.hibernate.beans.ProjectPlanReportDto;
import com.ibm.ioes.npd.hibernate.beans.ProjectStatusReportDto;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjectassumptions;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjectisssues;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjectrisks;
import com.ibm.ioes.npd.model.AssumptionCaptureModel;
import com.ibm.ioes.npd.model.CommonBaseModel;
import com.ibm.ioes.npd.model.ProjectPlanModel;
import com.ibm.ioes.npd.model.ProjectStatusModel;
import com.ibm.ioes.npd.model.RisksCaptureModel;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.ExcelCreator;
import com.ibm.ioes.npd.utilities.Messages;
import com.ibm.ioes.npd.utilities.PagingSorting;
import com.ibm.ioes.npd.utilities.Utility;

public class ProjectPlanReportAction extends DispatchAction {
	
	public ActionForward viewrProjectPlanDetail(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) 
	{
		ActionForward forward = new ActionForward();
		String forwardMapping = null;
		ProjectPlanModel objModel = new ProjectPlanModel();
		ActionMessages messages = new ActionMessages();
		ProjectPlanReportBean objFormBean = (ProjectPlanReportBean)actionForm; 
		ProjectPlanReportDto objProjectPlan = new ProjectPlanReportDto();
		ArrayList<ProjectPlanReportDto> listProjectPlan = null;
		String dateFilter=null;
		String fromDate=null;
		String toDate=null;
		String projectStatusFilter = null;
		boolean errorsFound=false;
		try 
		{
			String searchStageName=objFormBean.getSearchStageName();
			String searchTaskName=objFormBean.getSearchTaskName();	
			dateFilter=objFormBean.getDateFilter();
			fromDate=objFormBean.getFromDate();
			toDate=objFormBean.getToDate();	
			projectStatusFilter = objFormBean.getProjectStatusFilter();	
			
			//Server Side Security Start for Project Name
			if((objFormBean.getSearchprojectname()!=null) && (!objFormBean.getSearchprojectname().equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(objFormBean.getSearchprojectname(),"Project Name",20),
							""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;	
					}
				}
			}
			//Server Side Security End
				
			//Server Side Security Start for Project ID
			if((objFormBean.getSearchprojectid()!=null) && (!objFormBean.getSearchprojectid().equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(objFormBean.getSearchprojectid(),"Project ID",20),
							""+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;	
					}
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Stage Name
			if((searchStageName!=null) && (!searchStageName.equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(searchStageName,"Stage Name",20),
							""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;	
					}
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Task Name
			if((searchTaskName!=null) && (!searchTaskName.equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(searchTaskName,"Task Name",20),
							""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;	
					}
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Project Status
			if((String.valueOf(projectStatusFilter)!=null) && (!String.valueOf(projectStatusFilter).equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(String.valueOf(projectStatusFilter),"Project Status",20),
							""+Utility.CASE_SPECIALCHARACTERS).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;	
					}
				}
			}
			//Server Side Security End
						
			//Server Side Security Start for Date Filter
			if(((dateFilter!=null) && (!dateFilter.equalsIgnoreCase(""))) && (!dateFilter.equalsIgnoreCase("0")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(dateFilter,"Date Filter",20),
							""+Utility.CASE_SPECIALCHARACTERS).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;	
					}
				}
				
				if((!objFormBean.getFromDate().equalsIgnoreCase("") && objFormBean.getFromDate()!=null)
						|| (!objFormBean.getToDate().equalsIgnoreCase("") && objFormBean.getToDate()!=null))
				{
					if(!errorsFound)
					{
						Object obArray[]={""+ValidationBean.VN_DATE_COMPARISION,objFormBean.getFromDate(),"From Date",
								objFormBean.getToDate(),"To Date",ValidationBean.LESS_EQUAL,
								new java.text.SimpleDateFormat(Messages.getMessageValue("calendar_entry_format"))};
						ArrayList errors=Utility.validateValue(	new ValidationBean(obArray),
								""+Utility.CASE_DATECOMPARISION_NONMANDATORY).getCompleteMessageStrings();
						if(errors!=null)
						{
							request.setAttribute("validation_errors", errors);
							errorsFound=true;
						}
					}
				}
			}
			//Server Side Security End
			
			if(errorsFound)
			{
				saveMessages(request, messages);
				forwardMapping = "success";
				forward = mapping.findForward(forwardMapping);
				return forward;
			}
			else
			{
				if(fromDate==null || "".equals(fromDate.trim()))
				{
					SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
					toDate=sdf.format(new Date(System.currentTimeMillis()));
					
					Calendar cal=new GregorianCalendar();
					cal.setTime(new Date(System.currentTimeMillis()));
					cal.add(Calendar.MONTH, -1);
					fromDate=sdf.format(cal.getTime());		
					toDate=sdf.format(cal.getTime());		
					objFormBean.setFromDate(fromDate);
					objFormBean.setToDate(toDate);
				}
				else
				{
					SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
					fromDate = sdf.format(new Date(fromDate));
					//toDate = sdf.format(new Date(toDate));
				}
				
				if(toDate==null || "".equals(toDate.trim()))
				{
					SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
					toDate=sdf.format(new Date(System.currentTimeMillis()));
					
					Calendar cal=new GregorianCalendar();
					cal.setTime(new Date(System.currentTimeMillis()));
					cal.add(Calendar.MONTH, -1);
					fromDate=sdf.format(cal.getTime());		
					toDate=sdf.format(cal.getTime());		
					objFormBean.setFromDate(fromDate);
					objFormBean.setToDate(toDate);
				}
				else
				{
					SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
					//fromDate = sdf.format(new Date(fromDate));
					toDate = sdf.format(new Date(toDate));
				}
		
				if(objFormBean.getProjectId()!=null && !objFormBean.getProjectId().equalsIgnoreCase(""))
						objProjectPlan.setSearchProjectId(Integer.parseInt(objFormBean.getProjectId()));
				if(dateFilter==null  || "".equals(dateFilter.trim()) || "0".equals(dateFilter.trim()))
				{
					dateFilter="";
					objFormBean.setDateFilter(dateFilter);
				}
				objProjectPlan.setDateFilter(dateFilter);
				objProjectPlan.setFromDate(fromDate);
				objProjectPlan.setToDate(toDate);
				objProjectPlan.setSearchStageName(searchStageName);
				objProjectPlan.setSearchTaskName(searchTaskName);
				objProjectPlan.setProjectStatusFilter(Integer.parseInt(projectStatusFilter));
				objProjectPlan.setSearchprojectid(objFormBean.getSearchprojectid());
				objProjectPlan.setSearchprojectname(objFormBean.getSearchprojectname());
				objProjectPlan.setTaskStatus(objFormBean.getTaskStatus());
				objProjectPlan.setDelayindays(objFormBean.getDelayindays());
				objProjectPlan.setTaskDelayValue(objFormBean.getTaskDelayValue());
				objProjectPlan.setMonth_Name(objFormBean.getMonthName());
				
				PagingSorting pagingSorting=objFormBean.getPagingSorting();
				if(pagingSorting==null )
				{
					pagingSorting=new PagingSorting();
					objFormBean.setPagingSorting(pagingSorting);
				}
				pagingSorting.setPagingSorting(true,true);
				
				String sortByColumn=pagingSorting.getSortByColumn();
				String sortByOrder=pagingSorting.getSortByOrder();
				int pageNumber=pagingSorting.getPageNumber();
				
				if(sortByColumn==null || "".equals(sortByColumn))
				{
					sortByColumn="taskid";//default flag
				}
				if(sortByOrder==null || "".equals(sortByOrder))
				{
					sortByOrder=PagingSorting.increment;
				}
				if(pageNumber==0)
				{
					pageNumber=1;
				}
				
				pagingSorting.setSortByColumn(sortByColumn);
				pagingSorting.setSortByOrder(sortByOrder);
				pagingSorting.setPageNumber(pageNumber);
				objProjectPlan.setPagingSorting(pagingSorting);		
				
				listProjectPlan = objModel.fetchProjectPlanDetail(objProjectPlan);
				request.setAttribute("listProjectPlan", listProjectPlan);
				forwardMapping = "success";
			}
			
		} catch (Exception e) {
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
		}
		if (!messages.isEmpty()) {
			saveErrors(request, messages);
		}
		forward = mapping.findForward(forwardMapping);

		return forward;
	}
	
	public ActionForward viewprojectworkflow(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {

		ActionForward forward = new ActionForward();
		String forwardMapping = null;
		ProjectPlanModel objModel = new ProjectPlanModel();
		ProjectStatusModel  objstatusModel = new ProjectStatusModel();
		ActionMessages messages = new ActionMessages();
		ProjectPlanReportBean objFormBean = (ProjectPlanReportBean)actionForm; 
		ProjectPlanReportDto objProjectPlan = new ProjectPlanReportDto();
			ArrayList<ProjectPlanReportDto> listProjectPlan = null;
			String dateFilter=null;
			String fromDate=null;
			String toDate=null;
			String projectStatusFilter = null;
			
			AssumptionCaptureBean captureBean = new AssumptionCaptureBean();
			AssumptionCaptureModel model = new AssumptionCaptureModel();

			RisksCaptureBean risksCaptureBean= new  RisksCaptureBean() ;
			RisksCaptureModel risksCaptureModel = new RisksCaptureModel();
			
			ProjectPlanModel objprojectPlanModel = new ProjectPlanModel();
			
			ArrayList<TtrnProjectisssues> listProjectIssue = null;
			ArrayList<TtrnProjectassumptions> listProjectassumption = new ArrayList<TtrnProjectassumptions>(); 
			ArrayList<TtrnProjectrisks> listProjectrisk = new ArrayList<TtrnProjectrisks>(); 
			ProjectStatusReportDto objProjectStatus = new ProjectStatusReportDto();
			
			ArrayList<ProjectPlanReportDto> listProjectrfiaction = null;
			
		try {
			
			
			String searchStageName=objFormBean.getSearchStageName();
			String searchTaskName=objFormBean.getSearchTaskName();
			
			int searchProjStatus=objFormBean.getSearchProjectStatus();
			objProjectPlan.setProjectStatus(searchProjStatus);
			
			dateFilter=objFormBean.getDateFilter();
			fromDate=objFormBean.getFromDate();
			toDate=objFormBean.getToDate();	
			projectStatusFilter = objFormBean.getProjectStatusFilter();	
			
			if(fromDate==null || "".equals(fromDate.trim()))
			{
				SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
				toDate=sdf.format(new Date(System.currentTimeMillis()));
				
				Calendar cal=new GregorianCalendar();
				cal.setTime(new Date(System.currentTimeMillis()));
				cal.add(Calendar.MONTH, -1);
				fromDate=sdf.format(cal.getTime());			
				objFormBean.setFromDate(fromDate);
				objFormBean.setToDate(toDate);
			}
			else
			{
				SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
				fromDate = sdf.format(new Date(fromDate));
				toDate = sdf.format(new Date(toDate));
			}
			
			objProjectPlan.setFromDate(fromDate);
			objProjectPlan.setToDate(toDate);
			
			if(objFormBean.getProjectId()!=null && !objFormBean.getProjectId().equalsIgnoreCase(""))
					objProjectPlan.setSearchProjectId(Integer.parseInt(Utility.decryptString(objFormBean.getProjectId())));
			if(dateFilter==null  || "".equals(dateFilter.trim()) || "0".equals(dateFilter.trim()))
			{
				dateFilter="";
				objFormBean.setDateFilter(dateFilter);
			}
			objProjectPlan.setDateFilter(dateFilter);
			objProjectPlan.setFromDate(fromDate);
			objProjectPlan.setToDate(toDate);
			
			
			
			objProjectPlan.setSearchStageName(searchStageName);
			objProjectPlan.setSearchTaskName(searchTaskName);
			objProjectPlan.setProjectStatusFilter(Integer.parseInt(projectStatusFilter));
			objProjectPlan.setSearchprojectid(Utility.decryptString(objFormBean.getSearchprojectid()));
			objProjectPlan.setSearchprojectname(objFormBean.getSearchprojectname());
			
			//objProjectPlan.setSearchNpdCategoryId(checkForNoConditionInSelect(searchNpdCategoryId));
			//objProjectPlan.setSearchProductCategoryId(checkForNoConditionInSelect(searchProductCategoryId));
			//objProjectPlan.setSearchProjectId(checkForNoConditionInSelect(searchProjectId));
			
			PagingSorting pagingSorting=objFormBean.getPagingSorting();
			if(pagingSorting==null )
			{
				pagingSorting=new PagingSorting();
				objFormBean.setPagingSorting(pagingSorting);
			}
			pagingSorting.setPagingSorting(true,true);
			
			String sortByColumn=pagingSorting.getSortByColumn();
			String sortByOrder=pagingSorting.getSortByOrder();
			int pageNumber=pagingSorting.getPageNumber();
			
			if(sortByColumn==null || "".equals(sortByColumn))
			{
				sortByColumn="stageName";//default flag
			}
			if(sortByOrder==null || "".equals(sortByOrder))
			{
				sortByOrder=PagingSorting.increment;
			}
			if(pageNumber==0)
			{
				pageNumber=1;
			}
			
			pagingSorting.setSortByColumn(sortByColumn);
			pagingSorting.setSortByOrder(sortByOrder);
			pagingSorting.setPageNumber(pageNumber);
			
			objProjectPlan.setPagingSorting(pagingSorting);
			
			listProjectPlan = objModel.fetchProjectPlanDetail(objProjectPlan);
			
			///======================================================================
			
			objProjectStatus.setSearchProjectid(Integer.parseInt(Utility.decryptString(objFormBean.getSearchprojectid())));
			listProjectIssue = objstatusModel.exportIssueToExcel(objProjectStatus);
			captureBean.setProjectId(Utility.decryptString(objFormBean.getSearchprojectid()));
			captureBean = model.getAssumptionForAProject(captureBean);
			captureBean = model.viewAssumption(captureBean,null,null);
			
			risksCaptureBean.setProjectId(Utility.decryptString(objFormBean.getSearchprojectid()));
			risksCaptureBean = risksCaptureModel.getRisksForAProject(risksCaptureBean);
			risksCaptureBean = risksCaptureModel.viewRisks(risksCaptureBean,null,null);
			
			
			
			listProjectassumption = (ArrayList<TtrnProjectassumptions>)	captureBean.getAssumptionDetailList();
			listProjectrisk = (ArrayList<TtrnProjectrisks>)risksCaptureBean.getRiskDetailList();
			
			//objProjectPlan.setSearchProjectId(objFormBean.getSearchprojectid()));
			objProjectPlan.setSearchprojectid(Utility.decryptString(objFormBean.getSearchprojectid()));
			listProjectrfiaction = objModel.fetchRfiAction(objProjectPlan);
			//listProjectPlan = objprojectPlanModel.ExportProjectPlanDetail(objProjectPlan);

			///=========================================================================
			
			request.setAttribute("listProjectPlan", listProjectPlan);
			request.setAttribute("listProjectIssue", listProjectIssue);
			request.setAttribute("listProjectassumption", listProjectassumption);
			request.setAttribute("listProjectrisk", listProjectrisk);
			request.setAttribute("listProjectrfiaction", listProjectrfiaction);
			
			forwardMapping = "viewworkflow";
			
		} catch (Exception e) {
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
		}
		if (!messages.isEmpty()) {
			saveErrors(request, messages);
		}
		forward = mapping.findForward(forwardMapping);

		return forward;
	}

	public ActionForward exportProjectStatus(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {

		ActionForward forward = new ActionForward();
		String forwardMapping = null;
		ProjectStatusModel  objModel = new ProjectStatusModel();
		ActionMessages messages = new ActionMessages();
		ProjectPlanReportBean objFormBean = (ProjectPlanReportBean)actionForm; 
		ProjectStatusReportDto objProjectStatus = new ProjectStatusReportDto();
		
		AssumptionCaptureBean captureBean = new AssumptionCaptureBean();
		AssumptionCaptureModel model = new AssumptionCaptureModel();

		RisksCaptureBean risksCaptureBean= new  RisksCaptureBean() ;
		RisksCaptureModel risksCaptureModel = new RisksCaptureModel();
		
		ProjectPlanReportDto objProjectPlan = new ProjectPlanReportDto();
		ProjectPlanModel objprojectPlanModel = new ProjectPlanModel();
		
			ArrayList<TtrnProjectisssues> listProjectIssue = null;
			ArrayList<ProjectPlanReportDto> listProjectPlan = null;

			ArrayList<ProjectPlanReportDto> rfilist = null;
		try {
			
			objProjectStatus.setProjectidlist(Utility.decryptString(objFormBean.getSearchprojectid()));
			listProjectIssue = objModel.exportIssueToExcelForStatus(objProjectStatus);
			
			
			
			StringTokenizer splitStringStage = new StringTokenizer(Utility.decryptString(objFormBean.getSearchprojectid()).toString() ,",");
			String[] projectlist=new String[splitStringStage.countTokens()];
			Long[] projectidlist = new Long[projectlist.length];  
			
			for(int i=0; i< projectlist.length;i++ )
			{
				projectidlist[i] = Long.parseLong(splitStringStage.nextToken());
			}
				
			captureBean.setProjectidlist(projectidlist);
			risksCaptureBean.setProjectidlist(projectidlist);
			captureBean.setProjectId(Utility.decryptString(objFormBean.getSearchprojectid()));
			
			captureBean = model.getAssumptionForSelectedProject(captureBean);
			
			//captureBean = model.viewAssumption(captureBean,null,null);
			risksCaptureBean.setProjectId(Utility.decryptString(objFormBean.getSearchprojectid()));
			risksCaptureBean = risksCaptureModel.getRisksForSelectedProject(risksCaptureBean);
			//risksCaptureBean = risksCaptureModel.viewRisks(risksCaptureBean,null,null);

			
			objProjectPlan.setSearchprojectid(Utility.decryptString(objFormBean.getSearchprojectid()));
			objProjectPlan.setProjectStatusFilter(1);// For Open Project Only
			listProjectPlan = objprojectPlanModel.ExportProjectPlanDetail(objProjectPlan);
			rfilist = objprojectPlanModel.fetchRfiAction(objProjectPlan);
			
			ExcelCreator excelCreator = new ExcelCreator();
			HSSFWorkbook workbook = excelCreator.createWorkbook(listProjectIssue,captureBean.getAssumptionDetailList(),risksCaptureBean.getRiskDetailList(),listProjectPlan,rfilist);
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=ProjectStatus.xls");
			ServletOutputStream out = response.getOutputStream();
			workbook.write(out);
			out.flush();
			out.close();
			//request.setAttribute("listProjectPlan", listProjectPlan);
			forwardMapping = null;//"success";
			
		} catch (Exception e) {
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
		}
		if (!messages.isEmpty()) {
			saveErrors(request, messages);
		}
		forward = mapping.findForward(forwardMapping);

		return forward;
	}
	
	public ActionForward exportToExcel(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {

		ActionForward forward = new ActionForward();
		String forwardMapping = null;
		ProjectPlanModel objModel = new ProjectPlanModel();
		ActionMessages messages = new ActionMessages();
		ProjectPlanReportBean objFormBean = (ProjectPlanReportBean)actionForm; 
		ProjectPlanReportDto objProjectPlan = new ProjectPlanReportDto();
		ArrayList<ProjectPlanReportDto> listProjectPlan = null;
		String dateFilter=null;
		String fromDate=null;
		String toDate=null;
		String projectStatusFilter=null;
		boolean errorsFound=false;
		try 
		{	
			projectStatusFilter = objFormBean.getProjectStatusFilter();
			String searchStageName=objFormBean.getSearchStageName();
			String searchTaskName=objFormBean.getSearchTaskName();
			String searchProjectId = objFormBean.getSearchProjectId();
			dateFilter=objFormBean.getDateFilter();
			fromDate=objFormBean.getFromDate();
			toDate=objFormBean.getToDate();	

			//Server Side Security Start for Project Name
			if((objFormBean.getSearchprojectname()!=null) && (!objFormBean.getSearchprojectname().equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(objFormBean.getSearchprojectname(),"Project Name",20),
							""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;	
					}
				}
			}
			//Server Side Security End
				
			//Server Side Security Start for Project ID
			if((objFormBean.getSearchprojectid()!=null) && (!objFormBean.getSearchprojectid().equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(objFormBean.getSearchprojectid(),"Project ID",20),
							""+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;	
					}
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Stage Name
			if((searchStageName!=null) && (!searchStageName.equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(searchStageName,"Stage Name",20),
							""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;	
					}
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Task Name
			if((searchTaskName!=null) && (!searchTaskName.equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(searchTaskName,"Task Name",20),
							""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;	
					}
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Project Status
			if((String.valueOf(projectStatusFilter)!=null) && (!String.valueOf(projectStatusFilter).equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(String.valueOf(projectStatusFilter),"Project Status",20),
							""+Utility.CASE_SPECIALCHARACTERS).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;	
					}
				}
			}
			//Server Side Security End
						
			//Server Side Security Start for Date Filter
			if(((dateFilter!=null) && (!dateFilter.equalsIgnoreCase(""))) && (!dateFilter.equalsIgnoreCase("0")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(dateFilter,"Date Filter",20),
							""+Utility.CASE_SPECIALCHARACTERS).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;	
					}
				}
				
				if((!objFormBean.getFromDate().equalsIgnoreCase("") && objFormBean.getFromDate()!=null)
						|| (!objFormBean.getToDate().equalsIgnoreCase("") && objFormBean.getToDate()!=null))
				{
					if(!errorsFound)
					{
						Object obArray[]={""+ValidationBean.VN_DATE_COMPARISION,objFormBean.getFromDate(),"From Date",
								objFormBean.getToDate(),"To Date",ValidationBean.LESS_EQUAL,
								new java.text.SimpleDateFormat(Messages.getMessageValue("calendar_entry_format"))};
						ArrayList errors=Utility.validateValue(	new ValidationBean(obArray),
								""+Utility.CASE_DATECOMPARISION_NONMANDATORY).getCompleteMessageStrings();
						if(errors!=null)
						{
							request.setAttribute("validation_errors", errors);
							errorsFound=true;
						}
					}
				}
			}
			//Server Side Security End
			
			if(errorsFound)
			{
				saveMessages(request, messages);
				forwardMapping = "success";
				forward = mapping.findForward(forwardMapping);
				return forward;
			}
			else
			{
				
				if(fromDate==null || "".equals(fromDate.trim()))
				{
					SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
					toDate=sdf.format(new Date(System.currentTimeMillis()));
					
					Calendar cal=new GregorianCalendar();
					cal.setTime(new Date(System.currentTimeMillis()));
					cal.add(Calendar.MONTH, -1);
					fromDate=sdf.format(cal.getTime());		
					toDate=sdf.format(cal.getTime());		
					objFormBean.setFromDate(fromDate);
					objFormBean.setToDate(toDate);
				}
				else
				{
					SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
					fromDate = sdf.format(new Date(fromDate));
					//toDate = sdf.format(new Date(toDate));
				}
				
				if(toDate==null || "".equals(toDate.trim()))
				{
					SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
					toDate=sdf.format(new Date(System.currentTimeMillis()));
					
					Calendar cal=new GregorianCalendar();
					cal.setTime(new Date(System.currentTimeMillis()));
					cal.add(Calendar.MONTH, -1);
					fromDate=sdf.format(cal.getTime());		
					toDate=sdf.format(cal.getTime());		
					objFormBean.setFromDate(fromDate);
					objFormBean.setToDate(toDate);
				}
				else
				{
					SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
					//fromDate = sdf.format(new Date(fromDate));
					toDate = sdf.format(new Date(toDate));
				}
		
				if(objFormBean.getProjectId()!=null && !objFormBean.getProjectId().equalsIgnoreCase(""))
						objProjectPlan.setSearchProjectId(Integer.parseInt(objFormBean.getProjectId()));
				if(dateFilter==null  || "".equals(dateFilter.trim()) || "0".equals(dateFilter.trim()))
				{
					dateFilter="";
					objFormBean.setDateFilter(dateFilter);
				}
				
				if(searchProjectId==null || "".equals(searchProjectId.trim()))
					objProjectPlan.setSearchProjectId(0);
				else
					objProjectPlan.setSearchProjectId(Integer.parseInt(searchProjectId));
				
				objProjectPlan.setFromDate(fromDate);
				objProjectPlan.setToDate(toDate);
				
				if(dateFilter==null  || "".equals(dateFilter.trim()) || "0".equals(dateFilter.trim()))
				{
					dateFilter="";
					objFormBean.setDateFilter(dateFilter);
				}
				objProjectPlan.setDateFilter(dateFilter);
				objProjectPlan.setFromDate(fromDate);
				objProjectPlan.setToDate(toDate);
				objProjectPlan.setSearchprojectid(objFormBean.getSearchprojectid());
				objProjectPlan.setSearchprojectname(objFormBean.getSearchprojectname());
				objProjectPlan.setProjectStatusFilter(Integer.parseInt(projectStatusFilter));
				objProjectPlan.setSearchStageName(searchStageName);
				objProjectPlan.setSearchTaskName(searchTaskName);
				
				listProjectPlan = objModel.ExportProjectPlanDetail(objProjectPlan);
				request.setAttribute("listProjectPlan", listProjectPlan);
				forwardMapping = "export";
			}
		} 
		catch (Exception e) 
		{
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
		}
		if (!messages.isEmpty()) {
			saveErrors(request, messages);
		}
		forward = mapping.findForward(forwardMapping);

		return forward;
	}

	public ActionForward DownloadRFIFile(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {

		ActionForward forward = new ActionForward();
		String forwardMapping = null;
		ProjectPlanModel  objModel = new ProjectPlanModel();
		ActionMessages messages = new ActionMessages();
		ProjectPlanReportBean objFormBean = (ProjectPlanReportBean)actionForm;
		ProjectPlanReportDto objDto =new ProjectPlanReportDto();
		byte[] test=null;

		try {
				
				ServletOutputStream objOutputStream = null;
			if(objFormBean.getDocid()!=null && !objFormBean.getDocid().equalsIgnoreCase(""))
				objDto.setProjectActionId(Integer.parseInt(objFormBean.getDocid()));
				test = objModel.DownloadFileRFI(objDto);
				
				response.reset();
				response.setHeader("Pragma", "public");
				response.setHeader("Cache-Control", "max-age=0");	
				
				CommonBaseModel commonBaseModel=new CommonBaseModel(); 
				String ContentType = commonBaseModel.setContentTypeForFile(objFormBean.getRfidocname());
				response.setContentType(ContentType);
				
				response.setHeader("Content-Disposition", "attachment;filename=" + objFormBean.getRfidocname());        	
				        	
				objOutputStream = response.getOutputStream();
				objOutputStream.write(test);
				objOutputStream.flush();
				objOutputStream.close();

				
				forwardMapping = null;//"success";
			
		} catch (Exception e) {
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
		}
		if (!messages.isEmpty()) {
			saveErrors(request, messages);
		}
		forward = mapping.findForward(forwardMapping);

		return forward;
	}

	public ActionForward DownloadFile(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {

		ActionForward forward = new ActionForward();
		String forwardMapping = null;
		ProjectPlanModel  objModel = new ProjectPlanModel();
		ActionMessages messages = new ActionMessages();
		ProjectPlanReportBean objFormBean = (ProjectPlanReportBean)actionForm;
		ProjectPlanReportDto objDto =new ProjectPlanReportDto();
		byte[] test=null;

		try {
				
				ServletOutputStream objOutputStream = null;
			if(objFormBean.getProjectActionId()!=null)
				objDto.setProjectActionId(Integer.parseInt(objFormBean.getProjectActionId()));
				test = objModel.DownloadFile(objDto);
				
				response.reset();
				response.setHeader("Pragma", "public");
				response.setHeader("Cache-Control", "max-age=0");	
				
				CommonBaseModel commonBaseModel=new CommonBaseModel(); 
				String ContentType = commonBaseModel.setContentTypeForFile(objFormBean.getFileName());
				response.setContentType(ContentType);
				
				
				response.setHeader("Content-Disposition", "attachment;filename=" + objFormBean.getFileName());        	
				        	
				objOutputStream = response.getOutputStream();
				objOutputStream.write(test);
				objOutputStream.flush();
				objOutputStream.close();

				
				forwardMapping = null;//"success";
			
		} catch (Exception e) {
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
		}
		if (!messages.isEmpty()) {
			saveErrors(request, messages);
		}
		forward = mapping.findForward(forwardMapping);

		return forward;
	}

	public ActionForward viewProjectDocReport(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) 
	{
		ActionForward forward = new ActionForward();
		String forwardMapping = null;
		ProjectPlanModel objModel = new ProjectPlanModel();
		ActionMessages messages = new ActionMessages();
		ProjectPlanReportBean objFormBean = (ProjectPlanReportBean)actionForm; 
		ProjectPlanReportDto objProjectPlan = new ProjectPlanReportDto();
		ArrayList<ProjectPlanReportDto> listProjectPlan = null;
		String dateFilter=null;
		String fromDate=null;
		String toDate=null;
		String projectStatusFilter = null;
		boolean errorsFound=false;
		try 
		{
			String searchStageName=objFormBean.getSearchStageName();
			String searchTaskName=objFormBean.getSearchTaskName();
			dateFilter=objFormBean.getDateFilter();
			fromDate=objFormBean.getFromDate();
			toDate=objFormBean.getToDate();	
			projectStatusFilter = objFormBean.getProjectStatusFilter();	
			
			//Server Side Security Start for Project Name
			if((objFormBean.getSearchprojectname()!=null) && (!objFormBean.getSearchprojectname().equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(objFormBean.getSearchprojectname(),"Project Name",20),
							""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;	
					}
				}
			}
			//Server Side Security End
				
			//Server Side Security Start for Project ID
			if((objFormBean.getSearchProjectId()!=null) && (!objFormBean.getSearchProjectId().equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(objFormBean.getSearchProjectId(),"Project ID",20),
							""+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;	
					}
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Stage Name
			if((objFormBean.getSearchStageName()!=null) && (!objFormBean.getSearchStageName().equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(objFormBean.getSearchStageName(),"Stage Name",20),
							""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;	
					}
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Task Name
			if((objFormBean.getSearchTaskName()!=null) && (!objFormBean.getSearchTaskName().equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(objFormBean.getSearchTaskName(),"Task Name",20),
							""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;	
					}
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Project Status
			if((projectStatusFilter!=null) && (!projectStatusFilter.equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(projectStatusFilter,"Project Status",20),
							""+Utility.CASE_SPECIALCHARACTERS).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;	
					}
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Date Filter
			if(((dateFilter!=null) && (!dateFilter.equalsIgnoreCase(""))) && (!dateFilter.equalsIgnoreCase("0")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(dateFilter,"Date Filter",20),
							""+Utility.CASE_SPECIALCHARACTERS).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;	
					}
				}
				
				if((!objFormBean.getFromDate().equalsIgnoreCase("") && objFormBean.getFromDate()!=null)
						|| (!objFormBean.getToDate().equalsIgnoreCase("") && objFormBean.getToDate()!=null))
				{
					if(!errorsFound)
					{
						Object obArray[]={""+ValidationBean.VN_DATE_COMPARISION,objFormBean.getFromDate(),"From Date",
								objFormBean.getToDate(),"To Date",ValidationBean.LESS_EQUAL,
								new java.text.SimpleDateFormat(Messages.getMessageValue("calendar_entry_format"))};
						ArrayList errors=Utility.validateValue(	new ValidationBean(obArray),
								""+Utility.CASE_DATECOMPARISION_NONMANDATORY).getCompleteMessageStrings();
						if(errors!=null)
						{
							request.setAttribute("validation_errors", errors);
							errorsFound=true;
						}
					}
				}
			}
			//Server Side Security End
			
			if(errorsFound)
			{
				saveMessages(request, messages);
				return mapping.findForward("success");
			}
			else
			{
			
				if(fromDate==null || "".equals(fromDate.trim()))
				{
					SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
					toDate=sdf.format(new Date(System.currentTimeMillis()));
					
					Calendar cal=new GregorianCalendar();
					cal.setTime(new Date(System.currentTimeMillis()));
					cal.add(Calendar.MONTH, -1);
					fromDate=sdf.format(cal.getTime());		
					toDate=sdf.format(cal.getTime());		
					objFormBean.setFromDate(fromDate);
					objFormBean.setToDate(toDate);
				}
				else
				{
					SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
					fromDate = sdf.format(new Date(fromDate));
					//toDate = sdf.format(new Date(toDate));
				}
				
				if(toDate==null || "".equals(toDate.trim()))
				{
					SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
					toDate=sdf.format(new Date(System.currentTimeMillis()));
					
					Calendar cal=new GregorianCalendar();
					cal.setTime(new Date(System.currentTimeMillis()));
					cal.add(Calendar.MONTH, -1);
					fromDate=sdf.format(cal.getTime());		
					toDate=sdf.format(cal.getTime());		
					objFormBean.setFromDate(fromDate);
					objFormBean.setToDate(toDate);
				}
				else
				{
					SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
					//fromDate = sdf.format(new Date(fromDate));
					toDate = sdf.format(new Date(toDate));
				}
			
				objProjectPlan.setFromDate(fromDate);
				objProjectPlan.setToDate(toDate);
				
				if(objFormBean.getProjectId()!=null && !objFormBean.getProjectId().equalsIgnoreCase(""))
						objProjectPlan.setSearchProjectId(Integer.parseInt(objFormBean.getProjectId()));
				if(dateFilter==null  || "".equals(dateFilter.trim()) || "0".equals(dateFilter.trim()))
				{
					dateFilter="";
					objFormBean.setDateFilter(dateFilter);
				}
				objProjectPlan.setDateFilter(dateFilter);
				objProjectPlan.setFromDate(fromDate);
				objProjectPlan.setToDate(toDate);
	
				objProjectPlan.setSearchStageName(searchStageName);
				objProjectPlan.setSearchTaskName(searchTaskName);
				objProjectPlan.setProjectStatusFilter(Integer.parseInt(projectStatusFilter));
				objProjectPlan.setSearchprojectid(objFormBean.getSearchprojectid());
				objProjectPlan.setSearchprojectname(objFormBean.getSearchprojectname());
					
				PagingSorting pagingSorting=objFormBean.getPagingSorting();
				if(pagingSorting==null )
				{
					pagingSorting=new PagingSorting();
					objFormBean.setPagingSorting(pagingSorting);
				}
				pagingSorting.setPagingSorting(true,true);
				
				String sortByColumn=pagingSorting.getSortByColumn();
				String sortByOrder=pagingSorting.getSortByOrder();
				int pageNumber=pagingSorting.getPageNumber();
				
				if(sortByColumn==null || "".equals(sortByColumn))
				{
					sortByColumn="createdate";//default flag
				}
				if(sortByOrder==null || "".equals(sortByOrder))
				{
					sortByOrder=PagingSorting.increment;
				}
				if(pageNumber==0)
				{
					pageNumber=1;
				}
				
				pagingSorting.setSortByColumn(sortByColumn);
				pagingSorting.setSortByOrder(sortByOrder);
				pagingSorting.setPageNumber(pageNumber);
				
				objProjectPlan.setPagingSorting(pagingSorting);
						
				listProjectPlan = objModel.fetchProjectDocReport(objProjectPlan);
				request.setAttribute("listProjectPlan", listProjectPlan);
				forwardMapping = "success";
			}
		} 
		catch (Exception e) 
		{
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		if (!messages.isEmpty()) {
			saveErrors(request, messages);
		}
		forward = mapping.findForward(forwardMapping);

		return forward;
	}

	public ActionForward DownloadProjectDocFile(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {

		ActionForward forward = new ActionForward();
		String forwardMapping = null;
		ProjectPlanModel  objModel = new ProjectPlanModel();
		ActionMessages messages = new ActionMessages();
		ProjectPlanReportBean objFormBean = (ProjectPlanReportBean)actionForm;
		ProjectPlanReportDto objDto =new ProjectPlanReportDto();
		byte[] test=null;

		try {
				
				ServletOutputStream objOutputStream = null;
			if(objFormBean.getProjectActionId()!=null)
				objDto.setCheckFlag(objFormBean.getCheckFlag());
				objDto.setProjectActionId(Integer.parseInt(objFormBean.getProjectActionId()));
				test = objModel.DownloadProjectDocFile(objDto);
				
				response.reset();
				response.setHeader("Pragma", "public");
				response.setHeader("Cache-Control", "max-age=0");
				
				CommonBaseModel commonBaseModel=new CommonBaseModel(); 
				String ContentType = commonBaseModel.setContentTypeForFile(objFormBean.getFileName());
				response.setContentType(ContentType);
				
				response.setHeader("Content-Disposition", "attachment;filename=" + objFormBean.getFileName());        	
				        	
				objOutputStream = response.getOutputStream();
				objOutputStream.write(test);
				objOutputStream.flush();
				objOutputStream.close();

				
				forwardMapping = null;//"success";
			
		} catch (Exception e) {
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
		}
		if (!messages.isEmpty()) {
			saveErrors(request, messages);
		}
		forward = mapping.findForward(forwardMapping);

		return forward;
	}
}
