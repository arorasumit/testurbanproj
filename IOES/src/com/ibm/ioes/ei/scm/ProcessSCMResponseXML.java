package com.ibm.ioes.ei.scm;
//Tag Name Resource Name  Date		CSR No			Description
//[001]	 Kalpana	11-January-2014			New File to process xml  received from SCM third party

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.ibm.ioes.ei.scm.BL.SCMBL;
import com.ibm.ioes.ei.scm.dto.SCMRecXmlDto;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Messages;
import com.ibm.ioes.utilities.Utility;

public class ProcessSCMResponseXML {
	
	private SCMBL scmBLObj=new SCMBL();
	private int readResponseXMLId=0;
	private String absFileName=new String();

	
	/**
	 * This method will process xml  received from SCM
	 * @param Nothing.
	 * @return Nothing.
    */
	public  void processScmXml() {
		Utility.LOG(true,false,"Entering processScmXml");
		int returnStatusMain=0;
		int returnStatusFound=0;
		SCMRecXmlDto scmXMLDtoObjCurr=new SCMRecXmlDto();
		Connection conn=null;
		try{
			conn=DbConnection.getConnectionObject();
			if(Integer.parseInt(Utility.getAppConfigValue(AppConstants.IS_THIRD_PARTY_ACTIVE))==1){
				do
				{
					readResponseXMLId=0;
					returnStatusFound=fetchNextSCMXMLData(scmXMLDtoObjCurr,conn);
					System.out.println("readResponseXMLId"+readResponseXMLId);
					if(returnStatusFound==AppConstants.Fetch_Status_Found){
						returnStatusMain=returnStatusFound;
						returnStatusMain=processMainXmlContent(absFileName,scmXMLDtoObjCurr,conn);
						if(returnStatusMain==AppConstants.SCM_XML_PROCESSED_SUCCESS){
							//PR inserted in SCM table
							if(scmXMLDtoObjCurr.getOperatnExec()!=null && scmXMLDtoObjCurr.getOperatnExec().equalsIgnoreCase(AppConstants.PR_INSERTED)){
		    					StringBuilder scmProgStat=new StringBuilder();
							    if(scmXMLDtoObjCurr.getErrorMess() !=null && scmXMLDtoObjCurr.getErrorMess().equalsIgnoreCase(AppConstants.PR_INSERTED_SUCCESS)){
									scmProgStat.append(AppConstants.PR_EI_SUCCESS);
								}else{
									scmProgStat.append(AppConstants.PR_EI_FAILURE);
								}
							    returnStatusMain=scmBLObj.eiMessageUpdate(conn, scmProgStat.toString(),readResponseXMLId);
							}
							//Pr reuse invalid and valid
							if(scmXMLDtoObjCurr.getOperatnExec()!=null && scmXMLDtoObjCurr.getOperatnExec().equalsIgnoreCase(AppConstants.PR_REUSE)){
								if(scmXMLDtoObjCurr.getErrorCode()!=null && scmXMLDtoObjCurr.getErrorCode().equalsIgnoreCase(AppConstants.ERROR_CODE_FAIL)){
									returnStatusMain=scmBLObj.updateDBForReuseInvalidXml(conn,scmXMLDtoObjCurr,readResponseXMLId);
								}else if(scmXMLDtoObjCurr.getErrorCode()!=null && scmXMLDtoObjCurr.getErrorCode().equalsIgnoreCase(AppConstants.ERROR_CODE_SUCCESS)){
									returnStatusMain=scmBLObj.updateSCMXMLDataPrReuse(conn,scmXMLDtoObjCurr);
								}
							}
							//pr create or pr renewal
							if(scmXMLDtoObjCurr.getOperatnExec()!=null && !scmXMLDtoObjCurr.getOperatnExec().equalsIgnoreCase("") 
									&& scmXMLDtoObjCurr.getOperatnExec().equalsIgnoreCase(AppConstants.PR_CREATION) 
									|| scmXMLDtoObjCurr.getOperatnExec().equalsIgnoreCase(AppConstants.PR_RENEWAL)){
								if(scmXMLDtoObjCurr.getErrorCode()!=null && scmXMLDtoObjCurr.getErrorCode().equalsIgnoreCase(AppConstants.ERROR_CODE_FAIL)){
									returnStatusMain=scmBLObj.updateDbForCreateInvalidXml(conn,scmXMLDtoObjCurr);
								}else if(scmXMLDtoObjCurr.getErrorCode()!=null && scmXMLDtoObjCurr.getErrorCode().equalsIgnoreCase(AppConstants.ERROR_CODE_SUCCESS)){
									
									returnStatusMain=scmBLObj.updateSCMXMLDataPrCreate(conn,scmXMLDtoObjCurr);
								}
							}
						}
						scmBLObj.updateRecXmlStatus(conn, readResponseXMLId, returnStatusMain);
					}
				}while(returnStatusFound==AppConstants.Fetch_Status_Found);
			}
			
		}catch(Exception excp){
			Utility.onEx_LOG_RET_NEW_EX(excp, "processScmXml", "ProcessSCMResponseXML", null, true, true);
		}
		finally{
			try {
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Utility.onEx_LOG_RET_NEW_EX(e, "processScmXml", "ProcessSCMResponseXML", null, true, true);  
			}
		}
		Utility.LOG(true,false,"Exiting processScmXml");
	}
	
