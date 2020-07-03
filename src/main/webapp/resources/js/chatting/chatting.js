/**
 * 
 */

	$(function(){
		var ws = new WebSocket("ws://localhost/chat");  //이 url에 소켓 연결을 요청하고, WebChat 클래스가 요청을 받음

		
		updateScroll();
		ws.onmessage = function(e){
			
			var msg = JSON.parse(e.data);
						
			var id = msg.id;
			var text = msg.text;
			var date = msg.date;
			var time = msg.time;
			
			var chat_box = $("<div class='chat_box'>");
			var profile = $("<div class='profile'>");
			var profile_img = $("<img src='resources/images/chatting/profile.png' class='profile_img'>");
			
			profile.append(profile_img);
			
			var chat_box_in = $("<div class='chat_box_in'>");
			var name = $("<div class='name'>");
			var chat = $("<div class='chat'>");
			var timediv = $("<div class='time'>");
			
			name.append(id);
			chat.append(text);
			timediv.append(time);
			
			chat_box_in.append(name);
			chat_box_in.append(chat);
			chat_box_in.append(timediv);
			
			chat_box.append(profile);
			chat_box.append(chat_box_in);
			
			$(".chat_section").append(chat_box);
			
			updateScroll();
			
		}
		
		//엔터 버튼
		$(document).on("keydown", "#input", function(e){

			if(e.keyCode == 13){  //13번 = 엔터키
				
				console.log($("#input"));

				var d = new Date();
				
				var week = ["일","월","화","수","목","금","토"];
				var day = week[d.getDay()];
				
				var fulldate = d.toLocaleString();
				var date = d.getFullYear()+"년 "+(d.getMonth()+1)+"월 "+d.getDate()+"일 "+day+"요일";
				var time = d.toLocaleTimeString();
				
				var msg = {
					type: "message",
					text: $("#input").html(),
					fulldate: fulldate,
					date: date,
					time: time
				};
				
				ws.send(JSON.stringify(msg));  //json 데이터 string으로 보내기
				
				updateScroll();
				$("#input").html("");
				
				return false; //엔터쳤을때 커서가 아래로 떨어지지 않게 막아줌
			}
		})
		
		
		//전송 버튼
		$("#send_btn").on("click", function(){

			var d = new Date();
			
			var week = ["일","월","화","수","목","금","토"];
			var day = week[d.getDay()];
			
			var fulldate = d.toLocaleString();
			var date = d.getFullYear()+"년 "+(d.getMonth()+1)+"월 "+d.getDate()+"일 "+day+"요일";
			var time = d.toLocaleTimeString();
			
			var msg = {
				type: "message",
				text: $("#input").html(),
				fulldate: fulldate,
				date: date,
				time: time
			};
			
			ws.send(JSON.stringify(msg));
			
			
			
			//formdata로 보내기
			//var form = new FormData(document.getElementById('fileForm'));
			
			//ws.send(form);
			
			
			
			updateScroll();
			$("#input").html("");
			
			return false;
		})
		
		
		function updateScroll(){
			var element = document.getElementById("chat_section");
			element.scrollTop = element.scrollHeight;
		}
		
		
		var num = 1;
		//스크롤 위로 갈 시 이전 데이터 불러오기
		$("#chat_section").on("scroll", function(){
			
			if($("#chat_section").scrollTop() == 0){
				
				$.ajax({
					url : "lastChat",
					type : "post",
					data : {
						num : num
					},
					dataType : "json"

				}).done(function(response) {
					
					//대화 내용 먼저 출력
					for (var i = 0; i < response.length; i++) {
						
						var id = response[i].writer;
						var text = response[i].chat;
						var time = response[i].time;
						
						var chat_box = $("<div class='chat_box'>");
						var profile = $("<div class='profile'>");
						var profile_img = $("<img src='resources/images/chatting/profile.png' class='profile_img'>");
						
						profile.append(profile_img);
						
						var chat_box_in = $("<div class='chat_box_in'>");
						var name = $("<div class='name'>");
						var chat = $("<div class='chat'>");
						var timediv = $("<div class='time'>");
						
						name.append(id);
						chat.append(text);
						timediv.append(time);
						
						chat_box_in.append(name);
						chat_box_in.append(chat);
						chat_box_in.append(timediv);
						
						chat_box.append(profile);
						chat_box.append(chat_box_in);
						
						$(".chat_section").prepend(chat_box);
					}
					
					//해당 날짜 출력
					var date = response[0].form_date;
					
					var chat_section = $("<div class='chat_section' id=chat_section>");
					var chat_date_box = $("<div class=chat_date_box>");
					var chat_date_btn = $("<button class=chat_date_btn>");
					
					chat_date_btn.append(date);
					chat_date_box.append(chat_date_btn);
					
					$(".chat_section").prepend(chat_date_box);  //맨위에 올라가는게 가장 마지막에!
					
					num = num+1;  //날짜 +1
				})
				
			}
		})
		
		//마우스 우클릭 브라우저 기본이벤트 변경
		var chat_id = "";
		$(".chat").on('contextmenu', function() {
			
			chat_id = $(this).attr("id");
			
			$(".chat_section").css("overflow-y", "hidden");  //스크롤 방지

			event.stopPropagation();
			event.preventDefault();

			x = event.pageX;
			y = event.pageY;

			$("#contextmenu").css("margin-top", y + 1 + "px");
			$("#contextmenu").css("margin-left", x + 1 + "px");
			$("#contextmenu").css("display", "block");
			
		});
		
		
		//챗 우클릭 후 이외 공간 클릭시
		$(document).on("click", function(){
			$("#contextmenu").css("display", "none");
			$(".chat_section").css("overflow-y", "auto");
        })
		
        
        //대화 삭제
        $(".delete_chat").on("click", function(){
        	// chat_id = 지금 클릭한 챗
        	//먼저 팝업창 띄우고, 예 누르면 ajax로 삭제 실행
        	
        	$.ajax({
        		url: "deleteChat",
        		type: "post",
        		data: {seq: chat_id}
        		
        	}).done(function(response){
        		
        		if(response==1){
        			//id가 chat_id인 애를 화면에서 삭제!
            		$("#"+chat_id).parent().parent().remove();
            		alert("삭제되었습니다.");
        		}
        	})
        })
        
        
        //이모티콘 박스 보이기
        $(".emoticon_icon").on("click", function(){
        	$(".emoticon_section").toggle();
        })
        
        
        //이모티콘 선택하면 div에 넣기
        $(".emoticon_box>.emoticon").on("click", function(){
        	$(".emoticon_section").hide();
        	
        	var emoticon_id = $(this).attr("id");
        	$("#input").append("<img src=resources/images/chatting/"+emoticon_id+" class=emoticon id="+emoticon_id+"><br>");
        })
        
        
        //코드 편집기 열기
        $(".code_icon").on("click",function(){
        	$(".pre").css("display","block");
        	$(".code_editor").css("display","block");
        })
        
        //이모티콘박스 이외 부분 클릭시 이모티콘박스 닫히기 추가하기!!!
        
	})
