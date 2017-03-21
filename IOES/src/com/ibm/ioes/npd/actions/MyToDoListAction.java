package com.ibm.ioes.npd.actions;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

import com.ibm.appsecure.util.Encryption;
import com.ibm.appsecure.util.IEncryption;
import com.ibm.ioes.npd.beans.MyToDoListFormBean;
import com.ibm.ioes.npd.beans.ProjectPlanReportBean;
import com.ibm.ioes.npd.beans.ValidationBean;
import com.ibm.ioes.npd.hibernate.beans.MyToDoListDto;
import com.ibm.ioes.npd.hibernate.beans.ProjectPlanReportDto;
import com.ibm.ioes.npd.hibernate.beans.TmEmployee;
import com.ibm.ioes.npd.model.CommonBaseModel;
import com.ibm.ioes.npd.model.MyToDoListServicesImpl;
import com.ibm.ioes.npd.model.ProjectPlanModel;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.Messages;
import com.ibm.ioes.npd.utilities.PagingSorting;
import com.ibm.ioes.npd.utilities.Utility;

public class MyToDoListAction extends DispatchAction 
{
	/**
	 * Logger for the class.
	**/
	private static final Logger logger;	
	static 
	{
		logger = Logger.getLogger(MyToDoListAction.class);
	}
	
	
	public ActionForward myToDoList(ActionMapping mapping, ActionForm form,HttpServletRequest request, 
    HttpServletResponse response)throws Exception
    {
		logger.info("My To Do List Action.");
		MyToDoListFormBean searchForm=(MyToDoListFormBean)form;
		MyToDoListDto searchDto =new MyToDoListDto();
		
		TmEmployee tmEmployee =	(TmEmployee)request.getSession().getAttribute(AppConstants.LOGINBEAN);
		
		String userId = String.valueOf(tmEmployee.getNpdempid());
		String RoleId = String.valueOf(tmEmployee.getCurrentRoleId());
		
		String forwardMapping = null;
		String dateFilter=null;
		String fromDate=null;
		String toDate=null;
		if(userId!=null)
		{
			searchDto.setUserId(Integer.parseInt(userId));
			searchDto.setRoleId(Integer.parseInt(RoleId));
		}
		searchDto.setStatus(Integer.parseInt(searchForm.getChangeStatus()));
	
		if(searchForm.getSeachprojectId()!=null && !searchForm.getSeachprojectId().equalsIgnoreCase(""))
			searchDto.setSeachprojectId(searchForm.getSeachprojectId());
			
			searchDto.setSeachprojectName(searchForm.getSeachprojectName());	
			searchDto.setSearchPriorityOfLaunch(searchForm.getSearchPriorityOfLaunch());
			searchDto.setSearchProductBrief(searchForm.getSearchProductBrief());
			searchDto.setTargetmarket(searchForm.getTargetmarket());
			searchDto.setProductcategory(searchForm.getProductcategory());
			searchDto.setNpscategory(searchForm.getNpscategory());
			
		
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
			
	    	MyToDoListServicesImpl objServices = new MyToDoListServicesImpl(); 
	    	
	    	//set paging since sorting is always done as search.setSortBy
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
			searchDto.setPaging(paging);
			try
	    	{	
				ArrayList<MyToDoListDto> objAccountList = new ArrayList<MyToDoListDto>();
				ArrayList<MyToDoListDto> listOutStanding = new ArrayList<MyToDoListDto>();
				ArrayList<MyToDoListDto> listBcpDetails = new ArrayList<MyToDoListDto>();
				ArrayList commonList = new ArrayList();
				objAccountList = objServices.myToDoList(searchDto);
				request.setAttribute("accountList",objAccountList);
				//Paging: setting max Pages
				searchForm.setMaxPageNumber(String.valueOf(paging.getMaxPageNumber()));
				searchForm.setPageRecords(""+paging.getPageRecords());
				//commonList = objServices.outStandingSummaryList();
				/*if(commonList.size()>0)
				{
					listOutStanding = (ArrayList<MyToDoListDto>)commonList.get(0);	
					listBcpDetails = (ArrayList<MyToDoListDto>)commonList.get(1);
				}*/		
				request.setAttribute("ourStandingSummaryList",listOutStanding); 
				request.setAttribute("bcpList",listBcpDetails); 
				forwardMapping = "myToDoList";
	    	}
	    	catch(Exception ex)
	    	{
	    		forwardMapping = Messages.getMessageValue("errorGlobalForward");
				logger.error(ex.getMessage()+ " Exception occured in myToDoList method of "+ this.getClass().getSimpleName());
				return mapping.findForward(forwardMapping);
	    	}		
    	return mapping.findForward(forwardMapping);
    }
	
	
	public ActionForward approveTask(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		MyToDoListFormBean objformBean = (MyToDoListFormBean)form;
		String forwardMapping = null;
		forwardMapping="approveTask";
		return mapping.findForward(forwardMapping);
	}
	
