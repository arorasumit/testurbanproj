package com.ibm.ioes.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.ibm.ioes.beans.ArchivalReportBean;
import com.ibm.ioes.model.ArchivalModel.ARCHIVAL_CATEGORY;
import com.ibm.ioes.model.ArchivalModel.ARCHIVAL_PHASE;
import com.ibm.ioes.model.ArchivalModel.ARCHIVAL_STEP;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Utility;

public class ArchiveIb2bOrdersDao {
	
	class LsiE2EInfo{
		private Integer isE2eDone;
		private Timestamp e2eCompletionDate;
		
		public Integer getIsE2eDone() {
			return isE2eDone;
		}
		
		public void setIsE2eDone(Integer isE2eDone) {
			this.isE2eDone = isE2eDone;
		}
		
		public Timestamp getE2eCompletionDate() {
			return e2eCompletionDate;
		}
		
		public void setE2eCompletionDate(Timestamp e2eCompletionDate) {
			this.e2eCompletionDate = e2eCompletionDate;
		}
		
	}

	/**
	 * Brings archival queries form database and process them as per the process of archival.
	 * @param int
	 * @param String
	 * @return boolean
	 * @throws Exception
	 */
	public static int processArchiveIb2bOrderQueries(Connection connection,String query) throws Exception{
		PreparedStatement psProcessArchivalQuery =null;
		int result=-17;
		try{
			psProcessArchivalQuery= connection.prepareStatement(query);
			result=psProcessArchivalQuery.executeUpdate();
		}finally{
			DbConnection.closePreparedStatement(psProcessArchivalQuery);
		}
		return result;
	} 
	//TO DO VIPIN : to update isValidated/isArchived flagwa
	public static int updateArchiveTracker(Connection connection,String query) throws Exception{
		String msg="In ArchiveIb2bOrdersDao's processArchiveIb2bOrderQueries method";
		PreparedStatement psProcessArchivalQuery =null;
		int result=-17;
		try{
			psProcessArchivalQuery= connection.prepareStatement(query);
			result=psProcessArchivalQuery.executeUpdate();
		}catch (Exception e) {
			Utility.LOG(true, true, e, msg);
			throw new Exception();
		}finally{
			try{
				DbConnection.closePreparedStatement(psProcessArchivalQuery);
			}catch (Exception e){
				Utility.LOG(true, true, e, msg);
			}
		}
		return result;
	} 
	/**
	 * Brings archival queries from database based on category and step.
	 * @param Connection
	 * @param String
	 * @param String
	 * @return TreeMap
	 * @throws Exception
	 * 
	 * (CATEGORY IN('PD','CANCEL','DRAFT','CANCEL_N_DRAFT','ALL')
	 * (STEP IN('VALIDATION','DELETION','ARCHIVAL','ALL')
	 */
	//priya 
	public String computeListOfMqts(String KEYNAME) throws Exception{
		String listofmqt = "";
		Connection conn = null;
		CallableStatement cslistofmqt = null;
		ResultSet rslistofmqt=null;
		try
		{
			conn=DbConnection.getReportsConnectionObject();
			cslistofmqt= conn.prepareCall("SELECT KEYVALUE FROM IOE.TM_APPCONFIG WHERE KEYNAME = 'ARCHIVAL_MQT'");
			rslistofmqt=cslistofmqt.executeQuery();
			while(rslistofmqt.next())
			{
				listofmqt=rslistofmqt.getString("KEYVALUE");
			}
		}
		finally{
			DbConnection.closeResultset(rslistofmqt);
			DbConnection.closeCallableStatement(cslistofmqt);
			DbConnection.freeConnection(conn);
		}
		return listofmqt;
	}
	public int countOfNonRefreshedMqts(String finalmqtlist) throws Exception{
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		calendar.set(Calendar.MILLISECOND,0);
		java.sql.Date currentDate = new java.sql.Date(calendar.getTime().getTime());
		int count = 0;
		Connection conn = null;
		PreparedStatement psRefreshTimeOfMqt = null;
		ResultSet rsRefreshTimeOfMqt = null;
		//String refreshtime = "SELECT REFRESH_TIME FROM syscat.TABLES " +"where TABNAME in (?)";
		try
		{
			conn=DbConnection.getReportsConnectionObject();
			//psRefreshTimeOfMqt = conn.prepareStatement(refreshtime);
		    //psRefreshTimeOfMqt.setString(1, finalmqtlist);
			psRefreshTimeOfMqt = conn.prepareCall("SELECT Date(REFRESH_TIME) as REFRESH_TIME FROM syscat.TABLES where TABNAME IN("+finalmqtlist+")");
			rsRefreshTimeOfMqt = psRefreshTimeOfMqt.executeQuery();
			while(rsRefreshTimeOfMqt.next())
			{
				if(!(rsRefreshTimeOfMqt.getDate("REFRESH_TIME").toString()).equals(currentDate.toString()))
				{
						count++;
				}
			}
		}
		finally{
			DbConnection.closeResultset(rsRefreshTimeOfMqt);
			DbConnection.closePreparedStatement(psRefreshTimeOfMqt);
			DbConnection.freeConnection(conn);
		}
		return count;
	}
	public static TreeMap<Integer, String> getArchivalQueries(Connection oldConn,ARCHIVAL_STEP step,ARCHIVAL_CATEGORY category)throws Exception{
		String msg="In ArchiveIb2bOrdersDao's getArchivalQueries method";
		Connection conn=null;
		PreparedStatement psGetArchivalQueries=null;
		ResultSet rsGetArchivalQueries=null;
		TreeMap<Integer, String> tmArchivalQueries = new TreeMap<Integer, String>();
		StringBuffer sqlGetArchivalQuery=new StringBuffer("SELECT CATEGORY,STEP,QUERY_TEXT,SEQ " +
				"	FROM IOE.ARCHIVAL_QUERIES " +
				"	where CATEGORY=? and STEP=? order by SEQ");
		try{
			conn=oldConn;
			psGetArchivalQueries=conn.prepareStatement(sqlGetArchivalQuery.toString());
			psGetArchivalQueries.setString(1, category.toString());
			psGetArchivalQueries.setString(2, step.toString());
			rsGetArchivalQueries=psGetArchivalQueries.executeQuery();
			
			while(rsGetArchivalQueries.next()){
				tmArchivalQueries.put(rsGetArchivalQueries.getInt("SEQ"), rsGetArchivalQueries.getString("QUERY_TEXT"));
				
				
			}
		}finally{
			try{
				DbConnection.closeResultset(rsGetArchivalQueries);
				DbConnection.closePreparedStatement(psGetArchivalQueries);
			}catch (Exception e){
				Utility.LOG(true, true, e, msg);
			}
		}
		return tmArchivalQueries;
	}
	public static int fetchNextGroupId(Connection conn)throws Exception{
		String msg="In ArchiveIb2bOrdersDao's fetchNextGroupId method";
		PreparedStatement psFetchGroupId =null;
		ResultSet rsFetchGroupId=null;
		int result=-17;
		try{
			psFetchGroupId= conn.prepareStatement("SELECT NEXTVAL FOR IOE.SEQ_ARCHIVAL_GROUP_ID AS GROUP_ID FROM SYSIBM.SYSDUMMY1");
			rsFetchGroupId=psFetchGroupId.executeQuery();
			while(rsFetchGroupId.next())
				result=rsFetchGroupId.getInt("GROUP_ID");
		}finally{
			try{
				DbConnection.closeResultset(rsFetchGroupId);
				DbConnection.closePreparedStatement(psFetchGroupId);
			}catch (Exception e){
				Utility.LOG(true, true, e, msg);
			}
		}
		return result;
	}
	private String sqlUpdateQuery="UPDATE ioe.TPOSERVICEMASTER_EXTENDED SET E2E_COMPLETIION_DATE=?,IS_E2E_DONE=? WHERE SERVICEID=?";
	public void getAndUpdateLsiE2EDisconnInfo(Connection conn,ArrayList<Long> servicesList)throws Exception{
		PreparedStatement psUpdateE2ECompletionInfo=null;
		ResultSet rsUpdateE2ECompletionInfo=null;
		
		PreparedStatement psPendencyCount=null;
		
		PreparedStatement psE2ECompletionDate=null;
		try{
			int pendencyCount=-17;
			Timestamp e2eCompletionDate=null;
			Map<Long,LsiE2EInfo> mapServiceE2ECompletionInfo= new HashMap<Long,LsiE2EInfo>();
			LsiE2EInfo e2eDateAndStatus = null;
			psPendencyCount=conn.prepareStatement(sqlPendencyCountQuery);
			psE2ECompletionDate=conn.prepareStatement(sqlE2ECompletionDateQuery);
			for(Long serviceId : servicesList){
				pendencyCount=this.getPendencyValueForDisconnection(conn,serviceId,psPendencyCount);
				e2eDateAndStatus = new LsiE2EInfo();
				e2eDateAndStatus.setIsE2eDone(4);//Failure=4  putting all services in map with null date and failure status.
				e2eDateAndStatus.setE2eCompletionDate(null); 
				mapServiceE2ECompletionInfo.put(serviceId, e2eDateAndStatus);
				if(pendencyCount==0){
					e2eCompletionDate=getE2ECompletionDate(conn,serviceId,psE2ECompletionDate);
					if(null!=e2eCompletionDate){
						e2eDateAndStatus = new LsiE2EInfo();
						e2eDateAndStatus.setIsE2eDone(1);//success=1 Updating date and status in map only for success ones.
						e2eDateAndStatus.setE2eCompletionDate(e2eCompletionDate);
						mapServiceE2ECompletionInfo.put(serviceId, e2eDateAndStatus);
					}
				}
			}
			//updating Status Batch
			//conn.setAutoCommit(false);
			if(mapServiceE2ECompletionInfo!=null){
				psUpdateE2ECompletionInfo=conn.prepareStatement(sqlUpdateQuery);
				for(Map.Entry<Long, LsiE2EInfo> e : mapServiceE2ECompletionInfo.entrySet()){
					e2eDateAndStatus = null;
					e2eDateAndStatus=e.getValue();
					psUpdateE2ECompletionInfo.setTimestamp(1, e2eDateAndStatus.getE2eCompletionDate());
					psUpdateE2ECompletionInfo.setInt(2, e2eDateAndStatus.getIsE2eDone());
					psUpdateE2ECompletionInfo.setLong(3, e.getKey());
					
					psUpdateE2ECompletionInfo.addBatch();
				}
				int[] res=psUpdateE2ECompletionInfo.executeBatch();
				//System.out.println("Length : "+ res.length);
				//for(int a : res)
					//System.out.println("RES : "+a);
				//conn.commit();
				//conn.setAutoCommit(true);
			}
		}finally{
			try{DbConnection.closeResultset(rsUpdateE2ECompletionInfo);} catch(Exception ex){Utility.LOG(ex);}
			try{DbConnection.closePreparedStatement(psUpdateE2ECompletionInfo);} catch(Exception ex){Utility.LOG(ex);}
			try{DbConnection.closePreparedStatement(psPendencyCount);} catch(Exception ex){Utility.LOG(ex);}
			try{DbConnection.closePreparedStatement(psE2ECompletionDate);} catch(Exception ex){Utility.LOG(ex);}
		}
	}
	private String sqlE2ECompletionDateQuery="WITH  " +
			"TAB as (select serviceid as A from ioe.tposervicemaster where serviceid=?) " +
			"SELECT     " +
				"max((SELECT coalesce(max(LAST_MODIFIED_DATE),date('1/1/1900')) FROM IOE.TFX_SERVICECREATE WHERE CREATEDIN_SERVICEID=tab.A and FX_SCHEDULAR_CREATE_STATUS in(3,5))    " +
				",    " +
				"(SELECT coalesce(max(LAST_MODIFIED_DATE),date('1/1/1900')) FROM IOE.TFX_RC_CREATE WHERE CREATEDIN_SERVICEID=tab.A and FX_SCHEDULAR_CREATE_STATUS in(3,5))    " +
				",    " +
				"(SELECT coalesce(max(LAST_MODIFIED_DATE),date('1/1/1900')) FROM IOE.TFX_NRC_CREATE WHERE CREATEDIN_SERVICEID=tab.A and FX_SCHEDULAR_CREATE_STATUS=3)    " +
				",    " +
				"(SELECT coalesce(max(LAST_MODIFIED_DATE),date('1/1/1900')) FROM IOE.TFX_PACKAGE_CREATE WHERE CREATEDIN_SERVICEID=tab.A and FX_SCHEDULAR_CREATE_STATUS=3)    " +
				",    " +
				"(SELECT coalesce(max(LAST_MODIFIED_DATE),date('1/1/1900')) FROM IOE.TFX_COMPONENT_CREATE WHERE CREATEDIN_SERVICEID=tab.A and FX_SCHEDULAR_CREATE_STATUS in(3,5))    " +
				",    " +
				"(SELECT coalesce(max(LAST_UPDATED_DATE),date('1/1/1900')) FROM IOE.TFX_DISCONNECTION WHERE DISCONNECTEDIN_SERVICEID=tab.A and PROCESSING_STATUS in(3,6))    " +
				",    " +
				"(SELECT coalesce(max(LAST_UPDATED_DATE),date('1/1/1900')) FROM IOE.TFX_SERVICE_DISCONNECTION WHERE DISCONNECTEDIN_SERVICEID=tab.A and PROCESSING_STATUS=3)    " +
				",    " +
				"(SELECT coalesce(max(LAST_UPDATED_DATE),date('1/1/1900')) FROM IOE.TFX_COMPONENT_DISCONNECTION WHERE DISCONNECTEDIN_SERVICEID=tab.A and PROCESSING_STATUS in(3,5))    " +
				",    " +
				"(SELECT coalesce(max(BILLING_TRIGGER_CREATEDATE),date('1/1/1900')) FROM ioe.TPOSERVICEDETAILS serdet inner join ioe.TDISCONNECTION_HISTORY tdh on tdh.SERVICE_PRODUCT_ID=serdet.SERVICEPRODUCTID inner join ioe.tposervicemaster sermas on sermas.serviceid=tdh.MAIN_SERVICEID and sermas.is_in_history=0 and tdh.MAIN_SERVICEID=tab.A)    " +
				",    " +
				"(SELECT coalesce(max(BILLING_TRIGGER_CREATEDATE),date('1/1/1900')) FROM ioe.TPOSERVICEDETAILS_history serdet inner join ioe.TDISCONNECTION_HISTORY tdh on tdh.SERVICE_PRODUCT_ID=serdet.SERVICEPRODUCTID and tdh.MAIN_SERVICEID=serdet.MAIN_SERVICEID inner join ioe.tposervicemaster sermas on sermas.serviceid=tdh.MAIN_SERVICEID and sermas.is_in_history=1 and tdh.MAIN_SERVICEID=tab.A)    " +
				",    " +
				"(SELECT coalesce(max(timestamp(IOE.FORMAT_CALENDERDATE(BILLINGTRIGGERDATE),'00:00:00')),date('1/1/1900')) FROM ioe.TPOSERVICEDETAILS serdet inner join ioe.TDISCONNECTION_HISTORY tdh on tdh.SERVICE_PRODUCT_ID=serdet.SERVICEPRODUCTID inner join ioe.tposervicemaster sermas on sermas.serviceid=tdh.MAIN_SERVICEID and sermas.is_in_history=0  and tdh.MAIN_SERVICEID=tab.A)    " +
				",    " +
				"(SELECT coalesce(max(timestamp(IOE.FORMAT_CALENDERDATE(BILLINGTRIGGERDATE),'00:00:00')),date('1/1/1900')) FROM ioe.TPOSERVICEDETAILS_history serdet inner join ioe.TDISCONNECTION_HISTORY tdh on tdh.SERVICE_PRODUCT_ID=serdet.SERVICEPRODUCTID and tdh.MAIN_SERVICEID=serdet.MAIN_SERVICEID inner join ioe.tposervicemaster sermas on sermas.serviceid=tdh.MAIN_SERVICEID and sermas.is_in_history=1  and tdh.MAIN_SERVICEID=tab.A)    " +
				",    " +
				"(SELECT coalesce(MAX(PUBLISHED_DATE),date('1/1/1900')) FROM IOE.TPOSERVICEMASTER WHERE SERVICEID=TAB.A)    "+
				")    " +
				"AS E2E_COMPLETION_DATE " +
			"FROM tab";

