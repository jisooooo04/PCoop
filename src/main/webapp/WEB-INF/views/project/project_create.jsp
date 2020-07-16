<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="../header/cdn.jsp"></jsp:include>
<title>PCOOP!</title>
<style>
*{
	box-sizing:border-box;
}
.container {
	position: relative;
}

.container>h2 {
	font-family: 'Noto Sans KR', sans-serif;
	font-weight: 600px;
	text-align: center;
	font-weight: bolder;
	color: cadetblue;
	margin-top: 110px;
	margin-bottom: 50px;
}

form {
	border: 1.5px solid cadetblue;
	border-radius: 10px;
	padding: 20px;
	width: 600px;
	position: absolute;
	left: 25%;
	padding-bottom: 7px;
}

.btn {
	margin-top: 10px;
	width: 150px;
	height: 50px;
	background-color: cadetblue;
	opacity: 80%;
	color: aliceblue;
}

.btn:hover {
	opacity: 130%;
	color: aliceblue;
}

label {
	font-family: 'Noto Sans KR', sans-serif;
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
		<section>
			<div class="container">
				<h2>프로젝트 생성하기</h2>
				<form action="create" method="post" id="target">
					<div class="form-group">
						<label>Project name</label><input type="text" class="form-control"
							id="exampleFormControlInput1" name='name'
							placeholder='프로젝트의 이름을 입력해주세요.' required>
					</div>
					<div class="form-group">
						<label>프로젝트 참여 인원수를 설정해주세요.</label> <select class="form-control"
							name="people_num">
							<c:forEach var='i' begin='1' step='1' end='50'>
								<option value='${i}'>${i}</option>
							</c:forEach>
						</select>
						<button type="submit" class="btn">프로젝트 생성</button>
					</div>
				</form>
			</div>
		</section>
	</div>

	<script>
	
	var target = document.getElementById('target');
	target.addEventListener('submit',function(event){
				if(${countProject}>=10){
					event.preventDefault();    
					alert("프로젝트 참여는 10개까지만 가능합니다.");
				}      
        })  
        	
	</script>
</body>
</html>