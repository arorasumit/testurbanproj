package com.ibm.ioes.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import IdlStubs.longSeqHelper;

import com.ibm.ioes.newdesign.dto.StandardReason;
import com.ibm.ioes.utilities.Utility;
import com.ibm.ioes.dao.NewOrderDao;
import com.ibm.ioes.forms.FileAttachmentDto;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Messages;
import java.sql.Connection;


public class MigrationUtility {
	private static final int BUFFER_SIZE = 1048576;
	NewOrderDao objdao = new NewOrderDao();
	
	public int countNoOfAttachmentsToMigrate() throws Exception
	{
		int noofRecords=0;
		//MigrationDto fileDto = new MigrationDto();
		String methodName="countNoOfAttachmentsToMigrate" , className= this.getClass().getName() ,msg="" ;
		boolean logToFile=true, logToConsole=true;
		ArrayList<FileAttachmentDto> getFileAttributes = new ArrayList<FileAttachmentDto>() ;
		try
		{
		getFileAttributes =objdao.getEligibleFilesForOneTimeMigration();
		noofRecords = getFileAttributes.size();
		Utility.SPT_LOG(logToFile, logToConsole, "no of records to migrate are----------->"+noofRecords);
		//fileDto.setNoOfRecords(noOfRecords);
		}
		
		catch(Exception ex)
		{
			//Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);
			Utility.onEx_LOG_RET_NEW_EX_SHAREPOINT(ex, methodName, className, msg, logToFile, logToConsole);
		}
		return noofRecords;
	}
	
	public String migrateExistingAttachments() throws Exception
	{
		String pathwithLotId="";
		String methodName="migrateExistingAttachments" , className= this.getClass().getName() ,msg="" ;
		boolean logToFile=true, logToConsole=true;
		String directoryName = null;
		//String successmessage= "success";
		//long fileDownloadLotId = Long.valueOf(String.valueOf(((Math.random() + 1.00) * 500.00)));
		//Double randomlotId = new Double((Math.random() + 1.00) * 500.00);
		long fileDownloadLotId = objdao.getNewFileDownloadLotId();
		//System.out.println("lot id is -------------->"+fileDownloadLotId);
		ArrayList<FileAttachmentDto> getFileAttributes = new ArrayList<FileAttachmentDto>() ;
		getFileAttributes =objdao.getEligibleFilesForOneTimeMigration();
		Utility.SPT_LOG(logToFile, logToConsole, "no of records to migrate are"+getFileAttributes.size());
		Utility.SPT_LOG(true, logToConsole, "lot id is :"+fileDownloadLotId);
		Utility.SPT_LOG(logToFile, logToConsole, methodName + "\t" + className + "\t" +"********************Migration of files started at ************************:" + new Date());
		
		for(int size=0;size<getFileAttributes.size();size++)
		{
			String orderno= getFileAttributes.get(size).getHdnOrderNo(); 
			int SlNo = getFileAttributes.get(size).getSlno(); 
			String FileName= getFileAttributes.get(size).getFileName();
			File f = new File(Messages.getMessageValue("MIGRATED_DIR")); 
			directoryName= f.getAbsolutePath();    
			try{
				 saveTofileSystem(SlNo,FileName,directoryName,fileDownloadLotId);
			}
			catch(Exception ex)
			{
				//Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);
				Utility.onEx_LOG_RET_NEW_EX_SHAREPOINT(ex, methodName, className, msg, logToFile, logToConsole);
			}
		}
		
		Utility.SPT_LOG(logToFile, logToConsole, methodName + "\t" + className + "\t" +"******************Migration of files ended at ***********************************:" + new Date());
	    pathwithLotId = "FILES SAVED AT PATH : "+directoryName+ "\nLOT ID :" + fileDownloadLotId;
		System.out.println(pathwithLotId);
		return pathwithLotId;
		
	}
	private void saveTofileSystem(int slNo,String fileName,String directoryName,long fileDownloadLotId) throws Exception {
		String methodName="saveTofileSystem" , classname= this.getClass().getName() ;
		java.sql.Blob fileblob=null;
		fileblob= objdao.getFileDataForMigration(slNo);
		double filesizeinbytesbeforesaving = fileblob.length();
		double fileSizeBeforeSaving = (filesizeinbytesbeforesaving / 1024);
		//Utility.LOG(true, true,  methodName+"\t"+classname+"\t"+"size of file before saving to file system is----->" + fileSizeBeforeSaving);
		Utility.SPT_LOG(true, true,  methodName+"\t"+classname+"\t"+slNo+"  size of file before saving to file system is----->" + fileSizeBeforeSaving);
		
		File file = new File(directoryName+"//"+slNo+"_"+fileName); 
		String csvPath = file.getAbsolutePath();
		if(!file.exists())
		{
			file.createNewFile();
		}
		InputStream inputStream = fileblob.getBinaryStream();
		OutputStream outputStream = new FileOutputStream(file);
		int bytesRead = -1;
		byte[] buffer = new byte[BUFFER_SIZE];
		while ((bytesRead = inputStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, bytesRead);
		}
		double filesizeinbytesaftersaving= file.length();
		double fileSizeAfterSaving = (filesizeinbytesaftersaving/1024);
		//double fileSizeAfterSaving = 223.33;
		System.out.println("size after saving"+fileSizeAfterSaving);
		//Utility.LOG(true, true,  methodName+"\t"+classname+"\t"+"size of file after saving to file system is" + fileSizeAfterSaving);
		Utility.SPT_LOG(true, true,  methodName+"\t"+classname+"\t"+slNo+"  size of file after saving to file system is----->" + fileSizeAfterSaving);
		inputStream.close();
		outputStream.close();
		updateStatusForFileUploaded(slNo,csvPath,fileSizeBeforeSaving,fileSizeAfterSaving,fileDownloadLotId);
	}


