package com.ibm.ioes.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.ibm.ioes.beans.MasterAttributesBean;
import com.ibm.ioes.exception.IOESException;
import com.ibm.ioes.forms.ChangeOrderTypeDto;
import com.ibm.ioes.forms.MastersAttributesDto;
import com.ibm.ioes.forms.NewOrderDto;
import com.ibm.ioes.utilities.AppUtility;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Utility;

public class ChangeOrderTypeDao extends CommonBaseDao {

	public static String sqlGetChangeTypes = "{call IOE.SP_GET_CHANGE_ORDER_TYPES()}";
	
	public static String addupdateChangeTypes = "{call IOE.SP_ADDUPDATE_CHANGE_ORDER_TYPES(?,?,?,?,?,?)}";
	public static String sqlGetQuoteNoList="{call IOE.SP_GET_QUOTESNO_DETAILS(?)}";

	public ArrayList<ChangeOrderTypeDto> viewChangeOrderTypeList()
			throws Exception {
		//Added by Nagarjuna
		String methodName="viewChangeOrderTypeList",  msg="";
		boolean logToFile=true, logToConsole=true;
		//end
		ArrayList<ChangeOrderTypeDto> objTypeList = new ArrayList<ChangeOrderTypeDto>();
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement getTypes = null;
		ChangeOrderTypeDto objDto;// 

		try {
			conn = DbConnection.getConnectionObject();

			getTypes = conn.prepareCall(sqlGetChangeTypes);

			rs = getTypes.executeQuery();
			while (rs.next() != false) {

				objDto = new ChangeOrderTypeDto();
				objDto.setChangeTypeId(rs.getString("CHANGETYPEID"));
				objDto.setChangeTypeName(rs.getString("CHANGETYPENAME"));
				objDto.setStatus(AppUtility.fnCheckNull(rs.getString("STATUS")));
				objTypeList.add(objDto);

			}
		} catch (Exception ex) {
			throw Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//added by nagarjuna 
			
		} finally {
			try {
				DbConnection.closeCallableStatement(getTypes);
				DbConnection.closeResultset(rs);
				DbConnection.freeConnection(conn);

			} catch (Exception e) {
				Utility.LOG(true,true,"Exception while closing connection   : "+e);//Added by Nagarjuna
				//e.printStackTrace();
				//throw new Exception("Exception : " + e.getMessage(), e);
			}
		}
		return objTypeList;
	}
	public String addChangeOrderType(ChangeOrderTypeDto objDto) throws Exception {
		//Added by Nagarjuna
		String methodName="addChangeOrderType",  msg="";
		boolean logToFile=true, logToConsole=true;
		//end
		ChangeOrderTypeDto dto;
		Connection conn = null;
		String status = "";
		CallableStatement cstmt=null;
		boolean transactionDone = true;
		try {
			conn = DbConnection.getConnectionObject();
			conn.setAutoCommit(false);
			 cstmt= conn.prepareCall(addupdateChangeTypes);

				cstmt.setLong(1,0);
				cstmt.setString(2,objDto.getChangeTypeName());
				cstmt.setString(3,objDto.getStatus());
				cstmt.setInt(4,1);
				cstmt.setString(5,"1");
				cstmt.setString(6,"");
				cstmt.execute();
				status = cstmt.getString(6);
				if (!status.equalsIgnoreCase("SUCCESS")) {
					transactionDone = false;
				}
			

		} catch (Exception ex) {
			//Utility.LOG(true,true,"Exception    : "+ex);//Added by Nagarjuna
			throw Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, status, logToFile, logToConsole);//added by nagarjuna 
			
		} finally {
			try {
				if (transactionDone == true) {
					conn.commit();

				} else {
					conn.rollback();

				}
				DbConnection.closeCallableStatement(cstmt);
				DbConnection.freeConnection(conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				Utility.LOG(true,true,"Error While Closing Connection    : "+e);//Added by Nagarjuna
				//e.printStackTrace();
			}
		}
		return status;
	}
	
	public String updateChangeOrderTypeList(
			ArrayList<ChangeOrderTypeDto> objDtoList) throws Exception {
		//Added by Nagarjuna
		String methodName="updateChangeOrderTypeList",  msg="";
		boolean logToFile=true, logToConsole=true;
		//End
		ChangeOrderTypeDto objDto;
		Connection conn = null;
		String status = "";
		CallableStatement cstmt=null;
		boolean transactionDone = true;
		try {
			conn = DbConnection.getConnectionObject();
			conn.setAutoCommit(false);
			for (int count = 0; count < objDtoList.size(); count++) {
				objDto = (ChangeOrderTypeDto) objDtoList.get(count);
				cstmt= conn.prepareCall(addupdateChangeTypes);

				cstmt.setString(1, objDto.getChangeTypeId());
				cstmt.setString(2, objDto.getChangeTypeName());
				cstmt.setString(3, objDto.getStatus());
				cstmt.setInt(4,2);
				cstmt.setString(5,"1");
				cstmt.setString(6,"");
				cstmt.execute();
				status = cstmt.getString(6);
				if (!status.equalsIgnoreCase("SUCCESS")) {
					transactionDone = false;
					break;
				}
			}

		} catch (Exception ex) {
			//Utility.LOG(true,true,"Exception    : "+ex);//Added by Nagarjuna
			throw Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, status, logToFile, logToConsole);//added by nagarjuna 
		} finally {
			try {
				if (transactionDone == true) {
					conn.commit();

				} else {
					conn.rollback();

				}
				DbConnection.closeCallableStatement(cstmt);
		DbConnection.freeConnection(conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				Utility.LOG(true,true,"error while closing connection   : "+e);//added by Nagarjuna
				//e.printStackTrace();
			}
		}
		return status;
	}
	
	
}
