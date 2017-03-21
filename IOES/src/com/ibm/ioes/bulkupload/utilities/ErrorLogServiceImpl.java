//=================================================================================================================
//Tag Name		Resource Name		Date		CSR No			Description
//[001]			ANIL KUMAR	   28-JULY-2011		00-05422		to get Error excel file
//[002]			ANIL KUMAR	   28-JULY-2011		00-05422		to get Result excel file
//[003]			ANIL KUMAR	   28-JULY-2011		00-05422		to get filled template excel file	
//=================================================================================================================
package com.ibm.ioes.bulkupload.utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;


import com.ibm.ioes.beans.BillingTriggerValidation;
import com.ibm.ioes.beans.ViewOrderDto;
import com.ibm.ioes.bulkupload.dao.ErrorFileDaoImpl;
import com.ibm.ioes.bulkupload.dto.ErrorLogDto;
import com.ibm.ioes.bulkupload.dto.TransactionTemplateDto;
import com.ibm.ioes.exception.IOESException;
import com.ibm.ioes.model.ViewOrderModel;
import com.ibm.ioes.utilities.Utility;

/**
 * @version 	1.0
 * @author		Anil Kumar 
 */
public class ErrorLogServiceImpl
{
	/**
	 * Logger for the class.
	**/
	private static final Logger logger;
	
	static {

		logger = Logger.getLogger(ErrorLogServiceImpl.class);
	}
	//START[001]
	@SuppressWarnings("deprecation")
	public String getErrorExcel(String filePath, int fileID) throws IOESException 
	{
		//logger.info(" Entered into getErrorExcel method of " + this.getClass().getSimpleName());
		
		int colCount , ctr=0;
		String fileName ;
		HSSFWorkbook wb;
		HSSFSheet ws;
		HSSFRow wr;
		HSSFCell wc;
		ArrayList errVal = new ArrayList() ;
		ErrorFileDaoImpl objDao = new ErrorFileDaoImpl();
		ErrorLogDto dtoObj ;
		BillingTriggerValidation validateDto=null;
		
		try
		{
			ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");

			fileName = filePath.substring((filePath.lastIndexOf("/") + 1),filePath.length());
			String newFile = bundle.getString("excel.errors")+"/"+fileName;
			FileOutputStream fileOut = new FileOutputStream(newFile);
			wb = new HSSFWorkbook(new FileInputStream(filePath));
			
			errVal = objDao.getErrorLog(fileID);
			System.out.println(errVal.toString());
			System.out.println(wb.getNumberOfSheets());
			for(int s = 0; s < wb.getNumberOfSheets(); s++)
			{
				
				ws = wb.getSheetAt(s);
				wr = ws.getRow(1);
				colCount = wr.getLastCellNum();
				wc = wr.createCell(colCount);
				wc.setCellValue("ERROR LOG");
				
				for(int r = 2; r <= ws.getLastRowNum(); r++)
				{
					if((ctr<errVal.size())){
					dtoObj = (ErrorLogDto)errVal.get(ctr);
					wr = ws.getRow(r);
					if(wr!=null)
					{
						int chk =0;
						for(int col=0;col<colCount;col++)
						{
							wc = wr.getCell(col);
							if(wc!=null)
							{
								if(!(wc.toString().trim().equals("")))
								{
									chk = 1;
								}
							}
						}
						if(chk==1)
						{
							wc = wr.createCell(colCount);
							System.out.println(dtoObj.getErrorLogValue());
							if(dtoObj.getErrorLogValue()==null)
							{
								 wc.setCellValue("No Errors");
							}
							else
							{
								 wc.setCellValue(dtoObj.getErrorLogValue().toString());
							}
								
							ctr++;
						}
					}
					}
				}
			}
			wb.write(fileOut);
			fileOut.close();
			filePath = newFile;
		}		
		catch (IOESException ex)
		{
			logger.error(ex.getMessage() + "::BULKUPLOAD_ERROR:: Exception occured in getErrorExcel method of " + this.getClass().getSimpleName());
			throw new IOESException("SQL Exception : "+ ex.getMessage(), ex);
		}
		catch(IOException ioExp)
		{
			//logger.error(ioExp.getMessage() + " Exception occured in getErrorExcel method of " + this.getClass().getSimpleName());
			//throw new IOESException("SQL Exception : "+ ioExp.getMessage(), ioExp);
			Utility.LOG(true, false, ioExp, "::BULKUPLOAD_ERROR:: Exception occured in getErrorExcel method of " + this.getClass().getSimpleName());
			return filePath="NOTFOUND";
		}
		return filePath;
	}
	//END[001]
	//START[002]
	public String getResultExcel(String filePath, int fileID) throws IOESException 
	{
		//logger.info(" Entered into getErrorExcel method of " + this.getClass().getSimpleName());
		
		int colCount , ctr=0;
		String fileName ;
		HSSFWorkbook wb;
		HSSFSheet ws;
		HSSFRow wr;
		HSSFCell wc;
		ArrayList errVal = new ArrayList() ;
		ErrorFileDaoImpl objDao = new ErrorFileDaoImpl();
		ErrorLogDto dtoObj ;
		
		try
		{
			ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");

			fileName = filePath.substring((filePath.lastIndexOf("/") + 1),filePath.length());
			String newFile = bundle.getString("excel.success")+"/"+fileName;
			FileOutputStream fileOut = new FileOutputStream(newFile);
			wb = new HSSFWorkbook(new FileInputStream(filePath));
			
			errVal = objDao.getResultLog(fileID);
			System.out.println(errVal.toString());
			System.out.println(wb.getNumberOfSheets());
			for(int s = 0; s < wb.getNumberOfSheets(); s++)
			{
				
				ws = wb.getSheetAt(s);
				wr = ws.getRow(1);
				colCount = wr.getLastCellNum();
				wc = wr.createCell(colCount);
				wc.setCellValue("RESULT LOG"+"_"+"ORDERNO");
				
				for(int r = 2; r <= ws.getLastRowNum(); r++)
				{
					if((ctr<errVal.size())){
					dtoObj = (ErrorLogDto)errVal.get(ctr);
					wr = ws.getRow(r);
					if(wr!=null)
					{
						int chk =0;
						for(int col=0;col<colCount;col++)
						{
							wc = wr.getCell(col);
							if(wc!=null)
							{
								if(!(wc.toString().trim().equals("")))
								{
									chk = 1;
								}
							}
						}
						if(chk==1)
						{
							wc = wr.createCell(colCount);
							System.out.println(dtoObj.getResultLogValue());
							if(dtoObj.getResultLogValue()==null)
							{
								 wc.setCellValue("SUCCESS");
							}
							else
							{
								if(dtoObj.getOrderNo()!=null)
									wc.setCellValue(dtoObj.getResultLogValue().toString()+"_("+dtoObj.getOrderNo().toString()+")");
							}
								
							ctr++;
						}
					}
					}
				}
			}
			wb.write(fileOut);
			fileOut.close();
			filePath = newFile;
		}
		catch (IOESException ex)
		{
			logger.error(ex.getMessage() + "::BULKUPLOAD_ERROR:: Exception occured in getResultExcel method of " + this.getClass().getSimpleName());
			throw new IOESException("SQL Exception : "+ ex.getMessage(), ex);
		}
		catch(IOException ioExp)
		{
			//logger.error(ioExp.getMessage() + " Exception occured in getErrorExcel method of " + this.getClass().getSimpleName());
			//throw new IOESException("SQL Exception : "+ ioExp.getMessage(), ioExp);
			Utility.LOG(true, false, ioExp, "::BULKUPLOAD_ERROR:: Exception occured in getResultExcel method of " + this.getClass().getSimpleName());
			return filePath="NOTFOUND";
		}
		return filePath;
	}
//END[002]
	
