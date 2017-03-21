//=================================================================================================================
//Tag Name		Resource Name		Date		CSR No			Description
//[001]			ANIL KUMAR	   28-JULY-2011		00-05422		to get file status details
//[002]			ANIL KUMAR	   28-JULY-2011		00-05422		to get transaction file list after uploaded
//[003]			SUMIT ARORA	   28-JULY-2011		00-05422		to validate file 
//[004]			Gunjan Singla BillingEfficiency CR 
//=================================================================================================================
package com.ibm.ioes.bulkupload.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ibm.ioes.bulkupload.dto.TransactionValidationDto;
import com.ibm.ioes.exception.IOESException;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.PagingSorting;
import com.ibm.ioes.utilities.Utility;


/**
 * @version 	1.0
 * @author	Sumit Arora & Anil Kumar
 */

public class TransactionValidationDaoImpl 
{
	/**
	 * Logger for the class.
	**/
	private static final Logger logger;
	
	static {

		logger = Logger.getLogger(TransactionValidationDaoImpl.class);
	}
	
	public static String sqlGetTransactionType = "{call spBulkGetTransactionType(?)}";
	//public static String sqlGetTransFileList = "{call IOE.SPBULK_GETFILELIST(?)}";
	public static String sqlValidateFile = "{call BULKUPLOAD.SPBULK_DATATYPEVALIDATE(?,?,?,?,?,?)}";
	public static String sqlGetTransFileList = "{call BULKUPLOAD.SPBULK_GETFILELIST(?,?,?,?,?,?,?,?,?,?)}";
	public static String sqlGetFileStatusList = "{call BULKUPLOAD.SP_GET_FILESTAUSLIST()}";

