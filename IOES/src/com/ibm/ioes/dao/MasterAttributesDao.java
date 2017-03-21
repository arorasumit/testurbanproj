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
import com.ibm.ioes.forms.MastersAttributesDto;
import com.ibm.ioes.utilities.AppUtility;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Utility;

public class MasterAttributesDao extends CommonBaseDao {

	public static String sqlGetMainAttributes = "{call IOE.SP_GET_MAIN_ATTRIBUTES()}";

	//public static String sqlGetMainAttributeDetails = "{call IOE.SP_GET_MAIN_ATTRIBUTES_DETAILS(?)}";

	public static String sqlUpdateMainAttribute = "{call IOE.SP_UPDATE_MAIN_ATTRIBUTES_DETAILS(?,?,?,?,?,?)}";

	private static final String spGetAllServicesAttributes = "{ call IOE.SP_GET_SERVICE_ATTRIBUTES(?)}";

	private static final String updateServicesAttributes = "{ call IOE.SP_UPDATE_SERVICE_ATTRIBUTES(?,?,?,?,?,?)}";

	private static final String spGetAllProducts = "{ call IOE.GETSERVICEDETAILLIST(?)}";

	private static final String spGetAllProductAttributes = "{ call IOE.GETSERVICEATTRIBUTEMASTER(?)}";

	private static final String updateProductAttributes = "{ call IOE.SP_UPDATE_SERVICEDETAIL_ATTRIBUTES(?,?,?,?,?,?)}";
	
	private static final String spGetAllProductConfigAttributes="{ call IOE.SP_GET_PRODUCT_CONFIG_DETAILS(?)}";
	
	private static final String updateProductConfigAttributes = "{ call IOE.SP_UPDATE_PRODUCT_CONFIG_DETAILS(?,?,?,?,?,?,?,?,?,?,?,?,?)}";

