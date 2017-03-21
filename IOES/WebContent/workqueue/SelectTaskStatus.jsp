 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--Tag Name Resource Name  Date		CSR No			Description -->
<!--[001]	 ROHIT VERMA	23-Feb-11	00-05422		System Testing Defect for AM cant Approve an Order -->
<!--[002]    SUMIT ARORA    07-MAR-11   00-05422        Length Of Remarks to increased and restricted to 256 -->
<!--[250511AKS] ASHUTOSH  	24-MAY-11   00-05422        Defects: Validate PO During Aciton close and Publish -->
<!--[00055]	 Ashutosh Singh		05-July-11	CSR00-05422     Document tracking for Diffrent Role -->
<!--[03]	 Lawkush 		24-June-2013	CSR00-05422     Rejection Reason Drop Down List added -->
<!-- [003] 	 Rohit Verma	25-Aug-13		CSR-9912		Validation to Input Order No in Remarks Field for OTD Documents-->
<!-- [004] Submit Button added in uppper Section -By VIPIN SAHARIA -->
<!-- [210]   ANOOP TIWARI   20 feb-14-------O2C drop 2-----making comaseperated service string of selected service-->
<!-- [211]   Raveendra      23-Jan-2015    validation for selected services -->
<!-- [212] Licence company  -By RAVEENDRA -->
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://struts.apache.org/tags-nested" prefix="nested"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.ibm.ioes.beans.UserInfoDto"%>
<%@page import="com.ibm.ioes.utilities.AppConstants"%>
<html>
<head>
<title>:: View Order: Integrated Order Entry System</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
<link href="./gifs/style.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/PagingSorting.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/NewChangeOrderUtility.js"></script>
<style type="text/css">
    .opaqueLayer
    {
        display:none;
        position:absolute;
        top:0px;
        left:0px;
        opacity:0.5;
        filter:alpha(opacity=50);
        background-color: #000000;
        z-Index:1000;
        text-align:center;
        vertical-align:middle;
        padding:100px;
    }
</style>
<script language="javascript" type="text/javascript" >
var path='<%=request.getContextPath()%>';
var rolePassedEscalation = <%= request.getAttribute("ROLEPASSEDESCALATION") %>;
var rolePartialInitiator = <%= request.getAttribute("ROLEPARTIALINITIATOR") %>;
//var poNumber = <%=request.getParameter("poNumber")%>;
var orderOwner = <%= request.getAttribute("ORDEROWNER") %>;
var callerWindowObj=dialogArguments;
var roleID=callerWindowObj.document.getElementById('searchTaskOwnerId').value;
var allServicesSelected=true;
function getRoleAccess()
{
	var callerWindowObj = dialogArguments;
	var roleID=callerWindowObj.document.getElementById('searchTaskOwnerId').value;
	if(roleID==2 || roleID==3 || roleID==4)
	{
		fillRejectionReasons();
	}
}
function getDelayReason(){
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	var delayList = jsonrpc.processes.getDelayReason();
	if(null != delayList){
		var combo = document.getElementById("delayReason");
	    var iCountRows = combo.length;
	    for (i = 1; i <  iCountRows; i++){
	       combo.remove(1);
	    }
	    for(j=0;j<delayList.list.length;j++){
		   	var option = document.createElement("option");
		  	option.text = delayList.list[j].delayReason;
			option.value = delayList.list[j].id;
		  	option.title = delayList.list[j].delayReason;
			try{
				combo.add(option, null); //Standard
			}catch(error){
				combo.add(option); // IE only
			}
	    }
	}
}

function getTip(value)
{	
	Tip("<Table border=0> "+ value +"   </table>");	
}

function getTip_DD(obj,value,objName)
{	
	var SelectedValue;
	var selIndex = value;
	var myForm=document.getElementById('productCatelog');
	SelectedValue=document.getElementById(objName).options[selIndex].text	
	var combo = document.getElementById(objName);
	combo.title = SelectedValue;
}
//[00055] Start
var roleId=0;
var orderNo=0;
//[00055] End
//lawkush start
var fileTypeIdN=0;
var sentMethod=0;
var isLastTaskOwnerId=0;
//lawkush End
function goToHome()
{
	document.forms[0].action="<%=request.getContextPath()%>/defaultAction.do?method=goToHome";
	showLayer();
	document.forms[0].submit();
}


