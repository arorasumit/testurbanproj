package com.ibm.ioes.npd.hibernate.dao;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.ibm.ioes.npd.exception.NpdException;
import com.ibm.ioes.npd.hibernate.beans.TmstProjecthierarchytasksflow;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjecthierarchy;
import com.ibm.ioes.npd.model.NewIdeaDto;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.Messages;
import com.ibm.ioes.npd.utilities.PagingSorting;
import com.ibm.ioes.npd.utilities.Utility;
import com.ibm.ioes.utilities.DbConnection;

public class NewIdeaDao {

	//public static Lock lock=new ReentrantLock();
	
	public long saveIdea(Connection connection,NewIdeaDto dto) throws NpdException{
		CallableStatement cstmt =null;
		long ideaId=0;
		try
		{

			cstmt= connection.prepareCall(
									"{call NPD.P_SAVE_NEWIDEA(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			
			int parameterIndex=0;
			
			
			cstmt.setString(++parameterIndex, dto.getNameGenerator());
			cstmt.setString(++parameterIndex, dto.getMailId());
			cstmt.setString(++parameterIndex, dto.getPhoneNo());
			cstmt.setString(++parameterIndex, dto.getFunction());
			cstmt.setString(++parameterIndex, dto.getLocation());
			cstmt.setString(++parameterIndex, dto.getBriefDesc());
			cstmt.setString(++parameterIndex, dto.getBenefit());
			cstmt.setString(++parameterIndex, dto.getIndustryVertical());
			cstmt.setString(++parameterIndex, dto.getUsp());
			
			cstmt.setString(++parameterIndex, dto.getIsSimilarProductExist());
			cstmt.setString(++parameterIndex, dto.getCountry());
			cstmt.setString(++parameterIndex, dto.getOrganisation());
			cstmt.setString(++parameterIndex, dto.getPrdDescription());
			cstmt.setString(++parameterIndex, dto.getMarketSize());
			cstmt.setString(++parameterIndex, dto.getHrms_employeenumber());
			
			
			cstmt.setString(++parameterIndex, "");
			cstmt.setLong(++parameterIndex, 0);
			
			
			// Procedure returns ideaId by usig max query. If 100% accuracy is required for  generated idea id use lock by uncommenting it. 
			//lock.lock();
			boolean b=cstmt.execute();
			//lock.notifyAll();
			
			
			String msg=cstmt.getString(16);
			
			if(!msg.equals("Committed"))
			{
				ideaId=-1;	
			}
			else
			{
				ideaId=cstmt.getLong(17);
			}	
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in saveIdea method of "
					+ this.getClass().getSimpleName()+ ex.getMessage(), ex);
		}	
		finally
		{
			DbConnection.closeCallableStatement(cstmt);
		}
		return ideaId;
	}

