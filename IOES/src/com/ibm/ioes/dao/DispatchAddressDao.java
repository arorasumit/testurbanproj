package com.ibm.ioes.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


import com.ibm.ioes.forms.DispatchAddressDto;
import com.ibm.ioes.forms.LocationDetailDto;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.PagingSorting;
import com.ibm.ioes.utilities.Utility;

public class DispatchAddressDao extends CommonBaseDao
{
	public static String sqlGetAccount = "{call IOE.spGetAccount(?)}";//To Fetch All Account List from Database
	public static String sqlGetDispatchAddress =" SELECT DISPATCH_ADDRESS_CODE, DISPATCHADDNAME, tdm.ACCOUNTID,acc.ACCOUNTNAME, FNAME, LNAME, TELEPHONENO, FAX, EMAIL_ID, PIN, TITLE, ADDRESS1, ADDRESS2, ADDRESS3, ADDRESS4, CITY_ID, STATE_ID, POSTAL_CODE, COUNTRY_CODE "+ 
    " FROM IOE.TDISPATCH_ADDRESS_MSTR tdm, IOE.TM_ACCOUNT acc ";
	public static String sqlSearchDispatch= "{call IOE.SP_GET_DISPATCH_ADDRESS(?,?)}";
	public static String sqlDispatchList = "{call IOE.SP_GET_DISPATCH_DETAILS(?,?,?,?,?,?,?)}";//To Fetch All Account List from Database
	
