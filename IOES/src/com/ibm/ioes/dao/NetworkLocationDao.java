package com.ibm.ioes.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.ibm.ioes.forms.NetworkLocationDto;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.PagingSorting;
import com.ibm.ioes.utilities.Utility;

public class NetworkLocationDao extends CommonBaseDao
{
	public static String sqlGetNetworkLocationDetails = "{ call IOE.SP_GET_NETWORKLOCATION_DETAILS( ?,? ) }";//To Fetch All BCP List from Database
	public static String sqlGetNetworkLocationDetailsPaging = "{ call IOE.SP_GET_NETWORKLOCATION_DETAILS_PAGING( ?,?,?,?,?,? ) }";//To Fetch All BCP List from Database
	
		
	public ArrayList<NetworkLocationDto> viewNetworkLocationList(NetworkLocationDto objDto) throws Exception
	{
//		Added by Nagarjuna
		String methodName="viewNetworkLocationList",  msg="";
		boolean logToFile=true, logToConsole=true;
		//End Nagarjuna

		ArrayList<NetworkLocationDto> objUserList = new ArrayList<NetworkLocationDto>();
		Connection conn =null;
		//CallableStatement proc =null;
		ResultSet rs = null;
		CallableStatement getNetworkLocation =null;
		int recordCount=0;
		
		try
		{	
			String userName="";
			
			

			conn=DbConnection.getConnectionObject();
			
			getNetworkLocation= conn.prepareCall(sqlGetNetworkLocationDetailsPaging);
			
			
			String networkLocationIdStr=objDto.getSearchNetworkLocationIdStr();
			String networkLocationNameStr=objDto.getSearchNetworkLocationNameStr();
			
			
			
			if(networkLocationIdStr==null || networkLocationIdStr.trim().equals(""))
			{
				getNetworkLocation.setNull(1,java.sql.Types.BIGINT);
			}
			else
			{
				getNetworkLocation.setLong(1, Long.parseLong(networkLocationIdStr));
			}
			
			if(networkLocationNameStr==null || networkLocationNameStr.trim().equals(""))
			{
				getNetworkLocation.setNull(2,java.sql.Types.VARCHAR);
			}
			else
			{
				getNetworkLocation.setString(2, networkLocationNameStr);
			}
			
			PagingSorting pagingSorting = objDto.getPagingSorting();
			pagingSorting.sync();// To calculate start  index and Enc Index
			
			getNetworkLocation.setString(3, pagingSorting.getSortByColumn());//columnName
			getNetworkLocation.setString(4, PagingSorting.DB_Asc_Desc(pagingSorting.getSortByOrder()));//sort order
			getNetworkLocation.setInt(5, pagingSorting.getStartRecordId());//start index
			getNetworkLocation.setInt(6, pagingSorting.getEndRecordId());//end index
			rs = getNetworkLocation.executeQuery();
			
			int countFlag=0;
			while(rs.next()!=false)
			{   
				countFlag++;
			
				userName=(rs.getString("FNAME"))+" "+(rs.getString("LNAME"));
				objDto=  new NetworkLocationDto();				
				objDto.setCustomerName(userName);
				objDto.setNetworkLocationId(""+ rs.getInt("LOCATION_CODE"));
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
				
				pagingSorting.setRecordCount(recordCount);

//				if(pagingSorting.isPagingToBeDone())
//				{
//					pagingSorting.setRecordCount(rs.getInt("FULL_REC_COUNT"));
//				}

			}
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
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//added by nagarjuna 
			//ex.printStackTrace();
			//throw new Exception("SQL Exception : "+ ex.getMessage(), ex);
		}
		finally
		{
			try 
			{
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(getNetworkLocation);
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
	
	public NetworkLocationDto viewUserProfile(Connection conn, int networkLocationId) throws Exception
	{
		//		Added by Nagarjuna
		String methodName="viewUserProfile",  msg="";
		boolean logToFile=true, logToConsole=true;
		//End Nagarjuna

		ResultSet rs = null;
		
		//StringBuffer sQuery=new StringBuffer();
		//String userName="";
		NetworkLocationDto objDto=  new NetworkLocationDto();
		CallableStatement getAllNetworkLocationReport =null;
		try
		{	

		
			
			getAllNetworkLocationReport= conn.prepareCall(sqlGetNetworkLocationDetails);
			//getAllNetworkLocationReport.setInt(1,NetworkLocationId);
			
			getAllNetworkLocationReport.setLong(1, networkLocationId);
			getAllNetworkLocationReport.setNull(2, java.sql.Types.VARCHAR);
			
			rs = getAllNetworkLocationReport.executeQuery();
			while(rs.next())
			{
				objDto=  new NetworkLocationDto();				
				objDto.setNetworkLocationId(""+ rs.getInt("LOCATION_CODE"));
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
			
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//added by nagarjuna 
			//ex.printStackTrace();
			//throw new Exception("SQL Exception : "+ ex.getMessage(), ex);
		}
		finally
		{
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(getAllNetworkLocationReport);
		}
		
		return objDto;
	}
	public String addNewNetworkLocation(NetworkLocationDto objDto) throws Exception
	{
//		Added by Nagarjuna
		String methodName="addNewNetworkLocation",  msg1="";
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

			proc=conn.prepareCall(" {CALL IOE.SP_NETWORK_LOCATION_INSERT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			proc.setLong(1,0);//NetworkLocation_ID
			proc.setInt(2,objDto.getUpdateFlag());//update Flag
			proc.setString(3,objDto.getFirstname());
			proc.setString(4,objDto.getLastName());
			proc.setString(5,objDto.getTelephonePhno());
			proc.setString(6,objDto.getEmail_Id());
			proc.setString(7,objDto.getPin());
			proc.setString(8,objDto.getFax());
			proc.setString(9,objDto.getTitle());
			proc.setString(10,objDto.getAddress1());
			proc.setString(11,objDto.getAddress2());
			proc.setString(12,objDto.getAddress3());
			proc.setString(13,objDto.getAddress4());
			proc.setLong(14,Long.parseLong(objDto.getCity_Id()));
			proc.setLong(15,Long.parseLong(objDto.getState_Id()));
			proc.setLong(16,Long.parseLong(objDto.getPostalCode()));
			proc.setLong(17,Long.parseLong(objDto.getCountryCode()));
			proc.setInt(18,0);
			proc.setInt(19,0);
			proc.setString(20,"");
			
			proc.execute();	
			String msg=proc.getString(20);

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
				Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, "Error Message :"+proc.getString(20), logToFile, logToConsole);//added by nagarjuna 
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
	
	public String updateNetworkLocation(NetworkLocationDto objDto) throws Exception
	{
		//logger.info("AddEditUserDaoImpl's updateUser mehtod");
//		Added by Nagarjuna
		String methodName="updateNetworkLocation",  msg1="";
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

			proc=conn.prepareCall(" {CALL IOE.SP_NETWORK_LOCATION_INSERT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			
			proc.setLong(1,Long.parseLong(objDto.getNetworkLocationId()));//NetworkLocation_ID
			proc.setInt(2,objDto.getUpdateFlag());//update Flag
			proc.setString(3,objDto.getFirstname());
			proc.setString(4,objDto.getLastName());
			proc.setString(5,objDto.getTelephonePhno());
			proc.setString(6,objDto.getEmail_Id());
			proc.setString(7,null);
			proc.setString(8,objDto.getFax());
			proc.setString(9,objDto.getTitle());
			proc.setString(10,objDto.getAddress1());
			proc.setString(11,objDto.getAddress2());
			proc.setString(12,objDto.getAddress3());
			proc.setString(13,objDto.getAddress4());
			proc.setLong(14,Long.parseLong(objDto.getCity_Id()));
			proc.setLong(15,Long.parseLong(objDto.getState_Id()));
			proc.setLong(16,Long.parseLong(objDto.getPostalCode()));
			proc.setLong(17,Long.parseLong(objDto.getCountryCode()));
			proc.setInt(18,0);
			proc.setInt(19,0);
			proc.setString(20,"");
			
			proc.execute();	
			String msg=proc.getString(20);

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
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, "Error Message  :"+proc.getString(20), logToFile, logToConsole);//added by nagarjuna 
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
