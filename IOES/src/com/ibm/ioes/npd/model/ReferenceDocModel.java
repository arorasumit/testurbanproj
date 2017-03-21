/**
 * 
 */
package com.ibm.ioes.npd.model;

import java.util.ArrayList;

import org.hibernate.Session;

import com.ibm.ioes.npd.beans.MeetingBean;
import com.ibm.ioes.npd.beans.ReferenceDocBean;
import com.ibm.ioes.npd.hibernate.beans.TmEmployee;
import com.ibm.ioes.npd.hibernate.beans.TmReferencedocs;
import com.ibm.ioes.npd.hibernate.beans.TtrnMeetingschedules;
import com.ibm.ioes.npd.hibernate.dao.BaseDaoFactory;
import com.ibm.ioes.npd.hibernate.dao.CommonBaseDao;
import com.ibm.ioes.npd.hibernate.dao.DaoConstants;
import com.ibm.ioes.npd.hibernate.dao.MeetingDao;
import com.ibm.ioes.npd.hibernate.dao.NpdUserDao;
import com.ibm.ioes.npd.hibernate.dao.ReferenceDocDao;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.HibernateBeansConstants;

/**
 * @author Disha
 * 
 */
public class ReferenceDocModel extends CommonBaseModel {

	/**
	 * This method calls the method to get All the referenceDoc uploaded till
	 * yet.
	 * 
	 * @param ReferenceDocBean
	 * @return
	 * @throws Exception
	 */

	public ReferenceDocBean viewReferenceDoc(ReferenceDocBean referenceDocBean)
			throws Exception {

		Session hibernateSession = null;
		try {
			hibernateSession = CommonBaseDao.beginTrans();
			ReferenceDocDao referenceDocDao = (ReferenceDocDao) BaseDaoFactory
					.getInstance(DaoConstants.REFERENCE_DOC_DAO);
			if (referenceDocDao.getActiveRefDoc(referenceDocBean,
					hibernateSession).size() > 0) {
				referenceDocBean.setReferenceDocList(referenceDocDao
						.getActiveRefDoc(referenceDocBean, hibernateSession));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CommonBaseDao.closeTrans(hibernateSession);
		}
		return referenceDocBean;
	}

	/**
	 * This method calls the method to get All the referenceDoc uploaded till
	 * with its versioning yet.
	 * 
	 * @param ReferenceDocBean
	 * @return
	 * @throws Exception
	 */

	public ReferenceDocBean viewReferenceDocHistory(
			ReferenceDocBean referenceDocBean) throws Exception {

		Session hibernateSession = null;
		try {
			hibernateSession = CommonBaseDao.beginTrans();
			ReferenceDocDao referenceDocDao = (ReferenceDocDao) BaseDaoFactory
					.getInstance(DaoConstants.REFERENCE_DOC_DAO);
			if (referenceDocDao.getActiveRefDoc(referenceDocBean,
					hibernateSession).size() > 0) {
				referenceDocBean.setReferenceDocList(referenceDocDao
						.getActiveRefDoc(referenceDocBean, hibernateSession));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CommonBaseDao.closeTrans(hibernateSession);
		}

		return referenceDocBean;
	}

	/**
	 * This method add/update the Reference doc in DB.
	 * 
	 * @param MeetingBean
	 * @return
	 * @throws Exception
	 */

	public boolean saveReferenceDoc(ReferenceDocBean referenceDocBean,
			TmEmployee tmEmployee) throws Exception {
		Session hibernateSession = null;
		boolean insert = false;
		try {
			ReferenceDocDao referenceDocDao = (ReferenceDocDao) BaseDaoFactory
					.getInstance(DaoConstants.REFERENCE_DOC_DAO);

			hibernateSession = CommonBaseDao.beginTrans();

			insert = referenceDocDao.saveReferenceDoc(referenceDocBean,
					tmEmployee, hibernateSession);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CommonBaseDao.closeTrans(hibernateSession);
		}

		return insert;

	}

}
