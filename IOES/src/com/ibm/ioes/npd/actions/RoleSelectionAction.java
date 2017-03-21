package com.ibm.ioes.npd.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import com.ibm.appsecure.util.AppSecureConstant;
import com.ibm.appsecure.util.Encryption;
import com.ibm.appsecure.util.IEncryption;
import com.ibm.ioes.npd.beans.RoleSelectionBean;
import com.ibm.ioes.npd.beans.SubMenuBean;
import com.ibm.ioes.npd.beans.ValidationBean;
import com.ibm.ioes.npd.hibernate.beans.DashboardDto;
import com.ibm.ioes.npd.hibernate.beans.MenuDto;
import com.ibm.ioes.npd.hibernate.beans.RepositoryUploadDto;
import com.ibm.ioes.npd.hibernate.beans.RoleSelectionDto;
import com.ibm.ioes.npd.hibernate.beans.TmEmployee;
import com.ibm.ioes.npd.hibernate.beans.TmRoleempmapping;
import com.ibm.ioes.npd.model.MyToDoListServicesImpl;
import com.ibm.ioes.npd.model.PlrUploadingServicesImpl;
import com.ibm.ioes.npd.model.RfiModel;
import com.ibm.ioes.npd.model.RoleSelectionModel;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.PagingSorting;
import com.ibm.ioes.npd.utilities.Utility;
//import com.ibm.ws.http.HttpRequest;


