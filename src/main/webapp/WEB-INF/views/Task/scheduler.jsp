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


<!-- -----------------------------------------------------------------------------------------------------------------------------------------------------------		-->
					<div id="calendarBox">
						<div id='calendar'></div>
						
						
						<!-- ㅡㅡㅡㅡㅡㅡㅡ일정 조회하기ㅡㅡㅡㅡㅡㅡㅡ -->
						<div class="row">
							<div class="col-3">
								<dialog>
								<div class="dialog__inner">
									<div>
										<i class="far fa-edit" id="eventEdit"></i> <i
											class="far fa-trash-alt" id="eventDelete"></i>
										<button class="button button-close">╳</button>
									</div>

									<div class="dialog__content">
										<div>
											<div id="title-color"></div>
											<h4></h4>
										</div>
										<div>
											<i class="fas fa-bars"></i>

										</div>
										<br>
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



					</div>
	<!-- -----------------------------------------------------------------------------------------------------------------------------------------------------------		-->
	



</body>
</html>