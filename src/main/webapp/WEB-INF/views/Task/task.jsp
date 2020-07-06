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





	<section id="box">



		<!--Actions by ajax		-->

		<div id="actions-by-ajax">

			<!--불러올 데이터 없을 때 생성 버튼	-->
			<div id="create_list" style="display: none;">
				<button id="create_btn" type="button" class="btn btn-default btn-xs">
					<i class="glyphicon glyphicon-plus"></i>
				</button>
			</div>


			<!--진행률 바-->
			<div class="progress" id="progress" style="display: inline-block;">
				<div id="selector" class="progress-bar progress-bar-striped active"
					role="progressbar" aria-valuenow="60" aria-valuemin="0"
					aria-valuemax="100" style="width:${bar}%">
					<span class="sr-only">45% Complete</span>
				</div>
			</div>


		</div>




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
		$(function() {
			var list;
			
			
		
				 $('#actions-by-ajax').lobiList({
					 
				    });
	
			
			

			// 값이 없을 때 리스트 버튼 보이게
			if ($("#actions-by-ajax").find('.lobilist').text() == "") {
				console.log("남은 리스트가 없음");
				$("#create_list").css('display', 'inline-block');
				$("#progress").css('display', 'none');

			} else {
				console.log("남은 리스트가 있음");
				$("#create_list").css('display', 'none');
				$("#progress").css('display', 'inline-block');
				
				//ajax 코드 추가 작업진행바
				$.ajax({
					type : 'get',
					url : '/Task/selectCount',
					datatype : 'json',
					success : function(data) {
						console.log(data.to);
						$('#selector').css('width', data.to + '%');

					},
					error : function(error) {
						alert('data error');
					}
				});
				
			}
			
			
	
			$('#create_btn').click(function (){
				console.log("버튼 클릭!");
	            
				
				//$('#actions-by-ajax').val('');
			 //$('#actions-by-ajax').removeClass();
			 
			          $('#actions-by-ajax').lobiList({
			                actions: {
			                    load: '/resources/lobilist-master/demo/example1/load.json',
			                    insert: '',
			                    delete: '',
			                    update: ''
			                },
			                afterItemAdd: function(){
			                    console.log(arguments);
			                }
			            });
			
	  
		
					console.log("데모 추가 후 남은 리스트가 있음");
					//$("#create_list").css('display', 'none');
					//$("#progress").css('display', 'inline-block');
					
					//ajax 코드 추가 작업진행바
					$.ajax({
						type : 'get',
						url : '/Task/selectCount',
						datatype : 'json',
						success : function(data) {
							console.log(data.to);
							$('#selector').css('width', data.to + '%');

						},
						error : function(error) {
							alert('data error');
						}
					});
					
				});
	            
	            
			});


	</script>
</body>
</html>