function fnCheckDocumentAll()
{
  		var callerWindowObj = dialogArguments;
	    var i;
	    var checkedCounter=1;
        var myForm=document.getElementById('searchForm');		
	    var counter=1;
	    var counter2=1;
	    var counter3=1;
   	    var counter4=1;
	    orderNo=callerWindowObj.document.getElementById("poNumber").value;
		roleId = callerWindowObj.document.getElementById('searchTaskOwnerId').value;
	    DocumentTab = document.getElementById('fileCheckListTable');
        var rowlen= DocumentTab.rows.length;
        var chkLength1;
        var chkLength2;
        var chkLength3;
        var chkLength4;
        
        if(rowlen==1)
		{
			 alert("please add one row to delete");
			 document.getElementById('SelectAllDocuments').checked=false;
			 return false;
		}
        
       if(roleId==2)
       { 
         chkLength2=document.forms[0].chkPMDetail.length;
       }
       else if(roleId==3)
       { 
         chkLength3=document.forms[0].chkCOPCDetail.length;
       }
       else if(roleId==4)
       { 
         chkLength4=document.forms[0].chkSEDDetail.length;
       }
       else
       { 
         chkLength1=document.forms[0].chkAMDetail.length;
       }
      
   //<!--Changed the Condition of if else by removing AM from if and placing in else for RoleChange by Vishwa on 18-Apr-2012	--> 
	 if(roleId==2)
       {
	    
		if(document.getElementById('SelectAllDocuments').checked==true)
		{
			        if(chkLength2==undefined)
	                {
		                 if(document.forms[0].chkPMDetail.disabled==false)
			             {
			                    document.forms[0].chkPMDetail.checked=true;
			                    counter2++;
		                 }
                  	}
      				else
      				{    
	         			for (i =0; i<chkLength2; i++)
			   			{ 
			     			if(myForm.chkPMDetail[i].disabled==false)
					 		{
					        	myForm.chkPMDetail[i].checked=true ;
					           counter2++;
							} 
		      			}
					}
	      			if(counter2==1)
		         	{
						alert("No Documents Available for Selection");
						document.getElementById('SelectAllDocuments').checked=false;
						return false;         
		         	}
	
		}	
	else
	   {
	           	if(chkLength2==undefined)
          		{
	                 if(document.forms[0].chkPMDetail.disabled==false)
		             {
	                    document.forms[0].chkPMDetail.checked=false;
                       	counter2++;
	                 }
          		}
      			else
      			{    
         			for (i =0; i<chkLength2; i++)
		   			{ 
		     			if(myForm.chkPMDetail[i].disabled==false)
				 		{
				        	myForm.chkPMDetail[i].checked=false ;
				          	counter2++;
				 		}       
		   			}
				}
	     		if(counter2==1)
		        {
					alert("No PO Available for Selection");
					document.getElementById('SelectAllDocuments').checked=false;
					return false;                  
		        }
	
		}
	}
	else if(roleId==3)
    {

		if(document.getElementById('SelectAllDocuments').checked==true)
		{
			        if(chkLength3==undefined)
	                {
		                 if(document.forms[0].chkCOPCDetail.disabled==false)
			             {
			                    document.forms[0].chkCOPCDetail.checked=true;
			                    counter3++;
		                 }
                  	}
      				else
      				{    
	         			for (i =0; i<chkLength3; i++)
			   			{ 
			     			if(myForm.chkCOPCDetail[i].disabled==false)
					 		{
					        	myForm.chkCOPCDetail[i].checked=true ;
					            counter3++;
							} 
		      			}
					}
	      			if(counter3==1)
		         	{
						alert("No PO Available for Selection");
						document.getElementById('SelectAllDocuments').checked=false;
						return false;         
		         	}
		}	
		else
	   	{
	           if(chkLength3==undefined)
          		{
	                 if(document.forms[0].chkCOPCDetail.disabled==false)
		             {
	                    document.forms[0].chkCOPCDetail.checked=false;
	                    counter3++;
	                 }
          		}
      			else
      			{    
         			for (i =0; i<chkLength3; i++)
		   			{ 
		     			if(myForm.chkCOPCDetail[i].disabled==false)
				 		{
				        	myForm.chkCOPCDetail[i].checked=false ;
				          	counter3++;
				 		}       
		   			}
				}
	     		if(counter3==1)
		         {
					alert("No PO Available for Selection");
					document.getElementById('SelectAllDocuments').checked=false;
					return false;                  
		         }
	
		}

	}	
	//below code added for SED, since in dynamic new order workflow SED could be a task.
	else if(roleId==4)
    {

		if(document.getElementById('SelectAllDocuments').checked==true)
		{
			        if(chkLength4==undefined)
	                {
		                 if(document.forms[0].chkSEDDetail.disabled==false)
			             {
			                    document.forms[0].chkSEDDetail.checked=true;
			                    counter4++;
		                 }
                  	}
      				else
      				{    
	         			for (i =0; i<chkLength4; i++)
			   			{ 
			     			if(myForm.chkSEDDetail[i].disabled==false)
					 		{
					        	myForm.chkSEDDetail[i].checked=true ;
					            counter4++;
							} 
		      			}
					}
	      			if(counter4==1)
		         	{
						alert("No PO Available for Selection");
						document.getElementById('SelectAllDocuments').checked=false;
						return false;         
		         	}
		}	
		else
	   	{
	           if(chkLength4==undefined)
          		{
	                 if(document.forms[0].chkSEDDetail.disabled==false)
		             {
	                    document.forms[0].chkSEDDetail.checked=false;
	                    counter4++;
	                 }
          		}
      			else
      			{    
         			for (i =0; i<chkLength4; i++)
		   			{ 
		     			if(myForm.chkSEDDetail[i].disabled==false)
				 		{
				        	myForm.chkSEDDetail[i].checked=false ;
				          	counter4++;
				 		}       
		   			}
				}
	     		if(counter4==1)
		         {
					alert("No PO Available for Selection");
					document.getElementById('SelectAllDocuments').checked=false;
					return false;                  
		         }
	
		}

	}	
//<!--Changed the Condition of if else by removing AM from if and placing in else for RoleChange by Vishwa on 18-Apr-2012	-->
 else
      {   
		if(document.getElementById('SelectAllDocuments').checked==true)
		{
			        if(chkLength1==undefined)
	                {
		                 if(document.forms[0].chkAMDetail.disabled==false)
			             {
			                    document.forms[0].chkAMDetail.checked=true;
			                    counter++;
		                 }
                  	}
      				else
      				{    
	         			for (i =0; i<chkLength1; i++)
			   			{ 
			     			if(myForm.chkAMDetail[i].disabled==false)
					 		{
					        	myForm.chkAMDetail[i].checked=true ;
					           	counter++;
							} 
		      			}
					}
	      			if(counter==1)
		         	{
						alert("No Documents Available for Selection");
						document.getElementById('SelectAllDocuments').checked=false;
						return false;         
		         	}
		}	
	else
	   {
	           	if(chkLength1==undefined)
          		{
	                 if(document.forms[0].chkAMDetail.disabled==false)
		             {
	                    document.forms[0].chkAMDetail.checked=false;
	                    counter++;
	                 }
          		}
      			else
      			{    
         			for (i =0; i<chkLength1; i++)
		   			{ 
		     			if(myForm.chkAMDetail[i].disabled==false)
				 		{
				        	myForm.chkAMDetail[i].checked=false ;
				          	counter++;
				 		}       
		   			}
				}
	     	if(counter==1)
		    {
					alert("No PO Available for Selection");
					document.getElementById('SelectAllDocuments').checked=false;
					return false;                  
		    }
		}
	}
	
}



