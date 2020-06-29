<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="../header/cdn.jsp"></jsp:include>

<style>
/* 채팅jsp에만 적용되는 섹션,컨테이너 스타일*/
section {
	overflow-x: hidden;
	overflow-y: hidden;
	word-wrap: break-word;
}

#container {
	position: relative;
	overflow-x: hidden;
	overflow-y: hidden;
}

/* 여기부터 각자 영역 스타일 설정 */

/* 채팅 전체 레이아웃 */
.chat_title_section {
	height: 50px;
	border-bottom: 1px solid lightgray;
	padding-left: 15px;
	line-height: 50px;
}

.chat_section {
	padding-left: 15px;
	padding-right: 15px;
	overflow-x: hidden;
	overflow-y: auto;
	word-wrap: break-word;
	position: absolute;
	width: 100%;
	top: 50px;
	bottom: 130px;
}

.chat_input_section {
	height: 130px;
	width: 100%;
	position: absolute;
	bottom: 0px;
	border-top: 1px solid lightgray;
	padding: 10px;
}

/* 채팅창 섹션 */
.chat_date_box {
	position: relative;
	margin-bottom: 30px;
	padding-top: 30px;
	border-bottom: 1px solid #e4e8eb;
	text-align: center;
}

.chat_date_btn {
	position: absolute;
	top: 15px;
	border: 0;
	background-color: white;
	color: gray;
	margin-left: -100px;
}

.chat_date_btn:hover {
	cursor: default;
}

/* 채팅 내용 */
.chat_box {
	overflow: hidden;
	margin-bottom: 20px;
}

.profile {
	float: left;
	border-radius: 30px;
	width: 40px;
	height: 40px;
}

.profile_img {
	width: 100%;
}

.chat_box_in {
	margin-left: 10px;
	overflow: hidden;
	padding-left: 7px;
}

.name {
	font-weight: 600;
	font-size: 17px;
}

.chat {
	border-radius: 10px;
	background-color: #f0f0f0;
	margin: 5px;
	margin-left: 0px;
	float: left;
	clear: both;
	padding: 2px;
	padding-left: 7px;
	padding-right: 7px;
}

/* 채팅 타이틀 */
.chat_title {
	float: left;
	font-weight: 600;
	font-size: 20px;
}

.chat_person_num {
	float: left;
	padding-left: 10px;
	color: gray;
}

.chat_person_img {
	width: 13px;
	margin-bottom: 2px;
	margin-right: 2px;
}

/* 채팅 입력창 */
.chat_input_box {
	border: 1px solid gray;
	width: 100%;
	height: 100%;
	border: 1px solid lightgray;
	border-radius: 3px;
}

#send_btn {
	position: fixed;
	right: 10px;
	width: 70px;
	height: 50px;
	border: 0;
	border-radius: 2px;
	color: gray;
	margin-top: 30px;
	margin-right: 10px;
	margin-left: 10px;
}

#input {
	height: 107px;
	
	padding: 5px;
	padding-right: 3px;
	margin-right: 85px;
	
	overflow-x: hidden;
	overflow-y: auto;
}
</style>
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
                <div class="chat_section">
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
                            <div class=chat>내용1dddddd</div>
                            <div class=chat>내용1</div>
                            <div class=chat>내용1</div>
                        </div>
                    </div>
                    
                    <div class=chat_box>
                        <div class=profile>
                            <img src=resources/images/chatting/profile.png class=profile_img>
                        </div>
                        <div class=chat_box_in>
                            <div class=name>이름</div>
                            <div class=chat>내용1dddddd</div>
                            <div class=chat>내용1</div>
                            <div class=chat>내용1</div>
                        </div>
                    </div>
                    
                    <div class=chat_box>
                        <div class=profile>
                            <img src=resources/images/chatting/profile.png class=profile_img>
                        </div>
                        <div class=chat_box_in>
                            <div class=name>이름</div>
                            <div class=chat>내용1dddddd</div>
                            <div class=chat>내용1</div>
                            <div class=chat>내용1</div>
                        </div>
                    </div>
                    
                    <div class=chat_box>
                        <div class=profile>
                            <img src=resources/images/chatting/profile.png class=profile_img>
                        </div>
                        <div class=chat_box_in>
                            <div class=name>이름</div>
                            <div class=chat>내용1dddddd</div>
                            <div class=chat>내용1</div>
                            <div class=chat>내용1</div>
                        </div>
                    </div>
                    <div class=chat_box>
                        <div class=profile>
                            <img src=resources/images/chatting/profile.png class=profile_img>
                        </div>
                        <div class=chat_box_in>
                            <div class=name>이름</div>
                            <div class=chat>내용1dddddd</div>
                            <div class=chat>내용1</div>
                            <div class=chat>내용1</div>
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