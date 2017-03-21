package com.ibm.ioes.ei.scm.BL;
//Tag Name Resource Name  Date		CSR No			Description
//[001]	 Kalpana	11-January-2014			New File for business logic third party

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ibm.ioes.dao.SCMDao;
import com.ibm.ioes.ei.scm.dto.PRCreatnSCMXmlDto;
import com.ibm.ioes.ei.scm.dto.SCMRecXmlDto;
import com.ibm.ioes.ei.scm.dto.SCMXMLDto;
import com.ibm.ioes.forms.LineItemValueDTO;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.IB2BMail;
import com.ibm.ioes.utilities.IB2BMailDto;
import com.ibm.ioes.utilities.Utility;

public class SCMBL {
	private SCMDao scmDaoObj= new SCMDao();
	
	/**
	 * This method will update nfa number and insert orderNumber,serviceId and ServiceProductId
	 * @param conn,orderNumber,serviceId,eventTypeId, m6OrderId
	 * @return Nothing.
    */
	public int updateNfaAndInsertInScmTable(Connection conn,int orderNumber,int serviceId,String eventTypeId,int m6OrderId){
		Utility.LOG(true,false,"Entering updateNfaAndInsertInScmTable");
		ArrayList<LineItemValueDTO> spidLst=new ArrayList<LineItemValueDTO>();
		StringBuilder nfaNumNew=new StringBuilder();
		String operationExec=AppConstants.PR_CREATE;
		SCMXMLDto scmXMLDtoObj=new SCMXMLDto();
		ArrayList<SCMXMLDto> scmXMLDtoLst=new ArrayList<SCMXMLDto>();
		ArrayList<LineItemValueDTO> lnItmValDTOObjLst=new ArrayList<LineItemValueDTO>();
		int returnStatus=0;
		try {
			spidLst=scmDaoObj.getLineItemDtls(conn, orderNumber);
			if(spidLst!=null && spidLst.size()>0){
				LineItemValueDTO lnItmValDTOObj=new LineItemValueDTO();
				int lstSize=spidLst.size();
				StringBuilder nfaDesc=new StringBuilder(Utility.getAppConfigValue(AppConstants.NFA_NUMBER_ATT_DESC_KEY));
				for(int i=0;i<lstSize;i++){
					lnItmValDTOObj=(LineItemValueDTO)spidLst.get(i);
					scmXMLDtoObj=new SCMXMLDto();
					nfaNumNew=new StringBuilder();
					nfaNumNew.append(orderNumber);
					nfaNumNew.append(AppConstants.NFA_SEPARATOR);
					nfaNumNew.append(serviceId);
					nfaNumNew.append(AppConstants.NFA_SEPARATOR);
					nfaNumNew.append(lnItmValDTOObj.getSpId());
					nfaNumNew.append(AppConstants.NFA_SEPARATOR);
					nfaNumNew.append(m6OrderId);
					//update Nfa number
					Utility.LOG(true,false,"ScmProgStatus"+lnItmValDTOObj.getScmProgStatus());
					Utility.LOG(true,false,"IsPrReuse"+lnItmValDTOObj.getIsPrReuse());
					Utility.LOG(true,false,"PrId"+lnItmValDTOObj.getPrId());
					lnItmValDTOObj.setAttValue(nfaNumNew.toString());
					lnItmValDTOObjLst.add(lnItmValDTOObj);
					if((lnItmValDTOObj.getScmProgStatus()==null || lnItmValDTOObj.getScmProgStatus().equalsIgnoreCase("")) && lnItmValDTOObj.getIsPrReuse()==0){
						//SET values values in DTO
						scmXMLDtoObj.setOrderNumber(orderNumber);
						scmXMLDtoObj.setServiceId(serviceId);
						scmXMLDtoObj.setServicePrdId(lnItmValDTOObj.getSpId());
						scmXMLDtoObj.setOperatnExec(operationExec.toString());
						scmXMLDtoObj.setSend_status(AppConstants.STATUS_FOR_EXISTING_SPID);
						scmXMLDtoObj.setPrId(0);
						scmXMLDtoLst.add(scmXMLDtoObj);
					}
				}
				//update nfa number
				if(lnItmValDTOObjLst.size()>0){
					returnStatus=scmDaoObj.updateAttVal(conn, lnItmValDTOObjLst,nfaDesc.toString());
				}
				//insert xml
				conn.setAutoCommit(false);
				if(returnStatus==AppConstants.SCM_XML_PROCESSED_SUCCESS && scmXMLDtoLst.size()>0){
					returnStatus=scmDaoObj.insertSCMXML(conn, scmXMLDtoLst, eventTypeId);
					if(returnStatus==AppConstants.SCM_XML_PROCESSED_SUCCESS){
						conn.commit();
					}
				}
				
			}
		}catch (Exception e) {
			returnStatus=AppConstants.SCM_XML_PROCESSED_FAILURE;
			Utility.onEx_LOG_RET_NEW_EX(e, "updateNfaAndInsertInScmTable", "SCMBL", null, true, true);
		}finally{
			try {
				if(conn.getAutoCommit()==false){
					conn.setAutoCommit(true);
				}
			} catch (SQLException e) {
				 Utility.onEx_LOG_RET_NEW_EX(e, "insertInScmTablePRReuse", "processResponseXML", "sqlState", true, true);  
			}catch (Exception e) {
				Utility.onEx_LOG_RET_NEW_EX(e, "insertInScmTablePRReuse", "processResponseXML", null, true, true);
			}
		}		
		Utility.LOG(true,false,"Exiting updateNfaAndInsertInScmTable");	
		return returnStatus;
	}
	
	/**
	 * This method will create xml,update nfa number and insert created xml for PR creation and PR Reuse in case of 2222 event
	 * @param conn,orderNumber,serviceId,eventTypeId, m6OrderId
	 * @return Nothing.
    */
	public void createSCMXML(Connection conn,HashMap<SCMXMLDto,SCMXMLDto> mapParam){
		Utility.LOG(true,false,"Entering createSCMXML");
		ArrayList<PRCreatnSCMXmlDto> prCreatnSCMXmlLst=new ArrayList<PRCreatnSCMXmlDto>();
		ArrayList<LineItemValueDTO> lineItemValueLst=new ArrayList<LineItemValueDTO>();
		StringBuilder lineItemAttrXml=new StringBuilder();
		StringBuilder finalXml=new StringBuilder();
		SCMXMLDto scmXMLDtoObj=new SCMXMLDto();
		ArrayList<SCMXMLDto> scmXMLDtoLst=new ArrayList<SCMXMLDto>();
		String headerTemplate=new String();
		String footerTemplate=new String();
		String lineTemplate=new String();
		int returnStatus=0;
		SCMXMLDto tmpObj=new SCMXMLDto();
		try {
			headerTemplate=scmDaoObj.fetchXMLTemplate(conn,AppConstants.THIRD_PARTY_PR_REQUEST_HEADER_XML);
			footerTemplate=scmDaoObj.fetchXMLTemplate(conn,AppConstants.THIRD_PARTY_PR_REQUEST_FOOTER_XML);
			lineTemplate=scmDaoObj.fetchXMLTemplate(conn,AppConstants.THIRD_PARTY_PR_REQUEST_LINE_XML);
			for (Map.Entry<SCMXMLDto,SCMXMLDto> entry : mapParam.entrySet()) {
					System.out.println("xmlMapReuse Key : " + entry.getKey() + " Value : "
						+ entry.getValue());
				try{
					tmpObj=(SCMXMLDto)entry.getValue();

					scmXMLDtoObj=new SCMXMLDto();
					finalXml=new StringBuilder();
					finalXml.append(headerTemplate);
					//get line item attributes
					lineItemValueLst=scmDaoObj.getLineItemAttValues(conn,  tmpObj.getServicePrdId());
					//get item code details
					prCreatnSCMXmlLst=scmDaoObj.getItemCodeDtlsValues(conn,   tmpObj.getServicePrdId());
					//create line item attribute xml one time
					lineItemAttrXml=createAttributeXML(lineItemValueLst);
					//create item code xml
					finalXml.append(createFinalSCMXMLPrCreation(prCreatnSCMXmlLst,lineItemAttrXml,lineTemplate));
					finalXml.append(footerTemplate);
					System.out.println("finalXml"+finalXml);
					//SET xml values in DTO
					scmXMLDtoObj.setXml(finalXml.toString());
					scmXMLDtoObj.setXmlId(tmpObj.getXmlId());
					scmXMLDtoObj.setSend_status(AppConstants.STATUS_FOR_SUCCESS_XML_CREATION);
					scmXMLDtoObj.setScmProgressStatus(AppConstants.REQUEST_SENT_FOR_PR_CREATION);
					tmpObj.setXml(finalXml.toString());
				}catch (Exception e) {
					scmXMLDtoObj.setSend_status(AppConstants.STATUS_FOR_FAILURE_XML_CREATION);
					scmXMLDtoObj.setScmProgressStatus(AppConstants.REQUEST_FAILED_FOR_PR_CREATION);
					tmpObj.setXml(AppConstants.XML_NOT_CREATED);
					Utility.onEx_LOG_RET_NEW_EX(e, "createSCMXML", "SCMBL", "Exception ", true, true);
				}
					scmXMLDtoObj.setPrId(0);
					mapParam.put(entry.getKey(), tmpObj);
					scmXMLDtoLst.add(scmXMLDtoObj);
			}
				//insert xml
				if(scmXMLDtoLst.size()>0){
					conn.setAutoCommit(false);
					returnStatus=scmDaoObj.updateSCMXML(conn, scmXMLDtoLst);
					if(returnStatus==AppConstants.SCM_XML_PROCESSED_SUCCESS){
							scmDaoObj.updateRcvdMessage(conn, scmXMLDtoLst);
							if(returnStatus==AppConstants.SCM_XML_PROCESSED_SUCCESS){
								conn.commit();
							}
					}
				}
		}catch (Exception e) {
			Utility.onEx_LOG_RET_NEW_EX(e, "createSCMXML", "SCMBL", null, true, true);
		}finally{
			try {
				if(conn.getAutoCommit()==false){
					conn.setAutoCommit(true);
				}
			} catch (SQLException e) {
				 Utility.onEx_LOG_RET_NEW_EX(e, "insertInScmTableCircuitUpdate", "processResponseXML", "sqlState", true, true);  
			}catch (Exception e) {
				Utility.onEx_LOG_RET_NEW_EX(e, "insertInScmTableCircuitUpdate", "processResponseXML", null, true, true);
			}
		}		
		Utility.LOG(true,false,"Exiting createSCMXML");	
	}
	
	/**
	 * This method will create xml  and insert created xml for PR creation and PR Reuse in case of Repush event
	 * @param conn,orderNumber,serviceId,eventTypeId, serviceProductID
	 * @return Nothing.
    */
	public void createSCMXMLRepush(Connection conn,int orderNumber,int serviceId,String eventTypeId,int serviceProductID){
		Utility.LOG(true,false,"Entering createSCMXMLRepush");
		ArrayList<PRCreatnSCMXmlDto> prCreatnSCMXmlLst=new ArrayList<PRCreatnSCMXmlDto>();
		ArrayList<LineItemValueDTO> lineItemValueLst=new ArrayList<LineItemValueDTO>();
		StringBuilder lineItemAttrXml=new StringBuilder();
		StringBuilder finalXml=new StringBuilder();
		StringBuilder operationExec=new StringBuilder();
		LineItemValueDTO lnItmValDTOObj=new LineItemValueDTO();
		SCMXMLDto scmXMLDtoObj=new SCMXMLDto();
		ArrayList<SCMXMLDto> scmXMLDtoLst=new ArrayList<SCMXMLDto>();
		String headerTemplate=new String();
		String footerTemplate=new String();
		String lineTemplate=new String();
		int returnStatus=0;
		try {
			lnItmValDTOObj.setSpId(serviceProductID);
			operationExec=new StringBuilder();
			finalXml=new StringBuilder();
			//SET xml values in DTO
			scmXMLDtoObj.setOrderNumber(orderNumber);
			scmXMLDtoObj.setServiceId(serviceId);
			scmXMLDtoObj.setServicePrdId(lnItmValDTOObj.getSpId());
			headerTemplate=scmDaoObj.fetchXMLTemplate(conn,AppConstants.THIRD_PARTY_PR_REQUEST_HEADER_XML);
			footerTemplate=scmDaoObj.fetchXMLTemplate(conn,AppConstants.THIRD_PARTY_PR_REQUEST_FOOTER_XML);
			lineTemplate=scmDaoObj.fetchXMLTemplate(conn,AppConstants.THIRD_PARTY_PR_REQUEST_LINE_XML);
			finalXml.append(headerTemplate);
			operationExec.append(AppConstants.PR_CREATE);
			//get line item attributes
			lineItemValueLst=scmDaoObj.getLineItemAttValues(conn,  lnItmValDTOObj.getSpId());
			//get item code details
			prCreatnSCMXmlLst=scmDaoObj.getItemCodeDtlsValues(conn,   lnItmValDTOObj.getSpId());
			//create line item attribute xml one time
			lineItemAttrXml=createAttributeXML(lineItemValueLst);
			//create item code xml
			finalXml=createFinalSCMXMLPrCreation(prCreatnSCMXmlLst,lineItemAttrXml,lineTemplate);
			finalXml.append(footerTemplate);
			scmXMLDtoObj.setOperatnExec(operationExec.toString());
			scmXMLDtoObj.setXml(finalXml.toString());
			scmXMLDtoObj.setSend_status(AppConstants.STATUS_FOR_SUCCESS_XML_CREATION);
			scmXMLDtoObj.setScmProgressStatus(AppConstants.REQUEST_SENT_FOR_PR_CREATION);
			scmXMLDtoObj.setPrId(0);
			scmXMLDtoLst.add(scmXMLDtoObj);
			conn.setAutoCommit(false);
			//insert xml
			returnStatus=scmDaoObj.insertSCMXML(conn, scmXMLDtoLst, eventTypeId);
			if(returnStatus==AppConstants.SCM_XML_PROCESSED_SUCCESS){
				returnStatus=scmDaoObj.updateRcvdMessage(conn, scmXMLDtoLst);
				if(returnStatus==AppConstants.SCM_XML_PROCESSED_SUCCESS){
					conn.commit();
				}
			}
			
		} catch (Exception e) {
			Utility.onEx_LOG_RET_NEW_EX(e, "createSCMXMLRepush", "SCMBL", null, true, true);
		}finally{
			try {
				if(conn.getAutoCommit()==false){
					conn.setAutoCommit(true);
				}
			} catch (SQLException e) {
				 Utility.onEx_LOG_RET_NEW_EX(e, "insertInScmTableCircuitUpdate", "processResponseXML", "sqlState", true, true);  
			}catch (Exception e) {
				Utility.onEx_LOG_RET_NEW_EX(e, "insertInScmTableCircuitUpdate", "processResponseXML", null, true, true);
			}
		}	
		Utility.LOG(true,false,"Exiting createSCMXMLRepush");	
		
	}
	

