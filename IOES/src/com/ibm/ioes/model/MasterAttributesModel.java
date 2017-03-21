package com.ibm.ioes.model;

import java.sql.Connection;
import java.util.ArrayList;

import com.ibm.ioes.beans.MasterAttributesBean;
import com.ibm.ioes.dao.MasterAttributesDao;
import com.ibm.ioes.exception.IOESException;
import com.ibm.ioes.forms.MastersAttributesDto;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Utility;

public class MasterAttributesModel {

	public ArrayList<MastersAttributesDto> viewAttributesList()
			throws Exception {
		ArrayList<MastersAttributesDto> objList = new ArrayList<MastersAttributesDto>();
		MasterAttributesDao objDao = new MasterAttributesDao();
		try {
			objList = objDao.viewAttributesList();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("SQL Exception : " + ex.getMessage(), ex);
		}
		return objList;
	}

	public String updateMainAttributes(
			ArrayList<MastersAttributesDto> objDtoList) throws Exception {

		MasterAttributesDao objDao = new MasterAttributesDao();
		String result = "";
		try {
			result = objDao.updateMainAttributes(objDtoList);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("SQL Exception : " + ex.getMessage(), ex);
		}
		return result;
	}

	public void loadServices(MasterAttributesBean objForm) throws IOESException {

		Connection conn = null;
		String methodName = "loadServices", className = "MasterAttributesModel", msg = "";
		boolean logToFile = true, logToConsole = true;
		try {
			conn = DbConnection.getConnectionObject();
			objForm.setServices(UtilityModel.getAllServices(conn));

		} catch (Exception ex) {
			throw Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg,
					logToFile, logToConsole);
		} finally {
			try {
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				throw Utility.onEx_LOG_RET_NEW_EX(e, methodName, className,
						msg, logToFile, logToConsole);
			}
		}

	}

	public ArrayList<MastersAttributesDto> getServiceAttributeList(
			MasterAttributesBean objForm) throws IOESException {
		ArrayList<MastersAttributesDto> attributes = new ArrayList<MastersAttributesDto>();
		try {
			MasterAttributesDao objDao = new MasterAttributesDao();
			attributes = objDao.getServiceAttributeList(objForm);
		} catch (Exception ex) {

			ex.printStackTrace();
		}
		return attributes;
	}

	public String updateServiceAttributes(
			ArrayList<MastersAttributesDto> objDtoList) throws IOESException {
		String status = "";
		try {
			MasterAttributesDao objDao = new MasterAttributesDao();
			status = objDao.updateServiceAttributes(objDtoList);

		} catch (Exception ex) {

			ex.printStackTrace();
		}
		return status;
	}

	public ArrayList<MastersAttributesDto> loadProducts(
			MastersAttributesDto objDto) throws IOESException {

		ArrayList<MastersAttributesDto> objDtoList = new ArrayList<MastersAttributesDto>();
		try {
			MasterAttributesDao objDao = new MasterAttributesDao();
			objDtoList = objDao.loadProducts(objDto);

		} catch (Exception ex) {

			ex.printStackTrace();
		}
		return objDtoList;
	}

	public ArrayList<MastersAttributesDto> getProductAttributeList(
			MastersAttributesDto objDto) throws IOESException {
		ArrayList<MastersAttributesDto> attributes = new ArrayList<MastersAttributesDto>();
		try {
			MasterAttributesDao objDao = new MasterAttributesDao();
			attributes = objDao.getProductAttributeList(objDto);
		} catch (Exception ex) {

			ex.printStackTrace();
		}
		return attributes;
	}

	public String updateProductAttributes(
			ArrayList<MastersAttributesDto> objDtoList) throws IOESException {
		String status = "";
		try {
			MasterAttributesDao objDao = new MasterAttributesDao();
			status = objDao.updateProductAttributes(objDtoList);

		} catch (Exception ex) {

			ex.printStackTrace();
		}
		return status;
	}
	public ArrayList<MastersAttributesDto> getProductConfigAttList(
			MastersAttributesDto objDto) throws IOESException {
			ArrayList<MastersAttributesDto> attributes = new ArrayList<MastersAttributesDto>();
			try 
			{
				MasterAttributesDao objDao = new MasterAttributesDao();
				attributes = objDao.getProductConfigAttList(objDto);
			} catch (Exception ex) {

			ex.printStackTrace();
		}
		return attributes;
	}
	public String updateProductConfigAttributes(
			ArrayList<MastersAttributesDto> objDtoList) throws IOESException {
		String status = "";
		try {
			MasterAttributesDao objDao = new MasterAttributesDao();
			status = objDao.updateProductConfigAttributes(objDtoList);

		} catch (Exception ex) {

			ex.printStackTrace();
		}
		return status;
	}

}
