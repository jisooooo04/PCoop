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

<!-- 코드 편집기 CDN -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/9.13.1/styles/agate.min.css">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/9.12.0/highlight.min.js"></script>
<script>hljs.initHighlightingOnLoad();</script>

<script src="resources/js/chatting/chatting.js"></script>

</head>

<body>

	<div id=contextmenu>
		<div class="c_menu delete_chat">삭제</div>
		<div class="c_menu reply_chat">답장</div>
		<div class="c_menu copy_chat">복사</div>
		<div class="c_menu deliver_chat">전달</div>
		<div class="c_menu post_chat">공지</div>
	</div>

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

				<!-- 채팅 날짜 (어제) -->
				<div class=chat_date_box>
					<button class=chat_date_btn>${yesterday}</button>
				</div>

				<!-- 대화 내용 (어제) -->
				<c:forEach var="i" items="${yesterdayChat}">
					<div class=chat_box>
						<div class=profile>
							<img src=resources/images/chatting/profile.png class=profile_img>
						</div>
						<div class=chat_box_in>
							<div class=name>${i.writer}</div>
							<div class=chat id="${i.seq}">${i.chat}</div>
							<div class=time>${i.time}</div>
						</div>
					</div>
				</c:forEach>


				<!-- 채팅 날짜 (오늘) -->
				<div class=chat_date_box>
					<button class=chat_date_btn>${today}</button>
				</div>

				<!-- 대화 내용 (오늘) -->
				<c:forEach var="i" items="${todayChat}">
					<div class=chat_box>
						<div class=profile>
							<img src=resources/images/chatting/profile.png class=profile_img>
						</div>
						<div class=chat_box_in>
							<div class=name>${i.writer}</div>
							<div class=chat id="${i.seq}">${i.chat}</div>
							<div class=time>${i.time}</div>
						</div>
					</div>
				</c:forEach>



			</div>


			<!-- 채팅 입력창 -->
			<div class="chat_input_section">
				<div class=chat_input_box>
					
					<button id=send_btn>전송</button>
					
					<div id=input contenteditable=true></div>		
					
					
					<div class=icon_box>
						<img src=resources/images/chatting/smile.png
							class="input_icon emoticon_icon"> <img
							src=resources/images/chatting/file.png
							class="input_icon file_icon"> <img
							src=resources/images/chatting/code.png
							class="input_icon code_icon" style="width: 22px; margin-top: 4px">
					</div>
					
					
					<form method="POST" enctype="multipart/form-data" id="fileForm">
						<input type=file id=file_select name=file>
					</form>
					
				</div>
				
				<!-- 이모티콘 섹션 -->
				<div class=emoticon_section>
					<div class=emoticon_title>이모티콘</div>
					<div class=emoticon_box>
						<c:forEach var="i" begin="1" step="1" end="20">
							<img src="resources/images/chatting/1-${i}.gif" class=emoticon
								id="1-${i}.gif">
						</c:forEach>

					</div>
				</div>
				
				
				<input type=text id=copy_box style="bottom: -50px; position: absolute">
				
			</div>
			
			
			
			
			<!-- 여기까지 각자 영역 설정 -->
		
</body>
</html>

