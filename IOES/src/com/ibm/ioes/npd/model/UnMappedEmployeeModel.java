package com.ibm.ioes.npd.model;

import java.util.ArrayList;

import com.ibm.ioes.npd.hibernate.beans.NPDResourceListDto;
import com.ibm.ioes.npd.hibernate.beans.UnMappedEmployeeDto;
import com.ibm.ioes.npd.hibernate.dao.NPDResourceListDao;
import com.ibm.ioes.npd.hibernate.dao.UnMappedEmployeeDao;


public class UnMappedEmployeeModel extends CommonBaseModel
{
	public ArrayList<UnMappedEmployeeDto> viewEmpResourceReport(UnMappedEmployeeDto unMappedEmployeeDto) throws Exception  
	{
		ArrayList<UnMappedEmployeeDto> listEmpList = new ArrayList<UnMappedEmployeeDto>();
		UnMappedEmployeeDao objDao = new UnMappedEmployeeDao();
		try
		{
			listEmpList = objDao.getEmpReport(unMappedEmployeeDto);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listEmpList;
	}
	public ArrayList<UnMappedEmployeeDto> viewEmpResourceExport(UnMappedEmployeeDto unMappedEmployeeDto) throws Exception  
	{
		ArrayList<UnMappedEmployeeDto> listEmpList = new ArrayList<UnMappedEmployeeDto>();
		UnMappedEmployeeDao objDao = new UnMappedEmployeeDao();
		try
		{
			listEmpList = objDao.getEmpReportExport(unMappedEmployeeDto);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listEmpList;
	}
}
