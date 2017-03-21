<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<link rel="stylesheet" type="text/css" href="gifs/sdmenu.css" />
<link rel="stylesheet" type="text/css" href="CSS/chromestyle.css">
<link type="text/css" rel="stylesheet" href="CSS/style.css">

<%@page import="com.ibm.ioes.npd.hibernate.beans.TmWorkflowstage"%>
<html:html>
<head>
<%
response.setContentType("application/vnd.ms-excel");
%>
</head>
<body>

<%
int nSNo = 0;
%>

<logic:present name="listProjectPlan" scope="request">
			<%
						response.setHeader("content-Disposition",
						"attachment;filename=ProjectPlanReport.xls");
			%>
	<strong>Project Plan</strong>
	<table width="100%" border="1" cellpadding="0" cellspacing="0">
		<tr bgcolor="#FF9255" align="Center">
			<td align="left" nowrap>SNo.</td>
			<td align="left" nowrap>Project Id</td>
			<td align="left" nowrap>Project Name</td>
			<td align="left" nowrap>Stage</td>
			<td align="left" nowrap>Task</td>
			<td align="left" nowrap>Planned Start Date</td>
			<td align="left" nowrap>Planned End Date</td>
			<td align="left" nowrap>Acutal Start Date</td>
			<td align="left" nowrap>Actual End Date</td>
			<td align="left" nowrap>Stake Holder Role</td>
			<td align="left" nowrap>Stake Holder Name</td>
			<td align="left" nowrap>Delay In Days</td>
			<td align="left" nowrap>No Of Days In Project</td>
		</tr>

		<logic:empty name="listProjectPlan">No Record Found</logic:empty>
		<logic:notEmpty name="listProjectPlan">
			<logic:iterate name="listProjectPlan" id="row" indexId="recordId"
				type="com.ibm.ioes.npd.hibernate.beans.ProjectPlanReportDto">
				<%
							String classType = null;
							/*if (recordId.intValue() % 2 == 0) {
						classType = "redBg";
							} else {
						classType = "alterRedBg";
							}*/
							nSNo++;

							if (row.getDelayindays() > 0) {
						classType = "red";
							}
				%>
				
				<tr  style="background-color:<%=classType%>">
					<td  align="center"><%=nSNo%></td>
					<td><bean:write name="row" property="project.projectid" /></td>
					<td><bean:write name="row" property="project.projectName" /></td>
					<td><bean:write name="row" property="stagename" /></td>
					<td nowrap="nowrap"><bean:write name="row" property="taskname" /></td>
					<td><bean:write name="row" property="plannedstartdate" /></td>
					<td><bean:write name="row" property="plannedenddate" /></td>
					<td><bean:write name="row" property="actualstartdate" /></td>
					<td><bean:write name="row" property="actualenddate" /></td>
					<td><bean:write name="row" property="stakeholderrole" /></td>
					<td><bean:write name="row" property="stakeholdername" /></td>
					<td><bean:write name="row" property="delayindays" /></td>
					<td><bean:write name='row' property="daysInProject" /></td>
				</tr>
			</logic:iterate>
		</logic:notEmpty>
	</table>
</logic:present>

<logic:present name="listNPDResource" scope="request">
	<logic:notEmpty name="listNPDResource">
		<logic:equal value="1" property="reportID" name="npdResourceListBean">
			<%
						response.setHeader("content-Disposition",
						"attachment;filename=NPDResourceList.xls");
			%>
			<strong>NPD Resource List</strong>
			<table width="100%" border="1" cellpadding="0" cellspacing="0">
				<tr bgcolor="#FF9255" align="Center">
					<td background="#FF9255">S.No</td>
					<td background="#FF9255">Project ID</td>
					<td background="#FF9255">Project Name</td>
					<td background="#FF9255">Role Name</td>
					<td background="#FF9255">Employee Name</td>
					<td background="#FF9255">Project Status</td>
				</tr>
				<logic:notEmpty name="listNPDResource">
					<logic:iterate id="row" name="listNPDResource" indexId="index1">
						<%
						nSNo = nSNo + 1;
						%>
						<tr align="Center">
							<td background="#FF9255"><%=nSNo%></td>
							<td><bean:write name='row' property="projectID" /></td>
							<td><bean:write name='row' property="projectName" /></td>
							<td><bean:write name='row' property="roleName" /></td>
							<td><bean:write name='row' property="empName" /></td>
							<td>
								<logic:equal value="0" name='row' property="projectStatus">CLOSED</logic:equal>
								<logic:equal value="1" name='row' property="projectStatus">OPEN</logic:equal>
								<logic:equal value="2" name='row' property="projectStatus">DRAFT</logic:equal>
							</td>
						</tr>
					</logic:iterate>
				</logic:notEmpty>
			</table>
		</logic:equal>
	</logic:notEmpty>
