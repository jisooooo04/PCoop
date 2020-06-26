<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<jsp:include page="../header/cdn.jsp"></jsp:include>
<link rel="stylesheet" href="resources/css/backup/filelist.css" />

<script>
	$(function() {

		$(".backup").append("<ul id=root></ul>");

		// 디렉토리 가지고 오기
		var dirlist = ${dirlist};
		
		console.log(dirlist.length);

		for (var i = 0; i < dirlist.length; i++) {

			console.log(dirlist[i].path);
			var patharr = dirlist[i].path.split('/');
			var parent = "#root";

			for (var j = 2; j < patharr.length; j++) {

				if (j == patharr.length - 1)
					$(parent).append(
							"<ul id=" + patharr[j] + "><li class='dir'>"
									+ patharr[j] + "</li></ul>");
				else
					parent = "#" + patharr[j];

			}

		}

		// 파일 가지고 오기
		var filelist = ${filelist};

		for (var i = 0; i < filelist.length; i++) {

			console.log(filelist[i].path);
			var patharr = filelist[i].path.split('/');
			var parent = "#root";

			for (var j = 2; j < patharr.length; j++) {
				console.log(parent);
				if (j == patharr.length - 1)
					$(parent)
							.append(
									"<ul><li class='file'>" + patharr[j]
											+ "</li></ul>");
				else
					parent = "#" + patharr[j];

			}

		}
	})
</script>
</head>
<body>
	<jsp:include page="../header/header.jsp"></jsp:include>
	<!-- 왼쪽 사이드바 -->
	<jsp:include page="../header/sidebar-left.jsp"></jsp:include>

	<section>

		<div id="container">
			<!-- 여기부터 각자 영역 설정 -->
			<ul class="contextmenu">
				<li><a href="#">하위 디렉토리 추가</a></li>
				<li><a href="#">Link to somewhere</a></li>
				<li><a href="#">Another link</a></li>
				<li><a href="#">Link to nowhere</a></li>
				<li><a href="#">Random link</a></li>
			</ul>

			<!-- 여기까지 각자 영역 설정 -->
		</div>

	</section>
</body>
</html>