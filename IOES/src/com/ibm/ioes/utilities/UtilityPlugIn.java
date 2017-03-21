package com.ibm.ioes.utilities;

import javax.servlet.ServletException;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

public class UtilityPlugIn implements PlugIn {

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void init(ActionServlet actionServlet, ModuleConfig moduleConfig) throws ServletException {
		actionServlet.getServletContext().setAttribute(AppConstants.IB2B_DB_CONNECTION_LOGGER, new TestConnection());
		actionServlet.getServletContext().setAttribute(AppConstants.ApplicationFlags, new ApplicationFlags());
	}

}
