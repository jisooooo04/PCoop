<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="../header/cdn.jsp"></jsp:include>
<script>
	$(function() {
		
		var root_name = '${projectInfo.name}';
		
		$.ajax({
			url: "leftsidebar",
			type: "post",
			dataType: "json",
			success: function(response){
			
			//var data = JSON.parse(response);
			var root_seq = JSON.parse(response.root_seq);
			
				//var root_seq = data.root_seq;
				
				$(".backup").append("<ul id=dir" + root_seq + " class='root dir'><b>" + root_name + "</b></ul>");

				// 디렉토리 가지고 오기
				var dirlist = JSON.parse(response.dirlist);
				for (var i = 0; i < dirlist.length; i++) {
					var name = dirlist[i].name;
					var parent = ".root";
					$(parent).append("<li class=dir id=dir" + dirlist[i].seq + ">" + name + "</li>");
				}

				//채팅 목록 list 불러와서 왼쪽 사이드바에 뿌려주기
				//var chattingList = ${data.chattingList};
				var chattingList = JSON.parse(response.chattingList);
				var mainChatting = "c_num"+chattingList[0].chatting_num;

				for (var i = 0; i < chattingList.length; i++) {
					var chatting_seq = chattingList[i].chatting_seq;
					var project_seq = chattingList[i].project_seq;
					var chatting_num = chattingList[i].chatting_num;
					var title = chattingList[i].title;
					var member_count = chattingList[i].member_count;
					var member_seq = chattingList[i].member_seq;
					var member_name = chattingList[i].member_name;
					var create_date = chattingList[i].create_date;
					var type = chattingList[i].type;

					var div = $("<div class=side_chatting_list></div>");
					var span = $("<span class=logon>● </span>");
					var chatting = $("<a class=c_list id='c_num"+chatting_num+"'>"
							+ title + "</a>");
					div.append(span);
					div.append(chatting);
					$(".chattingList").append(div);
				}
			}
		});
		
		
		//미니 사이드바 아이콘 이벤트
        $(".navicon").on("click", function(){
            $("#leftnav").css("left", "0px");
            $("#mininav").css("display", "none");
            $('section').css("padding-left", "250px");
        })
        
        //사이드바 열렸을때 다시 닫기
        $(".back_btn").on("click", function(){
            $("#leftnav").css("left", "-190px");
            $("#mininav").css("display", "block");
            $('section').css("padding-left", "60px");
        })
        
        

		//각 jsp로 이동하는 코드

		//채팅
		$(document).on("click", ".c_list", function(){
			var c_num = $(this).attr("id");
			location.href = "chatting?c_num=" + c_num;
		});
		
		$(document).on("click", ".minichat", function(){
			location.href = "chatting?c_num=" + mainChatting;
		})

		//백업
		$(".menu-backup").on("click", function() {
			//$("#container").load("backup .contents");
			location.href = "backup";
			//$("#modals").load("backup .modals");
			$(".root").trigger("click");
		})

		//to-do-list
		$(".menu-todo").on("click", function() {
			$("#container").load("/Task/task");
		})

	})
</script>
</head>
<body>
	<!-- 왼쪽 사이드바 -->
	<nav id=leftnav>
		<div class="navhome">
			<img src="/resources/images/sidebar-left/home.png" class=navicon>
			<a class=project_home>프로젝트 홈</a>
		</div>
		<div class="navtitle menu-todo">
			<img src="/resources/images/sidebar-left/todolist.png" class=navicon>
			To-do List</a>
		</div>
		<div class="navcontents">
			<div>프론트엔드</div>
			<div>백엔드</div>
			<div>아두이노</div>
		</div>
		<div class="navtitle">
			<img src="/resources/images/sidebar-left/calendar.png" class=navicon>
			캘린더
		</div>
		<div class="navcontents">
			<div>세미 프로젝트</div>
			<div>파이널 프로젝트</div>
		</div>
		<div class="navtitle menu-chat">
			<img src="/resources/images/sidebar-left/chat.png" class=navicon>
			채팅
		</div>
		<div class="navcontents chattingList"></div>
		<div class="navtitle menu-backup">
			<img src="/resources/images/sidebar-left/folder.png" class=navicon>
			백업
		</div>
		<div class="navcontents backup"></div>
		
		<img src=/resources/images/sidebar-left/back.png class="back_btn">
	</nav>


	<!-- 왼쪽 사이드바 반응형 -->
	<nav id=mininav>
		<div class="navhome">
			<img src="/resources/images/sidebar-left/home.png" class=navicon>
		</div>
		<div class="navtitle">
			<img src="/resources/images/sidebar-left/todolist.png" class=navicon>
		</div>
		<div class="navtitle">
			<img src="/resources/images/sidebar-left/calendar.png" class=navicon>
		</div>
		<div class="navtitle">
			<img src="/resources/images/sidebar-left/chat.png"
				class="navicon minichat">
		</div>
		<div class="navtitle">
			<img src="/resources/images/sidebar-left/folder.png" class=navicon>
		</div>
	</nav>

</body>
</html>