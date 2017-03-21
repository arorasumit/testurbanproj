
 
<!--[001]	 LAWKUSH 		06-MAY-11	00-05422		Setting Paging ans sorting -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<html>
<head>
<title>FSE</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta name="GENERATOR" content="Rational Software Architect">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/PagingSorting.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script language="javascript" type="text/javascript">

function DrawFE(fieldEngineer,partnerId)
{
	   var str;
	   //alert('sadasd')
	   //var frm=document.getElementById('currencyTypes');
	  
	   var mytable = document.getElementById('tabChannelPartnerType');	
	   var iCountRows = mytable.rows.length;
  
	   for (i = 1; i <  iCountRows; i++)
	   {
	       mytable.deleteRow(1);
	   }   	  
	   //document.getElementById('txtPageNumber').value= pageNumber;
	   sync();	
	  
	   var jsData =
			{
			   fieldEngineer:fieldEngineer,
			   channelPartnerId:partnerId
			};
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	
	var lstChannelPartner = jsonrpc.processes.getFieldEngineer(jsData);
	
	var v_iTotalLength=lstChannelPartner.list.length;	
	/* if(v_iTotalLength !=0)
	{
		var v_iNoOfPages = lstChannelPartner.list[0].maxPageNo;
		iNoOfPages=v_iNoOfPages;
	}
	 else
	{     
	    v_iNoOfPages=1;
	} */
	
	
	/* document.getElementById('txtMaxPageNo').value=v_iNoOfPages; */				
	var colors=new Array("normal","lightBg");
	
	var counter = <%=request.getAttribute("count")%>;
	//  var index=0;
	 for (i = 0; i <  v_iTotalLength; i++)
	 {
	 	var colorValue=parseInt(i)%2;	 
	   // var currencyTypeName=lstChannelPartner.list[i].currencyCode;
	   // var currencyID=lstChannelPartner.list[i].currencyID;

	   	var tblRow=document.getElementById('tabChannelPartnerType').insertRow();
		tblRow.className=colors[colorValue];
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=i+1;
		CellContents = str;
		tblcol.innerHTML = CellContents;

		/* //str="<input name=chk id=chk type=radio onclick=getInfoAndUpdate('"+ escape(lstChannelPartner.list[i].channelPartnerId) +"','"+ escape(lstChannelPartner.list[i].channelPartnerCtgryName) +"','"+ escape(lstChannelPartner.list[i].channelPartnerCode) +"') />";
		str="<a href='#' name=chk id=chk onclick=getInfoAndUpdate('"+ escape(lstChannelPartner.list[i].channelPartnerId) +"','"+ escape(lstChannelPartner.list[i].channelPartnerCtgryName) +"','"+ escape(lstChannelPartner.list[i].channelPartnerCode) +"')>Select</a>";
		//str="<a href='#' onclick="sortOrder('PARTNER_NAME','SELECTCHANNELPARTNER')">Channel Partner</a>"	;
		CellContents = str;
		tblcol.innerHTML = CellContents;
		 */
		tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		str=lstChannelPartner.list[i].fieldEngineer;
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		if(lstChannelPartner.list[i].status==1){
			str="Active";
		}else{
			str="Inactive";
		}
		CellContents = str;
		tblcol.innerHTML = CellContents;
		/* 
		tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		str=lstChannelPartner.list[i].channelPartnerCode;
		CellContents = str;
		tblcol.innerHTML = CellContents;
 */	}

	if(v_iTotalLength== 0)
	{
		var tblRow=document.getElementById('tabChannelPartnerType').insertRow();
	    tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		tblcol.colSpan = 3;
		str='NO RECORD FOUND'
		CellContents = str;
		tblcol.innerHTML = CellContents;
	}	
		return false;
}
//
function clearFields()
{
	document.getElementById('txtFieldEngName').value="";
}
//var path='<%=request.getContextPath()%>';
function isBlankFields()
{
	//DrawFE('',<%=request.getParameter("partnerId")%>);
	var fieldEngineer = document.getElementById('txtFieldEngName').value
	//if(fieldEngineer!=''){
	DrawFE(fieldEngineer,<%=request.getParameter("partnerId")%>);
	//}
}

	function setValue()   //called this function on onload 
{
	
	var callerWindowObj = dialogArguments;
	
	//document.getElementById('txtChannelPartnerCode').value=callerWindowObj.document.getElementById('curShortCode').value ;

}
//
</script>
</head>
<body>

<center>
<div class="head"> <span>ATTACHED FSE</span></div>
<html:form action="/NewOrderAction" styleId="displayFE" onsubmit="return false;" method="post">
	<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >
			   <tr >
					<td   >
					<div ><strong>FSE:</strong><input type="text" maxlength="50" id="txtFieldEngName" name="txtFieldEngName" class="inputBorder1" onkeydown="if (event.keyCode == 13)  return isBlankFields();"   >
					</div>
					</td>
					<td>
					<a href="#"><img onclick="isBlankFields()" src="npd/Images/search.gif" title="search" height="15"></a>
					<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>&nbsp;
					</td>
			  </tr>
	</table>
			<table width="100%"  border="1" id="tabChannelPartnerType" cellpadding="0" cellspacing="0" class="tab2" >			   
				<tr class="grayBg">
				    <td width="2%" align="center">Serial No.</td>
					<td width="10%" align="left" style="margin-left:10px;">
						FSE				
					</td>
					<td width="10%" align="left" style="margin-left:10px;">
						Status				
					</td>
				</tr>
			</table>		
	<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >
		<!-- <tr class="grayBg">
			<td width="18%" >&nbsp;</td>
			<td width="20%" colspan="3">&nbsp;</td>
		</tr> -->
	</table>	
</html:form>
</center>
</body>
<script type="text/javascript">
	//DrawCurrencyType()
	setValue()
	DrawFE('',<%=request.getParameter("partnerId")%>);
</script>
</html>


	
 