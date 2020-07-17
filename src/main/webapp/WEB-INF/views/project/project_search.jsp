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
	*{
        box-sizing: border-box;
    }
     h2{
        text-align-last: center;
        font-family: 'Noto Sans KR', sans-serif; 
        margin-top: 110px;
        margin-bottom: 20px;
        color: darkslategrey;
    }
    .searchbox{
        width: 380px;
        height: 100px;
        margin: auto;
    }
    input{
        width: 300px;
        height: 50px;
        border: 2px solid #1994d1;
        border-radius: 15px;
        font-family: 'Noto Sans KR', sans-serif; 
    }
     input:focus{
        outline:none;
    }
    .btnbox{
        width: 70px;
        float: right;
        padding-top: 10px;
        color: #1994d1;
    }
    .fa-search{
        width: 100%;
        height: 100%;
    }
    .fa-search:hover{
        cursor: pointer;
    }
    #box{
        padding: 10px;
        width: 400px;
        /* border: 2px solid #1994d1; */
        /* border-radius: 10px; */

        font-family: 'Noto Sans KR', sans-serif; 
        margin: auto;
        font-size: 20px;
    }
    .fa-users{
        margin-right: 10px;
    }
    #box>div{
        margin: 6px;
        text-align-last: center;
    }
    #joinbtn{
        background-color: #5c96bd;
        color: white;
        border:none;
        width: auto;
        height: 50px;
        border-radius: 10px;
    }
    #box>p{
        text-align: center;
    }
    .fa-exclamation-circle{
        margin-right: 5px;
         color: red;
    }
   
</style>
</head>
<body>
		<jsp:include page="../header/board-header.jsp"></jsp:include>
	
		 <div class="container">
		  <h2>공유받은 초대코드로 프로젝트를 검색해보세요 :)</h2>
       <div class="searchbox">
           <input type="search"><div class="btnbox"><i id="searchbtn" class="fas fa-search fa-2x"></i></div>
       </div>
       <div id="box">
       </div>
   </div>
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
					if(resp[4]>=10){
						alert("프로젝트 참여는 10개까지만 가능합니다.")
					}else{
						if(resp[2]==resp[3]){
							$("#box").append("<div>"+resp[0].name+"</div><div><i class='fas fa-users'></i>"+resp[2]+"/"+resp[3]+"</div><p><i class='fas fa-exclamation-circle'></i>인원수 초과</p><div><button id='joinbtn' style='opacity:20%;' disabled>프로젝트 참여하기</button></div>");
						}else{
							$("#box").append("<div>"+resp[0].name+"</div><div><i class='fas fa-users'></i>"+resp[2]+"/"+resp[3]+"</div><div><button id='joinbtn'>프로젝트 참여하기</button></div>");
						}
					}
					
	
				}else if(str=='y'){
					$("#box").append("<div>"+resp[0].name+"</div><div><i class='fas fa-users'></i>"+resp[2]+"/"+resp[3]+"</div><div><button id='joinbtn' disabled> 이미 참여중인 프로젝트입니다.</button></div>");
				}else if(str=='n'){
					$("#box").append("<div>"+resp[0].name+"</div><div><i class='fas fa-users'></i>"+resp[2]+"/"+resp[3]+"</div><div><button id='joinbtn' disabled> 참여 대기중입니다.</button></div>");
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
					$("#joinbtn").html("참가신청 완료");
					$("#joinbtn").attr("disabled",true);
					$("#joinbtn").css("opacity","50%");
				}
				
			})
			
			
		})
		

	</script>

</body>
</html>