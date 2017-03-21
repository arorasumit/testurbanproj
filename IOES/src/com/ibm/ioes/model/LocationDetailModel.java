package com.ibm.ioes.model;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Map;
import java.util.Hashtable;

import com.ibm.ioes.beans.LocationDetailBean;
import com.ibm.ioes.beans.NewOrderBean;
import com.ibm.ioes.dao.LocationDetailDao;
import com.ibm.ioes.dao.NewOrderDao;
import com.ibm.ioes.exception.IOESException;
import com.ibm.ioes.forms.CityDto;
import com.ibm.ioes.forms.CountryDto;
import com.ibm.ioes.forms.LocationDetailDto;
import com.ibm.ioes.forms.NewOrderDto;
import com.ibm.ioes.forms.StateDto;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Utility;

public class LocationDetailModel 
{
	public ArrayList<LocationDetailDto> displayAccountDetails(LocationDetailDto objDto) throws Exception
	{
		ArrayList<LocationDetailDto> listAccountDetails= new ArrayList<LocationDetailDto>();
		LocationDetailDao objDao = new LocationDetailDao();
		try
		{
			listAccountDetails = objDao.getAccountDetails(objDto);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listAccountDetails;
	}
	public ArrayList<LocationDetailDto> displayLocationDetails(LocationDetailDto objDto) throws Exception
	{
		ArrayList<LocationDetailDto> listAccountDetails= new ArrayList<LocationDetailDto>();
		LocationDetailDao objDao = new LocationDetailDao();
		try
		{
			listAccountDetails = objDao.getLocationDetails(objDto);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listAccountDetails;
	}
	
		public ArrayList<LocationDetailDto> viewLocationList(LocationDetailDto objDto)  throws Exception
		{
			ArrayList<LocationDetailDto> objList = new ArrayList<LocationDetailDto>();
			LocationDetailDao objDao = new LocationDetailDao();
			try
			{
				objList = objDao.viewLocationList(objDto);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			return objList;
		}
		public LocationDetailDto viewUserProfile(int userId) throws Exception
		{
			LocationDetailDao objDao = new LocationDetailDao();
			LocationDetailDto objDto=  new LocationDetailDto();
			try
			{
				
				objDto = objDao.viewUserProfile(userId);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			return objDto;
		}
		public String addNewCustomerLocation(LocationDetailDto objDto) throws Exception
		{
			String success="";
			LocationDetailDao objDao = new LocationDetailDao();
			try
			{
				success = objDao.addNewCustomerLocation(objDto);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			return success;
		}
		
		public String updateCustomerLocation(LocationDetailDto objDto) throws Exception
		{
			String success="";
			LocationDetailDao objDao = new LocationDetailDao();
			try
			{
				success = objDao.updateCustomerLocation(objDto);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			return success;
		}
		public void loadcountries(LocationDetailBean objForm) throws IOESException {
			
			Connection conn=null;
			String methodName="loadcountries", className="LocationDetailModel", msg="";
			boolean logToFile=true, logToConsole=true;
			try
			{
				conn = DbConnection.getConnectionObject();
				objForm.setCountries(UtilityModel.getAllCountry(conn));
	
			}
			catch(Exception ex)
			{
				throw Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);
			}
			finally
			{
				try {
					DbConnection.freeConnection(conn);
				} catch (Exception e) {
					throw Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);
				}
			}
			
		}
		
		

	public void loadStates(LocationDetailBean objForm) throws IOESException {

		Connection conn = null;
		String methodName = "loadStates", className = "LocationDetailModel", msg = "";
		boolean logToFile = true, logToConsole = true;
		CountryDto country = new CountryDto();
		try {
			if(objForm.getEditddCountry() != null && objForm.getEditddCountry() != ""){
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
	
	public void loadCities(LocationDetailBean objForm) throws IOESException {

		Connection conn = null;
		String methodName = "loadCities", className = "LocationDetailModel", msg = "";
		boolean logToFile = true, logToConsole = true;
		StateDto state = new StateDto();
		try {
			if(objForm.getEditddState() != null && objForm.getEditddState() != ""){
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


		public static ArrayList<StateDto> validateCountrySeleted(CountryDto countryDto) {
			// TODO Auto-generated method stub
			return null;
		}
		public static ArrayList<CityDto> validateStateSeleted(StateDto stateDto) {
			// TODO Auto-generated method stub
			return null;
		}
		
}