	/**
	 * This method will create xml for PR creation and PR Reuse in case of Repush event
	 * @param prCreatnSCMXmlLst(have list of scm lines),lineItemAttrXml(xml of scm line attributes)
	 * @return finalXml(created xml) .
    */
	private StringBuilder createFinalSCMXMLPrCreation(ArrayList<PRCreatnSCMXmlDto> prCreatnSCMXmlLst,StringBuilder lineItemAttrXml,String lineTemplate){
		Utility.LOG(true,false,"Entering createFinalSCMXMLPrCreation");
		StringBuilder finalXml=new StringBuilder();
		try{
			int itemSize=prCreatnSCMXmlLst.size();
			PRCreatnSCMXmlDto prCreatnSCMXmlDtoObj=new PRCreatnSCMXmlDto();
			String lineTemplateTemp=new String();
			for(int i=0;i<itemSize;i++){
				prCreatnSCMXmlDtoObj=(PRCreatnSCMXmlDto)prCreatnSCMXmlLst.get(i);
				lineTemplateTemp=lineTemplate;
				lineTemplateTemp=lineTemplateTemp.replaceAll("transaction_id_value", String.valueOf(prCreatnSCMXmlDtoObj.getTrxId()));
				lineTemplateTemp=lineTemplateTemp.replaceAll("item_segment1_value", prCreatnSCMXmlDtoObj.getItemCode());
				lineTemplateTemp=lineTemplateTemp.replaceAll("quantity_value", String.valueOf(prCreatnSCMXmlDtoObj.getQuantity()));
				lineTemplateTemp=lineTemplateTemp.replaceAll("unit_price_value", String.valueOf(prCreatnSCMXmlDtoObj.getItemPrice()));
				lineTemplateTemp=lineTemplateTemp.replaceAll("deliver_to_location_value", prCreatnSCMXmlDtoObj.getDelToLoc());
				lineTemplateTemp=lineTemplateTemp.replaceAll("destination_subinventory_value", prCreatnSCMXmlDtoObj.getDestSubInv());
				lineTemplateTemp=lineTemplateTemp.replaceAll("aop_budget_head1_l_att2_value",prCreatnSCMXmlDtoObj.getAopHead1());
				lineTemplateTemp=lineTemplateTemp.replaceAll("aop_budget_head2_l_att5_value", prCreatnSCMXmlDtoObj.getAopHead2());
				lineTemplateTemp=lineTemplateTemp.replaceAll("aop_year_l_att6_value", prCreatnSCMXmlDtoObj.getAopYear());
				lineTemplateTemp=lineTemplateTemp.replaceAll("total_line_value", String.valueOf(itemSize));
				finalXml.append(lineTemplateTemp);
				finalXml.append(lineItemAttrXml);
				finalXml.append("</Line>");
			}
			//finalXml.append("</PRCreation:businessInput><PRCreation:businessOutput><PRCreation:status /><PRCreation:message /></PRCreation:businessOutput></PRCreation:PRCreate>");
		}catch (Exception e) {
			Utility.onEx_LOG_RET_NEW_EX(e, "createFinalSCMXMLPrCreation", "SCMBL", null, true, true);
		}
		Utility.LOG(true,false,"Exiting createFinalSCMXMLPrCreation");
		return finalXml;
	}
	
	/**
	 * This method will create xml for PR creation for the line attributes
	 * @param lineItemAttrXml(xml of scm line attributes)
	 * @return lineItemAttrXml(created xml) .
    */
	private StringBuilder createAttributeXML(ArrayList<LineItemValueDTO> lineItemValueLst){
		Utility.LOG(true,false,"Entering createAttributeXML");
		StringBuilder lineItemAttrXml=new StringBuilder();
		try{
			int itemSize=lineItemValueLst.size();
			LineItemValueDTO lineItemValueDTOObj=new LineItemValueDTO();
			for(int i=0;i<itemSize;i++){
				lineItemValueDTOObj=(LineItemValueDTO)lineItemValueLst.get(i);
				if((lineItemValueDTOObj.getExpectedValue().equalsIgnoreCase("DROPDOWN") || lineItemValueDTOObj.getExpectedValue().equalsIgnoreCase("LOV"))
					&& lineItemValueDTOObj.getAttValue().equalsIgnoreCase("0")){
					
					lineItemValueDTOObj.setExpectedValue("");
				}
				if(lineItemValueDTOObj.getAttDesc().equalsIgnoreCase("creation_date") || lineItemValueDTOObj.getAttDesc().equalsIgnoreCase("need_by_date")){
					lineItemValueDTOObj.setAttValue(formatDateRequest(lineItemValueDTOObj.getAttValue()));
				}
				lineItemAttrXml.append("<PRCreation:");
				lineItemAttrXml.append(lineItemValueDTOObj.getAttDesc());
				lineItemAttrXml.append(">");
				lineItemAttrXml.append(lineItemValueDTOObj.getAttValue());
				lineItemAttrXml.append("</PRCreation:");
				lineItemAttrXml.append(lineItemValueDTOObj.getAttDesc());
				lineItemAttrXml.append(">");
			}
		}catch (Exception e) {
			Utility.onEx_LOG_RET_NEW_EX(e, "createAttributeXML", "SCMBL", null, true, true);
		}
		Utility.LOG(true,false,"Exiting createAttributeXML");
		return lineItemAttrXml;
	}
	
	/**
	 * This method will format date according to SCM date format
	 * @param inputDateStr
	 * @return formattedDate
    */
	private String formatDateRequest(String inputDateStr){
		Utility.LOG(true,false,"Entering formatDateRequest");
		String formattedDate=new String();
		try{
			SimpleDateFormat formatter = new SimpleDateFormat(AppConstants.CALENDER_FORMAT_MMDDYY);
				if(inputDateStr!=null && !inputDateStr.equalsIgnoreCase("")){
				Date inputDate = formatter.parse(inputDateStr);
				 formattedDate = formatter.format(inputDate);
				Date date1 = formatter.parse(formattedDate);
				formatter = new SimpleDateFormat(AppConstants.DATE_FORMAT_DDMMMyyyy);
				formattedDate = formatter.format(date1);
			}else{
				formattedDate="";
			}
			System.out.println("formattedDate"+formattedDate);
		}catch(Exception e){
			Utility.onEx_LOG_RET_NEW_EX(e, "formatDateRequest", "SCMBL", null, true, true);
		}
		Utility.LOG(true,false,"Exiting formatDateRequest");
		return formattedDate;
	}
	
	/**
	 * This method will convert BLANK String to '' as receive from SCM
	 * @param inputStr
	 * @return outputStr
    */
	private String formatBlankResponse(String inputStr){
		Utility.LOG(true,false,"Entering formatBlankResponse");
		String outputStr=new String();
		try{
			if(inputStr!=null && inputStr.equalsIgnoreCase(AppConstants.SCM_BLANK_RESPONSE)){
				outputStr="";
			}else{
				outputStr=inputStr;
			}
		}catch(Exception e){
			Utility.onEx_LOG_RET_NEW_EX(e, "formatBlankResponse", "SCMBL", null, true, true);
		}
		Utility.LOG(true,false,"Exiting formatBlankResponse");
		return outputStr;
	}

	
	/**
	 * This method will create xml for PR Reuse
	 * @param newNfa(New NFA Number Generated),oldPrDtlsMap(Old NFA Number PR Details)
	 * @return finalXml
    */
	private StringBuilder createFinalSCMXMLPrReuse(HashMap<String,String> oldPrDtlsMap,String dataTemplate){
		Utility.LOG(true,false,"Entering createFinalSCMXMLPrReuse");
		StringBuilder finalXml=new StringBuilder();
		String dataTemplateTemp=new String();
		try{
			dataTemplateTemp=dataTemplate;
			dataTemplateTemp=dataTemplateTemp.replaceAll("new_nfa_number_value", oldPrDtlsMap.get("nfaNumber"));
			dataTemplateTemp=dataTemplateTemp.replaceAll("old_nfa_number_value", oldPrDtlsMap.get("oldNfaNumber"));
			dataTemplateTemp=dataTemplateTemp.replaceAll("pr_number_value", oldPrDtlsMap.get("prNumber"));
			dataTemplateTemp=dataTemplateTemp.replaceAll("circle_value", oldPrDtlsMap.get("circle"));
			finalXml.append(dataTemplateTemp);
		}catch (Exception e) {
			Utility.onEx_LOG_RET_NEW_EX(e, "createFinalSCMXMLPrReuse", "SCMBL", null, true, true);
		}
		Utility.LOG(true,false,"Exiting createFinalSCMXMLPrReuse");
		return finalXml;
	}
	
	

	/**
	 * This method will update data in tables in case New PR Creation
	 * @param conn,scmXMLDtoObjCurr(object having details of PR)
	 * @return finalXml
    */
	public  int updateSCMXMLDataPrCreate(Connection conn,SCMRecXmlDto scmXMLDtoObjCurr) {
		Utility.LOG(true,false,"Entering updateSCMXMLDataPrCreate");
		int returnStatus=0;
		try{
			
			returnStatus=scmDaoObj.updateSCMXMLDataPrCreate(conn, scmXMLDtoObjCurr);
			if(returnStatus==AppConstants.SCM_XML_PROCESSED_SUCCESS){
				//send circuit if present
				StringBuilder attDesc=new StringBuilder(Utility.getAppConfigValue(AppConstants.CIRCUIT_ID_ATT_DESC_KEY));
				String cktNumber=scmDaoObj.getLineAttValue(conn,scmXMLDtoObjCurr.getServicePrdId(),attDesc.toString());
				if(cktNumber!=null && !cktNumber.equalsIgnoreCase("")){
					createScmCircuitXml(conn,scmXMLDtoObjCurr.getOrderNumber(), scmXMLDtoObjCurr.getServiceId(),
						AppConstants.REPUSH_EVENT,cktNumber,scmXMLDtoObjCurr.getServicePrdId());
				}
			}
			
		}catch (Exception e) {
			returnStatus=AppConstants.SCM_XML_PROCESSED_FAILURE;
		    Utility.onEx_LOG_RET_NEW_EX(e, "updateSCMXMLDataPrCreate", "SCMBL", null, true, true);
		}
		Utility.LOG(true,false,"Exiting updateSCMXMLDataPrCreate");	
		 return returnStatus;
		
	}

