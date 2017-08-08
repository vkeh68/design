<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link href="resources/customcss/couponManagement.css" rel="stylesheet" />

<style>
td {
	text-align: center;
}
</style>


<div class="row">
	<div class="col-lg-12">
		<h3 class="page-header">
			<i class="icon-envelope-l"></i> 쿠폰 관리
		</h3>

		<ol class="breadcrumb">
			<li><i class="fa fa-home"></i><a href="index">Home</a></li>
			<li><i class="icon-envelope-l"></i>Coupon</li>
		</ol>
		<!-- page start -->
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h2>
							<i class="fa fa-map-marker red"></i><strong>쿠폰목록</strong>
						</h2>

						<div class="panel-actions">
							<a href="reg_coupon" class="btn-setting" id="couponBtn"><i
								id="couponBtn" class="fa fa-plus" aria-hidden="true"></i></a>
						</div>
					</div>

					<form class="formObj">
						<section class="panel col-lg-12"
							style="overflow: scroll; height: 600px;">
							<table class="table table-striped table-advance table-hover">
								<thead>
									<tr>
										<th style="text-align: center;"><i class="icon_profile"></i>
											쿠폰번호</th>
										<th style="text-align: center;"><i class="icon_calendar"></i>
											쿠폰이름</th>
										<th style="text-align: center;"><i class="icon_mail_alt"></i>
											쿠폰정보</th>
										<th style="text-align: center;"><i class="icon_pin_alt"></i>
											할인정보</th>
										<th style="text-align: center;"><i class="icon_pin_alt"></i>
											쿠폰시작날짜</th>
										<th style="text-align: center;"><i class="icon_pin_alt"></i>
											쿠폰종료날짜</th>
									</tr>
								</thead>

								<tbody id="couponList">
									<c:if test="${fn:length(list) != 0}">
										<c:forEach items="${ list }" var="selectCoupon">
											<tr>
												<td>${selectCoupon.coupon_code}</td>
												<td>${selectCoupon.coupon_nm}</td>
												<td>${selectCoupon.coupon_cntnts }</td>
												<td>${selectCoupon.coupon_dscnt }</td>
												<td>${selectCoupon.coupon_begin_de }</td>
												<td>${selectCoupon.coupon_end_de }</td>

												<c:if test="${bhf_code != 1}">
													<c:if test="${selectCoupon.bhf_code == 1}">
														<td><button id="modifyBtn"
																class="modifyBtn btn btn-danger" disabled>수정</button>
															<button id="delBtn" class="delBtn btn btn-default"
																disabled>삭제</button></td>
													</c:if>

													<c:if test="${selectCoupon.bhf_code != 1}">
														<td><button id="modifyBtn"
																class="modifyBtn btn btn-danger">수정</button>
															<button id="delBtn" class="delBtn btn btn-default">삭제</button></td>
													</c:if>

												</c:if>
												<c:if test="${bhf_code == 1}">

													<td><button id="modifyBtn"
															class="modifyBtn btn btn-danger">수정</button>
														<button id="delBtn" class="delBtn btn btn-default">삭제</button></td>

												</c:if>



											</tr>
										</c:forEach>
									</c:if>
								</tbody>
							</table>
							<c:if test="${fn:length(list) == 0}">
								<div class="text-center"
									style="font-size: 18px; font-weight: 600; margin: 25px 0px 500px 0px;">쿠폰이
									존재하지 않습니다.</div>
							</c:if>
						</section>
					</form>
				</div>
			</div>
		</div>
		<c:if test="${bhf_code != 1}">
			<div>
				<button id="periodicButton" class="btn"
					style="float: right; background-color: #F15F5F; color: white">쿠폰
					정기적 발송</button>
			</div>
		</c:if>

	</div>
</div>

<script src="resources/customjs/couponManagement.js"></script>

<script>
	$("#periodicButton").click(function() {
		$.ajax({
			url : "android/fcmCoupon",
			type : "GET",
			dataType : "text",
			success : function(data) {
				alert("쿠폰을 발송하였습니다.");
			}
		});
	});

	var bhf_code = "${bhf_code}";
	var status = "${status}";
	console.log(status);
	
	
	if(status != "modify"){
		if (bhf_code != 1) {
			var couponSocket = new SockJS("/scts/coupon-ws");

			couponSocket.onmessage = function(event) {

				var data = JSON.parse(event.data);
				var coupon = new Array();
				var length = data.coupon.length;
				var count = 0;
				for (var i = 0; i < length; i++) {
					if (data.coupon[i].bhf_code == 1
							|| data.coupon[i].bhf_code == bhf_code) {
						
						count++;
						
						if(count == 1){
							
							var list = '<tr>'
								+'<td style="background-color: #F15F5F; color:white;">'+ data.coupon[i].coupon_code +'</td>'
								+'<td style="background-color: #F15F5F; color:white;">'+ data.coupon[i].coupon_nm +'</td>'
								+'<td style="background-color: #F15F5F; color:white;">'+ data.coupon[i].coupon_cntnts +'</td>'
								+'<td style="background-color: #F15F5F; color:white;">'+ data.coupon[i].coupon_dscnt + '</td>'
								+'<td style="background-color: #F15F5F; color:white;">'+ data.coupon[i].coupon_begin_de + '</td>'
								+'<td style="background-color: #F15F5F; color:white;">'+ data.coupon[i].coupon_end_de + '</td>'
								+'<td style="background-color: #F15F5F; color:white;"><button id="modifyBtn" class="modifyBtn btn btn-danger">수정</button>'
								+'<button id="delBtn" class="delBtn btn btn-default">삭제</button></td>'
							    +'</tr>';
							$("#couponList").prepend(list);
							
						}
						
					}
				}

			}

		}
		
	}
	
</script>