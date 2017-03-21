package com.ibm.ioes.npd.hibernate.dao;

import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ibm.ioes.npd.beans.MeetingBean;
import com.ibm.ioes.npd.beans.ReferenceDocBean;
import com.ibm.ioes.npd.beans.UserNpdSpocs;
import com.ibm.ioes.npd.hibernate.beans.TmEmpescalationmatrix;
import com.ibm.ioes.npd.hibernate.beans.TmEmployee;
import com.ibm.ioes.npd.hibernate.beans.TmMeetings;
import com.ibm.ioes.npd.hibernate.beans.TmReferencedocs;
import com.ibm.ioes.npd.hibernate.beans.TmRoleempmapping;
import com.ibm.ioes.npd.hibernate.beans.TmRoles;
import com.ibm.ioes.npd.hibernate.beans.TtrnMeetingattendies;
import com.ibm.ioes.npd.hibernate.beans.TtrnMeetingschedules;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.HibernateBeansConstants;
import com.ibm.ioes.npd.utilities.PagingSorting;
import com.ibm.ioes.utilities.DbConnection;

/**
 * @author Disha
 * @version 1.0
 */

public class ReferenceDocDao extends CommonBaseDao {

	// This method add/update the Reference Doc in the database.

	public boolean saveReferenceDoc(ReferenceDocBean referenceDocBean,
			TmEmployee tmEmployee, Session hibernateSession) throws Exception {

		boolean insert = true;
		CommonBaseDao commonBaseDao = BaseDaoFactory
				.getInstance(DaoConstants.BASE_DAO_CLASS);
		TmReferencedocs referencedocs = new TmReferencedocs();
		String[] referenceDocId = null;
		Connection conn=null;
		try {
			// For updating the reference Doc.
			if (referenceDocBean.getReferenceDocId() != null
					&& referenceDocBean.getReferenceDocId().length > 0) {

				int size = referenceDocBean.getReferenceDocId().length;
				referenceDocId = new String[size];
				referenceDocId = referenceDocBean.getReferenceDocId();
				Query query = hibernateSession
						.createQuery("UPDATE TmReferencedocs referencedocs set referencedocs.isactive=:isactive where referencedocs.prev_doc_id=:prev_doc_id or referencedocs.refdocid=:prev_doc_id");
				query.setInteger("isactive", AppConstants.INACTIVE_FLAG);
				query.setLong("prev_doc_id", Long.parseLong(referenceDocId[0]));
				query.executeUpdate();

				// For adding a new reference Doc.
				if (referenceDocBean.getAttachment() != null) {
					if (tmEmployee != null)
						referencedocs.setCreatedby(tmEmployee);
					referencedocs.setCreateddate(new Date());
					byte[] FileData = referenceDocBean.getAttachment()
							.getFileData();
					Blob b = Hibernate.createBlob(FileData);
					referencedocs.setRefdoc(b);
					referencedocs.setRefdocname(referenceDocBean
							.getRefDocName());
					if (referenceDocId != null) {
						referencedocs.setPrev_doc_id(Long
								.parseLong(referenceDocId[0]));
					} else {
						referencedocs.setPrev_doc_id(Long
								.parseLong(AppConstants.NO_PREVIOUS_OPTION));
					}
					referencedocs.setIsactive(AppConstants.ACTIVE_FLAG);
					referencedocs.setActualRefDocName(referenceDocBean.getAttachment().getFileName());
					commonBaseDao.attachDirty(referencedocs, hibernateSession);
					
					//call procedure to replce old id by new id
					if(referencedocs.getPrev_doc_id()!=0)
					{
						conn=hibernateSession.connection();
						conn.setAutoCommit(false);
						CallableStatement cstmt = conn.prepareCall(
							"{call NPD.P_UPDATE_REFERENCEDOCID(?,?,?)}");
						cstmt.setLong(1,referencedocs.getPrev_doc_id() );
						cstmt.setLong(2,referencedocs.getRefdocid() );
						cstmt.setString(3, "");
						
						cstmt.execute();
						
						
						
					}
									
				}
			} else {
				// For adding a new reference Doc.
				if (referenceDocBean.getAttachment() != null) {
					if (tmEmployee != null)
						referencedocs.setCreatedby(tmEmployee);
					referencedocs.setCreateddate(new Date());
					byte[] FileData = referenceDocBean.getAttachment()
							.getFileData();
					Blob b = Hibernate.createBlob(FileData);
					referencedocs.setRefdoc(b);
					referencedocs.setRefdocname(referenceDocBean
							.getRefDocName());
					if (referenceDocId != null) {
						referencedocs.setPrev_doc_id(Long
								.parseLong(referenceDocId[0]));
					} else {
						referencedocs.setPrev_doc_id(Long
								.parseLong(AppConstants.NO_PREVIOUS_OPTION));
					}
					referencedocs.setIsactive(AppConstants.ACTIVE_FLAG);
					referencedocs.setActualRefDocName(referenceDocBean.getAttachment().getFileName());
					commonBaseDao.attachDirty(referencedocs, hibernateSession);
				}
			}

		} catch (Exception e) {
			insert = false;
			conn.rollback();
			conn.close();
			hibernateSession.getTransaction().rollback();
			e.printStackTrace();

		} finally {
			commonBaseDao.closeTrans(hibernateSession);
		}

		return insert;

	}

	
	private class MyComparator implements Comparator<TmReferencedocs>
	{