	private Timestamp getE2ECompletionDate(Connection conn, Long serviceId,PreparedStatement psE2ECompletionDate)throws Exception{
		Timestamp e2eCompletionDate=null;
		ResultSet rsE2ECompletionDate=null;
		try{
			psE2ECompletionDate.setLong(1, serviceId);
			rsE2ECompletionDate=psE2ECompletionDate.executeQuery();
			while(rsE2ECompletionDate.next())
				e2eCompletionDate=rsE2ECompletionDate.getTimestamp("E2E_COMPLETION_DATE");
		}finally{
				DbConnection.closeResultset(rsE2ECompletionDate);
		}
		
		return e2eCompletionDate;
	}
	private String sqlPendencyCountQuery="with  " +
			"TAB as (select serviceid as A,(SELECT VARCHAR(KEYVALUE) FROM ioe.TM_APPCONFIG WHERE KEYNAME='SERVICETYPEIDS_SINGLEVIEW') AS CRM_SERVICES from ioe.tposervicemaster where serviceid=?)  " +
			" SELECT     " +
				"((SELECT count(*) FROM IOE.TFX_SERVICECREATE WHERE CREATEDIN_SERVICEID=tab.A and FX_SCHEDULAR_CREATE_STATUS not in(3,5))    " +
				"+    " +
			    "(SELECT count(*) FROM IOE.TFX_RC_CREATE WHERE CREATEDIN_SERVICEID=tab.A and FX_SCHEDULAR_CREATE_STATUS not in(3,5))    " +
			    "+    " +
			    "(SELECT count(*) FROM IOE.TFX_NRC_CREATE WHERE CREATEDIN_SERVICEID=tab.A and FX_SCHEDULAR_CREATE_STATUS<>3)    " +
			    "+    " +
			    "(SELECT count(*) FROM IOE.TFX_PACKAGE_CREATE WHERE CREATEDIN_SERVICEID=tab.A and FX_SCHEDULAR_CREATE_STATUS<>3)    " +
			    "+    " +
			    "(SELECT count(*) FROM IOE.TFX_COMPONENT_CREATE WHERE CREATEDIN_SERVICEID=tab.A and FX_SCHEDULAR_CREATE_STATUS not in(3,5))    " +
			    "+    " +
				"(SELECT count(*) FROM IOE.TFX_DISCONNECTION WHERE DISCONNECTEDIN_SERVICEID=tab.A and PROCESSING_STATUS not in(3,6))    " +
				"+    " +
				"(SELECT count(*) FROM IOE.TFX_SERVICE_DISCONNECTION WHERE DISCONNECTEDIN_SERVICEID=tab.A and PROCESSING_STATUS<>3)    " +
				"+    " +
				"(SELECT count(*) FROM IOE.TFX_COMPONENT_DISCONNECTION WHERE DISCONNECTEDIN_SERVICEID=tab.A and PROCESSING_STATUS not in(3,5))    " +
				"+    " +
				"(SELECT count(*) FROM IOE.tposervicemaster WHERE serviceid=tab.A and CKT_PUSHED_INTO_CRM='N'  " +
				"		and SERVICETYPEID in (SELECT BIGINT(TRIM(CHAR(ELEM))) as vELEM FROM TABLE(IOE.ELEMENTS(tab.CRM_SERVICES)) AS t(elem)))  " +
				")    " +
				"as PENDENCY_COUNT " +
			"FROM tab";
	private int getPendencyValueForDisconnection(Connection conn, Long serviceId,PreparedStatement psPendencyCount) throws Exception{
		int pendencyCount=-17;
		ResultSet rsPendencyValue=null;
		try{
			psPendencyCount.setLong(1, serviceId);
			rsPendencyValue=psPendencyCount.executeQuery();
			while(rsPendencyValue.next())
				pendencyCount=rsPendencyValue.getInt("PENDENCY_COUNT");
		}finally{
				DbConnection.closeResultset(rsPendencyValue);
		}
		
		return pendencyCount;
	}
	private String sqlFetchNextServicesList= "SELECT SERVICEID FROM IOE.TPOSERVICEMASTER_EXTENDED EXT " +
			"WHERE LSI_STATUS_INFO IN('LSI_DISCONNECTED','LIVE') AND IS_E2E_DONE=0 order by SERVICEID FETCH FIRST 20 ROWS ONLY ";
	public ArrayList<Long> fetchNextServicesList(Connection conn) throws Exception{
		//This will fetch next 5000 services from TPOSERVICEMASTER_EXTENDED table having LSI_STATUS_INFO flag as LSI_DISCONNECTED.
		PreparedStatement psFetchNextServicesList= null;
		ResultSet rsFetchNextServicesList=null;
		ArrayList<Long> servicesArrList= new ArrayList<Long>();
		try{	
			psFetchNextServicesList= conn.prepareStatement(sqlFetchNextServicesList);
			rsFetchNextServicesList=psFetchNextServicesList.executeQuery();
			while(rsFetchNextServicesList.next()){
				servicesArrList.add(rsFetchNextServicesList.getLong("SERVICEID"));
			}
		}finally{
				try{DbConnection.closeResultset(rsFetchNextServicesList);} catch(Exception ex){Utility.LOG(ex);}
				try{DbConnection.closePreparedStatement(psFetchNextServicesList);} catch(Exception ex){Utility.LOG(ex);}
		}
		return servicesArrList;
	}
	private String sqlUpdatePreProcessedE2EFlag="UPDATE IOE.TPOSERVICEMASTER_EXTENDED SET IS_E2E_DONE=0 where IS_E2E_DONE=4";
	public void updatePreProcessedE2EFlag(Connection connection) throws Exception{
		PreparedStatement psUpdatePreProcessedFlag = null;
		try{
			psUpdatePreProcessedFlag= connection.prepareStatement(sqlUpdatePreProcessedE2EFlag);
			psUpdatePreProcessedFlag.executeUpdate();
		}finally{
				DbConnection.closePreparedStatement(psUpdatePreProcessedFlag);
		}
	}
	//Satish  Starts FOr Archival
	//private static final String UpdateOfPhase = "UPDATE IOE.ARCHIVAL_MASTER SET ARCHIVAL_PHASE=? WHERE GROUP_ID=?";
	
