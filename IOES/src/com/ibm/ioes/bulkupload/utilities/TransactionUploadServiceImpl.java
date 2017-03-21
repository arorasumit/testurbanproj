//=================================================================================================================
//Tag Name		Resource Name		Date		CSR No			Description
//[001]			ANIL KUMAR	   28-JULY-2011		00-05422		to get transaction type list and open Bulkupload Page
//[002]			ANIL KUMAR	   28-JULY-2011		00-05422		to get template details of the transaction selected	
//[003]			SUMIT ARORA	   28-JULY-2011		00-05422		validate the uploaded excel against the template for the selected transaction	
//[004]			SUMIT ARORA	   28-JULY-2011		00-05422		to get master refrence list 	
//[005]			SUMIT ARORA	   28-JULY-2011		00-05422		to save uploaded information 
//[006]			SUMIT ARORA	   28-JULY-2011		00-05422		to get refernce master data
//=================================================================================================================
package com.ibm.ioes.bulkupload.utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.ibm.ioes.bulkupload.dao.ErrorFileDaoImpl;
import com.ibm.ioes.bulkupload.dao.TransactionUploadDaoImpl;
import com.ibm.ioes.bulkupload.dto.TransactionUploadDto;
import com.ibm.ioes.exception.IOESException;

/**
 * @version 	1.0
 * @author		Sumit Arora
 */

public class TransactionUploadServiceImpl 
{
	/**
	 * Logger for the class.
	**/
	private static final Logger logger;
	
