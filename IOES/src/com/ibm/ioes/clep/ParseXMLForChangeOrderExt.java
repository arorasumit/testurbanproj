/**@author Vijay
 * The purpose of this file is to implement some other functionality for change order.
 * 
 * --This file contain the following functionalities--
 * 
 * 2- New charge addition 
 * 3- Charge disconnection 
 * 4- Charge disconnection + New charge addition
 * 5- Charge Addition  + Addition new Line Item
 * 6- Charge disconnection + Addition new Line Item
 * 7- Charge disconnection + New charge addition + Addition new Line Item   
 */
package com.ibm.ioes.clep;

import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ibm.ioes.beans.BillingTriggerValidation;
import com.ibm.ioes.beans.ViewOrderDto;
import com.ibm.ioes.dao.NewOrderDao;
import com.ibm.ioes.dao.NewOrderDaoExt;
import com.ibm.ioes.dao.ViewOrderDao;
import com.ibm.ioes.exception.IOESException;
import com.ibm.ioes.forms.NewOrderDto;
import com.ibm.ioes.forms.OrderHeaderDTO;
import com.ibm.ioes.forms.PoDetailsDTO;
import com.ibm.ioes.utilities.AjaxValidation;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.Utility;

public class ParseXMLForChangeOrderExt {
	private static final Logger logger = Logger.getLogger(CLEPListener.class);
	
//	################################################################################################################################################################
	public static String spInsertORDEXTATTtoIOMS = "{call IOE.SPCLEP_INSERT_TEMPORDEXTATT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	public static String spInsertCONTACTtoIOMS = "{call IOE.SPCLEP_INSERT_TEMPCONTACT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	
	/*In below procedure charge addition related information is going to save.*/
	public static String spInsertADDANDDISCHARGESOLCHNGtoIOMS = "{call IOE.SPCLEP_CHANGE_TEMPADDANDDISCHARGE_SOLCHNG(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	
	/*In below procedure charge disconnection related information is going to save.*/
	public static String spInsertDisconnectCharge = "{call IOE.SPCLEP_CHANGE_TEMPDISCONNECT_CHARGE_SOLCHNG(?,?)}"; 
	
	public static String spInsertVALIDATESOLCHNGtoIOMS = "{call IOE.SPMPP_CHANGE_DATATYPEVALIDATE_SOLCHANGE(?,?,?,?,?,?,?,?,?)}";

	public static String spInsertPROCESSCHANGEORDER_SOLCHNGtoIOMS = "{call IOE.SPMPP_PROCESSFILE_SOLCHNG(?,?,?,?,?,?,?,?,?,?)}";
	
	public static String gblLogicalLSINo="";
	
	/**@Vijay
	 * @param doc
	 * @param orderType
	 * @param orderSubType
	 * @param xmlfileid
	 * @param clepXmlDto
	 * @param jmsMessageID
	 * @param fileName
	 * @param absFileName
	 * @param filePath
	 * @param conn
	 * @return this method is return nothing. This method is using for processing all XML data for solution change
	 */
	public static void processSolutionChangeXML(Document doc, String orderType, String orderSubType, long xmlfileid,CLEPXmlDto clepXmlDto,String jmsMessageID,String fileName,String absFileName,String filePath,Connection conn){
		String missingAttribute="";
		String isWithWorkflow=AppConstants.TRUE_VALUE;
		String attachedWorkflowId=null;  
		CallableStatement csIOMS = null;
		String accountId=null;
		String projectMngrId=null;
		int taglength=0;
		int insertSuccessChangeOrder=-1;
		try{
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
				 
				 /*Start the validating for 
				  * attribute2 (that is workflow id)----*/ 
				 if(AppConstants.TRUE_VALUE.equalsIgnoreCase(isWithWorkflow)  
						 	&& getTagValue("Attribute2", elementOrdExtAttributeElement)==null){
					 
					 missingAttribute=" Attribute2 can not left blank ";
					 throw new IOESException("------ Error:    Attribute2 can not left blank! -----------");
				 }
				 else if(AppConstants.TRUE_VALUE.equalsIgnoreCase(isWithWorkflow) 
						 	&& (!validationForAttribute2(getTagValue("Attribute2", elementOrdExtAttributeElement))) ){
					 /*if validation return false 
					  * that means workflow id is not valid */
					 
					 missingAttribute=" Attribute2 is not valid workflow id ";
					 throw new IOESException("------ Error:    Attribute2 is not valid workflow id! -----------");	 
				 }

				 /*End of validating the workflow id */
				 missingAttribute="Attribute2";
				 csIOMS.setString(5,getTagValue("Attribute2", elementOrdExtAttributeElement));
				 
				 /*Assigning the workflow id 
				  * for interting workflow*/
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
					 /*Add an other parameter(workflow is), 
					  * the purpose is to insert workflow dynamically on the behalf of workflow id  
					  * */
					 validateAndProcessChangeOrderExt(elementOrdExtAttributeElement, orderType, orderSubType, xmlfileid, clepXmlDto, jmsMessageID, fileName, absFileName, filePath, conn, isWithWorkflow);
				 }
    		}else{
    			//each contact
    			insertSuccessChangeOrder=0;		
    			conn.rollback();
				CLEPUtility.SysErr("<<==========================InComing Message is Invalid=============================>>");					
				clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>CHANGE ORDER PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML,missing Contact tag </Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails></ChangeOrder></orderHeader>");
				clepXmlDto.setJmsMessageID(jmsMessageID);
				ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);
    		   }
    		}//ordextattribute end
		}
		catch(Exception ex ){
			CLEPUtility.LOG(true, false, ex, "<<============Exception Occur===========>>");
			
		}
	}
	
	/**@author Vijay
	 * @param elementOrdExtAttributeElement
	 * @param orderType
	 * @param orderSubType
	 * @param xmlfileid
	 * @param clepXmlDto
	 * @param jmsMessageID
	 * @param fileName
	 * @param absFileName
	 * @param filePath
	 * @param conn
	 * @param isWorkFlow
	 * @return int 
	 * Return If the process of change order success then return 1 or otherwise return 0 
	 */
	public static int validateAndProcessChangeOrderExt(Element elementOrdExtAttributeElement, String orderType, String orderSubType, long xmlfileid,CLEPXmlDto clepXmlDto, String jmsMessageID, String fileName, String absFileName, String filePath, Connection conn, String isWorkFlow){

		int successStatus=-1;
		CallableStatement csIOMS=null;
		String missAttribute="";
		String workFlowId = null;
		try{
			//################################################[ SOLUTIONCHANGE ]###########################################################################
			if(AppConstants.SOLUTIONCHANGE_ORDER.equalsIgnoreCase(orderType)){
				
				int insertStatus=-1;
				NodeList parentNodeListPOList = elementOrdExtAttributeElement.getElementsByTagName("PODetails");								
				int insertPOStatus=ParseXMLForChangeOrder.solutionChangeOrderTypePO(parentNodeListPOList,xmlfileid,jmsMessageID, fileName, absFileName, filePath,conn);
				if(insertPOStatus==1){
					NodeList parentNodeListServiceList = elementOrdExtAttributeElement.getElementsByTagName("ServiceDetails");
					insertStatus=ParseXMLForChangeOrder.solutionChangeServiceDetails(parentNodeListServiceList,xmlfileid, jmsMessageID, fileName, absFileName, filePath,conn);										
				}else{
					CLEPUtility.SysErr("---------- Data Insertion failed -----------");
					insertStatus=0;
				}
				if(insertStatus==1){
					//NodeList parentNodeListSubNodeLineItem = elementOrdExtAttributeElement.getElementsByTagName("SubNodeLineItemDetails");
					//insertStatus=solutionChangeOrderTypeSubNode(parentNodeListSubNodeLineItem,xmlfileid, jmsMessageID, fileName, absFileName, filePath,conn);					
				}
				if (insertStatus == 1
						&& (orderSubType
									.equals(AppConstants.CLEP_NEW_LINE_ADD)
								|| orderSubType
										.equals(AppConstants.CLEP_CHARGE_ADD_AND_NEW_LINE_ADD)
								|| orderSubType
										.equals(AppConstants.CLEP_CHARGE_DIS_AND_NEW_LINE_ADD) 
								|| orderSubType
										.equals(AppConstants.CLEP_CHARGE_ADD_AND_DIS_AND_NEW_LINE_ADD))) {
					
					NodeList parentNodeListNewLineItem = elementOrdExtAttributeElement.getElementsByTagName("productCatelog");
					insertStatus=ParseXMLForChangeOrder.solutionChangeProductCatelog(parentNodeListNewLineItem,xmlfileid, jmsMessageID, fileName, absFileName, filePath,conn);					
				}
				
				/*--add code for only charge Addition with existing line item */
				if(insertStatus==1 
						&& (orderSubType
								.equals(AppConstants.CLEP_CHARGE_ADD)
								|| orderSubType
										.equals(AppConstants.CLEP_CHARGE_ADD_AND_DIS) 
								|| orderSubType
										.equals(AppConstants.CLEP_CHARGE_ADD_AND_NEW_LINE_ADD)
								|| orderSubType
										.equals(AppConstants.CLEP_CHARGE_ADD_AND_DIS_AND_NEW_LINE_ADD))){
					
					NodeList parentNodeListChargeAddition = elementOrdExtAttributeElement.getElementsByTagName("AdditionalChargeInfo");
					insertStatus=solutionChangeChargeAddition(parentNodeListChargeAddition,xmlfileid, jmsMessageID, fileName, absFileName, filePath,conn);					
				}
				/*--end of charge Addition */
				
				/*--add code for only charge disconnection */
				if(insertStatus==1 
						&& (orderSubType
								.equals(AppConstants.CLEP_CHARGE_DIS)
								|| orderSubType
										.equals(AppConstants.CLEP_CHARGE_ADD_AND_DIS) 
								|| orderSubType
										.equals(AppConstants.CLEP_CHARGE_DIS_AND_NEW_LINE_ADD)
								|| orderSubType
										.equals(AppConstants.CLEP_CHARGE_ADD_AND_DIS_AND_NEW_LINE_ADD)) ){ 
					
					NodeList parentNodeListDisconnection = elementOrdExtAttributeElement.getElementsByTagName("DisconnectInfo");
					insertStatus=solutionChangeChargeDisconnection(parentNodeListDisconnection,xmlfileid, jmsMessageID, fileName, absFileName, filePath,conn);					
				}
				/*--end of charge disconnection */
				
				conn.commit();				
				int isProcessChangeOrderSolutionChange=-1;
				 //============================================================================================================================
				 					 //CHANGE ORDER VALIDATION FOR SOLUTIONCHANGE START HERE		
				 //============================================================================================================================
					if(insertStatus==1){
						CLEPUtility.SysErr("------------ ChangeOrder DataType Validation for Solution Change Start >>>>>>>>>>>>");								 												
						
						CallableStatement cstmt=conn.prepareCall(spInsertVALIDATESOLCHNGtoIOMS); 
						cstmt.setLong(1, xmlfileid); //file id 
						cstmt.setInt(2, Integer.valueOf(orderSubType)); //sub order type 
						cstmt.setLong(3, 0);
						cstmt.setInt(4, 0);
						cstmt.setString(5, "");
						cstmt.setString(6, "");
						cstmt.setString(7, "");
						cstmt.setNull(8, java.sql.Types.CLOB);
						cstmt.setString(9, "");
						cstmt.execute();
						
						CLEPUtility.SysErr("-------- The Tracking Logs in validation phase are :-"+cstmt.getString(7)+" -------------");
						long result=cstmt.getLong(3);
						if(result==2){							
							CLEPUtility.SysErr("---------ChangeOrder DataType Validation for Solution Change SUCCESS------");											
							isProcessChangeOrderSolutionChange=1;								
						}else{
							CLEPUtility.SysErr("---------ChangeOrder DataType Validation for Solution Change Failed(Refer:Error XML File for more information)------");											
							isProcessChangeOrderSolutionChange=0;
							conn.rollback();
						}
						Clob xmlValidateData=cstmt.getClob(8);
						if(xmlValidateData !=null){
							byte byteArrValidation[]=Utility.clobToByteArray(xmlValidateData);
							String xmlResponseValidationData=new String(byteArrValidation);	
							if(isProcessChangeOrderSolutionChange==0){
								clepXmlDto.setXmlData(xmlResponseValidationData);		
								
								CLEPUtility.SysErr("---------- Sending Response to MPP starting >>>>>>>>>>>");																		
								ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);							
							}
						}else{
							conn.rollback();
						  	String missingAttribute="[Internal error]{}";
						  	CLEPUtility.SysErr("------------ Error: ChangeOrder DataType Validation Response Creating FAILED --------");
							clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>SOLUTIONCHANGE PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Reason>Failed Due to "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg></ErrorMsg></Charges><ChargeAddition><ErrorMsg></ErrorMsg></ChargeAddition><ChargeDisconnection><ErrorMsg></ErrorMsg></ChargeDisconnection></ChangeOrder></orderHeader>");
							ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);
							isProcessChangeOrderSolutionChange=-1;							
					  }
					}
					//==========================================================================================
								//CHANGE ORDER PROCESSING FOR SOLUTIONCHANGE START HERE
					//==========================================================================================
							int orderNo=0;
							if(isProcessChangeOrderSolutionChange==1){
								
								CLEPUtility.SysErr("---------- Change Order for Solution Change Processing start >>>>>>>>>>>");												
								
								csIOMS=conn.prepareCall(spInsertPROCESSCHANGEORDER_SOLCHNGtoIOMS);
								csIOMS.setLong(1, xmlfileid);
								csIOMS.setLong(2, 1);//empid
								csIOMS.setLong(3, Integer.valueOf(orderSubType));//sub order type
								csIOMS.setLong(4, 0);//result
								csIOMS.setLong(5, 0);
								csIOMS.setString(6, "");//mscode
								csIOMS.setString(7, "");//diagnostic
								csIOMS.setString(8, "");//log
								csIOMS.setLong(9, 0);
								csIOMS.setNull(10, java.sql.Types.CLOB);
								csIOMS.execute();
																	
								CLEPUtility.SysErr("-------- The Tracking Logs in order processing phase are :-"+csIOMS.getString(8)+" -------------");
								
								orderNo=csIOMS.getInt(9);
								long resultProcess=csIOMS.getLong(4);
								int isProcessAhead=-1;
								if(resultProcess==2){
									isProcessAhead=1;
									CLEPUtility.SysErr("-------- Change Order Created Successfully:-"+csIOMS.getInt(9)+" -------------");										
								}else{									
									CLEPUtility.SysErr("---------ChangeOrder Processing for Solution Change Failed(Refer:Error XML File for more information)------");
									if(csIOMS.getString(7) !=null){
										CLEPUtility.SysErr("---------ChangeOrder Processing for Solution Change Failed due to Internal Error------");
										CLEPUtility.SysErr(csIOMS.getString(7));
									}
									isProcessAhead=0;
									conn.rollback();
								}
								
								Clob b=csIOMS.getClob(10);
								if(b!=null){
									byte byteArr[]=Utility.clobToByteArray(b);
									String xmlResponseProcessData=new String(byteArr);
									clepXmlDto.setXmlData(xmlResponseProcessData);
								/*------------- If Solution change Processing success then send it for Billing Trigger ------------*/
								if(isProcessAhead==1){
									int succesStatusBT=-1;
									if(AppConstants.FALSE_VALUE.equalsIgnoreCase(isWorkFlow)){
										/*-------------------- Billing Trigger for SaaS Only -------------*/
										/*Use  this method processOnlyNoNM6Product() if you want to insert workflow with auto approval and also publish with billing trigger*/
										//succesStatusBT=processOnlyNoNM6Product(orderNo,xmlfileid,"2",conn,jmsMessageID,"2","8");
										
										/*This method validate some data and attach workflow and do auto approval */
										succesStatusBT=ProcessXMLforCLEP.processOnlyNoNM6ProductForChange(orderNo,xmlfileid,"2",conn,jmsMessageID,"2","8");
										
										
										if(succesStatusBT==1){
											String responseData="",sendAttachedBillableResonse="";											
											if(clepXmlDto.getXmlData()!=null){
																																		
												responseData=clepXmlDto.getXmlData();	
												sendAttachedBillableResonse=ParseXMLForChangeOrder.attachBillableAccountToResponse(orderNo,xmlfileid,responseData,jmsMessageID,conn);
																							
												if(! AppConstants.FAILED_VALUE.equalsIgnoreCase(sendAttachedBillableResonse)){
													clepXmlDto.setStage(AppConstants.CLEP_SUCCESS);
													CLEPUtility.SysErr("-------- Sending Response to MPP >>>>>>>>>>>");
													clepXmlDto.setXmlData(sendAttachedBillableResonse);
													int sendStatus=ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"N","N",conn);
												}																																	
											}										
										}
									}else if(AppConstants.TRUE_VALUE.equalsIgnoreCase(isWorkFlow)){
										/*-------------------- Billing Trigger for SaaS ERP --------------*/										
										CLEPUtility.SysErr("************* SaaS ERP Change Order *************");
										
										 /* Add an other parameter(workflow id), 
										  * the purpose is to insert workflow dynamically on the behalf of workflow id receiving from XML file 
										  * */
										workFlowId = clepXmlDto.getWorkFlowId();
										int isValidateSuccess=ParseXMLForChangeOrder.validateSaaSErPChangeOrder(orderNo,xmlfileid,jmsMessageID,"2","8",conn, workFlowId);
										if(isValidateSuccess==1){
											CLEPUtility.SysErr("---------- Waiting for Aproval against orderNo:-"+orderNo+" >>>>>>>>>");
											/* 
											 *Start here to send the msg to AIE 
											 */
											
											 /*implement sending msg same as time of copc approval  */
											ViewOrderDto objServiceTypeOrdSrcDto = new ViewOrderDto();
											NewOrderDao objDao = new NewOrderDao();
											objServiceTypeOrdSrcDto =objDao.getServiceType_OrderSourceClepErp(orderNo,4); //here 4 means order creation successfully
											//if copc already sent message to mpp,it will not resent
											
											if(objServiceTypeOrdSrcDto.getServiceTypeID()==2 && AppConstants.CLEP_ORDER_SOURCE.equalsIgnoreCase(objServiceTypeOrdSrcDto.getOrder_creation_source())){
												if(objServiceTypeOrdSrcDto.getResponseClepOrderMsgAfterAproval()!=null){
													
													clepXmlDto.setXmlData(objServiceTypeOrdSrcDto.getResponseClepOrderMsgAfterAproval());
													CLEPUtility.SysErr("Message Request ID Was:- "+objServiceTypeOrdSrcDto.getClepJMSReqMsgId());
													clepXmlDto.setJmsMessageID(objServiceTypeOrdSrcDto.getClepJMSReqMsgId());
													clepXmlDto.setStage(AppConstants.CLEP_SUCCESS);	//here success means order inserted succesfully
													objDao.sendClepResponseToMpp(clepXmlDto,objServiceTypeOrdSrcDto.getClepFileId(),objServiceTypeOrdSrcDto.getOrderno(),"N");							
												}	
											} 
										}else{
											CLEPUtility.SysErr("---------- PO Validation Failed for Aproval against orderNo:-"+orderNo+" >>>>>>>>>");
										}
									}else{
										 missAttribute=" Attribute1 is Invalid{Hint:should be True for Workflow or False without Workflow} ";
										 CLEPUtility.SysErr("---------- "+missAttribute);
										 throw new IOESException("------ Error:    Attribute1 is Invalid ! -----------");
									}
									/*--------------- If Processing Failed then send it to Mpp error response -----------*/																													
								}else if(isProcessAhead==0){									
									clepXmlDto.setXmlData(xmlResponseProcessData);
									CLEPUtility.SysErr("------------ Sending Response to MPP Starting >>>>>>>>>>>");	
									int sendStatus=ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);							
								   }	
							}else{
								conn.rollback();
								String missingAttribute="[Internal error]{}";
								CLEPUtility.SysErr("------------ Error: ChangeOrder Processing Response Creating FAILED --------");
								clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>SOLUTIONCHANGE PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Reason>Failed Due to "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg></ErrorMsg></Charges><ChargeAddition><ErrorMsg></ErrorMsg></ChargeAddition><ChargeDisconnection><ErrorMsg></ErrorMsg></ChargeDisconnection></ChangeOrder></orderHeader>");
								ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);
								successStatus=-1;									
							}
						}
			}
		}catch(IOESException e){
			//insertFlag=0;
			CLEPUtility.LOG(true, false, e, "<<============Exception Occur===========>>");
			try{
				conn.rollback();
			}catch(Exception ex){
				CLEPUtility.LOG(true, false, ex, "<<============Exception Occur during connection rollback===========>>");
			}
			if(AppConstants.NEW_ORDER.equalsIgnoreCase(orderType)){
				clepXmlDto.setXmlData("<orderHeader><NewOrder><OrderType>NEW PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML,"+missAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg></ErrorMsg></Charges></NewOrder></orderHeader>");
			}else{
				clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>CHANGE ORDER PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML, "+missAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg></ErrorMsg></Charges></ChangeOrder></orderHeader>");
			}
			clepXmlDto.setJmsMessageID(jmsMessageID);
			ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);
		}
		catch(Exception e){
			CLEPUtility.LOG(true, false, e, "<<============Exception Occur===========>>");
			try{
				conn.rollback();
			}catch(Exception ex){
				CLEPUtility.LOG(true, false, ex, "<<============Exception Occur during connection rollback===========>>");
			}
			String missingAttribute="[Internal error]{}";
			clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>CHANGE ORDER PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg></ErrorMsg></Charges></ChangeOrder></orderHeader>");
			clepXmlDto.setJmsMessageID(jmsMessageID);
			ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);						
		}
		return successStatus;
		
	}
	
	
	/**@Author Vijay
	 * @param parentNodeListChargeDisconnectionInfo
	 * @param xmlfileid
	 * @param jmsMessageID
	 * @param fileName
	 * @param absFileName
	 * @param filePath
	 * @param conn
	 * @return The status of data insertion for charge disconnection in the form of 1 or 0
	 */
	public static int solutionChangeChargeDisconnection(NodeList parentNodeListChargeDisconnectionInfo,long xmlfileid,String jmsMessageID,String fileName,String absFileName,String filePath,Connection conn){
		int insertStatus=-1;	
		int taglength=0;
		int insertFlag=-1;
		CallableStatement csIOMSCharges = null;
		String missingAttribute="";
		
		CLEPXmlDto clepXmlDto=new CLEPXmlDto();
		try{
			
			//=============================CHARGES DISCONNECTION INFO========================================//
			missingAttribute="DisconnectInfo";
			taglength=parentNodeListChargeDisconnectionInfo.getLength();
			if(taglength > 0 ){
				
				/*Now traversing Disconnection charge info */
				for(int i=0;i<parentNodeListChargeDisconnectionInfo.getLength();i++)
				{	
					Node childNodeChargeDisconnectionInfo = parentNodeListChargeDisconnectionInfo.item(i);
					if (childNodeChargeDisconnectionInfo.getNodeType() == Node.ELEMENT_NODE){
						Element elementChargeDisconnectionInfo = (Element) childNodeChargeDisconnectionInfo;
	
						missingAttribute="chargesDisconnectInfoId in DisconnectInfo";
						NodeList nodeListDisconnectionCharge = elementChargeDisconnectionInfo.getElementsByTagName("chargesDisconnectInfoId");
						taglength=nodeListDisconnectionCharge.getLength();
						if(taglength > 0){
							/*Now traversing Line Item id */
							for(int j=0;j<nodeListDisconnectionCharge.getLength();j++)
							{
								CLEPUtility.SysErr("**************************Reading and Inserting DisconnectInfo["+(j+1)+"]********************************");											
								 csIOMSCharges=conn.prepareCall(spInsertDisconnectCharge);
								 Node childNodeDisconnectCharge = nodeListDisconnectionCharge.item(j);
								 if (childNodeDisconnectCharge.getNodeType() == Node.ELEMENT_NODE)
								 {
									 Element elementChargeDiscon = (Element) childNodeDisconnectCharge;
									 csIOMSCharges.setLong(1, xmlfileid);
									 missingAttribute="chargesDisconnectInfoId in DisconnectInfo";
									 //csIOMSCharges.setString(2,getTagValue("chargesDisconnectInfoId", elementChargeDiscon));
									 csIOMSCharges.setString(2,elementChargeDiscon.getFirstChild().getNodeValue());
									 csIOMSCharges.execute();
									 insertFlag=1;insertStatus=1;
								 }
							}
						}else{
							insertStatus=0;	 				
							conn.rollback();
							CLEPUtility.SysErr("<<==========================InComing Message is Invalid=============================>>");					
							clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>SOLUTIONCHANGE PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML, missing Attribute "+missingAttribute+" </Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg></ErrorMsg></Charges><ChargeDisconnection><ErrorMsg></ErrorMsg></ChargeDisconnection></ChangeOrder></orderHeader>");
							clepXmlDto.setJmsMessageID(jmsMessageID);
							ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);
						}
					}
				}
				/*End of traversing Disconnection charge info*/
			}else{
				insertStatus=0;	 				
				conn.rollback();
				CLEPUtility.SysErr("<<==========================InComing Message is Invalid=============================>>");					
				clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>SOLUTIONCHANGE PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML, missing Attribute "+missingAttribute+" </Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg></ErrorMsg></Charges><ChargeDisconnection><ErrorMsg></ErrorMsg></ChargeDisconnection></ChangeOrder></orderHeader>");
				clepXmlDto.setJmsMessageID(jmsMessageID);
				ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);
			}
			
			
		}catch(NullPointerException e){
			insertStatus=0;			
			CLEPUtility.LOG(true, false, e, "<<============Null Pointer Exception Occur===========>>");	
			try{
				conn.rollback();
			}catch(Exception ex){
				CLEPUtility.LOG(true, true, ex, "<<============Exception Occur during connection rollback===========>>");
			}
			clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>SOLUTIONCHANGE PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML,missing Attribute "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg></ErrorMsg></Charges><ChargeDisconnection><ErrorMsg></ErrorMsg></ChargeDisconnection></ChangeOrder></orderHeader>");
			clepXmlDto.setJmsMessageID(jmsMessageID);
			ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);	
		}
		catch(SQLException e){
			insertStatus=0;					
			CLEPUtility.LOG(true, true, e, "<<============SQLException Exception Occur===========>>");
			try{
				conn.rollback();
			}catch(Exception ex){
				CLEPUtility.LOG(true, true, ex, "<<============Exception Occur during rollback===========>>");
			}
			missingAttribute="[Internal error]{}";
			clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>SOLUTIONCHANGE PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg></ErrorMsg></Charges><ChargeDisconnection><ErrorMsg></ErrorMsg></ChargeDisconnection></ChangeOrder></orderHeader>");
			clepXmlDto.setJmsMessageID(jmsMessageID);
			ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);			
		}catch(Exception e){
			insertStatus=0;					
			CLEPUtility.LOG(true, true, e, "<<============Exception Occur===========>>");
			try{
				conn.rollback();
			}catch(Exception ex){
				CLEPUtility.LOG(true, true, ex, "<<============Exception Occur during rollback===========>>");
			}
			missingAttribute="[Internal error]{}";
			clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>SOLUTIONCHANGE PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg></ErrorMsg></Charges><ChargeDisconnection><ErrorMsg></ErrorMsg></ChargeDisconnection></ChangeOrder></orderHeader>");
			clepXmlDto.setJmsMessageID(jmsMessageID);
			ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);						
		}
		return insertStatus;
	}
	
	/**@Author Vijay
	 * @param parentNodeListChargeAdditionInfo
	 * @param xmlfileid
	 * @param jmsMessageID
	 * @param fileName
	 * @param absFileName
	 * @param filePath
	 * @param conn
	 * @return The status of data insertion for charge addition in the form of 1 or 0
	 */
	public static int solutionChangeChargeAddition(NodeList parentNodeListChargeAdditionInfo,long xmlfileid,String jmsMessageID,String fileName,String absFileName,String filePath,Connection conn){
		int insertStatus=-1;	
		int taglength=0;
		int insertFlag=-1;
		CallableStatement csIOMSCharges = null;
		String missingAttribute="";
		CLEPXmlDto clepXmlDto=new CLEPXmlDto();
		try{
				//=============================NEW CHARGES INFO========================================
			missingAttribute="AdditionalChargeInfo";
			taglength=parentNodeListChargeAdditionInfo.getLength();
			if(taglength > 0 ){							
				
					/*Now traversing Additional Charge Info */
				for(int i=0;i<parentNodeListChargeAdditionInfo.getLength();i++)
				{																										
					Node childNodeChargeAdditionInfo = parentNodeListChargeAdditionInfo.item(i);
					if (childNodeChargeAdditionInfo.getNodeType() == Node.ELEMENT_NODE){
						Element elementChargeAdditionInfo = (Element) childNodeChargeAdditionInfo;
						
						missingAttribute="LineItemid";
						NodeList parentNodeListLineItem = elementChargeAdditionInfo.getElementsByTagName("LineItemid");													
						taglength=parentNodeListLineItem.getLength();
						if(taglength > 0){
							
							/*Now traversing Line Item id */
							for(int j=0;j<parentNodeListLineItem.getLength();j++)
							{	
								Node childNodeLineItem = parentNodeListLineItem.item(j);
								if (childNodeLineItem.getNodeType() == Node.ELEMENT_NODE){
									Element elementchildNodeLineItem = (Element) childNodeLineItem;

									missingAttribute="ExsistingLineItemId in LineItemid";
									//getting ExistingLineItemId attribute of LineItemid
									String existingLineItemIdAttribute=elementchildNodeLineItem.getAttribute("ExistingLineItemId");
									
									if(!("".equalsIgnoreCase(existingLineItemIdAttribute))){
										
										//getting UniqePoDetailId 
										missingAttribute="UniqePoDetailId";
										String uniqePoDetailId = getTagValue("UniqePoDetailId", elementchildNodeLineItem);
										
											 
										missingAttribute="chargesInfoNode";
										NodeList parentNodeListchargesInfoNode = elementchildNodeLineItem.getElementsByTagName("chargesInfoNode");													
										taglength=parentNodeListchargesInfoNode.getLength();
										
										/*Now traversing Charges Info Node */
										if(taglength > 0){	
											for(int k=0;k<parentNodeListchargesInfoNode.getLength();k++)
											{	
												Node childNodeChargeInfo = parentNodeListchargesInfoNode.item(k);
												 CLEPUtility.SysErr("**************************Reading and Inserting chargesInfoNode["+(k+1)+"]********************************");											
												 csIOMSCharges=conn.prepareCall(spInsertADDANDDISCHARGESOLCHNGtoIOMS);
												 if (childNodeChargeInfo.getNodeType() == Node.ELEMENT_NODE)
												 {
													 Element eElementChargeInfo = (Element) childNodeChargeInfo;
													 missingAttribute="UniqueChargeId in chargesInfoNode";
													
													 String chargeIdAttribute=eElementChargeInfo.getAttribute("UniqueChargeId");
													 if(!("".equalsIgnoreCase(chargeIdAttribute))){
														 csIOMSCharges.setLong(1, xmlfileid);
														 
														 //Existing line item id 
														 csIOMSCharges.setString(2,existingLineItemIdAttribute);
														 
														 //Unique po id
														 csIOMSCharges.setString(3,uniqePoDetailId);
														 
														 //Unique charge id 
														 csIOMSCharges.setString(4,chargeIdAttribute);
														 
														 
														 missingAttribute="chargeType in chargesInfoNode";
														 csIOMSCharges.setString(5,getTagValue("chargeType", eElementChargeInfo));
														 
														 missingAttribute="chargeName in chargesInfoNode";
														 csIOMSCharges.setString(6,getTagValue("chargeName", eElementChargeInfo));
														 missingAttribute="chargePeriod in chargesInfoNode";
														 csIOMSCharges.setString(7, getTagValue("chargePeriod", eElementChargeInfo));
														 missingAttribute="chargeAmount in chargesInfoNode";
														 csIOMSCharges.setString(8, getTagValue("chargeAmount", eElementChargeInfo));
														 missingAttribute="chargeFrequency in chargesInfoNode";
														 csIOMSCharges.setString(9, getTagValue("chargeFrequency", eElementChargeInfo));
														 missingAttribute="startDateLogic in chargesInfoNode";
														 csIOMSCharges.setString(10, getTagValue("startDateLogic", eElementChargeInfo));
														 missingAttribute="startDateDays in chargesInfoNode";
														 csIOMSCharges.setString(11, getTagValue("startDateDays", eElementChargeInfo));
														 missingAttribute="startDateMonth in chargesInfoNode";
														 csIOMSCharges.setString(12, getTagValue("startDateMonth", eElementChargeInfo));
														 missingAttribute="endDateLogic in chargesInfoNode";
														 csIOMSCharges.setString(13, getTagValue("endDateLogic", eElementChargeInfo));
														 missingAttribute="endDateDays in chargesInfoNode";
														 csIOMSCharges.setString(14, getTagValue("endDateDays", eElementChargeInfo));
														 missingAttribute="endDateMonth in chargesInfoNode";
														 csIOMSCharges.setString(15, getTagValue("endDateMonth", eElementChargeInfo));
														 missingAttribute="annotation in chargesInfoNode";
														 csIOMSCharges.setString(16, getTagValue("annotation", eElementChargeInfo));
														 
														 //isAdditional charges 
														 csIOMSCharges.setInt(17, AppConstants.TRUE); 
														 //isDisconnection charges
														 csIOMSCharges.setInt(18,AppConstants.FALSE); 
														 
														  missingAttribute="exclude in chargesInfoNode";
														 csIOMSCharges.setString(19, getTagValue("exclude", eElementChargeInfo));
														 
														 //csIOMSCharges.setLong(17, Long.valueOf(uniqueProductDetaiId));
														 csIOMSCharges.execute();
														 insertFlag=1;insertStatus=1;
													 }else{
														 	insertFlag=0;
														 	conn.rollback();
															CLEPUtility.SysErr("<<==========================InComing Message is Invalid=============================>>");																
															clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>SOLUTIONCHANGE PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML,UniqueChargeId can not left blank or missing in any chargeInfoNode tag in XML</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg></ErrorMsg></Charges><ChargeAddition><ErrorMsg></ErrorMsg></ChargeAddition></ChangeOrder></orderHeader>");
															clepXmlDto.setJmsMessageID(jmsMessageID);
															ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);
															//break outer;
													 }
												 }									 
											
											}/*End of traversing Charges Info Node */
										}else{
											insertStatus=0;	 				
											conn.rollback();
											CLEPUtility.SysErr("<<==========================InComing Message is Invalid=============================>>");					
											clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>SOLUTIONCHANGE PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML, missing Attribute "+missingAttribute+" </Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg></ErrorMsg></Charges><ChargeAddition><ErrorMsg></ErrorMsg></ChargeAddition></ChangeOrder></orderHeader>");
											clepXmlDto.setJmsMessageID(jmsMessageID);
											ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);
										}
										
									}else{
										insertFlag=0;
									 	conn.rollback();
										CLEPUtility.SysErr("<<==========================InComing Message is Invalid=============================>>");																
										clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>SOLUTIONCHANGE PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML,ExistingLineItemId can not left blank or missing in any LineItemid tag in XML</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg></ErrorMsg></Charges><ChargeAddition><ErrorMsg></ErrorMsg></ChargeAddition></ChangeOrder></orderHeader>");
										clepXmlDto.setJmsMessageID(jmsMessageID);
										ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);
									}
								}
								
							}/*End of traversing Line Item id */
							
					}else{
						insertStatus=0;	 				
						conn.rollback();
						CLEPUtility.SysErr("<<==========================InComing Message is Invalid=============================>>");					
						clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>SOLUTIONCHANGE PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML, missing Attribute "+missingAttribute+" </Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg></ErrorMsg></Charges><ChargeAddition><ErrorMsg></ErrorMsg></ChargeAddition></ChangeOrder></orderHeader>");
						clepXmlDto.setJmsMessageID(jmsMessageID);
						ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);				
					}
				 }	
					
			  }	/*End of traversing Additional Charge Info */	
			}else{
				insertStatus=0;	 				
				conn.rollback();
				CLEPUtility.SysErr("<<==========================InComing Message is Invalid=============================>>");					
				clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>SOLUTIONCHANGE PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML, missing Attribute "+missingAttribute+" </Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg></ErrorMsg></Charges><ChargeAddition><ErrorMsg></ErrorMsg></ChargeAddition></ChangeOrder></orderHeader>");
				clepXmlDto.setJmsMessageID(jmsMessageID);
				ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);
			}
				
		}catch(NullPointerException e){
			insertStatus=0;			
			CLEPUtility.LOG(true, false, e, "<<============NullPointer Exception Occur===========>>");	
			try{
				conn.rollback();
			}catch(Exception ex){
				CLEPUtility.LOG(true, false, ex, "<<============Exception Occur during rollback===========>>");	
			}
			clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>SOLUTIONCHANGE PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML,missing Attribute "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg></ErrorMsg></Charges><ChargeAddition><ErrorMsg></ErrorMsg></ChargeAddition></ChangeOrder></orderHeader>");
			clepXmlDto.setJmsMessageID(jmsMessageID);
			ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);						
		}
		catch(SQLException e){
			insertStatus=0;					
			CLEPUtility.LOG(true, false, e, "<<============SQLException Exception Occur===========>>");
			try{
				conn.rollback();
			}catch(Exception ex){
				CLEPUtility.LOG(true, false, ex, "<<============Exception Occur during rollback===========>>");
			}
			missingAttribute="[Internal error]{}";
			clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>SOLUTIONCHANGE PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg></ErrorMsg></Charges><ChargeAddition><ErrorMsg></ErrorMsg></ChargeAddition></ChangeOrder></orderHeader>");
			clepXmlDto.setJmsMessageID(jmsMessageID);
			ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);			
		}catch(Exception e){
			insertStatus=0;					
			CLEPUtility.LOG(true, false, e, "<<============Exception Occur during rollback===========>>");
			try{
				conn.rollback();
			}catch(Exception ex){
				CLEPUtility.LOG(true, false, ex, "<<============Exception Occur during rollback===========>>");
			}
			missingAttribute="[Internal error]{}";
			clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>SOLUTIONCHANGE PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg></ErrorMsg></Charges><ChargeAddition><ErrorMsg></ErrorMsg></ChargeAddition></ChangeOrder></orderHeader>");
			clepXmlDto.setJmsMessageID(jmsMessageID);
			ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);						
		}
		return insertStatus;
	}
	
	/**@Author Vijay
	 * @param sTag
	 * @param eElement
	 * @return The tag value in the string form
	 */
	private static String getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
	 
	        Node nValue = (Node) nlList.item(0);
	        if(nValue!=null){
	        		return nValue.getNodeValue().trim();
	       }else{
	    	   		return null;
	       }			
	  }
	
	/**@Author Vijay
	 * @param attValue
	 * @return If Attribute value is valid then return 'true' otherwise return false
	 */
	public static boolean validationForAttribute2(String attValue){
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
			CLEPUtility.LOG(true, false, e, "<<============Exception Occur ===========>>");
		}
		return checkValidAttValue;
	}
	
}