	//Method used for Fetching All Accounts
	public ArrayList<DispatchAddressDto> getAccountDetails(DispatchAddressDto objDto) 
	{
		
		
		
		//Added by Nagarjuna
		String methodName="getAccountDetails",  msg="";
		boolean logToFile=true, logToConsole=true;
		//End Nagarjuna
		Connection connection =null;
		CallableStatement getAllAccountsReport =null;
		ResultSet rsAccountDetails = null;
		ArrayList<DispatchAddressDto> listAccountDetails = new ArrayList<DispatchAddressDto>();
		DispatchAddressDto DispatchAddressDto = null;
		
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
				DispatchAddressDto =  new DispatchAddressDto();
				DispatchAddressDto.setAccountID(""+rsAccountDetails.getInt("accountID"));
				DispatchAddressDto.setAccountName(rsAccountDetails.getString("accountName"));
				DispatchAddressDto.setAccphoneNo(rsAccountDetails.getInt("PhoneNo"));
				DispatchAddressDto.setLob(rsAccountDetails.getString("LOB"));
				DispatchAddressDto.setAccountManager(rsAccountDetails.getString("AccountManager"));
				DispatchAddressDto.setProjectManager(rsAccountDetails.getString("ProjManager"));
				DispatchAddressDto.setSpFirstname(rsAccountDetails.getString("SPFirstname"));
				DispatchAddressDto.setSpLastName(rsAccountDetails.getString("SLastName"));
				DispatchAddressDto.setSpLPhno(rsAccountDetails.getString("SLPhno"));//changed by kalpana from long to string for bug id HYPR11042013001
				DispatchAddressDto.setSpLEmail(rsAccountDetails.getString("emailID"));
				DispatchAddressDto.setRegion(rsAccountDetails.getString("Region"));
				DispatchAddressDto.setZone(rsAccountDetails.getString("ACCZONE"));
				listAccountDetails.add(DispatchAddressDto);
			}
			return listAccountDetails;
		}
		catch(Exception ex )
		{
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//added by nagarjuna 
			//ex.printStackTrace();	
		}
		finally
		{
			try 
			{
				DbConnection.closeResultset(rsAccountDetails);
				DbConnection.closeCallableStatement(getAllAccountsReport);
				DbConnection.freeConnection(connection);
			} 
			catch (Exception e) 
			{
				Utility.LOG(true,true,"error while closing connection   : "+e);//added by Nagarjuna
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		return listAccountDetails;
	}
	public ArrayList<DispatchAddressDto> getDispatchAddressDetails(DispatchAddressDto objDto) 
	{
//		Added by Nagarjuna
		String methodName="getDispatchAddressDetails",  msg="";
		boolean logToFile=true, logToConsole=true;
		//End Nagarjuna
			Connection connection =null;
			Statement st=null;
			ResultSet rs = null;
			ArrayList<DispatchAddressDto> listDispatchDetails = new ArrayList<DispatchAddressDto>();
			CallableStatement getDispatch =null;
			DispatchAddressDto dto = null;
			
			try
			{
				connection=DbConnection.getConnectionObject();
				
				getDispatch= connection.prepareCall(sqlSearchDispatch);
				getDispatch.setString(1,objDto.getSearchDispatchAddress());
				if(objDto.getSearchAccount()!=null && !"".equals(objDto.getSearchAccount()))
				{
					getDispatch.setLong(2,Long.parseLong(objDto.getSearchAccount()));
				}
				else
				{
					getDispatch.setNull(2,java.sql.Types.BIGINT);
				}
				rs = getDispatch.executeQuery();
				while(rs.next())
				{
					dto =  new DispatchAddressDto();
					dto.setDispatchAddressId(String.valueOf(rs.getInt("DISPATCH_ADDRESS_CODE")));
					dto.setDispatchAddressName((rs.getString("DISPATCHADDNAME")));
					dto.setFirstname(rs.getString("FNAME"));
					dto.setLastName(rs.getString("LNAME"));
					listDispatchDetails.add(dto);
				}
				
			}
			catch(Exception ex )
			{
				Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//added by nagarjuna 
				//ex.printStackTrace();	
			}
			finally
			{
				try 
				{
					DbConnection.closeResultset(rs);
					DbConnection.closeCallableStatement(getDispatch);
					DbConnection.closeStatement(st);
					DbConnection.freeConnection(connection);
				} 
				catch (Exception e) 
				{
					Utility.LOG(true,true,"error while closing connection   : "+e);//added by Nagarjuna
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			}
			return listDispatchDetails ;
		}
	//	Method used for Fetching MAIN TAB Label Values and Text
	public ArrayList<DispatchAddressDto> viewDispatchAddressList(DispatchAddressDto objDto) throws Exception
	{
//		Added by Nagarjuna
		String methodName="viewDispatchAddressList",  msg="";
		boolean logToFile=true, logToConsole=true;
		//End Nagarjuna
		ArrayList<DispatchAddressDto> objUserList = new ArrayList<DispatchAddressDto>();
		Connection conn =null;
		CallableStatement proc =null;
		ResultSet rs = null;
		try
		{	
			conn=DbConnection.getConnectionObject();
			proc=conn.prepareCall(sqlDispatchList);
			
			if(objDto.getDispatchAddressId()!=null && !"".equals(objDto.getDispatchAddressId()))
			{
				proc.setLong(2,Long.parseLong(objDto.getDispatchAddressId()));
			}
			else
			{
				proc.setNull(2,java.sql.Types.BIGINT);
			}
			if(objDto.getAccountID()!=null && !"".equals(objDto.getAccountID()))
			{
				proc.setLong(1,Long.parseLong(objDto.getAccountID()));
			}
			else
			{
				proc.setNull(1,java.sql.Types.BIGINT);
			}
			if(objDto.getDispatchAddressName()!=null && !"".equals(objDto.getDispatchAddressName()))
			{
				proc.setString(3,objDto.getDispatchAddressName().trim());
			}
			else
			{
				proc.setNull(3,java.sql.Types.VARCHAR);
			}
			PagingSorting pagingSorting = objDto.getPagingSorting();
			pagingSorting.sync();// To calculate start  index and Enc Index
			
			proc.setString(4, pagingSorting.getSortByColumn());//columnName
			proc.setString(5, PagingSorting.DB_Asc_Desc(pagingSorting.getSortByOrder()));//sort order
			proc.setInt(6, pagingSorting.getStartRecordId());//start index
			proc.setInt(7, pagingSorting.getEndRecordId());//end index
		
			rs=proc.executeQuery();
			int countFlag=0;
			int recordCount=0;
			while(rs.next()!=false)
			{   
				countFlag++;
				
				String userName=(rs.getString("FNAME"))+" "+(rs.getString("LNAME"));
				objDto=  new DispatchAddressDto();				
				objDto.setCustomerName(userName);
				objDto.setAccountID(String.valueOf(rs.getInt("ACCOUNTID")));
				objDto.setAccountName(rs.getString("ACCOUNTNAME"));
				objDto.setDispatchAddressId(String.valueOf(rs.getInt("DISPATCH_ADDRESS_CODE")));
				objDto.setDispatchAddressName(rs.getString("DISPATCHADDNAME"));
				objDto.setTelephonePhno(rs.getString("TELEPHONENO"));
				objDto.setEmail_Id(rs.getString("EMAIL_ID"));
				objDto.setAddress1(rs.getString("ADDRESS1"));
				objDto.setAddress2(rs.getString("ADDRESS2"));
				objDto.setAddress3(rs.getString("ADDRESS3"));
				objDto.setAddress4(rs.getString("ADDRESS4"));
				objDto.setFax(rs.getString("FAX"));
				objDto.setPin(rs.getString("PIN"));
				objDto.setTitle(rs.getString("TITLE"));
				objDto.setPostalCode(rs.getString("POSTAL_CODE"));
				objDto.setCountryName(rs.getString("COUNTRY_NAME"));
				objDto.setStateName(rs.getString("STATE_NAME"));
				objDto.setCityName(rs.getString("CITY_NAME"));
				
				recordCount=rs.getInt("FULL_REC_COUNT");
				
				objUserList.add(objDto);
			}
			pagingSorting.setRecordCount(recordCount);
		}
		catch(Exception ex)
		{
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//added by nagarjuna 
			//ex.printStackTrace();
			throw new Exception("SQL Exception : "+ ex.getMessage(), ex);
		}
		finally
		{
			try 
			{
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(proc);
				DbConnection.freeConnection(conn);
				
			} catch (Exception e) 
			{
				Utility.LOG(true,true,"error while closing connection   : "+e);//added by Nagarjuna
				//e.printStackTrace();
				throw new Exception("Exception : " + e.getMessage(),e);
			}
		}
		return objUserList;
	}
	public DispatchAddressDto viewDispatchAddressProfile(int userId) throws Exception
	{
//		Added by Nagarjuna
		String methodName="viewDispatchAddressProfile",  msg="";
		boolean logToFile=true, logToConsole=true;
		//End Nagarjuna
		Connection conn =null;
		ResultSet rs = null;
		Statement stmt=null;
		StringBuffer sQuery=new StringBuffer();
		String userName="";
		DispatchAddressDto objDto=  new DispatchAddressDto();
		try
		{	

			conn=DbConnection.getConnectionObject();
			stmt=conn.createStatement();
			rs =stmt.executeQuery(sqlGetDispatchAddress + " where DISPATCH_ADDRESS_CODE= " + userId + " and tdm.ACCOUNTID=acc.ACCOUNTID");		
			while(rs.next())
			{
				objDto.setAccountID(String.valueOf(rs.getInt("ACCOUNTID")));
				objDto.setAccountName(rs.getString("ACCOUNTNAME"));
				objDto.setDispatchAddressId(String.valueOf(rs.getInt("DISPATCH_ADDRESS_CODE")));
				objDto.setDispatchAddressName(rs.getString("DISPATCHADDNAME"));
				objDto.setFirstname(rs.getString("FNAME"));
				objDto.setLastName(rs.getString("LNAME"));
				
				objDto.setTelephonePhno(rs.getString("TELEPHONENO"));
				objDto.setEmail_Id(rs.getString("EMAIL_ID"));
				objDto.setAddress1(rs.getString("ADDRESS1"));
				objDto.setAddress2(rs.getString("ADDRESS2"));
				objDto.setAddress3(rs.getString("ADDRESS3"));
				objDto.setAddress4(rs.getString("ADDRESS4"));
				objDto.setFax(rs.getString("FAX"));
				objDto.setPin(rs.getString("PIN"));
				objDto.setTitle(rs.getString("TITLE"));
				objDto.setCountryCode(rs.getString("COUNTRY_CODE"));
				objDto.setPostalCode(rs.getString("POSTAL_CODE"));
				objDto.setState_Id(rs.getString("STATE_ID"));
				objDto.setCityID(rs.getString("CITY_ID"));
				
			}
		}
		catch(Exception ex)
		{
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//added by nagarjuna 
			//ex.printStackTrace();
			throw new Exception("SQL Exception : "+ ex.getMessage(), ex);
		}
		finally
		{
			try 
			{
				DbConnection.closeResultset(rs);
				DbConnection .closeStatement(stmt);
				DbConnection.freeConnection(conn);;
			} catch (Exception e) 
			{
				Utility.LOG(true,true,"error while closing connection   : "+e);//added by Nagarjuna
				//e.printStackTrace();
				throw new Exception("Exception : " + e.getMessage(),e);
			}
		}
		return objDto;
	}
	public String addNewDispatchAddress(DispatchAddressDto objDto) throws Exception
	{
//		Added by Nagarjuna
		String methodName="addNewDispatchAddress",  msg1="";
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

			proc=conn.prepareCall(" {CALL IOE.SP_DISPATCH_ADDRESS_INSERT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			proc.setInt(1,0);
			proc.setString(2, objDto.getDispatchAddressName());
			proc.setInt(3,Integer.parseInt(objDto.getAccountID()));
			proc.setInt(4,objDto.getUpdateFlag());
			proc.setString(5,objDto.getFirstname());
			proc.setString(6,objDto.getLastName());
			proc.setString(7,objDto.getTelephonePhno());
			proc.setString(8,objDto.getEmail_Id());
			proc.setString(9,objDto.getPin());
			proc.setString(10,objDto.getFax());
			proc.setString(11,objDto.getTitle());
			proc.setString(12,objDto.getAddress1());
			proc.setString(13,objDto.getAddress2());
			proc.setString(14,objDto.getAddress3());
			proc.setString(15,objDto.getAddress4());
			proc.setInt(16,Integer.parseInt(objDto.getCityID()));
			proc.setInt(17,Integer.parseInt(objDto.getState_Id()));
			proc.setInt(18,Integer.parseInt(objDto.getPostalCode()));
			proc.setInt(19,Integer.parseInt(objDto.getCountryCode()));
			proc.setInt(20,0);
			proc.setInt(21,0);
			proc.setString(22,"");
			
			proc.execute();	
			String msg=proc.getString(22);

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
				Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, "Error :"+proc.getString(22), logToFile, logToConsole);//added by nagarjuna 
				//ex.printStackTrace();
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
					Utility.LOG(true,true,"error while closing connection   : "+e);//added by Nagarjuna
					//e.printStackTrace();
					throw new Exception("Exception : " + e.getMessage(),e);
				}
			}
			return success;
		}
	
	public String updateDispatchAddress(DispatchAddressDto objDto) throws Exception
	{
		//logger.info("AddEditUserDaoImpl's updateUser mehtod");
//		Added by Nagarjuna
		String methodName="updateDispatchAddress",  msg1="";
		boolean logToFile=true, logToConsole=true;
		//End Nagarjuna
		String success="";
		Connection conn =null;
		boolean bTrasanctionFlag=false;
		CallableStatement proc =null;

		try
		{
			conn=DbConnection.getConnectionObject();
			conn.setAutoCommit(false);

			proc=conn.prepareCall(" {CALL IOE.SP_DISPATCH_ADDRESS_INSERT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			proc.setInt(1,Integer.parseInt(objDto.getDispatchAddressId()));
			proc.setString(2,objDto.getDispatchAddressName() );
			proc.setInt(3,Integer.parseInt(objDto.getAccountID()));
			proc.setInt(4,objDto.getUpdateFlag());
			proc.setString(5,objDto.getFirstname());
			proc.setString(6,objDto.getLastName());
			proc.setString(7,objDto.getTelephonePhno());
			proc.setString(8,objDto.getEmail_Id());
			proc.setString(9,objDto.getPin());
			proc.setString(10,objDto.getFax());
			proc.setString(11,objDto.getTitle());
			proc.setString(12,objDto.getAddress1());
			proc.setString(13,objDto.getAddress2());
			proc.setString(14,objDto.getAddress3());
			proc.setString(15,objDto.getAddress4());
			proc.setInt(16,Integer.parseInt(objDto.getCityID()));
			proc.setInt(17,Integer.parseInt(objDto.getState_Id()));
			proc.setInt(18,Integer.parseInt(objDto.getPostalCode()));
			proc.setInt(19,Integer.parseInt(objDto.getCountryCode()));
		
			proc.setInt(20,0);
			proc.setInt(21,0);
			proc.setString(22,"");
			
			proc.execute();	
			String msg=proc.getString(22);

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
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, "Error :"+proc.getString(22), logToFile, logToConsole);//added by nagarjuna 
			//ex.printStackTrace();
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
				Utility.LOG(true,true,"error while closing connection   : "+e);//added by Nagarjuna
				//e.printStackTrace();
				throw new Exception("Exception : " + e.getMessage(),e);
			}
		}
		return success;
	}
	
}
