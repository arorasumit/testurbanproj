package com.ibm.ioes.model;

import java.sql.SQLException;
import java.util.ArrayList;

import com.ibm.ioes.dao.RoleAcessMatrixDao;
import com.ibm.ioes.forms.RoleAcessMatrixDto;

public class RoleAcessMatrixModel {
	/**
	 * retrieves the list of all roles of system. 
	 * 
	 * @return list of all roles.
	 */
	public ArrayList<RoleAcessMatrixDto> getRoleList() throws Exception  
	{
		ArrayList<RoleAcessMatrixDto> listRole = new ArrayList<RoleAcessMatrixDto>();
		RoleAcessMatrixDao objDao = new RoleAcessMatrixDao();
		try
		{
			listRole = objDao.getRoleList();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listRole;
	}
	/**
	 * retreives the details of interfaces 
	 * @param roleID 
	 * @param moduleID
	 * @return list of dto having interface details
	 */
	public ArrayList<RoleAcessMatrixDto> getInterfaceList(String roleID,String moduleID) throws Exception  
	{
		ArrayList<RoleAcessMatrixDto> listIntefrace = new ArrayList<RoleAcessMatrixDto>();
		RoleAcessMatrixDao objDao = new RoleAcessMatrixDao();
		try
		{
			listIntefrace = objDao.getInterfaceList(roleID,moduleID);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listIntefrace;
	}
	/**
	 * update the accessFlag of interfaces as updated by user.
	 * @param allowedInterfaceId
	 * @param prohibitedInterfaceId
	 * @param roleId
	 * @return status
	 * @throws SQLException
	 */
	public int setUserMappedInterfaces(ArrayList allowedInterfaceId, ArrayList prohibitedInterfaceId,String roleID) throws Exception  
	{
		int listIntefrace = 0;
		RoleAcessMatrixDao objDao = new RoleAcessMatrixDao();
		try
		{
			listIntefrace = objDao.setUserMappedInterfaces(allowedInterfaceId,prohibitedInterfaceId,roleID);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listIntefrace;
	}
	/**
	 * retrieves the list of all modules of system. 
	 * 
	 * @return list of all modules.
	 */
	public ArrayList<RoleAcessMatrixDto> getModuleList() throws Exception  
	{
		ArrayList<RoleAcessMatrixDto> listModule = new ArrayList<RoleAcessMatrixDto>();
		RoleAcessMatrixDao objDao = new RoleAcessMatrixDao();
		try
		{
			listModule = objDao.getModuleList();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listModule;
	}
	
}
