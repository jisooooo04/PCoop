<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
<script
	src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<link rel="stylesheet" href="../../resources/css/member/login.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

</head>
<body>









	<div class="container" id="container">
		<div class="form-container log-in-container">
			<form action="login">
				<h1>로그인</h1>
				<br> <br> <input type="email" name="email"
					placeholder="Email" class="in" /> <input type="password" name="pw"
					placeholder="Password" class="in" /> <br> <br>
				<div class="id">
					<input type="checkbox" class="checkbox"> <span>
						email 기억하기(미구현)</span>
				</div>
				<br>
				<button type="submit">LogIn</button>
				<br>
				<button type="button">signUP</button>
			</form>
		</div>
		
		<div class="overlay-container">
			<div class="overlay">
				<div class="overlay-panel overlay-right">
					<form action=auth method="post">
					<h1 style="color:black">가입하기</h1>
						<br><br><br><br>
						<input type="email" name="e_mail"
							placeholder="이메일주소를 입력하세요." class="in">
						<br><br><br><br><br>
						<button type="submit" name="submit">이메일 인증받기</button>
						<br>
					</form>
				</div>
			</div>
		</div>
	</div>


</body>
</html>