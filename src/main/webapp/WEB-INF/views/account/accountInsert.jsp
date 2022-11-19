<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@ include file="/WEB-INF/views/header.jsp"%>

<title>지출내역 등록</title>
</head>
<body>

<h2>지출내역 등록하기</h2>
<table class="table table-bordered">
<input type="button" value="등록">
<input type="button" value="+ROW">
 <thead>
   <tr>
      <th>DATE</th>
      <th>카테고리</th>
      <th>지출수단</th>
      <th>내용</th>
      <th>금액</th>
      <th>비고</th>
   </tr>
 </thead>
 <tbody>
   <tr>
      <td>2021-02-01</td>
      <td>
		<select class="form-control">
		  <option>식비</option>
		</select>      
      </td>
      <td>
      	<select class="form-control">
		  <option>신한카드</option>
		</select>
      </td>
      <td>홍콩반점 짬뽕</td>
      <td>6,000</td>
      <td>짬뽕 존맛 ㅜㅜ</td>
   </tr>
 </tbody> 
</table>

</body>
</html>