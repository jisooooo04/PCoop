/**
 * 백업 - 파일 기능 관련 자바스크립트

 */


//디렉토리 - 우 클릭 - 드롭다운 메뉴

function checkFileExists(seq){
	
	var result;
	
	var data = {
			type : 'file',
			seq: seq
	};
	
	$.ajax({
		url: "checkExists",
		type: "POST",
		async:false,
		data: data,
		success: function(data){
			//getCheck(data);
			result =  data;
		}
	});
		
	return result;
	
}

$(document).on("click", ".file", function(){
	var dir_seq = $(".menu_upload_file").attr("id").substring(3);
	var check = checkExists(dir_seq);
	
	if(check == 0){
		
		var seq = this.id.substring(1);
		location.href = "downloadFile?seq=" + seq;
		
	}
	
	else{
		alert("이미 삭제된 디렉토리입니다. 상위 디렉토리로 이동합니다.");
		$(".menu_upload_file").attr("id", "dir" + check);
		$(".menu_upload_zip").attr("id", "dir" + check);
		$(".menu_add_dir").attr("id", "dir" + check);
		$(".menu_delete_dir").attr("id", "dir" + check);
		$(".menu_back_dir").attr("id", "dir" + check);
		$(".menu_rename_dir").attr("id", "dir" + check);
	
		$(".btn_add_dir").attr("id", "dir" + check);
		$(".btn_back_dir").attr("id", "dir" + check);
		$(".btn_upload").attr("id", "dir" + check);
		getDirAndFileList(check);
	}
})

$(document).on("contextmenu", "#container", function(e){

	var x = e.pageX;
	var y = e.pageY;

	$(".contextmenu_container").css({
		"left": x,
		"top": y
	}).show();

	$(".upload_context").hide();
	$(".contextmenu").hide();
	$(".add_dir").hide();
	$(".rename_dir").hide();
	$(".file_context").hide();

	return false;
})

$(document).on("contextmenu", ".file", function(event){


	event.stopPropagation();
	var id = this.id;
	var position = $("#" + id).offset();
	var left = position.left + 15;
	var top = position.top + 90;

	var file_yn = $("#" + id).attr("class").substring(5);

	if(file_yn == 'text_n')
		$(".menu_preview_file").css("display", "none");

	else $(".menu_preview_file").css("display", "block");

	$(".menu_preview_file").attr("id", id);
	$(".menu_delete_file").attr("id", id);

	$(".file_context").css({
		left: left,
		top: top
	}).show();

	$(".upload_context").hide();
	$(".contextmenu").hide();
	$(".contextmenu_container").hide();
	$(".add_dir").hide();
	$(".rename_dir").hide();

	return false;
})

$(document).on("click", ".menu_preview_file", function(){
	var seq = $(".menu_preview_file").attr("id").substring(1);

})

//파일 선택 Modal
$(document).on("click", ".menu_upload_file", function(event){
	var dir_seq = $(".menu_upload_file").attr("id").substring(3);
	var check = checkExists(dir_seq);

	if(check == 0){
		$(".upload_context").hide();
		$(".modal_upload").modal();
	}
	
	else{
		alert("이미 삭제된 디렉토리입니다. 상위 디렉토리로 이동합니다.");
		$(".menu_upload_file").attr("id", "dir" + check);
		$(".menu_upload_zip").attr("id", "dir" + check);
		$(".menu_add_dir").attr("id", "dir" + check);
		$(".menu_delete_dir").attr("id", "dir" + check);
		$(".menu_back_dir").attr("id", "dir" + check);
		$(".menu_rename_dir").attr("id", "dir" + check);
	
		$(".btn_add_dir").attr("id", "dir" + check);
		$(".btn_back_dir").attr("id", "dir" + check);
		$(".btn_upload").attr("id", "dir" + check);
		getDirAndFileList(check);
	}
	
	
})

