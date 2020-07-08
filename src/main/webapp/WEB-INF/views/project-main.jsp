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
	
	//바로 projectHome 로드되도록 설정
	$("#container").load("goProjectHome");  //리퀘스트매핑
	
	$(".backup").append("<ul id=dir4 class='root dir'>TEMP</ul>");

	// 디렉토리 가지고 오기
	var dirlist = ${dirlist};
	printDirList(dirlist);
	
	
	//채팅 목록 list 불러와서 왼쪽 사이드바에 뿌려주기
	var chattingList = ${chattingList};
	
	
	
	$(".chattinList").append();
	
	
})
</script>
<script>
	$(function() {

		$(".menu-chat").on("click", function(){
			$("#container").load("chatting");
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
			<!-- 본인영역 추가 -->
			
			project main입니다.
			
			<!-- 본인영역 끝 -->
		</div>
	</section>
	
	<div id="modals"></div>


	<script src="resources/js/backup/directory.js"></script>
	<script src="resources/js/backup/file.js"></script>
	
</body>
</html>