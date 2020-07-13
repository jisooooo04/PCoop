<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>PCOOP!</title>
<jsp:include page="../header/cdn.jsp"></jsp:include>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<style>
.row {
	margin-top: 10px;
}
/* member info 부분 */
.memInfoTitle {
	border: 2px solid #5f83ba;
	margin-top: 37px;
	/* margin-bottom: 30px; */
	 margin-bottom: 10px;
	font-size: 30px;
	background-color: #5f83ba;
	color: white;
	text-align: center;
}

.fa-id-card {
	margin-right: 10px;
}

.memInfo {
	/* border: 0.5px solid #5f83ba; */
	padding: 10px;
}

.memInfo>div {
	font-size: 20px;
	margin: 10px;
	text-align: center;
	color: dimgray;
}

.btns>button {
	outline: none;
	margin: 8px;
	width: 70px;
	height: 30px;
	border: 1px solid #5f83ba;
	background-color: #fff;
	border-radius: 10px;
	font-family: 'Noto Sans KR', sans-serif;
	color: dimgray;
	line-height: 10px;
}

.btns>button:hover {
	background-color: dimgray;
	color: white;
	border: none;
}

.modifymodal {
	padding-top: 30px;
	top: 100%;
	position: fixed;
	background: #fff;
	width: 100%;
	height: 100%;
	transition: all 600ms cubic-bezier(0.86, 0, 0.07, 1);
	margin: 0;
	font-family: 'Noto Sans KR', sans-serif;
	background-color: #fffefc;
}

.deletemodal {
	padding: 30px;
	top: 100%;
	position: fixed;
	background: #fff;
	width: 100%;
	height: 100%;
	transition: all 600ms cubic-bezier(0.86, 0, 0.07, 1);
	margin: 0;
	font-family: 'Noto Sans KR', sans-serif;
	background-color: #fffefc;
}

.deletemodal i {
	color: #fcd703;
}

.modal-open {
	top: 0;
}
/* 프로젝트 리스트  */
.box {
	margin-bottom: 10px;
	font-family: 'Noto Sans KR', sans-serif;
	text-align: center;
	font-size: 18px;
	padding: 20px;
	width: 400px;
	margin-bottom: 10px;
	border: 0.5px solid #5f83ba;
	color: dimgrey;
}

.head {
	width: 400px;
	background-color: #5f83ba;
	font-family: 'Noto Sans KR', sans-serif;
	font-size: 30px;
	text-align: center;
	color: white;
	margin-bottom: 30px;
	margin-top: 30px;
	padding:2px;
}

.fa-list-ul {
	margin-right: 10px;
}

.box button {
	margin: 5px;
	background-color: #5f83ba;
	border-radius: 10px;
	border: none;
	padding: 6px;
	outline:none;
}

.box a {
	text-decoration: none;
	color: white;
}

.box>div {
	margin: 5px;
}

.fa-user {
	margin-right: 5px;
}

.fa-crown {
	margin-right: 5px;
	color: #f5db33;
}
/* 프로젝트 나가기 모달  */
.exitProjectBox {
	transition: all 600ms cubic-bezier(0.86, 0, 0.07, 1);
	border: none;
	position: fixed;
	top: 100%;
	padding: 25px;
	border-radius: 10px;
	font-family: 'Noto Sans KR', sans-serif;
	background-color: white;
	transform: translateX(150px);
}

.emphasized {
	box-shadow: rgba(0, 0, 0, 0.5) 0 0 0 9999px, rgba(0, 0, 0, 0.5) 2px 2px
		3px 3px;
	z-index: 100;
}

.exitProjectBox>div {
	text-align: center;
	margin: auto;
	margin-top: 5px;
}

.exitProjectBox>div:nth-child(1) {
	font-size: 25px;
}

.exitProjectBox button {
	margin: 5px;
	width: 80px;
	border-radius: 15px;
	border: 0.5px solid #32a6f0;
	background-color: white;
	color: #32a6f0;
	outline:none;
}

