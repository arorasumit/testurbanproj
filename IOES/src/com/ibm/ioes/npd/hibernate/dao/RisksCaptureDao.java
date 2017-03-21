/**
 * 
 */
package com.ibm.ioes.npd.hibernate.dao;

import java.math.BigInteger;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import com.ibm.ioes.npd.beans.IssuesCaptureBean;
import com.ibm.ioes.npd.beans.RisksCaptureBean;
import com.ibm.ioes.npd.beans.UserNpdSpocs;
import com.ibm.ioes.npd.exception.NpdException;
import com.ibm.ioes.npd.hibernate.beans.TmEmployee;
import com.ibm.ioes.npd.hibernate.beans.TmRoleempmapping;
import com.ibm.ioes.npd.hibernate.beans.TmRoles;
import com.ibm.ioes.npd.hibernate.beans.TtrnProject;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjectisssues;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjectrisks;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.HibernateBeansConstants;
import com.ibm.ioes.npd.utilities.PagingSorting;

/**
 * @author Sanjay
 * 
 */
public class RisksCaptureDao extends CommonBaseDao {

	public ArrayList getProjects(TmEmployee tmEmployee, Session hibernateSession)
			throws SQLException {

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

				//Object obj[] = (Object[]) list.get(i);
				//BigInteger assignedToUser = (BigInteger) (obj[1]);
				//if (assignedToUser.longValue() == tmEmployee.getNpdempid()) {
					//BigInteger projectId = (BigInteger) (obj[0]);

					//project = (TtrnProject) commonBaseDao.findById(projectId
					//		.longValue(), HibernateBeansConstants.HBM_PROJECT,
					//		hibernateSession);
					//project.setAssignedToUser(assignedToUser.toString());
					//projectList.add(project);
				//}
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

	// Gets a List of Risks rearding a Project
	public ArrayList getRisksForAProject(RisksCaptureBean captureBean,
			Session hibernateSession) throws ParseException {

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
		Criteria ce = hibernateSession.createCriteria(TtrnProjectrisks.class);
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


	public ArrayList getRisksForSelectedProject(RisksCaptureBean captureBean,
			Session hibernateSession) throws ParseException {

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
		Criteria ce = hibernateSession.createCriteria(TtrnProjectrisks.class);
		ce.createAlias("ttrnProject", "ttrnProject");

		if (captureBean.getProjectId() != null
				&& !captureBean.getProjectId().equalsIgnoreCase(
						AppConstants.INI_VALUE)) 
		{
			ce.add(Restrictions.in("ttrnProject.projectid",captureBean.getProjectidlist()));
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

	// This method saves Entries of an Issue related to a project in DB.

	public boolean addRisks(RisksCaptureBean captureBean, TmEmployee tmEmployee)
			throws Exception {

		Session hibernateSession = null;
		hibernateSession = CommonBaseDao.beginTrans();
		CommonBaseDao commonBaseDao = BaseDaoFactory
				.getInstance(DaoConstants.BASE_DAO_CLASS);
		TtrnProjectrisks ttrnProjectrisks = new TtrnProjectrisks();
		boolean insert = true;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				AppConstants.DATE_FORMAT_PROC);

		try {
			if (captureBean.getRiskID() != null
					&& !captureBean.getRiskID().equalsIgnoreCase(
							AppConstants.INI_VALUE)) {
				ttrnProjectrisks = (TtrnProjectrisks) commonBaseDao.findById(
						new Long(captureBean.getRiskID()).longValue(),
						HibernateBeansConstants.HBM_PROJECT_RISK,
						hibernateSession);
				ttrnProjectrisks.setModifiedby(tmEmployee);
				ttrnProjectrisks.setModifieddate(new Date());
			} else {

				ttrnProjectrisks.setCreatedby(tmEmployee);
				ttrnProjectrisks.setCreateddate(new Date());
				ttrnProjectrisks.setRaisedby(tmEmployee);
				ttrnProjectrisks.setRaiseddate(simpleDateFormat
						.parse(captureBean.getRaisedon()));

				ttrnProjectrisks.setTtrnProject((TtrnProject) commonBaseDao
						.findById(new Long(captureBean.getProjectId())
								.longValue(),
								HibernateBeansConstants.HBM_PROJECT,
								hibernateSession));

			}
			ttrnProjectrisks.setIsactive(AppConstants.ACTIVE_FLAG);
			if (captureBean.getActualReslDate() != null
					&& !captureBean.getActualReslDate().equalsIgnoreCase(
							AppConstants.INI_VALUE))
				ttrnProjectrisks.setActualresodate(simpleDateFormat
						.parse(captureBean.getActualReslDate()));
			ttrnProjectrisks.setDescription(captureBean.getDescription());
			ttrnProjectrisks.setPriority(captureBean.getPriority());
			ttrnProjectrisks.setRisksource(captureBean.getSource());
			ttrnProjectrisks.setPlannedresodate(simpleDateFormat
					.parse(captureBean.getPlannedReslDate()));
			if (captureBean.getTimeframe() != null
					&& !captureBean.getTimeframe().equalsIgnoreCase(
							AppConstants.INI_VALUE))
				ttrnProjectrisks.setResolutiontime(new Integer(captureBean
						.getTimeframe()));
			if (captureBean.getMitigationOwner() != null
					&& !captureBean.getMitigationOwner().equalsIgnoreCase(
							AppConstants.INI_VALUE))
				ttrnProjectrisks.setMigratedowner((TmEmployee) commonBaseDao
						.findById(new Long(captureBean.getMitigationOwner())
								.longValue(),
								HibernateBeansConstants.HBM_EMPLOYEE,
								hibernateSession));
			if (captureBean.getStatus() != null
					&& !captureBean.getStatus().equalsIgnoreCase(
							AppConstants.INI_VALUE))
				ttrnProjectrisks.setStatus(Integer.parseInt(captureBean
						.getStatus()));
			if (captureBean.getRiskOwner() != null
					&& !captureBean.getRiskOwner().equalsIgnoreCase(
							AppConstants.INI_VALUE))
				ttrnProjectrisks.setRiskowner((TmEmployee) commonBaseDao
						.findById(new Long(captureBean.getRiskOwner())
								.longValue(),
								HibernateBeansConstants.HBM_EMPLOYEE,
								hibernateSession));
			if (captureBean.getProbability() != null
					&& !captureBean.getProbability().equalsIgnoreCase(
							AppConstants.INI_VALUE))
				ttrnProjectrisks.setProbability(captureBean.getProbability());
			if (captureBean.getImpact() != null
					&& !captureBean.getImpact().equalsIgnoreCase(
							AppConstants.INI_VALUE))
				ttrnProjectrisks.setImpact(captureBean.getImpact());
			ttrnProjectrisks.setMitigationplan(captureBean.getMitigationPlan());
			ttrnProjectrisks.setRiskimpact(captureBean.getImpactOfRisk());
			commonBaseDao.attachDirty(ttrnProjectrisks, hibernateSession);

		} catch (Exception e) {
			insert = false;
			hibernateSession.getTransaction().rollback();
			e.printStackTrace();
		} finally {

			commonBaseDao.closeTrans(hibernateSession);
		}

		return insert;

	}

	public ArrayList getUsersOfMitigation(String projectId,Session hibernateSession) throws Exception 
	{
		ArrayList employeeList=new  ArrayList();
		
		try
		{
						
			String sql="SELECT Distinct EMPNAME,NPDEMPID FROM NPD.VW_USERFORRESOLUTION WHERE PROJECTID=:projectId";
			SQLQuery query=hibernateSession.createSQLQuery(sql);
			
			query.setLong("projectId", Long.valueOf(projectId));
			
			query.addScalar("npdempid",Hibernate.LONG)
					.addScalar("empname",Hibernate.STRING)
					.setResultTransformer( Transformers.aliasToBean(TmEmployee.class));
		
			employeeList=(ArrayList)query.list();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			String msg=ex.getMessage()
			+ " Exception occured in getUsersOfRole method of "
			+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex);
			AppConstants.NPDLOGGER.error(msg);
			throw new NpdException(msg) ;
		}
		finally
		{
			
		}
		return employeeList;
	}
}
