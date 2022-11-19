<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/tiles/taglib.jsp" %>

<div align="center">
<form class="goodsForm">
<input type="hidden" name="compIdx" value="${compVo.compIdx }">
<input type="hidden" name="goodsIdx" value="${goodsVo.goodsIdx }">

	<section class="ftco-section">
		<div class="container">
			<div class="row justify-content-center">
				<div class="col-md-6 text-center mb-5">
					<c:choose>
						<c:when test="${goodsVo ne null }">
							<h2 class="heading-section">상품정보 수정</h2>
						</c:when>
						<c:otherwise>
							<h2 class="heading-section">상품정보 등록</h2>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="table-wrap">
						<table class="table">
						  <thead class="thead-dark">
						    <tr>
						      <th>상품명</th>
						      <td><input type="text" name="goodsName" value="${goodsVo.goodsName }"></td>
						    </tr>
						    <tr>
						      <th>상품가격</th>
						      <td><input type="text" name="goodsPrice" value="${fn:replace(goodsVo.goodsPrice,',','')}"></td>
						    </tr>
						    <tr>
						      <th>상품재고</th>
						      <td><input type="text" name="goodsInven" value="${goodsVo.goodsInven }"></td>
						    </tr>
						    <tr>
						      <th>상품설명</th>
						      <td><textarea  name="goodsComment"  rows="10" cols="20">${goodsVo.goodsComment }</textarea></td>
						    </tr>
						    <tr>
						      <th>전시여부</th>
						      <td>
						      	<c:choose>
									<c:when test="${goodsVo ne null}">
										<c:choose>
											<c:when test="${goodsVo.goodsDis eq 'Y'}">
												<input type="checkbox" name="goodsDis" value="Y" checked="checked">
											</c:when>
											<c:otherwise>
												<input type="checkbox" name="goodsDis" value="Y" >
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										<input type="checkbox" name="goodsDis" value="Y" checked="checked">
									</c:otherwise>
								</c:choose>
						      </td>
						    </tr>
						    <tr>
						      <th>상품대표이미지</th>
						      <td><input type="file" name="goodsImg1"></td>
						    </tr>
						    <tr align="center">
								<td colspan="2">
									<c:choose>
										<c:when test="${goodsVo ne null }">
											<input type="button" value="수정" id="modi">
										</c:when>
										<c:otherwise>
											<input type="button" value="신규등록" id="regi">
										</c:otherwise>
									</c:choose>
								 &nbsp; &nbsp;	<input type="button" value="상품리스트" id="move">
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
		console.log(5)
		
		$("#regi").on("click",function(){
			
			if($(":input[name='goodsName']").val().length <= 0){
				alert("상품명을 입력해주세요.");
				$(":input[name='goodsName']").focus()
				return false;
			}
			
			var $frm = $(".goodsForm :input")
			var param = $frm.serialize()
			var $frmData = $(".goodsForm")
			var formData = new FormData($frmData[0])
			
			$.ajax({
				url:"/medi/goodsRegiAction",
				dataType :"json",
				type:"POST",
				processData: false,
	            contentType: false,
				data: formData,
				success:function(data){
					console.log(data.success)
					if(data.success=="Y"){
						alert("등록성공")
						location.href="/medi/goods"
					}else{
						alert("등록실패")
					}
				},
				error:function(){
					console.log("실패")
				}
				
			});
		});
		
		$("#modi").on("click",function(){
			
			if($(":input[name='goodsName']").val().length <= 0){
				alert("상품명을 입력해주세요.");
				$(":input[name='goodsName']").focus()
				return false;
			}
			
			var $frm = $(".goodsForm :input")
			var param = $frm.serialize()
			var $frmData = $(".goodsForm")
			var formData = new FormData($frmData[0])
			
			$.ajax({
				url:"/medi/goodsModiAction",
				dataType :"json",
				type:"POST",
				processData: false,
	            contentType: false,
				data: formData,
				success:function(data){
					console.log(data.success)
					if(data.success=="Y"){
						alert("수정성공")
						location.href="/medi/goods"
					}else{
						alert("수정실패")
					}
				},
				error:function(){
					console.log("실패")
				}
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