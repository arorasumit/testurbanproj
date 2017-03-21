package com.ibm.ioes.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import com.ibm.ioes.dao.CopyOrderDao;
import com.ibm.ioes.dao.UniversalWorkQueueDao;
import com.ibm.ioes.newdesign.dto.AdvancePaymentDTO;
import com.ibm.ioes.newdesign.dto.BillingDetailsDto;
import com.ibm.ioes.newdesign.dto.ChargeDetailsDto;
import com.ibm.ioes.newdesign.dto.ComponentDTO;
import com.ibm.ioes.newdesign.dto.CopyOrderLineDataDTO;
import com.ibm.ioes.newdesign.dto.HardwareDetailsDto;
import com.ibm.ioes.newdesign.dto.LineItemDto;
import com.ibm.ioes.newdesign.dto.LocationDetailsDto;
import com.ibm.ioes.newdesign.dto.OrderDto;
import com.ibm.ioes.newdesign.dto.SCMDetailsDto;
import com.ibm.ioes.newdesign.dto.ServiceDetailsConfigAttValuesDTO;
import com.ibm.ioes.newdesign.dto.ServiceDto;
import com.ibm.ioes.newdesign.dto.ServiceProductAttributeDto;
import com.ibm.ioes.newdesign.dto.ServiceSummarryAttributesDto;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Utility;


class ValidateHardwareLine{
	/*
	 * contain a message  
	 */
	String message; 
	
	/*
	 * contain status that would be partial or full or do not copy
	 */
	int status; 
	
	/*
	 * contain the service list that should be copy
	 */
	ArrayList<Long> serviceList;
	
	/*
	 * contain the list of service count for copy 
	 */
	ArrayList<Integer> noOfCopy;  
}



/**
 * Copy Order functionality
 * @author IBM_ADMIN
 *@since June 2014
 */
public class CopyOrderModel {

			public static enum RESERVED {
				SERVICEID, SERVICEPRODUCTID, LSI		
			}

	/**
	 * @author Vijay		
	 * @param conn 
	 * @param targetOrderNo
	 * @param srcServiceIds
	 * @param nos
	 * @return A object of ValidateHardwareLine. This object contain the status of validation, message and  filter list of 
	 * service Ids and its copy count
	 * @throws Exception
	 */
	public static ValidateHardwareLine validateAndFilterHardwareService(Connection conn, Long targetOrderNo, List<Long>srcServiceIds, List<Integer> nos) throws Exception{

		ValidateHardwareLine validateHardwareLineObj = new ValidateHardwareLine();

		ArrayList<Long> srcServiceIdsList = new ArrayList<Long>(srcServiceIds);
		ArrayList<Integer> noOfCopyList = new ArrayList<Integer>(nos);
		//conn=DbConnection.getConnectionObject();
		OrderDto orderObj = CopyOrderDao.loadOrderLevelData(conn, targetOrderNo);
		/*If dispatch address is not available then need to check the hardware line item for each service and if hardware line item  exists then
		 * need to be skip that service for copy */
		
		if(orderObj.getDispatchAddCode() == null ||  orderObj.getDispatchAddCode() == 0){
			/*creating a MAP where key is service Id and its value is no of copy */
			Long[] arrayServiceIds=new Long[srcServiceIds.size()];
			arrayServiceIds=srcServiceIds.toArray(arrayServiceIds);
			String csvOrigServices= Utility.getCsv(arrayServiceIds); //implemented
			
			HashMap<Long,Integer> hmap_serviceId_countHardwareLines = CopyOrderDao.loadHardwareLinecount(conn, csvOrigServices);
			
			/*If Map size is greater than 0 that means it contain service ids which should not 
			 * be copy and must be deleted in the service List that is going to copy
			 */ 
			if(hmap_serviceId_countHardwareLines.size() > 0 ){
				Set<Long> serviceIdSet =  hmap_serviceId_countHardwareLines.keySet();
				for (Long serviceId : serviceIdSet) {
					
					//finding the index of service that should not be copy
					int indexOfService = srcServiceIdsList.indexOf(serviceId);
					
					//now delete that service and its no of copy in array list
					srcServiceIdsList.remove(indexOfService);
					noOfCopyList.remove(indexOfService);
				}
			}
			/*
			 * Setting the ValidateHardwareLine object that contain the status of validation, service list, no of copy list and message 
			 */
			if(hmap_serviceId_countHardwareLines.size() == 0){
				//No hardware line available for any service
				validateHardwareLineObj.status = AppConstants.FULL_COPY;
				//validateHardwareLineObj.message = "Success";
				//validateHardwareLineObj.serviceList = srcServiceIdsList;
				//validateHardwareLineObj.noOfCopy = noOfCopyList;
			}
			else if(hmap_serviceId_countHardwareLines.size() == srcServiceIds.size() ){
				//means  all services have hardware lines
				validateHardwareLineObj.status = AppConstants.DO_NO_COPY;
				//validateHardwareLineObj.message = "Dispatch Address is Not Available to this account..!";
				//validateHardwareLineObj.serviceList = srcServiceIdsList;
				//validateHardwareLineObj.noOfCopy = noOfCopyList;
			}
			else if(hmap_serviceId_countHardwareLines.size() < srcServiceIds.size()){
				//some service have hardware lines
				validateHardwareLineObj.status = AppConstants.PARTIAL_COPY;
				//validateHardwareLineObj.message = "Some Services has been copied , rest of the services doesn't have Dispatchaddress for Hardwarelineitem";
				validateHardwareLineObj.serviceList = srcServiceIdsList;
				validateHardwareLineObj.noOfCopy = noOfCopyList;
			}
		}else{
			validateHardwareLineObj.status=AppConstants.FULL_COPY;
		}

		return validateHardwareLineObj;
	}
			
