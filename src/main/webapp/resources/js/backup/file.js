/**
 * 백업 - 파일 기능 관련 자바스크립트

 */


// 디렉토리 - 우 클릭 - 드롭다운 메뉴

$(document).on("contextmenu", "#container", function(e){
	
	var x = e.pageX;
	var y = e.pageY;
	
	$(".contextmenu_container").css({
		"left": x,
		"top": y
	}).show();

	return false;
})

// 파일 선택 Modal
$(document).on("click", ".menu_upload_file", function(){
	$(".modal_upload").modal();
})

// 파일 upload - 폼 데이터 ajax 전송
$(document).on("click", "#uploadSubmit", function(event){
	
	// 기본으로 정의된 이벤트를 작동하지 못하게 막음
	// submit을 막음
	event.preventDefault();
	var dir_seq = $(".menu_upload_file").attr("id").substring(3);
	var form = document.uploadForm;
	form.dir_seq.value = dir_seq;
	
	console.log(form);
	
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
				
				$(".files").append("<div class=file id=" + id + "><a href=downloadFile?seq=" + files[i].seq + ">" + files[i].name + "</a></div>");
								
				if(files[i].text_yn == "Y"){
					$("#" + id).append("<button class=readFile id=read_" + id + " type=button>미리 보기</button>");
					$("#" + id).append("<button class=closeFile id=close_" + id + " type=button style='display: none;'>닫기</button>");
				}
				$("#" + id).append("<button class=renameFile id=rename_file_" + id + " type=button>이름 변경</button>");
				$("#" + id).append("<button class=deleteFile id=del_" + id + " type=button>삭제</button>");
			}
        },
        error: function (e) {
            console.log("ERROR : ", e);
            alert("fail");
        }
    });
    
})

// .zip 파일 선택 Modal
$(document).on("click", ".menu_upload_zip", function(){
	$(".modal_upload_zip").modal();
})

// upload .zip
$(document).on("click", "#uploadZipSubmit", function(event){
	
	// 기본으로 정의된 이벤트를 작동하지 못하게 막음
	// submit을 막음
	event.preventDefault();
	var dir_seq = $(".menu_upload_zip").attr("id").substring(3);
	var form = document.uploadZipForm;
	form.dir_seq.value = dir_seq;

	
	// 확장자 체크(.zip 파일만)
	var name = form.zip.value;
	extension = name.substring(name.indexOf('.'));
	
	if(extension != '.zip'){
		alert(".zip 파일만 가능합니다.");
		form.zip.value = "";
	}
	
	else{
		
		var data = new FormData(form);
		
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
	        	
	        	if(data == "dupl"){
	        		alert("디렉토리 이름 중복");
	        		form.zip_dir.value = "";
	        	}

	        },
	        error: function (e) {
	            console.log("ERROR : ", e);
	            alert("fail");
	        }
	    });
		
	}
	
})


$(document).on("click", ".readFile", function(){
	
	var id = this.id.substring(5);
	$("#read_" + id).hide();
	$("#close_" + id).show();
	$(".file-contents").css('display', 'block');

	
	var seq = this.id.substring(6);
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
	
})

$(document).on("click", ".closeFile", function(){
	$(".readFile").show();
	$(".closeFile").hide();
	$(".file-contents").css('display', 'none');
})

$(document).on("click", ".deleteFile", function(){

	var seq = this.id.substring(5);
	var dir_seq = $(".menu_upload_file").attr("id").substring(3);
	console.log(dir_seq);

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
				$(".files").append("<div class=file id=" + id + "><a href=downloadFile?seq=" + files[i].seq + ">" + files[i].name + "</a></div>");
				
//				if(files[i].text_yn == "Y"){
//					$("#" + id).append("<button class=readFile id=read_" + id + " type=button>미리 보기</button>");
//					$("#" + id).append("<button class=closeFile id=close_" + id + " type=button style='display: none;'>닫기</button>");
//				}
//				$("#" + id).append("<button class=renameFile id=rename_file_" + id + " type=button>이름 변경</button>");
//				$("#" + id).append("<button class=deleteFile id=btn_" + id + " type=button>삭제</button>");
				
			}

		}
	});

})

// 파일 이름 변경 클릭 - 입력 창
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


// 파일 이름 변경
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

// 파일 이름 변경 취소
$(document).on("click", ".cancel_rename_file", function(){
	$(".rename_file").hide();
})
