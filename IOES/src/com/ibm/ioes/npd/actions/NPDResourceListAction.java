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

import com.ibm.ioes.npd.beans.FunnelReportbean;
import com.ibm.ioes.npd.beans.NPDResourceListBean;
import com.ibm.ioes.npd.beans.RisksCaptureBean;
import com.ibm.ioes.npd.beans.ValidationBean;
import com.ibm.ioes.npd.hibernate.beans.FunnelReportDto;
import com.ibm.ioes.npd.hibernate.beans.NPDResourceListDto;
import com.ibm.ioes.npd.model.FunnelReportModel;
import com.ibm.ioes.npd.model.NPDResourceListModel;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.PagingSorting;
import com.ibm.ioes.npd.utilities.Utility;

public class NPDResourceListAction extends DispatchAction
{
	public ActionForward initNPDResourceList(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
   {
		ActionForward forward = new ActionForward();
		String forwardMapping = null;
		NPDResourceListModel model=new NPDResourceListModel();
		ActionMessages messages = new ActionMessages();
		NPDResourceListBean formBean = (NPDResourceListBean)form;
		NPDResourceListDto objNPDResource= new NPDResourceListDto();
		ArrayList<NPDResourceListDto> listNPDResource = new ArrayList<NPDResourceListDto>();
		boolean errorsFound=false;
		try 
		{
			//Searching
			String searchProjectName=formBean.getProjectName();
			String projID=formBean.getSearchProjectID();
			String searchRoleName=formBean.getRoleName();
			String searchEmpName=formBean.getEmpName();
			int searchProjStatus=formBean.getSearchProjectStatus();
			
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
			
			//Server Side Security Start for Project ID
			if((searchRoleName!=null) && (!searchRoleName.equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(searchRoleName,"Role Name",20),
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
			if((searchEmpName!=null) && (!searchEmpName.equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(searchEmpName,"Employee Name",20),
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
					ArrayList errors=Utility.validateValue(new ValidationBean(String.valueOf(searchProjStatus),"Employee Name",20),
							""+Utility.CASE_SPECIALCHARACTERS).getCompleteMessageStrings();
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
				forward = mapping.findForward(AppConstants.FAILURE);
				return forward;
			}
			else
			{
				objNPDResource.setProjectName(searchProjectName);
				int searchProjectID;
				if (projID==null || projID.equalsIgnoreCase(""))
				{
					 searchProjectID=0;
				}
				else
				{
					searchProjectID=Integer.parseInt(formBean.getSearchProjectID());
				}
				objNPDResource.setProjectID(searchProjectID);
				objNPDResource.setRoleName(searchRoleName);			
				objNPDResource.setEmpName(searchEmpName);
				objNPDResource.setProjectStatus(searchProjStatus);
				
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
				
				objNPDResource.setPagingSorting(pagingSorting);
		
				listNPDResource = model.viewNPDResourceReport(objNPDResource);
				request.setAttribute("listNPDResource", listNPDResource);
				if (listNPDResource.size()!=0)
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
		NPDResourceListModel model=new NPDResourceListModel();
		ActionMessages messages = new ActionMessages();
		NPDResourceListBean formBean = (NPDResourceListBean)form;
		NPDResourceListDto objNPDResource= new NPDResourceListDto();
		ArrayList<NPDResourceListDto> listNPDResourceExport = new ArrayList<NPDResourceListDto>();
		boolean errorsFound=false;
		try 
		{
			//Searching
			String searchProjectName=formBean.getProjectName();
			String searchEmpName=formBean.getEmpName();
			String projID=formBean.getSearchProjectID();
			String searchRoleName=formBean.getRoleName();
			int searchProjStatus=formBean.getSearchProjectStatus();
			
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
			
			//Server Side Security Start for Project ID
			if((searchRoleName!=null) && (!searchRoleName.equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(searchRoleName,"Role Name",20),
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
			if((searchEmpName!=null) && (!searchEmpName.equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(searchEmpName,"Employee Name",20),
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
					ArrayList errors=Utility.validateValue(new ValidationBean(String.valueOf(searchProjStatus),"Employee Name",20),
							""+Utility.CASE_SPECIALCHARACTERS).getCompleteMessageStrings();
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
				forward = mapping.findForward(AppConstants.FAILURE);
				return forward;
			}
			else
			{
			
				objNPDResource.setProjectName(searchProjectName);
				int searchProjectID;
				if (projID==null || projID.equalsIgnoreCase(""))
				{
					 searchProjectID=0;
				}
				else
				{
					searchProjectID=Integer.parseInt(formBean.getSearchProjectID());
				}
				objNPDResource.setProjectID(searchProjectID);
				objNPDResource.setRoleName(searchRoleName);
				objNPDResource.setEmpName(searchEmpName);
				objNPDResource.setProjectStatus(searchProjStatus);
				
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
				
				objNPDResource.setPagingSorting(pagingSorting);
				
				listNPDResourceExport = model.viewNPDResourceReportExport(objNPDResource);
				request.setAttribute("listNPDResource", listNPDResourceExport);
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
