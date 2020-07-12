<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>





	




	<div id="wrapper">

		<!-- 주간 스케쥴러 관련 스크랩트, calendarBox (모달은 별도) -->
		<jsp:include page="scheduler.jsp" />



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




	<!-- 주간 스케쥴러 관련 모달 -->
	<jsp:include page="modal.jsp" />


	<!-- Lobi List Default installation-->
	<script src="/resources/lobilist-master/lib/jquery/jquery.min.js"></script>
	<script src="/resources/lobilist-master/lib/jquery/jquery-ui.min.js"></script>
	<script
		src="/resources/lobilist-master/lib/jquery/jquery.ui.touch-punch-improved.js"></script>
	<!--<script
		src="/resources/lobilist-master/lib/bootstrap/js/bootstrap.min.js"></script>-->
	<script
		src="/resources/lobilist-master/dist/lobilist.js"></script>
	<script src="/resources/lobilist-master/lib/lobibox/js/lobibox.min.js"></script>
	<script
		src="/resources/lobilist-master/lib/highlight/highlight.pack.js"></script>
		
	<!--task.js는 로비리스트 기본 설치 태그 밑에 위치해야 동작! -->
	<script src='/resources/js/task/task.js'></script>
<script src='/resources/js/calendar/calendar.js'></script>
