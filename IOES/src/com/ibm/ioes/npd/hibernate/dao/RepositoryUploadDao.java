package com.ibm.ioes.npd.hibernate.dao;

import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.sql.rowset.serial.SerialBlob;

import org.apache.struts.upload.FormFile;

import com.ibm.ioes.npd.hibernate.beans.RepositoryUploadDto;
import com.ibm.ioes.npd.utilities.NpdConnection;
import com.ibm.ioes.npd.utilities.PagingSorting;
import com.ibm.ioes.npd.utilities.Utility;
import com.ibm.ioes.utilities.DbConnection;

public class RepositoryUploadDao 
{
	public ArrayList<RepositoryUploadDto> getNPDCategory(RepositoryUploadDto objDto) 
	{
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rsViewNPDCategoryList = null;
		ArrayList<RepositoryUploadDto> listNPDResource = new ArrayList<RepositoryUploadDto>();
		RepositoryUploadDto RespositoryDto = null;
		
		try
		{
			connection=NpdConnection.getConnectionObject();
			String getNPDCategoryList = null;
			getNPDCategoryList= "Select * from NPD.TM_NPDCATEGORY WHERE ISACTIVE=1";
						  
			proc=connection.prepareCall(getNPDCategoryList);
			rsViewNPDCategoryList = proc.executeQuery();
			  while(rsViewNPDCategoryList.next())
			  {
				  RespositoryDto =  new RepositoryUploadDto();
				  RespositoryDto.setNpdCategoryName(rsViewNPDCategoryList.getString("NPDCATDESC"));
				  RespositoryDto.setNpdCategoryID(rsViewNPDCategoryList.getInt("NPDCATID"));
				  listNPDResource.add(RespositoryDto);
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
				DbConnection.closeCallableStatement(proc);
				NpdConnection.freeConnection(connection);
			}
			catch(Exception e)
			{
				e.printStackTrace();	
			}
		}
		return listNPDResource;
	}
	
	public ArrayList<RepositoryUploadDto> getProjectDetails(RepositoryUploadDto respositoryDto, String transactionID) 
	{
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rsViewNPDCategoryList = null;
		ArrayList<RepositoryUploadDto> listNPDResource = new ArrayList<RepositoryUploadDto>();
		//RepositoryUploadDto respositoryDto = new RepositoryUploadDto();
		try
		{
			connection=NpdConnection.getConnectionObject();
			String getProjectList = null;
			getProjectList= "Select * from NPD.VW_PROJECTDETAILS";
			
			String whereCondition="";
			String condition;
			
			int projectStatus=0;//CLOSE
			if(projectStatus==0)
			{
				condition="PROJECTSTATUS=0";
				whereCondition=Utility.addToCondition(whereCondition,condition);
			}
			

			int isActive=1;//CLOSE
			if(isActive==1)
			{
				condition="ISACTIVE=1";
				whereCondition=Utility.addToCondition(whereCondition,condition);
			}
			
			int categoryID=Integer.parseInt(transactionID);
			if(categoryID!=-1)
			{
				condition=Utility.generateIntCondition(categoryID, "NPD_CATEGORY");
				whereCondition=Utility.addToCondition(whereCondition,condition);
			}
						
			//Project NAME Searching
			condition=Utility.generateStringLikeCondition(respositoryDto.getProjectName(), "PROJECT_NAME");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			//Project ID Searching
			int Test=respositoryDto.getProjectID();
			if(Test!=0)
			{
				condition=Utility.generateIntCondition(respositoryDto.getProjectID(), "PROJECTID");
				whereCondition=Utility.addToCondition(whereCondition,condition);
			}
			
			if(!(whereCondition.trim().equals("")))
			{
				getProjectList=getProjectList+" WHERE "+whereCondition;
			}
			
			//Generating Order By clause
			String orderByColumn="";
			String ASC_DESC="DESC";
			PagingSorting pagingSorting =respositoryDto.getPagingSorting();
			String sortBy=pagingSorting.getSortByColumn();
			
			if("projectName".equals(sortBy))
			{
				orderByColumn="PROJECT_NAME";
			}
			else if("projectID".equals(sortBy))
			{
				orderByColumn="PROJECTID";
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
				getProjectList=getProjectList+FullOrderBy;
				
//				For paging
				if(pagingSorting!=null)
				{
					if(pagingSorting.isPagingToBeDone())
					{
						pagingSorting.storeRecordCount(connection,getProjectList);
						getProjectList=pagingSorting.query(getProjectList, FullOrderBy,PagingSorting.jdbc);
					}
				}
			}
			//System.err.println("Query:"+getProjectList);
			
			connection.setAutoCommit(false);
			
			proc=connection.prepareCall(getProjectList);
			rsViewNPDCategoryList = proc.executeQuery();
			  while(rsViewNPDCategoryList.next())
			  {
				  respositoryDto =  new RepositoryUploadDto();
				  respositoryDto.setProjectName(rsViewNPDCategoryList.getString("PROJECT_NAME"));
				  respositoryDto.setProjectID(rsViewNPDCategoryList.getInt("PROJECTID"));
				  respositoryDto.setProjectWorkflowID(rsViewNPDCategoryList.getInt("PROJECTWORKFLOWID"));
				  listNPDResource.add(respositoryDto);
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
				DbConnection.closeResultset(rsViewNPDCategoryList);
				DbConnection.closeCallableStatement(proc);
				NpdConnection.freeConnection(connection);
			}
			catch(Exception e)
			{
				e.printStackTrace();	
			}
		}
		return listNPDResource;
	}

