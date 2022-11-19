<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/tiles/taglib.jsp" %>

<div align="center">
<form class="compForm" action="/excel/excelUpload" enctype="multipart/form-data" method="post">
	<input type="button" value="엑셀생성" name="excelButton" id = "excelButton">
	<input type="file" name = "excelFile">
	<input type="submit" value="엑셀 업로드">
</form>
</div>

<script>

$(document).ready(function(){
	
	
	$("#excelButton").on("click",function(){
		
		location.href = "/excel/excelDown";
		
	})
	
	
})


</script>