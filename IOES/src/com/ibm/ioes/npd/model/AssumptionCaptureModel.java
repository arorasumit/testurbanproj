package com.ibm.ioes.npd.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts.action.ActionForm;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ibm.ioes.npd.beans.AssumptionCaptureBean;
import com.ibm.ioes.npd.beans.IssuesCaptureBean;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjectassumptions;
import com.ibm.ioes.npd.hibernate.beans.TmEmployee;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjectisssues;
import com.ibm.ioes.npd.hibernate.dao.BaseDaoFactory;
import com.ibm.ioes.npd.hibernate.dao.CommonBaseDao;
import com.ibm.ioes.npd.hibernate.dao.DaoConstants;
import com.ibm.ioes.npd.hibernate.dao.AssumptionCaptureDao;
import com.ibm.ioes.npd.hibernate.dao.IssuesCaptureDao;
import com.ibm.ioes.npd.hibernate.dao.NpdUserDao;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.HibernateBeansConstants;

/**
 * @author Sanjay
 * 
 */

public class AssumptionCaptureModel  extends CommonBaseModel
{
	public AssumptionCaptureBean viewAssumption(AssumptionCaptureBean captureBean,String assumptionId,TmEmployee tmEmployee) throws Exception 
	{
		Session hibernateSession = null;
		AssumptionCaptureDao assumptionCaptureDao=(AssumptionCaptureDao) BaseDaoFactory.getInstance(DaoConstants.ASSUMPTION_CAPTURE_DAO);
		CommonBaseDao commonBaseDao = new CommonBaseDao();
		TtrnProjectassumptions TTRN_PROJECTASSUMPTIONS= new TtrnProjectassumptions();
		hibernateSession = CommonBaseDao.beginTrans();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				AppConstants.DATE_FORMAT_PROC);
		try
		{
			if (assumptionId != null && !assumptionId.equalsIgnoreCase(AppConstants.INI_VALUE)) 
			{
				TTRN_PROJECTASSUMPTIONS = (TtrnProjectassumptions) commonBaseDao.findById(new Long(assumptionId).longValue(),
						HibernateBeansConstants.HBM_PROJECT_Assumption,hibernateSession);
				captureBean.setDescription(TTRN_PROJECTASSUMPTIONS.getDescription());
				captureBean.setImpact(TTRN_PROJECTASSUMPTIONS.getImpact());
				if (TTRN_PROJECTASSUMPTIONS.getTtrnProject() != null)
					captureBean.setProjectId(new Long(TTRN_PROJECTASSUMPTIONS.getTtrnProject().getProjectid()).toString());
				captureBean.setRaisedon(simpleDateFormat.format(TTRN_PROJECTASSUMPTIONS.getRaiseddate()));
				captureBean.setAssumptionID(assumptionId);
			}
			else
			{
				captureBean.setAssumptionID(null);
				captureBean.setDescription(AppConstants.INI_VALUE);
				if (captureBean.getProjectId() == null
						|| captureBean.getProjectId().equalsIgnoreCase(
								AppConstants.INI_VALUE))
					captureBean.setProjectId(null);
				captureBean.setRaisedon(simpleDateFormat.format(new Date()));
				captureBean.setImpact(AppConstants.SELECT_OPTION);
			}
			captureBean.setProjectList(assumptionCaptureDao.getProjects(hibernateSession,tmEmployee));
			Criteria ce=hibernateSession.createCriteria(HibernateBeansConstants.HBM_PROJECT);
			ce.add(Restrictions.eq("projectstatus", new Integer(1)));
			ce.addOrder(Order.asc("projectid"));
			List list=ce.list();
			if(list!=null)
			{
				captureBean.setSearchProjectList((ArrayList)list);
			}
		}
		catch (Exception ex) 
		{
			ex.printStackTrace();
		} finally 
		{
			CommonBaseDao.closeTrans(hibernateSession);
		}
		return captureBean;
	}

	public boolean addAssumption(AssumptionCaptureBean captureBean,	TmEmployee tmEmployee) throws Exception 
	{
		AssumptionCaptureDao assumptionCaptureDao = (AssumptionCaptureDao) BaseDaoFactory.getInstance(DaoConstants.ASSUMPTION_CAPTURE_DAO);
		CommonBaseDao commonBaseDao = new CommonBaseDao();
		boolean insert = assumptionCaptureDao.addAssumption(captureBean, tmEmployee);
		return insert;

	}

	public AssumptionCaptureBean getAssumptionForAProject(AssumptionCaptureBean captureBean) 
	{
		Session hibernateSession = null;
		AssumptionCaptureDao assumptionCaptureDao = (AssumptionCaptureDao) BaseDaoFactory.getInstance(DaoConstants.ASSUMPTION_CAPTURE_DAO);
		String projectId = captureBean.getProjectId();

		try 
		{
			hibernateSession = CommonBaseDao.beginTrans();
			captureBean.setAssumptionDetailList(assumptionCaptureDao.getAssumptionForAProject(captureBean, hibernateSession));
			captureBean.setProjectId(projectId);

		} catch (Exception ex) {
			ex.printStackTrace();
		}finally {
			CommonBaseDao.closeTrans(hibernateSession);
		}
		return captureBean;
	}
	
	public AssumptionCaptureBean getAssumptionForSelectedProject(AssumptionCaptureBean captureBean) 
	{
		Session hibernateSession = null;
		AssumptionCaptureDao assumptionCaptureDao = (AssumptionCaptureDao) BaseDaoFactory.getInstance(DaoConstants.ASSUMPTION_CAPTURE_DAO);
		String projectId = captureBean.getProjectId();
		String projectName = captureBean.getProjectName();
		try 
		{
			hibernateSession = CommonBaseDao.beginTrans();
			captureBean.setAssumptionDetailList(assumptionCaptureDao.getAssumptionForSelectedProject(captureBean, hibernateSession));
			captureBean.setProjectId(projectId);
			captureBean.setProjectName(projectName);

		} catch (Exception ex) {
			ex.printStackTrace();
		}finally {
			CommonBaseDao.closeTrans(hibernateSession);
		}
		return captureBean;
	}

}