function setformValue()
{
	var callerWindowObj = dialogArguments;
	document.getElementById('searchTaskId').value =  callerWindowObj.document.getElementById("searchTaskId").value;
	//[00055] Start (Added By Ashutosh)
	orderNo=callerWindowObj.document.getElementById("poNumber").value;
	roleId = callerWindowObj.document.getElementById('searchTaskOwnerId').value;	
	getUploadedFileforApproval(orderNo,roleId);
	////[00055]  End
	
}
//[00055] Start
function getUploadedFileforApproval(orderNo,roleId)
{
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	var roleList = jsonrpc.processes.getListOfRoleFromWorkflow(orderNo);
	
   	var mytable = document.getElementById('fileCheckListTable');
 
    var iCountRows = mytable.rows.length;
   
  /* Star [212] */
    for (i = 0; i <  iCountRows; i++)
    {
        mytable.deleteRow(0);
    }   
  
   	var headerRow=document.getElementById('fileCheckListTable').insertRow();
   	var tblcol=headerRow.insertCell();
	tblcol.align = "left";
	tblcol.width="8%"
	tblcol.vAlign="top";
	tblcol.style.fontSize="12px";
	tblcol.style.fontWeight="bold";
	str= "S.NO";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
   	var tblcol=headerRow.insertCell();
	tblcol.align = "left";
	tblcol.width="40%"
	tblcol.vAlign="top";
	tblcol.style.fontSize="12px";
	tblcol.style.fontWeight="bold";
	str= "Document Name";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	
	for(i=0;i<roleList.list.length;i++)
	{
		//<!--Fetched the RoleName instead of HardCode RoleChange by Vishwa on 18-Apr-2012	-->
		var roleName=roleList.list[i].roleName;
		//Add for enable publish button on last task owner approval
		if(roleList.list[i].isLastTask==1)
		{
			isLastTaskOwnerId=roleList.list[i].roleId;
		}
		
		var tblcol=headerRow.insertCell();
		tblcol.align = "left";
		tblcol.width="7%"
		tblcol.vAlign="top";
		tblcol.style.fontSize="12px";
		tblcol.style.fontWeight="bold";
		str= ""+roleName;
		CellContents = str;	
		tblcol.innerHTML = CellContents;
		
		
	}
	
	//lawkush Start
	
	var tblcol=headerRow.insertCell();
	tblcol.align = "center";
	tblcol.width="10%"
	tblcol.vAlign="top";
	tblcol.style.fontSize="12px";
	tblcol.style.fontWeight="bold";
	str= "Attach Document";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	
	var tblcol=headerRow.insertCell();
	tblcol.align = "center";
	tblcol.width="10%"
	tblcol.vAlign="top";
	tblcol.style.fontSize="12px";
	tblcol.style.fontWeight="bold";
	str= "View Document";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	var roleList = jsonrpc.processes.getListOfRoleFromWorkflow(orderNo);
	
   	var mytable = document.getElementById('fileCheckListTable');
 
    var iCountRows = mytable.rows.length;
   
  
    for (i = 0; i <  iCountRows; i++)
    {
        mytable.deleteRow(0);
    }   
  
   	var headerRow=document.getElementById('fileCheckListTable').insertRow();
   	var tblcol=headerRow.insertCell();
	tblcol.align = "left";
	tblcol.width="8%";
	tblcol.vAlign="top";
	tblcol.style.fontSize="12px";
	tblcol.style.fontWeight="bold";
	str= "S.NO";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
   	var tblcol=headerRow.insertCell();
	tblcol.align = "left";
	tblcol.width="40%";
	tblcol.vAlign="top";
	tblcol.style.fontSize="12px";
	tblcol.style.fontWeight="bold";
	str= "Document Name";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	
	
	var OrderCreatedBy;
	if(roleList.list.length>0)
	{
		OrderCreatedBy=roleList.list[0].roleId;
		
	}
	
	
	
	for(i=0;i<roleList.list.length;i++)
	{
		var roleName=roleList.list[i].roleName;
		
		var tblcol=headerRow.insertCell();
		tblcol.align = "left";
		tblcol.width="7%";
		tblcol.vAlign="top";
		tblcol.style.fontSize="12px";
		tblcol.style.fontWeight="bold";
		str= ""+roleName;
		CellContents = str;	
		tblcol.innerHTML = CellContents;
		
		
	}
	
	//lawkush Start
	
	var tblcol=headerRow.insertCell();
	tblcol.align = "center";
	tblcol.width="10%";
	tblcol.vAlign="top";
	tblcol.style.fontSize="12px";
	tblcol.style.fontWeight="bold";
	str= "Attach Document";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	
	var tblcol=headerRow.insertCell();
	tblcol.align = "center";
	tblcol.width="10%";
	tblcol.vAlign="top";
	tblcol.style.fontSize="12px";
	tblcol.style.fontWeight="bold";
	str= "View Document";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	var fileList = jsonrpc.processes.getUploadedFileList(orderNo,roleId);
	var counter=1;
	for(i=0;i<fileList.list.length;i++)
	{
		
		var fileId=fileList.list[i].fileId;
		
		var str;
		var tblRow=document.getElementById('fileCheckListTable').insertRow();
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		str=""+counter+"<input type='hidden' name='fileId' id='fileId"+counter+"' value='"+fileId+"' >"
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		str=""+fileList.list[i].fileName+"<input type='hidden'  name='fileName' id='fileName"+counter+"' value='"+fileList.list[i].fileName+"'>"
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		
		for(j=0;j<roleList.list.length;j++)
		{
			if(roleList.list[j].roleId==2)
			{
				var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				if(roleId==2 && fileList.list[i].roleId==null)
					str ="<input type='hidden' class='inputBorder1' name='PM' id='PM"+counter+"' value='0'><input name='chkPMDetail' id='chkPMDetail"+fileId+"' type='checkbox' onclick='insertSelectedFileCHKList(0);' >";
				else if(roleId==2 && fileList.list[i].roleId!=null ) 
					str ="<input type='hidden' class='inputBorder1' name='PM' id='PM"+counter+"' value='0'><input name='chkPMDetail' id='chkPMDetail"+fileId+"' type='checkbox' onclick='insertSelectedFileCHKList(0);' >";
				else
					str ="<input type='hidden' class='inputBorder1' name='PM' id='PM"+counter+"' value='0'><input name='chkPMDetail' id='chkPMDetail"+fileId+"' type='checkbox' disabled='true'>";
				CellContents = str;
				tblcol.innerHTML = CellContents;
			}
			else if(roleList.list[j].roleId==3)
			{
				var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				if(roleId==3 && fileList.list[i].roleId==null)
					str ="<input type='hidden' class='inputBorder1' name='COPC' id='COPC"+counter+"' value='0'><input name='chkCOPCDetail' id='chkCOPCDetail"+fileId+"' type='checkbox' onclick='insertSelectedFileCHKList(0);' >";
				else if(roleId==3 && fileList.list[i].roleId!=null ) 
					str ="<input type='hidden' class='inputBorder1' name='COPC' id='COPC"+counter+"' value='0'><input name='chkCOPCDetail' id='chkCOPCDetail"+fileId+"' type='checkbox' onclick='insertSelectedFileCHKList(0);' >";			
				else
					str ="<input type='hidden' class='inputBorder1' name='COPC' id='COPC"+counter+"' value='0'><input name='chkCOPCDetail' id='chkCOPCDetail"+fileId+"' type='checkbox' disabled='true'>";
				CellContents = str;
				tblcol.innerHTML = CellContents;
			}
			else if(roleList.list[j].roleId==4)
			{
				var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				if(roleId==4 && fileList.list[i].roleId==null)
					str ="<input type='hidden' class='inputBorder1' name='SED' id='SED"+counter+"' value='0'><input name='chkSEDDetail' id='chkSEDDetail"+fileId+"' type='checkbox' onclick='insertSelectedFileCHKList(0);' >";
				else if(roleId==4 && fileList.list[i].roleId!=null ) 
					str ="<input type='hidden' class='inputBorder1' name='SED' id='SED"+counter+"' value='0'><input name='chkSEDDetail' id='chkSEDDetail"+fileId+"' type='checkbox' onclick='insertSelectedFileCHKList(0);'>";			
				else
					str ="<input type='hidden' class='inputBorder1' name='SED' id='SED"+counter+"' value='0'><input name='chkSEDDetail' id='chkSEDDetail"+fileId+"' type='checkbox' disabled='true'>";
				CellContents = str;
				tblcol.innerHTML = CellContents;
			}
			//<!--Changed the Condition of if else by removing AM from if and placing in else for RoleChange by Vishwa on 18-Apr-2012	-->
			else if(roleList.list[j].roleId==OrderCreatedBy)
			{
				var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				if(roleId==OrderCreatedBy && fileList.list[i].roleId==null)
					str ="<input type='hidden' class='inputBorder1' name='AM' id='"+fileList.list[i].roleName+""+counter+"' value='0'><input name='chkAMDetail' id='chkAMDetail"+fileId+"' type='checkbox' onclick='insertSelectedFileCHKList(0);'>";
				else if(roleId==OrderCreatedBy && fileList.list[i].roleId!=null ) 
					str ="<input type='hidden' class='inputBorder1' name='AM' id='"+fileList.list[i].roleName+""+counter+"' value='0'><input name='chkAMDetail' id='chkAMDetail"+fileId+"' type='checkbox' onclick='insertSelectedFileCHKList(0);'>";
				else 
					str ="<input type='hidden' class='inputBorder1' name='AM' id='"+fileList.list[i].roleName+""+counter+"' value='0'><input name='chkAMDetail' id='chkAMDetail"+fileId+"' type='checkbox' disabled='true'>";
				CellContents = str;
				tblcol.innerHTML = CellContents;
			}
			 
		}
		
		//lawkush Startz
		
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str="<input type='button' style='width:60px;' name='attachButton' value='Attach' id='attachButton"+counter+"'   onclick='javascript:getSetDocumentDetails("+fileId+","+1+","+counter+");' >"
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str="<input type='button' style='width:60px;'  name='viewButton' value='View' id='viewButton"+counter+"'  onclick='javascript:getSetDocumentDetails("+fileId+","+2+","+counter+");'  >"
		CellContents = str;
		tblcol.innerHTML = CellContents;
		/* End [212] */
		counter++;
	}
	showAllUserSelectedFileCHKList(orderNo);
}
function showAllUserSelectedFileCHKList(orderNo)
{
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	var selectedFileList = jsonrpc.processes.getALLSelectedFileCHKList(orderNo);
	var rowCounter=1;	
	for(i=0;i<selectedFileList.list.length;i++,rowCounter++)
	{
		var fileId=selectedFileList.list[i].fileId;		
		//<!--Changed the Condition of if else by removing AM from if and placing in else for RoleChange by Vishwa on 18-Apr-2012	-->
		if(selectedFileList.list[i].roleId==2 && selectedFileList.list[i].selectDocStatus==1)
		{
			//added below if codition for when order get rejected through aproval id chkPMDetail not found
			if(document.getElementById('chkPMDetail')!=null)	
				document.getElementById('chkPMDetail'+fileId).checked=true;
		}
		else if(selectedFileList.list[i].roleId==3 && selectedFileList.list[i].selectDocStatus==1)
		{
			//added below if codition for when order get rejected through aproval id chkCOPCDetail not found
			if(document.getElementById('chkCOPCDetail')!=null)	
				document.getElementById('chkCOPCDetail'+fileId).checked=true;
		}
		else if(selectedFileList.list[i].roleId==4 && selectedFileList.list[i].selectDocStatus==1)
		{
			//added below if codition for when order get rejected through aproval id chkSEDDetail not found
			if(document.getElementById('chkSEDDetail')!=null)	
				document.getElementById('chkSEDDetail'+fileId).checked=true;
		}
		else if (selectedFileList.list[i].selectDocStatus==1)
		{
			//added below if codition for when order get rejected through aproval id chkAMDetail not found
			if(document.getElementById('chkAMDetail')!=null)	
				document.getElementById('chkAMDetail'+fileId).checked=true;
		}
	}
}
function insertSelectedFileCHKList(rejectionSendTo)
{
	var fileTable = document.getElementById('fileCheckListTable');
	var selectedFileList ="";
	var roleName="";    	
	var rowCounter = 1;
	var status="";
	 if(fileTable.rows.length>1)
	 {
	 		if(roleId==2)  	
	 			roleName="PM";
	 		else if(roleId==3)
	 			roleName="COPC";
	 		else if(roleId==4)
	 			roleName="SED";
	 		else
	 		 	roleName="AM";
	 			
			var chkLength=document.getElementsByName(''+roleName);				    	
	    	if(chkLength==undefined)
	    	{
	    		if(document.getElementById(''+roleName+''+rowCounter).checked==true)
	    		{
	    		   	selectedFileList=document.getElementById('fileId'+rowCounter).value;
		     	}	
	    	}
	    	else
	    	{	    		
		    	for (i = 0 ; i < fileTable.rows.length - 1; i++,rowCounter++)
		    	{	
		    		var fileId=document.getElementById('fileId'+rowCounter).value;	
			    	if(document.getElementById('chk'+roleName+''+'Detail'+fileId).checked==true)
		    		{
		    			//[003] Start
		    			if(rejectionSendTo==0)
		    			{
			    			if(fileId==14)
			    			{
			    				alert("Please Make Sure you have input Order No in Main Tab for One Time Documents");
			    			}
			    		}
		    			//[003] End	    		   	
		    		   	selectedFileList=selectedFileList+','+fileId;
		    		   	status=status+','+1;		    		   	
			     	}else
			     	{
			     		selectedFileList=selectedFileList+','+fileId;
		    		   	status=status+','+0;		    		   
			     	}
			     	
			     	
		    	}
		    	
		    }
		
		   var jsData =			
		   {
			fileIds:selectedFileList,
			orderNumber:orderNo,	
			roleId:roleId,
			docStatus:status
					
		};
		
		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
		if(selectedFileList!="")
		{
			var insertUpdate = jsonrpc.processes.saveUploadedFileCHKList(jsData);
			//alert("insertUpdate :"+insertUpdate);
		}
		
	}
   
}
//[00055] End
function takeAction(){
	//[250511AKS]Start
	//commented by kalpana,validation is not required if not approved  bug ID 15032013003
	/*var callerWindowObj = dialogArguments;	
	callerWindowObj.validateOrder('1');
	var isValidatePO =	callerWindowObj.document.getElementById("isValidatePO").value;	
	//if Validate successfully 
	if(isValidatePO==0)
	{
		callerWindowObj.updateStatus(0);
		//aNIL cHANGE FOR cLOSE WINDOW ERROR
		window.close();return;		
	}
	callerWindowObj.updateStatus(1);*/
	//comment end
	//[250511AKS]End
	var actionTaken = 'NO';		
	if(rolePartialInitiator){
		if(!(document.getElementById('selectAllServices').checked || document.getElementById('partialInitiate').checked)){
			alert("Please Select All or Partial Initiate the Services.");
			return false;
		}
		if(document.getElementById('partialInitiate').checked && document.getElementById('partialInitiatorTable').style.display.block==true && document.getElementById('serviceTable').style.display.block==true){
			if(allServiceCheckedOrNot()==false){
				alert("Please select atleast one service.");
	    		return false;	
			}
		}
	}
	for (i = 2,j=1; i <= 3; i++,j++) {
		if(document.getElementById('taskTypeStatus'+i)==null){
			alert('Please select one of the options');
			return false;
		}else if(document.getElementById('taskTypeStatus'+i).checked==true) {
			SaveAction(j);
			actionTaken = 'YES';
			return;
		}
	}	
	if(actionTaken == 'NO'){	
		alert('Please Approve/Reject the Order');	
	}
}

