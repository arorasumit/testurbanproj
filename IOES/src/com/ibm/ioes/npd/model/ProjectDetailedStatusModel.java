package com.ibm.ioes.npd.model;

import java.util.ArrayList;

import com.ibm.ioes.npd.hibernate.beans.ProjectDetailedStatusReportDto;
import com.ibm.ioes.npd.hibernate.beans.ProjectPlanReportDto;
import com.ibm.ioes.npd.hibernate.beans.ProjectStatusReportDto;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjectassumptions;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjectisssues;
import com.ibm.ioes.npd.hibernate.dao.ProjectDetailedStatusReportDao;
import com.ibm.ioes.npd.hibernate.dao.ProjectPlanReportDao;
import com.ibm.ioes.npd.hibernate.dao.ProjectStatusReportDao;


public class ProjectDetailedStatusModel {

	public ArrayList<ProjectDetailedStatusReportDto> fetchProjectStatusDetail(ProjectDetailedStatusReportDto objProjectStatus)
	{
		ArrayList<ProjectDetailedStatusReportDto> listProjectStatus = null;
		ProjectDetailedStatusReportDao objDao = new ProjectDetailedStatusReportDao();
		try
		{
			listProjectStatus =  objDao.fetchProjectStatusDetail(objProjectStatus);
		}
		catch(Exception ex )
		{
			ex.printStackTrace();	
		}
		return listProjectStatus;
	}
	
	public ArrayList<ProjectDetailedStatusReportDto> fetchProjectStatusDetailExcel(ProjectDetailedStatusReportDto objProjectStatus)
	{
		ArrayList<ProjectDetailedStatusReportDto> listProjectStatus = null;
		ProjectDetailedStatusReportDao objDao = new ProjectDetailedStatusReportDao();
		try
		{
			listProjectStatus =  objDao.fetchProjectStatusDetailExcel(objProjectStatus);
		}
		catch(Exception ex )
		{
			ex.printStackTrace();	
		}
		return listProjectStatus;
	}
	public void DownloadProjectDocFile(ProjectDetailedStatusReportDto objProjectStatus)
	{
		byte[] fileBytes = null;
		//ProjectPlanReportDao objDao1 = new ProjectPlanReportDao();
		ProjectDetailedStatusReportDao objDao = new ProjectDetailedStatusReportDao();
		try
		{
			objDao.DownloadProjectDocFile(objProjectStatus);
		}
		catch(Exception ex )
		{
			ex.printStackTrace();	
		}
		return;
	}
}
