package com.ibm.ioes.model;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import com.ibm.ioes.beans.BCPAddressBean;
import com.ibm.ioes.beans.NewOrderBean;
import com.ibm.ioes.dao.BCPAddressDao;
import com.ibm.ioes.dao.CommonBaseDao;
import com.ibm.ioes.dao.LoginDao;
import com.ibm.ioes.dao.NewOrderDao;
import com.ibm.ioes.exception.IOESException;
import com.ibm.ioes.forms.BCPAddressDto;
import com.ibm.ioes.forms.CityDto;
import com.ibm.ioes.forms.CountryDto;
import com.ibm.ioes.forms.LoginDto;
import com.ibm.ioes.forms.NewOrderDto;
import com.ibm.ioes.forms.StateDto;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Utility;

public class LoginModel 
{
	public LoginDto getModuleInterfaceList(String roleId) throws Exception
	{
		LoginDto loginDto= new LoginDto();
		LoginDao objDao = new LoginDao();
		try
		{
			loginDto = objDao.getModuleInterfaceList(roleId);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
			throw new Exception("SQL Exception : "+ ex.getMessage(), ex);
		}
		return loginDto;
	}
	
	public LoginDto getModuleInterfaceListForReportLink(String userId) throws Exception
	{
		//LoginDto loginDto= new LoginDto();
		LoginDao objDao = new LoginDao();
		try
		{
			return objDao.getModuleInterfaceListForReportLink(userId);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
			throw new Exception("SQL Exception : "+ ex.getMessage(), ex);
		}
		//return loginDto;
	}
	
}
