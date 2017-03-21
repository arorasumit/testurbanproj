<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
  <script src="js/jquery-latest.js"></script>	
  <script type="text/javascript" src="js/j1.js"></script>
  <script type="text/javascript" src="../../js/jsonrpc.js"></script>
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
	$("#add").click(function()
	{
  		document.getElementById('browser').innerHTML= "";
	    var jsData = 
	    {
	    	serviceId:345,
	    	orderNumber:88
	    };
	  	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
		 var treeView = jsonrpc.processes.ViewServiceTreeView(jsData);
		var treeString="";
	  	for(var j=0;j<treeView.lstTreeItems.list.length;j++)
	  	{
			treeString = treeString + "<li><span class='file'><a href='#' onclick=alert(\'"+ treeView.lstTreeItems.list[j].treeViewURL +"\');>"+ treeView.lstTreeItems.list[j].treeViewText + "</a></span></li>"    		
	   	}	
	 	var branches = $("<li><span class='folder'>Service</span><ul>" + 
	 		treeString +
	 		"</ul></li>").appendTo("#browser");
	 	$("#browser").treeview({
	 		add: branches
	 	});
	 });
  });
  </script>

<body>
  
  <ul id="browser" class="filetree">
   </ul>

   <button id="add">Add!</button>
</body>

</html>