	//START[003]
	public int fileValidation(String fileID) throws IOESException
	{
		//logger.info(" Entered into fileValidation method of " + this.getClass().getSimpleName());
		
		int returnCode = 0;
		Connection con=null;
		CallableStatement csValidateFile=null;
		String errormsg=" ";
		try
		{
			DbConnection connectionClassObj = new DbConnection();	
			con = connectionClassObj.getConnectionObject();
			
			csValidateFile = con.prepareCall(sqlValidateFile);
			csValidateFile.setLong(1, Long.valueOf(fileID));
			csValidateFile.registerOutParameter(2,java.sql.Types.INTEGER);
			csValidateFile.setInt(3,0);
			//csValidateFile.setString(3, "");
			csValidateFile.setString(4, "");
			csValidateFile.setString(5, "");
			csValidateFile.setString(6, "");
			
			
			csValidateFile.execute();
			returnCode=csValidateFile.getInt(2);
			errormsg=csValidateFile.getString(5);
			
			if(returnCode==4){
				logger.error("Error Occurred while validating excel sheet:"+ errormsg);
			}
		}
		catch(SQLException sqlEx)
		{
			logger.error(sqlEx.getMessage() + " SQLException occured in fileValidation method of " + this.getClass().getSimpleName());
			throw new IOESException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
		}
		catch(Exception ex)
		{
			logger.error(ex.getMessage() + " Exception occured in fileValidation method of " + this.getClass().getSimpleName());
			throw new IOESException("Exception : "+ ex.getMessage(), ex);
		}
		finally
		{
			try
			{
				DbConnection.closeCallableStatement(csValidateFile);
				DbConnection.freeConnection(con);
			}
			catch(SQLException sqlEx)
			{
				logger.error(sqlEx.getMessage() + " SQLException occured while closing database objects in fileValidation method of " + this.getClass().getSimpleName());
				throw new IOESException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
			}
			catch(Exception ex)
			{
				logger.error(ex.getMessage() + " Exception occured while closing database objects in fileValidation method of " + this.getClass().getSimpleName());
				throw new IOESException("Exception : "+ ex.getMessage(), ex);
			}
		}
		return returnCode;
	}
	//END[003]
	//START[002]
	public ArrayList getTransFileList(TransactionValidationDto objDto) throws IOESException
	{
		//logger.info(" Entered into getTransFileList method of " + this.getClass().getSimpleName());
		
		ArrayList<TransactionValidationDto> transFileList = new ArrayList<TransactionValidationDto>();
		Connection con = null;
		CallableStatement csGetTransFileList = null;
		ResultSet rsGetTransFileList = null;
		TransactionValidationDto dtoTransFileList;
		
		int recordCount=0;
		try
		{
			DbConnection connectionClassObj = new DbConnection();	
			con = connectionClassObj.getConnectionObject();
			csGetTransFileList = con.prepareCall(sqlGetTransFileList);
			csGetTransFileList.setInt(1,objDto.getSelectedTransactionID());	
			if(objDto.getSearchFileStausId()==0||objDto.getSearchFileStausId()==-1)
			{
				csGetTransFileList.setInt(2,0);	
			}	
			else
			{
				csGetTransFileList.setInt(2,objDto.getSearchFileStausId());	
			}
			if(objDto.getSearchFileID()==0)
			{
				csGetTransFileList.setInt(3,0);	
			}
			else
			{
				csGetTransFileList.setInt(3,objDto.getSearchFileID());	
			}
			if("".equalsIgnoreCase(objDto.getSearchfromDate())||null==objDto.getSearchfromDate())
			{
				csGetTransFileList.setNull(4, java.sql.Types.VARCHAR);
			}
			else
			{
				csGetTransFileList.setString(4, objDto.getSearchfromDate());
			}
			///[004] start
			if("".equalsIgnoreCase(objDto.getSearchUserID())||null==objDto.getSearchUserID())
			{
				
				csGetTransFileList.setNull(5, java.sql.Types.VARCHAR);
			}
			else
			{
				csGetTransFileList.setString(5, objDto.getSearchUserID());
			}
			
			///[004] end
			if("".equalsIgnoreCase(objDto.getSearchToDate())||null==objDto.getSearchToDate())
			{
				csGetTransFileList.setNull(6, java.sql.Types.VARCHAR);
			}
			else
			{
				csGetTransFileList.setString(6, objDto.getSearchToDate());
			}
			PagingSorting pagingSorting = objDto.getPagingSorting();
			pagingSorting.sync();
			csGetTransFileList.setString(7,pagingSorting.getSortByColumn());
			csGetTransFileList.setString(8, pagingSorting.getSortByOrder());
			csGetTransFileList.setInt(9,pagingSorting.getStartRecordId());
			csGetTransFileList.setInt(10,pagingSorting.getEndRecordId());
			
			rsGetTransFileList = csGetTransFileList.executeQuery();			
			while(rsGetTransFileList.next())
			{
				dtoTransFileList = new TransactionValidationDto();
				dtoTransFileList.setFileId(rsGetTransFileList.getInt("FileID"));
				//dtoTransFileList.setStrSelectedTransactionName(rsGetTransFileList.getString("TransactionName"));
				dtoTransFileList.setStrFileName(rsGetTransFileList.getString("strFile"));
				dtoTransFileList.setStrTransDate(rsGetTransFileList.getString("TransactionDate"));
				dtoTransFileList.setStrFileStatus(rsGetTransFileList.getString("FILESTATUSNAME"));
				dtoTransFileList.setFileStatusId(rsGetTransFileList.getInt("FILESTATUSID"));				
				dtoTransFileList.setFilePath(rsGetTransFileList.getString("FILEPATH"));
				//[004] START
				dtoTransFileList.setSearchUserID(Utility.IfNullToBlank(rsGetTransFileList.getString("USERID"))+"-"+Utility.IfNullToBlank(rsGetTransFileList.getString("FIRSTNAME"))+" "+Utility.IfNullToBlank(rsGetTransFileList.getString("LASTNAME")));
				//[004] END
				recordCount=rsGetTransFileList.getInt("FULL_REC_COUNT");
				transFileList.add(dtoTransFileList);
			}
			pagingSorting.setRecordCount(recordCount);
		}
		catch(SQLException sqlEx)
		{
			logger.error(sqlEx.getMessage() + " SQLException occured in getTransFileList method of " + this.getClass().getSimpleName());
			throw new IOESException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
		}
		catch(Exception ex)
		{
			logger.error(ex.getMessage() + " Exception occured in getTransFileList method of " + this.getClass().getSimpleName());
			throw new IOESException("Exception : "+ ex.getMessage(), ex);
		}
		finally
		{
			try
			{
				DbConnection.closeResultset(rsGetTransFileList);
				DbConnection.closeCallableStatement(csGetTransFileList);
				DbConnection.freeConnection(con);
			}
			catch(SQLException sqlEx)
			{
				logger.error(sqlEx.getMessage() + " SQLException occured while closing database objects in getTransFileList method of " + this.getClass().getSimpleName());
				throw new IOESException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
			}
			catch(Exception ex)
			{
				logger.error(ex.getMessage() + " Exception occured while closing database objects in getTransFileList method of " + this.getClass().getSimpleName());
				throw new IOESException("Exception : "+ ex.getMessage(), ex);
			}
		}
		return transFileList;
	}
	//END[002]
	//START[001]
	public ArrayList<TransactionValidationDto> getFileStatusDetails()throws IOESException
	{
		ArrayList<TransactionValidationDto> fileStatusList = new ArrayList<TransactionValidationDto>();
		Connection con = null;
		CallableStatement csGetFileStausList = null;
		ResultSet rsGetFileStatusList = null;
		TransactionValidationDto objDto;
		try
		{
			DbConnection connectionClassObj = new DbConnection();	
			con = connectionClassObj.getConnectionObject();			
			csGetFileStausList = con.prepareCall(sqlGetFileStatusList);			
			rsGetFileStatusList = csGetFileStausList.executeQuery();
			while(rsGetFileStatusList.next())
			{
				objDto=new TransactionValidationDto();
				objDto.setSearchFileStausId(rsGetFileStatusList.getInt("FILESTATUSID"));
				objDto.setSearchStrFileStatus(rsGetFileStatusList.getString("FILESTATUSNAME"));
				fileStatusList.add(objDto);
			}
			
		}
		catch(SQLException sqlEx)
		{
			logger.error(sqlEx.getMessage() + " SQLException occured while closing database objects in getTransFileList method of " + this.getClass().getSimpleName());
			throw new IOESException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
		}
		catch(Exception ex)
		{
			logger.error(ex.getMessage() + " Exception occured in getFileStatusDetails method of " + this.getClass().getSimpleName());
			throw new IOESException("Exception : "+ ex.getMessage(), ex);
		}		
		finally
		{
			try
			{
				
				//csGetTransFileList.close();
				DbConnection.closeResultset(rsGetFileStatusList);
				DbConnection.closeCallableStatement(csGetFileStausList);
				DbConnection.freeConnection(con);
			}			
			catch(SQLException sqlEx)
			{
				logger.error(sqlEx.getMessage() + " SQLException occured while closing database objects in getTransFileList method of " + this.getClass().getSimpleName());
				throw new IOESException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
			}
			catch(Exception ex)
			{
				logger.error(ex.getMessage() + " Exception occured while closing database objects in getTransFileList method of " + this.getClass().getSimpleName());
				throw new IOESException("Exception : "+ ex.getMessage(), ex);
			}
		}
		return fileStatusList;
	}
	//END[001]
}
