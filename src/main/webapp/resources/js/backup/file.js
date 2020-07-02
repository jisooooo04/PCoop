/**
 * 백업 - 파일 기능 관련 자바스크립트
 */

// 디렉토리 - 우 클릭 - 드롭다운 메뉴
$("#container").on("contextmenu", function(e){
	
	var x = e.pageX;
	var y = e.pageY;
	
	$(".contextmenu_container").css({
		"left": x,
		"top": y
	}).show();

	return false;
})

// 파일 선택 Modal
$(".menu_upload_file").on("click", function(){
	$(".modal_upload").modal();
})

// 파일 upload - 폼 데이터 ajax 전송
$("#uploadSubmit").on("click", function(event){
	
	// 기본으로 정의된 이벤트를 작동하지 못하게 막음
	// submit을 막음
	event.preventDefault();
	
	var dir_seq = $(".menu_upload_file").attr("id");
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
				$(".files").append("<div class=file id=" + id + "><a href=downloadFile?seq=" + files[i].seq + ">" + files[i].name + "</a></div>");
				$("#" + id).append("<button class=deleteFile id=btn_" + id + " type=button>삭제</button>");
			}
        },
        error: function (e) {
            console.log("ERROR : ", e);
            alert("fail");
        }
    });
    
})

$(document).on("click", ".deleteFile", function(){

	var seq = this.id.substring(5);
	var dir_seq = $(".menu_upload_file").attr("id");

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
				$("#" + id).append("<button class=deleteFile id=btn_" + id + " type=button>삭제</button>");
			}

		}
	});

})