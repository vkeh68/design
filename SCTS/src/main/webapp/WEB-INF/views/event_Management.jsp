<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<link href="resources/customcss/event_Management.css" rel="stylesheet" />

<style>
body {
	background: white;
}


</style>
<script>

	var bhf_code = "${bhf_code}";
	$(document).ready(
			function() {

				var date = new Date();
				var year = date.getFullYear();
				var month = date.getMonth() + 1;
				if (month < 10) {
					month = "0" + month;
				}

				var day = date.getDate();

				var options = {

					header : {
						left : 'prev,next today',
						center : 'title',
						right : 'month,basicWeek,agendaDay,listWeek'
					},
					locale : "ko",
					views : {
						month : {
							titleFormat : 'YYYY-MM',
							eventLimit : 3
						},
						week : {

							titleFormat : 'YYYY-MM, DD'

						},
						day : {
							titleFormat : 'YYYY-MM-D'
						}
					},
					displayEventEnd : true,
					listDayFormat : false,
					timeFormat : 'HH:mm',
					defaultDate : year + "-" + month + "-" + day,
					navLinks : true, // can click day/week names to navigate views
					selectable : true,
					editable : true,
					selectHelper : true,
					visible : true,
					select : registerEvent(),
					dayClick : function(date) {

						$("#registerEvent").modal();

						var start = date.format("YYYY-MM-DD HH:mm");
						start = start.replace(" ", "T");
						
						var end = date.format("YYYY-MM-DD HH:mm");
						end = end.replace(" ", "T");
						
						$("#registerEvent .eventName").val("프로토 타입 발표");
						$("#registerEvent .eventInfo").val("프로토 타입 발표입니다.");
						$("#registerEvent .eventStart").val(start);
						$("#registerEvent .eventEnd").val(end);
					},

					eventClick : function(event) {

						var code = event.id;

						$.ajax({
							type : "GET",
							url : "eventOne",
							data : {
								code : code
							},
							dataType : "json",
							success : function(data) {
								
								if(bhf_code == data.result[0].bhf_code){
									$("#delete").attr("enabled", true);
									$("#modify").attr("enabled", true);
								}else{
									$("#delete").attr("disabled", true);
									$("#modify").attr("disabled", true);
								}

								var start = data.result[0].start;

								start = start.split(".")[0].replace(" ", "T");
								start = start.split(":")[0] + ":"
										+ start.split(":")[1];

								var end = data.result[0].end;
								end = end.split(".")[0].replace(" ", "T");
								end = end.split(":")[0] + ":"
										+ end.split(":")[1];

								$("#modifyEvent .code").val(
										data.result[0].bbsctt_code);
								$("#modifyEvent .eventName").val(
										data.result[0].title);
								$("#modifyEvent .eventStart").val(start);
								$("#modifyEvent .eventEnd").val(end);
								$("#modifyEvent .eventInfo").val(
										data.result[0].bbsctt_cn);
							}

						});

						$("#modifyEvent").modal();

					},
					eventDrop : function(event) {

						var code = event.id;
						var eventStart = event.start
								.format("YYYY-MM-DD HH:mm:ss");
						var eventEnd;

						if (event.end == null) {
							eventEnd = eventStart;
						} else {
							eventEnd = event.end.subtract(24, 'hours').format(
									"YYYY-MM-DD HH:mm:ss");
						}

						//console.log(eventEnd);

						$('#calendar').fullCalendar('updateEvent', event);

						$.ajax({
							type : "GET",
							url : "updateDropEvent",
							async : false,
							data : {

								bbsctt_code : code,
								event_begin_de : eventStart,
								event_end_de : eventEnd

							}

						});

						$('#calendar').fullCalendar('removeEvents');

						viewCalendar(bhf_code);
						
						sendNoti();

					}

				}

				$('#calendar').fullCalendar(options);

				viewCalendar(bhf_code);

				/* 취소 버튼 클릭 */
				$('.closeModal').click(function() {
					
					$(".modal-layout").modal('hide');

				});

				// 이벤트 수정
				$("#modify").click(function() {

					var code = $("#modifyEvent .code").val();
					var eventName = $("#modifyEvent .eventName").val();
					var eventStart = $("#modifyEvent .eventStart").val();
					var eventEnd = $("#modifyEvent .eventEnd").val();
					eventStart = eventStart.replace("T", " ");
					eventStart = eventStart + ":00";

					eventEnd = eventEnd.replace("T", " ") + ":00";

					var eventInfo = $("#modifyEvent .eventInfo").val();

					$.ajax({
						type : "GET",
						url : "updateEvent",
						headers : {
							"Content-Type" : "application/json",
							"X-HTTP-Method-Override" : "GET"
						},
						async : false,
						data : {

							bbsctt_code : code,
							bbsctt_sj : eventName,
							bbsctt_cn : eventInfo,
							event_begin_de : eventStart,
							event_end_de : eventEnd

						},
						async : false,
						dataType : "text",
						success : function(result) {
							if (result == "success") {

								$("#modifyEvent").modal("hide");

								$('#calendar').fullCalendar('removeEvents');

								viewCalendar(bhf_code);

							}
						}

					});
					
					
					sendNoti();

				});

				// 이벤트 삭제
				$("#delete").click(function() {
					var code = $("#modifyEvent .code").val();

					$.ajax({
						type : "GET",
						url : "deleteEvent",
						headers : {
							"Content-Type" : "application/json",
							"X-HTTP-Method-Override" : "GET"
						},
						data : {

							bbsctt_code : code
						},
						async : false,
						dataType : "text",
						success : function(result) {
							if (result == "success") {

								$("#modifyEvent").modal("hide");

								$('#calendar').fullCalendar('removeEvents');

								viewCalendar(bhf_code);
								
								
								
							}
						}

					});
					
					sendNoti();

				});

			});

	function registerEvent() {

		var j = 0;

		// 등록
		$('#edit').click(function() {
			var title = $("#registerEvent .eventName").val();
			var start = $("#registerEvent .eventStart").val();
			start = start.replace("T", " ");
			start = start + ":00";

			var end = $("#registerEvent .eventEnd").val();
			end = end.replace("T", " ") + ":00";

			var user_id = "${user_id}";

			var event_info = $("#registerEvent .eventInfo").val();

			$.ajax({
				type : "POST",
				url : "insertEvent",
				headers : {
					"Content-Type" : "application/json",
					"X-HTTP-Method-Override" : "POST"
				},
				async : false,
				data : JSON.stringify({

					bbsctt_sj : title,
					bbsctt_cn : event_info,
					event_begin_de : start,
					event_end_de : end,
					bhf_code : bhf_code,
					user_id : user_id

				}),
				async : false,
				dataType : "text",
				success : function(result) {
					if (result == "success") {

						$('#calendar').fullCalendar('removeEvents');

						viewCalendar(bhf_code);

					}
				}
			});

			$("#registerEvent").modal("hide");
			
			sendNoti();

		});

	}
	
	function sendNoti(){
		
		var bhf_code = "${bhf_code}";
		
		if(bhf_code == "1"){
			
			$.ajax({
				url : "allBranch_office",
				type : "GET",
				dataType : "json",
				success : function(data){
					var length = data.result.length;
					
					for(var i = 0; i < length; i++){
						var json = JSON.stringify({
							sender : bhf_code,
							reciever : data.result[i].bhf_code
						});
						
						eventSocket.send(json);
					}
				}
				
			});
			
		}else{
			
			var json = JSON.stringify({
				sender : bhf_code,
				reciever : bhf_code
			});
			
			eventSocket.send(json);
			
		}
		
	
	}

	// 페이지 로딩시 디비에 있는 이벤트 불러옴
	function viewCalendar(bhf_code) {
		$
				.ajax({
					type : "GET",
					url : "viewCalendar",
					dataType : "json",
					data : {
						bhf_code : bhf_code
					},
					success : function(data) {

						var length = data.result.length;

						var colorArray = new Array('#FAE0D4', '#FFA7A7',
								'#F15F5F', '#00005B', '#FFBA85', '#4D48E1',
								'#F25A5A', '#008299', '#005766', '#9C8136');

						var j = 0;

						for (var i = 0; i < length; i++) {

							var start = (data.result[i].start).split(".")[0];

							var temp = (data.result[i].end).split(".")[0];

							var end1 = parseInt(temp.split("-")[0]);

							var month1 = temp.split("-")[1];

							var end2 = temp.split(" ")[0].split("-")[2];

							if (month1.match("^0")) {
								month1 = parseInt(month1.split("0")[1]);
							} else {
								month1 = parseInt(month1);
							}

							if (end2.match("^0")) {
								end2 = parseInt(end2.split("0")[1]) + 1;

							} else {
								end2 = parseInt(end2) + 1;

								if (end2 == 32) {
									end2 = 1;
									month1 = month1 + 1;

									if (month1 == 13) {
										month1 = 1;
										end1 = end1 + 1;
									}

								}

							}

							if (end2 < 10) {
								end2 = "0" + end2;

							}

							if (month1 < 10) {
								month1 = "0" + month1;
							}

							var end3 = temp.split(" ")[1];

							var end = end1 + "-" + month1 + "-" + end2 + " "
									+ end3;

							var eventData = {
								title : data.result[i].title,
								start : start,
								end : end,
								id : data.result[i].bbsctt_code,
								bbsctt_cn : data.result[i].bbsctt_cn,
								color : colorArray[j]
							}

							j++;

							if (j > 10) {
								j = 0;
							}

							$('#calendar').fullCalendar('renderEvent',
									eventData, true);

						}

					}
				});

	}
