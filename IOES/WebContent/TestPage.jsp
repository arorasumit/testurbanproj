<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.ibm.ioes.utilities.AppConstants"%>
<%@page import="com.ibm.ioes.utilities.ApplicationFlags"%>
<html>
<head>
<title>scheduler</title>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
</head>
<script type="text/javascript" src="js/formValidations.js"></script>

<body >
	<marquee behavior="alternate"><font size="20" color="blue">IB2B Test Page </b></marquee>
	<%--<marquee style="z-index:2;position:absolute;left:86;top:64;font-family:Cursive;font-size:14pt;color:ffcc00;height:88;"scrollamount="7" direction="down">IB2B Test Page</marquee><marquee style="z-index:2;position:absolute;left:319;top:52;font-family:Cursive;font-size:14pt;color:ffcc00;height:167;"scrollamount="6" direction="down">IB2B Test Page</marquee><marquee style="z-index:2;position:absolute;left:114;top:91;font-family:Cursive;font-size:14pt;color:ffcc00;height:285;"scrollamount="7" direction="down">IB2B Test Page</marquee><marquee style="z-index:2;position:absolute;left:49;top:101;font-family:Cursive;font-size:14pt;color:ffcc00;height:318;"scrollamount="1" direction="down">IB2B Test Page</marquee><marquee style="z-index:2;position:absolute;left:111;top:103;font-family:Cursive;font-size:14pt;color:ffcc00;height:210;"direction="down">IB2B Test Page</marquee><marquee style="z-index:2;position:absolute;left:87;top:91;font-family:Cursive;font-size:14pt;color:ffcc00;height:116;"scrollamount="4" direction="down">IB2B Test Page</marquee><marquee style="z-index:2;position:absolute;left:137;top:42;font-family:Cursive;font-size:14pt;color:ffcc00;height:25;"direction="down">IB2B Test Page</marquee><marquee style="z-index:2;position:absolute;left:287;top:79;font-family:Cursive;font-size:14pt;color:ffcc00;height:58;"direction="down">IB2B Test Page</marquee><marquee style="z-index:2;position:absolute;left:213;top:47;font-family:Cursive;font-size:14pt;color:ffcc00;height:360;"scrollamount="5" direction="down">IB2B Test Page</marquee><marquee style="z-index:2;position:absolute;left:1;top:25;font-family:Cursive;font-size:14pt;color:ffcc00;height:58;"scrollamount="5" direction="down">IB2B Test Page</marquee><marquee style="z-index:2;position:absolute;left:53;top:88;font-family:Cursive;font-size:14pt;color:ffcc00;height:452;"scrollamount="4" direction="down">IB2B Test Page</marquee><marquee style="z-index:2;position:absolute;left:216;top:45;font-family:Cursive;font-size:14pt;color:ffcc00;height:213;"scrollamount="3" direction="down">IB2B Test Page</marquee><marquee style="z-index:2;position:absolute;left:236;top:102;font-family:Cursive;font-size:14pt;color:ffcc00;height:324;"scrollamount="4" direction="down">IB2B Test Page</marquee><marquee style="z-index:2;position:absolute;left:314;top:99;font-family:Cursive;font-size:14pt;color:ffcc00;height:318;"scrollamount="4" direction="down">IB2B Test Page</marquee><marquee style="z-index:2;position:absolute;left:134;top:19;font-family:Cursive;font-size:14pt;color:ffcc00;height:30;"scrollamount="2" direction="down">IB2B Test Page</marquee><marquee style="z-index:2;position:absolute;left:68;top:44;font-family:Cursive;font-size:14pt;color:ffcc00;height:412;"scrollamount="7" direction="down">IB2B Test Page</marquee><marquee style="z-index:2;position:absolute;left:3;top:46;font-family:Cursive;font-size:14pt;color:ffcc00;height:463;"scrollamount="7" direction="down">IB2B Test Page</marquee><marquee style="z-index:2;position:absolute;left:109;top:50;font-family:Cursive;font-size:14pt;color:ffcc00;height:111;"direction="down">IB2B Test Page</marquee><marquee style="z-index:2;position:absolute;left:244;top:47;font-family:Cursive;font-size:14pt;color:ffcc00;height:234;"direction="down">IB2B Test Page</marquee><marquee style="z-index:2;position:absolute;left:171;top:106;font-family:Cursive;font-size:14pt;color:ffcc00;height:401;"scrollamount="1" direction="down">IB2B Test Page</marquee><marquee style="z-index:2;position:absolute;left:281;top:38;font-family:Cursive;font-size:14pt;color:ffcc00;height:240;"scrollamount="3" direction="down">IB2B Test Page</marquee><span style="position:absolute;top:400px"></span>--%>
</body>

</html>
