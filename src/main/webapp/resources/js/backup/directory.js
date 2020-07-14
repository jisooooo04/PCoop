/**
 * 백업 - 디렉토리 기능 관련 자바스크립트

 */



//디렉토리 리스트 출력 함수

//side-left 디렉토리 리스트 
function printDirList(dirlist){

	for (var i = 0; i < dirlist.length; i++) {

		var name = dirlist[i].name;
		var parent = ".root";
		$(parent).append("<li class=dir id=dir" + dirlist[i].seq + ">" + name + "</li>");

	}

}

function getDirAndFileList(dir_seq){
	
	var id = "dir" + dir_seq;
	
	$(".menu_upload_file").attr("id", id);
	$(".menu_upload_zip").attr("id", id);
	$(".menu_add_dir").attr("id", id);
	$(".menu_delete_dir").attr("id", id);
	$(".menu_back_dir").attr("id", id);
	$(".menu_rename_dir").attr("id", id);

	$(".btn_add_dir").attr("id", id);
	$(".btn_back_dir").attr("id", id);
	$(".btn_upload").attr("id", id);
	
	var data = {
			dir_seq: dir_seq
	};

	$.ajax({
		url: "getDirAndFileList",
		type: "POST",
		data: data,
		success: function(data){

			$(".contents .dir").remove();
			$(".file").remove();

			var data = JSON.parse(data);
			var path = data.path;
			var root_seq = data.root_seq;
			
			if(root_seq == dir_seq)
				$(".menu_back_dir").hide();
			else $(".menu_back_dir").show();

			path = path.substring(path.indexOf('_') + 1, path.length);
			path = path.replace(/\//g, "　<i class='fas fa-chevron-right'></i>　")
			$(".backup-path").html(path);

			// 하위 디렉토리와 파일들 리스트 출력
			var dirs = JSON.parse(data.dirArr);
			var files = JSON.parse(data.fileArr);

			for(var i = 0 ; i < dirs.length ; i++){
				var dir_id = "dir" + dirs[i].seq;
				$(".dirs").append("<div class=dir id=" + dir_id + "><div class=icon><span class='fas fa-folder-open fa-3x'></span></div>" + dirs[i].name + "</div>")
			}

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


//디렉토리 - 클릭 - 디렉토리 내 파일 리스트
$(document).on("click", ".dir", function(event){

	// 이벤트 버블링 방지
	event.stopImmediatePropagation();

	var id = this.id;
	var dir_seq = id.substring(3);

	$(".menu_upload_file").attr("id", id);
	$(".menu_upload_zip").attr("id", id);
	$(".menu_add_dir").attr("id", id);
	$(".menu_delete_dir").attr("id", id);
	$(".menu_back_dir").attr("id", id);
	$(".menu_rename_dir").attr("id", id);

	$(".btn_add_dir").attr("id", id);
	$(".btn_back_dir").attr("id", id);
	$(".btn_upload").attr("id", id);


	getDirAndFileList(dir_seq);


})

$(document).on("click", ".menu_back_dir", function(event){

	// 이벤트 버블링 방지
	event.stopImmediatePropagation();

	var id = this.id;
	var dir_seq = id.substring(3);

	var back_dir_seq;
	
	var data = {
			dir_seq : dir_seq
	};

	$.ajax({
		url: "getParentDirSeq",
		type: "POST",
		data: data,
		success: function(data){
			
			back_dir_seq = data;
			var back_dir_id = "dir" + back_dir_seq;
			
			$(".menu_upload_file").attr("id", back_dir_id);
			$(".menu_upload_zip").attr("id", back_dir_id);
			$(".menu_add_dir").attr("id", back_dir_id);
			$(".menu_back_dir").attr("id", back_dir_id);
			$(".menu_delete_dir").attr("id", back_dir_id);
			$(".menu_rename_dir").attr("id", back_dir_id);

			$(".btn_back_dir").attr("id", back_dir_id);
			$(".btn_add_dir").attr("id", back_dir_id);
			$(".btn_upload").attr("id", back_dir_id);
			
			getDirAndFileList(back_dir_seq);
			
		}
	})

})


//디렉토리 - 우 클릭 - 드롭다운 메뉴
$(document).on("contextmenu", ".dirs>.dir", function(event){

	// 이벤트 버블링 방지
	event.stopImmediatePropagation();

	var id = this.id;
	var left = $(".dirs>#" + id).offset().left + 20;
	var top = $(".dirs>#" + id).offset().top + 90;

	// 루트 디렉토리는 삭제 메뉴가 안 보이게 처리함 
	var root_id = $(".root").attr("id");

	if(id == root_id)
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
	$(".menu_rename_dir").attr("id", id);
	//Prevent browser default contextmenu.
	$(".contextmenu_container").hide();
	$(".add_dir").hide();
	$(".rename_dir").hide();

	return false;
})


//Hide contextmenu:
$(document).click(function(event){

	$(".contextmenu").hide();
	$(".contextmenu_container").hide();
	$(".upload_context").hide();
	$(".add_dir").hide();
	$(".rename_dir").hide();
	$(".file_context").hide();

});

//업로드 버튼
$(document).on("click", ".menu_upload", function(event){
	event.stopPropagation();
	var position = $(".btn_upload").offset();
	var left = position.left;
	var top = position.top + 40;
	$(".upload_context").css({
		"left": left,
		"top": top
	}).show();

	$(".contextmenu").hide();
	$(".contextmenu_container").hide();
	$(".add_dir").hide();
	$(".rename_dir").hide();
	$(".file_context").hide();
})

//디렉토리 추가 버튼
$(document).on("click", ".menu_add_dir", function(event){

	event.stopPropagation();

	var position = $(".btn_add_dir").offset();
	var left = position.left;
	var top = position.top + 40;

	$(".add_dir").css({
		"left": left,
		"top": top
	}).show();

	$(".contextmenu").hide();
	$(".contextmenu_container").hide();
	$(".rename_dir").hide();
	$(".upload_context").hide();
})

//새 디렉토리 추가
$(document).on("click", "#ok", function(){

	var root_id = $(".root").attr("id");
	var root_name = $(".root>b").text();

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

			var json = JSON.parse(data);
			var checkDupl = json.checkDupl;
			var seq = json.seq;

			if(checkDupl != 1){
				var dirlist= JSON.parse(json.dirlist);
				$(".root").remove();
				// 리스트 새로 만들기
				$(".backup").append("<ul id=" + root_id + " class='root dir'><b>" + root_name + "</b></ul>");
				// 디렉토리 가지고 오기
				printDirList(dirlist);
				$(".dirs").append("<div class=dir id=dir" + seq + "><div class=icon><span class='fas fa-folder-open fa-3x'></span></div>" + name + "</div>")


			}
			else alert("디렉토리명 중복");

		}
	});
	$(".add_dir").hide();
	$("#dir_name").val("");

})

//새 디렉토리 추가  취소
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

	var root_id = $(".root").attr("id");
	var root_name = $(".root>b").text();

	$(".modal_alert").modal('hide');

	var seq = $(".menu_add_dir").attr("id").substring(3);
	var root_seq = root_id.substring(3);

	var data = {
			seq: seq,
			root_seq: root_seq
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
			$(".backup").append("<ul id=" + root_id + " class='root dir'><b>" + root_name + "</b></ul>");
			// 디렉토리 가지고 오기
			var dirlist = JSON.parse(data.dirlist);
			printDirList(dirlist);

			$("#dir" + seq + ".dir").remove();

		}
	});

})