	/**
	 * This method will save tags value from xml received from SCM in java object in case of PR creation
	 * @param doc(xml received from SCM),scmXMLDtoObjCurr(object having details of PR)
	 * @return returnStatus
    */
	public int processXmlForCreate(Document doc,SCMRecXmlDto scmXMLDtoObjCurr){
		Utility.LOG(true,false,"Entering processXmlForCreate");
		int returnStatus=0;
		try{
			NodeList nodeLstLine = doc.getElementsByTagName("Line");
			HashMap<String,String> attrMap=new HashMap<String,String>();
			scmXMLDtoObjCurr.setPrCreatnSCMXmlDtoList(new ArrayList<PRCreatnSCMXmlDto>());
			scmXMLDtoObjCurr.setTotalLine(nodeLstLine.getLength());
			
			 for (int s = 0; s < nodeLstLine.getLength(); s++) {
		    	  PRCreatnSCMXmlDto prCreatnSCMXmlDtoObj=null;
		    		  Node fstNode = nodeLstLine.item(s);
		  			  if (fstNode.getNodeType() == Node.ELEMENT_NODE){
		  				  prCreatnSCMXmlDtoObj=new PRCreatnSCMXmlDto();
		  				  Element xmlElmnt = (Element) fstNode;
				    	  System.out.println("PR Receive operatnName "+scmXMLDtoObjCurr.getOperatnExec());
				    	//to get trx id
				    	  if(scmXMLDtoObjCurr.getOperatnExec()!=null && scmXMLDtoObjCurr.getOperatnExec().equalsIgnoreCase(AppConstants.PR_CREATION)){	
			  				  NodeList trxIdElmntLst = xmlElmnt.getElementsByTagName("PRResponse:transaction_id");
			  				  Element trxIdElmnt = (Element) trxIdElmntLst.item(0);
			  				if(trxIdElmnt==null){
						    	  prCreatnSCMXmlDtoObj.setTrxId(Integer.parseInt("0"));
						      }else{
						    	  NodeList trxId = trxIdElmnt.getChildNodes();
						    	  System.out.println("PR Receive transaction_id "+((Node) trxId.item(0)).getNodeValue());
						    	  if(trxId.item(0)!=null){
							    	  if(((Node) trxId.item(0)).getNodeValue()!=null &&  (!((Node) trxId.item(0)).getNodeValue().equalsIgnoreCase(""))){
							    		  prCreatnSCMXmlDtoObj.setTrxId(Integer.parseInt(((Node) trxId.item(0)).getNodeValue().trim()));  
								      }else{
								    	  prCreatnSCMXmlDtoObj.setTrxId(Integer.parseInt("0"));
								      }
						    	  }else{
						    		  prCreatnSCMXmlDtoObj.setTrxId(Integer.parseInt("0"));
						    	  }	 
					    	  }
					      }else{
				    		  prCreatnSCMXmlDtoObj.setTrxId(Integer.parseInt("0"));
				    	  }
							     
	
		  				//to get item code
		  				  NodeList itemCodeElmntLst = xmlElmnt.getElementsByTagName("PRResponse:item_segment1");
		  				  Element itemCodeElmnt = (Element) itemCodeElmntLst.item(0);
					      NodeList itemCode = itemCodeElmnt.getChildNodes();
					      System.out.println("PR Receive item_segment1 "+((Node) itemCode.item(0)).getNodeValue());
					      if(itemCode.item(0)!=null){
					    	  if(((Node) itemCode.item(0)).getNodeValue()!=null &&  (!((Node) itemCode.item(0)).getNodeValue().equalsIgnoreCase(""))){
					    		  prCreatnSCMXmlDtoObj.setItemCode(((Node) itemCode.item(0)).getNodeValue().trim());  
						      }else{
						    	  prCreatnSCMXmlDtoObj.setItemCode("");
						      }
				    	  }else{
				    		  prCreatnSCMXmlDtoObj.setItemCode("");
				    	  }
					     
					     //to get PR number
					      NodeList prNumElmntLst = xmlElmnt.getElementsByTagName("PRResponse:pr_number");
		  				  Element prNumElmnt = (Element) prNumElmntLst.item(0);
					      NodeList prNum = prNumElmnt.getChildNodes();
					      System.out.println("PR Receive prNum"+((Node) prNum.item(0)).getNodeValue());
					      if(prNum.item(0)!=null){
					    	  if(((Node) prNum.item(0)).getNodeValue()!=null &&  (!((Node) prNum.item(0)).getNodeValue().equalsIgnoreCase(""))){
					    		  scmXMLDtoObjCurr.setPrNumber(((Node) prNum.item(0)).getNodeValue().trim());  
						      }else{
						    	  scmXMLDtoObjCurr.setPrNumber("");
						      }
				    	  }else{
				    		  scmXMLDtoObjCurr.setPrNumber("");
				    	  }
					      
					      //to get PR Value
					     /* NodeList prValElmntLst = xmlElmnt.getElementsByTagName("PRResponse:PRValue");
		  				  Element prValElmnt = (Element) prValElmntLst.item(0);
					      NodeList prVal = prValElmnt.getChildNodes();
					      System.out.println("PR Receive prVal "+((Node) prVal.item(0)).getNodeValue());
					      if(prVal.item(0)!=null){
					    	  if(((Node) prVal.item(0)).getNodeValue()!=null &&  (!((Node) prVal.item(0)).getNodeValue().equalsIgnoreCase(""))){
					    		  scmXMLDtoObjCurr.setPrValue(((Node) prVal.item(0)).getNodeValue().trim());  
						      }else{
						    	  scmXMLDtoObjCurr.setPrValue("");
						      }
				    	  }else{
				    		  scmXMLDtoObjCurr.setPrValue("");
				    	  }*/
					      scmXMLDtoObjCurr.setPrValue("0");
					      //to get PO Number
					      NodeList poNumElmntLst = xmlElmnt.getElementsByTagName("PRResponse:po_number");
		  				  Element poNumElmnt = (Element) poNumElmntLst.item(0);
					      NodeList poNum = poNumElmnt.getChildNodes();
					      System.out.println("PR Receive poNum "+((Node) poNum.item(0)).getNodeValue());
					      if(poNum.item(0)!=null){
					    	  if(((Node) poNum.item(0)).getNodeValue()!=null &&  (!((Node) poNum.item(0)).getNodeValue().equalsIgnoreCase(""))){
					    		  prCreatnSCMXmlDtoObj.setTrdPartyPoNum(((Node) poNum.item(0)).getNodeValue().trim()); 
					    		  prCreatnSCMXmlDtoObj.setTrdPartyPoNum(formatBlankResponse(prCreatnSCMXmlDtoObj.getTrdPartyPoNum()));
					    		  
						      }else{
						    	  prCreatnSCMXmlDtoObj.setTrdPartyPoNum("");
						      }
				    	  }else{
				    		  prCreatnSCMXmlDtoObj.setTrdPartyPoNum("");
				    	  }
					      
					    //to get PO Date
					      NodeList poDateElmntLst = xmlElmnt.getElementsByTagName("PRResponse:po_creation_date");
		  				  Element poDateElmnt = (Element) poDateElmntLst.item(0);
					      NodeList poDate = poDateElmnt.getChildNodes();
					      System.out.println("PR Receive Date_of_PO "+((Node) poDate.item(0)).getNodeValue());
					      if(poDate.item(0)!=null){
					    	  if(((Node) poDate.item(0)).getNodeValue()!=null &&  (!((Node) poDate.item(0)).getNodeValue().equalsIgnoreCase(""))){
					    		  prCreatnSCMXmlDtoObj.setDateOfPOTP(((Node) poDate.item(0)).getNodeValue().trim()); 
					    		  prCreatnSCMXmlDtoObj.setDateOfPOTP(formatBlankResponse((prCreatnSCMXmlDtoObj.getDateOfPOTP())));
						      }else{
						    	  prCreatnSCMXmlDtoObj.setDateOfPOTP("");
						      }
				    	  }else{
				    		  prCreatnSCMXmlDtoObj.setDateOfPOTP("");
				    	  }
					    //to get PO Amount
					      NodeList poAmtElmntLst = xmlElmnt.getElementsByTagName("PRResponse:po_unit_price");
		  				  Element poAmtElmnt = (Element) poAmtElmntLst.item(0);
					      NodeList poAmt = poAmtElmnt.getChildNodes();
					      System.out.println("PR Receive PO_Amount "+((Node) poAmt.item(0)).getNodeValue());
					      if(poAmt.item(0)!=null){
					    	  if(((Node) poAmt.item(0)).getNodeValue()!=null &&  (!((Node) poAmt.item(0)).getNodeValue().equalsIgnoreCase(""))){
					    		  prCreatnSCMXmlDtoObj.setPoAmtToTP(((Node) poAmt.item(0)).getNodeValue().trim());  
					    		  prCreatnSCMXmlDtoObj.setPoAmtToTP(formatBlankResponse(prCreatnSCMXmlDtoObj.getPoAmtToTP()));
					    		  if(prCreatnSCMXmlDtoObj.getPoAmtToTP().equalsIgnoreCase("")){
					    			  prCreatnSCMXmlDtoObj.setPoAmtToTP("0.0");
					    		  }
						      }else{
						    	  prCreatnSCMXmlDtoObj.setPoAmtToTP("0.0");
						      }
				    	  }else{
				    		  prCreatnSCMXmlDtoObj.setPoAmtToTP("0.0");
				    	  }
					      scmXMLDtoObjCurr.setPrRaisedBy("");
					      //to get PR Description
					      NodeList prDescElmntLst = xmlElmnt.getElementsByTagName("PRResponse:header_description");
		  				  Element prDescElmnt = (Element) prDescElmntLst.item(0);
					      NodeList prDesc = prDescElmnt.getChildNodes();
					      System.out.println("PR Receive PR_Description "+((Node) prDesc.item(0)).getNodeValue());
					      if(prDesc.item(0)!=null){
					    	  if(((Node) prDesc.item(0)).getNodeValue()!=null &&  (!((Node) prDesc.item(0)).getNodeValue().equalsIgnoreCase(""))){
					    		  scmXMLDtoObjCurr.setPrDesc(((Node) prDesc.item(0)).getNodeValue().trim());  
						      }else{
						    	  scmXMLDtoObjCurr.setPrDesc("");
						      }
				    	  }else{
				    		  scmXMLDtoObjCurr.setPrDesc("");
				    	  }
					      
					      //to get PR Deliver To Location
					      NodeList delToLocElmntLst = xmlElmnt.getElementsByTagName("PRResponse:deliver_to_location");
		  				  Element delToLocElmnt = (Element) delToLocElmntLst.item(0);
					      NodeList delToLoc = delToLocElmnt.getChildNodes();
					      System.out.println("PR Receive deliver_to_location "+((Node) delToLoc.item(0)).getNodeValue());
					      if(delToLoc.item(0)!=null){
					    	  if(((Node) delToLoc.item(0)).getNodeValue()!=null &&  (!((Node) delToLoc.item(0)).getNodeValue().equalsIgnoreCase(""))){
					    		  prCreatnSCMXmlDtoObj.setDelToLoc(((Node) delToLoc.item(0)).getNodeValue().trim());  
						      }else{
						    	  prCreatnSCMXmlDtoObj.setDelToLoc("");
						      }
				    	  }else{
				    		  prCreatnSCMXmlDtoObj.setDelToLoc("");
				    	  }
					      
					   
					      
					    //to get Quantity
		  				  NodeList quanElmntLst = xmlElmnt.getElementsByTagName("PRResponse:quantity");
		  				  Element quanElmnt = (Element) quanElmntLst.item(0);
					      NodeList quantity = quanElmnt.getChildNodes();
					      System.out.println("PR Receive quantity "+((Node) quantity.item(0)).getNodeValue());
					      if(quantity.item(0)!=null){
					    	  if(((Node) quantity.item(0)).getNodeValue()!=null &&  (!((Node) quantity.item(0)).getNodeValue().equalsIgnoreCase(""))){
					    		  prCreatnSCMXmlDtoObj.setQuantity(((Node) quantity.item(0)).getNodeValue().trim());  
						      }else{
						    	  prCreatnSCMXmlDtoObj.setQuantity("");
						      }
				    	  }else{
				    		  prCreatnSCMXmlDtoObj.setQuantity("");
				    	  }
					      
					      //to get Item Price
		  				  NodeList itmPrcElmntLst = xmlElmnt.getElementsByTagName("PRResponse:unit_price");
		  				  Element itmPrcElmnt = (Element) itmPrcElmntLst.item(0);
					      NodeList itmPrc = itmPrcElmnt.getChildNodes();
					      System.out.println("PR Receive Item_price "+((Node) itmPrc.item(0)).getNodeValue());
					      if(itmPrc.item(0)!=null){
					    	  if(((Node) itmPrc.item(0)).getNodeValue()!=null &&  (!((Node) itmPrc.item(0)).getNodeValue().equalsIgnoreCase(""))){
					    		  prCreatnSCMXmlDtoObj.setItemPrice(((Node) itmPrc.item(0)).getNodeValue().trim());  
						      }else{
						    	  prCreatnSCMXmlDtoObj.setItemPrice("");
						      }
				    	  }else{
				    		  prCreatnSCMXmlDtoObj.setItemPrice("");
				    	  }
					      
					    //to get PR Status
		  				  NodeList prStatElmntLst = xmlElmnt.getElementsByTagName("PRResponse:pr_status");
		  				  Element prStatElmnt = (Element) prStatElmntLst.item(0);
					      NodeList prStat = prStatElmnt.getChildNodes();
					      if(prStat.item(0)!=null){
					    	  if(((Node) prStat.item(0)).getNodeValue()!=null &&  (!((Node) prStat.item(0)).getNodeValue().equalsIgnoreCase(""))){
					    		  scmXMLDtoObjCurr.setPrStatus(((Node) prStat.item(0)).getNodeValue().trim());  
						      }else{
						    	  scmXMLDtoObjCurr.setPrStatus("");
						      }
				    	  }else{
				    		  scmXMLDtoObjCurr.setPrStatus("");
				    	  }
					      
					      
					      
					      //to get Destination Subinventory
		  				  NodeList destSubInvElmntLst = xmlElmnt.getElementsByTagName("PRResponse:dest_subinventory");
		  				  Element destSubInvElmnt = (Element) destSubInvElmntLst.item(0);
					      NodeList destSubInv = destSubInvElmnt.getChildNodes();
					      System.out.println("PR Receive destSubInv "+((Node) destSubInv.item(0)).getNodeValue());
					      if(destSubInv.item(0)!=null){
					    	  if(((Node) destSubInv.item(0)).getNodeValue()!=null &&  (!((Node) destSubInv.item(0)).getNodeValue().equalsIgnoreCase(""))){
					    		  prCreatnSCMXmlDtoObj.setDestSubInv(((Node) destSubInv.item(0)).getNodeValue().trim());  
						      }else{
						    	  prCreatnSCMXmlDtoObj.setDestSubInv("");
						      }
				    	  }else{
				    		  prCreatnSCMXmlDtoObj.setDestSubInv("");
				    	  }
					      
					    //to get AOP Head 1
		  				  NodeList aopOneElmntLst = xmlElmnt.getElementsByTagName("PRResponse:aop_budget_head1");
		  				  Element aopOneElmnt = (Element) aopOneElmntLst.item(0);
					      NodeList aopOne = aopOneElmnt.getChildNodes();
					      System.out.println("PR Receive aopOne "+((Node) aopOne.item(0)).getNodeValue());
					      if(aopOne.item(0)!=null){
					    	  if(((Node) aopOne.item(0)).getNodeValue()!=null &&  (!((Node) aopOne.item(0)).getNodeValue().equalsIgnoreCase(""))){
					    		  prCreatnSCMXmlDtoObj.setAopHead1(((Node) aopOne.item(0)).getNodeValue().trim());  
						      }else{
						    	  prCreatnSCMXmlDtoObj.setAopHead1("");
						      }
				    	  }else{
				    		  prCreatnSCMXmlDtoObj.setAopHead1("");
				    	  }
					      
					      //to get AOP Head 2
		  				  NodeList aopTwoElmntLst = xmlElmnt.getElementsByTagName("PRResponse:aop_budget_head2");
		  				  Element aopTwoElmnt = (Element) aopTwoElmntLst.item(0);
					      NodeList aopTwo = aopTwoElmnt.getChildNodes();
					      System.out.println("PR Receive aopTwo "+((Node) aopTwo.item(0)).getNodeValue());
					      if(aopTwo.item(0)!=null){
					    	  if(((Node) aopTwo.item(0)).getNodeValue()!=null &&  (!((Node) aopTwo.item(0)).getNodeValue().equalsIgnoreCase(""))){
					    		  prCreatnSCMXmlDtoObj.setAopHead2(((Node) aopTwo.item(0)).getNodeValue().trim());  
						      }else{
						    	  prCreatnSCMXmlDtoObj.setAopHead2("");
						      }
				    	  }else{
				    		  prCreatnSCMXmlDtoObj.setAopHead2("");
				    	  }
					      
					    //to get AOP Year
		  				  NodeList aopYearElmntLst = xmlElmnt.getElementsByTagName("PRResponse:aop_year");
		  				  Element aopYearElmnt = (Element) aopYearElmntLst.item(0);
					      NodeList aopYear = aopYearElmnt.getChildNodes();
					      System.out.println("PR Receive aopYear "+((Node) aopYear.item(0)).getNodeValue());
					      if(aopYear.item(0)!=null){
					    	  if(((Node) aopYear.item(0)).getNodeValue()!=null &&  (!((Node) aopYear.item(0)).getNodeValue().equalsIgnoreCase(""))){
					    		  prCreatnSCMXmlDtoObj.setAopYear(((Node) aopYear.item(0)).getNodeValue().trim());  
						      }else{
						    	  prCreatnSCMXmlDtoObj.setAopYear("");
						      }
				    	  }else{
				    		  prCreatnSCMXmlDtoObj.setAopYear("");
				    	  }
					      if(prCreatnSCMXmlDtoObj.getAopYear()!=null && !prCreatnSCMXmlDtoObj.getAopYear().equalsIgnoreCase("")){
					    	  int index=prCreatnSCMXmlDtoObj.getAopYear().indexOf("R");
					    	  if(prCreatnSCMXmlDtoObj.getAopYear().length()>(index+2)){
					    		  prCreatnSCMXmlDtoObj.setAopYear(prCreatnSCMXmlDtoObj.getAopYear().substring(index+2, prCreatnSCMXmlDtoObj.getAopYear().length()));
					    	  }
					      }
					      System.out.println("PR Receive getAopYear "+prCreatnSCMXmlDtoObj.getAopYear());
					    //to get authorization status
		  				 /* NodeList authStatElmntLst = xmlElmnt.getElementsByTagName("PRResponse:authorization_status");
		  				  Element authStatElmnt = (Element) authStatElmntLst.item(0);
					      NodeList authStat = authStatElmnt.getChildNodes();
					      System.out.println("PR Receive authStat "+((Node) authStat.item(0)).getNodeValue());
					      if(authStat.item(0)!=null){
					    	  if(((Node) authStat.item(0)).getNodeValue()!=null &&  (!((Node) authStat.item(0)).getNodeValue().equalsIgnoreCase(""))){
					    		  scmXMLDtoObjCurr.setAuthStatus(((Node) authStat.item(0)).getNodeValue().trim());  
						      }else{
						    	  scmXMLDtoObjCurr.setAuthStatus("");
						      }
				    	  }else{
				    		  scmXMLDtoObjCurr.setAuthStatus("");
				    	  }*/
					      scmXMLDtoObjCurr.setAuthStatus("");
					    //to get buying type
		  				  NodeList buyTypeElmntLst = xmlElmnt.getElementsByTagName("PRResponse:buying_type");
		  				  Element buyTypeElmnt = (Element) buyTypeElmntLst.item(0);
					      NodeList buyType = buyTypeElmnt.getChildNodes();
					      System.out.println("PR Receive buyType "+((Node) buyType.item(0)).getNodeValue());
					      if(buyType.item(0)!=null){
					    	  if(((Node) buyType.item(0)).getNodeValue()!=null &&  (!((Node) buyType.item(0)).getNodeValue().equalsIgnoreCase(""))){
					    		  scmXMLDtoObjCurr.setBuyingType(((Node) buyType.item(0)).getNodeValue().trim());  
						      }else{
						    	  scmXMLDtoObjCurr.setBuyingType("");
						      }
				    	  }else{
				    		  scmXMLDtoObjCurr.setBuyingType("");
				    	  }
					      
					      //to get circle
		  				  NodeList circleElmntLst = xmlElmnt.getElementsByTagName("PRResponse:circle");
		  				  Element circleElmnt = (Element) circleElmntLst.item(0);
					      NodeList circle = circleElmnt.getChildNodes();
					      System.out.println("PR Receive circle "+((Node) circle.item(0)).getNodeValue());
					      if(circle.item(0)!=null){
					    	  if(((Node) circle.item(0)).getNodeValue()!=null &&  (!((Node) circle.item(0)).getNodeValue().equalsIgnoreCase(""))){
					    		  scmXMLDtoObjCurr.setCircle(((Node) circle.item(0)).getNodeValue().trim());  
						      }else{
						    	  scmXMLDtoObjCurr.setCircle("");
						      }
				    	  }else{
				    		  scmXMLDtoObjCurr.setCircle("");
				    	  }
					      
					    //to get Creation Date
		  				  NodeList creatnDateElmntLst = xmlElmnt.getElementsByTagName("PRResponse:creation_date");
		  				  Element creatnDateElmnt = (Element) creatnDateElmntLst.item(0);
					      NodeList creatnDate = creatnDateElmnt.getChildNodes();
					      System.out.println("PR Receive creatnDate "+((Node) creatnDate.item(0)).getNodeValue());
					      if(creatnDate.item(0)!=null){
					    	  if(((Node) creatnDate.item(0)).getNodeValue()!=null &&  (!((Node) creatnDate.item(0)).getNodeValue().equalsIgnoreCase(""))){
					    		  scmXMLDtoObjCurr.setCreatnDate(((Node) creatnDate.item(0)).getNodeValue().trim()); 
					    		  scmXMLDtoObjCurr.setPrDate(((Node) creatnDate.item(0)).getNodeValue().trim());
					    		 // scmXMLDtoObjCurr.setCreatnDate(formatDateResponse(scmXMLDtoObjCurr.getCreatnDate()));
					    		  //scmXMLDtoObjCurr.setPrDate(formatDateResponse(scmXMLDtoObjCurr.getPrDate()));
						      }else{
						    	  scmXMLDtoObjCurr.setCreatnDate("");
						    	  scmXMLDtoObjCurr.setPrDate("");
						      }
				    	  }else{
				    		  scmXMLDtoObjCurr.setCreatnDate("");
				    		  scmXMLDtoObjCurr.setPrDate("");
				    	  }
					      
					    //to get deliver to requestor
		  				  NodeList delToReqElmntLst = xmlElmnt.getElementsByTagName("PRResponse:deliver_to_request");
		  				  Element delToReqElmnt = (Element) delToReqElmntLst.item(0);
					      NodeList delToReq = delToReqElmnt.getChildNodes();
					      System.out.println("PR Receive delToReq "+((Node) delToReq.item(0)).getNodeValue());
					      if(delToReq.item(0)!=null){
					    	  if(((Node) delToReq.item(0)).getNodeValue()!=null &&  (!((Node) delToReq.item(0)).getNodeValue().equalsIgnoreCase(""))){
					    		  scmXMLDtoObjCurr.setDelToRequestor(((Node) delToReq.item(0)).getNodeValue().trim());  
						      }else{
						    	  scmXMLDtoObjCurr.setDelToRequestor("");
						      }
				    	  }else{
				    		  scmXMLDtoObjCurr.setDelToRequestor("");
				    	  }
					      
					    //to get fdoa capex
		  				  NodeList fdoaCapElmntLst = xmlElmnt.getElementsByTagName("PRResponse:fdoa_capex_opex");
		  				  Element fdoaCapElmnt = (Element) fdoaCapElmntLst.item(0);
					      NodeList fdoaCap = fdoaCapElmnt.getChildNodes();
					      System.out.println("PR Receive fdoaCap "+((Node) fdoaCap.item(0)).getNodeValue());
					      if(fdoaCap.item(0)!=null){
					    	  if(((Node) fdoaCap.item(0)).getNodeValue()!=null &&  (!((Node) fdoaCap.item(0)).getNodeValue().equalsIgnoreCase(""))){
					    		  scmXMLDtoObjCurr.setFdoaCapOpType(((Node) fdoaCap.item(0)).getNodeValue().trim());  
						      }else{
						    	  scmXMLDtoObjCurr.setFdoaCapOpType("");
						      }
				    	  }else{
				    		  scmXMLDtoObjCurr.setFdoaCapOpType("");
				    	  }
					      
					    //to get fdoa zone
		  				  NodeList fdoaZoneElmntLst = xmlElmnt.getElementsByTagName("PRResponse:fdoa_zone_location");
		  				  Element fdoaZoneElmnt = (Element) fdoaZoneElmntLst.item(0);
					      NodeList fdoaZone = fdoaZoneElmnt.getChildNodes();
					      System.out.println("PR Receive fdoaZone "+((Node) fdoaZone.item(0)).getNodeValue());
					      if(fdoaZone.item(0)!=null){
					    	  if(((Node) fdoaZone.item(0)).getNodeValue()!=null &&  (!((Node) fdoaZone.item(0)).getNodeValue().equalsIgnoreCase(""))){
					    		  scmXMLDtoObjCurr.setFdoaZoneLoc(((Node) fdoaZone.item(0)).getNodeValue().trim());  
						      }else{
						    	  scmXMLDtoObjCurr.setFdoaZoneLoc("");
						      }
				    	  }else{
				    		  scmXMLDtoObjCurr.setFdoaZoneLoc("");
				    	  }
					      
					    //to get header Desc
		  				  NodeList headerDescElmntLst = xmlElmnt.getElementsByTagName("PRResponse:header_description");
		  				  Element headerDescElmnt = (Element) headerDescElmntLst.item(0);
					      NodeList headerDesc = headerDescElmnt.getChildNodes();
					      System.out.println("PR Receive headerDesc "+((Node) headerDesc.item(0)).getNodeValue());
					      if(headerDesc.item(0)!=null){
					    	  if(((Node) headerDesc.item(0)).getNodeValue()!=null &&  (!((Node) headerDesc.item(0)).getNodeValue().equalsIgnoreCase(""))){
					    		  scmXMLDtoObjCurr.setHeaderDesc(((Node) headerDesc.item(0)).getNodeValue().trim());  
						      }else{
						    	  scmXMLDtoObjCurr.setHeaderDesc("");
						      }
				    	  }else{
				    		  scmXMLDtoObjCurr.setHeaderDesc("");
				    	  }
					      
					    //to get iB2B Remarks
		  				  NodeList iB2BRemrksElmntLst = xmlElmnt.getElementsByTagName("PRResponse:ib2b_remarks");
		  				  Element iB2BRemrksElmnt = (Element)iB2BRemrksElmntLst.item(0);
					      NodeList iB2BRemrks = iB2BRemrksElmnt.getChildNodes();
					      System.out.println("PR Receive iB2BRemrks "+((Node) iB2BRemrks.item(0)).getNodeValue());
					      if(iB2BRemrks.item(0)!=null){
					    	  if(((Node) iB2BRemrks.item(0)).getNodeValue()!=null &&  (!((Node) iB2BRemrks.item(0)).getNodeValue().equalsIgnoreCase(""))){
					    		  scmXMLDtoObjCurr.setIb2bRemarks(((Node) iB2BRemrks.item(0)).getNodeValue().trim());  
						      }else{
						    	  scmXMLDtoObjCurr.setIb2bRemarks("");
						      }
				    	  }else{
				    		  scmXMLDtoObjCurr.setIb2bRemarks("");
				    	  }
					      
					    //to get need_by_date 
		  				  NodeList needByDateElmntLst = xmlElmnt.getElementsByTagName("PRResponse:need_by_date");
		  				  Element needByDateElmnt = (Element)needByDateElmntLst.item(0);
					      NodeList needByDate = needByDateElmnt.getChildNodes();
					      System.out.println("PR Receive needByDate "+((Node) needByDate.item(0)).getNodeValue());
					      if(needByDate.item(0)!=null){
					    	  if(((Node) needByDate.item(0)).getNodeValue()!=null &&  (!((Node) needByDate.item(0)).getNodeValue().equalsIgnoreCase(""))){
					    		  scmXMLDtoObjCurr.setNeedByDate(((Node) needByDate.item(0)).getNodeValue().trim());  
					    		 // scmXMLDtoObjCurr.setNeedByDate(formatDateResponse(scmXMLDtoObjCurr.getNeedByDate()));
						      }else{
						    	  scmXMLDtoObjCurr.setNeedByDate("");
						      }
				    	  }else{
				    		  scmXMLDtoObjCurr.setNeedByDate("");
				    	  }
					      
					    //to get preparer
		  				  NodeList preparerElmntLst = xmlElmnt.getElementsByTagName("PRResponse:preparer");
		  				  Element preparerElmnt = (Element)preparerElmntLst.item(0);
					      NodeList preparer = preparerElmnt.getChildNodes();
					      System.out.println("PR Receive preparer "+((Node) preparer.item(0)).getNodeValue());
					      if(preparer.item(0)!=null){
					    	  if(((Node) preparer.item(0)).getNodeValue()!=null &&  (!((Node) preparer.item(0)).getNodeValue().equalsIgnoreCase(""))){
					    		  scmXMLDtoObjCurr.setPreparer(((Node) preparer.item(0)).getNodeValue().trim());  
						      }else{
						    	  scmXMLDtoObjCurr.setPreparer("");
						      }
				    	  }else{
				    		  scmXMLDtoObjCurr.setPreparer("");
				    	  }
					      
					      //to get requisition line id
					      NodeList reqLineIdElmntLst = xmlElmnt.getElementsByTagName("PRResponse:requisition_line_id");
					      Element reqLineIdElmnt = (Element) reqLineIdElmntLst.item(0);
				    	  NodeList reqLineId = reqLineIdElmnt.getChildNodes();
				    	  System.out.println("PR Receive reqLineId "+((Node) reqLineId.item(0)).getNodeValue());
				    	  if(reqLineId.item(0)!=null){
					    	  if(((Node) reqLineId.item(0)).getNodeValue()!=null &&  (!((Node) reqLineId.item(0)).getNodeValue().equalsIgnoreCase(""))){
					    		  prCreatnSCMXmlDtoObj.setScmLineId(Integer.parseInt(((Node) reqLineId.item(0)).getNodeValue().trim()));  
						      }else{
						    	  prCreatnSCMXmlDtoObj.setScmLineId(Integer.parseInt("0"));
						      }
				    	  }else{
				    		  prCreatnSCMXmlDtoObj.setScmLineId(Integer.parseInt("0"));
				    	  }	 
					      if(s==0){
						      attrMap.put("buying_type_h_att6", scmXMLDtoObjCurr.getBuyingType());
						      attrMap.put("circle", scmXMLDtoObjCurr.getCircle());
						      attrMap.put("deliver_to_requestor", scmXMLDtoObjCurr.getDelToRequestor());
						      attrMap.put("fdoa_zone_location_h_att10", scmXMLDtoObjCurr.getFdoaZoneLoc());
						      attrMap.put("fdoa_capex_opex_h_att11", scmXMLDtoObjCurr.getFdoaCapOpType());
						      attrMap.put("header_description", scmXMLDtoObjCurr.getHeaderDesc());
						      attrMap.put("ib2b_remarks", scmXMLDtoObjCurr.getIb2bRemarks());
						      attrMap.put("need_by_date", scmXMLDtoObjCurr.getNeedByDate());
						      attrMap.put("nfa_number_l_att7", scmXMLDtoObjCurr.getNfaNumber());
						      attrMap.put("creation_date", scmXMLDtoObjCurr.getPrDate());
						      attrMap.put("preparer", scmXMLDtoObjCurr.getPreparer());
     			  			  System.out.println("last size"+scmXMLDtoObjCurr.getPrCreatnSCMXmlDtoList().size());
					    	  scmXMLDtoObjCurr.setAttrMap(attrMap);
				  			}
					      scmXMLDtoObjCurr.getPrCreatnSCMXmlDtoList().add(prCreatnSCMXmlDtoObj);
		  			  }
			 	}
			 returnStatus=AppConstants.SCM_XML_PROCESSED_SUCCESS;
			 }catch(Exception exc){
				 returnStatus=AppConstants.SCM_XML_PROCESSED_FAILURE;
				 Utility.onEx_LOG_RET_NEW_EX(exc, "processXmlForCreate", "SCMBL", null, true, true);
			 }
		Utility.LOG(true,false,"Exiting processXmlForCreate");
		 return returnStatus;
	}
	
