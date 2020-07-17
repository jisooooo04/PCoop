<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
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
a
</style>
</head>
<body>

	<header>
		<!-- 로고 -->
		<div class="logo" href="">
			<b>P</b>COOP!
		</div>

		<!-- 메뉴 -->
		<div class="row">
			<div class="header_menu_list">
				<div class="header_menu" id='logout'>로그아웃</div>
				<!-- <div class="header_menu">커뮤니티</div>
				<div class="header_menu">협업 구하기</div> -->
			</div>
			<!-- <div class="d-md-none d-block header_menu_list">
				<img src=/resources/images/header/menu.png class=header_sidebar>
			</div> -->
		</div>
	</header>
	
	

	
	
	<script>
			$('header>.logo').on("click",function(){
				location.href='/';
			})
			$("#logout").on("click",function(){
				location.href='../member/logout';
			})
		</script>
</body>
</html>