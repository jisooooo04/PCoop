<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link href='/resources/css/task/task.css' rel='stylesheet' />
   	<!--Error: Bootstrap dropdown require Popper.js -- 상단부에 있어야 리스트 색 변경시 에러가 안남 -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" ></script>
	

<!-- Jquery -->
<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
<!-- 부트스트랩 -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<script
   src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
<!-- 아이콘 -->
<script src="https://kit.fontawesome.com/8f6ea3bf70.js"
	crossorigin="anonymous"></script>
 <!-- 구글 폰트 -->
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400;500&display=swap" rel="stylesheet">	

<!-- Lobi List Default installation-->
<link rel="stylesheet"
	href="/resources/lobilist-master/lib/jquery/jquery-ui.min.css" />
<link rel="stylesheet"
	href="/resources/lobilist-master/lib/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="/resources/lobilist-master/dist/lobilist.min.css">
<link rel="stylesheet"
	href="/resources/lobilist-master/lib/lobibox/css/lobibox.min.css">
<link rel="stylesheet"
	href="/resources/lobilist-master/lib/highlight/github.css">
<!--<link rel="stylesheet" href="resources/lobilist-master/demo/demo.css">-->

<style>


</style>


</head>


<body>

	


	<section id="box">






		<div id="wrapper">

		<!-- 주간 스케쥴러 관련 스크랩트, calendarBox, 관련 모달 -->
<jsp:include page="scheduler.jsp"/>





			<!--진행률 바-->
			<div class="progress" id="progress">
				<div id="selector" class="progress-bar progress-bar-striped active"
					role="progressbar" aria-valuenow="60" aria-valuemin="0"
					aria-valuemax="100" style="width:${bar}%">
					<span class="sr-only">45% Complete</span>
				</div>
			</div>

		</div>
		<div id="todoListBox">

			<!--Actions by ajax		-->
			<div id="actions-by-ajax"></div>


			<!--불러올 데이터 없을 때 생성 버튼	-->
			<div id="create_list" style="display: none;">
				<button id="create_btn" type="button" class="btn btn-default btn-xs">
					<i class="glyphicon glyphicon-plus"></i>
				</button>
			</div>

		</div>

		</div>



	</section>







<script src='/resources/js/task/task.js'></script>

	<!-- Lobi List Default installation-->
	<script src="/resources/lobilist-master/lib/jquery/jquery.min.js"></script>
	<script src="/resources/lobilist-master/lib/jquery/jquery-ui.min.js"></script>
	<script
		src="/resources/lobilist-master/lib/jquery/jquery.ui.touch-punch-improved.js"></script>
	<script
		src="/resources/lobilist-master/lib/bootstrap/js/bootstrap.min.js"></script>

	<script
		src="/resources/lobilist-master/dist/lobilist.js?v=<%=System.currentTimeMillis()%>"></script>

	<script src="/resources/lobilist-master/lib/lobibox/js/lobibox.min.js"></script>
	<script
		src="/resources/lobilist-master/lib/highlight/highlight.pack.js"></script>
	<!-- <script src="resources/lobilist-master/demo/demo.js"></script>-->


		<!-- 주간 스케쥴러 관련 스크랩트, calendarBox, 관련 모달 -->
<jsp:include page="modal.jsp"/>


</body>
</html>