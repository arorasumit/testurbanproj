/**
 * 
 */
package com.ibm.ioes.npd.model;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.ibm.ioes.npd.beans.MeetingBean;
import com.ibm.ioes.npd.exception.NpdException;
import com.ibm.ioes.npd.hibernate.beans.LocationDto;
import com.ibm.ioes.npd.hibernate.beans.TmEmployee;
import com.ibm.ioes.npd.hibernate.beans.TmMeetings;
import com.ibm.ioes.npd.hibernate.beans.TmReferencedocs;
import com.ibm.ioes.npd.hibernate.beans.TtrnMeetingschedules;
import com.ibm.ioes.npd.hibernate.beans.TtrnProject;
import com.ibm.ioes.npd.hibernate.dao.BaseDaoFactory;
import com.ibm.ioes.npd.hibernate.dao.CommonBaseDao;
import com.ibm.ioes.npd.hibernate.dao.DaoConstants;
import com.ibm.ioes.npd.hibernate.dao.MeetingDao;
import com.ibm.ioes.npd.hibernate.dao.NpdUserDao;
import com.ibm.ioes.npd.hibernate.dao.repalceWKRoleEmployeeDao;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.HibernateBeansConstants;
import com.ibm.ioes.npd.utilities.NpdConnection;
import com.ibm.ioes.utilities.DbConnection;

/**
 * @author Disha
 * 
 */
public class MeetingModel extends CommonBaseModel {

	/**
	 * This method sets the mandatory Attendies List and Meeting Type value in
	 * the MeetingSchedule.jsp
	 * 
	 * @param MeetingBean
	 * @return
	 * @throws Exception
	 */

	public MeetingBean viewScheduleMeeting(MeetingBean meetingBean)
			throws Exception {
		Session hibernateSession = null;
		try {
			NpdUserDao npdUserDao = (NpdUserDao) BaseDaoFactory
					.getInstance(DaoConstants.NPD_USER_DAO);

			hibernateSession = CommonBaseDao.beginTrans();
			CommonBaseDao commonBaseDao = (CommonBaseDao) BaseDaoFactory
					.getInstance(DaoConstants.BASE_DAO_CLASS);

			meetingBean.setMeetingType(AppConstants.MEETING_TYPE_CFT);
/*			if (commonBaseDao.getAllEntriesInATable(hibernateSession,
					HibernateBeansConstants.HBM_EMPLOYEE).size() > 0)
				meetingBean.setMandatoryList(commonBaseDao
						.getAllEntriesInATable(hibernateSession,
								HibernateBeansConstants.HBM_EMPLOYEE));*/
			
			ArrayList<TtrnProject> projectList=new ArrayList<TtrnProject>();
			Connection conn=hibernateSession.connection();
			MeetingDao meetingDao=new MeetingDao();
			projectList=meetingDao.fetchProjects(conn);
			meetingBean.setProductList(projectList);
			
			/*
			ArrayList<TtrnProject> projectList = commonBaseDao.getAllEntriesInATableOrder(hibernateSession,
					HibernateBeansConstants.HBM_PROJECT,"projectid","asc");
			for (TtrnProject project : projectList) {
				project.setCSV_id_name(project.getProjectid()+" ("+project.getProjectName()+")");
			}
			
			meetingBean.setProductList(projectList);*/
			
			
			
			ArrayList<LocationDto> locationList=meetingDao.getLocationList(conn);
			conn.close();
			meetingBean.setLocationList(locationList);
			

			meetingBean.setMandatoryId(null);
			meetingBean.setDate(null);
			meetingBean.setStartTime("");
			meetingBean.setEndTime("");
			meetingBean.setSubject("");
			meetingBean.setProductId(null);
			meetingBean.setLocationId(null);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CommonBaseDao.closeTrans(hibernateSession);
		}
		return meetingBean;

	}

	/**
	 * This method sets the mandatory Attendies List and Meeting Type value in
	 * the MeetingSchedule.jsp
	 * 
	 * @param MeetingBean
	 * @return
	 * @throws Exception
	 */

