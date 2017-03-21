package com.ibm.ioes.npd.hibernate.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.ibm.ioes.npd.hibernate.beans.NPDResourceListDto;
import com.ibm.ioes.npd.utilities.NpdConnection;
import com.ibm.ioes.npd.utilities.PagingSorting;
import com.ibm.ioes.npd.utilities.Utility;
import com.ibm.ioes.utilities.DbConnection;

public class NPDResourceListDao extends CommonBaseDao
{
	public ArrayList<NPDResourceListDto> getNPDResourceReport(NPDResourceListDto npdResourceListDto) 
	{
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rsViewNPDResourceList = null;
		ArrayList<NPDResourceListDto> listNPDResource = new ArrayList<NPDResourceListDto>();
		NPDResourceListDto objDto = null;
		
		try
		{
			connection=NpdConnection.getConnectionObject();
			String getNPDResourceList = null;
			getNPDResourceList= "select * FROM NPD.V_NPDRESOURCELIST";
			
			String whereCondition="";
			String condition;
			
			condition=Utility.generateStringLikeCondition(npdResourceListDto.getProjectName(), "PROJECT_NAME");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			int Test=npdResourceListDto.getProjectID();
			if(Test!=0)
			{
				condition=Utility.generateIntCondition(npdResourceListDto.getProjectID(), "PROJECTID");
				whereCondition=Utility.addToCondition(whereCondition,condition);
			}
			condition=Utility.generateStringLikeCondition(npdResourceListDto.getRoleName(), "ROLENAME");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			condition=Utility.generateStringLikeCondition(npdResourceListDto.getEmpName(), "EMPNAME");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			if(npdResourceListDto.getProjectStatus()!=-1)
			{
				condition=Utility.generateRelativeCondition("EQUAL", String.valueOf(npdResourceListDto.getProjectStatus()), "", "PROJECTSTATUS");
				whereCondition=Utility.addToCondition(whereCondition,condition);
			}
			
			if(!(whereCondition.trim().equals("")))
			{
				getNPDResourceList=getNPDResourceList+" WHERE "+whereCondition;
			}
						
			//Generating Order By clause
			String orderByColumn="";
			String ASC_DESC="DESC";
			PagingSorting pagingSorting =npdResourceListDto.getPagingSorting();
			String sortBy=pagingSorting.getSortByColumn();
			
			if("projectName".equals(sortBy))
			{
				orderByColumn="PROJECT_NAME";
			}
			else if("projectID".equals(sortBy))
			{
				orderByColumn="PROJECTID";
			}
			else if("roleID".equals(sortBy))
			{
				orderByColumn="ROLEID";
			}
			else if("roleName".equals(sortBy))
			{
				orderByColumn="ROLENAME";
			}
			else if("empName".equals(sortBy))
			{
				orderByColumn="EMPNAME";
			}
						
			ASC_DESC="DESC";
			String sortByOrder=pagingSorting.getSortByOrder();
			if("increment".equals(sortByOrder))
			{
				ASC_DESC="ASC";
			}
			
			if(orderByColumn!=null && !(orderByColumn.trim().equals("")))
			{
				String FullOrderBy=" ORDER BY "+orderByColumn+" "+ASC_DESC+" ";
				getNPDResourceList=getNPDResourceList+FullOrderBy;
				
//				For paging
				if(pagingSorting!=null)
				{
					if(pagingSorting.isPagingToBeDone())
					{
						pagingSorting.storeRecordCount(connection,getNPDResourceList);
						getNPDResourceList=pagingSorting.query(getNPDResourceList, FullOrderBy,PagingSorting.jdbc);
					}
					
				}
			}
			//System.err.println("Query:"+getNPDResourceList);
			
			connection.setAutoCommit(false);
			  
			proc=connection.prepareCall(getNPDResourceList);
			rsViewNPDResourceList = proc.executeQuery();
			while(rsViewNPDResourceList.next())
			{
				objDto =  new NPDResourceListDto();
				objDto.setProjectName(rsViewNPDResourceList.getString("PROJECT_NAME"));
				objDto.setProjectID(rsViewNPDResourceList.getInt("PROJECTID"));
				objDto.setRoleID(rsViewNPDResourceList.getString("ROLEID"));
				objDto.setRoleName(rsViewNPDResourceList.getString("ROLENAME"));
				objDto.setEmpName(rsViewNPDResourceList.getString("EMPNAME"));
				objDto.setProjectStatus(rsViewNPDResourceList.getInt("PROJECTSTATUS"));
				listNPDResource.add(objDto);
			}
			//return listNPDResource;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		finally
		{
			try
			{
				DbConnection.closeResultset(rsViewNPDResourceList);
				DbConnection.closeCallableStatement(proc);
				DbConnection.freeConnection(connection);
			}
			catch(Exception e)
			{
				e.printStackTrace();	
			}
		}
		return listNPDResource;
	}
	
	public ArrayList<NPDResourceListDto> getNPDResourceReportExport(NPDResourceListDto npdResourceListDto) 
	{
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rsViewNPDResourceList = null;
		ArrayList<NPDResourceListDto> listNPDResourceExport = new ArrayList<NPDResourceListDto>();
		NPDResourceListDto objDto = null;
		
		try
		{
			connection=NpdConnection.getConnectionObject();
			String getNPDResourceList = null;
			getNPDResourceList= "select * FROM NPD.V_NPDRESOURCELIST";
			
			String whereCondition="";
			String condition;
			
			condition=Utility.generateStringLikeCondition(npdResourceListDto.getProjectName(), "PROJECT_NAME");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			int Test=npdResourceListDto.getProjectID();
			if(Test!=0)
			{
				condition=Utility.generateIntCondition(npdResourceListDto.getProjectID(), "PROJECTID");
				whereCondition=Utility.addToCondition(whereCondition,condition);
			}
			condition=Utility.generateStringLikeCondition(npdResourceListDto.getRoleName(), "ROLENAME");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			condition=Utility.generateStringLikeCondition(npdResourceListDto.getEmpName(), "EMPNAME");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			if(npdResourceListDto.getProjectStatus()!=-1)
			{
				condition=Utility.generateRelativeCondition("EQUAL", String.valueOf(npdResourceListDto.getProjectStatus()), "", "PROJECTSTATUS");
				whereCondition=Utility.addToCondition(whereCondition,condition);
			}
			
			if(!(whereCondition.trim().equals("")))
			{
				getNPDResourceList=getNPDResourceList+" WHERE "+whereCondition;
			}
						
			//Generating Order By clause
			String orderByColumn="";
			String ASC_DESC="DESC";
			PagingSorting pagingSorting =npdResourceListDto.getPagingSorting();
			String sortBy=pagingSorting.getSortByColumn();
			
			if("projectName".equals(sortBy))
			{
				orderByColumn="PROJECT_NAME";
			}
			else if("projectID".equals(sortBy))
			{
				orderByColumn="PROJECTID";
			}
			else if("roleID".equals(sortBy))
			{
				orderByColumn="ROLEID";
			}
			else if("roleName".equals(sortBy))
			{
				orderByColumn="ROLENAME";
			}
			else if("empName".equals(sortBy))
			{
				orderByColumn="EMPNAME";
			}
			else if("projectStatus".equals(sortBy))
			{
				orderByColumn="PROJECTSTATUS";
			}
						
			ASC_DESC="DESC";
			String sortByOrder=pagingSorting.getSortByOrder();
			if("increment".equals(sortByOrder))
			{
				ASC_DESC="ASC";
			}
			
			if(orderByColumn!=null && !(orderByColumn.trim().equals("")))
			{
				String FullOrderBy=" ORDER BY "+orderByColumn+" "+ASC_DESC+" ";
				getNPDResourceList=getNPDResourceList+FullOrderBy;
			}
			//System.err.println("Query:"+getNPDResourceList);
			
			connection.setAutoCommit(false);
			
			proc=connection.prepareCall(getNPDResourceList);
			rsViewNPDResourceList = proc.executeQuery();
			while(rsViewNPDResourceList.next())
			{
				objDto =  new NPDResourceListDto();
				objDto.setProjectName(rsViewNPDResourceList.getString("PROJECT_NAME"));
				objDto.setProjectID(rsViewNPDResourceList.getInt("PROJECTID"));
				objDto.setRoleID(rsViewNPDResourceList.getString("ROLEID"));
				objDto.setRoleName(rsViewNPDResourceList.getString("ROLENAME"));
				objDto.setEmpName(rsViewNPDResourceList.getString("EMPNAME"));
				objDto.setProjectStatus(rsViewNPDResourceList.getInt("PROJECTSTATUS"));
				listNPDResourceExport.add(objDto);
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
				DbConnection.closeResultset(rsViewNPDResourceList);
				DbConnection.closeCallableStatement(proc);
				DbConnection.freeConnection(connection);
			}
			catch(Exception e)
			{
				e.printStackTrace();	
			}
		}
		return listNPDResourceExport;
	}
}
