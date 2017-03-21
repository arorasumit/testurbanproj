package com.ibm.ioes.utilities;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.ibm.ioes.dao.NewOrderDao;
import com.ibm.ioes.forms.PrdCatelogTemplateExcelDto;


public class ExcelCreator {
	
	 HSSFPatriarch patr = null;
	 

	public void makePrdCatlogTemplateSheet(Connection connection, String[] chk_spId, HSSFSheet sheet, HSSFCellStyle headerCellStyle, String worksheetId, String serviceDetailId,int indexOfSheet) throws Exception{
		
		
         //set sheet header with comments from template
		 createHeader(sheet,indexOfSheet, headerCellStyle);
		 
		 NewOrderDao DaoObj=new NewOrderDao();
		 ResultSet rs=null;
		 if(AppConstants.prdCatelog_Billing_Information_Address.equals(worksheetId))
		 {
			 rs= DaoObj.fetchBillingInfoDetailsForExcel(connection, chk_spId);
		 }
		 if(AppConstants.prdCatelog_Hardware_Info.equals(worksheetId))
		 {
			 rs= DaoObj.fetchHardwareInfoDetailsForExcel(connection, chk_spId);
		 }
		 if(AppConstants.prdCatelog_ServiceLocation_Details.equals(worksheetId))
		 {
			 rs= DaoObj.fetchServiceLocationInfoDetailsForExcel(connection, chk_spId);
		 }
		 if(AppConstants.prdCatelog_Charges_Details.equals(worksheetId))
		 {
			 rs= DaoObj.fetchChargesInfoDetailsForExcel(connection, chk_spId);
		 }
		 if("MetaData_Sheet".equals(worksheetId))
		 {
			 rs= DaoObj.getMetaDataInformation(connection, serviceDetailId);
		 }
		 
		 int index=1;
		 int columnCount=sheet.getRow(0).getLastCellNum();
		 while(rs.next())
		 {
			    HSSFRow excelRow = null;
	            HSSFCell excelCell = null;
	            excelRow = sheet.createRow(index);
	            
	            for(int i=0;i<columnCount;i++)
	            {
	            	excelCell = excelRow.createCell(i);
		            excelCell.setCellValue(new HSSFRichTextString(rs.getString(i+1)));
	            }
	            index++;  
		 }
	}
	public void makePrdCatlogTemplateServiceSummarySheet(Connection connection, String[] chk_spId, HSSFSheet sheet, HSSFCellStyle headerCellStyle, String worksheetId, String serviceDetailId,int indexOfSheet) throws Exception{
		
		 
		        NewOrderDao DaoObj=new NewOrderDao();
		        ResultSet rs=null;
		        ResultSet rsServiceSummaryValues=null;
			    rs= DaoObj.fetchServiceSummaryHeaderForExcel(connection, serviceDetailId);
			 	HSSFRow row = sheet.createRow(0);
		        HSSFClientAnchor anchor=new HSSFClientAnchor(0, 0, 0, 0, (short)4, 2, (short)6, 5);
		        HSSFCell cell = null;
		        
		        //
		        ExcelCreator creator=createHeader(sheet, 0, headerCellStyle);
		        HSSFPatriarch patr = creator.getPatr();
		        int sheetColIndex=sheet.getRow(0).getLastCellNum();
		        if(sheetColIndex==-1)
		        {
		        	sheetColIndex=0;
		        }
		        String commentString=null;
		        ArrayList<String> al=new ArrayList<String>();
		        while(rs.next())
		        {
		        	cell = row.createCell(sheetColIndex);
					cell.setCellStyle(headerCellStyle);
					 //setting cell value from template
					 cell.setCellValue(new HSSFRichTextString(rs.getString("ATTDESCRIPTION")));
					 commentString=rs.getString("EXCELCOMMENTS");
					 al.add(rs.getString("ATTMASTERID"));
			        //setting cell comment from template
			        if(commentString!=null)
				    {	
		    	     HSSFComment comment=patr.createComment(anchor);
		    	   	 comment.setString(new HSSFRichTextString(commentString));
		    	   	 cell.setCellComment(comment);
			        }
			        sheetColIndex++;
		        }
		        Map<String, Map<String, String>> map_ServicePrdData =DaoObj.fetchServiceSummaryValuesForExcel(connection, chk_spId);
		        
		        int index=1;
			    HSSFRow excelRow = null;
	            HSSFCell excelCell = null;
	            for(int i=0;i<chk_spId.length;i++)
	   		      {
	            	Map<String, String> data=map_ServicePrdData.get(chk_spId[i]);
	            	excelRow = sheet.createRow(index);
	            	excelCell = excelRow.createCell(0);
		            excelCell.setCellValue(new HSSFRichTextString(chk_spId[i]));
		            int columnCount=1;
	            	for(int j=0;j < al.size();j++)
	            	{
	            		excelCell = excelRow.createCell(columnCount);
			            excelCell.setCellValue(new HSSFRichTextString(data.get(al.get(j))));
			            columnCount++;
	            	}
	            	index++;
	            	
	   		      }
	}
	public ExcelCreator createHeader(HSSFSheet sheet,int index,HSSFCellStyle headerCellStyle) throws Exception
	{
		    HSSFRow row = sheet.createRow(0);
	        
	        FileInputStream templateStream;
	        HSSFRow templateRow = null;
	        templateStream = new FileInputStream(AppConstants.UploadPrdCatelogExcelTemplateFilePath);
	        HSSFWorkbook templateWorkbook = new HSSFWorkbook(templateStream);
	        HSSFSheet templateFileSheet=templateWorkbook.getSheetAt(index);
	        templateRow = templateFileSheet.getRow(0);
	        HSSFPatriarch patr = sheet.createDrawingPatriarch();
	        int count_cell=-1;
	        HSSFClientAnchor anchor=new HSSFClientAnchor(0, 0, 0, 0, (short)4, 2, (short)6, 5);
	        HSSFCell projectplancell = null;
	        for(int i=0;i<templateRow.getLastCellNum();i++)
	        {
	     	  projectplancell = row.createCell(++count_cell);
	 	      createTemplateCell(projectplancell,headerCellStyle,count_cell,templateFileSheet,sheet,patr,anchor);
	        }
	        ExcelCreator creator=new ExcelCreator();
	        creator.setPatr(patr);
	        return creator;
	}
	
	private void createTemplateCell(HSSFCell cell, HSSFCellStyle headerCellStyle, int count_cell, HSSFSheet templateFileSheet, HSSFSheet projectplansheet, HSSFPatriarch patr, HSSFClientAnchor anchor) {
			HSSFRow templateRow=templateFileSheet.getRow(0);
			cell.setCellStyle(headerCellStyle);
			//setting cell value from template
			cell.setCellValue(templateRow.getCell(count_cell).getRichStringCellValue());
	        //setting column width from template
	        projectplansheet.setColumnWidth(count_cell,templateFileSheet.getColumnWidth(count_cell));
	        //setting cell comment from template
	        if(templateRow.getCell(count_cell).getCellComment()!=null)
		    {	
	        
    	     HSSFComment comment=patr.createComment(anchor);
    	   	 comment.setString(templateRow.getCell(count_cell).getCellComment().getString());
    	     //comment.setString(new HSSFRichTextString("abc"));
    	   	 cell.setCellComment(comment);
	        }
	}
	public HSSFPatriarch getPatr() {
		return patr;
	}
	public void setPatr(HSSFPatriarch patr) {
		this.patr = patr;
	}

}
