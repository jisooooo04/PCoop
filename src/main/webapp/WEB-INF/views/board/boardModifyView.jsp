<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="C"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>

<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
	integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
	crossorigin="anonymous"></script>

<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
	integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
	crossorigin="anonymous">
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
	integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
	crossorigin="anonymous"></script>

<link
	href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.js"></script>
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.7.2/animate.min.css">
<script src="https://kit.fontawesome.com/a207991562.js"
	crossorigin="anonymous"></script>


<style>
.page-link {
	color: #e8e8e8;
} /* 색상 설정 추가 */
textarea {
	padding: 0.75em 1em;
}

body, input, select, textarea {
	color: #7f888f;
	font-family: "Open Sans", sans-serif;
	font-size: 13pt;
	font-weight: 400;
	line-height: 1.65;
}

@media screen and (max-width: 1680px) {
	body, input, select, textarea {
		font-size: 11pt;
	}
}

@media screen and (max-width: 1280px) {
	body, input, select, textarea {
		font-size: 10pt;
	}
}

@media screen and (max-width: 360px) {
	body, input, select, textarea {
		font-size: 9pt;
	}
}

textarea {
	-moz-appearance: none;
	-webkit-appearance: none;
	-ms-appearance: none;
	appearance: none;
	background: #ffffff;
	border-radius: 0.375em;
	border: none;
	border: solid 1px rgba(210, 215, 217, 0.75);
	color: inherit;
	display: block;
	outline: 0;
	padding: 0 1em;
	text-decoration: none;
	width: 100%;
}

input[type="text"]:invalid, input[type="password"]:invalid, input[type="email"]:invalid,
	input[type="tel"]:invalid, input[type="search"]:invalid, input[type="url"]:invalid,
	select:invalid, textarea:invalid {
	box-shadow: none;
}

input[type="text"]:focus, input[type="password"]:focus, input[type="email"]:focus,
	input[type="tel"]:focus, input[type="search"]:focus, input[type="url"]:focus,
	select:focus, textarea:focus {
	border-color: #f56a6a;
	box-shadow: 0 0 0 1px #e8e8e8;
}

/* contents - 카테고리 영역*/
.categories {
	margin: auto;
}

.title {
	font-size: 20px;
	font-weight: 600;
	color: #1a1943;
}

.category {
	width: 130px;
}

.text_category {
	font-size: 20px;
	font-weight: 600;
	height: 50px;
	line-height: 50px;
}

/* contents - 의뢰게시판 */
.advice {
	width: 550px;
	margin: auto;
}

.board>.list-group>li>a {
	text-decoration: none;
	color: black;
	text-align: left;
}

.badge {
	background-color: hotpink;
	color: white;
} /* new게시글 뱃지*/
.contents .row>div {
	padding-top: 30px;
}
</style>
<script>
	
	$(function() {

		$("form").on("submit", function() {
			var title = $("#title").val();
			var contents = $("#contents").val();
			var option = $("#category option:selected").val();
			var result = true;
			if (option == "") {
				alert("카테고리를 선택해주세요.");
				result = false;
			} else if (title == "") {
				alert("제목을 입력해주세요.");
				result = false;
			} else if (contents == "") {
				alert("내용을 작성해주세요.");
				result = false;
			}
			return result;
		})

		$("#clear").on("click", function() {
			$("#title").val("");
			$("#contents").val("");
			$("#category").val("");
		})

		
	})
</script>
</head>
<body>
	<div class="container">
		<div class=contents>
			<h3>게시판</h3>
			<form id="contentsform" action="update?seq=${read.seq}" method="post">
				
				<div class="row top">
					<div class="col-4 col-sm-3 category">
						<select class="custom-select" name="category" id="category">
							<option value="" selected>카테고리</option>
							<option value="공지사항">공지사항</option>
							<option value="프로젝트설명">프로젝트설명</option>
							
						</select>
					</div>
					<div class="col-8 col-sm-9 search-box">
						<input type="text" id="title" name="title"
							class="form-control input" placeholder="제목을 작성해주세요."
							aria-label="Recipient's username"
							aria-describedby="button-addon2" maxlength="100">
					</div>
					<div class="col-12">
						<textarea id="contents" name="contents" placeholder="내용을 작성해주세요."
							rows="14" maxlength="4000"></textarea>
					</div>
					<div class="col-12">


						<!-- Break -->
						<div class="col-12 text-right">
							<button type="submit" class="btn btn-secondary text-right">
								<i class="fas fa-pen-nib"></i> 등록하기
							</button>
							<button type="button" id="clear"
								class="btn btn-secondary text-right">
								<i class="fas fa-eraser"></i> 다시작성
							</button>
						</div>
					</div>
			</form>


		</div>
	</div>

</body>
</html>