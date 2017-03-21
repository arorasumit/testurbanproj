package com.ibm.ioes.dao;
//Tag Name Resource Name  Date		CSR No			Description
//[001]	 Kalpana	11-January-2014			New File to access DB for third party

import java.math.BigDecimal;
import java.sql.BatchUpdateException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import com.ibm.ioes.ei.scm.dto.PRCreatnSCMXmlDto;
import com.ibm.ioes.ei.scm.dto.SCMRecXmlDto;
import com.ibm.ioes.ei.scm.dto.SCMXMLDto;
import com.ibm.ioes.exception.IOESException;
import com.ibm.ioes.forms.LineItemValueDTO;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.IB2BMailDto;
import com.ibm.ioes.utilities.Utility;

public class SCMDao {
	
	private static final String sqlInsertPRCreationSCMXML =
		"INSERT INTO  IOE.TSCM_XMLSEND_STATUS(ORDERNO, SERVICEID, SERVICEPRODUCTID,EVENT_TYPEID,PR_OPERATION) VALUES(?,?,?,?,?)";
	
	private static final String sqlUpdateAttVal =
		"UPDATE IOE.TPRODUCTLINEATTVALUE SET ATTVALUE=? WHERE SERVICEPRODUCTID=? and ATTMASTERID = (SELECT ATTMASTERID FROM IOE.TPRODUCTLINEATTMASTER WHERE ATTDESCRIPTION=? and SERVICEDETAILID=?)";
	
	private static final String sqlUpdateAttValHistory =
		"UPDATE IOE.TPRODUCTLINEATTVALUE_HISTORY SET ATTVALUE_NEW=? WHERE MAIN_SERVICE_ID=? and SERVICEPRODUCTID=? and   ATTMASTERID = (SELECT ATTMASTERID FROM IOE.TPRODUCTLINEATTMASTER WHERE ATTDESCRIPTION=? and SERVICEDETAILID=?)";
	
	private static final StringBuilder sqlGetLineValues=new StringBuilder();
	private static final StringBuilder sqlGetItemCodeValues=new StringBuilder();
	
	private static final String sql_updatePRChargeDtls = "{call IOE.UPDATE_PR_CREATE_LINE_VALUES_SCM(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String sql_updatePRAttributeOrdrDtls = "{call IOE.SP_UPDATE_ATTRIBUTE_DETAILS_SCM_RESPONSE(?,?,?,?,?,?)}";
	private static final String sqlUpdatePRCreationSCMStatus =
		"UPDATE IOE.TSCM_XMLSEND_STATUS set STATUS=? where  ORDERNO=? and SERVICEID=? and SERVICEPRODUCTID=? and STATUS=0";
	
	
	private static final StringBuilder sqlGetServiceLvlDtls =new StringBuilder();
	
	private static final String sqlUpdateEiStatOrdr =	"UPDATE IOE.TPOSERVICEDETAILS SET SCM_PROGRESS_STATUS=?,PR_ID=? WHERE SERVICEPRODUCTID=?";
	
	private static final String sqlInactiveExistingPr ="UPDATE IOE.TM_RECEVD_PR_DTLS SET  ISACTIVE=?,MODIFIEDDATE=current timestamp WHERE SERVICEPRODUCTID=? and ISACTIVE=1 and PR_ID!=?";

	private static final String sqlGetPrId =	"select PR_ID,PR_TYPE,PR_STATUS,ISACTIVE from IOE.TM_RECEVD_PR_DTLS where SERVICEPRODUCTID=? and PR_NUMBER=? and CIRCLE=? with ur";
	
	private static final String sqlInsertPRDtlsMain ="INSERT INTO  IOE.TM_RECEVD_PR_DTLS(SERVICEPRODUCTID,PR_NUMBER,CIRCLE, PR_STATUS, PR_TYPE,ISACTIVE) VALUES(?,?,?,?,?,?)";
	

	private static final String sqlUpdatePRDtlsMain ="UPDATE IOE.TM_RECEVD_PR_DTLS SET  PR_STATUS=?, ISACTIVE=?,PR_TYPE=?,MODIFIEDDATE=current timestamp  WHERE PR_ID=?";
	
	private static final String sqlUpdatePRItemsMain ="UPDATE IOE.TPRODUCTLINECHARGEVALUE_SCM  set IS_ACTIVE=0 WHERE PR_ID=? and SERVICEPRODUCTID=?";
	
	
	private static final String sqlUpdateLineMessage =	"UPDATE IOE.TPRODUCTLINECHARGEVALUE_SCM SET SCM_MESSAGE=?,MODIFIEDDATE=current timestamp WHERE CHARGEID_SCM=?  ";
	
	
	private static final StringBuilder sqlCopyPrLineDtls =new StringBuilder();
	
	private static final StringBuilder sqlCopyPrAttDtls =new StringBuilder();
	
	private static final String sqlUpdatePrReuseLineDtls =	"UPDATE IOE.TPRODUCTLINECHARGEVALUE_SCM SET SCM_MESSAGE=?,PR_ID=?,MODIFIEDDATE=current timestamp WHERE PR_ID=? and SERVICEPRODUCTID=? ";
	
	private static final String sqlGetNextReceiveXml ="select ID,FILENAME from IOE.TSCM_XMLRECEIVE_STATUS where status=0 order by CREATEDDATE asc FETCH FIRST ROW ONLY with ur";
	
	private static final String sqlUpdateReceiveStatus ="UPDATE IOE.TSCM_XMLRECEIVE_STATUS SET status=?,LASTUPDATEDDATE=current timestamp WHERE ID=?";
	
	private static final String sqlUpdateReceiveDtls ="UPDATE IOE.TSCM_XMLRECEIVE_STATUS SET ORDERNO=?,SERVICEPRODUCTID=?,status=?,NFA_NUMBER=?,LASTUPDATEDDATE=current timestamp,OPERATION_EXEC=? WHERE ID=?";
	
	private static final String sqlGetSpId ="select SERVICEPRODUCTID from IOE.TSCM_XMLSEND_STATUS where REC_FILE_ID=?";
	
	
	private static final StringBuilder sqlDeleteSentLine=new StringBuilder();
	
	private static final StringBuilder sqlChkSpId=new StringBuilder();
	
	private static final StringBuilder sqlGetLineItemIds=new StringBuilder();
	private static final StringBuilder sqlGetLineItemIdsForSed=new StringBuilder();
	private static final StringBuilder sqlGetAttributeValue=new StringBuilder();
	
	private static final StringBuilder sqlGetCurrentPrDtls=new StringBuilder();
	
