
/*
 *
 * Copyright (c) 2011-2013 IBM, Inc. All Rights Reserved.
 * ===================================================================================================================================================================
 * Procedure:spInsertORDEXTATTtoIOMS 
 * Purpose:Inserting parsed data to temporary OrderExtendedAttribute table[MAIN TAB]
 * 
 * Procedure:spInsertCONTACTtoIOMS
 * Purpose:Inserting parsed data to temporary Contact table[CONTACT TAB]
 * 
 * Procedure:spInsertPODETAILtoIOMS
 * Purpose:Inserting parsed data to temporary PO Details table[PODETAIL TAB]
 * 
 * Procedure:spInsertSERVICETABtoIOMS
 * Purpose:Inserting parsed data to temporary Service Details table[LINE TAB]
 * 
 * Procedure:spInsertPRODUCTDETAILStoIOMS
 * Purpose:Inserting parsed data to temporary Product Details table[PRODUCT CATELOG TAB]
 * 
 * Procedure:spInsertCHARGESINFOtoIOMS
 * Purpose:Inserting parsed data to temporary Charge Details table[CHARGE TAB]
 * 
 * Procedure:spInsertBILLINGTRIGGERINFOtoIOMS
 * Purpose:Inserting parsed data to temporary BillingTrigger Details table[BILLING TRIGGER TAB]
 * 
 * Procedure:spInsertPROCESSFILEINFOtoIOMS
 * Purpose:Inserting unique xml fileid details to table where filename,sendmessageid,receivemesageid and error xml,success xml stored
 * 
 * Procedure:spInsertVALIDATENEWORDERtoIOMS
 * Purpose:To validate data type,email,date and time of parsed data from temprary tables and stored result xml file to process(spInsertPROCESSFILEINFOtoIOMS) table
 * 
 * Procedure:spInsertPROCESSNEWORDERtoIOMS
 * Purpose:To process successful validated file for creating order input data from temporary tables, and stored result to process(spInsertPROCESSFILEINFOtoIOMS) table
 * 
 * Procedure:sqlSpValidatePODetail,sqlGetValidateFeild_Sass and sqlSpValidatePO
 * Purpose:To validate PO after success full created order[these procedures are existing iB2b Procedures not created in CLEP]
 * 
 * Procedure:sqlGet_TaskListOfOrder
 * Purpose:To get task id after successfull validated PO for starting and close workflow[this procedure is existing iB2b Procedure not created in CLEP]
 * 
 * Procedure:sqlSpInsertTaskAction
 * Purpose:To start and close workflow[this procedure is existing iB2b Procedure not created in CLEP]
 * 
 * 
=======================================================================================================================================================================
*/
package com.ibm.ioes.clep;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import com.ibm.ioes.beans.BillingTriggerValidation;
import com.ibm.ioes.beans.ViewOrderDto;
import com.ibm.ioes.dao.NewOrderDao;
import com.ibm.ioes.dao.ViewOrderDao;
import com.ibm.ioes.ecrm.FetchAccountFromCRM;
import com.ibm.ioes.ei.MessageHandler;
import com.ibm.ioes.exception.IOESException;
import com.ibm.ioes.forms.NewOrderDto;
import com.ibm.ioes.forms.OrderHeaderDTO;
import com.ibm.ioes.forms.PoDetailsDTO;
import com.ibm.ioes.utilities.AjaxValidation;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Utility;
/*
 * =================================================================================================================
 * CODE	 AUTHOR			CBR						DATE						REQUIREMENT
 * [001] ANIL KUMAR		20110428-XX-06272		08-SEPT-2011				CREATING ORDER USING CLOUD MACHENISM
 * [002] VIJAY PATHAK	20130716-IT-09161		26-AUG-2013					REAL TIME PROVISIONING
 * [003] raveendra      20150403-R1-021203      05-May-2015                 Online Payment fix
 * =================================================================================================================
*/
public class ParseXMLforCLEP {

