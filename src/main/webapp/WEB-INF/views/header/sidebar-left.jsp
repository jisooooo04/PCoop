<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
<script>
	$(function() {

		 // 디렉토리 가지고 오기
			var dirlist = ${dirlist};
			for(var i = 0 ; i < dirlist.length ; i++){
				
				var patharr = dirlist[i].path.split('/');
				var parent = "#root";
				
				for(var j = 2 ; j < patharr.length ; j++){
					
					if(j == patharr.length - 1)
						$(parent).append("<ul id=" + patharr[j] + "><li class='dir'>" + patharr[j] + "</li></ul>");
					else parent = "#" + patharr[j];
					
				}
				
			}
			
		// 파일 가지고 오기
			var filelist = ${filelist};
			
			for(var i = 0 ; i < filelist.length ; i++){
				
				console.log(filelist[i].path);
				var patharr = filelist[i].path.split('/');
				var parent = "#root";
				
				for(var j = 2 ; j < patharr.length ; j++){
					console.log(parent);
					if(j == patharr.length - 1)
						$(parent).append("<ul><li class='file'>" + patharr[j] + "</li></ul>");
					else parent = "#" + patharr[j];
					
				}
				
			}
	})
</script>
<style>
.dir {
	color: blue;
}

.file {
	color: red;
}
</style>
</head>
<body>
	<!-- 왼쪽 사이드바 -->
	<nav id=leftnav>
		<div class="navhome">
			<img src="resources/images/sidebar-left/home.png" class=navicon> <a class=project_home>프로젝트명</a>
		</div>
		<div class="navtitle">
			<img src="resources/images/sidebar-left/todolist.png" class=navicon> To-do List
		</div>
		<div class="navcontents">
			<div>프론트엔드</div>
			<div>백엔드</div>
			<div>아두이노</div>
		</div>
		<div class="navtitle">
			<img src="resources/images/sidebar-left/calendar.png" class=navicon> 캘린더
		</div>
		<div class="navcontents">
			<div>세미 프로젝트</div>
			<div>파이널 프로젝트</div>
		</div>
		<div class="navtitle">
			<img src="resources/images/sidebar-left/chat.png" class=navicon> 채팅
		</div>
		<div class="navcontents">
			<div>
				<span class=logon>● </span>kh 파이널 5조
			</div>
			<div>
				<span class=logon>● </span>박정수
			</div>
			<div>
				<span class=logon>● </span>김현동
			</div>
		</div>
		<div class="navtitle">
			<img src="resources/images/sidebar-left/folder.png" class=navicon> 백업
		</div>
		<div class="navcontents backup">
			
		</div>
	</nav>
</body>
</html>