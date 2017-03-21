package com.ibm.ioes.dao;

import com.ibm.ioes.forms.RoleAcessMatrixDto;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Utility;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;


public class RoleAcessMatrixDao extends CommonBaseDao {
	public static String sqlChangeUserInterfaceMapping = "{call IOE.SP_UPDATE_ACCESS_DETAILS(?,?,?,?,?)}";
	
	public static String sqlGetInterfaceData = "{call IOE.SP_GET_INTERFACEDATA(?,?)}";
	private static final Logger logger;
	
	private static final String getRoles = "{ call IOE.SP_GET_ALL_ROLES()}";
	private static final String getModules= "{ call IOE.SP_GET_ALL_MODULES()}";
	static 
	{
		logger = Logger.getLogger(RoleAcessMatrixDao.class);
	}
	
	/**
	 * retrieves the list of all roles of system. 
	 * 
	 * @return list of all roles.
	 */
	public ArrayList<RoleAcessMatrixDto> getRoleList() 
	{
//		Added by nagarjuna
		String methodName="getRoleList",  msg="";
		boolean logToFile=true, logToConsole=true;
		//	End nagarjuna
		Connection connection =null;
		ArrayList<RoleAcessMatrixDto> listRole = new ArrayList<RoleAcessMatrixDto>();
		RoleAcessMatrixDto accessobjDto = null;
		CallableStatement cstmt=null;
		ResultSet rs=null;
		
		try
		{
			connection=DbConnection.getConnectionObject();
			String sql=getRoles;
			cstmt= connection.prepareCall(sql);
			rs=cstmt.executeQuery();
			  while(rs.next())
			  {
				  accessobjDto =  new RoleAcessMatrixDto();
				  accessobjDto.setRoleName(rs.getString("ROLENAME"));
				  accessobjDto.setRoleID(rs.getInt("ROLEID"));
				  listRole.add(accessobjDto);
			  }
			  return listRole;
		}
		catch(Exception ex)
		{
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,"Exception"+msg, logToFile, logToConsole);//added by nagarjuna 
			//ex.printStackTrace();	
		}finally {
			try {
				DbConnection.closeResultset(rs);
				
				DbConnection.closePreparedStatement(cstmt);
				DbConnection.freeConnection(connection);
	
			} catch (Exception e) {
				Utility.LOG(true, true, "Exception "+e);//added by nagarjuna
				//e.printStackTrace();
			}
		}
		return listRole;
	}
	
	/**
	 * retrieves the list of all modules of system. 
	 * 
	 * @return list of all modules.
	 */
	public ArrayList<RoleAcessMatrixDto> getModuleList() 
	{
//		Added by nagarjuna
		String methodName="getModuleList",  msg="";
		boolean logToFile=true, logToConsole=true;
		//	End nagarjuna
		Connection connection =null;
		ArrayList<RoleAcessMatrixDto> listModule= new ArrayList<RoleAcessMatrixDto>();
		RoleAcessMatrixDto accessobjDto = null;
		CallableStatement cstmt=null;
		ResultSet rs=null;
		
		try
		{
			connection=DbConnection.getConnectionObject();
			String sql=getModules;
			cstmt= connection.prepareCall(sql);
			rs=cstmt.executeQuery();
			  while(rs.next())
			  {
				  accessobjDto =  new RoleAcessMatrixDto();
				  accessobjDto.setModuleName(rs.getString("MODULENAME"));
				  accessobjDto.setModID(rs.getInt("MODULEID"));
				  listModule.add(accessobjDto);
			  }
			  return listModule;
		}
		catch(Exception ex)
		{
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,"Exception"+msg, logToFile, logToConsole);//added by nagarjuna 
			//ex.printStackTrace();	
		}finally {
			try {
				DbConnection.closeResultset(rs);
				DbConnection.closePreparedStatement(cstmt);
				DbConnection.freeConnection(connection);
		
			} catch (Exception e) {
				Utility.LOG(true, true, "Exception "+e);//added by nagarjuna
				//e.printStackTrace();
			}
		}
		return listModule;
	}
	
	/**
	 * retreives the details of interfaces 
	 * @param roleID 
	 * @param moduleID
	 * @return list of dto having interface details
	 */
	public ArrayList<RoleAcessMatrixDto> getInterfaceList(String roleID,String moduleID) 
	{
//		Added by nagarjuna
		String methodName="getInterfaceList",  msg="";
		boolean logToFile=true, logToConsole=true;
		//	End nagarjuna
		Connection connection =null;
		ResultSet rsInterfaceList = null;
		ArrayList<RoleAcessMatrixDto>interfaceDetails = new ArrayList<RoleAcessMatrixDto>();
		RoleAcessMatrixDto accessobjDto = null;
		CallableStatement getInterfaceList = null;
		try
		{
			connection=DbConnection.getConnectionObject();
			getInterfaceList = connection.prepareCall(sqlGetInterfaceData);
			getInterfaceList.setString(1,roleID);
			getInterfaceList.setString(2,moduleID);
			
			//proc=connection.prepareCall(getInterfaceList);
			rsInterfaceList = getInterfaceList.executeQuery();
			  while(rsInterfaceList.next())
			  {
				  accessobjDto =  new RoleAcessMatrixDto();
				  accessobjDto.setInterfaceID(rsInterfaceList.getInt("INTERFACEID"));
				  accessobjDto.setInterfaceName(rsInterfaceList.getString("INTERFACENAME"));
				  accessobjDto.setAccessFlag(rsInterfaceList.getInt("ACCESSFLAG"));
				  interfaceDetails.add(accessobjDto);
			  }
			  return interfaceDetails;
		}
		catch(Exception ex)
		{
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,"Exception"+msg, logToFile, logToConsole);//added by nagarjuna 
			//ex.printStackTrace();	
		}finally {
			try {
				DbConnection.closeResultset(rsInterfaceList);
				DbConnection.closePreparedStatement(getInterfaceList);
				DbConnection.freeConnection(connection);
			
			} catch (Exception e) {
				Utility.LOG(true, true, "Exception "+e);//added by nagarjuna
				//e.printStackTrace();
			}
		}
		return interfaceDetails;
	}

	/**
	 * update the accessFlag of interfaces as updated by user.
	 * @param allowedInterfaceId
	 * @param prohibitedInterfaceId
	 * @param roleId
	 * @return status
	 * @throws SQLException
	 */
	public int setUserMappedInterfaces(ArrayList allowedInterfaceId, ArrayList prohibitedInterfaceId, String roleId) throws SQLException 
	{
		//logger.info(" Entered into setUserMappedInterfaces method of " + this.getClass().getSimpleName());
//		Added by nagarjuna
		String methodName="setUserMappedInterfaces",  msg="";
		boolean logToFile=true, logToConsole=true;
		//	End nagarjuna
		int retCode = 0;
		String strinsertNewInterfaceId = "", strEnabledInterfaceId = "", strDisabledInterfaceId = "";
		ArrayList<String> insertNewInterfaceId = new ArrayList<String>();
		ArrayList<String> updateEnableInterfaceId = new ArrayList<String>();
		ArrayList<String> updateDisableInterfaceId = new ArrayList<String>();
		Connection con = null;		
		CallableStatement csChangeUserAccess = null;
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
			
			con = DbConnection.getConnectionObject();
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
			csChangeUserAccess.setString(5, "0");
			csChangeUserAccess.execute();
			
			con.commit();
			retCode=1;
		}
		catch(Exception ex)
		{
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,"Exception"+msg, logToFile, logToConsole);//added by nagarjuna 
			con.rollback();
			//ex.printStackTrace();
			logger.error(ex.getMessage()
					+ " Exception occured while closing database objects in fetchBcpDetails method of "
					+ this.getClass().getSimpleName());
		}
		finally
		{
			try 
			{
				DbConnection.closeCallableStatement(csChangeUserAccess);
				DbConnection.freeConnection(con);
			} 
			catch (Exception e) 
			{
				Utility.LOG(true, true, "Exception "+e);//added by nagarjuna
				//e.printStackTrace();
			}
		}
		return retCode;
	}
}
