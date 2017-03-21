package com.ibm.ioes.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.ibm.ioes.forms.BCPAddressDto;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.PagingSorting;
import com.ibm.ioes.utilities.Utility;

public class BCPAddressDao extends CommonBaseDao
{
	public static String sqlGetAccount = "{call IOE.spGetAccount(?)}";//To Fetch All Account List from Database
	public static String sqlGetBCP = "{ call IOE.SP_GET_BCP( ?,? ) }";//To Fetch All BCP List from Database
	public static String sqlGetBCPDetails = "{ call IOE.SP_GET_BCP_DETAILS( ?,?,?,?,?,?,? ) }";//To Fetch All BCP List from Database
	public static String sqlGetBCPDetailById = "{ call IOE.SP_GET_BCP_DETAIL_BY_ID( ?,?,?) }";//To Fetch All BCP List from Database
	
	//Method used for Fetching All Accounts
	public ArrayList<BCPAddressDto> getAccountDetails(BCPAddressDto objDto) throws Exception
	{
		//Added by Nagarjuna
		String methodName="getAccountDetails",  msg="";
		boolean logToFile=true, logToConsole=true;
		//End Nagarjuna
		Connection connection =null;
		CallableStatement getAllAccountsReport =null;
		ResultSet rsAccountDetails = null;
		ArrayList<BCPAddressDto> listAccountDetails = new ArrayList<BCPAddressDto>();
		BCPAddressDto bcpDetailDto = null;
		
		try
		{
			connection=DbConnection.getConnectionObject();
			getAllAccountsReport= connection.prepareCall(sqlGetAccount);
			String searchAcc=objDto.getSearchAccount();
			if (searchAcc==null)
			{
				searchAcc="";
			}
			getAllAccountsReport.setString(1,searchAcc);
			rsAccountDetails = getAllAccountsReport.executeQuery();
			while(rsAccountDetails.next())
			{
				bcpDetailDto =  new BCPAddressDto();
				bcpDetailDto.setAccountID(""+rsAccountDetails.getInt("accountID"));
				bcpDetailDto.setAccountName(rsAccountDetails.getString("accountName"));
				bcpDetailDto.setAccphoneNo(rsAccountDetails.getInt("PhoneNo"));
				bcpDetailDto.setLob(rsAccountDetails.getString("LOB"));
				bcpDetailDto.setAccountManager(rsAccountDetails.getString("AccountManager"));
				bcpDetailDto.setProjectManager(rsAccountDetails.getString("ProjManager"));
				bcpDetailDto.setSpFirstname(rsAccountDetails.getString("SPFirstname"));
				bcpDetailDto.setSpLastName(rsAccountDetails.getString("SLastName"));
				//bcpDetailDto.setSpLPhno(rsAccountDetails.getInt("SLPhno"));//changed by kalpana from long to string for bug id HYPR11042013001
				bcpDetailDto.setSpLPhno(rsAccountDetails.getString("SLPhno"));
				bcpDetailDto.setSpLEmail(rsAccountDetails.getString("emailID"));
				bcpDetailDto.setRegion(rsAccountDetails.getString("Region"));
				bcpDetailDto.setZone(rsAccountDetails.getString("ACCZONE"));
				listAccountDetails.add(bcpDetailDto);
			}
			//return listAccountDetails;
		}
		catch(Exception ex )
		{
			
			//Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//added by nagarjuna 
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, "Error at AccountId  :"+rsAccountDetails.getInt("accountID"), logToFile, logToConsole);//added by nagarjuna 
			//throw new Exception("SQL Exception : "+ ex.getMessage(), ex);
		}
		finally
		{
			try 
			{
				DbConnection.closeResultset(rsAccountDetails);
				DbConnection.closeCallableStatement(getAllAccountsReport);
				DbConnection.freeConnection(connection);
			} 
			catch (SQLException e) 
			{
				Utility.LOG(true,true,"error while closing connection   : "+e);
				// TODO Auto-generated catch block
				//e.printStackTrace();
				throw new Exception("SQL Exception while Closing Connection: "+ e.getMessage(), e);
			}
		}
		return listAccountDetails;
	}
	
	
	
	public ArrayList<BCPAddressDto> getBCPDetails(BCPAddressDto objDto) throws Exception
	{
		//Added by Nagarjuna
		String methodName="getBCPDetails",  msg="";
		boolean logToFile=true, logToConsole=true;
		//End Nagarjuna
		Connection connection =null;
		Statement st=null;
		ResultSet rs = null;
		ArrayList<BCPAddressDto> listBCPDetails = new ArrayList<BCPAddressDto>();
		CallableStatement getBCP =null;
		BCPAddressDto dto = null;
		
		try
		{
			connection=DbConnection.getConnectionObject();
			
			getBCP= connection.prepareCall(sqlGetBCP);
			getBCP.setString(1,objDto.getSearchBCP());
			if(objDto.getSearchAccount()!=null && !"".equals(objDto.getSearchAccount()))
			{
				getBCP.setLong(2,Long.parseLong(objDto.getSearchAccount()));
			}
			else
			{
				getBCP.setNull(2,java.sql.Types.BIGINT);
			}
			rs = getBCP.executeQuery();
			while(rs.next())
			{
				dto =  new BCPAddressDto();
				dto.setBCPId(""+rs.getInt("BCP_ID"));
				dto.setFirstname(rs.getString("FNAME"));
				dto.setLastName(rs.getString("LNAME"));
				
				listBCPDetails.add(dto);
			}
			//return listBCPDetails;
		}
		catch(Exception ex )
		{
			//ex.printStackTrace();
			
Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//added by nagarjuna
		//	throw new Exception("SQL Exception : "+ ex.getMessage(), ex);	
		}
		finally
		{
			try 
			{
				DbConnection.closeResultset(rs);
				DbConnection.closeStatement(st);
				DbConnection.closeCallableStatement(getBCP);
				DbConnection.freeConnection(connection);
			} 
			catch (SQLException e) 
			{
				Utility.LOG(true,true,"error while closing connection   : "+e);
				// TODO Auto-generated catch block
				//e.printStackTrace();
				throw new Exception("SQL Exception : "+ e.getMessage(), e);
			}
		}
		return listBCPDetails;
	}
	//	Method used for Fetching MAIN TAB Label Values and Text
	public ArrayList<BCPAddressDto> viewBCPList(BCPAddressDto objDto) throws Exception
	{
		
		//		Added by Nagarjuna
		String methodName="viewBCPList",  msg="";
		boolean logToFile=true, logToConsole=true;
		//End Nagarjuna
		ArrayList<BCPAddressDto> objUserList = new ArrayList<BCPAddressDto>();
		Connection conn =null;
		//CallableStatement proc =null;
		ResultSet rs = null;
		CallableStatement getBCP =null;
		
		try
		{	
			String userName="";
			
			

			conn=DbConnection.getConnectionObject();
			
			getBCP= conn.prepareCall(sqlGetBCPDetails);
			
			String accountIdStr=objDto.getSearchAccountIdStr();
			String bcpIdStr=objDto.getSearchBcpIdStr();
			String bcpNameStr=objDto.getSearchBcpNameStr();
			
			if(accountIdStr==null || accountIdStr.trim().equals(""))
			{
				getBCP.setNull(1,java.sql.Types.BIGINT);
			}
			else
			{
				getBCP.setLong(1, Long.parseLong(accountIdStr));
			}
			
			if(bcpIdStr==null || bcpIdStr.trim().equals(""))
			{
				getBCP.setNull(2,java.sql.Types.BIGINT);
			}
			else
			{
				getBCP.setLong(2, Long.parseLong(bcpIdStr));
			}
			
			if(bcpNameStr==null || bcpNameStr.trim().equals(""))
			{
				getBCP.setNull(3,java.sql.Types.VARCHAR);
			}
			else
			{
				getBCP.setString(3, bcpNameStr);
			}
			
			PagingSorting pagingSorting = objDto.getPagingSorting();
			pagingSorting.sync();// To calculate start  index and Enc Index
			
			getBCP.setString(4, pagingSorting.getSortByColumn());//columnName
			getBCP.setString(5, PagingSorting.DB_Asc_Desc(pagingSorting.getSortByOrder()));//sort order
			getBCP.setInt(6, pagingSorting.getStartRecordId());//start index
			getBCP.setInt(7, pagingSorting.getEndRecordId());//end index
			
			
			rs = getBCP.executeQuery();
			
			int countFlag=0;
			int recordCount=0;
			while(rs.next()!=false)
			{   
				countFlag++;
			
				userName=(rs.getString("FNAME"))+" "+(rs.getString("LNAME"));
				objDto=  new BCPAddressDto();				
				objDto.setCustomerName(userName);
				objDto.setAccountID(""+ rs.getInt("ACCOUNTID"));
				objDto.setAccountName(rs.getString("ACCOUNTNAME"));
				objDto.setBCPId(""+ rs.getInt("BCP_ID"));
				objDto.setFirstname(rs.getString("FNAME"));
				objDto.setLastName(rs.getString("LNAME"));
				objDto.setTelephonePhno(rs.getString("TELEPHONENO"));
				objDto.setEmail_Id(rs.getString("EMAIL_ID"));
				objDto.setAddress1(rs.getString("ADDRESS1"));
				objDto.setAddress2(rs.getString("ADDRESS2"));
				objDto.setAddress3(rs.getString("ADDRESS3"));
				objDto.setAddress4(rs.getString("ADDRESS4"));
				objDto.setFax(rs.getString("FAX"));
				//objDto.setPin(rs.getString("PIN"));
				objDto.setTitle(rs.getString("TITLE"));
				objDto.setPostalCode(rs.getString("POSTAL_CODE"));
				
				objDto.setCityName(rs.getString("CITY_NAME"));
				objDto.setStateName(rs.getString("STATE_NAME"));
				objDto.setCountryName(rs.getString("COUNTRY_NAME"));
				
				recordCount=rs.getInt("FULL_REC_COUNT");
				
				objUserList.add(objDto);

//				if(pagingSorting.isPagingToBeDone())
//				{
//					pagingSorting.setRecordCount(rs.getInt("FULL_REC_COUNT"));
//				}

			}
			
			pagingSorting.setRecordCount(recordCount);
//			if(pagingSorting.isPagingToBeDone())
//			{
//				if(countFlag==0)
//				{
//					pagingSorting.setRecordCount(0);
//				}
//			}
					
		}
		catch(Exception ex)
		{
			//Utility.LOG(true,true,"Exception    : "+ex);
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//added by nagarjuna 
			throw new Exception("SQL Exception : "+ ex.getMessage(), ex);
		}
		finally
		{
			try 
			{
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(getBCP);
				DbConnection.freeConnection(conn);
				
			} catch (Exception e) 
			{
				//e.printStackTrace();
				Utility.LOG(true,true,"Exception in closing connection   : "+e);
				throw new Exception("Exception while Closing Connections: " + e.getMessage(),e);
			}
		}
		return objUserList;
	}
	
	public BCPAddressDto viewUserProfile(Connection conn, int bcpId) throws Exception
	{
		//Added by Nagarjuna
		String methodName="viewUserProfile",  msg="";
		boolean logToFile=true, logToConsole=true;
		//End Nagarjuna
		ResultSet rs = null;
		
		//StringBuffer sQuery=new StringBuffer();
		//String userName="";
		BCPAddressDto objDto=  new BCPAddressDto();
		CallableStatement getAllBCPReport =null;
		try
		{	

		
			
			getAllBCPReport= conn.prepareCall(sqlGetBCPDetailById);
			//getAllBCPReport.setInt(1,bcpId);
			getAllBCPReport.setNull(1, java.sql.Types.BIGINT);
			getAllBCPReport.setLong(2, bcpId);
			getAllBCPReport.setNull(3, java.sql.Types.VARCHAR);
			
			rs = getAllBCPReport.executeQuery();
			while(rs.next())
			{
				objDto=  new BCPAddressDto();				
				objDto.setAccountID(""+ rs.getInt("ACCOUNTID"));
				objDto.setAccountName(rs.getString("ACCOUNTNAME"));
				objDto.setBCPId(""+ rs.getInt("BCP_ID"));
				objDto.setFirstname(rs.getString("FNAME"));
				objDto.setLastName(rs.getString("LNAME"));
				objDto.setTelephonePhno(rs.getString("TELEPHONENO"));
				objDto.setEmail_Id(rs.getString("EMAIL_ID"));
				objDto.setAddress1(rs.getString("ADDRESS1"));
				objDto.setAddress2(rs.getString("ADDRESS2"));
				objDto.setAddress3(rs.getString("ADDRESS3"));
				objDto.setAddress4(rs.getString("ADDRESS4"));
				objDto.setFax(rs.getString("FAX"));
				objDto.setTitle(rs.getString("TITLE"));
				objDto.setPostalCode(rs.getString("POSTAL_CODE"));
				objDto.setCityName(rs.getString("CITY_NAME"));
				objDto.setStateName(rs.getString("STATE_NAME"));
				objDto.setCountryName(rs.getString("COUNTRY_NAME"));
				objDto.setCountryCode(rs.getString("COUNTRY_CODE"));
				objDto.setState_Id(rs.getString("STATE_ID"));
				objDto.setCity_Id(rs.getString("CITY_ID"));
			}
		}
		catch(Exception ex)
		{
			//Utility.LOG(true,true,"Exception    : "+ex);
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//added by nagarjuna
			throw new Exception("SQL Exception : "+ ex.getMessage(), ex);
		}
		finally
		{
			DbConnection.closeResultset(rs);
			DbConnection .closeCallableStatement(getAllBCPReport);
		}
		
		return objDto;
	}
	public String addNewBCP(BCPAddressDto objDto) throws Exception
	{
		//Added by Nagarjuna
		String methodName="addNewBCP",  msg1="";
		boolean logToFile=true, logToConsole=true;
		//End Nagarjuna
		String success="";
		Connection conn =null;
		CallableStatement proc =null;
		boolean bTrasanctionFlag=false;
	
		try
		{
			conn=DbConnection.getConnectionObject();
			conn.setAutoCommit(false);

			proc=conn.prepareCall(" {CALL IOE.SP_BCP_ADDRESS_INSERT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			proc.setInt(1,0);//BCP_ID
			proc.setLong(2,Long.parseLong(objDto.getAccountID()));//AccountID
			proc.setInt(3,objDto.getUpdateFlag());//update Flag
			proc.setString(4,objDto.getFirstname());
			proc.setString(5,objDto.getLastName());
			proc.setString(6,objDto.getTelephonePhno());
			proc.setString(7,objDto.getEmail_Id());
			proc.setString(8,objDto.getPin());
			proc.setString(9,objDto.getFax());
			proc.setString(10,objDto.getTitle());
			proc.setString(11,objDto.getAddress1());
			proc.setString(12,objDto.getAddress2());
			proc.setString(13,objDto.getAddress3());
			proc.setString(14,objDto.getAddress4());
			proc.setLong(15,Long.parseLong(objDto.getCity_Id()));
			proc.setLong(16,Long.parseLong(objDto.getState_Id()));
			proc.setLong(17,Long.parseLong(objDto.getPostalCode()));
			proc.setLong(18,Long.parseLong(objDto.getCountryCode()));
			proc.setInt(19,0);
			proc.setInt(20,0);
			proc.setString(21,"");
			
			proc.execute();	
			String msg=proc.getString(21);

			System.err.println(msg);
			if(msg.equalsIgnoreCase("SUCCESS")){
				
				success="true";
				bTrasanctionFlag=true;
				
			}else{
				
				success="false";
				
				
			}
			
			}
			catch(Exception ex)
			{
				Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,""+proc.getString(21), logToFile, logToConsole);//added by nagarjuna
				//Utility.LOG(true,true,"error message     : "+proc.getString(21));//added by nagarjuna 
				throw new Exception("SQL Exception : "+ ex.getMessage(), ex);
			}
			finally
			{
				try 
				{
					if(bTrasanctionFlag==true)
					{
						conn.commit();
					}
					else
					{
						conn.rollback();
					}
					DbConnection.closeCallableStatement(proc);
					DbConnection.freeConnection(conn);
				} catch (Exception e) 
				{
					//e.printStackTrace();
					Utility.LOG(true,true,"Exception    : "+e);
					throw new Exception("Exception : " + e.getMessage(),e);
				}
			}
			return success;
		}
	
	public String updateBCP(BCPAddressDto objDto) throws Exception
	{
		//Added by Nagarjuna
		String methodName="updateBCP",  msg1="";
		boolean logToFile=true, logToConsole=true;
		//End Nagarjuna
		//logger.info("AddEditUserDaoImpl's updateUser mehtod");
		
		String success="";
		Connection conn =null;
		boolean bTrasanctionFlag=false;
		CallableStatement proc =null;

		try
		{
			conn=DbConnection.getConnectionObject();
			conn.setAutoCommit(false);

			proc=conn.prepareCall(" {CALL IOE.SP_BCP_ADDRESS_INSERT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			
			proc.setLong(1,Long.parseLong(objDto.getBCPId()));//BCP_ID
			proc.setNull(2,java.sql.Types.BIGINT);//AccountID
			proc.setInt(3,objDto.getUpdateFlag());//update Flag
			proc.setString(4,objDto.getFirstname());
			proc.setString(5,objDto.getLastName());
			proc.setString(6,objDto.getTelephonePhno());
			proc.setString(7,objDto.getEmail_Id());
			proc.setString(8,null);
			proc.setString(9,objDto.getFax());
			proc.setString(10,objDto.getTitle());
			proc.setString(11,objDto.getAddress1());
			proc.setString(12,objDto.getAddress2());
			proc.setString(13,objDto.getAddress3());
			proc.setString(14,objDto.getAddress4());
			proc.setLong(15,Long.parseLong(objDto.getCity_Id()));
			proc.setLong(16,Long.parseLong(objDto.getState_Id()));
			proc.setLong(17,Long.parseLong(objDto.getPostalCode()));
			proc.setLong(18,Long.parseLong(objDto.getCountryCode()));
			proc.setInt(19,0);
			proc.setInt(20,0);
			proc.setString(21,"");
			
			proc.execute();	
			String msg=proc.getString(21);

			System.err.println(msg);
			if(msg.equalsIgnoreCase("SUCCESS")){
				
				success="true";
				bTrasanctionFlag=true;
				
			}else{				
				success="false";				
			}

		}
		catch(Exception ex)
		{
			//Utility.LOG(true,true,"Exception    : "+ex);
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,""+proc.getString(21), logToFile, logToConsole);//added by nagarjuna
			throw new Exception("SQL Exception : "+ ex.getMessage(), ex);
		}
		finally
		{
				try 
				{
					if(bTrasanctionFlag==true)
					{
						conn.commit();
					}
					else
					{
						conn.rollback();
					}
					DbConnection.closeCallableStatement(proc);
					DbConnection.freeConnection(conn);
				
			} catch (Exception e) 
			{
				//e.printStackTrace();
				Utility.LOG(true,true,"Exception    : "+e);
				//throw new Exception("Exception : " + e.getMessage(),e);
			}
		}
		return success;
	}
}
