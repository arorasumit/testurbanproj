/**
 * 
 */
package com.ibm.ioes.npd.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ibm.ioes.npd.beans.IssuesCaptureBean;
import com.ibm.ioes.npd.beans.RisksCaptureBean;
import com.ibm.ioes.npd.hibernate.beans.TmEmployee;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjectisssues;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjectrisks;
import com.ibm.ioes.npd.hibernate.dao.BaseDaoFactory;
import com.ibm.ioes.npd.hibernate.dao.CommonBaseDao;
import com.ibm.ioes.npd.hibernate.dao.DaoConstants;
import com.ibm.ioes.npd.hibernate.dao.IssuesCaptureDao;
import com.ibm.ioes.npd.hibernate.dao.NpdUserDao;
import com.ibm.ioes.npd.hibernate.dao.RisksCaptureDao;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.HibernateBeansConstants;

/**
 * @author Disha
 * 
 */
public class RisksCaptureModel extends CommonBaseModel {

	public boolean addRisks(RisksCaptureBean captureBean, TmEmployee tmEmployee)
			throws Exception {

		RisksCaptureDao risksCaptureDao = (RisksCaptureDao) BaseDaoFactory
				.getInstance(DaoConstants.RISK_CAPTURE_DAO);
		CommonBaseDao commonBaseDao = new CommonBaseDao();

		boolean insert = risksCaptureDao.addRisks(captureBean, tmEmployee);
		return insert;

	}

	public RisksCaptureBean viewRisks(RisksCaptureBean captureBean,
			String riskID, TmEmployee tmEmployee) throws Exception {
		Session hibernateSession = null;
		RisksCaptureDao risksCaptureDao = (RisksCaptureDao) BaseDaoFactory
				.getInstance(DaoConstants.RISK_CAPTURE_DAO);
		CommonBaseDao commonBaseDao = new CommonBaseDao();
		TtrnProjectrisks ttrnProjectrisks = new TtrnProjectrisks();
		hibernateSession = CommonBaseDao.beginTrans();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				AppConstants.DATE_FORMAT_PROC);

