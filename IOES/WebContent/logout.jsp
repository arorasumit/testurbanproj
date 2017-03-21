<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script language="javascript">
window.name = "idthWindow";
function init() {
//alert(document.cz_launch.accountId.value);
//document.cz_launch.submit();
alert('You have successfully logged out');
window.close();
}
</script>
<% 
	
	String logoutMsg = request.getAttribute("ErrorMsg").toString();
%>

</head>
<body onload="init();">
<form method="post" id="cz_launch" name="cz_launch">
<%=logoutMsg  %>
</body>
</html>