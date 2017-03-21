/**
 * @author Anil Kumar
 * Copy Order Functionality using Batch Processing
 * Copy right IBM 2014
 */
//[215] Raveendra      12-feb-2015    Advance payment refund process 
//[216] VIPIN SAHARIA 13th Aug 2015 CBR_20141219-R2-020936-Billing Efficiency Program Drop 3 (BFR 17) Removing Hard coding of linking LSI attmasterids.
//[217] Gunjan Singla  20 Oct 2016  20160219-XX-022117  ANG Bandwidth Correction in iB2B and M6
package com.ibm.ioes.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;

import com.ibm.ioes.model.CopyOrderSqlDeclaration;
import com.ibm.ioes.model.NewOrderModel;
import com.ibm.ioes.model.CopyOrderModel.RESERVED;
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
import com.ibm.ioes.utilities.ApplicationFlags;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Utility;

public class CopyOrderDao {

				//------------------------------------------------[START: INSERTING SQL QUERY DECLARATION ]-----------------------------------------------------------
				private static final String sqlInsertService =CopyOrderSqlDeclaration.insertSqlServiceData();
				private static final String sqlInsertServiceProductAttributes = CopyOrderSqlDeclaration.insertSqlServiceLevelAttributeData();
				private static final String sqlInsertLineItems = CopyOrderSqlDeclaration.insertSqlLineItemData();
				private static final String sqlServiceSummarryAttributes =CopyOrderSqlDeclaration.insertSqlServiceSummarryData();
				private static final String sqlServiceSummarryAttributesHis =CopyOrderSqlDeclaration.insertSqlServiceSummarryHisData();
				private static final String sqlInsertBillingDetails = CopyOrderSqlDeclaration.insertSqlBillingInfoData();
				private static final String sqlInsertLocationDetails = CopyOrderSqlDeclaration.insertSqlLocationInfoData();
				private static final String sqlInsertHardwareDetails = CopyOrderSqlDeclaration.insertSqlHardwareInfoData();
				private static final String sqlInsertServiceDetailsConfigAttValues = CopyOrderSqlDeclaration.insertSqlServiceDetailsConfigData();
				private static final String sqlInsertComponentDetails = CopyOrderSqlDeclaration.insertSqlComponentInfoData();
				private static final String sqlInsertAdvancePaymentDetails = CopyOrderSqlDeclaration.insertSqlAdvancePaymentData();
				private static final String sqlInsertChargeDetails =CopyOrderSqlDeclaration.insertSqlChargesInfoData();
				private static final String sqlInsertScmDetails = CopyOrderSqlDeclaration.insertSqlSCMInfoData();
				//[217] Start
				private static final String sqlUpdateServiceExt =CopyOrderSqlDeclaration.updateSqlServiceExtData();
				//[217] End
				//------------------------------------------------[END: INSERTING SQL QUERY DECLARATION ]-----------------------------------------------------------
				
				//------------------------------------------------[START: READING SQL QUERY DECLARATION ]------------------------------------------------------------
				private  static String sqlGetServiceDetails=CopyOrderSqlDeclaration.getSqlServiceDetails();
				private static String sqlGetServiceAttributesDetails=CopyOrderSqlDeclaration.getSqlServiceLevelAttributeDetails();
				private static String sqlGetLineDetails=CopyOrderSqlDeclaration.getSqlLineDetails();
				private static String sqlGetLineAttributesDetails=CopyOrderSqlDeclaration.getSqlLineLevelAttributeDetails();
				private static String sqlGetLineConfigValues=CopyOrderSqlDeclaration.getSqlLineCinfigAttributeDetails();
				private static String sqlGetBillingInfoDetails=CopyOrderSqlDeclaration.getSqlBillingInfoDetails();
				private static String sqlGetLocationInfoDetails=CopyOrderSqlDeclaration.getSqlLocationInfoDetails();
				private static String sqlGetHardwareInfoDetails=CopyOrderSqlDeclaration.getSqlHardwareInfoDetails();
				private static String sqlGetChargeInfoDetails=CopyOrderSqlDeclaration.getSqlChargeInfoDetails();
				private static String sqlGetComponentInfoDetails=CopyOrderSqlDeclaration.getSqlComponentInfoDetails();
				private static String sqlGetAdvancePayment=CopyOrderSqlDeclaration.getSqlAdvancePaymentDetails();
				private static String sqlGetLineChargeValueSCM=CopyOrderSqlDeclaration.getSqlLineChargeValueSCM();
				private static String sqlGetCurrentOrderPO=CopyOrderSqlDeclaration.getSqlCurrentOrderPODetails();
				private static String sqlGetThirdPartyServiceDetailId = CopyOrderSqlDeclaration.getSqlThirdPartyServiceDetailId();
				private static String sqlGetOrderLevelData=CopyOrderSqlDeclaration.getSqlOrderLevelData();
				private static String sqlServiceWithHardwareLine = CopyOrderSqlDeclaration.getServiceWithHardwareLinecount();
				//------------------------------------------------[END: READING SQL QUERY DECLARATION ]------------------------------------------------------------

	/**
	 * Vijay
	 * @param conn
	 * @param targetOrderNo
	 * @return A OrderDto that contains the order related details
	 */
		public static OrderDto loadOrderLevelData(Connection conn, Long targetOrderNo) {
			PreparedStatement pstmtOrderDet = null;
			ResultSet rsOrderDet = null;
			OrderDto orderDto = new OrderDto();
			try {
				pstmtOrderDet =  conn.prepareStatement(sqlGetOrderLevelData);
				pstmtOrderDet.setLong(1, targetOrderNo);
				pstmtOrderDet.setLong(2, targetOrderNo);
				rsOrderDet = pstmtOrderDet.executeQuery();
				
				if(rsOrderDet.next()){
					orderDto.setAccountId(rsOrderDet.getLong("ACCOUNTID"));
					orderDto.setBcp_Id(rsOrderDet.getLong("BCP_ID"));
					orderDto.setDispatchAddCode(rsOrderDet.getLong("DISPATCH_ADDRESS_CODE"));
					orderDto.setChangeTypeId(rsOrderDet.getLong("CHANGETYPEID"));
					orderDto.setOrder_type(rsOrderDet.getString("ORDER_TYPE"));
					orderDto.setTotalCountInWorkflowTask(rsOrderDet.getInt("WORKFLOW_COUNT"));
					orderDto.setOrderNo(targetOrderNo);
				}
				
			} catch (SQLException e) {
				Utility.LOG(true, false, e, ":: from method CopyOrderModel-> loadOrderLevelData 1");
			}
			finally{
				try 
				{
					DbConnection.closeResultset(rsOrderDet);
					DbConnection.closePreparedStatement(pstmtOrderDet);
				} 
				catch (Exception e) 
				{
					Utility.LOG(true, false, e, ":: from method CopyOrderModel-> loadOrderLevelData 1");
				}	
			}
			return orderDto;
		}
		
		/**
		 * @author Vijay
		 * @param conn
		 * @param serviceId
		 * @return A Map object which contain the service Id as a key and no of hardware line as a value.
		 * This map contain only those services which have hardware line item
		 */
			public static HashMap<Long,Integer> loadHardwareLinecount(Connection conn, String csvOrigServices) {
				PreparedStatement pstmtHardWareCount = null;
				ResultSet rsHardWareCount = null;
				HashMap<Long,Integer> hmap_serviceId_countHardwareLines = new HashMap<Long, Integer>();
				try {
					pstmtHardWareCount =  conn.prepareStatement(sqlServiceWithHardwareLine.replaceFirst("@csvServiceIds", csvOrigServices));
					rsHardWareCount = pstmtHardWareCount.executeQuery();
					
					while(rsHardWareCount.next()){
						hmap_serviceId_countHardwareLines.put(rsHardWareCount.getLong("SERVICEID"), rsHardWareCount.getInt("NoOfHardwareLine"));
					}
					
				} catch (SQLException e) {
					Utility.LOG(true, false, e, ":: from method CopyOrderModel-> loadHardwareLinecount");
				}
				finally{
					try 
					{
						DbConnection.closeResultset(rsHardWareCount);
						DbConnection.closePreparedStatement(pstmtHardWareCount);
					} 
					catch (Exception e) 
					{
						Utility.LOG(true, false, e, ":: from method CopyOrderModel-> loadHardwareLinecount");
					}	
				}
				return hmap_serviceId_countHardwareLines;
			}
		
