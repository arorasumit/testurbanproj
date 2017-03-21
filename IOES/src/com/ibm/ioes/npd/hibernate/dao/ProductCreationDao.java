/**
 * 
 */
package com.ibm.ioes.npd.hibernate.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ibm.ioes.npd.beans.ProductCreationBean;
import com.ibm.ioes.npd.exception.NpdException;
import com.ibm.ioes.npd.hibernate.beans.TmNpdcategory;
import com.ibm.ioes.npd.hibernate.beans.TmProductcategory;
import com.ibm.ioes.npd.hibernate.beans.TtrnProject;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjecthierarchy;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.HibernateBeansConstants;
import com.ibm.ioes.npd.utilities.PagingSorting;
import com.ibm.ioes.npd.utilities.Utility;
import com.ibm.ioes.utilities.DbConnection;

/**
 * @author Sanjay
 * 
 */
public class ProductCreationDao extends CommonBaseDao {

	public boolean saveProduct(ProductCreationBean productCreationBean,
			Session hibernateSession) throws Exception {

		boolean insert = false;

		Date projStartDate = null;
		Calendar startDateCal = Calendar.getInstance();
		Date expectedLaunchDate = null;
		Calendar exptLaunchDateCal = Calendar.getInstance();
		TtrnProject ttrnProject = new TtrnProject();
		TmNpdcategory tmNpdcategory = new TmNpdcategory();
		TmProductcategory tmProductcategory = new TmProductcategory();
		CommonBaseDao commonBaseDao = BaseDaoFactory
				.getInstance(DaoConstants.BASE_DAO_CLASS);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");

		try {

			tmNpdcategory = (TmNpdcategory) commonBaseDao.findById(
					productCreationBean.getNpdCategoryId(),
					HibernateBeansConstants.HBM_NPD_CATEGORY, hibernateSession);

			tmProductcategory = new TmProductcategory();
			tmProductcategory.setProdcatid(1);
			ttrnProject.setProjectstatus(new Integer(2));// 2 means projects
														// in draft ie its
															// plan is not yet
															// finalised
			ttrnProject.setCreatedby(Integer.parseInt(productCreationBean
					.getCreatedBy()));
			ttrnProject.setCreateddate(new Date());
			ttrnProject.setIsactive(AppConstants.ACTIVE_FLAG);
			ttrnProject.setNpdCategory(tmNpdcategory);
			ttrnProject.setProjectName(productCreationBean.getProjectName());
			ttrnProject.setPrdCategory(tmProductcategory);
			ttrnProject.setAirtelPotential("1");
			ttrnProject.setCapexReq("1");
			ttrnProject.setTargetMkt("1");
			ttrnProject.setTotalMktPotential("1");
			ttrnProject.setExpectedLaunchDate(new Date());
			ttrnProject.setLaunchPriority(productCreationBean
					.getLaunchPriority());
			ttrnProject.setProductBrief(productCreationBean.getProductBrief());
			ttrnProject.setStartDate(new Date());
			ttrnProject.setProductId(Long.valueOf(productCreationBean.getProductId()));
			commonBaseDao.attachDirty(ttrnProject, hibernateSession);
			productCreationBean.setProjectId(ttrnProject.getProjectid());
			
			insert = true;
		} catch (Exception ex) {
			insert = false;
			// hibernateSession.getTransaction().rollback();
			throw ex;
		} finally {
			// commonBaseDao.closeTrans(hibernateSession);
		}
		return insert;
	}

	// method to search project related to the filter criteria from the
	// Database.
	public ArrayList getSearchProjectList(
			ProductCreationBean productCreationBean, Session hibernateSession)
			throws Exception {

		ArrayList projectDetailsList = null;

		Criteria ce = hibernateSession.createCriteria(TtrnProject.class);
		ce.createAlias("npdCategory", "npdCategory");
		ce.createAlias("prdCategory", "prdCategory");

		if (productCreationBean.getNpdCategoryId() != -1)
			ce.add(Restrictions.eq("npdCategory.npdcatid", new Integer(
					productCreationBean.getNpdCategoryId())));
		if (productCreationBean.getPrdCategoryId() != -1) {
			ce.add(Restrictions.eq("prdCategory.prodcatid", new Integer(
					productCreationBean.getPrdCategoryId())));
		}
		if (productCreationBean.getProjectId() != -1)
			ce.add(Restrictions.eq("projectid", new Long(new Long(
					productCreationBean.getProjectId()).longValue())));
		if (productCreationBean.getProjectName() != null
				&& !productCreationBean.getProjectName().equalsIgnoreCase(
						AppConstants.INI_VALUE))
			ce.add(Restrictions.eq("projectName", productCreationBean
					.getProjectName()));

		ce.addOrder(Order.asc("projectName"));
		if (ce.list() != null) {
			projectDetailsList = (ArrayList) ce.list();
		}

		return projectDetailsList;
	}

	// Updates the Attributes of already existing Project in the DB.

