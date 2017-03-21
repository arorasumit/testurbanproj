<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>


<html>
<head>
<title>Migration Order-Teleport</title>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
</head>
<script type="text/javascript" src="js/formValidations.js"></script>

<script language="javascript" type="text/javascript">

var counter = 1;
function goToHome()
{
	var myForm=document.getElementById('searchForm');
	myForm.toExcel.value='false';
	myForm.action="<%=request.getContextPath()%>/defaultAction.do?method=goToHome";
	showLayer();
	myForm.submit();
}
path='<%=request.getContextPath()%>';
</script>


<body >

	<html:form action="/NewOrderAction" styleId="searchForm" method="post">
		
		
		<fieldset border="1" align="center" class="border2" >
			<legend> <b>Migration Order-Teleport</b></legend>
			<br/>
			
			<div class="scrollWithAutoScroll1" class="head"  style="height:325px;width:99% " >
			<table style="display:block;overflow:auto;" class="tab2" width="99%" border="1" align="center">
				<tr>
				    <td align="center" style="font-size:9px"><b>Order NO.</b></td>
					<td align="center" style="font-size:9px"><b>Document No.</b></td>							
					<td align="center" style="font-size:9px"><b>Spec Group Id</b></td>
					<td align="center" style="font-size:9px"><b>M6 Child_Sir_Key</b></td>
					<td align="center" style="font-size:9px"><b>M6 Parent_Sir_Key</b></td>
					<td align="center" style="font-size:9px"><b>PON</b></td>
					<td align="center" style="font-size:9px" ><b>RPON</b></td>
					<td align="center" style="font-size:9px"><b>ORDER_LINE_ID</b></td>
					<td align="center" style="font-size:9px"><b>CKT_ID</b></td>
					<td align="center" style="font-size:9px"><b>PRILOC</b></td>
					<td align="center" style="font-size:9px"><b>SECLOC</b></td>
					<td align="center" style="font-size:9px"><b>LOC_DATE</b></td>
					<td align="center" style="font-size:9px"><b>PRODUCT_NAME</b></td>
					<td align="center" style="font-size:9px"><b>TYPE_OF_ORDER</b></td>
					
					<td align="center" style="font-size:9px"><b>TB_SATELLITE_TYPE </b></td>
					<td align="center" style="font-size:9px"><b>TB_ORDBITAL_LOCATION </b></td>
					<td align="center" style="font-size:9px"><b>TB_START_FREQUENCY </b></td>
					<td align="center" style="font-size:9px"><b>TB_STOP_FREQUENCY </b></td>
					<td align="center" style="font-size:9px"><b>TB_CENTRE_FREQ </b></td>
					<td align="center" style="font-size:9px"><b>TB_BANDWIDTH </b></td>
					<td align="center" style="font-size:9px"><b>TCM_CHNG_REQSTER_NAME </b></td>
					<td align="center" style="font-size:9px"><b>TCM_CR_REQUEST_DATE </b></td>
					<td align="center" style="font-size:9px"><b>TCM_CR_REQUEST_END_TIME </b></td>
					<td align="center" style="font-size:9px"><b>TCM_CR_REQST_START_TIME </b></td>
					<td align="center" style="font-size:9px"><b>TCM_EXPECTED_DOWNTIME </b></td>
					<td align="center" style="font-size:9px"><b>TCM_ROLL_BACK_PLAN </b></td>
					<td align="center" style="font-size:9px"><b>TCM_SEVERITY </b></td>
					<td align="center" style="font-size:9px"><b>TC_AUDIO_BIT_RATE </b></td>
					<td align="center" style="font-size:9px"><b>TC_VIDEO_BIT_RATE </b></td>
					<td align="center" style="font-size:9px"><b>TC_ROUTER_PORT_ID </b></td>
					<td align="center" style="font-size:9px"><b>TC_MUX_PORT_ID </b></td>
					<td align="center" style="font-size:9px"><b>TC_AUDIO_PID </b></td>
					<td align="center" style="font-size:9px"><b>TC_VIDEO_PID </b></td>
					<td align="center" style="font-size:9px"><b>TU_INFO_RATE_UPLINK </b></td>
					<td align="center" style="font-size:9px"><b>TU_SYMBOL_RATE_UPLINK </b></td>
					<td align="center" style="font-size:9px"><b>TU_DVB_STANDARD_UPLINK </b></td>
					<td align="center" style="font-size:9px"><b>TU_FEC_UPLINK </b></td>
					<td align="center" style="font-size:9px"><b>TU_MODULATION_UPLINK </b></td>
					<td align="center" style="font-size:9px"><b>TU_TYPE_OF_CARRIER </b></td>
					<td align="center" style="font-size:9px"><b>TPR_CUSTOMER_TYPE </b></td>
					<td align="center" style="font-size:9px"><b>TPR_TYPE_OF_ORDER </b></td>
					<td align="center" style="font-size:9px"><b>TPR_TELEPORT_LOCATION </b></td>
					<td align="center" style="font-size:9px"><b>TPR_INPUT_FORMAT </b></td>
					<td align="center" style="font-size:9px"><b>TPR_PLAYOUT_SERVICES </b></td>
					<td align="center" style="font-size:9px"><b>TPR_TYPE_OF_SERVICE </b></td>
					<td align="center" style="font-size:9px"><b>TPR_INFO_RATE </b></td>
					<td align="center" style="font-size:9px"><b>TPR_SYMBOL_RATE </b></td>
					<td align="center" style="font-size:9px"><b>TPR_DVB_STANDARD </b></td>
					<td align="center" style="font-size:9px"><b>TPR_FEC </b></td>
					<td align="center" style="font-size:9px"><b>TPR_COMPRESSION_TECH </b></td>
					<td align="center" style="font-size:9px"><b>TPR_MODULATION </b></td>
					<td align="center" style="font-size:9px"><b>TPR_CUST_MULTICAST_IP </b></td>
					<td align="center" style="font-size:9px"><b>TPR_CUST_WAN_IP </b></td>
					<td align="center" style="font-size:9px"><b>TPR_AUDIO_FORMAT </b></td>
					<td align="center" style="font-size:9px"><b>TPR_NO_OF_AUDIOS </b></td>
					<td align="center" style="font-size:9px"><b>TPR_TYPE_OF_AUDIOS</b></td> 
					<td align="center" style="font-size:9px"><b>TPR_VIDEO_FORMAT </b></td>
					<td align="center" style="font-size:9px"><b>TC_COMPRESSION_TECH </b></td>
					<td align="center" style="font-size:9px"><b>TCM_COMMUNICATION </b></td>
					
					<td align="center" style="font-size:9px"><b>M6SHORTCODE</b></td>
					<td align="center" style="font-size:9px"><b>CRM_ACCT_NO</b></td>
					<td align="center" style="font-size:9px"><b>ORDER_STATUS</b></td>
					<td align="center" style="font-size:9px"><b>ORDER_PROVISION_STATUS</b></td>
					<td align="center" style="font-size:9px"><b>ORDER_LOGIN_DATE</b></td>

				</tr>
				
					<logic:notEmpty  name="listMigrationOrders" scope="request"> 					
						<logic:iterate id="row" name="listMigrationOrders" indexId="recordId">
							<%
								String classType=null;
								if(recordId.intValue() % 2 == 0)
								{
								classType="lightBg";
								}
								else
								{
								classType="normal";				
								}	
							%>				
							<tr class="<%=classType%>">
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="orderNo"/></td>
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="documentNo"/></td>
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="specGrpId"/></td>
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="m6ChildSirKey"/></td>
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="m6ParentSirKey"/></td>
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="pon"/></td>
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="rpon"/></td>
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="orderLineId"/></td>
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="ckt_id"/></td>
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="priLoc"/></td>
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="secLoc"/></td>
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="locDate"/></td>
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="productName"/></td>
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="typeOfOrder"/></td>
					
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="tb_satellite_type"/></td>  
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="tb_ordbital_location"/></td>  
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="tb_start_frequency"/></td>  
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="tb_stop_frequency"/></td>  
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="tb_centre_freq"/></td>  
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="tb_bandwidth"/></td>  
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="tcm_chng_reqster_name"/></td>  
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="tcm_cr_request_date"/></td>  
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="tcm_cr_request_end_time"/></td>  
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="tcm_cr_reqst_start_time"/></td>  
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="tcm_expected_downtime"/></td>  
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="tcm_roll_back_plan"/></td>  
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="tcm_severity"/></td>  
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="tc_audio_bit_rate"/></td>  
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="tc_video_bit_rate"/></td>  
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="tc_router_port_id"/></td>  
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="tc_mux_port_id"/></td>  
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="tc_audio_pid"/></td>  
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="tc_video_pid"/></td>  
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="tu_info_rate_uplink"/></td>  
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="tu_symbol_rate_uplink"/></td>  
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="tu_dvb_standard_uplink"/></td>  
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="tu_fec_uplink"/></td>  
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="tu_modulation_uplink"/></td>  
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="tu_type_of_carrier"/></td>  
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="tpr_customer_type"/></td>  
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="tpr_type_of_order"/></td>  
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="tpr_teleport_location"/></td>  
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="tpr_input_format"/></td>  
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="tpr_playout_services"/></td>  
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="tpr_type_of_service"/></td>  
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="tpr_info_rate"/></td>  
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="tpr_symbol_rate"/></td>  
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="tpr_dvb_standard"/></td>  
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="tpr_fec"/></td>  
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="tpr_compression_tech"/></td>  
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="tpr_modulation"/></td>  
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="tpr_cust_multicast_ip"/></td>  
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="tpr_cust_wan_ip"/></td>  
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="tpr_audio_format"/></td> 
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="tpr_no_of_audios"/></td> 
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="tpr_type_of_audios"/></td> 
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="tpr_video_format"/></td> 
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="tc_compression_tech"/></td> 
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="tcm_communication"/></td> 
				
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="m6shortcode"/></td> 
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="crm_acct_no"/></td> 
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="order_status"/></td> 
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="order_provision_status"/></td> 
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="order_login_date"/></td>
											</tr>
						</logic:iterate>		
					</logic:notEmpty>
					<logic:empty  name="listMigrationOrders">
					   <tr align="center" >
						 <td colspan="17" align="center"><B><font color="red">No Records Found</font></B></td>
					   </tr>
					</logic:empty>	
	
			</table>
			</div>
		</fieldset>
	</html:form>
</body>
</html>
