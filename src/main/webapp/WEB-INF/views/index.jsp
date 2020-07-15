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
<link rel="stylesheet" href="https://unpkg.com/aos@next/dist/aos.css" />
<link rel="stylesheet" href="resources/css/chatting/chatting.css?after" />


<script src="https://kit.fontawesome.com/8f6ea3bf70.js"
	crossorigin="anonymous"></script>
</head>

<style>
#logo {
	color: #58C9B9;
}

#banner {
	background-image: url(resources/images/index/banner2.jpg);
}
.chat_box, .time{
	float: left;
	text-align: left;
}
</style>


<body>

	<!-- Header -->
	<header id="header">
		<div class="inner">
			<a href="/" class="logo"><span id="logo">P</span>COOP!</a>
			<nav id="nav">
				<a href="member/gomypage">마이 페이지</a> <a href="#">협업 구하기</a> <a
					href="#">참여 중 프로젝트</a> <a href="#">프로젝트 소개</a> <a href="Task/task">할
					일 목록</a> <a href="calendar/calendar?project_seq=0">캘린더</a> <a
					href="chatting">채팅</a> <a href="community">커뮤니티</a>

			</nav>
			<a href="#navPanel" class="navPanelToggle"><span
				class="fa fa-bars"></span></a>

		</div>
	</header>

	<!-- Banner -->
	<section id="banner">
		<div class="inner">
			<header>
				<h1>
					효율적인 협업을 위한 <span id="logo">P</span>COOP!
				</h1>

			</header>

			<div class="flex ">

				<c:if test="${loginInfo == null}">
					<div id="signUp">
						<span class="icon fa-user-plus"></span>
						<h3>계정 만들기</h3>
						<p>
							프로젝트를 시작하기 위해<br> PCOOP 계정을 만드세요.
						</p>
					</div>
				</c:if>

				<div id="project_create">
					<span class="icon fa-briefcase"></span>
					<h3>프로젝트 생성</h3>
					<p>
						협업을 위한 프로젝트를<br> 생성해 보세요.
					</p>
				</div>

				<div id="project_join">
					<span class="icon fa-envelope-open"></span>
					<h3>프로젝트 참가</h3>
					<p>
						공유받은 초대 코드로<br>프로젝트에 참가하세요.
					</p>
				</div>

			</div>

			<footer>
				<c:choose>
					<c:when test="${empty loginInfo}">

						<a class="button" id="login">login</a>

					</c:when>
					<c:otherwise>
						<a class="button" id="logout">${loginInfo.name} logout</a>

					</c:otherwise>
				</c:choose>


			</footer>
		</div>
	</section>


	<!-- Three -->
	<section id="three" class="wrapper align-center">
		<div class="inner">
			<div class="flex flex-2">
				<article data-aos="fade-right">

					<header>
						<h3>
							단체 대화, 개인 대화, 주제별로<br/>채팅을 구분해 보세요!
						</h3>
					</header>
					<p>
						Morbi in sem quis dui placerat ornare. Pellentesquenisi<br />euismod
						in, pharetra a, ultricies in diam sed arcu. Cras<br />consequat
						egestas augue vulputate.
					</p>

				</article>
				<article>
					<div class=chat_box>
						<div class=profile>
							<img src=resources/images/chatting/profile.png class=profile_img>
						</div>
						<div class=chat_box_in>
							<div class=name>PCOOP</div>
							<div class=chat>채팅 기능 소스 코드 파일 좀 보내 주실래요?</div>
							<div class=time>오전 10:19:44</div>
						</div>
					</div>
				</article>
				<br />
				<article>
					<div class="image round">
						<img src="resources/images/index/pic02.jpg" alt="Pic 02" />
					</div>
					<header>
						<h3>
							Sed feugiat<br /> tempus adipicsing
						</h3>
					</header>
					<p>
						Pellentesque fermentum dolor. Aliquam quam lectus<br />facilisis
						auctor, ultrices ut, elementum vulputate, nunc<br /> blandit
						ellenste egestagus commodo.
					</p>

				</article>
				<article>
					<div class="image round">
						<img src="resources/images/index/pic02.jpg" alt="Pic 02" />
					</div>
					<header>
						<h3>
							Sed feugiat<br /> tempus adipicsing
						</h3>
					</header>
					<p>
						Pellentesque fermentum dolor. Aliquam quam lectus<br />facilisis
						auctor, ultrices ut, elementum vulputate, nunc<br /> blandit
						ellenste egestagus commodo.
					</p>

				</article>
			</div>
		</div>
	</section>

	<!-- Footer -->
	<footer id="footer">
		<div class="inner">FOOTER</div>
		<div class="copyright">
			&copy; Untitled. Design: <a href="https://templated.co">TEMPLATED</a>.
			Images: <a href="https://unsplash.com">Unsplash</a>.
		</div>
	</footer>

	<!-- Scripts -->
	<script src="resources/js/jquery.min.js"></script>
	<script src="resources/js/skel.min.js"></script>
	<script src="resources/js/util.js"></script>
	<script src="resources/js/main.js"></script>
	<script src="https://unpkg.com/aos@next/dist/aos.js"></script>
	<script>
		AOS.init();
	</script>
	<script>
		$("#project_create").on("click", function() {
			location.href = "project_create";
		})
		$("#project_join").on("click", function() {
			location.href = "project_join";
		})
		$("#signUp").on("click", function() {
			location.href = "member/toEmailView";
		})
		$("#login").on("click", function() {
			location.href = "member/toLoginView";
		})
		$("#logout").on("click", function() {
			location.href = "member/logout";
		})
	</script>

</body>
</html>