	/**
	 * This method will save status in IOE.TPOSERVICEDETAILS table
	 * @param conn, message(message to be saved), readResponseXMLId(file id of received file)
	 * @return returnStatus
    */
	public int eiMessageUpdate(Connection conn,String message,int readResponseXMLId){
		Utility.LOG(true,false,"Entering eiMessageUpdate");
		int returnStatus=0;
		try{
			if(message!=null){
				if(message.length()>25){
					message=message.substring(0, 24);
				}
			}
			returnStatus=scmDaoObj.updateEiRcvdStat(conn,  message,readResponseXMLId);

		}catch(Exception exc){
			returnStatus=AppConstants.SCM_XML_PROCESSED_FAILURE;
			Utility.onEx_LOG_RET_NEW_EX(exc, "eiMessageUpdate", "SCMBL", null, true, true);
		}
		Utility.LOG(true,false,"Exiting eiMessageUpdate");
		return returnStatus;
	}
	
	
	
	/**
	 * This method will process xml received in case validation failed at SCM end
	 * @param doc(xml received from SCM),scmXMLDtoObjCurr(object having details of PR)
	 * @return returnStatus
    */
	public int processInvalidXmlForCreate(Document doc,SCMRecXmlDto scmXMLDtoObjCurr){
		Utility.LOG(true,false,"Entering processInvalidXmlForCreate");
		int returnStatus=0;
		try{
			NodeList nodeLstLine = doc.getElementsByTagName("Line");
			int trxIdInt=0;
			StringBuilder prStatMess=new StringBuilder();
			HashMap<String,String> attrMap=new HashMap<String,String>(); 
			for (int s = 0; s < nodeLstLine.getLength(); s++) {
	  		  Node fstNode = nodeLstLine.item(s);
				  if (fstNode.getNodeType() == Node.ELEMENT_NODE){
					  Element xmlElmnt = (Element) fstNode;	  
						  NodeList trxIdElmntLst = xmlElmnt.getElementsByTagName("PRResponse:transaction_id");
						  Element trxIdElmnt = (Element) trxIdElmntLst.item(0);
				    	  NodeList trxId = trxIdElmnt.getChildNodes();
				    	  System.out.println("PR Receive transaction_id "+((Node) trxId.item(0)).getNodeValue());
				    	  if(trxId.item(0)!=null){
					    	  if(((Node) trxId.item(0)).getNodeValue()!=null &&  (!((Node) trxId.item(0)).getNodeValue().equalsIgnoreCase(""))){
					    		  trxIdInt=Integer.parseInt(((Node) trxId.item(0)).getNodeValue().trim());  
						      }
				    	  }
				    	  prStatMess=new StringBuilder();
				    	  NodeList prStatElmntLst = xmlElmnt.getElementsByTagName("PRResponse:message");
						  Element prStatElmnt = (Element) prStatElmntLst.item(0);
				    	  NodeList prStat = prStatElmnt.getChildNodes();
				    	  System.out.println("PR Receive transaction_id "+((Node) prStat.item(0)).getNodeValue());
				    	  if(prStat.item(0)!=null){
					    	  if(((Node) prStat.item(0)).getNodeValue()!=null &&  (!((Node) prStat.item(0)).getNodeValue().equalsIgnoreCase(""))){
					    		  prStatMess.append(((Node) prStat.item(0)).getNodeValue().trim());  
						      }else{
						    	  prStatMess.append(AppConstants.MESSAGE_NOT_REC);
						      }
				    	  }else{
				    		  prStatMess.append(AppConstants.MESSAGE_NOT_REC);
				    	  }	 
	
		  			  }
				  attrMap.put(String.valueOf(trxIdInt), prStatMess.toString());
				  
			 }
			scmXMLDtoObjCurr.setAttrMap(attrMap);
			returnStatus=AppConstants.SCM_XML_PROCESSED_SUCCESS;
		}catch(Exception exc){
			returnStatus=AppConstants.SCM_XML_PROCESSED_FAILURE;
			Utility.onEx_LOG_RET_NEW_EX(exc, "processInvalidXmlForCreate", "SCMBL", null, true, true);
		}
		Utility.LOG(true,false,"Exiting processInvalidXmlForCreate");
		return returnStatus;
	}
	
