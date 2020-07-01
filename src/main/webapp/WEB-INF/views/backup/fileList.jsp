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

		$(".backup").append("<ul id=dir4 class='root dir'>TEMP</ul>");

		// 디렉토리 가지고 오기
		var dirlist = ${dirlist};
		printDirList(dirlist);
		
		// 파일 가지고 오기
		var filelist = ${filelist};
		// printFileList(filelist);
		
	})
	
</script>
<link rel="stylesheet" href="resources/css/backup/filelist.css?after" />

</head>
<body>
	<jsp:include page="../header/header.jsp"></jsp:include>
	<jsp:include page="../header/sidebar-left.jsp"></jsp:include>

	<section>

		<div id="container">

			<div class="files"></div>

			<!-- 여기까지 각자 영역 설정 -->
		</div>

	</section>

	<!-- 디렉토리 우 클릭 시, 나타나는 드롭 다운 메뉴 -->
	<ul class="contextmenu">
		<li class="menu_add_dir"><a href="#">하위 디렉토리 추가</a></li>
		<li class="menu_delete_dir"><a href="#">디렉토리 삭제</a></li>
	</ul>

	<ul class="contextmenu_container">
		<li class="menu_upload_file"><a href="#">파일 업로드</a></li>
		<li class="menu_delete_file"><a href="#">디렉토리 삭제</a></li>
		<li class="menu_add_dir"><a href="#">하위 디렉토리 추가</a></li>
	</ul>

	<div class="add_dir">
		<input type="text" id="dir_name" placeholder="새 디렉토리 이름"> <input
			type="button" id="ok" value="OK"> <input type="button"
			id="cancel" value="취소">
	</div>

	<!-- 디렉토리 삭제 경고 Modal -->
	<div class="modal modal_alert fade" id="exampleModal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">정말 삭제하시겠습니까?</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">드라이브에 들어 있던 모든 디렉토리와 파일이 삭제됩니다. 삭제 후,
					복구하실 수 없습니다.</div>
				<div class="modal-footer">
					<button type="button" class="btn delete_dir_cancel"
						data-dismiss="modal">CANCEL</button>
					<button type="button" class="btn delete_dir">YES</button>
				</div>
			</div>
		</div>
	</div>

	<!-- 파일 추가 Modal -->
	<div class="modal modal_upload fade" id="exampleModal" tabindex="-1"
		role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">FILE UPLOAD</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form action="uploadFile" name="uploadForm" method="POST" enctype="multipart/form-data">
						<label for="recipient-name" class="col-form-label">파일 선택</label>
						<input type="file" name="file" class="form-control" id="selectedFile">
						<input type="hidden" name="dir_seq">
						<input type="button" onclick="uploadSubmit()">
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn upload_file_cancel"
						data-dismiss="modal">Close</button>
					<button type="button" class="btn upload_file">UPLOAD</button>
				</div>
			</div>
		</div>
	</div>

	<script src="resources/js/backup/directory.js"></script>
	<script src="resources/js/backup/file.js"></script>

</body>
</html>