	/**
	 * Outsourced to Lord Kartikeyan
	 * @param targetOrderNo
	 * @param srcServiceIds
	 * @param nos
	 * @param hashMapGUIDetails - Passed only for Copy Order , This is null for "Create Order With Existing Order" 
	 * @return
	 * @throws Exception
	 */
	boolean copyServices(Connection conn,String module,Long targetOrderNo,List<Long> srcServiceIds , List<Integer> nos,Long roleId, HashMap<Long, CopyOrderLineDataDTO> hashMapGUIDetails,String employeeId,HashMap<Object, List<Long>> map_ReserverIds) throws Exception{
		boolean status=false;
		String msg=getClass().getName()+"::Method:copyServices() ";
		
		Long[] s=new Long[srcServiceIds.size()];
		s=srcServiceIds.toArray(s);
		String csvOrigServices= Utility.getCsv(s); //implemented
		Long intitate_to=null;
		//get count of service to be created
		//get count of lines to be created
		//getReserved ServiceIds
		//get Reserved SPIds
		
		
		//create csvofServices
		//loadServiceData
		//loadServiceAttributeData
		//load Line , Line Attributes,Billing,Location,HW,Charges Data
		//organise in Service Enclosure
		
		List<ServiceDto> services = CopyOrderDao.loadServicesData(conn,csvOrigServices);
		List<ServiceProductAttributeDto> serviceProductAttributes = CopyOrderDao.loadServiceProductAttributeData(conn,csvOrigServices);
		List<LineItemDto> lineItems = CopyOrderDao.loadLineItems(conn,csvOrigServices);
		List<ServiceSummarryAttributesDto> serviceSummarryAttributes = CopyOrderDao.loadServiceSummarryAttributes(conn,csvOrigServices);
		List<BillingDetailsDto> billingDetails = CopyOrderDao.loadBillingDetails(conn,csvOrigServices);
		List<LocationDetailsDto> locationDetails = CopyOrderDao.loadLocationDetails(conn,csvOrigServices);
		
		List<HardwareDetailsDto> hardwareDetails = CopyOrderDao.loadHardwareDetails(conn,csvOrigServices);
		List<ChargeDetailsDto> chargeDetails = CopyOrderDao.loadChargeDetails(conn,csvOrigServices);
		List<ServiceDetailsConfigAttValuesDTO> serviceDetailsConfigAttValues = CopyOrderDao.loadServiceDetailsConfigAttValues(conn,csvOrigServices);
		List<ComponentDTO> componentDetails = CopyOrderDao.loadComponentDetails(conn, csvOrigServices); //implemented
		List<AdvancePaymentDTO> advancePaymentDetails = CopyOrderDao.loadAdvancePaymentDetails(conn, csvOrigServices); //implemented
		List<SCMDetailsDto> scmDetails = CopyOrderDao.loadSCMDetails(conn, csvOrigServices); 		//implemented
		OrderDto orderObj=CopyOrderDao.loadOrderLevelData(conn,targetOrderNo); //implemented
		if(orderObj.getTotalCountInWorkflowTask() == 0){
			intitate_to=0L;//if service copy from draft then role will be 0 which treated as Draft
		}else{
			intitate_to=roleId;
		}
		Set<Long> setThirdPartyServiceDetailId = CopyOrderDao.loadThirdPartyServiceDetailId(conn);
		
		HashMap<Long, Long> oldPoWithNewPoMap = null ;
		if(AppConstants.CREATE_ORDER_WITH_EXISTING_FEATURE.equalsIgnoreCase(module)){
			//hashMapGUIDetails = CopyOrderDao.fillHashMapGUIDetails(conn, csvOrigServices);
			oldPoWithNewPoMap = CopyOrderDao.getOldPoAndNewPoMapping(conn, targetOrderNo);
		}
		

		organiseAsServiceEncapsulation(services,serviceProductAttributes,lineItems,
				serviceSummarryAttributes,billingDetails,locationDetails,hardwareDetails,chargeDetails,serviceDetailsConfigAttValues,componentDetails,advancePaymentDetails, scmDetails);
				
		
		Stack<Long> reservedServiceIds = new Stack<Long>();
		reservedServiceIds.addAll(map_ReserverIds.get(RESERVED.SERVICEID));
		Stack<Long> reservedServiceProductIds=new Stack<Long>();
		reservedServiceProductIds.addAll( map_ReserverIds.get(RESERVED.SERVICEPRODUCTID));
		Stack<Long> reservedLsi = new Stack<Long>(); 
		reservedLsi.addAll(map_ReserverIds.get(RESERVED.LSI));
		
		
		HashMap<Long,Integer> map_ServiceId_Count = makeMapServiceId_Count(srcServiceIds,nos);  //implemented
		
		CopyOrderDao.generateServicesDataAndLoadInDB(conn,module,services,map_ServiceId_Count,reservedServiceIds,reservedLsi,reservedServiceProductIds,setThirdPartyServiceDetailId,targetOrderNo,roleId,orderObj,hashMapGUIDetails,employeeId,intitate_to,oldPoWithNewPoMap);
		
		status=true;
		
		return status;
	}

/**
	 * @author Vijay
	 * @param srcServiceIds
	 * @param nos
	 * @return A map object contain the service Id and its no of copy count
	 */
	private HashMap<Long, Integer> makeMapServiceId_Count(List<Long> srcServiceIds, List<Integer> nos) {
		HashMap<Long, Integer> mapServiceIds = new HashMap<Long, Integer>();
		/*get the total count for copy for all selected services */	
	    for(int i = 0; i < srcServiceIds.size(); i++)
	    {
	    	mapServiceIds.put(srcServiceIds.get(i), nos.get(i)) ; //set the service id and its no of copy into Map 
	    }
		return mapServiceIds;
	}


/**
 * 
 * @param services
 * @param serviceProductAttributes
 * @param lineItems
 * @param serviceSummarryAttributes
 * @param billingDetails
 * @param locationDetails
 * @param hardwareDetails
 * @param chargeDetails
 * @param serviceDetailsConfigAttValues
 * @param componentDetails
 * @param advancePaymentDetails
 */
	private void organiseAsServiceEncapsulation(List<ServiceDto> services,
			List<ServiceProductAttributeDto> serviceProductAttributes,
			List<LineItemDto> lineItems,
			List<ServiceSummarryAttributesDto> serviceSummarryAttributes,
			List<BillingDetailsDto> billingDetails,
			List<LocationDetailsDto> locationDetails,
			List<HardwareDetailsDto> hardwareDetails,
			List<ChargeDetailsDto> chargeDetails,
			List<ServiceDetailsConfigAttValuesDTO> serviceDetailsConfigAttValues, 
			List<ComponentDTO> componentDetails, 
			List<AdvancePaymentDTO> advancePaymentDetails,
			List<SCMDetailsDto> scmDetails)  {
		

		//Keep all service attributes details in map, key as service id and value as bean object of service attributes.
		HashMap<Long,ArrayList<ServiceProductAttributeDto>> map_serviceid_attributes=new HashMap<Long, ArrayList<ServiceProductAttributeDto>>();
		if(serviceProductAttributes !=null){
			for (ServiceProductAttributeDto serviceAttribute : serviceProductAttributes) {
				Long tempKey=serviceAttribute.getServiceDetailId();//here servicedetailid is serviceid
				if(!map_serviceid_attributes.containsKey(tempKey)){
					ArrayList<ServiceProductAttributeDto> serviceatt=new ArrayList<ServiceProductAttributeDto>();
					serviceatt.add(serviceAttribute);
					map_serviceid_attributes.put(tempKey, serviceatt);
				}else{
					map_serviceid_attributes.get(tempKey).add(serviceAttribute);
				}
			} 
		}		
		
		//Keep all lines details in map, key as service id and value as bean object of lines.
		HashMap<Long,ArrayList<LineItemDto>> map_serviceid_lines=new HashMap<Long, ArrayList<LineItemDto>>();
		for (LineItemDto line : lineItems) {
			Long tempKey=line.getServiceId();
			if(!map_serviceid_lines.containsKey(tempKey)){
				ArrayList<LineItemDto> serviceLines=new ArrayList<LineItemDto>();
				serviceLines.add(line);
				map_serviceid_lines.put(tempKey, serviceLines);
			}else{
				map_serviceid_lines.get(tempKey).add(line);
			}
		} 
		
		//Keep all lines attributes details in map, key as line item and value as bean object of lines attributes.
		HashMap<Long,ArrayList<ServiceSummarryAttributesDto>> map_lineid_attributes=new HashMap<Long, ArrayList<ServiceSummarryAttributesDto>>();
		if(serviceSummarryAttributes!=null){
			for (ServiceSummarryAttributesDto lineAttr : serviceSummarryAttributes) {
				Long tempKey=lineAttr.getServiceProductId();
				if(!map_lineid_attributes.containsKey(tempKey)){
					ArrayList<ServiceSummarryAttributesDto> lineAttributes=new ArrayList<ServiceSummarryAttributesDto>();
					lineAttributes.add(lineAttr);
					map_lineid_attributes.put(tempKey, lineAttributes);
				}else{
					map_lineid_attributes.get(tempKey).add(lineAttr);
				}
			} 
		}
		
		//Keep all lines billing info details in map, key as line item and value as bean object of lines billing info.
		HashMap<Long,BillingDetailsDto> map_lineid_billinginfo=new HashMap<Long, BillingDetailsDto>();
		if(billingDetails!=null){
			for (BillingDetailsDto billingInfo : billingDetails) {
				Long tempKey=billingInfo.getServiceProductId();
				if(!map_lineid_billinginfo.containsKey(tempKey)){
					map_lineid_billinginfo.put(tempKey, billingInfo);
				}
			} 
		}
		
		//Keep all lines location info details in map, key as line item and value as bean object of lines location info.
		HashMap<Long,LocationDetailsDto> map_lineid_locationinfo=new HashMap<Long, LocationDetailsDto>();
		if(locationDetails!=null){
			for (LocationDetailsDto locationInfo : locationDetails) {
				Long tempKey=locationInfo.getServiceProductId();
				if(!map_lineid_locationinfo.containsKey(tempKey)){
					map_lineid_locationinfo.put(tempKey, locationInfo);
				}
			} 		
		}
		
		//Keep all lines hardware info details in map, key as line item and value as bean object of lines hardware info.
		HashMap<Long,HardwareDetailsDto> map_lineid_hardwareinfo=new HashMap<Long, HardwareDetailsDto>();
		if(hardwareDetails!=null){
			for (HardwareDetailsDto hardwareInfo : hardwareDetails) {
				Long tempKey=hardwareInfo.getServiceProductId();
				if(!map_lineid_hardwareinfo.containsKey(tempKey)){
					map_lineid_hardwareinfo.put(tempKey, hardwareInfo);
				}
			} 
		}
		
		//Keep all lines charges details in map, key as line item and value as bean object of lines charges info
		HashMap<Long,ArrayList<ChargeDetailsDto>> map_lineid_charges=new HashMap<Long, ArrayList<ChargeDetailsDto>>();
		if(chargeDetails!=null){
			for (ChargeDetailsDto charge : chargeDetails) {
				Long tempKey=charge.getServiceProductId();
				if(!map_lineid_charges.containsKey(tempKey)){
					ArrayList<ChargeDetailsDto> charges=new ArrayList<ChargeDetailsDto>();
					charges.add(charge);
					map_lineid_charges.put(tempKey, charges);
				}else{
					map_lineid_charges.get(tempKey).add(charge);
				}
			} 
		}
		
		//Keep all lines components details in map, key as line item and value as bean object of lines components info.
		HashMap<Long,ArrayList<ComponentDTO>> map_lineid_component=new HashMap<Long, ArrayList<ComponentDTO>>();
		if(componentDetails!=null){
			for (ComponentDTO component : componentDetails) {
				Long tempKey=component.getServiceProductId();
				if(!map_lineid_component.containsKey(tempKey)){
					ArrayList<ComponentDTO> comp=new ArrayList<ComponentDTO>();
					comp.add(component);
					map_lineid_component.put(tempKey, comp);
				}else{
					map_lineid_component.get(tempKey).add(component);
				}
			} 
		}
		
		//Keep all lines config attribute details in map, key as line item and value as bean object of lines Config Attributes.
		HashMap<Long, ArrayList<ServiceDetailsConfigAttValuesDTO>> map_lineid_configAtt=new HashMap<Long, ArrayList<ServiceDetailsConfigAttValuesDTO>>();
		if(serviceDetailsConfigAttValues!=null){
			for (ServiceDetailsConfigAttValuesDTO lineConfigAtt : serviceDetailsConfigAttValues) {
				Long tempKey=lineConfigAtt.getServiceProductId();
				if(!map_lineid_configAtt.containsKey(tempKey)){
					ArrayList<ServiceDetailsConfigAttValuesDTO> lineconf=new ArrayList<ServiceDetailsConfigAttValuesDTO>();
					lineconf.add(lineConfigAtt);
					map_lineid_configAtt.put(tempKey, lineconf);
				}else{
					map_lineid_configAtt.get(tempKey).add(lineConfigAtt);
				}
			} 
		}
		
		//Keep all lines advance payment details in map, key as line item and value as bean object of lines advance payment.
		HashMap<Integer,AdvancePaymentDTO> map_lineid_advPayment=new HashMap<Integer, AdvancePaymentDTO>();
		if(advancePaymentDetails!=null){
			for (AdvancePaymentDTO advancePayment : advancePaymentDetails) {
				Integer tempKey=advancePayment.getSpId();
				if(!map_lineid_advPayment.containsKey(tempKey)){
					map_lineid_advPayment.put(tempKey, advancePayment);
				}
			} 
		}
		
		//Keep all lines SCM details in map, key as line item and value as bean object of list of  SCM details
		HashMap<Long,ArrayList<SCMDetailsDto>> map_lineid_scm=new HashMap<Long, ArrayList<SCMDetailsDto>>();
		if(scmDetails!=null){
			for (SCMDetailsDto scm : scmDetails) {
				Long tempKey=scm.getServiceProductId();
				if(!map_lineid_scm.containsKey(tempKey)){
					ArrayList<SCMDetailsDto> scmlist=new ArrayList<SCMDetailsDto>();
					scmlist.add(scm);
					map_lineid_scm.put(tempKey, scmlist);
				}else{
					map_lineid_scm.get(tempKey).add(scm);
				}
			} 
		}
				
		//Iterate each Service
		for(ServiceDto service:services){
		
			//Fetch and Set service Attribute list for each service
			ArrayList<ServiceProductAttributeDto> serviceAttributes=map_serviceid_attributes.get(service.getServiceId());
			service.setLsServiceProdAtt(serviceAttributes);
		
			//Fetch LinesItem list for each service
			ArrayList<LineItemDto> serviceLines=map_serviceid_lines.get(service.getServiceId());			
			service.setLsLineItemDetails(serviceLines);
			
			if(serviceLines!=null){
				//Iterate each Line 
				for(LineItemDto line:serviceLines){
															
					//Fetch and Set LineAttribute list for each line
					ArrayList<ServiceSummarryAttributesDto> lineAttributes=map_lineid_attributes.get(line.getServiceProductId());
					line.setLstServiceSummarryDetails(lineAttributes);		
					
					//Fetch and Set Billing info list for each line
					BillingDetailsDto lineBillinginfo=map_lineid_billinginfo.get(line.getServiceProductId());
					line.setBillingDetails(lineBillinginfo);
					
					//Fetch and Set Location info list for each line
					LocationDetailsDto lineLocationInfo=map_lineid_locationinfo.get(line.getServiceProductId());
					line.setLocationDetails(lineLocationInfo);
					
					//Fetch and Set Hardware info list for each line
					HardwareDetailsDto lineHardwareInfo=map_lineid_hardwareinfo.get(line.getServiceProductId());
					line.setHardwareDetails(lineHardwareInfo);
					
					//Fetch and Set Charge info list for each line
					ArrayList<ChargeDetailsDto> lineChargeinfo=map_lineid_charges.get(line.getServiceProductId());
					line.setLstChargeDetails(lineChargeinfo);
					
					//Fetch and Set Component info list for each line
					ArrayList<ComponentDTO> lineComponentInfo=map_lineid_component.get(line.getServiceProductId());
					line.setLstComponentDetails(lineComponentInfo);
					
					//Fetch and Set LineConfig attribute list for each line
					ArrayList<ServiceDetailsConfigAttValuesDTO> lineConfigAttInfo=map_lineid_configAtt.get(line.getServiceProductId());
					line.setLineConfigAttDetails(lineConfigAttInfo);
					
					//Fetch and Set Advance Payment list for each line
					AdvancePaymentDTO lineAdvPaymentInfo=map_lineid_advPayment.get(line.getServiceProductId().intValue());
					line.setAdvancePaymentDetails(lineAdvPaymentInfo);
					
					//Fetch and Set SCM info list for each line
					ArrayList<SCMDetailsDto> lineScmInfo=map_lineid_scm.get(line.getServiceProductId());
					line.setLstScmDetails(lineScmInfo);
					
				}				
			}
		}
	}
	
	
/**
 * @author Vijay
 * @param srcServiceIds
 * @return A string contain all service Ids separated by comma  
 */
	private String getCsv(ArrayList<Long> srcServiceIds) {
		
		StringBuffer strngBuffserviceIds = new StringBuffer();
		for (Long objServiceId : srcServiceIds) {
			/*add comma for service Id separation if string contain the values*/
			if(strngBuffserviceIds.length() >0)
				strngBuffserviceIds.append(",");
			
			strngBuffserviceIds.append(objServiceId);
		}
		return strngBuffserviceIds.toString();
	}

/**
 * 
 * @param orderNo
 * @param enteredOrderNo
 * @param serviceList
 * @param serviceProductList
 * @param poList
 * @param licenseCompanyList
 * @param storeList
 * @param noOfCopy
 * @param roleID
 * @param employeeId 
 * @return
 */
	public  String copyOrderUserInterfaceProcess(Long orderNo,
			long enteredOrderNo, String serviceList, String serviceProductList,
			String poList, String licenseCompanyList, String storeList,
			String noOfCopy, Long roleID, String employeeId)  {
		
		boolean status=false;
		String message = "Success";
		Connection conn= null;
		
		try{
			conn=DbConnection.getConnectionObject();
			String gui_old_spids[]=serviceProductList.split(",");
			String poIds[]=poList.split(",");
			String licenseCompanyIds[]=licenseCompanyList.split(",");
			String storeIds[]=storeList.split(",");
		
		HashMap<Long,CopyOrderLineDataDTO> hashMapGUIDetails=new HashMap<Long, CopyOrderLineDataDTO>();
		CopyOrderLineDataDTO copyOrderLineDto=null;
		if(enteredOrderNo!=orderNo)
		{
			for (int l = 0 ; l<gui_old_spids.length;l++) 
			{
				String key=gui_old_spids[l];
				Long oldSpIdKey=Long.valueOf(key);
				copyOrderLineDto=new CopyOrderLineDataDTO();
				copyOrderLineDto.setPoDetailId(Integer.valueOf(poIds[l]));
				copyOrderLineDto.setLicenseCompanyId(Long.valueOf(licenseCompanyIds[l]));
				copyOrderLineDto.setStoreId(Long.valueOf(storeIds[l]));
				hashMapGUIDetails.put(oldSpIdKey, copyOrderLineDto);
			}
		}
		String[] copyNo =  noOfCopy.split(",");
		List<Integer> lsNoService=new ArrayList<Integer>();
		for(String count:copyNo){
			lsNoService.add(Integer.valueOf(count));
		}

		String[] serviceIDs=serviceList.split(",");
		ArrayList<Long> lsServices=new ArrayList<Long>();
		for(String serviceid:serviceIDs){
			lsServices.add(Long.valueOf(serviceid));
		}
		
		List<Long>filteredServiceIds = null;
		List<Integer> filteredCopyCount = null;
		ValidateHardwareLine validHdObj = validateAndFilterHardwareService(conn,orderNo, Collections.unmodifiableList(lsServices), Collections.unmodifiableList(lsNoService));
			
		if(AppConstants.DO_NO_COPY == validHdObj.status){
				message="Dispatch Address is Not Available to this account..!";
				return message;
			} else
			if(AppConstants.PARTIAL_COPY == validHdObj.status){
				filteredServiceIds = validHdObj.serviceList;
				filteredCopyCount = validHdObj.noOfCopy;
				message = "Some Services has been copied , rest of the services doesn't have Dispatchaddress for Hardwarelineitem";
			}else if(AppConstants.FULL_COPY == validHdObj.status){
				filteredServiceIds = lsServices;
				filteredCopyCount = lsNoService;
				message = "Selected Services has been copied";
		}
				
			conn.setAutoCommit(false);
			HashMap<Object, List<Long>> map_ReserverIds = CopyOrderDao.reserveObjectsIdsInDB(conn,Collections.unmodifiableList(filteredServiceIds) , Collections.unmodifiableList(filteredCopyCount),orderNo);  
			conn.rollback();
			status=copyServices(conn,AppConstants.COPY_SERVICE_FEATURE,orderNo,Collections.unmodifiableList(filteredServiceIds) , Collections.unmodifiableList(filteredCopyCount),roleID,hashMapGUIDetails,employeeId,map_ReserverIds);
			conn.commit();
			
		}catch(Exception exp){
			//Utility.LOG(true, false, exp, ":: from method copyOrderUserInterfaceProcess");
			Utility.LOG_ITER(true, false, exp, ":: from method copyOrderUserInterfaceProcess");
			try {
				conn.rollback();
			} catch (SQLException e) {
				Utility.LOG(true, false, e, ":: from method copyOrderUserInterfaceProcess");
			}
		}finally{
			try {
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				Utility.LOG(true, false, e, ":: from method copyOrderUserInterfaceProcess");
			}
		}
		return (status==true?message:"Some Error Occured Please Try Again");
	}
	
