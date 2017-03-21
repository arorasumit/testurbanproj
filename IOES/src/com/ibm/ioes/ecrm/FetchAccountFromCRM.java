package com.ibm.ioes.ecrm;

import com.ibm.ioes.beans.ValidationBean;
import com.ibm.ioes.clep.CLEPUtility;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;

import com.ibm.ioes.ecrm.DBConnectionRetriever;
import com.ibm.ioes.ecrm.TransactionBatch;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Utility;


public class FetchAccountFromCRM {
	private static String strGetInsertECRMProductDDValues = "SELECT attribute15 ProductName ,attribute15||'-'||lookup_code||'-'||meaning TEXT ,lookup_code, "+
		" CREATION_DATE , LAST_UPDATE_DATE , ENABLED_FLAG FROM apps.fnd_lookup_values a WHERE LOOKUP_TYPE = 'IBMOE_HARDWARE_PRODUCT' "
		+" AND CREATION_DATE BETWEEN TO_DATE(?) AND SYSDATE ";
		//+" AND attribute15 = ? ";
	
	private static String strGetUpdateECRMProductDDValues = "SELECT attribute15 ProductName ,attribute15||'-'||lookup_code||'-'||meaning TEXT ,lookup_code, "+
		" CREATION_DATE , LAST_UPDATE_DATE , ENABLED_FLAG FROM apps.fnd_lookup_values a WHERE LOOKUP_TYPE = 'IBMOE_HARDWARE_PRODUCT' "
		+" AND LAST_UPDATE_DATE BETWEEN TO_DATE(?) AND SYSDATE";
		//+" AND attribute15 = ? ";

	public static String spInsertUpdateECRMProductDDValuesToIOMS="{call IOE.SP_INSERT_UPDATE_PRODUCT_ATT_VALUES(?,?,?,?,?,?,?,?,?) }";
	
