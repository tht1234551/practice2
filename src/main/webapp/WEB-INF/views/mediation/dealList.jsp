<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/tiles/taglib.jsp"%>

<div align="center">
	<form class="goodsList">
		<section class="ftco-section">
			<div class="container">
				<div class="row justify-content-center">
					<div class="col-md-6 text-center mb-5">
						<h2 class="heading-section">주문 목록</h2>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<div class="table-wrap">
							<table class="table">
						  <thead class="thead-dark">
						    <tr align="right">
						      <td colspan="5">
						   		<c:choose>
									<c:when test="${compVo ne null }">
									</c:when>
									<c:otherwise>
										<input type="button" value="상품리스트" id="move">
									</c:otherwise>
								</c:choose>   
						      </td>
						    </tr>
						    <tr>
						      <th>번호</th>
						      <th>상품명</th>
						      <th>상품가격</th>
					      	  <th>주문수</th>
					      	  <th>진행상태</th>
						    </tr>
						  </thead>
						  <tbody>
						    <c:forEach items="${dealList}" var="list">
								<tr>
							      <th scope="row">${list.rowNum }</th>
							      <td >${list.goodsVo.goodsName }</td>
							      <td>${list.goodsVo.goodsPrice }</td>
							      <td>${list.dealGoodVo.dealGoodCnt }</td>
							      <c:choose>
									<c:when test="${list.dealStatus eq 'S' }">
										<td>신규주문</td>
									</c:when>
									<c:when test="${list.dealStatus eq 'R' }">
										<td>접수완료</td>
									</c:when>
									<c:otherwise>
										<td>처리완료</td>
									</c:otherwise>
								</c:choose> 
							    </tr>
							</c:forEach>
						  </tbody>
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
		console.log(4)
		$("#compInfo").on("click",function(){
			
			location.href="/medi/comp"
		});

		$("#goodsRegi").on("click",function(){
			
			location.href="/medi/goodsRegi"
		});
		
		$(".idx").on("click",function(){
			var idx = $.trim($(this).attr("id"));
			console.log(idx)
			<c:choose>
				<c:when test="${compVo ne null }">
					location.href = "/medi/goodsDetail?goodsIdx="+idx
				</c:when>
				<c:otherwise>
					location.href = "/medi/goodsDeal?goodsIdx="+idx
				</c:otherwise>
			</c:choose> 
		})
		
		$("#move").on("click",function(){
			
			location.href="/medi/goods"
			
		});
		
		$("#logout").on("click",function(){
			
			location.href="/medi/logout"
		});
	});

</script>