function SaveAction(action){
	var callerWindowObj = dialogArguments;
	var rejectionSendTo=0;
	var projectmgrid = 0;
	var actionRemarks = document.getElementById('actionRemraks').value;
	var reasonID=0;
	roleId = callerWindowObj.document.getElementById('searchTaskOwnerId').value;
	var order = callerWindowObj.document.getElementById("poNumber").value;
	if(actionRemarks==""){
		
		if((action==2 && document.getElementById('txtRejectionReason').value==17) || action==1 )
		{
			alert('Please enter Remarks');
			return false;
		}		
	}else if(actionRemarks.length>501){
		alert('Remarks should be less than 501 characters');
		return false;
	}
	
	//lawkush Start
		
	if(action==2 && (document.getElementById('txtRejectionReason').value==0 ))
	{
		alert('Please Enter Rejection Reason');
		return false;
	}	
	
	//lawkush End
	
	
	var selectedServiceList;
	count=document.getElementById('hdnCounterLength').value;
	var selectedServiceList='';
	if(rolePartialInitiator){
		for (i = 0 ; i<count; i++){
			if(document.getElementById('chkService'+i).checked==true){ 
				//selectedServiceList[i]=document.getElementById('chk'+i).value;
				if(selectedServiceList=='')
					selectedServiceList=document.getElementById('chkService'+i).value;
				else
					selectedServiceList = selectedServiceList+ "," + document.getElementById('chkService'+i).value;
	 		}
	 	}
 	}
	
	//added by kalpana,validation is required if approved bug ID 15032013003
	if(action==1){
		
	//Start [211]
		if(allServicesSelected==false && selectedServiceList==''){
			alert("Please select atleast one service !.");
			return false;
			
		}
	//End [211]	
	
		callerWindowObj.validateOrder('1',allServicesSelected,selectedServiceList);
		var isValidatePO =	callerWindowObj.document.getElementById("isValidatePO").value;	
		//if Validate successfully 
		if(isValidatePO==0){
			//var validInvalid = jsonrpc.processes.updateStatus(order,0); commented by [211]
			//callerWindowObj.updateStatus(0);
			//aNIL cHANGE FOR cLOSE WINDOW ERROR
			window.close();return;		
		}
		
		jsonrpc.processes.updateStatus(order,1);
	}
	//end
	//[001] Start
	if(action==2){
		if(roleId=="1"){
			alert('Account Manager cant Request for Information');
			return false;
		}/*else if(document.getElementById('actionRemraks').value==""){
			alert('Please enter Request for Information Remarks');
			return false;
		}else if(document.getElementById('actionRemraks').value.length>256){//[002]----Start
			alert('Remarks should be less than 256 characters');
			return false;
		}//[002]----End	*/
		rejectionSendTo	 = 1;//callerWindowObj.document.getElementById("rejectionTo").value;
		reasonID=document.getElementById("txtRejectionReason").value;
	}
	//[001] End

	//[210] Start
	var selectedServiceList;
	count=document.getElementById('hdnCounterLength').value;
	var selectedServiceList='';
	if(rolePartialInitiator){
		for (i = 0 ; i<count; i++){
			if(document.getElementById('chkService'+i).checked==true){ 
				//selectedServiceList[i]=document.getElementById('chk'+i).value;
				if(selectedServiceList=='')
					selectedServiceList=document.getElementById('chkService'+i).value;
				else
					selectedServiceList = selectedServiceList+ "," + document.getElementById('chkService'+i).value;
	 		}
	 	}
 	}
//[210] end
	if(rolePassedEscalation){
		var jsData=null;
		var delayReason = document.getElementById("delayReason").value
		if(null == delayReason || "" ==  delayReason || "0"==  delayReason){
			alert("Please enter delay reason");
			return;
		}
		var jsData = {
			taskID:document.getElementById('searchTaskId').value,
			actionId:action,	
			rejectionSendTo:rejectionSendTo,
			actionRemraks:document.getElementById('actionRemraks').value,
			projectmanagerassignedId:callerWindowObj.document.getElementById('hdnprojectmgrid').value,
			order_creation_source:callerWindowObj.document.getElementById('hdnOrderCreationSourceId').value,
			orderno:order,
			delayReason:document.getElementById('delayReason').value,
			reasonID:reasonID,
			partialInitiator:rolePartialInitiator,
			allServicesSelectd:allServicesSelected,
			serviceList:selectedServiceList,
			ownerId:callerWindowObj.document.getElementById('searchTaskOwnerId').value
		};
	}else{
		var jsData = {
		taskID:document.getElementById('searchTaskId').value,
		actionId:action,	
		rejectionSendTo:rejectionSendTo,
		actionRemraks:document.getElementById('actionRemraks').value,
		projectmanagerassignedId:callerWindowObj.document.getElementById('hdnprojectmgrid').value,
		order_creation_source:callerWindowObj.document.getElementById('hdnOrderCreationSourceId').value,
		orderno:order,
		delayReason:0,
		reasonID:reasonID,
		partialInitiator:rolePartialInitiator,
		allServicesSelectd:allServicesSelected,
		serviceList:selectedServiceList,
		ownerId:callerWindowObj.document.getElementById('searchTaskOwnerId').value
	};
	}
    jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
    var sessionId='<%=request.getSession().getId() %>';	
    var retVal = jsonrpc.processes.partialInititaionTask(jsData,sessionId);
	alert(retVal.msgOut); 	  
   
    /* Sending approval or reection mail and sms async*/
    if(retVal.isSuccessApproved==1 || retVal.isPartialInitiateMail==1){
    	jsonrpc.processes.sendIB2BApprovalRejectionMailAndSMS(retVal);    		
    }   
    
    //--------------[Disable Task Action button when user once submit the task::Date:05-07-2013]---------------------
     callerWindowObj.document.getElementById('btnTaskAction').disabled=true;
    //---------------------------------------------------------------------------------------------------------------
    
    
    //[00055] Start (Added By Ashutosh )
    if(retVal.msgOut!="" && retVal.msgOut!=null){
    	 insertSelectedFileCHKList(action);
    }
    //[00055] End   
    //------------------[ added by anil to prevent any action on new order after success aproval ]--------------------
    callerWindowObj.showOpqChangeLayer();
    //------------------[ end ]---------------------------------------------------------------------------------------
    window.close();
     //Added for enable publish button on last task owner approval and page is refresh on function call updateStatus()
    if(retVal.saveActionCalled==false){
    	//callerWindowObj.ViewServiceTree(6);
    	callerWindowObj.openOrderAfterApproval();
    	//callerWindowObj.DrawTable('SERVICENO','SERVICELINETABLE');
    }else if(roleId != isLastTaskOwnerId){
    	callerWindowObj.openInViewOrder();
    }else if(action==1){
    	callerWindowObj.ViewServiceTree(6);
    }else if(action==2){
    	callerWindowObj.openInViewOrder();
    }
    //callerWindowObj.goToHome();	//Changed from submitParent by Saurabh    
    return false;
}

