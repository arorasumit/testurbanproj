/*
 * [001]       Gunjan Singla    19-May-2014 	Added a variable serviceproductid regarding disconnection of FX service having pending charges
 * */
package com.ibm.fx.dto;

	import java.sql.Timestamp;
import java.util.ArrayList;

public class ServiceDisconnectionDto {

	
	

		
		private Integer returnStatus=0;
		public int     charge_info_id ;
		public Timestamp service_disconnection_date = null; 
		
		public int       processing_status; 
		public String    view_id            	=   null;
		public String    exception_history_id = null;
		
		public int schedular_status         	    =  0;
		public int index_key						=	0;
		public Exception exception  = 		null;	
		private String  tokenid = null;
		private String acctExternalId = null;
		
		ArrayList<ExtendedData> orderExtendedData=null;
		
		private String exceptionMessage = null;
		// Meenakshi : added for SI/CC
		private int orderNo;
		//[001]
		private long serviceproductid;
		
		boolean exceptionInCancel = false;
		private boolean isDataIssueException;
		public boolean isDataIssueException() {
			return isDataIssueException;
		}
		public void setDataIssueException(boolean isDataIssueException) {
			this.isDataIssueException = isDataIssueException;
		}

		
		
		
		public int getOrderNo() {
			return orderNo;
		}
		public void setOrderNo(int orderNo) {
			this.orderNo = orderNo;
		}
		public int getCharge_info_id() {
			return charge_info_id;
		}
		public void setCharge_info_id(int charge_info_id) {
			this.charge_info_id = charge_info_id;
		}
		public Exception getException() {
			return exception;
		}
		public void setException(Exception exception) {
			this.exception = exception;
		}
		public String getException_history_id() {
			return exception_history_id;
		}
		public void setException_history_id(String exception_history_id) {
			this.exception_history_id = exception_history_id;
		}
		public String getExceptionMessage() {
			return exceptionMessage;
		}
		public void setExceptionMessage(String exceptionMessage) {
			this.exceptionMessage = exceptionMessage;
		}
		public int getIndex_key() {
			return index_key;
		}
		public void setIndex_key(int index_key) {
			this.index_key = index_key;
		}
		public int getProcessing_status() {
			return processing_status;
		}
		public void setProcessing_status(int processing_status) {
			this.processing_status = processing_status;
		}
		public int getSchedular_status() {
			return schedular_status;
		}
		public void setSchedular_status(int schedular_status) {
			this.schedular_status = schedular_status;
		}
		public Timestamp getService_disconnection_date() {
			return service_disconnection_date;
		}
		public void setService_disconnection_date(Timestamp service_disconnection_date) {
			this.service_disconnection_date = service_disconnection_date;
		}
		public String getView_id() {
			return view_id;
		}
		public void setView_id(String view_id) {
			this.view_id = view_id;
		}
		public Integer getReturnStatus() {
			return returnStatus;
		}
		public void setReturnStatus(Integer returnStatus) {
			this.returnStatus = returnStatus;
		}
		public String getTokenid() {
			return tokenid;
		}
		public void setTokenid(String tokenid) {
			this.tokenid = tokenid;
		}
		public String getAcctExternalId() {
			return acctExternalId;
		}
		public void setAcctExternalId(String acctExternalId) {
			this.acctExternalId = acctExternalId;
		}
		public ArrayList<ExtendedData> getOrderExtendedData() {
			return orderExtendedData;
		}
		public void setOrderExtendedData(ArrayList<ExtendedData> orderExtendedData) {
			this.orderExtendedData = orderExtendedData;
		}
		//[001] start
		public long getServiceproductid() {
			return serviceproductid;
		}
		public void setServiceproductid(long serviceproductid) {
			this.serviceproductid = serviceproductid;
		}
		//[001] end
		public boolean isExceptionInCancel() {
			return exceptionInCancel;
		}
		public void setExceptionInCancel(boolean exceptionInCancel) {
			this.exceptionInCancel = exceptionInCancel;
		}
		
		 

	

}
