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

import com.ibm.ioes.npd.beans.NewIdeaBean;
import com.ibm.ioes.npd.beans.PerformanceReportBean;
import com.ibm.ioes.npd.beans.TaskInstanceBean;
import com.ibm.ioes.npd.beans.ValidationBean;
import com.ibm.ioes.npd.hibernate.beans.PerformanceReportDto;
import com.ibm.ioes.npd.model.NewIdeaDto;
import com.ibm.ioes.npd.model.NewIdeaModel;
import com.ibm.ioes.npd.model.PerformanceReportModel;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.Messages;
import com.ibm.ioes.npd.utilities.PagingSorting;
import com.ibm.ioes.npd.utilities.Utility;

public class NewIdeaAction extends DispatchAction {

	public ActionForward init(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		//fill name,mailid,phone no,function,location
		NewIdeaBean formBean=(NewIdeaBean)form;
		NewIdeaModel model=new NewIdeaModel();
		ActionMessages messages=new ActionMessages();
		try
		{
			String ssfId=(String)request.getSession().getAttribute(AppConstants.SSFID);
			
			long userStatus=model.loadHRMSDetails(ssfId,formBean);
			if(userStatus==NewIdeaModel.USER_NOT_FOUND)
			{
				messages.add("userNotFoundForNewIdea",new ActionMessage("newIdea.user.not.found"));
				saveMessages(request, messages);
				return mapping.findForward("welcomeOptionPage");
			}
			
			formBean.setMode(NewIdeaBean.MODE_NEW);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in init method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			String forwardMapping = Messages.getMessageValue("errorGlobalForward");
			return mapping.findForward(forwardMapping);
		}
		return mapping.findForward("init");
	}
	
	public ActionForward save(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		ActionForward forward=null;
		NewIdeaBean formBean=(NewIdeaBean)form;
		NewIdeaModel model=new NewIdeaModel();
		ActionMessages messages=new ActionMessages();
		try
		{
			//Server side Validation
//			Validation start
			ArrayList errors=new ArrayList();
			
			//trim all values
			
			/*Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
					("Name of the Idea Generator", formBean.getNameGenerator(), 100),
					Utility.generateCSV(Utility.CASE_MANDATORY,Utility.CASE_SPECIALCHARACTERS,
										Utility.CASE_MAXLENGTH))
					.appendToAndRetNewErrs(errors);
			
			Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
					("Mail Id", formBean.getMailId(), 50),
					Utility.generateCSV(Utility.CASE_MANDATORY,Utility.CASE_SPECIALCHARACTERS,
										Utility.CASE_MAXLENGTH,Utility.CASE_EMAIL))
					.appendToAndRetNewErrs(errors);
			
			Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
					("Phone No", formBean.getPhoneNo(), 25),
					Utility.generateCSV(Utility.CASE_MANDATORY,
										Utility.CASE_MAXLENGTH,Utility.CASE_DIGITS_ONLY))
					.appendToAndRetNewErrs(errors);
			
			Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
					("Function", formBean.getFunction(), 100),
					Utility.generateCSV(Utility.CASE_MANDATORY,Utility.CASE_SPECIALCHARACTERS,
										Utility.CASE_MAXLENGTH))
					.appendToAndRetNewErrs(errors);
			
			Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
					("Location", formBean.getLocation(), 100),
					Utility.generateCSV(Utility.CASE_MANDATORY,Utility.CASE_SPECIALCHARACTERS,
										Utility.CASE_MAXLENGTH))
					.appendToAndRetNewErrs(errors);*/
			
			Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
					("Brief Description", formBean.getBriefDesc(), 1000),
					Utility.generateCSV(Utility.CASE_MANDATORY,Utility.CASE_SPECIALCHARACTERS,
										Utility.CASE_MAXLENGTH))
					.appendToAndRetNewErrs(errors);
			
			Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
					("Benefit of Idea", formBean.getBenefit(), 1000),
					Utility.generateCSV(Utility.CASE_MANDATORY,Utility.CASE_SPECIALCHARACTERS,
										Utility.CASE_MAXLENGTH))
					.appendToAndRetNewErrs(errors);
			
			
			if("Y".equals(formBean.getIsSimilarProductExist()))
			{
				Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
						("Organisation", formBean.getOrganisation(), 50),
						Utility.generateCSV(Utility.CASE_MANDATORY,Utility.CASE_SPECIALCHARACTERS,
											Utility.CASE_MAXLENGTH))
						.appendToAndRetNewErrs(errors);
				
				Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
						("Country", formBean.getCountry(), 50),
						Utility.generateCSV(Utility.CASE_MANDATORY,Utility.CASE_SPECIALCHARACTERS,
											Utility.CASE_MAXLENGTH))
						.appendToAndRetNewErrs(errors);
				
				Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
						("Product Description", formBean.getPrdDescription(), 1000),
						Utility.generateCSV(Utility.CASE_MANDATORY,Utility.CASE_SPECIALCHARACTERS,
											Utility.CASE_MAXLENGTH))
						.appendToAndRetNewErrs(errors);
				Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
						("Market Size", formBean.getMarketSize(), 50),
						Utility.generateCSV(Utility.CASE_MANDATORY,Utility.CASE_SPECIALCHARACTERS,
											Utility.CASE_MAXLENGTH))
						.appendToAndRetNewErrs(errors);
			}
			else if(!"N".equals(formBean.getIsSimilarProductExist()))
			{
				errors.add("Please Select - Is a similar product exist anywhere.");
			}
			
			
			
			Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
					("Industry Verticals", formBean.getIndustryVertical(), 1000),
					Utility.generateCSV(Utility.CASE_MANDATORY,Utility.CASE_SPECIALCHARACTERS,
										Utility.CASE_MAXLENGTH))
					.appendToAndRetNewErrs(errors);
			
			Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
					("USP of Idea", formBean.getUsp(), 1000),
					Utility.generateCSV(Utility.CASE_MANDATORY,Utility.CASE_SPECIALCHARACTERS,
										Utility.CASE_MAXLENGTH))
					.appendToAndRetNewErrs(errors);
			
			
			
			
			if(errors!=null && errors.size()>0)//During Server Side Validation
			{
				saveMessages(request, messages);
				request.setAttribute("validation_errors", errors);
				formBean.setMode(NewIdeaBean.MODE_NEW);
				return mapping.findForward("init");
			}
			
							
			
