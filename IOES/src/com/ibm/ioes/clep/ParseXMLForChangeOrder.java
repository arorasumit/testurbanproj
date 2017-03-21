
/*
 *
 * Copyright (c) 2011-2013 IBM, Inc. All Rights Reserved.
 * ===================================================================================================================================================================
 * Procedure:spInsertPROCESSFILEINFOtoIOMS
 * Purpose:Inserting unique xml fileid details to table where filename,sendmessageid,receivemesageid and error xml,success xml stored
 * 
 * Procedure:spInsertPODETAILtoIOMS
 * Purpose:Inserting parsed data to temporary PO Details table[PODETAIL TAB]
 * 
 * Procedure:spInsertSERVICETABCHANGEORDtoIOMS
 * Purpose:Inserting Data into Disconnection Service Details Temporary table
 * 
 * Procedure:spInsertVALIDATEDISCONNRtoIOMS
 * Purpose:Validation for Disconnection Order
 * 
 * Procedure:spInsertPROCESSCHANGEORDERtoIOMS
 * Purpose:Processing the change order for disconnection
 * 
 * Procedure:sqlSpValidatePODetail
 * Purpose:To validate PO after success full created order[these procedures are existing iB2b Procedures not created in CLEP]
 * 
 * Procedure:sqlSpValidatePO
 * Purpose:To validate PO after success full created order[these procedures are existing iB2b Procedures not created in CLEP](Currently it is not using may be use in future )
 * 
 * Procedure:sqlGet_TaskListOfOrder
 * Purpose:To get task id after successfull validated PO for starting and close workflow[this procedure is existing iB2b Procedure not created in CLEP]
 * 
 * Procedure:sqlSpInsertTaskActionForChangeOrder
 * Purpose:Insert the role wise task like AM,PM,COPC,SED
 * 
 * Procedure:spGetChangeOrderSubType 
 * Purpose:To Fetch Change Order Sub type
 * 
 * Procedure:spGetLocNoLocDateBillinTrgDate
 * Purpose:To get Billing trigger related information like Loc no, Billing trigger date etc
 * 
 * Procedure:spGetServiceProductIds
 * Purpose:To get Service Product id regarding billing trigger
 * 
 * Procedure:spInsertSERVICETABSOLCHNGtoIOMS for solution change order
 * Purpose:Inserting parsed data to temporary ServiceDetails table
 * 
 * Procedure:spInsertLINEITEMTABSOLCHNGtoIOMS for solution change order
 * Purpose:Inserting parsed data to temporary Line item table
 *
 * Procedure:spInsertSUBNODESOLCHNGtoIOMS
 * Purpose:Inserting parsed data to temporary Sub node table for solution change order
 * 
 * Procedure:spInsertCHARGESINFOSOLCHNGtoIOMS
 * Purpose:Inserting parsed data to temporary Charge info table for solution change order
 * 
 * Procedure:spInsertPRODUCTDETAILStoIOMS
 * Purpose:Inserting parsed data to temporary New line item table for solution change order 
 * 
 * Procedure:spInsertVALIDATESOLCHNGtoIOMS
 * Purpose:To validate Solution change order before inserting the data in main table
 * 
 * Procedure:spInsertPROCESSCHANGEORDER_SOLCHNGtoIOMS
 * Purpose:Inserting the the data after successfully validation for solution change order
 * 
 =======================================================================================================================================================================*/

package com.ibm.ioes.clep;

import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
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
/*
 * =================================================================================================================
 * CODE	 AUTHOR			CBR						DATE						REQUIREMENT
 * [001] ANIL KUMAR		20110428-XX-06272		08-SEPT-2011				CREATING ORDER USING CLOUD MACHENISM
 * [002] VIJAY PATHAK	20130716-IT-09161		26-AUG-2013					REAL TIME PROVISIONING
 * =================================================================================================================
*/
public class ParseXMLForChangeOrder {
	private static final Logger logger = Logger.getLogger(CLEPListener.class);
	//##########################################################################################################################################################################
	//												MISCLENIOUS 	
	public static String spInsertPROCESSFILEINFOtoIOMS = "{call IOE.SP_MPPINSERT_PROCESSED_FILEINFO(?,?,?,?,?,?,?,?,?,?)}";
	public static String spInsertPODETAILtoIOMS = "{call IOE.SPCLEP_INSERT_TEMPPODETAILS(?,?,?,?,?,?,?,?,?,?,?,?)}";
	
