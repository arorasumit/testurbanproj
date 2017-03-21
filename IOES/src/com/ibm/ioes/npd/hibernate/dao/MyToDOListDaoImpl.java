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
import java.util.StringTokenizer;

import javax.sql.rowset.serial.SerialBlob;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import com.ibm.ioes.npd.exception.NpdException;
import com.ibm.ioes.npd.hibernate.beans.MyToDoListDto;
import com.ibm.ioes.npd.hibernate.beans.PlrUploadingDto;
import com.ibm.ioes.npd.hibernate.beans.ProjectPlanReportDto;
import com.ibm.ioes.npd.hibernate.beans.TmEmployee;
import com.ibm.ioes.npd.hibernate.beans.TmNpdcategory;
import com.ibm.ioes.npd.hibernate.beans.TmProductcategory;
import com.ibm.ioes.npd.hibernate.beans.TmRoles;
import com.ibm.ioes.npd.hibernate.beans.TtrnProject;
import com.ibm.ioes.npd.model.CommonBaseModel;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.CommonUtilities;
import com.ibm.ioes.npd.utilities.Messages;
import com.ibm.ioes.npd.utilities.NpdConnection;
import com.ibm.ioes.npd.utilities.PagingSorting;
import com.ibm.ioes.npd.utilities.SendMail;
import com.ibm.ioes.npd.utilities.SendMailUtility;
import com.ibm.ioes.npd.utilities.Utility;
import com.ibm.ioes.utilities.DbConnection;

public class MyToDOListDaoImpl {
	private static final Logger logger;

	private static int iRoleId;

	private static int iUserID;

	private static int iProjectId;

	private static String iAccountId = null;
	static {
		logger = Logger.getLogger(MyToDOListDaoImpl.class);
	}

