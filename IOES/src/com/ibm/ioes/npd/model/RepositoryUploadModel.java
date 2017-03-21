package com.ibm.ioes.npd.model;

import java.util.ArrayList;

import org.apache.struts.upload.FormFile;

import com.ibm.ioes.npd.hibernate.beans.RepositoryUploadDto;
import com.ibm.ioes.npd.hibernate.dao.RepositoryUploadDao;

public class RepositoryUploadModel 
{
	public ArrayList<RepositoryUploadDto> getNPDList(RepositoryUploadDto objDto) throws Exception  
	{
		ArrayList<RepositoryUploadDto> listNPDCatagory = new ArrayList<RepositoryUploadDto>();
		RepositoryUploadDao objDao = new RepositoryUploadDao();
		try
		{
			listNPDCatagory = objDao.getNPDCategory(objDto);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listNPDCatagory;
	}
	
	public ArrayList<RepositoryUploadDto> getProjectList(RepositoryUploadDto objDto, String transactionID) throws Exception  
	{
		ArrayList<RepositoryUploadDto> listNPDCatagory = new ArrayList<RepositoryUploadDto>();
		RepositoryUploadDao objDao = new RepositoryUploadDao();
		try
		{
			listNPDCatagory = objDao.getProjectDetails(objDto, transactionID);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listNPDCatagory;
	}
	
	public ArrayList<RepositoryUploadDto> getStage(RepositoryUploadDto objDto, String projID) throws Exception  
	{
		ArrayList<RepositoryUploadDto> listStage = new ArrayList<RepositoryUploadDto>();
		RepositoryUploadDao objDao = new RepositoryUploadDao();
		try
		{
			listStage = objDao.getStageDetails(objDto, projID);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listStage;
	}
	
	public ArrayList<RepositoryUploadDto> getTask(RepositoryUploadDto objDto, String stageID,String projectId) throws Exception  
	{
		ArrayList<RepositoryUploadDto> listTask = new ArrayList<RepositoryUploadDto>();
		RepositoryUploadDao objDao = new RepositoryUploadDao();
		try
		{
			listTask = objDao.getTaskDetails(objDto, stageID,projectId);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listTask;
	}

	public boolean AddDocs(String DocName,FormFile Docattachment,int projID,int stageID,int taskID,int workFlowID) throws Exception
	{
		RepositoryUploadDao objDao = new RepositoryUploadDao();
		boolean insert=objDao.addDocuments(DocName,Docattachment,projID,stageID,taskID,workFlowID);
		return insert;
	}
	
	public ArrayList<RepositoryUploadDto> fetchProjectDocReport(String projID) throws Exception  
	{
		ArrayList<RepositoryUploadDto> docList = new ArrayList<RepositoryUploadDto>();
		RepositoryUploadDao objDao = new RepositoryUploadDao();
		try
		{
			docList = objDao.getDocDetails(projID);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return docList;
	}
}
