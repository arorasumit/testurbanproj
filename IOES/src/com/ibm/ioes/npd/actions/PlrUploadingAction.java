package com.ibm.ioes.npd.actions;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;
import org.hibernate.Session;

import com.ibm.ioes.npd.beans.MyToDoListFormBean;
import com.ibm.ioes.npd.beans.PlrUploadingFormBean;
import com.ibm.ioes.npd.beans.ProjectPlanReportBean;
import com.ibm.ioes.npd.beans.ValidationBean;
import com.ibm.ioes.npd.exception.NpdException;
import com.ibm.ioes.npd.hibernate.beans.MyToDoListDto;
import com.ibm.ioes.npd.hibernate.beans.PlrUploadingDto;
import com.ibm.ioes.npd.hibernate.beans.ProjectPlanReportDto;
import com.ibm.ioes.npd.hibernate.beans.TmEmployee;
import com.ibm.ioes.npd.hibernate.beans.TmRoleempmapping;
import com.ibm.ioes.npd.hibernate.dao.CommonBaseDao;
import com.ibm.ioes.npd.hibernate.dao.PlrUploadingDaoImpl;
import com.ibm.ioes.npd.model.MyToDoListServicesImpl;
import com.ibm.ioes.npd.model.PlrUploadingServicesImpl;
import com.ibm.ioes.npd.model.ProjectPlanModel;
import com.ibm.ioes.npd.utilities.*;

public class PlrUploadingAction extends DispatchAction {
	
