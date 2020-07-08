<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href='/resources/css/calendar/calendar.css?after' rel='stylesheet' />
<script src='/resources/js/calendar/calendar.js'></script>
<jsp:include page="../header/cdn.jsp"></jsp:include>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<!-- Lobi List Default installation-->
<link rel="stylesheet"
	href="/resources/lobilist-master/lib/jquery/jquery-ui.min.css" />
<link rel="stylesheet"
	href="/resources/lobilist-master/lib/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="/resources/lobilist-master/dist/lobilist.min.css">

<link rel="stylesheet"
	href="/resources/lobilist-master/lib/lobibox/css/lobibox.min.css">
<link rel="stylesheet"
	href="/resources/lobilist-master/lib/highlight/github.css">
<!--<link rel="stylesheet" href="resources/lobilist-master/demo/demo.css">-->
<style>
#box {
	margin: 10px 0px 0px 10px;
}

#calendar {
	max-width:1100px;
	margin: 0 auto;
}
#todoListBox{
	width:100%;
}
#calendarBox{
	margin-top:100px;
	width:1200px;
}
</style>


</head>




<body>

	<script>
  document.addEventListener('DOMContentLoaded', function() {
	
    var calendarEl = document.getElementById('calendar');
	
    var calendar = new FullCalendar.Calendar(calendarEl, {
    	height:200,
    	displayEventTime:false,//제목에 시간 같이 display 안되게 설정하는
    	locale: "ko", // 한국어로 설정 
    	initialView:'dayGridWeek',
      	headerToolbar: {
        left: 'prev,next today',
        center: 'title',
        right: 'dayGridWeek,dayGridDay'
      },
      navLinks: true, // can click day/week names to navigate views
      selectable: true,
      selectMirror: true,
      select: function(arg) {
          function date_to_str(format)
          {//날짜형식 내가 원하는 대로 바꾸는 함수 
              var year = format.getFullYear();
              var month = format.getMonth() + 1;
              if(month<10) month = '0' + month;
              var date = format.getDate();
              if(date<10) date = '0' + date;
              var hour = format.getHours();
              if(hour<10) hour = '0' + hour;
              var min = format.getMinutes();
              if(min<10) min = '0' + min;
             
		       return year + "-" + month + "-" + date + "T" + hour + ":" + min;
          }
          var arg_time = date_to_str(arg.start); //내가 클릭한 날짜
    	//modal 띄우기 
        $("#myModal").modal();
        $("#modal_date_start").val(arg_time);
    	//일정 끝나는 시간이 시작시간보다 빠르면 바꿔주는 이벤트 - 시작시간 선택 후 끝나는 시간 선택 했을 때
    	$("#modal_date_end").focusout(function(){
    		if(($("#modal_date_end").val()).replace(/[^0-9]/g,"")<($("#modal_date_start").val()).replace(/[^0-9]/g,"")){
    		$("#modal_date_end").val($("#modal_date_start").val());
    	}
    	})
    	
    		//일정 끝나는 시간이 시작시간보다 빠르면 바꿔주는 이벤트 - 끝나는 시간 선택 후 시작 시간 선택 했을 때
    	$("#modal_date_start").focusout(function(){
    			if(($("#modal_date_start").val()).replace(/[^0-9]/g,"")>($("#modal_date_end").val()).replace(/[^0-9]/g,"")){
    	    		if($("#modal_date_end").val()==''){
    	    			
    	    		} else{
    	    			$("#modal_date_start").val($("#modal_date_end").val()); 
    	    		}	
    		} 	
    	})
    	
       
        
        calendar.unselect();
      },
      eventClick: function(arg) {
    	var seq = arg.event.id;
    	$.ajax({
    		url : "../calendar/selectEvent",
    		type: "post",
    		data:{seq:seq}
    	}).done(function(resp){
    		resp=JSON.parse(resp);
    		var seq=resp.seq;
  			var projcet_seq=resp.project_seq;
  			var title = resp.title;
  			var contents = resp.contents;
  			var writer = resp.writer;
  			var start_date=resp.start_date;
  			var end_date=resp.end_date;
  			var color=resp.color;
  			
  			$('.dialog__content h4').append('<div class='+'dynamic'+'>'+title+'</div>'); 
  			$('.dialog__content>div:nth-child(2)').append('<div class="dynamic" style="width:270px;">'+contents+'</div>');
  			$('.dialog__content>div:nth-child(4)').append('<div class='+'dynamic'+'>'+start_date+'</div>');
  			$('.dialog__content>div:nth-child(4)').after('<div class='+'dynamic'+' style="margin-left:80px">'+end_date+'</div>');
  			$('.dialog__content>div:nth-child(6)').append('<div class="dynamic" style="float:left">'+writer+'</div>');
  			
  			$('#title-color').css('background-color',color);
  			
    		const modal = document.querySelector('dialog');
        	const btnClose = document.querySelectorAll('.button-close');
        	modal.showModal();
        	btnClose.forEach((elm) => elm.addEventListener('click', () => closeModal()));
        	modal.addEventListener('click', (e) => detectBackdropClick(e));
      
        	closeModal = () => {
        	    modal.classList.add("dialog__animate-out");
        	    modal.addEventListener('animationend', handleClose, false);
        	   
        	}

        	handleClose = () => {
        	    modal.classList.remove("dialog__animate-out");
        	    modal.removeEventListener('animationend', handleClose, false);
        	    modal.close();
        	    $('.dynamic').remove(); 
        	}

        	detectBackdropClick = (event) => {
        	    if(event.target === modal) {
        	        closeModal();
        	    }
        	}
        	
        	 $('#eventEdit').on('click',function(){    		 
        		 closeModal();	
        		 //수정하기 modal에  값 채워넣기 
        		 $('#Editrecipient-name').val(title);
        		 $("#Editmessage-text").val(contents)
        		 $('#Editmodal_date_start').val(start_date);
        		 $('#Editmodal_date_end').val(end_date);
        		 /* $('.Editcustom-radios input[type=radio][name=Editcolor][background-color:'+color+']:checked'); */
        		 //색깔 선택 수정하기 !!!!!!
        		 sessionStorage.setItem("seq",seq);
            	 $("#EditmyModal").modal();
        		 
            }) 
            
            $('#eventDelete').on("click",function(){//일정 삭제하기
            	sessionStorage.setItem("seq",seq);
            	closeModal();
            	$('#deleteModal').modal();
            })
            
            
    	})	
    
      },
     /*  editable: true, */
      dayMaxEvents: true, // allow "more" link when too many events
      events: [
    	   <c:forEach var="i" items="${list}">
         	{
         		id:'${i.seq}',
         		title:'${i.title}',
         		start:'${i.start_date}',
         		end:'${i.end_date}',
         		color:'${i.color}'
         	},
         </c:forEach>	 
      ]
    });
   
    //일정 생성하기 버튼 눌렀을 때 : 이벤트 안에 이벤트를 넣으면 버그가 일어난다!  
	   $("#save").on("click",function(){
        var title = $("#recipient-name").val();
        var start = $('#modal_date_start').val();
        var end =$('#modal_date_end').val(); 
        var color = $('input[type=radio][name=color]:checked').val();
 		
        if(title==''){
        	alert("제목은 필수 입력값 입니다!");
        	
        }else{
        	$.ajax({
            	url:"../calendar/addEvent",
            	type:"post",
            	data:{
            		title : title,
            		start_date : start,
            		end_date: end,
            		contents : $("#message-text").val(),
            		writer : '${loginInfo.name}',
            		color : color,
            		poject_seq:0
            	}
            }).done(function(resp){
           		resp=JSON.parse(resp);
           		var seq = resp.seq
           		if(title){
                    calendar.addEvent({
                    	id:seq,
                        title:title,
                        start:start,
                        end:end,
                        color:color
                    })
                }
           		
            })
            /*   modal clean 하기 */
            $("#recipient-name").val("");
            $('#modal_date_start').val("");
            $('#modal_date_end').val("");
            $("#message-text").val("");
            $("#modal_select").val('red');
    			
            
            /* modal hide  */
            $("#myModal").modal('hide');
        }
        
        
        
        
    
    })
    
 //ㅡㅡㅡㅡㅡㅡㅡㅡ일정 수정하기 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
  $('#Editsave').on("click",function(){//수정완료 버튼 
	       			
        	var seq = sessionStorage.getItem("seq");          
       		var Edittitle = $('#Editrecipient-name').val();
       		var Editstart = $('#Editmodal_date_start').val();
       		var Editend = $('#Editmodal_date_end').val();
       		var Editcolor = $('input[type=radio][name=Editcolor]:checked').val();
       		
       	 if(Edittitle==''){
         	alert("제목은 필수 입력값 입니다!");
         	
         }else{
        	 $.ajax({
        			url:'../calendar/EditEvent',
        			type:'post',
        			data:{ 
        			seq:seq, //수정하려면 seq 필요 . 맨 첫번째 줄에서 seq 선언. 
        			title:Edittitle,
        			start_date:Editstart,
          			end_date:Editend,
          			contents: $("#Editmessage-text").val(),
          			writer:'${loginInfo.name}',
          			color: Editcolor,
          			project_seq:0
        			}		
        		}).done(function(resp){	
        	       
        		   //일단 삭제 하고 
        		   var eventObj = calendar.getEventById(seq);
        			eventObj.remove(); 
 	
        		  if(Edittitle){//다시 만든다 똑같은 id(seq) 써주기
        			 	 calendar.addEvent({
        					id:seq,
        					title:Edittitle,
        					start:Editstart,
        					end:Editend,
        					color:Editcolor
        				})
        			}    
        			
        		
        			
        			    //modal clean 하기 
                 $("#Editrecipient-name").val("");
                 $('#Editmodal_date_start').val("");
                 $('#Editmodal_date_end').val("");
                 $("#Editmessage-text").val("");
                 $("#Editmodal_select").val('red');
        				
                  
                 /* modal hide  */
                   $("#EditmyModal").modal('hide'); 
                   sessionStorage.removeItem('seq');
                 
        		 })
         }
       		
       		
       		  
       	}) 
             //ㅡㅡㅡㅡㅡㅡㅡㅡ일정 삭제하기ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ	         
                   $("#deletebtn").on("click",function(){                  
                	   var seq = sessionStorage.getItem("seq"); 
                      $.ajax({
                         url:'../calendar/DeleteEvent',
                         type:'post',
                         data:{seq:seq}
                      }).done(function(resp){
                    	  sessionStorage.removeItem('seq');
                      })
                  	var eventObj = calendar.getEventById(seq);
             			eventObj.remove();
                   }) 
                  
   calendar.render(); 
  });