	//##########################################################################################################################################################################
	//												DISCONNECTION CHANGEORDER
	public static String spInsertSERVICETABCHANGEORDtoIOMS = "{CALL IOE.SPCLEP_CHANGE_TEMPSERVICEDETAILS_DISCONN(?,?,?,?,?,?,?,?,?,?,?,?)}";
	public static String spInsertVALIDATEDISCONNRtoIOMS = "{call IOE.SPMPP_CHANGE_DATATYPEVALIDATE_DISCONN(?,?,?,?,?,?,?,?)}";
	public static String spInsertPROCESSCHANGEORDERtoIOMS = "{call IOE.SPMPP_PROCESSFILE_PERMANENT_DISCONNECTION(?,?,?,?,?,?,?,?,?)}";		
	//##########################################################################################################################################################################
	//												VALIDATION AND WORKFLOW	
	public static String sqlSpValidatePODetail= "{call IOE.SP_VALIDATE_PODETAIL(?)}";//To Validate PO and update Status
	public static String sqlSpValidatePO= "{call IOE.SPUPDATEPOSTATUS(?,?,?,?,?)}";//To Validate PO and update Status
	public static String sqlGet_TaskListOfOrder = "{CALL IOE.SPGETTASKWORKFLOWDETAILS(?)}";	
	//public static String sqlSpInsertTaskActionForChangeOrder= "{call IOE.SP_INSERT_TASK_ACTION_CHANGE_ORDER(?,?,?,?,?,?,?,?,?,?)}";//To Insert Task Notes
	public static String sqlSpInsertTaskActionForChangeOrder= "{call IOE.SP_INSERT_TASK_ACTION_CHANGE_ORDER(?,?,?,?,?,?,?,?,?,?,?)}";//To Insert Task Notes
	public static String spGetChangeOrderSubType = "{call IOE.GET_CHANGE_ORDER_SUBTYPE(?)}";// To Fetch Change Order Sub type
	//###########################################################################################################################################################################
	//												BILLING TRIGGER	
	public static String spGetLocNoLocDateBillinTrgDate="{call IOE.SPCLEP_GET_LOCNO_DATEBILLTRG(?,?,?,?,?,?,?,?,?,?)}";
	public static String spGetServiceProductIds="{call IOE.SPCLEP_GETSERVICEPRODUCTIDS(?)}";
	//###########################################################################################################################################################################
	//												SOLUTION CHANGE	
	public static String spInsertSERVICETABSOLCHNGtoIOMS = "{CALL IOE.SPCLEP_CHANGE_TEMPSERVICEDETAILS_SOLCHNG(?,?,?,?,?,?,?,?,?,?)}";
	public static String spInsertLINEITEMTABSOLCHNGtoIOMS = "{CALL IOE.SPCLEP_CHANGE_TEMPLINEITEMS_SOLCHNG(?,?,?,?,?,?,?,?,?,?,?,?)}";
	public static String spInsertSUBNODESOLCHNGtoIOMS = "{CALL IOE.SPCLEP_CHANGE_TEMPSUBNODE_SOLCHNG(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	public static String spInsertCHARGESINFOSOLCHNGtoIOMS = "{call IOE.SPCLEP_CHANGE_TEMPCHARGESINFO_SOLCHNG(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	public static String spInsertPRODUCTDETAILStoIOMS = "{call IOE.SPCLEP_CHANGE_NEWLINEITEM_SOLCHNG(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	public static String spInsertVALIDATESOLCHNGtoIOMS = "{call IOE.SPMPP_CHANGE_DATATYPEVALIDATE_SOLCHANGE(?,?,?,?,?,?,?,?,?)}";
	public static String spInsertPROCESSCHANGEORDER_SOLCHNGtoIOMS = "{call IOE.SPMPP_PROCESSFILE_SOLCHNG(?,?,?,?,?,?,?,?,?,?)}";
	
	public static String gblLogicalLSINo="";
	
	/*Method:disconnectionOrderType
	 * Purpose:To Inserting the data into temporary table regarding Disconnection order 
	 * */
	public static int disconnectionOrderType(NodeList parentNodeListServiceList,long xmlfileid,String jmsMessageID,String fileName,String absFileName,String filePath,Connection conn){
		int insertStatus=-1;		
		CallableStatement csIOMS = null;
		String lineItemID="",lineItems="";
		String missingAttribute="";
		int taglength=0;
		CLEPXmlDto clepXmlDto=new CLEPXmlDto();
		try{
				//===============================INSERTING INTO FILEPROCESS INFO==========================================				        												
	 			CallableStatement csProcessFileInfoInsert=conn.prepareCall(spInsertPROCESSFILEINFOtoIOMS); 
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
	 			int isSucessForValidation=csProcessFileInfoInsert.getInt(8);
	 			if(isSucessForValidation==0){
	 				//File Tracking  Table must be commited
	 					conn.commit();
	 				//-------------------------------------
	 			}
	 			//==========================================================================================
	 			//Inserting Data into Disconnection Service Details Temporary table below
			 	if(isSucessForValidation==0){
					csIOMS=conn.prepareCall(spInsertSERVICETABCHANGEORDtoIOMS);
					taglength=parentNodeListServiceList.getLength();
					outer:
					if(taglength>0)
					{
						for(int i=0;i<parentNodeListServiceList.getLength();i++)
						{	
							 Node childNodeServiceDetails = parentNodeListServiceList.item(i);
							 CLEPUtility.SysErr("**************************Reading and Inserting ServiceDetails["+(i+1)+"]*******************************");				 				
							 if(childNodeServiceDetails.getNodeType() == Node.ELEMENT_NODE)
							 {
								 Element eElementService = (Element) childNodeServiceDetails;
								 										 																				 											 
								 NodeList parentNodeLineItemId = eElementService.getElementsByTagName("LineItemIdtobeDisconnect");
								 taglength=parentNodeLineItemId.getLength();
								 if(taglength>0)
								 {
									 for(int j=0;j<parentNodeLineItemId.getLength();j++)
									 {	
										 CLEPUtility.SysErr("**************************Reading and Inserting LineItemIdtobeDisconnect["+(j+1)+"]*******************************");							 							 
										 NodeList nlList = eElementService.getElementsByTagName("LineItemIdtobeDisconnect").item(j).getChildNodes();												 
										 Node nValue = (Node) nlList.item(0);
										 if(nValue!=null){										        	
											 lineItemID=lineItemID+nValue.getNodeValue().trim()+",";
										 }	
									 }
									 if(!"".equalsIgnoreCase(lineItemID))
									 {				
										 lineItems=Utility.removeCharAt(lineItemID,lineItemID.lastIndexOf(","));
										 csIOMS.setLong(1, xmlfileid);
										 missingAttribute="UniqueDisconnectionId in ServiceDetails tag";
										 csIOMS.setString(2,getTagValue("UniqueDisconnectionId", eElementService));
										 missingAttribute="LogicalSINo in ServiceDetails tag";
										 gblLogicalLSINo=getTagValue("LogicalSINo", eElementService);
										 csIOMS.setString(3, getTagValue("LogicalSINo", eElementService));
										 missingAttribute="ServiceId in ServiceDetails tag";
										 csIOMS.setString(4, "0");							
										 csIOMS.setString(5, lineItems);
										 missingAttribute="LocRecvDate in ServiceDetails tag";
										 csIOMS.setString(6, getTagValue("LocRecvDate", eElementService));
										 missingAttribute="BillingTrigger in ServiceDetails tag";
										 csIOMS.setString(7, getTagValue("BillingTrigger", eElementService));
										 missingAttribute="ReasonId in ServiceDetails tag";
										 csIOMS.setString(8, getTagValue("ReasonId", eElementService));
										 missingAttribute="RfsDate in ServiceDetails tag";
										 csIOMS.setString(9, getTagValue("RfsDate", eElementService));
										 missingAttribute="effectiveStartDate in ServiceDetails tag";
										 csIOMS.setString(10, getTagValue("effectiveStartDate", eElementService));
										 missingAttribute="effectiveDateForLSIChange in ServiceDetails tag";
										 csIOMS.setString(11, getTagValue("effectiveDateForLSIChange", eElementService));
										 missingAttribute="RemarksForLSIChange in ServiceDetails tag";
										 csIOMS.setString(12, getTagValue("RemarksForLSIChange", eElementService));
										 csIOMS.execute();
										 insertStatus=1;
									 }
									 	lineItemID="";lineItems="";
						 		 }else{
							 			insertStatus=0;	 
							 			conn.rollback();
										CLEPUtility.SysErr("<<==========================InComing Message is Invalid=============================>>");					
										clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>DISCONNECTION PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML,missing or spelling mistake LineItemIdtobeDisconnect tag </Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><SubNodeLineItemDetails><ErrorMsg></ErrorMsg></SubNodeLineItemDetails></ChangeOrder></orderHeader>");
										clepXmlDto.setJmsMessageID(jmsMessageID);
										ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);							
							 			break outer;
							 	 }
							 }					 
						}
					}else{
						insertStatus=0;	 				
						conn.rollback();
						CLEPUtility.SysErr("<<==========================InComing Message is Invalid=============================>>");					
						clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>DISCONNECTION PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML,missing or spelling mistake ServiceDetails tag </Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><SubNodeLineItemDetails><ErrorMsg></ErrorMsg></SubNodeLineItemDetails></ChangeOrder></orderHeader>");
						clepXmlDto.setJmsMessageID(jmsMessageID);
						ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);				
					}
			 	}else{	 				 		
		 			insertStatus=0;	
		 			conn.rollback();
					missingAttribute="[Internal error]{}";
					clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>DISCONNECTION PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><SubNodeLineItemDetails><ErrorMsg></ErrorMsg></SubNodeLineItemDetails></ChangeOrder></orderHeader>");
					clepXmlDto.setJmsMessageID(jmsMessageID);
					ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);
		 		}
			}catch(NullPointerException e){
				insertStatus=0;
				CLEPUtility.LOG(true, false, e, "from method disconnectionOrderType() for NullPointerException error");
				try{
					conn.rollback();
				}catch(Exception ex){
					CLEPUtility.LOG(true, false, ex, "from method disconnectionOrderType() while rollbacking the connection");
				}		
				clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>DISCONNECTION PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML,missing Attribute "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><SubNodeLineItemDetails><ErrorMsg></ErrorMsg></SubNodeLineItemDetails></ChangeOrder></orderHeader>");
				clepXmlDto.setJmsMessageID(jmsMessageID);
				ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);
				
			}catch(SQLException e){
				insertStatus=0;					
				CLEPUtility.LOG(true, false, e, "from method disconnectionOrderType() for SQLException error");
				try{
					conn.rollback();
				}catch(Exception ex){
					CLEPUtility.LOG(true, false, ex, "from method disconnectionOrderType() while rollbacking the connection");
				}
				missingAttribute="[Internal error]{}";
				clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>DISCONNECTION PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><SubNodeLineItemDetails><ErrorMsg></ErrorMsg></SubNodeLineItemDetails></ChangeOrder></orderHeader>");
				clepXmlDto.setJmsMessageID(jmsMessageID);
				ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);				
			}
			catch(Exception e){
				insertStatus=0;					
				CLEPUtility.LOG(true, false, e, "from method disconnectionOrderType() for others error");
				try{
					conn.rollback();
				}catch(Exception ex){
					CLEPUtility.LOG(true, false, ex, "from method disconnectionOrderType() while rollbacking the connection");
				}
				missingAttribute="[Internal error]{}";
				clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>DISCONNECTION PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><SubNodeLineItemDetails><ErrorMsg></ErrorMsg></SubNodeLineItemDetails></ChangeOrder></orderHeader>");
				clepXmlDto.setJmsMessageID(jmsMessageID);
				ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);						
		   }
		return insertStatus;
	}	
	
	/*Method:solutionChangeOrderTypePO
	 * Purpose:To Inserting the data into temporary table for  PO Tab for solution change order 
	 * */
	public static int solutionChangeOrderTypePO(NodeList parentNodeListPOList,long xmlfileid,String jmsMessageID,String fileName,String absFileName,String filePath,Connection conn){
		int insertStatus=-1;		
		CallableStatement csIOMS = null;
		String missingAttribute="";
		int taglength=0;
		CLEPXmlDto clepXmlDto=new CLEPXmlDto();
		try{
			//=================================PODETAIL TAB==================================================
			csIOMS=conn.prepareCall(spInsertPODETAILtoIOMS);
			taglength=parentNodeListPOList.getLength();
			if(taglength > 0 ){
				for(int i=0;i<parentNodeListPOList.getLength();i++)
				{					 
					 Node childNodePoDetail = parentNodeListPOList.item(i);
					 				
					 CLEPUtility.SysErr("**************************Reading and Inserting PODetails["+(i+1)+"]*******************************");					
					 
					 if (childNodePoDetail.getNodeType() == Node.ELEMENT_NODE)
					 {
						Element eElementPODetails = (Element) childNodePoDetail;
					 
					 	csIOMS.setLong(1, xmlfileid);
					 	missingAttribute="CustPONo in PODetails tag";
						csIOMS.setString(2,getTagValue("CustPONo", eElementPODetails));
						missingAttribute="CustPODate in PODetails tag";
						csIOMS.setString(3, getTagValue("CustPODate", eElementPODetails));
						missingAttribute="IsDefaultPO in PODetails tag";
						csIOMS.setString(4, getTagValue("IsDefaultPO", eElementPODetails));
						missingAttribute="Entity in PODetails tag";
						csIOMS.setString(5, getTagValue("Entity", eElementPODetails));
						missingAttribute="TotalPOAmount in PODetails tag";
						csIOMS.setString(6, getTagValue("TotalPOAmount", eElementPODetails));
						missingAttribute="PeriodInMonths in PODetails tag";
						csIOMS.setString(7, getTagValue("PeriodInMonths", eElementPODetails));
						missingAttribute="ContractStartDate in PODetails tag";
						csIOMS.setString(8, getTagValue("ContractStartDate", eElementPODetails));
						missingAttribute="ContractEndDate in PODetails tag";
						csIOMS.setString(9, getTagValue("ContractEndDate", eElementPODetails));
						missingAttribute="PORemarks in PODetails tag";
						if(getTagValue("PORemarks", eElementPODetails)!=null){
							csIOMS.setString(10, getTagValue("PORemarks", eElementPODetails));
						}else{
							csIOMS.setString(10, "");
						}
						missingAttribute="EmailID in PODetails tag";
						if(getTagValue("EmailID", eElementPODetails)!=null){
							csIOMS.setString(11, getTagValue("EmailID", eElementPODetails));
						}else{
							csIOMS.setString(11, "");
						}
						missingAttribute="UniqePoDetailId in PODetails tag";
						csIOMS.setString(12, getTagValue("UniqePoDetailId", eElementPODetails));
						csIOMS.execute();
						insertStatus=1;
				 	}					 
				}
			}else{
				insertStatus=0;	 				
				conn.rollback();
				CLEPUtility.SysErr("<<==========================InComing Message is Invalid=============================>>");					
				clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>SOLUTIONCHANGE PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML,missing PODetails tag </Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><SubNodeLineItemDetails><ErrorMsg></ErrorMsg></SubNodeLineItemDetails></ChangeOrder></orderHeader>");
				clepXmlDto.setJmsMessageID(jmsMessageID);
				ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);				
			}			
		}catch(NullPointerException e){
			insertStatus=0;			
			CLEPUtility.LOG(true, false, e, "from method solutionChangeOrderTypePO() for NullPointerException error");	
			try{
				conn.rollback();
			}catch(Exception ex){
				CLEPUtility.LOG(true, false, ex, "from method solutionChangeOrderTypePO()  while rollbaking the connection");
			}
			clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>SOLUTIONCHANGE PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML,missing Attribute "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><SubNodeLineItemDetails><ErrorMsg></ErrorMsg></SubNodeLineItemDetails></ChangeOrder></orderHeader>");
			clepXmlDto.setJmsMessageID(jmsMessageID);
			ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);						
		}
		catch(SQLException e){
			insertStatus=0;					
			CLEPUtility.LOG(true, false, e, "from method solutionChangeOrderTypePO() for SQLException error");
			try{
				conn.rollback();
			}catch(Exception ex){
				CLEPUtility.LOG(true, false, ex, "from method solutionChangeOrderTypePO()  while rollbaking the connection");
			}
			missingAttribute="[Internal error]{}";
			clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>SOLUTIONCHANGE PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><SubNodeLineItemDetails><ErrorMsg></ErrorMsg></SubNodeLineItemDetails></ChangeOrder></orderHeader>");
			clepXmlDto.setJmsMessageID(jmsMessageID);
			ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);			
			
		}catch(Exception e){
			insertStatus=0;					
			CLEPUtility.LOG(true, false, e, "from method solutionChangeOrderTypePO() for other error");
			try{
				conn.rollback();
			}catch(Exception ex){
				CLEPUtility.LOG(true, false, ex, "from method solutionChangeOrderTypePO()  while rollbaking the connection");
			}
			missingAttribute="[Internal error]{}";
			clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>SOLUTIONCHANGE PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><SubNodeLineItemDetails><ErrorMsg></ErrorMsg></SubNodeLineItemDetails></ChangeOrder></orderHeader>");
			clepXmlDto.setJmsMessageID(jmsMessageID);
			ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);						
		}
		return insertStatus;
	}
	
	/*Method:solutionChangeOrderTypeServiceDetails
	 * Purpose:To Inserting the data into temporary table regarding service detail
	 * information, New charge informaiotn for solution change order 
	 * */
	public static int solutionChangeOrderTypeServiceDetails(NodeList parentNodeListServiceList,long xmlfileid,String jmsMessageID,String fileName,String absFileName,String filePath,Connection conn){
		int insertStatus=-1;		
		CallableStatement csIOMS = null;
		CallableStatement csIOMSCharges=null;
		CallableStatement csIOMSLineitems=null;
		String chargeInfoID="",chargesID="";
		String isAdditionalCharge="";
		String PoDetailId="";
		String LOCRecvDate="";
		String BillingTriggerDate="";
		long serviceTabid=0l;
		String missingAttribute="";
		int taglength=0;
		CLEPXmlDto clepXmlDto=new CLEPXmlDto();
		try
		{
			int isSucessForValidation=0;
			//Inserting Data into Raterenwal Service Details Temporary table below
 			if(isSucessForValidation==0){
 				csIOMS=conn.prepareCall(spInsertSERVICETABSOLCHNGtoIOMS);
 				taglength=parentNodeListServiceList.getLength();
 				outer:
	 			if( taglength > 0 ){
	 				for(int i=0;i<parentNodeListServiceList.getLength();i++)
					{	
						 Node childNodeServiceDetails = parentNodeListServiceList.item(i);
						 CLEPUtility.SysErr("**************************Reading and Inserting ServiceDetails["+(i+1)+"]*******************************");							 
						 if(childNodeServiceDetails.getNodeType() == Node.ELEMENT_NODE)
						 {
							Element eElementService = (Element) childNodeServiceDetails;
						 				
						 	csIOMS.setLong(1, xmlfileid);
						 	missingAttribute="UniqueServiceDetailId in ServiceDetails tag";
						 	csIOMS.setString(2,getTagValue("UniqueServiceDetailId", eElementService));
						 	missingAttribute="LogicalSINo in ServiceDetails tag";
						 	gblLogicalLSINo=getTagValue("LogicalSINo", eElementService);
							csIOMS.setString(3, getTagValue("LogicalSINo", eElementService));
							missingAttribute="ServiceId in ServiceDetails tag";
							csIOMS.setString(4, getTagValue("ServiceId", eElementService));
							csIOMS.execute();
							
						 	NodeList parentNodeLineItem=eElementService.getElementsByTagName("LineItemDetails");
						 	csIOMSLineitems=conn.prepareCall(spInsertLINEITEMTABSOLCHNGtoIOMS);
						 	taglength=parentNodeLineItem.getLength();							 	
							if(taglength > 0 ){
							 	for(int k=0;k<parentNodeLineItem.getLength();k++)
							 	{
							 		Node childNodeLineItemsDetails=parentNodeLineItem.item(k);
							 		CLEPUtility.SysErr("**************************Reading and Inserting LineItemDetails["+(k+1)+"]*******************************");
							 		if(childNodeLineItemsDetails.getNodeType() == Node.ELEMENT_NODE)
							 		{
							 			Element eElementLineItems=(Element)childNodeLineItemsDetails;
							 			missingAttribute="isAdditionalCharge in LineItemDetails of ServiceDetails tag";
							 			isAdditionalCharge=getTagValue("isAdditionalCharge", eElementLineItems);	
							 			missingAttribute="PoDetailId in LineItemDetails of ServiceDetails tag";
							 			PoDetailId=getTagValue("PoDetailId", eElementLineItems);							 			
							 			missingAttribute="BillingTriggerInfo in LineItemDetails of ServiceDetails tag";
							 			NodeList parentNodeBillingTrigger = eElementLineItems.getElementsByTagName("BillingTriggerInfo");
							 			Node childNodeLineBillingTrigger=parentNodeBillingTrigger.item(0);
							 			if(childNodeLineBillingTrigger.getNodeType() == Node.ELEMENT_NODE)
								 		{
							 				Element eElementBillingTrigger=(Element)childNodeLineBillingTrigger;
							 				missingAttribute="locRecieveDate in BillingTriggerInfo of LineItemDetails tag";
							 				LOCRecvDate=getTagValue("locRecieveDate", eElementBillingTrigger);
							 				missingAttribute="billingTriggerDate in BillingTriggerInfo of LineItemDetails tag";
							 				BillingTriggerDate=getTagValue("billingTriggerDate", eElementBillingTrigger);
								 		}							 			
							 			NodeList parentNodeChargeInfoId = eElementLineItems.getElementsByTagName("ChargeInfoIdtobeDisconnect");
							 			taglength=parentNodeChargeInfoId.getLength();
							 			if(taglength <=0 && ("0".equalsIgnoreCase(isAdditionalCharge) ||"".equalsIgnoreCase(isAdditionalCharge))){
							 				insertStatus=0;	 				
											CLEPUtility.SysErr("<<==========================InComing Message is Invalid=============================>>");					
											clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>SOLUTIONCHANGE PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Reason>Failed Due to Invalid XML,atleast one tag should be available ChargeInfoIdtobeDisconnect tag or chargesInfoNode tag{isAdditionalCharge=1} </Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><SubNodeLineItemDetails><ErrorMsg></ErrorMsg></SubNodeLineItemDetails></ChangeOrder></orderHeader>");
											clepXmlDto.setJmsMessageID(jmsMessageID);
											ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);
											conn.rollback();
											break outer;
							 				
							 			}else{
					 						 String isCloseCharge="",isInActiveCharge="";
					 					 	 taglength=parentNodeChargeInfoId.getLength();							 					 							 					 
											 for(int j=0;j<parentNodeChargeInfoId.getLength();j++)
											 {	
												 Node childChargeInfoId=parentNodeChargeInfoId.item(j);
												 Element eElementChareInfoId=(Element)childChargeInfoId;
												 
												 CLEPUtility.SysErr("**************************Reading and Inserting ChargeInfoIdtobeDisconnect["+(j+1)+"]*******************************");
												 missingAttribute="ChargeInfoIdtobeDisconnect in LineItemDetails tag of ServiceDetails tag";
												 NodeList nlList = eElementLineItems.getElementsByTagName("ChargeInfoIdtobeDisconnect").item(j).getChildNodes();												 
											     Node nValue = (Node) nlList.item(0);
											     if(nValue!=null){														    	 
											    	   isCloseCharge=eElementChareInfoId.getAttribute("isCloseCharge");
											    	   isInActiveCharge=eElementChareInfoId.getAttribute("isInActiveCharge");
											    	   chargeInfoID=nValue.getNodeValue().trim();
											    	 
											    	 	csIOMSLineitems.setLong(1, xmlfileid);								
														missingAttribute="UniqueLineItemId in LineItemDetails tag of ServiceDetails tag";
														csIOMSLineitems.setString(2, getTagValue("UniqueLineItemId", eElementLineItems));
														csIOMSLineitems.setString(3, getTagValue("UniqueServiceDetailId", eElementService));
														missingAttribute="LineItemId in LineItemDetails tag of ServiceDetails tag";
														csIOMSLineitems.setString(4, getTagValue("LineItemId", eElementLineItems));
														csIOMSLineitems.setString(5, chargeInfoID);
														csIOMSLineitems.setString(6, isAdditionalCharge);
														csIOMSLineitems.setString(7, PoDetailId );
														missingAttribute="isCloseCharge in LineItemDetails tag of ServiceDetails tag";
														csIOMSLineitems.setString(8, isCloseCharge );
														missingAttribute="isInActiveCharge in LineItemDetails tag of ServiceDetails tag";
														csIOMSLineitems.setString(9,isInActiveCharge);
														missingAttribute="DisconnectionDate in LineItemDetails tag of ServiceDetails tag";
														csIOMSLineitems.setString(10, getTagValue("DisconnectionDate", eElementLineItems));
														csIOMSLineitems.setString(11, LOCRecvDate);
														csIOMSLineitems.setString(12, BillingTriggerDate);
														
														csIOMSLineitems.execute();
														insertStatus=1;
											     }	
											 }													   														
												
											chargeInfoID="";chargesID="";										
											serviceTabid=Long.valueOf(getTagValue("UniqueServiceDetailId", eElementService));
											if("1".equalsIgnoreCase(isAdditionalCharge))
											{
												NodeList parentChargeList = eElementLineItems.getElementsByTagName("chargesInfoNode");	
												taglength=parentChargeList.getLength();
												if(taglength > 0 ){
													for(int m=0;m<parentChargeList.getLength();m++)
													{					 
														 Node childNodeChargeInfo = parentChargeList.item(m);
														 CLEPUtility.SysErr("**************************Reading and Inserting chargesInfoNode["+(m+1)+"]********************************");
														 
														 csIOMSCharges=conn.prepareCall(spInsertCHARGESINFOSOLCHNGtoIOMS);
														 if (childNodeChargeInfo.getNodeType() == Node.ELEMENT_NODE)
														 {
															 Element eElementChargeInfo = (Element) childNodeChargeInfo;
															 	
															 String chargeAttribute=eElementChargeInfo.getAttribute("UniqueChargeId");
															 if(!"".equalsIgnoreCase(chargeAttribute)){
																 csIOMSCharges.setLong(1, xmlfileid);
																 missingAttribute="chargeType in LineItemDetails tag of ServiceDetails tag";
																 csIOMSCharges.setString(2,getTagValue("chargeType", eElementChargeInfo));	
																 missingAttribute="chargeName in LineItemDetails tag of ServiceDetails tag";
																 csIOMSCharges.setString(3,getTagValue("chargeName", eElementChargeInfo));			
																 missingAttribute="chargePeriod in LineItemDetails tag of ServiceDetails tag";
																 csIOMSCharges.setString(4, getTagValue("chargePeriod", eElementChargeInfo));		
																 missingAttribute="chargeAmount in LineItemDetails tag of ServiceDetails tag";
																 csIOMSCharges.setString(5, getTagValue("chargeAmount", eElementChargeInfo));		
																 missingAttribute="chargeFrequency in LineItemDetails tag of ServiceDetails tag";
																 csIOMSCharges.setString(6, getTagValue("chargeFrequency", eElementChargeInfo));	
																 missingAttribute="startDateLogic in LineItemDetails tag of ServiceDetails tag";
																 csIOMSCharges.setString(7, getTagValue("startDateLogic", eElementChargeInfo));		
																 missingAttribute="startDateDays in LineItemDetails tag of ServiceDetails tag";
																 csIOMSCharges.setString(8, getTagValue("startDateDays", eElementChargeInfo));		
																 missingAttribute="startDateMonth in LineItemDetails tag of ServiceDetails tag";
																 csIOMSCharges.setString(9, getTagValue("startDateMonth", eElementChargeInfo));		
																 missingAttribute="endDateLogic in LineItemDetails tag of ServiceDetails tag";
																 csIOMSCharges.setString(10, getTagValue("endDateLogic", eElementChargeInfo));		
																 missingAttribute="endDateDays in LineItemDetails tag of ServiceDetails tag";
																 csIOMSCharges.setString(11, getTagValue("endDateDays", eElementChargeInfo));		
																 missingAttribute="endDateMonth in LineItemDetails tag of ServiceDetails tag";
																 csIOMSCharges.setString(12, getTagValue("endDateMonth", eElementChargeInfo));
																 missingAttribute="endDateMonth in LineItemDetails tag of ServiceDetails tag";
																 csIOMSCharges.setString(13, getTagValue("annotation", eElementChargeInfo));
																 missingAttribute="exclude in LineItemDetails tag of ServiceDetails tag";
																 csIOMSCharges.setString(14, getTagValue("exclude", eElementChargeInfo));
																 missingAttribute="remarks in LineItemDetails tag of ServiceDetails tag";
																 csIOMSCharges.setString(15, getTagValue("remarks", eElementChargeInfo));															 
																 csIOMSCharges.setLong(16, serviceTabid);
																 csIOMSCharges.setString(17, getTagValue("UniqueLineItemId", eElementLineItems));
																 csIOMSCharges.setString(18, chargeAttribute);
																 csIOMSCharges.execute();
																 insertStatus=1;
															 }else{
																 	insertStatus=0;
																 	conn.rollback();
																	CLEPUtility.SysErr("<<==========================InComing Message is Invalid=============================>>");					
																	clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>SOLUTIONCHANGE PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML,missing UniqueChargeId Attribute in any chargesInfoNode </Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><SubNodeLineItemDetails><ErrorMsg></ErrorMsg></SubNodeLineItemDetails></ChangeOrder></orderHeader>");
																	clepXmlDto.setJmsMessageID(jmsMessageID);
																	ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);
																	break outer;
															  }
														 }									 
													}
												  }else{
												  	insertStatus=0;	 
												  	conn.rollback();
													CLEPUtility.SysErr("<<==========================InComing Message is Invalid=============================>>");					
													clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>SOLUTIONCHANGE PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML,missing chargesInfoNode tag in any LineItemDetails tag </Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><SubNodeLineItemDetails><ErrorMsg></ErrorMsg></SubNodeLineItemDetails></ChangeOrder></orderHeader>");
													clepXmlDto.setJmsMessageID(jmsMessageID);
													ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);														
													break outer;
											    }
											}							 				  
							 			}//
							 		 }
							 	}//each LineItemDetails
						    }else{
						    	//LineItemDetails
						    	insertStatus=0;	 				
						    	conn.rollback();
								CLEPUtility.SysErr("<<==========================InComing Message is Invalid=============================>>");					
								clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>SOLUTIONCHANGE PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML,missing LineItemDetails tag in any ServiceDetails tag </Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><SubNodeLineItemDetails><ErrorMsg></ErrorMsg></SubNodeLineItemDetails></ChangeOrder></orderHeader>");
								clepXmlDto.setJmsMessageID(jmsMessageID);
								ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);								
								break outer;
					     }
					 }						 														 								 
				 }
	 		}else{
 				//each serviceDetails
 				insertStatus=0;	 	
 				conn.rollback();
				CLEPUtility.SysErr("<<==========================InComing Message is Invalid=============================>>");					
				clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>SOLUTIONCHANGE PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML,missing ServiceDetails tag </Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><SubNodeLineItemDetails><ErrorMsg></ErrorMsg></SubNodeLineItemDetails></ChangeOrder></orderHeader>");
				clepXmlDto.setJmsMessageID(jmsMessageID);
				ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);					
 			}
 		}else{
 				insertStatus=0;						 				
 				conn.rollback();
 				missingAttribute="[Internal error]{}";
 				clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>SOLUTIONCHANGE PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><SubNodeLineItemDetails><ErrorMsg></ErrorMsg></SubNodeLineItemDetails></ChangeOrder></orderHeader>");
 				clepXmlDto.setJmsMessageID(jmsMessageID);
 				ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);	 				
 		}
		
	 }catch(NullPointerException e){
			insertStatus=0;			
			CLEPUtility.LOG(true, false, e, "from method solutionChangeOrderTypeServiceDetails() for NullPointerException error");		
			try{
				conn.rollback();
			}catch(Exception ex){
				CLEPUtility.LOG(true, false, ex, "from method solutionChangeOrderTypeServiceDetails() while rollbacking the connection");
			}
			clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>SOLUTIONCHANGE PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML,missing Attribute "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><SubNodeLineItemDetails><ErrorMsg></ErrorMsg></SubNodeLineItemDetails></ChangeOrder></orderHeader>");
			clepXmlDto.setJmsMessageID(jmsMessageID);
			ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);			
			
		}catch(SQLException e){
			insertStatus=0;					
			CLEPUtility.LOG(true, false, e, "from method solutionChangeOrderTypeServiceDetails() for SQLException error");
			try{
				conn.rollback();
			}catch(Exception ex){
				CLEPUtility.LOG(true, false, ex, "from method solutionChangeOrderTypeServiceDetails() while rollbacking the connection");
			}
			missingAttribute="[Internal error]{}";
			clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>SOLUTIONCHANGE PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><SubNodeLineItemDetails><ErrorMsg></ErrorMsg></SubNodeLineItemDetails></ChangeOrder></orderHeader>");
			clepXmlDto.setJmsMessageID(jmsMessageID);
			ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);
			
		}catch(IndexOutOfBoundsException e){
			insertStatus=0;					
			CLEPUtility.LOG(true, false, e, "from method solutionChangeOrderTypeServiceDetails() for IndexOutOfBoundsException error");
			try{
				conn.rollback();
			}catch(Exception ex){
				CLEPUtility.LOG(true, false, ex, "from method solutionChangeOrderTypeServiceDetails() while rollbacking the connection");
			}
			missingAttribute="[Internal error]{}";
			clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>SOLUTIONCHANGE PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><SubNodeLineItemDetails><ErrorMsg></ErrorMsg></SubNodeLineItemDetails></ChangeOrder></orderHeader>");
			clepXmlDto.setJmsMessageID(jmsMessageID);
			ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);
		}			
		catch(Exception e){
			insertStatus=0;					
			CLEPUtility.LOG(true, false, e, "from method solutionChangeOrderTypeServiceDetails() for other error");
			try{
				conn.rollback();
			}catch(Exception ex){
				CLEPUtility.LOG(true, false, ex, "from method solutionChangeOrderTypeServiceDetails() while rollbacking the connection");
			}
			missingAttribute="[Internal error]{}";
			clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>SOLUTIONCHANGE PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><SubNodeLineItemDetails><ErrorMsg></ErrorMsg></SubNodeLineItemDetails></ChangeOrder></orderHeader>");
			clepXmlDto.setJmsMessageID(jmsMessageID);
			ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);				
		}	
		return insertStatus;
	}
	
	/*Method:solutionChangeOrderTypeSubNode
	 * Purpose:To Inserting the data into temporary table regarding adding subnote in existing 
	 * line item for solution change order 
	 * */
	public static int solutionChangeOrderTypeSubNode(NodeList parentNodeListSubNodeLineItem,long xmlfileid,String jmsMessageID,String fileName,String absFileName,String filePath,Connection conn){
		int insertStatus=-1;		
		CallableStatement csIOMS = null;
		CallableStatement csIOMSCharges=null;		
		String isAdditionalCharge="";		
		int taglength=0;
		CLEPXmlDto clepXmlDto=new CLEPXmlDto();
		String missingAttribute="";
		try
		{			 	
				//Inserting Data into Solution change SubNode Details Temporary table below	 			
 				csIOMS=conn.prepareCall(spInsertSUBNODESOLCHNGtoIOMS);
 				taglength=parentNodeListSubNodeLineItem.getLength();
 				outer:
 				if(taglength > 0 ){
	 				for(int i=0;i<parentNodeListSubNodeLineItem.getLength();i++)
					{	
						 Node childNodeSubNodeDetails = parentNodeListSubNodeLineItem.item(i);
						 CLEPUtility.SysErr("**************************Reading and Inserting SubNodeLineItemDetails["+(i+1)+"]*******************************");							 
						 if(childNodeSubNodeDetails.getNodeType() == Node.ELEMENT_NODE)
						 {
							 Element eElementSubNode = (Element) childNodeSubNodeDetails;
							 				
							 	csIOMS.setLong(1, xmlfileid);
							 	missingAttribute="UniqueLineItemId in SubNodeLineItemDetails tag";
							 	csIOMS.setString(2, getTagValue("UniqueLineItemId", eElementSubNode));
							 	missingAttribute="LineItemId in SubNodeLineItemDetails tag";
								csIOMS.setString(3, getTagValue("LineItemId", eElementSubNode));
								missingAttribute="isAdditionalCharge in SubNodeLineItemDetails tag";
								csIOMS.setString(4, getTagValue("isAdditionalCharge", eElementSubNode));								
								
								isAdditionalCharge= getTagValue("isAdditionalCharge", eElementSubNode).trim();
								missingAttribute="BillingInfoNode in SubNodeLineItemDetails tag";
							 	NodeList parentNodeBillingInfo=eElementSubNode.getElementsByTagName("BillingInfoNode");								 	
							 	Node childNodeBillingInfo=parentNodeBillingInfo.item(0);
							 	CLEPUtility.SysErr("**************************Reading and Inserting BillingInfoNode*******************************");
							 	if(childNodeBillingInfo.getNodeType() == Node.ELEMENT_NODE)
							 	{
							 			Element eElementBillingInfo=(Element)childNodeBillingInfo;
							 			missingAttribute="creditPeriod in BillingInfoNode tag";
							 			csIOMS.setString(5,  getTagValue("creditPeriod", eElementBillingInfo));
							 			missingAttribute="billingMode in BillingInfoNode tag";
							 			csIOMS.setString(6,  getTagValue("billingMode", eElementBillingInfo));
							 			missingAttribute="billingFormat in BillingInfoNode tag";
							 			csIOMS.setString(7,  getTagValue("billingFormat", eElementBillingInfo));
							 			missingAttribute="licenseCoId in BillingInfoNode tag";
							 			csIOMS.setString(8,  getTagValue("licenseCoId", eElementBillingInfo));
							 			missingAttribute="taxation in BillingInfoNode tag";
							 			csIOMS.setString(9,  getTagValue("taxation", eElementBillingInfo));
							 			missingAttribute="billingLevel in BillingInfoNode tag";
							 			csIOMS.setString(10, getTagValue("billingLevel", eElementBillingInfo));
							 			missingAttribute="commitmentPeriod in BillingInfoNode tag";
							 			csIOMS.setString(11, getTagValue("commitmentPeriod", eElementBillingInfo));
							 			missingAttribute="penaltyClause in BillingInfoNode tag";
							 			csIOMS.setString(12, getTagValue("penaltyClause", eElementBillingInfo));
							 			missingAttribute="billingType in BillingInfoNode tag";
							 			csIOMS.setString(13, getTagValue("billingType", eElementBillingInfo));
							 			missingAttribute="billingLevelNo in BillingInfoNode tag";
							 			csIOMS.setString(14, getTagValue("billingLevelNo", eElementBillingInfo));
							 			missingAttribute="bcpid in BillingInfoNode tag";
							 			csIOMS.setString(15, getTagValue("bcpid", eElementBillingInfo));
							 			missingAttribute="noticePeriod in BillingInfoNode tag";
							 			csIOMS.setString(16, getTagValue("noticePeriod", eElementBillingInfo));
							 			missingAttribute="isnfa in BillingInfoNode tag";
							 			csIOMS.setString(17, getTagValue("isnfa", eElementBillingInfo));
							 			missingAttribute="PoDetailId in BillingInfoNode tag";
							 			csIOMS.setString(18, getTagValue("PoDetailId", eElementBillingInfo));
							 	}		
							 	missingAttribute="BillingTriggerInfo in SubNodeLineItemDetails tag";
							 	NodeList parentNodeBillingTriggerInfo=eElementSubNode.getElementsByTagName("BillingTriggerInfo");
							 	Node childNodeBillingTriggerInfo=parentNodeBillingTriggerInfo.item(0);
							 	CLEPUtility.SysErr("**************************Reading and Inserting BillingTriggerInfo*******************************");
							 	if(childNodeBillingTriggerInfo.getNodeType() == Node.ELEMENT_NODE)
							 	{
							 		Element eElementBillingTriggerInfo=(Element)childNodeBillingTriggerInfo;
							 		missingAttribute="locRecieveDate in BillingTriggerInfo of SubNodeLineItemDetails tag";
						 			csIOMS.setString(19, getTagValue("locRecieveDate", eElementBillingTriggerInfo));
						 			missingAttribute="billingTriggerDate in BillingTriggerInfo of SubNodeLineItemDetails tag";
						 			csIOMS.setString(20, getTagValue("billingTriggerDate", eElementBillingTriggerInfo));
						 			csIOMS.execute();
							 	}
								if("1".equalsIgnoreCase(isAdditionalCharge))
								{
									missingAttribute="NewchargesInfoNode in SubNodeLineItemDetails tag";
									NodeList parentChargeList = eElementSubNode.getElementsByTagName("NewchargesInfoNode");	
									taglength=parentChargeList.getLength();
									if(taglength > 0){
									for(int m=0;m<parentChargeList.getLength();m++)
									{					 
										Node childNodeChargeInfo = parentChargeList.item(m);
										CLEPUtility.SysErr("**************************Reading and Inserting NewchargesInfoNode["+(m+1)+"]********************************");
													 
										csIOMSCharges=conn.prepareCall(spInsertCHARGESINFOSOLCHNGtoIOMS);
										if (childNodeChargeInfo.getNodeType() == Node.ELEMENT_NODE)
										{
											Element eElementChargeInfo = (Element) childNodeChargeInfo;

											String chargeAttribute=eElementChargeInfo.getAttribute("UniqueChargeId");
											if(!"".equalsIgnoreCase(chargeAttribute)){
												csIOMSCharges.setLong(1, xmlfileid);
												missingAttribute="chargeType in NewchargesInfoNode of SubNodeLineItemDetails tag";
												csIOMSCharges.setString(2,  getTagValue("chargeType", eElementChargeInfo));
												missingAttribute="chargeName in NewchargesInfoNode of SubNodeLineItemDetails tag";
												csIOMSCharges.setString(3,  getTagValue("chargeName", eElementChargeInfo));			
												missingAttribute="chargePeriod in NewchargesInfoNode of SubNodeLineItemDetails tag";
												csIOMSCharges.setString(4,  getTagValue("chargePeriod", eElementChargeInfo));		
												missingAttribute="chargeAmount in NewchargesInfoNode of SubNodeLineItemDetails tag";
												csIOMSCharges.setString(5,  getTagValue("chargeAmount", eElementChargeInfo));		
												missingAttribute="chargeFrequency in NewchargesInfoNode of SubNodeLineItemDetails tag";
												csIOMSCharges.setString(6,  getTagValue("chargeFrequency", eElementChargeInfo));	
												missingAttribute="startDateLogic in NewchargesInfoNode of SubNodeLineItemDetails tag";
												csIOMSCharges.setString(7,  getTagValue("startDateLogic", eElementChargeInfo));		
												missingAttribute="startDateDays in NewchargesInfoNode of SubNodeLineItemDetails tag";
												csIOMSCharges.setString(8,  getTagValue("startDateDays", eElementChargeInfo));		
												missingAttribute="startDateMonth in NewchargesInfoNode of SubNodeLineItemDetails tag";
												csIOMSCharges.setString(9,  getTagValue("startDateMonth", eElementChargeInfo));		
												missingAttribute="endDateLogic in NewchargesInfoNode of SubNodeLineItemDetails tag";
												csIOMSCharges.setString(10, getTagValue("endDateLogic", eElementChargeInfo));		
												missingAttribute="endDateDays in NewchargesInfoNode of SubNodeLineItemDetails tag";
												csIOMSCharges.setString(11, getTagValue("endDateDays", eElementChargeInfo));		
												missingAttribute="endDateMonth in NewchargesInfoNode of SubNodeLineItemDetails tag";
												csIOMSCharges.setString(12, getTagValue("endDateMonth", eElementChargeInfo));		
												missingAttribute="annotation in NewchargesInfoNode of SubNodeLineItemDetails tag";
												csIOMSCharges.setString(13, getTagValue("annotation", eElementChargeInfo));
												missingAttribute="exclude in NewchargesInfoNode of SubNodeLineItemDetails tag";
												csIOMSCharges.setString(14, getTagValue("exclude", eElementChargeInfo));
												missingAttribute="remarks in NewchargesInfoNode of SubNodeLineItemDetails tag";
												csIOMSCharges.setString(15, getTagValue("remarks", eElementChargeInfo));												
												csIOMSCharges.setLong(16, Long.valueOf(getTagValue("UniqueLineItemId", eElementSubNode)));
												csIOMSCharges.setString(17, getTagValue("UniqueLineItemId", eElementSubNode));
												csIOMSCharges.setString(18,chargeAttribute);
												csIOMSCharges.execute();
												insertStatus=1;	
											}else{
												insertStatus=0;
												CLEPUtility.SysErr("<<==========================InComing Message is Invalid=============================>>");					
												clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>SOLUTIONCHANGE PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML,missing UniqueChargeId Attribute in any NewchargesInfoNode </Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><SubNodeLineItemDetails><ErrorMsg></ErrorMsg></SubNodeLineItemDetails></ChangeOrder></orderHeader>");
												clepXmlDto.setJmsMessageID(jmsMessageID);
												ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);
												break outer;												
										   }
										}									 
									}
									}else{
										//each charges
										insertStatus=0;
										CLEPUtility.SysErr("<<==========================InComing Message is Invalid=============================>>");					
										clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>SOLUTIONCHANGE PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML,missing NewchargesInfoNode tag in any SubNodeLineItemDetails </Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><SubNodeLineItemDetails><ErrorMsg></ErrorMsg></SubNodeLineItemDetails></ChangeOrder></orderHeader>");
										clepXmlDto.setJmsMessageID(jmsMessageID);
										ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);
										break outer;										
								  }
								}
							 }
						}	//each subnode details	
	 				}else{
	 					//subnode is optional so value of insertStatus=1
	 					insertStatus=1;
	 				}
			
		}catch(NullPointerException e){
			insertStatus=0;			
			CLEPUtility.LOG(true, false, e, "from method solutionChangeOrderTypeSubNode() for NullPointerException error");		
			clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>SOLUTIONCHANGE PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML,missing Attribute "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><SubNodeLineItemDetails><ErrorMsg></ErrorMsg></SubNodeLineItemDetails></ChangeOrder></orderHeader>");
			clepXmlDto.setJmsMessageID(jmsMessageID);
			ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);
			try{
				conn.rollback();
			}catch(Exception ex){
				CLEPUtility.LOG(true, false, ex, "from method solutionChangeOrderTypeSubNode() while rollbacking the connection");
			}
			
		}catch(SQLException e){
			insertStatus=0;					
			CLEPUtility.LOG(true, false, e, "from method solutionChangeOrderTypeSubNode() for SQLException error");
			missingAttribute="[Internal error]{Database related}";
			clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>SOLUTIONCHANGE PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><SubNodeLineItemDetails><ErrorMsg></ErrorMsg></SubNodeLineItemDetails></ChangeOrder></orderHeader>");
			clepXmlDto.setJmsMessageID(jmsMessageID);
			ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);
			try{
				conn.rollback();
			}catch(Exception ex){
				CLEPUtility.LOG(true, false, ex, "from method solutionChangeOrderTypeSubNode() while rollbacking the connection");
			}
		}
		catch(Exception e){
			insertStatus=0;					
			CLEPUtility.LOG(true, false, e, "from method solutionChangeOrderTypeSubNode() for other error");
			missingAttribute="[Internal error]{}";
			clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>SOLUTIONCHANGE PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><SubNodeLineItemDetails><ErrorMsg></ErrorMsg></SubNodeLineItemDetails></ChangeOrder></orderHeader>");
			clepXmlDto.setJmsMessageID(jmsMessageID);
			ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);
			try{
				conn.rollback();
			}catch(Exception ex){
				CLEPUtility.LOG(true, false, ex, "from method solutionChangeOrderTypeSubNode() while rollbacking the connection");
			}
		}	
		return insertStatus;
	}
	
	/*Method:solutionChangeServiceDetails
	 * Purpose:To Inserting the data into temporary table for Service Details related information for solution change order 
	 * */
	public static int solutionChangeServiceDetails(NodeList parentNodeListServiceList,long xmlfileid,String jmsMessageID,String fileName,String absFileName,String filePath,Connection conn){
		int insertStatus=-1;		
		CallableStatement csIOMS = null;
		CallableStatement csIOMSCharges=null;
		CallableStatement csIOMSLineitems=null;
		String chargeInfoID="",chargesID="";
		String isAdditionalCharge="";
		String PoDetailId="";
		String LOCRecvDate="";
		String BillingTriggerDate="";
		long serviceTabid=0l;
		String missingAttribute="";
		int taglength=0;
		CLEPXmlDto clepXmlDto=new CLEPXmlDto();
		try
		{
			 //===============================INSERTING INTO FILEPROCESS INFO==========================================				        												
	 			CallableStatement csProcessFileInfoInsert=conn.prepareCall(spInsertPROCESSFILEINFOtoIOMS); 
	 			csProcessFileInfoInsert.setLong(1, xmlfileid);
	 			csProcessFileInfoInsert.setLong(2, 5);//templateid for solution change (line item add, only addition charge or charge disconnection)
	 			csProcessFileInfoInsert.setString(3, "1");//user Id
	 			csProcessFileInfoInsert.setString(4, jmsMessageID);//messageRequestId
	 			csProcessFileInfoInsert.setString(5, fileName);//incomig filename
	 			csProcessFileInfoInsert.setString(6, absFileName);//fileName with path
	 			csProcessFileInfoInsert.setString(7,filePath);//filepath								
	 			csProcessFileInfoInsert.setInt(8, 0);
	 			csProcessFileInfoInsert.setInt(9, 0);
	 			csProcessFileInfoInsert.setString(10, "");
	 			csProcessFileInfoInsert.execute();
	 			int isSucessForValidation=csProcessFileInfoInsert.getInt(8);
	 			if(isSucessForValidation==0){
	 				conn.commit();//File Tracking Table should be commited
	 			}
		 //==========================================================================================	 			
	 	 //Inserting Data into Solution Change Service Details Temporary table below
	 			if(isSucessForValidation==0){
	 				csIOMS=conn.prepareCall(spInsertSERVICETABSOLCHNGtoIOMS);
	 				taglength=parentNodeListServiceList.getLength();
	 				outer:
	 				if( taglength > 0 ){
	 				for(int i=0;i<parentNodeListServiceList.getLength();i++)
					{	
						 Node childNodeServiceDetails = parentNodeListServiceList.item(i);
						 CLEPUtility.SysErr("**************************Reading and Inserting ServiceDetails["+(i+1)+"]*******************************");							 
						 if(childNodeServiceDetails.getNodeType() == Node.ELEMENT_NODE)
						 {
							 Element eElementService = (Element) childNodeServiceDetails;
							 				
							 	csIOMS.setLong(1, xmlfileid);
							 	missingAttribute="UniqueServiceDetailId in ServiceDetails tag";
							 	csIOMS.setString(2,getTagValue("UniqueServiceDetailId", eElementService));
							 	missingAttribute="logicalLSINo in ServiceDetails tag";
							 	gblLogicalLSINo=getTagValue("logicalLSINo", eElementService);
								csIOMS.setString(3, getTagValue("logicalLSINo", eElementService));
								missingAttribute="effectiveDateForLSIChange in ServiceDetails tag";
								csIOMS.setString(4, getTagValue("effectiveDateForLSIChange", eElementService));
								missingAttribute="RemarksForLSIChange in ServiceDetails tag";
								csIOMS.setString(5, getTagValue("RemarksForLSIChange", eElementService));
								missingAttribute="ReasonId in ServiceDetails tag";
								csIOMS.setString(6, getTagValue("ReasonId", eElementService));
								missingAttribute="RfsDate in ServiceDetails tag";
								csIOMS.setString(7, getTagValue("RfsDate", eElementService));
								missingAttribute="effectiveStartDate in ServiceDetails tag";
								csIOMS.setString(8, getTagValue("effectiveStartDate", eElementService));
								missingAttribute="ProductId in ServiceDetails tag";
								csIOMS.setString(9, getTagValue("ProductId", eElementService));
								missingAttribute="subProductId in ServiceDetails tag";
								csIOMS.setString(10, getTagValue("subProductId", eElementService));
								csIOMS.execute();	
								insertStatus=1;
						 }						 														 								 
					}
				}else{
					//each serviceDetails
					insertStatus=0;	 				
					conn.rollback();
					CLEPUtility.SysErr("<<==========================InComing Message is Invalid=============================>>");					
					clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>SOLUTIONCHANGE PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML,missing ServiceDetails tag </Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><SubNodeLineItemDetails><ErrorMsg></ErrorMsg></SubNodeLineItemDetails></ChangeOrder></orderHeader>");
					clepXmlDto.setJmsMessageID(jmsMessageID);
					ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);									
				}
			}else{
				insertStatus=0;	
				conn.rollback();
				missingAttribute="[Internal error]{Failed to Kept Data into File Process}";
				clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>SOLUTIONCHANGE PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><SubNodeLineItemDetails><ErrorMsg></ErrorMsg></SubNodeLineItemDetails></ChangeOrder></orderHeader>");
				clepXmlDto.setJmsMessageID(jmsMessageID);
				ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);	 				
			}
		
		}catch(NullPointerException e){
			insertStatus=0;			
			CLEPUtility.LOG(true, false, e, "from method solutionChangeServiceDetails() for NullPointerException error");
			try{
				conn.rollback();
			}catch(Exception ex){
				CLEPUtility.LOG(true, false, ex, "from method solutionChangeServiceDetails() while rollbacking the connection");
			}
			clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>SOLUTIONCHANGE PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML,missing Attribute "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><SubNodeLineItemDetails><ErrorMsg></ErrorMsg></SubNodeLineItemDetails></ChangeOrder></orderHeader>");
			clepXmlDto.setJmsMessageID(jmsMessageID);
			ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);
			
		}catch(SQLException e){
			insertStatus=0;					
			CLEPUtility.LOG(true, false, e, "from method solutionChangeServiceDetails() for SQLException error");
			try{
				conn.rollback();
			}catch(Exception ex){
				CLEPUtility.LOG(true, false, ex, "from method solutionChangeServiceDetails() while rollbacking the connection");
			}
			missingAttribute="[Internal error]{Database related}";
			clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>SOLUTIONCHANGE PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><SubNodeLineItemDetails><ErrorMsg></ErrorMsg></SubNodeLineItemDetails></ChangeOrder></orderHeader>");
			clepXmlDto.setJmsMessageID(jmsMessageID);
			ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);			
			
		}catch(IndexOutOfBoundsException e){
			insertStatus=0;					
			CLEPUtility.LOG(true, false, e, "from method solutionChangeServiceDetails() for IndexOutOfBoundsException error");
			try{
				conn.rollback();
			}catch(Exception ex){
				CLEPUtility.LOG(true, false, ex, "from method solutionChangeServiceDetails() while rollbacking the connection");
			}
			missingAttribute="[Internal error]{Bound of Memory Related}";
			clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>SOLUTIONCHANGE PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><SubNodeLineItemDetails><ErrorMsg></ErrorMsg></SubNodeLineItemDetails></ChangeOrder></orderHeader>");
			clepXmlDto.setJmsMessageID(jmsMessageID);
			ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);					
		}			
		catch(Exception e){
			insertStatus=0;					
			CLEPUtility.LOG(true, false, e, "from method solutionChangeServiceDetails() for other error");
			try{
				conn.rollback();
			}catch(Exception ex){
				CLEPUtility.LOG(true, false, ex, "from method solutionChangeServiceDetails() while rollbacking the connection");
			}
			missingAttribute="[Internal error]{}";
			clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>SOLUTIONCHANGE PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><SubNodeLineItemDetails><ErrorMsg></ErrorMsg></SubNodeLineItemDetails></ChangeOrder></orderHeader>");
			clepXmlDto.setJmsMessageID(jmsMessageID);
			ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);						
		}	
		return insertStatus;
	}
	
	/*Method:solutionChangeProductCatelog
	 * Purpose:To Inserting the data regarding product catelog into temporary table for
	 * solution change order  
	 * */
	public static int solutionChangeProductCatelog(NodeList parentNodeListNewLineItem,long xmlfileid,String jmsMessageID,String fileName,String absFileName,String filePath,Connection conn){
		int insertStatus=-1;
		String missingAttribute="";
		CLEPXmlDto clepXmlDto=new CLEPXmlDto();
		CallableStatement csIOMS=null;
		CallableStatement csIOMSCharges=null;
		int taglength=0;
		int isComponent=0,isSendToM6=0;
		String ParentLineItemId="";
		int isValidServicesWithM6NorM6=-1;
		int insertFlag=-1;
		try{
			missingAttribute="productCatelog";
			 
			 taglength=parentNodeListNewLineItem.getLength();
			 if(taglength > 0){			
				 for(int j=0;j<parentNodeListNewLineItem.getLength();j++)
				 {
					 Node chileNodeProductCatelog=parentNodeListNewLineItem.item(j);
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
				 		
				 		 uniqueProductDetaiId= nValueUniqProdId.getNodeValue().trim();
				 		 missingAttribute="isSendToM6 in productCatelog tag";
				 		 isSendToM6=Integer.parseInt(getTagValue("isSendToM6", eElementProdCatelog));
				 		 if((getTagValue("ParentLineItemId", eElementProdCatelog)!=null) && (!"".equalsIgnoreCase(getTagValue("ParentLineItemId", eElementProdCatelog)))){
				 		 	ParentLineItemId=getTagValue("ParentLineItemId", eElementProdCatelog);
				 		 }else{
				 			ParentLineItemId="0";
				 		 }
					 		
					 	  missingAttribute="product in productCatelog tag";
					 	  NodeList parentNodeList5 = eElementProdCatelog.getElementsByTagName("product");																										
					 	  Node childNodeProduct = parentNodeList5.item(0);
					 	  CLEPUtility.SysErr("**************************Reading and Inserting product*******************************");								 	  
					 	  if (childNodeProduct.getNodeType() == Node.ELEMENT_NODE)
					 	  {
					 		  Element eElementProduct = (Element) childNodeProduct;
					 		  missingAttribute="isServiceSummarry in productCatelog tag";
					 		  int isServiceSummarry=Integer.parseInt(getTagValue("isServiceSummarry", eElementProduct));
					 		  missingAttribute="isComponent in productCatelog tag";
					 		   isComponent=Integer.parseInt(getTagValue("isComponent", eElementProduct));								 		  
					 		  if(isServiceSummarry==0){	
					 			  csIOMS.setLong(1, xmlfileid);					 			  
					 			  csIOMS.setString(2,uniqueProductDetaiId);
					 			  csIOMS.setString(3,ParentLineItemId);
					 			  missingAttribute="isSendToM6 in productCatelog tag";
					 			  csIOMS.setString(4,getTagValue("isSendToM6", eElementProdCatelog));
					 			  missingAttribute="logicalLsiNo in productCatelog tag";
					 			  csIOMS.setString(5,getTagValue("logicalLsiNo", eElementProdCatelog));
					 			  missingAttribute="productName in productCatelog tag";
					 			  csIOMS.setString(6,getTagValue("productName", eElementProduct));
					 			  missingAttribute="serviceDetailId in productCatelog tag";
					 			  csIOMS.setString(7,getTagValue("serviceDetailId", eElementProduct));
					 			  missingAttribute="isServiceSummarry in productCatelog tag";
					 			  csIOMS.setString(8,getTagValue("isServiceSummarry", eElementProduct));
					 			  missingAttribute="isComponent in productCatelog tag";
					 			  csIOMS.setString(9,getTagValue("isComponent", eElementProduct));
					 		  }else{
					 			 csIOMS.setLong(1, xmlfileid);					 			  
					 			  csIOMS.setString(2,uniqueProductDetaiId);
					 			  csIOMS.setString(3,ParentLineItemId);
					 			  missingAttribute="isSendToM6 in productCatelog tag";
					 			  csIOMS.setString(4,getTagValue("isSendToM6", eElementProdCatelog));
					 			  missingAttribute="logicalLsiNo in productCatelog tag";
					 			  csIOMS.setString(5,getTagValue("logicalLsiNo", eElementProdCatelog));
					 			  missingAttribute="productName in productCatelog tag";
					 			  csIOMS.setString(6,getTagValue("productName", eElementProduct));
					 			  missingAttribute="serviceDetailId in productCatelog tag";
					 			  csIOMS.setString(7,getTagValue("serviceDetailId", eElementProduct));
					 			  missingAttribute="isServiceSummarry in productCatelog tag";
					 			  csIOMS.setString(8,getTagValue("isServiceSummarry", eElementProduct));
					 			  missingAttribute="isComponent in productCatelog tag";
					 			  csIOMS.setString(9,getTagValue("isComponent", eElementProduct));
					 			  //code here for service summarry details
					 			  long serviceTypeId=0l,serviceDetailId=0l;
					 			  missingAttribute="serviceDetailId or serviceTypeId in productCatelog tag";
					 			  if(CLEPUtility.isValidNONNegativeNumeric(getTagValue("serviceDetailId", eElementProduct))){								 				 					 				  							 				
					 				 serviceDetailId=Long.valueOf(getTagValue("serviceDetailId", eElementProduct));								 				 
					 			  }else{
					 				 serviceDetailId=0;
					 			  }					 			 								 			 
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
							 csIOMS.setString(10,getTagValue("creditPeriod", eElementBillInfo));	
							 missingAttribute="billingMode in billingInfoNode tag";
							 csIOMS.setString(11,getTagValue("billingMode", eElementBillInfo));
							 missingAttribute="billingFormat in billingInfoNode tag";
							 csIOMS.setString(12, getTagValue("billingFormat", eElementBillInfo));
							 missingAttribute="licenseCoId in billingInfoNode tag";
							 csIOMS.setString(13, getTagValue("licenseCoId", eElementBillInfo));
							 missingAttribute="taxation in billingInfoNode tag";
							 csIOMS.setString(14, getTagValue("taxation", eElementBillInfo));
							 missingAttribute="billingLevel in billingInfoNode tag";
							 csIOMS.setString(15, getTagValue("billingLevel", eElementBillInfo));
							 missingAttribute="commitmentPeriod in billingInfoNode tag";
							 csIOMS.setString(16, getTagValue("commitmentPeriod", eElementBillInfo));	
							 missingAttribute="penaltyClause in billingInfoNode tag";
							 csIOMS.setString(17, getTagValue("penaltyClause", eElementBillInfo));
							 missingAttribute="billingType in billingInfoNode tag";
							 csIOMS.setString(18, getTagValue("billingType", eElementBillInfo));
							 missingAttribute="billingLevelNo in billingInfoNode tag";
							 csIOMS.setString(19, getTagValue("billingLevelNo", eElementBillInfo));
							 missingAttribute="bcpid in billingInfoNode tag";
							 csIOMS.setString(20, getTagValue("bcpid", eElementBillInfo));	
							 missingAttribute="noticePeriod in billingInfoNode tag";
							 csIOMS.setString(21, getTagValue("noticePeriod", eElementBillInfo));
							 missingAttribute="isnfa in billingInfoNode tag";
							 csIOMS.setString(22, getTagValue("isnfa", eElementBillInfo));
							 missingAttribute="PoDetailId in billingInfoNode tag";
							 csIOMS.setString(23, getTagValue("PoDetailId", eElementBillInfo));
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
									csIOMS.setString(24,getTagValue("priLocType", eElementServiceLoc));
								}else{
									csIOMS.setString(24,"");
								}
								missingAttribute="priLocId in serviceLocInfoNode tag";
								if(getTagValue("priLocId", eElementServiceLoc)!=null){
									csIOMS.setString(25,getTagValue("priLocId", eElementServiceLoc));
								}else{
									csIOMS.setString(25,"");
								}
								missingAttribute="secLocType in serviceLocInfoNode tag";
								if(getTagValue("secLocType", eElementServiceLoc)!=null){
									csIOMS.setString(26, getTagValue("secLocType", eElementServiceLoc));	
								}else{
									csIOMS.setString(26, "");
								}
								missingAttribute="secLocId in serviceLocInfoNode tag";
								if(getTagValue("secLocId", eElementServiceLoc)!=null){
									csIOMS.setString(27, getTagValue("secLocId", eElementServiceLoc));
								}else{
									csIOMS.setString(27, "");
								}
								missingAttribute="fromLoc in serviceLocInfoNode tag";
								if(getTagValue("fromLoc", eElementServiceLoc)!=null){
									csIOMS.setString(28, getTagValue("fromLoc", eElementServiceLoc));
								}else{
									csIOMS.setString(28, "");
								}
								missingAttribute="toLoc in serviceLocInfoNode tag";
								if(getTagValue("toLoc", eElementServiceLoc)!=null){
									csIOMS.setString(29, getTagValue("toLoc", eElementServiceLoc));
								}else{
									csIOMS.setString(29, "");
								}																		 
							}	
							if(isSendToM6==0){
								//==========================BILLING TRIGGERINFO============================================
								missingAttribute="billingTriggerInfo in productCatelog tag";
								NodeList parentNodeList8 = eElementProdCatelog.getElementsByTagName("billingTriggerInfo");	
								Node childNodeBillingTrigger = parentNodeList8.item(0);
								CLEPUtility.SysErr("**************************Reading and Inserting billingTriggerInfo********************************");										
								if (childNodeBillingTrigger.getNodeType() == Node.ELEMENT_NODE)
								{												
									Element eElementBillingTrg = (Element) childNodeBillingTrigger;
									missingAttribute="locNo in billingTriggerInfo tag";
									csIOMS.setString(30,getTagValue("locNo", eElementBillingTrg));
									missingAttribute="locDate in billingTriggerInfo tag";
									csIOMS.setString(31, getTagValue("locDate", eElementBillingTrg));
									missingAttribute="locRecieveDate in billingTriggerInfo tag";
									csIOMS.setString(32, getTagValue("locRecieveDate", eElementBillingTrg));
									missingAttribute="billingTriggerDate in billingTriggerInfo tag";
									csIOMS.setString(33, getTagValue("billingTriggerDate", eElementBillingTrg));										
									csIOMS.execute();
									isValidServicesWithM6NorM6=1;
								}
							}else if(isSendToM6==1){
								csIOMS.setString(30,null);						 						 						 
								csIOMS.setString(31, null);						 												 
								csIOMS.setString(32, null);		
								csIOMS.setString(33, null);
								csIOMS.execute();
								isValidServicesWithM6NorM6=1;
							}				
							if(isComponent==1){
							//==============================COMPONENT INFO======================================
								
								// component code here (in future) 
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
										 csIOMSCharges=conn.prepareCall(spInsertCHARGESINFOSOLCHNGtoIOMS);
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
											 csIOMSCharges.setString(14, getTagValue("exclude", eElementChargeInfo));	
											 csIOMSCharges.setString(15, " ");
											 if("0".equalsIgnoreCase(ParentLineItemId) || ParentLineItemId==null ){
												 csIOMSCharges.setLong(16, 0);
											 }else{
												 csIOMSCharges.setLong(16, Long.valueOf(ParentLineItemId));
											 }									 
											 csIOMSCharges.setLong(17, Long.valueOf(uniqueProductDetaiId));
											 csIOMSCharges.setString(18, chargeAttribute);
											 csIOMSCharges.execute();
											 insertFlag=1;insertStatus=1;
											 }else{
												 	insertFlag=0;
												 	conn.rollback();
													CLEPUtility.SysErr("<<==========================InComing Message is Invalid=============================>>");																
													clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>SOLUTIONCHANGE PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML,UniqueChargeId can not left blank or missing in any chargeInfoNode tag in XML</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg></ErrorMsg></Charges></ChangeOrder></orderHeader>");
													clepXmlDto.setJmsMessageID(jmsMessageID);
													ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);
													//break outer;
											 }
										 }									 
									}//each charges
								}else{
									insertFlag=0;
									conn.rollback();
									CLEPUtility.SysErr("<<==========================InComing Message is Invalid=============================>>");					
									clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>SOLUTIONCHANGE PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML,chargesInfoNode tag is missing in any ProductCatelog tag in XML</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg></ErrorMsg></Charges></ChangeOrder></orderHeader>");
									clepXmlDto.setJmsMessageID(jmsMessageID);
									ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);
									//break outer;
								}
							}//charges code									
				   	   }
				 }
		 	}else{
				//each prodct catelog
				insertFlag=0;
				conn.rollback();
				CLEPUtility.SysErr("<<==========================InComing Message is Invalid=============================>>");					
				clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>SOLUTIONCHANGE PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Reason>Failed Due to Invalid XML,ProductCatelog tag is missing in XML</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg></ErrorMsg></Charges></ChangeOrder></orderHeader>");
				ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);
				//break outer;
		 	}
		}catch(NullPointerException e){
			insertStatus=0;			
			CLEPUtility.LOG(true, false, e, "from method solutionChangeProductCatelog() for NullPointerException error");	
			try{
				conn.rollback();
			}catch(Exception ex){
				CLEPUtility.LOG(true, false, ex, "from method solutionChangeProductCatelog() while rollbacking the connection");
			}
			clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>SOLUTIONCHANGE PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Reason>Failed Due to Invalid XML,missing Attribute "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><SubNodeLineItemDetails><ErrorMsg></ErrorMsg></SubNodeLineItemDetails></ChangeOrder></orderHeader>");
			ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);			
			
		}
		catch(Exception e){
			insertStatus=0;					
			CLEPUtility.LOG(true, false, e, "from method solutionChangeProductCatelog() for other error");
			try{
				conn.rollback();
			}catch(Exception ex){
				CLEPUtility.LOG(true, false, ex, "from method solutionChangeProductCatelog() while rollbacking the connection");
			}
			missingAttribute="[Internal error]{}";
			clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>SOLUTIONCHANGE PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Reason>Failed Due to "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><SubNodeLineItemDetails><ErrorMsg></ErrorMsg></SubNodeLineItemDetails></ChangeOrder></orderHeader>");
			ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);					
		 }
		return insertStatus;
	}
	
	
	/*Method:validateAndProcessChangeOrder
	 * Purpose: The purpose is to Validate and process the data for Change order 
	 * (Dissconnection order/Solution Change order) and also to insert workflow  
	 * dynamically on the behalf of workflow id   
     * */
	public static int validateAndProcessChangeOrder(Element elementOrdExtAttributeElement,String orderType, String orderSubType, long xmlfileid,CLEPXmlDto clepXmlDto,String jmsMessageID,String fileName,String absFileName,String filePath,Connection conn,String isWorkFlow){
		int successStatus=-1;
		CallableStatement csIOMS=null;
		String missAttribute="";
		String workFlowId = null;
		try{
			//################################################[DISCONNECTION]###########################################################################
			if("DISCONNECTION".equalsIgnoreCase(orderType)){
					 
				 NodeList parentNodeServiceDetailsList = elementOrdExtAttributeElement.getElementsByTagName("ServiceDetails");
				 int insertStatus=ParseXMLForChangeOrder.disconnectionOrderType(parentNodeServiceDetailsList,xmlfileid,jmsMessageID,fileName,absFileName,filePath,conn);
				 conn.commit();
				 if(insertStatus==1){
					
					 int isProcessChangeOrderDisconn=-1;
					 //==========================================================================================
					 					 //CHANGE ORDER VALIDATION FOR DISCONNECTION START HERE		
					 //==========================================================================================
							CLEPUtility.SysErr("--------- ChangeOrder DataType Validation for Disconnection Start >>>>>>>>>>>>");
																		
							CallableStatement cstmt=conn.prepareCall(spInsertVALIDATEDISCONNRtoIOMS); 
							cstmt.setLong(1, xmlfileid);
							cstmt.setLong(2, 0);
							cstmt.setInt(3, 0);
							cstmt.setString(4, "");
							cstmt.setString(5, "");
							cstmt.setString(6, "");
							cstmt.setNull(7, java.sql.Types.CLOB);
							cstmt.setString(8, "");
							cstmt.execute();
							
							CLEPUtility.SysErr("---------The Tracking logs are : "+cstmt.getString(6));
							long result=cstmt.getLong(2);
							if(result==2){
								CLEPUtility.SysErr("--------- ChangeOrder DataType Validation for Disconnection SUCCESS ------");												
								isProcessChangeOrderDisconn=1;								
							}else{
								CLEPUtility.SysErr("--------- ChangeOrder DataType Validation for Disconnection Failed(Refer:Error XML File for more information) ------");											
								isProcessChangeOrderDisconn=0;
								conn.rollback();
							}
							Clob xmlValidateData=cstmt.getClob(7);
							if(xmlValidateData!=null){
								byte byteArrValidation[]=Utility.clobToByteArray(xmlValidateData);
								String xmlResponseValidationData=new String(byteArrValidation);	
								if(isProcessChangeOrderDisconn==0){
									
									clepXmlDto.setXmlData(xmlResponseValidationData);											
									CLEPUtility.SysErr("----------- Sending Response to MPP starting >>>>>>>>>>>");																																
									ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);
									String responseMsgId=clepXmlDto.getSendMessageID();									
								}
						   }else{
							  	conn.rollback();
							  	String missingAttribute="[Internal error]{}";
							  	CLEPUtility.SysErr("------------ Error: ChangeOrder DataType Validation Response Creating FAILED --------");
								clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>DISCONNECTION PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Reason>Failed Due to "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><SubNodeLineItemDetails><ErrorMsg></ErrorMsg></SubNodeLineItemDetails></ChangeOrder></orderHeader>");
								ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);
								isProcessChangeOrderDisconn=-1;								
						  }
						//==========================================================================================
													//CHANGE ORDER PROCESSING FOR DISCONNECTION START HERE
						//==========================================================================================
							int orderNo=0;
							if(isProcessChangeOrderDisconn==1){
								CLEPUtility.SysErr("--------- Change Order for Disconnection Processing start >>>>>>>>>>>");												
								
								csIOMS=conn.prepareCall(spInsertPROCESSCHANGEORDERtoIOMS);
								csIOMS.setLong(1, xmlfileid);
								csIOMS.setLong(2, 1);//empid
								csIOMS.setLong(3, 0);//result
								csIOMS.setLong(4, 0);
								csIOMS.setString(5, "");//mscode
								csIOMS.setString(6, "");//diagnostic
								csIOMS.setString(7, "");//log
								csIOMS.setLong(8, 0);
								csIOMS.setNull(9, java.sql.Types.CLOB);
								csIOMS.execute();																				
								
								orderNo=csIOMS.getInt(8);
								long resultProcess=csIOMS.getInt(3);
								int isProcessAhead=-1;
								if(resultProcess==2){
									isProcessAhead=1;
									CLEPUtility.SysErr("-------------- Change Order Created Successfully:-"+csIOMS.getInt(8)+" -----------");	
								}else{
									CLEPUtility.SysErr("--------- ChangeOrder Processing for Disconnection Failed(Refer:Error XML File for more information) --------");
									//Vijay start
									if(csIOMS.getString(6) !=null){
										CLEPUtility.SysErr("---------ChangeOrder Processing for Disconnection Change Failed due to Internal Error------");
										CLEPUtility.SysErr(csIOMS.getString(6));
									}
									//vijay end
									isProcessAhead=0;
									conn.rollback();
								}

								Clob xmlProcessData=csIOMS.getClob(9);
								if(xmlProcessData!=null){
									byte byteArrProcess[]=Utility.clobToByteArray(xmlProcessData);
									String xmlResponseProcessData=new String(byteArrProcess);
									
									/*------------- If Suspention Processing success then send it for Billing Trigger ------------*/
									if(isProcessAhead==1){
										long LSINO=0l;int succesStatusBT=-1;
										if(gblLogicalLSINo!=null){
											LSINO=Long.valueOf(gblLogicalLSINo);
										}
										String serviceTypeID=getServiceTypeID(LSINO,conn);
										if("False".equalsIgnoreCase(isWorkFlow)){
											/*-------------------- Billing Trigger for SaaS Only -------------*/
											
											/*Use  this method processOnlyNoNM6Product() if you want to insert workflow with auto approval and also publish with billing trigger*/
											//succesStatusBT=processOnlyNoNM6Product(orderNo,xmlfileid,"2",conn,jmsMessageID,"3","23");
											
											/*This method validate some data and attach workflow and do auto approval */
											succesStatusBT=ProcessXMLforCLEP.processOnlyNoNM6ProductForChange(orderNo,xmlfileid,"2",conn,jmsMessageID,"3","23");
											
											if(succesStatusBT==1){
												/*Update XML response for ib2bMessage. This tag <Ib2bMessage> contain "Order is waiting for RFBT" */
												if(xmlResponseProcessData.contains("<Ib2bMessage>")){
													xmlResponseProcessData = xmlResponseProcessData.replace("<Ib2bMessage>", "<Ib2bMessage>Order is waiting for RFBT");
												}	
												clepXmlDto.setStage(AppConstants.CLEP_SUCCESS);
												clepXmlDto.setXmlData(xmlResponseProcessData);										
												CLEPUtility.SysErr("---------- Sending Response to MPP Starting >>>>>>>>>>>");																					
												int sendStatus=ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"N","N",conn);										
											}
										}else if("True".equalsIgnoreCase(isWorkFlow)){
											/*-------------------- Billing Trigger for SaaS ERP --------------*/
											//conn.commit();
											CLEPUtility.SysErr("************* SaaS ERP Change Order *************");
											/*Vijay
											 *  add an other parameter(workflow id), 
											  * the purpose is to insert workflow dynamically on the behalf of workflow id receiving from XML file 
											  * */
											workFlowId = clepXmlDto.getWorkFlowId();
											int isValidateSuccess=validateSaaSErPChangeOrder(orderNo,xmlfileid,jmsMessageID,"3","23",conn, workFlowId);
											
											/*Vijay start */
											if(isValidateSuccess==1){
												CLEPUtility.SysErr("---------- Waiting for Aproval against orderNo:-"+orderNo+" >>>>>>>>>");
												/* 
												 *Vijay
												 *Start here to send the msg to AIE 
												 */
												
												 
												/*clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>SO</OrderType><OrderExtAttributes><OrderId>"+uniqOrderId+"</OrderId><OrderNo>"+orderNo+"</OrderNo><FileId>"+xmlfileid+"</FileId><MessageId>"+jmsMessageID+"</MessageId><Status>SUCCESS</Status><CancelDate></CancelDate><Reason_Remarks></Reason_Remarks><Stage>AM</Stage><ApprovalRemarks></ApprovalRemarks></OrderExtAttributes></ChangeOrder></orderHeader>");
												clepXmlDto.setJmsMessageID(jmsMessageID);
												clepXmlDto.setStage("SUCCESS");
												ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"N","N",conn);*/
												 
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
												//Vijay end
												
											}else{
												CLEPUtility.SysErr("---------- PO Validation Failed for Aproval against orderNo:-"+orderNo+" >>>>>>>>>");
											}
											
											/*Vijay end */
											
											//CLEPUtility.SysErr("--------- Waiting for Aproval against orderNo:-"+orderNo+" >>>>>>>>>");
											
										}else{											
											 missAttribute=" Attribute1 is Invalid{Hint:should be True for Workflow or False without Workflow} ";
											 CLEPUtility.SysErr("---------- "+missAttribute);
											 throw new IOESException("------ Error:    Attribute1 is Invalid ! -----------");
										}
																														
										/*--------------- If Processing Failed then send it to Mpp error response -----------*/
									}else if(isProcessAhead==0){
										clepXmlDto.setXmlData(xmlResponseProcessData);										
										CLEPUtility.SysErr("-------- Sending Response to MPP Starting >>>>>>>>>>>");																					
										int sendStatus=ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);								
									}
								}else{
									conn.rollback();
									String missingAttribute="[Internal error]{}";
									CLEPUtility.SysErr("------------ Error: ChangeOrder Processing Response Creating FAILED --------");
									clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>DISCONNECTION PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Reason>Failed Due to "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><SubNodeLineItemDetails><ErrorMsg></ErrorMsg></SubNodeLineItemDetails></ChangeOrder></orderHeader>");
									ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);
									successStatus=-1;									
								}
							}
				 		}
				}
			
			//################################################[ SOLUTIONCHANGE ]###########################################################################
			if("SOLUTIONCHANGE".equalsIgnoreCase(orderType)){
				
				int insertStatus=-1;
				NodeList parentNodeListPOList = elementOrdExtAttributeElement.getElementsByTagName("PODetails");								
				int insertPOStatus=solutionChangeOrderTypePO(parentNodeListPOList,xmlfileid,jmsMessageID, fileName, absFileName, filePath,conn);
				if(insertPOStatus==1){
					NodeList parentNodeListServiceList = elementOrdExtAttributeElement.getElementsByTagName("ServiceDetails");
					insertStatus=solutionChangeServiceDetails(parentNodeListServiceList,xmlfileid, jmsMessageID, fileName, absFileName, filePath,conn);										
				}else{
					CLEPUtility.SysErr("---------- Data Insertion failed -----------");
					insertStatus=0;
				}
				if(insertStatus==1){
					//NodeList parentNodeListSubNodeLineItem = elementOrdExtAttributeElement.getElementsByTagName("SubNodeLineItemDetails");
					//insertStatus=solutionChangeOrderTypeSubNode(parentNodeListSubNodeLineItem,xmlfileid, jmsMessageID, fileName, absFileName, filePath,conn);					
				}
				if(insertStatus==1){
					NodeList parentNodeListNewLineItem = elementOrdExtAttributeElement.getElementsByTagName("productCatelog");
					insertStatus=solutionChangeProductCatelog(parentNodeListNewLineItem,xmlfileid, jmsMessageID, fileName, absFileName, filePath,conn);					
				}
				conn.commit();				
				int isProcessChangeOrderSolutionChange=-1;
				 //============================================================================================================================
				 					 //CHANGE ORDER VALIDATION FOR SOLUTIONCHANGE START HERE		
				 //============================================================================================================================
					if(insertStatus==1){
						CLEPUtility.SysErr("------------ ChangeOrder DataType Validation for Solution Change Start >>>>>>>>>>>>");								 												
						
						CallableStatement cstmt=conn.prepareCall(spInsertVALIDATESOLCHNGtoIOMS); 
						cstmt.setLong(1, xmlfileid);
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
							clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>SOLUTIONCHANGE PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Reason>Failed Due to "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><SubNodeLineItemDetails><ErrorMsg></ErrorMsg></SubNodeLineItemDetails></ChangeOrder></orderHeader>");
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
									long LSINO=0l;int succesStatusBT=-1;
									if(gblLogicalLSINo!=null){
										LSINO=Long.valueOf(gblLogicalLSINo);
									}
									String serviceTypeID=getServiceTypeID(LSINO,conn);
									if("False".equalsIgnoreCase(isWorkFlow)){
										/*-------------------- Billing Trigger for SaaS Only -------------*/
										/*Use  this method processOnlyNoNM6Product() if you want to insert workflow with auto approval and also publish with billing trigger*/
										//succesStatusBT=processOnlyNoNM6Product(orderNo,xmlfileid,"2",conn,jmsMessageID,"2","8");
										
										/*This method validate some data and attach workflow and do auto approval */
										succesStatusBT=ProcessXMLforCLEP.processOnlyNoNM6ProductForChange(orderNo,xmlfileid,"2",conn,jmsMessageID,"2","8");
										
										
										if(succesStatusBT==1){
											String responseData="",sendAttachedBillableResonse="";											
											if(clepXmlDto.getXmlData()!=null){
																																		
												responseData=clepXmlDto.getXmlData();	
												sendAttachedBillableResonse=attachBillableAccountToResponse(orderNo,xmlfileid,responseData,jmsMessageID,conn);
																							
												if(!"FAILED".equalsIgnoreCase(sendAttachedBillableResonse)){
													/*Update XML response for ib2bMessage. This tag <Ib2bMessage> contain "Order is waiting for RFBT" */
													if(sendAttachedBillableResonse.contains("<Ib2bMessage>")){
														sendAttachedBillableResonse = sendAttachedBillableResonse.replace("<Ib2bMessage>", "<Ib2bMessage>Order is waiting for RFBT");
													}
													clepXmlDto.setStage(AppConstants.CLEP_SUCCESS);
													CLEPUtility.SysErr("-------- Sending Response to MPP >>>>>>>>>>>");
													clepXmlDto.setXmlData(sendAttachedBillableResonse);
													int sendStatus=ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"N","N",conn);
												}																																	
											}										
										}
									}else if("True".equalsIgnoreCase(isWorkFlow)){
										/*-------------------- Billing Trigger for SaaS ERP --------------*/										
										CLEPUtility.SysErr("************* SaaS ERP Change Order *************");
										
										/*Vijay
										 *  add an other parameter(workflow id), 
										  * the purpose is to insert workflow dynamically on the behalf of workflow id receiving from XML file 
										  * */
										workFlowId = clepXmlDto.getWorkFlowId();
										int isValidateSuccess=validateSaaSErPChangeOrder(orderNo,xmlfileid,jmsMessageID,"2","8",conn, workFlowId);
										if(isValidateSuccess==1){
											CLEPUtility.SysErr("---------- Waiting for Aproval against orderNo:-"+orderNo+" >>>>>>>>>");
											/* 
											 *Vijay
											 *Start here to send the msg to AIE 
											 */
											
											/*clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>SO</OrderType><OrderExtAttributes><OrderId>"+uniqOrderId+"</OrderId><OrderNo>"+orderNo+"</OrderNo><FileId>"+xmlfileid+"</FileId><MessageId>"+jmsMessageID+"</MessageId><Status>SUCCESS</Status><CancelDate></CancelDate><Reason_Remarks></Reason_Remarks><Stage>AM</Stage><ApprovalRemarks></ApprovalRemarks></OrderExtAttributes></ChangeOrder></orderHeader>");
											clepXmlDto.setJmsMessageID(jmsMessageID);
											clepXmlDto.setStage("SUCCESS");
											ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"N","N",conn);*/
											
											
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
													clepXmlDto.setStage(AppConstants.CLEP_SUCCESS);	//here success means order inserted succesfully
													objDao.sendClepResponseToMpp(clepXmlDto,objServiceTypeOrdSrcDto.getClepFileId(),objServiceTypeOrdSrcDto.getOrderno(),"N");							
												}	
											} 
											//Vijay end
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
						clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>SOLUTIONCHANGE PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Reason>Failed Due to "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><SubNodeLineItemDetails><ErrorMsg></ErrorMsg></SubNodeLineItemDetails></ChangeOrder></orderHeader>");
						ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);
						successStatus=-1;									
			 		}
			 	}
			}
		}catch(IOESException e){
			//insertFlag=0;
			try{
				conn.rollback();
				CLEPUtility.LOG(true, false, e, "from method validateAndProcessChangeOrder() for IOESException error");
			}catch(Exception ex){
				CLEPUtility.LOG(true, false, ex, "from method validateAndProcessChangeOrder() while rollbacking the connection");
			}
			if("NEW".equalsIgnoreCase(orderType)){
				clepXmlDto.setXmlData("<orderHeader><NewOrder><OrderType>NEW PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML,"+missAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg></ErrorMsg></Charges></NewOrder></orderHeader>");
			}else{
				clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>CHANGE ORDER PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML, "+missAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><SubNodeLineItemDetails><ErrorMsg></ErrorMsg></SubNodeLineItemDetails></ChangeOrder></orderHeader>");
			}
			clepXmlDto.setJmsMessageID(jmsMessageID);
			ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);
		}
		catch(Exception e){
						
			CLEPUtility.LOG(true, false, e, "from method validateAndProcessChangeOrder() for other error");
			try{
				conn.rollback();
			}catch(Exception ex){
				CLEPUtility.LOG(true, false, ex, "from method validateAndProcessChangeOrder() while rollbacking the connection");
			}
			String missingAttribute="[Internal error]{}";
			clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>CHANGE ORDER PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsMessageID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><SubNodeLineItemDetails><ErrorMsg></ErrorMsg></SubNodeLineItemDetails></ChangeOrder></orderHeader>");
			clepXmlDto.setJmsMessageID(jmsMessageID);
			ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);						
		}
		return successStatus;
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
	 * Function:processOnlyNoNM6Product
	 * this method processing and creating order for non provision products
	 * */
	public static int processOnlyNoNM6Product(int orderNo,long xmlfileid,String projectMngrId,Connection conn,String jmsID,String changeType,String subChangeTypeId)throws Exception{
		CallableStatement csWorkflow = null;
		CallableStatement csWorkflowClose=null;		
		CallableStatement csGetLOCBillTrgDate=null;
		ResultSet rsWorkflow=null;		
		String taskWorkflowRoleName="";
		int taskId=0;
		String taskOwnerId=null;
		String retString="";
		String poDetailNo = "";
		String serviceId = "";
		int successStatus=-1;
		String validatePO="";
		int insertWorkflow=-1;
		CLEPXmlDto clepDto=new CLEPXmlDto();
		NewOrderDaoExt objNewExtDao=new NewOrderDaoExt();
		OrderHeaderDTO objNewDto=new OrderHeaderDTO();
		ArrayList<OrderHeaderDTO> objNewALDto=null;		
		ViewOrderDao objViewDao=new ViewOrderDao();
		try{
			//==============================VALIDATION=====================================
			long orderNumber=orderNo;
			CLEPUtility.SysErr("--------- Fetching ProjectManager and UserId  >>>>>>>>>>>");
			clepDto=ParseXMLForChangeOrder.getProjectManagerIdAndUserID(orderNo, conn);
			CLEPUtility.SysErr("--------- Validating start 1  >>>>>>>>>>>>>>");										
			objNewDto.setPoNumber(orderNo);
			objNewDto.setChangeType(changeType);
			objNewDto.setSubChangeTypeId(Integer.parseInt(subChangeTypeId));
			conn.commit();
			if(objNewDto.getChangeType().equals("3") || (objNewDto.getChangeType().equals("4") && (objNewDto.getSubChangeTypeId() == 13 || objNewDto.getSubChangeTypeId() == 14  || objNewDto.getSubChangeTypeId() == 15 || objNewDto.getSubChangeTypeId() == 16))) {
				validatePO = objNewExtDao.ValidateDisconnectionPO(objNewDto,conn);
			}else if(objNewDto.getChangeType().equals("2") || objNewDto.getChangeType().equals("5") || (objNewDto.getChangeType().equals("4") && (objNewDto.getSubChangeTypeId() == 12 || objNewDto.getSubChangeTypeId() == 17 || objNewDto.getSubChangeTypeId() == 18  || objNewDto.getSubChangeTypeId() == 19))) {
				validatePO = objNewExtDao.validateChargesPoLevelForChangeOrder(objNewDto,conn);  
			} else if(objNewDto.getChangeType().equals("1")) {
				validatePO = ""; 
			}	
			if("".equalsIgnoreCase(validatePO)){
				CLEPUtility.SysErr("------- Validating start 2  >>>>>>>>>>>>>>");						
				objNewALDto=objNewExtDao.getChangeOrderSubTypeAttached(String.valueOf(orderNo),conn);
				CLEPUtility.SysErr("------- Validating start 3  >>>>>>>>>>>>>>");
				objNewDto.setSubChangeTypeId(objNewALDto.get(0).getSubChangeTypeId());
				objNewDto.setSubChangeTypeName(objNewALDto.get(0).getSubChangeTypeName());
				objNewDto.setIb2bWorkflowAttachedId(objNewALDto.get(0).getIb2bWorkflowAttachedId());
				objNewDto.setIb2bWorkflowAttachedName(objNewALDto.get(0).getIb2bWorkflowAttachedName());
				objNewDto.setProjectWorkflowId(objNewALDto.get(0).getIb2bWorkflowAttachedId());
				CLEPUtility.SysErr("------- Validating start 4  >>>>>>>>>>>>>>");
				insertWorkflow=objNewExtDao.attachworkflowForChangeOrder(objNewDto,conn);
				CLEPUtility.SysErr("------- Validating start 5  >>>>>>>>>>>>>>");
			
			if(insertWorkflow==1){	
			    int errWorkflowClose=-1;
			    //==========================WORKFLOW START=================================			
				CLEPUtility.SysErr("------- Po Validated Successfully --------");
				CLEPUtility.SysErr("------- Workflow Start >>>>>>>>");
				csWorkflow = conn.prepareCall(sqlGet_TaskListOfOrder);
				csWorkflow.setLong(1,orderNo);//order no
				rsWorkflow=csWorkflow.executeQuery();
				int totalTaskId=0;
				while (rsWorkflow.next()) {
					
					//***********************************************************************************
					//								Total List of Owner of Workflow Task
					taskId=rsWorkflow.getInt("TASKID"); 		
					taskOwnerId=rsWorkflow.getString("OWNERTYPE_ID");	
					taskWorkflowRoleName=rsWorkflow.getString("ROLENAME");	
					//***********************************************************************************
					//***********************************************************************************	
					//								Workflow Closing
					csWorkflowClose= conn.prepareCall(sqlSpInsertTaskActionForChangeOrder);
					//commented by kalpana as added another procedure with one parameter less,removed @rejectionSendTo bug ID HYPR09042013001
					/*csWorkflowClose.setLong(1, Long.valueOf(taskId));
					csWorkflowClose.setLong(2, Long.valueOf(1));
					csWorkflowClose.setLong(3, Long.valueOf(1));
					csWorkflowClose.setString(4, "Create by CLEP");
					csWorkflowClose.setLong(5, 0);
					csWorkflowClose.setLong(6, 0);
					csWorkflowClose.setString(7, "");
					csWorkflowClose.setLong(8, clepDto.getProjectManagerId());
					csWorkflowClose.setString(9, String.valueOf(clepDto.getProjectManagerId()));
					csWorkflowClose.setInt(10, 0);
					csWorkflowClose.execute();
						errWorkflowClose = csWorkflowClose.getInt(6);*/
					//end
					//end
					//added by kalpana as added another procedure with one parameter less,removed @rejectionSendTo and changed index bug ID HYPR09042013001
						csWorkflowClose.setLong(1, Long.valueOf(taskId));
						csWorkflowClose.setLong(2, Long.valueOf(1));
						csWorkflowClose.setString(3, "Create by CLEP");
						csWorkflowClose.setLong(4, 0);
						csWorkflowClose.setLong(5, 0);
						csWorkflowClose.setString(6, "");
						csWorkflowClose.setLong(7, clepDto.getProjectManagerId());
						csWorkflowClose.setString(8, String.valueOf(clepDto.getProjectManagerId()));
						csWorkflowClose.setInt(9, 0);
						csWorkflowClose.setLong(10,0);
						csWorkflowClose.setLong(11,0);
						csWorkflowClose.execute();
							errWorkflowClose = csWorkflowClose.getInt(5);
					//end		
					if(errWorkflowClose==0){
						CLEPUtility.SysErr("------Workflow Closed for Role: "+taskWorkflowRoleName+" -------");
					}else{
						CLEPUtility.SysErr("Err:Workflow could not Closed for Role: "+taskWorkflowRoleName);
					}
					//**************************************************************************************
				}											
				if(errWorkflowClose==0){
					CLEPUtility.SysErr("-------- Action taken Successfully and workflow closed ---------");														
				}else{
					CLEPUtility.SysErr("-------- Error: occured during workflow!! ---------");	
					String msg="Error occured during workflow!!";
					String str="<orderHeader><ChangeOrder><OrderType>CHANGEORDER PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg>"+msg+"</ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails></ChangeOrder></orderHeader>";						
					conn.rollback();
				}				
				//############################################################################################
				//===================================PUBLISHING START============================
				if(errWorkflowClose==0){
					CLEPUtility.SysErr("------- publish is start against orderNo:"+orderNo+" >>>>>>>>>");
					int resCode=-1;														
					objNewDto.setOrder_type("CHANGE");
					objNewDto.setOrderType("CHANGE");
					objNewDto.setOrderType("CHANGE");
					objNewDto.setOrderInfo_OrderType("CHANGE");
					objNewDto.setOrderInfo_ChangeType(Integer.parseInt(changeType));
					objNewDto.setPublished_by_empid(1);
					objNewDto.setPublished_by_roleid(4);
					int publishStatus=objViewDao.getPublishResult(orderNo, "CHANGE", objNewDto,conn,"","","","");	//last 2 parameter added by saurabh to handle partial publish						
					if(publishStatus==0){
						 CLEPUtility.SysErr("-------- Publish is done successfully against orderNo:-"+orderNo+" ----------");	
						 CLEPUtility.SysErr("-------- Billing Trigger is start against orderNo:-"+orderNo+" >>>>>>>>>");
						 //############################################################################################
						 // 									BILLING TRIGGER START
						 	ArrayList<ViewOrderDto> alSelectServiceDetails =new ArrayList<ViewOrderDto>();
							ArrayList<ViewOrderDto> requireLineItemForBT =new ArrayList<ViewOrderDto>();
							BillingTriggerValidation billingTriggerValidation=new BillingTriggerValidation();
							ViewOrderDto eachLineItemDto=new ViewOrderDto();
							ViewOrderDto requireLineItemDto=new ViewOrderDto();
							ViewOrderDto validateInfo=new ViewOrderDto();
							ViewOrderDto viewOrderDto=new ViewOrderDto();
							
							String statusForRequireLineItemForBT="";
							String strBillingTrigger="";
							String ServiceProductId="";
							String LOCNo="",LOCdate="",BillingTriggerDate="",LocReceiveDate="";	
							
							viewOrderDto.setPonum(orderNo);
							alSelectServiceDetails=objViewDao.getSELECT_BT_PRODUCTS_FOR_CHANGE_ORDERS(viewOrderDto, conn);
							objNewDto=objViewDao.getOrderType(conn, orderNo);
							
							if(alSelectServiceDetails.size()>0){
								for(int i=0;i<alSelectServiceDetails.size();i++){											
									eachLineItemDto=alSelectServiceDetails.get(i);
									
									if(alSelectServiceDetails.get(i).getLineNumber()!=null){
										billingTriggerValidation.resetStateToNew();
										billingTriggerValidation.setSourceData(eachLineItemDto, objNewDto, null);
										billingTriggerValidation.computeProperties();
										statusForRequireLineItemForBT=billingTriggerValidation.getBillingTriggerActionStatus();
										if("required".equalsIgnoreCase(statusForRequireLineItemForBT)){
											requireLineItemForBT.add(eachLineItemDto);
											CLEPUtility.SysErr("-------- Billing Trigger Require for LineItem:="+alSelectServiceDetails.get(i).getLineNumber()+" --------");
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
											csGetLOCBillTrgDate.setInt(4, 0);//Billing Trigger will be automated
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
											CLEPUtility.SysErr("LineItem = "+ServiceProductId+" LOCNO = "+LOCNo+" LOCdate = "+LOCdate+" BillingTriggerDate = "+BillingTriggerDate+" LocReceiveDate = "+LocReceiveDate);
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
									CLEPUtility.SysErr("-------- Billing Trigger String is sending for validate:-"+strBillingTrigger+" >>>>>>>>>");
									AjaxValidation ajaxValidation=new AjaxValidation();
									validateInfo=ajaxValidation.validateActiveDate(requireLineItemDto,conn,"Y",null,0);
									String pushChargesFXStatus="";
									int pushFlagError=-1,insideFxPushCharge=-1;
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
												csGetLOCBillTrgDate.setInt(4, 0);//Billing Trigger will be automated
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
												newDtoFXChargeStatus=objViewDao.pushChargesInSecondaryTablesForChangeOrders(conn, longServiceProductId, LOCNo, LOCdate, BillingTriggerDate, LocReceiveDate, orderNo, "Y",-1);//Start:[001]
												insideFxPushCharge=1;
											}
											if(!"SUCCESS".equalsIgnoreCase(newDtoFXChargeStatus.getBillingTriggerStatus()) && insideFxPushCharge==1){
												pushFlagError=1;
												CLEPUtility.SysErr("-------- ChargesPush FAILURE for LineItem:="+String.valueOf(longServiceProductId)+" --------");
												CLEPUtility.SysErr("-------- Failed due to "+newDtoFXChargeStatus.getBillingTriggerMsg()+" -------");
											}else{
												CLEPUtility.SysErr("-------- ChargesPush SUCCESS for LineItem:="+String.valueOf(longServiceProductId)+" ---------");
											}
										}	
										if(pushFlagError==1){
																																												
												String msg="Billing Trigger is FAILED against orderNo:-"+orderNo+" due to "+newDtoFXChargeStatus.getBillingTriggerMsg();
												String str="<orderHeader><ChangeOrder><OrderType>CHANGEORDER PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid Information</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg>"+msg+"</ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails></ChangeOrder></orderHeader>";															
												successStatus=0;
												conn.rollback();
											
										}else{
											CLEPUtility.SysErr("--------- Billing Trigger is SUCCESS against orderNo:-"+orderNo+" ----------");																																																				
											CLEPUtility.SysErr("-------- Billing Trigger is END staus update against orderNo:-"+orderNo+" >>>>>>>");														
											//objViewDao.setBTEndIfPossible(orderNumber,conn);
											conn.commit();
											successStatus=1;
										}
									}else{
										//On Billing Trigger Validation Failure																										
										CLEPUtility.SysErr("-------- Billing Trigger is FAILED against orderNo:-"+orderNo+" due to "+validateInfo.getBillingTriggerValidationErrors()+" --------");																			
										String msg="Billing Trigger is FAILED against orderNo:-"+orderNo+" due to "+validateInfo.getBillingTriggerValidationErrors();
										String str="<orderHeader><ChangeOrder><OrderType>CHANGEORDER PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg>"+msg+"</ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails></ChangeOrder></orderHeader>";														
										successStatus=0;
										conn.rollback();													
									}
									
								}else{												
																					
									CLEPUtility.SysErr("-------- Error: No Billing Trigger required against orderNo:-"+orderNo+" ---------");																		
									String msg="No Billing Trigger required against orderNo "+orderNo;
									String str="<orderHeader><ChangeOrder><OrderType>CHANGEORDER PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsID+"</MessageId><Reason>Failed Due to Invalid XML</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg>"+msg+"</ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails></ChangeOrder></orderHeader>";													
									successStatus=0;
									conn.rollback();
								}
							}else{											
								CLEPUtility.SysErr("------- Error: No any LineItem available against orderNo:-"+orderNo+" -------");																		
								String msg="Billing Trigger is Failed against orderNo:-"+orderNo;
								String str="<orderHeader><ChangeOrder><OrderType>CHANGEORDER PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg>"+msg+"</ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails></ChangeOrder></orderHeader>";												
								successStatus=0;
								conn.rollback();											
							}									 
					 }else{
						CLEPUtility.SysErr("--------- Error: publishing FAILED --------");
					 	String msg="publishing failed";
					 	String str="<orderHeader><ChangeOrder><OrderType>CHANGEORDER PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg>"+msg+"</ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails></ChangeOrder></orderHeader>";										
						successStatus=0;
						conn.rollback();
					 }
				}																			
			}else{
				String str="<orderHeader><ChangeOrder><OrderType>CHANGEORDER PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid information</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg>Workflow Insertion failed!!</ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails></ChangeOrder></orderHeader>";
				CLEPXmlDto xmldto=new CLEPXmlDto();
				xmldto.setXmlData(str);
				xmldto.setJmsMessageID(jmsID);
				ParseXMLforCLEP.sendXMLtoMPP(xmldto,xmlfileid,"Y","N",conn);
				successStatus=0;
				conn.rollback();
			}
		}else{
			String str="<orderHeader><ChangeOrder><OrderType>CHANGEORDER PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid information</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg>"+validatePO+"</ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails></ChangeOrder></orderHeader>";
			CLEPXmlDto xmldto=new CLEPXmlDto();
			xmldto.setXmlData(str);
			xmldto.setJmsMessageID(jmsID);
			ParseXMLforCLEP.sendXMLtoMPP(xmldto,xmlfileid,"Y","N",conn);
			successStatus=0;
			conn.rollback();
		}
	}catch(Exception e){
			CLEPUtility.LOG(true, false, e, "from method processOnlyNoNM6Product() for other error");
			String missingAttribute="[Internal error]{}";
			CLEPXmlDto clepXmlDto=new CLEPXmlDto();
			clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>CHANGE ORDER PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><SubNodeLineItemDetails><ErrorMsg></ErrorMsg></SubNodeLineItemDetails></ChangeOrder></orderHeader>");
			clepXmlDto.setJmsMessageID(jmsID);
			ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);
			try{
				conn.rollback();
			}catch(Exception ex){
				CLEPUtility.LOG(true, false, ex, "from method processOnlyNoNM6Product() while rollbacking the connection");
			}
	   }
		return successStatus;
	}
	
	/*Method:getProjectManagerIdAndUserID
	 * Purpose:To find project manager associate with account request for order for auto aprove
	 * */
	public static CLEPXmlDto getProjectManagerIdAndUserID(int orderNo,Connection conn){
		CallableStatement csIOMS=null;	
		ResultSet res=null;
		CLEPXmlDto clepxmldto=new CLEPXmlDto();
		int isfound=-1;
		try{
			csIOMS=conn.prepareCall("SELECT PROJECTMGRID FROM IOE.TM_ACCOUNT WHERE ACCOUNTID in(SELECT ACCOUNTID FROM IOE.TPOMASTER WHERE ORDERNO=?)");
			csIOMS.setInt(1,orderNo);			
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
			CLEPUtility.LOG(true, false, ex, "from method getProjectManagerIdAndUserID() for other error");
		}
		return clepxmldto;
	}

	/*Method:attachBillableAccountToResponse
	 * Purpose:Attaching Billable Account and send to mpp(seventeen parameter after successfully billing trigger)
	 * */
	public static String attachBillableAccountToResponse(long orderNo,long xmlfileid,String resonseMsg,String jmsID,Connection conn){
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
					resonseMsg=CLEPUtility.replaceWord(resonseMsg, searchWord, "");
					isBillabelAccountCreated=false;
				}
			}
			if(isBillabelAccountCreated){
				attachedBillAccount=resonseMsg;
			}else{
				attachedBillAccount=resonseMsg;//if BillableAccount not found then replace with blank
			}
			
		}catch(Exception e){						
			CLEPUtility.LOG(true, false, e, "from method attachBillableAccountToResponse() for other error");
			String missingAttribute="[Internal error]{in iB2B}";				
			CLEPXmlDto clepXmlDto=new CLEPXmlDto();
			clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>CHANGE ORDER PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><SubNodeLineItemDetails><ErrorMsg></ErrorMsg></SubNodeLineItemDetails></ChangeOrder></orderHeader>");
			clepXmlDto.setJmsMessageID(jmsID);
			ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);
		}
		return attachedBillAccount;
	}
	
	/*
	 * Method:getServiceTypeID
	 * Purpose:To get the Service type id on the behalf of logical si no
	 * */
	public static String getServiceTypeID(long logicalLSiNo,Connection conn){
		CallableStatement csIOMS=null;	
		ResultSet res=null;
		String serviceTypeID="";
	
		try{
			csIOMS=conn.prepareCall("SELECT SERVICETYPEID FROM IOE.TPOSERVICEMASTER WHERE LOGICAL_SI_NO=? FETCH FIRST ROW ONLY");
			csIOMS.setLong(1,logicalLSiNo);			
			res=csIOMS.executeQuery();
			
			while(res.next()){
				serviceTypeID=res.getString("SERVICETYPEID");				
			}			
		}catch(Exception ex){
			CLEPUtility.LOG(true, false, ex, "from method getServiceTypeID() for other error");
		}
		return serviceTypeID;
	}
	
	/*
	 * Method:validateSaaSErPChangeOrder
	 *Purpose:Validate the 'saas' product Add insert workflow dynamically on the behalf of workflow id  
	 * */
	public static int validateSaaSErPChangeOrder(int orderNo,long xmlfileid,String jmsID,String changeType,String subChangeTypeId,Connection conn, String workFlowId){
		int successFlag=-1,isInsertedDataintoTemp=-1,isDataTypeValidateSuccess=-1;
		CallableStatement csWorkflow = null;
		CallableStatement csValidate = null;
		ResultSet rsWorkflow=null;
		ResultSet rsIOMS=null;
		int taskId=0;
		String taskOwnerId=null;
		String retString="";
		CLEPXmlDto clepDtoPrjMngr=new CLEPXmlDto();
		OrderHeaderDTO objNewDto=new OrderHeaderDTO();
		NewOrderDaoExt objNewExtDao=new NewOrderDaoExt();
		ArrayList<OrderHeaderDTO> objNewALDto=null;
		String validatePO="";
		int insertWorkflow=-1;
		try{
			CLEPUtility.SysErr("-------- Validating start for Message Req:="+jmsID+" >>>>>>>>>");		
			CLEPUtility.SysErr("-------- Validating start 1  >>>>>>>>>>>>>>");										
			objNewDto.setPoNumber(orderNo);
			objNewDto.setChangeType(changeType);
			objNewDto.setSubChangeTypeId(Integer.parseInt(subChangeTypeId));
			
			if(objNewDto.getChangeType().equals("3") || (objNewDto.getChangeType().equals("4") && (objNewDto.getSubChangeTypeId() == 13 || objNewDto.getSubChangeTypeId() == 14  || objNewDto.getSubChangeTypeId() == 15 || objNewDto.getSubChangeTypeId() == 16))) {
				validatePO = objNewExtDao.ValidateDisconnectionPO(objNewDto,conn);
			}else if(objNewDto.getChangeType().equals("2") || objNewDto.getChangeType().equals("5") || (objNewDto.getChangeType().equals("4") && (objNewDto.getSubChangeTypeId() == 12 || objNewDto.getSubChangeTypeId() == 17 || objNewDto.getSubChangeTypeId() == 18  || objNewDto.getSubChangeTypeId() == 19))) {
				validatePO = objNewExtDao.validateChargesPoLevelForChangeOrder(objNewDto,conn);  
			} else if(objNewDto.getChangeType().equals("1")) {
				validatePO = ""; 
			}	
			if("".equalsIgnoreCase(validatePO)){
				//CLEPUtility.SysErr("-------- Validating start 2  >>>>>>>>>>>>>>");
				/*Vijay 
				 * Following codes are commenting because now the project workflow is going to dynamic,
				 * and inserted on the behalf of workflow id that is receiving form XML file
				 *  */
				
				//objNewALDto=objNewExtDao.getChangeOrderSubTypeAttached(String.valueOf(orderNo),conn);
				//CLEPUtility.SysErr("-------- Validating start 3  >>>>>>>>>>>>>>");
				//objNewDto.setSubChangeTypeId(objNewALDto.get(0).getSubChangeTypeId());
				//objNewDto.setSubChangeTypeName(objNewALDto.get(0).getSubChangeTypeName());
				//objNewDto.setIb2bWorkflowAttachedId(objNewALDto.get(0).getIb2bWorkflowAttachedId());
				//objNewDto.setIb2bWorkflowAttachedName(objNewALDto.get(0).getIb2bWorkflowAttachedName());
				//objNewDto.setProjectWorkflowId(objNewALDto.get(0).getIb2bWorkflowAttachedId());
				//CLEPUtility.SysErr("-------- Validating start 4  >>>>>>>>>>>>>>");
				
				/*--Vijay. Here set the workFlowId for inserting workflow on the behalf of received workflow id---*/
				CLEPUtility.SysErr("-------- Going to attache dynamic workflow for this workflow id "+workFlowId);
				
				objNewDto.setProjectWorkflowId(workFlowId);
				insertWorkflow=objNewExtDao.attachworkflowForChangeOrder(objNewDto,conn);
				
				//CLEPUtility.SysErr("-------- Validating start 5  >>>>>>>>>>>>>>");
				if(insertWorkflow==1){	
					CLEPUtility.SysErr("-------Dynamic workflow has been inserted --------");
					CLEPUtility.SysErr("------- Po Validated Successfully --------");
					conn.commit();
					successFlag=1;
				}				
			}else{
				CLEPUtility.SysErr("------- Po Validation Failed due to "+validatePO+" --------");
			}
		}catch(Exception e){						
			CLEPUtility.LOG(true, false, e, "from method validateSaaSErPChangeOrder() for other error");
			String missingAttribute="[Internal error]{in iB2B}";				
			CLEPXmlDto clepXmlDto=new CLEPXmlDto();
			clepXmlDto.setXmlData("<orderHeader><NewOrder><OrderType>NEW PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML,missing Attribute "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg></ErrorMsg></Charges></NewOrder></orderHeader>");			
			clepXmlDto.setJmsMessageID(jmsID);
			ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);
		}		
		return successFlag;
	}
	
	/*
	 * Method:processOnlyNoNM6ChangeOrderAfterAproval
	 *Purpose:This method is using for billing trigger process. This method call another method for billing trigger 
	 * 
	 * */
	public static int processOnlyNoNM6ChangeOrderAfterAproval(int orderNo,long xmlfileid,String projectMngrId,Connection conn,String changeType,String subChangeTypeId,CLEPXmlDto clepXmlDto)throws Exception{
		
		CallableStatement csGetLOCBillTrgDate=null;		
		int successStatus=-1;						
		CallableStatement csGetServiceProductId = null;
		ResultSet rsServiceProductIds=null;
		String jmsID="";
		try{
									
		jmsID=clepXmlDto.getJmsMessageID();
								 									 
									 //############################################################################################
									//									RFBT DATA SAVING START									 											
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
														
											longServiceProductId=Long.valueOf(requireLineItemForBT.get(i).getLineNumber().trim());	
														
												if(longServiceProductId !=0){
													csGetLOCBillTrgDate= conn.prepareCall(spGetLocNoLocDateBillinTrgDate);	
													csGetLOCBillTrgDate.setLong(1, xmlfileid);
													csGetLOCBillTrgDate.setLong(2, Long.valueOf(longServiceProductId));
													csGetLOCBillTrgDate.setLong(3, orderNo);
													csGetLOCBillTrgDate.setInt(4, 1);//Billing Trigger will be manually from GUI
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
											CLEPUtility.SysErr("-------- Data Saving Failed for OrderNo["+orderNo+"] --------");
										}
										//==============================RFBT DATA SAVING END=================================
										//###############################[ START: AUTO PUBLISH AND BILLING TRRIGER ]#############################################################
										if(successStatus==1){
											CLEPUtility.SysErr("--------- Processing [Auto Publishing and BillingTrigger] start for Order No:[ " +orderNo+" ] >>>>>>>>");							
											ViewOrderDto objServiceTypeOrdSrcDto=new ViewOrderDto();
											NewOrderDao objNewDao = new NewOrderDao();
											int isSendToMPP=processAutoPUBLISH_BT_AfterRFBTChangeOrder(orderNo,xmlfileid,conn,jmsID,changeType);
											CLEPUtility.SysErr("--------send to MPP value is "+ isSendToMPP);
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
																	objNewDao.sendClepResponseToMpp(clepXmlDto,xmlfileid,orderNo,"BT");							
																}else{
																	CLEPUtility.SysErr("--------Error: Finding Response for sending to MPP [ FAILED ] >>>>>>>>>");
																}
															}
													}
						
												  //-----------------------------[ End: Sending Response to MPP ]------------------------------------------------																			
											 }else{		
												 successStatus=0;
												 CLEPUtility.SysErr("--------Error: Response Sending Failed !---------");
											 }
										}
										//###############################[ END: AUTO PUBLISH AND BILLING TRRIGER ]#############################################################
														 													 																					
		}catch(Exception e){
			CLEPUtility.LOG(true, false, e, "from method processOnlyNoNM6ChangeOrderAfterAproval() for other error");
			String missingAttribute="[Internal error]{}";
			CLEPXmlDto clepDto=new CLEPXmlDto();
			clepDto.setXmlData("<orderHeader><ChangeOrder><OrderType>CHANGE ORDER PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><SubNodeLineItemDetails><ErrorMsg></ErrorMsg></SubNodeLineItemDetails></ChangeOrder></orderHeader>");
			clepDto.setJmsMessageID(jmsID);
			ParseXMLforCLEP.sendXMLtoMPP(clepDto,xmlfileid,"Y","N",conn);
			try{
				conn.rollback();
			}catch(Exception ex){
				CLEPUtility.LOG(true, false, ex, "from method processOnlyNoNM6ChangeOrderAfterAproval() while rollbacking the connection");
			}
		}
		return successStatus;
	}
	
	/*
	 * Method:processAutoPUBLISH_BT_AfterRFBTChangeOrder
	 * Purpose:This method is responsible for publish the order and billing trigger  
	 * 
	 * */
	public static int processAutoPUBLISH_BT_AfterRFBTChangeOrder(int orderNo,long xmlfileid,Connection conn,String jmsID,String changeType){
		int successStatus=-1;		
		CallableStatement csGetLOCBillTrgDate=null;				
		OrderHeaderDTO objNewDto=new OrderHeaderDTO();			
		ViewOrderDao objViewDao=new ViewOrderDao();
		
		try{
			//###################################[ PUBLISH START ]#########################################################
			CLEPUtility.SysErr("------- publish is start against orderNo:"+orderNo+" >>>>>>>>>");														
			objNewDto.setOrder_type("CHANGE");
			objNewDto.setOrderType("CHANGE");
			objNewDto.setOrderType("CHANGE");
			objNewDto.setOrderInfo_OrderType("CHANGE");		
			objNewDto.setOrderInfo_ChangeType(Integer.parseInt(changeType));
			objNewDto.setPublished_by_empid(1);
			objNewDto.setPublished_by_roleid(4);
			int publishStatus=objViewDao.getPublishResult(orderNo, "CHANGE", objNewDto,conn,"","","","");//last 2 parameter added by saurabh to handle partial publish					
				 if(publishStatus==0){
					 CLEPUtility.SysErr("-------- Publish is done successfully against orderNo:-"+orderNo+" ----------");	
					 CLEPUtility.SysErr("-------- Billing Trigger is start against orderNo:-"+orderNo+" >>>>>>>>>");
					 //####################################[ BILLING TRIGGER START ]########################################################												
					    long orderNumber=orderNo;
					 	ArrayList<ViewOrderDto> alSelectServiceDetails =new ArrayList<ViewOrderDto>();
						ArrayList<ViewOrderDto> requireLineItemForBT =new ArrayList<ViewOrderDto>();
						BillingTriggerValidation billingTriggerValidation=new BillingTriggerValidation();
						ViewOrderDto eachLineItemDto=new ViewOrderDto();
						ViewOrderDto requireLineItemDto=new ViewOrderDto();
						ViewOrderDto validateInfo=new ViewOrderDto();
						ViewOrderDto viewOrderDto=new ViewOrderDto();
						
						String statusForRequireLineItemForBT="";
						String strBillingTrigger="";
						String ServiceProductId="";
						String LOCNo="",LOCdate="",BillingTriggerDate="",LocReceiveDate="";	
						
						viewOrderDto.setPonum(orderNo);
						alSelectServiceDetails=objViewDao.getSELECT_BT_PRODUCTS_FOR_CHANGE_ORDERS(viewOrderDto, conn);
						objNewDto=objViewDao.getOrderType(conn, orderNo);
						
						if(alSelectServiceDetails.size()>0){
							for(int i=0;i<alSelectServiceDetails.size();i++){											
								eachLineItemDto=alSelectServiceDetails.get(i);
								if(alSelectServiceDetails.get(i).getLineNumber()!=null){
									billingTriggerValidation.resetStateToNew();
									billingTriggerValidation.setSourceData(eachLineItemDto, objNewDto, null);
									billingTriggerValidation.computeProperties();
									statusForRequireLineItemForBT=billingTriggerValidation.getBillingTriggerActionStatus();
									if("required".equalsIgnoreCase(statusForRequireLineItemForBT)){
										requireLineItemForBT.add(eachLineItemDto);
										CLEPUtility.SysErr("-------- Billing Trigger Require for LineItem:="+alSelectServiceDetails.get(i).getLineNumber()+" --------");
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
										csGetLOCBillTrgDate.setInt(4, 0);//Billing Trigger will be automated
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
										CLEPUtility.SysErr("LineItem = "+ServiceProductId+" LOCNO = "+LOCNo+" LOCdate = "+LOCdate+" BillingTriggerDate = "+BillingTriggerDate+" LocReceiveDate = "+LocReceiveDate);
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
								CLEPUtility.SysErr("-------- Billing Trigger String is sending for validate:-"+strBillingTrigger+" >>>>>>>>>");
								AjaxValidation ajaxValidation=new AjaxValidation();
								validateInfo=ajaxValidation.validateActiveDate(requireLineItemDto,conn,"Y",null,0);
								String pushChargesFXStatus="";
								int pushFlagError=-1,insideFxPushCharge=-1;
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
											csGetLOCBillTrgDate.setInt(4, 0);//Billing Trigger will be automated
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
											newDtoFXChargeStatus=objViewDao.pushChargesInSecondaryTablesForChangeOrders(conn, longServiceProductId, LOCNo, LOCdate, BillingTriggerDate, LocReceiveDate, orderNo,"Y",-1);//Start:[001]
											insideFxPushCharge=1;
										}
										if(!"SUCCESS".equalsIgnoreCase(newDtoFXChargeStatus.getBillingTriggerStatus()) && insideFxPushCharge==1){
											pushFlagError=1;
											CLEPUtility.SysErr("-------- ChargesPush FAILURE for LineItem:="+String.valueOf(longServiceProductId)+" --------");
											CLEPUtility.SysErr("-------- Failed due to "+newDtoFXChargeStatus.getBillingTriggerMsg()+" -------");
											conn.rollback();
										}else{
											CLEPUtility.SysErr("-------- ChargesPush SUCCESS for LineItem:="+String.valueOf(longServiceProductId)+" ---------");
										}
									}	
									if(pushFlagError==1){
																																											
											String msg="Billing Trigger is FAILED against orderNo:-"+orderNo+" due to "+newDtoFXChargeStatus.getBillingTriggerMsg();
											String str="<orderHeader><ChangeOrder><OrderType>CHANGEORDER PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid Information</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg>"+msg+"</ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails></ChangeOrder></orderHeader>";															
											successStatus=0;
											conn.rollback();
										
									}else{
										CLEPUtility.SysErr("--------- Billing Trigger is SUCCESS against orderNo:-"+orderNo+" ----------");																																																				
										CLEPUtility.SysErr("-------- Billing Trigger is END staus update against orderNo:-"+orderNo+" >>>>>>>");														
										//objViewDao.setBTEndIfPossible(orderNumber,conn);
										conn.commit();
										successStatus=1;
									}
								}else{
									//On Billing Trigger Validation Failure																										
										CLEPUtility.SysErr("-------- Billing Trigger is FAILED against orderNo:-"+orderNo+" due to "+validateInfo.getBillingTriggerValidationErrors()+" --------");																			
										String msg="Billing Trigger is FAILED against orderNo:-"+orderNo+" due to "+validateInfo.getBillingTriggerValidationErrors();
										String str="<orderHeader><ChangeOrder><OrderType>CHANGEORDER PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg>"+msg+"</ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails></ChangeOrder></orderHeader>";														
										successStatus=0;
										conn.rollback();													
								}
								
							}else{												
																				
									CLEPUtility.SysErr("-------- Error: No Billing Trigger required against orderNo:-"+orderNo+" ---------");																		
									String msg="No Billing Trigger required against orderNo "+orderNo;
									String str="<orderHeader><ChangeOrder><OrderType>CHANGEORDER PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsID+"</MessageId><Reason>Failed Due to Invalid XML</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg>"+msg+"</ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails></ChangeOrder></orderHeader>";													
									successStatus=0;
									conn.rollback();
								
							}
						}else{											
								CLEPUtility.SysErr("------- Error: No any LineItem available against orderNo:-"+orderNo+" -------");																		
								String msg="Billing Trigger is Failed against orderNo:-"+orderNo;
								String str="<orderHeader><ChangeOrder><OrderType>CHANGEORDER PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg>"+msg+"</ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails></ChangeOrder></orderHeader>";												
								successStatus=0;
								conn.rollback();											
						}									 
				 }else{
					 	CLEPUtility.SysErr("--------- Error: publishing FAILED --------");
					 	String msg="publishing failed";
					 	String str="<orderHeader><ChangeOrder><OrderType>CHANGEORDER PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg>"+msg+"</ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails></ChangeOrder></orderHeader>";										
						successStatus=0;
						conn.rollback();
				 }		
		}catch(Exception e){
			CLEPUtility.LOG(true, false, e, "from method processAutoPUBLISH_BT_AfterRFBTChangeOrder() for other error");
			try{
				conn.rollback();
			}catch(Exception ex){
				CLEPUtility.LOG(true, false, ex, "from method processAutoPUBLISH_BT_AfterRFBTChangeOrder() while rollbacking the connection");
			}
			String missingAttribute="[Internal error]{}";
			CLEPXmlDto clepXmlDto=new CLEPXmlDto();
			clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>CHANGE ORDER PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><SubNodeLineItemDetails><ErrorMsg></ErrorMsg></SubNodeLineItemDetails></ChangeOrder></orderHeader>");
			clepXmlDto.setJmsMessageID(jmsID);
			ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);
		}
		return successStatus;
	}
}
