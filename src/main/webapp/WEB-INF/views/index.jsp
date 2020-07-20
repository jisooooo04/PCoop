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
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/9.13.1/styles/agate.min.css">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/9.12.0/highlight.min.js"></script>
<script>
	hljs.initHighlightingOnLoad();
</script>

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

.chat_box, .time {
	float: left;
	text-align: left;
}

.code_editor {
	width: auto;
}

article>p{
	font-size: 13px;
}

article{
	 margin: auto;
}

</style>


<body>

	<!-- Header -->
	<header id="header">
		<div class="inner">
			<a href="/" class="logo"><span id="logo">P</span>COOP!</a>
			<nav id="nav">
					<a href="member/gomypage">마이 페이지</a>
					<a href="board/list">공지사항</a> 
					
					<c:if test="${loginInfo != null}">
					<a href="member/logout">로그아웃</a>
					</c:if>
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
				<article>
					<header>
						<h3>
							단체 대화, 개인 대화, 주제별로<br> 구분된 <span id="logo">업무 메신저</span>를 이용하세요!
						</h3>
					</header>
					<p>
						같은 기능을 전담하는 사람들끼리 따로 채팅방을 생성하세요!<br />
						기능별, 개인별로 <span id="logo">대화 목적을 나누어</span> 프로젝트의 흐름을 효율적으로 파악할 수 있습니다.<br />
						대화 도중 주고받았던 <span id="logo">파일 목록</span>을 따로 보여 드려요.<br />
						부분적으로 보내 주는 코드 텍스트를 알아보기 쉽게!<br />
						<span id="logo">언어별로 하이라이팅</span>하여 보여 드릴게요.
					</p>
				</article>
				<article>
					<div class=chat_box>
						<div class=profile data-aos="fade-left" data-aos-delay="300">
							<img src=resources/images/chatting/profile.png class=profile_img>
						</div>
						<div class=chat_box_in data-aos="fade-left" data-aos-delay="300">
							<div class=name>철수</div>
							<div class=chat>수정하신 소스 코드 부분 보내 주실래요?</div>
							<div class=time>오전 10:19:44</div>
						</div>
						<br/>
						<div class=profile data-aos="fade-right" data-aos-delay="550">
							<img src=resources/images/chatting/profile.png class=profile_img>
						</div>
						<div class="chat_box_in" data-aos="fade-right"
							data-aos-delay="550">
							<div class=name>영희</div>
							<div class=chat>
								<div class="image">
									<img src="resources/images/index/precode.PNG" alt="Pic 02" />
								</div>
							</div>
							<div class=time>오전 11:20:26</div>
						</div>
					</div>
				</article>
				<br>
				<br>
				<article data-aos="fade-right" data-aos-delay="700">
					<div class="image">
						<img src="resources/images/index/todo.PNG" alt="Pic 02" />
						<img src="resources/images/index/todo2.PNG" alt="Pic 02" />
					</div>
				</article>

				<article>
					<header>
						<h3>
							칸반 스타일의 <br /><span id="logo">To-do List</span>!
						</h3>
					</header>
					<p>
						할 일이 정리가 안 되시나요?<br>
						담당한 업무들을 <span id="logo">하나의 카드</span>로 묶어 간편하게 관리해 보세요.<br>
						완료된 업무는 <span id="logo">체크</span>!<br>
						프로젝트 전체 진행 과정을 진행 바의 게이지를 통해 시각적으로 점검할 수 있습니다.<br>
						<span id="logo">드래그 앤 드롭</span>으로 보다 쉽고 빠르게 업무를 조정해 보세요.
					</p>
				</article>
				<article>
					<header>
						<h3>
							팀원들과 공유하는 <span id="logo">백업 드라이브</span>
						</h3>
					</header>
					<p>
						드라이브에  팀원들과 공유해야 하는 <span id="logo">파일들을 백업</span>하세요.<br>
							파일뿐만 아니라 <span id="logo">.zip 파일</span>을 업로드하면 <span id="logo">자동으로 압축 해제</span>해 줍니다.<br>
							업로드된 파일이 <span id="logo">텍스트 파일</span>이라면 <span id="logo">미리 보기</span> 기능을 제공합니다.<br>
							언어별로 자동으로 <span id="logo">하이라이팅된 소스 코드</span>를 볼 수 있습니다.
					</p>
				</article>
				<article>
					<div class="image">
						<img src="resources/images/index/backup.png" alt="Pic 02" />
					</div>
					<div class="image preview">
						<img src="resources/images/index/preview.png" alt="Pic 02" />
					</div>
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