	/**
	 * This method will save xml tags values in java object in case PR Reuse is successful at SCM end
	 * @param doc(xml received from SCM),operatnName,scmXMLDtoObjCurr(object having details of PR)
	 * @return returnStatus
    */
	public int processXmlForReuse(Document doc,String operatnName,SCMRecXmlDto scmXMLDtoObjCurr){
		Utility.LOG(true,false,"Entering processXmlForReuse");
		int returnStatus=0;
		try{
			NodeList nodeLstLine = doc.getElementsByTagName("PRReuse:PRReuse");
			scmXMLDtoObjCurr.setPrCreatnSCMXmlDtoList(new ArrayList<PRCreatnSCMXmlDto>());
			scmXMLDtoObjCurr.setTotalLine(nodeLstLine.getLength());
			  Node fstNode = nodeLstLine.item(0);
			  if (fstNode.getNodeType() == Node.ELEMENT_NODE){
				  Element xmlElmnt = (Element) fstNode;
		    	  System.out.println("PR Receive operatnName "+scmXMLDtoObjCurr.getOperatnExec());
			     //to get PR number
			      NodeList prNumElmntLst = xmlElmnt.getElementsByTagName("PRReuse:pr_number");
				  Element prNumElmnt = (Element) prNumElmntLst.item(0);
			      NodeList prNum = prNumElmnt.getChildNodes();
			      System.out.println("PR Receive prNum"+((Node) prNum.item(0)).getNodeValue());
			      if(prNum.item(0)!=null){
			    	  if(((Node) prNum.item(0)).getNodeValue()!=null &&  (!((Node) prNum.item(0)).getNodeValue().equalsIgnoreCase(""))){
			    		  scmXMLDtoObjCurr.setPrNumber(((Node) prNum.item(0)).getNodeValue().trim());  
				      }else{
				    	  scmXMLDtoObjCurr.setPrNumber("");
				      }
		    	  }else{
		    		  scmXMLDtoObjCurr.setPrNumber("");
		    	  }
					      
			      //to get circle
				  NodeList circleElmntLst = xmlElmnt.getElementsByTagName("PRReuse:circle");
				  Element circleElmnt = (Element) circleElmntLst.item(0);
			      NodeList circle = circleElmnt.getChildNodes();
			      System.out.println("PR Receive circle "+((Node) circle.item(0)).getNodeValue());
			      if(circle.item(0)!=null){
			    	  if(((Node) circle.item(0)).getNodeValue()!=null &&  (!((Node) circle.item(0)).getNodeValue().equalsIgnoreCase(""))){
			    		  scmXMLDtoObjCurr.setCircle(((Node) circle.item(0)).getNodeValue().trim());  
				      }else{
				    	  scmXMLDtoObjCurr.setCircle("");
				      }
		    	  }else{
		    		  scmXMLDtoObjCurr.setCircle("");
		    	  }
			  }      
			  returnStatus=AppConstants.SCM_XML_PROCESSED_SUCCESS;
			}catch(Exception exc){
				returnStatus=AppConstants.SCM_XML_PROCESSED_FAILURE;
				Utility.onEx_LOG_RET_NEW_EX(exc, "processXmlForReuse", "SCMBL", null, true, true);
			}
			Utility.LOG(true,false,"Exiting processXmlForReuse");
			return returnStatus;
		}
	