	public int updateProject(Connection conn,
			ProductCreationBean productCreationBean) throws NpdException {

		int status = 0;
		String sql = "INSERT INTO NPD.TTRN_PROJECTUPDATION(PROJECTID, CREATEDBY, CREATEDDATE, MODIFIEDBY, MODIFIEDDATE,"
				+ " AIRTEL_POTENTIAL, CAPEX_REQUIREMENT, TARGET_MARKET, TOTAL_MKT_POTENTIAL, EXPT_LAUNCH_DATE,  "
				+ "PRODUCT_BRIEF, ISACTIVE)"
				+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  ?, ?)";

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		try {

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, productCreationBean.getProjectId());
			pstmt
					.setLong(2, Long.parseLong(productCreationBean
							.getCreatedBy()));
			pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			pstmt
					.setLong(4, Long.parseLong(productCreationBean
							.getCreatedBy()));
			pstmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
			pstmt.setString(6, productCreationBean.getAirtelPotential());
			pstmt.setString(7, productCreationBean.getCapexRequirement());
			pstmt.setString(8, productCreationBean.getTargetMarket());
			pstmt.setString(9, productCreationBean.getTotalMktPotential());
			pstmt.setDate(10, new java.sql.Date(sdf.parse(
					productCreationBean.getLaunchDate()).getTime()));
			pstmt.setString(11, productCreationBean.getProductBrief());
			pstmt.setInt(12, 1);