		/**
		 * 
		 * @param conn
		 * @param services
		 * @param map_ServiceId_Count
		 * @param reservedServiceIds
		 * @param reservedLsi
		 * @param reservedServiceProductIds
		 * @param targetOrderNo
		 * @param roleId
		 * @param orderObj
		 * @param hashMapGUIDetails : - Passed only for Copy Order , This is null for "Create Order With Existing Order" 
		 * @throws Exception
		 * @author Anil Kumar
		 */
		public static void generateServicesDataAndLoadInDB(
					Connection conn,
					String module,
					List<ServiceDto> services,
					HashMap<Long,Integer> map_ServiceId_Count, 
					Stack<Long> reservedServiceIds, 
					Stack<Long> reservedLsi, 
					Stack<Long> reservedServiceProductIds, 
					Set<Long> setThirdPartyServiceDetailId,
					Long targetOrderNo, 
					Long roleId, 
					OrderDto orderObj, 
					HashMap<Long, CopyOrderLineDataDTO> hashMapGUIDetails,
					String employeeId,
					Long intitate_to,
					HashMap<Long, Long> oldPoWithNewPoMap) throws Exception{

				PreparedStatement pstmtService = conn.prepareStatement(sqlInsertService);
				PreparedStatement pstmtServiceProductAttributes = conn.prepareStatement(sqlInsertServiceProductAttributes);
				PreparedStatement pstmtLineItems = conn.prepareStatement(sqlInsertLineItems);
				PreparedStatement pstmtServiceSummarryAttributes = conn.prepareStatement(sqlServiceSummarryAttributes);
				PreparedStatement pstmtServiceSummarryAttributesHis = conn.prepareStatement(sqlServiceSummarryAttributesHis);
				PreparedStatement pstmtBillingDetails = conn.prepareStatement(sqlInsertBillingDetails);
				PreparedStatement pstmtLocationDetails = conn.prepareStatement(sqlInsertLocationDetails);
				PreparedStatement pstmtHardwareDetails = conn.prepareStatement(sqlInsertHardwareDetails);
				PreparedStatement pstmtChargeDetails = conn.prepareStatement(sqlInsertChargeDetails);
				PreparedStatement pstmtServiceDetailsConfigAttValues = conn.prepareStatement(sqlInsertServiceDetailsConfigAttValues);
				PreparedStatement pstmtComponentDetails = conn.prepareStatement(sqlInsertComponentDetails);
				PreparedStatement pstmtAdvancePaymentDetails = conn.prepareStatement(sqlInsertAdvancePaymentDetails);
				PreparedStatement pstmtScmDetails = conn.prepareStatement(sqlInsertScmDetails);
				//[217] Start
				PreparedStatement pstmtServiceExt = conn.prepareStatement(sqlUpdateServiceExt);
				//[217] End
				//[217]- added pstmtServiceExt in array of prepared statements
				PreparedStatement [] arrPstmt={pstmtService,pstmtServiceProductAttributes,pstmtLineItems,pstmtServiceSummarryAttributes,pstmtServiceSummarryAttributesHis,
						pstmtBillingDetails,pstmtLocationDetails,pstmtHardwareDetails,pstmtChargeDetails,pstmtServiceDetailsConfigAttValues,
						pstmtComponentDetails,pstmtAdvancePaymentDetails,pstmtScmDetails,pstmtServiceExt};
							
				
				Set<Long>setHardCodeLineAttMasterId=new HashSet<Long>();
				//[216] START
				//setHardCodeLineAttMasterId.add(3969L);
				setHardCodeLineAttMasterId.add(3932L);
				setHardCodeLineAttMasterId.add(3944L);
				setHardCodeLineAttMasterId.add(3945L);
				setHardCodeLineAttMasterId.add(3947L);
				//setHardCodeLineAttMasterId.add(4093L);
				setHardCodeLineAttMasterId.add(3948L);
				//setHardCodeLineAttMasterId.add(5000327L);
				setHardCodeLineAttMasterId.addAll(ApplicationFlags.SERVICE_LINKING_ATTMASTERIDS_SET);
				//[216] END
				Map<Long,Long> mapCurrentOrderPOandEntity = null;
				if(AppConstants.COPY_SERVICE_FEATURE.equals(module)){
					mapCurrentOrderPOandEntity=loadCurrentOrderPO(conn,targetOrderNo);	
				}
				
				
				/*make a HashMap that contain batch statement counter. set default value to 0 with each prepared statement object*/
					Map<PreparedStatement, Integer> mapStatementCounter = new HashMap<PreparedStatement, Integer>();
					mapStatementCounter.put(pstmtService ,0);
					mapStatementCounter.put(pstmtServiceProductAttributes ,0);
					mapStatementCounter.put(pstmtLineItems ,0);
					mapStatementCounter.put(pstmtServiceSummarryAttributes ,0);
					mapStatementCounter.put(pstmtServiceSummarryAttributesHis,0); 
					mapStatementCounter.put(pstmtBillingDetails ,0);
					mapStatementCounter.put(pstmtLocationDetails ,0);
					mapStatementCounter.put(pstmtHardwareDetails ,0);
					mapStatementCounter.put(pstmtChargeDetails ,0);
					mapStatementCounter.put(pstmtServiceDetailsConfigAttValues ,0);
					mapStatementCounter.put(pstmtComponentDetails ,0);
					mapStatementCounter.put(pstmtAdvancePaymentDetails ,0);
					mapStatementCounter.put(pstmtScmDetails ,0);
					//[217] Start
					mapStatementCounter.put(pstmtServiceExt ,0);
					//[217] End
				/*make a HashMap that contain batch statement counter. set default value to 0 with each prepared statement object*/
				
				for (ServiceDto serviceOb : services) {
					int totalServiceCount=map_ServiceId_Count.get(serviceOb.getServiceId());
					for(int i=1;i<=totalServiceCount;i++){
					
						Long generatedServiceId=reservedServiceIds.pop();
						Long generatedLsi=reservedLsi.pop();
						//[217] -added pstmtServiceExt in method generateBatch()
						generateBatch(module,serviceOb,pstmtService,pstmtServiceProductAttributes,pstmtLineItems,pstmtServiceSummarryAttributes,pstmtServiceSummarryAttributesHis,
								pstmtBillingDetails,pstmtLocationDetails,pstmtHardwareDetails,pstmtChargeDetails,pstmtServiceDetailsConfigAttValues,
								pstmtComponentDetails,pstmtAdvancePaymentDetails,pstmtScmDetails,pstmtServiceExt,generatedServiceId,generatedLsi,reservedServiceProductIds,setThirdPartyServiceDetailId,targetOrderNo,
								roleId,orderObj,setHardCodeLineAttMasterId,hashMapGUIDetails,mapCurrentOrderPOandEntity,mapStatementCounter,employeeId, intitate_to,oldPoWithNewPoMap);
						
						checkBatchThresholdAndExcute(mapStatementCounter,arrPstmt);//implemented
					}	
					/*This line should be execute on some condition*/
					executeAllStatement(mapStatementCounter,arrPstmt);  
				}
			}
		/**
		 * @author Anil Kumar
		 * @param mapStatementCounter
		 * @param arrPstmt
		 * @return integer
		 * @throws Exception
		 */
		public static int executeAllStatement(Map<PreparedStatement, Integer> mapStatementCounter, PreparedStatement [] arrPstmt) throws Exception{
			Integer executedFlag=0;
			//check if service batch generated more than one row then execute service batch
			if(mapStatementCounter.get(arrPstmt[0]) > 0){			
				int[] results=arrPstmt[0].executeBatch();
				for(int i:results){
					if(i==PreparedStatement.EXECUTE_FAILED){
						executedFlag=-1;
						throw new Exception(" Exception occurred during executing pstmtService Batch::");
					}
				}
				executedFlag=1;
			}
			//check if service level attribute batch generated more than one row then execute service level attribute batch
			if(mapStatementCounter.get(arrPstmt[1]) > 0){				
				int[] results=arrPstmt[1].executeBatch();
				for(int i:results){
					if(i==PreparedStatement.EXECUTE_FAILED){
						executedFlag=-1;
						throw new Exception(" Exception occurred during executing pstmtServiceProductAttributes Batch::");
					}
				}
				executedFlag=1;
			}
			//check if lines batch generated more than one row then execute lines batch
			if(mapStatementCounter.get(arrPstmt[2]) > 0){
				int[] results=arrPstmt[2].executeBatch();
				for(int i:results){
					if(i==PreparedStatement.EXECUTE_FAILED){
						executedFlag=-1;
						throw new Exception(" Exception occurred during executing pstmtLineItems Batch::");
					}
				}
				executedFlag=1;
			}
			//check if lines level attributes batch generated more than one row then execute lines level attributes batch
			if(mapStatementCounter.get(arrPstmt[3]) > 0){
				int[] results=arrPstmt[3].executeBatch();
				for(int i:results){
					if(i==PreparedStatement.EXECUTE_FAILED){
						executedFlag=-1;
						throw new Exception(" Exception occurred during executing pstmtServiceSummarryAttributes Batch::");
					}
				}
				executedFlag=1;
			}
			//check if lines level attributes batch generated more than one row then execute lines level attributes batch
			if(mapStatementCounter.get(arrPstmt[4]) > 0){
				int[] results=arrPstmt[4].executeBatch();
				for(int i:results){
					if(i==PreparedStatement.EXECUTE_FAILED){
						executedFlag=-1;
						throw new Exception(" Exception occurred during executing pstmtServiceSummarryAttributes History Batch::");
					}
				}
				executedFlag=1;
			}
			//check if billing info batch generated more than one row then execute billing info batch
			if(mapStatementCounter.get(arrPstmt[5]) > 0){
				int[] results=arrPstmt[5].executeBatch();
				for(int i:results){
					if(i==PreparedStatement.EXECUTE_FAILED){
						executedFlag=-1;
						throw new Exception(" Exception occurred during executing pstmtBillingDetails Batch::");
					}
				}
				executedFlag=1;
			}
			//check if location info batch generated more than one row then execute location info batch
			if(mapStatementCounter.get(arrPstmt[6]) > 0){
				int[] results=arrPstmt[6].executeBatch();
				for(int i:results){
					if(i==PreparedStatement.EXECUTE_FAILED){
						executedFlag=-1;
						throw new Exception(" Exception occurred during executing pstmtLocationDetails Batch::");
					}
				}
				executedFlag=1;
			}
			//check if hardware info batch generated more than one row then execute hardware info batch
			if(mapStatementCounter.get(arrPstmt[7]) > 0){
				int[] results=arrPstmt[7].executeBatch();
				for(int i:results){
					if(i==PreparedStatement.EXECUTE_FAILED){
						executedFlag=-1;
						throw new Exception(" Exception occurred during executing pstmtHardwareDetails Batch::");
					}
				}
				executedFlag=1;
			}
			//check if charges info batch generated more than one row then execute charges info batch
			if(mapStatementCounter.get(arrPstmt[8]) > 0){
				int[] results=arrPstmt[8].executeBatch();
				for(int i:results){
					if(i==PreparedStatement.EXECUTE_FAILED){
						executedFlag=-1;
						throw new Exception(" Exception occurred during executing pstmtChargeDetails Batch::");
					}
				}
				executedFlag=1;
			}
			//check if service details config info batch generated more than one row then execute service details config batch
			if(mapStatementCounter.get(arrPstmt[9]) > 0){
				int[] results=arrPstmt[9].executeBatch();
				for(int i:results){
					if(i==PreparedStatement.EXECUTE_FAILED){
						executedFlag=-1;
						throw new Exception(" Exception occurred during executing pstmtServiceDetailsConfigAttValues Batch::");
					}
				}
				executedFlag=1;
			}
			//check if component info batch generated more than one row then execute component info batch
			if(mapStatementCounter.get(arrPstmt[10]) > 0){
				int[] results=arrPstmt[10].executeBatch();
				for(int i:results){
					if(i==PreparedStatement.EXECUTE_FAILED){
						executedFlag=-1;
						throw new Exception(" Exception occurred during executing pstmtComponentDetails Batch::");
					}
				}
				executedFlag=1;
			}
			//check if advance payment batch generated more than one row then execute advance payment batch
			if(mapStatementCounter.get(arrPstmt[11]) > 0){
				int[] results=arrPstmt[11].executeBatch();
				for(int i:results){
					if(i==PreparedStatement.EXECUTE_FAILED){
						executedFlag=-1;
						throw new Exception(" Exception occurred during executing pstmtAdvancePaymentDetails Batch::");
					}
				}
				executedFlag=1;
			}
			//check if scm batch generated more than one row then execute scm batch
			if(mapStatementCounter.get(arrPstmt[12]) > 0){
				int[] results=arrPstmt[12].executeBatch();
				for(int i:results){
					if(i==PreparedStatement.EXECUTE_FAILED){
						executedFlag=-1;
						throw new Exception(" Exception occurred during executing pstmtScmDetails Batch::");
					}
				}
				executedFlag=1;
			}
			//[217] Start
			if(mapStatementCounter.get(arrPstmt[13]) > 0){				
				int[] results=arrPstmt[13].executeBatch();
				for(int i:results){
					if(i==PreparedStatement.EXECUTE_FAILED){
						executedFlag=-1;
						throw new Exception(" Exception occurred during executing pstmtServiceExt Batch::");
					}
				}
				executedFlag=1;
			}
			//[217] End
		return executedFlag;
		}
		/**
		 * once execute all batch then we have to clear batch.
		 * @param arrPstmt
		 * @throws Exception
		 */
		public static void clearAllBatch(PreparedStatement [] arrPstmt) throws Exception{
			for(int i=0;i<arrPstmt.length;i++){
				arrPstmt[i].clearBatch();
			}			
		}
		public static void resetBatchCounter(Map<PreparedStatement, Integer> mapStatementCounter, PreparedStatement [] arrPstmt){
			for(int i=0;i<arrPstmt.length;i++){
				mapStatementCounter.put(arrPstmt[i], 0);
			}
		}
		/**
		 * @author Vijay
		 * @param mapStatementCounter
		 * @param arrPstmt
		 * @return Return 1 if all batch statement executed other wise return 0.
		 */
		public static Integer checkBatchThresholdAndExcute(Map<PreparedStatement, Integer> mapStatementCounter, PreparedStatement [] arrPstmt) throws Exception 
		{
			Integer executeflag=0;
			int totalCount=0;
			for(Integer counter:mapStatementCounter.values()){
				totalCount=totalCount+counter;
			}
			
			if(totalCount >=AppConstants.BATCH_SIZE){
				executeflag=executeAllStatement(mapStatementCounter, arrPstmt);
				resetBatchCounter(mapStatementCounter,arrPstmt);
			}
					
			return executeflag;		
		}
		/**
		 * 
		 * @param conn 
		 * @param targetOrderNo
		 * @return
		 * @author Anil Kumar
		 */
		public static Map<Long, Long> loadCurrentOrderPO(Connection conn, Long targetOrderNo) throws Exception {
			ResultSet rs=null;
			PreparedStatement psLoadcurrentPO=null;
			Map<Long,Long> currentPOEntityMap=new HashMap<Long, Long>();
			try{
				psLoadcurrentPO=conn.prepareStatement(sqlGetCurrentOrderPO);
				psLoadcurrentPO.setLong(1, targetOrderNo);
				rs=psLoadcurrentPO.executeQuery();
				
				while(rs.next()){
					currentPOEntityMap.put(rs.getLong("PODETAILNUMBER"), rs.getLong("LEGALENTITY"));
				}
			}finally{
				DbConnection.closeResultset(rs);
				DbConnection.closePreparedStatement(psLoadcurrentPO);
			}
			return currentPOEntityMap;
		}
		/**
		 * @author Anil Kumar
		 * @param serviceOb
		 * @param pstmtService
		 * @param pstmtServiceProductAttributes
		 * @param pstmtLineItems
		 * @param pstmtServiceSummarryAttributes
		 * @param pstmtServiceSummarryAttributesHis
		 * @param pstmtBillingDetails
		 * @param pstmtLocationDetails
		 * @param pstmtHardwareDetails
		 * @param pstmtChargeDetails
		 * @param pstmtServiceDetailsConfigAttValues
		 * @param pstmtComponentDetails
		 * @param pstmtAdvancePaymentDetails
		 * @param pstmtScmDetails
		 * @param reservedServiceId
		 * @param reservedLsi
		 * @param reservedServiceProductIds
		 * @param setThirdPartyServiceDetailId
		 * @param targetOrderNo
		 * @param roleId
		 * @param orderObj
		 * @param setHardCodeLineAttMasterId
		 * @param hashMapGUIDetails  : passed for  Copy Order
		 * @param mapCurrentOrderPOandEntity : passed for  Copy Order
		 * @param mapStatementCounter
		 * @param employeeId
		 * @throws Exception
		 */
		//[217]- added pstmtServiceExt in signature
		public static void generateBatch(String module, ServiceDto serviceOb,
					PreparedStatement pstmtService,
					PreparedStatement pstmtServiceProductAttributes,
					PreparedStatement pstmtLineItems,
					PreparedStatement pstmtServiceSummarryAttributes,
					PreparedStatement pstmtServiceSummarryAttributesHis,
					PreparedStatement pstmtBillingDetails,
					PreparedStatement pstmtLocationDetails,
					PreparedStatement pstmtHardwareDetails,
					PreparedStatement pstmtChargeDetails,
					PreparedStatement pstmtServiceDetailsConfigAttValues,
					PreparedStatement pstmtComponentDetails,
					PreparedStatement pstmtAdvancePaymentDetails, 
					PreparedStatement pstmtScmDetails, 
					PreparedStatement pstmtServiceExt, 
					Long reservedServiceId, 
					Long reservedLsi, 
					Stack<Long> reservedServiceProductIds, 
					Set<Long> setThirdPartyServiceDetailId,
					Long targetOrderNo, 
					Long roleId, 
					OrderDto orderObj, 
					Set<Long> setHardCodeLineAttMasterId,
					HashMap<Long, CopyOrderLineDataDTO> hashMapGUIDetails, 
					Map<Long, Long> mapCurrentOrderPOandEntity,
					Map<PreparedStatement, Integer> mapStatementCounter,
					String employeeId,
					Long intitate_to,
					HashMap<Long, Long> oldPoWithNewPoMap) throws Exception {
			
					if(serviceOb !=null){
						//Creating batch for service
						generateServiceBatch(serviceOb,pstmtService,reservedServiceId,reservedLsi,targetOrderNo,roleId,orderObj,mapStatementCounter,employeeId,intitate_to);
						//Creating batch for service attribute
						//[217] Start
						generateServiceExtBatch(serviceOb,pstmtServiceExt,reservedServiceId,mapStatementCounter);
						//[217] End
						generateServiceAttributeBatch(serviceOb,pstmtServiceProductAttributes,reservedServiceId,mapStatementCounter,employeeId);
						generatedServiceLinesBatch(module,serviceOb,pstmtLineItems,pstmtServiceSummarryAttributes,pstmtServiceSummarryAttributesHis,pstmtBillingDetails,pstmtLocationDetails,pstmtHardwareDetails
						//Creating batch for Service Lines
								,pstmtChargeDetails,pstmtServiceDetailsConfigAttValues,pstmtComponentDetails,pstmtAdvancePaymentDetails,pstmtScmDetails,
								reservedServiceId,reservedServiceProductIds,setHardCodeLineAttMasterId,setThirdPartyServiceDetailId,targetOrderNo,hashMapGUIDetails,mapCurrentOrderPOandEntity,reservedLsi,orderObj,roleId,mapStatementCounter,employeeId,oldPoWithNewPoMap);
					
					}
				
			}
		/**
		 * @author Anil Kumar
		 * @param serviceOb
		 * @param pstmtService
		 * @param reservedServiceId
		 * @param reservedLsi
		 * @param targetOrderNo
		 * @param roleId
		 * @param orderObj
		 * @param mapStatementCounter
		 * @param employeeId
		 * @throws Exception
		 */
			public static void generateServiceBatch(ServiceDto serviceOb,
					PreparedStatement pstmtService, 
					Long reservedServiceId, 
					Long reservedLsi,
					Long targetOrderNo,
					Long roleId, 
					OrderDto orderObj,
					Map<PreparedStatement, Integer> mapStatementCounter,
					String employeeId,
					Long intitate_to) throws Exception {
						
						int batchCount=mapStatementCounter.get(pstmtService);
						java.util.Date date=new java.util.Date();
						Utility.dbStatementsetData(pstmtService,1,reservedServiceId,Utility.NUMERIC.LONG);
						Utility.dbStatementsetData(pstmtService,2,serviceOb.getServicetypeId(),Utility.NUMERIC.LONG);
						Utility.dbStatementsetData(pstmtService,3,serviceOb.getServiceStage(), Utility.NUMERIC.STRING);
						Utility.dbStatementsetData(pstmtService,4,serviceOb.getEffectiveStartDate(),Utility.NUMERIC.DATE);
						Utility.dbStatementsetData(pstmtService,5,serviceOb.getCustLogicalSiNo(),Utility.NUMERIC.STRING);
						Utility.dbStatementsetData(pstmtService,6,serviceOb.getEffectiveEndDate(),Utility.NUMERIC.DATE);
						Utility.dbStatementsetData(pstmtService,7,serviceOb.getProvisionPlan(),Utility.NUMERIC.STRING);
						Utility.dbStatementsetData(pstmtService,8,serviceOb.getRemarks(),Utility.NUMERIC.STRING);
						Utility.dbStatementsetData(pstmtService,9,serviceOb.getServiceSubtypeId(),Utility.NUMERIC.LONG);
						Utility.dbStatementsetData(pstmtService,10,serviceOb.getPreOrderNo(),Utility.NUMERIC.STRING);
						Utility.dbStatementsetData(pstmtService,11,serviceOb.getOldCustLogicalSiNo(),Utility.NUMERIC.STRING);						
						Utility.dbStatementsetData(pstmtService,12,serviceOb.getChangeType(),Utility.NUMERIC.LONG);
						Utility.dbStatementsetData(pstmtService,13,reservedLsi,Utility.NUMERIC.LONG);
						Utility.dbStatementsetData(pstmtService,14,reservedLsi,Utility.NUMERIC.LONG);
						Utility.dbStatementsetData(pstmtService,15,new Timestamp(date.getTime()),Utility.NUMERIC.TIMESTAMP);
						Utility.dbStatementsetData(pstmtService,16,new Timestamp(date.getTime()),Utility.NUMERIC.TIMESTAMP);
						Utility.dbStatementsetData(pstmtService,17,serviceOb.getRfsDate(),Utility.NUMERIC.DATE);
						Utility.dbStatementsetData(pstmtService,18,serviceOb.getProductId(),Utility.NUMERIC.LONG);
						Utility.dbStatementsetData(pstmtService,19,serviceOb.getSubProductId(),Utility.NUMERIC.LONG);
						Utility.dbStatementsetData(pstmtService,20,serviceOb.getSubChangeTypeId(),Utility.NUMERIC.LONG);
						if("D".equalsIgnoreCase(orderObj.getOrder_type())){
							Utility.dbStatementsetData(pstmtService,21,1,Utility.NUMERIC.INTEGER);
						}else{
							Utility.dbStatementsetData(pstmtService,21,0,Utility.NUMERIC.INTEGER);
						}
						Utility.dbStatementsetData(pstmtService,22,serviceOb.getBundled(),Utility.NUMERIC.STRING);
						Utility.dbStatementsetData(pstmtService,23,serviceOb.getDowntimeClause(),Utility.NUMERIC.STRING);		
						Utility.dbStatementsetData(pstmtService,24,serviceOb.getIsServiceValidated(),Utility.NUMERIC.INTEGER);
						Utility.dbStatementsetData(pstmtService,25,targetOrderNo,Utility.NUMERIC.LONG);
						Utility.dbStatementsetData(pstmtService,26,intitate_to,Utility.NUMERIC.LONG);
						Utility.dbStatementsetData(pstmtService,27,Long.valueOf(employeeId),Utility.NUMERIC.LONG);
						Utility.dbStatementsetData(pstmtService,28,Long.valueOf(employeeId),Utility.NUMERIC.LONG);
						pstmtService.addBatch();
						
						batchCount=batchCount+1;
						mapStatementCounter.put(pstmtService, batchCount);
						//addStatementBatchCounter(mapStatementCounter);
			}
			/**
			 * @author Gunjan Singla
			 * @param serviceOb
			 * @param Drop and Carry attributes
			 * @param reservedServiceId
			 * @param mapStatementCounter
			 * @throws Exception
			 */
			public static void generateServiceExtBatch(ServiceDto serviceOb,
					PreparedStatement pstmtServiceExt, Long reservedServiceId,Map<PreparedStatement, Integer> mapStatementCounter) throws Exception {					
				
				int batchCount=mapStatementCounter.get(pstmtServiceExt);
				
				Utility.dbStatementsetData(pstmtServiceExt,1,serviceOb.getServiceFlavor(), Utility.NUMERIC.STRING);
				Utility.dbStatementsetData(pstmtServiceExt,2,serviceOb.getCircuitType(), Utility.NUMERIC.STRING);
				Utility.dbStatementsetData(pstmtServiceExt,3,reservedServiceId,Utility.NUMERIC.LONG);
				pstmtServiceExt.addBatch();
				batchCount=batchCount+1;
				mapStatementCounter.put(pstmtServiceExt, batchCount);
			}
			
