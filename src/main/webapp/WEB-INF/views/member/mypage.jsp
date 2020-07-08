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
.container{
 	font-family: 'Noto Sans KR', sans-serif; 
}
.modifymodal{
	top:100%;
	position:fixed;
	background: #fff;
	width:100%;
	height:100%;
	transition: all 600ms cubic-bezier(0.86, 0, 0.07, 1);
	margin:0;
	font-family: 'Noto Sans KR', sans-serif;
	background-color: #fffefc;
}
.deletemodal{
	top:100%;
	position:fixed;
	background: #fff;
	width:100%;
	height:100%;
	transition: all 600ms cubic-bezier(0.86, 0, 0.07, 1);
	margin:0;
	font-family: 'Noto Sans KR', sans-serif;
	/* background-image:url("/resources/images/mypage/Colors Of Sky.jpg");  */
	background-color:#fffefc;
}
.modal-open{
	top:0;
}
.btns>button{
	width:80px;
	border-radius:10px;
	border:none;
	height:30px;
	font: inherit;
}
.card{
	border:none;
	border-radius:10px;
	background-color: #fcfbf7;
	opacity:80%;
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
			<!-- ㅡㅡㅡㅡㅡㅡㅡㅡㅡ프로젝트 카드  -->
			
			<div class="col-sm-8">
				<div class="row">
				<c:choose>
					<c:when test='${list_size ==0}'>
					<div class="col-4-sm m-5">
					</div>
						<div class="col-8-sm m-5">
					아직 참여한 프로젝트가 없습니다.. <br>
					프로젝트를 생성하거나 참여해보세요 !
					</div>
					</c:when>
					<c:otherwise>
						<c:forEach var="i" items="${list}">
						<div class="col-sm-6 pt-2">						
						<div class="card" style='width: 100%;'>
									<div class="card-body">
										<h5 class="card-title">${i.name}</h5>
										<p class="card-text">${i.code}</p>
										<a href="../project/goProjectHome?seq=${i.seq}" class="btn btn-outline-secondary">프로젝트 바로가기</a>
										<a href="" class="btn btn-outline-secondary" >프로젝트 나가기</a>
									</div>
								</div>	
						</div>
					</c:forEach>
					</c:otherwise>
				</c:choose>
					
				</div>
			</div>
			
		</div>
	</section>
		</div>

<!-- 수정하기  -->
		<div class="row modifymodal">
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
					<div class="col-2"><input type="text" id="modifyname"></div>
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
			<div class="col-sm-4">
				
			</div>
		</div>
		
		<!-- 탈퇴 + 프로젝트 나가기   -->
		<div class="row deletemodal">
			<div class="col-sm-2">
				
			</div>
			<div class="col-sm-8">
				
				<div class="row">
					<div class="col-2"></div>
					<div class="col-10"><i class="fas fa-exclamation-triangle"></i>회원을 탈퇴하시면 참여한 프로젝트에서 자동으로 나가게 됩니다.</div>
				</div>
				
				<div class="row">
					<div class="col-2"></div>
					<div class="col-10"><i class="fas fa-exclamation-triangle"></i>프로젝트의 리더(생성자)인 경우 리더를 다른 팀원에게 넘겨주어야 합니다.</div>
					
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
					<div class="col-4"></div>
					<div class="col-4">
						<button id="deletecancel">취소</button>
						<button id="deletesave">확인</button>
					</div>
					<div class="col-4"></div>
			</div>
				
				
			
			</div>
			<div class="col-sm-2">
				
			</div>
		</div>
		

		<script>
			$("#modifybtn").on("click",function(){
				$(".modifymodal").addClass('modal-open');
				$("#modifyname").val('${loginInfo.name}');
				$("#modifypw").val(""); 
			})
			$("#modifycancel").on("click",function(){
				$(".modifymodal").removeClass('modal-open');
			})
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
			
			$("#deletebtn").on("click",function(){
				$(".deletemodal").addClass('modal-open');
			})
			$("#deletecancel").on("click",function(){
				$(".deletemodal").removeClass('modal-open');
			})
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
			
		</script>
	
	
</body>
</html>