$(document).on("click", "#dir_name", function(event){
	event.stopPropagation();
	$(".add_dir").show();
})

//디렉토리 이름 변경 버튼 - 입력 창
$(document).on("click", ".menu_rename_dir", function(event){

	event.stopPropagation();

	var id = this.id;
	var left = $(".dirs>#" + id).offset().left;
	var top = $(".dirs>#" + id).offset().top + 70;
	var dir_name = $("#" + id + ".dir").tekRdxt();

	console.log(dir_name);
	$("#dir_rename").val(dir_name);
	
	$(".rename_dir").css({
		"left": left,
		"top": top
	});
	

	$(".contextmenu").hide();
	$(".contextmenu_container").hide();
	$(".add_dir").hide();
	$(".upload_context").hide();

	$(".rename_dir").css("display", "block");

})

//디렉토리 이름 변경
$(document).on("click", "#ok_rename_dir", function(){

	var id = $(".menu_rename_dir").attr("id");
	var rename = $("#dir_rename").val();
	var seq = $(".menu_rename_dir").attr("id").substring(3);

	var data = {
			seq: seq,
			rename: rename
	};

	$.ajax({
		url: "renameDirectory",
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

//디렉토리 이름 변경 취소
$(document).on("click", "#cancel_rename_dir", function(){
	var id = $(".menu_rename_dir").attr("id");
	$(".rename_dir").hide();
	var dir_name = $("#" + id).text();
	$("#dir_rename").val(dir_name);
})

