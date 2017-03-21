package com.ibm.ioes.npd.actions;

import java.io.FileOutputStream;
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
import com.ibm.ioes.npd.hibernate.dao.ProjectStatusReportDao;
import com.ibm.ioes.npd.model.AssumptionCaptureModel;
import com.ibm.ioes.npd.model.ProjectPlanModel;
import com.ibm.ioes.npd.model.ProjectStatusModel;
import com.ibm.ioes.npd.model.RisksCaptureModel;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.ExcelCreator;
import com.ibm.ioes.npd.utilities.Messages;
import com.ibm.ioes.npd.utilities.PagingSorting;
import com.ibm.ioes.npd.utilities.Utility;
//import com.ve.kavachart.chart.Dataset;

public class ProjectStatusReportAction extends DispatchAction {
	
	public ActionForward viewrProjectStatusDetail(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {

		ActionForward forward = new ActionForward();
		String forwardMapping = null;
		ProjectStatusModel objModel = new ProjectStatusModel();
		ActionMessages messages = new ActionMessages();
		ProjectStatusReportBean objFormBean = (ProjectStatusReportBean)actionForm; 
		ProjectStatusReportDto objProjectStatus = new ProjectStatusReportDto();
		ArrayList<ProjectStatusReportDto> listProjectPlan = null;
		String dateFilter=null;
		String fromDate=null;
		String toDate=null;
		boolean errorsFound=false;
		try 
		{
			String searchProjectName=objFormBean.getSearchProjectName();
			String searchNpdCategory=objFormBean.getSearchNpdCategory();
			String searchProductCatId=objFormBean.getSearchProductCatId();
			dateFilter=objFormBean.getDateFilter();
			fromDate=objFormBean.getFromDate();
			toDate=objFormBean.getToDate();	
			
			//Server Side Security Start for Project Name
			if((searchProjectName!=null) && (!searchProjectName.equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(searchProjectName,"Project Name",20),
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
			
			//Server Side Security Start for NPD Category
			if((searchNpdCategory!=null) && (!searchNpdCategory.equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(searchNpdCategory,"NPD Category",20),
							""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;	
					}
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Product Category
			if((searchProductCatId!=null) && (!searchProductCatId.equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(searchProductCatId,"Product Category",20),
							""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;	
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
					objFormBean.setFromDate(fromDate);
					objFormBean.setToDate(toDate);
				}
				else
				{
					SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
					fromDate = sdf.format(new Date(fromDate));
					toDate = sdf.format(new Date(toDate));
				}
				
				objProjectStatus.setFromDate(fromDate);
				objProjectStatus.setToDate(toDate);
				
				if(dateFilter==null  || "".equals(dateFilter.trim()) || "0".equals(dateFilter.trim()))
				{
					dateFilter="";
					objFormBean.setDateFilter(dateFilter);
				}
				objProjectStatus.setDateFilter(dateFilter);
				objProjectStatus.setFromDate(fromDate);
				objProjectStatus.setToDate(toDate);
				objProjectStatus.setSearchprojectid(objFormBean.getSearchprojectid());
				
				
				objProjectStatus.setSearchProjectName(searchProjectName);
				objProjectStatus.setSearchNpdCategory(searchNpdCategory);
				objProjectStatus.setSearchProductCatId(searchProductCatId);
						
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
					sortByColumn="projectName";//default flag
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
				
				objProjectStatus.setPagingSorting(pagingSorting);
				
				listProjectPlan = objModel.fetchProjectStatusDetail(objProjectStatus);
				request.setAttribute("listProjectPlan", listProjectPlan);
				if (listProjectPlan.size()!=0)
				{
					objFormBean.setCheckRptData(1);
				}
				else
				{
					objFormBean.setCheckRptData(0);
				}
				forwardMapping = "success";
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

	public ActionForward exportProjectStatus(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) 
	{
		ActionForward forward = new ActionForward();
		String forwardMapping = null;
		ProjectStatusModel  objModel = new ProjectStatusModel();
		ActionMessages messages = new ActionMessages();
		ProjectStatusReportBean objFormBean = (ProjectStatusReportBean)actionForm; 
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
		try 
		{	
			objProjectStatus.setProjectidlist(objFormBean.getSearchProjectid());
			listProjectIssue = objModel.exportIssueToExcelForStatus(objProjectStatus);
			StringTokenizer splitStringStage = new StringTokenizer(objFormBean.getSearchProjectid().toString() ,",");
			String[] projectlist=new String[splitStringStage.countTokens()];
			Long[] projectidlist = new Long[projectlist.length];  
			
			for(int i=0; i< projectlist.length;i++ )
			{
				projectidlist[i] = Long.parseLong(splitStringStage.nextToken());
			}
				
			captureBean.setProjectidlist(projectidlist);
			risksCaptureBean.setProjectidlist(projectidlist);
			captureBean.setProjectId(objFormBean.getSearchProjectid());
			
			captureBean = model.getAssumptionForSelectedProject(captureBean);
			
			//captureBean = model.viewAssumption(captureBean,null,null);
			risksCaptureBean.setProjectId(objFormBean.getSearchProjectid());
			risksCaptureBean = risksCaptureModel.getRisksForSelectedProject(risksCaptureBean);
			//risksCaptureBean = risksCaptureModel.viewRisks(risksCaptureBean,null,null);

			
			objProjectPlan.setSearchprojectid(objFormBean.getSearchProjectid());
			objProjectPlan.setProjectStatusFilter(-1);// For Open Project Only
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

	public ActionForward viewChart(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) 
	{
		ActionForward forward = new ActionForward();
		String forwardMapping = null;
		
		String str=getServlet().getServletContext().getRealPath("/Data");
		
		ActionMessages messages = new ActionMessages();
		ProjectStatusReportBean objFormBean = (ProjectStatusReportBean)actionForm; 
		
		ProjectPlanReportDto objProjectPlan = new ProjectPlanReportDto();
		ProjectPlanModel objprojectPlanModel = new ProjectPlanModel();
		ArrayList<ProjectPlanReportDto> ProjectPlanlist = null;
		ArrayList<ProjectPlanReportDto> ProjectTaskStatuslist = null;
		ArrayList<ProjectPlanReportDto> ProjectTaskDelaylist = null;
		ArrayList<ProjectPlanReportDto> ProjectTaskInMonthlist = null;
		try 
		{			
			objProjectPlan.setProjectPlanId(objFormBean.getSearchProjectid());// projectplanid means projectid
			ProjectPlanlist = objprojectPlanModel.viewChart(objProjectPlan,str);
			request.setAttribute("gantChartBean", ProjectPlanlist);
			ProjectTaskStatuslist=objProjectPlan.getTaskStatusList();
			request.setAttribute("TaskStatusPieChartBean", ProjectTaskStatuslist);
			ProjectTaskDelaylist=objProjectPlan.getTaskDelayList();
			request.setAttribute("TaskDelayPieChartBean", ProjectTaskDelaylist);
			ProjectTaskInMonthlist=objProjectPlan.getTaskInMonthList();
			request.setAttribute("TaskInMonthPieChartBean", ProjectTaskInMonthlist);
			request.setAttribute("GanttFileName", objProjectPlan.getFile_Name_Chart());
			request.setAttribute("TaskCount", objProjectPlan.getProjectTaskCount());
			request.setAttribute("OBJ_PROJECT_PLAN", objProjectPlan);
			forward=mapping.findForward("ganttChart");
			
		}
		catch (Exception e)
		{
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
		}
		return forward;
		
	}
	
	public ActionForward viewStageGanttChart(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) 
	{
		ActionForward forward = new ActionForward();
		String forwardMapping = null;
		
		String str=getServlet().getServletContext().getRealPath("/Data");
		
		ActionMessages messages = new ActionMessages();
		ProjectStatusReportBean objFormBean = (ProjectStatusReportBean)actionForm; 
		
		ProjectPlanReportDto objProjectPlan = new ProjectPlanReportDto();
		ProjectPlanModel objprojectPlanModel = new ProjectPlanModel();
		ArrayList<ProjectPlanReportDto> ProjectPlanlist = null;
		ArrayList<ProjectPlanReportDto> ProjectTaskStatuslist = null;
		ArrayList<ProjectPlanReportDto> ProjectTaskDelaylist = null;
		ArrayList<ProjectPlanReportDto> ProjectTaskInMonthlist = null;
		try 
		{			
			objProjectPlan.setProjectPlanId(objFormBean.getSearchProjectid());// projectplanid means projectid
			objProjectPlan.setStagename(objFormBean.getSearchStagename());
			ProjectPlanlist = objprojectPlanModel.viewStageGanttChart(objProjectPlan,str);
			request.setAttribute("GanttFileName", objProjectPlan.getFile_Name_Chart());
			request.setAttribute("TaskCount", objProjectPlan.getProjectTaskCount());
			request.setAttribute("OBJ_PROJECT_PLAN", objProjectPlan);
			forward=mapping.findForward("stageGanttChart");
		}
		catch (Exception e)
		{
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
		}
		return forward;
		
	}
}