//			Validation end
			
			
			
			
			
			long ideaId=model.saveIdea(formBean);
			if(ideaId>0)
			{
				formBean.setIdeaId(ideaId);
				//success
				messages.add("saveIdea",new ActionMessage("ideaInsertSuccessWithId","The generated Id is :"+ideaId));//If 100% accuracy is required for  generated idea id use lock by uncommenting code in dao  it.
				//messages.add("saveIdea",new ActionMessage("recordInsertSuccess"));
				model.sendMailForIdea(formBean);
			}
			else
			{
				//failure
				messages.add("saveIdea",new ActionMessage("recordInsertFailure"));
			}
			
			saveMessages(request, messages);
			forward=mapping.findForward("welcomeOptionPage");
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in save method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			String forwardMapping = Messages.getMessageValue("errorGlobalForward");
			return mapping.findForward(forwardMapping);
		}
		
		
		return forward;
	}
	
	public ActionForward backForSave(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		return mapping.findForward("welcomeOptionPage");
	}
	
	public ActionForward list(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		
		
		
		String forwardMapping = null;
		
		String fromDate=null;
		String toDate=null;
		NewIdeaBean formBean=(NewIdeaBean)form;
		NewIdeaModel model=new NewIdeaModel();
		
		ActionMessages messages = new ActionMessages();
		
		
		
		boolean errorsFound=false;
		try 
		{
			fromDate=formBean.getFromDate();
			toDate=formBean.getToDate();			
			if(fromDate==null || "".equals(fromDate) || toDate==null || "".equals(toDate))
			{
				SimpleDateFormat sdf=new SimpleDateFormat(Messages.getMessageValue("calendar_entry_format"));
				long time=System.currentTimeMillis();
				toDate=sdf.format(new Date(time));
				fromDate=sdf.format(new Date(time-AppConstants.one_hour_millisec));
				formBean.setToDate(toDate);
				formBean.setFromDate(fromDate);
			}
			
			ArrayList errors=new ArrayList();
			
			//Server Side Security Start for Date Filter
			Object obArray[]={""+ValidationBean.VN_DATE_COMPARISION,formBean.getFromDate(),"From Date",
					formBean.getToDate(),"To Date",ValidationBean.LESS_EQUAL,
					new java.text.SimpleDateFormat(Messages.getMessageValue("calendar_entry_format"))};
			Utility.validateValue(	new ValidationBean(obArray),
					""+Utility.CASE_DATECOMPARISION_MANDATORY).appendToAndRetNewErrs(errors);
			
			Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
					("Search Field- Name", formBean.getSearchName(), 50),
					Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS,
										Utility.CASE_MAXLENGTH))
					.appendToAndRetNewErrs(errors);
			
			Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
					("Search Field- Oracle Id", formBean.getSearchOracleId(), 50),
					Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS,
										Utility.CASE_MAXLENGTH))
					.appendToAndRetNewErrs(errors);
			
			Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
					("Search Field- Email Id", formBean.getSearchEmailId(), 50),
					Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS,
										Utility.CASE_MAXLENGTH))
					.appendToAndRetNewErrs(errors);
			
			if(errors!=null && errors.size()>0)//During Server Side Validation
			{
				saveMessages(request, messages);
				request.setAttribute("validation_errors", errors);
				request.setAttribute("reportData", new ArrayList());
				
				forwardMapping = "reportPage";
				return mapping.findForward(forwardMapping);
			}
			
			else
			{
				model.getIdeaList(formBean);
				ArrayList<NewIdeaDto> reportList=formBean.getReportList();
				request.setAttribute("reportData", reportList);
				
				if("exportExcel".equals(formBean.getExportExcel()))
				{
					forwardMapping = "excelPage";
				}
				else
				{
					forwardMapping = "reportPage";
				}
				
			}
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in list method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			forwardMapping = Messages.getMessageValue("errorGlobalForward");
			return mapping.findForward(forwardMapping);
			
		}
		if (!messages.isEmpty()) 
		{
			saveErrors(request, messages);
		}
			
		
		return mapping.findForward(forwardMapping);
	}
	
	
	
	
}
