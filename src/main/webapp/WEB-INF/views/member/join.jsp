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
<link rel="stylesheet" href="../../resources/css/member/sighup.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">


</head>
<body>


	<div class="container" id="container">
		<div class="form-container log-in-container">
			<form action="signup" id="signup">
			<br><br>
				<h1>회원가입</h1>
				<br><br>
				
				<input type="text" name="name" id="name" placeholder="이름"  class="in" />
				<input type="password" name="pw" id="password_1"  placeholder="비밀번호 (4~20글자)" class="in pw" />
				<input type="password" id="password_2"  placeholder="비밀번호 확인" class="in pw" />
				<br>
	<span id="alert-success" style="display: none;">
	비밀번호가 일치합니다.
	</span>
    <span id="alert-danger" style="display: none; color: #d92742; font-weight: bold; ">
    비밀번호가 일치하지 않습니다.
    </span>
				
				<br>
                <button type="submit">회원가입</button><br>
                <button type="button" id="back">돌아가기</button><br>
			</form>
		</div>
		
	</div>
			
<script src="/resources/js/member/join.js"></script>

</body>
</html>