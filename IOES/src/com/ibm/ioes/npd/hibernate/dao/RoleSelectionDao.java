package com.ibm.ioes.npd.hibernate.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ibm.appsecure.exception.EncryptionException;
import com.ibm.appsecure.util.Encryption;
import com.ibm.appsecure.util.IEncryption;
import com.ibm.ioes.npd.hibernate.beans.RoleSelectionDto;
import com.ibm.ioes.npd.hibernate.beans.TmEmployee;
import com.ibm.ioes.npd.hibernate.beans.TmRoles;
import com.ibm.ioes.npd.utilities.NpdConnection;
import com.ibm.ioes.npd.utilities.PagingSorting;
import com.ibm.ioes.npd.utilities.Utility;
import com.ibm.ioes.utilities.DbConnection;

public class RoleSelectionDao 
{
	public ArrayList<RoleSelectionDto> getRoleList(String userID) 
	{
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rsRoleList = null;
		ArrayList<RoleSelectionDto> listRoleList = new ArrayList<RoleSelectionDto>();
		RoleSelectionDto RoleSelectionDto = null;
		IEncryption objEncrypt = new Encryption();
		try
		{
			connection=NpdConnection.getConnectionObject();
			String getRoleList = null;
			getRoleList= "Select * from NPD.VW_ROLESELECTION";
			
			String whereCondition="";
			String condition;
			
			int UID=Integer.parseInt(userID);
			if(UID!=0)
			{
				condition=Utility.generateIntCondition(UID, "NPDEMPID");
				whereCondition=Utility.addToCondition(whereCondition,condition);
			}
			
			if(!(whereCondition.trim().equals("")))
			{
				getRoleList=getRoleList+" WHERE "+whereCondition;
			}
			
			String FullOrderBy=" ORDER BY ROLENAME ASC";
			getRoleList=getRoleList+FullOrderBy;
						  
			proc=connection.prepareCall(getRoleList);
			rsRoleList = proc.executeQuery();
			  while(rsRoleList.next())
			  {
				  RoleSelectionDto =  new RoleSelectionDto();
				  RoleSelectionDto.setRoleName(rsRoleList.getString("ROLENAME"));
				  RoleSelectionDto.setRoleID(rsRoleList.getInt("ROLEID"));
				  listRoleList.add(RoleSelectionDto);
			  }
			  //return listRoleList;
		}
		catch(EncryptionException ex1)
		{
			ex1.printStackTrace();
		}

		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		finally
		{
			try 
			{
				DbConnection.closeResultset(rsRoleList);
				DbConnection.closeCallableStatement(proc);
				DbConnection.freeConnection(connection);
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return listRoleList;
	}
	
	public ArrayList<RoleSelectionDto> getMenuList(RoleSelectionDto roleSelectionDto, String roleID) 
	{
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rsRoleMenu = null;
		ArrayList<RoleSelectionDto> listMenuList = new ArrayList<RoleSelectionDto>();
		RoleSelectionDto MenuDtos = null;
		
		try
		{
			connection=NpdConnection.getConnectionObject();
			String getMenuList = null;
			getMenuList= "Select * from NPD.VW_ACCESSMATRIX";
			
			String whereCondition="";
			String condition;
			
			int moduleActive=1;
			condition=Utility.generateIntCondition(moduleActive, "MODULEACTIVE");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			int interfaceActive=1;
			condition=Utility.generateIntCondition(interfaceActive, "INTERFACEACTIVE");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			int AccessFlag=1;
			condition=Utility.generateIntCondition(AccessFlag, "ACCESSFLAG");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			int roleIDForMenu=Integer.parseInt(roleID);
			
			condition=Utility.generateIntCondition(roleIDForMenu, "ROLEID");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			if(!(whereCondition.trim().equals("")))
			{
				getMenuList=getMenuList+" WHERE "+whereCondition;
			}
				
			String orderByColumn="INTERFACENAME";
			String ASC_DESC="ASC";
			PagingSorting pagingSorting =roleSelectionDto.getPagingSorting();
			String sortBy=pagingSorting.getSortByColumn();
			
			if(orderByColumn!=null && !(orderByColumn.trim().equals("")))
			{
				String FullOrderBy=" ORDER BY "+orderByColumn+" "+ASC_DESC+" ";
				getMenuList=getMenuList+FullOrderBy;
			}

			proc=connection.prepareCall(getMenuList);
			rsRoleMenu = proc.executeQuery();
			  while(rsRoleMenu.next())
			  {
				  MenuDtos =  new RoleSelectionDto();
				  MenuDtos.setModules(rsRoleMenu.getString("MODULENAME"));
				  MenuDtos.setInterfaceName(rsRoleMenu.getString("INTERFACENAME"));
				  MenuDtos.setUrl(rsRoleMenu.getString("URL"));
				  listMenuList.add(MenuDtos);
			  }
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		finally
		{
			try 
			{
				DbConnection.closeResultset(rsRoleMenu);
				DbConnection.closeCallableStatement(proc);
				DbConnection.freeConnection(connection);
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return listMenuList;
	}
	public ArrayList<TmEmployee> getEmployeeList(String employeeIds,Connection connection) {
		
		CallableStatement proc = null;
		ResultSet resultSet = null;
		ArrayList<TmEmployee> employeeList= new ArrayList<TmEmployee>();;
		try {
			

			StringBuffer query = new StringBuffer();

			query.append(" SELECT * FROM NPD.TM_EMPLOYEE");
			query.append(" WHERE NPDEMPID IN ( " + employeeIds+" )");

			//System.out.println("QUERY-->" + query.toString());
			
			proc = connection.prepareCall(query.toString());
			// proc.setLong(1,objProjectPlan.getTaskid());
			resultSet = proc.executeQuery();
			TmEmployee dto;
			while (resultSet.next()) {
				dto=new TmEmployee();
				dto.setEmpname(resultSet.getString("EMPNAME"));
				dto.setNpdempid(resultSet.getLong("NPDEMPID"));
				dto.setEmail(resultSet.getString("EMAIL"));
				
				employeeList.add(dto);
			}

			// return fileByte;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		finally
		{
			try
			{
			DbConnection.closeResultset(resultSet);
			DbConnection.closeCallableStatement(proc);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return employeeList;
	}

//	-- Manish 
	public  ArrayList<String> getRoleNames(String roleId,Connection connection) {
		
		CallableStatement proc = null;
		ResultSet resultSet = null;
		ArrayList<String> roleNames = new ArrayList<String>();;
		try {
		

			StringBuffer query = new StringBuffer();

			query.append(" SELECT ROLENAME FROM NPD.TM_ROLES");
			query.append(" WHERE ROLEID  IN ( " + roleId+" )");

			//System.out.println("QUERY-->" + query.toString());
			

			proc = connection.prepareCall(query.toString());
			// proc.setLong(1,objProjectPlan.getTaskid());
			resultSet = proc.executeQuery();
			while (resultSet.next()) {
				roleNames.add(resultSet.getString("ROLENAME"));
			}

			// return fileByte;
		} catch (Exception ex) {
			ex.printStackTrace();
		} 
		finally
		{
			try
			{
			DbConnection.closeResultset(resultSet);
			DbConnection.closeCallableStatement(proc);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return roleNames;
	}
	
	public  ArrayList<TmRoles> getRoles(String roleId,Connection connection) {
		
		CallableStatement proc = null;
		ResultSet resultSet = null;
		ArrayList<TmRoles> roles = new ArrayList<TmRoles>();
		try {
		

			StringBuffer query = new StringBuffer();

			query.append(" SELECT ROLEID,ROLENAME FROM NPD.TM_ROLES");
			query.append(" WHERE ROLEID  IN ( " + roleId+" )");

		//	System.out.println("QUERY-->" + query.toString());
			

			proc = connection.prepareCall(query.toString());
			// proc.setLong(1,objProjectPlan.getTaskid());
			resultSet = proc.executeQuery();
			TmRoles dto=null;
			while (resultSet.next()) {
				dto=new TmRoles();
				dto.setRoleid(resultSet.getInt("ROLEID"));
				dto.setRolename(resultSet.getString("ROLENAME"));
				roles.add(dto);
			}

			// return fileByte;
		} catch (Exception ex) {
			ex.printStackTrace();
		} 
		finally
		{
			try
			{
			DbConnection.closeResultset(resultSet);
			DbConnection.closeCallableStatement(proc);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return roles;
	}

	public ArrayList<RoleSelectionDto> getAdminReports(RoleSelectionDto roleSelectionDto, String roleID) 
	{
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rsRoleMenu = null;
		ArrayList<RoleSelectionDto> listAdminMenuList = new ArrayList<RoleSelectionDto>();
		RoleSelectionDto MenuDtos = null;
		
		try
		{
			connection=NpdConnection.getConnectionObject();
			String getMenuList = null;
			getMenuList= "Select * from NPD.VW_ACCESSMATRIX";
			
			String whereCondition="";
			String condition;
			
			int moduleActive=1;
			condition=Utility.generateIntCondition(moduleActive, "MODULEACTIVE");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			int interfaceActive=1;
			condition=Utility.generateIntCondition(interfaceActive, "INTERFACEACTIVE");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			int AccessFlag=1;
			condition=Utility.generateIntCondition(AccessFlag, "ACCESSFLAG");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			int roleIDForMenu=Integer.parseInt(roleID);
			condition=Utility.generateIntCondition(roleIDForMenu, "ROLEID");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			int moduleID=4;
			condition=Utility.generateIntCondition(moduleID, "MODULEID");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			
			if(!(whereCondition.trim().equals("")))
			{
				getMenuList=getMenuList+" WHERE "+whereCondition;
			}
				
			String orderByColumn="INTERFACENAME";
			String ASC_DESC="ASC";
			PagingSorting pagingSorting =roleSelectionDto.getPagingSorting();
			String sortBy=pagingSorting.getSortByColumn();
			
			if(orderByColumn!=null && !(orderByColumn.trim().equals("")))
			{
				String FullOrderBy=" ORDER BY "+orderByColumn+" "+ASC_DESC+" ";
				getMenuList=getMenuList+FullOrderBy;
			}

			proc=connection.prepareCall(getMenuList);
			rsRoleMenu = proc.executeQuery();
			  while(rsRoleMenu.next())
			  {
				  MenuDtos =  new RoleSelectionDto();
				  MenuDtos.setModules(rsRoleMenu.getString("MODULENAME"));
				  MenuDtos.setInterfaceName(rsRoleMenu.getString("INTERFACENAME"));
				  MenuDtos.setUrl(rsRoleMenu.getString("URL"));
				  listAdminMenuList.add(MenuDtos);
			  }
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		finally
		{
			try 
			{
				DbConnection.closeResultset(rsRoleMenu);
				DbConnection.closeCallableStatement(proc);
				DbConnection.freeConnection(connection);
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return listAdminMenuList;
	}

}
