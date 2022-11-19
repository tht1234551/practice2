<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%   
response.setHeader("Cache-Control","no-store");   
response.setHeader("Pragma","no-cache");   
response.setDateHeader("Expires",0);   
if (request.getProtocol().equals("HTTP/1.1")) response.setHeader("Cache-Control", "no-cache");

%><%@ include file="/WEB-INF/tiles/taglib.jsp" %>


<!DOCTYPE HTML>
<html lang="ko" style="width: 100%;">
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-compatible" content="IE=Edge, chrome=1" />
<title>Tour Best ERP1.0</title>

<!-- jquery -->
<link href="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.2/themes/ui-lightness/jquery-ui.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/tob_links/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="/tob_links/js/jquery-ui.min.js"></script>

</head>
  
<body>
<!-- 타일즈에 의해 jsp 내용이 여기에 붙는 것임 : 2020.01.27 신문섭 -->
	<tiles:insertAttribute name="header" />
	<tiles:insertAttribute name="body" />
	<tiles:insertAttribute name="footer" />		 
</body>
</html>
