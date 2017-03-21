package com.ibm.fx.dto;

import java.sql.Date;
import java.util.ArrayList;

import com.ibm.ioes.beans.ViewOrderDto;

public class LOCDATADTO {
	
	
	
	private long serviceProductId;
	private long nrcid;
	private int nrc_fx_schedular_create_status;
	private String nrc_viewid;
	private long rcid;
	private int rc_fx_schedular_create_status;
	private String rc_viewid;
	private long serviceid;
	private int service_fx_schedular_create_status;
	private String service_viewid;
	private ArrayList<LOCDATADTO> alselectNrclocdata=null;
	private ArrayList<LOCDATADTO> alselectRclocdata=null;
	private ArrayList<LOCDATADTO> alselectServicelocdata=null;
	
	private String locno;
	
	private String locdate;

	private String status;
	
	private String nrc_Status;
	private String rc_Status;
	private String service_Status;
	private String serviceType;
	
	
	private String sucfailstatus;
	private String exceptionMessage;
	
	private Integer subcno;
	private Date serviceactdate;

	private String service_parentid;
	
	private Long orderNo;
	
	public Long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}

	public String getService_parentid() {
		return service_parentid;
	}

	public void setService_parentid(String service_parentid) {
		this.service_parentid = service_parentid;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public String getSucfailstatus() {
		return sucfailstatus;
	}

	public void setSucfailstatus(String sucfailstatus) {
		this.sucfailstatus = sucfailstatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLocdate() {
		return locdate;
	}

	public void setLocdate(String locdate) {
		this.locdate = locdate;
	}

	public String getLocno() {
		return locno;
	}

	public void setLocno(String locno) {
		this.locno = locno;
	}

	

	public int getNrc_fx_schedular_create_status() {
		return nrc_fx_schedular_create_status;
	}

	public void setNrc_fx_schedular_create_status(int nrc_fx_schedular_create_status) {
		this.nrc_fx_schedular_create_status = nrc_fx_schedular_create_status;
	}

	public String getNrc_viewid() {
		return nrc_viewid;
	}

	public void setNrc_viewid(String nrc_viewid) {
		this.nrc_viewid = nrc_viewid;
	}

	public long getNrcid() {
		return nrcid;
	}

	public void setNrcid(long nrcid) {
		this.nrcid = nrcid;
	}

	public int getRc_fx_schedular_create_status() {
		return rc_fx_schedular_create_status;
	}

	public void setRc_fx_schedular_create_status(int rc_fx_schedular_create_status) {
		this.rc_fx_schedular_create_status = rc_fx_schedular_create_status;
	}

	public String getRc_viewid() {
		return rc_viewid;
	}

	public void setRc_viewid(String rc_viewid) {
		this.rc_viewid = rc_viewid;
	}

	public long getRcid() {
		return rcid;
	}

	public void setRcid(long rcid) {
		this.rcid = rcid;
	}

	public int getService_fx_schedular_create_status() {
		return service_fx_schedular_create_status;
	}

	public void setService_fx_schedular_create_status(
			int service_fx_schedular_create_status) {
		this.service_fx_schedular_create_status = service_fx_schedular_create_status;
	}

	public String getService_viewid() {
		return service_viewid;
	}

	public void setService_viewid(String service_viewid) {
		this.service_viewid = service_viewid;
	}

	public long getServiceid() {
		return serviceid;
	}

	public void setServiceid(long serviceid) {
		this.serviceid = serviceid;
	}

	public long getServiceProductId() {
		return serviceProductId;
	}

	public void setServiceProductId(long serviceProductId) {
		this.serviceProductId = serviceProductId;
	}

	

	public void setExceptionMessage(String message) {
		// TODO Auto-generated method stub
		
	}

	public ArrayList<LOCDATADTO> getAlselectNrclocdata() {
		return alselectNrclocdata;
	}

	public void setAlselectNrclocdata(ArrayList<LOCDATADTO> alselectNrclocdata) {
		this.alselectNrclocdata = alselectNrclocdata;
	}

	public ArrayList<LOCDATADTO> getAlselectRclocdata() {
		return alselectRclocdata;
	}

	public void setAlselectRclocdata(ArrayList<LOCDATADTO> alselectRclocdata) {
		this.alselectRclocdata = alselectRclocdata;
	}

	public ArrayList<LOCDATADTO> getAlselectServicelocdata() {
		return alselectServicelocdata;
	}

	public void setAlselectServicelocdata(
			ArrayList<LOCDATADTO> alselectServicelocdata) {
		this.alselectServicelocdata = alselectServicelocdata;
	}

	public String getNrc_Status() {
		return nrc_Status;
	}

	public void setNrc_Status(String nrc_Status) {
		this.nrc_Status = nrc_Status;
	}

	public String getRc_Status() {
		return rc_Status;
	}

	public void setRc_Status(String rc_Status) {
		this.rc_Status = rc_Status;
	}

	public String getService_Status() {
		return service_Status;
	}

	public void setService_Status(String service_Status) {
		this.service_Status = service_Status;
	}

	

	
	

	public Date getServiceactdate() {
		return serviceactdate;
	}

	public void setServiceactdate(Date serviceactdate) {
		this.serviceactdate = serviceactdate;
	}

	public Integer getSubcno() {
		return subcno;
	}

	public void setSubcno(Integer subcno) {
		this.subcno = subcno;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	
	

	

}
