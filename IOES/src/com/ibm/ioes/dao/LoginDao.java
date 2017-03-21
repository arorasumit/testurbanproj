package com.ibm.ioes.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.ibm.ioes.forms.LoginDto;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Utility;

public class LoginDao extends CommonBaseDao {
	public static String sqlGetModuleInterface = "{call IOE.sp_Get_Module_Interface_List(?)}";//To Fetch All Account List from Database
	public static String sqlGetModuleInterfaceReport = "{call IOE.SP_GET_MODULE_INTERFACE_REPORT(?)}";//To Fetch All Account List from Database

	//Method used for Fetching All Accounts
	public LoginDto getModuleInterfaceList(String roleId) throws Exception {
//		Added by Nagarjuna
		String methodName="getModuleInterfaceList",  msg1="";
		boolean logToFile=true, logToConsole=true;
		//End Nagarjuna
		ArrayList<LoginDto> listInterfaceDetails = new ArrayList<LoginDto>();
		Connection conn = null;
		CallableStatement cstmt = null;
		LoginDto objDtoForInterface = null;
		LoginDto objDto = new LoginDto();
		ResultSet rs = null;
		try {
			conn = DbConnection.getConnectionObject();
			cstmt = conn.prepareCall(sqlGetModuleInterface);
			cstmt.setLong(1, Long.parseLong(roleId));
			rs = cstmt.executeQuery();
			while (rs.next()) {

				objDtoForInterface = new LoginDto();

				objDtoForInterface.setUrl(rs.getString("URL"));
				objDtoForInterface.setModuleId(rs.getString("MODULEID"));
				objDtoForInterface.setModuleName(rs.getString("MODULENAME"));
				objDtoForInterface.setInterfaceId(rs.getString("INTERFACEID"));
				objDtoForInterface.setInterfaceName(rs.getString("INTERFACENAME"));

				listInterfaceDetails.add(objDtoForInterface);
			}
			objDto.setListInterfaceDetails(listInterfaceDetails);

		} catch (Exception ex) {
			
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg1, logToFile, logToConsole);//added by nagarjuna 
			//ex.printStackTrace();
			//throw new Exception("SQL Exception : " + ex.getMessage(), ex);
		}
		finally
		{
			try {
				DbConnection.closeResultset(rs);	
				DbConnection.closeCallableStatement(cstmt);
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				Utility.LOG(true,true,"error while closing connection   : "+e);//added by Nagarjuna
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		return objDto;
	}

	public LoginDto getModuleInterfaceListForReportLink(String userID) throws Exception {
		//Added by Nagarjuna
		String methodName="getModuleInterfaceListForReport",  msg1="";
		boolean logToFile=true, logToConsole=true;
		//End Nagarjuna
		ArrayList<LoginDto> listInterfaceDetails = new ArrayList<LoginDto>();
		Connection conn = null;
		CallableStatement cstmt = null;
		LoginDto objDtoForInterface = null;
		LoginDto objDto = new LoginDto();
		ResultSet rs = null;
		try {
			conn = DbConnection.getConnectionObject();
			cstmt = conn.prepareCall(sqlGetModuleInterfaceReport);
			cstmt.setString(1, userID);
			rs = cstmt.executeQuery();
			while (rs.next()) {
				objDtoForInterface = new LoginDto();

				objDtoForInterface.setUrl(rs.getString("URL"));
				objDtoForInterface.setModuleId(rs.getString("MODULEID"));
				objDtoForInterface.setModuleName(rs.getString("MODULENAME"));
				objDtoForInterface.setInterfaceId(rs.getString("INTERFACEID"));
				objDtoForInterface.setInterfaceName(rs.getString("INTERFACENAME"));
				listInterfaceDetails.add(objDtoForInterface);
			}
			objDto.setListInterfaceDetails(listInterfaceDetails);

		} catch (Exception ex) {
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg1, logToFile, logToConsole);//added by nagarjuna 
		}
		finally
		{
			try {
				DbConnection.closeResultset(rs);	
				DbConnection.closeCallableStatement(cstmt);
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				Utility.LOG(true,true,"error while closing connection   : "+e);//added by Nagarjuna
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		return objDto;
	}
}
