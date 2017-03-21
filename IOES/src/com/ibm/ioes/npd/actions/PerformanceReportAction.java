package com.ibm.ioes.npd.actions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;


import com.ibm.ioes.npd.beans.PerformanceReportBean;
import com.ibm.ioes.npd.beans.ValidationBean;
import com.ibm.ioes.npd.hibernate.beans.PerformanceReportDto;
import com.ibm.ioes.npd.model.PerformanceReportModel;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.Messages;
import com.ibm.ioes.npd.utilities.PagingSorting;
import com.ibm.ioes.npd.utilities.Utility;

public class PerformanceReportAction extends DispatchAction
{
	public ActionForward initPerformanceReport(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		ActionForward forward = new ActionForward();
		String forwardMapping = null;
		String dateFilter=null;
		String fromDate=null;
		String toDate=null;
		PerformanceReportModel model=new PerformanceReportModel();
		ActionMessages messages = new ActionMessages();
		PerformanceReportBean formBean = (PerformanceReportBean)form;
		PerformanceReportDto objPerformanceReport= new PerformanceReportDto();
		ArrayList<PerformanceReportDto> listPerformanceReport = new ArrayList<PerformanceReportDto>();
		boolean errorsFound=false;
		try 
		{
			String searchProductManager=formBean.getProductManager();
			String searchProjectName=formBean.getProjectName();
			String projID=formBean.getSearchProjectID();
			int searchProjStatus=formBean.getSearchProjectStatus();
			String deviation=formBean.getSearchDeviation();
			dateFilter=formBean.getDateFilter();
			fromDate=formBean.getFromDate();
			toDate=formBean.getToDate();			
			
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
			if((projID!=null) && (!projID.equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(projID,"Project ID",20),
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
			if((searchProductManager!=null) && (!searchProductManager.equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(searchProductManager,"Product Manager",20),
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
			if((String.valueOf(searchProjStatus)!=null) && (!String.valueOf(searchProjStatus).equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(String.valueOf(searchProjStatus),"Project Status",20),
							""+Utility.CASE_SPECIALCHARACTERS).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;	
					}
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Deviation
			if((deviation!=null) && (!deviation.equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(deviation,"Deviation",5),
							""+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
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
				
				if((!formBean.getFromDate().equalsIgnoreCase("") && formBean.getFromDate()!=null)
						|| (!formBean.getToDate().equalsIgnoreCase("") && formBean.getToDate()!=null))
				{
					if(!errorsFound)
					{
						Object obArray[]={""+ValidationBean.VN_DATE_COMPARISION,formBean.getFromDate(),"From Date",
								formBean.getToDate(),"To Date",ValidationBean.LESS_EQUAL,
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
					formBean.setFromDate(fromDate);
					formBean.setToDate(toDate);
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
					formBean.setFromDate(fromDate);
					formBean.setToDate(toDate);
				}
				else
				{
					SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
					//fromDate = sdf.format(new Date(fromDate));
					toDate = sdf.format(new Date(toDate));
				}
				
				objPerformanceReport.setSearchProductManager(searchProductManager);
				objPerformanceReport.setProjectName(searchProjectName);
				objPerformanceReport.setSearchFilter(formBean.getSearchFilter());
				
				int searchProjectID;
				if (projID==null || projID.equalsIgnoreCase(""))
				{
					 searchProjectID=0;
				}
				else
				{
					searchProjectID=Integer.parseInt(formBean.getSearchProjectID());
				}
				objPerformanceReport.setProjectID(searchProjectID);			
				objPerformanceReport.setProjectStatus(searchProjStatus);
			
				int searchDev;
				if (deviation==null || deviation.equalsIgnoreCase(""))
				{
					searchDev=0;
				}
				else
				{
					searchDev=Integer.parseInt(formBean.getSearchDeviation());
				}
				objPerformanceReport.setDeviation(searchDev);
				
				objPerformanceReport.setFromDate(fromDate);
				objPerformanceReport.setToDate(toDate);
				
				if(dateFilter==null  || "".equals(dateFilter.trim()) || "0".equals(dateFilter.trim()))
				{
					dateFilter="";
					formBean.setDateFilter(dateFilter);
				}
				objPerformanceReport.setDateFilter(dateFilter);
				objPerformanceReport.setFromDate(fromDate);
				objPerformanceReport.setToDate(toDate);
				
				PagingSorting pagingSorting=formBean.getPagingSorting();
				if(pagingSorting==null )
				{
					pagingSorting=new PagingSorting();
					formBean.setPagingSorting(pagingSorting);
				}
				pagingSorting.setPagingSorting(true,true);
				
				String sortByColumn=pagingSorting.getSortByColumn();
				String sortByOrder=pagingSorting.getSortByOrder();
				int pageNumber=pagingSorting.getPageNumber();
				
				if(sortByColumn==null || "".equals(sortByColumn))
				{
					sortByColumn="projectManager";//default flag
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
				
				objPerformanceReport.setPagingSorting(pagingSorting);
	
				listPerformanceReport = model.viewPerformanceReport(objPerformanceReport);
				request.setAttribute("listPerformanceReport", listPerformanceReport);
				if (listPerformanceReport.size()!=0)
				{
					formBean.setCheckRptData(1);
				}
				else
				{
					formBean.setCheckRptData(0);
				}
				forwardMapping = "success";
			}
		} 
		catch (Exception e) 
		{
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		if (!messages.isEmpty()) 
		{
			saveErrors(request, messages);
		}
		forward = mapping.findForward(forwardMapping);
	
		return forward;
	}
	
	public ActionForward viewExportToExcel(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		ActionForward forward = new ActionForward();
		String forwardMapping = null;
		String dateFilter=null;
		String fromDate=null;
		String toDate=null;
		PerformanceReportModel model=new PerformanceReportModel();
		ActionMessages messages = new ActionMessages();
		PerformanceReportBean formBean = (PerformanceReportBean)form;
		PerformanceReportDto objPerformanceReport= new PerformanceReportDto();
		ArrayList<PerformanceReportDto> listPerformanceReport = new ArrayList<PerformanceReportDto>();
		boolean errorsFound=false;
		try 
		{
			String searchProductManager=formBean.getProductManager();
			String searchProjectName=formBean.getProjectName();
			String projID=formBean.getSearchProjectID();
			int searchProjStatus=formBean.getSearchProjectStatus();
			String deviation=formBean.getSearchDeviation();
			dateFilter=formBean.getDateFilter();
			fromDate=formBean.getFromDate();
			toDate=formBean.getToDate();	
			
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
			if((projID!=null) && (!projID.equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(projID,"Project ID",20),
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
			if((searchProductManager!=null) && (!searchProductManager.equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(searchProductManager,"Product Manager",20),
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
			if((String.valueOf(searchProjStatus)!=null) && (!String.valueOf(searchProjStatus).equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(String.valueOf(searchProjStatus),"Project Status",20),
							""+Utility.CASE_SPECIALCHARACTERS).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;	
					}
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Deviation
			if((deviation!=null) && (!deviation.equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(deviation,"Deviation",5),
							""+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
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
				
				if((!formBean.getFromDate().equalsIgnoreCase("") && formBean.getFromDate()!=null)
						|| (!formBean.getToDate().equalsIgnoreCase("") && formBean.getToDate()!=null))
				{
					if(!errorsFound)
					{
						Object obArray[]={""+ValidationBean.VN_DATE_COMPARISION,formBean.getFromDate(),"From Date",
								formBean.getToDate(),"To Date",ValidationBean.LESS_EQUAL,
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
					formBean.setFromDate(fromDate);
					formBean.setToDate(toDate);
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
					formBean.setFromDate(fromDate);
					formBean.setToDate(toDate);
				}
				else
				{
					SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
					//fromDate = sdf.format(new Date(fromDate));
					toDate = sdf.format(new Date(toDate));
				}
			
				objPerformanceReport.setProjectName(searchProjectName);
				objPerformanceReport.setSearchFilter(formBean.getSearchFilter());
				objPerformanceReport.setSearchProductManager(searchProductManager);
			
				int searchProjectID;
				if (projID==null || projID.equalsIgnoreCase(""))
				{
					 searchProjectID=0;
				}
				else
				{
					searchProjectID=Integer.parseInt(formBean.getSearchProjectID());
				}
				objPerformanceReport.setProjectID(searchProjectID);
				objPerformanceReport.setProjectStatus(searchProjStatus);
				
				int searchDev;
				if (deviation==null || deviation.equalsIgnoreCase(""))
				{
					searchDev=0;
				}
				else
				{
					searchDev=Integer.parseInt(formBean.getSearchDeviation());
				}
				objPerformanceReport.setDeviation(searchDev);
				objPerformanceReport.setFromDate(fromDate);
				objPerformanceReport.setToDate(toDate);
			
					if(dateFilter==null  || "".equals(dateFilter.trim()) || "0".equals(dateFilter.trim()))
					{
						dateFilter="";
						formBean.setDateFilter(dateFilter);
					}
					objPerformanceReport.setDateFilter(dateFilter);
					objPerformanceReport.setFromDate(fromDate);
					objPerformanceReport.setToDate(toDate);
			
				PagingSorting pagingSorting=formBean.getPagingSorting();
				if(pagingSorting==null )
				{
					pagingSorting=new PagingSorting();
					formBean.setPagingSorting(pagingSorting);
				}
				pagingSorting.setPagingSorting(true,true);
				
				String sortByColumn=pagingSorting.getSortByColumn();
				String sortByOrder=pagingSorting.getSortByOrder();
				int pageNumber=pagingSorting.getPageNumber();
				
				if(sortByColumn==null || "".equals(sortByColumn))
				{
					sortByColumn="projectManager";//default flag
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
				
				objPerformanceReport.setPagingSorting(pagingSorting);
	
				listPerformanceReport = model.viewPerformanceReportExport(objPerformanceReport);
				request.setAttribute("listPerformanceReport", listPerformanceReport);
				if (listPerformanceReport.size()!=0)
				{
					formBean.setCheckRptData(1);
				}
				else
				{
					formBean.setCheckRptData(0);
				}
				forwardMapping = "exportToExcel";
			}
		} 
		catch (Exception e) 
		{
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		if (!messages.isEmpty()) 
		{
			saveErrors(request, messages);
		}
		forward = mapping.findForward(forwardMapping);
	
		return forward;
	}
}