			pstmt.execute();
			status = 1;

		} catch (Exception e) {
			status = -1;

			e.printStackTrace();
			throw new NpdException(e);
		} 
		/*finally 
		{
			try 
			{
				DbConnection.freeConnection(conn);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}	
		}*/

		return status;

	}

	// Updates the Attributes of already existing Project in the DB.

	public int actionOnUpdateProject(Connection conn,
			ProductCreationBean productCreationBean) throws Exception {

		int status = 0;
		String sql = "INSERT INTO NPD.TTRN_PROJECTUPDATION(PROJECTID, CREATEDBY, CREATEDDATE, MODIFIEDBY, MODIFIEDDATE,"
				+ " AIRTEL_POTENTIAL, CAPEX_REQUIREMENT, TARGET_MARKET, TOTAL_MKT_POTENTIAL, EXPT_LAUNCH_DATE,  "
				+ "PRODUCT_BRIEF, ISACTIVE)"
				+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  ?, ?)";

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		try {

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, productCreationBean.getProjectId());
			pstmt
					.setLong(2, Long.parseLong(productCreationBean
							.getCreatedBy()));
			pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			pstmt
					.setLong(4, Long.parseLong(productCreationBean
							.getCreatedBy()));
			pstmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
			pstmt.setString(6, productCreationBean.getAirtelPotential());
			pstmt.setString(7, productCreationBean.getCapexRequirement());
			pstmt.setString(8, productCreationBean.getTargetMarket());
			pstmt.setString(9, productCreationBean.getTotalMktPotential());
			pstmt.setDate(10, new java.sql.Date(sdf.parse(
					productCreationBean.getLaunchDate()).getTime()));

			pstmt.execute();
			status = 1;

		} catch (Exception e) {
			status = -1;

			e.printStackTrace();

		} finally {
			try {
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return status;

	}

	public ArrayList<TtrnProject> getSearchProjectList2(
			Session hibernateSession, TtrnProject searchDto)
			throws NpdException {

		Connection conn = hibernateSession.connection();

		ArrayList<TtrnProject> projectList = new ArrayList<TtrnProject>();
		try {

			String sql = "SELECT PROJECTID, PROJECTSTATUS, CREATEDBY, CREATEDDATE, MODIFIEDBY, MODIFIEDDATE, " +
					"ISACTIVE, NPD_CATEGORY, PROJECT_NAME, PRODUCT_CATEGORY, AIRTEL_POTENTIAL, CAPEX_REQUIREMENT, " +
					"TARGET_MARKET, TOTAL_MKT_POTENTIAL, EXPT_LAUNCH_DATE, LAUNCH_PRIORITY, PRODUCT_BRIEF, " +
					"START_DATE, ACT_LAUNCH_DATE, PRODCATID, PRODCATDESC, NPDCATID, NPDCATDESC, CURRENT_VERSION  " +
					"FROM NPD.V_SEARCH_PROJECTS  ";
			String whereCondition = "";
			String condition = "";

			condition = Utility.generateStringLikeCondition(searchDto
					.getSearchProjectName(), "PROJECT_NAME");
			whereCondition = Utility.addToCondition(whereCondition, condition);

			condition = Utility.generateRelativeCondition("EQUAL", searchDto
					.getSearchProjectId(), null, "PROJECTID");
			whereCondition = Utility.addToCondition(whereCondition, condition);

			condition = Utility.generateRelativeCondition("EQUAL", searchDto
					.getSearchNpdCategoryId(), null, "NPDCATID");
			whereCondition = Utility.addToCondition(whereCondition, condition);

			
				condition = Utility.generateRelativeCondition("EQUAL", searchDto
						.getSearchProductCategoryId(), null,
						"PRODCATID");
				whereCondition = Utility.addToCondition(whereCondition, condition);
			
			
			if(!searchDto.getLaunchPriority().equalsIgnoreCase("All"))
			{
				condition = Utility.generateStringLikeCondition(searchDto.getLaunchPriority(),"LAUNCH_PRIORITY");
				whereCondition = Utility.addToCondition(whereCondition, condition);
			}
		
			if(searchDto.getProjectstatus()!=-1)
			{
				condition = Utility.generateRelativeCondition("EQUAL", String.valueOf(searchDto.getProjectstatus()), null, "PROJECTSTATUS");
				whereCondition = Utility.addToCondition(whereCondition, condition);
			}
			
			if (!(whereCondition.trim().equals(""))) {
				sql = sql + " WHERE " + whereCondition;
			}

			// Generating Order By clause
			String orderByColumn = "";
			String ASC_DESC = "DESC";
			PagingSorting pagingSorting = searchDto.getPagingSorting();
			String sortBy = pagingSorting.getSortByColumn();

			if ("projectName".equals(sortBy)) {
				orderByColumn = "PROJECT_NAME";
			} else if ("projectId".equals(sortBy)) {
				orderByColumn = "PROJECTID";
			} else if ("npdCategory".equals(sortBy)) {
				orderByColumn = "NPDCATID";
			} else if ("prdCategory".equals(sortBy)) {
				orderByColumn = "PRODCATID";
			}

			ASC_DESC = "DESC";
			String sortByOrder = pagingSorting.getSortByOrder();
			if ("increment".equalsIgnoreCase(sortByOrder)) {
				ASC_DESC = "ASC";
			}

			if (orderByColumn != null && !(orderByColumn.trim().equals(""))) {
				String FullOrderBy = " ORDER BY " + orderByColumn + " "
						+ ASC_DESC + " ";
				sql = sql + FullOrderBy;

				// For paging
				if (pagingSorting.isPagingToBeDone()) {
					if (pagingSorting != null) {
						pagingSorting.storeRecordCount(conn, sql);
						sql = pagingSorting.query(sql,
								removeDOTBefore(FullOrderBy),
								PagingSorting.jdbc);
					}
				}
			}

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				TtrnProject dto = new TtrnProject();

				dto.setProjectid(rs.getLong("PROJECTID"));
				dto.setProjectName(rs.getString("PROJECT_NAME"));
				dto.setLaunchPriority(rs.getString("LAUNCH_PRIORITY"));
				dto.setProductBrief(rs.getString("PRODUCT_BRIEF"));
				dto.setTargetMkt(rs.getString("TARGET_MARKET"));
				dto.setPrdCategoryName(rs.getString("PRODCATDESC"));
				dto.setNpdCategoryName(rs.getString("NPDCATDESC"));
				dto.setAirtelPotential(rs.getString("AIRTEL_POTENTIAL"));
				dto.setTotalMktPotential(rs.getString("TOTAL_MKT_POTENTIAL"));
				dto.setCapexReq(rs.getString("CAPEX_REQUIREMENT"));
				dto.setProjectstatus(rs.getInt("PROJECTSTATUS"));
				
				dto.setProjectCurrentVersion(rs.getString("CURRENT_VERSION"));
				
				

				projectList.add(dto);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in getSearchProjectList method of "
					+ this.getClass().getSimpleName()
					+ AppUtility.getStackTrace(ex));
			throw new NpdException(
					"Exception occured in getSearchProjectList method of "
							+ this.getClass().getSimpleName());
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return projectList;
	}

	public ArrayList<TtrnProject> getProductsFinalizeNewPlan(
			Session hibernateSession, TtrnProject searchDto)
			throws NpdException {

		Connection conn = hibernateSession.connection();

		ArrayList<TtrnProject> projectList = new ArrayList<TtrnProject>();
		try {

			String sql = "SELECT	PRD.*,TM_PRODUCTCATEGORY.PRODCATID,TM_PRODUCTCATEGORY.PRODCATDESC,"
					+ " TM_NPDCATEGORY.NPDCATID,TM_NPDCATEGORY.NPDCATDESC "
					+ " FROM NPD.TTRN_PROJECT PRD "
					+ " LEFT OUTER JOIN NPD.TM_NPDCATEGORY TM_NPDCATEGORY ON PRD.NPD_CATEGORY = TM_NPDCATEGORY.NPDCATID "
					+ " LEFT OUTER JOIN NPD.TM_PRODUCTCATEGORY TM_PRODUCTCATEGORY "
					+ " ON TM_PRODUCTCATEGORY.PRODCATID = PRD.PRODUCT_CATEGORY  "
					+ " INNER JOIN "
					+ " ( "
					+ " select DET.PROJECTID,WFL.PROJECTWORKFLOWID,WFL.WORKFLOWSATTUS from NPD.TTRN_PROJECTWORKFLOWDET DET INNER JOIN "
					+ " NPD.TTRN_PROJECTWORKFLOW WFL ON DET.PROJECTWORKFLOWID=WFL.PROJECTWORKFLOWID WHERE DET.ISACTIVE=2 AND WFL.ATTACHHISTORYSTATUS='"
					+ AppConstants.ATTACH_NEW
					+ "'"
					+ " ) AS WK_DRAFT ON PRD.PROJECTID=WK_DRAFT.PROJECTID ";
			String whereCondition = "";
			String condition = "";

			condition = "PRD.CREATEDBY=" + searchDto.getCreatedby();
			whereCondition = Utility.addToCondition(whereCondition, condition);

			condition = Utility.generateStringLikeCondition(searchDto
					.getSearchProjectName(), "PRD.PROJECT_NAME");
			whereCondition = Utility.addToCondition(whereCondition, condition);

			condition = Utility.generateRelativeCondition("EQUAL", searchDto
					.getSearchProjectId(), null, "PRD.PROJECTID");
			whereCondition = Utility.addToCondition(whereCondition, condition);

			condition = Utility.generateRelativeCondition("EQUAL", searchDto
					.getSearchNpdCategoryId(), null, "TM_NPDCATEGORY.NPDCATID");
			whereCondition = Utility.addToCondition(whereCondition, condition);

			condition = Utility.generateRelativeCondition("EQUAL", searchDto
					.getSearchProductCategoryId(), null,
					"TM_PRODUCTCATEGORY.PRODCATID");
			whereCondition = Utility.addToCondition(whereCondition, condition);

			if (!(whereCondition.trim().equals(""))) {
				sql = sql + " WHERE " + whereCondition;
			}

			// Generating Order By clause
			String orderByColumn = "";
			String ASC_DESC = "DESC";
			PagingSorting pagingSorting = searchDto.getPagingSorting();
			String sortBy = pagingSorting.getSortByColumn();

			if ("projectName".equals(sortBy)) {
				orderByColumn = "PRD.PROJECT_NAME";
			} else if ("projectId".equals(sortBy)) {
				orderByColumn = "PRD.PROJECTID";
			} else if ("npdCategory".equals(sortBy)) {
				orderByColumn = "TM_NPDCATEGORY.NPDCATID";
			} else if ("prdCategory".equals(sortBy)) {
				orderByColumn = "TM_PRODUCTCATEGORY.PRODCATID";
			}

			ASC_DESC = "DESC";
			String sortByOrder = pagingSorting.getSortByOrder();
			if ("increment".equalsIgnoreCase(sortByOrder)) {
				ASC_DESC = "ASC";
			}

			if (orderByColumn != null && !(orderByColumn.trim().equals(""))) {
				String FullOrderBy = " ORDER BY " + orderByColumn + " "
						+ ASC_DESC + " ";
				sql = sql + FullOrderBy;

				// For paging
				if (pagingSorting.isPagingToBeDone()) {
					if (pagingSorting != null) {
						pagingSorting.storeRecordCount(conn, sql);
						sql = pagingSorting.query(sql,
								removeDOTBefore(FullOrderBy),
								PagingSorting.jdbc);
					}
				}
			}

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				TtrnProject dto = new TtrnProject();

				dto.setProjectid(rs.getLong("PROJECTID"));
				dto.setProjectName(rs.getString("PROJECT_NAME"));
				dto.setLaunchPriority(rs.getString("LAUNCH_PRIORITY"));
				dto.setProductBrief(rs.getString("PRODUCT_BRIEF"));
				dto.setTargetMkt(rs.getString("TARGET_MARKET"));
				dto.setPrdCategoryName(rs.getString("PRODCATDESC"));
				dto.setNpdCategoryName(rs.getString("NPDCATDESC"));
				dto.setAirtelPotential(rs.getString("AIRTEL_POTENTIAL"));
				dto.setTotalMktPotential(rs.getString("TOTAL_MKT_POTENTIAL"));
				dto.setCapexReq(rs.getString("CAPEX_REQUIREMENT"));
				// dto.set(rs.getString("TASKNAME"));
				// dto.setTaskname(rs.getString("TASKNAME"));
				/*
				 * dto.setTaskname(rs.getString("TASKNAME"));
				 * dto.setTaskname(rs.getString("TASKNAME"));
				 * dto.setTaskname(rs.getString("TASKNAME"));
				 * dto.setTaskname(rs.getString("TASKNAME"));
				 * dto.setTaskname(rs.getString("TASKNAME"));
				 * 
				 * 
				 * dto.setCurrenttaskid(rs.getLong("CURRENTTASKID"));
				 * dto.setTaskname(rs.getString("TASKNAME"));
				 * dto.setStagename(rs.getString("STAGENAME"));
				 * dto.setTaskstakeholderName(rs.getString("ROLENAME"));
				 * dto.setAssignedtouserName(rs.getString("EMPNAME"));
				 * dto.setTaskduration(rs.getLong("TASKDURATION"));
				 */

				// dto.setWorkflowAttachStatus(rs.getString("WORKFLOWSATTUS"));

				projectList.add(dto);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in getSearchProjectList method of "
					+ this.getClass().getSimpleName()
					+ AppUtility.getStackTrace(ex));
			throw new NpdException(
					"Exception occured in getSearchProjectList method of "
							+ this.getClass().getSimpleName());
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return projectList;
	}

	public ArrayList<TtrnProject> getProductsEditBaselinedPlan(
			Session hibernateSession, TtrnProject searchDto)
			throws NpdException {

		Connection conn = hibernateSession.connection();

		ArrayList<TtrnProject> projectList = new ArrayList<TtrnProject>();
		try {

			String sql = "SELECT PROJECTID, PROJECTSTATUS, CREATEDBY, CREATEDDATE, MODIFIEDBY, MODIFIEDDATE, " +
					"ISACTIVE, NPD_CATEGORY, PROJECT_NAME, PRODUCT_CATEGORY, AIRTEL_POTENTIAL, CAPEX_REQUIREMENT, " +
					"TARGET_MARKET, TOTAL_MKT_POTENTIAL, EXPT_LAUNCH_DATE, LAUNCH_PRIORITY, PRODUCT_BRIEF, " +
					"START_DATE, ACT_LAUNCH_DATE, PRODCATID, PRODCATDESC, NPDCATID, NPDCATDESC, F_ISACTIVE, " +
					"D_ISACTIVE, CURRENT_VERSION FROM NPD.V_EDITBASELINED_PLAN ";
			
			String whereCondition = "";
			String condition = "";

			condition = "PROJECTSTATUS=1";
			whereCondition = Utility.addToCondition(whereCondition, condition);

			condition = Utility.generateStringLikeCondition(searchDto
					.getSearchProjectName(), "PROJECT_NAME");
			whereCondition = Utility.addToCondition(whereCondition, condition);

			condition = Utility.generateRelativeCondition("EQUAL", searchDto
					.getSearchProjectId(), null, "PROJECTID");
			whereCondition = Utility.addToCondition(whereCondition, condition);

			condition = Utility.generateRelativeCondition("EQUAL", searchDto
					.getSearchNpdCategoryId(), null, "NPDCATID");
			whereCondition = Utility.addToCondition(whereCondition, condition);

			condition = Utility.generateRelativeCondition("EQUAL", searchDto
					.getSearchProductCategoryId(), null,
					"PRODCATID");
			whereCondition = Utility.addToCondition(whereCondition, condition);

			if (!(whereCondition.trim().equals(""))) {
				sql = sql + " WHERE " + whereCondition;
			}

			// Generating Order By clause
			String orderByColumn = "";
			String ASC_DESC = "DESC";
			PagingSorting pagingSorting = searchDto.getPagingSorting();
			String sortBy = pagingSorting.getSortByColumn();

			if ("projectName".equals(sortBy)) {
				orderByColumn = "PROJECT_NAME";
			} else if ("projectId".equals(sortBy)) {
				orderByColumn = "PROJECTID";
			} else if ("npdCategory".equals(sortBy)) {
				orderByColumn = "NPDCATID";
			} else if ("prdCategory".equals(sortBy)) {
				orderByColumn = "PRODCATID";
			}

			ASC_DESC = "DESC";
			String sortByOrder = pagingSorting.getSortByOrder();
			if ("increment".equalsIgnoreCase(sortByOrder)) {
				ASC_DESC = "ASC";
			}

			if (orderByColumn != null && !(orderByColumn.trim().equals(""))) {
				String FullOrderBy = " ORDER BY " + orderByColumn + " "
						+ ASC_DESC + " ";
				sql = sql + FullOrderBy;

				// For paging
				if (pagingSorting.isPagingToBeDone()) {
					if (pagingSorting != null) {
						pagingSorting.storeRecordCount(conn, sql);
						sql = pagingSorting.query(sql,
								removeDOTBefore(FullOrderBy),
								PagingSorting.jdbc);
					}
				}
			}

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				TtrnProject dto = new TtrnProject();

				dto.setProjectid(rs.getLong("PROJECTID"));
				dto.setProjectName(rs.getString("PROJECT_NAME"));
				dto.setLaunchPriority(rs.getString("LAUNCH_PRIORITY"));
				dto.setProductBrief(rs.getString("PRODUCT_BRIEF"));
				dto.setTargetMkt(rs.getString("TARGET_MARKET"));
				dto.setPrdCategoryName(rs.getString("PRODCATDESC"));
				dto.setNpdCategoryName(rs.getString("NPDCATDESC"));
				dto.setAirtelPotential(rs.getString("AIRTEL_POTENTIAL"));
				dto.setTotalMktPotential(rs.getString("TOTAL_MKT_POTENTIAL"));
				dto.setCapexReq(rs.getString("CAPEX_REQUIREMENT"));
				dto.setProjectCurrentVersion(rs.getString("CURRENT_VERSION"));
				

				int a = rs.getInt("F_ISACTIVE");
				int b = rs.getInt("D_ISACTIVE");
				if (b == 2) {
					dto.setIsactive(2);
				} else {
					dto.setIsactive(1);
				}
				// dto.setIsactive(rs.getInt("ISACTIVE"));

				// dto.set(rs.getString("TASKNAME"));
				// dto.setTaskname(rs.getString("TASKNAME"));
				/*
				 * dto.setTaskname(rs.getString("TASKNAME"));
				 * dto.setTaskname(rs.getString("TASKNAME"));
				 * dto.setTaskname(rs.getString("TASKNAME"));
				 * dto.setTaskname(rs.getString("TASKNAME"));
				 * dto.setTaskname(rs.getString("TASKNAME"));
				 * 
				 * 
				 * dto.setCurrenttaskid(rs.getLong("CURRENTTASKID"));
				 * dto.setTaskname(rs.getString("TASKNAME"));
				 * dto.setStagename(rs.getString("STAGENAME"));
				 * dto.setTaskstakeholderName(rs.getString("ROLENAME"));
				 * dto.setAssignedtouserName(rs.getString("EMPNAME"));
				 * dto.setTaskduration(rs.getLong("TASKDURATION"));
				 */

				// dto.setWorkflowAttachStatus(rs.getString("WORKFLOWSATTUS"));

				projectList.add(dto);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in getSearchProjectList method of "
					+ this.getClass().getSimpleName()
					+ AppUtility.getStackTrace(ex));
			throw new NpdException(
					"Exception occured in getSearchProjectList method of "
							+ this.getClass().getSimpleName());
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return projectList;
	}

	//
	public ArrayList<TtrnProject> getUpdateProductList(
			Session hibernateSession, TtrnProject searchDto)
			throws NpdException {

		Connection conn = hibernateSession.connection();

		ArrayList<TtrnProject> projectList = new ArrayList<TtrnProject>();
		try {

			String sql = "SELECT	PRD.*,TM_PRODUCTCATEGORY.PRODCATID,TM_PRODUCTCATEGORY.PRODCATDESC,"
					+ " TM_NPDCATEGORY.NPDCATID,TM_NPDCATEGORY.NPDCATDESC ,COALESCE(UPD.PROJECTID,0) AS PROJECTIDFLAG "
					+ " FROM NPD.TTRN_PROJECT PRD "
					+ " LEFT OUTER JOIN NPD.TM_NPDCATEGORY TM_NPDCATEGORY ON PRD.NPD_CATEGORY = TM_NPDCATEGORY.NPDCATID "
					+ " LEFT OUTER JOIN NPD.TM_PRODUCTCATEGORY TM_PRODUCTCATEGORY "
					+ " ON TM_PRODUCTCATEGORY.PRODCATID = PRD.PRODUCT_CATEGORY "
					+ " LEFT OUTER JOIN NPD.TTRN_PROJECTUPDATION AS UPD ON PRD.PROJECTID=UPD.PROJECTID AND UPD.ISACTIVE=1";

			String whereCondition = "";
			String condition = "";

			condition = " PRD.PROJECTSTATUS=1";
			whereCondition = Utility.addToCondition(whereCondition, condition);

			condition = Utility.generateStringLikeCondition(searchDto
					.getSearchProjectName(), "PRD.PROJECT_NAME");
			whereCondition = Utility.addToCondition(whereCondition, condition);

			condition = Utility.generateRelativeCondition("EQUAL", searchDto
					.getSearchProjectId(), null, "PRD.PROJECTID");
			whereCondition = Utility.addToCondition(whereCondition, condition);

			condition = Utility.generateRelativeCondition("EQUAL", searchDto
					.getSearchNpdCategoryId(), null, "TM_NPDCATEGORY.NPDCATID");
			whereCondition = Utility.addToCondition(whereCondition, condition);

			condition = Utility.generateRelativeCondition("EQUAL", searchDto
					.getSearchProductCategoryId(), null,
					"TM_PRODUCTCATEGORY.PRODCATID");
			whereCondition = Utility.addToCondition(whereCondition, condition);

			if (!(whereCondition.trim().equals(""))) {
				sql = sql + " WHERE " + whereCondition;
			}

			// Generating Order By clause
			String orderByColumn = "";
			String ASC_DESC = "DESC";
			PagingSorting pagingSorting = searchDto.getPagingSorting();
			String sortBy = pagingSorting.getSortByColumn();

			if ("projectName".equals(sortBy)) {
				orderByColumn = "PRD.PROJECT_NAME";
			} else if ("projectId".equals(sortBy)) {
				orderByColumn = "PRD.PROJECTID";
			} else if ("npdCategory".equals(sortBy)) {
				orderByColumn = "TM_NPDCATEGORY.NPDCATID";
			} else if ("prdCategory".equals(sortBy)) {
				orderByColumn = "TM_PRODUCTCATEGORY.PRODCATID";
			}

			ASC_DESC = "DESC";
			String sortByOrder = pagingSorting.getSortByOrder();
			if ("increment".equalsIgnoreCase(sortByOrder)) {
				ASC_DESC = "ASC";
			}

			if (orderByColumn != null && !(orderByColumn.trim().equals(""))) {
				String FullOrderBy = " ORDER BY " + orderByColumn + " "
						+ ASC_DESC + " ";
				sql = sql + FullOrderBy;

				// For paging
				if (pagingSorting.isPagingToBeDone()) {
					if (pagingSorting != null) {
						pagingSorting.storeRecordCount(conn, sql);
						sql = pagingSorting.query(sql,
								removeDOTBefore(FullOrderBy),
								PagingSorting.jdbc);
					}
				}
			}

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				TtrnProject dto = new TtrnProject();

				dto.setProjectid(rs.getLong("PROJECTID"));
				dto.setProjectName(rs.getString("PROJECT_NAME"));
				dto.setLaunchPriority(rs.getString("LAUNCH_PRIORITY"));
				dto.setProductBrief(rs.getString("PRODUCT_BRIEF"));
				dto.setTargetMkt(rs.getString("TARGET_MARKET"));
				dto.setPrdCategoryName(rs.getString("PRODCATDESC"));
				dto.setNpdCategoryName(rs.getString("NPDCATDESC"));
				dto.setAirtelPotential(rs.getString("AIRTEL_POTENTIAL"));
				dto.setTotalMktPotential(rs.getString("TOTAL_MKT_POTENTIAL"));
				dto.setCapexReq(rs.getString("CAPEX_REQUIREMENT"));

				dto.setProductUpdationFlag(rs.getString("PROJECTIDFLAG"));

				projectList.add(dto);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in getSearchProjectList method of "
					+ this.getClass().getSimpleName()
					+ AppUtility.getStackTrace(ex));
			throw new NpdException(
					"Exception occured in getSearchProjectList method of "
							+ this.getClass().getSimpleName());
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return projectList;
	}

	public ArrayList<TtrnProject> getProductsForUpdateAction(
			Session hibernateSession, TtrnProject searchDto)
			throws NpdException {

		Connection conn = hibernateSession.connection();

		ArrayList<TtrnProject> projectList = new ArrayList<TtrnProject>();
		try {

			String sql = "SELECT	PRD.*,TM_PRODUCTCATEGORY.PRODCATID,TM_PRODUCTCATEGORY.PRODCATDESC,"
					+ " TM_NPDCATEGORY.NPDCATID,TM_NPDCATEGORY.NPDCATDESC  "
					+ " FROM NPD.TTRN_PROJECT PRD "
					+ " LEFT OUTER JOIN NPD.TM_NPDCATEGORY TM_NPDCATEGORY ON PRD.NPD_CATEGORY = TM_NPDCATEGORY.NPDCATID "
					+ " LEFT OUTER JOIN NPD.TM_PRODUCTCATEGORY TM_PRODUCTCATEGORY "
					+ " ON TM_PRODUCTCATEGORY.PRODCATID = PRD.PRODUCT_CATEGORY "
					+ " INNER JOIN NPD.TTRN_PROJECTUPDATION AS UPD ON PRD.PROJECTID=UPD.PROJECTID ";

			String whereCondition = "";
			String condition = "";

			condition = "UPD.ISACTIVE=1 AND PRD.PROJECTSTATUS=1";
			whereCondition = Utility.addToCondition(whereCondition, condition);

			condition = Utility.generateStringLikeCondition(searchDto
					.getSearchProjectName(), "PRD.PROJECT_NAME");
			whereCondition = Utility.addToCondition(whereCondition, condition);

			condition = Utility.generateRelativeCondition("EQUAL", searchDto
					.getSearchProjectId(), null, "PRD.PROJECTID");
			whereCondition = Utility.addToCondition(whereCondition, condition);

			condition = Utility.generateRelativeCondition("EQUAL", searchDto
					.getSearchNpdCategoryId(), null, "TM_NPDCATEGORY.NPDCATID");
			whereCondition = Utility.addToCondition(whereCondition, condition);

			condition = Utility.generateRelativeCondition("EQUAL", searchDto
					.getSearchProductCategoryId(), null,
					"TM_PRODUCTCATEGORY.PRODCATID");
			whereCondition = Utility.addToCondition(whereCondition, condition);

			if (!(whereCondition.trim().equals(""))) {
				sql = sql + " WHERE " + whereCondition;
			}

			// Generating Order By clause
			String orderByColumn = "";
			String ASC_DESC = "DESC";
			PagingSorting pagingSorting = searchDto.getPagingSorting();
			String sortBy = pagingSorting.getSortByColumn();

			if ("projectName".equals(sortBy)) {
				orderByColumn = "PRD.PROJECT_NAME";
			} else if ("projectId".equals(sortBy)) {
				orderByColumn = "PRD.PROJECTID";
			} else if ("npdCategory".equals(sortBy)) {
				orderByColumn = "TM_NPDCATEGORY.NPDCATID";
			} else if ("prdCategory".equals(sortBy)) {
				orderByColumn = "TM_PRODUCTCATEGORY.PRODCATID";
			}

			ASC_DESC = "DESC";
			String sortByOrder = pagingSorting.getSortByOrder();
			if ("increment".equalsIgnoreCase(sortByOrder)) {
				ASC_DESC = "ASC";
			}

			if (orderByColumn != null && !(orderByColumn.trim().equals(""))) {
				String FullOrderBy = " ORDER BY " + orderByColumn + " "
						+ ASC_DESC + " ";
				sql = sql + FullOrderBy;

				// For paging
				if (pagingSorting.isPagingToBeDone()) {
					if (pagingSorting != null) {
						pagingSorting.storeRecordCount(conn, sql);
						sql = pagingSorting.query(sql,
								removeDOTBefore(FullOrderBy),
								PagingSorting.jdbc);
					}
				}
			}

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				TtrnProject dto = new TtrnProject();

				dto.setProjectid(rs.getLong("PROJECTID"));
				dto.setProjectName(rs.getString("PROJECT_NAME"));
				dto.setLaunchPriority(rs.getString("LAUNCH_PRIORITY"));
				dto.setProductBrief(rs.getString("PRODUCT_BRIEF"));
				dto.setTargetMkt(rs.getString("TARGET_MARKET"));
				dto.setPrdCategoryName(rs.getString("PRODCATDESC"));
				dto.setNpdCategoryName(rs.getString("NPDCATDESC"));
				dto.setAirtelPotential(rs.getString("AIRTEL_POTENTIAL"));
				dto.setTotalMktPotential(rs.getString("TOTAL_MKT_POTENTIAL"));
				dto.setCapexReq(rs.getString("CAPEX_REQUIREMENT"));

				projectList.add(dto);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in getSearchProjectList method of "
					+ this.getClass().getSimpleName()
					+ AppUtility.getStackTrace(ex));
			throw new NpdException(
					"Exception occured in getSearchProjectList method of "
							+ this.getClass().getSimpleName());
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return projectList;
	}

	public String removeDOTBefore(String str) {

		int dotPos = str.indexOf(".");
		if (dotPos >= 0) {
			String after = str.substring(dotPos + 1);
			String mid = str.substring(0, dotPos);
			String before = mid.substring(0, mid.lastIndexOf(" ") + 1);
			return before + after;
		} else {
			return str;
		}
	}

	public TtrnProject getDraftProject(Connection conn, long projectId)
			throws NpdException {

		TtrnProject bean = null;
		String sql = "SELECT UPD.* ,EMP.EMPNAME ,EMP.EMAIL,PRD.LAUNCH_PRIORITY AS INITIAL_LAUNCH_PRIORITY ,"
				+ " PRD.PRODUCT_CATEGORY AS INI_PRODUCT_CATEGORY"
				+ " FROM NPD.TTRN_PROJECTUPDATION AS UPD "
				+ " INNER JOIN NPD.TTRN_PROJECT PRD ON PRD.PROJECTID=UPD.PROJECTID"
				+ " INNER JOIN NPD.TM_EMPLOYEE EMP ON UPD.CREATEDBY=EMP.NPDEMPID"
				+ " WHERE  UPD.PROJECTID=" + projectId + " AND UPD.ISACTIVE=1";
		try {
			ResultSet rs = conn.createStatement().executeQuery(sql);
			if (rs.next()) {
				bean = new TtrnProject();

				bean.setProjectid(rs.getLong("PROJECTID"));
				bean.setProductBrief(rs.getString("PRODUCT_BRIEF"));
				bean.setTargetMkt(rs.getString("TARGET_MARKET"));
				bean.setAirtelPotential(rs.getString("AIRTEL_POTENTIAL"));
				bean.setTotalMktPotential(rs.getString("TOTAL_MKT_POTENTIAL"));
				bean.setCapexReq(rs.getString("CAPEX_REQUIREMENT"));
				bean.setExpectedLaunchDate(rs.getDate("EXPT_LAUNCH_DATE"));
				bean.setLaunchPriority(rs.getString("INITIAL_LAUNCH_PRIORITY"));
				bean.setPrdCategoryId(rs.getInt("INI_PRODUCT_CATEGORY"));
				bean.setUpdationRequestedByName(rs.getString("EMPNAME"));
				bean.setUpdationRequestedBy(rs.getString("CREATEDBY"));
				bean.setUpdationRequestedByEmail(rs.getString("EMAIL"));

			} else {
				bean = null;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in getDraftProject method of "
					+ this.getClass().getSimpleName()
					+ AppUtility.getStackTrace(ex));
			throw new NpdException(
					"Exception occured in getDraftProject method of "
							+ this.getClass().getSimpleName());
		} finally {
			try {
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return bean;
	}

	public int actionForUpdateProduct(Connection conn,
			ProductCreationBean productCreationBean, String actionType)
			throws NpdException {

		int status = 0;

		try {

			CallableStatement cstmt = conn
					.prepareCall("{call NPD.P_ACTION_ON_PROJECTUPDATE(?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");

			cstmt.setString(1, actionType);
			cstmt.setNull(2, java.sql.Types.BIGINT);
			cstmt.setLong(3, productCreationBean.getProjectId());
			cstmt
					.setLong(4, Long.parseLong(productCreationBean
							.getCreatedBy()));
			cstmt.setInt(5, productCreationBean.getPrdCategoryId());
			cstmt.setString(6, productCreationBean.getAirtelPotential());
			cstmt.setString(7, productCreationBean.getCapexRequirement());
			cstmt.setString(8, productCreationBean.getTargetMarket());
			cstmt.setString(9, productCreationBean.getTotalMktPotential());
			cstmt.setDate(10, new java.sql.Date(sdf.parse(
					productCreationBean.getLaunchDate()).getTime()));
			cstmt.setString(11, productCreationBean.getLaunchPriority());
			cstmt.setString(12, productCreationBean.getProductBrief());
			cstmt.setString(13, "");

			cstmt.execute();
			status = 1;

		} catch (Exception e) {
			status = -1;

			e.printStackTrace();
			throw new NpdException(e);
		} /*finally {
			try {
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}*/

		return status;

	}

}
