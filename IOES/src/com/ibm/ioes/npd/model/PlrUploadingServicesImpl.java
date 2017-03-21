package com.ibm.ioes.npd.model;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ibm.ioes.npd.exception.NpdException;
import com.ibm.ioes.npd.hibernate.beans.MyToDoListDto;
import com.ibm.ioes.npd.hibernate.beans.PlrUploadingDto;
import com.ibm.ioes.npd.hibernate.dao.*;
import com.ibm.ioes.npd.utilities.CommonUtilities;
import com.ibm.ioes.npd.utilities.TransactionDto;
import com.ibm.ioes.npd.utilities.TransactionServiceImpl;
public class PlrUploadingServicesImpl 
{
	private static final Logger logger;
	static {
		logger = Logger.getLogger(PlrUploadingServicesImpl.class);
	}
	public ArrayList<PlrUploadingDto> viewProjectList(PlrUploadingDto searchDto) throws NpdException 
	{
		ArrayList<PlrUploadingDto> list = new ArrayList<PlrUploadingDto>();
		
		PlrUploadingDaoImpl objDao = new PlrUploadingDaoImpl();
		try
		{
			list = objDao.viewProjectList(searchDto);  // For MyToDoList Summary			
		}
		catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage()
			+ " Exception occured in myToDoList method of "
			+ this.getClass().getSimpleName());
			throw new NpdException();
		}
		return list;
	}
	
	public boolean mapStakeHolder(PlrUploadingDto searchDto) throws NpdException 
	{
		ArrayList<PlrUploadingDto> list = new ArrayList<PlrUploadingDto>();
		
		PlrUploadingDaoImpl objDao = new PlrUploadingDaoImpl();
		boolean isSaved = false;
		try
		{
			isSaved = objDao.mapStakeHolder(searchDto);  // For MyToDoList Summary			
		}
		catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage()
			+ " Exception occured in myToDoList method of "
			+ this.getClass().getSimpleName());
			throw new NpdException();
		}
		return isSaved;
	}
	public ArrayList<PlrUploadingDto> alreadyMapStakeHolder(PlrUploadingDto searchDto) throws NpdException 
	{
		ArrayList<PlrUploadingDto> list = new ArrayList<PlrUploadingDto>();
		
		PlrUploadingDaoImpl objDao = new PlrUploadingDaoImpl();
		String employeeId[] = null;
		PlrUploadingDto objDto = new PlrUploadingDto();
		ArrayList<PlrUploadingDto> mapplst = new ArrayList<PlrUploadingDto>();
		try
		{
			mapplst = objDao.alreadyMappedStakeHolder(searchDto);  // For MyToDoList Summary			
		}
		catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage()
			+ " Exception occured in myToDoList method of "
			+ this.getClass().getSimpleName());
			throw new NpdException();
		}
		return mapplst;
	}
	
	
	
	public boolean UploadPlr(PlrUploadingDto searchDto) throws NpdException 
	{
		ArrayList<PlrUploadingDto> list = new ArrayList<PlrUploadingDto>();
		boolean isSaved = false;
		PlrUploadingDaoImpl objDao = new PlrUploadingDaoImpl();
		
		try
		{
			isSaved = objDao.updatePLR(searchDto);  // For MyToDoList Summary			
		}
		catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage()
			+ " Exception occured in myToDoList method of "
			+ this.getClass().getSimpleName());
			throw new NpdException();
		}
		return isSaved;
	}
	public ArrayList<PlrUploadingDto> viewDocHistory(PlrUploadingDto tranDto) throws NpdException 
	{
		ArrayList<PlrUploadingDto> list = new ArrayList<PlrUploadingDto>();
		boolean isSaved = false;
		PlrUploadingDaoImpl objDao = new PlrUploadingDaoImpl();
		
		try
		{
			list = objDao.viewDocHistory(tranDto);			
		}
		catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage()
			+ " Exception occured in myToDoList method of "
			+ this.getClass().getSimpleName());
			throw new NpdException();
		}
		return list;
	}
	public ArrayList getPendingMonthList(PlrUploadingDto searchdto) throws NpdException 
	{
		ArrayList list = new ArrayList();
		
		PlrUploadingDaoImpl objDao = new PlrUploadingDaoImpl();
		
		try
		{
			list = objDao.getPendingMonthList(searchdto);			
		}
		catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage()
			+ " Exception occured in myToDoList method of "
			+ this.getClass().getSimpleName());
			throw new NpdException();
		}
		return list;
	}

	public int getPendingPLRUploadingCount(String UserID)throws NpdException {
		int taskPending_plrUploading = 0;
		PlrUploadingDaoImpl pleDaoImpl = new PlrUploadingDaoImpl();

		try {
			taskPending_plrUploading = pleDaoImpl.getPendingPLRUploadingCount(UserID);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage()
					+ " Exception occured in myToDoList method of "
					+ this.getClass().getSimpleName());
			throw new NpdException();
		}
		return taskPending_plrUploading;
	}

	
}
