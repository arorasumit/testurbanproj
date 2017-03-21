package com.ibm.ioes.model;

//Tag Name 	Resource Name  	Date		CSR No						Description
//[001]	 	VIPIN SAHARIA	13-Apr-15	CBR_20150202-R2-021036		Sending ePCD and SMS alerts to customer at every order life cycle stage

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibm.ioes.dao.WelcomeMailSMSDao;
import com.ibm.ioes.newdesign.dto.WelcomeMailSMSDTO;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.SMSUtil;
import com.ibm.ioes.utilities.SendMail;
import com.ibm.ioes.utilities.Utility;

public class WelcomeMailSMSModel {
	
	
	private static final String COPC_EVENT="COPC";
	private static final String BT_EVENT="BT";
	private static final int TSM_EXT_INIT=1;
	private static final int TSM_EXT_TO_PROCESS=2;
	private static final int TSM_EXT_PROCESSED=3;
	private static final int TSM_EXT_NOT_ELIGIBLE=13;
	public static final int TSM_EXT_TEMP_ELIGIBLE=23;
	private static final String PS_INIT="INIT";
	private static final String PS_TO_PROCESS="TO_PROCESS";
	private static final String PS_PROCESSED="PROCESSED";
	private static final String PS_INIT_WIP="INIT_WIP";
	private static final int MAIL_FLAG=1;
	private static final int SMS_FLAG=2;
	private static final String SUCCESS="SUCCESS";
	private static final String FAILURE="FAILURE";
	private static final String SMS_PARTIAL="PARTIAL";
	
	/**
	 * To initiate process for finding eligible cust alert mail/SMS services and line items. Mark them TO_PROCESS in ORDER_LIFECYCLE_EVENT Table.
	 * @return void
	 * @throws Exception
	 * @author Vipin Saharia
	 * @date 03-Apr-2015
	 */
	public void filterAndComputeEligibleDataForAlert()throws Exception{
		String msg="In WelcomeMailSMSModel's filterAndComputeEligibleDataForAlert method";
			findAndInsertEligibleCOPCBTePCDalertsServices(COPC_EVENT);
			findAndInsertEligibleCOPCBTePCDalertsServices(BT_EVENT);
			WelcomeMailSMSDao daoObj = new WelcomeMailSMSDao();
			Connection conn=null;
			try{
				conn=DbConnection.getConnectionObject();
				daoObj.markServicePreStatus(conn,PS_INIT,PS_INIT_WIP,"'"+COPC_EVENT+"'");
				this.findEligibleLineEpcdAlerts(conn,COPC_EVENT);
				
				daoObj.markServicePreStatus(conn,PS_INIT,PS_INIT_WIP,"'"+BT_EVENT+"'");
				this.findEligibleLineEpcdAlerts(conn,BT_EVENT);
				
				//Marking INIT_WIP to TO_PROCESS for mail & SMS drafting
				daoObj.markServicePreStatus(conn,PS_INIT_WIP,PS_TO_PROCESS,"'"+COPC_EVENT+"','"+BT_EVENT+"'");
			}catch(Exception e){
				Utility.LOG(true, true, e, msg);
				//System.out.println(e);
				//conn.rollback();
				throw new Exception();
			}finally{
				try{
					DbConnection.freeConnection(conn);
				}catch (Exception e){
					e.printStackTrace();
				}
			}
	}
	
	/**
	 * To fetch next 500 eligible cust alert mail/SMS services with event(COPC/BT). Mark them PROCESS_WIP from TO_PROCESS before process in ORDER_LIFECYCLE_EVENT Table.
	 * @return void
	 * @throws Exception
	 * @author Vipin Saharia
	 * @date 03-Apr-2015
	 */
	public void createAndSendAlert()throws Exception{
		//This will fetch next 500 distinct orders and event from order_lifecycle_event table and firmly call draft&send method for each row.
		String msg="In WelcomeMailSMSModel's createAndSendAlert method";
		WelcomeMailSMSDao daoObj = new WelcomeMailSMSDao();
		ArrayList<WelcomeMailSMSDTO> orderList=null;
		try{
			String copcMailFlag=Utility.getAppConfigValue("ePCD_MAIL_ALERT_COPC");
			String copcSMSFlag=Utility.getAppConfigValue("ePCD_SMS_ALERT_COPC");
			String btMailFlag=Utility.getAppConfigValue("ePCD_MAIL_ALERT_BT");
			String btSMSFlag=Utility.getAppConfigValue("ePCD_SMS_ALERT_BT");
			do{
				orderList= daoObj.fetchNextePCDMailSMSAlertOrders();
				if(null!=orderList){
					for(WelcomeMailSMSDTO obj : orderList){
						//result=daoObj.updatePreStatusBeforeDraftingMailSMS(conn,obj.getOrderNo(),obj.getEvent());
						sendePCDMailSMSAlertAfterCOPCAndBT(obj.getOrderNo(),obj.getEvent(),copcMailFlag,copcSMSFlag,btMailFlag,btSMSFlag);
					}
				}
			}while(orderList.size()!=0);
		}catch(Exception ex){	
			Utility.LOG(true, true, ex, msg);
			throw new Exception();
		}
	}
	