			/**
			 * @author Anil Kumar
			 * @param serviceOb
			 * @param pstmtServiceProductAttributes
			 * @param reservedServiceId
			 * @param mapStatementCounter
			 * @param employeeId
			 * @throws Exception
			 */
				public static void generateServiceAttributeBatch(ServiceDto serviceOb,
						PreparedStatement pstmtServiceProductAttributes,
						Long reservedServiceId,
						Map<PreparedStatement, Integer> mapStatementCounter,String employeeId) throws Exception{
					
					if(serviceOb !=null && serviceOb.getLsServiceProdAtt()!=null){
						int batchCount=mapStatementCounter.get(pstmtServiceProductAttributes);
						java.util.Date date=new java.util.Date();
						for(ServiceProductAttributeDto serviceAtt:serviceOb.getLsServiceProdAtt()){
							Utility.dbStatementsetData(pstmtServiceProductAttributes,1,Long.valueOf(serviceAtt.getLabelAttId()),Utility.NUMERIC.LONG);
							Utility.dbStatementsetData(pstmtServiceProductAttributes,2,serviceAtt.getLabelAttValue(),Utility.NUMERIC.STRING);
							Utility.dbStatementsetData(pstmtServiceProductAttributes,3,reservedServiceId,Utility.NUMERIC.LONG);
							Utility.dbStatementsetData(pstmtServiceProductAttributes,4,new Timestamp(date.getTime()),Utility.NUMERIC.TIMESTAMP);
							Utility.dbStatementsetData(pstmtServiceProductAttributes,5,Long.valueOf(employeeId),Utility.NUMERIC.LONG);
							pstmtServiceProductAttributes.addBatch();
							
							batchCount=batchCount+1;
							//addStatementBatchCounter(mapStatementCounter);
						}
						mapStatementCounter.put(pstmtServiceProductAttributes, batchCount);
					}
				}
				/**
				 * @author Anil Kumar
				 * @param serviceOb
				 * @param pstmtLineItems
				 * @param pstmtServiceSummarryAttributes
				 * @param pstmtServiceSummarryAttributesHis
				 * @param pstmtBillingDetails
				 * @param pstmtLocationDetails
				 * @param pstmtHardwareDetails
				 * @param pstmtChargeDetails
				 * @param pstmtServiceDetailsConfigAttValues
				 * @param pstmtComponentDetails
				 * @param pstmtAdvancePaymentDetails
				 * @param pstmtScmDetails
				 * @param reservedServiceId
				 * @param reservedServiceProductIds
				 * @param setHardCodeLineAttMasterId
				 * @param setThirdPartyServiceDetailId
				 * @param targetOrderNo
				 * @param hashMapGUIDetails
				 * @param mapCurrentOrderPOandEntity
				 * @param reservedLsi
				 * @param orderObj
				 * @param roleId
				 * @param mapStatementCounter
				 * @param employeeId
				 * @throws Exception
				 */
					public static void generatedServiceLinesBatch(String module, ServiceDto serviceOb,
							PreparedStatement pstmtLineItems, 
							PreparedStatement pstmtServiceSummarryAttributes, 
							PreparedStatement pstmtServiceSummarryAttributesHis,
							PreparedStatement pstmtBillingDetails, 
							PreparedStatement pstmtLocationDetails, 
							PreparedStatement pstmtHardwareDetails, 
							PreparedStatement pstmtChargeDetails, 
							PreparedStatement pstmtServiceDetailsConfigAttValues, 
							PreparedStatement pstmtComponentDetails,
							PreparedStatement pstmtAdvancePaymentDetails, 
							PreparedStatement pstmtScmDetails, 
							Long reservedServiceId,
							Stack<Long> reservedServiceProductIds, 
							Set<Long> setHardCodeLineAttMasterId, 
							Set<Long>setThirdPartyServiceDetailId,
							Long targetOrderNo, 
							HashMap<Long, CopyOrderLineDataDTO> hashMapGUIDetails, 
							Map<Long, Long> mapCurrentOrderPOandEntity, 
							Long reservedLsi, OrderDto orderObj,
							Long roleId,
							Map<PreparedStatement, Integer> mapStatementCounter,
							String employeeId,
							HashMap<Long, Long> oldPoWithNewPoMap) throws Exception {
							

						if(serviceOb !=null && serviceOb.getLsLineItemDetails() !=null){
							
							Long oldOrderNo=serviceOb.getOrderNo();
							Long accountId=serviceOb.getAccountId();
							//mapping old line and new line where key is old line and value is new line
							Map<Long,Long> old_new_Line_mapped=new HashMap<Long,Long>();
							for(LineItemDto line:serviceOb.getLsLineItemDetails()){
								Long tempKey=line.getServiceProductId();
								Long newLineItem=reservedServiceProductIds.pop();
								old_new_Line_mapped.put(tempKey, newLineItem);
							}
							//Iterate each line of each service
							int lineBatchCount=mapStatementCounter.get(pstmtLineItems);
							java.util.Date date=new java.util.Date();
							for(LineItemDto line:serviceOb.getLsLineItemDetails()){
								Long generatedNewSPID=old_new_Line_mapped.get(line.getServiceProductId());
								Long generatedParentSPID=null;
								if(old_new_Line_mapped.get(line.getParentServiceProductId()) ==null){
									generatedParentSPID=0l;
								}else{
									generatedParentSPID=old_new_Line_mapped.get(line.getParentServiceProductId());
								}
								
								Utility.dbStatementsetData(pstmtLineItems,1,generatedNewSPID,Utility.NUMERIC.LONG);
								Utility.dbStatementsetData(pstmtLineItems,2,reservedServiceId,Utility.NUMERIC.LONG);
								Utility.dbStatementsetData(pstmtLineItems,3,line.getServiceDetailId(),Utility.NUMERIC.LONG);
								Utility.dbStatementsetData(pstmtLineItems,4,generatedParentSPID,Utility.NUMERIC.LONG);								
								Utility.dbStatementsetData(pstmtLineItems,5,0,Utility.NUMERIC.INTEGER);
								Utility.dbStatementsetData(pstmtLineItems,6,0,Utility.NUMERIC.INTEGER);
								Utility.dbStatementsetData(pstmtLineItems,7,0,Utility.NUMERIC.INTEGER);
								Utility.dbStatementsetData(pstmtLineItems,8,0,Utility.NUMERIC.INTEGER);
								Utility.dbStatementsetData(pstmtLineItems,9,0,Utility.NUMERIC.INTEGER);
								Utility.dbStatementsetData(pstmtLineItems,10,0,Utility.NUMERIC.INTEGER);
								Utility.dbStatementsetData(pstmtLineItems,11,0,Utility.NUMERIC.INTEGER);
								Utility.dbStatementsetData(pstmtLineItems,12,line.getLogicalCircuitId(),Utility.NUMERIC.STRING);
								Utility.dbStatementsetData(pstmtLineItems,13,line.getInfraOrderNo(),Utility.NUMERIC.STRING);
								Utility.dbStatementsetData(pstmtLineItems,14,line.getMetasolvCircuitId(),Utility.NUMERIC.STRING);
								Utility.dbStatementsetData(pstmtLineItems,15,line.getConfigId(),Utility.NUMERIC.LONG);
								Utility.dbStatementsetData(pstmtLineItems,16,line.getIsDummy(),Utility.NUMERIC.INTEGER);
								Utility.dbStatementsetData(pstmtLineItems,17,new Timestamp(date.getTime()),Utility.NUMERIC.TIMESTAMP);
								Utility.dbStatementsetData(pstmtLineItems,18,new Timestamp(date.getTime()),Utility.NUMERIC.TIMESTAMP);
								Utility.dbStatementsetData(pstmtLineItems,19,Long.valueOf(employeeId),Utility.NUMERIC.LONG);
								Utility.dbStatementsetData(pstmtLineItems,20,Long.valueOf(employeeId),Utility.NUMERIC.LONG);
								
								pstmtLineItems.addBatch();
								
								lineBatchCount=lineBatchCount+1;
								
								//addStatementBatchCounter(mapStatementCounter);
								
								generatedLineServiceSummarryBatch(line,pstmtServiceSummarryAttributes,pstmtServiceSummarryAttributesHis,generatedNewSPID,setHardCodeLineAttMasterId,setThirdPartyServiceDetailId,targetOrderNo,reservedServiceId,mapStatementCounter,employeeId);
								
								generatedLineBillingInfoBatch(module,line,pstmtBillingDetails,generatedNewSPID,hashMapGUIDetails,mapCurrentOrderPOandEntity,reservedLsi,oldOrderNo,targetOrderNo,orderObj,accountId,mapStatementCounter,employeeId,oldPoWithNewPoMap);
								
								generatedLineLocationInfoBatch(line,pstmtLocationDetails,generatedNewSPID,orderObj,oldOrderNo,targetOrderNo,accountId,mapStatementCounter,employeeId);
								
								generatedLineHardwareInfoBatch(module,line,pstmtHardwareDetails,generatedNewSPID,orderObj,hashMapGUIDetails,oldOrderNo,targetOrderNo,accountId,mapStatementCounter,employeeId,oldPoWithNewPoMap);
							
								generatedLineChargeInfoBatch(module,line,pstmtChargeDetails,reservedServiceId,generatedNewSPID,orderObj,hashMapGUIDetails,targetOrderNo,oldOrderNo,accountId,mapStatementCounter,employeeId,oldPoWithNewPoMap);
								
								generatedLineComponentInfoBatch(line,pstmtComponentDetails,reservedServiceId,generatedNewSPID,orderObj,mapStatementCounter,employeeId);
								
								generatedLineAdvancePaymentBatch(line,pstmtAdvancePaymentDetails,generatedNewSPID,targetOrderNo,reservedServiceId,reservedLsi,mapStatementCounter,employeeId);
								
								generatedLineConfigAttributeBatch(line,pstmtServiceDetailsConfigAttValues,generatedNewSPID,mapStatementCounter,employeeId);
																
								generatedLineScmInfoBatch(module,line,pstmtScmDetails,reservedServiceId,generatedNewSPID,orderObj,hashMapGUIDetails,setThirdPartyServiceDetailId,targetOrderNo,oldOrderNo,roleId,mapStatementCounter,employeeId,oldPoWithNewPoMap);
							}
							mapStatementCounter.put(pstmtLineItems, lineBatchCount);
						}
					}
					/**
					 * @author Anil Kumar
					 * @param line
					 * @param pstmtScmDetails
					 * @param reservedServiceId
					 * @param generatedNewSPID
					 * @param orderObj
					 * @param hashMapGUIDetails
					 * @param setThirdPartyServiceDetailId
					 * @param targetOrderNo
					 * @param oldOrderNo
					 * @param roleId
					 * @param mapStatementCounter
					 * @throws Exception
					 */
					public static void generatedLineScmInfoBatch(String module, LineItemDto line,
							PreparedStatement pstmtScmDetails, Long reservedServiceId,
							Long generatedNewSPID, OrderDto orderObj,
							HashMap<Long, CopyOrderLineDataDTO> hashMapGUIDetails,
							Set<Long> setThirdPartyServiceDetailId,
							Long targetOrderNo, Long oldOrderNo,
							Long roleId,
						    Map<PreparedStatement, Integer> mapStatementCounter,
						    String employeeId,HashMap<Long, Long> oldPoWithNewPoMap) throws Exception {
						
							if(line !=null && line.getLstScmDetails() !=null){
								/*If third party service details available then SCM related date would be inserted */
								if(setThirdPartyServiceDetailId.contains(line.getServiceDetailId())){
									
									java.util.Date date=new java.util.Date();
									int scmBatchCount=mapStatementCounter.get(pstmtScmDetails);
									for(SCMDetailsDto scm:line.getLstScmDetails()){
										//CopyOrderLineDataDTO copyLineGUIDataDto=hashMapGUIDetails.get(scm.getServiceProductId());
										Utility.dbStatementsetData(pstmtScmDetails,1,scm.getItemCode_Id(),Utility.NUMERIC.INTEGER);
										Utility.dbStatementsetData(pstmtScmDetails,2,scm.getQuntity(),Utility.NUMERIC.INTEGER);
										Utility.dbStatementsetData(pstmtScmDetails,3,scm.getChargeValue(),Utility.NUMERIC.DOUBLE);
										Utility.dbStatementsetData(pstmtScmDetails,4,generatedNewSPID,Utility.NUMERIC.LONG);
										Utility.dbStatementsetData(pstmtScmDetails,5,new Timestamp(date.getTime()),Utility.NUMERIC.TIMESTAMP);
										Utility.dbStatementsetData(pstmtScmDetails,6,Long.valueOf(employeeId),Utility.NUMERIC.LONG);
										Utility.dbStatementsetData(pstmtScmDetails,7,new Timestamp(date.getTime()),Utility.NUMERIC.TIMESTAMP);
										Utility.dbStatementsetData(pstmtScmDetails,8,Long.valueOf(employeeId),Utility.NUMERIC.LONG);
										Utility.dbStatementsetData(pstmtScmDetails,9,scm.getDeliveryLocationId(),Utility.NUMERIC.LONG);
										Utility.dbStatementsetData(pstmtScmDetails,10,scm.getSubInventryId(),Utility.NUMERIC.INTEGER);
										Utility.dbStatementsetData(pstmtScmDetails,11,scm.getAop1_Id(),Utility.NUMERIC.INTEGER);
										Utility.dbStatementsetData(pstmtScmDetails,12,scm.getAop2_Id(),Utility.NUMERIC.INTEGER);
										Utility.dbStatementsetData(pstmtScmDetails,13,scm.getIsactive(),Utility.NUMERIC.INTEGER);
										Utility.dbStatementsetData(pstmtScmDetails,14,scm.getAopYear(),Utility.NUMERIC.STRING);										
										Utility.dbStatementsetData(pstmtScmDetails,15,scm.getPo_no(),Utility.NUMERIC.STRING);										
										Utility.dbStatementsetData(pstmtScmDetails,16,scm.getPoDate(),Utility.NUMERIC.STRING);
										Utility.dbStatementsetData(pstmtScmDetails,17,scm.getPoAmount(),Utility.NUMERIC.DOUBLE);

										pstmtScmDetails.addBatch();
										
										scmBatchCount=scmBatchCount+1;
										//addStatementBatchCounter(mapStatementCounter);
									}
									mapStatementCounter.put(pstmtScmDetails, scmBatchCount);	
								}
							}
					}	

					/**
					 * 
					 * @param line
					 * @param pstmtServiceDetailsConfigAttValues
					 * @param generatedNewSPID
					 * @throws Exception
					 */
				public static void generatedLineConfigAttributeBatch(LineItemDto line,
						PreparedStatement pstmtServiceDetailsConfigAttValues,
						Long generatedNewSPID,Map<PreparedStatement, Integer> mapStatementCounter,String employeeId) throws Exception {
					
					if(line !=null && line.getLineConfigAttDetails() !=null){
						int linconfBatchcount=mapStatementCounter.get(pstmtServiceDetailsConfigAttValues);
						for(ServiceDetailsConfigAttValuesDTO linattConfig:line.getLineConfigAttDetails()){												
							Utility.dbStatementsetData(pstmtServiceDetailsConfigAttValues,1,generatedNewSPID,Utility.NUMERIC.LONG);
							Utility.dbStatementsetData(pstmtServiceDetailsConfigAttValues,2,linattConfig.getAttributeId(),Utility.NUMERIC.STRING);
							Utility.dbStatementsetData(pstmtServiceDetailsConfigAttValues,3,linattConfig.getAttributeValue(),Utility.NUMERIC.STRING);
							pstmtServiceDetailsConfigAttValues.addBatch();
							
							linconfBatchcount=linconfBatchcount+1;
							mapStatementCounter.put(pstmtServiceDetailsConfigAttValues, linconfBatchcount);
							//addStatementBatchCounter(mapStatementCounter);
						}
					}
				}

					/**
					 * 
					 * @param line
					 * @param pstmtAdvancePaymentDetails
					 * @param generatedNewSPID
					 * @param targetOrderNo
					 * @param reservedServiceId
					 * @param reservedLsi
					 * @throws Exception
					 */
						public static void generatedLineAdvancePaymentBatch(LineItemDto line,
							PreparedStatement pstmtAdvancePaymentDetails, Long generatedNewSPID,
							Long targetOrderNo, Long reservedServiceId, Long reservedLsi,Map<PreparedStatement, Integer> mapStatementCounter,String employeeId) throws Exception{

							if(line !=null && line.getAdvancePaymentDetails() !=null){
								int advPaymentBatchCount=mapStatementCounter.get(pstmtAdvancePaymentDetails);
								java.util.Date date= new java.util.Date();
								Integer empid=Integer.valueOf(employeeId);
								
								Utility.dbStatementsetData(pstmtAdvancePaymentDetails,1,line.getAdvancePaymentDetails().getArcExempted(),Utility.NUMERIC.STRING);
								Utility.dbStatementsetData(pstmtAdvancePaymentDetails,2,line.getAdvancePaymentDetails().getArcExpReason(),Utility.NUMERIC.STRING);
								Utility.dbStatementsetData(pstmtAdvancePaymentDetails,3,line.getAdvancePaymentDetails().getArcChqNo(),Utility.NUMERIC.STRING);
								Utility.dbStatementsetData(pstmtAdvancePaymentDetails,4,line.getAdvancePaymentDetails().getArcChqDate(),Utility.NUMERIC.STRING);
								Utility.dbStatementsetData(pstmtAdvancePaymentDetails,5,line.getAdvancePaymentDetails().getArcBankName(),Utility.NUMERIC.STRING);
								Utility.dbStatementsetData(pstmtAdvancePaymentDetails,6,line.getAdvancePaymentDetails().getArcChqAmt(),Utility.NUMERIC.DOUBLE);
								Utility.dbStatementsetData(pstmtAdvancePaymentDetails,7,line.getAdvancePaymentDetails().getArcAmtAjd(),Utility.NUMERIC.DOUBLE);
								Utility.dbStatementsetData(pstmtAdvancePaymentDetails,8,line.getAdvancePaymentDetails().getOtcExempted(),Utility.NUMERIC.STRING);
								Utility.dbStatementsetData(pstmtAdvancePaymentDetails,9,line.getAdvancePaymentDetails().getOtcExpReason(),Utility.NUMERIC.STRING);
								Utility.dbStatementsetData(pstmtAdvancePaymentDetails,10,line.getAdvancePaymentDetails().getOtcChqNo(),Utility.NUMERIC.STRING);
								Utility.dbStatementsetData(pstmtAdvancePaymentDetails,11,line.getAdvancePaymentDetails().getOtcChqDate(),Utility.NUMERIC.STRING);
								Utility.dbStatementsetData(pstmtAdvancePaymentDetails,12,line.getAdvancePaymentDetails().getOtcBankName(),Utility.NUMERIC.STRING);
								Utility.dbStatementsetData(pstmtAdvancePaymentDetails,13,line.getAdvancePaymentDetails().getOtcChqAmt(),Utility.NUMERIC.DOUBLE);
								Utility.dbStatementsetData(pstmtAdvancePaymentDetails,14,line.getAdvancePaymentDetails().getOtcAmtAjd(),Utility.NUMERIC.DOUBLE);
								
								Utility.dbStatementsetData(pstmtAdvancePaymentDetails,15,empid,Utility.NUMERIC.INTEGER);
								Utility.dbStatementsetData(pstmtAdvancePaymentDetails,16,new Timestamp(date.getTime()),Utility.NUMERIC.TIMESTAMP);
								Utility.dbStatementsetData(pstmtAdvancePaymentDetails,17,targetOrderNo,Utility.NUMERIC.LONG);
								Utility.dbStatementsetData(pstmtAdvancePaymentDetails,18,reservedLsi,Utility.NUMERIC.LONG);
								Utility.dbStatementsetData(pstmtAdvancePaymentDetails,19,reservedServiceId,Utility.NUMERIC.LONG);
								Utility.dbStatementsetData(pstmtAdvancePaymentDetails,20,generatedNewSPID,Utility.NUMERIC.LONG);
								
								//Start [215]
								//ARCRECHQAMT, ARCRECHQNO, ARCBANKACNO, ARCREBANKACNO, ARCIFSCCODE, ARCREIFSCCODE, OTCRECHQAMT, OTCRECHQNO, OTCBANKACNO, OTCREBANKACNO, OTCIFSCCODE, OTCREIFSCCODE)")

								Utility.dbStatementsetData(pstmtAdvancePaymentDetails,21,line.getAdvancePaymentDetails().getArcReEnterCheckamount(),Utility.NUMERIC.DOUBLE);
								Utility.dbStatementsetData(pstmtAdvancePaymentDetails,22,line.getAdvancePaymentDetails().getArcReEnterCheckNumber(),Utility.NUMERIC.STRING);
								Utility.dbStatementsetData(pstmtAdvancePaymentDetails,23,line.getAdvancePaymentDetails().getArcBankAccountNumber(),Utility.NUMERIC.STRING);
								Utility.dbStatementsetData(pstmtAdvancePaymentDetails,24,line.getAdvancePaymentDetails().getArcReEnterBankAccountNumber(),Utility.NUMERIC.STRING);
								Utility.dbStatementsetData(pstmtAdvancePaymentDetails,25,line.getAdvancePaymentDetails().getArcIfscCode(),Utility.NUMERIC.STRING);
								Utility.dbStatementsetData(pstmtAdvancePaymentDetails,26,line.getAdvancePaymentDetails().getArcReEnterIfscCode(),Utility.NUMERIC.STRING);
								Utility.dbStatementsetData(pstmtAdvancePaymentDetails,27,line.getAdvancePaymentDetails().getOtcReEnterCheckamount(),Utility.NUMERIC.DOUBLE);
								Utility.dbStatementsetData(pstmtAdvancePaymentDetails,28,line.getAdvancePaymentDetails().getOtcReEnterCheckNumber(),Utility.NUMERIC.STRING);
								Utility.dbStatementsetData(pstmtAdvancePaymentDetails,29,line.getAdvancePaymentDetails().getOtcBankAccountNumber(),Utility.NUMERIC.STRING);
								Utility.dbStatementsetData(pstmtAdvancePaymentDetails,30,line.getAdvancePaymentDetails().getOtcReEnterBankAccountNumber(),Utility.NUMERIC.STRING);
								Utility.dbStatementsetData(pstmtAdvancePaymentDetails,31,line.getAdvancePaymentDetails().getOtcIfscCode(),Utility.NUMERIC.STRING);
								Utility.dbStatementsetData(pstmtAdvancePaymentDetails,32,line.getAdvancePaymentDetails().getOtcReEnterIfscCode(),Utility.NUMERIC.STRING);
								//End [215]
								
								pstmtAdvancePaymentDetails.addBatch();
								
								advPaymentBatchCount=advPaymentBatchCount+1;
								mapStatementCounter.put(pstmtAdvancePaymentDetails, advPaymentBatchCount);
								
								//addStatementBatchCounter(mapStatementCounter);
							}
					}

					/**
					 * 
					 * @param line
					 * @param pstmtComponentDetails
					 * @param reservedServiceId
					 * @param generatedNewSPID
					 * @param orderObj
					 * @throws Exception
					 */
						public static void generatedLineComponentInfoBatch(LineItemDto line,
							PreparedStatement pstmtComponentDetails, Long reservedServiceId,
							Long generatedNewSPID, OrderDto orderObj,Map<PreparedStatement, Integer> mapStatementCounter,String employeeId) throws Exception {
						
							if(line !=null && line.getLstComponentDetails() !=null){
								java.util.Date date=new java.util.Date();
								
								int compBatchCount=mapStatementCounter.get(pstmtComponentDetails);
								
								for(ComponentDTO component:line.getLstComponentDetails()){
									
									Utility.dbStatementsetData(pstmtComponentDetails,1,orderObj.getAccountId(),Utility.NUMERIC.LONG);
									Utility.dbStatementsetData(pstmtComponentDetails,2,generatedNewSPID,Utility.NUMERIC.LONG);
									Utility.dbStatementsetData(pstmtComponentDetails,3,component.getComponentId(),Utility.NUMERIC.LONG);
									Utility.dbStatementsetData(pstmtComponentDetails,4,0,Utility.NUMERIC.INTEGER);
									Utility.dbStatementsetData(pstmtComponentDetails,5,new Timestamp(date.getTime()),Utility.NUMERIC.TIMESTAMP);
									Utility.dbStatementsetData(pstmtComponentDetails,6,component.getPackageId(),Utility.NUMERIC.LONG);
									Utility.dbStatementsetData(pstmtComponentDetails,7,"NEW",Utility.NUMERIC.STRING);
									Utility.dbStatementsetData(pstmtComponentDetails,8,reservedServiceId,Utility.NUMERIC.LONG);
									Utility.dbStatementsetData(pstmtComponentDetails,9,0,Utility.NUMERIC.INTEGER);
									Utility.dbStatementsetData(pstmtComponentDetails,10,component.getComponentStartLogic(),Utility.NUMERIC.STRING);
									Utility.dbStatementsetData(pstmtComponentDetails,11,component.getComponentEndLogic(),Utility.NUMERIC.STRING);
									Utility.dbStatementsetData(pstmtComponentDetails,12,component.getStartDays(),Utility.NUMERIC.INTEGER);
									Utility.dbStatementsetData(pstmtComponentDetails,13,component.getStartMonths(),Utility.NUMERIC.INTEGER);
									Utility.dbStatementsetData(pstmtComponentDetails,14,component.getEndDays(),Utility.NUMERIC.INTEGER);
									Utility.dbStatementsetData(pstmtComponentDetails,15,component.getEndMonths(),Utility.NUMERIC.INTEGER);
									pstmtComponentDetails.addBatch();
									
									compBatchCount=compBatchCount+1;
									//addStatementBatchCounter(mapStatementCounter);
								}
								mapStatementCounter.put(pstmtComponentDetails, compBatchCount);
							}
					}

