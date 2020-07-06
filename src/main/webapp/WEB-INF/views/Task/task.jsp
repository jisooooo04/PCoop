<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.5.1.js"></script>



<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

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
#box {
	margin: 10px 0px 0px 10px;
}
</style>


</head>
<body>



	<!-- Header -->
	<jsp:include page="../header/header.jsp"></jsp:include>
	<!-- 왼쪽 사이드바 -->
	<jsp:include page="../header/sidebar-left.jsp"></jsp:include>



	<!--진행률 바-->
	<div class="progress">
		<div id="selector" class="progress-bar progress-bar-striped active"
			role="progressbar" aria-valuenow="60" aria-valuemin="0"
			aria-valuemax="100" style="width:${bar}%">
			<span class="sr-only">45% Complete</span>
		</div>
	</div>
	<section id="box">




		<!--불러올 데이터 없을 때 생성 버튼	-->
		<div id="create_btn" style="display: none;">
			<button type="button" class="btn btn-success">리스트 생성</button>
		</div>



		<!--Actions by ajax		-->

		<div id="actions-by-ajax"></div>





	</section>


	<!-- Lobi List Default installation-->
	<script src="/resources/lobilist-master/lib/jquery/jquery.min.js"></script>
	<script src="/resources/lobilist-master/lib/jquery/jquery-ui.min.js"></script>
	<script
		src="/resources/lobilist-master/lib/jquery/jquery.ui.touch-punch-improved.js"></script>
	<script
		src="/resources/lobilist-master/lib/bootstrap/js/bootstrap.min.js"></script>
	<script src="/resources/lobilist-master/dist/lobilist.js"></script>

	<script src="/resources/lobilist-master/lib/lobibox/js/lobibox.min.js"></script>
	<script
		src="/resources/lobilist-master/lib/highlight/highlight.pack.js"></script>
	<!-- <script src="resources/lobilist-master/demo/demo.js"></script>-->

	<script>
   $(function () {
      

				if ( $('#actions-by-ajax').html == null) {
					$("#create_btn").css('display', 'inline-block');
				} else {
					$("#create_btn").css('display', 'none');
				}
			


              
       $('#actions-by-ajax').lobiList({
           
       });
    
		  $.ajax({
	            type: 'get',
	            url: 'selectCount',
	            datatype: 'json',
	            success: function(data){
	                alert('data load ' + data);
	                console.log(data.to);
	                
	                $('#selector').css('width', data.to+'%');
	               
	               
	            },
	            error: function (error) {
	                alert('data error');
	            }
	        });
    
   });
   </script>
</body>
</html>