<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="C"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
	integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
	crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
	integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
	crossorigin="anonymous"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
	integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
	crossorigin="anonymous"></script>
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.7.2/animate.min.css">
<script src="https://kit.fontawesome.com/a207991562.js"
	crossorigin="anonymous"></script>
</head>
<style>
.container {
   border: 2px solid black;
	background-color: white;
}

body {
	background-color: #fafafa;
}

.footer {
	background-color: white;
}

.board-info {
	height: 60px;
	line-height: 60px;
	background-color: #868e96;
}

.page-link {
	color: #616161;
} /* 색상 설정 */
.btn {
	height: 40px;
}

.table-wrapper>.row>div {
	padding-left: 0px;
	padding-right: 0px;
}

#box1 {
	width: 100%;
	height: 200px;
	border: 2px solid #e8e8e8;
	float: left;
	overflow-y: auto;
}

#writeCmt>.row {
	background-color: white;
}

.contents div {
	padding-top: 10px;
}

.comments {
	padding: 10px;
	background-color: #e8e8e8;
} /*변호사 comments 영역*/
.comments_contents {
	border-bottom: 1px solid #dddddd;
}

.comments_contents>.row>div {
	background-color: #fafafa;
}

.comments_contents>div {
	padding-top: 10px;
}

.all>div {
	padding-top: 10px;
}

#req_category {
	font-size: 8pt;
}

#req_title>a {
	font-size: 16pt;
	text-decoration: none;
	color: black;
}

#req_contents {
	font-size: 12pt;
	padding: 30px;
}

#req_date {
	font-size: 10pt;
}
</style>
<script>
            $(function(){
        		$("#back").on("click", function() {
					location.href = "list";
        		})
            })
           
</script>
<body>

	<div class="container">
		<div class=contents>
			<div class="row all">
				<div class="col-12 text-left" id="req_category">${read.category}</div>
				<div class="col-12 text-left" id="req_title">
					<a href="#" class="logo">${read.title}</a>
				</div>
				
				<div class="col-12 text-left" id="req_date">운영자 : ${read.writer}</div>
				<div class="col-12 text-left" id="req_contents">
					${read.contents}</div>
					
					
				
				
								
				
				<div class="col-12 text-right">
					<button type=button class="btn btn-secondary" id=back>
						<i class="fas fa-arrow-left"></i>
					</button>
					<C:choose>
						<C:when test="${loginInfo.email == read.writer}">
							<a onclick="return confirm('정말로 삭제하시겠습니까?')" href="delete?seq=${read.seq}" class="btn btn-primary">삭제</a>
							<a href="updatefomr?seq=${read.seq}" class="btn btn-primary">수정</a>


						</C:when>
					</C:choose>
				</div>
				
			</div>
			
		</div>
	</div>

</body>
</html>