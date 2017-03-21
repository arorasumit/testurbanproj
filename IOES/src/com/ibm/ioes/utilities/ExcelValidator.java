package com.ibm.ioes.utilities;

import java.io.FileInputStream;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;

import com.ibm.ioes.dao.NewOrderDaoExt;
import com.ibm.ioes.exception.IOESException;
import com.ibm.ioes.model.NewOrderModel;

public class ExcelValidator {
	
	/**
	 * @method validateUploadedExcel
	 * @purpose validate uploaded excel agaists the template before saving to
	 *          the database
	 * @param FormFile,
	 * @param templatePath
	 * @param messages 
	 * @param uploadedFilePath
	 * @return status
	 * @throws throws
	 *             IOESException
	 */
	public int validateUploadedExcel(FormFile file, String templateFilePath, ActionMessages messages) throws IOESException{

		AppConstants.IOES_LOGGER.info("validateUploadedExcel() started");
		int status = 0;
		String fileName = null;
		
		try {

			fileName = file.getFileName();
			
			if (fileName != null) {
			
				status = checkSheet(file, templateFilePath);
				if (status != 1 && status != 2 && status != 7 && status != 8 &&  status != 10 )
					status = checkServiceSummary(file);
				if (status != 1 && status != 2 && status != 7 && status != 8 &&  status != 10 &&  status != 12 )
					status = checkColumns(file, templateFilePath);
				if (status != 1 && status != 2 && status != 7 && status != 8
						&& status != 4 && status != 5 &&  status != 10 &&  status != 12)
					status = checkBlankSheetAndDataLength(file,templateFilePath,messages);
			}
		} catch (Exception e) {
			throw new IOESException(e);
		}
		AppConstants.IOES_LOGGER.info("validateUploadedExcel() completed");
		return status;
	}
	
	public int checkServiceSummary(FormFile file) throws IOESException {
		AppConstants.IOES_LOGGER.info("checkServiceSummary() started");
		int retCode = 0;
		HSSFWorkbook uploadedWorkBook;
		NewOrderModel model = new NewOrderModel();
		
		ArrayList<String> headerNamesdb = new ArrayList<String>();
		ArrayList<String> headerNamesFile = new ArrayList<String>();
		try {
		
			uploadedWorkBook = new HSSFWorkbook(file.getInputStream());
			int noOfSheets = uploadedWorkBook.getNumberOfSheets();
			HSSFSheet lastSheet = uploadedWorkBook.getSheetAt(noOfSheets-1);
			HSSFSheet summarySheet = uploadedWorkBook.getSheet("Service Summary");
			if(summarySheet == null){
				return retCode = 0;
			}
			int productID = 28;
			//int productID = new Double(lastSheet.getRow(1).getCell(0).getNumericCellValue()).intValue();
			ResultSet rs = model.fetchServiceSummaryHeaderForExcel(productID);
			while (rs.next()){
				headerNamesdb.add(rs.getString("ATTDESCRIPTION"));
			}
			int colNo = summarySheet.getRow(0).getLastCellNum();
			for (int count = 0; count<colNo;count++){
				headerNamesFile.add((summarySheet.getRow(0).getCell(count)).toString());
			}
			for(int index = 0,count=1;index< headerNamesdb.size();index++,count++) {
				if(! headerNamesFile.get(count).equals(headerNamesdb.get(index))){
					retCode = 12;
				}
			}
		
		} catch (Exception e) {
			if (retCode == 0) {
				retCode = 7;

			} else {

				AppConstants.IOES_LOGGER
						.error("Error while getting checkSheet "
								+ e.getMessage());
				throw new IOESException(e);
			}
		}

		AppConstants.IOES_LOGGER.info("checkSheet() completed");
		return retCode;

	}
	/**
	 * @param templateFilePath 
	 * @method checkSheet
	 * @purpose checks the number of sheet and sheetname of file uploaded
	 *          against that of template
	 * @param FormFile
	 * @param templateName
	 * @param uploadedFilePath
	 * @return
	 * @throws throws
	 *             IOESException
	 */

