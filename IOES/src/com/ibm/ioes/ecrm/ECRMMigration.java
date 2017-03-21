package com.ibm.ioes.ecrm;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.sql.Timestamp;

import com.ibm.ioes.utilities.DbConnection;



public class ECRMMigration {

	public static String spInsertECRMOrderHeadertoIOMS = "{call IOE.ECRM_INSERT_INTO_TPOMASTER(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	
	private static String strGetOrderHeaderFromCRM="select CRM_ORDER_ID,ORDERTYPE,ORDERSOURCE,COALESCE(QUOTENUMBER,'0') AS QUOTENUMBER,CURRENCY,M6_ORDER_STATUS,"
													+ " ORDER_STAGE,ACCOUNT_NO,PON,ORDER_SUBTYPE, ZONEID,PROJECT_MGR_ID,DEMO_TYPE,ORDERDATE,REGIONID, PUBLISH_DATE,"
													+ " CREATION_DATE,CREATED_BY, LAST_UPDATE_DATE,LAST_UPDATED_BY from IBMOE_ORDER_HEADER where CRM_ORDER_ID=544460";
	
	private static String strInsertECRMOrderHeaderAttributestoIOMS="{call IOE.ECRM_INSERT_INTO_TATTRIBUTEMASTER(?,?,?,?,?,?,?,?,?,?)}";
	
	private static String strGetOrderHeaderAttributesFromCRM="select OH_ATTRIBUTE_ID,LABEL_VALUE,CRM_ORDER_ID,CREATION_DATE,CREATED_BY,LAST_UPDATE_DATE,"
															  + " LAST_UPDATED_BY from IBMOE_ORDER_HEADER_ATTRIBUTES where CRM_ORDER_ID=544460";
	
	private static String strGetOrderContactFromCRM="select ORDER_CONTACT_ID, CONTACTTYPE, FORMOFADDRESS, GIVENNAME, FAMILYNAME,"
													+" EMAIL, CELLTELEPHONENR, CRM_ORDER_ID, FAXNR,  CREATION_DATE, CREATED_BY,"  
													+" LAST_UPDATE_DATE, LAST_UPDATED_BY from IBMOE_ORDER_CONTACT where CRM_ORDER_ID=544460";
	
	private static String spInsertOrderContactToIOMS="{CALL IOE.ECRM_INSERT_INTO_TPOCONTACT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		
	private static String strGetContactAddressFromCRM="select IBMOE_CONTACT_ADDRESS.CONTACT_ADDRESS_ID, IBMOE_CONTACT_ADDRESS.ADDRESS1, IBMOE_CONTACT_ADDRESS.ADDRESS2," 
													+" IBMOE_CONTACT_ADDRESS.ADDRESS3, IBMOE_CONTACT_ADDRESS.PIN,IBMOE_ORDER_CONTACT.CRM_ORDER_ID, IBMOE_CONTACT_ADDRESS.ORDER_CONTACT_ID, IBMOE_CONTACT_ADDRESS.CITY_ID,"
													+" IBMOE_CONTACT_ADDRESS.STATE_ID, IBMOE_CONTACT_ADDRESS.COUNTRY_ID, IBMOE_CONTACT_ADDRESS.CREATION_DATE, IBMOE_CONTACT_ADDRESS.CREATED_BY,IBMOE_CONTACT_ADDRESS.LAST_UPDATE_DATE,"
													+" IBMOE_CONTACT_ADDRESS.LAST_UPDATED_BY"
													+" from IBMOE_CONTACT_ADDRESS  JOIN IBMOE_ORDER_CONTACT"
													+" ON IBMOE_CONTACT_ADDRESS.ORDER_CONTACT_NO=IBMOE_ORDER_CONTACT.ORDER_CONTACT_NO" 
													+" where IBMOE_ORDER_CONTACT.CRM_ORDER_ID=544460";
	
	private static String spInsertContactAddressToIOMS="{CALL IOE.ECRM_INSERT_INTO_TPOADDRESS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	
	private static String strGetPODetailsFromCRM="select PO_ID, CRM_ORDER_ID, LEGAL_ENTITY_CODE, TOT_PO_AMT, CONTRACT_PERIOD_MNTHS, CUST_PO_RECEIVE_DATE,"
												 +" DEFAULT_FLAG, PO_ISSUING_PERSON_NAME, PO_PAYMENT_TERMS,  PO_ISSUING_PERSON_EMAIL, DEMO_CONTRACT_PERIOD,"
												 +" CONTRACT_START_DATE, CONTRACT_END_DATE,  CUST_PO_DATE, CUST_PO_NUMBER, CUST_PO_DATE, CREATION_DATE," 
												 +" CREATED_BY, LAST_UPDATE_DATE, LAST_UPDATED_BY"
												 +" from IBMOE_ORDER_PO_DTLS where CRM_ORDER_ID=544460";
	
	private static String spInsertPODetailsToIOMS="{CALL IOE.ECRM_INSERT_INTO_TPODETAILS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	
	private static String strGetOrderServiceFromCRM="select SERVICE.SERVICE_NO, SERVICE.M6_PRODUCT_ID, SERVICE.EFFECTIVE_START_DATE," 
													+ " SERVICE.CUST_LOGICAL_SI_ID, SERVICE.EFFECTIVE_END_DATE, SERVICE.PROVISIONING_PLAN_ID, SERVICE.REMARKS," 
													+ " SERVICE.CRM_ORDER_ID, MAX(ORDER_STATUS.M6_ORDER_ID) AS M6_ORDER_ID, ORDER_STATUS.PRE_M6_ORDER_ID, MAX(ORDER_STATUS.EVENT_TYPE_ID) AS EVENT_TYPE_ID,"
													+ " ORDER_STATUS.CANCEL_REASON,SERVICE.SERVICE_ORDER_TYPE, SERVICE.LOGICAL_SI_NUMBER, SERVICE.CUST_LOGICAL_SI_NO, SERVICE.CREATION_DATE,"
													+ " SERVICE.LAST_UPDATE_DATE, SERVICE.CREATED_BY, SERVICE.CREATION_DATE, SERVICE.LAST_UPDATE_DATE,"
													+ " SERVICE.LAST_UPDATED_BY , SERVICE.PROCESS_ID,SERVICE.NETWORK_STATE,SERVICE.ATTRIBUTE1"
													+ " from IBMOE_ORDER_SERVICE_DETAILS SERVICE LEFT JOIN IBMOE_ORDER_STATUS ORDER_STATUS "
													+ " ON ORDER_STATUS.SERVICE_LIST_ID=SERVICE.SERVICE_LIST_ID "
													+ " LEFT JOIN IBMOE_PRODUCT_MASTER_MAP PRODUCT_MSTR ON PRODUCT_MSTR.M6_PRODUCT_ID=SERVICE.M6_PRODUCT_ID "
													+ " where  SERVICE.CRM_ORDER_ID=544460 " +
															"GROUP BY SERVICE.SERVICE_NO, SERVICE.M6_PRODUCT_ID, SERVICE.EFFECTIVE_START_DATE, SERVICE.CUST_LOGICAL_SI_ID, " +
															"SERVICE.EFFECTIVE_END_DATE, SERVICE.PROVISIONING_PLAN_ID, SERVICE.REMARKS, SERVICE.CRM_ORDER_ID, " +
															" ORDER_STATUS.PRE_M6_ORDER_ID, ORDER_STATUS.CANCEL_REASON,SERVICE.SERVICE_ORDER_TYPE, " +
															"SERVICE.LOGICAL_SI_NUMBER, SERVICE.CUST_LOGICAL_SI_NO, SERVICE.CREATION_DATE, SERVICE.LAST_UPDATE_DATE, SERVICE.CREATED_BY, " +
															"SERVICE.CREATION_DATE, SERVICE.LAST_UPDATE_DATE, SERVICE.LAST_UPDATED_BY , SERVICE.PROCESS_ID,SERVICE.NETWORK_STATE,SERVICE.ATTRIBUTE1";
	
	private static String spInsertOrderServiceToIOMS="{CALL IOE.ECRM_INSERT_INTO_TPOSERVICEMASTER(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	
	private static String strGetServiceAttributesFromCRM="select IBMOE_CONFIG_PARAM.PRODUCT_ATTRIBUTE_ID,IBMOE_CONFIG_PARAM.M6_LABEL_VALUE,IBMOE_ORDER_LINE.SERVICE_NO,"
														 +" IBMOE_CONFIG_PARAM.CREATION_DATE,IBMOE_CONFIG_PARAM.CREATED_BY,IBMOE_CONFIG_PARAM.LAST_UPDATE_DATE,"
														 +" IBMOE_CONFIG_PARAM.LAST_UPDATED_BY from  APPS.IBMOE_CONFIG_PARAM IBMOE_CONFIG_PARAM RIGHT OUTER"
														 +" JOIN xxibm.IBMOE_ORDER_LINE IBMOE_ORDER_LINE on IBMOE_ORDER_LINE.ORDER_LINE_NO=IBMOE_CONFIG_PARAM.ORDER_LINE_NO"
														 +" where IBMOE_ORDER_LINE.CHILD_SERVICE_KEY in (1,33,42,59,88,133,138,142,149,1230) and IBMOE_CONFIG_PARAM.CRM_ORDER_ID=544460";
	
	private static String spInsertServiceAttributesToIOMS="{CALL IOE.ECRM_INSERT_INTO_TPRODUCTATTVALUE(?,?,?,?,?,?,?,?,?,?)}";
	
	private static String strGetOrderLineFromCRM="select IBMOE_ORDER_LINE.ORDER_LINE_ID,IBMOE_ORDER_LINE.SERVICE_LIST_ID,IBMOE_ORDER_LINE.M6_PRODUCT_ID,"
												+ "IBMOE_CIRCUIT_DETAIL.LOC_DATE,IBMOE_CIRCUIT_DETAIL.LOC_NUMBER, IBMOE_CIRCUIT_DETAIL.BILLING_TRIG_DATE,"
												+ "	IBMOE_ORDER_LINE.PARENT_SERVICE_KEY,IBMOE_CIRCUIT_DETAIL.BILLING_TRIG_FLAG,IBMOE_BILLING_ACCOUNTS.CHILD_ACCOUNT_NUMBER,"
												+ "	IBMOE_ORDER_LINE.M6CHILDSERKEY,IBMOE_ORDER_LINE.M6PARENTSERKEY,IBMOE_ORDER_LINE.CREATION_DATE,IBMOE_ORDER_LINE.LAST_UPDATE_DATE,"
												+ "	IBMOE_CIRCUIT_DETAIL.CIRCUIT_ID, IBMOE_ORDER_LINE.PRI_LOC_ID,IBMOE_CIRCUIT_DETAIL.HUBLOCATION,IBMOE_CIRCUIT_DETAIL.UOM,"
												+ "	IBMOE_CIRCUIT_DETAIL.LOC_DATE,IBMOE_CIRCUIT_DETAIL.BILL_TRG_CREATE_DATE,IBMOE_CIRCUIT_DETAIL.CREATION_DATE,IBMOE_CIRCUIT_DETAIL.CREATED_BY,"
												+ "	IBMOE_CIRCUIT_DETAIL.LAST_UPDATE_DATE,IBMOE_CIRCUIT_DETAIL.LAST_UPDATED_BY,IBMOE_CIRCUIT_DETAIL.CHALLEN_NO,"
												+ "	IBMOE_CIRCUIT_DETAIL.CHALLEN_DATE,IBMOE_ORDER_LINE.M6_PRODUCT_ID,IBMOE_ORDER_LINE.NETWORK_STAT"
												+ "	from XXIBM.IBMOE_ORDER_LINE IBMOE_ORDER_LINE LEFT JOIN XXIBM.IBMOE_CIRCUIT_DETAIL IBMOE_CIRCUIT_DETAIL"
												+ "	on IBMOE_ORDER_LINE.ORDER_LINE_ID = IBMOE_CIRCUIT_DETAIL.ORDER_LINE_ID"
												+ "	LEFT JOIN IBMOE_BILLING_ACCOUNTS IBMOE_BILLING_ACCOUNTS on IBMOE_BILLING_ACCOUNTS.CHILD_ID=IBMOE_ORDER_LINE.CHILD_ID "
												+ "	where IBMOE_ORDER_LINE.CRM_ORDER_ID in (544460) "
												+ "	order by ORDER_LINE_ID ";
	
	private static String spInsertOrderLineToIOMS="{CALL IOE.ECRM_INSERT_INTO_TPOSERVICEDETAILS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	
	private static String strGetLineAttributesFromCRM="select IBMOE_CONFIG_PARAM.PRODUCT_ATTRIBUTE_ID,IBMOE_CONFIG_PARAM.M6_LABEL_VALUE,IBMOE_CONFIG_PARAM.ORDER_LINE_NO,"
													  +" IBMOE_CONFIG_PARAM.CREATION_DATE,IBMOE_CONFIG_PARAM.CREATED_BY,IBMOE_CONFIG_PARAM.LAST_UPDATE_DATE,"
													  +" IBMOE_CONFIG_PARAM.LAST_UPDATED_BY from  APPS.IBMOE_CONFIG_PARAM IBMOE_CONFIG_PARAM"
													  +" JOIN xxibm.IBMOE_ORDER_LINE IBMOE_ORDER_LINE on IBMOE_ORDER_LINE.ORDER_LINE_NO=IBMOE_CONFIG_PARAM.ORDER_LINE_NO"
													  +" where IBMOE_ORDER_LINE.CHILD_SERVICE_KEY not in (1,33,42,59,88,133,138,142,149,1230) and HIDDEN_FLAG='N' and IBMOE_CONFIG_PARAM.CRM_ORDER_ID in (544460)";
	
	private static String spInsertLineAttributesToIOMS="{CALL IOE.ECRM_INSERT_INTO_TPRODUCTLINEATTVALUE(?,?,?,?,?,?,?,?,?,?)}";
		
