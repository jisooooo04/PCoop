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
	.container{
        margin-top: 300px;
        width: 600px;
        border-radius: 10px;
        padding-bottom: 200px;
        padding-top:10px;
    }
    .container div{
        width: 100%;
    }
   h2{
        font-family: 'Noto Sans KR', sans-serif;
        font-size: 30px;
        font-weight: 600;
        text-align: center;
        color: #1a6e67;
    }
    .col-12{
        position: relative;
    }
    .col-12>div{
        font-family: 'Noto Sans KR', sans-serif;
        font-size: 30px;
        font-weight: 600;
        text-align: center;
        color: #1a6e67;
    }
    button{
        margin-top: 20px;
        width: 200px;
        height: 60px;
        border:none;
        border-radius: 10px;
        background-color: #8ab8ab;
        position: absolute;
        left: 30%;
    }
    a{
        text-decoration: none;
        width: 100%;
        color: aliceblue;
        font-family: 'Noto Sans KR', sans-serif;
        font-weight: 500;
        font-size: 18px;
    }
    a:hover{
    	text-decoration: none;
    }
</style>
</head>
<body>
	<%-- <!-- Header -->
	<jsp:include page="../header/header.jsp"></jsp:include>
	<!-- 왼쪽 사이드바 -->
	<jsp:include page="../header/sidebar-left.jsp"></jsp:include> --%>

	<header>
		<nav class="navbar navbar-expand-lg navbar-light bg-light">
			<a class="navbar-brand" href="#">Navbar</a>
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#navbarNav" aria-controls="navbarNav"
				aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarNav">
				<ul class="navbar-nav">
					<li class="nav-item active"><a class="nav-link" href="/">Home
							<span class="sr-only">(current)</span>
					</a></li>
					<li class="nav-item"><a class="nav-link" href="#">Features</a>
					</li>
					<li class="nav-item"><a class="nav-link" href="#">Pricing</a>
					</li>
					<li class="nav-item"><a class="nav-link disabled" href="#">Disabled</a>
					</li>
				</ul>
			</div>
		</nav>

	</header>
	<div class="container">
       <div class="row">
           <div class="col-12 animate__animated animate__fadeInDownBig">
                  <h2>프로젝트 생성 완료!</h2>
                <div>프로젝트 코드는 ${code}입니다.</div>
                <button><a href="ProjectInvite?code=${code}&title=${title}">팀원 초대하러 가기</a></button>
           </div>
       </div>
   </div>
</body>
</html>