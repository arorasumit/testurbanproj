package com.ibm.ioes.model;

import java.sql.Connection;
import java.util.ArrayList;

import com.ibm.ioes.beans.DispatchAddressBean;
import com.ibm.ioes.beans.LocationDetailBean;
import com.ibm.ioes.dao.DispatchAddressDao;
import com.ibm.ioes.exception.IOESException;
import com.ibm.ioes.forms.CityDto;
import com.ibm.ioes.forms.CountryDto;
import com.ibm.ioes.forms.DispatchAddressDto;
import com.ibm.ioes.forms.StateDto;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Utility;

public class DispatchAddressModel {
	public ArrayList<DispatchAddressDto> displayAccountDetails(
			DispatchAddressDto objDto) throws Exception {
		ArrayList<DispatchAddressDto> listAccountDetails = new ArrayList<DispatchAddressDto>();
		DispatchAddressDao objDao = new DispatchAddressDao();
		try {
			listAccountDetails = objDao.getAccountDetails(objDto);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return listAccountDetails;
	}

	public ArrayList<DispatchAddressDto> displayDispatchAddressDetails(
			DispatchAddressDto objDto) throws Exception {
		ArrayList<DispatchAddressDto> listAccountDetails = new ArrayList<DispatchAddressDto>();
		DispatchAddressDao objDao = new DispatchAddressDao();
		try {
			listAccountDetails = objDao.getDispatchAddressDetails(objDto);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return listAccountDetails;
	}

	public ArrayList<DispatchAddressDto> viewDispatchAddressList(
			DispatchAddressDto objDto) throws Exception {
		ArrayList<DispatchAddressDto> objList = new ArrayList<DispatchAddressDto>();
		DispatchAddressDao objDao = new DispatchAddressDao();
		try {
			objList = objDao.viewDispatchAddressList(objDto);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return objList;
	}

	public DispatchAddressDto viewDispatchAddressProfile(int userId)
			throws Exception {
		DispatchAddressDao objDao = new DispatchAddressDao();
		DispatchAddressDto objDto = new DispatchAddressDto();
		try {
			objDto = objDao.viewDispatchAddressProfile(userId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return objDto;
	}

	public String addNewDispatchAddress(DispatchAddressDto objDto)
			throws Exception {
		String success = "";
		DispatchAddressDao objDao = new DispatchAddressDao();
		try {
			success = objDao.addNewDispatchAddress(objDto);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return success;
	}

	public String updateDispatchAddress(DispatchAddressDto objDto)
			throws Exception {
		String success = "";
		DispatchAddressDao objDao = new DispatchAddressDao();
		try {
			success = objDao.updateDispatchAddress(objDto);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return success;
	}

	public void loadcountries(DispatchAddressBean objForm) throws IOESException {

		Connection conn = null;
		String methodName = "loadcountries", className = "DispatchAddressModel", msg = "";
		boolean logToFile = true, logToConsole = true;
		try {
			conn = DbConnection.getConnectionObject();
			objForm.setCountries(UtilityModel.getAllCountry(conn));

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

	public static ArrayList<StateDto> validateCountrySeleted(
			CountryDto countryDto) {
		// TODO Auto-generated method stub
		return null;
	}

	public static ArrayList<CityDto> validateStateSeleted(StateDto stateDto) {
		// TODO Auto-generated method stub
		return null;
	}

	public void loadStates(DispatchAddressBean objForm) throws IOESException {

		Connection conn = null;
		String methodName = "loadStates", className = "DispatchAddressModel", msg = "";
		boolean logToFile = true, logToConsole = true;
		CountryDto country = new CountryDto();
		try {
			if (objForm.getEditddCountry() != null
					&& objForm.getEditddCountry() != "") {
				country.setCountryCode(new Long(objForm.getEditddCountry()));
			}
			conn = DbConnection.getConnectionObject();
			objForm.setStates(UtilityModel.getAllStates(conn, country));

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

	public void loadCities(DispatchAddressBean objForm) throws IOESException {

		Connection conn = null;
		String methodName = "loadCities", className = "DispatchAddressModel", msg = "";
		boolean logToFile = true, logToConsole = true;
		StateDto state = new StateDto();
		try {
			if (objForm.getEditddState() != null
					&& objForm.getEditddState() != "") {
				state.setStateId(new Long(objForm.getEditddState()));
			}
			conn = DbConnection.getConnectionObject();
			objForm.setCities(UtilityModel.getAllCities(conn, state));

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
}