	public int updatePhase( ARCHIVAL_PHASE phase, int groupId) throws Exception {
		String msg="in insertTimeTracker() method ";
		PreparedStatement pstmt=null;
		Connection conn=null;
		
		int insertCount=-1;
		try {
			conn=DbConnection.getConnectionObject();
			pstmt=conn.prepareStatement("UPDATE IOE.ARCHIVAL_MASTER SET ARCHIVAL_PHASE=? WHERE GROUP_ID=?");
			//set isEligible=1 in archival tracker
			pstmt.setString(1, phase.toString());
			pstmt.setInt(2, groupId);
			
			insertCount=pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			Utility.LOG(true, true, msg );
		}finally{
			try {
				DbConnection.closePreparedStatement(pstmt);
			} catch (Exception e) {
				Utility.LOG(true, true, msg+"in finally block" );
			}
		}
		return insertCount;
	}
	//private static final String IsEligible = "UPDATE ioe.archival_tracker SET IS_ELIGIBLE=? WHERE GROUP_ID=?";
	public int updateIsEligibleStatus( int isEligible, int groupId) throws Exception {
		String msg="in UpdateIsEligibleStatus() method ";
		PreparedStatement pstmt=null;
		Connection conn=null;
		
		int updateCount=-1;
		try {
			conn=DbConnection.getConnectionObject();
			//conn.setAutoCommit(false);
			pstmt=conn.prepareStatement("UPDATE ioe.archival_tracker SET IS_ELIGIBLE=? WHERE GROUP_ID=?");
			pstmt.setInt(1, isEligible);
			pstmt.setInt(2, groupId);
			
			updateCount=pstmt.executeUpdate();
			//conn.commit();
		} catch (SQLException e) {
			conn.rollback();
			e.printStackTrace();
			Utility.LOG(true, true, msg );
		}finally{
				DbConnection.closePreparedStatement(pstmt);
			
		}
		return updateCount;
	}
	//private static final String checkArchivalPhase = "SELECT ARCHIVAL_PHASE FROM ioe.ARCHIVAL_MASTER where group_id=?";
	public ArchivalReportBean checkArchivalPhase(String groupId) throws Exception {  
		Connection connection =null;
		CallableStatement callstmt =null;
		ResultSet rs = null;
		ArchivalReportBean objarchivalUserInfo = null;
		//ArrayList<ArchivalReportBean> objarchivalUserList = new ArrayList<ArchivalReportBean>();
		try {
			connection=DbConnection.getConnectionObject();
			callstmt= connection.prepareCall("SELECT ARCHIVAL_PHASE FROM ioe.ARCHIVAL_MASTER where group_id=?");	
			callstmt.setString(1, groupId);
			rs = callstmt.executeQuery();
			while(rs.next()){
				objarchivalUserInfo = new ArchivalReportBean();
				objarchivalUserInfo.setArchival_phase(rs.getString("ARCHIVAL_PHASE"));
			}
		}catch(Exception ex){
			ex.printStackTrace();
			
		   }finally{				
			DbConnection.closeResultset(rs);
			DbConnection.closePreparedStatement(callstmt);
			DbConnection.freeConnection(connection);		
		}
		return objarchivalUserInfo;
	}
	//private static final String DataForGroupId = "SELECT * FROM ioe.ARCHIVAL_TRACKER where group_id=?";
	public ArrayList<ArchivalReportBean> searchLsiGroupIdServiceList(String groupId) throws Exception {  
		Connection connection =null;
		CallableStatement callstmt =null;
		ResultSet rs = null;
		ArchivalReportBean objarchivalUserInfo = null;
		ArrayList<ArchivalReportBean> objarchivalUserList = new ArrayList<ArchivalReportBean>();
		try {
			connection=DbConnection.getConnectionObject();
			callstmt= connection.prepareCall("SELECT * FROM ioe.ARCHIVAL_TRACKER where group_id=?");	
			callstmt.setString(1, groupId);
			rs = callstmt.executeQuery();
			while(rs.next())
			{
				objarchivalUserInfo = new ArchivalReportBean();
				objarchivalUserInfo.setGroup_id(rs.getString("GROUP_ID")); 
				objarchivalUserInfo.setiseLIGIBLE(rs.getInt("IS_ELIGIBLE"));
			   if((groupId.equals(objarchivalUserInfo.getGroup_id()) && ( objarchivalUserInfo.getiseLIGIBLE()==0))){
					 objarchivalUserInfo.setLogical_si_no(rs.getString("LSI"));
					 objarchivalUserInfo.setOrder_no(rs.getString("ORDERNO"));
					 System.out.println(objarchivalUserInfo.getOrder_no());
					 objarchivalUserInfo.setCATEGORY_OF_ORDER(rs.getString("CATEGORY"));
					 objarchivalUserList.add(objarchivalUserInfo);
					 //System.out.println("GroupIdExist");
				 }
			}
		}catch(Exception ex){
			ex.printStackTrace();
			
		   }finally{				
			DbConnection.closeResultset(rs);
			DbConnection.closePreparedStatement(callstmt);
			DbConnection.freeConnection(connection);
			
			
			
		}
		return objarchivalUserList;
	}
	//private static final String updateTimeTracker = "UPDATE IOE.ARCHIVAL_MASTER SET END_TIME=null,ARCHIVAL_START_DATE=sysdate,ARCHIVAL_PHASE='ARCHIVAL',EXCLUSION_LSI =?  " +
	//		                                        "WHERE GROUP_ID=?";
	public int UpdateTimeTracker( int groupId,String exclusion) throws Exception {
		Connection conn=null;
		String msg="in getTimeTracker() method ";
		PreparedStatement pstmt=null;
		int updateCount=-1;
		java.sql.Clob myCCClob=null;
		try {
			conn=DbConnection.getConnectionObject();
			//conn.setAutoCommit(false);
			myCCClob = com.ibm.db2.jcc.t2zos.DB2LobFactory.createClob(exclusion);
	          
	          pstmt=conn.prepareStatement("UPDATE IOE.ARCHIVAL_MASTER SET END_TIME=null,ARCHIVAL_START_DATE=sysdate,ARCHIVAL_PHASE='ARCHIVAL',EXCLUSION_LSI =?  " +
                      "WHERE GROUP_ID=?");
			 pstmt.setClob(1,myCCClob);
			pstmt.setInt(2, groupId);
			updateCount=pstmt.executeUpdate();
			//conn.commit();
			
		} catch (SQLException e) {
			conn.rollback();
			e.printStackTrace();
			Utility.LOG(true, true, msg );
		}finally{
						try {
				DbConnection.closePreparedStatement(pstmt);
				
			} catch (Exception e) {
				e.printStackTrace();
				Utility.LOG(true, true, msg+"in finally block" );
			}
		}
		return updateCount;	
	}
	
