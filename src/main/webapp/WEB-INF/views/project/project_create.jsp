<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <link rel="stylesheet" href="/resources/css/main/main.css?after" /> 
 <jsp:include page="../header/cdn.jsp"></jsp:include>
<title>PCOOP!</title>

</head>
<body>
		<!-- Header -->
	<jsp:include page="../header/board_header.jsp"></jsp:include>
	 <section>
     <div> <h5>프로젝트 생성하기</h5></div>
  </section>
 <section>
      <div class="container">
  <form action="create" method="post">
  <div class="form-group">
    <label for="exampleFormControlInput1">Project name</label>
    <input type="text" class="form-control" id="exampleFormControlInput1" name='name' placeholder='프로젝트의 이름을 입력해주세요.'>
  </div>
  <div class="form-group">
    <label for="exampleFormControlSelect1">프로젝트 참여 인원수를 설정해주세요.</label>
    <select class="form-control" id="exampleFormControlSelect1" name="people_num">
      <c:forEach var='i' begin='1' step='1' end='50'>
      	<option value='${i}'>${i}</option>
      </c:forEach>
    </select>
  </div>
 <button type="submit" class="btn btn-info">프로젝트 생성</button>

</form>
   </div>
  </section> 
</body>
</html>