	private void updateStatusForFileUploaded(int slNo, String csvPath,double fileSizeBeforeSaving, double fileSizeAfterSaving , long fileDownloadLotId) throws Exception
	{
		String methodName="updateStatusForFileUploaded" , classname= this.getClass().getName() ;
		int compareResults = Double.compare(fileSizeBeforeSaving, fileSizeAfterSaving);
		System.out.println("size come"+compareResults);
		String error="ERR";
		if (compareResults==0)
		{
			//Utility.LOG(true, true,  methodName+"\t"+classname+"\t"+"sizes are equal-->" + compareResults);
			Utility.SPT_LOG(true, true,  methodName+"\t"+classname+"\t"+slNo+"\t"+"sizes are equal-->" + compareResults);
			
			objdao.updateStatusForFileUploaded(slNo,csvPath,fileDownloadLotId);
		}
		else
		{
			//Utility.LOG(true, true,  methodName+"\t"+classname+ "\t"+ "sizes are not equal-->" + compareResults);
			Utility.SPT_LOG(true, true,  methodName+"\t"+classname+ "\t"+ slNo+"\t"+"sizes are not equal-->" + compareResults);

			objdao.updateErrorStatusForUpload(slNo,csvPath,error,fileDownloadLotId);
		}
	}
	
	
	//nancy new method 

	public  void downloadAttachmentsForOrder(long orderno,long fileDownloadLotId ) throws Exception
	{
		String methodName="downloadAttachmentsForOrder" , classname= this.getClass().getName() ;
		ArrayList<FileAttachmentDto> getFileAttributes = new ArrayList<FileAttachmentDto>() ;
		try
		{
		getFileAttributes =objdao.getFileAttributesforOrder(orderno);
			Utility.SPT_LOG(true, true, "No of attachments to migrate for this order-->"+orderno+" are :"+ getFileAttributes.size());

		for( FileAttachmentDto fileDto : getFileAttributes)
			
		{
			int SlNo = fileDto.getSlno();
			String FileName= fileDto.getFileName();
				String directoryName=Utility.getAppConfigValue("DIRECTORY_FOR_ONE_TIME_MIGRATION");
			
			try{
				 saveTofileSystem(SlNo,FileName,directoryName,fileDownloadLotId);
			}
			catch(Exception ex)
			{
					//ex.printStackTrace();
					Utility.SPT_LOG(true,true,ex,"");
			}
			
		}

		}
		catch(Exception e)
		{
			Utility.SPT_LOG(true,true,e,"");
		}
		
	}
	
	
	
	
	
	
	
	public static void main(String args[]) throws Exception
	{

		//String message= null;
		System.out.println("main started");
		try{
			MigrationUtility mig = new MigrationUtility();
            System.out.println("started at ------------->"+ new Date());
			//mig.migrateExistingAttachments();
			//mig.downloadAttachmentsForOrder(3026699);
			System.out.println("ended at-------------->"+ new Date());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}