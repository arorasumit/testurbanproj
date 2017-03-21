package com.ibm.ioes.npd.model;

import java.util.ArrayList;

import com.ibm.ioes.npd.hibernate.beans.ProjectPlanReportDto;
import com.ibm.ioes.npd.hibernate.beans.ProjectStatusReportDto;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjectassumptions;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjectisssues;
import com.ibm.ioes.npd.hibernate.dao.ProjectPlanReportDao;
import com.ibm.ioes.npd.hibernate.dao.ProjectStatusReportDao;


public class ProjectStatusModel {

	public ArrayList<ProjectStatusReportDto> fetchProjectStatusDetail(ProjectStatusReportDto objProjectStatus)
	{
		ArrayList<ProjectStatusReportDto> listProjectStatus = null;
		ProjectStatusReportDao objDao = new ProjectStatusReportDao();
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
	public ArrayList<TtrnProjectisssues> exportIssueToExcel(ProjectStatusReportDto objProjectStatus)
	{
		ArrayList<TtrnProjectisssues> listProjectStatus = null;
		ProjectStatusReportDao objDao = new ProjectStatusReportDao();
		try
		{
			listProjectStatus =  objDao.fetchProjectIssue(objProjectStatus);
		}
		catch(Exception ex )
		{
			ex.printStackTrace();	
		}
		return listProjectStatus;
	}

	public ArrayList<TtrnProjectisssues> exportIssueToExcelForStatus(ProjectStatusReportDto objProjectStatus)
	{
		ArrayList<TtrnProjectisssues> listProjectStatus = null;
		ProjectStatusReportDao objDao = new ProjectStatusReportDao();
		try
		{
			listProjectStatus =  objDao.fetchProjectIssueExport(objProjectStatus);
		}
		catch(Exception ex )
		{
			ex.printStackTrace();	
		}
		return listProjectStatus;
	}

	
	public ArrayList<TtrnProjectassumptions> exportAssumptionToExcel(ProjectStatusReportDto objProjectStatus)
	{
		ArrayList<TtrnProjectassumptions> listProjectStatus = null;
		ProjectStatusReportDao objDao = new ProjectStatusReportDao();
		try
		{
			listProjectStatus =  objDao.fetchProjectAssumption(objProjectStatus);
		}
		catch(Exception ex )
		{
			ex.printStackTrace();	
		}
		return listProjectStatus;
	}
	
}
