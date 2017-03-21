/**
 * 
 */
package com.ibm.ioes.npd.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ibm.ioes.npd.beans.ProductCreationBean;
import com.ibm.ioes.npd.beans.ProjectPlanInstanceBean;

import com.ibm.ioes.npd.exception.NpdException;
import com.ibm.ioes.npd.hibernate.beans.TmEmployee;
import com.ibm.ioes.npd.hibernate.beans.TmProductcategory;
import com.ibm.ioes.npd.hibernate.beans.TmstProjecthierarchytasksflow;
import com.ibm.ioes.npd.hibernate.beans.TtrnProject;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjecthierarchy;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjectworkflow;
import com.ibm.ioes.npd.hibernate.dao.AttachEditProjectPlanDao;
import com.ibm.ioes.npd.hibernate.dao.BaseDaoFactory;
import com.ibm.ioes.npd.hibernate.dao.CommonBaseDao;
import com.ibm.ioes.npd.hibernate.dao.DaoConstants;
import com.ibm.ioes.npd.hibernate.dao.ProductCreationDao;
import com.ibm.ioes.npd.hibernate.dao.WorkFlowDao;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.HibernateBeansConstants;
import com.ibm.ioes.npd.utilities.Messages;
import com.ibm.ioes.npd.utilities.NpdConnection;
import com.ibm.ioes.npd.utilities.PagingSorting;
import com.ibm.ioes.npd.utilities.SendMailUtility;
import com.ibm.ioes.npd.utilities.Utility;

/**
 * @author Sanjay
 * 
 */
public class ProductCreationModel extends CommonBaseModel {

	public ProductCreationBean viewProduct(
			ProductCreationBean productCreationBean) throws Exception {
		Session hibernateSession = null;
		ArrayList npdCategoryList = null;
		ArrayList prdCategoryList = null;
		try {
			CommonBaseDao commonBaseDao = (CommonBaseDao) BaseDaoFactory
					.getInstance(DaoConstants.BASE_DAO_CLASS);

			hibernateSession = CommonBaseDao.beginTrans();

			npdCategoryList = commonBaseDao.getAllEntriesInATable(
					hibernateSession, HibernateBeansConstants.HBM_NPD_CATEGORY);
			/*prdCategoryList = commonBaseDao.getAllEntriesInATableOrder(
					hibernateSession, HibernateBeansConstants.HBM_PRD_CATEGORY,
					"prodcatdesc", "asc");*/
			Criteria ce = hibernateSession.createCriteria(HibernateBeansConstants.HBM_PRD_CATEGORY);
			ce.add(Restrictions.eq("isactive",1 ));
			ce.addOrder(Order.asc("prodcatdesc"));
			prdCategoryList=(ArrayList)ce.list();
			
			if (npdCategoryList != null && npdCategoryList.size() > 0) {
				productCreationBean.setNpdCategoryList(npdCategoryList);
			}

			if (prdCategoryList != null && prdCategoryList.size() > 0) {
				productCreationBean.setPrdCategoryList(prdCategoryList);
			}

			// Criteria ce=hibernateSession.createCriteria(TtrnProject.class);

			// productCreationBean.setProjectId(projectId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CommonBaseDao.closeTrans(hibernateSession);
		}
		return productCreationBean;
	}

