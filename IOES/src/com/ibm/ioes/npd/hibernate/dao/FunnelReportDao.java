package com.ibm.ioes.npd.hibernate.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.sql.CallableStatement;
import com.ibm.ioes.npd.utilities.NpdConnection;
import com.ibm.ioes.npd.exception.NpdException;
import com.ibm.ioes.npd.hibernate.beans.FunnelReportDto;
import com.ibm.ioes.npd.hibernate.beans.TmWorkflowstage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.PagingSorting;
import com.ibm.ioes.npd.utilities.Utility;
import com.ibm.ioes.utilities.DbConnection;

public class FunnelReportDao extends CommonBaseDao
{
	public HashMap getFunnelReport(FunnelReportDto funnelReportDto) 
	{
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rsViewProjectPlanDetail = null;
		ArrayList<FunnelReportDto> listProjectPlan = new ArrayList<FunnelReportDto>();
		
		FunnelReportDto objDto = null;
		 HashMap htValue = new HashMap();
		try
		{
			connection=NpdConnection.getConnectionObject();
			String getFunnelReport = null;
			getFunnelReport= "select * FROM NPD.V_FUNNELREPORT";
			
			String whereCondition="";
			String condition;
			String dateFilter=funnelReportDto.getDateFilter();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
			
			if("revisedLaunchDate".equals(dateFilter))
			{
				try {
					condition=Utility.generateDateRelativeCondition("BETWEEN",funnelReportDto.getFromDate(),funnelReportDto.getToDate(),"EXPT_LAUNCH_DATE",new SimpleDateFormat("dd/MM/yyyy"));
					whereCondition=Utility.addToCondition(whereCondition,condition);
				} 
				catch (Exception ex) 
				{
					// TODO Auto-generated catch block
					ex.printStackTrace();
					throw new NpdException("Exception : " + ex.getMessage(),ex);
				}
			}
			
			int projtID=funnelReportDto.getProjectID();
			if(projtID!=0)
			{
				condition=Utility.generateIntCondition(funnelReportDto.getProjectID(), "PROJECTID");
				whereCondition=Utility.addToCondition(whereCondition,condition);
			}
						
			condition=Utility.generateStringLikeCondition(funnelReportDto.getSearchStageName(), "PROJECT_NAME");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			if(funnelReportDto.getProjectStatus()!=-1)
			{
				condition=Utility.generateRelativeCondition("EQUAL", String.valueOf(funnelReportDto.getProjectStatus()), "", "PROJECTSTATUS");
				whereCondition=Utility.addToCondition(whereCondition,condition);
			}
			
			if(funnelReportDto.getSearchlaunchstatus()!=null && !funnelReportDto.getSearchlaunchstatus().equalsIgnoreCase("All") && 
					!funnelReportDto.getSearchlaunchstatus().equalsIgnoreCase(""))
			{
				condition=Utility.generateStringExactCondition(funnelReportDto.getSearchlaunchstatus().toUpperCase(),"LAUNCH_STATUS");
				whereCondition=Utility.addToCondition(whereCondition,condition);
			}

			if(!(whereCondition.trim().equals("")))
			{
				getFunnelReport=getFunnelReport+" WHERE "+whereCondition;
			}
						
			//Generating Order By clause
			String orderByColumn="";
			String ASC_DESC="DESC";
			PagingSorting pagingSorting =funnelReportDto.getPagingSorting();
			String sortBy=pagingSorting.getSortByColumn();
			
			if("projectID".equals(sortBy))
			{
				orderByColumn="PROJECTID";
			}
			else if("projectName".equals(sortBy))
			{
				orderByColumn="PROJECT_NAME";
			}
			else if("launchPriority".equals(sortBy))
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
			else if("launchPriority".equals(sortBy))
			{
				orderByColumn="LAUNCH_PRIORITY";
			}
			else if("airtelPotential".equals(sortBy))
			{
				orderByColumn="AIRTEL_POTENTIAL";
			}
			else if("TotalMktPotential".equals(sortBy))
			{
				orderByColumn="TOTAL_MKT_POTENTIAL";
			}
			else if("capexRequirement".equals(sortBy))
			{
				orderByColumn="CAPEX_REQUIREMENT";
			}
			else if("productMgr".equals(sortBy))
			{
				orderByColumn="PROJECT_MANAGER";
			}
			else if("techLead".equals(sortBy))
			{
				orderByColumn="TECH_LEAD";
			}
			else if("startMonth".equals(sortBy))
			{
				orderByColumn="CREATEDDATE";
			}
			else if("baselinelaunchmonth".equals(sortBy))
			{
				orderByColumn="BASELINE_MONTH";
			}
			else if("rvsdLaunchDate".equals(sortBy))
			{
				orderByColumn="EXPT_LAUNCH_DATE";
			}
			else if("launchqtr".equals(sortBy))
			{
				orderByColumn="LAUNCH_QUARTER";
			}
			else if("launchStatus".equals(sortBy))
			{
				orderByColumn="LAUNCH_STATUS";
			}
			else if("ideaStage".equals(sortBy))
			{
				orderByColumn="IDEA_STAGE";
			}
			else if("designStage".equals(sortBy))
			{
				orderByColumn="DESIGN_STAGE";
			}
			else if("dvptStage".equals(sortBy))
			{
				orderByColumn="DVP_STAGE";
			}
			else if("launchstage".equals(sortBy))
			{
				orderByColumn="LAUNCH_STAGE";
			}
			else if("launched".equals(sortBy))
			{
				orderByColumn="LAUNCHED";
			}
			else if("currentStage".equals(sortBy))
			{
				orderByColumn="CURRENT_STAGE";
			}
			else if("cuurentStatusRemarks".equals(sortBy))
			{
				orderByColumn="DETAILED_REMARKS";
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
				getFunnelReport=getFunnelReport+FullOrderBy;
				
//				For paging
				
				if(pagingSorting!=null)
				{
					if(pagingSorting.isPagingToBeDone())
					{
						//System.out.println(getFunnelReport);
						pagingSorting.storeRecordCount(connection,getFunnelReport);
						getFunnelReport=pagingSorting.query(getFunnelReport, FullOrderBy,PagingSorting.jdbc);
					}
					
				}
			}
			//System.err.println("Query:"+getFunnelReport);
						  
			connection.setAutoCommit(false);
			  
			//connection=NpdConnection.getConnectionObject();
			proc=connection.prepareCall(getFunnelReport);
			rsViewProjectPlanDetail = proc.executeQuery();
			  while(rsViewProjectPlanDetail.next())
			  {
				  objDto =  new FunnelReportDto();
				  objDto.setProjectID(rsViewProjectPlanDetail.getInt("PROJECTID"));
				  objDto.setProjectName(rsViewProjectPlanDetail.getString("PROJECT_NAME"));
				  objDto.setProjectStatus(rsViewProjectPlanDetail.getInt("PROJECTSTATUS"));
				  objDto.setLaunchPriority(rsViewProjectPlanDetail.getString("LAUNCH_PRIORITY"));
				  objDto.setProductBrief(rsViewProjectPlanDetail.getString("PRODUCT_BRIEF"));
				  objDto.setTargetMarket(rsViewProjectPlanDetail.getString("TARGET_MARKET"));
				  objDto.setProductCategory(rsViewProjectPlanDetail.getString("PRODCATDESC"));
				  objDto.setAirtelPotential(rsViewProjectPlanDetail.getString("AIRTEL_POTENTIAL"));
				  objDto.setTotalMktPotential(rsViewProjectPlanDetail.getString("TOTAL_MKT_POTENTIAL"));
				  objDto.setCapexRequirement(rsViewProjectPlanDetail.getString("CAPEX_REQUIREMENT"));
				  objDto.setProductMgr(rsViewProjectPlanDetail.getString("PROJECT_MANAGER"));
				  objDto.setTechLead(rsViewProjectPlanDetail.getString("TECH_LEAD"));
				  objDto.setStartMonth(rsViewProjectPlanDetail.getString("CREATEDDATE"));
				  objDto.setBaselinelaunchmonth(rsViewProjectPlanDetail.getString("BASELINE_MONTH"));
				  objDto.setRvsdLaunchDate(rsViewProjectPlanDetail.getString("EXPT_LAUNCH_DATE"));
				  objDto.setLaunchqtr(rsViewProjectPlanDetail.getString("LAUNCH_QUARTER"));
				  objDto.setLaunchStatus(rsViewProjectPlanDetail.getString("LAUNCH_STATUS"));
				  objDto.setIdeaStage(rsViewProjectPlanDetail.getString("IDEA_STAGE"));
				  objDto.setDesignStage(rsViewProjectPlanDetail.getString("DESIGN_STAGE"));
				  objDto.setDvptStage(rsViewProjectPlanDetail.getString("DVP_STAGE"));
				  objDto.setLaunchstage(rsViewProjectPlanDetail.getString("LAUNCH_STAGE"));
				  objDto.setLaunched(rsViewProjectPlanDetail.getString("LAUNCHED"));
				  objDto.setCurrentStage(rsViewProjectPlanDetail.getString("CURRENT_STAGE"));
				  objDto.setCuurentStatusRemarks(rsViewProjectPlanDetail.getString("DETAILED_REMARKS"));
				  objDto.setWorkflowid(rsViewProjectPlanDetail.getString("PROJECTWORKFLOWID"));
				  objDto.setDaysInProject(rsViewProjectPlanDetail.getInt("DAYSINPROJECT"));
				  
				  listProjectPlan.add(objDto);
			  }
			  
			  ArrayList<TmWorkflowstage> stagelist = new ArrayList<TmWorkflowstage>();
			  
			  stagelist = getprojectStageList("", true);
			  
			  ArrayList<TmWorkflowstage> projectstageList = null;
			  ArrayList<FunnelReportDto> funnelprojectList = new ArrayList<FunnelReportDto>();
			  
			  FunnelReportDto objfunnel = null;
			  
			  for (FunnelReportDto dto : listProjectPlan) 
			  {
				  objfunnel = new FunnelReportDto();
				  projectstageList = new ArrayList<TmWorkflowstage>();
				  projectstageList = getprojectStageList(dto.getWorkflowid(),false);
				  
				  objfunnel.setProjectID(dto.getProjectID());
				  objfunnel.setProjectstageList(projectstageList);
				  objfunnel.setProjectStatus(dto.getProjectStatus());
				  objfunnel.setProjectName(dto.getProjectName());
				  objfunnel.setLaunchPriority(dto.getLaunchPriority());
				  objfunnel.setProductBrief(dto.getProductBrief());
				  objfunnel.setTargetMarket(dto.getTargetMarket());
				  objfunnel.setProductCategory(dto.getProductCategory());
				  objfunnel.setAirtelPotential(dto.getAirtelPotential());
				  objfunnel.setTotalMktPotential(dto.getTotalMktPotential());
				  objfunnel.setCapexRequirement(dto.getCapexRequirement());
				  objfunnel.setProductMgr(getprojectPM_TL(dto.getWorkflowid(), AppConstants.PROJECT_MANAGER));
				  objfunnel.setTechLead(getprojectPM_TL(dto.getWorkflowid(), AppConstants.TECH_HEAD));
				  objfunnel.setStartMonth(dto.getStartMonth());
				  objfunnel.setBaselinelaunchmonth(dto.getBaselinelaunchmonth());
				  objfunnel.setRvsdLaunchDate(dto.getRvsdLaunchDate());
				  objfunnel.setLaunchqtr(dto.getLaunchqtr());
				  objfunnel.setLaunchStatus(dto.getLaunchStatus());
				  objfunnel.setIdeaStage(dto.getIdeaStage());
				  objfunnel.setDesignStage(dto.getDesignStage());
				  objfunnel.setDvptStage(dto.getDvptStage());
				  objfunnel.setLaunchstage(dto.getLaunchstage());
				  objfunnel.setLaunched(dto.getLaunched());
				  objfunnel.setCurrentStage(dto.getCurrentStage());
				  objfunnel.setCuurentStatusRemarks(dto.getCuurentStatusRemarks());
				  objfunnel.setWorkflowid(dto.getWorkflowid());
				  objfunnel.setDaysInProject(dto.getDaysInProject());
				  objfunnel.setAllstages(stagelist);
				  funnelprojectList.add(objfunnel);
			  }
			  
			  htValue.put("stagelist", stagelist);
			  htValue.put("funnelprojectList", funnelprojectList);
			  return htValue ;
		}
		catch(Exception ex )
		{
			ex.printStackTrace();	
		}
		finally
		{
			try 
			{
				DbConnection.closeResultset(rsViewProjectPlanDetail);
					DbConnection.closeCallableStatement(proc);
					DbConnection.freeConnection(connection);
				
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return htValue;
	}	
//	TODO Changes Made By Sumit On 09-Mar-2010 For Export to Excel 
	public HashMap getFunnelReportExport(FunnelReportDto funnelReportDto) 
	{
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rsViewProjectPlanDetail = null;
		ArrayList<FunnelReportDto> listProjectPlan = new ArrayList<FunnelReportDto>();
		FunnelReportDto objDto = null;
		 HashMap htValue = new HashMap();

		try
		{
			connection=NpdConnection.getConnectionObject();
			String getFunnelReport = null;
			getFunnelReport= "select * FROM NPD.V_FUNNELREPORT";
			
			String whereCondition="";
			String condition;
			String dateFilter=funnelReportDto.getDateFilter();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
			
			if("revisedLaunchDate".equals(dateFilter))
			{
				try {
					condition=Utility.generateDateRelativeCondition("BETWEEN",funnelReportDto.getFromDate(),funnelReportDto.getToDate(),"EXPT_LAUNCH_DATE",new SimpleDateFormat("dd/MM/yyyy"));
					whereCondition=Utility.addToCondition(whereCondition,condition);
				} 
				catch (Exception ex) 
				{
					// TODO Auto-generated catch block
					ex.printStackTrace();
					throw new NpdException("Exception : " + ex.getMessage(),ex);
				}
			}
			
			int projtID=funnelReportDto.getProjectID();
			if(projtID!=0)
			{
				condition=Utility.generateIntCondition(funnelReportDto.getProjectID(), "PROJECTID");
				whereCondition=Utility.addToCondition(whereCondition,condition);
			}
						
			condition=Utility.generateStringLikeCondition(funnelReportDto.getSearchStageName(), "PROJECT_NAME");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			if(funnelReportDto.getProjectStatus()!=-1)
			{
				condition=Utility.generateRelativeCondition("EQUAL", String.valueOf(funnelReportDto.getProjectStatus()), "", "PROJECTSTATUS");
				whereCondition=Utility.addToCondition(whereCondition,condition);
			}
			
			if(!(whereCondition.trim().equals("")))
			{
				getFunnelReport=getFunnelReport+" WHERE "+whereCondition;
			}
						
			//Generating Order By clause
			String orderByColumn="";
			String ASC_DESC="DESC";
			PagingSorting pagingSorting =funnelReportDto.getPagingSorting();
			String sortBy=pagingSorting.getSortByColumn();
			
			if("projectID".equals(sortBy))
			{
				orderByColumn="PROJECTID";
			}
			else if("projectName".equals(sortBy))
			{
				orderByColumn="PROJECT_NAME";
			}
			else if("launchPriority".equals(sortBy))
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
			else if("launchPriority".equals(sortBy))
			{
				orderByColumn="LAUNCH_PRIORITY";
			}
			else if("airtelPotential".equals(sortBy))
			{
				orderByColumn="AIRTEL_POTENTIAL";
			}
			else if("TotalMktPotential".equals(sortBy))
			{
				orderByColumn="TOTAL_MKT_POTENTIAL";
			}
			else if("capexRequirement".equals(sortBy))
			{
				orderByColumn="CAPEX_REQUIREMENT";
			}
			else if("productMgr".equals(sortBy))
			{
				orderByColumn="PROJECT_MANAGER";
			}
			else if("techLead".equals(sortBy))
			{
				orderByColumn="TECH_LEAD";
			}
			else if("startMonth".equals(sortBy))
			{
				orderByColumn="CREATEDDATE";
			}
			else if("baselinelaunchmonth".equals(sortBy))
			{
				orderByColumn="EXPT_LAUNCH_DATE";
			}
			else if("rvsdLaunchDate".equals(sortBy))
			{
				orderByColumn="EXPT_LAUNCH_DATE";
			}
			else if("launchqtr".equals(sortBy))
			{
				orderByColumn="LAUNCH_QUARTER";
			}
			else if("launchStatus".equals(sortBy))
			{
				orderByColumn="LAUNCH_STATUS";
			}
			else if("ideaStage".equals(sortBy))
			{
				orderByColumn="IDEA_STAGE";
			}
			else if("designStage".equals(sortBy))
			{
				orderByColumn="DESIGN_STAGE";
			}
			else if("dvptStage".equals(sortBy))
			{
				orderByColumn="DVP_STAGE";
			}
			else if("launchstage".equals(sortBy))
			{
				orderByColumn="LAUNCH_STAGE";
			}
			else if("launched".equals(sortBy))
			{
				orderByColumn="LAUNCHED";
			}
			else if("currentStage".equals(sortBy))
			{
				orderByColumn="CURRENT_STAGE";
			}
			else if("cuurentStatusRemarks".equals(sortBy))
			{
				orderByColumn="DETAILED_REMARKS";
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
				getFunnelReport=getFunnelReport+FullOrderBy;
			}
			//System.err.println("Query:"+getFunnelReport);
						  
			connection.setAutoCommit(false);
			//connection=NpdConnection.getConnectionObject();
			proc=connection.prepareCall(getFunnelReport);
			rsViewProjectPlanDetail = proc.executeQuery();
			  while(rsViewProjectPlanDetail.next())
			  {
				  objDto =  new FunnelReportDto();
				  objDto.setProjectID(rsViewProjectPlanDetail.getInt("PROJECTID"));
				  objDto.setProjectName(rsViewProjectPlanDetail.getString("PROJECT_NAME"));
				  objDto.setProjectStatus(rsViewProjectPlanDetail.getInt("PROJECTSTATUS"));
				  objDto.setLaunchPriority(rsViewProjectPlanDetail.getString("LAUNCH_PRIORITY"));
				  objDto.setProductBrief(rsViewProjectPlanDetail.getString("PRODUCT_BRIEF"));
				  objDto.setTargetMarket(rsViewProjectPlanDetail.getString("TARGET_MARKET"));
				  objDto.setProductCategory(rsViewProjectPlanDetail.getString("PRODCATDESC"));
				  objDto.setAirtelPotential(rsViewProjectPlanDetail.getString("AIRTEL_POTENTIAL"));
				  objDto.setTotalMktPotential(rsViewProjectPlanDetail.getString("TOTAL_MKT_POTENTIAL"));
				  objDto.setCapexRequirement(rsViewProjectPlanDetail.getString("CAPEX_REQUIREMENT"));
				  objDto.setProductMgr(rsViewProjectPlanDetail.getString("PROJECT_MANAGER"));
				  objDto.setTechLead(rsViewProjectPlanDetail.getString("TECH_LEAD"));
				  objDto.setStartMonth(rsViewProjectPlanDetail.getString("CREATEDDATE"));
				  //objDto.setBaselinelaunchmonth(rsViewProjectPlanDetail.getString("EXPT_LAUNCH_DATE"));
				  objDto.setBaselinelaunchmonth(rsViewProjectPlanDetail.getString("BASELINE_MONTH"));
				  objDto.setRvsdLaunchDate(rsViewProjectPlanDetail.getString("EXPT_LAUNCH_DATE"));
				  objDto.setLaunchqtr(rsViewProjectPlanDetail.getString("LAUNCH_QUARTER"));
				  objDto.setLaunchStatus(rsViewProjectPlanDetail.getString("LAUNCH_STATUS"));
				  objDto.setIdeaStage(rsViewProjectPlanDetail.getString("IDEA_STAGE"));
				  objDto.setDesignStage(rsViewProjectPlanDetail.getString("DESIGN_STAGE"));
				  objDto.setDvptStage(rsViewProjectPlanDetail.getString("DVP_STAGE"));
				  objDto.setLaunchstage(rsViewProjectPlanDetail.getString("LAUNCH_STAGE"));
				  objDto.setLaunched(rsViewProjectPlanDetail.getString("LAUNCHED"));
				  objDto.setCurrentStage(rsViewProjectPlanDetail.getString("CURRENT_STAGE"));
				  objDto.setCuurentStatusRemarks(rsViewProjectPlanDetail.getString("DETAILED_REMARKS"));
				  objDto.setWorkflowid(rsViewProjectPlanDetail.getString("PROJECTWORKFLOWID"));
				  objDto.setDaysInProject(rsViewProjectPlanDetail.getInt("DAYSINPROJECT"));
				  listProjectPlan.add(objDto);
			  }
			  ArrayList<TmWorkflowstage> stagelist = new ArrayList<TmWorkflowstage>();
			  
			  stagelist = getprojectStageList("", true);
			  
			  ArrayList<TmWorkflowstage> projectstageList = null;
			  ArrayList<FunnelReportDto> funnelprojectList = new ArrayList<FunnelReportDto>();
			  
			  FunnelReportDto objfunnel = null;
			  
			  for (FunnelReportDto dto : listProjectPlan) 
			  {
				  objfunnel = new FunnelReportDto();
				  projectstageList = new ArrayList<TmWorkflowstage>();
				  projectstageList = getprojectStageList(dto.getWorkflowid(),false);
				  
				  objfunnel.setProjectID(dto.getProjectID());
				  objfunnel.setProjectstageList(projectstageList);
				  objfunnel.setProjectStatus(dto.getProjectStatus());
				  objfunnel.setProjectName(dto.getProjectName());
				  objfunnel.setLaunchPriority(dto.getLaunchPriority());
				  objfunnel.setProductBrief(dto.getProductBrief());
				  objfunnel.setTargetMarket(dto.getTargetMarket());
				  objfunnel.setProductCategory(dto.getProductCategory());
				  objfunnel.setAirtelPotential(dto.getAirtelPotential());
				  objfunnel.setTotalMktPotential(dto.getTotalMktPotential());
				  objfunnel.setCapexRequirement(dto.getCapexRequirement());
				  objfunnel.setProductMgr(getprojectPM_TL(dto.getWorkflowid(), AppConstants.PROJECT_MANAGER));
				  objfunnel.setTechLead(getprojectPM_TL(dto.getWorkflowid(), AppConstants.TECH_HEAD));
				  objfunnel.setStartMonth(dto.getStartMonth());
				  objfunnel.setBaselinelaunchmonth(dto.getBaselinelaunchmonth());
				  objfunnel.setRvsdLaunchDate(dto.getRvsdLaunchDate());
				  objfunnel.setLaunchqtr(dto.getLaunchqtr());
				  objfunnel.setLaunchStatus(dto.getLaunchStatus());
				  objfunnel.setIdeaStage(dto.getIdeaStage());
				  objfunnel.setDesignStage(dto.getDesignStage());
				  objfunnel.setDvptStage(dto.getDvptStage());
				  objfunnel.setLaunchstage(dto.getLaunchstage());
				  objfunnel.setLaunched(dto.getLaunched());
				  objfunnel.setCurrentStage(dto.getCurrentStage());
				  objfunnel.setCuurentStatusRemarks(dto.getCuurentStatusRemarks());
				  objfunnel.setWorkflowid(dto.getWorkflowid());
				  objfunnel.setDaysInProject(dto.getDaysInProject());
				  objfunnel.setAllstages(stagelist);
				  funnelprojectList.add(objfunnel);
			  }
			  
			 
			  htValue.put("stagelist", stagelist);
			  htValue.put("funnelprojectList", funnelprojectList);
			  return htValue ;
		}
		catch(Exception ex )
		{
			ex.printStackTrace();	
		}
		finally
		{
			try 
			{
				
					DbConnection.closeResultset(rsViewProjectPlanDetail);
					DbConnection.closeCallableStatement(proc);
					DbConnection.freeConnection(connection);
				
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return htValue;
	}
	
	
	public ArrayList<TmWorkflowstage>  getprojectStageList( String projectworkflowid , boolean allstage )throws NpdException
	{
			Connection connection =null;
			CallableStatement proc =null;
			ResultSet rs = null;
			
			ArrayList<TmWorkflowstage> stagelist = new ArrayList<TmWorkflowstage>();  
			try
			{
				  connection=NpdConnection.getConnectionObject();
				
				  String query=null;
				  if(allstage==false)
				  {
					  query=" SELECT distinct  STAGENAME , CURRENTSTAGEID FROM NPD.TTRN_PROJECTHIERARCHY";
					  query=query+ " WHERE STAGENAME IS NOT NULL AND TASKSTATUS = 1 AND PROJECTWORKFLOWID  = "+ projectworkflowid ;
				  }
				  else
				  {
					  query=" SELECT distinct  STAGENAME , CURRENTSTAGEID FROM NPD.TTRN_PROJECTHIERARCHY";
					  query=query+ " WHERE STAGENAME IS NOT NULL AND TASKSTATUS = 1" ;

				  }
				
				  connection.setAutoCommit(false);
				  
				  proc=connection.prepareCall(query);
				  rs = proc.executeQuery();
				  TmWorkflowstage tmstage = null;
				  
				  while(rs.next())
				  {
					  tmstage = new TmWorkflowstage();
					  tmstage.setStagedesc(rs.getString("STAGENAME"));
					  tmstage.setStageid(rs.getInt("CURRENTSTAGEID"));
					  stagelist.add(tmstage);
				  }
				  
			}
			catch (Exception ex) {
				//logger.error(ex.getMessage();
				throw new NpdException("Exception : " + ex.getMessage(),ex);
			}
			finally
			{
				
				try {
					
						DbConnection.closeResultset(rs);
						DbConnection.closeCallableStatement(proc);
						DbConnection.freeConnection(connection);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new NpdException("Exception : " + e.getMessage(),e);
				}
			}
			return stagelist;
		
	}

	public String  getprojectPM_TL( String projectworkflowid , String rolename )throws NpdException
	{
			Connection connection =null;
			CallableStatement proc =null;
			ResultSet rs = null;
			String empName="";
			
			ArrayList<TmWorkflowstage> stagelist = new ArrayList<TmWorkflowstage>();  
			try
			{
				  connection=NpdConnection.getConnectionObject();
				
				  	  String query=null;
				  	  
				  	  if(AppConstants.PROJECT_MANAGER.equalsIgnoreCase(rolename))
				  	  {
				  		query="select DISTINCT(EMP.EMPNAME) ";
				  		query=query+ " from NPD.TM_EMPLOYEE EMP INNER JOIN NPD.TTRN_PROJECT PROJ ";
				  		query=query+ " ON EMP.NPDEMPID=PROJ.CREATEDBY INNER JOIN NPD.TTRN_PROJECTWORKFLOWDET DET  ";
				  		query=query+ " ON DET.PROJECTID=PROJ.PROJECTID  ";
				  		query=query+ " WHERE DET.PROJECTWORKFLOWID="+projectworkflowid;
				  	  }
				  	  else
				  	  {
						  	query=" SELECT DISTINCT EMPNAME FROM NPD.TTRN_PROJECTHIERARCHY ";
						  	query=query+ " INNER JOIN NPD.TM_EMPLOYEE ";
						  	query=query+ " ON ASSIGNEDTOUSERID = NPDEMPID ";
						  	query=query+ " WHERE PROJECTWORKFLOWID = "+ projectworkflowid;
						  	
						  	query=query+ " AND TASKSTAKEHOLDER = (SELECT ROLEID FROM NPD.TM_ROLES WHERE UPPER(ROLENAME) = UPPER('"+ rolename +"'))";
				  	  }
				  connection.setAutoCommit(false);
				  
				  proc=connection.prepareCall(query);
				  rs = proc.executeQuery();
				  TmWorkflowstage tmstage = null;
				  
				  while(rs.next())
				  {
					  empName = empName + rs.getString("EMPNAME") + ","; 
				  }
				  
				  if(!empName.equalsIgnoreCase(""))
				  empName = empName.substring(0, empName.length()-1);
			}
			catch (Exception ex) {
				//logger.error(ex.getMessage();
				throw new NpdException("Exception : " + ex.getMessage(),ex);
			}
			finally
			{
				
				try {
					DbConnection.closeResultset(rs);
						DbConnection.closeCallableStatement(proc);
						DbConnection.freeConnection(connection);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new NpdException("Exception : " + e.getMessage(),e);
				}
			}
			return empName;
		
	}

}
