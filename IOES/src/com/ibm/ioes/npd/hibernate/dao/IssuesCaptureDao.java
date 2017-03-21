/**
 * 
 */
package com.ibm.ioes.npd.hibernate.dao;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import com.ibm.ioes.npd.beans.UserNpdSpocs;
import com.ibm.ioes.npd.exception.NpdException;
import com.ibm.ioes.npd.hibernate.beans.TmEmployee;
import com.ibm.ioes.npd.hibernate.beans.TmRoleempmapping;
import com.ibm.ioes.npd.hibernate.beans.TmRoles;
import com.ibm.ioes.npd.hibernate.beans.TtrnProject;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjectisssues;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.HibernateBeansConstants;
import com.ibm.ioes.npd.utilities.PagingSorting;

/**
 * @author Sanjay
 * 
 */
public class IssuesCaptureDao extends CommonBaseDao {

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
						//	.longValue(), HibernateBeansConstants.HBM_PROJECT,
						//	hibernateSession);
					//project.setAssignedToUser(tmEmployee.getEmpname());
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

	// Gets a List of Issues rearding a Project
	public ArrayList getIssuesForAProject(IssuesCaptureBean captureBean,
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
		Criteria ce = hibernateSession.createCriteria(TtrnProjectisssues.class);
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

	// This method saves Entries of an Issue related to a project in DB.

	public boolean addIssues(IssuesCaptureBean captureBean,
			TmEmployee tmEmployee) throws Exception {

		Session hibernateSession = null;
		hibernateSession = CommonBaseDao.beginTrans();
		CommonBaseDao commonBaseDao = BaseDaoFactory
				.getInstance(DaoConstants.BASE_DAO_CLASS);
		TtrnProjectisssues ttrnProjectisssues = new TtrnProjectisssues();
		boolean insert = true;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				AppConstants.DATE_FORMAT_PROC);

		try {
			if (captureBean.getIssueID() != null
					&& !captureBean.getIssueID().equalsIgnoreCase(
							AppConstants.INI_VALUE)) {
				ttrnProjectisssues = (TtrnProjectisssues) commonBaseDao
						.findById(new Long(captureBean.getIssueID())
								.longValue(),
								HibernateBeansConstants.HBM_PROJECT_ISSUE,
								hibernateSession);
				ttrnProjectisssues.setModifiedby(new Long(tmEmployee
						.getNpdempid()));
				ttrnProjectisssues.setModifieddate(new Date());
			} else {

				ttrnProjectisssues.setCreatedby(tmEmployee.getNpdempid());
				ttrnProjectisssues.setCreateddate(new Date());
				ttrnProjectisssues.setRaisedby(tmEmployee);
				ttrnProjectisssues.setRaiseddate(simpleDateFormat
						.parse(captureBean.getRaisedon()));

				ttrnProjectisssues.setTtrnProject((TtrnProject) commonBaseDao
						.findById(new Long(captureBean.getProjectId())
								.longValue(),
								HibernateBeansConstants.HBM_PROJECT,
								hibernateSession));

			}
			ttrnProjectisssues.setIsactive(AppConstants.ACTIVE_FLAG);
			if (captureBean.getActualReslDate() != null
					&& !captureBean.getActualReslDate().equalsIgnoreCase(
							AppConstants.INI_VALUE))
				ttrnProjectisssues.setActualresolutiondate(simpleDateFormat
						.parse(captureBean.getActualReslDate()));
			ttrnProjectisssues.setIssuedesc(captureBean.getDescription());
			ttrnProjectisssues.setPreposedresolutionsteps(captureBean
					.getReslutionSteps());
			ttrnProjectisssues.setPriority(captureBean.getPriority());
			ttrnProjectisssues.setResolutiondate(simpleDateFormat
					.parse(captureBean.getPlannedReslDate()));
			if (captureBean.getTimeframe() != null
					&& !captureBean.getTimeframe().equalsIgnoreCase(
							AppConstants.INI_VALUE))
				ttrnProjectisssues.setResolutiontime(Integer
						.parseInt(captureBean.getTimeframe()));
			if (captureBean.getResolutionOwner() != null
					&& !captureBean.getResolutionOwner().equalsIgnoreCase(
							AppConstants.INI_VALUE))
				ttrnProjectisssues
						.setResolutionowner((TmEmployee) commonBaseDao
								.findById(new Long(captureBean
										.getResolutionOwner()).longValue(),
										HibernateBeansConstants.HBM_EMPLOYEE,
										hibernateSession));
			if (captureBean.getStatus() != null
					&& !captureBean.getStatus().equalsIgnoreCase(
							AppConstants.INI_VALUE))
				ttrnProjectisssues.setStatus(Integer.parseInt(captureBean
						.getStatus()));
			commonBaseDao.attachDirty(ttrnProjectisssues, hibernateSession);

		} catch (Exception e) {
			insert = false;
			hibernateSession.getTransaction().rollback();
			e.printStackTrace();
		} finally {

			commonBaseDao.closeTrans(hibernateSession);
		}

		return insert;

	}
	
	
	public ArrayList getUsersOfResolution(String projectId,Session hibernateSession) throws Exception 
	{
		ArrayList employeeList=new  ArrayList();
		
		try
		{
						
			String sql="SELECT Distinct EMPNAME,NPDEMPID	FROM NPD.VW_USERFORRESOLUTION WHERE PROJECTID=:projectId";
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
