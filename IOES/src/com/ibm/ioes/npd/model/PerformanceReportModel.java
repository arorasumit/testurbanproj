package com.ibm.ioes.npd.model;

import java.util.ArrayList;

import com.ibm.ioes.npd.hibernate.beans.PerformanceReportDto;
import com.ibm.ioes.npd.hibernate.dao.PerformanceReportDao;

public class PerformanceReportModel extends CommonBaseModel
{
	public ArrayList<PerformanceReportDto> viewPerformanceReport(PerformanceReportDto performanceReportDto) throws Exception  
	{
		ArrayList<PerformanceReportDto> listPerformanceReport = new ArrayList<PerformanceReportDto>();
		PerformanceReportDao objDao = new PerformanceReportDao();
		try
		{
			listPerformanceReport = objDao.getPerformanceReport(performanceReportDto);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listPerformanceReport;
	}
	
	public ArrayList<PerformanceReportDto> viewPerformanceReportExport(PerformanceReportDto performanceReportDto) throws Exception  
	{
		ArrayList<PerformanceReportDto> listPerformanceReport = new ArrayList<PerformanceReportDto>();
		PerformanceReportDao objDao = new PerformanceReportDao();
		try
		{
			listPerformanceReport = objDao.getPerformanceReportExport(performanceReportDto);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listPerformanceReport;
	}
	
	
}
