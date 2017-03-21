<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
	
  <script type="text/javascript" src="js/j1.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<link href="gifs/treeview.css" type="text/css" rel="stylesheet">
  <style sr type="text/css">
  #browser {
    font-family: Verdana, helvetica, arial, sans-serif;
    font-size: 100%;
  }
  </style>
  <script>
 $(document).ready(function()
 {
 	$("#browser").treeview();
  });
  </script>

<body>
  
  <ul id="browser" class="filetree">
   </ul>
</body>

</html>
