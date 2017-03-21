<!--Tag Name Resource Name  Date		CSR No			Description -->
<!--[001]	 ANIL KUMAR				21-june-2011				Remove Combo and set row for responsiblity-->
<!--[002]	 SAURABH SINGH			23-jan-2013					login automatically if there is only one role Point No 57 HyperCare-->
<!--[003]	 Santosh Srivastava		22-Jan-2014					Adding responsibility as lob in session



-->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.ibm.appsecure.util.Encryption"%>
<html>
<head>
<title>Welcome Page</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
<link href="gifs/style.css" type="text/css" rel="stylesheet">
	<script type="text/javascript">
	window.history.forward(1);
	function noBack() 
	{ 
		window.history.forward(1); 
	}
</script>
<!-- bug id [OPS01042013003] -vijay
add a style for footer -->
<style type="text/css">
	#footer {
		background: #CCCC99;
		bottom: 0;
	    height: 25px;
	    overflow: hidden;
	    padding: 5px;
	    position: fixed;
	    width: 100%;
	    
		}
</style>
<!-- end of style for footer -->	
</head>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="js/formValidations.js"></script>
  <link href="${pageContext.request.contextPath}/js/jquery-ui.css" rel="stylesheet" type="text/css"/>
  <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
  <script src="${pageContext.request.contextPath}/js/jquery-ui.min.js"></script>

  
<script type="text/javascript">



function Login(roleid)
{		
		var myform=document.getElementById("searchForm")
		myform.action="<%=request.getContextPath()%>/defaultAction.do?method=LoginPageAction";
		document.getElementById('responsiblity').value = roleid; 
    	myform.submit();   
}

//Fetch Role List acording to User
function fnGetRoleList()
{

	//var userId=document.getElementById("role").value;	
	
	var userId = '<%=request.getParameter("userId")%>';
	var test;
   // var combo = document.getElementById('responsiblity');	
	//var iCountRows = combo.length;
  	
  	var colors=new Array("normal","lightBg");
  	
   /*for (i = 1; i <  iCountRows; i++)
   {
      // combo.remove(1);
   }*/
	
    var jsData =			
    {
		userId:userId
	};
		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	    test = jsonrpc.processes.populateRoleList(jsData);    
    
    	if(test.list.length>0)
    	{
    		//start[002]
    		if(test.list.length==1)
    		{
    			Login(test.list[0].roleId);
    		}
    		//end[002]
    		else
    		{
			    /*for(j=0;j<test.list.length;j++)
			    {
			    	var option = document.createElement("option");
			   		option.text = test.list[j].roleName;
					option.value = test.list[j].roleId;
					try 
					{
					combo.add(option, null); //Standard
					
					}
					catch(error) 
					{
						combo.add(option); // IE only
					}
			    }*/
			    //start[001]
			    for(j=0;j<test.list.length;j++)
			    {
			    	var colorValue=parseInt(j)%2;	 		  
					var tblRow=document.getElementById('tabresponsiblity').insertRow();
					tblRow.className=colors[colorValue];
					
					var roleID=test.list[j].roleId;
					var tblcol=tblRow.insertCell();
						tblcol.align = "left";
						tblcol.vAlign="top";
						str=test.list[j].roleName;
						CellContents = str;
						tblcol.innerHTML = CellContents;
						
					var tblcol=tblRow.insertCell();
						tblcol.align = "center";
						tblcol.vAlign="top";
						str="<div class='searchBg1'><a href='#' onClick='Login("+ roleID +")'>Login</a></div>";
						CellContents = str;
						tblcol.innerHTML = CellContents;
						
			    }
			    //end[001]
			}
	    }
	    else
	    {
	    	alert('Not A Valid User....')
	    }
    
}

