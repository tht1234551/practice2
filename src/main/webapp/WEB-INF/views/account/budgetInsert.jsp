<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@ include file="/WEB-INF/views/header.jsp"%>

<title>예산 등록</title>
</head>
<body onload="test()" >
<form id="frm_budget" method="post" action="budgetInsert">
<h2>예산등록하기</h2>
<select name="budYear">
<option value=2021>2021년</option>
</select>
<select name="budMonth">
<option value=1>1월</option>
<option value=2>2월</option>
<option value=3>3월</option>
<option value=4>4월</option>
<option value=5>5월</option>
<option value=6>6월</option>
<option value=7>7월</option>
<option value=8>8월</option>
<option value=9>9월</option>
<option value=10>10월</option>
<option value=11>11월</option>
<option value=12>12월</option>
</select>
<table class="table table-bordered">
 <thead>
   <tr>
      <th>카테고리</th>
      <th>예산</th>
      <th>비고</th>
   </tr>
 </thead>
 <tbody>
 	
 	<c:forEach var="cate" varStatus="cateVo" items="${cateList}"> 
 		
   	<tr>
   	  <input type="hidden" name="budgetVoList[${cateVo.index}].cateCd" value="${cate.cateCd}"/>
      <td>${cate.cateNm}</td>
      <td><input type="text" name="budgetVoList[${cateVo.index}].budPrice" value="0"/></td>	
      <td><input type="text" name="budgetVoList[${cateVo.index}].budRemarks" value=""/></td>	
    </tr>	
    </c:forEach> 
 </tbody> 
</table>
<input type="submit" value="등록">
<input type="button" id="btn_close" value="닫기">
</form>

</body>
<script>
function test() {
    window.onbeforeunload = function (e) {
    	return 0;
    };
}

$(document).ready(function(){
	$('#btn_close').on("click", function(){		
		opener.location.reload();
		window.close();

	})
	
});

</script>
</html>