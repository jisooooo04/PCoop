/**
 * 백업 - 파일 기능 관련 자바스크립트
 */

function uploadSubmit(){
	var dir_seq = $(".menu_upload_file").attr("id");
	var form = document.uploadForm;
	form.dir_seq.value = dir_seq;
	form.submit();
}

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

$(".menu_upload_file").on("click", function(){
	$(".modal_upload").modal();
})

