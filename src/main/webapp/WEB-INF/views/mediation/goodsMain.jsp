<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/tiles/taglib.jsp"%>

<div align="center">
	<form class="goodsList">
		<section class="ftco-section">
			<div class="container">
				<div class="row justify-content-center">
					<div class="col-md-6 text-center mb-5">
						<h2 class="heading-section">상품 목록</h2>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<div class="table-wrap">
							<table class="table">
						  <thead class="thead-dark">
						    <tr align="right">
						      <td colspan="5">
						      	<input type="button" value="주문정보" id="dealList" /> &nbsp; &nbsp;
						   		<c:choose>
									<c:when test="${compVo ne null }">
										<input type="button" value="상품등록" id="goodsRegi" /> &nbsp; &nbsp;
										<input type="button" value="회사정보" id="compInfo" />
									</c:when>
									<c:otherwise>
										<input type="button" value="로그아웃" id="logout" />
									</c:otherwise>
								</c:choose>   
						      </td>
						    </tr>
						    <tr align="right">
						      <td colspan="5">
						      	
						      </td>
						    </tr>
						    <tr>
						      <th>번호</th>
						      <th>상품명</th>
						      <th>상품가격</th>
					      	  <th>상품재고</th>
						      <c:if test="${compVo ne null }">
						      	 <th>전시여부</th>
						      </c:if> 
						    </tr>
						  </thead>
						  <tbody>
						    <c:forEach items="${goodsList}" var="list">
								<tr class="alert idx" role="alert"  style="cursor:pointer" id = "${list.goodsIdx}" >
							      <th scope="row">${list.rowNum }</th>
							      <td >${list.goodsName }</td>
							      <td>${list.goodsPrice }</td>
							      <td>${list.goodsInven }</td>
							      <c:if test="${compVo ne null }">
							      	 <td>${list.goodsDis }</td>
							      </c:if> 
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
		
		$("#dealList").on("click",function(){
			
			location.href="/medi/dealList"
		});
		
		$("#logout").on("click",function(){
			
			location.href="/medi/logout"
		});
	});

</script>