	public MeetingBean saveScheduleMeeting(MeetingBean meetingBean,
			TmEmployee tmEmployee) throws Exception {
		Session hibernateSession = null;
		try {
			MeetingDao meetingDao = (MeetingDao) BaseDaoFactory
					.getInstance(DaoConstants.MEETING_DAO);

			hibernateSession = CommonBaseDao.beginTrans();
			boolean insert = false;

			meetingBean = meetingDao.saveMeetingSchedule(meetingBean,
					hibernateSession, tmEmployee);
			hibernateSession = CommonBaseDao.beginTrans();
			if (meetingBean.getMeetingId() != null
					&& !meetingBean.getMeetingId().equalsIgnoreCase(
							AppConstants.INI_VALUE)) {
				
				meetingDao.sendMailForSchedule(meetingBean, hibernateSession);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CommonBaseDao.closeTrans(hibernateSession);
		}
		return meetingBean;

	}

	/**
	 * This method sets the Mom's uploaded afor a meeting id.
	 * 
	 * @param MeetingBean
	 * @return
	 * @throws Exception
	 */

	public MeetingBean viewAddMom(MeetingBean meetingBean) throws Exception {
		Session hibernateSession = null;
		try {
			MeetingDao meetingDao = (MeetingDao) BaseDaoFactory
					.getInstance(DaoConstants.MEETING_DAO);

			hibernateSession = CommonBaseDao.beginTrans();
			TtrnMeetingschedules ttrnMeetingschedules = new TtrnMeetingschedules();
			CommonBaseDao commonBaseDao = (CommonBaseDao) BaseDaoFactory
					.getInstance(DaoConstants.BASE_DAO_CLASS);
			ArrayList list = new ArrayList();
			if (commonBaseDao.getAllEntriesInATable(hibernateSession,
					HibernateBeansConstants.HBM_MEETINGSCHEDULE).size() > 0) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
				Calendar calendar1 = Calendar.getInstance();
				Calendar calendar = Calendar.getInstance();
				if (meetingBean.getFromDate() != null
						&& !meetingBean.getFromDate().equalsIgnoreCase("")) {
					calendar.set(Calendar.HOUR_OF_DAY, 0);
					calendar.set(Calendar.MINUTE, 0);
					calendar.set(Calendar.SECOND, 0);
					calendar.setTime(sdf.parse(meetingBean.getFromDate()));
				}
				if (meetingBean.getToDate() != null
						&& !meetingBean.getToDate().equalsIgnoreCase("")) {
					calendar1.set(Calendar.HOUR_OF_DAY, 23);
					calendar1.set(Calendar.MINUTE, 59);
					calendar1.set(Calendar.SECOND, 59);
					calendar1.setTime(sdf.parse(meetingBean.getToDate()));
					//calendar1.add(Calendar.DATE, 1);
				}
				Criteria ce = hibernateSession
						.createCriteria(TtrnMeetingschedules.class);
				if (meetingBean != null
						&& meetingBean.getDocSearch() != null
						&& !meetingBean.getDocSearch().equalsIgnoreCase(
								AppConstants.INI_VALUE)) {
					ce.createAlias("tmMeetings", "tmMeetings");
					ce.add(Restrictions.ilike("tmMeetings.meetingtype",
							meetingBean.getDocSearch(), MatchMode.ANYWHERE));
				}
				if (meetingBean.getToDate() != null
						&& meetingBean.getFromDate() != null
						&& !(meetingBean.getToDate().equalsIgnoreCase("") && meetingBean
								.getFromDate().equalsIgnoreCase(""))) {

					ce.add(Restrictions.between("meetingdate", new Date(
							calendar.getTime().getTime()), new Date(calendar1
							.getTime().getTime())));
				}
				if(meetingBean.getSearchProjectId()!=null && !"".equals(meetingBean.getSearchProjectId()))
				{
					ce.createAlias("ttrnProduct", "ttrnProduct");
					ce.add(Restrictions.eq("ttrnProduct.projectid", Long.parseLong(meetingBean.getSearchProjectId())));
				}
				if(meetingBean.getSearchProjectName()!=null && !"".equals(meetingBean.getSearchProjectName()))
				{
					ce.createAlias("ttrnProduct", "ttrnProduct");
					ce.add(Restrictions.ilike("ttrnProduct.projectName", "%"+meetingBean.getSearchProjectName()+"%"));
				}
				if (ce.list() != null) {
					list = (ArrayList) ce.list();
				}
				meetingBean.setMeetingList(list);

				ttrnMeetingschedules = (TtrnMeetingschedules) commonBaseDao
						.getAllEntriesInATable(hibernateSession,
								HibernateBeansConstants.HBM_MEETINGSCHEDULE)
						.get(0);

				meetingBean.setMeetingIdCreated(new Long(ttrnMeetingschedules
						.getMeetingid()).toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CommonBaseDao.closeTrans(hibernateSession);
		}
		return meetingBean;

	}

	/**
	 * This method sets the mandatory Attendies List and Meeting Type value in
	 * the MeetingSchedule.jsp
	 * 
	 * @param MeetingBean
	 * @return
	 * @throws Exception
	 */

	public boolean saveMeetingMOM(MeetingBean meetingBean, TmEmployee tmEmployee)
			throws Exception {
		Session hibernateSession = null;
		boolean insert = false;
		try {
			MeetingDao meetingDao = (MeetingDao) BaseDaoFactory
					.getInstance(DaoConstants.MEETING_DAO);

			hibernateSession = CommonBaseDao.beginTrans();

			insert = meetingDao.saveMeetingMOM(meetingBean, hibernateSession,
					tmEmployee);
			hibernateSession = CommonBaseDao.beginTrans();
			if (insert) {
				meetingDao.sendMailForMOM(meetingBean, hibernateSession);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CommonBaseDao.closeTrans(hibernateSession);
		}

		return insert;

	}

	/**
	 * This method sets the Mom's uploaded afor a meeting id.
	 * 
	 * @param MeetingBean
	 * @return
	 * @throws Exception
	 */

	public MeetingBean viewMomHistory(MeetingBean meetingBean) throws Exception {
		Session hibernateSession = null;
		try {
			MeetingDao meetingDao = (MeetingDao) BaseDaoFactory
					.getInstance(DaoConstants.MEETING_DAO);

			hibernateSession = CommonBaseDao.beginTrans();
			CommonBaseDao commonBaseDao = (CommonBaseDao) BaseDaoFactory
					.getInstance(DaoConstants.BASE_DAO_CLASS);

			if (commonBaseDao.getAllEntriesInATable(hibernateSession,
					HibernateBeansConstants.HBM_MEETINGSCHEDULE).size() > 0) {
				meetingBean.setMeetingList(commonBaseDao.getAllEntriesInATable(
						hibernateSession,
						HibernateBeansConstants.HBM_MEETINGSCHEDULE));

			}
			meetingBean.setMeetingType(AppConstants.MEETING_TYPE_CFT);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CommonBaseDao.closeTrans(hibernateSession);
		}

		return meetingBean;

	}

	/**
	 * This method gets the MOM uploaded for meeting
	 * 
	 * @param MeetingBean
	 * @return
	 * @throws Exception
	 */

	public MeetingBean submitViewMomHistory(MeetingBean meetingBean)
			throws Exception {
		Session hibernateSession = null;
		try {
			MeetingDao meetingDao = (MeetingDao) BaseDaoFactory
					.getInstance(DaoConstants.MEETING_DAO);

			hibernateSession = CommonBaseDao.beginTrans();
			meetingBean.setMomList(meetingDao.getMomListForMeeting(meetingBean,
					hibernateSession));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CommonBaseDao.closeTrans(hibernateSession);
		}

		return meetingBean;

	}
	public MeetingBean viewMeetingSMS(MeetingBean meetingBean)
	throws Exception {
		Session hibernateSession = null;
		try {
			NpdUserDao npdUserDao = (NpdUserDao) BaseDaoFactory
					.getInstance(DaoConstants.NPD_USER_DAO);
		
			hibernateSession = CommonBaseDao.beginTrans();
			CommonBaseDao commonBaseDao = (CommonBaseDao) BaseDaoFactory
					.getInstance(DaoConstants.BASE_DAO_CLASS);
		
			
		
			
			ArrayList<TtrnProject> projectList=new ArrayList<TtrnProject>();
			Connection conn=hibernateSession.connection();
			MeetingDao meetingDao=new MeetingDao();
			projectList=meetingDao.fetchProjects(conn);
			meetingBean.setProductList(projectList);
			
			
			
			conn.close();
			
			
		
			meetingBean.setMandatoryId(null);
			meetingBean.setDate(null);
			meetingBean.setStartTime("");
			meetingBean.setEndTime("");
			meetingBean.setSubject("");
			meetingBean.setProductId(null);
			meetingBean.setLocationId(null);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CommonBaseDao.closeTrans(hibernateSession);
		}
		return meetingBean;
		
		}

	public ArrayList getMeetingsOfProduct(String productId) throws NpdException{
		ArrayList meetingList = new ArrayList();
		
		MeetingDao dao=new MeetingDao();
		Connection conn=null;
		
		try {
			conn=NpdConnection.getConnectionObject();
			meetingList = dao.getMeetingsOfProduct(conn,productId);
		} catch (Exception ex) {
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in getMeetingsOfProduct method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in getMeetingsOfProduct method of "
					+ this.getClass().getSimpleName()) ;
		}
		finally
		{
			try {
				NpdConnection.freeConnection(conn);
			} catch (Exception ex) {
				ex.printStackTrace();
				AppConstants.NPDLOGGER.error(ex.getMessage()
						+ " Exception occured in getMeetingsOfProduct method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
				throw new NpdException("Exception occured in getMeetingsOfProduct method of "
						+ this.getClass().getSimpleName()) ;
			}	
		}
		return meetingList;
	}

	public TtrnMeetingschedules getMeetingDetailById(String meetId) throws NpdException{
		TtrnMeetingschedules ttrnMeetingschedules = null;
		
		MeetingDao dao=new MeetingDao();
		Connection conn=null;
		
		try {
			conn=NpdConnection.getConnectionObject();
			ttrnMeetingschedules = dao.getMeetingDetailById(conn,meetId);
		} catch (Exception ex) {
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in getMeetingDetailById method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in getMeetingDetailById method of "
					+ this.getClass().getSimpleName()) ;
		}
		finally
		{
			try {
				NpdConnection.freeConnection(conn);
			} catch (Exception ex) {
				ex.printStackTrace();
				AppConstants.NPDLOGGER.error(ex.getMessage()
						+ " Exception occured in getMeetingDetailById method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
				throw new NpdException("Exception occured in getMeetingDetailById method of "
						+ this.getClass().getSimpleName()) ;
			}	
		}
		return ttrnMeetingschedules;
	}

}
