package com.ibm.ioes.npd.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import org.hibernate.Session;


import com.ibm.ioes.npd.hibernate.beans.TtrnProjectassumptions;
import com.ibm.ioes.npd.hibernate.dao.AssumptionCaptureDao;
import com.ibm.ioes.npd.hibernate.dao.BaseDaoFactory;
import com.ibm.ioes.npd.hibernate.dao.CommonBaseDao;
import com.ibm.ioes.npd.hibernate.dao.DaoConstants;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.hibernate.beans.FunnelReportDto;
import com.ibm.ioes.npd.hibernate.dao.FunnelReportDao;

public class FunnelReportModel extends CommonBaseModel
{
	public HashMap viewFunnelReport(FunnelReportDto funnelReportDto) throws Exception  
	{
		ArrayList<FunnelReportDto> listFunnelReport = new ArrayList<FunnelReportDto>();
		FunnelReportDao objDao = new FunnelReportDao();
		
		HashMap htValue = new HashMap();
		try
		{
			htValue = objDao.getFunnelReport(funnelReportDto);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return htValue;
	}
	
	public HashMap viewFunnelReportExport(FunnelReportDto funnelReportDto) throws Exception  
	{
		ArrayList<FunnelReportDto> listFunnelReport = new ArrayList<FunnelReportDto>();
		FunnelReportDao objDao = new FunnelReportDao();
		HashMap htValue = new HashMap();
		try
		{
			htValue = objDao.getFunnelReportExport(funnelReportDto);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return htValue;
	}
}
