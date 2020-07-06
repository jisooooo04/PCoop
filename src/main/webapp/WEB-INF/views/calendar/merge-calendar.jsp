<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<link href='/resources/css/calendar/calendar.css?after' rel='stylesheet' />
<script src='/resources/js/calendar/calendar.js'></script>
<jsp:include page="../header/cdn.jsp"></jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style>
#calendar {
	max-width: 1100px;
	margin: 0 auto;
}
</style>

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
								<button type="button" class="btn btn-light" data-dismiss="modal">취소</button>
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
										<input type="datetime-local" id="Editmodal_date_end"
											id="Editend_date">
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
											<input type="radio" id="Editcolor-1" name="Editcolor"
												value="#2ecc71" checked> <label for="Editcolor-1">
												<span> <img
													src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/242518/check-icn.svg"
													alt="Checked Icon" />
											</span>
											</label>
										</div>

										<div>
											<input type="radio" id="Editcolor-2" name="Editcolor"
												value="#3498db"> <label for="Editcolor-2"> <span>
													<img
													src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/242518/check-icn.svg"
													alt="Checked Icon" />
											</span>
											</label>
										</div>

										<div>
											<input type="radio" id="Editcolor-3" name="Editcolor"
												value="#f5ce42"> <label for="Editcolor-3"> <span>
													<img
													src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/242518/check-icn.svg"
													alt="Checked Icon" />
											</span>
											</label>
										</div>

										<div>
											<input type="radio" id="Editcolor-4" name="Editcolor"
												value="#e74c3c"> <label for="Editcolor-4"> <span>
													<img
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
								<button type="button" class="btn btn-light" data-dismiss="modal">취소</button>
								<button type="button" class="btn btn-info" id="Editsave">완료</button>
							</div>
						</form>
					</div>
				</div>
			</div>

			<!--ㅡㅡㅡㅡㅡ /일정 수정하기 modal ㅡㅡㅡㅡㅡㅡㅡ-->

			<!--ㅡㅡㅡㅡㅡ 삭제 확인 modal ㅡㅡㅡㅡㅡㅡㅡ-->

			<!-- Modal -->
			<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog"
				aria-labelledby="exampleModalLabel" aria-hidden="true">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="exampleModalLabel">일정 삭제</h5>
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
						<div class="modal-body">일정을 삭제하시겠습니까?</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-light" data-dismiss="modal">취소</button>
							<button type="button" class="btn btn-info" id="deletebtn"
								data-dismiss="modal">삭제</button>
						</div>
					</div>
				</div>
			</div>


	<script>
$('#myModal').on('hidden.bs.modal', function (e) {
	 $("#recipient-name").val("");
     $('#modal_date_start').val("");
     $('#modal_date_end').val("");
     $("#message-text").val("");
     $("#modal_select").val('red');
})
</script>

</body>
</html>