package com.ibm.ioes.npd.model;

import java.util.ArrayList;

import com.ibm.ioes.npd.hibernate.beans.MenuDto;
import com.ibm.ioes.npd.hibernate.beans.RoleSelectionDto;
import com.ibm.ioes.npd.hibernate.dao.RoleSelectionDao;

public class RoleSelectionModel 
{
	public ArrayList<RoleSelectionDto> viewRoleList(String userID) throws Exception  
	{
		ArrayList<RoleSelectionDto> listRole = new ArrayList<RoleSelectionDto>();
		RoleSelectionDao objDao = new RoleSelectionDao();
		try
		{
			listRole = objDao.getRoleList(userID);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listRole;
	}
	
	public ArrayList<RoleSelectionDto> getMenuDetails(RoleSelectionDto objDto,String roleID) throws Exception
	{
		ArrayList<RoleSelectionDto> listMenu = new ArrayList<RoleSelectionDto>();
		RoleSelectionDao objDao = new RoleSelectionDao();
		try
		{
			listMenu = objDao.getMenuList(objDto,roleID);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listMenu;
	}
	
	public ArrayList<RoleSelectionDto> getAdminMenuDetails(RoleSelectionDto objDto,String roleID) throws Exception
	{
		ArrayList<RoleSelectionDto> listMenu = new ArrayList<RoleSelectionDto>();
		RoleSelectionDao objDao = new RoleSelectionDao();
		try
		{
			listMenu = objDao.getAdminReports(objDto,roleID);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listMenu;
	}
}
