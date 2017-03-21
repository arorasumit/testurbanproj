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
import com.ibm.ioes.npd.hibernate.beans.TmEmployee;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjectisssues;
import com.ibm.ioes.npd.hibernate.dao.BaseDaoFactory;
import com.ibm.ioes.npd.hibernate.dao.CommonBaseDao;
import com.ibm.ioes.npd.hibernate.dao.DaoConstants;
import com.ibm.ioes.npd.hibernate.dao.IssuesCaptureDao;
import com.ibm.ioes.npd.hibernate.dao.NpdUserDao;
import com.ibm.ioes.npd.utilities.AjaxHelper;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.HibernateBeansConstants;

/**
 * @author Sanjay
 * 
 */
public class IssuesCaptureModel extends CommonBaseModel {

	public boolean addIssues(IssuesCaptureBean captureBean,
			TmEmployee tmEmployee) throws Exception {

		IssuesCaptureDao issuesCaptureDao = (IssuesCaptureDao) BaseDaoFactory
				.getInstance(DaoConstants.ISSUE_CAPTURE_DAO);
		CommonBaseDao commonBaseDao = new CommonBaseDao();

		boolean insert = issuesCaptureDao.addIssues(captureBean, tmEmployee);
		return insert;

	}

	public IssuesCaptureBean viewIssues(IssuesCaptureBean captureBean,
			String issueId, TmEmployee tmEmployee) throws Exception {
		Session hibernateSession = null;
		IssuesCaptureDao issuesCaptureDao = (IssuesCaptureDao) BaseDaoFactory
				.getInstance(DaoConstants.ISSUE_CAPTURE_DAO);
		CommonBaseDao commonBaseDao = new CommonBaseDao();
		TtrnProjectisssues ttrnProjectisssues = new TtrnProjectisssues();
		hibernateSession = CommonBaseDao.beginTrans();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				AppConstants.DATE_FORMAT_PROC);

		try {
			if (issueId != null
					&& !issueId.equalsIgnoreCase(AppConstants.INI_VALUE)) {
				ttrnProjectisssues = (TtrnProjectisssues) commonBaseDao
						.findById(new Long(issueId).longValue(),
								HibernateBeansConstants.HBM_PROJECT_ISSUE,
								hibernateSession);
				if (ttrnProjectisssues != null) {
					captureBean.setActualReslDate(simpleDateFormat
							.format(ttrnProjectisssues
									.getActualresolutiondate()));
					captureBean.setDescription(ttrnProjectisssues
							.getIssuedesc());
					captureBean.setPlannedReslDate(simpleDateFormat
							.format(ttrnProjectisssues.getResolutiondate()));
					captureBean.setPriority(ttrnProjectisssues.getPriority());
					if (ttrnProjectisssues.getTtrnProject() != null)
						captureBean.setProjectId(new Long(ttrnProjectisssues
								.getTtrnProject().getProjectid()).toString());
					if (ttrnProjectisssues.getRaisedby() != null)
						captureBean.setRaisedby(new Long(ttrnProjectisssues
								.getRaisedby().getNpdempid()).toString());
					captureBean.setRaisedon(simpleDateFormat
							.format(ttrnProjectisssues.getRaiseddate()));
					captureBean.setReslutionSteps(ttrnProjectisssues
							.getPreposedresolutionsteps());
					if (ttrnProjectisssues.getResolutionowner() != null)
						captureBean.setResolutionOwner(new Long(
								ttrnProjectisssues.getResolutionowner()
										.getNpdempid()).toString());
					captureBean.setStatus(new Integer(ttrnProjectisssues
							.getStatus()).toString());
					captureBean.setTimeframe(new Integer(ttrnProjectisssues
							.getResolutiontime()).toString());
					captureBean.setIssueID(issueId);

					IssuesCaptureDao dao = new IssuesCaptureDao();
					captureBean.setResolutionOwnerList(dao
							.getUsersOfResolution(captureBean.getProjectId(),
									hibernateSession));

				}

			} else {

				captureBean.setActualReslDate(null);
				captureBean.setDescription(AppConstants.INI_VALUE);
				captureBean.setPlannedReslDate(null);

				captureBean.setPriority(AppConstants.SELECT_OPTION);
				if (captureBean.getProjectId() == null
						|| captureBean.getProjectId().equalsIgnoreCase(
								AppConstants.INI_VALUE))
					captureBean.setProjectId(null);
				captureBean.setRaisedby(null);
				captureBean.setRaisedon(simpleDateFormat.format(new Date()));
				captureBean.setReslutionSteps(AppConstants.INI_VALUE);
				captureBean.setResolutionOwner(null);
				captureBean.setStatus(AppConstants.SELECT_OPTION);
				captureBean.setTimeframe(null);
				captureBean.setIssueID(null);
			}

			captureBean.setProjectList(issuesCaptureDao.getProjects(tmEmployee,
					hibernateSession));
			Criteria ce=hibernateSession.createCriteria(HibernateBeansConstants.HBM_PROJECT);
			ce.add(Restrictions.eq("projectstatus", new Integer(1)));
			ce.addOrder(Order.asc("projectid"));
			List list=ce.list();
			if(list!=null)
			{
				captureBean.setSearchProjectList((ArrayList)list);
			}
			/*
			 * captureBean.setResolutionOwnerList(commonBaseDao
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

	public IssuesCaptureBean getIssuesForAProject(IssuesCaptureBean captureBean) {
		Session hibernateSession = null;
		IssuesCaptureDao issuesCaptureDao = (IssuesCaptureDao) BaseDaoFactory
				.getInstance(DaoConstants.ISSUE_CAPTURE_DAO);
		String projectId = captureBean.getProjectId();

		try {

			hibernateSession = CommonBaseDao.beginTrans();
			captureBean.setIssueDetailList(issuesCaptureDao
					.getIssuesForAProject(captureBean, hibernateSession));
			captureBean.setProjectId(projectId);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			CommonBaseDao.closeTrans(hibernateSession);
		}
		return captureBean;
	}

}
