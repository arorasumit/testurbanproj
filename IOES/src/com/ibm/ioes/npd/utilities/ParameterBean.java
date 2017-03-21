package com.ibm.ioes.npd.utilities;

public class ParameterBean {

	private String paramName=null;
	private String paramValue=null;
	
	public ParameterBean(String name,String value){
		this.paramName=name;
		this.paramValue=value;
	}
	public String getParamName() {
		return paramName;
	}
	
	public String getParamValue() {
		return paramValue;
	}
	public String toString(){
		
		return "ParamName= "+this.paramName+"  ParamValue= "+this.paramValue;
		
	}
	
}
