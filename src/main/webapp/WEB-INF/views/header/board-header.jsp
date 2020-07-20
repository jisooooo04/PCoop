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
	z-index:3;
	position: fixed;
	top: 0;
	left: 0px;
	color: black;
	line-height: 50px;
	text-align: center;
	width: 100%;
	height: 100px;
    border-bottom: 1px solid #7dc8c9;
    background-color:white;
	padding-top: 50px;
}


.logo {
	float: left;
	width: 150px;
	color: #7dc8c9;
    font-size: 25px;
    padding-bottom:15px;
    position:absolute;
    top:-22px;
}

b{
	font-size: 25px;
    font-weight: bolder;
}

.header_menu_list {
	width: 100%;
}

.header_menu {
	float: right;
	padding-left: 15px;
	padding-right: 15px;
	font-size:17px;
	color:#7dc8c9;
	font-weight:600;
	line-height:2px;
}
.header_menu:hover{
	color:lightgray;
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



</style>
</head>
<body>

	<header>
		

		<!-- 메뉴 -->
		<div class="row">
			<div class="col-2 d-none d-md-block">
				
		</div>
		<div class="col-md-2 col-5">
				<!-- 로고 -->
				<div class="logo">
				<b>P</b>COOP!
				</div>
		</div>
		<div class="col-md-6 col-7">
			<div class="header_menu_list">
				<div class="header_menu" id='logout'>로그아웃</div>
				<div class="header_menu">Q&A</div>
				<!-- <div class="header_menu">커뮤니티</div> -->
			</div>
		</div>
		<div class="col-2 d-none d-md-block"></div>
		</div>
	</header>
	
	

	
	
	<script>
			$('header .logo').on("click",function(){
				location.href='/';
			})
			$("#logout").on("click",function(){
				location.href='../member/logout';
			})
		</script>
</body>
</html>