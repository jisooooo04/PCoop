<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>PCOOP!</title>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<style>
* {
	box-sizing: border-box;
}

.container {
	margin: auto;
	padding: 25px;
}
/*<!-- 프로젝트 참가 요청 -->*/
.joinRequest {
	margin: auto;
	padding: 10px;
	border-radius: 10px;
/* 	border: 2px solid #5f83ba; */
}

.joinRequest>div {
	font-family: 'Noto Sans KR', sans-serif;
	text-align: center;
	margin: 6px;
	font-size: 18px;
}

.badge {
	margin-right: 10px;
	background-color:#1e74eb;
}

.joinRequest button {
	width: 80px;
	height: 30px;
	margin: 5px;
	font-size: 17px;
	padding: 0;
	border-radius: 15px;
	border: none;
}

.joinRequest a {
	border-radius: 15px;
	text-decoration: none;
	color: white;
	background-color: #5f83ba;
	width: 100%;
	height: 100%;
	display: block;
}

.fa-user {
	margin-right: 10px;
	color: #5f83ba;
}
/*<!-- 프로젝트 info -->*/
.projectInfo {
	border-radius: 15px;
	padding: 15px;
	text-align:center;
}

.projectInfo>div {
	margin-top: 20px;
	font-family: 'Noto Sans KR', sans-serif;
	margin-bottom:50px;
}

.projectInfo>div:nth-child(1) {
	font-size: 35px;
	font-weight: 500;
}

.projectInfo>div:nth-child(1)>span {
	font-size: 20px;
	font-weight: 400;
	margin-left: 20px;
	color: gray;
}

.member {
	margin-top:10px;
	font-size: 18px;
}

.member>span {
   	border: 1px solid #5f83ba;
    border-radius: 10px;
    color: #4d70a8;
    background-color: white;
    line-height: 15px;
    font-size: 15px;
}

.person {
	font-size: 20px;
}

.fa-users {
	margin-right: 10px;
	color: #4d70a8;
}

.invite {
	margin: auto;
	padding-top: 20px;
}

.invite>button {
	outline:none;
	font-family: 'Noto Sans KR', sans-serif;
	width: 305px;
	height: 60px;
	font-weight: 500;
	background-color: #7dc8c9;
	border: none;
	border-radius: 15px;
	font-size: 30px;
	color: white;
}
.invite>button:hover{
	background-color:white;
	color:#7dc8c9;
	border:1px solid #7dc8c9;
	border-radius:15px;
}
.invite>button:hover>.fa-paper-plane{
	color:#7dc8c9;
}
.fa-paper-plane{
	color:white;
	margin-right:15px;
}
/* 멤버 삭제하기 (강퇴) 버튼 */
.memdel{
	width: 60px;
    height: 30px;
    border: none;
    background: #7dc8c9;
    color: white;
    font-size: 22px;
    border-radius: 10px;
    margin-left: 10px;
    outline:none;
}
.memdel:hover{
	background-color:white;
	color:#7dc8c9;
	border:1px solid #7dc8c9;
	border-radius:10px;
}
.proejctFooter{
	margin-top:200px;
	padding-left:150px;
}
.proejctFooter>img{
	max-width:100%;
	overflow:auto;
}
</style>
</head>
<body>

	<!-- 여기부터 각자 영역 설정 -->

	<div id="container" class="container-fluid">

		프로젝트 홈입니다.

		<c:choose>
			<c:when test="${loginInfo.seq==projectInfo.leader_seq}">
				<!-- 조장이라면  -->
				<c:choose>
					<c:when test="${empty list}"></c:when>
					<c:otherwise>
						<!-- 프로젝트 참가 요청이 있다면 -->
						<div class="row">
							<div class="col-sm-12">
								<div class="joinRequest">
									<div>
										<span class="badge badge-primary">New</span>${fn:length(list)}개의
										프로젝트 참여 요청이 있습니다.
									</div>
									<c:forEach var="i" items="${list}">
										<div>
											<i class="fas fa-user"></i>${i.member_name}/${i.member_email}님</div>
										<div>
											<button>
												<a
													href="accept?mem_seq=${i.member_seq}&project_seq=${projectInfo.seq}">수락</a>
											</button>
											<button>
												<a
													href="refuse?mem_seq=${i.member_seq}&project_seq=${projectInfo.seq}">거절</a>
											</button>
										</div>
									</c:forEach>
								</div>
							</div>
						</div>
					</c:otherwise>
				</c:choose>
			</c:when>
		</c:choose>
		<div class="row">
			<div class="col-sm-12">
				<div class="projectInfo">
					<div>
						final project<span>B8v08f</span>
					</div>
					<div class="memberBox">
						<c:forEach var="i" items="${member_list}" varStatus="status">
						<div class="member m${status.count}" id='${i.seq}'>
						${i.member_name} ${i.member_email}
					</div>
					</c:forEach>
					</div>
										
					<div class="person">
						<i class="fas fa-users"></i>${fn:length(member_list)}/${projectInfo.people_num}명
					</div>
				</div>
			</div>
		</div>
		<c:choose>
			<c:when test="${loginInfo.seq==projectInfo.leader_seq}">
				<!-- 조장이라면 팀원 초대하기 버튼 보이게  -->
				<div class="row">
			<div class="invite">
				<button class="inviteBtn"><i class="fas fa-paper-plane"></i>팀원 초대하기</button>
			</div>
		</div>
			</c:when>
		</c:choose>
		
		<div class="row">
			<div class="col-12 proejctFooter d-md-none d-lg-block">
				<img src="/resources/images/project/projectHomeFooter.png">
			</div>
				
		</div>
	</div>
	<!-- 여기까지 각자 영역 설정 -->

	
	<script>
	
		$(function(){
			$(".memberBox>div:nth-child(1)").append("<span class='badge'>생성자</span>");
			
			if(${loginInfo.seq}==${projectInfo.leader_seq}){
				for(var i=0;i<${fn:length(member_list)};i++){
					$(".m"+(i+2)).append("<button class='memdel'>삭제</button>");
				}
			}
		
			
			$(".memdel").on("click",function(){
				var project_mem_seq = $(".memdel").closest("div").attr('id');
				alert(project_mem_seq);
			})
			
		})
		$(".inviteBtn").on("click",function(){
			location.href="ProjectInvite?code=${projectInfo.code}&title=${projectInfo.name}";
		})
		
	</script>
</body>
</html>