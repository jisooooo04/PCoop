/**
 * 
 */


		$(function() {
			
			$('#collapseOne').collapse('hide');
			// 처음부터 show 를 빼버리면 캘린더 첫화면이 깨짐 (아무 캘린더 버튼을 누르면 정상화)
			// 클래스에 show를 줘서 정상로딩 시키고 별도로 hide 시키기
			
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
				                    load: "/resources/lobilist-master/demo/load.json?v=<%=System.currentTimeMillis()%>",
				                    insert: 'insert',
				    				delete: 'delete',
				    				update: 'update'
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
				    						console.error('selectCount (아이템 삭제)');
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
				    						console.error('selectCount (아이템 추가)');
				    					}
				    				});
				                }
				    			
				    			
				    			
						    });	          
					}

				},
				error : function(error) {
					alert('프로젝트가 없습니다.');
				}
			});
			
	
			//ajax 코드 추가 작업진행바 (처음 불러올때)
			$.ajax({
				type : 'get',
				url : '/Task/selectCount',
				datatype : 'json',
				success : function(data) {
					console.log('초기 작업진행률 : '+ data.to);
					$('#selector').css('width', data.to + '%');

				},
				error : function(error) {
					console.error('selectCount (첫 load)');
				}
			});
			
			


			
			$('#create_btn').click(function (){
				console.log("버튼 클릭!");
				 //location.reload(); // 버튼 누르면 새로고침 ... 개선필요...
				 history.go(0);
		
				});

	            
			});
		
		$( "#progress" ).click(function() {
			  $( "#calendar" ).slideToggle( "slow" );
			});
		