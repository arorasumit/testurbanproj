package com.ibm.ioes.npd.model;

import java.util.ArrayList;
import org.hibernate.Session;
import com.ibm.ioes.npd.hibernate.dao.BaseDaoFactory;
import com.ibm.ioes.npd.hibernate.dao.CommonBaseDao;
import com.ibm.ioes.npd.hibernate.dao.DaoConstants;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.hibernate.beans.NPDResourceListDto;
import com.ibm.ioes.npd.hibernate.dao.NPDResourceListDao;

public class NPDResourceListModel extends CommonBaseModel
{
	public ArrayList<NPDResourceListDto> viewNPDResourceReport(NPDResourceListDto npdResourceListDto) throws Exception  
	{
		ArrayList<NPDResourceListDto> listNPDResource = new ArrayList<NPDResourceListDto>();
		NPDResourceListDao objDao = new NPDResourceListDao();
		try
		{
			listNPDResource = objDao.getNPDResourceReport(npdResourceListDto);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listNPDResource;
	}
	
	public ArrayList<NPDResourceListDto> viewNPDResourceReportExport(NPDResourceListDto npdResourceListDto) throws Exception  
	{
		ArrayList<NPDResourceListDto> listNPDResourceExport = new ArrayList<NPDResourceListDto>();
		NPDResourceListDao objDao = new NPDResourceListDao();
		try
		{
			listNPDResourceExport = objDao.getNPDResourceReportExport(npdResourceListDto);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listNPDResourceExport;
	}
}
