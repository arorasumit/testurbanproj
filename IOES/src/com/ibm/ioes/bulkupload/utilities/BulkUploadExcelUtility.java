//=================================================================================================================
//Tag Name		Resource Name		Date		CSR No			Description
//[001]			SUMIT ARORA	   28-JULY-2011		00-05422		checks the number of sheet and sheetname of file uploaded against that of template
//[002]			SUMIT ARORA	   28-JULY-2011		00-05422		check if file uploaded has any blank sheet	
//[003]			SUMIT ARORA	   28-JULY-2011		00-05422		checks the number and names of columns of file uploaded against the template
//[004]			SUMIT ARORA	   28-JULY-2011		00-05422		checks if file uploaded has validation comments	
//=================================================================================================================
package com.ibm.ioes.bulkupload.utilities;

import java.io.FileInputStream;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import com.ibm.ioes.exception.IOESException;
/**
 * @version		1.0
 * @author 		Sumit Arora
 *
 */
public class BulkUploadExcelUtility 
{
	/**
	 * Logger for the class.
	**/
	private static final Logger logger;
	
	static {

		logger = Logger.getLogger(BulkUploadExcelUtility.class);
	}
	
	/**
	 * @method	checkSheet
	 * @purpose	checks the number of sheet and sheetname of file uploaded against that of template
	 * @param 	fileName
	 * @param 	templateName
	 * @return
	 * @throws 	IOESException
	 */
	//START[001]
	@SuppressWarnings("finally")
	public int checkSheet(String fileName, String templateName) throws IOESException
	{
		logger.info(" Checking number and names of sheet for file against template.");
		
		int sheetsInFile, sheetsInTemplate, retCode=0;
		HSSFWorkbook fileWorkBook, templateWorkbook;
		
		try
		{
			fileWorkBook = new HSSFWorkbook(new FileInputStream(fileName));
			templateWorkbook = new HSSFWorkbook(new FileInputStream(templateName));
	
			sheetsInFile = fileWorkBook.getNumberOfSheets();
			sheetsInTemplate = templateWorkbook.getNumberOfSheets();
			if(fileWorkBook.getAllEmbeddedObjects().size()>0)
			{
				System.out.println("File contains an object");
				retCode = 8;
			}
			if(sheetsInFile!=sheetsInTemplate)
			{
				retCode = 1;
			}
			else
			{
				for(int sheetCount = 0; sheetCount < sheetsInFile ; sheetCount++ )
				{
					String fileSheetName = fileWorkBook.getSheetName(sheetCount);
					String templateSheetName = templateWorkbook.getSheetName(sheetCount);
					if(!fileSheetName.equals(templateSheetName))
					{
						retCode = 2;
						break ;
					}
				}
			}
			logger.info(" returning value " + retCode);
		}
		catch(Exception ex)
		{
			if(retCode==0)
			{
				retCode = 7;
				logger.info(" returning value " + retCode);
			}
			else
			{	
				logger.error(ex.getMessage() + " Exception occured in checking name and number of sheet.");
				throw new IOESException("Exception : "+ ex.getMessage(), ex);
			}	
		}
		finally
		{
			return retCode;
		}
	}
	//END[001]
	/**
	 * @method	checkBlankSheet
	 * @purpose	check if file uploaded has any blank sheet
	 * @param 	fileName
	 * @return
	 * @throws 	IOESException
	 */
	//START[002]
	public int checkBlankSheet(String fileName) throws IOESException
	{
		logger.info(" Checking for blank sheet in uploaded file.");
		
		int sheetsInFile, retCode = 0, rowCount, columnCount;
		HSSFWorkbook fileWorkBook;
		HSSFSheet fileSheet;
		HSSFRow fileRow;
		HSSFCell fileCell;
		try
		{
			fileWorkBook = new HSSFWorkbook(new FileInputStream(fileName));
			sheetsInFile = fileWorkBook.getNumberOfSheets();
			
			for(int sheetCount = 0 ; sheetCount < sheetsInFile && retCode == 0; sheetCount ++)
			{
				
				if(!(fileWorkBook.getSheetName(sheetCount).equalsIgnoreCase("Additional New-Charge")|| fileWorkBook.getSheetName(sheetCount).equalsIgnoreCase("Add New LineItem")|| fileWorkBook.getSheetName(sheetCount).equalsIgnoreCase("Add SubNode")|| fileWorkBook.getSheetName(sheetCount).equalsIgnoreCase("Add SubNodeCharge") || fileWorkBook.getSheetName(sheetCount).equalsIgnoreCase("GAM")))
				{
				retCode = 3;
				fileSheet = fileWorkBook.getSheetAt(sheetCount);
				
				rowCount = fileSheet.getLastRowNum();
				for(int row=2 ; row <= rowCount && retCode != 0 ; row++)
				{
					fileRow = fileSheet.getRow(row);
					if(fileRow != null || fileWorkBook.getSheetName(sheetCount).equalsIgnoreCase("Additional New-Charge") || fileWorkBook.getSheetName(sheetCount).equalsIgnoreCase("Add New LineItem")||fileWorkBook.getSheetName(sheetCount).equalsIgnoreCase("Add SubNode")||fileWorkBook.getSheetName(sheetCount).equalsIgnoreCase("Add SubNodeCharge") || fileWorkBook.getSheetName(sheetCount).equalsIgnoreCase("GAM"))
					{
						columnCount = fileRow.getLastCellNum();
						for(int col = 1 ; col < columnCount && retCode != 0; col++)
						{
							fileCell = fileRow.getCell(col);
							if(fileCell != null || fileWorkBook.getSheetName(sheetCount).equalsIgnoreCase("Additional New-Charge") || fileWorkBook.getSheetName(sheetCount).equalsIgnoreCase("Add New LineItem")||fileWorkBook.getSheetName(sheetCount).equalsIgnoreCase("Add SubNode")||fileWorkBook.getSheetName(sheetCount).equalsIgnoreCase("Add SubNodeCharge") || fileWorkBook.getSheetName(sheetCount).equalsIgnoreCase("GAM"))
							{
								if(fileCell.toString().trim()!=""||fileCell.toString().trim()!=null||fileWorkBook.getSheetName(sheetCount).equalsIgnoreCase("Additional New-Charge") || fileWorkBook.getSheetName(sheetCount).equalsIgnoreCase("Add New LineItem")||fileWorkBook.getSheetName(sheetCount).equalsIgnoreCase("Add SubNode")||fileWorkBook.getSheetName(sheetCount).equalsIgnoreCase("Add SubNodeCharge") || fileWorkBook.getSheetName(sheetCount).equalsIgnoreCase("GAM"))
								{
									retCode = 0;
								}
							}
						}
					}	
				}
				}
				if(retCode == 3)
				{
					break;
				}
				
			}
			
		}	
		catch(Exception ex)
		{
			logger.error(ex.getMessage() + " Exception occured in checking blank sheet.");
			throw new IOESException("Exception : "+ ex.getMessage(), ex);
		}
		
		logger.info("Reurning value " + retCode);
		return retCode ;
	}
	//END[002]
	/**
	 * @method	checkColumns
	 * @purpose	checks the number and names of columns of file uploaded against the template
	 * @param 	fileName
	 * @param 	templateName
	 * @return
	 * @throws	IOESException
	 */
	//START[003]
	public int checkColumns(String fileName, String templateName) throws IOESException
	{
		logger.info("Checking column name and number of uploaded sheet against template.");
		
		int numberOfSheet , sheetCol, templateCol, retCode = 0;
		HSSFWorkbook fileWorkBook, templateWorkbook;
		HSSFSheet fileSheet, templateSheet;
		HSSFRow fileRow, templateRow;
		HSSFCell cellInSheet, cellInTemplate;
		
		try
		{
			fileWorkBook = new HSSFWorkbook(new FileInputStream(fileName));
			templateWorkbook = new HSSFWorkbook(new FileInputStream(templateName));
			
			numberOfSheet = fileWorkBook.getNumberOfSheets();
			
			for(int sheetCount = 0 ; sheetCount < numberOfSheet && retCode == 0 ; sheetCount++)
			{
				fileSheet = fileWorkBook.getSheetAt(sheetCount);
				templateSheet = templateWorkbook.getSheetAt(sheetCount);
				
				fileRow = fileSheet.getRow(1);
				templateRow = templateSheet.getRow(1);
				
				if(fileRow != null && templateRow != null)
				{
					sheetCol = fileRow.getLastCellNum();
					templateCol = templateRow.getLastCellNum();
					if(templateCol != sheetCol)
					{
						retCode = 4;
						break;
					}
					else
					{
						for(int col = 1 ; col < sheetCol && retCode == 0; col++)
						{
							cellInSheet = fileRow.getCell(col);
							cellInTemplate = templateRow.getCell(col);
							if(cellInSheet != null && cellInTemplate != null)
							{
								if(!(cellInSheet.toString().trim().equals(cellInTemplate.toString().trim())))
								{
									retCode = 5;
									break;
								}
							}
							else if(!(cellInSheet == null && cellInTemplate == null))
							{
								retCode = 5;
								break;
							}
							
						}
					}
				}
				else if(fileRow == null)
				{
					retCode = 5;
					break;
				}
			
			}
			
			
		}
		catch(Exception ex)
		{
			logger.error(ex.getMessage() + " Exception occured in checking columns.");
			throw new IOESException("Exception : "+ ex.getMessage(), ex);
		}
		
		logger.info(" Returning value " + retCode);
		return retCode;
	}
	//END[003]
	