	private static final Logger logger = Logger.getLogger(CLEPListener.class);
	//################################################################################################################################################################
	public static String spInsertORDEXTATTtoIOMS = "{call IOE.SPCLEP_INSERT_TEMPORDEXTATT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	public static String spInsertCONTACTtoIOMS = "{call IOE.SPCLEP_INSERT_TEMPCONTACT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	public static String spInsertPODETAILtoIOMS = "{call IOE.SPCLEP_INSERT_TEMPPODETAILS(?,?,?,?,?,?,?,?,?,?,?,?)}";
	public static String spInsertSERVICETABtoIOMS = "{call IOE.SPCLEP_INSERT_TEMPSERVICEDETAILS(?,?,?,?,?,?,?,?,?)}";
	public static String spInsertPRODUCTDETAILStoIOMS = "{call IOE.SPCLEP_INSERT_TEMPPRODUCTDETAILS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";	
	public static String spInsertCHARGESINFOtoIOMS = "{call IOE.SPCLEP_INSERT_TEMPCHARGESINFO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	public static String spInsertBILLINGTRIGGERINFOtoIOMS = "{call IOE.SPCLEP_INSERT_TEMPBILLTRIGGER(?,?,?,?,?,?)}";
	public static String spInsertPROCESSFILEINFOtoIOMS = "{call IOE.SP_MPPINSERT_PROCESSED_FILEINFO(?,?,?,?,?,?,?,?,?,?)}";
	public static String spInsertPROCESSFILEINFO_RFBTtoIOMS = "{call IOE.SP_MPPINSERT_PROCESSED_FILEINFORFBT(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	public static String spInsertVALIDATENEWORDERtoIOMS = "{call IOE.SPMPP_NEW_DATATYPEVALIDATE(?,?,?,?,?,?,?,?)}";
	public static String spInsertPROCESSNEWORDERtoIOMS = "{call IOE.SPCLEP_PROCESS_NEWORDER(?,?,?,?,?,?,?,?,?)}";	
	//################################################################################################################################################################
										//VALIDATE AND WORKFLOW CLOSING
	public static String sqlSpValidatePODetail= "{call IOE.SP_VALIDATE_PODETAIL(?)}";//To Validate PO and update Status
	public static String sqlSpValidatePO= "{call IOE.SPUPDATEPOSTATUS(?,?,?,?,?)}";//To Validate PO and update Status
	public static String sqlGet_TaskListOfOrder = "{CALL IOE.SPGETTASKWORKFLOWDETAILS(?)}";
	public static String sqlSpInsertTaskAction= "{call IOE.SP_INSERT_TASK_ACTION(?,?,?,?,?,?,?,?,?,?)}";//To Insert Task Notes
	public static String sqlGetValidateFeild_Sass="{call IOE.SASS_FEILD_VALDATION(?,?,?,?,?)}";
	//################################################################################################################################################################
										//PARTIAL PUBLISH AND PUBLISHING
	public static String sqlGetValidateFeild="{call IOE.ROLEWISE_VALIDATE_FEILDS(?,?,?,?,?,?)}";
	public static String sqlspGetNoNM6ServiceListForOrder = "{call IOE.SPCLEP_GET_NONM6SERVICELIST(?)}";
	public static String sqlinsertViewPartialPublish = "{call IOE.SP_UPDATE_PARTIALPUBLISH(?,?,?,?,?,?,?)}";	
	//################################################################################################################################################################
										//BILLING TRIGGER
	public static String spUpdateLocNoLocDateBillinTrgDate="{call IOE.SPCLEP_UPDATE_LOCNO_DATEBILLTRG(?,?,?,?,?)}";
	public static String spGetLocNoLocDateBillinTrgDate="{call IOE.SPCLEP_GET_LOCNO_DATEBILLTRG(?,?,?,?,?,?,?,?,?,?)}";
	public static String spGetServiceProductIds="{call IOE.SPCLEP_GETSERVICEPRODUCTIDS(?)}";
	//################################################################################################################################################################	
	public static String spInsertNEWRequestForBillingTrigger="{call IOE.SPCLEP_INSERT_TEMPREQUESTFORBILLTRIGGER(?,?,?,?,?,?,?,?,?,?,?,?)}";
	public static String spInsertVALIDATERFBTtoIOMS = "{call IOE.SPMPP_RFBT_DATATYPEVALIDATE(?,?,?,?,?,?,?,?)}";	
	//################################################################################################################################################################
		
/*Function :fetchNextXMLDataNewOrder
 * Purpose :To Parse XML data and Processing for NewOrder and ChangeOrder from EAI
 * Created By : Anil Kumar
 * Date :21-Sept-2011
 * */		
	
	public static int fetchNextXMLDataNewOrder(String fileName,CLEPXmlDto clepXmlDto) throws Exception {
		int returnStatus=0;
		int isProcess=0;
		long xmlfileid=0l;
		int isComponent=0;
		int isSendToM6=0;
		int isSendToM6ValueFromDB=-1;
		int isValidServicesWithM6NorM6=-1;
		String orderType=null;
		String accountId=null;
		String projectMngrId=null;
		String empId=null;	
		String jmsMessageID=null;
		String missingAttribute="";
		int taglength=0;
		int insertFlag=-1;
		Connection conn=null;
		CallableStatement csIOMS = null;	
		CallableStatement csIOMSCharges = null;	
		String serviceTypeID="";
		String isWithWorkflow="True";
		CallableStatement csProcessFileInfoInsert=null;
		CallableStatement cstmt=null;
		//vijay 
		/*-start define a variable to contain uniqOrderId-*/
		String uniqOrderId="";
		
		/*-start define a variable to contain workflow id -*/
		String attachedWorkflowId=null;  
		//vijay end
			try{
				
				conn= DbConnection.getConnectionObject();											
				conn.setAutoCommit(false);
				/*----------- Generating Unique FileProcessing Id for each XML -----------*/
				xmlfileid=getXMLNextFileId(conn);	
				/*------------------------------------------------------------------------*/
				/*----------- Set Request MessageId for Correlation Message Id -----------*/
				jmsMessageID=clepXmlDto.getJmsMessageID();
				/*------------------------------------------------------------------------*/
				String filePath=Utility.getAppConfigValue("CLEP_RESPONSE_PATH");				
				String absFileName=filePath+fileName;
				
				CLEPUtility.SysErr("Reading and Parsing "+absFileName+" file with FILEID "+xmlfileid+" >>>>>>>>>>>");
								
				File file = new File(absFileName);
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document doc = db.parse(file);
				doc.getDocumentElement().normalize();
				//===================================ORDER TYPE==============================================
				if(doc.getElementsByTagName("OrderType").item(0)!=null)
				{
					NodeList nlListOrderType = doc.getElementsByTagName("OrderType").item(0).getChildNodes();					
					Node nValueOrdType = (Node) nlListOrderType.item(0);
					if(nValueOrdType!=null){
						orderType= nValueOrdType.getNodeValue().trim();
					}
		        
				CLEPUtility.SysErr("**************Order Type is:>"+orderType+"**************");
				if(orderType !=null){
		        if(("NEW".equalsIgnoreCase(orderType))||("DISCONNECTION".equalsIgnoreCase(orderType))||("SOLUTIONCHANGE".equalsIgnoreCase(orderType))||("RFBT".equalsIgnoreCase(orderType))){
		        if("NEW".equalsIgnoreCase(orderType)){
		        //==============================NEW ORDER OPEN===============================================
				//==================================MAIN TAB=================================================
		        missingAttribute="OrderExtAttributes";
				NodeList parentNodeList = doc.getElementsByTagName("OrderExtAttributes");
				csIOMS=conn.prepareCall(spInsertORDEXTATTtoIOMS);									 
				Node childNodeOrderExtAtt = parentNodeList.item(0);
								
				CLEPUtility.SysErr("**************************Reading and Inserting OrderExtAttributes********************************");			
				
				if (childNodeOrderExtAtt.getNodeType() == Node.ELEMENT_NODE)
				{
						 Element eOrdExtAttributeElement = (Element) childNodeOrderExtAtt;
						 
						 csIOMS.setLong(1, xmlfileid);		
						 missingAttribute="UniqueOrderID[Hint: should be UniqueOrderID]";
						 if(getTagValue("UniqueOrderID", eOrdExtAttributeElement)==null){
							 missingAttribute=" UniqueOrderID can not left blank ";
							 throw new IOESException("------ Error:    UniqueOrderID can not left blank! -----------");
						 }
						 /*---Vijay. start the validating for 
						  * attribute2 (that is workflow id)----*/ 
						 
						 else if(getTagValue("Attribute2", eOrdExtAttributeElement)==null 
								 	&& "True".equalsIgnoreCase(getTagValue("Attribute1", eOrdExtAttributeElement))){
							 
							 missingAttribute=" Attribute2 can not left blank ";
							 throw new IOESException("------ Error:    Attribute2 can not left blank! -----------");
						 }
						 
						 else if( "True".equalsIgnoreCase(getTagValue("Attribute1", eOrdExtAttributeElement)) && (!validationForAttribute2(getTagValue("Attribute2", eOrdExtAttributeElement))) ){
							 /*if validation return false 
							  * that means workflow id is not valid */
							 
							 missingAttribute=" Attribute2 is not valid workflow id ";
							 throw new IOESException("------ Error:    Attribute2 is not valid workflow id! -----------");	 
							 
							 /*Vijay. end of validating the workflow id */
						 }else{
							 csIOMS.setString(2,getTagValue("UniqueOrderID", eOrdExtAttributeElement));
							 
							 //Vijay starting assigning uniqueOrderId
							 uniqOrderId=getTagValue("UniqueOrderID", eOrdExtAttributeElement);
							 //vijay end
							 missingAttribute="OrderForProduct[Hint: should be OrderForProduct]";
							 csIOMS.setString(3,getTagValue("OrderForProduct", eOrdExtAttributeElement));
							 missingAttribute="Attribute1[Hint: should be Attribute1]";
							 csIOMS.setString(4,getTagValue("Attribute1", eOrdExtAttributeElement));
							 isWithWorkflow=getTagValue("Attribute1", eOrdExtAttributeElement);
							 missingAttribute="Attribute2[Hint: should be Attribute2]";
							 csIOMS.setString(5,getTagValue("Attribute2", eOrdExtAttributeElement));
							 
							 /*Vijay assigning the workflow id 
							  * for interting workflow*/
							 
							 attachedWorkflowId = getTagValue("Attribute2", eOrdExtAttributeElement);
							 //vijay end
							 missingAttribute="Attribute3[Hint: should be Attribute3]";
							 csIOMS.setString(6,getTagValue("Attribute3", eOrdExtAttributeElement));
							 
							 missingAttribute="CRMAccountNo[Hint: should be CRMAccountNo]";
							 csIOMS.setString(7,getTagValue("CRMAccountNo", eOrdExtAttributeElement));	
							 accountId=getTagValue("CRMAccountNo", eOrdExtAttributeElement);
							 
							 String status=FetchAccountFromCRM.fetchingAccountBCPFromCRMForCLEP(accountId);
							 if(!"1".equalsIgnoreCase(status)){
								 missingAttribute=status;
								 throw new IOESException("------ Error: Account fetching from CRM is failed:"+missingAttribute);
							 }
							 missingAttribute="Source[Hint: should be Source]";
							 csIOMS.setString(8, getTagValue("Source", eOrdExtAttributeElement));
							 missingAttribute="Currency[Hint: should be Currency]";
							 csIOMS.setString(9, getTagValue("Currency", eOrdExtAttributeElement));
							 missingAttribute="QuoteNo[Hint: should be QuoteNo]";
							 csIOMS.setString(10, getTagValue("QuoteNo", eOrdExtAttributeElement));	
							 if(getTagValue("ProjectManager", eOrdExtAttributeElement)!=null){
								 missingAttribute="ProjectManager[Hint: should be ProjectManager]";
								 csIOMS.setString(11, getTagValue("ProjectManager", eOrdExtAttributeElement));
							 }else{
								 csIOMS.setString(11, "");
							 }
							 projectMngrId="2";//getTagValue("ProjectManager", eOrdExtAttributeElement);
							 missingAttribute="RFSDate[Hint: should be RFSDate]";
							 csIOMS.setString(12, getTagValue("RFSDate", eOrdExtAttributeElement));	
							 missingAttribute="ActMgrPhNo";
							 if(getTagValue("ActMgrPhNo", eOrdExtAttributeElement)!=null){
								 missingAttribute="ActMgrPhNo[Hint: should be ActMgrPhNo]";
								 csIOMS.setString(13, getTagValue("ActMgrPhNo", eOrdExtAttributeElement));
							 }else{
								 csIOMS.setString(13, "");
							 }
							 missingAttribute="ActMgrEmailID[Hint: should be ActMgrEmailID]";
							 if(getTagValue("ActMgrEmailID", eOrdExtAttributeElement)!=null){							 
							 if(CLEPUtility.isEmailValid(getTagValue("ActMgrEmailID", eOrdExtAttributeElement))){
								 csIOMS.setString(14, getTagValue("ActMgrEmailID", eOrdExtAttributeElement));	
							 }else{
								 csIOMS.setString(14, "-1");	
							 }		
							 }else{
								 csIOMS.setString(14, "");
							 }
							 missingAttribute="IRUOrderYN[Hint: should be IRUOrderYN]";
							 csIOMS.setString(15, getTagValue("IRUOrderYN", eOrdExtAttributeElement));
							 missingAttribute="FreePeriodYN[Hint: should be FreePeriodYN]";
							 csIOMS.setString(16, getTagValue("FreePeriodYN", eOrdExtAttributeElement));
							 missingAttribute="OrderExclTaxYN[Hint: should be OrderExclTaxYN]";
							 csIOMS.setString(17, getTagValue("OrderExclTaxYN", eOrdExtAttributeElement));
							 missingAttribute="DateCAF[Hint: should be DateCAF]";
							 csIOMS.setString(18, getTagValue("DateCAF", eOrdExtAttributeElement));
							 csIOMS.setString(19, orderType);
							 csIOMS.execute();	
						 }
						 														 					 					 																	
				//=====================================CONTACT TAB==================================================
				missingAttribute="Contacts";
				NodeList parentNodeList2 = eOrdExtAttributeElement.getElementsByTagName("Contacts");
				csIOMS=conn.prepareCall(spInsertCONTACTtoIOMS);
				taglength=parentNodeList2.getLength();
				if(taglength > 0){
				for(int i=0;i<parentNodeList2.getLength();i++)
				{					 
					 Node childNodeContact = parentNodeList2.item(i);
					 					 
					 CLEPUtility.SysErr("**************************Reading and Inserting Contacts["+(i+1)+"]*******************************");					 
					 
					 if (childNodeContact.getNodeType() == Node.ELEMENT_NODE)
					 {
						 	Element eElement = (Element) childNodeContact;
						 	
						 	csIOMS.setLong(1, xmlfileid);
						 	missingAttribute="ContactType in Contact tag";
							csIOMS.setString(2,getTagValue("ContactType", eElement));
							missingAttribute="Salutation in Contact tag";
							csIOMS.setString(3, getTagValue("Salutation", eElement));
							missingAttribute="FirstName in Contact tag";
							csIOMS.setString(4, getTagValue("FirstName", eElement));
							missingAttribute="LastName in Contact tag";
							if(getTagValue("LastName", eElement)!=null){
								csIOMS.setString(5, getTagValue("LastName", eElement));
							}else{
								csIOMS.setString(5, "");
							}
							missingAttribute="Email in Contact tag";
							if(getTagValue("Email", eElement)!=null){
								if(CLEPUtility.isEmailValid(getTagValue("Email", eElement))){
									csIOMS.setString(6, getTagValue("Email", eElement));
								}else{
									csIOMS.setString(6, "-1");
								}
							}else{
								csIOMS.setString(6, "");
							}
							missingAttribute="CellNo in Contact tag";
							csIOMS.setString(7, getTagValue("CellNo", eElement));
							missingAttribute="Fax in Contact tag";
							if(getTagValue("Fax", eElement)!=null){
								csIOMS.setString(8, getTagValue("Fax", eElement));
							}else{
								csIOMS.setString(8, "");
							}
							missingAttribute="Address1 in Contact tag";
							csIOMS.setString(9, getTagValue("Address1", eElement));
							missingAttribute="Address2 in Contact tag";
							if(getTagValue("Address2", eElement)!=null){
								csIOMS.setString(10, getTagValue("Address2", eElement));
							}else{
								csIOMS.setString(10,"");
							}
							missingAttribute="Address3 in Contact tag";
							if(getTagValue("Address3", eElement)!=null){
								csIOMS.setString(11, getTagValue("Address3", eElement));
							}else{
								csIOMS.setString(11, "");
							}
							missingAttribute="CountryID in Contact tag";
							csIOMS.setString(12, getTagValue("CountryID", eElement));
							missingAttribute="StateID in Contact tag";
							csIOMS.setString(13, getTagValue("StateID", eElement));
							missingAttribute="CityID in Contact tag";
							csIOMS.setString(14, getTagValue("CityID", eElement));
							missingAttribute="Pin in Contact tag";
							if(getTagValue("Pin", eElement)!=null){
								csIOMS.setString(15, getTagValue("Pin", eElement));
							}else{
								csIOMS.setString(15, "0");
							}
							missingAttribute="UniqueContactId in Contact tag";
							if(getTagValue("UniqueContactId", eElement)==null){
								 missingAttribute=" UniqueContactId can not left blank ";
								 throw new IOESException("------ Error:    UniqueContactId can not left blank! -----------");
							 }else{
								 	csIOMS.setString(16, getTagValue("UniqueContactId", eElement));
									missingAttribute="UniqueAddressId in Contact tag";
									csIOMS.setString(17, getTagValue("UniqueAddressId", eElement));
									csIOMS.execute();							
							 }							
					 }					 
				}//each contact		
				//=================================PODETAIL TAB==================================================
				missingAttribute="PODetails";
				NodeList parentNodeList3 = eOrdExtAttributeElement.getElementsByTagName("PODetails");
				csIOMS=conn.prepareCall(spInsertPODETAILtoIOMS);
				taglength=parentNodeList3.getLength();
				if(taglength > 0){
				for(int i=0;i<parentNodeList3.getLength();i++)
				{					 
					 Node childNodePoDetail = parentNodeList3.item(i);
					 				
					 CLEPUtility.SysErr("**************************Reading and Inserting PODetails["+(i+1)+"]*******************************");					
					 
					 if (childNodePoDetail.getNodeType() == Node.ELEMENT_NODE)
					 {
						 Element eElement = (Element) childNodePoDetail;
						 
						 	csIOMS.setLong(1, xmlfileid);
						 	missingAttribute="CustPONo in PODetails tag";
							csIOMS.setString(2,getTagValue("CustPONo", eElement));
							missingAttribute="CustPODate in PODetails tag";
							csIOMS.setString(3, getTagValue("CustPODate", eElement));
							missingAttribute="IsDefaultPO in PODetails tag";
							csIOMS.setString(4, getTagValue("IsDefaultPO", eElement));
							missingAttribute="Entity in PODetails tag";
							csIOMS.setString(5, getTagValue("Entity", eElement));
							missingAttribute="TotalPOAmount in PODetails tag";
							csIOMS.setString(6, getTagValue("TotalPOAmount", eElement));
							missingAttribute="PeriodInMonths in PODetails tag";
							csIOMS.setString(7, getTagValue("PeriodInMonths", eElement));
							missingAttribute="ContractStartDate in PODetails tag";
							if(getTagValue("ContractStartDate", eElement)!=null){
								csIOMS.setString(8, getTagValue("ContractStartDate", eElement));
							}else{
								csIOMS.setString(8,"");
							}
							missingAttribute="ContractEndDate in PODetails tag";
							if(getTagValue("ContractEndDate", eElement)!=null){
								csIOMS.setString(9, getTagValue("ContractEndDate", eElement));
							}else{
								csIOMS.setString(9,"");
							}
							missingAttribute="PORemarks in PODetails tag";
							if(getTagValue("PORemarks", eElement)!=null){
								csIOMS.setString(10, getTagValue("PORemarks", eElement));
							}else{
								csIOMS.setString(10, "");
							}
							missingAttribute="EmailID in PODetails tag";
							if(getTagValue("EmailID", eElement)!=null){
								if(CLEPUtility.isEmailValid(getTagValue("EmailID", eElement))){
									csIOMS.setString(11, getTagValue("EmailID", eElement));
								}else{
									csIOMS.setString(11, "-1");
								}
							}else{
								csIOMS.setString(11, "");
							}
							if(getTagValue("UniqePoDetailId", eElement)==null){
								 missingAttribute=" UniqePoDetailId can not left blank ";
								 throw new IOESException("------ Error:    UniqePoDetailId can not left blank! -----------");
							 }else{
								 missingAttribute="UniqePoDetailId in PODetails tag";
								 csIOMS.setString(12, getTagValue("UniqePoDetailId", eElement));
								 csIOMS.execute();
							 }
					 }					 					 							
				}//each po details
				
				//==================================LINE TAB================================================				
				missingAttribute="ServiceDetails";
				NodeList parentNodeList4 = eOrdExtAttributeElement.getElementsByTagName("ServiceDetails");		
				taglength=parentNodeList4.getLength();
				if(taglength > 0){
				outer:
				for(int i=0;i<parentNodeList4.getLength();i++)
				{					
					 Node childNodeService = parentNodeList4.item(i);
					 CLEPUtility.SysErr("**************************Reading and Inserting ServiceDetails["+(i+1)+"]*******************************");
					 
					 csIOMS=conn.prepareCall(spInsertSERVICETABtoIOMS);
					 if (childNodeService.getNodeType() == Node.ELEMENT_NODE)
					 {
						 Element eElementService = (Element) childNodeService;
						 
						 csIOMS.setLong(1, xmlfileid);
						 missingAttribute="serviceTypeId in ServiceDetails tag";
						 serviceTypeID=getTagValue("serviceTypeId", eElementService);
						 csIOMS.setString(2,getTagValue("serviceTypeId", eElementService));
						 missingAttribute="serviceTypeName in ServiceDetails tag";
						 csIOMS.setString(3, getTagValue("serviceTypeName", eElementService));
						 missingAttribute="serviceSubTypeId in ServiceDetails tag";
						 csIOMS.setString(4, getTagValue("serviceSubTypeId", eElementService));
						 missingAttribute="remarks in ServiceDetails tag";
						 if(getTagValue("remarks", eElementService)!=null){
							 csIOMS.setString(5, getTagValue("remarks", eElementService));
						 }else{
							 csIOMS.setString(5, "");
						 }
						 missingAttribute="effectiveStartDate in ServiceDetails tag";
						 csIOMS.setString(6, getTagValue("effectiveStartDate", eElementService));
						 missingAttribute="rfsDate in ServiceDetails tag";
						 csIOMS.setString(7, getTagValue("rfsDate", eElementService));
						 missingAttribute="UniqueServiceDetailId in ServiceDetails tag";
						 if(getTagValue("UniqueServiceDetailId", eElementService)==null){
							 missingAttribute=" UniqueServiceDetailId can not left blank ";
							 throw new IOESException("------ Error:    UniqueServiceDetailId can not left blank! -----------");
						 }else{
							 csIOMS.setString(8, getTagValue("UniqueServiceDetailId", eElementService));
							 missingAttribute="isSendToM6 in ServiceDetails tag";
							 csIOMS.setString(9, getTagValue("isSendToM6", eElementService));
							 csIOMS.execute();	
						 }						 
						 if(getTagValue("isSendToM6", eElementService)!=null){
							 if(CLEPUtility.isValidNONNegativeNumeric(getTagValue("isSendToM6", eElementService))){
								 isSendToM6=Integer.parseInt(getTagValue("isSendToM6", eElementService));
							 }
						 }
						 //==============================PRODUCT===================================						 						 
						 missingAttribute="productCatelog";
						 NodeList parentProductCateLogNodeList = eElementService.getElementsByTagName("productCatelog");
						 taglength=parentProductCateLogNodeList.getLength();
						 if(taglength > 0){
						 for(int j=0;j<parentProductCateLogNodeList.getLength();j++)
						 {
							 Node chileNodeProductCatelog=parentProductCateLogNodeList.item(j);
							 CLEPUtility.SysErr("**************************Reading and Inserting ProductCatelog["+(j+1)+"]*******************************");							 
							 csIOMS=conn.prepareCall(spInsertPRODUCTDETAILStoIOMS);	
							 if(chileNodeProductCatelog.getNodeType()==Node.ELEMENT_NODE)
							 {						
								 Element eElementProdCatelog=(Element)chileNodeProductCatelog;
								 	 //===========================LINE ITEM======================================								 	  
								 		String uniqueProductDetaiId=null;
								 		missingAttribute="UniqueProductId in productCatelog tag";
								 		NodeList nlListUniqProdId = eElementProdCatelog.getElementsByTagName("UniqueProductId").item(0).getChildNodes();					
								 		Node nValueUniqProdId = (Node) nlListUniqProdId.item(0);
								 		if(nValueUniqProdId!=null){
								 			uniqueProductDetaiId= nValueUniqProdId.getNodeValue().trim();
								 		}else{
								 				uniqueProductDetaiId="";
								 				missingAttribute=" UniqueProductId can not left blank ";
								 				throw new IOESException("------ Error:    UniqueProductId can not left blank! -----------");								 			
								 		}
								 	  missingAttribute="product in productCatelog tag";
								 	  NodeList parentNodeList5 = eElementProdCatelog.getElementsByTagName("product");																										
								 	  Node childNodeProduct = parentNodeList5.item(0);
								 	  CLEPUtility.SysErr("**************************Reading and Inserting product*******************************");								 	  
								 	  if (childNodeProduct.getNodeType() == Node.ELEMENT_NODE)
								 	  {
								 		  Element eElementProduct = (Element) childNodeProduct;
								 		  missingAttribute="isServiceSummarry in productCatelog tag";
								 		 int isServiceSummarry=-1;
								 		  if(getTagValue("isServiceSummarry", eElementProduct)!=null){
								 			 if(CLEPUtility.isValidNONNegativeNumeric(getTagValue("isServiceSummarry", eElementProduct))){
								 			  isServiceSummarry=Integer.parseInt(getTagValue("isServiceSummarry", eElementProduct));
								 			 }
								 		  }
								 		  missingAttribute="isComponent in productCatelog tag";
								 		  if(getTagValue("isComponent", eElementProduct)!=null){
								 			 if(CLEPUtility.isValidNONNegativeNumeric(getTagValue("isComponent", eElementProduct))){
								 			  isComponent=Integer.parseInt(getTagValue("isComponent", eElementProduct));
								 			 }
								 		  }
								 		  if(isServiceSummarry==0){	
								 			  csIOMS.setLong(1, xmlfileid);
								 			  missingAttribute="productName in productCatelog tag";
								 			  csIOMS.setString(2,getTagValue("productName", eElementProduct));
								 			  missingAttribute="serviceDetailId in productCatelog tag";
								 			  csIOMS.setString(3,getTagValue("serviceDetailId", eElementProduct));
								 			  missingAttribute="isServiceSummarry in productCatelog tag";
								 			  csIOMS.setString(4,getTagValue("isServiceSummarry", eElementProduct));
								 		  }else{
								 			  csIOMS.setLong(1, xmlfileid);
								 			  missingAttribute="productName in productCatelog tag";
								 			  csIOMS.setString(2,getTagValue("productName", eElementProduct));
								 			  missingAttribute="serviceDetailId in productCatelog tag";
								 			  csIOMS.setString(3,getTagValue("serviceDetailId", eElementProduct));
								 			  missingAttribute="isServiceSummarry in productCatelog tag";
								 			  csIOMS.setString(4,getTagValue("isServiceSummarry", eElementProduct));
								 			  
								 			  //code here for service summarry details
								 			  long serviceTypeId=0l,serviceDetailId=0l;
								 			  missingAttribute="serviceDetailId or serviceTypeId in productCatelog tag";
								 			  if(getTagValue("serviceDetailId", eElementProduct)!=null && getTagValue("serviceTypeId", eElementService)!=null){
									 			  if(CLEPUtility.isValidNONNegativeNumeric(getTagValue("serviceDetailId", eElementProduct)) && CLEPUtility.isValidNONNegativeNumeric(getTagValue("serviceTypeId", eElementService))){								 				 
									 				  serviceTypeId=Long.valueOf(getTagValue("serviceTypeId", eElementService));								 				 
									 				 serviceDetailId=Long.valueOf(getTagValue("serviceDetailId", eElementProduct));								 				 
									 			  }else{
									 				 serviceTypeId=0;serviceDetailId=0;
									 			  }
								 			  }else{
								 				 serviceTypeId=0;serviceDetailId=0;
								 			  }
								 			 isSendToM6ValueFromDB=getiS_SENDTOM6_Status(serviceTypeId,serviceDetailId,conn);								 			 
								 		  }									 
								 	  }							 
										//===========================BILLING INFO======================================
								 	  	missingAttribute="billingInfoNode in productCatelog tag";
										NodeList parentNodeList6 = eElementProdCatelog.getElementsByTagName("billingInfoNode");																			
										Node childNodebillInfo = parentNodeList6.item(0);
										CLEPUtility.SysErr("**************************Reading and Inserting billingInfoNode********************************");										
										if (childNodebillInfo.getNodeType() == Node.ELEMENT_NODE)
										{
												 Element eElementBillInfo = (Element) childNodebillInfo;
												 missingAttribute="creditPeriod in billingInfoNode tag";
												 csIOMS.setString(5,getTagValue("creditPeriod", eElementBillInfo));	
												 missingAttribute="billingMode in billingInfoNode tag";
												 csIOMS.setString(6,getTagValue("billingMode", eElementBillInfo));
												 missingAttribute="billingFormat in billingInfoNode tag";
												 csIOMS.setString(7, getTagValue("billingFormat", eElementBillInfo));
												 missingAttribute="licenseCoId in billingInfoNode tag";
												 csIOMS.setString(8, getTagValue("licenseCoId", eElementBillInfo));
												 missingAttribute="taxation in billingInfoNode tag";
												 csIOMS.setString(9, getTagValue("taxation", eElementBillInfo));
												 missingAttribute="billingLevel in billingInfoNode tag";
												 csIOMS.setString(10, getTagValue("billingLevel", eElementBillInfo));
												 missingAttribute="commitmentPeriod in billingInfoNode tag";
												 csIOMS.setString(11, getTagValue("commitmentPeriod", eElementBillInfo));	
												 missingAttribute="penaltyClause in billingInfoNode tag";
												 csIOMS.setString(12, getTagValue("penaltyClause", eElementBillInfo));
												 missingAttribute="billingType in billingInfoNode tag";
												 csIOMS.setString(13, getTagValue("billingType", eElementBillInfo));
												 missingAttribute="billingLevelNo in billingInfoNode tag";
												 csIOMS.setString(14, getTagValue("billingLevelNo", eElementBillInfo));
												 missingAttribute="bcpid in billingInfoNode tag";
												 csIOMS.setString(15, getTagValue("bcpid", eElementBillInfo));	
												 missingAttribute="noticePeriod in billingInfoNode tag";
												 csIOMS.setString(16, getTagValue("noticePeriod", eElementBillInfo));
												 missingAttribute="isnfa in billingInfoNode tag";
												 csIOMS.setString(17, getTagValue("isnfa", eElementBillInfo));
												 missingAttribute="PoDetailId in billingInfoNode tag";
												 csIOMS.setString(18, getTagValue("PoDetailId", eElementBillInfo));
										}
										//==============================SERVICE LOCATION INFO=====================================
										missingAttribute="serviceLocInfoNode in productCatelog tag";
										NodeList parentNodeList7 = eElementProdCatelog.getElementsByTagName("serviceLocInfoNode");																		 
										Node childNodeServiceLoc = parentNodeList7.item(0);
										CLEPUtility.SysErr("**************************Reading and Inserting serviceLocInfoNode********************************");										
										if (childNodeServiceLoc.getNodeType() == Node.ELEMENT_NODE)
										{
												Element eElementServiceLoc = (Element) childNodeServiceLoc;
												missingAttribute="priLocType in serviceLocInfoNode tag";
												if(getTagValue("priLocType", eElementServiceLoc)!=null){
													csIOMS.setString(19,getTagValue("priLocType", eElementServiceLoc));
												}else{
													csIOMS.setString(19,"0");
												}
												missingAttribute="priLocId in serviceLocInfoNode tag";
												if(getTagValue("priLocId", eElementServiceLoc)!=null){
													csIOMS.setString(20,getTagValue("priLocId", eElementServiceLoc));
												}else{
													csIOMS.setString(20,"0");
												}
												missingAttribute="secLocType in serviceLocInfoNode tag";
												if(getTagValue("secLocType", eElementServiceLoc)!=null){
													csIOMS.setString(21, getTagValue("secLocType", eElementServiceLoc));	
												}else{
													csIOMS.setString(21, "0");
												}
												missingAttribute="secLocId in serviceLocInfoNode tag";
												if(getTagValue("secLocId", eElementServiceLoc)!=null){
													csIOMS.setString(22, getTagValue("secLocId", eElementServiceLoc));
												}else{
													csIOMS.setString(22, "0");
												}
												missingAttribute="fromLoc in serviceLocInfoNode tag";
												if(getTagValue("fromLoc", eElementServiceLoc)!=null){
													csIOMS.setString(23, getTagValue("fromLoc", eElementServiceLoc));
												}else{
													csIOMS.setString(23, "");
												}
												missingAttribute="toLoc in serviceLocInfoNode tag";
												if(getTagValue("toLoc", eElementServiceLoc)!=null){
													csIOMS.setString(24, getTagValue("toLoc", eElementServiceLoc));
												}else{
													csIOMS.setString(24, "");
												}
												missingAttribute="UniqueServiceDetailId in ServiceDetails tag";
												if(getTagValue("UniqueServiceDetailId", eElementService)!=null){
													 if(CLEPUtility.isValidNONNegativeNumeric(getTagValue("UniqueServiceDetailId", eElementService))){
														 csIOMS.setLong(25, Long.valueOf(getTagValue("UniqueServiceDetailId", eElementService)));
													 }else{
														 missingAttribute=" UniqueServiceDetailId should be Non Negative Numeric only";
											 			throw new IOESException("------ Error:    UniqueServiceDetailId should be Non Negative Numeric only! -----------");
													 }
												}else{
													csIOMS.setLong(25, 0);
												}												 
										}	
										
										//==========================BILLING TRIGGERINFO============================================
											missingAttribute="billingTriggerInfo in productCatelog tag";
											NodeList parentNodeList8 = eElementProdCatelog.getElementsByTagName("billingTriggerInfo");	
											Node childNodeBillingTrigger = parentNodeList8.item(0);
											CLEPUtility.SysErr("**************************Reading and Inserting billingTriggerInfo********************************");										
											if (childNodeBillingTrigger.getNodeType() == Node.ELEMENT_NODE)
											{												
													Element eElementBillingTrg = (Element) childNodeBillingTrigger;
													missingAttribute="locNo in billingTriggerInfo tag";
													csIOMS.setString(26,getTagValue("locNo", eElementBillingTrg));
													missingAttribute="locDate in billingTriggerInfo tag";
													csIOMS.setString(27, getTagValue("locDate", eElementBillingTrg));
													missingAttribute="billingTriggerDate in billingTriggerInfo tag";
													csIOMS.setString(28, getTagValue("billingTriggerDate", eElementBillingTrg));	
													csIOMS.setString(29, uniqueProductDetaiId);
													csIOMS.execute();													
											}
											
										if(isComponent==1){
										//==============================COMPONENT INFO======================================
											
											// component code here
										}else{
										//==============================CHARGES INFO========================================
										missingAttribute="chargesInfoNode";
										NodeList parentNodeList9 = eElementProdCatelog.getElementsByTagName("chargesInfoNode");													
										taglength=parentNodeList9.getLength();
										if(taglength > 0){
										for(int k=0;k<parentNodeList9.getLength();k++)
										{																										
											 Node childNodeChargeInfo = parentNodeList9.item(k);
											 CLEPUtility.SysErr("**************************Reading and Inserting chargesInfoNode["+(k+1)+"]********************************");											
											 csIOMSCharges=conn.prepareCall(spInsertCHARGESINFOtoIOMS);
											 if (childNodeChargeInfo.getNodeType() == Node.ELEMENT_NODE)
											 {
												 Element eElementChargeInfo = (Element) childNodeChargeInfo;
												 missingAttribute="UniqueChargeId in chargesInfoNode";
												 String chargeAttribute=eElementChargeInfo.getAttribute("UniqueChargeId");
												 if(!("".equalsIgnoreCase(chargeAttribute))){
												 csIOMSCharges.setLong(1, xmlfileid);
												 missingAttribute="chargeType in chargesInfoNode";
												 csIOMSCharges.setString(2,getTagValue("chargeType", eElementChargeInfo));
												 missingAttribute="chargeName in chargesInfoNode";
												 csIOMSCharges.setString(3,getTagValue("chargeName", eElementChargeInfo));
												 missingAttribute="chargePeriod in chargesInfoNode";
												 csIOMSCharges.setString(4, getTagValue("chargePeriod", eElementChargeInfo));
												 missingAttribute="chargeAmount in chargesInfoNode";
												 csIOMSCharges.setString(5, getTagValue("chargeAmount", eElementChargeInfo));
												 missingAttribute="chargeFrequency in chargesInfoNode";
												 csIOMSCharges.setString(6, getTagValue("chargeFrequency", eElementChargeInfo));
												 missingAttribute="startDateLogic in chargesInfoNode";
												 csIOMSCharges.setString(7, getTagValue("startDateLogic", eElementChargeInfo));
												 missingAttribute="startDateDays in chargesInfoNode";
												 csIOMSCharges.setString(8, getTagValue("startDateDays", eElementChargeInfo));
												 missingAttribute="startDateMonth in chargesInfoNode";
												 csIOMSCharges.setString(9, getTagValue("startDateMonth", eElementChargeInfo));
												 missingAttribute="endDateLogic in chargesInfoNode";
												 csIOMSCharges.setString(10, getTagValue("endDateLogic", eElementChargeInfo));
												 missingAttribute="endDateDays in chargesInfoNode";
												 csIOMSCharges.setString(11, getTagValue("endDateDays", eElementChargeInfo));
												 missingAttribute="endDateMonth in chargesInfoNode";
												 csIOMSCharges.setString(12, getTagValue("endDateMonth", eElementChargeInfo));
												 missingAttribute="annotation in chargesInfoNode";
												 csIOMSCharges.setString(13, getTagValue("annotation", eElementChargeInfo));
												 missingAttribute="exclude in chargesInfoNode";
												 if(getTagValue("exclude", eElementChargeInfo)!=null){
													 if(getTagValue("exclude", eElementChargeInfo).length()>1){
													 	missingAttribute=" exclude should be Non Negative Numeric only and Length can not greater than one";
											 			throw new IOESException("------ Error:    exclude should be Non Negative Numeric only and Length can not greater than one! -----------");
													 }else{
														 csIOMSCharges.setString(14, getTagValue("exclude", eElementChargeInfo));
													 }
												 }else{
													 csIOMSCharges.setString(14, "");
												 }
												 if(getTagValue("UniqueServiceDetailId", eElementService)!=null){
													 if(CLEPUtility.isValidNONNegativeNumeric(getTagValue("UniqueServiceDetailId", eElementService))){
														 csIOMSCharges.setLong(15,Long.valueOf(getTagValue("UniqueServiceDetailId", eElementService)));
													 }else{
														 	missingAttribute=" UniqueServiceDetailId should be Non Negative Numeric only";
												 			throw new IOESException("------ Error:    UniqueServiceDetailId should be Non Negative Numeric only! -----------");
													 }
												 }else{
													 csIOMSCharges.setLong(15,0);
												 }
												 if(!"".equalsIgnoreCase(uniqueProductDetaiId)){
													 if(CLEPUtility.isValidNONNegativeNumeric(uniqueProductDetaiId)){
														 csIOMSCharges.setLong(16, Long.valueOf(uniqueProductDetaiId));
													 }else{
														 	missingAttribute=" uniqueProductDetaiId should be Non Negative Numeric only";
												 			throw new IOESException("------ Error:    uniqueProductDetaiId should be Non Negative Numeric only! -----------");
													 }
												 }else{
													 csIOMSCharges.setLong(16, 0);
												 }
												 csIOMSCharges.setString(17, chargeAttribute);
												 csIOMSCharges.execute();
												 insertFlag=1;
												 }else{
													 	insertFlag=0;
													 	conn.rollback();
														CLEPUtility.SysErr("<<==========================InComing Message is Invalid=============================>>");					
														clepXmlDto.setXmlData("<orderHeader><NewOrder><OrderType>NEW PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML,UniqueChargeId can not left blank or missing in any chargeInfoNode tag in XML</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg></ErrorMsg></Charges></NewOrder></orderHeader>");
														clepXmlDto.setJmsMessageID(jmsMessageID);
														sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);														
														break outer;
												 }
											 }									 
										}//each charges
										}else{
											insertFlag=0;
											conn.rollback();
											CLEPUtility.SysErr("<<==========================InComing Message is Invalid=============================>>");					
											clepXmlDto.setXmlData("<orderHeader><NewOrder><OrderType>NEW PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML,chargesInfoNode tag is missing in any ProductCatelog tag in XML</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg></ErrorMsg></Charges></NewOrder></orderHeader>");
											clepXmlDto.setJmsMessageID(jmsMessageID);
											sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);
											break outer;
										}
									}//charges code									
					 }
					}
					}else{
						//each prodct catelog
						insertFlag=0;
						conn.rollback();
						CLEPUtility.SysErr("<<==========================InComing Message is Invalid=============================>>");					
						clepXmlDto.setXmlData("<orderHeader><NewOrder><OrderType>NEW PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML,ProductCatelog tag is missing in any ServiceDetails tag in XML</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg></ErrorMsg></Charges></NewOrder></orderHeader>");
						clepXmlDto.setJmsMessageID(jmsMessageID);
						sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);
						break outer;
					}
				}					
			}
			}else{
				//each service details
				insertFlag=0;
				conn.rollback();
				CLEPUtility.SysErr("<<==========================InComing Message is Invalid=============================>>");					
				clepXmlDto.setXmlData("<orderHeader><NewOrder><OrderType>NEW PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML,ServiceDetails tag is missing in XML</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg></ErrorMsg></Charges></NewOrder></orderHeader>");
				clepXmlDto.setJmsMessageID(jmsMessageID);
				sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);
			}
			}else{
				insertFlag=0;
				conn.rollback();
				CLEPUtility.SysErr("<<==========================InComing Message is Invalid=============================>>");					
				clepXmlDto.setXmlData("<orderHeader><NewOrder><OrderType>NEW PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML,PODetails tag is missing in XML</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg></ErrorMsg></Charges></NewOrder></orderHeader>");				
				clepXmlDto.setJmsMessageID(jmsMessageID);
				sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);
			}
			}else{
				//contact
				insertFlag=0;
				conn.rollback();
				CLEPUtility.SysErr("<<==========================InComing Message is Invalid=============================>>");					
				clepXmlDto.setXmlData("<orderHeader><NewOrder><OrderType>NEW PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML,Contact tag is missing in XML</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg></ErrorMsg></Charges></NewOrder></orderHeader>");
				clepXmlDto.setJmsMessageID(jmsMessageID);
				sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);
			}
			}//OrderExtendedAttribute
				
				/*------------------------ Save MessageId,UniqueFileId in File Processing Table ---------------------------*/
				int isSucess=-1;
				if(insertFlag==1){
					csProcessFileInfoInsert=conn.prepareCall(spInsertPROCESSFILEINFOtoIOMS); 
								csProcessFileInfoInsert.setLong(1, xmlfileid);
								csProcessFileInfoInsert.setLong(2, 1);//templateid
								csProcessFileInfoInsert.setString(3, "1");//user Id
								csProcessFileInfoInsert.setString(4, jmsMessageID);//messageRequestId
								csProcessFileInfoInsert.setString(5, fileName);//incomig filename
								csProcessFileInfoInsert.setString(6, absFileName);//fileName with path
								csProcessFileInfoInsert.setString(7,filePath);//filepath								
								csProcessFileInfoInsert.setInt(8, 0);
								csProcessFileInfoInsert.setInt(9, 0);
								csProcessFileInfoInsert.setString(10, "");
								csProcessFileInfoInsert.execute();
								isSucess=csProcessFileInfoInsert.getInt(8);
								if(isSucess==0){
									conn.commit();
								}								
				}						
				//==========================================================================================
												//NEW ORDER VALIDATION START HERE
				//==========================================================================================
						if(isSucess==0){
							CLEPUtility.SysErr("-------- NewOrder DataType Validation Start >>>>>>>>>>>>");
													
							cstmt=conn.prepareCall(spInsertVALIDATENEWORDERtoIOMS); 
							cstmt.setLong(1, xmlfileid);
							cstmt.setLong(2, 0);
							cstmt.setInt(3, 0);
							cstmt.setString(4, "");
							cstmt.setString(5, "");
							cstmt.setString(6, "");
							cstmt.setNull(7, java.sql.Types.CLOB);
							cstmt.setString(8, "");
							cstmt.execute();
							long result=cstmt.getLong(2);
							if(result==2){
								CLEPUtility.SysErr("--------- NewOrder DataType Validation SUCCESS ------");								
								isProcess=1;								
							}else{
								CLEPUtility.SysErr("--------- NewOrder DataType Validation Failed(Refer:Error XML File for more information) ------");															
								isProcess=0;
							}
							Clob b=cstmt.getClob(7);
							if(b!=null){
								byte byteArr[]=Utility.clobToByteArray(b);								
								String xmlResponseValidationData=new String(byteArr);	
								if(isProcess==0){
									clepXmlDto.setXmlData(xmlResponseValidationData);									
									CLEPUtility.SysErr("------------ Sending Response to MPP Starting >>>>>>>>>>>");															
									int sendStatus=sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);								
								}	
							}else{
								isProcess=0;								
								conn.rollback();
								missingAttribute="[Internal error]{}";
								CLEPUtility.SysErr("------------ Error: NewOrder DataType Validation Response Creating FAILED --------");
								clepXmlDto.setXmlData("<orderHeader><NewOrder><OrderType>NEW PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg></ErrorMsg></Charges></NewOrder></orderHeader>");
								clepXmlDto.setJmsMessageID(jmsMessageID);
								sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);								
							}
						}										
			//==============================================================================================
													//NEW ORDER PROCESSING START HERE\\
			//==============================================================================================
				
				long orderNo=0l;
				if(isProcess==1){
				CLEPUtility.SysErr("------------ NewOrder Creation Processing start for ReqMsg("+jmsMessageID+") >>>>>>>>>>>");				
				
				csIOMS=conn.prepareCall(spInsertPROCESSNEWORDERtoIOMS);
				csIOMS.setLong(1, xmlfileid);
				csIOMS.setInt(2, 279214);//empid this is handling in data base
				csIOMS.setInt(3, 0);//result
				csIOMS.setInt(4, 0);
				csIOMS.setInt(5, 0);
				csIOMS.setString(6, "");
				csIOMS.setInt(7, 0);
				csIOMS.setInt(8, 0);
				csIOMS.setNull(9, java.sql.Types.CLOB);
				csIOMS.execute();
																	
				orderNo=csIOMS.getInt(8);
				long resultProcess=csIOMS.getLong(3);
				int isProcessAhead=-1;
				
				if(resultProcess==2){
					isProcessAhead=1;
					CLEPUtility.SysErr("----------- NewOrder Created Successfully:-"+csIOMS.getInt(8)+" ----------");	
					
					/*int updateTaxRate=getAllServices(orderNo,conn);
					if(updateTaxRate==1){
						CLEPUtility.SysErr("---- Tax Rate Updated Succesfully ---");
					}else{
						CLEPUtility.SysErr("--- Tax Rate Updation Failed ----");
					}*/
				}else{
					CLEPUtility.SysErr("--------- NewOrder Processing  Failed for ReqMsg("+jmsMessageID+"(Refer:Error XML File for more information) ------");
					isProcessAhead=0;
					conn.rollback();
				}
				
				Clob b=csIOMS.getClob(9);
				if(b!=null){
					byte byteArr[]=Utility.clobToByteArray(b);
					String xmlResponseProcessData=new String(byteArr);
					clepXmlDto.setXmlData(xmlResponseProcessData);
				}else{
					conn.rollback();
					isProcessAhead=0;
					CLEPUtility.SysErr("Error:-"+csIOMS.getString(6));
					missingAttribute="[Internal error]{}";
					CLEPUtility.SysErr("------------ Error: NewOrder Processing Response Creating FAILED --------");
					clepXmlDto.setXmlData("<orderHeader><NewOrder><OrderType>NEW PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML,missing Attribute "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg></ErrorMsg></Charges></NewOrder></orderHeader>");
					clepXmlDto.setJmsMessageID(jmsMessageID);								
				}
				int sendStatus=-1;
				if(isProcessAhead==0){
					CLEPUtility.SysErr("------- Sending Response to MPP Starting >>>>>>>>>>>");							
					sendStatus=sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);						
				}				
				if(isProcessAhead==1){
					int totalM6Product=getCountOfM6Product(xmlfileid,conn);		
					int totalNoNM6Product=getCountOfNoNM6Product(xmlfileid,conn);
				
					if(csIOMS.getString(6)==null || "".equalsIgnoreCase(csIOMS.getString(6)))
					{				
						if(isProcessAhead ==1){										
							String prjMngrId="";							
							//===========================ONLY FOR NON M6 PRODUCT===============================	
							if((totalM6Product==0) && (totalNoNM6Product!=0)){																					
									
									if("True".equalsIgnoreCase(isWithWorkflow)){
										//###############################################################################
										//									WITH WORKFLOW										
										CLEPUtility.SysErr("************* SaaS ERP orderNo *************");									
										int isValidateSuccess=validateSaaSErP(orderNo,xmlfileid,jmsMessageID,attachedWorkflowId,conn);
										if(isValidateSuccess==1){
											/* 
											 *Vijay
											 *Start here to send the msg to AIE 
											 */
											CLEPUtility.SysErr("----------- Waiting for Aproval against orderNo:-"+orderNo+" >>>>>>>>>>");
											/*clepXmlDto.setXmlData("<orderHeader><NewOrder><OrderType>SO</OrderType><OrderExtAttributes><OrderId>"+uniqOrderId+"</OrderId><OrderNo>"+orderNo+"</OrderNo><FileId>"+xmlfileid+"</FileId><MessageId>"+jmsMessageID+"</MessageId><Status>SUCCESS</Status><CancelDate></CancelDate><Reason_Remarks></Reason_Remarks><Stage>AM</Stage><ApprovalRemarks></ApprovalRemarks></OrderExtAttributes></NewOrder></orderHeader>");
											clepXmlDto.setJmsMessageID(jmsMessageID);
											clepXmlDto.setStage("SUCCESS");	//here success means order inserted succesfully
											sendStatus=sendXMLtoMPP(clepXmlDto,xmlfileid,"N","N",conn);*/
											
											
											/*implement sending msg same as time of copc approval  */
											ViewOrderDto objServiceTypeOrdSrcDto = new ViewOrderDto();
											NewOrderDao objDao = new NewOrderDao();
											objServiceTypeOrdSrcDto =objDao.getServiceType_OrderSourceClepErp(orderNo,4); //here 4 means order creation successfully
											//if copc already sent message to mpp,it will not resent
											
											if(objServiceTypeOrdSrcDto.getServiceTypeID()==2 && "2".equalsIgnoreCase(objServiceTypeOrdSrcDto.getOrder_creation_source())){
												if(objServiceTypeOrdSrcDto.getResponseClepOrderMsgAfterAproval()!=null){
													
													clepXmlDto.setXmlData(objServiceTypeOrdSrcDto.getResponseClepOrderMsgAfterAproval());
													CLEPUtility.SysErr("Message Request ID Was:- "+objServiceTypeOrdSrcDto.getClepJMSReqMsgId());
													clepXmlDto.setJmsMessageID(objServiceTypeOrdSrcDto.getClepJMSReqMsgId());
													clepXmlDto.setStage("SUCCESS");	//here success means order inserted succesfully
													objDao.sendClepResponseToMpp(clepXmlDto,objServiceTypeOrdSrcDto.getClepFileId(),objServiceTypeOrdSrcDto.getOrderno(),"N");							
												}	
											}
											
											//vijay end
										}else{
											CLEPUtility.SysErr("----------- PO Validation FAILED against:-"+orderNo+" -----------");
											conn.rollback();
										}
									}else if("False".equalsIgnoreCase(isWithWorkflow)){
								//###############################################################################
								//									WITHOUT WORKFLOW
								CLEPUtility.SysErr("--------- Processing[Validate,Workflow,Publishing,BillingTrigger] start for Non M6 Product >>>>>>>>");
								sendStatus=-1;
								CLEPUtility.SysErr("-------Printing Response MSG Before validation and attach billable response-------------- ");
								/*Use  this method processOnlyNoNM6Product() if you want to insert workflow with auto approval and also publish with billing trigger*/
								//int isSendToMPP=processOnlyNoNM6Product(orderNo,xmlfileid,prjMngrId,conn,jmsMessageID);
								
								/*This method validate some data and attach workflow and do auto approval */
								int isSendToMPP = ProcessXMLforCLEP.processWorkflowForNoNM6Product(orderNo,xmlfileid,prjMngrId,conn,jmsMessageID);
								if(isSendToMPP==1){
										String responseData="",sendAttachedBillableResonse="";										
										if(clepXmlDto.getXmlData()!=null){
											responseData=clepXmlDto.getXmlData();
											sendAttachedBillableResonse=ParseXMLForChangeOrder.attachBillableAccountToResponse(orderNo,xmlfileid,responseData,jmsMessageID,conn);
											if(!"FAILED".equalsIgnoreCase(sendAttachedBillableResonse)){
												/*Update XML response for ib2bMessage. This tag <Ib2bMessage> contain "Order is waiting for RFBT" */
												if(sendAttachedBillableResonse.contains("<Ib2bMessage>")){
													sendAttachedBillableResonse = sendAttachedBillableResonse.replace("<Ib2bMessage>", "<Ib2bMessage>Order is waiting for RFBT");
												}
												clepXmlDto.setStage(AppConstants.CLEP_SUCCESS);
												CLEPUtility.SysErr("--------- Sending Response to MPP >>>>>>>>>>>");
												clepXmlDto.setXmlData(sendAttachedBillableResonse);
												sendStatus=sendXMLtoMPP(clepXmlDto,xmlfileid,"N","N",conn);
											}																																	
										}																				
								 }else{								
									 CLEPUtility.SysErr("-------- Response Sending Failed !---------");
								 }
								}else{
									//error response attribute 1 is missing value
									 missingAttribute=" Attribute1 is Invalid{Hint:should be True for Workflow or False for without Workflow} ";
									 throw new IOESException("------ Error:    Attribute1 is Invalid ! -----------");
								}								
							}						
					//==========================ONLY FOR M6 PRODUCT [TELCO]====================================
					if((totalNoNM6Product==0)&&(totalM6Product !=0)){
						conn.commit();
					}
					}
				}else{					
					CLEPUtility.SysErr("Error:"+csIOMS.getString(6));					
					conn.rollback();
				}
				}
				}
				//##################################### NEW ORDER CLOSED ###############################################################
		        }else if("RFBT".equalsIgnoreCase(orderType)){
		        	
		        	//############################### REQUEST FOR BILLING TRIGGER OPEN FOR SAAS ERP ######################################
		        	
		        	/*----------------------- Parsing Request for Billing Trigger and Inserting Data Into Temporary table -----------------*/
		        	
		        	int successFlag=-1,isInsertedDataintoTemp=-1,isDataTypeValidateSuccess=-1;
		        	CallableStatement csReqForBillingTrigger=null;
		        	String orderNumberForBT="",locNoForBT="",locDateForBT="",locRecvDateForBT="",billTriggerDateForBT="";
		        	CLEPUtility.SysErr("**************************Reading and Inserting RequestForBillingTrigger********************************");
		        	CLEPUtility.SysErr("**************************Reading and Inserting OrderNo********************************");
		        	missingAttribute="OrderNo in RequestForBillingTrigger";
		        	NodeList nlListOrderNo = doc.getElementsByTagName("OrderNo").item(0).getChildNodes();					
					Node nValueOrdNo = (Node) nlListOrderNo.item(0);
					orderNumberForBT=nValueOrdNo.getNodeValue().trim();
					
					CLEPUtility.SysErr("**************************Reading and Inserting LOCNo********************************");
					missingAttribute="LOCNo in RequestForBillingTrigger";
					NodeList nlListLocNo = doc.getElementsByTagName("LOCNo").item(0).getChildNodes();					
					Node nValueLocNo = (Node) nlListLocNo.item(0);
					locNoForBT=nValueLocNo.getNodeValue().trim();
					
					CLEPUtility.SysErr("**************************Reading and Inserting LOCDate********************************");
					missingAttribute="LOCDate in RequestForBillingTrigger";
					NodeList nlListLocDate = doc.getElementsByTagName("LOCDate").item(0).getChildNodes();					
					Node nValueLocDate = (Node) nlListLocDate.item(0);
					locDateForBT=nValueLocDate.getNodeValue().trim();
					
					CLEPUtility.SysErr("**************************Reading and Inserting LOCRecDate********************************");
					missingAttribute="LOCRecDate in RequestForBillingTrigger";
					NodeList nlListLocRecvDate = doc.getElementsByTagName("LOCRecDate").item(0).getChildNodes();					
					Node nValueLocRecvDate = (Node) nlListLocRecvDate.item(0);
					locRecvDateForBT=nValueLocRecvDate.getNodeValue().trim();
					
					CLEPUtility.SysErr("**************************Reading and Inserting BillingTriggerDate********************************");
					missingAttribute="BillingTriggerDate in RequestForBillingTrigger";
					NodeList nlListBillTrgDate = doc.getElementsByTagName("BillingTriggerDate").item(0).getChildNodes();					
					Node nValueBillTrgDate = (Node) nlListBillTrgDate.item(0);
					billTriggerDateForBT=nValueBillTrgDate.getNodeValue().trim();
										
					csReqForBillingTrigger=conn.prepareCall(spInsertNEWRequestForBillingTrigger);
					csReqForBillingTrigger.setLong(1, xmlfileid);
					csReqForBillingTrigger.setString(2, orderNumberForBT);
					csReqForBillingTrigger.setString(3, locNoForBT);
					csReqForBillingTrigger.setString(4, locDateForBT);
					csReqForBillingTrigger.setString(5, locRecvDateForBT);
					csReqForBillingTrigger.setString(6, billTriggerDateForBT);
					csReqForBillingTrigger.setInt(7, 0);//OrderNo is valid out					
					csReqForBillingTrigger.setString(8, "");//OrderType out
					csReqForBillingTrigger.setString(9, "");//CHANGE TYPE OUT
					csReqForBillingTrigger.setString(10, "");//SUBCHANGE TYPE OUT
					csReqForBillingTrigger.setInt(11, 0);
					csReqForBillingTrigger.setString(12, "");
					csReqForBillingTrigger.execute();										
					
					int isOrderNoisValid=csReqForBillingTrigger.getInt(7);
					String orderPOType=csReqForBillingTrigger.getString(8);
					String changeTypeId=csReqForBillingTrigger.getString(9);
					String subChangeTypeId=csReqForBillingTrigger.getString(10);
					/*---------------------------------------------------------------------------------------------------------------------*/
									
						/*---------------- FileID,Requested MessageId Inserted into File Process Table ------ */
						csProcessFileInfoInsert=conn.prepareCall(spInsertPROCESSFILEINFO_RFBTtoIOMS); 
						csProcessFileInfoInsert.setLong(1, xmlfileid);
						csProcessFileInfoInsert.setLong(2, 2);//templateid fixed for new order
						csProcessFileInfoInsert.setString(3, "1");//user Id handling in data base
						csProcessFileInfoInsert.setString(4, jmsMessageID);//messageRequestId
						csProcessFileInfoInsert.setString(5, fileName);//incomig filename
						csProcessFileInfoInsert.setString(6, absFileName);//fileName with path
						csProcessFileInfoInsert.setString(7,filePath);//filepath								
						csProcessFileInfoInsert.setString(8,orderNumberForBT);//ORDERNO
						csProcessFileInfoInsert.setString(9,"RFBT");//ORDER TYPE
						csProcessFileInfoInsert.setString(10,"True");//ATTRIBUTE1
						csProcessFileInfoInsert.setInt(11, 0);
						csProcessFileInfoInsert.setInt(12, 0);
						csProcessFileInfoInsert.setString(13, "");
						csProcessFileInfoInsert.execute();
						int isSucess=csProcessFileInfoInsert.getInt(11);
						if(isSucess==0){
							conn.commit();
						}						
						/*------------------------------------------------------------------------------------*/
						
						/*---------------- DataType Validation for Request for Billing Trigger -------------*/
						if(isSucess==0){
								CLEPUtility.SysErr("--------- Request For BillingTrigger DataType Validation Start for Order No: "+orderNumberForBT+">>>>>>>>>>>>");
								
								cstmt=conn.prepareCall(spInsertVALIDATERFBTtoIOMS); 
								cstmt.setLong(1, xmlfileid);
								cstmt.setLong(2, 0);
								cstmt.setInt(3, 0);
								cstmt.setString(4, "");
								cstmt.setString(5, "");
								cstmt.setString(6, "");
								cstmt.setNull(7, java.sql.Types.CLOB);
								cstmt.setString(8, "");
								cstmt.execute();
								
								long result=cstmt.getLong(2);
								if(result==2){
									CLEPUtility.SysErr("--------- Request For BillingTrigger DataType Validation SUCCESS ------");								
									isDataTypeValidateSuccess=1;								
								}else{
									CLEPUtility.SysErr("--------- Request For BillingTrigger DataType Validation Failed(Refer:Error XML File for more information)------");															
									isDataTypeValidateSuccess=0;
								}
								
								Clob b=cstmt.getClob(7);
								if(b!=null){
									byte byteArr[]=Utility.clobToByteArray(b);
									String xmlResponseValidationData=new String(byteArr);	
									if(isDataTypeValidateSuccess==0){
										clepXmlDto.setXmlData(xmlResponseValidationData);	
										clepXmlDto.setJmsMessageID(jmsMessageID);
										CLEPUtility.SysErr("--------- Sending Response to MPP Starting >>>>>>>>>>>");							
										
										int sendStatus=sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);							
									}
								}else{
									isDataTypeValidateSuccess=0;
									conn.rollback();
									CLEPUtility.SysErr("Error:-"+csIOMS.getString(6));
									missingAttribute="[Internal error]{}";
									clepXmlDto.setXmlData("<orderHeader><NewOrder><OrderType>NEW PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML,missing Attribute "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg></ErrorMsg></Charges></NewOrder></orderHeader>");
									clepXmlDto.setJmsMessageID(jmsMessageID);
									sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);									
								}
								
								
						}
						/*----------------------------------------------------------------------------------*/
						/*------------- If OrderNo is Valid Then Send for BillingTrigger -------------------*/
						if(isDataTypeValidateSuccess==1){
							if(isOrderNoisValid==1){															
								int isSendToMPP=-1;
								long orderNumber=Long.valueOf(orderNumberForBT);
								if("NEW".equalsIgnoreCase(orderPOType)){																		
									
										CLEPUtility.SysErr("---------- Send For BillingTrigger Date Saving for CLEP New Order With Workflow->OrderNo:"+orderNumberForBT+" >>>>>>>>>>>>>>>");
										isSendToMPP=processOnlyNoNM6ProductAfterAproval(orderNumber,xmlfileid,"",conn,clepXmlDto);
									
								}else if("CHANGE".equalsIgnoreCase(orderPOType)){
									int orderNum=Integer.valueOf(orderNumberForBT);									
									int changeType=0;
										CLEPUtility.SysErr("---------- Send For BillingTrigger Date Saving for CLEP Change Order With Workflow->OrderNo:"+orderNumberForBT+" >>>>>>>>>>>>>>>");
										isSendToMPP=ParseXMLForChangeOrder.processOnlyNoNM6ChangeOrderAfterAproval(orderNum, xmlfileid, projectMngrId, conn,changeTypeId, subChangeTypeId,clepXmlDto);									
								}
								
								if(isSendToMPP==1){																
									conn.commit();																													
								}else{								
										CLEPUtility.SysErr("------ Response Sending Failed !---------");
										conn.rollback();
								}
							}else{
					        	//orderno is invalid
					        	insertFlag=0;
								conn.rollback();
								missingAttribute="Requested OrderNo for BillingTrigger is not valid[Hint:OrderNo is not exist or Billing Trigger already done]";
								CLEPUtility.SysErr(missingAttribute);								
								clepXmlDto.setXmlData("<orderHeader><NewOrder><OrderType>NEW PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to  "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg></ErrorMsg></Charges></NewOrder></orderHeader>");
								clepXmlDto.setJmsMessageID(jmsMessageID);
								sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);					
							}	
						}		       
						/*------------------------------------------------------------------------------------*/
						//########################### REQUEST FOR BILLING TRIGGER CLOSED FOR SAAS ERP #############################################
		        }
		        else{		        		
		        		int insertSuccessChangeOrder=-1;
		        				        		
		        	//############################## CHANGE ORDER OPEN ####################################################################
		        		/*Checking order sub type if find then call other method for manipulating the change order */
//		        		===================================ORDER TYPE==============================================
		        		String orderSubType=null;
		        		if(AppConstants.DISCONNECTION_ORDER.equalsIgnoreCase(orderType)){
		        			//If order type is disconnection then it will following the old flow 
		        			insertFlag=1;
		        			orderSubType="";
		        		}
						/*Start the validation of OrderSubType if orderType is SOLUTIONCHANGE */
		        		else if(AppConstants.SOLUTIONCHANGE_ORDER.equalsIgnoreCase(orderType) && doc.getElementsByTagName("OrderSubType").item(0)!=null)
						{
							NodeList nlListOrderSubType = doc.getElementsByTagName("OrderSubType").item(0).getChildNodes();					
							Node nValueOrdSubType = (Node) nlListOrderSubType.item(0);
							if(nValueOrdSubType!=null){
								orderSubType= nValueOrdSubType.getNodeValue().trim();
							}
							else{
								orderSubType="";
							}
							CLEPUtility.SysErr("**************Order Sub Type is:>"+orderSubType+"**************");
							if(orderSubType !=null && (validateOrderSubType(orderSubType))){
								//Here set the value 1 in 'insertFlag' variable if no error found 
									insertFlag=1;
							}else{
								/*----------------sending Error XML--------------------*/
								insertFlag=0;
								conn.rollback();
								CLEPUtility.SysErr("<<==========================InComing Message is Invalid=============================>>");					
								clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>CHANGE ORDER PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML, OrderSubType Attribute can not left blank or provide correct OrderSubType</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg></ErrorMsg></Charges></ChangeOrder></orderHeader>");
								clepXmlDto.setJmsMessageID(jmsMessageID);
								sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);	
							}
						}else{
						
							insertFlag=0;
							conn.rollback();
							CLEPUtility.SysErr("<<==========================InComing Message is Invalid=============================>>");					
							clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>CHANGE ORDER PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML, OrderSubType Attribute is missing in XML</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg></ErrorMsg></Charges></ChangeOrder></orderHeader>");
							clepXmlDto.setJmsMessageID(jmsMessageID);
							sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);
						}
						/*End of validation subOrderType tag */
						
						if((insertFlag==1) && (orderSubType.equals(AppConstants.CLEP_CHARGE_ADD) 
							|| orderSubType.equals(AppConstants.CLEP_CHARGE_DIS) 
							|| orderSubType.equals(AppConstants.CLEP_CHARGE_ADD_AND_DIS)
							|| orderSubType.equals(AppConstants.CLEP_CHARGE_ADD_AND_NEW_LINE_ADD)
							|| orderSubType.equals(AppConstants.CLEP_CHARGE_DIS_AND_NEW_LINE_ADD)
							|| orderSubType.equals(AppConstants.CLEP_CHARGE_ADD_AND_DIS_AND_NEW_LINE_ADD) )){
							
								/*here call other method that handle other type of order like charge adding, charge disconnection,
								 * charge addition + charge disconnection etc.
								 */	
								ParseXMLForChangeOrderExt.processSolutionChangeXML(doc, orderType, orderSubType, xmlfileid, clepXmlDto, jmsMessageID, fileName, absFileName, filePath, conn);
						}
						else if(insertFlag==1) {
								/*----------------going Normal Flow that is add line item only -------------- */
			        		//================================================== MAIN TAB ======================================================
			        		missingAttribute="OrderExtAttributes";
			        		NodeList parentNodeOrdExtAttributeList = doc.getElementsByTagName("OrderExtAttributes");
			        		csIOMS=conn.prepareCall(spInsertORDEXTATTtoIOMS);									 
			        		Node childNodeOrderExtAtt = parentNodeOrdExtAttributeList.item(0);
			        		CLEPUtility.SysErr("**************************Reading and Inserting OrderExtAttributes********************************");		        		
			        		if (childNodeOrderExtAtt.getNodeType() == Node.ELEMENT_NODE)
			        		{
								 Element elementOrdExtAttributeElement = (Element) childNodeOrderExtAtt;
								 
								 csIOMS.setLong(1, xmlfileid);		
								 missingAttribute="UniqueOrderID";
								 csIOMS.setString(2,getTagValue("UniqueOrderID", elementOrdExtAttributeElement));
								 
								 missingAttribute="OrderForProduct";
								 csIOMS.setString(3,getTagValue("OrderForProduct", elementOrdExtAttributeElement));
								 missingAttribute="Attribute1";
								 csIOMS.setString(4,getTagValue("Attribute1", elementOrdExtAttributeElement));
								 isWithWorkflow=getTagValue("Attribute1", elementOrdExtAttributeElement);
								 
								 /*---start the validating for 
								  * attribute2 (that is workflow id)
								  */ 
								 if("True".equalsIgnoreCase(isWithWorkflow)  
										 	&& getTagValue("Attribute2", elementOrdExtAttributeElement)==null){
									 
									 missingAttribute=" Attribute2 can not left blank ";
									 throw new IOESException("------ Error:    Attribute2 can not left blank! -----------");
								 }
								 else if( "True".equalsIgnoreCase(isWithWorkflow) 
										 	&& (!validationForAttribute2(getTagValue("Attribute2", elementOrdExtAttributeElement))) ){
									 /*if validation return false 
									  * that means workflow id is not valid */
									 
									 missingAttribute=" Attribute2 is not valid workflow id ";
									 throw new IOESException("------ Error:    Attribute2 is not valid workflow id! -----------");	 
								 }
	
								 /*End of validating the workflow id */
								 missingAttribute="Attribute2";
								 csIOMS.setString(5,getTagValue("Attribute2", elementOrdExtAttributeElement));
								 
								 /*assigning the workflow id for interting workflow */ 
								 attachedWorkflowId = getTagValue("Attribute2", elementOrdExtAttributeElement);
								 clepXmlDto.setWorkFlowId(attachedWorkflowId);
								 missingAttribute="Attribute3";
								 csIOMS.setString(6,getTagValue("Attribute3", elementOrdExtAttributeElement));
								 missingAttribute="CRMAccountNo";
								 csIOMS.setString(7,getTagValue("CRMAccountNo", elementOrdExtAttributeElement));								 
								 accountId=getTagValue("CRMAccountNo", elementOrdExtAttributeElement);							 
								 missingAttribute="Source";
								 csIOMS.setString(8, getTagValue("Source", elementOrdExtAttributeElement));
								 missingAttribute="Currency";
								 csIOMS.setString(9, getTagValue("Currency", elementOrdExtAttributeElement));
								 missingAttribute="QuoteNo";
								 csIOMS.setString(10, getTagValue("QuoteNo", elementOrdExtAttributeElement));	
								 if(getTagValue("ProjectManager", elementOrdExtAttributeElement)!=null){
									 missingAttribute="ProjectManager";
									 csIOMS.setString(11, getTagValue("ProjectManager", elementOrdExtAttributeElement));
								 }else{
									 csIOMS.setString(11, "");
								 }
								 projectMngrId="2";//getTagValue("ProjectManager", eOrdExtAttributeElement);
								 missingAttribute="RFSDate";
								 csIOMS.setString(12, getTagValue("RFSDate", elementOrdExtAttributeElement));	
								 missingAttribute="ActMgrPhNo";
								 if(getTagValue("ActMgrPhNo", elementOrdExtAttributeElement)!=null){
									 missingAttribute="ProjectManager";
									 csIOMS.setString(13, getTagValue("ActMgrPhNo", elementOrdExtAttributeElement));
								 }else{
									 csIOMS.setString(13, "");
								 }
								 missingAttribute="ActMgrEmailID";
								 if(getTagValue("ActMgrEmailID", elementOrdExtAttributeElement)!=null){							 
								 if(CLEPUtility.isEmailValid(getTagValue("ActMgrEmailID", elementOrdExtAttributeElement))){
									 csIOMS.setString(14, getTagValue("ActMgrEmailID", elementOrdExtAttributeElement));	
								 }else{
									 csIOMS.setString(14, "-1");	
								 }		
								 }else{
									 csIOMS.setString(14, "");
								 }
								 missingAttribute="IRUOrderYN";
								 csIOMS.setString(15, getTagValue("IRUOrderYN", elementOrdExtAttributeElement));
								 missingAttribute="FreePeriodYN";
								 csIOMS.setString(16, getTagValue("FreePeriodYN", elementOrdExtAttributeElement));
								 missingAttribute="OrderExclTaxYN";
								 csIOMS.setString(17, getTagValue("OrderExclTaxYN", elementOrdExtAttributeElement));
								 missingAttribute="DateCAF";
								 csIOMS.setString(18, getTagValue("DateCAF", elementOrdExtAttributeElement));
								 csIOMS.setString(19, orderType);
								 csIOMS.execute();											 					 					 					
														
								 //===================================== CONTACT TAB ==================================================
								 NodeList parentNodeContactsList = elementOrdExtAttributeElement.getElementsByTagName("Contacts");
								 csIOMS=conn.prepareCall(spInsertCONTACTtoIOMS);
								 taglength=parentNodeContactsList.getLength();
								 if(taglength > 0){
								 for(int i=0;i<parentNodeContactsList.getLength();i++)
								 {					 
									 Node childNodeContact = parentNodeContactsList.item(i);
									 CLEPUtility.SysErr("**************************Reading and Inserting Contacts["+(i+1)+"]*******************************");								
									 if (childNodeContact.getNodeType() == Node.ELEMENT_NODE)
									 {
										 Element eElement = (Element) childNodeContact;
								 	
										 csIOMS.setLong(1, xmlfileid);
										 	missingAttribute="ContactType in Contact tag";
											csIOMS.setString(2,getTagValue("ContactType", eElement));
											missingAttribute="Salutation in Contact tag";
											csIOMS.setString(3, getTagValue("Salutation", eElement));
											missingAttribute="FirstName in Contact tag";
											csIOMS.setString(4, getTagValue("FirstName", eElement));
											missingAttribute="LastName in Contact tag";
											if(getTagValue("LastName", eElement)!=null){
												csIOMS.setString(5, getTagValue("LastName", eElement));
											}else{
												csIOMS.setString(5, "");
											}
											missingAttribute="Email in Contact tag";
											if(getTagValue("Email", eElement)!=null){
												if(CLEPUtility.isEmailValid(getTagValue("Email", eElement))){
													csIOMS.setString(6, getTagValue("Email", eElement));
												}else{
													csIOMS.setString(6, "-1");
												}
											}else{
												csIOMS.setString(6, "");
											}
											missingAttribute="CellNo in Contact tag";
											csIOMS.setString(7, getTagValue("CellNo", eElement));
											missingAttribute="Fax in Contact tag";
											if(getTagValue("Fax", eElement)!=null){
												csIOMS.setString(8, getTagValue("Fax", eElement));
											}else{
												csIOMS.setString(8, "");
											}
											missingAttribute="Address1 in Contact tag";
											csIOMS.setString(9, getTagValue("Address1", eElement));
											missingAttribute="Address2 in Contact tag";
											if(getTagValue("Address2", eElement)!=null){
												csIOMS.setString(10, getTagValue("Address2", eElement));
											}else{
												csIOMS.setString(10,"");
											}
											missingAttribute="Address3 in Contact tag";
											if(getTagValue("Address3", eElement)!=null){
												csIOMS.setString(11, getTagValue("Address3", eElement));
											}else{
												csIOMS.setString(11, "");
											}
											missingAttribute="CountryID in Contact tag";
											csIOMS.setString(12, getTagValue("CountryID", eElement));
											missingAttribute="StateID in Contact tag";
											csIOMS.setString(13, getTagValue("StateID", eElement));
											missingAttribute="CityID in Contact tag";
											csIOMS.setString(14, getTagValue("CityID", eElement));
											missingAttribute="Pin in Contact tag";
											if(getTagValue("Pin", eElement)!=null){
												csIOMS.setString(15, getTagValue("Pin", eElement));
											}else{
												csIOMS.setString(15, "0");
											}
											missingAttribute="UniqueContactId in Contact tag";
											csIOMS.setString(16, getTagValue("UniqueContactId", eElement));
											missingAttribute="UniqueAddressId in Contact tag";
											csIOMS.setString(17, getTagValue("UniqueAddressId", eElement));
											csIOMS.execute();
											insertSuccessChangeOrder=1;
									 }					 
								 }							 
								 if(insertSuccessChangeOrder==1){
									 /*-------------- Calling Change Order functionality --------*/
									 /* Add an other parameter(workflow is), 
									  * the purpose is to insert workflow dynamically on the behalf of workflow id  
									  * */
									 ParseXMLForChangeOrder.validateAndProcessChangeOrder(elementOrdExtAttributeElement, orderType, orderSubType, xmlfileid, clepXmlDto, jmsMessageID, fileName, absFileName, filePath, conn, isWithWorkflow);
								 }
			        		}else{
			        			//each contact
			        			insertSuccessChangeOrder=0;		
			        			conn.rollback();
								CLEPUtility.SysErr("<<==========================InComing Message is Invalid=============================>>");					
								clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>CHANGE ORDER PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML,missing Contact tag </Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><SubNodeLineItemDetails><ErrorMsg></ErrorMsg></SubNodeLineItemDetails></ChangeOrder></orderHeader>");
								clepXmlDto.setJmsMessageID(jmsMessageID);
								ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);
			        		}
			        		}//ordextattribute end
			        		
			        	  }		        																													
		        	//==========================================CHANGE ORDER CLOSED=================================================						
		           }
		        }else{
		        	insertFlag=0;
		        	conn.rollback();
		        	CLEPUtility.SysErr("<<==========================InComing Message is Invalid=============================>>");		        	
		        	clepXmlDto.setXmlData("<orderHeader><NewOrder><OrderType>NEW PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML,expected value for OrderType Attribute is mismatch</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg></ErrorMsg></Charges></NewOrder></orderHeader>");
		        	clepXmlDto.setJmsMessageID(jmsMessageID);
		        	sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);
		        }
				}else{
					insertFlag=0;
					conn.rollback();
					CLEPUtility.SysErr("<<==========================InComing Message is Invalid=============================>>");					
					clepXmlDto.setXmlData("<orderHeader><NewOrder><OrderType>NEW PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML,OrderType Attribute can not left blank</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg></ErrorMsg></Charges></NewOrder></orderHeader>");
					clepXmlDto.setJmsMessageID(jmsMessageID);
					sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);
				}
				}else{
					insertFlag=0;
					conn.rollback();
					CLEPUtility.SysErr("<<==========================InComing Message is Invalid=============================>>");					
					clepXmlDto.setXmlData("<orderHeader><NewOrder><OrderType>NEW PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML,OrderType Attribute is missing in XML</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg></ErrorMsg></Charges></NewOrder></orderHeader>");
					clepXmlDto.setJmsMessageID(jmsMessageID);
					sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);
				}
				
			}catch(SAXException e){
				insertFlag=0;
				conn.rollback();
				CLEPUtility.LOG(true, false, e, "from method fetchNextXMLDataNewOrder() for SAXException error");
				clepXmlDto.setXmlData("[ Failed due to Invalid XML,"+e.getMessage()+" ]");
				clepXmlDto.setJmsMessageID(jmsMessageID);
				sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);
			}
			catch(NullPointerException e){
				insertFlag=0;
				conn.rollback();
				CLEPUtility.LOG(true, false, e, "from method fetchNextXMLDataNewOrder() for NullPointerException error");				
				if("NEW".equalsIgnoreCase(orderType)){
					clepXmlDto.setXmlData("<orderHeader><NewOrder><OrderType>NEW PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML,missing or spelling mistake for Attribute "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg></ErrorMsg></Charges></NewOrder></orderHeader>");
				}else{
					clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>CHANGE ORDER PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML,missing or spelling mistake for Attribute "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><SubNodeLineItemDetails><ErrorMsg></ErrorMsg></SubNodeLineItemDetails></ChangeOrder></orderHeader>");
				}
				clepXmlDto.setJmsMessageID(jmsMessageID);
				sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);			
			}catch(SQLException e){
				insertFlag=0;
				conn.rollback();
				CLEPUtility.LOG(true, false, e, "from method fetchNextXMLDataNewOrder() for SQLException error");					
				missingAttribute="[Internal error]{}";				
				if("NEW".equalsIgnoreCase(orderType)){
					clepXmlDto.setXmlData("<orderHeader><NewOrder><OrderType>NEW PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML,missing or spelling mistake Attribute "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg></ErrorMsg></Charges></NewOrder></orderHeader>");
				}else{
					clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>CHANGE ORDER PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML,missing or spelling mistake Attribute "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><SubNodeLineItemDetails><ErrorMsg></ErrorMsg></SubNodeLineItemDetails></ChangeOrder></orderHeader>");
				}
				clepXmlDto.setJmsMessageID(jmsMessageID);
				sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);	
			}catch(IOESException e){
				insertFlag=0;
				conn.rollback();
				CLEPUtility.LOG(true, false, e, "from method fetchNextXMLDataNewOrder() for IOESException error");							
				if("NEW".equalsIgnoreCase(orderType)){
					clepXmlDto.setXmlData("<orderHeader><NewOrder><OrderType>NEW PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML,"+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg></ErrorMsg></Charges></NewOrder></orderHeader>");
				}else{
					clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>CHANGE ORDER PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML, "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><SubNodeLineItemDetails><ErrorMsg></ErrorMsg></SubNodeLineItemDetails></ChangeOrder></orderHeader>");
				}
				clepXmlDto.setJmsMessageID(jmsMessageID);
				sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);
			}catch(NumberFormatException e){
				insertFlag=0;
				conn.rollback();
				CLEPUtility.LOG(true, false, e, "from method fetchNextXMLDataNewOrder() for NumberFormatException error");	
				missingAttribute="[Internal error]{}";
				if("NEW".equalsIgnoreCase(orderType)){
					clepXmlDto.setXmlData("<orderHeader><NewOrder><OrderType>NEW PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML,missing Attribute "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg></ErrorMsg></Charges></NewOrder></orderHeader>");
				}else{
					clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>CHANGE ORDER PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML,missing Attribute "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><SubNodeLineItemDetails><ErrorMsg></ErrorMsg></SubNodeLineItemDetails></ChangeOrder></orderHeader>");
				}
				clepXmlDto.setJmsMessageID(jmsMessageID);
				sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);
			}catch(Exception e){
				insertFlag=0;
				conn.rollback();
				CLEPUtility.LOG(true, false, e, "from method fetchNextXMLDataNewOrder() for others error");					
				missingAttribute="[Internal error]{}";
				if("NEW".equalsIgnoreCase(orderType)){
					clepXmlDto.setXmlData("<orderHeader><NewOrder><OrderType>NEW PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML,missing Attribute "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg></ErrorMsg></Charges></NewOrder></orderHeader>");
				}else if(("DISCONNECTION".equalsIgnoreCase(orderType))||("SUSPENTION".equalsIgnoreCase(orderType))||("RESUMPTION".equalsIgnoreCase(orderType))||("RATERENWAL".equalsIgnoreCase(orderType))||("SOLUTIONCHANGE".equalsIgnoreCase(orderType))){
					clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>CHANGE ORDER PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML,missing Attribute "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><SubNodeLineItemDetails><ErrorMsg></ErrorMsg></SubNodeLineItemDetails></ChangeOrder></orderHeader>");
				}else{
					clepXmlDto.setXmlData("<orderHeader><NewOrder><OrderType>NEW PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML,missing Attribute "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg></ErrorMsg></Charges></NewOrder></orderHeader>");
				}
				clepXmlDto.setJmsMessageID(jmsMessageID);
				sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);
			}
			finally{
				try{
					DbConnection.closeCallableStatement(csIOMS);
					DbConnection.closeCallableStatement(csIOMSCharges);
					DbConnection.closeCallableStatement(cstmt);
					DbConnection.closeCallableStatement(csProcessFileInfoInsert);
					DbConnection.freeConnection(conn);
				}catch(Exception ex){
					throw new Exception("Err:Connection closing failed!!");
				}
			}
		return returnStatus;
	}
	/*
	 * 
	 * Function:getTagValue
	 * this method find the value from tag attribute by parsing
	 * */
	private static String getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
	 
	        Node nValue = (Node) nlList.item(0);
	        if(nValue!=null){
	        		return nValue.getNodeValue().trim();
	       }else{
	    	   		return null;
	       }			
	  }
	/*
	 * 
	 * Function:getXMLNextFileId
	 * this method generate unique fileid of incoming xml from mpp.
	 * */
	public static long getXMLNextFileId(Connection conn){
	 	long xmlfileid=0l;
	 	//Connection conn=null;
		CallableStatement csIOMS = null;	
		ResultSet res=null;
		try{
			//conn= DbConnection.getConnectionObject();
			csIOMS=conn.prepareCall("SELECT (NEXTVAL for IOE.SEQ_XML_RECID) as XMLFILEID  from sysibm.sysdummy1");
			res=csIOMS.executeQuery();
			while(res.next()){
				xmlfileid=res.getLong("XMLFILEID");
			}			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		finally
		{
			try
			{
			DbConnection.closeResultset(res);
			DbConnection.closeCallableStatement(csIOMS);
			}
			catch(Exception ex)
			{
				CLEPUtility.LOG(true, false, ex, "from method getXMLNextFileId()");
			}
		}
	 return xmlfileid;
 }
	/*
	 * 
	 * Function:getProjectManagerID
	 * this method find project manager of requesting customer
	 * */
	public static String getProjectManagerID(String employeeId,Connection conn){	 		 	
		CallableStatement csIOMS = null;	
		ResultSet res=null;
		String prjMngrID=null;
		try{
			//conn= DbConnection.getConnectionObject();
			csIOMS=conn.prepareCall("select ID as PRJMNGRID from ioe.TM_ACCOUNTROLEDETAILS where EMPLOYEEID=?");
			csIOMS.setString(1,employeeId);
			res=csIOMS.executeQuery();
			while(res.next()){				
					prjMngrID= String.valueOf(res.getLong("PRJMNGRID"));			
			}			
		}catch(Exception ex){
			CLEPUtility.LOG(true, false, ex, "from method getProjectManagerID()");
		}
		finally
		{
			try
			{
			DbConnection.closeResultset(res);
			DbConnection .closeCallableStatement(csIOMS);
			}
			catch(Exception ex)
			{
				CLEPUtility.LOG(true, false, ex, "from method getProjectManagerID()");
			}
		}
	 return prjMngrID;
 }
	/*
	 * 
	 * Function:getiS_SENDTOM6_Status
	 * this method find out the requesting product will going to provisioning or not.
	 * */
	public static int getiS_SENDTOM6_Status(long serviceTypeID,long serviceDetailID,Connection conn){	 		 	
		CallableStatement csIOMS = null;	
		ResultSet res=null;
		int isSendToM6_Status=-1;
		try{
			//conn= DbConnection.getConnectionObject();
			csIOMS=conn.prepareCall("SELECT SENDTOM6 FROM IOE.TSERVICETYPEDETAIL WHERE SERVICETYPEID=? AND SERVICEDETAILID=?");
			csIOMS.setLong(1,serviceTypeID);
			csIOMS.setLong(2, serviceDetailID);
			res=csIOMS.executeQuery();
			while(res.next()){
				isSendToM6_Status=res.getInt("SENDTOM6");
			}			
		}catch(Exception ex){
			CLEPUtility.LOG(true, false, ex, "from method getiS_SENDTOM6_Status()");
		}
		finally
		{
			try
			{
			DbConnection.closeResultset(res);
			DbConnection.closeCallableStatement(csIOMS);
			}
			catch(Exception ex)
			{
				CLEPUtility.LOG(true, false, ex, "from method getiS_SENDTOM6_Status() for closing statements and resultset");
			}
		}
	 return isSendToM6_Status;
 }
	/*
	 * 
	 * Function:getCountOfM6Product
	 * this method find out total number of provisioning product requesting from mpp
	 * */
	public static int getCountOfM6Product(long xmlfileid,Connection conn){
		CallableStatement csIOMS=null;
		ResultSet res=null;
		int totalM6Prod=-1;
		try{
			csIOMS=conn.prepareCall("SELECT COUNT(ISSENDTOM6) AS TOTALM6PRODUCT  FROM IOE.TM_TEMPORARY_SERVICEDETAILS WHERE FILEID=? AND TRIM(ISSENDTOM6)='1'");
			csIOMS.setLong(1,xmlfileid);
			res=csIOMS.executeQuery();
			while(res.next()){
				totalM6Prod=res.getInt("TOTALM6PRODUCT");
			}
		}catch(Exception ex){
			CLEPUtility.LOG(true, false, ex, "from method getCountOfM6Product()");
		}finally{
			try{
			DbConnection.closeResultset(res);
			DbConnection.closeCallableStatement(csIOMS);
			}catch(Exception ex){
				CLEPUtility.LOG(true, false, ex, "from method getCountOfM6Product() while closing resultset and statement ");
			}
		}
		return totalM6Prod; 
	}
	/*
	 * 
	 * Function:getCountOfNoNM6Product
	 * this method find out total number of non provisioning product requesting from mpp
	 * */
	public static int getCountOfNoNM6Product(long xmlfileid,Connection conn){
		CallableStatement csIOMS=null;
		ResultSet res=null;
		int totalNoNM6Prod=-1;
		try{
			csIOMS=conn.prepareCall("SELECT COUNT(ISSENDTOM6) AS TOTALNONM6PRODUCT  FROM IOE.TM_TEMPORARY_SERVICEDETAILS WHERE FILEID=? AND TRIM(ISSENDTOM6)='0'");
			csIOMS.setLong(1,xmlfileid);
			res=csIOMS.executeQuery();
			while(res.next()){
				totalNoNM6Prod=res.getInt("TOTALNONM6PRODUCT");
			}
		}catch(Exception ex){
			CLEPUtility.LOG(true, false, ex, "from method getCountOfNoNM6Product()");
		}
		finally{
			try{
			DbConnection.closeResultset(res);
			DbConnection.closeCallableStatement(csIOMS);
			}catch(Exception ex){
				CLEPUtility.LOG(true, false, ex, "from method getCountOfNoNM6Product() while closing resultset and statement ");
			}
		}
		return totalNoNM6Prod; 
	}	
	/*
	 * 
	 * Function:sendResponseMsgIDToFileProcess
	 * this method save the sending information to the processing tracking table
	 * */
	public static void sendResponseMsgIDToFileProcess(long xmlfileid,String responseMsgID,String sendMsg,String isExternalErr,String msgReqID,Connection conn,  String Orderstage){
		CLEPUtility.SysErr("----------- Save Response Message Id to File Process >>>>>>>>>>>");			
		CallableStatement pstmt=null;
		try{
							
				//Add another parameter in procedure
				String sql="call IOE.SPSAVE_CLEPRESPONSE(?,?,?,?,?,?)";

				pstmt=conn.prepareCall(sql);
				pstmt.setString(1, responseMsgID);
				pstmt.setString(2, sendMsg);
				pstmt.setString(3, msgReqID);
				pstmt.setLong(4, xmlfileid);
				pstmt.setString(5, isExternalErr);
				//Aadd another argument for recognizaing the stage
				pstmt.setString(6, Orderstage);//
				// end
				pstmt.execute();				
				conn.commit();
		}catch(Exception ex){
			CLEPUtility.LOG(true, false, ex, "from method sendResponseMsgIDToFileProcess()");
		}	
		finally
		{
			DbConnection.closeCallableStatement(pstmt);
		}
	}
	/*
	 * 
	 * Function:sendXMLtoMPP
	 * this method used for sending message to the mpp
	 * */
	public static int sendXMLtoMPP(CLEPXmlDto clepXmldto,long xmlfileid,String isExternalError,String isFromGUI,Connection conn){
		
		int sendStatus=0;		
		try {
			CLEPUtility.SysErr("--------- Sending Response Message >>>>>>>> : "+clepXmldto.getXmlData());
			if("Y".equalsIgnoreCase(Utility.getAppConfigValue("ISSENDCLEPRESPONSE")))
			{				
				sendStatus =SendAndRecieveMessage.messageProducer(clepXmldto);				
				String respnseMsgId=clepXmldto.getSendMessageID();	
				if("N".equalsIgnoreCase(isFromGUI)){
					if("Y".equalsIgnoreCase(isExternalError)){
						sendResponseMsgIDToFileProcess(xmlfileid,respnseMsgId,clepXmldto.getXmlData(),"Y",clepXmldto.getJmsMessageID(),conn,clepXmldto.getStage());
					}else{
						sendResponseMsgIDToFileProcess(xmlfileid,respnseMsgId,clepXmldto.getXmlData(),"N",clepXmldto.getJmsMessageID(),conn,clepXmldto.getStage());
					}
				}else if("Y".equalsIgnoreCase(isFromGUI)){
					if("Y".equalsIgnoreCase(isExternalError)){
						sendResponseMsgIDToFileProcess(xmlfileid,respnseMsgId,clepXmldto.getXmlData(),"Y",clepXmldto.getJmsMessageID(),conn,clepXmldto.getStage());
					}else{
						sendResponseMsgIDToFileProcess(xmlfileid,respnseMsgId,clepXmldto.getXmlData(),"N",clepXmldto.getJmsMessageID(),conn,clepXmldto.getStage());
					}
				}
				else if("BT".equalsIgnoreCase(isFromGUI)){
						sendResponseMsgIDToFileProcess(xmlfileid,respnseMsgId,clepXmldto.getXmlData(),"BT",clepXmldto.getJmsMessageID(),conn,clepXmldto.getStage());
				}
				
			}else{
				CLEPUtility.SysErr("----------- Message Sending is Switch Off ------------");
				sendStatus=-1;
			}
		} 
		catch (Throwable e) {
			// TODO Auto-generated catch block
			CLEPUtility.LOG(true, false, e, "from method ParseXMLforCLEP.sendXMLtoMPP()");			
		}
		return 	sendStatus;
	}	
	/*
	 * 
	 * Function:processOnlyNoNM6Product
	 * this method processing and creating order for non provision products
	 * */
	public static int processOnlyNoNM6Product(long orderNo,long xmlfileid,String serviceType,Connection conn,String jmsID)throws Exception{
		CallableStatement csWorkflow = null;
		CallableStatement csPublish = null;		
		CallableStatement csGetLOCBillTrgDate = null;
		ResultSet rsIOMS=null;
		ResultSet rsWorkflow=null;
		ResultSet rsPublish=null;
		int taskId=0;
		String taskOwnerId=null;
		String retString="";
		String poDetailNo = "";
		String serviceId = "";
		int successStatus=-1;
		ViewOrderDao objViewOrderDao=new ViewOrderDao();
		OrderHeaderDTO newOrderDto=new OrderHeaderDTO();
		ViewOrderDto viewOrderDto=new ViewOrderDto();
		CLEPXmlDto clepDtoPrjMngr=new CLEPXmlDto();
		try{
			
		//==============================VALIDATION=====================================
		CLEPUtility.SysErr("-------- Order Validating start for Message Req:="+jmsID+" >>>>>>>>>>");		
		csWorkflow=conn.prepareCall(sqlSpValidatePODetail);
		csWorkflow.setLong(1,orderNo);	//order no
		rsIOMS=csWorkflow.executeQuery();
		if(rsIOMS.next()){
			poDetailNo = rsIOMS.getString("PODETAILNUMBER");
			serviceId = rsIOMS.getString("SERVICEID");
			retString = "Contract Period Does not Match with Frequency Seleceted for PO Detail No ";
		}
		if("".equalsIgnoreCase(retString)){
			//==========================WORKFLOW START=================================
			CLEPUtility.SysErr("------- Workflow is starting  >>>>>>>>>>>");			
			csWorkflow= conn.prepareCall(sqlSpValidatePO);	
			csWorkflow.setLong(1, orderNo);//order no
			csWorkflow.setLong(2, 1);//empid
			csWorkflow.setString(3, "");
			csWorkflow.setLong(4, 0);
			csWorkflow.setString(5, "");
			csWorkflow.execute();
			int err = csWorkflow.getInt(4);
			if(err==0){
				CLEPUtility.SysErr("------ Po Validated Successfully -------");				
				csWorkflow = conn.prepareCall(sqlGet_TaskListOfOrder);
				csWorkflow.setLong(1,orderNo);//order no
				rsWorkflow=csWorkflow.executeQuery();
				int totalTaskId=0;
				while (rsWorkflow.next()) {
					
					taskId=rsWorkflow.getInt("TASKID"); 		
					taskOwnerId=rsWorkflow.getString("OWNERTYPE_ID");	
					totalTaskId++;
				}
				
				clepDtoPrjMngr=getProjectManagerIdAndUserID(orderNo,conn);
				
				if(totalTaskId!=0){
				csWorkflow= conn.prepareCall(sqlSpInsertTaskAction);	
				csWorkflow.setLong(1, Long.valueOf(taskId));
				csWorkflow.setLong(2, Long.valueOf(1));//actionId
				csWorkflow.setLong(3, Long.valueOf(1));//rejectionSendTo
				csWorkflow.setString(4, "Order created by CLEP");//remarks				
				csWorkflow.setLong(5, clepDtoPrjMngr.getProjectManagerId());				
				csWorkflow.setLong(6, 1);//empid;
				csWorkflow.setLong(7, 0);
				csWorkflow.setLong(8, 0);
				csWorkflow.setString(9, "");							
				csWorkflow.setLong(10, 0);																	
				csWorkflow.execute();
				
				int err2 = csWorkflow.getInt(8);
				Long newTaskid = csWorkflow.getLong(10);								
				if(err2==0){									
					while(newTaskid!=0){
						csWorkflow= conn.prepareCall(sqlSpInsertTaskAction);	
						csWorkflow.setLong(1, Long.valueOf(newTaskid));
						csWorkflow.setLong(2, Long.valueOf(1));
						csWorkflow.setLong(3, Long.valueOf(1));
						csWorkflow.setString(4, "Order created by CLEP");
						csWorkflow.setLong(5, clepDtoPrjMngr.getProjectManagerId());//projectMngrID
						csWorkflow.setLong(6, 1);//empid;
						csWorkflow.setLong(7, 0);
						csWorkflow.setLong(8, 0);
						csWorkflow.setString(9, "");
						csWorkflow.setString(9, "");
						csWorkflow.setLong(10, 0);																	
						csWorkflow.execute();
						
						int err3 = csWorkflow.getInt(8);
															
						newTaskid = csWorkflow.getLong(10);
						if(err3==0){
							CLEPUtility.SysErr("-------- Action taken Successfully and closed Task["+taskId+"]and open Task["+newTaskid+"] -------");														
						}else{
							CLEPUtility.SysErr("--------- Error occured during workflow!! ------------");	
							String msg="Error occured during workflow!!";
							String str="<orderHeader><NewOrder><OrderType>NEWORDER PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsID+"</MessageId><Reason>Failed Due to Invalid Information</Reason></OrderExtAttributes><MainTab><ErrorMsg>NO ERROR</ErrorMsg></MainTab><ContactTab><ErrorMsg>NO ERROR</ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg>NO ERROR</ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg>"+msg+"</ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg>NO ERROR</ErrorMsg></Charges></NewOrder></orderHeader>";
							/*CLEPXmlDto xmldto=new CLEPXmlDto();
							xmldto.setXmlData(str);
							ParseXMLforCLEP.sendXMLtoMPP(xmldto,xmlfileid,"Y",conn);*/
							successStatus=0;
						}									
					}//end while							
					//=================================PARTIAL PUBLISHING================================================
					int status=0;
					String strValidateSaasRes=null,strValidateFieldRes=null,serviceIDList="",msgPartialPublishRes=null;
					
					csWorkflow= conn.prepareCall(sqlGetValidateFeild_Sass);	
					csWorkflow.setLong(1,orderNo);//order no	
					csWorkflow.setString(2,null );	
					csWorkflow.setString(3, null);	
					csWorkflow.setString(4, null);
					csWorkflow.setLong(5, 4);
					csWorkflow.executeUpdate();
					strValidateSaasRes=csWorkflow.getString(2);
					
					CLEPUtility.SysErr("------------ Message after SASS_FEILD_VALDATION:-"+strValidateSaasRes+" ----------");	
					
					if("SUCCESS".equalsIgnoreCase(strValidateSaasRes)){
						csWorkflow= conn.prepareCall(sqlGetValidateFeild);	
						csWorkflow.setLong(1, orderNo);//order no	
						csWorkflow.setString(2,null );	
						csWorkflow.setString(3, null);	
						csWorkflow.setString(4, null);
						csWorkflow.setString(5, null);
						csWorkflow.setInt(6, 4);//roleid
						csWorkflow.executeUpdate();
						strValidateFieldRes=csWorkflow.getString(2);
						
						CLEPUtility.SysErr("----------- Message after ROLEWISE_VALIDATE_FEILDS:-"+strValidateFieldRes+" --------");						
						
						if("SUCCESS".equalsIgnoreCase(strValidateFieldRes)){																			
						csWorkflow= conn.prepareCall(sqlspGetNoNM6ServiceListForOrder);	
						csWorkflow.setLong(1, orderNo);//orderNo		
						rsPublish = csWorkflow.executeQuery(); 
						int totalService=0;
						while(rsPublish.next())
						{																						
							serviceIDList=serviceIDList+String.valueOf(rsPublish.getInt("SERVICEID"))+",";
							totalService++;
						}
						ArrayList<String> services = new ArrayList<String>();
						StringTokenizer st = new StringTokenizer( serviceIDList, ",");									
						for (int i =0; st.hasMoreTokens();i++) 
						{
							services.add(st.nextToken());										
						}
						if(totalService!=0){
						for(int count=0; count<services.size();count++)
						{
							csPublish= conn.prepareCall(sqlinsertViewPartialPublish);	
							csPublish.setLong(1, Long.valueOf(services.get(count)));
							csPublish.setLong(2, 1);//isPublish
							csPublish.setString(3, "");
							csPublish.setInt(4, 0);
							csPublish.setString(5, "");
							csPublish.setLong(6, 1);
							csPublish.setLong(7, 4);
							csPublish.execute();
							status=csPublish.getInt(3);												
							if(status==-1)
							{
								msgPartialPublishRes=csPublish.getString(5);
								conn.rollback();
								break;
							}
							else
							{								
								msgPartialPublishRes="SUCCESS";
							}
						}																			
						}else{
							CLEPUtility.SysErr("--------- service is not available against orderNo:-"+orderNo+" ----------");							
							msgPartialPublishRes="FAILURE";
							String msg="service is not available against orderNo:-"+orderNo;
							String str="<orderHeader><NewOrder><OrderType>NEWORDER PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsID+"</MessageId><Reason>Failed Due to Invalid Information</Reason></OrderExtAttributes><MainTab><ErrorMsg>NO ERROR</ErrorMsg></MainTab><ContactTab><ErrorMsg>NO ERROR</ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg>NO ERROR</ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg>"+msg+"</ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg>NO ERROR</ErrorMsg></Charges></NewOrder></orderHeader>";
							/*CLEPXmlDto xmldto=new CLEPXmlDto();
							xmldto.setXmlData(str);
							ParseXMLforCLEP.sendXMLtoMPP(xmldto,xmlfileid,"Y",conn);*/
							successStatus=0;
							conn.rollback();
						}
						CLEPUtility.SysErr("---------- Message after Partial publish:-"+msgPartialPublishRes+" -----------");												
						//===================================PUBLISHING==========================
						if("SUCCESS".equalsIgnoreCase(msgPartialPublishRes)){
							newOrderDto.setOrderInfo_OrderType("NEW");
							newOrderDto.setPublished_by_empid(1);
							newOrderDto.setPublished_by_roleid(4);
							int publishStatus=objViewOrderDao.getPublishResult(orderNo, "NEW", newOrderDto,conn,"","","","");//last 2 parameter added by saurabh to handle partial publish
							if(publishStatus==0){
								CLEPUtility.SysErr("-------- Publish is done successfully against orderNo:-"+orderNo+" ----------");	
								CLEPUtility.SysErr("-------- Billing Trigger is start against orderNo:-"+orderNo+" >>>>>>>>>>");
								//==============================BILLING TRIGGER START===============================
									ArrayList<ViewOrderDto> alSelectServiceDetails =new ArrayList<ViewOrderDto>();
									ArrayList<ViewOrderDto> requireLineItemForBT =new ArrayList<ViewOrderDto>();
									BillingTriggerValidation billingTriggerValidation=new BillingTriggerValidation();
									ViewOrderDto eachLineItemDto=new ViewOrderDto();
									ViewOrderDto requireLineItemDto=new ViewOrderDto();
									ViewOrderDto validateInfo=new ViewOrderDto();
									
									String statusForRequireLineItemForBT="";
									String strBillingTrigger="";
									String ServiceProductId="";
									String LOCNo="",LOCdate="",BillingTriggerDate="",LocReceiveDate="";	
									
									viewOrderDto.setPonum(orderNo);
									alSelectServiceDetails=objViewOrderDao.getSELECT_BT_PRODUCTS_FOR_CHANGE_ORDERS(viewOrderDto, conn);
									newOrderDto=objViewOrderDao.getOrderType(conn, orderNo);
									
									if(alSelectServiceDetails.size()>0){
										for(int i=0;i<alSelectServiceDetails.size();i++){											
											eachLineItemDto=alSelectServiceDetails.get(i);
											if(alSelectServiceDetails.get(i).getLineNumber()!=null){
												billingTriggerValidation.resetStateToNew();
												billingTriggerValidation.setSourceData(eachLineItemDto, newOrderDto, null);
												billingTriggerValidation.computeProperties();
												statusForRequireLineItemForBT=billingTriggerValidation.getBillingTriggerActionStatus();
												if("required".equalsIgnoreCase(statusForRequireLineItemForBT)){
													requireLineItemForBT.add(eachLineItemDto);
													CLEPUtility.SysErr("------- Billing Trigger Require for LineItem:="+alSelectServiceDetails.get(i).getLineNumber()+" -------");
												}
											}
										}
										if(requireLineItemForBT.size()>0){
											for(int i=0;i<requireLineItemForBT.size();i++){
												
												ServiceProductId=requireLineItemForBT.get(i).getLineNumber().trim();	
												
												if(!"".equalsIgnoreCase(ServiceProductId)||ServiceProductId!=null){
													csGetLOCBillTrgDate= conn.prepareCall(spGetLocNoLocDateBillinTrgDate);	
													csGetLOCBillTrgDate.setLong(1, xmlfileid);
													csGetLOCBillTrgDate.setLong(2, Long.valueOf(ServiceProductId));
													csGetLOCBillTrgDate.setLong(3, orderNo);
													csGetLOCBillTrgDate.setInt(4, 0);//Billing Trigger Will be automated
													csGetLOCBillTrgDate.setLong(5, 0);
													csGetLOCBillTrgDate.setString(6, "");
													csGetLOCBillTrgDate.setString(7, "");
													csGetLOCBillTrgDate.setString(8, "");
													csGetLOCBillTrgDate.setString(9, "");
													csGetLOCBillTrgDate.setString(10, "");
													csGetLOCBillTrgDate.execute();
													
													LOCNo=csGetLOCBillTrgDate.getString(7);
													LOCdate=csGetLOCBillTrgDate.getString(8);
													BillingTriggerDate=csGetLOCBillTrgDate.getString(9);
													LocReceiveDate=csGetLOCBillTrgDate.getString(10);
													CLEPUtility.SysErr("------ LineItem = "+ServiceProductId+" LOCNO = "+LOCNo+" LOCdate = "+LOCdate+" BillingTriggerDate = "+BillingTriggerDate+" LocReceiveDate = "+LocReceiveDate+" -------");
												}
												if("".equals(strBillingTrigger))
												{
													if((LOCNo!=null && LOCdate !=null && BillingTriggerDate !=null)&&(!"".equalsIgnoreCase(LOCNo) && !"".equalsIgnoreCase(LOCdate) && !"".equalsIgnoreCase(BillingTriggerDate))){
														strBillingTrigger=ServiceProductId+"~"+LOCNo.trim()+"~"+LOCdate.trim()+"~"+BillingTriggerDate.trim()+"~"+LocReceiveDate.trim()+"~"+"abc";
													}
												}
												else
												{
													if((LOCNo!=null && LOCdate !=null && BillingTriggerDate !=null)&&(!"".equalsIgnoreCase(LOCNo) && !"".equalsIgnoreCase(LOCdate) && !"".equalsIgnoreCase(BillingTriggerDate))){
														strBillingTrigger=strBillingTrigger+"@@"+ServiceProductId+"~"+LOCNo.trim()+"~"+LOCdate.trim()+"~"+BillingTriggerDate.trim()+"~"+LocReceiveDate.trim()+"~"+"abc";
													}
												}																								
											}
											requireLineItemDto=new ViewOrderDto();
											requireLineItemDto.setOrderNo(String.valueOf(orderNo));
											requireLineItemDto.setBillingTriggerString(strBillingTrigger);
											CLEPUtility.SysErr("-------- Billing Trigger String is sending for validate:-"+strBillingTrigger+" >>>>>>>>>>");
											AjaxValidation ajaxValidation=new AjaxValidation();
											validateInfo=ajaxValidation.validateActiveDate(requireLineItemDto,conn,"Y",null,0);											
											int pushFlagError=-1;
											long longServiceProductId=0l;
											NewOrderDto newDtoFXChargeStatus=new NewOrderDto();
											if("SUCCESS".equalsIgnoreCase(validateInfo.getIfAnyFailValidation())){
												for(int i=0;i<requireLineItemForBT.size();i++){
													
													longServiceProductId=Long.valueOf(requireLineItemForBT.get(i).getLineNumber().trim());	
													
													if(longServiceProductId !=0){
														csGetLOCBillTrgDate= conn.prepareCall(spGetLocNoLocDateBillinTrgDate);	
														csGetLOCBillTrgDate.setLong(1, xmlfileid);
														csGetLOCBillTrgDate.setLong(2, Long.valueOf(longServiceProductId));
														csGetLOCBillTrgDate.setLong(3, orderNo);
														csGetLOCBillTrgDate.setInt(4, 0);//Billing Trigger Will be automated
														csGetLOCBillTrgDate.setLong(5, 0);
														csGetLOCBillTrgDate.setString(6, "");
														csGetLOCBillTrgDate.setString(7, "");
														csGetLOCBillTrgDate.setString(8, "");
														csGetLOCBillTrgDate.setString(9, "");
														csGetLOCBillTrgDate.setString(10, "");
														csGetLOCBillTrgDate.execute();
														
														LOCNo=csGetLOCBillTrgDate.getString(7);
														LOCdate=csGetLOCBillTrgDate.getString(8);
														BillingTriggerDate=csGetLOCBillTrgDate.getString(9);
														LocReceiveDate=csGetLOCBillTrgDate.getString(10);
													}
													
													if(longServiceProductId !=0 && LOCNo !=null && LOCdate !=null && BillingTriggerDate !=null && LocReceiveDate !=null){
														newDtoFXChargeStatus=objViewOrderDao.pushChargesInSecondaryTablesForChangeOrders(conn, longServiceProductId, LOCNo, LOCdate, BillingTriggerDate, LocReceiveDate, orderNo,"Y",-1);//Start:[001]
													}
													if(!"SUCCESS".equalsIgnoreCase(newDtoFXChargeStatus.getBillingTriggerStatus())){
														pushFlagError=1;
														CLEPUtility.SysErr("------- ChargesPush FAILURE for LineItem:="+String.valueOf(longServiceProductId)+" ---------");
													}else{
														CLEPUtility.SysErr("------- ChargesPush SUCCESS for LineItem:="+String.valueOf(longServiceProductId)+" ---------");
													}
												}	
												if(pushFlagError==1){
													CLEPUtility.SysErr("------- Billing Trigger is FAILED against due to "+newDtoFXChargeStatus.getBillingTriggerMsg()+" ------------");																			
													String msg="Billing Trigger is FAILED against orderNo:-"+orderNo+" due to "+newDtoFXChargeStatus.getBillingTriggerMsg();
													String str="<orderHeader><NewOrder><OrderType>NEWORDER PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsID+"</MessageId><Reason>Failed Due to Invalid Information</Reason></OrderExtAttributes><MainTab><ErrorMsg>NO ERROR</ErrorMsg></MainTab><ContactTab><ErrorMsg>NO ERROR</ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg>NO ERROR</ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg>"+msg+"</ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg>NO ERROR</ErrorMsg></Charges></NewOrder></orderHeader>";
													/*CLEPXmlDto xmldto=new CLEPXmlDto();
													xmldto.setXmlData(str);
													ParseXMLforCLEP.sendXMLtoMPP(xmldto,xmlfileid,"Y",conn);*/
													successStatus=0;
													conn.rollback();
												}else{
													CLEPUtility.SysErr("---------- Billing Trigger is SUCCESS against orderNo:-"+orderNo+" ------------");
													//---------Fx Account create Part Will be remove on dployement ,this task will be do by schedular
													CLEPUtility.SysErr("---------- Account Creating in FX Start against orderNo:-"+orderNo+" >>>>>>>>>");
													//CreateAccount account=new CreateAccount();
													//ViewOrderDao dao=new ViewOrderDao();
													//account.createAccount(conn ,orderNo);
													//-------------------------------------------------------------------------------------------------
													//dao.getINTERNALID(orderNo,conn);													
													CLEPUtility.SysErr("-------- Billing Trigger is END staus update against orderNo:-"+orderNo+" >>>>>>>>");
													//objViewOrderDao.setBTEndIfPossible(orderNo,conn);
													conn.commit();
													successStatus=1;
												}
											}else{
												CLEPUtility.SysErr("-------- Billing Trigger is FAILED against orderNo:-"+orderNo+" --------");
												CLEPUtility.SysErr("-------- THE REASON OF FAIL IS=> "+validateInfo.getBillingTriggerValidationErrors());
												
												String msg="Billing Trigger is FAILED against orderNo:-"+orderNo;
												String str="<orderHeader><NewOrder><OrderType>NEWORDER PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsID+"</MessageId><Reason>Failed Due to Invalid Information</Reason></OrderExtAttributes><MainTab><ErrorMsg>NO ERROR</ErrorMsg></MainTab><ContactTab><ErrorMsg>NO ERROR</ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg>NO ERROR</ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg>"+msg+"</ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg>NO ERROR</ErrorMsg></Charges></NewOrder></orderHeader>";
												/*CLEPXmlDto xmldto=new CLEPXmlDto();
												xmldto.setXmlData(str);
												ParseXMLforCLEP.sendXMLtoMPP(xmldto,xmlfileid,"Y",conn);*/
												successStatus=0;
												conn.rollback();
											}
											
										}else{
											
											CLEPUtility.SysErr("------- Error: No Billing Trigger required against orderNo:-"+orderNo+" --------");																		
											String msg="No Billing Trigger required against orderNo "+orderNo;
											String str="<orderHeader><NewOrder><OrderType>NEWORDER PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsID+"</MessageId><Reason>Failed Due to Invalid Information</Reason></OrderExtAttributes><MainTab><ErrorMsg>NO ERROR</ErrorMsg></MainTab><ContactTab><ErrorMsg>NO ERROR</ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg>NO ERROR</ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg>"+msg+"</ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg>NO ERROR</ErrorMsg></Charges></NewOrder></orderHeader>";
											/*CLEPXmlDto xmldto=new CLEPXmlDto();
											xmldto.setXmlData(str);
											ParseXMLforCLEP.sendXMLtoMPP(xmldto,xmlfileid,"Y",conn);*/
											successStatus=0;
											conn.rollback();
										}
									}else{
										CLEPUtility.SysErr("---------Error: No any LineItem available against orderNo:-"+orderNo+" --------");							
										msgPartialPublishRes="FAILURE";
										String msg="Billing Trigger is Failed against orderNo:-"+orderNo;
										String str="<orderHeader><NewOrder><OrderType>NEWORDER PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsID+"</MessageId><Reason>Failed Due to Invalid Information</Reason></OrderExtAttributes><MainTab><ErrorMsg>NO ERROR</ErrorMsg></MainTab><ContactTab><ErrorMsg>NO ERROR</ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg>NO ERROR</ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg>"+msg+"</ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg>NO ERROR</ErrorMsg></Charges></NewOrder></orderHeader>";
										/*CLEPXmlDto xmldto=new CLEPXmlDto();
										xmldto.setXmlData(str);
										ParseXMLforCLEP.sendXMLtoMPP(xmldto,xmlfileid,"Y",conn);*/
										successStatus=0;
										conn.rollback();
									}
								//==============================BILLING TRIGGER END=================================
							}else{
								CLEPUtility.SysErr("------- publish is Failed against orderNo:-"+orderNo+" --------");							
								msgPartialPublishRes="FAILURE";
								String msg="publish is Failed against orderNo:-"+orderNo;
								String str="<orderHeader><NewOrder><OrderType>NEWORDER PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsID+"</MessageId><Reason>Failed Due to Invalid Information</Reason></OrderExtAttributes><MainTab><ErrorMsg>NO ERROR</ErrorMsg></MainTab><ContactTab><ErrorMsg>NO ERROR</ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg>NO ERROR</ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg>"+msg+"</ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg>NO ERROR</ErrorMsg></Charges></NewOrder></orderHeader>";
								/*CLEPXmlDto xmldto=new CLEPXmlDto();
								xmldto.setXmlData(str);
								ParseXMLforCLEP.sendXMLtoMPP(xmldto,xmlfileid,"Y",conn);*/
								successStatus=0;
								conn.rollback();
							}							
						}else{
							CLEPUtility.SysErr("-------- Partial publish is Failed against orderNo:-"+orderNo+" ---------");							
							msgPartialPublishRes="FAILURE";
							String msg="Partial publish is Failed against orderNo:-"+orderNo;
							String str="<orderHeader><NewOrder><OrderType>NEWORDER PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsID+"</MessageId><Reason>Failed Due to Invalid Information</Reason></OrderExtAttributes><MainTab><ErrorMsg>NO ERROR</ErrorMsg></MainTab><ContactTab><ErrorMsg>NO ERROR</ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg>NO ERROR</ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg>"+msg+"</ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg>NO ERROR</ErrorMsg></Charges></NewOrder></orderHeader>";
							/*CLEPXmlDto xmldto=new CLEPXmlDto();
							xmldto.setXmlData(str);
							ParseXMLforCLEP.sendXMLtoMPP(xmldto,xmlfileid,"Y",conn);*/
							successStatus=0;
							conn.rollback();
						}						
					  }
					}
				}
				}else{
					CLEPUtility.SysErr("---------- Error:workflow cannot closed task is not available! ----------");					
				}
			}else{
				CLEPUtility.SysErr("----------- Due to error, PO can not validate!! ---------");
				String msg="Due to error, PO can not validate!!";
				String str="<orderHeader><NewOrder><OrderType>NEWORDER PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsID+"</MessageId><Reason>Failed Due to Invalid Information</Reason></OrderExtAttributes><MainTab><ErrorMsg>NO ERROR</ErrorMsg></MainTab><ContactTab><ErrorMsg>NO ERROR</ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg>"+msg+"</ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg>NO ERROR</ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg>NO ERROR</ErrorMsg></Charges></NewOrder></orderHeader>";
				/*CLEPXmlDto xmldto=new CLEPXmlDto();
				xmldto.setXmlData(str);
				ParseXMLforCLEP.sendXMLtoMPP(xmldto,xmlfileid,"Y",conn);*/
				successStatus=0;
				conn.rollback();
			}						
		}else{
			String str="<orderHeader><NewOrder><OrderType>NEWORDER PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsID+"</MessageId><Reason>Failed Due to Invalid Information</Reason></OrderExtAttributes><MainTab><ErrorMsg>NO ERROR</ErrorMsg></MainTab><ContactTab><ErrorMsg>NO ERROR</ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg>NO ERROR</ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg>NO ERROR</ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg>"+retString+"</ErrorMsg></ProductCatelog><Charges><ErrorMsg>NO ERROR</ErrorMsg></Charges></NewOrder></orderHeader>";
			/*CLEPXmlDto xmldto=new CLEPXmlDto();
			xmldto.setXmlData(str);
			ParseXMLforCLEP.sendXMLtoMPP(xmldto,xmlfileid,"Y",conn);*/
			successStatus=0;
			conn.rollback();
		}
		}catch(Exception e){
			successStatus=0;
			conn.rollback();
			CLEPUtility.LOG(true, false, e, "from method processOnlyNoNM6Product() for other err");
			String missingAttribute="[Internal error]{}";				
			CLEPXmlDto clepXmlDto=new CLEPXmlDto();
			clepXmlDto.setXmlData("<orderHeader><NewOrder><OrderType>NEW PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML,missing Attribute "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg></ErrorMsg></Charges></NewOrder></orderHeader>");			
			clepXmlDto.setJmsMessageID(jmsID);
			ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);
		}
		return successStatus;
	}	
	/*Method:getAllServices	 
	 * Purpose:To Calculate Tax Rate and update each charge created by mpp	 
	 * */
	public static int getAllServices(long orderno,Connection conn)throws Exception{
		ArrayList<Integer> serviceId=new ArrayList<Integer>();
		ArrayList<NewOrderDto>ALNewDto=new ArrayList<NewOrderDto>();
		ResultSet rs=null;
		CallableStatement csServices=null;
		CallableStatement csPOdetailChargeNameId=null;
		CallableStatement csUpdateTaxRate=null;
		long chargeinfoId=0l,chargeNameId=0l,podetailNo=0l;
		NewOrderDao objDao=new NewOrderDao();
		NewOrderDto objNewDto=null;
		int updateStatus=-1;
		try{
			csServices=conn.prepareCall("SELECT SERVICEID FROM IOE.TPOSERVICEMASTER WHERE ORDERNO=?");
			csServices.setLong(1, orderno);
			rs=csServices.executeQuery();
			while(rs.next()){
				serviceId.add(rs.getInt("SERVICEID"));
			}
			//get all podetail and chargenameid wst serviceid
			for(int i=0;i<serviceId.size();i++){
				csPOdetailChargeNameId=conn.prepareCall("SELECT CHARGEINFOID,CHARGENAME_ID,PODETAILNO FROM IOE.TCHARGES_INFO WHERE CREATEDIN_SERVICEID=?");
				csPOdetailChargeNameId.setInt(1, serviceId.get(i));
				rs=csPOdetailChargeNameId.executeQuery();
				while(rs.next()){
					objNewDto=new NewOrderDto();
					objNewDto.setChargeNameID(rs.getInt("CHARGENAME_ID"));
					objNewDto.setChargeInfoID(rs.getInt("CHARGEINFOID"));
					objNewDto.setPoDetailNo(rs.getString("PODETAILNO"));
					ALNewDto.add(objNewDto);
				}
			}
			for(int i=0;i<ALNewDto.size();i++){
				chargeinfoId=ALNewDto.get(i).getChargeInfoID();
				chargeNameId=ALNewDto.get(i).getChargeNameID();
				podetailNo=Long.valueOf(ALNewDto.get(i).getPoDetailNo());
				String taxRate=objDao.populateTaxRate(podetailNo, chargeNameId, conn);
				csUpdateTaxRate=conn.prepareCall("UPDATE IOE.TCHARGES_INFO SET TAXRATE=? WHERE CHARGEINFOID=?");
				csUpdateTaxRate.setString(1,taxRate );
				csUpdateTaxRate.setLong(2, chargeinfoId);
				csUpdateTaxRate.execute();
				updateStatus=1;
			}			
		}catch(Exception e){
			CLEPUtility.LOG(true, false, e, "from method getAllServices() for other err");
			updateStatus=0;
		}
		return updateStatus;
	}
	
	/*Method:attachBillableAccountToResponse
	 * Purpose:Attaching Billable Account and send to mpp(seventeen parameter after successfully billing trigger)
	 * */
	public static String attachBillableAccountToResponse(long orderNo,long xmlfileid,String resonseMsg,String jmsID,Connection conn){
		CLEPUtility.SysErr("------------- Attaching Billable Account to the Response >>>>>>>>>>");	
		String attachedBillAccount="",searchWord="",billActId="";
		boolean isBillabelAccountCreated=true;
		
		try{
			CallableStatement csGetBillableAccount=conn.prepareCall("call IOE.SPCLEP_GETBILLABLEACCOUNTID(?)");
			csGetBillableAccount.setLong(1,orderNo);
			ResultSet rsbillableAccountList=csGetBillableAccount.executeQuery();
						
			while(rsbillableAccountList.next()){
				searchWord=rsbillableAccountList.getString("SEARCHINGWORD");
				billActId=rsbillableAccountList.getString("FX_ACCOUNT_EXTERNAL_ID");
				if(billActId!=null){					
					resonseMsg=CLEPUtility.replaceWord(resonseMsg, searchWord, billActId);	
					isBillabelAccountCreated=true;
				}else{
					isBillabelAccountCreated=false;
				}
			}
			if(isBillabelAccountCreated){
				attachedBillAccount=resonseMsg;
			}else{
				attachedBillAccount="FAILED";
			}
			
		}catch(Exception e){						
			CLEPUtility.LOG(true, false, e, "from method attachBillableAccountToResponse() for other err");
			String missingAttribute="[Internal error]{}";				
			CLEPXmlDto clepXmlDto=new CLEPXmlDto();
			clepXmlDto.setXmlData("<orderHeader><NewOrder><OrderType>NEW PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML,missing Attribute "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg></ErrorMsg></Charges></NewOrder></orderHeader>");			
			clepXmlDto.setJmsMessageID(jmsID);
			ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);
		}
		return attachedBillAccount;
	}
	/*Method:validateSaaSErP
	 * Purpose:To validate order of workflow,after successfully validation order will be available for AM(Account Manager) for approval
	 * */
	public static int validateSaaSErP(long orderNo,long xmlfileid,String jmsID,String workFlwoId,Connection conn){
		int successFlag=-1,isInsertedDataintoTemp=-1,isDataTypeValidateSuccess=-1;
		CallableStatement csWorkflow = null;
		CallableStatement csValidate = null;
		ResultSet rsWorkflow=null;
		ResultSet rsIOMS=null;
		int taskId=0;
		String taskOwnerId=null;
		String retString="";
		CLEPXmlDto clepDtoPrjMngr=new CLEPXmlDto();
		NewOrderDao objNewDao=new NewOrderDao();
		ViewOrderDto objViewDto=new ViewOrderDto();
		try{
			CLEPUtility.SysErr("------- PO Validating start for Message Req:="+jmsID+" >>>>>>>>>>>");		
			csValidate=conn.prepareCall(sqlSpValidatePODetail);
			csValidate.setLong(1,orderNo);	//order no
			rsIOMS=csValidate.executeQuery();
			
			while(rsIOMS.next()){
				retString = "Contract Period Does not Match with Frequency Seleceted for PO Detail No ";
			}
			if("".equalsIgnoreCase(retString)){
				csWorkflow= conn.prepareCall(sqlSpValidatePO);	
				csWorkflow.setLong(1, orderNo);//order no
				csWorkflow.setLong(2, 1);//empid
				csWorkflow.setString(3, "");
				csWorkflow.setLong(4, 0);
				csWorkflow.setString(5, "");
				csWorkflow.execute();
			
				int err = csWorkflow.getInt(4);
				if(err==0){				
					CLEPUtility.SysErr("------ Po Validated Successfully!! -------");
				/*Here now dynemic workflow is going to inserted like normal order */
					OrderHeaderDTO objDto = new OrderHeaderDTO();
					objDto.setPoNumber((int)orderNo);
					objDto.setProjectWorkflowId(workFlwoId);
					
					int totalTaskId = objNewDao.attachworkflowForChangeOrder(objDto, conn);
					
					/*
					 * Following codes are commenting because now the project workflow is going to dynamic,
					 * and inserted on the behalf of workflow id that is receiving form XML file
					 *  */
					
					//csWorkflow = conn.prepareCall(sqlGet_TaskListOfOrder);
					//csWorkflow.setLong(1,orderNo);//order no
					//rsWorkflow=csWorkflow.executeQuery();
					
					//int totalTaskId=0;
					/*while (rsWorkflow.next()){
					
						taskId=rsWorkflow.getInt("TASKID"); 		
						taskOwnerId=rsWorkflow.getString("OWNERTYPE_ID");	
						totalTaskId++;
					}	*/							
				
				if(totalTaskId!=0){					
						conn.commit();
						CLEPUtility.SysErr("-------- Order Aproval is available for AM >>>>>>>>>>>");						
						successFlag=1;	
						
						
				}				
			}else{
				CLEPUtility.SysErr("-------- PO Validation Failed --------");
				successFlag=0;	
			}
			}else{
				String missingAttribute="PO Validation Failed Due to"+retString;	
				CLEPUtility.SysErr("-------- PO Validation Failed Due to"+retString);
				/*CLEPXmlDto clepXmlDto=new CLEPXmlDto();
				clepXmlDto.setXmlData("<orderHeader><NewOrder><OrderType>NEW PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsID+"</MessageId><Reason>Failed Due to Invalid XML,missing Attribute "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg></ErrorMsg></Charges></NewOrder></orderHeader>");			
				clepXmlDto.setJmsMessageID(jmsID);
				ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y",conn);*/
			}
		}catch(Exception e){						
			CLEPUtility.LOG(true, false, e, "from method validateSaaSErP() for other err");
			String missingAttribute="[Internal error]{}";				
			CLEPXmlDto clepXmlDto=new CLEPXmlDto();
			clepXmlDto.setXmlData("<orderHeader><NewOrder><OrderType>NEW PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML,missing Attribute "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg></ErrorMsg></Charges></NewOrder></orderHeader>");			
			clepXmlDto.setJmsMessageID(jmsID);
			ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);
		}
		finally
		{
			try
			{
			DbConnection.closeResultset(rsIOMS);
			DbConnection.closeResultset(rsWorkflow);
			DbConnection.closeCallableStatement(csValidate);
			DbConnection.closeCallableStatement(csWorkflow);
			}
			catch(Exception ex)
			{
				CLEPUtility.LOG(true, false, ex, "from method validateSaaSErP() while closing resultsets and statements");
			}
		}
		return successFlag;
	}
	/*Method:getProjectManagerIdAndUserID
	 * Purpose:To find project manager associate with account request for order for auto aprove
	 * */
	public static CLEPXmlDto getProjectManagerIdAndUserID(long orderNo,Connection conn){
		CLEPUtility.SysErr("Finding Project Manager >>>>>>>>");
		CallableStatement csIOMS=null;	
		ResultSet res=null;
		CLEPXmlDto clepxmldto=new CLEPXmlDto();
		int isfound=-1;
		try{
			csIOMS=conn.prepareCall("SELECT PROJECTMGRID FROM IOE.TM_ACCOUNT WHERE ACCOUNTID in(SELECT ACCOUNTID FROM IOE.TPOMASTER WHERE ORDERNO=?)");
			csIOMS.setLong(1,orderNo);			
			res=csIOMS.executeQuery();
			while(res.next()){
				clepxmldto.setProjectManagerId(res.getLong("PROJECTMGRID"));
				isfound=1;
			}
			if(isfound!=1){
				clepxmldto.setProjectManagerId(0);
				clepxmldto.setUserId("NA");
			}
		}catch(Exception ex){
			CLEPUtility.LOG(true, false, ex, "from method getProjectManagerIdAndUserID()");
		}finally{
			try
			{
			DbConnection.closeResultset(res);			
			DbConnection.closeCallableStatement(csIOMS);			
			}
			catch(Exception ex)
			{
				CLEPUtility.LOG(true, false, ex, "from method getProjectManagerIdAndUserID() while closing resultsets and statements");
			}
		}
		return clepxmldto;
	}
	/*Method:processOnlyNoNM6ProductAfterAproval
	 * Purpose:To Process Request for BillingTrigger(RFBT) after final aproved by last aproval in workflow
	 * In this method Saving BillingTrigger Date for each lineitems
	 * */
	public static int processOnlyNoNM6ProductAfterAproval(long orderNo,long xmlfileid,String serviceType,Connection conn,CLEPXmlDto clepXmlDto)throws Exception{			
		CallableStatement csGetLOCBillTrgDate = null;
		CallableStatement csGetServiceProductId = null;		
		ResultSet rsServiceProductIds=null;		
		int successStatus=-1;		
		String jmsID="";
		String prjMngrId="";
		try{		
			//############################## RFBT PROCESSING START #######################################
																																																				
							jmsID=clepXmlDto.getJmsMessageID();
							int isDataSaved=-1;
							long longServiceProductId=0l;
							String LOCNo="",LOCdate="",BillingTriggerDate="",LocReceiveDate="";	
							ArrayList<ViewOrderDto> requireLineItemForBT =new ArrayList<ViewOrderDto>();
							ViewOrderDto requireLineItemDto=null;
							CLEPUtility.SysErr("------- Fetching All Required LineItems for Saving BillingTrigger Date Details >>>>>>>");
							
							csGetServiceProductId=conn.prepareCall(spGetServiceProductIds);
							csGetServiceProductId.setLong(1, orderNo);
							rsServiceProductIds=csGetServiceProductId.executeQuery();
							while(rsServiceProductIds.next()){
								requireLineItemDto=new ViewOrderDto();
								requireLineItemDto.setLineNumber(rsServiceProductIds.getString("SERVICEPRODUCTID"));
								requireLineItemForBT.add(requireLineItemDto);
							}
							
							for(int i=0;i<requireLineItemForBT.size();i++){
													
								if(requireLineItemForBT.get(i).getLineNumber().trim()!=null){
									longServiceProductId=Long.valueOf(requireLineItemForBT.get(i).getLineNumber().trim());
								}
													
								if(longServiceProductId !=0){
										csGetLOCBillTrgDate= conn.prepareCall(spGetLocNoLocDateBillinTrgDate);	
										csGetLOCBillTrgDate.setLong(1, xmlfileid);
										csGetLOCBillTrgDate.setLong(2, Long.valueOf(longServiceProductId));
										csGetLOCBillTrgDate.setLong(3, orderNo);
										csGetLOCBillTrgDate.setLong(4, 1);//Billing Trigger Will be doing from GUI
										csGetLOCBillTrgDate.setLong(5, 0);
										csGetLOCBillTrgDate.setString(6, "");
										csGetLOCBillTrgDate.setString(7, "");
										csGetLOCBillTrgDate.setString(8, "");
										csGetLOCBillTrgDate.setString(9, "");
										csGetLOCBillTrgDate.setString(10, "");
										csGetLOCBillTrgDate.execute();
														
										LOCNo=csGetLOCBillTrgDate.getString(7);
										LOCdate=csGetLOCBillTrgDate.getString(8);
										BillingTriggerDate=csGetLOCBillTrgDate.getString(9);
										LocReceiveDate=csGetLOCBillTrgDate.getString(10);
										isDataSaved=1;successStatus=1;
									}	
								CLEPUtility.SysErr("-------- Saving LOCNo["+LOCNo+"],"+"LOCdate["+LOCdate+"],"+"BillingTriggerDate["+BillingTriggerDate+"],"+"LocReceiveDate["+LocReceiveDate+"] for LineItem["+longServiceProductId+"] -----------");
							}	
							if(isDataSaved==1){
								CLEPUtility.SysErr("-------- Data Saving Completed! Waiting for Billing Trigger Complete for OrderNo["+orderNo+"] ----------");
							}else{
								CLEPUtility.SysErr("-------- Data Saving Failed for OrderNo["+orderNo+"] Failed Auto Publishing and Billing Trigger --------");
							}
					//############################## RFBT PROCESSING END #######################################
							
					//############################## AUTO PUBLISH AND BILLING TRRIGER PROCESSING START #######################################
							if(successStatus==1){
								CLEPUtility.SysErr("--------- Processing [Auto Publishing and BillingTrigger] start for Order No:[ " +orderNo+" ] >>>>>>>>");							
								ViewOrderDto objServiceTypeOrdSrcDto=new ViewOrderDto();
								NewOrderDao objNewDao = new NewOrderDao();
								int isSendToMPP=processAutoPUBLISH_BT_AfterRFBT(orderNo,xmlfileid,conn,jmsID);								
								if(isSendToMPP==1){
											
										//--------------------------[ Start: Sending Response to MPP ]--------------------------
																
											objServiceTypeOrdSrcDto=objNewDao.getServiceType_OrderSourceClepErp(orderNo,0);
											
											/*Now if getServiceTypeID() =2 or 1, response would be send. getServiceTypeID() means Attribute1 in xml.  
											 * Now this condition "objServiceTypeOrdSrcDto.getServiceTypeID()==2"  is going to commented */ 

											if(/*objServiceTypeOrdSrcDto.getServiceTypeID()==2 &&*/ "2".equalsIgnoreCase(objServiceTypeOrdSrcDto.getOrder_creation_source())){
												if(objServiceTypeOrdSrcDto.getIsBTDone() > 0)
												{
													CLEPUtility.SysErr("-------- Finding Response for sending to MPP >>>>>>>>>");
													if(objServiceTypeOrdSrcDto.getResponseClepOrderMsgAfterAproval()!=null){														
														clepXmlDto.setXmlData(objServiceTypeOrdSrcDto.getResponseClepOrderMsgAfterAproval());
														CLEPUtility.SysErr("Mesaage Request ID Was:- "+jmsID);														
														//Start [003]
														Connection newConnnection = null;
														String responseMsg=null;
														try{
															newConnnection = DbConnection.getConnectionObject();
															responseMsg=NewOrderDao.constructBTResponse(clepXmlDto,orderNo, xmlfileid,  newConnnection);//objNewDao.getRFBTResponseMsg(clepXmlDto,xmlfileid,orderNo,"BT");
															clepXmlDto.setXmlData(responseMsg);
															newConnnection.setAutoCommit(false);
															objNewDao.saveRFBTResponseMsg(clepXmlDto,xmlfileid,orderNo,"BT",responseMsg,newConnnection);
														}catch(Exception ex){
															Utility.LOG(ex);
														}
														finally{
															DbConnection.freeConnection(newConnnection);
														}
														
														

													}else{
														CLEPUtility.SysErr("--------Error: Finding Response for sending to MPP [ FAILED ] >>>>>>>>>");
													}
												}
										}
											//End [003]
			
									  //-----------------------------[ End: Sending Response to MPP ]------------------------------------------------																			
								 }else{								
									 CLEPUtility.SysErr("--------Error: Response Sending Failed !---------");
								 }
							}
					//############################## AUTO PUBLISH AND BILLING TRRIGER PROCESSING END #######################################
							
		}catch(Exception e){
			successStatus=0;
			conn.rollback();
			CLEPUtility.LOG(true, false, e, "from method processOnlyNoNM6ProductAfterAproval()");
			String missingAttribute="[Internal error]{}";				
			CLEPXmlDto clepDto=new CLEPXmlDto();
			clepDto.setXmlData("<orderHeader><NewOrder><OrderType>NEW PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML,missing Attribute "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg></ErrorMsg></Charges></NewOrder></orderHeader>");			
			clepDto.setJmsMessageID(jmsID);
			ParseXMLforCLEP.sendXMLtoMPP(clepDto,xmlfileid,"Y","N",conn);
		}
		return successStatus;
	}		
	/*Method:getResponseMessageForBT
	 * Purpose:To get created response for sending to mpp after successfully BillingTrigger
	 * */
	public static CLEPXmlDto getResponseMessageForBT(long orderNo,Connection conn){
		CLEPUtility.SysErr("--------- Fetching Response Message For BillingTrigger >>>>>>>>");
		CallableStatement csIOMS=null;	
		ResultSet res=null;
		CLEPXmlDto clepxmldto=new CLEPXmlDto();		
		try{
			csIOMS=conn.prepareCall("SELECT XMLSUCCESSDATA FROM IOE.TM_MPPNEW_TTEMPLATEFILE_PROCESS WHERE ORDERNO=? and ORDERTYPE IS NULL");
			csIOMS.setLong(1,orderNo);			
			res=csIOMS.executeQuery();
			while(res.next()){
				clepxmldto.setXmlData(res.getString("XMLSUCCESSDATA"));				
			}			
		}catch(Exception ex){
			CLEPUtility.LOG(true, false, ex, "from method getResponseMessageForBT()");
		}finally{
			try
			{
			DbConnection.closeResultset(res);			
			DbConnection.closeCallableStatement(csIOMS);			
			}
			catch(Exception ex)
			{
				CLEPUtility.LOG(true, false, ex, "from method getResponseMessageForBT() while closing resultsets and statements");
			}
		}
		return clepxmldto;
	}
	/*
	 * Method:getOrderValidForRFBT
	 * Purpose:this method find the reqesting rfbt order is valid or invalid
	 * */
	public static int getOrderValidForRFBT(long orderNo,String orderType,String lastAprovalOwnerRole,Connection conn){
		CLEPUtility.SysErr("--------- Fetching Valid Status For RFBT >>>>>>>>");
		CallableStatement csIOMS=null;	
		ResultSet res=null;
		int isValidOrder=-1;
		try{
			if("NEW".equalsIgnoreCase(orderType)){
				csIOMS=conn.prepareCall("select count(1) as isValid from ioe.TPOWORKFLOWTASK where  TASKSTATUS_ID=2 and OWNERTYPE_ID= ? and ORDERNO=?");
				csIOMS.setLong(1,Long.valueOf(lastAprovalOwnerRole));	
				csIOMS.setLong(2,orderNo);
				res=csIOMS.executeQuery();
				while(res.next()){
					isValidOrder=res.getInt("isValid");				
				}	
			}else if("CHANGE".equalsIgnoreCase(orderType)){
				csIOMS=conn.prepareCall("SELECT count(1) as isValid FROM IOE.TPOWORKFLOWTASK WHERE  IS_LAST_TASK=1 and TASKSTATUS_ID=2 and ORDERNO=?");
				csIOMS.setLong(1,orderNo);			
				res=csIOMS.executeQuery();
				while(res.next()){
					isValidOrder=res.getInt("isValid");				
				}
			}
		}catch(Exception ex){
			CLEPUtility.LOG(true, false, ex, "from method getOrderValidForRFBT()");
		}finally{
			try
			{
			DbConnection.closeResultset(res);			
			DbConnection.closeCallableStatement(csIOMS);			
			}
			catch(Exception ex)
			{
				CLEPUtility.LOG(true, false, ex, "from method getOrderValidForRFBT() while closing resultsets and statements");
			}
		}
		return isValidOrder;
	}
	/*Method:saveResponseMsgWithBillableAc
	 * Purpose:To save final response(with billable account)
	 * */
	public static void saveResponseMsgWithBillableAc(long orderNo,String responseMsg,Connection conn){
		CLEPUtility.SysErr("--------- Saving Response Message With Billable Account >>>>>>>>");
		CallableStatement csIOMS=null;					
		try{
			
				csIOMS=conn.prepareCall("UPDATE IOE.TM_MPPNEW_TTEMPLATEFILE_PROCESS SET XMLSUCCESSDATA=? WHERE ORDERNO=?");
				csIOMS.setString(1,responseMsg);	
				csIOMS.setLong(2,orderNo);
				csIOMS.execute();				
			
		}catch(Exception ex){
			CLEPUtility.LOG(true, false, ex, "from method saveResponseMsgWithBillableAc()");
		}finally{
			try
			{					
			DbConnection.closeCallableStatement(csIOMS);			
			}
			catch(Exception ex)
			{
				CLEPUtility.LOG(true, false, ex, "from method saveResponseMsgWithBillableAc() while closing resultsets and statements");
			}
		}
	}
	/*
	 * 
	 * method:processAutoPUBLISH_BT_AfterRFBT
	 * this method processing automate publishing and billing trriger process.
	 * */
	public static int processAutoPUBLISH_BT_AfterRFBT(long orderNo,long xmlfileid,Connection conn,String jmsID){
		int successStatus=-1;
		CallableStatement csValidationFieldSaaS=null;
		CallableStatement csPublish = null;		
		CallableStatement csGetLOCBillTrgDate = null;		
		ResultSet rsPublish=null;
		ViewOrderDao objViewOrderDao=new ViewOrderDao();
		OrderHeaderDTO newOrderDto=new OrderHeaderDTO();
		ViewOrderDto viewOrderDto=new ViewOrderDto();
		try{
			//------------------------------------------------------------------
				int status=0;
				String strValidateSaasRes=null,strValidateFieldRes=null,serviceIDList="",msgPartialPublishRes=null;
				
				csValidationFieldSaaS= conn.prepareCall(sqlGetValidateFeild_Sass);	
				csValidationFieldSaaS.setLong(1,orderNo);//order no	
				csValidationFieldSaaS.setString(2,null );	
				csValidationFieldSaaS.setString(3, null);	
				csValidationFieldSaaS.setString(4, null);
				csValidationFieldSaaS.setLong(5, 4);
				csValidationFieldSaaS.executeUpdate();
				strValidateSaasRes=csValidationFieldSaaS.getString(2);
				
				CLEPUtility.SysErr("------------ Message after SASS_FEILD_VALDATION:-"+strValidateSaasRes+" ----------");	
			//------------------------------------------------------------------
				if("SUCCESS".equalsIgnoreCase(strValidateSaasRes)){
					csValidationFieldSaaS= conn.prepareCall(sqlGetValidateFeild);	
					csValidationFieldSaaS.setLong(1, orderNo);//order no	
					csValidationFieldSaaS.setString(2,null );	
					csValidationFieldSaaS.setString(3, null);	
					csValidationFieldSaaS.setString(4, null);
					csValidationFieldSaaS.setString(5, null);
					csValidationFieldSaaS.setInt(6, 4);//roleid
					csValidationFieldSaaS.executeUpdate();
					strValidateFieldRes=csValidationFieldSaaS.getString(2);
					
					CLEPUtility.SysErr("----------- Message after ROLEWISE_VALIDATE_FEILDS:-"+strValidateFieldRes+" --------");						
					
					if("SUCCESS".equalsIgnoreCase(strValidateFieldRes)){																			
						csValidationFieldSaaS= conn.prepareCall(sqlspGetNoNM6ServiceListForOrder);	
						csValidationFieldSaaS.setLong(1, orderNo);//orderNo		
						rsPublish = csValidationFieldSaaS.executeQuery(); 
					int totalService=0;
					while(rsPublish.next())
					{																						
						serviceIDList=serviceIDList+String.valueOf(rsPublish.getInt("SERVICEID"))+",";
						totalService++;
					}
					ArrayList<String> services = new ArrayList<String>();
					StringTokenizer st = new StringTokenizer( serviceIDList, ",");									
					for (int i =0; st.hasMoreTokens();i++) 
					{
						services.add(st.nextToken());										
					}
					if(totalService!=0){
					for(int count=0; count<services.size();count++)
					{
						csPublish= conn.prepareCall(sqlinsertViewPartialPublish);	
						csPublish.setLong(1, Long.valueOf(services.get(count)));
						csPublish.setLong(2, 1);//isPublish
						csPublish.setString(3, "");
						csPublish.setInt(4, 0);
						csPublish.setString(5, "");
						csPublish.setLong(6, 1);
						csPublish.setLong(7, 4);
						csPublish.execute();
						status=csPublish.getInt(3);												
						if(status==-1)
						{
							msgPartialPublishRes=csPublish.getString(5);
							conn.rollback();
							break;
						}
						else
						{								
							msgPartialPublishRes="SUCCESS";
						}
					}																			
					}else{
						conn.rollback();
						CLEPUtility.SysErr("--------- service is not available against orderNo:-"+orderNo+" ----------");							
						msgPartialPublishRes="FAILURE";
						String msg="service is not available against orderNo:-"+orderNo;
						String str="<orderHeader><NewOrder><OrderType>NEWORDER PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsID+"</MessageId><Reason>Failed Due to Invalid Information</Reason></OrderExtAttributes><MainTab><ErrorMsg>NO ERROR</ErrorMsg></MainTab><ContactTab><ErrorMsg>NO ERROR</ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg>NO ERROR</ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg>"+msg+"</ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg>NO ERROR</ErrorMsg></Charges></NewOrder></orderHeader>";
						/*CLEPXmlDto xmldto=new CLEPXmlDto();
						xmldto.setXmlData(str);
						ParseXMLforCLEP.sendXMLtoMPP(xmldto,xmlfileid,"Y",conn);*/
						successStatus=0;
						
					}
					CLEPUtility.SysErr("---------- Message after Partial publish:-"+msgPartialPublishRes+" -----------");												
					//===================================PUBLISHING==========================
					if("SUCCESS".equalsIgnoreCase(msgPartialPublishRes)){
						newOrderDto.setOrderInfo_OrderType("NEW");
						newOrderDto.setPublished_by_empid(1);
						newOrderDto.setPublished_by_roleid(4);
						int publishStatus=objViewOrderDao.getPublishResult(orderNo, "NEW", newOrderDto,conn,"","","","");//last 2 parameter added by saurabh to handle partial publish
						if(publishStatus==0){
							CLEPUtility.SysErr("-------- Publish is done successfully against orderNo:-"+orderNo+" ----------");	
							CLEPUtility.SysErr("-------- Billing Trigger is start against orderNo:-"+orderNo+" >>>>>>>>>>");
							//==============================BILLING TRIGGER START===============================
								ArrayList<ViewOrderDto> alSelectServiceDetails =new ArrayList<ViewOrderDto>();
								ArrayList<ViewOrderDto> requireLineItemForBT =new ArrayList<ViewOrderDto>();
								BillingTriggerValidation billingTriggerValidation=new BillingTriggerValidation();
								ViewOrderDto eachLineItemDto=new ViewOrderDto();
								ViewOrderDto requireLineItemDto=new ViewOrderDto();
								ViewOrderDto validateInfo=new ViewOrderDto();
								
								String statusForRequireLineItemForBT="";
								String strBillingTrigger="";
								String ServiceProductId="";
								String LOCNo="",LOCdate="",BillingTriggerDate="",LocReceiveDate="";	
								
								viewOrderDto.setPonum(orderNo);
								alSelectServiceDetails=objViewOrderDao.getSELECT_BT_PRODUCTS_FOR_CHANGE_ORDERS(viewOrderDto, conn);
								newOrderDto=objViewOrderDao.getOrderType(conn, orderNo);
								
								if(alSelectServiceDetails.size()>0){
									for(int i=0;i<alSelectServiceDetails.size();i++){											
										eachLineItemDto=alSelectServiceDetails.get(i);
										if(alSelectServiceDetails.get(i).getLineNumber()!=null){
											billingTriggerValidation.resetStateToNew();
											billingTriggerValidation.setSourceData(eachLineItemDto, newOrderDto, null);
											billingTriggerValidation.computeProperties();
											statusForRequireLineItemForBT=billingTriggerValidation.getBillingTriggerActionStatus();
											if("required".equalsIgnoreCase(statusForRequireLineItemForBT)){
												requireLineItemForBT.add(eachLineItemDto);
												CLEPUtility.SysErr("------- Billing Trigger Require for LineItem:="+alSelectServiceDetails.get(i).getLineNumber()+" -------");
											}
										}
									}
									if(requireLineItemForBT.size()>0){
										for(int i=0;i<requireLineItemForBT.size();i++){
											
											ServiceProductId=requireLineItemForBT.get(i).getLineNumber().trim();	
											
											if(!"".equalsIgnoreCase(ServiceProductId)||ServiceProductId!=null){
												csGetLOCBillTrgDate= conn.prepareCall(spGetLocNoLocDateBillinTrgDate);	
												csGetLOCBillTrgDate.setLong(1, xmlfileid);
												csGetLOCBillTrgDate.setLong(2, Long.valueOf(ServiceProductId));
												csGetLOCBillTrgDate.setLong(3, orderNo);
												csGetLOCBillTrgDate.setInt(4, 0);//Billing Trigger Will be automated
												csGetLOCBillTrgDate.setLong(5, 0);
												csGetLOCBillTrgDate.setString(6, "");
												csGetLOCBillTrgDate.setString(7, "");
												csGetLOCBillTrgDate.setString(8, "");
												csGetLOCBillTrgDate.setString(9, "");
												csGetLOCBillTrgDate.setString(10, "");
												csGetLOCBillTrgDate.execute();
												
												LOCNo=csGetLOCBillTrgDate.getString(7);
												LOCdate=csGetLOCBillTrgDate.getString(8);
												BillingTriggerDate=csGetLOCBillTrgDate.getString(9);
												LocReceiveDate=csGetLOCBillTrgDate.getString(10);
												CLEPUtility.SysErr("------ LineItem = "+ServiceProductId+" LOCNO = "+LOCNo+" LOCdate = "+LOCdate+" BillingTriggerDate = "+BillingTriggerDate+" LocReceiveDate = "+LocReceiveDate+" -------");
											}
											if("".equals(strBillingTrigger))
											{
												if((LOCNo!=null && LOCdate !=null && BillingTriggerDate !=null)&&(!"".equalsIgnoreCase(LOCNo) && !"".equalsIgnoreCase(LOCdate) && !"".equalsIgnoreCase(BillingTriggerDate))){
													strBillingTrigger=ServiceProductId+"~"+LOCNo.trim()+"~"+LOCdate.trim()+"~"+BillingTriggerDate.trim()+"~"+LocReceiveDate.trim()+"~"+"abc";
												}
											}
											else
											{
												if((LOCNo!=null && LOCdate !=null && BillingTriggerDate !=null)&&(!"".equalsIgnoreCase(LOCNo) && !"".equalsIgnoreCase(LOCdate) && !"".equalsIgnoreCase(BillingTriggerDate))){
													strBillingTrigger=strBillingTrigger+"@@"+ServiceProductId+"~"+LOCNo.trim()+"~"+LOCdate.trim()+"~"+BillingTriggerDate.trim()+"~"+LocReceiveDate.trim()+"~"+"abc";
												}
											}																								
										}
										requireLineItemDto=new ViewOrderDto();
										requireLineItemDto.setOrderNo(String.valueOf(orderNo));
										requireLineItemDto.setBillingTriggerString(strBillingTrigger);
										CLEPUtility.SysErr("-------- Billing Trigger String is sending for validate:-"+strBillingTrigger+" >>>>>>>>>>");
										AjaxValidation ajaxValidation=new AjaxValidation();
										validateInfo=ajaxValidation.validateActiveDate(requireLineItemDto,conn,"Y",null,0);											
										int pushFlagError=-1;
										long longServiceProductId=0l;
										NewOrderDto newDtoFXChargeStatus=new NewOrderDto();
										if("SUCCESS".equalsIgnoreCase(validateInfo.getIfAnyFailValidation())){
											for(int i=0;i<requireLineItemForBT.size();i++){
												
												longServiceProductId=Long.valueOf(requireLineItemForBT.get(i).getLineNumber().trim());	
												
												if(longServiceProductId !=0){
													csGetLOCBillTrgDate= conn.prepareCall(spGetLocNoLocDateBillinTrgDate);	
													csGetLOCBillTrgDate.setLong(1, xmlfileid);
													csGetLOCBillTrgDate.setLong(2, Long.valueOf(longServiceProductId));
													csGetLOCBillTrgDate.setLong(3, orderNo);
													csGetLOCBillTrgDate.setInt(4, 0);//Billing Trigger Will be automated
													csGetLOCBillTrgDate.setLong(5, 0);
													csGetLOCBillTrgDate.setString(6, "");
													csGetLOCBillTrgDate.setString(7, "");
													csGetLOCBillTrgDate.setString(8, "");
													csGetLOCBillTrgDate.setString(9, "");
													csGetLOCBillTrgDate.setString(10, "");
													csGetLOCBillTrgDate.execute();
													
													LOCNo=csGetLOCBillTrgDate.getString(7);
													LOCdate=csGetLOCBillTrgDate.getString(8);
													BillingTriggerDate=csGetLOCBillTrgDate.getString(9);
													LocReceiveDate=csGetLOCBillTrgDate.getString(10);
												}
												
												if(longServiceProductId !=0 && LOCNo !=null && LOCdate !=null && BillingTriggerDate !=null && LocReceiveDate !=null){
													newDtoFXChargeStatus=objViewOrderDao.pushChargesInSecondaryTablesForChangeOrders(conn, longServiceProductId, LOCNo, LOCdate, BillingTriggerDate, LocReceiveDate, orderNo,"Y",-1);//Start:[001]
												}
												if(!"SUCCESS".equalsIgnoreCase(newDtoFXChargeStatus.getBillingTriggerStatus())){
													pushFlagError=1;
													CLEPUtility.SysErr("------- ChargesPush FAILURE for LineItem:="+String.valueOf(longServiceProductId)+" ---------");
												}else{
													CLEPUtility.SysErr("------- ChargesPush SUCCESS for LineItem:="+String.valueOf(longServiceProductId)+" ---------");
												}
											}	
											if(pushFlagError==1){
												conn.rollback();
												CLEPUtility.SysErr("------- Billing Trigger is FAILED against due to "+newDtoFXChargeStatus.getBillingTriggerMsg()+" ------------");																			
												String msg="Billing Trigger is FAILED against orderNo:-"+orderNo+" due to "+newDtoFXChargeStatus.getBillingTriggerMsg();
												String str="<orderHeader><NewOrder><OrderType>NEWORDER PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsID+"</MessageId><Reason>Failed Due to Invalid Information</Reason></OrderExtAttributes><MainTab><ErrorMsg>NO ERROR</ErrorMsg></MainTab><ContactTab><ErrorMsg>NO ERROR</ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg>NO ERROR</ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg>"+msg+"</ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg>NO ERROR</ErrorMsg></Charges></NewOrder></orderHeader>";
												/*CLEPXmlDto xmldto=new CLEPXmlDto();
												xmldto.setXmlData(str);
												ParseXMLforCLEP.sendXMLtoMPP(xmldto,xmlfileid,"Y",conn);*/
												successStatus=0;												
											}else{
												CLEPUtility.SysErr("---------- Billing Trigger is SUCCESS against orderNo:-"+orderNo+" ------------");
												//---------Fx Account create Part Will be remove on dployement ,this task will be do by schedular
												CLEPUtility.SysErr("---------- Account Creating in FX Start against orderNo:-"+orderNo+" >>>>>>>>>");
												//CreateAccount account=new CreateAccount();
												//ViewOrderDao dao=new ViewOrderDao();
												//account.createAccount(conn ,orderNo);
												//-------------------------------------------------------------------------------------------------
												//dao.getINTERNALID(orderNo,conn);													
												CLEPUtility.SysErr("-------- Billing Trigger is END staus update against orderNo:-"+orderNo+" >>>>>>>>");
												//objViewOrderDao.setBTEndIfPossible(orderNo,conn);
												conn.commit();
												successStatus=1;
											}
										}else{
											conn.rollback();
											CLEPUtility.SysErr("-------- Billing Trigger is FAILED against orderNo:-"+orderNo+" --------");																			
											String msg="Billing Trigger is FAILED against orderNo:-"+orderNo;
											String str="<orderHeader><NewOrder><OrderType>NEWORDER PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsID+"</MessageId><Reason>Failed Due to Invalid Information</Reason></OrderExtAttributes><MainTab><ErrorMsg>NO ERROR</ErrorMsg></MainTab><ContactTab><ErrorMsg>NO ERROR</ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg>NO ERROR</ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg>"+msg+"</ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg>NO ERROR</ErrorMsg></Charges></NewOrder></orderHeader>";
											/*CLEPXmlDto xmldto=new CLEPXmlDto();
											xmldto.setXmlData(str);
											ParseXMLforCLEP.sendXMLtoMPP(xmldto,xmlfileid,"Y",conn);*/
											successStatus=0;											
										}
										
									}else{
										conn.rollback();
										CLEPUtility.SysErr("------- Error: No Billing Trigger required against orderNo:-"+orderNo+" --------");																		
										String msg="No Billing Trigger required against orderNo "+orderNo;
										String str="<orderHeader><NewOrder><OrderType>NEWORDER PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsID+"</MessageId><Reason>Failed Due to Invalid Information</Reason></OrderExtAttributes><MainTab><ErrorMsg>NO ERROR</ErrorMsg></MainTab><ContactTab><ErrorMsg>NO ERROR</ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg>NO ERROR</ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg>"+msg+"</ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg>NO ERROR</ErrorMsg></Charges></NewOrder></orderHeader>";
										/*CLEPXmlDto xmldto=new CLEPXmlDto();
										xmldto.setXmlData(str);
										ParseXMLforCLEP.sendXMLtoMPP(xmldto,xmlfileid,"Y",conn);*/
										successStatus=0;										
									}
								}else{
									conn.rollback();
									CLEPUtility.SysErr("---------Error: No any LineItem available against orderNo:-"+orderNo+" --------");							
									msgPartialPublishRes="FAILURE";
									String msg="Billing Trigger is Failed against orderNo:-"+orderNo;
									String str="<orderHeader><NewOrder><OrderType>NEWORDER PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsID+"</MessageId><Reason>Failed Due to Invalid Information</Reason></OrderExtAttributes><MainTab><ErrorMsg>NO ERROR</ErrorMsg></MainTab><ContactTab><ErrorMsg>NO ERROR</ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg>NO ERROR</ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg>"+msg+"</ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg>NO ERROR</ErrorMsg></Charges></NewOrder></orderHeader>";
									/*CLEPXmlDto xmldto=new CLEPXmlDto();
									xmldto.setXmlData(str);
									ParseXMLforCLEP.sendXMLtoMPP(xmldto,xmlfileid,"Y",conn);*/
									successStatus=0;									
								}
							//==============================BILLING TRIGGER END=================================
						}else{
							conn.rollback();
							CLEPUtility.SysErr("------- publish is Failed against orderNo:-"+orderNo+" --------");							
							msgPartialPublishRes="FAILURE";
							String msg="publish is Failed against orderNo:-"+orderNo;
							String str="<orderHeader><NewOrder><OrderType>NEWORDER PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsID+"</MessageId><Reason>Failed Due to Invalid Information</Reason></OrderExtAttributes><MainTab><ErrorMsg>NO ERROR</ErrorMsg></MainTab><ContactTab><ErrorMsg>NO ERROR</ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg>NO ERROR</ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg>"+msg+"</ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg>NO ERROR</ErrorMsg></Charges></NewOrder></orderHeader>";
							/*CLEPXmlDto xmldto=new CLEPXmlDto();
							xmldto.setXmlData(str);
							ParseXMLforCLEP.sendXMLtoMPP(xmldto,xmlfileid,"Y",conn);*/
							successStatus=0;							
						}							
					}else{
						conn.rollback();
						CLEPUtility.SysErr("-------- Partial publish is Failed against orderNo:-"+orderNo+" ---------");							
						msgPartialPublishRes="FAILURE";
						String msg="Partial publish is Failed against orderNo:-"+orderNo;
						String str="<orderHeader><NewOrder><OrderType>NEWORDER PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsID+"</MessageId><Reason>Failed Due to Invalid Information</Reason></OrderExtAttributes><MainTab><ErrorMsg>NO ERROR</ErrorMsg></MainTab><ContactTab><ErrorMsg>NO ERROR</ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg>NO ERROR</ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg>"+msg+"</ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg>NO ERROR</ErrorMsg></Charges></NewOrder></orderHeader>";
						/*CLEPXmlDto xmldto=new CLEPXmlDto();
						xmldto.setXmlData(str);
						ParseXMLforCLEP.sendXMLtoMPP(xmldto,xmlfileid,"Y",conn);*/
						successStatus=0;						
					}						
				  }
				}
		}catch(Exception e){
			successStatus=0;
			try{
				conn.rollback();
			}catch(Exception exp){
				CLEPUtility.LOG(true, false, exp, "from method processAutoPUBLISH_BT_AfterRFBT() while undo transaction");
			}		
			CLEPUtility.LOG(true, false, e, "from method processAutoPUBLISH_BT_AfterRFBT()");			
			String missingAttribute="[Internal error]{}";				
			CLEPXmlDto clepXmlDto=new CLEPXmlDto();
			clepXmlDto.setXmlData("<orderHeader><NewOrder><OrderType>NEW PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML,missing Attribute "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg></ErrorMsg></Charges></NewOrder></orderHeader>");			
			clepXmlDto.setJmsMessageID(jmsID);
			ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);
		}finally{
			try
			{
				DbConnection.closeResultset(rsPublish);			
				DbConnection.closeCallableStatement(csValidationFieldSaaS);		
				DbConnection.closeCallableStatement(csPublish);		
				DbConnection.closeCallableStatement(csGetLOCBillTrgDate);	
			}
			catch(Exception ex)
			{
				CLEPUtility.LOG(true, false, ex, "from method processAutoPUBLISH_BT_AfterRFBT() while closing resultsets and statements");
			}
		}
		return successStatus;
	}
	
	/**@Vijay 
	*checking the attribute has proper valid input or not
	*If provided input is wrong then return true otherwise false
	*/
	public static boolean validationForAttribute2(String attValue){
		/* by default here "checkValidAttValue = false" that
		 * means it is not valid input
		 */ 
		boolean checkValidAttValue = false;  
		try{
			CLEPUtility.SysErr("---------Getting configuration value for check workflow id--------------");
			
			String celpProjectWorkFlowId = Utility.getAppConfigValue("CLEP_ATTACHED_PROJECT_WORKFLOW_ID");
			if(celpProjectWorkFlowId !=null && celpProjectWorkFlowId !=""){
				String[] workFlowId = celpProjectWorkFlowId.split(",");
				CLEPUtility.SysErr("-----Checking WorkFlow Id exists or not for this id- " +attValue);
				for(int i=0; i<workFlowId.length;i++){
					if(attValue.equals(workFlowId[i])){
						CLEPUtility.SysErr("---------WorkFlow Id exists...--------------------------");
						checkValidAttValue = true;
					}
				}
			}
			else{
				CLEPUtility.SysErr("---------Configuration value is blank------------------------");
			}
		}
		catch(Exception e){
			CLEPUtility.LOG(true, false, e, "from method validationForAttribute2()");		
		}
		return checkValidAttValue;
	}
	
	/**@Vijay
	 * @param orderSubType
	 * @return true or false. 
	 * If retrun value is true that means entered value for orderSubType
	 * is correct otherwise returning vlaue would be false  
	 */
	public static boolean validateOrderSubType(String orderSubType){
		boolean flag = false;  
		try{
			CLEPUtility.SysErr("---------Getting configuration value for check OrderSubType --------------");
			NewOrderDao objDao = new NewOrderDao();
			int orderType = Integer.parseInt(orderSubType) ;
			if(objDao.isOrderSubTypeAvailable(orderType)){
				flag = true;
			}
		}
		catch(Exception ex){
			CLEPUtility.LOG(true, true, ex, "Error during validation of OrderSubType");
		}
		return flag;
	}
	
	
}
