<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<jsp:include page="../header/cdn.jsp"></jsp:include>

<script>
	$(function() {

		$(".backup").append("<ul id=root></ul>");

		// 디렉토리 가지고 오기
		var dirlist = ${dirlist};
		
		for (var i = 0; i < dirlist.length; i++) {

			var patharr = dirlist[i].path.split('/');
			var parent = "#root";
			
			for (var j = 2; j < patharr.length; j++) {

				if (j == patharr.length - 1)
					$(parent).append(
							"<ul id=" + patharr[j] + "><li class='dir' id='dir" + dirlist[i].seq +"'>"
									+ patharr[j] + "</li></ul>");
				else
					parent = "#" + patharr[j];

			}

		}

		// 파일 가지고 오기
		var filelist = ${filelist};

		for (var i = 0; i < filelist.length; i++) {

			var patharr = filelist[i].path.split('/');
			var parent = "#root";

			for (var j = 2; j < patharr.length; j++) {

				if (j == patharr.length - 1)
					$(parent)
							.append(
									"<ul><li class='file'>" + patharr[j]
											+ "</li></ul>");
				else
					parent = "#" + patharr[j];

			}

		}
		
		$(document).on("click", ".dir", function(e){
			var id = this.id;
			console.log(id);
			var left = $("#" + id).offset().left;
			var top = $("#" + id).offset().top + 30;
			   
			//Display contextmenu:
				$(".add_dir").css({
				  "left": left,
				  "top": top
				});
			
			$(".contextmenu").css({
			  "left": left,
			  "top": top
			}).show();
			
			$(".menu_dir").attr("id", id);

			//Prevent browser default contextmenu.
			return false;
		})
		
		
		//Hide contextmenu:
		  $(document).click(function(){
		    $(".contextmenu").hide();
		  });
		
		$(".menu_dir").on("click", function(){
			
			var id = this.id;
			var left = $("#" + id).offset().left;
			var top = $("#" + id).offset().top + 30;
			
			$(".add_dir").css({
				  "left": left,
				  "top": top
				}).show();
			
			$(".contextmenu").hide();
		})
		
		// 새 디렉토리 추가
		$("#ok").on("click", function(){
			
			var parent_seq = $(".menu_dir").attr("id").substring(3);
			var name = $("#dir_name").val();
			var data = {
					parent_seq: parent_seq,
					name: name,
			};
			
			$.ajax({
				url: "addDirectory",
				type: "POST",
				data: data,
				success: function(data){
					$("#dir" + parent_seq).append(
							"<ul><li class='dir' id='dir" + data + "'>"
							+ name + "</li></ul>");
				}
			});
			$(".add_dir").hide();
			$("#dir_name").val("");

		})
		
		// 새 디렉토리 추가 취소
		$("#cancel").on("click", function(){
			$(".add_dir").hide();

		})
		
		
	})
	
</script>
<link rel="stylesheet" href="resources/css/backup/filelist.css?after" />

</head>
<body>
	<jsp:include page="../header/header.jsp"></jsp:include>
	<!-- 왼쪽 사이드바 -->
	<jsp:include page="../header/sidebar-left.jsp"></jsp:include>

	<section>

		<div id="container">

			<!-- 여기부터 각자 영역 설정 -->
			<ul class="contextmenu">
				<li class="menu_dir"><a href="#">하위 디렉토리 추가</a></li>
				<li class="menu_file"><a href="#">파일 업로드</a></li>
			</ul>
			
			<div class="add_dir">
				<input type="text" id="dir_name" placeholder="새 디렉토리 이름">
				<input type="button" id="ok" value="OK">
				<input type="button" id="cancel" value="취소">
			</div>

			<!-- 여기까지 각자 영역 설정 -->
		</div>

	</section>
</body>
</html>