	public ActionForward rejectTask(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		MyToDoListFormBean objformBean = (MyToDoListFormBean)form;
		//projectId="+projectId+"&stageId="+stages+"&searchprojectid="+searchprojectid+"&mainprojectid="+varmainprojectid;	
		objformBean.setTaskId(Utility.decryptString(objformBean.getTaskId()));
		objformBean.setProjectId(Utility.decryptString(objformBean.getProjectId()));
		objformBean.setStageId(Utility.decryptString(objformBean.getStageId()));
		objformBean.setSearchprojectid(Utility.decryptString(objformBean.getSearchprojectid()));
		objformBean.setMainprojectid(Utility.decryptString(objformBean.getMainprojectid()));
		String forwardMapping = null;
		forwardMapping="rejectTask";
		return mapping.findForward(forwardMapping);
	}
	

	public ActionForward RFITask(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	
	{
		MyToDoListFormBean objformBean = (MyToDoListFormBean)form; 
		MyToDoListDto objDto = new MyToDoListDto();
		MyToDoListServicesImpl objService = new MyToDoListServicesImpl();
		String forwardMapping = null;
		try
		{
			objDto.setSeachprojectId(Utility.decryptString(objformBean.getSearchprojectid()));
			objDto = objService.alreadyMapStakeHolder(objDto);
			objformBean.setLstEmployee(objDto.getTmployeelst());

		forwardMapping="RFITask";
		}
    	catch(Exception ex)
    	{
    		forwardMapping = Messages.getMessageValue("errorGlobalForward");
			logger.error(ex.getMessage()+ " Exception occured in myToDoList method of "+ this.getClass().getSimpleName());
			return mapping.findForward(forwardMapping);
    	}		
		return mapping.findForward(forwardMapping);
	}

	public ActionForward saveApproveTask(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	
	{
		String forwardMapping = null;
		MyToDoListFormBean objForm = (MyToDoListFormBean)form;
		ActionMessages messages = new ActionMessages();
		boolean validation_error=false;
		IEncryption objsecure = new Encryption();
		boolean errorsFound=false;
		try
		{
			TmEmployee tmEmployee =	(TmEmployee)request.getSession().getAttribute(AppConstants.LOGINBEAN);
			String userId = String.valueOf(tmEmployee.getNpdempid());
			//Server Side Security Start for Employee ID
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(userId,"Employee ID",10),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			String RoleId = String.valueOf(tmEmployee.getCurrentRoleId());
			//Server Side Security Start for Role ID
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(RoleId,"Role ID",10),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Task ID
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(Utility.decryptString(objForm.getTaskId()),"Task ID",10),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Stage ID
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(Utility.decryptString(objForm.getStageId()),"Stage ID",10),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Project ID
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(Utility.decryptString(objForm.getProjectId()),"Project ID",10),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Task Comments
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(objForm.getTaskapproveComments(),"Task Comments",1000),
						Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			if(errorsFound)
			{
				saveMessages(request, messages);
				forwardMapping="approveTask";
				return mapping.findForward(forwardMapping);
			}
			else
			{
				MyToDoListDto objDto = new MyToDoListDto();
				objDto.setTaskIdList(Utility.decryptString(objForm.getTaskId()));
				objDto.setStageIdList(Utility.decryptString(objForm.getStageId()));
				objDto.setUserId(Integer.parseInt(userId));
				objDto.setProjectId(Integer.parseInt(Utility.decryptString(objForm.getProjectId())));
				FormFile file=objForm.getAttachment();
				validation_error=Utility.validateAttachment(file, messages);
				if(validation_error)
				{
					//uploadPlr(mapping, form, request, response);
					saveMessages(request, messages);
					forwardMapping="approveTask";
					return mapping.findForward(forwardMapping);
				}
				else
				{
					
				}
				validation_error=Utility.isAttachementBlank(file, messages , Utility.decryptString(objForm.getIsTaskMandatory()));
				if(validation_error)
				{
					saveMessages(request, messages);
					forwardMapping="approveTask";
					return mapping.findForward(forwardMapping);
					
				}
				objDto.setAttachedDoc(objForm.getAttachment());
				objForm.setHiddenReturnFlag("1");
				objDto.setTaskapproveComments(objForm.getTaskapproveComments());
				MyToDoListServicesImpl objModel = new MyToDoListServicesImpl();
				objModel.updateTask(objDto);
				forwardMapping="approveTask";
			}
		}
		catch(Exception ex)
		{
			logger.error(ex.getMessage()+ " Exception occured in saveTaskClouser method of "+ this.getClass().getSimpleName());
		}
		return mapping.findForward(forwardMapping);
	}

	public ActionForward saveRejectTask(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	
	{
		String forwardMapping = null;
		MyToDoListFormBean objForm = (MyToDoListFormBean)form;
		ActionMessages messages = new ActionMessages();
		boolean validation_error=false; 
		boolean errorsFound=false;
		try
		{
			TmEmployee tmEmployee =	(TmEmployee)request.getSession().getAttribute(AppConstants.LOGINBEAN);
			String userId = String.valueOf(tmEmployee.getNpdempid());
			//Server Side Security Start for Employee ID
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(userId,"Employee ID",10),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			String RoleId = String.valueOf(tmEmployee.getCurrentRoleId());
			//Server Side Security Start for Role ID
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(RoleId,"Role ID",10),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Task ID
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(objForm.getTaskId(),"Task ID",10),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Stage ID
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(objForm.getStageId(),"Stage ID",10),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Project ID
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(objForm.getProjectId(),"Project ID",10),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Task Comments
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(objForm.getTaskrejectComments(),"Reject Comments",1000),
						Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Main Project ID
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(objForm.getMainprojectid(),"Main Project ID",10),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			if(errorsFound)
			{
				saveMessages(request, messages);
				forwardMapping="rejectTask";
				return mapping.findForward(forwardMapping);
			}
			else
			{
				MyToDoListDto objDto = new MyToDoListDto();
				objDto.setTaskIdList(objForm.getTaskId());
				objDto.setStageIdList(objForm.getStageId());
				objDto.setUserId(Integer.parseInt(userId));
				objDto.setProjectId(Integer.parseInt(objForm.getProjectId()));
				objDto.setMainprojectid(Integer.parseInt(objForm.getMainprojectid()));
				FormFile file=objForm.getAttachment();
				validation_error=Utility.validateAttachment(file, messages);
				if(validation_error)
				{
					//uploadPlr(mapping, form, request, response);
					saveMessages(request, messages);
					forwardMapping="rejectTask";
					return mapping.findForward(forwardMapping);
				}
				else
				{
					
				}
				validation_error=Utility.isAttachementBlank(file, messages , objForm.getIsTaskMandatory() );
				if(validation_error)
				{
					saveMessages(request, messages);
					forwardMapping="rejectTask";
					return mapping.findForward(forwardMapping);
					
				}
				objDto.setAttachedDoc(objForm.getAttachment());
				objForm.setHiddenReturnFlag("1");
				objDto.setTaskrejectComments(objForm.getTaskrejectComments());
				MyToDoListServicesImpl objModel = new MyToDoListServicesImpl();
				int status=objModel.rejectTask(objDto);
				if(status==1)
				{
					//send mail
					objModel.sendEmailForReject(objDto);
					//String taskName = getTaskDetail(tranDto); 
					//String[] mailid = {userid};
					//SendMail objSendMail = new SendMail();
					
					//objSendMail.postMail(mailid, null, null, "Task Rejected - Project Closed", "A Task Has been Assigned " + taskName, null, null);
					
					
				}
				
				//send mail
				forwardMapping="approveTask";
			}
			
		}
		catch(Exception ex)
		{
			logger.error(ex.getMessage()+ " Exception occured in saveTaskClouser method of "+ this.getClass().getSimpleName());
		}
		return mapping.findForward(forwardMapping);
	}
	
	public ActionForward viewrProjectPlanDetail(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	
	{
		MyToDoListFormBean objformBean = (MyToDoListFormBean)form; 
		String forwardMapping = null;
		forwardMapping="viewWorkflow";
		return mapping.findForward(forwardMapping);
	}
		
	public ActionForward saveApproveRFI(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	
	{
		String forwardMapping = null;
		MyToDoListFormBean objForm = (MyToDoListFormBean)form;
		ActionMessages messages = new ActionMessages();
		boolean validation_error=false; 
		boolean errorsFound=false;
		try
		{
			MyToDoListDto objDto = new MyToDoListDto();
			//Server Side Security Start for Employee ID
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(objForm.getEmployeeId(),"Employee ID",10),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Role ID
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(objForm.getRolemapped(),"Role ID",10),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Task ID
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(Utility.decryptString(objForm.getTaskId()),"Task ID",10),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Stage ID
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(Utility.decryptString(objForm.getStageId()),"Stage ID",10),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Project ID
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(Utility.decryptString(objForm.getProjectId()),"Project ID",10),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Task Comments
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(objForm.getTaskapproveComments(),"Task Comments",1000),
						Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			if(errorsFound)
			{
				saveMessages(request, messages);
				forwardMapping="RFITask";
				RFITask(mapping, form, request, response);
				objForm.setEmployeeId(null);
				
				return mapping.findForward(forwardMapping);
			}
			else
			{
				objDto.setTaskIdList(Utility.decryptString(objForm.getTaskId()));
				objDto.setStageIdList(Utility.decryptString(objForm.getStageId()));
				objDto.setEmployeeId(objForm.getEmployeeId());
				//TODO Change Made BY Sumit on 09-Mar-2010 (Start) Pending RFI Should Be According to role
				objDto.setRoleid(objForm.getRolemapped());
				objDto.setProjectId(Integer.parseInt(Utility.decryptString(objForm.getProjectId())));
				
				FormFile file=objForm.getAttachment();
				validation_error=Utility.validateAttachment(file, messages);
				if(validation_error)
				{
					//uploadPlr(mapping, form, request, response);
					saveMessages(request, messages);
					forwardMapping="RFITask";
					RFITask(mapping, form, request, response);
					objForm.setEmployeeId(null);
					
					return mapping.findForward(forwardMapping);
				}
				else
				{
					objDto.setAttachedDoc(objForm.getAttachment());	
				}
	
				objForm.setHiddenReturnFlag("1");
				objDto.setTaskapproveComments(objForm.getTaskapproveComments());
				MyToDoListServicesImpl objModel = new MyToDoListServicesImpl();
				objModel.updateRFI(objDto);
				forwardMapping="RFITask";
			}
		}
		catch(Exception ex)
		{
			logger.error(ex.getMessage()+ " Exception occured in saveTaskClouser method of "+ this.getClass().getSimpleName());
		}
		return mapping.findForward(forwardMapping);
	}
	public ActionForward viewrProjectPlanDetail1(ActionMapping mapping, ActionForm actionForm,
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
			String projectStatusFilter = null;
			

		try {
			
			String searchStageName=objFormBean.getSearchStageName();
			String searchTaskName=objFormBean.getSearchTaskName();
			
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
			request.setAttribute("listProjectPlan", listProjectPlan);
			forwardMapping = "viewWorkflow";
			
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
		MyToDoListServicesImpl  objModel = new MyToDoListServicesImpl();
		ActionMessages messages = new ActionMessages();
		MyToDoListFormBean objFormBean = (MyToDoListFormBean)actionForm;
		MyToDoListDto objDto =new MyToDoListDto();
		byte[] test=null;

		try {
				
				ServletOutputStream objOutputStream = null;
				if(objFormBean.getSearchprojectworkflowid()!=null)
				{
					objDto.setProjectworkflowid(objFormBean.getSearchprojectworkflowid());
					objDto.setSearchtaskid(objFormBean.getSearchtaskid());
				}
				
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


}