//파일 upload - 폼 데이터 ajax 전송
$(document).on("click", "#uploadSubmit", function(event){

	// 기본으로 정의된 이벤트를 작동하지 못하게 막음
	// submit을 막음
	event.preventDefault();
	var dir_seq = $(".menu_upload_file").attr("id").substring(3);
	
	var form = document.uploadForm;
	form.dir_seq.value = dir_seq;

	var data = new FormData(form);

	$.ajax({
		url: "uploadFile",
		type: "POST",
		enctype: 'multipart/form-data',
		data: data,
		processData: false,
		contentType: false,
		cache: false,
		timeout: 600000,
		success: function (data) {
			$(".modal_upload").modal('hide');
			$(".file").remove();
			var files = JSON.parse(data);

			for(var i = 0 ; i < files.length ; i++){

				var id = "f" + files[i].seq;

				if(files[i].text_yn == "Y"){
					$(".files").append("<div class='file text_y' id=" + id + "><div class=icon><span class='fas fa-file-upload fa-3x'></span></div><a href=downloadFile?seq=" + files[i].seq + ">" + files[i].name + "</a></div>");
				}

				else 
					$(".files").append("<div class='file text_n' id=" + id + "><div class=icon><span class='fas fa-file-upload fa-3x'></span></div><a href=downloadFile?seq=" + files[i].seq + ">" + files[i].name + "</a></div>");


			}



		},
		error: function (e) {
			console.log("ERROR : ", e);
			alert("용량이 너무 큽니다.");
		}
	});

})

//.zip 파일 선택 Modal
$(document).on("click", ".menu_upload_zip", function(){
	var dir_seq = $(".menu_upload_zip").attr("id").substring(3);

	var check = checkExists(dir_seq);

	if(check == 0){
		$(".upload_context").hide();
		$(".modal_upload_zip").modal();
	}
	
	else{
		alert("이미 삭제된 디렉토리입니다. 상위 디렉토리로 이동합니다.");
		$(".menu_upload_file").attr("id", "dir" + check);
		$(".menu_upload_zip").attr("id", "dir" + check);
		$(".menu_add_dir").attr("id", "dir" + check);
		$(".menu_delete_dir").attr("id", "dir" + check);
		$(".menu_back_dir").attr("id", "dir" + check);
		$(".menu_rename_dir").attr("id", "dir" + check);
	
		$(".btn_add_dir").attr("id", "dir" + check);
		$(".btn_back_dir").attr("id", "dir" + check);
		$(".btn_upload").attr("id", "dir" + check);
		getDirAndFileList(check);
	}
	
	
})

//upload .zip
$(document).on("click", "#uploadZipSubmit", function(event){

	// 기본으로 정의된 이벤트를 작동하지 못하게 막음
	// submit을 막음
	event.preventDefault();
	var dir_seq = $(".menu_upload_zip").attr("id").substring(3);
	var form = document.uploadZipForm;
	form.dir_seq.value = dir_seq;

	var dir_name = form.zip_dir.value;

	// 확장자 체크(.zip 파일만)
	var name = form.zip.value;
	extension = name.substring(name.lastIndexOf('.'));

	var checkDupl;

	if(extension != '.zip'){
		alert(".zip 파일만 가능합니다.");
		form.zip.value = "";
	}

	else{

		var data = new FormData(form);
		$(".modal_upload_zip").modal('hide');

		$.ajax({
			url: "checkDirNameDupl",
			type: "POST",
			async: false,
			enctype: 'multipart/form-data',
			data: data,
			processData: false,
			contentType: false,
			cache: false,
			timeout: 600000,
			success: function (data) {
				checkDupl = data;

				if(checkDupl == "dupl"){
					alert("디렉토리 이름 중복");
					form.zip_dir.value = "";
				}

			}
		});

		if(checkDupl != "dupl"){

			$(".uploading").toast({ autohide: false });
			$(".uploading").toast('show');

			$.ajax({
				url: "uploadZip",
				type: "POST",
				enctype: 'multipart/form-data',
				data: data,
				processData: false,
				contentType: false,
				cache: false,
				timeout: 600000,
				success: function (data) {

					var data = JSON.parse(data);
					var zip_dir_seq = data.zip_dir_seq;
					$(".uploaded").toast({ autohide: false });
					$(".uploaded").toast('show');
					
					$(".dirs").append("<div class=dir id=dir" + zip_dir_seq + "><div class=icon><span class='fas fa-folder-open fa-3x'></span></div>" + dir_name + "</div>")


				},
				error: function (e) {
					alert("용량이 너무 큽니다.");
					console.log("ERROR : ", e);
					alert("fail");
				}
			});

		}

	}

})


