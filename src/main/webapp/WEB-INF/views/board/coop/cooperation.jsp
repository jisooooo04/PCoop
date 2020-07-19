<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML>
<!--
	Projection by TEMPLATED
	templated.co @templatedco
	Released for free under the Creative Commons Attribution 3.0 license (templated.co/license)
-->
<html>


<head>
<title>PCOOP!</title>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="stylesheet" href="resources/css/main/main.css?after" />
<script src="https://kit.fontawesome.com/8f6ea3bf70.js"
	crossorigin="anonymous"></script>
</head>

<style>
html{
	height: 100%;
}
#header{
	color: black;

}

#nav>a:hover{
	color: lightgray;
}

#header .logo{
	color: black;
}
#logo {
	color: #58C9B9;
}

#banner {
	background-image: url(resources/images/index/banner2.jpg);
}

#three{
	margin-top: 100px;
}
</style>


<body>

	<!-- Header -->
	<header id="header">
		<div class="inner">
			<a href="/" class="logo"><span id="logo">P</span>COOP!</a>
			<nav id="nav">
				<a href="member/gomypage">MY PAGE</a>
				<a href="goCoopBoard">협업 구하기</a> 
				<a href="#">참여 중 프로젝트</a> 
				<a href="#">프로젝트 소개</a> 
				<a href="Task/task">할 일 목록</a>
				<a href="calendar/calendar?project_seq=0">캘린더</a>
				<a href="chatting">채팅</a>

			</nav>
			<a href="#navPanel" class="navPanelToggle"><span
				class="fa fa-bars"></span></a>
				
		</div>
	</header>


	<!-- Three -->
	<section id="three" class="wrapper align-center">
		<div class="inner">협업 구하기</div>
		<div class="board-wrapper">
			<button id="btn-write"><a href=goCoopWrite>글쓰기</a></button>
		</div>
	</section>

	<!-- Scripts -->
	<script src="resources/js/jquery.min.js"></script>
	<script src="resources/js/skel.min.js"></script>
	<script src="resources/js/util.js"></script>
	<script src="resources/js/main.js"></script>

	<script>
		$("#project_create").on("click",function(){
			location.href="project_create";
		})
		$("#project_join").on("click",function(){
			location.href="project_join";
		})
		$("#signUp").on("click",function(){
			location.href="member/toEmailView";
		})
		
		$("#login").on("click",function(){
			location.href="member/toLoginView";
		})
		$("#logout").on("click",function(){
			location.href="member/logout";
		})
		
		
	</script>
</body>
</html>