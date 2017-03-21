//=================================================================================================================
//Tag Name		Resource Name		Date		CSR No			Description
//[001]			SUMIT ARORA	   28-JULY-2011		00-05422		get filled file for reference master
//[002]			SUMIT ARORA	   28-JULY-2011		00-05422		to save uploaded file data to temprary table
//[003]			SUMIT ARORA	   28-JULY-2011		00-05422		get filled file for reference master data
//[004]			ANIL KUMAR	   28-JULY-2011		00-05422		get list of subchange type	
//[005]			SUMIT ARORA	   28-JULY-2011		00-05422		get transaction template list
//=================================================================================================================
package com.ibm.ioes.bulkupload.dao;

import java.io.FileOutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;



import com.ibm.ioes.bulkupload.dto.TransactionUploadDto;
import com.ibm.ioes.exception.IOESException;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Utility;

/**
 * @version 	1.0
 * @author		Sumit Arora and Anil Kumar
 */

public class TransactionUploadDaoImpl 
{
	/**
	 * Logger for the class.
	**/
	private static final Logger logger;
	
	static {

		logger = Logger.getLogger(TransactionUploadDaoImpl.class);
	}
//	start 
	private String sqlspGetSubChangeType = "{call IOE.SP_GET_SUBCHANGETYPE(?)}";
	private String sqlGetTransactionTemplate = "{call BULKUPLOAD.SP_GET_CHANGETEMPLATE(?)}";
	public static String sqlGetMasterReference = "{call BULKUPLOAD.SP_GET_TEMPLATELIST(?)}";
	public static String sqlInsertprocessedFileInfo = "{call BULKUPLOAD.SP_BULKINSERT_PROCESSED_FILEINFO(?,?,?,?,?,?,?,?,?)}";
	public static String sqlGetTemporarySPName = "{call BULKUPLOAD.SP_BULK_GET_SPNAME(?)}";
	public static String spGetDataFromMasterView = "{call BULKUPLOAD.SP_GET_MASTERDATA(?)}";
	public static String spCall = "{call "; 
	//end
	/**
	 * @method 	fetchSubchangeType
	 * @purpose	get list of subchange type	
	 * @return	ArrayList of transactions
	 * @throws 	IOESException
	 */
	//START[004]
	public ArrayList<TransactionUploadDto> fetchSubchangeType(String rolename) 
	{
		Connection connection =null;
		CallableStatement csSubchangeType =null;
		ResultSet rsSubchangeType = null;
		ArrayList<TransactionUploadDto> listSubchhangetype = new ArrayList<TransactionUploadDto>();
		TransactionUploadDto objDto = null;	
		try
		{
			connection=DbConnection.getConnectionObject();
			csSubchangeType= connection.prepareCall(sqlspGetSubChangeType);
			csSubchangeType.setString(1, rolename);
									
			rsSubchangeType = csSubchangeType.executeQuery();
			while(rsSubchangeType.next())
			{
				objDto =  new TransactionUploadDto();
				objDto.setStrTransactionID(Long.toString(rsSubchangeType.getLong("SUBCHANGE_TYPEID")));
				objDto.setStrTransactionName(rsSubchangeType.getString("NAME_SUBTYPE"));
				listSubchhangetype.add(objDto);			
			}
			return listSubchhangetype;
		}
		catch(Exception ex )
		{
			ex.printStackTrace();	
		}
		finally
		{
			try 
			{
				DbConnection.closeResultset(rsSubchangeType);
				DbConnection.closeCallableStatement(csSubchangeType);
				DbConnection.freeConnection(connection);
			} 
			catch (SQLException e) 
			{
				// TODO Auto-generated catch block
				Utility.LOG(true, false, e, "::BULKUPLOAD_ERROR:: exception occured in method fetchSubchangeType");
				e.printStackTrace();
			}
			catch(Exception ex)
			{
				logger.error(ex.getMessage() + " Exception occured while closing database objects in getTransFileList method of " + this.getClass().getSimpleName());
			}
		}
		return listSubchhangetype;
	}
	//END[004]
	/**
	 * @method 	getTransactionTemplate
	 * @purpose	get transaction template
	 * @param 	intTemplateID
	 * @return	String of file path
	 * @throws 	IOESException
	 */
	//START[005]
	public String getTransactionTemplate(int intTemplateID) throws IOESException
	{
		//logger.info(" Entered into getTransactionTemplate method of " + this.getClass().getSimpleName());
		
		int templateID = -1;
		String strTransactionTemplateFileName = null, filename = null, filepath=null;
		Connection con = null;
		CallableStatement csGetTransactionTemplate = null;
		ResultSet rsGetTransactionTemplate = null;
		
		try
		{
			DbConnection connectionClassObj = new DbConnection();
			con = connectionClassObj.getConnectionObject();
			csGetTransactionTemplate = con.prepareCall(sqlGetTransactionTemplate);
			csGetTransactionTemplate.setInt(1,intTemplateID);
			rsGetTransactionTemplate = csGetTransactionTemplate.executeQuery();
			while(rsGetTransactionTemplate.next())
			{
				filename = rsGetTransactionTemplate.getString("FileName");
				filepath = rsGetTransactionTemplate.getString("FilePath");
				templateID = rsGetTransactionTemplate.getInt("TemplateId");
			}
			strTransactionTemplateFileName = filepath+"/"+filename+"&&&"+templateID;
		}
		catch(SQLException sqlEx)
		{
			logger.error(sqlEx.getMessage() + " SQLException occured in getTransactionTemplate method of " + this.getClass().getSimpleName());
			throw new IOESException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
		}
		catch(Exception ex)
		{
			logger.error(ex.getMessage() + " Exception occured in getTransactionTemplate method of " + this.getClass().getSimpleName());
			throw new IOESException("Exception : "+ ex.getMessage(), ex);
		}
		finally
		{
			try
			{
				DbConnection.closeResultset(rsGetTransactionTemplate);
				DbConnection.closeCallableStatement(csGetTransactionTemplate);
				DbConnection.freeConnection(con);
			}
			catch(SQLException sqlEx)
			{
				logger.error(sqlEx.getMessage() + " SQLException occured while closing database objects in getTransactionTemplate method of " + this.getClass().getSimpleName());
				throw new IOESException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
			}
			catch(Exception ex)
			{
				logger.error(ex.getMessage() + " Exception occured while closing database objects in getTransactionTemplate method of " + this.getClass().getSimpleName());
				throw new IOESException("Exception : "+ ex.getMessage(), ex);
			}
		}
		return strTransactionTemplateFileName;
	}
	//END[005]
	/**
	 * @method 	getReferenceMasterList
	 * @purpose	get filled file for reference master
	 * @param 	transactionID
	 * @return	ArrayList of master data
	 * @throws 	IOESException
	 */
	//START[001]
	public ArrayList getReferenceMasterList(int transactionID) throws IOESException 
	{
		//logger.info(" Entered into getReferenceMasterList method of " + this.getClass().getSimpleName());
		
		ArrayList<TransactionUploadDto> masterReferenceList =  new ArrayList<TransactionUploadDto>();
		Connection con=null;
		CallableStatement csGetMasterReference=null;
		ResultSet rsGetMasterReference=null;
		TransactionUploadDto dtoObj;
		
		try
		{
			DbConnection connectionClassObj = new DbConnection();	
			con = connectionClassObj.getConnectionObject();
			csGetMasterReference = con.prepareCall(sqlGetMasterReference);
			csGetMasterReference.setInt(1,transactionID);
			rsGetMasterReference = csGetMasterReference.executeQuery();
			while(rsGetMasterReference.next())
			{
				dtoObj = new TransactionUploadDto ();
				dtoObj.setReferenceMasterName(rsGetMasterReference.getString("MASTERNAME"));
				dtoObj.setReferenceMasterID(rsGetMasterReference.getInt("MASTERID"));
				masterReferenceList.add(dtoObj);
			}
		}
		catch(SQLException sqlEx)
		{
			logger.error(sqlEx.getMessage() + " SQLException occured in getReferenceMasterList method of " + this.getClass().getSimpleName());
			throw new IOESException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
		}
		catch(Exception ex)
		{
			logger.error(ex.getMessage() + " Exception occured in getReferenceMasterList method of " + this.getClass().getSimpleName());
			throw new IOESException("Exception : "+ ex.getMessage(), ex);
		}
		finally
		{
			try
			{
				DbConnection.closeResultset(rsGetMasterReference);
				DbConnection.closeCallableStatement(csGetMasterReference);
				DbConnection.freeConnection(con);
			}
			catch(SQLException sqlEx)
			{
				logger.error(sqlEx.getMessage() + " SQLException occured while closing database objects in getReferenceMasterList method of " + this.getClass().getSimpleName());
				throw new IOESException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
			}
			catch(Exception ex)
			{
				logger.error(ex.getMessage() + " Exception occured while closing database objects in getReferenceMasterList method of " + this.getClass().getSimpleName());
				throw new IOESException("Exception : "+ ex.getMessage(), ex);
			}
		}
		return masterReferenceList;
	}
	//END[001]
	/**
	 * @method 	saveUploadedFileToTemporaryTable
	 * @purpose	to save uploaded file data to temprary table
	 * @param 	excelDataList
	 * @param 	dtoObj
	 * @param 	userID
	 * @return	Long
	 * @throws 	IOESException
	 */
	//START[002]
	public Long saveUploadedFileToTemporaryTable(ArrayList excelDataList, TransactionUploadDto dtoObj, int userID, int respId) throws IOESException 
	{
		//logger.info(" Entered into saveUploadedFileToTemporaryTable method of " + this.getClass().getSimpleName());
		
		Long fileID=0l; 
		int pos, colCount = 0, sheetNo=0;
		String spName = null, fileName = null, filePath = null;
		/**
		 * DEFECT ID 	MASDB00113802
		 * For removing review defect conn connection Object added  
		 */
		Connection con = null, conn = null;
		CallableStatement csGetTemporarySPName = null, csInsertprocessedFileInfo = null, csTempTableInsert = null;
		ResultSet rsGetTemporarySPName = null, rsInsertprocessedFileInfo = null;
		try
		{
			filePath = dtoObj.getStrUploadFileName();
			pos = filePath.lastIndexOf("/");
			fileName = filePath.substring(pos+1,filePath.length());
			filePath = filePath.substring(0,pos);
			
			DbConnection connectionClassObj = new DbConnection();	
			con = connectionClassObj.getConnectionObject();
			conn = connectionClassObj.getConnectionObject();
			
			con.setAutoCommit(false);
			conn.setAutoCommit(false);
			
			csInsertprocessedFileInfo = con.prepareCall(sqlInsertprocessedFileInfo);
			csGetTemporarySPName = con.prepareCall(sqlGetTemporarySPName);
				
			/**	[001] Starts ##	Block to get insert processed file data and get fileId	 */
			csInsertprocessedFileInfo.setInt(1,dtoObj.getTemplateID());
			csInsertprocessedFileInfo.setInt(2,userID);
			csInsertprocessedFileInfo.setString(3,fileName);
			csInsertprocessedFileInfo.setString(4,filePath);
			csInsertprocessedFileInfo.setInt(5,0);
			csInsertprocessedFileInfo.setInt(6,0);
			csInsertprocessedFileInfo.setString(7,"");
			csInsertprocessedFileInfo.setLong(8,0);
			csInsertprocessedFileInfo.setInt(9, respId);
			
			csInsertprocessedFileInfo.execute();
			if(csInsertprocessedFileInfo.getLong(6)==0)
				fileID = csInsertprocessedFileInfo.getLong(8);
			
			csInsertprocessedFileInfo.close();
			
			/**	[001] Ends
			 *  
			 *  [002] Starts ## Block to get temporary table name, stored procedure name and columns in sheet for insertion  */ 
			csGetTemporarySPName.setInt(1,dtoObj.getTemplateID());
			rsGetTemporarySPName = csGetTemporarySPName.executeQuery();
			
			while(rsGetTemporarySPName.next())
			{
				spName = rsGetTemporarySPName.getString("TEMPSPNAME");
				colCount = rsGetTemporarySPName.getInt("ColumnCount");
				sheetNo= rsGetTemporarySPName.getInt("SheetNo");
				
				StringBuffer strBufSpCall = new StringBuffer(spCall);
				strBufSpCall.append(spName);
				strBufSpCall.append("(?");
				for(int ctr=0;ctr<colCount;ctr++)
				{
					strBufSpCall.append(",?");
				}
				strBufSpCall.append(")}");;
				csTempTableInsert = conn.prepareCall(strBufSpCall.toString());
				csTempTableInsert.setLong(1,fileID);
						
				Object[][] excelData = (Object[][])excelDataList.get(sheetNo-1);
				
				for(int r=0;r<excelData.length;r++)
				{
					int check = 0;
					for(int c=0;c<colCount;c++)
					{

						if(!(excelData[r][c].toString().trim().equals("")))
						{
							csTempTableInsert.setString(c+2,(excelData[r][c]).toString().trim());
							check = 1; 
						}
						else
						{
							csTempTableInsert.setString(c+2,"");
						}
					}
					if(check == 1)
					{	
						csTempTableInsert.execute();
					}	
				}
			
			}
			con.commit();
			conn.commit();
		}
		catch(SQLException sqlEx)
		{
			logger.error(sqlEx.getMessage() + " SQLException occured in saveUploadedFileToTemporaryTable method of " + this.getClass().getSimpleName());
			throw new IOESException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
		}
		catch(Exception ex)
		{
			logger.error(ex.getMessage() + " Exception occured in saveUploadedFileToTemporaryTable method of " + this.getClass().getSimpleName());
			throw new IOESException("Exception : "+ ex.getMessage(), ex);
		}
		finally
		{
			try
			{
				//csGetTemporarySPName.close();
				//csInsertprocessedFileInfo.close();
				//csTempTableInsert.close();
				DbConnection.closeResultset(rsGetTemporarySPName);
				DbConnection.closeCallableStatement(csGetTemporarySPName);
				DbConnection.freeConnection(con);
				DbConnection.freeConnection(conn);
			}
			catch(SQLException sqlEx)
			{
				logger.error(sqlEx.getMessage() + " SQLException occured while closing database objects in saveUploadedFileToTemporaryTable method of " + this.getClass().getSimpleName());
				throw new IOESException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
			}
			catch(Exception ex)
			{
				logger.error(ex.getMessage() + " Exception occured while closing database objects in saveUploadedFileToTemporaryTable method of " + this.getClass().getSimpleName());
				throw new IOESException("Exception : "+ ex.getMessage(), ex);
			}
		}
		return fileID;
	}
	//END[002]
	//START[003]
	public ArrayList getReferenceMasterData(int masterID) throws IOESException
	{
		//logger.info(" Entered into getReferenceMasterData method of " + this.getClass().getSimpleName());
		
		ArrayList<Comparable> resultList = new ArrayList<Comparable>();
		Connection con = null;
		CallableStatement csGetDataFromMasterView = null;
		ResultSet rsGetDataFromMasterView = null;
		
		try
		{
			DbConnection connectionClassObj = new DbConnection();	
			con = connectionClassObj.getConnectionObject();
			csGetDataFromMasterView = con.prepareCall(spGetDataFromMasterView);
			csGetDataFromMasterView.setInt(1,masterID);
			rsGetDataFromMasterView = csGetDataFromMasterView.executeQuery();
			int col = rsGetDataFromMasterView.getMetaData().getColumnCount();
			resultList.add(col);
			System.out.println(col);
			for(int c=1;c<=col;c++)
			{
				resultList.add(rsGetDataFromMasterView.getMetaData().getColumnName(c));
			}
			while(rsGetDataFromMasterView.next())
			{
				for(int c=1;c<=col;c++)
				{
					resultList.add(rsGetDataFromMasterView.getString(c));
				}
			}
		
		}
		catch(SQLException sqlEx)
		{
			logger.error(sqlEx.getMessage() + " SQLException occured in getReferenceMasterData method of " + this.getClass().getSimpleName());
			throw new IOESException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
		}
		catch(Exception ex)
		{
			logger.error(ex.getMessage() + " Exception occured in getReferenceMasterData method of " + this.getClass().getSimpleName());
			throw new IOESException("Exception : "+ ex.getMessage(), ex);
		}
		finally
		{
			try
			{
				DbConnection.closeResultset(rsGetDataFromMasterView);
				DbConnection.closeCallableStatement(csGetDataFromMasterView);
				DbConnection.freeConnection(con);
			}
			catch(SQLException sqlEx)
			{
				logger.error(sqlEx.getMessage() + " SQLException occured while closing database objects in getReferenceMasterData method of " + this.getClass().getSimpleName());
				throw new IOESException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
			}
			catch(Exception ex)
			{
				logger.error(ex.getMessage() + " Exception occured while closing database objects in getReferenceMasterData method of " + this.getClass().getSimpleName());
				throw new IOESException("Exception : "+ ex.getMessage(), ex);
			}
		}
		return resultList;
	}
	//END[003]

}
