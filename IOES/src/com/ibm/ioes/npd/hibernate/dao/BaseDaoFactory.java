package com.ibm.ioes.npd.hibernate.dao;

import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.ibm.ioes.npd.utilities.AppConstants;

/**
 * @author Varun
 * @version 1.0
 */
public abstract class BaseDaoFactory {

	protected static final Logger log = AppConstants.NPDLOGGER;

	/**
	 * 
	 * This method will locate SessionFactory in JNDI
	 * 
	 * @return SessionFactory
	 */
	public static SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext()
					.lookup("SessionFactory_NPD");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException(
					"Could not locate SessionFactory in JNDI");
		}
	}

	/**
	 * Creates the required dao instances based on classType
	 * 
	 * @see DaoConstants
	 * @param classType
	 * @return the object matching the classType supplied
	 */
	public static CommonBaseDao getInstance(int classType) {
		try {
			CommonBaseDao commonBaseDao = null;
			switch (classType) {
			case DaoConstants.BASE_DAO_CLASS:
				commonBaseDao = new CommonBaseDao();
				break;
			case DaoConstants.NPD_USER_DAO:
				commonBaseDao = (CommonBaseDao) new NpdUserDao();
				break;
			case DaoConstants.MEETING_DAO:
				commonBaseDao = (CommonBaseDao) new MeetingDao();
				break;
			case DaoConstants.REFERENCE_DOC_DAO:
				commonBaseDao = (CommonBaseDao) new ReferenceDocDao();
				break;
			case DaoConstants.MASTER_PROJECT_PLAN_DAO:
				commonBaseDao = (CommonBaseDao) new MasterProjectPlanDao();
				break;
			case DaoConstants.PRODUCT_CREATION_DAO:
				commonBaseDao = (CommonBaseDao) new ProductCreationDao();
				break;
			case DaoConstants.RFI_DOCUMENT_DAO:
				commonBaseDao = (CommonBaseDao) new RfiDao();
				break;
			case DaoConstants.ISSUE_CAPTURE_DAO:
				commonBaseDao = (CommonBaseDao) new IssuesCaptureDao();
				break;
				
			case DaoConstants.ASSUMPTION_CAPTURE_DAO:
				commonBaseDao = (CommonBaseDao) new AssumptionCaptureDao() ;
				break;
			case DaoConstants.RISK_CAPTURE_DAO:
				commonBaseDao = (CommonBaseDao) new RisksCaptureDao();
				break;
		
			}
			if (commonBaseDao == null) {
				throw new Exception("Invalid Class Type");
			}
			return commonBaseDao;
		} catch (Exception e) {
			AppConstants.NPDLOGGER.error(
					"Could not get Instance of Dao in DaoFactory", e);
			throw new IllegalStateException(
					"Could not get Instance of Dao in DaoFactory");
		}
	}


	
}
