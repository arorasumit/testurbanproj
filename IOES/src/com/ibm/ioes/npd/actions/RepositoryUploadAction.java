package com.ibm.ioes.npd.actions;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

import com.ibm.ioes.npd.beans.RepositoryUploadBean;
import com.ibm.ioes.npd.beans.ValidationBean;
import com.ibm.ioes.npd.hibernate.beans.RepositoryUploadDto;
import com.ibm.ioes.npd.model.RepositoryUploadModel;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.PagingSorting;
import com.ibm.ioes.npd.utilities.Utility;

public class RepositoryUploadAction extends DispatchAction
{
	public ArrayList transactionArrayList = new ArrayList();
	
	public ActionForward initDisplayNPDCategory(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	   {
			ActionForward forward = new ActionForward();
			String forwardMapping = null;
			ActionMessages messages = new ActionMessages();
			RepositoryUploadModel model=new RepositoryUploadModel();
			RepositoryUploadBean formBean = (RepositoryUploadBean)form; 
			RepositoryUploadDto objDto = new RepositoryUploadDto();
			ArrayList<RepositoryUploadDto> listNPDCategory = new ArrayList<RepositoryUploadDto>();
			try 
			{
				listNPDCategory = model.getNPDList(objDto);
				request.setAttribute("listNPDCategory", listNPDCategory);
				formBean.setListNPDcategory(listNPDCategory);
				forwardMapping = "success";
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
	   
	public ActionForward getProjectList(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
   {
		ActionForward forward = new ActionForward();
		String forwardMapping = null;
		ActionMessages messages = new ActionMessages();
		RepositoryUploadModel model=new RepositoryUploadModel();
		RepositoryUploadBean formBean = (RepositoryUploadBean)form; 
		RepositoryUploadDto objDto = new RepositoryUploadDto();
		String transactionID;
		boolean errorsFound=false;
		try 
		{
			transactionID=formBean.getNpdCategoryFilter();
			if(transactionID!=null && !"-1".equals(transactionID) && !"0".equals(transactionID)) 
			{
				//Searching
				String searchProjectName=formBean.getProjectName();			
				String projID=formBean.getSearchProjectID();
			
				//Server Side Validation Starts
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(transactionID,"NPD Category",1000),
							""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MANDATORY).getCompleteMessageStrings();					
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;
					}
				}
				
				if((searchProjectName!=null) && (!searchProjectName.equalsIgnoreCase("")))
				{
					if(!errorsFound)
					{
						ArrayList errors=Utility.validateValue(new ValidationBean(searchProjectName,"Project Name",30),
								""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MANDATORY).getCompleteMessageStrings();					
						if(errors!=null)
						{
							request.setAttribute("validation_errors", errors);
							errorsFound=true;
						}
					}
				}
				
				if((projID!=null) && (!projID.equalsIgnoreCase("")))
				{
					if(!errorsFound)
					{
						ArrayList errors=Utility.validateValue(new ValidationBean(projID,"Project ID",5),
								""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MANDATORY+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();					
						if(errors!=null)
						{
							request.setAttribute("validation_errors", errors);
							errorsFound=true;
						}
					}
				}
				//Server Side Validation Ends
				
				if(errorsFound)//During Server Side Validation
				{
					saveMessages(request, messages);
					formBean.setListNPDcategory(model.getNPDList(objDto));
					formBean.setNpdCategoryFilter("-1");
					forwardMapping = "failure";
					forward = mapping.findForward(forwardMapping);
					return forward;
				}
				else
				{
					objDto.setProjectName(searchProjectName);
					int searchProjectID;
					if (projID==null || projID.equalsIgnoreCase(""))
					{
						 searchProjectID=0;
					}
					else
					{
						searchProjectID=Integer.parseInt(formBean.getSearchProjectID());
					}
					objDto.setProjectID(searchProjectID);
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
					
					objDto.setPagingSorting(pagingSorting);
					
					formBean.setListProjects(model.getProjectList(objDto,transactionID));
					formBean.setListNPDcategory(model.getNPDList(objDto));
					forwardMapping = "showProjects";
				}
				
			}
			else
			{
				formBean.setListNPDcategory(model.getNPDList(objDto));
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
	
	public ActionForward viewStage(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		ActionForward forward = new ActionForward();
		RepositoryUploadBean formBean=(RepositoryUploadBean) form;
		RepositoryUploadModel model = new RepositoryUploadModel();
		ActionMessages messages = new ActionMessages();
		String forwardMapping = null;
		RepositoryUploadDto objDto = new RepositoryUploadDto();
		boolean errorsFound=false;
		try 
		{
		  	String projID=formBean.getSelectedProjID();
		  	if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(projID,"Project ID",1000),
						""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MANDATORY).getCompleteMessageStrings();					
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
		   	String projectName=formBean.getSelectedProjName();
		   	if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(projectName,"Project Name",1000),
						""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MANDATORY).getCompleteMessageStrings();					
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			String projectWorkflowID=formBean.getSelectedWorkFlowID();
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(projectWorkflowID,"Project WorkFlow ID",1000),
						""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MANDATORY).getCompleteMessageStrings();					
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			
			if(errorsFound)//During Server Side Validation
			{
				saveMessages(request, messages);
				formBean.setListNPDcategory(model.getNPDList(objDto));
				formBean.setNpdCategoryFilter("-1");
				BeanUtils.copyProperties(new RepositoryUploadBean(), formBean);
				forwardMapping="failure";
			}
			else
			{
				formBean.setProjectName(projectName);
				formBean.setListStage(model.getStage(objDto,projID));
				formBean.setProjWorkFlowID(projectWorkflowID);
				formBean.setDocType(AppConstants.TYPE);
				forwardMapping = "showStages";
			}
		} 
		catch (Exception e) 
		{
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
		}
		if (!messages.isEmpty()) {
			//saveErrors(request, messages);
		}

		forward = mapping.findForward(forwardMapping);

		return forward;
	}

	public ActionForward goToMenu(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		ActionForward forward = new ActionForward();
		RepositoryUploadBean formBean=(RepositoryUploadBean) form;
		RepositoryUploadModel model = new RepositoryUploadModel();
		ActionMessages messages = new ActionMessages();
		String forwardMapping = null;
		RepositoryUploadDto objDto = new RepositoryUploadDto();
		try 
		{
			String projID=request.getParameter("projectID");
			String projectName=request.getParameter("projectName");
			formBean.setProjectName(projectName);
			formBean.setListStage(model.getStage(objDto,projID));
			formBean.setDocType(AppConstants.TYPE);
			forwardMapping = "showStages";
		} 
		catch (Exception e) 
		{
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
		}
		if (!messages.isEmpty()) {
			//saveErrors(request, messages);
		}

		forward = mapping.findForward(forwardMapping);

		return forward;
	}
	
	public ActionForward getViewList(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		ActionForward forward = new ActionForward();
		RepositoryUploadBean formBean=(RepositoryUploadBean) form;
		RepositoryUploadModel model = new RepositoryUploadModel();
		ActionMessages messages = new ActionMessages();
		String forwardMapping = null;
		RepositoryUploadDto objDto = new RepositoryUploadDto();
		try 
		{
			String stageID=Utility.decryptString(request.getParameter("stageID"));
			String projectId=Utility.decryptString(request.getParameter("projectId"));
			formBean.setListTask(model.getTask(objDto,stageID,projectId));
			forwardMapping = "getViewList";
		} 
		catch (Exception e) 
		{
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
		}
		if (!messages.isEmpty()) {
			//saveErrors(request, messages);
		}

		forward = mapping.findForward(forwardMapping);

		return forward;
	}

	public ActionForward saveDocument(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		ActionForward forward = new ActionForward();
		RepositoryUploadBean formBean=(RepositoryUploadBean) form;
		String forwardMapping = null;
		RepositoryUploadDto objDto = new RepositoryUploadDto();
		RepositoryUploadModel model=new RepositoryUploadModel();
		ActionMessages messages = new ActionMessages();
		ArrayList<RepositoryUploadDto> listNPDCategory = new ArrayList<RepositoryUploadDto>();
		boolean insert = true;
		boolean errorsFound=false;
		int taskID;
		int projID;
		int stageID;
		int workFlowID;
		try
		{
			FormFile Docattachment=null;
			Docattachment=formBean.getAttachment();
			
			boolean validation_error=false; 
			FormFile file=Docattachment;
			validation_error=Utility.validateAttachment(file,messages);
			if(validation_error)
			{
				formBean.setSelectedWorkFlowID(formBean.getProjWorkFlowID());
				formBean.setSelectedProjName(formBean.getProjectName());
				viewStage(mapping, form, request, response);
				saveMessages(request, messages);
				forwardMapping="showStages";
				return mapping.findForward(forwardMapping);
			}
			else
			{
				
			}
			
			String DocName=null;
			DocName=formBean.getDocName();
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(DocName,"Document Name",1000),
						""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MANDATORY).getCompleteMessageStrings();					
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(formBean.getSelectedProjID(),"Project Name",1000),
						""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MANDATORY).getCompleteMessageStrings();					
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(formBean.getProjectName(),"Project Name",1000),
						""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MANDATORY).getCompleteMessageStrings();					
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			
			String StageIDfromBean=null;
			StageIDfromBean=formBean.getStageID();
			if((StageIDfromBean!=null) && (!StageIDfromBean.equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(StageIDfromBean,"Stage Name",1000),
							""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MANDATORY).getCompleteMessageStrings();					
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;
					}
				}
			}
			
			String TaskIDfromBean=null;
			TaskIDfromBean=formBean.getSelectedTaskID();
			if((TaskIDfromBean!=null) && (!TaskIDfromBean.equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(TaskIDfromBean,"Task Name",1000),
							""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MANDATORY).getCompleteMessageStrings();					
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;
					}
				}
			}
			
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(formBean.getProjWorkFlowID(),"Project Workflow ID",1000),
						""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MANDATORY).getCompleteMessageStrings();					
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			
			if(errorsFound)//During Server Side Validation
			{
				saveMessages(request, messages);
				formBean.setListStage(model.getStage(objDto,formBean.getSelectedProjID()));
				formBean.setProjWorkFlowID(formBean.getProjWorkFlowID());
				formBean.setDocType(AppConstants.TYPE);
				return mapping.findForward("showStages");
			}
			else
			{
				projID=Integer.parseInt(formBean.getSelectedProjID());
				if (StageIDfromBean.equalsIgnoreCase("") || StageIDfromBean==null)
				{
					stageID=0;
				}
				else
				{
					stageID=Integer.parseInt(StageIDfromBean);
				}
				if (TaskIDfromBean.equalsIgnoreCase("") || TaskIDfromBean==null)
				{
					taskID=0;
				}
				else
				{
					taskID=Integer.parseInt(TaskIDfromBean);
				}
				workFlowID=Integer.parseInt(formBean.getProjWorkFlowID());
				insert =model.AddDocs(DocName,Docattachment,projID,stageID,taskID,workFlowID);
				listNPDCategory = model.getNPDList(objDto);
				request.setAttribute("listNPDCategory", listNPDCategory);
				formBean.setListNPDcategory(listNPDCategory);
				forwardMapping="success";
			}
		}
		catch (Exception e) 
		{
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
		}
		if (insert) {
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.RECORD_SAVE_SUCCESS));

		} else {
			messages.add(AppConstants.RECORD_SAVE_FAILURE, new ActionMessage(
					AppConstants.RECORD_SAVE_FAILURE));
		}
		if (!messages.isEmpty()) {
			this.saveMessages(request, messages);
		}

		forward = mapping.findForward(forwardMapping);

		return forward;
	}

	public ActionForward viewProjectDocReport(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {

		ActionForward forward = new ActionForward();
		RepositoryUploadModel model = new RepositoryUploadModel();
		ActionMessages messages = new ActionMessages();
		String forwardMapping = null;
		RepositoryUploadDto objDto = new RepositoryUploadDto();
		ArrayList<RepositoryUploadDto> listProjectPlan = null;
		RepositoryUploadBean formBean=(RepositoryUploadBean)actionForm;
		try 
		{
			String projID=Utility.decryptString(request.getParameter("projectID"));
			String projectName=Utility.decryptString(request.getParameter("projectName"));
			if ((!projID.equals("null")) && (!projID.equals("")))
			{
				listProjectPlan = model.fetchProjectDocReport(projID);
				formBean.setListProjectPlan(listProjectPlan);
				formBean.setProjectID(projID);
				formBean.setProjectName(projectName);
				
			}

			forwardMapping = "displayDocs";
			
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
