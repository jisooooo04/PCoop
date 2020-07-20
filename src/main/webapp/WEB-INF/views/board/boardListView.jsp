<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="C"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width" initial-scale="1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>



<style>
body {
	overflow-y: hidden;
	overflow-x: hidden;
}

.container {
	padding-top: 150px;
}

.text-center {
	padding-right: 30px;
}

.search_section {
	margin-top: 30px;
	margin-bottom: 20px;
}

.search_gap {
	padding: 0px;
}

.search_box {
    border: 2px solid #C3C3C3;
	width: 100%;
	height: 150px;
	background-color: #7dc8c9;
	padding: 25px;
	padding-left: 50px;
	text-align: center;
}

.search_title {
	margin-bottom: 5px;
	font-size: 24px;
	color: dimgray;
}

.search_btn {
	border: 1px solid gray;
}

.col-10 search_box {
	background-color: #7dc8c9;
}
#option{
width: 50px;
}
.title{
width: 350px;
}


</style>
</head>
<body>


	<jsp:include page="../header/board-header.jsp"></jsp:include>

	<div class="container" style="height: 100%;">
		
		<div class="search_section row">
			
			<div class="search_box">
				<form action="search" method="post">
					<div class=search_title style="color: white;">공지사항</div>
					<br>
					
					
					<select id="option">
						<option value="사용방법" >제목</option>
					</select> <input name="title" type=text class="title">
					<button type=submit class="search_btn">검색</button>
				</form>
			</div>
			
		</div>
		<br> <br>
		<div class="row">
			<table class="table table-hover">
				<thead>

					<tr>
						<th
							style="background-color: #7dc8c9; text-align: center; color: white;">번호</th>
						<th
							style="background-color: #7dc8c9; text-align: center; color: white;">카테고리</th>
						<th
							style="background-color: #7dc8c9; text-align: center; color: white;">제목</th>
						<th
							style="background-color: #7dc8c9; text-align: center; color: white;">내용</th>
						<th
							style="background-color: #7dc8c9; text-align: center; color: white;">작성자</th>
						<th
							style="background-color: #7dc8c9; text-align: center; color: white;">작성일</th>

					</tr>

				</thead>

				<tbody>
					<C:forEach items="${list}" var="list">

						<tr style="text-align: center;">

							<td><C:out value="${list.seq}" /></td>
							<td><C:out value="${list.category}" /></td>

							<td><a href="toBoardContents?seq=${list.seq}"> <C:out
										value="${list.title}" /></a></td>
							<td><C:out value="${list.contents}" /></td>

							<td><C:out value="${list.writer}" /></td>

							<td><fmt:formatDate value="${list.write_date}"
									pattern="yyyy-MM-dd" /></td>

						</tr>

					</C:forEach>
				</tbody>




			</table>
		</div>

		<C:choose>
			<C:when test="${loginInfo.email == id}">

				<div class="col-12" align=right>


					<a href="toBoardWrite" class="btn btn-primary">글쓰기</a>

				</div>
			</C:when>
		</C:choose>

		<br>


		<!-- contents 영역 끝 -->
		<div class="text-center">
			<ul class="pagination">

				<div class="text-center">
					<ul class="pagination">${pageNavi}

					</ul>

				</div>



			</ul>

		</div>
	</div>
	<!--container 끝-->
</body>
</html>