	private TtrnProject initSearchProductUtility(Session hibernateSession,
			ProductCreationBean productCreationBean) throws Exception {

		ArrayList npdCategoryList = null;
		ArrayList projectList = null;
		ArrayList prdCategoryList = null;
		CommonBaseDao commonBaseDao = (CommonBaseDao) BaseDaoFactory
				.getInstance(DaoConstants.BASE_DAO_CLASS);

		npdCategoryList = commonBaseDao.getAllEntriesInATable(hibernateSession,
				HibernateBeansConstants.HBM_NPD_CATEGORY);
		projectList = commonBaseDao.getAllEntriesInATable(hibernateSession,
				HibernateBeansConstants.HBM_PROJECT);
		
		Criteria ce=hibernateSession.createCriteria(TmProductcategory.class);
		ce.add(Restrictions.eq("isactive", 1));
		ce.addOrder(Order.asc("prodcatdesc"));
		prdCategoryList = (ArrayList)ce.list();
		
		/*prdCategoryList = commonBaseDao.getAllEntriesInATableOrder(
				hibernateSession, HibernateBeansConstants.HBM_PRD_CATEGORY,
				"prodcatdesc", "asc");*/

		// fetching filtered recoreds

		TtrnProject searchDto = new TtrnProject();

		// Transfer search parameters from inputForm to saeerchDto

		String searchProjectId = productCreationBean.getSearchProjectId();
		String searchProjectName = productCreationBean.getSearchProjectName();
		String searchProductCategoryId = productCreationBean
				.getSearchProductCategoryId();
		String searchNpdCategoryId = productCreationBean
				.getSearchNpdCategoryId();
		PagingSorting pagingSorting = productCreationBean.getPagingSorting();

		if (searchNpdCategoryId != null && !"-1".equals(searchNpdCategoryId)) {
			searchDto.setSearchNpdCategoryId(searchNpdCategoryId);
		}
		if (searchProductCategoryId != null
				&& !"-1".equals(searchProductCategoryId)) {
			searchDto.setSearchProductCategoryId(searchProductCategoryId);
		}

		if (searchProjectId != null && !"-1".equals(searchProjectId)) {
			searchDto.setSearchProjectId(searchProjectId);
		}

		searchDto.setSearchProjectName(searchProjectName);

		if (pagingSorting == null) {
			pagingSorting = new PagingSorting();
			productCreationBean.setPagingSorting(pagingSorting);
		}
		pagingSorting.setPagingSorting(true, true);

		pagingSorting.setDefaultifNotPresent("projectId",
				PagingSorting.increment, 1);

		searchDto.setPagingSorting(pagingSorting);

		ProductCreationDao productCreationDao = (ProductCreationDao) BaseDaoFactory
				.getInstance(DaoConstants.PRODUCT_CREATION_DAO);

		// //////////ArrayList<TtrnProject>
		// filteredProjectList=productCreationDao.getSearchProjectList2(hibernateSession,searchDto);

		// projectDetailList = productCreationDao.getSearchProjectList(
		// productCreationBean, hibernateSession);
		// filteredProjectList=null;

		// /////////productCreationBean.setFilteredProjectList(filteredProjectList);

		if (npdCategoryList != null && npdCategoryList.size() > 0) {
			productCreationBean.setNpdCategoryList(npdCategoryList);
		}
		if (prdCategoryList != null && prdCategoryList.size() > 0) {
			productCreationBean.setPrdCategoryList(prdCategoryList);
		}
		if (projectList != null && projectList.size() > 0) {
			productCreationBean.setProjectList(projectList);
		}
		//CommonBaseDao.closeTrans(hibernateSession);
		return searchDto;
	}

	// to set the value of npdcategoryList and other field variables in serach
	// page for project.

	public ProductCreationBean initSearchProduct(
			ProductCreationBean productCreationBean) throws Exception {

		CommonBaseDao commonBaseDao = (CommonBaseDao) BaseDaoFactory
				.getInstance(DaoConstants.BASE_DAO_CLASS);
		Session hibernateSession = null;
		hibernateSession = CommonBaseDao.beginTrans();

		ProductCreationDao productCreationDao = (ProductCreationDao) BaseDaoFactory
				.getInstance(DaoConstants.PRODUCT_CREATION_DAO);

		TtrnProject searchDto = initSearchProductUtility(hibernateSession,
				productCreationBean);
		
		int searchProjStatus=productCreationBean.getSearchProjectStatus();
		searchDto.setProjectstatus(searchProjStatus);
		
		String searchProjPriority=productCreationBean.getSearchPriority();
		searchDto.setLaunchPriority(searchProjPriority);

		ArrayList<TtrnProject> filteredProjectList = productCreationDao
				.getSearchProjectList2(hibernateSession, searchDto);
		productCreationBean.setFilteredProjectList(filteredProjectList);

		commonBaseDao.closeTrans(hibernateSession);

		return productCreationBean;
	}

	public ProductCreationBean initSearchProductFinalizeNewPlan(
			ProductCreationBean productCreationBean) throws Exception {

		CommonBaseDao commonBaseDao = (CommonBaseDao) BaseDaoFactory
				.getInstance(DaoConstants.BASE_DAO_CLASS);
		Session hibernateSession = null;
		hibernateSession = CommonBaseDao.beginTrans();

		ProductCreationDao productCreationDao = (ProductCreationDao) BaseDaoFactory
				.getInstance(DaoConstants.PRODUCT_CREATION_DAO);

		TtrnProject searchDto = initSearchProductUtility(hibernateSession,
				productCreationBean);
		searchDto.setCreatedby(Integer.parseInt(productCreationBean
				.getCreatedBy()));
		ArrayList<TtrnProject> filteredProjectList = productCreationDao
				.getProductsFinalizeNewPlan(hibernateSession, searchDto);
		productCreationBean.setFilteredProjectList(filteredProjectList);

		commonBaseDao.closeTrans(hibernateSession);
		return productCreationBean;
	}