	public int checkSheet(FormFile file, String templateFilePath ) throws IOESException {
		AppConstants.IOES_LOGGER.info("checkSheet() completed");
		int sheetsInFile, sheetsInTemplate, retCode = 0;
		HSSFWorkbook uploadedWorkBook, templateWorkbook;
		try {
			FileInputStream templateStream=new FileInputStream(templateFilePath);
			uploadedWorkBook = new HSSFWorkbook(file.getInputStream());
			templateWorkbook = new HSSFWorkbook(templateStream);

			sheetsInFile = uploadedWorkBook.getNumberOfSheets();
			sheetsInTemplate = templateWorkbook.getNumberOfSheets();
			if (uploadedWorkBook.getAllEmbeddedObjects().size() > 0) {
				retCode = 8;
			}
			else if (sheetsInFile != sheetsInTemplate) {
				retCode = 1;
			} else {
				
				for(int count = 0;count< sheetsInFile;count++){
					if(!uploadedWorkBook.getSheetName(count).equals(templateWorkbook.getSheetName(count))){
						retCode = 10;
						break;
					}
				}
					
			}
			if(retCode == 0){
				int rowCount = uploadedWorkBook.getSheetAt(0).getLastRowNum();
				for(int count = 1 ;count< sheetsInFile-1;count ++){
					int noOfRows = uploadedWorkBook.getSheetAt(count).getLastRowNum();
					if( noOfRows != rowCount){
						retCode = 11;
						break;
					}
					
					
			}
			
				
			}
		} catch (Exception e) {
			if (retCode == 0) {
				retCode = 7;

			} else {

				AppConstants.IOES_LOGGER
						.error("Error while getting checkSheet "
								+ e.getMessage());
				throw new IOESException(e);
			}
		}

		AppConstants.IOES_LOGGER.info("checkSheet() completed");
		return retCode;

	}
	/**
	 * @method checkColumns
	 * @purpose checks the number and names of columns of file uploaded against
	 *          the template
	 * @param FormFile
	 * @param templateName,
	 *            uploadedFilePath
	 * @return
	 * @throws IOESException
	 */
	public int checkColumns(FormFile file, String templateFilePath) throws IOESException {
		AppConstants.IOES_LOGGER.info("UploadServiceImpl's checkColumns() completed");
		int numberOfSheet, sheetCol, templateCol, retCode = 0;
		HSSFWorkbook uploadedWorkBook, templateWorkbook;
		HSSFSheet fileSheet, templateSheet;
		HSSFRow fileRow, templateRow;
		HSSFCell cellInSheet, cellInTemplate;

		try {
			FileInputStream templateStream=new FileInputStream(templateFilePath);
			uploadedWorkBook = new HSSFWorkbook(file.getInputStream());
			templateWorkbook = new HSSFWorkbook(templateStream);

			numberOfSheet = uploadedWorkBook.getNumberOfSheets();

			for (int sheetCount = 0; sheetCount < numberOfSheet && retCode == 0; sheetCount++) {
				fileSheet = uploadedWorkBook.getSheetAt(sheetCount);
				templateSheet = templateWorkbook.getSheetAt(sheetCount);

				fileRow = fileSheet.getRow(0);
				templateRow = templateSheet.getRow(0);

				if (fileRow != null && templateRow != null) {
					sheetCol = fileRow.getLastCellNum();
					templateCol = templateRow.getLastCellNum();
					if (templateCol != sheetCol) {
						retCode = 4;
						break;
					} else {
						for (int col = 1; col < sheetCol && retCode == 0; col++) {
							cellInSheet = fileRow.getCell(col);
							cellInTemplate = templateRow.getCell(col);
							if (cellInSheet != null && cellInTemplate != null) {
								if (!(cellInSheet.toString().trim()
										.equals(cellInTemplate.toString()
												.trim()))) {
									retCode = 5;
									break;
								}
							} else if (!(cellInSheet == null && cellInTemplate == null)) {
								retCode = 5;
								break;
							}

						}
					}
				} else if (fileRow == null) {
					retCode = 5;
					break;
				}

			}

		} catch (Exception e) {
			AppConstants.IOES_LOGGER.error("Error while getting checkColumns " + e.getMessage());
			throw new IOESException(e);
		}

		AppConstants.IOES_LOGGER.info("checkColumns() completed");
		return retCode;
	}
	/**
	 * @method checkBlankSheetAndDataLength
	 * @purpose check if file uploaded has any blank sheet
	 * @param FormFile,
	 *            uploadedFilePath
	 * @return
	 * @throws IOESException
	 */
	public int checkBlankSheetAndDataLength(FormFile file,String templateFilePath,ActionMessages messages)
			throws IOESException {
 		AppConstants.IOES_LOGGER.info("checkBlankSheet() completed");
		int sheetsInFile, retCode = 0, rowCount, columnCount,templateColumnCount;
		HSSFWorkbook uploadedWorkBook,templateWorkbook;
		HSSFSheet fileSheet,templateFileSheet;
		HSSFRow fileRow,templateMaxLengthRow;
		HSSFCell fileCell,templateMaxLengthCell;
		try {
			int errorsFound=0;
			FileInputStream templateStream=new FileInputStream(templateFilePath);
			uploadedWorkBook = new HSSFWorkbook(file.getInputStream());
			sheetsInFile = uploadedWorkBook.getNumberOfSheets();
			
			templateWorkbook = new HSSFWorkbook(templateStream);
			int rowMaxLength=1;
			for (int sheetCount = 0; sheetCount < sheetsInFile ; sheetCount++) {
				retCode = 0;
				fileSheet = uploadedWorkBook.getSheetAt(sheetCount);
				templateFileSheet = templateWorkbook.getSheetAt(sheetCount);
				rowCount = fileSheet.getLastRowNum();
				int noOfRows = fileSheet.getPhysicalNumberOfRows();
				if(noOfRows==0)
				{
					return 3;
				}
				
				Integer [] colAllowedLengths=null;
				String [] colNames=null; 
				templateMaxLengthRow = templateFileSheet.getRow(rowMaxLength);
				if (templateMaxLengthRow != null) {
					templateColumnCount = templateMaxLengthRow.getLastCellNum();
					colAllowedLengths=new Integer[templateColumnCount];
					for(int col=0;col<templateColumnCount;col++)
					{
						templateMaxLengthCell = templateMaxLengthRow.getCell(col);
						if (templateMaxLengthCell!=null && !"".equals(templateMaxLengthCell.toString().trim())) {
							Float f=Float.parseFloat(templateMaxLengthCell.toString().trim());
							colAllowedLengths[col]=f.intValue();
						}
						else
						{
							colAllowedLengths[col]=0;
						}
					}
				}
				
				
				
				
				/*for (int row = 1; row <= rowCount ; row++) {
					fileRow = fileSheet.getRow(row);
					if (fileRow != null) {
						columnCount = fileRow.getLastCellNum();
						for (int col = 0; col < columnCount ; col++) {
							fileCell = fileRow.getCell(col);
							if (fileCell != null) {
//								check length
								
								//String s=fileCell.getStringCellValue();
								String str=fileCell.toString().trim();
								if(fileCell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC)
								{
									double val=fileCell.getNumericCellValue();
									String valstr=String.valueOf(val);
									str=Utility.convertWithOutE_WithOutDotZero(valstr);
								}
								if (colAllowedLengths[col]==0 || str.length()<=colAllowedLengths[col]) {
									retCode = 0;
								}else
								{
									String columnName=templateFileSheet.getRow(0).getCell(col).toString();
									messages.add("colLen_overflow",new ActionMessage("errors.excel.column.length",columnName,colAllowedLengths[col],row+1));
									errorsFound=1;
								}
							}
						}
					}
				}*/
				/*if (retCode == 3) {
					break;
				}*/

			}
			if(errorsFound==1)
			{
				retCode=9;
			}

		} catch (Exception e) {
			AppConstants.IOES_LOGGER.error("Error while getting checkBlankSheet "
					+ e.getMessage());
			throw new IOESException(e);
		}

		
		AppConstants.IOES_LOGGER.info("checkBlankSheet() completed");
		return retCode;
	}


}
