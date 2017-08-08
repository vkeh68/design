<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style>
	td{
		text-align : center;
	}
</style>
<script>

	var bhf_code = "${bhf_code}";
	
	$(document).ready(function(){
		$.ajax({
			url : "deliveryList",
			type : "GET",
			data : {
				bhf_code : bhf_code
			},
			dataType : "json",
			success : function(data){
				var length = data.delivery.length;
				
				for(var i=0; i < length; i++){
					
					console.log(data.delivery[i]);
					
					$("tbody").append($("<tr></tr>").attr("data-id", data.delivery[i].bill_code));
					$("tr[data-id="+data.delivery[i].bill_code+"]").append($("<td></td>").text(data.delivery[i].bill_code));
					$("tr[data-id="+data.delivery[i].bill_code+"]").append($("<td></td>").text(data.delivery[i].user_id));
					$("tr[data-id="+data.delivery[i].bill_code+"]").append($("<td></td>").text(data.delivery[i].bill_issu_de));
					$("tr[data-id="+data.delivery[i].bill_code+"]").append($("<td></td>").text(data.delivery[i].bill_totamt+"원"));
					
					
					console.log($("tr[data-id="+data.delivery[i].bill_code+"]"));
				}
			}
		});
		
	});

</script>

<div class="row">
	<div class="col-lg-12">
		<h3 class="page-header">
			<i class="icon-envelope-l"></i> 배송관리
		</h3>

		<ol class="breadcrumb">
			
			<li><i class="icon-envelope-l"></i>배송</li>
		</ol>
		<!-- page start -->
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h2>
							<i class="fa fa-map-marker red"></i><strong>배송목록</strong>
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
											계산서 번호</th>
										<th style="text-align: center;"><i class="icon_calendar"></i>
											구매자 이름</th>
										<th style="text-align: center;"><i class="icon_mail_alt"></i>
											주문 날짜</th>
										<th style="text-align: center;"><i class="icon_pin_alt"></i>
											총 가격</th>
									</tr>
								</thead>

								<tbody>
									<%-- <c:forEach items="${ list }" var="selectCoupon">
										<tr>
											<td style="text-align: center;">${selectCoupon.coupon_code}</td>
											<td style="text-align: center;">${selectCoupon.coupon_nm}</td>
											<td style="text-align: center;">${selectCoupon.coupon_cntnts }</td>
											<td style="text-align: center;">${selectCoupon.coupon_dscnt }</td>
											<td style="text-align: center;">${selectCoupon.coupon_begin_de }</td>
											<td style="text-align: center;">${selectCoupon.coupon_end_de }</td>
											<td><button id="modifyBtn"
													class="modifyBtn btn btn-danger">수정</button>
												<button id="delBtn" class="delBtn btn btn-default">삭제</button></td>
										</tr>
									</c:forEach> --%>
								</tbody>
							</table>
						</section>
					</form>
				</div>
			</div>
		</div>
		<div>
		</div>
		
	</div>
</div>

<script>
$(document).on("click", "tr", function(){
	var bill_code = $(this).attr("data-id");
	
	$('.formObj').append("<input type='hidden' name='bill_code' value='"+ bill_code +"'/>");
	$(".formObj").attr("action", "delivery_detail");
	$(".formObj").attr("method", "post");
	$(".formObj").submit();
});
</script>

