/**
 * 
 */
	var bhf_code = 1;
	$(document)
			.ready(
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
								
								$("#registerEvent .eventStart").val(start);
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

										var start = data.result[0].start;

										start = start.split(".")[0].replace(
												" ", "T");
										start = start.split(":")[0] + ":"
												+ start.split(":")[1];

										var end = data.result[0].end;
										end = end.split(".")[0].replace(" ",
												"T");
										end = end.split(":")[0] + ":"
												+ end.split(":")[1];

										$("#modifyEvent .code").val(
												data.result[0].bbsctt_code);
										$("#modifyEvent .eventName").val(
												data.result[0].title);
										$("#modifyEvent .eventStart")
												.val(start);
										$("#modifyEvent .eventEnd").val(end);
										$("#modifyEvent .eventInfo").val(
												data.result[0].bbsctt_cn);
									}

								});

								$("#modifyEvent").modal();

							},
							eventDrop : function(event) {
						
								var code = event.id;
								var eventStart = event.start.format("YYYY-MM-DD HH:mm:ss");
								var eventEnd;

								if (event.end == null) {
									eventEnd = eventStart;
								} else {
									eventEnd = event.end.subtract(24, 'hours').format("YYYY-MM-DD HH:mm:ss");
								}
								
								//console.log(eventEnd);
								
								$('#calendar').fullCalendar('updateEvent', event);

							

								

								$.ajax({
									type : "GET",
									url : "updateDropEvent",
									data : {

										bbsctt_code : code,
										event_begin_de : eventStart,
										event_end_de : eventEnd

									}


								});
								
								$('#calendar').fullCalendar ('removeEvents');
								
								viewCalendar(bhf_code);

							}

						}
						
						
						$('#calendar').fullCalendar(options);

						viewCalendar(bhf_code);


						

						/* 취소 버튼 클릭 */
						$('.close').click(function() {
							$(".modal-layout").modal('hide');

						});

						// 이벤트 수정
						$("#modify")
								.click(
										function() {

											var code = $("#modifyEvent .code")
													.val();
											var eventName = $(
													"#modifyEvent .eventName")
													.val();
											var eventStart = $(
													"#modifyEvent .eventStart")
													.val();
											var eventEnd = $(
													"#modifyEvent .eventEnd")
													.val();
											eventStart = eventStart.replace(
													"T", " ");
											eventStart = eventStart + ":00";

											eventEnd = eventEnd.replace("T",
													" ")
													+ ":00";

											var eventInfo = $(
													"#modifyEvent .eventInfo")
													.val();

											$
													.ajax({
														type : "GET",
														url : "updateEvent",
														headers : {
															"Content-Type" : "application/json",
															"X-HTTP-Method-Override" : "GET"
														},
														data : {

															bbsctt_code : code,
															bbsctt_sj : eventName,
															bbsctt_cn : eventInfo,
															event_begin_de : eventStart,
															event_end_de : eventEnd

														},
														dataType : "text",
														success : function(
																result) {
															if (result == "success") {

																$(
																		"#modifyEvent")
																		.modal(
																				"hide");

																
																$('#calendar').fullCalendar ('removeEvents');
																
																viewCalendar(bhf_code);

															}
														}

													});

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
								dataType : "text",
								success : function(result) {
									if (result == "success") {

										$("#modifyEvent").modal("hide");

										$('#calendar').fullCalendar ('removeEvents');
										
										viewCalendar(bhf_code);

									}
								}

							});
						});

					});

	function registerEvent() {

		var j = 0;

		// 등록
		$('#edit').click(
				function() {
					var title = $("#registerEvent .eventName").val();
					var start = $("#registerEvent .eventStart").val();
					start = start.replace("T", " ");
					start = start +":00";

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
						data : JSON.stringify({

							bbsctt_sj : title,
							bbsctt_cn : event_info,
							event_begin_de : start,
							event_end_de : end,
							bhf_code : bhf_code,
							user_id : user_id

						}),
						dataType : "text",
						success : function(result) {
							if (result == "success") {

										
								$('#calendar').fullCalendar ('removeEvents');
							
								viewCalendar(bhf_code);

							}
						}
					});

					$("#registerEvent").modal("hide");

				});

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
							
							if(month1.match("^0")){
								month1 = parseInt(month1.split("0")[1]);
							}else{
								month1 = parseInt(month1);
							}
					
							if(end2.match("^0")){
								end2 = parseInt(end2.split("0")[1]) + 1;

							}else{
								end2 = parseInt(end2) + 1;
								
								if(end2 == 32)
								{
									end2 = 1;
									month1 = month1 + 1;
									
									if(month1 == 13){
										month1 =  1;
										end1 = end1 + 1;
									}
									
								}
								
							
							}
							
							
							if(end2 < 10){
								end2 = "0"+end2;
								
							}
							
							
							
							if(month1 < 10){
								month1 = "0" + month1;
							}

							var end3 = temp.split(" ")[1];
							
						
							var end = end1 + "-" + month1 +"-" + end2 + " " + end3;
							
						
						
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