	static {

		logger = Logger.getLogger(TransactionUploadServiceImpl.class);
	}
	
	
	/**
	 * @method	validateUploadedExcel
	 * @purpose	validate the uploaded excel against the template for the selected transaction	
	 * @param 	fileName
	 * @param 	templateName
	 * @return 	string with upload status and message
	 * @throws 	IOESException
	 */
	//START[003]
	public ArrayList validateUploadedExcel(String fileName, String templateName) throws IOESException
	{
		//logger.info(" Entered into validateUploadedExcel method of " + this.getClass().getSimpleName());
		
		int status, ctr = 0;
		int  sheetsInFile,sheetRow;
		HSSFSheet fileSheet = null;
		HSSFWorkbook fileWorkBook = null;
		ArrayList<TransactionUploadDto> msgList = new ArrayList<TransactionUploadDto>();
		TransactionUploadDto dtoObj;
		BulkUploadExcelUtility excelUtilityObj = new BulkUploadExcelUtility();
		
		try
		{
			ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");
			
			status = excelUtilityObj.checkSheet(fileName, templateName);
			if(status == 1 )
			{
				ctr++;
				dtoObj = new TransactionUploadDto();
				dtoObj.setErrMsgOrder(ctr);
				dtoObj.setErrMsgString(bundle.getString("sheet.mismatch"));
				logger.info("Number of sheets in uploaded file do not match the template.");
				msgList.add(dtoObj);
			}
			else if(status == 2)
			{
				ctr++;
				dtoObj = new TransactionUploadDto();
				dtoObj.setErrMsgOrder(ctr);
				dtoObj.setErrMsgString(bundle.getString("sheetname.mismatch"));
				logger.info("Sheet name of uploaded file do not match the template.");
				msgList.add(dtoObj);
			}
			else if(status == 7)
			{
				ctr++;
				dtoObj = new TransactionUploadDto();
				dtoObj.setErrMsgOrder(ctr);
				dtoObj.setErrMsgString(bundle.getString("invalid.excel"));
				logger.info("File uploaded is not a valid excel file.");
				msgList.add(dtoObj);
			}
			else if(status == 8)
			{
				ctr++;
				dtoObj = new TransactionUploadDto();
				dtoObj.setErrMsgOrder(ctr);
				dtoObj.setErrMsgString(bundle.getString("invalid.filehasobject"));
				logger.info("File Contains an file object");
				msgList.add(dtoObj);
			}
			else
			{	
				status = excelUtilityObj.checkBlankSheet(fileName);
				if(status == 3)
				{
					ctr++;
					dtoObj = new TransactionUploadDto();
					dtoObj.setErrMsgOrder(ctr);
					dtoObj.setErrMsgString(bundle.getString("sheet.blank"));
					logger.info("Uploaded file has atleast one blank sheet.");
					msgList.add(dtoObj);
				}
				
				status = excelUtilityObj.checkColumns(fileName, templateName);
				if(status == 4)
				{
					ctr++;
					dtoObj = new TransactionUploadDto();
					dtoObj.setErrMsgOrder(ctr);
					dtoObj.setErrMsgString(bundle.getString("columnnumber.mismatch"));
					logger.info("Number of columns in atleast one sheet of uploaded file do not match the template.");
					msgList.add(dtoObj);
				}
				else if(status == 5)
				{
					ctr++;
					dtoObj = new TransactionUploadDto();
					dtoObj.setErrMsgOrder(ctr);
					dtoObj.setErrMsgString(bundle.getString("columnname.mismatch"));
					logger.info("Names of columns in atleast one sheet of uploaded file do not match the template. ");
					msgList.add(dtoObj);
				}
				
			}
			
		fileWorkBook = new HSSFWorkbook(new FileInputStream(fileName));
		sheetsInFile = fileWorkBook.getNumberOfSheets();
		for(int i=0;i<sheetsInFile;i++)
		{
			fileSheet = fileWorkBook.getSheetAt(i);
			sheetRow = fileSheet.getLastRowNum();
			if(sheetRow > 1501)
			{
				ctr++;
				dtoObj = new TransactionUploadDto();
				dtoObj.setErrMsgOrder(ctr);
				dtoObj.setErrMsgString("1500 rows are allowed to upload");
				logger.info("1500 rows are allowed to upload");
				msgList.add(dtoObj);
			}
		}
		
	}
		catch(Exception ex)
		{
			logger.error(ex.getMessage() + " Exception occured in validateUploadedExcel method of " + this.getClass().getSimpleName());
			throw new IOESException("Exception : "+ ex.getMessage(), ex);
		}
		
 		return msgList;
	}	
//END[003]
	//START[001]
	public ArrayList<TransactionUploadDto> fetchSubchangeType(String rolename) throws Exception
	{
		ArrayList<TransactionUploadDto> listSubchangeType= new ArrayList<TransactionUploadDto>();	
		TransactionUploadDaoImpl objDao=new TransactionUploadDaoImpl();
		try
		{
			listSubchangeType = objDao.fetchSubchangeType(rolename);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listSubchangeType;
	}
	//END[001]
	//START[002]
	public String getTransactionTemplate(int intTemplateID) throws Exception
	{
		String transactionTemplate=null;
		TransactionUploadDaoImpl objDao=new TransactionUploadDaoImpl();
		try
		{
			transactionTemplate=objDao.getTransactionTemplate(intTemplateID);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return transactionTemplate;
	}
//END[002]
	//START[004]
	public ArrayList getTransactionMasterReference(String intTemplateId) throws IOESException
	{
		//logger.info(" Entered into getTransactionMasterReference method of " + this.getClass().getSimpleName());

		ArrayList referenceMaster = new ArrayList();
		TransactionUploadDaoImpl objDao = new TransactionUploadDaoImpl();
		try
		{
			referenceMaster = objDao.getReferenceMasterList((int)Integer.parseInt(intTemplateId));
		}
		catch(Exception ex)
		{
			logger.error(ex.getMessage() + " Exception occured in getTransactionMasterReference method of " + this.getClass().getSimpleName());
			throw new IOESException("Exception :"+ ex.getMessage(), ex);
		}
			
		return referenceMaster;
	}
	//END[004]
	//START[005]
	public int saveUploadedFileInfo(String fileName, String templateId, int userId, int respId) throws IOESException 
	{
		//logger.info(" Entered into saveUploadedFileInfo method of " + this.getClass().getSimpleName());

		int sheetsInFile, sheetCol, sheetRow, retCode=0, checkCode=0;
		HSSFWorkbook fileWorkBook = null;
		HSSFSheet fileSheet = null;
		HSSFRow rowInSheet = null;
		HSSFCell cellInSheet = null;
		TransactionUploadDaoImpl objDao = new TransactionUploadDaoImpl();
		TransactionUploadDto dtoObj = new TransactionUploadDto();
		ArrayList<Object[][]> excelDataList=new ArrayList<Object[][]>();
		try
		{
			fileWorkBook = new HSSFWorkbook(new FileInputStream(fileName));
			sheetsInFile = fileWorkBook.getNumberOfSheets();
			
			dtoObj.setStrUploadFileName(fileName);
			dtoObj.setTemplateID((int)Integer.parseInt(templateId));
			
			for(int i=0;i<sheetsInFile;i++)
			{
				fileSheet = fileWorkBook.getSheetAt(i);
				sheetRow = fileSheet.getLastRowNum();
				sheetCol = fileSheet.getRow(1).getLastCellNum();
				Object excelData[][] = new Object[sheetRow-1][sheetCol-1];
				
				for(int r=2;r<=sheetRow;r++)
				{
					rowInSheet = fileSheet.getRow(r);
					for(int c=1;c<sheetCol;c++)
					{
						if(rowInSheet!=null)
						{	
							cellInSheet=rowInSheet.getCell(c);
							if(cellInSheet!=null)
							{
								if(cellInSheet.toString().trim().length()<=255)
								{
									excelData[r-2][c-1]=cellInSheet.toString().trim();
								}
								else
								{
									excelData[r-2][c-1]=cellInSheet.toString().trim().substring(0,255);
								}
								if(!cellInSheet.toString().trim().equals(""))
								{	
									checkCode = 1;
								}	
							}
							else
							{
								excelData[r-2][c-1]="";
							}
						}
						else
						{
							excelData[r-2][c-1]="";
						}
					}
				}
				excelDataList.add(excelData);
			}
			if(checkCode==1)
			{
				retCode = Integer.valueOf(objDao.saveUploadedFileToTemporaryTable(excelDataList, dtoObj, userId,respId).toString());
			}
		}
		catch(Exception ex)
		{
			logger.error(ex.getMessage() + " Exception occured in saveUploadedFileInfo method of " + this.getClass().getSimpleName());
			throw new IOESException("Exception :"+ ex.getMessage(), ex);
		}
		if(checkCode!=1)
		{
			retCode = -1;
		}
		return retCode;
	}
	
	//END[005]
	//START[006]
	public String getReferenceMasterData(int[] masterReferenceId, String[] masterReferenceName, String transactionType) throws IOESException
	{
		//logger.info(" Entered into getReferenceMasterData method of " + this.getClass().getSimpleName());

		int sCtr = 1;
		String filePath = null, fileName = null;
		ArrayList retObj = null;
		HSSFWorkbook workBook = null;
		HSSFRow workRow = null;
		HSSFCell workCell = null;
		
		TransactionUploadDaoImpl objDao = new TransactionUploadDaoImpl();
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
			String date = sdf.format(new java.util.Date());
			date = date.substring(0,19);
			
			ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");
			filePath = bundle.getString("excel.masterReference");
			fileName = filePath+transactionType+date+".xls";
			
			workBook = new HSSFWorkbook();
			HSSFSheet workSheet = null; 
			for(int i=0;i<masterReferenceId.length;i++)
			{	
				int rowCount = 1, colCount ;
				retObj = objDao.getReferenceMasterData(masterReferenceId[i]);
				
				colCount =(int)Integer.parseInt((retObj.get(0)).toString());
				for(int count=1;count<retObj.size();count=count+colCount)
				{
					
					if(rowCount == 1)
					{	
						if(retObj.size()>50000)
						{
								String sheetName = masterReferenceName[i] + "_" + ((Integer)sCtr).toString();
								workSheet = workBook.createSheet(sheetName);
						}
						else
						{
							workSheet = workBook.createSheet(masterReferenceName[i]);
						}
					}	
					workRow = workSheet.createRow(rowCount);
					for(int col=1;col<=colCount;col++)
					{
						workCell = workRow.createCell(col);
						String str = "";
						if(rowCount==1)
						{
							if(retObj.get((rowCount+col-1))!=null)
								str = retObj.get((rowCount+col-1)).toString().toUpperCase().trim();
							else
								str = "";	
						}
						else
						{
							if(retObj.get((count+col-1))!=null)
								str = retObj.get((count+col-1)).toString().trim();
							else
								str= "";
						}
						workCell.setCellValue(str);
					}
					rowCount++;
					if(rowCount > 50000)
					{
						rowCount = 1 ;
						sCtr = sCtr + 1;
					}	
				}
			}
			FileOutputStream fileOut = new FileOutputStream(fileName);
			workBook.write(fileOut);
			fileOut.close();
		}
		catch(Exception ex)
		{
			logger.error(ex.getMessage() + " Exception occured in getReferenceMasterData method of " + this.getClass().getSimpleName());
			throw new IOESException("Exception :"+ ex.getMessage(), ex);
		}
			
		return fileName;
	}
	//END[006]
}