</logic:present>


<!-- For Funnel Report -->
<logic:present name="listFunnelReport" scope="request">
	<logic:notEmpty name="listFunnelReport">
		<logic:equal value="2" property="reportID" name="funnelBean">
			<%
						response.setHeader("content-Disposition",
						"attachment;filename=FunnelReport.xls");
			%>
			<strong>Performance Report</strong>
			<table width="100%" border="1" cellpadding="0" cellspacing="0">
				<tr bgcolor="#FF9255" align="Center">
					<td background="#FF9255">S.No</td>
					<td background="#FF9255">Project Id</td>
					<td background="#FF9255">Project Name</td>
					<td background="#FF9255">Project Status</td>
					<td background="#FF9255">Priority of launch</td>
					<td background="#FF9255">Product Brief</td>
					<td background="#FF9255">Target Market</td>
					<td background="#FF9255">Product Category</td>
					<td background="#FF9255">Airtel Potential</td>
					<td background="#FF9255">Total Market Potential</td>
					<td background="#FF9255">Capex Requirement</td>
					<td background="#FF9255">Product Manager</td>
					<td background="#FF9255">Tech Lead</td>
					<td background="#FF9255">Start Month</td>
					<td background="#FF9255">Baseline Launch Month</td>
					<td background="#FF9255">Revised Launch Date</td>
					<td background="#FF9255">Launch Quarter</td>
					<td background="#FF9255">Launch Status</td>
					<td background="#FF9255">No of Days in Project</td>
					<logic:iterate id="stagelistrow1"  name="stagelist" indexId="index1">
									<td background="#FF9255">
											<bean:write name='stagelistrow1' property="stagedesc" />
									</td>
					</logic:iterate>
					
				</tr>

				<logic:notEmpty name="listFunnelReport">
					<logic:iterate id="row" name="listFunnelReport" indexId="index1" type="com.ibm.ioes.npd.hibernate.beans.FunnelReportDto">
						<%
						nSNo = nSNo + 1;
						%>
						<tr align="Center">
							<td background="#FF9255"><%=nSNo%></td>
							<td><bean:write name='row' property="projectID" /></td>
							<td><bean:write name='row' property="projectName" /></td>
							<td><bean:write name='row' property="launchPriority" /></td>
							<td>
								<logic:equal value="0" name='row' property="projectStatus">CLOSED</logic:equal>
								<logic:equal value="1" name='row' property="projectStatus">OPEN</logic:equal>
								<logic:equal value="2" name='row' property="projectStatus">DRAFT</logic:equal>
							</td>
							<td><bean:write name='row' property="productBrief" /></td>
							<td><bean:write name='row' property="targetMarket" /></td>
							<td><bean:write name='row' property="productCategory" /></td>
							<td><bean:write name='row' property="airtelPotential" /></td>
							<td><bean:write name='row' property="totalMktPotential" /></td>
							<td><bean:write name='row' property="capexRequirement" /></td>
							<td><bean:write name='row' property="productMgr" /></td>
							<td><bean:write name='row' property="techLead" /></td>
							<td><bean:write name='row' property="startMonth" /></td>
							<td><bean:write name='row' property="baselinelaunchmonth" /></td>
							<td><bean:write name='row' property="rvsdLaunchDate" /></td>
							<td><bean:write name='row' property="launchqtr" /></td>
							<td><bean:write name='row' property="launchStatus" /></td>
							<td><bean:write name='row' property="daysInProject" /></td>
							<logic:iterate id="stagelistrow"  name="stagelist" indexId="index3" type="com.ibm.ioes.npd.hibernate.beans.TmWorkflowstage">
											<% 
												TmWorkflowstage dto = null;
												String var = null;
												for (int i=0; i<row.getProjectstageList().size();i++) 
												{
													//var = null;
													dto = new TmWorkflowstage();
													dto = (TmWorkflowstage) row.getProjectstageList().get(i);
													
													 if(dto.getStageid() == stagelistrow.getStageid())
													 {
													 	var = "X";		
													 }								  	 		
												  }
												  if(var ==null )
												  	var = "";
											  	%>
											  			
		
												<td> <%=var %> 	</td>
																							
							</logic:iterate>
							
						</tr>
					</logic:iterate>
				</logic:notEmpty>
			</table>
		</logic:equal>
	</logic:notEmpty>
