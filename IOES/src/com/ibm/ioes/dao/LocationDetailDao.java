package com.ibm.ioes.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.ibm.ioes.forms.LocationDetailDto;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.PagingSorting;
import com.ibm.ioes.utilities.Utility;

public class LocationDetailDao extends CommonBaseDao
{
	public static String sqlGetAccount = "{call IOE.spGetAccount(?)}";//To Fetch All Account List from Database
	public static String sqlGetLocation =" SELECT LOCATION_CODE, LOCATION_NAME, tdm.ACCOUNTID,acc.ACCOUNTNAME, FNAME, LNAME, TELEPHONENO, FAX, EMAIL_ID, PIN, TITLE, ADDRESS1, ADDRESS2, ADDRESS3, ADDRESS4, CITY_ID, STATE_ID, POSTAL_CODE, COUNTRY_CODE "+ 
    " FROM IOE.TCUSTOMER_LOCATION_MSTR tdm, IOE.TM_ACCOUNT acc ";
	public static String sqlSearchLocation = "{call IOE.SP_GET_CUSTOMER_LOCATION(?,?)}";//To Fetch All Account List from Database
	public static String sqlLocationList = "{call IOE.SP_GET_CUSTOMER_DETAILS(?,?,?,?,?,?,?)}";//To Fetch All Account List from Database
	
	
	//Method used for Fetching All Accounts
	public ArrayList<LocationDetailDto> getAccountDetails(LocationDetailDto objDto) 
	{
//		Added by Nagarjuna
		String methodName="getAccountDetails",  msg="";
		boolean logToFile=true, logToConsole=true;
		//End Nagarjuna
		Connection connection =null;
		CallableStatement getAllAccountsReport =null;
		ResultSet rsAccountDetails = null;
		ArrayList<LocationDetailDto> listAccountDetails = new ArrayList<LocationDetailDto>();
		LocationDetailDto locationDetailDto = null;
		
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
				locationDetailDto =  new LocationDetailDto();
				locationDetailDto.setAccountID(""+rsAccountDetails.getInt("accountID"));
				locationDetailDto.setAccountName(rsAccountDetails.getString("accountName"));
				locationDetailDto.setAccphoneNo(rsAccountDetails.getInt("PhoneNo"));
				locationDetailDto.setLob(rsAccountDetails.getString("LOB"));
				locationDetailDto.setAccountManager(rsAccountDetails.getString("AccountManager"));
				locationDetailDto.setProjectManager(rsAccountDetails.getString("ProjManager"));
				locationDetailDto.setSpFirstname(rsAccountDetails.getString("SPFirstname"));
				locationDetailDto.setSpLastName(rsAccountDetails.getString("SLastName"));
				//locationDetailDto.setSpLPhno(rsAccountDetails.getInt("SLPhno"));//changed by kalpana from long to string for bug id HYPR11042013001
				locationDetailDto.setSpLPhno(rsAccountDetails.getString("SLPhno"));
				locationDetailDto.setSpLEmail(rsAccountDetails.getString("emailID"));
				locationDetailDto.setRegion(rsAccountDetails.getString("Region"));
				locationDetailDto.setZone(rsAccountDetails.getString("ACCZONE"));
				listAccountDetails.add(locationDetailDto);
			}
			return listAccountDetails;
		}
		catch(Exception ex )
		{
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, "Error At AccountID"+locationDetailDto.getAccountID(), logToFile, logToConsole);//added by nagarjuna 
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
	public ArrayList<LocationDetailDto> getCountryDetails() 
	{
//		Added by Nagarjuna
		String methodName="getCountryDetails",  msg="";
		boolean logToFile=true, logToConsole=true;
		//End Nagarjuna
		Connection connection =null;
		CallableStatement getAllCountry =null;
		ResultSet rs = null;
		ArrayList<LocationDetailDto> listCountryDetails = new ArrayList<LocationDetailDto>();
		LocationDetailDto dto = null;
		
		try
		{
			String sqlGetCountry="SELECT COUNTRY_CODE, COUNTRY_NAME FROM IOE.TCOUNTRY_MASTER";
			connection=DbConnection.getConnectionObject();
			getAllCountry= connection.prepareCall(sqlGetCountry);
			
			rs = getAllCountry.executeQuery();
			while(rs.next())
			{
				dto =  new LocationDetailDto();
				dto.setCountryID(rs.getString("COUNTRY_CODE"));
				dto.setCountryName(rs.getString("COUNTRY_NAME"));
				listCountryDetails.add(dto);
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
				DbConnection .closeCallableStatement(getAllCountry);
				DbConnection.freeConnection(connection);
			} 
			catch (Exception e) 
			{
				Utility.LOG(true,true,"error while closing connection   : "+e);//added by Nagarjuna
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		return listCountryDetails;
	}
	public ArrayList<LocationDetailDto> getStateDetails(LocationDetailDto Dto) 
	{
//		Added by Nagarjuna
		String methodName="getStateDetails",  msg="";
		boolean logToFile=true, logToConsole=true;
		//End Nagarjuna
		Connection connection =null;
		CallableStatement getAllCountry =null;
		ResultSet rs = null;
		ArrayList<LocationDetailDto> listCountryDetails = new ArrayList<LocationDetailDto>();
		LocationDetailDto dto = null;
		
		try
		{
			String sqlGetCountry="SELECT STATE_ID, STATE_NAME, COUNTRY_ID FROM IOE.TSTATE_MASTER WHERE COUNTRY_ID=?";
			connection=DbConnection.getConnectionObject();
			//getAllCountry= connection.prepareCall(sqlGetCountry);
			PreparedStatement ps=null;
			ps=connection.prepareStatement(sqlGetCountry);
			ps.setInt(1,Integer.parseInt(Dto.getCountryID()));
			
			rs = ps.executeQuery();
			while(rs.next())
			{
				dto =  new LocationDetailDto();
				dto.setStateID(rs.getString("STATE_ID"));
				dto.setStateName(rs.getString("STATE_NAME"));
				dto.setCountryID(rs.getString("COUNTRY_ID"));
				listCountryDetails.add(dto);
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
				DbConnection.closeCallableStatement(getAllCountry);
				DbConnection.freeConnection(connection);
			} 
			catch (Exception e) 
			{
				Utility.LOG(true,true,"error while closing connection   : "+e);//added by Nagarjuna
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		return listCountryDetails;
	}
	public ArrayList<LocationDetailDto> getCityDetails(LocationDetailDto Dto) 
	{
//		Added by Nagarjuna
		String methodName="getCityDetails",  msg="";
		boolean logToFile=true, logToConsole=true;
		//End Nagarjuna
		Connection connection =null;
		CallableStatement getAllCountry =null;
		ResultSet rs = null;
		ArrayList<LocationDetailDto> listCountryDetails = new ArrayList<LocationDetailDto>();
		LocationDetailDto dto = null;
		
		try
		{
			String sqlGetCountry="SELECT CITY_ID, CITY_NAME, STATE_ID FROM IOE.TCITY_MASTER WHERE STATE_ID=? ";
			connection=DbConnection.getConnectionObject();
			//getAllCountry= connection.prepareCall(sqlGetCountry);
			PreparedStatement ps=null;
			ps=connection.prepareStatement(sqlGetCountry);
			ps.setInt(1,Integer.parseInt(Dto.getStateID()));
			rs = ps.executeQuery();
			while(rs.next())
			{
				dto =  new LocationDetailDto();
				dto.setCityID(rs.getString("CITY_ID"));
				dto.setCityName(rs.getString("CITY_NAME"));
				dto.setStateID(rs.getString("STATE_ID"));
				listCountryDetails.add(dto);
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
				DbConnection.closeCallableStatement(getAllCountry);
				DbConnection.freeConnection(connection);
			} 
			catch (Exception e) 
			{
				Utility.LOG(true,true,"error while closing connection   : "+e);//added by Nagarjuna
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		return listCountryDetails;
	}
	public ArrayList<LocationDetailDto> getLocationDetails(LocationDetailDto objDto) 
	{
//		Added by Nagarjuna
		String methodName="getLocationDetails",  msg="";
		boolean logToFile=true, logToConsole=true;
		//End Nagarjuna
		Connection connection =null;
		Statement st=null;
		ResultSet rs = null;
		ArrayList<LocationDetailDto> listLocationDetails = new ArrayList<LocationDetailDto>();
		CallableStatement getLocation =null;
		LocationDetailDto dto = null;
		
		try
		{
			connection=DbConnection.getConnectionObject();
			
			getLocation= connection.prepareCall(sqlSearchLocation);
			
			getLocation.setString(1, objDto.getSearchLocation());
			if(objDto.getSearchAccount()!=null && !"".equals(objDto.getSearchAccount()))
			{
				getLocation.setLong(2,Long.parseLong(objDto.getSearchAccount()));
			}
			else
			{
				getLocation.setNull(2,java.sql.Types.BIGINT);
			}
			rs = getLocation.executeQuery();
			while(rs.next())
			{
				dto =  new LocationDetailDto();
				dto.setLocationId(String.valueOf(rs.getInt("LOCATION_CODE")));
				dto.setLocationName(rs.getString("LOCATION_NAME"));
				dto.setFirstname(rs.getString("FNAME"));
				dto.setLastName(rs.getString("LNAME"));
				
				listLocationDetails.add(dto);
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
				DbConnection .closeResultset(rs);
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
		return listLocationDetails ;
	}
	
	public ArrayList<LocationDetailDto> viewLocationList(LocationDetailDto objDto) throws Exception
	{
//		Added by Nagarjuna
		String methodName="viewLocationList",  msg="";
		boolean logToFile=true, logToConsole=true;
		//End Nagarjuna
		ArrayList<LocationDetailDto> objUserList = new ArrayList<LocationDetailDto>();
		Connection conn =null;
		CallableStatement proc =null;
		ResultSet rs = null;
		
		try
		{	
			String userName="";
			
			conn=DbConnection.getConnectionObject();

			proc=conn.prepareCall(sqlLocationList);
			if(objDto.getLocationId()!=null && !"".equals(objDto.getLocationId()))
			{
				proc.setLong(2,Long.parseLong(objDto.getLocationId()));
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
			if(objDto.getLocationName()!=null && !"".equals(objDto.getLocationName()))
			{
				proc.setString(3, objDto.getLocationName().trim());
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
				
				userName=(rs.getString("FNAME"))+" "+(rs.getString("LNAME"));
				objDto=  new LocationDetailDto();				
				objDto.setCustomerName(userName);
				objDto.setAccountID(String.valueOf(rs.getInt("ACCOUNTID")));
				objDto.setLocationId(String.valueOf(rs.getInt("LOCATION_CODE")));
				objDto.setLocationName(rs.getString("LOCATION_NAME"));
				objDto.setTelephonePhno(rs.getString("TELEPHONENO"));
				objDto.setEmail_Id(rs.getString("EMAIL_ID"));
				objDto.setAddress1(rs.getString("ADDRESS1"));
				objDto.setAddress2(rs.getString("ADDRESS2"));
				objDto.setAddress3(rs.getString("ADDRESS3"));
				objDto.setAddress4(rs.getString("ADDRESS4"));
				objDto.setFax(rs.getString("FAX"));
				objDto.setPin(rs.getString("PIN"));
				objDto.setTitle(rs.getString("TITLE"));
				objDto.setCountryName(rs.getString("COUNTRY_NAME"));
				objDto.setPostalCode(rs.getString("POSTAL_CODE"));
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
				DbConnection.closeCallableStatement(proc);
				DbConnection.freeConnection(conn);
				
			} catch (Exception e) 
			{
				Utility.LOG(true,true,"error while closing connection   : "+e);//added by Nagarjuna
				//e.printStackTrace();
				//throw new Exception("Exception : " + e.getMessage(),e);
			}
		}
		
		return objUserList;
	}
	public LocationDetailDto viewUserProfile(int userId) throws Exception
	{
//		Added by Nagarjuna
		String methodName="viewUserProfile",  msg="";
		boolean logToFile=true, logToConsole=true;
		//End Nagarjuna
		
		Connection conn =null;
		ResultSet rs = null;
		Statement stmt=null;
		StringBuffer sQuery=new StringBuffer();
		String userName="";
		LocationDetailDto objDto=  new LocationDetailDto();
		try
		{	

			conn=DbConnection.getConnectionObject();
			stmt=conn.createStatement();
			rs =stmt.executeQuery(sqlGetLocation + " where LOCATION_CODE= " + userId + " and tdm.ACCOUNTID=acc.ACCOUNTID");		
			
			while(rs.next())
			{
				objDto.setAccountID(""+ rs.getInt("ACCOUNTID"));
				objDto.setAccountName(rs.getString("ACCOUNTNAME"));
				objDto.setLocationId(""+ rs.getInt("LOCATION_CODE"));
				objDto.setLocationName(rs.getString("LOCATION_NAME"));
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
			//throw new Exception("SQL Exception : "+ ex.getMessage(), ex);
		}
		finally
		{
			try 
			{
				DbConnection.closeResultset(rs);
				DbConnection.closeStatement(stmt);
				DbConnection.freeConnection(conn);;
			} catch (Exception e) 
			{
				Utility.LOG(true,true,"error while closing connection   : "+e);//added by Nagarjuna
				//e.printStackTrace();
				//throw new Exception("Exception : " + e.getMessage(),e);
			}
		}
		return objDto;
	}
	public String addNewCustomerLocation(LocationDetailDto objDto) throws Exception
	{
//		Added by Nagarjuna
		String methodName="addNewCustomerLocation",  msg1="";
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

			proc=conn.prepareCall(" {CALL IOE.SP_CUSTOMER_LOCATION_INSERT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			proc.setInt(1,0);
			proc.setString(2, objDto.getLocationName());
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
				Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, "Error Message"+proc.getString(22), logToFile, logToConsole);//added by nagarjuna 
				//ex.printStackTrace();
				//throw new Exception("SQL Exception : "+ ex.getMessage(), ex);
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
					//throw new Exception("Exception : " + e.getMessage(),e);
				}
			}
			return success;
		}
	
	public String updateCustomerLocation(LocationDetailDto objDto) throws Exception
	{
		//logger.info("AddEditUserDaoImpl's updateUser mehtod");
//		Added by Nagarjuna
		String methodName="updateCustomerLocation",  msg1="";
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

			proc=conn.prepareCall(" {CALL IOE.SP_CUSTOMER_LOCATION_INSERT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			proc.setInt(1,Integer.parseInt(objDto.getLocationId()));
			proc.setString(2,objDto.getLocationName() );
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
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, "Error Message"+proc.getString(22), logToFile, logToConsole);//added by nagarjuna 
			//ex.printStackTrace();
			//throw new Exception("SQL Exception : "+ ex.getMessage(), ex);
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
				//throw new Exception("Exception : " + e.getMessage(),e);
			}
		}
		return success;
	}
	
}
