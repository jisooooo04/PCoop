/**
 * 백업 - 디렉토리 기능 관련 자바스크립트

 */



//디렉토리 리스트 출력 함수

function printDirList(dirlist){

	for (var i = 0; i < dirlist.length; i++) {

		var patharr = dirlist[i].path.split('/');
		var parent = ".root";

		for (var j = 2; j < patharr.length; j++) {

			if (j == patharr.length - 1)
				$(parent).append(
						"<ul id=" + patharr[j] + "><li class='dir' id='dir" + dirlist[i].seq +"'><b>"
						+ patharr[j] + "</b></li></ul>");
			else
				parent = "#" + patharr[j];

		}

	}

}


//전체 파일 리스트 가져오는 함수
function printFileList(filelist){
	for (var i = 0; i < filelist.length; i++) {

		var patharr = filelist[i].path.split('/');
		var parent = ".root";

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
}

//디렉토리 - 클릭 - 디렉토리 내 파일 리스트
$(document).on("click", ".dir", function(event){
	
	// 이벤트 버블링 방지
	event.stopImmediatePropagation();
	
	var dir_seq = this.id.substring(3);
	$(".menu_upload_file").attr("id", dir_seq);

	var data = {
			dir_seq: dir_seq
	};

	$.ajax({
		url: "getFileList",
		type: "POST",
		data: data,
		success: function(data){

			$(".file").remove();
			var files = JSON.parse(data);

			for(var i = 0 ; i < files.length ; i++){
				
				var id = "f" + files[i].seq;
				$(".files").append("<div class=file id=" + id + "><a href=downloadFile?seq=" + files[i].seq + ">" + files[i].name + "</a></div>");
				
				console.log(files[i].text_yn);
				
				if(files[i].text_yn == "Y"){
					$("#" + id).append("<button class=readFile id=read_" + id + " type=button>미리 보기</button>");
				}
				
				$("#" + id).append("<button class=deleteFile id=btn_" + id + " type=button>삭제</button>");
				
			}


		}
	});

})

//디렉토리 - 우 클릭 - 드롭다운 메뉴
$(document).on("contextmenu", ".dir", function(event){
	
	// 이벤트 버블링 방지
	event.stopImmediatePropagation();

	var id = this.id;
	var left = $("#" + id).offset().left;
	var top = $("#" + id).offset().top + 30;

	// 루트 디렉토리는 삭제 메뉴가 안 보이게 처리함 
	var root_id = $(".root").attr("id");
	
	if(id == 'dir4')
		$(".menu_delete_dir").css("display", "none");
	
	else $(".menu_delete_dir").css("display", "block");
	
	//Display contextmenu:
	$(".add_dir").css({
		"left": left,
		"top": top
	});

	$(".contextmenu").css({
		"left": left,
		"top": top
	}).show();

	$(".menu_add_dir").attr("id", id);
	$(".menu_delete_dir").attr("id", id);
	//Prevent browser default contextmenu.
	return false;
})


//Hide contextmenu:
$(document).click(function(){
	$(".contextmenu").hide();
	$(".contextmenu_container").hide();

});

//디렉토리 추가 버튼 - 드롭다운 메뉴
$(document).on("click", ".menu_add_dir", function(){

	console.log(this.id);
	var id = this.id;
	var left = $("#" + id).offset().left;
	var top = $("#" + id).offset().top + 30;

	$(".add_dir").css({
		"left": left,
		"top": top
	}).show();

	$(".contextmenu").hide();
})

//새 디렉토리 추가
$(document).on("click", "#ok", function(){

	var parent_seq = $(".menu_add_dir").attr("id").substring(3);
	var name = $("#dir_name").val();
	var data = {
			parent_seq: parent_seq,
			name: name
	};

	$.ajax({
		url: "addDirectory",
		type: "POST",
		data: data,
		success: function(data){
			$("#dir" + parent_seq).append(
					"<ul><li class='dir' id='dir" + data + "'><b>"
					+ name + "</b></li></ul>");
		}
	});
	$(".add_dir").hide();
	$("#dir_name").val("");

})

//새 디렉토리 추가 취소
$(document).on("click", "#cancel", function(){
	$(".add_dir").hide();
	$("#dir_name").val("");
})

//디렉토리 삭제 버튼 - 경고 모달 창
$(document).on("click", ".menu_delete_dir", function(){
	$(".modal_alert").modal();
})

//디렉토리 삭제
$(document).on("click", ".delete_dir", function(){

	$(".modal_alert").modal('hide');

	var seq = $(".menu_add_dir").attr("id").substring(3);
	console.log(seq);
	var data = {
			seq: seq
	}

	$.ajax({
		url: "deleteDirectory",
		type: "POST",
		data: data,
		success: function(data){

			$("#dir" + seq).remove();
			$(".root").remove();

			var data = JSON.parse(data);

			// 리스트 새로 만들기
			$(".backup").append("<ul id=dir4 class='root dir'>TEMP</ul>");
			// 디렉토리 가지고 오기
			var dirlist = JSON.parse(data.dirlist);
			printDirList(dirlist);

		}
	});

})