	public ProductCreationBean initSearchEditBaselinedPlan(
			ProductCreationBean productCreationBean) throws Exception {

		CommonBaseDao commonBaseDao = (CommonBaseDao) BaseDaoFactory
				.getInstance(DaoConstants.BASE_DAO_CLASS);
		Session hibernateSession = null;
		hibernateSession = CommonBaseDao.beginTrans();

		ProductCreationDao productCreationDao = (ProductCreationDao) BaseDaoFactory
				.getInstance(DaoConstants.PRODUCT_CREATION_DAO);

		TtrnProject searchDto = initSearchProductUtility(hibernateSession,
				productCreationBean);
		ArrayList<TtrnProject> filteredProjectList = productCreationDao
				.getProductsEditBaselinedPlan(hibernateSession, searchDto);
		productCreationBean.setFilteredProjectList(filteredProjectList);

		commonBaseDao.closeTrans(hibernateSession);
		return productCreationBean;
	}

	public ProductCreationBean initSearchUpdationAction(
			ProductCreationBean productCreationBean) throws Exception {

		CommonBaseDao commonBaseDao = (CommonBaseDao) BaseDaoFactory
				.getInstance(DaoConstants.BASE_DAO_CLASS);
		Session hibernateSession = null;
		hibernateSession = CommonBaseDao.beginTrans();

		ProductCreationDao productCreationDao = (ProductCreationDao) BaseDaoFactory
				.getInstance(DaoConstants.PRODUCT_CREATION_DAO);

		TtrnProject searchDto = initSearchProductUtility(hibernateSession,
				productCreationBean);
		ArrayList<TtrnProject> filteredProjectList = productCreationDao
				.getProductsForUpdateAction(hibernateSession, searchDto);
		productCreationBean.setFilteredProjectList(filteredProjectList);

		commonBaseDao.closeTrans(hibernateSession);
		return productCreationBean;
	}

	public ProductCreationBean initSearchProductUpdation(
			ProductCreationBean productCreationBean) throws Exception {

		CommonBaseDao commonBaseDao = (CommonBaseDao) BaseDaoFactory
				.getInstance(DaoConstants.BASE_DAO_CLASS);
		Session hibernateSession = null;
		hibernateSession = CommonBaseDao.beginTrans();

		ProductCreationDao productCreationDao = (ProductCreationDao) BaseDaoFactory
				.getInstance(DaoConstants.PRODUCT_CREATION_DAO);

		TtrnProject searchDto = initSearchProductUtility(hibernateSession,
				productCreationBean);
		ArrayList<TtrnProject> filteredProjectList = productCreationDao
				.getUpdateProductList(hibernateSession, searchDto);
		productCreationBean.setFilteredProjectList(filteredProjectList);

		commonBaseDao.closeTrans(hibernateSession);
		return productCreationBean;
	}

	// to set the value of other field variables in Update Project page.

	public ProductCreationBean initUpdateProject(long projectId)
			throws Exception {

		CommonBaseDao commonBaseDao = (CommonBaseDao) BaseDaoFactory
				.getInstance(DaoConstants.BASE_DAO_CLASS);
		Session hibernateSession = null;
		hibernateSession = CommonBaseDao.beginTrans();
		TtrnProject ttrnProject = new TtrnProject();
		ProductCreationBean productCreationBean = new ProductCreationBean();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				AppConstants.DATE_FORMAT_PROC);

