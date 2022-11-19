<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/tiles/taglib.jsp" %>

<form class="loginForm">
	<section class="ftco-section">
		<div class="container">
			<div class="row justify-content-center">
				<div class="col-md-6 text-center mb-5">
					<h2 class="heading-section">로그인</h2>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="table-wrap">
						<table class="table">
						  <thead class="thead-dark">
						    <tr>
						      <th>아이디</th>
						      <td><input type="text" name="userId"></td>
						    </tr>
						    <tr>
						      <th>비밀번호</th>
						      <td><input type="password" name="userPw"></td>
						    </tr>
						    <tr  align="center">
						      <td colspan="2" >
						      	<input type="button" value="로그인" id="login"> &nbsp; &nbsp;
								<input type="button" value="회원가입" id="join">
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
<script>

	$(document).ready(function(){
		
		var status = "${userVo.userAuth}";
		
		if(status == "sell"){
			location.href = "/medi/comp"
		}else if(status == "buy"){
			location.href = "/medi/goods"
		}
		
		$("#join").on("click",function(){
			location.href = "/medi/join";
		});

		$("#login").on("click",function(){
			var $frm = $(".loginForm :input")
			var param = $frm.serialize()
			
			$.ajax({
				url:"/medi/loginAction",
				dataType :"json",
				type:"POST",
				data:param,
				success:function(data){
					
					if(data.success == "N"){
						alert("로그인 실패")
						return false;
					}
					
					if(data.success.userAuth == "sell"){
						location.href = "/medi/comp"
					}else{
						location.href = "/medi/goods"
					}
					
				},
				error:function(){
					console.log("실패")
				},
				
			});
		});
	});

</script>