	/**
	 * This method will update data in tables in case New PR Creation
	 * @param conn,scmXMLDtoObjCurr(object having details of PR)
	 * @return returnStatus
    */
	public  int updateSCMXMLDataPrReuse(Connection conn,SCMRecXmlDto scmXMLDtoObjCurr) {
		Utility.LOG(true,false,"Entering updateSCMXMLDataPrReuse");
		int returnStatus=0;
		try{
			returnStatus=scmDaoObj.updateSCMXMLDataPrReuse( conn,scmXMLDtoObjCurr);
			if(returnStatus==AppConstants.SCM_XML_PROCESSED_SUCCESS){
				//to get sub change type
				ArrayList<LineItemValueDTO> spidLst=new ArrayList<LineItemValueDTO>();
				spidLst=scmDaoObj.getLineItemDtls(conn, scmXMLDtoObjCurr.getOrderNumber());
				if(spidLst!=null && spidLst.size()>0){
					LineItemValueDTO lnItmValDTOObj=new LineItemValueDTO();
					int lstSize=spidLst.size();
					for(int i=0;i<lstSize;i++){
						lnItmValDTOObj=(LineItemValueDTO)spidLst.get(i);
						if(lnItmValDTOObj.getSpId()==scmXMLDtoObjCurr.getServicePrdId() && 
								(lnItmValDTOObj.getSubChangeType()==AppConstants.PERMANENT_DISCONNECTION_ORDER_TYPE ||
										lnItmValDTOObj.getSubChangeType()==AppConstants.RATE_RENEWAL_ORDER_TYPE)){
							//send mail in case of disconnection and rate renewal order
							sendMailForTPChgOrder(conn,scmXMLDtoObjCurr,lnItmValDTOObj.getSubChangeType());
						}
					}
				}
			}
		}catch (Exception e) {
			returnStatus=AppConstants.SCM_XML_PROCESSED_FAILURE;
		    Utility.onEx_LOG_RET_NEW_EX(e, "updateSCMXMLDataPrReuse", "SCMBL", null, true, true);
		}
		Utility.LOG(true,false,"Exiting updateSCMXMLDataPrReuse");
    	return returnStatus;
		
	}
	
	/**
	 * This method will get XML received from SCM
	 * @param conn
	 * @return HashMap<Integer,String> (file Id,file Name)
    */
	public  HashMap<Integer,String> getNextReceiveXml(Connection conn) {
		Utility.LOG(true,false,"Entering getNextReceiveXml");
		HashMap<Integer,String> recXmlMap=new HashMap<Integer,String>();
		try{
			recXmlMap=scmDaoObj.getNextReceiveXml( conn);
		}catch (Exception e) {
		    Utility.onEx_LOG_RET_NEW_EX(e, "getNextReceiveXml", "SCMBL", null, true, true);
		}
		Utility.LOG(true,false,"Exiting getNextReceiveXml");
		return recXmlMap;
		
	}
	
	/**
	 * This method will update status  in IOE.TSCM_XMLRECEIVE_STATUS table whether successfully processed or any failure
	 * @param conn
	 * @return Nothing.
    */
	public void updateRecXmlStatus(Connection conn,int recId,int status){
		Utility.LOG(true,false,"Entering updateRecXmlStatus");
		try{
			
			scmDaoObj.updateRecXmlStatus( conn,recId,status);
		}catch (Exception e) {
		    Utility.onEx_LOG_RET_NEW_EX(e, "updateRecXmlStatus", "SCMBL", null, true, true);
		}
		Utility.LOG(true,false,"Exiting updateRecXmlStatus");
	}
	
	/**
	 * This method will update order number and service product id in IOE.TSCM_XMLRECEIVE_STATUS table
	 * @param  conn, orderNumber, spId, recId(file Id)
	 * @return returnStatus
    */
	public int updateRecXmlDtls(Connection conn,int orderNumber,int spId,int recId,String nfaNumber,String operatnExec){
		Utility.LOG(true,false,"Entering updateRecXmlDtls");
		int returnStatus=0;
		try{
			
			returnStatus=scmDaoObj.updateRecXmlDtls( conn,orderNumber,spId,recId, nfaNumber,operatnExec);
		}catch (Exception e) {
			returnStatus=AppConstants.SCM_XML_PROCESSED_FAILURE;
		    Utility.onEx_LOG_RET_NEW_EX(e, "updateRecXmlDtls", "SCMBL", null, true, true);
		}
		Utility.LOG(true,false,"Exiting updateRecXmlDtls");
		return returnStatus;
	}
	
	/**
	 * This method will save NFA number for the XML received from SCM in java object whether fail or success at SCM
	 * @param  doc,operatnName,scmXMLDtoObjCurr
	 * @return returnStatus
    */
	public int getNfaNumber(Document doc,String operatnName,SCMRecXmlDto scmXMLDtoObjCurr){
		Utility.LOG(true,false,"Entering getNfaNumber");
		int returnStatus=0;
		int counter=0;
		Utility.LOG(true,false,"operatnName   : "+operatnName);
		try{
			StringBuilder nfaNumber=new StringBuilder();
			if(scmXMLDtoObjCurr.getOperatnExec().equalsIgnoreCase(AppConstants.PR_CREATION) || scmXMLDtoObjCurr.getOperatnExec().equalsIgnoreCase(AppConstants.PR_RENEWAL)){
				NodeList nodeLstLine = doc.getElementsByTagName("Line");
		    		  Node fstNode = nodeLstLine.item(0);
		  			  if (fstNode.getNodeType() == Node.ELEMENT_NODE){
		  				  Element xmlElmnt = (Element) fstNode;
				    	  //to get NFA number
					      NodeList nfaNumElmntLst = xmlElmnt.getElementsByTagName("PRResponse:nfa_number");
						  Element nfaNumElmnt = (Element) nfaNumElmntLst.item(0);
					      NodeList nfaNum = nfaNumElmnt.getChildNodes();
					      System.out.println("PR Receive nfa_number_l_att7 "+((Node) nfaNum.item(0)).getNodeValue());
					      if(nfaNum.item(0)!=null){
						    	  if(((Node) nfaNum.item(0)).getNodeValue()!=null &&  (!((Node) nfaNum.item(0)).getNodeValue().equalsIgnoreCase(""))){
					    		  nfaNumber.append(((Node) nfaNum.item(0)).getNodeValue().trim());  
						      }else{
						    	  nfaNumber.append("");
						      }
				    	  }else{
				    		  nfaNumber.append("");
				    	  }
					      scmXMLDtoObjCurr.setNfaNumber(nfaNumber.toString());
					 }
	    	  }else if(scmXMLDtoObjCurr.getOperatnExec().equalsIgnoreCase(AppConstants.CIRCUIT_ID_UPDATE)){
	    		//to get NFA number
			      NodeList nfaNumElmntLst = doc.getElementsByTagName("PRCircuit:nfa_number");
				  Element nfaNumElmnt = (Element) nfaNumElmntLst.item(0);
			      NodeList nfaNum = nfaNumElmnt.getChildNodes();
			      System.out.println("PRCircuit:nfa_number "+((Node) nfaNum.item(0)).getNodeValue());
			      if(nfaNum.item(0)!=null){
				    	  if(((Node) nfaNum.item(0)).getNodeValue()!=null &&  (!((Node) nfaNum.item(0)).getNodeValue().equalsIgnoreCase(""))){
			    		  nfaNumber.append(((Node) nfaNum.item(0)).getNodeValue().trim());  
				      }else{
				    	  nfaNumber.append("");
				      }
		    	  }else{
		    		  nfaNumber.append("");
		    	  }
			      scmXMLDtoObjCurr.setNfaNumber(nfaNumber.toString());
	    	  }else if(scmXMLDtoObjCurr.getOperatnExec().equalsIgnoreCase(AppConstants.PR_REUSE)){
				//to get NFA number
			      NodeList nfaNumElmntLst = doc.getElementsByTagName("PRReuse:new_nfa_number");
				  Element nfaNumElmnt = (Element) nfaNumElmntLst.item(0);
			      NodeList nfaNum = nfaNumElmnt.getChildNodes();
			      System.out.println("PRReuse:new_nfa_number "+((Node) nfaNum.item(0)).getNodeValue());
			      if(nfaNum.item(0)!=null){
				    	  if(((Node) nfaNum.item(0)).getNodeValue()!=null &&  (!((Node) nfaNum.item(0)).getNodeValue().equalsIgnoreCase(""))){
			    		  nfaNumber.append(((Node) nfaNum.item(0)).getNodeValue().trim());  
				      }else{
				    	  nfaNumber.append("");
				      }
		    	  }else{
		    		  nfaNumber.append("");
		    	  }
			      scmXMLDtoObjCurr.setNfaNumber(nfaNumber.toString());
			      
				//to get old NFA number
			      StringBuilder nfaNumberOld=new StringBuilder();
			      NodeList nfaNumElmntOldLst = doc.getElementsByTagName("PRReuse:oldnfa_number");
				  Element nfaNumOldElmnt = (Element) nfaNumElmntOldLst.item(0);
			      NodeList nfaNumOld = nfaNumOldElmnt.getChildNodes();
			      System.out.println("PRReuse:OLD_NFA_NUMBER "+((Node) nfaNumOld.item(0)).getNodeValue());
			      nfaNumberOld=new StringBuilder();
			      if(nfaNumOld.item(0)!=null){
			    	  if(((Node) nfaNumOld.item(0)).getNodeValue()!=null &&  (!((Node) nfaNumOld.item(0)).getNodeValue().equalsIgnoreCase(""))){
			    		  nfaNumberOld.append(((Node) nfaNumOld.item(0)).getNodeValue().trim());  
				      }else{
				    	  nfaNumberOld.append("");
					  }
			  	  }else{
			  		nfaNumberOld.append("");
			  	  }
			      counter=0;
			      if(nfaNumberOld!=null && !"".equalsIgnoreCase(nfaNumberOld.toString())){
					StringTokenizer token=new StringTokenizer(nfaNumberOld.toString(),AppConstants.NFA_SEPARATOR);
					while(token.hasMoreTokens()){
						if(counter==0){
							scmXMLDtoObjCurr.setOldOrderNumber(Integer.parseInt(token.nextElement().toString()));
						}
						if(counter==1){
							scmXMLDtoObjCurr.setOldServiceId(Integer.parseInt(token.nextElement().toString()));
						}
						if(counter==2){
							scmXMLDtoObjCurr.setOldServicePrdId(Integer.parseInt(token.nextElement().toString()));
						}
						if(counter==3){
							scmXMLDtoObjCurr.setOldM6OrderNumber(Integer.parseInt(token.nextElement().toString()));
						}
						counter++;
					} 
					 System.out.println("PR Receive setOldOrderNumber "+scmXMLDtoObjCurr.getOldOrderNumber());
					 System.out.println("PR Receive setOldServiceId "+scmXMLDtoObjCurr.getOldServiceId());
					 System.out.println("PR Receive setOldServicePrdId "+scmXMLDtoObjCurr.getOldServicePrdId());
			      }
			  }	
		      counter=0;
		      Utility.LOG(true,false,"NFA Number   : "+scmXMLDtoObjCurr.getNfaNumber());
			  if(nfaNumber!=null && !"".equalsIgnoreCase(nfaNumber.toString())){
				StringTokenizer token=new StringTokenizer(nfaNumber.toString(),AppConstants.NFA_SEPARATOR);
				while(token.hasMoreTokens()){
					if(counter==0){
						scmXMLDtoObjCurr.setOrderNumber(Integer.parseInt(token.nextElement().toString()));
					}
					if(counter==1){
						scmXMLDtoObjCurr.setServiceId(Integer.parseInt(token.nextElement().toString()));
					}
					if(counter==2){
						scmXMLDtoObjCurr.setServicePrdId(Integer.parseInt(token.nextElement().toString()));
					}
					if(counter==3){
						scmXMLDtoObjCurr.setM6OrderNumber(Integer.parseInt(token.nextElement().toString()));
					}
					counter++;
				} 
				Utility.LOG(true,false,"PR Receive setOrderNumber "+scmXMLDtoObjCurr.getOrderNumber());
				Utility.LOG(true,false,"PR Receive setServiceId "+scmXMLDtoObjCurr.getServiceId());
				Utility.LOG(true,false,"PR Receive setServicePrdId "+scmXMLDtoObjCurr.getServicePrdId());
			  }
	  
		returnStatus=AppConstants.SCM_XML_PROCESSED_SUCCESS;
		}catch (Exception e) {
			returnStatus=AppConstants.SCM_XML_PROCESSED_FAILURE;
		    Utility.onEx_LOG_RET_NEW_EX(e, "getNfaNumber", "SCMBL", null, true, true);
		}
		Utility.LOG(true,false,"Exiting getNfaNumber");
		return returnStatus;
		}
	
