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
		updateScroll();
		
		var ws = new WebSocket("ws://localhost/chat");  //이 url에 소켓 연결을 요청하고, WebChat 클래스가 요청을 받음
		
		ws.onmessage = function(e){
			
			var msg = JSON.parse(e.data);
						
			var id = msg.id;
			var text = msg.text;
			var date = msg.date;
			var time = msg.time;
			
			var chat_box = $("<div class='chat_box'>");
			var profile = $("<div class='profile'>");
			var profile_img = $("<img src='resources/images/chatting/profile.png' class='profile_img'>");
			
			profile.append(profile_img);
			
			var chat_box_in = $("<div class='chat_box_in'>");
			var name = $("<div class='name'>");
			var chat = $("<div class='chat'>");
			var timediv = $("<div class='time'>");
			
			name.append(id);
			chat.append(text);
			timediv.append(time);
			
			chat_box_in.append(name);
			chat_box_in.append(chat);
			chat_box_in.append(timediv);
			
			chat_box.append(profile);
			chat_box.append(chat_box_in);
			
			$(".chat_section").append(chat_box);
			
			updateScroll();
			
		}
		
		//엔터 버튼
		$("#input").keydown(function(e){
			if(e.keyCode == 13){  //13번 = 엔터키
				
				var d = new Date();
				
				var week = ["일","월","화","수","목","금","토"];
				var day = week[d.getDay()];
				
				var fulldate = d.toLocaleString();
				var date = d.getFullYear()+"년 "+(d.getMonth()+1)+"월 "+d.getDate()+"일 "+day+"요일";
				var time = d.toLocaleTimeString();
				
				var msg = {
					type: "message",
					text: $("#input").html(),
					fulldate: fulldate,
					date: date,
					time: time
				};
				
				ws.send(JSON.stringify(msg));  //json 데이터 string으로 보내기
				
				updateScroll();
				$("#input").html("");
				
				return false; //엔터쳤을때 커서가 아래로 떨어지지 않게 막아줌
			}
		})
		
		
		//전송 버튼
		$("#send_btn").on("click", function(){

			var d = new Date();
			
			var week = ["일","월","화","수","목","금","토"];
			var day = week[d.getDay()];
			
			var fulldate = d.toLocaleString();
			var date = d.getFullYear()+"년 "+(d.getMonth()+1)+"월 "+d.getDate()+"일 "+day+"요일";
			var time = d.toLocaleTimeString();
			
			var msg = {
				type: "message",
				text: $("#input").html(),
				fulldate: fulldate,
				date: date,
				time: time
			};
			
			ws.send(JSON.stringify(msg));
			
			
			
			//formdata로 보내기
			//var form = new FormData(document.getElementById('fileForm'));
			
			//ws.send(form);
			
			
			
			updateScroll();
			$("#input").html("");
			
			return false;
		})
		
		
		function updateScroll(){
			var element = document.getElementById("chat_section");
			element.scrollTop = element.scrollHeight;
		}
		
		
		var num = 1;
		//스크롤 위로 갈 시 이전 데이터 불러오기
		$("#chat_section").on("scroll", function(){
			
			if($("#chat_section").scrollTop() == 0){
				
				$.ajax({
					url : "lastChat",
					type : "post",
					data : {
						num : num
					},
					dataType : "json"

				}).done(function(response) {
					
					//대화 내용 먼저 출력
					for (var i = 0; i < response.length; i++) {
						
						var id = response[i].writer;
						var text = response[i].chat;
						var time = response[i].time;
						
						var chat_box = $("<div class='chat_box'>");
						var profile = $("<div class='profile'>");
						var profile_img = $("<img src='resources/images/chatting/profile.png' class='profile_img'>");
						
						profile.append(profile_img);
						
						var chat_box_in = $("<div class='chat_box_in'>");
						var name = $("<div class='name'>");
						var chat = $("<div class='chat'>");
						var timediv = $("<div class='time'>");
						
						name.append(id);
						chat.append(text);
						timediv.append(time);
						
						chat_box_in.append(name);
						chat_box_in.append(chat);
						chat_box_in.append(timediv);
						
						chat_box.append(profile);
						chat_box.append(chat_box_in);
						
						$(".chat_section").prepend(chat_box);
					}
					
					//해당 날짜 출력
					var date = response[0].form_date;
					
					var chat_section = $("<div class='chat_section' id=chat_section>");
					var chat_date_box = $("<div class=chat_date_box>");
					var chat_date_btn = $("<button class=chat_date_btn>");
					
					chat_date_btn.append(date);
					chat_date_box.append(chat_date_btn);
					
					$(".chat_section").prepend(chat_date_box);  //맨위에 올라가는게 가장 마지막에!
					
					num = num+1;  //날짜 +1
				})
				
			}
		})
		
		
		
	})
</script>

</head>

<body>
	<!-- Header -->
	<jsp:include page="../header/header.jsp"></jsp:include>
	<!-- 왼쪽 사이드바 -->
	<jsp:include page="../header/sidebar-left.jsp"></jsp:include>

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
                        <button class=chat_date_btn>${date}</button>
                    </div>
                    
                    <!-- 대화 내용 -->
                    <c:forEach var="i" items="${chatList}">
                    	<div class=chat_box>
                        	<div class=profile>
                            	<img src=resources/images/chatting/profile.png class=profile_img>
                        	</div>
                        	<div class=chat_box_in>
                            	<div class=name>${i.writer}</div>
                            	<div class=chat>${i.chat}</div>
                            	<div class=time>${i.time}</div>
                        	</div>
                    	</div>
                    </c:forEach>
                    
                </div>
                
                
                <!-- 채팅 입력창 -->
                <!-- <form enctype="multipart/form-data" id="fileForm"> -->
                <div class="chat_input_section">
                    <div class=chat_input_box>
                    	<!-- <input type=file name=file> -->
                        <button id=send_btn>전송</button>
                        <div id=input contenteditable=true></div>
                    </div>
                </div>
				<!-- </form> -->
			
			
			<!-- 여기까지 각자 영역 설정 -->
		</div>
	</section>
</body>
</html>