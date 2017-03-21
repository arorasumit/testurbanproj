package com.ibm.ioes.npd.actions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.ibm.appsecure.exception.ValidationException;
import com.ibm.ioes.npd.beans.LoginBean;
import com.ibm.ioes.npd.hibernate.beans.SessionObjectsDto;
import com.ibm.ioes.npd.hibernate.dao.CommonBaseDao;
import com.ibm.ioes.npd.model.NpdUserModel;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;

/**
 * Action Class for managing Login functionality.
 * 
 * @author Disha
 * @version 1.0
 */
public class LoginActionNPD extends CommonBaseAction {

	private static final Logger NPD_LOGIN;

	static {
		NPD_LOGIN = Logger.getLogger(LoginActionNPD.class);
	}

	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("submit.login", "login");
		map.put("submit.chgPassword", "chgPassword");
		return map;
	}

	/**
	 * Automatically generated constructor: LoginAction
	 */
	public LoginActionNPD() {
	}

	/**
	 * Manage Login Functionality
	 * 
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return Describes where and how control should be forwarded.
	 * @throws java.lang.Exception
	 */

	public ActionForward login(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages messages = new ActionMessages();
		ActionForward forward = new ActionForward(); // return value
		NpdUserModel npdUserModel = new NpdUserModel();
		ArrayList roleListOfAnEmployee = null;
		HashMap userHashMap = new HashMap();
		LoginBean loginBean = (LoginBean) form;
		SimpleDateFormat sdf = null;
		String sUserId = null;
		String ssfUserId = null;
		try 
		{
			//cOMMENTED FOR tESTING
			///////ssfUserId=loginBean.getSsfUserID();
			ssfUserId=(String)request.getSession().getAttribute(AppConstants.SSFID);
			//GSDService gsds = new GSDService();
			//ssfUserId = loginBean.getLoginId();
			
			 //Makking UserId case insensitive
			if (ssfUserId != null && ssfUserId.length() > 0) 
			{
				ssfUserId = ssfUserId.trim();   
			}
			//String pass = AppUtility.decryptPassword(loginBean.getPassword());
			//loginBean = (LoginBean) gsds.validateCredentials(ssfUserId, pass,"com.ibm.ioes.npd.beans.LoginBean");
			
			CommonBaseDao commonBaseDao = new CommonBaseDao();
			int attachmentSize = commonBaseDao.getAttachemntSize();
			int pageSize = commonBaseDao.getPageSize();
			String specialCharacter = commonBaseDao.getSpecialChar();
			SessionObjectsDto sessionObjectsDto = SessionObjectsDto.getInstance();
			sessionObjectsDto.setAttachmentSize(attachmentSize);
			sessionObjectsDto.setPageSize(pageSize);
			sessionObjectsDto.setSpecialCharacter(specialCharacter);
			sessionObjectsDto.setGanttChartDirPath(getServlet().getServletContext().getRealPath("/Data"));

			ServletContext sc = request.getSession().getServletContext();
			HttpSession session = (HttpSession) sc.getAttribute(ssfUserId);
			

			if (session != null) {
				sc.removeAttribute(ssfUserId);
				try {
					session.invalidate();
				} catch (Exception e1) {    
					;
				}
			}
			
			userHashMap = npdUserModel
					.getEmployeeDetailsBasedOnEmailID(ssfUserId);
			if (userHashMap != null && userHashMap.size() > 0) {

				//loginBean.setRoleList(roleListOfAnEmployee);
				sdf = new SimpleDateFormat(AppConstants.DATE_TIME_FORMAT);
				NPD_LOGIN.error("User Id , " + ssfUserId + " , Logged In At , "
						+ sdf.format(new Date()));

				//request.getSession().invalidate();
				
				sc.setAttribute(sUserId, request.getSession(true));
				session = (HttpSession) sc.getAttribute(sUserId);

				session.setAttribute(AppConstants.NPD_USERS_SESSION_NAME,
						userHashMap);
				
				session.setAttribute(AppConstants.APPCONTEXTPATH, request
						.getContextPath());
				session.setAttribute("sessionId", session.getId());
				session.setAttribute("ipAddress", request.getRemoteAddr());
				forward = mapping.findForward("success1");

			} 
			else 
			{
				throw new Exception("logonfailure");
			}

		} 
		catch (ValidationException e) 
		{
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			sdf = new SimpleDateFormat(AppConstants.DATE_TIME_FORMAT);
			NPD_LOGIN.error("User Id , " + ssfUserId + " , Login Failed At , "+ sdf.format(new Date()));
			if ("msg.security.id02".equals(e.getMessageId())) {
				request.getSession().setAttribute("PASSWORD_EXPIRED", "true");
				messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
				forward = mapping.findForward(AppConstants.FAILURE);
			} 
			else if ("msg.security.id014".equals(e.getMessageId())) 
			{
				messages.add("msg.security.id014", new ActionMessage("msg.security.id014"));
				forward = mapping.findForward(AppConstants.FAILURE);
			}
//			 For everything show - User Login Failed
			else 
			{
				messages.add("logonfailure", new ActionMessage("msg.security.id013"));
				forward = mapping.findForward(AppConstants.FAILURE);
			}
			loginBean.setPassword(null);
			forward = mapping.findForward(AppConstants.FAILURE);
		} 
		catch (Exception e) 
		{
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add("logonfailure",new ActionMessage("msg.security.id013"));
			sdf = new SimpleDateFormat(AppConstants.DATE_TIME_FORMAT);
			NPD_LOGIN.error("User Id , " + ssfUserId + " , Login Failed At , "+ sdf.format(new Date()));
			loginBean.setPassword(null);
			forward = mapping.findForward(AppConstants.FAILURE);
		}
		if (!messages.isEmpty()) 
		{
			this.saveMessages(request, messages);
		}
		return forward;
	}
}