	private static final String sqlUpdatePRCreationSCMXML =
		"UPDATE IOE.TSCM_XMLSEND_STATUS set XML=?,status=? where  ID=?";
	static{
		
		sqlGetLineValues.append(" SELECT TPRODUCTLINEATTMASTER.ATTMASTERID,TPRODUCTLINEATTMASTER.ATTDESCRIPTION,TPRODUCTLINEATTMASTER.EXPECTEDVALUE");
		sqlGetLineValues.append(" ,CASE WHEN ((TPRODUCTLINEATTMASTER.EXPECTEDVALUE='DROPDOWN' or TPRODUCTLINEATTMASTER.EXPECTEDVALUE='LOV') and TPRODUCTLINEATTVALUE.attValue <> '0') THEN ");
		sqlGetLineValues.append(" TPRODUCTDDVALUES.TEXT else TPRODUCTLINEATTVALUE.attValue END as attValue,TPRODUCTLINEATTVALUE.SERVICEPRODUCTID FROM IOE.TPRODUCTLINEATTMASTER AS TPRODUCTLINEATTMASTER"); 
		sqlGetLineValues.append(" INNER JOIN  IOE.TPRODUCTLINEATTVALUE AS TPRODUCTLINEATTVALUE ON TPRODUCTLINEATTVALUE.ATTMASTERID = TRIM(CHAR(TPRODUCTLINEATTMASTER.ATTMASTERID))");
		sqlGetLineValues.append(" LEFT OUTER JOIN IOE.TPRODUCTDDVALUES TPRODUCTDDVALUES ON TPRODUCTDDVALUES.ATTMASTERID=TPRODUCTLINEATTVALUE.ATTMASTERID AND "); 
		sqlGetLineValues.append(" TPRODUCTLINEATTVALUE.ATTVALUE=TPRODUCTDDVALUES.VALUE and TPRODUCTDDVALUES.FLAG='PRODUCT' where TPRODUCTLINEATTMASTER.SENDTOSCM=1 and TPRODUCTLINEATTMASTER.ISACTIVE=1 and TPRODUCTLINEATTVALUE.SERVICEPRODUCTID=? WITH UR");
		
		
		sqlGetItemCodeValues.append(" select TPRODUCTLINECHARGEVALUE_SCM.CHARGEID_SCM,TM_ITEMCODEMASTER_SCM.ITEM_CODE ,TPRODUCTLINECHARGEVALUE_SCM.QUANTITY ,");
		sqlGetItemCodeValues.append(" TPRODUCTLINECHARGEVALUE_SCM.CHARGEVALUE ,	TM_DELIVERTOLOCATN_SCM.DELIVER_TO_LOCATN,TM_SUBINVENTORY_SCM.SUBINVENTORY, TM_AOPBUDGETHEAD1_SCM.AOP_BUDGET_HEAD1,");
		sqlGetItemCodeValues.append(" TM_AOPBUDGETHEAD2_SCM.AOP_BUDGET_HEAD2,TPRODUCTLINECHARGEVALUE_SCM.AOP_YEAR,TPRODUCTLINECHARGEVALUE_SCM.PR_ID FROM ioe.TPRODUCTLINECHARGEVALUE_SCM TPRODUCTLINECHARGEVALUE_SCM	inner join ioe.TM_ITEMCODEMASTER_SCM TM_ITEMCODEMASTER_SCM");
		sqlGetItemCodeValues.append(" on TM_ITEMCODEMASTER_SCM.ITEM_CODE_ID=TPRODUCTLINECHARGEVALUE_SCM.ITEM_CODE inner join ioe.TM_DELIVERTOLOCATN_SCM TM_DELIVERTOLOCATN_SCM on TM_DELIVERTOLOCATN_SCM.DEL_ID=TPRODUCTLINECHARGEVALUE_SCM.DEL_ID");
		sqlGetItemCodeValues.append(" inner join ioe.TM_SUBINVENTORY_SCM TM_SUBINVENTORY_SCM on TM_SUBINVENTORY_SCM.SUBINV_ID=TPRODUCTLINECHARGEVALUE_SCM.SUBINV_ID	inner join ioe.TM_AOPBUDGETHEAD1_SCM TM_AOPBUDGETHEAD1_SCM");
		sqlGetItemCodeValues.append(" on TM_AOPBUDGETHEAD1_SCM.AOP1_ID=TPRODUCTLINECHARGEVALUE_SCM.AOP1_ID inner join ioe.TM_AOPBUDGETHEAD2_SCM TM_AOPBUDGETHEAD2_SCM on TM_AOPBUDGETHEAD2_SCM.AOP2_ID=TPRODUCTLINECHARGEVALUE_SCM.AOP2_ID");		
		sqlGetItemCodeValues.append(" WHERE TPRODUCTLINECHARGEVALUE_SCM.SERVICEPRODUCTID=? WITH UR");
		
		
		
		sqlGetCurrentPrDtls.append("select  TPRODUCTLINEATTVALUE.ATTVALUE,TM_RECEVD_PR_DTLS.PR_NUMBER,TM_RECEVD_PR_DTLS.CIRCLE,TPRODUCTLINEATTVALUE_SCM.ATTVALUE AS ATTVALUE_OLD from IOE.TPOSERVICEDETAILS TPOSERVICEDETAILS inner join IOE.TM_RECEVD_PR_DTLS TM_RECEVD_PR_DTLS ON TM_RECEVD_PR_DTLS.SERVICEPRODUCTID=TPOSERVICEDETAILS.SERVICEPRODUCTID ");
		sqlGetCurrentPrDtls.append(" inner join IOE.TPRODUCTLINEATTMASTER TPRODUCTLINEATTMASTER ON   TPRODUCTLINEATTMASTER.SERVICEDETAILID=TPOSERVICEDETAILS.SERVICEDETAILID ");
		sqlGetCurrentPrDtls.append(" inner join IOE.TPRODUCTLINEATTVALUE TPRODUCTLINEATTVALUE ON   TPRODUCTLINEATTVALUE.ATTMASTERID=TPRODUCTLINEATTMASTER.ATTMASTERID and TPRODUCTLINEATTVALUE.SERVICEPRODUCTID=TPOSERVICEDETAILS.SERVICEPRODUCTID");
		sqlGetCurrentPrDtls.append(" left outer join IOE.TPRODUCTLINEATTVALUE_SCM TPRODUCTLINEATTVALUE_SCM ON TPRODUCTLINEATTVALUE_SCM.ATTMASTERID_SCM=TPRODUCTLINEATTMASTER.ATTMASTERID and TM_RECEVD_PR_DTLS.PR_ID=TPRODUCTLINEATTVALUE_SCM.PR_ID" );
		sqlGetCurrentPrDtls.append(" WHERE TPOSERVICEDETAILS.SERVICEPRODUCTID=? and TPRODUCTLINEATTMASTER.ATTDESCRIPTION=? ");
		
		sqlCopyPrLineDtls.append("	INSERT INTO IOE.TM_SENT_LINE_DTLS_SCM (ITEM_CODE,QUANTITY,CHARGEVALUE,DEL_ID,SUBINV_ID,AOP1_ID,");
		sqlCopyPrLineDtls.append("	AOP2_ID,AOP_YEAR,PO_NUMBER,PO_DATE,PO_AMOUNT,TRX_ID,IS_ACTIVE,SERVICEPRODUCTID)");
		sqlCopyPrLineDtls.append("	(SELECT ITEM_CODE,QUANTITY,CHARGEVALUE,DEL_ID,SUBINV_ID,AOP1_ID,");
		sqlCopyPrLineDtls.append(" AOP2_ID,AOP_YEAR,PO_NUMBER,PO_DATE,PO_AMOUNT,CHARGEID_SCM,IS_ACTIVE,SERVICEPRODUCTID FROM IOE.TPRODUCTLINECHARGEVALUE_SCM");
		sqlCopyPrLineDtls.append(" where SERVICEPRODUCTID=? and SCM_LINE_ID=0)");
		
		sqlCopyPrAttDtls.append(" INSERT INTO IOE.TPRODUCTLINEATTVALUE_SCM(ATTMASTERID_SCM, ATTVALUE, PR_ID) " );
		sqlCopyPrAttDtls.append(" SELECT ATTMASTERID_SCM, ATTVALUE,? FROM  IOE.TPRODUCTLINEATTVALUE_SCM WHERE PR_ID = ? WITH UR ");
		
		sqlGetServiceLvlDtls.append("SELECT M6_FX_PROGRESS_STATUS FROM IOE.TPOSERVICEMASTER WHERE SERVICEID=? WITH UR");
		
		sqlDeleteSentLine.append("DELETE FROM  IOE.TPRODUCTLINECHARGEVALUE_SCM where SERVICEPRODUCTID=? and SCM_LINE_ID=0");
		
		sqlChkSpId.append("SELECT TPOSERVICEDETAILS.SERVICEPRODUCTID FROM IOE.TPOSERVICEMASTER TPOSERVICEMASTER  ");
		sqlChkSpId.append("inner join ioe.TPOSERVICEDETAILS TPOSERVICEDETAILS ON (case when TPOSERVICEDETAILS.CHANGE_SERVICEID=0 then TPOSERVICEDETAILS.SERVICEID ");
		sqlChkSpId.append(" else TPOSERVICEDETAILS.CHANGE_SERVICEID end )=TPOSERVICEMASTER.SERVICEID");
		sqlChkSpId.append(" where TPOSERVICEMASTER.ORDERNO=? and TPOSERVICEMASTER.SERVICEID=? and TPOSERVICEDETAILS.SERVICEPRODUCTID=? WITH UR");
			
		sqlGetLineItemIds.append(" select TPOSERVICEDETAILS.SERVICEPRODUCTID,TPOSERVICEDETAILS.IS_PR_REUSE,TPOSERVICEDETAILS.PR_ID,TPOSERVICEDETAILS.SERVICEDETAILID,TPOSERVICEDETAILS.SCM_PROGRESS_STATUS,TPOMASTER.SUB_CHANGE_TYPE_ID, " );
		sqlGetLineItemIds.append(" case when TPOSERVICEDETAILS.CHANGE_SERVICEID=0 then TPOSERVICEDETAILS.SERVICEID else TPOSERVICEDETAILS.CHANGE_SERVICEID end as  SERVICEID ");
		sqlGetLineItemIds.append(" from ioe.TPOSERVICEMASTER TPOSERVICEMASTER inner join ioe.TPOSERVICEDETAILS TPOSERVICEDETAILS ON (case when TPOSERVICEDETAILS.CHANGE_SERVICEID=0 then TPOSERVICEDETAILS.SERVICEID ");
		sqlGetLineItemIds.append(" else TPOSERVICEDETAILS.CHANGE_SERVICEID end )=TPOSERVICEMASTER.SERVICEID");
		sqlGetLineItemIds.append(" inner join ioe.TPOMASTER TPOMASTER ON TPOMASTER.ORDERNO=TPOSERVICEMASTER.ORDERNO ");
		sqlGetLineItemIds.append(" where TPOSERVICEMASTER.ORDERNO=?");
		sqlGetLineItemIds.append(" and TPOSERVICEDETAILS.SERVICEDETAILID in (?)");
		sqlGetLineItemIds.append(" and (TPOSERVICEMASTER.M6_FX_PROGRESS_STATUS not in ('CANCELLED','M6_CANCELLED') or TPOSERVICEMASTER.M6_FX_PROGRESS_STATUS is null) and TPOSERVICEMASTER.IS_SERVICE_INACTIVE=0 WITH UR");
		//added by Deepak for upadte Sed user_Id
		sqlGetLineItemIdsForSed.append(" select TPOSERVICEDETAILS.SERVICEPRODUCTID,TPOSERVICEDETAILS.SERVICEID,TPOSERVICEDETAILS.SERVICEDETAILID,TPOSERVICEDETAILS.IS_PR_REUSE from ioe.TPOSERVICEMASTER TPOSERVICEMASTER ");
		sqlGetLineItemIdsForSed.append("inner join ioe.TPOSERVICEDETAILS TPOSERVICEDETAILS ON (case when TPOSERVICEDETAILS.CHANGE_SERVICEID=0 then TPOSERVICEDETAILS.SERVICEID ");
		sqlGetLineItemIdsForSed.append(" else TPOSERVICEDETAILS.CHANGE_SERVICEID end )=TPOSERVICEMASTER.SERVICEID");
		sqlGetLineItemIdsForSed.append(" inner join ioe.TPOMASTER TPOMASTER ON TPOMASTER.ORDERNO=TPOSERVICEMASTER.ORDERNO ");
		sqlGetLineItemIdsForSed.append(" where TPOSERVICEMASTER.ORDERNO=?");
		sqlGetLineItemIdsForSed.append(" and TPOSERVICEDETAILS.SERVICEDETAILID in (?)");
		sqlGetLineItemIdsForSed.append(" and TPOMASTER.STAGE not in ('CANCELLED','M6_CANCELLED') and TPOSERVICEMASTER.IS_SERVICE_INACTIVE=0 WITH UR");
		sqlGetAttributeValue.append("select TPRODUCTLINEATTVALUE.ATTVALUE from  IOE.TPRODUCTLINEATTVALUE TPRODUCTLINEATTVALUE inner join ");
		sqlGetAttributeValue.append(" IOE.TPRODUCTLINEATTMASTER TPRODUCTLINEATTMASTER ON TPRODUCTLINEATTMASTER.ATTMASTERID=TPRODUCTLINEATTVALUE.ATTMASTERID WHERE TPRODUCTLINEATTVALUE.SERVICEPRODUCTID=? ");
		sqlGetAttributeValue.append(" and TPRODUCTLINEATTMASTER.ATTDESCRIPTION=?");
		
		
	}
	
	

	
	/**
	 * This method will get attribute details required for PR creation of service product id
	 * @param  conn,lineItemId
	 * @return ArrayList<LineItemValueDTO>
    */
	public ArrayList<LineItemValueDTO> getLineItemAttValues(Connection conn,int lineItemId) throws SQLException{
		Utility.LOG(true,false,"Entering getLineItemAttValues");
		ResultSet rsLineDtls=null;
		ArrayList<LineItemValueDTO> lineItemValueLst=new ArrayList<LineItemValueDTO>();
		LineItemValueDTO lineItemValueDTOObj;
		PreparedStatement lineItemValPstmt =null;
		try {
					//get line item attributes
			lineItemValPstmt = conn.prepareStatement(sqlGetLineValues.toString());
			lineItemValPstmt.setInt(1,lineItemId);
			rsLineDtls = lineItemValPstmt.executeQuery();
			while(rsLineDtls.next()){
				lineItemValueDTOObj=new LineItemValueDTO();
				lineItemValueDTOObj.setAttMasterId(rsLineDtls.getInt("ATTMASTERID"));
				lineItemValueDTOObj.setAttDesc(rsLineDtls.getString("ATTDESCRIPTION"));
				lineItemValueDTOObj.setExpectedValue(rsLineDtls.getString("EXPECTEDVALUE"));
				lineItemValueDTOObj.setAttValue(rsLineDtls.getString("attValue"));
				lineItemValueDTOObj.setSpId(rsLineDtls.getInt("SERVICEPRODUCTID"));
				lineItemValueLst.add(lineItemValueDTOObj);
			}
					
		} catch (SQLException e) {
			Utility.onEx_LOG_RET_NEW_EX(e, "getLineItemAttValues", "SCMDao", "sqlState", true, true);
		} catch (Exception e) {
			Utility.onEx_LOG_RET_NEW_EX(e, "getLineItemAttValues", "SCMDao", null, true, true);
		}finally
		{
			try {
				DbConnection.closeResultset(rsLineDtls);
			} catch (Exception e) {
				Utility.onEx_LOG_RET_NEW_EX(e, "getLineItemAttValues", "SCMDao", null, true, true);
			}
		}
		Utility.LOG(true,false,"Exiting getLineItemAttValues");
		return lineItemValueLst;
		
	}
	
	
	/**
	 * This method will get scm line details required for PR creation of service product id
	 * @param  conn,lineItemId
	 * @return ArrayList<PRCreatnSCMXmlDto>
    */
	public ArrayList<PRCreatnSCMXmlDto> getItemCodeDtlsValues(Connection conn,int lineItemId) throws SQLException{
		Utility.LOG(true,false,"Entering getItemCodeDtlsValues");
		ResultSet itemCodeDtls=null;
		ArrayList<PRCreatnSCMXmlDto> prCreatnSCMXmlLst=new ArrayList<PRCreatnSCMXmlDto>();
		PRCreatnSCMXmlDto prCreatnSCMXmlDtoObj;
		PreparedStatement lineItemIdPstmt =null;
		try {
					//get item code details
			lineItemIdPstmt = conn.prepareStatement(sqlGetItemCodeValues.toString());
			lineItemIdPstmt.setInt(1,lineItemId);
			itemCodeDtls = lineItemIdPstmt.executeQuery();
			while(itemCodeDtls.next()){
				prCreatnSCMXmlDtoObj=new PRCreatnSCMXmlDto();
				prCreatnSCMXmlDtoObj.setTrxId(itemCodeDtls.getInt("CHARGEID_SCM"));
				prCreatnSCMXmlDtoObj.setItemCode(itemCodeDtls.getString("ITEM_CODE"));
				prCreatnSCMXmlDtoObj.setQuantity(itemCodeDtls.getString("QUANTITY"));
				prCreatnSCMXmlDtoObj.setItemPrice((BigDecimal.valueOf((itemCodeDtls.getDouble("CHARGEVALUE"))).toPlainString()));
				prCreatnSCMXmlDtoObj.setDelToLoc(itemCodeDtls.getString("DELIVER_TO_LOCATN"));
				prCreatnSCMXmlDtoObj.setDestSubInv(itemCodeDtls.getString("SUBINVENTORY"));
				prCreatnSCMXmlDtoObj.setAopHead1(itemCodeDtls.getString("AOP_BUDGET_HEAD1"));
				prCreatnSCMXmlDtoObj.setAopHead2(itemCodeDtls.getString("AOP_BUDGET_HEAD2"));
				prCreatnSCMXmlDtoObj.setAopYear(itemCodeDtls.getString("AOP_YEAR"));
				prCreatnSCMXmlDtoObj.setPrId(itemCodeDtls.getInt("PR_ID"));
				prCreatnSCMXmlLst.add(prCreatnSCMXmlDtoObj);
			}
		} catch (SQLException e) {
			Utility.onEx_LOG_RET_NEW_EX(e, "getItemCodeDtlsValues", "SCMDao", "sqlState", true, true);
		} catch (Exception e) {
			Utility.onEx_LOG_RET_NEW_EX(e, "getItemCodeDtlsValues", "SCMDao", null, true, true);
		}finally
		{
			try {
				DbConnection.closeResultset(itemCodeDtls);
			} catch (Exception e) {
				Utility.onEx_LOG_RET_NEW_EX(e, "getItemCodeDtlsValues", "SCMDao", null, true, true);
			}
		}
		Utility.LOG(true,false,"Exiting getItemCodeDtlsValues");
		return prCreatnSCMXmlLst;
		
	}
	
