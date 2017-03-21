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
<script type="text/javascript">
function TaskStatusPieChart(status)
{
	
	var myForm=document.getElementById('searchForm');
	projectID=document.getElementById('searchprojectid').value;
	var da=new Date();
	windowDefault='<%=Messages.getMessageValue("windowDefault")%>';
	var chil=window.open("","ProjectPlan"+da.getTime(),windowDefault);
	var strF="<HTML><HEAD></HEAD><BODY><FORM name='childform' method='post' action='<c:out value='${sessionScope.MenuContextPath}' />/projectPlanReport.do'>";
	var str1="<input type='hidden' name='method' value='viewrProjectPlanDetail'>";
	var str2="<input type='hidden' name='searchprojectid' value='"+projectID+"'> <input type='hidden' name='taskStatus' value='"+status+"'>";
	var strL="</FORM></BODY></HTML>";			
	var str=strF+str1+str2+strL;
	chil.document.write(str);
	var chilForm=chil.document.childform;
	chilForm.submit();
}

function TaskDelayPieChart(delayStatus)
{
	var myForm=document.getElementById('searchForm');
	if(delayStatus=='Delay')
	{
		//document.searchForm.action="<%=request.getContextPath()%>/projectPlanReport.do?method=viewrProjectPlanDetail";
		//document.getElementById("delayindays").value=1;
		//document.getElementById("taskDelayValue").value=delayStatus;
		//var myForm=document.getElementById('searchForm');*/
		
		projectID=document.getElementById('searchprojectid').value;
		var da=new Date();
		windowDefault='<%=Messages.getMessageValue("windowDefault")%>';
		var chil=window.open("","ProjectPlan"+da.getTime(),windowDefault);
		var strF="<HTML><HEAD></HEAD><BODY><FORM name='childform' method='post' action='<c:out value='${sessionScope.MenuContextPath}' />/projectPlanReport.do'>";
		var str1="<input type='hidden' name='method' value='viewrProjectPlanDetail'>";
		var str2="<input type='hidden' name='searchprojectid' value='"+projectID+"'> <input type='hidden' name='delayindays' value='1'> <input type='hidden' name='taskDelayValue' value='"+delayStatus+"'>";
		var strL="</FORM></BODY></HTML>";			
		var str=strF+str1+str2+strL;
		chil.document.write(str);
		var chilForm=chil.document.childform;
		chilForm.submit();
	}
	if(delayStatus=='No Delay')
	{
		//document.searchForm.action="<%=request.getContextPath()%>/projectPlanReport.do?method=viewrProjectPlanDetail";
		//document.getElementById("delayindays").value=0;
		//document.getElementById("taskDelayValue").value=delayStatus;
		projectID=document.getElementById('searchprojectid').value;
		var da=new Date();
		windowDefault='<%=Messages.getMessageValue("windowDefault")%>';
		var chil=window.open("","ProjectPlan"+da.getTime(),windowDefault);
		var strF="<HTML><HEAD></HEAD><BODY><FORM name='childform' method='post' action='<c:out value='${sessionScope.MenuContextPath}' />/projectPlanReport.do'>";
		var str1="<input type='hidden' name='method' value='viewrProjectPlanDetail'>";
		var str2="<input type='hidden' name='searchprojectid' value='"+projectID+"'> <input type='hidden' name='delayindays' value='0'> <input type='hidden' name='taskDelayValue' value='"+delayStatus+"'>";
		var strL="</FORM></BODY></HTML>";			
		var str=strF+str1+str2+strL;
		chil.document.write(str);
		var chilForm=chil.document.childform;
		chilForm.submit();
	}
}

function TaskMonthTaskPieChart(Month_Name)
{
	//var myForm=document.getElementById('searchForm');
	//document.searchForm.action="<%=request.getContextPath()%>/projectPlanReport.do?method=viewrProjectPlanDetail";
	//document.getElementById("monthName").value=Month_Name;
	//myForm.submit();
	
	projectID=document.getElementById('searchprojectid').value;
	var da=new Date();
	windowDefault='<%=Messages.getMessageValue("windowDefault")%>';
	var chil=window.open("","ProjectPlan"+da.getTime(),windowDefault);
	var strF="<HTML><HEAD></HEAD><BODY><FORM name='childform' method='post' action='<c:out value='${sessionScope.MenuContextPath}' />/projectPlanReport.do'>";
	var str1="<input type='hidden' name='method' value='viewrProjectPlanDetail'>";
	var str2="<input type='hidden' name='searchprojectid' value='"+projectID+"'> <input type='hidden' name='monthName' value='"+Month_Name+"'>";
	var strL="</FORM></BODY></HTML>";			
	var str=strF+str1+str2+strL;
	chil.document.write(str);
	var chilForm=chil.document.childform;
	chilForm.submit();
}

