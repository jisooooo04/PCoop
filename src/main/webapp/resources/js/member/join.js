



		document.getElementById("signup").onsubmit = function() {
			
			var name = document.getElementById("name").value;
			var password_1 = document.getElementById("password_1").value;
			var password_2 = document.getElementById("password_2").value;

			if (name == "") {
				alert("가입을 위해서는 이름을 입력해야 합니다.")
				return false
			} else {
				return true
			}
			if (password_1 == "") {
				alert("가입을 위해서는 비밀번호를 입력해야 합니다.")
				return false
			} else {
				return true
			}
			if (password_2 == "") {
				alert("가입을 위해서는 비밀번호 확인을 입력해야 합니다.")
				return false
			} else {
				return true
			}
			if (password_1 == password_2) {
				alert("가입을 위해서는 비밀번호가 일치해야 합니다.")
				return false
			} else {
				return true
			}
			
		}

	    $('.in').focusout(function () {
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
	                alert("비밀번호가 일치하지 않습니다. 비밀번호를 재확인해주세요.");
	                $("#alert-success").css('display', 'none');
	                $("#alert-danger").css('display', 'inline-block');
	            }
	        }
	    })
		
	