	public HSSFColor setColor(HSSFWorkbook workbook, byte r,byte g, byte b){
		HSSFPalette palette = workbook.getCustomPalette();
		HSSFColor hssfColor = null;
		try {
		hssfColor= palette.findColor(r, g, b); 
		if (hssfColor == null ){
		    palette.setColorAtIndex(HSSFColor.LAVENDER.index, r, g,b);
		    hssfColor = palette.getColor(HSSFColor.LAVENDER.index);
		}
		 } catch (Exception e) {
			 logger.error(e);
			 Utility.LOG(true, false, e, "::BULKUPLOAD_ERROR:: Exception occured in setColor method of " + this.getClass().getSimpleName());
		}

		 return hssfColor;
		}
	
	/*function:getFilledTemplate
	 * return:String
	 * createdby:Anil Kumar
	 * Purpose:to download filled template based on parameter.
	 * */
	//START[003]
	public String getFilledTemplate(String filePath,int templateId,int flag, String logicalLSI) throws IOESException, ParseException 
	{
		//logger.info(" Entered into getErrorExcel method of " + this.getClass().getSimpleName());
				
		String fileName ;
		HSSFWorkbook wb;
		HSSFSheet ws;
		HSSFRow wr;
		HSSFCell wc;
		ArrayList filledTemplateData = new ArrayList() ;
		ArrayList<ViewOrderDto> filledTemplateDataLineDetails = new ArrayList<ViewOrderDto>() ;
		ArrayList<ViewOrderDto> filledTemplateDataChargeDetails = new ArrayList<ViewOrderDto>() ;
		ErrorFileDaoImpl objDao = new ErrorFileDaoImpl();
		ViewOrderModel objviewmodel=new ViewOrderModel();
		//ErrorLogDto dtoObj ;
		TransactionTemplateDto dtoObj;
		ViewOrderDto dtoObj1;
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		int totalRowsOfSheet=0;
		ViewOrderDto objdto=null;
		String str=null;
		BillingTriggerValidation validateDto=null;
		try
		{
			ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");

			fileName = filePath.substring((filePath.lastIndexOf("/") + 1),filePath.length());
			String newFile = bundle.getString("excel.filledTemplate")+"/"+fileName;
			FileOutputStream fileOut = new FileOutputStream(newFile);
			wb = new HSSFWorkbook(new FileInputStream(filePath));
		    HSSFCellStyle whiteFG_yellow = wb.createCellStyle();
		    
		    HSSFColor yellow =  setColor(wb,(byte) 0xFF, (byte)0xFF,(byte) 0x00);
		    //whiteFG_yellow.setFillBackgroundColor(HSSFColor.YELLOW.index);
		    whiteFG_yellow.setFillBackgroundColor(yellow.getIndex());
		    
		    HSSFCellStyle whiteFG_green = wb.createCellStyle();
		    HSSFColor green =  setColor(wb,(byte) 0x00, (byte)0xFF,(byte) 0x00);
		    //whiteFG_green.setFillBackgroundColor(HSSFColor.GREEN.index);
		    whiteFG_green.setFillBackgroundColor(green.getIndex());
				
			//System.out.println(filledTemplateData.toString());
			//System.out.println(wb.getNumberOfSheets());
			
			
			for(int s = 0; s < wb.getNumberOfSheets(); s++)
			{				
				ws = wb.getSheetAt(s);	
				if(s==0 && (templateId==1 || templateId==22 || templateId==21 || templateId==41))
				{
					int  ctr=0,totalRowsOfSheetMain=0,totalRowsOfSheetAtt=0;
					totalRowsOfSheetMain=objDao.getTotalRowsOfSheet(s+1,templateId,flag,logicalLSI);
					filledTemplateData = objDao.getFilledTemplate(s+1,templateId,flag,logicalLSI);
					for(int r = 2; r <= (totalRowsOfSheetMain+1); r++)
					{
						dtoObj = (TransactionTemplateDto)filledTemplateData.get(ctr);
						wr=ws.createRow(r);
						if(wr!=null)
						{
							wr.createCell(1).setCellValue(dtoObj.getOrderNo());
							wr.createCell(2).setCellValue(new HSSFRichTextString((dtoObj.getAccountID())));
							wr.createCell(3).setCellValue(new HSSFRichTextString((dtoObj.getSource())));
							wr.createCell(4).setCellValue(new HSSFRichTextString((dtoObj.getCurrencyID())));
							wr.createCell(5).setCellValue(new HSSFRichTextString((dtoObj.getOpportunityId())));
							wr.createCell(6).setCellValue(new HSSFRichTextString((dtoObj.getQuoteNo())));
							wr.createCell(7).setCellValue(new HSSFRichTextString((dtoObj.getProjectMangerID())));
							wr.createCell(8).setCellValue(new HSSFRichTextString((dtoObj.getZoneId())));
						}
						ctr++;
					}
					totalRowsOfSheetAtt=objDao.getTotalRowsOfSheet(11,templateId,flag,logicalLSI);
					filledTemplateData = objDao.getFilledTemplate(11,templateId,flag,logicalLSI);
					ctr=0;
					for(int r = 2; r <= (totalRowsOfSheetMain+1); r++)
					{						
							wr=ws.getRow(r);							
							dtoObj = (TransactionTemplateDto)filledTemplateData.get(ctr);
							if(wr!=null)
							{
								wr.createCell(9).setCellValue(new HSSFRichTextString((dtoObj.getRFSDate())));
								wr.createCell(10).setCellValue(new HSSFRichTextString((dtoObj.getActMngrPhoneNo())));
								wr.createCell(11).setCellValue(new HSSFRichTextString((dtoObj.getActMngrEmailID())));
								wr.createCell(12).setCellValue(new HSSFRichTextString((dtoObj.getIRUOrderYN())));
								wr.createCell(13).setCellValue(new HSSFRichTextString((dtoObj.getFreePeriodYN())));
								wr.createCell(14).setCellValue(new HSSFRichTextString((dtoObj.getOrdExclusiveTax())));
								wr.createCell(15).setCellValue(new HSSFRichTextString((dtoObj.getCAFDate())));
							}
						ctr++;										
					}
				}
				//GAM Sheet
				if(s==1 && (templateId==1 || templateId==22 || templateId==21 || templateId==41)){
					//to do nothing
				}
				//Contact Sheet
				if(s==2 && (templateId==1 || templateId==22 || templateId==21 || templateId==41))
				{		
					int  ctr=0,rownum=1;
					totalRowsOfSheet=objDao.getTotalRowsOfSheet(s+1,templateId,flag,logicalLSI);
					filledTemplateData = objDao.getFilledTemplate(s+1,templateId,flag,logicalLSI);	
					
					for(int r = 2; r <= (totalRowsOfSheet+1); r++)
					{
						dtoObj = (TransactionTemplateDto)filledTemplateData.get(ctr);					
						wr=ws.createRow(r);
						if(wr!=null)
						{
							wr.createCell(1).setCellValue(rownum);
							wr.createCell(2).setCellValue(dtoObj.getOrderNo());
							wr.createCell(3).setCellValue(dtoObj.getContactType());
							wr.createCell(4).setCellValue(new HSSFRichTextString((dtoObj.getSalutation())));
							wr.createCell(5).setCellValue(new HSSFRichTextString((dtoObj.getFirstName())));
							wr.createCell(6).setCellValue(new HSSFRichTextString((dtoObj.getLastName())));							
							wr.createCell(7).setCellValue(new HSSFRichTextString((dtoObj.getEmail())));
							wr.createCell(8).setCellValue(new HSSFRichTextString((dtoObj.getCellno())));
							wr.createCell(9).setCellValue(new HSSFRichTextString((dtoObj.getFaxno())));
							wr.createCell(10).setCellValue(rownum);
							wr.createCell(11).setCellValue(new HSSFRichTextString((dtoObj.getAddress1())));
							wr.createCell(12).setCellValue(new HSSFRichTextString((dtoObj.getAddress2())));
							wr.createCell(13).setCellValue(new HSSFRichTextString((dtoObj.getAddress3())));
							wr.createCell(14).setCellValue(new HSSFRichTextString((dtoObj.getCountrycode())));
							wr.createCell(15).setCellValue(new HSSFRichTextString((dtoObj.getStateid())));
							wr.createCell(16).setCellValue(new HSSFRichTextString((dtoObj.getCityid())));
							wr.createCell(17).setCellValue(new HSSFRichTextString((dtoObj.getPincode())));												
						}
						ctr++;rownum++;
					}
				}
				if(s==3 && (templateId==1 || templateId==22 || templateId==21))
				{		
					int  ctr=0,rownum=1;
					if(templateId==21){
						totalRowsOfSheet=objDao.getTotalRowsOfSheet(s+1,templateId,flag,logicalLSI);	
						filledTemplateData = objDao.getFilledTemplate(s+1,templateId,flag,logicalLSI);	
						for(int r = 2; r <= (totalRowsOfSheet+1); r++)
						{
							dtoObj = (TransactionTemplateDto)filledTemplateData.get(ctr);					
							wr=ws.createRow(r);
							if(wr!=null)
							{			
								wr.createCell(1).setCellValue(rownum);
								wr.createCell(2).setCellValue(dtoObj.getOrderNo());
								wr.createCell(3).setCellValue(dtoObj.getLogicalsiNo());
								wr.createCell(4).setCellValue(dtoObj.getServiceid());
								wr.createCell(5).setCellValue(new HSSFRichTextString((dtoObj.getServiceName())));	
								wr.createCell(6).setCellValue(dtoObj.getLineItemID());	
								wr.createCell(7).setCellValue(new HSSFRichTextString((dtoObj.getLineItemName())));	
																		
							}
							ctr++;rownum++;
						}
					}
					else 
					{
						totalRowsOfSheet=objDao.getTotalRowsOfSheet(s+1,templateId,flag,logicalLSI);	
						filledTemplateData = objDao.getFilledTemplate(s+1,templateId,flag,logicalLSI);	
						for(int r = 2; r <= (totalRowsOfSheet+1); r++)
						{
							dtoObj = (TransactionTemplateDto)filledTemplateData.get(ctr);					
							wr=ws.createRow(r);
							if(wr!=null)
							{			
								wr.createCell(1).setCellValue(rownum);
								wr.createCell(2).setCellValue(dtoObj.getOrderNo());
								wr.createCell(3).setCellValue(dtoObj.getLogicalsiNo());
								wr.createCell(4).setCellValue(dtoObj.getServiceid());							
								wr.createCell(5).setCellValue(dtoObj.getLineItemID());											
							}
							ctr++;rownum++;
						}
				}
			}
			if(s==3 && templateId==41)
			{
				/*int  ctr=0,rownum=1;
				totalRowsOfSheet=objDao.getTotalRowsOfSheet(s+1,templateId,flag,logicalLSI);	
				filledTemplateData = objDao.getFilledTemplate(s+1,templateId,flag,logicalLSI);	
				for(int r = 2; r <= (totalRowsOfSheet+1); r++)
				{
					dtoObj = (TransactionTemplateDto)filledTemplateData.get(ctr);					
					wr=ws.createRow(r);
					if(wr!=null)
					{			
						wr.createCell(1).setCellValue(rownum);
						wr.createCell(2).setCellValue(dtoObj.getOrderNo());
						wr.createCell(3).setCellValue(new HSSFRichTextString((dtoObj.getCustPONumber())));
						wr.createCell(4).setCellValue(new HSSFRichTextString((dtoObj.getCustPODate())));							
						wr.createCell(5).setCellValue(new HSSFRichTextString((dtoObj.getIsDefaultPO())));
						wr.createCell(6).setCellValue(new HSSFRichTextString((dtoObj.getLegalEntity())));
						wr.createCell(7).setCellValue(new HSSFRichTextString((dtoObj.getTotalPOAmount())));
						wr.createCell(8).setCellValue(new HSSFRichTextString((dtoObj.getPeriodInMonths())));
						wr.createCell(9).setCellValue(new HSSFRichTextString((dtoObj.getContractStartDate())));
						wr.createCell(10).setCellValue(new HSSFRichTextString((dtoObj.getContractEndDate())));
						wr.createCell(11).setCellValue(new HSSFRichTextString((dtoObj.getPoRemarks())));
						wr.createCell(12).setCellValue(new HSSFRichTextString((dtoObj.getPoEmailId())));
					}
					ctr++;rownum++;
				}*/
			}
			if(s==4 && templateId==41)
			{
				int  ctr=0,rownum=1;
				totalRowsOfSheet=objDao.getTotalRowsOfSheet(s+1,templateId,flag,logicalLSI);	
				filledTemplateData = objDao.getFilledTemplate(s+1,templateId,flag,logicalLSI);	
				for(int r = 2; r <= (totalRowsOfSheet+1); r++)
				{
					dtoObj = (TransactionTemplateDto)filledTemplateData.get(ctr);					
					wr=ws.createRow(r);
					if(wr!=null)
					{			
						wr.createCell(1).setCellValue(rownum);
						wr.createCell(2).setCellValue(dtoObj.getOrderNo());
						wr.createCell(3).setCellValue(dtoObj.getLogicalsiNo());
						wr.createCell(4).setCellValue(dtoObj.getServiceid());							
						wr.createCell(5).setCellValue(dtoObj.getLineItemID());
						wr.createCell(6).setCellValue(dtoObj.getChargeID());
						wr.createCell(7).setCellValue(dtoObj.getChargeAmount());						
						wr.createCell(8).setCellValue(new HSSFRichTextString((dtoObj.getChargeFrequency())));
						wr.createCell(9).setCellValue(new HSSFRichTextString((dtoObj.getStrChargeType())));
						wr.createCell(10).setCellValue(new HSSFRichTextString((dtoObj.getStrChargeName())));
						wr.createCell(11).setCellValue(dtoObj.getFrequncyAmount());
						wr.createCell(12).setCellValue(new HSSFRichTextString((dtoObj.getAnnotation())));
					}
					ctr++;rownum++;
				}
			}
			if(s==5 && templateId==41)
			{
				int  ctr=0,rownum=1;
				totalRowsOfSheet=objDao.getTotalRowsOfSheet(s+1,templateId,flag,logicalLSI);	
				filledTemplateData = objDao.getFilledTemplate(s+1,templateId,flag,logicalLSI);	
				for(int r = 2; r <= (totalRowsOfSheet+1); r++)
				{
					dtoObj = (TransactionTemplateDto)filledTemplateData.get(ctr);					
					wr=ws.createRow(r);
					if(wr!=null)
					{			
						wr.createCell(1).setCellValue(rownum);
						wr.createCell(2).setCellValue(new HSSFRichTextString(""));
						wr.createCell(3).setCellValue(dtoObj.getLineItemID());						
						wr.createCell(4).setCellValue(dtoObj.getCreditPeriodID());
						wr.createCell(5).setCellValue(new HSSFRichTextString((dtoObj.getCreditPeriodName())));
						wr.createCell(6).setCellValue(dtoObj.getLegealEntityID());
						wr.createCell(7).setCellValue(new HSSFRichTextString((dtoObj.getLegalEntityName())));
						wr.createCell(8).setCellValue(dtoObj.getLicenseCompanyID());
						wr.createCell(9).setCellValue(new HSSFRichTextString((dtoObj.getLicenseCompanyName())));
						wr.createCell(10).setCellValue(dtoObj.getBillingModeID());
						wr.createCell(11).setCellValue(new HSSFRichTextString((dtoObj.getBillingModeName())));
						wr.createCell(12).setCellValue(dtoObj.getBillingFormatID());
						wr.createCell(13).setCellValue(new HSSFRichTextString((dtoObj.getBillingFormatName())));
						wr.createCell(14).setCellValue(dtoObj.getBillingTypeID());
						wr.createCell(15).setCellValue(new HSSFRichTextString((dtoObj.getBillingTypeName())));
						wr.createCell(16).setCellValue(dtoObj.getTaxationID());
						wr.createCell(17).setCellValue(new HSSFRichTextString(dtoObj.getTaxationName()));
						wr.createCell(18).setCellValue(dtoObj.getBillingLevelID());
						wr.createCell(19).setCellValue(new HSSFRichTextString(dtoObj.getBillingLevelName()));
						wr.createCell(20).setCellValue(dtoObj.getNoticePeriod());
						wr.createCell(21).setCellValue(new HSSFRichTextString(dtoObj.getPenaltyClause()));
						wr.createCell(22).setCellValue(dtoObj.getCommitPeriod());
						wr.createCell(23).setCellValue(dtoObj.getIsNfa());
						wr.createCell(24).setCellValue(dtoObj.getBcpID());
						wr.createCell(25).setCellValue(new HSSFRichTextString(dtoObj.getBcpName()));
						wr.createCell(26).setCellValue(new HSSFRichTextString(dtoObj.getStandardReasonId()));
						wr.createCell(27).setCellValue(new HSSFRichTextString(dtoObj.getStandardReasonName()));						
						
					}
					ctr++;rownum++;
				}
			}
			
			// billing trigger bulkupload  sheet 1 start
			
			if(s==0 && templateId==61)
			{
				int  ctr=0,rownum=1;
				
				filledTemplateDataLineDetails = objviewmodel.getFilledTemplateforBillingLineSectionBulkUpload();	
				for(int r =2; r <=(filledTemplateDataLineDetails.size()+1); r++)
				{
					dtoObj1 = (ViewOrderDto)filledTemplateDataLineDetails.get(ctr);	
					wr=ws.createRow(r);
					if(wr!=null)
					{
						HSSFCell cell0=wr.createCell(0);
						cell0.setCellValue(rownum);
						
						HSSFCell cell1=wr.createCell(1);
						cell1.setCellValue(new HSSFRichTextString(dtoObj1.getLineNumber()));
						HSSFCellStyle sty1=ws.getRow(1).getCell(1).getCellStyle();
						
						wr.createCell(2).setCellValue(new HSSFRichTextString(dtoObj1.getLineName()));	
						wr.createCell(3).setCellValue(new HSSFRichTextString(dtoObj1.getLogicalSino()));	
						wr.createCell(4).setCellValue(new HSSFRichTextString(dtoObj1.getCustLogicalSino()));	
						wr.createCell(5).setCellValue(dtoObj1.getOrderno());	
						wr.createCell(6).setCellValue(new HSSFRichTextString(dtoObj1.getOrdertype()));	
						wr.createCell(7).setCellValue(new HSSFRichTextString(dtoObj1.getOrder_subtype()));	
						wr.createCell(8).setCellValue(new HSSFRichTextString(dtoObj1.getSiid()));	
						wr.createCell(9).setCellValue(new HSSFRichTextString(dtoObj1.getAccountid()));	
						wr.createCell(10).setCellValue(new HSSFRichTextString(dtoObj1.getFx_status()));	
						wr.createCell(11).setCellValue(new HSSFRichTextString(dtoObj1.getLine_status()));	
						wr.createCell(12).setCellValue(new HSSFRichTextString(dtoObj1.getChallen_No()));	
						wr.createCell(13).setCellValue(new HSSFRichTextString(dtoObj1.getChallen_date()));
						validateDto=dtoObj1.getBillingTriggerAllowDenyLogic();
						
						HSSFCell cell14= wr.createCell(14);
						cell14.setCellValue(new HSSFRichTextString(dtoObj1.getLocNo()));
						HSSFCellStyle sty=ws.getRow(1).getCell(14).getCellStyle();
						if("allow".equals(validateDto.getLocNoForEdit()))
						{
							cell14.setCellStyle(sty);
							
						}
						else
						{
							cell14.setCellStyle(sty1);
						}
						
						HSSFCell cell15= wr.createCell(15);
						if(!(dtoObj1.getLocDate()==null || "".equals(dtoObj1.getLocDate())))
						{
							cell15.setCellValue(new HSSFRichTextString(Utility.showDate_Report4(df.parse(dtoObj1.getLocDate()))));
						}
						else
						{
							cell15.setCellValue(new HSSFRichTextString(dtoObj1.getLocDate()));
						}
						if("allow".equals(validateDto.getLocDateForEdit()))
						{
							cell15.setCellStyle(sty);
						}	
						else
						{
							cell15.setCellStyle(sty1);
						}
						
						HSSFCell cell16= wr.createCell(16);
						if(!(dtoObj1.getLocRecDate()==null || "".equals(dtoObj1.getLocRecDate())))
						{
							cell16.setCellValue(new HSSFRichTextString(Utility.showDate_Report4(df.parse(dtoObj1.getLocRecDate()))));
						}
						else
						{
							cell16.setCellValue(new HSSFRichTextString(dtoObj1.getLocRecDate()));
						}
						
						if("allow".equals(validateDto.getLocRecDateForEdit()))
						{
							cell16.setCellStyle(sty);
						}	
						else
						{
							cell16.setCellStyle(sty1);
						}
						
						HSSFCell cell17= wr.createCell(17);
						if(!(dtoObj1.getBillingTriggerDate()==null || "".equals(dtoObj1.getBillingTriggerDate())))
						{
							cell17.setCellValue(new HSSFRichTextString(Utility.showDate_Report4(df.parse(dtoObj1.getBillingTriggerDate()))));
						}
						else
						{
							cell17.setCellValue(new HSSFRichTextString(dtoObj1.getBillingTriggerDate()));
						}
						
						
						if("allow".equals(validateDto.getBtdForEdit()))
						{
							cell17.setCellStyle(sty);
						}	
						else
						{
							cell17.setCellStyle(sty1);
						}
						
						wr.createCell(18).setCellValue(new HSSFRichTextString(dtoObj1.getBillingTriggerProcess()));	
					
					}
					ctr++;rownum++;
				}
			}
			
			// billing trigger bulkupload  sheet 1 end
			
			//billing trigger bulkupload  sheet 2 start
			if(s==1 && templateId==61)
			{
				int  ctr=0,rownum=1;
				if(filledTemplateDataLineDetails.size()>0)
				{
					
				filledTemplateDataChargeDetails = objviewmodel.getFilledTemplateforBillingChargeSectionBulkUpload();
				for(int r =2; r <=(filledTemplateDataChargeDetails.size()+1); r++)
				{
					dtoObj1 = (ViewOrderDto)filledTemplateDataChargeDetails.get(ctr);					
					wr=ws.createRow(r);
					if(wr!=null)
					{	
						wr.createCell(0).setCellValue(rownum);
						HSSFCell cell1=wr.createCell(1);
						cell1.setCellValue(dtoObj1.getChargeInfoId());
						HSSFCellStyle sty1=ws.getRow(1).getCell(1).getCellStyle();
						wr.createCell(2).setCellValue(new HSSFRichTextString(dtoObj1.getLineNumber()));
						wr.createCell(3).setCellValue(new HSSFRichTextString(dtoObj1.getChargeType()));
						wr.createCell(4).setCellValue(new HSSFRichTextString(dtoObj1.getChargeName()));
						wr.createCell(5).setCellValue(dtoObj1.getChargePeriod());
						wr.createCell(6).setCellValue(dtoObj1.getChargeAmt());
						wr.createCell(7).setCellValue(new HSSFRichTextString(dtoObj1.getChargeStatus()));
						HSSFCell cell8=wr.createCell(8);
						HSSFCellStyle sty8=ws.getRow(1).getCell(8).getCellStyle();
						if(!(dtoObj1.getDisconnectiondate()==null || "".equals(dtoObj1.getDisconnectiondate())))
						{
							cell8.setCellValue(new HSSFRichTextString(Utility.showDate_Report4(df.parse(dtoObj1.getDisconnectiondate()))));
						}
						else
						{
							cell8.setCellValue(new HSSFRichTextString(dtoObj1.getDisconnectiondate()));
						}
						if("Changed".equalsIgnoreCase(dtoObj1.getChargeStatus()))
								{
										cell8.setCellStyle(sty8);
							
								}
						
						wr.createCell(9).setCellValue(new HSSFRichTextString(dtoObj1.getChargeCreatedOnOrder()));
						wr.createCell(10).setCellValue(new HSSFRichTextString(dtoObj1.getChargeEndedOnOrder()));
						wr.createCell(11).setCellValue(new HSSFRichTextString(dtoObj1.getBillPeriod()));
						wr.createCell(12).setCellValue(new HSSFRichTextString(dtoObj1.getStartdatelogic()));
						wr.createCell(13).setCellValue(dtoObj1.getStart_date_days());
						wr.createCell(14).setCellValue(dtoObj1.getStart_date_month());
						wr.createCell(15).setCellValue(new HSSFRichTextString(dtoObj1.getEnddatelogic()));
						wr.createCell(16).setCellValue(dtoObj1.getEnd_date_days());
						wr.createCell(17).setCellValue(dtoObj1.getEnd_date_month());
						wr.createCell(18).setCellValue(new HSSFRichTextString(dtoObj1.getChargeEndDate_String()));
						wr.createCell(19).setCellValue(new HSSFRichTextString(dtoObj1.getAnnualRate()));
						wr.createCell(20).setCellValue(new HSSFRichTextString(dtoObj1.getAnnotation()));
						wr.createCell(21).setCellValue(new HSSFRichTextString(dtoObj1.getStartTokenNo()));
						wr.createCell(22).setCellValue(new HSSFRichTextString(dtoObj1.getStartFxStatus()));
						wr.createCell(23).setCellValue(new HSSFRichTextString(dtoObj1.getStartFxNo()));
						wr.createCell(24).setCellValue(new HSSFRichTextString(dtoObj1.getEndTokenNo()));
						wr.createCell(25).setCellValue(new HSSFRichTextString(dtoObj1.getEndFxStatus()));
						wr.createCell(26).setCellValue(new HSSFRichTextString(dtoObj1.getEndFxNo()));
						wr.createCell(27).setCellValue(new HSSFRichTextString(dtoObj1.getChargeStartStatus()));
						wr.createCell(28).setCellValue(new HSSFRichTextString(dtoObj1.getChargeEndStatus()));
						wr.createCell(29).setCellValue(new HSSFRichTextString(dtoObj1.getChargefrequency()));
						wr.createCell(30).setCellValue(new HSSFRichTextString(dtoObj1.getFxViewId()));
					}
					ctr++;rownum++;
				}
			}	
		}
//			billing trigger bulkupload  sheet 2 end	
	}
			wb.write(fileOut);
			fileOut.close();
			filePath = newFile;
		}		
		catch (IOESException ex)
		{
			logger.error(ex.getMessage() + "::BULKUPLOAD_ERROR:: Exception occured in getFilledTemplate method of " + this.getClass().getSimpleName());
			throw new IOESException("SQL Exception : "+ ex.getMessage(), ex);
		}
		catch(IOException ioExp)
		{
			//logger.error(ioExp.getMessage() + " Exception occured in getErrorExcel method of " + this.getClass().getSimpleName());
			//throw new IOESException("SQL Exception : "+ ioExp.getMessage(), ioExp);
			Utility.LOG(true, false, ioExp, "::BULKUPLOAD_ERROR:: Exception occured in getFilledTemplate method of " + this.getClass().getSimpleName());
			return filePath="NOTFOUND";
		}
		return filePath;
	}
	//END[003]
	
