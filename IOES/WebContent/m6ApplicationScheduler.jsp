<html>
<head>
<title>M6 Scheduler</title>
</head>
<body>
<table align="center">
	<tr>
		<td width="5%" align="right">
		<div class="link" align="right">
		<%
		if ((String) getServletContext().getAttribute("StartM6Scheduler") != null) {
		%>
		
		<td>
		<div><b>M6Scheduler is running..</b></div>
		</td>
		<%
		} else {
		%>
		<center><!--<a href="./defaultAction.do?method=StartM6Schedular"> 
							<b>Start M6Schedular</b> </a>
						-->
			<input type="button" value="Start M6Scheduler"
				onclick="javascript:location.href='./defaultAction.do?method=StartM6Scheduler';" />
		</center>
		<%
		}
		%>
		</div>
		</td>
	</tr>
</table>
</body>
</html>
