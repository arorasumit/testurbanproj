package com.ibm.ioes.dbhelper;
// SQL Helper Version 1.0 Beta
//Tag Name Resource Name  Date		  		Description
//[001]	  LAWKUSH	    07-Feb-2013				Service Location Customer Address like search
//[002]	 Raveendra Kumar	25-Mar-15	20150225-R1-021110     Order transfer rights required on GUI for AM Role

import java.lang.reflect.Type;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.io.IOException;
import java.io.PrintWriter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
 
public class SQLHelper {
    
    public SQLHelper() 
    {
        
    }
    /**
     * Retrieves a <code>Label</code> and <code>Value<code> For a Table Specified
     * @param nm The String which is to be searched
     * @param label The Name of Column which is to be shown as Label
     * @param value The Name of Column which is to be shown as Value
     * @param tablename The name of Table from which Label Value is to be selected
     * @return Returns a <code>JSON</code> String
     * @exception SQLException if the columnLabel is not valid; 
     * if a database access error occurs or this method is 
     *            called on a closed result set
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     */       
    public String getJsonLableValue(String nm , String label , String value  , String tablename ,Connection con) throws SQLException {
        net.sf.json.JSONObject jsonObj = new net.sf.json.JSONObject();		
	 JsonConfig jsonConfig = new JsonConfig();
                
	 nm = nm.toLowerCase();
	List<LabelValue> equal = new ArrayList<LabelValue>();
        LabelValue objDto = new LabelValue();
        CallableStatement csIOMS = null;
        ResultSet rset = null;
        if(nm.equalsIgnoreCase("%"))
        	csIOMS=con.prepareCall("SELECT "+ value +" , "+ label +" FROM "+tablename );
        else
        	csIOMS=con.prepareCall("SELECT "+ value +" , "+ label +" FROM "+tablename + " WHERE lower(" + label + ") like '%"+ nm + "%' WITH UR FOR READ ONLY" );
        
        rset=csIOMS.executeQuery();
        while(rset.next()){
	         objDto = new LabelValue();
	         objDto.setValue(rset.getString(value));
			 objDto.setLabel(rset.getString(label));
			 equal.add(objDto);
        }
        
        if(equal.size()==0){
        	objDto = new LabelValue();
            objDto.setValue("0");
		    objDto.setLabel("-No Record Found-");  
            equal.add(objDto);
        }
        JSONArray jsonArray = JSONArray.fromObject(equal, jsonConfig);
		jsonObj.element("glossary", jsonArray);
		return jsonObj.toString();
}
    
