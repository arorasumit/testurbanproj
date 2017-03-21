package com.ibm.ioes.dao;

//Tag Name 	Resource Name  	Date		CSR No						Description
//[001]	 	VIPIN SAHARIA	13-Apr-15	CBR_20150202-R2-021036		Sending ePCD and SMS alerts to customer at every order life cycle stage

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ibm.ioes.model.WelcomeMailSMSModel;
import com.ibm.ioes.newdesign.dto.WelcomeMailSMSDTO;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Utility;

public class WelcomeMailSMSDao {
	//private static final String MRTG_INFOVISTA_URL="http://smartnet.bharti.com/VPortal";
	private static final String COPC_EVENT="COPC";
	private static final String BT_EVENT="BT";
	private static final int MAIL_FLAG=1;
	private static final int SMS_FLAG=2;
	private static final int TSM_EXT_TO_PROCESS=2;
	private static final int TSM_EXT_NOT_ELIGIBLE=13;
	private static final String PS_INIT_WIP="INIT_WIP";
	private static final String PS_PROCESS_WIP="PROCESS_WIP";
	private static final String PS_TO_PROCESS="TO_PROCESS";
	private static final String CONF_SERVICETYPEID="221";
	private static final String CONF_SERVICEDETAILID_BANDWIDTH_LINE="367";
	private static final String CONF_SERVICEDETAILID_ANG_LINE="377";

	
	private String sqlUpdateflagTSMExtendedTableCOPC="UPDATE ioe.TPOSERVICEMASTER_EXTENDED SET COPC_CUST_NOTIFY=? WHERE COPC_CUST_NOTIFY=?";
	private String sqlUpdateflagTSMExtendedTableBT="UPDATE ioe.TPOSERVICEMASTER_EXTENDED SET BT_CUST_NOTIFY=? WHERE BT_CUST_NOTIFY=?";
	/**
	 * To update COPC/BT_CUST_NOTIFY flag from provided oldvalue to provided newvalue respect to provided event(s) in TPOSERVICEMASTER_EXTENDED TABLE
	 * @return boolean
	 * @param Connection
	 * @param int
	 * @param int
	 * @param String
	 * @author VIPIN SAHARIA
	 * @date 25-Mar-2015
	 */
public boolean updateflagTSMExtendedTable(Connection oldConn,int oldvalue,int newvalue, String event) throws Exception{
	if(oldvalue==0){
		return false;
	}
	if(newvalue==0){
		return false;
	}
	String msg="In WelcomeMailSMSDao's updateflagTSMExtendedTable method";
	PreparedStatement psUpdateFlagTSMEx =null;
	Connection connection=null;
	int result=0;
	boolean success=false;
	boolean isConnCreated=false;
	try{
		if(null==oldConn){
			isConnCreated=true;
			connection=DbConnection.getConnectionObject();
			connection.setAutoCommit(false);
		}else
			connection=oldConn;
		if(event.equalsIgnoreCase(COPC_EVENT))
			psUpdateFlagTSMEx= connection.prepareStatement(sqlUpdateflagTSMExtendedTableCOPC);
		else
			psUpdateFlagTSMEx= connection.prepareStatement(sqlUpdateflagTSMExtendedTableBT);
		psUpdateFlagTSMEx.setInt(1, newvalue);
		psUpdateFlagTSMEx.setInt(2,oldvalue);
		result=psUpdateFlagTSMEx.executeUpdate();
		if(result>=0)
			success=true;
		if(success==true && null==oldConn){
			connection.commit();
		}
	}catch (Exception e) {
		Utility.LOG(true, true, e, msg);
		connection.rollback();
		throw new Exception();
	}finally{
		try{
			DbConnection.closePreparedStatement(psUpdateFlagTSMEx);
			if(isConnCreated)
				DbConnection.freeConnection(connection);
		}catch (Exception e){
			Utility.LOG(true, true, e, msg);
		}
	}
	return success;
} 

		/**
		 * To update PRE_STATUS from provided oldPreStatus to provided newPreStatus respect to provided event(s) in ORDER_LIFECYCLE_EVENT TABLE
		 * @return boolean
		 * @param Connection
		 * @param String
		 * @param String
		 * @param String
		 * @author VIPIN SAHARIA
		 * @date 20-Mar-2015
		 */
	public boolean markServicePreStatus(Connection oldConn,String oldPreStatus,String newPreStatus, String event) throws Exception{
		if(null == oldPreStatus || "".equals(oldPreStatus.trim())){
			return false;
		}
		if(null == newPreStatus || "".equals(newPreStatus.trim())){
			return false;
		}
		String msg="In WelcomeMailSMSDao's markServicePreStatus method";
		StringBuffer sqlMarkServicePreStatus=new StringBuffer("UPDATE ioe.ORDER_LIFECYCLE_EVENT SET PRE_STATUS=? WHERE  MODULE='CUSTOMER_NOTIFY' and PRE_STATUS=? and EVENT in (");
		PreparedStatement psUpdatePreStatus =null;
		Connection connection=null;
		int result=0;
		boolean success=false;
		boolean isConnCreated=false;
		try{	
			if(null==oldConn){
				isConnCreated=true;
				connection=DbConnection.getConnectionObject();
				connection.setAutoCommit(false);
			}else
				connection=oldConn;
			sqlMarkServicePreStatus.append(event+")");
			psUpdatePreStatus= connection.prepareStatement(sqlMarkServicePreStatus.toString());
			psUpdatePreStatus.setString(1, newPreStatus);
			psUpdatePreStatus.setString(2,oldPreStatus);
			result=psUpdatePreStatus.executeUpdate();
			if(result>=0)
				success=true;
			if(success==true && null==oldConn){
				connection.commit();
			}
		}catch (Exception e) {
			Utility.LOG(true, true, e, msg);
			connection.rollback();
			throw new Exception();
		}finally{
			try{
				DbConnection.closePreparedStatement(psUpdatePreStatus);
				if(isConnCreated)
					DbConnection.freeConnection(connection);
			}catch (Exception e){
				Utility.LOG(true, true, e, msg);
			}
		}
		return success;
	} 

