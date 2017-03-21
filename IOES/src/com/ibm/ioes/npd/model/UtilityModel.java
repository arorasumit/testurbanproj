package com.ibm.ioes.npd.model;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import com.ibm.ioes.npd.exception.NpdException;
import com.ibm.ioes.npd.hibernate.beans.TtrnProject;
import com.ibm.ioes.npd.hibernate.dao.BaseDaoFactory;
import com.ibm.ioes.npd.hibernate.dao.CommonBaseDao;
import com.ibm.ioes.npd.hibernate.dao.DaoConstants;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.HibernateBeansConstants;

public class UtilityModel {

	private static final Logger log;
	static {
		log = Logger.getLogger(UtilityModel.class);
	}
	
	public ArrayList fetchAllNpdCategoryList11() throws NpdException 
	{
		try
		{
		ArrayList npdCategoryList=null;
		Session hibernateSession = null;
		hibernateSession = CommonBaseDao.beginTrans();
		CommonBaseDao commonBaseDao = (CommonBaseDao) BaseDaoFactory
		.getInstance(DaoConstants.BASE_DAO_CLASS);
		
		npdCategoryList = commonBaseDao.getAllEntriesInATable(hibernateSession,
				HibernateBeansConstants.HBM_NPD_CATEGORY);
		
		return npdCategoryList;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			log.error(ex.getMessage()
					+ " Exception occured in fetchAllNpdCategoryList method of "
					+ this.getClass().getSimpleName());
			throw new NpdException("Exception : " + ex.getMessage(), ex);
		}
	}
	
	public ArrayList fetchAllPrdCategoryList11() throws NpdException 
	{
		try
		{
		ArrayList prdCategoryList=null;
		Session hibernateSession = null;
		hibernateSession = CommonBaseDao.beginTrans();
		CommonBaseDao commonBaseDao = (CommonBaseDao) BaseDaoFactory
		.getInstance(DaoConstants.BASE_DAO_CLASS);
		
		prdCategoryList = commonBaseDao.getAllEntriesInATableOrder(hibernateSession,
				HibernateBeansConstants.HBM_PRD_CATEGORY,"prodcatdesc","asc");
		
		return prdCategoryList;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			log.error(ex.getMessage()
					+ " Exception occured in fetchAllPrdCategoryList method of "
					+ this.getClass().getSimpleName());
			throw new NpdException("Exception : " + ex.getMessage(), ex);
		}
	}

	/*public ArrayList<TtrnProject> fetchProjects(TtrnProject searchProjectDto)throws NpdException {
		try
		{
		ArrayList<TtrnProject> projectList=null;
		Session hibernateSession = null;
		hibernateSession = CommonBaseDao.beginTrans();
		UtilityDao daoObj = new UtilityDao();
		
		projectList=daoObj.fetchProjects(hibernateSession,searchProjectDto);
		
		return projectList;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			log.error(ex.getMessage()
					+ " Exception occured in fetchAllProjects method of "
					+ this.getClass().getSimpleName());
			throw new NpdException("Exception : " + ex.getMessage(), ex);
		}
	}
*/
	/*public AttachmentDto getDocument(AttachmentDto attachmentDto) throws NpdException {
		Session hibernateSession = null;
		
		AttachmentDto dto;
		try {
			hibernateSession = CommonBaseDao.beginTrans();
			UtilityDao daoObj=new UtilityDao();
			dto=daoObj.getDocument(hibernateSession,attachmentDto);
			
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
			log.error(ex.getMessage()
			+ " SQLException occured in getDocument method of "
			+ this.getClass().getSimpleName());
			throw new NpdException();
	}
		return dto;
	}*/

	public static TtrnProject getProjectDetailById (String projectId) throws NpdException
	{
		TtrnProject projectDto=null;
		Session hibernateSession = null;
		try
		{
			hibernateSession = CommonBaseDao.beginTrans();
			CommonBaseDao daoObj=new CommonBaseDao();
			projectDto=(TtrnProject)daoObj.findById(Long.parseLong(projectId),HibernateBeansConstants.HBM_PROJECT, hibernateSession);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(ex));
			throw new NpdException("Exception : " + ex.getMessage(), ex);
	
		} finally {
			CommonBaseDao.closeTrans(hibernateSession);
		}
		return projectDto;
	}
}
