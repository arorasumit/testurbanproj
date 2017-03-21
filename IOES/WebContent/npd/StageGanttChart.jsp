<%@ taglib uri="/WEB-INF/kavachart-taglib.tld" prefix="chart" %>
<%@page import="com.ibm.ioes.npd.hibernate.beans.ProjectPlanReportDto"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Date"%>
<%@page import="com.ibm.ioes.npd.utilities.Utility"%>
<%@page import="com.ibm.ioes.npd.utilities.AppConstants"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="org.eclipse.jst.j2ee.application.ApplicationResource"%>
<%@page import="com.ibm.ioes.npd.utilities.Messages"%>
<%@page import="com.ibm.ioes.npd.beans.ProjectStatusReportBean"%>
<%@page import="com.ibm.ioes.npd.beans.ProjectDetailedStatusReportBean"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Iterator;"%>
<head>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<script language="JavaScript" src="js/FusionCharts.js"></script>
<title>Stage Project Gantt Chart</title>
	<link type="text/css" rel="stylesheet" href="../../../documentation/style.css" />
</head>
<html:form action="/projectDetailedStatusReport" method="post" styleId="searchForm">
	<input type="hidden" name="searchprojectid" value="<%=((ProjectPlanReportDto)request.getAttribute("OBJ_PROJECT_PLAN")).getProjectPlanId() %>"/>
	<input type="hidden" name="taskStatus" value=""/>
	<input type="hidden" name="delayindays" value=""/>
	<input type="hidden" name="taskDelayValue" value=""/>
	<input type="hidden" name="monthName" value=""/>
	<table width="98%" border="1" cellspacing="0" cellpadding="3" align="center" height="100%">
		<tr>
			<td>
				<div id="chartdiv" align="center"> </div>
				<script type="text/javascript">
					<%
					int taskcount=((Integer)request.getAttribute("TaskCount")).intValue();
					if(taskcount<=100)
					{%>
					var chart = new FusionCharts("Charts/FCF_Gantt.swf", "ChartId", "1950", "1650");
					<%}%>
					<%if((taskcount>100) && (taskcount<=200))
					{%>
					var chart = new FusionCharts("Charts/FCF_Gantt.swf", "ChartId", "3000", "3000");
					<%}%>
					<%if(taskcount>200)
					{%>
					var chart = new FusionCharts("Charts/FCF_Gantt.swf", "ChartId", "4100", "4100");
					<%}%>
					//chart.setDataURL("Data/Gantt1.xml");
					chart.setDataURL("Data/<%=request.getAttribute("GanttFileName")%>");
					chart.render("chartdiv");
				</script>
			</td>
		</tr>
	</table>
</html:form>
