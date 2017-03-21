<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>


<html>
<head>
<title>Migration Order-DC Colo</title>
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
			<legend> <b>Migration Order-DC Colo</b></legend>
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
					<td align="center" style="font-size:9px"><b>UTP</b></td>
					<td align="center" style="font-size:9px"><b>OPT</b></td>
					<td align="center" style="font-size:9px"><b>COAXIAL</b></td>
					<td align="center" style="font-size:9px"><b>ROOFSPACE</b></td>
					<td align="center" style="font-size:9px"><b>WORKSTNSEATS</b></td>
					<td align="center" style="font-size:9px"><b>CABINHALLMTNGROOM</b></td>
					<td align="center" style="font-size:9px"><b>LOCKBLSTOREAREA</b></td>
					<td align="center" style="font-size:9px"><b>STANDARDRACK</b></td>
					<td align="center" style="font-size:9px"><b>RACKSREQD</b></td>
					<td align="center" style="font-size:9px"><b>NONSTDDIM</b></td>
					<td align="center" style="font-size:9px"><b>NONSTDQUANTITY</b></td>
					<td align="center" style="font-size:9px"><b>PARTIALRACK</b></td>
					<td align="center" style="font-size:9px"><b>PARTIALRACKID</b></td>
					<td align="center" style="font-size:9px"><b>DC_FLOOR_SPACE</b></td>
					<td align="center" style="font-size:9px"><b>PHAC_MTR_PERKWH</b></td>
					<td align="center" style="font-size:9px"><b>PHAC_RATE_KVA_ANN</b></td>
					<td align="center" style="font-size:9px"><b>PHAC_RLOAD_KVA_ANN</b></td>
					<td align="center" style="font-size:9px"><b>SNGLEPHAC_MTR_KWHR</b></td>
					<td align="center" style="font-size:9px"><b>SNGLPHAC_RATE_KVA_AN</b></td>
					<td align="center" style="font-size:9px"><b>SNGLPHAC_RLOAD_KVA_A</b></td>
					<td align="center" style="font-size:9px"><b>MIN48V_DC_MTR</b></td>
					<td align="center" style="font-size:9px"><b>MIN48V_DC_RATE</b></td>
					<td align="center" style="font-size:9px"><b>MIN48V_D_RLD_KVA_ANN</b></td>
					<td align="center" style="font-size:9px"><b>RACK_ID_NO</b></td>
					<td align="center" style="font-size:9px"><b>DCF_REMARKS</b></td>
					<td align="center" style="font-size:9px"><b>CAMERA</b></td>
					<td align="center" style="font-size:9px"><b>ACCESS_CONTROL_SPEC</b></td>
					<td align="center" style="font-size:9px"><b>CUSTOMIZATION</b></td>
					<td align="center" style="font-size:9px"><b>CAMERA_SPECIFICATION</b></td>
					<td align="center" style="font-size:9px"><b>ACCESS_CONTROL</b></td>
					<td align="center" style="font-size:9px"><b>BAS_SMART_HAND_SRV_IT</b></td>
					<td align="center" style="font-size:9px"><b>PREPAID_PACK_MH_MONT1</b></td>
					<td align="center" style="font-size:9px"><b>POSTPAIDPACK_MH_MONT1</b></td>
					<td align="center" style="font-size:9px"><b>ADV_SMART_HAND_SRV_NT1</b></td>
					<td align="center" style="font-size:9px"><b>PREPAID_PACK_MH_MONT2</b></td>
					<td align="center" style="font-size:9px"><b>POSTPAIDPACK_MH_MONT2</b></td>
					<td align="center" style="font-size:9px"><b>MEDIA_HNDL_VAULT_SERV</b></td>
					<td align="center" style="font-size:9px"><b>VENDOR_MANAGEMENT</b></td>
					<td align="center" style="font-size:9px"><b>SURVEILLANCE_CAMERA</b></td>
					<td align="center" style="font-size:9px"><b>SURVEILLANCE_CAMERA_SPEC</b></td>
					<td align="center" style="font-size:9px"><b>DC_MPLS_CIRCUIT_ID</b></td>
					<td align="center" style="font-size:9px"><b>ANYOTHERPRODUCTSRVC</b></td>
					<td align="center" style="font-size:9px"><b>DC_ISP</b></td>
					<td align="center" style="font-size:9px"><b>DC_ISP_CIRCUIT_ID</b></td>
					<td align="center" style="font-size:9px"><b>DC_P_TO_P_LINKS</b></td>
					<td align="center" style="font-size:9px"><b>FEASIBILITY_ID</b></td>
					<td align="center" style="font-size:9px"><b>DC_MPLS</b></td>
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
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="utp"/></td>
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="opt"/></td>
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="coaxial"/></td>
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="roofSpace"/></td>
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="workStnSeat"/></td>
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="cabinHallMtngRoom"/></td>
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="lockblstorearea"/></td>
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="standardrack"/></td>
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="racksreqd"/></td> 
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="nonstddim"/></td> 
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="nonstdquantity"/></td> 
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="partialrack"/></td> 
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="partialrackid"/></td> 
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="dc_floor_space"/></td> 
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="phac_mtr_perkwh"/></td> 
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="phac_rate_kva_ann"/></td> 
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="phac_rload_kva_ann"/></td>
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="snglephac_mtr_kwhr"/></td>
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="snglphac_rate_kva_an"/></td>
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="snglphac_rload_kva_a"/></td>
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="min48v_dc_mtr"/></td>
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="min48v_dc_rate"/></td>
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="min48v_d_rld_kva_ann"/></td>
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="rack_id_no"/></td>
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="dcf_remarks"/></td>
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="camera"/></td> 
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="access_control_spec"/></td> 
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="customization"/></td> 
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="camera_specification"/></td> 
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="access_control"/></td> 
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="bas_smart_hand_srv_it"/></td> 
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="prepaid_pack_mh_mont1"/></td> 
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="postpaidpack_mh_mont1"/></td> 
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="adv_smart_hand_srv_nt1"/></td> 
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="prepaid_pack_mh_mont2"/></td> 
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="postpaidpack_mh_mont2"/></td> 
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="media_hndl_vault_serv"/></td> 
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="vendor_management"/></td> 
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="surveillance_camera"/></td> 
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="surveillance_camera_spec"/></td> 
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="dc_mpls_circuit_id"/></td> 
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="anyotherproductsrvc"/></td> 
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="dc_isp"/></td>
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="dc_isp_circuit_id"/></td>
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="dc_p_to_p_links"/></td>
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="feasibility_id"/></td> 
					<td align="left" style="font-size:9px" ><bean:write  name="row" property="dc_mpls"/></td> 
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