</script>

<div id="calendar"></div>



<div id="registerEvent" class="modal-layout"
	style="width: 55%; height: 440px">
	<div>
		<div class="col-lg-12">
			<section class="panel">
				<header class="panel-heading"> 이벤트 등록 </header>
				<div class="panel-body">
					<div class="form">
						<div class="form-group">
							<label for="name" class="control-label col-lg-3">이벤트명 <span
								class="required">*</span>
							</label> <input class="eventName" class="form-control" name="eventName"
								type="text" />

						</div>

						<div class="form-group ">

							<label for="price" class="control-label col-lg-3"> 이벤트
								시작일자 <span class="required">*</span>
							</label> <input class="eventStart" class="form-control" name="eventStart"
								type="datetime-local" />

						</div>

						<div class="form-group ">

							<label for="amount" class="control-label col-lg-3"> 이벤트
								종료일자 <span class="required">*</span>
							</label> <input class="eventEnd" name="eventEnd" class="form-control"
								type="datetime-local" />


						</div>


						<div class="form-group ">
							<label for="ccomment" class="control-label col-lg-3">이벤트
								설명</label>

							<textarea class="eventInfo" cols="40" class="form-control"
								name="eventInfo" cols="30" rows="5"></textarea>

						</div>

						<div class="form-group">
							<div class="col-lg-offset-2 col-lg-10">

								<button id="edit" class="btn btn-primary">등록</button>
								<button class="btn btn-default closeModal">닫기</button>
							</div>
						</div>

					</div>

				</div>
			</section>
		</div>
	</div>