</logic:present>
<!-- For Performance Report -->
<logic:present name="listPerformanceReport" scope="request">
	<logic:notEmpty name="listPerformanceReport">
		<logic:equal value="3" property="reportID"
			name="performanceReportBean">
			<%
						response.setHeader("content-Disposition",
						"attachment;filename=PerformanceReport.xls");
			%>
			<strong>Performance Report</strong>
			<table width="100%" border="1" cellpadding="0" cellspacing="0">
				<tr bgcolor="#FF9255" align="Center">
				<tr bgcolor="#FF9255" align="Center">
					<td background="#FF9255">S.No</td>
					<td background="#FF9255">Product Manager</td>
					<td background="#FF9255">Project ID</td>
					<td background="#FF9255">Project Description</td>
					<td background="#FF9255">Planned Launched Date</td>
					<td background="#FF9255">Actual Launched Date</td>
					<td background="#FF9255">Deviation(In Days)</td>
					<td background="#FF9255">No Of Days in Project</td>
					<td background="#FF9255">Project Status</td>
				</tr>
				<logic:notEmpty name="listPerformanceReport">
					<logic:iterate id="row" name="listPerformanceReport"
						indexId="index1">
						<%
						nSNo = nSNo + 1;
						%>
						<tr align="Center">
							<td background="#FF9255"><%=nSNo%></td>
							<td><bean:write name='row' property="productManager" /></td>
							<td><bean:write name='row' property="projectID" /></td>
							<td><bean:write name='row' property="projectName" /></td>
							<td><bean:write name='row' property="plndLaunchDate" /></td>
							<td><bean:write name='row' property="actLaunchDate" /></td>
							<td><bean:write name='row' property="deviation" /></td>
							<td><bean:write name='row' property="daysInProject" /></td>
							<td>
								<logic:equal value="0" name='row' property="projectStatus">CLOSED</logic:equal>
								<logic:equal value="1" name='row' property="projectStatus">OPEN</logic:equal>
								<logic:equal value="2" name='row' property="projectStatus">DRAFT</logic:equal>
							</td>
						</tr>
					</logic:iterate>
				</logic:notEmpty>
			</table>
		</logic:equal>
	</logic:notEmpty>
</logic:present>
<!-- For Detailed Report Plan-->
<logic:present name="listDetailedProjectPlan" scope="request">
	<logic:notEmpty name="listDetailedProjectPlan">
		<logic:equal value="5" property="reportID"
			name="projectDetailedStatusReportBean">
			<%
						response.setHeader("content-Disposition",
						"attachment;filename=DetailedProjectPlanReport.xls");
			%>
			<strong>Detailed Project Plan Report</strong>
			<table width="100%" border="1" cellpadding="0" cellspacing="0">
				<tr bgcolor="#FF9255" align="Center">
				<tr bgcolor="#FF9255" align="Center">
					<td align="left" nowrap>SNo.</td>
					<td align="left" nowrap>Project id</td>
					<td align="left" nowrap>Project Name</td>
					<td align="left" nowrap>Stage Name</td>
					<td align="left" nowrap>Task Name</td>
					<td align="left" nowrap>Planned Task Start Date</td>
					<td align="left" nowrap>Planned Task End Date</td>
					<td align="left" nowrap>Actual Task Start Date</td>
					<td align="left" nowrap>Actual Task End Date</td>
					<td align="left" nowrap>Project Status</td>
					<td align="left" nowrap>Role Name</td>
					<td align="left" nowrap>Delay In Days</td>
					
					<td align="left" nowrap>Document Name</td>
					<td align="left" nowrap>Document Type</td>
				<logic:notEmpty name="listDetailedProjectPlan">
					<logic:iterate id="row" name="listDetailedProjectPlan"
						indexId="index1">
						<%
						nSNo = nSNo + 1;
						%>
						<tr align="Center">
							<td align="center"><%=nSNo %></td>	
							<td nowrap="nowrap"><bean:write name="row" property="projectID" /></td>			
							<td nowrap="nowrap"><bean:write name="row" property="projectName" /></td>
							<td><bean:write name="row" property="stageName" /></td>
							<td><bean:write name="row" property="taskName" /></td>
							<td><bean:write name="row" property="taskStartDate" /></td>
							<td><bean:write name="row" property="taskEndDate" /></td>
							<td><bean:write name="row" property="actualStartDate" /></td>
							<td><bean:write name="row" property="actualEndDate" /></td>
							<logic:equal value="1" name="row" property="projectStatus">
							<td>OPEN</td>
							</logic:equal>
							<logic:equal value="0" name="row" property="projectStatus">
							<td>CLOSE</td>
							</logic:equal>
							<td><bean:write name="row" property="roleName" /></td>
							<td><bean:write name="row" property="delays" /></td>
					
							<td><bean:write name="row" property="docName" /></td>
							<td><bean:write name="row" property="docType" /></td>
						</tr>
					</logic:iterate>
				</logic:notEmpty>
			</table>
		</logic:equal>
	</logic:notEmpty>
