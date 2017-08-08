<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<script src="resources/customjs/sockjs.js"></script>


<script src="resources/customjs/posSystem.js"></script>

<style>
.white_content {
	position: fixed;
	top: 0;
	right: 0;
	bottom: 0;
	left: 0;
	background: rgba(0, 0, 0, 0.8);
	opacity: 0;
	-webkit-transition: opacity 400ms ease-in;
	-moz-transition: opacity 400ms ease-in;
	transition: opacity 400ms ease-in;
	pointer-events: none;
}

.white_content:target {
	opacity: 1;
	pointer-events: auto;
}

.white_content>div {
	position: absolute;
	top: 25%;
	left: 25%;
	width: 50%;
	height: 50%;
	padding: 16px;
	border: 16px solid orange;
	background-color: white;
	overflow: auto;
}

td {
	text-align: center;
}
</style>

<%-- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 상품리스트 부분 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ --%>
<div class="row" style="height: 400px;">
	<div class="col-lg-12">
		<section class="panel">
			<header class="panel-heading"> 상품 리스트 </header>
			<table class="table table-hover">
				<thead>
					<tr>
						<th>상품번호</th>
						<th>제조사</th>
						<th>상품명</th>
						<th>단가</th>
						<th>수량</th>
						<th>금액</th>
						<th>할인</th>
					</tr>
				</thead>
				<tbody id="goodsList" style="overflow: scroll;">
				</tbody>
			</table>
		</section>
	</div>
</div>


<%-- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 아래 버튼 부분 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ --%>
<div class="row">
	<div class="col-lg-4">
		<section class="panel">
			<div class="table-responsive">
				<table class="table">
					<tbody>
						<tr>
							<td>바코드</td>
							<td><input type="text" name="goods_code" id="goods_code" /></td>
						</tr>
					</tbody>
				</table>
			</div>
		</section>

		<section class="panel">
			<div class="table-responsive">
				<table class="table">
					<tbody>
						<tr>
							<td>소계</td>
							<td id="totalPrice">0</td>
						</tr>
						<tr>
							<td>할인</td>
							<td id="totalDscnt">0</td>
						</tr>
						<tr>
							<td>합계</td>
							<td id="totalAmount">0</td>
						</tr>
					</tbody>
				</table>
			</div>
		</section>
	</div>

	<div class="col-lg-2">
		<section class="panel">
			<div class="panel-body">
				<button class="btn btn-default numberPad" type="button">1</button>
				<button class="btn btn-default numberPad" type="button">2</button>
				<button class="btn btn-default numberPad" type="button">3</button>
				<br>
				<button class="btn btn-default numberPad" type="button">4</button>
				<button class="btn btn-default numberPad" type="button">5</button>
				<button class="btn btn-default numberPad" type="button">6</button>
				<br>
				<button class="btn btn-default numberPad" type="button">7</button>
				<button class="btn btn-default numberPad" type="button">8</button>
				<button class="btn btn-default numberPad" type="button">9</button>
				<br>
				<button class="btn btn-default numberPad" type="button">D</button>
				<button class="btn btn-default numberPad" type="button">0</button>
				<button class="btn btn-default numberPad" type="button">C</button>
				<br>

			</div>
		</section>

	</div>

	<div class="col-lg-2">
		<section class="panel">
			<div class="panel-body">
				<button class="btn btn-default" type="button" id="getGoods">등록</button>
				<br>
				<button class="btn btn-default" type="button" id="cancleGoods">취소</button>
				<br>
				<button class="btn btn-default" type="button" id="additionGoods">수량+</button>
				<br>
				<button class="btn btn-default" type="button" id="subtractGoods">수량-</button>
				<br>
			</div>
		</section>
	</div>

	<div class="col-lg-2">
		<section class="panel">
			<div class="panel-body">
				<a href="#searchGoodsOpen"><button class="btn btn-default"
						type="button" id="searchGoods">상품검색</button></a> <br> <a
					href="#couponPointOpen"><button class="btn btn-default"
						type="button" id="couponPoint">쿠폰 포인트</button></a> <br> <a
					href="#cardOpen"><button class="btn btn-default" type="button" id="settleBtn">결제</button></a>
				<!-- <button class="btn btn-default" type="button" id="card">신용카드 결제</button> -->
				<!-- <br>
				<a href="#moneyOpen"><button class="btn btn-default" type="button" id="money">현금 결제</button></a>
				<br>
				<a href="#mixOpen"><button class="btn btn-default" type="button" id="mix">복합 결제</button></a>
				<br> -->
			</div>
		</section>
	</div>
</div>