</div>



<div id="modifyEvent" class="modal-layout"
	style="width: 55%; height: 440px">

	<input type="hidden" value="" class="code" />
	<div>
		<div class="col-lg-12">
			<section class="panel">
				<header class="panel-heading"> 이벤트 등록 </header>
				<div class="panel-body">
					<div class="form">
						<div class="form-group">
							<label for="name" class="control-label col-lg-3">이벤트명 <span
								class="required">*</span>
							</label> <input class="eventName" class="form-control" name="eventName"
								type="text" />

						</div>

						<div class="form-group ">

							<label for="price" class="control-label col-lg-3"> 이벤트
								시작일자 <span class="required">*</span>
							</label> <input class="eventStart" class="form-control" name="eventStart"
								type="datetime-local" />

						</div>

						<div class="form-group ">

							<label for="amount" class="control-label col-lg-3"> 이벤트
								종료일자 <span class="required">*</span>
							</label> <input class="eventEnd" name="eventEnd" class="form-control"
								type="datetime-local" />


						</div>


						<div class="form-group ">
							<label for="ccomment" class="control-label col-lg-3">이벤트
								설명</label>

							<textarea class="eventInfo" cols="40" class="form-control"
								name="eventInfo" cols="30" rows="5"></textarea>

						</div>

						<div class="form-group">
							<div class="col-lg-offset-2 col-lg-10">
								<button id="modify" class="btn btn-primary">수정</button>
								<button id="delete" class="btn btn-warning">삭제</button>
								<button class="btn btn-default closeModal">닫기</button>
							</div>
						</div>

					</div>

				</div>
			</section>
		</div>
	</div>
</div>