					/**
					 * 
					 * @param line
					 * @param pstmtChargeDetails
					 * @param reservedServiceId
					 * @param generatedNewSPID
					 * @param orderObj
					 * @param hashMapGUIDetails
					 * @param targetOrderNo
					 * @param oldOrderNo
					 * @param accountId
					 * @throws Exception
					 */
					public static void generatedLineChargeInfoBatch(String module, LineItemDto line,
						PreparedStatement pstmtChargeDetails, Long reservedServiceId,
						Long generatedNewSPID, OrderDto orderObj,
						HashMap<Long, CopyOrderLineDataDTO> hashMapGUIDetails,
						Long targetOrderNo, Long oldOrderNo, Long accountId, Map<PreparedStatement, Integer> mapStatementCounter,String employeeId,
						HashMap<Long, Long> oldPoWithNewPoMap) throws Exception {
					
						if(line !=null && line.getLstChargeDetails() !=null){
							
							int chargeBatchCount=mapStatementCounter.get(pstmtChargeDetails);
							CopyOrderLineDataDTO copyLineGUIDataDto = null;
							for(ChargeDetailsDto charge:line.getLstChargeDetails()){
								
								if(AppConstants.COPY_SERVICE_FEATURE.equalsIgnoreCase(module)){
										copyLineGUIDataDto=hashMapGUIDetails.get(charge.getServiceProductId());
								}
								Utility.dbStatementsetData(pstmtChargeDetails,1,generatedNewSPID,Utility.NUMERIC.LONG);
								Utility.dbStatementsetData(pstmtChargeDetails,2,orderObj.getAccountId(),Utility.NUMERIC.LONG);
								Utility.dbStatementsetData(pstmtChargeDetails,3,charge.getChargesType(),Utility.NUMERIC.LONG);
								Utility.dbStatementsetData(pstmtChargeDetails,4,charge.getChargeName(),Utility.NUMERIC.STRING);
								Utility.dbStatementsetData(pstmtChargeDetails,5,charge.getChargePeriod(),Utility.NUMERIC.LONG);
								Utility.dbStatementsetData(pstmtChargeDetails,6,charge.getChargeAmount(),Utility.NUMERIC.DOUBLE);
								Utility.dbStatementsetData(pstmtChargeDetails,7,charge.getChargeFrequency(),Utility.NUMERIC.STRING);
								Utility.dbStatementsetData(pstmtChargeDetails,8,charge.getChargeFrequencyAmt(),Utility.NUMERIC.DOUBLE);
								Utility.dbStatementsetData(pstmtChargeDetails,9,charge.getStartDateLogic(),Utility.NUMERIC.STRING);
								Utility.dbStatementsetData(pstmtChargeDetails,10,charge.getEndDateLogic(),Utility.NUMERIC.STRING);
								Utility.dbStatementsetData(pstmtChargeDetails,11,0,Utility.NUMERIC.INTEGER);
								Utility.dbStatementsetData(pstmtChargeDetails,12,0,Utility.NUMERIC.INTEGER);
								Utility.dbStatementsetData(pstmtChargeDetails,13,0,Utility.NUMERIC.INTEGER);
								Utility.dbStatementsetData(pstmtChargeDetails,14,0,Utility.NUMERIC.INTEGER);
								Utility.dbStatementsetData(pstmtChargeDetails,15,0,Utility.NUMERIC.INTEGER);
								Utility.dbStatementsetData(pstmtChargeDetails,16,0,Utility.NUMERIC.INTEGER);								
								Utility.dbStatementsetData(pstmtChargeDetails,17,charge.getStartDateDays(),Utility.NUMERIC.INTEGER);
								Utility.dbStatementsetData(pstmtChargeDetails,18,charge.getStartDateMonth(),Utility.NUMERIC.INTEGER);
								Utility.dbStatementsetData(pstmtChargeDetails,19,charge.getEndDateDays(),Utility.NUMERIC.INTEGER);
								Utility.dbStatementsetData(pstmtChargeDetails,20,charge.getEndDateMonths(),Utility.NUMERIC.INTEGER);
								Utility.dbStatementsetData(pstmtChargeDetails,21,charge.getAnnotation(),Utility.NUMERIC.STRING);
								Utility.dbStatementsetData(pstmtChargeDetails,22,charge.getExclude(),Utility.NUMERIC.LONG);
								Utility.dbStatementsetData(pstmtChargeDetails,23,reservedServiceId,Utility.NUMERIC.LONG);
								if(oldOrderNo.equals(targetOrderNo)){
									Utility.dbStatementsetData(pstmtChargeDetails,24,charge.getPoDetailNo(),Utility.NUMERIC.INTEGER);
								}else if(AppConstants.CREATE_ORDER_WITH_EXISTING_FEATURE.equalsIgnoreCase(module)){
									Utility.dbStatementsetData(pstmtChargeDetails,24,Integer.valueOf(oldPoWithNewPoMap.get(charge.getPoDetailNo().longValue()).intValue()),Utility.NUMERIC.INTEGER);
								}else{
									Utility.dbStatementsetData(pstmtChargeDetails,24,copyLineGUIDataDto.getPoDetailId(),Utility.NUMERIC.INTEGER);
								}
								Utility.dbStatementsetData(pstmtChargeDetails,25,charge.getRemarks(),Utility.NUMERIC.STRING);
								Utility.dbStatementsetData(pstmtChargeDetails,26,charge.getPaymentTerm1(),Utility.NUMERIC.LONG);
								Utility.dbStatementsetData(pstmtChargeDetails,27,charge.getPaymentTerm2(),Utility.NUMERIC.LONG);
								Utility.dbStatementsetData(pstmtChargeDetails,28,charge.getPaymentTerm3(),Utility.NUMERIC.LONG);
								Utility.dbStatementsetData(pstmtChargeDetails,29,charge.getPaymentTerm4(),Utility.NUMERIC.LONG);
								Utility.dbStatementsetData(pstmtChargeDetails,30,charge.getTaxRate(),Utility.NUMERIC.STRING);
								Utility.dbStatementsetData(pstmtChargeDetails,31,charge.getLineItemId(),Utility.NUMERIC.LONG);
								Utility.dbStatementsetData(pstmtChargeDetails,32,charge.getSubLineItemId(),Utility.NUMERIC.LONG);
								Utility.dbStatementsetData(pstmtChargeDetails,33,Long.valueOf(employeeId),Utility.NUMERIC.LONG);
								Utility.dbStatementsetData(pstmtChargeDetails,34,Long.valueOf(charge.getChargeName()),Utility.NUMERIC.LONG);
								pstmtChargeDetails.addBatch();
								
								chargeBatchCount=chargeBatchCount+1;
								//addStatementBatchCounter(mapStatementCounter);
							}
							mapStatementCounter.put(pstmtChargeDetails, chargeBatchCount);
						}
				}

					/**
					 * 
					 * @param line
					 * @param pstmtHardwareDetails
					 * @param generatedNewSPID
					 * @param orderObj
					 * @param hashMapGUIDetails
					 * @param oldOrderNo
					 * @param targetOrderNo
					 * @param OldAccountId
					 * @throws Exception
					 */

						public static void generatedLineHardwareInfoBatch(String module, LineItemDto line,
							PreparedStatement pstmtHardwareDetails, Long generatedNewSPID,
							OrderDto orderObj,
							HashMap<Long, CopyOrderLineDataDTO> hashMapGUIDetails, Long oldOrderNo,
							Long targetOrderNo, Long OldAccountId,Map<PreparedStatement, Integer> mapStatementCounter,String employeeId,
							HashMap<Long, Long> oldPoWithNewPoMap) throws Exception {
							if(line !=null && line.getHardwareDetails()!=null){
									int hardwareBatchCount=mapStatementCounter.get(pstmtHardwareDetails);
									
									Long oldserviceProductId=line.getHardwareDetails().getServiceProductId();
									if(oldOrderNo.equals(targetOrderNo)){
										Utility.dbStatementsetData(pstmtHardwareDetails,1,line.getHardwareDetails().getStoreId(),Utility.NUMERIC.LONG);
									}else if(AppConstants.CREATE_ORDER_WITH_EXISTING_FEATURE.equalsIgnoreCase(module)){
										/*Store Id should be same if we are creating new order with existing order  */
										Utility.dbStatementsetData(pstmtHardwareDetails,1,line.getHardwareDetails().getStoreId(),Utility.NUMERIC.LONG);
									}else{
										Utility.dbStatementsetData(pstmtHardwareDetails,1,hashMapGUIDetails.get(oldserviceProductId).getStoreId(),Utility.NUMERIC.LONG);
									}
									Utility.dbStatementsetData(pstmtHardwareDetails,2,line.getHardwareDetails().getHardwareType(),Utility.NUMERIC.STRING);
									Utility.dbStatementsetData(pstmtHardwareDetails,3,line.getHardwareDetails().getFormAvailable(),Utility.NUMERIC.STRING);
									Utility.dbStatementsetData(pstmtHardwareDetails,4,line.getHardwareDetails().getSaleType(),Utility.NUMERIC.STRING);
									Utility.dbStatementsetData(pstmtHardwareDetails,5,line.getHardwareDetails().getSaleNature(),Utility.NUMERIC.STRING);
									if(oldOrderNo.equals(targetOrderNo)){
										Utility.dbStatementsetData(pstmtHardwareDetails,6,line.getHardwareDetails().getDispatchAddCode(),Utility.NUMERIC.LONG);
									}else{
										if(OldAccountId.equals(orderObj.getAccountId())){
											Utility.dbStatementsetData(pstmtHardwareDetails,6,line.getHardwareDetails().getDispatchAddCode(),Utility.NUMERIC.LONG);
										}else{
											Utility.dbStatementsetData(pstmtHardwareDetails,6,orderObj.getDispatchAddCode(),Utility.NUMERIC.LONG);
										}
									}
									Utility.dbStatementsetData(pstmtHardwareDetails,7,orderObj.getAccountId(),Utility.NUMERIC.LONG);
									Utility.dbStatementsetData(pstmtHardwareDetails,8,generatedNewSPID,Utility.NUMERIC.LONG);
									Utility.dbStatementsetData(pstmtHardwareDetails,9,line.getHardwareDetails().getStartDateLogic(),Utility.NUMERIC.STRING);
									Utility.dbStatementsetData(pstmtHardwareDetails,10,line.getHardwareDetails().getEndDateLogic(),Utility.NUMERIC.STRING);
									Utility.dbStatementsetData(pstmtHardwareDetails,11,line.getHardwareDetails().getWarrentyMonths(),Utility.NUMERIC.INTEGER);
									Utility.dbStatementsetData(pstmtHardwareDetails,12,0,Utility.NUMERIC.INTEGER);
									Utility.dbStatementsetData(pstmtHardwareDetails,13,0,Utility.NUMERIC.INTEGER);
									Utility.dbStatementsetData(pstmtHardwareDetails,14,0,Utility.NUMERIC.INTEGER);
									Utility.dbStatementsetData(pstmtHardwareDetails,15,0,Utility.NUMERIC.INTEGER);
									Utility.dbStatementsetData(pstmtHardwareDetails,16,0,Utility.NUMERIC.INTEGER);
									Utility.dbStatementsetData(pstmtHardwareDetails,17,0,Utility.NUMERIC.INTEGER);
									Utility.dbStatementsetData(pstmtHardwareDetails,18,line.getHardwareDetails().getPrincipalAmount(),Utility.NUMERIC.DOUBLE);
									Utility.dbStatementsetData(pstmtHardwareDetails,19,line.getHardwareDetails().getInterestRate(),Utility.NUMERIC.DOUBLE);
									Utility.dbStatementsetData(pstmtHardwareDetails,20,line.getHardwareDetails().getStartMonths(),Utility.NUMERIC.INTEGER);
									Utility.dbStatementsetData(pstmtHardwareDetails,21,line.getHardwareDetails().getEndDays(),Utility.NUMERIC.INTEGER);
									Utility.dbStatementsetData(pstmtHardwareDetails,22,line.getHardwareDetails().getEndMonth(),Utility.NUMERIC.INTEGER);
									Utility.dbStatementsetData(pstmtHardwareDetails,23,line.getHardwareDetails().getExtDays(),Utility.NUMERIC.INTEGER);
									Utility.dbStatementsetData(pstmtHardwareDetails,24,line.getHardwareDetails().getExtMonths(),Utility.NUMERIC.INTEGER);
									Utility.dbStatementsetData(pstmtHardwareDetails,25,line.getHardwareDetails().getStartDays(),Utility.NUMERIC.INTEGER);
									Utility.dbStatementsetData(pstmtHardwareDetails,26,line.getHardwareDetails().getDispatchcontactName(),Utility.NUMERIC.STRING);
									Utility.dbStatementsetData(pstmtHardwareDetails,27,Long.valueOf(employeeId),Utility.NUMERIC.LONG);									
									pstmtHardwareDetails.addBatch();
									
									hardwareBatchCount=hardwareBatchCount+1;
									mapStatementCounter.put(pstmtHardwareDetails, hardwareBatchCount);
									//addStatementBatchCounter(mapStatementCounter);
							}
					}