	public static void main(String args[]){
		System.out.println("PR Receive");
		new ProcessSCMResponseXML().processScmXml();
		
		
	}
	

	/**
	 * This method will get XML received from SCM
	 * @param scmXMLDtoObjCurr,conn
	 * @return returnStatus.
    */
	public  int fetchNextSCMXMLData(SCMRecXmlDto scmXMLDtoObjCurr,Connection conn) {
		Utility.LOG(true,false,"Entering fetchNextSCMXMLData");
		int returnStatus=0;
		 try {
			    HashMap<Integer,String> recXmlMap=new HashMap<Integer,String>();
			    recXmlMap=scmBLObj.getNextReceiveXml(conn);
			    if(recXmlMap.size()==0){ //no further records
					returnStatus=AppConstants.Fetch_Status_NotFound;
					return returnStatus;
				}else{
					for (Map.Entry<Integer, String> entry : recXmlMap.entrySet())
					{
						readResponseXMLId=entry.getKey();
						absFileName=Messages.getMessageValue("SCM_RESPONSE_PATH")+entry.getValue();
					}
					//update status as found in receive table
					scmBLObj.updateRecXmlStatus(conn,readResponseXMLId,AppConstants.Fetch_Status_Found);
					returnStatus=AppConstants.Fetch_Status_Found;
				}
		 }catch (Exception e) {
			returnStatus=AppConstants.SCM_XML_PROCESSED_FAILURE;
			Utility.onEx_LOG_RET_NEW_EX(e, "processScmXml", "ProcessSCMResponseXML", null, true, true);  
		  }
		 Utility.LOG(true,false,"Exiting fetchNextSCMXMLData");
		return returnStatus;
		 

	}
	