function StageGanttChart(stageName)
{
	projectID=document.getElementById('searchprojectid').value;
	var da=new Date();
	windowDefault='<%=Messages.getMessageValue("windowDefault")%>';
	var chil=window.open("","ProjectPlan"+da.getTime(),windowDefault);
	var strF="<HTML><HEAD></HEAD><BODY><FORM name='childform' method='post' action='<c:out value='${sessionScope.MenuContextPath}' />/projectStatusReport.do'>";
	var str1="<input type='hidden' name='method' value='viewStageGanttChart'>";
	var str2="<input type='hidden' name='searchProjectid' value='"+projectID+"'> <input type='hidden' name='searchStagename' value='"+stageName+"'>";
	var strL="</FORM></BODY></HTML>";			
	var str=strF+str1+str2+strL;
	chil.document.write(str);
	var chilForm=chil.document.childform;
	chilForm.submit();
}
</script>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<script language="JavaScript" src="js/FusionCharts.js"></script>
<title>Project Gantt Chart</title>
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
					<%//String strtaskcount="";
					//strtaskcount=(String)request.getAttribute("TaskCount");
					//int taskcount=Integer.parseInt(strtaskcount);
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
		<tr>
			<table border="1" align="center" width="50%" cellspacing="0" cellpadding="3" align="center">
				<div id="chartdiv" align="center"> </div>
					<% HashMap stageLegend=((ProjectPlanReportDto)request.getAttribute("OBJ_PROJECT_PLAN")).getMap_Stage_Color();
							Set stageSet=stageLegend.keySet();
							Iterator iter=stageSet.iterator();					
							while(iter.hasNext())
							{	
								String stageName=(String)iter.next();
								Integer index=(Integer)stageLegend.get(stageName);
								String colorCode=Utility.colorCodes[index.intValue()];
					%>
						<tr>
							<td width="50%">
								<a href="#" onclick="javascript:StageGanttChart('<%=stageName %>')"><i><font color="#FF0000"><%=stageName %></font></i></a>
							</td> 
							<td bgcolor="<%=colorCode %>" width="50%">
								<a href="#" onclick="javascript:StageGanttChart('<%=stageName %>')">&nbsp;</a>
							</td>
						</tr>
					<%}%>
				
			</table>
		</tr>
	</table>
	<table border=0>
		<tr>
			<td valign=top>
				<chart:streamed chartType="pieApp" > 
					<chart:param name="antialiasOn" value="true" />
					<chart:param name="width" value="400" />
					<chart:param name="height" value="400" />
					<chart:param name="backgroundImage" value="C:\blur_circle.jpg" />
					<chart:param name="backgroundTexture" value="-1" />
					<chart:param name="xLoc" value="0.49" />
					<chart:param name="yLoc" value="0.51" />
					<chart:param name="pieWidth" value="0.58" />
					<chart:param name="pieHeight" value="0.58" />
					<chart:param name="titleString" value="Task Status" />
					<chart:param name="subTitleString" value="% of Task Completed, Open and Pending" />
					<chart:param name="titleFont" value="Arial,14,1" />
					<chart:param name="subTitleFont" value="Arial,12,2" />
					<chart:param name="dwellUseXValue" value="false" />
					<chart:param name="dwellYString" value="XX Task" />
					<chart:param name="textLabelsOn" value="anything" />
					<chart:param name="3D" value="anything" />
	
					<logic:notEmpty name="TaskStatusPieChartBean" scope="request">
						<logic:iterate id="row1"  name="TaskStatusPieChartBean" indexId="index1">
							<%String taskStatus=((ProjectPlanReportDto)row1).getTaskCount(); 
							  String taskStatusString=((ProjectPlanReportDto)row1).getTaskStatusString();
							  String taskStatusQuery=((ProjectPlanReportDto)row1).getTaskStatusQuery();%>
							<chart:param name="dataset0yValues" value="<%=taskStatus %>" />
							<chart:param name="dataset0Labels" value="<%=taskStatusString %>" />
							<chart:param name="dataset0Links" value="<%=taskStatusQuery %>"/>
						</logic:iterate>
					</logic:notEmpty>
				</chart:streamed>
			</td>
			<td valign=top>
				<chart:streamed chartType="pieApp" > 
					<chart:param name="antialiasOn" value="true" />
					<chart:param name="width" value="400" />
					<chart:param name="height" value="400" />
					<chart:param name="backgroundImage" value="C:\blur_circle.jpg" />
					<chart:param name="backgroundTexture" value="-1" />
					<chart:param name="xLoc" value="0.49" />
					<chart:param name="yLoc" value="0.51" />
					<chart:param name="textLabelsOn" value="true" />
					<chart:param name="pieWidth" value="0.58" />
					<chart:param name="pieHeight" value="0.58" />
					<chart:param name="titleString" value="Task Delay" />
					<chart:param name="subTitleString" value="% of Task Delay" />
					<chart:param name="titleFont" value="Arial,14,1" />
					<chart:param name="subTitleFont" value="Arial,12,2" />
					<chart:param name="dwellUseXValue" value="false" />
					<chart:param name="dwellYString" value="XX Task" />
					<chart:param name="3D" value="anything" />
					
					<logic:notEmpty name="TaskDelayPieChartBean" scope="request">
						<logic:iterate id="row2"  name="TaskDelayPieChartBean" indexId="index1">
							<%String taskDelay=((ProjectPlanReportDto)row2).getTaskDelayCount(); 
							  String taskDelayString=((ProjectPlanReportDto)row2).getTaskDelayString();
							  String taskDelayQuery=((ProjectPlanReportDto)row2).getTaskDelayQuery();%>
							<chart:param name="dataset0yValues" value="<%=taskDelay %>" />
							<chart:param name="dataset0Labels" value="<%=taskDelayString %>" />
							<chart:param name="dataset0Links" value="<%=taskDelayQuery %>" />
						</logic:iterate>
					</logic:notEmpty>
				</chart:streamed>
			</td>
			<td valign=top>
				<chart:streamed chartType="pieApp" > 
					<chart:param name="byteStream" value="true" />
					<chart:param name="antialiasOn" value="true" />
					<chart:param name="width" value="400" />
					<chart:param name="height" value="400" />
					<chart:param name="backgroundImage" value="C:\blur_circle.jpg" />
					<chart:param name="backgroundTexture" value="-1" />
					<chart:param name="xLoc" value="0.49" />
					<chart:param name="yLoc" value="0.51" />
					<chart:param name="textLabelsOn" value="true" />
					<chart:param name="pieWidth" value="0.4" />
					<chart:param name="pieHeight" value="0.4" />
					<chart:param name="titleString" value="Month-Task Ratio" />
					<chart:param name="subTitleString" value="No of Task in a Month" />
					<chart:param name="titleFont" value="Arial,14,1" />
					<chart:param name="subTitleFont" value="Arial,12,2" />
					<chart:param name="dwellUseXValue" value="false" />
					<chart:param name="dwellYString" value="XX Task" />
					<chart:param name="imageLocation" value="C:/NPD" />
					<chart:param name="3D" value="anything" />
					<chart:param name="writeDirectory" value="C:/NPD" />
					<chart:param name="fileName" value="abc.png" />
					<chart:param name="labelsOn" value="true"/>
					
	
					<logic:notEmpty name="TaskInMonthPieChartBean" scope="request">
						<%String totalTaskCount="";
						  String month_Name=""; 
						  String monthQuery="";%>
						<logic:iterate id="row3"  name="TaskInMonthPieChartBean" indexId="index1">
							<%if ((totalTaskCount==null) || (totalTaskCount.equalsIgnoreCase("")))
							  {
								totalTaskCount=((ProjectPlanReportDto)row3).getTotalTaskCount(); 
							  }
							  else
							  {
							  	totalTaskCount=totalTaskCount+","+((ProjectPlanReportDto)row3).getTotalTaskCount(); 
							  }
							  if ((month_Name==null) || (month_Name.equalsIgnoreCase("")))
							  {
								month_Name=((ProjectPlanReportDto)row3).getMonth_Name();
							  }
							  else
							  {
							  	month_Name=month_Name+","+((ProjectPlanReportDto)row3).getMonth_Name(); 
							  }
							  if ((month_Name==null) || (month_Name.equalsIgnoreCase("")))
							  {
								monthQuery=((ProjectPlanReportDto)row3).getMonthQuery();
							  }
							  else
							  {
							  	monthQuery=monthQuery + "," +((ProjectPlanReportDto)row3).getMonthQuery();
							  }
							  
							  %>
						</logic:iterate>
						<chart:param name="dataset0yValues" value="<%=totalTaskCount %>" />
						<chart:param name="dataset0Labels" value="<%=month_Name %>" />
						<chart:param name="dataset0Links" value="<%=monthQuery %>" />
					</logic:notEmpty>
				</chart:streamed>
			</td>
		</tr>
	</table>
</html:form>