<div class="white_content modalPanel" id="searchGoodsOpen">
	<div>
		<p>상품검색</p>
		<input type="text" name="goods_nm" id="goods_nm" />
		<button class="btn btn-default" type="button" id="modalSearchGoods">검색</button>
		<h3>물품 정보</h3>
		<section class="panel">

			<table class="table table-hover">
				<thead>
					<tr>
						<th>물품 번호</th>
						<th>물품 이름</th>
						<th>물품 가격</th>
					</tr>

				</thead>
				<tbody id="searchGoodsList">
				</tbody>
			</table>
		</section>

		<br> <a href="#">닫기</a>
	</div>
</div>

<div class="white_content modalPanel" id="couponPointOpen">
	<div>
		<div id="inputMode">
			<p>쿠폰 포인트</p>
			고객아이디 <input type="text" name="user_id" id="user_id" />
			<button class="btn btn-default" type="button" id="getUserCoupon">검색</button>
			<br>
		</div>

		<div id="couponMode">
			<section class="panel">
				<table class="table table-hover" style="overflow: scroll;">
					<thead>
						<tr>
							<th>적용가능상품</th>
							<th>쿠폰이름</th>
							<th>할인율</th>
							<th>사용가능기간</th>
						</tr>
					</thead>
					<tbody id="couponList">
					</tbody>
				</table>
				<div>
					<button class="btn btn-default" type="button" id="useCoupon">등록</button>
					<button class="btn btn-default" type="button" id="exitCouponList">
						<a href="#">취소</a>
					</button>
				</div>
			</section>
		</div>
	</div>
</div>

<div class="white_content modalPanel" id="cardOpen">
	<div class="form-group">
		<h2>결제방법 선택</h2>
		<div class="form-group">
			<select id="settlement" class="form-control">
				<c:forEach items="${list}" var="list">
					<option value="${list.setle_mth_code}">${list.setle_mth_nm}</option>
				</c:forEach>
				<option value="${fn:length(list)+1}">복합결제</option>
			</select>
		</div>

		<div class="form-group">
			<h4>고객아이디</h4>
			<input type="text" name="user_id_payment" class="form-control"
				id="user_id_payment" value="user15" />
		</div>
		

		<div class="form-group" id="doubleSettle" style="display:none;">
			<h2>복합결제</h2>
			<div class="form-group">
				<h4>결제1</h4>
				<select class="settlement" class="form-control">
					<c:forEach items="${list}" var="list">
						<option value="${list.setle_mth_code}">${list.setle_mth_nm}</option>
					</c:forEach>
				</select>
				<input type="number" class="form-control tprice" />원
			</div>
			<div class="form-group">
				<h4>결제2</h4>
				<select class="settlement" class="form-control">
					<c:forEach items="${list}" var="list">
						<option value="${list.setle_mth_code}">${list.setle_mth_nm}</option>
					</c:forEach>
				</select>
				<input type="number" class="form-control tprice" />원
			</div>
			<div class="form-group">
				<h4>결제3</h4>
				<select class="settlement" class="form-control ">
					<c:forEach items="${list}" var="list">
						<option value="${list.setle_mth_code}">${list.setle_mth_nm}</option>
					</c:forEach>
				</select>
				<input type="number" class="form-control tprice" />원
			</div>
		</div>
		<div id="total" style="float:right;">
			
		</div>
		<div>
			<button class="btn btn-default" type="button" id="card">결제</button>
		</div>
		<a href="#">닫기</a>
	</div>
</div>


<script>
	$("#modalSearchGoods").click(
			function() {

				var goods_nm = $("#goods_nm").val();

				$.ajax({
					type : "GET",
					url : "searchGoodsList",
					data : {
						goods_nm : goods_nm
					},
					dataType : "json",
					success : function(data) {
						$("#searchGoodsList").empty();

						if (data.length > 0) {

							var length = data.length;

							console.log(data.length);

							for (var i = 0; i < length; i++) {
								console.log(data[i]);
								$("#searchGoodsList").append(
										$("<tr></tr>").addClass("goods").attr(
												"data-id", i));
								$(".goods[data-id=" + i + "]")
										.append(
												$("<td></td>").text(
														data[i].goods_code));
								$(".goods[data-id=" + i + "]").append(
										$("<td></td>").text(data[i].goods_nm));
								$(".goods[data-id=" + i + "]").append(
										$("<td></td>").text(data[i].goods_pc));

								console.log($(".goods[data-id=" + i + "] td"));
							}

						} else {
							$("#searchGoodsList").append(
									$("<tr></tr>").addClass("goods"));
							$(".goods").append(
									$("<td colspan='3'></td>").text(
											"물품 정보가 없습니다"));
						}
					}
				});
			});
	
	$("#settlement").on("change", function(){
		var select = $("#settlement option:selected").val();
		
		if(select == $("#settlement option").length){
			$("#doubleSettle").show();
		}else{
			$("#doubleSettle").hide();
		}
		
	});
	
	$("#settleBtn").click(function(){
		var total = $("#totalAmount").text();
		
		$("#total").text("합계 : " + total + "원");
	});
	
	
	
</script>
