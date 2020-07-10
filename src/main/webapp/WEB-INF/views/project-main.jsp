<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<jsp:include page="header/cdn.jsp"></jsp:include>

<!-- Lobi List Default installation-->
<link rel="stylesheet"
	href="/resources/lobilist-master/lib/jquery/jquery-ui.min.css" />
<link rel="stylesheet"
	href="/resources/lobilist-master/lib/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="/resources/lobilist-master/dist/lobilist.min.css">
<link rel="stylesheet"
	href="/resources/lobilist-master/lib/lobibox/css/lobibox.min.css">
<link rel="stylesheet"
	href="/resources/lobilist-master/lib/highlight/github.css">
	


<link href='/resources/css/task/task.css' rel='stylesheet' />

	

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

var root_seq = ${root_seq};
var root_name = '${projectInfo.name}';

$(function() {
	
	
	//바로 projectHome 로드되도록 설정
	$("#container").load("goProjectHome");  //리퀘스트매핑
	
	$(".backup").append("<ul id=dir" + root_seq + " class='root dir'><b>" + root_name + "</b></ul>");
	
	// 디렉토리 가지고 오기
	var dirlist = ${dirlist};
	printDirList(dirlist);
	
	
	//채팅 목록 list 불러와서 왼쪽 사이드바에 뿌려주기
	var chattingList = ${chattingList};
	
	for (var i = 0; i < chattingList.length; i++) {
		var chatting_seq = chattingList[i].chatting_seq;
		var project_seq = chattingList[i].project_seq;
		var title = chattingList[i].title;
		var member_count = chattingList[i].member_count;
		var member_seq = chattingList[i].member_seq;
		var member_name = chattingList[i].member_name;
		var create_date = chattingList[i].create_date;
		var type = chattingList[i].type;
		
		
		var div = $("<div></div>");
		var span = $("<span class=logon>● </span>");
		var chatting = $("<a class=c_list id='c_seq"+chatting_seq+"'>"+title+"</a>");
		div.append(span);
		div.append(chatting);
		$(".chattingList").append(div);
	}
	
	
})

	$(function() {

		$(".c_list").on("click", function(){
			var c_seq = $(this).attr("id");
			$("#container").load("chatting", {c_seq: c_seq});
			//$("#modals").load("chat .modals");
		})
		
		$(".menu-backup").on("click", function(){
			$("#container").load("backup .contents");
			$("#modals").load("backup .modals");
			$(".root").trigger("click");
		})
		
		
		$(".menu-todo").on("click", function(){
			$("#container").load("/Task/task");
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