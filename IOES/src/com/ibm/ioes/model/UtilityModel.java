package com.ibm.ioes.model;

import java.sql.Connection;
import java.util.ArrayList;

import com.ibm.ioes.dao.CommonBaseDao;
import com.ibm.ioes.exception.IOESException;
import com.ibm.ioes.forms.CityDto;
import com.ibm.ioes.forms.CountryDto;
import com.ibm.ioes.forms.MastersAttributesDto;
import com.ibm.ioes.forms.StateDto;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Utility;



public class UtilityModel 
{
/**
 * returns all country
 * conn is optional , if passed it is used without closing 
 * if not passsed , a new is created and closed in last
 * @param connection
 * @throws IOESException
 */
	public static ArrayList<CountryDto> getAllCountry(Connection connection) throws IOESException {
		Connection conn=null;
		boolean conn_local=false;
		String methodName="getAllCountry", className="UtilityModel", msg="";
		boolean logToFile=true, logToConsole=true;
		
		ArrayList<CountryDto> countries = null;
		try
		{
			if(connection==null)
			{
				conn = DbConnection.getConnectionObject();
				conn_local = true;
			}
			else
			{
				conn= connection;
				conn_local = false;
			}
			
			countries=CommonBaseDao.getAllCountry(conn);
			
			
			
		}
		catch(Exception ex)
		{
			throw Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);
		}
		finally
		{
			try {
				if(conn_local){
					DbConnection.freeConnection(conn);
				}
			} catch (Exception e) {
				throw Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);
			}
		}
		return countries;
	}
	
	/**
	 * returns all states
	 * conn is optional , if passed it is used without closing 
	 * if not passsed , a new is created and closed in last
	 * @param connection
	 * @throws IOESException
	 */
		public static ArrayList<StateDto> getAllStates(Connection connection,CountryDto countryDto) throws IOESException {
			Connection conn=null;
			boolean conn_local=false;
			String methodName="getAllStates", className="UtilityModel", msg="";
			boolean logToFile=true, logToConsole=true;
			
			ArrayList<StateDto> states = null;
			try
			{
				if(connection==null)
				{
					conn = DbConnection.getConnectionObject();
					conn_local = true;
				}
				else
				{
					conn= connection;
					conn_local = false;
				}
				
				states=CommonBaseDao.getAllStates(conn,countryDto);
				
				
				
			}
			catch(Exception ex)
			{
				throw Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);
			}
			finally
			{
				try {
					if(conn_local){
						DbConnection.freeConnection(conn);
					}
				} catch (Exception e) {
					throw Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);
				}
			}
			return states;
		}
		

		/**
		 * returns all cities
		 * conn is optional , if passed it is used without closing 
		 * if not passsed , a new is created and closed in last
		 * @param connection
		 * @throws IOESException
		 */
			public static ArrayList<CityDto> getAllCities(Connection connection,StateDto state) throws IOESException {
				Connection conn=null;
				boolean conn_local=false;
				String methodName="getAllCities", className="UtilityModel", msg="";
				boolean logToFile=true, logToConsole=true;
				
				ArrayList<CityDto> states = null;
				try
				{
					if(connection==null)
					{
						conn = DbConnection.getConnectionObject();
						conn_local = true;
					}
					else
					{
						conn= connection;
						conn_local = false;
					}
					
					states=CommonBaseDao.getAllCities(conn,state);
		
				}
				catch(Exception ex)
				{
					throw Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);
				}
				finally
				{
					try {
						if(conn_local){
							DbConnection.freeConnection(conn);
						}
					} catch (Exception e) {
						throw Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);
					}
				}
				return states;
			}
			
			public static ArrayList<MastersAttributesDto> getAllServices(Connection connection) throws IOESException {
				Connection conn=null;
				boolean conn_local=false;
				String methodName="getAllServices", className="UtilityModel", msg="";
				boolean logToFile=true, logToConsole=true;
				
				ArrayList<MastersAttributesDto> services = null;
				try
				{
					if(connection==null)
					{
						conn = DbConnection.getConnectionObject();
						conn_local = true;
					}
					else
					{
						conn= connection;
						conn_local = false;
					}
					
					services=CommonBaseDao.getAllServices(conn);
					
					
					
				}
				catch(Exception ex)
				{
					throw Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);
				}
				finally
				{
					try {
						if(conn_local){
							DbConnection.freeConnection(conn);
						}
					} catch (Exception e) {
						throw Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);
					}
				}
				return services;
			}
			
			
}