					/**
					 * 
					 * @param line
					 * @param pstmtServiceSummarryAttributes
					 * @param pstmtServiceSummarryAttributesHis
					 * @param generatedNewSPID
					 * @param setHardCodeLineAttMasterId
					 * @param targetOrderNo
					 * @param reservedServiceId
					 * @throws Exception
					 */
						public static void generatedLineServiceSummarryBatch(LineItemDto line,
								PreparedStatement pstmtServiceSummarryAttributes,
								PreparedStatement pstmtServiceSummarryAttributesHis,
								Long generatedNewSPID, 
								Set<Long> setHardCodeLineAttMasterId, 
								Set<Long> setThirdPartyServiceDetailId,
								Long targetOrderNo, 
								Long reservedServiceId,
								Map<PreparedStatement, Integer> mapStatementCounter,
								String employeeId) throws Exception {
							
							if(line !=null && line.getLstServiceSummarryDetails() !=null){
								int serviceSummrryBatchCount=mapStatementCounter.get(pstmtServiceSummarryAttributes);
								int serviceSummrryHisBatchCount=mapStatementCounter.get(pstmtServiceSummarryAttributesHis);
								
								for(ServiceSummarryAttributesDto serviceSummarry:line.getLstServiceSummarryDetails()){
									//Front Table batch preparation
									Utility.dbStatementsetData(pstmtServiceSummarryAttributes,1,serviceSummarry.getAttMasterId(),Utility.NUMERIC.LONG);
									
									/*Checking third party attribute on the basis of attribute description. A more condition would be added that servicedetailsId that is configure
									 * in TM_appconfig table for third party. If current service detail id match from third party service details id then below code should be run  */
									if(setThirdPartyServiceDetailId.contains(line.getServiceDetailId())){
										if("nfa_number_l_att7".equalsIgnoreCase(serviceSummarry.getAttDescription())){
											Utility.dbStatementsetData(pstmtServiceSummarryAttributes,2,targetOrderNo.toString()+"/"+reservedServiceId+"/"+generatedNewSPID,Utility.NUMERIC.STRING);
										}
										else if("deliver_to_requestor".equalsIgnoreCase(serviceSummarry.getAttDescription())){
											Utility.dbStatementsetData(pstmtServiceSummarryAttributes,2," ",Utility.NUMERIC.STRING);
										}
										else if("preparer".equalsIgnoreCase(serviceSummarry.getAttDescription())){
											Utility.dbStatementsetData(pstmtServiceSummarryAttributes,2," ",Utility.NUMERIC.STRING);
										}else{
											Utility.dbStatementsetData(pstmtServiceSummarryAttributes,2,serviceSummarry.getAttValue_New(),Utility.NUMERIC.STRING);
										}
									}								
									else if(setHardCodeLineAttMasterId.contains(serviceSummarry.getAttMasterId())){
										Utility.dbStatementsetData(pstmtServiceSummarryAttributes,2," ",Utility.NUMERIC.STRING);
									}else{
										Utility.dbStatementsetData(pstmtServiceSummarryAttributes,2,serviceSummarry.getAttValue_New(),Utility.NUMERIC.STRING);
									}
									
									Utility.dbStatementsetData(pstmtServiceSummarryAttributes,3,generatedNewSPID,Utility.NUMERIC.LONG);
									Utility.dbStatementsetData(pstmtServiceSummarryAttributes,4,Long.valueOf(employeeId),Utility.NUMERIC.LONG);
									pstmtServiceSummarryAttributes.addBatch();
									
									serviceSummrryBatchCount=serviceSummrryBatchCount+1;
									
									//addStatementBatchCounter(mapStatementCounter);
									
									//History Table batch preparation
									Utility.dbStatementsetData(pstmtServiceSummarryAttributesHis,1,serviceSummarry.getAttValueId(),Utility.NUMERIC.LONG);
									Utility.dbStatementsetData(pstmtServiceSummarryAttributesHis,2,serviceSummarry.getAttMasterId(),Utility.NUMERIC.LONG);
									
									/*Checking third party attribute on the basis of attribute description. A more condition would be added that servicedetailsId that is configure
									 * in TM_appconfig table for third party. If current service detail id match from third party service details id then below code should be run  */
									if(setThirdPartyServiceDetailId.contains(line.getServiceDetailId())){
										if("nfa_number_l_att7".equalsIgnoreCase(serviceSummarry.getAttDescription())){
											Utility.dbStatementsetData(pstmtServiceSummarryAttributesHis,3,targetOrderNo.toString()+"/"+reservedServiceId+"/"+generatedNewSPID,Utility.NUMERIC.STRING);
											Utility.dbStatementsetData(pstmtServiceSummarryAttributesHis,4,targetOrderNo.toString()+"/"+reservedServiceId+"/"+generatedNewSPID,Utility.NUMERIC.STRING);
										}
										else if("deliver_to_requestor".equalsIgnoreCase(serviceSummarry.getAttDescription())){
											Utility.dbStatementsetData(pstmtServiceSummarryAttributesHis,3," ",Utility.NUMERIC.STRING);
											Utility.dbStatementsetData(pstmtServiceSummarryAttributesHis,4," ",Utility.NUMERIC.STRING);
										}
										else if("preparer".equalsIgnoreCase(serviceSummarry.getAttDescription())){
											Utility.dbStatementsetData(pstmtServiceSummarryAttributesHis,3," ",Utility.NUMERIC.STRING);
											Utility.dbStatementsetData(pstmtServiceSummarryAttributesHis,4," ",Utility.NUMERIC.STRING);
										}else{
											Utility.dbStatementsetData(pstmtServiceSummarryAttributesHis,3,serviceSummarry.getAttValue(),Utility.NUMERIC.STRING);
											Utility.dbStatementsetData(pstmtServiceSummarryAttributesHis,4,serviceSummarry.getAttValue_New(),Utility.NUMERIC.STRING);
										}
									}									
									else if(setHardCodeLineAttMasterId.contains(serviceSummarry.getAttMasterId())){
										Utility.dbStatementsetData(pstmtServiceSummarryAttributesHis,3," ",Utility.NUMERIC.STRING);
										Utility.dbStatementsetData(pstmtServiceSummarryAttributesHis,4," ",Utility.NUMERIC.STRING);
									}else{
										Utility.dbStatementsetData(pstmtServiceSummarryAttributesHis,3,serviceSummarry.getAttValue(),Utility.NUMERIC.STRING);
										Utility.dbStatementsetData(pstmtServiceSummarryAttributesHis,4,serviceSummarry.getAttValue_New(),Utility.NUMERIC.STRING);
									}
									
									Utility.dbStatementsetData(pstmtServiceSummarryAttributesHis,5,generatedNewSPID,Utility.NUMERIC.LONG);
									Utility.dbStatementsetData(pstmtServiceSummarryAttributesHis,6,targetOrderNo,Utility.NUMERIC.LONG);
									Utility.dbStatementsetData(pstmtServiceSummarryAttributesHis,7,"N",Utility.NUMERIC.STRING);
									Utility.dbStatementsetData(pstmtServiceSummarryAttributesHis,8,reservedServiceId,Utility.NUMERIC.LONG);
									Utility.dbStatementsetData(pstmtServiceSummarryAttributesHis,9,Long.valueOf(employeeId),Utility.NUMERIC.LONG);
									pstmtServiceSummarryAttributesHis.addBatch();
									
									serviceSummrryHisBatchCount=serviceSummrryHisBatchCount+1;
									//addStatementBatchCounter(mapStatementCounter);
								}
								mapStatementCounter.put(pstmtServiceSummarryAttributes, serviceSummrryBatchCount);
								mapStatementCounter.put(pstmtServiceSummarryAttributesHis, serviceSummrryHisBatchCount);
							}		
						}
						/**
						 * 
						 * @param line
						 * @param pstmtBillingDetails
						 * @param generatedNewSPID
						 * @param hashMapGUIDetails
						 * @param mapCurrentOrderPOandEntity
						 * @param reservedLsi
						 * @param oldOrderNo 
						 * @param targetOrderNo 
						 * @param orderObj 
						 * @param accountId 
						 * @throws Exception
						 */
					public static void generatedLineBillingInfoBatch(String module, LineItemDto line,
							PreparedStatement pstmtBillingDetails, Long generatedNewSPID, 
							HashMap<Long, CopyOrderLineDataDTO> hashMapGUIDetails, 
							Map<Long, Long> mapCurrentOrderPOandEntity, 
							Long reservedLsi, 
							Long oldOrderNo, Long targetOrderNo, 
							OrderDto orderObj, 
							Long oldAccountId,
							Map<PreparedStatement, Integer> mapStatementCounter,
							String employeeId, HashMap<Long, Long> oldPoWithNewPoMap) throws Exception {
						
						if(line !=null && line.getBillingDetails() !=null){
							CopyOrderLineDataDTO copyLineGUIDataDto = null;
							int billingInfoBatchCount=mapStatementCounter.get(pstmtBillingDetails);
							
							if(AppConstants.COPY_SERVICE_FEATURE.equalsIgnoreCase(module)){
								copyLineGUIDataDto=hashMapGUIDetails.get(line.getServiceProductId());
							}
							Long billing_level_no=null;
							Utility.dbStatementsetData(pstmtBillingDetails,1,orderObj.getAccountId(),Utility.NUMERIC.LONG);
							Utility.dbStatementsetData(pstmtBillingDetails,2,line.getBillingDetails().getCreditPeriod(),Utility.NUMERIC.LONG);
							Utility.dbStatementsetData(pstmtBillingDetails,3,line.getBillingDetails().getBillingMode(),Utility.NUMERIC.STRING);
							Utility.dbStatementsetData(pstmtBillingDetails,4,line.getBillingDetails().getNoticePeriod(),Utility.NUMERIC.LONG);
							Utility.dbStatementsetData(pstmtBillingDetails,5,line.getBillingDetails().getBillingFormat(),Utility.NUMERIC.STRING);
							Utility.dbStatementsetData(pstmtBillingDetails,6,line.getBillingDetails().getTaxation(),Utility.NUMERIC.STRING);
							Utility.dbStatementsetData(pstmtBillingDetails,7,line.getBillingDetails().getBillingLevel(),Utility.NUMERIC.STRING);
							Utility.dbStatementsetData(pstmtBillingDetails,8,line.getBillingDetails().getCommitmentPeriod(),Utility.NUMERIC.LONG);
							Utility.dbStatementsetData(pstmtBillingDetails,9,line.getBillingDetails().getPenaltyClause(),Utility.NUMERIC.STRING);
							Utility.dbStatementsetData(pstmtBillingDetails,10,generatedNewSPID,Utility.NUMERIC.LONG);
							Utility.dbStatementsetData(pstmtBillingDetails,11,line.getBillingDetails().getBillingType(),Utility.NUMERIC.LONG);
							if(targetOrderNo.equals(oldOrderNo)){
								Utility.dbStatementsetData(pstmtBillingDetails,12,line.getBillingDetails().getBcpId(),Utility.NUMERIC.LONG);
								Utility.dbStatementsetData(pstmtBillingDetails,13,line.getBillingDetails().getServiceBcpId(),Utility.NUMERIC.LONG);//service_bcpid
							}else{
								if(oldAccountId.equals(orderObj.getAccountId())){
									Utility.dbStatementsetData(pstmtBillingDetails,12,line.getBillingDetails().getBcpId(),Utility.NUMERIC.LONG);
									Utility.dbStatementsetData(pstmtBillingDetails,13,line.getBillingDetails().getServiceBcpId(),Utility.NUMERIC.LONG);//service_bcpid
								}else{
									Utility.dbStatementsetData(pstmtBillingDetails,12,orderObj.getBcp_Id(),Utility.NUMERIC.LONG);
									if(line.getBillingDetails().getServiceBcpId()==0 || line.getBillingDetails().getServiceBcpId() ==null){
										Utility.dbStatementsetData(pstmtBillingDetails,13,line.getBillingDetails().getServiceBcpId(),Utility.NUMERIC.LONG);//service_bcpid
									}else{
										Utility.dbStatementsetData(pstmtBillingDetails,13,orderObj.getBcp_Id(),Utility.NUMERIC.LONG);//service_bcpid
									}									
								}
							}
							
							Utility.dbStatementsetData(pstmtBillingDetails,14,0,Utility.NUMERIC.INTEGER);
							Utility.dbStatementsetData(pstmtBillingDetails,15,0,Utility.NUMERIC.INTEGER);
							Utility.dbStatementsetData(pstmtBillingDetails,16,0,Utility.NUMERIC.INTEGER);
							Utility.dbStatementsetData(pstmtBillingDetails,17,0,Utility.NUMERIC.INTEGER);
							Utility.dbStatementsetData(pstmtBillingDetails,18,0,Utility.NUMERIC.INTEGER);
							Utility.dbStatementsetData(pstmtBillingDetails,19,0,Utility.NUMERIC.INTEGER);
							if(targetOrderNo.equals(oldOrderNo)){
								if("3".equals(line.getBillingDetails().getBillingLevel())){
									billing_level_no=reservedLsi;
								}else{
									billing_level_no=line.getBillingDetails().getBillingLevelNo();
								}
							}else{
								if("2".equals(line.getBillingDetails().getBillingLevel())){
									if(AppConstants.CREATE_ORDER_WITH_EXISTING_FEATURE.equalsIgnoreCase(module)){
										billing_level_no = oldPoWithNewPoMap.get(line.getBillingDetails().getPoDetailId());
									}else{
										billing_level_no=copyLineGUIDataDto.getPoDetailId().longValue();
									}
								}else if("3".equals(line.getBillingDetails().getBillingLevel())){
									billing_level_no=reservedLsi;
								}else{
									billing_level_no=0l;
								}		
							}
							
							Utility.dbStatementsetData(pstmtBillingDetails,20,billing_level_no,Utility.NUMERIC.LONG);
							Utility.dbStatementsetData(pstmtBillingDetails,21,line.getBillingDetails().getStdReasonId(),Utility.NUMERIC.LONG);		
							Utility.dbStatementsetData(pstmtBillingDetails,22,line.getBillingDetails().getIsNFA(),Utility.NUMERIC.INTEGER);
							Utility.dbStatementsetData(pstmtBillingDetails,23,line.getBillingDetails().getIsUsage(),Utility.NUMERIC.INTEGER);
							Utility.dbStatementsetData(pstmtBillingDetails,24,line.getBillingDetails().getWarrantyClause(),Utility.NUMERIC.STRING);		
							if(targetOrderNo.equals(oldOrderNo)){
								Utility.dbStatementsetData(pstmtBillingDetails,25,line.getBillingDetails().getPoDetailId(),Utility.NUMERIC.LONG);			
								Utility.dbStatementsetData(pstmtBillingDetails,26,line.getBillingDetails().getLicenseCoId(),Utility.NUMERIC.LONG);
								Utility.dbStatementsetData(pstmtBillingDetails,27,line.getBillingDetails().getEntityId(),Utility.NUMERIC.LONG);
							}else if(AppConstants.CREATE_ORDER_WITH_EXISTING_FEATURE.equalsIgnoreCase(module)){
								Utility.dbStatementsetData(pstmtBillingDetails,25,oldPoWithNewPoMap.get(line.getBillingDetails().getPoDetailId()),Utility.NUMERIC.LONG);			
								Utility.dbStatementsetData(pstmtBillingDetails,26,line.getBillingDetails().getLicenseCoId(),Utility.NUMERIC.LONG);
								Utility.dbStatementsetData(pstmtBillingDetails,27,line.getBillingDetails().getEntityId(),Utility.NUMERIC.LONG);
							}else{
								Utility.dbStatementsetData(pstmtBillingDetails,25,copyLineGUIDataDto.getPoDetailId().longValue(),Utility.NUMERIC.LONG);			
								Utility.dbStatementsetData(pstmtBillingDetails,26,copyLineGUIDataDto.getLicenseCompanyId(),Utility.NUMERIC.LONG);
								Utility.dbStatementsetData(pstmtBillingDetails,27,mapCurrentOrderPOandEntity.get(copyLineGUIDataDto.getPoDetailId().longValue()),Utility.NUMERIC.LONG);
							}
							Utility.dbStatementsetData(pstmtBillingDetails,28,Long.valueOf(employeeId),Utility.NUMERIC.LONG);
							//satyapan OSP By nagarjuna
							Utility.dbStatementsetData(pstmtBillingDetails,29,line.getBillingDetails().getIsOSPTagging(),Utility.NUMERIC.INTEGER);	
							Utility.dbStatementsetData(pstmtBillingDetails,30,line.getBillingDetails().getTxtOSPRegNo(),Utility.NUMERIC.STRING);	
							Utility.dbStatementsetData(pstmtBillingDetails,31,line.getBillingDetails().getTxtOSPRegDate(),Utility.NUMERIC.DATE);
							
							
							pstmtBillingDetails.addBatch();
							billingInfoBatchCount=billingInfoBatchCount+1;
							
							mapStatementCounter.put(pstmtBillingDetails, billingInfoBatchCount);
							//addStatementBatchCounter(mapStatementCounter);
						}
					}
					/**
					 * 
					 * @param line
					 * @param pstmtLocationDetails
					 * @param generatedNewSPID
					 * @param orderObj
					 * @param oldOrderNo
					 * @param targetOrderNo
					 * @param OldAccountId
					 * @throws Exception
					 */
						public static void generatedLineLocationInfoBatch(LineItemDto line,
							PreparedStatement pstmtLocationDetails, Long generatedNewSPID,
							OrderDto orderObj, Long oldOrderNo, Long targetOrderNo, Long OldAccountId, Map<PreparedStatement, Integer> mapStatementCounter,String employeeId) throws Exception {
						
							
							if(line !=null && line.getLocationDetails()!=null){
								int locationBatchCount=mapStatementCounter.get(pstmtLocationDetails);
								Utility.dbStatementsetData(pstmtLocationDetails,1,line.getLocationDetails().getPrimaryLocationType(),Utility.NUMERIC.LONG);
								if(oldOrderNo.equals(targetOrderNo)){
									Utility.dbStatementsetData(pstmtLocationDetails,2,line.getLocationDetails().getPrimaryLocationId(),Utility.NUMERIC.LONG);
								}else{ 
										if(line.getLocationDetails().getPrimaryLocationType() == 1 && !(OldAccountId.equals(orderObj.getAccountId()))){
											Utility.dbStatementsetData(pstmtLocationDetails,2,orderObj.getBcp_Id(),Utility.NUMERIC.LONG);
										}else{
											Utility.dbStatementsetData(pstmtLocationDetails,2,line.getLocationDetails().getPrimaryLocationId(),Utility.NUMERIC.LONG);
										}
								}
								Utility.dbStatementsetData(pstmtLocationDetails,3,line.getLocationDetails().getSecondaryLocationType(),Utility.NUMERIC.LONG);
								if(oldOrderNo.equals(targetOrderNo)){
									Utility.dbStatementsetData(pstmtLocationDetails,4,line.getLocationDetails().getSecondaryLocationId(),Utility.NUMERIC.LONG);
								}else{ 
										if(line.getLocationDetails().getSecondaryLocationType() == 1 && !(OldAccountId.equals(orderObj.getAccountId()))){
											Utility.dbStatementsetData(pstmtLocationDetails,4,orderObj.getBcp_Id(),Utility.NUMERIC.LONG);
										}else{
											Utility.dbStatementsetData(pstmtLocationDetails,4,line.getLocationDetails().getSecondaryLocationId(),Utility.NUMERIC.LONG);
										}
								}
								Utility.dbStatementsetData(pstmtLocationDetails,5,orderObj.getAccountId(),Utility.NUMERIC.LONG);
								Utility.dbStatementsetData(pstmtLocationDetails,6,generatedNewSPID,Utility.NUMERIC.LONG);
								Utility.dbStatementsetData(pstmtLocationDetails,7,0,Utility.NUMERIC.INTEGER);
								Utility.dbStatementsetData(pstmtLocationDetails,8,0,Utility.NUMERIC.INTEGER);
								Utility.dbStatementsetData(pstmtLocationDetails,9,0,Utility.NUMERIC.INTEGER);
								Utility.dbStatementsetData(pstmtLocationDetails,10,0,Utility.NUMERIC.INTEGER);
								Utility.dbStatementsetData(pstmtLocationDetails,11,0,Utility.NUMERIC.INTEGER);
								Utility.dbStatementsetData(pstmtLocationDetails,12,0,Utility.NUMERIC.INTEGER);
								Utility.dbStatementsetData(pstmtLocationDetails,13,line.getLocationDetails().getToAddress(),Utility.NUMERIC.STRING);
								Utility.dbStatementsetData(pstmtLocationDetails,14,line.getLocationDetails().getFromAddress(),Utility.NUMERIC.STRING);
								Utility.dbStatementsetData(pstmtLocationDetails,15,Long.valueOf(employeeId),Utility.NUMERIC.LONG);
								Utility.dbStatementsetData(pstmtLocationDetails,16,Long.valueOf(employeeId),Utility.NUMERIC.LONG);								
								pstmtLocationDetails.addBatch();
								
								locationBatchCount=locationBatchCount+1;
								mapStatementCounter.put(pstmtLocationDetails, locationBatchCount);
								//addStatementBatchCounter(mapStatementCounter);
						}
					}
				public static List<ServiceDetailsConfigAttValuesDTO> loadServiceDetailsConfigAttValues(
								Connection conn, String csvOrigServices) throws Exception {
							
							ResultSet rs = null;
							String value = null;
							PreparedStatement psLoadServdetailConfig=null;
							List<ServiceDetailsConfigAttValuesDTO> allServiceDetailsConfigAttValues = null;
							ServiceDetailsConfigAttValuesDTO serviceDetailsConfigAttValue = null;
							try{
								psLoadServdetailConfig=conn.prepareStatement(sqlGetLineConfigValues.replace("@csvServiceIds", csvOrigServices));
								rs=psLoadServdetailConfig.executeQuery();
							while(rs.next()){
								serviceDetailsConfigAttValue = new ServiceDetailsConfigAttValuesDTO();
								
								value = rs.getString("SERVICEPRODUCTID");
								if(Utility.checkNullValues(value))
									serviceDetailsConfigAttValue.setServiceProductId(null);
								else
									serviceDetailsConfigAttValue.setServiceProductId(Long.valueOf(value));
								
								value = rs.getString("ATTRIBUTE_ID");
								if(Utility.checkNullValues(value))
									serviceDetailsConfigAttValue.setAttributeId(null);
								else
									serviceDetailsConfigAttValue.setAttributeId(value);
								
								value = rs.getString("ATTRIBUTE_VALUE");
								if(Utility.checkNullValues(value))
									serviceDetailsConfigAttValue.setAttributeValue(null);
								else
									serviceDetailsConfigAttValue.setAttributeValue(value);
								
								if(null == allServiceDetailsConfigAttValues)
									allServiceDetailsConfigAttValues = new ArrayList<ServiceDetailsConfigAttValuesDTO>();
								allServiceDetailsConfigAttValues.add(serviceDetailsConfigAttValue);
							}
							}finally{
								DbConnection.closeResultset(rs);
								DbConnection.closePreparedStatement(psLoadServdetailConfig);
							}
							return allServiceDetailsConfigAttValues;
						}
		public static List<ChargeDetailsDto> loadChargeDetails(Connection conn,
								String csvOrigServices) throws Exception {
							
							ResultSet rs = null;
							String value = null;
							PreparedStatement psLoadChargesData=null;
							List<ChargeDetailsDto> chargeDetails = null;
							ChargeDetailsDto chargeDetail = null;
							try{
								psLoadChargesData=conn.prepareStatement(sqlGetChargeInfoDetails.replace("@csvServiceIds", csvOrigServices));
								rs=psLoadChargesData.executeQuery();
							while(rs.next()){
								chargeDetail = new ChargeDetailsDto();
								

								value = rs.getString("SERVICEPRODUCTID");
								if(Utility.checkNullValues(value))
									chargeDetail.setServiceProductId(null);
								else
									chargeDetail.setServiceProductId(Long.valueOf(value));
								
								value = rs.getString("ACCOUNTID");
								if(Utility.checkNullValues(value))
									chargeDetail.setAccountId(null);
								else
									chargeDetail.setAccountId(Long.valueOf(value));
								
								value = rs.getString("CHARGESTYPE");
								if(Utility.checkNullValues(value))
									chargeDetail.setChargesType(null);
								else
									chargeDetail.setChargesType(Long.valueOf(value));
								
								value = rs.getString("CHARGENAME");
								if(Utility.checkNullValues(value))
									chargeDetail.setChargeName(null);
								else
									chargeDetail.setChargeName(value);
								
								value = rs.getString("CHARGEPERIOD");
								if(Utility.checkNullValues(value))
									chargeDetail.setChargePeriod(null);
								else
									chargeDetail.setChargePeriod(Long.valueOf(value));
								
								value = rs.getString("CHARGEAMOUNT");
								if(Utility.checkNullValues(value))
									chargeDetail.setChargeAmount(null);
								else
									chargeDetail.setChargeAmount(Double.valueOf(value));
								
								value = rs.getString("CHARGEFREQUENCY");
								if(Utility.checkNullValues(value))
									chargeDetail.setChargeFrequency(null);
								else
									chargeDetail.setChargeFrequency(value);
								
								value = rs.getString("CHARGEFREQUENCYAMT");
								if(Utility.checkNullValues(value))
									chargeDetail.setChargeFrequencyAmt(null);
								else
									chargeDetail.setChargeFrequencyAmt(Double.valueOf(value));
								
								value = rs.getString("STARTDATELOGIC");
								if(Utility.checkNullValues(value))
									chargeDetail.setStartDateLogic(null);
								else
									chargeDetail.setStartDateLogic(value);
								
								value = rs.getString("ENDDATELOGIC");
								if(Utility.checkNullValues(value))
									chargeDetail.setEndDateLogic(null);
								else
									chargeDetail.setEndDateLogic(value);
								
																
								value = rs.getString("CHARGEINFOID");
								if(Utility.checkNullValues(value))
									chargeDetail.setChargeInfoId(null);
								else
									chargeDetail.setChargeInfoId(Long.valueOf(value));
								
								value = rs.getString("START_DATE_DAYS");
								if(Utility.checkNullValues(value))
									chargeDetail.setStartDateDays(null);
								else
									chargeDetail.setStartDateDays(Integer.valueOf(value));
								
								value = rs.getString("START_DATE_MONTH");
								if(Utility.checkNullValues(value))
									chargeDetail.setStartDateMonth(null);
								else
									chargeDetail.setStartDateMonth(Integer.valueOf(value));
								
								value = rs.getString("END_DATE_DAYS");
								if(Utility.checkNullValues(value))
									chargeDetail.setEndDateDays(null);
								else
									chargeDetail.setEndDateDays(Integer.valueOf(value));
								
								value = rs.getString("END_DATE_MONTH");
								if(Utility.checkNullValues(value))
									chargeDetail.setEndDateMonths(null);
								else
									chargeDetail.setEndDateMonths(Integer.valueOf(value));
								
								value = rs.getString("ANNOTATION");
								if(Utility.checkNullValues(value))
									chargeDetail.setAnnotation(null);
								else
									chargeDetail.setAnnotation(value);
								
								value = rs.getString("EXCLUDE");
								if(Utility.checkNullValues(value))
									chargeDetail.setExclude(null);
								else
									chargeDetail.setExclude(Long.valueOf(value));
								
								value = rs.getString("REMARKS");
								if(Utility.checkNullValues(value))
									chargeDetail.setRemarks(null);
								else
									chargeDetail.setRemarks(value);
								
								value = rs.getString("PAYMENTTERM1");
								if(Utility.checkNullValues(value))
									chargeDetail.setPaymentTerm1(null);
								else
									chargeDetail.setPaymentTerm1(Long.valueOf(value));
								
								value = rs.getString("PAYMENTTERM2");
								if(Utility.checkNullValues(value))
									chargeDetail.setPaymentTerm2(null);
								else
									chargeDetail.setPaymentTerm2(Long.valueOf(value));
								
								value = rs.getString("PAYMENTTERM3");
								if(Utility.checkNullValues(value))
									chargeDetail.setPaymentTerm3(null);
								else
									chargeDetail.setPaymentTerm3(Long.valueOf(value));
								
								value = rs.getString("PAYMENTTERM4");
								if(Utility.checkNullValues(value))
									chargeDetail.setPaymentTerm4(null);
								else
									chargeDetail.setPaymentTerm4(Long.valueOf(value));
								
								value = rs.getString("TAXRATE");
								if(Utility.checkNullValues(value))
									chargeDetail.setTaxRate(null);
								else
									chargeDetail.setTaxRate(value);
								
								value = rs.getString("LINEITEMID");
								if(Utility.checkNullValues(value))
									chargeDetail.setLineItemId(null);
								else
									chargeDetail.setLineItemId(Long.valueOf(value));
								
								value = rs.getString("SUBLINEITEMID");
								if(Utility.checkNullValues(value))
									chargeDetail.setSubLineItemId(null);
								else
									chargeDetail.setSubLineItemId(Long.valueOf(value));
								
								value = rs.getString("PODETAILNO");
								if(Utility.checkNullValues(value))
									chargeDetail.setPoDetailNo(null);
								else
									chargeDetail.setPoDetailNo(Integer.valueOf(value));
								
								if(null == chargeDetails)
									chargeDetails = new ArrayList<ChargeDetailsDto>();
								chargeDetails.add(chargeDetail);
							}
							}finally{
								DbConnection.closeResultset(rs);
								DbConnection.closePreparedStatement(psLoadChargesData);
							}
							return chargeDetails;
						}
			public static List<AdvancePaymentDTO> loadAdvancePaymentDetails(Connection conn, String csvOrigServices) throws Exception {
							
							ResultSet rs = null;
							String value = null;
							PreparedStatement psLoadAdvPaymentdata=null;
							List<AdvancePaymentDTO> advancePaymentDetailList = null;
							AdvancePaymentDTO advancePaymentDetail = null;
							try{
								psLoadAdvPaymentdata=conn.prepareStatement(sqlGetAdvancePayment.replace("@csvServiceIds", csvOrigServices));
								rs=psLoadAdvPaymentdata.executeQuery();
							while(rs.next()){
								advancePaymentDetail = new AdvancePaymentDTO();
								
								
								value = rs.getString("SPID");
								if(Utility.checkNullValues(value))
									advancePaymentDetail.setSpId(null);
								else
									advancePaymentDetail.setSpId(Integer.valueOf(value));
								
								value = rs.getString("ARCEXEMPTED");
								if(Utility.checkNullValues(value))
									advancePaymentDetail.setArcExempted(null);
								else
									advancePaymentDetail.setArcExempted(value);
								
								value = rs.getString("ARCEXPREASON");
								if(Utility.checkNullValues(value))
									advancePaymentDetail.setArcExpReason(null);
								else
									advancePaymentDetail.setArcExpReason(value);
						
								value = rs.getString("ARCCHQNO");
								if(Utility.checkNullValues(value))
									advancePaymentDetail.setArcChqNo(null);
								else
									advancePaymentDetail.setArcChqNo(value);
								
								value = rs.getString("ARCCHQDATE");
								if(Utility.checkNullValues(value))
									advancePaymentDetail.setArcChqDate(null);
								else
									advancePaymentDetail.setArcChqDate(value);
								
								value = rs.getString("ARCBANKNAME");
								if(Utility.checkNullValues(value))
									advancePaymentDetail.setArcBankName(null);
								else
									advancePaymentDetail.setArcBankName(value);
								
								value = rs.getString("ARCCHQAMT");
								if(Utility.checkNullValues(value))
									advancePaymentDetail.setArcChqAmt(null);
								else
									advancePaymentDetail.setArcChqAmt(Double.valueOf(value));
								
								value = rs.getString("ARCAMTAJD");
								if(Utility.checkNullValues(value))
									advancePaymentDetail.setArcAmtAjd(null);
								else
									advancePaymentDetail.setArcAmtAjd(Double.valueOf(value));
								
								value = rs.getString("OTCEXEMPTED");
								if(Utility.checkNullValues(value))
									advancePaymentDetail.setOtcExempted(null);
								else
									advancePaymentDetail.setOtcExempted(value);
								
								value = rs.getString("OTCEXPREASON");
								if(Utility.checkNullValues(value))
									advancePaymentDetail.setOtcExpReason(null);
								else
									advancePaymentDetail.setOtcExpReason(value);
								
								value = rs.getString("OTCCHQNO");
								if(Utility.checkNullValues(value))
									advancePaymentDetail.setOtcChqNo(null);
								else
									advancePaymentDetail.setOtcChqNo(value);
								
								value = rs.getString("OTCCHQDATE");
								if(Utility.checkNullValues(value))
									advancePaymentDetail.setOtcChqDate(null);
								else
									advancePaymentDetail.setOtcChqDate(value);
								
								value = rs.getString("OTCBANKNAME");
								if(Utility.checkNullValues(value))
									advancePaymentDetail.setOtcBankName(null);
								else
									advancePaymentDetail.setOtcBankName(value);
								
								value = rs.getString("OTCCHQAMT");
								if(Utility.checkNullValues(value))
									advancePaymentDetail.setOtcChqAmt(null);
								else
									advancePaymentDetail.setOtcChqAmt(Double.valueOf(value));
								
								value = rs.getString("OTCAMTAJD");
								if(Utility.checkNullValues(value))
									advancePaymentDetail.setOtcAmtAjd(null);
								else
									advancePaymentDetail.setOtcAmtAjd(Double.valueOf(value));
								
								
								//Start [215]
								//ARCRECHQAMT, ARCRECHQNO, ARCBANKACNO, ARCREBANKACNO, ARCIFSCCODE, ARCREIFSCCODE, OTCRECHQAMT, OTCRECHQNO, OTCBANKACNO, OTCREBANKACNO, OTCIFSCCODE, OTCREIFSCCODE)")

								value = rs.getString("ARCRECHQAMT");
								if(Utility.checkNullValues(value))
									advancePaymentDetail.setArcReEnterCheckamount(null);
								else
									advancePaymentDetail.setArcReEnterCheckamount(Double.valueOf(value));
								

								value = rs.getString("ARCRECHQNO");
								if(Utility.checkNullValues(value))
									advancePaymentDetail.setArcReEnterCheckNumber(null);
								else
									advancePaymentDetail.setArcReEnterCheckNumber(value);
								

								value = rs.getString("ARCBANKACNO");
								if(Utility.checkNullValues(value))
									advancePaymentDetail.setArcBankAccountNumber(null);
								else
									advancePaymentDetail.setArcBankAccountNumber(value);
								
								value = rs.getString("ARCREBANKACNO");
								if(Utility.checkNullValues(value))
									advancePaymentDetail.setArcReEnterBankAccountNumber(null);
								else
									advancePaymentDetail.setArcReEnterBankAccountNumber(value);
								
								value = rs.getString("ARCIFSCCODE");
								if(Utility.checkNullValues(value))
									advancePaymentDetail.setArcIfscCode(null);
								else
									advancePaymentDetail.setArcIfscCode(value);
								
								value = rs.getString("ARCREIFSCCODE");
								if(Utility.checkNullValues(value))
									advancePaymentDetail.setArcReEnterIfscCode(null);
								else
									advancePaymentDetail.setArcReEnterIfscCode(value);
								
								
								
								value = rs.getString("OTCRECHQAMT");
								if(Utility.checkNullValues(value))
									advancePaymentDetail.setOtcReEnterCheckamount(null);
								else
									advancePaymentDetail.setOtcReEnterCheckamount(Double.valueOf(value));
								

								value = rs.getString("OTCRECHQNO");
								if(Utility.checkNullValues(value))
									advancePaymentDetail.setOtcReEnterCheckNumber(null);
								else
									advancePaymentDetail.setOtcReEnterCheckNumber(value);
								

								value = rs.getString("OTCBANKACNO");
								if(Utility.checkNullValues(value))
									advancePaymentDetail.setOtcBankAccountNumber(null);
								else
									advancePaymentDetail.setOtcBankAccountNumber(value);
								
								value = rs.getString("OTCREBANKACNO");
								if(Utility.checkNullValues(value))
									advancePaymentDetail.setOtcReEnterBankAccountNumber(null);
								else
									advancePaymentDetail.setOtcReEnterBankAccountNumber(value);
								
								value = rs.getString("OTCIFSCCODE");
								if(Utility.checkNullValues(value))
									advancePaymentDetail.setOtcIfscCode(null);
								else
									advancePaymentDetail.setOtcIfscCode(value);
								
								value = rs.getString("OTCREIFSCCODE");
								if(Utility.checkNullValues(value))
									advancePaymentDetail.setOtcReEnterIfscCode(null);
								else
									advancePaymentDetail.setOtcReEnterIfscCode(value);
                                 //End [215]
																								
								
								if(null == advancePaymentDetailList)
									advancePaymentDetailList = new ArrayList<AdvancePaymentDTO>();
								advancePaymentDetailList.add(advancePaymentDetail);
							}
							}finally{
								DbConnection.closeResultset(rs);
								DbConnection.closePreparedStatement(psLoadAdvPaymentdata);
							}
							return advancePaymentDetailList;
						}
			public static List<ComponentDTO> loadComponentDetails(Connection conn, String csvOrigServices) throws Exception {
							
							ResultSet rs = null;
							String value = null;
							PreparedStatement psLoadComponentdata=null;
							List<ComponentDTO> componentDetailList = null;
							ComponentDTO componentDetail = null;
							try{
								psLoadComponentdata=conn.prepareStatement(sqlGetComponentInfoDetails.replace("@csvServiceIds", csvOrigServices));
								rs=psLoadComponentdata.executeQuery();
							while(rs.next()){
								componentDetail = new ComponentDTO();
								
								value = rs.getString("ACCOUNTID");
								if(Utility.checkNullValues(value))
									componentDetail.setAccountId(null);
								else
									componentDetail.setAccountId(Long.valueOf(value));
								
								value = rs.getString("SERVICEPRODUCTID");
								if(Utility.checkNullValues(value))
									componentDetail.setServiceProductId(null);
								else
									componentDetail.setServiceProductId(Long.valueOf(value));
						
								value = rs.getString("COMPONENT_ID");
								if(Utility.checkNullValues(value))
									componentDetail.setComponentId(null);
								else
									componentDetail.setComponentId(Long.valueOf(value));
																
								value = rs.getString("PACKAGE_ID");
								if(Utility.checkNullValues(value))
									componentDetail.setPackageId(null);
								else
									componentDetail.setPackageId(Long.valueOf(value));
								
								value = rs.getString("COMPONENT_STATUS");
								if(Utility.checkNullValues(value))
									componentDetail.setComponentStatus(null);
								else
									componentDetail.setComponentStatus(value);
								
								value = rs.getString("CREATEDIN_SERVICEID");
								if(Utility.checkNullValues(value))
									componentDetail.setCreatedInServiceId(null);
								else
									componentDetail.setCreatedInServiceId(Long.valueOf(value));
								
								value = rs.getString("DISCONNECTED_IN_SERVICEID");
								if(Utility.checkNullValues(value))
									componentDetail.setDisconnectedInServiceId(null);
								else
									componentDetail.setDisconnectedInServiceId(Long.valueOf(value));
								
								value = rs.getString("COMPONENT_START_LOGIC");
								if(Utility.checkNullValues(value))
									componentDetail.setComponentStartLogic(null);
								else
									componentDetail.setComponentStartLogic(value);
								
								value = rs.getString("COMPONENT_END_LOGIC");
								if(Utility.checkNullValues(value))
									componentDetail.setComponentEndLogic(null);
								else
									componentDetail.setComponentEndLogic(value);
								
								value = rs.getString("START_DAYS");
								if(Utility.checkNullValues(value))
									componentDetail.setStartDays(null);
								else
									componentDetail.setStartDays(Integer.valueOf(value));
								
								value = rs.getString("START_MONTHS");
								if(Utility.checkNullValues(value))
									componentDetail.setStartMonths(null);
								else
									componentDetail.setStartMonths(Integer.valueOf(value));
								
								value = rs.getString("END_DAYS");
								if(Utility.checkNullValues(value))
									componentDetail.setEndDays(null);
								else
									componentDetail.setEndDays(Integer.valueOf(value));
								
								value = rs.getString("END_MONTHS");
								if(Utility.checkNullValues(value))
									componentDetail.setEndMonths(null);
								else
									componentDetail.setEndMonths(Integer.valueOf(value));
								
								if(null == componentDetailList)
									componentDetailList = new ArrayList<ComponentDTO>();
								componentDetailList.add(componentDetail);
							}
							}finally{
								DbConnection.closeResultset(rs);
								DbConnection.closePreparedStatement(psLoadComponentdata);
							}
							return componentDetailList;
						}
			public static List<SCMDetailsDto> loadSCMDetails(Connection conn, String csvOrigServices) throws Exception {
							
							ResultSet rs = null;
							String value = null;
							PreparedStatement psLoadScmData=null;
							List<SCMDetailsDto> scmDetailsList = null;
							SCMDetailsDto scmDetails = null;
							try{
								psLoadScmData=conn.prepareStatement(sqlGetLineChargeValueSCM.replace("@csvServiceIds", csvOrigServices));
								rs=psLoadScmData.executeQuery();
							while(rs.next()){
								scmDetails = new SCMDetailsDto();
								
								value = rs.getString("ITEM_CODE");
								if(Utility.checkNullValues(value))
									scmDetails.setItemCode_Id(null);
								else
									scmDetails.setItemCode_Id(Integer.valueOf(value));
								
								value = rs.getString("QUANTITY");
								if(Utility.checkNullValues(value))
									scmDetails.setQuntity(null);
								else
									scmDetails.setQuntity(Integer.valueOf(value));

								value = rs.getString("CHARGEVALUE");
								if(Utility.checkNullValues(value))
									scmDetails.setChargeValue(null);
								else
									scmDetails.setChargeValue(Double.valueOf(value));
								
								value = rs.getString("SERVICEPRODUCTID");
								if(Utility.checkNullValues(value))
									scmDetails.setServiceProductId(null);
								else
									scmDetails.setServiceProductId(Long.valueOf(value));
																																																								
								value = rs.getString("DEL_ID");
								if(Utility.checkNullValues(value))
									scmDetails.setDeliveryLocationId(null);
								else
									scmDetails.setDeliveryLocationId(Long.valueOf(value));
								
								value = rs.getString("SUBINV_ID");
								if(Utility.checkNullValues(value))
									scmDetails.setSubInventryId(null);
								else
									scmDetails.setSubInventryId(Integer.valueOf(value));
								
								value = rs.getString("AOP1_ID");
								if(Utility.checkNullValues(value))
									scmDetails.setAop1_Id(null);
								else
									scmDetails.setAop1_Id(Integer.valueOf(value));
								
								value = rs.getString("AOP2_ID");
								if(Utility.checkNullValues(value))
									scmDetails.setAop2_Id(null);
								else
									scmDetails.setAop2_Id(Integer.valueOf(value));
								
								value = rs.getString("IS_ACTIVE");
								if(Utility.checkNullValues(value))
									scmDetails.setIsactive(null);
								else
									scmDetails.setIsactive(Integer.valueOf(value));
								
								value = rs.getString("AOP_YEAR");
								if(Utility.checkNullValues(value))
									scmDetails.setAopYear(null);
								else
									scmDetails.setAopYear(value);
								
								value = rs.getString("PO_NUMBER");
								if(Utility.checkNullValues(value))
									scmDetails.setPo_no(null);
								else
									scmDetails.setPo_no(value);
								
								value = rs.getString("PO_DATE");
								if(Utility.checkNullValues(value))
									scmDetails.setPoDate(null);
								else
									scmDetails.setPoDate(value);
								
								value = rs.getString("PO_AMOUNT");
								if(Utility.checkNullValues(value))
									scmDetails.setPoAmount(null);
								else
									scmDetails.setPoAmount(Double.valueOf(value));
								
								if(null == scmDetailsList)
									scmDetailsList = new ArrayList<SCMDetailsDto>();
								scmDetailsList.add(scmDetails);
							}
							}finally{
								DbConnection.closeResultset(rs);
								DbConnection.closePreparedStatement(psLoadScmData);
							}
							return scmDetailsList;
						}
				public static List<HardwareDetailsDto> loadHardwareDetails(Connection conn,
								String csvOrigServices) throws Exception {

							ResultSet rs = null;
							String value = null;
							PreparedStatement psLoadHardwareData=null;
							List<HardwareDetailsDto> hardwareDetails = null;
							HardwareDetailsDto hardwareDetail = null;
							try{
								psLoadHardwareData=conn.prepareStatement(sqlGetHardwareInfoDetails.replace("@csvServiceIds", csvOrigServices));
								rs=psLoadHardwareData.executeQuery();
							while(rs.next()){
								hardwareDetail = new HardwareDetailsDto();
								
								value = rs.getString("SERVICEPRODUCTID");
								if(Utility.checkNullValues(value))
									hardwareDetail.setServiceProductId(null);
								else
									hardwareDetail.setServiceProductId(Long.valueOf(value));
								
								value = rs.getString("STOREID");
								if(Utility.checkNullValues(value))
									hardwareDetail.setStoreId(null);
								else
									hardwareDetail.setStoreId(Long.valueOf(value));
								
								value = rs.getString("HARDWARETYPE");
								if(Utility.checkNullValues(value))
									hardwareDetail.setHardwareType(null);
								else
									hardwareDetail.setHardwareType(value);
								
								value = rs.getString("FORMAVAILABLE");
								if(Utility.checkNullValues(value))
									hardwareDetail.setFormAvailable(null);
								else
									hardwareDetail.setFormAvailable(value);
								
								value = rs.getString("SALETYPE");
								if(Utility.checkNullValues(value))
									hardwareDetail.setSaleType(null);
								else
									hardwareDetail.setSaleType(value);
								
								value = rs.getString("SALENATURE");
								if(Utility.checkNullValues(value))
									hardwareDetail.setSaleNature(null);
								else
									hardwareDetail.setSaleNature(value);
								
								value = rs.getString("DISPATCHADDCODE");
								if(Utility.checkNullValues(value))
									hardwareDetail.setDispatchAddCode(null);
								else
									hardwareDetail.setDispatchAddCode(Long.valueOf(value));
								
								value = rs.getString("ACCOUNTID");
								if(Utility.checkNullValues(value))
									hardwareDetail.setAccountId(null);
								else
									hardwareDetail.setAccountId(Long.valueOf(value));
								
								value = rs.getString("STARTDATELOGIC");
								if(Utility.checkNullValues(value))
									hardwareDetail.setStartDateLogic(null);
								else
									hardwareDetail.setStartDateLogic(value);
								
								value = rs.getString("ENDDATELOGIC");
								if(Utility.checkNullValues(value))
									hardwareDetail.setEndDateLogic(null);
								else
									hardwareDetail.setEndDateLogic(value);
								
								value = rs.getString("WARRENTYMONTHS");
								if(Utility.checkNullValues(value))
									hardwareDetail.setWarrentyMonths(null);
								else
									hardwareDetail.setWarrentyMonths(Integer.valueOf(value));
																								
								value = rs.getString("PRINCIPAL_AMOUNT");
								if(Utility.checkNullValues(value))
									hardwareDetail.setPrincipalAmount(null);
								else
									hardwareDetail.setPrincipalAmount(Double.valueOf(value));
								
								value = rs.getString("INTEREST_RATE");
								if(Utility.checkNullValues(value))
									hardwareDetail.setInterestRate(null);
								else
									hardwareDetail.setInterestRate(Double.valueOf(value));
								
								value = rs.getString("START_MONTHS");
								if(Utility.checkNullValues(value))
									hardwareDetail.setStartMonths(null);
								else
									hardwareDetail.setStartMonths(Integer.valueOf(value));
								
								value = rs.getString("END_DAYS");
								if(Utility.checkNullValues(value))
									hardwareDetail.setEndDays(null);
								else
									hardwareDetail.setEndDays(Integer.valueOf(value));
								
								value = rs.getString("END_MONTH");
								if(Utility.checkNullValues(value))
									hardwareDetail.setEndMonth(null);
								else
									hardwareDetail.setEndMonth(Integer.valueOf(value));
								
								value = rs.getString("EXT_DAYS");
								if(Utility.checkNullValues(value))
									hardwareDetail.setExtDays(null);
								else
									hardwareDetail.setExtDays(Integer.valueOf(value));
								
								value = rs.getString("EXT_MONTHS");
								if(Utility.checkNullValues(value))
									hardwareDetail.setExtMonths(null);
								else
									hardwareDetail.setExtMonths(Integer.valueOf(value));
								
								value = rs.getString("START_DAYS");
								if(Utility.checkNullValues(value))
									hardwareDetail.setStartDays(null);
								else
									hardwareDetail.setStartDays(Integer.valueOf(value));
								
								value = rs.getString("DISPATCH_CONTACTNAME");
								if(Utility.checkNullValues(value))
									hardwareDetail.setDispatchcontactName(null);
								else
									hardwareDetail.setDispatchcontactName(value);
								
								
								if(null == hardwareDetails)
									hardwareDetails = new ArrayList<HardwareDetailsDto>();
								hardwareDetails.add(hardwareDetail);
							}
							}finally{
								DbConnection.closeResultset(rs);
								DbConnection.closePreparedStatement(psLoadHardwareData);
							}
							return hardwareDetails;
						}
				public static List<LocationDetailsDto> loadLocationDetails(Connection conn,
								String csvOrigServices) throws Exception {
							
							ResultSet rs = null;
							String value = null;
							PreparedStatement psLoadLocationdata=null;
							List<LocationDetailsDto> allLocationDetails = null;
							LocationDetailsDto locationDetail = null;
							try{
								psLoadLocationdata=conn.prepareStatement(sqlGetLocationInfoDetails.replace("@csvServiceIds", csvOrigServices));
								rs=psLoadLocationdata.executeQuery();
							while(rs.next()){
								locationDetail = new LocationDetailsDto();
								
								value = rs.getString("SERVICEPRODUCTID");
								if(Utility.checkNullValues(value))
									locationDetail.setServiceProductId(null);
								else
									locationDetail.setServiceProductId(Long.valueOf(value));
																
								value = rs.getString("PRIMARYLOCATIONTYPE");
								if(Utility.checkNullValues(value))
									locationDetail.setPrimaryLocationType(null);
								else
									locationDetail.setPrimaryLocationType(Long.valueOf(value));
								
								value = rs.getString("PRIMARYLOCATIONID");
								if(Utility.checkNullValues(value))
									locationDetail.setPrimaryLocationId(null);
								else
									locationDetail.setPrimaryLocationId(Long.valueOf(value));
								
								value = rs.getString("SECONDARYLOCATIONTYPE");
								if(Utility.checkNullValues(value))
									locationDetail.setSecondaryLocationType(null);
								else
									locationDetail.setSecondaryLocationType(Long.valueOf(value));
								
								value = rs.getString("SECONDARYLOCATIONID");
								if(Utility.checkNullValues(value))
									locationDetail.setSecondaryLocationId(null);
								else
									locationDetail.setSecondaryLocationId(Long.valueOf(value));
								
								value = rs.getString("ACCOUNTID");
								if(Utility.checkNullValues(value))
									locationDetail.setAccountId(null);
								else
									locationDetail.setAccountId(Long.valueOf(value));
								
								value = rs.getString("TO_ADDRESS");
								if(Utility.checkNullValues(value))
									locationDetail.setToAddress(null);
								else
									locationDetail.setToAddress(value);
								
								value = rs.getString("FROM_ADDRESS");
								if(Utility.checkNullValues(value))
									locationDetail.setFromAddress(null);
								else
									locationDetail.setFromAddress(value);
								
								
								if(null == allLocationDetails)
									allLocationDetails = new ArrayList<LocationDetailsDto>();
								allLocationDetails.add(locationDetail);
							}
							}finally{
								DbConnection.closeResultset(rs);
								DbConnection.closePreparedStatement(psLoadLocationdata);
							}
							return allLocationDetails;
						}
				public static List<BillingDetailsDto> loadBillingDetails(Connection conn,
								String csvOrigServices) throws Exception {
							
							ResultSet rs = null;
							String value = null;
							PreparedStatement psLoadBillingData=null;
							List<BillingDetailsDto> billingDetails = null;
							BillingDetailsDto billingDetail = null;
							try{
								psLoadBillingData=conn.prepareStatement(sqlGetBillingInfoDetails.replace("@csvServiceIds", csvOrigServices));
								rs=psLoadBillingData.executeQuery();
							while(rs.next()){
								
								billingDetail = new BillingDetailsDto();
								
								value = rs.getString("SERVICEPRODUCTID");
								if(Utility.checkNullValues(value))
									billingDetail.setServiceProductId(null);
								else
									billingDetail.setServiceProductId(Long.valueOf(value));
								
								value = rs.getString("ACCOUNTID");
								if(Utility.checkNullValues(value))
									billingDetail.setAccountId(null);
								else
									billingDetail.setAccountId(Long.valueOf(value));
								
								value = rs.getString("CREDITPERIOD");
								if(Utility.checkNullValues(value))
									billingDetail.setCreditPeriod(null);
								else
									billingDetail.setCreditPeriod(Long.valueOf(value));
								
								value = rs.getString("BILLINGMODE");
								if(Utility.checkNullValues(value))
									billingDetail.setBillingMode(null);
								else
									billingDetail.setBillingMode(value);
								
								value = rs.getString("NOTICEPERIOD");
								if(Utility.checkNullValues(value))
									billingDetail.setNoticePeriod(null);
								else
									billingDetail.setNoticePeriod(Long.valueOf(value));
								
								value = rs.getString("BILLINGFORMAT");
								if(Utility.checkNullValues(value))
									billingDetail.setBillingFormat(null);
								else
									billingDetail.setBillingFormat(value);
								
								value = rs.getString("TAXATION");
								if(Utility.checkNullValues(value))
									billingDetail.setTaxation(null);
								else
									billingDetail.setTaxation(value);
								
								value = rs.getString("BILLINGLEVEL");
								if(Utility.checkNullValues(value))
									billingDetail.setBillingLevel(null);
								else
									billingDetail.setBillingLevel(value);
								
								value = rs.getString("COMMITMENTPERIOD");
								if(Utility.checkNullValues(value))
									billingDetail.setCommitmentPeriod(null);
								else
									billingDetail.setCommitmentPeriod(Long.valueOf(value));
								
								value = rs.getString("PENALTYCLAUSE");
								if(Utility.checkNullValues(value))
									billingDetail.setPenaltyClause(null);
								else
									billingDetail.setPenaltyClause(value);
								
								
								value = rs.getString("BILLINGTYPE");
								if(Utility.checkNullValues(value))
									billingDetail.setBillingType(null);
								else
									billingDetail.setBillingType(Long.valueOf(value));
								
								value = rs.getString("BCPID");
								if(Utility.checkNullValues(value))
									billingDetail.setBcpId(null);
								else
									billingDetail.setBcpId(Long.valueOf(value));
								
																
								value = rs.getString("BILLING_LEVEL_NO");
								if(Utility.checkNullValues(value))
									billingDetail.setBillingLevelNo(null);
								else
									billingDetail.setBillingLevelNo(Long.valueOf(value));
								
								value = rs.getString("STDREASION_ID");
								if(Utility.checkNullValues(value))
									billingDetail.setStdReasonId(null);
								else
									billingDetail.setStdReasonId(Long.valueOf(value));
								
								value = rs.getString("LICENCECOID");
								if(Utility.checkNullValues(value))
									billingDetail.setLicenseCoId(null);
								else
									billingDetail.setLicenseCoId(Long.valueOf(value));
								
								value = rs.getString("ENTITYID");
								if(Utility.checkNullValues(value))
									billingDetail.setEntityId(null);
								else
									billingDetail.setEntityId(Long.valueOf(value));
								
								value = rs.getString("IS_NFA");
								if(Utility.checkNullValues(value))
									billingDetail.setIsNFA(null);
								else
									billingDetail.setIsNFA(Integer.valueOf(value));
								
								value = rs.getString("ISUSAGE");
								if(Utility.checkNullValues(value))
									billingDetail.setIsUsage(null);
								else
									billingDetail.setIsUsage(Integer.valueOf(value));
								
								value = rs.getString("WARRANTYCLAUSE");
								if(Utility.checkNullValues(value))
									billingDetail.setWarrantyClause(null);
								else
									billingDetail.setWarrantyClause(value);
								//satyapan By nagarjuna
								value = rs.getString("IS_OSP");
								if(Utility.checkNullValues(value))
									billingDetail.setIsOSPTagging(null);
								else
									billingDetail.setIsOSPTagging(Integer.valueOf(value));
								
								value = rs.getString("OSP_REG_NO");
								if(Utility.checkNullValues(value))
									billingDetail.setTxtOSPRegNo(null);
								else
									billingDetail.setTxtOSPRegNo(value);
								
								
								value = rs.getString("OSP_REG_DATE");
								if(Utility.checkNullValues(value))
									billingDetail.setTxtOSPRegDate(null);
								else
									billingDetail.setTxtOSPRegDate(rs.getDate("OSP_REG_DATE"));
																
								////satyapan By nagarjuna
								value = rs.getString("SERVICE_BCPID");
								if(Utility.checkNullValues(value))
									billingDetail.setServiceBcpId(null);
								else
									billingDetail.setServiceBcpId(Long.valueOf(value));
								
								value = rs.getString("PODETAILID");
								if(Utility.checkNullValues(value))
									billingDetail.setPoDetailId(null);
								else
									billingDetail.setPoDetailId(Long.valueOf(value));
								
								if(null == billingDetails)
									billingDetails = new ArrayList<BillingDetailsDto>();
								billingDetails.add(billingDetail);
							}
							}finally{
								DbConnection.closeResultset(rs);
								DbConnection.closePreparedStatement(psLoadBillingData);
							}
							return billingDetails;
						}
				public static List<ServiceSummarryAttributesDto> loadServiceSummarryAttributes(
								Connection conn, String csvOrigServices) throws Exception {
							
							ResultSet rs = null;
							String value = null;
							PreparedStatement psLoadServiceSummarry=null;
							List<ServiceSummarryAttributesDto> allServiceSummaryAttributes = null;
							ServiceSummarryAttributesDto serviceSummaryAttributes = null;
							
							try{
								psLoadServiceSummarry=conn.prepareStatement(sqlGetLineAttributesDetails.replace("@csvServiceIds", csvOrigServices));
								rs=psLoadServiceSummarry.executeQuery();
								
							while(rs.next()){
								serviceSummaryAttributes = new ServiceSummarryAttributesDto();
								
								value = rs.getString("ATTVALUEID");
								if(Utility.checkNullValues(value))
									serviceSummaryAttributes.setAttValueId(null);
								else
									serviceSummaryAttributes.setAttValueId(Long.valueOf(value));
								
								value = rs.getString("ATTMASTERID");
								if(Utility.checkNullValues(value))
									serviceSummaryAttributes.setAttMasterId(null);
								else
									serviceSummaryAttributes.setAttMasterId(Long.valueOf(value));
											
								value = rs.getString("ATTVALUE");
								if(Utility.checkNullValues(value))
									serviceSummaryAttributes.setAttValue(null);
								else
									serviceSummaryAttributes.setAttValue(value);
								
								value = rs.getString("ATTVALUE_NEW");
								if(Utility.checkNullValues(value))
									serviceSummaryAttributes.setAttValue_New(null);
								else
									serviceSummaryAttributes.setAttValue_New(value);
								
								value = rs.getString("ATTDESCRIPTION");
								if(Utility.checkNullValues(value))
									serviceSummaryAttributes.setAttDescription(null);
								else
									serviceSummaryAttributes.setAttDescription(value);
								
								value = rs.getString("SERVICEPRODUCTID");
								if(Utility.checkNullValues(value))
									serviceSummaryAttributes.setServiceProductId(null);
								else
									serviceSummaryAttributes.setServiceProductId(Long.valueOf(value));
								
								if(null == allServiceSummaryAttributes)
									allServiceSummaryAttributes = new ArrayList<ServiceSummarryAttributesDto>();
								allServiceSummaryAttributes.add(serviceSummaryAttributes);
							}
							}finally{
								DbConnection.closeResultset(rs);
								DbConnection.closePreparedStatement(psLoadServiceSummarry);
							}
							return allServiceSummaryAttributes;
						}

