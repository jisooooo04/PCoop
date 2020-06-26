<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link href='/resources/css/calendar.css'
	rel='stylesheet' />
<script src='/resources/js/calendar.js'></script>
<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
	integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
	crossorigin="anonymous">
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script>

  document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');

    var calendar = new FullCalendar.Calendar(calendarEl, {
        locale: "ko",
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
        $("#myModal").modal();
        $("#save").on("click",function(){
            var title = $("#recipient-name").val()
            var start = $('#modal_date_start').val();
            var end = $('#modal_date_end').val();
            $.ajax({
            	url : "addEvent",
            	type:"post",
            	data:{
            		title : title,
            		start_date : start,
            		end_date : 
					if(end<start){
            			return start
            		}else{
            			return end
            		},
            		contents : $("#message-text").val(),
            		writer : 'writer',
            		color : $("#modal_select").val()
            	}
            }).done(function(resp){
            	console.log(resp)
            })
            
            if(title){
                calendar.addEvent({
                    title:title,
                    start:start,
                    end:end
                })
            }
            $("#myModal").modal('hide');
        })
        
        calendar.unselect();
      },
      eventClick: function(arg) {
        if (confirm('Are you sure you want to delete this event?')) {
          arg.event.remove()
        }
      },
      editable: true,
      dayMaxEvents: true, // allow "more" link when too many events
      events: [
        {
          title: 'All Day Event',
          start: '2020-06-01'
        },
        {
          title: 'Long Event',
          start: '2020-06-07',
          end: '2020-06-10'
        },
        {
          groupId: 999,
          title: 'Repeating Event',
          start: '2020-06-09T16:00:00'
        },
        {
          groupId: 999,
          title: 'Repeating Event',
          start: '2020-06-16T16:00:00'
        },
        {
          title: 'Conference',
          start: '2020-06-11',
          end: '2020-06-13'
        },
        {
          title: 'Meeting',
          start: '2020-06-12T10:30:00',
          end: '2020-06-12T12:30:00'
        },
        {
          title: 'Lunch',
          start: '2020-06-12T12:00:00'
        },
        {
          title: 'Meeting',
          start: '2020-06-12T14:30:00'
        },
        {
          title: 'Happy Hour',
          start: '2020-06-12T17:30:00'
        },
        {
          title: 'Dinner',
          start: '2020-06-12T20:00:00'
        },
        {
          title: 'Birthday Party',
          start: '2020-06-13T07:00:00'
        },
        {
          title: 'Click for Google',
          url: 'http://google.com/',
          start: '2020-06-28'
        }
      ]
    });

    calendar.render();
  });

</script>
<meta charset="UTF-8">
<title>Calendar</title>
<style>

body {
	margin: 40px 10px;
	padding: 0;
	font-family: Arial, Helvetica Neue, Helvetica, sans-serif;
	font-size: 14px;
}

#calendar {
	max-width: 1100px;
	margin: 0 auto;
}
</style>
</head>
<body>

<!-- Header -->
	<%--  <jsp:include page="../header/header.jsp"></jsp:include>  --%>
	<!-- 왼쪽 사이드바 -->
	<%--  <jsp:include page="../header/sidebar-left.jsp"></jsp:include>  --%>
	
	<section>

		<div id="container">
			<!-- 여기부터 각자 영역 설정 -->
				<div id='calendar'></div>

	<!--ㅡㅡㅡㅡㅡ modal ㅡㅡㅡㅡㅡㅡㅡ-->
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
							<label for="recipient-name" class="col-form-label">일정 제목
							</label> <input type="text" class="form-control" id="recipient-name" name="title">
						</div>
						<div class="form-group">
							<label for="message-text" class="col-form-label">일정 내용 </label>
							<textarea class="form-control" id="message-text" ></textarea>
						</div>
						<div class="form-group">
							<label for="message-text" class="col-form-label"> 일정 기간 </label>
							<br>
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
								<input type="datetime-local" id="modal_date_start" name="start_date"> 
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
							<label for="message-text" class="col-form-label">색깔 지정</label> <br>
							<!-- <input type="color" value="#429ef5">-->

							<select id="modal_select">
								<option value="red"></option>
								<option value="orange"></option>
								<option value="yellow"></option>
								<option value="green"></option>
								<option value="blue"></option>
							</select>
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
				
			<!-- 여기까지 각자 영역 설정 -->
		</div>

	</section>
	



</body>
</html>