	public int updateValidationFlagforDraft(String validationFlag, ARCHIVAL_CATEGORY category,int groupId) throws Exception {
		String msg="checkValidationFlag ";
		CallableStatement callstmt=null;
		Connection conn=null;
		ResultSet rs = null;
		 int updateCounts=0;
		try {
			conn=DbConnection.getConnectionObject();
				 callstmt = conn.prepareCall("UPDATE ioe.ARCHIVAL_TRACKER SET IS_VALIDATED=? WHERE GROUP_ID=? and CATEGORY=? and ERROR_MSG='' ");
				if(validationFlag==null){
					callstmt.setNull(1, java.sql.Types.INTEGER);
				}else{
					callstmt.setInt(1,Integer.parseInt(validationFlag));
				}
				 
				 callstmt.setInt(2,groupId);
				 callstmt.setString(3, category.toString());
			   conn.setAutoCommit(false);
			 updateCounts= callstmt.executeUpdate();
			 conn.commit();
			
		} catch(Exception e){
			conn.rollback();
			e.printStackTrace();
		   }finally{
	       DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(callstmt);
			
			//DbConnection.freeConnection(conn);		
		}
		return updateCounts;
	}
	public int[] updateValidationFlagforChange(int validationFlag, ARCHIVAL_CATEGORY category,int groupId,List<String> excludingListLSINo,
			ArrayList<ArchivalReportBean> retrievearchivalList) throws Exception {
		PreparedStatement  prestmt=null;
		Connection conn=null;
		int[] updateCounts=null;
		try {
			conn=DbConnection.getConnectionObject();
			/*for(ArchivalReportBean entry :retrievearchivalList){
				if(entry.getCATEGORY_OF_ORDER().trim().equalsIgnoreCase("PD")){	
					String tempListOFLsI=entry.getLogical_si_no();
					tempListOFExclusionLSI.add(Integer.parseInt(tempListOFLsI));
				}else{
					String tempListOFOrder=entry.getOrder_no();
					tempListOFExclusionOrder.add(Integer.parseInt(tempListOFOrder));
				}
			}*/
			//if(tempListOFExclusionLSI!=null && tempListOFExclusionLSI.size()>0){
				if(category  == ARCHIVAL_CATEGORY.PD){
				prestmt = conn.prepareCall("UPDATE ioe.ARCHIVAL_TRACKER SET IS_VALIDATED=? WHERE GROUP_ID=? and CATEGORY=? and LSI=?");
				for (int i = 0; i < excludingListLSINo.size(); i++) {
					prestmt.setInt(1,validationFlag);
					prestmt.setInt(2,groupId);
					prestmt.setString(3, category.toString());
					prestmt.setInt(4, Integer.parseInt(excludingListLSINo.get(i)));
					prestmt.addBatch();
				}
			}
				else{
			//if(tempListOFExclusionOrder!=null &&   tempListOFExclusionOrder.size()>0){
				prestmt = conn.prepareCall("UPDATE ioe.ARCHIVAL_TRACKER SET IS_VALIDATED=? WHERE GROUP_ID=? and CATEGORY=? and ORDERNO=?");
				for (int i = 0; i < excludingListLSINo.size(); i++) {
					prestmt.setInt(1,validationFlag);
					prestmt.setInt(2,groupId);
					prestmt.setString(3, category.toString());
					prestmt.setInt(4, Integer.parseInt(excludingListLSINo.get(i)));
					prestmt.addBatch();
				}
			}
			try{
				conn.setAutoCommit(false);
				prestmt.executeBatch();
				conn.commit();
			}
			catch(SQLException sq)
			{	
				conn.rollback();
				sq.printStackTrace();
				System.out.println(sq.getNextException());
			}
			
		}catch(Exception sqe){
			sqe.printStackTrace();
			conn.rollback();
			Utility.LOG(sqe);
		}finally{	
			DbConnection.freeConnection(conn);
			DbConnection.closePreparedStatement(prestmt);			
		}
		return updateCounts;
	}
	
