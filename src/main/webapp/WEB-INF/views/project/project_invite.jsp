<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<jsp:include page="../header/cdn.jsp"></jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<title>PCOOP!</title>
<style>
.wrapper {
	padding: 30px;
}
.wrapper>div {
	margin: 30px;
	text-align: center;
	font-family: 'Noto Sans KR', sans-serif;
}

.wrapper>div:nth-child(1) {
	font-size: 30px;
	font-weight: 500;
	color: #474a49;
}

.wrapper input {
	outline: none;
	width: 300px;
	height: 40px;
	border-radius: 10px;
	border: 1px solid #6caae0;
}

.wrapper button {
	width: 90px;
	height: 40px;
	border: none;
	color: white;
	background-color: #6caae0;
	border-radius: 10px;
	outline: none;
	margin-top: -5px;
}

.wrapper button:hover {
	background-color: #5989b3;
}
/* ----로딩이미지 style---- */
#lodingImg{
	position:relative;
	left:50%;
	z-index:100;
	display :none;
}
</style>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-12">
				<div class="wrapper">
					<div>이메일로 초대코드를 전송해보세요 :)</div>
					<div>
						<input type="email" placeholder="초대 할 회원의 이메일을 입력해주세요.">
						<button class="btn">초대하기</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 로딩중 이미지 -->
	<div class="loding">
		<img id="lodingImg" src="/resources/images/project/lodingImg.gif">
	</div>
	
	<script>
	
	/* $(function(){
		.ajaxStart(function(){
			$("#lodingImg").show();
		})
	}) */
	$(".btn").on("click",function(){
		var email=$("input").val();
		if(email==''){
			alert("이메일을 입력해주세요.");
		}else{
			$("#lodingImg").show();
			 $.ajax({
				url:"sendEmail",
				data:{
					email:email,
					code:'${code}',
					title:'${title}'
				},
			type:"post",
			beforeSend:function(resp){
				$("#lodingImg").show();
			},
			error:function(error){
				$("#lodingImg").hide();
				$("input").val('');
				alert("이메일을 다시 한번 확인해주세요.");
				$("#lodingImg").hide();
			},
			success : function(resp){
				$("#lodingImg").hide();
				$("input").val('');
				alert("이메일이 성공적으로 발신되었습니다.");
				$("#lodingImg").hide();
			}
			})
		}
	})
		
		
	</script>
</body>
</html>