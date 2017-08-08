<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style>
td {
	text-align: center;
}
</style>

<div class="row">
	<div class="col-lg-12">
		<h3 class="page-header">
			<i class="icon-envelope-l"></i> 배송정보
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
							<i class="fa fa-map-marker red"></i><strong>${delivery[0].user_id}
								배송정보</strong>
						</h2>


					</div>

					<form class="formObj">
					
						<section class="panel col-lg-12"
							style="overflow: scroll; height: 300px;">
							<table class="table table-striped table-advance table-hover">
								<thead>
									<tr>
										<th style="text-align: center;"><i class="icon_profile"></i>
											물품 이름</th>
										<th style="text-align: center;"><i class="icon_profile"></i>
											물품 원가</th>
										<th style="text-align: center;"><i class="icon_calendar"></i>
											물품 수량</th>
									</tr>
								</thead>

								<tbody>
									<c:forEach items="${ delivery }" var="delivery">
										<tr>
											<td style="text-align: center;">${delivery.goods_nm}</td>
											<td style="text-align: center;">${delivery.goods_pc}</td>
											<td style="text-align: center;">${delivery.purchsgoods_qy}</td>
										</tr>
									</c:forEach>
									<tr>
										<td style="text-align: center;" colspan="2">총 가격</td>
										<td style="text-align: center;">${delivery[0].bill_totamt}</td>
									</tr>

								</tbody>
							</table>
						</section>
					</form>
					<div class="panel-actions">

							<strong>받는 분 : ${delivery[0].user_id}</strong> <br>
							<strong>주소 : ${delivery[0].delivery_addr}</strong>

						</div>
				</div>
			</div>
		</div>
		<div>
			<button id="deliveryButton" class="btn"
				style="float: right; background-color: #F15F5F; color: white">배송
				완료</button>
		</div>

	</div>
</div>


<script>
$("#deliveryButton").click(function(){
	var bill_code = "${delivery[0].bill_code}";
	$('.formObj').append("<input type='hidden' name='bill_code' value='"+ bill_code +"'/>");
	$(".formObj").attr("action", "updateDelivery");
	$(".formObj").attr("method", "post");
	$(".formObj").submit();
	
});
</script>

