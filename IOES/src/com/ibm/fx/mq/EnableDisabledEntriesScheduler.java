package com.ibm.fx.mq;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Date;
import java.util.TimerTask;

import com.ibm.ioes.actions.FxSchedulerAction;
import com.ibm.ioes.dao.ViewOrderDao;
import com.ibm.ioes.schedular.AccountUpdateSchedular;
import com.ibm.ioes.schedular.BCP_Address_Change;
import com.ibm.ioes.schedular.ComponentDisconnectionSchedular;
import com.ibm.ioes.schedular.DisconnectionSchedular;
import com.ibm.ioes.schedular.ServiceUpdateSchedular;
import com.ibm.ioes.schedular.Service_DisconnectionSchedular;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.MailForProvisioning;
import com.ibm.ioes.utilities.Utility;

public class EnableDisabledEntriesScheduler extends TimerTask{

	public void run() {
		enableEntriesTask();
	}
	
	public static void main(String[] args) {
		new EnableDisabledEntriesScheduler().run();
	}
	
	private void enableEntriesTask() {
		try
		{
			if("Y".equals(Utility.getAppConfigValue("ENABLE_DISABLED_ENTRIES_SCHEDULER"))){
				Utility.LOG(true,true,"EnableDisabledEntriesScheduler job started at : "+new Date());
				executeAllUpdates();
				Utility.LOG(true,true,"EnableDisabledEntriesScheduler job ended at: "+new Date());
			}
		}
		catch (Throwable ex) {
			Utility.onEx_LOG_RET_NEW_EX(ex,"run","EnableDisabledEntriesScheduler ",null,true,true);
			Utility.LOG(true,true,"EnableDisabledEntriesScheduler job ended at : "+new Date());
		}
	}