		try {
			if (riskID != null
					&& !riskID.equalsIgnoreCase(AppConstants.INI_VALUE)) {
				ttrnProjectrisks = (TtrnProjectrisks) commonBaseDao.findById(
						new Long(riskID).longValue(),
						HibernateBeansConstants.HBM_PROJECT_RISK,
						hibernateSession);
				if (ttrnProjectrisks != null) {
					captureBean.setActualReslDate(simpleDateFormat
							.format(ttrnProjectrisks.getActualresodate()));
					captureBean.setDescription(ttrnProjectrisks
							.getDescription());
					captureBean.setPlannedReslDate(simpleDateFormat
							.format(ttrnProjectrisks.getPlannedresodate()));
					captureBean.setPriority(ttrnProjectrisks.getPriority());
					if (ttrnProjectrisks.getTtrnProject() != null)
						captureBean.setProjectId(new Long(ttrnProjectrisks
								.getTtrnProject().getProjectid()).toString());
					if (ttrnProjectrisks.getRaisedby() != null)
						captureBean.setRaisedby(new Long(ttrnProjectrisks
								.getRaisedby().getNpdempid()).toString());
					captureBean.setRaisedon(simpleDateFormat
							.format(ttrnProjectrisks.getRaiseddate()));

					captureBean.setStatus(new Integer(ttrnProjectrisks
							.getStatus()).toString());
					captureBean.setTimeframe(ttrnProjectrisks
							.getResolutiontime().toString());
					captureBean.setRiskID(riskID);
					captureBean.setSource(ttrnProjectrisks.getRisksource());
					captureBean.setProbability(ttrnProjectrisks
							.getProbability());
					captureBean.setImpact(ttrnProjectrisks.getImpact());
					captureBean.setMitigationPlan(ttrnProjectrisks
							.getMitigationplan());
					captureBean.setImpactOfRisk(ttrnProjectrisks
							.getRiskimpact());
					if (ttrnProjectrisks.getMigratedowner() != null)
						captureBean.setMitigationOwner(new Long(
								ttrnProjectrisks.getMigratedowner()
										.getNpdempid()).toString());
					if (ttrnProjectrisks.getRiskowner() != null)
						captureBean.setRiskOwner(new Long(ttrnProjectrisks
								.getRiskowner().getNpdempid()).toString());

					RisksCaptureDao dao = new RisksCaptureDao();
					captureBean.setMitigationOwnerList(dao
							.getUsersOfMitigation(captureBean.getProjectId(),
									hibernateSession));
					captureBean.setRiskOwnerList(dao.getUsersOfMitigation(
							captureBean.getProjectId(), hibernateSession));
				}

			} else {

				captureBean.setActualReslDate(null);
				captureBean.setDescription(AppConstants.INI_VALUE);
				captureBean.setSource(AppConstants.INI_VALUE);
				captureBean.setPlannedReslDate(null);
				captureBean.setPriority(AppConstants.SELECT_OPTION);
				if (captureBean.getProjectId() == null
						|| captureBean.getProjectId().equalsIgnoreCase(
								AppConstants.INI_VALUE))
					captureBean.setProjectId(null);
				captureBean.setRaisedby(null);
				captureBean.setRaisedon(simpleDateFormat.format(new Date()));
				captureBean.setStatus(AppConstants.SELECT_OPTION);
				captureBean.setProbability(AppConstants.SELECT_OPTION);
				captureBean.setImpact(AppConstants.SELECT_OPTION);
				captureBean.setMitigationOwner(AppConstants.SELECT_OPTION);
				captureBean.setRiskOwner(AppConstants.SELECT_OPTION);

				captureBean.setTimeframe(null);
				captureBean.setRiskID(null);
				captureBean.setMitigationPlan(AppConstants.INI_VALUE);
				captureBean.setImpactOfRisk(AppConstants.INI_VALUE);
			}

			captureBean.setProjectList(risksCaptureDao.getProjects(tmEmployee,
					hibernateSession));
			Criteria ce=hibernateSession.createCriteria(HibernateBeansConstants.HBM_PROJECT);
			ce.add(Restrictions.eq("projectstatus", new Integer(1)));
			ce.addOrder(Order.asc("projectid"));
			List list=ce.list();
			if(list!=null)
			{
				captureBean.setSearchProjectList((ArrayList)list);
			}
			/*captureBean.setSearchProjectList(commonBaseDao.getAllEntriesInATableOrder(hibernateSession, 
					HibernateBeansConstants.HBM_PROJECT, "projectid", "asc"));*/
			/*
			 * captureBean.setMitigationOwnerList(commonBaseDao
			 * .getAllEntriesInATable(hibernateSession,
			 * HibernateBeansConstants.HBM_EMPLOYEE));
			 * captureBean.setRiskOwnerList(commonBaseDao
			 * .getAllEntriesInATable(hibernateSession,
			 * HibernateBeansConstants.HBM_EMPLOYEE));
			 */

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			CommonBaseDao.closeTrans(hibernateSession);
		}
		return captureBean;
	}

	public RisksCaptureBean getRisksForAProject(RisksCaptureBean captureBean) {
		Session hibernateSession = null;
		RisksCaptureDao risksCaptureDao = (RisksCaptureDao) BaseDaoFactory
				.getInstance(DaoConstants.RISK_CAPTURE_DAO);
		String projectId = captureBean.getProjectId();

		try {

			hibernateSession = CommonBaseDao.beginTrans();
			captureBean.setRiskDetailList(risksCaptureDao.getRisksForAProject(
					captureBean, hibernateSession));
			captureBean.setProjectId(projectId);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			CommonBaseDao.closeTrans(hibernateSession);
		}
		return captureBean;
	}

	public RisksCaptureBean getRisksForSelectedProject(
			RisksCaptureBean captureBean) {
		Session hibernateSession = null;
		RisksCaptureDao risksCaptureDao = (RisksCaptureDao) BaseDaoFactory
				.getInstance(DaoConstants.RISK_CAPTURE_DAO);
		String projectId = captureBean.getProjectId();
		String projectName= captureBean.getProjectName();
		try {

			hibernateSession = CommonBaseDao.beginTrans();
			captureBean.setRiskDetailList(risksCaptureDao
					.getRisksForSelectedProject(captureBean, hibernateSession));
			captureBean.setProjectId(projectId);
			captureBean.setProjectName(projectName);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			CommonBaseDao.closeTrans(hibernateSession);
		}
		return captureBean;
	}

}