	/**
	 * @author Vijay
	 * @param mapStatementCounter
	 * This method will increase the statement batch counter by one and again set into Map. 
	 * This method is using for tracking the statement batch counter, so that we can execute
	 * the batch query on certain
	 */
	private void addStatementBatchCounter(Map<PreparedStatement, Integer> mapStatementCounter){
		/*Increase count for add batch */
		PreparedStatement pstm=null;
		int batchCount = (Integer)mapStatementCounter.get(AppConstants.BATCH_COUNTER);
		batchCount ++;
		mapStatementCounter.put(pstm, batchCount);
		/*--End of Increasing count--*/
	}
	
	
	public static void modifyList(ArrayList<Long> al){
		al.remove(1);
	}
	
	
	public  int createOrderWithExistingUserInterfaceProcess(String sourceOrderNo,String empID, Long roleId){
		UniversalWorkQueueDao objUniversalWorkQueueDao =  new UniversalWorkQueueDao();
		//HashMap hashMapGUIDetails = new HashMap<Long, CopyOrderLineDataDTO>();
		
		Connection conn= null;
		boolean status=false;
		int PONUMBEROUT = -1;
		Long srcOrderNo = Long.valueOf(sourceOrderNo);
		
		try{
			conn=DbConnection.getConnectionObject();
			
			/*--Going to validate the source order--*/
			int statusId = isVlidationSuccessForZoneAndRevMigration(conn, srcOrderNo);
			if(AppConstants.ZONE_NOT_AVAILABLE == statusId){
				return statusId; //returning -100
			}
			else if(AppConstants.REVERSE_MIG_ORDER == statusId){
				return statusId; //returning -101
			}
			/*--End of validation--*/
			
			/*-----------------Now going to create the order-----------------*/
		
			String orderType = CopyOrderDao.getOrderType(conn, srcOrderNo);
			
			List<Long> srcServiceIds = null;
			List<Integer> noOfCopyOfService = null;
			HashMap<Object, List<Long>> map_ReserverIds = null;
			
			if(! AppConstants.CHANGE_ORDER.equalsIgnoreCase(orderType)){
				/*Make service List */
				
				srcServiceIds= CopyOrderDao.getServiceList(conn,srcOrderNo);
			
				/*make its no of copy list 
				 * For this purpose we are just creating a list of default value that is 1. That means 
				 * service would be copy only one time in target order 
				 */
				noOfCopyOfService = new ArrayList<Integer>(srcServiceIds.size());
				for(int i=0; i<srcServiceIds.size(); i++){
					noOfCopyOfService.add(1);
				}
			}
			
			
			/* 
			 * get an next order no through sequence. This order no is going to make a new order no 
			 */
			Long targetOrderNo = CopyOrderDao.getNextOrderNoThroughSeq(conn);
			PONUMBEROUT=targetOrderNo.intValue();
			
			if(! AppConstants.CHANGE_ORDER.equalsIgnoreCase(orderType) && srcServiceIds.size() >0 ){
				conn.setAutoCommit(false);
				map_ReserverIds = CopyOrderDao.reserveObjectsIdsInDB(conn,Collections.unmodifiableList(srcServiceIds) , Collections.unmodifiableList(noOfCopyOfService),targetOrderNo);  
				conn.rollback();
			}
			
			conn.setAutoCommit(false);
			objUniversalWorkQueueDao.createNewOrderWithExisting_NewVersion(conn, srcOrderNo, targetOrderNo, empID);
			
			if(! AppConstants.CHANGE_ORDER.equalsIgnoreCase(orderType) && srcServiceIds.size() > 0){
				status=copyServices(conn,AppConstants.CREATE_ORDER_WITH_EXISTING_FEATURE,targetOrderNo,Collections.unmodifiableList(srcServiceIds) , Collections.unmodifiableList(noOfCopyOfService),roleId,null,empID,map_ReserverIds);
			}
			
			conn.commit();
		
		} catch (Exception exp) {
			Utility.LOG(true, false, exp, ":: from method copyOrderWithExistingUserInterfaceProcess");
			try {
				conn.rollback();
			} catch (SQLException e) {
				Utility.LOG(true, false, e, ":: from method copyOrderWithExistingUserInterfaceProcess");
			}
		}finally{
			try {
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				Utility.LOG(true, false, e, ":: from method copyOrderWithExistingUserInterfaceProcess");
			}
		}
		
		return PONUMBEROUT;
	}

