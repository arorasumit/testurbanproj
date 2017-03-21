package com.ibm.ioes.dbhelper;
/*
 * ProcParams.java
 *
 * Created on February 11, 2012, 4:29 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author administrator
 */
public class ProcParams {
    
    /** Creates a new instance of ProcParams */
    public ProcParams() {
    }
    
    private String paramname;
    private String paramdatatype;
    private String length;
    private String parammode;
    private String paramordinal;
    
    public String getParamName() {
            return paramname;
    }
    public void setParamName(String paramname) {
            this.paramname = paramname ;
    }
    
    public String getParamDataType() {
            return paramdatatype;
    }
    public void setParamDataType(String paramdatatype) {
            this.paramdatatype = paramdatatype ;
    }
    
    public String getLength() {
            return length;
    }
    public void setLength(String length) {
            this.length = length ;
    }
    
    
    public String getParamMode() {
            return parammode;
    }
    public void setParamMode(String parammode) {
            this.parammode = parammode;
    }

    public String getParamOrdinal() {
            return paramordinal;
    }
    public void setParamOrdinal(String paramordinal) {
            this.paramordinal = paramordinal;
    }

}
