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
*{
	box-sizing:border-box;
}
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
	margin-top: 100px;
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
/* 헤더 설정 */
header {
	position: fixed;
	top: 0px;
	left: 0px;
	background-color: #7dc8c9;
	color: white;
	line-height: 50px;
	text-align: center;
	width: 100%;
	height: 50px;
}

.logo {
	float: left;
	width: 150px;
}

.header_menu_list {
	width: 100%;
}

.header_menu {
	float: right;
	padding-left: 15px;
	padding-right: 15px;
}

.header_sidebar {
	width: 45px;
	float: right;
	padding-top: 10px;
	padding-right: 15px;
}

.logo, .header_menu, .header_sidebar:hover {
	cursor: pointer;
}

section {
	margin-top: 50px;
}
</style>
</head>
<body>
	<div class="container">
		<header>
			<!-- 로고 -->
			<div class="logo" >
				<b>P</b>COOP!
			</div>

			<!-- 메뉴 -->
			<div class="row">
				<div class="d-md-block d-none header_menu_list">
					<div class="header_menu">로그아웃</div>
					<div class="header_menu">커뮤니티</div>
					<div class="header_menu">협업 구하기</div>
				</div>
				<div class="d-md-none d-block header_menu_list">
					<img src=menu.png class=header_sidebar>
				</div>
			</div>


		</header>
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
		
		$('header>.logo').on("click",function(){
		location.href='/';
	})
	</script>
</body>
</html>