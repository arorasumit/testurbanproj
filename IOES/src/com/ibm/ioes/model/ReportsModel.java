//TagName Resource Name     Date		CSR No			Description 
//[001]	   LawKush	 10-Feb-11	     CSR00-05422     for orders pending in billing and hardware
//[0011] Rohit Verma	20-FEB-2013		 CSR00-07480	New Method to call jsp of Export to Excel for LSI Mapping Report
//[0012] Rohit Verma	5-Mar-13		CSR00-08440		Function for Searching Report for Hardware Canecl LIne item
//[101010]Rampratap 06-03-13 added for clep order report
//[HYPR22032013001]    Vijay    30-March-2013          Billing Work Queue Report //
// [008] Shourya Mishra	30-Nov-13 		CSR-09463		Advance Payment / OTC Report
//[009] Rohit Verma		11-Dec-13 CSR-09083			Line Item Dump for Bulk Upload
//[10] Santosh added a method viewActiveLineDemoList() on 28/02/2014
//[0020]   Gunjan Singla    1-Dec-14              Document Matrix Report
package com.ibm.ioes.model;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.ibm.ioes.beans.ArchivalReportBean;
import com.ibm.ioes.dao.NewOrderDaoExt;
import com.ibm.ioes.dao.ReportsDao;
import com.ibm.ioes.forms.ArchivalReportDto;
import com.ibm.ioes.forms.BCPAddressDto;
import com.ibm.ioes.forms.ChangeOrderTypeDto;
import com.ibm.ioes.forms.DispatchAddressDto;
import com.ibm.ioes.forms.LineItemDTO;
import com.ibm.ioes.forms.M6OrderStatusDto;
import com.ibm.ioes.forms.NetworkLocationDto;
import com.ibm.ioes.forms.LocationDetailDto;
import com.ibm.ioes.forms.NewOrderDto;
import com.ibm.ioes.forms.ProductCatelogDTO;
import com.ibm.ioes.forms.ReportsDto;
import com.ibm.ioes.forms.MastersAttributesDto;
import com.ibm.ioes.forms.UserAccessMatrixDto;
import com.ibm.ioes.newdesign.dto.ActiveLineDemoReportDTO;
import com.ibm.ioes.newdesign.dto.ActiveLineItemReportsDTO;
import com.ibm.ioes.newdesign.dto.AdvancePaymentReportDTO;
import com.ibm.ioes.newdesign.dto.BillingWorkQueueReportDTO;
import com.ibm.ioes.newdesign.dto.CopyCancelReportDTO;
import com.ibm.ioes.newdesign.dto.DisconnectChangeOrdeReportDTO;
import com.ibm.ioes.newdesign.dto.DisconnectChangeOrdeReportDTO;
import com.ibm.ioes.newdesign.dto.DocumentMatrixReportDTO;
import com.ibm.ioes.newdesign.dto.DummyLinesDetailsReportDTO;
import com.ibm.ioes.newdesign.dto.M6OrderCancelReportDTO;
import com.ibm.ioes.newdesign.dto.OBValueReportDTO;
import com.ibm.ioes.newdesign.dto.OrderClepReportDTO;
import com.ibm.ioes.newdesign.dto.OrderDetailChangeReportDTO;
import com.ibm.ioes.newdesign.dto.OrderDetailReportDTO;
import com.ibm.ioes.newdesign.dto.OrderReportNewDetailCwnDTO;
import com.ibm.ioes.newdesign.dto.OrderStageReportDTO;
import com.ibm.ioes.newdesign.dto.PendingOrderAndBillingReportDTO;
import com.ibm.ioes.newdesign.dto.PendingServicesReportDTO;
import com.ibm.ioes.newdesign.dto.PerformanceDetailReportDTO;
import com.ibm.ioes.newdesign.dto.PerformanceReportDTO;
import com.ibm.ioes.newdesign.dto.RateRenewalReportDTO;
import com.ibm.ioes.newdesign.dto.ParallelUpgradeReportDto;
import com.ibm.ioes.newdesign.dto.TelemediaOrderReportDTO;
import com.ibm.ioes.newdesign.dto.AttributeDetailsReportDTO;
import com.ibm.ioes.newdesign.dto.BillingTriggerDoneButFailedInFXDTO;
import com.ibm.ioes.newdesign.dto.BulkSIUploadReportDto;
import com.ibm.ioes.newdesign.dto.CancelledFailedLineReportDTO;
import com.ibm.ioes.newdesign.dto.DisconnectLineReportDTO;
import com.ibm.ioes.newdesign.dto.HardwareLineItemCancelReportDTO;
import com.ibm.ioes.newdesign.dto.LempCancelOrderReportDTO;
import com.ibm.ioes.newdesign.dto.LempOrderDetailsReportDTO;
import com.ibm.ioes.newdesign.dto.LempOwnerReportDTO;
import com.ibm.ioes.newdesign.dto.LogicalSIDataReportDTO;
import com.ibm.ioes.newdesign.dto.MigratedApprovedNewOrderDetailReportDTO;
import com.ibm.ioes.newdesign.dto.NonAPP_APPChangeOrderDetailsDTO;
import com.ibm.ioes.newdesign.dto.NonMigratedAPP_UNAPPNewOrderDetailsDTO;
import com.ibm.ioes.newdesign.dto.PendingOrdersAndBillingHardwaresDTO;
import com.ibm.ioes.newdesign.dto.PerformanceSummaryReportDTO;
import com.ibm.ioes.newdesign.dto.RestPendingLineReportDTO;
import com.ibm.ioes.newdesign.dto.StartChargeNotPushedInFXDTO;
import com.ibm.ioes.newdesign.dto.ZeroOrderValueReportDTO;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.Utility;

