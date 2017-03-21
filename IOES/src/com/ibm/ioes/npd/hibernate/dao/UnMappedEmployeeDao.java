package com.ibm.ioes.npd.hibernate.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.ibm.ioes.npd.hibernate.beans.NPDResourceListDto;
import com.ibm.ioes.npd.hibernate.beans.UnMappedEmployeeDto;
import com.ibm.ioes.npd.utilities.NpdConnection;
import com.ibm.ioes.npd.utilities.PagingSorting;
import com.ibm.ioes.npd.utilities.Utility;
import com.ibm.ioes.utilities.DbConnection;

public class UnMappedEmployeeDao extends CommonBaseDao
{	
	public ArrayList<UnMappedEmployeeDto> getEmpReport(UnMappedEmployeeDto unMappedEmployeeDto) 
	{
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rsViewEmpList = null;
		ArrayList<UnMappedEmployeeDto> listEmpResource = new ArrayList<UnMappedEmployeeDto>();
		UnMappedEmployeeDto objDto = null;
		
		try
		{
			connection=NpdConnection.getConnectionObject();
			String getUnMappedEmpList = null;
			getUnMappedEmpList= "select * FROM NPD.VW_UNMAPPEDEMPLOYEE";
			
			String whereCondition="";
			String condition;
			
			condition=Utility.generateStringLikeCondition(unMappedEmployeeDto.getEmployeeName(), "EMPLOYEENAME");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			int Test=unMappedEmployeeDto.getEmployeeId();
			if(Test!=0)
			{
				condition=Utility.generateIntCondition(unMappedEmployeeDto.getEmployeeId(), "EMPID");
				whereCondition=Utility.addToCondition(whereCondition,condition);
			}
			
			if(!(whereCondition.trim().equals("")))
			{
				getUnMappedEmpList=getUnMappedEmpList+" WHERE "+whereCondition;
			}
						
			//Generating Order By clause
			String orderByColumn="";
			String ASC_DESC="DESC";
			PagingSorting pagingSorting =unMappedEmployeeDto.getPagingSorting();
			String sortBy=pagingSorting.getSortByColumn();
			
			if("empName".equals(sortBy))
			{
				orderByColumn="EMPLOYEENAME";
			}
			else if("empID".equals(sortBy))
			{
				orderByColumn="EMPID";
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
				getUnMappedEmpList=getUnMappedEmpList+FullOrderBy;
				
//				For paging
				if(pagingSorting!=null)
				{
					if(pagingSorting.isPagingToBeDone())
					{
						pagingSorting.storeRecordCount(connection,getUnMappedEmpList);
						getUnMappedEmpList=pagingSorting.query(getUnMappedEmpList, FullOrderBy,PagingSorting.jdbc);
					}
					
				}
			}
			//System.err.println("Query:"+getUnMappedEmpList);
			
			connection.setAutoCommit(false);
			  
			proc=connection.prepareCall(getUnMappedEmpList);
			rsViewEmpList = proc.executeQuery();
			while(rsViewEmpList.next())
			{
				objDto =  new UnMappedEmployeeDto();
				objDto.setEmployeeName(rsViewEmpList.getString("EMPLOYEENAME"));
				objDto.setEmployeeId(rsViewEmpList.getInt("EMPID"));
				listEmpResource.add(objDto);
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
				DbConnection.closeResultset(rsViewEmpList);
				DbConnection.closeCallableStatement(proc);
				DbConnection.freeConnection(connection);
			}
			catch(Exception e)
			{
				e.printStackTrace();	
			}
		}
		return listEmpResource;
	}
	
	public ArrayList<UnMappedEmployeeDto> getEmpReportExport(UnMappedEmployeeDto unMappedEmployeeDto) 
	{
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rsViewEmpList = null;
		ArrayList<UnMappedEmployeeDto> listEmpResource = new ArrayList<UnMappedEmployeeDto>();
		UnMappedEmployeeDto objDto = null;
		
		try
		{
			connection=NpdConnection.getConnectionObject();
			String getUnMappedEmpList = null;
			getUnMappedEmpList= "select * FROM NPD.VW_UNMAPPEDEMPLOYEE";
			
			String whereCondition="";
			String condition;
			
			condition=Utility.generateStringLikeCondition(unMappedEmployeeDto.getEmployeeName(), "EMPLOYEENAME");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			int Test=unMappedEmployeeDto.getEmployeeId();
			if(Test!=0)
			{
				condition=Utility.generateIntCondition(unMappedEmployeeDto.getEmployeeId(), "EMPID");
				whereCondition=Utility.addToCondition(whereCondition,condition);
			}
			
			if(!(whereCondition.trim().equals("")))
			{
				getUnMappedEmpList=getUnMappedEmpList+" WHERE "+whereCondition;
			}
						
			//Generating Order By clause
			String orderByColumn="";
			String ASC_DESC="DESC";
			PagingSorting pagingSorting =unMappedEmployeeDto.getPagingSorting();
			String sortBy=pagingSorting.getSortByColumn();
			
			if("empName".equals(sortBy))
			{
				orderByColumn="EMPLOYEENAME";
			}
			else if("empID".equals(sortBy))
			{
				orderByColumn="EMPID";
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
				getUnMappedEmpList=getUnMappedEmpList+FullOrderBy;
			}
			//System.err.println("Query:"+getUnMappedEmpList);
			
			connection.setAutoCommit(false);
			  
			proc=connection.prepareCall(getUnMappedEmpList);
			rsViewEmpList = proc.executeQuery();
			while(rsViewEmpList.next())
			{
				objDto =  new UnMappedEmployeeDto();
				objDto.setEmployeeName(rsViewEmpList.getString("EMPLOYEENAME"));
				objDto.setEmployeeId(rsViewEmpList.getInt("EMPID"));
				listEmpResource.add(objDto);
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
				DbConnection.closeResultset(rsViewEmpList);
				DbConnection.closeCallableStatement(proc);
				DbConnection.freeConnection(connection);
			}
			catch(Exception e)
			{
				e.printStackTrace();	
			}
		}
		return listEmpResource;
	}
}