	/**
	 * This method will identify the operation name from XML and proceed accordingly for error or success events
	 * @param absFileName,scmXMLDtoObjCurr,conn
	 * @return returnStatus.
    */
	public  int processMainXmlContent(String absFileName,SCMRecXmlDto scmXMLDtoObjCurr,Connection conn) {
		Utility.LOG(true,false,"Entering processMainXmlContent");
		int returnStatus=0;
		String operatnName=null;

		 try {
				//starting to parse xml file
				File file = new File(absFileName);
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document doc = db.parse(file);
				doc.getDocumentElement().normalize();
				//checking pr reuse tags
				NodeList nodeNfaInfo = doc.getElementsByTagName("PRReuse:PRReuse");
				Element nfaInfoElmnt = (Element) nodeNfaInfo.item(0);
				if(nfaInfoElmnt!=null){
					operatnName=AppConstants.PR_REUSE;
				}else{
					//checking PR circuit tags
					 nodeNfaInfo = doc.getElementsByTagName("PRCircuit:PRCircuit");
					 nfaInfoElmnt = (Element) nodeNfaInfo.item(0);
					 if(nfaInfoElmnt!=null){
							operatnName=AppConstants.CIRCUIT_ID_UPDATE;
					}
				}
				
				
				//Start processing PR Reuse
				if(operatnName!=null && operatnName.equalsIgnoreCase(AppConstants.PR_REUSE)){
					NodeList nodeErrorInfo = doc.getElementsByTagName("PRReuse:errorstatus");
					Element errInfoElmnt = (Element) nodeErrorInfo.item(0);
					System.out.println("operatnName "+operatnName);
				
					NodeList errInfoId = errInfoElmnt.getChildNodes();
					if(errInfoId.item(0)!=null){
				    	  if(((Node) errInfoId.item(0)).getNodeValue()!=null &&  (!((Node) errInfoId.item(0)).getNodeValue().equalsIgnoreCase(""))){
				    		  scmXMLDtoObjCurr.setErrorCode(((Node) errInfoId.item(0)).getNodeValue().trim());  
					      }else{
					    	  scmXMLDtoObjCurr.setErrorCode("");
					      }
			    	  }else{
			    		  scmXMLDtoObjCurr.setErrorCode("");
			    	  }
					NodeList errMessElmntLst = doc.getElementsByTagName("PRReuse:errormessage");
				    Element errMessElmnt = (Element) errMessElmntLst.item(0);
				    NodeList errMess = errMessElmnt.getChildNodes();
				    if(errMess.item(0)!=null){
				    	if(((Node) errMess.item(0)).getNodeValue()!=null &&  (!((Node) errMess.item(0)).getNodeValue().equalsIgnoreCase(""))){
				    		  scmXMLDtoObjCurr.setErrorMess(((Node) errMess.item(0)).getNodeValue().trim());  
					      }else{
					    	  scmXMLDtoObjCurr.setErrorMess(AppConstants.MESSAGE_NOT_REC);
					      }
			    	}else{
			    		  scmXMLDtoObjCurr.setErrorMess(AppConstants.MESSAGE_NOT_REC);
			        }
				    scmXMLDtoObjCurr.setOperatnExec(operatnName);
				    //set nfa number
				    returnStatus=scmBLObj.getNfaNumber(doc,operatnName,scmXMLDtoObjCurr);
				    if(returnStatus==AppConstants.SCM_XML_PROCESSED_SUCCESS){
						//update details in receive table
						returnStatus=scmBLObj.updateRecXmlDtls(conn,scmXMLDtoObjCurr.getOrderNumber(),scmXMLDtoObjCurr.getServicePrdId(),readResponseXMLId,scmXMLDtoObjCurr.getNfaNumber(),scmXMLDtoObjCurr.getOperatnExec());
						 if(scmXMLDtoObjCurr.getErrorCode()!=null && scmXMLDtoObjCurr.getErrorCode().equalsIgnoreCase(AppConstants.ERROR_CODE_SUCCESS) && returnStatus==AppConstants.SCM_XML_PROCESSED_SUCCESS ){
							returnStatus=scmBLObj.processXmlForReuse(doc, operatnName, scmXMLDtoObjCurr);
						 }
					}
			}else if(operatnName!=null && operatnName.equalsIgnoreCase(AppConstants.CIRCUIT_ID_UPDATE)){
				NodeList nodeErrorInfo = doc.getElementsByTagName("PRCircuit:status");
				Element errInfoElmnt = (Element) nodeErrorInfo.item(0);
				System.out.println("operatnName "+operatnName);
			
				NodeList errInfoId = errInfoElmnt.getChildNodes();
				if(errInfoId.item(0)!=null){
			    	  if(((Node) errInfoId.item(0)).getNodeValue()!=null &&  (!((Node) errInfoId.item(0)).getNodeValue().equalsIgnoreCase(""))){
			    		  scmXMLDtoObjCurr.setErrorCode(((Node) errInfoId.item(0)).getNodeValue().trim());  
				      }else{
				    	  scmXMLDtoObjCurr.setErrorCode("");
				      }
		    	  }else{
		    		  scmXMLDtoObjCurr.setErrorCode("");
		    	  }
				NodeList errMessElmntLst = doc.getElementsByTagName("PRCircuit:message");
			    Element errMessElmnt = (Element) errMessElmntLst.item(0);
			    NodeList errMess = errMessElmnt.getChildNodes();
			    if(errMess.item(0)!=null){
			    	if(((Node) errMess.item(0)).getNodeValue()!=null &&  (!((Node) errMess.item(0)).getNodeValue().equalsIgnoreCase(""))){
			    		  scmXMLDtoObjCurr.setErrorMess(((Node) errMess.item(0)).getNodeValue().trim());  
				      }else{
				    	  scmXMLDtoObjCurr.setErrorMess(AppConstants.MESSAGE_NOT_REC);
				      }
		    	}else{
		    		  scmXMLDtoObjCurr.setErrorMess(AppConstants.MESSAGE_NOT_REC);
		        }
			    scmXMLDtoObjCurr.setOperatnExec(operatnName);
			    //set nfa number
			    returnStatus=scmBLObj.getNfaNumber(doc,operatnName,scmXMLDtoObjCurr);
			    if(returnStatus==AppConstants.SCM_XML_PROCESSED_SUCCESS){
					//update details in receive table
					returnStatus=scmBLObj.updateRecXmlDtls(conn,scmXMLDtoObjCurr.getOrderNumber(),scmXMLDtoObjCurr.getServicePrdId(),readResponseXMLId,scmXMLDtoObjCurr.getNfaNumber(),scmXMLDtoObjCurr.getOperatnExec());
					 /*if(scmXMLDtoObjCurr.getErrorCode()!=null && scmXMLDtoObjCurr.getErrorCode().equalsIgnoreCase(AppConstants.ERROR_CODE_SUCCESS) && returnStatus==AppConstants.SCM_XML_PROCESSED_SUCCESS ){
						returnStatus=scmBLObj.processXmlForReuse(doc, operatnName, scmXMLDtoObjCurr);
					 }*/
				}
			    returnStatus=AppConstants.SCM_XML_PROCESSED_SUCCESS;	    
			}else{
				//found out whether xml received from EI or SCM
				NodeList nodeErrorInfo = doc.getElementsByTagName("ERROR_STATUS");
				Element errInfoElmnt = (Element) nodeErrorInfo.item(0);
				if(errInfoElmnt!=null){
					operatnName=AppConstants.PR_INSERTED;
					NodeList errInfoId = errInfoElmnt.getChildNodes();
				      if(errInfoId.item(0)!=null){
				    	  if(((Node) errInfoId.item(0)).getNodeValue()!=null &&  (!((Node) errInfoId.item(0)).getNodeValue().equalsIgnoreCase(""))){
				    		  scmXMLDtoObjCurr.setErrorMess(((Node) errInfoId.item(0)).getNodeValue().trim());  
					      }else{
					    	  scmXMLDtoObjCurr.setErrorMess("");
					      }
			    	  }else{
			    		  scmXMLDtoObjCurr.setErrorMess("");
				      }
				      scmXMLDtoObjCurr.setOperatnExec(operatnName);
				      returnStatus=AppConstants.SCM_XML_PROCESSED_SUCCESS;
				      System.out.println("operatnName "+operatnName);
				}else{
					//xml received for pr create or renewal from SCM
					NodeList nodecntrlInfo = doc.getElementsByTagName("PRResponse:controlInfo");
					  
					Node fstNodecntrlInfo = nodecntrlInfo.item(0);
					
					if (fstNodecntrlInfo.getNodeType() == Node.ELEMENT_NODE){
						  Element fstElmntcntrlInfo = (Element) fstNodecntrlInfo;
						  NodeList cntrlInfoElmntLst = fstElmntcntrlInfo.getElementsByTagName("commBO:operationName");
					      Element cntrlInfoElmnt = (Element) cntrlInfoElmntLst.item(0);
					      NodeList cntrlInfoId = cntrlInfoElmnt.getChildNodes();
					      if(cntrlInfoId.item(0)!=null){
					    	  if(((Node) cntrlInfoId.item(0)).getNodeValue()!=null &&  (!((Node) cntrlInfoId.item(0)).getNodeValue().equalsIgnoreCase(""))){
					    		  operatnName=((Node) cntrlInfoId.item(0)).getNodeValue().trim();  
						      }else{
						    	  operatnName="";
						      }
				    	  }else{
				    		  operatnName="";
				    	  }
					}
					System.out.println("operatnName "+operatnName);
				}
		  		scmXMLDtoObjCurr.setOperatnExec(operatnName);
				//segregate code here
				if(scmXMLDtoObjCurr.getOperatnExec().equalsIgnoreCase(AppConstants.PR_CREATION) || operatnName.equalsIgnoreCase(AppConstants.PR_RENEWAL)){
					
					 //set nfa number
					returnStatus=scmBLObj.getNfaNumber(doc,operatnName,scmXMLDtoObjCurr);    
					if(returnStatus==AppConstants.SCM_XML_PROCESSED_SUCCESS){
					//update details in receive table
						returnStatus=scmBLObj.updateRecXmlDtls(conn,scmXMLDtoObjCurr.getOrderNumber(),scmXMLDtoObjCurr.getServicePrdId(),readResponseXMLId,scmXMLDtoObjCurr.getNfaNumber(),scmXMLDtoObjCurr.getOperatnExec());
					//to get whether success or failure then process accordingly
						if(returnStatus==AppConstants.SCM_XML_PROCESSED_SUCCESS){
							nodeErrorInfo = doc.getElementsByTagName("PRResponse:errorInfo");
							 Node fstNodeErrInfo = nodeErrorInfo.item(0);
							if (fstNodeErrInfo.getNodeType() == Node.ELEMENT_NODE){
								  Element fstElmntErrInfo = (Element) fstNodeErrInfo;
								  NodeList errInfoElmntLst = fstElmntErrInfo.getElementsByTagName("commBO:errorCode");
							      errInfoElmnt = (Element) errInfoElmntLst.item(0);
							      NodeList errInfoId = errInfoElmnt.getChildNodes();
							      if(errInfoId.item(0)!=null){
							    	  if(((Node) errInfoId.item(0)).getNodeValue()!=null &&  (!((Node) errInfoId.item(0)).getNodeValue().equalsIgnoreCase(""))){
							    		  scmXMLDtoObjCurr.setErrorCode(((Node) errInfoId.item(0)).getNodeValue().trim());  
								      }else{
								    	  scmXMLDtoObjCurr.setErrorCode("");
								      }
						    	  }else{
						    		  scmXMLDtoObjCurr.setErrorCode("");
						    	  }
							}
							if(scmXMLDtoObjCurr.getErrorCode()!=null && !scmXMLDtoObjCurr.getErrorCode().equalsIgnoreCase("") && scmXMLDtoObjCurr.getErrorCode().equalsIgnoreCase(AppConstants.ERROR_CODE_SUCCESS)){
								returnStatus=scmBLObj.processXmlForCreate(doc,  scmXMLDtoObjCurr);
							}else if(scmXMLDtoObjCurr.getOperatnExec()!=null && !scmXMLDtoObjCurr.getOperatnExec().equalsIgnoreCase("") &&
									scmXMLDtoObjCurr.getOperatnExec().equalsIgnoreCase(AppConstants.PR_CREATION) && scmXMLDtoObjCurr.getErrorCode()!=null 
									&& !scmXMLDtoObjCurr.getErrorCode().equalsIgnoreCase("") && scmXMLDtoObjCurr.getErrorCode().equalsIgnoreCase(AppConstants.ERROR_CODE_FAIL)){
								
								if (fstNodeErrInfo.getNodeType() == Node.ELEMENT_NODE){
									  Element fstElmntErrInfo = (Element) fstNodeErrInfo;
									  NodeList errMessElmntLst = fstElmntErrInfo.getElementsByTagName("commBO:errorMessage");
								      Element errMessElmnt = (Element) errMessElmntLst.item(0);
								      NodeList errMess = errMessElmnt.getChildNodes();
								      if(errMess.item(0)!=null){
								    	  if(((Node) errMess.item(0)).getNodeValue()!=null &&  (!((Node) errMess.item(0)).getNodeValue().equalsIgnoreCase(""))){
								    		  scmXMLDtoObjCurr.setErrorMess(((Node) errMess.item(0)).getNodeValue().trim());  
									      }else{
									    	  scmXMLDtoObjCurr.setErrorMess("");
									      }
							    	  }else{
							    		  scmXMLDtoObjCurr.setErrorMess("");
							    	  }
								}
								returnStatus=scmBLObj.processInvalidXmlForCreate(doc, scmXMLDtoObjCurr);
							}
						}
					}
				}

			}
		 }catch(SAXException saxExcp){
			 returnStatus=AppConstants.SCM_XML_PROCESSED_FAILURE;
			 Utility.onEx_LOG_RET_NEW_EX(saxExcp, "processScmXml", "ProcessSCMResponseXML", null, true, true);  
		}catch (Exception e) {
			returnStatus=AppConstants.SCM_XML_PROCESSED_FAILURE;
			Utility.onEx_LOG_RET_NEW_EX(e, "processScmXml", "ProcessSCMResponseXML", null, true, true);  
		}
		Utility.LOG(true,false,"Exiting processMainXmlContent");
		return returnStatus;
	}
	
	
}
