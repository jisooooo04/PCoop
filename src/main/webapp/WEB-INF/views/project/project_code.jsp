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

</style>
</head>
<body>


	<jsp:include page="../header/board-header.jsp"></jsp:include>
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

</body>
</html>