	/**
	 * This method will update tables in DB if there is error at SCM end in case of PR Reuse
	 * @param  doc,operatnName,scmXMLDtoObjCurr
	 * @return returnStatus
    */
	public int updateDBForReuseInvalidXml(Connection conn,SCMRecXmlDto scmRecXmlDtoObj,int readResponseXMLId){
		Utility.LOG(true,false,"Entering updateDBForReuseInvalidXml");
		int returnStatus=0;
		StringBuilder scmProgStat=new StringBuilder();
		int prId=0;
		int servicePrdId=0;
		HashMap<String,String> messMap=new HashMap<String,String>();

			if(scmRecXmlDtoObj.getErrorMess()!=null){
				if(scmRecXmlDtoObj.getErrorMess().equalsIgnoreCase(AppConstants.PR_PROCESSED_SUCCESS)){
					scmProgStat.append(AppConstants.PR_SCM_SUCCESS);
				}else{
					scmProgStat.append(AppConstants.PR_SCM_REUSE_FAILURE);
				}
			}
		try{
			//conditions to be handled when order is canceled but reuse has error...to do
			//get spId
			servicePrdId=scmDaoObj.getSpId(conn, readResponseXMLId);
			//set spid
			scmRecXmlDtoObj.setServicePrdId(servicePrdId);
			//get item code details
			ArrayList<PRCreatnSCMXmlDto> prCreatnSCMXmlLst=new ArrayList<PRCreatnSCMXmlDto>();
			prCreatnSCMXmlLst=scmDaoObj.getItemCodeDtlsValues(conn,   scmRecXmlDtoObj.getServicePrdId());
			PRCreatnSCMXmlDto prCreatnSCMXmlDtoObj=new PRCreatnSCMXmlDto();
			for(int i=0;i<prCreatnSCMXmlLst.size();i++){
				prCreatnSCMXmlDtoObj=(PRCreatnSCMXmlDto)prCreatnSCMXmlLst.get(i);
				messMap.put(String.valueOf(prCreatnSCMXmlDtoObj.getTrxId()), scmRecXmlDtoObj.getErrorMess());
				prId=prCreatnSCMXmlDtoObj.getPrId();
			}
			
			returnStatus=scmDaoObj.updateScmRcvdStat(conn,  scmRecXmlDtoObj.getServicePrdId()
					,  scmProgStat.toString(),prId,messMap);
			

		}catch (Exception e) {
			returnStatus=AppConstants.SCM_XML_PROCESSED_FAILURE;;
			Utility.onEx_LOG_RET_NEW_EX(e, "updateDBForReuseInvalidXml", "SCMBL", null, true, true);
		}
		Utility.LOG(true,false,"Exiting updateDBForReuseInvalidXml");
		return returnStatus;
	}
	
	/**
	 * This method will update tables in DB if there is error at SCM end in case of PR Creation
	 * @param  doc,operatnName,scmXMLDtoObjCurr
	 * @return returnStatus
    */
	public int updateDbForCreateInvalidXml(Connection conn,SCMRecXmlDto scmXMLDtoObjCurr){
		Utility.LOG(true,false,"Entering updateDbForCreateInvalidXml");
		int returnStatus=0;
		try{
			StringBuilder scmProgStat=new StringBuilder();
			if(scmXMLDtoObjCurr.getErrorMess()!=null && !scmXMLDtoObjCurr.getErrorMess().equalsIgnoreCase("")){
				if(scmXMLDtoObjCurr.getErrorMess().equalsIgnoreCase(AppConstants.PR_PROCESSED_SUCCESS)){
					scmProgStat.append(AppConstants.PR_SCM_SUCCESS);
				}else if(scmXMLDtoObjCurr.getErrorMess().equalsIgnoreCase(AppConstants.PR_PROCESSED_FAILURE)){
					scmProgStat.append(AppConstants.PR_SCM_VALIDATION_FAILURE);
				}
			}
			
			
				returnStatus=scmDaoObj.updateScmRcvdStat(conn,  scmXMLDtoObjCurr.getServicePrdId(),
					scmProgStat.toString(),0,scmXMLDtoObjCurr.getAttrMap());
			
		}catch (Exception e) {
			returnStatus=AppConstants.SCM_XML_PROCESSED_FAILURE;
			Utility.onEx_LOG_RET_NEW_EX(e, "updateDbForCreateInvalidXml", "SCMBL", null, true, true);
		}
		Utility.LOG(true,false,"Exiting updateDbForCreateInvalidXml");
		return returnStatus;
	}	
	
	/**
	 * This method will get service product id on the basis of received file id from send xml table
	 * @param  conn,readResponseXMLId
	 * @return returnStatus
    */
	public  int getSpId(Connection conn,int readResponseXMLId) {
		Utility.LOG(true,false,"Entering getSpId");
		int servicePrdId=0;
		try{
			servicePrdId=scmDaoObj.getSpId(conn, readResponseXMLId);
			
		}catch (Exception e) {
		    Utility.onEx_LOG_RET_NEW_EX(e, "getSpId", "SCMBL", null, true, true);
		}
		Utility.LOG(true,false,"Exiting getSpId");
		return servicePrdId;
		
	}
	
	/**
	 * This method will create xml for circuit update in case of 9999 event
	 * @param conn,orderNumber,serviceId,eventTypeId, m6OrderId
	 * @return Nothing.
    */
	public void createScmCircuitXml(Connection conn,int orderNumber,int serviceId,String eventTypeId,String cktNumber,int spId){
		Utility.LOG(true,false,"Entering createScmCircuitXml");
		Utility.LOG(true,false,"Entering createScmCircuitXml orderNumber"+orderNumber);
		Utility.LOG(true,false,"Entering createScmCircuitXml serviceId"+serviceId);
		Utility.LOG(true,false,"Entering createScmCircuitXml circuitMap"+cktNumber);
		Utility.LOG(true,false,"Entering createScmCircuitXml m6OrderId"+spId);
		StringBuilder finalXml=new StringBuilder();
		StringBuilder operationExec=new StringBuilder();
		HashMap<String,String> currentPrDtlsMap=new HashMap<String,String>();
		SCMXMLDto scmXMLDtoObj=new SCMXMLDto();
		ArrayList<SCMXMLDto> scmXMLDtoLst=new ArrayList<SCMXMLDto>();
		String headerTemplate=new String();
		String footerTemplate=new String();
		String dataTemplate=new String();
		int returnStatus=0;
		try {
			headerTemplate=scmDaoObj.fetchXMLTemplate(conn,AppConstants.THIRD_PARTY_PR_CIRCUIT_HEADER_XML);
			footerTemplate=scmDaoObj.fetchXMLTemplate(conn,AppConstants.THIRD_PARTY_PR_CIRCUIT_FOOTER_XML);
			dataTemplate=scmDaoObj.fetchXMLTemplate(conn,AppConstants.THIRD_PARTY_PR_CIRCUIT_DATA_XML);
			if(cktNumber!=null && !cktNumber.equalsIgnoreCase("")){
				finalXml=new StringBuilder();
				operationExec=new StringBuilder();
				operationExec.append(AppConstants.CIRCUIT_ID_UPDATE);
				//create xml for Circuit Update
				//get Pr number and details
				currentPrDtlsMap=scmDaoObj.getCurrentPrDtls(conn,spId);
				if(currentPrDtlsMap!=null && currentPrDtlsMap.size()>0){
					finalXml.append(headerTemplate);
					finalXml.append(createFinalSCMXMLPrCircuit(currentPrDtlsMap,cktNumber,dataTemplate));
					finalXml.append(footerTemplate);
					//SET xml values in DTO
					scmXMLDtoObj.setOrderNumber(orderNumber);
					scmXMLDtoObj.setServiceId(serviceId);
					scmXMLDtoObj.setServicePrdId(spId);
					scmXMLDtoObj.setXml(finalXml.toString());
					scmXMLDtoObj.setOperatnExec(operationExec.toString());
					scmXMLDtoLst.add(scmXMLDtoObj);
				}
			}
			if(scmXMLDtoLst.size()>0){
				conn.setAutoCommit(false);
				//insert xml
				returnStatus=scmDaoObj.insertSCMXML(conn, scmXMLDtoLst, eventTypeId);
				if(returnStatus==AppConstants.SCM_XML_PROCESSED_SUCCESS){
					conn.commit();
				}
			}
			
		} catch (Exception e) {
			Utility.onEx_LOG_RET_NEW_EX(e, "createScmCircuitXml", "SCMBL", null, true, true);
		}finally{
			try {
				if(conn.getAutoCommit()==false){
					conn.setAutoCommit(true);
				}
			}catch (SQLException sql){
				returnStatus=AppConstants.SCM_XML_PROCESSED_FAILURE;
				Utility.onEx_LOG_RET_NEW_EX(sql, "updateSCMXMLDataPrCreate", "SCMDao", "sqlState", true, true);
			}
		}
		Utility.LOG(true,false,"Exiting createScmCircuitXml");	
	}
	
	/**
	 * This method will create xml for Circuit Update
	 * @param newNfa(New NFA Number Generated),oldPrDtlsMap(Old NFA Number PR Details)
	 * @return finalXml
    */
	private StringBuilder createFinalSCMXMLPrCircuit(HashMap<String,String> oldPrDtlsMap,String circuitNumber,String dataTemplate){
		Utility.LOG(true,false,"Entering createFinalSCMXMLPrCircuit");
		StringBuilder finalXml=new StringBuilder();
		String dataTemplateTemp=new String();
		try{
			dataTemplateTemp=dataTemplate;
			dataTemplateTemp=dataTemplateTemp.replaceAll("circuit_number_value", circuitNumber);
			dataTemplateTemp=dataTemplateTemp.replaceAll("nfa_number_value", oldPrDtlsMap.get("nfaNumber"));
			dataTemplateTemp=dataTemplateTemp.replaceAll("pr_number_value", oldPrDtlsMap.get("prNumber"));
			dataTemplateTemp=dataTemplateTemp.replaceAll("circle_value", oldPrDtlsMap.get("circle"));
			finalXml.append(dataTemplateTemp);
		}catch (Exception e) {
			Utility.onEx_LOG_RET_NEW_EX(e, "createFinalSCMXMLPrCircuit", "SCMBL", null, true, true);
		}
		Utility.LOG(true,false,"Exiting createFinalSCMXMLPrCircuit");
		return finalXml;
	}
	
	/**
	 * This method will create and send mail for change order
	 * @param newNfa(New NFA Number Generated),oldPrDtlsMap(Old NFA Number PR Details)
	 * @return finalXml
    */
	private static void  sendMailForTPChgOrder(Connection conn,SCMRecXmlDto scmXMLDtoObjCurr,int subChgType){
		Utility.LOG(true,false,"Entering sendMailForTPChgOrder");

		try{
			if(Integer.parseInt(Utility.getAppConfigValue("THIRD_PARTY_MAIL_ALERT_FLAG"))==1){
				Utility.LOG(true,false,"THIRD_PARTY_MAIL_ALERT_START   : ");
				String toList[]=null;
				String mailGroup=new String();
				String orderType=new String();
				boolean mailFlag=true;
				if(subChgType==AppConstants.PERMANENT_DISCONNECTION_ORDER_TYPE){
					 mailGroup=Utility.getAppConfigValue("THIRD_PARTY_DISCONNECT_ORDER_MAIL_GROUP");
					 orderType=AppConstants.PERMANENT_DISCONNECTION;
				}else if(subChgType==AppConstants.RATE_RENEWAL_ORDER_TYPE){
					mailGroup=Utility.getAppConfigValue("THIRD_PARTY_RATE_RENWAL_ORDER_MAIL_GROUP");
					orderType=AppConstants.RATE_RENEWAL;
				}
				toList=new String[]{mailGroup};
				IB2BMailDto objMailDto = new IB2BMailDto();
				objMailDto.setNfaNumber(String.valueOf(scmXMLDtoObjCurr.getNfaNumber()));
				objMailDto.setPrNumber(String.valueOf(scmXMLDtoObjCurr.getPrNumber()));
				objMailDto.setOrderType(orderType);
				objMailDto.setMailTemplateType(AppConstants.THIRD_PARTY_CHANGE_ORDER_MAIL);
				objMailDto.setTo(toList);
				IB2BMail.sendiB2BMail(objMailDto, conn,false,mailFlag);
				Utility.LOG(true,false,"THIRD_PARTY_MAIL_ALERT_END   : ");
			}
			
		}catch (Exception e) {
			Utility.onEx_LOG_RET_NEW_EX(e, "sendMailForTPChgOrder", "SCMBL", null, true, true);
		}
		Utility.LOG(true,false,"Exiting sendMailForTPChgOrder");
	}

