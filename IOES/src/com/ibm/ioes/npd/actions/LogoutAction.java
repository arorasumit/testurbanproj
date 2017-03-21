package com.ibm.ioes.npd.actions;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;

/**
 * This class manages the logout functionality.
 * 
 * @author Shantnu Jain
 * @version 1.0
 */
public class LogoutAction extends CommonBaseAction {

	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("submit.logout", "logout");
		return map;
	}

	/**
	 * Automatically generated constructor: LogoutAction
	 */
	public LogoutAction() {
	}

	/**
	 * Manage Logout Functionality
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

	public ActionForward logout(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionForward forward = new ActionForward(); // return value
		ActionErrors errors = new ActionErrors();

		try {
			request.getSession().removeAttribute(
					AppConstants.NPD_USERS_SESSION_NAME);
			request.getSession().invalidate();

		} catch (Exception e) {

			// Report the error using the appropSriate name and ID.
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			errors.add("name", new ActionMessage("error"));
		}
		if (!errors.isEmpty()) {
			this.saveErrors(request, errors);
		}
		forward = mapping.findForward(AppConstants.SUCCESS);

		return forward;

	}
}