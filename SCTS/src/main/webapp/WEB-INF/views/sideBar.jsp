<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!Doctype html>
<html>
<head>
<meta charset="utf-8" />
<link rel="apple-touch-icon" sizes="76x76"
	href="designResources/img/apple-icon.png">
<link rel="icon" type="image/png" sizes="96x96"
	href="designResources/img/favicon.png">

<title>sideBar</title>

<meta
	content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0'
	name='viewport' />
<meta name="viewport" content="width=device-width" />


<!-- Bootstrap core CSS     -->
<link href="designResources/css/bootstrap.min.css" rel="stylesheet" />

<!-- Animation library for notifications   -->
<link href="designResources/css/animate.min.css" rel="stylesheet" />

<!--  Paper Dashboard core CSS    -->
<link href="designResources/css/paper-dashboard.css" rel="stylesheet" />


<!--  CSS for Demo Purpose, don't include it in your project     -->
<link href="designResources/css/demo.css" rel="stylesheet" />


<!--  Fonts and icons     -->
<link
	href="http://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css"
	rel="stylesheet">
<link href='https://fonts.googleapis.com/css?family=Muli:400,300'
	rel='stylesheet' type='text/css'>
<link href="designResources/css/themify-icons.css" rel="stylesheet">
<script>
	$(document)
			.ready(
					function() {
	
						var bhf_code = "${bhf_code}";
	
						if (bhf_code != "1") {
							deliveryNoti();
						}
	
						$
								.ajax({
									url : "notification",
									type : "GET",
									data : {
										reciever : bhf_code
									},
									dataType : "json",
									success : function(data) {
	
										console.log(data);
	
										var noti = $(".notification");
	
										noti.children().remove();
	
										var length = data.result.length;
	
										noti
												.append($("<div class='notify-arrow notify-arrow-blue'></div>"));
	
										$("#notiCnt").text(data.notiCnt);
	
										var notiCount = data.notiCnt;
										var notiCnt = parseInt($("#notiCnt")
												.text());
	
										if (notiCount > notiCnt) {
											setTimeout(
													function() {
														$(".header")
																.append(
																		$("<div id='notiDiv'></div>"));
														$("#notiDiv").css(
																"position",
																"absolute");
														$("#notiDiv").css(
																"z-index",
																"1000");
														$("#notiDiv").css(
																"text-align",
																"center");
														$("#notiDiv")
																.css(
																		"background-color",
																		"#FAECC5");
														$("#notiDiv").css(
																"width",
																"500px");
														$("#notiDiv")
																.html(
																		"<h3>새로운 알림이 왔습니다.</h3>");
														$("#notiDiv").css(
																"color",
																"black");
														$("#notiDiv").css(
																"right",
																"100px");
														$("#notiDiv").show();
													}, 1000);
	
										}
	
										setTimeout(function() {
	
											$("#notiDiv").hide();
	
										}, 2000);
	
										if (length <= 0) {
											noti.append($("<li></li>").append(
													$("<p></p>").addClass(
															"blue").text(
															"알림이 없습니다.")));
										} else {
											noti.append($("<li></li>").append(
													$("<p></p>").addClass(
															"blue").text(
															"알림이 있습니다.")));
	
											for (var i = 0; i < length; i++) {
	
												noti
														.append($("<li></li>")
																.attr(
																		"data-id",
																		data.result[i].ntcn_code)
																.attr(
																		"bbsctt_code",
																		data.result[i].bbsctt_code)
																.append(
																		$(
																				"<a></a>")
																				.attr(
																						"href",
																						"#")
																				.text(
																						data.result[i].bbsctt_sj)));
												$(
														"li[data-id="
																+ data.result[i].ntcn_code
																+ "] a")
														.append(
																$(
																		"<span></span>")
																		.addClass(
																				"small italic pull-right")
																		.text(
																				data.result[i].dateCha
																						+ " days"));
	
												if (i == 4) {
													break;
												}
	
											}
	
										}
									}
	
								});
	
						$(document)
								.on(
										"click",
										".notification li",
										function() {
	
											var nctn_code = $(this).attr(
													"data-id");
	
											var bbsctt_code = $(this).attr(
													"bbsctt_code");
	
											$
													.ajax({
	
														url : "notiEventDetail",
														type : "GET",
														data : {
															nctn_code : nctn_code,
															bbsctt_code : bbsctt_code,
															reciever : bhf_code
														},
														dataType : "json",
														success : function(data) {
	
															var length = data.result.length;
	
															$("#notiCnt")
																	.text(
																			data.notiCnt);
	
															for (var i = 0; i < length; i++) {
																$(
																		"#detailEvent #bbsctt_sj")
																		.text(
																				data.result[i].bbsctt_sj);
																$(
																		"#detailEvent #event_begin_de")
																		.text(
																				data.result[i].event_begin_de);
																$(
																		"#detailEvent #event_end_de")
																		.text(
																				data.result[i].event_begin_de);
																$(
																		"#detailEvent #bbsctt_cn")
																		.text(
																				data.result[i].bbsctt_cn);
	
															}
	
															$("#detailEvent")
																	.modal();
	
														}
	
													});
	
										});
	
						$(".closeModal").click(function() {
							$(".modal-layout").modal('hide');
						});
	
						if (bhf_code != "1") {
							setInterval(deliveryNoti, 3000);
						}
	
					});
	
	function deliveryNoti() {
	
		var bhf_code = "${bhf_code}"
	
		$.ajax({
			url : "deliveryNoti",
			type : "GET",
			data : {
				bhf_code : bhf_code
			},
			dataType : "json",
			success : function(data) {
				var length = data.delivery.length;
	
				var inbox = $(".inbox");
	
				inbox.children().remove();
	
				var inboxCnt = parseInt($(".inboxCnt").text());
	
				if (length > inboxCnt) {
					setTimeout(function() {
						$(".header").append($("<div id='notiDiv'></div>"));
						$("#notiDiv").css("position", "absolute");
						$("#notiDiv").css("z-index", "1000");
						$("#notiDiv").css("text-align", "center");
						$("#notiDiv").css("background-color", "#FAECC5");
						$("#notiDiv").css("width", "500px");
						$("#notiDiv").html("<h3>새로운 배송 주문이 왔습니다.</h3>");
						$("#notiDiv").css("color", "black");
						$("#notiDiv").css("right", "100px");
						$("#notiDiv").show();
					}, 1000);
	
				}
	
				setTimeout(function() {
	
					$("#notiDiv").hide();
	
				}, 2000);
	
				$(".inboxCnt").text(length);
	
				if (length <= 0) {
					inbox
							.append($("<li></li>").append(
									$("<p></p>").addClass("blue").text(
											"배송 목록이 없습니다.")));
				} else {
					inbox
							.append($("<li></li>").append(
									$("<p></p>").addClass("blue").text(
											"배송 목록이 있습니다.")));
				}
	
				for (var i = 0; i < length; i++) {
	
					inbox
							.append($("<li></li>").attr("data-id",
									data.delivery[i].bill_code).append(
									$("<a></a>").attr("href", "#").text(
											data.delivery[i].user_id
													+ " 배송 주문하였습니다.")));
	
					if (i == 4) {
						break;
					}
	
				}
			
	
			}
		});
	
	}
