<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>PCOOP!</title>
<jsp:include page="../header/cdn.jsp"></jsp:include>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<style>
* {
	box-sizing: border-box;
}

.container {
	border: 1px solid black;
	margin: auto;
	padding: 25px;
	font-family: 'Noto Sans KR', sans-serif;
}
/*<!-- 프로젝트 참가 요청 -->*/
.joinRequest {
	margin: auto;
	padding: 10px;
	border-radius: 10px;
	border: 2px solid #5f83ba;
	font-family: 'Noto Sans KR', sans-serif;
}

.joinRequest>div {
	text-align: center;
	margin: 6px;
	font-size: 18px;
}

.badge {
	margin-right: 10px;
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
	text-align: center;
	border: 1px solid black;
	border-radius: 15px;
	padding: 15px;
}

.projectInfo>div {
	margin: 10px;
}

.projectInfo>div:nth-child(1) {
	font-size: 35px;
	font-weight: bold;
}

.projectInfo>div:nth-child(1)>span {
	font-size: 20px;
	font-weight: 400;
	margin-left: 20px;
	color: gray;
}

.member {
	font-size: 20px;
}

.member>span {
	margin-left: -70px;
	border: 1px solid #5f83ba;
	border-radius: 10px;
	color: #4d70a8;
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
	width: 305px;
	height: 60px;
	font-weight: 500;
	background-color: #86d1b4;
	border: none;
	border-radius: 15px;
	font-size: 30px;
	color: white;
}
</style>
</head>
<body>

	<!-- 여기부터 각자 영역 설정 -->

	<div class="container">

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
					<div class="member">
						<!-- <span class="badge">생성자</span> -->
						${i.member_name}님
					</div>
					<div class="person">
						<i class="fas fa-users"></i>6/8 명
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="invite">
				<button>팀원 초대하러 가기 !</button>
			</div>
		</div>
	</div>
	<!-- 여기까지 각자 영역 설정 -->


	<script>
		
	</script>
</body>
</html>