$(document).on("click", ".menu_preview_file", function(){
	
	var dir_seq = $(".menu_upload_zip").attr("id").substring(3);

	var check = checkExists(dir_seq);
	
	if(check == 0){
		var id = this.id;
		var seq = this.id.substring(1);
		
		var checkFile = checkFileExists(seq);
		console.log(checkFile);

		$(".modal_preview").modal();
		var pre_extension = $(".file-contents").attr("class").substring("file-contents hljs ".length);

		var data = {
				seq : seq
		};

		$.ajax({
			url: "readFile",
			type: "POST",
			data: data,
			contentType: "application/x-www-form-urlencoded; charset=UTF-8",
			success: function(data){

				var data = JSON.parse(data);
				var text = data.text;
				var extension = data.extension;

				$(".file-contents").removeClass(pre_extension);
				$(".file-contents").addClass(extension);
				$(".file-contents").text(data.text);
				hljs.initHighlighting.called = false;
				hljs.initHighlighting();
			}
		})
	}
	
	else{
		alert("이미 삭제된 디렉토리입니다. 상위 디렉토리로 이동합니다.");
		$(".menu_upload_file").attr("id", "dir" + check);
		$(".menu_upload_zip").attr("id", "dir" + check);
		$(".menu_add_dir").attr("id", "dir" + check);
		$(".menu_delete_dir").attr("id", "dir" + check);
		$(".menu_back_dir").attr("id", "dir" + check);
		$(".menu_rename_dir").attr("id", "dir" + check);
	
		$(".btn_add_dir").attr("id", "dir" + check);
		$(".btn_back_dir").attr("id", "dir" + check);
		$(".btn_upload").attr("id", "dir" + check);
		getDirAndFileList(check);
	}

	

})


$(document).on("click", ".menu_delete_file", function(){

	var seq = this.id.substring(1);
	var dir_seq = $(".menu_upload_file").attr("id").substring(3);
	var check = checkExists(dir_seq);

	if(check == 0){

		var data = { dir_seq: dir_seq,
				seq : seq };

		$.ajax({
			url: "deleteFile",
			type: "POST",
			data: data,
			success: function(data){
				$(".modal_upload").modal('hide');
				$(".file").remove();
				var files = JSON.parse(data);

				for(var i = 0 ; i < files.length ; i++){

					var id = "f" + files[i].seq;

					if(files[i].text_yn == "Y"){
						$(".files").append("<div class='file text_y' id=" + id + "><div class=icon><span class='fas fa-file-upload fa-3x'></span></div><a href=downloadFile?seq=" + files[i].seq + ">" + files[i].name + "</a></div>");
					}

					else 
						$(".files").append("<div class='file text_n' id=" + id + "><div class=icon><span class='fas fa-file-upload fa-3x'></span></div><a href=downloadFile?seq=" + files[i].seq + ">" + files[i].name + "</a></div>");

				}

			}
		});
	}
	else{
		alert("이미 삭제된 디렉토리입니다. 상위 디렉토리로 이동합니다.");
		$(".menu_upload_file").attr("id", "dir" + check);
		$(".menu_upload_zip").attr("id", "dir" + check);
		$(".menu_add_dir").attr("id", "dir" + check);
		$(".menu_delete_dir").attr("id", "dir" + check);
		$(".menu_back_dir").attr("id", "dir" + check);
		$(".menu_rename_dir").attr("id", "dir" + check);
	
		$(".btn_add_dir").attr("id", "dir" + check);
		$(".btn_back_dir").attr("id", "dir" + check);
		$(".btn_upload").attr("id", "dir" + check);
		getDirAndFileList(check);
	}
	

})

//파일 이름 변경 클릭 - 입력 창
$(document).on("click", ".renameFile", function(){

	var fid = this.id.substring(12);
	$(".ok_rename_file").attr("id", fid);
	var left = $("#" + fid).offset().left;
	var top = $("#" + fid).offset().top + 30;

	$(".rename_file").css({
		"left" : left,
		"top" : top
	}).show();

})


//파일 이름 변경
$(document).on("click", ".ok_rename_file", function(){

	var id = this.id;
	var rename = $("#file_rename").val();
	var seq = id.substring(1);

	console.log(id + " : " + seq);
	var data = {
			seq: seq,
			rename: rename
	};

	$.ajax({
		url: "renameFile",
		type: "POST",
		data: data,
		success: function(data){

			if(data != -1)
				$("#" + id).html("<b>" + rename + "</b>");
			else alert("디렉토리 이름 중복");
		}
	});

	$(".rename_dir").hide();

})

//파일 이름 변경 취소
$(document).on("click", ".cancel_rename_file", function(){
	$(".rename_file").hide();
})
