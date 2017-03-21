/*
 * Created on Nov 13, 2006
 *
 */
package com.ibm.ioes.npd.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.ibm.ioes.npd.utilities.AppConstants;

/**
 * Class for processing the index page of application.
 * 
 * @author Shantnu Jain
 */
public class IndexAction extends Action {

	/**
	 * Process Index Action and remove old session
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


		public ActionForward execute(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {

			ActionForward forward = new ActionForward();
			ActionErrors errors = new ActionErrors();
			HttpSession session = request.getSession();
			if (session.getAttribute(Globals.ERROR_KEY) != null) {
				errors.add("name", (ActionMessage) ((ActionMessages) session
						.getAttribute(Globals.ERROR_KEY)).get().next());
			}

			if (!session.isNew()) {
				session.invalidate();
				session = request.getSession();
			}
			session.setAttribute(AppConstants.APPCONTEXTPATH, request
					.getContextPath());
			if (!errors.isEmpty()) {
				this.saveErrors(request, errors);
			}
			forward = mapping.findForward(AppConstants.SUCCESS);
			return forward;
		}
	}
