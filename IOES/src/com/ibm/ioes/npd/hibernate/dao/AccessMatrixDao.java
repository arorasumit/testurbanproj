package com.ibm.ioes.npd.hibernate.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ibm.ioes.forms.UserAccessMatrixDto;
import com.ibm.ioes.npd.hibernate.beans.AccessMatrixDto;
import com.ibm.ioes.npd.hibernate.beans.RoleSelectionDto;
import com.ibm.ioes.npd.utilities.NpdConnection;
import com.ibm.ioes.npd.utilities.Utility;
import com.ibm.ioes.utilities.DbConnection;

public class AccessMatrixDao extends CommonBaseDao
{
	public static String sqlChangeUserInterfaceMapping = "{call NPD.spUpdateUserAccessDetails(?,?,?,?)}";
	
	public static String sqlGetInterfaceData = "{call NPD.spGetInterfaceData(?,?)}";
	private static final Logger logger;
	static 
	{
		logger = Logger.getLogger(AccessMatrixDao.class);
	}
	
	public ArrayList<UserAccessMatrixDto> getActiveDeactivatedRoles(UserAccessMatrixDto objDto) 
	{
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rsRoleList = null;
		ArrayList<UserAccessMatrixDto> listRole = new ArrayList<UserAccessMatrixDto>();
		UserAccessMatrixDto accessObjDto = null;
		
		try
		{
			connection=DbConnection.getConnectionObject();
			String getExistingAccountDetails = "SELECT * FROM IOE.TM_ACCOUNTROLEDETAILS a inner join ioe.TM_ACCOUNTROLE b " +
					"on a.ROLEID=b.ROLEID WHERE EMPLOYEEID = ?";
			//String getExistingAccountDetails = "SELECT * FROM IOE.TM_ACCOUNTROLEDETAILS WHERE EMPLOYEEID = ?";
			proc=connection.prepareCall(getExistingAccountDetails);
			proc.setLong(1,objDto.getEmpId());
			rsRoleList = proc.executeQuery();
			  while(rsRoleList.next())
			  {
				  accessObjDto =  new UserAccessMatrixDto();
				  
				  accessObjDto.setRoleId(Long.valueOf(rsRoleList.getInt("ROLEID")));
				  accessObjDto.setEmpId(Long.valueOf(rsRoleList.getInt("EMPLOYEEID")));
				  accessObjDto.setFIRSTNAME(rsRoleList.getString("FIRSTNAME"));
				  accessObjDto.setLASTNAME(rsRoleList.getString("LASTNAME"));
				  accessObjDto.setPHONENO(""+rsRoleList.getLong("PHONENO"));
				  accessObjDto.setEMAILID(rsRoleList.getString("EMAILID"));
				  accessObjDto.setUserId(rsRoleList.getString("USER_ID"));
				  accessObjDto.setRoleName(rsRoleList.getString("ROLENAME"));
				  accessObjDto.setPHONE_NO(rsRoleList.getString("PHONE_NO"));
				  accessObjDto.setLOGIN_IP(rsRoleList.getString("LOGIN_IP"));
				  accessObjDto.setLOGIN_STATUS(""+rsRoleList.getLong("LOGIN_STATUS"));
				  accessObjDto.setLAST_ROLE_ACCESSED(""+rsRoleList.getLong("LAST_ROLE_ACCESSED"));
				  accessObjDto.setLAST_LOGGED_IN_TIME(""+rsRoleList.getTimestamp("LAST_LOGGED_IN_TIME"));
				  accessObjDto.setLAST_LOGGED_OUT_TIME(""+rsRoleList.getTimestamp("LAST_LOGGED_OUT_TIME"));
				  accessObjDto.setLOGIN_STATUS(""+rsRoleList.getLong("LOGIN_STATUS"));
				  accessObjDto.setIS_ACTIVE((""+rsRoleList.getLong("ISACTIVE")));
				  accessObjDto.setEMPLOYEENO(rsRoleList.getString("EMPLOYEENO"));
				  accessObjDto.setCREATED_BY(rsRoleList.getString("CREATED_BY"));
				  accessObjDto.setCREATED_DATE(rsRoleList.getString("CREATED_DATE"));
				  accessObjDto.setUPDATED_DATE(rsRoleList.getString("UPDATED_BY"));
				  accessObjDto.setUPDATED_DATE(rsRoleList.getString("UPDATED_DATE"));
				  accessObjDto.setORDER_NO(rsRoleList.getString("ORDER_NO"));
				  accessObjDto.setGAM_START_DATE(rsRoleList.getString("GAM_START_DATE"));
				  accessObjDto.setGAM_END_DATE(rsRoleList.getString("GAM_END_DATE"));
				  accessObjDto.setL1_EMPLOYEEID(rsRoleList.getString("L1_EMPLOYEEID"));
				  accessObjDto.setL2_EMPLOYEEID(rsRoleList.getString("L2_EMPLOYEEID"));
				  accessObjDto.setL3_EMPLOYEEID(rsRoleList.getString("L3_EMPLOYEEID"));
				  
				  listRole.add(accessObjDto);
			  }
			  return listRole;
		}
		catch(Exception ex)
		{
			com.ibm.ioes.utilities.Utility.LOG(true, true, ex, "error in getActiveDeactivatedRoles method");
		}finally {
			try {
				DbConnection.closeResultset(rsRoleList);
				DbConnection.closeCallableStatement(proc);
				DbConnection.freeConnection(connection);
				//DbConnection.freeConnection(connection);
			} catch (Exception e) {
				com.ibm.ioes.utilities.Utility.LOG(true, true, e, "error in getActiveDeactivatedRoles method");
			}
		}
		return listRole;
	}
	
	
	public ArrayList<AccessMatrixDto> getRoleList(AccessMatrixDto objDto) 
	{
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rsRoleList = null;
		ArrayList<AccessMatrixDto> listRole = new ArrayList<AccessMatrixDto>();
		AccessMatrixDto AccessobjDto = null;
		
		try
		{
			connection=NpdConnection.getConnectionObject();
			String getNPDCategoryList = null;
			getNPDCategoryList= "Select ROLEID,ROLENAME from NPD.TM_ROLES WHERE ISACTIVE=1";
						  
			String FullOrderBy=" ORDER BY ROLENAME ASC";
			getNPDCategoryList=getNPDCategoryList+FullOrderBy;
			
			proc=connection.prepareCall(getNPDCategoryList);
			rsRoleList = proc.executeQuery();
			  while(rsRoleList.next())
			  {
				  AccessobjDto =  new AccessMatrixDto();
				  AccessobjDto.setRoleName(rsRoleList.getString("ROLENAME"));
				  AccessobjDto.setRoleID(rsRoleList.getInt("ROLEID"));
				  listRole.add(AccessobjDto);
			  }
			  return listRole;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}finally {
			try {
				DbConnection.closeResultset(rsRoleList);
				DbConnection.closeCallableStatement(proc);
				NpdConnection.freeConnection(connection);
				//DbConnection.freeConnection(connection);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return listRole;
	}
	
	public ArrayList<AccessMatrixDto> getinterfaceList(String RoleID,String moduleID) 
	{
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rsInterfaceList = null;
		ArrayList<AccessMatrixDto> InterfaceDetails = new ArrayList<AccessMatrixDto>();
		AccessMatrixDto AccessobjDto = null;
		CallableStatement getInterfaceList = null;
		try
		{
			connection=NpdConnection.getConnectionObject();
			//String getInterfaceList = null;
			//getInterfaceList= "Select * from NPD.VW_INTERFACEDISPLAY";
			
			getInterfaceList = connection.prepareCall(sqlGetInterfaceData);
			getInterfaceList.setString(1,RoleID);
			getInterfaceList.setString(2,moduleID);
			
			//proc=connection.prepareCall(getInterfaceList);
			rsInterfaceList = getInterfaceList.executeQuery();
			  while(rsInterfaceList.next())
			  {
				  AccessobjDto =  new AccessMatrixDto();
				  AccessobjDto.setInterfaceID(rsInterfaceList.getInt("INTERFACEID"));
				  AccessobjDto.setInterfaceName(rsInterfaceList.getString("INTERFACENAME"));
				  AccessobjDto.setAccessFlag(rsInterfaceList.getInt("ACCESSFLAG"));
				  InterfaceDetails.add(AccessobjDto);
			  }
			  return InterfaceDetails;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}finally {
			try {
				DbConnection.closeResultset(rsInterfaceList);
				DbConnection.closeCallableStatement(proc);
				NpdConnection.freeConnection(connection);
				//DbConnection.freeConnection(connection);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return InterfaceDetails;
	}

	public int setUserMappedInterfaces(ArrayList allowedInterfaceId, ArrayList prohibitedInterfaceId, String roleId) throws SQLException 
	{
		//logger.info(" Entered into setUserMappedInterfaces method of " + this.getClass().getSimpleName());
		
		int retCode = 0;
		String strinsertNewInterfaceId = "", strEnabledInterfaceId = "", strDisabledInterfaceId = "";
		ArrayList<String> insertNewInterfaceId = new ArrayList<String>();
		ArrayList<String> updateEnableInterfaceId = new ArrayList<String>();
		ArrayList<String> updateDisableInterfaceId = new ArrayList<String>();
		Connection con = null;		
		CallableStatement csChangeUserAccess = null;
		ResultSet rsMatrixId = null;
		ResultSet rs=null;
		try
		{
			for(int i=0;i<allowedInterfaceId.size();i++)
			{
				String tempStr = allowedInterfaceId.get(i).toString();
				String[] tempArrStr = tempStr.split("#");
				if(tempArrStr[1].equals("2"))
				{
					insertNewInterfaceId.add(tempArrStr[0]);
				}
				else
				{
					updateEnableInterfaceId.add(tempArrStr[0]);
				}
			}
			for(int j=0 ; j<prohibitedInterfaceId.size() ; j++)
			{
				String tempStr = prohibitedInterfaceId.get(j).toString();
				String[] tempArrStr = tempStr.split("#");
				updateDisableInterfaceId.add(tempArrStr[0]);
			}
			
			NpdConnection connectionClassObj = new NpdConnection();
			con = connectionClassObj.getConnectionObject();
			rs=con.createStatement().executeQuery("SELECT 1 FROM SYSIBM.SYSDUMMY1");
			rs.next();
			String st1r=rs.getString(1);
			
			//con.setAutoCommit(false);
			
			csChangeUserAccess = con.prepareCall(sqlChangeUserInterfaceMapping);
			csChangeUserAccess.setString(1,roleId);				
			
			if(insertNewInterfaceId.size()>0)
			{	
				String str = insertNewInterfaceId.toString();
				strinsertNewInterfaceId = str.substring(1,(str.length()-1)).replaceAll(" ", "");
			}
			if(updateEnableInterfaceId.size()>0)
			{	
				String str2 = updateEnableInterfaceId.toString();
				strEnabledInterfaceId = str2.substring(1,(str2.length()-1)).replaceAll(" ", "");
			}
			
			if(updateDisableInterfaceId.size()>0)
			{
				String str3 = updateDisableInterfaceId.toString();
				strDisabledInterfaceId = str3.substring(1,(str3.length()-1)).replaceAll(" ", "");				
			}	
			
			csChangeUserAccess.setString(2,strEnabledInterfaceId);
			csChangeUserAccess.setString(3,strDisabledInterfaceId);
			csChangeUserAccess.setString(4,strinsertNewInterfaceId);
			csChangeUserAccess.execute();
			
			con.commit();
			retCode=1;
		}
		catch(Exception ex)
		{
			con.rollback();
			ex.printStackTrace();
			logger.error(ex.getMessage()
					+ " Exception occured while closing database objects in fetchBcpDetails method of "
					+ this.getClass().getSimpleName());
		}
		finally
		{
			try 
			{
				DbConnection.closeResultset(rs);
				DbConnection.closeResultset(rsMatrixId);
				DbConnection.closeCallableStatement(csChangeUserAccess);
				NpdConnection.freeConnection(con);
				//csChangeUserAccess.close();
				//DbConnection.freeConnection(conn);;
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		return retCode;
	}

	public ArrayList<AccessMatrixDto> getModuleList() 
	{
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rsRoleList = null;
		ArrayList<AccessMatrixDto> listModuleList = new ArrayList<AccessMatrixDto>();
		AccessMatrixDto AccessMatrixDto = null;
		
		try
		{
			connection=NpdConnection.getConnectionObject();
			String getModuleList = null;
			getModuleList= "Select * from NPD.TM_MODULEMASTER WHERE ISACTIVE=1";
									  
			proc=connection.prepareCall(getModuleList);
			rsRoleList = proc.executeQuery();
			  while(rsRoleList.next())
			  {
				  AccessMatrixDto =  new AccessMatrixDto();
				  AccessMatrixDto.setModuleName(rsRoleList.getString("MODULENAME"));
				  AccessMatrixDto.setModID(rsRoleList.getInt("MODULEID"));
				  listModuleList.add(AccessMatrixDto);
			  }
			  //return listModuleList;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		finally
		{
			try 
			{
				DbConnection.closeResultset(rsRoleList);
				DbConnection.closeCallableStatement(proc);
				DbConnection.freeConnection(connection);
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return listModuleList;
	}
}
