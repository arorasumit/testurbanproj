package com.ibm.ioes.npd.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.ioes.npd.utilities.HibernateUtility;

/**
 * @author Varun
 * 
 */
public class HibernateListener implements ServletContextListener {

	private static Log log = LogFactory.getLog(HibernateListener.class);

	/**
	 * Automatically generated constructor: HibernateListener
	 */
	public HibernateListener() {
	}

	/**
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent event) {
		log
				.info("[HibernateListener] : Shutting down Hibernate persistence service");
		HibernateUtility.shutdown();
		log.info("Hibernate shutdown complete");
	}

	/**
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent event) {
		log
				.info("[HibernateListener] :Starting Hibernate persistence service...");
		HibernateUtility.getSessionFactory(); // Just call static initializer
		log.info("Hibernate startup complete");
	}
}