	public ArrayList<MastersAttributesDto> viewAttributesList()
			throws Exception {
		//		Added by Nagarjuna
		String methodName="viewAttributesList",  msg1="";
		boolean logToFile=true, logToConsole=true;
		//End Nagarjuna

		ArrayList<MastersAttributesDto> objUserList = new ArrayList<MastersAttributesDto>();
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement getAttributes = null;
		MastersAttributesDto objDto;// = new MastersAttributesDto();

		try {
			conn = DbConnection.getConnectionObject();

			getAttributes = conn.prepareCall(sqlGetMainAttributes);

			rs = getAttributes.executeQuery();
			while (rs.next() != false) {

				objDto = new MastersAttributesDto();
				objDto.setAttAliasName(rs.getString("ALISNAME"));
				objDto.setAttDataType((AppUtility.fnCheckNull(rs
						.getString("ATTTYPE")).toUpperCase()));
				objDto.setAttDescription(rs.getString("ATTLABEL"));
				objDto.setAttExpectedValue((AppUtility.fnCheckNull(rs
						.getString("EXPECTEDVALUE")).toUpperCase()));
				objDto.setAttID(rs.getString("ATTRIBUTEID"));
				objDto.setAttIsmandatory((AppUtility.fnCheckNull(rs
						.getString("MANDATORY")).toUpperCase()));
				objDto.setAttMaxLenghth(rs.getString("ATTMAXLENGTH"));
				setApplicationProperties(objDto);
				objUserList.add(objDto);

			}
		} catch (Exception ex) {
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg1, logToFile, logToConsole);//added by nagarjuna 
			//ex.printStackTrace();
			//throw new Exception("SQL Exception : " + ex.getMessage(), ex);
		} finally {
			try {
				DbConnection.closeResultset(rs);	
				DbConnection.closeCallableStatement(getAttributes);
				DbConnection.freeConnection(conn);

			} catch (Exception e) {
				Utility.LOG(true,true,"error while closing connection   : "+e);//added by Nagarjuna
				//e.printStackTrace();
				//throw new Exception("Exception : " + e.getMessage(), e);
			}
		}
		return objUserList;
	}

	public String updateMainAttributes(
			ArrayList<MastersAttributesDto> objDtoList) throws Exception {
		//		Added by Nagarjuna
		String methodName="updateMainAttributes",  msg1="";
		boolean logToFile=true, logToConsole=true;
		//End Nagarjuna

		MastersAttributesDto objDto;
		CallableStatement cstmt=null;
		Connection conn = null;
		String status = "";
		boolean transactionDone = true;
		try {
			conn = DbConnection.getConnectionObject();
			conn.setAutoCommit(false);
			for (int count = 0; count < objDtoList.size(); count++) {
				objDto = (MastersAttributesDto) objDtoList.get(count);
				cstmt= conn.prepareCall(sqlUpdateMainAttribute);
				cstmt.setString(1, objDto.getAttID());
				cstmt.setString(2, objDto.getAttDescription());
				cstmt.setString(3, objDto.getAttDataType());
				cstmt.setString(4, objDto.getAttExpectedValue());
				cstmt.setString(5, objDto.getAttIsmandatory());

				cstmt.setString(6, "");
				cstmt.execute();
				status = cstmt.getString(6);
				if (!status.equalsIgnoreCase("SUCCESS")) {
					transactionDone = false;
					break;
				}
			}

		} catch (Exception ex) {
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg1, logToFile, logToConsole);//added by nagarjuna 
			//ex.printStackTrace();
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
				Utility.LOG(true,true,"error while closing connection   : "+e);//added by Nagarjuna
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		return status;
	}

	public ArrayList<MastersAttributesDto> getServiceAttributeList(
			MasterAttributesBean objForm) throws IOESException {
//		Added by Nagarjuna
		String methodName="getServiceAttributeList",  msg1="";
		boolean logToFile=true, logToConsole=true;
		//End Nagarjuna

		Connection conn = null;
		CallableStatement cstmt =null;
		ArrayList<MastersAttributesDto> attributes = new ArrayList<MastersAttributesDto>();
		try {
			conn = DbConnection.getConnectionObject();
			cstmt= conn
					.prepareCall(spGetAllServicesAttributes);
			cstmt.setLong(1, Long.parseLong(objForm.getHiddenServiceTypeId()));
			ResultSet rs = cstmt.executeQuery();
			MastersAttributesDto dto = null;
			while (rs.next()) {
				dto = new MastersAttributesDto();
				dto.setAttID(rs.getString("ATTMASTERID"));
				dto.setAttDescription(rs.getString("ATTDESCRIPTION"));
				dto.setAttDataType((AppUtility.fnCheckNull(rs
						.getString("ATTDATATYPE")).toUpperCase()));
				dto.setAttAliasName(rs.getString("ALISNAME"));
				dto.setAttExpectedValue((AppUtility.fnCheckNull(rs
						.getString("EXPECTEDVALUE")).toUpperCase()));
				dto.setAttMaxLenghth(rs.getString("MAXLENGTH"));
				dto.setAttIsmandatory((AppUtility.fnCheckNull(rs
						.getString("MANDATORY")).toUpperCase()));
				setApplicationProperties(dto);
				attributes.add(dto);
			}

		} catch (Exception ex) {
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg1, logToFile, logToConsole);//added by nagarjuna 
			//ex.printStackTrace();
		}
		finally
		{
			try {
				DbConnection.closeCallableStatement(cstmt);
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				Utility.LOG(true,true,"error while closing connection   : "+e);//added by Nagarjuna
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		return attributes;
	}

	public void setApplicationProperties(MastersAttributesDto dto)
			throws IOESException {
//		Added by Nagarjuna
		String methodName="setApplicationProperties",  msg1="";
		boolean logToFile=true, logToConsole=true;
		//End Nagarjuna

		Map<String, String> applicationProperties = new HashMap();
		Statement statement = null;
		ResultSet resultSet = null;

		java.sql.Connection conn = null;
		try {
			String DATA_TYPES = "DATA_TYPES";
			String EXPECTED_VALUES = "EXPECTED_VALUES";
			String query = "SELECT * FROM IOE.TM_APPCONFIG WHERE KEYNAME='"
					+ DATA_TYPES + "' or KEYNAME='" + EXPECTED_VALUES + "'";
			conn = DbConnection.getConnectionObject();
			conn.setAutoCommit(false);
			statement = conn.createStatement();
			resultSet = statement.executeQuery(query);
			// conn.commit();
			while (resultSet.next()) {
				String key = resultSet.getString("KEYNAME");
				String value = resultSet.getString("KEYVALUE");
				if (key.equalsIgnoreCase("DATA_TYPES")) {
					applicationProperties.put("DATA_TYPES", value);
				} else if (key.equalsIgnoreCase("EXPECTED_VALUES")) {
					applicationProperties.put("EXPECTED_VALUES", value);
				}
			}

			dto.setApplicatlionPropertiesMap(applicationProperties);
			

		} catch (Exception e) {
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg1, logToFile, logToConsole);//added by nagarjuna 
			//e.printStackTrace();

			throw new IOESException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (statement != null)
					statement.close();
				if (conn != null)
					DbConnection.closeResultset(resultSet);
					DbConnection.closeStatement(statement);
					DbConnection.freeConnection(conn);
			} catch (Exception e) {
				Utility.LOG(true,true,"error while closing connection   : "+e);//added by Nagarjuna
			}
		}

	}

	public String updateServiceAttributes(
			ArrayList<MastersAttributesDto> objDtoList) throws IOESException {
//		Added by Nagarjuna
		String methodName="updateServiceAttributes",  msg1="";
		boolean logToFile=true, logToConsole=true;
		//End Nagarjuna

		MastersAttributesDto objDto;
		Connection conn = null;
		String status = "";
		CallableStatement cstmt= null;
		boolean transactionDone = true;
		try {
			conn = DbConnection.getConnectionObject();
			conn.setAutoCommit(false);
			for (int count = 0; count < objDtoList.size(); count++) {
				objDto = (MastersAttributesDto) objDtoList.get(count);
				cstmt= conn
						.prepareCall(updateServicesAttributes);

				cstmt.setString(1, objDto.getAttID());
				cstmt.setString(2, objDto.getAttDescription());
				cstmt.setString(3, objDto.getAttDataType());
				cstmt.setString(4, objDto.getAttExpectedValue());
				cstmt.setString(5, objDto.getAttIsmandatory());

				cstmt.setString(6, "");
				cstmt.execute();
				status = cstmt.getString(6);
				if (!status.equalsIgnoreCase("SUCCESS")) {
					transactionDone = false;
					break;
				}
			}

		} catch (Exception ex) {
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg1, logToFile, logToConsole);//added by nagarjuna 
			//ex.printStackTrace();
		} finally {
			try {
				if (transactionDone == true) {
					conn.commit();

				} else {
					conn.rollback();

				}
				DbConnection.closeCallableStatement(cstmt);
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				Utility.LOG(true,true,"error while closing connection   : "+e);//added by Nagarjuna
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		return status;
	}

	public ArrayList<MastersAttributesDto> loadProducts(
			MastersAttributesDto objDto) throws IOESException {
//		Added by Nagarjuna
		String methodName="loadProducts",  msg1="";
		boolean logToFile=true, logToConsole=true;
		//End Nagarjuna

		Connection conn = null;
		CallableStatement cstmt =null;
		ResultSet rs =null;
		ArrayList<MastersAttributesDto> attributes = new ArrayList<MastersAttributesDto>();
		try {
			conn = DbConnection.getConnectionObject();
			cstmt= conn.prepareCall(spGetAllProducts);
			cstmt.setLong(1, Long.parseLong(objDto.getServiceTypeId()));
			rs= cstmt.executeQuery();
			MastersAttributesDto dto = null;
			while (rs.next()) {
				dto = new MastersAttributesDto();
				dto.setServiceDetailId(rs.getString("SERVICEDETAILID"));
				dto.setServiceDetailDescription(rs
						.getString("SERVICEDETDESCRIPTION"));
				dto.setServiceDetailType(rs.getString("SERVICEDETTYPE"));

				attributes.add(dto);
			}

		} catch (Exception ex) {
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg1, logToFile, logToConsole);//added by nagarjuna 
			//ex.printStackTrace();
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
		return attributes;
	}

	public ArrayList<MastersAttributesDto> getProductAttributeList(
			MastersAttributesDto objDto) throws IOESException {
//		Added by Nagarjuna
		String methodName="getProductAttributeList",  msg="";
		boolean logToFile=true, logToConsole=true;
		//End Nagarjuna

		Connection conn = null;
		CallableStatement cstmt=null;
		ResultSet rs=null;
		ArrayList<MastersAttributesDto> productAttributes = new ArrayList<MastersAttributesDto>();
		try {
			conn = DbConnection.getConnectionObject();
			 cstmt= conn
					.prepareCall(spGetAllProductAttributes);
			cstmt.setLong(1, Long.parseLong(objDto.getServiceDetailId()));
			 rs= cstmt.executeQuery();
			MastersAttributesDto dto = null;
			while (rs.next()) {
				dto = new MastersAttributesDto();
				dto.setAttID(rs.getString("ATTMASTERID"));
				dto.setAttDescription(rs.getString("ATTDESCRIPTION"));
				dto.setAttDataType((AppUtility.fnCheckNull(rs
						.getString("ATTDATATYPE")).toUpperCase()));
				dto.setAttAliasName(rs.getString("ALISNAME"));
				dto.setAttExpectedValue((AppUtility.fnCheckNull(rs
						.getString("EXPECTEDVALUE")).toUpperCase()));
				dto.setAttMaxLenghth(rs.getString("MAXLENGTH"));
				dto.setAttIsmandatory((AppUtility.fnCheckNull(rs
						.getString("MANDATORY")).toUpperCase()));
				setApplicationProperties(dto);
				productAttributes.add(dto);
			}

		} catch (Exception ex) {
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//added by nagarjuna 
			//ex.printStackTrace();
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
		return productAttributes;
	}

	public String updateProductAttributes(
			ArrayList<MastersAttributesDto> objDtoList) throws IOESException {
//		Added by Nagarjuna
		String methodName="updateProductAttributes",  msg="";
		boolean logToFile=true, logToConsole=true;
		//End Nagarjuna

		MastersAttributesDto objDto;
		Connection conn = null;
		String status = "";
		CallableStatement cstmt =null;
		boolean transactionDone = true;
		try {
			conn = DbConnection.getConnectionObject();
			conn.setAutoCommit(false);
			for (int count = 0; count < objDtoList.size(); count++) {
				objDto = (MastersAttributesDto) objDtoList.get(count);
				cstmt= conn
						.prepareCall(updateProductAttributes);

				cstmt.setString(1, objDto.getAttID());
				cstmt.setString(2, objDto.getAttDescription());
				cstmt.setString(3, objDto.getAttDataType());
				cstmt.setString(4, objDto.getAttExpectedValue());
				cstmt.setString(5, objDto.getAttIsmandatory());

				cstmt.setString(6, "");
				cstmt.execute();
				status = cstmt.getString(6);
				if (!status.equalsIgnoreCase("SUCCESS")) {
					transactionDone = false;
					break;
				}
			}

		} catch (Exception ex) {
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, "Error Message :"+status, logToFile, logToConsole);//added by nagarjuna 
			//ex.printStackTrace();
		} finally {
			try {
				if (transactionDone == true) {
					conn.commit();

				} else {
					conn.rollback();

				}
				DbConnection.closeCallableStatement(cstmt);
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				Utility.LOG(true,true,"error while closing connection   : "+e);//added by Nagarjuna
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		return status;
	}
	public ArrayList<MastersAttributesDto> getProductConfigAttList(
			MastersAttributesDto objDto) throws IOESException {
//		Added by Nagarjuna
		String methodName="getProductConfigAttList",  msg="";
		boolean logToFile=true, logToConsole=true;
		//End Nagarjuna

		Connection conn = null;
		CallableStatement cstmt=null;
		ResultSet rs =null;
		ArrayList<MastersAttributesDto> productAttributes = new ArrayList<MastersAttributesDto>();
		try {
			conn = DbConnection.getConnectionObject();
			cstmt = conn
					.prepareCall(spGetAllProductConfigAttributes);
			cstmt.setLong(1, Long.parseLong(objDto.getServiceTypeId()));
			rs= cstmt.executeQuery();
			MastersAttributesDto dto = null;
			while (rs.next()) {
				dto = new MastersAttributesDto();
				dto.setAttID(AppUtility.fnCheckNull
						(rs.getString("SERVICEDETAILID")));
				dto.setAttDescription(AppUtility.fnCheckNull(rs.getString("SERVICEDETDESCRIPTION")));
				dto.setOeParentID(AppUtility.fnCheckNull(rs.getString("SERVICEDETPARENTID")));
				dto.setM6ParentID(AppUtility.fnCheckNull(rs.getString("PARENTSPECID")));
				dto.setM6ChildID(AppUtility.fnCheckNull(rs.getString("CHILDSPECID")));
				dto.setSendToFX(AppUtility.fnCheckNull(rs.getString("SENDTOFX")));
				dto.setSendToM6(AppUtility.fnCheckNull(rs.getString("SENDTOM6")));
				dto.setServiceType(AppUtility.fnCheckNull(rs.getString("SERVICETYPE")));
				dto.setServiceSummary(AppUtility.fnCheckNull(rs.getString("SERVICESUMMARY")));
				dto.setBillingInfo(rs.getString("BILLINGINFO"));
				dto.setChargeInfo(rs.getString("CHARGEINFO"));
				dto.setHardwareInfo(rs.getString("HARDWAREINFO"));
				dto.setServiceLocation(rs.getString("SERVICELOCATION"));
				productAttributes.add(dto);
			}

		} catch (Exception ex) {
			
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//added by nagarjuna 
			//ex.printStackTrace();
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
		return productAttributes;
	}
	public String updateProductConfigAttributes(
			ArrayList<MastersAttributesDto> objDtoList) throws IOESException {
//		Added by Nagarjuna
		String methodName="updateProductConfigAttributes",  msg="";
		boolean logToFile=true, logToConsole=true;
		//End Nagarjuna

		MastersAttributesDto objDto;
		Connection conn = null;
		String status = "";
		CallableStatement cstmt=null;
		boolean transactionDone = true;
		try {
			conn = DbConnection.getConnectionObject();
			conn.setAutoCommit(false);
			for (int count = 0; count < objDtoList.size(); count++) {
				objDto = (MastersAttributesDto) objDtoList.get(count);
				cstmt = conn
						.prepareCall(updateProductConfigAttributes);

				cstmt.setString(1,objDto.getAttID());
				cstmt.setString(2, objDto.getOeParentID());
				cstmt.setString(3, objDto.getM6ParentID());
				cstmt.setString(4, objDto.getM6ChildID());
				cstmt.setString(5, objDto.getServiceSummary());
				cstmt.setString(6, objDto.getBillingInfo());
				cstmt.setString(7, objDto.getChargeInfo());
				cstmt.setString(8, objDto.getHardwareInfo());
				cstmt.setString(9, objDto.getServiceLocation());
				cstmt.setString(10, objDto.getSendToFX());
				cstmt.setString(11, objDto.getSendToM6());
				cstmt.setString(12, "1");
				cstmt.setString(13, "");
				
				cstmt.execute();
				status = cstmt.getString(13);
				if (!status.equalsIgnoreCase("SUCCESS")) {
					transactionDone = false;
					break;
				}
			}

		} catch (Exception ex) {
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, "Error Message :"+status, logToFile, logToConsole);//added by nagarjuna 
			//ex.printStackTrace();
		} finally {
			try {
				if (transactionDone == true) {
					conn.commit();

				} else {
					conn.rollback();

				}
				DbConnection.closeCallableStatement(cstmt);
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				Utility.LOG(true,true,"error while closing connection   : "+e);//added by Nagarjuna
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		return status;
	}
}
