package com.ibm.ioes.npd.actions;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import com.ibm.ioes.npd.beans.AccessMatrixBean;
import com.ibm.ioes.npd.beans.ValidationBean;
import com.ibm.ioes.npd.hibernate.beans.AccessMatrixDto;
import com.ibm.ioes.npd.hibernate.dao.AccessMatrixDao;
import com.ibm.ioes.npd.model.AccessMatrixModel;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.Utility;

public class AccessMatrixAction extends DispatchAction
{
	private static final Logger logger;
	static 
	{
		logger = Logger.getLogger(AccessMatrixAction.class);
	}
	
	public ActionForward DisplayRoles(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		ActionForward forward = new ActionForward();
		String forwardMapping = null;
		ActionMessages messages = new ActionMessages();
		AccessMatrixModel model=new AccessMatrixModel();
		AccessMatrixBean formBean = (AccessMatrixBean)form; 
		AccessMatrixDto objDto = new AccessMatrixDto();
		ArrayList<AccessMatrixDto> listRoles = new ArrayList<AccessMatrixDto>();
		try 
		{
			listRoles = model.getRoleList(objDto);
			request.setAttribute("listRoles", listRoles);
			//request.setAttribute("npdCate", model.getNPDList(objDto)); 
			formBean.setListRole(listRoles);
			
			formBean.setModuleList(model.viewModuleList());
			forwardMapping = "DisplayRoles";
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

	public ActionForward getInterfaceList(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		ActionForward forward = new ActionForward();
		String forwardMapping = null;
		ActionMessages messages = new ActionMessages();
		AccessMatrixModel model=new AccessMatrixModel();
		AccessMatrixBean formBean = (AccessMatrixBean)form; 
		AccessMatrixDto objDto = new AccessMatrixDto();
		ArrayList<AccessMatrixDto> listInterfaceDetails = new ArrayList<AccessMatrixDto>();
		ArrayList<AccessMatrixDto> listRoles = new ArrayList<AccessMatrixDto>();
		boolean errorsFound=false;
		try 
		{
			String roleID=formBean.getSelectedRoleID();
			String moduleID=formBean.getSelectedModuleID();
			
			//Server Side Security Start for Role ID
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(roleID,"Role ID",20),
						""+Utility.CASE_DIGITS_ONLY+","+Utility.CASE_MANDATORY).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;	
				}
			}
			//Server Side Security End for Role ID
			
			//Server Side Security Start for Module ID
			if(!errorsFound)
			{
				if(!moduleID.equalsIgnoreCase("-1"))
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(moduleID,"Module ID",20),
							""+Utility.CASE_DIGITS_ONLY+","+Utility.CASE_MANDATORY).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;	
					}
				}
			}
			//Server Side Security End for Module ID
			
			if(errorsFound)
			{
				saveMessages(request, messages);
				listRoles = model.getRoleList(objDto);
				request.setAttribute("listRoles", listRoles);
				formBean.setListRole(listRoles);
				formBean.setModuleList(model.viewModuleList());
				forward = mapping.findForward("DisplayRoles");
				return forward;
			}
			else
			{
				if(!roleID.equalsIgnoreCase("-1"))
				{
					listInterfaceDetails = model.getInterfaceList(roleID,moduleID);
					request.setAttribute("listInterfaceDetails", listInterfaceDetails);
					formBean.setListInterface(listInterfaceDetails);
					formBean.setModuleList(model.viewModuleList());
				}
				listRoles = model.getRoleList(objDto);
				formBean.setListRole(listRoles);
				forwardMapping = "InterfaceList";
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

	public ActionForward UpdateMatrix(ActionMapping mapping, ActionForm form, HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		ActionForward forward = new ActionForward();
		String forwardMapping = null;
		ActionMessages messages = new ActionMessages();
		AccessMatrixModel model=new AccessMatrixModel();
		AccessMatrixBean formBean = (AccessMatrixBean)form;
		boolean errorsFound=false;
		ArrayList<AccessMatrixDto> listRoles = new ArrayList<AccessMatrixDto>();
		AccessMatrixDto objDto = new AccessMatrixDto();
		try
		{
			ArrayList<String> interfaceAddedIdList = new ArrayList<String>();
			ArrayList<String> interfaceRemovedIdList = new ArrayList<String>();
			
			String roleID=formBean.getSelectedRoleID();
			
			//Server Side Security Start for Role ID
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(roleID,"Role ID",20),
						""+Utility.CASE_DIGITS_ONLY+","+Utility.CASE_MANDATORY).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;	
				}
			}
			
			if(errorsFound)
			{
				saveMessages(request, messages);
				listRoles = model.getRoleList(objDto);
				request.setAttribute("listRoles", listRoles);
				formBean.setListRole(listRoles);
				formBean.setModuleList(model.viewModuleList());
				forward = mapping.findForward("DisplayRoles");
				return forward;
			}
			else
			{
				String allowedIdStr = formBean.getAllowedInterfaceId();
				String prohibitedIdStr = formBean.getProhibitedInterfaceId();
				
				String[] allowedIdArr = allowedIdStr.split("&");
				String[] prohibitedIdArr = prohibitedIdStr.split("&");
				
				for(int i=1;i<allowedIdArr.length;i++)
				{
					interfaceAddedIdList.add(allowedIdArr[i]);
				}
				for(int i=1;i<prohibitedIdArr.length;i++)
				{
					if(interfaceAddedIdList.contains(prohibitedIdArr[i]))
					{
						interfaceAddedIdList.remove(prohibitedIdArr[i]);
					}
					else
					{
						interfaceRemovedIdList.add(prohibitedIdArr[i]);
					}	
				}
				
				if(interfaceAddedIdList.size()==0&&interfaceRemovedIdList.size()==0)
				{	
					logger.info("no changes to interface access for selected Role.");
				}
				else
				{
					int retCode = model.setUserMappedInterfaces(interfaceAddedIdList,interfaceRemovedIdList,roleID);
					if(retCode==1)
					{
						//formBean.setMessage(bundle.getString("matrix.updated"));
						logger.info("Access matrix changes updated for selected Role.");
					}
					else if(retCode==0)
					{
						//formBean.setErrorMessage(bundle.getString("matrix.failed"));
						logger.info("Access matrix changes could not be updated for selected Role.");
					}
				}
				getInterfaceList(mapping,formBean,request,response);
				

				forwardMapping = "DisplayRoles";
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
