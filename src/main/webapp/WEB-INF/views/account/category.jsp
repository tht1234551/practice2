<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@ include file="/WEB-INF/views/header.jsp"%>
 
<script>
$(document).ready(function(){
	
	
	$('.btn_addRow').on("click", function(){
		$('#addCategory > tbody:last').append("<tr><td><input type='text' class='category' name='cateNm'></td></tr>");
	});
	
	
	$(document).on('click', '.btn_disable', function(){
		var cateCd = $(this).parent().find($('input[name=cateCd]')).val();
		var cateYn = $(this).parent().find($('input[name=cateYn]')).val();
		var clickedBtn = $(this);
		
		if(cateYn == 'Y'){
			cateYn = 'N';
		}else{
			cateYn = 'Y';
		}
				
		$.ajax({
				type:"POST",
				url:"updateCateYn",
				cache: false,
				async:false,
				data: {
					cateCd : cateCd,	
					cateYn : cateYn
				},
				success : function(data){
					if(cateYn == 'N'){
						clickedBtn.parent().find($('input[name=result_cateNm]')).attr('disabled', true);
						clickedBtn.parent().find($('input[name=cateYn]')).val('N');
						clickedBtn.val('활성화');
					} else{
						clickedBtn.parent().find($('input[name=result_cateNm]')).attr("disabled", false);
						clickedBtn.parent().find($('input[name=cateYn]')).val('Y');
						clickedBtn.val('비활성화');
					}
				}
			})							
	});
		
});


</script>
<title>카테고리 등록</title>
</head>
<body>
<form id="frm_cate" method="post" action="categoryAdd">
<table class="table table-bordered" id="addCategory">
 <thead>
   <tr>
      <th>
      	카테고리
      	<input type="button" class="btn_addRow" value="+">
      </th>     
   </tr>
 </thead>
 <tbody>
      	<c:forEach items="${list}" var="item">
		<tr>
			<td>
				<input type="hidden" name="cateYn" value="${item.cateYn}">
				<input type="hidden" name="cateCd" value='<c:out value="${item.cateCd}"/>'>			
				<c:choose>
					<c:when test="${item.cateYn == 'Y'}">
						<input type="text" class="category" name="result_cateNm" value="${item.cateNm}" readonly>	
						<input type="button" class="btn_disable" value="비활성화">
					</c:when>
					<c:otherwise>
						<input type="text" class="category" name="result_cateNm" value="${item.cateNm}" disabled>	
						<input type="button" class="btn_disable" value="활성화">
					</c:otherwise>
				</c:choose>				
			</td>
		</tr>
		</c:forEach>
		<tr>
			<td>
				<input type="text" class="category" name="cateNm">
			</td>
		</tr>
 </tbody> 
</table>
<button type="submit">등록</button>
</form>
</body>
</html>

<!--       	<button type="button" class="btn btn-danger">삭제</button> -->