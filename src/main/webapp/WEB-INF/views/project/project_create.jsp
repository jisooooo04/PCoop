<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="../header/cdn.jsp"></jsp:include>
<title>PCOOP!</title>
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
			<div>
				<h5>프로젝트 생성하기</h5>
			</div>
			<form action="create" method="post" id="formtag">
				<div class="form-group">
					<label>Project name</label> <input type="text" class="form-control"
						id="exampleFormControlInput1" name='name'
						placeholder='프로젝트의 이름을 입력해주세요.' required>
				</div>
				<div class="form-group">
					<label>프로젝트 참여 인원수를 설정해주세요.</label> <select class="form-control"
						name="people_num">
						<c:forEach var='i' begin='1' step='1' end='50'>
							<option value='${i}'>${i}</option>
						</c:forEach>
					</select>
					<button type="submit" class="btn btn-sm">프로젝트 생성</button>
				</div>
			</form>
		</section>
	</div>
</body>
</html>