	private void executeAllUpdates() throws Exception{
		// TODO Auto-generated method stub
		Connection conn = null;
		Statement stmt = null;
		try{
			conn = DbConnection.getConnectionObject();
			
			stmt = conn.createStatement();

			try{
				
				if("Y".equals(Utility.getAppConfigValue("PD_AUTO_BILLING_MANUAL"))){

					stmt.executeUpdate(
							" update ioe.TAUTO_BILLING_LINE "+
							" set SPID=-SPID,ORDERNO=-ORDERNO,PROCESSINGCOUNT=600,UPDATED_DATE=current timestamp "+
							" where (SPID,ORDERNO) in "+ 
							" (SELECT au.spid,au.orderno "+
							" 	FROM ioe.tauto_billing_line au "+
							" 	    inner join ioe.tposervicemaster sermas on sermas.serviceid=au.serviceid "+
							" 	    inner join ioe.tposervicedetails serdet on serdet.serviceproductid=au.spid "+
							" 	    inner join ioe.TSERVICETYPEDETAIL tst on tst.servicedetailid=serdet.servicedetailid "+
							" 	    inner join ioe.tpomaster mas on mas.orderno=au.orderno "+
							" 	    inner join ioe.tm_account acc on acc.accountid=mas.accountid "+
							" 	    inner join ioe.tm_customer_segment_master seg on seg.cust_segment_id=acc.customersegment "+
							" 	    inner join ioe.tbilling_info tbi on tbi.serviceproductid=serdet.serviceproductid "+
							" 	    inner join ioe.TBILLINGMODE_MASTER bm on bm.SLNO= tbi.BILLINGMODE "+
							" 	WHERE AUTOTYPE in ('ON_LOC_RECEIVE','RR','DEMO_REG','PD') and EXCEPTION is not null and au.status<>'BT_SUCCESS' and au.PROCESSINGCOUNT=6 "+
							" 	and sermas.m6_fx_progress_status in ('M6_END-FX_BT_START')  and upper(EXCEPTION) like '%LESS THAN%' "+
							" 	and exception is not null )" 
							);
					
					stmt.executeUpdate(
							" update ioe.TPOSERVICEDETAILS serdet "+
							" set ISAUTOBILLING= "+
							"     (SELECT case when BILLINGMODE=21 then 2 else 0 end "+
							"         FROM ioe.tbilling_info tbi where tbi.serviceproductid=serdet.serviceproductid) "+
							" WHERE SERVICEPRODUCTID in "+ 
							"     (SELECT abs(spid) FROM ioe.TAUTO_BILLING_LINE WHERE PROCESSINGCOUNT=600) "
							);
					
					stmt.executeUpdate("update ioe.TAUTO_BILLING_LINE set PROCESSINGCOUNT=7 where PROCESSINGCOUNT=600");
				}
			}catch(Exception ex){
				Utility.LOG(ex);
			}
			

			try{
				if("Y".equals(Utility.getAppConfigValue("DUPLICATE_WK_REMOVAL"))){

					stmt.executeUpdate(
							" update ioe.tpoworkflowtask "+ 
							" set ORDERNO=-ORDERNO "+
							" where taskid in  "+
							" (SELECT max(TASKID) FROM ioe.tpoworkflowtask "+ 
							"                     where OWNERTYPE_ID=3 and TASKSTATUS_ID=2 "+
							"                     group by orderno "+
							"                     having count(*)>1) "
							);
					
				}
			}catch(Exception ex){
				Utility.LOG(ex);
			}
			
			stmt.executeUpdate("update ioe.TFX_ACCOUNTCREATE set PROCESSING_COUNT=0 where PROCESSING_COUNT=6 and PROCESSINGSTATUS<>3 ");
			stmt.executeUpdate("update ioe.TFX_SERVICECREATE set PROCESSING_COUNT=0 where PROCESSING_COUNT=6 and FX_SCHEDULAR_CREATE_STATUS<>3");
			stmt.executeUpdate("update ioe.TAUTO_BILLING_LINE set PROCESSINGCOUNT=0 where PROCESSINGCOUNT=6 and STATUS<>'BT_SUCCESS'");
			stmt.executeUpdate("update ioe.TFX_RC_CREATE set PROCESSING_COUNT=0 where PROCESSING_COUNT=6 and FX_SCHEDULAR_CREATE_STATUS<>3");
			stmt.executeUpdate("update ioe.TFX_NRC_CREATE set PROCESSING_COUNT=0 where PROCESSING_COUNT=6 and FX_SCHEDULAR_CREATE_STATUS<>3");
			stmt.executeUpdate("update ioe.TFX_PACKAGE_CREATE set PROCESSING_COUNT=0 where PROCESSING_COUNT=6 and FX_SCHEDULAR_CREATE_STATUS<>3");
			stmt.executeUpdate("update ioe.TFX_COMPONENT_CREATE set PROCESSING_COUNT=0 where PROCESSING_COUNT=6 and FX_SCHEDULAR_CREATE_STATUS<>3");
			stmt.executeUpdate("update ioe.TFX_CHARGE_REDIRECTION set PROCESSING_COUNT=0 where PROCESSING_COUNT=6 and PROCESSING_STATUS<>3	");
			stmt.executeUpdate("update ioe.TFX_CHARGE_REDIRECTION set CUMULATIVE_PROCESS_COUNT=0 where CUMULATIVE_PROCESS_COUNT=6 and CUMULATIVE_PROCESS_STATUS<>3");
			stmt.executeUpdate("update ioe.TFX_DISCONNECTION set TRANSACTION_REPUSH_COUNT=0 where TRANSACTION_REPUSH_COUNT=6 and PROCESSING_STATUS<>3");
			stmt.executeUpdate("update ioe.TFX_SERVICE_DISCONNECTION set TRANSACTION_REPUSH_COUNT=0 where TRANSACTION_REPUSH_COUNT in(6,101) and PROCESSING_STATUS<>3");
			stmt.executeUpdate("update IOe.TFX_COMPONENT_CREATE "+
								" set FX_SCHEDULAR_CREATE_STATUS=FX_SCHEDULAR_CREATE_STATUS "+
								" where COMPONENTINFOID in "+
								" (select tcc.COMPONENTINFOID from ioe.TFX_COMPONENT_CREATE tcc "+
								"     inner join ioe.TCOMPONENT_INFO tci on tcc.COMPONENTINFOID=tci.COMPONENTINFOID "+
								" where tcc.FX_SCHEDULAR_CREATE_STATUS=3 and tci.SYSTEM_START_STATUS<>'S')") ;
			

			
			
			
		}
		finally{
			DbConnection.closeStatement(stmt);
			DbConnection.freeConnection(conn);
		}
	}
}