	public ActionForward viewProjectList(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {

		ActionForward forward = new ActionForward();
		String forwardMapping = null;
		ActionMessages messages = new ActionMessages();
		PlrUploadingFormBean objFormBean = (PlrUploadingFormBean)actionForm;
		PlrUploadingDto objProjectPlan = new PlrUploadingDto();
		Session hibernateSession = null;
		
		try {
			
			
			
			
			hibernateSession = CommonBaseDao.beginTrans();
			CommonBaseDao objCommonDao = new CommonBaseDao();
			PlrUploadingServicesImpl objService = new PlrUploadingServicesImpl();
			String employeeId[] = null;
			ArrayList<PlrUploadingDto> mapplst = new ArrayList<PlrUploadingDto>(); 
			objProjectPlan.setSearchProjectId(Utility.decryptString(objFormBean.getProjectId()));
			mapplst = objService.alreadyMapStakeHolder(objProjectPlan);
			request.setAttribute("mappedlist",mapplst);
			
			forwardMapping = "mapStakeHolder";
			
		} catch (Exception e) {
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
		}
		finally
    	{
    		CommonBaseDao.closeTrans(hibernateSession);
    	}
		if (!messages.isEmpty()) {
			saveErrors(request, messages);
		}
		forward = mapping.findForward(forwardMapping);

		return forward;
	}
	
	@SuppressWarnings("unused")
	public ActionForward projectList(ActionMapping mapping, ActionForm form,HttpServletRequest request, 
		    HttpServletResponse response)throws Exception
		    {
				//logger.info("My To Do List Action.");
				PlrUploadingFormBean searchForm=(PlrUploadingFormBean)form;
				
				//Validation start
				ArrayList errors=new ArrayList();
				
				Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
						("Project Id", searchForm.getSearchproject(), 10),
						Utility.generateCSV(Utility.CASE_DIGITS_ONLY,
											Utility.CASE_MAXLENGTH))
						.appendToAndRetNewErrs(errors);
				
				Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
						("Project Name", searchForm.getSearchProjectPlanName(), 100),
						Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS,
											Utility.CASE_MAXLENGTH))
						.appendToAndRetNewErrs(errors);
				
				Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
						("Priority of Launch", searchForm.getSearchLaunchPriority(), 25),
						Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS,
											Utility.CASE_MAXLENGTH))
						.appendToAndRetNewErrs(errors);
				
				
				if(errors!=null && errors.size()>0)//During Server Side Validation
				{
					request.setAttribute("validation_errors", errors);
					searchForm.setSearchproject(null);
					searchForm.setSearchProjectPlanName(null);
					searchForm.setSearchLaunchPriority(null);
				}
				//Validation end
				
				PlrUploadingDto searchDto =new PlrUploadingDto();
				
				Date curDate = CommonUtilities.getSystemDate();
				SimpleDateFormat sdfCurDate=new SimpleDateFormat("MMMM");
				String str = sdfCurDate.format(curDate);
				
				
				SimpleDateFormat sdfCurday=new SimpleDateFormat("dd");
				int curDay = Integer.parseInt(sdfCurday.format(curDate));
				
				System.err.println(str);

				//PlrUploadingDaoImpl a = new PlrUploadingDaoImpl();
				
					//a.getMappedStakeholderDetails(str);
					//a.plrEscalation(str);
					

				
				String forwardMapping = null;
				String dateFilter=null;
				String fromDate=null;
				String toDate=null;
				String projectName = null;
				
				TmEmployee tmEmployee =	(TmEmployee)request.getSession().getAttribute(AppConstants.LOGINBEAN);
					
				String userId = String.valueOf(tmEmployee.getNpdempid()); 
				String RoleId = String.valueOf(tmEmployee.getCurrentRoleId());

				
				projectName = searchForm.getSearchProjectPlanName();
				searchDto.setSearchProjectPlanName(projectName);
				if(userId!=null)
					
				{
					searchForm.setUserId(userId);
					searchForm.setRoleid(RoleId);
					searchDto.setUserId(Integer.parseInt(userId));
					searchDto.setRoleId(Integer.parseInt(RoleId));
				}

						if(fromDate==null || "".equals(fromDate.trim()))
				{
					SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
					toDate=sdf.format(new Date(System.currentTimeMillis()));
					
					Calendar cal=new GregorianCalendar();
					cal.setTime(new Date(System.currentTimeMillis()));
					cal.add(Calendar.MONTH, -1);
					fromDate=sdf.format(cal.getTime());			
					searchForm.setFromDate(fromDate);
					searchForm.setToDate(toDate);
				}
				
				searchDto.setFromDate(fromDate);
				searchDto.setToDate(toDate);
				
				if(dateFilter==null  || "".equals(dateFilter.trim()) || "0".equals(dateFilter.trim()))
				{
					dateFilter="";
					searchForm.setDateFilter(dateFilter);
				}
				searchDto.setDateFilter(dateFilter);		
				searchDto.setDateFilter(dateFilter);
				searchDto.setFromDate(fromDate);
				searchDto.setToDate(toDate);
				
				if(searchForm.getSearchproject()!=null && !searchForm.getSearchproject().equalsIgnoreCase(""))
				{
					searchDto.setSearchProjectId(searchForm.getSearchproject());
				}
				
				if(searchForm.getSearchLaunchPriority()!=null && !searchForm.getSearchLaunchPriority().equalsIgnoreCase(""))
				{
					searchDto.setSearchLaunchPriority(searchForm.getSearchLaunchPriority());
				}
				
				
				String sortBy=searchForm.getSortBy();
				
				if(sortBy==null || "".equals(sortBy.trim()))
				{	searchDto.setSortBy("projectId");
					searchForm.setSortBy("projectId");
				}
				else	
				{
					searchDto.setSortBy(searchForm.getSortBy());
				}
				
				String sortOrderBy=searchForm.getSortByOrder();
				if(sortOrderBy==null || "".equals(sortOrderBy.trim()))
				{	searchDto.setSortByOrder("increment");
					searchForm.setSortByOrder("increment");
				}
				else
				{
					searchDto.setSortByOrder(searchForm.getSortByOrder());
				}
				
				
					
		    	PlrUploadingServicesImpl objServices = new PlrUploadingServicesImpl(); 
		    	