				public static List<LineItemDto> loadLineItems(Connection conn,
								String csvOrigServices) throws Exception {

							ResultSet rs = null;
							String value = null;
							PreparedStatement psLoadLinesData=null;
							List<LineItemDto> lineItems = null;
							LineItemDto lineItem = null;
							try{
								psLoadLinesData=conn.prepareStatement(sqlGetLineDetails.replace("@csvServiceIds", csvOrigServices));
								rs=psLoadLinesData.executeQuery();
							while(rs.next()){
								lineItem = new LineItemDto();
																					
								value = rs.getString("SERVICEID");
								if(Utility.checkNullValues(value))
									lineItem.setServiceId(null);
								else
									lineItem.setServiceId(Long.valueOf(value));
								
								value = rs.getString("SERVICEDETAILID");
								if(Utility.checkNullValues(value))
									lineItem.setServiceDetailId(null);
								else
									lineItem.setServiceDetailId(Long.valueOf(value));
								
								value = rs.getString("SERVICEPRODUCTID");
								if(Utility.checkNullValues(value))
									lineItem.setServiceProductId(null);
								else
									lineItem.setServiceProductId(Long.valueOf(value));
								
								value = rs.getString("PARENT_SERVICEPRODUCTID");
								if(Utility.checkNullValues(value))
									lineItem.setParentServiceProductId(null);
								else
									lineItem.setParentServiceProductId(Long.valueOf(value));
																																
								value = rs.getString("LOGICALCIRCUITID");
								if(Utility.checkNullValues(value))
									lineItem.setLogicalCircuitId(null);
								else
									lineItem.setLogicalCircuitId(value);
								
								value = rs.getString("INFRAORDERNO");
								if(Utility.checkNullValues(value))
									lineItem.setInfraOrderNo(null);
								else
									lineItem.setInfraOrderNo(value);
								
								value = rs.getString("METASOLVCIRCUITID");
								if(Utility.checkNullValues(value))
									lineItem.setMetasolvCircuitId(null);
								else
									lineItem.setMetasolvCircuitId(value);
								
								value = rs.getString("CONFIG_ID");
								if(Utility.checkNullValues(value))
									lineItem.setConfigId(null);
								else
									lineItem.setConfigId(Long.valueOf(value));
								
								value = rs.getString("ISDUMMY");
								if(Utility.checkNullValues(value))
									lineItem.setIsDummy(null);
								else
									lineItem.setIsDummy(Integer.valueOf(value));  //fix an issue. should be setIsDummy() rather than setConfigId()
								
								if(null == lineItems)
									lineItems = new ArrayList<LineItemDto>();
								lineItems.add(lineItem);
							}
							}finally{
								DbConnection.closeResultset(rs);
								DbConnection.closePreparedStatement(psLoadLinesData);
							}
							return lineItems;
						}


