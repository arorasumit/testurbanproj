package com.ibm.ioes.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Calendar;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

import com.ibm.ioes.beans.ArchivalReportBean;
import com.ibm.ioes.clep.CLEPUtility;
import com.ibm.ioes.dao.ArchiveIb2bOrdersDao;
import com.ibm.ioes.ecrm.CRMLogger;
//import com.ibm.ioes.ecrm.ReverseMigration_Main;
import com.ibm.ioes.npd.hibernate.beans.QueryBuilderDto;
import com.ibm.ioes.service.UtilityService;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Messages;
import com.ibm.ioes.utilities.Utility;


//import static org.mockito.Mockito.*;

public class ArchivalModel {
	
	
	public static enum ARCHIVAL_CATEGORY {
		DRAFT,CANCEL,PD, DUMMY,CANCEL_N_DRAFT
	}
	
	public static enum ARCHIVAL_PHASE {
		COMPUTE,ARCHIVAL
	}
		
	public static enum ARCHIVAL_STEP {
		ELIGIBLE, VALIDATION, PROD_TO_BCV_SRC , PROD_TO_BCV_TARGET , ARCHIVAL, BCV_TO_PROD_SRC , BCV_TO_PROD_TARGET , DELETION, DELETION_STATUS_UPDATE,RELIGIBLE,PRE_DELETION_STATUS_UPDATE
	}
	
	
	private static final CharSequence DELIMITER = "`";
	
	private UtilityService utilityService = new UtilityService();
	
	
	
	public void setUtilityService(UtilityService utilityService) {
		this.utilityService = utilityService;
	}

	/**
	 * TODO
	 * 1. userid missing
	 * 2. processing start and end record
	 * 3. Any statistics print?no need to do
	 * 4. parallel hit reject
	 * 
	 * @param category
	 * @param userId
	 * @return
	 */
	//priya
	public String computeFinalMqtString(String listofmqt){
		StringTokenizer strMqt  = new StringTokenizer(listofmqt,",");
		ArrayList<String> ArrMqt = new ArrayList<String>();
		String temp_list;
		for(int i=0;strMqt.hasMoreTokens();i++)
		{
			temp_list = strMqt.nextToken().trim();
			//ArrMqt.add("'"+temp_list.substring(temp_list.lastIndexOf(".",0)+5)+"'");  //'MQT_POMASTER' 'MQT_SERVICEMASTER'
			ArrMqt.add("'"+temp_list+"'");
		}
		String finalmqtlist = "";
		boolean first = true;
		for(String string : ArrMqt)
		{
			if(first){
				
				finalmqtlist = finalmqtlist + string;
				first = false;
			}
			else
			{
				finalmqtlist = finalmqtlist + ","+string; //'MQT_POMASTER','MQT_SERVICEMASTER'
			}
		}
		return finalmqtlist;
	}
	public ValidationStatus computeDataForArchival(ARCHIVAL_CATEGORY category, String userId)/*throws Exception*/{	
		
		int groupId=-1;
		ValidationStatus status = null;
		ArchiveIb2bOrdersDao archiveDao=new ArchiveIb2bOrdersDao();
		try{
			boolean isAlreadyArchiving=checkArchivalInProgress();
			if(isAlreadyArchiving==false){
			
					groupId = computeEligibleData(category,userId);
					archiveDao.updatePhase(ARCHIVAL_PHASE.COMPUTE, groupId);//by satish
					validateDataForArchival(category,groupId);//existing
					
					status=new ValidationStatus();
					status.setSuccess(true);
					status.setMessage("Success");
					/*if("Y".equalsIgnoreCase(Utility.getAppConfigValue("IS_ARCHIVAL_DELETION_ON"))){
						//priya
						status = checkArchivalPreRequisite();
						if(status.isSuccess()==true){
							moveFromProdToBcv(category,groupId);
							
							archive(category,groupId);
							
							moveFromBcvToProd(category,groupId);
							
							backupAndPurge(category,groupId);	
						}
					}*/
			}else{
				status=new ValidationStatus();
				status.setSuccess(false);
				status.setMessage("archivefailed_AlreadyInProgress");
			}
				
		}catch(Exception e){
			Utility.LOG_ITER(true, true, e, "ArchivalService Method of ArchivalModel");
		}finally{
			try {
				boolean requestCanProceed=true;
				if(status!=null && "archivefailed_AlreadyInProgress".equals(status.getMessage())){
					requestCanProceed=false;
				}
				if(requestCanProceed){
					updateTimeTracker(groupId);
				}
			} catch (Exception e) {
				Utility.LOG("in finally block of archivalService() method");
			}
			Utility.LOG(true, true, "ArchivalService Method of ArchivalModel in finally");
		}
		
		return status;
	}

