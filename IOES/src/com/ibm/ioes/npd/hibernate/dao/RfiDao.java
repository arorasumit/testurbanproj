/**
 * 
 */
package com.ibm.ioes.npd.hibernate.dao;

import java.math.BigInteger;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.log4j.Category;
import org.apache.struts.upload.FormFile;
import org.apache.tools.ant.Project;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.ibm.ioes.npd.beans.ReferenceDocBean;
import com.ibm.ioes.npd.beans.RfiBean;
import com.ibm.ioes.npd.hibernate.beans.TmEmployee;
import com.ibm.ioes.npd.hibernate.beans.TmRfiClarification;
import com.ibm.ioes.npd.hibernate.beans.TmRfiDocument;
import com.ibm.ioes.npd.hibernate.beans.TtrnProject;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.HibernateBeansConstants;
import com.ibm.ioes.npd.utilities.NpdConnection;
import com.ibm.ioes.npd.utilities.PagingSorting;
import com.ibm.ioes.utilities.DbConnection;

/**
 * @author Sanjay
 * 
 */
public class RfiDao extends CommonBaseDao {

	private Category logger;

	public ArrayList getPendingRfi(RfiBean rfiBean, TmEmployee tmEmployee,
			Session hibernateSession) {

		ArrayList list = new ArrayList();
		ArrayList pendingRfiList = new ArrayList();
	try
	{
		int counter = 0;
		CommonBaseDao commonBaseDao = BaseDaoFactory
				.getInstance(DaoConstants.BASE_DAO_CLASS);
		
		PagingSorting pagingSorting = rfiBean.getPagingSorting();
		if (pagingSorting == null) 
		{
			pagingSorting = new PagingSorting();
			rfiBean.setPagingSorting(pagingSorting);
		}
		pagingSorting.setPagingSorting(true, true);

		pagingSorting.setDefaultifNotPresent("employeeName",
				PagingSorting.increment, 1);

		pagingSorting.setMode("hibernate");
		SQLQuery query = hibernateSession
				.createSQLQuery("SELECT ASSIGNEDTOUSERID,PROJECTRFIID, PROJECTID, STAGENAME, TASKNAME, WITHCURRENTOWNER ,CURRENTSTAGEID,CURRENTTASKID ,PRODCATDESC ,ACTIONRFIREMARKS , PROJECTWORKFLOWID FROM NPD.V_RFI_DETAIL_LIST where ACTIONRFIRAISEDTO_ROLE = "+ tmEmployee.getCurrentRoleId() +" AND ACTIONRFIRAISEDTO = "+tmEmployee.getNpdempid());
		list = (ArrayList) query.list();
		if (list != null && list.size() > 0) {
			pagingSorting.setRecordCount(list.size());
		}
		// TODO Change Made BY Sumit on 08-Mar-2010 (Start)
		else
			pagingSorting.setRecordCount(1);
		//	Change Made BY Sumit on 08-Mar-2010	(End)
	

		SQLQuery query1 = hibernateSession
				.createSQLQuery("SELECT TAB.* FROM (SELECT ASSIGNEDTOUSERID,PROJECTRFIID, PROJECTID, STAGENAME, " +
						"TASKNAME, WITHCURRENTOWNER ,CURRENTSTAGEID,CURRENTTASKID ,PRODCATDESC ,ACTIONRFIREMARKS , " +
						"PROJECTWORKFLOWID,ROW_NUMBER()over(ORDER BY SNO)As SNO FROM NPD.V_RFI_DETAIL_LIST where ACTIONRFIRAISEDTO_ROLE = "
						+ tmEmployee.getCurrentRoleId() +" AND ACTIONRFIRAISEDTO = "+tmEmployee.getNpdempid()
						+") AS TAB WHERE SNO between "
						+ pagingSorting.getStartRecordId()
						+ " and "
						+ (pagingSorting.getStartRecordId() + pagingSorting
								.getPageRecords()));
		list = (ArrayList) query1.list();

		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object obj[] = null;
				obj = (Object[]) list.get(i);
				if (obj[0] != null) {
					TtrnProject project = null;
						BigInteger assignedToUser = (BigInteger) (obj[0]);
					
						BigInteger projectId = (BigInteger) (obj[2]);
						BigInteger rfiId = (BigInteger) (obj[1]);
						String currentTask = (String) (obj[4]);
						String currentStage = (String) (obj[3]);
						Integer daysWithCurrentUser = (Integer) (obj[5]);
						BigInteger stageId = (BigInteger) (obj[6]);
						BigInteger taskId = (BigInteger) (obj[7]);
						BigInteger projectworkflowid = (BigInteger) (obj[10]);
						project = new TtrnProject();
						TtrnProject dbproject = (TtrnProject) commonBaseDao.findById(
								projectId.longValue(),
								HibernateBeansConstants.HBM_PROJECT,
								hibernateSession);
						
						BeanUtils.copyProperties(project, dbproject);
						
						
						project.setAssignedToUser(assignedToUser.toString());
						project.setRfiId(rfiId.toString());
						project.setCurrentstatus(currentStage);
						project.setCurrentTask(currentTask);
						project.setDaysWithCurrentUser(daysWithCurrentUser
								.toString());
						project.setStageId(stageId.toString());
						project.setTaskId(taskId.toString());
						project.setProductCatDesc((String) (obj[8]));
						project.setRfiRemarks((String) (obj[9]));
						project.setProjectworkflowid(Long.parseLong(projectworkflowid.toString()));
						pendingRfiList.add(project);
								

				}

			}
		}
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
	}
		return pendingRfiList;
	}

	public ArrayList getPendingRfiNpdSpocMail(RfiBean rfiBean, TmEmployee tmEmployee,
			Session hibernateSession) {

		ArrayList list = new ArrayList();
	
		int counter = 0;
		CommonBaseDao commonBaseDao = BaseDaoFactory
				.getInstance(DaoConstants.BASE_DAO_CLASS);
		ArrayList pendingRfiList = new ArrayList();
		
		
	

		SQLQuery query1 = hibernateSession
				.createSQLQuery("SELECT ASSIGNEDTOUSERID,PROJECTRFIID, PROJECTID, STAGENAME, TASKNAME, " +
						"WITHCURRENTOWNER ,CURRENTSTAGEID,CURRENTTASKID ,PRODCATDESC ,ACTIONRFIREMARKS , " +
						"PROJECTWORKFLOWID,ACTIONRFIRAISEDTO_ROLE FROM NPD.V_RFI_DETAIL_LIST where ACTIONRFIRAISEDTO = "
						+tmEmployee.getNpdempid());
				
		list = (ArrayList) query1.list();

		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object obj[] = null;
				obj = (Object[]) list.get(i);
				if (obj[0] != null) {
					TtrnProject project = null;
						BigInteger assignedToUser = (BigInteger) (obj[0]);
					
						BigInteger projectId = (BigInteger) (obj[2]);
						BigInteger rfiId = (BigInteger) (obj[1]);
						String currentTask = (String) (obj[4]);
						String currentStage = (String) (obj[3]);
						Integer daysWithCurrentUser = (Integer) (obj[5]);
						BigInteger stageId = (BigInteger) (obj[6]);
						BigInteger taskId = (BigInteger) (obj[7]);
						BigInteger projectworkflowid = (BigInteger) (obj[10]);
						BigInteger roleid = (BigInteger) (obj[11]);
						project = new TtrnProject();
						project = (TtrnProject) commonBaseDao.findById(
								projectId.longValue(),
								HibernateBeansConstants.HBM_PROJECT,
								hibernateSession);
						project.setAssignedToUser(assignedToUser.toString());
						project.setRfiId(rfiId.toString());
						project.setCurrentstatus(currentStage);
						project.setCurrentTask(currentTask);
						project.setDaysWithCurrentUser(daysWithCurrentUser
								.toString());
						project.setStageId(stageId.toString());
						project.setTaskId(taskId.toString());
						project.setProductCatDesc((String) (obj[8]));
						project.setRfiRemarks((String) (obj[9]));
						project.setProjectworkflowid(Long.parseLong(projectworkflowid.toString()));
						project.setRoleId(Long.parseLong(roleid.toString()));
						pendingRfiList.add(project);
								

				}

			}
		}

		return pendingRfiList;
	}
	public boolean saveReferenceDoc(RfiBean rfiBean, TtrnProject project,
			Session hibernateSession, TmEmployee user) throws Exception {

		boolean insert = true;
		CommonBaseDao commonBaseDao = BaseDaoFactory
				.getInstance(DaoConstants.BASE_DAO_CLASS);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		java.sql.Date sqltDate = new java.sql.Date(new Date().getTime());
		CallableStatement proc = null;
		try {

			rfiBean = getRfiDetails(rfiBean, user, hibernateSession);

			proc = hibernateSession
					.connection()
					.prepareCall(
							" {CALL NPD.P_INSERT_TRNPROJECTACTIONDETAILS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)} ");
			proc.setLong(1, rfiBean.getProjectId());
			proc.setLong(2, Long.parseLong(rfiBean.getStageId()));
			proc.setLong(3, Long.parseLong(rfiBean.getTaskId()));
			proc.setString(4, null);
			proc.setLong(5, Integer.parseInt(rfiBean.getAssignedToUser()));
			proc.setDate(6, sqltDate);
			proc.setString(7, rfiBean.getRfiClarification());
			if (rfiBean.getAttachment() != null
					&& rfiBean.getAttachment().getFileSize() > 0) {
				proc.setLong(8, 1);
			} else {
				proc.setLong(8, 0);
			}

			proc.setLong(9, 4);
			proc.setLong(10, new Long(rfiBean.getRfiId()));// use this as rfi Id when ACTIONTYPE_I=4
			proc.setLong(11, 0);
			proc.setLong(12, user.getNpdempid());
			proc.setLong(13, user.getNpdempid());
			proc.setLong(14, AppConstants.ACTIVE_FLAG);
			// Uploaded document related setting
			java.sql.Blob blob = null;

			if (rfiBean.getAttachment() != null
					&& rfiBean.getAttachment().getFileSize() > 0) {
				FormFile file = rfiBean.getAttachment();
				proc.setBlob(15, Hibernate.createBlob(file.getFileData()));
				proc.setLong(16, 1);
				proc.setString(17, file.getFileName());
				proc.setString(18, file.getFileName().substring(file.getFileName().lastIndexOf(".") + 1));
			} else {
				Blob blob2 = null;
				proc.setBlob(15, blob2);
				proc.setLong(16, 0);
				proc.setString(17, null);
				proc.setString(18, null);
			}
			if (rfiBean.getAssignedToUser() != null) {
				proc.setLong(19, Integer.parseInt(rfiBean.getAssignedToUser()));
			} else {
				proc.setInt(19, 0);
			}
			// Email Related Setting
			proc.setLong(20, 0);
			proc.setString(21, null);
			proc.setString(22, null);
			proc.setString(23, null);
			proc.setString(24, null);
			proc.setString(25, null);
			proc.setString(26, null);
			proc.setString(27, null);
			//
			proc.setString(28, "0");
			proc.setString(29, "BLANCK");
			proc.setString(30,"");
			proc.setString(31,"");

			proc.execute();
			System.out.println(proc.getInt(28));
			System.out.println(proc.getString(29));

			String Msg = proc.getString(28);
			// Verifiying that exception accured or not in Stord Proc
			if (proc.getString(28).equals("66666")) {
				insert = false;
				hibernateSession.getTransaction().rollback();

			} else {
				commonBaseDao.closeTrans(hibernateSession);
			}

		} catch (Exception e) {
			e.printStackTrace();
			insert = false;
			hibernateSession.getTransaction().rollback();

		}

		return insert;

	}

	public boolean saveClarification(RfiBean rfiBean, TtrnProject project,
			Session hibernateSession, TmEmployee user) throws Exception {

		boolean insert = true;
		CommonBaseDao commonBaseDao = BaseDaoFactory
				.getInstance(DaoConstants.BASE_DAO_CLASS);
		try {
			if (rfiBean.getRfiClarification() != null
					&& !rfiBean.getRfiClarification().equalsIgnoreCase(
							AppConstants.INI_VALUE)) {

				TmRfiClarification rfiClarification = new TmRfiClarification();

				rfiClarification.setProjectId(Integer.parseInt((project
						.getProjectid() + "")));
				rfiClarification.setCreatedby(user.getNpdempid());
				rfiClarification.setCreateddate(new Date());
				rfiClarification.setRefclarificationdesc(rfiBean
						.getRfiClarification());

				commonBaseDao.attachDirty(rfiClarification, hibernateSession);
			}
		} catch (Exception e) {
			insert = false;
			hibernateSession.getTransaction().rollback();
			e.printStackTrace();
		} finally {

			commonBaseDao.closeTrans(hibernateSession);
		}

		return insert;

	}

	public TtrnProject getProject(int projectId, Session hibernateSession) {

		TtrnProject project = null;
		Iterator itr = null;

		Criteria ce = hibernateSession.createCriteria(TtrnProject.class);
		ce.add(Restrictions.eq("projectid", new Long(projectId)));

		if (ce.list() != null) {

			itr = ce.list().iterator();
			if (itr != null) {

				project = (TtrnProject) itr.next();

			}

		}

		return project;
	}

	public RfiBean getRfiDetails(RfiBean rfiBean, TmEmployee tmEmployee,
			Session hibernateSession) {

		ArrayList list = new ArrayList();
		SQLQuery query = hibernateSession
				.createSQLQuery("SELECT ASSIGNEDTOUSERID,PROJECTRFIID, PROJECTID, STAGENAME, TASKNAME, WITHCURRENTOWNER ,CURRENTSTAGEID,CURRENTTASKID ,PRODCATDESC ,ACTIONRFIREMARKS,UPLOADED_DOCUMENT,DOCNAME,PROJECTWORKFLOWID,ACTIONRFIRAISEDTO FROM NPD.V_RFI_DETAIL_LIST");
		list = (ArrayList) query.list();

		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object obj[] = (Object[]) list.get(i);

				if (obj[0] != null) {
					Integer raisedTo = (Integer) (obj[13]);
					if (raisedTo.longValue() == tmEmployee.getNpdempid()) {
						BigInteger rfiId = (BigInteger) (obj[1]);
						String currentTask = (String) (obj[4]);
						String currentStage = (String) (obj[3]);
						Integer daysWithCurrentUser = (Integer) (obj[5]);
						BigInteger stageId = (BigInteger) (obj[6]);
						BigInteger taskId = (BigInteger) (obj[7]);
						BigInteger projectworkflowid = (BigInteger) (obj[12]);
						if (rfiId.intValue() == rfiBean.getRfiId()) {

							rfiBean
									.setAssignedToUser(raisedTo
											.toString());
							rfiBean.setRfiId(rfiId.intValue());
							rfiBean.setCurrentstatus(currentStage);
							rfiBean.setCurrentTask(currentTask);
							rfiBean.setDaysWithCurrentUser(daysWithCurrentUser
									.toString());
							rfiBean.setStageId(stageId.toString());
							rfiBean.setTaskId(taskId.toString());
							rfiBean.setProductCatDesc((String) (obj[8]));
							rfiBean.setRfiRemarks((String) (obj[9]));
							if (obj[10] != null)
								rfiBean.setRfiDocument((Blob) (obj[10]));
							rfiBean.setRfiDocumentName((String) (obj[11]));
							rfiBean.setProjectworkflowid(projectworkflowid.toString());
						}

					}
				}
			}
		}
		return rfiBean;
	}
	public int getPendingRFICount(String userId,String roleID) {
		java.sql.Connection connection = null;
		CallableStatement proc = null;
		ResultSet resultSet = null;
		int taskPending_mytodoList = 0;
		try {
			connection = NpdConnection.getConnectionObject();

			StringBuffer query = new StringBuffer();
			query.append(" SELECT COUNT(*) AS PENDING_RFI_COUNT");
			query.append("  FROM NPD.V_RFI_DETAIL_LIST ");
			query.append(" WHERE ACTIONRFIRAISEDTO = " + userId + " AND ACTIONRFIRAISEDTO_ROLE=" + roleID);

			//System.out.println("QUERY-->" + query.toString());
			connection.setAutoCommit(false);

			proc = connection.prepareCall(query.toString());
			// proc.setLong(1,objProjectPlan.getTaskid());
			resultSet = proc.executeQuery();
			while (resultSet.next()) {
				taskPending_mytodoList = resultSet.getInt("PENDING_RFI_COUNT");
			}

			// return fileByte;
		} catch (Exception ex) {
			logger
					.error(ex.getMessage()
							+ " Exception occured while closing database objects in myToDoList method of "
							+ this.getClass().getSimpleName());
			// throw new NpdException("Exception : " + ex.getMessage(), ex);
		} finally {

			try {
				NpdConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				// throw new NpdException("Exception : " + e.getMessage(), e);
			}
		}
		return taskPending_mytodoList;
	}

}