	/**
	 * This method will insert created xml in TSCM_XMLSEND_STATUS and set status of previously created xml for the same spid to 1
	 * @param  conn,orderNumber,serviceId,spId,eventTypeId,finalXML,operationExec
	 * @return Nothing.
    */
	public int insertSCMXML(Connection conn,ArrayList<SCMXMLDto> scmXMLDtoLst,String eventTypeId) throws SQLException{
		Utility.LOG(true,false,"Entering insertSCMXML");
		//CallableStatement psInsertXML =null;
		//CallableStatement psUpdateXML =null;
		PreparedStatement psInsertXML =null;
		SCMXMLDto scmXMLDtoObj=new SCMXMLDto();
		int returnStatus=0;
		try {
			psInsertXML=conn.prepareCall(sqlInsertPRCreationSCMXML);
			int lstSize=scmXMLDtoLst.size();
			for(int i=0;i<lstSize;i++){
				scmXMLDtoObj=(SCMXMLDto)scmXMLDtoLst.get(i);
				psInsertXML.setInt(1, scmXMLDtoObj.getOrderNumber());
				psInsertXML.setInt(2, scmXMLDtoObj.getServiceId());
				psInsertXML.setInt(3, scmXMLDtoObj.getServicePrdId());
				psInsertXML.setString(4, eventTypeId);
				psInsertXML.setString(5, scmXMLDtoObj.getOperatnExec());
				psInsertXML.addBatch();
			}
			psInsertXML.executeBatch();
			returnStatus=AppConstants.SCM_XML_PROCESSED_SUCCESS;
		} catch (BatchUpdateException e) {
			returnStatus=AppConstants.SCM_XML_PROCESSED_FAILURE;
			Utility.onEx_LOG_RET_NEW_EX(e, "insertSCMXML", "SCMDao", "sqlState", true, true);
		} catch (SQLException e) {
			returnStatus=AppConstants.SCM_XML_PROCESSED_FAILURE;
			Utility.onEx_LOG_RET_NEW_EX(e, "insertSCMXML", "SCMDao", "sqlState", true, true);
		} catch (Exception e) {
			returnStatus=AppConstants.SCM_XML_PROCESSED_FAILURE;
			Utility.onEx_LOG_RET_NEW_EX(e, "insertSCMXML", "SCMDao", null, true, true);
		}finally
		{
			try {
				DbConnection.closePreparedStatement(psInsertXML);
			} catch (Exception e) {
				Utility.onEx_LOG_RET_NEW_EX(e, "insertSCMXML", "SCMDao", null, true, true);
				
			}
		}
		Utility.LOG(true,false,"Exiting insertSCMXML");
		return returnStatus;
	}
	
	/**
	 * This method will update Attribute value according to attribute description passed
	 * @param  conn,orderNumber,serviceId,spId,eventTypeId,finalXML,operationExec,attDesc
	 * @return Nothing.
    */
	public int updateAttVal(Connection conn,ArrayList<LineItemValueDTO> lineItemValueLst,String attDesc) throws SQLException{
		Utility.LOG(true,false,"Entering updateAttVal");
		PreparedStatement psUpdateNfaNum =null;
		PreparedStatement psUpdateNfaNumHistory =null;
		int returnStatus=0;
		LineItemValueDTO lineItemValueDTOObj=new LineItemValueDTO();
		try {
			conn.setAutoCommit(false);
			int lstSize=lineItemValueLst.size();
			psUpdateNfaNum=conn.prepareCall(sqlUpdateAttVal);
			psUpdateNfaNumHistory=conn.prepareCall(sqlUpdateAttValHistory);
			for(int i=0;i<lstSize;i++){
				lineItemValueDTOObj=(LineItemValueDTO)lineItemValueLst.get(i);
				
				psUpdateNfaNum.setString(1, lineItemValueDTOObj.getAttValue());
				psUpdateNfaNum.setInt(2, lineItemValueDTOObj.getSpId());
				psUpdateNfaNum.setString(3, attDesc);
				psUpdateNfaNum.setLong(4, lineItemValueDTOObj.getServiceDtlId());
				psUpdateNfaNum.addBatch();
				
				
				psUpdateNfaNumHistory.setString(1, lineItemValueDTOObj.getAttValue());
				psUpdateNfaNumHistory.setInt(2, lineItemValueDTOObj.getService_id());
				psUpdateNfaNumHistory.setInt(3, lineItemValueDTOObj.getSpId());
				psUpdateNfaNumHistory.setString(4,attDesc);
				psUpdateNfaNumHistory.setLong(5, lineItemValueDTOObj.getServiceDtlId());
				psUpdateNfaNumHistory.addBatch();
				
			}
			psUpdateNfaNum.executeBatch();
			psUpdateNfaNumHistory.executeBatch();
			returnStatus=AppConstants.SCM_XML_PROCESSED_SUCCESS;
			if(returnStatus==AppConstants.SCM_XML_PROCESSED_SUCCESS){
				conn.commit();
			}
		} catch (SQLException e) {
			returnStatus=AppConstants.SCM_XML_PROCESSED_FAILURE;
			Utility.onEx_LOG_RET_NEW_EX(e, "updateAttVal", "SCMDao", "sqlState", true, true);
		} catch (Exception e) {
			returnStatus=AppConstants.SCM_XML_PROCESSED_FAILURE;
			Utility.onEx_LOG_RET_NEW_EX(e, "updateAttVal", "SCMDao", null, true, true);
		}finally
		{
			try {
				DbConnection.closePreparedStatement(psUpdateNfaNum);
				DbConnection.closePreparedStatement(psUpdateNfaNumHistory);
			} catch (Exception e) {
				returnStatus=AppConstants.SCM_XML_PROCESSED_FAILURE;
				Utility.onEx_LOG_RET_NEW_EX(e, "updateNfaNumber", "SCMDao", null, true, true);
			}
		}
		Utility.LOG(true,false,"Exiting updateNfaNumber");
		System.out.println("No of update query got executed is::"+returnStatus);
		return returnStatus;
	}

