//[001] Raveendra Kumar  15-May-2015 20141219-R2-020936-Billing Efficiency Program      Billing trigger done by issue for bulk uploading 

package com.ibm.ioes.schedular;



import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.ibm.ioes.beans.UserInfoDto;
import com.ibm.ioes.bulkupload.dao.TransactionProcessDaoImpl;
import com.ibm.ioes.bulkupload.dto.TransactionUploadDto;
import com.ibm.ioes.ecrm.CRMLogger;
import com.ibm.ioes.ecrm.DBConnectionRetriever;
import com.ibm.ioes.exception.IOESException;
import com.ibm.ioes.forms.DisconnectOrderDto;
import com.ibm.ioes.forms.LineItemDTO;
import com.ibm.ioes.forms.NewOrderDto;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Utility;

public class BulkUpload {

private static final Logger logger;
private static final String status = "SUCCESS";
	
	static {

		logger = Logger.getLogger(TransactionProcessDaoImpl.class);
	}
	
	public static String sqlProcessSuspensionFile = "{call BULKUPLOAD.SPBULK_PROCESSFILE_SUSPENSION(?,?,?,?,?,?)}";
	public static String sqlProcessResumptionFile = "{call BULKUPLOAD.SPBULK_PROCESSFILE_RESUMPTION(?,?,?,?,?,?)}";
	
	public static String sqlFetchFileId = "{call BULKUPLOAD.SP_FETCH_FILE_ID_BULKUPLOAD(?)}";
	public static String sqlValidatePermanentDisconnectionFileid="{call BULKUPLOAD.SP_VALIDATE_PERMANENT_DISCONNECTION(?,?,?,?,?)}";
	public static String sqlValidateSuspensionFileid="{call BULKUPLOAD.SP_PROCESS_VALIDATION_SUSPENSION(?,?,?,?,?)}";
	public static String sqlValidateResumptionFileid="{call BULKUPLOAD.SP_PROCESS_VALIDATION_RESUMPTION(?,?,?,?,?)}";
	public static String sqlValidateRaterenewalFileid="{call BULKUPLOAD.SP_PROCESS_VALIDATE_RATERENEWAL(?,?,?,?,?)}";
	
	public static String sqlGetLSIno="{call BULKUPLOAD.SP_FETCH_PDLSI_BULKUPLOAD(?)}";
	public static String sqlGetSuspensionLSIno="{call BULKUPLOAD.SP_FETCH_SUSPENSIONLSI_BULKUPLOAD(?)}";
	public static String sqlGetResumptionLSIno="{call BULKUPLOAD.SP_FETCH_RESUMPTIONLSI_BULKUPLOAD(?)}";
	public static String sqlGetRaterenewalLSIno="{call BULKUPLOAD.SP_FETCH_RATERENEWALLSI_BULKUPLOAD(?)}";
	
	public static String sqlProcessFile = "{call BULKUPLOAD.SP_CREATE_PERMANENT_DISCONNECTION_ORDER_BULKUPLOAD(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	public static String sqlProcessFileSuspension = "{call BULKUPLOAD.SP_CREATE_SUSPENSION_ORDER_BULKUPLOAD(?,?,?,?,?,?,?,?,?,?)}";
	public static String sqlProcessFileResumption="{call BULKUPLOAD.SP_CREATE_RESUMPTION_ORDER_BULKUPLOAD(?,?,?,?,?,?,?,?,?,?)}";
	public static String sqlProcessFileRaterenewal="{call BULKUPLOAD.SP_CREATE_RATERENEWAL_ORDER_BULKUPLOAD(?,?,?,?,?,?,?)}";
	public static String sqlChangeFileIdStatus="{call BULKUPLOAD.SP_CHANGE_RRFILE_STATUS(?)}";
	
	/*
	 * Charge Redirection Bulkupload 
	 */
	private static String spProcessingValidationForChargeRedirection = "{call BULKUPLOAD.SPBULK_PROCESS_CHARGE_REDIRECTION_VALIDATION(?,?,?,?,?,?)}";
	private static String spProcessingForChargeRedirection = "{call BULKUPLOAD.SPBULK_PROCESSFILE_CHARGE_REDIRECTION(?,?,?,?,?,?)}";
	private static String strGetFileIDNTemplateIdforChargeRedirection ="SELECT FILEID, TEMPLATEID FROM BULKUPLOAD.TM_TTEMPLATEFILE_PROCESS WHERE FILESTATUSID=2 and TEMPLATEID=83";
	
