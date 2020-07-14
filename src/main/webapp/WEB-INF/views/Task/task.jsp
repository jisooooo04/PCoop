<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>



<!--Error: Bootstrap dropdown require Popper.js -- 상단부에 있어야 리스트 색 변경시 에러가 안남 -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<jsp:include page="../header/cdn.jsp" />

<link href='/resources/css/calendar/calendar.css?after' rel='stylesheet' />

<!-- Lobi List Default installation-->
<link rel="stylesheet"
	href="/resources/lobilist-master/lib/jquery/jquery-ui.min.css" />
<link rel="stylesheet"
	href="/resources/lobilist-master/lib/bootstrap/css/bootstrap.min.css" />
<!-- lib/bootstrap/css/bootstrap.min.css 폰트 축소 원인-->
<link rel="stylesheet"
	href="/resources/lobilist-master/dist/lobilist.min.css">
<link rel="stylesheet"
	href="/resources/lobilist-master/lib/highlight/github.css">

<link rel="stylesheet" href="/resources/css/task/task.css?after">

</head>


<body>

				
	<!-- Header -->
	<jsp:include page="../header/header.jsp"></jsp:include>
	<jsp:include page="../header/sidebar-left.jsp"></jsp:include>

	
	<section id="box">
		<div id="container">
		
			<div id="wrapper">

	
				<!-- 주간 스케줄러 -->
				<jsp:include page="scheduler.jsp"></jsp:include>
			
				<!--진행률 바-->
				<div class="progress" id="progress">
					<!--스케쥴러 토글 버튼-->
						<button id="toggle_btn" type="button" class="btn btn-default btn-xs">
							<i id="toggle_i" class="glyphicon glyphicon-arrow-down"></i>
						</button>
						
					<div id="selector" class="progress-bar progress-bar-striped active"
						role="progressbar" aria-valuenow="60" aria-valuemin="0"
						aria-valuemax="100" style="width:${bar}%">
						<span class="sr-only">45% Complete</span>
					</div>
				</div>
			</div>

			<div id="todoListBox">
				<!--불러올 데이터 없을 때 생성 버튼	-->
				<div id="create_list" style="display: none;">
					<button id="create_btn" type="button"
						class="btn btn-default btn-xs">
						<i class="glyphicon glyphicon-plus"></i>
					</button>
				</div>

				<!--Actions by ajax		-->
				<div id="actions-by-ajax" class="u-fancy-scrollbar"></div>

			</div>
		</div>

	</section>

	
		<!-- Lobi List Default installation-->
	<script src="/resources/lobilist-master/lib/jquery/jquery.min.js"></script>
	<script src="/resources/lobilist-master/lib/jquery/jquery-ui.min.js"></script>
	<script
		src="/resources/lobilist-master/lib/jquery/jquery.ui.touch-punch-improved.js"></script>
	<!--<script
		src="/resources/lobilist-master/lib/bootstrap/js/bootstrap.min.js"></script>-->
	<script
		src="/resources/lobilist-master/lib/bootstrap/js/bootstrap.min.js"></script>
	<script
		src="/resources/lobilist-master/dist/lobilist.js?v=<%=System.currentTimeMillis()%>"></script>
	<script src="/resources/lobilist-master/lib/lobibox/js/lobibox.min.js"></script>
	<script
		src="/resources/lobilist-master/lib/highlight/highlight.pack.js"></script>
	<!-- <script src="resources/lobilist-master/demo/demo.js"></script>-->

	<script src="/resources/js/task/task.js"></script>
<script src='/resources/js/calendar/calendar.js'></script>

	

</body>
	<!-- 주간 스케쥴러 관련 모달 : 자식요소의 z-index가 높아도 부모 z-index에 속한다. -->
	<jsp:include page="modal.jsp"></jsp:include>
</html>
