//=================================================================================================================
//Tag Name		Resource Name		Date		CSR No			Description
//[001]			ANIL KUMAR	   28-JULY-2011		00-05422		to get transaction type
//[002]			ANIL KUMAR	   28-JULY-2011		00-05422		get list of files validated successfully, for the selected transaction
//[003]			SUMIT ARORA	   28-JULY-2011		00-05422		to process file 
//=================================================================================================================
package com.ibm.ioes.bulkupload.utilities;

import java.util.ArrayList;
import org.apache.log4j.Logger;

import com.ibm.ioes.bulkupload.dao.TransactionProcessDaoImpl;
import com.ibm.ioes.exception.IOESException;


/**
 * @version 	1.0
 * @author 		Sumit Arora & Anil Kumar
 */

public class TransactionProcessServicesImpl 
{
	/**
	 * Logger for the class.
	**/
	private static final Logger logger;
	
	static {

		logger = Logger.getLogger(TransactionProcessServicesImpl.class);
	}
	
	/**
	 * @method	getTransactionType
	 * @purpose	get list of transactions for user
	 * @param 	userID
	 * @return	ArrayList of transaction
	 * @throws 	IOESException
	 */
	//START[001]
	public ArrayList getTransactionType(int userID) throws IOESException
	{
		logger.info(" Entered into getTransactionType method of " + this.getClass().getSimpleName());
		
		ArrayList transactionTypeList = new ArrayList();
		TransactionProcessDaoImpl objDao = new TransactionProcessDaoImpl();
		try
		{
			transactionTypeList = objDao.getTransactionType(userID);
		}
		catch(Exception ex)
		{
			logger.error(ex.getMessage() + " Exception occured in getTransactionType method of " + this.getClass().getSimpleName());
			throw new IOESException("Exception :"+ ex.getMessage(), ex);
		}
		
		return transactionTypeList;
	}
	//END[001]
	/**
	 * @method	getTransFileList
	 * @purpose	get list of files validated successfully, for the selected transaction
	 * @param 	transactionId
	 * @param	userId
	 * @return	ArrayList of files
	 * @throws 	IOESException
	 */
	//START[002]
	public ArrayList getTransFileList(int transactionId, int userId, int fileStatusId) throws IOESException
	{
		logger.info(" Entered into getTransFileList method of " + this.getClass().getSimpleName());

		ArrayList transactionFileList = new ArrayList();
		TransactionProcessDaoImpl objDao = new TransactionProcessDaoImpl();
		try
		{
			transactionFileList = objDao.getTransFileList(transactionId, userId, fileStatusId);
		}
		catch(Exception ex)
		{
			logger.error(ex.getMessage() + " Exception occured in getTransFileList method of " + this.getClass().getSimpleName());
			throw new IOESException("Exception :"+ ex.getMessage(), ex);
		}

		return transactionFileList;
	}
	//END[002]
	/**
	 * @method	ProcessFiles
	 * @purpose	mark files ready for processing
	 * @param 	fileIds
	 * @throws 	IOESException
	 */
	//START[003]
	public String processFiles(String fileIds, int statusId,String empid) throws IOESException
	{
		logger.info(" Entered into processFiles method of " + this.getClass().getSimpleName());

		TransactionProcessDaoImpl objDao = new TransactionProcessDaoImpl();
		
		try
		{
			if(statusId==61)
			{
				return objDao.processFiles_forBTBulkUpload(Integer.parseInt(fileIds), statusId,empid);   // for billing trigger process by manisha
			}
			else
			{
				return objDao.processFiles(fileIds, statusId,empid);
			}
			
		}
		catch(Exception ex)
		{
			logger.error(ex.getMessage() + " Exception occured in processFiles method of " + this.getClass().getSimpleName());
			throw new IOESException("Exception :"+ ex.getMessage(), ex);
		}
	}
	//END[003]
}
