<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@ include file="/WEB-INF/views/header.jsp"%>

<title>지출수단 등록</title>
</head>
<body>
<table class="table table-bordered">
<input type="button" value="+ROW">
 <thead>
   <tr>
      <th>지출수단</th>
   </tr>
 </thead>
 <tbody>
   <tr>
      <td>
      	<input type="text">
      	<button type="button" class="btn btn-danger">삭제</button>
      </td>
   </tr>
 </tbody> 
</table>
<input type="button" value="등록">
</body>
</html>