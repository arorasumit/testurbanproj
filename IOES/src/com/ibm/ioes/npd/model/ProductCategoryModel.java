package com.ibm.ioes.npd.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ibm.ioes.npd.exception.NpdException;
import com.ibm.ioes.npd.hibernate.beans.ProductCategoryDto;
import com.ibm.ioes.npd.hibernate.dao.ProductCategoryDao;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.NpdConnection;

public class ProductCategoryModel extends CommonBaseModel {

	public ArrayList<ProductCategoryDto> getProductCategoryList() throws NpdException {
		ArrayList<ProductCategoryDto> productCategoryDtoList = null;
		Connection connection = null;
		try {
			connection = NpdConnection.getConnectionObject();
			connection.setAutoCommit(false);
			ProductCategoryDao productCategoryDao = new ProductCategoryDao();
			productCategoryDtoList = productCategoryDao
					.getProductCategoryList(connection);
		} catch (Exception ex) {
			try {
				connection.rollback();
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
				NpdConnection.freeConnection(connection);
			} catch (Exception ex) {
				ex.printStackTrace();
				AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(ex));
				throw new NpdException("Exception : " + ex.getMessage(), ex);
			}
		}

		return productCategoryDtoList;

	}

	public int updateProductCategory(String[] productIdArr, String[] statusArr, String[] descriptionArr, int createdBy) throws NpdException {
		int updateStatus = 0;
		Connection connection = null;
		try {
			connection = NpdConnection.getConnectionObject();
			connection.setAutoCommit(false);
			ProductCategoryDao productCategoryDao = new ProductCategoryDao();
			updateStatus = productCategoryDao
					.updateProductCategory(productIdArr, statusArr, descriptionArr, createdBy, connection );
		} catch (Exception ex) {
			try {
				connection.rollback();
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
				NpdConnection.freeConnection(connection);
			} catch (Exception ex) {
				ex.printStackTrace();
				AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(ex));
				throw new NpdException("Exception : " + ex.getMessage(), ex);
			}
		}

		return updateStatus;
	}

	public int addProductCategory(ProductCategoryDto productCategoryDto, int createdBy) throws NpdException {
		int addStatus = 0;
		Connection connection = null;
		try {
			connection = NpdConnection.getConnectionObject();
			connection.setAutoCommit(false);
			ProductCategoryDao productCategoryDao = new ProductCategoryDao();
			addStatus = productCategoryDao
					.addProductCategory(productCategoryDto,createdBy, connection );
		} catch (Exception ex) {
			try {
				connection.rollback();
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
				NpdConnection.freeConnection(connection);
			} catch (Exception ex) {
				ex.printStackTrace();
				AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(ex));
				throw new NpdException("Exception : " + ex.getMessage(), ex);
			}
		}

		return addStatus;
	}

}