	/**
	 * @method	checkValidationComments
	 * @purpose	checks if file uploaded has validation comments
	 * @param 	fileName
	 * @param 	templateName
	 * @return
	 * @throws 	IOESException
	 */
	//START[004]
	public int checkValidationComments(String fileName, String templateName) throws IOESException
	{
		logger.info("Checking validation comments in uploaded file.");
		
		int numberOfSheet , sheetCol , rowsInTemplate , retCode = 0;
		HSSFWorkbook fileWorkBook, templateWorkbook;
		HSSFSheet fileSheet, templateSheet;
		HSSFRow fileRow, templateRow;
		HSSFCell cellInSheet, cellInTemplate;
		
		try
		{
			fileWorkBook = new HSSFWorkbook(new FileInputStream(fileName));
			templateWorkbook = new HSSFWorkbook(new FileInputStream(templateName));
			
			numberOfSheet = fileWorkBook.getNumberOfSheets();
			
			for(int sheetCount = 0 ; sheetCount < numberOfSheet && retCode == 0 ; sheetCount++)
			{
				fileSheet = fileWorkBook.getSheetAt(sheetCount);
				templateSheet = templateWorkbook.getSheetAt(sheetCount);
				rowsInTemplate = templateSheet.getLastRowNum();
				
				retCode = 6;
				for(int row = 2 ; row <= rowsInTemplate ; row ++)
				{
					fileRow = fileSheet.getRow(row);
					templateRow = templateSheet.getRow(row);
					
					if(fileRow != null && templateRow != null)
					{
						sheetCol = fileRow.getLastCellNum();
												
						for(int col = 1 ; col < sheetCol ; col++)
						{
							cellInSheet = fileRow.getCell(col);
							cellInTemplate = templateRow.getCell(col);
							if(cellInSheet != null && cellInTemplate != null)
							{
								if(!(cellInSheet.toString().trim().equals(cellInTemplate.toString().trim())))
								{
									retCode = 0;
									break;
								}
							}
							else if(!(cellInSheet == null && cellInTemplate == null))
							{
								retCode = 0;
								break;
							}
						}
					}
					else if(fileRow==null)
					{
						retCode = 0;
					}
				}
			}	
		}
		catch(Exception ex)
		{
			logger.error(ex.getMessage() + " Exception occured in checking validation comments");
			throw new IOESException("Exception : "+ ex.getMessage(), ex);
		}
		
		logger.info("Returning value " + retCode);
		return retCode;
	}
	//END[004]
}
