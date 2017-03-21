<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<html>
<head>
<!--<link href="${pageContext.request.contextPath}/CSS/style.css" rel="stylesheet" type="text/css">-->
<title>Insert title here</title>
<% 
							String  currPageNumber =request.getParameter("currPageNumber");
							String  maxPageNumber =request.getParameter("maxPageNumber");
							request.setAttribute("currPageNumber",currPageNumber);
							request.setAttribute("maxPageNumber",maxPageNumber);
							%>
<script type="text/javascript">
function goToPageSubmit()
{
	var maxPages=<bean:write name="maxPageNumber"/>;
	var temp=document.getElementById('id_paging_goToPage');
	
	if(temp.value!=null && temp.value!="")
	{
		var str=new String(temp.value);
	
		var reg =/^([0-9]+)$/;
	    if(reg.test(temp.value) == false) {
   		alert("Only Numeric Digits are Allowed in Go To Page Field");
   		temp.focus();
    	return false;
	   	}
	   	if(Number(temp.value)==0)
	   	{
	   		alert("Go To Page Number Cannot Be Less Than 1.");
	   		temp.focus();
	   		return false;
	   	}
	   	if(Number(temp.value)>Number(maxPages))
	   	{
	   		alert("Go To Page Number Cannot Be Greater Than Total No of Pages.");
	   		temp.focus();
	   		return false;
	   	}
	   	pagingSubmit(temp.value);
	}
	else
	{
		alert("Please Enter Page Number");
		temp.focus();
		return false;
	}
	
}
</script>
</head>
<body>
<table border="0" align="center" cellpadding="0" cellspacing="1"  width="100%" class="tabpage">  
							<tr>
							
						<td align="left"  class="last">
							
							<logic:equal name="currPageNumber" value="1">First</logic:equal>
							<logic:notEqual name="currPageNumber" value="1">
								<a href="#" onclick="pagingSubmit('1')" >First</a>
							</logic:notEqual>
							&nbsp;|&nbsp;						
							<%if(Integer.parseInt((String)currPageNumber)<Integer.parseInt((String)maxPageNumber)){ %>
								<a href="#" onclick="pagingSubmit('<%=Integer.parseInt((String)currPageNumber)+1%>')" class="last" >Next </a>
							<%}else { %>
								Next
							<%}%>
						</td>
						<td align="center"  class="last">Go To Page :
							<input type="text" class="inputNew" name="goToPageNumber" size="4" 
								maxlength="10" value="<bean:write name='currPageNumber'/>" id="id_paging_goToPage">
								<a href="#" onclick="goToPageSubmit()">GO </a>
						</td>
						<td align="center" >Page <bean:write name="currPageNumber"/> of <bean:write name="maxPageNumber"/></td>
						<td align="right"  class="last" >
							<logic:equal name="currPageNumber" value="1">Prev</logic:equal>
							<logic:notEqual name="currPageNumber" value="1">
								<a href="#" onclick="pagingSubmit('<%=Integer.parseInt((String)currPageNumber)-1%>')" >Prev </a>
							</logic:notEqual>
							&nbsp;|&nbsp;
							<%if(Integer.parseInt((String)currPageNumber)<Integer.parseInt((String)maxPageNumber)){ %>
								<a href="#" onclick="pagingSubmit('<%=maxPageNumber %>')" class="last">Last</a>
							<%}else { %>
								Last
							<%}%>
						</td>
					</tr>
						</table>
</body>
</html>