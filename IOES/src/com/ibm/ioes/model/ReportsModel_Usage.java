//TagName Resource Name     Date		CSR No			Description 
//[001]	   LawKush	 10-Feb-11	     CSR00-05422     for orders pending in billing and hardware
package com.ibm.ioes.model;

import java.util.ArrayList;


import com.ibm.ioes.dao.ReportsDao_Usage;
import com.ibm.ioes.forms.BCPAddressDto;
import com.ibm.ioes.forms.ChangeOrderTypeDto;
import com.ibm.ioes.forms.DispatchAddressDto;
import com.ibm.ioes.forms.M6OrderStatusDto;
import com.ibm.ioes.forms.NetworkLocationDto;
import com.ibm.ioes.forms.LocationDetailDto;
import com.ibm.ioes.forms.NewOrderDto;
import com.ibm.ioes.forms.ReportsDto;
import com.ibm.ioes.forms.MastersAttributesDto;
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

public class ReportsModel_Usage {

	public ArrayList<BCPAddressDto> viewBCPList(BCPAddressDto objDto)
			throws Exception {
		ArrayList<BCPAddressDto> objList = new ArrayList<BCPAddressDto>();
		ReportsDao_Usage objDao = new ReportsDao_Usage();
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
	ReportsDao_Usage objDao = new ReportsDao_Usage();
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
	ReportsDao_Usage objDao = new ReportsDao_Usage();
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
		ReportsDao_Usage objDao = new ReportsDao_Usage();
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
		ReportsDao_Usage objDao = new ReportsDao_Usage();
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
		ReportsDao_Usage objDao = new ReportsDao_Usage();
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
	ReportsDao_Usage objDao = new ReportsDao_Usage();
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
		ReportsDao_Usage objDao = new ReportsDao_Usage();
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
		ReportsDao_Usage objDao = new ReportsDao_Usage();
		try {
			objList = objDao.viewMasterHistory(objDto);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("SQL Exception : " + ex.getMessage(), ex);
		}
		return objList;
	}
	public ArrayList<ReportsDto> viewOrderStageList(ReportsDto objDto)
	throws Exception {
	
	ArrayList<ReportsDto> objList = new ArrayList<ReportsDto>();
	ReportsDao_Usage objDao = new ReportsDao_Usage();
	try {
		objList = objDao.viewOrderStageList(objDto);
	} catch (Exception ex) {
		ex.printStackTrace();
		throw new Exception("SQL Exception : " + ex.getMessage(), ex);
	}
	return objList;
}
//	Ramana
	public ArrayList<ReportsDto> viewPerformanceList(ReportsDto objDto) throws Exception
	{
		ArrayList<ReportsDto> listSearchDetails= new ArrayList<ReportsDto>();
		ReportsDao_Usage objDao = new ReportsDao_Usage();
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
		ReportsDao_Usage objDao = new ReportsDao_Usage();
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
		ReportsDao_Usage objDao = new ReportsDao_Usage();
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
	
	public ArrayList<NonAPP_APPChangeOrderDetailsDTO> viewNonMigAppUnappNewOrderDetails(NonAPP_APPChangeOrderDetailsDTO objDto) throws Exception
	{
		ArrayList<NonAPP_APPChangeOrderDetailsDTO> listSearchDetails= new ArrayList<NonAPP_APPChangeOrderDetailsDTO>();
		ReportsDao_Usage objDao = new ReportsDao_Usage();
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
		ReportsDao_Usage objDao = new ReportsDao_Usage();
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
	public ArrayList<ReportsDto> viewPerformanceSummaryReport(ReportsDto objDto) throws Exception
	{
		ArrayList<ReportsDto> listSearchDetails= new ArrayList<ReportsDto>();
		ReportsDao_Usage objDao = new ReportsDao_Usage();
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
	public ArrayList<ReportsDto> viewPendingServicesReport(ReportsDto objDto) throws Exception
	{
		ArrayList<ReportsDto> listSearchDetails= new ArrayList<ReportsDto>();
		ReportsDao_Usage objDao = new ReportsDao_Usage();
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
	ReportsDao_Usage objDao = new ReportsDao_Usage();
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
		ReportsDao_Usage objDao = new ReportsDao_Usage();
		try {
			objList = objDao.viewOrderReportNew(objDto);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("SQL Exception : " + ex.getMessage(), ex);
		}
		return objList;
	}

	
	public ArrayList<NewOrderDto> viewOrderReportChange(NewOrderDto objDto)
	throws Exception {

		ArrayList<NewOrderDto> objList = new ArrayList<NewOrderDto>();
		ReportsDao_Usage objDao = new ReportsDao_Usage();
		try {
			objList = objDao.viewOrderReportChange(objDto);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("SQL Exception : " + ex.getMessage(), ex);
		}
		return objList;
}
	
	
	public ArrayList<ReportsDto> viewOrderReportDetails(ReportsDto objDto)
	throws Exception {

		ArrayList<ReportsDto> objList = new ArrayList<ReportsDto>();
		ReportsDao_Usage objDao = new ReportsDao_Usage();
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
	ReportsDao_Usage objDao = new ReportsDao_Usage();
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
	public ArrayList<ReportsDto> pendingOrderBill(ReportsDto objDto)
	throws Exception {
	
	ArrayList<ReportsDto> objList = new ArrayList<ReportsDto>();
	ReportsDao_Usage objDao = new ReportsDao_Usage();
	try {
		objList = objDao.ViewpendingOrderBillandHardwareList(objDto);
	} catch (Exception ex) {
		ex.printStackTrace();
		throw new Exception("SQL Exception : " + ex.getMessage(), ex);
	}
	return objList;
}
	
public ArrayList<ReportsDto> viewM6OrderCancelReport(ReportsDto objDto)
	throws Exception {
	
	ArrayList<ReportsDto> objList = new ArrayList<ReportsDto>();
	ReportsDao_Usage objDao = new ReportsDao_Usage();
	try {
		objList = objDao.viewM6OrderCancelReport(objDto);
	} catch (Exception ex) {
		ex.printStackTrace();
		throw new Exception("SQL Exception : " + ex.getMessage(), ex);
	}
	return objList;
}
	
	
//	[001]	END
	
	public ArrayList<ReportsDto> viewPerformanceDetailList(ReportsDto objDto) throws Exception
	{
		ArrayList<ReportsDto> listSearchDetails= new ArrayList<ReportsDto>();
		ReportsDao_Usage objDao = new ReportsDao_Usage();
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
	public ArrayList<ReportsDto> getTelemediaOrderList(ReportsDto objDto) throws Exception
	{
		ArrayList<ReportsDto> listSearchDetails= new ArrayList<ReportsDto>();
		ReportsDao_Usage objDao = new ReportsDao_Usage();
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
	public ArrayList<ReportsDto> viewZeroOrderValueReportDetails(ReportsDto objDto) throws Exception
	{
		ArrayList<ReportsDto> listSearchDetails= new ArrayList<ReportsDto>();
		ReportsDao_Usage objDao = new ReportsDao_Usage();
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
		ReportsDao_Usage objDao = new ReportsDao_Usage();
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
	ReportsDao_Usage objDao = new ReportsDao_Usage();
	try {
		objList = objDao.viewRestPendingLineReport(objDto);
	} catch (Exception ex) {
		ex.printStackTrace();
		throw new Exception("SQL Exception : " + ex.getMessage(), ex);
	}
	return objList;
	
	}	
	public ArrayList<DisconnectLineReportDTO> viewDisconnectionLineReport(DisconnectLineReportDTO objDto)
	throws Exception {
	
	ArrayList<DisconnectLineReportDTO> objList = new ArrayList<DisconnectLineReportDTO>();
	ReportsDao_Usage objDao = new ReportsDao_Usage();
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
	ReportsDao_Usage objDao = new ReportsDao_Usage();
	try {
		objList = objDao.viewCancelledFailedLineReport(objDto);
	} catch (Exception ex) {
		ex.printStackTrace();
		throw new Exception("SQL Exception : " + ex.getMessage(), ex);
	}
	return objList;
	
	}		
	
	public ArrayList<ReportsDto> viewBulkSIUploadReport(ReportsDto objDto)
	throws Exception {
	
	ArrayList<ReportsDto> objList = new ArrayList<ReportsDto>();
	ReportsDao_Usage objDao = new ReportsDao_Usage();
	try {
		objList = objDao.viewBulkSIUploadReport(objDto);
	} catch (Exception ex) {
		ex.printStackTrace();
		throw new Exception("SQL Exception : " + ex.getMessage(), ex);
	}
	return objList;
	
	}
	
	
	
	public ArrayList<ReportsDto> viewAttributeDetailsReport(ReportsDto objDto)
	throws Exception {
	
	ArrayList<ReportsDto> objList = new ArrayList<ReportsDto>();
	ReportsDao_Usage objDao = new ReportsDao_Usage();
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
	ReportsDao_Usage objDao = new ReportsDao_Usage();
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
		ReportsDao_Usage objDao = new ReportsDao_Usage();
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
	public ArrayList<ReportsDto> viewLEPMOwnerReport(ReportsDto objDto) throws Exception
	{
		ArrayList<ReportsDto> listLEPMOwnerReport= new ArrayList<ReportsDto>();
		ReportsDao_Usage objDao = new ReportsDao_Usage();
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
	
	public ArrayList<ReportsDto> viewLEPMOrderCancelReport(ReportsDto objDto)
	throws Exception {
	
	ArrayList<ReportsDto> objList = new ArrayList<ReportsDto>();
	ReportsDao_Usage objDao = new ReportsDao_Usage();
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
	public ArrayList<ReportsDto> viewLEPMOrderDetailReport(ReportsDto objDto)
	throws Exception {
	
	ArrayList<ReportsDto> objList = new ArrayList<ReportsDto>();
	ReportsDao_Usage objDao = new ReportsDao_Usage();
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
	ReportsDao_Usage objDao = new ReportsDao_Usage();
	try {
		objList = objDao.viewPendingBillingPDOrderList(objDto);
	} catch (Exception ex) {
		ex.printStackTrace();
		throw new Exception("SQL Exception : " + ex.getMessage(), ex);
	}
	return objList;
}
//004 END	
	public ArrayList<CustomerBaseReportsDTO> viewCustomerBaseReport(CustomerBaseReportsDTO objDto)
	throws Exception {
	
	ArrayList<CustomerBaseReportsDTO> objList = new ArrayList<CustomerBaseReportsDTO>();
	ReportsDao_Usage objDao = new ReportsDao_Usage();
	try {
		objList = objDao.viewCustomerBaseReport(objDto);
	} catch (Exception ex) {
		ex.printStackTrace();
		throw new Exception("SQL Exception : " + ex.getMessage(), ex);
	}
	return objList;
	
	}	

	public ArrayList<BillingWorkQueueReportDTO> viewBillingWorkQueueList(BillingWorkQueueReportDTO objDto)
	throws Exception {

		ArrayList<BillingWorkQueueReportDTO> objList = new ArrayList<BillingWorkQueueReportDTO>();
		ReportsDao_Usage objDao = new ReportsDao_Usage();
		try {
			//objList = objDao.viewActiveLineItemsList(objDto);
			objList = objDao.viewBillingWorkQueueList(objDto);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("SQL Exception : " + ex.getMessage(), ex);
		}
		return objList;
	}
	
	public ArrayList<OBValueReportDTO> viewOBValueReport_Usage(OBValueReportDTO objDto)
			throws Exception {


		ArrayList<OBValueReportDTO> objList = new ArrayList<OBValueReportDTO>();
		ReportsDao_Usage objDao = new ReportsDao_Usage();
		try {
			objList = objDao.viewOBValueReport_Usage(objDto);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("SQL Exception : " + ex.getMessage(), ex);
		}
		return objList;
	}

}
