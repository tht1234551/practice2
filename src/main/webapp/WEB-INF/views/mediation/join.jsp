<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/tiles/taglib.jsp" %>

<div align="center">
<form class="joinForm">
	<section class="ftco-section">
		<div class="container">
			<div class="row justify-content-center">
				<div class="col-md-6 text-center mb-5">
					<h2 class="heading-section">회원가입</h2>
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
						    <tr>
						      <th>비밀번호 확인</th>
						      <td><input type="password" name="userPwCheck"></td>
						    </tr>
						    <tr>
						      <th>이름</th>
						      <td><input type="text" name="userName"></td>
						    </tr>
						    <tr>
						      <th>전화번호1</th>
						      <td><input type="text" name="userPhone1"></td>
						    </tr>
						    <tr>
						      <th>전화번호2</th>
						      <td><input type="text" name="userPhone2"></td>
						    </tr>
						    <tr>
						      <th>우편번호</th>
						      <td><input type="text" name="userPost"></td>
						    </tr>
						    <tr>
						      <th>주소</th>
						      <td><input type="text" name="userAddr"></td>
						    </tr>
						    <tr>
						      <th>회원유형</th>
						      <td>
						      	<input type="radio" name="userAuth" value="sell" checked="checked">판매자  &nbsp; &nbsp;
								<input type="radio" name="userAuth" value="buy">구매자
							  </td>
						    </tr>
						    <tr align="center">
								<td colspan="2">
									<input type="button" value="회원가입" id="join"> &nbsp; &nbsp;
									<input type="button" value="로그인" id="login">
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
		console.log(2)
		
		$("#join").on("click",function(){
			
			if($(":input[name='userId']").val().length <= 0){
				alert("아이디를입력해주세요.");
				$(":input[name='userId']").focus()
				return false;
			}
			
			var $frm = $(".joinForm :input")
			var param = $frm.serialize()
			
			$.ajax({
				url:"/medi/joinAction",
				dataType :"json",
				type:"POST",
				data:param,
				success:function(data){
					console.log(data.success)
					if(data.success=="Y"){
						alert("가입성공")
						location.href="/medi/main"
					}else{
						alert("가입실패")
					}
				},
				error:function(){
					console.log("실패")
				},
				
			});
		});
		
		$("#login").on("click",function(){
			
			location.href="/medi/main"
		});
	});

</script>