    public String getJsonLableValueWithQuery(
    		String nm ,List<String> labels , String value , String qry ,String attmasterId,String sourceType,Connection con,String paramId) throws SQLException {
    	net.sf.json.JSONObject jsonObj = new net.sf.json.JSONObject();		
    	JsonConfig jsonConfig = new JsonConfig();
    	nm = nm.toLowerCase();
    	List<LabelValue> equal = new ArrayList<LabelValue>();
    	LabelValue objDto = new LabelValue();
        CallableStatement csIOMS = null;
        ResultSet rset = null;
        csIOMS=con.prepareCall(qry);
        if("AUTOSUGGESTCLSI".equalsIgnoreCase(attmasterId)){
        	csIOMS.setInt(1,Integer.parseInt(paramId));
           	csIOMS.setString(2, nm);
        }
        //start Deepak Kumar
        else if(null != attmasterId && "AUTOSUGGESTITEMCODE".equalsIgnoreCase(attmasterId.trim()))
        {
        	nm = nm.toUpperCase();
        	csIOMS.setString(1,nm);
        	objDto = new LabelValue();
        	objDto.setValue("-1");
        	objDto.setLabel("Select Item Code");   
        	objDto.setAddParam("");
        	equal.add(objDto);
        }
        else if(null != attmasterId && "AUTOSUGGESTDELLOCATION".equalsIgnoreCase(attmasterId.trim()))
        {
        	 nm = nm.toUpperCase();
        	csIOMS.setString(1,nm);
        	csIOMS.setString(2,paramId);
        	objDto = new LabelValue();
        	objDto.setValue("-1");
        	objDto.setLabel("Select Delivery Location");   
        	objDto.setAddParam("");
        	equal.add(objDto);
        }
        else if(null != attmasterId && "AUTOSUGGESTSUBINVENTORY".equalsIgnoreCase(attmasterId.trim()))
        {
        	nm = nm.toUpperCase();
        	csIOMS.setString(1,nm);
        	csIOMS.setString(2,paramId);
        	objDto = new LabelValue();
        	objDto.setValue("-1");
        	objDto.setLabel("Select SubInventory");   
        	objDto.setAddParam("");
        	equal.add(objDto);
        }
        else if(null != attmasterId && "AUTOSUGGESTBUDGHEAD".equalsIgnoreCase(attmasterId.trim()))
        {
        	nm = nm.toUpperCase();
        	csIOMS.setString(1,nm);
        	objDto = new LabelValue();
        	objDto.setValue("-1");
        	objDto.setLabel("Select Budget Head1");   
        	objDto.setAddParam("");
        	equal.add(objDto);
        }
        //end Deepak kumar
        else if("AUTOSUGGESTLSI".equalsIgnoreCase(attmasterId)){
           	csIOMS.setInt(1,Integer.parseInt(paramId));
           	csIOMS.setString(2, nm);
        }else if("AUTOSUGGESTBCP".equalsIgnoreCase(attmasterId)){
           	csIOMS.setInt(1,Integer.parseInt(paramId));
           	csIOMS.setString(2, nm);
        }else if("AUTOSUGGESTBCPSERVICE".equalsIgnoreCase(attmasterId)){
           	csIOMS.setInt(1,Integer.parseInt(paramId));
           	csIOMS.setString(2, nm);
        }else if("AUTOSUGGESTTEXTFORM".equalsIgnoreCase(attmasterId)){
        	objDto = new LabelValue();
        	objDto.setValue("0");
        	objDto.setLabel("Select Form");    	         
     	    equal.add(objDto);
		}else if("AUTOSUGGESTDISPATCH".equalsIgnoreCase(attmasterId)){
           	csIOMS.setInt(1,Integer.parseInt(paramId));
           	csIOMS.setString(2, nm);
        }
        //Start[001]
        else if("AUTOSUGGESTPRICUSTLOC".equalsIgnoreCase(attmasterId) 
        		|| "AUTOSUGGESTSECCUSTLOC".equalsIgnoreCase(attmasterId)){
           	csIOMS.setInt(1,Integer.parseInt(paramId));
           	csIOMS.setString(2, nm);
        }//End[001]
        //Start [002]
        else if("AUTOSUGGESTAM".equalsIgnoreCase(attmasterId)){
        	csIOMS.setString(1, nm);
        }
        else if("AUTOSUGGESTAMASSIGNED".equalsIgnoreCase(attmasterId)){
        	csIOMS.setString(1, nm);
        }
        
        else if("AUTOSUGGESTPM".equalsIgnoreCase(attmasterId)){
        	csIOMS.setString(1, nm);
        }
      //End [002]
        else if("AUTOSUGGESTACCESSMATRIX".equalsIgnoreCase(attmasterId)){
        	csIOMS.setString(1, nm);
        }else if("AUTOSUGGESTPMASSIGNED".equalsIgnoreCase(attmasterId)){
           	csIOMS.setString(1, nm);
        }else if(null != attmasterId && "AUTOSUGGESTBILLTAXATION".equalsIgnoreCase(attmasterId.trim())){
        	csIOMS.setString(1, nm);
        }else if(null != attmasterId && "AUTOSUGGESTREASON".equalsIgnoreCase(attmasterId.trim())){
        	csIOMS.setInt(1, Integer.parseInt(paramId));
        	objDto = new LabelValue();
        	objDto.setValue("0");
        	objDto.setLabel("Select Standard Reason");    	         
     	    equal.add(objDto);
        }else if(null != attmasterId && "AUTOSUGGESTSALENATURE".equalsIgnoreCase(attmasterId.trim())){
        	objDto = new LabelValue();
        	objDto.setValue("0");
        	objDto.setLabel("Select Reason");    	         
     	    equal.add(objDto);
        }else if(null != attmasterId && "AUTOSUGGESTLEGALENTITY".equalsIgnoreCase(attmasterId.trim())){
        	csIOMS.setLong(1, Long.parseLong(paramId));
        	objDto = new LabelValue();
        	objDto.setValue("0");
        	objDto.setLabel("Select Legal Entity");    	         
        	objDto.setAddParam("");
     	    equal.add(objDto);
        }else if(null != attmasterId && "AUTOSUGGESTSALETYPE".equalsIgnoreCase(attmasterId.trim())){
        	csIOMS.setString(1, paramId);
        	objDto = new LabelValue();
        	objDto.setValue("0");
        	objDto.setLabel("Select Type Of Sale");    	         
     	    equal.add(objDto);
        }else if(null != attmasterId && "AUTOSUGGESTCP".equalsIgnoreCase(attmasterId.trim())){
        }else if(null != attmasterId && "AUTOSUGGESTBILLINGMODE".equalsIgnoreCase(attmasterId.trim())){
        	csIOMS.setLong(1,Long.parseLong(paramId));
        	objDto = new LabelValue();
        	objDto.setValue("0");
        	objDto.setLabel("Select");    	         
     	    equal.add(objDto);
        }else if(null != attmasterId && "AUTOSUGGESTSTORE".equalsIgnoreCase(attmasterId.trim())){
        	csIOMS.setInt(1,Integer.parseInt(paramId));
        	objDto = new LabelValue();
        	objDto.setValue("0");
        	objDto.setLabel("Select Store");    	         
     	    equal.add(objDto);
        }else if(null != attmasterId && "AUTOSUGGESTHTYPE".equalsIgnoreCase(attmasterId.trim())){
        	objDto = new LabelValue();
        	objDto.setValue("0");
        	objDto.setLabel("Select Hardware Type");    	         
     	    equal.add(objDto);
        }else if(null != attmasterId && "AUTOSUGGESTCHARGENAME".equalsIgnoreCase(attmasterId.trim())){
        	StringTokenizer token = new StringTokenizer(paramId, "_");
        	int i=1;
        	while(token.hasMoreTokens()){
        		csIOMS.setInt(i,Integer.valueOf(token.nextToken()));
        		i=i+1;
        	}
        	csIOMS.setString(i,nm);
        	objDto = new LabelValue();
        	objDto.setValue("-1");
        	objDto.setLabel("Select Charge Name");    	         
     	    equal.add(objDto);
        }else if(null != attmasterId && "AUTOSUGGESTBILLINGFORMAT".equalsIgnoreCase(attmasterId.trim())){
        	StringTokenizer token = new StringTokenizer(paramId, "_");
        	int i=1;
        	while(token.hasMoreTokens()){
        		if(i==1)
        			csIOMS.setString(i,token.nextToken());
        		else
        			csIOMS.setInt(i,Integer.valueOf(token.nextToken()));
        		i=i+1;
        	}
        	objDto = new LabelValue();
        	objDto.setValue("0");
        	objDto.setLabel("Select Bill Format");    	         
     	    equal.add(objDto);
        }else if(null != attmasterId && "AUTOSUGGESTCUSTPODETAIL".equalsIgnoreCase(attmasterId.trim())){
        	StringTokenizer token = new StringTokenizer(paramId, "_");
        	int i=1;
        	while(token.hasMoreTokens()){
        		csIOMS.setLong(i,Long.valueOf(token.nextToken()));
        		i=i+1;
        	}
        	objDto = new LabelValue();
        	objDto.setValue("0");
        	objDto.setLabel("Select Cust PO Detail No");    	         
     	    equal.add(objDto);
        }else if(null != attmasterId && "AUTOSUGGESTLICENSECO".equalsIgnoreCase(attmasterId.trim())){
        	StringTokenizer token = new StringTokenizer(paramId, "_");
        	int i=1;
        	while(token.hasMoreTokens()){
        		if(i==1)
        			csIOMS.setLong(i,Long.valueOf(token.nextToken()));
        		else if(i==2)
        			csIOMS.setInt(i,Integer.valueOf(token.nextToken()));
        		else if(i==3)
        			csIOMS.setLong(i,Long.valueOf(token.nextToken()));
        		i=i+1;
        	}
        	objDto = new LabelValue();
        	objDto.setValue("0");
        	objDto.setLabel("Select Licence Company");    	         
     	    equal.add(objDto);
        }else if(null != attmasterId && "AUTOSUGGESTBILLINGLEVEL".equalsIgnoreCase(attmasterId.trim())){
        	StringTokenizer token = new StringTokenizer(paramId, "_");
        	int i=1;
        	while(token.hasMoreTokens()){
        		if(i==1)
        			csIOMS.setInt(i,Integer.valueOf(token.nextToken()));
        		else
        			csIOMS.setLong(i,Long.valueOf(token.nextToken()));
        		i=i+1;
        	}
        	objDto = new LabelValue();
        	objDto.setValue("0");
        	objDto.setLabel("Select Billing Level");    	         
     	    equal.add(objDto);
        }else if("AUTOSUGGESTBILLINGTYPE".equalsIgnoreCase(attmasterId)){
        	objDto = new LabelValue();
        	objDto.setValue("0");
        	objDto.setLabel("Select Bill Type");    	         
     	    equal.add(objDto);
		}else if ("AUTOSUGGESTCHANGEREASON".equalsIgnoreCase(attmasterId)){
			//Shubhranshu,27-5-16
			String[] splitParam=paramId.split("_", 2);
			csIOMS.setString(1,splitParam[0]); // pass accNo as procedure argument
			csIOMS.setInt(2,Integer.valueOf(splitParam[1])); // pass subchangeTypeId as procedure argument
			
    	}else{
           	csIOMS.setLong(1,Long.valueOf(attmasterId));
           	csIOMS.setString(2, sourceType);
           	csIOMS.setString(3, nm);
           	if(paramId==null || paramId.equalsIgnoreCase("undefined")){
         	   paramId="0";
            }
            csIOMS.setLong(4,Long.valueOf(paramId));
        }
        rset=csIOMS.executeQuery();
        while(rset.next()){
        	objDto = new LabelValue();    	         
    	    objDto.setValue(rset.getString(value));
    	    if(null != attmasterId && "AUTOSUGGESTLEGALENTITY".equalsIgnoreCase(attmasterId.trim())){
    	    	objDto.setAddParam(rset.getString("ISFLE"));
    	    }
    	    String reqLabel = null;
    	    for(String label : labels){
    	    	if(null == reqLabel || "".equals(reqLabel.trim())){
    	    		reqLabel = rset.getString(label);
    	    	}else{
    	    		if(!(rset.getString(label) == null || "".equals(rset.getString(label).trim())))
    	    			reqLabel = reqLabel + "-" + rset.getString(label);
    	    	}
    	    }
    	    	
    	    objDto.setLabel(reqLabel);    	         
    	    equal.add(objDto);    	        	
        }
        if(equal.size()==0){
        	objDto = new LabelValue();
            objDto.setValue("-1");
            objDto.setLabel("-No Record Found-");  
            equal.add(objDto);
        }
        JSONArray jsonArray = JSONArray.fromObject(equal, jsonConfig);
    	jsonObj.element("glossary", jsonArray);
   		return jsonObj.toString();
    }
    /**
     * Retrieves a <code>HashTable</code> having <code>ResultSet<code> and <code>OUT<code> parameters of a Stored Procedure
     * @param isCRUD Should be <code>true</code> if Proc is For CRUD Operation Without Resultset and <code>false</code> if Proc is For Returning a Resultset
     * @param spname The name of <code>Store Procedure</code> having Schema of Database
     * @param con The Live Connection to the database in which Stored Proc exists
     * @param String...args The multiple Parameters in String Format
     * @return Returns Hastable having First Key as <code>result</code> and <code>OUT</code> parameters as <code>PARAM</code> Concatinated with Position of Param
     * @exception SQLException if the columnLabel is not valid; 
     * if a database access error occurs or this method is 
     *            called on a closed result set
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method     
     */       
    public Hashtable ExecuteStoredProcedure(boolean isCRUD,String spname ,Connection con ,String... args)  throws SQLException
    {
        
      	ResultSet rs = null;
        CallableStatement cstmt = null;
        ArrayList<ProcParams> lstProcParams = new ArrayList<ProcParams>();
        int iCount=0;
        Hashtable htTable = new Hashtable();
        String sError = "";
        try
        {
            
            lstProcParams = getParamDetails(spname,con);
            ProcParams objParam = new ProcParams();
            
            String params = "";
            for(String s : args)
            {
                params+="?,";
            }
            params = params.substring(0,params.length()-1);
            cstmt = con.prepareCall("{call "+ spname +"("+ params +")}");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            
            int iCountOutParam = 0;
            for(String s : args) 
            {                
                objParam = (ProcParams)lstProcParams.get(iCount);
                System.err.println(objParam.getParamName());
                int parampos = 0;
                parampos = Integer.parseInt(objParam.getParamOrdinal());
                if(objParam.getParamMode().equalsIgnoreCase("IN") && objParam.getParamDataType().equalsIgnoreCase("VARCHAR"))
                    cstmt.setString(parampos,s);    

                else
                if(objParam.getParamMode().equalsIgnoreCase("IN") && objParam.getParamDataType().equalsIgnoreCase("BIGINT"))
                    cstmt.setInt(parampos,Integer.parseInt(s));    

                else
                if(objParam.getParamMode().equalsIgnoreCase("IN") && objParam.getParamDataType().equalsIgnoreCase("INTEGER"))
                    cstmt.setInt(parampos,Integer.parseInt(s));    

                else
                if(objParam.getParamMode().equalsIgnoreCase("IN") && objParam.getParamDataType().equalsIgnoreCase("DATE"))
                    cstmt.setDate(parampos,new java.sql.Date(simpleDateFormat.parse(s).getTime()));    

                else
                if(objParam.getParamMode().equalsIgnoreCase("IN") && objParam.getParamDataType().equalsIgnoreCase("TIMESTAMP"))
                    cstmt.setTimestamp(parampos,new Timestamp(simpleDateFormat.parse(s).getTime()));    

                else
                if(objParam.getParamMode().equalsIgnoreCase("OUT") && objParam.getParamDataType().equalsIgnoreCase("BIGINT"))
                    cstmt.registerOutParameter(parampos,Types.BIGINT);

                else
                if(objParam.getParamMode().equalsIgnoreCase("OUT") && objParam.getParamDataType().equalsIgnoreCase("VARCHAR"))
                    cstmt.registerOutParameter(parampos,Types.VARCHAR);

                
                if(Integer.parseInt(objParam.getLength()) < s.length())
                {
                    sError = "Length Of " + objParam.getParamName() + " is greater than the Permitted Length. Allowed Length is " + objParam.getLength();
                    throw new Exception(sError);
                }
                
                if(s.toLowerCase().indexOf("<script>") > 0 || s.toLowerCase().indexOf("-") > 0 || s.toLowerCase().indexOf("'")>0)
                {
                    sError = "Special Character are not allowed in " + objParam.getParamName();
                    throw new Exception(sError);
                }
                iCount++;
            }
            if(isCRUD==true)
            {
                boolean isDone = false;
                isDone = cstmt.execute();
                htTable.put("result",String.valueOf(isDone));

            }
            else
            {
                rs = cstmt.executeQuery();
                htTable.put("result",rs);
            }
            iCountOutParam = 1;
            for (Iterator it = lstProcParams.iterator(); it.hasNext();) 
            {
                ProcParams objProcParam = (ProcParams) it.next();
                if(objProcParam.getParamMode().equalsIgnoreCase("OUT") && objProcParam.getParamDataType().equalsIgnoreCase("VARCHAR"))
                {
                   htTable.put("PARAM"+objProcParam.getParamOrdinal(),cstmt.getString(Integer.parseInt(objProcParam.getParamOrdinal())));
                   iCountOutParam++;
                }
                if(objProcParam.getParamMode().equalsIgnoreCase("OUT") && objProcParam.getParamDataType().equalsIgnoreCase("BIGINT"))
                {
                   htTable.put("PARAM"+objProcParam.getParamOrdinal(),cstmt.getLong(Integer.parseInt(objProcParam.getParamOrdinal())));
                   iCountOutParam++;
                }

            }
            
            
        }
        catch (SQLException ex)
        {
            
        }
        catch (Exception ex1)
        {
            System.err.println(ex1.getMessage());
        }
        return htTable;
  }
    