</script>

	<!-- Header -->
	<jsp:include page="../header/header.jsp"></jsp:include>
	<!-- 왼쪽 사이드바 -->
	<jsp:include page="../header/sidebar-left.jsp"></jsp:include>





	<section id="box">
		<div id="wrapper">
			<div id="todoListBox">
				<!--Actions by ajax		-->

				<div id="actions-by-ajax">

					<!--진행률 바-->
					<div class="progress" id="progress">
						<div id="selector"
							class="progress-bar progress-bar-striped active"
							role="progressbar" aria-valuenow="60" aria-valuemin="0"
							aria-valuemax="100" style="width:${bar}%">
							<span class="sr-only">45% Complete</span>
						</div>
					</div>

					<!--불러올 데이터 없을 때 생성 버튼	-->
					<div id="create_list" style="display: none;">
						<button id="create_btn" type="button"
							class="btn btn-default btn-xs">
							<i class="glyphicon glyphicon-plus"></i>
						</button>
					</div>
				</div>
			</div>

			<div id="calendarBox">

			<div id='calendar'></div>
		
				<!--ㅡㅡㅡㅡㅡ 일정 생성하기 modal ㅡㅡㅡㅡㅡㅡㅡ-->
			<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
				aria-labelledby="exampleModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="exampleModalLabel">일정 생성하기</h5>
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
						<form id="modal_form" action="">
							<div class="modal-body">

								<div class="form-group">
									<label for="recipient-name" class="col-form-label">일정
										제목 </label> <input type="text" class="form-control"
										id="recipient-name" name="title">
								</div>
								<div class="form-group">
									<label for="message-text" class="col-form-label">일정 내용
									</label>
									<textarea class="form-control" id="message-text"></textarea>
								</div>
								<div class="form-group">
									<label for="message-text" class="col-form-label"> 일정 기간
									</label> <br>
									<div>

										<!--  ㅡㅡㅡㅡㅡㅡㅡ	bootstrap -icon ㅡㅡㅡㅡㅡ -->
										<svg class="bi bi-alarm" width="1.5em" height="1.5em"
											viewBox="0 0 16 16" fill="currentColor"
											xmlns="http://www.w3.org/2000/svg">
                                    <path fill-rule="evenodd"
												d="M8 15A6 6 0 1 0 8 3a6 6 0 0 0 0 12zm0 1A7 7 0 1 0 8 2a7 7 0 0 0 0 14z" />
                                    <path fill-rule="evenodd"
												d="M8 4.5a.5.5 0 0 1 .5.5v4a.5.5 0 0 1-.053.224l-1.5 3a.5.5 0 1 1-.894-.448L7.5 8.882V5a.5.5 0 0 1 .5-.5z" />
                                    <path
												d="M.86 5.387A2.5 2.5 0 1 1 4.387 1.86 8.035 8.035 0 0 0 .86 5.387zM11.613 1.86a2.5 2.5 0 1 1 3.527 3.527 8.035 8.035 0 0 0-3.527-3.527z" />
                                    <path fill-rule="evenodd"
												d="M11.646 14.146a.5.5 0 0 1 .708 0l1 1a.5.5 0 0 1-.708.708l-1-1a.5.5 0 0 1 0-.708zm-7.292 0a.5.5 0 0 0-.708 0l-1 1a.5.5 0 0 0 .708.708l1-1a.5.5 0 0 0 0-.708zM5.5.5A.5.5 0 0 1 6 0h4a.5.5 0 0 1 0 1H6a.5.5 0 0 1-.5-.5z" />
                                    <path d="M7 1h2v2H7V1z" />
                                </svg>
										<!--  ㅡㅡㅡㅡㅡㅡㅡ	/bootstrap -icon ㅡㅡㅡㅡㅡ -->
										<input type="datetime-local" id="modal_date_start"
											name="start_date">
										<!-- 	<input type="time" id="start_time">  -->
										부터 <br>
										<!--  ㅡㅡㅡㅡㅡㅡㅡ	bootstrap -icon ㅡㅡㅡㅡㅡ -->
										<svg class="bi bi-alarm" width="1.5em" height="1.5em"
											viewBox="0 0 16 16" fill="currentColor"
											xmlns="http://www.w3.org/2000/svg">
                                <path fill-rule="evenodd"
												d="M8 15A6 6 0 1 0 8 3a6 6 0 0 0 0 12zm0 1A7 7 0 1 0 8 2a7 7 0 0 0 0 14z" />
                                <path fill-rule="evenodd"
												d="M8 4.5a.5.5 0 0 1 .5.5v4a.5.5 0 0 1-.053.224l-1.5 3a.5.5 0 1 1-.894-.448L7.5 8.882V5a.5.5 0 0 1 .5-.5z" />
                                <path
												d="M.86 5.387A2.5 2.5 0 1 1 4.387 1.86 8.035 8.035 0 0 0 .86 5.387zM11.613 1.86a2.5 2.5 0 1 1 3.527 3.527 8.035 8.035 0 0 0-3.527-3.527z" />
                                <path fill-rule="evenodd"
												d="M11.646 14.146a.5.5 0 0 1 .708 0l1 1a.5.5 0 0 1-.708.708l-1-1a.5.5 0 0 1 0-.708zm-7.292 0a.5.5 0 0 0-.708 0l-1 1a.5.5 0 0 0 .708.708l1-1a.5.5 0 0 0 0-.708zM5.5.5A.5.5 0 0 1 6 0h4a.5.5 0 0 1 0 1H6a.5.5 0 0 1-.5-.5z" />
                                <path d="M7 1h2v2H7V1z" />
                                </svg>
										<!--  ㅡㅡㅡㅡㅡㅡㅡ	/bootstrap -icon ㅡㅡㅡㅡㅡ -->
										<input type="datetime-local" id="modal_date_end" id="end_date">
										<!-- <input type="time" name="end_time"> -->
										까지
									</div>
								</div>
								<div class="form-group">
									<label for="message-text" class="col-form-label">색깔 지정</label>
									<br>

									<!-- ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ color radio -->
									<div class="custom-radios">
										<div>
											<input type="radio" id="color-1" name="color" value="#2ecc71"
												checked> <label for="color-1"> <span>
													<img
													src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/242518/check-icn.svg"
													alt="Checked Icon" />
											</span>
											</label>
										</div>

										<div>
											<input type="radio" id="color-2" name="color" value="#3498db">
											<label for="color-2"> <span> <img
													src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/242518/check-icn.svg"
													alt="Checked Icon" />
											</span>
											</label>
										</div>

										<div>
											<input type="radio" id="color-3" name="color" value="#f5ce42">
											<label for="color-3"> <span> <img
													src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/242518/check-icn.svg"
													alt="Checked Icon" />
											</span>
											</label>
										</div>

										<div>
											<input type="radio" id="color-4" name="color" value="#e74c3c">
											<label for="color-4"> <span> <img
													src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/242518/check-icn.svg"
													alt="Checked Icon" />
											</span>
											</label>
										</div>
									</div>
									<!-- ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ -->

								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-light"
									data-dismiss="modal">취소</button>
								<button type="button" class="btn btn-info" id="save">완료</button>
							</div>
						</form>
					</div>
				</div>
			</div>
			<!-- ㅡㅡㅡㅡㅡㅡㅡ일정 조회하기ㅡㅡㅡㅡㅡㅡㅡ -->
			<div class="row">
				<div class="col-3">
					<dialog>
					<div class="dialog__inner">
						<div>
							<i class="far fa-edit" id="eventEdit"></i>
							
							<i class="far fa-trash-alt" id="eventDelete"></i>
							<button class="button button-close">╳</button>
						</div>

						<div class="dialog__content">
							<div>
								<div id="title-color"></div>
								<h4></h4>
							</div>
							<div>
								<i class="fas fa-bars"></i>
								
							</div><br>
							<div>
								<i class="far fa-clock"></i>
							</div>
							
							<div>
								<i class="fas fa-user-edit"></i>
							</div>
							<footer class="dialog__footer">
								<button class="button button-close">확인</button>
							</footer>
						</div>
					</div>
					</dialog>
				</div>
			</div>
			<!-- ㅡㅡㅡㅡㅡㅡㅡ/일정 조회하기ㅡㅡㅡㅡㅡㅡㅡ -->
			
			  <!--ㅡㅡㅡㅡㅡ 일정 수정하기 modal ㅡㅡㅡㅡㅡㅡㅡ-->
			<div class="modal fade" id="EditmyModal" tabindex="-1" role="dialog"
				aria-labelledby="exampleModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="EditexampleModalLabel">일정 수정하기</h5>
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
						<form id="Editmodal_form" action="">
							<div class="modal-body">

								<div class="form-group">
									<label for="recipient-name" class="col-form-label">일정
										제목 </label> <input type="text" class="form-control"
										id="Editrecipient-name" name="title">
								</div>
								<div class="form-group">
									<label for="message-text" class="col-form-label">일정 내용
									</label>
									<textarea class="form-control" id="Editmessage-text"></textarea>
								</div>
								<div class="form-group">
									<label for="message-text" class="col-form-label"> 일정 기간
									</label> <br>
									<div>

										<!--  ㅡㅡㅡㅡㅡㅡㅡ	bootstrap -icon ㅡㅡㅡㅡㅡ -->
										<svg class="bi bi-alarm" width="1.5em" height="1.5em"
											viewBox="0 0 16 16" fill="currentColor"
											xmlns="http://www.w3.org/2000/svg">
                                    <path fill-rule="evenodd"
												d="M8 15A6 6 0 1 0 8 3a6 6 0 0 0 0 12zm0 1A7 7 0 1 0 8 2a7 7 0 0 0 0 14z" />
                                    <path fill-rule="evenodd"
												d="M8 4.5a.5.5 0 0 1 .5.5v4a.5.5 0 0 1-.053.224l-1.5 3a.5.5 0 1 1-.894-.448L7.5 8.882V5a.5.5 0 0 1 .5-.5z" />
                                    <path
												d="M.86 5.387A2.5 2.5 0 1 1 4.387 1.86 8.035 8.035 0 0 0 .86 5.387zM11.613 1.86a2.5 2.5 0 1 1 3.527 3.527 8.035 8.035 0 0 0-3.527-3.527z" />
                                    <path fill-rule="evenodd"
												d="M11.646 14.146a.5.5 0 0 1 .708 0l1 1a.5.5 0 0 1-.708.708l-1-1a.5.5 0 0 1 0-.708zm-7.292 0a.5.5 0 0 0-.708 0l-1 1a.5.5 0 0 0 .708.708l1-1a.5.5 0 0 0 0-.708zM5.5.5A.5.5 0 0 1 6 0h4a.5.5 0 0 1 0 1H6a.5.5 0 0 1-.5-.5z" />
                                    <path d="M7 1h2v2H7V1z" />
                                </svg>
										<!--  ㅡㅡㅡㅡㅡㅡㅡ	/bootstrap -icon ㅡㅡㅡㅡㅡ -->
										<input type="datetime-local" id="Editmodal_date_start"
											name="Editstart_date">
										<!-- 	<input type="time" id="start_time">  -->
										부터 <br>
										<!--  ㅡㅡㅡㅡㅡㅡㅡ	bootstrap -icon ㅡㅡㅡㅡㅡ -->
										<svg class="bi bi-alarm" width="1.5em" height="1.5em"
											viewBox="0 0 16 16" fill="currentColor"
											xmlns="http://www.w3.org/2000/svg">
                                <path fill-rule="evenodd"
												d="M8 15A6 6 0 1 0 8 3a6 6 0 0 0 0 12zm0 1A7 7 0 1 0 8 2a7 7 0 0 0 0 14z" />
                                <path fill-rule="evenodd"
												d="M8 4.5a.5.5 0 0 1 .5.5v4a.5.5 0 0 1-.053.224l-1.5 3a.5.5 0 1 1-.894-.448L7.5 8.882V5a.5.5 0 0 1 .5-.5z" />
                                <path
												d="M.86 5.387A2.5 2.5 0 1 1 4.387 1.86 8.035 8.035 0 0 0 .86 5.387zM11.613 1.86a2.5 2.5 0 1 1 3.527 3.527 8.035 8.035 0 0 0-3.527-3.527z" />
                                <path fill-rule="evenodd"
												d="M11.646 14.146a.5.5 0 0 1 .708 0l1 1a.5.5 0 0 1-.708.708l-1-1a.5.5 0 0 1 0-.708zm-7.292 0a.5.5 0 0 0-.708 0l-1 1a.5.5 0 0 0 .708.708l1-1a.5.5 0 0 0 0-.708zM5.5.5A.5.5 0 0 1 6 0h4a.5.5 0 0 1 0 1H6a.5.5 0 0 1-.5-.5z" />
                                <path d="M7 1h2v2H7V1z" />
                                </svg>
										<!--  ㅡㅡㅡㅡㅡㅡㅡ	/bootstrap -icon ㅡㅡㅡㅡㅡ -->
										<input type="datetime-local" id="Editmodal_date_end" id="Editend_date">
										<!-- <input type="time" name="end_time"> -->
										까지
									</div>
								</div>
								<div class="form-group">
									<label for="message-text" class="col-form-label">색깔 지정</label>
									<br>

									<!-- ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ color radio -->
									<div class="Editcustom-radios">
										<div>
											<input type="radio" id="Editcolor-1" name="Editcolor" value="#2ecc71"
												checked> <label for="Editcolor-1"> <span>
													<img
													src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/242518/check-icn.svg"
													alt="Checked Icon" />
											</span>
											</label>
										</div>

										<div>
											<input type="radio" id="Editcolor-2" name="Editcolor" value="#3498db">
											<label for="Editcolor-2"> <span> <img
													src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/242518/check-icn.svg"
													alt="Checked Icon" />
											</span>
											</label>
										</div>

										<div>
											<input type="radio" id="Editcolor-3" name="Editcolor" value="#f5ce42">
											<label for="Editcolor-3"> <span> <img
													src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/242518/check-icn.svg"
													alt="Checked Icon" />
											</span>
											</label>
										</div>

										<div>
											<input type="radio" id="Editcolor-4" name="Editcolor" value="#e74c3c">
											<label for="Editcolor-4"> <span> <img
													src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/242518/check-icn.svg"
													alt="Checked Icon" />
											</span>
											</label>
										</div>
									</div>
									<!-- ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ -->

								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-light"
									data-dismiss="modal">취소</button>
								<button type="button" class="btn btn-info" id="Editsave">완료</button>
							</div>
						</form>
					</div>
				</div>
			</div>
			
			<!--ㅡㅡㅡㅡㅡ /일정 수정하기 modal ㅡㅡㅡㅡㅡㅡㅡ-->
			
				<!--ㅡㅡㅡㅡㅡ 삭제 확인 modal ㅡㅡㅡㅡㅡㅡㅡ-->

