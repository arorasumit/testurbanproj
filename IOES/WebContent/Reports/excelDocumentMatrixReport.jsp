
<%--[001] Gunjan Singla  Document Matrix Report--%>
<%--[003] CSR::20150410-R1-021216 Resource: Raveendra Kumar Date:29 April-2015 Rearrangement column--%>
<%--[004] CSR::20160301-XX-022139 Resource: Priya Gupta Date: 18 May 2016 --%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<html:html>
<head>
<%
response.setContentType("application/vnd.ms-excel");
%>
</head>
<body>
<logic:present name="DocumentMatrixReportExcel" scope="request">
<% response.setHeader("content-Disposition","attachment;filename=DocumentMatrixReport.xls"); %>
<table width="100%" border="1" cellpadding="0" cellspacing="0">
<tr><td colspan="25" align="left" style="font-size:10px"><strong>Document Matrix Report</strong></td></tr>
<tr align="Center"><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Account Number</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Account Name</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Type</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Change Order Subtype</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Account Manager</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Sales Co-ordinator</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Region</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>customer segment</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Number</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Logical SI No</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Service Number</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Stage</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>COPC Approver</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>COPC Approver ID</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>COPC Approver Remarks </b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>COPC Approval date </b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Any deviation</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Non-India touching link</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>CAF</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>PO</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Customer Agreement</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>e-PCN\NFA</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Feasibility</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>ISP</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>LOU</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Network Diagram</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>OFS</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>PI/Third Party Related</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>PCD</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>RFP </b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>SOW</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>T&C</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Others</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Previous Month Trigger Approval</b></td>
</tr>
<logic:empty name="DocumentMatrixReportExcel">
<tr><td colspan="25" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td></tr>
</logic:empty>
<logic:notEmpty  name="DocumentMatrixReportExcel" scope="request"> 					
<logic:iterate id="row" name="DocumentMatrixReportExcel" indexId="recordId">
<tr>
<td><bean:write  name="row" property="accountNo"/></td><td><bean:write  name="row" property="accountName"/></td><td><bean:write  name="row" property="ordType"/></td><td><bean:write  name="row" property="ordSubType"/></td><td><bean:write  name="row" property="accntMgr"/></td><td><bean:write  name="row" property="salesCo"/></td><td><bean:write  name="row" property="region"/></td><td><bean:write  name="row" property="custSegment"/></td><td><bean:write  name="row" property="orderNo"/></td><td><bean:write  name="row" property="lsiNo"/></td><td><bean:write  name="row" property="serviceNo"/></td><td><bean:write  name="row" property="stage"/></td><td><bean:write  name="row" property="copcApprovr"/></td><td><bean:write  name="row" property="copcApprovrID"/></td><td><bean:write  name="row" property="copcApprovrRemarks"/></td><td><bean:write  name="row" property="copcAprDate"/></td>
<td><bean:write name="row" property="docAnyDeviation"/></td><td><bean:write name="row" property="docNonIndiaTouchLink"/></td><td><bean:write name="row" property="docCAF"/></td><td><bean:write name="row" property="docPO"/></td><td><bean:write name="row" property="docCustAgreemnt"/></td><td><bean:write name="row" property="docPCN"/></td><td><bean:write name="row" property="docFeasibility"/></td><td><bean:write name="row" property="docISP"/><td><bean:write name="row" property="docLOU"/></td></td><td><bean:write name="row" property="docNtwrk"/></td><td><bean:write name="row" property="docOFS"/></td><td><bean:write name="row" property="docThirdParty"/></td><td><bean:write name="row" property="docPCD"/></td><td><bean:write name="row" property="docRFP"/></td><td><bean:write name="row" property="docSOW"/></td><td><bean:write name="row" property="docTnC"/></td><td><bean:write name="row" property="docOther"/></td><td><bean:write name="row" property="docPMTA"/></td>
</tr>
</logic:iterate>
</logic:notEmpty>
</table>
</logic:present>
</body>
</html:html>
