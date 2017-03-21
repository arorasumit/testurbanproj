package com.ibm.ioes.npd.actions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
import com.ibm.ioes.npd.utilities.PagingSorting;

public class ProjectDocsReportAction extends DispatchAction {
	
	public ActionForward viewrProjectDocs(ActionMapping mapping, ActionForm actionForm,
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
			forwardMapping = "success";
			
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

		try {
			
			objProjectStatus.setSearchProjectid(Integer.parseInt(objFormBean.getSearchProjectid()));
			listProjectIssue = objModel.exportIssueToExcel(objProjectStatus);
			captureBean.setProjectId(objFormBean.getSearchProjectid());
			captureBean = model.getAssumptionForAProject(captureBean);
			captureBean = model.viewAssumption(captureBean,null,null);
			
			risksCaptureBean = risksCaptureModel.getRisksForAProject(risksCaptureBean);
			risksCaptureBean = risksCaptureModel.viewRisks(risksCaptureBean,null,null);

			
			objProjectPlan.setSearchProjectId(Integer.parseInt(objFormBean.getSearchProjectid()));
			listProjectPlan = objprojectPlanModel.ExportProjectPlanDetail(objProjectPlan);
			
			ExcelCreator excelCreator = new ExcelCreator();
			HSSFWorkbook workbook = excelCreator.createWorkbook(listProjectIssue,captureBean.getAssumptionDetailList(),risksCaptureBean.getRiskDetailList(),listProjectPlan,listProjectPlan);
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
				
			if(objFormBean.getProjectActionId()!=null)
				objDto.setProjectActionId(Integer.parseInt(objFormBean.getProjectActionId()));
				test = objModel.DownloadFile(objDto);
				forwardMapping = "success";
			
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