</script>
</head>
<body>

	<div class="wrapper">
		<div class="sidebar" data-background-color="white"
			data-active-color="danger">

			<!--
		Tip 1: you can change the color of the sidebar's background using: data-background-color="white | black"
		Tip 2: you can change the color of the active button using the data-active-color="primary | info | success | warning | danger"
	-->

			<div class="sidebar-wrapper">
				<div class="logo">
					<a href="mainPage" class="simple-text">Team8 SCTS <br/>
					<span class="simple-text">${ bhf_nm }지점</span>
					</a>
				</div>

			<c:choose>
				<c:when test="${bhf_code == 1}">
					<ul class="sidebar-menu">
						<li><a class="" href="headOfficeMain"> <i
								class="fa fa-home" aria-hidden="true"></i><span>Home
									Main</span></a></li>

						<li><a class="" href="product_List"> <i
								class="fa fa-tags" aria-hidden="true"></i> <span>물품 관리</span>
						</a></li>

						<li><a class="" href="coupon_Management"> <i
								class="icon_piechart"></i> <span>쿠폰 관리</span>
						</a></li>

						<li><a class="" href="event_Management"> <i
								class="fa fa-calendar" aria-hidden="true"></i> <span>이벤트
									관리</span>
						</a></li>
					</ul>
				</c:when>
				
				<c:when test="${bhf_code != 1}">
					<ul class="nav">
						<li class="active"><a class="" href="mainPage">
						<i class="ti-panel"></i><span>Dashboard</span>
						</a></li>
						<li class="sub-menu"><a href="javascript:;" class="">
						<i class="ti-user"></i><span>매장 관리</span> <span
								class="menu-arrow arrow_carrot-right"></span>
						</a>
							<ul class="sub">
								<li><a class="" href="shop_Register">매장 등록</a></li>
								<li><a class="" href="sales_Management">매출 관리</a></li>
								<li><a class="" href="stock_Management">재고 관리</a></li>
							</ul></li>
	
						<li><a class="" href="event_Management"> <i class="ti-view-list-alt"></i> 
							<span>이벤트 관리</span>
						</a></li>
	
						<li><a class="" href="coupon_Management"><i class="ti-text"></i><span>쿠폰 관리</span>
	
						</a></li>
	
						<li><a class="" href="posSystem"><i class="ti-pencil-alt2"></i>
								<span>포스</span>
	
						</a></li>
	
						<li><a class="" href="delivery_Management"><i class="ti-map"></i><span>배송</span>
						</a></li>
	
						<li><a class="" href="help_List"><i class="ti-map"></i><span>문의 사항</span>
						</a></li>
					</ul>
				</c:when>
			</c:choose>
			</div>
		</div>

		<div class="main-panel">
			<nav class="navbar navbar-default">
				<div class="container-fluid">
					<div class="navbar-header">
						<button type="button" class="navbar-toggle">
							<span class="sr-only">Toggle navigation</span> <span
								class="icon-bar bar1"></span> <span class="icon-bar bar2"></span>
							<span class="icon-bar bar3"></span>
						</button>
						<a class="navbar-brand" href="#">Dashboard</a>
					</div>
					<div class="collapse navbar-collapse">
						<ul class="nav navbar-nav navbar-right">
							
							<li class="dropdown"><a href="#" class="dropdown-toggle"
								data-toggle="dropdown"> <i class="ti-bell"></i>
									<p class="notification">5</p>
									<p>Notifications</p> <b class="caret"></b>
							</a>
								<ul class="dropdown-menu">
									<li><a href="#">Notification 1</a></li>
									<li><a href="#">Notification 2</a></li>
									<li><a href="#">Notification 3</a></li>
									<li><a href="#">Notification 4</a></li>
									<li><a href="#">Another notification</a></li>
								</ul></li>

						</ul>
					</div>
				</div>
			</nav>


			<div class="content">
				<div class="container-fluid">
					<section id="main-content" style="overflow-x: hidden;">
						<section class="wrapper">
							<c:if test="${ main_content != null }">
								<jsp:include page="${ main_content }.jsp" />
							</c:if>
						</section>
					</section>
				</div>
			</div>
		</div>
	</div>