	private static String strGetChargesHeaderFromCRM="select CHARGE_HDR_ID,PO_ID,ACCOUNT_NUMBER,CREDIT_PERIOD,LEGAL_ENTITY_CODE,BILLING_MODE,"
													+" BILL_FORMAT, LICENCE_COMPANY,TAXATION,BILLING_LEVEL,COMMITMENT_PERIOD,PENELTY_CLAUSE,ORDER_LINE_ID,"
													+" BILL_TYPE, BILLING_ADDRESS_ID, BILLING_LEVEL_NUMBER, CREATION_DATE, CREATED_BY, LAST_UPDATE_DATE,"
													+" LAST_UPDATED_BY, NOTICE_PERIOD from XXIBM.IBMOE_CHARGES_HDR where CRM_ORDER_ID=544460";
	
	private static String spInsertChargesHeaderToIOMS="{CALL IOE.ECRM_INSERT_INTO_TBILLING_INFO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	
	private static String strGetLocationHeaderFromCRM="select IBMOE_ORDER_LINE.PRI_LOC_TYP,IBMOE_ORDER_LINE.PRI_LOC_ID, IBMOE_ORDER_LINE.SEC_LOC_TYP, IBMOE_ORDER_LINE.SEC_LOC_ID,"
													+" IBMOE_ORDER_HEADER.ACCOUNT_NO,IBMOE_ORDER_LINE.ORDER_LINE_ID, IBMOE_ORDER_LINE.CREATION_DATE, IBMOE_ORDER_LINE.CREATED_BY,"
													+" IBMOE_ORDER_LINE.LAST_UPDATE_DATE, IBMOE_ORDER_LINE.LAST_UPDATED_BY  "
													+" from XXIBM.IBMOE_ORDER_LINE IBMOE_ORDER_LINE JOIN IBMOE_ORDER_HEADER IBMOE_ORDER_HEADER"
													+" on IBMOE_ORDER_HEADER.CRM_ORDER_ID=IBMOE_ORDER_LINE.CRM_ORDER_ID where "
													+" IBMOE_ORDER_LINE.PRI_LOC_TYP is not null and IBMOE_ORDER_LINE.SEC_LOC_TYP is not null AND IBMOE_ORDER_LINE.CRM_ORDER_ID=544460";
	
	private static String spInsertLocationHeaderToIOMS="{CALL IOE.ECRM_INSERT_INTO_TLOCATION_INFO(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	
	private static String strGetHardwareHeaderFromCRM="select STORE,HARDWARE_TYPE,FORM_C_AVAILABLE,TYPE_OF_SALE,NATURE_OF_SALE,DISPATCH_ADDRESS_ID,"
													+" ACCOUNT_NUMBER,ORDER_LINE_ID,WARRANTY_DATE_LOGIC,WARRANTY_END_DATE_LOGIC,WARRANTY_PERIOD_MONTHS,"
													+" WARRANTY_START_DATE,WARRANTY_END_DATE,PRINCIPAL_AMT,INTREST_RATE,CREATION_DATE,LAST_UPDATE_DATE,"
													+" CREATION_DATE,CREATED_BY,LAST_UPDATE_DATE,LAST_UPDATED_BY from XXIBM.IBMOE_CHARGES_HDR "
													+" where  store is not null AND CRM_ORDER_ID =544460";
	
	private static String spInsertHardwareHeaderToIOMS="{CALL IOE.ECRM_INSERT_INTO_THARDWARE_INFO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	
	private static String strGetChargesFromCRM="select PK_CHARGES_ID,ORDER_LINE_ID, CHARGE_TYPE,CONTRACT_PERIOD_MNTHS,TOTAL_AMOUNT,"
											+" FREQUENCY,CHARGE_VALUE, START_DATE_LOGIC,END_DATE_LOGIC,START_DATE_DAYS, START_DATE_MONTHS,"
											+" END_DATE_DAYS, END_DATE_MONTHS,CREATION_DATE,LAST_UPDATE_DATE, FX_STATUS, ANNOTATION,"
											+" CREATION_DATE, CREATED_BY, LAST_UPDATE_DATE, LAST_UPDATED_BY "
											+"  from XXIBM.IBMOE_CHARGES where CRM_ORDER_ID =544460";
	
	private static String spInsertChargesToIOMS="{CALL IOE.ECRM_INSERT_INTO_TCHARGES_INFO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	
	private static String spInsertApprovalWorkFlowFromCRM="SELECT TASK_ID,TASK_NAME,OWNER_ID,TASK_STATUS_ID,PLANNED_START_DATE, ACTUAL_END_DATE," +
															" SOURCE_OBJECT_ID,CREATED_BY,CREATION_DATE, REASON_CODE FROM JTF_TASKS_V " +
															" WHERE SOURCE_OBJECT_TYPE_CODE = 'XXIBM_ORDER' AND SOURCE_OBJECT_ID in (544460)";
	
	private static String spGetApprovalWorkFlowTOIOMS="{CALL IOE.ECRM_INSERT_INTO_TPOWORKFLOWTASK(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	
	private static String strGetApprovalDetailsFromCRM="SELECT TASK_ID,TASK_STATUS_ID,OWNER_ID,PLANNED_START_DATE,SOURCE_OBJECT_ID,REASON_CODE,"
													 +" ACTUAL_END_DATE,CREATED_BY"
													 +" FROM JTF_TASKS_V"
													 +" WHERE SOURCE_OBJECT_TYPE_CODE = 'XXIBM_ORDER' AND SOURCE_OBJECT_ID in (544460)";
	
	private static String strInsertECRMApprovalstoIOMS="{CALL IOE.ECRM_INSERT_INTO_TTASKACTIONHISTORY(?,?,?,?,?,?,?,?,?,?,?)}";
	
	private static String strGetECRMM6TaskHistoryFromIOMS="select distinct CRM_ORDER_ID,SERVICE_LIST_ID,EVENT_TYPE_ID,CANCEL_REASON,CREATION_DATE from IBMOE_ORDER_STATUS where CRM_ORDER_ID in (544460)";
	
	private static String strInsertECRMM6TaskHistoryToIOMS="{CALL IOE.ECRM_INSERT_INTO_TM6_RESPONSE_HISTORY(?,?,?,?,?,?,?,?)}";

	private static String strGetECRMFXAccountCreateFromIOMS="select '101',LEGAL_ENTITY,'3', CRM_ORDER_ID from IBMOE_CRM_FX_INTEGRATION where CRM_ORDER_ID in (544460)";
	
	private static String strInsertECRMFXAccountToCreate="{CALL IOE.ECRM_INSERT_INTO_TFX_ACCOUNTCREATE(?,?,?,?,?)}";
	
	private static String strGetECRMFXServiceCreateFromIOMS="select SERVICE_INTERNAL_ID,SERVICE_INTER_ID_RESET,'3',UPDATED_ON,CREATED_ON,CRM_ORDER_ID,"
														 +"	ORDER_LINE_ID,'101',SERVICE_LIST_ID,LEGAL_ENTITY,FX_TOKEN_NO,SERVICE_VIEW_ID from IBMOE_CRM_FX_INTEGRATION"
														 +" where CRM_ORDER_ID in (544460)";
	
	private static String strInsertECRMFXServiceCreateToIOMS="{CALL IOE.ECRM_INSERT_INTO_TFX_SERVICECREATE(?,?,?,?,?,?,?,?,?,?,?)}";
	
	private static String strGetECRMFXRCCreateFromIOMS="select CRM_ORDER_ID,ORDER_LINE_ID,PK_CHARGES_ID,SERVICE_VIEW_ID,LEGAL_ENTITY,UPDATED_ON,CREATED_ON " +
													   " from IBMOE_CRM_FX_INTEGRATION where PK_CHARGES_ID is not null and CHARGE_TYPE=30 and CRM_ORDER_ID in (544460)";
	
	private static String strInsertECRMFXRCCreateToIOMS="{CALL IOE.ECRM_INSERT_INTO_TFX_RC_CREATE(?,?,?,?,?,?,?,?,?,?)}";;
	
	private static String strGetECRMFXNRCCreateFromIOMS="select ORDER_LINE_ID,PK_CHARGES_ID,SERVICE_VIEW_ID,LEGAL_ENTITY,UPDATED_ON,CREATED_ON from IBMOE_CRM_FX_INTEGRATION " +
														" where PK_CHARGES_ID is not null and CHARGE_TYPE=20 and CRM_ORDER_ID in (544460)";
	
	private static String strInsertECRMFXNRCCreateToIOMS="{CALL IOE.ECRM_INSERT_INTO_TFX_NRC_CREATE(?,?,?,?,?,?,?,?,?)}";;
	
	
	ArrayList OrderNoList = new ArrayList();
	
	
	public static void InsertECRMOrderHeadertoIOMS()//Insert Into TPOMASTER
	{
				PreparedStatement pstmt = null;
				ResultSet rset = null;
				DBConnectionRetriever con = new DBConnectionRetriever();
				Connection crmcon = con.getCRMConnection();
				
				Connection iomsConn = null;
				CallableStatement csIOMS = null;
				boolean isInserted = false;
				try {
					System.out.println("Connect with IOMS database");
					iomsConn = getConnectionObject();
					//csIOMS=iomsConn.prepareCall(strGetInsertDateFromIOMS);
					//tableName="TM_ACCOUNTROLEDETAILS";
					//csIOMS.setString(1, tableName);
					//rset=csIOMS.executeQuery();
					//System.out.println("fetching insert date from IOMS history table");
					//while(rset.next())
					//{
					//	insertDate=rset.getString("INSERTDATE");
					//}
					System.out.println("query ::" + strGetOrderHeaderFromCRM);
					pstmt = crmcon.prepareStatement(strGetOrderHeaderFromCRM);
					//pstmt.setString(1,insertDate);
					rset = pstmt.executeQuery();
					int i = 0;
					ArrayList lstOrder = new ArrayList();
					ECRMMigrationDto objDto = null; 
					while (rset.next()) 
					{
						objDto = new ECRMMigrationDto();
						objDto.setCrmOrderId(rset.getString("CRM_ORDER_ID"));
						objDto.setOrderType(rset.getString("ORDERTYPE"));
						objDto.setOrderSource(rset.getString("ORDERSOURCE"));
						objDto.setQuoteNumber(rset.getString("QUOTENUMBER"));
						objDto.setCurrency(rset.getString("CURRENCY"));
						objDto.setM6OrderStatus(rset.getString("M6_ORDER_STATUS"));
						objDto.setOrderStage(rset.getString("ORDER_STAGE"));
						objDto.setCustAccountId(rset.getString("ACCOUNT_NO"));
						objDto.setPon(rset.getString("PON"));
						objDto.setOrderSubType(rset.getString("ORDER_SUBTYPE"));
						objDto.setZoneId(rset.getString("ZONEID"));
						objDto.setProjectManagerId(rset.getString("PROJECT_MGR_ID"));
						objDto.setDemoType(rset.getString("DEMO_TYPE"));
						//objDto.setIsRegularised(rset.getInt(columnName));
						objDto.setOrderDate1(rset.getString("ORDERDATE"));
						objDto.setRegionId(rset.getString("REGIONID"));
						objDto.setPublishDate1(rset.getString("PUBLISH_DATE"));
						objDto.setCreatedBy(rset.getString("CREATED_BY"));
						objDto.setCreationDate1(rset.getString("CREATION_DATE"));
						objDto.setLastUpdatedBy(rset.getString("LAST_UPDATED_BY"));
						objDto.setLastUpdatedDate1(rset.getString("LAST_UPDATE_DATE"));
						
						
						lstOrder.add(objDto);
						
					}
					
					System.err.println("Inside Function....");
					if(lstOrder.size()>0)
					{
					System.err.println("Employee Fetched And Stored In ArrayList");	
					iomsConn.setAutoCommit(false);
					csIOMS = iomsConn.prepareCall(spInsertECRMOrderHeadertoIOMS);
								
					
					for (Iterator iter = lstOrder.iterator(); iter.hasNext();) 
					{
						ECRMMigrationDto element = (ECRMMigrationDto) iter.next();
												
						csIOMS.setString(1, element.getCrmOrderId());
						csIOMS.setString(2, element.getOrderType());
						csIOMS.setString(3, element.getOrderSource());
						csIOMS.setString(4, element.getQuoteNumber());
						csIOMS.setString(5, element.getCurrency());						
						csIOMS.setString(6, element.getM6OrderStatus());
						csIOMS.setString(7,element.getOrderStage());
						csIOMS.setString(8,element.getCustAccountId());
						csIOMS.setString(9,element.getPon());
						csIOMS.setString(10,element.getOrderSubType());
						csIOMS.setString(11, element.getZoneId());
						csIOMS.setString(12,element.getProjectManagerId());
						csIOMS.setString(13, element.getDemoType());
						csIOMS.setString(14,element.getOrderDate1());
						csIOMS.setString(15,element.getRegionId());
						csIOMS.setString(16, element.getPublishDate1());
						csIOMS.setString(17, element.getCreationDate1());
						csIOMS.setString(18, element.getCreatedBy());
						csIOMS.setString(19, element.getLastUpdatedDate1());
						csIOMS.setString(20, element.getLastUpdatedBy());
						csIOMS.setInt(21, 0);
						csIOMS.setInt(22, 0);
						csIOMS.setString(23, "");
						csIOMS.execute();
						
					}
							 

								if (csIOMS.getInt(22) == 0) {
									iomsConn.commit();
									//SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");									
									//UpdateIOMSDataTracker("insert","TM_ACCOUNTROLEDETAILS",sdf.format(new Date()),sdf.format(new Date()));
									isInserted = true;
									System.err.println("Order Updated....");
								} else {
									iomsConn.rollback();
									System.err.println("Order Not Updated....");
									System.err.println(csIOMS.getString(22));
									isInserted = false;
								}
								
					}
					
						// Long ServiceSegment = null;
					System.out.println("Order Data is Inserted");
				} catch (Exception e) {
					System.out.println("Order Data is Not Inserted"
							+ e.getStackTrace());
					e.printStackTrace();
				} finally {
					try {
						DbConnection.closeResultset(rset);
						DbConnection.closeCallableStatement(csIOMS);
						DbConnection.freeConnection(iomsConn);
					} catch (Exception e) {
						System.out.println("exeption due to : " + e.getMessage());
					}
				}
	}
	
