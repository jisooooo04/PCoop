<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
	<script src="resources/js/chatting/chatting.js"></script>


<div class="modals">
	<div id=contextmenu>
		<div class="c_menu delete_chat">삭제</div>
		<div class="c_menu reply_chat">답장</div>
		<div class="c_menu copy_chat">복사</div>
		<div class="c_menu deliver_chat">전달</div>
		<div class="c_menu post_chat">공지</div>
	</div>
</div>

<div class="contents">

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
					<div class=chat id="${i.seq}">${i.chat}</div>
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
			<div id=input contenteditable=true>
				<!-- 입력창 -->

				<pre class=pre>
				<code class="code_editor"></code>
			</pre>
				<!-- 코드 편집기 -->

			</div>

			<div class=icon_box>
				<img src=resources/images/chatting/smile.png
					class="input_icon emoticon_icon"> <img
					src=resources/images/chatting/file.png class="input_icon file_icon">
				<img src=resources/images/chatting/code.png
					class="input_icon code_icon" style="width: 22px; margin-top: 4px">
			</div>
		</div>

		<!-- 이모티콘 섹션 -->
		<div class=emoticon_section>
			<div class=emoticon_title>이모티콘</div>
			<div class=emoticon_box>
				<img src="resources/images/chatting/1-1.gif" class=emoticon
					id=1-1.gif> <img src="resources/images/chatting/1-2.gif"
					class=emoticon id=1-2.gif> <img
					src="resources/images/chatting/1-3.gif" class=emoticon id=1-3.gif>
				<img src="resources/images/chatting/1-4.gif" class=emoticon
					id=1-4.gif> <img src="resources/images/chatting/1-5.gif"
					class=emoticon id=1-5.gif> <img
					src="resources/images/chatting/1-6.gif" class=emoticon id=1-6.gif>
				<img src="resources/images/chatting/1-7.gif" class=emoticon
					id=1-7.gif> <img src="resources/images/chatting/1-8.gif"
					class=emoticon id=1-8.gif> <img
					src="resources/images/chatting/1-9.gif" class=emoticon id=1-9.gif>
				<img src="resources/images/chatting/1-10.gif" class=emoticon
					id=1-10.gif> <img src="resources/images/chatting/1-11.gif"
					class=emoticon id=1-11.gif> <img
					src="resources/images/chatting/1-12.gif" class=emoticon id=1-12.gif>
				<img src="resources/images/chatting/1-13.gif" class=emoticon
					id=1-13.gif> <img src="resources/images/chatting/1-14.gif"
					class=emoticon id=1-14.gif> <img
					src="resources/images/chatting/1-15.gif" class=emoticon id=1-15.gif>
				<img src="resources/images/chatting/1-16.gif" class=emoticon
					id=1-16.gif> <img src="resources/images/chatting/1-17.gif"
					class=emoticon id=1-17.gif> <img
					src="resources/images/chatting/1-18.gif" class=emoticon id=1-18.gif>
				<img src="resources/images/chatting/1-19.gif" class=emoticon
					id=1-19.gif> <img src="resources/images/chatting/1-20.gif"
					class=emoticon id=1-20.gif>
			</div>
		</div>

	</div>
	<!-- </form> -->
</div>
