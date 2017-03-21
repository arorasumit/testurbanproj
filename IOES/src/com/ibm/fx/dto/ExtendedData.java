package com.ibm.fx.dto;

import java.text.SimpleDateFormat;

public class ExtendedData {
	
	
	public final static String ExtendedType_Integer = "INTEGER";
	public final static String ExtendedType_String = "STRING";
	public final static String ExtendedType_DateTime = "DATETIME";
	
	public final static String AssociatedWith_CMF = "CMF";
	public final static String AssociatedWith_SERVICE = "SERVICE";
	public final static String AssociatedWith_PRODUCT = "PRODUCT";
	public final static String AssociatedWith_NRC = "NRC";
	public final static String AssociatedWith_SERVICEUPDATE = "SERVICEUPDATE";		
	
	public final static String AssociatedWith_ORDER_FOR_SERVICE = "ORDER_FOR_SERVICE";
	public final static String AssociatedWith_ORDER_FOR_PRODUCT = "ORDER_FOR_PRODUCT";
	public final static String AssociatedWith_ORDER_FOR_NRC = "ORDER_FOR_NRC";
	public final static String AssociatedWith_ORDER_FOR_CHARGE_DISCONNECTION = "ORDER_FOR_CHARGE_DISCONN";
	public final static String AssociatedWith_ORDER_FOR_SERVICE_DISCONNECTION = "ORDER_FOR_SERVICE_DISCONN";
	
	public final static String AssociatedWith_ORDER_FOR_COMPONENT_DISCONNECTION = "ORDER_FOR_COMPO_DISCONN";
	public final static String AssociatedWith_ORDER_FOR_COMPONENT = "ORDER_FOR_COMPONENT";
		
	public final static String dateTimeFormat = "yyyy-MM-dd HHmmss";
	public final static String AssociatedWith_ACCOUNTUPDATE = "ACCOUNT_UPDATE";
	
	
	
	public final  SimpleDateFormat Extended_sdf=new SimpleDateFormat(dateTimeFormat); 
	
	
	private Integer paramId = null;
	//private Short dataType = null;
	private Object paramValue = null;
	
	public Integer getParamId() {
		return paramId;
	}
	public void setParamId(Integer paramId) {
		this.paramId = paramId;
	}
	public Object getParamValue() {
		return paramValue;
	}
	public void setParamValue(Object paramValue) {
		this.paramValue = paramValue;
	}
	

}
