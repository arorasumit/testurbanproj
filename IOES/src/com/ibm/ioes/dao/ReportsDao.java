//Tag Name Resource Name  Date		CSR No			Description 
//[001]	 LawKush		10-Feb-11	CSR00-05422     for orders pending in billing and hardware
//[005]	 Rohit Verma	19-Feb-2013	CSR00-07481		New Interface to show Mapping & Redirection Between LSI
//[101010] Rampratap 06-03-2013 added for clep order report
//[RPT7052013031]    Vijay    15-May-2013          Some Fields have added and some code have been changed according MQT in Zero Order value Report //
//[RPT7052013020]    Vijay    15-May-2013          Dates are formatted in Order Stage Report  //
//[RPT7052013027]    Vijay    17-May-2013          modify report for Rate Renewal Report  //
//  Mohit    3-June-2013          modify report for MIGRATED APPROVED NEW ORDER DETAIL REPORT  //
//  MOhit    3-June-2013          modify report for NON MIG APPROVED/UNAPPROVED NEW ORDER DETAILS  //
//[006]	 Rohit Verma	1-Mar-2013	CSR00-08440		New Interface to show Hardware Lines for Cancelation	
//[505053]  		Vijay     12-May-2013           Handling parsing exception
//[505054]   Neha Maheshwari    04-Dec-2014   CSR20141014-R2-020535-Service Segment Configuration-SMB
//[003] 	 Gunjan Singla  22-Jan-15  20141113-R1-020802     ParallelUpgrade-Multiple LSI Selection 
//[004]      Neha Maheshwari    14-Apr-15     CSR-20150212-R1-021088  Addition of Column in Active and Pending Line Item Reports
//[007]      Neha Maheshwari 10-Jun-2015 20141219-R2-020936 Billing Efficiency Program Reports
//[008]      Neha Maheshwari 07-Sep-2015 20150819-XX-021651 TimeStamp Reports
package com.ibm.ioes.dao;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import com.ibm.ioes.forms.UserAccessMatrixDto;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

//import org.apache.commons.net.ntp.TimeStamp;
//import org.apache.taglibs.string.UpperCaseTag;


import com.ibm.ioes.beans.ArchivalReportBean;
import com.ibm.ioes.bulkupload.dto.TransactionUploadDto;
import com.ibm.ioes.forms.ArchivalReportDto;
import com.ibm.ioes.forms.BCPAddressDto;
import com.ibm.ioes.forms.ChangeOrderTypeDto;
import com.ibm.ioes.forms.DispatchAddressDto;
import com.ibm.ioes.forms.LineItemDTO;
import com.ibm.ioes.forms.MastersAttributesDto;
import com.ibm.ioes.forms.NetworkLocationDto;
import com.ibm.ioes.forms.LocationDetailDto;
import com.ibm.ioes.forms.M6OrderStatusDto;
import com.ibm.ioes.forms.NewOrderDto;
import com.ibm.ioes.forms.ReportsDto;
import com.ibm.ioes.forms.UserAccessMatrixDto;
import com.ibm.ioes.newdesign.dto.ActiveLineDemoReportDTO;
import com.ibm.ioes.newdesign.dto.ActiveLineItemReportsDTO;
import com.ibm.ioes.newdesign.dto.AdvancePaymentReportDTO;
import com.ibm.ioes.newdesign.dto.BillingWorkQueueReportDTO;
import com.ibm.ioes.newdesign.dto.CopyCancelReportDTO;
import com.ibm.ioes.newdesign.dto.DisconnectChangeOrdeReportDTO;
import com.ibm.ioes.newdesign.dto.AttributeDetailsReportDTO;
import com.ibm.ioes.newdesign.dto.BillingTriggerDoneButFailedInFXDTO;
import com.ibm.ioes.newdesign.dto.BulkSIUploadReportDto;
import com.ibm.ioes.newdesign.dto.CancelledFailedLineReportDTO;
import com.ibm.ioes.newdesign.dto.DisconnectLineReportDTO;
import com.ibm.ioes.newdesign.dto.DocumentMatrixReportDTO;
import com.ibm.ioes.newdesign.dto.DummyLinesDetailsReportDTO;
import com.ibm.ioes.newdesign.dto.HardwareLineItemCancelReportDTO;
import com.ibm.ioes.newdesign.dto.LempCancelOrderReportDTO;
import com.ibm.ioes.newdesign.dto.LempOrderDetailsReportDTO;
import com.ibm.ioes.newdesign.dto.LempOwnerReportDTO;
import com.ibm.ioes.newdesign.dto.LogicalSIDataReportDTO;
import com.ibm.ioes.newdesign.dto.MigratedApprovedNewOrderDetailReportDTO;
import com.ibm.ioes.newdesign.dto.NonAPP_APPChangeOrderDetailsDTO;
import com.ibm.ioes.newdesign.dto.NonMigratedAPP_UNAPPNewOrderDetailsDTO;
import com.ibm.ioes.newdesign.dto.OBValueReportDTO;
import com.ibm.ioes.newdesign.dto.PendingOrdersAndBillingHardwaresDTO;
import com.ibm.ioes.newdesign.dto.PerformanceReportDTO;
import com.ibm.ioes.newdesign.dto.PerformanceSummaryReportDTO;
import com.ibm.ioes.newdesign.dto.RestPendingLineReportDTO;
import com.ibm.ioes.newdesign.dto.ParallelUpgradeReportDto;
import com.ibm.ioes.newdesign.dto.StartChargeNotPushedInFXDTO;
import com.ibm.ioes.newdesign.dto.M6OrderCancelReportDTO;
import com.ibm.ioes.newdesign.dto.OrderClepReportDTO;
import com.ibm.ioes.newdesign.dto.OrderDetailChangeReportDTO;
import com.ibm.ioes.newdesign.dto.OrderDetailReportDTO;
import com.ibm.ioes.newdesign.dto.OrderReportNewDetailCwnDTO;
import com.ibm.ioes.newdesign.dto.OrderStageReportDTO;
import com.ibm.ioes.newdesign.dto.PendingOrderAndBillingReportDTO;
import com.ibm.ioes.newdesign.dto.PendingServicesReportDTO;
import com.ibm.ioes.newdesign.dto.PerformanceDetailReportDTO;
import com.ibm.ioes.newdesign.dto.RateRenewalReportDTO;
import com.ibm.ioes.newdesign.dto.TelemediaOrderReportDTO;
import com.ibm.ioes.newdesign.dto.ZeroOrderValueReportDTO;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.PagingSorting;
import com.ibm.ioes.utilities.Utility;
//[101010]Rampratap added from date and to date in lepm report and also one parameter added in SP_GET_LEPM_ORDER_CANCEL_REPORT proc
//[202020]Rampratap added from date and to date in lepm report and also one parameter added in SP_GET_LEPM_ORDER_DETAIL_REPORT proc
//[303030]Rampratap added from date and to date in lepm report and also one parameter added in SP_GET_LEPM_OWNER_REPORT proc
//[606060] VIPIN SAHARIA 15-NOV-2103 added cancelMonth, cancelDate to performancesummaryreport and serviceNumber, currency in pending and billing hardware report
//[707070] Gunjan Singla  added verticalDetails, currencyName in report Pending Services Report and customer segment description in CWN report
//[808080] Gunjan Singla  added   method viewReportUsageDetails to insert data into database
//[HYPR22032013001]    Vijay    30-March-2013          Billing Work Queue Report //
//[404040] OppurtunityID column added by lawkush in reports "ACTIVE LINE ITEMS REPORT   ","PERFORMANCE SUMMARY REPORT  ","PERFORMANCE DETAIL REPORT","ORDER DETAIL REPORT","ORDER REPORT NEW DETAIL CWN","LEPM ORDER DETAIL REPORT"
//[007] Shourya Mishra	30-Nov-13	CSR-09463 		Advance Payment / OTC Report
//[009] Rohit Verma		11-Dec-13 CSR-09083			Line Item Dump for Bulk Upload
//[505051] Santosh S.   Added a method to get active line demo reports
//[505052] VIPIN SAHARIA Addition of 14 new columns in ORDER_STAGE_REPORT CBR : 20140424-00-0200pd10
//[909090] Gunjan Singla   added ord_cancel_reason and serv_cancel_reason  in LEPM cancel order report
//[10990] sadananda added service_cancel_date,service_cancel_remarks,service_cancel_reson and service_cancelby  in LEPM cancel order report
//[111] Gunjan Singla  22-Sept-14      CBR_20140704-XX-020224 Global Data Billing Efficiencies Phase1
//[1091]  Sadananda Behera  added new searching filter for Customer Segment
//[1110]  Gunjan Singla    1-Dec-14     Document Matrix Report
//[128]  Raveendra   16-02-2015  20150203-R1-021049  advance payment refund process 
//[129] Pankaj  24-03-2105 added the functionality of OB_YEAR
//[131] Gunjan Singla	4-May-15    20160418-R1-022266  Added Columns 
//[132] Priya Gupta	 17 May-16  
//[133] Priya Gupta 15-Jul-2016	
public class ReportsDao extends CommonBaseDao {

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
	public static String sqlGetNonMigAppUnappNewOrderDetails="{call IOE.SP_GET_NON_MIG_APP_UNAPP_NEW_ORDER_DETAILS(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	public static String sqlGetStartChargeNotPushedInFx="{call IOE.SP_GET_START_CHARGES_NOT_PUSHED_FX(?,?,?,?,?,?,?,?,?,?,?,?)}";
	public static String sqlGetLogicalSIDataReport="{call IOE.SP_GET_LOGICAL_SI_DATA_REPORT(?,?,?,?,?,?,?,?,?,?,?)}";
	public static String sqlGetBillingTriggerDoneButFailedInFX="{call IOE.SP_GET_BILLING_TRIGGER_DONE_BUT_FAILED_IN_FX(?,?,?,?,?,?,?,?,?,?,?,?)}";
	public static String sqlActiveLineItems="{call IOE.SP_GET_ACTIVE_LINE_ITEMS_REPORT(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	public static String sqlOrderReportNew="{call IOE.SP_GET_ORDER_REPORT_NEW(?,?,?,?,?,?,?,?,?,?)}";
	public static String sqlOrderReportChange="{call IOE.SP_GET_ORDER_REPORT_CHANGE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	public static String sqlOrderReportDetail="{call IOE.SP_GET_ORDER_DETAIL_REPORT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	public static String sqlPendingOrderAndBillingReport="{call IOE.SP_GET_PENDING_ORDER_BILLING_REPORT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	public static String sqlPendingOrderBillHardware="{call IOE.SP_GET_PENDING_ORDER_BILLING_HARDWARE_REPORT(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	public static String sqlM6OrderCancelReport="{call IOE.SPGETM6ORDERCANCELREPORT(?,?,?,?,?,?,?)}";
	public static String sqlGetPerformanceDetailReport="{call IOE.SP_GET_PERFORMANCE_DETAIL_REPORT(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	public static String sqlGetTelemediaOrderReport="{call IOE.SP_GET_TELEMEDIAORDER_REPORT(?,?,?,?,?,?,?)}";
	public static String sqlZeroOrdervalueReport="{call IOE.SP_GET_ZERO_ORDER_VALUE_REPORT(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	public static String sqlRateRenewalReport="{call IOE.SP_GET_RATE_RENEWAL_REPORT(?,?,?,?,?,?,?,?,?,?,?,?)}";

	public static String sqlGetRestPendingLineReports="{call IOE.SP_GET_REST_PENDING_LINE_REPORT(?,?,?,?,?,?,?,?,?,?)}";
	public static String sqlGetDisconnectionLineReport="{call IOE.SP_GET_DISCONNECT_LINE_ITEM_REPORT(?,?,?,?,?,?,?,?)}";
	public static String sqlGetCancelledFailedLineReports="{call IOE.SP_GET_CANCELLED_FAILED_LINE_REPORT(?,?,?,?,?,?,?,?)}";
	public static String sqlGetBulkUploadReports="{call IOE.SP_GET_BULK_SI_UPLOAD_REPORT(?,?,?,?,?,?,?,?,?,?,?,?)}";
	public static String sqlGetAttributeDetailsReport="{call IOE.SP_GET_ATTRIBUTE_DETAILS_REPORT(?,?,?,?,?,?,?,?,?)}";
	public static String sqlGetMigAppNewOrderDetails="{call IOE.SP_GET_MIG_APP_NEW_ORDER_DETAILS(?,?,?,?,?,?,?,?)}";
	public static String sqlClepOrderReport="{call IOE.SP_GET_CLEP_ORDER_REPORT(?,?,?)}"; 
	public static String sqlGetLEPMOwnerReport="{call IOE.SP_GET_LEPM_OWNER_REPORT(?,?,?,?,?,?,?,?,?,?)}";
	public static String sqlLEPMOrderCancelReport="{call IOE.SP_GET_LEPM_ORDER_CANCEL_REPORT(?,?,?,?,?,?,?)}";
	public static String sqlLEPMOrderDetailReport="{call IOE.SP_GET_LEPM_ORDER_DETAIL_REPORT(?,?,?,?,?,?,?,?,?,?,?)}";
	public static String SPGETSRORDERREPORT="{call IOE.SP_GET_SR_ORDER_STATUS_REPORT(?,?,?,?,?,?,?,?,?,?)}";
	public static String sqlGetLSIMappingDetails = "{CALL IOE.GETLSIMAPPING(?,?,?,?,?,?,?,?,?,?)}";

	/*Vijay. start add a new procedure for cancelcopy report */
	public static String sqlCancelCopyReport="{call IOE.SP_GET_CANCEL_COPY_REPORT(?,?,?,?,?,?,?)}";
	/*Vijay end*/

	/* Vijay
	 * increase two more parameter (party no & party name) in sqlBillingWorkQueue
	 */
	public static String sqlBillingWorkQueue="{call IOE.SP_GET_BILLING_WORKQUE_REPORT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

	/*Vijay. New proc is define here*/ 
	public static String sqlGetPendingLineReports="{call IOE.SP_GET_PENDING_LINE_REPORT(?,?,?,?,?,?,?,?,?,?)}";  
	/*Vijay end  */
	//[505051] Start
	public static String spGetActiveLineDemoReport = "{call IOE.SP_GET_ACTIVE_LINE_DEMO_REPORT(?, ?, ?, ?, ?, ?, ?, ?, ?) }";
	//[505051] End
	public static String sqlOBValueReport="{call IOE.SP_GET_OBVALUE_REPORT(?,?,?,?,?,?,?,?,?)}";
	public static String sqlGetDummyLineDetailsReport="{call IOE.SP_GET_DUMMY_LINES_DETAILS_REPORT(?,?,?,?,?,?,?,?,?)}";
	//[1110] start
	public static String sqlGetDocumentMatrixReport="{call IOE.SP_GET_DOCUMENT_MATRIX_REPORT(?,?,?,?,?,?,?,?)}";
	//[1110] end
	public static String sqlGetAccesstMatrixReport="{call IOE.SP_GET_ACCESS_MATRIX_REPORT(?,?,?,?,?)}";
	//Varun
	public static String sqlGetDraftReport ="{call IOE.SP_TARCHIVED_DARFT_ORDER_DATA(?,?,?,?,?,?,?,?,?,?,?,?,?)}";  
	public static String sqlGetPDReport ="{call IOE.SP_TARCHIVED_PD_ORDER_DATA(?,?,?,?,?,?,?,?,?,?,?,?,?)}";  
	public static String sqlGetCancelledtReport ="{call IOE.SP_TARCHIVED_CANCELLED_ORDER_DATA(?,?,?,?,?,?,?,?,?,?,?,?,?)}";  
	// VARUN END
	//priya
	public static String sqlGetParallelUpgradeReport="{call IOE.SP_GET_PARALLEL_UPGRADE_REPORT(?,?,?,?,?,?,?,?,?,?,?,?)}";
	
	public ArrayList<BCPAddressDto> viewBCPList(BCPAddressDto objDto)
			throws Exception {
		//Nagarjuna
		String methodName="viewBCPList", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;	
		//end
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
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			//ex.printStackTrace();
			//throw new Exception("SQL Exception : " + ex.getMessage(), ex);
		} finally {
			try {
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(getBCP);
				DbConnection.freeConnection(conn);

			} catch (Exception e) {
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
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
		//end
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
				Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
				//ex.printStackTrace();
				//throw new Exception("SQL Exception : " + ex.getMessage(), ex);
			} finally {
				try {
					DbConnection.closeResultset(rs);
					DbConnection.closeCallableStatement(getOrder);
					DbConnection.freeConnection(conn);
			
				} catch (Exception e) {
					Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
					//e.printStackTrace();
					//throw new Exception("Exception : " + e.getMessage(), e);
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
		//end
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
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			//ex.printStackTrace();	
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
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			}
		}
		return listM6ResponseHistory;
	}	
	
	
	public ArrayList<NetworkLocationDto> viewNetworkLocsList(
			NetworkLocationDto objDto) throws Exception {
		//Nagarjuna
		String methodName="viewNetworkLocsList", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;	
		//end
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
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			//ex.printStackTrace();
			//throw new Exception("SQL Exception : " + ex.getMessage(), ex);
		} finally {
			try {
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(getNetworkLocs);
				DbConnection.freeConnection(conn);

			} catch (Exception e) {
				//e.printStackTrace();
				//throw new Exception("Exception : " + e.getMessage(), e);
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			}
		}
		return objUserList;
	}

	public ArrayList<LocationDetailDto> viewCustomerLocationList(
			LocationDetailDto objDto) throws Exception {
		//Nagarjuna
		String methodName="viewCustomerLocationList", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;	
		//end
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

	public ArrayList<NewOrderDto> viewContactList(NewOrderDto objDto)
			throws Exception {
		//Nagarjuna
		String methodName="viewContactList", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;	
		//end
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

	public ArrayList<DispatchAddressDto> viewDispatchAddressList(
			DispatchAddressDto objDto) throws Exception {
		//Nagarjuna
		String methodName="viewDispatchAddressList", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;	
		//end
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
		//end
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
				//[131] START
				objDto.setPartnerCode(rs.getString("PARTNER_CODE"));
				objDto.setFieldEngineer(rs.getString("FIELD_ENGINEER"));
				objDto.setChannelPartner(rs.getString("PARTNER_NAME"));
				//[131] END

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

	public ArrayList<NewOrderDto> searchOrderStatus(NewOrderDto objDto)
			throws Exception {
		//		Nagarjuna
		String methodName="searchOrderStatus", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;	
		//end
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
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			//ex.printStackTrace();
		} finally {
			try {
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(callstmt);
				DbConnection.freeConnection(connection);
			} catch (SQLException e) {
				//e.printStackTrace();
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			}
		}
		return lstOrderStatusList;
	}

	public ArrayList<ReportsDto> viewMasterHistory(ReportsDto objDto)throws Exception 
	{
		//		Nagarjuna
		String methodName="viewMasterHistory", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;	
		//end
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
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			//ex.printStackTrace();
		} finally {
			try {
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(callstmt);
				DbConnection.freeConnection(connection);
			} catch (SQLException e) {
				//e.printStackTrace();
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			}
		}
		return historyList;
	}
	public ArrayList<OrderStageReportDTO> viewOrderStageList(OrderStageReportDTO objDto)
	throws Exception 
	{
		//		Nagarjuna
		String methodName="viewOrderStageList", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;	
		//end
		ArrayList<OrderStageReportDTO> objUserList = new ArrayList<OrderStageReportDTO>();
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
			
			if (objDto.getFromDate() != null && !"".equals(objDto.getFromDate())) {
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				Date dateStr = formatter.parse(objDto.getFromDate());
				String formattedDate = formatter.format(dateStr);
				Date date1 = formatter.parse(formattedDate);
				formatter = new SimpleDateFormat("yyyy-MM-dd");
				formattedDate = formatter.format(date1);
				proc.setString(2,formattedDate);
			} else {
				proc.setNull(2, java.sql.Types.VARCHAR);
			}
			if (objDto.getToDate() != null && !"".equals(objDto.getToDate())) {
				SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
				Date dateStr1 = formatter1.parse(objDto.getToDate());
				String formattedDate1 = formatter1.format(dateStr1);
				Date date2 = formatter1.parse(formattedDate1);
				formatter1 = new SimpleDateFormat("yyyy-MM-dd");
				formattedDate1 = formatter1.format(date2);
				proc.setString(3,formattedDate1);
			} else {
				proc.setNull(3, java.sql.Types.VARCHAR);
			}
			
			/*if (objDto.getFromDate() != null
					&& !"".equals(objDto.getFromDate())) {
				proc.setString(2, objDto.getFromDate().trim());
			} else {
				proc.setNull(2, java.sql.Types.VARCHAR);
			}
			if (objDto.getToDate() != null && !"".equals(objDto.getToDate())) {
				proc.setString(3, objDto.getToDate().trim());
			} else {
				proc.setNull(3, java.sql.Types.VARCHAR);
			}*/
		
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
				objDto = new OrderStageReportDTO();
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
				//lawkush start
				objDto.setPoReceiveDate(rs.getString("PORECEIVEDATE"));
				if (rs.getString("PORECEIVEDATE") != null && !"".equals(rs.getString("PORECEIVEDATE")))
				{
					objDto.setPoReceiveDate((utility.showDate_Report(new Date(rs.getTimestamp("PORECEIVEDATE").getTime()))).toUpperCase());
				}
				objDto.setPoDate(rs.getString("PODATE"));		
				if (rs.getString("PODATE") != null && !"".equals(rs.getString("PODATE")))
				{
					objDto.setPoDate((utility.showDate_Report(new Date(rs.getTimestamp("PODATE").getTime()))).toUpperCase());
				}
				//lawkush End
				//[505052] START
				objDto.setDemoType(rs.getString("DEMO_TYPE"));
				objDto.setCustPoNumber(rs.getString("CUSTPONUMBER"));
				objDto.setPoContractCnd(rs.getString("PO_CONTRACT_CND"));
				if (rs.getString("PO_CONTRACT_CND") != null && !"".equals(rs.getString("PO_CONTRACT_CND")))
				{
					objDto.setPoContractCnd((utility.showDate_Report(new Date(rs.getTimestamp("PO_CONTRACT_CND").getTime()))).toUpperCase());
				}
				objDto.setProductName(rs.getString("PRODUCTNAME"));
				objDto.setCustomerSegment(rs.getString("CUSTOMER_SEGMENT"));
				objDto.setServiceStatus(rs.getString("SERVICE_STATUS"));
				objDto.setPoDetailNumber(rs.getInt("PO_ID"));
				objDto.setOnnetOffnet(rs.getString("OFFNET_LABELATTVALUE"));
				objDto.setMedia(rs.getString("MEDIA_LABELATTVALUE"));
				objDto.setProjectCategory(rs.getString("ORDERCATGRY_LABELATTVALUE"));
				objDto.setPmRemarks(rs.getString("PM_REMARKS"));
				objDto.setServSubTypeName(rs.getString("SUBPRODUCT_NAME"));
				objDto.setOrderStage(rs.getString("ORDER_STAGE"));
				objDto.setTotalChargeAmount(rs.getString("TOTAL_CHARGE_AMT"));
				//[505052] END
				//[007] Start
				objDto.setLdClause(rs.getString("LDCLAUSE"));
				//[007] End
				// [130] start
				objDto.setSalesForceOpportunityNumber(rs.getString("SF_OPP_ID"));
				objDto.setChannelPartner(rs.getString("PARTNER_NAME"));
				objDto.setPartnerId(rs.getString("PARTNER_ID"));
				// [130] end
				//[131] start
				objDto.setPartnerCode(rs.getString("PARTNER_CODE"));
				objDto.setFieldEngineer(rs.getString("FIELD_ENGINEER"));
				//[131] end
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
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			//ex.printStackTrace();
			//throw new Exception("SQL Exception : " + ex.getMessage(), ex);
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
	public ArrayList<PerformanceReportDTO> viewPerformanceList(PerformanceReportDTO objDto) 
	{
		//		Nagarjuna
		String methodName="viewPerformanceList", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;	
		//end
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rs = null;
		ArrayList<PerformanceReportDTO> listSearchDetails = new ArrayList<PerformanceReportDTO>();
		PerformanceReportDTO objReportsDto = null;
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
				objReportsDto =  new PerformanceReportDTO();
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
				objReportsDto.setGroupName(rs.getString("GROUPNAME"));
				objReportsDto.setPartyNo(rs.getInt("PARTY_NO"));
				//lawkush start
				objReportsDto.setCopcStartDate(rs.getString("COPC_START_DATE"));
				if (rs.getString("COPC_START_DATE") != null && !"".equals(rs.getString("COPC_START_DATE")))
				{
					objReportsDto.setCopcStartDate((utility.showDate_Report(new Date(rs.getTimestamp("COPC_START_DATE").getTime()))).toUpperCase());
				}
				objReportsDto.setCopcEndDate(rs.getString("COPC_END_DATE"));		
				if (rs.getString("COPC_END_DATE") != null && !"".equals(rs.getString("COPC_END_DATE")))
				{
					objReportsDto.setCopcEndDate((utility.showDate_Report(new Date(rs.getTimestamp("COPC_END_DATE").getTime()))).toUpperCase());
				}
				//lawkush End
				//[007] Start
				objReportsDto.setStandardReason(rs.getString("STANDARDREASON"));
				//[007] End

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
	//Ramana
	//Created By Ashutosh
	public ArrayList<PerformanceSummaryReportDTO> viewPerformanceSummaryReport(PerformanceSummaryReportDTO objDto) 
	{
		//		Nagarjuna
		String methodName="viewPerformanceSummaryReport", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;	
		//end
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rs = null;
		ArrayList<PerformanceSummaryReportDTO> listSearchDetails = new ArrayList<PerformanceSummaryReportDTO>();
		PerformanceSummaryReportDTO objReportsDto = null;
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
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				Date dateStr = formatter.parse(objDto.getFromDate());
				String formattedDate = formatter.format(dateStr);
				Date date1 = formatter.parse(formattedDate);
				formatter = new SimpleDateFormat("MM-dd-yyyy");
				formattedDate = formatter.format(date1);
				proc.setString(2,formattedDate);
			} else {
				proc.setNull(2, java.sql.Types.VARCHAR);
			}
			if (objDto.getToDate() != null 
					&& !"".equals(objDto.getToDate())) {
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
				objReportsDto =  new PerformanceSummaryReportDTO();
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
				if(rs.getString("AM_DELAY_REASON") != null){
					objReportsDto.setAmDelayReason(rs.getString("AM_DELAY_REASON"));
				}else{
					objReportsDto.setAmDelayReason("");
				}
				if(rs.getString("COPC_DELAY_REASON") != null){
					objReportsDto.setCopcDelayReason(rs.getString("COPC_DELAY_REASON"));
				}else{
					objReportsDto.setCopcDelayReason("");
				}
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
//				[404040] Start 
				objReportsDto.setOpportunityId((rs.getString("OPPORTUNITYID")));				
				//[404040] End 
				objReportsDto.setGroupName((rs.getString("GROUPNAME")));
				//lawkush start
				objReportsDto.setCopcStartDate(rs.getString("COPC_START_DATE"));
				if (rs.getString("COPC_START_DATE") != null && !"".equals(rs.getString("COPC_START_DATE")))
				{
					objReportsDto.setCopcStartDate((utility.showDate_Report(new Date(rs.getTimestamp("COPC_START_DATE").getTime()))).toUpperCase());
				}
				objReportsDto.setCopcEndDate(rs.getString("COPC_END_DATE"));		
				if (rs.getString("COPC_END_DATE") != null && !"".equals(rs.getString("COPC_END_DATE")))
				{
					objReportsDto.setCopcEndDate((utility.showDate_Report(new Date(rs.getTimestamp("COPC_END_DATE").getTime()))).toUpperCase());
				}
				//lawkush End
				//[606060] Start
				objReportsDto.setCancelDate(rs.getString("CANCEL_DATE"));
				if (rs.getString("CANCEL_DATE") != null && !"".equals(rs.getString("CANCEL_DATE")))
				{
					objReportsDto.setCancelDate((utility.showDate_Report(new Date(rs.getTimestamp("CANCEL_DATE").getTime()))).toUpperCase());
					objReportsDto.setCancelMonth((new SimpleDateFormat("MMM").format(new Date(rs.getTimestamp("CANCEL_DATE").getTime()))).toUpperCase());
				}
				
				// [606060] END
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
	public ArrayList<PendingServicesReportDTO> viewPendingServicesReport(PendingServicesReportDTO objDto) 
	{
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rs = null;
		ArrayList<PendingServicesReportDTO> listSearchDetails = new ArrayList<PendingServicesReportDTO>();
		PendingServicesReportDTO objReportsDto = null;
		int recordCount =0;
		Utility utility=new Utility();
		//		Nagarjuna
		String methodName="viewPendingServicesReport", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;	
		//end
		
		
		
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
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				Date dateStr = formatter.parse(objDto.getFromDate());
				String formattedDate = formatter.format(dateStr);
				Date date1 = formatter.parse(formattedDate);
				formatter = new SimpleDateFormat("MM-dd-yyyy");
				formattedDate = formatter.format(date1);
				proc.setString(2,formattedDate);
			} else {
				proc.setNull(2, java.sql.Types.VARCHAR);
			}
			if (objDto.getToDate() != null 
					&& !"".equals(objDto.getToDate())) {
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
				objReportsDto =  new PendingServicesReportDTO();				
				objReportsDto.setParty_no(rs.getInt("Party_NO"));
				objReportsDto.setPartyName(rs.getString("PARTYNAME"));				
				//vijay replace CRMACCOUNTNO from  ACCOUNTNAME
				objReportsDto.setCrmAccountNoString(rs.getString("ACCOUNTNAME"));
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
				objReportsDto.setVerticalDetails(rs.getString("VERTICAL_DETAILS"));
				objReportsDto.setCurrencyName(rs.getString("CURNAME"));
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
	
	public ArrayList<NonMigratedAPP_UNAPPNewOrderDetailsDTO> viewNonMigAppUnappNewOrderDetails(NonMigratedAPP_UNAPPNewOrderDetailsDTO objDto) 
	{
		//		Nagarjuna
		String methodName="viewNonMigAppUnappNewOrderDetails", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;	
		//end
		
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rs = null;
		ArrayList<NonMigratedAPP_UNAPPNewOrderDetailsDTO> listSearchDetails = new ArrayList<NonMigratedAPP_UNAPPNewOrderDetailsDTO>();
		NonMigratedAPP_UNAPPNewOrderDetailsDTO objReportsDto = null;
		int recordCount =0;
		Utility utility=new Utility();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
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
			if (objDto.getOrderyear() != null
					&& !"".equals(objDto.getOrderyear())) {
				proc.setString(14, objDto.getOrderyear().trim());
			} else {
				proc.setNull(14, java.sql.Types.VARCHAR);
			}
			rs = proc.executeQuery();
			/*while(rs.next())
			{
				objReportsDto =  new ReportsDto();
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
				objReportsDto.setPrimaryLocation(rs.getString("PRIMARYLOCATION"));
				objReportsDto.setSecondaryLocation(rs.getString("SECONDARYLOCATION"));
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
				objReportsDto.setHardwaretypeName(rs.getString("HARDWARETYPENAME"));	
				objReportsDto.setHardwareFlag(rs.getString("HARDWARE_FLAG"));
				objReportsDto.setStorename(rs.getString("STORENAME"));
				objReportsDto.setSaleType(rs.getString("SALETYPE"));//Type Of Sale
				objReportsDto.setBillPeriod(rs.getString("BILL_PERIOD"));
				objReportsDto.setBillingMode(rs.getString("BILLINGMODE"));
				objReportsDto.setPonum(rs.getLong("PONUMBER"));
				objReportsDto.setPoDate(rs.getString("PODATE"));
				if (rs.getString("PODATE") != null && !"".equals(rs.getString("PODATE")))
				{
					
					Date date=df.parse(objReportsDto.getPoDate());
					objReportsDto.setPoDate((utility.showDate_Report(date)).toUpperCase());
					
				}
				objReportsDto.setContractPeriod(rs.getInt("CONTRACTPERIOD"));//Period In Month
				objReportsDto.setTotalPoAmt(rs.getString("TOTALPOAMOUNT"));
				objReportsDto.setPoRecieveDate(rs.getString("PORECEIVEDATE"));
				if (rs.getString("PORECEIVEDATE") != null && !"".equals(rs.getString("PORECEIVEDATE")))
				{
					
					Date date=df.parse(objReportsDto.getPoRecieveDate());
					objReportsDto.setPoRecieveDate((utility.showDate_Report(date)).toUpperCase());
					
				}
				objReportsDto.setCustPoDetailNo(rs.getString("CUSTPONUMBER"));   
				objReportsDto.setCustPoDate(rs.getString("CUSTPODATE"));   
				if (rs.getString("CUSTPODATE") != null && !"".equals(rs.getString("CUSTPODATE")))
				{
					
					Date date=df.parse(objReportsDto.getCustPoDate());
					objReportsDto.setCustPoDate((utility.showDate_Report(date)).toUpperCase());
					
				}
				objReportsDto.setPoAmount(rs.getString("CUST_TOT_PO_AMT"));
				objReportsDto.setChargeTypeName(rs.getString("CHARGE_TYPE"));
				objReportsDto.setChargeName(rs.getString("CHARGE_NAME"));
				objReportsDto.setStartDate(rs.getString("START_DATE"));
				if (rs.getString("START_DATE") != null && !"".equals(rs.getString("START_DATE")))
				{
					
					Date date=df.parse(objReportsDto.getStartDate());
					objReportsDto.setStartDate((utility.showDate_Report(date)).toUpperCase());
					
				}
				objReportsDto.setChargeAmount_String(rs.getString("INV_AMT"));
				objReportsDto.setLineItemAmount(rs.getString("LINEITEMAMOUNT"));//amt
				objReportsDto.setChargeAnnotation(rs.getString("ANNOTATION"));
				objReportsDto.setLocDate(rs.getString("LOCDATE"));
				if (rs.getString("LOCDATE") != null && !"".equals(rs.getString("LOCDATE")))
				{
					
					Date date=df.parse(objReportsDto.getLocDate());
					objReportsDto.setLocDate((utility.showDate_Report(date)).toUpperCase());
					
				}
				objReportsDto.setLOC_No(rs.getString("LOCNO"));  
				objReportsDto.setBillingTriggerDate(rs.getString("BILLINGTRIGGERDATE"));
				if (rs.getString("BILLINGTRIGGERDATE") != null && !"".equals(rs.getString("BILLINGTRIGGERDATE")))
				{
					
					Date date=df.parse(objReportsDto.getBillingTriggerDate());
					objReportsDto.setBillingTriggerDate((utility.showDate_Report(date)).toUpperCase());
					
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
				objReportsDto.setTokenNO(rs.getString("CSTATE_START_DETAILS_FX_TOKEN_NO"));//Token_No
				objReportsDto.setFx_St_Chg_Status(rs.getString("CSTATE_FX_CHARGE_START_STATUS"));//Fx_St_Chg_Status
				objReportsDto.setFxStatus(rs.getString("CSTATE_START_DETAILS_FX_STATUS"));//FX_STATUS
				//Business Serial No	
				//Opms Account Id	
				objReportsDto.setOrderLineNumber(rs.getInt("Order_Line_Id"));//Lineitemnumber	
				//Order Month	
				objReportsDto.setOrderStage(rs.getString("STAGE"));
				if (pagingSorting.isPagingToBeDone()) {
					recordCount = rs.getInt("FULL_REC_COUNT");
				}*/
			
			while(rs.next())
			{
				objReportsDto =  new NonMigratedAPP_UNAPPNewOrderDetailsDTO();
				objReportsDto.setOrderNumber(rs.getInt("ORDERNO"));
				objReportsDto.setOrderType(rs.getString("ORDERTYPE"));
				objReportsDto.setCrmAccountNoString(rs.getString("CRMACCOUNTNO"));
				objReportsDto.setPartyName(rs.getString("PARTYNAME"));
				objReportsDto.setParty_no(rs.getInt("Party_NO"));
				objReportsDto.setVerticalDetails(rs.getString("VERTICAL_DETAILS"));
				objReportsDto.setRegionName(rs.getString("REGION"));   
				objReportsDto.setM6OrderNo2(rs.getString("M6ORDERNO"));
				objReportsDto.setServiceName(rs.getString("SERVICESTAGE"));
				objReportsDto.setCustomer_logicalSINumber(rs.getInt("CUSTOMER_LOGICAL_SI_NO"));
				objReportsDto.setServiceDetDescription(rs.getString("SERVICEDETTYPE"));//LineName
				objReportsDto.setServiceOrderType(rs.getString("SERVICETYPE"));
				objReportsDto.setLogicalCircuitId(rs.getString("CKTID"));
				objReportsDto.setFromLocation(rs.getString("FROM_ADDRESS"));
				objReportsDto.setToLocation(rs.getString("TO_ADDRESS"));
				objReportsDto.setEntity(rs.getString("ENTITYNAME"));//Legal Entity
				objReportsDto.setLcompanyname(rs.getString("LCOMPANYNAME"));
				objReportsDto.setCurrencyName(rs.getString("CURNAME"));
				objReportsDto.setCreditPeriodName(rs.getString("CREDIT_PERIODNAME"));
				objReportsDto.setBillingTypeName(rs.getString("BILLING_TYPENAME"));//Bill Type
				objReportsDto.setBillingFormatName(rs.getString("BILLING_FORMATNAME"));
				objReportsDto.setBillingLevelName(rs.getString("BILLING_LEVELNAME"));
				objReportsDto.setBillingLevelNo(rs.getLong("BILLING_LEVEL_NO"));
				objReportsDto.setTaxation(rs.getString("TAXATIONVALUE"));
				objReportsDto.setHardwaretypeName(rs.getString("HARDWARETYPENAME"));
				objReportsDto.setStorename(rs.getString("STORENAME"));
				objReportsDto.setSaleType(rs.getString("SALETYPENAME"));//Type Of Sale
				objReportsDto.setBillingMode(rs.getString("BILLINGMODE"));
				objReportsDto.setPonum(rs.getLong("PODETAILNUMBER"));
				objReportsDto.setPoDate(rs.getString("PODATE"));
				if(!(rs.getString("PODATE")==null || rs.getString("PODATE")==""))
				{
					
					//Date date=df.parse(objReportsDto.getPoDate());
					objReportsDto.setPoDate((utility.showDate_Report(rs.getString("PODATE"))).toUpperCase());
					
				}
				objReportsDto.setContractPeriod(rs.getInt("CONTRACTPERIOD"));//Period In Month
				objReportsDto.setTotalPoAmt(rs.getString("POAMOUNT"));
				objReportsDto.setPoRecieveDate(rs.getString("PORECEIVEDATE"));
				if(!(rs.getString("PORECEIVEDATE")==null || rs.getString("PORECEIVEDATE")==""))
				{
					
					//Date date=df.parse(objReportsDto.getPoRecieveDate());
					objReportsDto.setPoRecieveDate((utility.showDate_Report(rs.getString("PORECEIVEDATE"))).toUpperCase());
					
				}
				objReportsDto.setCustPoDetailNo(rs.getString("CUSTPONUMBER"));   
				objReportsDto.setCustPoDate(rs.getString("CUSTPODATE")); 
				if(!(rs.getString("CUSTPODATE")==null || rs.getString("CUSTPODATE")==""))
				{
					
					//Date date=df.parse(objReportsDto.getCustPoDate());
					objReportsDto.setCustPoDate((utility.showDate_Report(rs.getString("CUSTPODATE"))).toUpperCase());
					
				}
				objReportsDto.setLOC_No(rs.getString("LOCNO"));
				objReportsDto.setLocDate(rs.getString("LOCDATE"));
				if (rs.getString("LOCDATE") != null
						&& !"".equals(rs.getString("LOCDATE"))) {
					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
					Date dateStr = formatter.parse(rs.getString("LOCDATE"));
					String formattedDate = formatter.format(dateStr);
					Date date1 = formatter.parse(formattedDate);
					formatter = new SimpleDateFormat("dd-MMM-yy");
					formattedDate = formatter.format(date1);
					objReportsDto.setLocDate(formattedDate);
				} 
				objReportsDto.setBillingTriggerDate(rs.getString("BILLINGTRIGGERDATE"));
				if (rs.getString("BILLINGTRIGGERDATE") != null
						&& !"".equals(rs.getString("BILLINGTRIGGERDATE"))) {
					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
					Date dateStr = formatter.parse(rs.getString("BILLINGTRIGGERDATE"));
					String formattedDate = formatter.format(dateStr);
					Date date1 = formatter.parse(formattedDate);
					formatter = new SimpleDateFormat("dd-MMM-yy");
					formattedDate = formatter.format(date1);
					objReportsDto.setBillingTriggerDate(formattedDate);
				}
				objReportsDto.setBillingtrigger_createdate(rs.getString("BILLING_TRIGGER_CREATEDATE"));
				if(!(rs.getString("BILLING_TRIGGER_CREATEDATE")==null || rs.getString("BILLINGTRIGGERDATE")==""))
				{
					
					//Date date=df2.parse(objReportsDto.getBillingtrigger_createdate());
					objReportsDto.setBillingtrigger_createdate((utility.showDate_Report(rs.getString("BILLING_TRIGGER_CREATEDATE"))).toUpperCase());
					
				}
				objReportsDto.setPmApproveDate(rs.getString("LOC_DATE"));
				if (rs.getString("LOC_DATE") != null && !"".equals(rs.getString("LOC_DATE")))
				{
				  String s1=rs.getString("LOC_DATE");
				  String s3=s1.substring(0,7);
				  String s4=s1.substring(9,11);
				  String s5=s3.concat(s4);
				  objReportsDto.setPmApproveDate(s5);
				}
				objReportsDto.setBilling_Trigger_Flag(rs.getString("BILLING_TRIGGER_STATUS"));
				objReportsDto.setBillPeriod(rs.getString("BILL_PERIOD"));
				objReportsDto.setBilling_bandwidth(rs.getString("BILLING_BANDWIDTH"));
				objReportsDto.setBillUom(rs.getString("BILLING_BANDWIDTH_UOM"));
				objReportsDto.setKmsDistance(rs.getString("DISTANCE"));
				objReportsDto.setBandwaidth(rs.getString("BANDWIDTH"));
				objReportsDto.setTokenNO(rs.getString("START_DETAILS_FX_TOKEN_NO"));//Token_No
				objReportsDto.setFx_St_Chg_Status(rs.getString("FX_CHARGE_START_STATUS"));//Fx_St_Chg_Status
				objReportsDto.setFxStatus(rs.getString("START_DETAILS_FX_STATUS"));//FX_STATUS
				objReportsDto.setChargeName(rs.getString("CHARGE_NAME"));
				objReportsDto.setChargeTypeName(rs.getString("CHARGE_TYPE"));
				objReportsDto.setProductName(rs.getString("PRODUCTNAME"));
				objReportsDto.setSubProductName(rs.getString("SUBPRODUCTNAME"));
				//objReportsDto.setChargeAmount_String(rs.getString("INV_AMT"));
				//[707070] Start 
				objReportsDto.setBusiness_serial_n(rs.getInt("BUSINESS_SERIAL_NO"));
				objReportsDto.setOpms_Account_Id(rs.getString("OPMS_ACCOUNT_ID"));
				objReportsDto.setOpms_lineItemNumber(rs.getString("OPMS_LINEITEMNUMBER"));
				// [707070] End
				
				if(rs.getString("INV_AMT") != null && !"".equals(rs.getString("INV_AMT"))){
					 objReportsDto.setChargeAmount_String(rs.getString("INV_AMT"));	
					//Double d2=Double.parseDouble(rs.getString("INV_AMT"));				
					//objReportsDto.setChargeAmount_Double((Math.round(d2 * 100.0) / 100.0));		
				}
				else{
					objReportsDto.setChargeAmount_Double(0.0);		
				}
				objReportsDto.setLineItemAmount(rs.getString("LINEITEMAMOUNT"));//amt
				objReportsDto.setStartDate(rs.getString("START_DATE"));
				
				if (rs.getString("START_DATE") != null
						&& !"".equals(rs.getString("START_DATE"))) {
					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
					Date dateStr = formatter.parse(rs.getString("START_DATE"));
					String formattedDate = formatter.format(dateStr);
					Date date1 = formatter.parse(formattedDate);
					formatter = new SimpleDateFormat("dd-MMM-yy");
					formattedDate = formatter.format(date1);
					objReportsDto.setStartDate(formattedDate);
				} else {
					objReportsDto.setStartDate("");
				}
				objReportsDto.setChargeAnnotation(rs.getString("ANNOTATION"));
				objReportsDto.setOrderLineNumber(rs.getInt("LINENUMBER"));//Lineitemnumber	
				objReportsDto.setOrdermonth(rs.getString("ORDERMONTH"));
				/*objReportsDto.setBlSource(rs.getString("BL_SOURCE"));
				objReportsDto.setBusiness_serial_no(rs.getString("BUSINESS_SERIAL_NO"));
				objReportsDto.setOpms_Account_Id(rs.getString("OPMS_ACCOUNT_ID"));*/
				objReportsDto.setRatio(rs.getString("RATIO"));
				objReportsDto.setHardwareFlag(rs.getString("HARDWARE_FLAG"));
				if (rs.getString("PRIMARYLOCATION") != null && !"".equals(rs.getString("PRIMARYLOCATION")))
				{
					if(Integer.parseInt(rs.getString("PRIMARYLOCATIONTYPE"))==1){
						String ss[] =rs.getString("PRIMARYLOCATION").split("~~");
						objReportsDto.setPrimaryLocation(ss[0]+" "+ss[1]+" "+ss[4]+" "+ss[5]+" "+ss[6]+" "+ss[7]+" "+ss[8]+" "+ss[9]+" "+ss[10]+" "+ss[2]);
					}
					else{
						String ss[] =rs.getString("PRIMARYLOCATION").split("~~");
						objReportsDto.setPrimaryLocation(ss[0]+" "+ss[1]+" "+ss[5]+" "+ss[3]);
					}
				}
				else
				{
					objReportsDto.setPrimaryLocation("");
				}
				if (rs.getString("SECONDARYLOCATION") != null && !"".equals(rs.getString("SECONDARYLOCATION")))
				{
					if(Integer.parseInt(rs.getString("SECONDARYLOCATIONTYPE"))==1){
						String ss[] =rs.getString("SECONDARYLOCATION").split("~~");
						objReportsDto.setSecondaryLocation(ss[0]+" "+ss[1]+" "+ss[4]+" "+ss[5]+" "+ss[6]+" "+ss[7]+" "+ss[8]+" "+ss[9]+" "+ss[10]+" "+ss[2]);
					}
					else{
						String ss[] =rs.getString("SECONDARYLOCATION").split("~~");
						objReportsDto.setSecondaryLocation(ss[0]+" "+ss[1]+" "+ss[5]+" "+ss[3]);
					}
				}
				else
				{
					objReportsDto.setSecondaryLocation("");
				}
				
				objReportsDto.setOrderStage(rs.getString("STAGE"));
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
	public ArrayList<StartChargeNotPushedInFXDTO> viewStartChargeNotPushedInFx(StartChargeNotPushedInFXDTO objDto) 
	{
		//		Nagarjuna
		String methodName="viewStartChargeNotPushedInFx", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;	
		//end
		
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rs = null;
		ArrayList<StartChargeNotPushedInFXDTO> listSearchDetails = new ArrayList<StartChargeNotPushedInFXDTO>();
		StartChargeNotPushedInFXDTO objReportsDto = null;
		int recordCount =0;
		Utility utility=new Utility();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try{
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
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				Date dateStr = formatter.parse(objDto.getFromDate());
				String formattedDate = formatter.format(dateStr);
				Date date1 = formatter.parse(formattedDate);
				formatter = new SimpleDateFormat("MM-dd-yyyy");
				formattedDate = formatter.format(date1);
				proc.setString(2,formattedDate);
			} else {
				proc.setNull(2, java.sql.Types.VARCHAR);
			}
			if (objDto.getToDate() != null 
					&& !"".equals(objDto.getToDate())) {
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
			// index
			rs = proc.executeQuery();
			while(rs.next())
			{
				objReportsDto =  new StartChargeNotPushedInFXDTO();
				objReportsDto.setLogicalSINumber(rs.getInt("LOGICAL_SI_NO"));
				objReportsDto.setCustomer_logicalSINumber(rs.getInt("CUSTOMER_LOGICAL_SI_NO"));
				objReportsDto.setServiceName(rs.getString("SERVICENAME"));
				objReportsDto.setServiceDetDescription(rs.getString("SERVICEDETDESCRIPTION"));
				objReportsDto.setChargeTypeName(rs.getString("CHARGE_TYPE"));
				objReportsDto.setFrequencyName(rs.getString("FREQUENCY"));
				objReportsDto.setChargeName(rs.getString("CHARGE_NAME"));
				objReportsDto.setBillPeriod(rs.getString("BILL_PERIOD"));
				//objReportsDto.setDnd_Dispatch_But_Not_Delivered(rs.getString("Dnd_Dispatch_But_Not_Delivered"));
				//objReportsDto.setDnd_Dispatch_And_Delivered(rs.getString("Dnd_Dispatch_And_Delivered"));
				//objReportsDto.setDnd_Disp_Del_Not_Installed(rs.getString("Ddni_Disp_Del_Not_Installed"));
				//objReportsDto.setDnd_Disp_Delivered_Installed(rs.getString("Ddni_Disp_Delivered_Installed"));				
				objReportsDto.setStartDateLogic(rs.getString("STARTDATELOGIC"));
				objReportsDto.setEndDateLogic(rs.getString("ENDDATELOGIC"));
				objReportsDto.setStartDate(rs.getString("START_DATE"));
				if (rs.getString("START_DATE") != null && !"".equals(rs.getString("START_DATE")))
				{
					
					objReportsDto.setStartDate((utility.showDate_Report(new Date(rs.getTimestamp("START_DATE").getTime()))).toUpperCase());
					
				}
				objReportsDto.setEndDate(rs.getString("END_DATE"));
				if (rs.getString("END_DATE") != null && !"".equals(rs.getString("END_DATE")))
				{
					
					objReportsDto.setEndDate((utility.showDate_Report(new Date(rs.getTimestamp("END_DATE").getTime()))).toUpperCase());
					
				}
				objReportsDto.setContractStartDate(rs.getString("CONTRACTSTARTDATE"));
				if (rs.getString("CONTRACTSTARTDATE") != null && !"".equals(rs.getString("CONTRACTSTARTDATE")))
				{
					
					objReportsDto.setContractStartDate((utility.showDate_Report(new Date(rs.getTimestamp("CONTRACTSTARTDATE").getTime()))).toUpperCase());
					
				}
				objReportsDto.setContractEndDate(rs.getString("CONTRACTENDDATE"));
				if (rs.getString("CONTRACTENDDATE") != null && !"".equals(rs.getString("CONTRACTENDDATE")))
				{
					
					objReportsDto.setContractEndDate((utility.showDate_Report(new Date(rs.getTimestamp("CONTRACTENDDATE").getTime()))).toUpperCase());
					
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
				objReportsDto.setDispatchAddressName(rs.getString("DISPATCHADDNAME"));
				objReportsDto.setPenaltyClause(rs.getString("PENALTYCLAUSE"));
				objReportsDto.setBillingLevel(rs.getString("BILLINGLEVEL"));
				objReportsDto.setBillingLevelNo1(rs.getString("BILLING_LEVEL_NO"));
				objReportsDto.setBillingLevelName(rs.getString("BILLING_LEVELNAME"));
				objReportsDto.setHardwaretypeName(rs.getString("HARDWARETYPENAME"));	
				objReportsDto.setStore(rs.getString("STORENAME"));
				objReportsDto.setFormAvailable(rs.getString("FORM_C_AVAILABLE"));
				objReportsDto.setSaleNature(rs.getString("SALENATURE"));
				objReportsDto.setSaleType(rs.getString("SALETYPE"));
				objReportsDto.setPrimaryLocation(rs.getString("PRIMARYLOCATION"));
				objReportsDto.setSecondaryLocation(rs.getString("SECONDARYLOCATION"));
				objReportsDto.setPonum1(rs.getString("PONUMBER"));
				
				objReportsDto.setPoDate(rs.getString("PODATE"));
				if (rs.getString("PODATE") != null && !"".equals(rs.getString("PODATE")))
				{
					
					objReportsDto.setPoDate((utility.showDate_Report(new Date(rs.getTimestamp("PODATE").getTime()))).toUpperCase());
					
				}
				objReportsDto.setParty_no(rs.getInt("Party_NO"));
				objReportsDto.setPartyName(rs.getString("PARTYNAME"));
				objReportsDto.setChargeAnnotation(rs.getString("ANNOTATION"));
				objReportsDto.setFxStatus(rs.getString("CSTATE_START_DETAILS_FX_STATUS"));//FX_STATUS
				objReportsDto.setFx_St_Chg_Status(rs.getString("CSTATE_FX_CHARGE_START_STATUS"));//Fx_St_Chg_Status
				objReportsDto.setFx_Ed_Chg_Status(rs.getString("CSTATE_FX_CHARGE_END_STATUS"));//Fx_Ed_Chg_Status
				objReportsDto.setTokenNO(rs.getString("CSTATE_START_DETAILS_FX_TOKEN_NO"));//Token_No
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
					objReportsDto.setLocDate((utility.showDate_Report(date)).toUpperCase());
					
				}
				objReportsDto.setBillingTriggerDate(rs.getString("BILLINGTRIGGERDATE"));
				if (rs.getString("BILLINGTRIGGERDATE") != null && !"".equals(rs.getString("BILLINGTRIGGERDATE")))
				{
					
					Date date=df.parse(objReportsDto.getBillingTriggerDate());
					objReportsDto.setBillingTriggerDate((utility.showDate_Report(date)).toUpperCase());
					
				}
				
				objReportsDto.setBillingtrigger_createdate(rs.getString("BILLING_TRIGGER_CREATEDATE"));
				if (rs.getString("BILLING_TRIGGER_CREATEDATE") != null && !"".equals(rs.getString("BILLING_TRIGGER_CREATEDATE")))
				{
					
					objReportsDto.setBillingtrigger_createdate((utility.showDate_Report(new Date(rs.getTimestamp("BILLING_TRIGGER_CREATEDATE").getTime()))).toUpperCase());
					
				}
				objReportsDto.setChallenno(rs.getString("CHALLEN_NO"));
				objReportsDto.setChallendate(rs.getString("CHALLEN_DATE"));
				if (rs.getString("CHALLEN_DATE") != null && !"".equals(rs.getString("CHALLEN_DATE")))
				{
				  String s1=rs.getString("CHALLEN_DATE");
				   if(s1.length()==10){
					  s1="0"+s1;
				   }
				  String s3=s1.substring(0,7).toUpperCase();
				  String s4=s1.substring(9,11);
				  String s5=s3.concat(s4);
				  objReportsDto.setChallendate(s5);
				}
				objReportsDto.setFx_external_acc_id(rs.getString("Child_Account_Number"));
				objReportsDto.setChildAccountFXSataus(rs.getString("Child_Account_FX_Sataus"));
				objReportsDto.setWarrantyStartDate(rs.getString("WARRENTY_START_DATE"));
				if (rs.getString("WARRENTY_START_DATE") != null && !"".equals(rs.getString("WARRENTY_START_DATE")))
				{
					objReportsDto.setWarrantyStartDate((utility.showDate_Report(new Date(rs.getTimestamp("WARRENTY_START_DATE").getTime()))).toUpperCase());
				}
				objReportsDto.setWarrantyEndDate(rs.getString("WARRENTY_END_DATE"));
				if (rs.getString("WARRENTY_END_DATE") != null && !"".equals(rs.getString("WARRENTY_END_DATE")))
				{
					objReportsDto.setWarrantyEndDate((utility.showDate_Report(new Date(rs.getTimestamp("WARRENTY_END_DATE").getTime()))).toUpperCase());
					
				}
				objReportsDto.setExtSuportEndDate(rs.getString("EXT_SUPPORT_END_DATE"));   
				if (rs.getString("EXT_SUPPORT_END_DATE") != null && !"".equals(rs.getString("EXT_SUPPORT_END_DATE")))
				{
					objReportsDto.setExtSuportEndDate((utility.showDate_Report(new Date(rs.getTimestamp("EXT_SUPPORT_END_DATE").getTime()))).toUpperCase());
				}
				objReportsDto.setOrderDate(rs.getString("ORDERDATE"));
				if (rs.getString("ORDERDATE") != null && !"".equals(rs.getString("ORDERDATE")))
				{
					objReportsDto.setOrderDate((utility.showDate_Report(new Date(rs.getTimestamp("ORDERDATE").getTime()))).toUpperCase());
				}
				
				objReportsDto.setCopcApproveDate(rs.getString("ORDER_APPROVED_DATE"));//Copc date
				if (rs.getString("ORDER_APPROVED_DATE") != null && !"".equals(rs.getString("ORDER_APPROVED_DATE")))
				{
					objReportsDto.setCopcApproveDate((utility.showDate_Report(new Date(rs.getTimestamp("ORDER_APPROVED_DATE").getTime()))).toUpperCase());
				}
				objReportsDto.setOrderType(rs.getString("ORDERTYPE"));
				
				//   --Order Type Id
				objReportsDto.setOrdersubtype(rs.getString("ORDERSUBTYPE"));
				objReportsDto.setServiceStageDescription(rs.getString("SERVICE_ORDER_TYPE_DESC"));
				//     "TST3"."TASK_END_DATE" as "COPC_APPROVED_DATE",      
				//    --TPOSERVICEDETAILS.BILLINGTRIGGERDATE as BILLINGTRIGGER_CREATE_DATE, 
				//    --Cust Logical Si ( Duplicate column)
				objReportsDto.setHardwareFlag(rs.getString("HARDWARE_FLAG"));
				objReportsDto.setChargeTypeID(rs.getInt("CHARGESTYPE"));
				objReportsDto.setServiceStage(rs.getString("SERVICE_STAGE"));
				objReportsDto.setOrderStage(rs.getString("STAGE"));
				objReportsDto.setAccountManager(rs.getString("ACCOUNTMANAGER"));
				objReportsDto.setProjectManager(rs.getString("PROJECTMANAGER"));
				// --"TPOMASTER"."ORDERDATE" ORDERCREATION DATE
				objReportsDto.setRfsDate(rs.getString("SERVICE_RFS_DATE"));  
				if (rs.getString("SERVICE_RFS_DATE") != null && !"".equals(rs.getString("SERVICE_RFS_DATE")))
				{
					objReportsDto.setRfsDate((utility.showDate_Report(new Date(rs.getTimestamp("SERVICE_RFS_DATE").getTime()))).toUpperCase());
				}
				objReportsDto.setPoRecieveDate(rs.getString("PORECEIVEDATE"));
				if (rs.getString("PORECEIVEDATE") != null && !"".equals(rs.getString("PORECEIVEDATE")))
				{
					
					objReportsDto.setPoRecieveDate((utility.showDate_Report(new Date(rs.getTimestamp("PORECEIVEDATE").getTime()))).toUpperCase());
					
				}
				objReportsDto.setTokenNoEd(rs.getString("CSTATE_END_DETAILS_FX_TOKEN_NO"));//--Token No Ed
			    //--Fx Status Ed       
				objReportsDto.setCustPoDetailNo(rs.getString("CUSTPONUMBER"));   
				objReportsDto.setCustPoDate(rs.getString("CUSTPODATE"));  
				if (rs.getString("CUSTPODATE") != null && !"".equals(rs.getString("CUSTPODATE")))
				{
					
					//Date date=df.parse(objReportsDto.getCustPoDate());
					objReportsDto.setCustPoDate((utility.showDate_Report(new Date(rs.getTimestamp("CUSTPODATE").getTime()))).toUpperCase());
					
				}
				objReportsDto.setCreatedDate(rs.getString("CREATEDDATE"));  
				if (rs.getString("CREATEDDATE") != null && !"".equals(rs.getString("CREATEDDATE")))
				{
					
					//Date date=df.parse(objReportsDto.getCustPoDate());
					objReportsDto.setCreatedDate((utility.showDate_Report(new Date(rs.getTimestamp("CREATEDDATE").getTime()))).toUpperCase());
					
				}
				
				objReportsDto.setCharge_status(rs.getString("CHARGES_STATUS"));   
				objReportsDto.setLOC_No(rs.getString("LOCNO"));   
				objReportsDto.setBillingAddress(rs.getString("BILLING_ADDRESS"));       
				objReportsDto.setFxSiId(rs.getString("FX_SI_ID"));  
				objReportsDto.setCancelBy(rs.getString("CANCEL_BY"));
				objReportsDto.setCanceldate(rs.getString("CANCEL_DATE"));
				objReportsDto.setCancelReason(rs.getString("CANCEL_REASON"));

				objReportsDto.setRegionName(rs.getString("REGION"));      
				objReportsDto.setBandwaidth(rs.getString("BANDWIDTH"));     
				objReportsDto.setVerticalDetails(rs.getString("VERTICAL_DETAILS"));      
				objReportsDto.setToLocation(rs.getString("TO_ADDRESS"));     
				objReportsDto.setFromLocation(rs.getString("FROM_ADDRESS"));
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
				objReportsDto.setChargeAmount_String(rs.getString("INV_AMT"));
				objReportsDto.setLineItemAmount(rs.getString("LINEITEMAMOUNT"));
				objReportsDto.setTotalPoAmt(rs.getString("TOTALPOAMOUNT")); 				
				//    --Installment Rate
				objReportsDto.setStartDateDays(rs.getInt("START_DATE_DAYS"));
				objReportsDto.setStartDateMonth(rs.getInt("START_DATE_MONTH"));
				objReportsDto.setEndDateDays(rs.getInt("END_DATE_DAYS"));
				objReportsDto.setEndDateMonth(rs.getInt("END_DATE_MONTH"));
				objReportsDto.setAnnual_rate(rs.getDouble("ANNUAL_RATE"));
				
				//--Trai Rate
				//--Discount
				objReportsDto.setContractPeriod(rs.getInt("CONTRACTPERIOD"));
				objReportsDto.setCommitmentPeriod(rs.getInt("COMMITMENTPERIOD"));  
				objReportsDto.setNoticePeriod(rs.getInt("NOTICEPERIOD"));  
				objReportsDto.setCharge_hdr_id(rs.getInt("CHARGE_HDR_ID"));  
				objReportsDto.setChargeInfoID(rs.getInt("CHARGEINFOID"));  
				//--Principal Amt
				//   --Intrest Rate
				//   --Period In Month
				objReportsDto.setPoAmount(rs.getString("CUST_TOT_PO_AMT"));
				objReportsDto.setParty_id(rs.getInt("PARTY_ID"));
				//   --Cust Account id
				objReportsDto.setM6_prod_id(rs.getString("M6_PRODUCT_ID"));
				objReportsDto.setM6_order_id(rs.getString("M6ORDERNO"));
				objReportsDto.setAccountID(rs.getInt("ACCOUNTID"));
				objReportsDto.setStart_fx_no(rs.getString("START_DETAILS_FX_NO"));
				objReportsDto.setEnd_fx_no(rs.getString("END_DETAILS_FX_NO"));
				
				
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
		//		Nagarjuna
		String methodName="viewLogicalSIDataReport", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;	
		//end
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rs = null;
		ArrayList<LogicalSIDataReportDTO> listSearchDetails = new ArrayList<LogicalSIDataReportDTO>();
		LogicalSIDataReportDTO objReportsDto = null;
		int recordCount =0;
		Utility utility=new Utility();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
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
			// index
			rs = proc.executeQuery();
			while(rs.next())
			{
				objReportsDto =  new LogicalSIDataReportDTO();
				objReportsDto.setOrderNumber(rs.getInt("ORDERNO"));
				objReportsDto.setRecordStatus(rs.getString("recordStatus"));
				objReportsDto.setLogicalSINumber(rs.getInt("LOGICAL_SI_NO"));				
				objReportsDto.setServiceName(rs.getString("SERVICENAME"));
				objReportsDto.setParent_name(rs.getString("PARENT_NAME"));//PARENT LINE NAME
				objReportsDto.setServiceDetDescription(rs.getString("SERVICEDETDESCRIPTION"));//Line Name
				objReportsDto.setChargeTypeName(rs.getString("CHARGE_TYPE"));
				objReportsDto.setChargeName(rs.getString("CHARGE_NAME"));
				objReportsDto.setCharge_status(rs.getString("CHARGES_STATUS"));
				objReportsDto.setContractPeriod(rs.getInt("CONTRACTPERIOD"));
				objReportsDto.setTotalPoAmt(rs.getString("TOTALPOAMOUNT")); 
				objReportsDto.setFrequencyName(rs.getString("FREQUENCY"));
				objReportsDto.setFrequencyAmt(rs.getString("FREQUENCY_AMT"));
				objReportsDto.setStartDateLogic(rs.getString("STARTDATELOGIC"));
				objReportsDto.setStartDateDays(rs.getInt("START_DATE_DAYS"));
				objReportsDto.setStartDateMonth(rs.getInt("START_DATE_MONTH"));
				objReportsDto.setBillingTriggerDate(rs.getString("BILLINGTRIGGERDATE"));
				if (rs.getString("BILLINGTRIGGERDATE") != null && !"".equals(rs.getString("BILLINGTRIGGERDATE")))
				{
					
					Date date=df.parse(objReportsDto.getBillingTriggerDate());
					objReportsDto.setBillingTriggerDate((utility.showDate_Report(date)).toUpperCase());
					
				}
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
				objReportsDto.setCustPoDetailNo(rs.getString("CUSTPONUMBER")); 
				objReportsDto.setPoAmount(rs.getString("CUST_TOT_PO_AMT"));
				objReportsDto.setEntity(rs.getString("ENTITYNAME"));
				objReportsDto.setCustPoDate(rs.getString("CUSTPODATE")); 
				if (rs.getString("CUSTPODATE") != null && !"".equals(rs.getString("CUSTPODATE")))
				{
					
					Date date=df.parse(objReportsDto.getCustPoDate());
					objReportsDto.setCustPoDate((utility.showDate_Report(date)).toUpperCase());
					
				}
				objReportsDto.setContractPeriod(rs.getInt("CONTRACTPERIOD"));//PO Contract Period
				objReportsDto.setContractStartDate(rs.getString("CONTRACTSTARTDATE"));
				if (rs.getString("CONTRACTSTARTDATE") != null && !"".equals(rs.getString("CONTRACTSTARTDATE")))
				{
					
					Date date=df.parse(objReportsDto.getContractStartDate());
					objReportsDto.setContractStartDate((utility.showDate_Report(date)).toUpperCase());
					
				}
				objReportsDto.setContractEndDate(rs.getString("CONTRACTENDDATE"));
				if (rs.getString("CONTRACTENDDATE") != null && !"".equals(rs.getString("CONTRACTENDDATE")))
				{
					
					Date date=df.parse(objReportsDto.getContractEndDate());
					objReportsDto.setContractEndDate((utility.showDate_Report(date)).toUpperCase());
					
				}
				objReportsDto.setPoRecieveDate(rs.getString("PORECEIVEDATE"));
				if (rs.getString("PORECEIVEDATE") != null && !"".equals(rs.getString("PORECEIVEDATE")))
				{
					
					Date date=df.parse(objReportsDto.getPoRecieveDate());
					objReportsDto.setPoRecieveDate((utility.showDate_Report(date)).toUpperCase());
					
				}
				objReportsDto.setChargeinfoID(rs.getString("CHARGEINFOID"));//need to add in view
				objReportsDto.setOrderLineNumber(rs.getInt("Order_Line_Id"));
				objReportsDto.setServiceId(rs.getInt("SERVICE_NO"));
				//Pk Charges Id	
				//M6 Product Id	
				//Parent Product Id	
				objReportsDto.setBillingInfoID(rs.getInt("CHARGE_HDR_ID"));//Charge Hdr Id	
				//Ib Pk Charges Id	
				//Ib Order Line Id	
				//M6 Order Id	
				//Order Line Si No	
				objReportsDto.setChargeAnnotation(rs.getString("ANNOTATION"));
				objReportsDto.setRemarks(rs.getString("REMARKS"));
				objReportsDto.setCreditPeriodName(rs.getString("CREDITPERIOD"));
				objReportsDto.setBillingTypeName(rs.getString("BILLING_TYPENAME"));
				objReportsDto.setBillingFormatName(rs.getString("BILLING_FORMATNAME"));
				objReportsDto.setTaxation(rs.getString("TAXATIONVALUE"));
				objReportsDto.setLcompanyname(rs.getString("LCOMPANYNAME"));
				String billAddress = rs.getString("BILLING_ADDRESS");
				String[] billAddressArray = billAddress.split("~~ ",14);
				objReportsDto.setCountyName(billAddressArray[8]);// rs.getString("COUNTRY_NAME")
				objReportsDto.setAddress1(billAddressArray[2]);//billing Address1
				objReportsDto.setAddress2(billAddressArray[3]);//billing Address2	
				objReportsDto.setAddress3(billAddressArray[4]);//billing Address3	
				objReportsDto.setAddress4(billAddressArray[5]);//billing Address4
				objReportsDto.setCityName(billAddressArray[6]);//need to add in view - rs.getString("CITY_NAME")
				objReportsDto.setPostalCode(billAddressArray[9]);//need to add in view -rs.getString("POSTAL_CODE")
				objReportsDto.setStateName(billAddressArray[7]);//need to add in view - rs.getString("STATE_NAME")
				//Active End Date
				objReportsDto.setContactName(billAddressArray[0]+" "+billAddressArray[1]);//Contact Person Name - rs.getString("BILL_CON_PER_NAME")	
				objReportsDto.setDesignation(billAddressArray[13]);//Person Designation	- rs.getString("DESIGNATION")
				objReportsDto.setTelePhoneNo(billAddressArray[10]);//Person Mobile	- rs.getString("TELEPHONENO")
				objReportsDto.setEmailId(billAddressArray[12]);//Person Email	- rs.getString("EMAIL_ID")
				objReportsDto.setFax(billAddressArray[11]);//Person Fax	- rs.getString("FAX")
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
				//Remrks	
				objReportsDto.setBillingLevel(rs.getString("BILLING_LEVELNAME"));
				objReportsDto.setCommitmentPeriod(rs.getInt("COMMITMENTPERIOD"));  
				objReportsDto.setNoticePeriod(rs.getInt("NOTICEPERIOD")); 
				objReportsDto.setLogicalCircuitId(rs.getString("CKTID"));
				
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
		//		Nagarjuna
		String methodName="viewBillingTriggerDoneButFailedInFX", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;	
		//end
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rs = null;
		ArrayList<BillingTriggerDoneButFailedInFXDTO> listSearchDetails = new ArrayList<BillingTriggerDoneButFailedInFXDTO>();
		BillingTriggerDoneButFailedInFXDTO objReportsDto = null;
		int recordCount =0;
		Utility utility=new Utility();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try{
			connection=DbConnection.getReportsConnectionObject();
			proc= connection.prepareCall(sqlGetBillingTriggerDoneButFailedInFX);
			
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
			} else {
				proc.setNull(2, java.sql.Types.VARCHAR);
			}
			if (objDto.getToDate() != null 
					&& !"".equals(objDto.getToDate())) {
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
			// index
			rs = proc.executeQuery();
			while(rs.next())
			{
				objReportsDto =  new BillingTriggerDoneButFailedInFXDTO();
				objReportsDto.setLogicalSINumber(rs.getInt("LOGICAL_SI_NO"));
				objReportsDto.setCustomer_logicalSINumber(rs.getInt("CUSTOMER_LOGICAL_SI_NO"));
				objReportsDto.setServiceName(rs.getString("SERVICENAME"));
				objReportsDto.setServiceDetDescription(rs.getString("SERVICEDETDESCRIPTION"));
				objReportsDto.setChargeTypeName(rs.getString("CHARGE_TYPE"));
				objReportsDto.setFrequencyName(rs.getString("FREQUENCY"));
				objReportsDto.setChargeName(rs.getString("CHARGE_NAME"));
				objReportsDto.setBillPeriod(rs.getString("BILL_PERIOD"));
				//objReportsDto.setDnd_Dispatch_But_Not_Delivered(rs.getString("Dnd_Dispatch_But_Not_Delivered"));
				//objReportsDto.setDnd_Dispatch_And_Delivered(rs.getString("Dnd_Dispatch_And_Delivered"));
				//objReportsDto.setDnd_Disp_Del_Not_Installed(rs.getString("Ddni_Disp_Del_Not_Installed"));
				//objReportsDto.setDnd_Disp_Delivered_Installed(rs.getString("Ddni_Disp_Delivered_Installed"));				
				objReportsDto.setStartDateLogic(rs.getString("STARTDATELOGIC"));
				objReportsDto.setEndDateLogic(rs.getString("ENDDATELOGIC"));
				objReportsDto.setStartDate(rs.getString("START_DATE"));
				if (rs.getString("START_DATE") != null && !"".equals(rs.getString("START_DATE")))
				{
					
					objReportsDto.setStartDate((utility.showDate_Report(new Date(rs.getTimestamp("START_DATE").getTime()))).toUpperCase());
					
				}
				objReportsDto.setEndDate(rs.getString("END_DATE"));
				if (rs.getString("END_DATE") != null && !"".equals(rs.getString("END_DATE")))
				{
					
					objReportsDto.setEndDate((utility.showDate_Report(new Date(rs.getTimestamp("END_DATE").getTime()))).toUpperCase());
					
				}
				objReportsDto.setContractStartDate(rs.getString("CONTRACTSTARTDATE"));
				if (rs.getString("CONTRACTSTARTDATE") != null && !"".equals(rs.getString("CONTRACTSTARTDATE")))
				{
					
					objReportsDto.setContractStartDate((utility.showDate_Report(new Date(rs.getTimestamp("CONTRACTSTARTDATE").getTime()))).toUpperCase());
					
				}
				objReportsDto.setContractEndDate(rs.getString("CONTRACTENDDATE"));
				if (rs.getString("CONTRACTENDDATE") != null && !"".equals(rs.getString("CONTRACTENDDATE")))
				{
					
					objReportsDto.setContractEndDate((utility.showDate_Report(new Date(rs.getTimestamp("CONTRACTENDDATE").getTime()))).toUpperCase());
					
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
				objReportsDto.setDispatchAddressName(rs.getString("DISPATCHADDNAME"));
				objReportsDto.setPenaltyClause(rs.getString("PENALTYCLAUSE"));
				objReportsDto.setBillingLevel(rs.getString("BILLINGLEVEL"));
				objReportsDto.setBillingLevelNo1(rs.getString("BILLING_LEVEL_NO"));
				objReportsDto.setBillingLevelName(rs.getString("BILLING_LEVELNAME"));
				objReportsDto.setHardwaretypeName(rs.getString("HARDWARETYPENAME"));	
				objReportsDto.setStore(rs.getString("STORENAME"));
				objReportsDto.setFormAvailable(rs.getString("FORM_C_AVAILABLE"));
				objReportsDto.setSaleNature(rs.getString("SALENATURE"));
				objReportsDto.setSaleType(rs.getString("SALETYPE"));
				objReportsDto.setPrimaryLocation(rs.getString("PRIMARYLOCATION"));
				objReportsDto.setSecondaryLocation(rs.getString("SECONDARYLOCATION"));
				objReportsDto.setPonum1(rs.getString("PONUMBER"));
				
				objReportsDto.setPoDate(rs.getString("PODATE"));
				if (rs.getString("PODATE") != null && !"".equals(rs.getString("PODATE")))
				{
					
					objReportsDto.setPoDate((utility.showDate_Report(new Date(rs.getTimestamp("PODATE").getTime()))).toUpperCase());
					
				}
				objReportsDto.setParty_no(rs.getInt("Party_NO"));
				objReportsDto.setPartyName(rs.getString("PARTYNAME"));
				objReportsDto.setChargeAnnotation(rs.getString("ANNOTATION"));
				objReportsDto.setFxStatus(rs.getString("CSTATE_START_DETAILS_FX_STATUS"));//FX_STATUS
				objReportsDto.setFx_St_Chg_Status(rs.getString("CSTATE_FX_CHARGE_START_STATUS"));//Fx_St_Chg_Status
				objReportsDto.setFx_Ed_Chg_Status(rs.getString("CSTATE_FX_CHARGE_END_STATUS"));//Fx_Ed_Chg_Status
				objReportsDto.setTokenNO(rs.getString("CSTATE_START_DETAILS_FX_TOKEN_NO"));//Token_No
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
					objReportsDto.setLocDate((utility.showDate_Report(date)).toUpperCase());
					
				}
				objReportsDto.setBillingTriggerDate(rs.getString("BILLINGTRIGGERDATE"));
				if (rs.getString("BILLINGTRIGGERDATE") != null && !"".equals(rs.getString("BILLINGTRIGGERDATE")))
				{
					
					Date date=df.parse(objReportsDto.getBillingTriggerDate());
					objReportsDto.setBillingTriggerDate((utility.showDate_Report(date)).toUpperCase());
					
				}
				objReportsDto.setBillingtrigger_createdate(rs.getString("BILLING_TRIGGER_CREATEDATE"));
				if (rs.getString("BILLING_TRIGGER_CREATEDATE") != null && !"".equals(rs.getString("BILLING_TRIGGER_CREATEDATE")))
				{
					
					objReportsDto.setBillingtrigger_createdate((utility.showDate_Report(new Date(rs.getTimestamp("BILLING_TRIGGER_CREATEDATE").getTime()))).toUpperCase());
					
				}
				objReportsDto.setChallenno(rs.getString("CHALLEN_NO"));
				objReportsDto.setChallendate(rs.getString("CHALLEN_DATE"));
				if (rs.getString("CHALLEN_DATE") != null && !"".equals(rs.getString("CHALLEN_DATE")))
				{
				  String s1=rs.getString("CHALLEN_DATE");
				   if(s1.length()==10){
					  s1="0"+s1;
				   }
				  String s3=s1.substring(0,7).toUpperCase();
				  String s4=s1.substring(9,11);
				  String s5=s3.concat(s4);
				  objReportsDto.setChallendate(s5);
				}
				objReportsDto.setFx_external_acc_id(rs.getString("Child_Account_Number"));
				objReportsDto.setChildAccountFXSataus(rs.getString("Child_Account_FX_Sataus"));
				objReportsDto.setWarrantyStartDate(rs.getString("WARRENTY_START_DATE"));
				if (rs.getString("WARRENTY_START_DATE") != null && !"".equals(rs.getString("WARRENTY_START_DATE")))
				{
					objReportsDto.setWarrantyStartDate((utility.showDate_Report(new Date(rs.getTimestamp("WARRENTY_START_DATE").getTime()))).toUpperCase());
				}
				objReportsDto.setWarrantyEndDate(rs.getString("WARRENTY_END_DATE"));
				if (rs.getString("WARRENTY_END_DATE") != null && !"".equals(rs.getString("WARRENTY_END_DATE")))
				{
					objReportsDto.setWarrantyEndDate((utility.showDate_Report(new Date(rs.getTimestamp("WARRENTY_END_DATE").getTime()))).toUpperCase());
					
				}
				objReportsDto.setExtSuportEndDate(rs.getString("EXT_SUPPORT_END_DATE"));   
				if (rs.getString("EXT_SUPPORT_END_DATE") != null && !"".equals(rs.getString("EXT_SUPPORT_END_DATE")))
				{
					objReportsDto.setExtSuportEndDate((utility.showDate_Report(new Date(rs.getTimestamp("EXT_SUPPORT_END_DATE").getTime()))).toUpperCase());
				}
				objReportsDto.setOrderDate(rs.getString("ORDERDATE"));
				if (rs.getString("ORDERDATE") != null && !"".equals(rs.getString("ORDERDATE")))
				{
					objReportsDto.setOrderDate((utility.showDate_Report(new Date(rs.getTimestamp("ORDERDATE").getTime()))).toUpperCase());
				}
				
				objReportsDto.setCopcApproveDate(rs.getString("ORDER_APPROVED_DATE"));//Copc date
				if (rs.getString("ORDER_APPROVED_DATE") != null && !"".equals(rs.getString("ORDER_APPROVED_DATE")))
				{
					objReportsDto.setCopcApproveDate((utility.showDate_Report(new Date(rs.getTimestamp("ORDER_APPROVED_DATE").getTime()))).toUpperCase());
				}
				objReportsDto.setOrderType(rs.getString("ORDERTYPE"));
				
				//   --Order Type Id
				objReportsDto.setOrdersubtype(rs.getString("ORDERSUBTYPE"));
				objReportsDto.setServiceStageDescription(rs.getString("SERVICE_ORDER_TYPE_DESC"));
				//     "TST3"."TASK_END_DATE" as "COPC_APPROVED_DATE",      
				//    --TPOSERVICEDETAILS.BILLINGTRIGGERDATE as BILLINGTRIGGER_CREATE_DATE, 
				//    --Cust Logical Si ( Duplicate column)
				objReportsDto.setHardwareFlag(rs.getString("HARDWARE_FLAG"));
				objReportsDto.setChargeTypeID(rs.getInt("CHARGESTYPE"));
				objReportsDto.setServiceStage(rs.getString("SERVICE_STAGE"));
				objReportsDto.setOrderStage(rs.getString("STAGE"));
				objReportsDto.setAccountManager(rs.getString("ACCOUNTMANAGER"));
				objReportsDto.setProjectManager(rs.getString("PROJECTMANAGER"));
				// --"TPOMASTER"."ORDERDATE" ORDERCREATION DATE
				objReportsDto.setRfsDate(rs.getString("SERVICE_RFS_DATE"));  
				if (rs.getString("SERVICE_RFS_DATE") != null && !"".equals(rs.getString("SERVICE_RFS_DATE")))
				{
					objReportsDto.setRfsDate((utility.showDate_Report(new Date(rs.getTimestamp("SERVICE_RFS_DATE").getTime()))).toUpperCase());
				}
				objReportsDto.setPoRecieveDate(rs.getString("PORECEIVEDATE"));
				if (rs.getString("PORECEIVEDATE") != null && !"".equals(rs.getString("PORECEIVEDATE")))
				{
					
					objReportsDto.setPoRecieveDate((utility.showDate_Report(new Date(rs.getTimestamp("PORECEIVEDATE").getTime()))).toUpperCase());
					
				}
				objReportsDto.setTokenNoEd(rs.getString("CSTATE_END_DETAILS_FX_TOKEN_NO"));//--Token No Ed
			    //--Fx Status Ed       
				objReportsDto.setCustPoDetailNo(rs.getString("CUSTPONUMBER"));   
				objReportsDto.setCustPoDate(rs.getString("CUSTPODATE"));  
				if (rs.getString("CUSTPODATE") != null && !"".equals(rs.getString("CUSTPODATE")))
				{
					
					//Date date=df.parse(objReportsDto.getCustPoDate());
					objReportsDto.setCustPoDate((utility.showDate_Report(new Date(rs.getTimestamp("CUSTPODATE").getTime()))).toUpperCase());
					
				}
				objReportsDto.setCreatedDate(rs.getString("CREATEDDATE"));  
				if (rs.getString("CREATEDDATE") != null && !"".equals(rs.getString("CREATEDDATE")))
				{
					
					//Date date=df.parse(objReportsDto.getCustPoDate());
					objReportsDto.setCreatedDate((utility.showDate_Report(new Date(rs.getTimestamp("CREATEDDATE").getTime()))).toUpperCase());
					
				}
				
				objReportsDto.setCharge_status(rs.getString("CHARGES_STATUS"));   
				objReportsDto.setLOC_No(rs.getString("LOCNO"));   
				objReportsDto.setBillingAddress(rs.getString("BILLING_ADDRESS"));       
				objReportsDto.setFxSiId(rs.getString("FX_SI_ID"));  
				objReportsDto.setCancelBy(rs.getString("CANCEL_BY"));
				objReportsDto.setCanceldate(rs.getString("CANCEL_DATE"));
				objReportsDto.setCancelReason(rs.getString("CANCEL_REASON"));

				objReportsDto.setRegionName(rs.getString("REGION"));      
				objReportsDto.setBandwaidth(rs.getString("BANDWIDTH"));     
				objReportsDto.setVerticalDetails(rs.getString("VERTICAL_DETAILS"));      
				objReportsDto.setToLocation(rs.getString("TO_ADDRESS"));     
				objReportsDto.setFromLocation(rs.getString("FROM_ADDRESS"));
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
				objReportsDto.setChargeAmount_String(rs.getString("INV_AMT"));
				objReportsDto.setLineItemAmount(rs.getString("LINEITEMAMOUNT"));
				objReportsDto.setTotalPoAmt(rs.getString("TOTALPOAMOUNT")); 				
				//    --Installment Rate
				objReportsDto.setStartDateDays(rs.getInt("START_DATE_DAYS"));
				objReportsDto.setStartDateMonth(rs.getInt("START_DATE_MONTH"));
				objReportsDto.setEndDateDays(rs.getInt("END_DATE_DAYS"));
				objReportsDto.setEndDateMonth(rs.getInt("END_DATE_MONTH"));
				objReportsDto.setAnnual_rate(rs.getDouble("ANNUAL_RATE"));
				
				//--Trai Rate
				//--Discount
				objReportsDto.setContractPeriod(rs.getInt("CONTRACTPERIOD"));
				objReportsDto.setCommitmentPeriod(rs.getInt("COMMITMENTPERIOD"));  
				objReportsDto.setNoticePeriod(rs.getInt("NOTICEPERIOD"));  
				objReportsDto.setCharge_hdr_id(rs.getInt("CHARGE_HDR_ID"));  
				objReportsDto.setChargeInfoID(rs.getInt("CHARGEINFOID"));  
				//--Principal Amt
				//   --Intrest Rate
				//   --Period In Month
				objReportsDto.setPoAmount(rs.getString("CUST_TOT_PO_AMT"));
				objReportsDto.setParty_id(rs.getInt("PARTY_ID"));
				//   --Cust Account id
				objReportsDto.setM6_prod_id(rs.getString("M6_PRODUCT_ID"));
				objReportsDto.setM6_order_id(rs.getString("M6ORDERNO"));
				objReportsDto.setAccountID(rs.getInt("ACCOUNTID"));
				objReportsDto.setStart_fx_no(rs.getString("START_DETAILS_FX_NO"));
				objReportsDto.setEnd_fx_no(rs.getString("END_DETAILS_FX_NO"));
				
				
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

	//	Nagarjuna
	String methodName="viewActiveLineItemsList", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;	
	//end
		ArrayList<ActiveLineItemReportsDTO> objUserList = new ArrayList<ActiveLineItemReportsDTO>();
		Connection conn = null;
		CallableStatement proc = null;
		ResultSet rs = null;
		int recordCount = 0;
		Utility utility=new Utility();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try {

			conn = DbConnection.getReportsConnectionObject();

			proc = conn.prepareCall(sqlActiveLineItems);
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
			PagingSorting pagingSorting = objDto.getPagingSorting();
			pagingSorting.sync();// To calculate start index and Enc Index

			proc.setString(7, pagingSorting.getSortByColumn());// columnName
			proc.setString(8, PagingSorting.DB_Asc_Desc(pagingSorting
					.getSortByOrder()));// sort order
			proc.setInt(9, pagingSorting.getStartRecordId());// start index
			proc.setInt(10, pagingSorting.getEndRecordId());// end index
			proc.setInt(11, (pagingSorting.isPagingToBeDone() ? 1 : 0));// end
			// index
			if (objDto.getPartyNo() != 0 
					&& !"".equals(objDto.getPartyNo())) {
				proc.setInt(12, objDto.getPartyNo());
			} else {
				proc.setNull(12,java.sql.Types.BIGINT);
			}
			System.out.println("Pankaj CustSeg"+objDto.getCustomerSegment());
			if (objDto.getCustomerSegment() != null 
					&& !"".equals(objDto.getCustomerSegment()) && !objDto.getCustomerSegment().equals("0")) {
				proc.setString(13, objDto.getCustomerSegment());
			} else {
				proc.setNull(13,java.sql.Types.VARCHAR);
			}
			System.out.println("Pankaj productname"+objDto.getProductName());
			if (objDto.getProductName() != null
					&& !"".equals(objDto.getProductName()) && !objDto.getProductName().equals("0")) {
						proc.setString(14, objDto.getProductName());
					} else {
						proc.setNull(14,java.sql.Types.VARCHAR);
					}

			rs = proc.executeQuery();
			int countFlag = 0;
			ActiveLineItemReportsDTO objdto ;
			while (rs.next() != false) {
				countFlag++;

				objdto = new ActiveLineItemReportsDTO();
				objdto.setLogicalSINo(rs.getString("LOGICAL_SI_NO"));
				objdto.setCustSINo(rs.getString("CUSTOMER_LOGICAL_SI_NO"));
				objdto.setServiceName(rs.getString("SERVICENAME"));
				objdto.setLinename(rs.getString("LINENAME"));
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
				objdto.setAccountID(rs.getInt("ACCOUNTID"));
				objdto.setCreditPeriodName(rs.getString("CREDIT_PERIODNAME"));
				objdto.setCurrencyName(rs.getString("CURNAME"));
				objdto.setEntity(rs.getString("ENTITYNAME"));
				objdto.setBillingMode(rs.getString("BILLINGMODE"));
				objdto.setBillingTypeName(rs.getString("BILLING_TYPENAME"));
				objdto.setBillingformat(rs.getString("BILLING_FORMATNAME"));
				objdto.setLicCompanyName(rs.getString("LCOMPANYNAME"));
				objdto.setTaxation(rs.getString("TAXATIONVALUE"));
				objdto.setBillingLevelNo(rs.getLong("BILLING_LEVEL_NO"));
				objdto.setBillingLevelName(rs.getString("BILLING_LEVELNAME"));
				objdto.setStore(rs.getString("STORENAME"));
				objdto.setHardwaretypeName(rs.getString("HARDWARETYPENAME"));
				objdto.setFormAvailable(rs.getString("FORM_C_AVAILABLE"));
				objdto.setSaleNature(rs.getString("SALENATURENAME"));
				objdto.setSaleTypeName(rs.getString("SALETYPENAME"));
				objdto.setPrimaryLocation(rs.getString("PRIMARYLOCATION"));
				objdto.setSeclocation(rs.getString("SECONDARYLOCATION"));
				objdto.setPoNumber(rs.getInt("PODETAILNUMBER"));
				objdto.setPoDate(rs.getString("PODATE"));
				if (rs.getString("PODATE") != null && !"".equals(rs.getString("PODATE")))
				{
					
					Date date=df.parse(objdto.getPoDate());
					objdto.setPoDate((utility.showDate_Report(date)).toUpperCase());
				}
				
				objdto.setParty_num(rs.getString("PARTY_NO"));
				objdto.setChargeAnnotation(rs.getString("ANNOTATION"));
				objdto.setFx_sd_charge_status(rs.getString("FX_SD_CHG_SATATUS"));
				objdto.setFx_charge_status(rs.getString("FX_STATUS"));
				objdto.setFx_Ed_Chg_Status(rs.getString("FX_ED_CHARGE_STATUS"));
				//Vijay start
				//objdto.setTokenID(rs.getInt("TOKEN_ID"));
				objdto.setToken_ID(rs.getString("TOKEN_ID"));
				//vijay end
				objdto.setModifiedDate(rs.getString("LAST_UPDATE_DATE"));
				if (rs.getString("LAST_UPDATE_DATE") != null && !"".equals(rs.getString("LAST_UPDATE_DATE")))
				{
					objdto.setModifiedDate((utility.showDate_Report(new Date(rs.getTimestamp("LAST_UPDATE_DATE").getTime()))).toUpperCase());
				}
				objdto.setBillingTriggerFlag(rs.getString("BILLING_TRIGGER_FLAG"));
				objdto.setPm_pro_date(rs.getString("PM_PROVISIONING_DATE"));
				if (rs.getString("PM_PROVISIONING_DATE") != null && !"".equals(rs.getString("PM_PROVISIONING_DATE")))
				{
					
					  String s1=rs.getString("PM_PROVISIONING_DATE");
					  String s3=s1.substring(0,7).toUpperCase();
					  String s4=s1.substring(9,11);
					  String s5=s3.concat(s4);
					  objdto.setPm_pro_date(s5);
				}
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
				objdto.setChallenno(rs.getString("CHALLEN_NO"));
				objdto.setChallendate(rs.getString("CHALLEN_DATE"));
				
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
				
				objdto.setFx_external_acc_id(rs.getString("FX_ACCOUNT_EXTERNAL_ID"));
				objdto.setChild_account_creation_status(rs.getString("Child_account_FX_sataus"));
				objdto.setOrderDate(rs.getString("ORDERDATE"));
				if (rs.getString("ORDERDATE") != null && !"".equals(rs.getString("ORDERDATE")))
				{
					
					Date date=df.parse(objdto.getOrderDate());
					objdto.setOrderDate((utility.showDate_Report(date)).toUpperCase());
					
				}
				
				objdto.setPmapprovaldate(rs.getString("PM_APPROVAL_DATE"));
				if (rs.getString("PM_APPROVAL_DATE") != null && !"".equals(rs.getString("PM_APPROVAL_DATE")))
				{
					objdto.setPmapprovaldate((utility.showDate_Report5(new Date(rs.getTimestamp("PM_APPROVAL_DATE").getTime()))).toUpperCase());
				}
				
				objdto.setCopcapprovaldate(rs.getString("APPROVAL_DATE"));
				if (rs.getString("APPROVAL_DATE") != null && !"".equals(rs.getString("APPROVAL_DATE")))
				{
					objdto.setCopcapprovaldate((utility.showDate_Report5(new Date(rs.getTimestamp("APPROVAL_DATE").getTime()))).toUpperCase());
				}
				objdto.setOrderType(rs.getString("ORDERTYPE"));
				
				objdto.setBillingtrigger_createdate(rs.getString("BILLING_TRIGGER_CREATEDATE"));
				if (rs.getString("BILLING_TRIGGER_CREATEDATE") != null && !"".equals(rs.getString("BILLING_TRIGGER_CREATEDATE")))
				{
					
					objdto.setBillingtrigger_createdate((utility.showDate_Report(new Date(rs.getTimestamp("BILLING_TRIGGER_CREATEDATE").getTime()))).toUpperCase());
					
				}
				objdto.setRatio(rs.getString("RATIO"));
				objdto.setProductName(rs.getString("PRODUCTNAME"));
				objdto.setSubProductName(rs.getString("SUBPRODUCTNAME"));
				objdto.setHardwareType(rs.getString("HARDWARE_FLAG"));
				objdto.setServiceStage(rs.getString("SERVICE_STAGE"));
				objdto.setOrderStage(rs.getString("STAGE"));
				objdto.setRfsDate(rs.getString("RFS_DATE"));
				if (rs.getString("RFS_DATE") != null && !"".equals(rs.getString("RFS_DATE")))
				{
					
					objdto.setRfsDate((utility.showDate_Report(new Date(rs.getTimestamp("RFS_DATE").getTime()))).toUpperCase());
					
				}
				
				objdto.setPoReceiveDate(rs.getString("PORECEIVEDATE"));
				if (rs.getString("PORECEIVEDATE") != null && !"".equals(rs.getString("PORECEIVEDATE")))
				{
					
					Date date=df.parse(objdto.getPoReceiveDate());
					objdto.setPoReceiveDate((utility.showDate_Report(date)).toUpperCase());
					
				}
				objdto.setCustPoDetailNo(rs.getString("CUSTPONUMBER"));
				objdto.setCustPoDate(rs.getString("CUST_PODATE"));
				if (rs.getString("CUST_PODATE") != null && !"".equals(rs.getString("CUST_PODATE")))
				{
					
					Date date=df.parse(objdto.getCustPoDate());
					objdto.setCustPoDate((utility.showDate_Report(date)).toUpperCase());
					
				}
				objdto.setCharge_status(rs.getString("CHARGES_STATUS"));
				objdto.setLOC_No(rs.getString("LOCNO"));
				objdto.setAddress1(rs.getString("ADDRESS"));
				objdto.setM6cktid(rs.getString("CKTID"));
				objdto.setOrdersubtype(rs.getString("ORDERSUBTYPE"));
				objdto.setRegion(rs.getString("REGIONNAME"));
				objdto.setBandwaidth(rs.getString("BANDWIDTH"));     
				objdto.setVertical(rs.getString("VERTICAL_DETAILS"));
				objdto.setAccountManager(rs.getString("ACCOUNTMANAGER"));
				objdto.setProjectManager(rs.getString("PROJECTMANAGER"));
				objdto.setRate_code(rs.getString("RATECODE"));
				objdto.setLmRemarks(rs.getString("LM_REMARKS"));
				objdto.setLmMedia(rs.getString("LM_MEDIA"));
//				last
				objdto.setChargeable_Distance(rs.getString("CHARGEABLE_DISTANCE"));
//				link
				objdto.setDispatchAddress1(rs.getString("DISPATCH_DETAILS"));
				objdto.setIndicative_value(rs.getString("INDICATIVE_VALUE"));
				// productname
				objdto.setPartyName(rs.getString("PARTYNAME"));
				objdto.setBilling_location_from(rs.getString("BILLING_LOCATION"));
				objdto.setDemo(rs.getString("DEMO_ORDER"));
				objdto.setCrm_productname(rs.getString("CRM_PRODUCTNAME"));
				objdto.setToLocation(rs.getString("FROM_LOCATION"));
				objdto.setFromLocation(rs.getString("TO_LOCATION"));
				objdto.setBilling_bandwidth(rs.getString("BILLING_BANDWIDTH"));
				objdto.setBilling_uom(rs.getString("BILLING_BANDWIDTH_UOM"));
				objdto.setBlSource(rs.getString("BL_SOURCE"));
				objdto.setServiceproductid(rs.getInt("ORDER_LINE_ID"));
				objdto.setOrderNumber(rs.getInt("ORDERNO"));
				objdto.setChargeAmount(rs.getDouble("INV_AMT"));
				objdto.setLineamt(rs.getDouble("LINEITEMAMOUNT"));
				objdto.setChargesSumOfLineitem(rs.getDouble("TOTAL_CHARGE_AMT"));
				objdto.setContractPeriod(rs.getInt("CONTRACTPERIOD"));
				objdto.setTotalPoAmt(""+rs.getDouble("TOTAL_POAMOUNT"));
				objdto.setParty_id(rs.getInt("PARTY_ID"));
				objdto.setCrmAccountNoString(rs.getString("CRMACCOUNTNO"));
				//m6 productid
				objdto.setM6OrderNo(rs.getInt("M6ORDERID"));
				objdto.setCust_total_poamt(rs.getDouble("CUST_TOT_PO_AMT"));
				//m6 order,business
			     objdto.setPk_charge_id(rs.getString("PK_CHARGE_ID"));//Added by Ashutosh as on 26-Jun-12
			     objdto.setContractPeriod(rs.getInt("CONTRACTPERIOD"));
			     objdto.setAnnual_rate(rs.getDouble("ANNUAL_RATE"));
				objdto.setServiceId(rs.getInt("SERVICEID"));
				objdto.setPoExpiryDate(rs.getString("PO_EXPIRY_DATE"));			
				if (rs.getString("PO_EXPIRY_DATE") != null && !"".equals(rs.getString("PO_EXPIRY_DATE")))
				{
					objdto.setPoExpiryDate((utility.showDate_Report(objdto.getPoExpiryDate())).toUpperCase());
				}
				
				//[404040] Start 
				objdto.setOpportunityId((rs.getString("OPPORTUNITYID")));				
				//[404040] End 
				objdto.setGroupName((rs.getString("GROUPNAME")));	
				//[505050] Start 
				objdto.setBusiness_serial_n(rs.getInt("BUSINESS_SERIAL_NO"));
				objdto.setMocn_no(rs.getString("MOCN_NUMBER"));
				objdto.setOpms_Account_Id(rs.getString("OPMS_ACCOUNT_ID"));
				objdto.setOldLsi(rs.getString("OLD_LSI_CRM"));
				objdto.setOpms_lineItemNumber(rs.getString("OPMS_LINEITEMNUMBER"));
				
				//[505050] End
				/*Vijay add few more columns*/
				objdto.setAsiteAdd1(rs.getString("ASITE_ADD1"));
				objdto.setAsiteAdd2(rs.getString("ASITE_ADD2"));
				objdto.setAsiteAdd3(rs.getString("ASITE_ADD3"));
				objdto.setBsiteName(rs.getString("BSITE_NAME"));
				objdto.setBsiteLineAdd1(rs.getString("BSITE_ADD1"));
				objdto.setBsiteLineAdd2(rs.getString("BSITE_ADD2"));
				//vijay end
				//[111] start
				objdto.setMainSpid(rs.getLong("MAIN_SERVICEPRODUCTID"));
				objdto.setTaxExemptReasonName(rs.getString("REASONNAME"));
				//[111] end
				//[505054] Start
				objdto.setServiceSegment(rs.getString("SERVICESEGMENT"));
				//[505054] End
				
				//[004] Start
				objdto.setInstallationFromCity(rs.getString("INSTALLATION_FROM_CITY"));
				objdto.setInstallationToCity(rs.getString("INSTALLATION_TO_CITY"));
				objdto.setInstallationFromState(rs.getString("INSTALLATION_FROM_STATE"));
				objdto.setInstallationToState(rs.getString("INSTALLATION_TO_STATE"));
				objdto.setBillingContactName(rs.getString("BILLING_CONTACT_NAME"));
				objdto.setBillingContactNumber(rs.getString("BILLING_CONTACT_NUMBER"));
				objdto.setBillingEmailId(rs.getString("BILLING_EMAIL_ID"));
				
				//[004] End
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
				//[007] Start
				objdto.setStandardReason(rs.getString("STANDARDREASON"));
				objdto.setLdClause(rs.getString("LDCLAUSE"));
				//[007] End
				
				//[008] Start
				objdto.setOrderCreationDate(rs.getString("CREATED_DATE"));
				if (rs.getString("CREATED_DATE") != null && !"".equals(rs.getString("CREATED_DATE")))
				{
					objdto.setOrderCreationDate((utility.showDate_Report5(new Date(rs.getTimestamp("CREATED_DATE").getTime()))).toUpperCase());
				}
				objdto.setPublishedDate(rs.getString("PUBLISHED_DATE"));
				if (rs.getString("PUBLISHED_DATE") != null && !"".equals(rs.getString("PUBLISHED_DATE")))
				{
					objdto.setPublishedDate((utility.showDate_Report5(new Date(rs.getTimestamp("PUBLISHED_DATE").getTime()))).toUpperCase());
				}
			
				//[008] End
				//[130] start
				objdto.setSalesForceOpportunityNumber(rs.getString("SF_OPP_ID"));
				objdto.setChannelPartner(rs.getString("PARTNER_NAME"));
				objdto.setNetworkType(rs.getString("NETWORK_SERVICE_TYPE"));
				objdto.setPartnerId(rs.getString("PARTNER_ID"));
				//[130] End
				//[131] start
				objdto.setPartnerCode(rs.getString("PARTNER_CODE"));
				objdto.setFieldEngineer(rs.getString("FIELD_ENGINEER"));
				//[131] end
				// nancy start
			    objdto.setePCNNo(rs.getString("EPCN_NO"));
				//nancy end
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

/**
 * 
 * @param objDto
 * @return
 * @throws Exception
 */
public ArrayList<OrderReportNewDetailCwnDTO> viewOrderReportNew(OrderReportNewDetailCwnDTO objDto) throws Exception {

	//	Nagarjuna
	String methodName="viewOrderReportNew", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;	
	//end	
	ArrayList<OrderReportNewDetailCwnDTO> objUserList = new ArrayList<OrderReportNewDetailCwnDTO>();
		Connection conn = null;
		CallableStatement proc = null;
		ResultSet rs = null;
		int recordCount = 0;
		OrderReportNewDetailCwnDTO objRDto;
		Utility utility=new Utility();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

		try {

			conn = DbConnection.getReportsConnectionObject();

		proc = conn.prepareCall(sqlOrderReportNew);
		
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
			if (objDto.getOsp() != null && !"".equals(objDto.getOsp())) {
				proc.setString(10, objDto.getOsp().trim());
			} else {
				proc.setNull(10, java.sql.Types.VARCHAR);
			}
			rs = proc.executeQuery();
			int countFlag = 0;
			while (rs.next() != false) {
				countFlag++;

			objDto = new OrderReportNewDetailCwnDTO();
			objDto.setPartyName(rs.getString("PARTYNAME"));
			objDto.setOrderNo(rs.getString("ORDERNO"));
			objDto.setCopcApproveDate(rs.getString("COPC_APPROVAL_DATE"));
		   if (rs.getString("COPC_APPROVAL_DATE") != null && !"".equals(rs.getString("COPC_APPROVAL_DATE")))
			{
				objDto.setCopcApproveDate((utility.showDate_Report(new Date(rs.getTimestamp("COPC_APPROVAL_DATE").getTime()))).toUpperCase());
			}
			objDto.setLogicalCircuitId(rs.getString("LOGICALCKTID"));
			objDto.setServiceId(rs.getInt("SERVICENO"));
			objDto.setQuoteNo(rs.getString("QUOTENO"));
			objDto.setProductName(rs.getString("PRODUCTNAME"));
			objDto.setSubProductName(rs.getString("SUBPRODUCTNAME"));
			if (rs.getString("FROM_SITE") != null && !"".equals(rs.getString("FROM_SITE")) && rs.getString("PRIMARYLOCATIONTYPE")!= null)
			{
				if(Integer.parseInt(rs.getString("PRIMARYLOCATIONTYPE"))==1){
					String ss[] =rs.getString("FROM_SITE").split("~~");
					objDto.setPrimarylocation(ss[0]+" "+ss[1]+" "+ss[4]+" "+ss[5]+" "+ss[6]+" "+ss[7]+" "+ss[8]+" "+ss[9]+" "+ss[10]+" "+ss[2]);
				}
				else{
					String ss[] =rs.getString("FROM_SITE").split("~~");
					objDto.setPrimarylocation(ss[0]+" "+ss[1]+" "+ss[5]+" "+ss[3]);
				}
				
				//objDto.setPrimarylocation(rs.getString("FROM_SITE"));
			}
			else
			{
				//String ss[] =("A ~~B ~~C ~~D ~~E ~~F ~~G ~~H ~~I").split("~~");
				//objDto.setPrimarylocation(ss[0]+" "+ss[1]+" "+ss[4]+" "+ss[5]+" "+ss[6]+" "+ss[7]+" "+ss[8]+" "+ss[9]+" "+ss[10]+" "+ss[2]);
				objDto.setPrimarylocation("");
			}
			if (rs.getString("TO_SITE") != null && !"".equals(rs.getString("TO_SITE")) && rs.getString("SECONDARYLOCATIONTYPE")!= null)
			{
				if(Integer.parseInt(rs.getString("SECONDARYLOCATIONTYPE"))==1){
					String ss[] =rs.getString("TO_SITE").split("~~");
					objDto.setSeclocation(ss[0]+" "+ss[1]+" "+ss[4]+" "+ss[5]+" "+ss[6]+" "+ss[7]+" "+ss[8]+" "+ss[9]+" "+ss[10]+" "+ss[2]);
				}
				else{
					String ss[] =rs.getString("TO_SITE").split("~~");
					objDto.setSeclocation(ss[0]+" "+ss[1]+" "+ss[5]+" "+ss[3]);
				}
			//objDto.setSeclocation(rs.getString("TO_SITE"));
				}
			else
			{
				objDto.setSeclocation("");
			}
			//objDto.setPrimarylocation(rs.getString("FROM_SITE"));
			//objDto.setSeclocation(rs.getString("TO_SITE"));
			objDto.setOrdersubtype(rs.getString("ORDERSUBTYPE"));
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
				
				objDto.setCustPoDate((utility.showDate_Report(new Date(rs.getTimestamp("CUSTPODATE").getTime()))).toUpperCase());
				
			}
			objDto.setCustPoDetailNo(rs.getString("CUSTPONUMBER"));
			objDto.setOrderDate(rs.getString("ORDERDATE"));
			if (rs.getString("ORDERDATE") != null && !"".equals(rs.getString("ORDERDATE")))
			{
				
				objDto.setOrderDate((utility.showDate_Report(new Date(rs.getTimestamp("ORDERDATE").getTime()))).toUpperCase());
				
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
			objDto.setNio_approve_date(rs.getString("NIO_APPROVAL_DATE"));
			if (rs.getString("NIO_APPROVAL_DATE") != null && !"".equals(rs.getString("NIO_APPROVAL_DATE")))
			{
				objDto.setNio_approve_date((utility.showDate_Report(new Date(rs.getTimestamp("NIO_APPROVAL_DATE").getTime()))).toUpperCase());
			}
			objDto.setDemo_infrastartdate(rs.getString("DEMP_INFRA_START_DATE"));
			objDto.setDemo_infra_enddate(rs.getString("DEMO_INFRA_ENDDATE"));
			objDto.setRfs_date(rs.getString("RFS_DATE"));
			if (rs.getString("RFS_DATE") != null && !"".equals(rs.getString("RFS_DATE")))
			{
				
				objDto.setRfs_date((utility.showDate_Report(new Date(rs.getTimestamp("RFS_DATE").getTime()))).toUpperCase());
				
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
			objDto.setFrequencyName(rs.getString("PAYMENTTERM"));
			objDto.setChargeTypeName(rs.getString("CYCLICNONCYCLIC"));
			objDto.setServiceType(rs.getString("SERVICETYPE"));
			objDto.setUom(rs.getString("UOM"));
			objDto.setBandwaidth(rs.getString("BANDWIDTH"));
			objDto.setBilling_bandwidth(rs.getString("BILLING_BANDWIDTH"));
			objDto.setBilling_uom(rs.getString("BILLING_BANDWIDTH_UOM"));
			objDto.setDistance(rs.getString("DISTANCE"));
			if (rs.getString("FROM_CITY") != null && !"".equals(rs.getString("FROM_CITY")) && rs.getString("PRIMARYLOCATIONTYPE")!= null )
			{
				if(Integer.parseInt(rs.getString("PRIMARYLOCATIONTYPE"))==1){
					String ss[] =rs.getString("FROM_CITY").split("~~");
					objDto.setFrom_city(ss[8]);
				}
				else{
					objDto.setFrom_city(" ");
				}
				//objDto.setFrom_city(rs.getString("FROM_CITY"));
			}
			else
			{
				objDto.setFrom_city("");
			}
			if (rs.getString("TO_CITY") != null && !"".equals(rs.getString("TO_CITY")) && rs.getString("SECONDARYLOCATIONTYPE")!= null )
			{
				if(Integer.parseInt(rs.getString("SECONDARYLOCATIONTYPE"))==1){
					String ss[] =rs.getString("TO_CITY").split("~~");
					objDto.setTo_city(ss[8]);
				}
				else{
					objDto.setTo_city(" ");
				}				
				//objDto.setTo_city(rs.getString("TO_CITY"));
			}
			else
			{
				objDto.setTo_city("");
			}
			//objDto.setTo_city("");
			//objDto.setTo_city(rs.getString("TO_CITY"));
			objDto.setOldordertotal(rs.getString("OLD_ORDER_TOTAL"));
			objDto.setOldlineamt(rs.getString("OLD_LINE_AMT"));
			objDto.setOld_contract_period(rs.getString("OLD_CONTRACTPERIOD"));
			objDto.setRatio(rs.getString("RATIO"));
			objDto.setTaxation(rs.getString("TAXATIONVALUE"));
			objDto.setFactory_ckt_id(rs.getString("FACTORY_CKT_ID"));
			objDto.setAccountManager(rs.getString("ACTMEMAIL"));
			objDto.setCurrencyCode(rs.getString("CURNAME"));
			objDto.setOrderTotal(rs.getDouble("ORDERTOTAL"));
			objDto.setPoAmount(rs.getString("POAMOUNT"));
			objDto.setBisource(rs.getString("BISOURCE"));
			objDto.setOrderType(rs.getString("ORDERTYPE"));
			objDto.setDispatchAddress1(rs.getString("DISPATCHADDRESS"));
			objDto.setParent_name(rs.getString("PARENTNAME"));
			objDto.setContractPeriod(rs.getInt("CONTRACTPERIOD"));
			objDto.setOsp(rs.getString("OSP"));
//				[404040] Start 
				objDto.setOpportunityId((rs.getString("OPPORTUNITYID")));				
				//[404040] End 
				objDto.setChargeRemarks(rs.getString("REMARKS"));

				//shourya start
				objDto.setRE_LOGGED_LSI_NO(rs.getString("RE_LOGGED_LSI_NO"));
				//[003] start
				/*objDto.setPARALLEL_UPGRADE_LSI_NO(rs.getString("PARALLEL_UPGRADE_LSI_NO"));*/
				objDto.setParallellUpgradeLSINo1(rs.getString("PARALLELUPGRADELSINO1"));
				objDto.setParallellUpgradeLSINo2(rs.getString("PARALLELUPGRADELSINO2"));
				objDto.setParallellUpgradeLSINo3(rs.getString("PARALLELUPGRADELSINO3"));
				//[003] end
				objDto.setCHARGEDISCONNECTIONSTATUS(rs.getString("CHARGEDISCONNECTIONSTATUS"));
				objDto.setSubchange_type(rs.getString("NAME_SUBTYPE"));
				objDto.setFxAccountExternalId(rs.getString("FX_ACCOUNT_EXTERNAL_ID"));
				objDto.setOldLsi(rs.getString("OLD_LSI_CRM"));
				//lawkush Start

				objDto.setPartyNo(rs.getInt("PARTY_NO"));
				objDto.setObValue(BigDecimal.valueOf((rs.getDouble("OB_VALUE"))).toPlainString());
				//lawkush End			
				objDto.setCustSeg_Description(rs.getString("DESCRIPTION"));
				objDto.setObValueLastUpdateDate(rs.getString("OB_VALUE_LAST_UPDATE_DATE"));
				//[130] start
				objDto.setSalesForceOpportunityNumber(rs.getString("SF_OPP_ID"));
				objDto.setChannelPartner(rs.getString("PARTNER_NAME"));
				objDto.setNetworkType(rs.getString("NETWORK_SERVICE_TYPE"));
				objDto.setPartnerId(rs.getString("PARTNER_ID"));
				//[130] End
				//[131] start
				objDto.setPartnerCode(rs.getString("PARTNER_CODE"));
				objDto.setFieldEngineer(rs.getString("FIELD_ENGINEER"));
				//[131] end
				//nancy start
				objDto.setePCNNo(rs.getString("EPCN_NO"));
				//nancy end
				objDto.setBillingTriggerCreateDate(rs.getString("BILLING_TRIGGER_CREATEDATE"));
				if (rs.getString("BILLING_TRIGGER_CREATEDATE") != null && !"".equals(rs.getString("BILLING_TRIGGER_CREATEDATE")))
				{
					objDto.setBillingTriggerCreateDate((utility.showDate_Report(new Date(rs.getTimestamp("BILLING_TRIGGER_CREATEDATE").getTime()))).toUpperCase());
				}
				//RAHEEM start
				objDto.setIsDifferential(rs.getString("Is_Differential"));
		        objDto.setLinkedOldCharge(rs.getLong("OLD_PK_CHARGEID"));
		        //RAHEEM END
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

/**
 * 
 * @param objDto
 * @return
 * @throws Exception
 */
public ArrayList<OrderDetailChangeReportDTO> viewOrderReportChange(OrderDetailChangeReportDTO objDto) throws Exception {
	//	Nagarjuna
	String methodName="viewOrderReportChange", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;	
	//end	
		ArrayList<OrderDetailChangeReportDTO> objUserList = new ArrayList<OrderDetailChangeReportDTO>();
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
			
			if (objDto.getFromDate() != null && !"".equals(objDto.getFromDate())) {
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				Date dateStr = formatter.parse(objDto.getFromDate());
				String formattedDate = formatter.format(dateStr);
				Date date1 = formatter.parse(formattedDate);
				formatter = new SimpleDateFormat("yyyy-MM-dd");
				formattedDate = formatter.format(date1);
				proc.setString(2,formattedDate);
			} else {
				proc.setNull(2, java.sql.Types.VARCHAR);
			}
			if (objDto.getToDate() != null && !"".equals(objDto.getToDate())) {
				SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
				Date dateStr1 = formatter1.parse(objDto.getToDate());
				String formattedDate1 = formatter1.format(dateStr1);
				Date date2 = formatter1.parse(formattedDate1);
				formatter1 = new SimpleDateFormat("yyyy-MM-dd");
				formattedDate1 = formatter1.format(date2);
				proc.setString(3,formattedDate1);
			} else {
				proc.setNull(3, java.sql.Types.VARCHAR);
			}
			
			/*if (objDto.getFromDate() != null
					&& !"".equals(objDto.getFromDate())) {
				proc.setString(2, objDto.getFromDate().trim());
			} else {
				proc.setNull(2, java.sql.Types.VARCHAR);
			}
			if (objDto.getToDate() != null && !"".equals(objDto.getToDate())) {
				proc.setString(3, objDto.getToDate().trim());
			} else {
				proc.setNull(3, java.sql.Types.VARCHAR);
			}*/
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
			if (objDto.getFromOrderDate() != null && !"".equals(objDto.getFromOrderDate()) && objDto.getToOrderDate() != null && !"".equals(objDto.getToOrderDate()) ) {
				proc.setString(16, objDto.getFromOrderDate());
				proc.setString(17, objDto.getToOrderDate());
			} else {
				proc.setNull(16, java.sql.Types.VARCHAR);
				proc.setNull(17, java.sql.Types.VARCHAR);
			}
			
			rs = proc.executeQuery();
			int countFlag = 0;
			while (rs.next() != false) {
				countFlag++;

				objDto = new OrderDetailChangeReportDTO();
				objDto.setAccountID(rs.getInt("ACCOUNTID"));
				objDto.setAccountManager(rs.getString("ACCT_MGR_NAME"));
				objDto.setAccountno(rs.getString("CRMACCOUNTNO"));
				objDto.setCustSegmentCode(rs.getString("CUST_SEGMENT_CODE"));
				objDto.setAccountCategory(rs.getString("ACCOUNTCATEGORY_DETAILS"));
				objDto.setServiceSubTypeName(rs.getString("SERVICESTAGE"));
				objDto.setServiceDetDescription(rs.getString("SERVICEDETDESCRIPTION"));
				objDto.setChargeStartDate(rs.getString("START_DATE"));
				if (rs.getString("START_DATE") != null && !"".equals(rs.getString("START_DATE")))
				{
					Date date=df.parse(objDto.getChargeStartDate());
					objDto.setChargeStartDate((utility.showDate_Report(date)).toUpperCase());
				}
				objDto.setCancelFlag(rs.getString("CANCELFLAG"));
				objDto.setEntity(rs.getString("ENTITYNAME"));
				objDto.setServiceproductid(rs.getInt("SERVICEPRODUCTID"));
				objDto.setContractPeriod(rs.getInt("CONTRACTPERIOD"));
				objDto.setOrderDate(rs.getString("ORDERDATE"));
				if (rs.getDate("ORDERDATE") != null && !"".equals(rs.getDate("ORDERDATE")))
				{
					Date date=rs.getDate("ORDERDATE");
					objDto.setOrderDate((utility.showDate_Report(date)).toUpperCase());
				}
				objDto.setOrderNumber(rs.getInt("ORDERNO"));
				objDto.setCurrencyCode(rs.getString("CURNAME"));
				objDto.setCustPoDate(rs.getString("CUSTPODATE"));
				if (rs.getString("CUSTPODATE") != null && !"".equals(rs.getString("CUSTPODATE")))
				{
					Date date=df.parse(objDto.getCustPoDate());
					objDto.setCustPoDate((utility.showDate_Report(date)).toUpperCase());
				}
				objDto.setLineItemQuantity(rs.getString("ITEM_QUANTITY"));
				objDto.setBillingBandwidth(rs.getString("BILLING_BANDWIDTH"));
				objDto.setBillingBandwidthUOM(rs.getString("BILLING_BANDWIDTH_UOM"));
				objDto.setDistance(rs.getString("DISTANCE"));
				objDto.setLineItemAmount(BigDecimal.valueOf((rs.getDouble("CHARGEAMOUNT"))).toPlainString());
				objDto.setChargeAnnotation(rs.getString("ANNOTATION"));
				objDto.setLOC_Date(rs.getString("LOCDATE"));
				if (rs.getString("LOCDATE") != null && !"".equals(rs.getString("LOCDATE")))
				{
					Date date=df.parse(objDto.getLOC_Date());
					objDto.setLOC_Date((utility.showDate_Report(date)).toUpperCase());
				}
				objDto.setCopcApproveDate(rs.getString("COPC_APPROVAL_DATE"));
				if (rs.getString("COPC_APPROVAL_DATE") != null && !"".equals(rs.getString("COPC_APPROVAL_DATE")))
				{
					objDto.setCopcApproveDate((utility.showDate_Report(new Date(rs.getTimestamp("COPC_APPROVAL_DATE").getTime()))).toUpperCase());
				}
				objDto.setRfsDate(rs.getString("RFS_DATE"));
				if (rs.getString("RFS_DATE") != null && !"".equals(rs.getString("RFS_DATE")))
				{
					Date date=df.parse(objDto.getRfsDate());
					objDto.setRfsDate((utility.showDate_Report(date)).toUpperCase());
				}
				objDto.setRfs_date(rs.getString("CUST_RFS_DATE"));
				if (rs.getString("CUST_RFS_DATE") != null && !"".equals(rs.getString("CUST_RFS_DATE")))
				{
					Date date=df.parse(objDto.getRfs_date());
					objDto.setRfs_date((utility.showDate_Report(date)).toUpperCase());
				}
				objDto.setCustPoDetailNo(rs.getString("CUSTPONUMBER"));
				objDto.setPoAmount(BigDecimal.valueOf((rs.getDouble("POAMOUNT"))).toPlainString());
				objDto.setOrderTotalAmount(BigDecimal.valueOf((rs.getDouble("TOT_AMOUNT"))).toPlainString());
				objDto.setAmApproveDate(rs.getString("AM_RECEIVE_DATE"));
				if (rs.getString("AM_RECEIVE_DATE") != null && !"".equals(rs.getString("AM_RECEIVE_DATE")))
				{
					objDto.setAmApproveDate((utility.showDate_Report(new Date(rs.getTimestamp("AM_RECEIVE_DATE").getTime()))).toUpperCase());
				}
				objDto.setLogicalSINo(rs.getString("LOGICAL_SI_NO"));
				objDto.setStartDate(rs.getString("CHARGE_CRT_ORDERNO"));
				objDto.setEndDate(rs.getString("END_ORDER_NO"));
				objDto.setOrderType(rs.getString("ORDERTYPE"));
				objDto.setFrequencyName(rs.getString("FREQUENCYNAME"));
				objDto.setProjectManager(rs.getString("PRJ_MGR_NAME"));
				objDto.setRegionName(rs.getString("REGIONNAME"));
				objDto.setRatio(rs.getString("RATIO"));
				objDto.setVerticalDetails(rs.getString("VERTICAL_DETAILS"));
				objDto.setOsp(rs.getString("OSP"));
			// Anadi's	
				objDto.setCopcEndDate(rs.getString("COPC_END_DATE"));
						
				if (rs.getString("COPC_END_DATE") != null && !"".equals(rs.getString("COPC_END_DATE")))
				{
					objDto.setCopcEndDate((utility.showDate_Report(new Date(rs.getTimestamp("COPC_END_DATE").getTime()))).toUpperCase());
					
					//Date date=df.parse(objDto.getCopcEndDate());
					//objDto.setCopcEndDate((utility.showDate_Report(date)).toUpperCase());
				}
			// End : Anadi	
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


/**
 * 
 * @param objDto
 * @return
 * @throws Exception
 */
public ArrayList<OrderDetailReportDTO> viewOrderReportDetails(OrderDetailReportDTO objDto) throws Exception {

	//	Nagarjuna
	String methodName="viewOrderReportDetails", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;	
	//end	
	ArrayList<OrderDetailReportDTO> objUserList = new ArrayList<OrderDetailReportDTO>();
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
		// index
		if (objDto.getOsp() != null && !"".equals(objDto.getOsp())) {
				proc.setString(14, objDto.getOsp().trim());
			} else {
				proc.setNull(14, java.sql.Types.VARCHAR);
			}
		if (objDto.getFromOrderDate() != null && !"".equals(objDto.getFromOrderDate()) && objDto.getToOrderDate() != null && !"".equals(objDto.getToOrderDate()) ) {
			proc.setString(15, objDto.getFromOrderDate());
			proc.setString(16, objDto.getToOrderDate());
		} else {
			proc.setNull(15, java.sql.Types.VARCHAR);
			proc.setNull(16, java.sql.Types.VARCHAR);
		}

		rs = proc.executeQuery();
		int countFlag = 0;
		while (rs.next() != false) {
			countFlag++;

			objDto = new OrderDetailReportDTO();
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
				DateFormat dformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
				DateFormat dformat2 = new SimpleDateFormat("dd-MM-yyyy");
				Date date;
				if(objDto.getBillingPODate().length()>10){
					date=dformat.parse(objDto.getBillingPODate());
				}else if(objDto.getBillingPODate().indexOf('-') != -1){
					date=dformat2.parse(objDto.getBillingPODate());
				}else{
					date=df.parse(objDto.getBillingPODate());
				}
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
			if (rs.getDate("ORDERCREATIONDATE") != null && !"".equals(rs.getDate("ORDERCREATIONDATE"))) 
			{
				Date date=rs.getDate("ORDERCREATIONDATE");
				objDto.setOrderDate((utility.showDate_Report(date)).toUpperCase());
			}
			objDto.setCustomerRfsDate(rs.getString("RFS_DATE"));
			if (rs.getString("RFS_DATE") != null && !"".equals(rs.getString("RFS_DATE"))) 
			{
				DateFormat dformat = new SimpleDateFormat("yyyy-MM-dd");
				Date date;
				if(objDto.getCustomerRfsDate().indexOf('-') != -1){
					date=dformat.parse(objDto.getCustomerRfsDate());
				}else{
					date=df.parse(objDto.getCustomerRfsDate());
				}
				objDto.setCustomerRfsDate((utility.showDate_Report(date)).toUpperCase());
				
			}
			objDto.setCustomerServiceRfsDate(rs.getString("SERVICE_RFS_DATE"));
			if (rs.getString("SERVICE_RFS_DATE") != null && !"".equals(rs.getString("SERVICE_RFS_DATE"))) 
			{
				
				Date date=df.parse(objDto.getCustomerServiceRfsDate());
				objDto.setCustomerServiceRfsDate((utility.showDate_Report(date)).toUpperCase());
				
			}
			objDto.setCurrencyCode(rs.getString("CURRENCYNAME")); 
			objDto.setChargeName(rs.getString("CHARGE_NAME"));
			objDto.setCustomerPoDate(rs.getString("PODATE"));
			if (rs.getString("PODATE") != null && !"".equals(rs.getString("PODATE"))) 
			{
				
				Date date=df.parse(objDto.getCustomerPoDate());
				objDto.setCustomerPoDate((utility.showDate_Report(date)).toUpperCase());
				
			}
			objDto.setCustomerPoNumber(rs.getString("CUSTPONUMBER"));
			objDto.setCyclicNonCyclic(rs.getString("CYCLIC_NONCYCLIC"));
			objDto.setChallenno(rs.getString("CHALLEN_NO"));
			objDto.setOrderNumber(rs.getInt("ORDERNO"));
			objDto.setFromSite(rs.getString("PRIMARYLOCATION")); 
			objDto.setFromSite(objDto.getFromSite()==null ? null : objDto.getFromSite().replaceAll("~", ""));
			objDto.setToSite(rs.getString("SECONDARYLOCATION")); 
			objDto.setToSite(objDto.getToSite() ==null ? null : objDto.getToSite().replaceAll("~", ""));
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
				objDto.setLocDate((utility.showDate_Report(date)).toUpperCase());
				
			}
			objDto.setAmReceiveDate(rs.getString("AMRECEIVEDATE")); 
			if (rs.getString("AMRECEIVEDATE") != null && !"".equals(rs.getString("AMRECEIVEDATE"))) 
			{
				
				Date date=df.parse(objDto.getAmReceiveDate());
				objDto.setAmReceiveDate((utility.showDate_Report(date)).toUpperCase());
				
			}
			objDto.setOrderTotal(rs.getDouble("POAMOUNT"));
			objDto.setOrderEntryDate(rs.getString("ORDERCREATIONDATE"));
			if (rs.getDate("ORDERCREATIONDATE") != null && !"".equals(rs.getDate("ORDERCREATIONDATE"))) 
			{
				Date date=rs.getDate("ORDERCREATIONDATE");
				objDto.setOrderEntryDate((utility.showDate_Report(date)).toUpperCase());
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
				objDto.setChargeEndDate((utility.showDate_Report(date)).toUpperCase());
				
			}
			objDto.setEndDateLogic(rs.getString("ENDDATELOGIC"));
			objDto.setNewOrderRemark(rs.getString("NEWORDER_REMARKS"));
			objDto.setRemarks(rs.getString("REMARKS"));
			objDto.setServiceOrderType(rs.getString("SERVICE_ORDER_TYPE"));
			objDto.setOsp(rs.getString("OSP"));
		
//			[404040] Start 
			objDto.setOpportunityId((rs.getString("OPPORTUNITYID")));				
			//[404040] End 
			//<!--GlobalDataBillingEfficiency BFR6  -->
			objDto.setTaxExcemption_Reason(rs.getString("TAXEXCEMPTION_REASON"));
				//[130] start
				objDto.setSalesForceOpportunityNumber(rs.getString("SF_OPP_ID"));
				objDto.setChannelPartner(rs.getString("PARTNER_NAME"));
				objDto.setNetworkType(rs.getString("NETWORK_SERVICE_TYPE"));
				objDto.setPartnerId(rs.getString("PARTNER_ID"));
				//[130] End
				//[131] start
				objDto.setPartnerCode(rs.getString("PARTNER_CODE"));
				objDto.setFieldEngineer(rs.getString("FIELD_ENGINEER"));
				//[131] end
			
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
	
	// Saurabh
public ArrayList<PendingOrderAndBillingReportDTO> viewPendingOrderAndBillingList(PendingOrderAndBillingReportDTO objDto)
throws Exception {
	//	Nagarjuna
	String methodName="viewPendingOrderAndBillingList", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;	
	//end	
		ArrayList<PendingOrderAndBillingReportDTO> objUserList = new ArrayList<PendingOrderAndBillingReportDTO>();
		Connection conn = null;
		CallableStatement proc = null;
		ResultSet rs = null;
		int recordCount = 0;
		PendingOrderAndBillingReportDTO objRDto;
		Utility utility=new Utility();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		
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
			proc.setString(5, formattedDate);
		} 
		else 
		{
			proc.setNull(5, java.sql.Types.VARCHAR);
		}
		if (objDto.getToCopcApprovedDate() != null && !"".equals(objDto.getToCopcApprovedDate())) {
			SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
			Date dateStr1 = formatter1.parse(objDto.getToCopcApprovedDate());
			String formattedDate1 = formatter1.format(dateStr1);
			Date date2 = formatter1.parse(formattedDate1);
			formatter1 = new SimpleDateFormat("MM-dd-yyyy");
			formattedDate1 = formatter1.format(date2);
			proc.setString(6, formattedDate1);
		}
		else 
		{
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
if (objDto.getOsp() != null && !"".equals(objDto.getOsp())) {
			proc.setString(15, objDto.getOsp().trim());
		} else {
			proc.setNull(15, java.sql.Types.VARCHAR);
		}
		
		rs = proc.executeQuery();
		int countFlag = 0;
		while (rs.next() != false) {
			countFlag++;
		
			objDto = new PendingOrderAndBillingReportDTO();
			objDto.setCrmAccountNo(rs.getInt("CRMACCOUNTNO"));
			objDto.setRegionName(rs.getString("REGIONNAME"));
			objDto.setCus_segment(rs.getString("CUST_SEGMENT_CODE"));
			objDto.setAct_category(rs.getString("ACCOUNTCATEGORY_DETAILS"));
			objDto.setAccountID(rs.getInt("ACCOUNTID"));
			objDto.setCrm_productname(rs.getString("CRM_PRODUCT_NAME"));
			objDto.setLogicalCircuitId(rs.getString("LOGICALCKTID"));
			objDto.setCompanyName(rs.getString("COMPANYNAME"));
			objDto.setFactory_ckt_id(rs.getString("FACTORY_CKT_ID"));
			objDto.setOrderNumber(rs.getInt("ORDERNO"));
			objDto.setCustPoDate(rs.getString("CUSTPODATE"));
	if (rs.getString("CUSTPODATE") != null && !"".equals(rs.getString("CUSTPODATE")))
			{
				
				Date date=df.parse(objDto.getCustPoDate());
				objDto.setCustPoDate((utility.showDate_Report(date)).toUpperCase());
				
			}
			objDto.setCustPoDetailNo(rs.getString("CUSTPONUMBER"));
			objDto.setLinename(rs.getString("LINENAME"));
			objDto.setLcompanyname(rs.getString("LICENCECOMPANYNAME"));
			objDto.setChargeName(rs.getString("CHARGE_NAME"));
			objDto.setChargeTypeName(rs.getString("CYCLICNONCYCLIC"));
			objDto.setStorename(rs.getString("STORENAME"));
			objDto.setM6OrderNo(rs.getInt("M6ORDERNO"));
			objDto.setContractPeriod(rs.getInt("CONTRACTPERIOD"));
			objDto.setCurrencyName(rs.getString("CURRENCYNAME"));
			objDto.setAnnualRate(rs.getString("ANNUAL_RATE"));
			if(objDto.getAnnualRate()==null){
				objDto.setAnnualRate(" ");
			}
			objDto.setChargeAmount_String(BigDecimal.valueOf((rs.getDouble("CHARGEAMOUNT"))).toPlainString());
			
			if(objDto.getChargeAmount_String()==null){
				objDto.setChargeAmount_String(" ");
			}
			objDto.setLine_desc(rs.getString("LINE_ITEM_DESC"));
			objDto.setActmngname(rs.getString("ACTMNAME"));
			objDto.setOrderType(rs.getString("ORDERTYPE"));
			objDto.setFrequencyName(rs.getString("PAYMENTTERM"));
			objDto.setLOC_Date(rs.getString("LOCDATE"));
			if (rs.getString("LOCDATE") != null && !"".equals(rs.getString("LOCDATE")))
			{
				
				Date date=df.parse(objDto.getLOC_Date());
				objDto.setLOC_Date((utility.showDate_Report(date)).toUpperCase());
				
			}
			objDto.setPm_pro_date(rs.getString("PM_PROV_DATE"));
if (rs.getString("PM_PROV_DATE") != null && !"".equals(rs.getString("PM_PROV_DATE")))
			{
			  String s1=rs.getString("PM_PROV_DATE");
			  String s3=s1.substring(0,7).toUpperCase();
			  String s4=s1.substring(9,11);
			  String s5=s3.concat(s4);
			  objDto.setPm_pro_date(s5);
			}
			objDto.setOrderDate(rs.getString("POCREATEDATE"));
			if (rs.getString("POCREATEDATE") != null && !"".equals(rs.getString("POCREATEDATE")))
			{
				
				objDto.setOrderDate((utility.showDate_Report(new Date(rs.getTimestamp("POCREATEDATE").getTime()))).toUpperCase());
				
			}
			
			objDto.setPoNumber(rs.getInt("PONUMBER"));
			objDto.setPrjmngname(rs.getString("PMNAME"));
			objDto.setCopcApproveDate(rs.getString("COPC_APPROVAL_DATE"));
				if (rs.getString("COPC_APPROVAL_DATE") != null && !"".equals(rs.getString("COPC_APPROVAL_DATE")))
			{
				objDto.setCopcApproveDate((utility.showDate_Report(new Date(rs.getTimestamp("COPC_APPROVAL_DATE").getTime()))).toUpperCase());
			}
		    objDto.setTaxation(rs.getString("TAXATION"));
		    objDto.setUom(rs.getString("UOM"));
			objDto.setBandwaidth(rs.getString("BANDWIDTH"));
			objDto.setBilling_bandwidth(rs.getString("BILLING_BANDWIDTH"));
			objDto.setBilling_uom(rs.getString("BILLING_BANDWIDTH_UOM"));
			objDto.setVerticalDetails(rs.getString("VERTICAL_DETAILS"));
			//objDto.setOsp(rs.getString("OSP"));
			
			objDto.setServiceProductId(rs.getString("SERVICEPRODUCTID"));            //  StartChange by Anadi's Change
			objDto.setBillingTriggStatus(rs.getString("BILLING_TRIGGER_STATUS"));
			objDto.setChargeInfoId(rs.getString("CHARGEINFOID"));
			objDto.setChangeServiceId(rs.getString("CHANGE_SERVICEID"));				//  EndChange By Anadi 
			
			
			
			
			
			
			
			
			
		
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
public ArrayList<PendingOrdersAndBillingHardwaresDTO> ViewpendingOrderBillandHardwareList(PendingOrdersAndBillingHardwaresDTO objDto)
throws Exception {
	//	Nagarjuna
	String methodName="ViewpendingOrderBillandHardwareList", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;	
	//end	
ArrayList<PendingOrdersAndBillingHardwaresDTO> objUserList = new ArrayList<PendingOrdersAndBillingHardwaresDTO>();
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
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	Date dateStr = formatter.parse(objDto.getFromOrderDate());
		String formattedDate = formatter.format(dateStr);
		Date date1 = formatter.parse(formattedDate);
		formatter = new SimpleDateFormat("MM-dd-yyyy");
		formattedDate = formatter.format(date1);
	
	proc.setString(4, formattedDate);
} else {
	proc.setNull(4, java.sql.Types.VARCHAR);
}
if (objDto.getToOrderDate() != null && !"".equals(objDto.getToOrderDate())) {
	SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
	Date dateStr1 = formatter1.parse(objDto.getToOrderDate());
	String formattedDate1 = formatter1.format(dateStr1);
	Date date2 = formatter1.parse(formattedDate1);
	formatter1 = new SimpleDateFormat("MM-dd-yyyy");
	formattedDate1 = formatter1.format(date2);
	proc.setString(5, formattedDate1);
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

	objDto = new PendingOrdersAndBillingHardwaresDTO();
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
	objDto.setLinename(rs.getString("LINENAME"));
	//objDto.setChargeAmount(rs.getDouble("CHARGEAMOUNT"));
	objDto.setChargeAmount_String(BigDecimal.valueOf((rs.getDouble("CHARGEAMOUNT"))).toPlainString());
	
	if(objDto.getChargeAmount_String()==null){
		objDto.setChargeAmount_String(" ");
	}
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
	  if(s1.length()==10){
		  s1="0"+s1;
	  }
	  String s3=s1.substring(0,7).toUpperCase();
	  String s4=s1.substring(9,11);
	  String s5=s3.concat(s4);
	  objDto.setChallendate(s5);
	}

	objDto.setPartyName(rs.getString("PARTYNAME"));
	objDto.setOrderType(rs.getString("ORDERTYPE"));
	//[606060] Start
	objDto.setServiceNumber(rs.getInt("SERVICE"));
	objDto.setCurrency(rs.getString("CURRENCY"));
	//[606060] End
	

	
	

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


public ArrayList<M6OrderCancelReportDTO> viewM6OrderCancelReport(M6OrderCancelReportDTO objDto)
	throws Exception {
	//	Nagarjuna
	String methodName="viewM6OrderCancelReport", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;	
	//end	
	ArrayList<M6OrderCancelReportDTO> objUserList = new ArrayList<M6OrderCancelReportDTO>();
	Connection conn = null;
	CallableStatement proc = null;
	ResultSet rs = null;
	int recordCount = 0;
	Utility utility=new Utility();
	
	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	try {
	
		conn = DbConnection.getReportsConnectionObject();
		
		proc = conn.prepareCall(sqlM6OrderCancelReport);
		/*if (objDto.getFromDate() != null
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
		}*/
		if (objDto.getFromDate() != null && !"".equals(objDto.getFromDate())) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");	
		Date dateStr = formatter.parse(objDto.getFromDate());
		String formattedDate = formatter.format(dateStr);
		Date date1 = formatter.parse(objDto.getFromDate());
		formatter = new SimpleDateFormat("yyyy-MM-dd");
		formattedDate = formatter.format(date1);
		proc.setString(1,formattedDate);
		} else {
		proc.setNull(1, java.sql.Types.VARCHAR);
		}
		if (objDto.getToDate() != null && !"".equals(objDto.getToDate())) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");	
		Date dateStr = formatter.parse(objDto.getToDate());
		String formattedDate = formatter.format(dateStr);
		Date date1 = formatter.parse(objDto.getToDate());
		formatter = new SimpleDateFormat("yyyy-MM-dd");
		formattedDate = formatter.format(date1);
		proc.setString(2,formattedDate);
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
		proc.setInt(7, (pagingSorting.isPagingToBeDone() ? 1 : 0));// end
		// index
		rs = proc.executeQuery();
		int countFlag = 0;
		while (rs.next() != false) {
			countFlag++;
		
			objDto = new M6OrderCancelReportDTO();
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
			objDto.setOldLsi(rs.getString("OLD_LSI_CRM"));
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

public ArrayList<PerformanceDetailReportDTO> viewPerformanceDetailList(PerformanceDetailReportDTO objDto) 
{
	//	Nagarjuna
	String methodName="viewPerformanceDetailList", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;	
	//end	
	Connection connection =null;
	CallableStatement proc =null;
	ResultSet rs = null;
	ArrayList<PerformanceDetailReportDTO> listSearchDetails = new ArrayList<PerformanceDetailReportDTO>();
	PerformanceDetailReportDTO objReportsDto = null;
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
		/*if (objDto.getFromDate() != null
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
		}*/
		
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
			objReportsDto =  new PerformanceDetailReportDTO();
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
			objReportsDto.setPoNumber(rs.getInt("PODETAILNUMBER"));
//			[404040] Start 
			objReportsDto.setOpportunityId((rs.getString("OPPORTUNITYID")));				
			//[404040] End 
			objReportsDto.setOldLsi(rs.getString("OLD_LSI_CRM"));
			objReportsDto.setGroupName((rs.getString("GROUPNAME")));
			//lawkush start
			objReportsDto.setCopcStartDate(rs.getString("COPC_START_DATE"));
			if (rs.getString("COPC_START_DATE") != null && !"".equals(rs.getString("COPC_START_DATE")))
			{
				objReportsDto.setCopcStartDate((utility.showDate_Report(new Date(rs.getTimestamp("COPC_START_DATE").getTime()))).toUpperCase());
			}
			objReportsDto.setCopcEndDate(rs.getString("COPC_END_DATE"));		
			if (rs.getString("COPC_END_DATE") != null && !"".equals(rs.getString("COPC_END_DATE")))
			{
				objReportsDto.setCopcEndDate((utility.showDate_Report(new Date(rs.getTimestamp("COPC_END_DATE").getTime()))).toUpperCase());
			}
			//lawkush End
				//[130] start
				objReportsDto.setSalesForceOpportunityNumber(rs.getString("SF_OPP_ID"));
				objReportsDto.setChannelPartner(rs.getString("PARTNER_NAME"));
				objReportsDto.setPartnerId(rs.getString("PARTNER_ID"));
				//[130] End
				//[131] start
				objReportsDto.setPartnerCode(rs.getString("PARTNER_CODE"));
				objReportsDto.setFieldEngineer(rs.getString("FIELD_ENGINEER"));
				//[131] end
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
public ArrayList<TelemediaOrderReportDTO> getTelemediaOrderList(TelemediaOrderReportDTO objDto) 
{
	//	Nagarjuna
	String methodName="getTelemediaOrderList", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;	
	//end	
	Connection connection =null;
	CallableStatement proc =null;
	ResultSet rs = null;
	ArrayList<TelemediaOrderReportDTO> listSearchDetails = new ArrayList<TelemediaOrderReportDTO>();
	TelemediaOrderReportDTO objReportsDto = null;
	int recordCount =0;
	Utility utility=new Utility();
	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	try
	{
		connection=DbConnection.getReportsConnectionObject();
		proc= connection.prepareCall(sqlGetTelemediaOrderReport);
		/*if (objDto.getFromCopcApprovedDate() != null 
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
		}*/
		
		if (objDto.getFromCopcApprovedDate() != null
				&& !"".equals(objDto.getFromCopcApprovedDate())) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date dateStr = formatter.parse(objDto.getFromCopcApprovedDate());
			String formattedDate = formatter.format(dateStr);
			Date date1 = formatter.parse(formattedDate);
			formatter = new SimpleDateFormat("yyyy-MM-dd");
			formattedDate = formatter.format(date1);
			proc.setString(1,formattedDate);
		} else {
			proc.setNull(1, java.sql.Types.VARCHAR);
		}
		if (objDto.getToCopcApprovedDate() != null 
				&& !"".equals(objDto.getToCopcApprovedDate())) {
			SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
			Date dateStr1 = formatter1.parse(objDto.getToCopcApprovedDate());
			String formattedDate1 = formatter1.format(dateStr1);
			Date date2 = formatter1.parse(formattedDate1);
			formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			formattedDate1 = formatter1.format(date2);
			proc.setString(2,formattedDate1);
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
			objReportsDto =  new TelemediaOrderReportDTO();
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
public ArrayList<ZeroOrderValueReportDTO> viewZeroOrderValueReportDetails(ZeroOrderValueReportDTO objDto) throws Exception {

	//	Nagarjuna
	String methodName="viewZeroOrderValueReportDetails", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;	
	//end	
	ArrayList<ZeroOrderValueReportDTO> objUserList = new ArrayList<ZeroOrderValueReportDTO>();
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

			objDto = new ZeroOrderValueReportDTO();
			
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
			objDto.setOldLsi(rs.getString("OLD_LSI_CRM"));
			objDto.setBusiness_serial_n(rs.getInt("BUSINESS_SERIAL_NO"));
			objDto.setMocn_no(rs.getString("MOCN_NUMBER"));
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
	
//LAWKUSH END

public ArrayList<RateRenewalReportDTO> viewRateRenewalReport(RateRenewalReportDTO objDto) throws Exception {

	//	Nagarjuna
	String methodName="viewRateRenewalReport", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;	
	//end	
	ArrayList<RateRenewalReportDTO> objUserList = new ArrayList<RateRenewalReportDTO>();
	Connection conn = null;
	CallableStatement proc = null;
	ResultSet rs = null;
	int recordCount = 0;
	RateRenewalReportDTO objRDto ;
	Utility utility=new Utility();
	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	try {

		conn = DbConnection.getReportsConnectionObject();

		proc = conn.prepareCall(sqlRateRenewalReport);
		
	/*	if (objDto.getOrderType() != null
				&& !"".equals(objDto.getOrderType())) {
			proc.setString(1, objDto.getOrderType().trim());
		} else {
			proc.setNull(1, java.sql.Types.VARCHAR);
		}*/
		/*(if (objDto.getFromDate() != null
				&& !"".equals(objDto.getFromDate())) {
			proc.setString(1, objDto.getFromDate().trim());
		} else {
			proc.setNull(1, java.sql.Types.VARCHAR);
		}
		if (objDto.getToDate() != null && !"".equals(objDto.getToDate())) {
			proc.setString(2, objDto.ge
			tToDate().trim());
		} else {
			proc.setNull(2, java.sql.Types.VARCHAR);
		}*/
		
		if (objDto.getFromDate() != null
				&& !"".equals(objDto.getFromDate())) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date dateStr = formatter.parse(objDto.getFromDate());
			String formattedDate = formatter.format(dateStr);
			Date date1 = formatter.parse(formattedDate);
			formatter = new SimpleDateFormat("yyyy-MM-dd");
			formattedDate = formatter.format(date1);
			proc.setString(1,formattedDate);
		} else {
			proc.setNull(1, java.sql.Types.VARCHAR);
		}
		if (objDto.getToDate() != null 
				&& !"".equals(objDto.getToDate())) {
			SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
			Date dateStr1 = formatter1.parse(objDto.getToDate());
			String formattedDate1 = formatter1.format(dateStr1);
			Date date2 = formatter1.parse(formattedDate1);
			formatter1 = new SimpleDateFormat("yyyy-MM-dd");
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
			//sada
			if (objDto.getCus_segment() != null 
					&& !"".equals(objDto.getCus_segment())) {
				proc.setString(12, objDto.getCus_segment().trim().toUpperCase());
			} else {
				proc.setNull(12, java.sql.Types.VARCHAR);
			}
			
		
			
			//sada

		rs = proc.executeQuery();
		int countFlag = 0;
		while (rs.next() != false) {
			countFlag++;

			objDto = new RateRenewalReportDTO();
			objDto.setPartyNo(rs.getInt("PARTY_NO"));
			objDto.setPartyName(rs.getString("PARTYNAME"));  
			objDto.setCrmACcountNO(rs.getString("CRMACCOUNTNO"));
			objDto.setAccountManager(rs.getString("ACCOUNTMANAGER"));
			objDto.setCus_segment(rs.getString("CUST_SEGMENT_CODE"));
			objDto.setServiceSegment(rs.getString("SERVICESEGMENT"));
			objDto.setVerticalDetails(rs.getString("VERTICAL_DETAILS"));
			objDto.setRegionName(rs.getString("REGION"));
			objDto.setServiceDetDescription(rs.getString("SERVICEDETDESCRIPTION"));
			objDto.setBilling_bandwidth(rs.getString("BILLING_BANDWIDTH"));
			objDto.setBilling_uom(rs.getString("BILLING_BANDWIDTH_UOM"));
			objDto.setChangeTypeName(rs.getString("NAME_SUBTYPE"));
			objDto.setOrderType(rs.getString("ORDERTYPE"));
			objDto.setCompanyName(rs.getString("COMPANYNAME"));
			objDto.setCreatedDate(rs.getString("CREATEDDATE"));
if (rs.getString("CREATEDDATE") != null && !"".equals(rs.getString("CREATEDDATE")))
			{
				objDto.setCreatedDate((utility.showDate_Report(new Date(rs.getTimestamp("CREATEDDATE").getTime()))).toUpperCase());
			}
			objDto.setCurrencyCode(rs.getString("CURNAME"));
			objDto.setPoDate(rs.getString("PODATE"));
			if (rs.getString("PODATE") != null && !"".equals(rs.getString("PODATE")))
			{
				objDto.setPoDate((utility.showDate_Report(new Date(rs.getTimestamp("PODATE").getTime()))).toUpperCase());							
			}
			
			objDto.setCustPoDetailNo(rs.getString("CUSTPONUMBER"));
			objDto.setChargeName(rs.getString("CHARGE_NAME"));
			objDto.setChargeTypeName(rs.getString("CHARGENAME"));
			objDto.setFromLocation(rs.getString("PRIMARYLOCATION"));
			objDto.setToLocation(rs.getString("SECONDARYLOCATION"));
			objDto.setDistance(rs.getString("DISTANCE"));
			objDto.setLineItemDescription(rs.getString("LINE_ITEM_DESCRIPTION"));
			objDto.setLOC_Date(rs.getString("LOCDATE")); 
	if (rs.getString("LOCDATE") != null && !"".equals(rs.getString("LOCDATE")))
			{
				
				Date date=df.parse(objDto.getLOC_Date());
				objDto.setLOC_Date((utility.showDate_Report(date)).toUpperCase());
			}
			objDto.setCopcApproveDate(rs.getString("COPC_APPROVAL_DATE"));       
if (rs.getString("COPC_APPROVAL_DATE") != null && !"".equals(rs.getString("COPC_APPROVAL_DATE")))
			{
				objDto.setCopcApproveDate((utility.showDate_Report(new Date(rs.getTimestamp("COPC_APPROVAL_DATE").getTime()))).toUpperCase());
			}
			objDto.setLogicalCircuitNumber(rs.getString("CKTID"));  
			objDto.setPaymentTerm(rs.getString("PAYMENTTERM")); 
			objDto.setTaxationName(rs.getString("TAXATIONVALUE")); 
			objDto.setLcompanyname(rs.getString("LCOMPANYNAME"));
			objDto.setOrder_type(rs.getString("DEMO_TYPE"));
			objDto.setServiceStage(rs.getString("SERVICE_STAGE"));
			objDto.setStageName(rs.getString("ORDER_STAGE"));
			objDto.setNewOrderRemark(rs.getString("NEWORDER_REMARKS"));
			objDto.setMocn_no(rs.getString("MOCN_NUMBER"));
			objDto.setRemarks(rs.getString("REMARKS"));
			//nagarjuna
			objDto.setProductName(rs.getString("PRODUCTNAME")); //nagarjuna
			objDto.setSubProductName(rs.getString("SERVICESUBTYPENAME")); 
			objDto.setBilling_trigger_date(rs.getString("BILLINGTRIGGERDATE")); 
if (rs.getString("BILLINGTRIGGERDATE") != null && !"".equals(rs.getString("BILLINGTRIGGERDATE")))
			{
				
				Date date=df.parse(objDto.getBilling_trigger_date());
				objDto.setBilling_trigger_date((utility.showDate_Report(date)).toUpperCase());
			}
			objDto.setBillingTriggerFlag(rs.getString("BILLINGTRIGGERFLAG")); 
			objDto.setLinename(rs.getString("SERVICEDETDESCRIPTION"));
			objDto.setStartDate(rs.getString("START_DATE"));
	if (rs.getString("START_DATE") != null && !"".equals(rs.getString("START_DATE")))
			{
				
				Date date=df.parse(objDto.getStartDate());
				objDto.setStartDate((utility.showDate_Report(date)).toUpperCase());
				
			}
			objDto.setEndDate(rs.getString("END_DATE")); 
if (rs.getString("END_DATE") != null && !"".equals(rs.getString("END_DATE")))
			{
				
				Date date=df.parse(objDto.getEndDate());
				objDto.setEndDate((utility.showDate_Report(date)).toUpperCase());
				
			}
			objDto.setEndHWDateLogic(rs.getString("ENDDATELOGIC"));
	/*[RPT7052013027]- start - change TD to Till Disconnection and BTD to Billing Trigger Done */		
			if(rs.getString("ENDDATELOGIC") !=null && rs.getString("ENDDATELOGIC").equalsIgnoreCase("TD")){
				objDto.setEndHWDateLogic("TILL DISCONNECTION");
			}
			else if(rs.getString("ENDDATELOGIC") !=null &&  rs.getString("ENDDATELOGIC").equalsIgnoreCase("BTD")){
				objDto.setEndHWDateLogic("Billing Trigger Done");
			}
	/*[RPT7052013027] - end */		
			objDto.setCharge_status(rs.getString("CHARGES_STATUS"));
			objDto.setStartDaysLogic(rs.getString("CHARGESTARTDAYSLOGIC")); 
			objDto.setStartMonthsLogic(rs.getString("CHARGESTARTMONTHSLOGIC")); 
			objDto.setZoneName(rs.getString("ZONE"));
			objDto.setSalesCoordinator(rs.getString("SALESCOORDINATOR"));
			objDto.setPoAmount(rs.getString("POAMOUNT"));
			objDto.setContractPeriod(rs.getInt("CONTRACTPERIOD")); 
			objDto.setChargePeriod(rs.getInt("CHARGEPERIOD")); 
			objDto.setItemQuantity(1);
			objDto.setTotalPoAmt(rs.getString("POAMOUNT"));
			objDto.setServiceId(rs.getInt("SERVICEID")); 
			objDto.setM6OrderNo(rs.getInt("M6ORDERNO"));
			objDto.setLogicalSINo(rs.getString("CUSTOMER_LOGICAL_SI_NO"));
			objDto.setM6cktid(rs.getString("CKTID"));
			objDto.setAnnual_rate(rs.getDouble("ANNUAL_RATE"));
			objDto.setServiceProductID(rs.getInt("SERVICEPRODUCTID"));
			objDto.setOrderNo(rs.getString("ORDERNO"));
			objDto.setOldOrderNo(rs.getInt("OLDORDERNO"));
			objDto.setChargeAmount_String(rs.getString("CHARGEAMOUNT"));
			objDto.setOldLineitemAmount(rs.getString("OLDCHARGEAMOUNT"));
			objDto.setTxtStartDays(rs.getInt("START_DATE_DAYS"));
			objDto.setTxtStartMonth(rs.getInt("START_DATE_MONTH"));
			/* [RPT7052013027]- start --add some fields */
			objDto.setLogicalCircuitId(rs.getString("LOGICAL_SI_NO"));
			objDto.setRatio(rs.getString("ratio"));
			objDto.setPk_charge_id(rs.getString("CHARGEINFOID"));
			/* [RPT7052013027] -end*/
			objDto.setStandardReason(rs.getString("STANDARDREASON"));
                //NANCY START
				objDto.setIsDifferential(rs.getString("IS_DIFFERENTIAL"));
				objDto.setOldPkChargeId(rs.getLong("OLD_PK_CHARGEID"));
				objDto.setCopcApproverName(rs.getString("COPC_APPROVER_NAME"));
				objDto.setEffectiveDate(rs.getString("EFFECTIVEDATE"));
				if (rs.getString("EFFECTIVEDATE") != null && !"".equals(rs.getString("EFFECTIVEDATE")))
				{
					objDto.setEffectiveDate((utility.showDate_Report(new Date(rs.getTimestamp("EFFECTIVEDATE").getTime()))).toUpperCase());
				}
				objDto.setBillingTriggerCreateDate(rs.getString("BILLING_TRIGGER_CREATEDATE"));
				if (rs.getString("BILLING_TRIGGER_CREATEDATE") != null && !"".equals(rs.getString("BILLING_TRIGGER_CREATEDATE")))
				{
					objDto.setBillingTriggerCreateDate((utility.showDate_Report(new Date(rs.getTimestamp("BILLING_TRIGGER_CREATEDATE").getTime()))).toUpperCase());
				}
				objDto.setIsTriggerRequired(rs.getString("IsTriggerRequired"));
				objDto.setLineTriggered(rs.getString("LineTriggered"));
				objDto.setTriggerProcess(rs.getString("TriggerProcess"));
				objDto.setTriggerDoneBy(rs.getString("TriggerDoneBy"));
				objDto.setAutomaticTriggerError(rs.getString("AutomaticTriggerError"));
				//NANCY END

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
	//end	
	Connection connection = null;
	CallableStatement proc = null;
	ResultSet rs = null;
	int recordCount = 0;
	ArrayList<RestPendingLineReportDTO> listSearchDetails = new ArrayList<RestPendingLineReportDTO>();
	RestPendingLineReportDTO objReportsDto = null;
	Utility utility=new Utility();
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	try
	{
		connection=DbConnection.getReportsConnectionObject();
		proc= connection.prepareCall(sqlGetRestPendingLineReports);
		
		
		if (objDto.getFromDate() != null
				&& !"".equals(objDto.getFromDate())) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date dateStr = formatter.parse(objDto.getFromDate());
			String formattedDate = formatter.format(dateStr);
			Date date1 = formatter.parse(objDto.getFromDate());
			formatter = new SimpleDateFormat("MM-dd-yyyy");
			formattedDate = formatter.format(date1);
			proc.setString(1,formattedDate);
		} else {
			proc.setNull(1, java.sql.Types.VARCHAR);
		}
		if (objDto.getToDate() != null 
				&& !"".equals(objDto.getToDate())) {
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
		rs = proc.executeQuery();
		while(rs.next())
		{
			objReportsDto =  new RestPendingLineReportDTO();
			objReportsDto.setAccountID(rs.getInt("ACCOUNTID"));
			objReportsDto.setPartyName(rs.getString("PARTYNAME"));
			objReportsDto.setPartyNo(rs.getInt("PARTY_NO"));
			objReportsDto.setAccountManager(rs.getString("ACTMNAME"));
			objReportsDto.setProjectManager(rs.getString("PMNAME"));
			objReportsDto.setOrderDate(rs.getString("ORDERDATE"));
			if (rs.getString("ORDERDATE") != null && !"".equals(rs.getString("ORDERDATE")))
				{
					objReportsDto.setOrderDate((utility.showDate_Report(new Date(rs.getTimestamp("ORDERDATE").getTime()))).toUpperCase());
				}
				
			objReportsDto.setDispatchAddress1(rs.getString("DISPATCHADDRESS"));
			objReportsDto.setChargeEndDate(rs.getString("CHARGE_START_DATE"));
			if (rs.getString("CHARGE_START_DATE") != null && !"".equals(rs.getString("CHARGE_START_DATE")))
				{
				Date date=df.parse(objReportsDto.getChargeEndDate());
				objReportsDto.setChargeEndDate((utility.showDate_Report(date)).toUpperCase());
				}
			
			 objReportsDto.setPoDate(rs.getString("PODATE"));
			 if (rs.getString("PODATE") != null && !"".equals(rs.getString("PODATE")))
				{
				Date date=df.parse(objReportsDto.getPoDate());
				objReportsDto.setPoDate((utility.showDate_Report(date)).toUpperCase());
				}
			objReportsDto.setAmapprovaldate(rs.getString("AM_APPROVAL_DATE"));
			if (rs.getString("AM_APPROVAL_DATE") != null && !"".equals(rs.getString("AM_APPROVAL_DATE")))
				{
					objReportsDto.setAmapprovaldate((utility.showDate_Report(new Date(rs.getTimestamp("AM_APPROVAL_DATE").getTime()))).toUpperCase());
				}
				
			objReportsDto.setPmapprovaldate(rs.getString("PM_APPROVAL_DATE"));
			if (rs.getString("PM_APPROVAL_DATE") != null && !"".equals(rs.getString("PM_APPROVAL_DATE")))
				{
					objReportsDto.setPmapprovaldate((utility.showDate_Report(new Date(rs.getTimestamp("PM_APPROVAL_DATE").getTime()))).toUpperCase());
				}
			
			objReportsDto.setCopcapprovaldate(rs.getString("COPC_APPROVAL_DATE"));
			if (rs.getString("COPC_APPROVAL_DATE") != null && !"".equals(rs.getString("COPC_APPROVAL_DATE")))
				{
					objReportsDto.setCopcapprovaldate((utility.showDate_Report(new Date(rs.getTimestamp("COPC_APPROVAL_DATE").getTime()))).toUpperCase());
				}	
			
			objReportsDto.setOrderDate(rs.getString("ORDERCREATEDATE"));
			if (rs.getString("ORDERCREATEDATE") != null && !"".equals(rs.getString("ORDERCREATEDATE")))
				{
				Date date=df.parse(objReportsDto.getOrderDate());
				objReportsDto.setOrderDate((utility.showDate_Report(date)).toUpperCase());
				}
			objReportsDto.setCustPoDate(rs.getString("CUSTPODATE"));
			if (rs.getString("CUSTPODATE") != null && !"".equals(rs.getString("CUSTPODATE")))
				{
				Date date=df.parse(objReportsDto.getCustPoDate());
				objReportsDto.setCustPoDate((utility.showDate_Report(date)).toUpperCase());
				}
			objReportsDto.setPoReceiveDate(rs.getString("CUSTPORECDATE"));
			if (rs.getString("CUSTPORECDATE") != null && !"".equals(rs.getString("CUSTPORECDATE")))
				{
				Date date=df.parse(objReportsDto.getPoReceiveDate());
				objReportsDto.setPoReceiveDate((utility.showDate_Report(date)).toUpperCase());
				}
			
			objReportsDto.setCanceldate(rs.getString("CANCELDATE"));
			
			objReportsDto.setRate_code(rs.getString("RATECODE"));
			objReportsDto.setLast_mile_media(rs.getString("LAST_MILE_MEDIA"));
			objReportsDto.setLast_mile_remarks(rs.getString("LAST_MILE_REMARKS"));
			objReportsDto.setLink_type(rs.getString("LINK_TYPE"));
			objReportsDto.setIndicative_value(rs.getString("INDICATIVE_VALUE"));
			objReportsDto.setUom(rs.getString("UOM"));
			objReportsDto.setLast_mile_provider(rs.getString("LAST_MILE_PROVIDER"));
			objReportsDto.setBilling_address(rs.getString("BILLING_LOCATION"));
			objReportsDto.setHardware_flag(rs.getString("HARDWARE_FLAG"));
			objReportsDto.setChargeTypeID(rs.getInt("CHARGE_TYPEID"));
			objReportsDto.setCharge_status(rs.getString("CHARGES_STATUS"));
			objReportsDto.setBillingMode(rs.getString("BILLMODE_NAME"));
			objReportsDto.setFormAvailable(rs.getString("FORM_C_AVAILABLE"));
			objReportsDto.setChargeName(rs.getString("CHARGE_NAME"));
			objReportsDto.setCustPoDetailNo(rs.getString("CUSTPONUMBER"));
			objReportsDto.setPoNumber(rs.getInt("PONUMBER"));
			objReportsDto.setCancelflag(rs.getString("CANCELBY"));
			
			objReportsDto.setPrimarylocation(rs.getString("PRIMARYLOCATION"));
			objReportsDto.setSeclocation(rs.getString("SECONDARYLOCATION"));
			objReportsDto.setAddress1(rs.getString("ADDRESS"));
			objReportsDto.setFx_status(rs.getString("FX_STATUS"));
			objReportsDto.setFx_sd_status(rs.getString("Fx_St_Chg_Status"));
			objReportsDto.setFx_ed_status(rs.getString("Fx_Ed_Chg_Status"));
			objReportsDto.setChild_ac_fxstatus(rs.getString("Child_Account_FX_Sataus"));
			objReportsDto.setRfs_date(rs.getString("SERVICE_RFS_DATE"));
			if (rs.getString("SERVICE_RFS_DATE") != null && !"".equals(rs.getString("SERVICE_RFS_DATE")))
			{
				Date date=df.parse(objReportsDto.getRfs_date());
				objReportsDto.setRfs_date((utility.showDate_Report(date)).toUpperCase());

			}
			objReportsDto.setOpms_act_id(rs.getString("OPMS_ACT_ID"));
			objReportsDto.setProductName(rs.getString("PRODUCTNAME"));
			objReportsDto.setSubProductName(rs.getString("SUBPRODUCTNAME"));
			objReportsDto.setLineno(rs.getInt("LINEITEMNO"));
			objReportsDto.setFrequencyName(rs.getString("FREQUENCY"));
			objReportsDto.setMocn_no(rs.getString("MOCN_NO"));
			objReportsDto.setCrm_productname(rs.getString("CRMPRODUCTNAME"));
			
			
			objReportsDto.setBillingLevelNo(rs.getLong("BILLING_LEVEL_NO"));
			objReportsDto.setLogicalCircuitId(rs.getString("LOGICALCIRCUITID"));
			objReportsDto.setCustomer_logicalSINumber(rs.getInt("CUSTOMER_LOGICAL_SI_NO"));
			objReportsDto.setServiceName(rs.getString("SERVICENAME"));
			objReportsDto.setLinename(rs.getString("LINENAME"));
			objReportsDto.setCkt_id(rs.getString("CKTID"));
			
			
			objReportsDto.setLocation_from(rs.getString("FROM_LOCATION"));
			objReportsDto.setLocation_to(rs.getString("TO_LOCATION"));
			objReportsDto.setServiceStage(rs.getString("SERVICESTAGE"));
			objReportsDto.setBisource(rs.getString("BISOURCE"));
			objReportsDto.setHardwaretypeName(rs.getString("HARDWARETYPENAME"));
			objReportsDto.setTokenno(rs.getString("TOKENNO"));
			objReportsDto.setBillingLevel(rs.getString("BILLING_LEVELNAME"));
			objReportsDto.setSaleNature(rs.getString("SALENATURENAME"));
			objReportsDto.setDemoType(rs.getString("Demo_Type"));
			objReportsDto.setSaleType(rs.getString("SALETYPENAME"));
			objReportsDto.setMocn_no(rs.getString("MOCN_NO"));
			objReportsDto.setOldLsi(rs.getString("OLD_LSI_CRM"));
			objReportsDto.setOpms_Account_Id(rs.getString("OPMS_ACT_ID"));

			objReportsDto.setBilling_bandwidth(rs.getString("BILLING_BANDWIDTH"));
			objReportsDto.setBilling_uom(rs.getString("BILLING_BANDWIDTH_UOM"));
			objReportsDto.setDistance(rs.getString("DISTANCE"));
			objReportsDto.setRatio(rs.getString("RATIO"));
			objReportsDto.setStageName(rs.getString("ORDERSTAGE"));
			objReportsDto.setAnnitation(rs.getString("ANNOTATION"));
			objReportsDto.setChild_act_no(rs.getString("CHILD_AC_NO"));
			objReportsDto.setCancelServiceReason(rs.getString("CANCELREASON"));
			objReportsDto.setChargeTypeName(rs.getString("CHARGE_TYPE"));
			objReportsDto.setOrder_type(rs.getString("ORDERTYPE"));
			objReportsDto.setOrdersubtype(rs.getString("ORDERSUBTYPE"));
			objReportsDto.setRegionName(rs.getString("REGIONNAME"));
			objReportsDto.setVerticalDetails(rs.getString("VERTICAL_DETAILS"));
			objReportsDto.setCreditPeriod(rs.getInt("CREDITPERIOD"));
			objReportsDto.setCurrencyName(rs.getString("CURRENCYNAME"));
			objReportsDto.setEntity(rs.getString("ENTITYNAME"));
			objReportsDto.setBillingTypeName(rs.getString("BILLING_TYPENAME"));
			objReportsDto.setBillingFormatName(rs.getString("BILLING_FORMATNAME"));
			objReportsDto.setLcompanyname(rs.getString("LCOMPANYNAME"));
			objReportsDto.setStorename(rs.getString("STORENAME"));
			objReportsDto.setTaxation(rs.getString("TAXATIONVALUE"));
			objReportsDto.setBill_period(rs.getString("BILL_PERIOD"));
			objReportsDto.setOrderNumber(rs.getInt("ORDERNO"));
			objReportsDto.setChargeAmount(rs.getDouble("INV_AMT"));
			objReportsDto.setLineamt(rs.getLong("LINEITEMAMOUNT"));
			objReportsDto.setPoAmountSum(rs.getLong("ORDERAMOUNT"));
			objReportsDto.setContractPeriod(rs.getInt("CONTRACTPERIOD"));
			objReportsDto.setTotalAmountIncludingCurrent(rs.getDouble("TOTALPOAMOUNT"));
			objReportsDto.setParty_id(rs.getInt("PARTY_ID"));
			objReportsDto.setCrmAccountNo(rs.getInt("CRMACCOUNTNO"));
			objReportsDto.setM6cktid(rs.getString("M6_PRODUCT_ID"));
			objReportsDto.setCust_total_poamt(rs.getDouble("CUST_TOT_PO_AMT"));
			objReportsDto.setServiceId(rs.getInt("SERVICEID"));
			objReportsDto.setServiceproductid(rs.getInt("SERVICEPRODUCTID"));
		    objReportsDto.setBusiness_serial_no(rs.getString("BUSINESS_SERIAL_NO"));
			objReportsDto.setLb_service_id(rs.getString("LB_SERVICE_LIST_ID"));
			objReportsDto.setLb_pk_charge_id(rs.getString("LB_PK_CHARGE_ID"));
			objReportsDto.setChargeinfoID(rs.getString("PK_CHARGE_ID"));
			objReportsDto.setAnnual_rate(rs.getInt("ANNUAL_RATE"));
			objReportsDto.setBandWidth((rs.getString("BANDWIDTH")));
            //[007] Start
		    objReportsDto.setStandardReason(rs.getString("STANDARDREASON"));
			//[007] End
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

/*Vijay start 
 * of Pending Line Report
 */
public ArrayList<ReportsDto> viewPendingLineReport(ReportsDto objDto) 
{
	//	Nagarjuna
	String methodName="viewPendingLineReport", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;	
	//end	
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
		proc= connection.prepareCall(sqlGetPendingLineReports);
		
		
		if (objDto.getFromDate() != null
				&& !"".equals(objDto.getFromDate())) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date dateStr = formatter.parse(objDto.getFromDate());
			String formattedDate = formatter.format(dateStr);
			Date date1 = formatter.parse(objDto.getFromDate());
			formatter = new SimpleDateFormat("MM-dd-yyyy");
			formattedDate = formatter.format(date1);
			proc.setString(1,formattedDate);
		} else {
			proc.setNull(1, java.sql.Types.VARCHAR);
		}
		if (objDto.getToDate() != null 
				&& !"".equals(objDto.getToDate())) {
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
		rs = proc.executeQuery();
		while(rs.next())
		{
			objReportsDto =  new ReportsDto();
			objReportsDto.setAccountID(rs.getInt("ACCOUNTID"));
			objReportsDto.setPartyName(rs.getString("PARTYNAME"));
			objReportsDto.setPartyNo(rs.getInt("PARTY_NO"));
			objReportsDto.setAccountManager(rs.getString("ACTMNAME"));
			objReportsDto.setProjectManager(rs.getString("PMNAME"));
			objReportsDto.setOrderDate(rs.getString("ORDERDATE"));
			if (rs.getString("ORDERDATE") != null && !"".equals(rs.getString("ORDERDATE")))
				{
					objReportsDto.setOrderDate((utility.showDate_Report(new Date(rs.getTimestamp("ORDERDATE").getTime()))).toUpperCase());
				}
				
			objReportsDto.setDispatchAddress1(rs.getString("DISPATCHADDRESS"));
			objReportsDto.setChargeEndDate(rs.getString("CHARGE_START_DATE"));
			if (rs.getString("CHARGE_START_DATE") != null && !"".equals(rs.getString("CHARGE_START_DATE")))
				{
				Date date=df.parse(objReportsDto.getChargeEndDate());
				objReportsDto.setChargeEndDate((utility.showDate_Report(date)).toUpperCase());
				}
			
			 objReportsDto.setPoDate(rs.getString("PODATE"));
			 if (rs.getString("PODATE") != null && !"".equals(rs.getString("PODATE")))
				{
				Date date=df.parse(objReportsDto.getPoDate());
				objReportsDto.setPoDate((utility.showDate_Report(date)).toUpperCase());
				}
			objReportsDto.setAmapprovaldate(rs.getString("AM_APPROVAL_DATE"));
			if (rs.getString("AM_APPROVAL_DATE") != null && !"".equals(rs.getString("AM_APPROVAL_DATE")))
				{
					objReportsDto.setAmapprovaldate((utility.showDate_Report5(new Date(rs.getTimestamp("AM_APPROVAL_DATE").getTime()))).toUpperCase());
				}
				
			objReportsDto.setPmapprovaldate(rs.getString("PM_APPROVAL_DATE"));
			if (rs.getString("PM_APPROVAL_DATE") != null && !"".equals(rs.getString("PM_APPROVAL_DATE")))
				{
					objReportsDto.setPmapprovaldate((utility.showDate_Report5(new Date(rs.getTimestamp("PM_APPROVAL_DATE").getTime()))).toUpperCase());
				}
			
			objReportsDto.setCopcapprovaldate(rs.getString("COPC_APPROVAL_DATE"));
			if (rs.getString("COPC_APPROVAL_DATE") != null && !"".equals(rs.getString("COPC_APPROVAL_DATE")))
				{
					objReportsDto.setCopcapprovaldate((utility.showDate_Report5(new Date(rs.getTimestamp("COPC_APPROVAL_DATE").getTime()))).toUpperCase());
				}	
			
			objReportsDto.setOrderDate(rs.getString("ORDERCREATEDATE"));
			if (rs.getString("ORDERCREATEDATE") != null && !"".equals(rs.getString("ORDERCREATEDATE")))
				{
				Date date=df.parse(objReportsDto.getOrderDate());
				objReportsDto.setOrderDate((utility.showDate_Report(date)).toUpperCase());
				}
			objReportsDto.setCustPoDate(rs.getString("CUSTPODATE"));
			if (rs.getString("CUSTPODATE") != null && !"".equals(rs.getString("CUSTPODATE")))
				{
				Date date=df.parse(objReportsDto.getCustPoDate());
				objReportsDto.setCustPoDate((utility.showDate_Report(date)).toUpperCase());
				}
			objReportsDto.setPoReceiveDate(rs.getString("CUSTPORECDATE"));
			if (rs.getString("CUSTPORECDATE") != null && !"".equals(rs.getString("CUSTPORECDATE")))
				{
				Date date=df.parse(objReportsDto.getPoReceiveDate());
				objReportsDto.setPoReceiveDate((utility.showDate_Report(date)).toUpperCase());
				}
			
			objReportsDto.setCanceldate(rs.getString("CANCELDATE"));
			
			objReportsDto.setRate_code(rs.getString("RATECODE"));
			objReportsDto.setLast_mile_media(rs.getString("LAST_MILE_MEDIA"));
			objReportsDto.setLast_mile_remarks(rs.getString("LAST_MILE_REMARKS"));
			objReportsDto.setLink_type(rs.getString("LINK_TYPE"));
			objReportsDto.setIndicative_value(rs.getString("INDICATIVE_VALUE"));
			objReportsDto.setUom(rs.getString("UOM"));
			objReportsDto.setLast_mile_provider(rs.getString("LAST_MILE_PROVIDER"));
			objReportsDto.setBilling_address(rs.getString("BILLING_LOCATION"));
			objReportsDto.setHardware_flag(rs.getString("HARDWARE_FLAG"));
			objReportsDto.setChargeTypeID(rs.getInt("CHARGE_TYPEID"));
			objReportsDto.setCharge_status(rs.getString("CHARGES_STATUS"));
			objReportsDto.setBillingMode(rs.getString("BILLMODE_NAME"));
			objReportsDto.setFormAvailable(rs.getString("FORM_C_AVAILABLE"));
			objReportsDto.setChargeName(rs.getString("CHARGE_NAME"));
			objReportsDto.setCustPoDetailNo(rs.getString("CUSTPONUMBER"));
			objReportsDto.setPoNumber(rs.getInt("PONUMBER"));
			objReportsDto.setCancelflag(rs.getString("CANCELBY"));
			
			objReportsDto.setPrimarylocation(rs.getString("PRIMARYLOCATION"));
			objReportsDto.setSeclocation(rs.getString("SECONDARYLOCATION"));
			objReportsDto.setAddress1(rs.getString("ADDRESS"));
			objReportsDto.setFx_status(rs.getString("FX_STATUS"));
			objReportsDto.setFx_sd_status(rs.getString("Fx_St_Chg_Status"));
			objReportsDto.setFx_ed_status(rs.getString("Fx_Ed_Chg_Status"));
			objReportsDto.setChild_ac_fxstatus(rs.getString("Child_Account_FX_Sataus"));
			objReportsDto.setRfs_date(rs.getString("SERVICE_RFS_DATE"));
			if (rs.getString("SERVICE_RFS_DATE") != null && !"".equals(rs.getString("SERVICE_RFS_DATE")))
			{
				Date date=df.parse(objReportsDto.getRfs_date());
				objReportsDto.setRfs_date((utility.showDate_Report(date)).toUpperCase());

			}
			objReportsDto.setOpms_act_id(rs.getString("OPMS_ACT_ID"));
			objReportsDto.setProductName(rs.getString("PRODUCTNAME"));
			objReportsDto.setSubProductName(rs.getString("SUBPRODUCTNAME"));
			objReportsDto.setLineno(rs.getInt("LINEITEMNO"));
			objReportsDto.setFrequencyName(rs.getString("FREQUENCY"));
			objReportsDto.setMocn_no(rs.getString("MOCN_NO"));
			objReportsDto.setCrm_productname(rs.getString("CRMPRODUCTNAME"));
			
			
			objReportsDto.setBillingLevelNo(rs.getLong("BILLING_LEVEL_NO"));
			objReportsDto.setLogicalCircuitId(rs.getString("LOGICALCIRCUITID"));
			objReportsDto.setCustomer_logicalSINumber(rs.getInt("CUSTOMER_LOGICAL_SI_NO"));
			objReportsDto.setServiceName(rs.getString("SERVICENAME"));
			objReportsDto.setLinename(rs.getString("LINENAME"));
			objReportsDto.setCkt_id(rs.getString("CKTID"));
			
			
			objReportsDto.setLocation_from(rs.getString("FROM_LOCATION"));
			objReportsDto.setLocation_to(rs.getString("TO_LOCATION"));
			objReportsDto.setServiceStage(rs.getString("SERVICESTAGE"));
			objReportsDto.setBisource(rs.getString("BISOURCE"));
			objReportsDto.setHardwaretypeName(rs.getString("HARDWARETYPENAME"));
			objReportsDto.setTokenno(rs.getString("TOKENNO"));
			objReportsDto.setBillingLevel(rs.getString("BILLING_LEVELNAME"));
			objReportsDto.setSaleNature(rs.getString("SALENATURENAME"));
			objReportsDto.setDemoType(rs.getString("Demo_Type"));
			objReportsDto.setSaleType(rs.getString("SALETYPENAME"));

			objReportsDto.setBilling_bandwidth(rs.getString("BILLING_BANDWIDTH"));
			objReportsDto.setBilling_uom(rs.getString("BILLING_BANDWIDTH_UOM"));
			objReportsDto.setDistance(rs.getString("DISTANCE"));
			objReportsDto.setRatio(rs.getString("RATIO"));
			objReportsDto.setStageName(rs.getString("ORDERSTAGE"));
			objReportsDto.setAnnitation(rs.getString("ANNOTATION"));
			objReportsDto.setChild_act_no(rs.getString("CHILD_AC_NO"));
			objReportsDto.setCancelServiceReason(rs.getString("CANCELREASON"));
			objReportsDto.setChargeTypeName(rs.getString("CHARGE_TYPE"));
			objReportsDto.setOrder_type(rs.getString("ORDERTYPE"));
			objReportsDto.setOrdersubtype(rs.getString("ORDERSUBTYPE"));
			objReportsDto.setRegionName(rs.getString("REGIONNAME"));
			objReportsDto.setOldLsi(rs.getString("OLD_LSI_CRM"));
			objReportsDto.setOpms_Account_Id(rs.getString("OPMS_ACT_ID"));
			objReportsDto.setVerticalDetails(rs.getString("VERTICAL_DETAILS"));
			//objReportsDto.setCreditPeriod(rs.getInt("CREDITPERIOD"));
			//here getting only digit  
			if (rs.getString("CREDIT_PERIODNAME") != null)
					objReportsDto.setCreditPeriod(Integer.parseInt(
							rs.getString("CREDIT_PERIODNAME").replaceAll("[\\D]","")));
			else
				objReportsDto.setCreditPeriod(0);
			
			objReportsDto.setCurrencyName(rs.getString("CURRENCYNAME"));
			objReportsDto.setEntity(rs.getString("ENTITYNAME"));
			objReportsDto.setBillingTypeName(rs.getString("BILLING_TYPENAME"));
			objReportsDto.setBillingFormatName(rs.getString("BILLING_FORMATNAME"));
			objReportsDto.setLcompanyname(rs.getString("LCOMPANYNAME"));
			objReportsDto.setStorename(rs.getString("STORENAME"));
			objReportsDto.setTaxation(rs.getString("TAXATIONVALUE"));
			objReportsDto.setBill_period(rs.getString("BILL_PERIOD"));
			objReportsDto.setOrderNumber(rs.getInt("ORDERNO"));
			objReportsDto.setChargeAmount_String(BigDecimal.valueOf((rs.getDouble("INV_AMT"))).toPlainString());
			objReportsDto.setLineamtString(BigDecimal.valueOf((rs.getDouble("LINEITEMAMOUNT"))).toPlainString());
			objReportsDto.setPoAmountSumString(BigDecimal.valueOf((rs.getDouble("ORDERAMOUNT"))).toPlainString());
			objReportsDto.setContractPeriod(rs.getInt("CONTRACTPERIOD"));
			objReportsDto.setTotalAmountIncludingCurrentString(BigDecimal.valueOf((rs.getDouble("TOTALPOAMOUNT"))).toPlainString());
			objReportsDto.setParty_id(rs.getInt("PARTY_ID"));
			objReportsDto.setCrmAccountNo(rs.getInt("CRMACCOUNTNO"));
			objReportsDto.setM6cktid(rs.getString("M6_PRODUCT_ID"));
			objReportsDto.setCust_total_poamtString(BigDecimal.valueOf((rs.getDouble("CUST_TOT_PO_AMT"))).toPlainString());
			objReportsDto.setServiceId(rs.getInt("SERVICEID"));
			objReportsDto.setServiceproductid(rs.getInt("SERVICEPRODUCTID"));
		    objReportsDto.setBusiness_serial_no(rs.getString("BUSINESS_SERIAL_NO"));
			objReportsDto.setLb_service_id(rs.getString("LB_SERVICE_LIST_ID"));
			objReportsDto.setLb_pk_charge_id(rs.getString("LB_PK_CHARGE_ID"));
			objReportsDto.setChargeinfoID(rs.getString("PK_CHARGE_ID"));
			objReportsDto.setAnnual_rateString(BigDecimal.valueOf((rs.getDouble("ANNUAL_RATE"))).toPlainString());
			
			
			
			objReportsDto.setBilling_trigger_date(rs.getString("BILLINGTRIGGERDATE"));
			if (rs.getString("BILLINGTRIGGERDATE") != null && !"".equals(rs.getString("BILLINGTRIGGERDATE")))
			{
				Date date=df.parse(objReportsDto.getBilling_trigger_date());
				objReportsDto.setBilling_trigger_date((utility.showDate_Report(date)).toUpperCase());
			}
			objReportsDto.setBillingtrigger_createdate(rs.getString("BILLING_TRIGGER_CREATEDATE"));
			if (rs.getString("BILLING_TRIGGER_CREATEDATE") != null && !"".equals(rs.getString("BILLING_TRIGGER_CREATEDATE")))
			{
				objReportsDto.setBillingtrigger_createdate((utility.showDate_Report(new Date(rs.getTimestamp("BILLING_TRIGGER_CREATEDATE").getTime()))).toUpperCase());
			}
			objReportsDto.setBilling_Trigger_Flag(rs.getString("BT_FLAG"));
			objReportsDto.setLocDate(rs.getString("LOCDATE"));
			objReportsDto.setLocno(rs.getString("LOCNO"));
			objReportsDto.setChallenno(rs.getString("CHALLEN_NO"));
			objReportsDto.setChallendate(rs.getString("CHALLEN_DATE"));
			if(!(rs.getString("CHALLEN_DATE")==null || rs.getString("CHALLEN_DATE")==""))
			{
				objDto.setChallendate(rs.getString("CHALLEN_DATE"));
				if (rs.getString("CHALLEN_DATE") != null && !"".equals(rs.getString("CHALLEN_DATE")))
				{
				  String s1=rs.getString("CHALLEN_DATE");
				  if(s1.length()==10){
					  s1="0"+s1;
				  }
	  
				  String s3=s1.substring(0,7).toUpperCase();
				  String s4=s1.substring(9,11);
				  String s5=s3.concat(s4);
				  objDto.setChallendate(s5);
				}
			}
						
			/*Vijay add few more columns*/
			objReportsDto.setAsiteAdd1(rs.getString("ASITE_ADD1"));
			objReportsDto.setAsiteAdd2(rs.getString("ASITE_ADD2"));
			objReportsDto.setAsiteAdd3(rs.getString("ASITE_ADD3"));
			objReportsDto.setBsiteName(rs.getString("BSITE_NAME"));
			objReportsDto.setBsiteLineAdd1(rs.getString("BSITE_ADD1"));
			objReportsDto.setBsiteLineAdd2(rs.getString("BSITE_ADD2"));
			//vijay end
			
			objReportsDto.setBandwaidth(rs.getString("BANDWIDTH"));
			objReportsDto.setTaxExcemption_Reason(rs.getString("TAXEXCEMPTION_REASON"));
			//[505054] Start
			objReportsDto.setServiceSegment(rs.getString("SERVICESEGMENT"));
			//[505054] End
               
			//[004] Start
			objReportsDto.setInstallationFromCity(rs.getString("INSTALLATION_FROM_CITY"));
			objReportsDto.setInstallationToCity(rs.getString("INSTALLATION_TO_CITY"));
			objReportsDto.setInstallationFromState(rs.getString("INSTALLATION_FROM_STATE"));
			objReportsDto.setInstallationToState(rs.getString("INSTALLATION_TO_STATE"));
			objReportsDto.setBillingContactName(rs.getString("BILLING_CONTACT_NAME"));
			objReportsDto.setBillingContactNumber(rs.getString("BILLING_CONTACT_NUMBER"));
			objReportsDto.setBillingEmailId(rs.getString("BILLING_EMAIL_ID"));
			//[004] End
            //[008] Start
				objReportsDto.setOrderCreationDate(rs.getString("CREATED_DATE"));
				if (rs.getString("CREATED_DATE") != null && !"".equals(rs.getString("CREATED_DATE")))
				{
					objReportsDto.setOrderCreationDate((utility.showDate_Report5(new Date(rs.getTimestamp("CREATED_DATE").getTime()))).toUpperCase());
				}
				objReportsDto.setPublishedDate(rs.getString("PUBLISHED_DATE"));
				if (rs.getString("PUBLISHED_DATE") != null && !"".equals(rs.getString("PUBLISHED_DATE")))
				{
					objReportsDto.setPublishedDate((utility.showDate_Report5(new Date(rs.getTimestamp("PUBLISHED_DATE").getTime()))).toUpperCase());
				}
				
				//[008] End
				//[131] start
				objReportsDto.setChannelPartner(rs.getString("PARTNER_NAME"));
				objReportsDto.setPartnerCode(rs.getString("PARTNER_CODE"));
				objReportsDto.setFieldEngineer(rs.getString("FIELD_ENGINEER"));
			    //[131] end
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

/*Vijay end
 * of Pending Line Report */

public ArrayList<DisconnectLineReportDTO> viewDisconnectionLineReport(DisconnectLineReportDTO objDto) 
{
	//	Nagarjuna
	String methodName="viewDisconnectionLineReport", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;	
	//end	
	Connection connection = null;
	CallableStatement proc = null;
	ResultSet rs = null;
	int recordCount = 0;
	ArrayList<DisconnectLineReportDTO> listSearchDetails = new ArrayList<DisconnectLineReportDTO>();
	DisconnectLineReportDTO objReportsDto = null;
	Utility utility=new Utility();
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	try
	{
		connection=DbConnection.getReportsConnectionObject();
		proc= connection.prepareCall(sqlGetDisconnectionLineReport);

		/*if (objDto.getOrdermonth() != null
				&& !"".equals(objDto.getOrdermonth())) {
			proc.setString(1, objDto.getOrdermonth().trim());
		} else {
			proc.setNull(1, java.sql.Types.VARCHAR);
		}
		
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
		System.out.println("Vijay 2");
		if (objDto.getOrdersubtype() != null 
				&& !"".equals(objDto.getOrdersubtype())) {
			proc.setString(4, objDto.getOrdersubtype().trim());
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
		System.out.println("Vijay 3");
		proc.setString(3, pagingSorting.getSortByColumn());// columnName
		
		System.out.println("Vijay sort column name :"+pagingSorting.getSortByColumn());
		
		proc.setString(4, PagingSorting.DB_Asc_Desc(pagingSorting
				.getSortByOrder()));// sort order
		proc.setInt(5, pagingSorting.getStartRecordId());// start index
		 proc.setInt(6, pagingSorting.getEndRecordId());// end index
		proc.setInt(7, (pagingSorting.isPagingToBeDone() ? 1 : 0));*/
		
		
		if (objDto.getOrderNo() != null 
				&& !"".equals(objDto.getOrderNo())) {
			proc.setInt(1, new Integer(objDto.getOrderNo()));
		} else {
			proc.setNull(1, java.sql.Types.BIGINT);
		}
		//[303030]START
		if (objDto.getFromDate() != null && !"".equals(objDto.getFromDate()) && objDto.getToDate() != null && !"".equals(objDto.getToDate()) ) {
			//proc.setString(2, objDto.getCopcApproveDate());
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");	
			Date dateStr = formatter.parse(objDto.getFromDate());
			String formattedDate = formatter.format(dateStr);
			Date date1 = formatter.parse(objDto.getFromDate());
			formatter = new SimpleDateFormat("MM-dd-yyyy");
			formattedDate = formatter.format(date1);
			proc.setString(2,formattedDate);
					
			//proc.setString(2, objDto.getFromDate());
			
			formatter = new SimpleDateFormat("dd/MM/yyyy");	
			dateStr = formatter.parse(objDto.getToDate());
			formattedDate = formatter.format(dateStr);
			date1 = formatter.parse(objDto.getToDate());
			formatter = new SimpleDateFormat("MM-dd-yyyy");
			formattedDate = formatter.format(date1);
			proc.setString(3,formattedDate);
			
			///proc.setString(3, objDto.getToDate());
			
		} else {
			proc.setNull(2, java.sql.Types.VARCHAR);
			proc.setNull(3, java.sql.Types.VARCHAR);
		}
		//[303030]END
		PagingSorting pagingSorting = objDto.getPagingSorting();
		pagingSorting.sync();// To calculate start index and Enc Index

		proc.setString(4, pagingSorting.getSortByColumn());// columnName
		proc.setString(5, PagingSorting.DB_Asc_Desc(pagingSorting
				.getSortByOrder()));// sort order
		proc.setInt(6, pagingSorting.getStartRecordId());// start index
		proc.setInt(7, pagingSorting.getEndRecordId());// end index
		proc.setInt(8, (pagingSorting.isPagingToBeDone() ? 1 : 0));
		
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
			objReportsDto.setPrimarylocation(rs.getString("PRIMARYLOCATION"));
			objReportsDto.setSeclocation(rs.getString("SECONDARYLOCATION"));
			objReportsDto.setProductName(rs.getString("PRODUCTNAME"));
			objReportsDto.setSubProductName(rs.getString("SUBPRODUCTNAME"));
			objReportsDto.setEntity(rs.getString("ENTITYNAME"));
			objReportsDto.setLcompanyname(rs.getString("LCOMPANYNAME"));
			objReportsDto.setCurrencyName(rs.getString("CURRENCYNAME"));
			objReportsDto.setCreditPeriod(rs.getInt("CREDITPERIOD"));
			objReportsDto.setBillingTypeName(rs.getString("BILLING_TYPENAME"));
			objReportsDto.setBillingFormatName(rs.getString("BILLING_FORMATNAME"));
			objReportsDto.setBillingLevel(rs.getString("BILLING_LEVELNAME"));
			objReportsDto.setBillingLevelNo(rs.getLong("BILLING_LEVEL_NO"));
			objReportsDto.setTaxation(rs.getString("TAXATIONVALUE"));
			objReportsDto.setHardwaretypeName(rs.getString("HARDWARETYPENAME"));
			objReportsDto.setHardware_flag(rs.getString("HARDWARE_FLAG"));
			objReportsDto.setStorename(rs.getString("STORENAME"));
			objReportsDto.setSaleType(rs.getString("SALETYPE"));
			objReportsDto.setBill_period(rs.getString("BILL_PERIOD"));
			objReportsDto.setBillingMode(rs.getString("BILLINGMODE"));
			objReportsDto.setPoNumber(rs.getInt("PONUMBER"));
			objReportsDto.setOldLsi(rs.getString("OLD_LSI_CRM"));
			objReportsDto.setBusiness_serial_n(rs.getInt("BUSINESS_SERIAL_NO"));
			objReportsDto.setOpms_Account_Id(rs.getString("OPMS_ACCOUNT_ID"));
			objReportsDto.setPoDate(rs.getString("PODATE"));
			if (rs.getString("PODATE") != null && !"".equals(rs.getString("PODATE")))
			{
				Date date=df.parse(objReportsDto.getPoDate());
				objReportsDto.setPoDate((utility.showDate_Report(date)).toUpperCase());
			}
			objReportsDto.setContractPeriod(rs.getInt("CONTRACTPERIOD"));
			objReportsDto.setTotalAmountIncludingCurrent(rs.getDouble("TOTALPOAMOUNT"));
			objReportsDto.setPoReceiveDate(rs.getString("PORECEIVEDATE"));
			if (rs.getString("PORECEIVEDATE") != null && !"".equals(rs.getString("PORECEIVEDATE")))
			{
				Date date=df.parse(objReportsDto.getPoReceiveDate());
				objReportsDto.setPoReceiveDate((utility.showDate_Report(date)).toUpperCase());
			}
			objReportsDto.setCustPoDetailNo(rs.getString("CUSTPONUMBER"));
			objReportsDto.setCustPoDate(rs.getString("CUSTPODATE"));
			if (rs.getString("CUSTPODATE") != null && !"".equals(rs.getString("CUSTPODATE")))
			{
				Date date=df.parse(objReportsDto.getCustPoDate());
				objReportsDto.setCustPoDate((utility.showDate_Report(date)).toUpperCase());
			}
			objReportsDto.setCust_total_poamt(rs.getDouble("CUST_TOT_PO_AMT"));		
			objReportsDto.setChargeTypeName(rs.getString("CHARGE_TYPE"));
			objReportsDto.setChargeName(rs.getString("CHARGE_NAME"));
			objReportsDto.setChargeEndDate(rs.getString("CSTATE_CHARGE_CURRENT_START_DATE"));	
			if (rs.getString("CSTATE_CHARGE_CURRENT_START_DATE") != null && !"".equals(rs.getString("CSTATE_CHARGE_CURRENT_START_DATE")))
			{
				Date date=df.parse(objReportsDto.getChargeEndDate());
				objReportsDto.setChargeEndDate((utility.showDate_Report(date)).toUpperCase());
			}
			//lawkush Start
			objReportsDto.setFrequencyAmount(rs.getDouble("INV_AMT"));					
			objReportsDto.setChargeAmount(rs.getDouble("CUST_TOT_PO_AMT"));	
			//lawkush End
		//	objReportsDto.setLineamt(rs.getLong("LINEITEMAMOUNT"));		//do
			objReportsDto.setLineamt(0);	
			
			objReportsDto.setAnnitation(rs.getString("ANNOTATION"));
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
			objReportsDto.setBillingtrigger_createdate(rs.getString("BILLING_TRIGGER_CREATEDATE"));
			if (rs.getString("BILLING_TRIGGER_CREATEDATE") != null && !"".equals(rs.getString("BILLING_TRIGGER_CREATEDATE")))
			{
				objReportsDto.setBillingtrigger_createdate((utility.showDate_Report(new Date(rs.getTimestamp("BILLING_TRIGGER_CREATEDATE").getTime()))).toUpperCase());
			}
			objReportsDto.setPmapprovaldate(rs.getString("PM_PROV_DATE"));
			if (rs.getString("Pm_Prov_Date") != null && !"".equals(rs.getString("Pm_Prov_Date")))
			{
				  String s1=rs.getString("Pm_Prov_Date");
				  String s3=s1.substring(0,7);
				  String s4=s1.substring(9,11);
				  String s5=s3.concat(s4);
				  objReportsDto.setPmApproveDate(s5);
			}
			objReportsDto.setBilling_Trigger_Flag(rs.getString("BILLING_TRIGGER_FLAG"));				
			
			objReportsDto.setTokenno(rs.getString("CSTATE_START_DETAILS_FX_TOKEN_NO"));
			objReportsDto.setFx_status(rs.getString("CSTATE_START_DETAILS_FX_STATUS"));		
			objReportsDto.setFx_sd_status(rs.getString("CSTATE_FX_CHARGE_START_STATUS"));		
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
			//objReportsDto.setRequest_rec_date(rs.getString("DISCONNECTION_RECEIVE_DATE"));
			
			objReportsDto.setStandard_reason(rs.getString("STANDARDREASON"));
			objReportsDto.setOrderDate(rs.getString("ORDERDATE"));
			if (rs.getString("ORDERDATE") != null && !"".equals(rs.getString("ORDERDATE")))
			{
				objReportsDto.setOrderDate((utility.showDate_Report(new Date(rs.getTimestamp("ORDERDATE").getTime()))).toUpperCase());
			}
			objReportsDto.setRatio(rs.getString("RATIO"));
			objReportsDto.setChild_account_number(rs.getString("CHILD_ACCOUNT_NUMBER"));
			objReportsDto.setOrdermonth(rs.getString("ORDER_MONTH"));
			objReportsDto.setCkt_id(rs.getString("CKTID"));
			
			
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
	//end
	Connection connection = null;
	CallableStatement proc = null;
	ResultSet rs = null;
	int recordCount = 0;
	ArrayList<CancelledFailedLineReportDTO> listSearchDetails = new ArrayList<CancelledFailedLineReportDTO>();
	CancelledFailedLineReportDTO objReportsDto = null;
	Utility utility=new Utility();
	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat formatter2 = new SimpleDateFormat("MM-dd-yyyy");
	
	try
	{
		connection=DbConnection.getReportsConnectionObject();
		proc= connection.prepareCall(sqlGetCancelledFailedLineReports);
		
		
		
		if (objDto.getFromDate() != null
				&& !"".equals(objDto.getFromDate())) {
			Date dateStr = formatter.parse(objDto.getFromDate().trim());
 			String formattedDate = formatter.format(dateStr);
 			Date date1 = formatter.parse(formattedDate);
 			formattedDate = formatter2.format(date1);
 			proc.setString(1, formattedDate);
			
		} else {
			proc.setNull(1, java.sql.Types.VARCHAR);
		}
		
		if (objDto.getToDate() != null 
				&& !"".equals(objDto.getToDate())) {
			Date dateStr = formatter.parse(objDto.getToDate().trim());
 			String formattedDate = formatter.format(dateStr);
 			Date date1 = formatter.parse(formattedDate);
 			formattedDate = formatter2.format(date1);
 			proc.setString(2, formattedDate);
			
		} else {
			proc.setNull(2, java.sql.Types.VARCHAR);
		}
		
		if (objDto.getServiceName() != null 
				&& !"".equals(objDto.getServiceName())) {
			proc.setString(3, objDto.getServiceName().trim().toUpperCase());
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
		// index
		rs = proc.executeQuery();
		while(rs.next())
		{
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
			objReportsDto.setPrimarylocation(rs.getString("PRIMARYLOCATION"));
			objReportsDto.setSeclocation(rs.getString("SECONDARYLOCATION"));
			objReportsDto.setProductName(rs.getString("PRODUCTNAME"));
			objReportsDto.setSubProductName(rs.getString("SUBPRODUCTNAME"));
			objReportsDto.setEntity(rs.getString("ENTITYNAME"));
			objReportsDto.setLcompanyname(rs.getString("LCOMPANYNAME"));
			objReportsDto.setCurrencyName(rs.getString("CURRENCYNAME"));
			objReportsDto.setCreditPeriodName(rs.getString("CREDITPERIOD"));
			objReportsDto.setBillingTypeName(rs.getString("BILLING_TYPENAME"));
			objReportsDto.setBillingFormatName(rs.getString("BILLING_FORMATNAME"));
			objReportsDto.setBillingLevel(rs.getString("BILLING_LEVELNAME"));
			objReportsDto.setBillingLevelNo(rs.getLong("BILLING_LEVEL_NO"));
			objReportsDto.setTaxation(rs.getString("TAXATIONVALUE"));
			objReportsDto.setHardwaretypeName(rs.getString("HARDWARETYPENAME"));
			objReportsDto.setHardware_flag(rs.getString("HARDWARE_FLAG"));
			objReportsDto.setStorename(rs.getString("STORENAME"));
			objReportsDto.setSaleType(rs.getString("SALETYPE"));
			objReportsDto.setBill_period(rs.getString("BILL_PERIOD"));
			objReportsDto.setBillingMode(rs.getString("BILLINGMODE"));
			objReportsDto.setPoNumber(rs.getInt("PONUMBER"));
			objReportsDto.setOldLsi(rs.getString("OLD_LSI_CRM"));
			objReportsDto.setBusiness_serial_n(rs.getInt("BUSINESS_SERIAL_NO"));
			objReportsDto.setOpms_Account_Id(rs.getString("OPMS_ACCOUNT_ID"));
			objReportsDto.setPoDate(rs.getString("PODATE"));
			if (rs.getString("PODATE") != null && !"".equals(rs.getString("PODATE")))
			{
				Date date=df.parse(objReportsDto.getPoDate());
				objReportsDto.setPoDate((utility.showDate_Report(date)).toUpperCase());
			}
			objReportsDto.setContractPeriod(rs.getInt("CONTRACTPERIOD"));
			objReportsDto.setTotalPoAmt(BigDecimal.valueOf((rs.getDouble("TOTALPOAMOUNT"))).toPlainString());   
			
			if(objReportsDto.getTotalPoAmt()==null){
				objReportsDto.setTotalPoAmt(" ");
			}
			objReportsDto.setPoReceiveDate(rs.getString("PORECEIVEDATE"));
			if (rs.getString("PORECEIVEDATE") != null && !"".equals(rs.getString("PORECEIVEDATE")))
			{
				Date date=df.parse(objReportsDto.getPoReceiveDate());
				objReportsDto.setPoReceiveDate((utility.showDate_Report(date)).toUpperCase());
			}
			objReportsDto.setCustPoDetailNo(rs.getString("CUSTPONUMBER"));
			objReportsDto.setCustPoDate(rs.getString("CUSTPODATE"));
			if (rs.getString("CUSTPODATE") != null && !"".equals(rs.getString("CUSTPODATE")))
			{
				Date date=df.parse(objReportsDto.getCustPoDate());
				objReportsDto.setCustPoDate((utility.showDate_Report(date)).toUpperCase());
			}
			//objReportsDto.setCust_total_poamt(rs.getDouble("CUST_TOT_PO_AMT"));
			objReportsDto.setChargeTypeName(rs.getString("CHARGE_TYPE"));
			objReportsDto.setChargeName(rs.getString("CHARGE_NAME"));
			objReportsDto.setChargeEndDate(rs.getString("CSTATE_CHARGE_CURRENT_START_DATE"));
			//objReportsDto.setChargeAmount(rs.getDouble("INV_AMT"));
			objReportsDto.setFrequencyAmt(BigDecimal.valueOf((rs.getDouble("CHARGEFREQUENCYAMT"))).toPlainString()); 
			
			
			if(objReportsDto.getFrequencyAmt()==null){
				objReportsDto.setFrequencyAmt(" ");
			}
			//objReportsDto.setLineamt(rs.getLong("LINEITEMAMOUNT"));
			//objReportsDto.setAmt(rs.getLong("CHARGEAMOUNT"));//particular charge amount
			objReportsDto.setLineItemAmount(BigDecimal.valueOf((rs.getDouble("CHARGEAMOUNT"))).toPlainString()); 
			
			if(objReportsDto.getLineItemAmount()==null){
				objReportsDto.setLineItemAmount(" ");
			}
			objReportsDto.setAnnitation(rs.getString("ANNOTATION"));
			objReportsDto.setTokenno(rs.getString("CSTATE_START_DETAILS_FX_TOKEN_NO"));
			objReportsDto.setFx_status(rs.getString("CSTATE_START_DETAILS_FX_STATUS"));
			objReportsDto.setFx_sd_status(rs.getString("CSTATE_FX_CHARGE_START_STATUS"));
			objReportsDto.setOrderDate(rs.getString("ORDERDATE"));
			if(!(rs.getString("ORDERDATE")==null || rs.getString("ORDERDATE")==""))
			{
				objReportsDto.setOrderDate((utility.showDate_Report(new Date(rs.getTimestamp("ORDERDATE").getTime()))).toUpperCase());
			}
			
			//objReportsDto.setBusiness_serial_no(rs.getString("Business_No"));
			//objReportsDto.setOpms_act_id(rs.getString("Opms_Account_Id"));
			objReportsDto.setLineno(rs.getInt("ORDER_LINE_ID"));
			
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
		//	e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return listSearchDetails;	
}




public ArrayList<BulkSIUploadReportDto> viewBulkSIUploadReport(BulkSIUploadReportDto objDto) 
{
	//	Nagarjuna
	String methodName="viewBulkSIUploadReport", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;	
	//end
	Connection connection = null;
	CallableStatement proc = null;
	ResultSet rs = null;
	int recordCount = 0;
	ArrayList<BulkSIUploadReportDto> listSearchDetails = new ArrayList<BulkSIUploadReportDto>();
	BulkSIUploadReportDto objReportsDto = null;
	Utility utility=new Utility();
	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	try
	{
		connection=DbConnection.getReportsConnectionObject();
		proc= connection.prepareCall(sqlGetBulkUploadReports);
		
		/*if (objDto.getDate_of_inst_from() != null
				&& !"".equals(objDto.getDate_of_inst_from())) {
			proc.setString(1, objDto.getDate_of_inst_from().trim());
		} else {
			proc.setNull(1, java.sql.Types.VARCHAR);
		}
		
		if (objDto.getDate_of_inst_to() != null
				&& !"".equals(objDto.getDate_of_inst_to())) {
			proc.setString(2, objDto.getDate_of_inst_to().trim());
		} else {
			proc.setNull(2, java.sql.Types.VARCHAR);
		}*/
		
			if (objDto.getDate_of_installation_from() != null
				&& !"".equals(objDto.getDate_of_installation_from())) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date dateStr = formatter.parse(objDto.getDate_of_installation_from());
			String formattedDate = formatter.format(dateStr);
			Date date1 = formatter.parse(formattedDate);
			formatter = new SimpleDateFormat("MM-dd-yyyy");
			formattedDate = formatter.format(date1);
			proc.setString(1,formattedDate);
		} else {
			proc.setNull(1, java.sql.Types.VARCHAR);
		}
		if (objDto.getDate_of_installation_to() != null 
				&& !"".equals(objDto.getDate_of_installation_to())) {
			SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
			Date dateStr1 = formatter1.parse(objDto.getDate_of_installation_to());
			String formattedDate1 = formatter1.format(dateStr1);
			Date date2 = formatter1.parse(formattedDate1);
			formatter1 = new SimpleDateFormat("MM-dd-yyyy");
			formattedDate1 = formatter1.format(date2);
			proc.setString(2,formattedDate1);
		} else {
			proc.setNull(2, java.sql.Types.VARCHAR);
		}
		
		if (objDto.getServiceName() != null
				&& !"".equals(objDto.getServiceName())) {
			proc.setString(3, objDto.getServiceName().trim());
		} else {
			proc.setNull(3, java.sql.Types.VARCHAR);
		}
		
		
		if (objDto.getParty_no() != 0 
				&& !"".equals(objDto.getParty_no())) {
			proc.setInt(4, objDto.getParty_no());
		} else {
			proc.setNull(4,java.sql.Types.BIGINT);
		}
		
		if (objDto.getOrderNo() != null
				&& !"".equals(objDto.getOrderNo())) {
			proc.setString(5, objDto.getOrderNo().trim());
		} else {
			proc.setNull(5, java.sql.Types.VARCHAR);
		}
		
		
		if (objDto.getLogicalSINo() != null
				&& !"".equals(objDto.getLogicalSINo())) {
			proc.setString(6, objDto.getLogicalSINo().trim());
		} else {
			proc.setNull(6, java.sql.Types.VARCHAR);
		}
		
		if (objDto.getLinename() != null
				&& !"".equals(objDto.getLinename())) {
			proc.setString(7, objDto.getLinename().trim());
		} else {
			proc.setNull(7, java.sql.Types.VARCHAR);
		}
		
	

		PagingSorting pagingSorting = objDto.getPagingSorting();
		pagingSorting.sync();// To calculate start index and Enc Index

		proc.setString(8, pagingSorting.getSortByColumn());// columnName
		proc.setString(9, PagingSorting.DB_Asc_Desc(pagingSorting
				.getSortByOrder()));// sort order
		proc.setInt(10, pagingSorting.getStartRecordId());// start index
		 proc.setInt(11, pagingSorting.getEndRecordId());// end index
		proc.setInt(12, (pagingSorting.isPagingToBeDone() ? 1 : 0));
		// index
		rs = proc.executeQuery();
		while(rs.next())
		{
			objReportsDto =  new BulkSIUploadReportDto();
			objReportsDto.setAccountName(rs.getString("ACCOUNTNAME"));
			objReportsDto.setOrderNumber(rs.getInt("CRM_ORDER_MOCN_NO"));
			objReportsDto.setCrm_service_opms_id(rs.getString("CRM_SERVICE_OPMS_ID"));
			objReportsDto.setServiceName(rs.getString("SERVICETYPE"));
			
//			 get city,state,country
			
			if (rs.getString("FROM_CITY") != null && !"".equals(rs.getString("FROM_CITY")))
			{
				if(Integer.parseInt(rs.getString("PRIMARYLOCATIONTYPE"))==1){
					String ss[] =rs.getString("FROM_CITY").split("~~");
					objReportsDto.setFrom_city(ss[8]);
				}
				else{
					objReportsDto.setFrom_city(" ");
				}
				//objDto.setFrom_city(rs.getString("FROM_CITY"));
			}
			else
			{
				objReportsDto.setFrom_city("");
			}
			if (rs.getString("TO_CITY") != null && !"".equals(rs.getString("TO_CITY")))
			{
				if(Integer.parseInt(rs.getString("SECONDARYLOCATIONTYPE"))==1){
					String ss[] =rs.getString("TO_CITY").split("~~");
					objReportsDto.setTo_city(ss[8]);
				}
				else{
					objReportsDto.setTo_city(" ");
				}				
				//objDto.setTo_city(rs.getString("TO_CITY"));
			}
			else
			{
				objReportsDto.setTo_city("");
			}
			
			
			if (rs.getString("FROM_CITY") != null && !"".equals(rs.getString("FROM_CITY")))
			{
				if(Integer.parseInt(rs.getString("PRIMARYLOCATIONTYPE"))==1){
					String ss[] =rs.getString("FROM_CITY").split("~~");
					objReportsDto.setFrom_state(ss[9]);
				}
				else{
					objReportsDto.setFrom_state(" ");
				}
				//objDto.setFrom_city(rs.getString("FROM_CITY"));
			}
			else
			{
				objReportsDto.setFrom_state("");
			}
			if (rs.getString("TO_CITY") != null && !"".equals(rs.getString("TO_CITY")))
			{
				if(Integer.parseInt(rs.getString("SECONDARYLOCATIONTYPE"))==1){
					String ss[] =rs.getString("TO_CITY").split("~~");
					objReportsDto.setTo_state(ss[9]);
				}
				else{
					objReportsDto.setTo_state(" ");
				}				
				//objDto.setTo_city(rs.getString("TO_CITY"));
			}
			else
			{
				objReportsDto.setTo_state("");
			}
			
			if (rs.getString("FROM_CITY") != null && !"".equals(rs.getString("FROM_CITY")))
			{
				if(Integer.parseInt(rs.getString("PRIMARYLOCATIONTYPE"))==1){
					String ss[] =rs.getString("FROM_CITY").split("~~");
					objReportsDto.setFrom_country(ss[10]);
				}
				else{
					objReportsDto.setFrom_country(" ");
				}
				//objDto.setFrom_city(rs.getString("FROM_CITY"));
			}
			else
			{
				objReportsDto.setFrom_country("");
			}
			if (rs.getString("TO_CITY") != null && !"".equals(rs.getString("TO_CITY")))
			{
				if(Integer.parseInt(rs.getString("SECONDARYLOCATIONTYPE"))==1){
					String ss[] =rs.getString("TO_CITY").split("~~");
					objReportsDto.setTo_country(ss[10]);
				}
				else{
					objReportsDto.setTo_country(" ");
				}				
				//objDto.setTo_city(rs.getString("TO_CITY"));
			}
			else
			{
				objReportsDto.setTo_country("");
			}
			
//			 get city,state,country end
			
			
			// get addresses start
			
			if (rs.getString("PRIMARYLOCATION") != null && !"".equals(rs.getString("PRIMARYLOCATION")))
			{
				if(Integer.parseInt(rs.getString("PRIMARYLOCATIONTYPE"))==1){
					String ss[] =rs.getString("PRIMARYLOCATION").split("~~");
					objReportsDto.setInstallation_addressaa1(ss[4]);
				}
				else{
					String ss[] =rs.getString("PRIMARYLOCATION").split("~~");
					objReportsDto.setInstallation_addressaa1(ss[5]);
				}
			}
			else
			{
				objReportsDto.setInstallation_addressaa1("");
			}
			
			if (rs.getString("SECONDARYLOCATION") != null && !"".equals(rs.getString("SECONDARYLOCATION")))
			{
				if(Integer.parseInt(rs.getString("SECONDARYLOCATIONTYPE"))==1){
					String ss[] =rs.getString("SECONDARYLOCATION").split("~~");
					objReportsDto.setInstallation_addressab1(ss[4]);
				}
				else{
					String ss[] =rs.getString("SECONDARYLOCATION").split("~~");
					objReportsDto.setInstallation_addressab1(ss[5]);
				}
			}
			else
			{
				objReportsDto.setInstallation_addressab1("");
			}
			
			if (rs.getString("PRIMARYLOCATION") != null && !"".equals(rs.getString("PRIMARYLOCATION")))
			{
				if(Integer.parseInt(rs.getString("PRIMARYLOCATIONTYPE"))==1){
					String ss[] =rs.getString("PRIMARYLOCATION").split("~~");
					objReportsDto.setInstallation_addressaa2(ss[5]);
				}
				else{
					objReportsDto.setInstallation_addressaa2("");
				}
			}
			else
			{
				objReportsDto.setInstallation_addressaa2("");
			}
			
			if (rs.getString("PRIMARYLOCATION") != null && !"".equals(rs.getString("PRIMARYLOCATION")))
			{
				if(Integer.parseInt(rs.getString("PRIMARYLOCATIONTYPE"))==1){
					String ss[] =rs.getString("PRIMARYLOCATION").split("~~");
					objReportsDto.setInstallation_addressaa3(ss[6]);
				}
				else{
					objReportsDto.setInstallation_addressaa3("");
				}
			}
			else
			{
				objReportsDto.setInstallation_addressaa3("");
			}
			
			if (rs.getString("SECONDARYLOCATION") != null && !"".equals(rs.getString("SECONDARYLOCATION")))
			{
				if(Integer.parseInt(rs.getString("SECONDARYLOCATIONTYPE"))==1){
					String ss[] =rs.getString("SECONDARYLOCATION").split("~~");
					objReportsDto.setInstallation_addressab2(ss[5]);
				}
				else{
					objReportsDto.setInstallation_addressab2("");
				}
			}
			else
			{
				objReportsDto.setInstallation_addressab2("");
			}
			
			if (rs.getString("SECONDARYLOCATION") != null && !"".equals(rs.getString("SECONDARYLOCATION")))
			{
				if(Integer.parseInt(rs.getString("SECONDARYLOCATIONTYPE"))==1){
					String ss[] =rs.getString("SECONDARYLOCATION").split("~~");
					objReportsDto.setInstallation_addressab3(ss[6]);
				}
				else{
					objReportsDto.setInstallation_addressab3("");
				}
			}
			else
			{
				objReportsDto.setInstallation_addressab3("");
			}
			
			objReportsDto.setDate_of_inst(rs.getString("DATE_OF_INST")); //left
			objReportsDto.setDate_of_act(rs.getString("DATE_OF_ACT"));
			if (rs.getString("DATE_OF_ACT") != null && !"".equals(rs.getString("DATE_OF_ACT")))
			{
			Date date=df.parse(objReportsDto.getDate_of_act());
			objReportsDto.setDate_of_act((utility.showDate_Report(date)).toUpperCase());
			}
			objReportsDto.setBandwaidth(rs.getString("BANDWIDTH"));
			objReportsDto.setUom(rs.getString("UOM"));
			objReportsDto.setLob(rs.getString("LOB"));
			objReportsDto.setCircle(rs.getString("CIRCLE"));
			objReportsDto.setZone(rs.getString("ZONE"));
			objReportsDto.setLocation_from(rs.getString("SUPPORT_LOCATION_A"));
			objReportsDto.setLocation_to(rs.getString("SUPPORT_LOCATION_B"));
			objReportsDto.setCommited_sla(rs.getString("COMMITED_SLA"));
			objReportsDto.setHub_location(rs.getString("UB_LOCATION"));
			objReportsDto.setPlatform(rs.getString("PLATFORM"));
			objReportsDto.setCustomer_logicalSINumber(rs.getInt("CUSTOMER_LOGICAL_SI_NO"));
			objReportsDto.setFxSiId(rs.getString("SIID"));
			objReportsDto.setIpls(rs.getString("IPLC"));
			objReportsDto.setManaged_yes_no(rs.getString("MANAGED_YES_NO"));
			objReportsDto.setActmngname(rs.getString("ACTMNAME"));
			objReportsDto.setPrjmngname(rs.getString("PMNAME"));
			objReportsDto.setService_provider(rs.getString("LAST_MILE_PROVIDER"));
			objReportsDto.setLineItemDescription(rs.getString("LINENAME"));
			
			
			
			
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

public ArrayList<AttributeDetailsReportDTO> viewAttributeDetailsReport(AttributeDetailsReportDTO objDto) 
{
	//	Nagarjuna
	String methodName="viewAttributeDetailsReport", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;	
	//end
	Connection connection = null;
	CallableStatement proc = null;
	ResultSet rs = null;
	int recordCount = 0;
	ArrayList<AttributeDetailsReportDTO> listAttributeDetailsReport = new ArrayList<AttributeDetailsReportDTO>();
	AttributeDetailsReportDTO objReportsDto = null;
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat formatter2 = new SimpleDateFormat("MM-dd-yyyy");

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

		if (objDto.getFromDate() != null
				&& !"".equals(objDto.getFromDate())) {
			Date dateStr = formatter.parse(objDto.getFromDate().trim());
 			String formattedDate = formatter.format(dateStr);
 			Date date1 = formatter.parse(formattedDate);
 			formattedDate = formatter2.format(date1);
 			proc.setString(2, formattedDate);
			//proc.setString(2, objDto.getFromDate().trim());
		} else {
			proc.setNull(2, java.sql.Types.VARCHAR);
		}
		if (objDto.getToDate() != null && !"".equals(objDto.getToDate())) {
			Date dateStr = formatter.parse(objDto.getToDate().trim());
 			String formattedDate = formatter.format(dateStr);
 			Date date1 = formatter.parse(formattedDate);
 			formattedDate = formatter2.format(date1);
			proc.setString(3, formattedDate);
			//proc.setString(3, objDto.getToDate().trim());
		} else {
			proc.setNull(3, java.sql.Types.VARCHAR);
		}
		
		if (objDto.getServiceTypeId() != 0 
				&& !"".equals(objDto.getServiceTypeId())) {
			proc.setInt(4, objDto.getServiceTypeId());
		} else {
			proc.setNull(4, java.sql.Types.BIGINT);
		}
		
		PagingSorting pagingSorting = objDto.getPagingSorting();
		pagingSorting.sync();// To calculate start index and Enc Index

		proc.setString(5, pagingSorting.getSortByColumn());// columnName
		proc.setString(6, PagingSorting.DB_Asc_Desc(pagingSorting
				.getSortByOrder()));// sort order
		proc.setInt(7, pagingSorting.getStartRecordId());// start index
		 proc.setInt(8, pagingSorting.getEndRecordId());// end index
		proc.setInt(9, (pagingSorting.isPagingToBeDone() ? 1 : 0));
		// index
		rs = proc.executeQuery();
		while(rs.next())
		{
			objReportsDto =  new AttributeDetailsReportDTO();
		
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

	public static String sqlGetDisconnectionChangeOrderReport="{call IOE.SP_GET_DISCONNECT_CHANGE_ORDER_REPORT(?,?,?,?,?,?,?,?,?,?,?,?,?)}";

public ArrayList<DisconnectChangeOrdeReportDTO> viewDisconnectionChangeOrderReport(DisconnectChangeOrdeReportDTO objDto) 
{
	//	Nagarjuna
	String methodName="viewDisconnectionChangeOrderReport", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;	
	//end
	Connection connection = null;
	CallableStatement proc = null;
	ResultSet rs = null;
	int recordCount = 0;
	ArrayList<DisconnectChangeOrdeReportDTO> listSearchDetails = new ArrayList<DisconnectChangeOrdeReportDTO>();
	DisconnectChangeOrdeReportDTO objReportsDto = null;
	Utility utility=new Utility();
	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat formatter2 = new SimpleDateFormat("MM-dd-yyyy");
	try
	{
		connection=DbConnection.getReportsConnectionObject();
		proc= connection.prepareCall(sqlGetDisconnectionChangeOrderReport);
		if (objDto.getFromDate() != null
				&& !"".equals(objDto.getFromDate())) {
			Date dateStr = formatter.parse(objDto.getFromDate().trim());
 			String formattedDate = formatter.format(dateStr);
 			Date date1 = formatter.parse(formattedDate);
 			formattedDate = formatter2.format(date1);
 			proc.setString(1, formattedDate);
			
		} else {
			proc.setNull(1, java.sql.Types.VARCHAR);
		}
		
		if (objDto.getToDate() != null 
				&& !"".equals(objDto.getToDate())) {
			Date dateStr = formatter.parse(objDto.getToDate().trim());
 			String formattedDate = formatter.format(dateStr);
 			Date date1 = formatter.parse(formattedDate);
 			formattedDate = formatter2.format(date1);
			proc.setString(2, formattedDate);
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
		proc.setInt(7, (pagingSorting.isPagingToBeDone() ? 1 : 0));// end
		if (objDto.getServiceName() != null 
				&& !"".equals(objDto.getServiceName())) {
			proc.setString(8, objDto.getServiceName().trim().toUpperCase());
		} else {
			proc.setNull(8, java.sql.Types.VARCHAR);
		}
		
	
			
		
		
		if (objDto.getOrdersubtype() != null
				&& !"".equals(objDto.getOrdersubtype())) {
			proc.setInt(9, Integer.parseInt(objDto.getOrdersubtype().trim()));
		} else {
			proc.setNull(9, java.sql.Types.BIGINT);
		}
		proc.setNull(10, java.sql.Types.VARCHAR);
		if (objDto.getVerticalDetails() != null 
				&& !"".equals(objDto.getVerticalDetails())) {
			proc.setString(11, objDto.getVerticalDetails().trim().toUpperCase());
		} else {
			proc.setNull(11, java.sql.Types.VARCHAR);
		}
		
			if (objDto.getCus_segment() != null 
					&& !"".equals(objDto.getCus_segment())) {
				proc.setString(12, objDto.getCus_segment().trim().toUpperCase());
			} else {
				proc.setNull(12, java.sql.Types.VARCHAR);
			}
			if (objDto.getSrrequest() != null 
					&& !"".equals(objDto.getSrrequest())) {
				proc.setString(13, objDto.getSrrequest());
			} else {
				proc.setNull(13, java.sql.Types.VARCHAR);
			}
		
		/*if (objDto.getOrdermonth() != null
				&& !"".equals(objDto.getOrdermonth())) {
			proc.setString(1, objDto.getOrdermonth().trim());
		} else {
			proc.setNull(1, java.sql.Types.VARCHAR);
		}
		
		if (objDto.getFromDate() != null
				&& !"".equals(objDto.getFromDate())) {
			Date dateStr = formatter.parse(objDto.getFromDate().trim());
 			String formattedDate = formatter.format(dateStr);
 			Date date1 = formatter.parse(formattedDate);
 			formattedDate = formatter2.format(date1);
 			proc.setString(2, formattedDate);
			
		} else {
			proc.setNull(2, java.sql.Types.VARCHAR);
		}
		
		if (objDto.getToDate() != null 
				&& !"".equals(objDto.getToDate())) {
			Date dateStr = formatter.parse(objDto.getToDate().trim());
 			String formattedDate = formatter.format(dateStr);
 			Date date1 = formatter.parse(formattedDate);
 			formattedDate = formatter2.format(date1);
			proc.setString(3, formattedDate);
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
		*/
		// index
		rs = proc.executeQuery();
		while(rs.next())
		{ 
			objReportsDto =  new DisconnectChangeOrdeReportDTO() ;
			objReportsDto.setLogicalCircuitId(rs.getString("LOGICALCIRCUITID"));
			objReportsDto.setServiceName(rs.getString("SERVICENAME"));
			objReportsDto.setLinename(rs.getString("SERVICEDETDESCRIPTION"));
			objReportsDto.setChargeTypeName(rs.getString("CHARGE_TYPE"));
			objReportsDto.setChargeName(rs.getString("CHARGE_NAME"));
			objReportsDto.setFrequencyName(rs.getString("FREQUENCY"));
				objReportsDto.setCus_segment(rs.getString("CUST_SEGMENT_CODE"));
			objReportsDto.setBill_period(rs.getString("BILL_PERIOD"));
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
			objReportsDto.setAccountID(rs.getInt("CRMACCOUNTNO"));
			objReportsDto.setCreditPeriodName(rs.getString("CREDITPERIOD"));
			objReportsDto.setCurrencyName(rs.getString("CURRENCYNAME"));
			objReportsDto.setEntity(rs.getString("ENTITYNAME"));
			objReportsDto.setBillingMode(rs.getString("BILLINGMODE"));
			objReportsDto.setBilling_Trigger_Status(rs.getString("BILLING_TRIGGER_STATUS"));
			objReportsDto.setBilling_Trigger_Flag(rs.getString("Billing_Trigger_Flag"));
			objReportsDto.setBillingTypeName(rs.getString("BILLING_TYPENAME"));
			objReportsDto.setBillingformat(rs.getString("BILLING_FORMATNAME"));
			objReportsDto.setLicCompanyName(rs.getString("LCOMPANYNAME"));
			objReportsDto.setTaxation(rs.getString("TAXATIONVALUE"));
			objReportsDto.setPenaltyClause(rs.getString("PENALTYCLAUSE"));
			objReportsDto.setBillingLevel(rs.getString("BILLING_LEVELNAME"));
			objReportsDto.setBillingLevelId(rs.getInt("BILLING_LEVEL_NO"));
			objReportsDto.setStore(rs.getString("STORENAME"));
			objReportsDto.setHardwareType(rs.getString("HARDWARETYPENAME"));
			objReportsDto.setFormAvailable(rs.getString("FORM_C_AVAILABLE"));
			objReportsDto.setNature_sale(rs.getString("SALENATURE"));
			objReportsDto.setType_sale(rs.getString("SALETYPE"));
			objReportsDto.setPoNumber(rs.getInt("PONUMBER"));
			objReportsDto.setPoDate(rs.getString("PODATE"));
			if(!(rs.getString("PODATE")==null || rs.getString("PODATE")==""))
			{
				
				Date date=df.parse(objReportsDto.getPoDate());
				objReportsDto.setPoDate((utility.showDate_Report(date)).toUpperCase());
				
			}
			objReportsDto.setParty(rs.getString("PARTYNAME"));
			objReportsDto.setAnnitation(rs.getString("ANNOTATION"));
			objReportsDto.setTokenno(rs.getString("CSTATE_START_DETAILS_FX_TOKEN_NO"));
			objReportsDto.setPm_pro_date(rs.getString("Pm_Prov_Date"));
			
			objReportsDto.setLocDate(rs.getString("LOCDATE"));
			if(!(rs.getString("LOCDATE")==null || rs.getString("LOCDATE")==""))
			{
				
				Date date=df.parse(objReportsDto.getLocDate());
				objReportsDto.setLocDate((utility.showDate_Report(date)).toUpperCase());
				
			}
			objReportsDto.setBillingTriggerDate(rs.getString("BILLINGTRIGGERDATE"));
			if(!(rs.getString("BILLINGTRIGGERDATE")==null || rs.getString("BILLINGTRIGGERDATE")==""))
			{
				Date date=df.parse(objReportsDto.getBillingTriggerDate());
				objReportsDto.setBillingTriggerDate((utility.showDate_Report(date)).toUpperCase());
			}
			objReportsDto.setChallenno(rs.getString("CHALLEN_NO"));
			objReportsDto.setChallendate(rs.getString("CHALLEN_DATE"));
			if(!(rs.getString("CHALLEN_DATE")==null || rs.getString("CHALLEN_DATE")==""))
			{
				objDto.setChallendate(rs.getString("CHALLEN_DATE"));
				if (rs.getString("CHALLEN_DATE") != null && !"".equals(rs.getString("CHALLEN_DATE")))
				{
				  String s1=rs.getString("CHALLEN_DATE");
				  if(s1.length()==10){
					  s1="0"+s1;
				  }
	  
				  String s3=s1.substring(0,7).toUpperCase();
				  String s4=s1.substring(9,11);
				  String s5=s3.concat(s4);
				  objDto.setChallendate(s5);
				}
			}
			objReportsDto.setChild_act_no(rs.getString("Child_Account_Number"));
			objReportsDto.setChild_ac_fxstatus(rs.getString("Child_Account_FX_Sataus"));
			objReportsDto.setOrderDate(rs.getString("ORDERDATE"));
			if(!(rs.getString("ORDERDATE")==null || rs.getString("ORDERDATE")==""))
			{
				objReportsDto.setOrderDate((utility.showDate_Report(new Date(rs.getTimestamp("ORDERDATE").getTime()))).toUpperCase());
			}
			objReportsDto.setOrder_type(rs.getString("ORDERTYPE"));
			objReportsDto.setServiceOrderType(rs.getString("SERVICE_ORDER_TYPE_DESC"));
			objReportsDto.setCopcapprovaldate(rs.getString("COPC_APPROVED_DATE"));
			if(!(rs.getString("COPC_APPROVED_DATE")==null || rs.getString("COPC_APPROVED_DATE")==""))
			{
				objReportsDto.setCopcapprovaldate((utility.showDate_Report(new Date(rs.getTimestamp("COPC_APPROVED_DATE").getTime()))).toUpperCase());
			}
			objReportsDto.setBillingtrigger_createdate(rs.getString("BILLING_TRIGGER_CREATEDATE"));
			if(!(rs.getString("BILLING_TRIGGER_CREATEDATE")==null || rs.getString("BILLING_TRIGGER_CREATEDATE")==""))
			{
				//Date date=df.parse(objReportsDto.getBillingtrigger_createdate());
				//objReportsDto.setBillingtrigger_createdate((utility.showDate_Report(date)).toUpperCase());
				objReportsDto.setBillingtrigger_createdate((utility.showDate_Report(new Date(rs.getTimestamp("BILLING_TRIGGER_CREATEDATE").getTime()))).toUpperCase());
				
			}
			objReportsDto.setCustomer_logicalSINumber(rs.getInt("CUSTOMER_LOGICAL_SI_NO"));
			objReportsDto.setHardware_flag(rs.getString("HARDWARE_FLAG"));
			objReportsDto.setChargeinfoID(rs.getString("CHARGETYPEID"));
			if(objReportsDto.getChargeinfoID()==null){
				objReportsDto.setChargeinfoID(" ");
			}
			objReportsDto.setServiceStage(rs.getString("SERVICE_STAGE"));
			objReportsDto.setOrderStage(rs.getString("STAGE"));
			objReportsDto.setActmgrname(rs.getString("ACCOUNTMANAGER"));
			objReportsDto.setPrjmngname(rs.getString("PROJECTMANAGER"));
			/*objReportsDto.setOrderDate(rs.getString("ORDERDATE"));
			if(!(rs.getString("ORDERDATE")==null || rs.getString("ORDERDATE")==""))
			{
				objReportsDto.setOrderDate((utility.showDate_Report(new Date(rs.getTimestamp("ORDERDATE").getTime()))).toUpperCase());
			}*/
			objReportsDto.setRfs_date(rs.getString("SERVICE_RFS_DATE"));
			if(!(rs.getString("SERVICE_RFS_DATE")==null || rs.getString("SERVICE_RFS_DATE")==""))
			{
				//objReportsDto.setRfs_date(rs.getString("SERVICE_RFS_DATE"));
				//Date date=df.parse(objReportsDto.getRfs_date().trim());
				//objReportsDto.setRfs_date((utility.showDate_Report(date)).toUpperCase());
				objReportsDto.setRfs_date((utility.showDate_Report(new Date(rs.getTimestamp("SERVICE_RFS_DATE").getTime()))).toUpperCase());
			}
			objReportsDto.setCust_po_rec_date(rs.getString("PORECEIVEDATE"));
			if(!(rs.getString("PORECEIVEDATE")==null || rs.getString("PORECEIVEDATE")==""))
			{
				
				Date date=df.parse(objReportsDto.getCust_po_rec_date());
				objReportsDto.setCust_po_rec_date((utility.showDate_Report(date)).toUpperCase());
				
			}
			objReportsDto.setCharge_status(rs.getString("CHARGES_STATUS"));
			objReportsDto.setVerticalDetails(rs.getString("VERTICAL_DETAILS"));
			objReportsDto.setRegion(rs.getString("REGION"));
			objReportsDto.setDemo(rs.getString("Demo_Type"));
			objReportsDto.setNewOrderRemark(rs.getString("NEWORDER_REMARKS"));
			//objReportsDto.setOrderStageDescription(rs.getString("STAGE"));
			//objReportsDto.setMocn_no(rs.getString("MOCN_NO"));
			objReportsDto.setDisconnection_remarks(rs.getString("DISCONNECTION_REMARKS"));
			objReportsDto.setSrno(rs.getString("SR_NO"));
			objReportsDto.setRequest_rec_date(rs.getString("SRDATE"));
			//objReportsDto.setLineno(rs.getInt("M6_CKTID"));
			//objReportsDto.setOrderDate((utility.showDate_Report(new Date(rs.getTimestamp("ORDERDATE").getTime()))).toUpperCase());
			String tmpDate=objReportsDto.getOrderDate().substring((objReportsDto.getOrderDate().indexOf("-")+1), objReportsDto.getOrderDate().length());
			objReportsDto.setOrdermonth(tmpDate);
			objReportsDto.setCkt_id(rs.getString("CKTID"));
			objReportsDto.setStandard_reason(rs.getString("STANDARDREASON"));
			objReportsDto.setBandwaidth(rs.getString("BANDWIDTH"));
			objReportsDto.setBilling_bandwidth(rs.getString("BILLING_BANDWIDTH"));
			objReportsDto.setBilling_uom(rs.getString("BILLING_BANDWIDTH_UOM"));
			//objReportsDto.setBandwidth_att(rs.getString("BANDWIDTH_ATT"));
			objReportsDto.setUom(rs.getString("UOM"));
			objReportsDto.setRate_code(rs.getString("RATECODE"));
			objReportsDto.setLast_mile_media(rs.getString("LAST_MILE_MEDIA"));
			objReportsDto.setLast_mile_remarks(rs.getString("LAST_MILE_REMARKS"));
			objReportsDto.setChargeable_Distance(rs.getString("DISTANCE"));
			objReportsDto.setLast_mile_provider(rs.getString("LAST_MILE_PROVIDER"));
			objReportsDto.setLink_type(rs.getString("LINK_TYPE"));
			objReportsDto.setDispatchAddress1(rs.getString("DISP_ADDRESS1"));
			objReportsDto.setIndicative_value(rs.getString("INDICATIVE_VALUE"));
			objReportsDto.setProductName(rs.getString("SERVICEDETDESCRIPTION"));
			objReportsDto.setCustPoDate(rs.getString("CUSTPODATE"));
			if(!(rs.getString("CUSTPODATE")==null || rs.getString("CUSTPODATE")==""))
			{
				
				Date date=df.parse(objReportsDto.getCustPoDate());
				objReportsDto.setCustPoDate((utility.showDate_Report(date)).toUpperCase());
				
			}
			objReportsDto.setCustPoDetailNo(rs.getString("CUSTPONUMBER"));
			objReportsDto.setLocno(rs.getString("LOCNO"));
			objReportsDto.setPrimarylocation(rs.getString("PRIMARYLOCATION"));
			objReportsDto.setProdAlisName(rs.getString("PRODUCTNAME"));
			objReportsDto.setRatio(rs.getString("RATIO"));
			objReportsDto.setSeclocation(rs.getString("SECONDARYLOCATION"));
			objReportsDto.setSub_linename(rs.getString("SUBPRODUCTNAME"));
			//objReportsDto.setTokenNO(rs.getString("CSTATE_START_DETAILS_FX_TOKEN_NO"));
			objReportsDto.setOrderNo(rs.getString("ORDERNO"));
			objReportsDto.setCharge_hdr_id(rs.getInt("CHARGE_HDR_ID"));
			objReportsDto.setOrderLineNumber(rs.getInt("Order_Line_Id"));
			//objReportsDto.setCustomer_logicalSINumber(rs.getInt("CUSTOMER_LOGICAL_SI_NO"));
			objReportsDto.setServiceId(rs.getInt("SERVICE_NO"));
			//objReportsDto.setLineItemAmount(rs.getString("LINEITEMAMOUNT"));
			objReportsDto.setFrequencyAmt(BigDecimal.valueOf((rs.getDouble("CHARGEFREQUENCYAMT"))).toPlainString());  
			
			if(objReportsDto.getFrequencyAmt()==null){
				objReportsDto.setFrequencyAmt(" ");
			}
			//objReportsDto.setAmt(rs.getLong("CHARGEAMOUNT"));//particular charge amount
			objReportsDto.setLineItemAmount(BigDecimal.valueOf((rs.getDouble("CHARGEAMOUNT"))).toPlainString());  
			
			if(objReportsDto.getLineItemAmount()==null){
				objReportsDto.setLineItemAmount(" ");
			}
			//objReportsDto.setTotalAmountIncludingCurrent(rs.getDouble("TOTALPOAMOUNT"));
			objReportsDto.setChargeAmount_String(objReportsDto.getLineItemAmount());
			//objReportsDto.setAdvance(rs.getString("ADVANCE"));
			//objReportsDto.setInstallation_addressaa1(rs.getString("INSTALLEMENT"));
			objReportsDto.setStartDateDays(rs.getInt("START_DATE_DAYS"));
			objReportsDto.setStartDateMonth(rs.getInt("START_DATE_MONTH"));
			objReportsDto.setEndDateDays(rs.getInt("END_DATE_DAYS"));
			objReportsDto.setEndDateMonth(rs.getInt("END_DATE_MONTH"));
			objReportsDto.setAnnualRate(rs.getString("ANNUAL_RATE"));
			objReportsDto.setContractMonths(rs.getInt("CONTRACTPERIOD"));
			objReportsDto.setCommitmentPeriod(rs.getInt("COMMITMENTPERIOD"));
			objReportsDto.setNoticePeriod(rs.getInt("NOTICEPERIOD"));
			objReportsDto.setPeriodsInMonths(rs.getString("CONTRACTPERIOD"));
			objReportsDto.setTotalPoAmt(BigDecimal.valueOf((rs.getDouble("POAMOUNT"))).toPlainString()); 
			
			if(objReportsDto.getTotalPoAmt()==null){
				objReportsDto.setTotalPoAmt(" ");
			}
			objReportsDto.setParty_id(rs.getInt("PARTY_NO"));
			objReportsDto.setCust_act_id(rs.getString("CUSTACCOUNTID"));
			//objReportsDto.setM6_prod_id(rs.getString("M6_PRODUCT_ID"));
			objReportsDto.setM6_order_id(rs.getString("M6ORDERNO"));
			objReportsDto.setPre_crmorderid(rs.getInt("Pre_Crm_orderNo"));
	        //objReportsDto.setM6cktid(rs.getString("M6_CKTID"));
			objReportsDto.setPk_charge_id(rs.getString("CHARGEINFOID"));
			//objReportsDto.setBusiness_serial_no(rs.getString("Business_No"));
			objReportsDto.setBilling_location_from(rs.getString("FROM_ADDRESS"));
			objReportsDto.setBilling_location_to(rs.getString("TO_ADDRESS"));
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

public ArrayList<MigratedApprovedNewOrderDetailReportDTO> viewMigAppNewOrderDetails(MigratedApprovedNewOrderDetailReportDTO objDto) 
{
	//	Nagarjuna
	String methodName="viewMigAppNewOrderDetails", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;	
	//end
	Connection connection =null;
	CallableStatement proc =null;
	ResultSet rs = null;
	ArrayList<MigratedApprovedNewOrderDetailReportDTO> listSearchDetails = new ArrayList<MigratedApprovedNewOrderDetailReportDTO>();
	MigratedApprovedNewOrderDetailReportDTO objReportsDto = null;
	int recordCount =0;
	Utility utility=new Utility();
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    Date tempDate=null;
    //Timestamp tempTimestamp=null;
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
			//proc.set(2, java.sql.Types.VARCHAR);
		}
		
		PagingSorting pagingSorting = objDto.getPagingSorting();
		pagingSorting.sync();// To calculate start index and Enc Index
		proc.setString(3, pagingSorting.getSortByColumn());// columnName
		proc.setString(4, PagingSorting.DB_Asc_Desc(pagingSorting
				.getSortByOrder()));// sort order
		proc.setInt(5, pagingSorting.getStartRecordId());// start index
		 proc.setInt(6, pagingSorting.getEndRecordId());// end index
		proc.setInt(7, (pagingSorting.isPagingToBeDone() ? 1 : 0));
		if (objDto.getOrderyear() != null
				&& !"".equals(objDto.getOrderyear())) {
			proc.setString(8, objDto.getOrderyear().trim());
		} else {
			proc.setNull(8, java.sql.Types.VARCHAR);
		}
		rs = proc.executeQuery();
		while(rs.next())
		{
			objReportsDto =  new MigratedApprovedNewOrderDetailReportDTO();
			objReportsDto.setOrderNumber(rs.getInt("ORDERNO"));
			objReportsDto.setOrderType(rs.getString("ORDERTYPE"));
			objReportsDto.setCrmAccountNoString(rs.getString("CRMACCOUNTNO"));
			objReportsDto.setPartyName(rs.getString("PARTYNAME"));
			objReportsDto.setParty_no(rs.getInt("Party_NO"));
			objReportsDto.setVerticalDetails(rs.getString("VERTICAL_DETAILS"));
			objReportsDto.setRegionName(rs.getString("REGION"));   
			objReportsDto.setM6OrderNo2(rs.getString("M6ORDERNO"));
			objReportsDto.setServiceName(rs.getString("SERVICESTAGE"));
			objReportsDto.setCustomer_logicalSINumber(rs.getInt("CUSTOMER_LOGICAL_SI_NO"));
			objReportsDto.setServiceDetDescription(rs.getString("SERVICEDETTYPE"));//LineName
			objReportsDto.setServiceOrderType(rs.getString("SERVICETYPE"));
			objReportsDto.setLogicalCircuitId(rs.getString("CKTID"));
			objReportsDto.setFromLocation(rs.getString("FROM_ADDRESS"));
			objReportsDto.setToLocation(rs.getString("TO_ADDRESS"));
			objReportsDto.setEntity(rs.getString("ENTITYNAME"));//Legal Entity
			objReportsDto.setLcompanyname(rs.getString("LCOMPANYNAME"));
			objReportsDto.setCurrencyName(rs.getString("CURNAME"));
			objReportsDto.setCreditPeriodName(rs.getString("CREDIT_PERIODNAME"));
			objReportsDto.setBillingTypeName(rs.getString("BILLING_TYPENAME"));//Bill Type
			objReportsDto.setBillingFormatName(rs.getString("BILLING_FORMATNAME"));
			objReportsDto.setBillingLevelName(rs.getString("BILLING_LEVELNAME"));
			objReportsDto.setBillingLevelNo(rs.getLong("BILLING_LEVEL_NO"));
			objReportsDto.setTaxation(rs.getString("TAXATIONVALUE"));
			objReportsDto.setHardwaretypeName(rs.getString("HARDWARETYPENAME"));
			objReportsDto.setStorename(rs.getString("STORENAME"));
			objReportsDto.setSaleType(rs.getString("SALETYPENAME"));//Type Of Sale
			objReportsDto.setBillingMode(rs.getString("BILLINGMODE"));
			objReportsDto.setPonum(rs.getLong("PODETAILNUMBER"));
			objReportsDto.setPoDate(rs.getString("PODATE"));
			if(!(rs.getString("PODATE")==null || rs.getString("PODATE")==""))
			{
				
				//Date date=df.parse(objReportsDto.getPoDate());
				objReportsDto.setPoDate((utility.showDate_Report(rs.getString("PODATE"))).toUpperCase());
				
			}
			objReportsDto.setContractPeriod(rs.getInt("CONTRACTPERIOD"));//Period In Month
			objReportsDto.setTotalPoAmt(rs.getString("POAMOUNT"));
			objReportsDto.setPoRecieveDate(rs.getString("PORECEIVEDATE"));
			if(!(rs.getString("PORECEIVEDATE")==null || rs.getString("PORECEIVEDATE")==""))
			{
				
				//Date date=df.parse(objReportsDto.getPoRecieveDate());
				objReportsDto.setPoRecieveDate((utility.showDate_Report(rs.getString("PORECEIVEDATE"))).toUpperCase());
				
			}
			objReportsDto.setCustPoDetailNo(rs.getString("CUSTPONUMBER"));   
			objReportsDto.setCustPoDate(rs.getString("CUSTPODATE")); 
			if(!(rs.getString("CUSTPODATE")==null || rs.getString("CUSTPODATE")==""))
			{
				
				//Date date=df.parse(objReportsDto.getCustPoDate());
				objReportsDto.setCustPoDate((utility.showDate_Report(rs.getString("CUSTPODATE"))).toUpperCase());
				
			}
			objReportsDto.setLOC_No(rs.getString("LOCNO"));
			objReportsDto.setLocDate(rs.getString("LOCDATE"));
			if (rs.getString("LOCDATE") != null
					&& !"".equals(rs.getString("LOCDATE"))) {
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				Date dateStr = formatter.parse(rs.getString("LOCDATE"));
				String formattedDate = formatter.format(dateStr);
				Date date1 = formatter.parse(formattedDate);
				formatter = new SimpleDateFormat("dd-MMM-yy");
				formattedDate = formatter.format(date1);
				objReportsDto.setLocDate(formattedDate);
			} 
			objReportsDto.setBillingTriggerDate(rs.getString("BILLINGTRIGGERDATE"));
			if (rs.getString("BILLINGTRIGGERDATE") != null
					&& !"".equals(rs.getString("BILLINGTRIGGERDATE"))) {
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				Date dateStr = formatter.parse(rs.getString("BILLINGTRIGGERDATE"));
				String formattedDate = formatter.format(dateStr);
				Date date1 = formatter.parse(formattedDate);
				formatter = new SimpleDateFormat("dd-MMM-yy");
				formattedDate = formatter.format(date1);
				objReportsDto.setBillingTriggerDate(formattedDate);
			}
			objReportsDto.setBillingtrigger_createdate(rs.getString("BILLING_TRIGGER_CREATEDATE"));
			if(!(rs.getString("BILLING_TRIGGER_CREATEDATE")==null || rs.getString("BILLINGTRIGGERDATE")==""))
			{
				
				//Date date=df2.parse(objReportsDto.getBillingtrigger_createdate());
				objReportsDto.setBillingtrigger_createdate((utility.showDate_Report(rs.getString("BILLING_TRIGGER_CREATEDATE"))).toUpperCase());
				
			}
			objReportsDto.setPmApproveDate(rs.getString("LOC_DATE"));
			if (rs.getString("LOC_DATE") != null && !"".equals(rs.getString("LOC_DATE")))
			{
			  String s1=rs.getString("LOC_DATE");
			  String s3=s1.substring(0,7);
			  String s4=s1.substring(9,11);
			  String s5=s3.concat(s4);
			  objReportsDto.setPmApproveDate(s5);
			}
			objReportsDto.setBilling_Trigger_Flag(rs.getString("BILLING_TRIGGER_STATUS"));
			objReportsDto.setBillPeriod(rs.getString("BILL_PERIOD"));
			objReportsDto.setBilling_bandwidth(rs.getString("BILLING_BANDWIDTH"));
			objReportsDto.setBillUom(rs.getString("BILLING_BANDWIDTH_UOM"));
			objReportsDto.setKmsDistance(rs.getString("DISTANCE"));
			objReportsDto.setBandwaidth(rs.getString("BANDWIDTH"));
			objReportsDto.setTokenNO(rs.getString("START_DETAILS_FX_TOKEN_NO"));//Token_No
			objReportsDto.setFx_St_Chg_Status(rs.getString("FX_CHARGE_START_STATUS"));//Fx_St_Chg_Status
			objReportsDto.setFxStatus(rs.getString("START_DETAILS_FX_STATUS"));//FX_STATUS
			objReportsDto.setChargeName(rs.getString("CHARGE_NAME"));
			objReportsDto.setChargeTypeName(rs.getString("CHARGE_TYPE"));
			objReportsDto.setProductName(rs.getString("PRODUCTNAME"));
			objReportsDto.setSubProductName(rs.getString("SUBPRODUCTNAME"));
			objReportsDto.setChargeAmount_String(rs.getString("INV_AMT"));
			objReportsDto.setLineItemAmount(rs.getString("LINEITEMAMOUNT"));//amt
			objReportsDto.setStartDate(rs.getString("START_DATE"));
			
			if (rs.getString("START_DATE") != null
					&& !"".equals(rs.getString("START_DATE"))) {
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				Date dateStr = formatter.parse(rs.getString("START_DATE"));
				String formattedDate = formatter.format(dateStr);
				Date date1 = formatter.parse(formattedDate);
				formatter = new SimpleDateFormat("dd-MMM-yy");
				formattedDate = formatter.format(date1);
				objReportsDto.setStartDate(formattedDate);
			} else {
				objReportsDto.setStartDate("");
			}
			objReportsDto.setChargeAnnotation(rs.getString("ANNOTATION"));
			objReportsDto.setOrderLineNumber(rs.getInt("LINENUMBER"));//Lineitemnumber	
			objReportsDto.setOrdermonth(rs.getString("ORDERMONTH"));
			/*objReportsDto.setBlSource(rs.getString("BL_SOURCE"));
			objReportsDto.setBusiness_serial_no(rs.getString("BUSINESS_SERIAL_NO"));
			objReportsDto.setOpms_Account_Id(rs.getString("OPMS_ACCOUNT_ID"));*/
			objReportsDto.setBusiness_serial_n(rs.getInt("BUSINESS_SERIAL_NO"));
			objReportsDto.setOpms_Account_Id(rs.getString("OPMS_ACCOUNT_ID"));
			objReportsDto.setOpms_lineItemNumber(rs.getString("OPMS_LINEITEMNUMBER"));
			objReportsDto.setRatio(rs.getString("RATIO"));
			objReportsDto.setHardwareFlag(rs.getString("HARDWARE_FLAG"));
			if (rs.getString("PRIMARYLOCATION") != null && !"".equals(rs.getString("PRIMARYLOCATION")))
			{
				if(Integer.parseInt(rs.getString("PRIMARYLOCATIONTYPE"))==1){
					String ss[] =rs.getString("PRIMARYLOCATION").split("~~");
					objReportsDto.setPrimaryLocation(ss[0]+" "+ss[1]+" "+ss[4]+" "+ss[5]+" "+ss[6]+" "+ss[7]+" "+ss[8]+" "+ss[9]+" "+ss[10]+" "+ss[2]);
				}
				else{
					String ss[] =rs.getString("PRIMARYLOCATION").split("~~");
					objReportsDto.setPrimaryLocation(ss[0]+" "+ss[1]+" "+ss[5]+" "+ss[3]);
				}
			}
			else
			{
				objReportsDto.setPrimaryLocation("");
			}
			if (rs.getString("SECONDARYLOCATION") != null && !"".equals(rs.getString("SECONDARYLOCATION")))
			{
				if(Integer.parseInt(rs.getString("SECONDARYLOCATIONTYPE"))==1){
					String ss[] =rs.getString("SECONDARYLOCATION").split("~~");
					objReportsDto.setSecondaryLocation(ss[0]+" "+ss[1]+" "+ss[4]+" "+ss[5]+" "+ss[6]+" "+ss[7]+" "+ss[8]+" "+ss[9]+" "+ss[10]+" "+ss[2]);
				}
				else{
					String ss[] =rs.getString("SECONDARYLOCATION").split("~~");
					objReportsDto.setSecondaryLocation(ss[0]+" "+ss[1]+" "+ss[5]+" "+ss[3]);
				}
			}
			else
			{
				objReportsDto.setSecondaryLocation("");
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
public ArrayList<LempOwnerReportDTO> viewLEPMOwnerReport(LempOwnerReportDTO objDto) 
{
	//	Nagarjuna
	String methodName="viewLEPMOwnerReport", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;	
	//end
	Connection connection = null;
	CallableStatement proc = null;
	ResultSet rs = null;
	int recordCount = 0;
	Utility utility=new Utility();
	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	ArrayList<LempOwnerReportDTO> listLEPMOwnerReport = new ArrayList<LempOwnerReportDTO>();
	LempOwnerReportDTO objReportsDto = null;
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
		//[303030]START
		if (objDto.getCopcApproveFromDate() != null && !"".equals(objDto.getCopcApproveFromDate()) && objDto.getCopcApproveToDate() != null && !"".equals(objDto.getCopcApproveToDate()) ) {
			//proc.setString(2, objDto.getCopcApproveDate());
			proc.setString(2, objDto.getCopcApproveFromDate());
			proc.setString(3, objDto.getCopcApproveToDate());
		} else {
			proc.setNull(2, java.sql.Types.VARCHAR);
			proc.setNull(3, java.sql.Types.VARCHAR);
		}
		//[303030]END
		PagingSorting pagingSorting = objDto.getPagingSorting();
		pagingSorting.sync();// To calculate start index and Enc Index

		proc.setString(4, pagingSorting.getSortByColumn());// columnName
		proc.setString(5, PagingSorting.DB_Asc_Desc(pagingSorting
				.getSortByOrder()));// sort order
		proc.setInt(6, pagingSorting.getStartRecordId());// start index
		 proc.setInt(7, pagingSorting.getEndRecordId());// end index
		proc.setInt(8, (pagingSorting.isPagingToBeDone() ? 1 : 0));
		// index
		if (objDto.getFromDate() != null && !"".equals(objDto.getFromDate()) && objDto.getToDate() != null && !"".equals(objDto.getToDate()) ) {
			//proc.setString(2, objDto.getCopcApproveDate());
			proc.setString(9, objDto.getFromDate());
			proc.setString(10, objDto.getToDate());
		} else {
			proc.setNull(9, java.sql.Types.VARCHAR);
			proc.setNull(10, java.sql.Types.VARCHAR);
		}
		rs = proc.executeQuery();
		while(rs.next())
		{
			objReportsDto =  new LempOwnerReportDTO();
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
		//ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
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
public ArrayList<LempCancelOrderReportDTO> viewLEPMOrderCancelReport(LempCancelOrderReportDTO objDto)throws Exception 
{
	//	Nagarjuna
	String methodName="viewLEPMOrderCancelReport", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;	
	//end
	ArrayList<LempCancelOrderReportDTO> objUserList = new ArrayList<LempCancelOrderReportDTO>();
	Connection conn = null;
	CallableStatement proc = null;
	ResultSet rs = null;
	int recordCount = 0;
	int countFlag = 0;
	LempCancelOrderReportDTO objReportsDto = null;
	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	ArrayList<LempCancelOrderReportDTO> listSearchDetails = new ArrayList<LempCancelOrderReportDTO>();
	
		try {
		//[101010]START
		conn = DbConnection.getReportsConnectionObject();
		proc = conn.prepareCall(sqlLEPMOrderCancelReport);
		
		if (objDto.getCanceldatefrom() != null
				&& !"".equals(objDto.getCanceldatefrom())) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date dateStr = formatter.parse(objDto.getCanceldatefrom());
			String formattedDate = formatter.format(dateStr);
			Date date1 = formatter.parse(formattedDate);
			formatter = new SimpleDateFormat("yyyy-MM-dd");
			formattedDate = formatter.format(date1);
			proc.setString(1,formattedDate);
		} else {
			proc.setNull(1, java.sql.Types.VARCHAR);
		}
		if (objDto.getCanceldateto() != null 
				&& !"".equals(objDto.getCanceldateto())) {
			SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
			Date dateStr1 = formatter1.parse(objDto.getCanceldateto());
			String formattedDate1 = formatter1.format(dateStr1);
			Date date2 = formatter1.parse(formattedDate1);
			formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			formattedDate1 = formatter1.format(date2);
			proc.setString(2,formattedDate1);
		} else {
			proc.setNull(2, java.sql.Types.VARCHAR);
		}
		
		/*if (objDto.getCanceldatefrom() != null
				&& !"".equals(objDto.getCanceldatefrom())) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date dateStr1 = formatter.parse(objDto.getCanceldatefrom());
			String formattedDate1 = formatter.format(dateStr1);
			proc.setString(1, formattedDate1);
		} else {
			proc.setNull(1, java.sql.Types.VARCHAR);
		}
		if (objDto.getCanceldateto() != null
				&& !"".equals(objDto.getCanceldateto())) {
			SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
			Date dateStr2 = formatter1.parse(objDto.getCanceldateto());
			String formattedDate2 = formatter1.format(dateStr2);
			proc.setString(1, formattedDate2);
			proc.setString(2, objDto.getCanceldateto().trim());
		} else {
			proc.setNull(2, java.sql.Types.VARCHAR);
		}*/
	
		//[101010]END
		PagingSorting pagingSorting = objDto.getPagingSorting();
		pagingSorting.sync();// To calculate start index and Enc Index
		
		proc.setString(3, pagingSorting.getSortByColumn());// columnName
		proc.setString(4, PagingSorting.DB_Asc_Desc(pagingSorting
				.getSortByOrder()));// sort order
		proc.setInt(5, pagingSorting.getStartRecordId());// start index
		proc.setInt(6, pagingSorting.getEndRecordId());// end index
		proc.setInt(7, (pagingSorting.isPagingToBeDone() ? 1 : 0));// end
		rs = proc.executeQuery();
		

		while (rs.next() != false) 
		{
			objDto = new LempCancelOrderReportDTO();
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
			objDto.setLineItemAmount(rs.getString("CHARGEAMOUNT"));
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
		
			objDto.setRfs_date(rs.getString("RFS_DATE"));
			if (rs.getString("RFS_DATE") != null && !"".equals(rs.getString("RFS_DATE")))
			{
				
				Date date=df.parse(objDto.getRfs_date());
				objDto.setRfs_date((Utility.showDate_Report(date)).toUpperCase());
				
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
			objDto.setPartyid(rs.getString("PARTY_ID"));
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
			if (rs.getString("FROM_CITY") != null && !"".equals(rs.getString("FROM_CITY")))
			{
				//[505053] start
				try{
					if(Integer.parseInt(rs.getString("PRIMARYLOCATIONTYPE"))==1){
						String ss[] =rs.getString("FROM_CITY").split("~~");
						objDto.setFrom_city(ss[8]);
					}
					else{
						objDto.setFrom_city(" ");
					}
				}catch(Exception exp){
					//It's okay to  ignore 'exp' here because setting a default value
					objDto.setFrom_city(" ");
				}
				//[505053] end
			}
			else
			{
				objDto.setFrom_city("");
			}
			if (rs.getString("TO_CITY") != null && !"".equals(rs.getString("TO_CITY")))
			{
				//[505053] start
				try{
					if(Integer.parseInt(rs.getString("SECONDARYLOCATIONTYPE"))==1){
						String ss[] =rs.getString("TO_CITY").split("~~");
						objDto.setTo_city(ss[8]);
					}
					else{
						objDto.setTo_city(" ");
					}
				}catch(Exception exp){
					//It's okay to  ignore 'exp' here because setting a default value
					objDto.setTo_city(" ");
				}
				//[505053] end
			}
			else
			{
				objDto.setTo_city("");
			}
			
			objDto.setRatio(rs.getString("RATIO"));
			objDto.setTaxation(rs.getString("TAXATIONVALUE"));
			//objDto.setAm_approval_remarks(rs.getString("AM_APPROVAL_TASK_REMARKS"));
			//objDto.setPm_approval_remarks(rs.getString("PM_APPROVAL_TASK_REMARKS"));
			//objDto.setCopc_approval_remarks(rs.getString("COPC_APPROVAL_TASK_REMARKS"));
			
			objDto.setDistance(rs.getString("DISTANCE"));
			objDto.setAccountManager(rs.getString("ACTMEMAIL"));
			objDto.setCurrencyCode(rs.getString("CURNAME"));
			objDto.setOrderTotal(rs.getDouble("ORDERTOTAL"));
			objDto.setPoAmount(rs.getString("POAMOUNT"));
			
			objDto.setOrderType(rs.getString("ORDERTYPE"));
			objDto.setDispatchAddress1(rs.getString("DISPATCHADDRESS"));
			//nagarjuna
			objDto.setCharge_Disconnection_Status(rs.getString("CHARGES_STATUS"));
			objDto.setSubchange_type(rs.getString("NAME_SUBTYPE"));
			//nagarjuna end
			objDto.setServiceStage(rs.getString("STAGE"));
				//[909090]
				objDto.setOrd_cancel_reason(rs.getString("ORD_CANCEL_REASON"));
				
			
				//Start [10990] 
				objDto.setServ_cancel_reson(rs.getString("SERV_CANCEL_REASON"));
				objDto.setService_cancelledby(rs.getString("SERVICE_CANCELLEDBY"));
				objDto.setServ_cancel_remarks(rs.getString("SERV_CANCEL_REMARKS"));
				objDto.setService_cancl_date(rs.getString("SERVICE_CANCEL_DATE"));
				if (rs.getString("SERVICE_CANCEL_DATE") != null && !"".equals(rs.getString("SERVICE_CANCEL_DATE")))//service_cancel_Date for IB2B cancelation
				{

					objDto.setService_cancl_date((Utility.showDate_Report((rs.getDate("SERVICE_CANCEL_DATE")))).toUpperCase());

				 }
				//end [10990] 
			objDto.setCanceldate(rs.getString("CANCEL_DATE"));
			if (rs.getString("CANCEL_DATE") != null && !"".equals(rs.getString("CANCEL_DATE")))
			{
				
				objDto.setCanceldate((Utility.showDate_Report((rs.getDate("CANCEL_DATE")))).toUpperCase());
				
			}

			//lawkush Start
			objDto.setOpportunityId((rs.getString("OPPORTUNITYID")));				
			 //lawkush End
			
			
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
public ArrayList<LempOrderDetailsReportDTO> viewLEPMOrderDetailReport(LempOrderDetailsReportDTO objDto)throws Exception 
{
	//	Nagarjuna
	String methodName="viewLEPMOrderDetailReport", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;	
	//end
	ArrayList<LempOrderDetailsReportDTO> objUserList = new ArrayList<LempOrderDetailsReportDTO>();
	Connection conn = null;
	CallableStatement proc = null;
	ResultSet rs = null;
	int recordCount = 0;
	int countFlag = 0;
	LempOrderDetailsReportDTO objReportsDto = null;
	ArrayList<LempOrderDetailsReportDTO> listSearchDetails = new ArrayList<LempOrderDetailsReportDTO>();
	
		try {
		
		conn = DbConnection.getReportsConnectionObject();
	
		proc = conn.prepareCall(sqlLEPMOrderDetailReport);
		if (objDto.getOrderType() != null
				&& !"".equals(objDto.getOrderType())) {
			proc.setString(1, objDto.getOrderType().trim());
		} else {
			proc.setNull(1, java.sql.Types.VARCHAR);
		}
		//[202020]START
		if (objDto.getCopcApproveFromDate() != null	&& !"".equals(objDto.getCopcApproveFromDate()) && objDto.getCopcApproveToDate() != null	&& !"".equals(objDto.getCopcApproveToDate())) {
			proc.setString(2, objDto.getCopcApproveFromDate().trim());
			proc.setString(3, objDto.getCopcApproveToDate().trim());
		} else {
			proc.setNull(2, java.sql.Types.VARCHAR);
			proc.setNull(3, java.sql.Types.VARCHAR);
		}
		//[202020]END
		if (objDto.getVerticalDetails() != null && !"".equals(objDto.getVerticalDetails())) {
			proc.setString(4, objDto.getVerticalDetails().trim());
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
		rs = proc.executeQuery();
		

		while (rs.next() != false) 
		{
			objDto = new LempOrderDetailsReportDTO();
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
			objDto.setChargeAmount_String(rs.getString("CHARGEAMOUNT"));
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
			//Vijay [RPT7052013010] -start. 
			 //fetch the only city becoz of the FROM_CITY & TO_CITY has full adDress

			objDto.setFrom_city(rs.getString("FROM_CITY"));
			String frmCity = rs.getString("FROM_CITY");
			try{
				 if(frmCity !=null){
				 String[] fcity = frmCity.split("~~");
				 objDto.setFrom_city(fcity[5]);
				 }
			 }
			 catch(IndexOutOfBoundsException e)
			 {
				 //e.printStackTrace();
			 }
			 
			 objDto.setTo_city(rs.getString("TO_CITY"));
			 String toCity = rs.getString("TO_CITY");
			try{
				 if(toCity !=null){
				 String[] tcity = toCity.split("~~");
				 objDto.setTo_city(tcity[5]);
				 }
			 }
			 catch(IndexOutOfBoundsException e)
			 {
				 Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
				 //e.printStackTrace();
			 }
			//Vijay. [RPT7052013010] - end 
			
			objDto.setOldordertotal(rs.getString("OLD_ORDER_TOTAL"));
			objDto.setOldlineamt(rs.getString("OLD_LINE_AMT"));
			objDto.setOld_contract_period(rs.getString("OLD_CONTRACTPERIOD"));
			objDto.setRatio(rs.getString("RATIO"));
			objDto.setTaxation(rs.getString("TAXATIONVALUE"));
			objDto.setFactory_ckt_id(rs.getString("FACTORY_CKT_ID"));
			objDto.setDistance(rs.getString("DISTANCE"));
			objDto.setAccountManager(rs.getString("ACTMEMAIL"));
			objDto.setCurrencyCode(rs.getString("CURNAME"));
			objDto.setTotalPoAmt(rs.getString("ORDERTOTAL"));
			objDto.setPoAmount(rs.getString("POAMOUNT"));
			objDto.setBisource(rs.getString("BISOURCE"));
			objDto.setOrderType(rs.getString("ORDERTYPE"));
			objDto.setDispatchAddress1(rs.getString("DISPATCHADDRESS"));
			objDto.setParent_name(rs.getString("PARENTNAME"));
			//[404040] Start 
			objDto.setOpportunityId((rs.getString("OPPORTUNITYID")));				
			//[404040] End 
			//[505050] Start
			objDto.setOnnetOffnet((rs.getString("OFFNET_LABELATTVALUE")));
			objDto.setMedia((rs.getString("MEDIA_LABELATTVALUE")));
			objDto.setServOrderCategory((rs.getString("ORDERCATGRY_LABELATTVALUE")));
			//[505050] End
			
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
	//end
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

//[005] Start
public ArrayList<LineItemDTO> getLSIMappingDetails(LineItemDTO objDto) 
{
	//	Nagarjuna
	String methodName="getLSIMappingDetails", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;	
	//end
	Connection connection =null;
	CallableStatement getLSIMappingDetails =null;
	ResultSet rsLSIMappingDetails = null;
	ArrayList<LineItemDTO> LSIMappingList = new ArrayList<LineItemDTO>();
	LineItemDTO objLineItemDTO = null;
	int recordCount=0;
	try
	{
		connection=DbConnection.getReportsConnectionObject();
		
		getLSIMappingDetails= connection.prepareCall(sqlGetLSIMappingDetails);
		//PagingSorting pagingSorting = objDto.getPagingSorting();
		getLSIMappingDetails.setString(1,objDto.getLsiNO());
		getLSIMappingDetails.setString(2,objDto.getRedirectedLSINo());
		getLSIMappingDetails.setString(3,objDto.getMappedLSINo());
		if(objDto.getAccountNo().equals(""))
		{
			getLSIMappingDetails.setString(4,null );
		}
		else 
		{
			getLSIMappingDetails.setString(4,objDto.getAccountNo());
		}
		//getLSIMappingDetails.setString(4,objDto.getAccountNo());
		getLSIMappingDetails.setString(5,objDto.getAccountName());
		getLSIMappingDetails.setString(6, objDto.getPagingDto().getSortBycolumn());
		getLSIMappingDetails.setString(7, objDto.getPagingDto().getSortByOrder());
		getLSIMappingDetails.setInt(8, objDto.getPagingDto().getStartIndex());
		getLSIMappingDetails.setInt(9, objDto.getPagingDto().getEndIndex());
		getLSIMappingDetails.setInt(10, objDto.getPagingRequired());// end
		
		rsLSIMappingDetails = getLSIMappingDetails.executeQuery();
		while(rsLSIMappingDetails.next())
		{
			objLineItemDTO =  new LineItemDTO();
			objLineItemDTO.setAccountNo(rsLSIMappingDetails.getString("CRMACCOUNTNO"));
			objLineItemDTO.setAccountName(rsLSIMappingDetails.getString("ACCOUNTNAME"));
			objLineItemDTO.setLsiNO(rsLSIMappingDetails.getString("LSINO"));
			objLineItemDTO.setProductName(rsLSIMappingDetails.getString("PRODUCTNAME"));
			objLineItemDTO.setLineNo(rsLSIMappingDetails.getString("LINEITEMNO"));
			objLineItemDTO.setLineName(rsLSIMappingDetails.getString("LINEITEMNAME"));
			objLineItemDTO.setRedirectedLSINo(rsLSIMappingDetails.getString("REDLSINO"));
			objLineItemDTO.setRedirectedProductName(rsLSIMappingDetails.getString("REDPRODUCTNAME"));
			objLineItemDTO.setRedirectedLineNo(rsLSIMappingDetails.getString("REDLINENO"));
			objLineItemDTO.setRedirectedLineName(rsLSIMappingDetails.getString("REDLINENAME"));
			objLineItemDTO.setMappedLSINo(rsLSIMappingDetails.getString("MAPPEDLSINO"));
			objLineItemDTO.setMappedProductName(rsLSIMappingDetails.getString("MAPPEDSERVICENAME"));
			objLineItemDTO.setMappedLineNo(rsLSIMappingDetails.getString("MAPPEDLINENO"));
			objLineItemDTO.setMappedLineName(rsLSIMappingDetails.getString("MAPPEDLINEITEMNAME"));
			if (objDto.getPagingRequired()==1) 
			{
				recordCount = rsLSIMappingDetails.getInt("FULL_REC_COUNT");
				objLineItemDTO.getPagingDto().setRecordCount(recordCount);
			}
			
			objLineItemDTO.setMaxPageNo(objLineItemDTO.getPagingDto().getMaxPageNumber());
			
			LSIMappingList.add(objLineItemDTO);
		}
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
			DbConnection.closeResultset(rsLSIMappingDetails);
			DbConnection.closeCallableStatement(getLSIMappingDetails);
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}
	}
	return LSIMappingList;
}	
//[005] Start
//[101010]START
public ArrayList<OrderClepReportDTO> viewClepOrderReport(OrderClepReportDTO objDto)
throws Exception 
{
	//	Nagarjuna
	String methodName="viewClepOrderReport", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;	
	//end
	ArrayList<OrderClepReportDTO> objUserList = new ArrayList<OrderClepReportDTO>();
	Connection conn = null;
	CallableStatement proc = null;
	ResultSet rs = null;
	int recordCount = 0;
	//Utility utility=new Utility();
	//DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	try 
	{
		conn = DbConnection.getReportsConnectionObject();
		proc = conn.prepareCall(sqlClepOrderReport);
		PagingSorting pagingSorting = objDto.getPagingSorting();
		proc.setInt(1, pagingSorting.getStartRecordId());// start index
		proc.setInt(2, pagingSorting.getEndRecordId());// end index
		//proc.setInt(3, (pagingSorting.isPagingToBeDone() ? 1 : 0));
		proc.setInt(3, (pagingSorting.isPagingToBeDone() ? 1 : 0));
		System.out.println("pagingSorting.isPagingToBeDone() :"+pagingSorting.isPagingToBeDone());
		rs = proc.executeQuery();
		int countFlag = 0;
		while (rs.next() != false) 
		{
			countFlag++;
			recordCount++;
			objDto = new OrderClepReportDTO();
			objDto.setOrderNumber(rs.getInt("ORDERNO"));
			objDto.setOrderType(rs.getString("ORDERTYPE"));
			objDto.setOrderStage(rs.getString("STAGE"));
			objDto.setM6OrderDate(rs.getString("CREATEDDATE"));
			objDto.setAccountID(rs.getInt("ACCOUNTID"));
			objDto.setAccountName(rs.getString("ACCOUNTNAME"));
			objDto.setCrmAccountNo(rs.getInt("CRMACCOUNTNO"));
			objUserList.add(objDto);
			if (pagingSorting.isPagingToBeDone()) 
			{
				recordCount = rs.getInt("FULL_REC_COUNT");
			}
		}
		
		objUserList.add(objDto);
		System.out.println("Total records for clep reports :"+recordCount);
		pagingSorting.setRecordCount(recordCount);
	} 	
	catch (Exception ex) 
	{
		Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		//System.out.println("SQL Exception : " + ex.getMessage());
		//ex.printStackTrace();
		//throw new Exception("SQL Exception : " + ex.getMessage(), ex);
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
//[101010]END
/*[HYPR22032013001] -- start */

public ArrayList<BillingWorkQueueReportDTO> viewBillingWorkQueueList(BillingWorkQueueReportDTO objDto2) throws Exception {
	//	Nagarjuna
	String methodName="viewBillingWorkQueueList", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;	
	//end
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
		if (objDto2.getOrderType() != null
				&& !"".equals(objDto2.getOrderType())) {
			proc.setString(2, objDto2.getOrderType().trim());
		} else {
			proc.setNull(2, java.sql.Types.VARCHAR);
		}
		if (objDto2.getFromDate() != null
				&& !"".equals(objDto2.getFromDate())) {
			proc.setString(3, objDto2.getFromDate().trim());
		} else {
			proc.setNull(3, java.sql.Types.VARCHAR);
		}
		if (objDto2.getToDate() != null && !"".equals(objDto2.getToDate())) {
			proc.setString(4, objDto2.getToDate().trim());
		} else {
			proc.setNull(4, java.sql.Types.VARCHAR);
		}

		if (objDto2.getFromOrderNo() != 0 
				&& !"".equals(objDto2.getFromOrderNo())) {
			proc.setInt(5, objDto2.getFromOrderNo());
		} else {
			proc.setNull(5,java.sql.Types.BIGINT);
		}
		if (objDto2.getToOrderNo() != 0 
				&& !"".equals(objDto2.getToOrderNo())) {
			proc.setInt(6, objDto2.getToOrderNo());
		} else {
			proc.setNull(6, java.sql.Types.BIGINT);
		}
		
		/*
		 * add party no and party name
		 */
		if (objDto2.getParty_no() != 0 
				&& !"".equals(objDto2.getParty_no())) {
			proc.setInt(7, objDto2.getParty_no());
		} else {
			proc.setNull(7, java.sql.Types.BIGINT);
		}
		if (objDto2.getPartyName() != null
				&& !"".equals(objDto2.getPartyName())) {
			proc.setString(8, objDto2.getPartyName().trim());
		} else {
			proc.setNull(8, java.sql.Types.VARCHAR);
		}
		if (objDto2.getOrderStage() != null
				&& !"".equals(objDto2.getOrderStage())) {
			proc.setString(9, objDto2.getOrderStage().trim());
		} else {
			proc.setNull(9, java.sql.Types.VARCHAR);
		}
		if (objDto2.getHardwareType() != null
				&& !"".equals(objDto2.getHardwareType())) {
			proc.setString(10, objDto2.getHardwareType().trim());
		} else {
			proc.setNull(10, java.sql.Types.VARCHAR);
		}
		PagingSorting pagingSorting = objDto2.getPagingSorting();
		pagingSorting.sync();// To calculate start index and Enc Index

		proc.setString(11, pagingSorting.getSortByColumn());// columnName
		proc.setString(12, PagingSorting.DB_Asc_Desc(pagingSorting
				.getSortByOrder()));// sort order
		proc.setInt(13, pagingSorting.getStartRecordId());// start index
		proc.setInt(14, pagingSorting.getEndRecordId());// end index
		proc.setInt(15, (pagingSorting.isPagingToBeDone() ? 1 : 0));// end
		//proc.setInt(15, (pagingSorting.isPagingToBeDone() ? 0 : 0));// end
		// index
System.out.println("sqlBillingWorkQueue :"+sqlBillingWorkQueue);
		rs = proc.executeQuery();
		int countFlag = 0;
		BillingWorkQueueReportDTO objdto ;
		
		while (rs.next() != false) {
			countFlag++;
		//	System.out.println("in while roop of rs");
			objdto = new BillingWorkQueueReportDTO();
			objdto.setLogicalSINo(rs.getString("LOGICAL_CIRCUIT_ID"));
			objdto.setCustSINo(rs.getString("CUST_LOGICAL_SI_ID"));
			objdto.setServiceName(rs.getString("SERVICE_NAME"));
			objdto.setLinename(rs.getString("LINE_NAME"));
			objdto.setChargeTypeName(rs.getString("CHARGE_TYPE"));
			objdto.setChargeTypeID(rs.getInt("CHARGE_TYPE_ID")); 
			objdto.setChargeName(rs.getString("CHARGE_NAME"));
			objdto.setChargeFrequency(rs.getString("FREQUENCY"));
			objdto.setBillPeriod(rs.getString("BILL_PERIOD"));
			/*
			 * newly added fields in code
			 */
			objdto.setEndDateLogic(rs.getString("ENDDATELOGIC"));
			if (rs.getString("CHARGE_START_DATE") != null && !"".equals(rs.getString("CHARGE_START_DATE")))
			{
				objdto.setStartDate(rs.getString("CHARGE_START_DATE"));
				//Date date=df.parse(objdto.getStartDate());
				//objdto.setStartDate((utility.showDate_Report(date)).toUpperCase());
			}
			if (rs.getString("CHARGE_END_DATE") != null && !"".equals(rs.getString("CHARGE_END_DATE")))
			{
				objdto.setChargeEndDate(rs.getString("CHARGE_END_DATE"));
			}
			objdto.setAdvance(rs.getString("ADVANCE"));
			objdto.setRate_code(rs.getString("TRAI_RATE"));
			objdto.setDiscount(rs.getString("DISCOUNT"));
			objdto.setInstallRate(rs.getString("INSTALRATE"));
			objdto.setInterestRate(rs.getInt("INTREST_RATE"));
			objdto.setPrincipalAmount(rs.getInt("PRINCIPAL_AMOUNT"));
			objdto.setNoticePeriod(rs.getLong("NOTICEPERIOD"));
			objdto.setPenaltyClause(rs.getString("PENALTYCLAUSE"));
			objdto.setCommitmentPeriod(rs.getInt("COMMITMENTPERIOD"));
			
			if (rs.getString("WARRANTY_START_DATE") != null && !"".equals(rs.getString("WARRANTY_START_DATE")))
			{
				objdto.setWarrantyStartDate(rs.getString("WARRANTY_START_DATE"));
				objdto.setPoDate((utility.showDate_Report(objdto.getWarrantyStartDate())).toUpperCase());
			}
			if (rs.getString("WARRANTY_END_DATE") != null && !"".equals(rs.getString("WARRANTY_END_DATE")))
			{
				objdto.setWarrantyEndDate(rs.getString("WARRANTY_END_DATE"));
				objdto.setPoDate((utility.showDate_Report(objdto.getWarrantyEndDate())).toUpperCase());
			}
			objdto.setExtSuportEndDate(rs.getString("EXT_SUPPORT_END_DATE"));
			objdto.setContractStartDate(rs.getString("CONTRACT_START_DATE"));
			objdto.setContractEndDate(rs.getString("CONTRACT_END_DATE"));
			objdto.setStartDateLogic(rs.getString("STARTDATELOGIC"));
			objdto.setDnd_Dispatch_And_Delivered(rs.getString("DND_DISPATCH_AND_DELIVERED"));
			objdto.setDnd_Dispatch_But_Not_Delivered(rs.getString("DND_DISPATCH_BUT_NOT_DELIVERED"));
			objdto.setBilling_address(rs.getString("BILLING_ADDRESS"));
			objdto.setServiceTypeDescription(rs.getString("SERVICE_ORDER_TYPE_DESC"));
			objdto.setCancelBy(rs.getString("CANCEL_BY"));
			objdto.setCanceldate(rs.getString("CANCEL_DATE"));
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
			objdto.setBillingMode(rs.getString("BILLINGMODE"));
			objdto.setBillingTypeName(rs.getString("BILL_TYPE"));
			objdto.setBillingformat(rs.getString("BILL_FORMAT"));
			objdto.setLicCompanyName(rs.getString("LICENSE_COMP"));
			//objdto.setTaxation(rs.getString("TAXATIONVALUE")); // is it TAXATION
			objdto.setTaxation(rs.getString("TAXATION"));
			objdto.setBillingLevelNo(rs.getLong("BILLING_LEVEL_NO")); 
			//objdto.setBillingLevelName(rs.getString("BILLING_LEVELNAME")); // is it BILLINGLEVEL
			objdto.setBillingLevelName(rs.getString("BILLINGLEVEL"));
			//objdto.setStore(rs.getString("STORENAME")); // is it STORE 
			objdto.setStore(rs.getString("STORE"));
			objdto.setHardwaretypeName(rs.getString("HARDWARETYPE"));
			objdto.setFormAvailable(rs.getString("FORM_C_AVAILABLE"));
			objdto.setSaleNature(rs.getString("NATURE_OF_SALE"));
			objdto.setSaleTypeName(rs.getString("TYPE_OF_SALE"));
			objdto.setPrimaryLocation(rs.getString("PRIMLOC"));
			objdto.setSeclocation(rs.getString("SECLOC"));
			objdto.setPoNumber(rs.getInt("PODETAILNUMBER"));
			objdto.setPoDate(rs.getString("PODATE"));
			if (rs.getString("PODATE") != null && !"".equals(rs.getString("PODATE")))
			{
				
				//Date date=df.parse(objdto.getPoDate());
				objdto.setPoDate((utility.showDate_Report(objdto.getPoDate())).toUpperCase());
			}
			
			objdto.setParty_num(rs.getString("PARTY_NO"));  
			objdto.setChargeAnnotation(rs.getString("ANNOTATION"));
			objdto.setFx_sd_charge_status(rs.getString("FX_SD_CHG_STATUS"));
			
			objdto.setFx_charge_status(rs.getString("FX_STATUS"));
			objdto.setFx_Ed_Chg_Status(rs.getString("FX_ED_CHG_STATUS"));
			//objdto.setTokenID(rs.getInt("TOKEN_ID")); // is it TOKEN_NO
			objdto.setTokenno(rs.getString("TOKEN_NO"));
			objdto.setModifiedDate(rs.getString("LAST_UPDATE_DATE"));
			if (rs.getString("LAST_UPDATE_DATE") != null && !"".equals(rs.getString("LAST_UPDATE_DATE")))
			{
				objdto.setModifiedDate((utility.showDate_Report(new Date(rs.getTimestamp("LAST_UPDATE_DATE").getTime()))).toUpperCase());
			}
			objdto.setBillingTriggerFlag(rs.getString("BILLING_TRIG_FLAG"));
			
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
			objdto.setChallenno(rs.getString("CHALLEN_NO"));
			objdto.setChallendate(rs.getString("CHALLEN_DATE"));
			
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
			objdto.setChild_ac_fxstatus(rs.getString("CHILD_ACCOUNT_FX_STATUS"));
			
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
			objdto.setCharge_status(rs.getString("CHARGES_STATUS"));
			objdto.setLOC_No(rs.getString("LOC_NUMBER"));
			//objdto.setAddress1(rs.getString("ADDRESS")); // is it BILLING_ADDRESS
			objdto.setAddress1(rs.getString("BILLING_ADDRESS"));
			objdto.setM6cktid(rs.getString("CIRCUIT_ID")); 
			objdto.setOrdersubtype(rs.getString("ORDERSUBTYPE"));
			objdto.setRegion(rs.getString("REGION"));
			objdto.setBandwaidth(rs.getString("BILLING_BANDWIDTH"));     
			objdto.setVertical(rs.getString("VERTICAL"));
			objdto.setAccountManager(rs.getString("ACCOUNT_MGR"));
			objdto.setProjectManager(rs.getString("PROJECT_MGR"));
			objdto.setDistance(rs.getString("CHARGEABLE_DISTANCE"));
			objdto.setDispatchAddress1(rs.getString("DISPATCH_ADDRESS"));
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
			objdto.setChargeAmount(rs.getDouble("INV_AMT"));
			// LINEITEMAMOUNT = ?
			//objdto.setLineamt(rs.getDouble("LINEITEMAMOUNT"));
			//TOTAL_CHARGE_AMT = ?
			//objdto.setChargesSumOfLineitem(rs.getDouble("TOTAL_CHARGE_AMT"));
			objdto.setContractPeriod(rs.getInt("CONTRACT_PERIOD_MNTHS"));
			
			//objdto.setTotalPoAmt(""+rs.getDouble("TOTAL_POAMOUNT")); // is it POAMOUNT
			objdto.setTotalPoAmt(""+rs.getDouble("POAMOUNT"));
			//PARTY_ID = ?
			//objdto.setParty_id(rs.getInt("PARTY_ID")); // is it party no
			
			// CRMACCOUNTNO = ?
			//objdto.setCrmAccountNoString(rs.getString("CRMACCOUNTNO"));
			//m6 productid
			objdto.setM6OrderNo(rs.getInt("M6_ORDER_ID"));
			
			// CUST_TOT_PO_AMT = ?
			//objdto.setCust_total_poamt(rs.getDouble("CUST_TOT_PO_AMT"));
			
			//m6 order,business
			// PK_CHARGE_ID = ?
		     //objdto.setPk_charge_id(rs.getString("PK_CHARGE_ID"));//Added by Ashutosh as on 26-Jun-12
		     
			// objdto.setContractPeriod(rs.getInt("CONTRACTPERIOD")); // CONTRACT_PERIOD_MNTHS is using above 
		     objdto.setAnnual_rate(rs.getDouble("ANNUAL_RATE"));
		     objdto.setServiceId(rs.getInt("SERVICE_NO"));
			if (rs.getString("PO_EXPIRY_DATE") != null && !"".equals(rs.getString("PO_EXPIRY_DATE")))
			{
				objdto.setPoExpiryDate(rs.getString("PO_EXPIRY_DATE"));
				objdto.setPoExpiryDate((utility.showDate_Report(objdto.getPoExpiryDate())).toUpperCase());
			}
			//lawkush start
			objdto.setOpportunityId((rs.getString("OPPORTUNITYID")));				
			//lawkush end
			
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
/*[HYPR22032013001] -- end */

/*Vijay. start - a new method for cancel copy report*/

public ArrayList<CopyCancelReportDTO> cancelCopyReport(CopyCancelReportDTO objDto) throws Exception {

	//	Nagarjuna
	String methodName="cancelCopyReport", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;	
	//end
	ArrayList<CopyCancelReportDTO> objUserList = new ArrayList<CopyCancelReportDTO>();
	Connection conn = null;
	CallableStatement proc = null;
	ResultSet rs = null;
	int recordCount = 0;
	Utility utility=new Utility();
	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	try {

		conn = DbConnection.getReportsConnectionObject();

		proc = conn.prepareCall(sqlCancelCopyReport);
		
		if (objDto.getFromDate() != null
				&& !"".equals(objDto.getFromDate())) {
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");	
			Date dateStr = formatter.parse(objDto.getFromDate());
			String formattedDate = formatter.format(dateStr);
			Date date1 = formatter.parse(objDto.getFromDate());
			formatter = new SimpleDateFormat("MM-dd-yyyy");
			formattedDate = formatter.format(date1);
			proc.setString(1,formattedDate);
			//proc.setString(1, objDto.getFromDate().trim());
		} else {
			proc.setNull(1, java.sql.Types.VARCHAR);
		}
		if (objDto.getToDate() != null && !"".equals(objDto.getToDate())) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");	
			Date dateStr = formatter.parse(objDto.getToDate());
			String formattedDate = formatter.format(dateStr);
			Date date1 = formatter.parse(objDto.getToDate());
			formatter = new SimpleDateFormat("MM-dd-yyyy");
			formattedDate = formatter.format(date1);
			proc.setString(2,formattedDate);
			//proc.setString(2, objDto.getToDate().trim());
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
		proc.setInt(7, (pagingSorting.isPagingToBeDone() ? 1 : 0));// end

		rs = proc.executeQuery();
		int countFlag = 0;
		while (rs.next() != false) {
			countFlag++;

			objDto = new CopyCancelReportDTO();
			
			objDto.setOldOrderNo(rs.getInt("OLDORDERNO"));
			objDto.setNewOrderNo(rs.getInt("NEWORDERNO"));
			objDto.setRootOrderNo(rs.getInt("ROOTORDERNO"));
			objDto.setOldServiceNo(rs.getInt("OLDSERVICENO"));
			objDto.setNewServiceNo(rs.getInt("NEWSERVICENO"));
			objDto.setCreatedBy(rs.getString("CREATEDBY"));
			objDto.setCreatedDate(rs.getString("CREATEDDATE"));	
			if (rs.getString("CREATEDDATE") != null && !"".equals(rs.getString("CREATEDDATE")))
			{
				//Date date=df.parse(objDto.getCreatedDate());
				//objDto.setCreatedDate((utility.showDate_Report(date)).toUpperCase());
				objDto.setCreatedDate((utility.showDate_Report(new Date(rs.getTimestamp("CREATEDDATE").getTime()))).toUpperCase());
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

/*Vijay end */

//[006] Start
	public static String SPGETCANCELHARDWARELINEITEMREPORT="{call IOE.SP_GET_CANCEL_HARDWARE_LINE_REPORT(?,?,?,?,?,?,?,?,?,?,?,?)}";//SP_GET_SR_ORDER_STATUS_REPORT
	public ArrayList<HardwareLineItemCancelReportDTO> viewHardwareCancelReport(HardwareLineItemCancelReportDTO objDto)throws Exception 
	{
		//	Nagarjuna
		String methodName="viewHardwareCancelReport", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;	
		//end
	   ArrayList<HardwareLineItemCancelReportDTO> objHardwareLineItemList = new ArrayList<HardwareLineItemCancelReportDTO>();
	   Connection conn = null;
	   ResultSet rs = null;
	   CallableStatement getHardwareLineItemList = null;
		try 
		{
			conn = DbConnection.getConnectionObject();
			getHardwareLineItemList = conn.prepareCall(SPGETCANCELHARDWARELINEITEMREPORT);
			String searchLineItemNo = objDto.getSearchLineNo();
			String searchfromDate=objDto.getSearchfromDate();
			String searchToDate = objDto.getSearchToDate();
			String searchSrno=objDto.getSearchSRNO();
			String searchLSI=objDto.getSearchLSI();
			String searchAccountNo=objDto.getSearchAccount();
			String searchAccountName=objDto.getSearchAccountName();
			
			if (searchAccountNo == null || searchAccountNo.trim().equals("")) 
			{
				getHardwareLineItemList.setNull(1, java.sql.Types.VARCHAR);
			} 
			else 
			{
				getHardwareLineItemList.setString(1, searchAccountNo);
			}
			if (searchAccountName == null || searchAccountName.trim().equals("")) 
			{
				getHardwareLineItemList.setNull(2, java.sql.Types.VARCHAR);
			} 
			else 
			{
				getHardwareLineItemList.setString(2, searchAccountName);
			}
			if (searchfromDate == null || searchfromDate.trim().equals("")) 
			{
				getHardwareLineItemList.setNull(3, java.sql.Types.VARCHAR);
			} 
			else 
			{
				getHardwareLineItemList.setString(3, searchfromDate);
			}
			if (searchToDate == null || searchToDate.trim().equals("")) 
			{
				getHardwareLineItemList.setNull(4, java.sql.Types.VARCHAR);
			} 
			else 
			{
				getHardwareLineItemList.setString(4, searchToDate);
			}
			
			if (searchSrno == null || searchSrno.trim().equals("")) 
			{
				getHardwareLineItemList.setNull(5, java.sql.Types.VARCHAR);
			} 
			else 
			{
				getHardwareLineItemList.setString(5, searchSrno);
			}
			
			if (searchLSI == null || searchLSI.trim().equals("")) 
			{
				getHardwareLineItemList.setNull(6, java.sql.Types.VARCHAR);
			} 
			else 
			{
				getHardwareLineItemList.setString(6, searchLSI);
			}
			if (searchLineItemNo == null || searchLineItemNo.trim().equals(""))
			{
				getHardwareLineItemList.setNull(7, java.sql.Types.BIGINT);
			} 
			else 
			{
				getHardwareLineItemList.setString(7, searchLineItemNo);
			}
			
			SimpleDateFormat sdf  = new SimpleDateFormat("dd-MM-yyyy");
			PagingSorting pagingSorting = objDto.getPagingSorting();
			pagingSorting.sync();// To calculate start index and Enc Index
			
			getHardwareLineItemList.setString(8, pagingSorting.getSortByColumn());// columnName
			getHardwareLineItemList.setString(9, PagingSorting.DB_Asc_Desc1(pagingSorting
					.getSortByOrder()));// sort order
			getHardwareLineItemList.setInt(10, pagingSorting.getStartRecordId());// start index
			getHardwareLineItemList.setInt(11, pagingSorting.getEndRecordId());// end index
			getHardwareLineItemList.setInt(12, (pagingSorting.isPagingToBeDone() ? 1 : 0));// end
			// index
			rs = getHardwareLineItemList.executeQuery();
			
			int countFlag = 0;
			int recordCount = 0;
			while (rs.next() != false) 
			{				
				countFlag++;
				objDto = new HardwareLineItemCancelReportDTO();
				objDto.setAccountID(rs.getInt("ACCOUNTID"));
				objDto.setAccountName(rs.getString("ACCOUNTNAME"));
				objDto.setSrno(rs.getString("REQUESTID"));
				objDto.setOrderLineNumber(rs.getInt("LINEITEMNO"));
				objDto.setLineItemName(rs.getString("LINEITEMNAME"));
				objDto.setOrderNo(rs.getString("ORDERNO"));
				objDto.setServiceName(rs.getString("SERVICENAME"));
				objDto.setLogicalSINo(rs.getString("LSINO"));
				objDto.setM6OrderNo(rs.getInt("M6ORDERNO"));
				if(rs.getInt("REQUESTSTATUS")==0 || rs.getInt("REQUESTSTATUS")==1)
				{
					objDto.setRemarks("Open");
				}
				else if (rs.getInt("REQUESTSTATUS")==2)
				{
					objDto.setRemarks("Failed in M6");
				}
				else if (rs.getInt("REQUESTSTATUS")==3)
				{
					objDto.setRemarks("Closed");
				}
				objDto.setCreatedBy(rs.getString("REQUESTEDBY"));
				objDto.setUserId(rs.getString("USER_ID"));
				objDto.setSrDate(rs.getString("REQUESTDATE"));
				if (rs.getString("REQUESTDATE") != null && !"".equals(rs.getString("REQUESTDATE")))
				{
					objDto.setSrDate((Utility.showDate_Report((rs.getTimestamp("REQUESTDATE")))).toUpperCase());
				}	
				if (pagingSorting.isPagingToBeDone()) 
				{
					recordCount = rs.getInt("FULL_REC_COUNT");
				}
				objHardwareLineItemList.add(objDto);
			}
			pagingSorting.setRecordCount(recordCount);
		} 
		catch (Exception ex) 
		{
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			//ex.printStackTrace();
			throw new Exception("SQL Exception : " + ex.getMessage(), ex);
		} 
		finally 
		{
			try 
			{
				rs.close();
				getHardwareLineItemList.close();
				DbConnection.freeConnection(conn);	
			} 
			catch (Exception e) 
			{
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
				//e.printStackTrace();
				throw new Exception("Exception : " + e.getMessage(), e);
			}
		}
		return objHardwareLineItemList;
	}
	
	public static String sqlRequestHistory = "{CALL IOE.GET_REQUEST_HISTORY(?)}";
	public ArrayList<ReportsDto> getRequestHistory(ReportsDto objDto) //Method used to display Hardware Line items for Cancelation
	{
		//	Nagarjuna
		String methodName="getRequestHistory", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;	
		//end
		Connection connection =null;
		CallableStatement getRequestHistory =null;
		ResultSet rsRequestHistory = null;
		ArrayList<ReportsDto> requestHistoryList = new ArrayList<ReportsDto>();
		ReportsDto objReportsDTO = null;
		int recordCount=0;
		try
		{
			connection=DbConnection.getConnectionObject();
			
			getRequestHistory= connection.prepareCall(sqlRequestHistory);
			//[505053] start
			try{
				getRequestHistory.setInt(1, Integer.parseInt(objDto.getSrno()));
			}catch(Exception exp){
				//It's okay to  ignore 'exp' here because setting a default value
				getRequestHistory.setInt(1,AppConstants.INACTIVE_FLAG); //set 0
			}
				
			
			rsRequestHistory = getRequestHistory.executeQuery();
			while(rsRequestHistory.next())
			{
				objReportsDTO =  new ReportsDto();
				objReportsDTO.setSrno(rsRequestHistory.getString("REQUESTID"));
				objReportsDTO.setLineno(rsRequestHistory.getInt("LINEITEMNO"));
				objReportsDTO.setOrderNumber(rsRequestHistory.getInt("ORDERNO"));
				objReportsDTO.setM6OrderNumber(rsRequestHistory.getInt("M6ORDERNO"));
				objReportsDTO.setNeworder_remarks(rsRequestHistory.getString("REMARKS"));
				objReportsDTO.setSrDate(rsRequestHistory.getString("CREATEDATE"));
				requestHistoryList.add(objReportsDTO);
			}
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
				DbConnection.closeResultset(rsRequestHistory);
				DbConnection.closeCallableStatement(getRequestHistory);
				DbConnection.freeConnection(connection);
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				//e.printStackTrace();
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
			}
		}
		return requestHistoryList;
	}	
//[006] End
	//[007] Start
	/*
	 * This Method is used to fetch the Advancedpayment Report Columns Data From DataBase.
	 *
	 */
	public static String sqlAdvancePayementReport="{call IOE.SP_GET_ADVANCE_PAYMENT_REPORT(?,?,?,?,?,?,?,?,?,?,?,?,?)}";  
	public ArrayList<AdvancePaymentReportDTO> viewAdvancePaymentReport(AdvancePaymentReportDTO objDto) throws Exception 
	{
		String methodName="viewAdvancePaymentReport", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;	
		
		ArrayList<AdvancePaymentReportDTO> objUserList = new ArrayList<AdvancePaymentReportDTO>();
		AdvancePaymentReportDTO objReportsDto = null;
		Connection conn = null;
		CallableStatement proc = null;
		ResultSet rs = null;
		int recordCount = 0;
		Utility utility=new Utility();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try 
		{
			conn = DbConnection.getReportsConnectionObject();
			proc = conn.prepareCall(sqlAdvancePayementReport);
			if (objDto.getCrmAccountNo() != null && !"".equals(objDto.getCrmAccountNo())) 
			{
				proc.setString(1, objDto.getCrmAccountNo().trim());
			} 
			else 
			{
				proc.setNull(1, java.sql.Types.VARCHAR);
			}
		  
			if (objDto.getArcChqNo() != null && !"".equals(objDto.getArcChqNo())) 
			{
				proc.setString(2, objDto.getArcChqNo().trim());
			} 
			else 
			{
				proc.setNull(2, java.sql.Types.VARCHAR);
			}
			if (objDto.getOtcChqNo() != null && !"".equals(objDto.getOtcChqNo())) 
			{
				proc.setString(3, objDto.getOtcChqNo().trim());
			} 
			else 
			{
				proc.setNull(3, java.sql.Types.VARCHAR);
			}		
			if (objDto.getFromorderCreationDate() != null && !"".equals(objDto.getFromorderCreationDate())) 
			{
				SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
				proc.setString(4, objDto.getFromorderCreationDate().trim());
			} 
			else 
			{
				proc.setNull(4, java.sql.Types.VARCHAR);
			}
			if (objDto.getToorderCreationDate() != null && !"".equals(objDto.getToorderCreationDate())) 
			{
				proc.setString(5, objDto.getToorderCreationDate().trim());
			} 
			else 
			{
				proc.setNull(5, java.sql.Types.VARCHAR);
			}
			if (objDto.getFromChqDate() != null && !"".equals(objDto.getFromChqDate())) 
			{
				proc.setString(6, objDto.getFromChqDate().trim());
			} 
			else 
			{
				proc.setNull(6, java.sql.Types.VARCHAR);
			}
			if (objDto.getToChqDate() != null && !"".equals(objDto.getToChqDate())) 
			{
				proc.setString(7, objDto.getToChqDate().trim());
			} 
			else 
			{
				proc.setNull(7, java.sql.Types.VARCHAR);
			}
			if (objDto.getDatetype() != null && !"".equals(objDto.getDatetype())) 
			{
				proc.setString(8, objDto.getDatetype().trim());
			} 
			else 
			{
				proc.setNull(8, java.sql.Types.VARCHAR);
			}
			
			SimpleDateFormat sdf  = new SimpleDateFormat("dd-MM-yyyy");
			PagingSorting pagingSorting = objDto.getPagingSorting();
			pagingSorting.sync();// To calculate start index and Enc Index
			
			proc.setString(9, pagingSorting.getSortByColumn());// columnName
			proc.setString(10, PagingSorting.DB_Asc_Desc1(pagingSorting.getSortByOrder()));// sort order
			proc.setInt(11, pagingSorting.getStartRecordId());// start index
			proc.setInt(12, pagingSorting.getEndRecordId());// end index
			proc.setInt(13, (pagingSorting.isPagingToBeDone() ? 1 : 0));// end
			
			rs = proc.executeQuery();
			int countFlag = 0;
			while (rs.next() != false) 
			{
				countFlag++;
				objReportsDto =  new AdvancePaymentReportDTO();
				objReportsDto.setOrderNo(rs.getInt("ORDERNO"));
				objReportsDto.setOrderStatus(rs.getString("ORDER_STATUS"));
				objReportsDto.setOrderCreationDate(rs.getString("ORDER_CREATION_DATE"));
				if (rs.getString("ORDER_CREATION_DATE") != null && !"".equals(rs.getString("ORDER_CREATION_DATE")))
				{
					objReportsDto.setOrderCreationDate((utility.showDate_Report(objReportsDto.getOrderCreationDate())).toUpperCase());	
				}

				objReportsDto.setCrmAccountNo(rs.getString("CRMACCOUNTNO"));
				objReportsDto.setCustomerSegment(rs.getString("CUSTOMERSEGMENT"));
				//objReportsDto.setCircle(rs.getString("CIRCLE"));
				objReportsDto.setLicenseCompany(rs.getString("LICENCE_COMPANY"));
				objReportsDto.setCurrencyofOrder(rs.getString("CURRENCY_ORDER"));
				objReportsDto.setAmApprovalDate(rs.getString("AM_APPROVALS"));
				if (rs.getString("AM_APPROVALS") != null && !"".equals(rs.getString("AM_APPROVALS")))
				{
					//Date date=df.parse(objReportsDto.getCustPoDate());
					objReportsDto.setAmApprovalDate(utility.showDate_Report(objReportsDto.getAmApprovalDate()).toUpperCase());	
				}
				
				objReportsDto.setPmApprovalDate(rs.getString("PM_APPROVALS"));
				if (rs.getString("PM_APPROVALS") != null && !"".equals(rs.getString("PM_APPROVALS")))
				{
					objReportsDto.setPmApprovalDate((utility.showDate_Report(objReportsDto.getPmApprovalDate())));					
				}
				
				objReportsDto.setOrderApprovalDate(rs.getString("COPC_APPROVALS"));
				if (rs.getString("COPC_APPROVALS") != null && !"".equals(rs.getString("COPC_APPROVALS")))
				{
					objReportsDto.setOrderApprovalDate((utility.showDate_Report(objReportsDto.getOrderApprovalDate())));	
				}
				objReportsDto.setServiceNo(rs.getInt("SERVICE_NO"));
				objReportsDto.setProduct(rs.getString("PRODUCT"));
				objReportsDto.setLsi(rs.getInt("LSI"));
				objReportsDto.setFxChildAccount(rs.getString("FX_CHILD_ACCOUNT"));
				if (rs.getString("LOC_DATE") != null && !"".equals(rs.getString("LOC_DATE")))
				{
					Date date=df.parse(rs.getString("LOC_DATE"));
					objReportsDto.setLocDate((utility.showDate_Report(date)).toUpperCase());
				}
				if (rs.getString("BILLINGTRIGGERDATE") != null && !"".equals(rs.getString("BILLINGTRIGGERDATE")))
				{
					Date date=df.parse(rs.getString("BILLINGTRIGGERDATE"));
					objReportsDto.setBillingTriggerDate((utility.showDate_Report(date)).toUpperCase());
				}
				objReportsDto.setArcChqNo(rs.getString("ARCCHQNO"));
				objReportsDto.setArcChqDate(rs.getString("ARCCHQDATE"));
				
				if (rs.getString("ARCCHQDATE") != null && !"".equals(rs.getString("ARCCHQDATE")))
				{
					Date date=df.parse(objReportsDto.getArcChqDate());
					objReportsDto.setArcChqDate((utility.showDate_Report(date)).toUpperCase());
				}
				
				objReportsDto.setArcChqAmt(rs.getDouble("ARCCHQAMT"));
				objReportsDto.setArcBankName(rs.getString("ARCBANKNAME"));
				objReportsDto.setArcAmtAjd(rs.getDouble("ARCAMTAJD"));
				//objReportsDto.setArcAmtAjd(rs.getString("ARCAMTAJD"));
				objReportsDto.setOtcChqNo(rs.getString("OTCCHQNO"));
				objReportsDto.setArcExempted(rs.getString("ARCEXEMPTED"));
				objReportsDto.setArcExpreason(rs.getString("ARCEXPREASON"));
				objReportsDto.setOtcExempted(rs.getString("OTCEXEMPTED"));
				objReportsDto.setOtcExpreason(rs.getString("OTCEXPREASON"));
				objReportsDto.setOtcChqDate(rs.getString("OTCCHQDATE"));
				
				//Start [128]
				objReportsDto.setArcReEnterCheckamount(rs.getDouble("ARCRECHQAMT"));
				objReportsDto.setArcReEnterCheckNumber(rs.getString("ARCRECHQNO"));
				objReportsDto.setArcBankAccountNumber(rs.getString("ARCBANKACNO"));
				objReportsDto.setArcReEnterBankAccountNumber(rs.getString("ARCREBANKACNO"));
				objReportsDto.setArcIfscCode(rs.getString("ARCIFSCCODE"));
				objReportsDto.setArcReEnterIfscCode(rs.getString("ARCREIFSCCODE"));
				
				objReportsDto.setOtcReEnterCheckamount(rs.getDouble("OTCRECHQAMT"));
				objReportsDto.setOtcReEnterCheckNumber(rs.getString("OTCRECHQNO"));
				objReportsDto.setOtcBankAccountNumber(rs.getString("OTCBANKACNO"));
				objReportsDto.setOtcReEnterBankAccountNumber(rs.getString("OTCREBANKACNO"));
				objReportsDto.setOtcIfscCode(rs.getString("OTCIFSCCODE"));
				objReportsDto.setOtcReEnterIfscCode(rs.getString("OTCREIFSCCODE"));
				//End [128]
				
				
				objReportsDto.setLineNo(rs.getString("SPID"));
				objReportsDto.setLineName(rs.getString("LineName"));
				if (rs.getString("OTCCHQDATE") != null && !"".equals(rs.getString("OTCCHQDATE")))
				{
					Date date=df.parse(objReportsDto.getOtcChqDate());
					objReportsDto.setOtcChqDate((utility.showDate_Report(date)).toUpperCase());
				}
				objReportsDto.setOtcChqAmt(rs.getDouble("OTCCHQAMT"));
				objReportsDto.setOtcBankName(rs.getString("OTCBANKNAME"));
				objReportsDto.setOtcAmtAjd(rs.getDouble("OTCAMTAJD"));
				objReportsDto.setPartyNo(rs.getString("PARTY_NO"));
				objReportsDto.setAccountName(rs.getString("ACCOUNTNAME"));
				objReportsDto.setBillingTriggerActionDate(rs.getTimestamp("BILLINGTRIGGERDATEACTION"));
				String BillActionDate_String=utility.showDate_Report(objReportsDto.getBillingTriggerActionDate());
				objReportsDto.setBillingTriggerActionDate_string(BillActionDate_String);
				objReportsDto.setProductName(rs.getString("PRODUCTNAME"));
				objReportsDto.setSubproductName(rs.getString("SERVICESUBTYPENAME"));
				objReportsDto.setParty_id(rs.getString("PARTY_ID"));
				objReportsDto.setPodate(rs.getDate("PODATE"));
				String poDate_String=utility.showDate_Report(objReportsDto.getPodate());
				objReportsDto.setPodate_String(poDate_String);
				objReportsDto.setPoRecieveDate(rs.getDate("PORECEIVEDATE"));
				String poRDate_String=utility.showDate_Report(objReportsDto.getPoRecieveDate());
				objReportsDto.setPoRecieveDate_String(poRDate_String);
				objReportsDto.setZoneName(rs.getString("ZONENNAME"));
				if (pagingSorting.isPagingToBeDone()) 
				{
					recordCount = rs.getInt("FULL_REC_COUNT");
				}
				
				objUserList.add(objReportsDto);
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
		//[007] End
	
	//[009] Start
	//[133]
	public static String sqlBTLineItemDump="{call BULKUPLOAD.GET_BILLING_TRIGGER_LINEITEM_DATA(?)}";  
	//public static String sqlBTLineItemDump="{call BULKUPLOAD.GET_BILLING_TRIGGER_LINEITEM_DATA()}";  
	public ArrayList<AdvancePaymentReportDTO> fetchTBTLineItemData(AdvancePaymentReportDTO objDto) throws Exception 
	{
		String methodName="viewAdvancePaymentReport", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;	
		
		ArrayList<AdvancePaymentReportDTO> objUserList = new ArrayList<AdvancePaymentReportDTO>();
		AdvancePaymentReportDTO objReportsDto = null;
		Connection conn = null;
		CallableStatement proc = null;
		ResultSet rs = null;
		int recordCount = 0;
		//[133]
		int actiontype = objDto.getActiontype();
		Utility utility=new Utility();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try 
		{
			conn = DbConnection.getConnectionObject();
			proc = conn.prepareCall(sqlBTLineItemDump);	
			//[133] 
			proc.setInt(1,actiontype);
			rs = proc.executeQuery();
			int countFlag = 0;
			while (rs.next() != false) 
			{
				countFlag++;
				objReportsDto =  new AdvancePaymentReportDTO();
				objReportsDto.setLineNo(rs.getString("LINENUMBER"));
				objReportsDto.setLineName(rs.getString("LINENAME"));
				objReportsDto.setLsi(rs.getInt("LOGICALSINO"));
				objReportsDto.setClsi(rs.getInt("CUSTLOGICALSINO"));
				objReportsDto.setOrderNo(rs.getInt("ORDERNO"));
				objReportsDto.setOrderType(rs.getString("ORDERTYPE"));
				objReportsDto.setChange_OrderType(rs.getString("CHANGETYPENAME"));
				objReportsDto.setSiID(rs.getString("SIID"));
				objReportsDto.setFxChildAccount(rs.getString("FX_ACCOUNT_EXTERNAL_ID"));
				objReportsDto.setFxStatus(rs.getString("FX_STATUS"));
				objReportsDto.setLineStatus(rs.getString("LINE_STATUS"));
				objReportsDto.setChallanNo(rs.getString("CHALLEN_NO"));
				objReportsDto.setChallanDate(rs.getString("CHALLEN_DATE"));
				objReportsDto.setLocNo(rs.getString("LOCNO"));	
				objReportsDto.setLoc_Recv_Date(rs.getString("LOC_REC_DATE"));
				objReportsDto.setLocDate(rs.getString("LOCDATE"));
				objReportsDto.setCustomerSegment(rs.getString("CUST_SEGMENT_CODE"));
				//[133] start
				objReportsDto.setTriggerprocess(rs.getString("TRIGGER_PROCESS"));
				objReportsDto.setLocupdateprocess(rs.getString("LOC_UPDATE_PROCESS"));
				if(actiontype == 2)
				{
					objReportsDto.setAutomatictriggereror(rs.getString("AUTOMATIC_TRIGGER_ERROR"));
					objReportsDto.setTriggerprocesspendencystart(rs.getTimestamp("Trigger_Process_Pendency_Start"));
					String Trigger_Pendency_Start_String = utility.showDate_Report5(objReportsDto.getTriggerprocesspendencystart());
					objReportsDto.setTriggerprocesspendencystart_String(Trigger_Pendency_Start_String);
					objReportsDto.setLocupdateprocesspendencystart(rs.getTimestamp("Loc_Update_Process_Pendency_Start"));
					String Loc_Pendency_start_String = utility.showDate_Report5(objReportsDto.getLocupdateprocesspendencystart());
					objReportsDto.setLocupdateprocesspendencystart_String(Loc_Pendency_start_String);
				}
				//[133] end
				objUserList.add(objReportsDto);
			}
		} 
		catch (Exception ex) 
		{
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//nagarjuna
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
		//[009] End
	public void viewReportUsageDetails(ReportsDto rptdtoobj) throws Exception{
		
		Connection conn = null;
		PreparedStatement ps=null,ps1=null;
		
		try {
			conn = DbConnection.getConnectionObject();
			
			String s= rptdtoobj.getInterfaceId();
			System.out.println("interface id>>"+s);
			int interfaceId=Integer.parseInt(s);
			System.out.println("interface id>>"+interfaceId);
			
			ps=conn.prepareStatement("INSERT INTO IOE.TRN_REPORT_USAGE_DETAILS(USERID,INTERFACEID,ACTIONTYPE,ACTIONTIMESTAMP)VALUES(?,?,?,current timestamp)");
			ps.setString(1, rptdtoobj.getUserId());
			ps.setInt(2, interfaceId);
			ps.setString(3, rptdtoobj.getActionType());
			
			ps.execute();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Utility.LOG(true, true, e, "error in viewReportUsageDetails method");
		}
		finally{
			try {
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				throw new Exception("Exception : " + e.getMessage(), e);
			}
		}
		
		
	}
	
	//[505051] Start
		public ArrayList<ActiveLineDemoReportDTO> viewActiveLineDemoReportDetails(ActiveLineDemoReportDTO reportsDto) {
			
			AppConstants.IOES_LOGGER.info("ReportsDao: viewActiveLineDemoReportDetails is executing...");
			Connection connection = null;
			ResultSet resultSet = null;
			CallableStatement callableStatement = null;
			ArrayList<ActiveLineDemoReportDTO> activeLineDemoReportDTOsList = null;
			try {
				
				connection = DbConnection.getReportsConnectionObject();
				callableStatement = connection.prepareCall(spGetActiveLineDemoReport);
				activeLineDemoReportDTOsList =  new ArrayList<ActiveLineDemoReportDTO>();
				String orderFromDate = reportsDto.getOrderDate();
				String orderToDate = reportsDto.getOrderDate();
				long  fromOrderNo = reportsDto.getFromOrderNo();
				long toOrderNo  = reportsDto.getToOrderNo();
				
			     SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy");  
			     
			     if(orderFromDate == null || orderFromDate.trim().equals("")) {
					callableStatement.setNull(1, java.sql.Types.VARCHAR);
				}
				else {
					orderFromDate = simpleDateFormat.format(orderFromDate);
					callableStatement.setString(1, Utility.getReportOrderDate(orderFromDate));
					AppConstants.IOES_LOGGER.info("Date format is recomemded: "+simpleDateFormat.format(orderFromDate));
				}
				if(orderToDate == null ||  orderToDate.trim().equals("")) {
					callableStatement.setNull(2, java.sql.Types.VARCHAR);
				} else {
					
					callableStatement.setString(2, Utility.getReportOrderDate(orderToDate));
				}
				if(fromOrderNo != 0 && toOrderNo != 0) {
					callableStatement.setLong(3, fromOrderNo);
					callableStatement.setLong(4, toOrderNo);
					
				} else {
					callableStatement.setInt(3, java.sql.Types.BIGINT);
					callableStatement.setInt(4, java.sql.Types.BIGINT);
			
				}
				//AppConstants.IOES_LOGGER.info(Utility.getReportOrderDate(orderFromDate)+"Order numL  ");
				Utility utility=new Utility();
				PagingSorting pagingSorting = reportsDto.getPagingSorting();
				pagingSorting.sync();// To calculate start index and Enc Index
				
				callableStatement.setString(5, pagingSorting.getSortByColumn());// columnName
				callableStatement.setString(6, PagingSorting.DB_Asc_Desc1(pagingSorting.getSortByOrder()));// sort order
				callableStatement.setInt(7, pagingSorting.getStartRecordId());// start index
				callableStatement.setInt(8, pagingSorting.getEndRecordId());// end index
				callableStatement.setInt(9, (pagingSorting.isPagingToBeDone() ? 1 : 0));// end
				
				resultSet = callableStatement.executeQuery();
				
				int countFlag = 0;
				int recordCount = 0;
				ActiveLineDemoReportDTO demoReportDTO = new ActiveLineDemoReportDTO();
				
				while (resultSet.next() != false) {

					countFlag++;
					
					demoReportDTO.setPartyName(resultSet.getString("PARTYNAME"));
					demoReportDTO.setPartyNo(resultSet.getString("PARTY_NO"));
					demoReportDTO.setCrmAccNo(resultSet.getLong("CRMACCOUNTNO"));
					demoReportDTO.setCustomerSegment(resultSet.getString("CUSTOMER_SEGMENT"));
					demoReportDTO.setIndustrySegment(resultSet.getString("INDUSTRY_SEGMENT"));
					demoReportDTO.setRegionName(resultSet.getString("REGIONNAME"));
					demoReportDTO.setZoneName(resultSet.getString("ZONENNAME"));
					demoReportDTO.setAcctMgrName(resultSet.getString("ACCT_MGR_NAME"));
					demoReportDTO.setPrjMGRName(resultSet.getString("PRJ_MGR_NAME"));
					demoReportDTO.setDemoOrder(resultSet.getString("DEMO_ORDER"));
					demoReportDTO.setNoOfDays(resultSet.getLong("NO_OF_DAYS"));
					demoReportDTO.setOrderType(resultSet.getString("ORDERTYPE"));
					demoReportDTO.setChangeTypeName(resultSet.getString("CHANGETYPENAME"));
					demoReportDTO.setSubChangeType(resultSet.getString("ORDER_SUBCHANGETYPE"));
					if(!(resultSet.getString("ORDERDATE")==null || resultSet.getString("ORDERDATE")==""))
					{
						demoReportDTO.setOrderDate((utility.showDate_Report(resultSet.getString("ORDERDATE"))));
					}
					demoReportDTO.setServiceId(resultSet.getLong("SERVICEID"));
					demoReportDTO.setOrderNo(resultSet.getLong("ORDERNO"));
					demoReportDTO.setLogicalSINo(resultSet.getString("LOGICAL_SI_NO"));
					demoReportDTO.setCustLogicalSINo(resultSet.getString("CUSTOMER_LOGICAL_SI_NO"));
					demoReportDTO.setCktId(resultSet.getString("CKTID"));
					demoReportDTO.setAnnotation(resultSet.getString("ANNOTATION"));
					demoReportDTO.setM6OrderNo(resultSet.getLong("M6ORDERNO"));
					demoReportDTO.setLocNo(resultSet.getString("LOCNO"));
					demoReportDTO.setLocDate(resultSet.getString("LOC_DATE"));
					demoReportDTO.setFromAddress(resultSet.getString("FROM_ADDRESS"));
					demoReportDTO.setToAddress(resultSet.getString("TO_ADDRESS"));
					demoReportDTO.setBillingBandWidth(resultSet.getString("BILLING_BANDWIDTH"));
					
					demoReportDTO.setBillingBandwidthUOM(resultSet.getString("BILLING_BANDWIDTH_UOM"));
					demoReportDTO.setCreateDate(utility.showDate_Report5((new Date(resultSet.getTimestamp("CREATEDDATE").getTime()))).toUpperCase());
					demoReportDTO.setOrderApprovalDate(utility.showDate_Report5((new Date(resultSet.getTimestamp("ORDER_APPROVAL_DATE").getTime()))).toUpperCase());
				
					if(!(resultSet.getString("PUBLISHED_DATE")==null || resultSet.getString("PUBLISHED_DATE")==""))
					{
						demoReportDTO.setPublishedDate((utility.showDate_Report(resultSet.getString("PUBLISHED_DATE"))));
					}

					if(!(resultSet.getString("SERVICE_CLOSURE_DATE")==null || resultSet.getString("SERVICE_CLOSURE_DATE")==""))
					{
						demoReportDTO.setServiceClosureDate((utility.showDate_Report(resultSet.getString("SERVICE_CLOSURE_DATE"))));
					}
					
					if(!(resultSet.getString("BILLING_TRIGGER_CREATEDATE")==null || resultSet.getString("BILLING_TRIGGER_CREATEDATE")==""))
					{
						demoReportDTO.setBillingTriggerCreateDate((utility.showDate_Report(resultSet.getString("BILLING_TRIGGER_CREATEDATE"))));
					}
					if(!(resultSet.getString("BILLINGTRIGGERDATE")==null || resultSet.getString("BILLINGTRIGGERDATE")==""))
					{
						demoReportDTO.setBillingTriggerDate((utility.showDate_Report(resultSet.getString("BILLINGTRIGGERDATE"))));
					}
					if(!(resultSet.getString("CHARGE_CURRENT_START_DATE")==null || resultSet.getString("CHARGE_CURRENT_START_DATE")==""))
					{
						demoReportDTO.setChargeCurrentStartDate((utility.showDate_Report(resultSet.getString("CHARGE_CURRENT_START_DATE"))));
					}

					
					if(!(resultSet.getString("CURRENT_END_DATE")==null || resultSet.getString("CURRENT_END_DATE")==""))
					{
						demoReportDTO.setChargeCurrentEndDate((utility.showDate_Report(resultSet.getString("CURRENT_END_DATE"))));
					}
					
					demoReportDTO.setMstChargeName(resultSet.getString("MST_CHARGENAME"));
					demoReportDTO.setProductName(resultSet.getString("PRODUCTNAME"));
					demoReportDTO.setSubTypeName(resultSet.getString("SERVICESUBTYPENAME"));
					demoReportDTO.setStage(resultSet.getString("STAGE"));
					demoReportDTO.setServiceTypeName(resultSet.getString("SERVICETYPENAME"));
					demoReportDTO.setCopcApprovalRemark(resultSet.getString("Copc_Approval_Remark"));
					demoReportDTO.setOrderEntryRemark(resultSet.getString("Order_Entry_Task_Remark"));
					demoReportDTO.setpMRemark(resultSet.getString("Pm_Approval_Task_Remark"));
					demoReportDTO.setTotalAmount(resultSet.getLong("TOT_AMOUNT"));
					demoReportDTO.setCurName(resultSet.getString("CURNAME"));
					demoReportDTO.setAnnualRate(resultSet.getLong("ANNUAL_RATE"));
					demoReportDTO.setPublished(resultSet.getString("PUBLISHED"));
					demoReportDTO.setServiceStage(resultSet.getString("SERVICE_STAGE"));
					demoReportDTO.setLsiDemoType(resultSet.getInt("IS_DEMO"));
				
					
					if (pagingSorting.isPagingToBeDone()) {
						 recordCount = resultSet.getInt("FULL_REC_COUNT");
						//recordCount = countFlag;
					}
					activeLineDemoReportDTOsList.add(demoReportDTO);
				}
				pagingSorting.setRecordCount(recordCount);
			
				
			} catch (Exception exception) {
				
				exception.printStackTrace();		
				AppConstants.IOES_LOGGER.info("ReportsDao: viewActiveLineDemoReportDetails has problem in execution "+exception);

			} finally {
				try {
					
					DbConnection.closeResultset(resultSet);
					DbConnection.closeCallableStatement(callableStatement);
					DbConnection.freeConnection(connection);
					
				} catch (Exception exception2) {
					AppConstants.IOES_LOGGER.info("ReportsDao: Problem in closing connetion "+exception2);
				}
			}
			return activeLineDemoReportDTOsList;
	}
	//[505051] End

	public ArrayList<OBValueReportDTO> viewOBValueReport(OBValueReportDTO objDto) throws Exception {

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
			proc = conn.prepareCall(sqlOBValueReport);

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
				objDto.setChargeAmount(rs.getDouble("CHARGEAMOUNT"));
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
				objDto.setLine_desc(rs.getString("LINE_ITEM_DESC"));
				objDto.setLinename(rs.getString("LINENAME"));
				objDto.setSub_linename(rs.getString("ORDER_SUBLINENAME"));
				objDto.setChargeName(rs.getString("CHARGE_NAME"));
				objDto.setChargeinfoID(rs.getString("CHARGEINFOID"));
				objDto.setServiceProductID(rs.getInt("LINENO"));
				objDto.setServiceName(rs.getString("SERVICENAME"));
				objDto.setAccountID(rs.getInt("ACCOUNTID"));
				objDto.setEntity(rs.getString("COMPANYNAME"));
				objDto.setLicCompanyName(rs.getString("LCOMPANYNAME"));
				objDto.setContractPeriod(rs.getInt("CONTRACTPERIOD"));
				objDto.setFrequencyName(rs.getString("PAYMENTTERM"));
				objDto.setChargeTypeName(rs.getString("CYCLICNONCYCLIC"));
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
				objDto.setActualOB(BigDecimal.valueOf((rs.getDouble("ACTUAL_OB"))).toPlainString());
				objDto.setActualOBINR(Double.valueOf(Utility.round(rs.getDouble("ACTUAL_OB_INR"), 2)).toString());
				//objDto.setActualOBINR(BigDecimal.valueOf((rs.getDouble("ACTUAL_OB_INR"))).toPlainString());
				
				objDto.setFinalOB(BigDecimal.valueOf((rs.getDouble("OB_VALUE"))).toPlainString());
				objDto.setFinalOBINR(Double.valueOf(Utility.round(rs.getDouble("OB_VALUE_INR"), 2)).toString());
				//objDto.setFinalOBINR(Double.valueOf((rs.getDouble("OB_VALUE_INR"))).toString());
				
				objDto.setChargeRemarks(rs.getString("REMARKS"));
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
				objDto.setTotalDays(rs.getString("TOTALDAYS"));
				objDto.setEffectiveDays(rs.getString("EFFECTIVE_DAYS"));
				objDto.setMn(BigDecimal.valueOf((rs.getDouble("MN"))).toPlainString()); //using this value for ob value
				objDto.setOldPKChargeid(rs.getLong("OBLINKCHARGE"));
				objDto.setOldChargeAmount(BigDecimal.valueOf((rs.getDouble("OBLINKCHARGEAMOUNT"))).toPlainString());
				objDto.setOrderEnteredBy(rs.getString("ORDER_CREATED_BY_NAME"));
				objDto.setExchangeRate(BigDecimal.valueOf((rs.getDouble("EXCHANGE_RATE"))).toPlainString());
				objDto.setObValue(BigDecimal.valueOf((rs.getDouble("OB_VALUE_TRANSACTION"))).toPlainString());
				objDto.setObValueINR(Double.valueOf(Utility.round(rs.getDouble("OB_VALUE_TRANSACTION_INR"), 2)).toString());
				
				objDto.setCustomerSegment(rs.getString("CUST_SEGMENT_CODE")); //newly added
				objDto.setProjectCategory(rs.getString("ORDERCATGRY_LABELATTVALUE")); //newly added
 				objDto.setServiceRemarks(rs.getString("SERVICE_REMARKS")); // newly added
				
				objDto.setObMonth(rs.getString("OB_MONTH"));
				objDto.setObYear(rs.getString("OB_YEAR"));  //[129]
				
				objDto.setEntryType(rs.getString("ENTRY_TYPE"));
				objDto.setIsNfa(rs.getString("IS_NFA"));
				objDto.setChargeperiod(rs.getString("TOTAL_DAYS"));
				objDto.setCopcApproveDate(rs.getString("CANCEL_DATE"));
				if (rs.getString("CANCEL_DATE") != null && !"".equals(rs.getString("CANCEL_DATE"))){
					objDto.setCopcApproveDate((utility.showDate_Report(new Date(rs.getTimestamp("CANCEL_DATE").getTime()))).toUpperCase());
				}
				//[130] Start
				objDto.setSalesForceOpportunityNumber(rs.getString("SF_OPP_ID"));
				objDto.setChannelPartner(rs.getString("PARTNER_NAME"));
				objDto.setNetworkType(rs.getString("NETWORK_SERVICE_TYPE"));
				objDto.setPartnerId(rs.getString("PARTNER_ID"));
				//[130] End
				//[131] start
				objDto.setPartnerCode(rs.getString("PARTNER_CODE"));
				objDto.setFieldEngineer(rs.getString("FIELD_ENGINEER"));
				//[131] end
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

	//[505051] End
	/**
	 * Method:fetchServiceTypeName
	 * @author Anil Kumar
	 * @return
	 */
		public ArrayList<DummyLinesDetailsReportDTO> fetchServiceTypeName() 
		{
			Connection connection =null;
			CallableStatement csServiceTypeName =null;
			ResultSet rsServiceTypeName = null;
			ArrayList<DummyLinesDetailsReportDTO> listServicetypename = new ArrayList<DummyLinesDetailsReportDTO>();
			DummyLinesDetailsReportDTO objDto = null;	
			String sqlFetchServiceTypeName="SELECT SERVICETYPEID,SERVICETYPENAME FROM ioe.TSERVICETYPE where ISACTIVE=1 and SERVICETYPEID in(122,411,412,413,381,431,221)";
			try
			{
				connection=DbConnection.getConnectionObject();
				csServiceTypeName= connection.prepareCall(sqlFetchServiceTypeName);
				
										
				rsServiceTypeName = csServiceTypeName.executeQuery();
				while(rsServiceTypeName.next())
				{
					objDto =  new DummyLinesDetailsReportDTO();
					objDto.setServicetypeid(rsServiceTypeName.getString("SERVICETYPEID"));
					objDto.setServicename(rsServiceTypeName.getString("SERVICETYPENAME"));
					listServicetypename.add(objDto);			
				}
				return listServicetypename;
			}
			catch(Exception ex )
			{
				Utility.LOG(true, false, ex, "::Exception occured while fetching service type name in method fetchServiceTypeName::block1");	
			}
			finally
			{
				try 
				{
					DbConnection.closeResultset(rsServiceTypeName);
					DbConnection.closeCallableStatement(csServiceTypeName);
					DbConnection.freeConnection(connection);
				} 
				catch (SQLException e) 
				{
					// TODO Auto-generated catch block
					Utility.LOG(true, false, e, "::Exception occured while fetching service type name in method fetchServiceTypeName::block2");					
				}
				catch(Exception ex)
				{
					Utility.LOG((ex.getMessage() + " Exception occured while fetching service type name in method fetchServiceTypeName::block3" ));
				}
			}
			return listServicetypename;
		}
		
		/**
		 * Method::viewDummyLineDetailsReport
		 * @param DummyLinesDetailsReportDTO
		 * @author Anil Kumar
		 * @return
		 */
		public ArrayList<DummyLinesDetailsReportDTO> viewDummyLineDetailsReport(DummyLinesDetailsReportDTO objDto) 
		{			
			String methodName="viewDummyLineDetailsReport", className=this.getClass().getName(), msg="";
			boolean logToFile=true, logToConsole=true;	
			Connection connection =null;
			CallableStatement proc =null;
			ResultSet rs = null;
			ArrayList<DummyLinesDetailsReportDTO> listSearchDetails = new ArrayList<DummyLinesDetailsReportDTO>();
			DummyLinesDetailsReportDTO objReportsDto = null;
			int recordCount =0;						
			try
			{
				connection=DbConnection.getReportsConnectionObject();
				proc= connection.prepareCall(sqlGetDummyLineDetailsReport);
		
				if (!"0".equals(objDto.getLogical_si_no()) 
						&& !"".equals(objDto.getLogical_si_no())) {
					proc.setLong(1, Long.valueOf(objDto.getLogical_si_no()));
				} else {
					proc.setNull(1,java.sql.Types.BIGINT);
				}
				
				if (!"0".equals(objDto.getCrmaccountno()) 
						&& !"".equals(objDto.getCrmaccountno()) && objDto.getCrmaccountno() !=null) {
					proc.setLong(2, Long.valueOf(objDto.getCrmaccountno()));
				} else {
					proc.setNull(2,java.sql.Types.BIGINT);
				}
				
				if (!"0".equals(objDto.getLineitemid()) 
						&& !"".equals(objDto.getLineitemid()) && objDto.getLineitemid() !=null) {
					proc.setLong(3, Long.valueOf(objDto.getLineitemid()));
				} else {
					proc.setNull(3,java.sql.Types.BIGINT);
				}
				
				if (!"0".equals(objDto.getServicetypeid()) 
						&& !"".equals(objDto.getServicetypeid()) && objDto.getServicetypeid() !=null) {
					proc.setLong(4, Long.valueOf(objDto.getServicetypeid()));
				} else {
					proc.setNull(4,java.sql.Types.BIGINT);
				}
								
				PagingSorting pagingSorting = objDto.getPagingSorting();
				pagingSorting.sync();// To calculate start index and Enc Index
				
				proc.setString(5, pagingSorting.getSortByColumn());// columnName
				proc.setString(6, PagingSorting.DB_Asc_Desc(pagingSorting.getSortByOrder()));// sort order
				proc.setInt(7, pagingSorting.getStartRecordId());// start index
				proc.setInt(8, pagingSorting.getEndRecordId());// end index
				proc.setInt(9, (pagingSorting.isPagingToBeDone() ? 1 : 0));// index
				
				rs = proc.executeQuery();
				
				while(rs.next())
				{
					objReportsDto =  new DummyLinesDetailsReportDTO();
					
					objReportsDto.setOrderno(rs.getString("ORDERNO"));
					objReportsDto.setLogical_si_no(rs.getString("LOGICAL_SI_NO"));
					objReportsDto.setCust_logical_si_no(rs.getString("CUSTOMER_LOGICAL_SI_NO"));
					objReportsDto.setProductname(rs.getString("PRODUCTNAME"));
					objReportsDto.setSubproductname(rs.getString("SERVICESUBTYPENAME"));
					objReportsDto.setPartyname(rs.getString("PARTYNAME"));
					objReportsDto.setAccountid(rs.getString("ACCOUNTID"));
					objReportsDto.setCrmaccountno(rs.getString("CRMACCOUNTNO"));
					objReportsDto.setRegionname(rs.getString("REGIONNAME"));
					objReportsDto.setCust_seg_code(rs.getString("CUST_SEGMENT_CODE"));
					objReportsDto.setLineitemid(rs.getString("SER_SERVICEPRODUCTID"));
					objReportsDto.setCktid(rs.getString("CKTID"));
					objReportsDto.setFx_si_id(rs.getString("FX_SI_ID"));
					objReportsDto.setFx_account_external_id(rs.getString("FX_ACCOUNT_EXTERNAL_ID"));
					objReportsDto.setFx_account_internal_id(rs.getString("FX_ACCOUNT_INTERNAL_ID"));
					objReportsDto.setLine_status(rs.getString("LINE_STATUS"));
					objReportsDto.setVerticalname(rs.getString("VERTICAL_DETAILS"));
					objReportsDto.setServiceid(rs.getString("SERVICEID"));
					objReportsDto.setServicename(rs.getString("SERVICETYPENAME"));
					objReportsDto.setLineitemname(rs.getString("SERVICEDETDESCRIPTION"));
					objReportsDto.setService_stage(rs.getString("DISC_SERVICE_STAGE"));
					
					
					if (pagingSorting.isPagingToBeDone()) {
						recordCount = rs.getInt("FULL_REC_COUNT");
					}
					listSearchDetails.add(objReportsDto);
				}
				pagingSorting.setRecordCount(recordCount);
			}
			catch(Exception ex )
			{
				Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);
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
					Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);
				}
			}
			return listSearchDetails;
		}
//[1091]
	public ArrayList<RateRenewalReportDTO> facthCustomerSegmentDetails() 
	{
		Connection connection =null;
		CallableStatement customerSegment =null;
		ResultSet rsCustomerSegment = null;
		ArrayList<RateRenewalReportDTO> listCustomerSegmrnt = new ArrayList<RateRenewalReportDTO>();
		RateRenewalReportDTO objDto = null;	
		String customer_segment="";
		String sqlFetchServiceTypeName="SELECT CUST_SEGMENT_ID,CUST_SEGMENT_CODE FROM IOE.TM_CUSTOMER_SEGMENT_MASTER";
		try
		{
			connection=DbConnection.getConnectionObject();
			customerSegment= connection.prepareCall(sqlFetchServiceTypeName);
			rsCustomerSegment = customerSegment.executeQuery();
			while(rsCustomerSegment.next())
			{
				objDto =  new RateRenewalReportDTO();
				objDto.setCustomerSegmentId(rsCustomerSegment.getInt("CUST_SEGMENT_ID"));
				objDto.setCus_segment(rsCustomerSegment.getString("CUST_SEGMENT_CODE"));
				
				listCustomerSegmrnt.add(objDto);	
				
			}
			return listCustomerSegmrnt;
		}
		catch(Exception ex )
		{
			Utility.LOG(true, false, ex, "::Exception occured while fetching customer Sigment  name in method facthCustomerSegmentDetails::block1");	
		}
		finally
		{
			try 
			{
				DbConnection.closeResultset(rsCustomerSegment);
				DbConnection.closeCallableStatement(customerSegment);
				DbConnection.freeConnection(connection);
			} 
			catch (SQLException e) 
			{
				// TODO Auto-generated catch block
				Utility.LOG(true, false, e, "::Exception occured while fetching customer Sigment name in method facthCustomerSegmentDetails::block2");					
			}
			catch(Exception ex)
			{
				Utility.LOG((ex.getMessage() + " Exception occured while fetching customer Sigment name in method facthCustomerSegmentDetails::block3" ));
			}
		}
		return listCustomerSegmrnt;
	}

	
	
	public ArrayList<DisconnectChangeOrdeReportDTO> facthCustomerSegmentForDisconChaneReportDetails() 
	{
		Connection connection =null;
		CallableStatement customerSegment =null;
		ResultSet rsCustomerSegment = null;
		ArrayList<DisconnectChangeOrdeReportDTO> listCustomerSegmrntforDisconChangeOdr = new ArrayList<DisconnectChangeOrdeReportDTO>();
		DisconnectChangeOrdeReportDTO objDtoo = null;	
		String customer_segment="";
		String sqlFetchServiceTypeName="SELECT CUST_SEGMENT_ID,CUST_SEGMENT_CODE FROM IOE.TM_CUSTOMER_SEGMENT_MASTER";
		try
		{
			connection=DbConnection.getConnectionObject();
			customerSegment= connection.prepareCall(sqlFetchServiceTypeName);
			rsCustomerSegment = customerSegment.executeQuery();
			while(rsCustomerSegment.next())
			{
				objDtoo =  new DisconnectChangeOrdeReportDTO();
				objDtoo.setCustomerSegmentId(rsCustomerSegment.getInt("CUST_SEGMENT_ID"));
				objDtoo.setCus_segment(rsCustomerSegment.getString("CUST_SEGMENT_CODE"));
				
				listCustomerSegmrntforDisconChangeOdr.add(objDtoo);	
				
			}
			return listCustomerSegmrntforDisconChangeOdr;
		}
		catch(Exception ex )
		{
			Utility.LOG(true, false, ex, "::Exception occured while fetching customer Sigment  name in method facthCustomerSegmentDetails::block1");	
		}
		finally
		{
			try 
			{
				DbConnection.closeResultset(rsCustomerSegment);
				DbConnection.closeCallableStatement(customerSegment);
				DbConnection.freeConnection(connection);
			} 
			catch (SQLException e) 
			{
				// TODO Auto-generated catch block
				Utility.LOG(true, false, e, "::Exception occured while fetching customer Sigment name in method facthCustomerSegmentDetails::block2");					
			}
			catch(Exception ex)
			{
				Utility.LOG((ex.getMessage() + " Exception occured while fetching customer Sigment name in method facthCustomerSegmentDetails::block3" ));
			}
		}
		return listCustomerSegmrntforDisconChangeOdr;
	}
	//[1091]
	//[1110] start
	/**
	 * Method::viewDocumentMatrixReport
	 * @param DocumentMatrixReportDTO
	 * @author Gunjan Singla
	 * @return ArrayList
	 */
	public ArrayList<DocumentMatrixReportDTO> viewDocumentMatrixReport(
			DocumentMatrixReportDTO objDto) {
		//start
		String methodName="viewDocumentMatrixReport", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;	
		//end
		Connection connection = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		int recordCount = 0;
		ArrayList<DocumentMatrixReportDTO> docListDetails = new ArrayList<DocumentMatrixReportDTO>();
		DocumentMatrixReportDTO objReportsDto = null;
		SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy");
		String copcFromDate=objDto.getFromCOPCDate();
		String cocpToDate=objDto.getToCOPCDate();
		String custSegment=objDto.getCustSegment();
		
		try {
			connection=DbConnection.getReportsConnectionObject();
			cstmt= connection.prepareCall(sqlGetDocumentMatrixReport);
			
			if ( copcFromDate != null && !"".equals(copcFromDate)) {
				
				Date fromDate=df.parse(copcFromDate);
				cstmt.setDate(1, new java.sql.Date(fromDate.getTime()));
				
				} else {
					cstmt.setNull(1, java.sql.Types.DATE);
			}
			
			if ( cocpToDate != null && !"".equals(cocpToDate)) {
				
				Date toDate=df.parse(cocpToDate);
				cstmt.setDate(2, new java.sql.Date(toDate.getTime()));
				
				} else {
					cstmt.setNull(2, java.sql.Types.DATE);
			}
			if (custSegment!= null && !"".equals(custSegment)) {
				cstmt.setString(3, custSegment.trim().toUpperCase());
			} else {
				cstmt.setNull(3, java.sql.Types.VARCHAR);
			}
		
			PagingSorting pagingSorting = objDto.getPagingSorting();
			pagingSorting.sync();// To calculate start index and Enc Index

			cstmt.setString(4, pagingSorting.getSortByColumn());// columnName
			cstmt.setString(5, PagingSorting.DB_Asc_Desc(pagingSorting
					.getSortByOrder()));// sort order
			cstmt.setInt(6, pagingSorting.getStartRecordId());// start index
			cstmt.setInt(7, pagingSorting.getEndRecordId());// end index
			cstmt.setInt(8, (pagingSorting.isPagingToBeDone() ? 1 : 0));
			
			rs=cstmt.executeQuery();
			while(rs.next()){
				objReportsDto=new DocumentMatrixReportDTO();
				objReportsDto.setAccountNo(rs.getString("CRMACCOUNTNO"));
				objReportsDto.setAccountName(rs.getString("ACCOUNTNAME"));
				objReportsDto.setAccntMgr(rs.getString("ACCT_MGR_NAME"));
				objReportsDto.setCopcApprovr(rs.getString("COPC_APPROVER_NAME"));
				objReportsDto.setCopcApprovrID(rs.getString("COPC_APPROVER_ID"));
				objReportsDto.setCopcAprDate(rs.getString("COPC_APPROVAL_DATE"));				
				if (rs.getString("COPC_APPROVAL_DATE") != null && !"".equals(rs.getString("COPC_APPROVAL_DATE"))){
					objReportsDto.setCopcAprDate((Utility.showDate_Report(new Date(rs.getTimestamp("COPC_APPROVAL_DATE").getTime()))).toUpperCase());
				}
				objReportsDto.setCopcApprovrRemarks(rs.getString("COPC_APPROVER_REMARKS"));
				objReportsDto.setCustSegment(rs.getString("CUST_SEGMENT_CODE"));
				objReportsDto.setLsiNo(rs.getString("LOGICAL_SI_NO"));
				objReportsDto.setOrderNo(rs.getString("ORDERNO"));
				objReportsDto.setOrdSubType(rs.getString("NAME_SUBTYPE"));
				objReportsDto.setOrdType(rs.getString("ORDERTYPE"));
				objReportsDto.setRegion(rs.getString("REGIONNAME"));
				objReportsDto.setSalesCo(rs.getString("SALES_CORD_NAME"));
				objReportsDto.setServiceNo(rs.getString("SERVICEID"));
				objReportsDto.setStage(rs.getString("STAGE"));
				objReportsDto.setDocCAF((rs.getBoolean("CAF"))?"Yes":"No");
				objReportsDto.setDocCustAgreemnt((rs.getBoolean("Customer_Agreement"))?"Yes":"No");
				objReportsDto.setDocFeasibility((rs.getBoolean("Feasibility"))?"Yes":"No");
				objReportsDto.setDocISP((rs.getBoolean("ISP"))?"Yes":"No");
				objReportsDto.setDocNtwrk((rs.getBoolean("Network_Diagram"))?"Yes":"No");
				objReportsDto.setDocOFS((rs.getBoolean("OFS"))?"Yes":"No");
				objReportsDto.setDocOther((rs.getBoolean("OTHERS"))?"Yes":"No");
				objReportsDto.setDocPCD((rs.getBoolean("PCD"))?"Yes":"No");
				objReportsDto.setDocPCN((rs.getBoolean("e_PCN_NFA"))?"Yes":"No");
				objReportsDto.setDocPO((rs.getBoolean("PO_Order_form"))?"Yes":"No");
				objReportsDto.setDocRFP((rs.getBoolean("RFP"))?"Yes":"No");
				objReportsDto.setDocSOW((rs.getBoolean("SOW"))?"Yes":"No");
				objReportsDto.setDocThirdParty((rs.getBoolean("PI_Third_Party_Related"))?"Yes":"No");
				objReportsDto.setDocTnC((rs.getBoolean("TandC"))?"Yes":"No");
				objReportsDto.setDocLOU((rs.getBoolean("LOU"))?"Yes":"No");
				objReportsDto.setDocAnyDeviation((rs.getBoolean("Any_deviation"))?"Yes":"No");
				objReportsDto.setDocNonIndiaTouchLink((rs.getBoolean("NonIndia_touch_link"))?"Yes":"No");
				//[132]
				objReportsDto.setDocPMTA((rs.getBoolean("PMTA"))?"Yes":"No");
				
				docListDetails.add(objReportsDto);
				if (pagingSorting.isPagingToBeDone() && recordCount==0) {
					recordCount = rs.getInt("FULL_REC_COUNT");
				
			}
			}
			pagingSorting.setRecordCount(recordCount);
		} catch (Exception e) {
			
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}
		finally
		{
			try 
			{
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(cstmt);
				DbConnection.freeConnection(connection);
			} 
			catch (Exception e) 
			{
				
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);
			}
		}
			
		
		return docListDetails;
		
	}
	//[1110] end

	public ArrayList<UserAccessMatrixDto> viewAccessMatrixReport(
			UserAccessMatrixDto objDto) {
		//start
		String methodName="viewAccessMatrixReport", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;	
		//end
		Connection connection = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		ArrayList<UserAccessMatrixDto> docListDetails = new ArrayList<UserAccessMatrixDto>();
		int recordCount = 0;
		UserAccessMatrixDto objReportsDto = null;
		SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy");
		String copcFromDate=objDto.getFromCOPCDate();
		String cocpToDate=objDto.getToCOPCDate();
		
		try {
			connection=DbConnection.getReportsConnectionObject();
			cstmt= connection.prepareCall(sqlGetAccesstMatrixReport);
			
			if ( copcFromDate != null && !"".equals(copcFromDate)) {
				
				Date fromDate=df.parse(copcFromDate);
				cstmt.setDate(1, new java.sql.Date(fromDate.getTime()));
				
				} else {
					cstmt.setNull(1, java.sql.Types.DATE);
			}
			
			if ( cocpToDate != null && !"".equals(cocpToDate)) {
				
				Date toDate=df.parse(cocpToDate);
				cstmt.setDate(2, new java.sql.Date(toDate.getTime()));
				
				} else {
					cstmt.setNull(2, java.sql.Types.DATE);
			}
			
			PagingSorting pagingSorting = objDto.getPagingSorting();
			pagingSorting.sync();// To calculate start index and Enc Index
/*
			cstmt.setString(3,  pagingSorting.getSortByColumn());// columnName
			cstmt.setString(4, PagingSorting.DB_Asc_Desc(pagingSorting
					.getSortByOrder()));// sort order
*/			cstmt.setInt(3, pagingSorting.getStartRecordId());// start index
			cstmt.setInt(4, pagingSorting.getEndRecordId());// end index
			cstmt.setInt(5, (pagingSorting.isPagingToBeDone() ? 1 : 0));
			
			rs=cstmt.executeQuery();
			long i=1;
			while(rs.next()){
				objReportsDto=new UserAccessMatrixDto();
				objReportsDto.setSrno(i++);
				objReportsDto.setUserId(rs.getString("USERID"));
				objReportsDto.setUserName(rs.getString("USERNAME"));
				objReportsDto.setRoleId(rs.getLong("ROLEID"));
				objReportsDto.setRoleName(rs.getString("ROLENAME"));
				objReportsDto.setAccessOrDenied(rs.getString("STATUS"));
				objReportsDto.setDateOfmofification(rs.getString("DATEOFMODIFICATION"));
				objReportsDto.setModifiedByUserId(rs.getString("MODIFIEDBYUSERID"));
				objReportsDto.setModifiedByUserName(rs.getString("MODIFIEDBYUSERNAME"));
				objReportsDto.setOldCustSegmentName(rs.getString("OLDCUSTOMERSEGMENT"));
				objReportsDto.setCus_segment(rs.getString("NEWCUSTOMERSEGMENT"));
				
				docListDetails.add(objReportsDto);
				if (pagingSorting.isPagingToBeDone() && recordCount==0) {
					recordCount = rs.getInt("FULL_REC_COUNT");
				}
			}
			pagingSorting.setRecordCount(recordCount);
		} catch (Exception e) {
			
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//nagarjuna
		}
		finally
		{
			try 
			{
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(cstmt);
				DbConnection.freeConnection(connection);
			} 
			catch (Exception e) 
			{
				
				Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);
			}
		}
			
		
		return docListDetails;
		
	}


	public ArrayList<ArchivalReportDto> reportDraftOrder(ArchivalReportDto reportsDto) {
		
		String methodName="reportDraftOrder";
		String className=this.getClass().getName();
	    String msg="";
		boolean logToFile=true, 
		logToConsole=true;	
		
		Connection connection = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		ArrayList docListDetails = new ArrayList();
	    int recordCount = 0;
	    ArchivalReportDto dto = null;
	    Utility utility=new Utility();
	    SimpleDateFormat df=new SimpleDateFormat("MM/dd/yyyy");
		String fromDate=reportsDto.getFromdate();
		String toDate=reportsDto.getTodate();

		try {
			PagingSorting pagingSorting = reportsDto.getPagingSorting();
			pagingSorting.sync();// To calculate start index and Enc Index
			connection = DbConnection.getConnectionObject();
			cstmt= connection.prepareCall(sqlGetDraftReport);
			
			
			if ( fromDate != null && !"".equals(fromDate)) {
				Date fDate=df.parse(fromDate);
				cstmt.setDate(1, new java.sql.Date(fDate.getTime()));
				} else {
					cstmt.setNull(1, java.sql.Types.DATE);
			}
			
			
			if ( toDate != null && !"".equals(toDate)) {
				Date tDate=df.parse(toDate);
				cstmt.setDate(2, new java.sql.Date(tDate.getTime()));
				} else {
					cstmt.setNull(2, java.sql.Types.DATE);
			}
			cstmt.setString(3,reportsDto.getAccount_number());
			cstmt.setString(4,reportsDto.getOrder_no());
			cstmt.setString(5,reportsDto.getLogical_si_no());
			cstmt.setString(6,reportsDto.getLine_it_no());
			cstmt.setString(7,reportsDto.getCkt_id());
			cstmt.setString(8,reportsDto.getM6orderno());
			cstmt.setString(9, pagingSorting.getSortByColumn());
			cstmt.setString(10, PagingSorting.DB_Asc_Desc(pagingSorting.getSortByOrder()));
			cstmt.setInt(11, pagingSorting.getStartRecordId());
			cstmt.setInt(12, pagingSorting.getEndRecordId());
			cstmt.setInt(13, (pagingSorting.isPagingToBeDone() ? 1 : 0));
			
		
			rs=cstmt.executeQuery();
			while(rs.next()){
				dto=new ArchivalReportDto();
				dto.setService_stage_description(rs.getString("SERVICE_STAGE_DESCRIPTION"));
				dto.setBilling_bandwidth(rs.getString("BILLING_BANDWIDTH"));
				dto.setLogical_si_no(rs.getString("LOGICAL_SI_NO"));
				dto.setAccount_id(rs.getString("ACCOUNT_ID")); 
				dto.setLine_it_no(rs.getString("LINE_IT_NO"));
				dto.setAccount_manager(rs.getString("ACCOUNT_MANAGER"));
				dto.setAccount_number(rs.getString("ACCOUNT_NUMBER"));
				dto.setPo_amount(rs.getDouble("PO_AMOUNT"));
				dto.setCustomer_segment(rs.getString("CUSTOMER_SEGMENT"));
				dto.setAccount_category(rs.getString("ACCOUNT_CATEGORY"));
				dto.setVertical(rs.getString("VERTICAL"));
				dto.setBilling_charge_start_date(rs.getString("BILLING_CHARGE_START_DATE"));
				
				if (rs.getString("BILLING_CHARGE_START_DATE") != null && !"".equals(rs.getString("BILLING_CHARGE_START_DATE")))
				{
					dto.setBilling_charge_start_date((utility.showDate_Report2(dto.getBilling_charge_start_date())).toUpperCase());
				}
				
				dto.setLine_name(rs.getString("SERVICE_NAME"));
				dto.setOrder_line_no(rs.getString("ORDER_LINE_NO"));
				dto.setCharge_name(rs.getString("LINE_NAME"));
				dto.setCancel_flag(rs.getString("CANCEL_FLAG"));
				dto.setProvision_bandwidth(rs.getString("PROVISION_BANDWIDTH"));
				dto.setUom(rs.getString("UOM"));
				dto.setBill_uom(rs.getString("BILL_UOM"));
				dto.setCategory_of_order(rs.getString("CATEGORY_OF_ORDER"));
				dto.setContract_period(rs.getString("CONTRACT_PERIOD"));
				dto.setCompany_name(rs.getString("COMPANY_NAME"));
				dto.setOrder_creation_date(rs.getString("ORDER_CREATION_DATE"));
				
				if (rs.getString("ORDER_CREATION_DATE") != null && !"".equals(rs.getString("ORDER_CREATION_DATE")))
				{

					dto.setOrder_creation_date((utility.showDate_Report(dto.getOrder_creation_date())).toUpperCase());
				}
				
				dto.setCustomer_service_rfs_date(rs.getString("CUSTOMER_SERVICE_RFS_DATE"));
				
				if (rs.getString("CUSTOMER_SERVICE_RFS_DATE") != null && !"".equals(rs.getString("CUSTOMER_SERVICE_RFS_DATE")))
				{
					dto.setCustomer_service_rfs_date((utility.showDate_Report2(dto.getCustomer_service_rfs_date())).toUpperCase()); 
				}
				
				dto.setCurrency(rs.getString("CURRENCY"));
				dto.setCharge_name(rs.getString("CHARGE_NAME"));
				dto.setCustomer_po_date(rs.getString("CUSTOMER_PO_DATE"));
				
				if (rs.getString("CUSTOMER_PO_DATE") != null && !"".equals(rs.getString("CUSTOMER_PO_DATE")))
				{

					dto.setCustomer_po_date((utility.showDate_Report2(dto.getCustomer_po_date())).toUpperCase());
				}
				
				dto.setCustomer_po_number(rs.getString("CUSTOMER_PO_NUMBER"));
				dto.setCyclic_or_non_cyclic(rs.getString("CYCLIC_OR_NON_CYCLIC"));
				dto.setChallen_no(rs.getString("CHALLEN_NO"));
				dto.setOrder_no(rs.getString("ORDER_NO"));
				dto.setFrom_site(rs.getString("FROM_SITE"));
				dto.setTo_site(rs.getString("TO_SITE"));
				dto.setItem_quantity(rs.getString("ITEM_QUANTITY"));
				dto.setKms_distance(rs.getString("KMS_DISTANCE"));
				dto.setLine_item_amount(rs.getDouble("LINE_ITEM_AMOUNT"));
				dto.setCopc_approved_date(rs.getString("COPC_APPROVED_DATE"));
				
				if (rs.getString("COPC_APPROVED_DATE") != null && !"".equals(rs.getString("COPC_APPROVED_DATE")))
				{
					dto.setCopc_approved_date(utility.showDate_Report(new Date(rs.getTimestamp("COPC_APPROVED_DATE").getTime())));
				}
				
				dto.setLine_item_description(rs.getString("LINE_ITEM_DESCRIPTION"));
				dto.setLoc_date(rs.getString("LOC_Date"));
				
				if (rs.getString("LOC_Date") != null && !"".equals(rs.getString("LOC_Date")))
				{
					dto.setLoc_date((utility.showDate_Report2(dto.getLoc_date())).toUpperCase());
				}
				
				dto.setAccount_manager_receive_date(rs.getString("ACCOUNT_MANAGER_RECEIVE_DATE"));
				if (rs.getString("ACCOUNT_MANAGER_RECEIVE_DATE") != null && !"".equals(rs.getString("ACCOUNT_MANAGER_RECEIVE_DATE")))
				{
					dto.setAccount_manager_receive_date((utility.showDate_Report2(dto.getAccount_manager_receive_date())).toUpperCase());
				}
				
				dto.setOrder_total(rs.getDouble("ORDER_TOTAL"));
				dto.setTaxation(rs.getString("TAXATION"));
				dto.setTaxexemption_reason(rs.getString("TAXEXEMPTION_REASON"));
				dto.setLicence_company(rs.getString("LICENCE_COMPANY"));
				dto.setLogical_circuit_id(rs.getString("LOGICAL_CIRCUIT_ID"));
				dto.setOrder_type(rs.getString("ORDER_TYPE"));
				dto.setPayment_term(rs.getString("PAYMENT_TERM"));
				dto.setProject_mgr(rs.getString("PROJECT_MGR"));
				dto.setRegion_name(rs.getString("REGION_NAME"));
				dto.setOld_line_item_amount(rs.getString("OLD_LINE_ITEM_AMOUNT"));
				dto.setDemo_type(rs.getString("DEMO_TYPE"));
				dto.setParty_name(rs.getString("PARTY_NAME"));
				dto.setOrder_stage_description(rs.getString("ORDER_STAGE_DESCRIPTION"));
				dto.setCharge_end_date(rs.getString("CHARGE_END_DATE"));
				
				if (rs.getString("CHARGE_END_DATE") != null && !"".equals(rs.getString("CHARGE_END_DATE")))
				{
					dto.setCharge_end_date((utility.showDate_Report2(dto.getCharge_end_date())).toUpperCase());
				}
				
				dto.setEnd_date_logic(rs.getString("END_DATE_LOGIC"));
				dto.setNew_order_remark(rs.getString("NEW_ORDER_REMARK"));
				dto.setRemarks(rs.getString("REMARKS"));
				dto.setService_order_type(rs.getString("SERVICE_ORDER_TYPE"));
				dto.setOsp(rs.getString("OSP"));
				dto.setOpportunity_id(rs.getString("OPPORTUNITY_ID"));
				dto.setStore_address(rs.getString("STORE_ADDRESS"));
				dto.setCustomer_rfs_date(rs.getString("CUSTOMER_RFS_DATE"));
				if (rs.getString("CUSTOMER_RFS_DATE") != null && !"".equals(rs.getString("CUSTOMER_RFS_DATE")))
				{
					dto.setCustomer_rfs_date((utility.showDate_Report2(dto.getCustomer_rfs_date())).toUpperCase());
				}
				
				dto.setOrder_entry_date(rs.getString("ORDER_ENTRY_DATE"));
				if (rs.getString("ORDER_ENTRY_DATE") != null && !"".equals(rs.getString("ORDER_ENTRY_DATE")))
				{
					dto.setOrder_entry_date((utility.showDate_Report2(dto.getOrder_entry_date())).toUpperCase());
				}
				
				dto.setCkt_id(rs.getString("CKT_ID"));
				dto.getInterfaceid();
		       
				if (pagingSorting.isPagingToBeDone() && recordCount==0) {
					recordCount = rs.getInt("FULL_REC_COUNT");
					}
					docListDetails.add(dto);
			}
			pagingSorting.setRecordCount(recordCount);
		} catch (Exception e) {
			
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);
		}
		finally
		{
			try 
			{
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(cstmt);
				DbConnection.freeConnection(connection);
			} 
			catch (Exception e) 
			{
			System.out.println(">>>>>>>>>>>.");	
			}
		}
			
		System.out.println("DTO_>>>>>>>>>>>>>>>end>>>>>>>>>>>>>");
		return docListDetails;
	
		
	}
	

	public ArrayList<ArchivalReportDto> pdReportOrder(ArchivalReportDto reportsDto) {
		
		String methodName="pdReportOrder";
		String className=this.getClass().getName();
	    String msg="";
		boolean logToFile=true, 
		logToConsole=true;	
		
		Connection connection = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		ArrayList docListDetails = new ArrayList();
	    int recordCount = 0;
	    ArchivalReportBean dto = null;
        SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy");
		String fromDate=reportsDto.getFromdate();
		String toDate=reportsDto.getTodate();
		Utility utility=new Utility();

		try {
			
			PagingSorting pagingSorting = reportsDto.getPagingSorting();
			pagingSorting.sync();// To calculate start index and Enc Index
			connection = DbConnection.getConnectionObject();
			cstmt= connection.prepareCall(sqlGetPDReport);
			
			if ( fromDate != null && !"".equals(fromDate)) {
				Date fDate=df.parse(fromDate);
				cstmt.setDate(1, new java.sql.Date(fDate.getTime()));
				} else {
					cstmt.setNull(1, java.sql.Types.DATE);
			}
			if ( toDate != null && !"".equals(toDate)) {
				Date tDate=df.parse(toDate);
				cstmt.setDate(2, new java.sql.Date(tDate.getTime()));
				} else {
					cstmt.setNull(2, java.sql.Types.DATE);
			}
			
			cstmt.setString(3,reportsDto.getAccount_id());
			cstmt.setString(4,reportsDto.getOrder_no());
			cstmt.setString(5,reportsDto.getLogical_si_no());
			//System.out.println("reportsDto.getLogical_si_no()>>>>"+reportsDto.getLogical_si_no());
			cstmt.setString(6,reportsDto.getM6orderno());
			cstmt.setString(7,reportsDto.getCircuit_id());
			cstmt.setString(8,reportsDto.getOrder_line_no()); 
			cstmt.setString(9, pagingSorting.getSortByColumn());
			cstmt.setString(10, PagingSorting.DB_Asc_Desc(pagingSorting.getSortByOrder()));
			cstmt.setInt(11, pagingSorting.getStartRecordId());
			cstmt.setInt(12, pagingSorting.getEndRecordId());
			cstmt.setInt(13, (pagingSorting.isPagingToBeDone() ? 1 : 0));
			
			rs=cstmt.executeQuery();
			while(rs.next()){
				dto=new ArchivalReportBean();
				dto.setAccount_mgr(rs.getString("ACCOUNT_MGR"));
				dto.setAccount_no(rs.getString("ACCOUNT_NO"));
				dto.setAmt(rs.getString("AMT"));
				dto.setAnnotation(rs.getString("ANNOTATION"));
				dto.setAnnual_rate(rs.getString("ANNUAL_RATE"));
				dto.setBandwidth(rs.getString("BANDWIDTH"));
				dto.setBandwidth_uom(rs.getString("BANDWIDTH_UOM"));
				dto.setBill_format(rs.getString("BILL_FORMAT"));
				dto.setBill_period(rs.getString("BILL_PERIOD"));
				dto.setBill_trg_Create_date(rs.getString("BILL_TRG_CREATE_DATE"));
				dto.setBill_type(rs.getString("BILL_TYPE"));
				dto.setBilling_bandwidth(rs.getString("BILLING_BANDWIDTH"));
				dto.setBilling_bandwidth_uom(rs.getString("BILLING_BANDWIDTH_UOM"));
				dto.setBilling_level(rs.getString("BILLING_LEVEL"));
				dto.setBilling_level_number(rs.getString("BILLING_LEVEL_NUMBER"));
				dto.setBilling_location_from(rs.getString("BILLING_LOCATION_FROM"));
				dto.setBilling_location_to(rs.getString("BILLING_LOCATION_TO"));
				dto.setBilling_mode(rs.getString("BILLING_MODE"));
				dto.setBilling_trig_flag(rs.getString("BILLING_TRIG_FLAG"));
				dto.setChallen_date(rs.getString("CHALLEN_DATE"));
				dto.setCharge_end_date(rs.getString("CHARGE_END_DATE"));
				dto.setCharge_hdr_id(rs.getString("CHARGE_HDR_ID"));
				dto.setCharge_name(rs.getString("CHARGE_NAME"));
				dto.setCharge_start_date(rs.getString("CHARGE_START_DATE"));
				dto.setCharge_status(rs.getString("CHARGE_STATUS"));
				dto.setCharge_type(rs.getString("CHARGE_TYPE"));
				dto.setCharge_type_id(rs.getString("CHARGE_TYPE_ID"));
				dto.setChargeable_distance(rs.getString("CHARGEABLE_DISTANCE"));
				dto.setChild_acc_fx_status(rs.getString("CHILD_ACC_FX_STATUS"));
				dto.setChild_acc_no(rs.getString("CHILD_ACC_NO"));
				dto.setCircuit_id(rs.getString("CIRCUIT_ID"));
				dto.setCommitment_period(rs.getString("COMMITMENT_PERIOD"));
				dto.setContract_period_months(rs.getString("CONTRACT_PERIOD_MONTHS"));
				dto.setCopc_approval_date(rs.getString("COPC_APPROVAL_DATE"));
				dto.setCredit_period(rs.getString("CREDIT_PERIOD"));
				dto.setCurrency(rs.getString("CURRENCY"));
				dto.setCust_acc_id(rs.getString("CUST_ACC_ID"));
				dto.setCust_logical_si_no(rs.getString("CUST_LOGICAL_SI_NO"));
				dto.setCust_po_date(rs.getString("CUST_PO_DATE"));
				dto.setCust_po_number(rs.getString("CUST_PO_NUMBER"));
				dto.setCust_po_receive_date(rs.getString("CUST_PO_RECEIVE_DATE"));
				dto.setCustomer_segment(rs.getString("CUSTOMER_SEGMENT"));
				dto.setCustomer_service_rfs_date(rs.getString("CUSTOMER_SERVICE_RFS_DATE"));
				dto.setDemo_type(rs.getString("DEMO_TYPE"));
				dto.setDisconnection_remark(rs.getString("DISCONNECTION_REMARK"));
				dto.setEnd_date_days(rs.getString("END_DATE_DAYS"));
				dto.setEnd_date_logic(rs.getString("END_DATE_LOGIC"));
				dto.setEnd_date_months(rs.getString("END_DATE_MONTHS"));
				dto.setForm_c_available(rs.getString("FORM_C_AVAILABLE"));
				dto.setFrequency(rs.getString("FREQUENCY"));
				dto.setHardware_flag(rs.getString("HARDWARE_FLAG"));
				dto.setHardware_type(rs.getString("HARDWARE_TYPE"));
				dto.setIndicative_value(rs.getString("INDICATIVE_VALUE"));
				dto.setInv_amt(rs.getString("INV_AMT"));
				dto.setLast_mile_media(rs.getString("LAST_MILE_MEDIA"));
				dto.setLast_mile_provider(rs.getString("LAST_MILE_PROVIDER"));
				dto.setLast_mile_remarks(rs.getString("LAST_MILE_REMARKS"));
				dto.setLegal_entity(rs.getString("LEGAL_ENTITY"));
				dto.setLicence_company(rs.getString("LICENCE_COMPANY"));
				dto.setLoc_date(rs.getString("LOC_DATE"));
				dto.setLoc_number(rs.getString("LOC_NUMBER"));
				dto.setLogical_circuit_id(rs.getString("LOGICAL_CIRCUIT_ID"));
				dto.setM6_order_id(rs.getString("M6_ORDER_ID"));
				dto.setNature_of_sale(rs.getString("NATURE_OF_SALE"));
				dto.setNew_order_remarks(rs.getString("NEW_ORDER_REMARKS"));
				dto.setNotice_period(rs.getString("NOTICE_PERIOD"));
				dto.setOrder_creation_date(rs.getString("ORDER_CREATION_DATE"));
				if (rs.getString("ORDER_CREATION_DATE") != null && !"".equals(rs.getString("ORDER_CREATION_DATE")))
				{
					dto.setOrder_creation_date((utility.showDate_Report(dto.getOrder_creation_date())).toUpperCase());
				}
				dto.setOrder_date(rs.getString("ORDER_DATE"));
				dto.setOrder_line_id(rs.getString("ORDER_LINE_ID"));
				dto.setOrder_month(rs.getString("ORDER_MONTH"));
				dto.setOrder_number(rs.getString("ORDER_NUMBER"));
				dto.setOrder_stage(rs.getString("ORDER_STAGE"));
				dto.setOrder_type(rs.getString("ORDER_TYPE"));
				dto.setParty(rs.getString("PARTY"));
				dto.setParty_id(rs.getString("PARTY_ID"));
				dto.setPenalty_clause(rs.getString("PENALTY_CLAUSE"));
				dto.setPeriod_in_month(rs.getString("PERIOD_IN_MONTH"));
				dto.setPk_chageges_id(rs.getString("PK_CHAGEGES_ID"));
				dto.setPm_prov_date(rs.getString("PM_PROV_DATE"));
				dto.setPo_date(rs.getString("PO_DATE"));
				dto.setPre_crm_order_id(rs.getString("PRE_CRM_ORDER_ID"));
				dto.setProduct(rs.getString("PRODUCT"));
				dto.setProduct_name(rs.getString("PRODUCT_NAME"));
				dto.setRate_code(rs.getString("RATE_CODE"));
				dto.setRegion(rs.getString("REGION"));
				dto.setRequest_received_date(rs.getString("REQUEST_RECEIVED_DATE"));
				dto.setSec_loc(rs.getString("SEC_LOC"));
				dto.setService_no(rs.getString("SERVICE_NO"));
				dto.setService_order_type(rs.getString("SERVICE_ORDER_TYPE"));
				dto.setService_stage(rs.getString("SERVICE_STAGE"));
				dto.setSr_number(rs.getString("SR_NUMBER"));
				dto.setStandard_reason(rs.getString("STANDARD_REASON"));
				dto.setStart_date_days(rs.getString("START_DATE_DAYS"));
				dto.setStart_date_logic(rs.getString("START_DATE_LOGIC"));
				dto.setStart_date_months(rs.getString("START_DATE_MONTHS"));
				dto.setStore(rs.getString("STORE"));
				dto.setSub_product(rs.getString("SUB_PRODUCT"));
				dto.setTaxation(rs.getString("TAXATION"));
				dto.setToken_no(rs.getString("TOKEN_NO"));
				dto.setTot_po_amt(rs.getString("TOT_PO_AMT"));
				dto.setTotal_amount(rs.getString("TOTAL_AMOUNT"));
				dto.setType_of_sale(rs.getString("TYPE_OF_SALE"));
				dto.setVertical(rs.getString("VERTICAL"));
				dto.setProject_mgr(rs.getString("PROJECT_MGR"));
				dto.setProject_mgr_email(rs.getString("PROJECT_MGR_EMAIL"));
				dto.setProvision_bandwidth(rs.getString("PROVISION_BANDWIDTH"));
				dto.setQuote_no(rs.getString("QUOTE_NO"));
				dto.setRatio(rs.getString("RATIO"));
				dto.setRegion_name(rs.getString("REGION_NAME"));
				dto.setRe_logged_lsi_no(rs.getString("RE_LOGGED_LSI_NO"));
				dto.setService_name(rs.getString("SERVICE_NAME"));
				dto.setService_number(rs.getString("SERVICE_NUMBER"));
				dto.setSub_product_type(rs.getString("SUB_PRODUCT_TYPE"));
				dto.setTo_city(rs.getString("TO_CITY"));
				dto.setTo_site(rs.getString("TO_SITE"));
				dto.setUom(rs.getString("UOM"));
				dto.setZone(rs.getString("ZONE"));
				dto.setDis_sr(rs.getString("DIS_SR"));
				dto.setDod(rs.getString("DOD"));
				dto.setOrder_no(rs.getString("ORDER_NO"));
				dto.setLogical_si_no(rs.getString("LOGICAL_SI_NO"));
				dto.setLine_it_no(rs.getString("LINE_IT_NO"));
				dto.setAccount_id(rs.getString("ACCOUNT_ID"));
				dto.setCkt_id(rs.getString("CKT_ID"));
				dto.setPackage_id(rs.getString("PACKAGE_ID"));
				dto.setPackage_name(rs.getString("PACKAGE_NAME"));
				dto.setComponentinfoid(rs.getString("COMPONENTINFOID"));
				dto.setComponent_id(rs.getString("COMPONENT_ID"));
				dto.setComponent_name(rs.getString("COMPONENT_NAME"));
				dto.setComponent_status(rs.getString("COMPONENT_STATUS"));
				dto.setComponent_start_logic(rs.getString("COMPONENT_START_LOGIC"));
				dto.setComponent_start_date(rs.getString("COMPONENT_START_DATE"));
				dto.setComponent_end_logic(rs.getString("COMPONENT_END_LOGIC"));
				dto.setComponent_end_date(rs.getString("COMPONENT_END_DATE"));
				dto.setComp_start_days(rs.getString("COMP_START_DAYS"));	
				dto.setComp_start_days(rs.getString("COMP_START_MONTHS"));
				dto.setComp_start_months(rs.getString("COMP_END_MONTHS"));
				dto.setComp_end_days(rs.getString("COMP_END_DAYS"));
				dto.setComp_end_months(rs.getString("COMP_END_MONTHS"));
				dto.setComponent_type(rs.getString("COMPONENT_TYPE"));
				dto.setComponent_instance_id(rs.getString("COMPONENT_INSTANCE_ID"));
				dto.setStart_component_token_no(rs.getString("START_COMPONENT_TOKEN_NO"));
				dto.setEnd_component_token_no(rs.getString("END_COMPONENT_TOKEN_NO"));
				dto.setHardware_type(rs.getString("HARDWARE_TYPE"));
				dto.setLink_type(rs.getString("LINK_TYPE"));
				dto.setTaxexcemption_reason(rs.getString("TAXEXEMPTION_REASON"));
				dto.setRate_code(rs.getString("RATE_CODE"));
				dto.setPri_loc(rs.getString("PRI_LOC"));
				dto.setSub_change_type(rs.getString("SUB_CHANGE_TYPE"));
				dto.setCHALLEN_NO(rs.getString("CHALLEN_NO"));
				dto.setLINE_NAME(rs.getString("LINE_NAME"));
				dto.setBilling_trig_date(rs.getString("BILLING_TRIG_DATE"));
				dto.setDispatch_address(rs.getString("DISPATCH_ADDRESS"));
				dto.setPo_number(rs.getString("PO_NUMBER"));
				dto.getInterfaceId();     
				if (pagingSorting.isPagingToBeDone() && recordCount==0) {
				recordCount = rs.getInt("FULL_REC_COUNT");
				}
				docListDetails.add(dto);
				
			}
			pagingSorting.setRecordCount(recordCount);
		} catch (Exception e) {
			
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);
		}
		finally
		{
			try 
			{
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(cstmt);
				DbConnection.freeConnection(connection);
			} 
			catch (Exception e) 
			{
			}
		}
			
		return docListDetails;
	
		
	}

	public ArrayList<ArchivalReportDto> cancelledReportOrder(ArchivalReportDto reportsDto) {
		
		String methodName="cancelledReportOrder";
		String className=this.getClass().getName();
	    String msg="";
		boolean logToFile=true, 
		logToConsole=true;	
		
		Connection connection = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		ArrayList docListDetails = new ArrayList();
	    int recordCount = 0;
	    ArchivalReportDto dto = null;
	    Utility utility=new Utility();
        SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy");
		String fromDate=reportsDto.getFromdate();
		String toDate=reportsDto.getTodate();

		try {
			
			PagingSorting pagingSorting = reportsDto.getPagingSorting();
			pagingSorting.sync();// To calculate start index and Enc Index
			connection = DbConnection.getConnectionObject();
			cstmt= connection.prepareCall(sqlGetCancelledtReport);
			
			
			if ( fromDate != null && !"".equals(fromDate)) {
				Date fDate=df.parse(fromDate);
				cstmt.setDate(1, new java.sql.Date(fDate.getTime()));
				} else {
					cstmt.setNull(1, java.sql.Types.DATE);
			}
			if ( toDate != null && !"".equals(toDate)) {
				Date tDate=df.parse(toDate);
				cstmt.setDate(2, new java.sql.Date(tDate.getTime()));
				} else {
					cstmt.setNull(2, java.sql.Types.DATE);
			}
			
			cstmt.setString(3,reportsDto.getAccount_id());
			cstmt.setString(4,reportsDto.getOrder_no());
			cstmt.setString(5,reportsDto.getLogical_si_no());
			cstmt.setString(6,reportsDto.getLine_it_no());
			cstmt.setString(7,reportsDto.getCkt_id());
			cstmt.setString(8,reportsDto.getM6orderno());
			cstmt.setString(9, pagingSorting.getSortByColumn());
			cstmt.setString(10, PagingSorting.DB_Asc_Desc(pagingSorting.getSortByOrder()));
			cstmt.setInt(11, pagingSorting.getStartRecordId());
			cstmt.setInt(12, pagingSorting.getEndRecordId());
			cstmt.setInt(13, (pagingSorting.isPagingToBeDone() ? 1 : 0));
			
			rs=cstmt.executeQuery();
			while(rs.next()){
				dto=new ArchivalReportDto();
			   

dto.setAccount_id(rs.getString("ACCOUNT_ID"));
dto.setAccount_manager(rs.getString("ACCOUNT_MANAGER"));
dto.setAccount_number(rs.getString("ACCOUNT_NUMBER"));
dto.setPo_amount(rs.getDouble("PO_AMOUNT"));
dto.setCustomer_segment(rs.getString("CUSTOMER_SEGMENT"));
dto.setAccount_category(rs.getString("ACCOUNT_CATEGORY"));
dto.setVertical(rs.getString("VERTICAL"));
dto.setBilling_charge_start_date(rs.getString("BILLING_CHARGE_START_DATE"));
if (rs.getString("BILLING_CHARGE_START_DATE") != null && !"".equals(rs.getString("BILLING_CHARGE_START_DATE")))
{
	dto.setBilling_charge_start_date((utility.showDate_Report(dto.getBilling_charge_start_date())).toUpperCase());
}

dto.setService_name(rs.getString("SERVICE_NAME"));
dto.setOrder_line_no(rs.getString("ORDER_LINE_NO"));
dto.setLine_name(rs.getString("LINE_NAME"));
dto.setCancel_flag(rs.getString("CANCEL_FLAG"));
dto.setProvision_bandwidth(rs.getString("PROVISION_BANDWIDTH"));
dto.setUom(rs.getString("UOM"));
dto.setBilling_bandwidth(rs.getString("BILLING_BANDWIDTH"));
dto.setStore_address(rs.getString("STORE_ADDRESS"));
dto.setBill_uom(rs.getString("BILL_UOM"));
dto.setCategory_of_order(rs.getString("CATEGORY_OF_ORDER"));
dto.setContract_period(rs.getString("CONTRACT_PERIOD"));
dto.setCompany_name(rs.getString("COMPANY_NAME"));
dto.setCustomer_rfs_date(rs.getString("CUSTOMER_RFS_DATE"));

if (rs.getString("CUSTOMER_RFS_DATE") != null && !"".equals(rs.getString("CUSTOMER_RFS_DATE")))
{
	dto.setCustomer_rfs_date((utility.showDate_Report3(dto.getCustomer_rfs_date())).toUpperCase());// issuee gui
}

dto.setCustomer_service_rfs_date(rs.getString("CUSTOMER_SERVICE_RFS_DATE"));

if (rs.getString("CUSTOMER_SERVICE_RFS_DATE") != null && !"".equals(rs.getString("CUSTOMER_SERVICE_RFS_DATE")))
{
	dto.setCustomer_service_rfs_date((utility.showDate_Report2(dto.getCustomer_service_rfs_date())).toUpperCase()); // issue GUI
}

dto.setCurrency(rs.getString("CURRENCY"));
dto.setCharge_name(rs.getString("CHARGE_NAME"));
dto.setCustomer_po_date(rs.getString("CUSTOMER_PO_DATE"));

if (rs.getString("CUSTOMER_PO_DATE") != null && !"".equals(rs.getString("CUSTOMER_PO_DATE")))
{

	dto.setCustomer_po_date((utility.showDate_Report2(dto.getCustomer_po_date())).toUpperCase());
}

dto.setCustomer_po_number(rs.getString("CUSTOMER_PO_NUMBER"));
dto.setCyclic_or_non_cyclic(rs.getString("CYCLIC_OR_NON_CYCLIC"));
dto.setChallen_no(rs.getString("CHALLEN_NO"));
dto.setOrder_no(rs.getString("ORDER_NO"));
dto.setFrom_site(rs.getString("FROM_SITE"));
dto.setTo_site(rs.getString("TO_SITE"));
dto.setItem_quantity(rs.getString("ITEM_QUANTITY"));
dto.setKms_distance(rs.getString("KMS_DISTANCE"));
dto.setLine_item_amount(rs.getDouble("LINE_ITEM_AMOUNT"));
dto.setCopc_approved_date(rs.getString("COPC_APPROVED_DATE"));

if (rs.getString("COPC_APPROVED_DATE") != null && !"".equals(rs.getString("COPC_APPROVED_DATE")))
{
	dto.setCopc_approved_date(utility.showDate_Report(new Date(rs.getTimestamp("COPC_APPROVED_DATE").getTime())));
}

dto.setLine_item_description(rs.getString("LINE_ITEM_DESCRIPTION"));
dto.setLoc_date(rs.getString("LOC_DATE"));

if (rs.getString("LOC_Date") != null && !"".equals(rs.getString("LOC_Date")))
{
	dto.setLoc_date((utility.showDate_Report2(dto.getLoc_date())).toUpperCase());
}

dto.setAccount_manager_receive_date(rs.getString("ACCOUNT_MANAGER_RECEIVE_DATE"));

if (rs.getString("ACCOUNT_MANAGER_RECEIVE_DATE") != null && !"".equals(rs.getString("ACCOUNT_MANAGER_RECEIVE_DATE")))
{
	dto.setAccount_manager_receive_date((utility.showDate_Report2(dto.getAccount_manager_receive_date())).toUpperCase());
}

dto.setOrder_total(rs.getDouble("ORDER_TOTAL"));
dto.setOrder_entry_date(rs.getString("ORDER_ENTRY_DATE"));

if (rs.getString("ORDER_ENTRY_DATE") != null && !"".equals(rs.getString("ORDER_ENTRY_DATE")))
{
	dto.setOrder_entry_date((utility.showDate_Report2(dto.getOrder_entry_date())).toUpperCase());
}

dto.setTaxation(rs.getString("TAXATION"));
dto.setTaxexemption_reason(rs.getString("TAXEXEMPTION_REASON"));
dto.setLicence_company(rs.getString("LICENCE_COMPANY"));
dto.setLogical_circuit_id(rs.getString("LOGICAL_CIRCUIT_ID"));
dto.setOrder_type(rs.getString("ORDER_TYPE"));
dto.setPayment_term(rs.getString("PAYMENT_TERM"));
dto.setProject_mgr(rs.getString("PROJECT_MGR"));
dto.setRegion_name(rs.getString("REGION_NAME"));
dto.setOld_line_item_amount(rs.getString("OLD_LINE_ITEM_AMOUNT"));
dto.setDemo_type(rs.getString("DEMO_TYPE"));
dto.setParty_name(rs.getString("PARTY_NAME"));
dto.setOrder_stage_description(rs.getString("ORDER_STAGE_DESCRIPTION"));
dto.setService_stage_description(rs.getString("SERVICE_STAGE_DESCRIPTION"));
dto.setCharge_end_date(rs.getString("CHARGE_END_DATE"));

if (rs.getString("CHARGE_END_DATE") != null && !"".equals(rs.getString("CHARGE_END_DATE")))
{
	dto.setCharge_end_date((utility.showDate_Report2(dto.getCharge_end_date())).toUpperCase());
}

dto.setEnd_date_logic(rs.getString("END_DATE_LOGIC"));
dto.setNew_order_remark(rs.getString("NEW_ORDER_REMARK"));
dto.setRemarks(rs.getString("REMARKS"));
dto.setService_order_type(rs.getString("SERVICE_ORDER_TYPE"));
dto.setOsp(rs.getString("OSP"));
dto.setOpportunity_id(rs.getString("OPPORTUNITY_ID"));
dto.setLogical_si_no(rs.getString("LOGICAL_SI_NO"));
dto.setLine_it_no(rs.getString("LINE_IT_NO"));
dto.setOrder_creation_date(rs.getString("ORDER_CREATION_DATE"));
if (rs.getString("ORDER_CREATION_DATE") != null && !"".equals(rs.getString("ORDER_CREATION_DATE")))
{
	dto.setOrder_creation_date((utility.showDate_Report(dto.getOrder_creation_date())).toUpperCase());	
}
dto.setCkt_id(rs.getString("CKT_ID"));
dto.setM6orderno(rs.getString("M6_ORDER_NO"));
dto.setService_id(rs.getString("SERVICEID"));
dto.setOldLsi(rs.getString("OLD_LSI"));
dto.setCancel_reason(rs.getString("CANCEL_REASON"));
dto.setCancel_date(rs.getString("CANCEL_DATE"));

if (rs.getString("CANCEL_DATE") != null && !"".equals(rs.getString("CANCEL_DATE")))
{
	dto.setCancel_date((utility.showDate_Report2(dto.getCancel_date())).toUpperCase());	
}

dto.setProduct(rs.getString("PRODUCT"));
dto.setSub_product(rs.getString("SUB_PRODUCT"));
dto.setEffective_start_data(rs.getString("EFFECTIVE_START_DATA"));


if (rs.getString("EFFECTIVE_START_DATA") != null && !"".equals(rs.getString("EFFECTIVE_START_DATA")))
{
	dto.setEffective_start_data((utility.showDate_Report(dto.getEffective_start_data())).toUpperCase());	
}
dto.getInterfaceid();
				docListDetails.add(dto);
				if (pagingSorting.isPagingToBeDone() && recordCount==0) {
					recordCount = rs.getInt("FULL_REC_COUNT");
					}
					docListDetails.add(dto);
							}
			pagingSorting.setRecordCount(recordCount);
		} catch (Exception e) {
			
			Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);
		}
		finally
		{
			try 
			{
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(cstmt);
				DbConnection.freeConnection(connection);
			} 
			catch (Exception e) 
			{
			}
		}
			
		return docListDetails;
	
		
	}
	//priya
	public ArrayList<ParallelUpgradeReportDto> viewParallelUpgradeReport(
			ParallelUpgradeReportDto objDto,String inExcel,HttpServletResponse response) {
		
		String methodName="viewParallelUpgradeReport", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;	
		//end
		Connection connection =null;
		CallableStatement proc =null;
		ResultSet rs = null;
		ArrayList<ParallelUpgradeReportDto> listSearchDetails = new ArrayList<ParallelUpgradeReportDto>();
		ParallelUpgradeReportDto objReportsDto = null;
		int recordCount =0;
		Utility utility=new Utility();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try
		{
			connection=DbConnection.getReportsConnectionObject();
			proc= connection.prepareCall(sqlGetParallelUpgradeReport);
			
			proc.setInt(1, objDto.getChangeTypeId());
			
			/*if (objDto.getFromDate() != null
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
			}*/
			if (objDto.getFromDate() != null
					&& !"".equals(objDto.getFromDate())) {
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				Date dateStr = formatter.parse(objDto.getFromDate());
				String formattedDate = formatter.format(dateStr);
				Date date1 = formatter.parse(formattedDate);
				formatter = new SimpleDateFormat("yyyy-MM-dd");
				formattedDate = formatter.format(date1);
				proc.setString(2,formattedDate);
			} else {
				proc.setNull(2, java.sql.Types.VARCHAR);
			}
			if (objDto.getToDate() != null 
					&& !"".equals(objDto.getToDate())) {
				SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
				Date dateStr1 = formatter1.parse(objDto.getToDate());
				String formattedDate1 = formatter1.format(dateStr1);
				Date date2 = formatter1.parse(formattedDate1);
				formatter1 = new SimpleDateFormat("yyyy-MM-dd");
				formattedDate1 = formatter1.format(date2);
				proc.setString(3,formattedDate1);
			} else {
				proc.setNull(3, java.sql.Types.VARCHAR);
			}
			if (objDto.getFromServiceNo() != 0 
					&& !"".equals(objDto.getFromServiceNo())) {
				proc.setInt(4, objDto.getFromServiceNo());
			} else {
				proc.setNull(4,java.sql.Types.BIGINT);
			}
			if (objDto.getToServiceNo() != 0 
					&& !"".equals(objDto.getToServiceNo())) {
				proc.setInt(5, objDto.getToServiceNo());
			} else {
				proc.setNull(5, java.sql.Types.BIGINT);
			}
			if (objDto.getCustomerSegment() != null
					&& !"".equals(objDto.getCustomerSegment()) && !objDto.getCustomerSegment().equals("0")) {
				proc.setString(6, objDto.getCustomerSegment());
			} else {
				proc.setNull(6, java.sql.Types.VARCHAR);
			}
			if (objDto.getExclude_comp_orders() != null 
					&& !"".equals(objDto.getExclude_comp_orders())) {
				proc.setString(7, objDto.getExclude_comp_orders().trim());
			} else {
				proc.setNull(7,java.sql.Types.VARCHAR);
			}
			PagingSorting pagingSorting = objDto.getPagingSorting();
			pagingSorting.sync();// To calculate start index and End Index
			proc.setString(8, pagingSorting.getSortByColumn());// columnName
			proc.setString(9, PagingSorting.DB_Asc_Desc(pagingSorting
					.getSortByOrder()));// sort order
			proc.setInt(10, pagingSorting.getStartRecordId());// start index
			proc.setInt(11, pagingSorting.getEndRecordId());// end index
			proc.setInt(12, (pagingSorting.isPagingToBeDone() ? 1 : 0));
			// index
			rs = proc.executeQuery();
			StringBuffer row = new StringBuffer();
			if("true".equals(inExcel))
			{
				setResponseHeaderForParallelUpgrade(response);
				startTableHeader(row);
				columnHeadeForParallelUpgrade(response,row);
			}
			while(rs.next())
			{
				objReportsDto =  new ParallelUpgradeReportDto();
				objReportsDto.setCustomerSegment(Utility.fnCheckNull(rs.getString("CUST_SEGMENT_CODE")));
				objReportsDto.setOrderType(Utility.fnCheckNull(rs.getString("ORDERTYPE")));
				objReportsDto.setChangeType(Utility.fnCheckNull(rs.getString("CHANGETYPENAME")));				
				objReportsDto.setCustomername(Utility.fnCheckNull(rs.getString("PARTYNAME")));
				objReportsDto.setCrm_order_id(rs.getInt("ORDERNO"));
				objReportsDto.setOrderDate(Utility.fnCheckNull(rs.getString("ORDERDATE")));
				if (rs.getString("ORDERDATE") != null && !"".equals(rs.getString("ORDERDATE"))) 
				{
					objReportsDto.setOrderDate((utility.showDate_Report(new Date(rs.getTimestamp("ORDERDATE").getTime()))).toUpperCase());
				}
				objReportsDto.setCopcApprovalDate(Utility.fnCheckNull(rs.getString("COPC_APPROVAL_DATE")));
				if (rs.getString("COPC_APPROVAL_DATE") != null && !"".equals(rs.getString("COPC_APPROVAL_DATE"))) 
				{
					objReportsDto.setCopcApprovalDate((utility.showDate_Report(new Date(rs.getTimestamp("COPC_APPROVAL_DATE").getTime()))).toUpperCase());
				}
				objReportsDto.setLogical_si_no(rs.getInt("LOGICAL_SI_NO"));
				objReportsDto.setServiceNo(rs.getInt("SERVICEID"));
				objReportsDto.setChangeReason(Utility.fnCheckNull(rs.getString("CHANGE_REASON")));
				objReportsDto.setLsi_disconnection_SRNO(Utility.fnCheckNull(rs.getString("SRNO")));
				objReportsDto.setService_stage(Utility.fnCheckNull(rs.getString("service_stage")));
				objReportsDto.setServiceBTActionDate(Utility.fnCheckNull(rs.getString("SERVICE_BT_ACTION_DATE")));
				if (rs.getString("SERVICE_BT_ACTION_DATE") != null && !"".equals(rs.getString("SERVICE_BT_ACTION_DATE"))) 
				{
					objReportsDto.setServiceBTActionDate((utility.showDate_Report(new Date(rs.getTimestamp("SERVICE_BT_ACTION_DATE").getTime()))).toUpperCase());
				}
				objReportsDto.setAttribute_remarks(Utility.fnCheckNull(rs.getString("SERVICE_REMARKS")));
				objReportsDto.setBin(Utility.fnCheckNull(rs.getString("BIN"))); 
				objReportsDto.setOld_lsi(rs.getInt("OLD_LSI"));
				objReportsDto.setOld_lsi_lateststage(Utility.fnCheckNull(rs.getString("OLD_LSI_LATEST_STAGE")));
				objReportsDto.setOld_lsi_BT_ActionDate(Utility.fnCheckNull(rs.getString("Old_Lsi_BT_Action_Date")));
				if (rs.getString("Old_Lsi_BT_Action_Date") != null && !"".equals(rs.getString("Old_Lsi_BT_Action_Date"))) 
				{
					objReportsDto.setOld_lsi_BT_ActionDate((utility.showDate_Report(new Date(rs.getTimestamp("Old_Lsi_BT_Action_Date").getTime()))).toUpperCase());
				}
				objReportsDto.setOld_lsi_disconnection_SRno(Utility.fnCheckNull(rs.getString("OLD_LSI_Disconnetion_SRNO")));
				if (pagingSorting.isPagingToBeDone()) {
					recordCount = rs.getInt("FULL_REC_COUNT");
				}
				if("true".equals(inExcel))
				{
					savedatainexcel(objReportsDto,response);
				}
				else
				{
					listSearchDetails.add(objReportsDto);
				}
			}
			
			if("true".equals(inExcel))
			{
				endTable(response);
				closePrintWriterForParallelUpgrade(response);
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
		if("true".equals(inExcel))
		{
			return null;
		}
		else{
		return listSearchDetails;
		}
	}
		
		
	private void closePrintWriterForParallelUpgrade(
			HttpServletResponse response) throws Exception {
		PrintWriter pw = response.getWriter();
		if(null != pw)
			pw.close();
	}

	private void setResponseHeaderForParallelUpgrade(HttpServletResponse response) {
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("content-Disposition","attachment;filename=ParallelUpgradeReport.xls");
	}

	private void columnHeadeForParallelUpgrade(HttpServletResponse response,StringBuffer row) throws Exception {
		PrintWriter pw = response.getWriter();
		try{
			startRow(row);
			addCell(row,"Customer Segment");
			addCell(row,"Order Type");
			addCell(row,"Change Type");
			addCell(row,"Customer Name");
			addCell(row,"CRM_Order_ID");
			addCell(row,"Order Date");
			addCell(row,"COPC Approval Date");
			addCell(row,"Logical Circuit Id");
			addCell(row,"Service No");
			addCell(row,"Change Reason");
			addCell(row,"LSI_Disconnection_SR_No");
			addCell(row,"Service_Stage");
			addCell(row,"Service BT Action Date");
			addCell(row,"Attribute Remarks");
			addCell(row,"Bin");
			addCell(row,"OLD LSI");
			addCell(row,"OLD LSI _Latest stage");
			addCell(row,"Old_Lsi_BT_Action_Date");
			addCell(row,"OLD_LSI_Disconnetion_SRNO");
			endRow(row);
			
			pw.write(row.toString());
			response.flushBuffer();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	private void savedatainexcel(ParallelUpgradeReportDto objReportsDto,HttpServletResponse response) throws Exception {
		
		PrintWriter pw = response.getWriter();

		StringBuffer row=new StringBuffer();
			try{
		
				startRow(row);
				addCell(row,objReportsDto.getCustomerSegment());
				addCell(row,objReportsDto.getOrderType());
				addCell(row,objReportsDto.getChangeType());
				addCell(row,objReportsDto.getCustomername());
				addCellint(row,objReportsDto.getCrm_order_id());
				addCell(row,objReportsDto.getOrderDate());
				addCell(row,objReportsDto.getCopcApprovalDate());
				addCellint(row,objReportsDto.getLogical_si_no());
				addCellint(row,objReportsDto.getServiceNo());
				addCell(row,objReportsDto.getChangeReason());
				addCell(row,objReportsDto.getLsi_disconnection_SRNO());
				addCell(row,objReportsDto.getService_stage());
				addCell(row,objReportsDto.getServiceBTActionDate());
				addCell(row,objReportsDto.getAttribute_remarks());
				addCell(row,objReportsDto.getBin());
				addCellint(row,objReportsDto.getOld_lsi());
				addCell(row,objReportsDto.getOld_lsi_lateststage());
				addCell(row,objReportsDto.getOld_lsi_BT_ActionDate());
				addCell(row,objReportsDto.getOld_lsi_disconnection_SRno());
				endRow(row);
				
				pw.write(row.toString());	
				response.flushBuffer();
				}
			catch (Exception e){
				e.printStackTrace();
			}
			/*finally{
				if(null != pw)
					pw.close();
			}*/
	}
	private void addCell(StringBuffer row, String data) {
		row.append("<td>").append(data).append("</td>");
	}
	private void addCellint(StringBuffer row, int data) {
		row.append("<td>").append(data).append("</td>");
	}
	private void endRow(StringBuffer row) {
		row.append("</tr>");
	}

	private void startRow(StringBuffer row) {
		row.append("\n<tr>");
	}
	private void startTableHeader(StringBuffer row) {
		row.append("<table border=\"1\">");
	}
	private void endTable(HttpServletResponse response) throws Exception {
		response.getWriter().write("\n</table>");
	}



}