function getSetDocumentDetails(fileId,type,counter)
	{
		var callerWindowObj = dialogArguments;
		sentMethod=1;
		fileTypeIdN=fileId;
		var accountID=callerWindowObj.document.getElementById('accountID').value;
		var poNumber=callerWindowObj.document.getElementById('poNumber').value;
		var accountNumber=callerWindowObj.document.getElementById('accNo').value;
		var crmAccountNo=callerWindowObj.document.getElementById('crmAccountNo').value;
		var counterbutton=counter;
		if(type==1)
		{
				if(accountID=="" || poNumber=="")
			    {
			    	alert('Please Select an Account or Order No Before Attaching a File');	    	
			    	return false;
			    }
			    else
			    {
			    	var width  = 700;
		 			var height = 300;
		 			var left   = (screen.width  - width)/2;
		 			var top    = (screen.height - height)/2;
		 			var params = 'width='+width+', height='+height;
		 				params += ', top='+top+', left='+left;
		 				params += ', directories=no';
		 				params += ', location=no';
		 				params += ', menubar=no';
		 				params += ', resizable=no';
		 				params += ', scrollbars=yes';
		 				params += ', status=no';
		 				params += ', toolbar=no';
		 				
					var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=goToFileAttachmentPage&orderNo="+poNumber+"&accountNo="+accountNumber+"&fileTypeId="+fileTypeIdN+"&sentMethod="+sentMethod+"&counterbutton="+counterbutton;	
					window.open(path,"FileAttachmentWindow",params); 						
				}
		
		}
		if(type==2)
		{
					if(accountID=="" || poNumber=="")
					    {
					    	alert('Please Select an Account or Order No Before Dowloading a File');	    	
					    	return false;
					    }
					    else
					    {
					    	var width  = 1000;
				 			var height = 600;
				 			var left   = (screen.width  - width)/2;
				 			var top    = (screen.height - height)/2;
				 			var params = 'width='+width+', height='+height;
				 				params += ', top='+top+', left='+left;
				 				params += ', directories=no';
				 				params += ', location=no';
				 				params += ', menubar=no';
				 				params += ', resizable=no';
				 				params += ', scrollbars=yes';
				 				params += ', status=no';
				 				params += ', toolbar=no';
				 				params += ', status=yes';
					    	
							var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=goToDownloadedFilePage&orderNo="+poNumber+"&accountNo="+accountNumber+"&crmAccountNo="+crmAccountNo+"&fileTypeId="+fileTypeIdN+"&sentMethod="+sentMethod+"&counterbutton="+counterbutton;	
							window.open(path,"FileDownloadWindow",params); 						
						}
		}
		
	}


 // *******************Filling Rejection Reason  Combo of approval page************************
 
 function fillRejectionReasons()
	{
 
	      var tr, td, i, j, oneRecord;
	     var rejectionReasonList;
	     var combo = document.getElementById("txtRejectionReason");
	     var iCountRows = combo.length;
	  
	    for (i = 1; i <  iCountRows; i++)
	    {
	       combo.remove(1);
	    }
		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	    rejectionReasonList = jsonrpc.processes.populateRejectionReason();
	    for(j=0;j<rejectionReasonList.list.length;j++)
	    {
	    	
		   	var option = document.createElement("option");
		  	option.text = rejectionReasonList.list[j].reasonName;
			option.value = rejectionReasonList.list[j].reasonID;
		  	option.title = rejectionReasonList.list[j].reasonName;
			try 
			{
				combo.add(option, null); //Standard
			}
			catch(error) 
			{
				combo.add(option); // IE only
			}
	    }
	  }
