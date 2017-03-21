package com.ibm.ioes.ei.m6;

public class FetchLastTaskNewOrderXMLDto {
	
//	Raghu
	private String 	verb = null;
	private String 	orderLineNo = null;
	private String 	paramKey = null;
	private String 	value = null;
	
	
	public String getOrderLineNo() {
		return orderLineNo;
	}
	public void setOrderLineNo(String orderLineNo) {
		this.orderLineNo = orderLineNo;
	}
	public String getParamKey() {
		return paramKey;
	}
	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getVerb() {
		return verb;
	}
	public void setVerb(String verb) {
		this.verb = verb;
	}

}
