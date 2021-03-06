/*
 * Created on Apr 10, 2008
 *
 */
package com.ibm.ioes.utilities;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.InvalidCancelException;
import org.apache.struts.action.RequestProcessor;


import com.ibm.ioes.beans.UserInfoDto;
import com.ibm.ioes.exception.IOESException;
import com.ibm.ioes.npd.hibernate.beans.TmEmployee;
import com.ibm.ioes.npd.utilities.CommonUtilities;

/**
 * Request Preprocessor Class for NWI_Tracker
 * 
 * @author Shantnu Jain
 */
public class IOES_RequestProcessor extends RequestProcessor {
	private static final Logger logger;

	static 
	{
		logger = com.ibm.ioes.utilities.AppConstants.IOES_LOGGER;
	}

	/**
	 * Function to validate that request does not contain illegal characters
	 * 
	 * @param request
	 * @return boolean indicating whether request has special characters or not.
	 */
	public static boolean validateRequestHasSpecialChar(
			final HttpServletRequest request) {

		Object obj = null;
		Object tempObj = null;
		int numCons = 0;
		String specialChar = Messages.getMessageValue("SpecialCharacters");
		char c1[] = specialChar.toCharArray();
		for (Enumeration e = request.getParameterNames(); e.hasMoreElements();) {
			tempObj = e.nextElement();
			obj = request.getParameter(tempObj.toString());
			char c[] = obj.toString().toCharArray();
			numCons = 0;
			for (int i = 0; i < c.length; i++) {
				for (int j = 0; j < c1.length; j++) {
					if (c[i] == c1[j]) {
						numCons++;
						break;
					}
				}
			}
			/*if (obj.toString().indexOf("\\") != -1) {
				numCons++;
			}
			if (numCons > 0) {
				return true;
			}*/
		}
		obj = request.getRequestURI();
		char c[] = obj.toString().toCharArray();
		numCons = 0;
		for (int i = 0; i < c.length; i++) {
			for (int j = 0; j < c1.length; j++) {
				if (c[i] == c1[j]) {
					numCons++;
					break;
				}
			}
		}
		if (numCons > 0) {
			return true;
		}

		return false;

	}

	/**
	 * This method will check if user try to insert the java script in the
	 * request parameters
	 * 
	 * @param request
	 * @return
	 * @author Disha
	 * @version initial 25/03/2009
	 * 
	 */
	public static boolean validateRequestforScript(
			final HttpServletRequest request) {
		Pattern pattern1 = Pattern
				.compile("<script>", Pattern.CASE_INSENSITIVE);
		Pattern pattern2 = Pattern.compile("</script>",
				Pattern.CASE_INSENSITIVE);
		boolean flag = false;
		Object obj = null;
		Object tempObj = null;

		for (Enumeration e = request.getParameterNames(); e.hasMoreElements();) {
			tempObj = e.nextElement();
			obj = request.getParameter(tempObj.toString());

			if (obj != null) {
				Matcher matcher = pattern1.matcher(obj.toString());
				Matcher matcher1 = pattern2.matcher(obj.toString());
				while (matcher.find()) {
					flag = true;

				}
				while (matcher1.find()) {
					flag = true;

				}
			}
		}
		return flag;
	}

