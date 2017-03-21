package com.ibm.fx.dto;

import java.sql.Timestamp;
import java.util.ArrayList;

public class DisconnectionDto {

	public int     charge_info_id ;
	public Timestamp charge_disconnection_date = null; 
	public int       charge_type;
	public int       processing_status; 
	public String    view_id            	=   null;
	public String    exception_history_id = null;
	public java.sql.Date   charge_active_date    =   null;
	public java.sql.Date charge_inactive_date	=	null;
	public int schedular_status         	    =  0;
	public int index_key						=	0;
	public Exception exception  = 		null;	
	private String exceptionMessage = null;
	// Meenakshi : added for SI Transfer/ CC
	private int orderno;
	private Integer fxAccountInternalId = null;
	
	private Integer trackingId = null;
	private Integer trackingIdServ = null;	
	private boolean isDataIssueException;
	public boolean isDataIssueException() {
			return isDataIssueException;
	}
	public void setDataIssueException(boolean isDataIssueException) {
			this.isDataIssueException = isDataIssueException;
	}

	
	ArrayList<ExtendedData> orderExtendedData=null;
	
	public ArrayList<ExtendedData> getOrderExtendedData() {
		return orderExtendedData;
	}
	public void setOrderExtendedData(ArrayList<ExtendedData> orderExtendedData) {
		this.orderExtendedData = orderExtendedData;
	}
	public int getOrderno() {
		return orderno;
	}
	public void setOrderno(int orderno) {
		this.orderno = orderno;
	}
	public int getIndex_key() {
		return index_key;
	}
	public void setIndex_key(int index_key) {
		this.index_key = index_key;
	}
	
	public int getCharge_info_id() {
		return charge_info_id;
	}
	public void setCharge_info_id(int charge_info_id) {
		this.charge_info_id = charge_info_id;
	}
	public int getCharge_type() {
		return charge_type;
	}
	public void setCharge_type(int charge_type) {
		this.charge_type = charge_type;
	}
	
	public String getException_history_id() {
		return exception_history_id;
	}
	public void setException_history_id(String exception_history_id) {
		this.exception_history_id = exception_history_id;
	}
	public int getProcessing_status() {
		return processing_status;
	}
	public void setProcessing_status(int processing_status) {
		this.processing_status = processing_status;
	}
	public String getView_id() {
		return view_id;
	}
	public void setView_id(String view_id) {
		this.view_id = view_id;
	}
	public java.sql.Date getCharge_active_date() {
		return charge_active_date;
	}
	public void setCharge_active_date(java.sql.Date charge_active_date) {
		this.charge_active_date = charge_active_date;
	}
	public Timestamp getCharge_disconnection_date() {
		return charge_disconnection_date;
	}
	public void setCharge_disconnection_date(Timestamp charge_disconnection_date) {
		this.charge_disconnection_date = charge_disconnection_date;
	}
	public java.sql.Date getCharge_inactive_date() {
		return charge_inactive_date;
	}
	public void setCharge_inactive_date(java.sql.Date charge_inactive_date) {
		this.charge_inactive_date = charge_inactive_date;
	}
	public int getSchedular_status() {
		return schedular_status;
	}
	public void setSchedular_status(int schedular_status) {
		this.schedular_status = schedular_status;
	}
	public Exception getException() {
		return exception;
	}
	public void setException(Exception exception) {
		this.exception = exception;
	}
	public String getExceptionMessage() {
		return exceptionMessage;
	}
	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}
	public Integer getFxAccountInternalId() {
		return fxAccountInternalId;
	}
	public void setFxAccountInternalId(Integer fxAccountInternalId) {
		this.fxAccountInternalId = fxAccountInternalId;
	}
	public Integer getTrackingId() {
		return trackingId;
	}
	public void setTrackingId(Integer trackingId) {
		this.trackingId = trackingId;
	}
	public Integer getTrackingIdServ() {
		return trackingIdServ;
	}
	public void setTrackingIdServ(Integer trackingIdServ) {
		this.trackingIdServ = trackingIdServ;
	}

	 

}