	private static String strGetInsertUpdateDateFromIOMS="SELECT IOE.FORMAT_DATE_REPORT1(INSERTDATE) AS INSERTDATE ,IOE.FORMAT_DATE_REPORT1(UPDATEDATE) AS UPDATEDATE "+ 
		" FROM IOE.TTRACKINSERTDATE WHERE TABLENAME=?";
	private static String transactionDate=null;
	
public static String fetchingAccountBCPFromCRM(String crmAcctNo){
		
		int insertStatus=-1;
		Connection crmcon=null;							
		String crmAccountNo="";
		int length=0;
		int accountSyncErrCode=0;
		String statusMsg="";
		String statusBCP="";
		try{
			
			if("Y".equalsIgnoreCase(Utility.getAppConfigValue("ISFETCHACCOUNTFROMGUI"))){
				if(crmAcctNo !=null && (!"".equalsIgnoreCase(crmAcctNo))){
					crmAccountNo=crmAcctNo.trim();
					length=crmAccountNo.length();	
				}							
				if(length > 7){
					DBConnectionRetriever con = new DBConnectionRetriever();
					CRMLogger.SysErr("******************************[START ACCOUNT FETCHING REQUEST FROM GUI]************************************");
					CRMLogger.SysErr("----- Connecting with CRM Database .....");
					crmcon= con.getCRMConnection();
				
						if(crmcon!=null)
						{
							CRMLogger.SysErr("----- Connected ------");
							int isAccountinIb2b=TransactionBatch.findAccountAlreadyInIB2B(crmAcctNo);
							if(isAccountinIb2b > 0){
								
								CRMLogger.SysErr("Account Updation Started for CRM AccountNo => "+crmAccountNo);
								statusMsg=TransactionBatch.fetchAccountFromCRM(crmcon,1,"update",crmAccountNo);
								CRMLogger.SysErr("Account Updation Completed.....");
								insertStatus=accountSyncErrCode;
								if("1".equalsIgnoreCase(statusMsg)){
									CRMLogger.SysErr("BCP Address Inserting Start .....");
									statusBCP=TransactionBatch.fetchBCPAddressFromCRM(String.valueOf(isAccountinIb2b),1,"GUI");
									CRMLogger.SysErr("BCP Address Inserting Completed.....");																			
							
									CRMLogger.SysErr("BCP Address Updation Start .....");
									statusBCP=TransactionBatch.fetchBCPAddressFromCRM(String.valueOf(isAccountinIb2b),2,"GUI");
									CRMLogger.SysErr("BCP Address Updation Completed.....");
									
									CRMLogger.SysErr("DISPATCH Address Inserting Start .....");
									statusBCP=TransactionBatch.fetchDispatchAddressFromCRM(String.valueOf(isAccountinIb2b),1);
									CRMLogger.SysErr("DISPATCH Address Inserting Completed.....");
																															
									CRMLogger.SysErr("DISPATCH Address Updation Start .....");
									statusBCP=TransactionBatch.fetchDispatchAddressFromCRM(String.valueOf(isAccountinIb2b),2);
									CRMLogger.SysErr("DISPATCH Address Updation Completed.....");
								}
								
							}else if(isAccountinIb2b == 0){
								
								CRMLogger.SysErr("Account Inserting Started for CRM AccountNo => "+crmAccountNo);
								statusMsg=TransactionBatch.fetchAccountFromCRM(crmcon,1,"inserting",crmAccountNo);
								CRMLogger.SysErr("Account Inserting Completed.....");
								insertStatus=accountSyncErrCode;
								
								if("1".equalsIgnoreCase(statusMsg)){
									int accountid=TransactionBatch.findAccountAlreadyInIB2B(crmAccountNo);
									CRMLogger.SysErr("BCP Address Inserting Start .....");
									statusBCP=TransactionBatch.fetchBCPAddressFromCRM(String.valueOf(accountid),1,"GUI");
									CRMLogger.SysErr("BCP Address Inserting Completed.....");																			
							
									CRMLogger.SysErr("BCP Address Updation Start .....");
									statusBCP=TransactionBatch.fetchBCPAddressFromCRM(String.valueOf(accountid),2,"GUI");
									CRMLogger.SysErr("BCP Address Updation Completed.....");
									
									CRMLogger.SysErr("DISPATCH Address Inserting Start .....");
									statusBCP=TransactionBatch.fetchDispatchAddressFromCRM(String.valueOf(accountid),1);
									CRMLogger.SysErr("DISPATCH Address Inserting Completed.....");
																															
									CRMLogger.SysErr("DISPATCH Address Updation Start .....");
									statusBCP=TransactionBatch.fetchDispatchAddressFromCRM(String.valueOf(accountid),2);
									CRMLogger.SysErr("DISPATCH Address Updation Completed.....");
								}
								
							}else{
								insertStatus=isAccountinIb2b;
								statusMsg="Error[-1]:Invalid Account!!";
							}
														
						}else{
							statusMsg="Error[-2]: Failed to Connect with CRM Database";
							CRMLogger.SysErr("------Error: Failed to Connect with CRM Database ---------");
							insertStatus=-2;
						}
				}else{					
					statusMsg="Error: Invalid Account!!";
					CRMLogger.SysErr("********Invalid Account["+insertStatus+"] ******** ");
				}
			}else{
				CRMLogger.SysErr("------ Account Fetching Switch off ------");
				insertStatus=-4;
			}
			
		}catch(Exception exp){
			insertStatus=-2222;
			statusMsg="Error[-2222]:Some error has occured In method fetchingAccountBCPFromCRM()";
			CRMLogger.SysErr("------- Error: "+exp.getMessage());	
			exp.printStackTrace();
		}finally{
			try{
				if(crmcon !=null){
					crmcon.close();
					CRMLogger.SysErr("------- CRM Connection Closed --------");	
					CRMLogger.SysErr("******************************[END ACCOUNT FETCHING REQUEST FROM GUI]************************************");
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return statusMsg;
	}
	
public static String refreshAccountBCPFromCRM(String crmAcctNo){
	
	int insertStatus=-1;
	Connection crmcon=null;		
	String crmAccountNo="";
	int length=0;
	int accountSyncErrCode=0;
	String statusMsg="";
	String  statusBCP="";
	try{
		
		if("Y".equalsIgnoreCase(Utility.getAppConfigValue("ISFETCHACCOUNTFROMGUI"))){
			
			if(crmAcctNo !=null && (!"".equalsIgnoreCase(crmAcctNo))){
				crmAccountNo=crmAcctNo.trim();
				length=crmAccountNo.length();	
			}						
			if(length > 7){
				DBConnectionRetriever con = new DBConnectionRetriever();
				CRMLogger.SysErr("******************************[START ACCOUNT SYNC REQUEST FROM GUI]************************************");
				CRMLogger.SysErr("----- Connecting with CRM Database .....");
				crmcon= con.getCRMConnection();
			
					if(crmcon!=null)
					{
						CRMLogger.SysErr("----- Connected ------");
						int isAccountinIb2b=TransactionBatch.findAccountAlreadyInIB2B(crmAccountNo);
						if(isAccountinIb2b > 0){
							
							CRMLogger.SysErr("Account Updation Started for CRM AccountNo => "+crmAccountNo);
							statusMsg=TransactionBatch.fetchAccountFromCRM(crmcon,1,"update",crmAccountNo);
							CRMLogger.SysErr("Account Updation Completed.....");
							insertStatus=accountSyncErrCode;
							
							if("1".equalsIgnoreCase(statusMsg)){
								CRMLogger.SysErr("BCP Address Inserting Start .....");
								statusBCP=TransactionBatch.fetchBCPAddressFromCRM(String.valueOf(isAccountinIb2b),1,AppConstants.FETCH_LEVEL_GUI);
								CRMLogger.SysErr("BCP Address Inserting Completed.....");																			
						
								CRMLogger.SysErr("BCP Address Updation Start .....");
								statusBCP=TransactionBatch.fetchBCPAddressFromCRM(String.valueOf(isAccountinIb2b),2,AppConstants.FETCH_LEVEL_GUI);
								CRMLogger.SysErr("BCP Address Updation Completed.....");
								
								CRMLogger.SysErr("DISPATCH Address Inserting Start .....");
								statusBCP=TransactionBatch.fetchDispatchAddressFromCRM(String.valueOf(isAccountinIb2b),1);
								CRMLogger.SysErr("DISPATCH Address Inserting Completed.....");
																														
								CRMLogger.SysErr("DISPATCH Address Updation Start .....");
								statusBCP=TransactionBatch.fetchDispatchAddressFromCRM(String.valueOf(isAccountinIb2b),2);
								CRMLogger.SysErr("DISPATCH Address Updation Completed.....");
							}
							
						}else if(isAccountinIb2b == 0){
							
							CRMLogger.SysErr("Account Inserting Started for CRM AccountNo => "+crmAccountNo);
							statusMsg=TransactionBatch.fetchAccountFromCRM(crmcon,1,"inserting",crmAccountNo);
							CRMLogger.SysErr("Account Inserting Completed.....");
							insertStatus=accountSyncErrCode;
							if("1".equalsIgnoreCase(statusMsg)){
								int accountid=TransactionBatch.findAccountAlreadyInIB2B(crmAccountNo);
								CRMLogger.SysErr("BCP Address Inserting Start .....");
								statusBCP=TransactionBatch.fetchBCPAddressFromCRM(String.valueOf(accountid),1,"GUI");
								CRMLogger.SysErr("BCP Address Inserting Completed.....");																			
						
								CRMLogger.SysErr("BCP Address Updation Start .....");
								statusBCP=TransactionBatch.fetchBCPAddressFromCRM(String.valueOf(accountid),2,"GUI");
								CRMLogger.SysErr("BCP Address Updation Completed.....");
								
								CRMLogger.SysErr("DISPATCH Address Inserting Start .....");
								statusBCP=TransactionBatch.fetchDispatchAddressFromCRM(String.valueOf(accountid),1);
								CRMLogger.SysErr("DISPATCH Address Inserting Completed.....");
																														
								CRMLogger.SysErr("DISPATCH Address Updation Start .....");
								statusBCP=TransactionBatch.fetchDispatchAddressFromCRM(String.valueOf(accountid),2);
								CRMLogger.SysErr("DISPATCH Address Updation Completed.....");
							}
							
						}else{
							insertStatus=isAccountinIb2b;
							statusMsg="Error[-1]:Invalid Account!!";
						}
											
					}else{
						statusMsg="Error[-2]: Failed to Connect with CRM Database";
						CRMLogger.SysErr("------Error: Failed to Connect with CRM Database ---------");
						insertStatus=-2;
					}
			}else{				
				statusMsg="Error[-1]:Invalid Account!!";
				CRMLogger.SysErr("********Invalid Account["+insertStatus+"] ******** ");
			}
		}else{
		   statusMsg="Error[-40]:Account Fetching Switch off";
			CRMLogger.SysErr("------ Account Fetching Switch off ------");
			insertStatus=-40;
		}
		
	}catch(Exception exp){
		insertStatus=-2222;
		statusMsg="Error[-2222]:Some error occurred in method refreshAccountBCPFromCRM()";
		CRMLogger.SysErr("------- Error: "+exp.getMessage());	
		exp.printStackTrace();
	}finally{
		try{
			if(crmcon !=null){
				crmcon.close();
				CRMLogger.SysErr("------- CRM Connection Closed --------");	
				CRMLogger.SysErr("******************************[END ACCOUNT SYNC REQUEST FROM GUI]************************************");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	return statusMsg;
}

public static String fetchingAccountBCPFromCRMForCLEP(String crmAcctNo){
	
	int insertStatus=-1;
	int isAccountAlreadyFound=-1;
	Connection crmcon=null;				
	String crmAccountNo="";
	int length=0;
	String statusMsg="";
	String statusMsgBCP="";
	try{
		
		
			if(crmAcctNo !=null && (!"".equalsIgnoreCase(crmAcctNo))){
				crmAccountNo=crmAcctNo.trim();
				length=crmAccountNo.length();	
			}							
			if(length > 7){
				
				isAccountAlreadyFound=TransactionBatch.findAccountAlreadyInIB2BCLEP(crmAcctNo);
				if(isAccountAlreadyFound > 0){
					DBConnectionRetriever con = new DBConnectionRetriever();
					CLEPUtility.SysErr("******************************[START ACCOUNT UPDATE FOR CLEP]************************************");
					CLEPUtility.SysErr("----- Connecting with CRM Database .....");
					crmcon= con.getCRMConnection();
					
					if(crmcon!=null)
					{
						CLEPUtility.SysErr("----- Connected ------");
						CLEPUtility.SysErr("Account Updating Started for CRM AccountNo => "+crmAccountNo);
						statusMsg=TransactionBatch.fetchAccountFromCRMForCLEP(crmcon,1,"update",crmAccountNo);
						CLEPUtility.SysErr("Account Updating Completed.....");
						
						if("1".equalsIgnoreCase(statusMsg)){
							CLEPUtility.SysErr("BCP Address Inserting Start .....");
							statusMsgBCP=TransactionBatch.fetchBCPAddressFromCRM(crmAccountNo,2,AppConstants.FETCH_LEVEL_CLEP);
							CLEPUtility.SysErr("BCP Address Inserting Completed.....");	
							
							CLEPUtility.SysErr("BCP Address Updating Start .....");					
							statusMsgBCP=TransactionBatch.fetchBCPAddressFromCRM(crmAccountNo,1,AppConstants.FETCH_LEVEL_CLEP);
							CLEPUtility.SysErr("BCP Address Updating Completed.....");
							
							if("1".equalsIgnoreCase(statusMsgBCP)){								
								statusMsg="1";
							}else{
								statusMsg=statusMsgBCP+" of requested AccountId["+crmAccountNo+"]";
							}
						}
						
					}else{
						statusMsg="Error[-2]: Failed to Connect with CRM Database";
						CLEPUtility.SysErr("------Error[-2]: Failed to Connect with CRM Database ---------");
						insertStatus=-2;
					}				
				}else{
					DBConnectionRetriever con = new DBConnectionRetriever();
					CLEPUtility.SysErr("******************************[START ACCOUNT FETCH FOR CLEP]************************************");
					CLEPUtility.SysErr("----- Connecting with CRM Database .....");
					crmcon= con.getCRMConnection();
					
						if(crmcon!=null)
						{
							CLEPUtility.SysErr("----- Connected ------");
							CLEPUtility.SysErr("Account Inserting Started for CRM AccountNo => "+crmAccountNo);
							statusMsg=TransactionBatch.fetchAccountFromCRMForCLEP(crmcon,1,"inserting",crmAccountNo);
							CLEPUtility.SysErr("Account Inserting Completed.....");	
							
							if("1".equalsIgnoreCase(statusMsg)){
								CLEPUtility.SysErr("BCP Address Inserting Start .....");
								statusMsgBCP=TransactionBatch.fetchBCPAddressFromCRM(crmAccountNo,1,"CLEP");
								CLEPUtility.SysErr("BCP Address Inserting Completed.....");	
					
								CLEPUtility.SysErr("DISPATCH Address Inserting Start .....");
								statusMsg=TransactionBatch.fetchDispatchAddressFromCRM(crmAccountNo,1);
								CLEPUtility.SysErr("DISPATCH Address Inserting Completed.....");
								
								if("1".equalsIgnoreCase(statusMsgBCP)){								
									statusMsg="1";
								}else{
									statusMsg=statusMsgBCP+" of requested AccountId["+crmAccountNo+"]";
								}
							}		
																												
						}else{
							statusMsg="Error[-2]: Failed to Connect with CRM Database";
							CLEPUtility.SysErr("------Error[-2]: Failed to Connect with CRM Database ---------");
							insertStatus=-2;
						}
				}
			}else{					
				statusMsg="Error[-1]: Invalid AccountId["+crmAcctNo+"]";
				CLEPUtility.SysErr("********Invalid Account["+insertStatus+"] ******** ");
			}		
		
	}catch(Exception exp){
		insertStatus=-2222;
		CLEPUtility.SysErr("------- Error: "+exp.getMessage());	
		exp.printStackTrace();
	}finally{
		try{
			if(crmcon !=null){
				crmcon.close();
				CLEPUtility.SysErr("------- CRM Connection Closed --------");	
				CLEPUtility.SysErr("******************************[END ACCOUNT FETCH]************************************");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	return statusMsg;
}

	public static void main(String args[]) throws Exception {
		System.out.println("Starting Procesing");
		int status=-1;
		/*status=FetchAccountFromCRM.updateAccountBCPFromCRM("19614344");
		if(status > 0){
			System.err.println(" SUCCESS ");
		}else{
			System.err.println(" FAILED ");
		}*/
	}
	
	/*   	********************************************************************************
	 *		Function Name:- InsertUpdateProductDDValuesInIOMS
	 *		Created By:-    Saurabh Singh
	 * 		Created On:-    02-11-2011
	 * 		Purpose:-		To Insert And Update ProductDDValues In IOMS
	 *      ********************************************************************************
	 */ 
	
	public int InsertUpdateProductDDValuesInIOMS(int stage)
	{
				PreparedStatement pstmt = null;
				ResultSet rset = null;
				DBConnectionRetriever con = new DBConnectionRetriever();
				Connection crmcon = con.getCRMConnection();
				
				Connection iomsConn = null;
				CallableStatement csIOMS = null;
				CallableStatement csIOMS1 = null;
				int status=0;
				//String data="Black berry";
				//String data1="MM/DD/YYYY HH:MI:SS AM";
				try 
				{
					System.out.println("Connect with IOMS database");					
					iomsConn = getConnectionObject();
					csIOMS1=iomsConn.prepareCall(strGetInsertUpdateDateFromIOMS);
					csIOMS1.setString(1,"TPRODUCTDDVALUES");
					rset=csIOMS1.executeQuery();
					System.out.println("fetching date from IOMS history table");
					while(rset.next())
					{
						if(stage==1) // For Insertion Date
						{
							transactionDate=rset.getString("INSERTDATE");
						}
						else
						{
							transactionDate=rset.getString("UPDATEDATE");
							
						}
					}
					if(stage==1)
					{
						System.out.println("ddvalues()- query ::" + strGetInsertECRMProductDDValues);
						pstmt = crmcon.prepareStatement(strGetInsertECRMProductDDValues);
					}
					else
					{
						System.out.println("ddvalues()- query ::" + strGetUpdateECRMProductDDValues);
						pstmt = crmcon.prepareStatement(strGetUpdateECRMProductDDValues);
					}
					pstmt.setString(1,transactionDate);
					//pstmt.setString(2,data);
					rset = pstmt.executeQuery();
					ArrayList<ECRMProductDDValuesDto> lstUserInfo = new ArrayList<ECRMProductDDValuesDto>();
					ECRMProductDDValuesDto objDto = null; 
					while (rset.next()) 
					{
						objDto = new ECRMProductDDValuesDto();
						objDto.setProductName((rset.getString("ProductName")));
						objDto.setText(rset.getString("TEXT"));
						objDto.setLookUpCode(rset.getString("lookup_code"));
						objDto.setCreation_Date(rset.getString("CREATION_DATE"));
						objDto.setLast_Update_date(rset.getString("LAST_UPDATE_DATE"));
						objDto.setEnabledFlag(rset.getString("ENABLED_FLAG"));
						lstUserInfo.add(objDto);
					}
														
					System.err.println("Inside Function....");
					if(lstUserInfo.size()>0)
					{
						System.err.println("Product DD Values fetched and stored in Array list");	
						iomsConn.setAutoCommit(false);
						csIOMS = iomsConn.prepareCall(spInsertUpdateECRMProductDDValuesToIOMS);
						
						for (Iterator iter = lstUserInfo.iterator(); iter.hasNext();) 
						{
							ECRMProductDDValuesDto element = (ECRMProductDDValuesDto) iter.next();
							csIOMS.setString(1, element.getText());
							csIOMS.setString(2, element.getLookUpCode());
							csIOMS.setInt(3, stage);
							csIOMS.setString(4, element.getProductName());
							if(element.getEnabledFlag().equalsIgnoreCase("N"))
							{
								csIOMS.setInt(5, 0);
							}else{
								csIOMS.setInt(5, 1);
							}
							csIOMS.registerOutParameter(6, java.sql.Types.INTEGER);						
							csIOMS.registerOutParameter(7, java.sql.Types.VARCHAR);						
							csIOMS.registerOutParameter(8, java.sql.Types.VARCHAR);	
							csIOMS.registerOutParameter(9, java.sql.Types.VARCHAR);	
							csIOMS.execute();						
						}
						if (csIOMS.getInt(6) != 1) 
						{
							iomsConn.commit();
							SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");									
							if(stage==1) // For Insertion 
							{
								TransactionBatch.UpdateIOMSDataTracker("Insert","TPRODUCTDDVALUES",sdf.format(new Date()),sdf.format(new Date()));
							}
							else
							{
								TransactionBatch.UpdateIOMSDataTracker("Update","TPRODUCTDDVALUES",sdf.format(new Date()),sdf.format(new Date()));
							}
							System.err.println("Product DD Values Inserted in IOMS database");
						} 
						else 
						{
							iomsConn.rollback();
							status=1;
						}
					}
				} 
				catch (Exception e) 
				{
					System.out.println("Error in method InsertUpdateProductDDValuesInIOMS()"
							+ e.getStackTrace());
					e.printStackTrace();
					status=1;
				} 
				finally 
				{
					try 
					{
						DbConnection.closeResultset(rset);
						DbConnection.closePreparedStatement(pstmt);
						DbConnection.freeConnection(crmcon);
						DbConnection.freeConnection(iomsConn);
					} 
					catch (Exception e) 
					{
						System.out.println("exeption due to : " + e.getMessage());
					}
				}
				return status;
	}
	public static Connection getConnectionObject() throws Exception {
		Connection conn = null;
		try {
			conn= DbConnection.getConnectionObject();
		} catch (Exception e) {
			e.printStackTrace();
			throw (Exception) e;
		}
		return conn;
	}
	
	//lawkush start
	
	
	public static int refreshOpportunity(Long accountId){
		
		int insertStatus=-1;
		Connection crmcon=null;	
		int length=0;
		int accountSyncErrCode=0;
		int status=-1;
		int statusUpdate=-1;
		String  statusMsg="";
		try{
			
			
					DBConnectionRetriever con = new DBConnectionRetriever();
					CRMLogger.SysErr("******************************[START OPPORTUNITY SYNC REQUEST FROM GUI]************************************");
					CRMLogger.SysErr("----- Connecting with CRM Database .....");
					crmcon= con.getCRMConnection();
				
						if(crmcon!=null)
						{
							CRMLogger.SysErr("----- Connected ------");
						
								CRMLogger.SysErr("Opportunity Insertion Started ......");
								status=TransactionBatch.UpdateECRMQuotesInIOMS(accountId,null);	
								//InsertUpdateOpportunityIdInIOMS
								statusUpdate=TransactionBatch.ModifyECRMQuotesInIOMS(accountId);
								
								
								
								CRMLogger.SysErr("Opportunity Insertion Completed.....");
								
																	
						}else{
							statusMsg="Error[-2]: Failed to Connect with CRM Database";
							CRMLogger.SysErr("------Error: Failed to Connect with CRM Database ---------");
							insertStatus=-2;
							status=0;
							statusUpdate=0;
						}
				if(status==1 && statusUpdate==1)
				{
					status=1;
				}
				else if(status==0 && statusUpdate==0)
				{
					status=-3;
				}
				else
				{
					status=-1;
				}
			
			
		}catch(Exception exp){
			insertStatus=-2222;
			statusMsg="Error[-2222]:Some error occurred in method refreshOpportunity()";
			CRMLogger.SysErr("------- Error: "+exp.getMessage());	
			exp.printStackTrace();
		}finally{
			try{
				if(crmcon !=null){
					crmcon.close();
					CRMLogger.SysErr("------- CRM Connection Closed --------");	
					CRMLogger.SysErr("******************************[END OPPORTUNITY SYNC REQUEST FROM GUI]************************************");
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return status;
	}
	
	//lawkush End
	
}
