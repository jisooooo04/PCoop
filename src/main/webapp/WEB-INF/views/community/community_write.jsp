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
header {
	height: 80px;
	border: 1px solid black;
}

footer {
	height: 80px;
	border: 1px solid black;
}

section {
	margin: auto;
}

.gap {
	padding: 0px;
}

.contents_section {
	padding: 0px;
}

/* 공통 div 설정 */
.box {
	overflow: hidden;
	margin-top: 10px;
	margin-bottom: 10px;
}

.menu {
	float: left;
	width: 100px;
}

.input {
	float: left;
	width: 100%;
}

hr {
	background-color: black;
	height: 3px;
	margin-bottom: 20px;
}

.header {
	height: 50px;
	line-height: 70px;
	font-size: 20px;
}

.footer {
	text-align: center;
	margin: 20px;
}

/* 컨텐츠 작성 영역 */
.title, .link {
	width: 100%;
}

.checkbox {
	margin-left: 10px;
	margin-right: 5px;
}

.contents {
	border: 1px solid black;
	min-height: 300px;
	margin-top: 20px;
	margin-bottom: 20px;
}

button {
	border: 1.5px solid black;
	background-color: white;
	border-radius: 15px;
	margin-left: 5px;
	margin-right: 5px;
	width: 100px;
	height: 40px;
	font-weight: 600;
}

button:hover {
	background-color: #f0f0f0;
}
</style>

<script>
	$(function(){
		
		//대표이미지 = img/png인지 확인
		//아니면 첨부한거 지우고 알림띄우기
		
		$(".submit").on("click", function(){
			//제목, 카테고리 1개이상, 대표이미지, 글작성 했는지 확인
			
		})
		
	})
</script>
</head>
<body>
	<header>헤더</header>

	<section>
		<div class=row>

			<div class="gap col-1"></div>

			<div class="contents_section col-10">
				<div class=header>게시글 작성</div>

				<hr>

				<form action="community_write" method=post
					enctype="multipart/form-data">

					<div class="box row">
						<div class="menu col-md-2 col-3">제목</div>
						<div class="input col-md-10 col-9">
							<input type=text name=title class=title placeholder="제목을 입력해주세요.">
						</div>
					</div>

					<div class="box row">
						<div class="menu col-md-2 col-3">카테고리</div>
						<div class="input col-md-10 col-9">
							<input type=checkbox name=category value=웹사이트 class=checkbox
								style="margin-left: 0px;">웹사이트 <input type=checkbox
								name=category value=Android class=checkbox>Android <input
								type=checkbox name=category value=iOS class=checkbox>iOS
							<input type=checkbox name=category value=JAVA class=checkbox>JAVA
							<input type=checkbox name=category value=HTML class=checkbox>HTML
							<input type=checkbox name=category value=기타 class=checkbox>기타
						</div>
					</div>

					<div class="box row">
						<div class="menu col-md-2 col-3">링크</div>
						<div class="input col-md-10 col-9">
							<input type=text name=link class=link placeholder="링크를 입력해주세요.">
						</div>
					</div>

					<div class="box row">
						<div class="menu col-md-2 col-3">대표 이미지</div>
						<div class="input col-md-10 col-9">
							<input type=file name=files>
						</div>
					</div>

					<div class="box row">
						<div class="menu col-md-2 col-3">파일첨부</div>
						<div class="input col-md-10 col-9">
							<input type=file name=files>
						</div>
					</div>

					<div class=contents contenteditable="true"></div>


					<hr>

					<div class=footer>
						<button type=submit class=submit>작성완료</button>
						<button type=button onclick="location.href='community'">목록으로</button>
					</div>


				</form>
			</div>
			<!-- section col-10 종료 -->

			<div class="gap col-1"></div>

		</div>
	</section>

	<footer>푸터</footer>
</body>
</html>