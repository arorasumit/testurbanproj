<html>
<head>
<title>FxScheduler</title>
</head>
<body>
<table align="center">
	<tr>
		<td width="5%" align="right">
		<div class="link" align="right">
		<%
		if ((String) getServletContext().getAttribute("StartFxScheduler") != null) {
		%>
		
		<td>
		<div><b>FxScheduler is running...</b></div>
		</td>
		<%
		} else {
		%>
		<center><!--<a href="./defaultAction.do?method=StartFxSchedular"> 
				<b>Start FxSchedular</b>
			</a>	-->
			 <input type="button" value="Start FxScheduler"
				onclick="javascript:location.href='./defaultAction.do?method=StartFxScheduler';" />

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