		public int compare(TmReferencedocs o1, TmReferencedocs o2) {
			
			if(o1.getRefdocid()<o2.getRefdocid())
			{return -1;
			}
			else if(o1.getRefdocid()>o2.getRefdocid())
			{	return 1;
			}
			{
				return 1;
			}
			
		}
		
	}
	
	public TreeMap getActiveRefDoc(ReferenceDocBean referenceDocBean,
			Session hibernateSession) throws Exception {

		ArrayList list = new ArrayList();
		ArrayList activeRefdocList = new ArrayList();
		ArrayList inactiveRefdocList = null;
		TmReferencedocs tmReferencedocs = new TmReferencedocs();
		TmReferencedocs tmReferencedocs2 = new TmReferencedocs();
		TreeMap<TmReferencedocs, ArrayList> hashMap = new TreeMap<TmReferencedocs, ArrayList>(new MyComparator());
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar = Calendar.getInstance();
		if (referenceDocBean.getFromDate() != null
				&& !referenceDocBean.getFromDate().equalsIgnoreCase("")) {
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.setTime(sdf.parse(referenceDocBean.getFromDate()));
		}
		if (referenceDocBean.getToDate() != null
				&& !referenceDocBean.getToDate().equalsIgnoreCase("")) {
			calendar1.set(Calendar.HOUR_OF_DAY, 23);
			calendar1.set(Calendar.MINUTE, 59);
			calendar1.set(Calendar.SECOND, 59);
			calendar1.setTime(sdf.parse(referenceDocBean.getToDate()));
			calendar1.add(Calendar.DATE, 1);
		}

		PagingSorting pagingSorting = referenceDocBean.getPagingSorting();
		if (pagingSorting == null) {
			pagingSorting = new PagingSorting();
			referenceDocBean.setPagingSorting(pagingSorting);
		}
		pagingSorting.setPagingSorting(true, true);

		pagingSorting.setDefaultifNotPresent("employeeName",
				PagingSorting.increment, 1);

		pagingSorting.setMode("hibernate");

		Criteria ce = hibernateSession.createCriteria(TmReferencedocs.class);
		 ce.add(Restrictions.eq("isactive",
		 new Integer(AppConstants.ACTIVE_FLAG)));
		if (referenceDocBean != null
				&& referenceDocBean.getRfDocSearch() != null
				&& !referenceDocBean.getRfDocSearch().equalsIgnoreCase(
						AppConstants.INI_VALUE)) {
			ce.add(Restrictions.ilike("refdocname", referenceDocBean
					.getRfDocSearch(), MatchMode.ANYWHERE));
		}
		if (referenceDocBean.getToDate() != null
				&& referenceDocBean.getFromDate() != null
				&& !(referenceDocBean.getToDate().equalsIgnoreCase("") && referenceDocBean
						.getFromDate().equalsIgnoreCase(""))) {

			ce.add(Restrictions.between("createddate", new Date(calendar
					.getTime().getTime()), new Date(calendar1.getTime()
					.getTime())));
		}
		
		ce.addOrder(Order.asc("refdocid"));
		
		if (ce.list() != null) {
			list = (ArrayList) ce.list();
		}
		if (list != null && list.size() > 0) {

			pagingSorting.setRecordCount(list.size());

		}
		ce.setFirstResult(pagingSorting.getStartRecordId());
		ce.setMaxResults(pagingSorting.getPageRecords());
		list = new ArrayList();
		list = (ArrayList) ce.list();
		if (list != null && list.size() > 0) {

			for (int i = 0; i < list.size(); i++) {
				tmReferencedocs = new TmReferencedocs();
				tmReferencedocs = (TmReferencedocs) list.get(i);
				activeRefdocList.add(list.get(i));
			}

		}
		if (activeRefdocList != null && activeRefdocList.size() > 0) {
			for (int z = 0; z < activeRefdocList.size(); z++) {
				inactiveRefdocList = new ArrayList();
				list = new ArrayList();
				tmReferencedocs = (TmReferencedocs) activeRefdocList.get(z);
				if (tmReferencedocs != null) {
					tmReferencedocs2 = tmReferencedocs;
					while (tmReferencedocs2 != null
							&& tmReferencedocs2.getPrev_doc_id() != 0) {
						tmReferencedocs2 = getPreviousReferenceDocVersion(
								hibernateSession, tmReferencedocs2
										.getPrev_doc_id());
						inactiveRefdocList.add(tmReferencedocs2);
					}

					hashMap.put(tmReferencedocs, inactiveRefdocList);
				}

			}
		} 
		return hashMap;
	}

	// to get the previous version of doc.with the current Doc Id
	public TmReferencedocs getPreviousReferenceDocVersion(
			Session hibernateSession, long prev_doc_id) throws Exception {

		ArrayList list = new ArrayList();
		TmReferencedocs tmReferencedocs = null;
		Criteria ce1 = hibernateSession.createCriteria(TmReferencedocs.class);
		ce1.add(Restrictions.eq("refdocid", new Long(prev_doc_id)));

		if (ce1.list() != null) {
			list = (ArrayList) ce1.list();
			tmReferencedocs = (TmReferencedocs) list.get(0);
		}

		return tmReferencedocs;

	}

}