	private String sqlMarkNonChannelServicesCOPC="UPDATE IOE.TPOSERVICEMASTER_EXTENDED SET COPC_CUST_NOTIFY=? WHERE COPC_CUST_NOTIFY=? AND SERVICEID NOT IN( "+
			"SELECT TSM_E.SERVICEID FROM IOE.TPOSERVICEMASTER_EXTENDED TSM_E "+
			"INNER JOIN IOE.TPOSERVICEMASTER TSM ON TSM_E.SERVICEID=TSM.SERVICEID AND TSM_E.COPC_CUST_NOTIFY=? "+
			"INNER JOIN IOE.TPOMASTER TM ON TSM.ORDERNO=TM.ORDERNO AND TM.CHANGETYPEID IN(2,5,141) "+
			"INNER JOIN IOE.TM_ACCOUNT ACC ON TM.ACCOUNTID=ACC.ACCOUNTID "+
			"INNER JOIN IOE.TM_CUSTOMER_SEGMENT_MASTER SEG ON ACC.CUSTOMERSEGMENT=SEG.CUST_SEGMENT_ID AND UPPER(SEG.DESCRIPTION) LIKE '%CHANNEL%' "+
			")"; 
	private String sqlMarkNonChannelServicesBT="UPDATE IOE.TPOSERVICEMASTER_EXTENDED SET BT_CUST_NOTIFY=? WHERE BT_CUST_NOTIFY=? AND SERVICEID NOT IN( "+
			"SELECT TSM_E.SERVICEID FROM IOE.TPOSERVICEMASTER_EXTENDED TSM_E "+
			"INNER JOIN IOE.TPOSERVICEMASTER TSM ON TSM_E.SERVICEID=TSM.SERVICEID AND TSM_E.BT_CUST_NOTIFY=? "+
			"INNER JOIN IOE.TPOMASTER TM ON TSM.ORDERNO=TM.ORDERNO AND TM.CHANGETYPEID IN(2,5,141) "+
			"INNER JOIN IOE.TM_ACCOUNT ACC ON TM.ACCOUNTID=ACC.ACCOUNTID "+
			"INNER JOIN IOE.TM_CUSTOMER_SEGMENT_MASTER SEG ON ACC.CUSTOMERSEGMENT=SEG.CUST_SEGMENT_ID AND UPPER(SEG.DESCRIPTION) LIKE '%CHANNEL%' "+
			")";
	/**
	 * To update COPC/BT_CUST_NOTIFY flag from 2 to 13 for all Services in TPOSERVICEMASTER_EXTENDED TABLE whose A/C is not Channel and OrderType not in('New','Sol.Change','RR')
	 * @return boolean
	 * @param Connection
	 * @param String
	 * @author VIPIN SAHARIA
	 * @date 20-Mar-2015
	 */
public boolean markNonChannelServices(Connection oldConn,String event) throws Exception{
	String msg="In WelcomeMailSMSDao's markNonChannelServices method";
	PreparedStatement psMarkNonChannel =null;
	Connection connection=null;
	int updateCount=-1;
	boolean success=false;
	boolean isConnCreated=false;
	try{	
		if(null==oldConn){
			isConnCreated=true;
			connection=DbConnection.getConnectionObject();
			connection.setAutoCommit(false);
		}else
			connection=oldConn;
		if(event.equalsIgnoreCase(COPC_EVENT))
			psMarkNonChannel= connection.prepareStatement(sqlMarkNonChannelServicesCOPC);
		else
			psMarkNonChannel= connection.prepareStatement(sqlMarkNonChannelServicesBT);
		psMarkNonChannel.setInt(1, TSM_EXT_NOT_ELIGIBLE);
		psMarkNonChannel.setInt(2, TSM_EXT_TO_PROCESS);
		psMarkNonChannel.setInt(3, TSM_EXT_TO_PROCESS);
		updateCount=psMarkNonChannel.executeUpdate();
		if(updateCount==PreparedStatement.EXECUTE_FAILED){
			success=false;
			throw new Exception(" Exception occurred during executing markNonChannelServices() of WelcomeMailSMSDao");
		}
		else if(updateCount>=0)
			success=true;
		if(success==true && null==oldConn){
			connection.commit();
		}
	}catch (Exception e) {
		Utility.LOG(true, true, e, msg);
		connection.rollback();
		throw new Exception();
	}finally{
		try{
			DbConnection.closePreparedStatement(psMarkNonChannel);
			if(isConnCreated)
				DbConnection.freeConnection(connection);
		}catch (Exception e){
			Utility.LOG(true, true, e, msg);
		}
	}
	return success;
}	

private String sqlCOPC_EligibleServices = 
		" SELECT distinct TSM_E.SERVICEID FROM IOE.TPOSERVICEMASTER_EXTENDED TSM_E  "+
		" INNER JOIN IOE.TPOSERVICEMASTER TSM ON TSM_E.SERVICEID=TSM.SERVICEID AND TSM_E.COPC_CUST_NOTIFY=? "+ 
		"     AND TSM.SERVICETYPEID IN("+CONF_SERVICETYPEID+") "+ 
		" INNER JOIN IOE.TPRODUCTTYPE PROD ON TSM.PRODUCTID=PROD.PRODUCTTYPEID "+ 
		" INNER JOIN IOE.TSERVICESUBTYPE SUBPROD ON TSM.SUBPRODUCTID=SUBPROD.SERVICESUBTYPEID "+ 
		" INNER JOIN IOE.TPOMASTER TM ON TSM.ORDERNO=TM.ORDERNO AND TM.CHANGETYPEID IN(2,5,141) "+ 
		" INNER JOIN IOE.TM_ACCOUNT ACC ON TM.ACCOUNTID=ACC.ACCOUNTID "+ 
		" INNER JOIN IOE.TM_CUSTOMER_SEGMENT_MASTER SEG ON ACC.CUSTOMERSEGMENT=SEG.CUST_SEGMENT_ID AND UPPER(SEG.DESCRIPTION) LIKE '%CHANNEL%' "+
		" INNER JOIN ioe.TDISCONNECTION_HISTORY DIS_ANG on TSM_E.SERVICEID=DIS_ANG.MAIN_SERVICEID "+ 
		" INNER JOIN ioe.TPOSERVICEDETAILS TSD_ANG on DIS_ANG.SERVICE_PRODUCT_ID=TSD_ANG.SERVICEPRODUCTID "+ 
		"     and TSD_ANG.SERVICEDETAILID in ("+CONF_SERVICEDETAILID_ANG_LINE+") and TSD_ANG.ADDITIONALNODE=0 "+
		" INNER JOIN ioe.TDISCONNECTION_HISTORY DIS_BW on TSM_E.SERVICEID=DIS_BW.MAIN_SERVICEID "+
		" INNER JOIN ioe.TPOSERVICEDETAILS TSD_BW on DIS_BW.SERVICE_PRODUCT_ID=TSD_BW.SERVICEPRODUCTID "+ 
		"     and TSD_BW.SERVICEDETAILID in ("+CONF_SERVICEDETAILID_BANDWIDTH_LINE+") and TSD_BW.ADDITIONALNODE=0 "+ 
		" where upper(SUBPROD.SERVICESUBTYPENAME) LIKE '%ILP%' AND (TSM.SERVICETYPEID IN("+CONF_SERVICETYPEID+")) "; 


private String sqlBT_EligibleServices = 
		" SELECT distinct TSM_E.SERVICEID FROM IOE.TPOSERVICEMASTER_EXTENDED TSM_E  "+
		" INNER JOIN IOE.TPOSERVICEMASTER TSM ON TSM_E.SERVICEID=TSM.SERVICEID AND TSM_E.BT_CUST_NOTIFY=? "+ 
		"     AND TSM.SERVICETYPEID IN("+CONF_SERVICETYPEID+") "+ 
		" INNER JOIN IOE.TPRODUCTTYPE PROD ON TSM.PRODUCTID=PROD.PRODUCTTYPEID "+ 
		" INNER JOIN IOE.TSERVICESUBTYPE SUBPROD ON TSM.SUBPRODUCTID=SUBPROD.SERVICESUBTYPEID "+ 
		" INNER JOIN IOE.TPOMASTER TM ON TSM.ORDERNO=TM.ORDERNO AND TM.CHANGETYPEID IN(2,5,141) "+ 
		" INNER JOIN IOE.TM_ACCOUNT ACC ON TM.ACCOUNTID=ACC.ACCOUNTID "+ 
		" INNER JOIN IOE.TM_CUSTOMER_SEGMENT_MASTER SEG ON ACC.CUSTOMERSEGMENT=SEG.CUST_SEGMENT_ID AND UPPER(SEG.DESCRIPTION) LIKE '%CHANNEL%' "+
		" INNER JOIN ioe.TDISCONNECTION_HISTORY DIS_ANG on TSM_E.SERVICEID=DIS_ANG.MAIN_SERVICEID "+ 
		" INNER JOIN ioe.TPOSERVICEDETAILS TSD_ANG on DIS_ANG.SERVICE_PRODUCT_ID=TSD_ANG.SERVICEPRODUCTID "+ 
		"     and TSD_ANG.SERVICEDETAILID in ("+CONF_SERVICEDETAILID_ANG_LINE+") and TSD_ANG.ADDITIONALNODE=0 "+
		" INNER JOIN ioe.TDISCONNECTION_HISTORY DIS_BW on TSM_E.SERVICEID=DIS_BW.MAIN_SERVICEID "+
		" INNER JOIN ioe.TPOSERVICEDETAILS TSD_BW on DIS_BW.SERVICE_PRODUCT_ID=TSD_BW.SERVICEPRODUCTID "+ 
		"     and TSD_BW.SERVICEDETAILID in ("+CONF_SERVICEDETAILID_BANDWIDTH_LINE+") and TSD_BW.ADDITIONALNODE=0 "+ 
		" where upper(SUBPROD.SERVICESUBTYPENAME) LIKE '%ILP%' AND (TSM.SERVICETYPEID IN("+CONF_SERVICETYPEID+")) "; 



private String sqlMarkNonEligibleServicesNoANGCOPC="UPDATE IOE.TPOSERVICEMASTER_EXTENDED SET COPC_CUST_NOTIFY=? WHERE COPC_CUST_NOTIFY=? AND SERVICEID NOT IN( "+
		"SELECT TSM_E.SERVICEID FROM IOE.TPOSERVICEMASTER_EXTENDED TSM_E "+
		"INNER JOIN ioe.TDISCONNECTION_HISTORY DIS on TSM_E.SERVICEID=DIS.MAIN_SERVICEID AND TSM_E.COPC_CUST_NOTIFY=? "+
		"INNER JOIN ioe.TPOSERVICEDETAILS TSD on DIS.SERVICE_PRODUCT_ID=TSD.SERVICEPRODUCTID and TSD.SERVICEDETAILID in ("+CONF_SERVICEDETAILID_ANG_LINE+") and TSD.ADDITIONALNODE=0 "+
		")"; 
private String sqlMarkNonEligibleServicesNoBandwidthCOPC="UPDATE IOE.TPOSERVICEMASTER_EXTENDED SET COPC_CUST_NOTIFY=? WHERE COPC_CUST_NOTIFY=? AND SERVICEID NOT IN( "+
		"SELECT TSM_E.SERVICEID FROM IOE.TPOSERVICEMASTER_EXTENDED TSM_E "+
		"INNER JOIN ioe.TDISCONNECTION_HISTORY DIS on TSM_E.SERVICEID=DIS.MAIN_SERVICEID AND TSM_E.COPC_CUST_NOTIFY=? "+
		"INNER JOIN ioe.TPOSERVICEDETAILS TSD on DIS.SERVICE_PRODUCT_ID=TSD.SERVICEPRODUCTID and TSD.SERVICEDETAILID in ("+CONF_SERVICEDETAILID_BANDWIDTH_LINE+") and TSD.ADDITIONALNODE=0 "+
		")";
private String sqlMarkNonEligibleServicesNoANGBT="UPDATE IOE.TPOSERVICEMASTER_EXTENDED SET BT_CUST_NOTIFY=? WHERE BT_CUST_NOTIFY=? AND SERVICEID NOT IN( "+
		"SELECT TSM_E.SERVICEID FROM IOE.TPOSERVICEMASTER_EXTENDED TSM_E "+
		"INNER JOIN ioe.TDISCONNECTION_HISTORY DIS on TSM_E.SERVICEID=DIS.MAIN_SERVICEID AND TSM_E.BT_CUST_NOTIFY=? "+
		"INNER JOIN ioe.TPOSERVICEDETAILS TSD on DIS.SERVICE_PRODUCT_ID=TSD.SERVICEPRODUCTID and TSD.SERVICEDETAILID in ("+CONF_SERVICEDETAILID_ANG_LINE+") and TSD.ADDITIONALNODE=0 "+
		")"; 
private String sqlMarkNonEligibleServicesNoBandwidthBT="UPDATE IOE.TPOSERVICEMASTER_EXTENDED SET BT_CUST_NOTIFY=? WHERE BT_CUST_NOTIFY=? AND SERVICEID NOT IN( "+
		"SELECT TSM_E.SERVICEID FROM IOE.TPOSERVICEMASTER_EXTENDED TSM_E "+
		"INNER JOIN ioe.TDISCONNECTION_HISTORY DIS on TSM_E.SERVICEID=DIS.MAIN_SERVICEID AND TSM_E.BT_CUST_NOTIFY=? "+
		"INNER JOIN ioe.TPOSERVICEDETAILS TSD on DIS.SERVICE_PRODUCT_ID=TSD.SERVICEPRODUCTID and TSD.SERVICEDETAILID in ("+CONF_SERVICEDETAILID_BANDWIDTH_LINE+") and TSD.ADDITIONALNODE=0 "+
		")";

/*private String sqlMarkNonEligibleServicesNoANGCOPC="UPDATE IOE.TPOSERVICEMASTER_EXTENDED SET COPC_CUST_NOTIFY=? WHERE COPC_CUST_NOTIFY=? AND SERVICEID NOT IN( "+
		"SELECT TSM_E.SERVICEID FROM IOE.TPOSERVICEMASTER_EXTENDED TSM_E "+
		"INNER JOIN ioe.TDISCONNECTION_HISTORY DIS on TSM_E.SERVICEID=DIS.MAIN_SERVICEID AND TSM_E.COPC_CUST_NOTIFY=? "+
		"INNER JOIN ioe.TPOSERVICEDETAILS TSD on DIS.SERVICE_PRODUCT_ID=TSD.SERVICEPRODUCTID and TSD.SERVICEDETAILID in(273,377,770) and TSD.ADDITIONALNODE=0 "+
		")"; 
private String sqlMarkNonEligibleServicesNoBandwidthCOPC="UPDATE IOE.TPOSERVICEMASTER_EXTENDED SET COPC_CUST_NOTIFY=? WHERE COPC_CUST_NOTIFY=? AND SERVICEID NOT IN( "+
		"SELECT TSM_E.SERVICEID FROM IOE.TPOSERVICEMASTER_EXTENDED TSM_E "+
		"INNER JOIN ioe.TDISCONNECTION_HISTORY DIS on TSM_E.SERVICEID=DIS.MAIN_SERVICEID AND TSM_E.COPC_CUST_NOTIFY=? "+
		"INNER JOIN ioe.TPOSERVICEDETAILS TSD on DIS.SERVICE_PRODUCT_ID=TSD.SERVICEPRODUCTID and TSD.SERVICEDETAILID in(222,367,768) and TSD.ADDITIONALNODE=0 "+
		")";
private String sqlMarkNonEligibleServicesNoANGBT="UPDATE IOE.TPOSERVICEMASTER_EXTENDED SET BT_CUST_NOTIFY=? WHERE BT_CUST_NOTIFY=? AND SERVICEID NOT IN( "+
		"SELECT TSM_E.SERVICEID FROM IOE.TPOSERVICEMASTER_EXTENDED TSM_E "+
		"INNER JOIN ioe.TDISCONNECTION_HISTORY DIS on TSM_E.SERVICEID=DIS.MAIN_SERVICEID AND TSM_E.BT_CUST_NOTIFY=? "+
		"INNER JOIN ioe.TPOSERVICEDETAILS TSD on DIS.SERVICE_PRODUCT_ID=TSD.SERVICEPRODUCTID and TSD.SERVICEDETAILID in(273,377,770) and TSD.ADDITIONALNODE=0 "+
		")"; 
private String sqlMarkNonEligibleServicesNoBandwidthBT="UPDATE IOE.TPOSERVICEMASTER_EXTENDED SET BT_CUST_NOTIFY=? WHERE BT_CUST_NOTIFY=? AND SERVICEID NOT IN( "+
		"SELECT TSM_E.SERVICEID FROM IOE.TPOSERVICEMASTER_EXTENDED TSM_E "+
		"INNER JOIN ioe.TDISCONNECTION_HISTORY DIS on TSM_E.SERVICEID=DIS.MAIN_SERVICEID AND TSM_E.BT_CUST_NOTIFY=? "+
		"INNER JOIN ioe.TPOSERVICEDETAILS TSD on DIS.SERVICE_PRODUCT_ID=TSD.SERVICEPRODUCTID and TSD.SERVICEDETAILID in(222,367,768) and TSD.ADDITIONALNODE=0 "+
		")";

*/


/**
 * To update COPC/BT_CUST_NOTIFY flag from 2 to 13 for all Services in TPOSERVICEMASTER_EXTENDED TABLE not containing bandwidth+ANG lines.
 * @return boolean
 * @param Connection
 * @param String
 * @author VIPIN SAHARIA
 * @date 23-Mar-2015
 */
public boolean markNonANGAndBandwidthServices(Connection oldConn,String event) throws Exception{
String msg="In WelcomeMailSMSDao's markNonANGAndBandwidthServices method";
PreparedStatement psMarkNonANG =null;
PreparedStatement psMarkNonBandwidth =null;
Connection connection=null;
boolean successNonANG=true;
boolean successNonBandwidth=true;
boolean isConnCreated=false;
int upCountNonANG=-1;
int upCountNonBandwidth=-1;
try{
	if(null==oldConn){
		isConnCreated=true;
		connection=DbConnection.getConnectionObject();
		connection.setAutoCommit(false);
	}else
		connection=oldConn;
	if(event.equalsIgnoreCase(COPC_EVENT))
		psMarkNonANG= connection.prepareStatement(sqlMarkNonEligibleServicesNoANGCOPC);
	else
		psMarkNonANG= connection.prepareStatement(sqlMarkNonEligibleServicesNoANGBT);
	psMarkNonANG.setInt(1, TSM_EXT_NOT_ELIGIBLE);
	psMarkNonANG.setInt(2, TSM_EXT_TO_PROCESS);
	psMarkNonANG.setInt(3, TSM_EXT_TO_PROCESS);
	upCountNonANG=psMarkNonANG.executeUpdate();
	//System.out.println("markNonANGAndBandwidthServices updateCount1: "+upCountNonANG);
	if(upCountNonANG==PreparedStatement.EXECUTE_FAILED){
		successNonANG=false;
		throw new Exception(" Exception occurred during executing markNonANGAndBandwidthServices() of WelcomeMailSMSDao");
	}
	//--------------
	if(event.equalsIgnoreCase(COPC_EVENT))
		psMarkNonBandwidth= connection.prepareStatement(sqlMarkNonEligibleServicesNoBandwidthCOPC);
	else
		psMarkNonBandwidth= connection.prepareStatement(sqlMarkNonEligibleServicesNoBandwidthBT);
	psMarkNonBandwidth.setInt(1, TSM_EXT_NOT_ELIGIBLE);
	psMarkNonBandwidth.setInt(2, TSM_EXT_TO_PROCESS);
	psMarkNonBandwidth.setInt(3, TSM_EXT_TO_PROCESS);

	upCountNonBandwidth=psMarkNonBandwidth.executeUpdate();
	//System.out.println("markNonANGAndBandwidthServices updateCount2: "+upCountNonBandwidth);
	if(upCountNonBandwidth==PreparedStatement.EXECUTE_FAILED){
		successNonBandwidth=false;
		throw new Exception(" Exception occurred during executing markNonANGAndBandwidthServices() of WelcomeMailSMSDao");
	}
	if(successNonANG && successNonBandwidth){
		if(null==oldConn)
			connection.commit();
	}
}catch (Exception e) {
	Utility.LOG(true, true, e, msg);
	connection.rollback();
	throw new Exception();
}finally{
	try{
		DbConnection.closePreparedStatement(psMarkNonANG);
		DbConnection.closePreparedStatement(psMarkNonBandwidth);
		if(isConnCreated)
			DbConnection.freeConnection(connection);
	}catch (Exception e){
		Utility.LOG(true, true, e, msg);
	}
}
return successNonANG && successNonBandwidth;
}

private String sqlMarkNonILPMPLSServicesCOPC="UPDATE IOE.TPOSERVICEMASTER_EXTENDED SET COPC_CUST_NOTIFY=? WHERE COPC_CUST_NOTIFY=? AND SERVICEID NOT IN( "+
"SELECT TSM_E.SERVICEID FROM IOE.TPOSERVICEMASTER_EXTENDED TSM_E "+
"INNER JOIN IOE.TPOSERVICEMASTER TSM ON TSM_E.SERVICEID=TSM.SERVICEID AND TSM.SERVICETYPEID IN("+CONF_SERVICETYPEID+") AND TSM_E.COPC_CUST_NOTIFY=? "+
"INNER JOIN IOE.TPRODUCTTYPE PROD ON TSM.PRODUCTID=PROD.PRODUCTTYPEID "+
"INNER JOIN IOE.TSERVICESUBTYPE SUBPROD ON TSM.SUBPRODUCTID=SUBPROD.SERVICESUBTYPEID "+
"WHERE upper(SUBPROD.SERVICESUBTYPENAME) LIKE '%ILP%' AND (TSM.SERVICETYPEID IN("+CONF_SERVICETYPEID+")) "+
")";

/*private String sqlMarkNonILPMPLSServicesCOPC="UPDATE IOE.TPOSERVICEMASTER_EXTENDED SET COPC_CUST_NOTIFY=? WHERE COPC_CUST_NOTIFY=? AND SERVICEID NOT IN( "+
"SELECT TSM_E.SERVICEID FROM IOE.TPOSERVICEMASTER_EXTENDED TSM_E "+
"INNER JOIN IOE.TPOSERVICEMASTER TSM ON TSM_E.SERVICEID=TSM.SERVICEID AND TSM.SERVICETYPEID IN(141,221,321) AND TSM_E.COPC_CUST_NOTIFY=? "+
"INNER JOIN IOE.TPRODUCTTYPE PROD ON TSM.PRODUCTID=PROD.PRODUCTTYPEID "+
"INNER JOIN IOE.TSERVICESUBTYPE SUBPROD ON TSM.SUBPRODUCTID=SUBPROD.SERVICESUBTYPEID "+
"WHERE (upper(PROD.PRODUCTNAME) LIKE '%MPLS%' AND (TSM.SERVICETYPEID IN(141,321))) OR (upper(SUBPROD.SERVICESUBTYPENAME) LIKE '%ILP%' AND (TSM.SERVICETYPEID IN(221))) "+
")"; 
*/
private String sqlMarkNonILPMPLSServicesBT="UPDATE IOE.TPOSERVICEMASTER_EXTENDED SET BT_CUST_NOTIFY=? WHERE BT_CUST_NOTIFY=? AND SERVICEID NOT IN( "+
"SELECT TSM_E.SERVICEID FROM IOE.TPOSERVICEMASTER_EXTENDED TSM_E "+
"INNER JOIN IOE.TPOSERVICEMASTER TSM ON TSM_E.SERVICEID=TSM.SERVICEID AND TSM.SERVICETYPEID IN("+CONF_SERVICETYPEID+") AND TSM_E.BT_CUST_NOTIFY=? "+
"INNER JOIN IOE.TPRODUCTTYPE PROD ON TSM.PRODUCTID=PROD.PRODUCTTYPEID "+
"INNER JOIN IOE.TSERVICESUBTYPE SUBPROD ON TSM.SUBPRODUCTID=SUBPROD.SERVICESUBTYPEID "+
"WHERE upper(SUBPROD.SERVICESUBTYPENAME) LIKE '%ILP%' AND (TSM.SERVICETYPEID IN("+CONF_SERVICETYPEID+")) "+
")";

/*private String sqlMarkNonILPMPLSServicesBT="UPDATE IOE.TPOSERVICEMASTER_EXTENDED SET BT_CUST_NOTIFY=? WHERE BT_CUST_NOTIFY=? AND SERVICEID NOT IN( "+
"SELECT TSM_E.SERVICEID FROM IOE.TPOSERVICEMASTER_EXTENDED TSM_E "+
"INNER JOIN IOE.TPOSERVICEMASTER TSM ON TSM_E.SERVICEID=TSM.SERVICEID AND TSM.SERVICETYPEID IN(221) AND TSM_E.BT_CUST_NOTIFY=? "+
"INNER JOIN IOE.TPRODUCTTYPE PROD ON TSM.PRODUCTID=PROD.PRODUCTTYPEID "+
"INNER JOIN IOE.TSERVICESUBTYPE SUBPROD ON TSM.SUBPRODUCTID=SUBPROD.SERVICESUBTYPEID "+
"WHERE (upper(PROD.PRODUCTNAME) LIKE '%MPLS%' AND (TSM.SERVICETYPEID IN(141,321))) OR (upper(SUBPROD.SERVICESUBTYPENAME) LIKE '%ILP%' AND (TSM.SERVICETYPEID IN(221))) "+
")";*/

/**
 * To update COPC/BT_CUST_NOTIFY flag from 2 to 13 for non ISP/MPLS Services and not containing ILP/MPLS in product attribute field in TPOSERVICEMASTER_EXTENDED TABLE.
 * @return boolean
 * @param Connection
 * @param String
 * @author VIPIN SAHARIA
 * @date 23-Mar-2015
 */
public boolean markNonILPMPLSServices(Connection oldConn,String event) throws Exception{
String msg="In WelcomeMailSMSDao's markNonILPMPLSServices method";
PreparedStatement psMarkNonILPMPLS =null;
boolean successNonILPMPLS=false;
Connection connection=null;
boolean isConnCreated=false;
int upCountNonILPMPLS=-1;
try{	
	if(null==oldConn){
		isConnCreated=true;
		connection=DbConnection.getConnectionObject();
		connection.setAutoCommit(false);
	}else
		connection=oldConn;
	if(event.equalsIgnoreCase(COPC_EVENT))
		psMarkNonILPMPLS= connection.prepareStatement(sqlMarkNonILPMPLSServicesCOPC);
	else
		psMarkNonILPMPLS= connection.prepareStatement(sqlMarkNonILPMPLSServicesBT);
	psMarkNonILPMPLS.setInt(1, TSM_EXT_NOT_ELIGIBLE);
	psMarkNonILPMPLS.setInt(2, TSM_EXT_TO_PROCESS);
	psMarkNonILPMPLS.setInt(3, TSM_EXT_TO_PROCESS);
	upCountNonILPMPLS=psMarkNonILPMPLS.executeUpdate();
	//System.out.println("markNonILPMPLSServices : "+upCountNonILPMPLS);
	if(upCountNonILPMPLS==PreparedStatement.EXECUTE_FAILED){
		successNonILPMPLS=false;
		throw new Exception(" Exception occurred during executing markNonILPMPLSServices() of WelcomeMailSMSDao");
	}else if(upCountNonILPMPLS>=0){
		successNonILPMPLS=true;
	}
	if(successNonILPMPLS==true && isConnCreated==true)
		connection.commit();
}catch (Exception e) {
	Utility.LOG(true, true, e, msg);
	throw new Exception();
}finally{
	try{
		DbConnection.closePreparedStatement(psMarkNonILPMPLS);
		if(isConnCreated)
			DbConnection.freeConnection(connection);
	}catch (Exception e){
		Utility.LOG(true, true, e, msg);
	}
}
return successNonILPMPLS;
}

private String sqlinsertIntoOrderLifeCycleEventCOPC="INSERT INTO ioe.ORDER_LIFECYCLE_EVENT(MODULE,SERVICEID,ORDERNO,EVENT,PRE_STATUS)"+
" SELECT 'CUSTOMER_NOTIFY' AS MODULE, TSM.SERVICEID, TSM.ORDERNO, 'COPC' AS EVENT, 'INIT' AS PRE_STATUS "+
"FROM IOE.TPOSERVICEMASTER_EXTENDED TSM_E "+
"INNER JOIN IOE.TPOSERVICEMASTER TSM on TSM_E.SERVICEID=TSM.SERVICEID AND TSM_E.COPC_CUST_NOTIFY=? ";
private String sqlinsertIntoOrderLifeCycleEventBT="INSERT INTO ioe.ORDER_LIFECYCLE_EVENT(MODULE,SERVICEID,ORDERNO,EVENT,PRE_STATUS)"+
" SELECT 'CUSTOMER_NOTIFY' AS MODULE, TSM.SERVICEID, TSM.ORDERNO, 'BT' AS EVENT, 'INIT' AS PRE_STATUS "+
"FROM IOE.TPOSERVICEMASTER_EXTENDED TSM_E "+
"INNER JOIN IOE.TPOSERVICEMASTER TSM on TSM_E.SERVICEID=TSM.SERVICEID AND TSM_E.BT_CUST_NOTIFY=? ";

/**
* To insert services into ORDER_LIFECYCLE_EVENT table for COPC/BT event regarding ePCD mail SMS alerts
* @return boolean
* @param Connection
* @param String
* @author VIPIN SAHARIA
* @date 25-Mar-2015
*/
public boolean insertIntoOrderLifeCycleEvent(Connection oldConn, String event) throws Exception{
String msg="In WelcomeMailSMSDao's insertIntoOrderLifeCycleEvent method";
PreparedStatement psInsertServices =null;
Connection connection=null;
int insertCount=0;
boolean success=false;
boolean isConnCreated=false;
try{	
	if(null==oldConn){
		isConnCreated=true;
		connection=DbConnection.getConnectionObject();
		connection.setAutoCommit(false);
	}else
		connection=oldConn;
	if(event.equalsIgnoreCase(COPC_EVENT))
		psInsertServices= connection.prepareStatement(sqlinsertIntoOrderLifeCycleEventCOPC);
	else
		psInsertServices= connection.prepareStatement(sqlinsertIntoOrderLifeCycleEventBT);
	//psInsertServices.setInt(1, TSM_EXT_TO_PROCESS);
	psInsertServices.setInt(1, WelcomeMailSMSModel.TSM_EXT_TEMP_ELIGIBLE);
	
	insertCount=psInsertServices.executeUpdate();
	if(insertCount>=0)
		success=true;
	if(success==true && null==oldConn){
		connection.commit();
	}
}catch (Exception e) {
	Utility.LOG(true, true, e, msg);
	connection.rollback();
	throw new Exception();
}finally{
	try{
		DbConnection.closePreparedStatement(psInsertServices);
		if(isConnCreated)
			DbConnection.freeConnection(connection);
	}catch (Exception e){
		Utility.LOG(true, true, e, msg);
	}
}
return success;
}

private String sqlGetDetailsTofindEligibleLineItem="SELECT OLE.SERVICEID,SERVICEPRODUCTID,TSD.SERVICEID as CREATED_IN_SERVICEID FROM ioe.ORDER_LIFECYCLE_EVENT OLE "+
"INNER JOIN ioe.TDISCONNECTION_HISTORY DIS on OLE.SERVICEID=DIS.MAIN_SERVICEID AND OLE.MODULE='CUSTOMER_NOTIFY' and OLE.PRE_STATUS=? AND OLE.EVENT=? "+
"INNER JOIN IOE.TPOSERVICEDETAILS TSD on DIS.SERVICE_PRODUCT_ID=TSD.SERVICEPRODUCTID and TSD.SERVICEDETAILID in ("+CONF_SERVICEDETAILID_BANDWIDTH_LINE+") and TSD.ADDITIONALNODE=0 ";

/*private String sqlGetDetailsTofindEligibleLineItem="SELECT OLE.SERVICEID,SERVICEPRODUCTID,TSD.SERVICEID as CREATED_IN_SERVICEID FROM ioe.ORDER_LIFECYCLE_EVENT OLE "+
"INNER JOIN ioe.TDISCONNECTION_HISTORY DIS on OLE.SERVICEID=DIS.MAIN_SERVICEID AND OLE.MODULE='CUSTOMER_NOTIFY' and OLE.PRE_STATUS=? AND OLE.EVENT=? "+
"INNER JOIN IOE.TPOSERVICEDETAILS TSD on DIS.SERVICE_PRODUCT_ID=TSD.SERVICEPRODUCTID and TSD.SERVICEDETAILID in(222,367,768) and TSD.ADDITIONALNODE=0 ";
*/

/**
* To get details to find eligible LSI for mail/sms from ORDER_LIFECYCLE_EVENT table for COPC/BT event regarding ePCD mail SMS alerts
* @return ArrayList<WelcomeMailSMSDTO>
* @param Connection
* @param String
* @author VIPIN SAHARIA
* @date 25-Mar-2015
*/
public ArrayList<WelcomeMailSMSDTO> getDetailsTofindEligibleLineItem(Connection oldConn,String event) throws Exception{
String msg="In WelcomeMailSMSDao's getDetailsTofindEligibleLineItem method";
PreparedStatement psGetLineDetails =null;
ResultSet rsGetLineDetails =null;
Connection connection=null;
boolean isConnCreated=false;
ArrayList<WelcomeMailSMSDTO> lineDetailsArrList = new ArrayList<WelcomeMailSMSDTO>();
WelcomeMailSMSDTO lineDetailsdto =null;
try{
	if(null==oldConn){
		isConnCreated=true;
		connection=DbConnection.getConnectionObject();
	}else
		connection=oldConn;
	psGetLineDetails= connection.prepareStatement(sqlGetDetailsTofindEligibleLineItem);
	psGetLineDetails.setString(1, PS_INIT_WIP);
	psGetLineDetails.setString(2, event);
	
	rsGetLineDetails=psGetLineDetails.executeQuery();
	while(rsGetLineDetails.next()){
		lineDetailsdto=new WelcomeMailSMSDTO();
		lineDetailsdto.setServiceId(rsGetLineDetails.getLong("SERVICEID"));
		lineDetailsdto.setServiceProductId(rsGetLineDetails.getLong("SERVICEPRODUCTID"));
		lineDetailsdto.setCreatedInServiceId(rsGetLineDetails.getLong("CREATED_IN_SERVICEID"));
		
		lineDetailsArrList.add(lineDetailsdto);
		//System.out.println("VIPIN "+lineDetailsdto.getServiceId()+" "+lineDetailsdto.getServiceProductId()+"   "+lineDetailsdto.getCreatedInServiceId());
	}
}catch (Exception e) {
	Utility.LOG(true, true, e, msg);
	throw new Exception();
}finally{
	try{
		DbConnection.closeResultset(rsGetLineDetails);
		DbConnection.closePreparedStatement(psGetLineDetails);
		if(isConnCreated)
			DbConnection.freeConnection(connection);
	}catch (Exception e){
		Utility.LOG(true, true, e, msg);
	}
}
return lineDetailsArrList;
}

private String sqlUpdateEligibleLineItem="UPDATE IOE.ORDER_LIFECYCLE_EVENT SET MAIL_LINE_ITEM_NO=? where MODULE='CUSTOMER_NOTIFY' and PRE_STATUS=? and SERVICEID=? and EVENT=?";
/**
* To update eligible LSI for mail/sms in ORDER_LIFECYCLE_EVENT table for COPC/BT event regarding ePCD mail SMS alerts
* @return boolean
* @param Connection
* @param String
* @param Map
* @author VIPIN SAHARIA
* @date 25-Mar-2015
*/
public boolean updateEligibleLineItem(Connection oldConn,String event,Map<Long, WelcomeMailSMSDTO> hm) throws Exception{
String msg="In WelcomeMailSMSDao's updateEligibleLineItem method";
PreparedStatement psUpdateEligibleLine =null;
Connection connection=null;
boolean isConnCreated=false;
boolean res=true;
try{	
	if(null==oldConn){
		isConnCreated=true;
		connection=DbConnection.getConnectionObject();
		connection.setAutoCommit(false);
	}else
		connection=oldConn;
	psUpdateEligibleLine= connection.prepareStatement(sqlUpdateEligibleLineItem);
	for(Map.Entry<Long,WelcomeMailSMSDTO> e:hm.entrySet()){
		psUpdateEligibleLine.setLong(1, e.getValue().getServiceProductId());
		psUpdateEligibleLine.setString(2, PS_INIT_WIP);
		psUpdateEligibleLine.setLong(3, e.getKey());
		psUpdateEligibleLine.setString(4, event);
		psUpdateEligibleLine.addBatch();
	}
	int results[]=psUpdateEligibleLine.executeBatch();
	for(int i:results){
		if(i==PreparedStatement.EXECUTE_FAILED){
			res=false;
			throw new Exception(" Exception occurred during executing updateEligibleLineItem Batch::");
		}
	}
	if(res==true && isConnCreated==true)
		connection.commit();
	
}catch (Exception e) {
	Utility.LOG(true, true, e, msg);
	connection.rollback();
	throw new Exception();
}finally{
	try{
		DbConnection.closePreparedStatement(psUpdateEligibleLine);
		if(isConnCreated)
			DbConnection.freeConnection(connection);
	}catch (Exception e){
		Utility.LOG(true, true, e, msg);
	}
}
return res;
}

/**
* To get Key Value correspond to provided template key regarding ePCD mail SMS alerts
* @return String
* @param String
* @author VIPIN SAHARIA
* @date 29-Mar-2015
*/

public static String getTemplateKeyValue(String key) throws Exception{
	
	Connection conn=null;
	ResultSet rs=null;
	
	try
	{
		conn=DbConnection.getConnectionObject();
	
		rs=conn.createStatement().executeQuery("SELECT TEMPLATE_KEY,TEMPLATE_VALUE FROM IOE.TM_CUST_MAIL_SMS_TEMPLATE " +
				"WHERE TEMPLATE_KEY='"+key+"' and IS_APPEND=1");
		if(rs.next())
		{
			return rs.getString("TEMPLATE_VALUE");
		}
	}
	catch (Exception ex) {
		throw Utility.onEx_LOG_RET_NEW_EX(ex,"getTemplateKeyValue","WelcomeMailSMSDao",null,true,true);
	}finally{
		try {
			DbConnection.closeResultset(rs);
			DbConnection.freeConnection(conn);
		} catch (Exception e) {
			throw Utility.onEx_LOG_RET_NEW_EX(e,"getTemplateKeyValue","WelcomeMailSMSDao",null,true,true);
		}
	}
	throw new Exception("Key not Present :"+key);
}

private String sqlGetDetailsCustEPCDAlertCOPC="SELECT "+
        "TSM.LOGICAL_SI_NO,TSD.SERVICEPRODUCTID,CASE WHEN SERVICEDETAILID=768 THEN 'L2 MPLS' WHEN SERVICEDETAILID=222 THEN 'L3 MPLS' "+
        "ELSE 'INTERNET ILP' END AS ORDERTYPE,CON.EMAIL AS CUST_EMAIL,CON.CELLNO AS CUST_PHNO,ROLEDET.PHONE_NO AS ACC_PHNO,ROLEDET.EMAILID AS ACC_EMAIL, "+
        "case when loc.PRIMARYLOCATIONTYPE=1 then "+
            "BCP_ADDMSTR.ADDRESS1 || ' ' || BCP_ADDMSTR.ADDRESS2 || ' ' || BCP_ADDMSTR.ADDRESS3 || ' ' || BCP_ADDMSTR.ADDRESS4 || ' ' || CITY.CITY_NAME "+
            "|| ' ' || STATE.STATE_NAME || ' ' || COUNTRY.COUNTRY_NAME || ' ' || BCP_ADDMSTR.PIN  "+
        "else "+
          "NWK_ADDMSTR.ADDRESS1  "+
        "end AS ADDRESS,TM.ORDERNO,OLE.SERVICEID,OLE.ID "+  
        "FROM IOE.ORDER_LIFECYCLE_EVENT OLE "+
        "INNER JOIN IOE.TPOSERVICEDETAILS TSD on OLE.MAIL_LINE_ITEM_NO=TSD.SERVICEPRODUCTID AND OLE.ORDERNO=? AND OLE.EVENT=? AND OLE.PRE_STATUS=? "+
        "INNER JOIN IOE.TPOSERVICEMASTER TSM ON TSM.SERVICEID= OLE.SERVICEID "+
        "INNER JOIN IOE.TPOMASTER TM ON TSM.ORDERNO=TM.ORDERNO "+
        "INNER JOIN ( SELECT * FROM IOE.TPOCONTACT  WHERE "+
        "ORDERNO=? fetch first 1 row only)AS CON ON CON.ORDERNO=TM.ORDERNO "+
        "INNER JOIN IOE.TM_ACCOUNT ACC ON TM.ACCOUNTID=ACC.ACCOUNTID "+
        "INNER JOIN IOE.TM_ACCOUNTROLEDETAILS ROLEDET ON ROLEDET.ID=ACC.ACCOUNTMGRID "+
        "INNER JOIN IOE.TLOCATION_INFO LOC ON TSD.SERVICEPRODUCTID=LOC.SERVICEPRODUCTID "+
        "LEFT OUTER JOIN IOE.TBCP_ADDRESS_MSTR BCP_ADDMSTR ON (LOC.PRIMARYLOCATIONTYPE=1 AND	LOC.PRIMARYLOCATIONID = BCP_ADDMSTR.BCP_ID) "+ 
		"LEFT OUTER JOIN IOE.TCITY_MASTER CITY 	ON CITY.CITY_ID=BCP_ADDMSTR.CITY_ID "+ 
		"LEFT OUTER JOIN IOE.TSTATE_MASTER STATE  ON STATE.STATE_ID=CITY.STATE_ID "+
        "lEFT OUTER JOIN IOE.TCOUNTRY_MASTER COUNTRY ON BCP_ADDMSTR.COUNTRY_CODE =COUNTRY.COUNTRY_CODE "+
        "LEFT OUTER JOIN IOE.TNETWORK_LOCATION_MSTR NWK_ADDMSTR ON (LOC.PRIMARYLOCATIONTYPE=2 AND LOC.PRIMARYLOCATIONID = NWK_ADDMSTR.LOCATION_CODE) ";

/**
* To get sms & mail details pertain to order no in ORDER_LIFECYCLE_EVENT table for COPC event regarding ePCD mail SMS alerts
* @return ArrayList
* @param Connection
* @param long
* @author VIPIN SAHARIA
 * @date 31-Mar-2015
*/
public ArrayList<WelcomeMailSMSDTO> getDetailsCustEPCDAlertCOPC(Connection oldConn,long orderNo)throws Exception{
	String msg="In WelcomeMailSMSDao's getDetailsCustEPCDAlertCOPC method";
	PreparedStatement psCopcMailDetails =null;
	ResultSet rsCopcMailDetails =null;
	Connection connection=null;
	ArrayList<WelcomeMailSMSDTO> copcMailDetailsArrList = new ArrayList<WelcomeMailSMSDTO>();
	WelcomeMailSMSDTO copcMailDetailsDto =null;
	boolean isConnCreated=false;
	try{
		if(null==oldConn){
			isConnCreated=true;
			connection=DbConnection.getConnectionObject();
		}else
			connection=oldConn;
		psCopcMailDetails= connection.prepareStatement(sqlGetDetailsCustEPCDAlertCOPC);
		psCopcMailDetails.setLong(1, orderNo);
		psCopcMailDetails.setString(2,COPC_EVENT);
		psCopcMailDetails.setString(3, PS_PROCESS_WIP);
		psCopcMailDetails.setLong(4, orderNo);
		rsCopcMailDetails=psCopcMailDetails.executeQuery();
		while(rsCopcMailDetails.next()){
			copcMailDetailsDto=new WelcomeMailSMSDTO();
			copcMailDetailsDto.setCircuitId(rsCopcMailDetails.getLong("LOGICAL_SI_NO"));
			copcMailDetailsDto.setServiceProductId(rsCopcMailDetails.getLong("SERVICEPRODUCTID"));
			copcMailDetailsDto.setOrderType(rsCopcMailDetails.getString("ORDERTYPE"));
			copcMailDetailsDto.setCustEmail(rsCopcMailDetails.getString("CUST_EMAIL"));
			copcMailDetailsDto.setCustPhone(rsCopcMailDetails.getString("CUST_PHNO"));
			copcMailDetailsDto.setAccMngrEmail(rsCopcMailDetails.getString("ACC_EMAIL"));
			//System.out.println("---------------------------------------------------------------------------------------"+rsCopcMailDetails.getString("ACC_EMAIL")+","+amGroupList);
			copcMailDetailsDto.setAccMngrPhone(rsCopcMailDetails.getString("ACC_PHNO"));
			copcMailDetailsDto.setCustAddress(rsCopcMailDetails.getString("ADDRESS"));
			copcMailDetailsDto.setOrderNo(rsCopcMailDetails.getLong("ORDERNO"));
			copcMailDetailsDto.setServiceId(rsCopcMailDetails.getLong("SERVICEID"));
			copcMailDetailsDto.setId(rsCopcMailDetails.getInt("ID"));
			
			copcMailDetailsArrList.add(copcMailDetailsDto);
			
		}
	}catch (Exception e) {
		Utility.LOG(true, true, e, msg);
		throw new Exception();
	}finally{
		try{
			DbConnection.closeResultset(rsCopcMailDetails);
			DbConnection.closePreparedStatement(psCopcMailDetails);
			if(isConnCreated)
				DbConnection.freeConnection(connection);
		}catch (Exception e){
			Utility.LOG(true, true, e, msg);
		}
	}
	return copcMailDetailsArrList;
}

private String sqlGetDetailsCustEPCDAlertBT="SELECT CASE WHEN TSD.SERVICEDETAILID=768 THEN 'L2 MPLS' WHEN TSD.SERVICEDETAILID=222 THEN 'L3 MPLS' ELSE 'INTERNET ILP' END AS ORDERTYPE, "+
				"TSD.SERVICEPRODUCTID,CON.EMAIL AS CUST_EMAIL,CON.CELLNO AS CUST_PHNO,ROLEDET.PHONE_NO AS ACC_PHNO,ROLEDET.EMAILID AS ACC_EMAIL, "+
				"case when loc.PRIMARYLOCATIONTYPE=1 then "+
				    "BCP_ADDMSTR.ADDRESS1 || ' ' || BCP_ADDMSTR.ADDRESS2 || ' ' || BCP_ADDMSTR.ADDRESS3 || ' ' || BCP_ADDMSTR.ADDRESS4 || ' ' || CITY.CITY_NAME "+
				    "|| ' ' || STATE.STATE_NAME || ' ' || COUNTRY.COUNTRY_NAME || ' ' || BCP_ADDMSTR.PIN  "+
				"else "+
				  "NWK_ADDMSTR.ADDRESS1 end AS ADDRESS,TM.ORDERNO,TM.SUB_CHANGE_TYPE_ID,con.SALUATION,con.FIRSTNAME ||' '|| con.LASTNAME as CUST_NAME,ACC.ACCOUNTNAME, "+
				"TSM.LOGICAL_SI_NO as CIRCUITID,TSD.LOCDATE AS COMMDATE,TSM.SERVICEID,ACC.M6SHORT_CODE AS USERID,'#@!aesc' as Password,tsm.M6ORDERNO, "+
				"ATTVALBB.ATTVALUE || ' ' || DDVAL.TEXT AS BANDWIDTH,ATTVALURL.ATTVALUE AS URL,OLE.ID,TSD.SERVICEDETAILID "+
				"FROM IOE.ORDER_LIFECYCLE_EVENT OLE "+
				"INNER JOIN IOE.TPOSERVICEDETAILS TSD on OLE.MAIL_LINE_ITEM_NO=TSD.SERVICEPRODUCTID and OLE.ORDERNO=?  "+
				    "and ole.PRE_STATUS=? and  ole.event=? "+
				"INNER JOIN IOE.TPOSERVICEMASTER TSM ON TSM.SERVICEID= OLE.SERVICEID and TSM.IS_IN_HISTORY=0 "+
				"INNER JOIN IOE.TPOMASTER TM ON TSM.ORDERNO=TM.ORDERNO "+
				"INNER JOIN ( SELECT * FROM IOE.TPOCONTACT  WHERE "+
				"ORDERNO=? fetch first 1 row only)AS CON on CON.ORDERNO=TM.ORDERNO "+
				"INNER JOIN IOE.TM_ACCOUNT ACC ON TM.ACCOUNTID=ACC.ACCOUNTID "+
				"INNER JOIN IOE.TM_ACCOUNTROLEDETAILS ROLEDET ON ROLEDET.ID=ACC.ACCOUNTMGRID "+
				"INNER JOIN IOE.TLOCATION_INFO LOC ON TSD.SERVICEPRODUCTID=LOC.SERVICEPRODUCTID "+
				"LEFT OUTER JOIN IOE.TBCP_ADDRESS_MSTR BCP_ADDMSTR ON (LOC.PRIMARYLOCATIONTYPE=1 AND	LOC.PRIMARYLOCATIONID = BCP_ADDMSTR.BCP_ID)  "+
				"LEFT OUTER JOIN IOE.TCITY_MASTER CITY 	ON CITY.CITY_ID=BCP_ADDMSTR.CITY_ID  "+
				"LEFT OUTER JOIN IOE.TSTATE_MASTER STATE  ON STATE.STATE_ID=CITY.STATE_ID "+
				"lEFT OUTER JOIN IOE.TCOUNTRY_MASTER COUNTRY ON BCP_ADDMSTR.COUNTRY_CODE =COUNTRY.COUNTRY_CODE "+
				"LEFT OUTER JOIN IOE.TNETWORK_LOCATION_MSTR NWK_ADDMSTR ON (LOC.PRIMARYLOCATIONTYPE=2 AND LOC.PRIMARYLOCATIONID = NWK_ADDMSTR.LOCATION_CODE) "+
				"LEFT JOIN IOE.TPRODUCTLINEATTMASTER ATTMAS1 ON ATTMAS1.SERVICEDETAILID=TSD.SERVICEDETAILID and  ATTMAS1.ATTDESCRIPTION ='Billing Bandwidth'  "+
				"LEFT JOIN ioe.TPRODUCTLINEATTVALUE ATTVALBB ON OLE.MAIL_LINE_ITEM_NO=ATTVALBB.SERVICEPRODUCTID and ATTVALBB.ATTMASTERID=ATTMAS1.ATTMASTERID "+
				"LEFT JOIN IOE.TPRODUCTLINEATTMASTER ATTMAS2 ON ATTMAS2.SERVICEDETAILID=TSD.SERVICEDETAILID and ATTMAS2.ATTDESCRIPTION in('Billing Bandwidth UOM') "+
				"LEFT JOIN ioe.TPRODUCTLINEATTVALUE ATTVALBBUOM ON OLE.MAIL_LINE_ITEM_NO=ATTVALBBUOM.SERVICEPRODUCTID and ATTVALBBUOM.ATTMASTERID=ATTMAS2.ATTMASTERID "+
				"LEFT JOIN ioe.TPRODUCTDDVALUES DDVAL ON ATTVALBBUOM.ATTVALUE=DDVAL.VALUE and ATTMAS2.ATTMASTERID=DDVAL.ATTMASTERID "+
				"LEFT JOIN IOE.TPRODUCTLINEATTMASTER ATTMAS3 ON ATTMAS3.SERVICEDETAILID=TSD.SERVICEDETAILID and ATTMAS3.ATTDESCRIPTION in('K6.MRTG URL','K9.Infovista URL') "+
				"LEFT JOIN ioe.TPRODUCTLINEATTVALUE ATTVALURL ON OLE.MAIL_LINE_ITEM_NO=ATTVALURL.SERVICEPRODUCTID and ATTVALURL.ATTMASTERID=ATTMAS3.ATTMASTERID   "+    
				"UNION ALL "+
				"SELECT CASE WHEN TSD.SERVICEDETAILID=768 THEN 'L2 MPLS' WHEN TSD.SERVICEDETAILID=222 THEN 'L3 MPLS' ELSE 'INTERNET ILP' END AS ORDERTYPE, "+
				"TSD.SERVICEPRODUCTID,CON.EMAIL AS CUST_EMAIL,CON.CELLNO AS CUST_PHNO,ROLEDET.PHONE_NO AS ACC_PHNO,ROLEDET.EMAILID AS ACC_EMAIL, "+
				"case when loc.PRIMARYLOCATIONTYPE=1 then "+
				    "BCP_ADDMSTR.ADDRESS1 || ' ' || BCP_ADDMSTR.ADDRESS2 || ' ' || BCP_ADDMSTR.ADDRESS3 || ' ' || BCP_ADDMSTR.ADDRESS4 || ' ' || CITY.CITY_NAME "+
				    "|| ' ' || STATE.STATE_NAME || ' ' || COUNTRY.COUNTRY_NAME || ' ' || BCP_ADDMSTR.PIN  "+
				"else "+
				  "NWK_ADDMSTR.ADDRESS1 end AS ADDRESS,TM.ORDERNO,TM.SUB_CHANGE_TYPE_ID,con.SALUATION,con.FIRSTNAME ||' '|| con.LASTNAME as CUST_NAME,ACC.ACCOUNTNAME, "+
				"TSM.LOGICAL_SI_NO as CIRCUITID,TSD.LOCDATE AS COMMDATE,TSM.SERVICEID,ACC.M6SHORT_CODE AS USERID,'!@#aesc' as Password,tsm.M6ORDERNO, "+
				"case when ATTVALBB_HIS.HISTORYID is null then ATTVALBB.ATTVALUE || ' ' || DDVAL.TEXT else ATTVALBB_HIS.ATTVALUE || ' ' || DDVAL_HIS.TEXT end AS BANDWIDTH, "+
				"case when ATTVALURL_HIS.HISTORYID is null then ATTVALURL.ATTVALUE else ATTVALURL_HIS.ATTVALUE end AS URL,OLE.ID,TSD.SERVICEDETAILID "+
				"FROM IOE.ORDER_LIFECYCLE_EVENT OLE "+
				"INNER JOIN IOE.TPOSERVICEDETAILS TSD on OLE.MAIL_LINE_ITEM_NO=TSD.SERVICEPRODUCTID and OLE.ORDERNO=? and ole.PRE_STATUS=?  and ole.event=? "+
				"INNER JOIN IOE.TPOSERVICEMASTER TSM ON TSM.SERVICEID= OLE.SERVICEID  and TSM.IS_IN_HISTORY=1 "+
				"INNER JOIN IOE.TPOMASTER TM ON TSM.ORDERNO=TM.ORDERNO "+
				"INNER JOIN ( SELECT * FROM IOE.TPOCONTACT  WHERE "+
				"ORDERNO=? fetch first 1 row only)AS CON on CON.ORDERNO=TM.ORDERNO "+
				"INNER JOIN IOE.TM_ACCOUNT ACC ON TM.ACCOUNTID=ACC.ACCOUNTID "+
				"INNER JOIN IOE.TM_ACCOUNTROLEDETAILS ROLEDET ON ROLEDET.ID=ACC.ACCOUNTMGRID "+
				"INNER JOIN IOE.TLOCATION_INFO_HISTORY LOC ON TSD.SERVICEPRODUCTID=LOC.SERVICEPRODUCTID and LOC.MAIN_SERVICE_ID=OLE.SERVICEID "+
				"LEFT OUTER JOIN IOE.TBCP_ADDRESS_MSTR BCP_ADDMSTR ON (LOC.PRIMARYLOCATIONTYPE=1 AND	LOC.PRIMARYLOCATIONID = BCP_ADDMSTR.BCP_ID) "+ 
				"LEFT OUTER JOIN IOE.TCITY_MASTER CITY 	ON CITY.CITY_ID=BCP_ADDMSTR.CITY_ID "+ 
				"LEFT OUTER JOIN IOE.TSTATE_MASTER STATE  ON STATE.STATE_ID=CITY.STATE_ID "+
				"lEFT OUTER JOIN IOE.TCOUNTRY_MASTER COUNTRY ON BCP_ADDMSTR.COUNTRY_CODE =COUNTRY.COUNTRY_CODE "+
				"LEFT OUTER JOIN IOE.TNETWORK_LOCATION_MSTR NWK_ADDMSTR ON (LOC.PRIMARYLOCATIONTYPE=2 AND LOC.PRIMARYLOCATIONID = NWK_ADDMSTR.LOCATION_CODE) "+
				"LEFT JOIN IOE.TPRODUCTLINEATTMASTER ATTMAS1 ON ATTMAS1.SERVICEDETAILID=TSD.SERVICEDETAILID and  ATTMAS1.ATTDESCRIPTION ='Billing Bandwidth' "+ 
				"LEFT JOIN ioe.TPRODUCTLINEATTVALUE ATTVALBB ON OLE.MAIL_LINE_ITEM_NO=ATTVALBB.SERVICEPRODUCTID and ATTVALBB.ATTMASTERID=ATTMAS1.ATTMASTERID "+
				"LEFT JOIN ioe.TPRODUCTLINEATTVALUE_HISTORY ATTVALBB_HIS ON OLE.MAIL_LINE_ITEM_NO=ATTVALBB.SERVICEPRODUCTID  "+
				    "and ATTVALBB_HIS.ATTMASTERID=ATTMAS1.ATTMASTERID and ATTVALBB_HIS.MAIN_SERVICE_ID=OLE.SERVICEID "+
				"LEFT JOIN IOE.TPRODUCTLINEATTMASTER ATTMAS2 ON ATTMAS2.SERVICEDETAILID=TSD.SERVICEDETAILID and ATTMAS2.ATTDESCRIPTION in('Billing Bandwidth UOM') "+
				"LEFT JOIN ioe.TPRODUCTLINEATTVALUE ATTVALBBUOM ON OLE.MAIL_LINE_ITEM_NO=ATTVALBBUOM.SERVICEPRODUCTID and ATTVALBBUOM.ATTMASTERID=ATTMAS2.ATTMASTERID "+
				"LEFT JOIN ioe.TPRODUCTDDVALUES DDVAL ON ATTVALBBUOM.ATTVALUE=DDVAL.VALUE and ATTMAS2.ATTMASTERID=DDVAL.ATTMASTERID "+
				"LEFT JOIN ioe.TPRODUCTLINEATTVALUE_HISTORY ATTVALBBUOM_HIS ON OLE.MAIL_LINE_ITEM_NO=ATTVALBBUOM.SERVICEPRODUCTID "+
				    "and ATTVALBBUOM_HIS.ATTMASTERID=ATTMAS2.ATTMASTERID and ATTVALBBUOM_HIS.MAIN_SERVICE_ID=OLE.SERVICEID "+
				"LEFT JOIN ioe.TPRODUCTDDVALUES DDVAL_HIS ON ATTVALBBUOM_HIS.ATTVALUE=DDVAL_HIS.VALUE and ATTMAS2.ATTMASTERID=DDVAL_HIS.ATTMASTERID "+
				"LEFT JOIN IOE.TPRODUCTLINEATTMASTER ATTMAS3 ON ATTMAS3.SERVICEDETAILID=TSD.SERVICEDETAILID and ATTMAS3.ATTDESCRIPTION in('K6.MRTG URL','K9.Infovista URL') "+
				"LEFT JOIN ioe.TPRODUCTLINEATTVALUE ATTVALURL ON OLE.MAIL_LINE_ITEM_NO=ATTVALURL.SERVICEPRODUCTID and ATTVALURL.ATTMASTERID=ATTMAS3.ATTMASTERID  "+
				"LEFT JOIN ioe.TPRODUCTLINEATTVALUE_HISTORY ATTVALURL_HIS ON OLE.MAIL_LINE_ITEM_NO=ATTVALURL.SERVICEPRODUCTID "+
				    "and ATTVALURL_HIS.ATTMASTERID=ATTMAS3.ATTMASTERID and ATTVALURL_HIS.MAIN_SERVICE_ID=OLE.SERVICEID";
/**
* To get sms & mail details pertain to order no in ORDER_LIFECYCLE_EVENT table for BT event regarding ePCD mail SMS alerts
* @return ArrayList
* @param Connection
* @param long
* @author VIPIN SAHARIA 
* @date 31-Mar-2015
*/
public ArrayList<WelcomeMailSMSDTO> getDetailsCustEPCDAlertBT(Connection oldConn,long orderNo)throws Exception{
	String msg="In WelcomeMailSMSDao's getDetailsCustEPCDAlertBT method";
	PreparedStatement psBtMailDetails =null;
	ResultSet rsBtMailDetails =null;
	Connection connection=null;
	ArrayList<WelcomeMailSMSDTO> btMailDetailsArrList = new ArrayList<WelcomeMailSMSDTO>();
	WelcomeMailSMSDTO btMailDetailsDto =null;
	boolean isConnCreated=false;
	try{
		if(null==oldConn){
			isConnCreated=true;
			connection=DbConnection.getConnectionObject();
		}else
			connection=oldConn;
		psBtMailDetails= connection.prepareStatement(sqlGetDetailsCustEPCDAlertBT);
		psBtMailDetails.setLong(1, orderNo);
		psBtMailDetails.setString(2,PS_PROCESS_WIP);
		psBtMailDetails.setString(3, BT_EVENT);
		psBtMailDetails.setLong(4, orderNo);
		psBtMailDetails.setLong(5, orderNo);
		psBtMailDetails.setString(6,PS_PROCESS_WIP);
		psBtMailDetails.setString(7, BT_EVENT);
		psBtMailDetails.setLong(8, orderNo);
		rsBtMailDetails=psBtMailDetails.executeQuery();
		while(rsBtMailDetails.next()){
			btMailDetailsDto=new WelcomeMailSMSDTO();
			btMailDetailsDto.setOrderType(rsBtMailDetails.getString("ORDERTYPE"));
			btMailDetailsDto.setServiceProductId(rsBtMailDetails.getLong("SERVICEPRODUCTID"));
			btMailDetailsDto.setCustEmail(rsBtMailDetails.getString("CUST_EMAIL"));
			btMailDetailsDto.setCustPhone(rsBtMailDetails.getString("CUST_PHNO"));
			btMailDetailsDto.setAccMngrEmail(rsBtMailDetails.getString("ACC_EMAIL"));
			btMailDetailsDto.setAccMngrPhone(rsBtMailDetails.getString("ACC_PHNO"));
			if(null!=rsBtMailDetails.getString("ADDRESS"))
				btMailDetailsDto.setCustAddress(rsBtMailDetails.getString("ADDRESS"));
			else
				btMailDetailsDto.setCustAddress(" ");
			btMailDetailsDto.setOrderNo(rsBtMailDetails.getLong("ORDERNO"));
			if(null!=rsBtMailDetails.getString("SALUATION"))
				btMailDetailsDto.setSalutation(rsBtMailDetails.getString("SALUATION"));
			else
				btMailDetailsDto.setSalutation(" ");
			if(null!=rsBtMailDetails.getString("CUST_NAME"))
				btMailDetailsDto.setCustName(rsBtMailDetails.getString("CUST_NAME").trim().toUpperCase());
			else
				btMailDetailsDto.setCustName(" ");
			if(null!=rsBtMailDetails.getString("ACCOUNTNAME"))
				btMailDetailsDto.setAccName(rsBtMailDetails.getString("ACCOUNTNAME"));
			else
				btMailDetailsDto.setAccName(" ");
			btMailDetailsDto.setCircuitId(rsBtMailDetails.getLong("CIRCUITID"));
			if(null!=rsBtMailDetails.getString("COMMDATE"))
				btMailDetailsDto.setCommDate(rsBtMailDetails.getString("COMMDATE"));
			else
				btMailDetailsDto.setCommDate(" ");
			if(null!=rsBtMailDetails.getString("SERVICEID"))
				btMailDetailsDto.setServiceId(rsBtMailDetails.getLong("SERVICEID"));
			if(null != rsBtMailDetails.getString("USERID") && rsBtMailDetails.getString("USERID").trim()!=""){
				btMailDetailsDto.setUserid(rsBtMailDetails.getString("USERID").trim().toUpperCase()+"_ilp");
				btMailDetailsDto.setPassword(StringUtils.capitalise(rsBtMailDetails.getString("USERID").trim().toLowerCase()+"_ilp")+ rsBtMailDetails.getString("PASSWORD").trim());
			}else{
				btMailDetailsDto.setUserid(" ");
				btMailDetailsDto.setPassword(" ");
			}
			if(null!=rsBtMailDetails.getString("M6ORDERNO"))
				btMailDetailsDto.setM6OrderNo(rsBtMailDetails.getString("M6ORDERNO"));
			else
				btMailDetailsDto.setM6OrderNo(" ");
			if(null!=rsBtMailDetails.getString("BANDWIDTH"))
				btMailDetailsDto.setBandwidth(rsBtMailDetails.getString("BANDWIDTH"));
			else
				btMailDetailsDto.setBandwidth(" ");
			if(null==rsBtMailDetails.getString("URL"))
				btMailDetailsDto.setUrl(" ");
			else if(null!=rsBtMailDetails.getString("URL")&&(rsBtMailDetails.getString("URL").trim().equals("")||rsBtMailDetails.getString("URL").trim().equalsIgnoreCase("NA")))
				btMailDetailsDto.setUrl(" ");
			else
				btMailDetailsDto.setUrl(rsBtMailDetails.getString("URL"));
			btMailDetailsDto.setId(rsBtMailDetails.getInt("ID"));
			btMailDetailsDto.setServiceDetailId(rsBtMailDetails.getInt("SERVICEDETAILID"));
			btMailDetailsDto.setSubChangeTypeId(rsBtMailDetails.getInt("SUB_CHANGE_TYPE_ID"));
			
			btMailDetailsArrList.add(btMailDetailsDto);
		}
	}catch (Exception e) {
		Utility.LOG(true, true, e, msg);
		throw new Exception();
	}finally{
		try{
			DbConnection.closeResultset(rsBtMailDetails);
			DbConnection.closePreparedStatement(psBtMailDetails);
			if(isConnCreated)
				DbConnection.freeConnection(connection);
		}catch (Exception e){
			Utility.LOG(true, true, e, msg);
		}
	}
	return btMailDetailsArrList;
}

private String sqlUpdateMailstatusAndDetails="UPDATE IOE.ORDER_LIFECYCLE_EVENT SET PRE_STATUS=?,MAIL_STATUS=?,MAIL_LAST_UP_DT=CURRENT TIMESTAMP,TO=?,CC=? WHERE ID=? ";
private String sqlUpdateSMSstatusAndDetails="UPDATE IOE.ORDER_LIFECYCLE_EVENT SET PRE_STATUS=?,SMS_STATUS=?,SMS_LAST_UP_DT=CURRENT TIMESTAMP,CELLNO=? WHERE ID=? ";
/**
* To update final mail/sms send status along with required details in ORDER_LIFECYCLE_EVENT table for COPC/BT event regarding ePCD mail SMS alerts
* @return boolean
* @param Connection
* @param int
* @param int
* @param String
* @param String
* @param String
* @param String
* @param String
* @author VIPIN SAHARIA
* @date 02-Apr-2015
*/
public boolean updateMailSMSstatusAndDetails(Connection oldConn,int flag,int id,String preStatus,String mailSmsStatus,String to, String cc, String cellNo) throws Exception{
String msg="In WelcomeMailSMSDao's updateMailSMSstatusAndDetails method";
PreparedStatement psUpMailSMSDetails =null;
Connection connection=null;
boolean isConnCreated=false;
int resCount=-1;
boolean res=false;
try{	
	if(null==oldConn){
		isConnCreated=true;
		connection=DbConnection.getConnectionObject();
		connection.setAutoCommit(false);
	}else
		connection=oldConn;
	if(flag==MAIL_FLAG){
		//for Mail
		psUpMailSMSDetails= connection.prepareStatement(sqlUpdateMailstatusAndDetails);
		psUpMailSMSDetails.setString(1, preStatus);
		psUpMailSMSDetails.setString(2, mailSmsStatus);
		if(null!=to)
			psUpMailSMSDetails.setString(3, to);
		else
			psUpMailSMSDetails.setString(3, "");
		if(null!=cc)
			psUpMailSMSDetails.setString(4, cc);
		else
			psUpMailSMSDetails.setString(4, "");
		psUpMailSMSDetails.setInt(5, id);
	}
	else if(flag==SMS_FLAG){
		//for SMS
		psUpMailSMSDetails= connection.prepareStatement(sqlUpdateSMSstatusAndDetails);
		psUpMailSMSDetails.setString(1, preStatus);
		psUpMailSMSDetails.setString(2, mailSmsStatus);
		if(null!=cellNo)
			psUpMailSMSDetails.setString(3, cellNo);
		else
			psUpMailSMSDetails.setString(3, "");
		psUpMailSMSDetails.setInt(4, id);
	}
	
	resCount=psUpMailSMSDetails.executeUpdate();
	if(resCount>=0)
		res=true;
	if(res==true && isConnCreated==true)
		connection.commit();
}catch (Exception e) {
	Utility.LOG(true, true, e, msg);
	connection.rollback();
	throw new Exception();
}finally{
	try{
		DbConnection.closePreparedStatement(psUpMailSMSDetails);
		if(isConnCreated)
			DbConnection.freeConnection(connection);
	}catch (Exception e){
		Utility.LOG(true, true, e, msg);
	}
}
return res;
}

private String sqlFetchNextePCDMailSMSAlertOrders="SELECT DISTINCT ORDERNO,EVENT FROM(SELECT DISTINCT ORDERNO,EVENT,DATE(ROW_CREATED_DT) FROM IOE.ORDER_LIFECYCLE_EVENT WHERE MODULE ='CUSTOMER_NOTIFY' AND PRE_STATUS=? ORDER BY DATE(ROW_CREATED_DT),EVENT DESC FETCH FIRST 500 ROWS ONLY)ORDER BY EVENT DESC";
/**
 * To fetch next 500 distinct orders and event from ORDER_LIFECYCLE_EVENT Table to draft ePCD customer notification mails/SMS
  * @return ArrayList<WelcomeMailSMSDTO>
 * @throws Exception
 * @author VIPIN SAHARIA
 * @date 03-Apr-2015
 */
public ArrayList<WelcomeMailSMSDTO> fetchNextePCDMailSMSAlertOrders()throws Exception{
	//This will fetch next 500 distinct orders and event from order lifecycle event table and firmle call draft&send method for each row.
	String msg="In WelcomeMailSMSDao's fetchNextePCDMailSMSAlertOrders method";
	Connection conn=null;
	PreparedStatement psFetchNextOrders= null;
	ResultSet rsFetchNextOrders=null;
	ArrayList<WelcomeMailSMSDTO> ordersArrList= new ArrayList<WelcomeMailSMSDTO>();
	try{	
		conn=DbConnection.getConnectionObject();
		psFetchNextOrders= conn.prepareStatement(sqlFetchNextePCDMailSMSAlertOrders);
		psFetchNextOrders.setString(1, PS_TO_PROCESS);
		WelcomeMailSMSDTO orderListDto = null;
		rsFetchNextOrders=psFetchNextOrders.executeQuery();
		while(rsFetchNextOrders.next()){
			orderListDto=new WelcomeMailSMSDTO();
			orderListDto.setOrderNo(rsFetchNextOrders.getLong("ORDERNO"));
			orderListDto.setEvent(rsFetchNextOrders.getString("EVENT"));
			
			ordersArrList.add(orderListDto);
		}
	}catch(Exception ex){	
		Utility.LOG(true, true, ex, msg);
		conn.rollback();
		throw new Exception();
	}finally{
		try{
			DbConnection.closeResultset(rsFetchNextOrders);
			DbConnection.closePreparedStatement(psFetchNextOrders);
			DbConnection.freeConnection(conn);
		}catch (Exception e){
			Utility.LOG(true, true, e, msg);
		}
	}
	return ordersArrList;
}

private String sqlUpdatePreStatusBeforeDraftingMailSMS="UPDATE IOE.ORDER_LIFECYCLE_EVENT SET PRE_STATUS=?  WHERE MODULE='CUSTOMER_NOTIFY' AND ORDERNO=? AND EVENT=? AND PRE_STATUS=?";

/**
 * To update PRE_STATUS to PROCESS_WIP in ORDER_LIFECYCLE_EVENT TABLE for particular (order,event) before drafting mail/sms for the same
 * @return boolean
 * @param Connection
 * @param long
 * @param String
 * @author VIPIN SAHARIA
 * @date 04-Apr-2015
 */
public boolean updatePreStatusBeforeDraftingMailSMS(Connection oldConn,long orderNo, String event) throws Exception{
		String msg = "In WelcomeMailSMSDao's updatePreStatusBeforeDraftingMailSMS method";
		PreparedStatement psUpPreStatus = null;
		Connection connection = null;
		int upPreStatusCount = 0;
		boolean result = false;
		boolean isConnCreated = false;
		try {
			if (null == oldConn) {
				isConnCreated = true;
				connection = DbConnection.getConnectionObject();
				connection.setAutoCommit(false);
			} else
				connection = oldConn;
			psUpPreStatus = connection.prepareStatement(sqlUpdatePreStatusBeforeDraftingMailSMS);
			psUpPreStatus.setString(1,PS_PROCESS_WIP);
			psUpPreStatus.setLong(2, orderNo);
			psUpPreStatus.setString(3, event);
			psUpPreStatus.setString(4,PS_TO_PROCESS);
			upPreStatusCount = psUpPreStatus.executeUpdate();
			if (upPreStatusCount >= 0)
				result = true;
			if (result == true && null == oldConn) {
				connection.commit();
			}
		} catch (Exception e) {
			Utility.LOG(true, true, e, msg);
			connection.rollback();
			throw new Exception();
		} finally {
			try {
				DbConnection.closePreparedStatement(psUpPreStatus);
				if (isConnCreated)
					DbConnection.freeConnection(connection);
			} catch (Exception e) {
				Utility.LOG(true, true, e, msg);
			}
		}
		return result;
} 

private String sqlUpdateCOPCCustNotifyflag="UPDATE ioe.TPOSERVICEMASTER_EXTENDED SET COPC_CUST_NOTIFY =1 where SERVICEID =?";

/**
 * To update COPC_CUST_NOTIFY flag in TPOSERVICEMASTER_EXTENDED table on first time COPC approval regarding COPC ePCD mail SMS alerts
 * @return boolean
 * @param Connection
 * @param String[]
 * @author VIPIN SAHARIA
 * @date 08-Apr-2015
 */
public boolean updateCOPCCustNotifyflag(Connection oldConn,String[] serviceList) throws Exception{
	String msg="In WelcomeMailSMSDao's updateCOPCCustNotifyflag method";
	PreparedStatement psUpCOPCFlag =null;
	boolean isConnCreated = false;
	Connection connection=null;
	boolean result=true;
	try{
		if (null == oldConn) {
			isConnCreated = true;
			connection = DbConnection.getConnectionObject();
			connection.setAutoCommit(false);
		} else
			connection = oldConn;
		psUpCOPCFlag= connection.prepareStatement(sqlUpdateCOPCCustNotifyflag);
		for(String str : serviceList){
			psUpCOPCFlag.setLong(1, Long.valueOf(str.trim()));
			psUpCOPCFlag.addBatch();
		}
		int resArrUpCOPCFlag[]=psUpCOPCFlag.executeBatch();
		for(int i:resArrUpCOPCFlag){
			if(i==PreparedStatement.EXECUTE_FAILED){
				result=false;
				throw new Exception(" Exception occurred during executing updateEligibleLineItem Batch::");
			}
		}
		if (result == true && null == oldConn) {
			connection.commit();
		}
	}catch (Exception e) {
		Utility.LOG(true, true, e, msg);
		throw new Exception();
	}finally{
		try{
			DbConnection.closePreparedStatement(psUpCOPCFlag);
			if(isConnCreated)
				DbConnection.freeConnection(connection);
		}catch (Exception e){
			Utility.LOG(true, true, e, msg);
		}
	}
	return result;
}


private String sqlGetIsCOPCFirstTimeApproval="SELECT count(HISTORY_ID) AS COUNT FROM ioe.TTASK_ACTION_HISTORY WHERE ORDER_ID=? and TASK_OWNER_ID=3 WITH UR";

/**
 * To get whether order is for first time COPC approval or not regarding COPC ePCD mail SMS alerts
 * @return boolean
 * @param Connection
 * @param long
 * @author VIPIN SAHARIA
 * @date 09-Apr-2015
 */
public int getIsCOPCFirstTimeApproval(Connection oldConn,long orderNo) throws Exception{
	String msg="In WelcomeMailSMSDao's getIsCOPCFirstTimeApproval method";
	PreparedStatement psCOPCApprovalCount =null;
	ResultSet rsCOPCApprovalCount=null;
	boolean isConnCreated = false;
	Connection connection=null;
	int countCOPCApproval=0;
	try{
		if(null == oldConn){
			isConnCreated = true;
			connection = DbConnection.getConnectionObject();
		}else
			connection = oldConn;
		psCOPCApprovalCount= connection.prepareStatement(sqlGetIsCOPCFirstTimeApproval);
		psCOPCApprovalCount.setLong(1, orderNo);
		rsCOPCApprovalCount=psCOPCApprovalCount.executeQuery();
		if(rsCOPCApprovalCount.next()){
			countCOPCApproval=rsCOPCApprovalCount.getInt("COUNT");
		}
	}catch (Exception e) {
		Utility.LOG(true, true, e, msg);
		throw new Exception();
	}finally{
		try{
			DbConnection.closeResultset(rsCOPCApprovalCount);
			DbConnection.closePreparedStatement(psCOPCApprovalCount);
			if(isConnCreated)
				DbConnection.freeConnection(connection);
		}catch (Exception e){
			Utility.LOG(true, true, e, msg);
		}
	}
	return countCOPCApproval;
}

private String sqlGetM6OrderNoLatestService="SELECT TAB_LATEST_SERVICE.LOGICAL_SI_NO,TAB_LATEST_SERVICE.SERVICEID,TAB_LATEST_SERVICE.LATEST_SERVICEID ,SERMASLATEST.M6ORDERNO "+
	"FROM ( "+
	"SELECT SERMAS.LOGICAL_SI_NO,SERMAS.SERVICEID,MAX(SERMASALL.SERVICEID) AS LATEST_SERVICEID FROM  "+
	    "IOE.ORDER_LIFECYCLE_EVENT OLE  "+
	        "INNER JOIN IOE.TPOSERVICEMASTER SERMAS ON SERMAS.SERVICEID=OLE.SERVICEID "+
	       " INNER JOIN IOE.TPOSERVICEMASTER SERMASALL ON SERMASALL.LOGICAL_SI_NO=SERMAS.LOGICAL_SI_NO "+
	            "AND SERMASALL.M6_FX_PROGRESS_STATUS NOT LIKE '%CANCEL%' AND SERMASALL.IS_SERVICE_INACTIVE=0 "+
	            "AND SERMASALL.SERVICEID<=SERMAS.SERVICEID  "+
	"WHERE OLE.ORDERNO=? AND OLE.PRE_STATUS=? AND  OLE.EVENT=? "+
	    "AND SERMASALL.M6ORDERNO IS NOT NULL AND SERMASALL.M6ORDERNO<>0 "+
	"GROUP BY SERMAS.LOGICAL_SI_NO,SERMAS.SERVICEID) TAB_LATEST_SERVICE "+
	    "INNER JOIN IOE.TPOSERVICEMASTER SERMASLATEST ON SERMASLATEST.SERVICEID=TAB_LATEST_SERVICE.LATEST_SERVICEID ";

/**
 * To get M6 order no of latest completed service in M6 for all mail eligible LSI's in an order
 * Required as for Rate Renewal orders M6OrderNo. was not present and hence need to bring M6OrderNo from latest M6 completed LSI. 
 * @return void
 * @param conn
 * @param orderNo
 * @param custDetailList
 * @author VIPIN SAHARIA
 * @date 25-June-2015
 */
public HashMap<Long, String> getM6OrderNoLatestService(Connection conn, long orderNo)throws Exception{
	String msg="In WelcomeMailSMSDao's setM6OrderNoLatestService method";
	PreparedStatement psGetM6OrderNo =null;
	ResultSet rsGetM6OrderNo =null;
	HashMap<Long, String> hmM6OrderNo=new HashMap<Long, String>();
	try{
		psGetM6OrderNo= conn.prepareStatement(sqlGetM6OrderNoLatestService);
		psGetM6OrderNo.setLong(1, orderNo);
		psGetM6OrderNo.setString(2,PS_PROCESS_WIP);
		psGetM6OrderNo.setString(3, BT_EVENT);
		rsGetM6OrderNo=psGetM6OrderNo.executeQuery();
		while(rsGetM6OrderNo.next()){
			hmM6OrderNo.put(rsGetM6OrderNo.getLong("SERVICEID"), rsGetM6OrderNo.getString("M6ORDERNO"));
			/*for(int i=0; i<custDetailList.size();i++){
				if(custDetailList.get(i).getServiceId()==rsGetM6OrderNo.getLong("SERVICEID"))
					custDetailList.get(i).setM6OrderNo(rsGetM6OrderNo.getString("M6ORDERNO"));
			}*/
		}
	}catch (Exception e) {
		Utility.LOG(true, true, e, msg);
		throw e;
	}finally{
		try{
			DbConnection.closeResultset(rsGetM6OrderNo);
			DbConnection.closePreparedStatement(psGetM6OrderNo);
		}catch (Exception e){
			Utility.LOG(true, true, e, msg);
		}
	}
	return hmM6OrderNo;
}

public List<Long> readValidServicesForEPCDAlert(Connection conn, String event, int tsmExtToProcess) throws Exception{
	
	List<Long> serviceIds = new ArrayList<Long>();
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	try{
		if(event.equalsIgnoreCase(COPC_EVENT))
			pstmt= conn.prepareStatement(sqlCOPC_EligibleServices);
		else
			pstmt= conn.prepareStatement(sqlBT_EligibleServices);
		
		pstmt.setInt(1, tsmExtToProcess);
		
		rs=pstmt.executeQuery();
		while(rs.next()){
			serviceIds.add(rs.getLong("SERVICEID"));
//			System.out.println("Services "+rs.getLong("SERVICEID"));
		}
		
	}catch(Exception ex){
		Utility.LOG(ex);
		throw ex;
	}finally{
		DbConnection.closeResultset(rs);
		DbConnection.closePreparedStatement(pstmt);
	}
	
	return serviceIds;
}

public void markEligibleForEPCDAlert(Connection conn,
		List<Long> eligibleServiceIds, String event, int tsmExtTempEligible) throws Exception{
	
	PreparedStatement pstmt = null;
	try{
		if(event.equalsIgnoreCase(COPC_EVENT))
			pstmt= conn.prepareStatement("UPDATE ioe.TPOSERVICEMASTER_EXTENDED SET COPC_CUST_NOTIFY=? WHERE SERVICEID=?");
		else
			pstmt= conn.prepareStatement("UPDATE ioe.TPOSERVICEMASTER_EXTENDED SET BT_CUST_NOTIFY=? WHERE SERVICEID=?");
		
		for (Long serviceId : eligibleServiceIds) {
			//mark these tsmExtTempEligible
			pstmt.setInt(1, tsmExtTempEligible);
			pstmt.setLong(2, serviceId);
//			System.out.println("eligible services"+serviceId);
			pstmt.addBatch();
		}		
		pstmt.executeBatch();
	}catch(Exception ex){
		Utility.LOG(ex);
		throw ex;
	}
	finally{
		DbConnection.closePreparedStatement(pstmt);
	}
}

}
