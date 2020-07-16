<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<jsp:include page="../header/cdn.jsp"></jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.0.0/animate.min.css" />
<title>PCOOP!</title>
<style>
*{
	box-sizing:border-box;
}
.container {
	margin-top: 300px;
	width: 600px;
	border-radius: 10px;
	padding-bottom: 200px;
	padding-top: 10px;
}

.container div {
	width: 100%;
}

h2 {
	font-family: 'Noto Sans KR', sans-serif;
	font-size: 30px;
	font-weight: 600;
	text-align: center;
	color: #1a6e67;
}

.col-12 {
	position: relative;
}

.col-12>div {
	font-family: 'Noto Sans KR', sans-serif;
	font-size: 30px;
	font-weight: 600;
	text-align: center;
	color: #1a6e67;
}

.col-12 .invite {
	margin-top: 20px;
	width: 200px;
	height: 60px;
	border: none;
	border-radius: 10px;
	background-color: #8ab8ab;
	position: absolute;
	left: 13%;
}
.col-12 .goProject{
	margin-top: 20px;
	width: 200px;
	height: 60px;
	border: none;
	border-radius: 10px;
	background-color: #8ab8ab;
	position: absolute;
	left: 52%;
}
.invite>a,.goProject>a {
	text-decoration: none;
	width: 100%;
	color: aliceblue;
	font-family: 'Noto Sans KR', sans-serif;
	font-weight: 500;
	font-size: 18px;
}

a:hover {
	text-decoration: none;
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
	<%-- <!-- Header -->
	<jsp:include page="../header/header.jsp"></jsp:include>
	<!-- 왼쪽 사이드바 -->
	<jsp:include page="../header/sidebar-left.jsp"></jsp:include> --%>

	<header>
		<!-- 로고 -->
		<div class="logo" href="">
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
	<div class="container">
		<div class="row">
			<div class="col-12 animate__animated animate__fadeInDownBig">
				<h2>프로젝트 생성 완료!</h2>
				<div>프로젝트 코드는 ${code}입니다.</div>
				<button class="invite">
					<a href="ProjectInvite?code=${code}&title=${title}">팀원 초대하러 가기</a>
				</button>
				<button class='goProject'>
					<a href="goProjectHome?seq=${project_seq}">프로젝트 바로가기</a>
				</button>
			</div>
		</div>
	</div>
	<script>
	$('header>.logo').on("click",function(){
		location.href='/';
	})
	</script>
</body>
</html>