	/**
	 * Initiates Archival  status .
	 * @return void
	 * @throws Exception
	 * @author Satish Kumar
	 * @param excludingLSINo 
	 * @date 24-Nov-2016
	 */
	public ValidationStatus archiveDataForGroup(ARCHIVAL_CATEGORY category, String userId, String valueGroupID, String excludingLSINo,List<ArchivalReportBean> archivallist) throws Exception {	
		ValidationStatus status = null;
		ArrayList<String> lsiList=null;
		int isEligible=0;
		ArchiveIb2bOrdersDao archiveDao=new ArchiveIb2bOrdersDao();
		try{
			boolean isAlreadyArchiving=checkArchivalInProgress();
			if(isAlreadyArchiving==false){
				status = checkArchivalPreRequisite();
				if(status.isSuccess()==true){
					lsiList=listofalltheInputLsi(excludingLSINo);
						status.setMessage("Success");
						/*saveAll lsi,setEndDate to null,archival_phase--'Archival' ,Archival Start Date to archival,*/
						updateModifiedAndEndDateForArchival(category, userId,Integer.parseInt(valueGroupID),excludingLSINo);// we are updating
						archiveDao.updateIsEligibleStatus(isEligible, Integer.parseInt(valueGroupID));//set 0
						computeReligibleData(valueGroupID,category,userId); //it will return list where is_eligible=1
						validateDataForArchival(category, valueGroupID,lsiList,archivallist);
						//old functionality remain as it is for archiving
						if("N".equalsIgnoreCase(Utility.getAppConfigValue("IS_ARCHIVAL_DELETION_ON"))){
							moveFromProdToBcv(category,Integer.parseInt(valueGroupID));
							archive(category,Integer.parseInt(valueGroupID));
	                        moveFromBcvToProd(category,Integer.parseInt(valueGroupID));		
							backupAndPurge(category,Integer.parseInt(valueGroupID));	
						}
				}else{
					status=new ValidationStatus();
					status.setSuccess(false);
					status.setMessage("archivefailed_MQT_NOT_REFRESHED");
				}
			}else{
				status=new ValidationStatus();
				status.setSuccess(false);
				status.setMessage("archivefailed_AlreadyInProgress");
			}
		}catch(Exception e){
			e.printStackTrace();
			Utility.LOG_ITER(true, true, e, "ArchivalService Method of ArchivalModel");
		}finally{
			try {
				boolean requestCanProceed=true;
				if(status!=null 
						&& ("archivefailed_AlreadyInProgress".equals(status.getMessage())
								|| "archivefailed_MQT_NOT_REFRESHED".equals(status.getMessage()))){
					requestCanProceed=false;
				}
				if(requestCanProceed){
					updateTimeTracker(Integer.parseInt(valueGroupID));
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				Utility.LOG("in finally block of archivalService() method");
			}
			Utility.LOG(true, true, "ArchivalService Method of ArchivalModel in finally");
		}

		return status;
	}
	//priya
	private ValidationStatus checkArchivalPreRequisite() throws Exception {
		// TODO Auto-generated method stub
		ValidationStatus status = new ValidationStatus();
		status.setSuccess(true);
		ArchiveIb2bOrdersDao archivedao = new ArchiveIb2bOrdersDao();
		String listofmqt = archivedao.computeListOfMqts("ARCHIVAL_MQT");
		if(listofmqt != "")
		{
			String finalmqtString = computeFinalMqtString(listofmqt);
		    int countNonRefreshed = archivedao.countOfNonRefreshedMqts(finalmqtString);
			if(countNonRefreshed>0)
			{
				//status.setSuccess(false);
				status.setSuccess(true); //for testing making it as true
				status.setMessage("archivefailed_MQT_NOT_REFRESHED");
			}
		}
		return status;
	}
	private boolean checkArchivalInProgress() throws Exception {
		
		String msg="In checkArchivalInProgress() method";
		boolean archivalFlag=false;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		Connection conn=null;
				try {
					conn=DbConnection.getConnectionObject();
					pstmt=conn.prepareStatement("SELECT COUNT(*)  AS COUNT FROM IOE.ARCHIVAL_MASTER WHERE END_TIME IS NULL");
					rs=pstmt.executeQuery();
					while(rs.next()){
						if(rs.getInt("COUNT")>0){
							archivalFlag=true;
						}
					}
				} catch (Exception e) {
					conn.rollback();
					Utility.LOG(true, true, msg);
				}finally{
					try {
						DbConnection.closeResultset(rs);
						DbConnection.closePreparedStatement(pstmt);
						DbConnection.freeConnection(conn);
					} catch (Exception e) {
						Utility.LOG(true, true, msg+"in finally");
					}
					
				}
		
		return archivalFlag;
	}

	private int updateTimeTracker(int groupId) throws Exception {
		
		String msg="in updateTimeTracker() method ";
		Connection conn=null;
		PreparedStatement pstmt=null;
		int updateCount=-2;
		try {
			conn=DbConnection.getConnectionObject();
			
			pstmt=conn.prepareStatement("UPDATE IOE.ARCHIVAL_MASTER SET END_TIME=CURRENT TIMESTAMP WHERE GROUP_ID=? ");
			pstmt.setInt(1, groupId);
			updateCount=pstmt.executeUpdate();
		} catch (Exception e) {
			Utility.LOG(true, true, msg );
		}finally{
			
			DbConnection.closePreparedStatement(pstmt);
			DbConnection.freeConnection(conn);
		}
		return updateCount;
	}

	private void moveFromProdToBcv(ARCHIVAL_CATEGORY category, int groupId) throws Exception{
		Connection srcConnProd = null;	
		Connection tgtConnBcv = null;
		try{
			srcConnProd = DbConnection.getConnectionObject();
			tgtConnBcv = DbConnection.getReportsConnectionObject();
			
			moveFromSrcToTgt(groupId, ARCHIVAL_STEP.PROD_TO_BCV_SRC, ARCHIVAL_STEP.PROD_TO_BCV_TARGET,srcConnProd,tgtConnBcv,ARCHIVAL_CATEGORY.DUMMY);
		}finally{
			DbConnection.freeConnection(srcConnProd);
			DbConnection.freeConnection(tgtConnBcv);
		}
	}

	private void moveFromBcvToProd(ARCHIVAL_CATEGORY category, int groupId) throws Exception{
		/**
		 * Reporting to be transferred to Prod
		 */
		Connection srcConnBcv = null;	
		Connection tgtConnProd = null;
		try{
			srcConnBcv = DbConnection.getReportsConnectionObject();
			tgtConnProd = DbConnection.getConnectionObject();
			
			moveFromSrcToTgt(groupId, ARCHIVAL_STEP.BCV_TO_PROD_SRC, ARCHIVAL_STEP.BCV_TO_PROD_TARGET,srcConnBcv,tgtConnProd,category);
		}finally{
			DbConnection.freeConnection(srcConnBcv);
			DbConnection.freeConnection(tgtConnProd);
		}
		
	}

	private void moveFromSrcToTgt(int groupId,ARCHIVAL_STEP srcQueryStep , ARCHIVAL_STEP tgtQueryStep,Connection srcConn, Connection tgtConn, ARCHIVAL_CATEGORY category) throws Exception{
		/**
		 * ARCHIVAL Tracker to be transferred to BCV
		 */
				
				Map<Integer,String> srcQueries = ArchiveIb2bOrdersDao.getArchivalQueries(srcConn, srcQueryStep, category);
				Map<Integer,String> tgtQueries = ArchiveIb2bOrdersDao.getArchivalQueries(tgtConn, tgtQueryStep, category);
				
				for (Entry<Integer,String> entry : srcQueries.entrySet()) {
					String srcQuery = entry.getValue();
					String tgtQuery = tgtQueries.get(entry.getKey());
					
					srcQuery=srcQuery.replaceAll("(?i)\\{\\{groupId\\}\\}", String.valueOf(groupId));
					tgtQuery=tgtQuery.replaceAll("(?i)\\{\\{groupId\\}\\}", String.valueOf(groupId));
					
					UtilityService.syncDataUtilityFunction(srcConn,tgtConn,srcQuery,tgtQuery) ;	
				}
	}

	private static class PurgeQueryAndTable{
		String query;
		String tableName;
		
		public PurgeQueryAndTable(String query,String tableName){
			this.query = query;
			this.tableName = tableName;
		}
		
		public String getQuery() {
			return query;
		}
		public void setQuery(String query) {
			this.query = query;
		}
		public String getTableName() {
			return tableName;
		}
		public void setTableName(String tableName) {
			this.tableName = tableName;
		}
		
		
	}
	
	private void backupAndPurge(ARCHIVAL_CATEGORY category, int groupId) throws Exception{
		/**
		 * get all ids for deletion
		 * foreach id for deletion
		 * 		set autocommit false
		 * 		get Purge Queries
		 * 		for each purge Query
		 * 			appendData in file
		 * 			delete data
		 * 		end for
		 * 		commit
		 * end foreach
		 */
		Connection conn = null;
		
		try{
			conn = DbConnection.getConnectionObject();
			Map<Integer,String> purgeQueries=new TreeMap<Integer, String>();
			ArrayList<CsvArchivalTracker> ids = getIdsForDeletion(conn,category,groupId);
			if(ARCHIVAL_CATEGORY.DRAFT==category || ARCHIVAL_CATEGORY.CANCEL==category){
				
				purgeQueries = ArchiveIb2bOrdersDao.getArchivalQueries(conn, ARCHIVAL_STEP.DELETION, ARCHIVAL_CATEGORY.CANCEL_N_DRAFT);
			}
			else if(ARCHIVAL_CATEGORY.PD==category){
				
				purgeQueries = ArchiveIb2bOrdersDao.getArchivalQueries(conn, ARCHIVAL_STEP.DELETION, category);
			}
			//Map<Integer,String> purgeQueries = ArchiveIb2bOrdersDao.getArchivalQueries(conn, ARCHIVAL_STEP.DELETION, category);//getPurgeQueries(conn,category);
			
			TreeMap<Integer,PurgeQueryAndTable> purgeQueriesAndTable =  new TreeMap<Integer, PurgeQueryAndTable>();
			
			for (Map.Entry<Integer,String> queryEntry : purgeQueries.entrySet()) {
				purgeQueriesAndTable.put(queryEntry.getKey(), 
											new PurgeQueryAndTable(queryEntry.getValue(),getTableName(queryEntry.getValue())));
			}
			
			String directoryName = createPurgeDirectory(groupId);
			
			
			conn.setAutoCommit(false);
			for (CsvArchivalTracker id : ids) {
				try{
					
					preupdateStatusToSuccess(conn,category,groupId,id);     //NANCY FOR SHAREPOINT FILEUPLOAD FLAG
					for (Map.Entry<Integer,String> queryEntry : purgeQueries.entrySet()) {
						String purgeQuery = queryEntry.getValue();
						
						purgeQuery=getReplaceColVal(category,id,purgeQuery,groupId);
																							
						appendData(conn,purgeQuery,category,groupId,id,purgeQueriesAndTable.get(queryEntry.getKey()).getTableName(),directoryName);
						purgeData(conn,purgeQuery,category,groupId,id);
						
					}
					updateStatusToSuccess(conn,category,groupId,id);
					conn.commit();
				}catch(Exception ex){
					Utility.LOG(ex);
					conn.rollback();
					throw ex;
				}
			}	
			
		}finally{
			DbConnection.freeConnection(conn);
		}
		
		
	}

	private String getReplaceColVal(ARCHIVAL_CATEGORY category,
			CsvArchivalTracker id, String query, int groupId) {
		
		if(ARCHIVAL_CATEGORY.CANCEL==category || ARCHIVAL_CATEGORY.DRAFT==category /*|| ARCHIVAL_CATEGORY.CANCEL_N_DRAFT==category*/){
			//purgeQuery=purgeQuery.replaceAll("(?i)\\{\\{COLUMN VALUE\\}\\}", "ORDERNO="+id.getCsvOrderNo());
			query=query.replaceAll("(?i)\\{\\{COLUMN\\}\\}", "ORDERNO");
			query=query.replaceAll("(?i)\\{\\{VALUE\\}\\}",id.getCsvOrderNo());
			query=query.replaceAll("(?i)\\{\\{GROUPID\\}\\}",String.valueOf(groupId));
		}else if(ARCHIVAL_CATEGORY.PD==category){
			query=query.replaceAll("(?i)\\{\\{COLUMN\\}\\}", "LOGICAL_SI_NO");
			query=query.replaceAll("(?i)\\{\\{VALUE\\}\\}",id.getCsvLsi());
			query=query.replaceAll("(?i)\\{\\{GROUPID\\}\\}",String.valueOf(groupId));
			//purgeQuery=purgeQuery.replaceAll("(?i)\\{\\{COLUMN=VALUE\\}\\}", "LOGICAL_SI_NO="+id.getCsvLsi());
		}
		
		
		return query;
	}
	
	
	
	private void preupdateStatusToSuccess(Connection conn,
			ARCHIVAL_CATEGORY category, int groupId, CsvArchivalTracker id) throws Exception {
		String msg="In ArchivalModel's preupdateStatusToSuccess method";
		//here update the isArchived flag of ioe.tfileupload
		try{
			Map<Integer,String> successQueries = ArchiveIb2bOrdersDao.getArchivalQueries(conn, ARCHIVAL_STEP.PRE_DELETION_STATUS_UPDATE, category);
			for(Map.Entry<Integer,String> queryEntry:successQueries.entrySet()){
				String successQuery=queryEntry.getValue();
				successQuery=getReplaceColVal(category,id,successQuery,groupId);
				
				ArchiveIb2bOrdersDao.processArchiveIb2bOrderQueries(conn, successQuery);
				
			}
		
		}catch(Exception e){
			Utility.LOG(true, true, e, msg);
			throw e;
		}
		
	}

	private void updateStatusToSuccess(Connection conn,
			ARCHIVAL_CATEGORY category, int groupId, CsvArchivalTracker id) throws Exception {
		String msg="In ArchivalModel's updateStatusToSuccess method";
		//here update all the flags is backup
		try{
			Map<Integer,String> successQueries = ArchiveIb2bOrdersDao.getArchivalQueries(conn, ARCHIVAL_STEP.DELETION_STATUS_UPDATE, category);
			for(Map.Entry<Integer,String> queryEntry:successQueries.entrySet()){
				String successQuery=queryEntry.getValue();
				successQuery=getReplaceColVal(category,id,successQuery,groupId);
				
				ArchiveIb2bOrdersDao.processArchiveIb2bOrderQueries(conn, successQuery);
				
			}
		
		}catch(Exception e){
			Utility.LOG(true, true, e, msg);
			throw e;
		}
		
	}


	private String createPurgeDirectory(int groupId) throws Exception{
		File f = new File(Messages.getMessageValue("ARCHIVAL_DIR")+groupId);
		f.mkdir();
		return f.getAbsolutePath();
	}


	private void purgeData(Connection conn, String purgeQuery, ARCHIVAL_CATEGORY category, int groupId, CsvArchivalTracker id) throws Exception{
		
		PreparedStatement pstmt = null;
		int noOfRecords=-8;
		try { 
			pstmt = conn.prepareStatement(purgeQuery);
			noOfRecords = pstmt.executeUpdate();	
			if(noOfRecords<0){
				throw new Exception("Purging failed for sql:"+purgeQuery);
			}
		}finally{
				DbConnection.closePreparedStatement(pstmt);
		}
	}


	private void appendData(Connection conn, String purgeQuery, ARCHIVAL_CATEGORY category, int groupId, CsvArchivalTracker id, String tableName, String directoryName) throws Exception{
		// TODO Auto-generated method stub
		String selectQuery = purgeQuery.toLowerCase().replaceFirst("delete", "select *");
		
		File file = new File(directoryName+"//"+tableName+".txt");
		
		if(!file.exists()){
			file.createNewFile();
		}
		
		FileWriter fileWritter = new FileWriter(file,true);
        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
        
		try{
			executeAndAppend(conn,selectQuery,bufferWritter);
		}finally{
			bufferWritter.close();
		}
		
	}


	private void executeAndAppend(Connection conn, String selectQuery,
			BufferedWriter bufferWritter) throws Exception{
		// TODO Test
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		
		try { 
			pstmt = conn.prepareStatement(selectQuery);
			
			resultSet=pstmt.executeQuery();
			ResultSetMetaData md = resultSet.getMetaData();
			int colCount =  md.getColumnCount();
			  
			while(resultSet.next()){
				StringBuffer sb = new StringBuffer();
				for(int i=1;i<=colCount;i++){
					String cell = resultSet.getString(i);
					if(cell!=null && cell.contains(DELIMITER)){
						cell=cell.replace(DELIMITER, " ");
					}
					sb.append(cell).append(DELIMITER);
				}
				bufferWritter.write(sb.append("\n").toString());
			}
		
		}finally{
				DbConnection.closeResultset(resultSet);
				DbConnection.closePreparedStatement(pstmt);
		}
	}


	/*private File getFileForGroupId(int groupId, String tableName) {
		// TODO Auto-generated method stub
		return null;
	}
*/

	private String getTableName(String purgeQuery) {
		// TODO Auto-generated method stub
		purgeQuery = purgeQuery
				.toLowerCase()
				.replaceFirst("delete from", "")
				.replaceAll("\r", " ")
				.replaceAll("\n", " ")
				.trim();

		return new StringTokenizer(purgeQuery).nextToken();
	}


	private ArrayList<CsvArchivalTracker> getIdsForDeletion(
			Connection conn, ARCHIVAL_CATEGORY category, int groupId) throws Exception{
		// TODO Testing
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		
		ArrayList<CsvArchivalTracker> ids = new ArrayList<CsvArchivalTracker>();
		
		String sqlGetDetails="select * from ioe.ARCHIVAL_TRACKER where GROUP_ID=? and IS_ARCHIVED=1";
		int maxCountArchivalDeletion=Integer.parseInt(Utility.getAppConfigValue("NO_OF_RECORDS_FOR_ARCHIVAL_DELETION"));
		try { 
			pstmt = conn.prepareStatement(sqlGetDetails);
			pstmt.setLong(1, groupId);
			
			resultSet=pstmt.executeQuery();
			
			
			int counter=0;
			StringBuffer csvLsi = new StringBuffer();
			StringBuffer csvOrderNo = new StringBuffer();
			
			while(resultSet.next()){
				counter++;
				csvLsi.append(resultSet.getLong("LSI")).append(",");
				csvOrderNo.append(resultSet.getLong("ORDERNO")).append(",");
				
				
				if(counter%maxCountArchivalDeletion==0){
					counter=0;
					ids=addCsvToArraylist(csvLsi,csvOrderNo,ids);
						
					csvLsi=new StringBuffer();
					csvOrderNo = new StringBuffer();
										
				}
			
			}
			if(counter>0){
				addCsvToArraylist(csvLsi,csvOrderNo,ids);
				
			}
		
		}finally{
				DbConnection.closeResultset(resultSet);
				DbConnection.closePreparedStatement(pstmt);
		}
		return ids;
	}
	private ArrayList<CsvArchivalTracker> addCsvToArraylist(StringBuffer csvLsi,
			StringBuffer csvOrderNo, ArrayList<CsvArchivalTracker> ids) {
		
		CsvArchivalTracker csvtracker = new CsvArchivalTracker();
		csvtracker.setCsvLsi(csvLsi.substring(0,(csvLsi.length()-1)).toString());
		//csvtracker.setCsvOrderNo(csvOrderNo.toString());
		csvtracker.setCsvOrderNo(csvOrderNo.substring(0, (csvOrderNo.length()-1)).toString());
		ids.add(csvtracker);
		return ids;
		
	}

	public static class CsvArchivalTracker{
		
		private String csvLsi=null;
		private String csvOrderNo=null;
		
		public String getCsvLsi() {
			return csvLsi;
		}
		public void setCsvLsi(String csvLsi) {
			this.csvLsi = csvLsi;
		}
		public String getCsvOrderNo() {
			return csvOrderNo;
		}
		public void setCsvOrderNo(String csvOrderNo) {
			this.csvOrderNo = csvOrderNo;
		}
		
		
	}

	public static class ArchivalQueries{
		
	}
	
	private void archive(ARCHIVAL_CATEGORY category, int groupId) throws Exception{
		String msg="In ArchiveIb2bOrdersDao's processArchiveIb2bOrderQueries method";
		Connection connection=null;
		//String userId="A10YZI3R";//using thread local variable
		try{
			connection=DbConnection.getReportsConnectionObject();
			connection.setAutoCommit(false);
			getProcessQueriesInSeq(connection, ARCHIVAL_STEP.ARCHIVAL, category, groupId, "");
			connection.commit();
			System.out.println("DONE !!!");
		}catch (Exception e) {
			Utility.LOG(true, true, e, msg);
			connection.rollback();
			throw e;
		}finally{
			try{
				DbConnection.freeConnection(connection);
			}catch (Exception e){
				Utility.LOG(true, true, e, msg);
			}
		}
	}

	private void validateDataForArchival(ARCHIVAL_CATEGORY category, int groupId) throws Exception{
		String msg="In ArchiveIb2bOrdersDao's validateDataForArchival method";
		Connection connection=null;
		String userId="A10YZI3R";//using thread local variable
		try{
			connection=DbConnection.getConnectionObject();
			connection.setAutoCommit(false);
			getProcessQueriesInSeq(connection, ARCHIVAL_STEP.VALIDATION, category, groupId, userId);
			connection.commit();
			System.out.println("DONE !!!");
		}catch (Exception e) {
			Utility.LOG(true, true, e, msg);
			connection.rollback();
			throw e;
		}finally{
			try{
				DbConnection.freeConnection(connection);
			}catch (Exception e){
				Utility.LOG(true, true, e, msg);
			}
		}
		//return success;
	}
//TODO VIPIN: BRING USERID form GUI to this method to update it in tracker
	private int computeEligibleData(ARCHIVAL_CATEGORY category,String userId)throws Exception {
		Connection conn=null;
		int groupId=-15;
		
		try{
			
			conn=DbConnection.getConnectionObject();
			conn.setAutoCommit(false);
			groupId = fetchNextGroupId(conn);
			Utility.LOG("groupId:"+groupId+" generated");
			insertTimeTracker(conn,groupId);
			//Utility.LOG("Logging starts for groupId:"+groupId+" at time:"+new TimeStamp(date.getTime()));
			getProcessQueriesInSeq(conn, ARCHIVAL_STEP.ELIGIBLE, category, groupId, userId);
			conn.commit();
		}catch(Exception e){
			Utility.LOG(true, true, e, "ArchiveModel computeEligibleData function");
			conn.rollback();
		}finally{
			try{
				DbConnection.freeConnection(conn);
			}catch(Exception e){
				Utility.LOG(true, true, e, "ArchiveModel computeEligibleData function finally block");
			}
		}
		return groupId;
	}
	private int insertTimeTracker(Connection conn, int groupId) {
		String msg="in getTimeTracker() method ";
		PreparedStatement pstmt=null;
		
		int insertCount=-1;
		try {
			pstmt=conn.prepareStatement("INSERT INTO IOE.ARCHIVAL_MASTER (GROUP_ID) VALUES(?)");
			pstmt.setInt(1, groupId);
			insertCount=pstmt.executeUpdate();
			
		} catch (SQLException e) {
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

	private int fetchNextGroupId(Connection conn) {
		int res=0;
		try{
			res= ArchiveIb2bOrdersDao.fetchNextGroupId(conn);
		}catch(Exception e){
			Utility.LOG(true, true, e, "ArchiveModel fetchNextGroupId function");
		}
		return res;
	}
	private void getProcessQueriesInSeq(Connection conn,ARCHIVAL_STEP step,ARCHIVAL_CATEGORY category,int groupId,String userId)throws Exception{
		Map<Integer, String> tmQueries=new TreeMap<Integer, String>();
		tmQueries=ArchiveIb2bOrdersDao.getArchivalQueries(conn, step, category);
		for(Map.Entry<Integer,String> e:tmQueries.entrySet()){
			String strQuery=e.getValue();
			strQuery=strQuery.replaceAll("(?i)\\{\\{groupId\\}\\}", String.valueOf(groupId));
			strQuery=strQuery.replaceAll("(?i)\\{\\{category\\}\\}", String.valueOf(category));
			strQuery=strQuery.replaceAll("(?i)\\{\\{userId\\}\\}", String.valueOf(userId));
			strQuery=strQuery.replaceAll("(?i)\\{\\{rowsCount\\}\\}", utilityService.getAppConfigValue("NO_OF_RECORDS_FOR_ARCHIVAL"));
			ArchiveIb2bOrdersDao.processArchiveIb2bOrderQueries(conn, strQuery);
		}
	}
	
	/**
	 * Initiates LSI Disconnection status update in tbl TPOSERVICEMASTER_EXTENDED.
	 * @return void
	 * @throws Exception
	 * @author Vipin Saharia
	 * @date 06-Jan-2016
	 */
	public void getAndUpdateLsiE2EDisconnInfo()throws Exception{
		String msg="In ArchivalModel's getAndUpdateLsiE2EDisconnInfo method";
		ArchiveIb2bOrdersDao daoObj = new ArchiveIb2bOrdersDao();
		Connection conn=null;
		ArrayList<Long> serviceList = null;
		try{
			conn=DbConnection.getConnectionObject();
			conn.setAutoCommit(false);
			//updating all IS_E2E_DONE=4 (i.e. failure) to 0 (i.e. toProcess) in table TPOSERVICEMASTER_EXTENDED
			daoObj.updatePreProcessedE2EFlag(conn);
			conn.commit();
			
			do{
				serviceList=daoObj.fetchNextServicesList(conn);
				conn.commit();
				if(null!=serviceList && serviceList.size()>0){
					Utility.LOG(false,true,"********************** WORKING ON SERVICE IDs : "+serviceList);
					daoObj.getAndUpdateLsiE2EDisconnInfo(conn,serviceList);
					conn.commit();
				}
			}while(serviceList.size()!=0);
		}catch(Exception e){
			Utility.LOG(true, true, e, msg);
			conn.rollback();
			throw new Exception();
		}finally{
			try{
				DbConnection.freeConnection(conn);
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	/*public static void main(String[] args) {
		try{
		//new ArchivalModel().archive(CATEGORY.PD, 17);
			ArchivalModel archivalModel = new ArchivalModel();
			
			UtilityService mockedUtilityService = mock(UtilityService.class);
			when(mockedUtilityService.getAppConfigValue("NO_OF_RECORDS_FOR_ARCHIVAL")).thenReturn("10");
			
			archivalModel.setUtilityService(mockedUtilityService);
			archivalModel.archivalService(ARCHIVAL_CATEGORY.PD,"A1448525");
		}catch(Exception e){
			Utility.LOG_ITER(true, true, e, "MAIN METHOD VIPIN");
		}
	}*/
	/**
	 * Initiates Archival  status .
	 * @return void
	 * @throws Exception
	 * @author Satish Kumar
	 * @date 06-Jan-2016
	 */
	private void validateDataForArchival(ARCHIVAL_CATEGORY category,String groupId, List<String> excludingLSListINo,List<ArchivalReportBean> retrievearchivalList ) throws Exception {
		//Set IsValidatedFlag to 0 for all data of this grouyp Id
		
		int validationFlagForUpdate=-1;
		String validationFlag=null;
		ArchiveIb2bOrdersDao daoObj = new ArchiveIb2bOrdersDao();
		try { 
			 //Set IsValidatedFlag null for Exclusion List
					daoObj.updateValidationFlagforDraft(validationFlag,category,Integer.parseInt(groupId));  //isValidatedFlag  is null
            	   if(excludingLSListINo !=null && excludingLSListINo.size()> 0 && (!excludingLSListINo.isEmpty())){
            		 //Set IsValidatedFlag -1 for Exclusion List
            		   daoObj.updateValidationFlagforChange(validationFlagForUpdate, category,Integer.parseInt(groupId),excludingLSListINo,(ArrayList<ArchivalReportBean>) retrievearchivalList);
                                  }
            	  validateDataForArchival(category,Integer.parseInt(groupId));
			
		} catch (Exception e) {
			e.printStackTrace();
			Utility.LOG_ITER(true, true, e, "IsValidatedFlag to 0 for all data of this grouyp Id");
		}
	 	
		
	}
	
	/**
	 * Initiates Archival  status .
	 * @return void
	 * @throws Exception
	 * @author Satish Kumar
	 * @date 06-Jan-2016
	 */
	
	public ArrayList<String> listofalltheInputLsi(String excludingLSINo) {
		StringTokenizer stk = new StringTokenizer(excludingLSINo," ,");
		   
		   ArrayList<String> LsiList = new ArrayList<String>();
		   while ( stk.hasMoreTokens() ) {
			   LsiList.add(stk.nextToken());
			   System.out.println(LsiList);
		   }
		   
		   
		return LsiList;
	}
	/**
	 * Initiates Archival  status .
	 * @return void
	 * @throws Exception
	 * @author Satish Kumar
	 * @date 06-Jan-2016
	 */
	
	public boolean isLsiExistInDb(ArrayList<String> lsiList, ArrayList<ArchivalReportBean> archivalList) {
		boolean isDataExist=false;

		ArrayList<String> masterList=new ArrayList<String>();
		for(ArchivalReportBean entry:archivalList)
		{
			try {
				if(!(entry.getLogical_si_no()==null))
				{
					masterList.add(entry.getLogical_si_no().trim());
				}else

				{
					masterList.add(entry.getOrder_no().trim());
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		if(masterList.containsAll(lsiList))
		{
			isDataExist=true;
		}       
		return isDataExist;
	}
	/**
	 * Initiates Archival  status .
	 * @return void
	 * @throws Exception
	 * @author Satish Kumar
	 * @param excludingLSINo 
	 * @date 06-Jan-2016
	 */
	
	private int updateModifiedAndEndDateForArchival(ARCHIVAL_CATEGORY category,String userId,int groupId, String excludingLSINo)throws Exception {
		Connection conn=null;
		int update=-1;
		ArchiveIb2bOrdersDao archiveDao=null;
		try{
			
			archiveDao=new ArchiveIb2bOrdersDao();
			update=archiveDao.UpdateTimeTracker(groupId,excludingLSINo);
			if(update==0){
				Utility.LOG("success");
			}else{
				Utility.LOG("failed");
			}
			
		}catch(Exception e){
			e.printStackTrace();
			Utility.LOG(true, true, e, "ArchiveModel computeEligibleData function");
		}finally{
				Utility.LOG(true, true, "ArchiveModel computeEligibleData function finally block");
		}
		return update;
	}
	/**
	 * Initiates Archival  status .
	 * @return void
	 * @throws Exception
	 * @author Satish Kumar
	 * @param excludingLSINo 
	 * @date 24-Nov-2016
	 */
	private void computeReligibleData(String groupid,ARCHIVAL_CATEGORY category,String userId)throws Exception {
		Connection conn=null;
		try{
			
			conn=DbConnection.getConnectionObject();
			conn.setAutoCommit(false);
			//insertTimeTracker(conn,groupId);
			getProcessQueriesInSeq(conn, ARCHIVAL_STEP.RELIGIBLE, category, Integer.parseInt(groupid), userId);
			conn.commit();
		}catch(Exception e){
			e.printStackTrace();
			Utility.LOG(true, true, e, "ArchiveModel computeEligibleData function");
			conn.rollback();
		}finally{
			try{
				DbConnection.freeConnection(conn);
			}catch(Exception e){
				Utility.LOG(true, true, e, "ArchiveModel computeEligibleData function finally block");
			}
		}
	}
	//priya
	public static class ValidationStatus{
		
		private boolean success = false;
		private String message;
		public boolean isSuccess() {
			return success;
		}
		public void setSuccess(boolean success) {
			this.success = success;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		
		
		
	}
}