				public static List<ServiceProductAttributeDto> loadServiceProductAttributeData(
								Connection conn, String csvOrigServices) throws Exception {
								PreparedStatement psLoadPrdAttribute=null;
								ResultSet rs = null;
								String value = null;
								List<ServiceProductAttributeDto> lsServiceProdAtt = null;
								ServiceProductAttributeDto serviceProductAttributeDto = null;
								try{
									psLoadPrdAttribute=conn.prepareStatement(sqlGetServiceAttributesDetails.replace("@csvServiceIds", csvOrigServices));
									rs=psLoadPrdAttribute.executeQuery();
								while(rs.next()){
									serviceProductAttributeDto = new ServiceProductAttributeDto();
									value = rs.getString("LABELATTID");
									if(Utility.checkNullValues(value))
										serviceProductAttributeDto.setLabelAttId(null);
									else
										serviceProductAttributeDto.setLabelAttId(value);
									
									value = rs.getString("LABELATTVALUE");
									if(Utility.checkNullValues(value))
										serviceProductAttributeDto.setLabelAttValue(null);
									else
										serviceProductAttributeDto.setLabelAttValue(value);
																		
									value = rs.getString("SERVICEDETAILID");
									if(Utility.checkNullValues(value))
										serviceProductAttributeDto.setServiceDetailId(null);
									else
										serviceProductAttributeDto.setServiceDetailId(Long.valueOf(value));
									
									if(null == lsServiceProdAtt)
										lsServiceProdAtt = new ArrayList<ServiceProductAttributeDto>();
									lsServiceProdAtt.add(serviceProductAttributeDto);
								}
								}finally{
									DbConnection.closeResultset(rs);
									DbConnection.closePreparedStatement(psLoadPrdAttribute);
								}
							return lsServiceProdAtt;
						}

