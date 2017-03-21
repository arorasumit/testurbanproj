package com.ibm.ioes.npd.hibernate.dao;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.ibm.ioes.npd.beans.AssumptionCaptureBean;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjectassumptions;
import com.ibm.ioes.npd.beans.IssuesCaptureBean;
import com.ibm.ioes.npd.beans.UserNpdSpocs;
import com.ibm.ioes.npd.hibernate.beans.TmEmployee;
import com.ibm.ioes.npd.hibernate.beans.TmRoleempmapping;
import com.ibm.ioes.npd.hibernate.beans.TmRoles;
import com.ibm.ioes.npd.hibernate.beans.TtrnProject;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.HibernateBeansConstants;
import com.ibm.ioes.npd.utilities.PagingSorting;
import com.ibm.ioes.npd.beans.UserNpdSpocs;
import com.ibm.ioes.npd.hibernate.beans.TmEmployee;
//import com.ibm.security.tools.parseCert;

public class AssumptionCaptureDao extends CommonBaseDao {
	public ArrayList getProjects(Session hibernateSession, TmEmployee tmEmployee) {

		ArrayList list = new ArrayList();
		ArrayList projectList = new ArrayList();
		TtrnProject project = new TtrnProject();
		CommonBaseDao commonBaseDao = BaseDaoFactory
				.getInstance(DaoConstants.BASE_DAO_CLASS);

		SQLQuery query = hibernateSession
				.createSQLQuery("SELECT DISTINCT PROJECTID FROM NPD.V_OPENPROJECTLISTFORUSER where ASSIGNEDTOUSERID="
						+ tmEmployee.getNpdempid());
		list = (ArrayList) query.list();

		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {

				BigInteger projectId = (BigInteger) list.get(i);

				project = (TtrnProject) commonBaseDao.findById(projectId
						.longValue(), HibernateBeansConstants.HBM_PROJECT,
						hibernateSession);
				project.setAssignedToUser(tmEmployee.getEmpname());
				projectList.add(project);

			}
		}