	public int updateValidationFlag(int validationFlag, ARCHIVAL_CATEGORY category,int groupId,String excludingLSINo) throws Exception {
		String msg="checkValidationFlag ";
		CallableStatement callstmt=null;
		Connection conn=null;
		ResultSet rs = null;
		int updateCount=-1;
		try {
			conn=DbConnection.getConnectionObject();
			conn.setAutoCommit(false);
			 callstmt = conn.prepareCall("UPDATE ioe.ARCHIVAL_TRACKER SET IS_VALIDATED=? WHERE GROUP_ID=? and CATEGORY=? where lSI=?");
			 callstmt.setInt(1,validationFlag);
			 callstmt.setInt(2,groupId);
			 callstmt.setString(3, category.toString());
			 updateCount=callstmt.executeUpdate();
		} catch(Exception e){
			
		   }finally{				
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(callstmt);
			//DbConnection.freeConnection(conn);
		
		}
		return updateCount;
	}
	public int CheckValidationFlag(ARCHIVAL_CATEGORY category,int groupId) throws Exception {
		String msg="checkValidationFlag ";
		CallableStatement callstmt=null;
		Connection conn=null;
		ResultSet rs = null;
		ArchivalReportBean objarchivalUserInfo = null;
		int rowcount=0;
		try {
			conn=DbConnection.getConnectionObject();
			conn.setAutoCommit(false);
			 callstmt = conn.prepareCall("SELECT IS_VALIDATED,ORDERNO,LSI FROM ioe.ARCHIVAL_TRACKER WHERE  GROUP_ID=? and CATEGORY=? ");
			 callstmt.setInt(1,groupId);
			 callstmt.setString(2, category.toString());
				rowcount=callstmt.executeUpdate();
				while(rs.next()){
					objarchivalUserInfo=new ArchivalReportBean();
					objarchivalUserInfo.setIsValidated(rs.getInt("IS_VALIDATED"));
					objarchivalUserInfo.setOrder_no(rs.getString("ORDERNO"));
					objarchivalUserInfo.setLogical_si_no(rs.getString("LSI"));
						
				}
		} catch(Exception e){
			
		   }finally{				
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(callstmt);
			//DbConnection.freeConnection(conn);
		
		}
		return rowcount;
	}
	
}