public class ReportsModel {

	public ArrayList<BCPAddressDto> viewBCPList(BCPAddressDto objDto)
			throws Exception {
		ArrayList<BCPAddressDto> objList = new ArrayList<BCPAddressDto>();
		ReportsDao objDao = new ReportsDao();
		try {
			objList = objDao.viewBCPList(objDto);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("SQL Exception : " + ex.getMessage(), ex);
		}
		return objList;
	}
	
	public ArrayList<M6OrderStatusDto> viewM6OrderList(M6OrderStatusDto objDto)
	throws Exception {
	ArrayList<M6OrderStatusDto> objList = new ArrayList<M6OrderStatusDto>();
	ReportsDao objDao = new ReportsDao();
	try {
		objList = objDao.viewM6OrderList(objDto);
	} catch (Exception ex) {
		ex.printStackTrace();
		throw new Exception("SQL Exception : " + ex.getMessage(), ex);
	}
	return objList;
	}
	
	public ArrayList<M6OrderStatusDto> viewM6ResponseHistory(M6OrderStatusDto objDto,long m6OrderNo)
	throws Exception {
	ArrayList<M6OrderStatusDto> objList = new ArrayList<M6OrderStatusDto>();
	ReportsDao objDao = new ReportsDao();
	try {
		objList = objDao.viewM6ResponseHistory(objDto,m6OrderNo);
	} catch (Exception ex) {
		ex.printStackTrace();
		throw new Exception("SQL Exception : " + ex.getMessage(), ex);
	}
	return objList;
	}


	public ArrayList<NetworkLocationDto> viewNetworkLocsList(
			NetworkLocationDto objDto) throws Exception {
		ArrayList<NetworkLocationDto> objList = new ArrayList<NetworkLocationDto>();
		ReportsDao objDao = new ReportsDao();
		try {
			objList = objDao.viewNetworkLocsList(objDto);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("SQL Exception : " + ex.getMessage(), ex);
		}
		return objList;
	}
		public ArrayList<LocationDetailDto> viewCustomerLocationList(LocationDetailDto objDto)
			throws Exception {
		ArrayList<LocationDetailDto> objList = new ArrayList<LocationDetailDto>();
		ReportsDao objDao = new ReportsDao();
		try {
			objList = objDao.viewCustomerLocationList(objDto);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("SQL Exception : " + ex.getMessage(), ex);
		}
		return objList;
	}
		
		public ArrayList<NewOrderDto> viewContactList(NewOrderDto objDto)
		throws Exception {
		ArrayList<NewOrderDto> objList = new ArrayList<NewOrderDto>();
		ReportsDao objDao = new ReportsDao();
		try {
			objList = objDao.viewContactList(objDto);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("SQL Exception : " + ex.getMessage(), ex);
		}
		return objList;
}
	public ArrayList<DispatchAddressDto> viewDispatchAddressList(DispatchAddressDto objDto)
		throws Exception {
	ArrayList<DispatchAddressDto> objList = new ArrayList<DispatchAddressDto>();
	ReportsDao objDao = new ReportsDao();
	try {
		objList = objDao.viewDispatchAddressList(objDto);
	} catch (Exception ex) {
		ex.printStackTrace();
		throw new Exception("SQL Exception : " + ex.getMessage(), ex);
	}
	return objList;
}
		

