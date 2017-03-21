package com.ibm.ioes.utilities;

//Tag Name 	Resource Name  	Date		CSR No						Description
//[001]	 	VIPIN SAHARIA	13-Apr-15	CBR_20150202-R2-021036		Sending ePCD and SMS alerts to customer at every order life cycle stage

import java.sql.Connection;

import com.ibm.ioes.dao.WelcomeMailSMSDao;
import com.ibm.ioes.model.ArchivalModel;
import com.ibm.ioes.model.ArchivalModel.ARCHIVAL_CATEGORY;
import com.ibm.ioes.model.WelcomeMailSMSModel;

/**
 * To get whether order is for first time COPC approval or not regarding COPC ePCD mail SMS alerts
 * @return boolean
 * @param Connection
 * @param long
 * @author VIPIN SAHARIA
 * @date 4-Apr-2015
 */
public class CustomerAlertService {

	public boolean getIsCOPCFirstTimeApproval(Connection oldConn, long orderNo){
		String msg="In CustomerAlertService's getIsCOPCFirstTimeApproval method";
		WelcomeMailSMSDao objDao= new WelcomeMailSMSDao();
		boolean isCOPCFirstTimeApproval=false;
		
		try{
			int countCOPCApproval=objDao.getIsCOPCFirstTimeApproval(oldConn, orderNo);
			if(countCOPCApproval==1)
				isCOPCFirstTimeApproval=true;
		}catch (Exception e) {
			Utility.LOG(true, true, e, msg);
		}
		return isCOPCFirstTimeApproval;
	}
	
	
	/**
	 * To update COPC_CUST_NOTIFY flag in TPOSERVICEMASTER_EXTENDED table on first time COPC approval regarding COPC ePCD mail SMS alerts
	 * @return boolean
	 * @param Connection
	 * @param String[]
	 * @author VIPIN SAHARIA
	 * @date 25-Mar-2015
	 */
	public boolean updateCOPCCustNotifyflag(Connection oldConn,String[] serviceList) throws Exception{
		if(null == serviceList || serviceList.length<=0){
			return false;
		}
		String msg="In CustomerAlertService's updateCOPCCustNotifyflag method";
		WelcomeMailSMSDao objDao= new WelcomeMailSMSDao();
		boolean success=true;
		try{
			success=objDao.updateCOPCCustNotifyflag(oldConn, serviceList);
		}catch (Exception e) {
			Utility.LOG(true, true, e, msg);
			throw new Exception();
		}
		return success;
	}
//VIPIN
	/**
	 * Initiates Customer alert process. Calls marking and inserting eligible services in ORDER_LIFECYCLE_EVENT table, then calls method to send alerts
	 * @return void
	 * @author VIPIN SAHARIA
	 * @date 02-Apr-2015
	 */
	public void processEPCDCustomerAlert(){
		try{
			
			Utility.LOG("Calling filterAndComputeEligibleDataForAlert of WelcomeMailSMSModel");
			new WelcomeMailSMSModel().filterAndComputeEligibleDataForAlert();
			Utility.LOG("Calling filterAndComputeEligibleDataForAlert of WelcomeMailSMSModel");
			
			Utility.LOG("Calling createAndSendAlert of WelcomeMailSMSModel");
			new WelcomeMailSMSModel().createAndSendAlert();
			Utility.LOG("Calling createAndSendAlert of WelcomeMailSMSModel");
			
		}catch(Exception ex){
			Utility.LOG(true, true, ex, "Excetion in processEPCDCustomerAlert method of CustomerAlertService");
		}
	}
	/**
	 * Initiates LSI Disconnection status update in tbl TPOSERVICEMASTER_EXTENDED. 
	 * @return void
	 * @author VIPIN SAHARIA
	 * @date 06-Jan-2016 
	 */
	public void processLsiDisconnectionStatusUpdation(){
		
		try{
			Utility.LOG("Calling getAndUpdateLsiDisconnStatusUpdate method of Archival Model");
			new ArchivalModel().getAndUpdateLsiE2EDisconnInfo();
			Utility.LOG("Calling getAndUpdateLsiDisconnStatusUpdate method of Archival Model");
			
		}catch(Exception ex){
			Utility.LOG(true, true, ex, "Excetion in processLsiDisconnectionStatusUpdation method of CustomerAlertService");
		}
	}
	/*public static void main(String[] args) {
		new CustomerAlertService().processLsiDisconnectionStatusUpdation();
		//new ArchivalModel().archivalService(ARCHIVAL_CATEGORY.CANCEL, "VIPIN SAHARIA");
	}*/
	
}
