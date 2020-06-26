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
	
	<link rel="stylesheet" href="../../resources/css/sighup.css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>
회원가입 폼입니다 css 색상 수정전
${e_mail}

${tomail}

	<div class="container" id="container">
		<div class="form-container log-in-container">
			<form action="#">
			<br><br>
				<h1>회원가입</h1>
				<br><br>
				
				<input type="email" placeholder="이름"  class="in" />
				<input type="password" placeholder="비밀번호" class="in" />
				<input type="password" placeholder="비밀번호 확인" class="in" />
				<br><br>
                <button type="submit">회원가입</button><br>
                <button type="button">돌아가기</button><br>
			</form>
		</div>
		
	</div>
</body>
</html>