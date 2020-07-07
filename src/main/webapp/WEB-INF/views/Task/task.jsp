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
		
					<!--진행률 바-->
			<div class="progress" id="progress">
				<div id="selector" class="progress-bar progress-bar-striped active"
					role="progressbar" aria-valuenow="60" aria-valuemin="0"
					aria-valuemax="100" style="width:${bar}%">
					<span class="sr-only">45% Complete</span>
				</div>
			</div>

			<!--불러올 데이터 없을 때 생성 버튼	-->
			<div id="create_list" style="display: none;">
				<button id="create_btn" type="button" class="btn btn-default btn-xs">
					<i class="glyphicon glyphicon-plus"></i>
				</button>
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

	<script
		src="/resources/lobilist-master/dist/lobilist.js?v=<%=System.currentTimeMillis()%>"></script>

	<script src="/resources/lobilist-master/lib/lobibox/js/lobibox.min.js"></script>
	<script
		src="/resources/lobilist-master/lib/highlight/highlight.pack.js"></script>
	<!-- <script src="resources/lobilist-master/demo/demo.js"></script>-->

	<script>
		$(function() {
			
			
			function isEmpty(param) {
				  return Object.keys(param).length === 0;
			}

			// 할일 리스트들 양식대로 불러오기 전에 데이터 존재 여부 체크
			$.ajax({
				type : 'get',
				url : '/Task/TaskAjax',
				datatype : 'json',
				success : function(data) {			
					if(isEmpty(data.lists)){
						console.log('lists가 데이터베이스에 없음!');

				          $('#actions-by-ajax').lobiList({
				                actions: {
				                    load: '/resources/lobilist-master/demo/example1/load.json',
				                    insert: '',
				                    delete: '',
				                    update: ''
				                },
				                afterItemAdd: function(){
				                    console.log(arguments);
				                },			                
				                afterListRemove: function(){
				            		console.log("afterListRemove 변화 감지!");
				    				if ($("#actions-by-ajax").find('.lobilist').text() == "") {
				    				console.log("버튼 보여라!!!");
				    				$("#create_list").css('display', 'inline-block');
				    			} else {
				    				console.log("버튼 숨겨라!!!");
				    				$("#create_list").css('display', 'none');
				    			}	
				    						                }			                
				            });
					
					}else{
						console.log('lists가 데이터베이스에 존재!');					
						 $('#actions-by-ajax').lobiList({   
				                afterListRemove: function(){
				            		console.log("afterListRemove 변화 감지!");

				    				if ($("#actions-by-ajax").find('.lobilist').text() == "") {
				    				console.log("버튼 보여라!!!");
				    				$("#create_list").css('display', 'inline-block');
				    				} else {
				    				console.log("버튼 숨겨라!!!");
				    				$("#create_list").css('display', 'none');
				    				}
				    			},
				    			afterItemDelete: function(){
				    				//ajax 코드 추가 작업진행바 (아이템 삭제시)
				    				$.ajax({
				    					type : 'get',
				    					url : '/Task/selectCount',
				    					datatype : 'json',
				    					success : function(data) {
				    						//console.log('작업진행률 : '+ data.to);
				    						$('#selector').css('width', data.to + '%');

				    					},
				    					error : function(error) {
				    						alert('data error');
				    					}
				    				});
				    				
				    			},
				                afterItemAdd: function(){
				                	//ajax 코드 추가 작업진행바 (아이템 추가시)
				    				$.ajax({
				    					type : 'get',
				    					url : '/Task/selectCount',
				    					datatype : 'json',
				    					success : function(data) {
				    						console.log('작업진행률 : '+ data.to);
				    						$('#selector').css('width', data.to + '%');

				    					},
				    					error : function(error) {
				    						alert('data error');
				    					}
				    				});
				                }
				    			
				    			
				    			
						    });	          
					}

				},
				error : function(error) {
					alert('data error');
				}
			});
			
	
			//ajax 코드 추가 작업진행바 (처음 불러올때)
			$.ajax({
				type : 'get',
				url : '/Task/selectCount',
				datatype : 'json',
				success : function(data) {
					console.log('작업진행률 : '+ data.to);
					$('#selector').css('width', data.to + '%');

				},
				error : function(error) {
					alert('data error');
				}
			});
			
			


			
			$('#create_btn').click(function (){
				console.log("버튼 클릭!");
				 //location.reload(); // 버튼 누르면 새로고침 ... 개선필요...
				 history.go(0);
			/*
				$.ajax({
					type : 'get',
					url : '/Task/TaskAjax',
					datatype : 'json',
					success : function(data) {			
				
						if(isEmpty(data.lists)){
							console.log('lists가 데이터베이스에 없음 (버튼)');
					          $('#actions-by-ajax').lobiList({
					                actions: {
					                    load: '/resources/lobilist-master/demo/example1/load.json',
					                    insert: '',
					                    delete: '',
					                    update: ''
					                },
					                afterItemAdd: function(){
					                    console.log(arguments);
					                },
					                
					                afterListRemove: function(){
					            		console.log("afterListRemove 변화 감지!");
					    				if ($("#actions-by-ajax").find('.lobilist').text() == "") {
					    				console.log("버튼 보여라!!!");
					    				$("#create_list").css('display', 'inline-block');
					    				} else {
					    				console.log("버튼 숨겨라!!!");
					    				$("#create_list").css('display', 'none');
					    				}	
					    			}
					                
					            });
					          
					          
							
						}else{
							console.log('lists가 데이터베이스에 존재(버튼)');
							
							 $('#actions-by-ajax').lobiList({

					                afterListRemove: function(){
					            		console.log("afterListRemove 변화 감지!");

					    				if ($("#actions-by-ajax").find('.lobilist').text() == "") {
					    				console.log("버튼 보여라!!!");
					    				$("#create_list").css('display', 'inline-block');
					    			} else {
					    				console.log("버튼 숨겨라!!!");
					    				$("#create_list").css('display', 'none');
					    			}
					    				}
							 				 
							    });				 	 
					          
						};

					},
					error : function(error) {
						alert('data error');
					}
				});
					
	*/
				});

	            
			});
	</script>
</body>
</html>