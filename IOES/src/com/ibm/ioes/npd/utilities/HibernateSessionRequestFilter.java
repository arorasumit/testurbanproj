package com.ibm.ioes.npd.utilities;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.StaleObjectStateException;

/**
 * @author Shaan
 * 
 * Filter to handle hibernate session requests
 */
public class HibernateSessionRequestFilter implements Filter {

	private static Logger log = AppConstants.NPDLOGGER;

	/**
	 * Initialize the filter
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	/**
	 * Do the filter processing
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
//		Session session = null;

		try {

			log.debug("Starting a database transaction");

//			session = HibernateUtility.currentSession();
//			session.beginTransaction();

			// Call the next filter (continue request processing)
			chain.doFilter(request, response);

//			if (session.isOpen()) {
//				// Commit and cleanup
//				// Fix for Bug MASDB00057917 
//				session.flush();
//				log.debug("Committing the database transaction");
//				session.getTransaction().commit();
//			}
		} catch (StaleObjectStateException staleEx) {
			log
					.error("This interceptor does not implement optimistic concurrency control!");
			log
					.error("Your application will not work until you add compensation actions!");
			// Rollback, close everything, possibly compensate
			// for any permanent changes during the conversation,
			// and finally restart business conversation. Maybe
			// give the user of the application a chance to merge
			// some of his work with fresh data...
			throw staleEx;
		} catch (Throwable ex) {
			// Rollback only
			log.error(AppUtility.getStackTrace(ex));
			try {
//				if (session.getTransaction().isActive()) {
//					log
//							.debug("Trying to rollback database transaction after exception");
//					session.getTransaction().rollback();
//				}
			} catch (Throwable rbEx) {
				log.error("Could not rollback transaction after exception!",
						rbEx);
			} finally {
				// No matter what happens, close the Session.
//				HibernateUtility.closeSession();
			}
			// Let others handle it..
			throw new ServletException(ex);
		}
	}

	public void destroy() {
	}

}