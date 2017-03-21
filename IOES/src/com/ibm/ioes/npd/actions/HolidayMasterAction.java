package com.ibm.ioes.npd.actions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import com.ibm.ioes.npd.beans.AssumptionCaptureBean;
import com.ibm.ioes.npd.beans.HolidayMasterBean;
import com.ibm.ioes.npd.beans.PerformanceReportBean;
import com.ibm.ioes.npd.beans.ValidationBean;
import com.ibm.ioes.npd.hibernate.beans.HolidayMasterDto;
import com.ibm.ioes.npd.hibernate.beans.PerformanceReportDto;
import com.ibm.ioes.npd.model.HolidayMasterModel;
import com.ibm.ioes.npd.model.PerformanceReportModel;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.Messages;
import com.ibm.ioes.npd.utilities.PagingSorting;
import com.ibm.ioes.npd.utilities.Utility;

public class HolidayMasterAction extends DispatchAction
{
	public ActionForward displayHolidayList(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		ActionForward forward = new ActionForward();
		String forwardMapping = null;
		String dateFilter=null;
		String fromDate=null;
		String toDate=null;
		HolidayMasterModel model=new HolidayMasterModel();
		ActionMessages messages = new ActionMessages();
		HolidayMasterBean formBean = (HolidayMasterBean)form;
		HolidayMasterDto objPerformanceReport= new HolidayMasterDto();
		ArrayList<HolidayMasterDto> holidayDetailList = new ArrayList<HolidayMasterDto>();
		boolean errorsFound=false;
		try 
		{
			String searchHolidayName=formBean.getSearchHolidayName();
			fromDate=formBean.getFromDate();
			toDate=formBean.getToDate();	
			
			if((searchHolidayName!=null) && (!searchHolidayName.equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(searchHolidayName,"Holiday Name",30),
							""+Utility.CASE_SPECIALCHARACTERS).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;
					}
				}
			}
			
			if(fromDate!=null && (!fromDate.equalsIgnoreCase(""))
					|| toDate!=null && (!toDate.equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					Object obArray[]={""+ValidationBean.VN_DATE_COMPARISION,fromDate,"From Date",
							toDate,"To Date",ValidationBean.LESS_EQUAL,
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
		

			if(errorsFound)//During Server Side Validation
			{
				saveMessages(request, messages);
				return mapping.findForward("success");
			}
			else
			{
			
				objPerformanceReport.setSearchHolidayName(searchHolidayName);
						
				if(fromDate==null || "".equals(fromDate.trim()))
				{
					SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
					toDate="";
					fromDate="";
					formBean.setFromDate(fromDate);
					formBean.setToDate(toDate);
				}
				else
				{
					SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
					fromDate = sdf.format(new Date(fromDate));
					toDate = sdf.format(new Date(toDate));
				}
				
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
					sortByColumn="holidayDate";//default flag
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
	
				holidayDetailList = model.viewHolidayList(objPerformanceReport);
				request.setAttribute("holidayDetailList", holidayDetailList);
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

	public ActionForward AddHoliday(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		ActionForward forward = new ActionForward();
		String forwardMapping = null;
		String dateFilter=null;
		String fromDate=null;
		String toDate=null;
		HolidayMasterModel model=new HolidayMasterModel();
		ActionMessages messages = new ActionMessages();
		HolidayMasterBean formBean = (HolidayMasterBean)form;
		HolidayMasterDto addHolidayDto= new HolidayMasterDto();
		int addStatus = 0;
		boolean errorsFound=false;
		try
		{
			String HolidayID=formBean.getHolidayID();
			
			//Server Side Security Start for Holiday ID
			if((HolidayID!=null) && (!HolidayID.equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(HolidayID,"Holiday ID",20),
							""+Utility.CASE_DIGITS_ONLY+","+Utility.CASE_MANDATORY).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;	
					}
				}
			}
			
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(formBean.getHolidayName(),"Holiday Name",20),
						""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH+","+Utility.CASE_MANDATORY).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;	
				}
			}
		
			//Server Side Security Start for Date of Holiday
			if(!errorsFound)
			{
				Object arr[]=new Object[]{ValidationBean.VN_DATE_VALID+"",
						formBean.getHolidayDate(),
						"Holiday Date",
						new SimpleDateFormat(Messages.getMessageValue("calendar_entry_format"))};
				ArrayList errors=Utility.validateValue(new ValidationBean(arr),
						""+Utility.CASE_VN_DATE_VALID+","+Utility.CASE_MANDATORY).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;	
				}
			}
			//Server Side Security End for Date of Holiday
			
			if(errorsFound)
			{
				saveMessages(request, messages);
				forward = mapping.findForward("success");
				return forward;
			}
			else
			{
			
				if((HolidayID.equalsIgnoreCase("")) || (HolidayID.equalsIgnoreCase("null")))
				{
					HolidayID="null";
				}
				else
				{
					addHolidayDto.setHolidayID(Integer.parseInt(HolidayID));
				}
				addHolidayDto.setActionType(formBean.getActionType());
				addHolidayDto.setHolidayName(formBean.getHolidayName());
				addHolidayDto.setHolidayDate(formBean.getHolidayDate());
				addStatus=model.addHoliday(addHolidayDto);
				formBean.setHolidayName(null);
				formBean.setHolidayDate(null);
				formBean.setActionType(0);
				
				if (addStatus == 1) 
				{
					messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
							AppConstants.RECORD_SAVE_SUCCESS));
				} 
				else 
				{
					messages.add(AppConstants.RECORD_SAVE_FAILURE,
							new ActionMessage(AppConstants.RECORD_SAVE_FAILURE));
				}
				if (!messages.isEmpty()) 
				{
					this.saveMessages(request, messages);
				}
				displayHolidayList(mapping, form, request, response);
				forwardMapping = "holidayAdded";
			}
		}
		catch (Exception e) {
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
		}

		forward = mapping.findForward(forwardMapping);
		return forward;
	}
	
}
