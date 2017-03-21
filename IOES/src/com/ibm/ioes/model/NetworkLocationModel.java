package com.ibm.ioes.model;

import java.sql.Connection;
import java.util.ArrayList;

import com.ibm.ioes.beans.NetworkLocationBean;
import com.ibm.ioes.dao.CommonBaseDao;
import com.ibm.ioes.dao.NetworkLocationDao;
import com.ibm.ioes.exception.IOESException;
import com.ibm.ioes.forms.CityDto;
import com.ibm.ioes.forms.CountryDto;
import com.ibm.ioes.forms.NetworkLocationDto;
import com.ibm.ioes.forms.StateDto;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Utility;

public class NetworkLocationModel 
{
	
			public ArrayList<NetworkLocationDto> viewNetworkLocationList(NetworkLocationDto objDto)  throws Exception
			{
				ArrayList<NetworkLocationDto> objList = new ArrayList<NetworkLocationDto>();
				NetworkLocationDao objDao = new NetworkLocationDao();
				try
				{
					objList = objDao.viewNetworkLocationList(objDto);
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
					throw new Exception("SQL Exception : "+ ex.getMessage(), ex);
				}
				return objList;
			}
			public NetworkLocationDto loadNetworkLocationEditView(NetworkLocationBean objForm ) throws IOESException
			{
				NetworkLocationDao objDao = new NetworkLocationDao();
				NetworkLocationDto objDto=  new NetworkLocationDto();
				String methodName="viewUserProfile", className="NetworkLocationModel", msg="";
				boolean logToFile=true, logToConsole=true;
				Connection conn=null;
				try
				{
					conn=DbConnection.getConnectionObject();
					int networkLocationId=objForm.getHiddenNetworkLocationId();
					objDto = objDao.viewUserProfile(conn,networkLocationId);
					
					objForm.setNetworkLocationId(objDto.getNetworkLocationId());
					objForm.setFirstname(objDto.getFirstname());
					objForm.setLastName(objDto.getLastName());
					objForm.setTitle(objDto.getTitle());
		
					objForm.setAddress1(objDto.getAddress1());
					objForm.setAddress2(objDto.getAddress2());
					objForm.setAddress3(objDto.getAddress3());
					objForm.setAddress4(objDto.getAddress4());
					objForm.setFax(objDto.getFax());
					
					objForm.setEmail_Id(objDto.getEmail_Id());
					objForm.setTelephonePhno(objDto.getTelephonePhno());
					objForm.setPostalCode((objDto.getPostalCode()));
					objForm.setCity((objDto.getCity_Id()));
					objForm.setState((objDto.getState_Id()));
					objForm.setCountry((objDto.getCountryCode()));
					
					
					//load country list
					objForm.setCountries(UtilityModel.getAllCountry(conn));
//					load state list
					CountryDto countryDto=new CountryDto();
					countryDto.setCountryCode(Long.parseLong(objDto.getCountryCode()));
					objForm.setStates(CommonBaseDao.getAllStates(conn,countryDto));
//					load city list
					StateDto stateDto=new StateDto();
					stateDto.setStateId(Long.parseLong(objDto.getState_Id()));
					objForm.setCities(CommonBaseDao.getAllCities(conn,stateDto));
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
				return objDto;
			}
			public String addNewNetworkLocation(NetworkLocationDto objDto) throws Exception
			{
				String success="";
				NetworkLocationDao objDao = new NetworkLocationDao();
				try
				{
					success = objDao.addNewNetworkLocation(objDto);
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
					throw new Exception("SQL Exception : "+ ex.getMessage(), ex);
				}
				return success;
			}
			
			public String updateNetworkLocation(NetworkLocationDto objDto) throws Exception
			{
				String success="";
				NetworkLocationDao objDao = new NetworkLocationDao();
				try
				{
					success = objDao.updateNetworkLocation(objDto);
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
					throw new Exception("SQL Exception : "+ ex.getMessage(), ex);
				}
				return success;
			}
			public void loadNewNetworkLocationView(NetworkLocationBean objForm) throws IOESException {
				
				Connection conn=null;
				String methodName="loadNewNetworkLocationView", className="NetworkLocationModel", msg="";
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
			public static ArrayList<StateDto> validateCountrySeleted(CountryDto countryDto) {
				// TODO Auto-generated method stub
				return null;
			}
			public static ArrayList<CityDto> validateStateSeleted(StateDto stateDto) {
				// TODO Auto-generated method stub
				return null;
			}
}