public class RoleSelectionAction extends DispatchAction
{
	public ActionForward getRoleList(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{
			ActionForward forward = new ActionForward();
			String forwardMapping = null;
			RoleSelectionModel model=new RoleSelectionModel();
			ActionMessages messages = new ActionMessages();
			RoleSelectionBean formBean = (RoleSelectionBean)form;
			RoleSelectionDto objRoleListDto= new RoleSelectionDto();
			ArrayList<RoleSelectionDto> listRole = new ArrayList<RoleSelectionDto>();
		 	TmEmployee tmEmployee = null;
		 	ArrayList roleList = null;
		 	String roleID=null;
			String roleName=null;
			TmRoleempmapping dtoRoleMap = new TmRoleempmapping();
			int i = 0;
			try 
			{
				HashMap userHashMap = (HashMap) request.getSession().getAttribute(
			 			AppConstants.NPD_USERS_SESSION_NAME);
			 	if (userHashMap != null && userHashMap.size() > 0) {
			 		Set set1 = userHashMap.keySet();
			 		Set set = new TreeSet(set1);
			 		Object[] userArray;
			 		userArray = set.toArray();
			 		for (i = 0; i < userArray.length; i++) {
			 			tmEmployee = (TmEmployee) userArray[i];
			 			roleList = (ArrayList) userHashMap.get(tmEmployee);
			 			request.getSession().setAttribute(AppConstants.LOGINBEAN, tmEmployee);
			 		}
			 	}				
			 	// TODO Changes Made By Sumit On 09-Mar-2010 For Role Selection 
			 	// Role assigned to user is 1 than Landing Page should be Menu.jsp else role selection (start)
			 	if(roleList.size()==1)
			 	{
			 		dtoRoleMap = (TmRoleempmapping) roleList.get(0);
			 		roleID = String.valueOf(dtoRoleMap.getTmRoles().getRoleid());
			 		roleName = String.valueOf(dtoRoleMap.getTmRoles().getRolename());
			 		
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
					
					pagingSorting.setSortByColumn(sortByColumn);
					pagingSorting.setSortByOrder(sortByOrder);
					
					objRoleListDto.setPagingSorting(pagingSorting);

			 		
			 		TmEmployee tmEmployee1 =(TmEmployee)request.getSession().getAttribute(AppConstants.LOGINBEAN);
					tmEmployee1.setCurrentRoleId(Long.parseLong(roleID));
					tmEmployee1.setCurrentRoleName(roleName);//loading Role Name value in Session
					//MenuList=model.getMenuDetails(objDto,roleID);//loading Role ID value in Session
						
					ArrayList<RoleSelectionDto> meuList  = (ArrayList<RoleSelectionDto>)model.getMenuDetails(objRoleListDto,roleID);
					
					HashMap<String,String> map_ModuleName=new HashMap<String, String>();
					
					for (RoleSelectionDto dto : meuList) 
					{
						String key=dto.getModules();
						map_ModuleName.put(key, key);
					}
					
				 	String userId = String.valueOf(tmEmployee.getNpdempid());
					MyToDoListServicesImpl myToDoListServicesImpl = new MyToDoListServicesImpl();
					RfiModel rfiModel = new RfiModel();
					PlrUploadingServicesImpl plrUploadingServicesImpl = new PlrUploadingServicesImpl();
					int taskPending_mytodoList = myToDoListServicesImpl
							.getMyToListCount(userId, roleID);
					int taskPending_rfi = rfiModel.getPendingRFICount(userId, roleID);
					int taskPending_plrUploading = plrUploadingServicesImpl
							.getPendingPLRUploadingCount(userId);
					DashboardDto dashboardDto = new DashboardDto();
					dashboardDto.setTaskpending_mytodoList(taskPending_mytodoList);
					dashboardDto.setTaskpending_rfi(taskPending_rfi);
					dashboardDto.setTaskpending_plrUploading(taskPending_plrUploading);
					dashboardDto.setTotal_taskPending(taskPending_mytodoList
							+ taskPending_rfi + taskPending_plrUploading);

					ArrayList<String> moduleNameList=new ArrayList<String>(map_ModuleName.keySet());
					
					//changes by rohit to introduce admin reports in reports menu
					ArrayList<RoleSelectionDto> subAdminMenuList  = (ArrayList<RoleSelectionDto>)model.getAdminMenuDetails(objRoleListDto,roleID);
					
					request.setAttribute("moduleNameList", moduleNameList);
					request.setAttribute("subMenuList", (ArrayList)meuList);
					request.setAttribute("dashboardDto", dashboardDto);
					//changes by rohit to introduce admin reports in reports menu
					request.setAttribute("subAdminMenuList", subAdminMenuList);
					forwardMapping = "ShowHomePage";  
			 	}
			 	else
			 	{
			 		
			 		
			 		
				 	TmEmployee tmployee = (TmEmployee) request.getSession().getAttribute(AppConstants.LOGINBEAN);
					String userid = String.valueOf(tmployee.getNpdempid());
					formBean.setRoleList(model.viewRoleList(userid));
					forwardMapping = "success";
			 	}
			 	// Role assigned to user is 1 than Landing Page should be Menu.jsp else role selection (End)
			} 
			catch (Exception e) 
			{
				forwardMapping = "SessionExpired";
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

	public ActionForward goToMenu(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		ActionForward forward = new ActionForward();
		RoleSelectionBean formBean=(RoleSelectionBean) form;
		RoleSelectionModel model = new RoleSelectionModel();
		ActionMessages messages = new ActionMessages();
		String forwardMapping = null;
		ArrayList<RoleSelectionDto> MenuList = new ArrayList<RoleSelectionDto>();
		RoleSelectionDto objDto = new RoleSelectionDto();
		boolean errorsFound=false;
		try 
		{
			HashMap userHashMap = (HashMap) request.getSession().getAttribute(
		 			AppConstants.NPD_USERS_SESSION_NAME);
			if(userHashMap==null)
			{
				forwardMapping = "SessionExpired";
			}
			else
			{
				String roleID=Utility.decryptString(request.getParameter("RoleID"));
				String roleName=Utility.decryptString(request.getParameter("RoleName"));
				
				int noOfRecords=0;
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(roleID,"Role ID"),
							Utility.CASE_DIGITS_ONLY+"").getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;
					}
				}
				if(errorsFound)
				{
					saveMessages(request, messages);
					TmEmployee tmployee = (TmEmployee) request.getSession().getAttribute(AppConstants.LOGINBEAN);
					String userid = String.valueOf(tmployee.getNpdempid());
					formBean.setRoleList(model.viewRoleList(userid));
					return mapping.findForward("success");
				}
				
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
				
				pagingSorting.setSortByColumn(sortByColumn);
				pagingSorting.setSortByOrder(sortByOrder);
				
				objDto.setPagingSorting(pagingSorting);
				
				TmEmployee tmEmployee=(TmEmployee)request.getSession().getAttribute(AppConstants.LOGINBEAN);
				tmEmployee.setCurrentRoleId(Long.parseLong(roleID));
				tmEmployee.setCurrentRoleName(roleName);//loading Role Name value in Session
				//MenuList=model.getMenuDetails(objDto,roleID);//loading Role ID value in Session
							
				ArrayList<RoleSelectionDto> meuList  = (ArrayList<RoleSelectionDto>)model.getMenuDetails(objDto,roleID);
				
				HashMap<String,String> map_ModuleName=new HashMap<String, String>();
				
				for (RoleSelectionDto dto : meuList) 
				{
					String key=dto.getModules();
					map_ModuleName.put(key, key);
				}
				ArrayList<String> moduleNameList=new ArrayList<String>(map_ModuleName.keySet());
				
				//changes by rohit to introduce admin reports in reports menu
				ArrayList<RoleSelectionDto> subAdminMenuList  = (ArrayList<RoleSelectionDto>)model.getAdminMenuDetails(objDto,roleID);
				request.setAttribute("subAdminMenuList", subAdminMenuList);
				
				request.setAttribute("moduleNameList", moduleNameList);
				request.setAttribute("subMenuList", (ArrayList)meuList);
				
				String userId = String.valueOf(tmEmployee.getNpdempid());
				MyToDoListServicesImpl myToDoListServicesImpl = new MyToDoListServicesImpl();
				RfiModel rfiModel = new RfiModel();
				PlrUploadingServicesImpl plrUploadingServicesImpl = new PlrUploadingServicesImpl();
				int taskPending_mytodoList = myToDoListServicesImpl
						.getMyToListCount(userId, roleID);
				int taskPending_rfi = rfiModel.getPendingRFICount(userId, roleID);
				int taskPending_plrUploading = plrUploadingServicesImpl
				.getPendingPLRUploadingCount(userId);
	
				DashboardDto dashboardDto = new DashboardDto();
				dashboardDto.setTaskpending_mytodoList(taskPending_mytodoList);
				dashboardDto.setTaskpending_rfi(taskPending_rfi);
				dashboardDto.setTaskpending_plrUploading(taskPending_plrUploading);
				dashboardDto.setTotal_taskPending(taskPending_mytodoList
						+ taskPending_rfi + taskPending_plrUploading);
	
				request.setAttribute("dashboardDto", dashboardDto);
				
				forwardMapping = "ShowHomePage";
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
	
}