</script>
<body onload="noBack();fnGetRoleList();ShowDiv();" 
onpageshow="if (event.persisted) noBack();" onunload="" style="background:#ffffff; ">
	<div align="center">
          <div style="width: 100%; height: 100px; background: #c6c68f; border: 1px solid black; position: relative;display: none" id="divSlide"  onclick="javascript:ShowDiv()" >
		    <table width="1334" border="0">
              <tr>
                <td width="367"><div align="center"></div></td>
              </tr>
              <tr>
                <td><div align="center" class="style3"><i><b><font color="#ffffff">You are using upgraded version of IE </font></b></i></div></td>
              </tr>
              <tr>
                <td><div align="center" class="style3"><i><b><font color="#ffffff">Please Check your Compatiblity Settings </font></b></i></div></td>
              </tr>
              <tr>
                <td align="center"><span class="style4">Steps:-</span> <span class="style2"><strong>Click on Tools</strong> &gt;&gt; <strong>Compatiblity View Settings &gt;&gt; <br>
                  Check Display intranet sites in Compatbity View or type URL in Add Current this Website and Click OK </strong></span></td>
              </tr>
                    </table>
          </div>
        </div>
		<table width="100%"  border="0" cellspacing="0" cellpadding="0" >
			<tr height="25">
				<td background="gifs/bg-header-main.jpg" width="50%" align="left"><img src="gifs/logo-inner.jpg">&nbsp;</td>
				<td background="gifs/bg-header-main.jpg" width="50%" align="right">&nbsp;<font face="verdana" size="4" color="white"><b><i>iB2B-Integrated Order Entry Management System</i></b></font></td>
			</tr>
		</table>
		<table width="100%"  border="0" cellspacing="0" cellpadding="0" >
			<tr height="25">
				<td width="100%" class="userName">
					<%
						Encryption enc = new Encryption();
				 	%>
				Welcome: <b><%=enc.decrypt(request.getParameter("userId"))%></b>  	
			</td>
		</table>
	
	<div class="nav">Navigator</div>
	<html:form action="/defaultAction" styleId="searchForm" method="post">
	<input type="hidden" id="responsiblity" name="responsiblity"/>	
		<table width="70%"  border="0" cellspacing="0" cellpadding="0"  style="margin-left:20px; ">
			<tr>
				<td width="100%">
					<table  border="0" cellspacing="0" cellpadding="0" align="right">
						<tr>
							<td></td>
						</tr>
					</table> 
				</td>
			</tr>
			<tr>
				<td>
				<table width="100%" id="tabresponsiblity"  border="0" cellspacing="0" cellpadding="0" class="newPage">
						<tr><td valign="top" width="30%"><b>Responsiblity</b></td><td valign="top" width="30%"><b>Click here to login</b></td></tr>						
				</table>
				
					<input type="hidden" name="userId" id="userId" value="<%=request.getParameter("userId")%>">
					<input type="hidden" name="respId" id="respId" value="<%=request.getParameter("respId") %>" />
					<html:hidden property="userId" name="defaultBean"></html:hidden>
					<!--<table width="100%"  border="0" cellspacing="0" cellpadding="0" class="newPage">
						
				<tr>
					<td valign="top" width="30%">Responsiblity</td>
					<td valign="top" width="309">
					<select name="responsiblity" id="responsiblity">
							<option value="0">--Select--</option>
					 </select> 
					<input type="hidden" name="userId" id="userId" value="<%=request.getParameter("userId")%>">
								<html:hidden property="userId" name="defaultBean"></html:hidden>
					</td>
				</tr>
				<tr>
					<td valign="top" align="center" width="30%" colspan="2">
						<div class="searchBg1"><a href="#" onClick="return Login()">Login</a></div>
					</td></tr>
			</table>-->
				</td>
			</tr>
			
			
		</table>
	</html:form>
	<div class="nav"></div>
	<script type="text/javascript">
var Browser = {
     Version: function() {
    var version = 999; // we assume a sane browser
    if (navigator.appVersion.indexOf("MSIE") != -1)
      // bah, IE again, lets downgrade version number
      version = parseFloat(navigator.appVersion.split("MSIE")[1]);
    return version;
   }
  }
function ShowDiv() 
{		 
if (Browser.Version() >= 7) {
	$("#divSlide").slideToggle(1000);
}
}
</script>	
<!-- [OPS01042013003] add footer -->	
<div align="center" id="footer" class="footer">
			Supported Browsers: Microsoft Internet Explorer 7.0 and above (with Compatiblity View)
</div>	
<!-- [OPS01042013003] end of footer -->
</body>
</html>
