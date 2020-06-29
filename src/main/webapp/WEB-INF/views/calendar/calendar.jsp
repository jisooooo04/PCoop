<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link href='/resources/css/calendar/calendar.css' rel='stylesheet' />
<script src='/resources/js/calendar.js'></script>
<jsp:include page="../header/cdn.jsp"></jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
	integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
	crossorigin="anonymous"></script>

<script>
  document.addEventListener('DOMContentLoaded', function() {
	
    var calendarEl = document.getElementById('calendar');

    var calendar = new FullCalendar.Calendar(calendarEl, {
    	displayEventTime:false,//제목에 시간 같이 display 안되게 설정하는
    	locale: "ko", // 한국어로 설정 
      headerToolbar: {
        left: 'prev,next today',
        center: 'title',
        right: 'dayGridMonth,timeGridWeek,timeGridDay'
      },
      initialDate: '2020-06-12',
      navLinks: true, // can click day/week names to navigate views
      selectable: true,
      selectMirror: true,
      select: function(arg) {
          function date_to_str(format)
          {
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
    	
        //일정 생성하기 버튼 눌렀을 때
        $("#save").on("click",function(){
            var title = $("#recipient-name").val();
            var start = ($('#modal_date_start').val()).replace(/(.{16})/,'$1:00');
            var end = ($('#modal_date_end').val()).replace(/(.{16})/,'$1:00');
            var color = $('input[type=radio][name=color]:checked').val();
     	
            $.ajax({
            	url:"addEvent",
            	type:"post",
            	data:{
            		title : title,
            		start_date : start,
            		end_date: end ,
            		contents : $("#message-text").val(),
            		writer : 'writer',
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
        })
        
        calendar.unselect();
      },
      eventClick: function(arg) {
    	var seq = arg.event.id;
    	$.ajax({
    		url : "selectEvent",
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
  			
  			$('.dialog__content h4').append('<div>'+title+'</div>'); 
  			$('.dialog__content>div:nth-child(2)').append('<div>'+contents+'</div>');
  			$('.dialog__content>div:nth-child(3)').append('<div>'+start_date+'</div>');
  			$('.dialog__content>div:nth-child(5)').append('<div>'+end_date+'</div>');
  			$('.dialog__content>div:nth-child(6)').append('<div>'+writer+'</div>');
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
        	    
        	    $('.dialog__content h4>div').remove(); 
     			 $('.dialog__content>div:nth-child(2)>div').remove(); 
        	}

        	detectBackdropClick = (event) => {
        	    if(event.target === modal) {
        	        closeModal();
        	        
        	     /*    $('.dialog__content h4').append(''); 
         			$('.dialog__content>div:nth-child(2)>div').append(''); */
        	    }
        	}
    	})
    	
    	
      },
      editable: true,
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
   
    calendar.render();
  });

</script>
<meta charset="UTF-8">
<title>Calendar</title>
<style>
#calendar {
	max-width: 1100px;
	margin: 0 auto;
}
</style>
</head>
<body>

	<!-- Header -->
	<jsp:include page="../header/header.jsp"></jsp:include>
	<!-- 왼쪽 사이드바 -->
	<jsp:include page="../header/sidebar-left.jsp"></jsp:include>

	<section>

		<div id="container">
			<!-- 여기부터 각자 영역 설정 -->
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
											<input type="radio" id="color-1" name="color" value="green"
												checked> <label for="color-1"> <span>
													<img
													src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/242518/check-icn.svg"
													alt="Checked Icon" />
											</span>
											</label>
										</div>

										<div>
											<input type="radio" id="color-2" name="color" value="blue">
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
											<input type="radio" id="color-4" name="color" value="red">
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
								<button type="button" class="btn btn-secondary"
									data-dismiss="modal">취소</button>
								<button type="button" class="btn btn-primary" id="save">완료</button>
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
							<i class="far fa-edit"></i>
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
							<div>~ </div>
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

		</div>

	</section>




</body>
</html>