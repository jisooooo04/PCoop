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
			<button id="create_btn" type="button" class="btn btn-default btn-xs"><i class="glyphicon glyphicon-plus"></i>리스트 생성</button>
		</div>


			<!--진행률 바-->
			<div class="progress" id="progress">
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
			
			 
			$('#actions-by-ajax').lobiList({

			});
			
			 var list;
			$('#create_btn').click(function () {
				
			    list = $('#actions-by-ajax').lobiList({
			        init: function () {
			            Lobibox.notify('default', {
			                msg: 'init'
			            });
			        },
			        beforeDestroy: function () {
			            Lobibox.notify('default', {
			                msg: 'beforeDestroy'
			            });
			        },
			        afterDestroy: function () {
			            Lobibox.notify('default', {
			                msg: 'afterDestroy'
			            });
			        },
			        beforeListAdd: function () {
			            Lobibox.notify('default', {
			                msg: 'beforeListAdd'
			            });
			        },
			        afterListAdd: function () {
			            Lobibox.notify('default', {
			                msg: 'afterListAdd'
			            });
			        },
			        beforeListRemove: function (list) {
			            Lobibox.notify('default', {
			                msg: 'beforeListRemove'
			            });
			        },
			        afterListRemove: function () {
			            Lobibox.notify('default', {
			                msg: 'afterListRemove'
			            });
			        },
			        beforeItemAdd: function () {
			            Lobibox.notify('default', {
			                msg: 'beforeItemAdd'
			            });
			        },
			        afterItemAdd: function () {
			            Lobibox.notify('default', {
			                msg: 'afterItemAdd'
			            });
			        },
			        beforeItemUpdate: function () {
			            Lobibox.notify('default', {
			                msg: 'beforeItemUpdate'
			            });
			        },
			        afterItemUpdate: function () {
			            Lobibox.notify('default', {
			                msg: 'afterItemUpdate'
			            });
			        },
			        beforeItemDelete: function () {
			            Lobibox.notify('default', {
			                msg: 'beforeItemDelete'
			            });
			        },
			        afterItemDelete: function () {
			            Lobibox.notify('default', {
			                msg: 'afterItemDelete'
			            });
			        },
			        beforeListDrop: function () {
			            Lobibox.notify('default', {
			                msg: 'beforeListDrop'
			            });
			        },
			        afterListReorder: function () {
			            Lobibox.notify('default', {
			                msg: 'afterListReorder'
			            });
			        },
			        beforeItemDrop: function () {
			            Lobibox.notify('default', {
			                msg: 'beforeItemDrop'
			            });
			        },
			        afterItemReorder: function () {
			            Lobibox.notify('default', {
			                msg: 'afterItemReorder'
			            });
			        },
			        afterMarkAsDone: function () {
			            Lobibox.notify('default', {
			                msg: 'afterMarkAsDone'
			            });
			        },
			        afterMarkAsUndone: function () {
			            Lobibox.notify('default', {
			                msg: 'afterMarkAsUndone'
			            });
			        },
			    	 lists: [
			             {
			                 title: 'TODO',
			                 defaultStyle: 'lobilist-info',
			                 items: [
			                     {
			                         title: 'Floor cool cinders',
			                         description: 'Thunder fulfilled travellers folly, wading, lake.',
			                         dueDate: '2015-01-31'
			                     },
			                     {
			                         title: 'Periods pride',
			                         description: 'Accepted was mollis',
			                         done: true
			                     },
			                     {
			                         title: 'Flags better burns pigeon',
			                         description: 'Rowed cloven frolic thereby, vivamus pining gown intruding strangers prank ' +
			                         'treacherously darkling.'
			                     },
			                     {
			                         title: 'Accepted was mollis',
			                         description: 'Rowed cloven frolic thereby, vivamus pining gown intruding strangers prank ' +
			                         'treacherously darkling.',
			                         dueDate: '2015-02-02'
			                     }
			                 ]
			             }
			         ]
			    })
			    .data('lobiList');
				});
			
			
			

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

			
			if ($("#actions-by-ajax").find('.lobilist').text() == "") {
				console.log("남은 리스트가 없음");
				$("#create_list").css('display', 'inline-block');
				$("#progress").css('display', 'none');
				
			} else {
				console.log("남은 리스트가 있음3");
				$("#create_list").css('display', 'none');
			}



		});
	</script>
</body>
</html>