//		    	 set paging since sorting is always done as search.setSortBy
				PagingSorting paging = new PagingSorting();
				int pageNumber;
				String pN = searchForm.getPageNumber();
				if (pN == null || "".equals(pN.trim())) {
					pageNumber = 1;
					searchForm.setPageNumber(String.valueOf(pageNumber));
				} 
				else 
				{
					pageNumber = Integer.parseInt(pN);
				}

				paging.setPageNumber(pageNumber);
				searchDto.setPagingSorting(paging);
				try
		    	{	
					if(CommonUtilities.isAuthorised(request, String.valueOf(tmEmployee.getCurrentRoleId()),null)==false)
						throw new Exception();

					ArrayList<PlrUploadingDto> objAccountList = new ArrayList<PlrUploadingDto>();
					objAccountList = objServices.viewProjectList(searchDto);
					request.setAttribute("accountList",objAccountList);
					searchForm.setMaxPageNumber(String.valueOf(paging.getMaxPageNumber()));
					searchForm.setPageRecords(paging.getPageRecords());
					forwardMapping = "success";
		    	}
		    	catch(Exception ex)
		    	{
		    		forwardMapping = Messages.getMessageValue("errorGlobalForward");
					//logger.error(ex.getMessage()+ " Exception occured in myToDoList method of "+ this.getClass().getSimpleName());
					return mapping.findForward(forwardMapping);
		    	}		
		    	return mapping.findForward(forwardMapping);
		    }

	
	public ActionForward mapStakeholder(ActionMapping mapping, ActionForm form,HttpServletRequest request, 
		    HttpServletResponse response)throws Exception
		    {
				//logger.info("My To Do List Action.");
				PlrUploadingFormBean searchForm=(PlrUploadingFormBean)form;
				PlrUploadingDto searchDto =new PlrUploadingDto();
				
				
				ArrayList errors=new ArrayList();
				//Validation
				String csv=searchForm.getSelectedRow();
				if(csv!=null && csv.trim().length()!=0)
				{
					String[] eachRow=csv.split(",");
					for (String row : eachRow) {
						if(!"".equals(row.trim()))
						{
							String[] row_values=row.split("_");
							if(row_values.length!=3)
							{
								//error
							}
							else
							{
								String duration=row_values[0];
								ArrayList newerrors=Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
										("Duration", duration, 2),
										Utility.generateCSV(Utility.CASE_MANDATORY,Utility.CASE_DIGITS_ONLY,
															Utility.CASE_MAXLENGTH))
										.appendToAndRetNewErrs(errors);
								if(newerrors==null)
								{
									if(0==Integer.parseInt(duration))
									{
										errors=new ArrayList();
										errors.add("Only values greater than 0 are in Duration");
									}
								}
								
								break;
						}
					}
				}
				}
					if(errors!=null && errors.size()>0)//During Server Side Validation
					{
						
						errors.add("and please refill the form.");
						request.setAttribute("validation_errors", errors);
						viewProjectList(mapping, form, request, response);
						return mapping.findForward("mapStakeHolder");
					}
					
					
				
				TmEmployee tmEmployee =	(TmEmployee)request.getSession().getAttribute(AppConstants.LOGINBEAN);
				String userId = String.valueOf(tmEmployee.getNpdempid()); //((SessionDto)request.getSession().getAttribute("sessionDto")).getCwsId();
				String RoleId = String.valueOf(tmEmployee.getCurrentRoleId()) ;//((SessionDto)request.getSession().getAttribute("sessionDto")).getRoleId();
				
				String forwardMapping = null;
				Session hibernateSession = null;
				PlrUploadingServicesImpl objServices = new PlrUploadingServicesImpl(); 
				try
		    	{	


					searchDto.setStakeholderidlist(searchForm.getSelectedRow());
					searchDto.setSearchProjectId(Utility.decryptString(searchForm.getProjectId()));
					
					boolean isSaved = objServices.mapStakeHolder(searchDto);
					if(isSaved ==true)
						searchForm.setHiddenReturnFlag("1");
					else
						searchForm.setHiddenReturnFlag("0");
					
					//hibernateSession = CommonBaseDao.beginTrans();
					//CommonBaseDao objCommonDao = new CommonBaseDao();
					//searchForm.setLstEmployee(objCommonDao.getAllEntriesInATable(hibernateSession, HibernateBeansConstants.HBM_EMPLOYEE));
					viewProjectList(mapping,form,request,response);
					forwardMapping="mapStakeHolder";
		    	}
		    	catch(Exception ex)
		    	{
		    		forwardMapping = Messages.getMessageValue("errorGlobalForward");
					//logger.error(ex.getMessage()+ " Exception occured in myToDoList method of "+ this.getClass().getSimpleName());
					return mapping.findForward(forwardMapping);
		    	}
		    	return mapping.findForward(forwardMapping);
		    }

	public ActionForward uploadPlr(ActionMapping mapping, ActionForm form,HttpServletRequest request, 
		    HttpServletResponse response)throws Exception
		    {
		//logger.info("My To Do List Action.");
		PlrUploadingFormBean searchForm=(PlrUploadingFormBean)form;
		PlrUploadingDto searchDto =new PlrUploadingDto();
		
		TmEmployee tmEmployee =	(TmEmployee)request.getSession().getAttribute(AppConstants.LOGINBEAN);
		String userId = String.valueOf(tmEmployee.getNpdempid()); //((SessionDto)request.getSession().getAttribute("sessionDto")).getCwsId();
		String RoleId = String.valueOf(tmEmployee.getCurrentRoleId()) ;//((SessionDto)request.getSession().getAttribute("sessionDto")).getRoleId();
		
		String forwardMapping = null;
		ArrayList lstMonthYear = new ArrayList();
		PlrUploadingServicesImpl objServices = new PlrUploadingServicesImpl(); 
		try
    	{	
			searchDto.setSearchProjectId(Utility.decryptString(searchForm.getProjectId()));
			searchDto.setRoleid(RoleId);
			lstMonthYear = objServices.getPendingMonthList(searchDto);
			searchForm.setLstMonthYear(lstMonthYear);
			request.setAttribute("lstMonthYear",lstMonthYear);
			forwardMapping="uploadPlr";
    	}
    	catch(Exception ex)
    	{
    		forwardMapping = Messages.getMessageValue("errorGlobalForward");
			//logger.error(ex.getMessage()+ " Exception occured in myToDoList method of "+ this.getClass().getSimpleName());
			return mapping.findForward(forwardMapping);
    	}		
    	return mapping.findForward(forwardMapping);

	}
	
	public ActionForward uploadPlrDoc(ActionMapping mapping, ActionForm form,HttpServletRequest request, 
		    HttpServletResponse response)throws Exception
		    {
		//logger.info("My To Do List Action.");
		PlrUploadingFormBean searchForm=(PlrUploadingFormBean)form;
		
		
		//validation start
		ArrayList errors=new ArrayList();
		
		Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
						("Plr for Period", searchForm.getMonthid(), 10),
						Utility.generateCSV(Utility.CASE_MANDATORY,Utility.CASE_DIGITS_ONLY,
											Utility.CASE_MAXLENGTH))
						.appendToAndRetNewErrs(errors);
		
		if(errors!=null && errors.size()>0)//During Server Side Validation
		{
			
			request.setAttribute("validation_errors", errors);
			uploadPlr(mapping, form, request, response);
			return mapping.findForward("uploadPlr");
		}
		
		
		//validation end
		
		PlrUploadingDto searchDto =new PlrUploadingDto();
		
		TmEmployee tmEmployee =	(TmEmployee)request.getSession().getAttribute(AppConstants.LOGINBEAN);
		String userId = String.valueOf(tmEmployee.getNpdempid()); //((SessionDto)request.getSession().getAttribute("sessionDto")).getCwsId();
		String RoleId = String.valueOf(tmEmployee.getCurrentRoleId()) ;//((SessionDto)request.getSession().getAttribute("sessionDto")).getRoleId();
		
		String forwardMapping = null;
		
		PlrUploadingServicesImpl objServices = new PlrUploadingServicesImpl();
		ActionMessages messages = new ActionMessages();
		boolean isSaved = false;
		try
    	{	

			searchDto.setAttachment(searchForm.getAttachment());
			boolean validation_error=false; 
			
			FormFile file=searchForm.getAttachment();
			validation_error=Utility.validateAttachment(file, messages);
			if(validation_error)
			{
				//uploadPlr(mapping, form, request, response);
				saveMessages(request, messages);
				forwardMapping="uploadPlr";
				uploadPlr(mapping, form, request, response);
				return mapping.findForward(forwardMapping);
			}
			else
			{
				
			}

			searchDto.setSearchProjectId(Utility.decryptString(searchForm.getProjectId()));
			searchDto.setUserId(Integer.parseInt(userId));
			searchDto.setSearchPlrId(searchForm.getMonthid());
			isSaved = objServices.UploadPlr(searchDto);
			if(isSaved ==true)
				searchForm.setHiddenReturnFlag("1");
			else
				searchForm.setHiddenReturnFlag("0");				
			forwardMapping="uploadPlr";
    	}
    	catch(Exception ex)
    	{
    		forwardMapping = Messages.getMessageValue("errorGlobalForward");
			//logger.error(ex.getMessage()+ " Exception occured in myToDoList method of "+ this.getClass().getSimpleName());
			return mapping.findForward(forwardMapping);
    	}		
    	return mapping.findForward(forwardMapping);

		    }


	public ActionForward viewhistory(ActionMapping mapping, ActionForm form,HttpServletRequest request, 
		    HttpServletResponse response)throws Exception
		    {
		//logger.info("My To Do List Action.");
		PlrUploadingFormBean searchForm=(PlrUploadingFormBean)form;
		
//		Validation start
		ArrayList errors=new ArrayList();
		
		if(searchForm.getEmployeeId()!=null )
		{
			if(searchForm.getEmployeeId().length==0)
			{
				searchForm.setEmployeeId(null);
			}
			else
			{
				if(!"ALL".equalsIgnoreCase(searchForm.getEmployeeId()[0]))
				{
					Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
							("StakeHolder", searchForm.getEmployeeId()[0], 15),
							Utility.generateCSV(Utility.CASE_DIGITS_ONLY))
							.appendToAndRetNewErrs(errors);
					
				}
			}
		}
		
		if(errors!=null && errors.size()>0)//During Server Side Validation
		{
			request.setAttribute("validation_errors", errors);
			searchForm.setEmployeeId(null);
		}
		//Validation end
		
		PlrUploadingDto searchDto =new PlrUploadingDto();
		
		TmEmployee tmEmployee =	(TmEmployee)request.getSession().getAttribute(AppConstants.LOGINBEAN);
		String userId = String.valueOf(tmEmployee.getNpdempid()); //((SessionDto)request.getSession().getAttribute("sessionDto")).getCwsId();
		String RoleId = String.valueOf(tmEmployee.getCurrentRoleId()) ;//((SessionDto)request.getSession().getAttribute("sessionDto")).getRoleId();
		

		String forwardMapping = null;
		ArrayList<PlrUploadingDto> objAccountList = new ArrayList<PlrUploadingDto>();
		PlrUploadingServicesImpl objServices = new PlrUploadingServicesImpl();
		String sortBy=searchForm.getSortBy();
		if(sortBy==null || "".equals(sortBy.trim()))
		{	searchDto.setSortBy("version");
			searchForm.setSortBy("version");
		}
		else	
		{
			searchDto.setSortBy(searchForm.getSortBy());
		}
		
		String sortOrderBy=searchForm.getSortByOrder();
		if(sortOrderBy==null || "".equals(sortOrderBy.trim()))
		{	searchDto.setSortByOrder("increment");
			searchForm.setSortByOrder("increment");
		}
		else
		{
			searchDto.setSortByOrder(searchForm.getSortByOrder());
		}
			
		PagingSorting paging = new PagingSorting();
		int pageNumber;
		String pN = searchForm.getPageNumber();
		if (pN == null || "".equals(pN.trim())) {
			pageNumber = 1;
			searchForm.setPageNumber(String.valueOf(pageNumber));
		} 
		else 
		{
			pageNumber = Integer.parseInt(pN);
		}

		paging.setPageNumber(pageNumber);
		searchDto.setPagingSorting(paging);

		try
    	{			
			//searchForm.setLstEmployee(objCommonDao.getAllEntriesInATable(hibernateSession, HibernateBeansConstants.HBM_EMPLOYEE));
			String searchProjectId = Utility.decryptString(searchForm.getProjectId());
			searchDto.setSearchProjectId(searchProjectId);
			ArrayList empList=objServices.alreadyMapStakeHolder(searchDto);
			
			TreeSet<PlrUploadingDto> empSet=new TreeSet<PlrUploadingDto>(new MyComparator());
			empSet.addAll(empList);
			
			searchForm.setLstemployee(new ArrayList(empSet));
			String stakeholderid = null;
			
			if(searchForm.getEmployeeId()!=null)
			{	
				stakeholderid = searchForm.getEmployeeId()[0].toString();
				if(stakeholderid.equalsIgnoreCase("All"))
					stakeholderid = null;	
			}	
			searchDto.setStakeholderid(stakeholderid);
			objAccountList = objServices.viewDocHistory(searchDto);
			request.setAttribute("accountList",objAccountList);
			searchForm.setMaxPageNumber(String.valueOf(paging.getMaxPageNumber()));
			searchForm.setPageRecords(paging.getPageRecords());
			forwardMapping="viewDocHistory";
    	}
    	catch(Exception ex)
    	{
    		forwardMapping = Messages.getMessageValue("errorGlobalForward");
			//logger.error(ex.getMessage()+ " Exception occured in myToDoList method of "+ this.getClass().getSimpleName());
			return mapping.findForward(forwardMapping);
    	}		
    	return mapping.findForward(forwardMapping);

	}

	
	
	
}
class MyComparator implements Comparator<PlrUploadingDto>
{

	public int compare(PlrUploadingDto o1, PlrUploadingDto o2) {
		return o1.getEmployeelabel().compareToIgnoreCase(o2.getEmployeelabel());
	}
	
}