<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="../header/cdn.jsp"></jsp:include>
<script>
	$(function() {
		
		$(".backup").append("<ul id=root></ul>");

		 // 디렉토리 가지고 오기
			var dirlist = ${dirlist};
			for(var i = 0 ; i < dirlist.length ; i++){
				
				var patharr = dirlist[i].path.split('/');
				var parent = "#root";
				
				for(var j = 2 ; j < patharr.length ; j++){
					
					if(j == patharr.length - 1)
						$(parent).append("<ul id=" + patharr[j] + "><li class='dir'>" + patharr[j] + "</li></ul>");
					else parent = "#" + patharr[j];
					
				}
				
			}
			
		// 파일 가지고 오기
			var filelist = ${filelist};
			
			for(var i = 0 ; i < filelist.length ; i++){
				
				console.log(filelist[i].path);
				var patharr = filelist[i].path.split('/');
				var parent = "#root";
				
				for(var j = 2 ; j < patharr.length ; j++){
					console.log(parent);
					if(j == patharr.length - 1)
						$(parent).append("<ul><li class='file'>" + patharr[j] + "</li></ul>");
					else parent = "#" + patharr[j];
					
				}
				
			}
	})
</script>
<style>
.dir {
	color: blue;
}

.file {
	color: red;
}
</style>
</head>
<body>
	<jsp:include page="../header/header.jsp"></jsp:include>
	<!-- 왼쪽 사이드바 -->
	<jsp:include page="../header/sidebar-left.jsp"></jsp:include>
	
	<section>

		<div id="container">
			<!-- 여기부터 각자 영역 설정 -->
			

			<!-- 여기까지 각자 영역 설정 -->
		</div>

	</section>
</body>
</html>