</logic:present>
<!-- For Un Mapped Resource Report-->
<logic:present name="listEmpResource" scope="request">
	<logic:notEmpty name="listEmpResource">
		<logic:equal value="6" property="reportID"
			name="unMappedEmployeeBean">
			<%
						response.setHeader("content-Disposition",
						"attachment;filename=UnMappedEmployeeReport.xls");
			%>
			<strong>UnMapped Employee Resource Report</strong>
			<table width="100%" border="1" cellpadding="0" cellspacing="0">
				<tr bgcolor="#FF9255" align="Center">
				<tr bgcolor="#FF9255" align="Center">
					<td align="left" nowrap>SNo.</td>
					<td align="left" nowrap>Employee ID</td>
					<td align="left" nowrap>Employee Name</td>
				<logic:notEmpty name="listEmpResource">
					<logic:iterate id="row" name="listEmpResource"
						indexId="index1">
						<%
						nSNo = nSNo + 1;
						%>
						<tr align="Center">
							<td align="center"><%=nSNo %></td>	
							<td nowrap="nowrap"><bean:write name="row" property="employeeId" /></td>			
							<td nowrap="nowrap"><bean:write name="row" property="employeeName" /></td>
						</tr>
					</logic:iterate>
				</logic:notEmpty>
			</table>
		</logic:equal>
	</logic:notEmpty>
</logic:present>
<!-- For Idea Report -->
<logic:present name="reportData" scope="request">
	<logic:notEmpty name="reportData">
		<logic:equal value="7" property="reportID"
			name="newIdeaBean">
			<%
						response.setHeader("content-Disposition",
						"attachment;filename=IdeaReport.xls");
			%>
			<strong>Idea Report</strong>
			<table width="100%" border="1" cellpadding="0" cellspacing="0">
				<tr bgcolor="#FF9255" align="Center">
				<tr bgcolor="#FF9255" align="Center">
					<td align="left">S.No</td>
					<td align="left">Name</td>
					<td align="left">Oracle Id</td>
					<td align="left">Email</td>
					<td align="left">Mobile</td>
					<td align="left">Function</td>
					<td align="left">Location</td>
					<td align="left">Submitted Date</td>
					<td align="left">Brief Description</td>
					<td align="left">Benefit to Customer</td>									
					<td align="left">Do Similar Product Exist </td>
					<td align="left">Organisation</td>								
					<td align="left">Country</td>								
					<td align="left">Product Description</td>								
					<td align="left">Market Size</td>								
					<td align="left">Industry Verticals Best Suited For</td>								
					<td align="left">USP of Idea</td>	
				<logic:notEmpty name="reportData">
					<logic:iterate id="row" name="reportData"
						indexId="index1">
						<%
						nSNo = nSNo + 1;
						%>
						<tr align="Center">
							<td><%=nSNo%></td>
							<td><bean:write name='row' property="nameGenerator" /></td>
							<td><bean:write name='row' property="hrms_employeenumber" /></td>
							<td><bean:write name='row' property="mailId" /></td>
							<td><bean:write name='row' property="phoneNo" /></td>
							<td><bean:write name='row' property="function" /></td>
							<td><bean:write name='row' property="location" /></td>
							<td><bean:write name='row' property="createdDateString" /></td>
							<td><bean:write name='row' property="briefDesc" /></td>
							<td><bean:write name='row' property="benefit" /></td>
							<td><bean:write name='row' property="isSimilarProductExist" /></td>
							<td><bean:write name='row' property="organisation" /></td>
							<td><bean:write name='row' property="country" /></td>
							<td><bean:write name='row' property="prdDescription" /></td>
							<td><bean:write name='row' property="marketSize" /></td>
							<td><bean:write name='row' property="industryVertical" /></td>
							<td><bean:write name='row' property="usp" /></td>	
						</tr>
					</logic:iterate>
				</logic:notEmpty>
			</table>
		</logic:equal>
	</logic:notEmpty>
</logic:present>
</body>
</html:html>

