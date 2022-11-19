<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/tiles/taglib.jsp" %>

<div align="center">
<form class="compForm">
<input type="hidden" name="compIdx" value="${compVo.compIdx }">
	<section class="ftco-section">
		<div class="container">
			<div class="row justify-content-center">
				<div class="col-md-6 text-center mb-5">
					<h2 class="heading-section">회사정보</h2>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="table-wrap">
						<table class="table">
						  <thead class="thead-dark">
						    <tr>
						      <th>회사명</th>
						      <td><input type="text" name="compName" value="${compVo.compName }"></td>
						    </tr>
						    <tr>
						      <th>대표자명</th>
						      <td><input type="text" name="compCeo" value="${compVo.compCeo }"></td>
						    </tr>
						    <tr>
						      <th>사업자번호</th>
						      <td><input type="text" name="compNumber" value="${compVo.compNumber }"></td>
						    </tr>
						    <tr>
						      <th>전화번호</th>
						      <td><input type="text" name="compPhone" value="${compVo.compPhone }"></td>
						    </tr>
						    <tr>
						      <th>우편번호</th>
						      <td><input type="text" name="compPost" value="${compVo.compPost }"></td>
						    </tr>
						    <tr>
						      <th>주소</th>
						      <td><input type="text" name="compAddr" value="${compVo.compAddr }"></td>
						    </tr>
						    <tr>
						      <th>홈페이지</th>
						      <td>http://www.<input type="text" name="compUrl" value="${compVo.compUrl }"></td>
						    </tr>
						    <tr>
						      <th>회사소개</th>
						      <td><textarea rows="10" cols="20" name = "compComment">${compVo.compComment }</textarea></td>
						    </tr>
						    <tr  align="center">
								<td colspan="2">
									<c:choose>
										<c:when test="${compVo ne null }">
											<input type="button" value="수정" id="modi"> &nbsp; &nbsp;
										</c:when>
										<c:otherwise>
											<input type="button" value="신규등록" id="regi"> &nbsp; &nbsp;
										</c:otherwise>
									</c:choose>
									<input type="button" value="상품리스트" id="move"> &nbsp; &nbsp;
									<input type="button" value="로그아웃" id="logout">
								</td>
							</tr>
						  </thead>
						</table>
					</div>
				</div>
			</div>	
		</div>
	</section>
</form>
</div>
<script>
	$(document).ready(function(){
		console.log(3)
		
		
		$("#regi").on("click",function(){
			
			if($(":input[name='compName']").val().length <= 0){
				alert("회사명을 입력해주세요.");
				$(":input[name='compName']").focus()
				return false;
			}
			
			var $frm = $(".compForm :input")
			var param = $frm.serialize()
			
			$.ajax({
				url:"/medi/compRegiAction",
				dataType :"json",
				type:"POST",
				data:param,
				success:function(data){
					console.log(data.success)
					if(data.success=="Y"){
						alert("등록성공")
						location.href = "/medi/comp"
					}else{
						alert("등록실패")
					}	
				},
				error:function(){
					console.log("실패")
				},
				
			});
		});
		
		$("#modi").on("click",function(){
			
			if($(":input[name='compName']").val().length <= 0){
				alert("회사명을 입력해주세요.");
				$(":input[name='compName']").focus()
				return false;
			}
			
			var $frm = $(".compForm :input")
			var param = $frm.serialize()
			
			$.ajax({
				url:"/medi/compModiAction",
				dataType :"json",
				type:"POST",
				data:param,
				success:function(data){
					console.log(data.success)
					if(data.success=="Y"){
						alert("수정성공")
						location.href = "/medi/comp"
					}else{
						alert("수정실패")
					}	
				},
				error:function(){
					console.log("실패")
				},
				
			});
		});
		
		$("#move").on("click",function(){
			
			location.href="/medi/goods"
			
		});
		
		$("#logout").on("click",function(){
				
			location.href="/medi/logout"
		});
	});

</script>