	public void processFileBillingTrigger() throws Exception
		{
			logger.info(" Entered into processFiles method of " + this.getClass().getSimpleName());
			
			Connection conn = null;
			Statement stmt=null;
			CallableStatement csProcessFiles = null;
			ResultSet rs=null;
			TransactionProcessDaoImpl daoimp =new TransactionProcessDaoImpl();
			BulkuploadDto dto=null;
			ArrayList<BulkuploadDto> lstfFileid = new ArrayList<BulkuploadDto>();
			BulkuploadDto newObj=null; 

			try
			{
				conn=DbConnection.getConnectionObject();
				csProcessFiles = conn.prepareCall(sqlFetchFileId);
				csProcessFiles.setInt(1,4);
				rs=csProcessFiles.executeQuery();
				while(rs.next())
				{
					dto = new BulkuploadDto();
					dto.setFileID(rs.getString("FILEID"));
					//[001] Start
					dto.setBtDoneBy(rs.getLong("EMP_ID"));
					//[001] End
					lstfFileid.add(dto);
				}
				for(int i=0; i<lstfFileid.size();i++)
				{
					new NewOrderDto();
					newObj =lstfFileid.get(i);
					//[001] Start now added the emp id parameter
					daoimp.processFiles_forBTBulkUpload(Integer.parseInt(newObj.getFileID()),4,Long.toString(newObj.getBtDoneBy()));		
				}
			}
				
			catch(SQLException sqlEx)
			{
				sqlEx.printStackTrace();
				try {
					conn.rollback();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				logger.error(sqlEx.getMessage() + " SQLException occured in processFiles method of " + this.getClass().getSimpleName());
				throw new IOESException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
			}
			catch(Exception ex)
			{
				try {
					conn.rollback();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ex.printStackTrace();
				logger.error(ex.getMessage() + " Exception occured in processFiles method of " + this.getClass().getSimpleName());
				throw new IOESException("Exception : "+ ex.getMessage(), ex);
			}
			finally
			{
				try
				{
					DbConnection.closeCallableStatement(csProcessFiles);
					DbConnection.freeConnection(conn);
				}
				catch(SQLException sqlEx)
				{
					logger.error(sqlEx.getMessage() + " SQLException occured while closing database objects in processFiles method of " + this.getClass().getSimpleName());
					throw new IOESException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
				}
				catch(Exception ex)
				{
					logger.error(ex.getMessage() + " Exception occured while closing database objects in processFiles method of " + this.getClass().getSimpleName());
					throw new IOESException("Exception : "+ ex.getMessage(), ex);
				}
			}
		}
	

		public void processFilePermanentDisconnection() throws Exception
		{
			logger.info(" Entered into processFilesFor PermanentDisconnection method of " + this.getClass().getSimpleName());
			Connection conn = null;
			PreparedStatement psmt=null;
			ResultSet rset=null;
			CallableStatement csProcessFiles = null;
			Statement stmt= null;
			try
			{
				conn=DbConnection.getConnectionObject();
				psmt = conn.prepareCall(sqlFetchFileId);
				psmt.setLong(1,1);
				rset=psmt.executeQuery();
				ArrayList <BulkuploadDto> objDto = new ArrayList<BulkuploadDto>();
				BulkuploadDto dto = null; 
				while (rset.next()) 
				{
					dto = new BulkuploadDto();
					dto.setFileID(rset.getString("FILEID"));
					objDto.add(dto);
				}
				rset.close();
				if(objDto.size()>0 )
				{
					for(Iterator iter1 = objDto.iterator(); iter1.hasNext();)
					{
						try
						{
							System.out.println("VALIDATION START");
							csProcessFiles = conn.prepareCall(sqlValidatePermanentDisconnectionFileid);
							BulkuploadDto element = (BulkuploadDto) iter1.next();
							csProcessFiles.setLong(1, new Long(element.getFileID()));
							csProcessFiles.setInt(2,0);
							csProcessFiles.setString(3,"");
							csProcessFiles.setString(4,"");
							csProcessFiles.setString(5,"");
							csProcessFiles.execute();
							if(csProcessFiles.getString(3)==null)
							{
								System.out.println("VALIDATION SUCCESSFULLY DONE:::::");
								ResultSet rsetlsi=null;
								PreparedStatement psmt1=null;
								psmt1 = conn.prepareCall(sqlGetLSIno);
								psmt1.setLong(1,new Long(element.getFileID()));
								rsetlsi=psmt1.executeQuery();
								ArrayList <BulkuploadDto> objDtolsi = new ArrayList<BulkuploadDto>();
								while (rsetlsi.next()) 
								{
									dto = new BulkuploadDto();
									dto.setLsiNo(rsetlsi.getString("LSINO"));
									dto.setSr_No(rsetlsi.getString("SR_NO"));
									dto.setDate_Disconnection(rsetlsi.getString("DATE_OF_DISCONNECTION"));
									dto.setDate_Disconnection_Cust(rsetlsi.getString("DISCONNECTION_DATE_BY_CUST"));
									dto.setDate_Disconnection_Np(rsetlsi.getString("DISCONNECTION_DATE_NP"));
									dto.setDate_Intimated(rsetlsi.getString("INTIMATED_DATE"));
									
									objDtolsi.add(dto);
								}
								rsetlsi.close();
								if(objDtolsi.size()>0)
								{
									for (Iterator iter = objDtolsi.iterator(); iter.hasNext();) 
									{
										System.out.println("Creating Orders:::::");
										conn.setAutoCommit(false);	
										csProcessFiles = conn.prepareCall(sqlProcessFile);
										BulkuploadDto element1 = (BulkuploadDto) iter.next();
										
										csProcessFiles.setLong(1, new Long(element1.getLsiNo()));
										
										csProcessFiles.setString(2," ");
										csProcessFiles.setString(3, element1.getSr_No());
										csProcessFiles.setLong(4, new Long(element.getFileID()));
										csProcessFiles.setString(5, element1.getDate_Disconnection());
										csProcessFiles.setString(6, element1.getDate_Disconnection_Cust());
										csProcessFiles.setString(7, element1.getDate_Disconnection_Np());
										csProcessFiles.setString(8, element1.getDate_Intimated());
										csProcessFiles.setLong(9,0);
										csProcessFiles.setLong(10,0);
										csProcessFiles.setString(11,"");
										csProcessFiles.setString(12,"");
										csProcessFiles.setString(13,"");
										csProcessFiles.execute();
										String orderStatus=csProcessFiles.getString(12);
										System.out.println("Error:::::::::::::::::::::::::"+ csProcessFiles.getString(12));
										System.out.println("Step:::::::::::::::::::::::::::"+ csProcessFiles.getString(13));			
										
										if(orderStatus.equalsIgnoreCase("SUCCESS"))
										{
											conn.commit();
										logger.info(" Processing of PermanentDisconnection method  " + this.getClass().getSimpleName());
										}
										else
										{
											conn.rollback();				
										logger.info(" Processing of PermanentDisconnection method of " + this.getClass().getSimpleName());
										}
									}
								}
								else
								{
										try{
										
										//Statement stmt=null;
										stmt = conn.createStatement();
										String sql="UPDATE BULKUPLOAD.TM_TTEMPLATEFILE_PROCESS SET FILESTATUSID = 4 WHERE FILEID ="+element.getFileID();
										//psmt = conn.prepareStatement(sql);
										stmt.executeUpdate(sql);
										//stmt.close();
										
											}catch(Exception e){logger.info("SQL Exception  " + this.getClass().getSimpleName());}
									
								}
								
							}
						}
						catch(Exception e)
						{
							logger.info(" End of processFilesFor PermanentDisconnection method of " + this.getClass().getSimpleName());
						}
						/*finally
						{
							try
							{
								DbConnection.closeCallableStatement(csProcessFiles);
								DbConnection.freeConnection(conn);
							}
							catch(SQLException sqlEx)
							{
								logger.error(sqlEx.getMessage() + " SQLException occured while closing database objects in processFiles method of " + this.getClass().getSimpleName());
								throw new IOESException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
							}
							catch(Exception ex)
							{
								logger.error(ex.getMessage() + " Exception occured while closing database objects in processFiles method of " + this.getClass().getSimpleName());
								throw new IOESException("Exception : "+ ex.getMessage(), ex);
							}
						}*/
					}
					logger.info(" End of processFilesFor PermanentDisconnection method of " + this.getClass().getSimpleName());
				}
			}
			catch(SQLException sqlEx)
			{
				sqlEx.printStackTrace();
				try {
					conn.rollback();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				logger.error(sqlEx.getMessage() + " SQLException occured in processFiles method of " + this.getClass().getSimpleName());
				throw new IOESException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
			}
			catch(Exception ex)
			{
				try {
					conn.rollback();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ex.printStackTrace();
				logger.error(ex.getMessage() + " Exception occured in processFiles method of " + this.getClass().getSimpleName());
				throw new IOESException("Exception : "+ ex.getMessage(), ex);
			}
			finally
			{
				try
				{
					
					DbConnection.closeCallableStatement(csProcessFiles);
					DbConnection.closePreparedStatement(psmt);
					
					DbConnection.freeConnection(conn);
				}
				catch(SQLException sqlEx)
				{
					logger.error(sqlEx.getMessage() + " SQLException occured while closing database objects in processFiles method of " + this.getClass().getSimpleName());
					throw new IOESException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
				}
				catch(Exception ex)
				{
					logger.error(ex.getMessage() + " Exception occured while closing database objects in processFiles method of " + this.getClass().getSimpleName());
					throw new IOESException("Exception : "+ ex.getMessage(), ex);
				}
			}
			//System.out.println("ENDED PROCESSFILE FOR PD ORDERS."+date.get(Calendar.MINUTE));
		}
		

		/*   	********************************************************************************
		 *		Method Name:- 	processFilesSuspension()
		 *		Created By:-    Nagarjuna
		 * 		Created On:-    02-12-2013
		 * 		Purpose:-		Scheduler to Create BulkSuspension orders.
		 *      ********************************************************************************
		 */ 	
		/*public void processFilesSuspension() throws Exception
		{
			logger.info(" Entered into processFilesSuspension method of " + this.getClass().getSimpleName());
			
			Connection conn = null;
			PreparedStatement psmt=null;
			ResultSet rset=null;
			CallableStatement csProcessFiles = null;
			try
			{
				conn=DbConnection.getConnectionObject();
				
				 * Calling Procedure to get all the Fileids to Create BulkSuspension orders.
				 * Here parameter (3) means TemplateID of Suspension from db table.
				 * Then after it will iterate all fileids to process and create Suspension Orders.
				 * 
				 
				psmt = conn.prepareCall(sqlFetchFileId);
				psmt.setLong(1, 3);
				rset = psmt.executeQuery();
				ArrayList <BulkuploadDto> objDto = new ArrayList<BulkuploadDto>();
				BulkuploadDto dto = null; 
				while (rset.next()) 
				{
					dto = new BulkuploadDto();
					dto.setFileID(rset.getString("FILEID"));
					objDto.add(dto);
				}
				
				if(objDto.size()>0 )
				{
					
					for (Iterator iter = objDto.iterator(); iter.hasNext();) 
					{
						conn.setAutoCommit(false);	
						csProcessFiles=conn.prepareCall(sqlProcessSuspensionFile);
						BulkuploadDto element = (BulkuploadDto) iter.next();
						csProcessFiles.setLong(1, new Long(element.getFileID()));
						csProcessFiles.setLong(2,1);
						csProcessFiles.setLong(3,0);
						csProcessFiles.setLong(4,0);//RESULT
						csProcessFiles.setString(5,"");
						csProcessFiles.setString(6,"");
						csProcessFiles.execute();
						System.out.println("Error:::::::::::::::::::::::::"+ csProcessFiles.getString(5));
						System.out.println("Step:::::::::::::::::::::::::::"+ csProcessFiles.getString(6));			
						if(csProcessFiles.getString(5)==null)
						{
							conn.commit();
					logger.info(" END of processFilesSuspension method of " + this.getClass().getSimpleName());
						}
						else
						{
							conn.rollback();				
					logger.info(" END of processFilesSuspension method of " + this.getClass().getSimpleName());
						}
					}
				}
			}
			catch(SQLException sqlEx)
			{
				sqlEx.printStackTrace();
				try {
					conn.rollback();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				logger.error(sqlEx.getMessage() + " SQLException occured in processFilesSuspension method of " + this.getClass().getSimpleName());
				throw new IOESException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
			}
			catch(Exception ex)
			{
				try {
					conn.rollback();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ex.printStackTrace();
				logger.error(ex.getMessage() + " Exception occured in processFilesSuspension method of " + this.getClass().getSimpleName());
				throw new IOESException("Exception : "+ ex.getMessage(), ex);
			}
			finally
			{
				try
				{
					
					DbConnection.closeCallableStatement(csProcessFiles);
					DbConnection.freeConnection(conn);
				}
				catch(SQLException sqlEx)
				{
					logger.error(sqlEx.getMessage() + " SQLException occured while closing database objects in processFilesSuspension method of " + this.getClass().getSimpleName());
					throw new IOESException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
				}
				catch(Exception ex)
				{
					logger.error(ex.getMessage() + " Exception occured while closing database objects in processFilesSuspension method of " + this.getClass().getSimpleName());
					throw new IOESException("Exception : "+ ex.getMessage(), ex);
				}
			}
	    }*/
		
		
		public void processFileSuspension() throws Exception
		{
			logger.info(" Entered into processFilesFor Suspension method of " + this.getClass().getSimpleName());
			Connection conn = null;
			PreparedStatement psmt=null;
			ResultSet rset=null;
			CallableStatement csProcessFiles = null;
			Statement stmt= null;
			try
			{
				conn=DbConnection.getConnectionObject();
				psmt = conn.prepareCall(sqlFetchFileId);
				psmt.setLong(1,3);
				rset=psmt.executeQuery();
				ArrayList <BulkuploadDto> objDto = new ArrayList<BulkuploadDto>();
				BulkuploadDto dto = null; 
				while (rset.next()) 
				{
					dto = new BulkuploadDto();
					dto.setFileID(rset.getString("FILEID"));
					objDto.add(dto);
				}
				rset.close();
				if(objDto.size()>0 )
				{
					for(Iterator iter1 = objDto.iterator(); iter1.hasNext();)
					{
						try
						{
							System.out.println("VALIDATION START");
							csProcessFiles = conn.prepareCall(sqlValidateSuspensionFileid);
							BulkuploadDto element = (BulkuploadDto) iter1.next();
							csProcessFiles.setLong(1, new Long(element.getFileID()));
							csProcessFiles.setInt(2,0);
							csProcessFiles.setString(3,"");
							csProcessFiles.setString(4,"");
							csProcessFiles.setString(5,"");
							csProcessFiles.execute();
							if(csProcessFiles.getString(3)==null)
							{
								System.out.println("VALIDATION SUCCESSFULLY DONE:::::");
								ResultSet rsetlsi=null;
								PreparedStatement psmt1=null;
								psmt1 = conn.prepareCall(sqlGetSuspensionLSIno);
								psmt1.setLong(1,new Long(element.getFileID()));
								rsetlsi=psmt1.executeQuery();
								ArrayList <BulkuploadDto> objDtolsi = new ArrayList<BulkuploadDto>();
								while (rsetlsi.next()) 
								{
									dto = new BulkuploadDto();
									dto.setLsiNo(rsetlsi.getString("LSINO"));
									dto.setReasonId(rsetlsi.getString("REASON_ID"));
									dto.setReasonForSuspension(rsetlsi.getString("REASON_FOR_SUSPENSION"));
									
									objDtolsi.add(dto);
								}
								rsetlsi.close();
								if(objDtolsi.size()>0)
								{
									for (Iterator iter = objDtolsi.iterator(); iter.hasNext();) 
									{
										System.out.println("Creating Orders:::::");
										conn.setAutoCommit(false);	
										csProcessFiles = conn.prepareCall(sqlProcessFileSuspension);
										BulkuploadDto element1 = (BulkuploadDto) iter.next();
										
										csProcessFiles.setLong(1, new Long(element1.getLsiNo()));
										
										csProcessFiles.setString(2," ");
										
										csProcessFiles.setLong(3, new Long(element.getFileID()));
										csProcessFiles.setString(4, element1.getReasonId());
										csProcessFiles.setString(5, element1.getReasonForSuspension());
										csProcessFiles.setLong(6,0);
										csProcessFiles.setLong(7,0);
										csProcessFiles.setString(8,"");
										csProcessFiles.setString(9,"");
										csProcessFiles.setString(10,"");
										csProcessFiles.execute();
										String orderStatus=csProcessFiles.getString(9);
										System.out.println("Error:::::::::::::::::::::::::"+ csProcessFiles.getString(9));
										System.out.println("Step:::::::::::::::::::::::::::"+ csProcessFiles.getString(10));			
										
										if(orderStatus.equalsIgnoreCase("SUCCESS"))
										{
											conn.commit();
										logger.info(" Processing of Suspension method  " + this.getClass().getSimpleName());
										}
										else
										{
											conn.rollback();				
										logger.info(" Processing of Suspension method of " + this.getClass().getSimpleName());
										}
									}
								}
								else
								{
										try{
										
										//Statement stmt=null;
										stmt = conn.createStatement();
										String sql="UPDATE BULKUPLOAD.TM_TTEMPLATEFILE_PROCESS SET FILESTATUSID = 4 WHERE FILEID ="+element.getFileID();
										//psmt = conn.prepareStatement(sql);
										stmt.executeUpdate(sql);
										//stmt.close();
										
											}catch(Exception e){logger.info("SQL Exception  " + this.getClass().getSimpleName());}
									
								}
								
							}
						}
						catch(Exception e)
						{
							logger.info(" End of processFilesFor Suspension method of " + this.getClass().getSimpleName());
						}
						
					}
					logger.info(" End of processFilesFor Suspension method of " + this.getClass().getSimpleName());
				}
			}
			catch(SQLException sqlEx)
			{
				sqlEx.printStackTrace();
				try {
					conn.rollback();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				logger.error(sqlEx.getMessage() + " SQLException occured in processFiles method of " + this.getClass().getSimpleName());
				throw new IOESException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
			}
			catch(Exception ex)
			{
				try {
					conn.rollback();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ex.printStackTrace();
				logger.error(ex.getMessage() + " Exception occured in processFiles method of " + this.getClass().getSimpleName());
				throw new IOESException("Exception : "+ ex.getMessage(), ex);
			}
			finally
			{
				try
				{
					
					DbConnection.closeCallableStatement(csProcessFiles);
					DbConnection.closePreparedStatement(psmt);
					
					DbConnection.freeConnection(conn);
				}
				catch(SQLException sqlEx)
				{
					logger.error(sqlEx.getMessage() + " SQLException occured while closing database objects in processFiles method of " + this.getClass().getSimpleName());
					throw new IOESException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
				}
				catch(Exception ex)
				{
					logger.error(ex.getMessage() + " Exception occured while closing database objects in processFiles method of " + this.getClass().getSimpleName());
					throw new IOESException("Exception : "+ ex.getMessage(), ex);
				}
			}
		
		}
		/*   	********************************************************************************
		 *		Method Name:- 	processFilesResumption()
		 *		Created By:-    Nagarjuna
		 * 		Created On:-    02-12-2013
		 * 		Purpose:-		Scheduler to Create BulkResumption orders.
		 *      ********************************************************************************
		 */ 		
	/*	public void processFilesResumption() throws Exception
		{
				logger.info(" Entered into processFilesResumption method of " + this.getClass().getSimpleName());
				
				Connection conn = null;
				PreparedStatement psmt=null;
				ResultSet rset=null;
				CallableStatement csProcessFiles = null;
				try
				{
					conn=DbConnection.getConnectionObject();
					
					 * Calling Procedure to get all the Fileids to Create BulkResumption orders.
					 * Here parameter (2) means TemplateID of Resumption from db table.
					 * Then after it will iterate all fileids to process and create Resumption Orders.
					 * 
					 
					psmt = conn.prepareCall(sqlFetchFileId);
					psmt.setLong(1, 2);
					rset = psmt.executeQuery();
					ArrayList <BulkuploadDto> objDto = new ArrayList<BulkuploadDto>();
					BulkuploadDto dto = null; 
					while (rset.next()) 
					{
						dto = new BulkuploadDto();
						dto.setFileID(rset.getString("FILEID"));
						objDto.add(dto);
					}
					
					if(objDto.size()>0 )
					{
					
						for (Iterator iter = objDto.iterator(); iter.hasNext();) 
						{
							conn.setAutoCommit(false);	
							csProcessFiles=conn.prepareCall(sqlProcessResumptionFile);
							BulkuploadDto element = (BulkuploadDto) iter.next();
							csProcessFiles.setLong(1, new Long(element.getFileID()));
							csProcessFiles.setLong(2,1);
							csProcessFiles.setLong(3,0);
							csProcessFiles.setLong(4,0);//RESULT
							csProcessFiles.setString(5,"");
							csProcessFiles.setString(6,"");
							csProcessFiles.execute();
							System.out.println("Error:::::::::::::::::::::::::"+ csProcessFiles.getString(5));
							System.out.println("Step:::::::::::::::::::::::::::"+ csProcessFiles.getString(6));			
							if(csProcessFiles.getString(5)==null)
							{
								conn.commit();
						logger.info(" End of processFilesResumption method of " + this.getClass().getSimpleName());
							}
							else
							{
								conn.rollback();				
						logger.info(" End of processFilesResumption method of " + this.getClass().getSimpleName());
							}
					    }
					}
				}
				catch(SQLException sqlEx)
				{
					sqlEx.printStackTrace();
					try {
						conn.rollback();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					logger.error(sqlEx.getMessage() + " SQLException occured in processFilesResumption method of " + this.getClass().getSimpleName());
					throw new IOESException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
				}
				catch(Exception ex)
				{
					try {
						conn.rollback();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ex.printStackTrace();
					logger.error(ex.getMessage() + " Exception occured in processFilesResumption method of " + this.getClass().getSimpleName());
					throw new IOESException("Exception : "+ ex.getMessage(), ex);
				}
				finally
				{
					try
					{
						
						DbConnection.closeCallableStatement(csProcessFiles);
						DbConnection.freeConnection(conn);
					}
					catch(SQLException sqlEx)
					{
						logger.error(sqlEx.getMessage() + " SQLException occured while closing database objects in processFilesResumption method of " + this.getClass().getSimpleName());
						throw new IOESException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
					}
					catch(Exception ex)
					{
						logger.error(ex.getMessage() + " Exception occured while closing database objects in processFilesResumption method of " + this.getClass().getSimpleName());
						throw new IOESException("Exception : "+ ex.getMessage(), ex);
					}
				}
		}*/
		public void processFilesResumption() throws Exception
		{
			logger.info(" Entered into processFilesFor Suspension method of " + this.getClass().getSimpleName());
			Connection conn = null;
			PreparedStatement psmt=null;
			ResultSet rset=null;
			CallableStatement csProcessFiles = null;
			Statement stmt= null;
			try
			{
				conn=DbConnection.getConnectionObject();
				psmt = conn.prepareCall(sqlFetchFileId);
				psmt.setLong(1,2);
				rset=psmt.executeQuery();
				ArrayList <BulkuploadDto> objDto = new ArrayList<BulkuploadDto>();
				BulkuploadDto dto = null; 
				while (rset.next()) 
				{
					dto = new BulkuploadDto();
					dto.setFileID(rset.getString("FILEID"));
					objDto.add(dto);
				}
				rset.close();
				if(objDto.size()>0 )
				{
					for(Iterator iter1 = objDto.iterator(); iter1.hasNext();)
					{
						try
						{
							System.out.println("VALIDATION START");
							csProcessFiles = conn.prepareCall(sqlValidateResumptionFileid);
							BulkuploadDto element = (BulkuploadDto) iter1.next();
							csProcessFiles.setLong(1, new Long(element.getFileID()));
							csProcessFiles.setInt(2,0);
							csProcessFiles.setString(3,"");
							csProcessFiles.setString(4,"");
							csProcessFiles.setString(5,"");
							csProcessFiles.execute();
							if(csProcessFiles.getString(3)==null)
							{
								System.out.println("VALIDATION SUCCESSFULLY DONE:::::");
								ResultSet rsetlsi=null;
								PreparedStatement psmt1=null;
								psmt1 = conn.prepareCall(sqlGetResumptionLSIno);
								psmt1.setLong(1,new Long(element.getFileID()));
								rsetlsi=psmt1.executeQuery();
								ArrayList <BulkuploadDto> objDtolsi = new ArrayList<BulkuploadDto>();
								while (rsetlsi.next()) 
								{
									dto = new BulkuploadDto();
									dto.setLsiNo(rsetlsi.getString("LSINO"));
									dto.setReasonId(rsetlsi.getString("REASON_ID"));
									dto.setReasonForSuspension(rsetlsi.getString("REASON_FOR_DISCONNECTION"));
									
									objDtolsi.add(dto);
								}
								rsetlsi.close();
								if(objDtolsi.size()>0)
								{
									for (Iterator iter = objDtolsi.iterator(); iter.hasNext();) 
									{
										System.out.println("Creating Orders:::::");
										conn.setAutoCommit(false);	
										csProcessFiles = conn.prepareCall(sqlProcessFileResumption);
										BulkuploadDto element1 = (BulkuploadDto) iter.next();
										
										csProcessFiles.setLong(1, new Long(element1.getLsiNo()));
										
										csProcessFiles.setString(2," ");
										
										csProcessFiles.setLong(3, new Long(element.getFileID()));
										csProcessFiles.setString(4, element1.getReasonId());
										csProcessFiles.setString(5, element1.getReasonForSuspension());
										csProcessFiles.setLong(6,0);
										csProcessFiles.setLong(7,0);
										csProcessFiles.setString(8,"");
										csProcessFiles.setString(9,"");
										csProcessFiles.setString(10,"");
										csProcessFiles.execute();
										String orderStatus=csProcessFiles.getString(9);
										System.out.println("Error:::::::::::::::::::::::::"+ csProcessFiles.getString(9));
										System.out.println("Step:::::::::::::::::::::::::::"+ csProcessFiles.getString(10));			
										
										if(orderStatus.equalsIgnoreCase("SUCCESS"))
										{
											conn.commit();
										logger.info(" Processing of Suspension method  " + this.getClass().getSimpleName());
										}
										else
										{
											conn.rollback();				
										logger.info(" Processing of Suspension method of " + this.getClass().getSimpleName());
										}
									}
								}
								else
								{
										try{
										
										//Statement stmt=null;
										stmt = conn.createStatement();
										String sql="UPDATE BULKUPLOAD.TM_TTEMPLATEFILE_PROCESS SET FILESTATUSID = 4 WHERE FILEID ="+element.getFileID();
										//psmt = conn.prepareStatement(sql);
										stmt.executeUpdate(sql);
										//stmt.close();
										
											}catch(Exception e){logger.info("SQL Exception  " + this.getClass().getSimpleName());}
									
								}
								
							}
						}
						catch(Exception e)
						{
							logger.info(" End of processFilesFor Suspension method of " + this.getClass().getSimpleName());
						}
						
					}
					logger.info(" End of processFilesFor Suspension method of " + this.getClass().getSimpleName());
				}
			}
			catch(SQLException sqlEx)
			{
				sqlEx.printStackTrace();
				try {
					conn.rollback();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				logger.error(sqlEx.getMessage() + " SQLException occured in processFiles method of " + this.getClass().getSimpleName());
				throw new IOESException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
			}
			catch(Exception ex)
			{
				try {
					conn.rollback();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ex.printStackTrace();
				logger.error(ex.getMessage() + " Exception occured in processFiles method of " + this.getClass().getSimpleName());
				throw new IOESException("Exception : "+ ex.getMessage(), ex);
			}
			finally
			{
				try
				{
					
					DbConnection.closeCallableStatement(csProcessFiles);
					DbConnection.closePreparedStatement(psmt);
					
					DbConnection.freeConnection(conn);
				}
				catch(SQLException sqlEx)
				{
					logger.error(sqlEx.getMessage() + " SQLException occured while closing database objects in processFiles method of " + this.getClass().getSimpleName());
					throw new IOESException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
				}
				catch(Exception ex)
				{
					logger.error(ex.getMessage() + " Exception occured while closing database objects in processFiles method of " + this.getClass().getSimpleName());
					throw new IOESException("Exception : "+ ex.getMessage(), ex);
				}
			}
		
		}		
		
		/**
		 * @method 	processFiles_forOBValueBulkUpload
		 * @purpose	mark files ready for processing(Updation of OB value)
		 * @param 	fileIds
		 * @throws 	IOESException
		 */
		private static String strGetFileNTemplateIdFromIOMS ="SELECT FILEID, TEMPLATEID FROM BULKUPLOAD.TM_TTEMPLATEFILE_PROCESS WHERE FILESTATUSID=2 and TEMPLATEID=81";
		//public static String sqlGetTemporarySPName = "{call IOE.SP_BULK_GET_PROCESS_SPNAME(?)}";
		public static String spValidateOBValueBeforeProcssing = "{call BULKUPLOAD.SPBULK_PROCESSFILE_OB_VALUE_VALIDATION(?,?,?,?,?,?)}";
		public static String spProcessOBValue = "{call BULKUPLOAD.SPBULK_PROCESSFILE_OB_VALUE_PROCESSING(?,?,?,?,?,?)}";
		
		public void processFiles_forOBValueBulkUpload() throws Exception
		{
			logger.info(" Entered into processFiles_forOBValueBulkUpload  " );
			Connection con = null;
			CallableStatement cstmt=null;
			Statement stmt=null;
			ResultSet rsGetFileTemplateId = null;
			Long fileId=0l;	
			try
			{
				con = DbConnection.getConnectionObject();
				ArrayList<Long> lstFileIDTemplateID = new ArrayList<Long>();

				cstmt = con.prepareCall(strGetFileNTemplateIdFromIOMS);
				rsGetFileTemplateId = cstmt.executeQuery();
				while (rsGetFileTemplateId.next()){
					fileId=rsGetFileTemplateId.getLong("FILEID");					
					lstFileIDTemplateID.add(fileId);						
				}
				rsGetFileTemplateId.close();				

				if(lstFileIDTemplateID.size()==0){
					CRMLogger.SysErr("********* No FileID  present to process!! *********");
				}

				for (Iterator iter = lstFileIDTemplateID.iterator(); iter.hasNext();) {

					Long elementFileID = (Long) iter.next();//Getting the fileID
					CRMLogger.SysErr("********* Validation Started For File ID -> "+elementFileID+" !! *********");
					cstmt = con.prepareCall(spValidateOBValueBeforeProcssing);
					cstmt.setLong(1,elementFileID);
					cstmt.setLong(2,0);//RESULT
					cstmt.setLong(3,0);//@SQLCODE
					cstmt.setString(4,"");//@MSGCODE
					cstmt.setString(5,"");//@DIAGNOSTICS
					cstmt.setString(6,"");//@LOG
					cstmt.execute();

					if(cstmt.getLong(2)==5 && cstmt.getString(5)==null){//If Successfully Validated
						/*Vijay
						 *  Now processing would be done after OB calculation phase, so following codes are commented.
						 *   when OB calculation will be done then file status would be change with 5 (Processing successfully)
						 */
						
						/*CRMLogger.SysErr("********* Processing Started For File ID -> "+elementFileID+" !! *********");
						con.setAutoCommit(false);
						cstmt = con.prepareCall(spProcessOBValue);
						cstmt.setLong(1,elementFileID);
						cstmt.setLong(2,0);//RESULT
						cstmt.setLong(3,0);//@SQLCODE
						cstmt.setString(4,"");//@MSGCODE
						cstmt.setString(5,"");//@DIAGNOSTICS
						cstmt.setString(6,"");//@LOG
						cstmt.execute();

						if(cstmt.getLong(2)==5 && cstmt.getString(5)==null){//If Successfully Processed
							CRMLogger.SysErr("********* Processing Done Successfully For File ID -> "+elementFileID+" !! *********");
							con.commit();
						}else{
							con.rollback();	
							CRMLogger.SysErr("********* Processing Error For File ID -> "+elementFileID+" !! *********");
							CRMLogger.SysErr("********* Error Step -> "+cstmt.getString(6)+" !! *********");
							CRMLogger.SysErr("********* Error Message -> "+cstmt.getString(5)+" !! *********");
							stmt = con.createStatement();
							String sql="UPDATE BULKUPLOAD.TM_TTEMPLATEFILE_PROCESS SET FILESTATUSID = 4 WHERE FILEID = "+elementFileID;
							stmt.executeUpdate(sql);
						}*/
					}else if(cstmt.getLong(2)==4 && cstmt.getString(5)==null){
						CRMLogger.SysErr("********* Validation Error For File ID -> "+elementFileID+" !! *********");
						CRMLogger.SysErr("********* Error Step -> "+cstmt.getString(6)+" !! *********");
						CRMLogger.SysErr("********* Error Message -> "+cstmt.getString(4)+" !! *********");
					}else{
						CRMLogger.SysErr("********* SQL Error During Validation For File ID -> "+elementFileID+" !! *********");
						CRMLogger.SysErr("********* Error Step -> "+cstmt.getString(6)+" !! *********");
						CRMLogger.SysErr("********* Error Message -> "+cstmt.getString(5)+" !! *********");
					}
				}	
			}catch(SQLException sqlEx){
				sqlEx.printStackTrace();
				logger.error(sqlEx.getMessage() + " SQLException occured in processFiles method of " );
				throw new IOESException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
			}catch(Exception ex){
				ex.printStackTrace();
				logger.error(ex.getMessage() + " Exception occured in processFiles method of " );
				throw new IOESException("Exception : "+ ex.getMessage(), ex);
			}finally{
				try{	
					DbConnection.closeResultset(rsGetFileTemplateId);
					DbConnection.closeCallableStatement(cstmt);
					DbConnection.freeConnection(con);
				}catch(SQLException sqlEx){
					logger.error(sqlEx.getMessage() + " SQLException occured while closing database objects in processFiles method of ");
					throw new IOESException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
				}catch(Exception ex){
					logger.error(ex.getMessage() + " Exception occured while closing database objects in processFiles method of ");
					throw new IOESException("Exception : "+ ex.getMessage(), ex);
				}
			}
			logger.info(" End of  processFiles_forOBValueBulkUpload " );
		}
		
		public void processFilesRaterenewal() throws Exception
		{
			logger.info(" Entered into processFilesRaterenewal method of " + this.getClass().getSimpleName());
			Connection conn = null;
			PreparedStatement psmt=null;
			ResultSet rset=null;
			CallableStatement csProcessFiles = null;
			CallableStatement csProcessFilesInternal = null;
			Statement stmt= null;
			try
			{
				conn=DbConnection.getConnectionObject();
				psmt = conn.prepareCall(sqlFetchFileId);
				psmt.setLong(1,5);
				rset=psmt.executeQuery();
				ArrayList <BulkuploadDto> objDto = new ArrayList<BulkuploadDto>();
				BulkuploadDto dto = null; 
				
				while (rset.next()) 
				{
					dto = new BulkuploadDto();
					dto.setFileID(rset.getString("FILEID"));
					objDto.add(dto);
				}
				rset.close();
				psmt.close();
				
				if(objDto.size()>0 ){
					for(Iterator<BulkuploadDto> iter1 = objDto.iterator(); iter1.hasNext();){
						try{
							System.out.println("VALIDATION START");
							csProcessFiles = conn.prepareCall(sqlValidateRaterenewalFileid);
							BulkuploadDto element = (BulkuploadDto) iter1.next();
							csProcessFiles.setLong(1, new Long(element.getFileID()));
							csProcessFiles.setInt(2,0);
							csProcessFiles.setString(3,"");
							csProcessFiles.setString(4,"");
							csProcessFiles.setString(5,"");
							csProcessFiles.execute();
							System.out.println("BEFORE GETTING LSI NO"+csProcessFiles.getString(3));
							if(csProcessFiles.getString(3)==null){
								System.out.println("GETTING LSI NO");
								ResultSet rsetlsi=null;
								PreparedStatement psmt1=null;
								ArrayList <BulkuploadDto> objDtolsi = null;
								try{
									psmt1 = conn.prepareCall(sqlGetRaterenewalLSIno);
									psmt1.setLong(1,new Long(element.getFileID()));
									rsetlsi=psmt1.executeQuery();
									objDtolsi = new ArrayList<BulkuploadDto>();
									while (rsetlsi.next()) {
										dto = new BulkuploadDto();
										dto.setLsiNo(rsetlsi.getString("LSINO"));
																		
										objDtolsi.add(dto);
									}
								}finally{
									rsetlsi.close();
									psmt1.close();
								}
								
								if(objDtolsi.size()>0){
									for (Iterator<BulkuploadDto> iter = objDtolsi.iterator(); iter.hasNext();) {
										System.out.println("Creating Orders:::::");
										conn.setAutoCommit(false);	
										csProcessFilesInternal = conn.prepareCall(sqlProcessFileRaterenewal);
										BulkuploadDto element1 = (BulkuploadDto) iter.next();
										logger.info("LSI NO:::"+element1.getLsiNo());
										logger.info("FILE ID:::"+element.getFileID());
										csProcessFilesInternal.setLong(1, new Long(element1.getLsiNo()));
										
										csProcessFilesInternal.setLong(2,new Long(element.getFileID()));
										
										csProcessFilesInternal.setLong(3,0);
										csProcessFilesInternal.setString(4, "");
										csProcessFilesInternal.setString(5,"");
										csProcessFilesInternal.setString(6,"");
										csProcessFilesInternal.setLong(7,0);
										
										csProcessFilesInternal.execute();
										String orderStatus=csProcessFilesInternal.getString(6);
										logger.info(" sqlcode::: " + csProcessFilesInternal.getString(3));
										logger.info(" MSGcode::: " + csProcessFilesInternal.getString(4));
										logger.info(" MSG::: " + csProcessFilesInternal.getString(5));
										System.out.println("Error:::::::::::::::::::::::::"+ csProcessFilesInternal.getString(6));
										System.out.println("Step:::::::::::::::::::::::::::"+ csProcessFilesInternal.getString(7));			
										if(orderStatus.equalsIgnoreCase("SUCCESS")){
											conn.commit();
											logger.info(" Processing of RateRenewal method  " + this.getClass().getSimpleName());
										}else{
											conn.rollback();				
											logger.info(" Processing of RateRenewal method of " + this.getClass().getSimpleName());
										}
										csProcessFilesInternal.close();
									}
									logger.info(" Processing of file Completed " + this.getClass().getSimpleName());
									conn.setAutoCommit(true);
									try{
										psmt=conn.prepareCall(sqlChangeFileIdStatus);
										psmt.setLong(1,new Long(element.getFileID()));
										int i=psmt.executeUpdate();
										logger.info(" Status Updated " + i);
										
									}catch(Exception e1){e1.printStackTrace();}
									finally{
										psmt.close();
									}
									logger.info(" Processing of file Status Updated Successfully " + this.getClass().getSimpleName());
							
								}else{
									logger.info(" Processing of file Completed with all LSI Contains ERROR  " + this.getClass().getSimpleName());
									stmt = conn.createStatement();
									String sql="UPDATE BULKUPLOAD.TM_TTEMPLATEFILE_PROCESS SET FILESTATUSID = 4 WHERE FILEID ="+element.getFileID();
									stmt.executeUpdate(sql);
								}
							}
						}catch(Exception e){
												logger.error(e.getMessage() + " Exception occured in processFiles method of " + this.getClass().getSimpleName());
												throw new IOESException("Exception : "+ e.getMessage(), e);
						}finally{
							DbConnection.closeCallableStatement(csProcessFiles);	
							DbConnection.closeStatement(stmt);
							DbConnection.closeCallableStatement(csProcessFilesInternal);
						}
					}
				}
			}catch(Exception ex){
									logger.error(ex.getMessage() + " Exception occured in processFiles method of " + this.getClass().getSimpleName());
									throw new IOESException("Exception : "+ ex.getMessage(), ex);
								}
			finally{
				try{	
					conn.commit();			
					DbConnection.closeResultset(rset);
					//DbConnection.closePreparedStatement(psmt);
					//DbConnection.closeCallableStatement(csProcessFiles);
					
				}catch(SQLException sqlEx){
					logger.error(sqlEx.getMessage() + " SQLException occured while closing database objects in processFiles method of ");
					throw new IOESException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
				}catch(Exception ex){
					logger.error(ex.getMessage() + " Exception occured while closing database objects in processFiles method of ");
					throw new IOESException("Exception : "+ ex.getMessage(), ex);
				}finally{
					DbConnection.freeConnection(conn);
				}
			}
		}
		
		/**
		 * @method 	processFiles_forOBValueUsageBulkUpload
		 * @purpose	mark files ready for processing(Updation of OB value)
		 * @param 	fileIds
		 * @throws 	IOESException
		 */
		private static String strGetFileIDNTemplateIdFromIOMS ="SELECT FILEID, TEMPLATEID FROM BULKUPLOAD.TM_TTEMPLATEFILE_PROCESS WHERE FILESTATUSID=2 and TEMPLATEID=82";
		private static String spProcessingValidationOBValueUsage = "{call BULKUPLOAD.SPBULK_PROCESSFILE_OBVALUE_USAGE_VALIDATION(?,?,?,?,?,?)}";
		private static String spCreateOBValueUsageOrders = "{call BULKUPLOAD.SPBULK_PROCESSFILE_OBVALUE_USAGE_PROCESSING(?,?,?,?,?,?)}";
		//private static String sqlGetComponentIdforOBValueUsage= "{call BULKUPLOAD.SP_FETCH_OBVALUEUSAGE_COMPONENTID_BULKUPLOAD(?)}";
		
		public void processFiles_forOBValueUsage_BulkUpload() throws Exception
		{
			logger.info(" Entered into processFiles_forOBValueUsageBulkUpload  " );
			Connection con = null;
			CallableStatement cstmt=null;
			Statement stmt=null;
			ResultSet rsGetFileTemplateId = null;
			Long fileId=0l;	
			try
			{
				con = DbConnection.getConnectionObject();
				ArrayList<Long> lstFileIDTemplateID = new ArrayList<Long>();
				cstmt = con.prepareCall(strGetFileIDNTemplateIdFromIOMS);
				rsGetFileTemplateId = cstmt.executeQuery();
				while (rsGetFileTemplateId.next()){
					fileId=rsGetFileTemplateId.getLong("FILEID");					
					lstFileIDTemplateID.add(fileId);						
				}
				rsGetFileTemplateId.close();				

				if(lstFileIDTemplateID.size()==0){
					CRMLogger.SysErr("********* No FileID  present to process!! *********");
				}

				for (Iterator iter = lstFileIDTemplateID.iterator(); iter.hasNext();) {

					Long elementFileID = (Long) iter.next();//Getting the fileID
					CRMLogger.SysErr("********* Validation Started For File ID -> "+elementFileID+" !! *********");
					cstmt = con.prepareCall(spProcessingValidationOBValueUsage);
					cstmt.setLong(1,elementFileID);
					cstmt.setLong(2,0);//RESULT
					cstmt.setLong(3,0);//@SQLCODE
					cstmt.setString(4,"");//@MSGCODE
					cstmt.setString(5,"");//@DIAGNOSTICS
					cstmt.setString(6,"");//@LOG
					cstmt.execute();

					if(cstmt.getLong(2)==5 && cstmt.getString(5)==null){//If Successfully Validated
						/*Vijay
						 *  Now processing would be done after OB calculation phase, so following codes are commented.
						 *   when OB calculation will be done then file status would be change with 5 (Processing successfully)
						 */
					
						/*CRMLogger.SysErr("********* Processing Started For File ID -> "+elementFileID+" !! *********");
						con.setAutoCommit(false);
						cstmt = con.prepareCall(spCreateOBValueUsageOrders);
						cstmt.setLong(1,elementFileID);
						cstmt.setLong(2,0);//RESULT
						cstmt.setLong(3,0);//@SQLCODE
						cstmt.setString(4,"");//@MSGCODE
						cstmt.setString(5,"");//@DIAGNOSTICS
						cstmt.setString(6,"");//@LOG
						//cstmt.setString(7, element1.getComponentid());
						CRMLogger.SysErr("********* Processing Started For File ID -> "+elementFileID+" !! *********");
						cstmt.execute();

						if(cstmt.getLong(2)==5 && cstmt.getString(5)==null){//If Successfully Processed
							CRMLogger.SysErr("********* Processing Done Successfully For File ID -> "+elementFileID+" !! *********");
							con.commit();
						}else{
							con.rollback();	
							CRMLogger.SysErr("********* Processing Error For File ID -> "+elementFileID+" !! *********");
							CRMLogger.SysErr("********* Error Step -> "+cstmt.getString(6)+" !! *********");
							CRMLogger.SysErr("********* Error Message -> "+cstmt.getString(5)+" !! *********");
							stmt = con.createStatement();
							String sql="UPDATE BULKUPLOAD.TM_TTEMPLATEFILE_PROCESS SET FILESTATUSID = 4 WHERE FILEID = "+elementFileID;
							stmt.executeUpdate(sql);
						}*/
					}else if(cstmt.getLong(2)==4 && cstmt.getString(5)==null){
						CRMLogger.SysErr("********* Validation Error For File ID -> "+elementFileID+" !! *********");
						CRMLogger.SysErr("********* Error Step -> "+cstmt.getString(6)+" !! *********");
						CRMLogger.SysErr("********* Error Message -> "+cstmt.getString(4)+" !! *********");
					}else{
						CRMLogger.SysErr("********* SQL Error During Validation For File ID -> "+elementFileID+" !! *********");
						CRMLogger.SysErr("********* Error Step -> "+cstmt.getString(6)+" !! *********");
						CRMLogger.SysErr("********* Error Message -> "+cstmt.getString(5)+" !! *********");
					}
				}	
			}catch(SQLException sqlEx){
				sqlEx.printStackTrace();
				logger.error(sqlEx.getMessage() + " SQLException occured in processFiles method of " );
				throw new IOESException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
			}catch(Exception ex){
				ex.printStackTrace();
				logger.error(ex.getMessage() + " Exception occured in processFiles method of " );
				throw new IOESException("Exception : "+ ex.getMessage(), ex);
			}finally{
				try{	
					DbConnection.closeResultset(rsGetFileTemplateId);
					DbConnection.closeCallableStatement(cstmt);
					DbConnection.freeConnection(con);
				}catch(SQLException sqlEx){
					logger.error(sqlEx.getMessage() + " SQLException occured while closing database objects in processFiles method of ");
					throw new IOESException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
				}catch(Exception ex){
					logger.error(ex.getMessage() + " Exception occured while closing database objects in processFiles method of ");
					throw new IOESException("Exception : "+ ex.getMessage(), ex);
				}
			}
			logger.info(" End of  processFiles_forOBValueUsageBulkUpload " );
		}

		
		public void processFiles_forChargeRedirection() throws Exception
		{
			logger.info(" Entered into processFiles_forChargeRedirection  " );
			Connection con = null;
			CallableStatement cstmtForGetChargeRedirectionFileId,cstmtForValidation = null,cstmtForProcessingForChargeRedirection=null;
			Statement stmt=null;
			ResultSet rsGetFileTemplateId = null;
			Long fileId=0l;	
			try
			{
				con = DbConnection.getConnectionObject();
				ArrayList<Long> lstFileIDTemplateID = new ArrayList<Long>();
				cstmtForGetChargeRedirectionFileId = con.prepareCall(strGetFileIDNTemplateIdforChargeRedirection);
				rsGetFileTemplateId = cstmtForGetChargeRedirectionFileId.executeQuery();
				while (rsGetFileTemplateId.next()){
					fileId=rsGetFileTemplateId.getLong("FILEID");					
					lstFileIDTemplateID.add(fileId);						
				}
				rsGetFileTemplateId.close();	
				cstmtForGetChargeRedirectionFileId.close();
				
				if(lstFileIDTemplateID.size()==0){
					logger.info("********* No FileID  present to process!! *********");
				}

				for (Iterator iter = lstFileIDTemplateID.iterator(); iter.hasNext();) {
					Long elementFileID = null;
					elementFileID = (Long) iter.next();
					try {
						logger.info("********* Validation Started For File ID -> "+elementFileID+" !! *********");
							
							con.setAutoCommit(true);
							
							cstmtForValidation = con.prepareCall(spProcessingValidationForChargeRedirection);
									
							cstmtForValidation.setLong(1,elementFileID);
							cstmtForValidation.setLong(2,0);//RESULT
							cstmtForValidation.setLong(3,0);//@SQLCODE
							cstmtForValidation.setString(4,"");//@MSGCODE
							cstmtForValidation.setString(5,"");//@DIAGNOSTICS
							cstmtForValidation.setString(6,"");//@LOG
							cstmtForValidation.execute();

						if(cstmtForValidation.getLong(2)==5 && cstmtForValidation.getString(5)==null){
							try {
								logger.info("********* Processing Started For File ID -> "+elementFileID+" !! *********");
								con.setAutoCommit(false);
								cstmtForProcessingForChargeRedirection = con.prepareCall(spProcessingForChargeRedirection);
								cstmtForProcessingForChargeRedirection.setLong(1,elementFileID);
								cstmtForProcessingForChargeRedirection.setLong(2,0);//RESULT
								cstmtForProcessingForChargeRedirection.setLong(3,0);//@SQLCODE
								cstmtForProcessingForChargeRedirection.setString(4,"");//@MSGCODE
								cstmtForProcessingForChargeRedirection.setString(5,"");//@DIAGNOSTICS
								cstmtForProcessingForChargeRedirection.setString(6,"");//@LOG
								logger.info("********* Processing Started For File ID -> "+elementFileID+" !! *********");
								cstmtForProcessingForChargeRedirection.execute();

								if(cstmtForProcessingForChargeRedirection.getLong(2)==5 && cstmtForProcessingForChargeRedirection.getString(5)==null){//If Successfully Processed
									logger.info("********* Processing Done Successfully For File ID -> "+elementFileID+" !! *********");
									con.commit();
									
								}else{
									con.rollback();	
									logger.info("********* ERROR IN BULKUPLOAD CHARGE REDIRECTION JAVA CODE FOR FILE ID : -> "+elementFileID+" !! *********");
									logger.info("********* Processing Error For File ID -> "+elementFileID+" !! *********");
									logger.info("********* Error Step -> "+cstmtForProcessingForChargeRedirection.getString(6)+" !! *********");
									logger.info("********* Error Message -> "+cstmtForProcessingForChargeRedirection.getString(5)+" !! *********");
									stmt = con.createStatement();
									String sql="UPDATE BULKUPLOAD.TM_TTEMPLATEFILE_PROCESS SET FILESTATUSID = 4 WHERE FILEID = "+elementFileID;
									stmt.executeUpdate(sql);
									DbConnection.closeStatement(stmt);
									con.commit();
								}
								
							} catch (Exception e) {
								con.rollback();	
								logger.error("********* ERROR IN BULKUPLOAD CHARGE REDIRECTION JAVA CODE FOR FILE ID : -> "+elementFileID+" !! *********");
								logger.error("********* Processing Error For File ID -> "+elementFileID+" !! *********");
								logger.error("********* Error Step -> "+cstmtForProcessingForChargeRedirection.getString(6)+" !! *********");
								logger.error("********* Error Message -> "+cstmtForProcessingForChargeRedirection.getString(5)+" !! *********");
								stmt = con.createStatement();
								String sql="UPDATE BULKUPLOAD.TM_TTEMPLATEFILE_PROCESS SET FILESTATUSID = 4 WHERE FILEID = "+elementFileID;
								stmt.executeUpdate(sql);
								con.commit();
								DbConnection.closeStatement(stmt);
								Utility.LOG(e);
							}
							
							
						}else if(cstmtForValidation.getLong(2)==4 && cstmtForValidation.getString(5)==null){
							logger.info("********* Validation Error For File ID -> "+elementFileID+" !! *********");
							logger.info("********* Error Step -> "+cstmtForValidation.getString(6)+" !! *********");
							logger.info("********* Error Message -> "+cstmtForValidation.getString(4)+" !! *********");
						}else{
							String sql="UPDATE BULKUPLOAD.TM_TTEMPLATEFILE_PROCESS SET FILESTATUSID = 4 WHERE FILEID = "+elementFileID;
							stmt.executeUpdate(sql);
							con.commit();
							logger.info("********* SQL Error During Validation For File ID -> "+elementFileID+" !! *********");
							logger.info("********* Error Step -> "+cstmtForValidation.getString(6)+" !! *********");
							logger.info("********* Error Message -> "+cstmtForValidation.getString(5)+" !! *********");
						}
					} catch (Exception e) {
						String sql="UPDATE BULKUPLOAD.TM_TTEMPLATEFILE_PROCESS SET FILESTATUSID = 4 WHERE FILEID = "+elementFileID;
						stmt.executeUpdate(sql);
						con.commit();
						DbConnection.closeStatement(stmt);
						Utility.LOG(e);
					}finally{
						DbConnection.closeCallableStatement(cstmtForProcessingForChargeRedirection);
						DbConnection.closeCallableStatement(cstmtForValidation);
					}
					
				}	
			}catch(SQLException sqlEx){
				Utility.LOG(sqlEx);
				logger.error(sqlEx.getMessage() + " SQLException occured in processFiles method of " );
				throw new IOESException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
			}catch(Exception ex){
				Utility.LOG(ex);
				logger.error(ex.getMessage() + " Exception occured in processFiles method of " );
				throw new IOESException("Exception : "+ ex.getMessage(), ex);
			}finally{
				try{
					if(con!=null && con.isClosed()==false &&con.getAutoCommit()==false){
						con.rollback();
					}
					con.close();
				}catch(SQLException sqlEx){
					logger.error(sqlEx.getMessage() + " SQLException occured while closing database objects in processFiles method of ");
					throw new IOESException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
				}catch(Exception ex){
					logger.error(ex.getMessage() + " Exception occured while closing database objects in processFiles method of ");
					throw new IOESException("Exception : "+ ex.getMessage(), ex);
				}
			}
			logger.info(" End of  processFiles_forChargeRedirection " );
		}
		
		public static void processBulkSITrasnferJob() throws Exception
		{
			logger.info("Step->1: Entered into processFiles method of " + BulkUpload.class.getSimpleName());
			
			Connection conn = null;
			CallableStatement csProcessFiles = null;
			ResultSet rs=null;			
			BulkuploadDto dto=null;
			ArrayList<BulkuploadDto> lstfFileid = new ArrayList<BulkuploadDto>();
			BulkuploadDto newObj=null; 

			try
			{
				conn=DbConnection.getConnectionObject();
				csProcessFiles = conn.prepareCall(sqlFetchFileId);
				csProcessFiles.setInt(1,22);//Template Id for SITransfer
				rs=csProcessFiles.executeQuery();
				while(rs.next())
				{
					dto = new BulkuploadDto();
					dto.setFileID(rs.getString("FILEID"));
					//[001] Start
					dto.setBtDoneBy(rs.getLong("EMP_ID"));
					//[001] End
					dto.setRespId(rs.getInt("RESP_ID"));
					lstfFileid.add(dto);
				}
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(csProcessFiles);
				for(int i=0; i<lstfFileid.size();i++)
				{					
					newObj =lstfFileid.get(i);
				
					//master validation for SI Transfer Bulkuplaod
					boolean successValidateFileIds=TransactionProcessDaoImpl.processMasterValidationForSITransfer(Long.valueOf(newObj.getFileID()),newObj.getRespId(),newObj.getBtDoneBy());	
					if(successValidateFileIds){
						Utility.LOG("BulkSITransfer Done successfully for fileid:"+newObj.getFileID());
					}else{
						Utility.LOG("BulkSITransfer FAILED for fileid:"+newObj.getFileID());
					}
				}
			}
				
			catch(SQLException sqlEx)
			{
				sqlEx.printStackTrace();
				try {
					conn.rollback();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				logger.error(sqlEx.getMessage() + " SQLException occured in processFiles method of " + BulkUpload.class.getSimpleName());
				throw new IOESException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
			}
			catch(Exception ex)
			{
				try {
					conn.rollback();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ex.printStackTrace();
				logger.error(ex.getMessage() + " Exception occured in processFiles method of " + BulkUpload.class.getSimpleName());
				throw new IOESException("Exception : "+ ex.getMessage(), ex);
			}
			finally
			{
				try
				{
					DbConnection.closeCallableStatement(csProcessFiles);
					DbConnection.freeConnection(conn);
				}
				catch(SQLException sqlEx)
				{
					logger.error(sqlEx.getMessage() + " SQLException occured while closing database objects in processFiles method of " + BulkUpload.class.getSimpleName());
					throw new IOESException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
				}
				catch(Exception ex)
				{
					logger.error(ex.getMessage() + " Exception occured while closing database objects in processFiles method of " + BulkUpload.class.getSimpleName());
					throw new IOESException("Exception : "+ ex.getMessage(), ex);
				}
			}
		}
//Start [002]		
		public static void processBulkCurrencyTrasnferJob() throws Exception {

			logger.info("Step->1: Entered into processFiles method of " + BulkUpload.class.getSimpleName());
			
			Connection conn = null;
			CallableStatement csProcessFiles = null;
			ResultSet rs=null;			
			BulkuploadDto dto=null;
			ArrayList<BulkuploadDto> lstfFileid = new ArrayList<BulkuploadDto>();
			BulkuploadDto newObj=null; 

			try
			{
				conn=DbConnection.getConnectionObject();
				csProcessFiles = conn.prepareCall(sqlFetchFileId);
				csProcessFiles.setInt(1,24);//Template Id for Currency Transfer
				rs=csProcessFiles.executeQuery();
				while(rs.next())
				{
					dto = new BulkuploadDto();
					dto.setFileID(rs.getString("FILEID"));
					//[001] Start
					dto.setBtDoneBy(rs.getLong("EMP_ID"));
					//[001] End
					dto.setRespId(rs.getInt("RESP_ID"));
					lstfFileid.add(dto);
				}
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(csProcessFiles);
				for(int i=0; i<lstfFileid.size();i++)
				{					
					newObj =lstfFileid.get(i);
				
					//master validation for Currency Transfer Bulkuplaod
					boolean successValidateFileIds=TransactionProcessDaoImpl.processMasterValidationForCurrencyTransfer(Long.valueOf(newObj.getFileID()),newObj.getRespId(),newObj.getBtDoneBy());	
					if(successValidateFileIds){
						Utility.LOG("BulkCurrencyTransfer Done successfully for fileid:"+newObj.getFileID());
					}else{
						Utility.LOG("BulkCurrencyTransfer FAILED for fileid:"+newObj.getFileID());
					}
				}
			}
				
			catch(SQLException sqlEx)
			{
				logger.error(sqlEx.getMessage() + " SQLException occured in processFiles method of " + BulkUpload.class.getSimpleName());
				throw new IOESException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
			}
			catch(Exception ex)
			{
				logger.error(ex.getMessage() + " Exception occured in processFiles method of " + BulkUpload.class.getSimpleName());
				throw new IOESException("Exception : "+ ex.getMessage(), ex);
			}
			finally
			{
				try
				{
					DbConnection.closeCallableStatement(csProcessFiles);
					DbConnection.freeConnection(conn);
				}
				catch(SQLException sqlEx)
				{
					logger.error(sqlEx.getMessage() + " SQLException occured while closing database objects in processFiles method of " + BulkUpload.class.getSimpleName());
					throw new IOESException("SQL Exception : "+ sqlEx.getMessage(), sqlEx);
				}
				catch(Exception ex)
				{
					logger.error(ex.getMessage() + " Exception occured while closing database objects in processFiles method of " + BulkUpload.class.getSimpleName());
					throw new IOESException("Exception : "+ ex.getMessage(), ex);
				}
			}
		
			
		}
//End [002]
public  static void main (String args[]) throws Exception
{
	//BulkUpload bu=new BulkUpload();
	//bu.processFilePermanentDisconnection();
	//bu.processFileSuspension();
	//bu.processFilesResumption();
	//bu.processFiles_forOBValueBulkUpload();
	//bu.processFilesRaterenewal();
}

	
}
