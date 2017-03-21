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
import com.ibm.ioes.npd.beans.ProjectDetailedStatusReportBean;
import com.ibm.ioes.npd.beans.ProjectPlanReportBean;
import com.ibm.ioes.npd.beans.RisksCaptureBean;
import com.ibm.ioes.npd.beans.ValidationBean;
import com.ibm.ioes.npd.hibernate.beans.ProjectDetailedStatusReportDto;
import com.ibm.ioes.npd.hibernate.beans.ProjectPlanReportDto;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjectisssues;
import com.ibm.ioes.npd.model.AssumptionCaptureModel;
import com.ibm.ioes.npd.model.CommonBaseModel;
import com.ibm.ioes.npd.model.ProjectDetailedStatusModel;
import com.ibm.ioes.npd.model.ProjectPlanModel;
import com.ibm.ioes.npd.model.RisksCaptureModel;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.ExcelCreator;
import com.ibm.ioes.npd.utilities.Messages;
import com.ibm.ioes.npd.utilities.PagingSorting;
import com.ibm.ioes.npd.utilities.Utility;

public class ProjectDetailedStatusReportAction extends DispatchAction {
	
	public ActionForward viewProjectStatusDetail(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) 
	{
		ActionForward forward = new ActionForward();
		String forwardMapping = null;
		ProjectDetailedStatusModel objModel = new ProjectDetailedStatusModel();
		ActionMessages messages = new ActionMessages();
		ProjectDetailedStatusReportBean objFormBean = (ProjectDetailedStatusReportBean)actionForm; 
		ProjectDetailedStatusReportDto objProjectStatus = new ProjectDetailedStatusReportDto();
		ArrayList<ProjectDetailedStatusReportDto> listProjectdetailed = null;
		String dateFilter=null;
		String fromDate=null;
		String toDate=null;
		boolean errorsFound=false;
		try 
		{
			
			String searchprojID=objFormBean.getSearchByProjectid();
			//Server Side Security Start for Project Name
			if((objFormBean.getSearchProjectName()!=null) && (!objFormBean.getSearchProjectName().equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(objFormBean.getSearchProjectName(),"Project Name",20),
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
			if((searchprojID!=null) && (!searchprojID.equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(searchprojID,"Project ID",20),
							""+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;	
					}
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Product Manager
			if((objFormBean.getStageName()!=null) && (!objFormBean.getStageName().equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(objFormBean.getStageName(),"Stage Name",20),
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
			if((objFormBean.getTaskName()!=null) && (!objFormBean.getTaskName().equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(objFormBean.getTaskName(),"Task Name",20),
							""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;	
					}
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Document Name
			if((objFormBean.getDocName()!=null) && (!objFormBean.getDocName().equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(objFormBean.getDocName(),"Document Name",20),
							""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;	
					}
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Role Name
			if((objFormBean.getRoleName()!=null) && (!objFormBean.getRoleName().equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(objFormBean.getRoleName(),"Role Name",20),
							""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;	
					}
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Role Name
			if((objFormBean.getDocType()!=null) && (!objFormBean.getDocType().equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(objFormBean.getDocType(),"Doc Type",20),
							""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;	
					}
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Delay In Days
			if((objFormBean.getDelays()!=null) && (!objFormBean.getDelays().equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(objFormBean.getDelays(),"Delay in Days",20),
							""+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
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
				int searchProjectID;
				if (searchprojID==null || searchprojID.equalsIgnoreCase(""))
				{
					 searchProjectID=0;
				}
				else
				{
					searchProjectID=Integer.parseInt(objFormBean.getSearchByProjectid());
				}
				objProjectStatus.setSearchByProjectID(searchProjectID);
				objProjectStatus.setSearchProjectName(objFormBean.getSearchProjectName());
				objProjectStatus.setStageName(objFormBean.getStageName());
				objProjectStatus.setTaskName(objFormBean.getTaskName());
				objProjectStatus.setDocName(objFormBean.getDocName());
				objProjectStatus.setDocType(objFormBean.getDocType());
				objProjectStatus.setProjectStatus(objFormBean.getProjectStatus());
				objProjectStatus.setRoleName(objFormBean.getRoleName());
				
				String Delays=objFormBean.getDelays();
				int del;
				if (Delays==null || Delays.equalsIgnoreCase(""))
				{
					del=0;
				}
				else
				{
					del=Integer.parseInt(objFormBean.getSearchByProjectid());
				}
				objProjectStatus.setDelays(del);
				
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
				
				objProjectStatus.setPagingSorting(pagingSorting);
				
				listProjectdetailed = objModel.fetchProjectStatusDetail(objProjectStatus);
				request.setAttribute("listProjectPlan", listProjectdetailed);
				if (listProjectdetailed.size()>0)
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

	public ActionForward viewExportToExcel(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {

		ActionForward forward = new ActionForward();
		String forwardMapping = null;
		ProjectDetailedStatusModel objModel = new ProjectDetailedStatusModel();
		ActionMessages messages = new ActionMessages();
		ProjectDetailedStatusReportBean objFormBean = (ProjectDetailedStatusReportBean)actionForm; 
		ProjectDetailedStatusReportDto objProjectStatus = new ProjectDetailedStatusReportDto();
		ArrayList<ProjectDetailedStatusReportDto> listDetailedProjectPlan = null;
		String dateFilter=null;
		String fromDate=null;
		String toDate=null;
		boolean errorsFound=false;
		try 
		{
			String searchprojID=objFormBean.getSearchByProjectid();
//			Server Side Security Start for Project Name
			if((objFormBean.getSearchProjectName()!=null) && (!objFormBean.getSearchProjectName().equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(objFormBean.getSearchProjectName(),"Project Name",20),
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
			if((searchprojID!=null) && (!searchprojID.equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(searchprojID,"Project ID",20),
							""+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;	
					}
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Product Manager
			if((objFormBean.getStageName()!=null) && (!objFormBean.getStageName().equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(objFormBean.getStageName(),"Stage Name",20),
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
			if((objFormBean.getTaskName()!=null) && (!objFormBean.getTaskName().equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(objFormBean.getTaskName(),"Task Name",20),
							""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;	
					}
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Document Name
			if((objFormBean.getDocName()!=null) && (!objFormBean.getDocName().equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(objFormBean.getDocName(),"Document Name",20),
							""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;	
					}
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Role Name
			if((objFormBean.getRoleName()!=null) && (!objFormBean.getRoleName().equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(objFormBean.getRoleName(),"Role Name",20),
							""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;	
					}
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Role Name
			if((objFormBean.getDocType()!=null) && (!objFormBean.getDocType().equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(objFormBean.getDocType(),"Doc Type",20),
							""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;	
					}
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Delay In Days
			if((objFormBean.getDelays()!=null) && (!objFormBean.getDelays().equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(objFormBean.getDelays(),"Delay in Days",20),
							""+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
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
				int searchProjectID;
				if (searchprojID==null || searchprojID.equalsIgnoreCase(""))
				{
					 searchProjectID=0;
				}
				else
				{
					searchProjectID=Integer.parseInt(objFormBean.getSearchByProjectid());
				}
				objProjectStatus.setSearchByProjectID(searchProjectID);
				objProjectStatus.setSearchProjectName(objFormBean.getSearchProjectName());
				objProjectStatus.setStageName(objFormBean.getStageName());
				objProjectStatus.setTaskName(objFormBean.getTaskName());
				objProjectStatus.setDocName(objFormBean.getDocName());
				objProjectStatus.setDocType(objFormBean.getDocType());
				objProjectStatus.setProjectStatus(objFormBean.getProjectStatus());
				objProjectStatus.setRoleName(objFormBean.getRoleName());
				
				String Delays=objFormBean.getDelays();
				int del;
				if (Delays==null || Delays.equalsIgnoreCase(""))
				{
					del=0;
				}
				else
				{
					del=Integer.parseInt(objFormBean.getSearchByProjectid());
				}
				objProjectStatus.setDelays(del);
				
				listDetailedProjectPlan = objModel.fetchProjectStatusDetailExcel(objProjectStatus);
				//objFormBean.setListProjectdetailed(objModel.fetchProjectStatusDetailExcel(objProjectStatus));
				//listProjectdetailed = objModel.fetchProjectStatusDetailExcel(objProjectStatus);
				//objFormBean.setListProjectdetailed(listProjectdetailed);
				request.setAttribute("listDetailedProjectPlan", listDetailedProjectPlan);
				forwardMapping = "exporttoExcel";
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
	
	public ActionForward DownloadFile(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {

		ActionForward forward = new ActionForward();
		String forwardMapping = null;
		ProjectDetailedStatusModel objModel = new ProjectDetailedStatusModel();
		ActionMessages messages = new ActionMessages();
		
		ProjectDetailedStatusReportBean objFormBean = (ProjectDetailedStatusReportBean)actionForm; 
		ProjectDetailedStatusReportDto objProjectStatus = new ProjectDetailedStatusReportDto();
		

		try {
				
				ServletOutputStream objOutputStream = null;
				
				if(objFormBean.getDocProjectId()==null 
						|| "".equals(objFormBean.getDocProjectId()) 
						|| objFormBean.getDocTaskId()==null 
						|| "".equals(objFormBean.getDocTaskId()))
				{
					return null;
				}
				
			
				objProjectStatus.setProjectID(Integer.parseInt(objFormBean.getDocProjectId()));
				objProjectStatus.setCurrentTaskId(Long.parseLong(objFormBean.getDocTaskId()));
				
				
				objModel.DownloadProjectDocFile(objProjectStatus);
				
				response.reset();
				response.setHeader("Pragma", "public");
				response.setHeader("Cache-Control", "max-age=0");
				
				CommonBaseModel commonBaseModel=new CommonBaseModel(); 
				String ContentType = commonBaseModel.setContentTypeForFile(objProjectStatus.getDocName());
				response.setContentType(ContentType);
				
				response.setHeader("Content-Disposition", "attachment;filename=" + objProjectStatus.getDocName());        	
				        	
				objOutputStream = response.getOutputStream();
				objOutputStream.write(objProjectStatus.getFileBytes());
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