   public ArrayList<ProcParams> getParamDetails( String procname , Connection con)  throws SQLException
   {
        
      	ResultSet rs = null;
        CallableStatement cstmt = null;
        ProcParams objParam = null;
        ArrayList<ProcParams> lstProcParam = new ArrayList<ProcParams>();
        String[] strProcName = null;
        String delimiter = "\\.";
        try
        {        
            strProcName = procname.split(delimiter);
            cstmt = con.prepareCall("SELECT PARMNAME,TYPENAME,LENGTH , PARM_MODE , ORDINAL FROM SYSIBM.SYSPROCPARMS WHERE PROCNAME = '"+ strProcName[1] +"' ORDER BY ORDINAL");
            rs = cstmt.executeQuery();
            
            while (rs.next()) 
            {                
                objParam = new ProcParams();
                objParam.setParamName(rs.getString("PARMNAME"));
                objParam.setLength(rs.getString("LENGTH"));
                objParam.setParamDataType(rs.getString("TYPENAME"));
                objParam.setParamMode(rs.getString("PARM_MODE"));
                objParam.setParamOrdinal(rs.getString("ORDINAL"));
                
                lstProcParam.add(objParam);
            }
        }
        catch (SQLException ex)
        {
            
        }
        return lstProcParam;
  }
   
