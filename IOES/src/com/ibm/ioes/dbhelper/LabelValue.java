package com.ibm.ioes.dbhelper;
/*
 * LabelValue.java
 *
 * Created on February 10, 2012, 10:55 AM
 *  
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Sumit Arora
 */
public class LabelValue {
    
    /** Creates a new instance of TestDto */
    public LabelValue() {
    }
    public LabelValue(String label,String value) {
    	this.label = label ;
    	this.value = value;
    }
    private String label;
    private String value;
    private String addParam;
    
    public String getAddParam() {
		return addParam;
	}
	public void setAddParam(String addParam) {
		this.addParam = addParam;
	}
    	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label ;
	}
    
    	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
    
    
}