//	Filling Rejection Reason  Combo of approval page End
function enableDisableRejectionReason(actionType){
	var callerWindowObj = dialogArguments;
	var stage = callerWindowObj.document.getElementById('stageName').value;
	
    if(roleID==2 || roleID==3 || roleID==4){
    	if(actionType==2){
    	    if(!(stage == "Partial Initiated" || stage == "Partial Publish")){
    	    	document.getElementById('txtRejectionReason').disabled=false;
    	    }
    	    else{
    	    	alert("You cannot reject order at this stage.");
    	    	document.getElementById('taskTypeStatus3').checked=false;
				return false;
    	    }
		}else{
			document.getElementById("txtRejectionReason").value=0;
			document.getElementById('txtRejectionReason').disabled=true;
		}
	}
}
function drawApproveServicesTable(){
    var mytable = document.getElementById('approveServicesTable');
    mytable.innerHTML="";
    document.getElementById('txtPageNumber').value= pageNumber;
	sync(); 
	var callerWindowObj = dialogArguments;
    var poNumber=callerWindowObj.document.getElementById('poNumber').value;
    var sortByColumn='serviceno';
    var colors=new Array("normal","lightBg");
	var tableDataStart;
	var tableDataEnd = "</td>";
	var tableRowStart;
	var tableRowEnd = "</tr>";
	var tableStr = "<table width='100%' border='1' align='center' cellpadding='0' cellspacing='0' id='ServiceList'>";
	var tableData;
	var index_var=0;
	index_var=startRecordId;
	var roleID=callerWindowObj.document.getElementById('searchTaskOwnerId').value;
    var jsData = 
		{
			startIndex:startRecordId,
			endIndex:endRecordId,
			sortBycolumn:sortByColumn,
			sortByOrder:sortByOrder,
			orderNo:poNumber,
			roleId:roleId
		};
		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
		try
	{
		   	serviceList = jsonrpc.processes.getServiceListForTheOrderAndRoleWithPagination(jsData);
		}
   		catch(e)
   		{	
   			alert("exception :  "+ e);
		}
		iTotalLength=serviceList.list.length;	
		if(iTotalLength !=0){
			iNoOfPages = serviceList.list[0].maxPageNo;
		}else{     
		    iNoOfPages=1;
		}
		document.getElementById('txtMaxPageNo').value=iNoOfPages;
		document.getElementById('hdnCounterLength').value=serviceList.list.length;
	   	for (i = 0; i <  serviceList.list.length; i++)
	 	{
	 	    tableRowStart = "<tr>"; /* class='" + colors[parseInt(i)%2] + "'>";*/
    	    tableStr = tableStr + tableRowStart;
		    tableDataStart = "<td align='center' nowrap colspan='1' width='30%'>";				
		    tableData="<input class='inputBorder5' type='text' readonly='true' name='servicesId' id='servicesId"+i+"' value='"+ serviceList.list[i].serviceId +"' >";
			tableStr = tableStr + tableDataStart + tableData + tableDataEnd;
			tableDataStart = "<td align='center' nowrap colspan='1'  width='40%'>";
			tableData="<input class='inputBorder5' type='text' readonly='true' name='servicesName' id='servicesName"+i+"' value='"+ serviceList.list[i].serviceName +"'>";	
			tableStr = tableStr + tableDataStart + tableData + tableDataEnd;
			tableDataStart = "<td align='center' nowrap colspan='1' width='30%'>";				
			tableData="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name='chkService' id='chkService"+i+"' value='"+ serviceList.list[i].serviceId +"' type='checkbox' onclick='selectUnselectAllServices()'>";	
			tableStr = tableStr + tableDataStart + tableData + tableDataEnd;	
			tableStr = tableStr + tableRowEnd;	
		}	
		tableStr = tableStr + "</table>";
        mytable.innerHTML = tableStr;
		if(serviceList.list.length == 0)
		{
			var tblRow=document.getElementById('approveServices').insertRow();
    		tblcol=tblRow.insertCell();
			tblcol.align = "center";
			tblcol.vAlign="top";
			tblcol.colSpan = 7;
			str='NO SERVICES';
			CellContents = str;
			tblcol.innerHTML = CellContents;
			document.getElementById('first').disabled=true;
		    document.getElementById('prev').disabled=true;
		    document.getElementById('last').disabled=true;
		    document.getElementById('next').disabled=true;
		}
		else{
			document.getElementById("taskTypeStatus11").checked=false;
			var pagenumber=document.getElementById('txtPageNumber').value;
			var MaxPageNo=document.getElementById('txtMaxPageNo').value;
			if(pagenumber && MaxPageNo==1)
			{
				document.getElementById('first').disabled=true;
		     	document.getElementById('prev').disabled=true;
				document.getElementById('last').disabled=true;
		     	document.getElementById('next').disabled=true;
			}
			if(pagenumber==1 && MaxPageNo!=1){
				document.getElementById('first').disabled=true;
				document.getElementById('prev').disabled=true;
				document.getElementById('last').disabled=false;
				document.getElementById('next').disabled=false;
		}
			if(pagenumber==MaxPageNo && pagenumber!=1){
		   		document.getElementById('last').disabled=true;
		  		document.getElementById('next').disabled=true;
		   		document.getElementById('first').disabled=false;
			    document.getElementById('prev').disabled=false;
			}
		 	if(pagenumber!=MaxPageNo && pagenumber!=1){
		    	document.getElementById('last').disabled=false;
		    	document.getElementById('next').disabled=false;
		    	document.getElementById('first').disabled=false;
		    	document.getElementById('prev').disabled=false;
		 	}
		}		
}
function selectUnselectAllServices(){
	var count=document.getElementById('hdnCounterLength').value;
	var temp=0;
	for(var i=0; i<count; i++){
		if(document.getElementById('chkService'+i).checked==false)
			document.getElementById('taskTypeStatus11').checked=false;
		else
			temp++;
        }
    if(temp!=count)
    	document.getElementById('taskTypeStatus11').checked=false;
    //else
    	//document.getElementById('taskTypeStatus11').checked=true;
}
function allServiceCheckedOrNot(){
	var count=document.getElementById('hdnCounterLength').value;
	var temp=0;
	for(i=0; i<count; i++){
    	if(document.getElementById('chkService'+i).checked)
        	return true;
    }
   	return false;
}
function checkAll()
		{
	try{
		if(document.getElementById('taskTypeStatus11').checked==true)
		{
			CheckAllServices(true);
		}else{
			CheckAllServices(false);
		}
	}catch(e)
	{
		alert('error code 123: '+ e.message);
	}
}
function CheckAllServices(chkbox)
{
	try{
		var i=0;
		while(eval(document.getElementById('chkService'+i)))
		{
			eval(document.getElementById('chkService'+i)).checked=chkbox;
			i=i+1;
		}
	}catch(e)
	{
		alert('error code 124: '+ e.message);
	}
}
function selectAllServicesChecked(){
    allServicesSelected=true;
   if(document.getElementById('partialInitiate').checked==true)
   		document.getElementById('partialInitiate').checked=false;
    document.getElementById('serviceTable').style.display='none';
    document.getElementById('serviceTablePagination').style.display='none';
    document.getElementById('approveServices').style.display='none';
    document.getElementById('approveServicesTable').style.display='none';
}
function enablePartialInitiatedServices(){
    allServicesSelected=false;
    if(document.getElementById('selectAllServices').checked==true)
   		document.getElementById('selectAllServices').checked=false;
    document.getElementById('serviceTable').style.display='block';
    document.getElementById('serviceTablePagination').style.display='block';
    document.getElementById('approveServices').style.display='block';
	drawApproveServicesTable();
	document.getElementById('approveServicesTable').style.display='block';
}

