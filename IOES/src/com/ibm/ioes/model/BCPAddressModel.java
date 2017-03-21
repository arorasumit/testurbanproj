package com.ibm.ioes.model;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import com.ibm.ioes.beans.BCPAddressBean;
import com.ibm.ioes.beans.NewOrderBean;
import com.ibm.ioes.dao.BCPAddressDao;
import com.ibm.ioes.dao.CommonBaseDao;
import com.ibm.ioes.dao.NewOrderDao;
import com.ibm.ioes.exception.IOESException;
import com.ibm.ioes.forms.BCPAddressDto;
import com.ibm.ioes.forms.CityDto;
import com.ibm.ioes.forms.CountryDto;
import com.ibm.ioes.forms.NewOrderDto;
import com.ibm.ioes.forms.StateDto;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Utility;

public class BCPAddressModel 
{
	public ArrayList<BCPAddressDto> displayAccountDetails(BCPAddressDto objDto) throws Exception
	{
		ArrayList<BCPAddressDto> listAccountDetails= new ArrayList<BCPAddressDto>();
		BCPAddressDao objDao = new BCPAddressDao();
		try
		{
			listAccountDetails = objDao.getAccountDetails(objDto);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
			throw new Exception("SQL Exception : "+ ex.getMessage(), ex);
		}
		return listAccountDetails;
	}
	
	
		public ArrayList<BCPAddressDto> displayBCPDetails(BCPAddressDto objDto) throws Exception
		{
			ArrayList<BCPAddressDto> listBCPDetails= new ArrayList<BCPAddressDto>();
			BCPAddressDao objDao = new BCPAddressDao();
			try
			{
				listBCPDetails = objDao.getBCPDetails(objDto);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();	
				throw new Exception("SQL Exception : "+ ex.getMessage(), ex);
			}
			return listBCPDetails;
		}
		
			public ArrayList<BCPAddressDto> viewBCPList(BCPAddressDto objDto)  throws Exception
			{
				ArrayList<BCPAddressDto> objList = new ArrayList<BCPAddressDto>();
				BCPAddressDao objDao = new BCPAddressDao();
				try
				{
					objList = objDao.viewBCPList(objDto);
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
					throw new Exception("SQL Exception : "+ ex.getMessage(), ex);
				}
				return objList;
			}
			public BCPAddressDto loadBcpEditView(BCPAddressBean objForm ) throws IOESException
			{
				BCPAddressDao objDao = new BCPAddressDao();
				BCPAddressDto objDto=  new BCPAddressDto();
				String methodName="viewUserProfile", className="BCPAddressModel", msg="";
				boolean logToFile=true, logToConsole=true;
				Connection conn=null;
				try
				{
					conn=DbConnection.getConnectionObject();
					int bcpId=objForm.getHiddenBCPId();
					objDto = objDao.viewUserProfile(conn,bcpId);
					objForm.setAccountID(objDto.getAccountID());
					objForm.setAccountName(objDto.getAccountName());
					objForm.setBcpId(objDto.getBCPId());
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
					
					//load acc list
					/*ArrayList<BCPAddressDto> listAccount = new ArrayList<BCPAddressDto>();
					listAccount = objDao.getAccountDetails(objDto);
					objForm.setCustomerList(listAccount);*/
					
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
			public String addNewBCP(BCPAddressDto objDto) throws Exception
			{
				String success="";
				BCPAddressDao objDao = new BCPAddressDao();
				try
				{
					success = objDao.addNewBCP(objDto);
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
					throw new Exception("SQL Exception : "+ ex.getMessage(), ex);
				}
				return success;
			}
			
			public String updateBCP(BCPAddressDto objDto) throws Exception
			{
				String success="";
				BCPAddressDao objDao = new BCPAddressDao();
				try
				{
					success = objDao.updateBCP(objDto);
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
					throw new Exception("SQL Exception : "+ ex.getMessage(), ex);
				}
				return success;
			}
			public void loadNewBCPView(BCPAddressBean objForm) throws IOESException {
				
				Connection conn=null;
				String methodName="loadNewBCPView", className="BCPAddressModel", msg="";
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
