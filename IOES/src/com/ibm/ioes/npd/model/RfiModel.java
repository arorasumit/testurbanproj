/**
 * 
 */
package com.ibm.ioes.npd.model;

import org.hibernate.Session;

import com.ibm.ioes.npd.beans.ReferenceDocBean;
import com.ibm.ioes.npd.beans.RfiBean;
import com.ibm.ioes.npd.hibernate.beans.TmEmployee;
import com.ibm.ioes.npd.hibernate.beans.TtrnProject;
import com.ibm.ioes.npd.hibernate.dao.BaseDaoFactory;
import com.ibm.ioes.npd.hibernate.dao.CommonBaseDao;
import com.ibm.ioes.npd.hibernate.dao.DaoConstants;
import com.ibm.ioes.npd.hibernate.dao.ReferenceDocDao;
import com.ibm.ioes.npd.hibernate.dao.RfiDao;

/**
 * @author Sanjay
 * 
 */
public class RfiModel extends CommonBaseModel {

	public RfiBean rfiDetails(RfiBean rfiBean, TmEmployee tmEmployee)
			throws Exception {

		RfiDao rfiDao = new RfiDao();
		Session hibernateSession = null;

		try {
			hibernateSession = CommonBaseDao.beginTrans();
			rfiBean.setProjectDetailList(rfiDao.getPendingRfi(rfiBean,
					tmEmployee, hibernateSession));
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {

			CommonBaseDao.closeTrans(hibernateSession);
		}

		return rfiBean;
	}

	public RfiBean rfiDetailsNpdSpocMail(RfiBean rfiBean,TmEmployee tmEmployee) throws Exception {

		RfiDao rfiDao = new RfiDao();
		Session hibernateSession = null;

		hibernateSession = CommonBaseDao.beginTrans();
		try{
			rfiBean.setProjectDetailList(rfiDao.getPendingRfiNpdSpocMail(rfiBean,tmEmployee,hibernateSession));
		}finally{
		CommonBaseDao.closeTrans(hibernateSession);
		}
		return rfiBean;
	}
	public RfiBean getRfiCommentsAndBlob(RfiBean rfiBean, TmEmployee tmEmployee)
			throws Exception {

		RfiDao rfiDao = new RfiDao();
		Session hibernateSession = null;

		try {
			hibernateSession = CommonBaseDao.beginTrans();
			rfiBean = rfiDao.getRfiDetails(rfiBean, tmEmployee,
					hibernateSession);
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {

			CommonBaseDao.closeTrans(hibernateSession);
		}
		return rfiBean;
	}

	public boolean saveReferenceDoc(RfiBean rfiBean, TmEmployee tmEmployee)
			throws Exception {

		RfiDao rfiDao = (RfiDao) BaseDaoFactory
				.getInstance(DaoConstants.RFI_DOCUMENT_DAO);
		Session hibernateSession = null;
		TtrnProject project = null;
		hibernateSession = CommonBaseDao.beginTrans();
		boolean insert = false;
		try {

			insert = rfiDao.saveReferenceDoc(rfiBean, project,
					hibernateSession, tmEmployee);
			/*
			 * hibernateSession = CommonBaseDao.beginTrans(); insert =
			 * rfiDao.saveClarification(rfiBean, project, hibernateSession,
			 * tmEmployee);
			 */
		} catch (Exception ex) {
			hibernateSession.beginTransaction().rollback();
			ex.printStackTrace();
		} finally {

			CommonBaseDao.closeTrans(hibernateSession);
		}
		return insert;

	}

	public int getPendingRFICount(String userId, String roleID) {
		RfiDao rfiDao = new RfiDao();
		int taskPending_rfi = 0;
		try {
			taskPending_rfi = rfiDao.getPendingRFICount(userId, roleID);
		} catch (Exception e) {
		}
		return taskPending_rfi;
	}
}
