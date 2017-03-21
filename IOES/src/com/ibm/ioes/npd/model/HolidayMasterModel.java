package com.ibm.ioes.npd.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ibm.ioes.npd.exception.NpdException;
import com.ibm.ioes.npd.hibernate.beans.HolidayMasterDto;
import com.ibm.ioes.npd.hibernate.dao.HolidayMasterDao;
import com.ibm.ioes.npd.hibernate.dao.ProductCategoryDao;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.NpdConnection;

public class HolidayMasterModel extends CommonBaseModel
{
	public ArrayList<HolidayMasterDto> viewHolidayList(HolidayMasterDto holidayDto) throws Exception  
	{
		ArrayList<HolidayMasterDto> listHolidayMaster = new ArrayList<HolidayMasterDto>();
		HolidayMasterDao objDao = new HolidayMasterDao();
		try
		{
			listHolidayMaster = objDao.getHolidayList(holidayDto);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listHolidayMaster;
	}
	
	public int addHoliday(HolidayMasterDto holidayDto) throws Exception  
	{
		int addStatus=0;
		Connection connection = null;
		try 
		{
			connection = NpdConnection.getConnectionObject();
			connection.setAutoCommit(false);
			HolidayMasterDao holidayMasterDao = new HolidayMasterDao();
			addStatus = holidayMasterDao.addHoliday(holidayDto,connection );
		} 
		catch (Exception ex) 
		{
			try 
			{
				connection.rollback();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
				AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
				throw new NpdException("Exception : " + e.getMessage(), e);
			}
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(ex));
			throw new NpdException("Exception : " + ex.getMessage(), ex);

		} 
		finally 
		{
			try 
			{
				NpdConnection.freeConnection(connection);
			} 
			catch (Exception ex) 
			{
				ex.printStackTrace();
				AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(ex));
				throw new NpdException("Exception : " + ex.getMessage(), ex);
			}
		}
		return addStatus;
	}
}