</script>
</head>
<script type="text/javascript" src="npd/js/opaqueLayer.js"></script>
<script type="text/javascript" src="js/NewChangeOrderUtility.js"></script>
<script language="javascript">window.onresize = setLayerPosition;</script>
<body onload="setformValue();">
<div id="shadow" class="opaqueLayer"><font color="yellow" size="6"> Please wait..</font></div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/wz_tooltip.js"></script>
<html:form action="/viewOrderAction" enctype="text/plain" styleId="searchForm">
<div>

<html:hidden property="searchTaskId" />
<html:hidden property="searchTaskOwner" />
<div class="head"> <span>Approval/Rejection</span> </DIV>
		<fieldset class="border1">
			<legend> <b>Approval/Rejection</b> </legend>
			<table width="100%"  border="1" align="center" id = "taskTable" cellpadding="0" cellspacing="0" class="tab2" >
				<tr class="grayBg">
					<td width="3%" align="center">Select</td>
					<td width="17%">Action</td><td></td>
				</tr>
				<!--<tr class="lightBg">
					<td align="center"><input type="radio" name="taskTypeStatus" value="1" Onclick="javascript:saveTaskStatus('OPEN')"></td>
					<td>OPEN</td>
				</tr>-->
				<tr class="normal">
					<td align="center"><input type="radio" id="taskTypeStatus2"  name="taskTypeStatus" value="2"  onclick="enableDisableRejectionReason(1);enableDisablePartialInitiatorTable(1)" ></td>
					<td>CLOSE/APPROVED</td><td></td>
				</tr>
				 <%
					
					HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
					HttpSession sessionObj = (HttpSession) userHashMap.get(session.getId());
					UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
					//<!--Changed the Condition to allow PM selection for other than AM, PM and COPC for RoleChange by Vishwa on 18-Apr-2012	-->
					if (objUserDto.getUserRoleId().equalsIgnoreCase("2") || objUserDto.getUserRoleId().equalsIgnoreCase("3") || objUserDto.getUserRoleId().equalsIgnoreCase("4"))		
					{
				 %>
				<tr class="lightBg">
					<td align="center">
						<input type="radio" id="taskTypeStatus3" name="taskTypeStatus" value="3"   onclick="enableDisableRejectionReason(2);enableDisablePartialInitiatorTable(2)" ></td>
					<td>
						REQUEST FOR INFORMATION/REJECTION
					</td>
					
				</tr>
				<!--[03] Start-->
				<tr>
					<td width="16%" align="left" nowrap class="lightBg">
					Rejection Reason
					</td>
					<td width="32%" align="left" nowrap>
								<select onfocus="getTip_DD(this,this.selectedIndex,this.name)" name="txtRejectionReason" id="txtRejectionReason" class="dropdownMargin" isRequired="0" style="width:250px" >
									<option value="0" title="Select Rejection Reason">Select Rejection Reason</option>
								</select>										
					</td><td></td>
				</tr>
				<!--[03] End-->
				<%} %>
				<%if(request.getAttribute("ROLEPASSEDESCALATION").equals(true)){ %>
				<tr class="lightBg">
					<!--[00055] Start-->
					<td>
						Delay Reason
					</td>
					<!--[00055] End-->
					<td colspan="2" align="left">
						<select onfocus="getTip_DD(this,this.selectedIndex,this.name)" name="delayReason" id="delayReason" class="dropdownMargin" isRequired="0" style="width:300px" >
							<option value="0" title="Select Delay Reason">Select Delay Reason</option>
						</select>		
					</td><td></td>
				</tr>
				<%} %>
				<tr class="lightBg">
					<!--[00055] Start-->
					<td>
						Remarks
					</td>
					<!--[00055] End-->
					<td colspan="2" align="left">
						<html:textarea rows="10" cols="50" property="actionRemraks" onmouseover="getTip(value)" onmouseout="UnTip()" onblur="if( trim(this.value).length>0){return ValidateTextField(this,path,'Remarks')};"></html:textarea>
					</td>
					<td>
				</tr>
				<tr class="lightBg">
					<td colspan="8" align="center"> <div class="searchBg1"><a href="#" onClick="return takeAction()">Submit</a></div> 	</td>
				</tr>
			</table>
			<div id='partialInitiatorTable'>
			<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0"><tr>
				<td align="center"><input type="radio" id="selectAllServices"	name="selectAllServices" onclick="selectAllServicesChecked()" checked='true'></td><td style="font-weight: bold;">&nbsp;&nbsp;Select All Services</td>
				<td align="center"><input type="radio" id="partialInitiate"	name="partialInitiate" onclick="enablePartialInitiatedServices()"></td><td style="font-weight: bold;">&nbsp;&nbsp;Partial Initiate Services</td>
				</tr>
			</table>
			</div>
			<div id='serviceTable'>			
				<table id='serviceTablePagination' width="100%" border="1" align="center" cellpadding="0" cellspacing="0">
					<tr>
						<td align="center" nowrap height="40" colspan="1" width="30%"><a href="#" id="first" onclick="FirstPage('SERVICEID','SELECTSERVICES')">First</a>&nbsp;&nbsp;<a href="#" id="next" onclick="NextPage('SERVICEID','SELECTSERVICES')">Next</a></td>
						<td align="center" nowrap height="40" colspan="1" width="40%"><input type="text" id="txtPageNumber" class="inputBorder2" readonly="true" size="2" />of <input type="text" id="txtMaxPageNo" class="inputBorder2" readonly="true" size="2" />Pages</td>
						<td align="center" nowrap height="40" colspan="1" width="30%"><a href="#" id="prev" onclick="PrevPage('SERVICEID','SELECTSERVICES')">Prev</a>&nbsp;&nbsp;<a href="#" id="last"	onclick="LastPage('SERVICEID','SELECTSERVICES')">Last</a></td>
					</tr>
				</table>
               	<table id='approveServices' width="100%" border="1" align="center" cellpadding="0"  cellspacing="0">
					<tr style="font-weight: bold;">
					    <td align="center" nowrap height="40" colspan="1" width="30%">Service No</td>
					    <td align="center" nowrap height="40" colspan="1" width="40%">Service Name</td>
					 	<td align="center" nowrap height="40" colspan="1" width="30%">Approve
					 			<input type="checkbox" id="taskTypeStatus11"  name="taskTypeStatus11" value="2"  onclick="checkAll()" >
					      	    <input type="hidden" name="hdnCounterLength" id="hdnCounterLength" />
					    </td>
				</tr>
				</table>
				<div id='approveServicesTable' class='content1' onscroll='reposHeadService(this);'>
				</div>	
			</div>	
			<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" >
		    <tr style="font-weight: bold;">
			<td width="20%">Select All
					        
					        <input type="checkbox" name="SelectAllDocuments" id="SelectAllDocuments" onclick="javascript:fnCheckDocumentAll();"/>
					         </td>
			</tr>
				
				
				
				<!--[00055] Start-->
				<tr class="lightBg">
					<table width="100%"  border="1" align="center" id = "fileCheckListTable" cellpadding="0" cellspacing="0" class="tab2" >
					</table>
				</tr>
				<!--[00055] End-->
				<tr class="lightBg">
					<td colspan="2" align="center"> <div class="searchBg1"><a href="#" onClick="return takeAction()">Submit</a></div> 	</td>
				</tr>
				
				
				
				
			</table>
		</fieldset>
	</div>
</html:form>
</body>
<script type="text/javascript">
getRoleAccess();
if(rolePassedEscalation){
	getDelayReason();
}
document.getElementById('serviceTable').style.display='none';
document.getElementById('serviceTablePagination').style.display='none';
document.getElementById('approveServices').style.display='none';
document.getElementById('partialInitiatorTable').style.display='none';
if(rolePartialInitiator==true || rolePartialInitiator=='true'){
    document.getElementById('partialInitiatorTable').style.display='block';
}else{
    document.getElementById('partialInitiatorTable').style.display='none';
    document.getElementById('serviceTable').style.display='none';
	document.getElementById('serviceTablePagination').style.display='none';
	document.getElementById('approveServices').style.display='none';
	//document.getElementById('serviceTable').style.display='none';
}
</script>
</html>
		