</body>

<!--   Core JS Files   -->
<script src="designResources/js/jquery-1.10.2.js" type="text/javascript"></script>
<script src="designResources/js/bootstrap.min.js" type="text/javascript"></script>

<!--  Checkbox, Radio & Switch Plugins -->
<script src="designResources/js/bootstrap-checkbox-radio.js"></script>

<!--  Charts Plugin -->
<script src="designResources/js/chartist.min.js"></script>

<!--  Notifications Plugin    -->
<script src="designResources/js/bootstrap-notify.js"></script>

<!--  Google Maps Plugin    -->
<script type="text/javascript"
	src="https://maps.googleapis.com/maps/api/js"></script>

<!-- Paper Dashboard Core javascript and methods for Demo purpose -->
<script src="designResources/js/paper-dashboard.js"></script>

<!-- Paper Dashboard DEMO methods, don't include it in your project! -->
<script src="designResources/js/demo.js"></script>

<script>
var eventSocket = new SockJS("/scts/event-ws");

eventSocket.onmessage = function(event) {
	var data = event.data;
	data = JSON.parse(data);

	var reciever = "${bhf_code}";

	if (data.eventNotification[0].reciever == reciever) {

		var noti = $(".notification");

		noti.children().remove();

		var length = data.eventNotification.length;

		noti
				.append($("<div class='notify-arrow notify-arrow-blue'></div>"));

		var notiCount = data.notiCnt;
		var notiCnt = parseInt($("#notiCnt").text());

		if (notiCount > notiCnt) {
			setTimeout(function() {
				$(".header").append($("<div id='notiDiv'></div>"));
				$("#notiDiv").css("position", "absolute");
				$("#notiDiv").css("z-index", "1000");
				$("#notiDiv").css("text-align", "center");
				$("#notiDiv").css("background-color", "#FAECC5");
				$("#notiDiv").css("width", "500px");
				$("#notiDiv").html("<h3>새로운 알림이 왔습니다.</h3>");
				$("#notiDiv").css("color", "black");
				$("#notiDiv").css("right", "100px");
				$("#notiDiv").show();
			}, 1000);

		}

		setTimeout(function() {

			$("#notiDiv").hide();

		}, 2000);

		$("#notiCnt").text(data.notiCnt);

		if (length <= 0) {
			noti.append($("<li></li>").append(
					$("<p></p>").addClass("blue").text("알림이 없습니다.")));
		} else {
			noti.append($("<li></li>").append(
					$("<p></p>").addClass("blue").text("알림이 있습니다.")));
		}

		for (var i = 0; i < length; i++) {

			noti.append($("<li></li>").attr("data-id",
					data.eventNotification[i].ntcn_code).attr(
					"bbsctt_code",
					data.eventNotification[i].bbsctt_code).append(
					$("<a></a>").attr("href", "#").text(
							data.eventNotification[i].bbsctt_sj)));
			$(
					"li[data-id=" + data.eventNotification[i].ntcn_code
							+ "] a").append(
					$("<span></span>").addClass(
							"small italic pull-right")
							.text(
									data.eventNotification[i].dateCha
											+ " days"));

			if (i == 4) {
				break;
			}

		}
	}

}

$(document)
		.on(
				"click",
				".inbox li",
				function() {

					var bill_code = $(this).attr("data-id");

					$('.formObj')
							.append(
									"<input type='hidden' name='bill_code' value='"+ bill_code +"'/>");
					$(".formObj").attr("action", "delivery_detail");
					$(".formObj").attr("method", "post");
					$(".formObj").submit();

				});
</script>
</html>