	public NewIdeaDto loadHRMSDetails(Connection conn, String ssfId) throws NpdException{
		
		
		ResultSet rs=null;
		NewIdeaDto dto=null;
		try
		{
			String sql=" SELECT APPENDED_FULL_NAME,EMAIL_ADDRESS,MOBILE,FUNCTION,LOCATION_CODE,EMPLOYEE_NUMBER " +
						" FROM NPD.V_HRMS WHERE UPPER(SSFID)=UPPER('"+ssfId+"')";
			rs=conn.createStatement().executeQuery(sql);
			if(rs.next())
			{
				dto=new NewIdeaDto();
				dto.setNameGenerator(rs.getString("APPENDED_FULL_NAME"));
				dto.setMailId(rs.getString("EMAIL_ADDRESS"));
				dto.setPhoneNo(rs.getString("MOBILE"));
				dto.setFunction(rs.getString("FUNCTION"));
				dto.setLocation(rs.getString("LOCATION_CODE"));
				dto.setHrms_employeenumber(ssfId);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in loadHRMSDetails method of "
					+ this.getClass().getSimpleName()+ ex.getMessage(), ex);
		}	
		finally
		{
			try
			{
			DbConnection.closeResultset(rs);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return dto;
	}

	public ArrayList<NewIdeaDto> getIdeaList(Connection connection,NewIdeaDto searchDto) throws NpdException {
		
		ArrayList<NewIdeaDto> reportList = new ArrayList<NewIdeaDto>();
		Statement stmt=null;
		ResultSet rs=null;
		try{
			
			
			String sql="SELECT IDEAID, GENERATOR_NAME, MAIL_ID, PHONE_NO, FUNCTION, LOCATION, BREIEF_DESC, " +
					"BENEFIT, ISSIMILAR_PRODUCTEXIST, COUNTRY, ORGANISATION, PRD_DESCRIPTION, MARKET_SIZE, " +
					"INDUSTRY_VERT, USP, HRMS_ID, CREATEDDATE FROM NPD.V_IDEA "; 
			
			String whereCondition="";
			String condition="";
			
			condition=Utility.generateDateRelativeCondition("BETWEEN", searchDto.getFromDate(), 
											searchDto.getToDate(), "DATE(CREATEDDATE)", 
											new SimpleDateFormat(Messages.getMessageValue("calendar_entry_format")));
			whereCondition=Utility.addToCondition(whereCondition, condition);
		

			condition=Utility.generateStringLikeCondition(searchDto.getSearchEmailId(), "MAIL_ID");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			condition=Utility.generateStringLikeCondition(searchDto.getSearchName(), "GENERATOR_NAME");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			condition=Utility.generateStringLikeCondition(searchDto.getSearchOracleId(), "HRMS_ID");
			whereCondition=Utility.addToCondition(whereCondition,condition);
			
			
			if(!(whereCondition.trim().equals("")))
			{
				sql=sql+" WHERE "+whereCondition;
			}
						
			//Generating Order By clause
			String orderByColumn="";
			String ASC_DESC="DESC";
			PagingSorting pagingSorting =searchDto.getPagingSorting();
			String sortBy=pagingSorting.getSortByColumn();
			
			if("name".equals(sortBy))
			{
				orderByColumn="GENERATOR_NAME";
			}
			else if("oracleId".equals(sortBy))
			{
				orderByColumn="HRMS_ID";
			}
			else if("email".equals(sortBy))
			{
				orderByColumn="MAIL_ID";
			}
			else if("function".equals(sortBy))
			{
				orderByColumn="FUNCTION";
			}
			else if("location".equals(sortBy))
			{
				orderByColumn="LOCATION";
			}
			else if("submittedDate".equals(sortBy))
			{
				orderByColumn="CREATEDDATE";
			}
			
						
			ASC_DESC="DESC";
			String sortByOrder=pagingSorting.getSortByOrder();
			if("increment".equalsIgnoreCase(sortByOrder))
			{
				ASC_DESC="ASC";
			}
			int a=1;
			if(orderByColumn!=null && !(orderByColumn.trim().equals("")))
			{
				String FullOrderBy=" ORDER BY "+orderByColumn+" "+ASC_DESC+" ";
				sql=sql+FullOrderBy;
				
//				For paging
				if(pagingSorting.isPagingToBeDone())
				{	
					//pagingSorting.storeRecordCount(conn,sql);
					sql=pagingSorting.query_v2(sql, FullOrderBy,PagingSorting.jdbc);
					pagingSorting.setRecordCount(0);
				}
			}
			
			
			
			stmt=connection.createStatement();
			 rs=stmt.executeQuery(sql);
			while(rs.next())
			{
				if(pagingSorting.isPagingToBeDone())
				{	
					pagingSorting.setRecordCount(rs.getInt("FULL_REC_COUNT"));
				}
				
				
				NewIdeaDto dto=new NewIdeaDto();
				
				dto.setBenefit(rs.getString("BENEFIT"));
				dto.setBriefDesc(rs.getString("BREIEF_DESC"));
				dto.setCountry(rs.getString("COUNTRY"));
				dto.setCreatedDate(rs.getTimestamp("CREATEDDATE"));
				
				dto.setCreatedDateString(Utility.showDate_Report(dto.getCreatedDate()));
				
				dto.setFunction(rs.getString("FUNCTION"));
				dto.setHrms_employeenumber(rs.getString("HRMS_ID"));
				dto.setIndustryVertical(rs.getString("INDUSTRY_VERT"));
				dto.setIsSimilarProductExist(rs.getString("ISSIMILAR_PRODUCTEXIST"));
				dto.setLocation(rs.getString("LOCATION"));
				dto.setMailId(rs.getString("MAIL_ID"));
				dto.setMarketSize(rs.getString("MARKET_SIZE"));
				dto.setNameGenerator(rs.getString("GENERATOR_NAME"));
				dto.setOrganisation(rs.getString("ORGANISATION"));
				dto.setPhoneNo(rs.getString("PHONE_NO"));
				dto.setPrdDescription(rs.getString("PRD_DESCRIPTION"));
				dto.setUsp(rs.getString("USP"));
				
				
				
				
				reportList.add(dto);
			}
			  
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in getIdeaList method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in getIdeaList method of "
					+ this.getClass().getSimpleName()) ;
		}
		finally
		{
			try
			{
			DbConnection.closeResultset(rs);
			DbConnection.closeStatement(stmt);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return reportList;
	}

	public  String[] getAllAdminEmailIds(Connection conn) throws NpdException {
		String[] cc = null;
		try{
			
			
			String sql="SELECT EMAIL ,NPDEMPID,EMPNAME from NPD.V_EMPS_CC_IDEA ORDER BY EMAIL "; 
			
			
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			ArrayList<String> list=new ArrayList<String>();
			while(rs.next())
			{
				list.add(rs.getString("EMAIL"));
			}
			cc=new String[list.size()];
			cc=list.toArray(cc);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in getAllAdminEmailIds method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in getAllAdminEmailIds method of "
					+ this.getClass().getSimpleName()) ;
		}
		
		return cc;
	}

}
