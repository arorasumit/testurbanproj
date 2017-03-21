package com.ibm.ioes.utilities;

/**
 * @author santosh.patro
 *
 */
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.metaparadigm.jsonrpc.JSONRPCBridge;

public class AjaxService implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent sce) {

	}

	public void contextInitialized(ServletContextEvent sce) {
		JSONRPCBridge.getGlobalBridge().registerObject("processes", new AjaxHelper(sce.getServletContext()));
	}
}


