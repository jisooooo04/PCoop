

	window.onload = function() {

		$("#back").on("click",function(){
			location.href="/";
		})
	
		
document.getElementById("signup").onsubmit = function() {

	var name = document.getElementById("name").value;
	var password_1 = document.getElementById("password_1").value;
	var password_2 = document.getElementById("password_2").value;

	
	if (name == "") {
		alert("가입을 위해서는 이름을 입력해야 합니다.")
		return false
	} 
	if (password_1 == "") {
		alert("가입을 위해서는 비밀번호를 입력해야 합니다.")
		return false
	} 
	if (password_2 == "") {
		alert("가입을 위해서는 비밀번호 확인을 입력해야 합니다.")
		return false
	} 
	if (password_1 != password_2) {
		
		alert("가입을 위해서는 비밀번호가 일치해야 합니다.")
		return false
	}

    var pw1 = document.getElementById("password_1").value;
    var regexpw1= /.{4,20}/;
    var resultpw1 = regexpw1.test(pw1);
    if(!resultpw1){
        alert("비밀번호는 공백문자를 제외하고 4글자 이상 20글자 이하로 입력하세요")
        return false
    }
	
	return true

}

$('.pw').focusout(function () {
	console.log('비밀번호 확인');
	var pwd1 = $("#password_1").val();
	var pwd2 = $("#password_2").val();

	if ( pwd1 != '' && pwd2 == '' ) {
		null;
	} else if (pwd1 != "" || pwd2 != "") {
		if (pwd1 == pwd2) {
			$("#alert-success").css('display', 'inline-block');
			$("#alert-danger").css('display', 'none');
		} else {
			$("#alert-success").css('display', 'none');
			$("#alert-danger").css('display', 'inline-block');
		}
	}
})
	}
