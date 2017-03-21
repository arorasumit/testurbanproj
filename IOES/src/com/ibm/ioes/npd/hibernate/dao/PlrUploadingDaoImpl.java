package com.ibm.ioes.npd.hibernate.dao;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.sql.rowset.serial.SerialBlob;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;

import com.ibm.ioes.npd.exception.NpdException;
import com.ibm.ioes.npd.hibernate.beans.MyToDoListDto;
import com.ibm.ioes.npd.hibernate.beans.PlrUploadingDto;
import com.ibm.ioes.npd.hibernate.beans.TmEmployee;
import com.ibm.ioes.npd.hibernate.beans.TmNpdcategory;
import com.ibm.ioes.npd.hibernate.beans.TmProductcategory;
import com.ibm.ioes.npd.hibernate.beans.TtrnProject;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.CommonUtilities;
import com.ibm.ioes.npd.utilities.Messages;
import com.ibm.ioes.npd.utilities.NpdConnection;
import com.ibm.ioes.npd.utilities.PagingSorting;
import com.ibm.ioes.npd.utilities.SendMail;
import com.ibm.ioes.npd.utilities.Utility;
import com.ibm.ioes.utilities.DbConnection;

public class PlrUploadingDaoImpl 
{
	private static final Logger logger;
	private static int iRoleId;
	private static int iUserID;
	private static String iProjectId=null;
	static 
	{
		logger = Logger.getLogger(PlrUploadingDaoImpl.class);
	}
	//Fetch MyToDoList Summary
	public ArrayList<PlrUploadingDto>  viewProjectList(PlrUploadingDto searchDto) throws NpdException
	{		
		iRoleId = searchDto.getRoleId();
		iUserID = searchDto.getUserId();
		iProjectId = searchDto.getSearchProjectId();
	
		
		StringBuffer sQuery = new StringBuffer();


		sQuery.append(" select distinct  PROJECTID,NPDCATDESC,NPD_CATEGORY,PROJECT_NAME,PRODCATDESC,PRODUCT_CATEGORY,AIRTEL_POTENTIAL,");
		sQuery.append(" CAPEX_REQUIREMENT,TARGET_MARKET,TOTAL_MKT_POTENTIAL,");
		sQuery.append(" (SELECT COUNT(1)  FROM  NPD.TM_PLRDOCS WHERE ROLE_ID = "+ iRoleId +" AND IS_DOC_UPLOADED = 0) AS DAYINPLR ");
		sQuery.append(", EXPT_LAUNCH_DATE,LAUNCH_PRIORITY,PRODUCT_BRIEF ");
		sQuery.append(", COALESCE( ");
		sQuery.append("	 ( ");
		sQuery.append(" SELECT NPD_EMP_ID FROM NPD.TTRN_PLR_MAPPING WHERE NPD_EMP_ID = "+  iUserID +"  AND PROJECT_ID = NPD.V_CPPMYTODOLIST_FORPLR.PROJECTID ");
		sQuery.append(" ),0) AS NPD_EMP ");
		sQuery.append(" from NPD.V_CPPMYTODOLIST_FORPLR");
		sQuery.append(" WHERE 1 =1 ");
				
		//sQuery.append(" WHERE ROLEHOLDERID = "+ searchDto.getUserId() +" and USERID= "+searchDto.getRoleId());
		
		//System.out.println("QUERY-->"+sQuery.toString());
		//generating condr=tion for where 
		String whereCondition="";
		
		String condition;		
		if(searchDto.getSearchProjectId()!=null && !searchDto.getSearchProjectId().trim().equalsIgnoreCase(""))
		{
			iProjectId = searchDto.getSearchProjectId();			
		}
		else
		{
			iProjectId = null;
		}
		
		if(!String.valueOf(iRoleId).equalsIgnoreCase(AppConstants.ADMIN_USERID))
		{
			condition=Utility.generateRelativeCondition("EQUAL",String.valueOf(iRoleId),"","TASKSTAKEHOLDER");
			whereCondition=Utility.addToCondition(whereCondition,condition);
		}
		
		condition=Utility.generateRelativeCondition("EQUAL",searchDto.getSearchProjectId(),"","PROJECTID");
		whereCondition=Utility.addToCondition(whereCondition,condition);
		
		condition=Utility.generateStringLikeCondition(searchDto.getSearchProjectPlanName(),"PROJECT_NAME");
		whereCondition=Utility.addToCondition(whereCondition,condition);

		condition=Utility.generateStringLikeCondition(searchDto.getSearchLaunchPriority(),"LAUNCH_PRIORITY");
		whereCondition=Utility.addToCondition(whereCondition,condition);

		
		if(!(whereCondition.trim().equals("")))
		{
			sQuery=sQuery.append(" AND ").append(whereCondition);
		}
		
		//Generating Order By clause
		String orderByColumn="";
		String ASC_DESC="DESC";
		String sortBy=searchDto.getSortBy();
		if("projectId".equals(sortBy))
		{
			orderByColumn="PROJECTID";
		}
		else if("projectName".equals(sortBy))
		{
			orderByColumn="PROJECT_NAME";
		}
		else if("priorityOfLaunch".equals(sortBy))
		{
			orderByColumn="LAUNCH_PRIORITY";
		}
		else if("productBrief".equals(sortBy))
		{
			orderByColumn="PRODUCT_BRIEF";
		}
		else if("targetMarket".equals(sortBy))
		{
			orderByColumn="TARGET_MARKET";
		}
		else if("productCategory".equals(sortBy))
		{
			orderByColumn="PRODCATDESC";
		}
		else if("npdCategory".equals(sortBy))
		{
			orderByColumn="NPDCATDESC";
		}
		else if("airtelPotential".equals(sortBy))
		{
			orderByColumn="AIRTEL_POTENTIAL";
		}
		else if("totalMarketPotential".equals(sortBy))
		{
			orderByColumn="TOTAL_MKT_POTENTIAL";
		}
		else if("capexRequirement".equals(sortBy))
		{
			orderByColumn="CAPEX_REQUIREMENT";
		}
		
		ASC_DESC="DESC";
		if("increment".equals(searchDto.getSortByOrder()))
		{
			ASC_DESC="ASC";
		}

		if(orderByColumn!=null && !(orderByColumn.trim().equals("")))
		{
			String FullOrderBy=" ORDER BY "+orderByColumn+" "+ASC_DESC+" ";
			sQuery.append(FullOrderBy);			
			//For paging
			PagingSorting paging=searchDto.getPagingSorting();
			if(paging!=null)
			{
				Connection conn=null;
				try 
				{
					conn=NpdConnection.getConnectionObject();
					paging.storeRecordCount(conn,sQuery.toString());
					
				} catch (NpdException e) 
				{
					e.printStackTrace();
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
				finally
				{
					try{
						NpdConnection.freeConnection(conn);
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
				sQuery=new StringBuffer(paging.query(sQuery.toString(), FullOrderBy,"jdbc"));				
			}
		}
		ResultSet rs = null;		
		ArrayList<PlrUploadingDto> dtoList = new ArrayList<PlrUploadingDto>(); 
		Connection conn=null;
		TmProductcategory prdCategory = null;
		TtrnProject projectDetails = null;
		TmNpdcategory npdCategory = null;
		try 
		{
			PlrUploadingDto objDto=null;			
			conn=NpdConnection.getConnectionObject();
			Statement stmt=conn.createStatement();
			rs=stmt.executeQuery(sQuery.toString());
			//rs=NpdConnection.getResultSet(sQuery.toString());
			while(rs.next())
			{
				objDto = new PlrUploadingDto();
				prdCategory = new TmProductcategory();
				projectDetails = new TtrnProject();
				npdCategory = new TmNpdcategory();

				projectDetails.setProjectid(rs.getInt("PROJECTID"));
				projectDetails.setProjectName(rs.getString("PROJECT_NAME"));
				projectDetails.setLaunchPriority(rs.getString("LAUNCH_PRIORITY"));
				projectDetails.setProductBrief(rs.getString("PRODUCT_BRIEF"));
				projectDetails.setTargetMkt(rs.getString("TARGET_MARKET"));
				prdCategory.setProdcatdesc(rs.getString("PRODCATDESC"));
				projectDetails.setPrdCategory(prdCategory);
				npdCategory.setNpdcatdesc(rs.getString("NPDCATDESC"));
				projectDetails.setNpdCategory(npdCategory);
				projectDetails.setAirtelPotential(rs.getString("AIRTEL_POTENTIAL"));
				projectDetails.setTotalMktPotential(rs.getString("TOTAL_MKT_POTENTIAL"));
				projectDetails.setCapexReq(rs.getString("CAPEX_REQUIREMENT"));
				objDto.setIsupload(Integer.parseInt(rs.getString("NPD_EMP")));
				objDto.setDayforplr(String.valueOf(rs.getString("DAYINPLR")));
				
				objDto.setProjectDetails(projectDetails);
				
				dtoList.add(objDto);
			}
			
		} 		
		catch (SQLException sqlEx) {
			logger.error(sqlEx.getMessage()
			+ " SQLException occured while closing database objects in myToDoList method of "
			+ this.getClass().getSimpleName());
			throw new NpdException("SQL Exception : "
			+ sqlEx.getMessage(), sqlEx);
		} catch (Exception ex) {
			logger.error(ex.getMessage()
			+ " Exception occured while closing database objects in myToDoList method of "
			+ this.getClass().getSimpleName());
			throw new NpdException("Exception : " + ex.getMessage(),ex);
		}
		finally
		{
			
			try {
				DbConnection.closeResultset(rs);
				NpdConnection.freeConnection(conn);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new NpdException("Exception : " + e.getMessage(),e);
			}
		}
		return dtoList;
	}
	
	public boolean  mapStakeHolder(PlrUploadingDto searchDto) throws NpdException
	{		
		iRoleId = searchDto.getRoleId();
		iUserID = searchDto.getUserId();
		iProjectId = searchDto.getSearchProjectId();
		
		StringBuffer sQuery = new StringBuffer();
		Connection connection=null;
		 String Msg="";
		 CallableStatement proc =null;
		 boolean isSaved=false;
		 String roleid = null;
		 String npdempid = null;
		 String plrdays  = null;
		try{
					
			StringTokenizer splitStringStage = new StringTokenizer(searchDto.getStakeholderidlist().toString() ,",");
			String[] arr=new String[splitStringStage.countTokens()];
			
			connection=NpdConnection.getConnectionObject();
			connection.setAutoCommit(false);
			
			
			
			if(searchDto.getStakeholderidlist().toString().equalsIgnoreCase(""))
			{
				sQuery.append(" DELETE FROM NPD.TTRN_PLR_MAPPING WHERE PROJECT_ID="+searchDto.getSearchProjectId() );
	
				ResultSet rs = null;		
				Connection conn=null;
				try
				{
				conn=NpdConnection.getConnectionObject();
				Statement stmt=conn.createStatement();
				stmt.executeUpdate(sQuery.toString());
				}
				finally
				{
					NpdConnection.freeConnection(conn);
				}
				
			}
			
			for (int i = 0; i < arr.length; i++) 
				
			{
				proc=connection.prepareCall(" {CALL NPD.P_INSERT_TTRN_PLR_MAPPING(?,?,?,?,?,?,?,?,?)}");

				StringTokenizer splitStringValues = new StringTokenizer(splitStringStage.nextToken() ,"_");
				String[] plrvalues=new String[splitStringValues.countTokens()];

				 
				plrdays = splitStringValues.nextToken(); 
				roleid = splitStringValues.nextToken();
				npdempid = splitStringValues.nextToken();
				
				proc.setLong(1,Integer.parseInt(searchDto.getSearchProjectId()));
				proc.setLong(2,1);
				proc.setLong(3,Integer.parseInt(npdempid));
				proc.setLong(4,Long.parseLong(plrdays));
				proc.setLong(5,searchDto.getUserId()); ///Task Owner
				proc.setLong(6,i); ///For Deleting
				proc.setLong(7,Long.parseLong(roleid)); ///For Deleting
				proc.setString(8,"");
				proc.setString(9,"");
				proc.execute();
				Msg=proc.getString(8);
				//Verifiying that exception accured or not in Stord Proc
				if(proc.getString(8).equals("66666")){
				connection.rollback();	
				}else{
				connection.commit();	

				}
			}
			proc=connection.prepareCall(" {CALL NPD.P_INSERT_TTRN_PLR_MAPPING_HISTORY(?,?,?,?,?,?,?,?)}");
			proc.setLong(1,Integer.parseInt(searchDto.getSearchProjectId()));
			proc.setLong(2,1);
			proc.setLong(3,1);
			proc.setLong(4,1);
			proc.setLong(5,1); ///Task Owner
			proc.setLong(6,0); ///For Deleting
			proc.setString(7,"");
			proc.setString(8,"");
			proc.execute();
			Msg=proc.getString(7);
			//Verifiying that exception accured or not in Stord Proc
			if(proc.getString(7).equals("66666")){
			connection.rollback();	
			}else{
			connection.commit();	

			}

			isSaved =true;
			
		}

catch (SQLException sqlEx) {
			logger.error(sqlEx.getMessage()
			+ " SQLException occured while closing database objects in myToDoList method of "
			+ this.getClass().getSimpleName());
			throw new NpdException("SQL Exception : "
			+ sqlEx.getMessage(), sqlEx);
		} catch (Exception ex) {
			logger.error(ex.getMessage()
			+ " Exception occured while closing database objects in myToDoList method of "
			+ this.getClass().getSimpleName());
			throw new NpdException("Exception : " + ex.getMessage(),ex);
		}
		finally
		{
			
			try {
				DbConnection.closeCallableStatement(proc);
				NpdConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new NpdException("Exception : " + e.getMessage(),e);
			}
		}
		return isSaved;
		
	}

	public boolean  mapStakeHolderHistory(PlrUploadingDto searchDto) throws NpdException
	{		
		iRoleId = searchDto.getRoleId();
		iUserID = searchDto.getUserId();
		iProjectId = searchDto.getSearchProjectId();
		
		StringBuffer sQuery = new StringBuffer();
		Connection connection=null;
		 String Msg="";
		 CallableStatement proc =null;
		 boolean isSaved=false;

		try{
					
			StringTokenizer splitStringStage = new StringTokenizer(searchDto.getStakeholderidlist().toString() ,",");
			String[] arr=new String[splitStringStage.countTokens()];
			
			connection=NpdConnection.getConnectionObject();
			connection.setAutoCommit(false);
			proc=connection.prepareCall(" {CALL NPD.P_INSERT_TTRN_PLR_MAPPING_HISTORY(?,?,?,?,?,?,?,?)}");
			proc.setLong(1,Integer.parseInt(searchDto.getSearchProjectId()));
			proc.setLong(2,1);
			proc.setLong(3,1);
			proc.setLong(4,1);
			proc.setLong(5,1); ///Task Owner
			proc.setLong(6,0); ///For Deleting
			proc.setString(7,"");
			proc.setString(8,"");
			proc.execute();
			Msg=proc.getString(7);
			//Verifiying that exception accured or not in Stord Proc
			if(proc.getString(7).equals("66666")){
			connection.rollback();	
			}else{
			connection.commit();	

			}

			isSaved =true;
			
		}

catch (SQLException sqlEx) {
			logger.error(sqlEx.getMessage()
			+ " SQLException occured while closing database objects in myToDoList method of "
			+ this.getClass().getSimpleName());
			throw new NpdException("SQL Exception : "
			+ sqlEx.getMessage(), sqlEx);
		} catch (Exception ex) {
			logger.error(ex.getMessage()
			+ " Exception occured while closing database objects in myToDoList method of "
			+ this.getClass().getSimpleName());
			throw new NpdException("Exception : " + ex.getMessage(),ex);
		}
		finally
		{
			
			try {
				DbConnection.closeCallableStatement(proc);
				NpdConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new NpdException("Exception : " + e.getMessage(),e);
			}
		}
		return isSaved;
		
	}

	
	public ArrayList<PlrUploadingDto>  alreadyMappedStakeHolder(PlrUploadingDto searchDto) throws NpdException
	{		
		iRoleId = searchDto.getRoleId();
		iUserID = searchDto.getUserId();
		iProjectId = searchDto.getSearchProjectId();
		Statement stmt=null;
		StringBuffer sQuery = new StringBuffer();
		Connection connection=null;
		 String employeeId= "";
		 String[] employeeIdList = null;
		 PlrUploadingDto objDto = null;
		 ResultSet rs = null;		
			
		 ArrayList<PlrUploadingDto> mapstakeholderlst = new ArrayList<PlrUploadingDto>();
		try{
					
			
				
			sQuery.append(" SELECT DISTINCT ASSIGNEDTOUSERID , EMPNAME , TASKSTAKEHOLDER , ROLENAME , ");
			sQuery.append(" COALESCE((SELECT PLR_DURATION   ");
			sQuery.append(" FROM NPD.TTRN_PLR_MAPPING "); 
			sQuery.append(" WHERE NPD_EMP_ID = NPD.TTRN_PROJECTHIERARCHY.ASSIGNEDTOUSERID ");
			sQuery.append(" AND PROJECT_ID = NPD.TTRN_PROJECTWORKFLOWDET.PROJECTID AND ROLE_ID = NPD.TTRN_PROJECTHIERARCHY.TASKSTAKEHOLDER ),0) AS PLR_DURATION,");
			sQuery.append(" COALESCE((SELECT 1 ");   
			sQuery.append(" FROM NPD.TTRN_PLR_MAPPING "); 
			sQuery.append(" WHERE NPD_EMP_ID = NPD.TTRN_PROJECTHIERARCHY.ASSIGNEDTOUSERID ");
			sQuery.append("         AND PROJECT_ID = NPD.TTRN_PROJECTWORKFLOWDET.PROJECTID AND ROLE_ID = NPD.TTRN_PROJECTHIERARCHY.TASKSTAKEHOLDER ),0) AS ISMAPPED ");
			sQuery.append(" FROM NPD.TTRN_PROJECTHIERARCHY ");
			sQuery.append(" INNER JOIN NPD.TTRN_PROJECTWORKFLOWDET ");
			sQuery.append(" ON NPD.TTRN_PROJECTWORKFLOWDET.PROJECTWORKFLOWID = NPD.TTRN_PROJECTHIERARCHY.PROJECTWORKFLOWID ");
			sQuery.append(" INNER JOIN NPD.TM_EMPLOYEE "); 
			sQuery.append(" ON NPD.TM_EMPLOYEE.NPDEMPID = ASSIGNEDTOUSERID and NPD.TM_EMPLOYEE.ISACTIVE=1 "); 
			sQuery.append(" INNER JOIN NPD.TM_ROLES ");
			sQuery.append(" ON TASKSTAKEHOLDER = ROLEID ");
			sQuery.append(" WHERE NPD.TTRN_PROJECTHIERARCHY.PROJECTWORKFLOWID in ( "); 
			sQuery.append(" SELECT PROJECTWORKFLOWID "); 
			sQuery.append(" FROM NPD.TTRN_PROJECTWORKFLOWDET WHERE PROJECTID = "+ Integer.parseInt(searchDto.getSearchProjectId()) + " AND ISACTIVE =1 ORDER BY EMPNAME");
			sQuery.append(" ) ");
	
				
				//Connection conn=null;
				connection=NpdConnection.getConnectionObject();
				stmt=connection.createStatement();
				rs=stmt.executeQuery(sQuery.toString());
				
				int iCount = 0;
				//rs=NpdConnection.getResultSet(sQuery.toString());
				TmEmployee tmemployee = null;
				
				
				while(rs.next())
				{
						tmemployee = new TmEmployee();
						objDto = new PlrUploadingDto();
						tmemployee.setNpdempid(rs.getInt("ASSIGNEDTOUSERID"));
						tmemployee.setEmpname(rs.getString("EMPNAME"));
						objDto.setRoleid(rs.getString("TASKSTAKEHOLDER"));
						objDto.setRolename(rs.getString("ROLENAME"));
						objDto.setPlannedduration(rs.getInt("PLR_DURATION"));
						objDto.setIsmapped(rs.getString("ISMAPPED"));
						objDto.setEmployeevalue(rs.getString("ASSIGNEDTOUSERID"));
						objDto.setEmployeelabel(rs.getString("EMPNAME"));

						objDto.setTmployee(tmemployee);
						mapstakeholderlst.add(objDto);
				}

		}

catch (SQLException sqlEx) {
			logger.error(sqlEx.getMessage()
			+ " SQLException occured while closing database objects in myToDoList method of "
			+ this.getClass().getSimpleName());
			throw new NpdException("SQL Exception : "
			+ sqlEx.getMessage(), sqlEx);
		} catch (Exception ex) {
			logger.error(ex.getMessage()
			+ " Exception occured while closing database objects in myToDoList method of "
			+ this.getClass().getSimpleName());
			throw new NpdException("Exception : " + ex.getMessage(),ex);
		}
		finally
		{
			
			try {
				DbConnection.closeResultset(rs);
				DbConnection.closeStatement(stmt);
				NpdConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new NpdException("Exception : " + e.getMessage(),e);
			}
		}
		return mapstakeholderlst;
		
	}

	public boolean updatePLR(PlrUploadingDto tranDto)
	{
		 Connection connection=null;
		 String Msg="";
		 CallableStatement proc =null;
		 boolean isSaved = false;
			try
			{

						connection=NpdConnection.getConnectionObject();
						connection.setAutoCommit(false);

						proc=connection.prepareCall(" {CALL NPD.P_UPLOAD_DOCS(?,?,?,?,?,?,?)}");
						proc.setLong(1,Integer.parseInt(tranDto.getSearchProjectId()));

						java.sql.Blob blob=null;
						
						FormFile doc=tranDto.getAttachment();
						if(doc!=null)
						{
						blob=new SerialBlob( new SerialBlob(tranDto.getAttachment().getFileData())); 
							proc.setBlob(2,blob);
							proc.setString(3,doc.getFileName());
							//proc.setString(18,doc.getFileName().substring(doc.getFileName().lastIndexOf(".")+1));
						}
						else
						{
							Blob blob2 = null;
							proc.setBlob(2,blob2);
							//proc.setLong(16,0);
							proc.setString(3,null);
							//proc.setString(18,null);
						}
						proc.setInt(4,tranDto.getUserId());
						proc.setInt(5,Integer.parseInt(tranDto.getSearchPlrId()));
						proc.setString(6,"");
						proc.setString(7,"");
						proc.execute();
						System.out.println(proc.getInt(6));
						System.out.println(proc.getString(6));
						
						Msg=proc.getString(6);
						//Verifiying that exception accured or not in Stord Proc
						if(proc.getString(6).equals("66666")){
						connection.rollback();	
						}else{
						connection.commit();	
						isSaved = true;
						}
			}
			
			catch(Exception ex)
			{
				ex.printStackTrace();
				logger.error(ex.getMessage()
						+ " Exception occured while closing database objects in fetchBcpDetails method of "
						+ this.getClass().getSimpleName());
			}
			finally
			{
				try {
					DbConnection.closeCallableStatement(proc);
					NpdConnection.freeConnection(connection);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				
				}
			}
		return isSaved;
	}

	
	public ArrayList<PlrUploadingDto> viewDocHistory(PlrUploadingDto searchDto)
	{
		 Connection connection=null;
		 String Msg="";
		 CallableStatement proc =null;
		 ResultSet rs = null;
		 boolean isSaved = false;
		 Hashtable htValue = new Hashtable();
		 StringBuffer sQuery = new StringBuffer();
		 PlrUploadingDto objDto = null;
		 ArrayList<PlrUploadingDto> dtoList = new ArrayList<PlrUploadingDto>();
			try
			{

						connection=NpdConnection.getConnectionObject();
						connection.setAutoCommit(false);

						sQuery.append(" SELECT * FROM NPD.VW_PLR_DOC_LIST WHERE 1=1 ");
						
						String whereCondition="";
						
						String condition;		
						if(searchDto.getSearchProjectId()!=null && !searchDto.getSearchProjectId().trim().equalsIgnoreCase(""))
						{
							iProjectId = searchDto.getSearchProjectId();			
						}
						else
						{
							iProjectId = null;
						}
						
						condition=Utility.generateRelativeCondition("EQUAL",searchDto.getSearchProjectId(),"","PROJECT_ID");
						whereCondition=Utility.addToCondition(whereCondition,condition);

						if(searchDto.getStakeholderid()!=null && !searchDto.getStakeholderid().equalsIgnoreCase(""))
						{
							condition=Utility.generateRelativeCondition("EQUAL",searchDto.getStakeholderid(),"","NPDEMPID");
							whereCondition=Utility.addToCondition(whereCondition,condition);
						}	
						
						if(!(whereCondition.trim().equals("")))
						{
							sQuery=sQuery.append(" AND ").append(whereCondition);
						}

						
						
						//Generating Order By clause
						String orderByColumn="";
						String ASC_DESC="DESC";
						String sortBy=searchDto.getSortBy();
						if("version".equals(sortBy))
						{
							orderByColumn="PREV_DOC_ID";
						}
						else if("refdocname".equals(sortBy))
						{
							orderByColumn="PLRDOCNAME";
						}
						else if("createdate".equals(sortBy))
						{
							orderByColumn="CREATEDDATE";
						}
						
						ASC_DESC="DESC";
						if("increment".equals(searchDto.getSortByOrder()))
						{
							ASC_DESC="ASC";
						}

						if(orderByColumn!=null && !(orderByColumn.trim().equals("")))
						{
							String FullOrderBy=" ORDER BY "+orderByColumn+" "+ASC_DESC+" ";
							sQuery.append(FullOrderBy);			
							//For paging
							PagingSorting paging=searchDto.getPagingSorting();
							if(paging!=null)
							{
								Connection conn=null;
								try 
								{
									conn=NpdConnection.getConnectionObject();
									paging.storeRecordCount(conn,sQuery.toString());
									
								} catch (NpdException e) 
								{
									e.printStackTrace();
								} catch (Exception e) 
								{
									e.printStackTrace();
								}finally
								{
									try{
										NpdConnection.freeConnection(conn);
									}
									catch(Exception e)
									{
										e.printStackTrace();
									}
								}
								sQuery=new StringBuffer(paging.query(sQuery.toString(), FullOrderBy,"jdbc"));				
							}
						}
						
						
						Statement stmt=connection.createStatement();
						rs=stmt.executeQuery(sQuery.toString());
						//rs=NpdConnection.getResultSet(sQuery.toString());
						while(rs.next())
						{
							objDto = new PlrUploadingDto();

							objDto.setRefDocName(rs.getString("PLRDOCNAME"));
							objDto.setAttachedFile(rs.getBlob("PLRDOC"));
							objDto.setCreatedBy(rs.getString("EMPNAME"));
							objDto.setCreatedDate(rs.getDate("CREATEDDATE"));
							objDto.setVersion(rs.getString("PREV_DOC_ID"));
							objDto.setRolename(rs.getString("ROLENAME"));
							objDto.setMonthlabel(rs.getString("MONTH_YEAR"));
							
							dtoList.add(objDto);
						}

						

			}
			
			catch(Exception ex)
			{
				ex.printStackTrace();
				logger.error(ex.getMessage()
						+ " Exception occured while closing database objects in fetchBcpDetails method of "
						+ this.getClass().getSimpleName());
			}
			finally
			{
				try {
					DbConnection.closeResultset(rs);
					DbConnection.closeCallableStatement(proc);
					
					NpdConnection.freeConnection(connection);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				
				}
			}
		return dtoList;
	}

	public ArrayList<PlrUploadingDto> getMappedStakeholderDetails(String str)
	{
		 Connection connection=null;
		 String Msg="";
		 CallableStatement proc =null;
		 ResultSet rs = null;
		 boolean isSaved = false;
		 Hashtable htValue = new Hashtable();
		 StringBuffer sQuery = new StringBuffer();
		 PlrUploadingDto objDto = null;
		 ArrayList<PlrUploadingDto> dtoList = new ArrayList<PlrUploadingDto>();
			try
			{
				
				 		
						updatePlrMailListing("");

						connection=NpdConnection.getConnectionObject();
						connection.setAutoCommit(false);

						sQuery.append("SELECT PROJECT_NAME , ROLENAME, NPD.V_PLR_PENDING.* , EMPNAME , EMAIL  FROM NPD.V_PLR_PENDING  ");
						sQuery.append("INNER JOIN NPD.TM_EMPLOYEE  ON NPD.TM_EMPLOYEE.NPDEMPID = NPD.V_PLR_PENDING.NPD_EMP_ID ");
						sQuery.append("INNER JOIN NPD.TM_ROLES    ON NPD.TM_ROLES.ROLEID = NPD.V_PLR_PENDING.ROLE_ID ");
						sQuery.append("INNER JOIN NPD.TTRN_PROJECT     ON NPD.V_PLR_PENDING.PROJECT_ID = PROJECTID ");
						sQuery.append("WHERE IS_DOC_UPLOADED = 0 AND IS_CURRENT  = 1 ");
						
						//sQuery.append(" WHERE PROJECTSTATUS = 0");
						
						String whereCondition="";
						
						
						if(!(whereCondition.trim().equals("")))
						{
							sQuery=sQuery.append(" AND ").append(whereCondition);
						}

						//int curMonth = CommonUtilities.getSystemDate().
						Statement stmt=connection.createStatement();
						rs=stmt.executeQuery(sQuery.toString());
						//rs=NpdConnection.getResultSet(sQuery.toString());
						TmEmployee tmployee = null;
						TtrnProject projectDetails = null;
						SendMail objSendMail = new SendMail();
						String subject = "PLR Needs to be uploaded";
						while(rs.next())
						{
							String temp="";
							String tempTo="";
							try
							{
								String emailId=rs.getString("EMAIL");
								if(emailId==null || "".equals(emailId))
								{
									String errStr=new Date()+" :Since email is not present Could not send getMappedStakeholderDetails mail for "
									+rs.getString("EMPNAME")+" for role "+rs.getString("ROLENAME")+" for project,year" +
									rs.getString("PROJECT_NAME")+"("+rs.getString("PROJECT_ID")+")"+","+rs.getString("MONTH_YEAR");
									
									System.err.println(errStr);
									logger.error(errStr);
									continue;
								}
								
								String[] to = {rs.getString("EMAIL")};
								tempTo=rs.getString("EMAIL");
								String message = "";
								message = "Dear " + rs.getString("EMPNAME") + "("+rs.getString("ROLENAME")+")";
								message = "<HTML><BODY>"+message  + "<BR><BR>PLR Needs to be uploaded for Project-"+ rs.getString("PROJECT_NAME") + "(Project Id :"+rs.getString("PROJECT_ID")+" ) For The Period Of " + rs.getString("MONTH_YEAR")+
								"<BR><BR>"+Messages.getMessageValue("Mail_Footer")+"</BODY></HTML>";
								temp=message;
								
								//System.err.println("plr uploading mail :to[0]"+to[0]+"::subject-"+subject+"::message:"+message);

								objSendMail.postMail(to, null, null, subject, message, null, null);
							}
							catch(Exception ex)
							{
								ex.printStackTrace();
								logger.error(ex.getMessage()
										+ " Exception occured while sending mail in getMappedStakeholderDetails method of "
										+ this.getClass().getSimpleName()+":"+new Date()+" Message"+temp+" . Email:"+tempTo);
							}
							
						}

						

			}
			
			catch(Exception ex)
			{
				ex.printStackTrace();
				logger.error(ex.getMessage()
						+ " Exception occured while closing database objects in fetchBcpDetails method of "
						+ this.getClass().getSimpleName());
			}
			finally
			{
				try {
					DbConnection.closeResultset(rs);
					DbConnection.closeCallableStatement(proc);
					NpdConnection.freeConnection(connection);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				
				}
			}
		return dtoList;
	}
	

	public ArrayList<PlrUploadingDto> plrEscalation(String str)
	{
		 Connection connection=null;
		 String Msg="";
		 CallableStatement proc =null;
		 ResultSet rs = null;
		 boolean isSaved = false;
		 Hashtable htValue = new Hashtable();
		 StringBuffer sQuery = new StringBuffer();
		 PlrUploadingDto objDto = null;
		 ArrayList<PlrUploadingDto> dtoList = new ArrayList<PlrUploadingDto>();
		 String[] toid = new String[1]; 
			try
			{

						connection=NpdConnection.getConnectionObject();
						connection.setAutoCommit(false);

						sQuery.append(" SELECT * FROM NPD.VW_PLRESCALATIONMAIL WHERE IS_CURRENT = 0 AND IS_DOC_UPLOADED = 0");
						
						//sQuery.append(" WHERE PROJECTSTATUS = 0");
						
						String whereCondition="";
						
						
						if(!(whereCondition.trim().equals("")))
						{
							sQuery=sQuery.append(" AND ").append(whereCondition);
						}

						//int curMonth = CommonUtilities.getSystemDate().
						Statement stmt=connection.createStatement();
						rs=stmt.executeQuery(sQuery.toString());
						//rs=NpdConnection.getResultSet(sQuery.toString());
						TmEmployee tmployee = null;
						TtrnProject projectDetails = null;
						SendMail objSendMail = new SendMail();
						String subject = "PLR Needs to be uploaded (Escalation)";
						PlrUploadingDto searchdto = new PlrUploadingDto();
						while(rs.next())
						{
							String log_toEmailId=null;
							String log_message=null;
							String log_esc_level=null;
							try
							{
							if(str.equalsIgnoreCase(AppConstants.PLR_FIRST_LEVEL_ESCALATION))
							{
								String[] to = {rs.getString("LEVEL1MAIL")};
								toid = to;
								log_toEmailId=rs.getString("LEVEL1MAIL");
								log_esc_level="Escalation Level 1";
							}

							if(str.equalsIgnoreCase(AppConstants.PLR_SECOND_LEVEL_ESCALATION))
							{
								String[] to = {rs.getString("LEVEL2MAIL")};
								toid = to;
								log_toEmailId=rs.getString("LEVEL2MAIL");
								log_esc_level="Escalation Level 2";
							}
							if(log_toEmailId==null || "".equals(log_toEmailId))
							{
								String logstr=new Date()+" :No email id present for "+log_esc_level+" for" +
										"user emailid,project name : "+rs.getString("EMPEMAIL")+","+rs.getString("PROJECT_NAME")+",";
								
								System.err.println(logstr);
								logger.error(logstr);
								continue;
							}

							String[] cc = {rs.getString("EMPEMAIL")};
							searchdto.setSearchProjectId(rs.getString("PROJECT_ID"));
							searchdto.setRoleid(rs.getString("ROLE_ID"));
							String monthlist =  getPendingMonthForMail(searchdto);
							String message = "<HTML><BODY>"+Messages.getMessageValue("Mail_Header")+"<BR>"
							+"PLR Needs to be uploaded for Project- "+ rs.getString("PROJECT_NAME") +"(Project Id:"+rs.getString("PROJECT_ID")+")"+ 
							" For the Month Of "+ monthlist+"<BR><BR>"+Messages.getMessageValue("Mail_Footer")+"</BODY></HTML>";
							
							//System.err.println("mail for plr uploading escalation :toid[0]:"+toid[0]+"::cc[0]:"+cc[0]+"::subject:"+subject+"::message:"+message);
							objSendMail.postMail(toid, cc, null, subject, message, null, null);
							}
							catch(Exception e)
							{
								e.printStackTrace();
								String logstr=new Date()+" :Error sending mail for esc level : "+log_esc_level+" for" +
								"user emailid,project name : "+rs.getString("EMPEMAIL")+","+rs.getString("PROJECT_NAME")+",";
								
								logger.error(e.getMessage()
										+ " Error sending mail "
										+ this.getClass().getSimpleName()+" "+logstr);
							}
						}

						

			}
			
			catch(Exception ex)
			{
				ex.printStackTrace();
				logger.error(ex.getMessage()
						+ " Exception occured while closing database objects in fetchBcpDetails method of "
						+ this.getClass().getSimpleName());
			}
			finally
			{
				try {
					DbConnection.closeResultset(rs);
					DbConnection.closeCallableStatement(proc);
					NpdConnection.freeConnection(connection);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				
				}
			}
		return dtoList;
	}
	
	public void updatePlrMailListing(String str)
	{
		Statement stmt=null;
		 Connection connection=null;
		 StringBuffer sQuery = new StringBuffer();
		 ArrayList<PlrUploadingDto> dtoList = new ArrayList<PlrUploadingDto>();
			try
			{

						connection=NpdConnection.getConnectionObject();
						connection.setAutoCommit(false);
						
						sQuery.append("UPDATE NPD.TM_PLRDOCS SET IS_CURRENT = 0");
						stmt=connection.createStatement();
						stmt.executeUpdate(sQuery.toString());

						sQuery = new StringBuffer();
						
						sQuery.append("INSERT INTO NPD.TM_PLRDOCS");
						sQuery.append("(PROJECT_ID,PLRDOCNAME,ISACTIVE,CREATEDBY,CREATEDDATE,PLR_MONTH,PLR_YEAR,ROLE_ID,IS_DOC_UPLOADED)");
						sQuery.append("SELECT PROJECT_ID , '-' AS PLRDOCNAME  ,1 AS ISACTIVE , NPD_EMP_ID AS CREATEDBY ,"); 
						sQuery.append("CURRENT_TIMESTAMP AS CREATEDDATE , MONTH(CURRENT_DATE) AS PLR_MONTH , YEAR(CURRENT_DATE) AS PLR_YEAR ,");
						sQuery.append(" ROLE_ID , 0 AS IS_DOC_UPLOADED ");
						sQuery.append(" FROM NPD.TTRN_PLR_MAPPING WHERE ");
						sQuery.append(" DATE(TRIM(CHAR(MONTH(CREATED_DATE + PLR_DURATION MONTH))) || '-' || '01' || '-' || TRIM(CHAR(YEAR(CREATED_DATE + PLR_DURATION MONTH)))) >  CURRENT_DATE ");
						
						//int curMonth = CommonUtilities.getSystemDate().
						stmt=connection.createStatement();
						stmt.executeUpdate(sQuery.toString());
						connection.commit();
			}
			
			catch(Exception ex)
			{
				ex.printStackTrace();
				logger.error(ex.getMessage()
						+ " Exception occured while closing database objects in fetchBcpDetails method of "
						+ this.getClass().getSimpleName());
			}
			finally
			{
				try {
					DbConnection.closeStatement(stmt);
					NpdConnection.freeConnection(connection);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				
				}
			}
	}

	
	public int getPendingPLRUploadingCount(String UserID) {
		Connection connection = null;
		ResultSet rs = null;
		int taskPending_plrUploadingCount = 0;
		Statement stmt =null;
		try {

			connection = NpdConnection.getConnectionObject();
			connection.setAutoCommit(false);

			String query = "SELECT COUNT(*) AS PENDING_PLR_UPLOADING_COUNT FROM NPD.V_PLRESCALATIONMAIL WHERE NPDEMPID="+UserID;

			stmt= connection.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				taskPending_plrUploadingCount = rs
						.getInt("PENDING_PLR_UPLOADING_COUNT");
			}

		}

		catch (Exception ex) {
			ex.printStackTrace();
			logger
					.error(ex.getMessage()
							+ " Exception occured while closing database objects in fetchBcpDetails method of "
							+ this.getClass().getSimpleName());
		} finally {
			try {
				DbConnection.closeResultset(rs);
				DbConnection.closeStatement(stmt);
				NpdConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}
		return taskPending_plrUploadingCount;
	}

	public ArrayList getPendingMonthList(PlrUploadingDto searchDto) {
		Connection connection = null;
		ResultSet rs = null;
		int taskPending_plrUploadingCount = 0;
		ArrayList objMonthLst =  new ArrayList();
		Statement stmt =null;
		try {

			connection = NpdConnection.getConnectionObject();
			connection.setAutoCommit(false);

			String query = "SELECT * FROM NPD.V_PLR_PENDING WHERE IS_DOC_UPLOADED = 0 ";
			query+=" AND PROJECT_ID = "+searchDto.getSearchProjectId()+" AND ROLE_ID ="+searchDto.getRoleid();

			stmt= connection.createStatement();
			rs = stmt.executeQuery(query);
			
			PlrUploadingDto objDto = null;
			while (rs.next()) 
			{
				objDto = new PlrUploadingDto();
				objDto = new PlrUploadingDto();
				objDto.setMonthvalue(rs.getString("PLR_ID"));
				objDto.setMonthlabel(rs.getString("MONTH_YEAR"));
				objMonthLst.add(objDto);
			}

		}

		catch (Exception ex) {
			ex.printStackTrace();
			logger
					.error(ex.getMessage()
							+ " Exception occured while closing database objects in fetchBcpDetails method of "
							+ this.getClass().getSimpleName());
		} finally {
			try {
				DbConnection.closeResultset(rs);
				DbConnection.closeStatement(stmt);
				NpdConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}
		return objMonthLst;
	}

	public String getPendingMonthForMail(PlrUploadingDto searchDto) {
		Connection connection = null;
		ResultSet rs = null;
		int taskPending_plrUploadingCount = 0;
		ArrayList objMonthLst =  new ArrayList();
		String monthList = "";
		Statement stmt =null;
		try {

			connection = NpdConnection.getConnectionObject();
			connection.setAutoCommit(false);

			String query = "SELECT * FROM NPD.V_PLR_PENDING WHERE IS_DOC_UPLOADED = 0 ";
			query+=" AND PROJECT_ID = "+searchDto.getSearchProjectId()+" AND ROLE_ID ="+searchDto.getRoleid();

			stmt= connection.createStatement();
			rs = stmt.executeQuery(query);
			
			PlrUploadingDto objDto = null;
			while (rs.next()) 
			{
				monthList+=rs.getString("MONTH_YEAR")+",";
			}
			monthList = monthList.substring(0, monthList.length()-1);
		}

		catch (Exception ex) {
			ex.printStackTrace();
			logger
					.error(ex.getMessage()
							+ " Exception occured while closing database objects in fetchBcpDetails method of "
							+ this.getClass().getSimpleName());
		} finally {
			try {
				DbConnection.closeResultset(rs);
				DbConnection.closeStatement(stmt);
				NpdConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}
		return monthList;
	}

}
