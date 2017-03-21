//Tag Name Resource Name  Date		CSR No			Description 
//[001]	 LawKush		10-Feb-11	CSR00-05422     for orders pending in billing and hardware
//[HYPR11042013001]	Kalpana 18-April-2013 display sales phone number
//[002]     Gunjan Singla                     added customer segment description column in CWN usage report
//[111]    Gunjan Singla  22-Sept-14      CBR_20140704-XX-020224 Global Data Billing Efficiencies Phase1
//[003]   Neha Maheshwari    04-Dec-2014   CSR20141014-R2-020535-Service Segment Configuration-SMB
//[005]   Neha Maheshwari    14-Apr-15     CSR-20150212-R1-021088  Addition of Column in Active and Pending Line Item Reports
//[006]   Neha Maheshwari 10-Jun-2015 20141219-R2-020936 Billing Efficiency Program Reports
//[007]  Priya Gupta	 28-Jan-2016	Added 3 new columns
//[008]  Gunjan Singla   6-May-2016   20160418-R1-022266   Added Columns
package com.ibm.ioes.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//import org.apache.commons.net.ntp.TimeStamp;
//import org.apache.taglibs.string.UpperCaseTag;


import com.ibm.ioes.forms.BCPAddressDto;
import com.ibm.ioes.forms.ChangeOrderTypeDto;
import com.ibm.ioes.forms.ComponentsDto;
import com.ibm.ioes.forms.DispatchAddressDto;
import com.ibm.ioes.forms.MastersAttributesDto;
import com.ibm.ioes.forms.NetworkLocationDto;
import com.ibm.ioes.forms.LocationDetailDto;
import com.ibm.ioes.forms.M6OrderStatusDto;
import com.ibm.ioes.forms.NewOrderDto;
import com.ibm.ioes.forms.ReportsDto;
import com.ibm.ioes.newdesign.dto.ActiveLineItemReportsDTO;
import com.ibm.ioes.newdesign.dto.BillingTriggerDoneButFailedInFXDTO;
import com.ibm.ioes.newdesign.dto.BillingWorkQueueReportDTO;
import com.ibm.ioes.newdesign.dto.CancelledFailedLineReportDTO;
import com.ibm.ioes.newdesign.dto.CustomerBaseReportsDTO;
import com.ibm.ioes.newdesign.dto.DisconnectChangeOrdeReportDTO;
import com.ibm.ioes.newdesign.dto.DisconnectLineReportDTO;
import com.ibm.ioes.newdesign.dto.LogicalSIDataReportDTO;
import com.ibm.ioes.newdesign.dto.MigratedApprovedNewOrderDetailReportDTO;
import com.ibm.ioes.newdesign.dto.NonAPP_APPChangeOrderDetailsDTO;
import com.ibm.ioes.newdesign.dto.OBValueReportDTO;
import com.ibm.ioes.newdesign.dto.OrderReportNewDetailCwnDTO;
import com.ibm.ioes.newdesign.dto.PendingOrderAndBillingReportDTO;
import com.ibm.ioes.newdesign.dto.RateRenewalReportDTO;
import com.ibm.ioes.newdesign.dto.RestPendingLineReportDTO;
import com.ibm.ioes.newdesign.dto.StartChargeNotPushedInFXDTO;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.PagingSorting;
import com.ibm.ioes.utilities.Utility;

public class ReportsDao_Usage extends CommonBaseDao {

	public static String sqlGetBCPReport = "{ call IOE.SP_GET_BCP_REPORT( ?,?,?,?,?,?,?,?,? ) }";
	
	public static String sqlGetM6ResponseHistoryReport = "{ call IOE.SP_GET_M6_RESPONSE_HISTORY_REPORT( ?) }";
	
	public static String sqlGetM6OrderStatusReport = "{ call IOE.SP_GET_M6_ORDERSTATUS_REPORT( ?,?,?,?,?,?,?,? ) }";

	public static String sqlGetNetworkLocReport = "{ call IOE.SP_GET_NETWORKLOCATION_REPORT( ?,?,?,?,?,?,? ) }";

	public static String sqlGetCustomerLocReport = "{call IOE.SP_GET_CUSTOMER_DETAILS_REPORT(?,?,?,?,?,?,?,?,?)}";

	public static String sqlContactList = "{call IOE.SP_GET_CONTACT_REPORT(?,?,?,?,?,?,?,?,?,?)}";

	public static String sqlOrderList = "{call IOE.SP_GET_ORDER_STATUS_REPORT(?,?,?,?,?,?,?,?,?,?,?)}";

	public static String sqlDispatchList = "{call IOE.SP_GET_DISPATCH_ADDRESS_REPORT(?,?,?,?,?,?,?,?,?)}";

	public static String sqlSearchOrderStatues = "{call IOE.SP_GET_ORDER_STATUS(?)}";
	
	public static String sqlGetMasterReportStatus="{call IOE.SP_GET_MASTER_REPORT(?,?,?,?,?,?)}";
	
	public static String sqlOrderStage="{call IOE.SP_GET_ORDER_STAGE_REPORT(?,?,?,?,?,?,?,?,?)}";

	public static String sqlGetPerformanceReport="{call IOE.SP_GET_PERFORMANCE_REPORT(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	
	public static String sqlGetPerformanceSummaryReport="{call IOE.SP_GET_PERFORMANCE_SUMMARY_REPORT(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	public static String sqlGetPendingServicesReport="{call IOE.SP_GET_PENDING_SERVICES_REPORT(?,?,?,?,?,?,?,?,?,?,?,?)}";
	public static String sqlGetNonMigAppUnappNewOrderDetails="{call IOE.SP_GET_NON_MIG_APP_UNAPP_NEW_ORDER_DETAILS_USAGE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	public static String sqlGetStartChargeNotPushedInFx="{call IOE.SP_GET_START_CHARGES_NOT_PUSHED_FX_USAGE(?,?,?,?,?,?,?,?,?,?,?,?)}";
	public static String sqlGetLogicalSIDataReport="{call IOE.SP_GET_LOGICAL_SI_DATA_REPORT_USAGE(?,?,?,?,?,?,?,?,?,?,?,?)}";
	public static String sqlGetBillingTriggerDoneButFailedInFX="{call IOE.SP_GET_BILLING_TRIGGER_DONE_BUT_FAILED_IN_FX_USAGE(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	public static String sqlActiveLineItems="{call IOE.SP_GET_ACTIVE_LINE_ITEMS_REPORT(?,?,?,?,?,?,?,?,?,?,?)}";
	public static String sqlActiveLineItemsForUsage="{call IOE.SP_GET_ACTIVE_LINE_ITEMS_REPORT_USAGE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	
	public static String sqlOrderReportNew="{call IOE.SP_GET_ORDER_REPORT_NEW_USAGE(?,?,?,?,?,?,?,?,?,?,?)}";
	
	
	public static String sqlOrderReportChange="{call IOE.SP_GET_ORDER_REPORT_CHANGE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	public static String sqlOrderReportDetail="{call IOE.SP_GET_ORDER_DETAIL_REPORT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	public static String sqlPendingOrderAndBillingReport="{call IOE.SP_GET_PENDING_ORDER_BILLING_REPORT_USAGE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	public static String sqlPendingOrderBillHardware="{call IOE.SP_GET_PENDING_ORDER_BILLING_HARDWARE_REPORT(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	public static String sqlM6OrderCancelReport="{call IOE.SPGETM6ORDERCANCELREPORT(?,?,?,?,?,?)}";
	public static String sqlGetPerformanceDetailReport="{call IOE.SP_GET_PERFORMANCE_DETAIL_REPORT(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	public static String sqlGetTelemediaOrderReport="{call IOE.SP_GET_TELEMEDIAORDER_REPORT(?,?,?,?,?,?,?)}";
	public static String sqlZeroOrdervalueReport="{call IOE.SP_GET_ZERO_ORDER_VALUE_REPORT(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	public static String sqlRateRenewalReportforUsage="{call IOE.SP_GET_RATE_RENEWAL_REPORT_USAGE(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	//public static String sqlRateRenewalReport="{call IOE.SP_GET_RATE_RENEWAL_REPORT(?,?,?,?,?,?,?,?,?,?,?)}";
	
	public static String sqlGetRestPendingLineReports="{call IOE.SP_GET_REST_PENDING_LINE_REPORT_USAGE(?,?,?,?,?,?,?,?,?,?,?)}";

	public static String sqlGetDisconnectionLineReport="{call IOE.SP_GET_DISCONNECT_LINE_ITEM_REPORT(?,?,?,?,?,?,?,?,?,?,?)}";
	
	
	//public static String sqlGetCancelledFailedLineReports="{call IOE.SP_GET_CANCELLED_FAILED_LINE_REPORT(?,?,?,?,?,?,?,?)}";
	
	public static String sqlGetBulkUploadReports="{call IOE.SP_GET_BULK_SI_UPLOAD_REPORT(?,?,?,?,?,?,?,?,?,?,?)}";
	
	public static String sqlGetAttributeDetailsReport="{call IOE.SP_GET_ATTRIBUTE_DETAILS_REPORT(?,?,?,?,?,?)}";
	
	public static String sqlGetDisconnectionLineReportForUsage="{call IOE.SP_GET_DISCONNECT_LINE_ITEM_REPORT_USAGE(?,?,?,?,?,?,?,?,?,?,?,?)}";
	public static String sqlGetCancelledFailedLineReportsForUsage="{call IOE.SP_GET_CANCELLED_FAILED_LINE_REPORT_USAGE(?,?,?,?,?,?,?,?,?)}";
	public static String sqlGetMigAppNewOrderDetails="{call IOE.SP_GET_MIG_APP_NEW_ORDER_DETAILS_USAGE(?,?,?,?,?,?,?,?,?)}";
	
	//Meenakshi : Adding String for LEPM Owner report.
	public static String sqlGetLEPMOwnerReport="{call IOE.SP_GET_LEPM_OWNER_REPORT(?,?,?,?,?,?,?)}";
	public static String sqlLEPMOrderCancelReport="{call IOE.SP_GET_LEPM_ORDER_CANCEL_REPORT(?,?,?,?,?,?)}";
	public static String sqlLEPMOrderDetailReport="{call IOE.SP_GET_LEPM_ORDER_DETAIL_REPORT(?,?,?,?,?,?,?,?,?,?)}";
	public static String SPGETSRORDERREPORT="{call IOE.SP_GET_SR_ORDER_STATUS_REPORT(?,?,?,?,?,?,?,?,?,?)}";
	public static String sqlGetCustomerbaseReport="{call IOE.SP_GET_CUSTOMER_BASE_REPORT(?,?,?,?,?,?,?,?)}";
	public static String sqlOrderReportNewForUsage="{call IOE.SP_GET_ORDER_REPORT_NEW_USAGE(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	public static String sqlBillingWorkQueue="{call IOE.SP_GET_BILLING_WORKQUE_REPORT_USAGE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	public static String sqlOBValueReport_Usage="{call IOE.SP_GET_OBVALUE_REPORT_USAGE(?,?,?,?,?,?,?,?,?)}";

	/*Globle Varible for Assigning Primary ,Secondary Location and billing Location
		* Index   Name
			''2''  AS BILL_ADDRESS1,   
			''3''  AS BILL_ADDRESS2,
			''4''  AS BILL_ADDRESS3,
			''5''  AS BILL_ADDRESS4,
			''0-1'' AS BILL_CON_PER_NAME,
			''6''  AS BCP_CITY_NAME,
			''7''  AS BCP_STATE_NAME,
			''8''  AS BCP_COUNTRY_NAME,
			''9''  AS BCP_PIN,
			''11'' AS FAX,
			''12'' AS EMAIL_ID,
			''13'' AS DESIGNATION,
	 */
	String VAR_BILL_ADDRESS1="";   
	String VAR_BILL_ADDRESS2="";
	String VAR_BILL_ADDRESS3="";
	String VAR_BILL_ADDRESS4="";
	String VAR_BILL_CON_PER_NAME="";	
	String VAR_BCP_CITY_NAME="";
	String VAR_BCP_STATE_NAME="";
	String VAR_BCP_COUNTRY_NAME="";
	String VAR_BCP_PIN="";
	String VAR_FAX="";
	String VAR_EMAIL_ID="";
	String VAR_TELEPHONENO="";
	String VAR_DESIGNATION="";
	String VAR_BILLING_ADDRESS="";
	String VAR_PRIMARYLOCATION="";
	String VAR_SECONDARYLOCATION="";
	String VAR_BILL_FNAME="";
	String VAR_BILL_PHONE="";
	private void replaceSeperator(String ColumnName,String ColumnValue)
	{		
		if(!"".equals(ColumnValue) && ColumnValue!=null )
		{
			String[] array=ColumnValue.split("~~");
			if(ColumnName.equals("PRIMARYLOCATION"))
			{
				VAR_PRIMARYLOCATION=ColumnValue.replaceAll("~", "");
			}
			else if(ColumnName.equals("SECONDARYLOCATION"))
			{
				VAR_SECONDARYLOCATION=ColumnValue.replaceAll("~", "");
			}
			else
			{
				VAR_BILLING_ADDRESS=ColumnValue.replaceAll("~", "");
				VAR_BILL_FNAME=array[0];
				VAR_BILL_ADDRESS1=array[2];   
				VAR_BILL_ADDRESS2=array[3];
				VAR_BILL_ADDRESS3=array[4];
				VAR_BILL_ADDRESS4=array[5];
				VAR_BILL_CON_PER_NAME=array[0]+" "+array[1];
				VAR_BCP_CITY_NAME=array[6];
				VAR_BCP_STATE_NAME=array[7];
				VAR_BCP_COUNTRY_NAME=array[8];
				VAR_BCP_PIN=array[9];
				VAR_BILL_PHONE=array[10];
				VAR_FAX=array[11];
				VAR_EMAIL_ID=array[12];
				VAR_DESIGNATION=array[13];
				VAR_TELEPHONENO=array[10];
				
			}
		}
	}
	private void setBlank()
	{
		VAR_BILL_ADDRESS1="";   
		VAR_BILL_ADDRESS2="";
		VAR_BILL_ADDRESS3="";
		VAR_BILL_ADDRESS4="";
		VAR_BILL_CON_PER_NAME="";
		VAR_BCP_CITY_NAME="";
		VAR_BCP_STATE_NAME="";
		VAR_BCP_COUNTRY_NAME="";
		VAR_BCP_PIN="";
		VAR_FAX="";
		VAR_EMAIL_ID="";
		VAR_DESIGNATION="";
		VAR_BILLING_ADDRESS="";
		VAR_PRIMARYLOCATION="";
		VAR_SECONDARYLOCATION="";
		VAR_TELEPHONENO="";
	}
	public static String fnGetUsageReportBIllPeriod(String configId,String entityId)
	{
			if(configId == null){
				configId="";
			}
			if(entityId == null){
				entityId="";
			}
			
			if((configId.equals("2") || configId.equals("102")) && entityId.equals("2")){
				return "AB3";
			}
			else if((configId.equals("2") || configId.equals("102")) && entityId.equals("3")){
				return "Pr3";
			}
			else if(configId.equals("1")){
				return "95P";
			}
			else if(configId.equals("3")){
				return "IN3";
			}
			else if(configId.equals("4")){
				return "TFS";
			}
			else if(configId.equals("5")){
				return "MBC";
			}
			else if(configId.equals("6")){
				return "VC3";
			}
			else if(configId.equals("12")){
				return "AMA";
			}
			else if(configId.equals("16")){
				return "DMX";
			}
			else if(configId.equals("101")){
				return "95P";
			}
			else if(configId.equals("103")){
				return "IN3";
			}
			else if(configId.equals("104")){
				return "TFS";
			}
			else if(configId.equals("105")){
				return "MBC";
			}
			else if(configId.equals("106")){
				return "VC3";
			}
		return null;
	}
	public ArrayList<BCPAddressDto> viewBCPList(BCPAddressDto objDto)
			throws Exception {
		//Nagarjuna
		String methodName="viewBCPList", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;
		//end Nagarjuna
		ArrayList<BCPAddressDto> objUserList = new ArrayList<BCPAddressDto>();
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement getBCP = null;

		try {
			String userName = "";
			conn = DbConnection.getReportsConnectionObject();
			getBCP = conn.prepareCall(sqlGetBCPReport);
			String accountIdStr = objDto.getSearchAccountIdStr();
			String bcpIdStr = objDto.getSearchBcpIdStr();
			String bcpNameStr = objDto.getSearchBcpNameStr();
			String accountNameStr = objDto.getSearchAccountNameStr();

			if (accountIdStr == null || accountIdStr.trim().equals("")) {
				getBCP.setNull(1, java.sql.Types.BIGINT);
			} else {
				getBCP.setLong(1, Long.parseLong(accountIdStr));
			}

			if (bcpIdStr == null || bcpIdStr.trim().equals("")) {
				getBCP.setNull(2, java.sql.Types.BIGINT);
			} else {
				getBCP.setLong(2, Long.parseLong(bcpIdStr));
			}

			if (bcpNameStr == null || bcpNameStr.trim().equals("")) {
				getBCP.setNull(3, java.sql.Types.VARCHAR);
			} else {
				getBCP.setString(3, bcpNameStr);
			}

			if (accountNameStr == null || accountNameStr.trim().equals("")) {
				getBCP.setNull(4, java.sql.Types.VARCHAR);
			} else {
				getBCP.setString(4, accountNameStr);
			}

			PagingSorting pagingSorting = objDto.getPagingSorting();
			pagingSorting.sync();// To calculate start index and Enc Index

			getBCP.setString(5, pagingSorting.getSortByColumn());// columnName
			getBCP.setString(6, PagingSorting.DB_Asc_Desc(pagingSorting
					.getSortByOrder()));// sort order
			getBCP.setInt(7, pagingSorting.getStartRecordId());// start index
			getBCP.setInt(8, pagingSorting.getEndRecordId());// end index
			getBCP.setInt(9, (pagingSorting.isPagingToBeDone() ? 1 : 0));// end
			// index

			rs = getBCP.executeQuery();

			int countFlag = 0;
			int recordCount = 0;
			while (rs.next() != false) {
				countFlag++;

				userName = (rs.getString("FNAME")) + " "
						+ (rs.getString("LNAME"));
				objDto = new BCPAddressDto();
				objDto.setCustomerName(userName);
				objDto.setAccountID("" + rs.getInt("ACCOUNTID"));
				objDto.setAccountName(rs.getString("ACCOUNTNAME"));
				objDto.setBCPId("" + rs.getInt("BCP_ID"));
				objDto.setFirstname(rs.getString("FNAME"));
				objDto.setLastName(rs.getString("LNAME"));
				objDto.setTelephonePhno(rs.getString("TELEPHONENO"));
				objDto.setEmail_Id(rs.getString("EMAIL_ID"));
				objDto.setAddress1(rs.getString("ADDRESS1"));
				objDto.setAddress2(rs.getString("ADDRESS2"));
				objDto.setAddress3(rs.getString("ADDRESS3"));
				objDto.setAddress4(rs.getString("ADDRESS4"));
				objDto.setFax(rs.getString("FAX"));
				// objDto.setPin(rs.getString("PIN"));
				objDto.setTitle(rs.getString("TITLE"));
				objDto.setPostalCode(rs.getString("POSTAL_CODE"));
				objDto.setCityName(rs.getString("CITY_NAME"));
				objDto.setStateName(rs.getString("STATE_NAME"));
				objDto.setCountryName(rs.getString("COUNTRY_NAME"));

				if (pagingSorting.isPagingToBeDone()) {
					recordCount = rs.getInt("FULL_REC_COUNT");
				}

				objUserList.add(objDto);
			}
			pagingSorting.setRecordCount(recordCount);
		} catch (Exception ex) {
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);
			//ex.printStackTrace();
			//throw new Exception("SQL Exception : " + ex.getMessage(), ex);
		} finally {
			try {
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(getBCP);
				DbConnection.freeConnection(conn);

			} catch (Exception e) {
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);
				//e.printStackTrace();
				//throw new Exception("Exception : " + e.getMessage(), e);
			}
		}
		return objUserList;
	}

	public ArrayList<M6OrderStatusDto> viewM6OrderList(M6OrderStatusDto objDto)
				throws Exception {
		//Nagarjuna
		String methodName="viewM6OrderList", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;
		//end Nagarjuna
			ArrayList<M6OrderStatusDto> objUserList = new ArrayList<M6OrderStatusDto>();
			Connection conn = null;
			ResultSet rs = null;
			CallableStatement getOrder = null;
			
			try {
				conn = DbConnection.getReportsConnectionObject();
				getOrder = conn.prepareCall(sqlGetM6OrderStatusReport);
				String accountIdStr = objDto.getSearchAccountIdStr();
				String orderNoStr = objDto.getSearchOrderNoStr();
			//	String m6OrderNoStr = objDto.getSearchM6OrderNoStr();
				String accountNameStr = objDto.getSearchAccountNameStr();
			
				if (accountIdStr == null || accountIdStr.trim().equals("")) {
					getOrder.setNull(1, java.sql.Types.BIGINT);
				} else {
					getOrder.setLong(1, Long.parseLong(accountIdStr));
				}
			
				if (orderNoStr == null || orderNoStr.trim().equals("")) {
					getOrder.setNull(2, java.sql.Types.BIGINT);
				} else {
					getOrder.setLong(2, Long.parseLong(orderNoStr));
				}
			
				if (accountNameStr == null || accountNameStr.trim().equals("")) {
					getOrder.setNull(3, java.sql.Types.VARCHAR);
				} else {
					getOrder.setString(3, accountNameStr);
				}
			
				PagingSorting pagingSorting = objDto.getPagingSorting();
				pagingSorting.sync();// To calculate start index and Enc Index
			
				getOrder.setString(4, pagingSorting.getSortByColumn());// columnName
				getOrder.setString(5, PagingSorting.DB_Asc_Desc(pagingSorting
						.getSortByOrder()));// sort order
				getOrder.setInt(6, pagingSorting.getStartRecordId());// start index
				getOrder.setInt(7, pagingSorting.getEndRecordId());// end index
				getOrder.setInt(8, (pagingSorting.isPagingToBeDone() ? 1 : 0));// end
				// index
			
				rs = getOrder.executeQuery();
			
				int countFlag = 0;
				int recordCount = 0;
				while (rs.next() != false) {
					countFlag++;
			
					objDto = new M6OrderStatusDto();
					objDto.setAccountID("" + rs.getInt("ACCOUNTID"));
					objDto.setAccountName(rs.getString("ACCOUNTNAME"));
					objDto.setOrderNo("" + rs.getInt("OrderNO"));
					objDto.setM6OrderNo("" + rs.getInt("M6OrderNO"));
					
			
					if (pagingSorting.isPagingToBeDone()) {
						recordCount = rs.getInt("FULL_REC_COUNT");
					}
			
					objUserList.add(objDto);
				}
				pagingSorting.setRecordCount(recordCount);
			} catch (Exception ex) {
				Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);
				//ex.printStackTrace();
				throw new Exception("SQL Exception : " + ex.getMessage(), ex);
			} finally {
				try {
					DbConnection.closeResultset(rs);
					DbConnection.closeCallableStatement(getOrder);
					DbConnection.freeConnection(conn);
			
				} catch (Exception e) {
					Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);
					//e.printStackTrace();
					throw new Exception("Exception : " + e.getMessage(), e);
				}
			}
			return objUserList;
}
	//	Method used for Fetching Account and Main Details from Database
	public ArrayList<M6OrderStatusDto> viewM6ResponseHistory(M6OrderStatusDto objDto,long m6OrderNo) 
	{
		//Nagarjuna
		String methodName="viewM6ResponseHistory", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;
		//end Nagarjuna
		Connection connection =null;
		CallableStatement getM6ResponseHistory =null;
		ResultSet rsM6ResponseHistory = null;
		ArrayList<M6OrderStatusDto> listM6ResponseHistory = new ArrayList<M6OrderStatusDto>();
		M6OrderStatusDto objNewOrderDto = null;
		Utility utility=new Utility();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		
		try
		{
			connection=DbConnection.getReportsConnectionObject();
			getM6ResponseHistory= connection.prepareCall(sqlGetM6ResponseHistoryReport);
			
			if (m6OrderNo != 0) {
				getM6ResponseHistory.setLong(1, m6OrderNo);
			} else {
				getM6ResponseHistory.setNull(1, java.sql.Types.BIGINT);
			}
		//	getM6ResponseHistory.setLong(1, Intm6OrderNo);
			rsM6ResponseHistory = getM6ResponseHistory.executeQuery();
			while(rsM6ResponseHistory.next())
			{
				objNewOrderDto =  new M6OrderStatusDto();
				objNewOrderDto.setOrderNo(rsM6ResponseHistory.getString("orderNo"));
			    objNewOrderDto.setM6OrderNo(String.valueOf(m6OrderNo));
				objNewOrderDto.setRemarks(rsM6ResponseHistory.getString("Reason"));
				objNewOrderDto.setCreatedDate((rsM6ResponseHistory.getString("Created_Date")));
				if(!(rsM6ResponseHistory.getString("Created_Date")==null || rsM6ResponseHistory.getString("Created_Date")==""))
				{
					objNewOrderDto.setCreatedDate((utility.showDate_Report(new Date(rsM6ResponseHistory.getTimestamp("Created_Date").getTime()))).toUpperCase());
				}
		
			
				listM6ResponseHistory.add(objNewOrderDto);
			}
		}
		catch(Exception ex )
		{
			//ex.printStackTrace();	
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);
		}
		finally
		{
			try 
			{
				DbConnection.closeResultset(rsM6ResponseHistory);
				DbConnection.closeCallableStatement(getM6ResponseHistory);
				DbConnection.freeConnection(connection);
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				//e.printStackTrace();
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);
			}
		}
		return listM6ResponseHistory;
	}	
	
	
	public ArrayList<NetworkLocationDto> viewNetworkLocsList(
			NetworkLocationDto objDto) throws Exception {
		//Nagarjuna
		String methodName="viewNetworkLocsList", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;
		//end Nagarjuna
		ArrayList<NetworkLocationDto> objUserList = new ArrayList<NetworkLocationDto>();
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement getNetworkLocs = null;

		try {
			String userName = "";
			conn = DbConnection.getReportsConnectionObject();
			getNetworkLocs = conn.prepareCall(sqlGetNetworkLocReport);
			String networkLocationIdStr = objDto
					.getSearchNetworkLocationIdStr();
			String contactNameStr = objDto.getSearchContactNameStr();

			if (networkLocationIdStr == null
					|| networkLocationIdStr.trim().equals("")) {
				getNetworkLocs.setNull(1, java.sql.Types.BIGINT);
			} else {
				getNetworkLocs.setLong(1, Long.parseLong(networkLocationIdStr));
			}

			if (contactNameStr == null || contactNameStr.trim().equals("")) {
				getNetworkLocs.setNull(2, java.sql.Types.VARCHAR);
			} else {
				getNetworkLocs.setString(2, contactNameStr);
			}

			PagingSorting pagingSorting = objDto.getPagingSorting();
			pagingSorting.sync();// To calculate start index and Enc Index

			getNetworkLocs.setString(3, pagingSorting.getSortByColumn());// columnName
			getNetworkLocs.setString(4, PagingSorting.DB_Asc_Desc(pagingSorting
					.getSortByOrder()));// sort order
			getNetworkLocs.setInt(5, pagingSorting.getStartRecordId());// start
			// index
			getNetworkLocs.setInt(6, pagingSorting.getEndRecordId());// end
			// index
			getNetworkLocs
					.setInt(7, (pagingSorting.isPagingToBeDone() ? 1 : 0));// end
			// index

			rs = getNetworkLocs.executeQuery();

			int countFlag = 0;
			int recordCount = 0;
			while (rs.next() != false) {
				countFlag++;

				userName = (rs.getString("FNAME")) + " "
						+ (rs.getString("LNAME"));
				objDto = new NetworkLocationDto();
				objDto.setCustomerName(userName);
				objDto.setNetworkLocationId("" + rs.getInt("LOCATION_CODE"));
				objDto.setFirstname(rs.getString("FNAME"));
				objDto.setLastName(rs.getString("LNAME"));
				objDto.setTelephonePhno(rs.getString("TELEPHONENO"));
				objDto.setEmail_Id(rs.getString("EMAIL_ID"));
				objDto.setAddress1(rs.getString("ADDRESS1"));
				objDto.setAddress2(rs.getString("ADDRESS2"));
				objDto.setAddress3(rs.getString("ADDRESS3"));
				objDto.setAddress4(rs.getString("ADDRESS4"));
				objDto.setFax(rs.getString("FAX"));
				// objDto.setPin(rs.getString("PIN"));
				objDto.setTitle(rs.getString("TITLE"));
				objDto.setPostalCode(rs.getString("POSTAL_CODE"));
				objDto.setCityName(rs.getString("CITY_NAME"));
				objDto.setStateName(rs.getString("STATE_NAME"));
				objDto.setCountryName(rs.getString("COUNTRY_NAME"));

				if (pagingSorting.isPagingToBeDone()) {
					recordCount = rs.getInt("FULL_REC_COUNT");
				}

				objUserList.add(objDto);
			}
			pagingSorting.setRecordCount(recordCount);
		} catch (Exception ex) {
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);
			//ex.printStackTrace();
			throw new Exception("SQL Exception : " + ex.getMessage(), ex);
		} finally {
			try {
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(getNetworkLocs);
				DbConnection.freeConnection(conn);

			} catch (Exception e) {
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);
				//e.printStackTrace();
				throw new Exception("Exception : " + e.getMessage(), e);
			}
		}
		return objUserList;
	}

	public ArrayList<LocationDetailDto> viewCustomerLocationList(
			LocationDetailDto objDto) throws Exception {
		//Nagarjuna
		String methodName="viewCustomerLocationList", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;
		//end Nagarjuna
		ArrayList<LocationDetailDto> objUserList = new ArrayList<LocationDetailDto>();
		Connection conn = null;
		CallableStatement proc = null;
		ResultSet rs = null;
		int recordCount = 0;

		try {
			String userName = "";

			conn = DbConnection.getReportsConnectionObject();

			proc = conn.prepareCall(sqlGetCustomerLocReport);
			if (objDto.getLocationId() != null
					&& !"".equals(objDto.getLocationId())) {
				proc.setLong(2, Long.parseLong(objDto.getLocationId()));
			} else {
				proc.setNull(2, java.sql.Types.BIGINT);
			}
			if (objDto.getAccountID() != null
					&& !"".equals(objDto.getAccountID())) {
				proc.setLong(1, Long.parseLong(objDto.getAccountID()));
			} else {
				proc.setNull(1, java.sql.Types.BIGINT);
			}
			if (objDto.getLocationName() != null
					&& !"".equals(objDto.getLocationName())) {
				proc.setString(3, objDto.getLocationName().trim());
			} else {
				proc.setNull(3, java.sql.Types.VARCHAR);
			}
			if (objDto.getAccountName() != null
					&& !"".equals(objDto.getAccountName())) {
				proc.setString(4, objDto.getAccountName().trim());
			} else {
				proc.setNull(4, java.sql.Types.VARCHAR);
			}
			PagingSorting pagingSorting = objDto.getPagingSorting();
			pagingSorting.sync();// To calculate start index and Enc Index

			proc.setString(5, pagingSorting.getSortByColumn());// columnName
			proc.setString(6, PagingSorting.DB_Asc_Desc(pagingSorting
					.getSortByOrder()));// sort order
			proc.setInt(7, pagingSorting.getStartRecordId());// start index
			proc.setInt(8, pagingSorting.getEndRecordId());// end index
			proc.setInt(9, (pagingSorting.isPagingToBeDone() ? 1 : 0));// end
			// index

			rs = proc.executeQuery();
			int countFlag = 0;
			while (rs.next() != false) {
				countFlag++;

				userName = (rs.getString("FNAME")) + " "
						+ (rs.getString("LNAME"));
				objDto = new LocationDetailDto();
				objDto.setCustomerName(userName);
				objDto.setAccountID(String.valueOf(rs.getInt("ACCOUNTID")));
				objDto
						.setLocationId(String.valueOf(rs
								.getInt("LOCATION_CODE")));
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
				objDto.setAccountName(rs.getString("ACCOUNTNAME"));

				if (pagingSorting.isPagingToBeDone()) {
					recordCount = rs.getInt("FULL_REC_COUNT");
				}
				objUserList.add(objDto);
			}
			pagingSorting.setRecordCount(recordCount);
		} catch (Exception ex) {
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);
			//ex.printStackTrace();
			throw new Exception("SQL Exception : " + ex.getMessage(), ex);
		} finally {
			try {
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(proc);
				DbConnection.freeConnection(conn);

			} catch (Exception e) {
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);
				//e.printStackTrace();
				throw new Exception("Exception : " + e.getMessage(), e);
			}
		}

		return objUserList;
	}

	public ArrayList<NewOrderDto> viewContactList(NewOrderDto objDto)
			throws Exception {
		//Nagarjuna
		String methodName="viewContactList", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;
		//end Nagarjuna
		ArrayList<NewOrderDto> objUserList = new ArrayList<NewOrderDto>();
		Connection conn = null;
		CallableStatement proc = null;
		ResultSet rs = null;
		int recordCount = 0;

		try {
			String userName = "";

			conn = DbConnection.getReportsConnectionObject();

			proc = conn.prepareCall(sqlContactList);
			if (objDto.getAccountID() != 0) {
				proc.setLong(1, (objDto.getAccountID()));
			} else {
				proc.setNull(1, java.sql.Types.BIGINT);
			}
			if (objDto.getContactType() != null
					&& !"".equals(objDto.getContactType())) {
				proc.setString(2, (objDto.getContactType()));
			} else {
				proc.setNull(2, java.sql.Types.VARCHAR);
			}

			if (objDto.getContactName() != null
					&& !"".equals(objDto.getContactName())) {
				proc.setString(3, objDto.getContactName().trim());
			} else {
				proc.setNull(3, java.sql.Types.VARCHAR);
			}
			if (objDto.getAccountName() != null
					&& !"".equals(objDto.getAccountName())) {
				proc.setString(4, objDto.getAccountName().trim());
			} else {
				proc.setNull(4, java.sql.Types.VARCHAR);
			}
			if (objDto.getOrderNumber() != 0) {
				proc.setLong(5, (objDto.getOrderNumber()));
			} else {
				proc.setNull(5, java.sql.Types.BIGINT);
			}
			PagingSorting pagingSorting = objDto.getPagingSorting();
			pagingSorting.sync();// To calculate start index and Enc Index

			proc.setString(6, pagingSorting.getSortByColumn());// columnName
			proc.setString(7, PagingSorting.DB_Asc_Desc(pagingSorting
					.getSortByOrder()));// sort order
			proc.setInt(8, pagingSorting.getStartRecordId());// start index
			proc.setInt(9, pagingSorting.getEndRecordId());// end index
			proc.setInt(10, (pagingSorting.isPagingToBeDone() ? 1 : 0));// end
			// index

			rs = proc.executeQuery();
			int countFlag = 0;
			while (rs.next() != false) {
				countFlag++;

				userName = (rs.getString("FIRSTNAME")) + " "
						+ (rs.getString("LASTNAME"));
				objDto = new NewOrderDto();
				objDto.setContactName(userName);
				objDto.setAccountID(rs.getInt("ACCOUNTID"));
				objDto.setOrderNumber(rs.getInt("ORDERNO"));
				objDto.setContactId(rs.getLong("CONTACTID"));
				objDto.setContactType(rs.getString("CONTACTTYPE"));
				objDto.setSaluation(rs.getString("SALUATION"));
				objDto.setCntEmail(rs.getString("EMAIL"));
				objDto.setContactCell(rs.getString("CELLNO"));
				objDto.setContactFax(rs.getString("FAXNO"));
				objDto.setAddress1(rs.getString("ADDRESS1"));
				objDto.setAddress2(rs.getString("ADDRESS2"));
				objDto.setAddress3(rs.getString("ADDRESS3"));
				objDto.setCityName(rs.getString("CITY"));
				objDto.setStateName(rs.getString("STATE"));
				objDto.setCountyName(rs.getString("COUNTRY"));
				objDto.setPinNo(rs.getString("PINCODE"));
				objDto.setAccountName(rs.getString("ACCOUNTNAME"));

				if (pagingSorting.isPagingToBeDone()) {
					recordCount = rs.getInt("FULL_REC_COUNT");
				}
				objUserList.add(objDto);
			}
			pagingSorting.setRecordCount(recordCount);
		} catch (Exception ex) {
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);
			//ex.printStackTrace();
			throw new Exception("SQL Exception : " + ex.getMessage(), ex);
		} finally {
			try {
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(proc);
				DbConnection.freeConnection(conn);

			} catch (Exception e) {
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);
				//e.printStackTrace();
				throw new Exception("Exception : " + e.getMessage(), e);
			}
		}

		return objUserList;
	}

	public ArrayList<DispatchAddressDto> viewDispatchAddressList(
			DispatchAddressDto objDto) throws Exception {
		//Nagarjuna
		String methodName="viewDispatchAddressList", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;
		//end Nagarjuna
		ArrayList<DispatchAddressDto> objUserList = new ArrayList<DispatchAddressDto>();
		Connection conn = null;
		CallableStatement proc = null;
		ResultSet rs = null;

		try {
			conn = DbConnection.getReportsConnectionObject();
			proc = conn.prepareCall(sqlDispatchList);

			if (objDto.getDispatchAddressId() != null
					&& !"".equals(objDto.getDispatchAddressId())) {
				proc.setLong(2, Long.parseLong(objDto.getDispatchAddressId()));
			} else {
				proc.setNull(2, java.sql.Types.BIGINT);
			}
			if (objDto.getAccountID() != null
					&& !"".equals(objDto.getAccountID())) {
				proc.setLong(1, Long.parseLong(objDto.getAccountID()));
			} else {
				proc.setNull(1, java.sql.Types.BIGINT);

			}

			if (objDto.getDispatchAddressName() != null
					&& !"".equals(objDto.getDispatchAddressName())) {
				proc.setString(3, objDto.getDispatchAddressName().trim());
			} else {
				proc.setNull(3, java.sql.Types.VARCHAR);
			}

			if (objDto.getAccountName() != null
					|| !objDto.getAccountName().trim().equals("")) {
				proc.setString(4, objDto.getAccountName());
			} else {
				proc.setNull(4, java.sql.Types.VARCHAR);
			}

			PagingSorting pagingSorting = objDto.getPagingSorting();
			pagingSorting.sync();// To calculate start index and Enc Index

			proc.setString(5, pagingSorting.getSortByColumn());// columnName
			proc.setString(6, PagingSorting.DB_Asc_Desc(pagingSorting
					.getSortByOrder()));// sort order
			proc.setInt(7, pagingSorting.getStartRecordId());// start index
			proc.setInt(8, pagingSorting.getEndRecordId());// end index
			proc.setInt(9, (pagingSorting.isPagingToBeDone() ? 1 : 0));// end
			// index

			rs = proc.executeQuery();
			int countFlag = 0;
			int recordCount = 0;

			while (rs.next() != false) {
				countFlag++;

				String userName = (rs.getString("FNAME")) + " "
						+ (rs.getString("LNAME"));
				objDto = new DispatchAddressDto();
				objDto.setCustomerName(userName);
				objDto.setAccountID(String.valueOf(rs.getInt("ACCOUNTID")));
				objDto.setAccountName(rs.getString("ACCOUNTNAME"));
				objDto.setDispatchAddressId(String.valueOf(rs
						.getInt("DISPATCH_ADDRESS_CODE")));
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

				if (pagingSorting.isPagingToBeDone()) {
					recordCount = rs.getInt("FULL_REC_COUNT");
				}
				objUserList.add(objDto);
			}
			pagingSorting.setRecordCount(recordCount);
		} catch (Exception ex) {
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);
			//ex.printStackTrace();
			throw new Exception("SQL Exception : " + ex.getMessage(), ex);
		} finally {
			try {
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(proc);
				DbConnection.freeConnection(conn);

			} catch (Exception e) {
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);
				//e.printStackTrace();
				throw new Exception("Exception : " + e.getMessage(), e);
			}
		}

		return objUserList;
	}

	public ArrayList<NewOrderDto> viewOrderStatusList(NewOrderDto objDto)
			throws Exception {
		//Nagarjuna
		String methodName="viewOrderStatusList", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;
		//end Nagarjuna
		ArrayList<NewOrderDto> objUserList = new ArrayList<NewOrderDto>();
		Connection conn = null;
		CallableStatement proc = null;
		ResultSet rs = null;
		int recordCount = 0;
		Utility utility=new Utility();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try {

			conn = DbConnection.getReportsConnectionObject();

			proc = conn.prepareCall(sqlOrderList);
			if (objDto.getAccountID() != 0) {
				proc.setLong(1, (objDto.getAccountID()));
			} else {
				proc.setNull(1, java.sql.Types.BIGINT);
			}
			if (objDto.getAccountName() != null
					&& !"".equals(objDto.getAccountName())) {
				proc.setString(2, objDto.getAccountName().trim());
			} else {
				proc.setNull(2, java.sql.Types.VARCHAR);
			}
			if (objDto.getOrderType() != null
					&& !"".equals(objDto.getOrderType())) {
				proc.setString(3, objDto.getOrderType().trim());
			} else {
				proc.setNull(3, java.sql.Types.VARCHAR);
			}

			if (objDto.getOrderNumber() != 0) {
				proc.setLong(4, objDto.getOrderNumber());
			} else {
				proc.setNull(4, java.sql.Types.BIGINT);
			}
			if (objDto.getFromDate() != null
					&& !"".equals(objDto.getFromDate())) {
				proc.setString(5, objDto.getFromDate().trim());
			} else {
				proc.setNull(5, java.sql.Types.VARCHAR);
			}
			if (objDto.getToDate() != null && !"".equals(objDto.getToDate())) {
				proc.setString(6, objDto.getToDate().trim());
			} else {
				proc.setNull(6, java.sql.Types.VARCHAR);
			}

			PagingSorting pagingSorting = objDto.getPagingSorting();
			pagingSorting.sync();// To calculate start index and Enc Index

			proc.setString(7, pagingSorting.getSortByColumn());// columnName
			proc.setString(8, PagingSorting.DB_Asc_Desc(pagingSorting
					.getSortByOrder()));// sort order
			proc.setInt(9, pagingSorting.getStartRecordId());// start index
			proc.setInt(10, pagingSorting.getEndRecordId());// end index
			proc.setInt(11, (pagingSorting.isPagingToBeDone() ? 1 : 0));// end
			// index

			rs = proc.executeQuery();
			int countFlag = 0;
			while (rs.next() != false) {
				countFlag++;

				objDto = new NewOrderDto();
				objDto.setAccountID(rs.getInt("ACCOUNTID"));
				objDto.setAccountName(rs.getString("ACCOUNTNAME"));
				objDto.setOrderNumber(rs.getInt("ORDERNO"));
				objDto.setOrderType(rs.getString("ORDERTYPE"));
				objDto.setOrderDate(rs.getString("ORDERDATE"));
				if (rs.getString("ORDERDATE") != null && !"".equals(rs.getString("ORDERDATE"))) 
				{
					
					Date date=df.parse(objDto.getOrderDate());
					objDto.setOrderDate((utility.showDate_Report(date)).toUpperCase());
					
				}
				objDto.setSourceName(rs.getString("SOURCE"));
				objDto.setQuoteNo(rs.getString("QUOTENO"));
				objDto.setCurrencyName(rs.getString("CURNAME"));
				// objDto.setStatus(new
				// Integer(rs.getString("STATUS")).intValue());
				objDto.setStageName(rs.getString("STAGE"));

				if (pagingSorting.isPagingToBeDone()) {
					recordCount = rs.getInt("FULL_REC_COUNT");
				}
				objUserList.add(objDto);
			}
			pagingSorting.setRecordCount(recordCount);
		} catch (Exception ex) {
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);
			//ex.printStackTrace();
			throw new Exception("SQL Exception : " + ex.getMessage(), ex);
		} finally {
			try {
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(proc);
				DbConnection.freeConnection(conn);

			} catch (Exception e) {
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);
				//e.printStackTrace();
				throw new Exception("Exception : " + e.getMessage(), e);
			}
		}

		return objUserList;
	}

	public ArrayList<NewOrderDto> searchOrderStatus(NewOrderDto objDto)
			throws Exception {
		//Nagarjuna
		String methodName="searchOrderStatus", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;
		//end Nagarjuna
		Connection connection = null;
		CallableStatement callstmt = null;
		ResultSet rs = null;
		NewOrderDto objConDto = new NewOrderDto();
		ArrayList<NewOrderDto> lstOrderStatusList = new ArrayList<NewOrderDto>();
		try {
			connection = DbConnection.getReportsConnectionObject();
			callstmt = connection.prepareCall(sqlSearchOrderStatues);
			callstmt.setString(1, objDto.getOrderStatus());
			rs = callstmt.executeQuery();
			while (rs.next()) {
				objConDto = new NewOrderDto();
				objConDto.setOrderStatus(rs.getString("STAGE"));
				lstOrderStatusList.add(objConDto);
			}
		} catch (Exception ex) {
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);
			//ex.printStackTrace();
		} finally {
			try {
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(callstmt);
				DbConnection.freeConnection(connection);
			} catch (SQLException e) {
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);
			//	e.printStackTrace();
			}
		}
		return lstOrderStatusList;
	}

	public ArrayList<ReportsDto> viewMasterHistory(ReportsDto objDto)throws Exception 
	{
		//Nagarjuna
		String methodName="viewMasterHistory", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;
		//end Nagarjuna
		Connection connection = null;
		CallableStatement callstmt = null;
	
		ResultSet rs = null;
		ReportsDto objConDto = new ReportsDto();
		ArrayList<ReportsDto> historyList= new ArrayList<ReportsDto>();
		Utility utility=new Utility();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try {
			connection = DbConnection.getReportsConnectionObject();
			callstmt = connection.prepareCall(sqlGetMasterReportStatus);
			callstmt.setString(1, objDto.getMasterValue());
			
			
			PagingSorting pagingSorting = objDto.getPagingSorting();
			pagingSorting.sync();// To calculate start index and Enc Index

			callstmt.setString(2, pagingSorting.getSortByColumn());// columnName
			callstmt.setString(3, PagingSorting.DB_Asc_Desc(pagingSorting
					.getSortByOrder()));// sort order
			callstmt.setInt(4, pagingSorting.getStartRecordId());// start index
			callstmt.setInt(5, pagingSorting.getEndRecordId());// end index
			callstmt.setInt(6, (pagingSorting.isPagingToBeDone() ? 1 : 0));// end
			
			
		
			rs = callstmt.executeQuery();
			int recordCount = 0;
			while (rs.next()) {
				objConDto = new ReportsDto();
				objConDto.setMasterName(rs.getString("MASTERNAME"));
				objConDto.setColumnName(rs.getString("COLUMNS"));
				objConDto.setOldValues(rs.getString("OLD_VALUES"));
				objConDto.setNewValues(rs.getString("NEW_VALUES"));
				objConDto.setOperationName(rs.getString("OPERATION_NAME"));
				objConDto.setModifiedDate(rs.getString("MODIFIED_DATE"));
				if (rs.getString("MODIFIED_DATE") != null && !"".equals(rs.getString("MODIFIED_DATE"))) 
				   {
					objConDto.setModifiedDate((utility.showDate_Report(new Date(rs.getTimestamp("MODIFIED_DATE").getTime()))).toUpperCase());
				   }
			
				
				objConDto.setModifiedBy(rs.getString("MODIFIED_BY"));
				objConDto.setAttribiuteId(rs.getString("ATTRIBUTE_ID"));
				if (pagingSorting.isPagingToBeDone()) {
					recordCount = rs.getInt("FULL_REC_COUNT");
				}
				historyList.add(objConDto);
			}
			pagingSorting.setRecordCount(recordCount);
		} catch (Exception ex) {
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);
			//ex.printStackTrace();
		} finally {
			try {
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(callstmt);
				DbConnection.freeConnection(connection);
			} catch (SQLException e) {
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);
				//e.printStackTrace();
			}
		}
		return historyList;
	}
	public ArrayList<ReportsDto> viewOrderStageList(ReportsDto objDto)
	throws Exception 
	{
		//Nagarjuna
		String methodName="viewOrderStageList", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;
		//end Nagarjuna
		ArrayList<ReportsDto> objUserList = new ArrayList<ReportsDto>();
		Connection conn = null;
		CallableStatement proc = null;
		ResultSet rs = null;
		int recordCount = 0;
		Utility utility=new Utility();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try 
		{
			conn = DbConnection.getReportsConnectionObject();
			proc = conn.prepareCall(sqlOrderStage);
			if (objDto.getOrderType() != null
					&& !"".equals(objDto.getOrderType())) {
				proc.setString(1, objDto.getOrderType().trim());
			} else {
				proc.setNull(1, java.sql.Types.VARCHAR);
			}
			if (objDto.getFromDate() != null
					&& !"".equals(objDto.getFromDate())) {
				proc.setString(2, objDto.getFromDate().trim());
			} else {
				proc.setNull(2, java.sql.Types.VARCHAR);
			}
			if (objDto.getToDate() != null && !"".equals(objDto.getToDate())) {
				proc.setString(3, objDto.getToDate().trim());
			} else {
				proc.setNull(3, java.sql.Types.VARCHAR);
			}
		
			PagingSorting pagingSorting = objDto.getPagingSorting();
			pagingSorting.sync();// To calculate start index and Enc Index
		
			proc.setString(4, pagingSorting.getSortByColumn());// columnName
			proc.setString(5, PagingSorting.DB_Asc_Desc(pagingSorting
					.getSortByOrder()));// sort order
			proc.setInt(6, pagingSorting.getStartRecordId());// start index
			proc.setInt(7, pagingSorting.getEndRecordId());// end index
			proc.setInt(8, (pagingSorting.isPagingToBeDone() ? 1 : 0));
			if (objDto.getOsp() != null && !"".equals(objDto.getOsp())) {
				proc.setString(9, objDto.getOsp().trim());
			} else {
				proc.setNull(9, java.sql.Types.VARCHAR);
			}
			
			
			
			rs = proc.executeQuery();
			int countFlag = 0;
			while (rs.next() != false) 
			{
				countFlag++;
				objDto = new ReportsDto();
				objDto.setOrderNumber(rs.getInt("ORDERNO"));
				objDto.setOrderType(rs.getString("ORDERTYPE"));
				objDto.setOrderStatus(rs.getString("STATUS"));
				objDto.setOrderDate(rs.getString("ORDERDATE"));
				if (rs.getString("ORDERDATE") != null && !"".equals(rs.getString("ORDERDATE"))) 
				{
					objDto.setOrderDate((utility.showDate_Report(new Date(rs.getTimestamp("ORDERDATE").getTime()))).toUpperCase());
				}
				
				objDto.setRegionName(rs.getString("REGIONNAME"));
				objDto.setZoneName(rs.getString("ZONENNAME"));
				objDto.setAccountName(rs.getString("ACCOUNTNAME"));
				objDto.setAccountManager(rs.getString("ACCOUNTMANAGER"));
				objDto.setProjectManager(rs.getString("PROJECTMANAGER"));	
				objDto.setServiceId(rs.getInt("SERVICEID"));
				objDto.setEffStartDate(rs.getString("EFFSTARTDATE"));
				if (rs.getString("EFFSTARTDATE") != null && !"".equals(rs.getString("EFFSTARTDATE"))) 
				{
					
					Date date=df.parse(objDto.getEffStartDate());
					objDto.setEffStartDate((utility.showDate_Report(date)).toUpperCase());
					
				}
				objDto.setM6OrderNumber(rs.getInt("M6ORDERNO"));
				objDto.setCustLogicalSI(rs.getInt("CUSTOMER_LOGICAL_SI_NO"));	
				objDto.setPublishedDate(rs.getString("PUBLISHED_DATE"));
				if (rs.getString("PUBLISHED_DATE") != null && !"".equals(rs.getString("PUBLISHED_DATE")))
				{
					objDto.setPublishedDate((utility.showDate_Report(new Date(rs.getTimestamp("PUBLISHED_DATE").getTime()))).toUpperCase());
				}
				objDto.setServiceDetDescription(rs.getString("SERVICESTAGE"));
				objDto.setPartyName(rs.getString("PARTYNAME"));
				objDto.setPartyNo(rs.getInt("PARTY_NO"));
				objDto.setBillingStatus(rs.getString("BILLING_STATUS"));
				objDto.setServiceStage(rs.getString("SERVICE_STAGE"));
				objDto.setParentOrderSubType(rs.getString("PARENTORDERSUBTYPE"));
				objDto.setAmName(rs.getString("AMNAME"));
				objDto.setPmName(rs.getString("PMNAME"));
				objDto.setCopcName(rs.getString("COPCNAME"));
				objDto.setStandardReason(rs.getString("STANDARDREASON"));
				objDto.setM6OrderDate(rs.getString("CREATED_DATE"));
				if (rs.getString("CREATED_DATE") != null && !"".equals(rs.getString("CREATED_DATE")))
				{
					objDto.setM6OrderDate((utility.showDate_Report(new Date(rs.getTimestamp("CREATED_DATE").getTime()))).toUpperCase());
				}
				objDto.setAmApproveDate(rs.getString("AM_APPROVAL_DATE"));
				if (rs.getString("AM_APPROVAL_DATE") != null && !"".equals(rs.getString("AM_APPROVAL_DATE")))
				{
					objDto.setAmApproveDate((utility.showDate_Report(new Date(rs.getTimestamp("AM_APPROVAL_DATE").getTime()))).toUpperCase());
				}
				objDto.setPmApproveDate(rs.getString("PM_APPROVAL_DATE"));
				if (rs.getString("PM_APPROVAL_DATE") != null && !"".equals(rs.getString("PM_APPROVAL_DATE")))
				{
					objDto.setPmApproveDate((utility.showDate_Report(new Date(rs.getTimestamp("PM_APPROVAL_DATE").getTime()))).toUpperCase());
				}
				objDto.setCopcApproveDate(rs.getString("COPC_APPROVAL_DATE"));
				if (rs.getString("COPC_APPROVAL_DATE") != null && !"".equals(rs.getString("COPC_APPROVAL_DATE")))
				{
					objDto.setCopcApproveDate((utility.showDate_Report(new Date(rs.getTimestamp("COPC_APPROVAL_DATE").getTime()))).toUpperCase());
				}
				objDto.setCircuitStatus(rs.getString("CIRCUIT_STATUS"));
				objDto.setOrderProvision(rs.getString("ORDER_PROVISION"));
				objDto.setVerticalDetails(rs.getString("VERTICAL_DETAILS"));
				objDto.setOsp(rs.getString("OSP"));
		
				if (pagingSorting.isPagingToBeDone()) 
				{
					recordCount = rs.getInt("FULL_REC_COUNT");
				}
				objUserList.add(objDto);
			}
			pagingSorting.setRecordCount(recordCount);
		} 	
		catch (Exception ex) 
		{
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);
			//ex.printStackTrace();
			throw new Exception("SQL Exception : " + ex.getMessage(), ex);
		} 		
		finally 
		{
			try 
			{
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(proc);
				DbConnection.freeConnection(conn);
			} 
			catch (Exception e) 
			{
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
				//e.printStackTrace();
				throw new Exception("Exception : " + e.getMessage(), e);
			}
		}
		return objUserList;
	}
//	Ramana
	public ArrayList<ReportsDto> viewPerformanceList(ReportsDto objDto) 
	{
		//Nagarjuna
		String methodName="viewPerformanceList", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;
		//end Nagarjuna
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rs = null;
		ArrayList<ReportsDto> listSearchDetails = new ArrayList<ReportsDto>();
		ReportsDto objReportsDto = null;
		int recordCount =0;
		Utility utility=new Utility();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try
		{
			connection=DbConnection.getReportsConnectionObject();
			proc= connection.prepareCall(sqlGetPerformanceReport);
			
			if (objDto.getOrderType() != null
					&& !"".equals(objDto.getOrderType())) {
				proc.setString(1, objDto.getOrderType().trim());
			} else {
				proc.setNull(1, java.sql.Types.VARCHAR);
			}
			
			if (objDto.getFromDate() != null
					&& !"".equals(objDto.getFromDate())) {
				proc.setString(2, objDto.getFromDate().trim());
			} else {
				proc.setNull(2, java.sql.Types.VARCHAR);
			}
			
			if (objDto.getToDate() != null 
					&& !"".equals(objDto.getToDate())) {
				proc.setString(3, objDto.getToDate().trim());
			} else {
				proc.setNull(3, java.sql.Types.VARCHAR);
			}
			
			if (objDto.getFromOrderNo() != 0 
					&& !"".equals(objDto.getFromOrderNo())) {
				proc.setInt(4, objDto.getFromOrderNo());
			} else {
				proc.setNull(4,java.sql.Types.BIGINT);
			}
			
			if (objDto.getToOrderNo() != 0 
					&& !"".equals(objDto.getToOrderNo())) {
				proc.setInt(5, objDto.getToOrderNo());
			} else {
				proc.setNull(5, java.sql.Types.BIGINT);
			}
			
			if (objDto.getFromAccountNo() != 0 
					&& !"".equals(objDto.getFromAccountNo())) {
				proc.setInt(6, objDto.getFromAccountNo());
			} else {
				proc.setNull(6,java.sql.Types.BIGINT);
			}
			
			if (objDto.getToAccountNo() != 0 
					&& !"".equals(objDto.getToAccountNo())) {
				proc.setInt(7, objDto.getToAccountNo());
			} else {
				proc.setNull(7, java.sql.Types.BIGINT);
			}

			PagingSorting pagingSorting = objDto.getPagingSorting();
			pagingSorting.sync();// To calculate start index and Enc Index

			proc.setString(8, pagingSorting.getSortByColumn());// columnName
			proc.setString(9, PagingSorting.DB_Asc_Desc(pagingSorting
					.getSortByOrder()));// sort order
			proc.setInt(10, pagingSorting.getStartRecordId());// start index
			 proc.setInt(11, pagingSorting.getEndRecordId());// end index
			proc.setInt(12, (pagingSorting.isPagingToBeDone() ? 1 : 0));
			if (objDto.getOsp() != null && !"".equals(objDto.getOsp())) {
				proc.setString(13, objDto.getOsp().trim());
			} else {
				proc.setNull(13, java.sql.Types.VARCHAR);
			}
			// index
			rs = proc.executeQuery();
			while(rs.next())
			{
				objReportsDto =  new ReportsDto();
				objReportsDto.setOrderNumber(rs.getInt("ORDERNO"));
				objReportsDto.setOrderDate(rs.getString("ORDERDATE"));
				if (rs.getString("ORDERDATE") != null && !"".equals(rs.getString("ORDERDATE"))) 
				{
					
					Date date=df.parse(objReportsDto.getOrderDate());
					objReportsDto.setOrderDate((utility.showDate_Report(date)).toUpperCase());
					
				}
				
				objReportsDto.setProductName(rs.getString("PRODUCT_NAME"));
				objReportsDto.setRegionName(rs.getString("REGIONNAME"));
				objReportsDto.setZoneName(rs.getString("ZONENNAME"));
				objReportsDto.setCrmAccountNoString(rs.getString("CRMACCOUNTNO"));
				objReportsDto.setAccountId(rs.getLong("ACCOUNTID"));
				objReportsDto.setPartyName(rs.getString("PARTYNAME"));
				objReportsDto.setVerticalDetails(rs.getString("VERTICAL_DETAILS"));
				objReportsDto.setAccountManager(rs.getString("ACCOUNTMANAGER"));
				objReportsDto.setProjectManager(rs.getString("PROJECTMANAGER"));
				objReportsDto.setOrderType(rs.getString("ORDERTYPE"));
				objReportsDto.setOrder_type(rs.getString("ORDER_TYPE"));
				objReportsDto.setCustPoDate(rs.getString("CUSTPODATE"));
				if (rs.getString("CUSTPODATE") != null && !"".equals(rs.getString("CUSTPODATE"))) 
				{
					
					Date date=df.parse(objReportsDto.getCustPoDate());
					objReportsDto.setCustPoDate((utility.showDate_Report(date)).toUpperCase());
					
				}
				objReportsDto.setPoRecieveDate(rs.getString("PORECEIVEDATE"));
				if (rs.getString("PORECEIVEDATE") != null && !"".equals(rs.getString("PORECEIVEDATE"))) 
				{
					
					Date date=df.parse(objReportsDto.getPoRecieveDate());
					objReportsDto.setPoRecieveDate((utility.showDate_Report(date)).toUpperCase());
					
				}
				objReportsDto.setPoAmountSum(rs.getLong("POAMOUNT"));
				objReportsDto.setPo_recpt_delay(rs.getString("PO_RECPT_DELAY"));
				objReportsDto.setPo_logging_delay(rs.getString("PO_LOGGING_DELAY"));
				objReportsDto.setOrder_completion_date(rs.getString("ORDER_COMPLETION_DATE"));
				objReportsDto.setAppr_delay_in_region(rs.getString("APPR_DELAY_IN_REGION"));
				objReportsDto.setDays_in_copc(rs.getString("DAYS_IN_COPC"));
				objReportsDto.setDays_for_order(rs.getString("DAYS_FOR_ORDER"));
				objReportsDto.setAmApproveDate(rs.getString("AM_APPROVAL_DATE"));
				if (rs.getString("AM_APPROVAL_DATE") != null && !"".equals(rs.getString("AM_APPROVAL_DATE"))) 
				{
					objReportsDto.setAmApproveDate((utility.showDate_Report(new Date(rs.getTimestamp("AM_APPROVAL_DATE").getTime()))).toUpperCase());
				}
				objReportsDto.setPmApproveDate(rs.getString("PM_APPROVAL_DATE"));
				if (rs.getString("PM_APPROVAL_DATE") != null && !"".equals(rs.getString("PM_APPROVAL_DATE"))) 
				{
					objReportsDto.setPmApproveDate((utility.showDate_Report(new Date(rs.getTimestamp("PM_APPROVAL_DATE").getTime()))).toUpperCase());
				}
				objReportsDto.setCopcApproveDate(rs.getString("COPC_APPROVAL_DATE"));
                
				objReportsDto.setOsp(rs.getString("OSP"));
				objReportsDto.setCus_segment(rs.getString("CUST_SEGMENT_CODE"));
				objReportsDto.setOrderStatus(rs.getString("APPROVAL_STATUS"));

				if (rs.getString("COPC_APPROVAL_DATE") != null && !"".equals(rs.getString("COPC_APPROVAL_DATE"))) 
				{
					objReportsDto.setCopcApproveDate((utility.showDate_Report(new Date(rs.getTimestamp("COPC_APPROVAL_DATE").getTime()))).toUpperCase());
				}
				if (pagingSorting.isPagingToBeDone()) {
					recordCount = rs.getInt("FULL_REC_COUNT");
				}
				listSearchDetails.add(objReportsDto);
			}
			pagingSorting.setRecordCount(recordCount);
		}
		catch(Exception ex )
		{
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			//ex.printStackTrace();	
		}
		finally
		{
			try 
			{
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(proc);
				DbConnection.freeConnection(connection);
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				//e.printStackTrace();
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			}
		}
		return listSearchDetails;
	}
	//Ramana
	//Created By Ashutosh
	public ArrayList<ReportsDto> viewPerformanceSummaryReport(ReportsDto objDto) 
	{
		//Nagarjuna
		String methodName="viewPerformanceSummaryReport", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;
		//end Nagarjuna
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rs = null;
		ArrayList<ReportsDto> listSearchDetails = new ArrayList<ReportsDto>();
		ReportsDto objReportsDto = null;
		int recordCount =0;
		Utility utility=new Utility();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try
		{
			connection=DbConnection.getReportsConnectionObject();
			proc= connection.prepareCall(sqlGetPerformanceSummaryReport);
			if (objDto.getOrderType() != null
					&& !"".equals(objDto.getOrderType())) {
				proc.setString(1, objDto.getOrderType().trim());
			} else {
				proc.setNull(1, java.sql.Types.VARCHAR);
			}
			if (objDto.getFromDate() != null
					&& !"".equals(objDto.getFromDate())) {
				proc.setString(2, objDto.getFromDate().trim());
			} else {
				proc.setNull(2, java.sql.Types.VARCHAR);
			}
			if (objDto.getToDate() != null 
					&& !"".equals(objDto.getToDate())) {
				proc.setString(3, objDto.getToDate().trim());
			} else {
				proc.setNull(3, java.sql.Types.VARCHAR);
			}
			if (objDto.getFromOrderNo() != 0 
					&& !"".equals(objDto.getFromOrderNo())) {
				proc.setInt(4, objDto.getFromOrderNo());
			} else {
				proc.setNull(4,java.sql.Types.BIGINT);
			}
			if (objDto.getToOrderNo() != 0 
					&& !"".equals(objDto.getToOrderNo())) {
				proc.setInt(5, objDto.getToOrderNo());
			} else {
				proc.setNull(5, java.sql.Types.BIGINT);
			}
			if (objDto.getFromAccountNo() != 0 
					&& !"".equals(objDto.getFromAccountNo())) {
				proc.setInt(6, objDto.getFromAccountNo());
			} else {
				proc.setNull(6,java.sql.Types.BIGINT);
			}
			if (objDto.getToAccountNo() != 0 
					&& !"".equals(objDto.getToAccountNo())) {
				proc.setInt(7, objDto.getToAccountNo());
			} else {
				proc.setNull(7, java.sql.Types.BIGINT);
			}
			PagingSorting pagingSorting = objDto.getPagingSorting();
			pagingSorting.sync();// To calculate start index and Enc Index
			proc.setString(8, pagingSorting.getSortByColumn());// columnName
			proc.setString(9, PagingSorting.DB_Asc_Desc(pagingSorting
					.getSortByOrder()));// sort order
			proc.setInt(10, pagingSorting.getStartRecordId());// start index
			 proc.setInt(11, pagingSorting.getEndRecordId());// end index
			proc.setInt(12, (pagingSorting.isPagingToBeDone() ? 1 : 0));
			if (objDto.getOsp() != null && !"".equals(objDto.getOsp())) {
				proc.setString(13, objDto.getOsp().trim());
			} else {
				proc.setNull(13, java.sql.Types.VARCHAR);
			}
			// index
			rs = proc.executeQuery();
			while(rs.next())
			{
				objReportsDto =  new ReportsDto();
				objReportsDto.setOrderNumber(rs.getInt("ORDERNO"));
				objReportsDto.setParty_no(rs.getInt("Party_NO"));
				//objReportsDto.setServiceId(rs.getInt("SERVICEID"));
				objReportsDto.setRegionName(rs.getString("REGION"));
				objReportsDto.setZoneName(rs.getString("ZONE"));
				objReportsDto.setCrmAccountNoString(rs.getString("CRMACCOUNTNO"));
				//objReportsDto.setAccountId(rs.getLong("ACCOUNTID"));
				objReportsDto.setPartyName(rs.getString("PARTYNAME"));
				objReportsDto.setCus_segment(rs.getString("CUST_SEGMENT_CODE"));
				objReportsDto.setIndustrySegment(rs.getString("INDUSTRYSEGMENT"));
				
				//objReportsDto.setVerticalDetails(rs.getString("VERTICAL_DETAILS"));
				objReportsDto.setAccountManager(rs.getString("ACCOUNTMANAGER"));
				objReportsDto.setProjectManager(rs.getString("PROJECTMANAGER"));
				objReportsDto.setOrderType(rs.getString("ORDERTYPE"));
				objReportsDto.setOrder_type(rs.getString("DEMO_TYPE"));
				objReportsDto.setSubChangeTypeName(rs.getString("ORDER_SUBTYPE"));
				objReportsDto.setOrderStage(rs.getString("STAGE"));
				objReportsDto.setOrderDate(rs.getString("ORDERDATE"));
				if (rs.getString("ORDERDATE") != null && !"".equals(rs.getString("ORDERDATE")))
				{
					
					Date date=df.parse(objReportsDto.getOrderDate());
					objReportsDto.setOrderDate((utility.showDate_Report(date)).toUpperCase());
					
				}
				
				//objReportsDto.setPoRecieveDate(rs.getString("PORECEIVEDATE"));
				objReportsDto.setPoAmountSum(rs.getLong("POAMOUNT"));
				objReportsDto.setCopcApproveDate(rs.getString("ORDER_APPROVED_DATE"));//Copc date
				if (rs.getString("ORDER_APPROVED_DATE") != null && !"".equals(rs.getString("ORDER_APPROVED_DATE")))
					{
						objReportsDto.setCopcApproveDate((utility.showDate_Report(new Date(rs.getTimestamp("ORDER_APPROVED_DATE").getTime()))).toUpperCase());
					}
				objReportsDto.setPublishedDate(rs.getString("Published_Date"));
				if (rs.getString("Published_Date") != null && !"".equals(rs.getString("Published_Date")))
					{
						objReportsDto.setPublishedDate((utility.showDate_Report(new Date(rs.getTimestamp("Published_Date").getTime()))).toUpperCase());
					}
				//objReportsDto.setCopcApproveDate(rs.getString("COPC_APPROVAL_DATE"));
				objReportsDto.setDayInAM(rs.getString("DAYS_IN_AM"));
				objReportsDto.setDayInPM(rs.getString("DAYS_IN_PM"));
				objReportsDto.setDayInCOPC(rs.getString("DAYS_IN_COPC"));
				objReportsDto.setDayInSED(rs.getString("DAYS_IN_SED"));
				objReportsDto.setTotalDays(rs.getString("TOTAL_DAYS"));
				objReportsDto.setOsp(rs.getString("OSP"));
				if (pagingSorting.isPagingToBeDone()) {
					recordCount = rs.getInt("FULL_REC_COUNT");
				}
				listSearchDetails.add(objReportsDto);
			}
			pagingSorting.setRecordCount(recordCount);
		}
		catch(Exception ex )
		{
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			//ex.printStackTrace();	
		}
		finally
		{
			try 
			
			{
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(proc);
				DbConnection.freeConnection(connection);
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				//e.printStackTrace();
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			}
		}
		return listSearchDetails;
	}
	public ArrayList<ReportsDto> viewPendingServicesReport(ReportsDto objDto) 
	{
		//Nagarjuna
		String methodName="viewPendingServicesReport", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;
		//end Nagarjuna
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rs = null;
		ArrayList<ReportsDto> listSearchDetails = new ArrayList<ReportsDto>();
		ReportsDto objReportsDto = null;
		int recordCount =0;
		Utility utility=new Utility();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try
		{
			connection=DbConnection.getReportsConnectionObject();
			proc= connection.prepareCall(sqlGetPendingServicesReport);
			if (objDto.getOrderType() != null
					&& !"".equals(objDto.getOrderType())) {
				proc.setString(1, objDto.getOrderType().trim());
			} else {
				proc.setNull(1, java.sql.Types.VARCHAR);
			}
			if (objDto.getFromDate() != null
					&& !"".equals(objDto.getFromDate())) {
				proc.setString(2, objDto.getFromDate().trim());
			} else {
				proc.setNull(2, java.sql.Types.VARCHAR);
			}
			if (objDto.getToDate() != null 
					&& !"".equals(objDto.getToDate())) {
				proc.setString(3, objDto.getToDate().trim());
			} else {
				proc.setNull(3, java.sql.Types.VARCHAR);
			}
			if (objDto.getFromOrderNo() != 0 
					&& !"".equals(objDto.getFromOrderNo())) {
				proc.setInt(4, objDto.getFromOrderNo());
			} else {
				proc.setNull(4,java.sql.Types.BIGINT);
			}
			if (objDto.getToOrderNo() != 0 
					&& !"".equals(objDto.getToOrderNo())) {
				proc.setInt(5, objDto.getToOrderNo());
			} else {
				proc.setNull(5, java.sql.Types.BIGINT);
			}
			if (objDto.getFromAccountNo() != 0 
					&& !"".equals(objDto.getFromAccountNo())) {
				proc.setInt(6, objDto.getFromAccountNo());
			} else {
				proc.setNull(6,java.sql.Types.BIGINT);
			}
			if (objDto.getToAccountNo() != 0 
					&& !"".equals(objDto.getToAccountNo())) {
				proc.setInt(7, objDto.getToAccountNo());
			} else {
				proc.setNull(7, java.sql.Types.BIGINT);
			}
			PagingSorting pagingSorting = objDto.getPagingSorting();
			pagingSorting.sync();// To calculate start index and Enc Index
			proc.setString(8, pagingSorting.getSortByColumn());// columnName
			proc.setString(9, PagingSorting.DB_Asc_Desc(pagingSorting
					.getSortByOrder()));// sort order
			proc.setInt(10, pagingSorting.getStartRecordId());// start index
			 proc.setInt(11, pagingSorting.getEndRecordId());// end index
			proc.setInt(12, (pagingSorting.isPagingToBeDone() ? 1 : 0));
			rs = proc.executeQuery();
			while(rs.next())
			{
				objReportsDto =  new ReportsDto();				
				objReportsDto.setParty_no(rs.getInt("Party_NO"));
				objReportsDto.setPartyName(rs.getString("PARTYNAME"));				
				objReportsDto.setCrmAccountNoString(rs.getString("CRMACCOUNTNO"));
				objReportsDto.setAccountManager(rs.getString("ACCOUNTMANAGER"));
				objReportsDto.setOrderDate(rs.getString("ORDERDATE"));
				if (rs.getString("ORDERDATE") != null && !"".equals(rs.getString("ORDERDATE")))
				{
					objReportsDto.setOrderDate((utility.showDate_Report(new Date(rs.getTimestamp("ORDERDATE").getTime()))).toUpperCase());
				}
				objReportsDto.setAmApproveDate(rs.getString("AM_APPROVED_DATE"));
				if (rs.getString("AM_APPROVED_DATE") != null && !"".equals(rs.getString("AM_APPROVED_DATE")))
				{
					objReportsDto.setAmApproveDate((utility.showDate_Report(new Date(rs.getTimestamp("AM_APPROVED_DATE").getTime()))).toUpperCase());
				}
				objReportsDto.setAmName(rs.getString("AM_APPROVED_BY")); 
				objReportsDto.setProjectManager(rs.getString("PROJECTMANAGER"));
				objReportsDto.setPmApproveDate(rs.getString("PM_APPROVED_DATE"));
				if (rs.getString("PM_APPROVED_DATE") != null && !"".equals(rs.getString("PM_APPROVED_DATE")))
				{
					objReportsDto.setPmApproveDate((utility.showDate_Report(new Date(rs.getTimestamp("PM_APPROVED_DATE").getTime()))).toUpperCase());
				}
				objReportsDto.setPmName(rs.getString("PM_APPROVED_BY"));
				objReportsDto.setCopcApproveDate(rs.getString("FINAL_APPROVED_DATE"));//Copc date
				if (rs.getString("FINAL_APPROVED_DATE") != null && !"".equals(rs.getString("FINAL_APPROVED_DATE")))
				{
					objReportsDto.setCopcApproveDate((utility.showDate_Report(new Date(rs.getTimestamp("FINAL_APPROVED_DATE").getTime()))).toUpperCase());
				}
				objReportsDto.setCopcName(rs.getString("COPC_APPROVED_BY"));
				objReportsDto.setOrderType(rs.getString("ORDERTYPE"));
				objReportsDto.setOrderStage(rs.getString("APPROVED_STATUS"));
				objReportsDto.setServiceName(rs.getString("SERVICENAME"));
				objReportsDto.setM6OrderDate(rs.getString("M6_ORDER_DATE"));
				if (rs.getString("M6_ORDER_DATE") != null && !"".equals(rs.getString("M6_ORDER_DATE")))
				{
					objReportsDto.setM6OrderDate((utility.showDate_Report(new Date(rs.getTimestamp("M6_ORDER_DATE").getTime()))).toUpperCase());
				}
				objReportsDto.setBillingStatus(rs.getString("BILLING_STATUS"));			
				objReportsDto.setPublishedDate(rs.getString("PUBLISHED_DATE"));
				if (rs.getString("PUBLISHED_DATE") != null && !"".equals(rs.getString("PUBLISHED_DATE")))
				{
					objReportsDto.setPublishedDate((utility.showDate_Report(new Date(rs.getTimestamp("PUBLISHED_DATE").getTime()))).toUpperCase());
				}
				objReportsDto.setEffStartDate(rs.getString("EFFECTIVE_START_DATE"));
				if (rs.getString("EFFECTIVE_START_DATE") != null && !"".equals(rs.getString("EFFECTIVE_START_DATE")))
				{
					objReportsDto.setEffStartDate((utility.showDate_Report(new Date(rs.getTimestamp("EFFECTIVE_START_DATE").getTime()))).toUpperCase());
				}
				objReportsDto.setOrderStatus(rs.getString("ORDER_STATUS"));
				objReportsDto.setServiceStage(rs.getString("SERVICE_STAGE"));
				objReportsDto.setCircuitStatus(rs.getString("CIRCUIT_STATUS"));
				objReportsDto.setRegionName(rs.getString("REGION"));
				objReportsDto.setZoneName(rs.getString("ZONE"));
				objReportsDto.setStandardReason(rs.getString("STANDARD_REASON"));
				objReportsDto.setLogicalSINo(rs.getString("LOGICAL_SI_NO"));
				objReportsDto.setOrderNumber(rs.getInt("ORDERNO"));				
				objReportsDto.setServiceIdString(rs.getString("SERVICEID"));
				objReportsDto.setAccountId(rs.getLong("ACCOUNTID"));
				if (pagingSorting.isPagingToBeDone()) {
					recordCount = rs.getInt("FULL_REC_COUNT");
				}
				listSearchDetails.add(objReportsDto);
			}
			pagingSorting.setRecordCount(recordCount);
		}
		catch(Exception ex )
		{
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			//ex.printStackTrace();	
		}
		finally
		{
			try 
			{
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(proc);
				DbConnection.freeConnection(connection);
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				//e.printStackTrace();
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			}
		}
		return listSearchDetails;
	}
	
	public ArrayList<NonAPP_APPChangeOrderDetailsDTO> viewNonMigAppUnappNewOrderDetails(NonAPP_APPChangeOrderDetailsDTO objDto) 
	{
		//Nagarjuna
		String methodName="viewNonMigAppUnappNewOrderDetails", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;
		//end Nagarjuna
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rs = null;
		ArrayList<NonAPP_APPChangeOrderDetailsDTO> listSearchDetails = new ArrayList<NonAPP_APPChangeOrderDetailsDTO>();
		NonAPP_APPChangeOrderDetailsDTO objReportsDto = null;
		int recordCount =0;
		Utility utility=new Utility();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date tempDate=null;
		try
		{
			connection=DbConnection.getReportsConnectionObject();
			proc= connection.prepareCall(sqlGetNonMigAppUnappNewOrderDetails);
			if (objDto.getOrderType() != null
					&& !"".equals(objDto.getOrderType())) {
				proc.setString(1, objDto.getOrderType().trim());
			} else {
				proc.setNull(1, java.sql.Types.VARCHAR);
			}
			if (objDto.getApprovalType() != null
					&& !"".equals(objDto.getApprovalType())) {
				proc.setString(2, objDto.getApprovalType().trim());
			} else {
				proc.setNull(2, java.sql.Types.VARCHAR);
			}
			if (objDto.getServiceOrderType() != null
					&& !"".equals(objDto.getServiceOrderType())) {
				proc.setInt(3, Integer.parseInt(objDto.getServiceOrderType().trim()));
			} else {
				proc.setNull(3, java.sql.Types.BIGINT);
			}
			if (objDto.getOrdermonth() != null
					&& !"".equals(objDto.getOrdermonth())) {
				proc.setString(4, objDto.getOrdermonth().trim());
			} else {
				proc.setNull(4, java.sql.Types.VARCHAR);
			}
			if (objDto.getVerticalDetails() != null
					&& !"".equals(objDto.getVerticalDetails())) {
				proc.setString(5, objDto.getVerticalDetails().trim());
			} else {
				proc.setNull(5, java.sql.Types.VARCHAR);
			}
			if (objDto.getServiceName() != null
					&& !"".equals(objDto.getServiceName())) {
				proc.setString(6, objDto.getServiceName().trim());
			} else {
				proc.setNull(6, java.sql.Types.VARCHAR);
			}
			if (objDto.getFromOrderNo() != 0 
					&& !"".equals(objDto.getFromOrderNo())) {
				proc.setInt(7, objDto.getFromOrderNo());
			} else {
				proc.setNull(7,java.sql.Types.BIGINT);
			}
			if (objDto.getToOrderNo() != 0 
					&& !"".equals(objDto.getToOrderNo())) {
				proc.setInt(8, objDto.getToOrderNo());
			} else {
				proc.setNull(8, java.sql.Types.BIGINT);
			}
			PagingSorting pagingSorting = objDto.getPagingSorting();
			pagingSorting.sync();// To calculate start index and Enc Index
			proc.setString(9, pagingSorting.getSortByColumn());// columnName
			proc.setString(10, PagingSorting.DB_Asc_Desc(pagingSorting
					.getSortByOrder()));// sort order
			proc.setInt(11, pagingSorting.getStartRecordId());// start index
			 proc.setInt(12, pagingSorting.getEndRecordId());// end index
			proc.setInt(13, (pagingSorting.isPagingToBeDone() ? 1 : 0));
			proc.setInt(14,objDto.getIsUsage());
			if (objDto.getOrderyear() != null
					&& !"".equals(objDto.getOrderyear())) {
				proc.setString(15, objDto.getOrderyear().trim());
			} else {
				proc.setNull(15, java.sql.Types.VARCHAR);
			}
			rs = proc.executeQuery();
			while(rs.next())
			{
				setBlank();
				replaceSeperator("PRIMARYLOCATION",rs.getString("PRIMARYLOCATION"));
				replaceSeperator("SECONDARYLOCATION",rs.getString("SECONDARYLOCATION"));

				objReportsDto =  new NonAPP_APPChangeOrderDetailsDTO();
				objReportsDto.setParty_no(rs.getInt("Party_NO"));
				objReportsDto.setCrmAccountNoString(rs.getString("CRMACCOUNTNO"));
				objReportsDto.setPartyName(rs.getString("PARTYNAME"));
				objReportsDto.setVerticalDetails(rs.getString("VERTICAL_DETAILS"));
				objReportsDto.setRegionName(rs.getString("REGION"));      
				objReportsDto.setOrderNumber(rs.getInt("ORDERNO"));	
				objReportsDto.setOrderType(rs.getString("ORDERTYPE"));
				objReportsDto.setM6OrderNo(rs.getInt("M6ORDERNO"));
				objReportsDto.setServiceName(rs.getString("SERVICENAME"));
				objReportsDto.setCustomer_logicalSINumber(rs.getInt("CUSTOMER_LOGICAL_SI_NO"));
				objReportsDto.setServiceDetDescription(rs.getString("SERVICEDETDESCRIPTION"));//LineName
				objReportsDto.setServiceOrderType(rs.getString("SERVICE_ORDER_TYPE_DESC"));
				objReportsDto.setLogicalCircuitId(rs.getString("CKTID"));
				objReportsDto.setBandwaidth(rs.getString("BANDWIDTH"));     
				objReportsDto.setBilling_bandwidth(rs.getString("BILLING_BANDWIDTH"));
				objReportsDto.setBillUom(rs.getString("BILLING_BANDWIDTH_UOM"));
				objReportsDto.setKmsDistance(rs.getString("DISTANCE"));
				objReportsDto.setRatio(rs.getString("RATIO"));
				objReportsDto.setFromLocation(rs.getString("FROM_ADDRESS"));
				objReportsDto.setToLocation(rs.getString("TO_ADDRESS"));
				// change
				objReportsDto.setPrimaryLocation(VAR_PRIMARYLOCATION);
				objReportsDto.setSecondaryLocation(VAR_SECONDARYLOCATION);
				objReportsDto.setProductName(rs.getString("PRODUCTNAME"));
				objReportsDto.setSubProductName(rs.getString("SUBPRODUCTNAME"));
				objReportsDto.setEntity(rs.getString("ENTITYNAME"));//Legal Entity
				objReportsDto.setLcompanyname(rs.getString("LCOMPANYNAME"));
				objReportsDto.setCurrencyName(rs.getString("CURRENCYNAME"));
				objReportsDto.setCreditPeriodName(rs.getString("CREDITPERIOD"));
				objReportsDto.setBillingTypeName(rs.getString("BILLING_TYPENAME"));//Bill Type
				objReportsDto.setBillingFormatName(rs.getString("BILLING_FORMATNAME"));
				objReportsDto.setBillingLevelName(rs.getString("BILLING_LEVELNAME"));
				objReportsDto.setBillingLevelNo(rs.getLong("BILLING_LEVEL_NO"));
				objReportsDto.setTaxation(rs.getString("TAXATIONVALUE"));					
				objReportsDto.setBillingMode(rs.getString("BILLINGMODE"));
				objReportsDto.setPonum(rs.getLong("PONUMBER"));
				// change
				tempDate=rs.getDate("PODATE");
				if(tempDate!=null)
				{
					objReportsDto.setPoDate((Utility.showDate_Report(tempDate)).toUpperCase());
				}
				objReportsDto.setContractPeriod(rs.getInt("CONTRACTPERIOD"));//Period In Month
				objReportsDto.setTotalPoAmt(rs.getString("TOTALPOAMOUNT"));
				// change
				tempDate=rs.getDate("PORECEIVEDATE");
				if(tempDate!=null)
				{
					objReportsDto.setPoReceiveDate((Utility.showDate_Report(tempDate)).toUpperCase());
				}
				objReportsDto.setCustPoDetailNo(rs.getString("CUSTPONUMBER"));
				// change
				tempDate=rs.getDate("CUSTPODATE");   
				if(tempDate!=null)
				{
					objReportsDto.setCustPoDate((Utility.showDate_Report(tempDate)).toUpperCase());
				}
				objReportsDto.setPoAmount(rs.getString("CUST_TOT_PO_AMT"));
				
				objReportsDto.setLocDate(rs.getString("LOCDATE"));
				if (rs.getString("LOCDATE") != null && !"".equals(rs.getString("LOCDATE")))
				{
					
					Date date=df.parse(objReportsDto.getLocDate());
					objReportsDto.setLocDate((Utility.showDate_Report(date)).toUpperCase());
					
				}
				objReportsDto.setLOC_No(rs.getString("LOCNO"));  
				objReportsDto.setBillingTriggerDate(rs.getString("BILLINGTRIGGERDATE"));
				if (rs.getString("BILLINGTRIGGERDATE") != null && !"".equals(rs.getString("BILLINGTRIGGERDATE")))
				{
					
					Date date=df.parse(objReportsDto.getBillingTriggerDate());
					objReportsDto.setBillingTriggerDate((Utility.showDate_Report(date)).toUpperCase());
					
				}
				//Bill Trg Create Date
				objReportsDto.setPmApproveDate(rs.getString("Pm_Prov_Date"));
				if (rs.getString("Pm_Prov_Date") != null && !"".equals(rs.getString("Pm_Prov_Date")))
				{
					  String s1=rs.getString("Pm_Prov_Date");
					  String s3=s1.substring(0,7);
					  String s4=s1.substring(9,11);
					  String s5=s3.concat(s4);
					  objReportsDto.setPmApproveDate(s5);
					
					
				}
				objReportsDto.setBilling_Trigger_Flag(rs.getString("Billing_Trigger_Flag"));
				
				
				//Business Serial No	
				//Opms Account Id	
				objReportsDto.setOrderLineNumber(rs.getInt("Order_Line_Id"));//Lineitemnumber	
				//Order Month	
				objReportsDto.setFx_external_acc_id(rs.getString("Child_Account_Number"));
				objReportsDto.setChildAccountFXSataus(rs.getString("Child_Account_FX_Sataus"));
				if(objDto.getIsUsage() == 1)
				{
					objReportsDto.setOrderStage(rs.getString("ORDERSTAGE"));
					objReportsDto.setComponentInfoID(rs.getInt("COMPONENTINFOID"));
					objReportsDto.setComponentID(rs.getInt("COMPONENT_ID"));
					objReportsDto.setComponentName(rs.getString("COMPONENT_NAME"));
					objReportsDto.setPackageID(rs.getInt("PACKAGE_ID"));
					objReportsDto.setPackageName(rs.getString("PACKAGE_NAME"));					
					objReportsDto.setFx_internal_acc_id(rs.getLong("INTERNAL_ID"));
					objReportsDto.setCreditPeriodName(rs.getString("CREDIT_PERIODNAME"));
					
					ComponentsDto dto = new ComponentsDto();
					dto.setComponentStatus(rs.getString("COMPONENT_STATUS"));
					dto.setStartLogic(rs.getString("COMPONENT_START_LOGIC"));
					dto.setStartDate(rs.getString("COMPONENT_START_DATE"));
					if (rs.getString("COMPONENT_START_DATE") != null && !"".equals(rs.getString("COMPONENT_START_DATE")))
					{
						
						Date date=df.parse(dto.getStartDate());
						dto.setStartDate((Utility.showDate_Report(date)).toUpperCase());
						
					}
					dto.setEndLogic(rs.getString("COMPONENT_END_LOGIC"));
					dto.setEnd_date(rs.getString("COMPONENT_END_DATE"));
					if (rs.getString("COMPONENT_END_DATE") != null && !"".equals(rs.getString("COMPONENT_END_DATE")))
					{
						
						Date date=df.parse(dto.getEnd_date());
						dto.setEnd_date((Utility.showDate_Report(date)).toUpperCase());
						
					}
					dto.setFxTokenNo(rs.getString("START_COMPONENT_TOKEN_NO"));
					dto.setFxStartStatus(rs.getString("SYSTEM_START_STATUS"));
					//dto.setEndFxStatus(rs.getString("FX_END_COMPONENT_STATUS"));
					//dto.setComponentFXStatus(rs.getString("FX_STATUS"));					
					dto.setComponentInstanceID(rs.getString("COMPONENT_INST_ID"));
					dto.setComponentType(rs.getString("COMPONENT_TYPE"));
					dto.setComponentAmount(rs.getDouble("COMP_AMOUNT"));
					
		            objReportsDto.setComponentDto(dto);
				}else{
					objReportsDto.setOrderStage(rs.getString("STAGE"));
					objReportsDto.setFxStatus(rs.getString("CSTATE_START_DETAILS_FX_STATUS"));//FX_STATUS
					objReportsDto.setHardwaretypeName(rs.getString("HARDWARETYPENAME"));	
					objReportsDto.setHardwareFlag(rs.getString("HARDWARE_FLAG"));
					objReportsDto.setStorename(rs.getString("STORENAME"));
					objReportsDto.setSaleType(rs.getString("SALETYPE"));//Type Of Sale		
					objReportsDto.setBillPeriod(rs.getString("BILL_PERIOD"));
					objReportsDto.setChargeTypeName(rs.getString("CHARGE_TYPE"));
					objReportsDto.setChargeName(rs.getString("CHARGE_NAME"));
					objReportsDto.setStartDate(rs.getString("START_DATE"));
					if (rs.getString("START_DATE") != null && !"".equals(rs.getString("START_DATE")))
					{
						
						Date date=df.parse(objReportsDto.getStartDate());
						objReportsDto.setStartDate((Utility.showDate_Report(date)).toUpperCase());
						
					}
					objReportsDto.setChargeAmount_String(rs.getString("INV_AMT"));
					objReportsDto.setLineItemAmount(rs.getString("LINEITEMAMOUNT"));//amt
					objReportsDto.setChargeAnnotation(rs.getString("ANNOTATION"));
					objReportsDto.setTokenNO(rs.getString("CSTATE_START_DETAILS_FX_TOKEN_NO"));//Token_No
					objReportsDto.setFx_St_Chg_Status(rs.getString("CSTATE_FX_CHARGE_START_STATUS"));//Fx_St_Chg_Status
					objReportsDto.setFxStatus(rs.getString("CSTATE_START_DETAILS_FX_STATUS"));//FX_STATUS
				}
			
				
				
				
				if (pagingSorting.isPagingToBeDone()) {
					recordCount = rs.getInt("FULL_REC_COUNT");
				}
				listSearchDetails.add(objReportsDto);
			}
			pagingSorting.setRecordCount(recordCount);
		}
		catch(Exception ex )
		{
			//ex.printStackTrace();	
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}
		finally
		{
			try 
			{
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(proc);
				DbConnection.freeConnection(connection);
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				//e.printStackTrace();
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			}
		}
		return listSearchDetails;
	}
	public ArrayList<StartChargeNotPushedInFXDTO> viewStartChargeNotPushedInFx(StartChargeNotPushedInFXDTO objDto) 
	{
		//Nagarjuna
		String methodName="viewStartChargeNotPushedInFx", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;
		//end Nagarjuna
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rs = null;
		ArrayList<StartChargeNotPushedInFXDTO> listSearchDetails = new ArrayList<StartChargeNotPushedInFXDTO>();
		StartChargeNotPushedInFXDTO objReportsDto = null;
		int recordCount =0;
		Utility utility=new Utility();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date tempDate=null;
		Timestamp ts=null;
		try
		{
			connection=DbConnection.getReportsConnectionObject();
			proc= connection.prepareCall(sqlGetStartChargeNotPushedInFx);
			
			if (objDto.getOrderType() != null
					&& !"".equals(objDto.getOrderType())) {
				proc.setString(1, objDto.getOrderType().trim());
			} else {
				proc.setNull(1, java.sql.Types.VARCHAR);
			}
			
			if (objDto.getFromDate() != null
					&& !"".equals(objDto.getFromDate())) {
				proc.setString(2, objDto.getFromDate().trim());
			} else {
				proc.setNull(2, java.sql.Types.VARCHAR);
			}
			
			if (objDto.getToDate() != null 
					&& !"".equals(objDto.getToDate())) {
				proc.setString(3, objDto.getToDate().trim());
			} else {
				proc.setNull(3, java.sql.Types.VARCHAR);
			}
			
			if (objDto.getFromOrderNo() != 0 
					&& !"".equals(objDto.getFromOrderNo())) {
				proc.setInt(4, objDto.getFromOrderNo());
			} else {
				proc.setNull(4,java.sql.Types.BIGINT);
			}
			
			if (objDto.getToOrderNo() != 0 
					&& !"".equals(objDto.getToOrderNo())) {
				proc.setInt(5, objDto.getToOrderNo());
			} else {
				proc.setNull(5, java.sql.Types.BIGINT);
			}
			
			if (objDto.getVerticalDetails() != null
					&& !"".equals(objDto.getVerticalDetails())) {
				proc.setString(6, objDto.getVerticalDetails().trim());
			} else {
				proc.setNull(6, java.sql.Types.VARCHAR);
			}

			PagingSorting pagingSorting = objDto.getPagingSorting();
			pagingSorting.sync();// To calculate start index and Enc Index

			proc.setString(7, pagingSorting.getSortByColumn());// columnName
			proc.setString(8, PagingSorting.DB_Asc_Desc(pagingSorting
					.getSortByOrder()));// sort order
			proc.setInt(9, pagingSorting.getStartRecordId());// start index
			 proc.setInt(10, pagingSorting.getEndRecordId());// end index
			proc.setInt(11, (pagingSorting.isPagingToBeDone() ? 1 : 0));
			// index
			proc.setInt(12, objDto.getIsUsage());
			rs = proc.executeQuery();
			while(rs.next())
			{
				objReportsDto =  new StartChargeNotPushedInFXDTO();
				objReportsDto.setLogicalSINumber(rs.getInt("LOGICAL_SI_NO"));
				objReportsDto.setCustomer_logicalSINumber(rs.getInt("CUSTOMER_LOGICAL_SI_NO"));
				objReportsDto.setServiceName(rs.getString("SERVICENAME"));
				objReportsDto.setServiceDetDescription(rs.getString("SERVICEDETDESCRIPTION"));
				
				//objReportsDto.setContractStartDate(rs.getString("CONTRACTSTARTDATE"));
				tempDate=rs.getDate("CONTRACTSTARTDATE");
				objReportsDto.setContractStartDate(rs.getString("CONTRACTSTARTDATE"));
				if (tempDate != null && !"".equals(tempDate))
				{
					objReportsDto.setContractStartDate((utility.showDate_Report(tempDate)).toUpperCase());
				}
				//objReportsDto.setContractEndDate(rs.getString("CONTRACTENDDATE"));
				tempDate=rs.getDate("CONTRACTENDDATE");
				objReportsDto.setContractEndDate(rs.getString("CONTRACTENDDATE"));
				if (tempDate != null && !"".equals(tempDate))
				{
					objReportsDto.setContractEndDate((utility.showDate_Report(tempDate)).toUpperCase());
				}
				objReportsDto.setCrmAccountNoString(rs.getString("CRMACCOUNTNO"));
				objReportsDto.setCreditPeriodName(rs.getString("CREDITPERIOD"));
				objReportsDto.setCurrencyName(rs.getString("CURRENCYNAME"));
				objReportsDto.setEntity(rs.getString("ENTITYNAME"));
				objReportsDto.setBillingMode(rs.getString("BILLINGMODE"));
				objReportsDto.setBillingTypeName(rs.getString("BILLING_TYPENAME"));
				objReportsDto.setBillingFormatName(rs.getString("BILLING_FORMATNAME"));
				objReportsDto.setLcompanyname(rs.getString("LCOMPANYNAME"));
				objReportsDto.setTaxation(rs.getString("TAXATIONVALUE"));				
				objReportsDto.setPenaltyClause(rs.getString("PENALTYCLAUSE"));
				objReportsDto.setBillingLevel(rs.getString("BILLINGLEVEL"));
				objReportsDto.setBillingLevelNo(rs.getLong("BILLING_LEVEL_NO"));
				objReportsDto.setBillingLevelName(rs.getString("BILLING_LEVELNAME"));				
				//objReportsDto.setPrimaryLocation(rs.getString("PRIMARYLOCATION"));
				//objReportsDto.setSecondaryLocation(rs.getString("SECONDARYLOCATION"));
				setBlank();
				replaceSeperator("PRIMARYLOCATION",rs.getString("PRIMARYLOCATION"));
				objReportsDto.setPrimaryLocation(VAR_PRIMARYLOCATION);
				replaceSeperator("SECONDARYLOCATION",rs.getString("SECONDARYLOCATION"));
				objReportsDto.setSecondaryLocation(VAR_SECONDARYLOCATION);
				objReportsDto.setPonum(rs.getLong("PONUMBER"));
				/*objReportsDto.setPoDate(rs.getString("PODATE"));
				if (rs.getString("PODATE") != null && !"".equals(rs.getString("PODATE")))
				{
					
					Date date=df.parse(objReportsDto.getPoDate());
					objReportsDto.setPoDate((Utility.showDate_Report(date)).toUpperCase());
					
				}*/
				tempDate=rs.getDate("PODATE");
				objReportsDto.setPoDate(rs.getString("PODATE"));
				if (tempDate != null && !"".equals(tempDate))
				{
					objReportsDto.setPoDate((utility.showDate_Report(tempDate)).toUpperCase());
				}
				objReportsDto.setParty_no(rs.getInt("Party_NO"));
				objReportsDto.setPartyName(rs.getString("PARTYNAME"));								
				objReportsDto.setBilling_Trigger_Flag(rs.getString("Billing_Trigger_Flag"));
				objReportsDto.setLOC_Date(rs.getString("Pm_Prov_Date"));
				if (rs.getString("Pm_Prov_Date") != null && !"".equals(rs.getString("Pm_Prov_Date")))
				{
					  String s1=rs.getString("Pm_Prov_Date");
					  String s3=s1.substring(0,7);
					  String s4=s1.substring(9,11);
					  String s5=s3.concat(s4);
					  objReportsDto.setPmApproveDate(s5);
					
					
				}
				objReportsDto.setLocDate(rs.getString("LOCDATE"));
				if (rs.getString("LOCDATE") != null && !"".equals(rs.getString("LOCDATE")))
				{
					
					Date date=df.parse(objReportsDto.getLocDate());
					objReportsDto.setLocDate((Utility.showDate_Report(date)).toUpperCase());
					
				}
				objReportsDto.setBillingTriggerDate(rs.getString("BILLINGTRIGGERDATE"));
				if (rs.getString("BILLINGTRIGGERDATE") != null && !"".equals(rs.getString("BILLINGTRIGGERDATE")))
				{
					
					Date date=df.parse(objReportsDto.getBillingTriggerDate());
					objReportsDto.setBillingTriggerDate((Utility.showDate_Report(date)).toUpperCase());
					
				}
				objReportsDto.setChallenno(rs.getString("CHALLEN_NO"));
				objReportsDto.setChallendate(rs.getString("CHALLEN_DATE"));
				if (rs.getString("CHALLEN_DATE") != null && !"".equals(rs.getString("CHALLEN_DATE")))
				{
					//Date date=df.parse(objReportsDto.getChallendate());
					objReportsDto.setChallendate(rs.getString("CHALLEN_DATE"));
				}
				objReportsDto.setFx_external_acc_id(rs.getString("Child_Account_Number"));
				objReportsDto.setChildAccountFXSataus(rs.getString("Child_Account_FX_Sataus"));
				
				/*objReportsDto.setOrderDate(rs.getString("ORDERDATE"));  
				if (rs.getString("ORDERDATE") != null && !"".equals(rs.getString("ORDERDATE")))
				{
										
					Date date=df.parse(objReportsDto.getOrderDate());
					objReportsDto.setOrderDate((Utility.showDate_Report(date)).toUpperCase());
					
					
				}*/
				tempDate=rs.getDate("ORDERDATE");
				objReportsDto.setOrderDate(rs.getString("ORDERDATE"));
				if (tempDate != null && !"".equals(tempDate))
				{
					objReportsDto.setOrderDate((utility.showDate_Report(tempDate)).toUpperCase());
				}
				objReportsDto.setOrderApproveDate(rs.getString("ORDER_APPROVED_DATE"));//Copc date
				tempDate=rs.getDate("ORDER_APPROVED_DATE");
				if (tempDate != null && !"".equals(tempDate))
				{					
					objReportsDto.setOrderApproveDate((utility.showDate_Report(tempDate)).toUpperCase());		
					
				}
				objReportsDto.setCopcApproveDate(rs.getString("COPC_APPROVED_DATE"));
				ts=rs.getTimestamp("COPC_APPROVED_DATE");
				if(ts!=null)
				{
					tempDate=new Date(ts.getTime());
					objReportsDto.setCopcApproveDate((utility.showDate_Report(tempDate)).toUpperCase());
					
				}	
				objReportsDto.setOrderType(rs.getString("ORDERTYPE"));
				//   --Order Type Id
				//   --Service Order Type  
				//   ''SERVICE ORDER TYPE DESC'' AS SERVICE_ORDER_TYPE_DESC,
				//     "TST3"."TASK_END_DATE" as "COPC_APPROVED_DATE",      
				//    --TPOSERVICEDETAILS.BILLINGTRIGGERDATE as BILLINGTRIGGER_CREATE_DATE, 
				//    --Cust Logical Si ( Duplicate column)
				
				// --Charge Type Id
				objReportsDto.setServiceStage(rs.getString("SERVICE_STAGE"));
				objReportsDto.setAccountManager(rs.getString("ACCOUNTMANAGER"));
				objReportsDto.setProjectManager(rs.getString("PROJECTMANAGER"));
				// --"TPOMASTER"."ORDERDATE" ORDERCREATION DATE
				/*objReportsDto.setRfsDate(rs.getString("SERVICE_RFS_DATE"));  
				if (rs.getString("SERVICE_RFS_DATE") != null && !"".equals(rs.getString("SERVICE_RFS_DATE")))
				{
					Date date=df.parse(objReportsDto.getRfsDate());
					objReportsDto.setRfsDate((Utility.showDate_Report(date)).toUpperCase());
				}*/
				tempDate=rs.getDate("SERVICE_RFS_DATE");
				objReportsDto.setRfsDate(rs.getString("SERVICE_RFS_DATE"));
				if (tempDate != null && !"".equals(tempDate))
				{
					objReportsDto.setRfsDate((utility.showDate_Report(tempDate)).toUpperCase());
				}
				/*objReportsDto.setPoRecieveDate(rs.getString("PORECEIVEDATE"));   
				if (rs.getString("PORECEIVEDATE") != null && !"".equals(rs.getString("PORECEIVEDATE")))
				{
					
					Date date=df.parse(objReportsDto.getPoRecieveDate());
					objReportsDto.setPoRecieveDate((Utility.showDate_Report(date)).toUpperCase());
					
				}*/
				tempDate=rs.getDate("PORECEIVEDATE");
				objReportsDto.setPoRecieveDate(rs.getString("PORECEIVEDATE"));
				if (tempDate != null && !"".equals(tempDate))
				{
					objReportsDto.setPoRecieveDate((utility.showDate_Report(tempDate)).toUpperCase());
				}
			    //--Fx Status Ed       
				objReportsDto.setCustPoDetailNo(rs.getString("CUSTPONUMBER"));   
				/*objReportsDto.setCustPoDate(rs.getString("CUSTPODATE"));  
				if (rs.getString("CUSTPODATE") != null && !"".equals(rs.getString("CUSTPODATE")))
				{
					
					Date date=df.parse(objReportsDto.getCustPoDate());
					objReportsDto.setCustPoDate((Utility.showDate_Report(date)).toUpperCase());
					
				}*/
				tempDate=rs.getDate("CUSTPODATE");
				objReportsDto.setCustPoDate(rs.getString("CUSTPODATE"));
				if (tempDate != null && !"".equals(tempDate))
				{
					objReportsDto.setCustPoDate((utility.showDate_Report(tempDate)).toUpperCase());
				}
				 
				objReportsDto.setLOC_No(rs.getString("LOCNO"));   
				//objReportsDto.setBillingAddress(rs.getString("BILLING_ADDRESS"));
				replaceSeperator("BILLING_LOCATION",rs.getString("BILLING_ADDRESS"));
				objReportsDto.setBillingAddress(VAR_BILLING_ADDRESS);
				objReportsDto.setFxSiId(rs.getString("FX_SI_ID"));  
				objReportsDto.setCancelBy(rs.getString("CANCEL_BY"));
				objReportsDto.setCanceldate(rs.getString("CANCEL_DATE"));
				objReportsDto.setCancelReason(rs.getString("CANCEL_RESION"));
				objReportsDto.setOpms_Account_Id(rs.getString("Opms_Account_Id"));				
				objReportsDto.setRegionName(rs.getString("REGION"));      
				objReportsDto.setBandwaidth(rs.getString("BANDWIDTH"));     
				objReportsDto.setVerticalDetails(rs.getString("VERTICAL_DETAILS"));      
				objReportsDto.setToLocation(rs.getString("FROM_ADDRESS"));     
				objReportsDto.setFromLocation(rs.getString("TO_ADDRESS"));
				objReportsDto.setColl_Manager(rs.getString("COLL_MANAGER"));
				objReportsDto.setColl_Manager_Mail(rs.getString("COLL_MANAGER_MAIL"));
				objReportsDto.setColl_Manager_Phone(rs.getString("COLL_MANAGER_PHONE"));				
				objReportsDto.setBilling_bandwidth(rs.getString("BILLING_BANDWIDTH"));    
				objReportsDto.setOrder_type(rs.getString("DEMO_TYPE"));
				objReportsDto.setTotalPoAmt(rs.getString("TOTALPOAMOUNT")); 
				//--CRM ORDER ID
				objReportsDto.setOrderNumber(rs.getInt("ORDERNO"));	
				
				//--Charge Hdr Id
				objReportsDto.setOrderLineNumber(rs.getInt("Order_Line_Id"));
				objReportsDto.setServiceId(rs.getInt("SERVICE_NO"));							
				//    --Installment Rate				
				//--Trai Rate
				//--Discount
				objReportsDto.setContractPeriod(rs.getInt("CONTRACTPERIOD"));
				objReportsDto.setCommitmentPeriod(rs.getInt("COMMITMENTPERIOD"));  
				objReportsDto.setNoticePeriod(rs.getInt("NOTICEPERIOD"));   
				//--Principal Amt
				//   --Intrest Rate
				//   --Period In Month
				objReportsDto.setPoAmount(rs.getString("CUST_TOT_PO_AMT"));
				// --party Id
				//   --Cust Account id
				 //  --M6 Product Id
				 //  --M6 Order Id
				 //  --Ib Order Line Id
				  // --Ib Service List Id
				 //  --Ib Pk Charges Id
				 //  --Fx Sno
				 //  --Fx Sno Ed
				  // --Cust Tot Po Amt
				  // --M6 Order No
				 //  --Business Serial No
				 //  --Bus Serial
				  // --Advance
				// Meenakshi : Changes for Usage
				if(objDto.getIsUsage() == 1)
				{
					
					objReportsDto.setComponentInfoID(rs.getInt("COMPONENTINFOID"));
					objReportsDto.setComponentID(rs.getInt("COMPONENT_ID"));
					objReportsDto.setComponentName(rs.getString("COMPONENT_NAME"));
					objReportsDto.setPackageID(rs.getInt("PACKAGE_ID"));
					objReportsDto.setPackageName(rs.getString("PACKAGE_NAME"));
					objReportsDto.setCreditPeriodName(rs.getString("CREDIT_PERIODNAME"));
					objReportsDto.setServiceOrderType(rs.getString("SERVICE_ORDER_TYPE_DESC"));
					objReportsDto.setM6OrderNo(rs.getInt("M6ORDERNO"));
					objReportsDto.setCancelServiceReason(rs.getString("CANCEL_SERVICE_REASON"));
					objReportsDto.setCancelBy(rs.getString("CANCELBY"));	
					objReportsDto.setCanceldate(rs.getString("CANCELDATE"));
					objReportsDto.setOrderStage(rs.getString("ORDERSTAGE"));
					
					ComponentsDto dto = new ComponentsDto();
					dto.setComponentStatus(rs.getString("COMPONENT_STATUS"));
					dto.setStartLogic(rs.getString("COMPONENT_START_LOGIC"));
					dto.setStartDate(rs.getString("SYSTEM_START_DATE"));
					if (rs.getString("SYSTEM_START_DATE") != null && !"".equals(rs.getString("SYSTEM_START_DATE")))
					{
						
						Date date=df.parse(dto.getStartDate());
						dto.setStartDate((Utility.showDate_Report(date)).toUpperCase());
						
					}
					dto.setEndLogic(rs.getString("COMPONENT_END_LOGIC"));
					dto.setEnd_date(rs.getString("SYSTEM_END_DATE"));
					if (rs.getString("SYSTEM_END_DATE") != null && !"".equals(rs.getString("SYSTEM_END_DATE")))
					{
						
						Date date=df.parse(dto.getEnd_date());
						dto.setEnd_date((Utility.showDate_Report(date)).toUpperCase());
						
					}
					dto.setFxTokenNo(rs.getString("START_COMPONENT_TOKEN_NO"));
					dto.setFxStartStatus(rs.getString("SYSTEM_START_STATUS"));
					dto.setEndFxStatus(rs.getString("SYSTEM_END_STATUS"));
					dto.setEndTokenNo(rs.getString("END_COMPONENT_TOKEN_NO"));
					//dto.setComponentFXStatus(rs.getString("FX_STATUS"));
					objReportsDto.setStartDateDays(rs.getInt("COMP_START_DAYS"));
					objReportsDto.setStartDateMonth(rs.getInt("COMP_START_MONTHS"));
					objReportsDto.setEndDateDays(rs.getInt("COMP_END_DAYS"));
					objReportsDto.setEndDateMonth(rs.getInt("COMP_END_MONTHS"));
					objReportsDto.setSourcePartyID(rs.getLong("PARTY_ID"));
					objReportsDto.setAccountID(rs.getInt("CUSTACCOUNTID"));
					objReportsDto.setFx_internal_acc_id(rs.getLong("INTERNAL_ID"));
					dto.setComponentInstanceID(rs.getString("COMPONENT_INST_ID"));
					dto.setComponentType(rs.getString("COMPONENT_TYPE"));
					dto.setComponentAmount(rs.getDouble("COMP_AMOUNT"));
					
					
					///dto.setEndTokenNo(rs.getString("")); //END_COMPONENT_TOKEN_NO  
					objReportsDto.setComponentDto(dto);
				}else{
					objReportsDto.setOrderStage(rs.getString("STAGE"));
					objReportsDto.setTokenNoEd(rs.getString("CSTATE_END_DETAILS_FX_TOKEN_NO"));//--Token No Ed
					objReportsDto.setHardwareFlag(rs.getString("HARDWARE_FLAG"));
					objReportsDto.setWarrantyStartDate(rs.getString("WARRENTY_START_DATE"));
					if (rs.getString("WARRENTY_START_DATE") != null && !"".equals(rs.getString("WARRENTY_START_DATE")))
					{
						
						Date date=df.parse(objReportsDto.getWarrantyStartDate());
						objReportsDto.setWarrantyStartDate((Utility.showDate_Report(date)).toUpperCase());
						
					}
					objReportsDto.setWarrantyEndDate(rs.getString("WARRENTY_END_DATE"));
					if (rs.getString("WARRENTY_END_DATE") != null && !"".equals(rs.getString("WARRENTY_END_DATE")))
					{
						
						Date date=df.parse(objReportsDto.getWarrantyEndDate());
						objReportsDto.setWarrantyEndDate((Utility.showDate_Report(date)).toUpperCase());
						
					}
					objReportsDto.setExtSuportEndDate(rs.getString("EXT_SUPPORT_END_DATE"));   
					if (rs.getString("EXT_SUPPORT_END_DATE") != null && !"".equals(rs.getString("EXT_SUPPORT_END_DATE")))
					{
						
						Date date=df.parse(objReportsDto.getExtSuportEndDate());
						objReportsDto.setExtSuportEndDate((Utility.showDate_Report(date)).toUpperCase());
						
					}
					objReportsDto.setFxStatus(rs.getString("CSTATE_START_DETAILS_FX_STATUS"));//FX_STATUS				
					objReportsDto.setTokenNO(rs.getString("CSTATE_START_DETAILS_FX_TOKEN_NO"));//Token_No
					objReportsDto.setHardwaretypeName(rs.getString("HARDWARETYPENAME"));	
					objReportsDto.setFormAvailable(rs.getString("FORM_C_AVAILABLE"));
					objReportsDto.setSaleNature(rs.getString("SALENATURE"));
					objReportsDto.setSaleType(rs.getString("SALETYPE"));
					objReportsDto.setDispatchAddressName(rs.getString("DISPATCHADDNAME"));
					objReportsDto.setChargeTypeName(rs.getString("CHARGE_TYPE"));
					objReportsDto.setFrequencyName(rs.getString("FREQUENCY"));
					objReportsDto.setChargeName(rs.getString("CHARGE_NAME"));
					objReportsDto.setBillPeriod(rs.getString("BILL_PERIOD"));
					objReportsDto.setDnd_Dispatch_But_Not_Delivered(rs.getString("Dnd_Dispatch_But_Not_Delivered"));
					objReportsDto.setDnd_Dispatch_And_Delivered(rs.getString("Dnd_Dispatch_And_Delivered"));
					objReportsDto.setDnd_Disp_Del_Not_Installed(rs.getString("Ddni_Disp_Del_Not_Installed"));
					objReportsDto.setDnd_Disp_Delivered_Installed(rs.getString("Ddni_Disp_Delivered_Installed"));				
					objReportsDto.setStartDateLogic(rs.getString("STARTDATELOGIC"));
					objReportsDto.setEndDateLogic(rs.getString("ENDDATELOGIC"));
					objReportsDto.setStartDate(rs.getString("START_DATE"));
					if (rs.getString("START_DATE") != null && !"".equals(rs.getString("START_DATE")))
					{
						
						Date date=df.parse(objReportsDto.getStartDate());
						objReportsDto.setStartDate((Utility.showDate_Report(date)).toUpperCase());
						
					}
					objReportsDto.setEndDate(rs.getString("END_DATE"));
					if (rs.getString("END_DATE") != null && !"".equals(rs.getString("END_DATE")))
					{
						
						Date date=df.parse(objReportsDto.getEndDate());
						objReportsDto.setEndDate((Utility.showDate_Report(date)).toUpperCase());
						
					}
					objReportsDto.setChargeAnnotation(rs.getString("ANNOTATION"));
					objReportsDto.setFx_St_Chg_Status(rs.getString("CSTATE_FX_CHARGE_START_STATUS"));//Fx_St_Chg_Status
					objReportsDto.setFx_Ed_Chg_Status(rs.getString("CSTATE_FX_CHARGE_END_STATUS"));//Fx_Ed_Chg_Status
					objReportsDto.setCharge_status(rs.getString("CHARGES_STATUS"));  
					objReportsDto.setChargeAmount_String(rs.getString("INV_AMT"));
					objReportsDto.setLineItemAmount(rs.getString("LINEITEMAMOUNT"));					
					objReportsDto.setStartDateDays(rs.getInt("START_DATE_DAYS"));
					objReportsDto.setStartDateMonth(rs.getInt("START_DATE_MONTH"));
					objReportsDto.setEndDateDays(rs.getInt("END_DATE_DAYS"));
					objReportsDto.setEndDateMonth(rs.getInt("END_DATE_MONTH"));
					objReportsDto.setAnnual_rate(rs.getDouble("ANNUAL_RATE"));
				}
				
				/// End

				if (pagingSorting.isPagingToBeDone()) {
					recordCount = rs.getInt("FULL_REC_COUNT");
				}
				listSearchDetails.add(objReportsDto);
			}
			pagingSorting.setRecordCount(recordCount);
		}
		catch(Exception ex )
		{
			//ex.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}
		finally
		{
			try 
			{
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(proc);
				DbConnection.freeConnection(connection);
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				//e.printStackTrace();
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			}
		}
		return listSearchDetails;
	}
	
	public ArrayList<LogicalSIDataReportDTO> viewLogicalSIDataReport(LogicalSIDataReportDTO objDto) 
	{
		//Nagarjuna
		String methodName="viewLogicalSIDataReport", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;
		//end Nagarjuna
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rs = null;
		ArrayList<LogicalSIDataReportDTO> listSearchDetails = new ArrayList<LogicalSIDataReportDTO>();
		LogicalSIDataReportDTO objReportsDto = null;
		int recordCount =0;
		Utility utility=new Utility();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date tempDate=null;
		Timestamp ts=null;
		try
		{
			connection=DbConnection.getReportsConnectionObject();
			proc= connection.prepareCall(sqlGetLogicalSIDataReport);
	
			if (objDto.getOrderType() != null
					&& !"".equals(objDto.getOrderType())) {
				proc.setString(1, objDto.getOrderType().trim());
			} else {
				proc.setNull(1, java.sql.Types.VARCHAR);
			}
			if (objDto.getFromDate() != null
					&& !"".equals(objDto.getFromDate())) {
				proc.setString(2, objDto.getFromDate().trim());
			} else {
				proc.setNull(2, java.sql.Types.VARCHAR);
			}
			if (objDto.getToDate() != null 
					&& !"".equals(objDto.getToDate())) {
				proc.setString(3, objDto.getToDate().trim());
			} else {
				proc.setNull(3, java.sql.Types.VARCHAR);
			}
			if (objDto.getFromOrderNo() != 0 
					&& !"".equals(objDto.getFromOrderNo())) {
				proc.setInt(4, objDto.getFromOrderNo());
			} else {
				proc.setNull(4,java.sql.Types.BIGINT);
			}
			if (objDto.getToOrderNo() != 0 
					&& !"".equals(objDto.getToOrderNo())) {
				proc.setInt(5, objDto.getToOrderNo());
			} else {
				proc.setNull(5, java.sql.Types.BIGINT);
			}
			if (objDto.getLogicalSINumber() != 0 
					&& !"".equals(objDto.getLogicalSINumber())) {
				proc.setInt(6, objDto.getLogicalSINumber());
			} else {
				proc.setNull(6,java.sql.Types.BIGINT);
			}
			PagingSorting pagingSorting = objDto.getPagingSorting();
			pagingSorting.sync();// To calculate start index and Enc Index
			proc.setString(7, pagingSorting.getSortByColumn());// columnName
			proc.setString(8, PagingSorting.DB_Asc_Desc(pagingSorting
					.getSortByOrder()));// sort order
			proc.setInt(9, pagingSorting.getStartRecordId());// start index
			 proc.setInt(10, pagingSorting.getEndRecordId());// end index
			proc.setInt(11, (pagingSorting.isPagingToBeDone() ? 1 : 0));
			proc.setInt(12,objDto.getIsUsage());
			// index
			rs = proc.executeQuery();
			while(rs.next())
			{
				setBlank();
				replaceSeperator("BILLING_LOCATION",rs.getString("BILLING_ADDRESS"));
				objReportsDto =  new LogicalSIDataReportDTO();
				objReportsDto.setOrderNumber(rs.getInt("ORDERNO"));
				
				objReportsDto.setLogicalSINumber(rs.getInt("LOGICAL_SI_NO"));				
				objReportsDto.setServiceName(rs.getString("SERVICENAME"));
				objReportsDto.setParent_name(rs.getString("PARENT_NAME"));//PARENT LINE NAME
				objReportsDto.setServiceDetDescription(rs.getString("SERVICEDETDESCRIPTION"));//Line Name
				
				objReportsDto.setContractPeriod(rs.getInt("CONTRACTPERIOD"));
				objReportsDto.setTotalPoAmt(rs.getString("TOTALPOAMOUNT")); 
				
				objReportsDto.setBillingTriggerDate(rs.getString("BILLINGTRIGGERDATE"));
				if (rs.getString("BILLINGTRIGGERDATE") != null && !"".equals(rs.getString("BILLINGTRIGGERDATE")))
				{
					
					Date date=df.parse(objReportsDto.getBillingTriggerDate());
					objReportsDto.setBillingTriggerDate((utility.showDate_Report(date)).toUpperCase());
					
				}
				
				objReportsDto.setCustPoDetailNo(rs.getString("CUSTPONUMBER")); 
				objReportsDto.setPoAmount(rs.getString("CUST_TOT_PO_AMT"));
				objReportsDto.setEntity(rs.getString("ENTITYNAME"));
				// change
				tempDate=rs.getDate("CUSTPODATE"); 
				if(tempDate!=null)
				{
					objReportsDto.setCustPoDate((Utility.showDate_Report(tempDate)).toUpperCase());
				}
				objReportsDto.setContractPeriod(rs.getInt("CONTRACTPERIOD"));//PO Contract Period
				tempDate=rs.getDate("CONTRACTSTARTDATE");
				if(tempDate!=null)
				{
					objReportsDto.setContractStartDate((Utility.showDate_Report(tempDate)).toUpperCase());
				}
				tempDate=rs.getDate("CONTRACTENDDATE");
				if(tempDate!=null)
				{
					objReportsDto.setContractEndDate((Utility.showDate_Report(tempDate)).toUpperCase());
				}
				tempDate=rs.getDate("PORECEIVEDATE");
				if(tempDate!=null)
				{
					objReportsDto.setPoRecieveDate((Utility.showDate_Report(tempDate)).toUpperCase());
				}
				
				objReportsDto.setOrderLineNumber(rs.getInt("Order_Line_Id"));
				objReportsDto.setServiceId(rs.getInt("SERVICE_NO"));
					
				
				
				objReportsDto.setBillingTypeName(rs.getString("BILLING_TYPENAME"));
				objReportsDto.setBillingFormatName(rs.getString("BILLING_FORMATNAME"));
				objReportsDto.setTaxation(rs.getString("TAXATIONVALUE"));
				objReportsDto.setLcompanyname(rs.getString("LCOMPANYNAME"));
				
				
				objReportsDto.setContactName(VAR_BILL_CON_PER_NAME);//Contact Person Name	
				objReportsDto.setDesignation(VAR_DESIGNATION);//Person Designation	
				objReportsDto.setTelePhoneNo(VAR_TELEPHONENO);//Person Mobile	
				objReportsDto.setEmailId(VAR_EMAIL_ID);//Person Email	
				objReportsDto.setFax(VAR_FAX);//Person Fax	
				
				//Remrks	
				objReportsDto.setBillingLevel(rs.getString("BILLING_LEVELNAME"));
				objReportsDto.setCommitmentPeriod(rs.getInt("COMMITMENTPERIOD"));  
				objReportsDto.setNoticePeriod(rs.getInt("NOTICEPERIOD")); 
				objReportsDto.setLogicalCircuitId(rs.getString("CKTID"));
				
				if(objDto.getIsUsage() == 0)
				{
					objReportsDto.setRecordStatus(rs.getString("recordStatus"));
					
					objReportsDto.setCreditPeriodName(rs.getString("CREDITPERIOD"));
					
					objReportsDto.setCountyName(rs.getString("COUNTRY_NAME"));
					objReportsDto.setAddress1(rs.getString("BILL_ADDRESS1"));//billing Address1
					objReportsDto.setAddress2(rs.getString("BILL_ADDRESS2"));//billing Address2	
					objReportsDto.setAddress3(rs.getString("BILL_ADDRESS3"));//billing Address3	
					objReportsDto.setAddress4(rs.getString("BILL_ADDRESS4"));//billing Address4
					objReportsDto.setCityName(rs.getString("CITY_NAME"));//need to add in view
					objReportsDto.setPostalCode(rs.getString("POSTAL_CODE"));//need to add in view
					objReportsDto.setStateName(rs.getString("STATE_NAME"));//need to add in view
					
					objReportsDto.setChargeTypeName(rs.getString("CHARGE_TYPE"));
					objReportsDto.setChargeName(rs.getString("CHARGE_NAME"));
					objReportsDto.setCharge_status(rs.getString("CHARGES_STATUS"));
					
					objReportsDto.setFrequencyName(rs.getString("FREQUENCY"));
					objReportsDto.setFrequencyAmt(rs.getString("FREQUENCY_AMT"));
					objReportsDto.setStartDateLogic(rs.getString("STARTDATELOGIC"));
					objReportsDto.setStartDateDays(rs.getInt("START_DATE_DAYS"));
					objReportsDto.setStartDateMonth(rs.getInt("START_DATE_MONTH"));
					
					objReportsDto.setEndDateLogic(rs.getString("ENDDATELOGIC"));
					objReportsDto.setEndDateMonth(rs.getInt("END_DATE_MONTH"));
					objReportsDto.setEndDateDays(rs.getInt("END_DATE_DAYS"));
					objReportsDto.setChargeEndDate(rs.getString("END_DATE"));//Charge End Date
					if (rs.getString("END_DATE") != null && !"".equals(rs.getString("END_DATE")))
					{
						
						Date date=df.parse(objReportsDto.getChargeEndDate());
						objReportsDto.setChargeEndDate((utility.showDate_Report(date)).toUpperCase());
						
					}
					objReportsDto.setAnnual_rate(rs.getDouble("ANNUAL_RATE"));
					//--Trai Rate
					//--Discount
					//Advance
					//Installment Rate
					objReportsDto.setDnd_Dispatch_But_Not_Delivered(rs.getString("Dnd_Dispatch_But_Not_Delivered"));
					objReportsDto.setDnd_Dispatch_And_Delivered(rs.getString("Dnd_Dispatch_And_Delivered"));
					objReportsDto.setDnd_Disp_Del_Not_Installed(rs.getString("Ddni_Disp_Del_Not_Installed"));
					objReportsDto.setDnd_Disp_Delivered_Installed(rs.getString("Ddni_Disp_Delivered_Installed"));
					objReportsDto.setPoExclude(rs.getString("PO_EXCLUDE"));//Po Valid Exclude
					
					objReportsDto.setChargeinfoID(rs.getString("CHARGEINFOID"));//need to add in view
					
					//M6 Order Id	
					
					//remarks
					
					//Pk Charges Id	
					//M6 Product Id	
					//Parent Product Id	
					objReportsDto.setBillingInfoID(rs.getInt("CHARGE_HDR_ID"));//Charge Hdr Id	
					//Ib Pk Charges Id	
					//Ib Order Line Id
					
					//Order Line Si No	
					objReportsDto.setChargeAnnotation(rs.getString("ANNOTATION"));
					
//					Active End Date
					
					objReportsDto.setLst_No(rs.getString("LST_NO"));//Lst No	
					objReportsDto.setLstDate(rs.getString("LST_DATE"));//Lst Date	
					if (rs.getString("LST_DATE") != null && !"".equals(rs.getString("LST_DATE")))
					{
						objReportsDto.setLstDate((utility.showDate_Report(new Date(rs.getTimestamp("LST_DATE").getTime()))).toUpperCase());
									
					
						
					}
					//Billing Address Type
					//objReportsDto.setAttributeLabel(rs.getString("Attribute_Name"));
					//objReportsDto.setAttributeValue(rs.getString("Attribute_Value"));
					objReportsDto.setStoreName(rs.getString("STORENAME"));
					objReportsDto.setHardwaretypeName(rs.getString("HARDWARETYPENAME"));
					objReportsDto.setSaleNature(rs.getString("SALENATURE"));
					objReportsDto.setFormAvailable(rs.getString("FORM_C_AVAILABLE"));
					objReportsDto.setSaleType(rs.getString("SALETYPE"));
					//Principle Amt	
					//Interest Rate	
					objReportsDto.setWarrantyStartDateLogic(rs.getString("WARRENTY_START_DATE_LOGIC"));
					objReportsDto.setWarrantyPeriodMonths(rs.getString("WARRENTY_PERIOD_MONTHS"));
					objReportsDto.setWarrantyPeriodDays(rs.getString("WARRENTY_PERIOD_DAYS"));
					objReportsDto.setWarrantyStartDate(rs.getString("WARRENTY_START_DATE"));
					if (rs.getString("WARRENTY_START_DATE") != null && !"".equals(rs.getString("WARRENTY_START_DATE")))
					{
						
						Date date=df.parse(objReportsDto.getWarrantyStartDate());
						objReportsDto.setWarrantyStartDate((utility.showDate_Report(date)).toUpperCase());
						
					}
					objReportsDto.setWarrantyEndDateLogic(rs.getString("WARRENTY_END_DATE_LOGIC"));
					objReportsDto.setWarrantyEndPeriodMonths(rs.getString("WARRENTY_END_PERIOD_MONTHS"));//need
					objReportsDto.setWarrantyEndPeriodDays(rs.getString("WARRENTY_END_PERIOD_DAYS"));//need
					objReportsDto.setWarrantyEndDate(rs.getString("WARRENTY_END_DATE"));//need
					if (rs.getString("WARRENTY_END_DATE") != null && !"".equals(rs.getString("WARRENTY_END_DATE")))
					{
						
						Date date=df.parse(objReportsDto.getWarrantyEndDate());
						objReportsDto.setWarrantyEndDate((utility.showDate_Report(date)).toUpperCase());
						
					}
					objReportsDto.setExtndSupportPeriodMonths(rs.getString("EXT_SUPPORT_PERIOD_MONTHS"));
					objReportsDto.setExtndSupportPeriodDays(rs.getString("EXT_SUPPORT_PERIOD_DAYS"));
					objReportsDto.setExtSuportEndDate(rs.getString("EXT_SUPPORT_END_DATE")); 
					if (rs.getString("EXT_SUPPORT_END_DATE") != null && !"".equals(rs.getString("EXT_SUPPORT_END_DATE")))
					{
						
						Date date=df.parse(objReportsDto.getExtSuportEndDate());
						objReportsDto.setExtSuportEndDate((utility.showDate_Report(date)).toUpperCase());
						
					}
					objReportsDto.setDispatchAddress1(rs.getString("DISP_ADDRESS1"));//Dispatch Address1	
					objReportsDto.setDispatchAddress2(rs.getString("DISP_ADDRESS2"));//Dispatch Address2	
					objReportsDto.setDispatchAddress3(rs.getString("DISP_ADDRESS3"));//Dispatch Address3	
					objReportsDto.setDispatchCityName(rs.getString("DISP_CITY_NAME"));//Dispatch City	
					objReportsDto.setDispatchPostalCode(rs.getString("DISP_POSTAL_CODE"));//Dispatch Postal Code	
					objReportsDto.setDispatchStateName(rs.getString("DISP_STATE_NAME"));//Dispatch State	
					objReportsDto.setDispatchPersonName(rs.getString("DISP_Con_Person_Name"));//Dispatch Conact Person Name	
					objReportsDto.setDispatchPhoneNo(rs.getString("DISP_TELEPHONENO"));//Dispatch Contact Person Mobile	
					objReportsDto.setDispatchLstNumber(rs.getString("DISP_LST_NO"));//Dispatch Lst Number	
					objReportsDto.setDispatchLstDate(rs.getString("DISP_LST_DATE"));//Dispatch Lst Date	
					if (rs.getString("DISP_LST_DATE") != null && !"".equals(rs.getString("DISP_LST_DATE")))
					{
						
						objReportsDto.setDispatchLstDate((utility.showDate_Report(new Date(rs.getTimestamp("DISP_LST_DATE").getTime()))).toUpperCase());
						
					}
					//Dispatch Address Type	
					//New Service List Id	
					//New Crm Order Id	
					
					
					
					
					
				}
				
				
				if(objDto.getIsUsage() == 1)
				{
					
					objReportsDto.setDisconnection_remarks(rs.getString("DISCONNECTION_REMARKS"));
					objReportsDto.setNeworder_remarks(rs.getString("NEWORDER_REMARKS"));
					objReportsDto.setM6OrderNo(rs.getInt("M6ORDERNO"));
					
					objReportsDto.setCreditPeriodName(rs.getString("CREDIT_PERIODNAME"));
					objReportsDto.setCountyName(VAR_BCP_COUNTRY_NAME);
					objReportsDto.setAddress1(VAR_BILL_ADDRESS1);//billing Address1
					objReportsDto.setAddress2(VAR_BILL_ADDRESS2);//billing Address2	
					objReportsDto.setAddress3(VAR_BILL_ADDRESS3);//billing Address3	
					objReportsDto.setAddress4(VAR_BILL_ADDRESS4);//billing Address4
					objReportsDto.setCityName(VAR_BCP_CITY_NAME);//need to add in view
					objReportsDto.setPostalCode(VAR_BCP_PIN);//need to add in view
					objReportsDto.setStateName(VAR_BCP_STATE_NAME);//need to add in view
					
					
					objReportsDto.setFx_external_acc_id(rs.getString("Child_Account_Number"));
					objReportsDto.setFxInternalId(rs.getInt("INTERNAL_ID"));
					objReportsDto.setChild_account_creation_status(rs.getString("Child_Account_FX_Sataus"));
					objReportsDto.setPackageID(rs.getInt("PACKAGE_ID"));
					objReportsDto.setPackageName(rs.getString("PACKAGE_NAME"));
					objReportsDto.setComponentID(rs.getInt("COMPONENT_ID"));
					objReportsDto.setComponentName(rs.getString("COMPONENT_NAME"));
					objReportsDto.setComponentInfoID(rs.getInt("COMPONENTINFOID"));
					
					ComponentsDto dto = new ComponentsDto();
					dto.setComponentType(rs.getString("COMPONENT_TYPE"));
					dto.setComponentAmount(rs.getDouble("COMP_AMOUNT"));
					dto.setComponentStatus(rs.getString("COMPONENT_STATUS"));
					dto.setStartLogic(rs.getString("COMPONENT_START_LOGIC"));
					dto.setStartDateDays(rs.getInt("COMP_START_DAYS"));
					dto.setStartDateMonth(rs.getInt("COMP_START_MONTHS"));
					dto.setEndLogic(rs.getString("COMPONENT_END_LOGIC"));
					dto.setEndDateDays(rs.getInt("COMP_END_DAYS"));
					dto.setEndDateMonth(rs.getInt("COMP_END_MONTHS"));
					
					dto.setStartDate(rs.getString("SYSTEM_START_DATE"));
					if (rs.getString("SYSTEM_START_DATE") != null && !"".equals(rs.getString("SYSTEM_START_DATE")))
					{
						
						Date date=df.parse(dto.getStartDate());
						dto.setStartDate((utility.showDate_Report(date)).toUpperCase());
						
					}
					
					dto.setEnd_date(rs.getString("SYSTEM_END_DATE"));
					if (rs.getString("SYSTEM_END_DATE") != null && !"".equals(rs.getString("SYSTEM_END_DATE")))
					{
						
						Date date=df.parse(dto.getEnd_date());
						dto.setEnd_date((utility.showDate_Report(date)).toUpperCase());
						
					}
					objReportsDto.setComponentDto(dto);
			   }
				
				
				
				if (pagingSorting.isPagingToBeDone()) {
					recordCount = rs.getInt("FULL_REC_COUNT");
				}
				listSearchDetails.add(objReportsDto);
			}
			pagingSorting.setRecordCount(recordCount);
		}
		catch(Exception ex )
		{
			//ex.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}
		finally
		{
			try 
			{
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(proc);
				DbConnection.freeConnection(connection);
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				//e.printStackTrace();
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			}
		}
		return listSearchDetails;
	}
	
	public ArrayList<BillingTriggerDoneButFailedInFXDTO> viewBillingTriggerDoneButFailedInFX(BillingTriggerDoneButFailedInFXDTO objDto) 
	{
		//Nagarjuna
		String methodName="viewBillingTriggerDoneButFailedInFX", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;
		//end Nagarjuna
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rs = null;
		ArrayList<BillingTriggerDoneButFailedInFXDTO> listSearchDetails = new ArrayList<BillingTriggerDoneButFailedInFXDTO>();
		BillingTriggerDoneButFailedInFXDTO objReportsDto = null;
		int recordCount =0;
		Utility utility=new Utility();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date tempDate=null;
		Timestamp ts=null;
		try{
			connection=DbConnection.getReportsConnectionObject();
		    proc = connection.prepareCall(sqlGetBillingTriggerDoneButFailedInFX);
		
		
			if (objDto.getOrderType() != null
					&& !"".equals(objDto.getOrderType())) {
				proc.setString(1, objDto.getOrderType().trim());
			} else {
				proc.setNull(1, java.sql.Types.VARCHAR);
			}
			
			if (objDto.getFromDate() != null
					&& !"".equals(objDto.getFromDate())) {
				proc.setString(2, objDto.getFromDate().trim());
			} else {
				proc.setNull(2, java.sql.Types.VARCHAR);
			}
			
			if (objDto.getToDate() != null 
					&& !"".equals(objDto.getToDate())) {
				proc.setString(3, objDto.getToDate().trim());
			} else {
				proc.setNull(3, java.sql.Types.VARCHAR);
			}
			
			if (objDto.getFromOrderNo() != 0 
					&& !"".equals(objDto.getFromOrderNo())) {
				proc.setInt(4, objDto.getFromOrderNo());
			} else {
				proc.setNull(4,java.sql.Types.BIGINT);
			}
			
			if (objDto.getToOrderNo() != 0 
					&& !"".equals(objDto.getToOrderNo())) {
				proc.setInt(5, objDto.getToOrderNo());
			} else {
				proc.setNull(5, java.sql.Types.BIGINT);
			}
			
			if (objDto.getFromAccountNo() != 0 
					&& !"".equals(objDto.getFromAccountNo())) {
				proc.setInt(6, objDto.getFromAccountNo());
			} else {
				proc.setNull(6,java.sql.Types.BIGINT);
			}
			
			if (objDto.getToAccountNo() != 0 
					&& !"".equals(objDto.getToAccountNo())) {
				proc.setInt(7, objDto.getToAccountNo());
			} else {
				proc.setNull(7, java.sql.Types.BIGINT);
			}

			PagingSorting pagingSorting = objDto.getPagingSorting();
			pagingSorting.sync();// To calculate start index and Enc Index

			proc.setString(8, pagingSorting.getSortByColumn());// columnName
			proc.setString(9, PagingSorting.DB_Asc_Desc(pagingSorting
					.getSortByOrder()));// sort order
			 proc.setInt(10, pagingSorting.getStartRecordId());// start index
			 proc.setInt(11, pagingSorting.getEndRecordId());// end index
			 proc.setInt(12, (pagingSorting.isPagingToBeDone() ? 1 : 0));
			 proc.setInt(13,objDto.getIsUsage());
			
			// index
			rs = proc.executeQuery();
			while(rs.next())
			{
				objReportsDto =  new BillingTriggerDoneButFailedInFXDTO();
				//[270513]Start : Added by Ashutosh for Billing Address
				setBlank();
				replaceSeperator("BILLING_LOCATION",rs.getString("BILLING_ADDRESS"));
				replaceSeperator("PRIMARYLOCATION",rs.getString("PRIMARYLOCATION"));
				replaceSeperator("SECONDARYLOCATION",rs.getString("SECONDARYLOCATION"));		
				//[270513]Start
				objReportsDto.setLogicalSINumber(rs.getInt("LOGICAL_SI_NO"));
				objReportsDto.setCustomer_logicalSINumber(rs.getInt("CUSTOMER_LOGICAL_SI_NO"));
				objReportsDto.setServiceName(rs.getString("SERVICENAME"));
				objReportsDto.setServiceDetDescription(rs.getString("SERVICEDETDESCRIPTION"));
				
				//orderStageDescription
				tempDate=rs.getDate("CONTRACTSTARTDATE");
				if(tempDate!=null)
				{
					
					objReportsDto.setContractStartDate((Utility.showDate_Report(tempDate)).toUpperCase());					
					
				}
				tempDate=rs.getDate("CONTRACTENDDATE");
				if(tempDate!=null)
				{
					
					objReportsDto.setContractEndDate(Utility.showDate_Report(tempDate).toUpperCase());
					
				}
				objReportsDto.setCrmAccountNoString(rs.getString("CRMACCOUNTNO"));
				objReportsDto.setCreditPeriodName(rs.getString("CREDITPERIOD"));
				objReportsDto.setCurrencyName(rs.getString("CURRENCYNAME"));
				objReportsDto.setEntity(rs.getString("ENTITYNAME"));
				objReportsDto.setBillingMode(rs.getString("BILLINGMODE"));
				objReportsDto.setBillingTypeName(rs.getString("BILLING_TYPENAME"));
				objReportsDto.setBillingFormatName(rs.getString("BILLING_FORMATNAME"));
				objReportsDto.setLcompanyname(rs.getString("LCOMPANYNAME"));
				objReportsDto.setTaxation(rs.getString("TAXATIONVALUE"));
				objReportsDto.setPenaltyClause(rs.getString("PENALTYCLAUSE"));
				objReportsDto.setBillingLevel(rs.getString("BILLINGLEVEL"));
				objReportsDto.setBillingLevelNo(rs.getLong("BILLING_LEVEL_NO"));
				objReportsDto.setBillingLevelName(rs.getString("BILLING_LEVELNAME"));				
				objReportsDto.setPrimaryLocation(VAR_PRIMARYLOCATION);
				objReportsDto.setSecondaryLocation(VAR_SECONDARYLOCATION);
				objReportsDto.setPonum(rs.getLong("PONUMBER"));
				tempDate=rs.getDate("PODATE");
				if(tempDate!=null)
				{
					
					objReportsDto.setPoDate((Utility.showDate_Report(tempDate)).toUpperCase());
					
				}
				objReportsDto.setParty_no(rs.getInt("Party_NO"));
				objReportsDto.setPartyName(rs.getString("PARTYNAME"));								
				objReportsDto.setBilling_Trigger_Flag(rs.getString("Billing_Trigger_Flag"));
				objReportsDto.setLOC_Date(rs.getString("Pm_Prov_Date"));
				if (rs.getString("Pm_Prov_Date") != null && !"".equals(rs.getString("Pm_Prov_Date")))
				{
				  String s1=rs.getString("Pm_Prov_Date");
				  String s3=s1.substring(0,7);
				  String s4=s1.substring(9,11);
				  String s5=s3.concat(s4);
				  objReportsDto.setLOC_Date(s5);
				}
				objReportsDto.setLocDate(rs.getString("LOCDATE"));
				if (rs.getString("LOCDATE") != null && !"".equals(rs.getString("LOCDATE")))
				{
					
					Date date=df.parse(objReportsDto.getLocDate());
					objReportsDto.setLocDate((Utility.showDate_Report(date)).toUpperCase());
					
				}
				objReportsDto.setBillingTriggerDate(rs.getString("BILLINGTRIGGERDATE"));
				if (rs.getString("BILLINGTRIGGERDATE") != null && !"".equals(rs.getString("BILLINGTRIGGERDATE")))
				{
					
					Date date=df.parse(objReportsDto.getBillingTriggerDate());
					objReportsDto.setBillingTriggerDate((Utility.showDate_Report(date)).toUpperCase());
					
				}				
				tempDate=rs.getDate("ORDERDATE");
				if(tempDate!=null)
				{
					objReportsDto.setOrderDate((Utility.showDate_Report(tempDate)).toUpperCase());
				}
				tempDate=rs.getDate("ORDER_APPROVED_DATE");
				if(tempDate!=null)
				{
					objReportsDto.setOrderApproveDate(Utility.showDate_Report(tempDate).toUpperCase());
				}
				ts=rs.getTimestamp("COPC_APPROVED_DATE");//Copc date
				if(ts!=null)
				{
					tempDate=new Date(ts.getTime());
					objReportsDto.setCopcApproveDate(Utility.showDate_Report(tempDate).toUpperCase());
				}
				objReportsDto.setOrderType(rs.getString("ORDERTYPE"));
				
				//   --Order Type Id
				//   --Service Order Type
				//   ''SERVICE ORDER TYPE DESC'' AS SERVICE_ORDER_TYPE_DESC,
				//     "TST3"."TASK_END_DATE" as "COPC_APPROVED_DATE",      
				//    --TPOSERVICEDETAILS.BILLINGTRIGGERDATE as BILLINGTRIGGER_CREATE_DATE, 
				//    --Cust Logical Si ( Duplicate column)				
				// --Charge Type Id
				objReportsDto.setServiceStage(rs.getString("SERVICE_STAGE"));
				objReportsDto.setAccountManager(rs.getString("ACCOUNTMANAGER"));
				objReportsDto.setProjectManager(rs.getString("PROJECTMANAGER"));
				// --"TPOMASTER"."ORDERDATE" ORDERCREATION DATE
				tempDate=rs.getDate("SERVICE_RFS_DATE");  
				if(tempDate!=null)
				{
					objReportsDto.setRfsDate((Utility.showDate_Report(tempDate)).toUpperCase());
				}
				tempDate=rs.getDate("PORECEIVEDATE");
				if (rs.getString("PORECEIVEDATE") != null && !"".equals(rs.getString("PORECEIVEDATE")))
				{
					objReportsDto.setPoRecieveDate((Utility.showDate_Report(tempDate)).toUpperCase());
				}
				
			    //--Fx Status Ed       
				objReportsDto.setCustPoDetailNo(rs.getString("CUSTPONUMBER"));   
				tempDate=rs.getDate("CUSTPODATE");  
				if(tempDate!=null)
				{
					objReportsDto.setCustPoDate((Utility.showDate_Report(tempDate)).toUpperCase());
				}
				  
				objReportsDto.setLOC_No(rs.getString("LOCNO"));   
				objReportsDto.setBillingAddress(VAR_BILLING_ADDRESS);       
				objReportsDto.setFxSiId(rs.getString("FX_SI_ID"));  
				objReportsDto.setCancelBy(rs.getString("CANCEL_BY"));
				//objReportsDto.setCanceldate(rs.getString("CANCEL_DATE"));//CANCEL_DATE
				tempDate=rs.getDate("CANCEL_DATE");  
				if(tempDate!=null)
				{
					objReportsDto.setCanceldate((Utility.showDate_Report(tempDate)).toUpperCase());
				}
				objReportsDto.setCancelReason(rs.getString("CANCEL_RESION"));
				objReportsDto.setOpms_Account_Id(rs.getString("Opms_Account_Id"));				
				objReportsDto.setRegionName(rs.getString("REGION"));      
				objReportsDto.setBandwaidth(rs.getString("BANDWIDTH"));     
				objReportsDto.setVerticalDetails(rs.getString("VERTICAL_DETAILS"));      
				objReportsDto.setToLocation(rs.getString("FROM_ADDRESS"));     
				objReportsDto.setFromLocation(rs.getString("TO_ADDRESS"));
				objReportsDto.setColl_Manager(rs.getString("COLL_MANAGER"));
				objReportsDto.setColl_Manager_Mail(rs.getString("COLL_MANAGER_MAIL"));
				objReportsDto.setColl_Manager_Phone(rs.getString("COLL_MANAGER_PHONE"));				
				objReportsDto.setBilling_bandwidth(rs.getString("BILLING_BANDWIDTH"));    
				objReportsDto.setOrder_type(rs.getString("DEMO_TYPE"));
				//--CRM ORDER ID
				objReportsDto.setOrderNumber(rs.getInt("ORDERNO"));	
				//--Charge Hdr Id
				objReportsDto.setOrderLineNumber(rs.getInt("Order_Line_Id"));
				objReportsDto.setServiceId(rs.getInt("SERVICE_NO"));
				
				objReportsDto.setTotalPoAmt(rs.getString("TOTALPOAMOUNT")); 				
				//    --Installment Rate				
				//--Trai Rate
				//--Discount
				objReportsDto.setContractPeriod(rs.getInt("CONTRACTPERIOD"));
				objReportsDto.setCommitmentPeriod(rs.getInt("COMMITMENTPERIOD"));  
				objReportsDto.setNoticePeriod(rs.getInt("NOTICEPERIOD"));   
				//--Principal Amt
				//   --Intrest Rate
				//   --Period In Month
				objReportsDto.setPoAmount(rs.getString("CUST_TOT_PO_AMT"));
				// --party Id
				//   --Cust Account id
				 //  --M6 Product Id
				 //  --M6 Order Id
				 //  --Ib Order Line Id
				  // --Ib Service List Id
				 //  --Ib Pk Charges Id
				 //  --Fx Sno
				 //  --Fx Sno Ed
				  // --Cust Tot Po Amt
				  // --M6 Order No
				 //  --Business Serial No
				 //  --Bus Serial
				  // --Advance
				if(objDto.getIsUsage() == 1)
				{
					objReportsDto.setOrderStage(rs.getString("ORDERSTAGE"));
					objReportsDto.setComponentInfoID(rs.getInt("COMPONENTINFOID"));
					objReportsDto.setComponentID(rs.getInt("COMPONENT_ID"));
					objReportsDto.setComponentName(rs.getString("COMPONENT_NAME"));
					objReportsDto.setPackageID(rs.getInt("PACKAGE_ID"));
					objReportsDto.setPackageName(rs.getString("PACKAGE_NAME"));
					objReportsDto.setCreditPeriodName(rs.getString("CREDIT_PERIODNAME"));
					objReportsDto.setServiceOrderType(rs.getString("SERVICE_ORDER_TYPE_DESC"));
					objReportsDto.setM6OrderNo(rs.getInt("M6ORDERNO"));
					objReportsDto.setCancelServiceReason(rs.getString("CANCEL_SERVICE_REASON"));
					//objReportsDto.setCancelBy(rs.getString("CANCELBY"));	
					//objReportsDto.setCanceldate(rs.getString("CANCELDATE"));
					
					ComponentsDto dto = new ComponentsDto();
					dto.setComponentStatus(rs.getString("COMPONENT_STATUS"));
					dto.setStartLogic(rs.getString("COMPONENT_START_LOGIC"));
					dto.setStartDate(rs.getString("COMPONENT_START_DATE"));
					if (rs.getString("COMPONENT_START_DATE") != null && !"".equals(rs.getString("COMPONENT_START_DATE")))
					{
						
						Date date=df.parse(dto.getStartDate());
						dto.setStartDate((Utility.showDate_Report(date)).toUpperCase());
						
					}
					dto.setEndLogic(rs.getString("COMPONENT_END_LOGIC"));
					dto.setEnd_date(rs.getString("COMPONENT_END_DATE"));
					if (rs.getString("COMPONENT_END_DATE") != null && !"".equals(rs.getString("COMPONENT_END_DATE")))
					{
						
						Date date=df.parse(dto.getEnd_date());
						dto.setEnd_date((Utility.showDate_Report(date)).toUpperCase());
						
					}
					dto.setFxTokenNo(rs.getString("START_COMPONENT_TOKEN_NO"));
					dto.setFxStartStatus(rs.getString("SYSTEM_START_STATUS"));
					dto.setEndTokenNo(rs.getString("LOCAL_END_COMPONENT_TOKEN_NO"));
					dto.setEndFxStatus(rs.getString("FX_END_STATUS"));
					
					objReportsDto.setStartDateDays(rs.getInt("COMP_START_DAYS"));
					objReportsDto.setStartDateMonth(rs.getInt("COMP_START_MONTHS"));
					objReportsDto.setEndDateDays(rs.getInt("COMP_END_DAYS"));
					objReportsDto.setEndDateMonth(rs.getInt("COMP_END_MONTHS"));
					objReportsDto.setFx_internal_acc_id(rs.getLong("INTERNAL_ID"));
					objReportsDto.setFx_external_acc_id(rs.getString("Child_Account_Number"));
					objReportsDto.setChildAccountFXSataus(rs.getString("Child_Account_FX_Sataus"));
					dto.setComponentInstanceID(rs.getString("COMPONENT_INST_ID"));
					dto.setComponentType(rs.getString("COMPONENT_TYPE"));
					dto.setComponentAmount(rs.getDouble("COMP_AMOUNT"));
					
					objReportsDto.setComponentDto(dto);
				}else{
					objReportsDto.setOrderStage(rs.getString("STAGE"));
					objReportsDto.setFxStatus(rs.getString("CSTATE_START_DETAILS_FX_STATUS"));//FX_STATUS
					objReportsDto.setFx_St_Chg_Status(rs.getString("CSTATE_FX_CHARGE_START_STATUS"));//Fx_St_Chg_Status
					objReportsDto.setFx_Ed_Chg_Status(rs.getString("CSTATE_FX_CHARGE_END_STATUS"));//Fx_Ed_Chg_Status
					objReportsDto.setTokenNO(rs.getString("CSTATE_START_DETAILS_FX_TOKEN_NO"));//Token_No
					objReportsDto.setChallenno(rs.getString("CHALLEN_NO"));
					objReportsDto.setChallendate(rs.getString("CHALLEN_DATE"));
					if (rs.getString("CHALLEN_DATE") != null && !"".equals(rs.getString("CHALLEN_DATE")))
					{
						Date date=df.parse(objReportsDto.getChallendate());
						objReportsDto.setChallendate((Utility.showDate_Report(date)).toUpperCase());
					}
					objReportsDto.setFx_external_acc_id(rs.getString("Child_Account_Number"));
					objReportsDto.setChildAccountFXSataus(rs.getString("Child_Account_FX_Sataus"));
					objReportsDto.setWarrantyStartDate(rs.getString("WARRENTY_START_DATE"));
					if (rs.getString("WARRENTY_START_DATE") != null && !"".equals(rs.getString("WARRENTY_START_DATE")))
					{
						Date date=df.parse(objReportsDto.getWarrantyStartDate());
						objReportsDto.setWarrantyStartDate((Utility.showDate_Report(date)).toUpperCase());
					}
					objReportsDto.setWarrantyEndDate(rs.getString("WARRENTY_END_DATE"));
					if (rs.getString("WARRENTY_END_DATE") != null && !"".equals(rs.getString("WARRENTY_END_DATE")))
					{
						Date date=df.parse(objReportsDto.getWarrantyEndDate());
						objReportsDto.setWarrantyEndDate((Utility.showDate_Report(date)).toUpperCase());
						
					}
					objReportsDto.setExtSuportEndDate(rs.getString("EXT_SUPPORT_END_DATE"));   
					if (rs.getString("EXT_SUPPORT_END_DATE") != null && !"".equals(rs.getString("EXT_SUPPORT_END_DATE")))
					{
						Date date=df.parse(objReportsDto.getExtSuportEndDate());
						objReportsDto.setExtSuportEndDate((Utility.showDate_Report(date)).toUpperCase());
					}
					objReportsDto.setDispatchAddressName(rs.getString("DISPATCHADDNAME"));
					objReportsDto.setHardwareFlag(rs.getString("HARDWARE_FLAG"));
					objReportsDto.setTokenNoEd(rs.getString("CSTATE_END_DETAILS_FX_TOKEN_NO"));//--Token No Ed
					objReportsDto.setHardwaretypeName(rs.getString("HARDWARETYPENAME"));	
					objReportsDto.setFormAvailable(rs.getString("FORM_C_AVAILABLE"));
					objReportsDto.setSaleNature(rs.getString("SALENATURE"));
					objReportsDto.setSaleType(rs.getString("SALETYPE"));
					objReportsDto.setChargeTypeName(rs.getString("CHARGE_TYPE"));
					objReportsDto.setFrequencyName(rs.getString("FREQUENCY"));
					objReportsDto.setChargeName(rs.getString("CHARGE_NAME"));
					if((rs.getString("CONFIG_ID")) != null && !"".equals(rs.getString("CONFIG_ID")) && (rs.getString("ENTITYID")) != null && !"".equals(rs.getString("ENTITYID")))
					{
						String tBillPeriod=fnGetUsageReportBIllPeriod(rs.getString("CONFIG_ID"), rs.getString("ENTITYID"));
						objReportsDto.setBillPeriod(tBillPeriod);
					}
					objReportsDto.setDnd_Dispatch_But_Not_Delivered(rs.getString("Dnd_Dispatch_But_Not_Delivered"));
					objReportsDto.setDnd_Dispatch_And_Delivered(rs.getString("Dnd_Dispatch_And_Delivered"));
					objReportsDto.setDnd_Disp_Del_Not_Installed(rs.getString("Ddni_Disp_Del_Not_Installed"));
					objReportsDto.setDnd_Disp_Delivered_Installed(rs.getString("Ddni_Disp_Delivered_Installed"));				
					objReportsDto.setStartDateLogic(rs.getString("STARTDATELOGIC"));
					objReportsDto.setEndDateLogic(rs.getString("ENDDATELOGIC"));
					objReportsDto.setStartDate(rs.getString("START_DATE"));
					if (rs.getString("START_DATE") != null && !"".equals(rs.getString("START_DATE")))
					{
						
						Date date=df.parse(objReportsDto.getStartDate());
						objReportsDto.setStartDate((Utility.showDate_Report(date)).toUpperCase());
						
					}
					objReportsDto.setEndDate(rs.getString("END_DATE"));
					if (rs.getString("END_DATE") != null && !"".equals(rs.getString("END_DATE")))
					{
						
						Date date=df.parse(objReportsDto.getEndDate());
						objReportsDto.setEndDate((Utility.showDate_Report(date)).toUpperCase());
						
					}
					objReportsDto.setChargeAnnotation(rs.getString("ANNOTATION"));
					objReportsDto.setChargeAmount_String(rs.getString("INV_AMT"));
					objReportsDto.setLineItemAmount(rs.getString("LINEITEMAMOUNT"));
					objReportsDto.setCharge_status(rs.getString("CHARGES_STATUS")); 
					objReportsDto.setStartDateDays(rs.getInt("START_DATE_DAYS"));
					objReportsDto.setStartDateMonth(rs.getInt("START_DATE_MONTH"));
					objReportsDto.setEndDateDays(rs.getInt("END_DATE_DAYS"));
					objReportsDto.setEndDateMonth(rs.getInt("END_DATE_MONTH"));
					objReportsDto.setAnnualRate(rs.getString("ANNUAL_RATE"));
				}
				
				
				if (pagingSorting.isPagingToBeDone()) {
					recordCount = rs.getInt("FULL_REC_COUNT");
				}
				listSearchDetails.add(objReportsDto);
			}
			pagingSorting.setRecordCount(recordCount);
		}
		catch(Exception ex )
		{
			//ex.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}
		finally
		{
			try 
			{
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(proc);
				DbConnection.freeConnection(connection);
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				//e.printStackTrace();
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			}
		}
		return listSearchDetails;
	}
	/**
 * 
 * @param objDto
 * @return
 * @throws Exception
 */
public ArrayList<ActiveLineItemReportsDTO> viewActiveLineItemsList(ActiveLineItemReportsDTO objDto) throws Exception {

	//Nagarjuna
	String methodName="viewActiveLineItemsList", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
		ArrayList<ActiveLineItemReportsDTO> objUserList = new ArrayList<ActiveLineItemReportsDTO>();
		Connection conn = null;
		CallableStatement proc = null;
		ResultSet rs = null;
		int recordCount = 0;
		Utility utility=new Utility();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date tempDate=null;
		Timestamp ts=null;
		try {

			conn = DbConnection.getReportsConnectionObject();

			proc = conn.prepareCall(sqlActiveLineItemsForUsage);
			proc.setInt(1, java.sql.Types.BIGINT);
			if (objDto.getOrderType() != null
					&& !"".equals(objDto.getOrderType())) {
				proc.setString(2, objDto.getOrderType().trim());
			} else {
				proc.setNull(2, java.sql.Types.VARCHAR);
			}
			if (objDto.getFromDate() != null
					&& !"".equals(objDto.getFromDate())) {
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				Date dateStr = formatter.parse(objDto.getFromDate());
				String formattedDate = formatter.format(dateStr);
				Date date1 = formatter.parse(formattedDate);
				formatter = new SimpleDateFormat("MM-dd-yyyy");
				formattedDate = formatter.format(date1);
				proc.setString(3,formattedDate);
				//proc.setString(2, objDto.getFromDate().trim());
			} else {
				proc.setNull(3, java.sql.Types.VARCHAR);
			}				
			if (objDto.getToDate() != null && !"".equals(objDto.getToDate())) {
				SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
				Date dateStr1 = formatter1.parse(objDto.getToDate());
				String formattedDate1 = formatter1.format(dateStr1);
				Date date2 = formatter1.parse(formattedDate1);
				formatter1 = new SimpleDateFormat("MM-dd-yyyy");
				formattedDate1 = formatter1.format(date2);
				proc.setString(4,formattedDate1);
			} else {
				proc.setNull(4, java.sql.Types.VARCHAR);
			}

			if (objDto.getFromOrderNo() != 0 
					&& !"".equals(objDto.getFromOrderNo())) {
				proc.setInt(5, objDto.getFromOrderNo());
			} else {
				proc.setNull(5,java.sql.Types.BIGINT);
			}
			if (objDto.getToOrderNo() != 0 
					&& !"".equals(objDto.getToOrderNo())) {
				proc.setInt(6, objDto.getToOrderNo());
			} else {
				proc.setNull(6, java.sql.Types.BIGINT);
			}			
			PagingSorting pagingSorting = objDto.getPagingSorting();
			pagingSorting.sync();// To calculate start index and Enc Index

			proc.setString(7, pagingSorting.getSortByColumn());// columnName
			proc.setString(8, PagingSorting.DB_Asc_Desc(pagingSorting
					.getSortByOrder()));// sort order
			proc.setInt(9, pagingSorting.getStartRecordId());// start index
			proc.setInt(10, pagingSorting.getEndRecordId());// end index
			proc.setInt(11, (pagingSorting.isPagingToBeDone() ? 1 : 0));// end
			// index
			proc.setInt(12, (objDto.getIsUsage()));// end

			if (objDto.getFromCopcApprovedDate() != null
					&& !"".equals(objDto.getFromCopcApprovedDate())) {
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				Date dateStr = formatter.parse(objDto.getFromCopcApprovedDate());
				String formattedDate = formatter.format(dateStr);
				Date date1 = formatter.parse(formattedDate);
				formatter = new SimpleDateFormat("MM-dd-yyyy");
				formattedDate = formatter.format(date1);
				proc.setString(13,formattedDate);
				//proc.setString(2, objDto.getFromDate().trim());
			} else {
				proc.setNull(13, java.sql.Types.VARCHAR);
			}				
			if (objDto.getToCopcApprovedDate() != null && !"".equals(objDto.getToCopcApprovedDate())) {
				SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
				Date dateStr1 = formatter1.parse(objDto.getToCopcApprovedDate());
				String formattedDate1 = formatter1.format(dateStr1);
				Date date2 = formatter1.parse(formattedDate1);
				formatter1 = new SimpleDateFormat("MM-dd-yyyy");
				formattedDate1 = formatter1.format(date2);
				proc.setString(14,formattedDate1);
			} else {
				proc.setNull(14, java.sql.Types.VARCHAR);
			}
			if (objDto.getPartyNo() != 0 
					&& !"".equals(objDto.getPartyNo())) {
				proc.setInt(15, objDto.getPartyNo());
			} else {
				proc.setNull(15,java.sql.Types.BIGINT);
			}
			System.out.println("Dao" +objDto.getPartyNo());
			
			if (objDto.getCustomerSegment() != null
					&& !"".equals(objDto.getCustomerSegment()) && !objDto.getCustomerSegment().equals("0") ) {
				proc.setString(16, objDto.getCustomerSegment());
			} else {
				proc.setNull(16,java.sql.Types.VARCHAR);
			}
			System.out.println("Dao cust " +objDto.getCustomerSegment());
			if (objDto.getProductName() != null
			&& !"".equals(objDto.getProductName()) && !objDto.getProductName().equals("0")) {
				proc.setString(17, objDto.getProductName());
			} else {
				proc.setNull(17,java.sql.Types.VARCHAR);
			}
			rs = proc.executeQuery();
			int countFlag = 0;
			ActiveLineItemReportsDTO objdto ;
			while (rs.next() != false) {
				countFlag++;
				//Added by Ashutosh for Billing Address
				//[270513]Start
				setBlank();
				//replaceSeperator("BILLING_LOCATION",rs.getString("BILLING_ADDRESS"));
				replaceSeperator("PRIMARYLOCATION",rs.getString("PRIMARYLOCATION"));
				replaceSeperator("SECONDARYLOCATION",rs.getString("SECONDARYLOCATION"));

				objdto = new ActiveLineItemReportsDTO();
				objdto.setLogicalSINo(rs.getString("LOGICAL_SI_NO"));
				objdto.setCustSINo(rs.getString("CUSTOMER_LOGICAL_SI_NO"));
				objdto.setServiceName(rs.getString("SERVICENAME"));
				//objdto.setLinename(rs.getString("LINENAME"));//PARENT_NAME
				//objdto.setAccountID(rs.getInt("ACCOUNTID"));//CUSTACCOUNTID
				objdto.setCreditPeriodName(rs.getString("CREDIT_PERIODNAME"));
				//objdto.setCurrencyName(rs.getString("CURNAME"));//CURRENCYNAME
				objdto.setEntity(rs.getString("ENTITYNAME"));
				objdto.setBillingMode(rs.getString("BILLINGMODE"));
				objdto.setBillingTypeName(rs.getString("BILLING_TYPENAME"));
				objdto.setBillingformat(rs.getString("BILLING_FORMATNAME"));
				objdto.setLicCompanyName(rs.getString("LCOMPANYNAME"));
				objdto.setTaxation(rs.getString("TAXATIONVALUE"));
				objdto.setBillingLevelName(rs.getString("BILLING_LEVELNAME"));
				objdto.setBillingLevelNo(rs.getLong("BILLING_LEVEL_NO"));
				//objdto.setPoNumber(rs.getInt("PODETAILNUMBER"));//PONUMBER
				Date temDate=rs.getDate("PODATE");
				if(temDate!=null)
				{
					objdto.setPoDate((Utility.showDate_Report(temDate)).toUpperCase());
				}
				objdto.setParty_num(rs.getString("PARTY_NO"));
				objdto.setBillingTriggerFlag(rs.getString("BILLING_TRIGGER_FLAG"));
				/*objdto.setPm_pro_date(rs.getString("PM_PROVISIONING_DATE"));//Pm_Prov_Date
				if (rs.getString("PM_PROVISIONING_DATE") != null && !"".equals(rs.getString("PM_PROVISIONING_DATE")))
				{
					  String s1=rs.getString("PM_PROVISIONING_DATE");
					  String s3=s1.substring(0,7).toUpperCase();
					  String s4=s1.substring(9,11);
					  String s5=s3.concat(s4);
					  objdto.setPm_pro_date(s5);
				}*/
				objdto.setLocDate(rs.getString("LOCDATE"));
				if (rs.getString("LOCDATE") != null && !"".equals(rs.getString("LOCDATE")))
				{
					
					Date date=df.parse(objdto.getLocDate());
					objdto.setLocDate((utility.showDate_Report(date)).toUpperCase());
				}
				objdto.setBilling_trigger_date(rs.getString("BILLINGTRIGGERDATE"));
				if (rs.getString("BILLINGTRIGGERDATE") != null && !"".equals(rs.getString("BILLINGTRIGGERDATE")))
				{
					
					Date date=df.parse(objdto.getBilling_trigger_date());
					objdto.setBilling_trigger_date((utility.showDate_Report(date)).toUpperCase());
				}
				//objdto.setFx_external_acc_id(rs.getString("FX_ACCOUNT_EXTERNAL_ID"));//Child_Account_Number
				objdto.setChild_account_creation_status(rs.getString("Child_account_FX_sataus"));
				
				tempDate=rs.getDate("ORDERDATE");
				if(tempDate!=null)
				{
					objdto.setOrderDate((Utility.showDate_Report(tempDate)).toUpperCase());
				}
				/*objdto.setCopcapprovaldate(rs.getString("APPROVAL_DATE"));//ORDER_APPROVED_DATE
				if (rs.getString("APPROVAL_DATE") != null && !"".equals(rs.getString("APPROVAL_DATE")))
				{
					objdto.setCopcapprovaldate((utility.showDate_Report(new Date(rs.getTimestamp("APPROVAL_DATE").getTime()))).toUpperCase());
				}*/
				objdto.setOrderType(rs.getString("ORDERTYPE"));
				ts=rs.getTimestamp("BILLING_TRIGGER_CREATEDATE");
				if (ts!=null)
				{
					tempDate=new Date(ts.getTime());
					objdto.setBillingtrigger_createdate(Utility.showDate_Report(tempDate).toUpperCase());
				}
				objdto.setProductName(rs.getString("PRODUCTNAME"));
				objdto.setSubProductName(rs.getString("SUBPRODUCTNAME"));
				objdto.setServiceStage(rs.getString("SERVICE_STAGE"));
				objdto.setOrderStage(rs.getString("STAGE"));
				/*objdto.setRfsDate(rs.getString("RFS_DATE"));//SERVICE_RFS_DATE
				if (rs.getString("RFS_DATE") != null && !"".equals(rs.getString("RFS_DATE")))
				{
					objdto.setRfsDate((utility.showDate_Report(new Date(rs.getTimestamp("RFS_DATE").getTime()))).toUpperCase());
				}*/
				tempDate=rs.getDate("PORECEIVEDATE");
				if (rs.getString("PORECEIVEDATE") != null && !"".equals(rs.getString("PORECEIVEDATE")))
				{
					objdto.setPoReceiveDate((Utility.showDate_Report(tempDate)).toUpperCase());
				}
				objdto.setCustPoDetailNo(rs.getString("CUSTPONUMBER"));
				//objdto.setCustPoDate(rs.getString("CUST_PODATE"));//PORECEIVEDATE
				/*if (rs.getString("CUST_PODATE") != null && !"".equals(rs.getString("CUST_PODATE")))
				{
					Date date=df.parse(objdto.getCustPoDate());
					objdto.setCustPoDate((utility.showDate_Report(date)).toUpperCase());
				}*/
				
				objdto.setM6cktid(rs.getString("CKTID"));
				objdto.setOrdersubtype(rs.getString("ORDERSUBTYPE"));
				objdto.setRegion(rs.getString("REGIONNAME"));//Added in View Componet  :AKS
				objdto.setBandwaidth(rs.getString("BANDWIDTH"));     
				objdto.setVertical(rs.getString("VERTICAL_DETAILS"));
				objdto.setAccountManager(rs.getString("ACCOUNTMANAGER"));
				objdto.setProjectManager(rs.getString("PROJECTMANAGER"));
				objdto.setPartyName(rs.getString("PARTYNAME"));
				//objdto.setBilling_location_from(rs.getString("BILLING_LOCATION"));//BILLING_ADDRESS
				//objdto.setDemo(rs.getString("DEMO_ORDER"));//Demo_Type
				//objdto.setToLocation(rs.getString("FROM_LOCATION"));//FROM_ADDRESS
				//objdto.setFromLocation(rs.getString("TO_LOCATION"));//TO_ADDRESS
				objdto.setBilling_bandwidth(rs.getString("BILLING_BANDWIDTH"));
				objdto.setBilling_uom(rs.getString("BILLING_BANDWIDTH_UOM"));
				objdto.setServiceproductid(rs.getInt("ORDER_LINE_ID"));
				objdto.setOrderNumber(rs.getInt("ORDERNO"));
				objdto.setContractPeriod(rs.getInt("CONTRACTPERIOD"));
				//objdto.setTotalPoAmt(""+rs.getDouble("TOTAL_POAMOUNT"));//ORDERTOTAL
				objdto.setParty_id(rs.getInt("PARTY_ID"));
				objdto.setCrmAccountNoString(rs.getString("CRMACCOUNTNO"));
				//objdto.setServiceId(rs.getInt("SERVICEID"));//SERVICE_NO
				//[111] START
				objdto.setMainSpid(rs.getLong("MAIN_SERVICEPRODUCTID"));
				objdto.setTaxExemptReasonName(rs.getString("REASONNAME"));
				//[111] end
                //[003] Start
				objdto.setServiceSegment(rs.getString("ACCOUNTCATEGORY_DETAILS"));
                //[003] end
				
				//[004] rahul Start
				objdto.setChargeRedirectionLSI(rs.getString("FX_REDIRECTION_LSI"));
                //[004] rahul end
				
				//[005] Start
				objdto.setInstallationFromCity(rs.getString("INSTALLATION_FROM_CITY"));
				objdto.setInstallationToCity(rs.getString("INSTALLATION_TO_CITY"));
				objdto.setInstallationFromState(rs.getString("INSTALLATION_FROM_STATE"));
				objdto.setInstallationToState(rs.getString("INSTALLATION_TO_STATE"));
				objdto.setBillingContactName(rs.getString("BILLING_CONTACT_NAME"));
				objdto.setBillingContactNumber(rs.getString("BILLING_CONTACT_NUMBER"));
				objdto.setBillingEmailId(rs.getString("BILLING_EMAIL_ID"));
				//[005] End
				
				//[006] Start
				objdto.setStandardReason(rs.getString("STANDARDREASON"));
				objdto.setLdClause(rs.getString("LDCLAUSE"));
				//[006] End
				
				//Saurabh : Changes for Usage for storing charge data
				if(objDto.getIsUsage() == 0)
				{
					objdto.setChargeTypeName(rs.getString("CHARGETYPE"));
					objdto.setChargeTypeID(rs.getInt("CHARGETYPEID"));
					objdto.setChargeName(rs.getString("CHARGE_NAME"));
					objdto.setChargeFrequency(rs.getString("FREQUENCYNAME"));
					objdto.setBillPeriod(rs.getString("BILL_PERIOD"));
					objdto.setStartDate(rs.getString("START_DATE"));
					if (rs.getString("START_DATE") != null && !"".equals(rs.getString("START_DATE")))
					{
						
						Date date=df.parse(objdto.getStartDate());
						objdto.setStartDate((utility.showDate_Report(date)).toUpperCase());
					}
					objdto.setStore(rs.getString("STORENAME"));
					objdto.setHardwaretypeName(rs.getString("HARDWARETYPENAME"));
					objdto.setFormAvailable(rs.getString("FORM_C_AVAILABLE"));
					objdto.setSaleNature(rs.getString("SALENATURENAME"));
					objdto.setSaleTypeName(rs.getString("SALETYPENAME"));
					objdto.setPrimaryLocation(rs.getString("PRIMARYLOCATION"));
					objdto.setSeclocation(rs.getString("SECONDARYLOCATION"));
					objdto.setChargeAnnotation(rs.getString("ANNOTATION"));
					objdto.setFx_sd_charge_status(rs.getString("FX_SD_CHG_SATATUS"));
					objdto.setFx_charge_status(rs.getString("FX_STATUS"));
					objdto.setFx_Ed_Chg_Status(rs.getString("FX_ED_CHARGE_STATUS"));
					objdto.setTokenID(rs.getInt("TOKEN_ID"));
					objdto.setModifiedDate(rs.getString("LAST_UPDATE_DATE"));
					if (rs.getString("LAST_UPDATE_DATE") != null && !"".equals(rs.getString("LAST_UPDATE_DATE")))
					{
						objdto.setModifiedDate((utility.showDate_Report(new Date(rs.getTimestamp("LAST_UPDATE_DATE").getTime()))).toUpperCase());
					}
					objdto.setChallenno(rs.getString("CHALLEN_NO"));
					objdto.setChallendate(rs.getString("CHALLEN_DATE"));
					if (rs.getString("CHALLEN_DATE") != null && !"".equals(rs.getString("CHALLEN_DATE")))
					{
					  String s1=rs.getString("CHALLEN_DATE");
					  String s3=s1.substring(0,7).toUpperCase();
					  String s4=s1.substring(9,11);
					  String s5=s3.concat(s4);
					  objdto.setChallendate(s5);
					}
					objdto.setRatio(rs.getString("RATIO"));
					objdto.setHardwareType(rs.getString("HARDWARE_FLAG"));
					objdto.setCharge_status(rs.getString("CHARGES_STATUS"));
					objdto.setLOC_No(rs.getString("LOCNO"));
					objdto.setAddress1(rs.getString("ADDRESS"));
					objdto.setRate_code(rs.getString("RATECODE"));
					objdto.setDistance(rs.getString("CHARGEABLE_DISTANCE"));
					objdto.setDispatchAddress1(rs.getString("DISPATCH_DETAILS"));
					objdto.setIndicative_value(rs.getString("INDICATIVE_VALUE"));
					objdto.setCrm_productname(rs.getString("CRM_PRODUCTNAME"));
					objdto.setBlSource(rs.getString("BL_SOURCE"));
					objdto.setChargeAmount(rs.getDouble("INV_AMT"));
					objdto.setLineamt(rs.getDouble("LINEITEMAMOUNT"));
					objdto.setChargesSumOfLineitem(rs.getDouble("TOTAL_CHARGE_AMT"));
					objdto.setM6OrderNo(rs.getInt("M6ORDERID"));
					objdto.setCust_total_poamt(rs.getDouble("CUST_TOT_PO_AMT"));
					objdto.setPk_charge_id(rs.getString("PK_CHARGE_ID"));//Added by Ashutosh as on 26-Jun-12
				    objdto.setContractPeriod(rs.getInt("CONTRACTPERIOD"));
				    objdto.setAnnual_rate(rs.getDouble("ANNUAL_RATE"));
					//Start: Adding Changes 
					objdto.setLinename(rs.getString("LINENAME"));//PARENT_NAME
					objdto.setAccountID(rs.getInt("ACCOUNTID"));//CUSTACCOUNTID					
					objdto.setCurrencyName(rs.getString("CURNAME"));//CURRENCYNAME
					objdto.setPoNumber(rs.getInt("PODETAILNUMBER"));//PONUMBER				
					objdto.setPm_pro_date(rs.getString("PM_PROVISIONING_DATE"));//Pm_Prov_Date
					if (rs.getString("PM_PROVISIONING_DATE") != null && !"".equals(rs.getString("PM_PROVISIONING_DATE")))
					{
						  String s1=rs.getString("PM_PROVISIONING_DATE");
						  String s3=s1.substring(0,7).toUpperCase();
						  String s4=s1.substring(9,11);
						  String s5=s3.concat(s4);
						  objdto.setPm_pro_date(s5);
					}
					objdto.setFx_external_acc_id(rs.getString("FX_ACCOUNT_EXTERNAL_ID"));//Child_Account_Number				
					objdto.setCopcapprovaldate(rs.getString("APPROVAL_DATE"));//ORDER_APPROVED_DATE
					if (rs.getString("APPROVAL_DATE") != null && !"".equals(rs.getString("APPROVAL_DATE")))
					{
						objdto.setCopcapprovaldate((utility.showDate_Report(new Date(rs.getTimestamp("APPROVAL_DATE").getTime()))).toUpperCase());
					}
					tempDate=rs.getDate("RFS_DATE");					
					if (tempDate != null)
					{
						objdto.setRfsDate((Utility.showDate_Report(tempDate)).toUpperCase());
					}
					objdto.setCustPoDate(rs.getString("CUST_PODATE"));//PORECEIVEDATE
					if (rs.getString("CUST_PODATE") != null && !"".equals(rs.getString("CUST_PODATE")))
					{
						Date date=df.parse(objdto.getCustPoDate());
						objdto.setCustPoDate((utility.showDate_Report(date)).toUpperCase());
					}				
					objdto.setBilling_location_from(rs.getString("BILLING_LOCATION"));//BILLING_ADDRESS
					objdto.setDemo(rs.getString("DEMO_ORDER"));//Demo_Type
					objdto.setToLocation(rs.getString("FROM_LOCATION"));//FROM_ADDRESS
					objdto.setFromLocation(rs.getString("TO_LOCATION"));//TO_ADDRESS
					
					objdto.setTotalPoAmt(""+rs.getDouble("TOTAL_POAMOUNT"));//ORDERTOTAL
					
					objdto.setServiceId(rs.getInt("SERVICEID"));//SERVICE_NO
					objdto.setPoExpiryDate(rs.getString("PO_EXPIRY_DATE"));			
					if (rs.getString("PO_EXPIRY_DATE") != null && !"".equals(rs.getString("PO_EXPIRY_DATE")))
					{
						//Date date=df.parse(objdto.getPoExpiryDate());
						//objdto.setPoExpiryDate((utility.showDate_Report(date)).toUpperCase());
						objdto.setPoExpiryDate((utility.showDate_Report(new Date(rs.getTimestamp("PO_EXPIRY_DATE").getTime()))).toUpperCase());
						
					}
					//END
				}
				
				// Meenakshi : Changes for Usage for storing component data
				else if(objDto.getIsUsage() == 1)
				{
					objdto.setFxInternalId(rs.getInt("INTERNAL_ID"));
					objdto.setMinimum_bandwidth(rs.getString("MINIMUM_BANDWIDTH"));//Need to add in View : AKS(Added)
					objdto.setMinimum_bandwidth_UOM(rs.getString("MINIMUM_BANDWIDTH_UOM"));//Need to add in View : AKS(Added)
					objdto.setComponentInfoID(rs.getInt("COMPONENTINFOID"));
					objdto.setComponentID(rs.getInt("COMPONENT_ID"));
					objdto.setComponentName(rs.getString("COMPONENT_NAME"));
					objdto.setPackageID(rs.getInt("PACKAGE_ID"));
					objdto.setPackageName(rs.getString("PACKAGE_NAME"));
					ComponentsDto dto = new ComponentsDto();
					dto.setComponentType(rs.getString("COMPONENT_TYPE"));
					dto.setComponentAmount(rs.getDouble("COMP_AMOUNT"));//RC_NRC_COMP_AMOUNT : AKS
					dto.setComponentStatus(rs.getString("COMPONENT_STATUS"));
					dto.setStartLogic(rs.getString("COMPONENT_START_LOGIC"));
					dto.setStartDate(rs.getString("COMPONENT_START_DATE"));
					if (rs.getString("COMPONENT_START_DATE") != null && !"".equals(rs.getString("COMPONENT_START_DATE")))
					{						
						Date date=df.parse(dto.getStartDate());
						dto.setStartDate((utility.showDate_Report(date)).toUpperCase());
					}
					dto.setEndLogic(rs.getString("COMPONENT_END_LOGIC"));
					dto.setEnd_date(rs.getString("COMPONENT_END_DATE"));
					if (rs.getString("COMPONENT_END_DATE") != null && !"".equals(rs.getString("COMPONENT_END_DATE")))
					{						
						Date date=df.parse(dto.getEnd_date());
						dto.setEnd_date((utility.showDate_Report(date)).toUpperCase());
					}
					dto.setFxTokenNo(rs.getString("START_COMPONENT_TOKEN_NO"));
					dto.setFxStartStatus(rs.getString("SYSTEM_START_STATUS"));
					//dto.setChargesSumOfLineitem(rs.getString("FX_START_COMPONENT_STATUS"));
					dto.setComponentInstanceID(rs.getString("COMPONENT_INST_ID"));//COMP_FX_INSTANCE_ID : AKS
					dto.setEndFxStatus(rs.getString("SYSTEM_END_STATUS"));//FX_END_COMPONENT_STATUS :AKS
					//dto.setStartStatus(rs.getString("FX_ST_COMPONENT_STATUS"));
					objdto.setExternalId(rs.getString("FX_SI_ID"));
					//Start: Adding Changes 
					objdto.setM6OrderNo(rs.getInt("M6ORDERNO"));
					objdto.setLinename(rs.getString("PARENT_NAME"));
					objdto.setAccountID(rs.getInt("CUSTACCOUNTID"));				
					objdto.setCurrencyName(rs.getString("CURRENCYNAME"));
					objdto.setPoNumber(rs.getInt("PONUMBER"));				
					objdto.setPm_pro_date(rs.getString("Pm_Prov_Date"));
					if (rs.getString("Pm_Prov_Date") != null && !"".equals(rs.getString("Pm_Prov_Date")))
					{
						  String s1=rs.getString("Pm_Prov_Date");
						  String s3=s1.substring(0,7).toUpperCase();
						  String s4=s1.substring(9,11);
						  String s5=s3.concat(s4);
						  objdto.setPm_pro_date(s5);
					}
					objdto.setFx_external_acc_id(rs.getString("Child_Account_Number"));			
/*					objdto.setCopcapprovaldate(rs.getString("ORDER_APPROVED_DATE"));
					if (rs.getString("ORDER_APPROVED_DATE") != null && !"".equals(rs.getString("ORDER_APPROVED_DATE")))
					{
						objdto.setCopcapprovaldate((utility.showDate_Report(new Date(rs.getTimestamp("ORDER_APPROVED_DATE").getTime()))).toUpperCase());
					}*/
					
					
					objdto.setOrderApproveDate(rs.getString("ORDER_APPROVED_DATE"));//Copc date
					tempDate=rs.getDate("ORDER_APPROVED_DATE");
					if (tempDate != null && !"".equals(tempDate))
					{						
						objdto.setOrderApproveDate((utility.showDate_Report(tempDate)).toUpperCase());					
						
					}
					objdto.setCopcapprovaldate(rs.getString("COPC_APPROVED_DATE"));
					ts=rs.getTimestamp("COPC_APPROVED_DATE");
					if(ts!=null)
					{
						tempDate=new Date(ts.getTime());
						objdto.setCopcapprovaldate((utility.showDate_Report(tempDate)).toUpperCase());
						
					}
					
					
					tempDate=rs.getDate("SERVICE_RFS_DATE");
					if (tempDate!=null)
					{
						objdto.setRfsDate(Utility.showDate_Report(tempDate).toUpperCase());
					}
					tempDate=rs.getDate("PORECEIVEDATE");
					if(tempDate!=null)
					{
						
						objdto.setCustPoDate((Utility.showDate_Report(tempDate)).toUpperCase());
					}				
					objdto.setBilling_location_from(VAR_PRIMARYLOCATION);
					objdto.setBilling_location_to(VAR_SECONDARYLOCATION);
					objdto.setDemo(rs.getString("Demo_Type"));
					objdto.setToLocation(rs.getString("TO_ADDRESS"));
					objdto.setFromLocation(rs.getString("FROM_ADDRESS"));
					
					objdto.setTotalPoAmt(""+rs.getDouble("ORDERTOTAL"));
					
					objdto.setServiceId(rs.getInt("SERVICE_NO"));
					//nagarjuna PoExpiryDate Modified
					objdto.setPoExpiryDate(rs.getString("PO_EXPIRY_DATE"));			
					if (rs.getString("PO_EXPIRY_DATE") != null && !"".equals(rs.getString("PO_EXPIRY_DATE")))
					{
						//Date date=df.parse(objdto.getPoExpiryDate());
						//objdto.setPoExpiryDate((utility.showDate_Report(date)).toUpperCase());
						objdto.setPoExpiryDate((utility.showDate_Report(new Date(rs.getTimestamp("PO_EXPIRY_DATE").getTime()))).toUpperCase());
					}
					//nagarjuna PoExpiryDate Modified End
					//END
					//satyapan OSP/ISP
					objdto.setIsOspRequired(rs.getString("IS_OSP"));
					if (rs.getString("OSP_REG_NO") != null && !"".equals(rs.getString("OSP_REG_NO")))
					{	
					objdto.setOspRegistrationNo(rs.getString("OSP_REG_NO"));
					}else{
					objdto.setOspRegistrationNo("");	
					}
					objdto.setOspRegistrationDate(rs.getString("OSP_REG_DATE"));
					if (rs.getString("OSP_REG_DATE") != null && !"".equals(rs.getString("OSP_REG_DATE")))
					{	
						objdto.setOspRegistrationDate((Utility.showDate_Report(new Date(rs.getTimestamp("OSP_REG_DATE").getTime()))).toUpperCase());
					}
					objdto.setIsIspRequired(rs.getString("ISP_TAGGING"));
					if (rs.getString("ISP_LIC_CTGRY") != null && !"".equals(rs.getString("ISP_LIC_CTGRY")) && !"0".equals(rs.getString("ISP_LIC_CTGRY")))
					{
					objdto.setIspCatageory(rs.getString("ISP_LIC_CTGRY"));
					}else{
					objdto.setIspCatageory("");	
					}
					
					if (rs.getString("ISP_LIC_NO") != null && !"".equals(rs.getString("ISP_LIC_NO")))
					{	
					objdto.setIspLicenseNo(rs.getString("ISP_LIC_NO"));
					}else{
					objdto.setIspLicenseNo("");	
					}
					objdto.setIspLicenseDate(rs.getString("ISP_LIC_DATE"));
					if (rs.getString("ISP_LIC_DATE") != null && !"".equals(rs.getString("ISP_LIC_DATE")))
					{		
						objdto.setIspLicenseDate((Utility.showDate_Report(new Date(rs.getTimestamp("ISP_LIC_DATE").getTime()))).toUpperCase());
					}
					
					//End of satyapan OSP/ISP
					
					objdto.setComponentDto(dto);
				}
				/// End
				//[007] start
				objdto.setSalesForceOpportunityNumber(rs.getString("SF_OPP_ID"));
				objdto.setChannelPartner(rs.getString("PARTNER_NAME"));
				objdto.setNetworkType(rs.getString("NETWORK_SERVICE_TYPE"));
				objdto.setPartnerId(rs.getString("PARTNER_ID"));
				//[007] End
				//[008] start
				objdto.setPartnerCode(rs.getString("PARTNER_CODE"));
				objdto.setFieldEngineer(rs.getString("FIELD_ENGINEER"));
				//NANCY
				objdto.setePCNNo(rs.getString("EPCN_NO"));
				//NANCY		
				//[008] end
				if (pagingSorting.isPagingToBeDone()) {
					 recordCount = rs.getInt("FULL_REC_COUNT");
					//recordCount = countFlag;
				}
				objUserList.add(objdto);
			}
			pagingSorting.setRecordCount(recordCount);
		} catch (Exception ex) {
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			//ex.printStackTrace();
			//throw new Exception("SQL Exception : " + ex.getMessage(), ex);
		} finally {
			try {
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(proc);
				DbConnection.freeConnection(conn);

			} catch (Exception e) {
				//e.printStackTrace();
				//throw new Exception("Exception : " + e.getMessage(), e);
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			}
		}

		return objUserList;
}

/**
 * 
 * @param objDto
 * @return
 * @throws Exception
 */
public ArrayList<OrderReportNewDetailCwnDTO> viewOrderReportNew(OrderReportNewDetailCwnDTO objDto) throws Exception {
	//Nagarjuna
	String methodName="viewOrderReportNew", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	ArrayList<OrderReportNewDetailCwnDTO> objUserList = new ArrayList<OrderReportNewDetailCwnDTO>();
	Connection conn = null;
	CallableStatement proc = null;
	ResultSet rs = null;
	int recordCount = 0;
	OrderReportNewDetailCwnDTO objRDto;
	Utility utility=new Utility();
	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	Date tempDate=null;
	Timestamp ts=null;

	try {

		conn = DbConnection.getReportsConnectionObject();

		proc = conn.prepareCall(sqlOrderReportNewForUsage);
		
		if (objDto.getOrderType() != null
				&& !"".equals(objDto.getOrderType())) {
			proc.setString(1, objDto.getOrderType().trim());
		} else {
			proc.setNull(1, java.sql.Types.VARCHAR);
		}
		if (objDto.getFromDate() != null
				&& !"".equals(objDto.getFromDate())) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date dateStr = formatter.parse(objDto.getFromDate());
			String formattedDate = formatter.format(dateStr);
			Date date1 = formatter.parse(formattedDate);
			formatter = new SimpleDateFormat("MM-dd-yyyy");
			formattedDate = formatter.format(date1);
			proc.setString(2,formattedDate);
			//proc.setString(2, objDto.getFromDate().trim());
		} else {
			proc.setNull(2, java.sql.Types.VARCHAR);
		}				
		if (objDto.getToDate() != null && !"".equals(objDto.getToDate())) {
			SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
			Date dateStr1 = formatter1.parse(objDto.getToDate());
			String formattedDate1 = formatter1.format(dateStr1);
			Date date2 = formatter1.parse(formattedDate1);
			formatter1 = new SimpleDateFormat("MM-dd-yyyy");
			formattedDate1 = formatter1.format(date2);
			proc.setString(3,formattedDate1);
		} else {
			proc.setNull(3, java.sql.Types.VARCHAR);
		}
		
		if (objDto.getVerticalDetails() != null && !"".equals(objDto.getVerticalDetails())) {
			proc.setString(4, objDto.getVerticalDetails().trim());
		} else {
			proc.setNull(4, java.sql.Types.VARCHAR);
		}
		

		PagingSorting pagingSorting = objDto.getPagingSorting();
		pagingSorting.sync();// To calculate start index and Enc Index

		proc.setString(5, pagingSorting.getSortByColumn());// columnName
		proc.setString(6, PagingSorting.DB_Asc_Desc(pagingSorting
				.getSortByOrder()));// sort order
		proc.setInt(7, pagingSorting.getStartRecordId());// start index
		proc.setInt(8, pagingSorting.getEndRecordId());// end index
		proc.setInt(9, (pagingSorting.isPagingToBeDone() ? 1 : 0));// end
		// index
		proc.setInt(10, (objDto.getIsUsage()));// end
		if (objDto.getOsp() != null && !"".equals(objDto.getOsp())) {
			proc.setString(11, objDto.getOsp().trim());
		} else {
			proc.setNull(11, java.sql.Types.VARCHAR);
		}
		
		if (objDto.getFromCopcApprovedDate() != null
				&& !"".equals(objDto.getFromCopcApprovedDate())) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date dateStr = formatter.parse(objDto.getFromCopcApprovedDate());
			String formattedDate = formatter.format(dateStr);
			Date date1 = formatter.parse(formattedDate);
			formatter = new SimpleDateFormat("MM-dd-yyyy");
			formattedDate = formatter.format(date1);
			proc.setString(12,formattedDate);
			//proc.setString(2, objDto.getFromDate().trim());
		} else {
			proc.setNull(12, java.sql.Types.VARCHAR);
		}				
		if (objDto.getToCopcApprovedDate() != null && !"".equals(objDto.getToCopcApprovedDate())) {
			SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
			Date dateStr1 = formatter1.parse(objDto.getToCopcApprovedDate());
			String formattedDate1 = formatter1.format(dateStr1);
			Date date2 = formatter1.parse(formattedDate1);
			formatter1 = new SimpleDateFormat("MM-dd-yyyy");
			formattedDate1 = formatter1.format(date2);
			proc.setString(13,formattedDate1);
		} else {
			proc.setNull(13, java.sql.Types.VARCHAR);
		}
		rs = proc.executeQuery();
		int countFlag = 0;
		while (rs.next() != false) {
			countFlag++;
			setBlank();
			replaceSeperator("PRIMARYLOCATION",rs.getString("PRIMARYLOCATION"));
			replaceSeperator("SECONDARYLOCATION",rs.getString("SECONDARYLOCATION"));

			objRDto = new OrderReportNewDetailCwnDTO();
				objRDto.setPartyName(rs.getString("PARTYNAME"));
			objRDto.setOrderNo(rs.getString("ORDERNO"));			
			//objRDto.setLogicalCircuitId(rs.getString("LOGICALCKTID"));LOGICAL_SI_NO(Commented By :AKS)
			//objRDto.setServiceId(rs.getInt("SERVICENO"));SERVICEID(Commented By :AKS)
			objRDto.setQuoteNo(rs.getString("QUOTENO"));
			objRDto.setProductName(rs.getString("PRODUCTNAME"));
			objRDto.setSubProductName(rs.getString("SUBPRODUCTNAME"));
//			objRDto.setPrimarylocation(rs.getString("FROM_SITE"));//AKS:Need To Add in Component View:PRIMARYLOCATION
//			objRDto.setSeclocation(rs.getString("TO_SITE"));//AKS:Need To Add in Component View:SECONDARYLOCATION
			
			//objRDto.setPrjmngname(rs.getString("PMNAME"));(Commented By :AKS)PROJECTMANAGER
			objRDto.setPrjmgremail(rs.getString("PMEMAIL"));//PMEMAIL:same:(AKS)Need To Add in Component View
			//objRDto.setActmngname(rs.getString("ACTMNAME"));ACCOUNTMANAGER
			objRDto.setZoneName(rs.getString("ZONENNAME"));//(AKS)Need To Add in Component View
			//objRDto.setRegionName(rs.getString("REGIONNAME"));REGION
			objRDto.setVerticalDetails(rs.getString("VERTICAL_DETAILS"));
			objRDto.setAct_category(rs.getString("ACCOUNTCATEGORY_DETAILS"));
			
			objRDto.setCustPoDetailNo(rs.getString("CUSTPONUMBER"));
			//change
			tempDate=rs.getDate("ORDERDATE");
			if(tempDate!=null)
			{
				objRDto.setOrderDate((Utility.showDate_Report(tempDate)).toUpperCase());
			}
			ts=rs.getTimestamp("AM_APPROVAL_DATE");
			if (ts!=null)
			{
				tempDate=new Date(ts.getTime());
				objRDto.setAmApproveDate((Utility.showDate_Report(tempDate)).toUpperCase());
			}
			
			ts=rs.getTimestamp("PM_APPROVAL_DATE");
			if (ts!=null)
			{
				tempDate=new Date(ts.getTime());
				objRDto.setPmApproveDate((Utility.showDate_Report(tempDate)).toUpperCase());
			}
			
			ts=rs.getTimestamp("NIO_APPROVAL_DATE");
			if (ts!=null)
			{
				tempDate=new Date(ts.getTime());
				objRDto.setNio_approve_date((Utility.showDate_Report(tempDate)).toUpperCase());
			}
			objRDto.setDemo_infrastartdate(rs.getString("DEMP_INFRA_START_DATE"));
			
			objRDto.setDemo_infra_enddate(rs.getString("DEMO_INFRA_ENDDATE"));
			
			/*objRDto.setRfs_date(rs.getString("RFS_DATE"));SERVICE_RFS_DATE
			if (rs.getString("RFS_DATE") != null && !"".equals(rs.getString("RFS_DATE")))
			{
				
				objRDto.setRfs_date((utility.showDate_Report(new Date(rs.getTimestamp("RFS_DATE").getTime()))).toUpperCase());
				
			}*/
			//objRDto.setOrdercategory(rs.getString("ORDERCATEGORY"));ORDERTYPE(Commented By :AKS)
			objRDto.setOrderStatus(rs.getString("STATUS"));
			
			//objRDto.setLinename(rs.getString("LINENAME"));SERVICEDETDESCRIPTION(Commented By :AKS)
			
			//objRDto.setServiceProductID(rs.getInt("LINENO"));Order_Line_Id(Commented By :AKS)
			objRDto.setServiceName(rs.getString("SERVICENAME"));
			//objRDto.setAccountID(rs.getInt("ACCOUNTID"));CRMACCOUNTNO(Commented By :AKS)
			objRDto.setLicCompanyName(rs.getString("LCOMPANYNAME"));
			//objRDto.setEntity(rs.getString("COMPANYNAME"));ENTITYNAME
			
			objRDto.setServiceType(rs.getString("SERVICETYPE"));
			objRDto.setUom(rs.getString("UOM"));
			objRDto.setBandwaidth(rs.getString("BANDWIDTH"));
			objRDto.setBilling_bandwidth(rs.getString("BILLING_BANDWIDTH"));
			objRDto.setBilling_uom(rs.getString("BILLING_BANDWIDTH_UOM"));
			objRDto.setDistance(rs.getString("DISTANCE"));
			//objRDto.setFrom_city(rs.getString("FROM_CITY"));//(AKS)Need To Add in Component View
			if (rs.getString("FROM_CITY") != null && !"".equals(rs.getString("FROM_CITY"))){
				String ss[] =rs.getString("FROM_CITY").split("~~");
				objRDto.setFrom_city(ss[8]);
			}else{
				objRDto.setFrom_city(rs.getString("FROM_CITY"));
			}
			//objRDto.setTo_city(rs.getString("TO_CITY"));//(AKS)Need To Add in Component View
			if (rs.getString("TO_CITY") != null && !"".equals(rs.getString("TO_CITY"))){
				String ss[] =rs.getString("TO_CITY").split("~~");
				objRDto.setTo_city(ss[8]);
			}else{
				objRDto.setTo_city(rs.getString("TO_CITY"));
			}
			
			objRDto.setRatio(rs.getString("RATIO"));
			objRDto.setTaxation(rs.getString("TAXATIONVALUE"));
			
			objRDto.setAccountManager(rs.getString("ACTMEMAIL"));//(AKS)Need To Add in Component View
			//objRDto.setCurrencyCode(rs.getString("CURNAME"));CURRENCYNAME
			objRDto.setOrderTotal(rs.getDouble("ORDERTOTAL"));
			//objRDto.setPoAmount(rs.getString("POAMOUNT"));TOTALPOAMOUNT
			objRDto.setBisource(rs.getString("BISOURCE"));
			objRDto.setOrderType(rs.getString("ORDERTYPE"));
			
			//objRDto.setParent_name(rs.getString("PARENTNAME"));PARENT_NAME
			objRDto.setContractPeriod(rs.getInt("CONTRACTPERIOD"));
			objRDto.setOrdersubtype(rs.getString("ORDERSUBTYPE"));
			//Saurabh : Changes to separate charge related specific column from common
			if(objDto.getIsUsage() == 0)
			{
				
				objRDto.setChargeAmount(rs.getDouble("CHARGEAMOUNT"));
				
				objRDto.setFrequencyName(rs.getString("PAYMENTTERM"));
				objRDto.setChargeTypeName(rs.getString("CYCLICNONCYCLIC"));
				
				objRDto.setSub_linename(rs.getString("ORDER_SUBLINENAME"));
				objRDto.setChargeName(rs.getString("CHARGE_NAME"));
				objDto.setChargeinfoID(rs.getString("CHARGEINFOID"));
				
				objRDto.setOldordertotal(rs.getString("OLD_ORDER_TOTAL"));
				objRDto.setOldlineamt(rs.getString("OLD_LINE_AMT"));
				objRDto.setOld_contract_period(rs.getString("OLD_CONTRACTPERIOD"));
				
				objRDto.setFactory_ckt_id(rs.getString("FACTORY_CKT_ID"));
				
				objRDto.setDispatchAddress1(rs.getString("DISPATCHADDRESS"));
				
				objRDto.setLine_desc(rs.getString("LINE_ITEM_DESC"));
				//Start: AKS- Adding Column in Chanrge Section Which are not in View
				objRDto.setCopcApproveDate(rs.getString("COPC_APPROVAL_DATE"));
				if (rs.getString("COPC_APPROVAL_DATE") != null && !"".equals(rs.getString("COPC_APPROVAL_DATE")))
				{
					objRDto.setCopcApproveDate((utility.showDate_Report(new Date(rs.getTimestamp("COPC_APPROVAL_DATE").getTime()))).toUpperCase());
				}
				objRDto.setLogicalCircuitId(rs.getString("LOGICALCKTID"));
				objRDto.setServiceId(rs.getInt("SERVICENO"));
				objRDto.setPrjmngname(rs.getString("PMNAME"));				
				objRDto.setActmngname(rs.getString("ACTMNAME"));
				objRDto.setRegionName(rs.getString("REGIONNAME"));
				objRDto.setRfs_date(rs.getString("RFS_DATE"));
				if (rs.getString("RFS_DATE") != null && !"".equals(rs.getString("RFS_DATE")))
				{
					objRDto.setRfs_date((utility.showDate_Report(new Date(rs.getTimestamp("RFS_DATE").getTime()))).toUpperCase());
				}
				objRDto.setOrdercategory(rs.getString("ORDERCATEGORY"));
				objRDto.setLinename(rs.getString("LINENAME"));
				objRDto.setServiceProductID(rs.getInt("LINENO"));
				objRDto.setAccountID(rs.getInt("ACCOUNTID"));
				objRDto.setEntity(rs.getString("COMPANYNAME"));
				objRDto.setCurrencyCode(rs.getString("CURNAME"));
				objRDto.setPoAmount(rs.getString("POAMOUNT"));
				objRDto.setParent_name(rs.getString("PARENTNAME"));
				objRDto.setPrimarylocation(rs.getString("FROM_SITE"));//AKS:Need To Add in Component View:PRIMARYLOCATION
				objRDto.setSeclocation(rs.getString("TO_SITE"));//AKS:Need To Add in Component View:SECONDARYLOCATION
				objRDto.setCustPoDate(rs.getString("CUST_PODATE"));
				if (rs.getString("CUST_PODATE") != null && !"".equals(rs.getString("CUST_PODATE")))
				{
					
					objRDto.setCustPoDate((utility.showDate_Report(new Date(rs.getTimestamp("CUST_PODATE").getTime()))).toUpperCase());
					
				}
				//End AKS
			}
			
			//Meenakshi : Changes for Usage
			if(objDto.getIsUsage() == 1)
			{
				
				objRDto.setFx_external_acc_id(rs.getString("Child_Account_Number"));//FX_ACCOUNT_EXTERNAL_ID
				objRDto.setFxInternalId(rs.getInt("INTERNAL_ID"));
				objRDto.setChild_account_creation_status(rs.getString("Child_Account_FX_Sataus"));//CHILD_ACCOUNT_FX_STATUS
				objRDto.setPackageID(rs.getInt("PACKAGE_ID"));
				objRDto.setPackageName(rs.getString("PACKAGE_NAME"));
				objRDto.setComponentID(rs.getInt("COMPONENT_ID"));
				objRDto.setComponentName(rs.getString("COMPONENT_NAME"));
				objRDto.setComponentInfoID(rs.getInt("COMPONENTINFOID"));
				//Start Adding Column in Component Section Which are present in View
				ts=rs.getTimestamp("COPC_APPROVED_DATE");
				if(ts!=null)
				{
					tempDate=new Date(ts.getTime());
					objRDto.setCopcApproveDate((Utility.showDate_Report(tempDate)).toUpperCase());
				}
				objRDto.setLogicalCircuitId(rs.getString("LOGICAL_SI_NO"));
				objRDto.setServiceId(rs.getInt("SERVICE_NO"));
				objRDto.setPrjmngname(rs.getString("PROJECTMANAGER"));				
				objRDto.setActmngname(rs.getString("ACCOUNTMANAGER"));
				objRDto.setRegionName(rs.getString("REGION"));
				tempDate=rs.getDate("SERVICE_RFS_DATE");
				if(tempDate!=null)
				{
					objRDto.setRfs_date((Utility.showDate_Report(tempDate)).toUpperCase());
				}
				objRDto.setOrdercategory(rs.getString("ORDERTYPE"));
				objRDto.setLinename(rs.getString("SERVICEDETDESCRIPTION"));
				objRDto.setServiceProductID(rs.getInt("Order_Line_Id"));
				objRDto.setAccountID(rs.getInt("CRMACCOUNTNO"));
				objRDto.setEntity(rs.getString("ENTITYNAME"));
				objRDto.setCurrencyCode(rs.getString("CURRENCYNAME"));
				objRDto.setPoAmount(rs.getString("TOTALPOAMOUNT"));
				objRDto.setParent_name(rs.getString("PARENT_NAME"));
				objRDto.setPrimarylocation(VAR_PRIMARYLOCATION);//AKS:Need To Add in Component View:PRIMARYLOCATION
				objRDto.setSeclocation(VAR_SECONDARYLOCATION);//AKS:Need To Add in Component View:SECONDARYLOCATION
				objRDto.setRE_LOGGED_LSI_NO(rs.getString("RE_LOGGED_LSI_NO"));
				objRDto.setPARALLEL_UPGRADE_LSI_NO(rs.getString("PARALLEL_UPGRADE_LSI_NO"));
				objRDto.setCHARGEDISCONNECTIONSTATUS(rs.getString("CHARGEDISCONNECTIONSTATUS"));
				objRDto.setSubchange_type(rs.getString("NAME_SUBTYPE"));
				objRDto.setFxAccountExternalId(rs.getString("CHILD_ACCOUNT_NUMBER"));
				tempDate=rs.getDate("CUSTPODATE");
				if(tempDate!=null)
				{
					objRDto.setCustPoDate((Utility.showDate_Report(tempDate)).toUpperCase());
				}
				//End : AKS
				
				ComponentsDto dto = new ComponentsDto();
				dto.setComponentType(rs.getString("COMPONENT_TYPE"));
				dto.setComponentAmount(rs.getDouble("COMP_AMOUNT"));
				dto.setComponentStatus(rs.getString("COMPONENT_STATUS"));
					//nagarjuna OB Value Usage
					objDto.setObValue(BigDecimal.valueOf((rs.getDouble("OB_VALUE"))).toPlainString());
					objDto.setObValueLastUpdateDate(rs.getString("OB_VALUE_LAST_UPDATE_DATE"));
					//nagarjuna OB Value Usage END
					//<!--GlobalDataBillingEfficiency BFR7  -->
					objRDto.setTaxExcemption_Reason(rs.getString("TAXEXCEMPTION_REASON"));
				objRDto.setLastApprovalRemarks(rs.getString("LAST_APPROVAL_REMARKS"));
					//NANCY
					objRDto.setePCNNo(rs.getString("EPCN_NO"));
					objRDto.setBillingTriggerCreateDate(rs.getString("BILLING_TRIGGER_CREATEDATE"));
					if (rs.getString("BILLING_TRIGGER_CREATEDATE") != null && !"".equals(rs.getString("BILLING_TRIGGER_CREATEDATE")))
					{
						objRDto.setBillingTriggerCreateDate((utility.showDate_Report(new Date(rs.getTimestamp("BILLING_TRIGGER_CREATEDATE").getTime()))).toUpperCase());
					}
					objRDto.setComponentDto(dto);
				}

			objRDto.setOsp(rs.getString("OSP"));
			objRDto.setCustSeg_Description(rs.getString("DESCRIPTION"));
			/// End
			objRDto.setOpportunityId((rs.getString("OPPORTUNITYID")));
			if (pagingSorting.isPagingToBeDone()) {
				 recordCount = rs.getInt("FULL_REC_COUNT");
				//recordCount = countFlag;
			}
			objUserList.add(objRDto);
		}
		pagingSorting.setRecordCount(recordCount);
	} catch (Exception ex) {
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		//ex.printStackTrace();
		//throw new Exception("SQL Exception : " + ex.getMessage(), ex);
	} finally {
		try {
			DbConnection.freeConnection(conn);

		} catch (Exception e) {
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			//e.printStackTrace();
			//throw new Exception("Exception : " + e.getMessage(), e);
		}
	}

	return objUserList;
}

/**
 * 
 * @param objDto
 * @return
 * @throws Exception
 */
public ArrayList<NewOrderDto> viewOrderReportChange(NewOrderDto objDto) throws Exception {
	//Nagarjuna
	String methodName="viewOrderReportChange", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
		ArrayList<NewOrderDto> objUserList = new ArrayList<NewOrderDto>();
		Connection conn = null;
		CallableStatement proc = null;
		ResultSet rs = null;
		int recordCount = 0;
		Utility utility=new Utility();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try {

			conn = DbConnection.getReportsConnectionObject();

			proc = conn.prepareCall(sqlOrderReportChange);
			
			if (objDto.getOrderType() != null
					&& !"".equals(objDto.getOrderType())) {
				proc.setString(1, objDto.getOrderType().trim());
			} else {
				proc.setNull(1, java.sql.Types.VARCHAR);
			}
			if (objDto.getFromDate() != null
					&& !"".equals(objDto.getFromDate())) {
				proc.setString(2, objDto.getFromDate().trim());
			} else {
				proc.setNull(2, java.sql.Types.VARCHAR);
			}
			if (objDto.getToDate() != null && !"".equals(objDto.getToDate())) {
				proc.setString(3, objDto.getToDate().trim());
			} else {
				proc.setNull(3, java.sql.Types.VARCHAR);
			}
			//Company Name
			proc.setNull(4, java.sql.Types.VARCHAR);
			// Logical Si no
			if (objDto.getLogicalSINo() != null && !"".equals(objDto.getLogicalSINo())) {
				proc.setLong(5, new Long(objDto.getLogicalSINo().trim()));
			} else {
				proc.setNull(5, java.sql.Types.BIGINT);
			}
			
			if (objDto.getFromAccountNo() != 0 && objDto.getToAccountNo() !=0 ) {
				proc.setLong(6,objDto.getFromAccountNo());
				proc.setLong(7,objDto.getToAccountNo());
			} else {
				proc.setNull(6, java.sql.Types.BIGINT);
				proc.setNull(7, java.sql.Types.BIGINT);
			}
			
			if (objDto.getFromOrderNo() != 0 && objDto.getToOrderNo() !=0 ) {
				proc.setLong(8,objDto.getFromOrderNo());
				proc.setLong(9,objDto.getToOrderNo());
			} else {
				proc.setNull(8, java.sql.Types.BIGINT);
				proc.setNull(9, java.sql.Types.BIGINT);
			}
			
			
			PagingSorting pagingSorting = objDto.getPagingSorting();
			pagingSorting.sync();// To calculate start index and Enc Index

			proc.setString(10, pagingSorting.getSortByColumn());// columnName
			proc.setString(11, PagingSorting.DB_Asc_Desc(pagingSorting
					.getSortByOrder()));// sort order
			proc.setInt(12, pagingSorting.getStartRecordId());// start index
			proc.setInt(13, pagingSorting.getEndRecordId());// end index
			proc.setInt(14, (pagingSorting.isPagingToBeDone() ? 1 : 0));// end
			// index
			if (objDto.getOsp() != null && !"".equals(objDto.getOsp())) {
				proc.setString(15, objDto.getOsp().trim());
			} else {
				proc.setNull(15, java.sql.Types.VARCHAR);
			}
			
			
			rs = proc.executeQuery();
			int countFlag = 0;
			while (rs.next() != false) {
				countFlag++;

				objDto = new NewOrderDto();
				objDto.setOrderNumber(rs.getInt("ORDERNO"));
				objDto.setOrderType(rs.getString("ORDERTYPE"));
				objDto.setRegionName(rs.getString("REGIONNAME"));
				objDto.setCurrencyCode(rs.getString("CURNAME"));
				objDto.setLogicalSINo(rs.getString("LOGICAL_SI_NO"));
				objDto.setServiceSubTypeName(rs.getString("SERVICESTAGE"));
				objDto.setServiceId(rs.getInt("SERVICEID"));
				objDto.setServiceproductid(rs.getInt("SERVICEPRODUCTID"));
				objDto.setLOC_Date(rs.getString("LOCDATE"));
				if (rs.getString("LOCDATE") != null && !"".equals(rs.getString("LOCDATE")))
				{
					
					Date date=df.parse(objDto.getLOC_Date());
					objDto.setLOC_Date((utility.showDate_Report(date)).toUpperCase());
					
				}
				objDto.setAccountName(rs.getString("ACCOUNTNAME"));
				objDto.setAccountID(rs.getInt("ACCOUNTID"));
				objDto.setPoAmount(rs.getString("POAMOUNT"));
				objDto.setContractPeriod(rs.getInt("CONTRACTPERIOD"));
				objDto.setLicCompanyName(rs.getString("LCOMPANYNAME"));
				objDto.setFrequencyName(rs.getString("FREQUENCYNAME"));
				objDto.setCopcApproveDate(rs.getString("COPC_APPROVAL_DATE"));
				if (rs.getString("COPC_APPROVAL_DATE") != null && !"".equals(rs.getString("COPC_APPROVAL_DATE")))
				{
					objDto.setCopcApproveDate((utility.showDate_Report(new Date(rs.getTimestamp("COPC_APPROVAL_DATE").getTime()))).toUpperCase());
				}
				objDto.setAmApproveDate(rs.getString("AM_RECEIVE_DATE"));

				objDto.setOsp(rs.getString("OSP"));

				if (rs.getString("AM_RECEIVE_DATE") != null && !"".equals(rs.getString("AM_RECEIVE_DATE")))
				{
					objDto.setAmApproveDate((utility.showDate_Report(new Date(rs.getTimestamp("AM_RECEIVE_DATE").getTime()))).toUpperCase());
				}
				if (pagingSorting.isPagingToBeDone()) {
					 recordCount = rs.getInt("FULL_REC_COUNT");
					//recordCount = countFlag;
				}
				objUserList.add(objDto);
			}
			pagingSorting.setRecordCount(recordCount);
		} catch (Exception ex) {
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			//ex.printStackTrace();
			//throw new Exception("SQL Exception : " + ex.getMessage(), ex);
		} finally {
			try {
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(proc);
				DbConnection.freeConnection(conn);

			} catch (Exception e) {
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
				//e.printStackTrace();
				//throw new Exception("Exception : " + e.getMessage(), e);
			}
		}

		return objUserList;
}


/**
 * 
 * @param objDto
 * @return
 * @throws Exception
 */
public ArrayList<ReportsDto> viewOrderReportDetails(ReportsDto objDto) throws Exception {
	//Nagarjuna
	String methodName="viewOrderReportDetails", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna

	ArrayList<ReportsDto> objUserList = new ArrayList<ReportsDto>();
	Connection conn = null;
	CallableStatement proc = null;
	ResultSet rs = null;
	int recordCount = 0;
	Utility utility=new Utility();
	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	try {

		conn = DbConnection.getReportsConnectionObject();

		proc = conn.prepareCall(sqlOrderReportDetail);
		
		if (objDto.getOrderType() != null
				&& !"".equals(objDto.getOrderType())) {
			proc.setString(1, objDto.getOrderType().trim());
		} else {
			proc.setNull(1, java.sql.Types.VARCHAR);
		}
		if (objDto.getFromDate() != null
				&& !"".equals(objDto.getFromDate())) {
			proc.setString(2, objDto.getFromDate().trim());
		} else {
			proc.setNull(2, java.sql.Types.VARCHAR);
		}
		if (objDto.getToDate() != null && !"".equals(objDto.getToDate())) {
			proc.setString(3, objDto.getToDate().trim());
		} else {
			proc.setNull(3, java.sql.Types.VARCHAR);
		}
		//Company Name
		//proc.setNull(4, java.sql.Types.VARCHAR);
		// Logical Si no
		if (objDto.getDemo() != null && !"".equals(objDto.getDemo())) {
			proc.setString(4, objDto.getDemo().trim());
		} else {
			proc.setNull(4, java.sql.Types.VARCHAR);
		}
		
		if (objDto.getFromAccountNo() != 0 && objDto.getToAccountNo() !=0 ) {
			proc.setLong(5,objDto.getFromAccountNo());
			proc.setLong(6,objDto.getToAccountNo());
		} else {
			proc.setNull(5, java.sql.Types.BIGINT);
			proc.setNull(6, java.sql.Types.BIGINT);
		}
		
		if (objDto.getFromOrderNo() != 0 && objDto.getToOrderNo() !=0 ) {
			proc.setLong(7,objDto.getFromOrderNo());
			proc.setLong(8,objDto.getToOrderNo());
		} else {
			proc.setNull(7, java.sql.Types.BIGINT);
			proc.setNull(8, java.sql.Types.BIGINT);
		}
		
		
		PagingSorting pagingSorting = objDto.getPagingSorting();
		pagingSorting.sync();// To calculate start index and Enc Index

		proc.setString(9, pagingSorting.getSortByColumn());// columnName
		proc.setString(10, PagingSorting.DB_Asc_Desc(pagingSorting
				.getSortByOrder()));// sort order
		proc.setInt(11, pagingSorting.getStartRecordId());// start index
		proc.setInt(12, pagingSorting.getEndRecordId());// end index
		proc.setInt(13, (pagingSorting.isPagingToBeDone() ? 1 : 0));// end
		proc.setInt(14,objDto.getIsUsage());
		// index
	if (objDto.getOsp() != null && !"".equals(objDto.getOsp())) {
			proc.setString(15, objDto.getOsp().trim());
		} else {
			proc.setNull(15, java.sql.Types.VARCHAR);
		}

		rs = proc.executeQuery();
		int countFlag = 0;
		while (rs.next() != false) {
			countFlag++;

			objDto = new ReportsDto();
			objDto.setAccountID(rs.getInt("ACCOUNTID"));
			objDto.setAccountManager(rs.getString("ACCOUNTMANAGER"));
			objDto.setCrmACcountNO(rs.getString("CRMACCOUNTNO"));
			objDto.setPoAmounts(rs.getDouble("POAMOUNT"));
			objDto.setCus_segment(rs.getString("CUST_SEGMENT_CODE"));
			objDto.setAct_category(rs.getString("ACCOUNTCATEGORY_DETAILS"));
			objDto.setVerticalDetails(rs.getString("VERTICAL_DETAILS"));
			objDto.setBillingPODate(rs.getString("START_DATE"));
			if (rs.getString("START_DATE") != null && !"".equals(rs.getString("START_DATE"))) 
			{
				
				Date date=df.parse(objDto.getBillingPODate());
				objDto.setBillingPODate((utility.showDate_Report(date)).toUpperCase());
				
			}
			objDto.setServiceDetDescription(rs.getString("SERVICESTAGE"));
			objDto.setOrderLineNumber(rs.getInt("SERVICEPRODUCTID"));
			objDto.setLinename(rs.getString("SERVICEDETDESCRIPTION"));
			objDto.setCancelflag(rs.getString("CANCELFLAG"));
			objDto.setProvisionBandWidth(rs.getString("BANDWIDTH"));
			objDto.setUom(rs.getString("UOM"));
			objDto.setBillingBandWidth(rs.getString("BILLING_BANDWIDTH"));
			objDto.setStoreName(rs.getString("STORENAME"));
			objDto.setBillUom(rs.getString("BILLING_BANDWIDTH_UOM"));
			objDto.setCategoryOfOrder(rs.getString("ORDERCATEGORY"));
			objDto.setContractPeriod(rs.getInt("CONTRACTPERIOD"));
			objDto.setCompanyName(rs.getString("COMPANYNAME"));
			objDto.setOrderDate(rs.getString("ORDERCREATIONDATE"));
			if (rs.getString("ORDERCREATIONDATE") != null && !"".equals(rs.getString("ORDERCREATIONDATE"))) 
			{
				
				Date date=df.parse(objDto.getOrderDate());
				objDto.setOrderDate((utility.showDate_Report(date)).toUpperCase().toString());
				
			}
			objDto.setCustomerRfsDate(rs.getString("RFS_DATE"));
			if (rs.getString("RFS_DATE") != null && !"".equals(rs.getString("RFS_DATE"))) 
			{
				
				Date date=df.parse(objDto.getCustomerRfsDate());
				objDto.setCustomerRfsDate((utility.showDate_Report(date)).toUpperCase().toString());
				
			}
			objDto.setCustomerServiceRfsDate(rs.getString("SERVICE_RFS_DATE"));
			if (rs.getString("SERVICE_RFS_DATE") != null && !"".equals(rs.getString("SERVICE_RFS_DATE"))) 
			{
				
				Date date=df.parse(objDto.getCustomerServiceRfsDate());
				objDto.setCustomerServiceRfsDate((utility.showDate_Report(date)).toUpperCase().toString());
				
			}
			objDto.setCurrencyCode(rs.getString("CURRENCYNAME")); 
			objDto.setChargeName(rs.getString("CHARGE_NAME"));
			objDto.setCustomerPoDate(rs.getString("PODATE"));
			if (rs.getString("PODATE") != null && !"".equals(rs.getString("PODATE"))) 
			{
				
				Date date=df.parse(objDto.getCustomerPoDate());
				objDto.setCustomerPoDate((utility.showDate_Report(date)).toUpperCase().toString());
				
			}
			objDto.setCustomerPoNumber(rs.getString("CUSTPONUMBER"));
			objDto.setCyclicNonCyclic(rs.getString("CYCLIC_NONCYCLIC"));
			objDto.setChallenno(rs.getString("CHALLEN_NO"));
			objDto.setOrderNumber(rs.getInt("ORDERNO"));
			objDto.setFromSite(rs.getString("PRIMARYLOCATION")); 
			objDto.setToSite(rs.getString("SECONDARYLOCATION")); 
			objDto.setItemQuantity(1);
			objDto.setKmsDistance(rs.getString("DISTANCE")); 
			objDto.setChargeAmount(rs.getDouble("LINEITEMAMOUNT"));
			objDto.setCopcApproveDate(rs.getString("COPC_APPROVAL_DATE"));
			if (rs.getString("COPC_APPROVAL_DATE") != null && !"".equals(rs.getString("COPC_APPROVAL_DATE"))) 
			{
				objDto.setCopcApproveDate((utility.showDate_Report(new Date(rs.getTimestamp("COPC_APPROVAL_DATE").getTime()))).toUpperCase());
			}
			objDto.setLineItemDescription(rs.getString("SERVICEDETDESCRIPTION")); 
			objDto.setLocDate(rs.getString("LOCDATE")); 
			if (rs.getString("LOCDATE") != null && !"".equals(rs.getString("LOCDATE"))) 
			{
				
				Date date=df.parse(objDto.getLocDate());
				objDto.setLocDate((utility.showDate_Report(date)).toUpperCase().toString());
				
			}
			objDto.setAmReceiveDate(rs.getString("AMRECEIVEDATE")); 
			if (rs.getString("AMRECEIVEDATE") != null && !"".equals(rs.getString("AMRECEIVEDATE"))) 
			{
				
				Date date=df.parse(objDto.getAmReceiveDate());
				objDto.setAmReceiveDate((utility.showDate_Report(date)).toUpperCase().toString());
				
			}
			objDto.setOrderTotal(rs.getDouble("POAMOUNT"));
			objDto.setOrderEntryDate(rs.getString("ORDERCREATIONDATE"));
			if (rs.getString("ORDERCREATIONDATE") != null && !"".equals(rs.getString("ORDERCREATIONDATE"))) 
			{
				
				Date date=df.parse(objDto.getOrderEntryDate());
				objDto.setOrderEntryDate((utility.showDate_Report(date)).toUpperCase().toString());
				
			}
			
			objDto.setTaxation(rs.getString("TAXATIONVALUE"));
			objDto.setLicenceCoName(rs.getString("LCOMPANYNAME"));
			objDto.setLogicalCircuitNumber(rs.getString("LOGICAL_CIRCUITID"));
			objDto.setOrderType(rs.getString("ORDERCATEGORY"));
			objDto.setPaymentTerm(rs.getString("PAYMENTTERM"));
			objDto.setProjectManager(rs.getString("PROJECTMANAGER"));
			objDto.setRegionName(rs.getString("REGIONNAME"));
			objDto.setOldLineitemAmount("");
			objDto.setDemoType(rs.getString("DEMO_TYPE")); 
			objDto.setPartyName(rs.getString("PARTYNAME")); 
			objDto.setOrderStageDescription(rs.getString("STAGE")); 
			objDto.setServiceStageDescription(rs.getString("SERVICE_STAGE"));
			objDto.setChargeEndDate(rs.getString("END_DATE"));
			if (rs.getString("END_DATE") != null && !"".equals(rs.getString("END_DATE"))) 
			{
				
				Date date=df.parse(objDto.getChargeEndDate());
				objDto.setChargeEndDate((utility.showDate_Report(date)).toUpperCase().toString());
				
			}
			objDto.setEndDateLogic(rs.getString("ENDDATELOGIC"));
			objDto.setNewOrderRemark(rs.getString("NEWORDER_REMARKS"));
			objDto.setRemarks(rs.getString("REMARKS"));
			objDto.setServiceOrderType(rs.getString("SERVICE_ORDER_TYPE"));
			objDto.setOsp(rs.getString("OSP"));
			if(objDto.getIsUsage() == 1)
			{
				objDto.setComponentInfoID(rs.getInt("COMPONENTINFOID"));
				objDto.setComponentID(rs.getInt("COMPONENT_ID"));
				objDto.setComponentName(rs.getString("COMPONENT_NAME"));
				objDto.setPackageID(rs.getInt("PACKAGE_ID"));
				objDto.setPackageName(rs.getString("PACKAGE_NAME"));
		   }
		
			
			
			if (pagingSorting.isPagingToBeDone()) {
				 recordCount = rs.getInt("FULL_REC_COUNT");
				//recordCount = countFlag;
			}
			objUserList.add(objDto);
		}
		pagingSorting.setRecordCount(recordCount);
	} catch (Exception ex) {
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		//ex.printStackTrace();
		//throw new Exception("SQL Exception : " + ex.getMessage(), ex);
	} finally {
		try {
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(proc);
			DbConnection.freeConnection(conn);

		} catch (Exception e) {
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			//e.printStackTrace();
			//throw new Exception("Exception : " + e.getMessage(), e);
		}
	}

	return objUserList;
	}
	
	// Saurabh
public ArrayList<PendingOrderAndBillingReportDTO> viewPendingOrderAndBillingList(PendingOrderAndBillingReportDTO objDto)
throws Exception {
	//Nagarjuna
	String methodName="viewPendingOrderAndBillingList", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
		ArrayList<PendingOrderAndBillingReportDTO> objUserList = new ArrayList<PendingOrderAndBillingReportDTO>();
		Connection conn = null;
		CallableStatement proc = null;
		ResultSet rs = null;
		int recordCount = 0;
		PendingOrderAndBillingReportDTO objRDto;
		Utility utility=new Utility();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date tempDate=null;
		
		try {
		
		conn = DbConnection.getReportsConnectionObject();
		
 		proc = conn.prepareCall(sqlPendingOrderAndBillingReport);
		if (objDto.getOrderType() != null
				&& !"".equals(objDto.getOrderType())) {
			proc.setString(1, objDto.getOrderType().trim());
		} 
		else 
		{
			proc.setNull(1, java.sql.Types.VARCHAR);
		}
		if (objDto.getLOC_Date() !=null
				&& !"".equals(objDto.getLOC_Date())) {
			proc.setString(2, objDto.getLOC_Date());
		} 
		else 
		{
			proc.setNull(2, java.sql.Types.VARCHAR);
		}
		if (objDto.getFromAccountNo() !=0
				&& !"".equals(objDto.getFromAccountNo())) {
			proc.setInt(3, objDto.getFromAccountNo());
		} 
		else 
		{
			proc.setNull(3, java.sql.Types.BIGINT);
		}
		if (objDto.getToAccountNo() !=0
				&& !"".equals(objDto.getToAccountNo())) {
			proc.setInt(4, objDto.getToAccountNo());
		} 
		else 
		{
			proc.setNull(4, java.sql.Types.BIGINT);
		}
		if (objDto.getFromCopcApprovedDate() != null
				&& !"".equals(objDto.getFromCopcApprovedDate())) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date dateStr = formatter.parse(objDto.getFromCopcApprovedDate());
			String formattedDate = formatter.format(dateStr);
			Date date1 = formatter.parse(formattedDate);
			formatter = new SimpleDateFormat("MM-dd-yyyy");
			formattedDate = formatter.format(date1);
			proc.setString(5,formattedDate);
		} else {
			proc.setNull(5, java.sql.Types.VARCHAR);
		}
		if (objDto.getToCopcApprovedDate() != null && !"".equals(objDto.getToCopcApprovedDate())) {
			SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
			Date dateStr1 = formatter1.parse(objDto.getToCopcApprovedDate());
			String formattedDate1 = formatter1.format(dateStr1);
			Date date2 = formatter1.parse(formattedDate1);
			formatter1 = new SimpleDateFormat("MM-dd-yyyy");
			formattedDate1 = formatter1.format(date2);
			proc.setString(6,formattedDate1);
		} else {
			proc.setNull(6, java.sql.Types.VARCHAR);
		}

		
		if (objDto.getFromOrderNo() !=0
				&& !"".equals(objDto.getFromOrderNo())) {
			proc.setInt(7, objDto.getFromOrderNo());
		} 
		else 
		{
			proc.setNull(7, java.sql.Types.BIGINT);
		}
		if (objDto.getToOrderNo() !=0
				&& !"".equals(objDto.getToOrderNo())) {
			proc.setInt(8, objDto.getToOrderNo());
		} 
		else 
		{
			proc.setNull(8, java.sql.Types.BIGINT);
		}
		
		
		if (objDto.getActmngname() != null 
				&& !"".equals(objDto.getActmngname())) {
			proc.setString(9, objDto.getActmngname().trim());
		} else {
			proc.setNull(9, java.sql.Types.VARCHAR);
		}
		
		PagingSorting pagingSorting = objDto.getPagingSorting();
		pagingSorting.sync();// To calculate start index and Enc Index
		
		proc.setString(10, pagingSorting.getSortByColumn());// columnName
		proc.setString(11, PagingSorting.DB_Asc_Desc(pagingSorting
				.getSortByOrder()));// sort order
		proc.setInt(12, pagingSorting.getStartRecordId());// start index
		proc.setInt(13, pagingSorting.getEndRecordId());// end index
		proc.setInt(14, (pagingSorting.isPagingToBeDone() ? 1 : 0));// end
		// index
		proc.setInt(15, objDto.getIsUsage());// end
		if (objDto.getOsp() != null && !"".equals(objDto.getOsp())) {
			proc.setString(16, objDto.getOsp().trim());
		} else {
			proc.setNull(16, java.sql.Types.VARCHAR);
		}
		rs = proc.executeQuery();
		int countFlag = 0;
		while (rs.next() != false) {
			countFlag++;
		
			objRDto = new PendingOrderAndBillingReportDTO();
			objRDto.setCrmAccountNo(rs.getInt("CRMACCOUNTNO"));			
			objRDto.setCus_segment(rs.getString("CUST_SEGMENT_CODE"));
			objRDto.setAct_category(rs.getString("ACCOUNTCATEGORY_DETAILS"));	
			objRDto.setOrderNumber(rs.getInt("ORDERNO"));			
			objRDto.setContractPeriod(rs.getInt("CONTRACTPERIOD"));
			objRDto.setCustPoDate(rs.getString("CUSTPODATE"));
			/*if (rs.getString("CUSTPODATE") != null && !"".equals(rs.getString("CUSTPODATE")))
			{				
				Date date=df.parse(objRDto.getCustPoDate());
				objRDto.setCustPoDate((Utility.showDate_Report(date)).toUpperCase());			
			}*/
			tempDate=rs.getDate("CUSTPODATE");
			if (tempDate != null && !"".equals(tempDate))
			{
				objRDto.setCustPoDate((utility.showDate_Report(tempDate)).toUpperCase());
			}			

			objRDto.setCustPoDetailNo(rs.getString("CUSTPONUMBER"));	
			objRDto.setM6OrderNo(rs.getInt("M6ORDERNO"));		
			objRDto.setCurrencyName(rs.getString("CURRENCYNAME"));									
			objRDto.setOrderType(rs.getString("ORDERTYPE"));			
			objRDto.setLOC_Date(rs.getString("LOCDATE"));			
			if (rs.getString("LOCDATE") != null && !"".equals(rs.getString("LOCDATE")))
			{				
				Date date=df.parse(objRDto.getLOC_Date());
				objRDto.setLOC_Date((Utility.showDate_Report(date)).toUpperCase());			
			}
			objRDto.setPm_pro_date(rs.getString("PM_PROV_DATE"));
			if (rs.getString("PM_PROV_DATE") != null && !"".equals(rs.getString("PM_PROV_DATE")))
			{
			  String s1=rs.getString("PM_PROV_DATE");
			  String s3=s1.substring(0,7).toUpperCase();
			  String s4=s1.substring(9,11);
			  String s5=s3.concat(s4);
			  objRDto.setPm_pro_date(s5);
			}			
			tempDate=rs.getDate("ORDERDATE");
			objRDto.setOrderDate(rs.getString("ORDERDATE"));
			if (tempDate != null && !"".equals(tempDate))
			{
				objRDto.setOrderDate((utility.showDate_Report(tempDate)).toUpperCase());
			}			
			objRDto.setPoNumber(rs.getInt("PONUMBER"));		
		    objRDto.setUom(rs.getString("UOM"));
			objRDto.setBandwaidth(rs.getString("BANDWIDTH"));
			objRDto.setBilling_bandwidth(rs.getString("BILLING_BANDWIDTH"));
			objRDto.setBilling_uom(rs.getString("BILLING_BANDWIDTH_UOM"));
			objRDto.setVerticalDetails(rs.getString("VERTICAL_DETAILS"));
			
//			 Meenakshi : Changes for Usage
			if(objDto.getIsUsage() == 1)
			{
				objRDto.setRegionName(rs.getString("REGION"));
				objRDto.setAccountID(rs.getInt("CUSTACCOUNTID"));
				objRDto.setLogicalSINo(rs.getString("LOGICAL_SI_NO"));
				objRDto.setLinename(rs.getString("SERVICEDETDESCRIPTION"));
				objRDto.setLcompanyname(rs.getString("LCOMPANYNAME"));
				objRDto.setLine_desc(rs.getString("SERVICEDETDESCRIPTION"));
				objRDto.setActmngname(rs.getString("ACCOUNTMANAGER"));
				objRDto.setPrjmngname(rs.getString("PROJECTMANAGER"));
				objRDto.setCopcApproveDate(rs.getString("COPC_APPROVED_DATE"));				
				if (rs.getString("COPC_APPROVED_DATE") != null && !"".equals(rs.getString("COPC_APPROVED_DATE")))
				{
					objRDto.setCopcApproveDate((Utility.showDate_Report(new Date(rs.getTimestamp("COPC_APPROVED_DATE").getTime()))).toUpperCase());
				}				
				objRDto.setTaxation(rs.getString("TAXATIONVALUE"));
								
				objRDto.setComponentInfoID(rs.getInt("COMPONENTINFOID"));
				objRDto.setComponentID(rs.getInt("COMPONENT_ID"));
				objRDto.setComponentName(rs.getString("COMPONENT_NAME"));
				objRDto.setComponentType(rs.getString("COMPONENT_TYPE"));
				objRDto.setComponentRCNRCAmount(rs.getDouble("COMP_AMOUNT"));
				objRDto.setPackageID(rs.getInt("PACKAGE_ID"));
				objRDto.setPackageName(rs.getString("PACKAGE_NAME"));
				objRDto.setChild_act_no(rs.getString("Child_Account_Number"));
				
				
			}else{
				objRDto.setRegionName(rs.getString("REGIONNAME"));
				objRDto.setAccountID(rs.getInt("ACCOUNTID"));
				objRDto.setCrm_productname(rs.getString("CRM_PRODUCT_NAME"));
				objRDto.setLogicalCircuitId(rs.getString("LOGICALCKTID"));
				objRDto.setCompanyName(rs.getString("COMPANYNAME"));
				objRDto.setFactory_ckt_id(rs.getString("FACTORY_CKT_ID"));
				objRDto.setLinename(rs.getString("LINENAME"));
				objRDto.setLcompanyname(rs.getString("LICENCECOMPANYNAME"));	
				objRDto.setStorename(rs.getString("STORENAME"));
				objRDto.setLine_desc(rs.getString("LINE_ITEM_DESC"));
				objRDto.setActmngname(rs.getString("ACTMNAME"));
				objRDto.setPrjmngname(rs.getString("PMNAME"));
				objRDto.setCopcApproveDate(rs.getString("COPC_APPROVAL_DATE"));
				if (rs.getString("COPC_APPROVAL_DATE") != null && !"".equals(rs.getString("COPC_APPROVAL_DATE")))
				{
					objRDto.setCopcApproveDate((Utility.showDate_Report(new Date(rs.getTimestamp("COPC_APPROVAL_DATE").getTime()))).toUpperCase());
				}
				objRDto.setTaxation(rs.getString("TAXATION"));
				objRDto.setOsp(rs.getString("OSP"));
				
				objRDto.setAnnual_rate(rs.getDouble("ANNUAL_RATE"));
				objRDto.setChargeAmount(rs.getDouble("CHARGEAMOUNT"));
				objRDto.setChargeName(rs.getString("CHARGE_NAME"));
				objRDto.setChargeTypeName(rs.getString("CYCLICNONCYCLIC"));				
				objRDto.setFrequencyName(rs.getString("PAYMENTTERM"));
				objRDto.setChild_act_no(rs.getString("CHILD_ACCOUNT_NO"));
			}
			
			/// End
			if (pagingSorting.isPagingToBeDone()) {
				recordCount = rs.getInt("FULL_REC_COUNT");
				//recordCount = countFlag;
			}
			objUserList.add(objRDto);
		}
		pagingSorting.setRecordCount(recordCount);
		} catch (Exception ex) {
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		//ex.printStackTrace();
		throw new Exception("SQL Exception : " + ex.getMessage(), ex);
		} finally {
		try {
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(proc);
			DbConnection.freeConnection(conn);
		
		} catch (Exception e) {
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			//e.printStackTrace();
			throw new Exception("Exception : " + e.getMessage(), e);
		}
		}
		return objUserList;
		}
//added by lawkush
 //[001]	Start
public ArrayList<ReportsDto> ViewpendingOrderBillandHardwareList(ReportsDto objDto)
throws Exception {
//	Nagarjuna
	String methodName="ViewpendingOrderBillandHardwareList", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
ArrayList<ReportsDto> objUserList = new ArrayList<ReportsDto>();
Connection conn = null;
CallableStatement proc = null;
ResultSet rs = null;
int recordCount = 0;

Utility utility=new Utility();

DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

try {

conn = DbConnection.getReportsConnectionObject();

proc = conn.prepareCall(sqlPendingOrderBillHardware);
if (objDto.getOrderType() != null
		&& !"".equals(objDto.getOrderType())) {
	proc.setString(1, objDto.getOrderType().trim());
} else {
	proc.setNull(1, java.sql.Types.VARCHAR);
}

if (objDto.getFromAccountNo() != 0
		&& !"".equals(objDto.getFromAccountNo())) {
	proc.setInt(2, objDto.getFromAccountNo());
} else {
	proc.setNull(2, java.sql.Types.BIGINT);
}
if (objDto.getToAccountNo() != 0
		&& !"".equals(objDto.getToAccountNo())) {
	proc.setInt(3, objDto.getToAccountNo());
} else {
	proc.setNull(3, java.sql.Types.BIGINT);
}
if (objDto.getFromOrderDate() != null
		&& !"".equals(objDto.getFromOrderDate())) {
	proc.setString(4, objDto.getFromOrderDate().trim());
} else {
	proc.setNull(4, java.sql.Types.VARCHAR);
}
if (objDto.getToOrderDate() != null && !"".equals(objDto.getToOrderDate())) {
	proc.setString(5, objDto.getToOrderDate().trim());
} else {
	proc.setNull(5, java.sql.Types.VARCHAR);
}
if (objDto.getFromCrmOrderid() != null
		&& !"".equals(objDto.getFromCrmOrderid())) {
	proc.setLong(6, new Long(objDto.getFromCrmOrderid().trim()));
} else {
	proc.setNull(6, java.sql.Types.BIGINT);
}
if (objDto.getToCrmOrderid() != null && !"".equals(objDto.getToCrmOrderid())) {
	proc.setLong(7, new Long(objDto.getToCrmOrderid().trim()));
} else {
	proc.setNull(7, java.sql.Types.BIGINT);
}

if (objDto.getParty() != null 
		&& !"".equals(objDto.getParty())) {
	proc.setString(8, objDto.getParty().trim());
} else {
	proc.setNull(8, java.sql.Types.VARCHAR);
}



PagingSorting pagingSorting = objDto.getPagingSorting();
pagingSorting.sync();// To calculate start index and Enc Index

proc.setString(9, pagingSorting.getSortByColumn());// columnName
proc.setString(10, PagingSorting.DB_Asc_Desc(pagingSorting
		.getSortByOrder()));// sort order
proc.setInt(11, pagingSorting.getStartRecordId());// start index
proc.setInt(12, pagingSorting.getEndRecordId());// end index
proc.setInt(13, (pagingSorting.isPagingToBeDone() ? 1 : 0));// end
// index

rs = proc.executeQuery();
int countFlag = 0;
while (rs.next() != false) {
	countFlag++;

	objDto = new ReportsDto();
	objDto.setVerticalDetails(rs.getString("VERTICAL_DETAILS"));
	objDto.setCus_segment(rs.getString("CUST_SEGMENT_CODE"));
	objDto.setAct_category(rs.getString("ACCOUNTCATEGORY_DETAILS"));
	objDto.setRegionName(rs.getString("REGIONNAME"));
	objDto.setActmngname(rs.getString("ACTMNAME"));
	objDto.setPrjmngname(rs.getString("PMNAME"));
	objDto.setAccountID(rs.getInt("CRMACCOUNTNO"));
	objDto.setCopcApproveDate(rs.getString("COPC_APPROVAL_DATE"));
	if (rs.getString("COPC_APPROVAL_DATE") != null && !"".equals(rs.getString("COPC_APPROVAL_DATE")))
		{
			objDto.setCopcApproveDate((utility.showDate_Report(new Date(rs.getTimestamp("COPC_APPROVAL_DATE").getTime()))).toUpperCase());
		}
	objDto.setOrderNo(rs.getString("ORDERNO"));
	objDto.setPoNumber(rs.getInt("PODETAILNO"));
	objDto.setServiceName(rs.getString("SERVICENAME"));
	objDto.setLogicalCircuitId(rs.getString("LOGICAL_SI_NO"));
	objDto.setFactory_ckt_id(rs.getString("FACTORY_CKT_ID"));
	objDto.setLinename(rs.getString("LINENAME"));
	objDto.setChargeAmount(rs.getDouble("CHARGEAMOUNT"));
	objDto.setStartDate(rs.getString("START_DATE"));
	if (rs.getString("START_DATE") != null && !"".equals(rs.getString("START_DATE")))
		{
		Date date=df.parse(objDto.getStartDate());
		objDto.setStartDate((utility.showDate_Report(date)).toUpperCase());
		}
	
	objDto.setChallenno(rs.getString("CHALLEN_NO"));
	objDto.setChallendate(rs.getString("CHALLEN_DATE"));
	if (rs.getString("CHALLEN_DATE") != null && !"".equals(rs.getString("CHALLEN_DATE")))
	{
	  String s1=rs.getString("CHALLEN_DATE");
	  String s3=s1.substring(0,7);
	  String s4=s1.substring(9,11);
	  String s5=s3.concat(s4);
	  objDto.setChallendate(s5);
	}

	objDto.setPartyName(rs.getString("PARTYNAME"));
	objDto.setOrderType(rs.getString("ORDERTYPE"));
	

	


	
	

	
	

	if (pagingSorting.isPagingToBeDone()) {
		recordCount = rs.getInt("FULL_REC_COUNT");
		
	}
	objUserList.add(objDto);
}
pagingSorting.setRecordCount(recordCount);
} catch (Exception ex) {
	Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
//ex.printStackTrace();
throw new Exception("SQL Exception : " + ex.getMessage(), ex);
} finally {
try {
	DbConnection.closeResultset(rs);
	DbConnection.closeCallableStatement(proc);
	DbConnection.freeConnection(conn);

} catch (Exception e) {
	Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
	//e.printStackTrace();
	throw new Exception("Exception : " + e.getMessage(), e);
}
}
return objUserList;
}


public ArrayList<ReportsDto> viewM6OrderCancelReport(ReportsDto objDto)
throws Exception {
//	Nagarjuna
	String methodName="viewM6OrderCancelReport", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
ArrayList<ReportsDto> objUserList = new ArrayList<ReportsDto>();
Connection conn = null;
CallableStatement proc = null;
ResultSet rs = null;
int recordCount = 0;
Utility utility=new Utility();

DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
try {

conn = DbConnection.getReportsConnectionObject();

proc = conn.prepareCall(sqlM6OrderCancelReport);
if (objDto.getCanceldate() != null
		&& !"".equals(objDto.getCanceldate())) {
	proc.setString(1, objDto.getCanceldate().trim());
} else {
	proc.setNull(1, java.sql.Types.VARCHAR);
}

PagingSorting pagingSorting = objDto.getPagingSorting();
pagingSorting.sync();// To calculate start index and Enc Index

proc.setString(2, pagingSorting.getSortByColumn());// columnName
proc.setString(3, PagingSorting.DB_Asc_Desc(pagingSorting
		.getSortByOrder()));// sort order
proc.setInt(4, pagingSorting.getStartRecordId());// start index
proc.setInt(5, pagingSorting.getEndRecordId());// end index
proc.setInt(6, (pagingSorting.isPagingToBeDone() ? 1 : 0));// end
// index
rs = proc.executeQuery();
int countFlag = 0;
while (rs.next() != false) {
	countFlag++;

	objDto = new ReportsDto();
	objDto.setOrderNumber(rs.getInt("ORDERNO"));
	objDto.setServiceId(rs.getInt("SERVICEID"));
	objDto.setOrderType(rs.getString("ORDERTYPE"));
	objDto.setServiceType(rs.getString("SERVICETYPE"));
	objDto.setCreatedDate(rs.getString("CREATEDDATE"));
	if (rs.getString("CREATEDDATE") != null && !"".equals(rs.getString("CREATEDDATE")))
		{
			objDto.setCreatedDate((utility.showDate_Report(new Date(rs.getTimestamp("CREATEDDATE").getTime()))).toUpperCase());
		}
	objDto.setEffDate(rs.getString("EFFSTARTDATE"));
	if (rs.getString("EFFSTARTDATE") != null && !"".equals(rs.getString("EFFSTARTDATE")))
		{
		Date date=df.parse(objDto.getEffDate());
		objDto.setEffDate((utility.showDate_Report(date)).toUpperCase());
		}
	    objDto.setRfs_date(rs.getString("RFS_DATE"));
	    if (rs.getString("RFS_DATE") != null && !"".equals(rs.getString("RFS_DATE")))
		{
		Date date=df.parse(objDto.getRfs_date());
		objDto.setRfs_date((utility.showDate_Report(date)).toUpperCase());
		}
	objDto.setProductName(rs.getString("PRODUCTNAME"));
	objDto.setSubProductName(rs.getString("SUBPRODUCTNAME"));
	objDto.setServiceStage(rs.getString("SERVICESTAGE"));
	objDto.setCrmAccountId(rs.getInt("CRMACCOUNTNO"));
	objDto.setLogicalSINo(rs.getString("LOGICAL_SI_NO"));
	objDto.setCancelServiceReason(rs.getString("CANCEL_SERVICE_REASON"));
	objDto.setOrdertype_demo(rs.getString("order_type_DEMO"));
	objDto.setBisource(rs.getString("BISOURCE"));
	objDto.setCanceldate(rs.getString("CANCEL_DATE"));
	   if (rs.getString("CANCEL_DATE") != null && !"".equals(rs.getString("CANCEL_DATE")))
		{
			objDto.setCanceldate((utility.showDate_Report(new Date(rs.getTimestamp("CANCEL_DATE").getTime()))).toUpperCase());
		}
	
	if (pagingSorting.isPagingToBeDone()) {
		recordCount = rs.getInt("FULL_REC_COUNT");
		
	}
	objUserList.add(objDto);
}
pagingSorting.setRecordCount(recordCount);
} catch (Exception ex) {
	Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
//ex.printStackTrace();
throw new Exception("SQL Exception : " + ex.getMessage(), ex);
} finally {
try {
	DbConnection.closeResultset(rs);
	DbConnection.closeCallableStatement(proc);
	DbConnection.freeConnection(conn);

} catch (Exception e) {
	Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
	//e.printStackTrace();
	throw new Exception("Exception : " + e.getMessage(), e);
}
}
return objUserList;
}


// [001]	End

public ArrayList<ReportsDto> viewPerformanceDetailList(ReportsDto objDto) 
{
//	Nagarjuna
	String methodName="viewPerformanceDetailList", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement proc =null;
	ResultSet rs = null;
	ArrayList<ReportsDto> listSearchDetails = new ArrayList<ReportsDto>();
	ReportsDto objReportsDto = null;
	int recordCount =0;
	Utility utility=new Utility();
	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	try
	{
		connection=DbConnection.getReportsConnectionObject();
		proc= connection.prepareCall(sqlGetPerformanceDetailReport);
		
		if (objDto.getOrderType() != null
				&& !"".equals(objDto.getOrderType())) {
			proc.setString(1, objDto.getOrderType().trim());
		} else {
			proc.setNull(1, java.sql.Types.VARCHAR);
		}
		
		if (objDto.getFromDate() != null
				&& !"".equals(objDto.getFromDate())) {
			proc.setString(2, objDto.getFromDate().trim());
		} else {
			proc.setNull(2, java.sql.Types.VARCHAR);
		}
		
		if (objDto.getToDate() != null 
				&& !"".equals(objDto.getToDate())) {
			proc.setString(3, objDto.getToDate().trim());
		} else {
			proc.setNull(3, java.sql.Types.VARCHAR);
		}
		
		if (objDto.getFromOrderNo() != 0 
				&& !"".equals(objDto.getFromOrderNo())) {
			proc.setInt(4, objDto.getFromOrderNo());
		} else {
			proc.setNull(4,java.sql.Types.BIGINT);
		}
		
		if (objDto.getToOrderNo() != 0 
				&& !"".equals(objDto.getToOrderNo())) {
			proc.setInt(5, objDto.getToOrderNo());
		} else {
			proc.setNull(5, java.sql.Types.BIGINT);
		}
		
		if (objDto.getFromAccountNo() != 0 
				&& !"".equals(objDto.getFromAccountNo())) {
			proc.setInt(6, objDto.getFromAccountNo());
		} else {
			proc.setNull(6,java.sql.Types.BIGINT);
		}
		
		if (objDto.getToAccountNo() != 0 
				&& !"".equals(objDto.getToAccountNo())) {
			proc.setInt(7, objDto.getToAccountNo());
		} else {
			proc.setNull(7, java.sql.Types.BIGINT);
		}

		PagingSorting pagingSorting = objDto.getPagingSorting();
		pagingSorting.sync();// To calculate start index and Enc Index

		proc.setString(8, pagingSorting.getSortByColumn());// columnName
		proc.setString(9, PagingSorting.DB_Asc_Desc(pagingSorting
				.getSortByOrder()));// sort order
		proc.setInt(10, pagingSorting.getStartRecordId());// start index
		 proc.setInt(11, pagingSorting.getEndRecordId());// end index
		proc.setInt(12, (pagingSorting.isPagingToBeDone() ? 1 : 0));
		if (objDto.getOsp() != null && !"".equals(objDto.getOsp())) {
			proc.setString(13, objDto.getOsp().trim());
		} else {
			proc.setNull(13, java.sql.Types.VARCHAR);
		}
		// index
		rs = proc.executeQuery();
		while(rs.next())
		{
			objReportsDto =  new ReportsDto();
			objReportsDto.setPartyNo(rs.getInt("PARTY_NO"));
			objReportsDto.setPartyName(rs.getString("PARTYNAME"));
			objReportsDto.setOrderNumber(rs.getInt("ORDERNO"));
			objReportsDto.setRegionName(rs.getString("REGION"));
			objReportsDto.setZoneName(rs.getString("ZONE"));
			objReportsDto.setCrmACcountNO(rs.getString("CRMACCOUNTNO"));
			objReportsDto.setCus_segment(rs.getString("CUST_SEGMENT_CODE"));
			objReportsDto.setIndustrySegment(rs.getString("INDUSTRYSEGMENT"));
			objReportsDto.setOrder_type(rs.getString("DEMO_TYPE"));
			objReportsDto.setOrderType(rs.getString("ORDERTYPE"));
			objReportsDto.setOrdersubtype(rs.getString("ORDER_SUBTYPE"));
			objReportsDto.setOrderStatus(rs.getString("STAGE"));
			objReportsDto.setOrderDate(rs.getString("ORDERDATE"));

		   if (rs.getString("ORDERDATE") != null && !"".equals(rs.getString("ORDERDATE")))
			{
				objReportsDto.setOrderDate((utility.showDate_Report(new Date(rs.getTimestamp("ORDERDATE").getTime()))).toUpperCase());
			}
			objReportsDto.setOrderApproveDate(rs.getString("ORDER_APPROVED_DATE"));

			if (rs.getString("ORDER_APPROVED_DATE") != null && !"".equals(rs.getString("ORDER_APPROVED_DATE")))
			{
				objReportsDto.setOrderApproveDate((utility.showDate_Report(new Date(rs.getTimestamp("ORDER_APPROVED_DATE").getTime()))).toUpperCase());
			}
			objReportsDto.setPublishedDate(rs.getString("PUBLISHED_DATE"));

			if (rs.getString("PUBLISHED_DATE") != null && !"".equals(rs.getString("PUBLISHED_DATE")))
			{
				objReportsDto.setPublishedDate((utility.showDate_Report(new Date(rs.getTimestamp("PUBLISHED_DATE").getTime()))).toUpperCase());
			}
			objReportsDto.setAccountManager(rs.getString("ACCOUNTMANAGER"));
			objReportsDto.setProjectManager(rs.getString("PROJECTMANAGER"));
			objReportsDto.setVertical(rs.getString("VERTICAL_DETAILS"));
			objReportsDto.setOrderTotal(rs.getDouble("ORDER_TOTAL"));
			objReportsDto.setTaskName(rs.getString("TASK_NAME"));
			objReportsDto.setActualStartDate(rs.getString("TASKSTARTDATE"));

			if (rs.getString("TASKSTARTDATE") != null && !"".equals(rs.getString("TASKSTARTDATE")))
			{
				objReportsDto.setActualStartDate((utility.showDate_Report(new Date(rs.getTimestamp("TASKSTARTDATE").getTime()))).toUpperCase());
			}
			objReportsDto.setActualEndDate(rs.getString("TASKENDDATE"));

			if (rs.getString("TASKENDDATE") != null && !"".equals(rs.getString("TASKENDDATE")))
			{
				objReportsDto.setActualEndDate((utility.showDate_Report(new Date(rs.getTimestamp("TASKENDDATE").getTime()))).toUpperCase());
			}
			objReportsDto.setTaskNumber(rs.getInt("TASKID"));
			objReportsDto.setOwner(rs.getString("OWNER_NAME"));
			objReportsDto.setAccountMgrPhoneNo(rs.getString("PHONENO"));//changed by kalpana from long to string for bug id HYPR11042013001
			objReportsDto.setEmailId(rs.getString("EMAILID"));
			objReportsDto.setUserName(rs.getString("USER_NAME"));
			objReportsDto.setTotalDays(rs.getString("TASK_DAYS"));
			objReportsDto.setRemarks(rs.getString("ACTION_REMARKS"));
			objReportsDto.setOutCome(rs.getString("OUTCOME"));
			objReportsDto.setOsp(rs.getString("OSP"));
			if (pagingSorting.isPagingToBeDone()) {
				recordCount = rs.getInt("FULL_REC_COUNT");
			}
			listSearchDetails.add(objReportsDto);
		}
		pagingSorting.setRecordCount(recordCount);
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();	
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(proc);
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return listSearchDetails;
}
//Telemedia Order Reports Start
public ArrayList<ReportsDto> getTelemediaOrderList(ReportsDto objDto) 
{
	//	Nagarjuna
	String methodName="getTelemediaOrderList", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement proc =null;
	ResultSet rs = null;
	ArrayList<ReportsDto> listSearchDetails = new ArrayList<ReportsDto>();
	ReportsDto objReportsDto = null;
	int recordCount =0;
	Utility utility=new Utility();
	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	try
	{
		connection=DbConnection.getReportsConnectionObject();
		proc= connection.prepareCall(sqlGetTelemediaOrderReport);
		if (objDto.getFromCopcApprovedDate() != null 
				&& !"".equals(objDto.getFromCopcApprovedDate())) {
			proc.setString(1, objDto.getFromCopcApprovedDate().trim());
		} else {
			proc.setNull(1, java.sql.Types.VARCHAR);
		}
		if (objDto.getToCopcApprovedDate()!= null 
				&& !"".equals(objDto.getToCopcApprovedDate())) {
			proc.setString(2, objDto.getToCopcApprovedDate().trim());
		} else {
			proc.setNull(2, java.sql.Types.VARCHAR);
		}
		PagingSorting pagingSorting = objDto.getPagingSorting();
		pagingSorting.sync();// To calculate start index and Enc Index
		proc.setString(3, pagingSorting.getSortByColumn());// columnName
		proc.setString(4, PagingSorting.DB_Asc_Desc(pagingSorting
				.getSortByOrder()));// sort order
		proc.setInt(5, pagingSorting.getStartRecordId());// start index
		 proc.setInt(6, pagingSorting.getEndRecordId());// end index
		proc.setInt(7, (pagingSorting.isPagingToBeDone() ? 1 : 0));
		// index
		rs = proc.executeQuery();
		while(rs.next())
		{			
			objReportsDto =  new ReportsDto();
			objReportsDto.setPartyName(rs.getString("PARTYNAME"));	
			objReportsDto.setOrderNo(rs.getString("ORDERNO"));
			objReportsDto.setCrmAccountNoString(rs.getString("PARENT_ACCOUNT_NUMBER"));
			objReportsDto.setChild_act_no(rs.getString("CHILD_ACCOUNT_NUMBER"));			
			objReportsDto.setCopcApproveDate(Utility.showDate_Report(rs.getString("COPC_APPROVED_DATE")));
			if (rs.getString("COPC_APPROVED_DATE") != null && !"".equals(rs.getString("COPC_APPROVED_DATE")))
			{
				objReportsDto.setCopcApproveDate((utility.showDate_Report(new Date(rs.getTimestamp("COPC_APPROVED_DATE").getTime()))).toUpperCase());
			}
			
			
			if (pagingSorting.isPagingToBeDone()) {
				recordCount = rs.getInt("FULL_REC_COUNT");
			}
			listSearchDetails.add(objReportsDto);
		}
		pagingSorting.setRecordCount(recordCount);
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();	
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(proc);
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return listSearchDetails;
}
//Telemedia Order Report End
//LAWKUSH START
public ArrayList<ReportsDto> viewZeroOrderValueReportDetails(ReportsDto objDto) throws Exception {

	//	Nagarjuna
	String methodName="viewZeroOrderValueReportDetails", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	ArrayList<ReportsDto> objUserList = new ArrayList<ReportsDto>();
	Connection conn = null;
	CallableStatement proc = null;
	ResultSet rs = null;
	int recordCount = 0;
	Utility utility=new Utility();
	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	try {

		conn = DbConnection.getReportsConnectionObject();

		proc = conn.prepareCall(sqlZeroOrdervalueReport);
		
		if (objDto.getOrderType() != null
				&& !"".equals(objDto.getOrderType())) {
			proc.setString(1, objDto.getOrderType().trim());
		} else {
			proc.setNull(1, java.sql.Types.VARCHAR);
		}
		if (objDto.getFromDate() != null
				&& !"".equals(objDto.getFromDate())) {
			proc.setString(2, objDto.getFromDate().trim());
		} else {
			proc.setNull(2, java.sql.Types.VARCHAR);
		}
		if (objDto.getToDate() != null && !"".equals(objDto.getToDate())) {
			proc.setString(3, objDto.getToDate().trim());
		} else {
			proc.setNull(3, java.sql.Types.VARCHAR);
		}
	
		if (objDto.getFromAccountNo() != 0 && objDto.getToAccountNo() !=0 ) {
			proc.setLong(4,objDto.getFromAccountNo());
			proc.setLong(5,objDto.getToAccountNo());
		} else {
			proc.setNull(4, java.sql.Types.BIGINT);
			proc.setNull(5, java.sql.Types.BIGINT);
		}
		
		if (objDto.getFromOrderNo() != 0 && objDto.getToOrderNo() !=0 ) {
			proc.setLong(6,objDto.getFromOrderNo());
			proc.setLong(7,objDto.getToOrderNo());
		} else {
			proc.setNull(6, java.sql.Types.BIGINT);
			proc.setNull(7, java.sql.Types.BIGINT);
		}
		if (objDto.getCustPoDetailNo() != null && !"".equals(objDto.getCustPoDetailNo())) {
			proc.setString(8, objDto.getCustPoDetailNo().trim());
		} else {
			proc.setNull(8, java.sql.Types.VARCHAR);
		}
		
		PagingSorting pagingSorting = objDto.getPagingSorting();
		pagingSorting.sync();// To calculate start index and Enc Index

		proc.setString(9, pagingSorting.getSortByColumn());// columnName
		proc.setString(10, PagingSorting.DB_Asc_Desc(pagingSorting
				.getSortByOrder()));// sort order
		proc.setInt(11, pagingSorting.getStartRecordId());// start index
		proc.setInt(12, pagingSorting.getEndRecordId());// end index
		proc.setInt(13, (pagingSorting.isPagingToBeDone() ? 1 : 0));// end
		// index

		rs = proc.executeQuery();
		int countFlag = 0;
		while (rs.next() != false) {
			countFlag++;

			objDto = new ReportsDto();
			
			objDto.setPartyName(rs.getString("PARTYNAME"));
			objDto.setOrderNumber(rs.getInt("ORDERNO"));
			objDto.setPoDetailNo(rs.getString("PODETAILNUMBER"));
			objDto.setAccountID(rs.getInt("ACCOUNTID"));
			objDto.setEntityId(rs.getString("ENTITYCODE"));
			objDto.setCustPoDetailNo(rs.getString("CUSTPONUMBER"));
			objDto.setCustPoDate(rs.getString("CUSTPODATE"));

			if (rs.getString("CUSTPODATE") != null && !"".equals(rs.getString("CUSTPODATE")))
						{
				objDto.setCustPoDate((utility.showDate_Report(new Date(rs.getTimestamp("CUSTPODATE").getTime()))).toUpperCase());
						}
			objDto.setPoAmounts(rs.getDouble("POAMOUNT"));
			objDto.setPaymentTerm(rs.getString("PAYMENTTERM"));
			objDto.setContractStartDate(rs.getString("CONTRACTSTARTDATE"));
			if (rs.getString("CONTRACTSTARTDATE") != null && !"".equals(rs.getString("CONTRACTSTARTDATE")))
			{
				
				Date date=df.parse(objDto.getContractStartDate());
				objDto.setContractStartDate((utility.showDate_Report(date)).toUpperCase());
			}	
			objDto.setContractEndDate(rs.getString("CONTRACTENDDATE"));
			if (rs.getString("CONTRACTENDDATE") != null && !"".equals(rs.getString("CONTRACTENDDATE")))
			{
				
				Date date=df.parse(objDto.getContractEndDate());
				objDto.setContractEndDate((utility.showDate_Report(date)).toUpperCase());
			}	
			objDto.setContractPeriod(rs.getInt("CONTRACTPERIOD"));
			objDto.setPoRecieveDate(rs.getString("PORECEIVEDATE"));
			if (rs.getString("PORECEIVEDATE") != null && !"".equals(rs.getString("PORECEIVEDATE")))
			{
				
				Date date=df.parse(objDto.getPoRecieveDate());
				objDto.setPoRecieveDate((utility.showDate_Report(date)).toUpperCase());
			}	
			objDto.setPoIssueBy(rs.getString("ISSUEDBY"));
			objDto.setPoEmailId(rs.getString("EMAILID"));
			objDto.setCrmACcountNO(rs.getString("CRMACCOUNTNO"));
			objDto.setPartyNo(rs.getInt("PARTY_NO"));
			objDto.setLogicalSINo(rs.getString("LOGICAL_SI_NO"));
			objDto.setAccountManager(rs.getString("ACCOUNTMANAGER"));
			objDto.setLineItemDescription(rs.getString("SERVICEPRODUCTID"));
			objDto.setRegionName(rs.getString("REGION"));
			objDto.setChargeAnnotation(rs.getString("ANNOTATION")); 
			objDto.setM6OrderNo(rs.getInt("M6ORDERNO")); 
			objDto.setFromLocation(rs.getString("FROM_LOCATION")); 
			objDto.setToLocation(rs.getString("TO_LOCATION"));
			objDto.setServiceId(rs.getInt("SERVICEID"));
			objDto.setPoDemoContractPeriod(rs.getString("DEMOCONTRACTPERIOD"));
			objDto.setIsDefaultPO(rs.getInt("ISDEFAULTPO"));
			objDto.setCreatedBy(rs.getString("CREATEDBY"));
			objDto.setCreatedDate(rs.getString("CREATEDDATE"));
			if (rs.getString("CREATEDDATE") != null && !"".equals(rs.getString("CREATEDDATE")))
			{
				objDto.setCreatedDate((utility.showDate_Report(new Date(rs.getTimestamp("CREATEDDATE").getTime()))).toUpperCase());
			}
			objDto.setUom(rs.getString("UOM")); 
			objDto.setBillingBandWidth(rs.getString("BILLING_BANDWIDTH")); 
			objDto.setOrder_type(rs.getString("ORDER_TYPE")); 
			objDto.setFxSiId(rs.getString("FX_SI_ID"));
			objDto.setSourceName("UNMIGRATED"); 
			objDto.setTokenNO(rs.getString("TOKEN_NO"));
			objDto.setPoEndDate(rs.getString("CONTRACTENDDATE"));
			if (rs.getString("CONTRACTENDDATE") != null && !"".equals(rs.getString("CONTRACTENDDATE")))
			{
				
				Date date=df.parse(objDto.getPoEndDate());
				objDto.setPoEndDate((utility.showDate_Report(date)).toUpperCase());
			}	
			objDto.setLastUpdatedDate(""); 
			objDto.setLastUpdatedBy("");
			
			
			
			
			
			if (pagingSorting.isPagingToBeDone()) {
				 recordCount = rs.getInt("FULL_REC_COUNT");
				//recordCount = countFlag;
			}
			objUserList.add(objDto);
		}
		pagingSorting.setRecordCount(recordCount);
	} catch (Exception ex) {
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		//ex.printStackTrace();
		//throw new Exception("SQL Exception : " + ex.getMessage(), ex);
	} finally {
		try {
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(proc);
			DbConnection.freeConnection(conn);

		} catch (Exception e) {
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			//e.printStackTrace();
			//throw new Exception("Exception : " + e.getMessage(), e);
		}
	}

	return objUserList;
	}
	
//LAWKUSH END

public ArrayList<RateRenewalReportDTO> viewRateRenewalReport(RateRenewalReportDTO objDto) throws Exception {

	//	Nagarjuna
	String methodName="viewRateRenewalReport", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	ArrayList<RateRenewalReportDTO> objUserList = new ArrayList<RateRenewalReportDTO>();
	Connection conn = null;
	CallableStatement proc = null;
	ResultSet rs = null;
	int recordCount = 0;
	RateRenewalReportDTO objRDto ;
	Utility utility=new Utility();
	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	Date tempDate=null;
	try {

		conn = DbConnection.getReportsConnectionObject();

				proc = conn.prepareCall(sqlRateRenewalReportforUsage);
		
	/*	if (objDto.getOrderType() != null
				&& !"".equals(objDto.getOrderType())) {
			proc.setString(1, objDto.getOrderType().trim());
		} else {
			proc.setNull(1, java.sql.Types.VARCHAR);
		}*/
		if (objDto.getFromDate() != null
				&& !"".equals(objDto.getFromDate())) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date dateStr = formatter.parse(objDto.getFromDate());
			String formattedDate = formatter.format(dateStr);
			Date date1 = formatter.parse(formattedDate);
			formatter = new SimpleDateFormat("MM-dd-yyyy");
			formattedDate = formatter.format(date1);
			proc.setString(1,formattedDate);
			//proc.setString(2, objDto.getFromDate().trim());
		} else {
			proc.setNull(1, java.sql.Types.VARCHAR);
		}				
		if (objDto.getToDate() != null && !"".equals(objDto.getToDate())) {
			SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
			Date dateStr1 = formatter1.parse(objDto.getToDate());
			String formattedDate1 = formatter1.format(dateStr1);
			Date date2 = formatter1.parse(formattedDate1);
			formatter1 = new SimpleDateFormat("MM-dd-yyyy");
			formattedDate1 = formatter1.format(date2);
			proc.setString(2,formattedDate1);
		} else {
			proc.setNull(2, java.sql.Types.VARCHAR);
		}
		/*if (objDto.getDemo() != null && !"".equals(objDto.getDemo())) {
			proc.setString(4, objDto.getDemo().trim());
		} else {
			proc.setNull(4, java.sql.Types.VARCHAR);
		}
		*/
		if (objDto.getFromAccountNo() != 0 && objDto.getToAccountNo() !=0 ) {
			proc.setLong(3,objDto.getFromAccountNo());
			proc.setLong(4,objDto.getToAccountNo());
		} else {
			proc.setNull(3, java.sql.Types.BIGINT);
			proc.setNull(4, java.sql.Types.BIGINT);
		}
		
		if (objDto.getFromOrderNo() != 0 && objDto.getToOrderNo() !=0 ) {
			proc.setLong(5,objDto.getFromOrderNo());
			proc.setLong(6,objDto.getToOrderNo());
		} else {
			proc.setNull(5, java.sql.Types.BIGINT);
			proc.setNull(6, java.sql.Types.BIGINT);
		}
		
		
		PagingSorting pagingSorting = objDto.getPagingSorting();
		pagingSorting.sync();// To calculate start index and Enc Index

		proc.setString(7, pagingSorting.getSortByColumn());// columnName
		proc.setString(8, PagingSorting.DB_Asc_Desc(pagingSorting
				.getSortByOrder()));// sort order
		proc.setInt(9, pagingSorting.getStartRecordId());// start index
		proc.setInt(10, pagingSorting.getEndRecordId());// end index
		proc.setInt(11, (pagingSorting.isPagingToBeDone() ? 1 : 0));// end
		// index
		proc.setInt(12, (objDto.getIsUsage()));// end
		if (objDto.getFromCopcApprovedDate() != null
				&& !"".equals(objDto.getFromCopcApprovedDate())) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date dateStr = formatter.parse(objDto.getFromCopcApprovedDate());
			String formattedDate = formatter.format(dateStr);
			Date date1 = formatter.parse(formattedDate);
			formatter = new SimpleDateFormat("MM-dd-yyyy");
			formattedDate = formatter.format(date1);
			proc.setString(13,formattedDate);
		} else {
			proc.setNull(13, java.sql.Types.VARCHAR);
		}				
		if (objDto.getToCopcApprovedDate() != null && !"".equals(objDto.getToCopcApprovedDate())) {
			SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
			Date dateStr1 = formatter1.parse(objDto.getToCopcApprovedDate());
			String formattedDate1 = formatter1.format(dateStr1);
			Date date2 = formatter1.parse(formattedDate1);
			formatter1 = new SimpleDateFormat("MM-dd-yyyy");
			formattedDate1 = formatter1.format(date2);
			proc.setString(14,formattedDate1);
		} else {
			proc.setNull(14, java.sql.Types.VARCHAR);
		}

		rs = proc.executeQuery();
		int countFlag = 0;
		while (rs.next() != false) {
			countFlag++;

			objRDto = new RateRenewalReportDTO();
			objRDto.setPartyNo(rs.getInt("PARTY_NO"));
			objRDto.setPartyName(rs.getString("PARTYNAME"));  
			objRDto.setCrmACcountNO(rs.getString("CRMACCOUNTNO"));
			objRDto.setAccountManager(rs.getString("ACCOUNTMANAGER"));
			objRDto.setCus_segment(rs.getString("CUST_SEGMENT_CODE"));
			objRDto.setServiceSegment(rs.getString("SERVICESEGMENT"));//Added in View
			objRDto.setVerticalDetails(rs.getString("VERTICAL_DETAILS"));
			objRDto.setRegionName(rs.getString("REGION"));
			//objRDto.setServiceDetDescription(rs.getString("SERVICEDETDESCRIPTION"));PARENT_NAME
			objRDto.setBilling_bandwidth(rs.getString("BILLING_BANDWIDTH"));
			objRDto.setBilling_uom(rs.getString("BILLING_BANDWIDTH_UOM"));
			//objRDto.setChangeTypeName(rs.getString("NAME_SUBTYPE"));SERVICE_ORDER_TYPE_DESC
			objRDto.setOrderType(rs.getString("ORDERTYPE"));
			//objRDto.setCompanyName(rs.getString("COMPANYNAME"));ENTITYNAME
			
			//objRDto.setCurrencyCode(rs.getString("CURNAME"));CURRENCYNAME
			/*objRDto.setPoDate(rs.getString("PODATE"));
			if (rs.getString("PODATE") != null && !"".equals(rs.getString("PODATE")))
			{								
				Date date=df.parse(objRDto.getPoDate());
				objRDto.setPoDate((utility.showDate_Report(date)).toUpperCase());
				//objDto.setPoDate((utility.showDate_Report(new Date(rs.getTimestamp("PODATE").getTime()))).toUpperCase());							
				objRDto.setPoDate(utility.showDate_Report(df.parse(rs.getString("PODATE"))).toUpperCase());
			}*/
			
			tempDate=rs.getDate("PODATE");
			objRDto.setPoDate(rs.getString("PODATE"));
			if (tempDate != null && !"".equals(tempDate))
			{
				objRDto.setPoDate((utility.showDate_Report(tempDate)).toUpperCase());
			}
			objRDto.setCustPoDetailNo(rs.getString("CUSTPONUMBER"));
			
			//objRDto.setFromLocation(rs.getString("PRIMARYLOCATION"));
			//objRDto.setToLocation(rs.getString("SECONDARYLOCATION"));
			setBlank();
			replaceSeperator("PRIMARYLOCATION",rs.getString("PRIMARYLOCATION"));
			objRDto.setFromLocation(VAR_PRIMARYLOCATION);
			//objReportsDto.setSeclocation(rs.getString("SECONDARYLOCATION"));
			replaceSeperator("SECONDARYLOCATION",rs.getString("SECONDARYLOCATION"));
			objRDto.setToLocation(VAR_SECONDARYLOCATION);
			objRDto.setDistance(rs.getString("DISTANCE"));
			objRDto.setLineItemDescription(rs.getString("SERVICEDETDESCRIPTION"));//Changed Column Name :AKS
			objRDto.setLOC_Date(rs.getString("LOCDATE")); 
			if (rs.getString("LOCDATE") != null && !"".equals(rs.getString("LOCDATE")))
			{
				
				Date date=df.parse(objRDto.getLOC_Date());
				objRDto.setLOC_Date((utility.showDate_Report(date)).toUpperCase());
			}
			
			objRDto.setLogicalCircuitNumber(rs.getString("CKTID"));  
			
			objRDto.setTaxationName(rs.getString("TAXATIONVALUE")); 
			objRDto.setLcompanyname(rs.getString("LCOMPANYNAME"));
			objRDto.setOrder_type(rs.getString("DEMO_TYPE"));
			objRDto.setServiceStage(rs.getString("SERVICE_STAGE"));
			objRDto.setNewOrderRemark(rs.getString("NEWORDER_REMARKS"));
			objRDto.setBilling_trigger_date(rs.getString("BILLINGTRIGGERDATE")); 
			if (rs.getString("BILLINGTRIGGERDATE") != null && !"".equals(rs.getString("BILLINGTRIGGERDATE")))
			{
				Date date=df.parse(objRDto.getBilling_trigger_date());
				objRDto.setBilling_trigger_date((utility.showDate_Report(date)).toUpperCase());
			}
			//objRDto.setBillingTriggerFlag(rs.getString("BILLINGTRIGGERFLAG"));Billing_Trigger_Flag
			//objRDto.setZoneName(rs.getString("ZONE"));ZONENNAME
			objRDto.setSalesCoordinator(rs.getString("SALESCOORDINATOR"));//Column Added in View :AKS
			//objRDto.setPoAmounts(rs.getDouble("POAMOUNT"));ORDERTOTAL
			objRDto.setContractPeriod(rs.getInt("CONTRACTPERIOD")); 
			objRDto.setItemQuantity(1);//Need to Ask Ravneet : AKS
			//objRDto.setServiceId(rs.getInt("SERVICEID"));SERVICE_NO 
			objRDto.setM6OrderNo(rs.getInt("M6ORDERNO"));
			objRDto.setCustSINo(rs.getString("CUSTOMER_LOGICAL_SI_NO"));
			objRDto.setM6cktid(rs.getString("CKTID"));
			//objRDto.setServiceProductID(rs.getInt("SERVICEPRODUCTID"));Order_Line_Id
			objRDto.setOrderNo(rs.getString("ORDERNO"));
			if(objDto.getIsUsage() == 0)
			{
				objRDto.setCreatedDate(rs.getString("CREATEDDATE"));
				if (rs.getString("CREATEDDATE") != null && !"".equals(rs.getString("CREATEDDATE")))
			{
				
					objRDto.setCreatedDate((utility.showDate_Report(new Date(rs.getTimestamp("CREATEDDATE").getTime()))).toUpperCase());
			}
				objRDto.setChargeName(rs.getString("CHARGE_NAME"));
				objRDto.setChargeTypeName(rs.getString("CHARGENAME"));
				objRDto.setPaymentTerm(rs.getString("PAYMENTTERM"));
				objRDto.setLinename(rs.getString("SERVICEDETDESCRIPTION"));
				objRDto.setStartDate(rs.getString("START_DATE"));
	if (rs.getString("START_DATE") != null && !"".equals(rs.getString("START_DATE")))
			{
				
					Date date=df.parse(objRDto.getStartDate());
					objRDto.setStartDate((utility.showDate_Report(date)).toUpperCase());
				
			}
				objRDto.setEndDate(rs.getString("END_DATE")); 
if (rs.getString("END_DATE") != null && !"".equals(rs.getString("END_DATE")))
			{
				
					Date date=df.parse(objRDto.getEndDate());
					objRDto.setEndDate((utility.showDate_Report(date)).toUpperCase());
				
			}
				objRDto.setEndHWDateLogic(rs.getString("ENDDATELOGIC")); 
				objRDto.setCharge_status(rs.getString("CHARGES_STATUS"));
				objRDto.setStartDaysLogic(rs.getString("CHARGESTARTDAYSLOGIC"));
				objRDto.setCurrencyCode(rs.getString("CURNAME"));
				objRDto.setStartMonthsLogic(rs.getString("CHARGESTARTMONTHSLOGIC"));
				objRDto.setOrderTotal(rs.getDouble("POAMOUNT"));
				objRDto.setChargeAmount(rs.getDouble("CHARGEAMOUNT"));
				objRDto.setOldLineitemAmount(rs.getString("CHARGEAMOUNT"));
				objRDto.setChargePeriod(rs.getInt("CHARGEPERIOD")); 
				objRDto.setAnnual_rate(rs.getDouble("ANNUAL_RATE"));
				objRDto.setTxtStartDays(rs.getInt("START_DATE_DAYS"));
				objRDto.setTxtStartMonth(rs.getInt("START_DATE_MONTH"));
				objRDto.setServiceDetDescription(rs.getString("SERVICEDETDESCRIPTION"));
				objRDto.setChangeTypeName(rs.getString("NAME_SUBTYPE"));
				objRDto.setCompanyName(rs.getString("COMPANYNAME"));
				objRDto.setCopcApproveDate(rs.getString("COPC_APPROVAL_DATE"));
				if (rs.getString("COPC_APPROVAL_DATE") != null && !"".equals(rs.getString("COPC_APPROVAL_DATE")))
				{
					objRDto.setCopcApproveDate((utility.showDate_Report(new Date(rs.getTimestamp("COPC_APPROVAL_DATE").getTime()))).toUpperCase());
				}
				objRDto.setStageName(rs.getString("ORDER_STAGE"));
				objRDto.setRemarks(rs.getString("REMARKS"));
				objRDto.setProductName(rs.getString("SERVICESTAGE"));
				objRDto.setSubProductName(rs.getString("SERVICESUBTYPENAME")); 
				objRDto.setZoneName(rs.getString("ZONE"));
				objRDto.setPoAmounts(rs.getDouble("POAMOUNT"));
				objRDto.setServiceId(rs.getInt("SERVICEID")); 
				objRDto.setServiceProductID(rs.getInt("SERVICEPRODUCTID"));
				objRDto.setBillingTriggerFlag(rs.getString("BILLINGTRIGGERFLAG"));
				objRDto.setOldOrderNo(rs.getInt("OLDORDERNO"));
			}
			if(objDto.getIsUsage() == 1)
			{
				objRDto.setLogicalSINumber(rs.getInt("LOGICAL_SI_NO"));
				objRDto.setOrderTotal(rs.getDouble("ORDERTOTAL"));
				objRDto.setFx_external_acc_id(rs.getString("Child_Account_Number"));//FX_ACCOUNT_EXTERNAL_ID
				objRDto.setFxInternalId(rs.getInt("INTERNAL_ID"));
				objRDto.setChild_account_creation_status(rs.getString("Child_Account_FX_Sataus"));//CHILD_ACCOUNT_FX_STATUS
				objRDto.setPackageID(rs.getInt("PACKAGE_ID"));
				objRDto.setPackageName(rs.getString("PACKAGE_NAME"));
				objRDto.setComponentID(rs.getInt("COMPONENT_ID"));
				objRDto.setComponentName(rs.getString("COMPONENT_NAME"));
				objRDto.setComponentInfoID(rs.getInt("COMPONENTINFOID"));
				ComponentsDto dto = new ComponentsDto();
				dto.setComponentType(rs.getString("COMPONENT_TYPE"));
				dto.setComponentAmount(rs.getDouble("COMP_AMOUNT"));//RC_NRC_COMP_AMOUNT
				dto.setComponentStatus(rs.getString("COMPONENT_STATUS"));
				dto.setStartLogic(rs.getString("COMPONENT_START_LOGIC"));
				dto.setStartDateDays(rs.getInt("COMP_START_DAYS"));//START_DAYS
				dto.setStartDateMonth(rs.getInt("COMP_START_MONTHS"));//START_MONTHS
				dto.setEndLogic(rs.getString("COMPONENT_END_LOGIC"));
				dto.setEndDateDays(rs.getInt("COMP_END_DAYS"));//END_DAYS
				dto.setEndDateMonth(rs.getInt("COMP_END_MONTHS"));//END_MONTHS
				objRDto.setServiceDetDescription(rs.getString("PARENT_NAME"));
				objRDto.setChangeTypeName(rs.getString("SERVICE_ORDER_TYPE_DESC"));
				objRDto.setCompanyName(rs.getString("ENTITYNAME"));
				objRDto.setCopcApproveDate(rs.getString("COPC_APPROVED_DATE"));
				if (rs.getString("COPC_APPROVED_DATE") != null && !"".equals(rs.getString("COPC_APPROVED_DATE")))
				{
					objRDto.setCopcApproveDate((utility.showDate_Report(new Date(rs.getTimestamp("COPC_APPROVED_DATE").getTime()))).toUpperCase());
				}
				objRDto.setStageName(rs.getString("ORDERSTAGE"));
				objRDto.setRemarks(rs.getString("DISCONNECTION_REMARKS"));
				objRDto.setProductName(rs.getString("SERVICENAME")); 
				objRDto.setCurrencyCode(rs.getString("CURRENCYNAME"));
				objRDto.setSubProductName(rs.getString("SUBPRODUCTNAME"));
				objRDto.setZoneName(rs.getString("ZONENNAME"));
				objRDto.setPoAmounts(rs.getDouble("TOTALPOAMOUNT"));
				objRDto.setServiceId(rs.getInt("SERVICE_NO")); 
				objRDto.setServiceProductID(rs.getInt("Order_Line_Id"));
				objRDto.setBillingTriggerFlag(rs.getString("Billing_Trigger_Flag"));
				objRDto.setOldOrderNo(rs.getInt("Pre_Crm_orderNo"));
				dto.setStartDate(rs.getString("SYSTEM_START_DATE"));
				if (rs.getString("SYSTEM_START_DATE") != null && !"".equals(rs.getString("SYSTEM_START_DATE")))
				{
					Date date=df.parse(dto.getStartDate());
					dto.setStartDate((utility.showDate_Report(date)).toUpperCase());
				}
			
				dto.setEnd_date(rs.getString("COMPONENT_END_DATE"));
				if (rs.getString("COMPONENT_END_DATE") != null && !"".equals(rs.getString("COMPONENT_END_DATE")))
				{
					Date date=df.parse(dto.getEnd_date());
					dto.setEnd_date((utility.showDate_Report(date)).toUpperCase());
				}
				objRDto.setComponentDto(dto);
					//NANCY START
					objRDto.setIsDifferential(rs.getString("IS_DIFFERENTIAL"));
					objRDto.setCopcApproverName(rs.getString("COPC_APPROVER_NAME"));
					objRDto.setEffectiveDate(rs.getString("EFFECTIVEDATE"));
					if (rs.getString("EFFECTIVEDATE") != null && !"".equals(rs.getString("EFFECTIVEDATE")))
					{
						objRDto.setEffectiveDate((utility.showDate_Report(new Date(rs.getTimestamp("EFFECTIVEDATE").getTime()))).toUpperCase());
					}
					objRDto.setBillingTriggerCreateDate(rs.getString("BILLING_TRIGGER_CREATEDATE"));
					if (rs.getString("BILLING_TRIGGER_CREATEDATE") != null && !"".equals(rs.getString("BILLING_TRIGGER_CREATEDATE")))
					{
						objRDto.setBillingTriggerCreateDate((utility.showDate_Report(new Date(rs.getTimestamp("BILLING_TRIGGER_CREATEDATE").getTime()))).toUpperCase());
					}
					objRDto.setIsTriggerRequired(rs.getString("IsTriggerRequired"));
					objRDto.setLineTriggered(rs.getString("LineTriggered"));
					objRDto.setTriggerProcess(rs.getString("TriggerProcess"));
					objRDto.setTriggerDoneBy(rs.getString("TriggerDoneBy"));
					objRDto.setAutomaticTriggerError(rs.getString("AutomaticTriggerError"));
					//NANCY END
				}

				if (pagingSorting.isPagingToBeDone()) {
					recordCount = rs.getInt("FULL_REC_COUNT");
					//recordCount = countFlag;
				}
				objUserList.add(objRDto);
			}
			pagingSorting.setRecordCount(recordCount);
		} catch (Exception ex) {
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			//ex.printStackTrace();
			throw new Exception("SQL Exception : " + ex.getMessage(), ex);
		} finally {
			try {
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(proc);
				DbConnection.freeConnection(conn);

		} catch (Exception e) {
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			//e.printStackTrace();
			throw new Exception("Exception : " + e.getMessage(), e);
		}
	}

	return objUserList;
	}
public ArrayList<RestPendingLineReportDTO> viewRestPendingLineReport(RestPendingLineReportDTO objDto) 
{
	//	Nagarjuna
	String methodName="viewRestPendingLineReport", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection = null;
	CallableStatement proc = null;
	ResultSet rs = null;
	int recordCount = 0;
	ArrayList<RestPendingLineReportDTO> listSearchDetails = new ArrayList<RestPendingLineReportDTO>();
	RestPendingLineReportDTO objReportsDto = null;
	Utility utility=new Utility();
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    Date tempDate=null;
	try
	{
		connection=DbConnection.getReportsConnectionObject();
		proc= connection.prepareCall(sqlGetRestPendingLineReports);
		
		
		if (objDto.getFromDate() != null
				&& !"".equals(objDto.getFromDate())) {
			proc.setString(1, objDto.getFromDate().trim());
		} else {
			proc.setNull(1, java.sql.Types.VARCHAR);
		}
		
		if (objDto.getToDate() != null 
				&& !"".equals(objDto.getToDate())) {
			proc.setString(2, objDto.getToDate().trim());
		} else {
			proc.setNull(2, java.sql.Types.VARCHAR);
		}
		
		if (objDto.getServiceName() != null 
				&& !"".equals(objDto.getServiceName())) {
			proc.setString(3, objDto.getServiceName().trim());
		} else {
			proc.setNull(3, java.sql.Types.VARCHAR);
		}
		
		if (objDto.getSubChangeTypeName() != null 
				&& !"".equals(objDto.getSubChangeTypeName())) {
			proc.setString(4, objDto.getSubChangeTypeName().trim());
		} else {
			proc.setNull(4, java.sql.Types.VARCHAR);
		}
		
		if (objDto.getVerticalDetails() != null 
				&& !"".equals(objDto.getVerticalDetails())) {
			proc.setString(5, objDto.getVerticalDetails().trim());
		} else {
			proc.setNull(5, java.sql.Types.VARCHAR);
		}

		PagingSorting pagingSorting = objDto.getPagingSorting();
		pagingSorting.sync();// To calculate start index and Enc Index

		proc.setString(6, pagingSorting.getSortByColumn());// columnName
		proc.setString(7, PagingSorting.DB_Asc_Desc(pagingSorting
				.getSortByOrder()));// sort order
		proc.setInt(8, pagingSorting.getStartRecordId());// start index
		 proc.setInt(9, pagingSorting.getEndRecordId());// end index
		proc.setInt(10, (pagingSorting.isPagingToBeDone() ? 1 : 0));
		// index
		proc.setInt(11, objDto.getIsUsage());		
		rs = proc.executeQuery();
		while(rs.next())
		{
			objReportsDto =  new RestPendingLineReportDTO();
			objReportsDto.setAccountID(rs.getInt("CUSTACCOUNTID"));
			objReportsDto.setPartyName(rs.getString("PARTYNAME"));
			objReportsDto.setPartyNo(rs.getInt("PARTY_NO"));			
			objReportsDto.setOrderDate(rs.getString("ORDERDATE"));
			tempDate=rs.getDate("ORDERDATE");
			objReportsDto.setOrderDate(rs.getString("ORDERDATE"));
			if (tempDate != null && !"".equals(tempDate))
			{
				objReportsDto.setOrderDate((utility.showDate_Report(tempDate)).toUpperCase());
			}											
/*			 objReportsDto.setPoDate(rs.getString("PODATE"));
			 if (rs.getString("PODATE") != null && !"".equals(rs.getString("PODATE")))
				{
				Date date=df.parse(objReportsDto.getPoDate());
				objReportsDto.setPoDate((Utility.showDate_Report(date)).toUpperCase());
				}*/
			tempDate=rs.getDate("PODATE");
			objReportsDto.setPoDate(rs.getString("PODATE"));
			if (tempDate != null && !"".equals(tempDate))
			{
				objReportsDto.setPoDate((utility.showDate_Report(tempDate)).toUpperCase());
			}
			/*objReportsDto.setCustPoDate(rs.getString("CUSTPODATE"));
			if (rs.getString("CUSTPODATE") != null && !"".equals(rs.getString("CUSTPODATE")))
				{
				Date date=df.parse(objReportsDto.getCustPoDate());
				objReportsDto.setCustPoDate((Utility.showDate_Report(date)).toUpperCase());
				}*/
			tempDate=rs.getDate("CUSTPODATE");
			objReportsDto.setCustPoDate(rs.getString("CUSTPODATE"));
			if (tempDate != null && !"".equals(tempDate))
			{
				objReportsDto.setCustPoDate((utility.showDate_Report(tempDate)).toUpperCase());
			}
			objReportsDto.setRate_code(rs.getString("RATECODE"));
			objReportsDto.setLast_mile_media(rs.getString("LAST_MILE_MEDIA"));
			objReportsDto.setLast_mile_remarks(rs.getString("LAST_MILE_REMARKS"));
			objReportsDto.setLink_type(rs.getString("LINK_TYPE"));
			objReportsDto.setIndicative_value(rs.getString("INDICATIVE_VALUE"));
			objReportsDto.setUom(rs.getString("UOM"));
			objReportsDto.setLast_mile_provider(rs.getString("LAST_MILE_PROVIDER"));				
			objReportsDto.setBillingMode(rs.getString("BILLINGMODE"));			
			objReportsDto.setCustPoDetailNo(rs.getString("CUSTPONUMBER"));
			objReportsDto.setPoNumber(rs.getInt("PONUMBER"));					
			//objReportsDto.setPrimarylocation(rs.getString("PRIMARYLOCATION"));
			setBlank();
			replaceSeperator("PRIMARYLOCATION",rs.getString("PRIMARYLOCATION"));
			objReportsDto.setPrimarylocation(VAR_PRIMARYLOCATION);
			//objReportsDto.setSeclocation(rs.getString("SECONDARYLOCATION"));
			replaceSeperator("SECONDARYLOCATION",rs.getString("SECONDARYLOCATION"));
			objReportsDto.setSeclocation(VAR_SECONDARYLOCATION);
					
			/*objReportsDto.setRfs_date(rs.getString("SERVICE_RFS_DATE"));
			if (rs.getString("SERVICE_RFS_DATE") != null && !"".equals(rs.getString("SERVICE_RFS_DATE")))
			{
				Date date=df.parse(objReportsDto.getRfs_date());
				objReportsDto.setRfs_date((Utility.showDate_Report(date)).toUpperCase());

			}*/
			tempDate=rs.getDate("SERVICE_RFS_DATE");
			objReportsDto.setRfs_date(rs.getString("SERVICE_RFS_DATE"));
			if (tempDate != null && !"".equals(tempDate))
			{
				objReportsDto.setRfs_date((utility.showDate_Report(tempDate)).toUpperCase());
			}
			objReportsDto.setProductName(rs.getString("PRODUCTNAME"));
			objReportsDto.setSubProductName(rs.getString("SUBPRODUCTNAME"));						
			objReportsDto.setMocn_no(rs.getString("MOCN_NO"));								
			objReportsDto.setBillingLevelNo(rs.getLong("BILLING_LEVEL_NO"));
			objReportsDto.setLogicalCircuitId(rs.getString("CUSTOMER_LOGICAL_SI_NO"));
			objReportsDto.setCustomer_logicalSINumber(rs.getInt("CUSTOMER_LOGICAL_SI_NO"));
			objReportsDto.setServiceName(rs.getString("SERVICENAME"));			
			objReportsDto.setCkt_id(rs.getString("CKTID"));																							
			objReportsDto.setBillingLevel(rs.getString("BILLING_LEVELNAME"));			
			objReportsDto.setDemoType(rs.getString("Demo_Type"));			
			objReportsDto.setBilling_bandwidth(rs.getString("BILLING_BANDWIDTH"));
			objReportsDto.setBilling_uom(rs.getString("BILLING_BANDWIDTH_UOM"));
			objReportsDto.setDistance(rs.getString("DISTANCE"));			
			objReportsDto.setStageName(rs.getString("ORDERSTAGE"));											
			objReportsDto.setOrder_type(rs.getString("ORDERTYPE"));
			objReportsDto.setOrdersubtype(rs.getString("ORDERSUBTYPE"));			
			objReportsDto.setVerticalDetails(rs.getString("VERTICAL_DETAILS"));
			
			objReportsDto.setCurrencyName(rs.getString("CURRENCYNAME"));
			objReportsDto.setEntity(rs.getString("ENTITYNAME"));
			objReportsDto.setBillingTypeName(rs.getString("BILLING_TYPENAME"));
			objReportsDto.setBillingFormatName(rs.getString("BILLING_FORMATNAME"));
			objReportsDto.setLcompanyname(rs.getString("LCOMPANYNAME"));			
			objReportsDto.setTaxation(rs.getString("TAXATIONVALUE"));		
			objReportsDto.setOrderNumber(rs.getInt("ORDERNO"));									
			objReportsDto.setContractPeriod(rs.getInt("CONTRACTPERIOD"));
			objReportsDto.setTotalAmountIncludingCurrent(rs.getDouble("TOTALPOAMOUNT"));
			objReportsDto.setParty_id(rs.getInt("PARTY_ID"));
			objReportsDto.setCrmAccountNo(rs.getInt("CRMACCOUNTNO"));
			objReportsDto.setM6cktid(rs.getString("M6_PRODUCT_ID"));
			objReportsDto.setCust_total_poamt(rs.getDouble("CUST_TOT_PO_AMT"));								    
						
			if(objDto.getIsUsage() == 1){
				objReportsDto.setCancelBy(rs.getString("CANCEL_BY"));	
				//objReportsDto.setCanceldate(rs.getString("CANCELDATE"));
				
				tempDate=rs.getDate("CANCEL_DATE");  
				if(tempDate!=null)
				{
					objReportsDto.setCanceldate((Utility.showDate_Report(tempDate)).toUpperCase());
				}
				
				objReportsDto.setCreditPeriodName(rs.getString("CREDIT_PERIODNAME"));
				objReportsDto.setM6_prod_id(rs.getString("CHILDSPECID"));
				objReportsDto.setServiceId(rs.getInt("SERVICE_NO"));
				objReportsDto.setServiceproductid(rs.getInt("Order_Line_Id"));
				objReportsDto.setLinename(rs.getString("SERVICEDETDESCRIPTION"));
				objReportsDto.setCancelServiceReason(rs.getString("CANCEL_RESION"));	
				
				ComponentsDto dto = new ComponentsDto();
				objReportsDto.setRegionName(rs.getString("REGION"));
				objReportsDto.setAccountManager(rs.getString("ACCOUNTMANAGER"));
				objReportsDto.setProjectManager(rs.getString("PROJECTMANAGER"));
				objReportsDto.setCopcapprovaldate(rs.getString("COPC_APPROVED_DATE"));
				if (rs.getString("COPC_APPROVED_DATE") != null && !"".equals(rs.getString("COPC_APPROVED_DATE")))
					{
						objReportsDto.setCopcapprovaldate((Utility.showDate_Report(new Date(rs.getTimestamp("COPC_APPROVED_DATE").getTime()))).toUpperCase());
					}
				/*objReportsDto.setPoReceiveDate(rs.getString("PORECEIVEDATE"));
				if (rs.getString("PORECEIVEDATE") != null && !"".equals(rs.getString("PORECEIVEDATE")))
					{
					Date date=df.parse(objReportsDto.getPoReceiveDate());
					objReportsDto.setPoReceiveDate((Utility.showDate_Report(date)).toUpperCase());
					}*/
				tempDate=rs.getDate("PORECEIVEDATE");
				objReportsDto.setPoReceiveDate(rs.getString("PORECEIVEDATE"));
				if (tempDate != null && !"".equals(tempDate))
				{
					objReportsDto.setPoReceiveDate((utility.showDate_Report(tempDate)).toUpperCase());
				}
				//objReportsDto.setBilling_address(rs.getString("BILLING_ADDRESS"));
				replaceSeperator("BILLING_LOCATION",rs.getString("BILLING_ADDRESS"));
				objReportsDto.setBilling_address(VAR_BILLING_ADDRESS);
				objReportsDto.setLineno(rs.getInt("Order_Line_Id"));
				objReportsDto.setLocation_from(rs.getString("FROM_ADDRESS"));
				objReportsDto.setLocation_to(rs.getString("TO_ADDRESS"));
				objReportsDto.setServiceStage(rs.getString("SERVICE_STAGE"));
				objReportsDto.setChild_act_no(rs.getString("Child_Account_Number"));
				objReportsDto.setCrm_productname(rs.getString("SERVICEDETDESCRIPTION"));
				
				objReportsDto.setComponentInfoID(rs.getInt("COMPONENTINFOID"));
				dto.setComponentStatus(rs.getString("COMPONENT_STATUS"));
				objReportsDto.setComponentID(rs.getInt("COMPONENT_ID"));
				objReportsDto.setComponentName(rs.getString("COMPONENT_NAME"));
				objReportsDto.setPackageID(rs.getInt("PACKAGE_ID"));
				objReportsDto.setPackageName(rs.getString("PACKAGE_NAME"));
				objReportsDto.setFx_internal_acc_id(rs.getLong("INTERNAL_ID"));
				dto.setComponentInstanceID(rs.getString("COMPONENT_INST_ID"));
				dto.setComponentType(rs.getString("COMPONENT_TYPE"));
				dto.setComponentAmount(rs.getDouble("COMP_AMOUNT"));
				dto.setFxTokenNo(rs.getString("START_COMPONENT_TOKEN_NO"));
				dto.setStartDate(rs.getString("SYSTEM_START_DATE"));
				if (rs.getString("SYSTEM_START_DATE") != null && !"".equalsIgnoreCase(rs.getString("SYSTEM_START_DATE")))					
				{					
						Date date=df.parse(dto.getStartDate());
						dto.setStartDate((Utility.showDate_Report(date)).toUpperCase());					
				}
				dto.setFxStartStatus(rs.getString("SYSTEM_START_STATUS"));
				dto.setEndFxStatus(rs.getString("SYSTEM_END_STATUS"));
				//dto.setComponentFXStatus(rs.getString("FX_STATUS"));
				// <!--GlobalDataBillingEfficiency BFR5  -->
				objReportsDto.setTaxExcemption_Reason(rs.getString("TAXEXCEMPTION_REASON"));
				
				objReportsDto.setComponentDto(dto);
			}else{
				objReportsDto.setCreditPeriod(rs.getInt("CREDITPERIOD"));
				objReportsDto.setLb_service_id(rs.getString("LB_SERVICE_LIST_ID"));
				objReportsDto.setServiceproductid(rs.getInt("SERVICEPRODUCTID"));
				objReportsDto.setServiceId(rs.getInt("SERVICEID"));
				objReportsDto.setPoAmountSum(rs.getLong("ORDERAMOUNT"));
				objReportsDto.setRegionName(rs.getString("REGIONNAME"));
				objReportsDto.setCancelServiceReason(rs.getString("CANCELREASON"));	
				objReportsDto.setChild_act_no(rs.getString("CHILD_AC_NO"));
				objReportsDto.setBisource(rs.getString("BISOURCE"));	
				objReportsDto.setServiceStage(rs.getString("SERVICESTAGE"));
				objReportsDto.setLocation_from(rs.getString("FROM_LOCATION"));
				objReportsDto.setLocation_to(rs.getString("TO_LOCATION"));
				objReportsDto.setLinename(rs.getString("LINENAME"));
				objReportsDto.setCrm_productname(rs.getString("CRMPRODUCTNAME"));	
				objReportsDto.setAccountID(rs.getInt("CUSTACCOUNTID"));
				objReportsDto.setAccountManager(rs.getString("ACTMNAME"));
				objReportsDto.setProjectManager(rs.getString("PMNAME"));
				objReportsDto.setAmapprovaldate(rs.getString("AM_APPROVAL_DATE"));
				if (rs.getString("AM_APPROVAL_DATE") != null && !"".equals(rs.getString("AM_APPROVAL_DATE")))
					{
						objReportsDto.setAmapprovaldate((Utility.showDate_Report(new Date(rs.getTimestamp("AM_APPROVAL_DATE").getTime()))).toUpperCase());
					}
				objReportsDto.setPmapprovaldate(rs.getString("PM_APPROVAL_DATE"));
				if (rs.getString("PM_APPROVAL_DATE") != null && !"".equals(rs.getString("PM_APPROVAL_DATE")))
				{
					objReportsDto.setPmapprovaldate((Utility.showDate_Report(new Date(rs.getTimestamp("PM_APPROVAL_DATE").getTime()))).toUpperCase());
				}
			
			objReportsDto.setCopcapprovaldate(rs.getString("COPC_APPROVAL_DATE"));
			if (rs.getString("COPC_APPROVAL_DATE") != null && !"".equals(rs.getString("COPC_APPROVAL_DATE")))
				{
					objReportsDto.setCopcApproveDate((Utility.showDate_Report(new Date(rs.getTimestamp("COPC_APPROVAL_DATE").getTime()))).toUpperCase());
				}
			objReportsDto.setOrderDate(rs.getString("ORDERCREATEDATE"));
			if (rs.getString("ORDERCREATEDATE") != null && !"".equals(rs.getString("ORDERCREATEDATE")))
				{
				Date date=df.parse(objReportsDto.getOrderDate());
				objReportsDto.setOrderDate((Utility.showDate_Report(date)).toUpperCase());
				}
			objReportsDto.setPoReceiveDate(rs.getString("CUSTPORECDATE"));
			if (rs.getString("CUSTPORECDATE") != null && !"".equals(rs.getString("CUSTPORECDATE")))
				{
				Date date=df.parse(objReportsDto.getPoReceiveDate());
				objReportsDto.setPoReceiveDate((Utility.showDate_Report(date)).toUpperCase());
				}
			
				objReportsDto.setChargeEndDate(rs.getString("CHARGE_START_DATE"));
				if (rs.getString("CHARGE_START_DATE") != null && !"".equals(rs.getString("CHARGE_START_DATE")))
				{
					Date date=df.parse(objReportsDto.getChargeEndDate());
					objReportsDto.setChargeEndDate((Utility.showDate_Report(date)).toUpperCase());
				}
				objReportsDto.setLineno(rs.getInt("LINEITEMNO"));
				objReportsDto.setOpms_act_id(rs.getString("OPMS_ACT_ID"));
				objReportsDto.setAddress1(rs.getString("ADDRESS"));	
				objReportsDto.setCancelflag(rs.getString("CANCELBY"));	
				objReportsDto.setBilling_address(rs.getString("BILLING_LOCATION"));		
				objReportsDto.setCanceldate(rs.getString("CANCELDATE"));
				
				objReportsDto.setDispatchAddress1(rs.getString("DISPATCHADDRESS"));		
				objReportsDto.setHardware_flag(rs.getString("HARDWARE_FLAG"));
				objReportsDto.setChargeTypeName(rs.getString("CHARGE_TYPEID"));
				objReportsDto.setCharge_status(rs.getString("CHARGES_STATUS"));
				objReportsDto.setFormAvailable(rs.getString("FORM_C_AVAILABLE"));
				objReportsDto.setChargeName(rs.getString("CHARGE_NAME"));
				objReportsDto.setFx_status(rs.getString("FX_STATUS"));
				objReportsDto.setFx_sd_status(rs.getString("Fx_St_Chg_Status"));
				objReportsDto.setFx_ed_status(rs.getString("Fx_Ed_Chg_Status"));
				objReportsDto.setChild_ac_fxstatus(rs.getString("Child_Account_FX_Sataus"));
				objReportsDto.setFrequencyName(rs.getString("FREQUENCY"));
				objReportsDto.setHardwaretypeName(rs.getString("HARDWARETYPENAME"));
				objReportsDto.setTokenno(rs.getString("TOKENNO"));
				objReportsDto.setSaleNature(rs.getString("SALENATURENAME"));
				objReportsDto.setSaleType(rs.getString("SALETYPENAME"));
				objReportsDto.setRatio(rs.getString("RATIO"));
				objReportsDto.setAnnitation(rs.getString("ANNOTATION"));
				objReportsDto.setChargeTypeName(rs.getString("CHARGE_TYPE"));
				objReportsDto.setStorename(rs.getString("STORENAME"));
				objReportsDto.setBill_period(rs.getString("BILL_PERIOD"));
				if((rs.getString("CONFIG_ID")) != null && !"".equals(rs.getString("CONFIG_ID"))
						&& (rs.getString("ENTITYID")) != null && !"".equals(rs.getString("ENTITYID"))){
					String tBillPeriod=fnGetUsageReportBIllPeriod(rs.getString("CONFIG_ID"), rs.getString("ENTITYID"));
					objReportsDto.setBill_period(tBillPeriod);
					
				}
				objReportsDto.setChargeAmount(rs.getDouble("INV_AMT"));
				objReportsDto.setLineamt(rs.getLong("LINEITEMAMOUNT"));
				objReportsDto.setBusiness_serial_no(rs.getString("BUSINESS_SERIAL_NO"));
				objReportsDto.setLb_pk_charge_id(rs.getString("LB_PK_CHARGE_ID"));
				objReportsDto.setChargeinfoID(rs.getString("PK_CHARGE_ID"));
				objReportsDto.setAnnual_rate(rs.getInt("ANNUAL_RATE"));
				// <!--GlobalDataBillingEfficiency BFR5  -->
				objReportsDto.setTaxExcemption_Reason(rs.getString("TAXEXCEMPTION_REASON"));
				}
                //[005] Start
				objReportsDto.setInstallationFromCity(rs.getString("INSTALLATION_FROM_CITY"));
				objReportsDto.setInstallationToCity(rs.getString("INSTALLATION_TO_CITY"));
				objReportsDto.setInstallationFromState(rs.getString("INSTALLATION_FROM_STATE"));
				objReportsDto.setInstallationToState(rs.getString("INSTALLATION_TO_STATE"));
				objReportsDto.setBillingContactName(rs.getString("BILLING_CONTACT_NAME"));
				objReportsDto.setBillingContactNumber(rs.getString("BILLING_CONTACT_NUMBER"));
				objReportsDto.setBillingEmailId(rs.getString("BILLING_EMAIL_ID"));
				//[005] End
				
				//[006] Start
				objReportsDto.setStandardReason(rs.getString("STANDARDREASON"));
				//[006] End

				if (pagingSorting.isPagingToBeDone()) {
					recordCount = rs.getInt("FULL_REC_COUNT");
				}
				listSearchDetails.add(objReportsDto);
			}
			pagingSorting.setRecordCount(recordCount);
		}
		catch(Exception ex )
		{
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			//ex.printStackTrace();	
		}
		finally
		{
			try 
			{
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(proc);
				DbConnection.freeConnection(connection);
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				//e.printStackTrace();
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			}
		}
		return listSearchDetails;	
	}



public ArrayList<DisconnectLineReportDTO> viewDisconnectionLineReport(DisconnectLineReportDTO objDto) 
{
	//	Nagarjuna
	String methodName="viewDisconnectionLineReport", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection = null;
	CallableStatement proc = null;
	ResultSet rs = null;
	int recordCount = 0;
	ArrayList<DisconnectLineReportDTO> listSearchDetails = new ArrayList<DisconnectLineReportDTO>();
	DisconnectLineReportDTO objReportsDto = null;
	Utility utility=new Utility();
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    Date tempDate=null;
    
	try
	{
		connection=DbConnection.getReportsConnectionObject();
		proc= connection.prepareCall(sqlGetDisconnectionLineReportForUsage);
		
		if (objDto.getOrdermonth() != null
				&& !"".equals(objDto.getOrdermonth())) {
			proc.setString(1, objDto.getOrdermonth().trim());
		} else {
			proc.setNull(1, java.sql.Types.VARCHAR);
		}		
		if (objDto.getFromDate() != null
				&& !"".equals(objDto.getFromDate())) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date dateStr = formatter.parse(objDto.getFromDate());
			String formattedDate = formatter.format(dateStr);
			Date date1 = formatter.parse(formattedDate);
			formatter = new SimpleDateFormat("MM-dd-yyyy");
			formattedDate = formatter.format(date1);
			proc.setString(2,formattedDate);
			//proc.setString(2, objDto.getFromDate().trim());
		} else {
			proc.setNull(2, java.sql.Types.VARCHAR);
		}				
		if (objDto.getToDate() != null && !"".equals(objDto.getToDate())) {
			SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
			Date dateStr1 = formatter1.parse(objDto.getToDate());
			String formattedDate1 = formatter1.format(dateStr1);
			Date date2 = formatter1.parse(formattedDate1);
			formatter1 = new SimpleDateFormat("MM-dd-yyyy");
			formattedDate1 = formatter1.format(date2);
			proc.setString(3,formattedDate1);
		} else {
			proc.setNull(3, java.sql.Types.VARCHAR);
		}
		if (objDto.getServiceName() != null 
				&& !"".equals(objDto.getServiceName())) {
			proc.setString(4, objDto.getServiceName().trim());
		} else {
			proc.setNull(4, java.sql.Types.VARCHAR);
		}
		
		if (objDto.getOrdersubtype() != null 
				&& !"".equals(objDto.getOrdersubtype())) {
			proc.setString(5, objDto.getOrdersubtype().trim());
		} else {
			proc.setNull(5, java.sql.Types.VARCHAR);
		}
		
		if (objDto.getVerticalDetails() != null 
				&& !"".equals(objDto.getVerticalDetails())) {
			proc.setString(6, objDto.getVerticalDetails().trim());
		} else {
			proc.setNull(6, java.sql.Types.VARCHAR);
		}
		
		PagingSorting pagingSorting = objDto.getPagingSorting();
		pagingSorting.sync();// To calculate start index and Enc Index

		proc.setString(7, pagingSorting.getSortByColumn());// columnName
		proc.setString(8, PagingSorting.DB_Asc_Desc(pagingSorting
				.getSortByOrder()));// sort order
		proc.setInt(9, pagingSorting.getStartRecordId());// start index
		 proc.setInt(10, pagingSorting.getEndRecordId());// end index
		proc.setInt(11, (pagingSorting.isPagingToBeDone() ? 1 : 0));
		proc.setInt(12, objDto.getIsUsage());
		// index
		rs = proc.executeQuery();
		while(rs.next())
		{
			objReportsDto =  new DisconnectLineReportDTO();
			objReportsDto.setPartyNo(rs.getInt("PARTY_NO"));
			objReportsDto.setAccountID(rs.getInt("CRMACCOUNTNO"));
			objReportsDto.setCust_name(rs.getString("PARTYNAME"));
			objReportsDto.setVerticalDetails(rs.getString("VERTICAL_DETAILS"));
			objReportsDto.setRegionName(rs.getString("REGION"));
			objReportsDto.setOrderNumber(rs.getInt("ORDERNO"));
			objReportsDto.setOrder_type(rs.getString("ORDERTYPE"));
			objReportsDto.setM6OrderNo(rs.getInt("M6ORDERNO"));
			objReportsDto.setServiceName(rs.getString("SERVICENAME"));
			objReportsDto.setCustomer_logicalSINumber(rs.getInt("CUSTOMER_LOGICAL_SI_NO"));
			objReportsDto.setLinename(rs.getString("SERVICEDETDESCRIPTION"));
			objReportsDto.setOrdersubtype(rs.getString("ORDERSUBTYPE"));
			objReportsDto.setCkt_id(rs.getString("CKTID"));
			objReportsDto.setBandwaidth(rs.getString("BANDWIDTH"));
			objReportsDto.setUom(rs.getString("UOM"));
			objReportsDto.setBilling_bandwidth(rs.getString("BILLING_BANDWIDTH"));
			objReportsDto.setBilling_uom(rs.getString("BILLING_BANDWIDTH_UOM"));
			objReportsDto.setDistance(rs.getString("DISTANCE"));
			objReportsDto.setLocation_from(rs.getString("FROM_ADDRESS"));
			objReportsDto.setLocation_to(rs.getString("TO_ADDRESS"));
			//objReportsDto.setPrimarylocation(rs.getString("PRIMARYLOCATION"));
			setBlank();
			replaceSeperator("PRIMARYLOCATION",rs.getString("PRIMARYLOCATION"));
			objReportsDto.setPrimarylocation(VAR_PRIMARYLOCATION);
			//objReportsDto.setSeclocation(rs.getString("SECONDARYLOCATION"));
			replaceSeperator("SECONDARYLOCATION",rs.getString("SECONDARYLOCATION"));
			objReportsDto.setSeclocation(VAR_SECONDARYLOCATION);
			objReportsDto.setProductName(rs.getString("PRODUCTNAME"));
			objReportsDto.setSubProductName(rs.getString("SUBPRODUCTNAME"));
			objReportsDto.setEntity(rs.getString("ENTITYNAME"));
			objReportsDto.setLcompanyname(rs.getString("LCOMPANYNAME"));
			objReportsDto.setCurrencyName(rs.getString("CURRENCYNAME"));
			
			objReportsDto.setBillingTypeName(rs.getString("BILLING_TYPENAME"));
			objReportsDto.setBillingFormatName(rs.getString("BILLING_FORMATNAME"));
			objReportsDto.setBillingLevel(rs.getString("BILLING_LEVELNAME"));
			objReportsDto.setBillingLevelNo(rs.getLong("BILLING_LEVEL_NO"));
			objReportsDto.setTaxation(rs.getString("TAXATIONVALUE"));
			objReportsDto.setBill_period(rs.getString("BILL_PERIOD"));
			if((rs.getString("CONFIG_ID")) != null || !"".equals(rs.getString("CONFIG_ID"))
					&& (rs.getString("ENTITYID")) != null || !"".equals(rs.getString("ENTITYID"))){
				String tBillPeriod=fnGetUsageReportBIllPeriod(rs.getString("CONFIG_ID"), rs.getString("ENTITYID"));
				objReportsDto.setBill_period(tBillPeriod);								
			}
			objReportsDto.setBillingMode(rs.getString("BILLINGMODE"));
			objReportsDto.setPoNumber(rs.getInt("PONUMBER"));
			objReportsDto.setPoDate(rs.getString("PODATE"));
			if (rs.getString("PODATE") != null && !"".equals(rs.getString("PODATE")))
			{
				//Date date=df.parse(objReportsDto.getPoDate());
				objReportsDto.setPoDate((utility.showDate_Report(objReportsDto.getPoDate())).toUpperCase());
			}
			objReportsDto.setContractPeriod(rs.getInt("CONTRACTPERIOD"));
			objReportsDto.setTotalAmountIncludingCurrent(rs.getDouble("TOTALPOAMOUNT"));
			objReportsDto.setPoReceiveDate(rs.getString("PORECEIVEDATE"));
			tempDate=rs.getDate("PORECEIVEDATE");
			objReportsDto.setPoRecieveDate(rs.getString("PORECEIVEDATE"));
			if (tempDate != null && !"".equals(tempDate))
			{
				objReportsDto.setPoReceiveDate((utility.showDate_Report(tempDate)).toUpperCase());
			}			
			objReportsDto.setCustPoDetailNo(rs.getString("CUSTPONUMBER"));
			tempDate=rs.getDate("CUSTPODATE");
			objReportsDto.setCustPoDate(rs.getString("CUSTPODATE"));
			if (tempDate != null && !"".equals(tempDate))
			{
				objReportsDto.setCustPoDate((utility.showDate_Report(tempDate)).toUpperCase());
			}			
			objReportsDto.setLocDate(rs.getString("LOCDATE"));
			if (rs.getString("LOCDATE") != null && !"".equals(rs.getString("LOCDATE")))
			{
				Date date=df.parse(objReportsDto.getLocDate());
				objReportsDto.setLocDate((utility.showDate_Report(date)).toUpperCase());
			}
			objReportsDto.setLocno(rs.getString("LOCNO"));
			objReportsDto.setBilling_trigger_date(rs.getString("BILLINGTRIGGERDATE"));
			if (rs.getString("BILLINGTRIGGERDATE") != null && !"".equals(rs.getString("BILLINGTRIGGERDATE")))
			{
				Date date=df.parse(objReportsDto.getBilling_trigger_date());
				objReportsDto.setBilling_trigger_date((utility.showDate_Report(date)).toUpperCase());
			}			
			objReportsDto.setPmapprovaldate(rs.getString("PM_PROV_DATE"));
			if (rs.getString("Pm_Prov_Date") != null && !"".equals(rs.getString("Pm_Prov_Date")))
			{
				  String s1=rs.getString("Pm_Prov_Date");
				  String s3=(s1.substring(0,7)).toUpperCase();
				  String s4=s1.substring(9,11);
				  String s5=s3.concat(s4);
				  objReportsDto.setPmApproveDate(s5);
			}
			objReportsDto.setBilling_Trigger_Flag(rs.getString("BILLING_TRIGGER_FLAG"));			
			objReportsDto.setLineno(rs.getInt("ORDER_LINE_ID"));
			objReportsDto.setServiceId(rs.getInt("SERVICE_NO"));
			objReportsDto.setPre_crmorderid(rs.getInt("Pre_Crm_orderNo"));
			objReportsDto.setDisconnection_remarks(rs.getString("DISCONNECTION_REMARKS"));
			objReportsDto.setStageName(rs.getString("STAGE"));
			objReportsDto.setServiceStage(rs.getString("SERVICE_STAGE"));
			objReportsDto.setNeworder_remarks(rs.getString("NEWORDER_REMARKS"));
			objReportsDto.setCopcapprovaldate(rs.getString("COPC_APPROVED_DATE"));
			if (rs.getString("COPC_APPROVED_DATE") != null && !"".equals(rs.getString("COPC_APPROVED_DATE")))
			{
				objReportsDto.setCopcapprovaldate((utility.showDate_Report(new Date(rs.getTimestamp("COPC_APPROVED_DATE").getTime()))).toUpperCase());
			}
			objReportsDto.setRequest_rec_date(rs.getString("DISCONNECTION_RECEIVE_DATE"));
			
			objReportsDto.setStandard_reason(rs.getString("STANDARDREASON"));
			tempDate=rs.getDate("ORDERDATE");
			objReportsDto.setOrderDate(rs.getString("ORDERDATE"));
			if (tempDate != null && !"".equals(tempDate))
			{
				objReportsDto.setOrderDate((utility.showDate_Report(tempDate)).toUpperCase());
			}
			if(objDto.getIsUsage() == 0)
			{
			objReportsDto.setRatio(rs.getString("RATIO"));
				objReportsDto.setCreditPeriod(rs.getInt("CREDITPERIOD"));
				objReportsDto.setHardwaretypeName(rs.getString("HARDWARETYPENAME"));
				objReportsDto.setHardware_flag(rs.getString("HARDWARE_FLAG"));
				objReportsDto.setStorename(rs.getString("STORENAME"));
				objReportsDto.setSaleType(rs.getString("SALETYPE"));
				objReportsDto.setCust_total_poamt(rs.getDouble("CUST_TOT_PO_AMT"));
				objReportsDto.setChargeTypeName(rs.getString("CHARGE_TYPE"));
				objReportsDto.setChargeName(rs.getString("CHARGE_NAME"));
				objReportsDto.setChargeEndDate(rs.getString("CSTATE_CHARGE_CURRENT_START_DATE"));
				if (rs.getString("CSTATE_CHARGE_CURRENT_START_DATE") != null && !"".equals(rs.getString("CSTATE_CHARGE_CURRENT_START_DATE")))
				{
					Date date=df.parse(objReportsDto.getChargeEndDate());
					objReportsDto.setChargeEndDate((utility.showDate_Report(date)).toUpperCase());
				}
				objReportsDto.setChargeAmount(rs.getDouble("INV_AMT"));
				objReportsDto.setLineamt(rs.getLong("LINEITEMAMOUNT"));
				objReportsDto.setAnnitation(rs.getString("ANNOTATION"));
				objReportsDto.setBillingtrigger_createdate(rs.getString("BILLING_TRIGGER_CREATEDATE"));
				if (rs.getString("BILLING_TRIGGER_CREATEDATE") != null && !"".equals(rs.getString("BILLING_TRIGGER_CREATEDATE")))
				{
					objReportsDto.setBillingtrigger_createdate((utility.showDate_Report(new Date(rs.getTimestamp("BILLING_TRIGGER_CREATEDATE").getTime()))).toUpperCase());
				}
				objReportsDto.setTokenno(rs.getString("CSTATE_START_DETAILS_FX_TOKEN_NO"));
				objReportsDto.setFx_status(rs.getString("CSTATE_START_DETAILS_FX_STATUS"));
				objReportsDto.setFx_sd_status(rs.getString("CSTATE_FX_CHARGE_START_STATUS"));
				objReportsDto.setBusiness_serial_no(rs.getString("Business_No"));
				objReportsDto.setOpms_act_id(rs.getString("Opms_Account_Id"));
			}
			if(objDto.getIsUsage() == 1)
			{
				objReportsDto.setRatio(rs.getString("RATIO"));
				objReportsDto.setCreditPeriodName(rs.getString("CREDIT_PERIODNAME"));
				objReportsDto.setFx_external_acc_id(rs.getString("Child_Account_Number"));
				objReportsDto.setFxInternalId(rs.getInt("INTERNAL_ID"));
				objReportsDto.setChild_account_creation_status(rs.getString("Child_Account_FX_Sataus"));
				objReportsDto.setPackageID(rs.getInt("PACKAGE_ID"));
				objReportsDto.setPackageName(rs.getString("PACKAGE_NAME"));
				objReportsDto.setComponentID(rs.getInt("COMPONENT_ID"));
				objReportsDto.setComponentName(rs.getString("COMPONENT_NAME"));
				objReportsDto.setComponentInfoID(rs.getInt("COMPONENTINFOID"));
				ComponentsDto dto = new ComponentsDto();
				dto.setComponentType(rs.getString("COMPONENT_TYPE"));
				dto.setComponentAmount(rs.getDouble("COMP_AMOUNT"));
				dto.setComponentStatus(rs.getString("COMPONENT_STATUS"));
				dto.setStartDate(rs.getString("SYSTEM_START_DATE"));
				if (rs.getString("SYSTEM_START_DATE") != null && !"".equals(rs.getString("SYSTEM_START_DATE")))
				{
					Date date=df.parse(dto.getStartDate());
					dto.setStartDate((utility.showDate_Report(date)).toUpperCase());
				}dto.setFxTokenNo(rs.getString("START_COMPONENT_TOKEN_NO"));
				dto.setComponentInstanceID(rs.getString("COMPONENT_INST_ID"));
				dto.setFxStartStatus(rs.getString("SYSTEM_START_STATUS"));
				dto.setEnd_date(rs.getString("SYSTEM_END_DATE"));
				if (rs.getString("SYSTEM_END_DATE") != null && !"".equals(rs.getString("SYSTEM_END_DATE")))
				{
					Date date=df.parse(dto.getEnd_date());
					dto.setEnd_date((utility.showDate_Report(date)).toUpperCase());
				}
				dto.setEndFxStatus(rs.getString("SYSTEM_END_STATUS"));
				objReportsDto.setComponentDto(dto);
			}
			
			if (pagingSorting.isPagingToBeDone()) {
				recordCount = rs.getInt("FULL_REC_COUNT");
			}
			listSearchDetails.add(objReportsDto);
		}
		pagingSorting.setRecordCount(recordCount);
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(proc);
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return listSearchDetails;	
}
public ArrayList<CancelledFailedLineReportDTO> viewCancelledFailedLineReport(CancelledFailedLineReportDTO objDto) 
{
	//	Nagarjuna
	String methodName="viewCancelledFailedLineReport", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection = null;
	CallableStatement proc = null;
	ResultSet rs = null;
	int recordCount = 0;
	ArrayList<CancelledFailedLineReportDTO> listSearchDetails = new ArrayList<CancelledFailedLineReportDTO>();
	CancelledFailedLineReportDTO objReportsDto = null;
	Utility utility=new Utility();
	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	Date tempDate=null;
	Timestamp ts=null;
	try
	{
		connection=DbConnection.getReportsConnectionObject();
		proc= connection.prepareCall(sqlGetCancelledFailedLineReportsForUsage);
		
		
		
		if (objDto.getFromDate() != null
				&& !"".equals(objDto.getFromDate())) {
			proc.setString(1, objDto.getFromDate().trim());
		} else {
			proc.setNull(1, java.sql.Types.VARCHAR);
		}
		
		if (objDto.getToDate() != null 
				&& !"".equals(objDto.getToDate())) {
			proc.setString(2, objDto.getToDate().trim());
		} else {
			proc.setNull(2, java.sql.Types.VARCHAR);
		}
		
		if (objDto.getServiceName() != null 
				&& !"".equals(objDto.getServiceName())) {
			proc.setString(3, objDto.getServiceName().trim());
		} else {
			proc.setNull(3, java.sql.Types.VARCHAR);
		}
		
		PagingSorting pagingSorting = objDto.getPagingSorting();
		pagingSorting.sync();// To calculate start index and Enc Index

		proc.setString(4, pagingSorting.getSortByColumn());// columnName
		proc.setString(5, PagingSorting.DB_Asc_Desc(pagingSorting
				.getSortByOrder()));// sort order
		proc.setInt(6, pagingSorting.getStartRecordId());// start index
		 proc.setInt(7, pagingSorting.getEndRecordId());// end index
		proc.setInt(8, (pagingSorting.isPagingToBeDone() ? 1 : 0));
		proc.setInt(9, objDto.getIsUsage());
		// index
		rs = proc.executeQuery();
		while(rs.next())
		{
			setBlank();
			replaceSeperator("PRIMARYLOCATION",rs.getString("PRIMARYLOCATION"));
			replaceSeperator("SECONDARYLOCATION",rs.getString("SECONDARYLOCATION"));
			objReportsDto =  new CancelledFailedLineReportDTO();
			objReportsDto.setPartyNo(rs.getInt("PARTY_NO"));
			objReportsDto.setAccountID(rs.getInt("CRMACCOUNTNO"));
			objReportsDto.setPartyName(rs.getString("PARTYNAME"));
			objReportsDto.setVerticalDetails(rs.getString("VERTICAL_DETAILS"));
			objReportsDto.setRegionName(rs.getString("REGION"));
			objReportsDto.setOrderNumber(rs.getInt("ORDERNO"));
			objReportsDto.setOrder_type(rs.getString("ORDERTYPE"));
			objReportsDto.setServiceName(rs.getString("SERVICENAME"));
			objReportsDto.setCustomer_logicalSINumber(rs.getInt("CUSTOMER_LOGICAL_SI_NO"));
			objReportsDto.setLinename(rs.getString("SERVICEDETDESCRIPTION"));
			objReportsDto.setOrdersubtype(rs.getString("ORDERSUBTYPE"));
			objReportsDto.setCkt_id(rs.getString("CKTID"));
			objReportsDto.setBandwaidth(rs.getString("BANDWIDTH"));
			objReportsDto.setUom(rs.getString("UOM"));
			objReportsDto.setBilling_bandwidth(rs.getString("BILLING_BANDWIDTH"));
			objReportsDto.setBilling_uom(rs.getString("BILLING_BANDWIDTH_UOM"));
			objReportsDto.setDistance(rs.getString("DISTANCE"));
			objReportsDto.setRatio(rs.getString("RATIO"));
			objReportsDto.setLocation_from(rs.getString("FROM_ADDRESS"));
			objReportsDto.setLocation_to(rs.getString("TO_ADDRESS"));
			// change
			objReportsDto.setPrimarylocation(VAR_PRIMARYLOCATION);
			objReportsDto.setSeclocation(VAR_SECONDARYLOCATION);
			objReportsDto.setProductName(rs.getString("PRODUCTNAME"));
			objReportsDto.setSubProductName(rs.getString("SUBPRODUCTNAME"));
			objReportsDto.setEntity(rs.getString("ENTITYNAME"));
			objReportsDto.setLcompanyname(rs.getString("LCOMPANYNAME"));
			objReportsDto.setCurrencyName(rs.getString("CURRENCYNAME"));
			
			objReportsDto.setBillingTypeName(rs.getString("BILLING_TYPENAME"));
			objReportsDto.setBillingFormatName(rs.getString("BILLING_FORMATNAME"));
			objReportsDto.setBillingLevel(rs.getString("BILLING_LEVELNAME"));
			objReportsDto.setBillingLevelNo(rs.getLong("BILLING_LEVEL_NO"));
			objReportsDto.setTaxation(rs.getString("TAXATIONVALUE"));
			
			//objReportsDto.setBill_period(rs.getString("BILL_PERIOD"));
			if((rs.getString("CONFIG_ID")) != null && !"".equals(rs.getString("CONFIG_ID")) && 
					(rs.getString("ENTITYID")) != null && !"".equals(rs.getString("ENTITYID")) ) {
				String tBillPeriod=fnGetUsageReportBIllPeriod(rs.getString("CONFIG_ID"), rs.getString("ENTITYID"));
				objReportsDto.setBill_period(tBillPeriod);
			}
			objReportsDto.setBillingMode(rs.getString("BILLINGMODE"));
			objReportsDto.setPoNumber(rs.getInt("PONUMBER"));
			// change
			tempDate=rs.getDate("PODATE");
			if(tempDate!=null)
			{
				objReportsDto.setPoDate((Utility.showDate_Report(tempDate)).toUpperCase());
			}
			objReportsDto.setContractPeriod(rs.getInt("CONTRACTPERIOD"));
			objReportsDto.setTotalAmountIncludingCurrent(rs.getDouble("TOTALPOAMOUNT"));
			// change
			tempDate=rs.getDate("PORECEIVEDATE");
			if(tempDate!=null)
			{
				objReportsDto.setPoReceiveDate((Utility.showDate_Report(tempDate)).toUpperCase());
			}
			objReportsDto.setCustPoDetailNo(rs.getString("CUSTPONUMBER"));
			// change
			tempDate=rs.getDate("CUSTPODATE");
			if(tempDate!=null)
			{
				objReportsDto.setCustPoDate((Utility.showDate_Report(tempDate)).toUpperCase());
			}
			
			objReportsDto.setLineno(rs.getInt("ORDER_LINE_ID"));
			
//			Saurabh : Changes to separate charge related specific column from common
			if(objDto.getIsUsage() == 0)
			{
				
				objReportsDto.setCreditPeriod(rs.getInt("CREDITPERIOD"));
				
				objReportsDto.setHardwaretypeName(rs.getString("HARDWARETYPENAME"));
				objReportsDto.setHardware_flag(rs.getString("HARDWARE_FLAG"));
				objReportsDto.setStorename(rs.getString("STORENAME"));
				objReportsDto.setSaleType(rs.getString("SALETYPE"));
				
				objReportsDto.setCust_total_poamt(rs.getDouble("CUST_TOT_PO_AMT"));
				objReportsDto.setChargeTypeName(rs.getString("CHARGE_TYPE"));
				objReportsDto.setChargeName(rs.getString("CHARGE_NAME"));
				objReportsDto.setChargeEndDate(rs.getString("CSTATE_CHARGE_CURRENT_START_DATE"));
				objReportsDto.setChargeAmount(rs.getDouble("INV_AMT"));
				objReportsDto.setLineamt(rs.getLong("LINEITEMAMOUNT"));
				objReportsDto.setAnnitation(rs.getString("ANNOTATION"));
				objReportsDto.setTokenno(rs.getString("CSTATE_START_DETAILS_FX_TOKEN_NO"));
				objReportsDto.setFx_status(rs.getString("CSTATE_START_DETAILS_FX_STATUS"));
				objReportsDto.setFx_sd_status(rs.getString("CSTATE_FX_CHARGE_START_STATUS"));
				objReportsDto.setBusiness_serial_no(rs.getString("Business_No"));
				objReportsDto.setOpms_act_id(rs.getString("Opms_Account_Id"));
			}
			
			
			//Meenakshi : Changes for Usage
			if(objDto.getIsUsage() == 1)
			{
				objReportsDto.setCreditPeriodName(rs.getString("CREDIT_PERIODNAME"));
				
				objReportsDto.setFx_external_acc_id(rs.getString("Child_Account_Number"));
				objReportsDto.setFxInternalId(rs.getInt("INTERNAL_ID"));
				objReportsDto.setChild_account_creation_status(rs.getString("Child_Account_FX_Sataus"));
				objReportsDto.setPackageID(rs.getInt("PACKAGE_ID"));
				objReportsDto.setPackageName(rs.getString("PACKAGE_NAME"));
				objReportsDto.setComponentID(rs.getInt("COMPONENT_ID"));
				objReportsDto.setComponentName(rs.getString("COMPONENT_NAME"));
				objReportsDto.setComponentInfoID(rs.getInt("COMPONENTINFOID"));
				objReportsDto.setServiceId(rs.getInt("SERVICE_NO"));
				objReportsDto.setLogicalSINo(rs.getString("LOGICAL_SI_NO"));
				ComponentsDto dto = new ComponentsDto();

				
				dto.setComponentType(rs.getString("COMPONENT_TYPE"));
				dto.setComponentAmount(rs.getDouble("COMP_AMOUNT"));
				dto.setComponentStatus(rs.getString("COMPONENT_STATUS"));
				
				dto.setStartDate(rs.getString("COMPONENT_START_DATE"));
				if (rs.getString("COMPONENT_START_DATE") != null && !"".equals(rs.getString("COMPONENT_START_DATE")))
				{
					
					Date date=df.parse(dto.getStartDate());
					dto.setStartDate((utility.showDate_Report(date)).toUpperCase());
					
				}
				
				dto.setFxTokenNo(rs.getString("START_COMPONENT_TOKEN_NO"));
				dto.setFxStartStatus(rs.getString("SYSTEM_START_STATUS"));
				dto.setComponentInstanceID(rs.getString("COMPONENT_INST_ID"));
				objReportsDto.setComponentDto(dto);
			}
			
			/// End
			
			if (pagingSorting.isPagingToBeDone()) {
				recordCount = rs.getInt("FULL_REC_COUNT");
			}
			listSearchDetails.add(objReportsDto);
		}
		pagingSorting.setRecordCount(recordCount);
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();	
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(proc);
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return listSearchDetails;	
}




public ArrayList<ReportsDto> viewBulkSIUploadReport(ReportsDto objDto) 
{
	//	Nagarjuna
	String methodName="viewBulkSIUploadReport", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection = null;
	CallableStatement proc = null;
	ResultSet rs = null;
	int recordCount = 0;
	ArrayList<ReportsDto> listSearchDetails = new ArrayList<ReportsDto>();
	ReportsDto objReportsDto = null;
	Utility utility=new Utility();
	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	try
	{
		connection=DbConnection.getReportsConnectionObject();
		proc= connection.prepareCall(sqlGetBulkUploadReports);
		
		if (objDto.getDate_of_inst() != null
				&& !"".equals(objDto.getDate_of_inst())) {
			proc.setString(1, objDto.getDate_of_inst().trim());
		} else {
			proc.setNull(1, java.sql.Types.VARCHAR);
		}
		
		if (objDto.getServiceName() != null
				&& !"".equals(objDto.getServiceName())) {
			proc.setString(2, objDto.getServiceName().trim());
		} else {
			proc.setNull(2, java.sql.Types.VARCHAR);
		}
		
		
		if (objDto.getParty_no() != 0 
				&& !"".equals(objDto.getParty_no())) {
			proc.setInt(3, objDto.getParty_no());
		} else {
			proc.setNull(3,java.sql.Types.BIGINT);
		}
		
		if (objDto.getOrderNo() != null
				&& !"".equals(objDto.getOrderNo())) {
			proc.setString(4, objDto.getOrderNo().trim());
		} else {
			proc.setNull(4, java.sql.Types.VARCHAR);
		}
		
		
		if (objDto.getLogicalSINo() != null
				&& !"".equals(objDto.getLogicalSINo())) {
			proc.setString(5, objDto.getLogicalSINo().trim());
		} else {
			proc.setNull(5, java.sql.Types.VARCHAR);
		}
		
		if (objDto.getLinename() != null
				&& !"".equals(objDto.getLinename())) {
			proc.setString(6, objDto.getLinename().trim());
		} else {
			proc.setNull(6, java.sql.Types.VARCHAR);
		}
		
	

		PagingSorting pagingSorting = objDto.getPagingSorting();
		pagingSorting.sync();// To calculate start index and Enc Index

		proc.setString(7, pagingSorting.getSortByColumn());// columnName
		proc.setString(8, PagingSorting.DB_Asc_Desc(pagingSorting
				.getSortByOrder()));// sort order
		proc.setInt(9, pagingSorting.getStartRecordId());// start index
		 proc.setInt(10, pagingSorting.getEndRecordId());// end index
		proc.setInt(11, (pagingSorting.isPagingToBeDone() ? 1 : 0));
		// index
		rs = proc.executeQuery();
		while(rs.next())
		{
			objReportsDto =  new ReportsDto();
			objReportsDto.setAccountName(rs.getString("ACCOUNTNAME"));
			objReportsDto.setOrderNumber(rs.getInt("CRM_ORDER_MOCN_NO"));
			objReportsDto.setCrm_service_opms_id(rs.getString("CRM_SERVICE_OPMS_ID"));
			objReportsDto.setServiceName(rs.getString("SERVICETYPE"));
			objReportsDto.setInstallation_addressaa1(rs.getString("INSTALLATION_ADDRESSA1"));
			objReportsDto.setInstallation_addressaa2(rs.getString("INSTALLATION_ADDRESSA2"));
			objReportsDto.setInstallation_addressaa3(rs.getString("INSTALLATION_ADDRESSA3"));
			objReportsDto.setFrom_city(rs.getString("FROM_CITY"));
			objReportsDto.setFrom_state(rs.getString("FROM_STATE"));
			objReportsDto.setFrom_country(rs.getString("FROM_COUNTRY"));
			objReportsDto.setInstallation_addressab1(rs.getString("INSTALLATION_ADDRESSB1"));
			objReportsDto.setInstallation_addressab2(rs.getString("INSTALLATION_ADDRESSB2"));
			objReportsDto.setInstallation_addressab3(rs.getString("INSTALLATION_ADDRESSB3"));
			objReportsDto.setTo_city(rs.getString("TO_CITY"));
			objReportsDto.setTo_state(rs.getString("TO_STATE"));
			objReportsDto.setTo_country(rs.getString("TO_COUNTRY"));
			objReportsDto.setDate_of_inst(rs.getString("DATE_OF_INST"));
			objReportsDto.setDate_of_act(rs.getString("DATE_OF_ACT"));
			if (rs.getString("DATE_OF_ACT") != null && !"".equals(rs.getString("DATE_OF_ACT")))
			{
			Date date=df.parse(objReportsDto.getDate_of_act());
			objReportsDto.setDate_of_act((utility.showDate_Report(date)).toUpperCase());
			}
			objReportsDto.setBandwaidth(rs.getString("BANDWIDTH"));
			objReportsDto.setUom(rs.getString("UOM"));
			objReportsDto.setParent_name(rs.getString("PARENT_PRODUCT"));
			objReportsDto.setParent_circuit(rs.getString("PARENT_CIRCUIT"));
			objReportsDto.setLob(rs.getString("LOB"));
			objReportsDto.setCircle(rs.getString("CIRCLE"));
			objReportsDto.setZone(rs.getString("ZONE"));
			objReportsDto.setLocation_from(rs.getString("SUPPORT_LOCATION_A"));
			objReportsDto.setLocation_to(rs.getString("SUPPORT_LOCATION_B"));
			objReportsDto.setCommited_sla(rs.getString("COMMITED_SLA"));
			objReportsDto.setHub_location(rs.getString("UB_LOCATION"));
			objReportsDto.setPlatform(rs.getString("PLATFORM"));
			objReportsDto.setCustomer_logicalSINumber(rs.getInt("CUSTOMER_LOGICAL_SI_NO"));
			objReportsDto.setSi_id(rs.getInt("SIID"));
			objReportsDto.setIpls(rs.getString("IPLC"));
			objReportsDto.setManaged_yes_no(rs.getString("MANAGED_YES_NO"));
			objReportsDto.setActmngname(rs.getString("ACTMNAME"));
			objReportsDto.setPrjmngname(rs.getString("PMNAME"));
			objReportsDto.setTl(rs.getString("TL"));
			objReportsDto.setService_provider(rs.getString("LAST_MILE_PROVIDER"));
			
			
			
			if (pagingSorting.isPagingToBeDone()) {
				recordCount = rs.getInt("FULL_REC_COUNT");
			}
			listSearchDetails.add(objReportsDto);
		}
		pagingSorting.setRecordCount(recordCount);
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(proc);
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return listSearchDetails;	
}

public ArrayList<ReportsDto> viewAttributeDetailsReport(ReportsDto objDto) 
{
	//	Nagarjuna
	String methodName="viewAttributeDetailsReport", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection = null;
	CallableStatement proc = null;
	ResultSet rs = null;
	int recordCount = 0;
	ArrayList<ReportsDto> listAttributeDetailsReport = new ArrayList<ReportsDto>();
	ReportsDto objReportsDto = null;
	try
	{
		connection=DbConnection.getReportsConnectionObject();
		proc= connection.prepareCall(sqlGetAttributeDetailsReport);
		
		if (objDto.getCustLogicalSI() != 0 
				&& !"".equals(objDto.getCustLogicalSI())) {
			proc.setInt(1, objDto.getCustLogicalSI());
		} else {
			proc.setNull(1, java.sql.Types.BIGINT);
		}

		PagingSorting pagingSorting = objDto.getPagingSorting();
		pagingSorting.sync();// To calculate start index and Enc Index

		proc.setString(2, pagingSorting.getSortByColumn());// columnName
		proc.setString(3, PagingSorting.DB_Asc_Desc(pagingSorting
				.getSortByOrder()));// sort order
		proc.setInt(4, pagingSorting.getStartRecordId());// start index
		 proc.setInt(5, pagingSorting.getEndRecordId());// end index
		proc.setInt(6, (pagingSorting.isPagingToBeDone() ? 1 : 0));
		// index
		rs = proc.executeQuery();
		while(rs.next())
		{
			objReportsDto =  new ReportsDto();
		
			objReportsDto.setServiceName(rs.getString("SERVICENAME"));
			objReportsDto.setLinename(rs.getString("LINENAME"));
			objReportsDto.setCrm_att(rs.getString("CRM_ATT"));
			objReportsDto.setM6_label_name(rs.getString("LABEL_NAME"));
			objReportsDto.setM6_label_value(rs.getString("LABEL_VALUE"));
			objReportsDto.setCustLogicalSI(rs.getInt("CUSTOMER_LOGICAL_SI_NO"));
			objReportsDto.setCrm_order_id(rs.getInt("LAST_CRM_ORDER_ID"));
			
			
			if (pagingSorting.isPagingToBeDone()) {
				recordCount = rs.getInt("FULL_REC_COUNT");
			}
			listAttributeDetailsReport.add(objReportsDto);
		}
		pagingSorting.setRecordCount(recordCount);
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(proc);
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return listAttributeDetailsReport;	
}

	public static String sqlGetDisconnectionChangeOrderReport="{call IOE.SP_GET_DISCONNECT_CHANGE_ORDER_REPORT_USAGE(?,?,?,?,?,?,?,?,?,?,?,?,?)}";

public ArrayList<DisconnectChangeOrdeReportDTO> viewDisconnectionChangeOrderReport(DisconnectChangeOrdeReportDTO objDto) 
{
	//	Nagarjuna
	String methodName="viewDisconnectionChangeOrderReport", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection = null;
	CallableStatement proc = null;
	ResultSet rs = null;
	int recordCount = 0;
	ArrayList<DisconnectChangeOrdeReportDTO> listSearchDetails = new ArrayList<DisconnectChangeOrdeReportDTO>();
	DisconnectChangeOrdeReportDTO objReportsDto = null;
	Utility utility=new Utility();
	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	Date tempDate=null;
	Timestamp ts=null;
	try
	{
		connection=DbConnection.getReportsConnectionObject();
		proc= connection.prepareCall(sqlGetDisconnectionChangeOrderReport);
		
		if (objDto.getOrdermonth() != null
				&& !"".equals(objDto.getOrdermonth())) {
			proc.setString(1, objDto.getOrdermonth().trim());
		} else {
			proc.setNull(1, java.sql.Types.VARCHAR);
		}
		
		if (objDto.getFromDate() != null
				&& !"".equals(objDto.getFromDate())) {
			proc.setString(2, objDto.getFromDate().trim());
		} else {
			proc.setNull(2, java.sql.Types.VARCHAR);
		}
		
		if (objDto.getToDate() != null 
				&& !"".equals(objDto.getToDate())) {
			proc.setString(3, objDto.getToDate().trim());
		} else {
			proc.setNull(3, java.sql.Types.VARCHAR);
		}
		if (objDto.getServiceName() != null 
				&& !"".equals(objDto.getServiceName())) {
			proc.setString(4, objDto.getServiceName().trim());
		} else {
			proc.setNull(4, java.sql.Types.VARCHAR);
		}
		
		if (objDto.getOrdersubtype() != null
				&& !"".equals(objDto.getOrdersubtype())) {
			proc.setInt(5, Integer.parseInt(objDto.getOrdersubtype().trim()));
		} else {
			proc.setNull(5, java.sql.Types.BIGINT);
		}
		
		if (objDto.getVerticalDetails() != null 
				&& !"".equals(objDto.getVerticalDetails())) {
			proc.setString(6, objDto.getVerticalDetails().trim());
		} else {
			proc.setNull(6, java.sql.Types.VARCHAR);
		}
		
		PagingSorting pagingSorting = objDto.getPagingSorting();
		pagingSorting.sync();// To calculate start index and Enc Index

		proc.setString(7, pagingSorting.getSortByColumn());// columnName
		proc.setString(8, PagingSorting.DB_Asc_Desc(pagingSorting
				.getSortByOrder()));// sort order
		proc.setInt(9, pagingSorting.getStartRecordId());// start index
		 proc.setInt(10, pagingSorting.getEndRecordId());// end index
		proc.setInt(11, (pagingSorting.isPagingToBeDone() ? 1 : 0));
		proc.setInt(12,objDto.getIsUsage());
			if (objDto.getSrrequest() != null 
					&& !"".equals(objDto.getSrrequest())) {
				proc.setString(13, objDto.getSrrequest());
			} else {
				proc.setNull(13, java.sql.Types.VARCHAR);
			}
		// index
		rs = proc.executeQuery();
		while(rs.next())
		{ 
			objReportsDto =  new DisconnectChangeOrdeReportDTO();
			//[270513]Start : Added by Ashutosh for Billing Address
			setBlank();
			//replaceSeperator("BILLING_LOCATION",rs.getString("BILLING_ADDRESS"));
			replaceSeperator("PRIMARYLOCATION",rs.getString("PRIMARYLOCATION"));
			replaceSeperator("SECONDARYLOCATION",rs.getString("SECONDARYLOCATION"));		
			//[270513]Start
			objReportsDto.setLogicalCircuitId(rs.getString("LOGICALCIRCUITID"));
			objReportsDto.setServiceName(rs.getString("SERVICENAME"));
			objReportsDto.setLinename(rs.getString("SERVICEDETDESCRIPTION"));			
			objReportsDto.setAccountID(rs.getInt("CRMACCOUNTNO"));
			objReportsDto.setCreditPeriod(rs.getInt("CREDITPERIOD"));
			objReportsDto.setCurrencyName(rs.getString("CURRENCYNAME"));
			objReportsDto.setEntity(rs.getString("ENTITYNAME"));
			objReportsDto.setBillingMode(rs.getString("BILLINGMODE"));
			objReportsDto.setBilling_Trigger_Flag(rs.getString("Billing_Trigger_Flag"));
			objReportsDto.setBillingTypeName(rs.getString("BILLING_TYPENAME"));
			objReportsDto.setBillingformat(rs.getString("BILLING_FORMATNAME"));
			objReportsDto.setLicCompanyName(rs.getString("LCOMPANYNAME"));
			objReportsDto.setTaxation(rs.getString("TAXATIONVALUE"));
			objReportsDto.setPenaltyClause(rs.getString("PENALTYCLAUSE"));
			objReportsDto.setBillingLevel(rs.getString("BILLINGLEVEL"));
			objReportsDto.setBillingLevelId(rs.getInt("BILLING_LEVEL_NO"));			
			objReportsDto.setPoNumber(rs.getInt("PONUMBER"));
			tempDate=rs.getDate("PODATE");
			if(tempDate!=null)
			{
				objReportsDto.setPoDate((Utility.showDate_Report(tempDate)).toUpperCase());				
			}
			objReportsDto.setParty(rs.getString("PARTYNAME"));
			objReportsDto.setPm_pro_date(rs.getString("Pm_Prov_Date"));			
			objReportsDto.setLocDate(rs.getString("LOCDATE"));
			if(!(rs.getString("LOCDATE")==null || rs.getString("LOCDATE")==""))
			{				
				Date date=df.parse(objReportsDto.getLocDate());
				objReportsDto.setLocDate((utility.showDate_Report(date)).toUpperCase());			
			}
			objReportsDto.setBillingTriggerDate(rs.getString("BILLINGTRIGGERDATE"));
			
			objReportsDto.setChild_act_no(rs.getString("Child_Account_Number"));
			objReportsDto.setChild_ac_fxstatus(rs.getString("Child_Account_FX_Sataus"));
			tempDate=rs.getDate("ORDERDATE");
			if(tempDate!=null)
			{
				objReportsDto.setOrderDate((utility.showDate_Report(tempDate)).toUpperCase());
			}
			objReportsDto.setOrder_type(rs.getString("ORDERTYPE"));
			objReportsDto.setServiceOrderType(rs.getString("SERVICE_ORDER_TYPE_DESC"));
			ts=rs.getTimestamp("BILLING_TRIGGER_CREATEDATE");
			if(ts!=null)
			{
				tempDate=new Date(ts.getTime());
				objReportsDto.setBillingtrigger_createdate(Utility.showDate_Report(tempDate).toUpperCase());
			}
			ts=rs.getTimestamp("BILLING_TRIGGER_CREATEDATE");
			if(ts!=null)
			{
				tempDate=new Date(ts.getTime());
				objReportsDto.setBillingtrigger_createdate((utility.showDate_Report(tempDate)).toUpperCase());
				
			}
			objReportsDto.setCustomer_logicalSINumber(rs.getInt("CUSTOMER_LOGICAL_SI_NO"));			
			objReportsDto.setServiceStage(rs.getString("SERVICE_STAGE"));
			objReportsDto.setOrderStage(rs.getString("STAGE"));
			objReportsDto.setActmgrname(rs.getString("ACCOUNTMANAGER"));
			objReportsDto.setPrjmngname(rs.getString("PROJECTMANAGER"));
			tempDate=rs.getDate("ORDERDATE");
			if(tempDate!=null)
			{
				objReportsDto.setOrderDate((Utility.showDate_Report(tempDate)).toUpperCase());
			}
			tempDate=rs.getDate("SERVICE_RFS_DATE");
			if(!(rs.getString("SERVICE_RFS_DATE")==null || rs.getString("SERVICE_RFS_DATE")==""))
			{
				objReportsDto.setRfs_date((Utility.showDate_Report(tempDate)).toUpperCase());
			}
			tempDate=rs.getDate("PORECEIVEDATE");
			if(tempDate!=null)
			{
				objReportsDto.setCust_po_rec_date((Utility.showDate_Report(tempDate)).toUpperCase());
			}			
			objReportsDto.setVerticalDetails(rs.getString("VERTICAL_DETAILS"));
			objReportsDto.setRegion(rs.getString("REGION"));
			objReportsDto.setDemo(rs.getString("Demo_Type"));
			objReportsDto.setNewOrderRemark(rs.getString("NEWORDER_REMARKS"));
			objReportsDto.setOrderStageDescription(rs.getString("STAGE"));
			objReportsDto.setMocn_no(rs.getString("MOCN_NO"));
			objReportsDto.setDisconnection_remarks(rs.getString("DISCONNECTION_REMARKS"));			
			objReportsDto.setRequest_rec_date(rs.getString("DISCONNECTION_RECEIVE_DATE"));
			if(!(rs.getString("DISCONNECTION_RECEIVE_DATE")==null || rs.getString("DISCONNECTION_RECEIVE_DATE")==""))
			{
				
				//Date date=df.parse(objReportsDto.getRequest_rec_date());
				//objReportsDto.setRequest_rec_date((utility.showDate_Report(date)).toUpperCase());
				
			}
			objReportsDto.setLineno(rs.getInt("Order_Line_Id"));
			
			objReportsDto.setOrdermonth(rs.getString("ORDERMONTH"));
			objReportsDto.setCkt_id(rs.getString("CKTID"));
			objReportsDto.setStandard_reason(rs.getString("STANDARDREASON"));
			objReportsDto.setBandwaidth(rs.getString("BANDWIDTH"));
			objReportsDto.setBilling_bandwidth(rs.getString("BILLING_BANDWIDTH"));
			objReportsDto.setBilling_uom(rs.getString("BILLING_BANDWIDTH_UOM"));
			objReportsDto.setBandwidth_att(rs.getString("BANDWIDTH_ATT"));
			objReportsDto.setUom(rs.getString("UOM"));
			objReportsDto.setRate_code(rs.getString("RATECODE"));
			objReportsDto.setLast_mile_media(rs.getString("LAST_MILE_MEDIA"));
			objReportsDto.setLast_mile_remarks(rs.getString("LAST_MILE_REMARKS"));
			objReportsDto.setChargeable_Distance(rs.getString("DISTANCE"));
			objReportsDto.setLast_mile_provider(rs.getString("LAST_MILE_PROVIDER"));
			objReportsDto.setLink_type(rs.getString("LINK_TYPE"));			
			objReportsDto.setIndicative_value(rs.getString("INDICATIVE_VALUE"));
			objReportsDto.setProductName(rs.getString("SERVICEDETDESCRIPTION"));
			tempDate=rs.getDate("CUSTPODATE");
			if(tempDate!=null)
			{
				objReportsDto.setCustPoDate((Utility.showDate_Report(tempDate)).toUpperCase());
			}
			objReportsDto.setCustPoDetailNo(rs.getString("CUSTPONUMBER"));
			objReportsDto.setLocno(rs.getString("LOCNO"));
			objReportsDto.setPrimarylocation(VAR_PRIMARYLOCATION);
			objReportsDto.setProdAlisName(rs.getString("PRODUCTNAME"));
			objReportsDto.setRatio(rs.getString("RATIO"));
			objReportsDto.setSeclocation(VAR_SECONDARYLOCATION);
			objReportsDto.setSub_linename(rs.getString("SUBPRODUCTNAME"));			
			objReportsDto.setOrderNo(rs.getString("ORDERNO"));			
			objReportsDto.setOrderLineNumber(rs.getInt("Order_Line_Id"));
			objReportsDto.setCustomer_logicalSINumber(rs.getInt("CUSTOMER_LOGICAL_SI_NO"));
			objReportsDto.setServiceId(rs.getInt("SERVICE_NO"));			
			objReportsDto.setAmt(rs.getLong("CUST_TOT_PO_AMT"));
			objReportsDto.setTotalAmountIncludingCurrent(rs.getDouble("TOTALPOAMOUNT"));
			objReportsDto.setAdvance(rs.getString("ADVANCE"));			
			objReportsDto.setContractMonths(rs.getInt("CONTRACTPERIOD"));
			objReportsDto.setCommitmentPeriod(rs.getInt("COMMITMENTPERIOD"));
			objReportsDto.setNoticePeriod(rs.getInt("NOTICEPERIOD"));
			objReportsDto.setPeriodsInMonths(rs.getString("PERIODS_IN_MONTH"));
			objReportsDto.setTotalPoAmt(rs.getString("CUST_TOT_PO_AMT"));
			objReportsDto.setParty_id(rs.getInt("PARTY_NO"));
			objReportsDto.setCust_act_id(rs.getString("CUSTACCOUNTID"));
			objReportsDto.setM6_prod_id(rs.getString("M6_PRODUCT_ID"));
			objReportsDto.setM6_order_id(rs.getString("M6ORDERNO"));
			objReportsDto.setPre_crmorderid(rs.getInt("Pre_Crm_orderNo"));
	        objReportsDto.setM6cktid(rs.getString("M6_CKTID"));			
			objReportsDto.setBilling_location_from(rs.getString("FROM_ADDRESS"));
			objReportsDto.setBilling_location_to(rs.getString("TO_ADDRESS"));
			
			if(objDto.getIsUsage() == 1)
			{
				objReportsDto.setOrderStage(rs.getString("ORDERSTAGE"));
				objReportsDto.setCrmAccountNoString(rs.getString("CRMACCOUNTNO"));
				objReportsDto.setCreditPeriodName(rs.getString("CREDIT_PERIODNAME"));
				objReportsDto.setBillingLevelName(rs.getString("BILLING_LEVELNAME"));				
				objReportsDto.setBillingTriggerDate(rs.getString("BILLINGTRIGGERDATE"));
				if (rs.getString("BILLINGTRIGGERDATE") != null && !"".equals(rs.getString("BILLINGTRIGGERDATE")))
				{
					
					Date date=df.parse(objReportsDto.getBillingTriggerDate());
					objReportsDto.setBillingTriggerDate((Utility.showDate_Report(date)).toUpperCase());
					
				}
				objReportsDto.setLineno(rs.getInt("Order_Line_Id"));
				
				objReportsDto.setComponentInfoID(rs.getInt("COMPONENTINFOID"));
				objReportsDto.setComponentID(rs.getInt("COMPONENT_ID"));
				objReportsDto.setComponentName(rs.getString("COMPONENT_NAME"));
				objReportsDto.setPackageID(rs.getInt("PACKAGE_ID"));
				objReportsDto.setPackageName(rs.getString("PACKAGE_NAME"));
				ComponentsDto dto = new ComponentsDto();
				dto.setComponentStatus(rs.getString("COMPONENT_STATUS"));
				dto.setStartLogic(rs.getString("COMPONENT_START_LOGIC"));
				dto.setStartDate(rs.getString("COMPONENT_START_DATE"));
				if (rs.getString("COMPONENT_START_DATE") != null && !"".equals(rs.getString("COMPONENT_START_DATE")))
				{
					
					Date date=df.parse(dto.getStartDate());
					dto.setStartDate((Utility.showDate_Report(date)).toUpperCase());
					
				}
				dto.setEndLogic(rs.getString("COMPONENT_END_LOGIC"));
				dto.setEnd_date(rs.getString("COMPONENT_END_DATE"));
				if (rs.getString("COMPONENT_END_DATE") != null && !"".equals(rs.getString("COMPONENT_END_DATE")))
				{
					
					Date date=df.parse(dto.getEnd_date());
					dto.setEnd_date((Utility.showDate_Report(date)).toUpperCase());
					
				}
				dto.setFxTokenNo(rs.getString("START_COMPONENT_TOKEN_NO"));
				dto.setFxStartStatus(rs.getString("SYSTEM_START_STATUS"));
				dto.setEndFxStatus(rs.getString("FX_END_STATUS"));
				dto.setEndTokenNo(rs.getString("LOCAL_END_COMPONENT_TOKEN_NO"));
				//dto.setComponentFXStatus(rs.getString("FX_STATUS"));
				objReportsDto.setStartDateDays(rs.getInt("COMP_START_DAYS"));
				objReportsDto.setStartDateMonth(rs.getInt("COMP_START_MONTHS"));
				objReportsDto.setEndDateDays(rs.getInt("COMP_END_DAYS"));
				objReportsDto.setEndDateMonth(rs.getInt("COMP_END_MONTHS"));
				objReportsDto.setSourcePartyID(rs.getLong("PARTY_ID"));
				objReportsDto.setAccountID(rs.getInt("CUSTACCOUNTID"));
				objReportsDto.setFx_internal_acc_id(rs.getLong("INTERNAL_ID"));
				dto.setComponentInstanceID(rs.getString("COMPONENT_INST_ID"));
				dto.setComponentType(rs.getString("COMPONENT_TYPE"));
				dto.setComponentAmount(rs.getDouble("COMP_AMOUNT"));
				
				
				
				objReportsDto.setComponentInfoID(rs.getInt("COMPONENTINFOID"));
				objReportsDto.setComponentID(rs.getInt("COMPONENT_ID"));
				objReportsDto.setComponentName(rs.getString("COMPONENT_NAME"));
				objReportsDto.setPackageID(rs.getInt("PACKAGE_ID"));
				objReportsDto.setPackageName(rs.getString("PACKAGE_NAME"));	
				objReportsDto.setCopcapprovaldate(rs.getString("COPC_APPROVED_DATE"));
				ts=rs.getTimestamp("COPC_APPROVED_DATE");
				if(ts!=null)
				{
					tempDate=new Date(ts.getTime());
					objReportsDto.setCopcapprovaldate((utility.showDate_Report(tempDate)).toUpperCase());
					
				}
					objReportsDto.setSrno(rs.getString("SR_NO"));
					objReportsDto.setCus_segment(rs.getString("CUST_SEGMENT_CODE"));
					
					objReportsDto.setDesiredDueDate(rs.getString("DESIRED_DUE_DATE"));
					objReportsDto.setDerivedDisconnectionDate(rs.getString("DERIVEDDISCONNECTIONDATE"));
					
					objReportsDto.setIsTriggerRequired(rs.getString("ISTRIGGERREQUIRED(Y/N)"));
					objReportsDto.setLineTriggered(rs.getString("LINETRIGGERED(Y/N)"));
					objReportsDto.setTriggerProcess(rs.getString("TRIGGERPROCESS"));
					objReportsDto.setTriggerDoneBy(rs.getString("BILLINGTRIGGERDONEBY"));
					objReportsDto.setAutomaticTriggerError(rs.getString("AUTOMATICTRIGGERERROR"));
					
					
					/*20151224-R1-021980 - Changes in Disconnection Report ||ends*/
	            objReportsDto.setComponentDto(dto);
			}else{
				objReportsDto.setOrderStage(rs.getString("STAGE"));
				objReportsDto.setChargeTypeName(rs.getString("CHARGE_TYPE"));
				objReportsDto.setChargeName(rs.getString("CHARGE_NAME"));
				objReportsDto.setFrequencyName(rs.getString("FREQUENCY"));
				if((rs.getString("CONFIG_ID")) != null && !"".equals(rs.getString("CONFIG_ID")) && (rs.getString("ENTITYID")) != null && !"".equals(rs.getString("ENTITYID")))
				{
					String tBillPeriod=fnGetUsageReportBIllPeriod(rs.getString("CONFIG_ID"), rs.getString("ENTITYID"));
					objReportsDto.setBill_period(tBillPeriod);
				}
				objReportsDto.setStartDateLogic(rs.getString("STARTDATELOGIC"));
				objReportsDto.setEndDateLogic(rs.getString("ENDDATELOGIC"));
				objReportsDto.setChargeEndDate(rs.getString("END_DATE"));
				if(!(rs.getString("END_DATE")==null || rs.getString("END_DATE")==""))
				{
					
					Date date=df.parse(objReportsDto.getChargeEndDate());
					objReportsDto.setChargeEndDate((utility.showDate_Report(date)).toUpperCase());
					
				}
				
				objReportsDto.setStartDate(rs.getString("START_DATE"));
				if(!(rs.getString("START_DATE")==null || rs.getString("START_DATE")==""))
				{
					
					Date date=df.parse(objReportsDto.getStartDate());
					objReportsDto.setStartDate((utility.showDate_Report(date)).toUpperCase());
					
				}
				objReportsDto.setStore(rs.getString("STORENAME"));
				objReportsDto.setHardwareType(rs.getString("HARDWARETYPENAME"));
				objReportsDto.setFormAvailable(rs.getString("FORM_C_AVAILABLE"));
				objReportsDto.setNature_sale(rs.getString("SALENATURE"));
				objReportsDto.setType_sale(rs.getString("SALETYPE"));
				objReportsDto.setAnnitation(rs.getString("ANNOTATION"));
				objReportsDto.setTokenno(rs.getString("CSTATE_START_DETAILS_FX_TOKEN_NO"));
				objReportsDto.setChallenno(rs.getString("CHALLEN_NO"));
				objReportsDto.setChallendate(rs.getString("CHALLEN_DATE"));
				if(!(rs.getString("CHALLEN_DATE")==null || rs.getString("CHALLEN_DATE")==""))
				{
					Date date=df.parse(objReportsDto.getChallendate());
					objReportsDto.setChallendate((utility.showDate_Report(date)).toUpperCase());
				}
				objReportsDto.setHardware_flag(rs.getString("HARDWARE_FLAG"));
				objReportsDto.setChargeTypeID(rs.getInt("CHARGETYPEID"));
				objReportsDto.setCharge_status(rs.getString("CHARGES_STATUS"));
				objReportsDto.setSrno(rs.getString("SR_NO"));
				objReportsDto.setDispatchAddress1(rs.getString("DISP_ADDRESS1"));
				objReportsDto.setTokenNO(rs.getString("CSTATE_START_DETAILS_FX_TOKEN_NO"));
				objReportsDto.setCharge_hdr_id(rs.getInt("CHARGE_HDR_ID"));
				objReportsDto.setLineItemAmount(rs.getString("LINEITEMAMOUNT"));
				objReportsDto.setInstallation_addressaa1(rs.getString("INSTALLEMENT"));
				objReportsDto.setStartDateDays(rs.getInt("START_DATE_DAYS"));
				objReportsDto.setStartDateMonth(rs.getInt("START_DATE_MONTH"));
				objReportsDto.setEndDateDays(rs.getInt("END_DATE_DAYS"));
				objReportsDto.setEndDateMonth(rs.getInt("END_DATE_MONTH"));
				objReportsDto.setAnnualRate(rs.getString("ANNUAL_RATE"));
				objReportsDto.setPk_charge_id(rs.getString("CHARGEINFOID"));
				objReportsDto.setBusiness_serial_no(rs.getString("Business_No"));
					/*20151224-R1-021980 - Changes in Disconnection Report || ROM 
					 *  CHanged by :satish Start*/
					objReportsDto.setDesiredDueDate(rs.getString("DESIRED_DUE_DATE"));
					
					objReportsDto.setDerivedDisconnectionDate(rs.getString("DERIVEDDISCONNECTIONDATE"));
					
					objReportsDto.setIsTriggerRequired(rs.getString("ISTRIGGERREQUIRED(Y/N)"));
					objReportsDto.setLineTriggered(rs.getString("LINETRIGGERED(Y/N)"));
					objReportsDto.setTriggerProcess(rs.getString("TRIGGERPROCESS"));
					objReportsDto.setTriggerDoneBy(rs.getString("BILLINGTRIGGERDONEBY"));
					objReportsDto.setAutomaticTriggerError(rs.getString("AUTOMATICTRIGGERERROR"));
					
					
					/*20151224-R1-021980 - Changes in Disconnection Report ||ends*/
			}
		
			
			if (pagingSorting.isPagingToBeDone()) {
				recordCount = rs.getInt("FULL_REC_COUNT");
			}
			listSearchDetails.add(objReportsDto);
		}
		pagingSorting.setRecordCount(recordCount);
	}
	catch(Exception ex )
	{
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		//ex.printStackTrace();	
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(proc);
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return listSearchDetails;	
}

public ArrayList<MigratedApprovedNewOrderDetailReportDTO> viewMigAppNewOrderDetails(MigratedApprovedNewOrderDetailReportDTO objDto) 
{
	//	Nagarjuna
	String methodName="viewMigAppNewOrderDetails", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection =null;
	CallableStatement proc =null;
	ResultSet rs = null;
	ArrayList<MigratedApprovedNewOrderDetailReportDTO> listSearchDetails = new ArrayList<MigratedApprovedNewOrderDetailReportDTO>();
	MigratedApprovedNewOrderDetailReportDTO objReportsDto = null;
	int recordCount =0;
	Utility utility=new Utility();
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	Date tempDate=null;
	Timestamp ts=null;
	try
	{
		connection=DbConnection.getReportsConnectionObject();
		proc= connection.prepareCall(sqlGetMigAppNewOrderDetails);
		
		if (objDto.getOrdermonth() != null
				&& !"".equals(objDto.getOrdermonth())) {
			proc.setString(1, objDto.getOrdermonth().trim());
		} else {
			proc.setNull(1, java.sql.Types.VARCHAR);
		}
		
		if (objDto.getServiceName() != null
				&& !"".equals(objDto.getServiceName())) {
			proc.setString(2, objDto.getServiceName().trim());
		} else {
			proc.setNull(2, java.sql.Types.VARCHAR);
		}
		
		PagingSorting pagingSorting = objDto.getPagingSorting();
		pagingSorting.sync();// To calculate start index and Enc Index
		proc.setString(3, pagingSorting.getSortByColumn());// columnName
		proc.setString(4, PagingSorting.DB_Asc_Desc(pagingSorting
				.getSortByOrder()));// sort order
		proc.setInt(5, pagingSorting.getStartRecordId());// start index
		 proc.setInt(6, pagingSorting.getEndRecordId());// end index
		proc.setInt(7, (pagingSorting.isPagingToBeDone() ? 1 : 0));
		proc.setInt(8, objDto.getIsUsage());
		if (objDto.getOrderyear() != null
				&& !"".equals(objDto.getOrderyear())) {
			proc.setString(9, objDto.getOrderyear().trim());
		} else {
			proc.setNull(9, java.sql.Types.VARCHAR);
		}
		rs = proc.executeQuery();
		while(rs.next())
		{
			objReportsDto =  new MigratedApprovedNewOrderDetailReportDTO();
			//[270513]Start : Added by Ashutosh for Billing Address
			setBlank();
			replaceSeperator("BILLING_LOCATION",rs.getString("BILLING_ADDRESS"));
			replaceSeperator("PRIMARYLOCATION",rs.getString("PRIMARYLOCATION"));
			replaceSeperator("SECONDARYLOCATION",rs.getString("SECONDARYLOCATION"));		
			objReportsDto.setOrderNumber(rs.getInt("ORDERNO"));
			objReportsDto.setOrderType(rs.getString("ORDERTYPE"));
			objReportsDto.setCrmAccountNoString(rs.getString("CRMACCOUNTNO"));
			objReportsDto.setPartyName(rs.getString("PARTYNAME"));
			objReportsDto.setParty_no(rs.getInt("Party_NO"));
			objReportsDto.setVerticalDetails(rs.getString("VERTICAL_DETAILS"));
			objReportsDto.setRegionName(rs.getString("REGION"));   
			objReportsDto.setM6OrderNo(rs.getInt("M6ORDERNO"));
			objReportsDto.setServiceName(rs.getString("SERVICENAME"));
			objReportsDto.setCustomer_logicalSINumber(rs.getInt("CUSTOMER_LOGICAL_SI_NO"));
			objReportsDto.setServiceDetDescription(rs.getString("SERVICEDETDESCRIPTION"));//LineName
			//objReportsDto.setServiceOrderType(rs.getString("SERVICETYPE"));
			objReportsDto.setLogicalCircuitId(rs.getString("CKTID"));
			objReportsDto.setFromLocation(rs.getString("FROM_ADDRESS"));
			objReportsDto.setToLocation(rs.getString("TO_ADDRESS"));
			objReportsDto.setEntity(rs.getString("ENTITYNAME"));//Legal Entity
			objReportsDto.setLcompanyname(rs.getString("LCOMPANYNAME"));
			objReportsDto.setCurrencyName(rs.getString("CURRENCYNAME"));
			objReportsDto.setCreditPeriodName(rs.getString("CREDITPERIOD"));
			objReportsDto.setBillingTypeName(rs.getString("BILLING_TYPENAME"));//Bill Type
			objReportsDto.setBillingFormatName(rs.getString("BILLING_FORMATNAME"));
			objReportsDto.setBillingLevelName(rs.getString("BILLING_LEVELNAME"));
			objReportsDto.setBillingLevelNo(rs.getLong("BILLING_LEVEL_NO"));
			objReportsDto.setTaxation(rs.getString("TAXATIONVALUE"));			
			objReportsDto.setBillingMode(rs.getString("BILLINGMODE"));
			objReportsDto.setPonum(rs.getLong("PONUMBER"));			
			objReportsDto.setProductName(rs.getString("PRODUCTNAME"));
			objReportsDto.setSubProductName(rs.getString("SUBPRODUCTNAME"));
			tempDate=rs.getDate("PODATE");
			if(tempDate!=null)
			{
				objReportsDto.setPoDate((Utility.showDate_Report(tempDate)).toUpperCase());				
			}
			objReportsDto.setContractPeriod(rs.getInt("CONTRACTPERIOD"));//Period In Month
			objReportsDto.setTotalPoAmt(rs.getString("CUST_TOT_PO_AMT"));
			tempDate=rs.getDate("PORECEIVEDATE");
			if(tempDate!=null)
			{
				objReportsDto.setPoRecieveDate((Utility.showDate_Report(tempDate)).toUpperCase());
				
			}
			objReportsDto.setCustPoDetailNo(rs.getString("CUSTPONUMBER"));   
			tempDate=rs.getDate("CUSTPODATE"); 
			if(tempDate!=null)
			{
				objReportsDto.setCustPoDate((Utility.showDate_Report(tempDate)).toUpperCase());
			}
			objReportsDto.setLOC_No(rs.getString("LOCNO"));
			objReportsDto.setLocDate(rs.getString("LOCDATE"));
			if(!(rs.getString("LOCDATE")==null || rs.getString("LOCDATE")==""))
			{
				
				Date date=df.parse(objReportsDto.getLocDate());
				objReportsDto.setLocDate((Utility.showDate_Report(date)).toUpperCase());
				
			}
			objReportsDto.setPmApproveDate(rs.getString("Pm_Prov_Date"));
			if (rs.getString("Pm_Prov_Date") != null && !"".equals(rs.getString("Pm_Prov_Date")))
			{
			  String s1=rs.getString("Pm_Prov_Date");
			  String s3=s1.substring(0,7);
			  String s4=s1.substring(9,11);
			  String s5=s3.concat(s4);
			  objReportsDto.setPmApproveDate(s5);
			}
			objReportsDto.setBilling_Trigger_Flag(rs.getString("Billing_Trigger_Flag"));			
			objReportsDto.setBilling_bandwidth(rs.getString("BILLING_BANDWIDTH"));
			objReportsDto.setBillUom(rs.getString("BILLING_BANDWIDTH_UOM"));
			objReportsDto.setKmsDistance(rs.getString("DISTANCE"));
			objReportsDto.setBandwaidth(rs.getString("BANDWIDTH"));					
			objReportsDto.setOrderLineNumber(rs.getInt("Order_Line_Id"));//Lineitemnumber	
			objReportsDto.setOrdermonth(rs.getString("ORDERMONTH"));					
			//objReportsDto.setOpms_Account_Id(rs.getString("OPMS_ACCOUNT_ID"));
			objReportsDto.setPrimaryLocation(VAR_PRIMARYLOCATION);
			objReportsDto.setSecondaryLocation(VAR_SECONDARYLOCATION);
			objReportsDto.setFx_external_acc_id(rs.getString("Child_Account_Number"));
			objReportsDto.setChildAccountFXSataus(rs.getString("Child_Account_FX_Sataus"));
			if(objDto.getIsUsage()==1){
				objReportsDto.setBillingTriggerDate(rs.getString("BILLINGTRIGGERDATE"));
				if(!(rs.getString("BILLINGTRIGGERDATE")==null || rs.getString("BILLINGTRIGGERDATE")==""))
				{
					
					Date date=df.parse(objReportsDto.getBillingTriggerDate());
					objReportsDto.setBillingTriggerDate((Utility.showDate_Report(date)).toUpperCase());					
					
				}
				ts=rs.getTimestamp("BILLING_TRIGGER_CREATEDATE");
				if(ts!=null)
				{
					tempDate=new Date(ts.getTime());
					objReportsDto.setBillingtrigger_createdate((Utility.showDate_Report(tempDate)).toUpperCase());
					
				}
				objReportsDto.setComponentInfoID(rs.getInt("COMPONENTINFOID"));
				objReportsDto.setComponentID(rs.getInt("COMPONENT_ID"));
				objReportsDto.setComponentName(rs.getString("COMPONENT_NAME"));
				objReportsDto.setPackageID(rs.getInt("PACKAGE_ID"));
				objReportsDto.setPackageName(rs.getString("PACKAGE_NAME"));				
				objReportsDto.setCreditPeriodName(rs.getString("CREDIT_PERIODNAME"));
				objReportsDto.setServiceOrderType(rs.getString("SERVICE_ORDER_TYPE_DESC"));
				
				ComponentsDto dto = new ComponentsDto();
				dto.setComponentStatus(rs.getString("COMPONENT_STATUS"));
				dto.setStartLogic(rs.getString("COMPONENT_START_LOGIC"));
				dto.setStartDate(rs.getString("COMPONENT_START_DATE"));
				if (rs.getString("COMPONENT_START_DATE") != null && !"".equals(rs.getString("COMPONENT_START_DATE")))
				{
					
					Date date=df.parse(dto.getStartDate());
					dto.setStartDate((Utility.showDate_Report(date)).toUpperCase());
					
				}
				dto.setEndLogic(rs.getString("COMPONENT_END_LOGIC"));
				dto.setEnd_date(rs.getString("COMPONENT_END_DATE"));
				if (rs.getString("COMPONENT_END_DATE") != null && !"".equals(rs.getString("COMPONENT_END_DATE")))
				{
					
					Date date=df.parse(dto.getEnd_date());
					dto.setEnd_date((Utility.showDate_Report(date)).toUpperCase());
					
				}
				dto.setFxTokenNo(rs.getString("START_COMPONENT_TOKEN_NO"));
				dto.setFxStartStatus(rs.getString("FX_START_STATUS"));
				dto.setEndTokenNo(rs.getString("LOCAL_END_COMPONENT_TOKEN_NO"));
				dto.setEndFxStatus(rs.getString("FX_END_STATUS"));
				//dto.setComponentFXStatus(rs.getString("FX_STATUS"));
				objReportsDto.setStartDateDays(rs.getInt("COMP_START_DAYS"));
				objReportsDto.setStartDateMonth(rs.getInt("COMP_START_MONTHS"));
				objReportsDto.setEndDateDays(rs.getInt("COMP_END_DAYS"));
				objReportsDto.setEndDateMonth(rs.getInt("COMP_END_MONTHS"));
				objReportsDto.setSourcePartyID(rs.getLong("PARTY_ID"));
				objReportsDto.setAccountID(rs.getInt("CUSTACCOUNTID"));
				objReportsDto.setFx_internal_acc_id(rs.getLong("INTERNAL_ID"));
				dto.setComponentInstanceID(rs.getString("COMPONENT_INST_ID"));
				dto.setComponentType(rs.getString("COMPONENT_TYPE"));
				dto.setComponentAmount(rs.getDouble("COMP_AMOUNT"));
												
				objReportsDto.setComponentDto(dto);
				
			}else{
				objReportsDto.setBillingTriggerDate(rs.getString("BILLINGTRIGGERDATE"));
				if(!(rs.getString("BILLINGTRIGGERDATE")==null || rs.getString("BILLINGTRIGGERDATE")==""))
				{
					
					Date date=df.parse(objReportsDto.getBillingTriggerDate());
					objReportsDto.setBillingTriggerDate((Utility.showDate_Report(date)).toUpperCase());					
					
				}
				objReportsDto.setBillingtrigger_createdate(rs.getString("BILLING_TRIGGER_CREATEDATE"));
				if(!(rs.getString("BILLING_TRIGGER_CREATEDATE")==null || rs.getString("BILLING_TRIGGER_CREATEDATE")==""))
				{
					
					//Date date=df.parse(objReportsDto.getBillingtrigger_createdate());
					objReportsDto.setBillingtrigger_createdate((Utility.showDate_Report(rs.getDate("BILLING_TRIGGER_CREATEDATE"))).toUpperCase());
					
				}
				objReportsDto.setRatio(rs.getString("RATIO"));						
				objReportsDto.setBlSource(rs.getString("BL_SOURCE"));
				objReportsDto.setFx_St_Chg_Status(rs.getString("CSTATE_FX_CHARGE_START_STATUS"));//Fx_St_Chg_Status
				objReportsDto.setFxStatus(rs.getString("CSTATE_START_DETAILS_FX_STATUS"));//FX_STATUS
				objReportsDto.setChargeName(rs.getString("CHARGE_NAME"));
				objReportsDto.setChargeTypeName(rs.getString("CHARGE_TYPE"));				
				objReportsDto.setChargeAmount_String(rs.getString("INV_AMT"));
				objReportsDto.setLineItemAmount(rs.getString("LINEITEMAMOUNT"));//amt
				objReportsDto.setStartDate(rs.getString("START_DATE"));
				if(!(rs.getString("START_DATE")==null || rs.getString("START_DATE")==""))
				{
					
					Date date=df.parse(objReportsDto.getStartDate());
					objReportsDto.setStartDate((Utility.showDate_Report(date)).toUpperCase());
					
				}
				objReportsDto.setChargeAnnotation(rs.getString("ANNOTATION"));
				objReportsDto.setTokenNO(rs.getString("CSTATE_START_DETAILS_FX_TOKEN_NO"));//Token_No
				objReportsDto.setHardwareFlag(rs.getString("HARDWARE_FLAG"));
				objReportsDto.setHardwaretypeName(rs.getString("HARDWARETYPENAME"));
				objReportsDto.setStorename(rs.getString("STORENAME"));
				objReportsDto.setBillPeriod(rs.getString("BILL_PERIOD"));
				objReportsDto.setBusiness_serial_no(rs.getString("BUSINESS_SERIAL_NO"));
				//objReportsDto.setSaleType(rs.getString("SALETYPENAME"));//Type Of Sale
			}
			
			if (pagingSorting.isPagingToBeDone()) {
				recordCount = rs.getInt("FULL_REC_COUNT");
			}
			listSearchDetails.add(objReportsDto);
		}
		pagingSorting.setRecordCount(recordCount);
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();	
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(proc);
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return listSearchDetails;
}

/**
 * method to fetch data for LEPM Owner report.
 * @param objDto
 * @return
 */
public ArrayList<ReportsDto> viewLEPMOwnerReport(ReportsDto objDto) 
{
	//	Nagarjuna
	String methodName="viewLEPMOwnerReport", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection = null;
	CallableStatement proc = null;
	ResultSet rs = null;
	int recordCount = 0;
	Utility utility=new Utility();
	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	ArrayList<ReportsDto> listLEPMOwnerReport = new ArrayList<ReportsDto>();
	ReportsDto objReportsDto = null;
	try
	{
		connection=DbConnection.getReportsConnectionObject();
		proc= connection.prepareCall(sqlGetLEPMOwnerReport);
		
		if (objDto.getOrderNo() != null 
				&& !"".equals(objDto.getOrderNo())) {
			proc.setInt(1, new Integer(objDto.getOrderNo()));
		} else {
			proc.setNull(1, java.sql.Types.BIGINT);
		}
		if (objDto.getCopcApproveDate() != null 
				&& !"".equals(objDto.getCopcApproveDate())) {
			proc.setString(2, objDto.getCopcApproveDate());
		} else {
			proc.setNull(2, java.sql.Types.VARCHAR);
		}
		PagingSorting pagingSorting = objDto.getPagingSorting();
		pagingSorting.sync();// To calculate start index and Enc Index

		proc.setString(3, pagingSorting.getSortByColumn());// columnName
		proc.setString(4, PagingSorting.DB_Asc_Desc(pagingSorting
				.getSortByOrder()));// sort order
		proc.setInt(5, pagingSorting.getStartRecordId());// start index
		 proc.setInt(6, pagingSorting.getEndRecordId());// end index
		proc.setInt(7, (pagingSorting.isPagingToBeDone() ? 1 : 0));
		// index
		rs = proc.executeQuery();
		while(rs.next())
		{
			objReportsDto =  new ReportsDto();
			objReportsDto.setPm_pro_date(rs.getString("ACTUAL_START_DATE"));
			if(!(rs.getString("ACTUAL_START_DATE")==null || rs.getString("ACTUAL_START_DATE")==""))
			{
				
				objReportsDto.setPm_pro_date((utility.showDate_Report(new Date(rs.getTimestamp("ACTUAL_START_DATE").getTime()))).toUpperCase());
							
			}
			objReportsDto.setPmapprovaldate(rs.getString("PLANNED_END_DATE")); 
			if(!(rs.getString("PLANNED_END_DATE")==null || rs.getString("PLANNED_END_DATE")==""))
			{
				
				objReportsDto.setPmapprovaldate((utility.showDate_Report(new Date(rs.getTimestamp("PLANNED_END_DATE").getTime()))).toUpperCase());
				
			}
			objReportsDto.setPmApproveDate(rs.getString("ACTUAL_END_DATE"));
			if(!(rs.getString("ACTUAL_END_DATE")==null || rs.getString("ACTUAL_END_DATE")==""))
			{
				
				objReportsDto.setPmApproveDate((utility.showDate_Report(new Date(rs.getTimestamp("ACTUAL_END_DATE").getTime()))).toUpperCase());
				
			}
			objReportsDto.setCopcApproveDate(rs.getString("ORDER_APPROVAL_DATE"));
			if(!(rs.getString("ORDER_APPROVAL_DATE")==null || rs.getString("ORDER_APPROVAL_DATE")==""))
			{
				
				
				objReportsDto.setCopcApproveDate((utility.showDate_Report(new Date(rs.getTimestamp("ORDER_APPROVAL_DATE").getTime()))).toUpperCase());
			}
			 
			objReportsDto.setOrderNo(rs.getString("CRM_ORDER_ID"));
			objReportsDto.setPmName(rs.getString("OWNER_NAME"));
			objReportsDto.setContactCell(rs.getString("OWNER_PHONE"));
			objReportsDto.setEmailId(rs.getString("OWNER_EMAILID"));
			objReportsDto.setUserName(rs.getString("USER_NAME"));
			objReportsDto.setTaskNumber(rs.getInt("TASK_NUMBER"));
			objReportsDto.setTaskName(rs.getString("TASK_NAME"));
			
			
			
			
			if (pagingSorting.isPagingToBeDone()) {
				recordCount = rs.getInt("FULL_REC_COUNT");
			}
			listLEPMOwnerReport.add(objReportsDto);
		}
		pagingSorting.setRecordCount(recordCount);
	}
	catch(Exception ex )
	{
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		//ex.printStackTrace();	
	}
	finally
	{
		try 
		{
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return listLEPMOwnerReport;	
}
//002 START
/**
 * Create a Report to generate LEPM Order Cancel Report
 
 * @param obj   a DTO which consist all the search parameters
 * @return      a ArrayList of dto which consist all the data of reports 
 * @exception   Sql Exception
 *            
 */	
public ArrayList<ReportsDto> viewLEPMOrderCancelReport(ReportsDto objDto)throws Exception 
{
	//	Nagarjuna
	String methodName="viewLEPMOrderCancelReport", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	ArrayList<ReportsDto> objUserList = new ArrayList<ReportsDto>();
	Connection conn = null;
	CallableStatement proc = null;
	ResultSet rs = null;
	int recordCount = 0;
	int countFlag = 0;
	ReportsDto objReportsDto = null;
	ArrayList<ReportsDto> listSearchDetails = new ArrayList<ReportsDto>();
	
		try {
		
		conn = DbConnection.getReportsConnectionObject();
	
		proc = conn.prepareCall(sqlLEPMOrderCancelReport);
		if (objDto.getCanceldate() != null
				&& !"".equals(objDto.getCanceldate())) {
			proc.setString(1, objDto.getCanceldate().trim());
		} else {
			proc.setNull(1, java.sql.Types.VARCHAR);
		}
		
		PagingSorting pagingSorting = objDto.getPagingSorting();
		pagingSorting.sync();// To calculate start index and Enc Index
		
		proc.setString(2, pagingSorting.getSortByColumn());// columnName
		proc.setString(3, PagingSorting.DB_Asc_Desc(pagingSorting
				.getSortByOrder()));// sort order
		proc.setInt(4, pagingSorting.getStartRecordId());// start index
		proc.setInt(5, pagingSorting.getEndRecordId());// end index
		proc.setInt(6, (pagingSorting.isPagingToBeDone() ? 1 : 0));// end
		rs = proc.executeQuery();
		

		while (rs.next() != false) 
		{
			objDto = new ReportsDto();
			objDto.setPartyName(rs.getString("PARTYNAME"));
			objDto.setOrderNo(rs.getString("ORDERNO"));
			objDto.setCopcApproveDate(rs.getString("COPC_APPROVAL_DATE"));
		   if (rs.getString("COPC_APPROVAL_DATE") != null && !"".equals(rs.getString("COPC_APPROVAL_DATE")))
			{
				objDto.setCopcApproveDate((Utility.showDate_Report((rs.getTimestamp("COPC_APPROVAL_DATE")))).toUpperCase());
			}
			objDto.setLogicalCircuitId(rs.getString("LOGICALCKTID"));
			objDto.setServiceId(rs.getInt("SERVICENO"));
			objDto.setQuoteNo(rs.getString("QUOTENO"));
			objDto.setProductName(rs.getString("PRODUCTNAME"));
			objDto.setSubProductName(rs.getString("SUBPRODUCTNAME"));
			objDto.setPrimarylocation(rs.getString("FROM_SITE"));
			objDto.setSeclocation(rs.getString("TO_SITE"));
			objDto.setOrdersubtype(rs.getString("ORDERSUBTYPE"));
			objDto.setBandwaidth(rs.getString("BANDWIDTH"));
			objDto.setChargeAmount(rs.getDouble("CHARGEAMOUNT"));
			objDto.setPrjmngname(rs.getString("PMNAME"));
			objDto.setPrjmgremail(rs.getString("PMEMAIL"));
			objDto.setActmngname(rs.getString("ACTMNAME"));
			objDto.setZoneName(rs.getString("ZONENNAME"));
			objDto.setRegionName(rs.getString("REGIONNAME"));
			objDto.setVerticalDetails(rs.getString("VERTICAL_DETAILS"));
			objDto.setAct_category(rs.getString("ACCOUNTCATEGORY_DETAILS"));
			objDto.setCustPoDate(rs.getString("CUSTPODATE"));
			if (rs.getString("CUSTPODATE") != null && !"".equals(rs.getString("CUSTPODATE")))
			{
				
				objDto.setCustPoDate((Utility.showDate_Report((rs.getTimestamp("CUSTPODATE")))).toUpperCase());
				
			}
			objDto.setCustPoDetailNo(rs.getString("CUSTPONUMBER"));
			objDto.setOrderDate(rs.getString("ORDERDATE"));
			if (rs.getString("ORDERDATE") != null && !"".equals(rs.getString("ORDERDATE")))
			{
				
				objDto.setOrderDate((Utility.showDate_Report((rs.getTimestamp("ORDERDATE")))).toUpperCase());
				
			}
			objDto.setPmApproveDate(rs.getString("PM_APPROVAL_DATE"));
			if (rs.getString("PM_APPROVAL_DATE") != null && !"".equals(rs.getString("PM_APPROVAL_DATE")))
			{
				objDto.setPmApproveDate((Utility.showDate_Report((rs.getTimestamp("PM_APPROVAL_DATE")))).toUpperCase());
			}
			objDto.setAmApproveDate(rs.getString("AM_APPROVAL_DATE"));
			if (rs.getString("AM_APPROVAL_DATE") != null && !"".equals(rs.getString("AM_APPROVAL_DATE")))
			{
				objDto.setAmApproveDate((Utility.showDate_Report((rs.getTimestamp("AM_APPROVAL_DATE")))).toUpperCase());
			}
			objDto.setNio_approve_date(rs.getString("NIO_APPROVAL_DATE"));
			if (rs.getString("NIO_APPROVAL_DATE") != null && !"".equals(rs.getString("NIO_APPROVAL_DATE")))
			{
				objDto.setNio_approve_date((Utility.showDate_Report((rs.getTimestamp("NIO_APPROVAL_DATE")))).toUpperCase());
			}
			objDto.setDemo_infrastartdate(rs.getString("DEMP_INFRA_START_DATE"));
			objDto.setDemo_infra_enddate(rs.getString("DEMO_INFRA_ENDDATE"));
			objDto.setRfs_date(rs.getString("RFS_DATE"));
			if (rs.getString("RFS_DATE") != null && !"".equals(rs.getString("RFS_DATE")))
			{
				
				objDto.setRfs_date((Utility.showDate_Report((rs.getTimestamp("RFS_DATE")))).toUpperCase());
				
			}
			objDto.setOrdercategory(rs.getString("ORDERCATEGORY"));
			objDto.setOrderStatus(rs.getString("STATUS"));
			objDto.setLine_desc(rs.getString("LINE_ITEM_DESC"));
			objDto.setLinename(rs.getString("LINENAME"));
			objDto.setSub_linename(rs.getString("ORDER_SUBLINENAME"));
			objDto.setChargeName(rs.getString("CHARGE_NAME"));
			objDto.setChargeinfoID(rs.getString("CHARGEINFOID"));
			objDto.setServiceProductID(rs.getInt("LINENO"));
			objDto.setServiceName(rs.getString("SERVICENAME"));
			objDto.setAccountID(rs.getInt("ACCOUNTID"));
			objDto.setLicCompanyName(rs.getString("LCOMPANYNAME"));
			objDto.setEntity(rs.getString("COMPANYNAME"));
			objDto.setContractPeriod(rs.getInt("CONTRACTPERIOD"));
			objDto.setFrequencyName(rs.getString("PAYMENTTERM"));
			objDto.setChargeTypeName(rs.getString("CYCLICNONCYCLIC"));
			if("NULL".equals(rs.getString("SERVICETYPE")))
			{
				objDto.setServiceType("");
			}
			else
			{	
			objDto.setServiceType(rs.getString("SERVICETYPE"));
			}
			objDto.setUom(rs.getString("UOM"));
			objDto.setBilling_bandwidth(rs.getString("BILLING_BANDWIDTH"));
			objDto.setBilling_uom(rs.getString("BILLING_BANDWIDTH_UOM"));
			objDto.setFrom_city(rs.getString("FROM_CITY"));
			objDto.setTo_city(rs.getString("TO_CITY"));
			objDto.setOldordertotal(rs.getString("OLD_ORDER_TOTAL"));
			objDto.setOldlineamt(rs.getString("OLD_LINE_AMT"));
			objDto.setOld_contract_period(rs.getString("OLD_CONTRACTPERIOD"));
			objDto.setRatio(rs.getString("RATIO"));
			objDto.setTaxation(rs.getString("TAXATIONVALUE"));
			objDto.setFactory_ckt_id(rs.getString("FACTORY_CKT_ID"));
			objDto.setDistance(rs.getString("DISTANCE"));
			objDto.setAccountManager(rs.getString("ACTMEMAIL"));
			objDto.setCurrencyCode(rs.getString("CURNAME"));
			objDto.setOrderTotal(rs.getDouble("ORDERTOTAL"));
			objDto.setPoAmount(rs.getString("POAMOUNT"));
			objDto.setBisource(rs.getString("BISOURCE"));
			objDto.setOrderType(rs.getString("ORDERTYPE"));
			objDto.setDispatchAddress1(rs.getString("DISPATCHADDRESS"));
			objDto.setParent_name(rs.getString("PARENTNAME"));
			objDto.setServiceStage(rs.getString("STAGE"));
			objDto.setCanceldate(rs.getString("CANCEL_DATE"));
			if (rs.getString("CANCEL_DATE") != null && !"".equals(rs.getString("CANCEL_DATE")))
			{
				
				objDto.setCanceldate((Utility.showDate_Report((rs.getTimestamp("CANCEL_DATE")))).toUpperCase());
				
			}
			if (pagingSorting.isPagingToBeDone()) {
				 recordCount = rs.getInt("FULL_REC_COUNT");
			}
			listSearchDetails.add(objDto);
		}
			pagingSorting.setRecordCount(recordCount);
		
		}
		
	catch (Exception ex) {
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			//ex.printStackTrace();
			throw new Exception("SQL Exception : " + ex.getMessage(), ex);
			} finally {
			try {
				DbConnection.freeConnection(conn);
			
			} catch (Exception e) {
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
				//e.printStackTrace();
				throw new Exception("Exception : " + e.getMessage(), e);
			}
			}
	return listSearchDetails;
	
} 

//002 START

//003 START
/**
 * Create a Report to generate LEPM Order Detail Report
 
 * @param obj   a DTO which consist all the search parameters
 * @return      a ArrayList of dto which consist all the data of reports 
 * @exception   Sql Exception
 *            
 */	
public ArrayList<ReportsDto> viewLEPMOrderDetailReport(ReportsDto objDto)throws Exception 
{
	//	Nagarjuna
	String methodName="viewLEPMOrderDetailReport", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	ArrayList<ReportsDto> objUserList = new ArrayList<ReportsDto>();
	Connection conn = null;
	CallableStatement proc = null;
	ResultSet rs = null;
	int recordCount = 0;
	int countFlag = 0;
	ReportsDto objReportsDto = null;
	ArrayList<ReportsDto> listSearchDetails = new ArrayList<ReportsDto>();
	
		try {
		
		conn = DbConnection.getReportsConnectionObject();
	
		proc = conn.prepareCall(sqlLEPMOrderDetailReport);
		if (objDto.getOrderType() != null
				&& !"".equals(objDto.getOrderType())) {
			proc.setString(1, objDto.getOrderType().trim());
		} else {
			proc.setNull(1, java.sql.Types.VARCHAR);
		}
		if (objDto.getCopcApproveDate() != null
				&& !"".equals(objDto.getCopcApproveDate())) {
			proc.setString(2, objDto.getCopcApproveDate().trim());
		} else {
			proc.setNull(2, java.sql.Types.VARCHAR);
		}
		
		if (objDto.getVerticalDetails() != null && !"".equals(objDto.getVerticalDetails())) {
			proc.setString(3, objDto.getVerticalDetails().trim());
		} else {
			proc.setNull(3, java.sql.Types.VARCHAR);
		}
		
		if (objDto.getFromOrderNo() != 0 
				&& !"".equals(objDto.getFromOrderNo())) {
			proc.setInt(4, objDto.getFromOrderNo());
		} else {
			proc.setNull(4,java.sql.Types.BIGINT);
		}
		
		if (objDto.getToOrderNo() != 0 
				&& !"".equals(objDto.getToOrderNo())) {
			proc.setInt(5, objDto.getToOrderNo());
		} else {
			proc.setNull(5, java.sql.Types.BIGINT);
		}
		
		PagingSorting pagingSorting = objDto.getPagingSorting();
		pagingSorting.sync();// To calculate start index and Enc Index
		
		proc.setString(6, pagingSorting.getSortByColumn());// columnName
		proc.setString(7, PagingSorting.DB_Asc_Desc(pagingSorting
				.getSortByOrder()));// sort order
		proc.setInt(8, pagingSorting.getStartRecordId());// start index
		proc.setInt(9, pagingSorting.getEndRecordId());// end index
		proc.setInt(10, (pagingSorting.isPagingToBeDone() ? 1 : 0));// end
		rs = proc.executeQuery();
		

		while (rs.next() != false) 
		{
			objDto = new ReportsDto();
			objDto.setPartyName(rs.getString("PARTYNAME"));
			objDto.setOrderNo(rs.getString("ORDERNO"));
			objDto.setCopcApproveDate(rs.getString("COPC_APPROVAL_DATE"));
		   if (rs.getString("COPC_APPROVAL_DATE") != null && !"".equals(rs.getString("COPC_APPROVAL_DATE")))
			{
				objDto.setCopcApproveDate((Utility.showDate_Report((rs.getTimestamp("COPC_APPROVAL_DATE")))).toUpperCase());
			}
			objDto.setLogicalCircuitId(rs.getString("LOGICALCKTID"));
			objDto.setServiceId(rs.getInt("SERVICENO"));
			objDto.setQuoteNo(rs.getString("QUOTENO"));
			objDto.setProductName(rs.getString("PRODUCTNAME"));
			objDto.setSubProductName(rs.getString("SUBPRODUCTNAME"));
			objDto.setPrimarylocation(rs.getString("FROM_SITE"));
			objDto.setSeclocation(rs.getString("TO_SITE"));
			objDto.setOrdersubtype(rs.getString("ORDERSUBTYPE"));
			objDto.setBandwaidth(rs.getString("BANDWIDTH"));
			objDto.setChargeAmount(rs.getDouble("CHARGEAMOUNT"));
			objDto.setPrjmngname(rs.getString("PMNAME"));
			objDto.setPrjmgremail(rs.getString("PMEMAIL"));
			objDto.setActmngname(rs.getString("ACTMNAME"));
			objDto.setZoneName(rs.getString("ZONENNAME"));
			objDto.setRegionName(rs.getString("REGIONNAME"));
			objDto.setVerticalDetails(rs.getString("VERTICAL_DETAILS"));
			objDto.setAct_category(rs.getString("ACCOUNTCATEGORY_DETAILS"));
			objDto.setCustPoDate(rs.getString("CUSTPODATE"));
			if (rs.getString("CUSTPODATE") != null && !"".equals(rs.getString("CUSTPODATE")))
			{
				
				objDto.setCustPoDate((Utility.showDate_Report((rs.getTimestamp("CUSTPODATE")))).toUpperCase());
				
			}
			objDto.setCustPoDetailNo(rs.getString("CUSTPONUMBER"));
			objDto.setOrderDate(rs.getString("ORDERDATE"));
			if (rs.getString("ORDERDATE") != null && !"".equals(rs.getString("ORDERDATE")))
			{
				
				objDto.setOrderDate((Utility.showDate_Report((rs.getTimestamp("ORDERDATE")))).toUpperCase());
				
			}
			objDto.setPmApproveDate(rs.getString("PM_APPROVAL_DATE"));
			if (rs.getString("PM_APPROVAL_DATE") != null && !"".equals(rs.getString("PM_APPROVAL_DATE")))
			{
				objDto.setPmApproveDate((Utility.showDate_Report((rs.getTimestamp("PM_APPROVAL_DATE")))).toUpperCase());
			}
			objDto.setAmApproveDate(rs.getString("AM_APPROVAL_DATE"));
			if (rs.getString("AM_APPROVAL_DATE") != null && !"".equals(rs.getString("AM_APPROVAL_DATE")))
			{
				objDto.setAmApproveDate((Utility.showDate_Report((rs.getTimestamp("AM_APPROVAL_DATE")))).toUpperCase());
			}
			objDto.setNio_approve_date(rs.getString("NIO_APPROVAL_DATE"));
			if (rs.getString("NIO_APPROVAL_DATE") != null && !"".equals(rs.getString("NIO_APPROVAL_DATE")))
			{
				objDto.setNio_approve_date((Utility.showDate_Report((rs.getTimestamp("NIO_APPROVAL_DATE")))).toUpperCase());
			}
			objDto.setDemo_infrastartdate(rs.getString("DEMP_INFRA_START_DATE"));
			objDto.setDemo_infra_enddate(rs.getString("DEMO_INFRA_ENDDATE"));
			objDto.setRfs_date(rs.getString("RFS_DATE"));
			if (rs.getString("RFS_DATE") != null && !"".equals(rs.getString("RFS_DATE")))
			{
				
				objDto.setRfs_date((Utility.showDate_Report((rs.getTimestamp("RFS_DATE")))).toUpperCase());
				
			}
			objDto.setOrdercategory(rs.getString("ORDERCATEGORY"));
			objDto.setOrderStatus(rs.getString("STATUS"));
			objDto.setLine_desc(rs.getString("LINE_ITEM_DESC"));
			objDto.setLinename(rs.getString("LINENAME"));
			objDto.setSub_linename(rs.getString("ORDER_SUBLINENAME"));
			objDto.setChargeName(rs.getString("CHARGE_NAME"));
			objDto.setChargeinfoID(rs.getString("CHARGEINFOID"));
			objDto.setServiceProductID(rs.getInt("LINENO"));
			objDto.setServiceName(rs.getString("SERVICENAME"));
			objDto.setAccountID(rs.getInt("ACCOUNTID"));
			objDto.setLicCompanyName(rs.getString("LCOMPANYNAME"));
			objDto.setEntity(rs.getString("COMPANYNAME"));
			objDto.setContractPeriod(rs.getInt("CONTRACTPERIOD"));
			objDto.setFrequencyName(rs.getString("PAYMENTTERM"));
			objDto.setChargeTypeName(rs.getString("CYCLICNONCYCLIC"));
			if("NULL".equals(rs.getString("SERVICETYPE")))
			{
				objDto.setServiceType("");
			}
			else
			{	
			objDto.setServiceType(rs.getString("SERVICETYPE"));
			}
			objDto.setUom(rs.getString("UOM"));
			objDto.setBilling_bandwidth(rs.getString("BILLING_BANDWIDTH"));
			objDto.setBilling_uom(rs.getString("BILLING_BANDWIDTH_UOM"));
			objDto.setFrom_city(rs.getString("FROM_CITY"));
			objDto.setTo_city(rs.getString("TO_CITY"));
			objDto.setOldordertotal(rs.getString("OLD_ORDER_TOTAL"));
			objDto.setOldlineamt(rs.getString("OLD_LINE_AMT"));
			objDto.setOld_contract_period(rs.getString("OLD_CONTRACTPERIOD"));
			objDto.setRatio(rs.getString("RATIO"));
			objDto.setTaxation(rs.getString("TAXATIONVALUE"));
			objDto.setFactory_ckt_id(rs.getString("FACTORY_CKT_ID"));
			objDto.setDistance(rs.getString("DISTANCE"));
			objDto.setAccountManager(rs.getString("ACTMEMAIL"));
			objDto.setCurrencyCode(rs.getString("CURNAME"));
			objDto.setOrderTotal(rs.getDouble("ORDERTOTAL"));
			objDto.setPoAmount(rs.getString("POAMOUNT"));
			objDto.setBisource(rs.getString("BISOURCE"));
			objDto.setOrderType(rs.getString("ORDERTYPE"));
			objDto.setDispatchAddress1(rs.getString("DISPATCHADDRESS"));
			objDto.setParent_name(rs.getString("PARENTNAME"));
			
			if (pagingSorting.isPagingToBeDone()) {
				 recordCount = rs.getInt("FULL_REC_COUNT");
			}
			listSearchDetails.add(objDto);
		}
			pagingSorting.setRecordCount(recordCount);
		
		}
		
	catch (Exception ex) {
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			//ex.printStackTrace();
			throw new Exception("SQL Exception : " + ex.getMessage(), ex);
			} finally {
			try {
				DbConnection.freeConnection(conn);
			
			} catch (Exception e) {
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
				//e.printStackTrace();
				throw new Exception("Exception : " + e.getMessage(), e);
			}
			}
	return listSearchDetails;
	
} 
//003 END
//004 START
/**
 * Create a Report to generate Pending Billing Permanent Disconnection Report
 
 * @param obj   a DTO which consist all the search parameters
 * @return      a ArrayList of dto which consist all the data of reports 
 * @exception   Sql Exception
 *            
 */	

public ArrayList<ReportsDto> viewPendingBillingPDOrderList(ReportsDto objDto)throws Exception 
{
	//	Nagarjuna
	String methodName="viewPendingBillingPDOrderList", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
   ArrayList<ReportsDto> objOrderList = new ArrayList<ReportsDto>();
   Connection conn = null;
   ResultSet rs = null;
   CallableStatement getOrder = null;

	try 
	{
		conn = DbConnection.getReportsConnectionObject();
		getOrder = conn.prepareCall(SPGETSRORDERREPORT);
		String searchCRMOrder = objDto.getSearchCRMOrder();
		String searchfromDate=objDto.getSearchfromDate();
		String searchToDate = objDto.getSearchToDate();
		String searchSrno=objDto.getSearchSRNO();
		String searchLSI=objDto.getSearchLSI();
		if (searchCRMOrder == null || searchCRMOrder.trim().equals("")) {
			getOrder.setNull(1, java.sql.Types.BIGINT);
		} else {
			getOrder.setLong(1, Long.parseLong(searchCRMOrder));
		}
		if (searchfromDate == null || searchfromDate.trim().equals("")) {
			getOrder.setNull(2, java.sql.Types.VARCHAR);
		} else {
			getOrder.setString(2, searchfromDate);
		}
		
		if (searchToDate == null || searchToDate.trim().equals("")) {
			getOrder.setNull(3, java.sql.Types.VARCHAR);
		} else {
			getOrder.setString(3, searchToDate);
		}
		
		if (searchSrno == null || searchSrno.trim().equals("")) {
			getOrder.setNull(4, java.sql.Types.VARCHAR);
		} else {
			getOrder.setString(4, searchSrno);
		}
		
		if (searchLSI == null || searchLSI.trim().equals("")) {
			getOrder.setNull(5, java.sql.Types.VARCHAR);
		} else {
			getOrder.setString(5, searchLSI);
		}
		
		SimpleDateFormat sdf  = new SimpleDateFormat("dd-MM-yyyy");
		PagingSorting pagingSorting = objDto.getPagingSorting();
		pagingSorting.sync();// To calculate start index and Enc Index
		
		getOrder.setString(6, pagingSorting.getSortByColumn());// columnName
		getOrder.setString(7, PagingSorting.DB_Asc_Desc1(pagingSorting
				.getSortByOrder()));// sort order
		getOrder.setInt(8, pagingSorting.getStartRecordId());// start index
		getOrder.setInt(9, pagingSorting.getEndRecordId());// end index
		getOrder.setInt(10, (pagingSorting.isPagingToBeDone() ? 1 : 0));// end
		// index
		rs = getOrder.executeQuery();
		
		int countFlag = 0;
		int recordCount = 0;
		while (rs.next() != false) {				
		countFlag++;
			objDto = new ReportsDto();
			objDto.setOrderNo(rs.getString("ORDERNO"));
			objDto.setSrno(rs.getString("SRNO"));
			objDto.setSrDate(rs.getString("SR_CREATION_DATE"));
			if (rs.getString("SR_CREATION_DATE") != null && !"".equals(rs.getString("SR_CREATION_DATE")))
			{
				
				objDto.setSrDate((Utility.showDate_Report((rs.getTimestamp("SR_CREATION_DATE")))).toUpperCase());
				
			}
			objDto.setLogicalSINo(rs.getString("LSINO"));
			objDto.setOrderStatus(rs.getString("ORDER_STATUS"));
			objDto.setNeworder_remarks(rs.getString("REMARKS"));
			objDto.setProductName(rs.getString("SR_RAISED_BY"));
			objDto.setDisdate(rs.getString("DATE_DIS"));
			if (rs.getString("DATE_DIS") != null && !"".equals(rs.getString("DATE_DIS")))
			{
				
				objDto.setDisdate((Utility.showDate_Report((rs.getTimestamp("DATE_DIS")))).toUpperCase());
				
			}

		if (pagingSorting.isPagingToBeDone()) {
			recordCount = rs.getInt("FULL_REC_COUNT");
		}
	
		objOrderList.add(objDto);
	}
	pagingSorting.setRecordCount(recordCount);
} 
catch (Exception ex) {
	Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
	//ex.printStackTrace();
	throw new Exception("SQL Exception : " + ex.getMessage(), ex);
	} finally {
	try {
		rs.close();
		getOrder.close();
		DbConnection.freeConnection(conn);
	
	} catch (Exception e) {
		Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		//e.printStackTrace();
		throw new Exception("Exception : " + e.getMessage(), e);
	}
	}
return objOrderList;
}

public ArrayList<CustomerBaseReportsDTO> viewCustomerBaseReport(CustomerBaseReportsDTO objDto) 
{
	//	Nagarjuna
	String methodName="viewCustomerBaseReport", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	//end Nagarjuna
	Connection connection = null;
	CallableStatement proc = null;
	ResultSet rs = null;
	int recordCount = 0;
	ArrayList<CustomerBaseReportsDTO> listSearchDetails = new ArrayList<CustomerBaseReportsDTO>();
	CustomerBaseReportsDTO objReportsDto = null;
	Utility utility=new Utility();
	Date tempDate=null;
	try
	{
		connection=DbConnection.getReportsConnectionObject();
		proc= connection.prepareCall(sqlGetCustomerbaseReport);
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		
		if (objDto.getCrmAccountNoString() != null 
				&& !"".equals(objDto.getCrmAccountNoString())) {
			proc.setString(1, objDto.getCrmAccountNoString().trim());
		} else {
			proc.setNull(1, java.sql.Types.VARCHAR);
		}
		
		PagingSorting pagingSorting = objDto.getPagingSorting();
		pagingSorting.sync();// To calculate start index and Enc Index

		proc.setString(2, pagingSorting.getSortByColumn());// columnName
		proc.setString(3, PagingSorting.DB_Asc_Desc(pagingSorting.getSortByOrder()));// sort order
		proc.setInt(4, pagingSorting.getStartRecordId());// start index
		proc.setInt(5, pagingSorting.getEndRecordId());// end index
		proc.setInt(6, (pagingSorting.isPagingToBeDone() ? 1 : 0));
		if (objDto.getFromDate() != null
				&& !"".equals(objDto.getFromDate())) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date dateStr = formatter.parse(objDto.getFromDate());
			String formattedDate = formatter.format(dateStr);
			Date date1 = formatter.parse(formattedDate);
			formatter = new SimpleDateFormat("MM-dd-yyyy");
			formattedDate = formatter.format(date1);
			proc.setString(7,formattedDate);
		} else {
			proc.setNull(7, java.sql.Types.VARCHAR);
		}				
		if (objDto.getToDate() != null && !"".equals(objDto.getToDate())) {
			SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
			Date dateStr1 = formatter1.parse(objDto.getToDate());
			String formattedDate1 = formatter1.format(dateStr1);
			Date date2 = formatter1.parse(formattedDate1);
			formatter1 = new SimpleDateFormat("MM-dd-yyyy");
			formattedDate1 = formatter1.format(date2);
			proc.setString(8,formattedDate1);
		} else {
			proc.setNull(8, java.sql.Types.VARCHAR);
		}
		rs = proc.executeQuery();
		while(rs.next())
		{
			objReportsDto =  new CustomerBaseReportsDTO();
			replaceSeperator("BILLING_LOCATION",rs.getString("BILLING_ADDRESS"));
			objReportsDto.setFxSiId(rs.getString("FX_SI_ID"));
			objReportsDto.setFx_external_acc_id(rs.getString("FX_ACCOUNT_EXTERNAL_ID"));
			tempDate=rs.getDate("SERVICEACTIVEDT");
			if(tempDate!=null)
			{
				objReportsDto.setActiveDate(utility.showDate_Report(tempDate).toUpperCase());
			}
			objReportsDto.setInActiveDate(rs.getString("DISCONNECTION_DATE"));
			if(!(rs.getString("DISCONNECTION_DATE")==null || rs.getString("DISCONNECTION_DATE")==""))
			{
				objReportsDto.setInActiveDate((utility.showDate_Report(new Date(rs.getTimestamp("DISCONNECTION_DATE").getTime()))).toUpperCase());
			}
			objReportsDto.setOrderNo((rs.getString("ORDERNO")));
			objReportsDto.setCrmACcountNO(rs.getString("CRMACCOUNTNO"));
			objReportsDto.setAccountno(rs.getString("INTERNAL_ID"));
			objReportsDto.setAccountSegment(rs.getString("ACCOUNT_SEGMENT"));
			objReportsDto.setParent_name(rs.getString("PARENT_ID"));
			objReportsDto.setBillingFormatName(VAR_BILL_FNAME);
			objReportsDto.setBillCompany(rs.getString("BILL_COMPANY"));
			objReportsDto.setBillingAddress(VAR_BILL_ADDRESS1);
			objReportsDto.setBilling_address2(VAR_BILL_ADDRESS2);
			objReportsDto.setBilling_address(VAR_BILL_ADDRESS3);
			objReportsDto.setBillCity(VAR_BCP_CITY_NAME);
			objReportsDto.setBillState(VAR_BCP_STATE_NAME);
			objReportsDto.setAccountManager(rs.getString("ACCOUNT_MANAGER_NAME"));
			objReportsDto.setAcmgrEmail(rs.getString("ACCOUNT_MANAGER_EMAILID"));
			objReportsDto.setAccountMgrPhoneNo(rs.getString("ACCOUNT_MANAGER_PHONENO"));
			objReportsDto.setContact1_Phone(VAR_BILL_PHONE);
			objReportsDto.setContact2_Phone(rs.getString("CONTACT2_PHONE"));
			objReportsDto.setBillZip(VAR_BCP_PIN);
			objReportsDto.setOrder_type(rs.getString("ORDER_TYPE"));
			objReportsDto.setContactName(VAR_BILL_CON_PER_NAME);
			objReportsDto.setContactPersonEmail(VAR_EMAIL_ID);
			objReportsDto.setChairPersonName(rs.getString("CHAIRPERSON_NAME"));
			objReportsDto.setChairPersonPhone(rs.getString("CHAIRPERSON_PHONE"));
			objReportsDto.setChairPersonEmail(rs.getString("CHAIRPERSON_EMAIL"));
			objReportsDto.setComponentName(rs.getString("COMPONENT_NAME"));
			objReportsDto.setComponentActiveDate(rs.getString("COMP_ACTIVE_DATE"));
			if(!(rs.getString("COMP_ACTIVE_DATE")==null || rs.getString("COMP_ACTIVE_DATE")==""))
			{
				objReportsDto.setComponentActiveDate((utility.showDate_Report(sdf.parse(rs.getString("COMP_ACTIVE_DATE")))).toUpperCase());
			}
			objReportsDto.setBusinessSegment(rs.getString("BUSINESS_SEGMENT"));
			if((rs.getString("CONFIG_ID")) != null && !"".equals(rs.getString("CONFIG_ID")) && (rs.getString("ENTITYID")) != null && !"".equals(rs.getString("ENTITYID")))
			{
				String tBillPeriod=fnGetUsageReportBIllPeriod(rs.getString("CONFIG_ID"), rs.getString("ENTITYID"));
				objReportsDto.setBill_period(tBillPeriod);
			}
			objReportsDto.setFrequencyName(rs.getString("BILL_FREQUENCY"));
			objReportsDto.setProductName(rs.getString("PRODUCT"));
			if (pagingSorting.isPagingToBeDone()) {
				recordCount = rs.getInt("FULL_REC_COUNT");
			}
			listSearchDetails.add(objReportsDto);
		}
		pagingSorting.setRecordCount(recordCount);
	}
	catch(Exception ex )
	{
		//ex.printStackTrace();	
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
	}
	finally
	{
		try 
		{
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(proc);
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return listSearchDetails;	
}

	public ArrayList<BillingWorkQueueReportDTO> viewBillingWorkQueueList(BillingWorkQueueReportDTO objDto) throws Exception {
		//	Nagarjuna
		String methodName="viewBillingWorkQueueList", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;
		//end Nagarjuna
		ArrayList<BillingWorkQueueReportDTO> objUserList = new ArrayList<BillingWorkQueueReportDTO>();
		Connection conn = null;
		CallableStatement proc = null;
		ResultSet rs = null;
		int recordCount = 0;
		Utility utility=new Utility();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try {
	
			conn = DbConnection.getReportsConnectionObject();
	
			proc = conn.prepareCall(sqlBillingWorkQueue);
			proc.setInt(1, java.sql.Types.BIGINT);
			if (objDto.getOrderType() != null
					&& !"".equals(objDto.getOrderType())) {
				proc.setString(2, objDto.getOrderType().trim());
			} else {
				proc.setNull(2, java.sql.Types.VARCHAR);
			}
			if (objDto.getFromDate() != null
					&& !"".equals(objDto.getFromDate())) {
				proc.setString(3, objDto.getFromDate().trim());
			} else {
				proc.setNull(3, java.sql.Types.VARCHAR);
			}
			if (objDto.getToDate() != null && !"".equals(objDto.getToDate())) {
				proc.setString(4, objDto.getToDate().trim());
			} else {
				proc.setNull(4, java.sql.Types.VARCHAR);
			}
	
			if (objDto.getFromOrderNo() != 0 
					&& !"".equals(objDto.getFromOrderNo())) {
				proc.setInt(5, objDto.getFromOrderNo());
			} else {
				proc.setNull(5,java.sql.Types.BIGINT);
			}
			if (objDto.getToOrderNo() != 0 
					&& !"".equals(objDto.getToOrderNo())) {
				proc.setInt(6, objDto.getToOrderNo());
			} else {
				proc.setNull(6, java.sql.Types.BIGINT);
			}
			
			/*
			 * add party no and party name
			 */
			if (objDto.getParty_no() != 0 
					&& !"".equals(objDto.getParty_no())) {
				proc.setInt(7, objDto.getParty_no());
			} else {
				proc.setNull(7, java.sql.Types.BIGINT);
			}
			if (objDto.getPartyName() != null
					&& !"".equals(objDto.getPartyName())) {
				proc.setString(8, objDto.getPartyName().trim());
			} else {
				proc.setNull(8, java.sql.Types.VARCHAR);
			}
			if (objDto.getOrderStage() != null
					&& !"".equals(objDto.getOrderStage())) {
				proc.setString(9, objDto.getOrderStage().trim());
			} else {
				proc.setNull(9, java.sql.Types.VARCHAR);
			}
			if (objDto.getHardwareType() != null
					&& !"".equals(objDto.getHardwareType())) {
				proc.setString(10, objDto.getHardwareType().trim());
			} else {
				proc.setNull(10, java.sql.Types.VARCHAR);
			}
			PagingSorting pagingSorting = objDto.getPagingSorting();
			pagingSorting.sync();// To calculate start index and Enc Index
	
			proc.setString(11, pagingSorting.getSortByColumn());// columnName
			proc.setString(12, PagingSorting.DB_Asc_Desc(pagingSorting
					.getSortByOrder()));// sort order
			proc.setInt(13, pagingSorting.getStartRecordId());// start index
			proc.setInt(14, pagingSorting.getEndRecordId());// end index
			proc.setInt(15, (pagingSorting.isPagingToBeDone() ? 1 : 0));// end
			//proc.setInt(15, (pagingSorting.isPagingToBeDone() ? 0 : 0));// end
			// index
			//System.out.println("sqlBillingWorkQueue :"+sqlBillingWorkQueue);
			rs = proc.executeQuery();
			int countFlag = 0;
			BillingWorkQueueReportDTO objdto ;
			
			while (rs.next() != false) {
				countFlag++;
			//	System.out.println("in while roop of rs");
				setBlank();
				replaceSeperator("BILLING_LOCATION",rs.getString("BILLING_ADDRESS"));
				replaceSeperator("PRIMARYLOCATION",rs.getString("PRIMLOC"));
				replaceSeperator("SECONDARYLOCATION",rs.getString("SECLOC"));
				objdto = new BillingWorkQueueReportDTO();
				objdto.setLogicalSINo(rs.getString("LOGICAL_CIRCUIT_ID"));
				objdto.setCustSINo(rs.getString("CUST_LOGICAL_SI_ID"));
				objdto.setServiceName(rs.getString("SERVICE_NAME"));
				objdto.setLinename(rs.getString("LINE_NAME"));
//				objdto.setChargeTypeName(rs.getString("CHARGE_TYPE"));
//				objdto.setChargeTypeID(rs.getInt("CHARGE_TYPE_ID")); 
//				objdto.setChargeName(rs.getString("CHARGE_NAME"));
//				objdto.setChargeFrequency(rs.getString("FREQUENCY"));
//				objdto.setBillPeriod(rs.getString("BILL_PERIOD"));
				/*
				 * newly added fields in code
				 */
//				objdto.setEndDateLogic(rs.getString("ENDDATELOGIC"));
//				if (rs.getString("CHARGE_START_DATE") != null && !"".equals(rs.getString("CHARGE_START_DATE")))
//				{
//					objdto.setStartDate(rs.getString("CHARGE_START_DATE"));
					//Date date=df.parse(objdto.getStartDate());
					//objdto.setStartDate((utility.showDate_Report(date)).toUpperCase());
//				}
//				if (rs.getString("CHARGE_END_DATE") != null && !"".equals(rs.getString("CHARGE_END_DATE")))
//				{
//					objdto.setChargeEndDate(rs.getString("CHARGE_END_DATE"));
//				}
//				objdto.setAdvance(rs.getString("ADVANCE"));
//				objdto.setRate_code(rs.getString("TRAI_RATE"));
//				objdto.setDiscount(rs.getString("DISCOUNT"));
//				objdto.setInstallRate(rs.getString("INSTALRATE"));
//				objdto.setInterestRate(rs.getInt("INTREST_RATE"));
//				objdto.setPrincipalAmount(rs.getInt("PRINCIPAL_AMOUNT"));
				objdto.setNoticePeriod(rs.getLong("NOTICEPERIOD"));
				objdto.setPenaltyClause(rs.getString("PENALTYCLAUSE"));
				objdto.setCommitmentPeriod(rs.getInt("COMMITMENTPERIOD"));
				
//				if (rs.getString("WARRANTY_START_DATE") != null && !"".equals(rs.getString("WARRANTY_START_DATE")))
//				{
//					objdto.setWarrantyStartDate(rs.getString("WARRANTY_START_DATE"));
//					objdto.setPoDate((utility.showDate_Report(objdto.getWarrantyStartDate())).toUpperCase());
//				}
//				if (rs.getString("WARRANTY_END_DATE") != null && !"".equals(rs.getString("WARRANTY_END_DATE")))
//				{
//					objdto.setWarrantyEndDate(rs.getString("WARRANTY_END_DATE"));
//					objdto.setPoDate((utility.showDate_Report(objdto.getWarrantyEndDate())).toUpperCase());
//				}
//				objdto.setExtSuportEndDate(rs.getString("EXT_SUPPORT_END_DATE"));
				objdto.setContractStartDate(rs.getString("CONTRACT_START_DATE"));
				objdto.setContractEndDate(rs.getString("CONTRACT_END_DATE"));
//				objdto.setStartDateLogic(rs.getString("STARTDATELOGIC"));
//				objdto.setDnd_Dispatch_And_Delivered(rs.getString("DND_DISPATCH_AND_DELIVERED"));
//				objdto.setDnd_Dispatch_But_Not_Delivered(rs.getString("DND_DISPATCH_BUT_NOT_DELIVERED"));
				objdto.setBilling_address(VAR_BILLING_ADDRESS);
				objdto.setServiceTypeDescription(rs.getString("SERVICE_ORDER_TYPE_DESC"));
				objdto.setCancelBy(rs.getString("CANCEL_BY"));
				objdto.setCanceldate(rs.getString("CANCEL_DATE"));
				if (rs.getString("CANCEL_DATE") != null && !"".equals(rs.getString("CANCEL_DATE")))
				{
					objdto.setCanceldate((utility.showDate_Report(new Date(rs.getTimestamp("CANCEL_DATE").getTime()))).toUpperCase());
				}
				objdto.setCancelReason(rs.getString("CANCEL_REASON"));
				objdto.setDemo(rs.getString("DEMO_TYPE"));
				/*
				 * end of newly ended code
				 */
				// start_date = ?
				objdto.setAccountID(rs.getInt("ACCOUNT_NUMBER"));
				objdto.setCreditPeriodName(rs.getString("CREDIT_PERIOD"));
				//objdto.setCurrencyName(rs.getString("CURNAME")); // is it currency 
				objdto.setCurrencyName(rs.getString("CURRENCY"));
				//objdto.setEntity(rs.getString("ENTITYNAME")); // is it legal entity
				objdto.setEntity(rs.getString("LEGAL_ENTITY")); 
				objdto.setBillingMode(rs.getString("BILLING_MODE_NAME")); // text of BILLINGMODE
				objdto.setBillingTypeName(rs.getString("BILL_TYPE"));
				objdto.setBillingformat(rs.getString("BILL_FORMAT"));
				objdto.setLicCompanyName(rs.getString("LICENSE_COMP"));
				objdto.setTaxation(rs.getString("TAXATION_NAME")); // is it TAXATION
//				objdto.setTaxation(rs.getString("TAXATION"));
				objdto.setBillingLevelNo(rs.getLong("BILLING_LEVEL_NO")); 
				objdto.setBillingLevelName(rs.getString("BILLING_LEVELNAME")); // is it BILLINGLEVEL
//				objdto.setBillingLevelName(rs.getString("BILLINGLEVEL"));
				//objdto.setStore(rs.getString("STORENAME")); // is it STORE 
//				objdto.setStore(rs.getString("STORE"));
//				objdto.setHardwaretypeName(rs.getString("HARDWARETYPE"));
				objdto.setFormAvailable(rs.getString("FORM_C_AVAILABLE"));
//				objdto.setSaleNature(rs.getString("NATURE_OF_SALE"));
//				objdto.setSaleTypeName(rs.getString("TYPE_OF_SALE"));
				objdto.setPrimaryLocation(VAR_PRIMARYLOCATION);
				objdto.setSeclocation(VAR_SECONDARYLOCATION);
				objdto.setPoNumber(rs.getInt("PODETAILNUMBER"));
				objdto.setPoDate(rs.getString("PODATE"));
				if (rs.getString("PODATE") != null && !"".equals(rs.getString("PODATE")))
				{
					Date date=rs.getDate("PODATE");
					objdto.setPoDate((utility.showDate_Report(date)).toUpperCase());
				}
				
				objdto.setParty_num(rs.getString("PARTY_NO"));  
//				objdto.setChargeAnnotation(rs.getString("ANNOTATION"));
//				objdto.setFx_sd_charge_status(rs.getString("FX_SD_CHG_STATUS"));
				
//				objdto.setFx_charge_status(rs.getString("FX_STATUS"));
//				objdto.setFx_Ed_Chg_Status(rs.getString("FX_ED_CHG_STATUS"));
				//objdto.setTokenID(rs.getInt("TOKEN_ID")); // is it TOKEN_NO
//				objdto.setTokenno(rs.getString("TOKEN_NO"));
				objdto.setModifiedDate(rs.getString("LAST_UPDATE_DATE"));
				if (rs.getString("LAST_UPDATE_DATE") != null && !"".equals(rs.getString("LAST_UPDATE_DATE")))
				{
					objdto.setModifiedDate((utility.showDate_Report(new Date(rs.getTimestamp("LAST_UPDATE_DATE").getTime()))).toUpperCase());
				}
				objdto.setBillingTriggerFlag(rs.getString("BILLING_TRIG_FLAGNAME")); // text of BILLING_TRIG_FLAG
				
				if (rs.getString("PM_PROV_DATE") != null && !"".equals(rs.getString("PM_PROV_DATE")))
				{
					 objdto.setPm_pro_date(rs.getString("PM_PROV_DATE"));
					 /* String s1=rs.getString("PM_PROVISIONING_DATE");
					  String s3=s1.substring(0,7).toUpperCase();
					  String s4=s1.substring(9,11);
					  String s5=s3.concat(s4);
					  objdto.setPm_pro_date(s5);*/
				}
				if (rs.getString("LOC_DATE") != null && !"".equals(rs.getString("LOC_DATE")))
				{
					objdto.setLocDate(rs.getString("LOC_DATE"));
					Date date=df.parse(objdto.getLocDate());
					objdto.setLocDate((utility.showDate_Report(date)).toUpperCase());
				}
				if (rs.getString("BILLING_TRIG_DATE") != null && !"".equals(rs.getString("BILLING_TRIG_DATE")))
				{
					objdto.setBilling_trigger_date(rs.getString("BILLING_TRIG_DATE"));
					Date date=df.parse(objdto.getBilling_trigger_date());
					objdto.setBilling_trigger_date((utility.showDate_Report(date)).toUpperCase());
				}
//				objdto.setChallenno(rs.getString("CHALLEN_NO"));
//				objdto.setChallendate(rs.getString("CHALLEN_DATE"));
				
				if (rs.getString("CHALLEN_DATE") != null && !"".equals(rs.getString("CHALLEN_DATE")))
				{
				  String s1=rs.getString("CHALLEN_DATE");
				  if(s1.length()==10){
					  s1="0"+s1;
				  }
				  String s3=s1.substring(0,7).toUpperCase();
				  String s4=s1.substring(9,11);
				  String s5=s3.concat(s4);
				  objdto.setChallendate(s5);
				}
				
				//FX_ACCOUNT_EXTERNAL_ID = ?
				//objdto.setFx_external_acc_id(rs.getString("FX_ACCOUNT_EXTERNAL_ID"));
				//objdto.setChild_account_creation_status(rs.getString("CHILD_ACCOUNT_FX_STATUS"));
				objdto.setChild_act_no(rs.getString("CHILD_ACCOUNT_NUMBER"));
				objdto.setChild_ac_fxstatus(rs.getString("CHILD_ACCOUNT_STATUS_NAME")); // text of CHILD_ACCOUNT_FX_STATUS
				
				if (rs.getString("ORDERDATE") != null && !"".equals(rs.getString("ORDERDATE")))
				{
					objdto.setOrderDate(rs.getString("ORDERDATE"));
					//Date date=df.parse(objdto.getOrderDate());
					objdto.setOrderDate((utility.showDate_Report(objdto.getOrderDate())).toUpperCase());
					
				}
				if (rs.getString("APPROVED_DATE") != null && !"".equals(rs.getString("APPROVED_DATE")))
				{
					objdto.setCopcapprovaldate(rs.getString("APPROVED_DATE"));	
					objdto.setCopcapprovaldate((utility.showDate_Report(objdto.getCopcapprovaldate())).toUpperCase());
				}
				objdto.setOrderType(rs.getString("ORDERTYPE"));
				if (rs.getString("BILL_TRG_CREATE_DATE") != null && !"".equals(rs.getString("BILL_TRG_CREATE_DATE")))
				{
					objdto.setBillingtrigger_createdate(rs.getString("BILL_TRG_CREATE_DATE"));
					objdto.setBillingtrigger_createdate((utility.showDate_Report(new Date(rs.getTimestamp("BILL_TRG_CREATE_DATE").getTime()))).toUpperCase());
				}
				objdto.setRatio(rs.getString("RATIO"));
				objdto.setProductName(rs.getString("PRODUCT"));
				objdto.setSubProductName(rs.getString("SUBPRODUCT"));
				objdto.setHardwareType(rs.getString("HARDWARE_FLAG"));
				objdto.setServiceStage(rs.getString("SERVICE_STAGE"));
				objdto.setOrderStage(rs.getString("ORDER_STAGE")); 
				if (rs.getString("RFS_DATE") != null && !"".equals(rs.getString("RFS_DATE")))
				{
					objdto.setRfsDate(rs.getString("RFS_DATE"));
					objdto.setRfsDate((utility.showDate_Report(objdto.getRfsDate())).toUpperCase());
					
				}
				// PORECEIVEDATE = ?
				/*objdto.setPoReceiveDate(rs.getString("PORECEIVEDATE"));
				if (rs.getString("PORECEIVEDATE") != null && !"".equals(rs.getString("PORECEIVEDATE")))
				{
					
					Date date=df.parse(objdto.getPoReceiveDate());
					objdto.setPoReceiveDate((utility.showDate_Report(date)).toUpperCase());
					
				}*/
				objdto.setCustPoDetailNo(rs.getString("CUSTPONUMBER"));
				if (rs.getString("CUSTPODATE") != null && !"".equals(rs.getString("CUSTPODATE")))
				{
					objdto.setCustPoDate(rs.getString("CUSTPODATE"));
					//Date date=df.parse(objdto.getCustPoDate());
					objdto.setCustPoDate((utility.showDate_Report(objdto.getCustPoDate())).toUpperCase());
				}
//				objdto.setCharge_status(rs.getString("CHARGES_STATUS"));
				objdto.setLOC_No(rs.getString("LOC_NUMBER"));
				//objdto.setAddress1(rs.getString("ADDRESS")); // is it BILLING_ADDRESS
				objdto.setAddress1(VAR_BILLING_ADDRESS);
				objdto.setM6cktid(rs.getString("CIRCUIT_ID")); 
				objdto.setOrdersubtype(rs.getString("ORDERSUBTYPE"));
				objdto.setRegion(rs.getString("REGION"));
				objdto.setBandwaidth(rs.getString("BILLING_BANDWIDTH"));     
				objdto.setVertical(rs.getString("VERTICAL"));
				objdto.setAccountManager(rs.getString("ACCOUNT_MGR"));
				objdto.setProjectManager(rs.getString("PROJECT_MGR"));
				objdto.setDistance(rs.getString("CHARGEABLE_DISTANCE"));
//				objdto.setDispatchAddress1(rs.getString("DISPATCH_ADDRESS"));
				objdto.setIndicative_value(rs.getString("INDICATIVE_VALUE"));
				// productname
				objdto.setPartyName(rs.getString("PARTY_NAME"));
				//objdto.setBilling_location_from(rs.getString("BILLING_ADDRESS"));
				
				// DEMO_ORDER = ?
				//objdto.setDemo(rs.getString("DEMO_ORDER"));
				
				// CRM_PRODUCTNAME = ?
				//objdto.setCrm_productname(rs.getString("CRM_PRODUCTNAME"));
				
				objdto.setToLocation(rs.getString("TO_ADDRESS"));
				objdto.setFromLocation(rs.getString("FROM_ADDRESS"));
				objdto.setBilling_bandwidth(rs.getString("BILLING_BANDWIDTH"));
				
				//BILLING_BANDWIDTH_UOM = ?   //remove this column
				//objdto.setBilling_uom(rs.getString("BILLING_BANDWIDTH_UOM"));
				
				//BL_SOURCE = ?
				//objdto.setBlSource(rs.getString("BL_SOURCE"));
				objdto.setServiceproductid(rs.getInt("ORDER_LINE_ID"));
				objdto.setOrderNumber(rs.getInt("ORDERID")); 
//				objdto.setChargeAmount(rs.getDouble("INV_AMT"));
				// LINEITEMAMOUNT = ?
				//objdto.setLineamt(rs.getDouble("LINEITEMAMOUNT"));
				//TOTAL_CHARGE_AMT = ?
				//objdto.setChargesSumOfLineitem(rs.getDouble("TOTAL_CHARGE_AMT"));
//				objdto.setContractPeriod(rs.getInt("CONTRACT_PERIOD_MNTHS"));
				
				//objdto.setTotalPoAmt(""+rs.getDouble("TOTAL_POAMOUNT")); // is it POAMOUNT
				objdto.setTotalPoAmt(""+rs.getDouble("POAMOUNT"));
				//PARTY_ID = ?
				//objdto.setParty_id(rs.getInt("PARTY_ID")); // is it party no
				
				// CRMACCOUNTNO = ?
				//objdto.setCrmAccountNoString(rs.getString("CRMACCOUNTNO"));
				//m6 productid
				objdto.setM6OrderNo(rs.getInt("M6_ORDER_ID"));
				
				// CUST_TOT_PO_AMT = ?
				objdto.setCust_total_poamt(rs.getDouble("tot_amount")); // old value from CUST_TOT_PO_AMT
				
				//m6 order,business
				// PK_CHARGE_ID = ?
			     //objdto.setPk_charge_id(rs.getString("PK_CHARGE_ID"));//Added by Ashutosh as on 26-Jun-12
			     
				// objdto.setContractPeriod(rs.getInt("CONTRACTPERIOD")); // CONTRACT_PERIOD_MNTHS is using above 
//			     objdto.setAnnual_rate(rs.getDouble("ANNUAL_RATE"));
			     objdto.setServiceId(rs.getInt("SERVICE_NO"));
				if (rs.getString("PO_EXPIRY_DATE") != null && !"".equals(rs.getString("PO_EXPIRY_DATE")))
				{
					objdto.setPoExpiryDate(rs.getString("PO_EXPIRY_DATE"));
					objdto.setPoExpiryDate((utility.showDate_Report(objdto.getPoExpiryDate())).toUpperCase());
				}
				if (pagingSorting.isPagingToBeDone()) {
					 recordCount = rs.getInt("FULL_REC_COUNT");
					//recordCount = countFlag;
				}
				objdto.setFxInternalId(rs.getInt("INTERNAL_ID"));
//				objdto.setMinimum_bandwidth(rs.getString("MINIMUM_BANDWIDTH"));//Need to add in View : AKS(Added)
//				objdto.setMinimum_bandwidth_UOM(rs.getString("MINIMUM_BANDWIDTH_UOM"));//Need to add in View : AKS(Added)
				objdto.setComponentInfoID(rs.getInt("COMPONENTINFOID"));
				objdto.setComponentID(rs.getInt("COMPONENT_ID"));
				objdto.setComponentName(rs.getString("COMPONENT_NAME"));
				objdto.setPackageID(rs.getInt("PACKAGE_ID"));
				objdto.setPackageName(rs.getString("PACKAGE_NAME"));
				ComponentsDto dto = new ComponentsDto();
				dto.setComponentType(rs.getString("COMPONENT_TYPE"));
				dto.setComponentAmount(rs.getDouble("COMP_AMOUNT"));//RC_NRC_COMP_AMOUNT : AKS
				dto.setComponentStatus(rs.getString("COMPONENT_STATUS"));
				dto.setStartLogic(rs.getString("COMPONENT_START_LOGIC"));
				dto.setStartDate(rs.getString("SYSTEM_START_DATE")); // COMPONENT_START_DATE
				if (rs.getString("SYSTEM_START_DATE") != null && !"".equals(rs.getString("SYSTEM_START_DATE")))
				{
					Date date=df.parse(dto.getStartDate());
					dto.setStartDate((utility.showDate_Report(date)).toUpperCase());
				}
				dto.setEndLogic(rs.getString("COMPONENT_END_LOGIC"));
				dto.setEnd_date(rs.getString("SYSTEM_END_DATE")); // COMPONENT_END_DATE
				if (rs.getString("SYSTEM_END_DATE") != null && !"".equals(rs.getString("SYSTEM_END_DATE")))
				{
					Date date=df.parse(dto.getEnd_date());
					dto.setEnd_date((utility.showDate_Report(date)).toUpperCase());
				}
				dto.setFxTokenNo(rs.getString("START_COMPONENT_TOKEN_NO"));
				dto.setEndTokenNo(rs.getString("END_COMPONENT_TOKEN_NO"));
				dto.setFxStartStatus(rs.getString("SYSTEM_START_STATUS"));
				//dto.setChargesSumOfLineitem(rs.getString("FX_START_COMPONENT_STATUS"));
				dto.setComponentInstanceID(rs.getString("COMPONENT_INST_ID"));//COMP_FX_INSTANCE_ID : AKS
				dto.setEndFxStatus(rs.getString("SYSTEM_END_STATUS"));//FX_END_COMPONENT_STATUS :AKS
				//dto.setStartStatus(rs.getString("FX_ST_COMPONENT_STATUS"));
				objdto.setComponentDto(dto);
				
				objUserList.add(objdto);
			}
			pagingSorting.setRecordCount(recordCount);
		} catch (Exception ex) {
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			//ex.printStackTrace();
			throw new Exception("SQL Exception : " + ex.getMessage(), ex);
		} finally {
			try {
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(proc);
				DbConnection.freeConnection(conn);

			} catch (Exception e) {
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
				//e.printStackTrace();
				throw new Exception("Exception : " + e.getMessage(), e);
			}
		}

		return objUserList;
	}

	public ArrayList<OBValueReportDTO> viewOBValueReport_Usage(OBValueReportDTO objDto) throws Exception {

		String methodName="viewOBValueReport", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;	
		ArrayList<OBValueReportDTO> objUserList = new ArrayList<OBValueReportDTO>();
		Connection conn = null;
		CallableStatement proc = null;
		ResultSet rs = null;
		int recordCount = 0;
		OBValueReportDTO objRDto;
		Utility utility=new Utility();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try {
			conn = DbConnection.getReportsConnectionObject();
			proc = conn.prepareCall(sqlOBValueReport_Usage);

			if (objDto.getOrderType() != null
					&& !"".equals(objDto.getOrderType())) {
				proc.setString(1, objDto.getOrderType().trim());
			}else{
				proc.setNull(1, java.sql.Types.VARCHAR);
			}
			if (objDto.getFromDate() != null && !"".equals(objDto.getFromDate())) {
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				Date dateStr = formatter.parse(objDto.getFromDate());
				String formattedDate = formatter.format(dateStr);
				Date date1 = formatter.parse(formattedDate);
				formatter = new SimpleDateFormat("MM-dd-yyyy");
				formattedDate = formatter.format(date1);
				proc.setString(2,formattedDate);
			}else{
				proc.setNull(2, java.sql.Types.VARCHAR);
			}
			if (objDto.getToDate() != null && !"".equals(objDto.getToDate())) {
				SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
				Date dateStr1 = formatter1.parse(objDto.getToDate());
				String formattedDate1 = formatter1.format(dateStr1);
				Date date2 = formatter1.parse(formattedDate1);
				formatter1 = new SimpleDateFormat("MM-dd-yyyy");
				formattedDate1 = formatter1.format(date2);
				proc.setString(3,formattedDate1);
			}else{
				proc.setNull(3, java.sql.Types.VARCHAR);
			}
			if (objDto.getVerticalDetails() != null && !"".equals(objDto.getVerticalDetails())) {
				proc.setString(4, objDto.getVerticalDetails().trim());
			} else {
				proc.setNull(4, java.sql.Types.VARCHAR);
			}
			PagingSorting pagingSorting = objDto.getPagingSorting();
			pagingSorting.sync();// To calculate start index and Enc Index
			proc.setString(5, pagingSorting.getSortByColumn());// columnName
			proc.setString(6, PagingSorting.DB_Asc_Desc(pagingSorting.getSortByOrder()));// sort order
			proc.setInt(7, pagingSorting.getStartRecordId());// start index
			proc.setInt(8, pagingSorting.getEndRecordId());// end index
			proc.setInt(9, (pagingSorting.isPagingToBeDone() ? 1 : 0));// end
			rs = proc.executeQuery();
			int countFlag = 0;
			while (rs.next() != false) {
				countFlag++;

				objDto = new OBValueReportDTO();
				objDto.setPartyNo(rs.getInt("PARTY_NO"));
				objDto.setPartyName(rs.getString("PARTYNAME"));
				objDto.setOrderNo(rs.getString("ORDERNO"));
				objDto.setOpportunityId((rs.getString("OPPORTUNITYID")));
				objDto.setLineType(rs.getString("LINETYPE"));
				objDto.setCopcApproveDate(rs.getString("COPC_APPROVAL_DATE"));
				if (rs.getString("COPC_APPROVAL_DATE") != null && !"".equals(rs.getString("COPC_APPROVAL_DATE"))){
					objDto.setCopcApproveDate((utility.showDate_Report(new Date(rs.getTimestamp("COPC_APPROVAL_DATE").getTime()))).toUpperCase());
				}
				objDto.setLogicalCircuitId(rs.getString("LOGICALCKTID"));
				objDto.setServiceId(rs.getInt("SERVICENO"));
				objDto.setQuoteNo(rs.getString("QUOTENO"));
				objDto.setProductName(rs.getString("PRODUCTNAME"));
				objDto.setSubProductName(rs.getString("SUBPRODUCTNAME"));
				if (rs.getString("FROM_SITE") != null && !"".equals(rs.getString("FROM_SITE")) && rs.getString("PRIMARYLOCATIONTYPE")!= null){
					if(Integer.parseInt(rs.getString("PRIMARYLOCATIONTYPE"))==1){
						String ss[] =rs.getString("FROM_SITE").split("~~");
						objDto.setPrimaryLocation(ss[0]+" "+ss[1]+" "+ss[4]+" "+ss[5]+" "+ss[6]+" "+ss[7]+" "+ss[8]+" "+ss[9]+" "+ss[10]+" "+ss[2]);
					}else{
						String ss[] =rs.getString("FROM_SITE").split("~~");
						objDto.setPrimaryLocation(ss[0]+" "+ss[1]+" "+ss[5]+" "+ss[3]);
					}
				}else{
					objDto.setPrimaryLocation("");
				}
				if (rs.getString("TO_SITE") != null && !"".equals(rs.getString("TO_SITE")) && rs.getString("SECONDARYLOCATIONTYPE")!= null){
					if(Integer.parseInt(rs.getString("SECONDARYLOCATIONTYPE"))==1){
						String ss[] =rs.getString("TO_SITE").split("~~");
						objDto.setSeclocation(ss[0]+" "+ss[1]+" "+ss[4]+" "+ss[5]+" "+ss[6]+" "+ss[7]+" "+ss[8]+" "+ss[9]+" "+ss[10]+" "+ss[2]);
					}else{
						String ss[] =rs.getString("TO_SITE").split("~~");
						objDto.setSeclocation(ss[0]+" "+ss[1]+" "+ss[5]+" "+ss[3]);
					}
				}else{
					objDto.setSeclocation("");
				}
				objDto.setOrdersubtype(rs.getString("ORDERSUBTYPE"));
				objDto.setBandwaidth(rs.getString("BANDWIDTH"));
				objDto.setPrjmngname(rs.getString("PRJ_MGR_NAME"));
				objDto.setPrjmgremail(rs.getString("PROJECTMGR_MAIL"));
				objDto.setActmngname(rs.getString("ACCT_MGR_NAME"));
				objDto.setZoneName(rs.getString("ZONENNAME"));
				objDto.setRegionName(rs.getString("REGIONNAME"));
				objDto.setVerticalDetails(rs.getString("VERTICAL_DETAILS"));
				objDto.setAct_category(rs.getString("ACCOUNTCATEGORY_DETAILS"));
				objDto.setCustPoDetailNo(rs.getString("CUSTPONUMBER"));
				objDto.setCustPoDate(rs.getString("CUSTPODATE"));
				if (rs.getString("CUSTPODATE") != null && !"".equals(rs.getString("CUSTPODATE"))){
					objDto.setCustPoDate((utility.showDate_Report(new Date(rs.getTimestamp("CUSTPODATE").getTime()))).toUpperCase());
				}
				objDto.setOrderDate(rs.getString("ORDERDATE"));
				if (rs.getString("ORDERDATE") != null && !"".equals(rs.getString("ORDERDATE"))){
					objDto.setOrderDate((utility.showDate_Report(new Date(rs.getTimestamp("ORDERDATE").getTime()))).toUpperCase());
				}
				objDto.setPmApproveDate(rs.getString("PM_APPROVAL_DATE"));
				if (rs.getString("PM_APPROVAL_DATE") != null && !"".equals(rs.getString("PM_APPROVAL_DATE"))){
					objDto.setPmApproveDate((utility.showDate_Report(new Date(rs.getTimestamp("PM_APPROVAL_DATE").getTime()))).toUpperCase());
				}
				objDto.setAmApproveDate(rs.getString("AM_APPROVAL_DATE"));
				if (rs.getString("AM_APPROVAL_DATE") != null && !"".equals(rs.getString("AM_APPROVAL_DATE"))){
					objDto.setAmApproveDate((utility.showDate_Report(new Date(rs.getTimestamp("AM_APPROVAL_DATE").getTime()))).toUpperCase());
				}
				objDto.setDemo_infrastartdate(rs.getString("DEMP_INFRA_START_DATE"));
				objDto.setDemo_infra_enddate(rs.getString("DEMO_INFRA_ENDDATE"));
				objDto.setRfs_date(rs.getString("RFS_DATE"));
				if (rs.getString("RFS_DATE") != null && !"".equals(rs.getString("RFS_DATE"))){
					objDto.setRfs_date((utility.showDate_Report(new Date(rs.getTimestamp("RFS_DATE").getTime()))).toUpperCase());
				}
				objDto.setOrdercategory(rs.getString("ORDERCATEGORY"));
				objDto.setOrderStatus(rs.getString("STATUS"));
				objDto.setLinename(rs.getString("LINENAME"));
				objDto.setSub_linename(rs.getString("ORDER_SUBLINENAME"));
				objDto.setComponentName(rs.getString("COMPONENT_NAME"));
				objDto.setComponentID(rs.getInt("COMPONENTINFOID"));
				objDto.setServiceProductID(rs.getInt("LINENO"));
				objDto.setServiceName(rs.getString("SERVICENAME"));
				objDto.setAccountID(rs.getInt("ACCOUNTID"));
				objDto.setEntity(rs.getString("COMPANYNAME"));
				objDto.setLicCompanyName(rs.getString("LCOMPANYNAME"));
				objDto.setContractPeriod(rs.getInt("CONTRACTPERIOD"));
				objDto.setServiceType(rs.getString("SERVICETYPE"));
				objDto.setUom(rs.getString("UOM"));
				objDto.setBilling_bandwidth(rs.getString("BILLING_BANDWIDTH"));
				objDto.setBilling_uom(rs.getString("BILLING_BANDWIDTH_UOM"));
				if (rs.getString("FROM_CITY") != null && !"".equals(rs.getString("FROM_CITY")) && rs.getString("PRIMARYLOCATIONTYPE")!= null ){
					if(Integer.parseInt(rs.getString("PRIMARYLOCATIONTYPE"))==1){
						String ss[] =rs.getString("FROM_CITY").split("~~");
						objDto.setFrom_city(ss[8]);
					}else{
						objDto.setFrom_city(" ");
					}
				}else{
					objDto.setFrom_city("");
				}
				if (rs.getString("TO_CITY") != null && !"".equals(rs.getString("TO_CITY")) && rs.getString("SECONDARYLOCATIONTYPE")!= null ){
					if(Integer.parseInt(rs.getString("SECONDARYLOCATIONTYPE"))==1){
						String ss[] =rs.getString("TO_CITY").split("~~");
						objDto.setTo_city(ss[8]);
					}else{
						objDto.setTo_city(" ");
					}				
				}else{
					objDto.setTo_city("");
				}
				objDto.setOldordertotal(rs.getString("OLD_ORDER_TOTAL"));
				objDto.setOldlineamt(rs.getString("OLD_LINE_AMT"));
				objDto.setOld_contract_period(rs.getString("OLD_CONTRACTPERIOD"));
				objDto.setRatio(rs.getString("RATIO"));
				objDto.setTaxation(rs.getString("TAXATIONVALUE"));
				objDto.setFactory_ckt_id(rs.getString("FACTORY_CKT_ID"));
				objDto.setDistance(rs.getString("DISTANCE"));
				objDto.setAccountManager(rs.getString("ACCOUNTMGR_EMAIL"));
				objDto.setCurrencyCode(rs.getString("CURNAME"));
				objDto.setOrderTotal(rs.getDouble("ORDERTOTAL"));
				objDto.setPoAmount(rs.getString("POAMOUNT"));
				objDto.setBisource(rs.getString("BISOURCE"));
				objDto.setOrderType(rs.getString("ORDERTYPE"));
				objDto.setDispatchAddress1(rs.getString("DISPATCHADDRESS"));
				objDto.setParent_name(rs.getString("PARENTNAME"));
				objDto.setAccountName(rs.getString("ACCOUNTNAME"));
				objDto.setLoc_date(rs.getString("LOC_DATE"));
				objDto.setFinalOB(BigDecimal.valueOf((rs.getDouble("OB_VALUE"))).toPlainString());
				objDto.setFinalOBINR(Double.valueOf(Utility.round(rs.getDouble("OB_VALUE_INR"), 2)).toString());
				objDto.setCopcApprovedBy(rs.getString("COPC_APPROVER_NAME"));
				objDto.setPmApprovedby(rs.getString("PM_APPROVER_NAME"));
				objDto.setDemoFlag(rs.getString("ISDEMO"));
				objDto.setOffnet(rs.getString("OFFNET_LABELATTVALUE"));
				objDto.setMediaType(rs.getString("MEDIA_LABELATTVALUE"));
				objDto.setCancellationReason(rs.getString("CANCELLATION_REASON"));
				objDto.setrRDate(rs.getString("RR_DATE"));
				if (rs.getString("RR_DATE") != null && !"".equals(rs.getString("RR_DATE"))){
					objDto.setrRDate((utility.showDate_Report(new Date(rs.getTimestamp("RR_DATE").getTime()))).toUpperCase());
				}
				objDto.setDiffDays(rs.getString("DIFF_DAYS"));
				objDto.setOrderEnteredBy(rs.getString("ORDER_CREATED_BY_NAME"));
				objDto.setExchangeRate(BigDecimal.valueOf((rs.getDouble("EXCHANGE_RATE"))).toPlainString());
				objDto.setObValue(BigDecimal.valueOf((rs.getDouble("OB_VALUE_TRANSACTION"))).toPlainString());
				objDto.setObValueINR(Double.valueOf(Utility.round(rs.getDouble("OB_VALUE_TRANSACTION_INR"), 2)).toString());
				
				objDto.setCustomerSegment(rs.getString("CUST_SEGMENT_CODE")); //newly added
				objDto.setProjectCategory(rs.getString("ORDERCATGRY_LABELATTVALUE")); //newly added
 				objDto.setServiceRemarks(rs.getString("SERVICE_REMARKS")); // newly added
				
				objDto.setObMonth(rs.getString("OB_MONTH"));
				objDto.setObYear(rs.getString("OB_YEAR"));  //pankaj
				
				objDto.setEntryType(rs.getString("ENTRY_TYPE"));
				objDto.setIsNfa("NFA");
				//[007] start
				objDto.setSalesForceOpportunityNumber(rs.getString("SF_OPP_ID"));
				objDto.setChannelPartner(rs.getString("PARTNER_NAME"));
				objDto.setNetworkType(rs.getString("NETWORK_SERVICE_TYPE"));
				objDto.setPartnerId(rs.getString("PARTNER_ID"));
				//[007] End
				if (pagingSorting.isPagingToBeDone()) {
					recordCount = rs.getInt("FULL_REC_COUNT");
				}
				objUserList.add(objDto);
			}
			pagingSorting.setRecordCount(recordCount);
		} catch (Exception ex) {
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);
			throw new Exception("SQL Exception : " + ex.getMessage(), ex);
		} finally {
			try {
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(proc);
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);
				throw new Exception("Exception : " + e.getMessage(), e);
			}
		}

		return objUserList;
	}
}