	// added by maisha start
	public String getTemplate(String filePath,int templateId) throws IOESException, ParseException 
	{
		//logger.info(" Entered into getErrorExcel method of " + this.getClass().getSimpleName());
				
		String fileName ;
		HSSFWorkbook wb;
		HSSFSheet ws;
		HSSFRow wr;
		HSSFCell wc;
		ArrayList filledTemplateData = new ArrayList() ;
		ErrorFileDaoImpl objDao = new ErrorFileDaoImpl();
		ViewOrderModel objviewmodel=new ViewOrderModel();
		//ErrorLogDto dtoObj ;
		TransactionTemplateDto dtoObj;
		ViewOrderDto dtoObj1;
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		int totalRowsOfSheet=0;
		ViewOrderDto objdto=null;
		String str=null;
		try
		{
			ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");

			fileName = filePath.substring((filePath.lastIndexOf("/") + 1),filePath.length());
			String newFile = bundle.getString("excel.filledTemplate")+"/"+fileName;
			FileOutputStream fileOut = new FileOutputStream(newFile);
			wb = new HSSFWorkbook(new FileInputStream(filePath));
		    HSSFCellStyle whiteFG_yellow = wb.createCellStyle();
		    
		    HSSFColor yellow =  setColor(wb,(byte) 0xFF, (byte)0xFF,(byte) 0x00);
		    //whiteFG_yellow.setFillBackgroundColor(HSSFColor.YELLOW.index);
		    whiteFG_yellow.setFillBackgroundColor(yellow.getIndex());
		    
		    HSSFCellStyle whiteFG_green = wb.createCellStyle();
		    HSSFColor green =  setColor(wb,(byte) 0x00, (byte)0xFF,(byte) 0x00);
		    //whiteFG_green.setFillBackgroundColor(HSSFColor.GREEN.index);
		    whiteFG_green.setFillBackgroundColor(green.getIndex());
				
			//System.out.println(filledTemplateData.toString());
			//System.out.println(wb.getNumberOfSheets());
			
			
			for(int s = 0; s < wb.getNumberOfSheets(); s++)
			{				
				ws = wb.getSheetAt(s);	
				if(s==0 && (templateId==1))
				{
					int  ctr=0,totalRowsOfSheetMain=0,totalRowsOfSheetAtt=0;
					//filledTemplateData = objDao.getFilledTemplate(s+1,templateId,flag);
					for(int r = 2; r <= (totalRowsOfSheetMain+1); r++)
					{
						dtoObj = (TransactionTemplateDto)filledTemplateData.get(ctr);
						wr=ws.createRow(r);
						if(wr!=null)
						{
							wr.createCell(1).setCellValue(dtoObj.getOrderNo());
							wr.createCell(2).setCellValue(new HSSFRichTextString((dtoObj.getAccountID())));
							wr.createCell(3).setCellValue(new HSSFRichTextString((dtoObj.getSource())));
							wr.createCell(4).setCellValue(new HSSFRichTextString((dtoObj.getCurrencyID())));
							wr.createCell(5).setCellValue(new HSSFRichTextString((dtoObj.getOpportunityId())));
							wr.createCell(6).setCellValue(new HSSFRichTextString((dtoObj.getQuoteNo())));
							wr.createCell(7).setCellValue(new HSSFRichTextString((dtoObj.getProjectMangerID())));
							wr.createCell(8).setCellValue(new HSSFRichTextString((dtoObj.getZoneId())));
						}
						ctr++;
					}
				}
				wb.write(fileOut);
				fileOut.close();
				filePath = newFile;
			}		
		}		
		catch(IOException ioExp)
		{
			//logger.error(ioExp.getMessage() + " Exception occured in getErrorExcel method of " + this.getClass().getSimpleName());
			//throw new IOESException("SQL Exception : "+ ioExp.getMessage(), ioExp);
			Utility.LOG(true, false, ioExp, "::BULKUPLOAD_ERROR::Exception occured in getTemplate method of " + this.getClass().getSimpleName());
			return filePath="NOTFOUND";
		}
		return filePath;
	}
	//added by maisha end
	
	
	public String getResultErrorMixLog(String filePath, int fileID) throws IOESException 
	{
		//logger.info(" Entered into getErrorExcel method of " + this.getClass().getSimpleName());
		
		int colCount , ctr=0;
		String fileName ;
		HSSFWorkbook wb;
		HSSFSheet ws;
		HSSFRow wr;
		HSSFCell wc;
		ArrayList errVal = new ArrayList() ;
		ErrorFileDaoImpl objDao = new ErrorFileDaoImpl();
		ErrorLogDto dtoObj ;
		
		try
		{
			ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");

			fileName = filePath.substring((filePath.lastIndexOf("/") + 1),filePath.length());
			String newFile = bundle.getString("excel.success")+"/"+fileName;
			FileOutputStream fileOut = new FileOutputStream(newFile);
			wb = new HSSFWorkbook(new FileInputStream(filePath));
			
			errVal = objDao.getResultErrorMixLog(fileID);
			System.out.println(errVal.toString());
			System.out.println(wb.getNumberOfSheets());
			
			HSSFCellStyle styleErr = wb.createCellStyle();
			HSSFCellStyle styleSuccess = wb.createCellStyle();
			HSSFFont fontSuccess=wb.createFont();
			HSSFFont fontErr=wb.createFont();
			
			for(int s = 0; s < wb.getNumberOfSheets(); s++)
			{
				
				ws = wb.getSheetAt(s);
				wr = ws.getRow(1);
				colCount = wr.getLastCellNum();
				wc = wr.createCell(colCount);
				wc.setCellValue("RESULT LOG"+"_"+"ORDERNO");
				
				for(int r = 2; r <= ws.getLastRowNum(); r++)
				{
					if((ctr<errVal.size())){
					dtoObj = (ErrorLogDto)errVal.get(ctr);
					wr = ws.getRow(r);
					if(wr!=null)
					{
						int chk =0;
						for(int col=0;col<colCount;col++)
						{
							wc = wr.getCell(col);
							if(wc!=null)
							{
								if(!(wc.toString().trim().equals("")))
								{
									chk = 1;
								}
							}
						}
						if(chk==1)
						{
							wc = wr.createCell(colCount);
							if((dtoObj.getResultLogValue()==null||dtoObj.getResultLogValue().length()==0) && dtoObj.getErrorLogValue()!=null)
							{
								fontErr.setColor(HSSFColor.RED.index);
								styleErr.setFont(fontErr);
								wc.setCellStyle(styleErr);
								wc.setCellValue(dtoObj.getErrorLogValue().toString());
							}
							else
							{				
								fontSuccess.setColor(HSSFColor.BLACK.index);
								styleSuccess.setFont(fontSuccess);
								wc.setCellStyle(styleSuccess);
								wc.setCellValue(dtoObj.getResultLogValue().toString());
							}
								
							ctr++;
						}
					}
					}
				}
			}
			wb.write(fileOut);
			fileOut.close();
			filePath = newFile;
		}
		catch (IOESException ex)
		{
			logger.error(ex.getMessage() + " Exception occured in getResultExcel method of " + this.getClass().getSimpleName());
			throw new IOESException("SQL Exception : "+ ex.getMessage(), ex);
		}
		catch(IOException ioExp)
		{
			//logger.error(ioExp.getMessage() + " Exception occured in getErrorExcel method of " + this.getClass().getSimpleName());
			//throw new IOESException("SQL Exception : "+ ioExp.getMessage(), ioExp);
			return filePath="NOTFOUND";
		}
		return filePath;
	}
}
