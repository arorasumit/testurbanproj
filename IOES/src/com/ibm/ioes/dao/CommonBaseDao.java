package com.ibm.ioes.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ibm.ioes.exception.IOESException;
import com.ibm.ioes.forms.CityDto;
import com.ibm.ioes.forms.CountryDto;
import com.ibm.ioes.forms.MastersAttributesDto;
import com.ibm.ioes.forms.StateDto;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Utility;


/**
 * This class contains the common methods for the insertion,deletion & findbyid
 * for all classes
 * 
 * @author Varun
 * @version 1.0
 */

public class CommonBaseDao {

	protected static final Logger log = AppConstants.IOES_LOGGER;
	final static String className="CommonBaseDao";
	private static final String spGetAllCountries = "{ call IOE.SP_GET_COUNTRIES()}";
	private static final String spGetStatesOdCountry = "{ call IOE.SP_GET_STATE_OF_COUNTRY(?)}";
	private static final String spGetCitiesOfState = "{ call IOE.SP_GET_CITY_OF_STATE(?)}";
	private static final String spGetAllServices = "{ call IOE.GETSERVICETYPE(?)}";
		
	public int getAttachemntSize() throws Exception
	{
		//Added by Nagarjuna
		String methodName="getAttachemntSize",  msg="";
		boolean logToFile=true, logToConsole=true;
		//End
		int attachmentSize=0;
		Connection connObj=null;
		
		String str="SELECT ATTACHMENTSIZE FROM IOE.TM_ATTACHMENTSIZE";
		Statement stmt = null;
		ResultSet rs=null;
		try 
		{
			connObj = DbConnection.getConnectionObject();
			stmt = connObj.createStatement();
			rs=stmt.executeQuery(str);
			rs.next();
			attachmentSize=rs.getInt(1);			
		} 
		catch (Exception ex) 
		{
			throw Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole); 
			//ex.printStackTrace();
		}
		finally
		{
			try {
				DbConnection .closeResultset(rs);
				DbConnection.closeStatement(stmt);
				DbConnection.freeConnection(connObj);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return attachmentSize;
	}
	
	public int getPageSize() throws Exception
	{
		//Added by Nagarjuna
		String methodName="getPageSize",  msg="";
		boolean logToFile=true, logToConsole=true;
		//End
		Connection connObj=null;
		int pageRecords=0;
		String str="select size from IOE.VW_PAGING";
		Statement stmt = null;
		ResultSet rs=null;
		try 
		{
			connObj = DbConnection.getConnectionObject();
			stmt = connObj.createStatement();
			rs=stmt.executeQuery(str);
			rs.next();
			pageRecords=rs.getInt(1);			
		} 
		catch (Exception ex) 
		{
			throw Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole); 
			//ex.printStackTrace();
		}
		finally
		{
			try {
				DbConnection.closeResultset(rs);
				DbConnection.closeStatement(stmt);
				DbConnection.freeConnection(connObj);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return pageRecords;
	}
	
	public String getSpecialChar() throws Exception
	{
		//Added by Nagarjuna
		String methodName="getSpecialChar",  msg="";
		boolean logToFile=true, logToConsole=true;
		//End
		Connection connObj=null;
		String iChar="";
		String str="SELECT CHARACTERS FROM IOE.VW_SPECIALCHARACTERS";
		Statement stmt = null;
		ResultSet rs=null;
		try 
		{
			connObj = DbConnection.getConnectionObject();
			stmt = connObj.createStatement();
			rs=stmt.executeQuery(str);
			rs.next();
			iChar=rs.getString(1);			
		} 
		catch (Exception ex) 
		{
			throw Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole); 
			//ex.printStackTrace();
		}
		finally
		{
			try {
				DbConnection .closeResultset(rs);
				DbConnection.closeStatement(stmt);
				DbConnection.freeConnection(connObj);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return iChar;
	}

	public static ArrayList<CountryDto> getAllCountry(Connection conn) throws IOESException {
		
		
		String methodName="getAllCountry",  msg="";
		boolean logToFile=true, logToConsole=true;
		CallableStatement cstmt=null;
		ResultSet rs=null;
		ArrayList<CountryDto> countries = new ArrayList<CountryDto>();
		try
		{
			String sql=spGetAllCountries;
			cstmt= conn.prepareCall(sql);
			rs=cstmt.executeQuery();
			CountryDto dto=null;
			while(rs.next())
			{
				dto=new CountryDto();
				dto.setCountryCode(rs.getLong("COUNTRY_CODE"));
				dto.setCountryName(rs.getString("COUNTRY_NAME"));
				countries.add(dto);
			}
			
		}
		catch(Exception ex)
		{
			throw Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);
		}
		finally
		{
			try
			{
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(cstmt);
			}
			catch(Exception es)
			{
				es.printStackTrace();
			}
		}
		
		return countries;
	}

	public static ArrayList<StateDto> getAllStates(Connection conn, CountryDto countryDto) throws IOESException {
		String methodName="getAllStates",  msg="";
		boolean logToFile=true, logToConsole=true;
		CallableStatement cstmt=null;
		ResultSet rs=null;
		ArrayList<StateDto> states = new ArrayList<StateDto>();
		try
		{
			String sql=spGetStatesOdCountry;
			cstmt= conn.prepareCall(sql);
			cstmt.setLong(1, (countryDto.getCountryCode()));
			rs=cstmt.executeQuery();
			StateDto dto=null;
			while(rs.next())
			{
				dto=new StateDto();
				dto.setStateId(rs.getLong("STATE_ID"));
				dto.setStateName(rs.getString("STATE_NAME"));
				states.add(dto);
			}
			
		}
		catch(Exception ex)
		{
			throw Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);
		}
		finally
		{
			try
			{
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(cstmt);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		
		return states;
	}
	
	public static ArrayList<CityDto> getAllCities(Connection conn, StateDto stateDto) throws IOESException {
		String methodName="getAllCities",  msg="";
		boolean logToFile=true, logToConsole=true;
		CallableStatement cstmt=null;
		ResultSet rs=null;
		ArrayList<CityDto> cities = new ArrayList<CityDto>();
		try
		{
			String sql=spGetCitiesOfState;
			cstmt= conn.prepareCall(sql);
			cstmt.setLong(1, (stateDto.getStateId()));
			rs=cstmt.executeQuery();
			CityDto dto=null;
			while(rs.next())
			{
				dto=new CityDto();
				dto.setCityId(rs.getLong("CITY_ID"));
				dto.setCityName(rs.getString("CITY_NAME"));
				cities.add(dto);
			}
			
		}
		catch(Exception ex)
		{
			throw Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);
		}
		finally
		{
			try
			{
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(cstmt);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			
		}
		
		return cities;
	}
    public static ArrayList<MastersAttributesDto> getAllServices(Connection conn) throws IOESException {
		
		
		String methodName="getAllServices",  msg="";
		boolean logToFile=true, logToConsole=true;
		CallableStatement cstmt=null;
		ResultSet rs=null;
		ArrayList<MastersAttributesDto> services = new ArrayList<MastersAttributesDto>();
		try
		{
			
			cstmt= conn.prepareCall(spGetAllServices);
			cstmt.setString(1,"");
			rs=cstmt.executeQuery();
			MastersAttributesDto dto=null;
			while(rs.next())
			{
				dto=new MastersAttributesDto();
				dto.setServiceTypeId(rs.getString("SERVICETYPEID"));
				dto.setServiceType(rs.getString("SERVICETYPENAME"));
				services.add(dto);
			}
			
		}
		catch(Exception ex)
		{
			throw Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);
		}
		finally
		{
			try
			{
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(cstmt);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		
		return services;
	}
}
