<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/tiles/taglib.jsp" %>

<div align="center">
<form class="dealForm">
<input type="hidden" name="compIdx" value="${goodsVo.compIdx }">
<input type="hidden" name="goodsIdx" value="${goodsVo.goodsIdx }">
	<section class="ftco-section">
		<div class="container">
			<div class="row justify-content-center">
				<div class="col-md-6 text-center mb-5">
					<c:choose>
						<c:when test="${dealVo ne null }">
							<h2 class="heading-section">주문정보 수정</h2>
						</c:when>
						<c:otherwise>
							<h2 class="heading-section">주문정보 등록</h2>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="table-wrap">
						<table class="table">
							<colgroup>
						  		<col width="30%"/>
						  		<col width="70%"/>
						  	</colgroup>
						  <thead class="thead-dark">
						    <tr>
						      <th>상품명</th>
						      <td>
						      	${goodsVo.goodsName }
						      </td>
						    </tr>
						    <tr>
						      <th>상품가격</th>
						      <td>
						      	${goodsVo.goodsPrice }
						      </td>
						    </tr>
						    <tr>
						      <th>상품재고</th>
						      <td>
						      	${goodsVo.goodsInven}
						      </td>
						    </tr>
						    <tr>
						      <th>상품설명</th>
						      <td>${goodsVo.goodsComment }</td>
						    </tr>
						    <tr align="center">
						      <td colspan="2">
						      	<span  style="cursor:pointer" id="plus">➕</span>
						      	&nbsp;<input type="text" style="text-align:right;" name = "dealGoodCnt" size="1" readonly="readonly"> &nbsp;
						      	<span  style="cursor:pointer" id="minus">➖</span>
						      </td>
						    </tr>
						    <tr align="center">
								<td colspan="2">
									<c:choose>
										<c:when test="${DealVo ne null }">
											<input type="button" value="수정" id="modi">
										</c:when>
										<c:otherwise>
											<input type="button" value="주문등록" id="regi">
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
			
			if($(":input[name='dealGoodCnt']").val().length <= 0){
				alert("주문갯수를 입력해주세요.");
				return false;
			}
			
			var $frm = $(".dealForm :input")
			var param = $frm.serialize()
			
			$.ajax({
				url:"/medi/dealRegiAction",
				dataType :"json",
				type:"POST",
				data: param,
				success:function(data){
					console.log(data.success)
					if(data.success=="Y"){
						alert("등록성공")
						location.href="/medi/dealList"
						
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
			
			if($(":input[name='dealGoodCnt']").val().length <= 0){
				alert("주문갯수를 입력해주세요.");
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
		
		var dealCnt = 0;
		
		$("#plus").on("click",function(){
			
			var $dealGoodCnt = $("input[name='dealGoodCnt']")
			
			if($dealGoodCnt.legnth <= 0){
				$dealGoodCnt.val(1);
				dealCnt = 1;
			}else{
				dealCnt++;
				$dealGoodCnt.val(dealCnt);
			}
			
			var invenCnt = parseInt("${goodsVo.goodsInven}");
			
			if($dealGoodCnt.val() > invenCnt){
				alert("재고수량을 초과하였습니다.")
				dealCnt--;
				$dealGoodCnt.val(dealCnt)
				return false;
			}
			
		})
		
		$("#minus").on("click",function(){
			
			var $dealGoodCnt = $("input[name='dealGoodCnt']")
			
			if($dealGoodCnt.val().length <= 0){
				$dealGoodCnt.val(1);
			}else if($dealGoodCnt.val() <= 1){
				$dealGoodCnt.val(1);
				dealCnt = 1;
			}else{
				$dealGoodCnt.val(dealCnt-1);
				dealCnt--;
			}
			
		})
	});

</script>