	public static void InsertECRMHeaderAttributesInfotoIOMS()//INSERT INTO IOE.TATTRIBUTEVALUES
	{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		DBConnectionRetriever con = new DBConnectionRetriever();
		Connection crmcon = con.getCRMConnection();
		
		Connection iomsConn = null;
		CallableStatement csIOMS = null;
		boolean isInserted = false;
		try {
			System.out.println("Connect with IOMS database");
			iomsConn = getConnectionObject();
			System.out.println("query ::" + strGetOrderHeaderAttributesFromCRM);
			pstmt = crmcon.prepareStatement(strGetOrderHeaderAttributesFromCRM);
			//pstmt.setString(1,insertDate);
			rset = pstmt.executeQuery();
			int i = 0;
			ArrayList lstOrderAttributes = new ArrayList();
			ECRMMigrationDto objDto = null; 
			while (rset.next()) 
			{
				objDto = new ECRMMigrationDto();
				objDto.setLabelName(rset.getString("OH_ATTRIBUTE_ID"));
				objDto.setLabelValue(rset.getString("LABEL_VALUE"));
				objDto.setCrmOrderId(rset.getString("CRM_ORDER_ID"));
				objDto.setCreatedBy(rset.getString("CREATED_BY"));
				objDto.setCreationDate(rset.getTimestamp("CREATION_DATE"));
				objDto.setLastUpdatedBy(rset.getString("LAST_UPDATED_BY"));
				objDto.setLastUpdatedDate(rset.getTimestamp("LAST_UPDATE_DATE"));
				lstOrderAttributes.add(objDto);
				
			}
			
			System.err.println("Inside Function....");
			if(lstOrderAttributes.size()>0)
			{
			System.err.println("Employee Fetched And Stored In ArrayList");	
			iomsConn.setAutoCommit(false);
			csIOMS = iomsConn.prepareCall(strInsertECRMOrderHeaderAttributestoIOMS);
						
			
			for (Iterator iter = lstOrderAttributes.iterator(); iter.hasNext();) 
			{
				ECRMMigrationDto element = (ECRMMigrationDto) iter.next();
				
				csIOMS.setString(1, element.getLabelName());
				csIOMS.setString(2, element.getLabelValue());
				csIOMS.setString(3, element.getCrmOrderId());
				csIOMS.setTimestamp(4, element.getCreationDate());
				csIOMS.setString(5, element.getCreatedBy());
				csIOMS.setTimestamp(6, element.getLastUpdatedDate());
				csIOMS.setString(7, element.getLastUpdatedBy());
				csIOMS.setInt(8, 0);
				csIOMS.setInt(9, 0);
				csIOMS.setString(10, "");
				csIOMS.execute();
				
			}
			 

			if (csIOMS.getInt(9) == 0) {
				iomsConn.commit();
				SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");									
				//UpdateIOMSDataTracker("insert","TM_ACCOUNTROLEDETAILS",sdf.format(new Date()),sdf.format(new Date()));
				isInserted = true;
				System.err.println("Header Attributes Updated....");
			} else {
				System.err.println("Header Attributes not Updated....");
				System.err.println(csIOMS.getString(10));
				iomsConn.rollback();
				isInserted = false;
			}
			
			}

				// Long ServiceSegment = null;
			System.out.println("Order Attributes Inserted");
			} catch (Exception e) {
			System.out.println("Error in method processPartyIds()"
					+ e.getStackTrace());
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				rset.close();
				iomsConn.close();
				DbConnection.closeResultset(rset);
				DbConnection.closePreparedStatement(pstmt);
				DbConnection.freeConnection(iomsConn);
			} catch (Exception e) {
				System.out.println("exeption due to : " + e.getMessage());
			}
		}
	}
	
	public static void InsertECRMContacttoIOMS()//INSERT TO TPOCONTACT
	{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		DBConnectionRetriever con = new DBConnectionRetriever();
		Connection crmcon = con.getCRMConnection();
		
		Connection iomsConn = null;
		CallableStatement csIOMS = null;
		boolean isInserted = false;
		try {
			System.out.println("Connect with IOMS database");
			iomsConn = getConnectionObject();
			System.out.println("query ::" + strGetOrderContactFromCRM);
			pstmt = crmcon.prepareStatement(strGetOrderContactFromCRM);
			//pstmt.setString(1,insertDate);
			rset = pstmt.executeQuery();
			int i = 0;
			ArrayList lstContact = new ArrayList();
			ECRMMigrationDto objDto = null; 
			while (rset.next()) 
			{
				objDto = new ECRMMigrationDto();
				objDto.setOrderContactId(rset.getString("ORDER_CONTACT_ID"));
				objDto.setContactType(rset.getString("CONTACTTYPE"));
				objDto.setFormofAddress(rset.getString("FORMOFADDRESS"));
				objDto.setGivenName(rset.getString("GIVENNAME"));
				objDto.setFamilyName(rset.getString("FAMILYNAME"));
				objDto.setEMail(rset.getString("EMAIL"));
				objDto.setCellPhoneNo(rset.getLong("CELLTELEPHONENR"));
				objDto.setCrmOrderId(rset.getString("CRM_ORDER_ID"));
				objDto.setFaxNo(rset.getString("FAXNR"));
				objDto.setCreatedBy(rset.getString("CREATED_BY"));
				objDto.setCreationDate1(rset.getString("CREATION_DATE"));
				objDto.setLastUpdatedBy(rset.getString("LAST_UPDATED_BY"));
				objDto.setLastUpdatedDate1(rset.getString("LAST_UPDATE_DATE"));
				
				lstContact.add(objDto);
				
			}
			
			System.err.println("Inside Function....");
			if(lstContact.size()>0)
			{
			System.err.println("Employee Fetched And Stored In ArrayList");	
			iomsConn.setAutoCommit(false);
			csIOMS = iomsConn.prepareCall(spInsertOrderContactToIOMS);
						
			
			for (Iterator iter = lstContact.iterator(); iter.hasNext();) 
			{
				ECRMMigrationDto element = (ECRMMigrationDto) iter.next();
				
				csIOMS.setString(1, element.getOrderContactId());
				csIOMS.setString(2, element.getContactType());
				csIOMS.setString(3, element.getFormofAddress());
				csIOMS.setString(4, element.getGivenName());
				csIOMS.setString(5, element.getFamilyName());
				csIOMS.setString(6, element.getEMail());
				csIOMS.setLong(7, element.getCellPhoneNo());
				csIOMS.setString(8, element.getCrmOrderId());
				csIOMS.setString(9, element.getFaxNo());
				csIOMS.setString(10, element.getCreationDate1());
				csIOMS.setString(11, element.getCreatedBy());
				csIOMS.setString(12, element.getLastUpdatedDate1());
				csIOMS.setString(13, element.getLastUpdatedBy());
				csIOMS.setInt(14, 0);
				csIOMS.setInt(15, 0);
				csIOMS.setString(16, "");
				csIOMS.execute();
			}
			 

			if (csIOMS.getInt(15) == 0) {
				iomsConn.commit();
				//SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");									
				//UpdateIOMSDataTracker("insert","TM_ACCOUNTROLEDETAILS",sdf.format(new Date()),sdf.format(new Date()));
				isInserted = true;
				System.err.println("Contacts Updated....");
			} else {
				System.err.println("Contacts not Updated....");
				System.err.println(csIOMS.getString(15));
				iomsConn.rollback();
				isInserted = false;
			}
			
			}

				// Long ServiceSegment = null;
			//System.out.println("Values assigned processPartyIds()");
			} catch (Exception e) {
			System.out.println("Error in method processPartyIds()"
					+ e.getStackTrace());
			e.printStackTrace();
		} finally {
			try {
				
				DbConnection.closeResultset(rset);
				DbConnection.closePreparedStatement(pstmt);
				DbConnection.closeCallableStatement(csIOMS);
				DbConnection.freeConnection(crmcon);
				DbConnection.freeConnection(iomsConn);
			} catch (Exception e) {
				System.out.println("exeption due to : " + e.getMessage());
			}
		}
	}
	
	public static void InsertECRMAddresstoIOMS()//INSERT INTO TPOADDRESS
	{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		DBConnectionRetriever con = new DBConnectionRetriever();
		Connection crmcon = con.getCRMConnection();
		
		Connection iomsConn = null;
		CallableStatement csIOMS = null;
		boolean isInserted = false;
		try {
			System.out.println("Connect with IOMS database");
			iomsConn = getConnectionObject();
			System.out.println("query ::" + strGetContactAddressFromCRM);
			pstmt = crmcon.prepareStatement(strGetContactAddressFromCRM);
			//pstmt.setString(1,insertDate);
			rset = pstmt.executeQuery();
			int i = 0;
			ArrayList lstContactAddress = new ArrayList();
			ECRMMigrationDto objDto = null; 
			while (rset.next()) 
			{
				objDto = new ECRMMigrationDto();
				objDto.setContactAddressId(rset.getString("CONTACT_ADDRESS_ID"));
				objDto.setAddress1(rset.getString("ADDRESS1"));
				objDto.setAddress2(rset.getString("ADDRESS2"));
				objDto.setAddress3(rset.getString("ADDRESS3"));
				objDto.setPin(rset.getString("PIN"));
				objDto.setCrmOrderId(rset.getString("CRM_ORDER_ID"));
				objDto.setOrderContactId(rset.getString("ORDER_CONTACT_ID"));
				objDto.setCityId(rset.getString("CITY_ID"));
				objDto.setStateId(rset.getString("STATE_ID"));
				objDto.setCountryId(rset.getString("COUNTRY_ID"));
				objDto.setCreatedBy(rset.getString("CREATED_BY"));
				objDto.setCreationDate1(rset.getString("CREATION_DATE"));
				objDto.setLastUpdatedBy(rset.getString("LAST_UPDATED_BY"));
				objDto.setLastUpdatedDate1(rset.getString("LAST_UPDATE_DATE"));
				
				lstContactAddress.add(objDto);
				
			}
			
			System.err.println("Inside Function....");
			if(lstContactAddress.size()>0)
			{
			System.err.println("Employee Fetched And Stored In ArrayList");	
			iomsConn.setAutoCommit(false);
			csIOMS = iomsConn.prepareCall(spInsertContactAddressToIOMS);
						
			
			for (Iterator iter = lstContactAddress.iterator(); iter.hasNext();) 
			{
				ECRMMigrationDto element = (ECRMMigrationDto) iter.next();
				
				csIOMS.setString(1, element.getContactAddressId());
				csIOMS.setString(2, element.getAddress1());
				csIOMS.setString(3, element.getAddress2());
				csIOMS.setString(4, element.getAddress3());
				csIOMS.setString(5, element.getPin());
				csIOMS.setString(6, element.getCrmOrderId());
				csIOMS.setString(7, element.getOrderContactId());
				csIOMS.setString(8, element.getCityId());
				csIOMS.setString(9, element.getStateId());
				csIOMS.setString(10, element.getCountryId());
				csIOMS.setString(11, element.getCreationDate1());
				csIOMS.setString(12, element.getCreatedBy());
				csIOMS.setString(13, element.getLastUpdatedDate1());
				csIOMS.setString(14, element.getLastUpdatedBy());
				csIOMS.setInt(15, 0);
				csIOMS.setInt(16, 0);
				csIOMS.setString(17, "");
				csIOMS.execute();
			}
			 

			if (csIOMS.getInt(16) == 0) {
				iomsConn.commit();
				//SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");									
				//UpdateIOMSDataTracker("insert","TM_ACCOUNTROLEDETAILS",sdf.format(new Date()),sdf.format(new Date()));
				isInserted = true;
				System.err.println("Contact Address Updated....");
			} else {
				iomsConn.rollback();
				System.err.println("Contact Address Not Updated....");
				System.err.println(csIOMS.getString(16));
				isInserted = false;
			}
			
			}

				// Long ServiceSegment = null;
			System.out.println("Values assigned processPartyIds()");
			} catch (Exception e) {
			System.out.println("Error in method processPartyIds()"
					+ e.getStackTrace());
			e.printStackTrace();
		} finally {
			try {
				
				DbConnection.closeResultset(rset);
				DbConnection.closePreparedStatement(pstmt);
				DbConnection.closeCallableStatement(csIOMS);
				DbConnection.freeConnection(crmcon);
				DbConnection.freeConnection(iomsConn);
			} catch (Exception e) {
				System.out.println("exeption due to : " + e.getMessage());
			}
		}
	}
	
	public static void InsertECRMPODetailstoIOMS()//INSERT INTO TPODETAILS
	{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		DBConnectionRetriever con = new DBConnectionRetriever();
		Connection crmcon = con.getCRMConnection();
		
		Connection iomsConn = null;
		CallableStatement csIOMS = null;
		boolean isInserted = false;
		try {
			System.out.println("Connect with IOMS database");
			iomsConn = getConnectionObject();
			System.out.println("query ::" + strGetPODetailsFromCRM);
			pstmt = crmcon.prepareStatement(strGetPODetailsFromCRM);
			//pstmt.setString(1,insertDate);
			rset = pstmt.executeQuery();
			int i = 0;
			ArrayList lstPODetails = new ArrayList();
			ECRMMigrationDto objDto = null; 
			while (rset.next()) 
			{
				objDto = new ECRMMigrationDto();
				objDto.setPoId(rset.getString("PO_ID"));
				objDto.setCrmOrderId(rset.getString("CRM_ORDER_ID"));
				objDto.setLegalEntityCode(rset.getString("LEGAL_ENTITY_CODE"));
				objDto.setTotalPOAmount(rset.getString("TOT_PO_AMT"));
				objDto.setContractPeriod(rset.getString("CONTRACT_PERIOD_MNTHS"));
				objDto.setCustPOReceiveDate(rset.getTimestamp("CUST_PO_RECEIVE_DATE"));
				objDto.setDefaultFlag(rset.getString("DEFAULT_FLAG"));
				objDto.setPoIssuingPersonName(rset.getString("PO_ISSUING_PERSON_NAME"));
				objDto.setPoPaymentTerms(rset.getString("PO_PAYMENT_TERMS"));
				objDto.setPoIssuingPersonEmail(rset.getString("PO_ISSUING_PERSON_EMAIL"));
				objDto.setDemoContractPeriod(rset.getString("DEMO_CONTRACT_PERIOD"));
				objDto.setContractStartDate(rset.getTimestamp("CONTRACT_START_DATE"));
				objDto.setContactEndDate(rset.getTimestamp("CONTRACT_END_DATE"));
				objDto.setCustPODate(rset.getTimestamp("CUST_PO_DATE"));
				objDto.setCustPONo(rset.getString("CUST_PO_NUMBER"));
				objDto.setCustPODate(rset.getTimestamp("CUST_PO_DATE"));
				objDto.setCreatedBy(rset.getString("CREATED_BY"));
				objDto.setCreationDate(rset.getTimestamp("CREATION_DATE"));
				objDto.setLastUpdatedBy(rset.getString("LAST_UPDATED_BY"));
				objDto.setLastUpdatedDate(rset.getTimestamp("LAST_UPDATE_DATE"));
				
				lstPODetails.add(objDto);
				
			}
			
			System.err.println("Inside Function....");
			if(lstPODetails.size()>0)
			{
			System.err.println("Employee Fetched And Stored In ArrayList");	
			iomsConn.setAutoCommit(false);
			csIOMS = iomsConn.prepareCall(spInsertPODetailsToIOMS);
						
			
			for (Iterator iter = lstPODetails.iterator(); iter.hasNext();) 
			{
				ECRMMigrationDto element = (ECRMMigrationDto) iter.next();
				
				csIOMS.setString(1, element.getPoId());
				csIOMS.setString(2, element.getCrmOrderId());
				csIOMS.setString(3, element.getLegalEntityCode());
				csIOMS.setString(4, element.getTotalPOAmount());
				csIOMS.setString(5, element.getContractPeriod());
				csIOMS.setTimestamp(6, element.getCustPOReceiveDate());
				csIOMS.setString(7, element.getDefaultFlag());
				csIOMS.setString(8, element.getPoIssuingPersonName());
				csIOMS.setString(9, element.getPoPaymentTerms());
				csIOMS.setString(10, element.getPoIssuingPersonEmail());
				csIOMS.setString(11, element.getDemoContractPeriod());
				csIOMS.setTimestamp(12, element.getContractStartDate());
				csIOMS.setTimestamp(13, element.getContactEndDate());
				csIOMS.setTimestamp(14, element.getCustPODate());
				csIOMS.setString(15, element.getCustPONo());
				csIOMS.setTimestamp(16, element.getCustPODate());
				csIOMS.setTimestamp(17, element.getCreationDate());
				csIOMS.setString(18, element.getCreatedBy());
				csIOMS.setTimestamp(19, element.getLastUpdatedDate());
				csIOMS.setString(20, element.getLastUpdatedBy());
				csIOMS.setInt(21, 0);
				csIOMS.setInt(22, 0);
				csIOMS.setString(23, "");
				csIOMS.execute();
				
			}
			 

			if (csIOMS.getInt(22) == 0) {
				iomsConn.commit();
				//SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");									
				//UpdateIOMSDataTracker("insert","TM_ACCOUNTROLEDETAILS",sdf.format(new Date()),sdf.format(new Date()));
				isInserted = true;
				System.err.println("PO Details Updated....");
			} else {
				iomsConn.rollback();
				System.err.println("PO Details Not Updated....");
				System.err.println(csIOMS.getString(22));
				isInserted = false;
			}
			
			}

				// Long ServiceSegment = null;
			System.out.println("Values assigned processPartyIds()");
			} catch (Exception e) {
			System.out.println("Error in method processPartyIds()"
					+ e.getStackTrace());
			e.printStackTrace();
		} finally {
			try {
				
				DbConnection.closeResultset(rset);
				DbConnection.closePreparedStatement(pstmt);
				DbConnection.closeCallableStatement(csIOMS);
				DbConnection.freeConnection(crmcon);
				DbConnection.freeConnection(iomsConn);
			} catch (Exception e) {
				System.out.println("exeption due to : " + e.getMessage());
			}
		}
	}
	
	public static void InsertECRMServicetoIOMS()//insert into TPOSERVICEMASTER
	{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		DBConnectionRetriever con = new DBConnectionRetriever();
		Connection crmcon = con.getCRMConnection();
		
		Connection iomsConn = null;
		CallableStatement csIOMS = null;
		boolean isInserted = false;
		try {
			System.out.println("Connect with IOMS database");
			iomsConn = getConnectionObject();
			System.out.println("query ::" + strGetOrderServiceFromCRM);
			pstmt = crmcon.prepareStatement(strGetOrderServiceFromCRM);
			//pstmt.setString(1,insertDate);
			rset = pstmt.executeQuery();
			int i = 0;
			ArrayList lstService = new ArrayList();
			ECRMMigrationDto objDto = null; 
			while (rset.next()) 
			{
				objDto = new ECRMMigrationDto();
				objDto.setServiceNo(rset.getString("SERVICE_NO"));
				objDto.setServiceType(rset.getString("M6_PRODUCT_ID"));
				objDto.setEffectiveStartDate1(rset.getString("EFFECTIVE_START_DATE"));
				objDto.setCustomerLogicalSIId(rset.getString("CUST_LOGICAL_SI_ID"));
				objDto.setEffectiveEndDate1(rset.getString("EFFECTIVE_END_DATE"));
				objDto.setProvisioningPlanId(rset.getString("PROVISIONING_PLAN_ID"));
				objDto.setRemarks(rset.getString("REMARKS"));
				objDto.setCrmOrderId(rset.getString("CRM_ORDER_ID"));
				objDto.setM6OrderId(rset.getString("M6_ORDER_ID"));
				objDto.setPreM6OrderId(rset.getString("PRE_M6_ORDER_ID"));
				objDto.setEventTypeId(rset.getString("EVENT_TYPE_ID"));
				objDto.setCancelReason(rset.getString("CANCEL_REASON"));
				objDto.setOrderType(rset.getString("SERVICE_ORDER_TYPE"));
				objDto.setLogicalSINo(rset.getString("LOGICAL_SI_NUMBER"));
				objDto.setCustomerLogicalSINo(rset.getString("CUST_LOGICAL_SI_NO"));
				objDto.setServiceState(rset.getString("NETWORK_STATE"));
				objDto.setProcessId(rset.getString("PROCESS_ID"));
				objDto.setRfsDate1(rset.getString("ATTRIBUTE1"));
				objDto.setCreatedBy(rset.getString("CREATED_BY"));
				objDto.setCreationDate1(rset.getString("CREATION_DATE"));
				objDto.setLastUpdatedBy(rset.getString("LAST_UPDATED_BY"));
				objDto.setLastUpdatedDate1(rset.getString("LAST_UPDATE_DATE"));
				
				lstService.add(objDto);
				
			}
			
			System.err.println("Inside Function....");
			if(lstService.size()>0)
			{
			System.err.println("Employee Fetched And Stored In ArrayList");	
			iomsConn.setAutoCommit(false);
			csIOMS = iomsConn.prepareCall(spInsertOrderServiceToIOMS);
						
			
			for (Iterator iter = lstService.iterator(); iter.hasNext();) 
			{
				ECRMMigrationDto element = (ECRMMigrationDto) iter.next();
				
				csIOMS.setString(1, element.getServiceNo());
				csIOMS.setString(2, element.getServiceType());
				csIOMS.setString(3, element.getEffectiveStartDate1());
				csIOMS.setString(4, element.getCustomerLogicalSIId());
				csIOMS.setString(5, element.getEffectiveEndDate1());
				csIOMS.setString(6, element.getProvisioningPlanId());
				csIOMS.setString(7, element.getRemarks());
				csIOMS.setString(8, element.getCrmOrderId());
				csIOMS.setString(9, element.getM6OrderId());
				csIOMS.setString(10, element.getPreM6OrderId());
				csIOMS.setString(11, element.getEventTypeId());
				csIOMS.setString(12, element.getCancelReason());
				csIOMS.setString(13, element.getServiceOrderType());
				csIOMS.setString(14, element.getLogicalSINo());
				csIOMS.setString(15, element.getCustomerLogicalSINo());
				csIOMS.setString(16, element.getProcessId());
				csIOMS.setString(17, element.getRfsDate1());
				csIOMS.setString(18, element.getServiceState());
				csIOMS.setString(19, element.getCreationDate1());
				csIOMS.setString(20, element.getCreatedBy());
				csIOMS.setString(21, element.getLastUpdatedDate1());
				csIOMS.setString(22, element.getLastUpdatedBy());
				csIOMS.setInt(23, 0);
				csIOMS.setInt(24, 0);
				csIOMS.setString(25, "");
				csIOMS.execute();
			}
			 

			if (csIOMS.getInt(24) == 0) {
				iomsConn.commit();
				//SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");									
				//UpdateIOMSDataTracker("insert","TM_ACCOUNTROLEDETAILS",sdf.format(new Date()),sdf.format(new Date()));
				isInserted = true;
				System.err.println("Service Details Updated....");
			} else {
				System.err.println("Service Details Not Updated....");
				iomsConn.rollback();
				System.err.println(csIOMS.getString(24));
				isInserted = false;
			}
			
			}

				// Long ServiceSegment = null;
			System.out.println("Values assigned");
			} catch (Exception e) {
			System.out.println("Error in method"
					+ e.getStackTrace());
			e.printStackTrace();
		} finally {
			try {
				DbConnection.closeResultset(rset);
				DbConnection.closePreparedStatement(pstmt);
				DbConnection.closeCallableStatement(csIOMS);
				DbConnection.freeConnection(crmcon);
				DbConnection.freeConnection(iomsConn);
			} catch (Exception e) {
				System.out.println("exeption due to : " + e.getMessage());
			}
		}
	}
	
	public static void InsertECRMServiceAttributestoIOMS()//insert into TPRODUCTATTVALUE
	{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		DBConnectionRetriever con = new DBConnectionRetriever();
		Connection crmcon = con.getCRMConnection();
		
		Connection iomsConn = null;
		CallableStatement csIOMS = null;
		boolean isInserted = false;
		try {
			System.out.println("Connect with IOMS database");
			iomsConn = getConnectionObject();
			System.out.println("query ::" + strGetServiceAttributesFromCRM);
			pstmt = crmcon.prepareStatement(strGetServiceAttributesFromCRM);
			//pstmt.setString(1,insertDate);
			rset = pstmt.executeQuery();
			int i = 0;
			ArrayList lstServiceAttributes = new ArrayList();
			ECRMMigrationDto objDto = null; 
			while (rset.next()) 
			{
				objDto = new ECRMMigrationDto();
				objDto.setM6LabelName(rset.getString("PRODUCT_ATTRIBUTE_ID"));
				objDto.setM6LabelValue(rset.getString("M6_LABEL_VALUE"));
				objDto.setServiceNo(rset.getString("SERVICE_NO"));
				objDto.setCreatedBy(rset.getString("CREATED_BY"));
				objDto.setCreationDate(rset.getTimestamp("CREATION_DATE"));
				objDto.setLastUpdatedBy(rset.getString("LAST_UPDATED_BY"));
				objDto.setLastUpdatedDate(rset.getTimestamp("LAST_UPDATE_DATE"));
				
				lstServiceAttributes.add(objDto);
				
			}
			
			System.err.println("Inside Function....");
			if(lstServiceAttributes.size()>0)
			{
			System.err.println("Employee Fetched And Stored In ArrayList");	
			iomsConn.setAutoCommit(false);
			csIOMS = iomsConn.prepareCall(spInsertServiceAttributesToIOMS);
			
						
			
			for (Iterator iter = lstServiceAttributes.iterator(); iter.hasNext();) 
			{
				ECRMMigrationDto element = (ECRMMigrationDto) iter.next();
				
				csIOMS.setString(1, element.getM6LabelName());
				csIOMS.setString(2, element.getM6LabelValue());
				csIOMS.setString(3, element.getServiceNo());
				csIOMS.setTimestamp(4, element.getCreationDate());
				csIOMS.setString(5, element.getCreatedBy());
				csIOMS.setTimestamp(6, element.getLastUpdatedDate());
				csIOMS.setString(7, element.getLastUpdatedBy());
				csIOMS.setInt(8, 0);
				csIOMS.setInt(9, 0);
				csIOMS.setString(10, "");
				csIOMS.execute();
			}
			 

			if (csIOMS.getInt(9) == 0) {
				iomsConn.commit();
				//SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");									
				//UpdateIOMSDataTracker("insert","TM_ACCOUNTROLEDETAILS",sdf.format(new Date()),sdf.format(new Date()));
				isInserted = true;
				System.err.println("Service Attributes Updated....");
			} else {
				iomsConn.rollback();
				System.err.println("Service Attributes Not Updated....");
				System.err.println(csIOMS.getString(9));
				isInserted = false;
			}
			
			}

				// Long ServiceSegment = null;
			System.out.println("Values assigned ");
			} catch (Exception e) {
			System.out.println("Error in method"
					+ e.getStackTrace());
			e.printStackTrace();
		} finally {
			try {
				DbConnection.closeResultset(rset);
				DbConnection.closePreparedStatement(pstmt);
				DbConnection.closeCallableStatement(csIOMS);
				DbConnection.freeConnection(crmcon);
				DbConnection.freeConnection(iomsConn);
				System.out.println("Connection Closed....");
			} catch (Exception e) {
				System.out.println("exeption due to : " + e.getMessage());
			}
		}
	}
	
	public static void InsertECRMOrderLinetoIOMS()//INSERT INTO TPOSERVICEDETAILS
	{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		DBConnectionRetriever con = new DBConnectionRetriever();
		Connection crmcon = con.getCRMConnection();
		
		Connection iomsConn = null;
		CallableStatement csIOMS = null;
		boolean isInserted = false;
		try {
			System.out.println("Connect with IOMS database");
			iomsConn = getConnectionObject();
			System.out.println("query ::" + strGetOrderLineFromCRM);
			pstmt = crmcon.prepareStatement(strGetOrderLineFromCRM);
			//pstmt.setString(1,insertDate);
			rset = pstmt.executeQuery();
			int i = 0;
			ArrayList lstLine = new ArrayList();
			ECRMMigrationDto objDto = null; 
			while (rset.next()) 
			{
				objDto = new ECRMMigrationDto();
				objDto.setOrderLineId(rset.getString("ORDER_LINE_ID"));
				objDto.setServiceNo(rset.getString("SERVICE_LIST_ID"));
				objDto.setProductName(rset.getString("M6_PRODUCT_ID"));
				objDto.setLocDate(rset.getString("LOC_DATE"));
				objDto.setLocNo(rset.getString("LOC_NUMBER"));
				objDto.setBillingTriggerDate(rset.getString("BILLING_TRIG_DATE"));
				objDto.setParentServiceId(rset.getString("PARENT_SERVICE_KEY"));
				objDto.setBillingTriggerFlag(rset.getString("BILLING_TRIG_FLAG"));
				objDto.setCircuitId(rset.getString("CIRCUIT_ID"));
				objDto.setPriLocId(rset.getString("PRI_LOC_ID"));
				objDto.setHubLocation(rset.getString("HUBLOCATION"));
				objDto.setUom(rset.getString("UOM"));
				objDto.setLocDate(rset.getString("LOC_DATE"));
				objDto.setBillingTriggerCreateDate(rset.getString("BILL_TRG_CREATE_DATE"));
				objDto.setChallenNo(rset.getString("CHALLEN_NO"));
				objDto.setChallenDate(rset.getString("CHALLEN_DATE"));
				objDto.setChildAccountNo(rset.getString("CHILD_ACCOUNT_NUMBER"));
				objDto.setM6ChildServiceKey(rset.getString("M6CHILDSERKEY"));
				objDto.setM6ParentServiceKey(rset.getString("M6PARENTSERKEY"));
				objDto.setLineState(rset.getString("NETWORK_STAT"));
				objDto.setM6ProductId(rset.getString("M6_PRODUCT_ID"));
				objDto.setCreatedBy(rset.getString("CREATED_BY"));
				objDto.setCreationDate(rset.getTimestamp("CREATION_DATE"));
				objDto.setLastUpdatedBy(rset.getString("LAST_UPDATED_BY"));
				objDto.setLastUpdatedDate(rset.getTimestamp("LAST_UPDATE_DATE"));
				
				lstLine.add(objDto);
				
			}
			
			System.err.println("Inside Function....");
			if(lstLine.size()>0)
			{
			System.err.println("Employee Fetched And Stored In ArrayList");	
			iomsConn.setAutoCommit(false);
			csIOMS = iomsConn.prepareCall(spInsertOrderLineToIOMS);
						
			for (Iterator iter = lstLine.iterator(); iter.hasNext();) 
			{
				ECRMMigrationDto element = (ECRMMigrationDto) iter.next();
				
				csIOMS.setString(1, element.getOrderLineId());
				csIOMS.setString(2, element.getServiceNo());
				csIOMS.setString(3, element.getProductName());
				csIOMS.setString(4, element.getLocDate());
				csIOMS.setString(5, element.getBillingTriggerDate());
				csIOMS.setString(6, element.getLocNo());
				csIOMS.setString(7, element.getBillingTriggerCreateDate());
				csIOMS.setString(8, element.getParentServiceId());
				csIOMS.setString(9, element.getBillingTriggerFlag());
				csIOMS.setString(10, element.getChildAccountNo());
				csIOMS.setString(11, element.getM6ChildServiceKey());
				csIOMS.setString(12, element.getM6ParentServiceKey());
				csIOMS.setString(13, element.getLineState());
				csIOMS.setString(14, element.getCircuitId());
				csIOMS.setString(15, element.getPriLocId());
				csIOMS.setString(16, element.getHubLocation());
				csIOMS.setString(17,element.getUom());
				csIOMS.setString(18,element.getLocDate());
				csIOMS.setString(19, element.getChallenNo());
				csIOMS.setString(20, element.getChallenDate());
				csIOMS.setTimestamp(21, element.getCreationDate());
				csIOMS.setString(22, element.getCreatedBy());
				csIOMS.setTimestamp(23, element.getLastUpdatedDate());
				csIOMS.setString(24, element.getLastUpdatedBy());
				csIOMS.setInt(25, 0);
				csIOMS.setInt(26, 0);
				csIOMS.setString(27, "");
				csIOMS.execute();
			}
			 

			if (csIOMS.getInt(26) == 0) {
				iomsConn.commit();
				//SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");									
				//UpdateIOMSDataTracker("insert","TM_ACCOUNTROLEDETAILS",sdf.format(new Date()),sdf.format(new Date()));
				isInserted = true;
				System.err.println("ORDER Line Updated....");
			} else {
				iomsConn.rollback();
				System.err.println(csIOMS.getString(26));
				System.err.println("ORDER Line Not Updated....");
				isInserted = false;
			}
			
			}

				// Long ServiceSegment = null;
			System.out.println("Values assigned");
			} catch (Exception e) {
			System.out.println("Error in method "
					+ e.getStackTrace());
			e.printStackTrace();
		} finally {
			try {
				DbConnection.closeResultset(rset);
				DbConnection.closePreparedStatement(pstmt);
				DbConnection.closeCallableStatement(csIOMS);
				DbConnection.freeConnection(crmcon);
				DbConnection.freeConnection(iomsConn);
			} catch (Exception e) {
				System.out.println("exeption due to : " + e.getMessage());
			}
		}
	}
	
	public static void InsertECRMLineAttributestoIOMS()//INSERT INTO TPRODUCTLINEATTVALUE
	{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		DBConnectionRetriever con = new DBConnectionRetriever();
		Connection crmcon = con.getCRMConnection();
		
		Connection iomsConn = null;
		CallableStatement csIOMS = null;
		boolean isInserted = false;
		try {
			System.out.println("Connect with IOMS database");
			iomsConn = getConnectionObject();
			System.out.println("query ::" + strGetLineAttributesFromCRM);
			pstmt = crmcon.prepareStatement(strGetLineAttributesFromCRM);
			//pstmt.setString(1,insertDate);
			rset = pstmt.executeQuery();
			int i = 0;
			ArrayList lstLineAttributes = new ArrayList();
			ECRMMigrationDto objDto = null; 
			while (rset.next()) 
			{
				objDto = new ECRMMigrationDto();
				objDto.setM6LabelName(rset.getString("PRODUCT_ATTRIBUTE_ID"));
				objDto.setM6LabelValue(rset.getString("M6_LABEL_VALUE"));
				objDto.setOrderLineId(rset.getString("ORDER_LINE_NO"));
				objDto.setCreatedBy(rset.getString("CREATED_BY"));
				objDto.setCreationDate(rset.getTimestamp("CREATION_DATE"));
				objDto.setLastUpdatedBy(rset.getString("LAST_UPDATED_BY"));
				objDto.setLastUpdatedDate(rset.getTimestamp("LAST_UPDATE_DATE"));
				
				lstLineAttributes.add(objDto);
				
			}
			
			System.err.println("Inside Function....");
			if(lstLineAttributes.size()>0)
			{
			System.err.println("Employee Fetched And Stored In ArrayList");	
			iomsConn.setAutoCommit(false);
			csIOMS = iomsConn.prepareCall(spInsertLineAttributesToIOMS);
						
			
			for (Iterator iter = lstLineAttributes.iterator(); iter.hasNext();) 
			{
				ECRMMigrationDto element = (ECRMMigrationDto) iter.next();
				
				csIOMS.setString(1, element.getM6LabelName());
				csIOMS.setString(2, element.getM6LabelValue());
				csIOMS.setString(3, element.getOrderLineId());
				csIOMS.setTimestamp(4, element.getCreationDate());
				csIOMS.setString(5, element.getCreatedBy());
				csIOMS.setTimestamp(6, element.getLastUpdatedDate());
				csIOMS.setString(7, element.getLastUpdatedBy());
				csIOMS.setInt(8, 0);
				csIOMS.setInt(9, 0);
				csIOMS.setString(10, "");
				csIOMS.execute();
			}
			 

			if (csIOMS.getInt(9) == 0) {
				iomsConn.commit();
				//SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");									
				//UpdateIOMSDataTracker("insert","TM_ACCOUNTROLEDETAILS",sdf.format(new Date()),sdf.format(new Date()));
				isInserted = true;
				System.err.println("Line Attributes Updated....");
			} else {
				iomsConn.rollback();
				System.err.println(csIOMS.getString(9));
				System.err.println("Line Attributes Not Updated....");
				isInserted = false;
			}
			
			}

				// Long ServiceSegment = null;
			System.out.println("Values assigned processPartyIds()");
			} catch (Exception e) {
			System.out.println("Error in method processPartyIds()"
					+ e.getStackTrace());
			e.printStackTrace();
		} finally {
			try {
				DbConnection.closeResultset(rset);
				DbConnection.closePreparedStatement(pstmt);
				DbConnection.closeCallableStatement(csIOMS);
				DbConnection.freeConnection(crmcon);
				DbConnection.freeConnection(iomsConn);
			} catch (Exception e) {
				System.out.println("exeption due to : " + e.getMessage());
			}
		}
	}
	
	public static void InsertECRMChargesHeaderBillingInfotoIOMS()//INSERT INTO TBILLING_INFO
	{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		DBConnectionRetriever con = new DBConnectionRetriever();
		Connection crmcon = con.getCRMConnection();
		
		Connection iomsConn = null;
		CallableStatement csIOMS = null;
		boolean isInserted = false;
		try {
			System.out.println("Connect with IOMS database");
			iomsConn = getConnectionObject();
			System.out.println("query ::" + strGetChargesHeaderFromCRM);
			pstmt = crmcon.prepareStatement(strGetChargesHeaderFromCRM);
			//pstmt.setString(1,insertDate);
			rset = pstmt.executeQuery();
			int i = 0;
			ArrayList lstBillingInfo = new ArrayList();
			ECRMMigrationDto objDto = null; 
			while (rset.next()) 
			{
				objDto = new ECRMMigrationDto();
				objDto.setBillingInfoId(rset.getString("CHARGE_HDR_ID"));
				objDto.setPoId(rset.getString("PO_ID"));
				objDto.setCustAccountId(rset.getString("ACCOUNT_NUMBER"));
				objDto.setCreditPeriod(rset.getString("CREDIT_PERIOD"));
				objDto.setLegalEntityCode(rset.getString("LEGAL_ENTITY_CODE"));
				objDto.setBillingMode(rset.getString("BILLING_MODE"));
				objDto.setBillFormat(rset.getString("BILL_FORMAT"));
				objDto.setLicenceCompany(rset.getString("LICENCE_COMPANY"));
				objDto.setTaxation(rset.getString("TAXATION"));
				objDto.setBillingLevel(rset.getString("BILLING_LEVEL"));
				objDto.setCommitmentPeriod(rset.getString("COMMITMENT_PERIOD"));
				objDto.setPenaltyCause(rset.getString("PENELTY_CLAUSE"));
				objDto.setOrderLineId(rset.getString("ORDER_LINE_ID"));
				objDto.setBillType(rset.getString("BILL_TYPE"));
				objDto.setBillingAddressId(rset.getString("BILLING_ADDRESS_ID"));
				objDto.setBillingLevelNo(rset.getString("BILLING_LEVEL_NUMBER"));
				objDto.setNoticePeriod(rset.getString("NOTICE_PERIOD"));
				objDto.setCreatedBy(rset.getString("CREATED_BY"));
				objDto.setCreationDate1(rset.getString("CREATION_DATE"));
				objDto.setLastUpdatedBy(rset.getString("LAST_UPDATED_BY"));
				objDto.setLastUpdatedDate1(rset.getString("LAST_UPDATE_DATE"));
				
				lstBillingInfo.add(objDto);
				
			}
			
			System.err.println("Inside Function....");
			if(lstBillingInfo.size()>0)
			{
			System.err.println("Employee Fetched And Stored In ArrayList");	
			iomsConn.setAutoCommit(false);
			csIOMS = iomsConn.prepareCall(spInsertChargesHeaderToIOMS);
						
			
			for (Iterator iter = lstBillingInfo.iterator(); iter.hasNext();) 
			{
				ECRMMigrationDto element = (ECRMMigrationDto) iter.next();
				
				
				csIOMS.setString(1, element.getPoId());
				csIOMS.setString(2, element.getCustAccountId());
				csIOMS.setString(3, element.getCreditPeriod());
				csIOMS.setString(4, element.getLegalEntityCode());
				csIOMS.setString(5, element.getBillingMode());
				csIOMS.setString(6, element.getBillFormat());
				csIOMS.setString(7, element.getLicenceCompany());
				csIOMS.setString(8, element.getTaxation());
				csIOMS.setString(9, element.getBillingLevel());
				csIOMS.setString(10, element.getCommitmentPeriod());
				csIOMS.setString(11, element.getPenaltyCause());
				csIOMS.setString(12, element.getOrderLineId());
				csIOMS.setString(13, element.getBillType());
				csIOMS.setString(14, element.getBillingAddressId());
				csIOMS.setString(15, element.getBillingLevelNo());
				csIOMS.setString(16, element.getNoticePeriod());
				csIOMS.setString(17, element.getCreationDate1());
				csIOMS.setString(18, element.getCreatedBy());
				csIOMS.setString(19, element.getLastUpdatedDate1());
				csIOMS.setString(20, element.getLastUpdatedBy());
				csIOMS.setString(21,element.getBillingInfoId());
				csIOMS.setInt(22, 0);
				csIOMS.setInt(23, 0);
				csIOMS.setString(24, "");
				csIOMS.execute();
			}
			 

			if (csIOMS.getInt(23) == 0) {
				iomsConn.commit();
				//SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");									
				//UpdateIOMSDataTracker("insert","TM_ACCOUNTROLEDETAILS",sdf.format(new Date()),sdf.format(new Date()));
				isInserted = true;
				System.err.println("Billing Header Updated....");
			} else {
				iomsConn.rollback();
				System.err.println(csIOMS.getString(23));
				System.err.println("Billing Header Not Updated....");
				isInserted = false;
			}
			
			}

				// Long ServiceSegment = null;
			System.out.println("Values assigned processPartyIds()");
			} catch (Exception e) {
			System.out.println("Error in method processPartyIds()"
					+ e.getStackTrace());
			e.printStackTrace();
		} finally {
			try {
				DbConnection.closeResultset(rset);
				DbConnection.closePreparedStatement(pstmt);
				DbConnection.closeCallableStatement(csIOMS);
				DbConnection.freeConnection(crmcon);
				DbConnection.freeConnection(iomsConn);
			} catch (Exception e) {
				System.out.println("exeption due to : " + e.getMessage());
			}
		}
	}
	
	
	public static void InsertECRMLocationInfotoIOMS()//insert into TLOCATION_INFO
	{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		DBConnectionRetriever con = new DBConnectionRetriever();
		Connection crmcon = con.getCRMConnection();
		
		Connection iomsConn = null;
		CallableStatement csIOMS = null;
		boolean isInserted = false;
		try {
			System.out.println("Connect with IOMS database");
			iomsConn = getConnectionObject();
			System.out.println("query ::" + strGetLocationHeaderFromCRM);
			pstmt = crmcon.prepareStatement(strGetLocationHeaderFromCRM);
			//pstmt.setString(1,insertDate);
			rset = pstmt.executeQuery();
			int i = 0;
			ArrayList lstLocation = new ArrayList();
			ECRMMigrationDto objDto = null; 
			while (rset.next()) 
			{
				objDto = new ECRMMigrationDto();
				objDto.setPriLocationType(rset.getString("PRI_LOC_TYP"));
				objDto.setPriLocationId(rset.getString("PRI_LOC_ID"));
				objDto.setSecLocationType(rset.getString("SEC_LOC_TYP"));
				objDto.setSecLocationId(rset.getString("SEC_LOC_ID"));
				objDto.setCustAccountId(rset.getString("ACCOUNT_NO"));
				objDto.setOrderLineId(rset.getString("ORDER_LINE_ID"));
				objDto.setCreatedBy(rset.getString("CREATED_BY"));
				objDto.setCreationDate1(rset.getString("CREATION_DATE"));
				objDto.setLastUpdatedBy(rset.getString("LAST_UPDATED_BY"));
				objDto.setLastUpdatedDate1(rset.getString("LAST_UPDATE_DATE"));
				
				lstLocation.add(objDto);
				
			}
			
			System.err.println("Inside Function....");
			if(lstLocation.size()>0)
			{
			System.err.println("Employee Fetched And Stored In ArrayList");	
			iomsConn.setAutoCommit(false);
			csIOMS = iomsConn.prepareCall(spInsertLocationHeaderToIOMS);
						
			
			for (Iterator iter = lstLocation.iterator(); iter.hasNext();) 
			{
				ECRMMigrationDto element = (ECRMMigrationDto) iter.next();
				
				csIOMS.setString(1, element.getPriLocationType());
				csIOMS.setString(2, element.getPriLocationId());
				csIOMS.setString(3, element.getSecLocationType());
				csIOMS.setString(4, element.getSecLocationId());
				csIOMS.setString(5, element.getCustAccountId());
				csIOMS.setString(6, element.getOrderLineId());
				csIOMS.setString(7, element.getCreationDate1());
				csIOMS.setString(8, element.getCreatedBy());
				csIOMS.setString(9, element.getLastUpdatedDate1());
				csIOMS.setString(10, element.getLastUpdatedBy());
				csIOMS.setInt(11, 0);
				csIOMS.setInt(12, 0);
				csIOMS.setString(13, "");
				csIOMS.execute();
			}
			 

			if (csIOMS.getInt(12) == 0) {
				iomsConn.commit();
				//SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");									
				//UpdateIOMSDataTracker("insert","TM_ACCOUNTROLEDETAILS",sdf.format(new Date()),sdf.format(new Date()));
				isInserted = true;
				System.err.println("Location details Updated....");
			} else {
				iomsConn.rollback();
				System.err.println(csIOMS.getString(12));
				System.err.println("Location details Not Updated....");
				isInserted = false;
			}
			
			}

				// Long ServiceSegment = null;
			System.out.println("Values assigned");
			} catch (Exception e) {
			System.out.println("Error in method"
					+ e.getStackTrace());
			e.printStackTrace();
		} finally {
			try {
				DbConnection.closeResultset(rset);
				DbConnection.closePreparedStatement(pstmt);
				DbConnection.closeCallableStatement(csIOMS);
				DbConnection.freeConnection(crmcon);
				DbConnection.freeConnection(iomsConn);
			} catch (Exception e) {
				System.out.println("exeption due to : " + e.getMessage());
			}
		}
	}
	
		
	public static void InsertECRMHardwareInfotoIOMS()//INSERT INTO THARDWARE_INFO
	{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		DBConnectionRetriever con = new DBConnectionRetriever();
		Connection crmcon = con.getCRMConnection();
		
		Connection iomsConn = null;
		CallableStatement csIOMS = null;
		boolean isInserted = false;
		try {
			System.out.println("Connect with IOMS database");
			iomsConn = getConnectionObject();
			System.out.println("query ::" + strGetHardwareHeaderFromCRM);
			pstmt = crmcon.prepareStatement(strGetHardwareHeaderFromCRM);
			//pstmt.setString(1,insertDate);
			rset = pstmt.executeQuery();
			int i = 0;
			ArrayList lstHardware = new ArrayList();
			ECRMMigrationDto objDto = null; 
			while (rset.next()) 
			{
				objDto = new ECRMMigrationDto();
				objDto.setStore(rset.getString("STORE"));
				objDto.setHarwareType(rset.getString("HARDWARE_TYPE"));
				objDto.setForm(rset.getString("FORM_C_AVAILABLE"));
				objDto.setTypeofSale(rset.getString("TYPE_OF_SALE"));
				objDto.setNatureofSale(rset.getString("NATURE_OF_SALE"));
				objDto.setDispatchAddId(rset.getString("DISPATCH_ADDRESS_ID"));
				objDto.setCustAccountId(rset.getString("ACCOUNT_NUMBER"));
				objDto.setOrderLineId(rset.getString("ORDER_LINE_ID"));
				objDto.setHwWarrantyStartDateLogic(rset.getString("WARRANTY_DATE_LOGIC"));
				objDto.setHwWarrantyEndDateLogic(rset.getString("WARRANTY_END_DATE_LOGIC"));
				objDto.setHwWarrantyPeriodMonths(rset.getString("WARRANTY_PERIOD_MONTHS"));
				objDto.setHwWarrantyStartDate(rset.getString("WARRANTY_START_DATE"));
				objDto.setHwWarrantyEndDate(rset.getString("WARRANTY_END_DATE"));
				objDto.setPrincipalAmount(rset.getString("PRINCIPAL_AMT"));
				objDto.setInterestRate(rset.getString("INTREST_RATE"));
				objDto.setCreatedBy(rset.getString("CREATED_BY"));
				objDto.setCreationDate1(rset.getString("CREATION_DATE"));
				objDto.setLastUpdatedBy(rset.getString("LAST_UPDATED_BY"));
				objDto.setLastUpdatedDate1(rset.getString("LAST_UPDATE_DATE"));
				
				lstHardware.add(objDto);
				
			}
			
			System.err.println("Inside Function....");
			if(lstHardware.size()>0)
			{
			System.err.println("Employee Fetched And Stored In ArrayList");	
			iomsConn.setAutoCommit(false);
			csIOMS = iomsConn.prepareCall(spInsertHardwareHeaderToIOMS);
						
			
			for (Iterator iter = lstHardware.iterator(); iter.hasNext();) 
			{
				ECRMMigrationDto element = (ECRMMigrationDto) iter.next();
				
				csIOMS.setString(1, element.getStore());
				csIOMS.setString(2, element.getHarwareType());
				csIOMS.setString(3, element.getForm());
				csIOMS.setString(4, element.getTypeofSale());
				csIOMS.setString(5, element.getNatureofSale());
				csIOMS.setString(6, element.getDispatchAddId());
				csIOMS.setString(7, element.getCustAccountId());
				csIOMS.setString(8, element.getOrderLineId());
				csIOMS.setString(9, element.getHwWarrantyStartDateLogic());
				csIOMS.setString(10, element.getHwWarrantyEndDateLogic());
				csIOMS.setString(11, element.getHwWarrantyPeriodMonths());
				csIOMS.setString(12, element.getHwWarrantyStartDate());
				csIOMS.setString(13, element.getHwWarrantyEndDate());
				csIOMS.setString(14, element.getPrincipalAmount());
				csIOMS.setString(15, element.getInterestRate());
				csIOMS.setString(16, element.getCreationDate1());
				csIOMS.setString(17, element.getCreatedBy());
				csIOMS.setString(18, element.getLastUpdatedDate1());
				csIOMS.setString(19, element.getLastUpdatedBy());
				csIOMS.setInt(20, 0);
				csIOMS.setInt(21, 0);
				csIOMS.setString(22, "");
				csIOMS.execute();
			}
			 

			if (csIOMS.getInt(21) == 0) {
				iomsConn.commit();
				//SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");									
				//UpdateIOMSDataTracker("insert","TM_ACCOUNTROLEDETAILS",sdf.format(new Date()),sdf.format(new Date()));
				isInserted = true;
				System.err.println("Hardware Info Updated....");
			} else {
				iomsConn.rollback();
				System.err.println(csIOMS.getString(21));
				System.err.println("Hardware Info Not Updated....");
				isInserted = false;
			}
			
			}

				// Long ServiceSegment = null;
			System.out.println("Values assigned");
			} catch (Exception e) {
			System.out.println("Error in method"
					+ e.getStackTrace());
			e.printStackTrace();
		} finally {
			try {
				DbConnection.closeResultset(rset);
				DbConnection.closePreparedStatement(pstmt);
				DbConnection.closeCallableStatement(csIOMS);
				DbConnection.freeConnection(crmcon);
				DbConnection.freeConnection(iomsConn);
			} catch (Exception e) {
				System.out.println("exeption due to : " + e.getMessage());
			}
		}
	}
	
	
	public static void InsertECRMChargestoIOMS()
	{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		DBConnectionRetriever con = new DBConnectionRetriever();
		Connection crmcon = con.getCRMConnection();
		
		Connection iomsConn = null;
		CallableStatement csIOMS = null;
		boolean isInserted = false;
		try {
			System.out.println("Connect with IOMS database");
			iomsConn = getConnectionObject();
			System.out.println("query ::" + strGetChargesFromCRM);
			pstmt = crmcon.prepareStatement(strGetChargesFromCRM);
			//pstmt.setString(1,insertDate);
			rset = pstmt.executeQuery();
			int i = 0;
			ArrayList lstCharges = new ArrayList();
			ECRMMigrationDto objDto = null; 
			while (rset.next()) 
			{
				objDto = new ECRMMigrationDto();
				
				objDto.setChargeId(rset.getString("PK_CHARGES_ID"));
				objDto.setOrderLineId(rset.getString("ORDER_LINE_ID"));
				objDto.setChargetype(rset.getString("CHARGE_TYPE"));
				objDto.setContractPeriodMonths(rset.getString("CONTRACT_PERIOD_MNTHS"));
				objDto.setTotalAmount(rset.getDouble("TOTAL_AMOUNT"));
				objDto.setFrequency(rset.getString("FREQUENCY"));
				objDto.setChargeValue(rset.getDouble("CHARGE_VALUE"));
				objDto.setStartDatLogic(rset.getString("START_DATE_LOGIC"));
				objDto.setEndDateLogic(rset.getString("END_DATE_LOGIC"));
				objDto.setStartDateDays(rset.getString("START_DATE_DAYS"));
				objDto.setStartDateMonths(rset.getString("START_DATE_MONTHS"));
				objDto.setEndDateDays(rset.getString("END_DATE_DAYS"));
				objDto.setEndDateMonths(rset.getString("END_DATE_MONTHS"));
				objDto.setFxStatus(rset.getString("FX_STATUS"));
				objDto.setAnnotation(rset.getString("ANNOTATION"));
				objDto.setCreatedBy(rset.getString("CREATED_BY"));
				objDto.setCreationDate1(rset.getString("CREATION_DATE"));
				objDto.setLastUpdatedBy(rset.getString("LAST_UPDATED_BY"));
				objDto.setLastUpdatedDate1(rset.getString("LAST_UPDATE_DATE"));
				
				lstCharges.add(objDto);
				
			}
			
			System.err.println("Inside Function....");
			if(lstCharges.size()>0)
			{
			System.err.println("Employee Fetched And Stored In ArrayList");	
			iomsConn.setAutoCommit(false);
			csIOMS = iomsConn.prepareCall(spInsertChargesToIOMS);
						
			
			for (Iterator iter = lstCharges.iterator(); iter.hasNext();) 
			{
			ECRMMigrationDto element = (ECRMMigrationDto) iter.next();
			
			csIOMS.setString(1, element.getChargeId());
			csIOMS.setString(2, element.getOrderLineId());
			csIOMS.setString(3, element.getChargetype());
			csIOMS.setString(4, element.getContractPeriodMonths());
			csIOMS.setDouble(5, element.getTotalAmount());
			csIOMS.setString(6, element.getFrequency());
			csIOMS.setDouble(7, element.getChargeValue());
			csIOMS.setString(8, element.getStartDatLogic());
			csIOMS.setString(9, element.getEndDateLogic());
			csIOMS.setString(10, element.getStartDateDays());
			csIOMS.setString(11, element.getStartDateMonths());
			csIOMS.setString(12, element.getEndDateDays());
			csIOMS.setString(13, element.getEndDateMonths());
			csIOMS.setString(14, element.getFxStatus());
			csIOMS.setString(15, element.getAnnotation());
			csIOMS.setString(16, element.getCreationDate1());
			csIOMS.setString(17, element.getCreatedBy());
			csIOMS.setString(18, element.getLastUpdatedDate1());
			csIOMS.setString(19, element.getLastUpdatedBy());
			csIOMS.setInt(20, 0);
			csIOMS.setInt(21, 0);
			csIOMS.setString(22, "");
			csIOMS.execute();
			}
			 

			if (csIOMS.getInt(21) == 0) {
				iomsConn.commit();
				//SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");									
				//UpdateIOMSDataTracker("insert","TM_ACCOUNTROLEDETAILS",sdf.format(new Date()),sdf.format(new Date()));
				isInserted = true;
				System.err.println("Charges Info Updated....");
			} else {
				iomsConn.rollback();
				System.err.println(csIOMS.getString(21));
				System.err.println("Charges Info Not Updated....");
				isInserted = false;
			}
			
			}

				// Long ServiceSegment = null;
			System.out.println("Values assigned ");
			} catch (Exception e) {
			System.out.println("Error in method "
					+ e.getStackTrace());
			e.printStackTrace();
		} finally {
			try {
				DbConnection.closeResultset(rset);
				DbConnection.closePreparedStatement(pstmt);
				DbConnection.closeCallableStatement(csIOMS);
				DbConnection.freeConnection(crmcon);
				DbConnection.freeConnection(iomsConn);
			} catch (Exception e) {
				System.out.println("exeption due to : " + e.getMessage());
			}
		}
	}
	
	public static void InsertECRMApprovalWorkFlowtoIOMS()//INSERT INTO IOE.TPOWORKFLOWTASK
	{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		DBConnectionRetriever con = new DBConnectionRetriever();
		Connection crmcon = con.getCRMConnection();
		
		Connection iomsConn = null;
		CallableStatement csIOMS = null;
		boolean isInserted = false;
		try {
			System.out.println("Connect with IOMS database");
			iomsConn = getConnectionObject();
			System.out.println("query ::" + spInsertApprovalWorkFlowFromCRM);
			pstmt = crmcon.prepareStatement(spInsertApprovalWorkFlowFromCRM);
			//pstmt.setString(1,insertDate);
			rset = pstmt.executeQuery();
			int i = 0;
			ArrayList lstApprovals = new ArrayList();
			ECRMMigrationDto objDto = null; 
			while (rset.next()) 
			{
				objDto = new ECRMMigrationDto();
				objDto.setTaskId(rset.getString("TASK_ID"));
				objDto.setTaskName(rset.getString("TASK_NAME"));
				objDto.setOwnerId(rset.getString("OWNER_ID"));
				objDto.setTaskStatusId(rset.getString("TASK_STATUS_ID"));
				objDto.setPlannedStartDate(rset.getString("PLANNED_START_DATE"));
				objDto.setCrmOrderId(rset.getString("SOURCE_OBJECT_ID"));
				objDto.setReasonCode(rset.getString("REASON_CODE"));
				objDto.setActualEndDate(rset.getString("ACTUAL_END_DATE"));
				objDto.setCreatedBy(rset.getString("CREATED_BY"));
				objDto.setCreationDate1(rset.getString("CREATION_DATE"));
				lstApprovals.add(objDto);
				
			}
			
			System.err.println("Inside Function....");
			if(lstApprovals.size()>0)
			{
			System.err.println("Employee Fetched And Stored In ArrayList");	
			iomsConn.setAutoCommit(false);
			csIOMS = iomsConn.prepareCall(spGetApprovalWorkFlowTOIOMS);
						
			
			for (Iterator iter = lstApprovals.iterator(); iter.hasNext();) 
			{
				ECRMMigrationDto element = (ECRMMigrationDto) iter.next();
				
				csIOMS.setString(1, element.getTaskId());
				csIOMS.setString(2, element.getTaskName());
				csIOMS.setString(3, element.getOwnerId());
				csIOMS.setString(4, element.getTaskStatusId());
				csIOMS.setString(5, element.getPlannedStartDate());
				csIOMS.setString(6, element.getActualEndDate());
				csIOMS.setString(7, element.getCrmOrderId());
				csIOMS.setString(8, element.getCreatedBy());
				csIOMS.setString(9, element.getCreationDate1());
				csIOMS.setString(10, element.getReasonCode());
				csIOMS.setInt(11, 0);
				csIOMS.setInt(12, 0);
				csIOMS.setString(13, "");
				csIOMS.execute();
				
			}
			 

			if (csIOMS.getInt(12) == 0) {
				iomsConn.commit();
				//SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");									
				//UpdateIOMSDataTracker("insert","TM_ACCOUNTROLEDETAILS",sdf.format(new Date()),sdf.format(new Date()));
				isInserted = true;
				System.err.println("Approval Workflow Details Updated....");
			} else {
				System.err.println("Approval Workflow Details Not Updated....");
				System.err.println(csIOMS.getString(10));
				iomsConn.rollback();
				isInserted = false;
			}
			
			}

				// Long ServiceSegment = null;
			System.out.println("Approval Workflow Details Inserted");
			} catch (Exception e) {
			System.out.println("Error in method Approval Workflow()"
					+ e.getStackTrace());
			e.printStackTrace();
		} finally {
			try {
				DbConnection.closeResultset(rset);
				DbConnection.closePreparedStatement(pstmt);
				DbConnection.closeCallableStatement(csIOMS);
				DbConnection.freeConnection(crmcon);
				DbConnection.freeConnection(iomsConn);
			} catch (Exception e) {
				System.out.println("exeption due to : " + e.getMessage());
			}
		}
	}
	
	public static void InsertECRMApprovalsInfotoIOMS()//INSERT INTO IOE.TTASKACTIONHISTORY
	{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		DBConnectionRetriever con = new DBConnectionRetriever();
		Connection crmcon = con.getCRMConnection();
		
		Connection iomsConn = null;
		CallableStatement csIOMS = null;
		boolean isInserted = false;
		try {
			System.out.println("Connect with IOMS database");
			iomsConn = getConnectionObject();
			System.out.println("query ::" + strGetApprovalDetailsFromCRM);
			pstmt = crmcon.prepareStatement(strGetApprovalDetailsFromCRM);
			//pstmt.setString(1,insertDate);
			rset = pstmt.executeQuery();
			int i = 0;
			ArrayList lstApprovals = new ArrayList();
			ECRMMigrationDto objDto = null; 
			while (rset.next()) 
			{
				objDto = new ECRMMigrationDto();
				objDto.setTaskId(rset.getString("TASK_ID"));
				objDto.setTaskStatusId(rset.getString("TASK_STATUS_ID"));
				objDto.setOwnerId(rset.getString("OWNER_ID"));
				objDto.setPlannedStartDate(rset.getString("PLANNED_START_DATE"));
				objDto.setCrmOrderId(rset.getString("SOURCE_OBJECT_ID"));
				objDto.setReasonCode(rset.getString("REASON_CODE"));
				objDto.setActualEndDate(rset.getString("ACTUAL_END_DATE"));
				objDto.setCreatedBy(rset.getString("CREATED_BY"));
				lstApprovals.add(objDto);
				
			}
			
			System.err.println("Inside Function....");
			if(lstApprovals.size()>0)
			{
			System.err.println("Employee Fetched And Stored In ArrayList");	
			iomsConn.setAutoCommit(false);
			csIOMS = iomsConn.prepareCall(strInsertECRMApprovalstoIOMS);
						
			
			for (Iterator iter = lstApprovals.iterator(); iter.hasNext();) 
			{
				ECRMMigrationDto element = (ECRMMigrationDto) iter.next();
				
				csIOMS.setString(1, element.getTaskId());
				csIOMS.setString(2, element.getTaskStatusId());
				csIOMS.setString(3, element.getOwnerId());
				csIOMS.setString(4, element.getPlannedStartDate());
				csIOMS.setString(5, element.getCrmOrderId());
				csIOMS.setString(6, element.getReasonCode());
				csIOMS.setString(7, element.getActualEndDate());
				csIOMS.setString(8, element.getCreatedBy());
				csIOMS.setInt(9, 0);
				csIOMS.setInt(10, 0);
				csIOMS.setString(11, "");
				csIOMS.execute();
				
			}
			 

			if (csIOMS.getInt(10) == 0) {
				iomsConn.commit();
				//SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");									
				//UpdateIOMSDataTracker("insert","TM_ACCOUNTROLEDETAILS",sdf.format(new Date()),sdf.format(new Date()));
				isInserted = true;
				System.err.println("Approval Details Updated....");
			} else {
				System.err.println("Approval Details Not Updated....");
				System.err.println(csIOMS.getString(10));
				iomsConn.rollback();
				isInserted = false;
			}
			
			}

				// Long ServiceSegment = null;
			System.out.println("Approval Details Inserted");
			} catch (Exception e) {
			System.out.println("Error in method Approval()"
					+ e.getStackTrace());
			e.printStackTrace();
		} finally {
			try {
				DbConnection.closeResultset(rset);
				DbConnection.closePreparedStatement(pstmt);
				DbConnection.closeCallableStatement(csIOMS);
				DbConnection.freeConnection(crmcon);
				DbConnection.freeConnection(iomsConn);
			} catch (Exception e) {
				System.out.println("exeption due to : " + e.getMessage());
			}
		}
	}
	
	public static void InsertECRMM6TaskHistorytoIOMS()//INSERT INTO IOE.TM6_RESPONSE_HISTORY
	{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		DBConnectionRetriever con = new DBConnectionRetriever();
		Connection crmcon = con.getCRMConnection();
		
		Connection iomsConn = null;
		CallableStatement csIOMS = null;
		boolean isInserted = false;
		try {
			System.out.println("Connect with IOMS database");
			iomsConn = getConnectionObject();
			System.out.println("query ::" + strGetECRMM6TaskHistoryFromIOMS);
			pstmt = crmcon.prepareStatement(strGetECRMM6TaskHistoryFromIOMS);
			//pstmt.setString(1,insertDate);
			rset = pstmt.executeQuery();
			int i = 0;
			ArrayList lstM6Task = new ArrayList();
			ECRMMigrationDto objDto = null; 
			while (rset.next()) 
			{
				objDto = new ECRMMigrationDto();
				objDto.setCrmOrderId(rset.getString("CRM_ORDER_ID"));
				objDto.setServiceNo(rset.getString("SERVICE_LIST_ID"));
				objDto.setEventTypeId(rset.getString("EVENT_TYPE_ID"));
				objDto.setCancelReason(rset.getString("CANCEL_REASON"));
				objDto.setCreationDate1(rset.getString("CREATION_DATE"));
				lstM6Task.add(objDto);
				
			}
			
			System.err.println("Inside Function....");
			if(lstM6Task.size()>0)
			{
			System.err.println("Employee Fetched And Stored In ArrayList");	
			iomsConn.setAutoCommit(false);
			csIOMS = iomsConn.prepareCall(strInsertECRMM6TaskHistoryToIOMS);
						
			
			for (Iterator iter = lstM6Task.iterator(); iter.hasNext();) 
			{
				ECRMMigrationDto element = (ECRMMigrationDto) iter.next();
				
				csIOMS.setString(1, element.getCrmOrderId());
				csIOMS.setString(2, element.getServiceNo());
				csIOMS.setString(3, element.getEventTypeId());
				csIOMS.setString(4, element.getCancelReason());
				csIOMS.setString(5, element.getCreationDate1());
				csIOMS.setInt(6, 0);
				csIOMS.setInt(7, 0);
				csIOMS.setString(8, "");
				csIOMS.execute();
				
			}
			 

			if (csIOMS.getInt(7) == 0) {
				iomsConn.commit();
				//SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");									
				//UpdateIOMSDataTracker("insert","TM_ACCOUNTROLEDETAILS",sdf.format(new Date()),sdf.format(new Date()));
				isInserted = true;
				System.err.println("M6 Task History Details Updated....");
			} else {
				System.err.println("M6 Task History Details Not Updated....");
				System.err.println(csIOMS.getString(7));
				iomsConn.rollback();
				isInserted = false;
			}
			
			}

				// Long ServiceSegment = null;
			System.out.println("M6 Task History Details Inserted");
			} catch (Exception e) {
			System.out.println("Error in method M6 Task History"
					+ e.getStackTrace());
			e.printStackTrace();
		} finally {
			try {
				DbConnection.closeResultset(rset);
				DbConnection.closePreparedStatement(pstmt);
				DbConnection.closeCallableStatement(csIOMS);
				DbConnection.freeConnection(crmcon);
				DbConnection.freeConnection(iomsConn);
			} catch (Exception e) {
				System.out.println("exeption due to : " + e.getMessage());
			}
		}
	}
	
	public static void InsertECRMFXAccountCreationtoIOMS()//INSERT INTO IOE.TFX_ACCOUNTCREATE
	{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		DBConnectionRetriever con = new DBConnectionRetriever();
		Connection crmcon = con.getCRMConnection();
		
		Connection iomsConn = null;
		CallableStatement csIOMS = null;
		boolean isInserted = false;
		try {
			System.out.println("Connect with IOMS database");
			iomsConn = getConnectionObject();
			System.out.println("query ::" + strGetECRMFXAccountCreateFromIOMS);
			pstmt = crmcon.prepareStatement(strGetECRMFXAccountCreateFromIOMS);
			//pstmt.setString(1,insertDate);
			rset = pstmt.executeQuery();
			int i = 0;
			ArrayList lstM6Task = new ArrayList();
			ECRMMigrationDto objDto = null; 
			while (rset.next()) 
			{
				objDto = new ECRMMigrationDto();
				objDto.setCrmOrderId(rset.getString("CRM_ORDER_ID"));
				objDto.setLegalEntityCode(rset.getString("LEGAL_ENTITY"));
				lstM6Task.add(objDto);
				
			}
			
			System.err.println("Inside Function....");
			if(lstM6Task.size()>0)
			{
			System.err.println("Employee Fetched And Stored In ArrayList");	
			iomsConn.setAutoCommit(false);
			csIOMS = iomsConn.prepareCall(strInsertECRMFXAccountToCreate);
						
			
			for (Iterator iter = lstM6Task.iterator(); iter.hasNext();) 
			{
				ECRMMigrationDto element = (ECRMMigrationDto) iter.next();
				
				csIOMS.setString(1, element.getLegalEntityCode());
				csIOMS.setString(2, element.getCrmOrderId());
				csIOMS.setInt(3, 0);
				csIOMS.setInt(4, 0);
				csIOMS.setString(5, "");
				csIOMS.execute();
				
			}
			 

			if (csIOMS.getInt(4) == 0) {
				iomsConn.commit();
				//SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");									
				//UpdateIOMSDataTracker("insert","TM_ACCOUNTROLEDETAILS",sdf.format(new Date()),sdf.format(new Date()));
				isInserted = true;
				System.err.println("FX Account Details Updated....");
			} else {
				System.err.println("FX Account Details Not Updated....");
				System.err.println(csIOMS.getString(4));
				iomsConn.rollback();
				isInserted = false;
			}
			
			}

				// Long ServiceSegment = null;
			System.out.println("FX Account Details Inserted");
			} catch (Exception e) {
			System.out.println("Error in method FX Account"
					+ e.getStackTrace());
			e.printStackTrace();
		} finally {
			try {
				DbConnection.closeResultset(rset);
				DbConnection.closePreparedStatement(pstmt);
				DbConnection.closeCallableStatement(csIOMS);
				DbConnection.freeConnection(crmcon);
				DbConnection.freeConnection(iomsConn);
			} catch (Exception e) {
				System.out.println("exeption due to : " + e.getMessage());
			}
		}
	}
	
	public static void InsertECRMFXServiceCreationtoIOMS()//INSERT INTO IOE.TFX_SERVICECREATE
	{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		DBConnectionRetriever con = new DBConnectionRetriever();
		Connection crmcon = con.getCRMConnection();
		
		Connection iomsConn = null;
		CallableStatement csIOMS = null;
		boolean isInserted = false;
		try {
			System.out.println("Connect with IOMS database");
			iomsConn = getConnectionObject();
			System.out.println("query ::" + strGetECRMFXServiceCreateFromIOMS);
			pstmt = crmcon.prepareStatement(strGetECRMFXServiceCreateFromIOMS);
			//pstmt.setString(1,insertDate);
			rset = pstmt.executeQuery();
			int i = 0;
			ArrayList lstM6Task = new ArrayList();
			ECRMMigrationDto objDto = null; 
			while (rset.next()) 
			{
				objDto = new ECRMMigrationDto();
				objDto.setSubScrNo(rset.getString("SERVICE_INTERNAL_ID"));
				objDto.setSubScrNoReset(rset.getString("SERVICE_INTER_ID_RESET"));
				objDto.setLastUpdatedDate1(rset.getString("UPDATED_ON"));
				objDto.setCreationDate1(rset.getString("CREATED_ON"));
				objDto.setOrderLineId(rset.getString("ORDER_LINE_ID"));
				objDto.setServiceNo(rset.getString("SERVICE_LIST_ID"));
				objDto.setAccExternalId(rset.getString("LEGAL_ENTITY"));
				objDto.setTokenId(rset.getString("FX_TOKEN_NO"));
				lstM6Task.add(objDto);
				
			}
			
			System.err.println("Inside Function....");
			if(lstM6Task.size()>0)
			{
			System.err.println("Employee Fetched And Stored In ArrayList");	
			iomsConn.setAutoCommit(false);
			csIOMS = iomsConn.prepareCall(strInsertECRMFXServiceCreateToIOMS);
						
			
			for (Iterator iter = lstM6Task.iterator(); iter.hasNext();) 
			{
				ECRMMigrationDto element = (ECRMMigrationDto) iter.next();
				
				csIOMS.setString(1, element.getSubScrNo());
				csIOMS.setString(2, element.getSubScrNoReset());
				csIOMS.setString(3, element.getLastUpdatedDate1());
				csIOMS.setString(4, element.getCreationDate1());
				csIOMS.setString(5, element.getOrderLineId());
				csIOMS.setString(6, element.getServiceNo());
				csIOMS.setString(7, element.getAccExternalId());
				csIOMS.setString(8, element.getTokenId());
				csIOMS.setInt(9, 0);
				csIOMS.setInt(10, 0);
				csIOMS.setString(11, "");
				csIOMS.execute();
				
			}
			 

			if (csIOMS.getInt(10) == 0) {
				iomsConn.commit();
				//SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");									
				//UpdateIOMSDataTracker("insert","TM_ACCOUNTROLEDETAILS",sdf.format(new Date()),sdf.format(new Date()));
				isInserted = true;
				System.err.println("FX Service Details Updated....");
			} else {
				System.err.println("FX Service Details Not Updated....");
				System.err.println(csIOMS.getString(10));
				iomsConn.rollback();
				isInserted = false;
			}
			
			}

				// Long ServiceSegment = null;
			System.out.println("FX Service Details Inserted");
			} catch (Exception e) {
			System.out.println("Error in method FX Service"
					+ e.getStackTrace());
			e.printStackTrace();
		} finally {
			try {
				DbConnection.closeResultset(rset);
				DbConnection.closePreparedStatement(pstmt);
				DbConnection.closeCallableStatement(csIOMS);
				DbConnection.freeConnection(crmcon);
				DbConnection.freeConnection(iomsConn);
			} catch (Exception e) {
				System.out.println("exeption due to : " + e.getMessage());
			}
		}
	}
	
	public static void InsertECRMFXRCCreationtoIOMS()//INSERT INTO IOE.TFX_RC_CREATE
	{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		DBConnectionRetriever con = new DBConnectionRetriever();
		Connection crmcon = con.getCRMConnection();
		
		Connection iomsConn = null;
		CallableStatement csIOMS = null;
		boolean isInserted = false;
		try {
			System.out.println("Connect with IOMS database");
			iomsConn = getConnectionObject();
			System.out.println("query ::" + strGetECRMFXRCCreateFromIOMS);
			pstmt = crmcon.prepareStatement(strGetECRMFXRCCreateFromIOMS);
			//pstmt.setString(1,insertDate);
			rset = pstmt.executeQuery();
			int i = 0;
			ArrayList lstM6Task = new ArrayList();
			ECRMMigrationDto objDto = null; 
			while (rset.next()) 
			{
				objDto = new ECRMMigrationDto();
				objDto.setCrmOrderId(rset.getString("CRM_ORDER_ID"));
				objDto.setOrderLineId(rset.getString("ORDER_LINE_ID"));
				objDto.setChargeId(rset.getString("PK_CHARGES_ID"));
				objDto.setViewId(rset.getString("SERVICE_VIEW_ID"));
				objDto.setLegalEntityCode(rset.getString("LEGAL_ENTITY"));
				objDto.setLastUpdatedDate1(rset.getString("UPDATED_ON"));
				objDto.setCreationDate1(rset.getString("CREATED_ON"));
				lstM6Task.add(objDto);
				
			}
			
			System.err.println("Inside Function....");
			if(lstM6Task.size()>0)
			{
			System.err.println("Employee Fetched And Stored In ArrayList");	
			iomsConn.setAutoCommit(false);
			csIOMS = iomsConn.prepareCall(strInsertECRMFXRCCreateToIOMS);
						
			
			for (Iterator iter = lstM6Task.iterator(); iter.hasNext();) 
			{
				ECRMMigrationDto element = (ECRMMigrationDto) iter.next();
				
				csIOMS.setString(1, element.getCrmOrderId());
				csIOMS.setString(2, element.getOrderLineId());
				csIOMS.setString(3, element.getChargeId());
				csIOMS.setString(4, element.getViewId());
				csIOMS.setString(5, element.getLegalEntityCode());
				csIOMS.setString(6, element.getLastUpdatedDate1());
				csIOMS.setString(7, element.getCreationDate1());
				csIOMS.setInt(8, 0);
				csIOMS.setInt(9, 0);
				csIOMS.setString(10, "");
				csIOMS.execute();
				
			}
			 

			if (csIOMS.getInt(9) == 0) {
				iomsConn.commit();
				//SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");									
				//UpdateIOMSDataTracker("insert","TM_ACCOUNTROLEDETAILS",sdf.format(new Date()),sdf.format(new Date()));
				isInserted = true;
				System.err.println("RC Details Updated....");
			} else {
				System.err.println("RC Details Not Updated....");
				System.err.println(csIOMS.getString(9));
				iomsConn.rollback();
				isInserted = false;
			}
			
			}

				// Long ServiceSegment = null;
			System.out.println("RC Details Inserted");
			} catch (Exception e) {
			System.out.println("Error in method RC"
					+ e.getStackTrace());
			e.printStackTrace();
		} finally {
			try {
				DbConnection.closeResultset(rset);
				DbConnection.closePreparedStatement(pstmt);
				DbConnection.closeCallableStatement(csIOMS);
				DbConnection.freeConnection(crmcon);
				DbConnection.freeConnection(iomsConn);
			} catch (Exception e) {
				System.out.println("exeption due to : " + e.getMessage());
			}
		}
	}
	
	public static void InsertECRMFXNRCCreationtoIOMS()//INSERT INTO IOE.TFX_NRC_CREATE
	{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		DBConnectionRetriever con = new DBConnectionRetriever();
		Connection crmcon = con.getCRMConnection();
		
		Connection iomsConn = null;
		CallableStatement csIOMS = null;
		boolean isInserted = false;
		try {
			System.out.println("Connect with IOMS database");
			iomsConn = getConnectionObject();
			System.out.println("query ::" + strGetECRMFXNRCCreateFromIOMS);
			pstmt = crmcon.prepareStatement(strGetECRMFXNRCCreateFromIOMS);
			//pstmt.setString(1,insertDate);
			rset = pstmt.executeQuery();
			int i = 0;
			ArrayList lstM6Task = new ArrayList();
			ECRMMigrationDto objDto = null; 
			while (rset.next()) 
			{
				objDto = new ECRMMigrationDto();
				objDto.setOrderLineId(rset.getString("ORDER_LINE_ID"));
				objDto.setChargeId(rset.getString("PK_CHARGES_ID"));
				objDto.setViewId(rset.getString("SERVICE_VIEW_ID"));
				objDto.setLegalEntityCode(rset.getString("LEGAL_ENTITY"));
				objDto.setLastUpdatedDate1(rset.getString("UPDATED_ON"));
				objDto.setCreationDate1(rset.getString("CREATED_ON"));
				lstM6Task.add(objDto);
				
			}
			
			System.err.println("Inside Function....");
			if(lstM6Task.size()>0)
			{
			System.err.println("Employee Fetched And Stored In ArrayList");	
			iomsConn.setAutoCommit(false);
			csIOMS = iomsConn.prepareCall(strInsertECRMFXNRCCreateToIOMS);
						
			
			for (Iterator iter = lstM6Task.iterator(); iter.hasNext();) 
			{
				ECRMMigrationDto element = (ECRMMigrationDto) iter.next();
				
				csIOMS.setString(1, element.getOrderLineId());
				csIOMS.setString(2, element.getChargeId());
				csIOMS.setString(3, element.getViewId());
				csIOMS.setString(4, element.getLegalEntityCode());
				csIOMS.setString(5, element.getLastUpdatedDate1());
				csIOMS.setString(6, element.getCreationDate1());
				csIOMS.setInt(7, 0);
				csIOMS.setInt(8, 0);
				csIOMS.setString(9, "");
				csIOMS.execute();
				
			}
			 

			if (csIOMS.getInt(8) == 0) {
				iomsConn.commit();
				//SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");									
				//UpdateIOMSDataTracker("insert","TM_ACCOUNTROLEDETAILS",sdf.format(new Date()),sdf.format(new Date()));
				isInserted = true;
				System.err.println("NRC Details Updated....");
			} else {
				System.err.println("NRC Details Not Updated....");
				System.err.println(csIOMS.getString(8));
				iomsConn.rollback();
				isInserted = false;
			}
			
			}

				// Long ServiceSegment = null;
			System.out.println("NRC Details Inserted");
			} catch (Exception e) {
			System.out.println("Error in method NRC"
					+ e.getStackTrace());
			e.printStackTrace();
		} finally {
			try {
				DbConnection.closeResultset(rset);
				DbConnection.closePreparedStatement(pstmt);
				DbConnection.closeCallableStatement(csIOMS);
				DbConnection.freeConnection(crmcon);
				DbConnection.freeConnection(iomsConn);
			} catch (Exception e) {
				System.out.println("exeption due to : " + e.getMessage());
			}
		}
	}
	
	public static Connection getConnectionObject() throws Exception {
		Connection conn = null;
		try {
			DriverManager.registerDriver(new com.ibm.db2.jcc.DB2Driver());
			conn = DriverManager.getConnection("jdbc:db2://10.24.62.159:50000/IOES_159", "db2admin","password");
			//conn = getURL();
			
			// System.err.println("conn2"+conn);
		} catch (Exception e) {
			e.printStackTrace();
			throw (Exception) e;
		}
		return conn;
	}
	
	public static void main(String args[]) {
		System.out.println("Starting Procesing");
		//int sucessCount = 0;
		//int failurecount = 0;
		DBConnectionRetriever con = new DBConnectionRetriever();
		Connection crmcon = con.getCRMConnection();
		
		System.err.println("Order Insertion....Started");
		InsertECRMOrderHeadertoIOMS();
		
		System.err.println("Order Attributes Insertion....Started");
		InsertECRMHeaderAttributesInfotoIOMS();
	
		System.err.println("Order Contact Insertion....Started");
		InsertECRMContacttoIOMS();  

		System.err.println("Contact Address Insertion....Started");
		InsertECRMAddresstoIOMS();

		System.err.println("PO Details Insertion....Started");
		InsertECRMPODetailstoIOMS();

		System.err.println("Service Details Insertion....Started");//Need to test
		InsertECRMServicetoIOMS();
		
		System.err.println("Service Attribute Insertion....Started");
		InsertECRMServiceAttributestoIOMS();
		
		System.err.println("Order Line Insertion....Started");
		InsertECRMOrderLinetoIOMS();
		
		System.err.println("Order Line Attributes Insertion....Started");
		InsertECRMLineAttributestoIOMS();
		
		System.err.println("Billing Info Insertion....Started");
		InsertECRMChargesHeaderBillingInfotoIOMS();
		
		System.err.println("Location Info Insertion....Started");
		InsertECRMLocationInfotoIOMS();
		
		System.err.println("Hardware Info Insertion....Started");
		InsertECRMHardwareInfotoIOMS();
		
		System.err.println("Charges Info Insertion....Started");
		InsertECRMChargestoIOMS();
		
		System.err.println("Approval Workflow Details Insertion....Started");
		InsertECRMApprovalWorkFlowtoIOMS();
		
		System.err.println("Approval Details Insertion....Started");
		InsertECRMApprovalsInfotoIOMS();
				
		System.err.println("M6 Task History Details Insertion....Started");
		InsertECRMM6TaskHistorytoIOMS();
		
		System.err.println("FX Account Creation Details Insertion....Started");
		InsertECRMFXAccountCreationtoIOMS();
		
		System.err.println("FX Service Creation Details Insertion....Started");
		InsertECRMFXServiceCreationtoIOMS();
		
		System.err.println("FX RC Creation Details Insertion....Started");
		InsertECRMFXRCCreationtoIOMS();
		
		System.err.println("FX NRC Creation Details Insertion....Started");
		InsertECRMFXNRCCreationtoIOMS();
		
	}
	
}