	/**
	 * This method will update PR line details received from SCM
	 * @param  conn,scmXMLDtoObjCurr,prId
	 * @return Nothing.
    */
	public int updatePrItemDtls(Connection conn,SCMRecXmlDto scmXMLDtoObjCurr,int prId){
		Utility.LOG(true,false,"Entering updatePrItemDtls");
		int returnStatus=0;
		CallableStatement csUpdatePrItems=null;
			
			try{
				String sql=sql_updatePRChargeDtls;
				csUpdatePrItems= conn.prepareCall(sql);
				csUpdatePrItems.setLong(1, scmXMLDtoObjCurr.getServicePrdId());
				for (PRCreatnSCMXmlDto prCreatnSCMXmlDtoObj :scmXMLDtoObjCurr.getPrCreatnSCMXmlDtoList()){
					csUpdatePrItems.setString(2, prCreatnSCMXmlDtoObj.getItemCode());
					csUpdatePrItems.setLong(3, Integer.parseInt(prCreatnSCMXmlDtoObj.getQuantity()));
					csUpdatePrItems.setDouble(4, Double.parseDouble(prCreatnSCMXmlDtoObj.getItemPrice()));
					
					csUpdatePrItems.setString(5, prCreatnSCMXmlDtoObj.getDelToLoc());
					csUpdatePrItems.setString(6, prCreatnSCMXmlDtoObj.getDestSubInv());
					csUpdatePrItems.setString(7, prCreatnSCMXmlDtoObj.getAopHead1());
					csUpdatePrItems.setString(8, prCreatnSCMXmlDtoObj.getAopHead2());
					csUpdatePrItems.setString(9, prCreatnSCMXmlDtoObj.getAopYear());
					csUpdatePrItems.setString(10, prCreatnSCMXmlDtoObj.getTrdPartyPoNum());
					
					csUpdatePrItems.setString(11, prCreatnSCMXmlDtoObj.getDateOfPOTP());
					csUpdatePrItems.setDouble(12, Double.parseDouble(prCreatnSCMXmlDtoObj.getPoAmtToTP()));
					csUpdatePrItems.setString(13, scmXMLDtoObjCurr.getOperatnExec());
					csUpdatePrItems.setLong(14, prCreatnSCMXmlDtoObj.getTrxId());
					csUpdatePrItems.setLong(15,prId);
					csUpdatePrItems.setLong(16,prCreatnSCMXmlDtoObj.getScmLineId());
					csUpdatePrItems.setString(17,scmXMLDtoObjCurr.getCircle());
					csUpdatePrItems.addBatch();
				}
				csUpdatePrItems.executeBatch();
				returnStatus=AppConstants.SCM_XML_PROCESSED_SUCCESS;
			}catch (BatchUpdateException e) {
				returnStatus=AppConstants.SCM_XML_PROCESSED_FAILURE;
				try {
					conn.rollback();
				}catch (SQLException sql){
					Utility.onEx_LOG_RET_NEW_EX(sql, "updatePrItemDtls", "SCMDao", "sqlState", true, true);
				}catch (Exception exc){
					Utility.onEx_LOG_RET_NEW_EX(exc,"updatePrItemDtls", "SCMDao", null, true, true);
				}
				e.getNextException();
				Utility.onEx_LOG_RET_NEW_EX(e, "updatePrItemDtls", "SCMDao", null, true, true);
			} catch (SQLException e) {
				returnStatus=AppConstants.SCM_XML_PROCESSED_FAILURE;
				try {
					conn.rollback();
				}catch (SQLException sql){
					Utility.onEx_LOG_RET_NEW_EX(sql, "updatePrItemDtls", "SCMDao", "sqlState", true, true);
				}catch (Exception exc){
					Utility.onEx_LOG_RET_NEW_EX(exc,"updatePrItemDtls", "SCMDao", "sqlState", true, true);
				}
				Utility.onEx_LOG_RET_NEW_EX(e, "updatePrItemDtls", "SCMDao", "sqlState", true, true);
			} catch (Exception e) {
				returnStatus=AppConstants.SCM_XML_PROCESSED_FAILURE;
				try {
					conn.rollback();
				}catch (SQLException sql){
					Utility.onEx_LOG_RET_NEW_EX(sql, "updatePrItemDtls", "SCMDao", "sqlState", true, true);
				}catch (Exception exc){
					Utility.onEx_LOG_RET_NEW_EX(exc, "updatePrItemDtls", "SCMDao", null, true, true);
				}
				Utility.onEx_LOG_RET_NEW_EX(e,"updatePrItemDtls", "SCMDao", null, true, true);
			}finally
			{
				try {
					DbConnection.closeCallableStatement(csUpdatePrItems);
				} catch (Exception e) {
					returnStatus=AppConstants.SCM_XML_PROCESSED_FAILURE;
					Utility.onEx_LOG_RET_NEW_EX(e, "updatePrItemDtls", "SCMDao", null, true, true);
					
				}
			}
			Utility.LOG(true,false,"Exiting updatePrItemDtls");
			return returnStatus;
	}
		
	/**
	 * This method will update PR attribute details received from SCM
	 * @param  conn,scmXMLDtoObjCurr,prId
	 * @return Nothing.
    */
		public int updtePrAttrDtl(Connection conn,SCMRecXmlDto scmXMLDtoObjCurr,int prId){
			Utility.LOG(true,false,"Entering updtePrAttrDtl");
			CallableStatement csUpdtePrAttrDtl=null;
			int returnStatus=0;
			try{
				String sql2=sql_updatePRAttributeOrdrDtls;
				csUpdtePrAttrDtl= conn.prepareCall(sql2);
				csUpdtePrAttrDtl.setLong(1, scmXMLDtoObjCurr.getOrderNumber());
				csUpdtePrAttrDtl.setLong(2, scmXMLDtoObjCurr.getServiceId());
				csUpdtePrAttrDtl.setLong(3, scmXMLDtoObjCurr.getServicePrdId());
				System.out.println("PR Receive prCreatnSCMXmlDtoObj size "+scmXMLDtoObjCurr.getAttrMap().size());
				for (Map.Entry<String, String> entry : scmXMLDtoObjCurr.getAttrMap().entrySet()){
					System.out.println("PR Receive prCreatnSCMXmlDtoObj getOrderNumber "+scmXMLDtoObjCurr.getOrderNumber());
					System.out.println("PR Receive prCreatnSCMXmlDtoObj getServiceId "+scmXMLDtoObjCurr.getServiceId());
					System.out.println("PR Receive prCreatnSCMXmlDtoObj getServicePrdId "+scmXMLDtoObjCurr.getServicePrdId());
					System.out.println("PR Receive prCreatnSCMXmlDtoObj entry.getKey() "+entry.getKey());
					System.out.println("PR Receive prCreatnSCMXmlDtoObj entry.getValue() "+entry.getValue());
					System.out.println("PR Receive prCreatnSCMXmlDtoObj prId "+prId);
					csUpdtePrAttrDtl.setString(4,entry.getKey());
					csUpdtePrAttrDtl.setString(5,entry.getValue());
					csUpdtePrAttrDtl.setLong(6, prId);
					csUpdtePrAttrDtl.addBatch();
				}
			csUpdtePrAttrDtl.executeBatch();
			returnStatus=AppConstants.SCM_XML_PROCESSED_SUCCESS;
			} catch (BatchUpdateException e) {
				returnStatus=AppConstants.SCM_XML_PROCESSED_FAILURE;
				try {
					conn.rollback();
				}catch (SQLException sql){
					Utility.onEx_LOG_RET_NEW_EX(sql, "updtePrAttrDtl", "SCMDao", "sqlState", true, true);
				}catch (Exception exc){
					Utility.onEx_LOG_RET_NEW_EX(exc,"updtePrAttrDtl", "SCMDao", "sqlState", true, true);
				}
				e.getNextException();
				Utility.onEx_LOG_RET_NEW_EX(e, "updtePrAttrDtl", "SCMDao", "sqlState", true, true);
			}catch (SQLException e) {
				returnStatus=AppConstants.SCM_XML_PROCESSED_FAILURE;
				try {
					conn.rollback();
				}catch (SQLException sql){
					Utility.onEx_LOG_RET_NEW_EX(sql, "updtePrAttrDtl", "SCMDao", "sqlState", true, true);
				}catch (Exception exc){
					Utility.onEx_LOG_RET_NEW_EX(exc, "updtePrAttrDtl", "SCMDao","sqlState", true, true);
				}
				Utility.onEx_LOG_RET_NEW_EX(e, "updtePrAttrDtl", "SCMDao", "sqlState", true, true);
			} catch (Exception e) {
				returnStatus=AppConstants.SCM_XML_PROCESSED_FAILURE;
				try {
					conn.rollback();
				}catch (SQLException sql){
					Utility.onEx_LOG_RET_NEW_EX(sql,"updtePrAttrDtl", "SCMDao", null, true, true);
				}catch (Exception exc){
					Utility.onEx_LOG_RET_NEW_EX(exc, "updtePrAttrDtl", "SCMDao",null, true, true);
					
				}
				Utility.onEx_LOG_RET_NEW_EX(e, "updtePrAttrDtl", "SCMDao", null, true, true);
			}finally
			{
				try {
					DbConnection.closeCallableStatement(csUpdtePrAttrDtl);
				} catch (Exception e) {
					returnStatus=AppConstants.SCM_XML_PROCESSED_FAILURE;
					Utility.onEx_LOG_RET_NEW_EX(e, "updtePrAttrDtl", "SCMDao",  null, true, true);
				}
			}
			Utility.LOG(true,false,"Exiting updtePrAttrDtl");
			return returnStatus;
	}	
		