		return projectList;
	}

	public boolean addAssumption(AssumptionCaptureBean captureBean,
			TmEmployee tmEmployee) throws Exception {
		Session hibernateSession = null;
		hibernateSession = CommonBaseDao.beginTrans();
		CommonBaseDao commonBaseDao = BaseDaoFactory
				.getInstance(DaoConstants.BASE_DAO_CLASS);
		TtrnProjectassumptions ttrnProjectAssumptions = new TtrnProjectassumptions();
		boolean insert = true;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				AppConstants.DATE_FORMAT_PROC);

		try {
			if (captureBean.getAssumptionID() != null
					&& !captureBean.getAssumptionID().equalsIgnoreCase(
							AppConstants.INI_VALUE)) {
				ttrnProjectAssumptions = (TtrnProjectassumptions) commonBaseDao
						.findById(new Long(captureBean.getAssumptionID())
								.longValue(),
								HibernateBeansConstants.HBM_PROJECT_Assumption,
								hibernateSession);
				ttrnProjectAssumptions.setModifiedby(new Long(tmEmployee
						.getNpdempid()));
				ttrnProjectAssumptions.setModifieddate(new Date());
			} else {
				ttrnProjectAssumptions.setCreatedby(tmEmployee.getNpdempid());// Created
				// By
				ttrnProjectAssumptions.setCreateddate(new Date());// create
				// Date
				ttrnProjectAssumptions
						.setTtrnProject((TtrnProject) commonBaseDao.findById(
								new Long(captureBean.getProjectId())
										.longValue(),
								HibernateBeansConstants.HBM_PROJECT,
								hibernateSession));// Project ID
			}
			ttrnProjectAssumptions.setRaiseddate(simpleDateFormat
					.parse(captureBean.getRaisedon()));// Raised On
			ttrnProjectAssumptions.setDescription(captureBean.getDescription());// Decription
			ttrnProjectAssumptions.setImpact(captureBean.getImpact());// Impact

			commonBaseDao.attachDirty(ttrnProjectAssumptions, hibernateSession);
		} catch (Exception e) {
			insert = false;
			hibernateSession.getTransaction().rollback();
			e.printStackTrace();
		} finally {

			commonBaseDao.closeTrans(hibernateSession);
		}

		return insert;
	}

	public ArrayList getAssumptionForAProject(
			AssumptionCaptureBean captureBean, Session hibernateSession)
			throws ParseException {
		ArrayList list = new ArrayList();
		CommonBaseDao commonBaseDao = BaseDaoFactory
				.getInstance(DaoConstants.BASE_DAO_CLASS);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar = Calendar.getInstance();
		if (captureBean.getFromDate() != null
				&& !captureBean.getFromDate().equalsIgnoreCase("")) {
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.setTime(sdf.parse(captureBean.getFromDate()));
		}
		if (captureBean.getToDate() != null
				&& !captureBean.getToDate().equalsIgnoreCase("")) {
			calendar1.set(Calendar.HOUR_OF_DAY, 23);
			calendar1.set(Calendar.MINUTE, 59);
			calendar1.set(Calendar.SECOND, 59);
			calendar1.setTime(sdf.parse(captureBean.getToDate()));
			calendar1.add(Calendar.DATE, 1);
		}

		PagingSorting pagingSorting = captureBean.getPagingSorting();
		if (pagingSorting == null) {
			pagingSorting = new PagingSorting();
			captureBean.setPagingSorting(pagingSorting);
		}
		pagingSorting.setPagingSorting(true, true);

		pagingSorting.setDefaultifNotPresent("projissueid",
				PagingSorting.increment, 1);

		pagingSorting.setMode("hibernate");

		Criteria ce = hibernateSession
				.createCriteria(TtrnProjectassumptions.class);
		
		ce.createAlias("ttrnProject", "ttrnProject");

		if (captureBean.getProjectId() != null
				&& !captureBean.getProjectId().equalsIgnoreCase(
						AppConstants.INI_VALUE)) {
			ce.add(Restrictions.eq("ttrnProject.projectid", new Long(
					captureBean.getProjectId())));
		}
		if (captureBean.getProjectName() != null
				&& !captureBean.getProjectName().equalsIgnoreCase(
						AppConstants.INI_VALUE)) {
			ce.add(Restrictions.ilike("ttrnProject.projectName", captureBean
					.getProjectName(), MatchMode.ANYWHERE));
		}
		if (captureBean.getToDate() != null
				&& captureBean.getFromDate() != null
				&& !(captureBean.getToDate().equalsIgnoreCase("") && captureBean
						.getFromDate().equalsIgnoreCase(""))) {

			ce.add(Restrictions.between("createddate", new Date(calendar
					.getTime().getTime()), new Date(calendar1.getTime()
					.getTime())));
		}

		if (ce.list() != null) {

			list = (ArrayList) ce.list();
			pagingSorting.setRecordCount(list.size());
		}
		ce.setFirstResult(pagingSorting.getStartRecordId());
		ce.setMaxResults(pagingSorting.getPageRecords());
		list = (ArrayList) ce.list();
		return list;
	}

	public ArrayList getAssumptionForSelectedProject(
			AssumptionCaptureBean captureBean, Session hibernateSession)
			throws ParseException {
		ArrayList list = new ArrayList();
		CommonBaseDao commonBaseDao = BaseDaoFactory
				.getInstance(DaoConstants.BASE_DAO_CLASS);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar = Calendar.getInstance();
		if (captureBean.getFromDate() != null
				&& !captureBean.getFromDate().equalsIgnoreCase("")) {
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.setTime(sdf.parse(captureBean.getFromDate()));
		}
		if (captureBean.getToDate() != null
				&& !captureBean.getToDate().equalsIgnoreCase("")) {
			calendar1.set(Calendar.HOUR_OF_DAY, 23);
			calendar1.set(Calendar.MINUTE, 59);
			calendar1.set(Calendar.SECOND, 59);
			calendar1.setTime(sdf.parse(captureBean.getToDate()));
			calendar1.add(Calendar.DATE, 1);
		}

		PagingSorting pagingSorting = captureBean.getPagingSorting();
		if (pagingSorting == null) {
			pagingSorting = new PagingSorting();
			captureBean.setPagingSorting(pagingSorting);
		}
		pagingSorting.setPagingSorting(true, true);

		pagingSorting.setDefaultifNotPresent("projissueid",
				PagingSorting.increment, 1);

		pagingSorting.setMode("hibernate");

		Criteria ce = hibernateSession
				.createCriteria(TtrnProjectassumptions.class);
		
		ce.createAlias("ttrnProject", "ttrnProject");

		if (captureBean.getProjectId() != null
				&& !captureBean.getProjectId().equalsIgnoreCase(
						AppConstants.INI_VALUE)) 
		{
			ce.add(Restrictions.in("ttrnProject.projectid", captureBean.getProjectidlist()));
			//ce.add(Restrictions.eq("ttrnProject.projectid", new Long(captureBean.getProjectId())));
		}
		if (captureBean.getProjectName() != null
				&& !captureBean.getProjectName().equalsIgnoreCase(
						AppConstants.INI_VALUE)) {
			ce.add(Restrictions.ilike("ttrnProject.projectName", captureBean
					.getProjectName(), MatchMode.ANYWHERE));
		}
		if (captureBean.getToDate() != null
				&& captureBean.getFromDate() != null
				&& !(captureBean.getToDate().equalsIgnoreCase("") && captureBean
						.getFromDate().equalsIgnoreCase(""))) {

			ce.add(Restrictions.between("createddate", new Date(calendar
					.getTime().getTime()), new Date(calendar1.getTime()
					.getTime())));
		}

		if (ce.list() != null) {

			list = (ArrayList) ce.list();
			pagingSorting.setRecordCount(list.size());
		}
		ce.setFirstResult(pagingSorting.getStartRecordId());
		ce.setMaxResults(pagingSorting.getPageRecords());
		list = (ArrayList) ce.list();
		return list;
	}

}