   public void Insert( String tabname , Connection con , String seqname ,String... params)  throws SQLException
   {
        ResultSet rs = null;
        CallableStatement cstmt = null;
        ProcParams objParam = null;
        ArrayList<ProcParams> lstProcParam = new ArrayList<ProcParams>();
        StringBuffer colName = new StringBuffer();
        Long pkValue = 0l;
        try
        {            
            
            cstmt = con.prepareCall("SELECT (NEXTVAL FOR "+ seqname +") AS NEXTPKVAL FROM SYSIBM.SYSDUMMY1");
            rs = cstmt.executeQuery();
            
            while (rs.next()) 
                pkValue = rs.getLong("NEXTPKVAL");                   
            
            cstmt = con.prepareCall("SELECT * FROM SYSIBM.SYSCOLUMNS WHERE TBNAME = '"+ tabname +"'");
            rs = cstmt.executeQuery();
            
            while (rs.next()) 
                colName.append(rs.getString("NAME") + ",");

            
            System.err.println(colName.toString());
        }
        catch (SQLException ex)
        {
            
        }
   }
   
   public String getScalarValue( String sql , Connection con , String... params)  throws SQLException
   {
        ResultSet rs = null;
        CallableStatement cstmt = null;
        ProcParams objParam = null;
        ArrayList<ProcParams> lstProcParam = new ArrayList<ProcParams>();
        StringBuffer colName = new StringBuffer();
        String scalarVal = "";
        try
        {            
            
            cstmt = con.prepareCall(sql);
            rs = cstmt.executeQuery();
            
            while (rs.next()) 
                scalarVal = rs.getString(1);                   
                                    
               System.err.println(colName.toString());
        }
        catch (SQLException ex)
        {
            
        }
        return scalarVal;
   }

}
