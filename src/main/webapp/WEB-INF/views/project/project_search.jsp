<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>PCOOP!</title>
<jsp:include page="../header/cdn.jsp"></jsp:include>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
</head>
<body>
	
		<input type="text" placeholder="초대코드를 입력해주세요.">
		<button id="searchbtn">검색</button>
		
		<div id="box"></div>

	<script>
	var project_seq;
	var project_name;
		$("#searchbtn").on("click",function(){
			$("#box").empty();
			$.ajax({
				url:"searchByCode",
				type:"post",
				data:{
					code:$("input").val()
				}
			}).done(function(resp){
				resp=JSON.parse(resp);
				var str = resp[1];
				if(str=='null'){
					if(resp[2]==resp[3]){
						$("#box").append("<div>"+resp[0].name+"</div><div> 인원수 "+resp[2]+"/"+resp[3]+"</div><button id='joinbtn' disabled>프로젝트 참여하기</button><p>인원수 다참0</p>");
					}else{
						$("#box").append("<div>"+resp[0].name+"</div><div> 인원수 "+resp[2]+"/"+resp[3]+"</div><button id='joinbtn'>프로젝트 참여하기</button>");
					}
	
				}else if(str=='y'){
					$("#box").append("<div>"+resp[0].name+"</div><div> 인원수 "+resp[2]+"/"+resp[3]+"</div><button id='joinbtn' disabled> 이미 참여중인 프로젝트입니다.</button>");
				}else if(str=='n'){
					$("#box").append("<div>"+resp[0].name+"</div><div> 인원수 "+resp[2]+"/"+resp[3]+"</div><button id='joinbtn' disabled> 참여 대기중입니다.</button>");
				}
				
				$("input").val(""); 
				project_seq=resp[0].seq;
				project_name=resp[0].name;
			}).fail(function(){
				alert("검색 결과가 없습니다.")
			})
		})
		$(document).on("click","#joinbtn",function(){
			$.ajax({
				url:"sendJoin",
				type:"post",
				data:{
					project_seq:project_seq,
					project_name:project_name
				}
			}).done(function(resp){
				if(resp==1){
					alert("참여 신청되었습니다."); 
					$("#joinbtn").html("참가신청 완료");//색깔 흐릿하게주기
					$("#joinbtn").attr("disabled",true);
				}
				
			})
			
			
		})
	</script>

</body>
</html>