	public ArrayList<NewOrderDto> viewOrderStatusList(NewOrderDto objDto)
		throws Exception {
		
		ArrayList<NewOrderDto> objList = new ArrayList<NewOrderDto>();
		ReportsDao objDao = new ReportsDao();
		try {
			objList = objDao.viewOrderStatusList(objDto);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("SQL Exception : " + ex.getMessage(), ex);
		}
		return objList;
}

	public ArrayList<ReportsDto> viewMasterHistory(ReportsDto objDto)throws Exception 
	{
	
		ArrayList<ReportsDto> objList = new ArrayList<ReportsDto>();
		ReportsDao objDao = new ReportsDao();
		try {
			objList = objDao.viewMasterHistory(objDto);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("SQL Exception : " + ex.getMessage(), ex);
		}
		return objList;
	}
	public ArrayList<OrderStageReportDTO> viewOrderStageList(OrderStageReportDTO objDto)
	throws Exception {
	
	ArrayList<OrderStageReportDTO> objList = new ArrayList<OrderStageReportDTO>();
	ReportsDao objDao = new ReportsDao();
	try {
		objList = objDao.viewOrderStageList(objDto);
	} catch (Exception ex) {
		ex.printStackTrace();
		throw new Exception("SQL Exception : " + ex.getMessage(), ex);
	}
	return objList;
}
//	Ramana
	public ArrayList<PerformanceReportDTO> viewPerformanceList(PerformanceReportDTO objDto) throws Exception
	{
		ArrayList<PerformanceReportDTO> listSearchDetails= new ArrayList<PerformanceReportDTO>();
		ReportsDao objDao = new ReportsDao();
		try
		{
			listSearchDetails = objDao.viewPerformanceList(objDto);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listSearchDetails;
	}
	//Ramana
	public ArrayList<StartChargeNotPushedInFXDTO> viewStartChargeNotPushedInFx(StartChargeNotPushedInFXDTO objDto) throws Exception
	{
		ArrayList<StartChargeNotPushedInFXDTO> listSearchDetails= new ArrayList<StartChargeNotPushedInFXDTO>();
		ReportsDao objDao = new ReportsDao();
		try
		{
			listSearchDetails = objDao.viewStartChargeNotPushedInFx(objDto);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listSearchDetails;
	}
	public ArrayList<LogicalSIDataReportDTO> viewLogicalSIDataReport(LogicalSIDataReportDTO objDto) throws Exception
	{
		ArrayList<LogicalSIDataReportDTO> listSearchDetails= new ArrayList<LogicalSIDataReportDTO>();
		ReportsDao objDao = new ReportsDao();
		try
		{
			listSearchDetails = objDao.viewLogicalSIDataReport(objDto);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listSearchDetails;
	}
	
	public ArrayList<NonMigratedAPP_UNAPPNewOrderDetailsDTO> viewNonMigAppUnappNewOrderDetails(NonMigratedAPP_UNAPPNewOrderDetailsDTO objDto) throws Exception
	{
		ArrayList<NonMigratedAPP_UNAPPNewOrderDetailsDTO> listSearchDetails= new ArrayList<NonMigratedAPP_UNAPPNewOrderDetailsDTO>();
		ReportsDao objDao = new ReportsDao();
		try
		{
			listSearchDetails = objDao.viewNonMigAppUnappNewOrderDetails(objDto);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listSearchDetails;
	}
	
	
	public ArrayList<BillingTriggerDoneButFailedInFXDTO> viewBillingTriggerDoneButFailedInFX(BillingTriggerDoneButFailedInFXDTO objDto) throws Exception
	{
		ArrayList<BillingTriggerDoneButFailedInFXDTO> listSearchDetails= new ArrayList<BillingTriggerDoneButFailedInFXDTO>();
		ReportsDao objDao = new ReportsDao();
		try
		{
			listSearchDetails = objDao.viewBillingTriggerDoneButFailedInFX(objDto);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listSearchDetails;
	}
	public ArrayList<PerformanceSummaryReportDTO> viewPerformanceSummaryReport(PerformanceSummaryReportDTO objDto) throws Exception
	{
		ArrayList<PerformanceSummaryReportDTO> listSearchDetails= new ArrayList<PerformanceSummaryReportDTO>();
		ReportsDao objDao = new ReportsDao();
		try
		{
			listSearchDetails = objDao.viewPerformanceSummaryReport(objDto);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listSearchDetails;
	}
	public ArrayList<PendingServicesReportDTO> viewPendingServicesReport(PendingServicesReportDTO objDto) throws Exception
	{
		ArrayList<PendingServicesReportDTO> listSearchDetails= new ArrayList<PendingServicesReportDTO>();
		ReportsDao objDao = new ReportsDao();
		try
		{
			listSearchDetails = objDao.viewPendingServicesReport(objDto);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listSearchDetails;
	}
	public ArrayList<ActiveLineItemReportsDTO> viewActiveLineItemsList(ActiveLineItemReportsDTO objDto)
	throws Exception {
	
	ArrayList<ActiveLineItemReportsDTO> objList = new ArrayList<ActiveLineItemReportsDTO>();
	ReportsDao objDao = new ReportsDao();
	try {
		objList = objDao.viewActiveLineItemsList(objDto);
	} catch (Exception ex) {
		ex.printStackTrace();
		throw new Exception("SQL Exception : " + ex.getMessage(), ex);
	}
	return objList;
}
	
	

	public ArrayList<OrderReportNewDetailCwnDTO> viewOrderReportNew(OrderReportNewDetailCwnDTO objDto)
			throws Exception {

		ArrayList<OrderReportNewDetailCwnDTO> objList = new ArrayList<OrderReportNewDetailCwnDTO>();
		ReportsDao objDao = new ReportsDao();
		try {
			objList = objDao.viewOrderReportNew(objDto);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("SQL Exception : " + ex.getMessage(), ex);
		}
		return objList;
	}

	
	public ArrayList<OrderDetailChangeReportDTO> viewOrderReportChange(OrderDetailChangeReportDTO objDto)
	throws Exception {

		ArrayList<OrderDetailChangeReportDTO> objList = new ArrayList<OrderDetailChangeReportDTO>();
		ReportsDao objDao = new ReportsDao();
		try {
			objList = objDao.viewOrderReportChange(objDto);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("SQL Exception : " + ex.getMessage(), ex);
		}
		return objList;
}
	
	
	public ArrayList<OrderDetailReportDTO> viewOrderReportDetails(OrderDetailReportDTO objDto)
	throws Exception {

		ArrayList<OrderDetailReportDTO> objList = new ArrayList<OrderDetailReportDTO>();
		ReportsDao objDao = new ReportsDao();
		try {
			objList = objDao.viewOrderReportDetails(objDto);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("SQL Exception : " + ex.getMessage(), ex);
		}
		return objList;
}
	
		//Saurabh
	public ArrayList<PendingOrderAndBillingReportDTO> viewPendingOrderAndBillingList(PendingOrderAndBillingReportDTO objDto)
	throws Exception {
	
	ArrayList<PendingOrderAndBillingReportDTO> objList = new ArrayList<PendingOrderAndBillingReportDTO>();
	ReportsDao objDao = new ReportsDao();
	try {
		objList = objDao.viewPendingOrderAndBillingList(objDto);
	} catch (Exception ex) {
		ex.printStackTrace();
		throw new Exception("SQL Exception : " + ex.getMessage(), ex);
	}
	return objList;
}
	// added by lawkush 
//	[001]	START 
	public ArrayList<PendingOrdersAndBillingHardwaresDTO> pendingOrderBill(PendingOrdersAndBillingHardwaresDTO objDto)
	throws Exception {
	
	ArrayList<PendingOrdersAndBillingHardwaresDTO> objList = new ArrayList<PendingOrdersAndBillingHardwaresDTO>();
	ReportsDao objDao = new ReportsDao();
	try {
		objList = objDao.ViewpendingOrderBillandHardwareList(objDto);
	} catch (Exception ex) {
		ex.printStackTrace();
		throw new Exception("SQL Exception : " + ex.getMessage(), ex);
	}
	return objList;
}
	
public ArrayList<M6OrderCancelReportDTO> viewM6OrderCancelReport(M6OrderCancelReportDTO objDto)
	throws Exception {
	
	ArrayList<M6OrderCancelReportDTO> objList = new ArrayList<M6OrderCancelReportDTO>();
	ReportsDao objDao = new ReportsDao();
	try {
		objList = objDao.viewM6OrderCancelReport(objDto);
	} catch (Exception ex) {
		ex.printStackTrace();
		throw new Exception("SQL Exception : " + ex.getMessage(), ex);
	}
	return objList;
}
	
	
//	[001]	END
	
	public ArrayList<PerformanceDetailReportDTO> viewPerformanceDetailList(PerformanceDetailReportDTO objDto) throws Exception
	{
		ArrayList<PerformanceDetailReportDTO> listSearchDetails= new ArrayList<PerformanceDetailReportDTO>();
		ReportsDao objDao = new ReportsDao();
		try
		{
			listSearchDetails = objDao.viewPerformanceDetailList(objDto);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listSearchDetails;
	}
	public ArrayList<TelemediaOrderReportDTO> getTelemediaOrderList(TelemediaOrderReportDTO objDto) throws Exception
	{
		ArrayList<TelemediaOrderReportDTO> listSearchDetails= new ArrayList<TelemediaOrderReportDTO>();
		ReportsDao objDao = new ReportsDao();
		try
		{
			listSearchDetails = objDao.getTelemediaOrderList(objDto);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listSearchDetails;
	}
	public ArrayList<ZeroOrderValueReportDTO> viewZeroOrderValueReportDetails(ZeroOrderValueReportDTO objDto) throws Exception
	{
		ArrayList<ZeroOrderValueReportDTO> listSearchDetails= new ArrayList<ZeroOrderValueReportDTO>();
		ReportsDao objDao = new ReportsDao();
		try
		{
			listSearchDetails = objDao.viewZeroOrderValueReportDetails(objDto);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listSearchDetails;
	}
	
	public ArrayList<RateRenewalReportDTO> viewRateRenewalReport(RateRenewalReportDTO objDto) throws Exception
	{
		ArrayList<RateRenewalReportDTO> listSearchDetails= new ArrayList<RateRenewalReportDTO>();
		ReportsDao objDao = new ReportsDao();
		try
		{
			listSearchDetails = objDao.viewRateRenewalReport(objDto);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listSearchDetails;
	}
	
	public ArrayList<RestPendingLineReportDTO> viewRestPendingLineReport(RestPendingLineReportDTO objDto)
	throws Exception {
	
	ArrayList<RestPendingLineReportDTO> objList = new ArrayList<RestPendingLineReportDTO>();
	ReportsDao objDao = new ReportsDao();
	try {
		objList = objDao.viewRestPendingLineReport(objDto);
	} catch (Exception ex) {
		ex.printStackTrace();
		throw new Exception("SQL Exception : " + ex.getMessage(), ex);
	}
	return objList;
	
	}	
	
/*Vijay start of Pending Line Report */
	
	public ArrayList<ReportsDto> viewPendingLineReport(ReportsDto objDto)
	throws Exception {
	
	ArrayList<ReportsDto> objList = new ArrayList<ReportsDto>();
	ReportsDao objDao = new ReportsDao();
	try {
		objList = objDao.viewPendingLineReport(objDto);
	} catch (Exception ex) {
		ex.printStackTrace();
		throw new Exception("SQL Exception : " + ex.getMessage(), ex);
	}
	return objList;
	
	}	
	/*Vijay end of Pending Line Report */
	
	public ArrayList<DisconnectLineReportDTO> viewDisconnectionLineReport(DisconnectLineReportDTO objDto)
	throws Exception {
	
	ArrayList<DisconnectLineReportDTO> objList = new ArrayList<DisconnectLineReportDTO>();
	ReportsDao objDao = new ReportsDao();
	try {
		
		objList = objDao.viewDisconnectionLineReport(objDto);
	} catch (Exception ex) {
		ex.printStackTrace();
		throw new Exception("SQL Exception : " + ex.getMessage(), ex);
	}
	return objList;
	
	}	
	
	public ArrayList<CancelledFailedLineReportDTO> viewCancelledFailedLineReport(CancelledFailedLineReportDTO objDto)
	throws Exception {
	
	ArrayList<CancelledFailedLineReportDTO> objList = new ArrayList<CancelledFailedLineReportDTO>();
	ReportsDao objDao = new ReportsDao();
	try {
		objList = objDao.viewCancelledFailedLineReport(objDto);
	} catch (Exception ex) {
		ex.printStackTrace();
		throw new Exception("SQL Exception : " + ex.getMessage(), ex);
	}
	return objList;
	
	}		
	
	public ArrayList<BulkSIUploadReportDto> viewBulkSIUploadReport(BulkSIUploadReportDto objDto)
	throws Exception {
	
	ArrayList<BulkSIUploadReportDto> objList = new ArrayList<BulkSIUploadReportDto>();
	ReportsDao objDao = new ReportsDao();
	try {
		objList = objDao.viewBulkSIUploadReport(objDto);
	} catch (Exception ex) {
		ex.printStackTrace();
		throw new Exception("SQL Exception : " + ex.getMessage(), ex);
	}
	return objList;
	
	}
	
	
	
	public ArrayList<AttributeDetailsReportDTO> viewAttributeDetailsReport(AttributeDetailsReportDTO objDto)
	throws Exception {
	
	ArrayList<AttributeDetailsReportDTO> objList = new ArrayList<AttributeDetailsReportDTO>();
	ReportsDao objDao = new ReportsDao();
	try {
		objList = objDao.viewAttributeDetailsReport(objDto);
	} catch (Exception ex) {
		ex.printStackTrace();
		throw new Exception("SQL Exception : " + ex.getMessage(), ex);
	}
	return objList;
	
	}
	
	public ArrayList<DisconnectChangeOrdeReportDTO> viewDisconnectionChangeOrderReport(DisconnectChangeOrdeReportDTO objDto)
	throws Exception {
	
	ArrayList<DisconnectChangeOrdeReportDTO> objList = new ArrayList<DisconnectChangeOrdeReportDTO>();
	ReportsDao objDao = new ReportsDao();
	try {
		
		objList = objDao.viewDisconnectionChangeOrderReport(objDto);
	} catch (Exception ex) {
		ex.printStackTrace();
		throw new Exception("SQL Exception : " + ex.getMessage(), ex);
	}
	return objList;
	
	}		
	
	public ArrayList<MigratedApprovedNewOrderDetailReportDTO> viewMigAppNewOrderDetails(MigratedApprovedNewOrderDetailReportDTO objDto) throws Exception
	{
		ArrayList<MigratedApprovedNewOrderDetailReportDTO> listSearchDetails= new ArrayList<MigratedApprovedNewOrderDetailReportDTO>();
		ReportsDao objDao = new ReportsDao();
		try
		{
			listSearchDetails = objDao.viewMigAppNewOrderDetails(objDto);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listSearchDetails;
	}
	/**
	 *  method to fetch data for LEPM Owner report.
	 * @param objDto
	 * @return
	 * @throws Exception
	 */
	public ArrayList<LempOwnerReportDTO> viewLEPMOwnerReport(LempOwnerReportDTO objDto) throws Exception
	{
		ArrayList<LempOwnerReportDTO> listLEPMOwnerReport= new ArrayList<LempOwnerReportDTO>();
		ReportsDao objDao = new ReportsDao();
		try
		{
			listLEPMOwnerReport = objDao.viewLEPMOwnerReport(objDto);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
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
	
	public ArrayList<LempCancelOrderReportDTO> viewLEPMOrderCancelReport(LempCancelOrderReportDTO objDto)
	throws Exception {
	
	ArrayList<LempCancelOrderReportDTO> objList = new ArrayList<LempCancelOrderReportDTO>();
	ReportsDao objDao = new ReportsDao();
	try {
		objList = objDao.viewLEPMOrderCancelReport(objDto);
	} catch (Exception ex) {
		ex.printStackTrace();
		throw new Exception("SQL Exception : " + ex.getMessage(), ex);
	}
	return objList;
}

//002 END

//003 START	
	
	 /**
     * Create a Report to generate LEPM Order Detail Report
     
     * @param obj   a DTO which consist all the search parameters
     * @return      a ArrayList of dto which consist all the data of reports 
     * @exception   Sql Exception
     *            
     */	
	public ArrayList<LempOrderDetailsReportDTO> viewLEPMOrderDetailReport(LempOrderDetailsReportDTO objDto)
	throws Exception {
	
	ArrayList<LempOrderDetailsReportDTO> objList = new ArrayList<LempOrderDetailsReportDTO>();
	ReportsDao objDao = new ReportsDao();
	try {
		objList = objDao.viewLEPMOrderDetailReport(objDto);
	} catch (Exception ex) {
		ex.printStackTrace();
		throw new Exception("SQL Exception : " + ex.getMessage(), ex);
	}
	return objList;
}
//003 END	
	//	004 START	
	
	 /**
   * Create a Report to generate Pending Order PD Order Report
   
   * @param obj   a DTO which consist all the search parameters
   * @return      a ArrayList of dto which consist all the data of reports 
   * @exception   Sql Exception
   *            
   */	
	public ArrayList<ReportsDto> viewPendingBillingPDOrderList(ReportsDto objDto)
	throws Exception {
	
	ArrayList<ReportsDto> objList = new ArrayList<ReportsDto>();
	ReportsDao objDao = new ReportsDao();
	try {
		objList = objDao.viewPendingBillingPDOrderList(objDto);
	} catch (Exception ex) {
		ex.printStackTrace();
		throw new Exception("SQL Exception : " + ex.getMessage(), ex);
	}
	return objList;
}
//004 END
	//[0011]  Start
	public ArrayList<LineItemDTO> viewLSIMapping(LineItemDTO objDto) throws Exception
	{
		ReportsDao objDao = new ReportsDao();
		ArrayList<LineItemDTO> saDto=new ArrayList<LineItemDTO>();
		try 
		{
			saDto = objDao.getLSIMappingDetails(objDto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return saDto;
	}
	//[0011]  Start
	public ArrayList<OrderClepReportDTO> viewClepOrderReport(OrderClepReportDTO objDto)
	throws Exception {
	
	ArrayList<OrderClepReportDTO> objList = new ArrayList<OrderClepReportDTO>();
	ReportsDao objDao = new ReportsDao();
	try {
		objList = objDao.viewClepOrderReport(objDto);
	} catch (Exception ex) {
		ex.printStackTrace();
		throw new Exception("SQL Exception : " + ex.getMessage(), ex);
	}
	return objList;
}
	//[101010]END
	/*[HYPR22032013001] -- start */	
	public ArrayList<BillingWorkQueueReportDTO> viewBillingWorkQueueList(BillingWorkQueueReportDTO objDto)
		throws Exception {
		
		ArrayList<BillingWorkQueueReportDTO> objList = new ArrayList<BillingWorkQueueReportDTO>();
		ReportsDao objDao = new ReportsDao();
		try {
			//objList = objDao.viewActiveLineItemsList(objDto);
			objList = objDao.viewBillingWorkQueueList(objDto);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("SQL Exception : " + ex.getMessage(), ex);
		}
		return objList;
	}
	/*[HYPR22032013001] -- end */
	
	/*Vijay. -start. new method of cancelcopy report */
	public ArrayList<CopyCancelReportDTO> cancelCopyReport(CopyCancelReportDTO objDto) throws Exception
	{
		ArrayList<CopyCancelReportDTO> listSearchDetails= new ArrayList<CopyCancelReportDTO>();
		ReportsDao objDao = new ReportsDao();
		try
		{
			listSearchDetails = objDao.cancelCopyReport(objDto);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listSearchDetails;
	}
	/*Vijay. end */
	
		//[0012] Start
	public ArrayList<HardwareLineItemCancelReportDTO> viewHardwareCancelReport(HardwareLineItemCancelReportDTO objDto) throws Exception 
	{
		 ArrayList<HardwareLineItemCancelReportDTO> objList = new ArrayList<HardwareLineItemCancelReportDTO>();
		ReportsDao objDao = new ReportsDao();
		try 
		{
			objList = objDao.viewHardwareCancelReport(objDto);
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
			throw new Exception("SQL Exception : " + ex.getMessage(), ex);
		}
		return objList;
	}
	//[0012] Start	
	
	public ArrayList<ProductCatelogDTO> getCancelledHardwareLineDetails(long orderNo) throws Exception
	{
		ArrayList<ProductCatelogDTO> lineList=null;
		NewOrderDaoExt objDao = new NewOrderDaoExt();
		try
		{
			lineList = objDao.getCancelledHardwareLineDetails(orderNo);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return lineList;
	}

	//[008] Start
	public ArrayList<AdvancePaymentReportDTO> viewAdvancePaymentReport(AdvancePaymentReportDTO objDto) throws Exception 
	{
		 ArrayList<AdvancePaymentReportDTO> objList = new ArrayList<AdvancePaymentReportDTO>();
		ReportsDao objDao = new ReportsDao();
		try 
		{
			objList = objDao.viewAdvancePaymentReport(objDto);
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
			throw new Exception("SQL Exception : " + ex.getMessage(), ex);
		}
		return objList;
	}
	//[008] End
	//[009] Start
	public ArrayList<AdvancePaymentReportDTO> fetchTBTLineItemData(AdvancePaymentReportDTO objDto) throws Exception 
	{
		 ArrayList<AdvancePaymentReportDTO> objList = new ArrayList<AdvancePaymentReportDTO>();
		ReportsDao objDao = new ReportsDao();
		try 
		{
			objList = objDao.fetchTBTLineItemData(objDto);
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
			throw new Exception("SQL Exception : " + ex.getMessage(), ex);
		}
		return objList;
	}
	//[009] End
	
//	[10] Start
	public ArrayList<ActiveLineDemoReportDTO> viewActiveLineDemoList(ActiveLineDemoReportDTO objDto)
	throws Exception {
	
	ArrayList<ActiveLineDemoReportDTO> objList = new ArrayList<ActiveLineDemoReportDTO>();
	ReportsDao objDao = new ReportsDao();
	try {
		AppConstants.IOES_LOGGER.info("ReportsModel:  executing viewActiveLineDemoList()");
		objList = objDao.viewActiveLineDemoReportDetails(objDto);
	} catch (Exception ex) {
		ex.printStackTrace();
		throw new Exception("SQL Exception : " + ex.getMessage(), ex);
	}
	return objList;
}
	//[10] End
	
	public ArrayList<OBValueReportDTO> viewOBValueReport(OBValueReportDTO objDto)
			throws Exception {


		ArrayList<OBValueReportDTO> objList = new ArrayList<OBValueReportDTO>();
		ReportsDao objDao = new ReportsDao();
		try {
			objList = objDao.viewOBValueReport(objDto);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("SQL Exception : " + ex.getMessage(), ex);
		}
		return objList;
	}

	

	/**
	 * Method::viewDummyLineDetailsReport
	 * @param DummyLinesDetailsReportDTO
	 * @return ArrayList
	 * @author Anil Kumar
	 * @throws Exception
	 */
	public ArrayList<DummyLinesDetailsReportDTO> viewDummyLineDetailsReport(DummyLinesDetailsReportDTO objDto) throws Exception
	{
		ArrayList<DummyLinesDetailsReportDTO> listSearchDetails= new ArrayList<DummyLinesDetailsReportDTO>();
		ReportsDao objDao = new ReportsDao();
		try
		{
			listSearchDetails = objDao.viewDummyLineDetailsReport(objDto);
		}
		catch(Exception ex)
		{
			Utility.LOG(true, false, "from method viewDummyLineDetailsReport in ReportsModel");
		}
		return listSearchDetails;
	}
	
//[0020] start
	/**
	 * Method::viewDocumentMatrixReport
	 * @param DocumentMatrixReportDTO
	 * @return ArrayList
	 * @author Gunjan Singla
	 * @throws Exception
	 */
	public ArrayList<DocumentMatrixReportDTO> viewDocumentMatrixReport(
			DocumentMatrixReportDTO objDto) throws Exception {
		
		ArrayList<DocumentMatrixReportDTO> objList = new ArrayList<DocumentMatrixReportDTO>();
		ReportsDao objDao = new ReportsDao();
		try {
			
			objList = objDao.viewDocumentMatrixReport(objDto);
			
		} catch (Exception ex) {
			Utility.LOG(true, false, "from method viewDocumentMatrixReport in ReportsModel");
		}
		return objList;
		
	}
//[0020] end
	
	
	
	public ArrayList<UserAccessMatrixDto> viewAccessMatrixReport(
			UserAccessMatrixDto objDto) throws Exception {
		
		ArrayList<UserAccessMatrixDto> objList = new ArrayList<UserAccessMatrixDto>();
		ReportsDao objDao = new ReportsDao();
		try {
			objList = objDao.viewAccessMatrixReport(objDto);
		} catch (Exception ex) {
			Utility.LOG(true, false, "from method viewAccessMatrixReport in ReportsModel");
		}
		return objList;
		
	}
	public ArrayList<ArchivalReportDto> reportDraftOrder(ArchivalReportDto reportsDto) throws Exception {
		
		ArrayList<ArchivalReportDto> objList = new ArrayList<ArchivalReportDto>();
		ReportsDao objDao = new ReportsDao();
		try {
			objList = objDao.reportDraftOrder(reportsDto);
		} catch (Exception ex) {
			Utility.LOG(true, false, "from method reportDraftOrder in ReportsModel");
		}
		return objList;
		
	}
	
public ArrayList<ArchivalReportDto> cancelledReportOrder(ArchivalReportDto reportsDto) throws Exception {
		
		ArrayList<ArchivalReportDto> objList = new ArrayList<ArchivalReportDto>();
		ReportsDao objDao = new ReportsDao();
		try {
			objList = objDao.cancelledReportOrder(reportsDto);
		} catch (Exception ex) {
			Utility.LOG(true, false, "from method cancelledReportOrder in ReportsModel");
		}
		return objList;
		
	}	
public ArrayList<ArchivalReportDto> pdReportOrder(ArchivalReportDto reportsDto) throws Exception {
	
	ArrayList<ArchivalReportDto> objList = new ArrayList<ArchivalReportDto>();
	ReportsDao objDao = new ReportsDao();
	try {
		objList = objDao.pdReportOrder(reportsDto);
	} catch (Exception ex) {
		Utility.LOG(true, false, "from method pdReportOrder in ReportsModel");
	}
	return objList;
	
}

//priya
public ArrayList<ParallelUpgradeReportDto> viewParallelUpgradeReport(ParallelUpgradeReportDto objDto,String inExcel,HttpServletResponse response) throws Exception
{
	ArrayList<ParallelUpgradeReportDto> listSearchDetails= new ArrayList<ParallelUpgradeReportDto>();
	ReportsDao objDao = new ReportsDao();
	try
	{
		listSearchDetails = objDao.viewParallelUpgradeReport(objDto,inExcel,response);
	}
	catch(Exception ex)
	{
		ex.printStackTrace();	
	}
	return listSearchDetails;
	
}



}