	public ArrayList<RepositoryUploadDto> getStageDetails(RepositoryUploadDto respositoryDto, String projID) 
	{
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rsViewStageList = null;
		ArrayList<RepositoryUploadDto> listStageDetails = new ArrayList<RepositoryUploadDto>();
		//RepositoryUploadDto respositoryDto = new RepositoryUploadDto();
		try
		{
			connection=NpdConnection.getConnectionObject();
			String getStageList = null;
			getStageList= "Select * from NPD.VW_STAGEDETAILS";
			
			String whereCondition="";
			String condition;
			
			int projectID=Integer.parseInt(projID);
			condition=Utility.generateIntCondition(projectID, "PROJECTID");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			int isActive=1;//Will fecth Active Data
			condition=Utility.generateIntCondition(isActive, "ISACTIVE");
			whereCondition=Utility.addToCondition(whereCondition,condition);
						
			if(!(whereCondition.trim().equals("")))
			{
				getStageList=getStageList+" WHERE "+whereCondition;
			}
			
			proc=connection.prepareCall(getStageList);
			rsViewStageList = proc.executeQuery();
			  while(rsViewStageList.next())
			  {
				  respositoryDto =  new RepositoryUploadDto();
				  respositoryDto.setStageName(rsViewStageList.getString("STAGENAME"));
				  respositoryDto.setStageID(rsViewStageList.getInt("CURRENTSTAGEID"));
				  listStageDetails.add(respositoryDto);
			  }
			  //return listStageDetails;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		finally
		{
			try
			{
				DbConnection.closeResultset(rsViewStageList);
				DbConnection.closeCallableStatement(proc);
				NpdConnection.freeConnection(connection);
			}
			catch(Exception e)
			{
				e.printStackTrace();	
			}
		}
		return listStageDetails;
	}
	
	public ArrayList<RepositoryUploadDto> getTaskDetails(RepositoryUploadDto respositoryDto, String varStageID,String projectId) 
	{
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rsViewStageList = null;
		ArrayList<RepositoryUploadDto> listTaskDetails = new ArrayList<RepositoryUploadDto>();
		//RepositoryUploadDto respositoryDto = new RepositoryUploadDto();
		try
		{
			connection=NpdConnection.getConnectionObject();
			String getTaskList = null;
			getTaskList= "Select * from NPD.VW_GETTASKDETAILS";
			
			String whereCondition="";
			String condition;
			
			int stageID=Integer.parseInt(varStageID);
			condition=Utility.generateIntCondition(stageID, "CURRENTSTAGEID");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			int isActive=1;//Will fecth Active Data
			condition=Utility.generateIntCondition(isActive, "ISACTIVE");
			whereCondition=Utility.addToCondition(whereCondition,condition);
						
			condition="PROJECTID="+projectId;
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			if(!(whereCondition.trim().equals("")))
			{
				getTaskList=getTaskList+" WHERE "+whereCondition;
			}
			
			proc=connection.prepareCall(getTaskList);
			rsViewStageList = proc.executeQuery();
			  while(rsViewStageList.next())
			  {
				  respositoryDto =  new RepositoryUploadDto();
				  respositoryDto.setTaskName(rsViewStageList.getString("TASKNAME"));
				  respositoryDto.setTaskID(rsViewStageList.getInt("CURRENTTASKID"));
				  listTaskDetails.add(respositoryDto);
			  }
			 // return listTaskDetails;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		finally
		{
			try
			{
				DbConnection.closeResultset(rsViewStageList);
				DbConnection.closeCallableStatement(proc);
				NpdConnection.freeConnection(connection);
			}
			catch(Exception e)
			{
				e.printStackTrace();	
			}
		}
		return listTaskDetails;
	}

	public boolean addDocuments(String DocName,FormFile Docattachment,int projID,int stageID,int taskID,int workFlowID)
	{
		 StringBuffer sQuery = new StringBuffer();
		 Connection connection=null;
		 String Msg="";
		 String Msg1="";
		 CallableStatement proc =null;
		 boolean insert = true;
		try
		{
			connection=NpdConnection.getConnectionObject();
			connection.setAutoCommit(false);
			proc=connection.prepareCall(" {CALL NPD.P_INSERT_REPOSITORYCLOSEDPROJECTS(?,?,?,?,?,?,?,?,?)} ");
			proc.setLong(1, projID);
			proc.setString(2, DocName);
			java.sql.Blob blob=null;
			FormFile doc=Docattachment;
			if(doc!=null)
			{
				blob=new SerialBlob( new SerialBlob(Docattachment.getFileData())); 
				proc.setBlob(3,blob);
				proc.setString(4,doc.getFileName());
			}
			else
			{
				Blob blob2 = null;
				proc.setBlob(3,blob2);
				proc.setString(4,null);
			}
			proc.setLong(5, stageID);
			proc.setLong(6, taskID);
			proc.setLong(7, workFlowID);
			
			proc.setString(8,"");
			proc.setString(9,"");
	
			proc.execute();
			
			Msg=proc.getString(9);
			Msg1=proc.getString(8);
			 
			if(proc.getString(8).equals("66666"))
			{
				connection.rollback();	
			}
			else
			{
				connection.commit();
			}
		}
		catch(Exception EX)
		{
			insert = false;
			EX.printStackTrace();
		}
		finally
		{
			try 
			{
				DbConnection.closeCallableStatement(proc);
				NpdConnection.freeConnection(connection);
			} catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			
			}
		}
		return insert;
	}

	public ArrayList<RepositoryUploadDto> getDocDetails(String projID) 
	{
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rsViewDocList = null;
		ArrayList<RepositoryUploadDto> listTaskDetails = new ArrayList<RepositoryUploadDto>();
		RepositoryUploadDto respositoryDto = new RepositoryUploadDto();
		try
		{
			connection=NpdConnection.getConnectionObject();
			String getDoclist = null;
			getDoclist= "SELECT * FROM NPD.VW_PROJECTDOCREPORT where PROJECTID="+projID;
			
			proc=connection.prepareCall(getDoclist);
			rsViewDocList = proc.executeQuery();
			  while(rsViewDocList.next())
			  {
				  respositoryDto =  new RepositoryUploadDto();
				  respositoryDto.setDocName(rsViewDocList.getString("DOCNAME"));
				  respositoryDto.setTaskName(rsViewDocList.getString("TASKNAME"));
				  respositoryDto.setStageName(rsViewDocList.getString("STAGENAME"));
				  listTaskDetails.add(respositoryDto);
			  }
			 // return listTaskDetails;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		finally
		{
			try
			{
				DbConnection.closeResultset(rsViewDocList);
				DbConnection.closeCallableStatement(proc);
				NpdConnection.freeConnection(connection);
			}
			catch(Exception e)
			{
				e.printStackTrace();	
			}
		}
		return listTaskDetails;
	}
}