	//Fetch MyToDoList Summary
	public ArrayList<MyToDoListDto> myToDoList(MyToDoListDto searchDto)
			throws NpdException {
		iRoleId = searchDto.getRoleId();
		iUserID = searchDto.getUserId();
		iAccountId = searchDto.getSearchAccountId();
		StringBuffer sQuery = new StringBuffer();
		ResultSet rs = null;
		sQuery.append(" SELECT *");
		sQuery.append(" FROM NPD.V_CPPMYTODOLIST ");
		//sQuery.append(" Where 1=1 ");
		sQuery.append(" WHERE TASKSTAKEHOLDER = " + iRoleId
				+ "  AND ASSIGNEDTOUSERID = " + searchDto.getUserId());

		//System.out.println("QUERY-->" + sQuery.toString());
		//generating condr=tion for where 
		String whereCondition = "";

		String condition;
		if (searchDto.getSearchAccountId() != null
				&& !searchDto.getSearchAccountId().trim().equalsIgnoreCase("")) {
			iAccountId = searchDto.getSearchAccountId();
		} else {
			iAccountId = null;
		}

		condition = Utility.generateRelativeCondition("EQUAL", searchDto
				.getSeachprojectId(), "", "PROJECTID");
		whereCondition = Utility.addToCondition(whereCondition, condition);

		condition = Utility.generateStringLikeCondition(searchDto
				.getSeachprojectName(), "PROJECT_NAME");
		whereCondition = Utility.addToCondition(whereCondition, condition);

		condition = Utility.generateStringLikeCondition(searchDto
				.getSearchPriorityOfLaunch(), "LAUNCH_PRIORITY");
		whereCondition = Utility.addToCondition(whereCondition, condition);

		condition = Utility.generateStringLikeCondition(searchDto
				.getTargetmarket(), "TARGET_MARKET");
		whereCondition = Utility.addToCondition(whereCondition, condition);

		condition = Utility.generateStringLikeCondition(searchDto
				.getSearchProductBrief(), "PRODUCT_BRIEF");
		whereCondition = Utility.addToCondition(whereCondition, condition);

		condition = Utility.generateStringLikeCondition(searchDto
				.getNpscategory(), "NPDCATDESC");
		whereCondition = Utility.addToCondition(whereCondition, condition);

		condition = Utility.generateStringLikeCondition(searchDto
				.getProductcategory(), "PRODCATDESC");
		whereCondition = Utility.addToCondition(whereCondition, condition);

		if (!(whereCondition.trim().equals(""))) {
			sQuery = sQuery.append(" AND ").append(whereCondition);
		}

		//Generating Order By clause
		String orderByColumn = "";
		String ASC_DESC = "DESC";
		String sortBy = searchDto.getSortBy();
		if ("projectId".equals(sortBy)) {
			orderByColumn = "PROJECTWORKFLOWID";
		} else if ("projectName".equals(sortBy)) {
			orderByColumn = "PROJECT_NAME";
		} else if ("priorityOfLaunch".equals(sortBy)) {
			orderByColumn = "LAUNCH_PRIORITY";
		} else if ("productBrief".equals(sortBy)) {
			orderByColumn = "PRODUCT_BRIEF";
		} else if ("targetMarket".equals(sortBy)) {
			orderByColumn = "TARGET_MARKET";
		} else if ("productCategory".equals(sortBy)) {
			orderByColumn = "PRODCATDESC";
		} else if ("npdCategory".equals(sortBy)) {
			orderByColumn = "NPDCATDESC";
		} else if ("airtelPotential".equals(sortBy)) {
			orderByColumn = "AIRTEL_POTENTIAL";
		} else if ("totalMarketPotential".equals(sortBy)) {
			orderByColumn = "TOTAL_MKT_POTENTIAL";
		} else if ("capexRequirement".equals(sortBy)) {
			orderByColumn = "CAPEX_REQUIREMENT";
		} else if ("currentStage".equals(sortBy)) {
			orderByColumn = "STAGENAME";
		} else if ("currentTask".equals(sortBy)) {
			orderByColumn = "TASKNAME";
		} else if ("plannedStartDate".equals(sortBy)) {
			orderByColumn = "PLANNEDSTARTDATE";
		} else if ("plannedEndDate".equals(sortBy)) {
			orderByColumn = "PLANNEDENDDATE";
		} else if ("duration".equals(sortBy)) {
			orderByColumn = "TASKDURATION";
		}

		ASC_DESC = "DESC";
		if ("increment".equals(searchDto.getSortByOrder())) {
			ASC_DESC = "ASC";
		}

		if (orderByColumn != null && !(orderByColumn.trim().equals(""))) {
			String FullOrderBy = " ORDER BY " + orderByColumn + " " + ASC_DESC
					+ " ";
			sQuery.append(FullOrderBy);
			//For paging
			PagingSorting paging = searchDto.getPaging();
			if (paging != null) {
				try {
					Connection conn = NpdConnection.getConnectionObject();
					paging.storeRecordCount(conn, sQuery.toString());
					NpdConnection.freeConnection(conn);
				} catch (NpdException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				sQuery = new StringBuffer(paging.query(sQuery.toString(),
						FullOrderBy, "jdbc"));
			}
		}
		
		ArrayList<MyToDoListDto> dtoList = new ArrayList<MyToDoListDto>();
		Connection conn = null;
		TmProductcategory prdCategory = null;
		TtrnProject projectDetails = null;
		TmNpdcategory npdCategory = null;
		try {
			MyToDoListDto objDto = null;
			conn = NpdConnection.getConnectionObject();
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(sQuery.toString());
			while (rs.next()) {

				objDto = new MyToDoListDto();
				prdCategory = new TmProductcategory();
				projectDetails = new TtrnProject();
				npdCategory = new TmNpdcategory();

				projectDetails.setProjectid(rs.getInt("PROJECTID"));
				projectDetails.setProjectworkflowid(rs
						.getLong("PROJECTWORKFLOWID"));
				projectDetails.setProjectName(rs.getString("PROJECT_NAME"));
				projectDetails.setLaunchPriority(rs
						.getString("LAUNCH_PRIORITY"));
				projectDetails.setProductBrief(rs.getString("PRODUCT_BRIEF"));
				projectDetails.setTargetMkt(rs.getString("TARGET_MARKET"));
				prdCategory.setProdcatdesc(rs.getString("PRODCATDESC"));
				projectDetails.setPrdCategory(prdCategory);
				npdCategory.setNpdcatdesc(rs.getString("NPDCATDESC"));
				projectDetails.setNpdCategory(npdCategory);
				projectDetails.setAirtelPotential(rs
						.getString("AIRTEL_POTENTIAL"));
				projectDetails.setTotalMktPotential(rs
						.getString("TOTAL_MKT_POTENTIAL"));
				projectDetails.setCapexReq(rs.getString("CAPEX_REQUIREMENT"));
				objDto.setStageName(rs.getString("STAGENAME"));
				objDto.setTaskName(rs.getString("TASKNAME"));
				objDto.setTaskId(rs.getInt("CURRENTTASKID"));
				objDto.setStageId(rs.getInt("CURRENTSTAGEID"));
				objDto.setCurrentTaskStatus(rs.getString("CURRENTTASKSTATUS"));
				objDto.setWithCurrentOwner(rs.getString("WITHCURRENTOWNER"));
				objDto.setIsTaskMandatory(rs.getString("TASK_ISATTACHMENT"));
				objDto.setDocname(rs.getString("REFDOCNAME"));
				objDto.setProjectDetails(projectDetails);
				objDto.setRejectionAllowed(rs.getInt("TASK_REJECTIONALLOWED"));
				objDto.setTaskDuration(rs.getString("TASKDURATION"));
				objDto.setTaskstartdate(rs.getDate("TASKSTARTDATE"));
				objDto.setTaskenddate(rs.getDate("TASKENDDATE"));
				objDto.setActualtaskstartdate(rs.getDate("ACTUALTASKSTARTDATE"));
				
				dtoList.add(objDto);
			}

		} catch (SQLException sqlEx) {
			logger
					.error(sqlEx.getMessage()
							+ " SQLException occured while closing database objects in myToDoList method of "
							+ this.getClass().getSimpleName());
			throw new NpdException("SQL Exception : " + sqlEx.getMessage(),
					sqlEx);
		} catch (Exception ex) {
			logger
					.error(ex.getMessage()
							+ " Exception occured while closing database objects in myToDoList method of "
							+ this.getClass().getSimpleName());
			throw new NpdException("Exception : " + ex.getMessage(), ex);
		} finally {

			try {
				DbConnection.closeResultset(rs);
				NpdConnection.freeConnection(conn);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new NpdException("Exception : " + e.getMessage(), e);
			}
		}
		return dtoList;
	}

	public ArrayList<MyToDoListDto>  myToDoListNpdSpocMail(MyToDoListDto searchDto) throws NpdException
	{		
		iRoleId = searchDto.getRoleId();
		iUserID = searchDto.getUserId();
		iAccountId = searchDto.getSearchAccountId();
		Statement stmt=null;
		StringBuffer sQuery = new StringBuffer();
		
		sQuery.append(" SELECT *");
		sQuery.append(" FROM NPD.V_ALL_CPPMYTODOLIST ");
		//sQuery.append(" Where 1=1 ");
		sQuery.append(" WHERE TASKSTAKEHOLDER = "+ iRoleId +"  AND ASSIGNEDTOUSERID = "+ searchDto.getUserId()
				+" ORDER BY CURRENTTASKSTATUS DESC ");
		
		//System.out.println("QUERY-->"+sQuery.toString());
		//generating condr=tion for where 
		
		ResultSet rs = null;		
		ArrayList<MyToDoListDto> dtoList = new ArrayList<MyToDoListDto>(); 
		Connection conn=null;
		TmProductcategory prdCategory = null;
		TtrnProject projectDetails = null;
		TmNpdcategory npdCategory = null;
		try 
		{
			MyToDoListDto objDto=null;			
			conn=NpdConnection.getConnectionObject();
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sQuery.toString());
			while(rs.next())
			{
				
				objDto = new MyToDoListDto();
				prdCategory = new TmProductcategory();
				projectDetails = new TtrnProject();
				npdCategory = new TmNpdcategory();
				
				projectDetails.setProjectid(rs.getInt("PROJECTID"));
				projectDetails.setProjectworkflowid(rs.getLong("PROJECTWORKFLOWID"));
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
				objDto.setStageName(rs.getString("STAGENAME"));
				objDto.setTaskName(rs.getString("TASKNAME"));
				objDto.setTaskId(rs.getInt("CURRENTTASKID"));
				objDto.setStageId(rs.getInt("CURRENTSTAGEID"));
				objDto.setCurrentTaskStatus(rs.getString("CURRENTTASKSTATUS"));
				objDto.setWithCurrentOwner(rs.getString("WITHCURRENTOWNER"));
				objDto.setIsTaskMandatory(rs.getString("TASK_ISATTACHMENT"));
				objDto.setDocname(rs.getString("REFDOCNAME"));
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
				DbConnection.closeStatement(stmt);
				NpdConnection.freeConnection(conn);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new NpdException("Exception : " + e.getMessage(),e);
			}
		}
		return dtoList;
	}
	public void updateTask(MyToDoListDto tranDto) {
		StringBuffer sQuery = new StringBuffer();
		Connection connection = null;
		String Msg = "";
		CallableStatement proc = null;
		try {
			StringTokenizer splitString = new StringTokenizer(tranDto
					.getTaskIdList().toString(), ",");
			String[] arr = new String[splitString.countTokens()];

			StringTokenizer splitStringStage = new StringTokenizer(tranDto
					.getStageIdList().toString(), ",");
			String[] arrStage = new String[splitStringStage.countTokens()];

			String userid = null;
			connection = NpdConnection.getConnectionObject();
			connection.setAutoCommit(false);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			tranDto.setCallBackDate(sdf.format(new Date(System
					.currentTimeMillis())));

			String stringDate = tranDto.getCallBackDate() + " 00:00:00";
			DateFormat formater = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			java.util.Date parsedUtilDate = formater.parse(stringDate);
			
			String csvUserIds=null;
			String csvNextTaskIds=null;
			int flag=0;
			for (int i = 0; i < arr.length; i++)

			{
				java.sql.Date sqltDate = new java.sql.Date(parsedUtilDate
						.getTime());
				proc = connection
						.prepareCall(" {CALL NPD.P_INSERT_TRNPROJECTACTIONDETAILS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)} ");
				tranDto.setStageId(Integer.parseInt(splitStringStage
						.nextToken()));
				tranDto.setTaskId(Integer.parseInt(splitString.nextToken()));
				proc.setLong(1, tranDto.getProjectId());
				proc.setLong(2, tranDto.getStageId());
				proc.setLong(3, tranDto.getTaskId());
				proc.setString(4, "");
				proc.setLong(5, tranDto.getUserId()); ///Task Owner	
				proc.setDate(6, sqltDate);
				proc.setString(7, tranDto.getTaskapproveComments());
				proc.setLong(8, 0);//IsDocAttached()
				proc.setLong(9, 1); //Action Type i.e Whether Approved or Not
				proc.setLong(10, 20);//Next Stage Id
				proc.setLong(11, 34);//Next Task Id
				proc.setLong(12, tranDto.getUserId()); //Created by 
				proc.setLong(13, tranDto.getUserId()); //Created by
				proc.setLong(14, 1); //Is Active

				//Uploaded document related setting
				java.sql.Blob blob = null;

				FormFile doc = tranDto.getAttachedDoc();
				if (!"".equals(doc.getFileName())) {
					blob = new SerialBlob(new SerialBlob(tranDto
							.getAttachedDoc().getFileData()));
					proc.setBlob(15, blob);
					proc.setLong(16, 1);
					proc.setString(17, doc.getFileName());
					proc.setString(18, doc.getFileName().substring(
							doc.getFileName().lastIndexOf(".") + 1));
				} else {
					Blob blob2 = null;
					proc.setBlob(15, blob2);
					proc.setLong(16, 0);
					proc.setString(17, null);
					proc.setString(18, null);
				}
				if (tranDto.getRfiFrom() != null) {
					proc.setLong(19, Integer.parseInt(tranDto.getRfiFrom()));
				} else {
					proc.setInt(19, 0);
				}
				//Email Related Setting
				proc.setLong(20, 0); //Is Mail
				proc.setString(21, ""); // To Email Id
				proc.setString(22, ""); // To CC Email Id
				proc.setString(23, ""); // To BccEmail Id
				proc.setString(24, ""); // To Email Id
				proc.setString(25, ""); // To Body
				proc.setString(26, ""); // To Email Header
				proc.setString(27, ""); // To Email Footer
				//
				proc.setString(28, "");
				proc.setString(29, "");
				proc.setString(30, "");
				proc.setString(31, "");
				proc.execute();
				System.out.println(proc.getInt(28));
				System.out.println(proc.getString(29));

				Msg = proc.getString(28);
				//userid = proc.getString(30);
				//tranDto.setNextTaskId(proc.getInt(31));
				
				csvUserIds=proc.getString(30);
				csvNextTaskIds=proc.getString(31);
				//Verifiying that exception accured or not in Stord Proc
				if (proc.getString(28).equals("66666")) {
					connection.rollback();
					flag=0;
				} else {
					connection.commit();
					flag=1;
					
				}
			}

			if (flag==1 && csvUserIds != null && !"".equals(csvUserIds) && csvNextTaskIds !=null && !"".equals(csvNextTaskIds)) {
				
				String [] nextTaskIds=csvNextTaskIds.split(",");
				String [] nextUserIds=csvUserIds.split(",");
				
				for(int i=0;i<nextTaskIds.length;i++)
				{
					try
					{
						if(nextTaskIds[i]==null || "".equals(nextTaskIds[i].trim()) 
									|| nextUserIds[i]==null || "".equals(nextUserIds[i].trim()))
						{
							continue;
						}
						userid=nextUserIds[i];
						
						tranDto.setNextTaskId(Integer.parseInt(nextTaskIds[i]));
						MyToDoListDto dto= getTaskDetail(tranDto);
						String[] mailid = { userid };
						SendMail objSendMail = new SendMail();
						
						//fetch Details of task
						StringBuffer sb=new StringBuffer();
						sb.append("<HTML><BODY>");
						sb.append(Messages.getMessageValue("Mail_Header")+"<BR>");
						sb.append("<BR>A Task has been Assigned :");
						sb.append("<BR>Task Name :"+dto.getTaskName());
						sb.append("<BR>Stage Name :"+dto.getStageName());
						sb.append("<BR>Project Id :"+dto.getProjectDetails().getProjectid());
						sb.append("<BR>Project Name :"+dto.getProjectDetails().getProjectName());
						sb.append("<BR><BR>"+Messages.getMessageValue("Mail_Footer")+"<BR>");
						sb.append("</BODY></HTML>");
							
							objSendMail.postMail(mailid, null, null, "Task Assigned", sb.toString(), null, null);	
						
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
						logger
						.error(ex.getMessage()
								+ " Mail sending failed"
								+ this.getClass().getSimpleName());
						System.err.println("Mail sending failed for todolist approval");
					}
				}
				
				/*MyToDoListDto dto= getTaskDetail(tranDto);
				try
				{
				//String taskName = getTaskDetail(tranDto);
				
				String[] mailid = { userid };
				SendMail objSendMail = new SendMail();
				
				//fetch Details of task
				StringBuffer sb=new StringBuffer();
				sb.append("<HTML><BODY>");
				sb.append(Messages.getMessageValue("Mail_Header")+"<BR>");
				sb.append("<BR>A Task has been Assigned :");
				sb.append("<BR>Task Name :"+dto.getTaskName());
				sb.append("<BR>Stage Name :"+dto.getStageName());
				sb.append("<BR>Project Id :"+dto.getProjectDetails().getProjectid());
				sb.append("<BR>Project Name :"+dto.getProjectDetails().getProjectName());
				sb.append("<BR><BR>"+Messages.getMessageValue("Mail_Footer")+"<BR>");
				sb.append("</BODY></HTML>");
					
					objSendMail.postMail(mailid, null, null, "Task Assigned", sb.toString(), null, null);	
				}
					catch(Exception ex)
					{
						ex.printStackTrace();
						logger
						.error(ex.getMessage()
								+ " Mail sending failed"
								+ this.getClass().getSimpleName());
						System.err.println("Mail sending failed for todolist approval");
					}*/
					
				
				
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
				DbConnection.closeCallableStatement(proc);
				NpdConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}

	}

public int rejectTask(MyToDoListDto tranDto)
	{
		int status=-1;
		StringBuffer sQuery = new StringBuffer();
		 Connection connection=null;
		 String Msg="";
		 CallableStatement proc =null;
			try
			{
				StringTokenizer splitString = new StringTokenizer(tranDto.getTaskIdList().toString() ,",");
				String[] arr=new String[splitString.countTokens()];

				StringTokenizer splitStringStage = new StringTokenizer(tranDto.getStageIdList().toString() ,",");
				String[] arrStage=new String[splitStringStage.countTokens()];

				String userid = null;
				connection=NpdConnection.getConnectionObject();
				connection.setAutoCommit(false);
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				tranDto.setCallBackDate(sdf.format(new Date(System.currentTimeMillis())));
				
				String stringDate=tranDto.getCallBackDate()+" 00:00:00";
				DateFormat formater = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				java.util.Date parsedUtilDate = formater.parse(stringDate);

				for (int i = 0; i < arr.length; i++) 
				
				{
						java.sql.Date sqltDate= new java.sql.Date(parsedUtilDate.getTime());
						proc=connection.prepareCall(" {CALL NPD.P_INSERT_TRNPROJECTACTIONDETAILS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)} ");
						tranDto.setStageId(Integer.parseInt(splitStringStage.nextToken()));
						tranDto.setTaskId(Integer.parseInt(splitString.nextToken()));
						proc.setLong(1,tranDto.getProjectId());
						proc.setLong(2,tranDto.getStageId());
						proc.setLong(3,tranDto.getTaskId());
						proc.setString(4,"");
						proc.setLong(5,tranDto.getUserId()); ///Task Owner	
						proc.setDate(6,sqltDate);
						proc.setString(7,tranDto.getTaskrejectComments());
						proc.setLong(8,0);//IsDocAttached()
						proc.setLong(9,2); //Action Type i.e Whether Approved or Not
						proc.setLong(10,20);//Next Stage Id
						proc.setLong(11,34);//Next Task Id
						proc.setLong(12,tranDto.getUserId()); //Created by 
						proc.setLong(13,tranDto.getUserId()); //Created by
						proc.setLong(14,1); //Is Active
						
						//Uploaded document related setting
						java.sql.Blob blob=null;
						
						FormFile doc=tranDto.getAttachedDoc();
						if(doc!=null && !"".equals(doc.getFileName()))
						{
						blob=new SerialBlob( new SerialBlob(tranDto.getAttachedDoc().getFileData())); 
							proc.setBlob(15,blob);
							proc.setLong(16,1);
							proc.setString(17,doc.getFileName());
							proc.setString(18,doc.getFileName().substring(doc.getFileName().lastIndexOf(".")+1));
						}
						else
						{
							Blob blob2 = null;
							proc.setBlob(15,blob2);
							proc.setLong(16,0);
							proc.setString(17,null);
							proc.setString(18,null);
						}
						if(tranDto.getRfiFrom()!=null)
						{
							proc.setLong(19,Integer.parseInt(tranDto.getRfiFrom()));
						}
						else
						{
							proc.setInt(19,0);
						}				
						//Email Related Setting
						proc.setLong(20,0); //Is Mail
						proc.setString(21,""); // To Email Id
						proc.setString(22,""); // To CC Email Id
						proc.setString(23,""); // To BccEmail Id
						proc.setString(24,""); // To Email Id
						proc.setString(25,""); // To Body
						proc.setString(26,""); // To Email Header
						proc.setString(27,""); // To Email Footer
						//
						proc.setString(28,"");
						proc.setString(29,"");
						proc.setString(30,"");
						proc.setString(31,"");
						proc.execute();
						System.out.println(proc.getInt(28));
						System.out.println(proc.getString(29));
						
						Msg=proc.getString(28);
						userid = proc.getString(30);
						
						//Verifiying that exception accured or not in Stord Proc
						if(proc.getString(28).equals("66666")){
						connection.rollback();	
						}else{
						connection.commit();	
						status=1;
						}
			  }
				
				
				/*if (userid!=null)
				{
					String taskName = getTaskDetail(tranDto); 
					String[] mailid = {userid};
					SendMail objSendMail = new SendMail();
					objSendMail.postMail(mailid, null, null, "Task Assigned", "A Task Has been Assigned " + taskName, null, null);
				}*/

				
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
			
			return status;
		
	}
	public void updateRFI(MyToDoListDto tranDto) {
		StringBuffer sQuery = new StringBuffer();
		Connection connection = null;
		String Msg = "";
		CallableStatement proc = null;
		try {
			StringTokenizer splitString = new StringTokenizer(tranDto
					.getTaskIdList().toString(), ",");
			String[] arr = new String[splitString.countTokens()];

			StringTokenizer splitStringStage = new StringTokenizer(tranDto
					.getStageIdList().toString(), ",");
			String[] arrStage = new String[splitStringStage.countTokens()];
			String userid = null;

			connection = NpdConnection.getConnectionObject();
			connection.setAutoCommit(false);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			tranDto.setCallBackDate(sdf.format(new Date(System
					.currentTimeMillis())));
			String stringDate = tranDto.getCallBackDate() + " 00:00:00";
			DateFormat formater = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			java.util.Date parsedUtilDate = formater.parse(stringDate);

			for (int i = 0; i < arr.length; i++)

			{
				java.sql.Date sqltDate = new java.sql.Date(parsedUtilDate
						.getTime());
				proc = connection
						.prepareCall(" {CALL NPD.P_INSERT_TRNPROJECTACTIONDETAILS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)} ");
				tranDto.setStageId(Integer.parseInt(splitStringStage
						.nextToken()));
				tranDto.setTaskId(Integer.parseInt(splitString.nextToken()));
				proc.setLong(1, tranDto.getProjectId());
				proc.setLong(2, tranDto.getStageId());
				proc.setLong(3, tranDto.getTaskId());
				proc.setString(4, "");
				proc.setLong(5, 1); ///Task Owner	
				proc.setDate(6, sqltDate);
				proc.setString(7, tranDto.getTaskapproveComments());
				proc.setLong(8, 0);//IsDocAttached()
				proc.setLong(9, 3); //Action Type i.e Whether Approved or Not
				proc.setLong(10, 20);//Next Stage Id
				proc.setLong(11, 34);//Next Task Id
				proc.setLong(12, 1); //Created by 
				proc.setLong(13, 1); //Created by
				proc.setLong(14, 1); //Is Active

				//Uploaded document related setting
				java.sql.Blob blob = null;

				FormFile doc = tranDto.getAttachedDoc();
				if (!"".equals(doc.getFileName())) {
					blob = new SerialBlob(new SerialBlob(tranDto
							.getAttachedDoc().getFileData()));
					proc.setBlob(15, blob);
					proc.setLong(16, 1);
					proc.setString(17, doc.getFileName());
					proc.setString(18, doc.getFileName().substring(
							doc.getFileName().lastIndexOf(".") + 1));
				} else {
					Blob blob2 = null;
					proc.setBlob(15, blob2);
					proc.setLong(16, 0);
					proc.setString(17, null);
					proc.setString(18, null);
				}
				if (!tranDto.getEmployeeId().equals(null)
						&& !tranDto.getEmployeeId().equalsIgnoreCase("-1")) {
					proc.setLong(19, Integer.parseInt(tranDto.getEmployeeId()));
				} else {
					proc.setInt(19, 0);
				}
				//Email Related Setting
				proc.setLong(20, 0); //Is Mail
				proc.setString(21, ""); // To Email Id
				//	TODO Change Made BY Sumit on 09-Mar-2010 (Start) Pending RFI Should Be According to role
				proc.setString(22, tranDto.getRoleid()); // To CC Email Id //CCEMAIL ID is used for Role in RFI
				// Change Made BY Sumit on 09-Mar-2010 (End)
				proc.setString(23, ""); // To BccEmail Id
				proc.setString(24, ""); // To Email Id
				proc.setString(25, ""); // To Body
				proc.setString(26, ""); // To Email Header
				proc.setString(27, ""); // To Email Footer
				//
				proc.setString(28, "");
				proc.setString(29, "");
				proc.setString(30, "");
				proc.setString(31, "");
				proc.execute();
				System.out.println(proc.getInt(28));
				System.out.println(proc.getString(29));

				Msg = proc.getString(28);
				userid = proc.getString(30);
				
				//Verifiying that exception accured or not in Stord Proc
				if (proc.getString(28).equals("66666")) {
					connection.rollback();
				} else {
					connection.commit();

				}
			}

			if (tranDto.getEmployeeId() != null && !"".equals(tranDto.getEmployeeId())) {
				
				//String taskName = getTaskDetail(tranDto);
				//String[] mailid = { userid };
				//SendMail objSendMail = new SendMail();
				/*objSendMail.postMail(mailid, null, null, "Task Assigned",
						"A Task Has been Assigned " + taskName, null, null);*/
				
				
				try
				{
				ResultSet rs=connection.createStatement().executeQuery("SELECT  EMAIL FROM NPD.TM_EMPLOYEE WHERE NPDEMPID="+tranDto.getEmployeeId());
				int flag=0;
				if(rs.next())
				{
					userid=rs.getString("EMAIL");
					flag=1;
				}
				else
				{
					flag=0;
				}
					
				if(flag==1)
				{
					tranDto.setNextTaskId(tranDto.getTaskId());
				MyToDoListDto dto= getTaskDetail(tranDto);
				String[] mailid = { userid };
				SendMail objSendMail = new SendMail();
				
				//fetch Details of task
				StringBuffer sb=new StringBuffer();
				sb.append("<HTML><BODY>");
				sb.append(Messages.getMessageValue("Mail_Header")+"<BR>");
				sb.append("<BR>A RFI has been Raised :");
				sb.append("<BR>Task Name :"+dto.getTaskName());
				sb.append("<BR>Stage Name :"+dto.getStageName());
				sb.append("<BR>Project Id :"+dto.getProjectDetails().getProjectid());
				sb.append("<BR>Project Name :"+dto.getProjectDetails().getProjectName());
				sb.append("<BR><BR>"+Messages.getMessageValue("Mail_Footer")+"<BR>");
				sb.append("</BODY></HTML>");
					
					objSendMail.postMail(mailid, null, null, "RFI Raised",sb.toString(), null, null);	
				}
				rs.close();
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
					logger
					.error(ex.getMessage()
							+ " Mail sending failed for rfi"
							+ this.getClass().getSimpleName());
					System.err.println("Mail sending failed for rfi");
				}
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
				DbConnection.closeCallableStatement(proc);
				NpdConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}

	}

	public MyToDoListDto alreadyMappedStakeHolder(MyToDoListDto searchDto)
			throws NpdException {
		iRoleId = searchDto.getRoleId();
		iUserID = searchDto.getUserId();

		StringBuffer sQuery = new StringBuffer();
		Connection connection = null;
		String employeeId = "";
		String[] employeeIdList = null;
		MyToDoListDto objDto = new MyToDoListDto();
		ResultSet rs = null;
		Statement stmt =null;
		try {

			sQuery
					.append(" SELECT DISTINCT ASSIGNEDTOUSERID , EMPNAME FROM NPD.TTRN_PROJECTHIERARCHY");
			sQuery.append(" INNER JOIN NPD.TM_EMPLOYEE ");
			sQuery.append(" ON NPD.TM_EMPLOYEE.NPDEMPID = ASSIGNEDTOUSERID and NPD.TM_EMPLOYEE.ISACTIVE=1");
			sQuery.append(" WHERE PROJECTWORKFLOWID = ( ");
			sQuery.append("             SELECT PROJECTWORKFLOWID ");
			sQuery
					.append(" FROM NPD.TTRN_PROJECTWORKFLOWDET WHERE PROJECTID = "
							+ Integer.parseInt(searchDto.getSeachprojectId())
							+ " AND ISACTIVE =1 ");
			sQuery.append(" ) ");

			
			//Connection conn = null;
			connection = NpdConnection.getConnectionObject();
			stmt= connection.createStatement();
			rs = stmt.executeQuery(sQuery.toString());

			int iCount = 0;
			//rs=NpdConnection.getResultSet(sQuery.toString());
			TmEmployee tmemployee = null;

			ArrayList<TmEmployee> empLst = new ArrayList<TmEmployee>();
			while (rs.next()) {
				tmemployee = new TmEmployee();
				tmemployee.setNpdempid(rs.getInt("ASSIGNEDTOUSERID"));
				tmemployee.setEmpname(rs.getString("EMPNAME"));
				empLst.add(tmemployee);
			}

			objDto.setTmployeelst(empLst);
		}

		catch (SQLException sqlEx) {
			logger
					.error(sqlEx.getMessage()
							+ " SQLException occured while closing database objects in myToDoList method of "
							+ this.getClass().getSimpleName());
			throw new NpdException("SQL Exception : " + sqlEx.getMessage(),
					sqlEx);
		} catch (Exception ex) {
			logger
					.error(ex.getMessage()
							+ " Exception occured while closing database objects in myToDoList method of "
							+ this.getClass().getSimpleName());
			throw new NpdException("Exception : " + ex.getMessage(), ex);
		} finally {

			try {
				DbConnection.closeResultset(rs);
				DbConnection.closeStatement(stmt);
				NpdConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new NpdException("Exception : " + e.getMessage(), e);
			}
		}
		return objDto;

	}

	public byte[] DownloadFile(MyToDoListDto objProjectPlan)
			throws NpdException {
		Connection connection = null;
		CallableStatement proc = null;
		ResultSet rsViewProjectPlanDetail = null;
		byte[] fileByte = null;

		try {
			connection = NpdConnection.getConnectionObject();

			String query = null;
			query = " SELECT REFDOC FROM NPD.V_CPPMYTODOLIST ";
			//generating condr=tion for where 
			String whereCondition = "";
			String condition;
			if (objProjectPlan.getSearchtaskid() != null) {
				condition = Utility.generateIntCondition(Integer
						.parseInt(objProjectPlan.getSearchtaskid()),
						"CURRENTTASKID");
				whereCondition = Utility.addToCondition(whereCondition,
						condition);
			}

			if (objProjectPlan.getProjectworkflowid() != null) {
				condition = Utility.generateIntCondition(Integer
						.parseInt(objProjectPlan.getProjectworkflowid()),
						"PROJECTWORKFLOWID");
				whereCondition = Utility.addToCondition(whereCondition,
						condition);
			}

			if (!(whereCondition.trim().equals(""))) {
				query = query + " WHERE " + whereCondition;
			}
			connection.setAutoCommit(false);

			proc = connection.prepareCall(query);
			//proc.setLong(1,objProjectPlan.getTaskid());
			rsViewProjectPlanDetail = proc.executeQuery();
			while (rsViewProjectPlanDetail.next()) {
				CommonBaseModel objModel = new CommonBaseModel();
				fileByte = objModel.blobToByteArray(rsViewProjectPlanDetail
						.getBlob("REFDOC"));
			}
			//			  return fileByte;
		} catch (SQLException sqlEx) {
			logger
					.error(sqlEx.getMessage()
							+ " SQLException occured while closing database objects in myToDoList method of "
							+ this.getClass().getSimpleName());
			throw new NpdException("SQL Exception : " + sqlEx.getMessage(),
					sqlEx);
		} catch (Exception ex) {
			logger
					.error(ex.getMessage()
							+ " Exception occured while closing database objects in myToDoList method of "
							+ this.getClass().getSimpleName());
			throw new NpdException("Exception : " + ex.getMessage(), ex);
		} finally {

			try {
				DbConnection.closeResultset(rsViewProjectPlanDetail);
				DbConnection.closeCallableStatement(proc);
				NpdConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new NpdException("Exception : " + e.getMessage(), e);
			}
		}
		return fileByte;
	}

	public MyToDoListDto getTaskDetail(MyToDoListDto objProjectPlan)
			throws NpdException {
		MyToDoListDto dto=null;
		Connection connection = null;
		CallableStatement proc = null;
		ResultSet rsViewProjectPlanDetail = null;
		byte[] fileByte = null;
		String taskname = null;
		try {
			connection = NpdConnection.getConnectionObject();

			String query = null;
			query = " SELECT * FROM NPD.V_CPPMYTODOLIST WHERE CURRENTTASKID = "
					+ objProjectPlan.getNextTaskId();
			//generating condr=tion for where 
			String whereCondition = "";

			if (!(whereCondition.trim().equals(""))) {
				query = query + " WHERE " + whereCondition;
			}
			connection.setAutoCommit(false);

			proc = connection.prepareCall(query);
			//proc.setLong(1,objProjectPlan.getTaskid());
			rsViewProjectPlanDetail = proc.executeQuery();
			while (rsViewProjectPlanDetail.next()) {
				
				taskname = rsViewProjectPlanDetail.getString("TASKNAME");
				
				dto=new MyToDoListDto();
				dto.setStageName(rsViewProjectPlanDetail.getString("STAGENAME"));
				dto.setTaskName(rsViewProjectPlanDetail.getString("TASKNAME"));
				
				TtrnProject project=new TtrnProject();
				project.setProjectid(rsViewProjectPlanDetail.getLong("PROJECTID"));
				project.setProjectName(rsViewProjectPlanDetail.getString("PROJECT_NAME"));
				dto.setProjectDetails(project);
			}

			//			  return fileByte;
		} catch (SQLException sqlEx) {
			logger
					.error(sqlEx.getMessage()
							+ " SQLException occured while closing database objects in myToDoList method of "
							+ this.getClass().getSimpleName());
			throw new NpdException("SQL Exception : " + sqlEx.getMessage(),
					sqlEx);
		} catch (Exception ex) {
			logger
					.error(ex.getMessage()
							+ " Exception occured while closing database objects in myToDoList method of "
							+ this.getClass().getSimpleName());
			throw new NpdException("Exception : " + ex.getMessage(), ex);
		} finally {

			try {
				DbConnection.closeResultset(rsViewProjectPlanDetail);
				DbConnection.closeCallableStatement(proc);
				NpdConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new NpdException("Exception : " + e.getMessage(), e);
			}
		}
		return dto;
	}

	public ArrayList<TmRoles> getRoleMappedWithEmployee(String empid)
			throws Exception {
		ArrayList<TmRoles> employeeList = new ArrayList<TmRoles>();
		Connection connection = null;
		CallableStatement proc = null;
		ResultSet rs = null;
		TmRoles tmrole = null;
		try {

			String sql = "SELECT NPD.TM_ROLES.ROLEID , ROLENAME from NPD.TM_ROLEEMPMAPPING";
			sql = sql + " INNER JOIN NPD.TM_ROLES ";
			sql = sql
					+ " ON NPD.TM_ROLEEMPMAPPING.ROLEID = NPD.TM_ROLES.ROLEID ";
			sql = sql + " WHERE NPDEMPID = " + empid;
			connection = NpdConnection.getConnectionObject();
			connection.setAutoCommit(false);

			proc = connection.prepareCall(sql);
			//proc.setLong(1,objProjectPlan.getTaskid());
			rs = proc.executeQuery();
			while (rs.next()) {
				tmrole = new TmRoles();
				tmrole.setRoleid(rs.getInt("ROLEID"));
				tmrole.setRolename(rs.getString("ROLENAME"));
				employeeList.add(tmrole);

			}
		} catch (SQLException sqlEx) {
			logger
					.error(sqlEx.getMessage()
							+ " SQLException occured while closing database objects in myToDoList method of "
							+ this.getClass().getSimpleName());
			throw new NpdException("SQL Exception : " + sqlEx.getMessage(),
					sqlEx);
		} catch (Exception ex) {
			logger
					.error(ex.getMessage()
							+ " Exception occured while closing database objects in myToDoList method of "
							+ this.getClass().getSimpleName());
			throw new NpdException("Exception : " + ex.getMessage(), ex);
		} finally {

			try {
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(proc);
				NpdConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new NpdException("Exception : " + e.getMessage(), e);
			}
		}
		return employeeList;
	}

	public int getMyToListCount(String userId, String roleId)
			throws NpdException {
		Connection connection = null;
		CallableStatement proc = null;
		ResultSet resultSet = null;
		int taskPending_mytodoList = 0;
		try {
			connection = NpdConnection.getConnectionObject();

			StringBuffer query = new StringBuffer();

			query.append(" SELECT COUNT(*) AS PENDING_MYTODOLIST_COUNT");
			query.append(" FROM NPD.V_CPPMYTODOLIST ");
			query.append(" WHERE TASKSTAKEHOLDER = " + roleId
					+ "  AND ASSIGNEDTOUSERID = " + userId);

			//System.out.println("QUERY-->" + query.toString());
			connection.setAutoCommit(false);

			proc = connection.prepareCall(query.toString());
			// proc.setLong(1,objProjectPlan.getTaskid());
			resultSet = proc.executeQuery();
			while (resultSet.next()) {
				taskPending_mytodoList = resultSet
						.getInt("PENDING_MYTODOLIST_COUNT");
			}

			// return fileByte;
		} catch (SQLException sqlEx) {
			logger
					.error(sqlEx.getMessage()
							+ " SQLException occured while closing database objects in myToDoList method of "
							+ this.getClass().getSimpleName());
			throw new NpdException("SQL Exception : " + sqlEx.getMessage(),
					sqlEx);
		} catch (Exception ex) {
			logger
					.error(ex.getMessage()
							+ " Exception occured while closing database objects in myToDoList method of "
							+ this.getClass().getSimpleName());
			throw new NpdException("Exception : " + ex.getMessage(), ex);
		} finally {

			try {
				DbConnection.closeResultset(resultSet);
				DbConnection.closeCallableStatement(proc);
				NpdConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new NpdException("Exception : " + e.getMessage(), e);
			}
		}
		return taskPending_mytodoList;
	}

	public ArrayList<PlrUploadingDto> MailTaskPendingForApproval() {
		Connection connection = null;
		String Msg = "";
		CallableStatement proc = null;
		ResultSet rs = null;
		boolean isSaved = false;
		Hashtable htValue = new Hashtable();
		StringBuffer sQuery = new StringBuffer();
		PlrUploadingDto objDto = null;
		ArrayList<PlrUploadingDto> dtoList = new ArrayList<PlrUploadingDto>();
		try {

			connection = NpdConnection.getConnectionObject();
			connection.setAutoCommit(false);

			
			CallableStatement cstmt=connection.prepareCall("{call NPD.TASKDELAY_ESCALATION()}");
			rs=cstmt.executeQuery();
			
			TmEmployee tmployee = null;
			TtrnProject projectDetails = null;
			SendMail objSendMail = new SendMail();
			String subject = "Task Pending For Approval";
			while (rs.next()) {
				
				try
				{
				String[] to = { rs.getString("LVLEMPEMAIL") };
				String[] cc = { rs.getString("EMAIL") };
				
				String log_emailId=rs.getString("LVLEMPEMAIL");
				if(log_emailId==null || "".equals(log_emailId))
				{
					String logstr=new Date()+" :No email id present for employee : "+rs.getString("LVLEMPNAME")+" as he is defined  " +
							"the escalation for name:"+rs.getString("EMPNAME")+"   or No Escalation defined for name:"+rs.getString("EMPNAME")+
							"    Project id:"+rs.getString("PROJECTID");
					System.err.println(logstr);
					logger.error(logstr);
					continue;
				}
				subject = "Task Pending For Approval";
				String escType=rs.getString("ESCL_TYPE");
				if(AppConstants.ESCALATION_LEVEL_1.equals(escType))
				{
					subject=subject+" (Escalation Level 1)";
				}
				else if(AppConstants.ESCALATION_LEVEL_2.equals(escType))
				{
					subject=subject+" (Escalation Level 2)";
				}
				
				String message = "";
				message = "<HTML><BODY>Dear " + rs.getString("LVLEMPNAME");
						
				message = message + "<BR><BR>Following Task is Pending For Approval ";
				message = message + "<BR>Task Name : "+rs.getString("TASKNAME");
				message = message + "<BR>Project Name : "+rs.getString("PROJECT_NAME");
				message = message + "<BR>Project Id : "+rs.getString("PROJECTID");
				message = message + "<BR>Owner Name : "+rs.getString("EMPNAME");
				message = message + "<BR>With Current (in Days) : "+rs.getString("WITHCURRENTOWNER");
				message = message + "<BR><BR>"+Messages.getMessageValue("Mail_Footer");
				
				/*
						+ rs.getString("PROJECT_NAME") + " For Last "
						+ rs.getString("WITHCURRENTOWNER") +  " Days With "+ rs.getString("EMPNAME")
						+"<BR><BR>"+Messages.getMessageValue("Mail_Footer");*/
				
				/*message = message + "<BR><BR>Task Pending For Approval "
						+ rs.getString("PROJECT_NAME") + " For Last "
						+ rs.getString("WITHCURRENTOWNER") +  " Days With "+ rs.getString("EMPNAME")
						+"<BR><BR>"+Messages.getMessageValue("Mail_Footer");*/
				
				//System.err.println("Mail for todolist escalation:to[0]"+to[0]+" cc[0]:"+cc[0]+" message:"+message);
				objSendMail.postMail(to, cc, null, subject, message, null,null);
				}
				catch(Exception e)
				{
					e.printStackTrace();
					try
					{
					String log_mess = null;
					
					log_mess = "<HTML><BODY>Dear " + rs.getString("LVLEMPNAME");
					
					log_mess = log_mess + "<BR><BR>Following Task is Pending For Approval ";
					log_mess = log_mess + "<BR>Task Name : "+rs.getString("TASKNAME");
					log_mess = log_mess + "<BR>Project Name : "+rs.getString("PROJECT_NAME");
					log_mess = log_mess + "<BR>Project Id : "+rs.getString("PROJECTID");
					log_mess = log_mess + "<BR>Owner Name : "+rs.getString("EMPNAME");
					log_mess = log_mess + "<BR>With Current (in Days) : "+rs.getString("WITHCURRENTOWNER");
					log_mess = log_mess + "<BR><BR>"+Messages.getMessageValue("Mail_Footer");
					String logstr=new Date()+" Error: Mail could" +
							"not be sent . Message was:"+log_mess;
			
					System.err.println(logstr);
					logger.error(logstr);
					logger.error(e);
					}
					catch(Exception e1)
					{
						e1.printStackTrace();
					}
				}

			}

		}

		catch (Exception ex) {
			ex.printStackTrace();
			logger
					.error(ex.getMessage()
							+ " Exception occured while send mails in MailTaskPendingForApproval method of "
							+ this.getClass().getSimpleName());
		} finally {
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

}
