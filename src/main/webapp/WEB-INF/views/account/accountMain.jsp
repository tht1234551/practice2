<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@ include file="/WEB-INF/views/header.jsp"%>
<%@ include file="/js/calendar.jsp"%>
<%-- <%@ include file="/js/common.jsp"%> --%>
<%@ include file="/css/calendar.css"%>

<script>

var openPop = {
		
		small : function(url, popNm){
			var option="width = 300, height = 300, top = 100, left = 200, location = no";
			window.open(url, popNm, option);
		},
		
		medium : function(url, popNm){
			var option="width = 700, height = 500, top = 100, left = 200, location = no";
			window.open(url, popNm, option);
		},
		
		large : function(url, popNm){
			var option="width = 1000, height = 1000, top = 100, left = 200, location = no";
			window.open(url, popNm, option);
		}
	};


$(document).ready(function(){
	
	$('.btn_budInsert').on('click', function(){
		openPop.medium('budgetList', 'pop');
	});
	
	
});




</script>




<title>Main</title>
</head>
<body>
<h2>지출관리시스템</h2>

  <div id='script-warning'>
    <code>ics/feed.ics</code> must be servable
  </div>

  <div id='loading'>loading...</div>

  <div id='calendar'></div>
  
<h2>예산</h2>
<table class="table table-bordered">
<input type="button" class="btn_budInsert" value="예산 등록">
<input type="button" value="예산 수정" disabled>
 <thead>
   <tr>
      <th>
      	<a href="javascript:void(0);" onclick="openPop.small('categoryList', 'pop')">카테고리</a>
      </th>
      <th>예산</th>
      <th>사용 금액</th>
      <th>비고</th>
   </tr>
 </thead>
 <tbody>
   <tr>
      <td>식비</td>
      <td>200,000</td>
      <td>250,000</td>
      <td>비고입니다.</td>
   </tr>
 </tbody> 

</table>
<h2>결제수단</h2>
<table class="table table-bordered">
<input type="button" value="결제 수단 등록">
<input type="button" value="수정">
 <thead>
   <tr>
      <th>결제수단</th>
      <th>사용 금액</th>
   </tr>
 </thead>
 <tbody>
   <tr>
      <td>신한카드</td>
      <td>300,000</td>
   </tr>
 </tbody> 

</table>

  
</body>
</html>