<!-- Modal -->
<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">일정 삭제</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        일정을 삭제하시겠습니까?
      </div> 
      <div class="modal-footer">
        <button type="button" class="btn btn-light" data-dismiss="modal">취소</button>
        <button type="button" class="btn btn-info" id="deletebtn" data-dismiss="modal">삭제</button>
      </div>
    </div>
  </div>
</div>
     								
<!--ㅡㅡㅡㅡㅡ /삭제 확인 modal ㅡㅡㅡㅡㅡㅡㅡ-->
		</div>
			</div>
		
		



	</section>


	<!-- Lobi List Default installation-->
	<script src="/resources/lobilist-master/lib/jquery/jquery.min.js"></script>
	<script src="/resources/lobilist-master/lib/jquery/jquery-ui.min.js"></script>
	<script
		src="/resources/lobilist-master/lib/jquery/jquery.ui.touch-punch-improved.js"></script>
	<script
		src="/resources/lobilist-master/lib/bootstrap/js/bootstrap.min.js"></script>

	<script
		src="/resources/lobilist-master/dist/lobilist.js?v=<%=System.currentTimeMillis()%>"></script>

	<script src="/resources/lobilist-master/lib/lobibox/js/lobibox.min.js"></script>
	<script
		src="/resources/lobilist-master/lib/highlight/highlight.pack.js"></script>
	<!-- <script src="resources/lobilist-master/demo/demo.js"></script>-->

	<script>
		$(function() {
			
			
			function isEmpty(param) {
				  return Object.keys(param).length === 0;
			}

			// 할일 리스트들 양식대로 불러오기 전에 데이터 존재 여부 체크
			$.ajax({
				type : 'get',
				url : '/Task/TaskAjax',
				datatype : 'json',
				success : function(data) {			
					if(isEmpty(data.lists)){
						console.log('lists가 데이터베이스에 없음!');

				          $('#actions-by-ajax').lobiList({
				                actions: {
				                    load: '/resources/lobilist-master/demo/example1/load.json',
				                    insert: '',
				                    delete: '',
				                    update: ''
				                },
				                afterItemAdd: function(){
				                    console.log(arguments);
				                },			                
				                afterListRemove: function(){
				            		console.log("afterListRemove 변화 감지!");
				    				if ($("#actions-by-ajax").find('.lobilist').text() == "") {
				    				console.log("버튼 보여라!!!");
				    				$("#create_list").css('display', 'inline-block');
				    			} else {
				    				console.log("버튼 숨겨라!!!");
				    				$("#create_list").css('display', 'none');
				    			}	
				    						                }			                
				            });
					
					}else{
						console.log('lists가 데이터베이스에 존재!');					
						 $('#actions-by-ajax').lobiList({   
				                afterListRemove: function(){
				            		console.log("afterListRemove 변화 감지!");

				    				if ($("#actions-by-ajax").find('.lobilist').text() == "") {
				    				console.log("버튼 보여라!!!");
				    				$("#create_list").css('display', 'inline-block');
				    				} else {
				    				console.log("버튼 숨겨라!!!");
				    				$("#create_list").css('display', 'none');
				    				}
				    			},
				    			afterItemDelete: function(){
				    				//ajax 코드 추가 작업진행바 (아이템 삭제시)
				    				$.ajax({
				    					type : 'get',
				    					url : '/Task/selectCount',
				    					datatype : 'json',
				    					success : function(data) {
				    						//console.log('작업진행률 : '+ data.to);
				    						$('#selector').css('width', data.to + '%');

				    					},
				    					error : function(error) {
				    						alert('data error');
				    					}
				    				});
				    				
				    			},
				                afterItemAdd: function(){
				                	//ajax 코드 추가 작업진행바 (아이템 추가시)
				    				$.ajax({
				    					type : 'get',
				    					url : '/Task/selectCount',
				    					datatype : 'json',
				    					success : function(data) {
				    						console.log('작업진행률 : '+ data.to);
				    						$('#selector').css('width', data.to + '%');

				    					},
				    					error : function(error) {
				    						alert('data error');
				    					}
				    				});
				                }
				    			
				    			
				    			
						    });	          
					}

				},
				error : function(error) {
					alert('data error');
				}
			});
			
	
			//ajax 코드 추가 작업진행바 (처음 불러올때)
			$.ajax({
				type : 'get',
				url : '/Task/selectCount',
				datatype : 'json',
				success : function(data) {
					console.log('작업진행률 : '+ data.to);
					$('#selector').css('width', data.to + '%');

				},
				error : function(error) {
					alert('data error');
				}
			});
			
			


			
			$('#create_btn').click(function (){
				console.log("버튼 클릭!");
				 //location.reload(); // 버튼 누르면 새로고침 ... 개선필요...
				 history.go(0);
			/*
				$.ajax({
					type : 'get',
					url : '/Task/TaskAjax',
					datatype : 'json',
					success : function(data) {			
				
						if(isEmpty(data.lists)){
							console.log('lists가 데이터베이스에 없음 (버튼)');
					          $('#actions-by-ajax').lobiList({
					                actions: {
					                    load: '/resources/lobilist-master/demo/example1/load.json',
					                    insert: '',
					                    delete: '',
					                    update: ''
					                },
					                afterItemAdd: function(){
					                    console.log(arguments);
					                },
					                
					                afterListRemove: function(){
					            		console.log("afterListRemove 변화 감지!");
					    				if ($("#actions-by-ajax").find('.lobilist').text() == "") {
					    				console.log("버튼 보여라!!!");
					    				$("#create_list").css('display', 'inline-block');
					    				} else {
					    				console.log("버튼 숨겨라!!!");
					    				$("#create_list").css('display', 'none');
					    				}	
					    			}
					                
					            });
					          
					          
							
						}else{
							console.log('lists가 데이터베이스에 존재(버튼)');
							
							 $('#actions-by-ajax').lobiList({

					                afterListRemove: function(){
					            		console.log("afterListRemove 변화 감지!");

					    				if ($("#actions-by-ajax").find('.lobilist').text() == "") {
					    				console.log("버튼 보여라!!!");
					    				$("#create_list").css('display', 'inline-block');
					    			} else {
					    				console.log("버튼 숨겨라!!!");
					    				$("#create_list").css('display', 'none');
					    			}
					    				}
							 				 
							    });				 	 
					          
						};

					},
					error : function(error) {
						alert('data error');
					}
				});
					
	*/
				});

	            
			});
	</script>
</body>
</html>