.openModal {
	top: 20%;
}
</style>
</head>
<body>

	<div class="container">
		<header>
			<nav class="navbar navbar-expand-lg navbar-light bg-light">
				<a class="navbar-brand" href="#">Navbar</a>
				<button class="navbar-toggler" type="button" data-toggle="collapse"
					data-target="#navbarNav" aria-controls="navbarNav"
					aria-expanded="false" aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>
				<div class="collapse navbar-collapse" id="navbarNav">
					<ul class="navbar-nav">
						<li class="nav-item active"><a class="nav-link" href="/">Home
								<span class="sr-only">(current)</span>
						</a></li>
						<li class="nav-item"><a class="nav-link" href="#">Features</a>
						</li>
						<li class="nav-item"><a class="nav-link" href="#">Pricing</a>
						</li>
						<li class="nav-item"><a class="nav-link disabled" href="#">Disabled</a>
						</li>
					</ul>
				</div>
			</nav>

		</header>
		<section>

			<div class="row">
				<!-- ㅡㅡㅡㅡㅡㅡㅡㅡㅡ회원 정보-->

				<div class="col-4-sm">
					<div class="memInfoTitle">
						<i class="far fa-id-card"></i>회원 정보
					</div>
					<div class="memInfo">
						<div class="row p-1">
							<div class="col-12"></div>
						</div>
						<div class="row">

							<div class="col-4">e-mail</div>
							<div class="col-5">${loginInfo.email}</div>
							<div class="col-3"></div>
						</div>
						<div class="row">

							<div class="col-4">name</div>
							<div class="col-5">${loginInfo.name}</div>
							<div class="col-3"></div>
						</div>
						<div class="row">
							<div class="col-4">password</div>
							<div class="col-5">
								<input type="password" readonly value="${loginInfo.pw}">
							</div>
							<div class="col-3"></div>
						</div>
						<div class="row">
							<div class="col-2"></div>
							<div class="col-8 btns">
								<button id="modifybtn">수정</button>
								<button id="deletebtn">탈퇴</button>
							</div>
							<div class="col-2"></div>
						</div>

					</div>

				</div>
				<!-- ㅡㅡㅡ프로젝트 리스트  -->
				<div class="col-sm-1"></div>
				<div class="col-sm-6">
					<div class="row">
						<c:choose>
							<c:when test='${list_size ==0}'>
								<div class="col-4-sm m-5"></div>
								<div class="col-8-sm m-5">
									아직 참여한 프로젝트가 없습니다.. <br> 프로젝트를 생성하거나 참여해보세요 !
								</div>
							</c:when>
							<c:otherwise>
								<div class="col-sm-12">
									<div class="wrapper">
										<div class="head">
											<i class="fas fa-list-ul"></i>프로젝트
										</div>
										<c:forEach var="i" items="${list}">
											<div class="box">
												<c:choose>
													<c:when test='${i.leader_seq==loginInfo.seq}'>
														<div>
															<i class="fas fa-crown"></i>팀장
														</div>
													</c:when>
												</c:choose>
												<div>${i.name}</div>
												<div>초대 코드 : ${i.code}</div>
												<div>
													<i class="fas fa-user fa-lg"></i> /${i.people_num}
												</div>
												<div>
													<button>
														<a href="../project-main?seq=${i.seq}">프로젝트 바로가기</a>
													</button>
													<button>
														<a href="#" onclick="notice(${i.seq});return false;">프로젝트
															나가기</a>
													</button>
												</div>
											</div>

										</c:forEach>
									</div>
								</div>
							</c:otherwise>
						</c:choose>

					</div>
				</div>

			</div>
		</section>
		<!--프로젝트 나가기   -->
		<div class="exitProjectBox">
			<div>프로젝트를 나가시겠습니까?</div>
			<div>회원님이 프로젝트의 팀장(프로젝트 생성자)일 경우에는 팀원 중 한명이 프로젝트의 팀장이 됩니다.</div>
			<div>팀원들이 모두 프로젝트에서 나간다면 프로젝트는 자동으로 삭제됩니다.</div>
			<div>
				<button>취소</button>
				<button>나가기</button>
			</div>
		</div>
	</div>

	<!-- 수정하기  -->
	<div class="row modifymodal">
		<div class="col-sm-2"></div>
		<div class="col-sm-8">

			<div class="row">
				<div class="col-4"></div>
				<div class="col-2">e-mail</div>
				<div class="col-2">${loginInfo.email}</div>
				<div class="col-4"></div>
			</div>
			<div class="row">
				<div class="col-4"></div>
				<div class="col-2">name</div>
				<div class="col-2">
					<input type="text" id="modifyname">
				</div>
				<div class="col-4"></div>
			</div>
			<div class="row">
				<div class="col-4"></div>
				<div class="col-2">password</div>
				<div class="col-4">
					<input type="password" value="${loginInfo.pw}" id="modifypw">
				</div>
				<div class="col-2"></div>
			</div>
			<div class="row">
				<div class="col-4"></div>
				<div class="col-8 btns">
					<button id="modifycancel">취소</button>
					<button id="save">저장</button>
				</div>
			</div>



		</div>
		<div class="col-sm-2"></div>
	</div>

	<!-- 탈퇴 + 프로젝트 나가기   -->
	<div class="row deletemodal">
		<div class="col-sm-2"></div>
		<div class="col-sm-8">

			<div class="row">
				<div class="col-2"></div>
				<div class="col-10">
					<i class="fas fa-exclamation-triangle"></i>회원을 탈퇴하시면 참여한 프로젝트에서
					자동으로 나가게 됩니다.
				</div>
			</div>

			<div class="row">
				<div class="col-2"></div>
				<div class="col-10">
					<i class="fas fa-exclamation-triangle"></i>프로젝트의 리더(생성자)인 경우 리더를 다른
					팀원에게 넘겨주어야 합니다.
				</div>

			</div>
			<div class="row">
				<div class="col-2"></div>
				<div class="col-10">본인확인을 위해 비밀번호를 입력해주세요.</div>
			</div>
			<div class="row">
				<div class="col-2"></div>
				<div class="col-2">password</div>
				<div class="col-5">
					<input type="password" id="deletepw">
				</div>
				<div class="col-3"></div>
			</div>
			<div class="row">
				<div class="col-3"></div>
				<div class="col-6 btns">
					<button id="deletecancel">취소</button>
					<button id="deletesave">확인</button>
				</div>
				<div class="col-3"></div>
			</div>



		</div>
		<div class="col-sm-2"></div>
	</div>


	<script>
	var peopleCount = ${respObj}
	console.log(${respObj}['9']);
		/* 회원 정보 수정하기 모달 띄우기 */
			$("#modifybtn").on("click",function(){
				$(".modifymodal").addClass('modal-open');
				$("#modifyname").val('${loginInfo.name}');
				$("#modifypw").val(""); 
			})
			$("#modifycancel").on("click",function(){
				$(".modifymodal").removeClass('modal-open');
			})
		/* 	회원정보 수정 저장 버튼 클릭 */
			$("#save").on("click",function(){
				var name = $("#modifyname").val();
				var pw = $("#modifypw").val();
				 if(name!=''&&pw!=''){
					$.ajax({
						url:"modify",
						type:"post",
						data:{
							name:name,
							pw:pw
						}
					}).done(function(resp){
						if(resp==1){
							alert("저장되었습니다.")
							$(".modifymodal").removeClass('modal-open');
						}
					}) 
				}else{
					alert("이름과 비밀번호는 필수 입력입니다.")
				} 
				
			})
			
			/* 	회원 탈퇴  모달 띄우기 */
			$("#deletebtn").on("click",function(){
				$(".deletemodal").addClass('modal-open');
			})
			$("#deletecancel").on("click",function(){
				$(".deletemodal").removeClass('modal-open');
			})
			/* 	회원 탈퇴 버튼 눌렀을 때 */
			$("#deletesave").on("click",function(){
				var pw = $("#deletepw").val();
				console.log("click");
				if(pw==''){
					alert("비밀번호를 입력해주세요 !")
				}else{
					$.ajax({
						url:"delmem",
						type:"post",
						data:{
							seq:${loginInfo.seq},
							pw:pw
						}
					}).done(function(resp){
						if(resp=="fail"){
							alert("비밀번호가 일치하지 않습니다.")
						}else if(resp=="success"){
							alert("탈퇴가 완료되었습니다. 이용해주셔서 감사합니다.");
							location.href="/";
						}
					})
				}
			})
	/* 프로젝트 나가기 모달 띄우기 */			
	function notice(project_seq){
        $(".exitProjectBox").addClass("openModal");
        $(".exitProjectBox").addClass("emphasized");
        
        $(".exitProjectBox>div>button:nth-child(1)").on("click",function(){
            $(".exitProjectBox").removeClass("openModal");
            $(".exitProjectBox").removeClass("emphasized");
            })
         $(".exitProjectBox>div>button:nth-child(2)").on("click",function(){
            $(".exitProjectBox").removeClass("openModal");
            $(".exitProjectBox").removeClass("emphasized");
            setTimeout(function() {
            	location.href="../exitProject?project_seq="+project_seq+"&mem_seq=${loginInfo.seq}"
            	}, 600);
            
            })  
    }
     
		</script>


</body>
</html>