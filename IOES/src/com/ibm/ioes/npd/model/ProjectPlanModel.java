package com.ibm.ioes.npd.model;

import java.util.ArrayList;

import com.ibm.ioes.npd.hibernate.beans.ProjectPlanReportDto;
import com.ibm.ioes.npd.hibernate.beans.ProjectStatusReportDto;
import com.ibm.ioes.npd.hibernate.dao.ProjectPlanReportDao;


public class ProjectPlanModel {

	public ArrayList<ProjectPlanReportDto> fetchProjectPlanDetail(ProjectPlanReportDto objProjectPlan)
	{
		ArrayList<ProjectPlanReportDto> listProjectPlan = null;
		ProjectPlanReportDao objDao = new ProjectPlanReportDao();
		try
		{
			listProjectPlan =  objDao.fetchProjectPlanDetail(objProjectPlan);
		}
		catch(Exception ex )
		{
			ex.printStackTrace();	
		}
		return listProjectPlan;
	}
	
	public ArrayList<ProjectPlanReportDto> fetchProjectDocReport(ProjectPlanReportDto objProjectPlan)
	{
		ArrayList<ProjectPlanReportDto> listProjectPlan = null;
		ProjectPlanReportDao objDao = new ProjectPlanReportDao();
		try
		{
			listProjectPlan =  objDao.fetchProjectDocReport(objProjectPlan);
		}
		catch(Exception ex )
		{
			ex.printStackTrace();	
		}
		return listProjectPlan;
	}

	public ArrayList<ProjectPlanReportDto> fetchRfiAction(ProjectPlanReportDto objProjectPlan)
	{
		ArrayList<ProjectPlanReportDto> listProjectrfiaction = null;
		ProjectPlanReportDao objDao = new ProjectPlanReportDao();
		try
		{
			listProjectrfiaction =  objDao.fetchRfiActionDetails(objProjectPlan);
		}
		catch(Exception ex )
		{
			ex.printStackTrace();	
		}
		return listProjectrfiaction;
	}

	public ArrayList<ProjectPlanReportDto> ExportProjectPlanDetail(ProjectPlanReportDto objProjectPlan)
	{
		ArrayList<ProjectPlanReportDto> listProjectPlan = null;
		ProjectPlanReportDao objDao = new ProjectPlanReportDao();
		try
		{
			listProjectPlan =  objDao.ExportProjectPlanDetail(objProjectPlan);
		}
		catch(Exception ex )
		{
			ex.printStackTrace();	
		}
		return listProjectPlan;
	}

	public byte[] DownloadFile(ProjectPlanReportDto objProjectPlan)
	{
		byte[] fileBytes = null;
		ProjectPlanReportDao objDao = new ProjectPlanReportDao();
		try
		{
			fileBytes =  objDao.DownloadFile(objProjectPlan);
		}
		catch(Exception ex )
		{
			ex.printStackTrace();	
		}
		return fileBytes;
	}

	public byte[] DownloadFileRFI(ProjectPlanReportDto objProjectPlan)
	{
		byte[] fileBytes = null;
		ProjectPlanReportDao objDao = new ProjectPlanReportDao();
		try
		{
			fileBytes =  objDao.DownloadFileRFI(objProjectPlan);
		}
		catch(Exception ex )
		{
			ex.printStackTrace();	
		}
		return fileBytes;
	}
	
	public byte[] DownloadProjectDocFile(ProjectPlanReportDto objProjectPlan)
	{
		byte[] fileBytes = null;
		ProjectPlanReportDao objDao = new ProjectPlanReportDao();
		try
		{
			fileBytes =  objDao.DownloadProjectDocFile(objProjectPlan);
		}
		catch(Exception ex )
		{
			ex.printStackTrace();	
		}
		return fileBytes;
	}
	public ArrayList<ProjectPlanReportDto> viewChart(ProjectPlanReportDto objProjectPlan,String str)
	{
		ArrayList<ProjectPlanReportDto> listProjectPlan = null;
		ProjectPlanReportDao objDao = new ProjectPlanReportDao();
		try
		{
			listProjectPlan = objDao.viewChart(objProjectPlan,str);
		}
		catch(Exception ex )
		{
			ex.printStackTrace();	
		}
		return listProjectPlan;
	}
	
	public ArrayList<ProjectPlanReportDto> viewStageGanttChart(ProjectPlanReportDto objProjectPlan,String str)
	{
		ArrayList<ProjectPlanReportDto> listProjectPlan = null;
		ProjectPlanReportDao objDao = new ProjectPlanReportDao();
		try
		{
			listProjectPlan = objDao.viewStageGanttChart(objProjectPlan,str);
		}
		catch(Exception ex )
		{
			ex.printStackTrace();	
		}
		return listProjectPlan;
	}
	
}