		/**
		 * This method will update all the details of PR received from SCM in case of PR creation
		 * @param  conn,scmXMLDtoObjCurr
		 * @return returnStatus.
	    */
		public  int updateSCMXMLDataPrCreate(Connection conn,SCMRecXmlDto scmXMLDtoObjCurr) {
			Utility.LOG(true,false,"Entering updateSCMXMLDataPrCreate");
			int returnStatus=0;
			PreparedStatement psGetPrId =null;
			PreparedStatement psDisablePr =null;
			PreparedStatement psPrMainDtls=null;
			PreparedStatement psInsertSent=null;
			PreparedStatement psDeleteSent=null;
			PreparedStatement psChkSpId =null;
			PreparedStatement psGetOrderStage =null;
			ResultSet rs=null;
			int prId=0;
			StringBuilder prType=new StringBuilder();
			boolean spIdExists=false;
			String orderStage=new String();
			try{
				psChkSpId = conn.prepareStatement(sqlChkSpId.toString());
				psChkSpId.setLong(1,scmXMLDtoObjCurr.getOrderNumber());
				psChkSpId.setLong(2,scmXMLDtoObjCurr.getServiceId());
				psChkSpId.setLong(3,scmXMLDtoObjCurr.getServicePrdId());
				rs = psChkSpId.executeQuery();
				while(rs.next()){
					spIdExists=true;
				}
				if(spIdExists){
					conn.setAutoCommit(false);
					//get order stage
					psGetOrderStage = conn.prepareStatement(sqlGetServiceLvlDtls.toString());
					psGetOrderStage.setLong(1,scmXMLDtoObjCurr.getOrderNumber());
					rs = psGetOrderStage.executeQuery();
							
					while(rs.next()){
						orderStage=rs.getString("M6_FX_PROGRESS_STATUS");
					}
					
					//get Pr id
					psGetPrId = conn.prepareStatement(sqlGetPrId);
					psGetPrId.setLong(1,scmXMLDtoObjCurr.getServicePrdId());
					psGetPrId.setString(2,scmXMLDtoObjCurr.getPrNumber());
					psGetPrId.setString(3,scmXMLDtoObjCurr.getCircle());
					rs = psGetPrId.executeQuery();
							
					while(rs.next()){
						prId=rs.getInt("PR_ID");
						prType.append(rs.getString("PR_TYPE"));
					}
					DbConnection.closeResultset(rs);
					DbConnection.closePreparedStatement(psGetPrId);
					
					//disable already existing PR and PR items

					psDisablePr=conn.prepareCall(sqlInactiveExistingPr);
					psDisablePr.setInt(1, Integer.parseInt(AppConstants.PR_INACTIVE));
					psDisablePr.setLong(2,scmXMLDtoObjCurr.getServicePrdId());
					psDisablePr.setLong(3,prId);
					psDisablePr.executeUpdate();
					

					DbConnection.closePreparedStatement(psDisablePr);
					
					//inactivate all line items in table, existing line items in xml will be activated in procedure called after this
					String sql=sqlUpdatePRItemsMain;//pr id will be zero initially in main table before sending to scm so setting spid also
					psPrMainDtls= conn.prepareCall(sql);
					psPrMainDtls.setInt(1, prId);
					psPrMainDtls.setInt(2, scmXMLDtoObjCurr.getServicePrdId());
					psPrMainDtls.executeUpdate();
					
					
					//if Pr does not exists then insert else update
					if(prId==0){
						sql=sqlInsertPRDtlsMain;
						psPrMainDtls= conn.prepareCall(sql);
						psPrMainDtls.setLong(1, scmXMLDtoObjCurr.getServicePrdId());
						psPrMainDtls.setString(2, scmXMLDtoObjCurr.getPrNumber());
						psPrMainDtls.setString(3, scmXMLDtoObjCurr.getCircle());
						psPrMainDtls.setString(4, scmXMLDtoObjCurr.getPrStatus());
						psPrMainDtls.setString(5, scmXMLDtoObjCurr.getOperatnExec());
						psPrMainDtls.setInt(6, Integer.parseInt(AppConstants.PR_ACTIVE));
						psPrMainDtls.executeUpdate();
						
						//get Pr id
						psGetPrId = conn.prepareStatement(sqlGetPrId);
						psGetPrId.setLong(1,scmXMLDtoObjCurr.getServicePrdId());
						psGetPrId.setString(2,scmXMLDtoObjCurr.getPrNumber());
						psGetPrId.setString(3,scmXMLDtoObjCurr.getCircle());
						rs = psGetPrId.executeQuery();
								
						while(rs.next()){
							prId=rs.getInt("PR_ID");
						}
						DbConnection.closeResultset(rs);
						DbConnection.closePreparedStatement(psGetPrId);
						
						//copy pr line details in sent table
						psInsertSent=conn.prepareCall(sqlCopyPrLineDtls.toString());
						psInsertSent.setInt(1,scmXMLDtoObjCurr.getServicePrdId());
						psInsertSent.executeUpdate();
						DbConnection.closePreparedStatement(psInsertSent);
						//delete sent line details from front table,received line details will be inserted or updated  in procedure called after this 
						psDeleteSent=conn.prepareCall(sqlDeleteSentLine.toString());
						psDeleteSent.setInt(1,scmXMLDtoObjCurr.getServicePrdId());
						psDeleteSent.executeUpdate();
						DbConnection.closePreparedStatement(psDeleteSent);
						
					}else{
						//update status of PR
						sql=sqlUpdatePRDtlsMain;
						psPrMainDtls= conn.prepareCall(sql);
						psPrMainDtls.setString(1,scmXMLDtoObjCurr.getPrStatus());
						if(orderStage!=null && !orderStage.equalsIgnoreCase("") && 
								(orderStage.equalsIgnoreCase(AppConstants.ORDER_STAGE_CANCELLED) || orderStage.equalsIgnoreCase(AppConstants.ORDER_STAGE_M6_CANCELLED))){
							psPrMainDtls.setInt(2, Integer.parseInt(AppConstants.PR_INACTIVE));
						}else{
							psPrMainDtls.setInt(2, Integer.parseInt(AppConstants.PR_ACTIVE));
						}
						psPrMainDtls.setString(3,scmXMLDtoObjCurr.getOperatnExec());
						psPrMainDtls.setInt(4, prId);
						psPrMainDtls.executeUpdate();
						DbConnection.closePreparedStatement(psPrMainDtls);
					}
					
					
					//update(PR_CREATE) or insert(PR_RENEWAL) items details
					//initially items will be inactive in the main table and will be activted in procedure called in this
					returnStatus=updatePrItemDtls(conn, scmXMLDtoObjCurr, prId);
					if(returnStatus==AppConstants.SCM_XML_PROCESSED_SUCCESS){
						//update or insert attribute details	
						returnStatus=updtePrAttrDtl(conn, scmXMLDtoObjCurr, prId);
						if(returnStatus==AppConstants.SCM_XML_PROCESSED_SUCCESS){
							//updatePrID
							//if order cancelled then set pr id=0 and isactive=0....
							SCMXMLDto scmXMLDtoObj=new SCMXMLDto();
							ArrayList<SCMXMLDto> scmXMLDtoLst=new ArrayList<SCMXMLDto>();
							if(orderStage!=null && !orderStage.equalsIgnoreCase("") && 
									(orderStage.equalsIgnoreCase(AppConstants.ORDER_STAGE_CANCELLED) || orderStage.equalsIgnoreCase(AppConstants.ORDER_STAGE_M6_CANCELLED))){
								prId=0;
								scmXMLDtoObj.setScmProgressStatus(AppConstants.PR_SCM_FAILURE);
								scmXMLDtoObj.setPrId(0);
								scmXMLDtoObj.setServicePrdId(scmXMLDtoObjCurr.getServicePrdId());
								scmXMLDtoLst.add(scmXMLDtoObj);
								returnStatus=updateRcvdMessage(conn, scmXMLDtoLst);
							}else{
								scmXMLDtoObj.setScmProgressStatus(AppConstants.PR_SCM_SUCCESS);
								scmXMLDtoObj.setPrId(prId);
								scmXMLDtoObj.setServicePrdId(scmXMLDtoObjCurr.getServicePrdId());
								scmXMLDtoLst.add(scmXMLDtoObj);
								returnStatus=updateRcvdMessage(conn,scmXMLDtoLst);
							}
							
							if(returnStatus==AppConstants.SCM_XML_PROCESSED_SUCCESS){
								conn.commit();
							}
						}
					}
				}
				
			} catch (SQLException e) {
				returnStatus=AppConstants.SCM_XML_PROCESSED_FAILURE;
				try {
					conn.rollback();
				}catch (SQLException sql){
					Utility.onEx_LOG_RET_NEW_EX(sql, "updateSCMXMLDataPrCreate", "SCMDao", "sqlState", true, true);
				}catch (Exception exc){
					Utility.onEx_LOG_RET_NEW_EX(exc,  "updateSCMXMLDataPrCreate", "SCMDao", null, true, true);
				}
				Utility.onEx_LOG_RET_NEW_EX(e, "updateSCMXMLDataPrCreate", "SCMDao", null, true, true);
			} catch (Exception e) {
				returnStatus=AppConstants.SCM_XML_PROCESSED_FAILURE;
				try {
					conn.rollback();
				}catch (SQLException sql){
					Utility.onEx_LOG_RET_NEW_EX(sql, "updateSCMXMLDataPrCreate", "SCMDao", "sqlState", true, true);
				}catch (Exception exc){
					Utility.onEx_LOG_RET_NEW_EX(exc,  "updateSCMXMLDataPrCreate", "SCMDao",null, true, true);
				}
				Utility.onEx_LOG_RET_NEW_EX(e,  "updateSCMXMLDataPrCreate", "SCMDao", null, true, true);
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
			Utility.LOG(true,false,"Exiting updateSCMXMLDataPrCreate");
			return returnStatus;
		}
		
		/**
		 *  This method will save status in IOE.TPOSERVICEDETAILS table
		 * @param  conn, message(message to be saved), readResponseXMLId(file id of received file)
		 * @return returnStatus.
	    */
		public int updateEiRcvdStat(Connection conn,String scmProgStat,int readResponseXMLId){
			Utility.LOG(true,false,"Entering updateEiRcvdStat");
			int returnStatus=0;
			int servicePrdId=0;
			try{
				//conn.setAutoCommit(false);
				servicePrdId=getSpId(conn, readResponseXMLId);
				SCMXMLDto scmXMLDtoObj=new SCMXMLDto();
				ArrayList<SCMXMLDto> scmXMLDtoLst=new ArrayList<SCMXMLDto>();
				scmXMLDtoObj.setScmProgressStatus(scmProgStat);
				scmXMLDtoObj.setPrId(0);
				scmXMLDtoObj.setServicePrdId(servicePrdId);
				scmXMLDtoLst.add(scmXMLDtoObj);
				returnStatus=updateRcvdMessage(conn,scmXMLDtoLst);
				//conn.commit();
			} catch (Exception e) {
				returnStatus=AppConstants.SCM_XML_PROCESSED_FAILURE;
				try {
					conn.rollback();
				}catch (SQLException sql){
					Utility.onEx_LOG_RET_NEW_EX(sql, "updateSCMXMLDataPrCreate", "SCMDao", "sqlState", true, true);
				}catch (Exception exc){
					Utility.onEx_LOG_RET_NEW_EX(exc, "updateSCMXMLDataPrCreate", "SCMDao", null, true, true);
				}
				Utility.onEx_LOG_RET_NEW_EX(e, "updateSCMXMLDataPrCreate", "SCMDao", null, true, true);
			}
			Utility.LOG(true,false,"Exiting updateEiRcvdStat");
			return returnStatus;
		}
		
		/**
		 *  This method will save status in IOE.TPOSERVICEDETAILS table
		 * @param  conn, servicePrdId,scmProgStat(message),prId(latest pr id for this line item)
		 * @return returnStatus.
	    */
		public int updateRcvdMessage(Connection conn,ArrayList<SCMXMLDto> scmXMLDtoLst){
			Utility.LOG(true,false,"Entering updateRcvdMessage");
			int returnStatus=0;
			PreparedStatement psUpdateScmStat =null;
			SCMXMLDto scmXMLDtoObj=new SCMXMLDto();
			try{
				psUpdateScmStat=conn.prepareCall(sqlUpdateEiStatOrdr);
				int lstSize=scmXMLDtoLst.size();
				for(int i=0;i<lstSize;i++){
					scmXMLDtoObj=(SCMXMLDto)scmXMLDtoLst.get(i);
					psUpdateScmStat.setString(1, scmXMLDtoObj.getScmProgressStatus());
					psUpdateScmStat.setInt(2, scmXMLDtoObj.getPrId());
					psUpdateScmStat.setInt(3, scmXMLDtoObj.getServicePrdId());
					psUpdateScmStat.addBatch();
				}
				psUpdateScmStat.executeBatch();
				returnStatus=AppConstants.SCM_XML_PROCESSED_SUCCESS;
			} catch (SQLException e) {
				returnStatus=AppConstants.SCM_XML_PROCESSED_FAILURE;
				Utility.onEx_LOG_RET_NEW_EX(e, "updateRcvdMessage", "SCMDao", "sqlState", true, true);
			} catch (Exception e) {
				returnStatus=AppConstants.SCM_XML_PROCESSED_FAILURE;
				Utility.onEx_LOG_RET_NEW_EX(e, "updateRcvdMessage", "SCMDao", null, true, true);
			}finally{
				try {
					DbConnection.closePreparedStatement(psUpdateScmStat);
				} catch (Exception e) {
					returnStatus=AppConstants.SCM_XML_PROCESSED_FAILURE;
					Utility.onEx_LOG_RET_NEW_EX(e, "updateRcvdMessage", "SCMDao", null, true, true);
				}
			}
			Utility.LOG(true,false,"Exiting updateRcvdMessage");
			return returnStatus;
		}

		/**
		 *  This method will save progress status in IOE.TPRODUCTLINECHARGEVALUE_SCM table
		 * @param  conn, servicePrdId,scmProgStat(message),prId(latest pr id for this line item),mapParam(chargeId and error mapping)
		 * @return returnStatus.
	    */
		public int updateScmRcvdStat(Connection conn,int servicePrdId,String scmProgStat,int prId,HashMap<String,String> mapParam){
			Utility.LOG(true,false,"Entering updateScmRcvdStat");
			int returnStatus=0;
			PreparedStatement psUpdateScmLineStat=null;
			try{
				//conn.setAutoCommit(false);
				
				//update line message PR creation and PR Reuse
				psUpdateScmLineStat=conn.prepareCall(sqlUpdateLineMessage);
				for (Map.Entry<String, String> entry : mapParam.entrySet())
				{
					psUpdateScmLineStat.setString(1, entry.getValue());
					psUpdateScmLineStat.setLong(2, Integer.parseInt(entry.getKey()));
					psUpdateScmLineStat.addBatch();
				}
				psUpdateScmLineStat.executeBatch();
				
				//update line item message
				SCMXMLDto scmXMLDtoObj=new SCMXMLDto();
				ArrayList<SCMXMLDto> scmXMLDtoLst=new ArrayList<SCMXMLDto>();
				scmXMLDtoObj.setScmProgressStatus(scmProgStat);
				scmXMLDtoObj.setPrId(prId);
				scmXMLDtoObj.setServicePrdId(servicePrdId);
				scmXMLDtoLst.add(scmXMLDtoObj);
				returnStatus=updateRcvdMessage(conn, scmXMLDtoLst);
				
			} catch (SQLException e) {
				returnStatus=AppConstants.SCM_XML_PROCESSED_FAILURE;
				try {
					conn.rollback();
				}catch (SQLException sql){
					Utility.onEx_LOG_RET_NEW_EX(sql, "updateScmRcvdStat", "SCMDao", "sqlState", true, true);
				}catch (Exception exc){
					Utility.onEx_LOG_RET_NEW_EX(exc, "updateScmRcvdStat", "SCMDao", null, true, true);
				}
				Utility.onEx_LOG_RET_NEW_EX(e, "updateScmRcvdStat", "SCMDao",null, true, true);
			} catch (Exception e) {
				returnStatus=AppConstants.SCM_XML_PROCESSED_FAILURE;
				try {
					conn.rollback();
				}catch (SQLException sql){
					Utility.onEx_LOG_RET_NEW_EX(sql,"updateScmRcvdStat", "SCMDao", "sqlState", true, true);
				}catch (Exception exc){
					Utility.onEx_LOG_RET_NEW_EX(exc, "updateScmRcvdStat", "SCMDao", null, true, true);
				}
				Utility.onEx_LOG_RET_NEW_EX(e, "updateScmRcvdStat", "SCMDao", null, true, true);
			}
			Utility.LOG(true,false,"Exiting updateScmRcvdStat");
			return returnStatus;
		}
		
		
		/**
		 *  This method will get Nfa Number and other details for the pr ID passed
		 * @param  conn, servicePrdId,scmProgStat(message),prId(latest pr id for this line item),mapParam(chargeId and error mapping)
		 * @return HashMap<String,String>(details fetched).
	    */
		public HashMap<String,String> getCurrentPrDtls(Connection conn,int spId) throws SQLException{
			Utility.LOG(true,false,"Entering getCurrentPrDtls");
			ResultSet rs=null;
			HashMap<String,String> currentPrDtlsMap=new HashMap<String,String>();
			PreparedStatement nfaPstmt =null;
			try {
				nfaPstmt = conn.prepareStatement(sqlGetCurrentPrDtls.toString());
				nfaPstmt.setInt(1,spId);
				nfaPstmt.setString(2,Utility.getAppConfigValue(AppConstants.NFA_NUMBER_ATT_DESC_KEY));
				rs = nfaPstmt.executeQuery();
				while(rs.next()){
					currentPrDtlsMap.put("nfaNumber", rs.getString("ATTVALUE"));
					currentPrDtlsMap.put("prNumber", rs.getString("PR_NUMBER"));
					currentPrDtlsMap.put("circle", rs.getString("CIRCLE"));
					currentPrDtlsMap.put("oldNfaNumber", rs.getString("ATTVALUE_OLD"));
				}
			} catch (SQLException e) {
				Utility.onEx_LOG_RET_NEW_EX(e, "getOldNfaNumber", "SCMDao", "sqlState", true, true);
			} catch (Exception e) {
				Utility.onEx_LOG_RET_NEW_EX(e, "getOldNfaNumber", "SCMDao", null, true, true);
			}finally
			{
				try {
					DbConnection.closeResultset(rs);
					DbConnection.closePreparedStatement(nfaPstmt);
				} catch (Exception e) {
					Utility.onEx_LOG_RET_NEW_EX(e,  "getOldNfaNumber", "SCMDao", null, true, true);
				}
			}
			Utility.LOG(true,false,"Exiting getOldNfaNumber");
			return currentPrDtlsMap;
			
		}
		
		/**
		 * This method will update all the details of PR received from SCM in case of PR reuse
		 * @param  conn,scmXMLDtoObjCurr
		 * @return returnStatus.
	    */
		public  int updateSCMXMLDataPrReuse(Connection conn,SCMRecXmlDto scmXMLDtoObjCurr) {
			Utility.LOG(true,false,"Entering updateSCMXMLDataPrReuse");
			int returnStatus=0;
			PreparedStatement psGetPrId =null;
			PreparedStatement psUpdateScmLineStat =null;
			PreparedStatement psUpdateScmAtt =null;
			PreparedStatement psPrMainDtls=null;
			PreparedStatement psGetOrderStage=null;
			ResultSet rs=null;
			int newPrId=0;
			int oldPrId=0;
			int oldPrActive=0;
			StringBuilder prStatus=new StringBuilder();
			StringBuilder prType=new StringBuilder();
			String orderStage=new String();
			try{
				conn.setAutoCommit(false);
				
				//get order stage
				psGetOrderStage = conn.prepareStatement(sqlGetServiceLvlDtls.toString());
				psGetOrderStage.setLong(1,scmXMLDtoObjCurr.getOrderNumber());
				rs = psGetOrderStage.executeQuery();
						
				while(rs.next()){
					orderStage=rs.getString("M6_FX_PROGRESS_STATUS");
				}
				
				//updatePrID
				//if order cancelled then do not proceed
				if(orderStage!=null && !orderStage.equalsIgnoreCase("") && 
						(!orderStage.equalsIgnoreCase(AppConstants.ORDER_STAGE_CANCELLED) && !orderStage.equalsIgnoreCase(AppConstants.ORDER_STAGE_M6_CANCELLED))){
				
					//get Old Pr id and pr dtls
					psGetPrId = conn.prepareStatement(sqlGetPrId);
					psGetPrId.setLong(1,scmXMLDtoObjCurr.getOldServicePrdId());
					psGetPrId.setString(2,scmXMLDtoObjCurr.getPrNumber());
					psGetPrId.setString(3,scmXMLDtoObjCurr.getCircle());
					rs = psGetPrId.executeQuery();
					while(rs.next()){
						oldPrId=rs.getInt("PR_ID");
						prType.append(rs.getString("PR_TYPE"));
						prStatus.append(rs.getString("PR_STATUS"));
						oldPrActive=rs.getInt("ISACTIVE");
					}
					DbConnection.closeResultset(rs);
					DbConnection.closePreparedStatement(psGetPrId);
					
					//insert new row for pr for new nfa number
					String sql=sqlInsertPRDtlsMain;
					psPrMainDtls= conn.prepareCall(sql);
					psPrMainDtls.setLong(1, scmXMLDtoObjCurr.getServicePrdId());
					psPrMainDtls.setString(2, scmXMLDtoObjCurr.getPrNumber());
					psPrMainDtls.setString(3, scmXMLDtoObjCurr.getCircle());
					psPrMainDtls.setString(4, prStatus.toString());
					psPrMainDtls.setString(5, prType.toString());
					psPrMainDtls.setInt(6, Integer.parseInt(AppConstants.PR_ACTIVE));
					psPrMainDtls.executeUpdate();
					DbConnection.closePreparedStatement(psPrMainDtls);
						
					//get New Pr id
					psGetPrId = conn.prepareStatement(sqlGetPrId);
					psGetPrId.setLong(1,scmXMLDtoObjCurr.getServicePrdId());
					psGetPrId.setString(2,scmXMLDtoObjCurr.getPrNumber());
					psGetPrId.setString(3,scmXMLDtoObjCurr.getCircle());
					rs = psGetPrId.executeQuery();
								
					while(rs.next()){
						newPrId=rs.getInt("PR_ID");
					}
					DbConnection.closeResultset(rs);
					DbConnection.closePreparedStatement(psGetPrId);
					
					//copy pr line details in received table
					/*psInsertHistory=conn.prepareCall(sqlCopyPrLineDtls.toString());
					psInsertHistory.setInt(1,newPrId);
					psInsertHistory.setInt(2,oldPrId);
					DbConnection.closePreparedStatement(psInsertHistory);*/
					
					//update new pr id in line table
					psUpdateScmLineStat=conn.prepareCall(sqlUpdatePrReuseLineDtls);
					psUpdateScmLineStat.setString(1, AppConstants.PR_SCM_SUCCESS);
					psUpdateScmLineStat.setLong(2,newPrId  );
					psUpdateScmLineStat.setLong(3,oldPrId  );
					psUpdateScmLineStat.setLong(4, scmXMLDtoObjCurr.getServicePrdId());
					psUpdateScmLineStat.executeUpdate();
					DbConnection.closePreparedStatement(psUpdateScmLineStat);
					
					//insert attribute details	in received table
					psUpdateScmAtt=conn.prepareCall(sqlCopyPrAttDtls.toString());
					psUpdateScmAtt.setLong(1,newPrId);
					psUpdateScmAtt.setLong(2,oldPrId);
					psUpdateScmAtt.executeUpdate();
					DbConnection.closePreparedStatement(psUpdateScmAtt);
						
					SCMXMLDto scmXMLDtoObj=new SCMXMLDto();
					ArrayList<SCMXMLDto> scmXMLDtoLst=new ArrayList<SCMXMLDto>();
					scmXMLDtoObj.setScmProgressStatus(AppConstants.PR_SCM_SUCCESS);
					scmXMLDtoObj.setPrId(newPrId);
					scmXMLDtoObj.setServicePrdId(scmXMLDtoObjCurr.getServicePrdId());
					scmXMLDtoLst.add(scmXMLDtoObj);
					returnStatus=updateRcvdMessage(conn, scmXMLDtoLst);
					if(returnStatus==AppConstants.SCM_XML_PROCESSED_SUCCESS){
		
						//set status pr reuse of old pr in case of inactive
						if(oldPrActive==0){
							sql=sqlUpdatePRDtlsMain;
							psPrMainDtls= conn.prepareCall(sql);
							psPrMainDtls.setString(1,prStatus.toString());
							psPrMainDtls.setInt(2, Integer.parseInt(AppConstants.PR_REUSE_STATUS));
							psPrMainDtls.setString(3,prType.toString());
							psPrMainDtls.setInt(4, oldPrId);
							psPrMainDtls.executeUpdate();
							DbConnection.closePreparedStatement(psPrMainDtls);
						}
						conn.commit();
					}
				}
				
			} catch (SQLException e) {
				returnStatus=AppConstants.SCM_XML_PROCESSED_FAILURE;
				try {
					conn.rollback();
				}catch (SQLException sql){
					Utility.onEx_LOG_RET_NEW_EX(sql, "updateSCMXMLDataPrReuse", "SCMDao", "sqlState", true, true);
				}catch (Exception exc){
					Utility.onEx_LOG_RET_NEW_EX(exc, "updateSCMXMLDataPrReuse", "SCMDao", null, true, true);
				}
				Utility.onEx_LOG_RET_NEW_EX(e, "updateSCMXMLDataPrReuse", "SCMDao", null, true, true);
			} catch (Exception e) {
				returnStatus=AppConstants.SCM_XML_PROCESSED_FAILURE;
				try {
					conn.rollback();
				}catch (SQLException sql){
					Utility.onEx_LOG_RET_NEW_EX(sql,"updateSCMXMLDataPrReuse", "SCMDao", null, true, true);
				}catch (Exception exc){
					Utility.onEx_LOG_RET_NEW_EX(exc, "updateSCMXMLDataPrReuse", "SCMDao", null, true, true);
				}
				Utility.onEx_LOG_RET_NEW_EX(e, "updateSCMXMLDataPrReuse", "SCMDao", null, true, true);
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
			Utility.LOG(true,false,"Exiting updateSCMXMLDataPrReuse");
			return returnStatus;
		}
				
		/**
		 * This method will get XML received from SCM
		 * @param conn
		 * @return HashMap<Integer,String> (file Id,file Name)
	    */
		public HashMap<Integer,String> getNextReceiveXml(Connection conn) throws SQLException{
			Utility.LOG(true,false,"Entering getNextReceiveXml");
			ResultSet rs=null;
			HashMap<Integer,String> recXmlMap=new HashMap<Integer,String>();
			PreparedStatement psFileDtls =null;
			try {
				psFileDtls = conn.prepareStatement(sqlGetNextReceiveXml);
				rs = psFileDtls.executeQuery();
				while(rs.next()){
					recXmlMap.put(rs.getInt("ID"),rs.getString("FILENAME"));
				}
				Utility.LOG(true,false,"recXmlMap   : "+recXmlMap);
			} catch (SQLException e) {
				Utility.onEx_LOG_RET_NEW_EX(e, "SCMDao", "getNextReceiveXml", "sqlState", true, true);
			} catch (Exception e) {
				Utility.onEx_LOG_RET_NEW_EX(e,  "SCMDao", "getNextReceiveXml", null, true, true);
			}finally
			{
				try {
					DbConnection.closeResultset(rs);
					DbConnection.closePreparedStatement(psFileDtls);
				} catch (Exception e) {
					Utility.onEx_LOG_RET_NEW_EX(e, "SCMDao", "getNextReceiveXml",null, true, true);
				}
			}
			Utility.LOG(true,false,"Exiting getNextReceiveXml");
			return recXmlMap;
			
		}
		
		/**
		 * This method will update order number and service product id in IOE.TSCM_XMLRECEIVE_STATUS table
		 * @param  conn, orderNumber, spId, recId(file Id)
		 * @return returnStatus
	    */
		public int updateRecXmlDtls(Connection conn,int orderNumber,int spId,int recId,String nfaNumber,String operatnExec) throws SQLException{
			Utility.LOG(true,false,"Entering updateRecXmlDtls");
			PreparedStatement psUpdateDtls =null;
			int returnStatus=AppConstants.Fetch_Status_Found;
			try {
				//set status pr reuse of old pr
				String sql=sqlUpdateReceiveDtls;
				psUpdateDtls= conn.prepareCall(sql);
				psUpdateDtls.setInt(1,orderNumber);
				psUpdateDtls.setInt(2, spId);
				psUpdateDtls.setInt(3,returnStatus);
				psUpdateDtls.setString(4,nfaNumber);
				psUpdateDtls.setString(5, operatnExec);
				psUpdateDtls.setInt(6, recId);
				psUpdateDtls.executeUpdate();
				returnStatus=AppConstants.SCM_XML_PROCESSED_SUCCESS;
			} catch (SQLException e) {
				returnStatus=AppConstants.SCM_XML_PROCESSED_FAILURE;
				Utility.onEx_LOG_RET_NEW_EX(e, "SCMDao", "updateRecXmlDtls", "sqlState", true, true);
			} catch (Exception e) {
				returnStatus=AppConstants.SCM_XML_PROCESSED_FAILURE;
				Utility.onEx_LOG_RET_NEW_EX(e, "SCMDao", "updateRecXmlDtls", null, true, true);
			}finally
			{
				try {
					DbConnection.closePreparedStatement(psUpdateDtls);
				} catch (Exception e) {
					returnStatus=AppConstants.SCM_XML_PROCESSED_FAILURE;
					Utility.onEx_LOG_RET_NEW_EX(e, "SCMDao", "updateRecXmlDtls", null, true, true);
				}
			}
			Utility.LOG(true,false,"Exiting updateRecXmlDtls");
			return returnStatus;
			
		}
		
		/**
		 * This method will update status  in IOE.TSCM_XMLRECEIVE_STATUS table whether successfully processed or any failure
		 * @param conn
		 * @return Nothing.
	    */
		public void updateRecXmlStatus(Connection conn,int recId,int status) throws SQLException{
			Utility.LOG(true,false,"Entering updateRecXmlStatus");
			PreparedStatement psUpdateDtls =null;
			try {
				Utility.LOG(true,false,"Exiting updateRecXmlStatus getAutoCommit"+conn.getAutoCommit());
				Utility.LOG(true,false,"Exiting updateRecXmlStatus status"+status);
				Utility.LOG(true,false,"Exiting updateRecXmlStatus recId"+recId);
				String sql=sqlUpdateReceiveStatus;
				psUpdateDtls= conn.prepareCall(sql);
				psUpdateDtls.setInt(1,status);
				psUpdateDtls.setInt(2, recId);
				psUpdateDtls.executeUpdate();
				
			} catch (SQLException e) {
				Utility.onEx_LOG_RET_NEW_EX(e, "updateRecXmlStatus", "SCMDao", "sqlState", true, true);
			} catch (Exception e) {
				Utility.onEx_LOG_RET_NEW_EX(e,  "updateRecXmlStatus", "SCMDao", null, true, true);
			}finally
			{
				try {
					DbConnection.closePreparedStatement(psUpdateDtls);
				} catch (Exception e) {
					Utility.onEx_LOG_RET_NEW_EX(e,  "updateRecXmlStatus", "SCMDao", null, true, true);
				}
			}
			Utility.LOG(true,false,"Exiting updateRecXmlStatus");
			
		}
		
		/**
		 * This method will get service product id on the basis of received file id from send xml table
		 * @param  conn,readResponseXMLId
		 * @return returnStatus
	    */
		public int getSpId(Connection conn,int readResponseXMLId) throws SQLException{
			Utility.LOG(true,false,"Entering getSpId");
			ResultSet rs=null;
			int spId=0;
			PreparedStatement psSpId =null;
			try {
				psSpId = conn.prepareStatement(sqlGetSpId);
				psSpId.setInt(1,readResponseXMLId);
				rs = psSpId.executeQuery();
				while(rs.next()){
					spId=rs.getInt("SERVICEPRODUCTID");
				}
			} catch (SQLException e) {
				Utility.onEx_LOG_RET_NEW_EX(e, "getSpId", "SCMDao", "sqlState", true, true);
			} catch (Exception e) {
				Utility.onEx_LOG_RET_NEW_EX(e,  "getSpId", "SCMDao",  null, true, true);
			}finally
			{
				try {
					DbConnection.closeResultset(rs);
					DbConnection.closePreparedStatement(psSpId);
				} catch (Exception e) {
					Utility.onEx_LOG_RET_NEW_EX(e,  "getSpId", "SCMDao",  null, true, true);
					
				}
			}
			Utility.LOG(true,false,"Exiting getSpId");
			return spId;
			
		}

		/**
		 * This method will get details required for PR creation from TPOSERVICEDETAILS table on the basis of orderNumber,serviceId
		 * @param  conn,orderNumber,serviceId
		 * @return ArrayList<LineItemValueDTO>
	    */
		public ArrayList<LineItemValueDTO> getLineItemDtls(Connection conn,int orderNumber) throws SQLException{
			Utility.LOG(true,false,"Entering getLineItemDtls");
			ResultSet rs=null;
			ArrayList<LineItemValueDTO> lineItemValueLst=new ArrayList<LineItemValueDTO>();
			LineItemValueDTO lineItemValueDTOObj;
			PreparedStatement lineItemIdPstmt =null;
			try {
				//SCM_PROGRESS_STATUS will guarantee to not to send to SCM again if 2222 received again after sending the XML to SCM
				String srvDtlId=Utility.getAppConfigValue(AppConstants.TP_LINE_ITEMS_APPCONFIG_KEY);
				lineItemIdPstmt = conn.prepareStatement(sqlGetLineItemIds.toString());
				lineItemIdPstmt.setInt(1,orderNumber);
				StringTokenizer token=new StringTokenizer(srvDtlId,AppConstants.SERVICE_DTL_ID_SEPARATOR);
				while(token.hasMoreTokens()){
					lineItemIdPstmt.setInt(2,Integer.parseInt(token.nextElement().toString()));
					rs = lineItemIdPstmt.executeQuery();
					while(rs.next()){
						lineItemValueDTOObj=new LineItemValueDTO();
						lineItemValueDTOObj.setSpId((rs.getInt("SERVICEPRODUCTID")));
						lineItemValueDTOObj.setIsPrReuse((rs.getInt("IS_PR_REUSE")));
						lineItemValueDTOObj.setPrId((rs.getInt("PR_ID")));
						lineItemValueDTOObj.setServiceDtlId(rs.getInt("SERVICEDETAILID"));
						lineItemValueDTOObj.setScmProgStatus(rs.getString("SCM_PROGRESS_STATUS"));
						lineItemValueDTOObj.setSubChangeType(rs.getInt("SUB_CHANGE_TYPE_ID"));
						lineItemValueDTOObj.setService_id(rs.getInt("SERVICEID"));
						lineItemValueLst.add(lineItemValueDTOObj);
					}
					
				}
			} catch (SQLException e) {
				Utility.onEx_LOG_RET_NEW_EX(e, "SCMDao", "getLineItemDtls", "sqlState", true, true);
			} catch (Exception e) {
				Utility.onEx_LOG_RET_NEW_EX(e, "SCMDao", "getLineItemDtls", null, true, true);
			}finally
			{
				try {
					DbConnection.closeResultset(rs);
					DbConnection.closePreparedStatement(lineItemIdPstmt);
				} catch (Exception e) {
					Utility.onEx_LOG_RET_NEW_EX(e, "SCMDao", "getLineItemDtls", null, true, true);
				}
			}
			Utility.LOG(true,false,"Exiting getLineItemDtls");
			return lineItemValueLst;
			
		}
		/**
		 * This method will get details required for SED's user_Id upadtion  from TPOSERVICEDETAILS table on the basis of orderNumber
		 * @param  conn,orderNumber
		 * @return ArrayList<LineItemValueDTO>
	    */
		public ArrayList<LineItemValueDTO> getLineItemDtlsForSed(Connection conn,int orderNumber) throws SQLException{
			Utility.LOG(true,false,"Entering getLineItemDtlsForSed");
			ResultSet rs=null;
			ArrayList<LineItemValueDTO> lineItemValueLst=new ArrayList<LineItemValueDTO>();
			LineItemValueDTO lineItemValueDTOObj;
			PreparedStatement lineItemIdPstmt =null;
			try {
				//SCM_PROGRESS_STATUS will guarantee to not to send to SCM again if 2222 and 9999 received again after sending the XML to SCM
				String srvDtlId=Utility.getAppConfigValue(AppConstants.TP_LINE_ITEMS_APPCONFIG_KEY);
				lineItemIdPstmt = conn.prepareStatement(sqlGetLineItemIdsForSed.toString());
				lineItemIdPstmt.setInt(1,orderNumber);
				StringTokenizer token=new StringTokenizer(srvDtlId,AppConstants.SERVICE_DTL_ID_SEPARATOR);
				while(token.hasMoreTokens()){
					lineItemIdPstmt.setInt(2,Integer.parseInt(token.nextElement().toString()));
					rs = lineItemIdPstmt.executeQuery();
					while(rs.next()){
						lineItemValueDTOObj=new LineItemValueDTO();
						lineItemValueDTOObj.setSpId((rs.getInt("SERVICEPRODUCTID")));
						lineItemValueDTOObj.setService_id(rs.getInt("SERVICEID"));
						lineItemValueDTOObj.setServiceDtlId(rs.getInt("SERVICEDETAILID"));
						lineItemValueDTOObj.setIsPrReuse(rs.getInt("IS_PR_REUSE"));
						lineItemValueLst.add(lineItemValueDTOObj);
						/*for(LineItemValueDTO temp:lineItemValueLst)
						{
							System.out.println("SERVICEPRODUCTID"+temp.getSpId());
							System.out.println("SERVICEID"+temp.getService_id());
							System.out.println("SERVICEID"+temp.getServiceDtlId());
						}*/
					}
					
				}
				for(LineItemValueDTO temp:lineItemValueLst)
				{
					System.out.println("SERVICEPRODUCTID"+temp.getSpId());
					System.out.println("SERVICEID"+temp.getService_id());
					System.out.println("SERVICEID"+temp.getServiceDtlId());
				}
			} catch (SQLException e) {
				e.printStackTrace();
				Utility.onEx_LOG_RET_NEW_EX(e, "SCMDao", "getLineItemDtlsForSed", "sqlState", true, true);
			} catch (Exception e) {
				e.printStackTrace();
				Utility.onEx_LOG_RET_NEW_EX(e, "SCMDao", "getLineItemDtlsForSed", null, true, true);
			}finally
			{
				try {
					DbConnection.closeResultset(rs);
					DbConnection.closePreparedStatement(lineItemIdPstmt);
				} catch (Exception e) {
					e.printStackTrace();
					Utility.onEx_LOG_RET_NEW_EX(e, "SCMDao", "getLineItemDtlsForSed", null, true, true);
					
				}
			}
			Utility.LOG(true,false,"Exiting getLineItemDtlsForSed");
			return lineItemValueLst;
			
		}
		/**
		 *  This method will get line level attribute value
		 * @param  conn, servicePrdId,scmProgStat(message),prId(latest pr id for this line item),mapParam(chargeId and error mapping)
		 * @return HashMap<String,String>(details fetched).
	    */
		public String getLineAttValue(Connection conn,int spId,String attDesc) throws SQLException{
			Utility.LOG(true,false,"Entering getLineAttValue");
			ResultSet rs=null;
			HashMap<Integer,String> lineAttValueMap = new HashMap<Integer,String> ();
			PreparedStatement lineAttValPstmt =null;
			String cktNumber=new String();
			try {
				
				lineAttValPstmt = conn.prepareStatement(sqlGetAttributeValue.toString());
				lineAttValPstmt.setInt(1,spId);
				lineAttValPstmt.setString(2,attDesc);
				rs = lineAttValPstmt.executeQuery();
				while(rs.next()){
					cktNumber=rs.getString("ATTVALUE");
				}
			} catch (SQLException e) {
				Utility.onEx_LOG_RET_NEW_EX(e, "getLineAttValue", "SCMDao", "sqlState", true, true);
			} catch (Exception e) {
				Utility.onEx_LOG_RET_NEW_EX(e, "getLineAttValue", "SCMDao", null, true, true);
			}finally
			{
				try {
					DbConnection.closeResultset(rs);
					DbConnection.closePreparedStatement(lineAttValPstmt);
				} catch (Exception e) {
					Utility.onEx_LOG_RET_NEW_EX(e,  "getLineAttValue", "SCMDao", null, true, true);
				}
			}
			Utility.LOG(true,false,"Exiting getLineAttValue");
			return cktNumber;
			
		}
		
		public  String fetchXMLTemplate(Connection conn,String xmlTemplateType){
			Utility.LOG(true,false,"Entering fetchXMLTemplate");
			ResultSet rs=null;
			PreparedStatement pstmt =null;
			String templateValue=new String();
			try{
				String sql="select TEMPLATE_VALUE from IOE.TEMPLATE_MSTR AS TMPLATEMASTER WHERE TEMPLATE_KEY=? ORDER BY APPEND_ORDER ";
				pstmt=conn.prepareStatement(sql);
				pstmt.setString(1, xmlTemplateType);
				rs=pstmt.executeQuery();
				while(rs.next())
				{
					templateValue=rs.getString("TEMPLATE_VALUE");
				}
			} catch (SQLException e) {
				Utility.onEx_LOG_RET_NEW_EX(e, "fetchXMLTemplate", "SCMDao", "sqlState", true, true);
			} catch (Exception e) {
				Utility.onEx_LOG_RET_NEW_EX(e, "fetchXMLTemplate", "SCMDao", null, true, true);
			}finally
			{
				try {
					DbConnection.closeResultset(rs);
					DbConnection.closePreparedStatement(pstmt);
				} catch (Exception e) {
					Utility.onEx_LOG_RET_NEW_EX(e,  "fetchXMLTemplate", "SCMDao", null, true, true);
				}
			}
			Utility.LOG(true,false,"Exiting fetchXMLTemplate");
			return templateValue;

		}
		
		/**
		 * This method will insert created xml in TSCM_XMLSEND_STATUS and set status of previously created xml for the same spid to 1
		 * @param  conn,orderNumber,serviceId,spId,eventTypeId,finalXML,operationExec
		 * @return Nothing.
	    */
		public int updateSCMXML(Connection conn,ArrayList<SCMXMLDto> scmXMLDtoLst){
			Utility.LOG(true,false,"Entering updateSCMXML");
			PreparedStatement psUpdateXML =null;
			SCMXMLDto scmXMLDtoObj=new SCMXMLDto();
			int returnStatus=0;
			try {
				psUpdateXML=conn.prepareCall(sqlUpdatePRCreationSCMXML);
				int lstSize=scmXMLDtoLst.size();
				for(int i=0;i<lstSize;i++){
					scmXMLDtoObj=(SCMXMLDto)scmXMLDtoLst.get(i);
					java.sql.Clob myXMLClob=null;
					if(scmXMLDtoObj.getXml()!=null && !"".equalsIgnoreCase(scmXMLDtoObj.getXml())){
						myXMLClob = com.ibm.db2.jcc.t2zos.DB2LobFactory.createClob(scmXMLDtoObj.getXml());
					}else{
						scmXMLDtoObj.setXml("");
						myXMLClob = com.ibm.db2.jcc.t2zos.DB2LobFactory.createClob(scmXMLDtoObj.getXml());
					}
					psUpdateXML.setClob(1, myXMLClob);
					psUpdateXML.setInt(2, scmXMLDtoObj.getSend_status());
					psUpdateXML.setLong(3, scmXMLDtoObj.getXmlId());
					psUpdateXML.addBatch();
				}
				psUpdateXML.executeBatch();
				returnStatus=AppConstants.SCM_XML_PROCESSED_SUCCESS;
			} catch (BatchUpdateException e) {
				returnStatus=AppConstants.SCM_XML_PROCESSED_FAILURE;
				Utility.onEx_LOG_RET_NEW_EX(e, "updateSCMXML", "SCMDao", "sqlState", true, true);
			} catch (SQLException e) {
				returnStatus=AppConstants.SCM_XML_PROCESSED_FAILURE;
				Utility.onEx_LOG_RET_NEW_EX(e, "updateSCMXML", "SCMDao", "sqlState", true, true);
			} catch (Exception e) {
				returnStatus=AppConstants.SCM_XML_PROCESSED_FAILURE;
				Utility.onEx_LOG_RET_NEW_EX(e, "updateSCMXML", "SCMDao", null, true, true);
			}finally
			{
				try {
					DbConnection.closePreparedStatement(psUpdateXML);
				} catch (Exception e) {
					Utility.onEx_LOG_RET_NEW_EX(e, "updateSCMXML", "SCMDao", null, true, true);
					
				}
			}
			Utility.LOG(true,false,"Exiting updateSCMXML");
			return returnStatus;
		}
		
		/**
		 * This method will insert created xml in TSCM_XMLSEND_STATUS and set status of previously created xml for the same spid to 1
		 * @param  conn,orderNumber,serviceId,spId,eventTypeId,finalXML,operationExec
		 * @return Nothing.
	    */
		public int updateSCMXMLStatus(Connection conn,ArrayList<SCMXMLDto> scmXMLDtoLst) throws SQLException{
			Utility.LOG(true,false,"Entering updateSCMXML");
			PreparedStatement psUpdateStatus =null;
			SCMXMLDto scmXMLDtoObj=new SCMXMLDto();
			int returnStatus=0;
			try {
				
				psUpdateStatus=conn.prepareCall(sqlUpdatePRCreationSCMStatus);
				int lstSize=scmXMLDtoLst.size();
				for(int i=0;i<lstSize;i++){
					scmXMLDtoObj=(SCMXMLDto)scmXMLDtoLst.get(i);
					psUpdateStatus.setInt(1, scmXMLDtoObj.getSend_status());
					psUpdateStatus.setInt(2, scmXMLDtoObj.getOrderNumber());
					psUpdateStatus.setInt(3, scmXMLDtoObj.getServiceId());
					psUpdateStatus.setInt(4, scmXMLDtoObj.getServicePrdId());
					psUpdateStatus.addBatch();
				}
				psUpdateStatus.executeBatch();
				returnStatus=AppConstants.SCM_XML_PROCESSED_SUCCESS;
			} catch (BatchUpdateException e) {
				returnStatus=AppConstants.SCM_XML_PROCESSED_FAILURE;
				Utility.onEx_LOG_RET_NEW_EX(e, "updateSCMXMLStatus", "SCMDao", "sqlState", true, true);
			} catch (SQLException e) {
				returnStatus=AppConstants.SCM_XML_PROCESSED_FAILURE;
				Utility.onEx_LOG_RET_NEW_EX(e, "updateSCMXMLStatus", "SCMDao", "sqlState", true, true);
			} catch (Exception e) {
				returnStatus=AppConstants.SCM_XML_PROCESSED_FAILURE;
				Utility.onEx_LOG_RET_NEW_EX(e, "updateSCMXMLStatus", "SCMDao", null, true, true);
			}finally
			{
				try {
					DbConnection.closePreparedStatement(psUpdateStatus);
				} catch (Exception e) {
					Utility.onEx_LOG_RET_NEW_EX(e, "updateSCMXMLStatus", "SCMDao", null, true, true);
					
				}
			}
			Utility.LOG(true,false,"Exiting updateSCMXMLStatus");
			return returnStatus;
		}
				
}
		
		
