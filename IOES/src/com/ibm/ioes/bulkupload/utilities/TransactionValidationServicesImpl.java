//=================================================================================================================
//Tag Name		Resource Name		Date		CSR No			Description
//[001]			ANIL KUMAR	   28-JULY-2011		00-05422		to get file status details
//[002]			ANIL KUMAR	   28-JULY-2011		00-05422		to get transaction file list after uploaded
//[003]			SUMIT ARORA	   28-JULY-2011		00-05422		to validate file 
//=================================================================================================================
package com.ibm.ioes.bulkupload.utilities;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ibm.ioes.bulkupload.dao.TransactionValidationDaoImpl;
import com.ibm.ioes.bulkupload.dto.TransactionValidationDto;
import com.ibm.ioes.exception.IOESException;

/**
 * @version 	1.0
 * @author		Anveeksha Varma & Neeraj Aggarwal
 */

public class TransactionValidationServicesImpl  
{
	/**
	 * Logger for the class.
	**/
	private static final Logger logger;
	
	static {

		logger = Logger.getLogger(TransactionValidationServicesImpl.class);
	}
	
	//START[003]
	public int getValidation(String fileId) throws IOESException
	{
		//logger.info(" Entered into getValidation method of " + this.getClass().getSimpleName());

		int isFileValid = 0;
		TransactionValidationDaoImpl objDao = new TransactionValidationDaoImpl();
		
		try
		{
			isFileValid = objDao.fileValidation(fileId);
		}
		catch(Exception ex)
		{
			logger.error(ex.getMessage() + " Exception occured in getValidation method of " + this.getClass().getSimpleName());
			throw new IOESException("Exception : "+ ex.getMessage(), ex);
		}
		return isFileValid;
	}
	//END[003]
	//START[002]
	public ArrayList getTransFileList(TransactionValidationDto objDto) throws IOESException
	{
		//logger.info(" Entered into getTransFileList method of " + this.getClass().getSimpleName());

		ArrayList transactionFileList = new ArrayList();
		TransactionValidationDaoImpl objDao = new TransactionValidationDaoImpl();
		
		try
		{
			transactionFileList = objDao.getTransFileList(objDto);
		}
		catch(Exception ex)
		{
			logger.error(ex.getMessage() + " Exception occured in getTransFileList method of " + this.getClass().getSimpleName());
			throw new IOESException("Exception : "+ ex.getMessage(), ex);
		}
		
		return transactionFileList;
	}
	//END[002]
	//START[001]
	public ArrayList<TransactionValidationDto> getFileStatusDetails()throws IOESException
	{
		ArrayList<TransactionValidationDto> fileStatusList = new ArrayList<TransactionValidationDto>();
		TransactionValidationDaoImpl objDao = new TransactionValidationDaoImpl();
		try
		{
			fileStatusList=objDao.getFileStatusDetails();
		}
		catch(Exception ex)
		{
			logger.error(ex.getMessage() + " Exception occured in getFileStatusDetails method of " + this.getClass().getSimpleName());
			throw new IOESException("Exception : "+ ex.getMessage(), ex);
		}
		return fileStatusList;
	}
	//END[001]
}