	/**
	 * This method will create xml,update nfa number and insert created xml PR Reuse in case of 9999 event
	 * @param conn,orderNumber,serviceId,eventTypeId, m6OrderId
	 * @return Nothing.
    */
	public void createAndUpdateXmlPRReuse(Connection conn,HashMap<SCMXMLDto,SCMXMLDto> mapParam){
		Utility.LOG(true,false,"Entering createAndUpdateXmlPRReuse");
		StringBuilder finalXml=new StringBuilder();
		HashMap<String,String> prDtlsMap=new HashMap<String,String>();
		SCMXMLDto scmXMLDtoObj=new SCMXMLDto();
		ArrayList<SCMXMLDto> scmXMLDtoLst=new ArrayList<SCMXMLDto>();
		String headerTemplatePRReuse=new String();
		String footerTemplatePRReuse=new String();
		String dataTemplatePRReuse=new String();
		String headerTemplateCktUpdate=new String();
		String footerTemplateCktUpdate=new String();
		String dataTemplateCktUpdate=new String();
		SCMXMLDto tmpObj=new SCMXMLDto();
		String cktNumber=new String();
		int returnStatus=0;
		try {
			headerTemplatePRReuse=scmDaoObj.fetchXMLTemplate(conn,AppConstants.THIRD_PARTY_PR_REUSE_HEADER_XML);
			footerTemplatePRReuse=scmDaoObj.fetchXMLTemplate(conn,AppConstants.THIRD_PARTY_PR_REUSE_FOOTER_XML);
			dataTemplatePRReuse=scmDaoObj.fetchXMLTemplate(conn,AppConstants.THIRD_PARTY_PR_REUSE_DATA_XML);
			headerTemplateCktUpdate=scmDaoObj.fetchXMLTemplate(conn,AppConstants.THIRD_PARTY_PR_CIRCUIT_HEADER_XML);
			footerTemplateCktUpdate=scmDaoObj.fetchXMLTemplate(conn,AppConstants.THIRD_PARTY_PR_CIRCUIT_FOOTER_XML);
			dataTemplateCktUpdate=scmDaoObj.fetchXMLTemplate(conn,AppConstants.THIRD_PARTY_PR_CIRCUIT_DATA_XML);
			StringBuilder cktDesc=new StringBuilder(Utility.getAppConfigValue(AppConstants.CIRCUIT_ID_ATT_DESC_KEY));
			for (Map.Entry<SCMXMLDto,SCMXMLDto> entry : mapParam.entrySet()) {
				System.out.println("xmlMapReuse Key : " + entry.getKey() + " Value : "
					+ entry.getValue());
				try{	
					tmpObj=(SCMXMLDto)entry.getValue();
					scmXMLDtoObj=new SCMXMLDto();
					finalXml=new StringBuilder();
					scmXMLDtoObj.setXmlId(tmpObj.getXmlId());
					//get old and current nfa number
					prDtlsMap=scmDaoObj.getCurrentPrDtls(conn,tmpObj.getServicePrdId());
					if(prDtlsMap!=null && prDtlsMap.size()>0){
						//create xml for PR Reuse
						if(tmpObj.getOperatnExec()!=null && tmpObj.getOperatnExec().equalsIgnoreCase(AppConstants.PR_REUSE)){
							finalXml.append(headerTemplatePRReuse);
							finalXml.append(createFinalSCMXMLPrReuse(prDtlsMap,dataTemplatePRReuse));
							finalXml.append(footerTemplatePRReuse);
							//SET xml values in DTO
							scmXMLDtoObj.setXml(finalXml.toString());
							scmXMLDtoObj.setSend_status(AppConstants.STATUS_FOR_SUCCESS_XML_CREATION);
							tmpObj.setXml(finalXml.toString());
						}else if(tmpObj.getOperatnExec()!=null && tmpObj.getOperatnExec().equalsIgnoreCase(AppConstants.CIRCUIT_ID_UPDATE)){
							//create xml for circuit update
							finalXml.append(headerTemplateCktUpdate);
							cktNumber=scmDaoObj.getLineAttValue(conn,tmpObj.getServicePrdId(),cktDesc.toString());
							finalXml.append(createFinalSCMXMLPrCircuit(prDtlsMap,cktNumber,dataTemplateCktUpdate));
							finalXml.append(footerTemplateCktUpdate);
							//SET xml values in DTO
							scmXMLDtoLst.add(scmXMLDtoObj);
							scmXMLDtoObj.setXml(finalXml.toString());
							scmXMLDtoObj.setSend_status(AppConstants.STATUS_FOR_SUCCESS_XML_CREATION);
							tmpObj.setXml(finalXml.toString());
						}
					}
				}catch (Exception e) {
					scmXMLDtoObj.setSend_status(AppConstants.STATUS_FOR_FAILURE_XML_CREATION);
					tmpObj.setXml(AppConstants.XML_NOT_CREATED);
					Utility.onEx_LOG_RET_NEW_EX(e, "createSCMXML", "SCMBL", "Exception ", true, true);
				}
				mapParam.put(entry.getKey(), tmpObj);
				scmXMLDtoLst.add(scmXMLDtoObj);
			}
			//insert xml
			if(scmXMLDtoLst.size()>0){
				conn.setAutoCommit(false);
				returnStatus=scmDaoObj.updateSCMXML(conn, scmXMLDtoLst);
				if(returnStatus==AppConstants.SCM_XML_PROCESSED_SUCCESS){
					conn.commit();
				}
			}
				
		}catch (Exception e) {
			Utility.onEx_LOG_RET_NEW_EX(e, "createAndUpdateXmlPRReuse", "SCMBL", null, true, true);
		}finally
		{
			try {
				if(conn.getAutoCommit()==false){
					conn.setAutoCommit(true);
				}
			} catch (SQLException e) {
				 Utility.onEx_LOG_RET_NEW_EX(e, "insertInScmTableCircuitUpdate", "processResponseXML", "sqlState", true, true);  
			}catch (Exception e) {
				Utility.onEx_LOG_RET_NEW_EX(e, "insertInScmTableCircuitUpdate", "processResponseXML", null, true, true);
			}
		}		
		Utility.LOG(true,false,"Exiting createAndUpdateXmlPRReuse");	
	}
	
	/**
	 * This method will create xml,update nfa number and insert created xml PR Reuse in case of 9999 event
	 * @param conn,orderNumber,serviceId,eventTypeId, m6OrderId
	 * @return Nothing.
    */
	public int insertInScmTablePRReuse(Connection conn,int orderNumber,int serviceId,String eventTypeId,String m6OrderId){
		Utility.LOG(true,false,"Entering insertInScmTablePRReuse");
		ArrayList<LineItemValueDTO> spidLst=new ArrayList<LineItemValueDTO>();
		StringBuilder nfaNumNew=new StringBuilder();
		String operationExec=AppConstants.PR_REUSE;
		SCMXMLDto scmXMLDtoObj=new SCMXMLDto();
		ArrayList<SCMXMLDto> scmXMLDtoLst=new ArrayList<SCMXMLDto>();
		ArrayList<LineItemValueDTO> lnItmValDTOObjLst=new ArrayList<LineItemValueDTO>();
		int returnStatus=0;
		try {
			
			spidLst=scmDaoObj.getLineItemDtls(conn, orderNumber);
			if(spidLst!=null && spidLst.size()>0){
				StringBuilder nfaDesc=new StringBuilder(Utility.getAppConfigValue(AppConstants.NFA_NUMBER_ATT_DESC_KEY));
				LineItemValueDTO lnItmValDTOObj=new LineItemValueDTO();
				int lstSize=spidLst.size();
				for(int i=0;i<lstSize;i++){
					lnItmValDTOObj=(LineItemValueDTO)spidLst.get(i);
					if(lnItmValDTOObj.getIsPrReuse()==1 && lnItmValDTOObj.getPrId()!=0){
						scmXMLDtoObj=new SCMXMLDto();
						nfaNumNew=new StringBuilder();
						nfaNumNew.append(orderNumber);
						nfaNumNew.append(AppConstants.NFA_SEPARATOR);
						nfaNumNew.append(serviceId);
						nfaNumNew.append(AppConstants.NFA_SEPARATOR);
						nfaNumNew.append(lnItmValDTOObj.getSpId());
						nfaNumNew.append(AppConstants.NFA_SEPARATOR);
						nfaNumNew.append(m6OrderId);
						//SET values values in DTO
						lnItmValDTOObj.setAttValue(nfaNumNew.toString());
						Utility.LOG(true,false,"IsPrReuse"+lnItmValDTOObj.getIsPrReuse());
						Utility.LOG(true,false,"PrId"+lnItmValDTOObj.getPrId());
						//SET xml values in DTO
						scmXMLDtoObj.setOrderNumber(orderNumber);
						scmXMLDtoObj.setServiceId(serviceId);
						scmXMLDtoObj.setServicePrdId(lnItmValDTOObj.getSpId());
						scmXMLDtoObj.setOperatnExec(operationExec.toString());
						Utility.LOG(true,false,"scmXMLDtoObj.getScmProgressStatus()"+scmXMLDtoObj.getScmProgressStatus());
						scmXMLDtoLst.add(scmXMLDtoObj);
						lnItmValDTOObjLst.add(lnItmValDTOObj);
					}
				}
				
				//update nfa number
				if(lnItmValDTOObjLst.size()>0){
					returnStatus=scmDaoObj.updateAttVal(conn, lnItmValDTOObjLst,nfaDesc.toString());
				}
				conn.setAutoCommit(false);
				//insert xml
				if(returnStatus==AppConstants.SCM_XML_PROCESSED_SUCCESS && scmXMLDtoLst.size()>0){
					returnStatus=scmDaoObj.insertSCMXML(conn, scmXMLDtoLst, eventTypeId);
					if(returnStatus==AppConstants.SCM_XML_PROCESSED_SUCCESS){
						conn.commit();
					}
				}
				
			}
		}catch (Exception e) {
			returnStatus=AppConstants.SCM_XML_PROCESSED_FAILURE;
			Utility.onEx_LOG_RET_NEW_EX(e, "insertInScmTablePRReuse", "SCMBL", null, true, true);
		}finally{
			try {
				if(conn.getAutoCommit()==false){
					conn.setAutoCommit(true);
				}
			} catch (SQLException e) {
				 Utility.onEx_LOG_RET_NEW_EX(e, "insertInScmTablePRReuse", "processResponseXML", "sqlState", true, true);  
			}catch (Exception e) {
				Utility.onEx_LOG_RET_NEW_EX(e, "insertInScmTablePRReuse", "processResponseXML", null, true, true);
			}
		}		
		Utility.LOG(true,false,"Exiting insertInScmTablePRReuse");
		return returnStatus;
	}
	
	/**
	 * This method will create xml,update nfa number and insert created xml PR Reuse in case of 9999 event
	 * @param conn,orderNumber,serviceId,eventTypeId, m6OrderId
	 * @return Nothing.
    */
	public int insertInScmTableCircuitUpdate(Connection conn,int orderNumber,int serviceId,String eventTypeId,HashMap<Integer,String> circuitMap,int m6OrderId){
		Utility.LOG(true,false,"Entering insertInScmTableCircuitUpdate");
		Utility.LOG(true,false,"Entering insertInScmTableCircuitUpdate orderNumber"+orderNumber);
		Utility.LOG(true,false,"Entering insertInScmTableCircuitUpdate serviceId"+serviceId);
		Utility.LOG(true,false,"Entering insertInScmTableCircuitUpdate circuitMap"+circuitMap);
		Utility.LOG(true,false,"Entering insertInScmTableCircuitUpdate m6OrderId"+m6OrderId);
		StringBuilder operationExec=new StringBuilder();
		int spId=0;
		SCMXMLDto scmXMLDtoObj=new SCMXMLDto();
		ArrayList<SCMXMLDto> scmXMLDtoLst=new ArrayList<SCMXMLDto>();
		int returnStatus=0;
		try {
			for (Map.Entry<Integer,String> entry : circuitMap.entrySet()){
				spId=entry.getKey();
				operationExec=new StringBuilder();
				operationExec.append(AppConstants.CIRCUIT_ID_UPDATE);
				//SET xml values in DTO
				scmXMLDtoObj.setOrderNumber(orderNumber);
				scmXMLDtoObj.setServiceId(serviceId);
				scmXMLDtoObj.setServicePrdId(spId);
				scmXMLDtoObj.setOperatnExec(operationExec.toString());
				scmXMLDtoLst.add(scmXMLDtoObj);
			}
			if(scmXMLDtoLst.size()>0){
				conn.setAutoCommit(false);
				//insert xml
				returnStatus=scmDaoObj.insertSCMXML(conn, scmXMLDtoLst, eventTypeId);
				if(returnStatus==AppConstants.SCM_XML_PROCESSED_SUCCESS){
					conn.commit();
				}
			}
			
		} catch (Exception e) {
			returnStatus=AppConstants.SCM_XML_PROCESSED_FAILURE;
			Utility.onEx_LOG_RET_NEW_EX(e, "insertInScmTableCircuitUpdate", "SCMBL", null, true, true);
		}finally{
			try {
				if(conn.getAutoCommit()==false){
					conn.setAutoCommit(true);
				}
			} catch (SQLException e) {
				 Utility.onEx_LOG_RET_NEW_EX(e, "insertInScmTablePRReuse", "processResponseXML", "sqlState", true, true);  
			}catch (Exception e) {
				Utility.onEx_LOG_RET_NEW_EX(e, "insertInScmTablePRReuse", "processResponseXML", null, true, true);
			}
		}
		Utility.LOG(true,false,"Exiting insertInScmTableCircuitUpdate");
		return returnStatus;
	}


	}

			