	public static int isVlidationSuccessForZoneAndRevMigration(Connection conn, Long orderNo) throws Exception {
		int notAvailableZoneCount = CopyOrderDao.getNotAvailableZoneCount(conn, orderNo);
		if(notAvailableZoneCount == 1){
			return AppConstants.ZONE_NOT_AVAILABLE; // returning -100
		}
		int migratedServiceCount = CopyOrderDao.getMigratedServiceCount(conn, orderNo);
		if(migratedServiceCount > 0){
			return AppConstants.REVERSE_MIG_ORDER; // returning -101
		}
		return 1; // returning positive result that means validation success
	}
			
	
	
	public static void main(String ars[]){
		
		ArrayList<Long> nocopy = new ArrayList<Long>();
		nocopy.add(1l);
		nocopy.add(2l);
		nocopy.add(3l);
		nocopy.add(4l);
		System.out.println("Before modifying list "+nocopy);
		modifyList(nocopy);
		System.out.println("After modifying list "+nocopy);
		
		//String serviceIds = "1000111,1000110,1001230,1001110,1000072,1000511,1000616,1124610,1000029,1000530";
		
		try {
			Connection conn = DbConnection.getConnectionObject();
			//CopyOrderModel obj = new CopyOrderModel();
				//HashMap hm = obj.reserveObjectsIdsInDB(conn,serviceIds, nocopy);
				//System.out.println(hm);
			//obj.loadOrderLevelData(conn, 70606l);
			//Object[] o = nocopy.toArray()
			//String serviceIds1 = obj.getCsv((Long[])o);
			conn.close();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	
}
