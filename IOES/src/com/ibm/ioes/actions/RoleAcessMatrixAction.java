package com.ibm.ioes.actions;

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

import com.ibm.ioes.beans.RoleAcessMatrixBean;
import com.ibm.ioes.beans.ValidationBean;
import com.ibm.ioes.forms.RoleAcessMatrixDto;
import com.ibm.ioes.model.RoleAcessMatrixModel;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.AppUtility;
import com.ibm.ioes.utilities.Utility;

public class RoleAcessMatrixAction extends DispatchAction{
	
		private static final Logger logger;
		static 
		{
			logger = Logger.getLogger(RoleAcessMatrixAction.class);
		}
		/**
		 * method to retreive roles and modules data from DB and set on form.
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward getRoles(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
		{
			ActionForward forward = new ActionForward();
			String forwardMapping = null;
			ActionMessages messages = new ActionMessages();
			RoleAcessMatrixModel model=new RoleAcessMatrixModel();
			RoleAcessMatrixBean formBean = (RoleAcessMatrixBean)form; 
			ArrayList<RoleAcessMatrixDto> listRoles = new ArrayList<RoleAcessMatrixDto>();
			try 
			{
				listRoles = model.getRoleList();
				request.setAttribute("listRoles", listRoles);
				formBean.setListRoles(listRoles);
				
				formBean.setModuleList(model.getModuleList());
				forwardMapping = "DisplayRoleAcessPage";
			} 
			catch (Exception e) 
			{
				AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
				messages.add("", new ActionMessage("RoleRetreivalFailure"));
			}
			if (!messages.isEmpty()) 
			{
				saveErrors(request, messages);
			}
			forward = mapping.findForward(forwardMapping);
			
			return forward;
		}
		/**
		 * Method to retrive data of interface on basis of role and module selected.
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward getInterfaceList(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
		{
			ActionForward forward = new ActionForward();
			String forwardMapping = null;
			ActionMessages messages = new ActionMessages();
			RoleAcessMatrixModel model=new RoleAcessMatrixModel();
			RoleAcessMatrixBean formBean = (RoleAcessMatrixBean)form; 
			ArrayList<RoleAcessMatrixDto> listInterfaceDetails = new ArrayList<RoleAcessMatrixDto>();
			ArrayList<RoleAcessMatrixDto> listRoles = new ArrayList<RoleAcessMatrixDto>();
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
					listRoles = model.getRoleList();
					request.setAttribute("listRoles", listRoles);
					formBean.setListRoles(listRoles);
					formBean.setModuleList(model.getModuleList());
					forward = mapping.findForward("DisplayRoleAcessPage");
					return forward;
				}
				else
				{
					if(!roleID.equalsIgnoreCase("-1"))
					{
						listInterfaceDetails = model.getInterfaceList(roleID,moduleID);
						request.setAttribute("listInterfaceDetails", listInterfaceDetails);
						formBean.setListInterface(listInterfaceDetails);
						formBean.setModuleList(model.getModuleList());
					}
					listRoles = model.getRoleList();
					formBean.setListRoles(listRoles);
					forwardMapping = "DisplayRoleAcessPage";
				}
			} 
			catch (Exception e) 
			{
				AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
				messages.add("", new ActionMessage("RoleRetreivalFailure"));
				//messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
			}
			if (!messages.isEmpty()) 
			{
				saveErrors(request, messages);
			}
			forward = mapping.findForward(forwardMapping);
			
			return forward;
		}

		/**
		 * method to update the user modified data.
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward UpdateMatrix(ActionMapping mapping, ActionForm form, HttpServletRequest request,HttpServletResponse response)throws Exception
		{
			ActionForward forward = new ActionForward();
			String forwardMapping = null;
			ActionMessages messages = new ActionMessages();
			RoleAcessMatrixModel model=new RoleAcessMatrixModel();
			RoleAcessMatrixBean formBean = (RoleAcessMatrixBean)form;
			boolean errorsFound=false;
			ArrayList<RoleAcessMatrixDto> listRoles = new ArrayList<RoleAcessMatrixDto>();
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
					listRoles = model.getRoleList();
					request.setAttribute("listRoles", listRoles);
					formBean.setListRoles(listRoles);
					formBean.setModuleList(model.getModuleList());
					forward = mapping.findForward("DisplayRoleAcessPage");
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
							interfaceRemovedIdList.add(prohibitedIdArr[i]);
						}
						else
						{
							interfaceRemovedIdList.add(prohibitedIdArr[i]);
						}	
					}
					
					/*if(interfaceAddedIdList.size()==0&&interfaceRemovedIdList.size()==0)
					{	
						logger.info("no changes to interface access for selected Role.");
					}
					else
					{*/
						int retCode = model.setUserMappedInterfaces(interfaceAddedIdList,interfaceRemovedIdList,roleID);
						if(retCode==1)
						{
							//formBean.setMessage(bundle.getString("matrix.updated"));
							messages.add("", new ActionMessage("recordUpdateSuccess"));
							
							//
							saveMessages(request , messages);
							logger.info("Access matrix changes updated for selected Role.");
						}
						else if(retCode==0)
						{
							//formBean.setErrorMessage(bundle.getString("matrix.failed"));
							messages.add("", new ActionMessage("recordFailureSuccess"));
							
							//
							saveMessages(request , messages);
							logger.info("Access matrix changes could not be updated for selected Role.");
						}
					//}
					getInterfaceList(mapping,formBean,request,response);
					

					forwardMapping = "DisplayRoleAcessPage";
				}
			}
			catch (Exception e) 
			{
				AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
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
