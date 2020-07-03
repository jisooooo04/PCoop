<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<jsp:include page="header/cdn.jsp"></jsp:include>

<link rel="stylesheet" href="resources/css/backup/filelist.css?after" />
<link rel="stylesheet" href="resources/css/chatting/chatting.css?after" />

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/9.13.1/styles/vs2015.min.css">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/9.12.0/highlight.min.js"></script>
<script>
	hljs.initHighlightingOnLoad();
</script>
<script>
$(function() {

	$(".backup").append("<ul id=dir4 class='root dir'>TEMP</ul>");

	// 디렉토리 가지고 오기
	var dirlist = ${dirlist};
	printDirList(dirlist);

	// 파일 가지고 오기
	var filelist = ${filelist};
	// printFileList(filelist);
	
	

})
</script>
<script>
	$(function() {
		var ws = new WebSocket("ws://localhost/chat");  //이 url에 소켓 연결을 요청하고, WebChat 클래스가 요청을 받음

		$(".menu-chat").on("click", function(){
			$("#container").load("chat");
			//$("#modals").load("chat .modals");
		})
		
		$(".menu-backup").on("click", function(){
			$("#container").load("backup .contents");
			$("#modals").load("backup .modals");
		})
		
	})
</script>
</head>
<body>
	<jsp:include page="header/header.jsp"></jsp:include>
	<jsp:include page="header/sidebar-left.jsp"></jsp:include>

	<section>

		<div id="container">
		
		</div>

	</section>
	
	<div id="modals"></div>


	<script src="resources/js/backup/directory.js"></script>
	<script src="resources/js/backup/file.js"></script>
	
</body>
</html>