<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
	<link rel="stylesheet" href="../../resources/css/member/login.css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>
<div class="container" id="container">
		<div class="form-container log-in-container">
			<form action="#">
				<h1>LOGO</h1>
				<br><br>
				
				<input type="email" placeholder="Email"  class="in" />
				<input type="password" placeholder="Password" class="in" />
				<br><br>
                <div class="id"><input type="checkbox" class="checkbox">
                <span> ID 기억하기</span></div>
                <br>
				<button type="submit">LogIn</button><br>		
				<button type="button">signUP</button>
			</form>
		</div>
		<div class="overlay-container">
			<div class="overlay">
				<div class="overlay-panel overlay-right">
					<h1>안내문</h1>
					<p>사이트소개</p>
				</div>
			</div>
		</div>
	</div>
</body>
</html>