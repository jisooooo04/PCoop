<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="../header/cdn.jsp"></jsp:include>

<link rel="stylesheet" href="resources/css/backup/filelist.css?after" />

<!-- 코드 편집기 CDN -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/9.13.1/styles/agate.min.css">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/9.12.0/highlight.min.js"></script>
<script>
	hljs.initHighlightingOnLoad();
</script>
</head>
<body>

	<!-- Header -->
	<jsp:include page="../header/header.jsp"></jsp:include>
	<!-- 왼쪽 사이드바 -->
	<jsp:include page="../header/sidebar-left.jsp"></jsp:include>
	<section>

		<div id="container">
			<!-- 여기부터 각자 영역 설정 -->
			<div class="contents">

				<div class="backup-path"></div>
				<div class="backup-btns">
					<div class="menu_back_dir">
						<i class="fas fa-arrow-circle-left"></i>
						<button type=button class=btn_back_dir>뒤로가기</button>
					</div>
					<div class="menu_add_dir">
						<i class="fas fa-plus-circle"></i>
						<button type=button class=btn_add_dir>ADD DIRECTORY</button>
					</div>
					<div class="menu_upload">
						<i class="fas fa-arrow-down"></i>
						<button type=button class="btn_upload">UPLOAD</button>
					</div>
				</div>

				<div class="list">
					<div class="dirs"></div>

					<div class="files"></div>
				</div>



			</div>
			<div class="modals">
				<!-- 디렉토리 우 클릭 시, 나타나는 드롭 다운 메뉴 -->
				<ul class="contextmenu">
					<li class="menu_rename_dir"><a href="#">디렉토리 이름 변경</a></li>
					<li class="menu_delete_dir"><a href="#">디렉토리 삭제</a></li>
				</ul>

				<ul class="contextmenu_container">
					<li class="menu_upload_file"><a href="#">파일 업로드</a></li>
					<li class="menu_upload_zip"><a href="#">.zip 파일 업로드 - 자동
							해제</a></li>
					<li class="menu_add_dir"><a href="#">하위 디렉토리 추가</a></li>
				</ul>

				<ul class="upload_context">
					<li class="menu_upload_file"><a href="#">파일 업로드</a></li>
					<li class="menu_upload_zip"><a href="#">.zip 파일 업로드 - 자동
							압축 해제</a></li>
				</ul>

				<ul class="file_context">
					<li class="menu_preview_file"><a href="#">미리 보기</a></li>
					<li class="menu_delete_file"><a href="#">삭제</a></li>
				</ul>

				<div class="add_dir">
					<input type="text" id="dir_name" placeholder="새 디렉토리 이름"> <input
						type="button" id="ok" value="OK">
				</div>

				<div class="rename_dir">
					<input type="text" id="dir_rename" placeholder="변경할 디렉토리 이름">
					<input type="button" id="ok_rename_dir" value="OK"> <input
						type="button" id="cancel_rename_dir" value="취소">
				</div>

				<div class="rename_file">
					<input type="text" id="file_rename" placeholder="변경할 파일 이름">
					<input type="button" class="ok_rename_file" value="OK"> <input
						type="button" class="cancel_rename_file" value="취소">
				</div>

				<!-- 디렉토리 삭제 경고 Modal -->
				<div class="modal modal_alert fade" id="exampleModal" tabindex="-1"
					role="dialog" aria-labelledby="exampleModalLabel"
					aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title" id="exampleModalLabel">정말 삭제하시겠습니까?</h5>
								<button type="button" class="close" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
							</div>
							<div class="modal-body">드라이브에 들어 있던 모든 디렉토리와 파일이 삭제됩니다. 삭제
								후, 복구하실 수 없습니다.</div>
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
					role="dialog" aria-labelledby="exampleModalLabel"
					aria-hidden="true">
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
								<form action="uploadFile" name="uploadForm" method="POST"
									enctype="multipart/form-data">
									<label for="recipient-name" class="col-form-label">파일
										선택</label> <input type="file" name="file" class="form-control"
										id="selectedFile"> <input type="hidden" name="dir_seq">
									<input type="submit" id="uploadSubmit">
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

				<!-- .zip 파일 추가 Modal -->
				<div class="modal modal_upload_zip fade" id="exampleModal"
					tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
					aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title" id="exampleModalLabel">ZIP UPLOAD</h5>
								<button type="button" class="close" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
							</div>
							<div class="modal-body">
								<form action="uploadZip" name="uploadZipForm" method="POST"
									enctype="multipart/form-data">
									<label for="recipient-name" class="col-form-label">.zip
										파일 선택</label> <input type="file" name="zip" class="form-control"
										id="selectedFiles"> <label id=zipname>압축 해제할
										디렉토리 이름 설정</label><input type="text" name=zip_dir> <input
										type="hidden" name="dir_seq"> <input type="submit"
										id="uploadZipSubmit">
								</form>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn upload_zip_cancel"
									data-dismiss="modal">Close</button>
								<button type="button" class="btn upload_zip">UPLOAD</button>
							</div>
						</div>
					</div>
				</div>

				<!-- 파일 미리 보기 Modal -->
				<div class="modal modal_preview fade" id="exampleModal"
					tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
					aria-hidden="true">
					<div class="modal-dialog modal-lg">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title" id="exampleModalLabel">FILE PREVIEW</h5>
								<button type="button" class="close" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
							</div>
							<div class="modal-body">
								<!-- HTML코드를 강조하여 보여줄때.. -->
								<pre>
				<code class="file-contents">
							
					</code>
				</pre>
							</div>
							<div class="modal-footer"></div>
						</div>
					</div>
				</div>

			</div>
			<!-- 여기까지 각자 영역 설정 -->
		</div>

	</section>

	<script src="resources/js/backup/directory.js"></script>
	<script src="resources/js/backup/file.js"></script>
	<script>
		$(function(){
			$(".backup").css("display", "block");
			var root_seq = ${root_seq};
			var dir_seq = ${dir_seq};
			
			getDirAndFileList(dir_seq);
		})
		
	</script>

</body>
</html>