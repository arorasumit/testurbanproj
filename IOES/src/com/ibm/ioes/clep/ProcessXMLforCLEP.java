//=================================================================================================================
/**@author Vijay
 * The purpose of this file is to write methods that process CLEP related works.
 */
//=================================================================================================================
package com.ibm.ioes.clep;


import java.sql.CallableStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ibm.ioes.beans.BillingTriggerValidation;
import com.ibm.ioes.beans.ViewOrderDto;
import com.ibm.ioes.dao.NewOrderDaoExt;
import com.ibm.ioes.dao.ViewOrderDao;
import com.ibm.ioes.forms.NewOrderDto;
import com.ibm.ioes.forms.OrderHeaderDTO;
import com.ibm.ioes.utilities.AjaxValidation;


public class ProcessXMLforCLEP {

	private static final Logger logger = Logger.getLogger(CLEPListener.class);
//################################################################################################################################################################
						//VALIDATE AND WORKFLOW CLOSING
	public static String sqlSpValidatePODetail= "{call IOE.SP_VALIDATE_PODETAIL(?)}";//To Validate PO and update Status
	public static String sqlSpValidatePO= "{call IOE.SPUPDATEPOSTATUS(?,?,?,?,?)}";//To Validate PO and update Status
	public static String sqlGet_TaskListOfOrder = "{CALL IOE.SPGETTASKWORKFLOWDETAILS(?)}";
	public static String sqlSpInsertTaskAction= "{call IOE.SP_INSERT_TASK_ACTION(?,?,?,?,?,?,?,?,?,?)}";//To Insert Task Notes
	
	public static String sqlSpInsertTaskActionForChangeOrder= "{call IOE.SP_INSERT_TASK_ACTION_CHANGE_ORDER(?,?,?,?,?,?,?,?,?,?,?)}";//To Insert Task Notes
//################################################################################################################################################################
		
	
	/**@Vijay 
	 * This method will validate data and attach default workflow(AM,PM & COPC)
	 * 	and also provide auto approval.
	 *    
	 * Return int as 0 or 1 or -1 that denote the success/failure of process
	 * 
	 *  -----Method for New Order-----
	 */
	public static int processWorkflowForNoNM6Product(long orderNo,long xmlfileid,String serviceType,Connection conn,String jmsID)throws Exception{
		CallableStatement csWorkflow = null;
		ResultSet rsIOMS=null;
		ResultSet rsWorkflow=null;
		
		int taskId=0;
		String taskOwnerId=null;
		String retString="";
		String poDetailNo = "";
		String serviceId = "";
		int successStatus=-1;
		ViewOrderDao objViewOrderDao=new ViewOrderDao();
		
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
							
							/*Vijay 
							 * Here tasks are committing on database  */
							conn.commit();
							successStatus=1;
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
				e.printStackTrace();
				String missingAttribute="[Internal error]{}";				
				CLEPXmlDto clepXmlDto=new CLEPXmlDto();
				clepXmlDto.setXmlData("<orderHeader><NewOrder><OrderType>NEW PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML,missing Attribute "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><ProductCatelog><ErrorMsg></ErrorMsg></ProductCatelog><Charges><ErrorMsg></ErrorMsg></Charges></NewOrder></orderHeader>");			
				clepXmlDto.setJmsMessageID(jmsID);
				ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);
		}
		return successStatus;
	}
	
	
	/**@Vijay 
	 * This method will validate data and attach default workflow(AM,PM & COPC)
	 * 	and also provide auto approval.
	 *    
	 * Return int as 0 or 1 or -1 that denote the success/failure of process
	 * 
	 *  -----Method for Change Order-----
	 */
	public static int processOnlyNoNM6ProductForChange(int orderNo,long xmlfileid,String projectMngrId,Connection conn,String jmsID,String changeType,String subChangeTypeId)throws Exception{
		CallableStatement csWorkflow = null;
		CallableStatement csWorkflowClose=null;		
		ResultSet rsWorkflow=null;		
		String taskWorkflowRoleName="";
		int taskId=0;
		String taskOwnerId=null;
		//String retString="";
		//String poDetailNo = "";
		//String serviceId = "";
		int successStatus=-1;
		String validatePO="";
		int insertWorkflow=-1;
		CLEPXmlDto clepDto=new CLEPXmlDto();
		NewOrderDaoExt objNewExtDao=new NewOrderDaoExt();
		OrderHeaderDTO objNewDto=new OrderHeaderDTO();
		ArrayList<OrderHeaderDTO> objNewALDto=null;		
		//ViewOrderDao objViewDao=new ViewOrderDao();
		try{
			//==============================VALIDATION=====================================
			//long orderNumber=orderNo;
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
						conn.commit();
						successStatus=1;
					}else{
						CLEPUtility.SysErr("-------- Error: occured during workflow!! ---------");	
						String msg="Error occured during workflow!!";
						String str="<orderHeader><ChangeOrder><OrderType>CHANGEORDER PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to Invalid XML</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg>"+msg+"</ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails></ChangeOrder></orderHeader>";						
						conn.rollback();
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
			e.printStackTrace();
			String missingAttribute="[Internal error]{}";
			CLEPXmlDto clepXmlDto=new CLEPXmlDto();
			clepXmlDto.setXmlData("<orderHeader><ChangeOrder><OrderType>CHANGE ORDER PROCESS VALIDATION</OrderType><OrderExtAttributes><OrderNo></OrderNo><FileId></FileId><MessageId>"+jmsID+"</MessageId><Status>FAILED</Status><Reason>Failed Due to "+missingAttribute+"</Reason></OrderExtAttributes><MainTab><ErrorMsg></ErrorMsg></MainTab><ContactTab><ErrorMsg></ErrorMsg></ContactTab><PoDetailsTab><ErrorMsg></ErrorMsg></PoDetailsTab><ServiceDetails><ErrorMsg></ErrorMsg></ServiceDetails><SubNodeLineItemDetails><ErrorMsg></ErrorMsg></SubNodeLineItemDetails></ChangeOrder></orderHeader>");
			clepXmlDto.setJmsMessageID(jmsID);
			ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto,xmlfileid,"Y","N",conn);
			CLEPUtility.SysErr(e.getMessage());
			try{
				conn.rollback();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return successStatus;
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
			ex.printStackTrace();
		}
		return clepxmldto;
	}
	
}