	/**
	* To process Services and insert services into ORDER_LIFECYCLE_EVENT table for COPC/BT event regarding ePCD mail SMS alerts
	* @return boolean
	* @param String
	* @throws Exception
	* @author Vipin Saharia
	* @date 30-Mar-2015
	*/
	public boolean findAndInsertEligibleCOPCBTePCDalertsServices(String event) throws Exception{
		String msg="In WelcomeMailSMSModel's findAndInsertEligibleCOPCePCDalertsServices method";
		WelcomeMailSMSDao daoObj = new WelcomeMailSMSDao();
		Connection conn=null;
		boolean result = true;
		try{	
			conn=DbConnection.getConnectionObject();
			conn.setAutoCommit(false);
			//STEP 1: Update BT/COPC_CUST_NOTIFY Flag Col in TPOSERVICEMASTER_EXTENDED from 1 to 2 to process it
			daoObj.updateflagTSMExtendedTable(conn,TSM_EXT_INIT, TSM_EXT_TO_PROCESS, event);
			conn.commit();
			
			
			
			/*//STEP 2: Mark BT/COPC_CUST_NOTIFY to 13 for non channel services for COPC event
			if(result)
				result=daoObj.markNonChannelServices(conn,event);
			//STEP 3: Mark BT/COPC_CUST_NOTIFY to 13 for (non ILP/MPS service attribute value) containing services for COPC/BT event
			if(result)
				result=daoObj.markNonILPMPLSServices(conn,event);
			//STEP 4: Mark BT/COPC_CUST_NOTIFY to 13 for (non pure ANG + non Bandwidth) containing lines services for COPC/BT event
			if(result)
				result=daoObj.markNonANGAndBandwidthServices(conn, event);*/
			
			
			/*STEP : Read Elgible services where 
			 *  Account's Segment is Channel
			 *  ILP/MPS service attribute value
			 *  (pure ANG + Bandwidth) both lines present in service
			 */
			List<Long> eligibleServiceIds = daoObj.readValidServicesForEPCDAlert(conn,event,TSM_EXT_TO_PROCESS);
			
			/**
			 * Mark elgible services to a temporary flag : TSM_EXT_TEMP_ELIGIBLE
			 */
			daoObj.markEligibleForEPCDAlert(conn,eligibleServiceIds,event,TSM_EXT_TEMP_ELIGIBLE);
			conn.commit();

			//Insert temporary eligible services to ORDER_LIFECYCLE_EVENT table for BT/COPC event
			daoObj.insertIntoOrderLifeCycleEvent(conn, event);

			/**
			 * Mark Rest services as Not eligible
			 */
			daoObj.updateflagTSMExtendedTable(conn, TSM_EXT_TO_PROCESS, TSM_EXT_NOT_ELIGIBLE, event);
			/**
			 * Mark temporary eligible services as Processed
			 */
			daoObj.updateflagTSMExtendedTable(conn, TSM_EXT_TEMP_ELIGIBLE, TSM_EXT_PROCESSED, event);

			conn.commit();
			

			
		}catch(Exception ex){	
			Utility.LOG(true, true, ex, msg);
			conn.rollback();
			throw new Exception();
		}finally{
			try{
				DbConnection.freeConnection(conn);
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		return result;
	}
	

	/**
	* To find eligible line item of INIT_WIP services and update into ORDER_LIFECYCLE_EVENT table for COPC/BT event regarding ePCD mail SMS alerts
	* @return boolean
	* @param Connection
	* @param String
	* @author Vipin Saharia
	* @date 30-Mar-2015
	*/
	public boolean findEligibleLineEpcdAlerts(Connection oldConn,String event) throws Exception{
		
		String msg="In WelcomeMailSMSModel's findEligibleLineEpcdAlerts method";
		WelcomeMailSMSDao daoObj = new WelcomeMailSMSDao();
		ArrayList<WelcomeMailSMSDTO> lineDetailsArrList=null;
		Map<Long, WelcomeMailSMSDTO> eligibleLineDetailsMap = new HashMap<Long, WelcomeMailSMSDTO>();
		Connection conn=null;
		boolean isConnCreated=false;
		boolean result = true;
		try{
			if(null==oldConn){
				isConnCreated=true;
				conn=DbConnection.getConnectionObject();
				conn.setAutoCommit(false);
			}else
				conn=oldConn;
			lineDetailsArrList=daoObj.getDetailsTofindEligibleLineItem(conn,event);
			if(null !=lineDetailsArrList){
				for(int i=0;i<lineDetailsArrList.size();i++)
				{
					//MAXIMUM of Created in service ID -> minimum(Service Product Id)
					if(eligibleLineDetailsMap.containsKey(lineDetailsArrList.get(i).getServiceId())){
						if(eligibleLineDetailsMap.get(lineDetailsArrList.get(i).getServiceId()).getCreatedInServiceId()<=lineDetailsArrList.get(i).getCreatedInServiceId()){
							if(eligibleLineDetailsMap.get(lineDetailsArrList.get(i).getServiceId()).getCreatedInServiceId()==lineDetailsArrList.get(i).getCreatedInServiceId()){
								if(eligibleLineDetailsMap.get(lineDetailsArrList.get(i).getServiceId()).getServiceProductId() > lineDetailsArrList.get(i).getServiceProductId()){
									//eligibleLineDetailsMap.get(lineDetailsArrList.get(i).getServiceId()).setServiceProductId(lineDetailsArrList.get(i).getServiceProductId());
									eligibleLineDetailsMap.put(lineDetailsArrList.get(i).getServiceId(),lineDetailsArrList.get(i));
								}
							}
							else
								eligibleLineDetailsMap.put(lineDetailsArrList.get(i).getServiceId(),lineDetailsArrList.get(i));
						}
						/*if(lineDetailsArrList.get(i).getCreatedInServiceId()<=lineDetailsArrList.get(i).getServiceId()){
							//if line is added in current service (equals case)
							//if line is added in previous order service (less than case)
							if(eligibleLineDetailsMap.get(lineDetailsArrList.get(i).getServiceId()).getServiceProductId() > lineDetailsArrList.get(i).getServiceProductId())
								eligibleLineDetailsMap.get(lineDetailsArrList.get(i).getServiceId()).setServiceProductId(lineDetailsArrList.get(i).getServiceProductId());
						}*/
					}else
						eligibleLineDetailsMap.put(lineDetailsArrList.get(i).getServiceId(), lineDetailsArrList.get(i));
				}
			}
			if(null!=eligibleLineDetailsMap)
				result=daoObj.updateEligibleLineItem(conn,event, eligibleLineDetailsMap);
			if(result==true && isConnCreated==true)
				conn.commit();
		}catch(Exception ex){	
			Utility.LOG(true, true, ex, msg);
			throw new Exception();
		}finally{
			if(isConnCreated)
				DbConnection.freeConnection(conn);
		}
		return result;
	}
	
	/**
	 * To send ePCD mail and SMS to customer with PROCESS_WIP status in ORDER_LIFECYCLE_EVENT TABLE for event BT/COPC and update MAIL/SMS_STATUS flag.
	 * @return boolean
	 * @param long
	 * @param String
	 * @param String
	 * @param String
	 * @param String
	 * @param String
	 * @throws Exception
	 * @author Vipin Saharia
	 * @date 02-Apr-2015
	 */
	//MAIL & SMS DRAFTING COPC
	public int sendePCDMailSMSAlertAfterCOPCAndBT(long orderNo,String event,String copcMailFlag, String copcSMSFlag,String btMailFlag,String btSMSFlag)  throws Exception {
		int status=0;
		Connection conn= null;
		boolean result=false;
		WelcomeMailSMSDao daoObj = new WelcomeMailSMSDao();
		try
		{
			conn = DbConnection.getConnectionObject();
			conn.setAutoCommit(false);
			WelcomeMailSMSDao mailDao=new WelcomeMailSMSDao();
			ArrayList<WelcomeMailSMSDTO> custDetailList= new ArrayList<WelcomeMailSMSDTO>();
			//ArrayList<WelcomeMailSMSDTO> updateStatusList= new ArrayList<WelcomeMailSMSDTO>();
			result=daoObj.updatePreStatusBeforeDraftingMailSMS(conn,orderNo,event);
			if(result)
				conn.commit();
			//WelcomeMailSMSDTO statusDtoObj=null;
			if(result && event.equalsIgnoreCase(COPC_EVENT)){
				if(copcMailFlag.equalsIgnoreCase("Y")||copcSMSFlag.equalsIgnoreCase("Y")){
					//String amGroupList=Utility.getAppConfigValue("ePCD_MAIL_AM_GROUP");
					String eSubject=WelcomeMailSMSDao.getTemplateKeyValue("COPC_MAIL_SUBJECT");
					//System.out.println("---------------------------------------------------------------------------------------"+amGroupList);
					custDetailList= mailDao.getDetailsCustEPCDAlertCOPC(conn,orderNo);
					if(null!=custDetailList && copcMailFlag.equalsIgnoreCase("Y")){
						for(int i=0; i<custDetailList.size();i++){
							//statusDtoObj= new WelcomeMailSMSDTO();
							status=draftAndSendMailcustAlertCOPC(custDetailList.get(i),eSubject);
							if(status==1){
								/*statusDtoObj.setMailSmsFlag(MAIL_FLAG);
								statusDtoObj.setServiceId(custDetailList.get(i).getServiceId());
								statusDtoObj.setEvent(COPC_EVENT);
								statusDtoObj.setPreStatus(PS_PROCESSED);
								statusDtoObj.setMailSMSStatus(SUCCESS);
								statusDtoObj.setToList(custDetailList.get(i).getCustEmail());
								statusDtoObj.setCcList(custDetailList.get(i).getAccMngrEmail());
								statusDtoObj.setCellNoList(null);*/
								result=mailDao.updateMailSMSstatusAndDetails(conn,MAIL_FLAG,custDetailList.get(i).getId(),PS_PROCESSED,
										SUCCESS,custDetailList.get(i).getCustEmail(),custDetailList.get(i).getAccMngrEmail()/*+","+amGroupList*/,null);
								if(result)
									conn.commit();
							}else{
								/*statusDtoObj.setMailSmsFlag(MAIL_FLAG);
								statusDtoObj.setServiceId(custDetailList.get(i).getServiceId());
								statusDtoObj.setEvent(COPC_EVENT);
								statusDtoObj.setPreStatus(PS_PROCESSED);
								statusDtoObj.setMailSMSStatus(FAILURE);
								statusDtoObj.setToList(custDetailList.get(i).getCustEmail());
								statusDtoObj.setCcList(custDetailList.get(i).getAccMngrEmail());
								statusDtoObj.setCellNoList(null);*/
								result=mailDao.updateMailSMSstatusAndDetails(conn,MAIL_FLAG,custDetailList.get(i).getId(),PS_PROCESSED,
										FAILURE,custDetailList.get(i).getCustEmail(),custDetailList.get(i).getAccMngrEmail()/*+","+amGroupList*/,null);
								if(result)
									conn.commit();
							}
							//updateStatusList.add(statusDtoObj);
						}
					}
					if(null!=custDetailList && copcSMSFlag.equalsIgnoreCase("Y")){
						for(int i=0; i<custDetailList.size();i++){
							//statusDtoObj= new WelcomeMailSMSDTO();
							ArrayList<Integer> reslist =draftAndSendSMScustAlertCOPC(custDetailList.get(i));
							String smsStatus="";
							int countSuccess=0;
							int countFailure=0;
							for(int r : reslist){
								if(r==1)
									countSuccess++;
								else
									countFailure++;
							}
							if(countSuccess==reslist.size())
								smsStatus=SUCCESS;
							else if(countFailure==reslist.size())
								smsStatus=FAILURE;
							else
								smsStatus=SMS_PARTIAL;
							
							/*statusDtoObj.setMailSmsFlag(SMS_FLAG);
							statusDtoObj.setServiceId(custDetailList.get(i).getServiceId());
							statusDtoObj.setEvent(COPC_EVENT);
							statusDtoObj.setPreStatus(PS_PROCESSED);
							statusDtoObj.setMailSMSStatus(smsStatus);
							statusDtoObj.setToList(null);
							statusDtoObj.setCcList(null);
							statusDtoObj.setCellNoList(custDetailList.get(i).getCustPhone()+","+custDetailList.get(i).getAccMngrPhone());*/
							result=mailDao.updateMailSMSstatusAndDetails(conn,SMS_FLAG,custDetailList.get(i).getId(),PS_PROCESSED,
									smsStatus,null,null,custDetailList.get(i).getCustPhone()+","+custDetailList.get(i).getAccMngrPhone());
							if(result)
								conn.commit();
							//updateStatusList.add(statusDtoObj);
						}
					}
				}
			}
			else if(result && event.equalsIgnoreCase(BT_EVENT)){
				if(btMailFlag.equalsIgnoreCase("Y") || btSMSFlag.equalsIgnoreCase("Y")){
					String amGroupList=Utility.getAppConfigValue("ePCD_MAIL_AM_GROUP");
					String eSubject=WelcomeMailSMSDao.getTemplateKeyValue("BT_MAIL_SUBJECT");
					custDetailList= mailDao.getDetailsCustEPCDAlertBT(conn,orderNo);
					if(null!=custDetailList && btMailFlag.equalsIgnoreCase("Y")){
						setM6OrderNoLatestServiceIfRRorders(conn,orderNo,custDetailList);
						for(int i=0; i<custDetailList.size();i++){
							//statusDtoObj = new WelcomeMailSMSDTO();
							status=draftAndSendMailcustAlertBT(custDetailList.get(i),eSubject,amGroupList);
							if(status==1){
								/*statusDtoObj.setMailSmsFlag(MAIL_FLAG);
								statusDtoObj.setServiceId(custDetailList.get(i).getServiceId());
								statusDtoObj.setEvent(BT_EVENT);
								statusDtoObj.setPreStatus(PS_PROCESSED);
								statusDtoObj.setMailSMSStatus(SUCCESS);
								statusDtoObj.setToList(custDetailList.get(i).getCustEmail());
								statusDtoObj.setCcList(custDetailList.get(i).getAccMngrEmail());
								statusDtoObj.setCellNoList(null);*/
								result=mailDao.updateMailSMSstatusAndDetails(conn,MAIL_FLAG,custDetailList.get(i).getId(),PS_PROCESSED,
										SUCCESS,custDetailList.get(i).getCustEmail(),custDetailList.get(i).getAccMngrEmail()+","+amGroupList,null);
								if(result)
									conn.commit();
							}else{
								/*statusDtoObj.setMailSmsFlag(MAIL_FLAG);
								statusDtoObj.setServiceId(custDetailList.get(i).getServiceId());
								statusDtoObj.setEvent(BT_EVENT);
								statusDtoObj.setPreStatus(PS_PROCESSED);
								statusDtoObj.setMailSMSStatus(FAILURE);
								statusDtoObj.setToList(custDetailList.get(i).getCustEmail());
								statusDtoObj.setCcList(custDetailList.get(i).getAccMngrEmail());
								statusDtoObj.setCellNoList(null);*/
								result=mailDao.updateMailSMSstatusAndDetails(conn,MAIL_FLAG,custDetailList.get(i).getId(),PS_PROCESSED,
										FAILURE,custDetailList.get(i).getCustEmail(),custDetailList.get(i).getAccMngrEmail()+","+amGroupList,null);
								if(result)
									conn.commit();
							}
							//updateStatusList.add(statusDtoObj);
						}
					}
					if(null!=custDetailList && btSMSFlag.equalsIgnoreCase("Y")){
						for(int i=0; i<custDetailList.size();i++){
							//statusDtoObj = new WelcomeMailSMSDTO();
							ArrayList<Integer> reslist =draftAndSendSMScustAlertBT(custDetailList.get(i));
							String smsStatus="";
							int countSuccess=0;
							int countFailure=0;
							for(int r : reslist){
								if(r==1)
									countSuccess++;
								else
									countFailure++;
							}
							if(countSuccess==reslist.size())
								smsStatus=SUCCESS;
							else if(countFailure==reslist.size())
								smsStatus=FAILURE;
							else
								smsStatus=SMS_PARTIAL;
							
							/*statusDtoObj.setMailSmsFlag(SMS_FLAG);
							statusDtoObj.setServiceId(custDetailList.get(i).getServiceId());
							statusDtoObj.setEvent(BT_EVENT);
							statusDtoObj.setPreStatus(PS_PROCESSED);
							statusDtoObj.setMailSMSStatus(smsStatus);
							statusDtoObj.setToList(null);
							statusDtoObj.setCcList(null);
							statusDtoObj.setCellNoList(custDetailList.get(i).getCustPhone()+","+custDetailList.get(i).getAccMngrPhone());*/
							result=mailDao.updateMailSMSstatusAndDetails(conn,SMS_FLAG,custDetailList.get(i).getId(),PS_PROCESSED,
									smsStatus,null,null,custDetailList.get(i).getCustPhone()+","+custDetailList.get(i).getAccMngrPhone());
							if(result)
								conn.commit();
							//updateStatusList.add(statusDtoObj);
						}
					}
				}
			}
			//updating mail/SMS success/failure status in Order_Lifecycle_Event table for all services processed above correspond to a particular order
			/*result=mailDao.updateMailSMSstatusAndDetails(conn, updateStatusList);
			if(result)
				conn.commit();*/
		}catch (Throwable ex) {
			conn.rollback();
			String exc= " Exception occured in Generating Mail/SMS body of ePCD CUST Alert after COPC Approval";
			com.ibm.ioes.utilities.Utility.LOG(exc);
			com.ibm.ioes.utilities.Utility.LOG(ex);
			
		}finally{
			try{
				DbConnection.freeConnection(conn);
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		return 0;
	}
	
	/**
	 * To draft COPC ePCD mail for customer as per details present in IN param(WelcomeMailSMSDTO) & send mail.
	 * @return int
	 * @param WelcomeMailSMSDTO
	 * @author Vipin Saharia
	 * @param eSubject 
	 * @date 01-Apr-2015
	 */
	//COPC MAIL
	public int draftAndSendMailcustAlertCOPC(WelcomeMailSMSDTO copcMailDetailsDto, String eSubject){
		SendMail sendMail=new SendMail();
		int retStatus=0;
		try{
		String toList[]={copcMailDetailsDto.getCustEmail()};
		//String amGroupList=Utility.getAppConfigValue("ePCD_MAIL_AM_GROUP");
		String ccList[]={copcMailDetailsDto.getAccMngrEmail()};
		String bcc[]=null;
		String from=null;
		String emailBody="<!DOCTYPE html><HTML><BODY>"+WelcomeMailSMSDao.getTemplateKeyValue("CUST_MAIL_AFTER_COPC")+"<br/></body></html>";	
		//String eSubject=WelcomeMailSMSDao.getTemplateKeyValue("COPC_MAIL_SUBJECT");
		emailBody=emailBody.replaceAll("(?i)\\{\\{orderType\\}\\}", copcMailDetailsDto.getOrderType());
		emailBody=emailBody.replaceAll("(?i)\\{\\{circuitId\\}\\}", String.valueOf(copcMailDetailsDto.getCircuitId()));
		emailBody=emailBody.replaceAll("(?i)\\{\\{address\\}\\}", copcMailDetailsDto.getCustAddress());
		retStatus=sendMail.postMailWithAttachment(toList,ccList, bcc, eSubject, emailBody, from,null); 
		if(retStatus==1){
			String success= " ePCD Mail after COPC approval for LSI "+String.valueOf(copcMailDetailsDto.getCircuitId())+" sent successfully to Customer & Acc. Manager: "+ Arrays.toString(toList)+ " & "+Arrays.toString(ccList)
						+"\n address: "+copcMailDetailsDto.getCustAddress();
			com.ibm.ioes.utilities.Utility.LOG(success);
		}
		}catch(Exception ex){
			String exc= " Exception occured in Drafting/Sending Mail to Customer after COPC Approval";
			com.ibm.ioes.utilities.Utility.LOG(exc);
			com.ibm.ioes.utilities.Utility.LOG(ex);
		}
		return retStatus;
	}
	
	/**
	 * To draft COPC ePCD SMS for customer as per details present in IN param(WelcomeMailSMSDTO) and send SMS.
	 * @return ArrayList<Integer>
	 * @param WelcomeMailSMSDTO
	 * @author Vipin Saharia
	 * @date 01-Apr-2015
	 */
	//COPC SMS
	public ArrayList<Integer> draftAndSendSMScustAlertCOPC(WelcomeMailSMSDTO copcSMSDetailsDto){
		SMSUtil sms=new SMSUtil();
		ArrayList<Integer> reslist = new ArrayList<Integer>();
		try{
		ArrayList<String> phoneNos=new ArrayList<String>();
		phoneNos.add(copcSMSDetailsDto.getCustPhone());
		phoneNos.add(copcSMSDetailsDto.getAccMngrPhone());
		String smsBody=WelcomeMailSMSDao.getTemplateKeyValue("CUST_SMS_AFTER_COPC");
		smsBody=smsBody.replaceAll("(?i)\\{\\{orderType\\}\\}", copcSMSDetailsDto.getOrderType());
		smsBody=smsBody.replaceAll("(?i)\\{\\{circuitId\\}\\}", String.valueOf(copcSMSDetailsDto.getCircuitId()));
		smsBody=smsBody.replaceAll("(?i)\\{\\{address\\}\\}", copcSMSDetailsDto.getCustAddress());
		com.ibm.ioes.utilities.Utility.LOG(smsBody);
		if(phoneNos!=null && phoneNos.size()==1){
			for (int i = 0; i < phoneNos.size(); i++){
				reslist.add(sms.sendingSMS((String)phoneNos.get(i), smsBody));
			}
		}
		else if(phoneNos.size()>1){
			for (int i = 0; i < phoneNos.size(); i++){
				try{
					String mobileNumber = (String) phoneNos.get(i);
					if (mobileNumber!=null && mobileNumber.length() == 10){
						reslist.add(sms.sendingSMS(mobileNumber, smsBody));
					} else if (mobileNumber!=null && mobileNumber.length() > 10){
						reslist.add(sms.sendingSMS(mobileNumber, smsBody));
					}else {
						com.ibm.ioes.utilities.Utility.LOG("Incorrect mobile number");
					}
				}catch(Exception ex){
					ex.printStackTrace();
					System.err.println("Cannot send messge to no:"+phoneNos.get(i));
				}
			}
		}
		}catch(Exception ex){
			String exc= " Exception occured in Drafting/Sending SMS to Customer after COPC Approval";
			com.ibm.ioes.utilities.Utility.LOG(exc);
			com.ibm.ioes.utilities.Utility.LOG(ex);
		}
		return reslist;
	}
	
	/**
	 * To draft Billing Trigger ePCD mail for customer as per details present in IN param(WelcomeMailSMSDTO) and send mail.
	 * @return int
	 * @param WelcomeMailSMSDTO
	 * @author Vipin Saharia
	 * @param eSubject 
	 * @param amGroupList 
	 * @date 01-Apr-2015
	 */
	//BT MAIL
	public int draftAndSendMailcustAlertBT(WelcomeMailSMSDTO btMailDetailsDto, String eSubject, String amGroupList){
		SendMail sendMail=new SendMail();
		int retStatus=0;
		try{
			String toList[]={btMailDetailsDto.getCustEmail()};
			String ccList[]={btMailDetailsDto.getAccMngrEmail()};
			StringBuffer image=new StringBuffer("data:image/jpeg;base64,Qk1WYwAAAAAAADYAAAAoAAAAogAAADQAAAABABgAAAAAACBjAAAAAAAAAAAAAAAAAAAAAAAA////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////AAD///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////8AAP///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////wAA////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////AAD///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////8AAP//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////"+
				"/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////wAA////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////AAD////////////////////////////////+/v/+/v////7//v79/fz8/v38/v/1/f/F0vOGjuRWXN45Qds2QtZPWdhxeNueqObM1PTv8/7///////////7+/v7+/v7+/v7+/v////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////8AAP////////////////////////////////7///7////9///9//3++vr/+/X4/5yh4zlEzRsq0BwpzzA4zWZy1neA1F9nyzxIvDQ9vVRZ0Y6V4cvR8vb5///////////+/f7+/f////7////////////////+///////////////////////////////////////////////////////////////////////////////////////////////////////////+///+///////////////////////////////////////+///+//////////////////////////////////////////////////////7///7//////v///////////////////////////////////v/////////////////////////////////////////+///+/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////v////7///7//////////////////////////////////////////////////////////////wAA/////////////////////////////////////v///v7+//79//38/f/+sbrrN0DMICvXHyreISnSZmzT6fL//f//8vL+09T1mZ3jTFbNIS2/MTzEbHPVvcDu9Pb////////8/v/+/P7//v7////+/v/+//7//v/+/v/+/v////7///7///////////////////////////////////////////////////////////////////////////////////7///3//v7//v/+//7+//7////9///8///+/v///v///v////7///7+/v///v/////+///+/////////v///v/+/v/9/////////////v/+/P///P///v/9///+/////v///f///v/+/v///v/////+///9//////7///7//////////////////////////////v7//v7///7//////////////v////7//////v/+/f////////////7//v/////////////////+//////////////7///7///7////////////+///////+///8/v/9/P///f/////////+///+//////7/////////////////////////////////////////AAD///////////////////////////////////7+//3+//7///3//v3v7v1tdtQhMdYjLtojLtkeKddkZ9fy8//////////////////q7fypsOtVXs0gK8EkMsdfatW3u+z29v/////////+//z8//78//////3///7+/v78/v7+/v7////////////////////////////////////////////////////////////////////////////////////+///9/v/+//////7//v7//f////3///v///7////+///+//7///3///z///7//v////7///7+//79///8/v/+/v////3///3//v/+/v////7///3//v/////+/v///v/////9///9///9//79///9//7+//7+///9///9///+//////////////////////////////7///7////+///+//////7///3+/v79/v/9///+//////3//v7//v/////////////+///+//////7///3+///9///9//7+//3///3///7////8///9//////7///39/v7+/v///////v///v///v////////////////////////////////////////////8AAP///////////////////////////////////v7//P7+/v7+/v3//dvd+FJb0R4w2h8t2SEt2iAs2zY8z7a47P/////+/v3//v7//f//+////+nw/aKo6EZRzxgkxSYwyWx02MbH8fz8/////P7++/3+/P3+/v7+/v7+/v39/vz+/v7///////////////////////////////////////////////////////////////////////////////////z///z////+/////v7+/v39//7+/v3//vz+//3///7"+
				"///7///3///3+/v7+//7+//7+//7+//3///39//7+/v/9///+//7//vz//fz//f7//v/+/f/+//3///z///3//v7//f/+/v7+/v7+/f7//P7//f7+/v7//////////////////////////////////////////f///f7///v+//r+//v//fz/+/z//Pz//P7+/P/9///+/f/+/v7+/f///f////z+/vr+//v///3//v///f7//f3//f3//Pz//P3+/f3+/v3///v//v3////+///////9//79//7//P7//P7+/v3+/v3//v///////////////////////////////////////wAA/////////////////////////////////////v7///3//v7+/P/92N33TlvOIDPXIDHZITDcJzDcIyzRSlbMz9f1///////8/f79/v3+///9////////3d74hYneLzjJFCDJNz7Niove4eD6///////9/f/6/f/8/v7+//7+//7//v////////////////////////////////////////////////////////////////////////////////////////7///3//v7++//8/P/8/v78//3+/v7+/v79///9///9///+/v///v7//v/+/f/9/v/+///+//7///7//v/+/f/8/v7//v/+///9///8//79//3+///9/v7///7+///8/v7++/39/f78//7+/v3//f3+/f/+/v///v///////////////////////////////v////////////7+/f7++v7/+v///f7//v39///6/v3+/f3//v////7+/v7//f/+/P/8/f/8///8///9/v/9/v/8///9//3///3///3+//7+///+///+//3///7//v/8/f/9/v///v76//77//78//78/v/8////////////////////////////////////////AAD///////////////////////////////////3+/v7+/f/9//z9//7l6/tlc9IuP9csPdwpPtwuPN8oNdkcKdFUXM7W3fb//////fv+/f79/f/4/v39//3////4+/++we5WW9EaJcodJ81LVs+wtuz1+f////z//vv9/f/8//z+//v///3//////////////////////////////////////////////////////////////////////////////////v/+/v/9//78//37//z///3//f7///7///////////////////////////7//v///f///v7///z+//z9/v/9//7+//z//f////////////3///////7///z+/vv9/v7+//7+//79//v+//v///////////3///z///7//v///v/////////////////////+///9/v/8/v7///7///7///7//////////////P7//v79//37//79/////v////////7///z///3///7///7///////////////3///3//v7//f7+/v/+///+/v78//78//3+/f3//v7//v7///7///////////////////////////////////////////////////8AAP///////////////////////////////////f7//v3//v3//f/+//j6/o+b3UZZ2T9V4TxU4T1R4zJI3Cc13B8n0VNez8vW9v///////P3+/fn/+/z/+/z++/3/+////uXp+oOJ2y44yREkzSY2yXyG3Nzi+P////7///r//v3/+////f///////////////////////////////////////////////////////////////////////////////////vz//fr//vv+/v/+/f////////L1/t/j99XX89LU8tbZ9ePm+fP4/v///////////////v///f3//fv//f3//f/+//z+//r6//n4//n4//r5//n6//v9//7//fz+/ff7//f5//r4//j6//j6//z6//39/v7//P7//P3+//7+///////////////////////////+/f///v////z+/+7x/ePk99/f9ePp9fT8+/7+//3//Pz//P7//////v////////z+/+zv/eDh99ja9Nve9ePl9u7x/Pz//v///////////v7//v7///7//fz//P3////+/f////////L4/9/j9dve9drf9f///////////////////////////////////////wAA//////7//////////////////////////////////f/+/v7///39////v8nwWXDZVG/oVm7mUmjmRl3jNkreJjXbICnRUVjQyM3z/////v///P7+/v///v39/f78+v/8////+fj/rLDnP0vJGCTPHyjNVlzQwMfw/v///////v7//v7///7+/v/9/v/+///+/v///v/////////////////////////////////////////////////////////////8/f79+v3//f7/////5Of6nqXkZ2vXSU3PO0LKNz/MPkXMTVTNZ27WkJTfwcXu7fP9//////////z//v38//78/v//3ef3envUaWzZaW/ZaW7YgILZ3+X4////////wMTub3LUaG/ZZ2/aaW3Zi43X6O/8/P/+/v3+/v7+/v/+/v////7+//7//v7+//7+///+////7O39r7PpeYDZWWDVSVDQREnJZWnK2eX4/P////79/v/7/v/+////6u78srfofX7dWV3XQ07LP0fIREfMSFHKWmHUen3fpqjn2tz1/f////////7///7+//3+////////2uD3mp7kaG/XRU3LQUfOP0nM////////////////////////////////////////AAD//////f////////////7///7///7////+//7///7//v///v7//vz8///r8v2AkdpdeOVmge1lfOtccOZLYeQ4S+EoM94gKdNJUM3Gxu/////7///8//3+/vz9/v38/f/+/f////79/v/NzfFVX8saJswcI9M+RcujrOP2+//////+/v7+/f77//r8//n//v39///9/v///v////////////////////////////////////////////////7+/v3//vz7/f37//78/f+9vu5TWs8fKcoZHNQVHtUQH9gWHtobHNoVHNUUHtEaJMosN8pbY9Sxsuv39P/////+/v79/v7////P1PQqLscQHNEUHtESG882PMrS1/X///7+//+go+cdJMgSHdAUHdISGtBFTMvj6Pz//////v///v7///77//7+/v3//f/7/v36/v3+///N0/NmZtApLMoYIM0VHtIUHNcRGtI4Pc7Y2fr+//7+/vz8//z7///BxPBcYtIoL8caHdEUHdYQIdIVH9YXHdcYHNYSHdMVH9MjJ89CRc9+hd3LzfD49/79/f7//vz+/v+0uuxGUMweJssZHdASHdcRG9oQHNX///////////////////////////////////////8AAP/////+///////////////////////+//7//v7//v/9///+/v7//fv+/vz//8jN72t51WeB7WyG7GiA6Ft16Exk5ztN4Cc72Bww0UJPx7vE6/7////9+/7++/z+/fz9//7+/f7/+f3//P///93i+W901SUuyBol1SYyzYCJ3O"+
				"3u/f////38/P3+/f7//f/9//79//7//v///v///////////////////////////////////////////////v3+/P3+/P3+/Pz+/6yw5zY5yBYe0Rgj2Roj3Roj2xQg1RUg0RggzxEf0RQf1Rki2RUe3BQc1SYpy2Zs1MvU9fv+/f7+/f///9PV9S40zxUj2hsl2hkh2TtCz9XY9v///v7//6On6iMrzxkk2Rwj2xkh2UlR1Ojo/P////////////////v///7////+//v//vv//tjZ90tWxxcgzBgg3Bwj2xsi1xgf0hslzUdOyd3f+f///v/+/f3+/6qw5zg7yBYd0Boj2Rgk3BQi3BQf1Rcg0Rgg0Bge0hUb1xcd2Bog2hMf1RQdyl9kyOnr+v///v7//b/C8Ts7yBYd0xoj2R0l2BggziIoyygwy////////////////////////////////////////wAA///////9//////////7///7///7///7//v///v////7///7//v/9/v78//7+9fj/oavjZ3nccIjtconsaoPoXHXmUGbjPlLbLEDcIjTWRlPLwsny/f3//vz8/f7+/P/+/v/8/v77/f76/P/8////7O39gYjXJC/IGSbYICvMcXbZ4ub6/////v77/f3//v7+/f/+///9///+///////////////////////////////////////////////+/v/9/f79////0NH1O0HKFR/VGSPcHCTbGyTWIyPQSkzUcXzegIrgbnbbR0zSJSvLHCTXGiXbFyLZEx/ORk7J0dTy//7+////0tT1LzXOFiTZHCbZGSLYO0PP1dj2///+/v//oqbpIyvOGSTYHSTaGiHYSlHT5+j8/////////////////f///f/+/f79/f/+9/z/iYvgHiTKGSPcGCXbGiTWLjbLdXzfo6nowMbr8Pf8/v7+////w8LwNTvFFSHTGiXbHyPcGSLYICvLT1XTe3/gi4zmfIHgXmPYQEPNIyvJER7OJinMpafn+v3+///98PL+Z27TGiDRGiPcHSXYISbScnXZp6vntrrs////////////////////////////////////////AAD///////3//////////v////////7///3///3//v7//v/////9//7+//z//vz9//7o8PyUodxtg994ke96kO1xhupjeeZRa+JAWeQ5R+MwO9RfbM7i6fn//v79/v38//7+/v7//P/+/P/+/v7+///////y9f2Gi9kgL78dKdIfK85ebdLc3/f///36/v39/P/+//3//v3+/v7+/v7///////////////////////////////////////////7+//3//v76+/94gdYXI8oZJdsbI94bI9ogK8p7e93f3fn8/f/////8+//g4viQluUsM9AeJNcdJNsYI9olK8S1uer////////S1PUvNM4WJNkcJtkZItc8Q8/V2Pb///7+//+jpukkLM8ZJNgdJNoaIdhKUdPn6Pz////////////////+///8//78//3///7r7vxZXdQeINUdJtcYJdoaI9R4gt7z/P/////////7/v7+//7l5/xZXdEZIdEZJ9gZJtgaIdgyNc+UmOPg5vn6/////////v/y9P3W2PSoretscth7fdDt7P3///////7W3PdAScoZI9QbJdkZJdgqMM3x9f////////////////////////////////////////////////8AAP////7+/v////////////7+/v3//v3//f///P/+/P7+/f7///z+/v3+/v3//v///f///ufw+5uq4nqM6IGZ8oKa8XOK8GF861Nw6Ehg5DtQ3zZExbG26f////7//f3//f7+/v/9///+/////vz//f3+/P////T1/4iO2yYtwhoo1hcqz15l0N7f+P/////8/f79//z+/vz9/P7+/f///v/////////////+//////////////////////7//v3//f7//uDk+kRMzBsj0x4l2xwl2xwl0lhk1+Ts/f///////////////v///9Xa9T1Dzhsi1xom2hoj3Scvyri87f///////9LV9S80zhYk2Rwm2Rki1zxDz9XY9v///v7//6Om6SQszxkk2B0k2hoh2EpR0+fo/P////////////////////3//v3+/v///+Lk+UhRzxoh1xkm2Rgl2iMpz6ap7f////7//f/8/fv9//79/6Ol5SEqyxoj2x0l2xoj2yMuzZqf5v38//////7//////v///////f////////f3//L0+/7+/v/+/v///8nO8jc8yx4k1R0l2RQi2zE7y////////v/9/////////////////////////////////////////wAA/////f39///+///+/f7+/v/////////////////+/v/9/v/9/f7+/P7+/P/+/f7+//z+///+8vf9tLvqg5DfdIjnb4bwZ3/sXHHrSWDnNEjaQU3GwMbu///+//79/P79/f/+///////+/v/9+//7/P78/v78///+9vP/f4bVIirDHinXHCjPW2LT3+H5/////P38+/78/f78///+//////////////////7////////////////////////+/v/9/v/+zM3xNzjRHSHZHiTZGSXaISvNmZ3p///////9+//+/v7+///+///+0tjyO0LOGSHaHSTbHCLbJTDLtrzv////////0tX1LzTOFiTZHCbZGSLXPEPP1dj2///+/v//o6bpJCzPGSTYHSTaGiHYSlHT5+j8/////////////////v/////9//7/////3+P4Rk/NGyLYGiXaGyTZJCvMqKrs/////v/9/f7+/f7/7ev8YGTRGCTRGyTaHCXbHiLQYGPR7/L////6/f/8+/3//v7//v7//v/+///+///9///+////+/79/v77////x83vNzzIHCPYHCbaFiLZNDvL//////////79////////////////////////////////////////AAD//////v7+/vz///3////5/v/q7Pve3/fl6vrz+f7///////////3+//z+//36//38/f3//f3+//34/f3T2vGXpt9rfdhTZtJKWNVJVNFka9Cutef2+f///f3//v79/v/9///////////+/f/+/f/9/v78/v3//vz///zs8P1vcs4gJsAhKdkbJ9JjbtPl5vr////+/P7+/f3+//3+//7+/v///////////v////////////////////////7+//3////V1/Q6Ps4YJNccJdoYI9sfKdCWmuf///////38//3///3///7////T1vE7Q80cIdofI9scJNkmM8i6vu7////////R1PUuNM0XJNkcJdkZItc8Q8/V2Pb///7+//+jpuokLM8ZJNgdJNoaIdhKUdPn6Pz//////////////v/9//7///3//f7////f5fhJTs8ZIdcZJ9cdI9oiKdCjqe3////+//78//7////P0fU2QcwaI9gcJtgaJdgeJ8ufpOH//////v39/f7+/v/+//3///3///3////+//39//v+/vv//v3///7////Jzu82PsUXI9oZJdsdJNc2O8n////8//7///7///////////////////////////////////////8AAP/////+/v7///j9/8jK8Hd910xW0kRL0UxS02Rr1"+
				"ZOX4MzP8vT3/////////v7//P3//f79/vz+/fz/+////fj7/uHj983R8cXI8NDS8enr+v3//////f7+/v7+/v/////////////////+///+///+//79//79/v3/+v///+Df+FNUvx0jyR4r2h8sz3R61u7w/f///v39/v3//P///P/+///+//7//v3//f7//////////////////////v7//f///eru/FJXzRUi1Bgm2h0k2xok1FBb0tbf+v///////////v///////9rd8ztEzhsi2R0k2xkk2SgyyLy+7v///////9HU9C0zzRgk2R0l2Roi1z1Dz9fY9/////7//6Kn6SIszxkk2R0k2hoi10pR0ufp/P///v/////////+//3//////v7+/v///uDk+ElPzxkh1hgm2Rwk2yIqzqSp6/////3//v7//f///7K37SYuzB4j2Bsn1xsj2CszzsbP9f///////////////////////////////////////////v////7//////8nO7zU9xxkj2Bol2xoj2TU7yf////7////+/////////////////////////////////////////wAA////////yM/yjJPfNDjNHSbQGiXZGyfaHSbZHCbUHinNLzrKZm3WsLXr6+38///////+/v/8/v39//3+/v78/v/9///////////////////+/v/9/v/9/v7+/////////////////////////v///v////7//v3//v7+/f/8////wsLtMT2/HijOIyrYIy7KkJTf+fv//f///P/+/P/+/v7///7////9///9///////////////////////////9///+/P7/mJvhHCXMFyLdISPcHyXXHCXPUlbSp6jpy8z00dT209T10tP1q7HsMz3RGSTXHSXZGiPaKDHMu77w////////0dT0LTPNGCTZHiXZGiLXPUTP19j2/////v//o6foIy3NGSTZHSTaGiLXSlHS5+n8///+/////////////f///////v/+///+4OP4SE/PGiLVGSTaGyTbIirNpanr/////f/+/v7/////pKboIynQHSTZGSTcHCLZMzjRrLTy19v31df31tn21Nn11tj21tf31dn209r10tf21Nb31tf229v29PX9////yc7wNj3IGyPYGyTbGiPZNDvJ//////////7/////////////////////////////////////////AAD////d4vlRVMsoMcgaKtggLtoiLtsfLtofLtogLdwgLNwcK9cbKM8sNcpfaNKxuOvx9f////////79/v77//z9//3+//7///z+//7+//7+//3///7////////////////////////////////+///+/////v///v///v/9//z9//z5/P+Kk9giKb0gKdccKdwxOsi0tuz////+/v/7/v/7//7+//z//v7//v/////////////////////////+//7//v7//v/m6ftbYtMWIM4ZI9gaI90ZJdkYINAgJc4rM9AyOdIzOdAuN88rMdQfJtkaJdkeJNocJNooMsq7wfD////////Q1PQtM80YJNkeJdkaItc8RM/X2fb////+//+jp+kjLc0YJNgdJNoaItdKUdLn6fz///7////////////9///////+//7////g4vlITtEaIdYZJNobI9siKs6lqez////9/v79/v/+//+lp+kiKc4ZJNoZJdwdJNofKdYyOdU5PtA4PNM3PtA0QM42PdA3PtE4PtE3P9A1P9AzP881PsxSVsrW2PX////JzvA2PckcI9gcJNsbI9g1O8n//////////v////////////////////////////////////////8AAP///5CT5SEqyh4s1CQt3SYr3SQs3CEu2yIu2iUs3CQt3CIt3CIs3SAs2Bso0i03zXF12crK8/7+//////3//P/+/P/9///+//7+///+///+///+///+//////////////////////////////7+//7+/////v/+/v/+///+//7/+////OHj+ExRwxgmyR8s2hwn1E5UzuHh+v////3+/fz//v7//v/9//7+//7///////////////////7//v7//f3+/f79/P///tbY9mJozR0mxBAd2BEi1xQl2RQi2RYg1xkf2Rce1xMg1RUh2hoj3Rom2B4l2Rwj2ygyyrvB8f///////8/T9C0zzhgj2h4l2Roh1zxEz9fY9v////7//6On6SMtzRck2B0k2hoi10pR0ufp/P///v////////////3///////7//////+Di+UhO0Roh1hkk2hsj3CIqzqWp7P////3+//z//v7//7C07CQsyxgl2Bko1R8m2Boj2Rce2RUe1RUc2RMd2BAf1RMd2RYe1hoe1xse2hog1hUi1BIe0zk/0NfZ9////8nN8DY9yRwj2Rwl2hsk2DU7yf/////////+/////////////////////////////////////////wAA////V17gISrRIS7XIizdJSrfJSvdIi3cIi7aIi7aJCzdIirhHy3dIC/YJCzcIyjbICjQN0DKhove3974///////9/f/9//7+/v/9/v79//7///7///7///////////////////////////////7///7////+///8///+//7///7///7/////nKHdHiu4ISrVIizdHyzMf4TY9fj+//77/v7+/v///////v///f///////////////////v/+/v/9+//+/P76//76////6+z7nZ/iUFbNLjTLHibMFyPLGSDPGh7UGiHNGiDNGyDRHCXYGifXHiXaHCLbKDHMvMHz////////0NP0LTPPGCPaHiXaGiHYPEPP1tj3////////pKfpIy3OFyTaHSTbGiLXSlHS5+n8///+/////////////f///////v/+////4OL5SE7RGiHWGSTaGyPbIirOpans/////f7//f7/////xMfyMjfMGSXWGifXHiXaGSbaGyfTHifNHyfLHyjLHCfOHybPHibQHyjTGyTaHCTbGibZFiLZR0vW4OH6////ys3wNz3JHCPZHCXaGiTYNDvJ//////////7/////////////////////////////////////////AAD///9JU+EiKdUgLNwcLd0hLdojLtkiLdohLdsgLtohLdsiLdwgLdsgLtsiLN0iLdshLtoeKdggKc1MU8+jqefs8fz////+//39//v///z///7//////v///////////////////////////////////v///v7///z+//3//v///v/9/v7///7f4vc8RLsfJs4gLNsbK9gzPcq5we7////+/v78/v3//v7+/v/+//////////////////////7///7+/f/+/fz+/fz9/v7////////s6/rFx/Kip+aUmeCQj+GOjeSKkt+Ok99/geMxONkYItodJNobJNkoM8m9w/D////////P0vQtM88YI9oeJdoaIdg7Q8/V2Pf///////+kp+kjLc4XJNodJNsaItdKUdLo6Pz///3////////////9///////+//7////g4vhITs8aIdYZJNobI9siKs2lqez////9//79/v7////d3fpITs8bI9UdJNsdI9sbJdJpb9ylquqlquemp+elpuqlp+ilqepuc9wfJtEcI90bJdkWI9RZ"+
				"XtXr6/z///7LzvA4PckbI9kbJdsaJNg0O8n//////////v////////////////////////////////////////8AAP///15n3x0q0iAu3CIv3Cgx2Ccw2iMv3CIw2yIv2yMu2iIu2CEu2SEt2yMt2iEv2SAt3CIr3yEt2x4o1S4zzGtu1cfK8fz+/////////f7+/v7//v///P///f7+/v7+/v///////////////////f/+/v/9/////v3//f3///3//fv/+v///Pn3/2ptzhkkxB4q2h0s2xwp02lw1/L0/f///fv9/f39/v7+/v7//v///////////////////v///v/+//7+/vz//v7+/v7//f///f////////////////////////7//////+Pk/URM1xkj2Bwk2xkk2igyyb/C8f///////8/T9Cwzzhcj2h4l2Roh2DtD0NXZ9////////6Ol6iMtzxgk2hwk2xoj10xU0+vs/f///f/+/f7//v79/vz9/f7+/f3+/v///t/i+EhPzhoh1hok2hwj2yMqzqWp6////f3+/f3+/P///fL0/2pz1Rsi0hwl2h0l2hgizn6A2////////////////////////4+O3x4jzRok2h0l2R4i0n1/1/j4/v///s3O8Dc9yRsj2Rol3Bgk2DQ7yf/////////+/////////////////////////////////////////wAA////jZjiITDQJTTbKT3aLD7bMD7eLD7fKz7dKjzcJzjbJTXbJDPbJDHcJTDdIzDbIC7bISvdIizdIi3cICvaHSjSOkLMio/d3+T4///////+/f7+/v38//38//78//79///9///////////+///+//7///7//////////v///f/+/P/9///+//7/lZbaHybCISjZHy7YHSvXMDnOwMTv/////v7++v/8/P/9//7+//////////////////////////7//f7+/P/+//7++Pj+3d/08fD+///////////////////9///+////ur3uMTnMGSXWGSXaGSLcLjXJzM74////////z9P0KjPPFyTZGCbZGiLYPULQ1tj2///+////oKPqICvOGiTaHSPbGiPWQ0rT1Nv6///////+///+///////+///++//+///94OL4R0/NGiLWGyXaHSLbIinQp67t///9/////////v79///9qq7pISzLFybYHiXZGCLXP0TP0tX3///////////9////4OL8TU3RHSHVHiTaGSTXKC3LsLHq////////y83wNT3JGiPZGyTcFyTZMzvJ//////////7/////////////////////////////////////////AAD////L0/Q6SM4sPNU1R+I0T+E7UOM9UuE8UeE5TuE1St8yRt4tQN0pO9snOd0lNtwiMtshLtsiLdwiLdshLdsgLN0cKdkgKs9LU9Cmrunt8f////////3+/fz+/f3//f/+///7///8//////7///7////////////////+///9///8//3+//3///+kqNwjK70eKdUhLNogLtkeK9B6f9v5+v////v6/v78//3//v7////////////////////////+//78///+//7////N0fdHTMdWXtGboOPO0PTo6v309P709v3l6/2xtuxJT88dJNUYJdwYJtoXI9NMU8nl6fz+//3////Rz/UsMs4bJNgZJNwXItk8Q9DY2Pf///7///+qregkLMUaIdkdI94ZJdkeJdZQVdKZnOPGyfPZ3Prb4PvS1vXm5/j///////3f4flHTs8ZI9QZJtgcJdkhKtCOmOzd5fvd4/no6vr4/P3///7p6v1aYdMSI9AaJtgcIt0gJNFgY9XO0/f39v/5+P/a3vtveNceJdAaJNodJdkYIdVOV83h4vv//v7////KzPA1PcoaI9kbJNwXJNkzO8n//////////v////////////////////////////////////////8AAP////n7/3aC1D9RyThN3j5b5Edg5U1k401j5Eph40Vd4UBY4DtR3zRL3S5D3Cs+3Sk63ic23iQx3CMv2yIt2yIs3CIs3iAs3xsm2iwyz25y1cHE7/f6//////7//v39/v3//fz//v3//v///////v////////////////7///3///z+/v39/f///6Ck3CErvBsq1CQs2yAs2hos10JIz+Dm+v//+v7+/fz//f/+/v////////////////////////7///z///7//vj5/4GG3hchzxMgzh8kzDc50lFW12Jp02Vt1U9Z1ygxzBcj0iAj3B4j3Bki2h8oyJif4/z9//z+/////9DR9Co0zB0j1yAk2hwj1j5D0NjZ9/7//f39/+bn+XV81SAnyRUf2Bcm2Bsi3Rke1BwlyC82zURC1UJJ0zk+y3d51PD1/v///t/i+ElOzxsi1Rwl2x0l2yAl2DQ610RK1URHzn2D0/Dz/f///f///8LF8TE6xxcg0x8k3R4j2hwk0DtFzmtv0XJy1kdM1R0mzh8j2B4k2hch2SYryqer5/3+//79/v///8vN8DQ9yRoj2Rsk3Bgj2TM7yf/////////+/////////////////////////////////////////wAA////////ydDxgY7ZNUzRQl7iTWnmVnHmWnHpWXHnVW3lT2fjSGHiQlnhOVLgNEvfMUXfLUDeKTreKDbcJjLcIy/dIC7bIC7aIi3aICvYHyjROD7LeYLX1dn0///////+/v/8/v/8/v/9/v3//v3//v/+/////////v7+///+/v/+/v39//z+/f3/gYTYHSbBHCrVIyzZISzaHS7bKzTNub/q///+//79+/78/f7//////////////////////////f////3+//7/2NvzRkzCEBrPFx7ZGh/dFx/ZFR/SFB7RFR3UFh3WGSDcGiTbGSTXFSHTHibJd3ra7O3+///7/P79////ydDzIC7IEx3YFyLXFiPTOkLQ1Nb3/v/+/vz+////5/L9kZjiND3GFx/MGR/VGSPZGCPZFSHXEyHTEyHSFR7VLzPMtb/t////4uH4RU/QFyLXHCTcHCXZHSXYFyHXExzYFBzPXWXR7PH9///9/v/6/f3/qazoNjvHGB7QGSDbGSLdFiDVFx7QFR3UEx/VGSLcFiLaEiDQISjGhojd9Pf9///8/v/+////ys3wND3JGiPZHCTcGSPZNDvJ//////////7/////////////////////////////////////////AAD////8/v37///c4flhbNM/WNxOauldd+dmfelnf+plfelgd+hYcOZPaOVIYeNBWOE7Ud82S94yRd4tQN0qO90nNd0iMtwhMNwkLtwiLdseK98dKtsfKs4+RcuLkODa2/b//v/////+//z7//z8/f78/v79/v7+/v7////+//3+//3//vv///zl6/tHTcYcJsggLNQgLNohLdokL9woNtCaoOH//v///v/7/v79//7////////////////////////7//78/vv9/P3x8vu9u+11ddk6Qc4eKs4UINAUHdMWHtQWH9QXHtYWHdYXHdIaJcw6RseQm+Dw8v7////7/vv8//3//v/X2PRUXMQ7QMssMc8bJM05QcXU1/T///7//vz+/vv9//78///Qz/R+fdw6QMscKMoXH9AZHdYVINYVIdcUIdc"+
				"UG9FaYdHn7Pvj4/lEUc0YItYeI9wcJtccJ9UYJNAVH9AVHMxiYtDq8f7+//7//f3//vz8/v/AxvBdZdAoMMsYINAXHtITHtURHtUUH9AaHs8dKMtATcifpOP09f/9//z8/f79/v/////KzfA0PckaI9kcJNwZI9k0O8n//////////v////////////////////////////////////////8AAP////z9/fv///v//8fJ8VFh0Upk5GJ56GyF6XKK7HKK7G6G6mh/6F926Fdu5k5k5Edd4kFW4DpP3zNK3i9E3iw93is43Sk23SYy3CQv3iEu2iMu2SQs2x8p2CArzEVOzI+S4NHU9Pj8//////////7//v7//v7+/v7+/v///f7//f///f3//5ef3R8qvCApzyMr1x4s2yEt2iQy2yY303+G2fr5//7+//z+/v7//f////////////////////////3//v39/vz9/f3/+/////z6/9bX9aiq6IGB2mJk1kxV1EJOz0FMzkxV0Wdu05ib49jW9f7//////v3+/vv+/fz+/v78/fn5/urr+9na9sLE8qir7Kao4Ojt+Pz//fz+/v/8/f/9/f///f////79/9bV9Zyj5G931FVY00NG0Tk+0TU/zjxDzV5iyNTX8uLj+kdPzxgi1hoj3Rwk2x4n1FNa13l91Xd60qai2/D1/v3+///8//39/f7//v///+7v/ri77YGK2GNr0Vld1Vxd1WVm0nl/1Keq5+Dd+P///////vz9//z8/v3+/////8nN8DQ9yRoj2Rwk3Bkj2TQ7yf/////////+/////////////////////////////////////////wAA//////789/7/9v/+/v//qbPnT2HRWG7mbYbtepLsfJXtepPtdo3sbITqY3znWXHmUWjlS2HkQ1viO1ThNkzhMkbgLkLeLEDcKTzbJzfcJDTbIjLYITDZIS7bICzbHCfXISzOQEfSeXvfubvr6+77/v///////////////////////P//s7foOD6+GSjHISvUISzbIC/cITDcJDbdJTfYcXfV8/X//P/+/v/+///9///////////////////////////9/P79/f7//f/7/P79/v/+/////////v//8vP96On84uP63+D55en88fj+/////////v/+/f79/v7+/f79+/7+/f78/f/9/////////////////////f3/+//8+/78/f77/P7//P7+/f7+///8/////v//8/r/6Oz829740tP2z9P119v25+j6+Pn+3uH5SU/RGiPUGSXZHSPaISrPo6jw/P//+f3/+/3++/37/v78/f7/+/7//v/7/v78/////////v7/8vX+7O7+7+398vT9+P3///////////77/f76/v3+//78/f/9////yM3wND3JGyPZHSTcGSPYMzvJ//////////7/////////////////////////////////////////AAD//////v/8/f/7/f////z2/P6jruVVZtNgd+V2j+yAmu6FnfCEmfB7lOxzjOlogehedudWb+VPZ+NHX+NAV+I5UOE1TN8xSN0uRN4qPt0oO90mONwlNd0kMt4iMN0hLdwhLN0dKNggKNAtNcxQVNF8gdulrOPHzPDV2vPU2fO/v+t7ftQyOLsfJMcgKdYhLtkiM9ojNdwkNt0pOt0mOtZwdtPx9P7+//3///z+/v/////////////////////////+//79//79///9//79///9///9///+//////////////7////////////////////+/v79/v79/v7+/v3+//39/v3//P76/v/LzvGWlt6Fh92orOXp6vv+///9/v79//r9//n7/v/+/v////////7///7///////////////////7///////////7///ze4fhFUM8aI9UcJdkaI9sjKc+oqu7////////+//////7///7+///+//////7///79/v7//v7///////////7///7////////8/v78/v79//79//38/v79/v38//3////IzvA2PcoaI9kcJNoZJNcyPMn//////////v7///////////////////////////////////////8AAP///////f/+///+/////f///vP9/6Ow4Vpq0mZ96n+V8Iqf8Y2g8Yqc74CX8HeQ8G6G7GR96Ftz5lNr50tj5UNb4j9W4TtQ4TZL4TJG3y9C3i0/3iw73io43yY13yIy3SIu3iIt3SEt2yAq2hom2Rkm0SEtyS01yjI6xy85xSQtxRsjxh4nzyQt2SYy3ic43yY53Sc43So73y883io80Xd+0vX1//7//v3//f3///////////////////////////7///7///7///3///7///7///7///////////////////////////////////////////////////7//v7//P7++/v8/6mv5jQ6xhUfyw8fzB4kyGVk0Nrg9/7//v/+/v79//7+/////f///v///////////////////////////v///v///v7+/v///d3h+UVO0Bkj1Rwm2Rcl2SEqzqWo7f////7//P///v////////////////////////////////////////////////////////////////////////7+/v7+/v/9/////8fO8TY7yxkj2B0l2Rwi2TY6yv///////////f///////////////////////////////////////wAA///////+/v///v////////3+///+9/r/rrfoY3bSbYTnhZz0j6TwjqHwiZ7vg5rve5TudIzsaoLpYHjoVm/nT2jkSmDiQ1nhPlThOk/gN0rgNEfgMkLeLkDdKTzcJzjbJTTdIzHdIy7dIy3cIy7aIS3aISvbISrbICfbHijaHizaIC3dIjDdJDfcKDzcKT7eKjzfLDzfLj7fLj3gLTzRjZPb/fv///7//P/+/P///////////////////////////////////////////////////////////////////////////////////////////////////////v/+/v/9/v/+3uX6R0/JFB3UGCLeFyTbGSPYHyPIjpHd/fv////+///+//7//v/9/v/+//////////////////////////7//////v/+/f/+/v/93d/4Q0XJFBnSFR/XFSHXISjMoqjr/v////7+//7//////////////////////////////////////////////////////////////////////////v///v7+//3+////ws7tLDfAER3UEyDZFR7YNDjK///////+//3/////////////////////////////////////////AAD////////+///9///9///+/v//+////v77/v+9x+pxgdFvh+eHofaQp/KOou6In+6DnO1+l+x3j+tvh+plfehdduZVbuVOZeNHXuJAV+A8Ut85T+A2S+AyRt0uQ9srPtwoOdwlNdwkM9wjL9sjLtkjLNwiLN0hLtohLtsgLN0gLdshMNslN9spPtkqQtoqQN4rPuAsPd4rQN0rPeIwPNKqseT//////f79/v78//////////////////////////////////////////////////////////////////////////"+
				"////////////////////////////////7+//3////O1PQvOMQYI9ofJNseJdkXJdkTINBwddP7+/////7//v///v/9///+/////////////////////////////v/9///+//7+/v7///7s7PuChtFNVMo4QcYtM8gvLr+mqub9//z//vv//v/////////////////////////////////////////////////////////////////////////+/v7+/v3///v//v7Z3/RxdsxHT8c1PMUoL8U7Pbv+///+//7//v////////////////////////////////////////8AAP////////7+//3+//v///z//f7+/f/9/f///f7//9fd9ImU2nOF44ed846k9Iqh8oWe7oOa7H+X7HqR63KJ6WqD6GJ86Fp051Ns5ktk5ERd4T5X4DtT4DdN4TNH4C5C3yo83SY23CQ03CEw2iEt2SIs3CEt3SEv2CEv2SEs3SMu3Ck03Ss53i0+3StA3Ss93ys+3yxA3ClA3Sg93T9IzNLa9f///v/9/f7+/v7//v////////////////////////////////////////////////////////////////////////////////////////////////////7//v7//f///unt/FphzxYd0Bgg3Bcj2xUg2CAmyJqg4f3//v///P7+///+/v7//v7////////////////////////////+/vz///3////+/v/+/v39/vb8/+3z++Dl+MrO9ba56dre9Pr+/P3//f////////////////////////////////////////////////////////////////////////////7+/v3+/P3/+/78/f78/vj5/+zw+tvd98PF8LS74/v9/v79///+/////////////////////////////////////////wAA/////f////7///7//v///P/9/f/9///+//7//v7/////6+78o6rhc4LZepDoh5/zhp3yhZrvgpfsgJbse5LsdozrbobpZ4DpX3noWHHnUWrlS2PjRVzhPlTiOU7iM0nfLkLeKDrcJTXbIjHZIC/WIi7YIizcIy3aJC3bJC7dJjLdKTfeKzrgLT3gLD7fLD7fLD7dLD/cKz/fKjzRcXnT9Pf//f/+/v/8//7////+/////////////////////////////////////////////////////////////////////////////////////////////////////v/+/v/8/v77/v7/w8ryVFvQJS3MHCfNMDbKf4LY5ev8//7///79/f/+/v////7///////////////////////////////7+/P/+/f///v7//v7+//7+/////////////////////////P7+/P7+///+//////////////////////////////////////////////////////////////////////////7//v7+/P/9/P7+///8///+/////////////////f3+/v39///9////////////////////////////////////////AAD////9//7//////////v///v/+/////v///v/+/v78//7+//71+v7Cyu19jNdofdt6j++CmPOCl+6BleuAlOt8ket2jepxiOprg+tkfOtddehXbudRaOZKY+RFXOI/V+A4UN8ySN4uQt0pPNwmN9okNdskMdwlMd0kMdwlNNsmOdwpPN0qPt4rP94sPt0tPtwsP9stPt0sO+A5Rsu8wuz////9/v7+//7///7///////////////////////////////////////////////////////////////////////////////////////////////////////////79/vz7/P3++/3+//7l5/q9wOyusejKzPL19P7//////f/+/f79//3+/////v///////////////////////////////v/9//3///3+/v/9/v////7//v/+///8/v////7///7+//79/v/+/v3///7//////////////////////////////////////////////////////////////////////////////////v/+/v/+/v7+/v7+//79//////7///7+/v3+//v//v7///////////////////////////////////////8AAP/////////////////////////////+///+///+/////vz/+/3//f3//+vv+6Os4WV30GJ13nGH8HmS8H2T63yQ7HqO7XaM63KJ7G6F7WiB6mF66Ft05lhv5FNq401k40dg4kFb4DxV4TdO4TRJ4TBE3yxA3io+3ik+3So+2yw/3C4+3i5A3S5B2zBB3TJC4DJI3DNI3i081XyE2vX1///////9///9//7//f3//P////////////////////////////////////////////////////////////////////////////////////////////////////////7+/v39/v39/f7+/P///////////////////v7+/v/+/////////////////////////////////////////////////////v///v////////////////7///7///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////7//////////////v///v/+/////////////////////////////////////////wAA//////////////////////////////////7///7////+/v/+/P79+/7//f///P//0tjwj5TaXmnVWG3iaILuc47wdo7tdY3rc4vrcYnqb4jpbITpaH/oYnrnXHTnV27nU2roT2flSmLjRFziQFfhPlPgOk/fN0zeNUvdNUvcNUzdN07fPVLfQlfaQFveQ1/lSmHpOkzaWWbN2N34///8/f/8/f79//3//v7+/f/9/////////////////////////////////////////////////////////////////////////////////////////////////////f3+/f39/f7+/P7+/v7+/v7+/v7+///+//79/v39/v7+////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////AAD///////////////////////////////////7///7//////v/+/v/9/v78/f7//v3////2+f/IzfCGjtpZaNBSad1ifO1vh/FzifBziutxiehvhulug+xogepkfuhheehedOhZcOdUa+ZPZ+VJYOREXOJDWeJDV+NDV+NFWuNGXeJMY"+
				"+NUa+VacuVed+tcd+5KW+JUYM/GzvD///7///r8//38//3//v/+/f7+///////////////////////////////////////////////////////////////////////////////////////////////////////+/v7+/v7+/v79/v7+/v7+/v7+/v7+/v7//f3+/v7+//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////8AAP///////////////////////////////////v///v/+///+///+/////////f///P7+/v3///////f7/c3U8I2Y3Vts0VJn2F1x5GZ77WqC722E726E7WuE62mD6Wh/62V77GB261ly6FVv5U5q5Upm5Upj40ph5E1h5U1j4lFp4V505Gd/6mmD82F48U1c12VrzsjO8v///////v79//z9//3//v/+/////v///f///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////wAA///////////////////////////////////+///+//7///7///7////////9///9/v7+/v7+/v/8///7/////f//3eL2qK/ieITSWWzUTmXaVGvhXnXmaHzsan/uaH7rZX3rX3jpWHPmU2/kUWrlUGjlTmbiTGXiTmflVW/oX3vqZoLuYXzsUGXhTFrOhIzU3+T3/////f7//f7//v3+/v7+/////////v/+/v/9////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////AAD//////////////////////////////////v///v///////v////////////7//////v///v7//v7//f3//v7//v7////////y8/3M0vGfq+F3htVfb9NQZNZPZthRa9lPbd1Pa+JOZ+ZOZedQY+dQZOZNZuNOauZUbOxYbetYauFSY9VVZs58htXDx+73+f/9///7/v37/v77/v7+//7///7+///+/////v7//v7///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////8AAP/////////////////////////////////+///+/////////v7//v3///3//////v7//fv//Pz//P/+/f/+/vv+/vz//v///v///v////////f4/+Xm+MbK8KSt44KN4GJx4kJT2jdK2jpQ3z9X3kFZ3zxW4DpQ3kBR2Vtp3ICM3q635dvd9fv7//////7//vz++v79/Pz9/v39/v/9/v79//v///z//f/+/v/+/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////wAA////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////AAA=");
			String bcc[]={amGroupList};
			String from=null;
			StringBuffer emailBody=new StringBuffer();//WelcomeMailSMSDao.getTemplateKeyValue("BT_MAIL_TITLE");
			//String eSubject=WelcomeMailSMSDao.getTemplateKeyValue("BT_MAIL_SUBJECT");
			emailBody.append(WelcomeMailSMSDao.getTemplateKeyValue("FIRST"));
			emailBody.append("<DIV  style='width: 800px; word-wrap: break-word; margin-left:50px;'>");
			emailBody.append("<table border= '3px'   style='border-color:#F00; border-style: solid; background-color:##FFFFFF; border-collapse:collapse;padding-left: 50px; padding-right: 50px;'>");
			emailBody.append("<tr><td style='border:hidden;padding-left: 30px; padding-right: 30px; '>");
			emailBody.append("<table width='100%' border='0'>");
			emailBody.append("<tr ><td align='right'><img  src='"+image+"'></td></tr>");
			emailBody.append("<tr><td style='text-align: center; '> <B >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			emailBody.append("<font style='font-family:arial;font-size:18px;'><U> "+WelcomeMailSMSDao.getTemplateKeyValue("BT_MAIL_TITLE")+" </U></font></B> </td>");
			emailBody.append("</tr><TR> </TR><TR> </TR></br><TR><tr><td style='align: left; '><font style='font-family:arial;font-size:14px;'>Dear "+btMailDetailsDto.getSalutation()+" &nbsp;<U>"+btMailDetailsDto.getCustName()+"</U>,<p></p></font></td></tr><TR> </TR>");
			emailBody.append("<tr><td style='align: left;'><font style='font-family:arial;font-size:14px;'><p style='text-align:justify;'>"+WelcomeMailSMSDao.getTemplateKeyValue("BT_MAIL_BODY1")+"</p></font></td></tr>");
			emailBody.append("<tr><td align='center'><table border='1' width='100%'><tr><td  colspan='2' style='background-color:lightgrey;text-align: center;'>ORDER DETAILS</td></tr>");
			emailBody.append("<tr><td>Account Name</td><td style='text-align: center;'>"+btMailDetailsDto.getAccName()+"</td></tr>");
			emailBody.append("<tr><td>Circuit ID (LSI)</td><td style='text-align: center;font-weight:bold;'>"+btMailDetailsDto.getCircuitId()+"</td></tr>");
			emailBody.append("<tr><td>Commissioning date</td><td style='text-align: center;'>"+btMailDetailsDto.getCommDate()+"</td></tr>");
			emailBody.append("<tr><td>Service number</td><td style='text-align: center;'>"+btMailDetailsDto.getServiceId()+"</td></tr>");
			emailBody.append("<tr><td>Installation address</td><td style='text-align: center;'>"+btMailDetailsDto.getCustAddress()+"</td></tr>");
			if(btMailDetailsDto.getServiceDetailId()==367){
				emailBody.append("<tr><td>MRTG / Infovista URL</td><td style='text-align: center;'><a href='"+btMailDetailsDto.getUrl()+"'>"+btMailDetailsDto.getUrl()+"</a> <br>(Id:"+btMailDetailsDto.getUserid()+",Password:"+btMailDetailsDto.getPassword()+")</td></tr>");
			}
			/*else
				emailBody.append("<tr><td>MRTG / Infovista URL</td><td style='text-align: center;'><a href='"+btMailDetailsDto.getUrl()+"'>"+btMailDetailsDto.getUrl()+"</a> (User ID - "+btMailDetailsDto.getUserid()+", Password - "+btMailDetailsDto.getPassword()+")</td></tr>");
			*///emailBody.append("<tr><td>User ID</td><td style='text-align: center;'>"+btMailDetailsDto.getUserid()+"</td></tr>");
			//emailBody.append("<tr><td>Password</td><td style='text-align: center;'>"+btMailDetailsDto.getPassword()+"</td></tr>");
			emailBody.append("<tr><td>Order number (M6)</td><td style='text-align: center;'>"+btMailDetailsDto.getM6OrderNo()+"</td></tr>");
			emailBody.append("<tr><td>Bandwidth</td><td style='text-align: center;'>"+btMailDetailsDto.getBandwidth()+"</td></tr>");
			emailBody.append("</table></td></tr><br>");
			emailBody.append("<tr><td style='align: left;'><font style='font-family:arial;font-size:14px;'><p style='text-align:justify;'>"+WelcomeMailSMSDao.getTemplateKeyValue("BT_MAIL_BODY2")+"</p></font></td></tr>");
			emailBody.append("<tr><td align='center'><table border='1'>");
			emailBody.append(WelcomeMailSMSDao.getTemplateKeyValue("BT_MAIL_ESCALATION_MATRIX"));
			emailBody.append("</td></tr><tr><td style='align: left;'><font style='font-family:arial;font-size:14px;'><p style='text-align:justify;'>"+WelcomeMailSMSDao.getTemplateKeyValue("BT_MAIL_BODY3")+"</p></font></td></tr>");
			emailBody.append("<TR> </TR><TR> </TR>");
			emailBody.append("<tr><td style='align:left;'><B>Best Regards,</B></td></tr>");
			emailBody.append("<tr><td style='align:left;'>Customer Service</td></tr>");
			emailBody.append("<tr><td style='align:center;'>Bharti Airtel Ltd.</td></tr><tr> </br></tr><tr> </br></tr><tr> </br></tr>");
			emailBody.append("<tr><td style='text-align: center;'>This is an auto generated mail, does not require any signature</td></tr>");
			emailBody.append("</table></td></tr></table></DIV></BODY></HTML>");
			
			//System.out.println("BT MAIL CONTENT : -----> "+ emailBody);
			retStatus=sendMail.postMailWithAttachment(toList,ccList, bcc, eSubject, emailBody.toString(), from,null); 
			if(retStatus==1){
				StringBuffer successMsg=new StringBuffer();
				successMsg.append("Important Order Details appended in Mail : \n");
				successMsg.append("Account Name: "+btMailDetailsDto.getAccName()+"\n");
				successMsg.append("Circuit ID (LSI): "+btMailDetailsDto.getCircuitId()+"\n");
				successMsg.append("Commissioning date: "+btMailDetailsDto.getCommDate()+"\n");
				successMsg.append("Service number: "+btMailDetailsDto.getServiceId()+"\n");
				successMsg.append("Installation address: "+btMailDetailsDto.getCustAddress()+"\n");
				if(btMailDetailsDto.getServiceDetailId()==367)
					successMsg.append("MRTG / Infovista URL: "+btMailDetailsDto.getUrl()+"\n");
				successMsg.append("Order number (M6): "+btMailDetailsDto.getM6OrderNo()+"\n");
				successMsg.append("Bandwidth: "+btMailDetailsDto.getBandwidth()+"\n");
				successMsg.append("ePCD Customer BILLING TRIGGER Welcome Mail Sent Successfully to Customer "+ Arrays.toString(toList)+ " & Acc. Manager: "+Arrays.toString(ccList) + " bccList:  "+Arrays.toString(bcc));
				com.ibm.ioes.utilities.Utility.LOG(successMsg.toString());
			}
		}catch(Exception ex){
			String exc= " Exception occured in Drafting/Sending Mail to Customer after Billing Trigger";
			com.ibm.ioes.utilities.Utility.LOG(exc);
			com.ibm.ioes.utilities.Utility.LOG(ex);
		}
		return retStatus;
	}
	
	/**
	 * To draft BT ePCD SMS for customer as per details present in IN param(WelcomeMailSMSDTO) and send SMS.
	 * @return ArrayList<Integer>
	 * @param WelcomeMailSMSDTO
	 * @author Vipin Saharia
	 * @date 01-Apr-2015
	 */
	//BT SMS
	public ArrayList<Integer> draftAndSendSMScustAlertBT(WelcomeMailSMSDTO btSMSDetailsDto){
		SMSUtil sms=new SMSUtil();
		ArrayList<Integer> reslist = new ArrayList<Integer>();
		try{
		ArrayList<String> phoneNos=new ArrayList<String>();
		phoneNos.add(btSMSDetailsDto.getCustPhone());
		phoneNos.add(btSMSDetailsDto.getAccMngrPhone());
		String smsBody=WelcomeMailSMSDao.getTemplateKeyValue("BT_SMS_BODY1");
		smsBody=smsBody.replaceAll("(?i)\\{\\{orderType\\}\\}", btSMSDetailsDto.getOrderType());
		smsBody=smsBody.replaceAll("(?i)\\{\\{circuitId\\}\\}", String.valueOf(btSMSDetailsDto.getCircuitId()));
		smsBody=smsBody.replaceAll("(?i)\\{\\{address\\}\\}", btSMSDetailsDto.getCustAddress());
		com.ibm.ioes.utilities.Utility.LOG(smsBody);
		if(phoneNos!=null && phoneNos.size()==1){
			for (int i = 0; i < phoneNos.size(); i++){
				reslist.add(sms.sendingSMS((String)phoneNos.get(i), smsBody));
			}
		}
		else if(phoneNos.size()>1){
			for (int i = 0; i < phoneNos.size(); i++){
				try{
					String mobileNumber = (String) phoneNos.get(i);
					if (mobileNumber!=null && mobileNumber.length() == 10){
						reslist.add(sms.sendingSMS(mobileNumber, smsBody.toString()));
					} else if (mobileNumber!=null && mobileNumber.length() > 10){
						reslist.add(sms.sendingSMS(mobileNumber, smsBody.toString()));
					}else {
						com.ibm.ioes.utilities.Utility.LOG("Incorrect mobile number");
					}
				}catch(Exception ex){
					ex.printStackTrace();
					System.err.println("Cannot send messge to no:"+phoneNos.get(i));
				}
			}
		}
		}catch(Exception ex){
			String exc= " Exception occured in Drafting/Sending SMS to Customer after Billing Trigger";
			com.ibm.ioes.utilities.Utility.LOG(exc);
			com.ibm.ioes.utilities.Utility.LOG(ex);
		}
		return reslist;
	}
	/**
	 * To get M6 order no of latest completed service in M6 for all mail eligible LSI's in a Rate renewal order.
	 * Here checking whether order is rate renewal or not. If yes, picking M6 order no. of all mail eligible LSI in map & 
	 * update M6OrderNo for only those LSI which r having it as Null or blank. 
	 * @param conn
	 * @param orderNo
	 * @param custDetailList
	 * @author Vipin Saharia
	 * @date 26-June-2015
	 */
	public void setM6OrderNoLatestServiceIfRRorders(Connection conn,long orderNo,ArrayList<WelcomeMailSMSDTO> custDetailList)throws Exception{
		String msg="In WelcomeMailSMSModel's setM6OrderNoLatestServiceForRRorders method";
		HashMap<Long, String> hmM6OrderNo=null;
		boolean dataFetched=false;
		WelcomeMailSMSDao mailDao=new WelcomeMailSMSDao();
		try{
			if(custDetailList.get(0).getSubChangeTypeId()==5){
			
				for(int i=0; i<custDetailList.size();i++)
					if(/*custDetailList.get(i).getSubChangeTypeId()==5 && */(null==custDetailList.get(i).getM6OrderNo() 
							|| custDetailList.get(i).getM6OrderNo().trim().equals(""))){
						if(dataFetched==false){
							hmM6OrderNo=mailDao.getM6OrderNoLatestService(conn, orderNo);
							dataFetched=true;
						}
						if(hmM6OrderNo.containsKey(custDetailList.get(i).getServiceId()))
							custDetailList.get(i).setM6OrderNo(hmM6OrderNo.get(custDetailList.get(i).getServiceId()));
					}
						
			}
		}catch(Exception ex){
			Utility.LOG(true, true, ex, msg);
			throw ex;
		}
	}
}
