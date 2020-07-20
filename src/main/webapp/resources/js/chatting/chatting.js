$(function(){
      updateScroll();
      updateFileScroll();
      
      var ws = new WebSocket("ws://localhost/chat");  //이 url에 소켓 연결을 요청하고, WebChat 클래스가 요청을 받음
      
      ws.onmessage = function(e){
         
         var msg = JSON.parse(e.data);
                  
         var chat_seq = msg.seq;
         var nickname = msg.nickname;
         var text = msg.text;
         var date = msg.date;
         var time = msg.time;
         
         var chat_box = $("<div class='chat_box'>");
         var profile = $("<div class='profile'>");
         var profile_img = $("<img src='resources/images/chatting/profile.png' class='profile_img'>");
         
         profile.append(profile_img);
         
         var chat_box_in = $("<div class='chat_box_in'>");
         var name = $("<div class='name'>");
         var chat = $("<div class='chat' id="+chat_seq+">");
         var timediv = $("<div class='time'>");
         
         name.append(nickname);
         chat.append(text);
         timediv.append(time);
         
         chat_box_in.append(name);
         chat_box_in.append(chat);
         chat_box_in.append(timediv);
         
         chat_box.append(profile);
         chat_box.append(chat_box_in);
         
         console.log(text);
         
         $(".chat_section").append(chat_box);
         
         
         //파일리스트에 뿌리기
         var extension = msg.extension;
         var array = ['css','docx','gif','html','java','jpg','pdf','php','png','ppt','ps','sql','txt','xls','xml','zip'];
         var file_img = $("<img src='resources/images/chatting/file.png' class=file_img>");

         for(var i = 0; i < array.length; i++) {
        	 if(extension == array[i]){
        		 file_img = $("<img src='resources/images/chatting/"+extension+".png' class=file_img>");
        		 break;
        	 }
         }
         
         var file_date_form = msg.file_date_form;  //파일리스트 출력용 날짜
         
         if(file_date_form != null){
        	 
        	 var file_img_box = $("<div class=file_img_box>");
             //var file_img = $("<img src='resources/images/chatting/pdf.png' class=file_img>");
             file_img_box.append(file_img);
             
             var file_info_box = $("<div class=file_info_box>");
             var file_name = $("<div class=file_name id="+chat_seq+">");
             var file_date = $("<div class=file_date>");
             var file_writer = $("<div class=file_writer>");
             
             file_name.append(text);
             file_date.append(file_date_form);
             file_writer.append(nickname)
             
             file_info_box.append(file_name);
             file_info_box.append(file_date);
             file_info_box.append(file_writer);
             
             var file_list = $("<div class=file_list>");
             file_list.append(file_img_box);
             file_list.append(file_info_box);
             
             $(".file_contents").append(file_list);
        	 
         }
         
         
         updateScroll();
         updateFileScroll();
         
         //페이지 로딩 후 추가된 동적 태그들에도 코드 형식 적용하기
         hljs.initHighlighting.called = false; 
         hljs.initHighlighting();
         
      }
      
      
      //글자수 바이트로 반환하는 함수
      var strByteLength = function(s,b,i,c){
    	  for(b=i=0;c=s.charCodeAt(i++);b+=c>>11?3:c>>7?2:1);
    	  return b
      }
      
      
      //엔터 버튼
      $("#input").keydown(function(e){
         if(e.keyCode == 13){  //13번 = 엔터키 누르면,
        	 
        	 if(strByteLength($("#input").html()) >= 4000){
        		 alert("보낼 수 있는 글자수를 초과했습니다.\n길이가 긴 텍스트의 경우 텍스트 파일로 첨부해주세요.");
        		 $("#input").html("");
        		 return false;
        	 }
            
        	var d = new Date();
 			
 			var week = ["일","월","화","수","목","금","토"];
 			var day = week[d.getDay()];
 			
 			var fulldate = d.toLocaleString();
 			var date = d.getFullYear()+"년 "+(d.getMonth()+1)+"월 "+d.getDate()+"일 "+day+"요일";
 			var mm = d.getMonth()+1;
 			var dd = d.getDate();
 			if(mm < 10){ mm = "0"+mm; }
 		    if(dd < 10){ dd = "0"+dd; }
 			var file_date_form = d.getFullYear()+"/"+mm+"/"+dd;
 			var time = d.toLocaleTimeString();
 			
 			var c_num = $(".chat_title").attr("id");
			var p_seq = $(".chat_person_num").attr("id");
			
			
			//메세지만 보냈으면 그냥 원래처럼 메세지 보내기
			if($("#input").html() != ""){
				var msg = {
			               type: "message",
			               p_seq: p_seq,
			               c_num: c_num,
			               text: $("#input").html(),
			               fulldate: fulldate,
			               date: date,
			               time: time
			            };				
				
				$.ajax({
					url : "fileUpload",
					type : "post",
					data : {message: JSON.stringify(msg)},
					dataType : "json"
					
				}).done(function(response) {
					ws.send(JSON.stringify(response));
				})
				
			}
			
 			
 			var fileCheck = $("#file_select").val();
 			
 			//파일이 첨부됬으면
 			if(fileCheck){
 				
 				var form = $('#fileForm')[0];
 				var formData = new FormData(form);
 				
 				//formData에 기본 메세지 정보 json 추가
 				var msg = {
						type: "message",
						p_seq: p_seq,
						c_num: c_num,
						fulldate: fulldate,
						date: date,
						time: time,  //이 아래부터 file 내용
						file: "file",  //key를 text 대신 file이라고 보냄!!
						file_date_form: file_date_form
					};
 				formData.append("message", JSON.stringify(msg));  //ChattingController로 보낼 json에 기본 메시지 정보도 추가해줌(거기서 DB 저장할거니까)
 				
 			
 				// 1) chattingController로 파일과 챗 정보 보내 DB에 저장
				$.ajax({
					url : "fileUpload",
					type : "post",
					data : formData,
					processData: false,
					contentType: false,
					dataType : "json"
						
				}).done(function(response) {
					
					ws.send(JSON.stringify(response));  //ajax로 ChattingController에 파일,메세지 정보 보내서 DB에 저장 후 webSocket으로 json 보내서 모든 채팅 참가자들의 화면에서 보이게 해줌
					
					//파일박스 내 버튼 내용이랑, 파일첨부된거 삭제하고
					$(".file_upload_box").empty();
					document.getElementById('fileForm').reset();  //file form 리셋
					
					//안보이게하기
					$(".file_box").css("display", "none");
					
				}) //done 끝!
 				
 			}
 			
			updateScroll();
			$("#input").html("");
			
			return false; //엔터쳤을때 커서가 아래로 떨어지지 않게 막아줌
            
         }
      })
      
      
      //전송 버튼
      $(document).on("click", "#send_btn", function(){
    	  
    	  if(strByteLength($("#input").html()) >= 4000){
     		 alert("보낼 수 있는 글자수를 초과했습니다.\n길이가 긴 텍스트의 경우 텍스트 파일로 첨부해주세요.");
     		 $("#input").html("");
     		 return false;
     	 }
    	  
    	  var d = new Date();
			
			var week = ["일","월","화","수","목","금","토"];
			var day = week[d.getDay()];
			
			var fulldate = d.toLocaleString();
			var date = d.getFullYear()+"년 "+(d.getMonth()+1)+"월 "+d.getDate()+"일 "+day+"요일";
			var mm = d.getMonth()+1;
			var dd = d.getDate();
			if(mm < 10){ mm = "0"+mm; }
		    if(dd < 10){ dd = "0"+dd; }
			var file_date_form = d.getFullYear()+"/"+mm+"/"+dd;
			var time = d.toLocaleTimeString();
			
			var c_num = $(".chat_title").attr("id");
			var p_seq = $(".chat_person_num").attr("id");
			
			
			//메세지만 보냈으면 그냥 원래처럼 메세지 보내기
			if($("#input").html() != ""){
				var msg = {
			               type: "message",
			               p_seq: p_seq,
			               c_num: c_num,
			               text: $("#input").html(),
			               fulldate: fulldate,
			               date: date,
			               time: time
			            };				
				
				$.ajax({
					url : "fileUpload",
					type : "post",
					data : {message: JSON.stringify(msg)},
					dataType : "json"
					
				}).done(function(response) {
					ws.send(JSON.stringify(response));
				})
				
			}
			
			
			var fileCheck = $("#file_select").val();
			
			//파일이 첨부됬으면
			if(fileCheck){
				
				var form = $('#fileForm')[0];
				var formData = new FormData(form);
				
				//formData에 기본 메세지 정보 json 추가
				var msg = {
						type: "message",
						p_seq: p_seq,
						c_num: c_num,
						fulldate: fulldate,
						date: date,
						time: time,  //이 아래부터 file 내용
						file: "file",  //key를 text 대신 file이라고 보냄!!
						file_date_form: file_date_form
					};
				formData.append("message", JSON.stringify(msg));  //ChattingController로 보낼 json에 기본 메시지 정보도 추가해줌(거기서 DB 저장할거니까)
				
			
				// 1) chattingController로 파일과 챗 정보 보내 DB에 저장
				$.ajax({
					url : "fileUpload",
					type : "post",
					data : formData,
					processData: false,
					contentType: false,
					dataType : "json"
						
				}).done(function(response) {
					
					ws.send(JSON.stringify(response));  //ajax로 ChattingController에 파일,메세지 정보 보내서 DB에 저장 후 webSocket으로 json 보내서 모든 채팅 참가자들의 화면에서 보이게 해줌
					
					//파일박스 내 버튼 내용이랑, 파일첨부된거 삭제하고
					$(".file_upload_box").empty();
					document.getElementById('fileForm').reset();  //file form 리셋
					
					//안보이게하기
					$(".file_box").css("display", "none");
					
				}) //done 끝!
				
			}
			
			updateScroll();
			$("#input").html("");
			
			return false; //엔터쳤을때 커서가 아래로 떨어지지 않게 막아줌
    	  
      })
		
		
		function updateScroll(){
			var element = document.getElementById("chat_section");
			element.scrollTop = element.scrollHeight;
		}
      
        function updateFileScroll(){
			var element = document.getElementById("file_contents_section");
			element.scrollTop = element.scrollHeight;
		}
		
		
		
		//스크롤 위로 갈 시 이전 데이터 불러오기 - 채팅방 seq랑 오늘로부터 몇일 전 날짜인지(num)보냄
      	var beforenum = 2;
      	var c_num = $(".chat_title").attr("id");
		$("#chat_section").on("scroll", function(){
			
			if($("#chat_section").scrollTop() == 0){
				
				$.ajax({
					url : "lastChat",
					type : "post",
					data : {
						beforenum : beforenum,
						c_num : c_num
					},
					dataType : "json"
					
				}).done(function(response) {
               
               //대화 내용 먼저 출력
               for (var i = 0; i < response.length; i++) {
                  
            	  var seq = response[i].seq;
                  var writer = response[i].writer;
                  var text = response[i].chat;
                  var time = response[i].time;
                  
                  var chat_box = $("<div class='chat_box'>");
                  var profile = $("<div class='profile'>");
                  var profile_img = $("<img src='resources/images/chatting/profile.png' class='profile_img'>");
                  
                  profile.append(profile_img);
                  
                  var chat_box_in = $("<div class='chat_box_in'>");
                  var name = $("<div class='name'>");
                  var chat = $("<div class='chat' id="+seq+">");
                  var timediv = $("<div class='time'>");
                  
                  name.append(writer);
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
               
               beforenum = beforenum+1;  //날짜 +1
            })
            
         }
      })
      
      
		//마우스 우클릭 브라우저 기본이벤트 변경
		var chat_id = "";
		$(document).on('contextmenu', ".chat", function() {

			event.stopPropagation();
			event.preventDefault();

			chat_id = $(this).attr("id");

			$(".chat_section").css("overflow-y", "hidden"); //스크롤 방지
			
			x = event.pageX;
			y = event.pageY;

			$("#contextmenu").css("margin-top", y + 1 + "px");
			$("#contextmenu").css("margin-left", x + 1 + "px");
			$(".copy_chat").css("display", "block");
			$("#contextmenu").css("display", "block");
			
			
			var contents = $(this).html();
			var fileStr = "fileDownload?presentFileSeq=";
			var codeStr = "<pre class=\"pre\"><code class=\"code_editor hljs"
			
			if(contents.indexOf(fileStr) >= 0 || contents.indexOf(codeStr) >= 0 ){
				$(".copy_chat").css("display", "none");
			}

		});
		
		
		//챗 우클릭 후 이외 공간 클릭시
		$(document).on("click", function() {
			$("#contextmenu").css("display", "none");
			$(".chat_section").css("overflow-y", "auto");
		})
		
		
		//1. 우클릭 > 대화 삭제
		$(document).on("click", ".delete_chat", function() {

			// chat_id = 지금 클릭한 챗
			//먼저 팝업창 띄우고, 예 누르면 ajax로 삭제 실행

			$.ajax({
				url : "deleteChat",
				type : "post",
				data : {
					seq : chat_id
				}

			}).done(function(response) {

				if (response == 1) {
					
					//파일이면 파일리스트의 file_name에 chat_seq와 동일한 id를 부여했기 때문에 parent의 parent 요소를 삭제하는게 동일
					
					//id가 chat_id인 애를 화면에서 삭제!
					$("#" + chat_id).parent().parent().remove();
					alert("삭제되었습니다.");
				}
			})
		})

		//2. 우클릭 > 복사 선택시
		$(".copy_chat").on("click", function() {
			//id가 chat_id 인 애의 내용을 가져와서 복사함

			var contents = $("#" + chat_id).html();
			$("#copy_box").val(contents);

			$("#copy_box").select();
			document.execCommand("copy");
			
		})

		//이모티콘 박스 보이기
		$(".emoticon_icon").on("click", function() {
			$(".emoticon_section").toggle();
		})

		//이모티콘 선택하면 div에 넣기
		$(".emoticon_box>.emoticon").on("click", function() {
			$(".emoticon_section").hide();
			
			var emoticon_id = $(this).attr("id");
			$("#input").append("<img src=resources/images/chatting/"+emoticon_id+" class=emoticon id="+emoticon_id+"><br>");
			$("#input").focus(); //추가하고 커서 div로 옮기기
			
			//자동줄바꿈 추가하기!
			
		})

		//코드 편집기 열기
		var code_index = 1;  // 1=코드편집기 없는 상태
		$(".code_icon").on("click", function() {
			if ($("#input").html().indexOf("</pre>") == -1) { //이 코드가 없다면
				
				$("#input").append("<pre class=pre><code class='code_editor hljs' style='overflow-x: hidden; overflow-y: auto;'></code></pre>");
				
				code_index = -1;
				
			} else if (code_index == 1) {
				var pre = $("<pre class=pre></pre>");
				var code = $("<code class='code_editor hljs' style='overflow-x: hidden'></code>");
				pre.append(code);
				$("#input").append(pre);
				
				code_index = -1;
				
			} else {
					$("#input>.pre").remove();
					$("#input>.pre>.code_editor").remove();
					
					code_index = 1;
					}
			})
			
			
		//파일첨부 아이콘 누르면 파일첨부 버튼 눌러지게
		$(".file_icon").on("click", function() {
			$("#file_select").trigger("click");

		})

		//파일 첨부되면 이벤트 발생
		$("#file_select").change(function(e) {

			var file = $("#file_select").val(); //경로+이름

			if (file) { //파일이 첨부됬으면
				$(".file_upload_box").empty();

				$(".file_box").css("display", "block");

				$(".file_upload_box").append(file);

				$("#input").focus(); //추가하고 커서 div로 옮기기

			} else { //파일 첨부 안됬으면
				$(".file_upload_box").empty();
			}
		})
		
		//파일첨부박스 x버튼 클릭시
		$(".file_delete").on("click", function(){
			//파일박스 내 버튼 내용이랑, 파일첨부된거 삭제하고
			$(".file_upload_box").empty();
			document.getElementById('fileForm').reset();  //file form 리셋
			
			//안보이게하기
			$(".file_box").css("display", "none");
		})
		
		
		//채팅방 인원 누르면 멤버 보여주기
		$(".chat_person_num").on("click", function(){
			$(".chat_people").toggle();
		})
		
		
		
		//오른쪽 파일 리스트 열기
		var fileListVisible = 1;  //1=안보이는거
		$(document).on("click", ".open_file_btn", function() {
        	if(fileListVisible == 1){
        		$(".file_list_section").animate({right:0}, 400);
        		
        		$(".chat_section").animate({paddingRight:400}, 400);
                $(".chat_input_section").animate({paddingRight:410}, 400);
                $("#send_btn").animate({right:410}, 400);
        		
        		fileListVisible = -1;
        		updateScroll();  //채팅창 스크롤 내리기
        		
        	}else if(fileListVisible == -1){
        		$(".file_list_section").animate({right:-400}, 400);
        		
        		$(".chat_section").animate({paddingRight:15}, 400);
                $(".chat_input_section").animate({paddingRight:10}, 400);
                $("#send_btn").animate({right: 10}, 400);
        		
        		fileListVisible = 1;
        	}
        	
        })
        
        //오른쪽 파일 리스트 닫기
        $(".close_file_btn").on("click", function(){
        	$(".file_list_section").animate({right:-400}, 400);
        	
        	$(".chat_section").animate({paddingRight:15}, 400);
            $(".chat_input_section").animate({paddingRight:10}, 400);
            $("#send_btn").animate({right: 10}, 400);
        	
        	fileListVisible = 1;
        })
		
        
        
//        //입력창에 붙여넣기 이벤트 발생했을 때 (span, a 태그 등 제거하기)
//        $("#input").bind('paste', function(e) {
//        	event.preventDefault();
//        	
//        	var pastedData = event.clipboardData ||  window.clipboardData;
//        	var textData = pastedData.getData('Text').replace(/\r\n/gi, '<br>');
//        	
//        	window.document.execCommand('insertHTML', false,  textData);
//        });
		
		//이모티콘박스 이외 부분 클릭시 이모티콘박스 닫히기 추가하기!!!

	})

