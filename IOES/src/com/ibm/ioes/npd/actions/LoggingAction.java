/**
 * @author Shantnu Jain
 * Created on May 9, 2007
 * 
 */
package com.ibm.ioes.npd.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.actions.DispatchAction;

import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.GetPropertiesUtility;

/**
 * @author IBM
 */
public class LoggingAction extends DispatchAction {

	/**
	 * Method to edit the logging status.
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
	public ActionForward editLogging(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionErrors errors = new ActionErrors();
		ActionForward forward = new ActionForward(); // return value

		try {
			String tempString = request.getParameter("loggingStatus");
			if ((tempString != null) && !("".equalsIgnoreCase(tempString))) {
				int loggingStatus = new Integer(tempString).intValue();
				switch (loggingStatus) {
				case 0:
					AppConstants.NPDLOGGER.setLevel(Level.OFF);
					break;
				case 1:
					AppConstants.NPDLOGGER.setLevel(Level.FATAL);
					break;
				case 2:
					AppConstants.NPDLOGGER.setLevel(Level.ERROR);
					break;
				case 3:
					AppConstants.NPDLOGGER.setLevel(Level.WARN);
					break;
				case 4:
					AppConstants.NPDLOGGER.setLevel(Level.INFO);
					break;
				case 5:
					AppConstants.NPDLOGGER.setLevel(Level.DEBUG);
					break;
				case 6:
					AppConstants.NPDLOGGER.setLevel(Level.ALL);
					break;
				}
			}
		} catch (Exception e) {

			// Report the error using the appropriate name and ID.
			errors.add("name", new ActionMessage("error"));
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
		}

		// If a message is required, save the specified key(s)
		// into the request for use by the <struts:errors> tag.

		if (!errors.isEmpty()) {
			this.saveErrors(request, errors);

		}
		// Forward control to the appropriate 'success' URI (change name as
		// desired)

		forward = null;
		// forward = mapping.findForward("initLogging");

		// Finish with
		return forward;

	}

	/**
	 * Method to edit the logging status.
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
	public ActionForward displayLogging(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionErrors errors = new ActionErrors();
		ActionForward forward = new ActionForward(); // return value
		OutputStream out = null;
		String fileName = request.getParameter("fileName");
		String pathName = GetPropertiesUtility.getProperty("Logging.Path");
		FileInputStream fis = null;

		try {
			if ((pathName == null) || (fileName == null)) {
				AppConstants.NPDLOGGER.error("Invalid path");
			} else {
				File newFile = new File(pathName + fileName);
				if ((newFile != null) && newFile.exists()) {
					out = response.getOutputStream();
					response.setContentType("application/binary");
					response.setHeader("Content-Disposition",
							"attachment; filename=\" Logs.txt \";");
					fis = new FileInputStream(newFile);
					while (fis.available() != 0) {
						out.write(fis.read());
					}
				}
			}

		} catch (Exception e) {

			// Report the error using the appropriate name and ID.
			errors.add("name", new ActionMessage("error"));
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
		} finally {
			if (fis != null) {
				fis.close();
			}
			if (out != null) {
				out.close();
			}
		}

		// If a message is required, save the specified key(s)
		// into the request for use by the <struts:errors> tag.

		if (!errors.isEmpty()) {
			this.saveErrors(request, errors);

		}
		// Forward control to the appropriate 'success' URI (change name as
		// desired)

		forward = null;
		// forward = mapping.findForward("initLogging");

		// Finish with
		return forward;
	}
}