package com.ibm.fx.dto;

public class OrderTrackerDto {	
	
	
	private Long rowId	=	null;
	
	private Long parent_id = null;
	
	private String status = null;
	
	private String orderno = null;
	
	private Long subscr_no = null;
	
	private Long subscr_no_reset = null;
	
	private Integer trackingId=null;
	private Integer trackingIdServ=null;
	
	private  String view_id = null;

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public Long getParent_id() {
		return parent_id;
	}

	public void setParent_id(Long parent_id) {
		this.parent_id = parent_id;
	}

	public Long getRowId() {
		return rowId;
	}

	public void setRowId(Long rowId) {
		this.rowId = rowId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getSubscr_no() {
		return subscr_no;
	}

	public void setSubscr_no(Long subscr_no) {
		this.subscr_no = subscr_no;
	}

	public Long getSubscr_no_reset() {
		return subscr_no_reset;
	}

	public void setSubscr_no_reset(Long subscr_no_reset) {
		this.subscr_no_reset = subscr_no_reset;
	}

	public String getView_id() {
		return view_id;
	}

	public void setView_id(String view_id) {
		this.view_id = view_id;
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