		ttrnProject = (TtrnProject) commonBaseDao.findById(projectId,
				HibernateBeansConstants.HBM_PROJECT, hibernateSession);
		productCreationBean.setProjectId(new Long(ttrnProject.getProjectid())
				.intValue());
		productCreationBean.setProjectName(ttrnProject.getProjectName());
		productCreationBean.setProductBrief(ttrnProject.getProductBrief());
		productCreationBean.setTargetMarket(ttrnProject.getTargetMkt());
		productCreationBean
				.setAirtelPotential(ttrnProject.getAirtelPotential());
		productCreationBean.setTotalMktPotential(ttrnProject
				.getTotalMktPotential());
		productCreationBean.setCapexRequirement(ttrnProject.getCapexReq());
		if (ttrnProject.getExpectedLaunchDate() != null)
			productCreationBean.setLaunchDate(simpleDateFormat
					.format(ttrnProject.getExpectedLaunchDate()));
		if (ttrnProject.getStartDate() != null)
			productCreationBean.setPrjStartDate(simpleDateFormat
					.format(ttrnProject.getStartDate()));
		CommonBaseDao.closeTrans(hibernateSession);
		return productCreationBean;
	}

	//
	public ProductCreationBean initDraftProject(long projectId)
			throws NpdException {

		Connection conn = null;
		ProductCreationBean productCreationBean = null;
		try {
			conn = NpdConnection.getConnectionObject();

			ProductCreationDao dao = new ProductCreationDao();
			TtrnProject bean = dao.getDraftProject(conn, projectId);
			if (bean == null) {
				productCreationBean = null;
			} else {
				productCreationBean = new ProductCreationBean();
				productCreationBean.setProjectId(bean.getProjectid());
				productCreationBean.setProductBrief(bean.getProductBrief());
				productCreationBean.setTargetMarket(bean.getTargetMkt());
				productCreationBean.setAirtelPotential(bean
						.getAirtelPotential());
				productCreationBean.setTotalMktPotential(bean
						.getTotalMktPotential());
				productCreationBean.setCapexRequirement(bean.getCapexReq());
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
				productCreationBean.setLaunchDate(sdf.format(bean
						.getExpectedLaunchDate()));
				productCreationBean.setLaunchPriority(bean.getLaunchPriority());
				productCreationBean.setPrdCategoryId(bean.getPrdCategoryId());
				productCreationBean.setUpdationRequestedByName(bean
						.getUpdationRequestedByName());
				productCreationBean.setUpdationRequestedBy(bean
						.getUpdationRequestedBy());
				productCreationBean.setUpdationRequestedByEmail(bean
						.getUpdationRequestedByEmail());

			}

		} catch (Exception ex) {
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in initDraftProject method of "
					+ this.getClass().getSimpleName()
					+ AppUtility.getStackTrace(ex));
			throw new NpdException(
					"Exception occured in initDraftProject method of "
							+ this.getClass().getSimpleName());
		} finally {
			try {
				NpdConnection.freeConnection(conn);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				AppConstants.NPDLOGGER.error(e.getMessage()
						+ " Exception occured in initDraftProject method of "
						+ this.getClass().getSimpleName()
						+ AppUtility.getStackTrace(e));
				throw new NpdException(
						"Exception occured in initDraftProject method of "
								+ this.getClass().getSimpleName());
			}
		}

		return productCreationBean;
	}

	// to get the list of Projects which satisfy the filtered criteria
	public ProductCreationBean searchProduct111(
			ProductCreationBean productCreationBean) throws Exception {

		ArrayList projectDetailList = null;
		ProductCreationDao productCreationDao = (ProductCreationDao) BaseDaoFactory
				.getInstance(DaoConstants.PRODUCT_CREATION_DAO);
		Session hibernateSession = null;
		hibernateSession = CommonBaseDao.beginTrans();
		projectDetailList = productCreationDao.getSearchProjectList(
				productCreationBean, hibernateSession);

		productCreationBean.setProjectDetailList(projectDetailList);

		return productCreationBean;
	}

	public TtrnProject getProduct(String projectId) throws Exception {
		CommonBaseDao commonBaseDao = (CommonBaseDao) BaseDaoFactory
				.getInstance(DaoConstants.BASE_DAO_CLASS);

		Session hibernateSession = CommonBaseDao.beginTrans();
		TtrnProject ttrnProject = new TtrnProject();

		ttrnProject = (TtrnProject) commonBaseDao.findById(Long
				.parseLong(projectId), HibernateBeansConstants.HBM_PROJECT,
				hibernateSession);
		CommonBaseDao.closeTrans(hibernateSession);
		return ttrnProject;
	}

	public int updateProduct(ProductCreationBean productCreationBean)
			throws NpdException {
		int status = 0;
		Connection conn = null;

		try {
			conn = NpdConnection.getConnectionObject();
			conn.setAutoCommit(false);
			ProductCreationDao dao = new ProductCreationDao();
			status = dao.updateProject(conn, productCreationBean);
			conn.commit();

		} catch (Exception ex) {
			try {
				conn.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
				throw new NpdException("Exception : " + e.getMessage(), e);
			}
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(ex));
			throw new NpdException("Exception : " + ex.getMessage(), ex);

		} finally {
			try {
				NpdConnection.freeConnection(conn);
			} catch (Exception ex) {
				ex.printStackTrace();
				AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(ex));
				throw new NpdException("Exception : " + ex.getMessage(), ex);
			}
		}

		return status;
	}

	public boolean insertProduct(ProductCreationBean productCreationBean)
			throws NpdException {
		boolean insert = false;
		Date expectedLaunchDate = null;
		// Date projStartDate = null;
		// Calendar startDateCal = Calendar.getInstance();
		// Calendar exptLaunchDateCal = Calendar.getInstance();
		// Calendar modifiedDateCal = Calendar.getInstance();
		Session hibernateSession = null;
		ProductCreationDao productCreationDao = new ProductCreationDao();
		CommonBaseDao commonBaseDao = BaseDaoFactory
				.getInstance(DaoConstants.BASE_DAO_CLASS);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		Connection conn = null;

		try {
			hibernateSession = CommonBaseDao.beginTrans();


			insert = productCreationDao.saveProduct(productCreationBean,
					hibernateSession);

			// import workflow and get projectworkflowid
			long productId = productCreationBean.getProjectId();
			long npdCatgeotyId = productCreationBean.getNpdCategoryId();
			long workflowId = 0;// get corres to npdCatgeotyId

			conn = NpdConnection.getConnectionObject();
			conn.setAutoCommit(false);

			WorkFlowDao daoObj = new WorkFlowDao();

			workflowId = daoObj.getWorkFlowIdForNpdId(conn, npdCatgeotyId);// get
																			// corres
																			// to
																			// npdCatgeotyId
			if (workflowId == -1) {
				// error
				throw new NpdException(
						"Exception occured in insertProduct method of "
								+ this.getClass().getSimpleName()
								+ "  fetching workflowId for npdId");
			}

			long projectWorkflowId = daoObj.importWorkFlow(conn, workflowId);

			if (projectWorkflowId == -1) {
				// error
				throw new NpdException(
						"Exception occured in insertProduct method of "
								+ this.getClass().getSimpleName()
								+ "  importing workflow");
			}
			// insert mapping in projectworkflowdet with status draft
			int status = daoObj.mapProductWithWorkflow(conn, productId,
					projectWorkflowId);
			if (status == -1) {
				// error
				throw new NpdException(
						"Exception occured in insertProduct method of "
								+ this.getClass().getSimpleName()
								+ "  inserting mapping ");
			}

			conn.commit();
			productCreationBean.setProjectWorkflowId(projectWorkflowId);
			System.out.println(insert);

			/*
			 * CallableStatement cst =
			 * hibernateSession.connection().prepareCall("{call
			 * NPD.SAVEPRODUCT(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			 * cst.registerOutParameter(14, Types.VARCHAR); cst.setInt(1, 10);
			 * cst.setInt(2, productCreationBean.getNpdCategoryId());
			 * cst.setString(3,productCreationBean.getProjectName());
			 * cst.setInt(4,productCreationBean.getPrdCategoryId());
			 * cst.setString(5,productCreationBean.getAirtelPotential());
			 * cst.setString(6, productCreationBean.getCapexRequirement());
			 * cst.setString(7, productCreationBean.getTotalMktPotential());
			 * cst.setString(8, productCreationBean.getTotalMktPotential());
			 * cst.setDate(9, date1); cst.setString(10,
			 * productCreationBean.getLaunchPriority()); cst.setString(11,
			 * productCreationBean.getProductBrief()); cst.setDate(12,
			 * modifiedDate); cst.setDate(13, sDate); cst.execute();
			 * System.out.println(cst.getString(13)); String res =
			 * cst.getString(13);
			 */

		} catch (Exception ex) {
			hibernateSession.getTransaction().rollback();
			try {
				conn.rollback();
			} catch (SQLException e) {

				e.printStackTrace();
				throw new NpdException("Exception : " + e.getMessage(), e);
			}

			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(ex));
			throw new NpdException("Exception : " + ex.getMessage(), ex);

		} finally {
			try
			{
				NpdConnection.freeConnection(conn);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				commonBaseDao.closeTrans(hibernateSession);
			}
		}

		return insert;
	}

	public int actionForUpdateProduct(ProductCreationBean productCreationBean)
			throws NpdException {
		int status = 0;
		Connection conn = null;

		try {
			conn = NpdConnection.getConnectionObject();
			conn.setAutoCommit(false);
			ProductCreationDao dao = new ProductCreationDao();

			String updateActionType = productCreationBean.getUpdateActionType();
			String value = null;
			if (ProductCreationBean.APPROVE_OVERRIDE.equals(updateActionType)) {
				value = "APPROVE";
			} else if (ProductCreationBean.REJECT.equals(updateActionType)) {
				value = "REJECT";
			}
			status = dao.actionForUpdateProduct(conn, productCreationBean,
					value);
			conn.commit();

		} catch (Exception ex) {
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
				AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
				throw new NpdException("Exception : " + e.getMessage(), e);
			}
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(ex));
			throw new NpdException("Exception : " + ex.getMessage(), ex);

		} finally {
			try {
				NpdConnection.freeConnection(conn);
			} catch (Exception e) {
				e.printStackTrace();
				AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
				throw new NpdException("Exception : " + e.getMessage(), e);
			}
		}

		return status;
	}

	public void sendMailForProjectUpdation(
			ProductCreationBean productCreationBean) throws NpdException {
		int status = -1;
		Connection conn = null;
		Session hibernateSession = null;
		try {
			conn = NpdConnection.getConnectionObject();
			conn.setAutoCommit(false);

			CommonBaseDao commonBaseDao = (CommonBaseDao) BaseDaoFactory
					.getInstance(DaoConstants.BASE_DAO_CLASS);
			hibernateSession = CommonBaseDao.beginTrans();

			SendMailUtility oSendMail = new SendMailUtility();
			ArrayList toList = new ArrayList();
			ArrayList ccList = new ArrayList();

			AttachEditProjectPlanDao daoObj = new AttachEditProjectPlanDao();

			// get EmailId of employees
			String projectWorkflowId = null;
			AttachEditProjectPlanDao dao = new AttachEditProjectPlanDao();
			TtrnProjectworkflow ttrnProjectworkflow = dao
					.getActiveProjectWorkflowId(conn, String
							.valueOf(productCreationBean.getProjectId()));
			projectWorkflowId = String.valueOf(ttrnProjectworkflow
					.getProjectworkflowid());

			ArrayList<TmEmployee> employeeList = new ArrayList<TmEmployee>();
			employeeList = daoObj
					.getEmployeesOfProject(conn, projectWorkflowId);
			for (TmEmployee employee : employeeList) {
				toList.add(employee.getEmail());
			}

			// get EmailId of user who raised the request
			String empId = productCreationBean.getUpdationRequestedBy();
			TmEmployee tmEmployee = new TmEmployee();
			tmEmployee = (TmEmployee) commonBaseDao.findById((Long
					.parseLong(empId)), HibernateBeansConstants.HBM_EMPLOYEE,
					hibernateSession);
			toList.add(tmEmployee.getEmail());

			String updateActionType = productCreationBean.getUpdateActionType();
			String eSubject = null;
			StringBuffer eBody = new StringBuffer();
			if (ProductCreationBean.APPROVE_OVERRIDE.equals(updateActionType)) {
				eSubject = "Product Details Updated.";
			} else if (ProductCreationBean.REJECT.equals(updateActionType)) {
				eSubject = "Product Updation Request Rejected";
			}

			eBody.append("<HTML><BODY>");
			eBody.append("<TABLE>");

			eBody.append("<TR><TD>");
				eBody.append("<TABLE>");
					eBody.append("<TR>");
						eBody.append("<TD>");
							eBody.append(Messages.getMessageValue("Mail_Header")+"<BR>");
						eBody.append("</TD>");
					eBody.append("</TR>");
				eBody.append("</TABLE>");
			eBody.append("</TD></TR>");
		
			eBody.append("<TR><TD>");
			eBody.append("<TABLE>");
			eBody.append("<TR>");
			eBody.append("<TD bgcolor=\"#FF9255\">");

			if (ProductCreationBean.APPROVE_OVERRIDE.equals(updateActionType)) {
				eBody
						.append("Request For Product Details Updations has been Approved/Overrided . New Details Are as Follows :");
			} else if (ProductCreationBean.REJECT.equals(updateActionType)) {
				eBody
						.append("Request For Product Details Updations has been Rejected. Product Details are :");
			}

			eBody.append("</TD>");
			eBody.append("</TR>");
			eBody.append("</TABLE>");
			eBody.append("</TD></TR>");
			// fetch project details and set
			StringBuffer productDetails = new StringBuffer();

			eBody.append("<TR><TD>");

			eBody.append("</TD></TR>");

			AttachEditProjectPlanModel aPPModel = new AttachEditProjectPlanModel();
			eBody.append(aPPModel.getProjectDetailString(productCreationBean
					.getProjectId(), hibernateSession));

			eBody.append("</TD></TR>");
			// fetch plan details and set
			StringBuffer planDetails = new StringBuffer();
			// StringBuffer taskRow=new StringBuffer();
			eBody.append("<TR><TD>");
			eBody.append("<TABLE>");
			eBody.append("<TR>");
			eBody.append("<TD>");
			eBody.append("Project Plan is as follows :");
			eBody.append("</TD>");
			eBody.append("</TR>");
			eBody.append("</TABLE>");
			eBody.append("</TD></TR>");
			eBody.append("<TR><TD>");

			eBody.append(aPPModel.getProjectPlanMailString(projectWorkflowId,
					conn));

			eBody.append("</TD></TR>");
			StringBuffer toMessage = new StringBuffer();
			StringBuffer fromMessage = new StringBuffer();

			eBody.append("<TR><TD>");
			eBody.append("<TABLE>");
			eBody.append("<TR><TD>");
				eBody.append("<TABLE>");
					eBody.append("<TR>");
						eBody.append("<TD>");
							eBody.append("<BR>"+Messages.getMessageValue("Mail_Footer")+"<BR>");
						eBody.append("</TD>");
					eBody.append("</TR>");
				eBody.append("</TABLE>");
			eBody.append("</TD></TR>");
			eBody.append("</TABLE>");
			eBody.append("</TD></TR>");

			eBody.append("</TABLE>");
			eBody.append("</BODY></HTML>");
			// eBody=toMessage.append(productDetails).append(planDetails).append(fromMessage).toString();

			oSendMail.setOToList(toList);
			oSendMail.setOCcList(ccList);
			oSendMail.setStrSubject(eSubject);
			oSendMail.setStrMessage(eBody.toString());
			oSendMail.sendMessageWithAttachment(null);

			conn.commit();
		}

		catch (Exception ex) {
			try {
				conn.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				AppConstants.NPDLOGGER
						.error(e.getMessage()
								+ " Exception occured in sendMailForProjectUpdation method of "
								+ this.getClass().getSimpleName()
								+ AppUtility.getStackTrace(e));
				throw new NpdException(
						"Exception occured in sendMailForProjectUpdation method of "
								+ this.getClass().getSimpleName());
			}
			ex.printStackTrace();
			AppConstants.NPDLOGGER
					.error(ex.getMessage()
							+ " Exception occured in sendMailForProjectUpdation method of "
							+ this.getClass().getSimpleName()
							+ AppUtility.getStackTrace(ex));
			throw new NpdException(
					"Exception occured in sendMailForProjectUpdation method of "
							+ this.getClass().getSimpleName());
		} finally {
			try {
				//hibernateSession.close();
				NpdConnection.freeConnection(conn);
			} catch (Exception e) {
				e.printStackTrace();
				AppConstants.NPDLOGGER
						.error(e.getMessage()
								+ " Exception occured in sendMailForProjectUpdation method of "
								+ this.getClass().getSimpleName()
								+ AppUtility.getStackTrace(e));
				throw new NpdException(
						"Exception occured in sendMailForProjectUpdation method of "
								+ this.getClass().getSimpleName());
			}finally
			{
				try{hibernateSession.close();}
				catch(Exception e)
				{
					e.printStackTrace();
					AppConstants.NPDLOGGER.error(e.getMessage()
							+ " Exception occured in sendMailForProjectUpdation method of "
							+ this.getClass().getSimpleName()+AppUtility.getStackTrace(e));
					throw new NpdException("Exception occured in sendMailForProjectUpdation method of "
							+ this.getClass().getSimpleName()) ;

				}
			}
		}

	}

}