	protected boolean processValidate(HttpServletRequest request,
			HttpServletResponse response, ActionForm form, ActionMapping mapping)
			throws IOException, ServletException, InvalidCancelException {
		super.processValidate(request, response, form, mapping);
		String requestedURI = request.getRequestURI();
		HttpSession session = request.getSession();
		boolean flag = false;
		String requestMapping = requestedURI.substring(requestedURI
				.lastIndexOf("/") + 1);
		String requestedMethodNameNew=request.getParameter("methodName");

		if (!("defaultAction.do".equals(requestMapping)))
				{
			if (validateRequestHasSpecialChar(request)) {
				try {
					session.setAttribute("MenuContextPath", request
							.getContextPath());
					request.getRequestDispatcher("ErrorPage.jsp").forward(request,
							response);
				} catch (IOException e) {
					com.ibm.ioes.utilities.AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
				} catch (ServletException e) {
					com.ibm.ioes.utilities.AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
				}
				flag = false;
				return flag;

			}
		}
		
		if (validateRequestforScript(request)) {
			try {
				session.setAttribute("MenuContextPath", request
						.getContextPath());
				request.getRequestDispatcher("ErrorPage.jsp").forward(request,
						response);
			} catch (IOException e) {
				com.ibm.ioes.utilities.AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			} catch (ServletException e) {
				com.ibm.ioes.utilities.AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			}
			flag = false;
			return flag;

		}
		
		try {
			
			if (!("defaultAction.do".equals(requestMapping))
					&& !("LoginActionNPD.do".equals(requestMapping))
					&& !("LogoutAction.do".equals(requestMapping))
					&& !("InvalidSession.do".equals(requestMapping))
					&& !("Welcome.do".equals(requestMapping))
					&& !("changePwd.do".equals(requestMapping))
					&& !("ResetPasswordAction.do".equals(requestMapping))
					&& !("RoleSelection.do".equals(requestMapping))
					&& !("WelcomeOption.do".equals(requestMapping))
					&& !("HaveAnIdea.do".equals(requestMapping))
					&& !("ApplicationPerformanceLog.do".equals(requestMapping))
					&& !("QueryBuilder.do".equals(requestMapping))
					&& !("masterPlanVersion.do".equals(requestMapping))
					) 
			{
				
					if (session.getAttribute(com.ibm.ioes.utilities.AppConstants.APP_SESSION_USER_INFO) != null) 
					{
			 			//HashMap userHashMap = (HashMap) request.getSession().getAttribute(AppConstants.APP_SESSION);
						
						HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(com.ibm.ioes.utilities.AppConstants.APP_SESSION); 
						HttpSession sessionObj = (HttpSession) userHashMap.get(session.getId());
						UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(com.ibm.ioes.utilities.AppConstants.APP_SESSION_USER_INFO);
						if("Y".equalsIgnoreCase(objUserDto.getReportParameter())){
							/* We are skipping "Utility.isAuthorised(request,objUserDto)" method 
							 * because we don't have roleId of user who is accessing the report
							 */
								flag=true;
						}else if(objUserDto.getUserId()!=null){
							if(Utility.isAuthorised(request,objUserDto)==false)
								//throw new Exception();
								flag=true;
			 		 	}else{
							throw new Exception();
						}
						
						flag = true;
						if("universalWorkQueueAction.do".equals(requestMapping) && "accessMatrixiB2B".equals(requestedMethodNameNew)){
								 if(objUserDto.getUserId()!=null){
									 if(Utility.isAssignedToAccessMatrixRole(requestMapping,requestedMethodNameNew,objUserDto)==true)
											flag=true;
									 else{
										    flag=false;
									 }
					 		 	}else{
					 		 		flag=false;
									throw new Exception();
								}
						}
						else if("universalWorkQueueAction.do".equals(requestMapping) && "channelPartnerMasteriB2B".equals(requestedMethodNameNew)){
							 if(objUserDto.getUserId()!=null){
								 if(Utility.isRequestAuthorisedforRoleorResp(requestMapping,requestedMethodNameNew,objUserDto)==true)
										flag=true;
								 else{
									    flag=false;
								 }
				 		 	}else{
				 		 		flag=false;
								throw new Exception();
							}
					    }
						
					} 
					else if (session.getAttribute(AppConstants.NPD_USERS_SESSION_NAME) != null) 
					{
			 			HashMap userHashMap = (HashMap) request.getSession().getAttribute(
			 		 			AppConstants.NPD_USERS_SESSION_NAME);
			 		 	TmEmployee tmEmployee = null;
			 		 	ArrayList roleList = null;
			 		 	if (userHashMap != null && userHashMap.size() > 0) {
			 		 		Set set1 = userHashMap.keySet();
			 		 		Set set = new TreeSet(set1);
			 		 		Object[] userArray;
			 		 		userArray = set.toArray();
			 		 		for (int i = 0; i < userArray.length; i++) {
			 		 			tmEmployee = (TmEmployee) userArray[i];
			 		 			//roleList = (ArrayList) userHashMap.get(tmEmployee);
			 		 			//session.setAttribute(AppConstants.LOGINBEAN, tmEmployee);
			 		 		}
			 		 	}

			 		 	if(tmEmployee.getCurrentRoleId()!=0)
			 		 	{
						if(CommonUtilities.isAuthorised(request,String.valueOf(tmEmployee.getCurrentRoleId()),tmEmployee)==false)
							throw new Exception();
			 		 	}
			 		 	flag = true;
			 		 	
					} 
					else 
					{
						flag = false;
					}
					//Object ipAddress = session.getAttribute("ipAddress");
					// if ((flag == true)
					// && ((sessionId == null) || !(session.getId()
					// .equals(sessionId)))) {
					// flag = false;
					// }
					//if ((flag == true)
					//		&& ((ipAddress == null) || !(request.getRemoteAddr()
					//				.equals(ipAddress)))) {
					//	flag = false;
					//}
					// sessionId = request.getParameter("sessionId");
					// if (flag == true && !session.getId().equals(sessionId)) {
					// flag = false;
					// }
			

				
			} else
			{
				flag = true;
			}
			if (!flag) 
			{
				doForward("/InvalidSession.do", request, response);
				//session.removeAttribute(AppConstants.APP_SESSION_USER_INFO);
				//doForward("/InvalidSession.do", request, response);
			}
		} catch (Exception e) {
			//session.removeAttribute(AppConstants.APP_SESSION_USER_INFO);
			//doForward("/InvalidSession.do", request, response);
			//AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			//flag = false;
		}
		//session.setAttribute("MenuContextPath", request.getContextPath());
		//return flag;
		return flag;
	}
	
}