				public static List<ServiceDto> loadServicesData(Connection conn,
						String csvOrigServices) throws Exception {
					List<ServiceDto> services = null;
					ServiceDto serviceDetail = null;
					//getting service level details
					ResultSet rs = null;
					String value = null;
					PreparedStatement psLoadServices=null;
					try{
					psLoadServices=conn.prepareStatement(sqlGetServiceDetails.replaceFirst("@csvServiceIds", csvOrigServices));
					rs=psLoadServices.executeQuery();
					
					while(rs.next()){
						//if(null == serviceDetail)
							serviceDetail = new ServiceDto();
						
						value = rs.getString("SERVICEID");
						if(Utility.checkNullValues(value))
							serviceDetail.setServiceId(null);
						else
							serviceDetail.setServiceId(Long.valueOf(value));
						
						value = rs.getString("ACCOUNTID");
						if(Utility.checkNullValues(value))
							serviceDetail.setAccountId(null);
						else
							serviceDetail.setAccountId(Long.valueOf(value));
						
						value = rs.getString("ORDERNO");
						if(Utility.checkNullValues(value))
							serviceDetail.setOrderNo(null);
						else
							serviceDetail.setOrderNo(Long.valueOf(value));
						
						value = rs.getString("SERVICETYPEID");
						if(Utility.checkNullValues(value))
							serviceDetail.setServicetypeId(null);
						else
							serviceDetail.setServicetypeId(Long.valueOf(value));
						
						value = rs.getString("SERVICESTAGE");
						if(Utility.checkNullValues(value))
							serviceDetail.setServiceStage(null);
						else
							serviceDetail.setServiceStage(value);
						
						value = rs.getString("EFFSTARTDATE");
						if(Utility.checkNullValues(value))
							serviceDetail.setEffectiveStartDate(null);
						else
							serviceDetail.setEffectiveStartDate(rs.getDate("EFFSTARTDATE"));
						
						value = rs.getString("CUSTLOGICALSINO");
						if(Utility.checkNullValues(value))
							serviceDetail.setCustLogicalSiNo(null);
						else
							serviceDetail.setCustLogicalSiNo(value);
						
						value = rs.getString("EFFENDDATE");
						if(Utility.checkNullValues(value))
							serviceDetail.setEffectiveEndDate(null);
						else
							serviceDetail.setEffectiveEndDate(rs.getDate("EFFENDDATE"));
						
						value = rs.getString("PROVISIONINGPLAN");
						if(Utility.checkNullValues(value))
							serviceDetail.setProvisionPlan(null);
						else
							serviceDetail.setProvisionPlan(value);
						
						value = rs.getString("REMARKS");
						if(Utility.checkNullValues(value))
							serviceDetail.setRemarks(null);
						else
							serviceDetail.setRemarks(value);
						
						value = rs.getString("SERVICESUBSTYPEID");
						if(Utility.checkNullValues(value))
							serviceDetail.setServiceSubtypeId(null);
						else
							serviceDetail.setServiceSubtypeId(Long.valueOf(value));
						
						value = rs.getString("PREORDERNO");
						if(Utility.checkNullValues(value))
							serviceDetail.setPreOrderNo(null);
						else
							serviceDetail.setPreOrderNo(value);
						
						value = rs.getString("OLDCUSTLOGICALSINO");
						if(Utility.checkNullValues(value))
							serviceDetail.setOldCustLogicalSiNo(null);
						else
							serviceDetail.setOldCustLogicalSiNo(value);												
						
						value = rs.getString("CHANGE_TYPE");
						if(Utility.checkNullValues(value))
							serviceDetail.setChangeType(null);
						else
							serviceDetail.setChangeType(Long.valueOf(value));												
						
						value = rs.getString("CREATED_DATE");
						if(Utility.checkNullValues(value))
							serviceDetail.setCreatedDate(null);
						else
							serviceDetail.setCreatedDate(rs.getDate("CREATED_DATE"));
						
						value = rs.getString("MODIFIED_DATE");
						if(Utility.checkNullValues(value))
							serviceDetail.setModifiedDate(null);
						else
							serviceDetail.setModifiedDate(rs.getDate("MODIFIED_DATE"));
						
						value = rs.getString("RFS_DATE");
						if(Utility.checkNullValues(value))
							serviceDetail.setRfsDate(null);
						else
							serviceDetail.setRfsDate(rs.getDate("RFS_DATE"));
						
						value = rs.getString("RFS_DATE");
						if(Utility.checkNullValues(value))
							serviceDetail.setRfsDate(null);
						else
							serviceDetail.setRfsDate(rs.getDate("RFS_DATE"));
						
						value = rs.getString("PRODUCTID");
						if(Utility.checkNullValues(value))
							serviceDetail.setProductId(null);
						else
							serviceDetail.setProductId(Long.valueOf(value));
						
						value = rs.getString("SUBPRODUCTID");
						if(Utility.checkNullValues(value))
							serviceDetail.setSubProductId(null);
						else
							serviceDetail.setSubProductId(Long.valueOf(value));
						
						value = rs.getString("SUBCHANGETYPEID");
						if(Utility.checkNullValues(value))
							serviceDetail.setSubChangeTypeId(null);
						else
							serviceDetail.setSubChangeTypeId(Long.valueOf(value));
						
						value = rs.getString("IS_DEMO");
						if(Utility.checkNullValues(value))
							serviceDetail.setIsDemo(null);
						else
							serviceDetail.setIsDemo(Integer.valueOf(value));
						
						value = rs.getString("BUNDLED");
						if(Utility.checkNullValues(value))
							serviceDetail.setBundled(null);
						else
							serviceDetail.setBundled(value);
						
						value = rs.getString("DOWNTIME_CLAUSE");
						if(Utility.checkNullValues(value))
							serviceDetail.setDowntimeClause(null);
						else
							serviceDetail.setDowntimeClause(value);
						
						value = rs.getString("IS_SERVICE_VALIDATED");
						if(Utility.checkNullValues(value))
							serviceDetail.setIsServiceValidated(null);
						else
							serviceDetail.setIsServiceValidated(Integer.valueOf(value));
																								
						//[217] Start
						value = rs.getString("SERVICE_FLAVOUR");
						if(Utility.checkNullValues(value))
							serviceDetail.setServiceFlavor(null);
						else
							serviceDetail.setServiceFlavor(value);
						
						value = rs.getString("CKT_TYPE");
						if(Utility.checkNullValues(value))
							serviceDetail.setCircuitType(null);
						else
							serviceDetail.setCircuitType(value);
							
						//[217] End
						if(null == services)
							services = new ArrayList<ServiceDto>();
						
						services.add(serviceDetail);
					}
				}finally{
					DbConnection.closeResultset(rs);
					DbConnection.closePreparedStatement(psLoadServices);
				}
					return services;
			}
				
				
			public static Set<Long> loadThirdPartyServiceDetailId(Connection conn) throws Exception {
				Set<Long> setThirdPartyServiceDetailId = new HashSet<Long>();
				ResultSet rs = null;
				PreparedStatement psLoadThirdParty=null;
				try{
					psLoadThirdParty=conn.prepareStatement(sqlGetThirdPartyServiceDetailId);
					rs=psLoadThirdParty.executeQuery();
				
					if(rs.next()){
						String value = rs.getString("THIRD_PARTY_SERVICE_DETAILID");
						if(! Utility.checkNullValues(value)){
							StringTokenizer st = new StringTokenizer(value, ",");
							while(st.hasMoreElements()) {
								setThirdPartyServiceDetailId.add(Long.valueOf(st.nextToken()));
							}
						}
					}
				}finally{
					DbConnection.closeResultset(rs);
					DbConnection.closePreparedStatement(psLoadThirdParty);
				}
				return setThirdPartyServiceDetailId;
			}
				
				
				/**
				 * @author Vijay
				 * @param conn
				 * @param csvServiceIds
				 * @param nos
				 * @param targetOrderNo 
				 * @return A Map object. This map contains reserved  ids for Service, LSI and Lines. These reserved 
				 * 			Ids are store as a value in the form of List object.  
				 */
				public static HashMap<Object, List<Long>> reserveObjectsIdsInDB(Connection conn, List<Long> csvServiceIds, List<Integer> nos, Long targetOrderNo) throws Exception {
						/*Reserved ids for TposerviceMaster
						 *reserved ids for LSI
						 *reserved ids for TposerviceDetails
						 */
						
						String serviceIncrementQuery = "INSERT INTO IOE.TPOSERVICEMASTER(ORDERNO,LOGICAL_SI_NO) VALUES(?,0)";
						String lsiIncrementQuery = "SELECT (NEXTVAL for IOE.SEQ_LOGICAL_SI_NO) as LSI_NO from sysibm.sysdummy1";
						String spidIncrementQuery = "INSERT INTO IOE.TPOSERVICEDETAILS(SERVICEID) VALUES(?)";
						
						/*String deleteServiceQuery = "delete from IOE.TPOSERVICEMASTER where ORDERNO=? and LOGICAL_SI_NO=0";
						String deleteSpIdQuery = "delete from IOE.TPOSERVICEDETAILS where serviceid=?";*/
								
						
						String sqlGetGeneratedServiceId="SELECT SERVICEID FROM IOE.TPOSERVICEMASTER WHERE ORDERNO=? AND LOGICAL_SI_NO=0";
						String sqlGetGeneratedSPID="SELECT SERVICEPRODUCTID FROM IOE.TPOSERVICEDETAILS WHERE SERVICEID=?";
						
						HashMap<Object, List<Long>> reservedIdsMap = new HashMap<Object, List<Long>>();
						PreparedStatement pstmForService =null;
						PreparedStatement pstmforLsi =null;
						PreparedStatement pstmForLine = null;
						ResultSet rs = null;
						
						/*PreparedStatement psmtForServiceDelete = null;
						PreparedStatement psmtForSpIdDelete = null;*/
						
						int sumOfServicesCount = 0;
						ArrayList<Long> reservedServiceIds = new ArrayList<Long>();
						ArrayList<Long> reservedLsiIds = new ArrayList<Long>();
						ArrayList<Long> reservedLineIds = new ArrayList<Long>();
							
						try
						{
							Long[] services=new Long[csvServiceIds.size()];							
							services=csvServiceIds.toArray(services);
							
							Integer[] noOfservices= new Integer[nos.size()];
							noOfservices=nos.toArray(noOfservices);
							
							/*START===================[CREATE RESERVED SERVICE IDS]===========================START*/
							
							/*get the total count for copy for all selected services*/
							for(int z = 0; z < nos.size(); z++)
						    {
						    	sumOfServicesCount += nos.get(z);
						    }
							/* inserting row to tposervicemaster table to create serviceid identity*/
							pstmForService= conn.prepareStatement(serviceIncrementQuery);
							pstmForService.setLong(1, targetOrderNo);
							for(int i=0;i<sumOfServicesCount;i++){
								pstmForService.addBatch();
							}
							pstmForService.executeBatch();
							pstmForService.close();
							
							/*psmtForServiceDelete = conn.prepareStatement(deleteServiceQuery);
							psmtForServiceDelete.setLong(1, targetOrderNo);*/
							
							/*fetch created service ids and store all to the list*/
							pstmForService= conn.prepareStatement(sqlGetGeneratedServiceId);
							pstmForService.setLong(1, targetOrderNo);
							
							rs=pstmForService.executeQuery();
							while(rs.next()){
								reservedServiceIds.add(rs.getLong("SERVICEID"));
							}
							rs.close();
							/*END===================[CREATE RESERVED SERVICE IDS]===========================END*/
							
							/*START===================[CREATE RESERVED LOGICAL SI IDS]===========================START*/
								pstmforLsi = conn.prepareStatement(lsiIncrementQuery);
								for(int i=0;i<sumOfServicesCount;i++){					
									rs=pstmforLsi.executeQuery();
									if(rs.next()){
										reservedLsiIds.add(rs.getLong("LSI_NO"));
									}
									rs.close();
								}
							/*END==================[CREATE RESERVED LOGICAL SI IDS]==============================END*/
							
							/*START===================[CREATE RESERVED LINEITEM IDS]===========================START*/
								Integer totalLinesCount=NewOrderModel.getCountTotalLines(conn, services, noOfservices);
								pstmForLine = conn.prepareStatement(spidIncrementQuery);
								Long genServiceId=reservedServiceIds.get(0);
								pstmForLine.setLong(1,genServiceId);
								for(int i=0;i<totalLinesCount;i++){					
									pstmForLine.addBatch();
								}
								pstmForLine.executeBatch();
								pstmForLine.close();
								rs.close();
								
								pstmForLine = conn.prepareStatement(sqlGetGeneratedSPID);
								pstmForLine.setLong(1, genServiceId);
								rs=pstmForLine.executeQuery();
								while(rs.next()){
									reservedLineIds.add(rs.getLong("SERVICEPRODUCTID"));
								}
								rs.close();
								
								/*psmtForSpIdDelete=conn.prepareStatement(deleteSpIdQuery);
								psmtForSpIdDelete.setLong(1,genServiceId);*/
								
								
							/*END===================[CREATE RESERVED LINEITEM IDS]===========================END*/
							
							/*set the reserved Ids list into Map */
							reservedIdsMap.put(RESERVED.SERVICEID, reservedServiceIds);
							reservedIdsMap.put(RESERVED.LSI, reservedLsiIds);
							reservedIdsMap.put(RESERVED.SERVICEPRODUCTID, reservedLineIds);
							
							
							/*psmtForSpIdDelete.execute();
							psmtForServiceDelete.execute();*/
							
						}
						finally
						{								
								DbConnection.closeResultset(rs);
								DbConnection.closePreparedStatement(pstmForLine);
								DbConnection.closePreparedStatement(pstmForService);
								DbConnection.closePreparedStatement(pstmforLsi);										
						}
						return reservedIdsMap;
					}
				
				public static String getOrderType(Connection conn, Long orderNo)throws Exception{
					ResultSet rs = null;
					PreparedStatement psGetOrderType=null;
					String orderType = null;
					try{
						psGetOrderType=conn.prepareStatement(CopyOrderSqlDeclaration.getOrderType());
						psGetOrderType.setLong(1, orderNo);
						rs=psGetOrderType.executeQuery();
						if(rs.next()){
							orderType = rs.getString("ORDERTYPE");
						}
						
					}
					finally{
						DbConnection.closeResultset(rs);
						DbConnection.closePreparedStatement(psGetOrderType);
					}
					return orderType;
				}
				public static HashMap<Long, Long> getOldPoAndNewPoMapping(Connection conn, Long orderNo) throws Exception{
					ResultSet rs = null;
					PreparedStatement psGetPoDetail=null;
					HashMap<Long, Long> mapObj = new HashMap<Long, Long>();
					try{
						psGetPoDetail=conn.prepareStatement(CopyOrderSqlDeclaration.getOldPoAndNewPoMapping());
						psGetPoDetail.setLong(1, orderNo);
						rs=psGetPoDetail.executeQuery();
					
						while(rs.next()){
							mapObj.put(rs.getLong("OldPoDetailNo"), rs.getLong("NewPoDetailNo")) ;
						}
					}finally{
						DbConnection.closeResultset(rs);
						DbConnection.closePreparedStatement(psGetPoDetail);
					}
					return mapObj;
				}
				/**
				 * This method is use from createOrderwithExistingOrder,to find out source order have zone or not.
				 * @param conn
				 * @param orderNo
				 * @return
				 * @throws Exception
				 */
				public static int getNotAvailableZoneCount(Connection conn, Long orderNo) throws Exception{
					int notAvailableZoneCount = 0;
					ResultSet rs = null;
					PreparedStatement psGetNaZone=null;
					try{
						psGetNaZone=conn.prepareStatement(CopyOrderSqlDeclaration.getNotAvailableZoneCount());
						psGetNaZone.setLong(1, orderNo);
						rs=psGetNaZone.executeQuery();
					
						if(rs.next()){
							notAvailableZoneCount = rs.getInt("NA_REGION_ZONE_ORDER");
						}
					}finally{
						DbConnection.closeResultset(rs);
						DbConnection.closePreparedStatement(psGetNaZone);
					}
					return notAvailableZoneCount;
				}
				
				public static int getMigratedServiceCount(Connection conn, Long orderNo) throws Exception{
					int migratedServiceCount = 0;
					ResultSet rs = null;
					PreparedStatement psGetMigratedService=null;
					try{
						psGetMigratedService=conn.prepareStatement(CopyOrderSqlDeclaration.getMigratedServiceCount());
						psGetMigratedService.setLong(1, orderNo);
						rs=psGetMigratedService.executeQuery();
					
						if(rs.next()){
							migratedServiceCount = rs.getInt("IS_MIGRATED_NEW_PROD");
						}
					}finally{
						DbConnection.closeResultset(rs);
						DbConnection.closePreparedStatement(psGetMigratedService);
					}
					return migratedServiceCount;
				}
				
				public static List<Long> getServiceList(Connection conn, Long orderNo) throws Exception{
					List<Long> serviceIdList = new ArrayList<Long>();
					ResultSet rs = null;
					PreparedStatement psGetServiceList=null;
					try{
						psGetServiceList=conn.prepareStatement(CopyOrderSqlDeclaration.getServiceList());
						psGetServiceList.setLong(1, orderNo);
						rs=psGetServiceList.executeQuery();
					
						while(rs.next()){
							serviceIdList.add(rs.getLong("SERVICEID"));
						}
					}finally{
						DbConnection.closeResultset(rs);
						DbConnection.closePreparedStatement(psGetServiceList);
					}
					return serviceIdList;
				}
				
				public static Long getNextOrderNoThroughSeq(Connection conn) throws Exception {
					long nextOrderNo = -1l;
					ResultSet rs = null;
					PreparedStatement psGetOrderNo=null;
					try{
						psGetOrderNo=conn.prepareStatement(CopyOrderSqlDeclaration.getNextOrderNo());
						rs=psGetOrderNo.executeQuery();
					
						if(rs.next()){
							nextOrderNo = rs.getLong("NEXT_ORDER_NO");
						}
					}finally{
						DbConnection.closeResultset(rs);
						DbConnection.closePreparedStatement(psGetOrderNo);
					}
					return nextOrderNo;
				}
	}		
