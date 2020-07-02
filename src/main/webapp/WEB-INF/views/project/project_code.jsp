<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href='/resources/css/calendar/calendar.css?after' rel='stylesheet' />
<script src='/resources/js/calendar/calendar.js'></script>
<jsp:include page="../header/cdn.jsp"></jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<title>PCOOP!</title>
</head>
<body>
	<%-- <!-- Header -->
	<jsp:include page="../header/header.jsp"></jsp:include>
	<!-- 왼쪽 사이드바 -->
	<jsp:include page="../header/sidebar-left.jsp"></jsp:include> --%>
	<div>프로젝트 코드는 ${code}입니다.</div>
	<div>이제 팀원들을 초대해주세요 click!</div>
</body>
</html>