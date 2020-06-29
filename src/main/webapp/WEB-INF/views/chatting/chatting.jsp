<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="../header/cdn.jsp"></jsp:include>

<link rel="stylesheet" href="resources/css/chatting/chatting.css?after" />

<script>
	$(function(){
		var ws = new WebSocket("ws://localhost/chat");  //이 url에 소켓 연결을 요청하고, WebChat 클래스가 요청을 받음
		
		ws.onmessage = function(e){
			
			console.log(e.data);
			
			var chat_box = $("<div class='chat_box'>");
			var profile = $("<div class='profile'>");
			var profile_img = $("<img src='resources/images/chatting/profile.png' class='profile_img'>");
			
			profile.append(profile_img);
			
			var chat_box_in = $("<div class='chat_box_in'>");
			var name = $("<div class='name'>이름</div>");
			var chat = $("<div class='chat'>")
			
			chat.append(e.data);
			
			chat_box_in.append(name);
			chat_box_in.append(chat);
			
			chat_box.append(profile);
			chat_box.append(chat_box_in);
			
			$(".chat_section").append(chat_box);			
			
			updateScroll();
			
		}
		
		
		$("#input").keydown(function(e){
			if(e.keyCode == 13){  //13번 = 엔터키
				var text = $("#input").html();
				
				updateScroll();
				$("#input").html("");
				
				ws.send(text)  //웹소켓에 보냄?
				
				return false; //엔터쳤을때 커서가 아래로 떨어지지 않게 막아줌
			}
		})
		
		
		$("#send_btn").on("click", function(){
			var text = $("#input").html();			
			
			updateScroll();
			$("#input").html("");
			
			ws.send(text);

			return false; //엔터쳤을때 커서가 아래로 떨어지지 않게 막아줌
		})
		
		
		function updateScroll(){
			var element = document.getElementById("chat_section");
			element.scrollTop = element.scrollHeight;
		}
		
	})
</script>

</head>

<body>
	<!-- Header -->
	<jsp:include page="../header/header.jsp"></jsp:include>
	<!-- 왼쪽 사이드바 -->
	<jsp:include page="../header/left-sidebar.jsp"></jsp:include>

	<section>
		<div id="container">
			<!-- 여기부터 각자 영역 설정 -->
			
			<!-- 채팅 타이틀 -->
                <div class="chat_title_section">
                    <div class=chat_title>KH 파이널 5조</div>
                    <div class=chat_person_num>
                    	<img src="resources/images/chatting/user.png" class=chat_person_img>5
                    </div>
                </div>
                
                <!-- 채팅창 -->
                <div class="chat_section" id=chat_section>
                   <!-- 채팅 날짜 -->
                    <div class=chat_date_box>
                        <button class=chat_date_btn>2020년 06월 25일 목요일</button>
                    </div>
                    
                    <!-- 대화 내용 -->
                    <div class=chat_box>
                        <div class=profile>
                            <img src=resources/images/chatting/profile.png class=profile_img>
                        </div>
                        <div class=chat_box_in>
                            <div class=name>이름</div>
                            <div class=chat>내용</div>
                        </div>
                    </div>
                    
                    <div class=chat_box>
                        <div class=profile>
                            <img src=resources/images/chatting/profile.png class=profile_img>
                        </div>
                        <div class=chat_box_in>
                            <div class=name>이름</div>
                            <div class=chat>내용</div>
                        </div>
                    </div>
                    
                    
                </div>
                
                <!-- 채팅 입력창 -->
                <div class="chat_input_section">
                    <div class=chat_input_box>
                        <button id=send_btn>전송</button>
                        <div id=input contenteditable=true></div>
                    </div>
                </div>
			
			
			<!-- 여기까지 각자 영역 설정 -->
		</div>
	</section>
</body>
</html>