package com.ibm.ioes.npd.hibernate.dao;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;

import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.NpdConnection;
import com.ibm.ioes.utilities.DbConnection;

/**
 * This class contains the common methods for the insertion,deletion & findbyid
 * for all classes
 * 
 * @author Varun
 * @version 1.0
 */

public class CommonBaseDao {

	protected static final Logger log = AppConstants.NPDLOGGER;

	protected static final SessionFactory sessionFactory = BaseDaoFactory
			.getSessionFactory();

	public static Session beginTrans() throws Exception {
		Session hibernateSession = null;
		// Get session from Hibernate persistence service
		hibernateSession = sessionFactory.openSession();
		// Begin the hibernate transaction
		hibernateSession = sessionFactory.getCurrentSession();
		hibernateSession.beginTransaction();
		return hibernateSession;
	}

	public static void closeTrans(Session hibernateSession) {
		try {
			// Commit the hibernate transaction
			if (hibernateSession != null && hibernateSession.isOpen()) {
				hibernateSession.getTransaction().commit();
			}
		} catch (HibernateException e) {
			hibernateSession.getTransaction().rollback();
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			log.error(AppUtility.getStackTrace(e));
			throw e;
		} catch (RuntimeException re) {
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(re));
			throw re;
		} finally {
			if (hibernateSession != null && hibernateSession.isOpen()) {
				hibernateSession.close();
			}
		}
	}

	/**
	 * Save or update the supplied object into database
	 * 
	 * @param instance
	 * @param hibernateSession
	 */

	public void attachDirty(Object instance, Session hibernateSession) {
		try {
			log.debug("attaching dirty instance");
			hibernateSession.saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (HibernateException e) {
			log.error("Exception while attaching dirty");
			log.error(AppUtility.getStackTrace(e));
			throw e;
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/**
	 * Delete the supplied object from database
	 * 
	 * @param persistentInstance
	 * @param hibernateSession
	 */
	public void delete(Object persistentInstance, Session hibernateSession) {
		try {
			log.debug("deleting instance");
			hibernateSession.delete(persistentInstance);
			log.debug("delete successful");
		} catch (HibernateException e) {
			log.error("Exception while deleting");
			log.error(AppUtility.getStackTrace(e));
			throw e;
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/**
	 * Find an object corresponding to a particular id
	 * 
	 * @param id
	 * @param className
	 * @param hibernateSession
	 * @return the object corresponding to the supplied id
	 */
	public Object findById(int id, String className, Session hibernateSession) {

		try {
			log.debug("getting " + className + " instance with id: " + id);
			hibernateSession.get(className, new Integer(0));
			Object instance = hibernateSession.get(className, new Integer(id));
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;

		} catch (HibernateException e) {
			log.error("Exception while getting " + className);
			log.error(AppUtility.getStackTrace(e));
			throw e;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/**
	 * Find an object corresponding to a particular id
	 * 
	 * @param id
	 * @param className
	 * @param hibernateSession
	 * @return the object corresponding to the supplied id
	 */
	public Object findById(long id, String className, Session hibernateSession) {

		try {
			log.debug("getting " + className + " instance with id: " + id);
			hibernateSession.get(className, new Long(0));
			Object instance = hibernateSession.get(className, new Long(id));
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;

		} catch (HibernateException e) {
			log.error("Exception while getting " + className);
			log.error(AppUtility.getStackTrace(e));
			throw e;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/**
	 * Find objects corresponding to a particular objects
	 * 
	 * @param instance
	 * @param className
	 * @param hibernateSession
	 * @return list containing the objects matching the supplied object
	 */
	public List findByExample(Object instance, String className,
			Session hibernateSession) {

		try {
			log.debug("finding " + className + " instance by example");
			List results = hibernateSession.createCriteria(className).add(
					Example.create(instance).ignoreCase()).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (HibernateException e) {
			log.error("Exception while getting " + className);
			log.error(AppUtility.getStackTrace(e));
			throw e;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	/**
	 * Save or update the supplied object into database
	 * 
	 * @param instance
	 * @param hibernateSession
	 */

	public void attachDirty(Object instance) {
		Session hibernateSession = null;
		try {
			// Get session from Hibernate persistence service
			sessionFactory.openSession();
			// Begin the hibernate transaction
			hibernateSession = sessionFactory.getCurrentSession();
			hibernateSession.beginTransaction();
			log.debug("attaching dirty instance");
			hibernateSession.saveOrUpdate(instance);
			log.debug("attach successful");
			// Commit the hibernate transaction
			hibernateSession.getTransaction().commit();
		} catch (HibernateException e) {
			hibernateSession.getTransaction().rollback();
			log.error("Exception while attaching dirty");
			log.error(AppUtility.getStackTrace(e));
			throw e;
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		} finally {
			if (hibernateSession != null && hibernateSession.isOpen()) {
				hibernateSession.close();
			}
		}
	}

	/**
	 * Delete the supplied object from database
	 * 
	 * @param persistentInstance
	 * @param hibernateSession
	 */
	public void delete(Object persistentInstance) {
		Session hibernateSession = null;
		try {
			// Get session from Hibernate persistence service
			sessionFactory.openSession();
			// Begin the hibernate transaction
			hibernateSession = sessionFactory.getCurrentSession();
			hibernateSession.beginTransaction();
			log.debug("delete instance");
			hibernateSession.delete(persistentInstance);
			log.debug("delete successful");
			// Commit the hibernate transaction
			hibernateSession.getTransaction().commit();
		} catch (HibernateException e) {
			hibernateSession.getTransaction().rollback();
			log.error("Exception while deleting");
			log.error(AppUtility.getStackTrace(e));
			throw e;
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		} finally {
			if (hibernateSession != null && hibernateSession.isOpen()) {
				hibernateSession.close();
			}
		}

	}

	/**
	 * Find an object corresponding to a particular id
	 * 
	 * @param id
	 * @param className
	 * @param hibernateSession
	 * @return the object corresponding to the supplied id
	 */
	public Object findById(int id, String className) {

		Session hibernateSession = null;
		try {
			// Get session from Hibernate persistence service
			sessionFactory.openSession();
			// Begin the hibernate transaction
			hibernateSession = sessionFactory.getCurrentSession();
			log.debug("getting " + className + " instance with id: " + id);
			hibernateSession.get(className, new Integer(0));
			Object instance = hibernateSession.get(className, new Integer(id));
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			// Commit the hibernate transaction
			hibernateSession.getTransaction().commit();
			return instance;
		} catch (HibernateException e) {
			hibernateSession.getTransaction().rollback();
			log.error("Exception while attaching dirty");
			log.error(AppUtility.getStackTrace(e));
			throw e;
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		} finally {
			if (hibernateSession != null && hibernateSession.isOpen()) {
				hibernateSession.close();
			}
		}
	}

	/**
	 * Find objects corresponding to a particular objects
	 * 
	 * @param instance
	 * @param className
	 * @param hibernateSession
	 * @return list containing the objects matching the supplied object
	 */
	public List findByExample(Object instance, String className) {
		Session hibernateSession = null;
		try {
			// Get session from Hibernate persistence service
			sessionFactory.openSession();
			// Begin the hibernate transaction
			hibernateSession = sessionFactory.getCurrentSession();
			log.debug("finding " + className + " instance by example");
			List results = hibernateSession.createCriteria(className).add(
					Example.create(instance).ignoreCase()).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			// Commit the hibernate transaction
			hibernateSession.getTransaction().commit();
			return results;
		} catch (HibernateException e) {
			hibernateSession.getTransaction().rollback();
			log.error("Exception while attaching dirty");
			log.error(AppUtility.getStackTrace(e));
			throw e;
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		} finally {
			if (hibernateSession != null && hibernateSession.isOpen()) {
				hibernateSession.close();
			}
		}
	}

	public Object attachDirtyWithMerge(Object instance, Session hibernateSession) {
		try {
			log.debug("attaching dirty instance");
			// hibernateSession.saveOrUpdate(instance);
			Object insertedObj = null;
			insertedObj = hibernateSession.merge(instance);
			log.debug("attach successful");
			return insertedObj;
		} catch (HibernateException e) {
			log.error("Exception while attaching dirty");
			log.error(AppUtility.getStackTrace(e));
			throw e;
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}

	}

	// This method gets All Entries of a Table

	public ArrayList getAllEntriesInATable(Session hibernateSession,
			String beanName) throws Exception {

		ArrayList List = new ArrayList();
		String className = beanName;
		Criteria ce = hibernateSession.createCriteria(className);

		if (ce.list() != null) {
			List = (ArrayList) ce.list();
		}
		return List;

	}
	public ArrayList getAllEntriesInATableOrder(Session hibernateSession,String beanName,String fieldname,String ascDesc)
	throws Exception {

		ArrayList List = new ArrayList();
		String className =beanName;
		Criteria ce = hibernateSession.createCriteria(className);
		
		if(fieldname!=null && !"".equals(fieldname))
		{
			if(ascDesc!=null && !"".equals(ascDesc))
			{
				if("desc".equalsIgnoreCase(ascDesc))
				{
					ce=ce.addOrder(Order.desc(fieldname));
				}
				else if("asc".equalsIgnoreCase(ascDesc))
				{
					ce=ce.addOrder(Order.asc(fieldname));
				}
			}
		}

		if (ce.list() != null) {
			List = (ArrayList) ce.list();
		}
		return List;

}	
	
	public int getAttachemntSize()
	{
		int attachmentSize=0;
		Connection connObj=null;
		
		String str="SELECT ATTACHMENTSIZE FROM NPD.TM_ATTACHMENTSIZE";
		Statement stmt = null;
		ResultSet rs=null;
		try 
		{
			connObj = NpdConnection.getConnectionObject();
			stmt = connObj.createStatement();
			rs=stmt.executeQuery(str);
			rs.next();
			attachmentSize=rs.getInt(1);			
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		}
		finally
		{
			try {
				rs.close();
				stmt.close();
				connObj.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return attachmentSize;
	}
	
	public int getPageSize()
	{
		Connection connObj=null;
		int pageRecords=0;
		String str="select size from NPD.VW_PAGING";
		Statement stmt = null;
		ResultSet rs=null;
		try 
		{
			connObj = NpdConnection.getConnectionObject();
			stmt = connObj.createStatement();
			rs=stmt.executeQuery(str);
			rs.next();
			pageRecords=rs.getInt(1);			
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		}
		finally
		{
			try {
				rs.close();
				stmt.close();
				connObj.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return pageRecords;
	}
	
	public String getSpecialChar()
	{
		Connection connObj=null;
		String iChar="";
		String str="SELECT CHARACTERS FROM NPD.VW_SPECIALCHARACTERS";
		Statement stmt = null;
		ResultSet rs=null;
		try 
		{
			connObj = NpdConnection.getConnectionObject();
			stmt = connObj.createStatement();
			rs=stmt.executeQuery(str);
			rs.next();
			iChar=rs.getString(1);			
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		}
		finally
		{
			try {
				//stmt.close();
				DbConnection.closeStatement(stmt);
				DbConnection.closeStatement(stmt);
				
				DbConnection.freeConnection(connObj);
			}
				catch(Exception exx)
				{
					exx.printStackTrace();
				}
				//connObj.close();
			
			}
		return iChar;
	}

}
