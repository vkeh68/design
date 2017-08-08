<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link href="resources/customcss/couponManagement.css" rel="stylesheet" />
<script>
	$(document)
			.ready(
					function() {

						var p_div = document.getElementById("p_div");
						var c_div = document.getElementById("c_div");

						p_div.style.display = "none";
						c_div.style.display = "none";
						$("#productList").hide();

						var select = $("#selection option:selected").val();
						if (select == "category") {
							p_div.style.display = "none";
							c_div.style.display = "block";

							$("#productList").hide();

						}

						$
								.ajax({
									type : "get",
									url : "selectAllCategory",
									dataType : "json",

									success : function(data) {

										$("#selectionCategory").empty();

										if (data.result.length > 0) {

											var length = data.result.length;
											for (var i = 0; i < length; i++) {

												$("#selectionCategory")
														.append(
																$(
																		"<option></option>")
																		.attr(
																				"value",
																				data.result[i].detailctgry_code)
																		.text(
																				data.result[i].detailctgry_nm));
											}

										}
									}
								});

						$("#selection")
								.on(
										"change",
										function() {
											var selected = $(
													"#selection option:selected")
													.val();
											if (selected == "product") {
												p_div.style.display = "block";
												c_div.style.display = "none";
												$("#productList").show();

											} else if (selected == "category") {
												p_div.style.display = "none";
												c_div.style.display = "block";
												$("#productList").hide();

											}
										});
					});
</script>
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
				<section class="panel" style="width: 60%;">
					<header class="panel-heading"> 쿠폰 수정</header>
					<div class="panel-body">
						<div class="form">
							<div class="form-validate form-horizontal" id="couponForm">

								<div class="form-group">
									<label for="coupon_nm" class="control-label col-lg-2">쿠폰
										이름 <span class="required">*</span>
									</label>
									<div class="col-lg-3">
										<input class="form-control" id="coupon_code"
											name="coupon_code" type="hidden" style="width: 100%;"
											value="${coupon.coupon_code}" required /> <input
											class="form-control" id="coupon_nm" name="coupon_nm"
											type="text" style="width: 100%;" value="${coupon.coupon_nm}"
											required />
									</div>

									<label for="coupon_insert" class="control-label col-lg-2">적용
										분류 <span class="required">*</span>
									</label>
									<div class="col-lg-3">
										<select id="selection" class="btn btn-default dropdown-toggle"
											style="width: 100%;">
											<option value="category">상품카테고리별</option>
											<option value="product">물품별</option>
										</select>
									</div>
								</div>

								<div class="form-group ">
									<label for="coupon_cntnts" class="control-label col-lg-2">쿠폰
										내용 <span class="required">*</span>
									</label>
									<div class="col-lg-3">
										<input class="form-control" id="coupon_cntnts"
											name="coupon_cntnts" type="text" style="width: 100%;"
											value="${coupon.coupon_cntnts}" required />
									</div>


									<div id="c_div">
										<label for="category" class="control-label col-lg-2">카테고리
											<span class="required">*</span>
										</label>
										<div class="col-lg-2">
											<select id="selectionCategory"
												class="btn btn-default dropdown-toggle" style="width: 150%;">

											</select>
										</div>
									</div>

									<div id="p_div">
										<label for="category" class="control-label col-lg-2">적용
											물품 <span class="required">*</span>
										</label>
										<div class="col-lg-3">
											<input type="text" id="selectGoods" class="form-control"
												style="width: 100%;" readonly="readonly"> <input
												type="number" style="width: 100%;" class="form-control"
												id="selectGcode" readonly="readonly">
										</div>
									</div>
								</div>

								<div class="form-group ">
									<label for="coupon_co" class="control-label col-lg-2">쿠폰
										갯수<span class="required">*</span>
									</label> <select id="yPersent" name="yPersent">
										<option value="per">%</option>
										<option value="won">￦</option>
									</select>
									<div class="col-lg-3">
										<input class="form-control" id="coupon_co" name="coupon_co"
											type="number" style="width: 100%;"
											value="${coupon.coupon_co}" />
									</div>

									<label for="coupon_dscnt" class="control-label col-lg-2">쿠폰
										할인율<span class="required">*</span>
									</label>
									<div class="col-lg-3">
										<input class="form-control" id="coupon_dscnt"
											name="coupon_dscnt" type="text" style="width: 100%;"
											value="${coupon.coupon_dscnt}" required />
									</div>
								</div>

								<div class="form-group ">
									<label for="coupon_begin_de" class="control-label col-lg-2">쿠폰
										등록 날짜 <span class="required">*</span>
									</label>
									<div class="col-lg-3">
										<input class="form-control" id="coupon_begin_de"
											name="coupon_begin_de" type="date" style="width: 100%;"
											value="${coupon.coupon_begin_de}" required />
									</div>
								</div>

								<div class="form-group ">
									<label for="coupon_end_de" class="control-label col-lg-2">쿠폰
										종료 날짜 <span class="required">*</span>
									</label>
									<div class="col-lg-3">
										<input class="form-control" id="coupon_end_de"
											name="coupon_end_de" type="date" style="width: 100%;"
											value="${coupon.coupon_end_de}" required />
									</div>
								</div>

								<div class="form-group" style="margin-left: 30%;">
									<div class="col-lg-offset-2 col-lg-10">
										<button class="btn btn-primary" id="couponModify"
											type="submit">Modify</button>
										<button class="cancel btn btn-default" id="couponCancel"
											type="button">Cancel</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</section>

				<section class="panel"
					style="width: 38%; height: 30%; margin-left: 62%; margin-top: -27.7%;">
					<header class="panel-heading"> 물품 리스트</header>
					<div class="panel-body">
						<div class="form">
							<input type="text" id="search" class="form-control">
							<button id="searching" class="btn btn-default">검색</button>
							<div class="form-group" style="overflow: scroll; height: 300px;">
								<table class="table table-striped table-advance table-hover">
									<thead>
										<tr>
											<th style="text-align: center;"></th>
											<th style="text-align: center;"><i class="icon_profile"></i>
												물품번호</th>
											<th style="text-align: center;"><i class="icon_pin_alt"></i>
												물품이름</th>
											<th style="text-align: center;"><i class="icon_pin_alt"></i>
												물품단가</th>
										</tr>
									</thead>

									<tbody id="productList">
										<c:if test="${ GoodsList != null}">
											<c:forEach items="${ GoodsList }" var="vo">
												<tr>
													<td><input type='radio' name='goodsCodeList'
														class='checked'></td>
													<td style="text-align: center;" class="goods_code">${ vo.goods_code }</td>
													<td style="text-align: center;" class="goods_nm">${ vo.goods_nm }</td>
													<td style="text-align: center;" class="goods_pc">${ vo.goods_pc }</td>
												</tr>
											</c:forEach>
										</c:if>

										<c:if test="${ GoodsList == null}">
											<td style="text-align: center;" colspan="4">물품 정보가 없습니다.</td>
										</c:if>
									</tbody>

								</table>
							</div>
						</div>
					</div>
				</section>
			</div>
		</div>
	</div>
</div>

<c:if test="${bhf_code == 1}">
	<div class="row">
		<div class="col-lg-12">

			<section class="panel">
				<header class="panel-heading"> 지점 리스트 </header>
				<div class="panel-body">
					<div class="form">
						<input type="text" id="searchBranch" class="form-control">
						<button type="submit" id="searchingBranchOffice"
							class="btn btn-default">검색</button>
						<div class="form-group" style="overflow: scroll; height: 300px;">
							<table class="table table-striped table-advance table-hover">
								<thead>
									<tr>
										<th style="text-align: center;"><input type='checkbox'
											name='allBranch' id="allBranch"><label
											for="allBranch">&nbsp전체선택</label></th>
										<th style="text-align: center;"><i class="icon_profile"></i>
											지점코드</th>
										<th style="text-align: center;"><i class="icon_pin_alt"></i>
											지점이름</th>
										<th style="text-align: center;"><i class="icon_pin_alt"></i>
											지점주소</th>
									</tr>
								</thead>

								<tbody id="branchList">
									<c:if test="${ branchList != null}">
										<c:forEach items="${ branchList }" var="vo">
											<tr class="branch">
												<td><input type='checkbox' name='branchList' class='branchList'></td>
												<td style="text-align: center;" class="bhf_code">${ vo.bhf_code }</td>
												<td style="text-align: center;" class="bhf_nm">${ vo.bhf_nm }</td>
												<td style="text-align: center;" class="bhf_adres">${ vo.bhf_adres }</td>
											</tr>
										</c:forEach>
									</c:if>
									<c:if test="${ branchList == null}">
										<tr>

											<td style="text-align: center;" colspan="4">지점 정보가 없습니다.</td>
										</tr>
									</c:if>

								</tbody>

							</table>
						</div>
					</div>
				</div>
			</section>
		</div>
	</div>
</c:if>

<script>
	var bhf_code = "${bhf_code}";

	$("#couponModify").click(function() {

		var select = $("#selection option:selected").val();
		var coupon_code = $("#coupon_code").val();
		var goods_code = $("#selectGcode").val();
		var coupon_co = $("#coupon_co").val();
		var coupon_nm = $("#coupon_nm").val();
		var coupon_cntnts = $("#coupon_cntnts").val();
		var coupon_dscnt = $("#coupon_dscnt").val();
		var coupon_begin_de = $("#coupon_begin_de").val();
		var coupon_end_de = $("#coupon_end_de").val();
		var detailctgry_code = $("#selectionCategory option:selected").val();
		var yPersent = $("#yPersent option:selected").val();
		
		
		if (confirm("쿠폰을 수정하시겠습니까?")) {
			if (bhf_code == "1") {
				
				var branch_office = new Array();
				var count = 0;
				branch_office.push(1);
				
				$(".branch").each(function(){
					
					var branch = $(this);
					
					if($(this).find("input[name=branchList]").is(":checked")){
						branch_office.push(parseInt(branch.find("input[name=branchList]:checked").parent().next().text()));
						count++;
					}
				
				});
				
				if(count == $(".branch").length){
					branch_office = [];
					branch_office.push(1);
				}
				
				branch_office = JSON.stringify({
					branch : branch_office
				})
				
				console.log(branch_office);
				
			
				if (select == "product") {

					$.ajax({
						type : "get",
						url : "modifyAdCoupon",
						data : {
							coupon_code : coupon_code,
							goods_code : goods_code,
							coupon_co : coupon_co,
							coupon_nm : coupon_nm,
							coupon_cntnts : coupon_cntnts,
							coupon_dscnt : coupon_dscnt,
							yPersent : yPersent,
							coupon_begin_de : coupon_begin_de,
							coupon_end_de : coupon_end_de,
							select : select,
							branch : branch_office
						},
						success : function(data) {
							if(data == "success"){
								window.location.href = "coupon_Management?status=modify";
							}else{
								console.log("실패");
							}
							
						}
					});

				} else if (select == "category") {

					$.ajax({
						type : "get",
						url : "modifyAdCoupon",
						data : {
							coupon_code : coupon_code,
							detailctgry_code : detailctgry_code,
							coupon_co : coupon_co,
							coupon_nm : coupon_nm,
							coupon_cntnts : coupon_cntnts,
							coupon_dscnt : coupon_dscnt,
							yPersent : yPersent,
							coupon_begin_de : coupon_begin_de,
							coupon_end_de : coupon_end_de,
							select : select,
							branch : branch_office
						},
						success : function(data) {
							if(data == "success"){
								window.location.href = "coupon_Management?status=modify";
							}else{
								console.log("실패");
							}
						}
					});

				}

			// 지점이 1 아닐때
			} else {

				if (select == "product") {

					$.ajax({
						type : "get",
						url : "modifyCoupon",
						data : {
							coupon_code : coupon_code,
							goods_code : goods_code,
							coupon_co : coupon_co,
							coupon_nm : coupon_nm,
							coupon_cntnts : coupon_cntnts,
							coupon_dscnt : coupon_dscnt,
							yPersent : yPersent,
							coupon_begin_de : coupon_begin_de,
							coupon_end_de : coupon_end_de,
							select : select
						},
						success : function(data) {
							if(data == "success"){
								window.location.href = "coupon_Management?status=modify";
							}else{
								console.log("실패");
							}
							
						}
					});

				} else if (select == "category") {

					$.ajax({
						type : "get",
						url : "modifyCoupon",
						data : {
							coupon_code : coupon_code,
							detailctgry_code : detailctgry_code,
							coupon_co : coupon_co,
							coupon_nm : coupon_nm,
							coupon_cntnts : coupon_cntnts,
							coupon_dscnt : coupon_dscnt,
							yPersent : yPersent,
							coupon_begin_de : coupon_begin_de,
							coupon_end_de : coupon_end_de,
							select : select
						},
						success : function(data) {
							if(data == "success"){
								window.location.href = "coupon_Management?status=modify";
							}else{
								console.log("실패");
							}
						}
					});

				}

			}
		}
	});

	$("#searchingBranchOffice")
			.on(
					"click",
					function() {

						var bhf_nm = $("#searchBranch").val();
						$
								.ajax({
									type : "get",
									url : "searchingBranchOffice",
									data : {
										bhf_nm : bhf_nm
									},
									dataType : "json",

									success : function(data) {

										$("#branchList").empty();

										if (data.result.length > 0) {

											var length = data.result.length;
											for (var i = 0; i < length; i++) {

												var branchs = $("<tr class='branch'></tr>");
												$(
														"<td><input type='checkbox' name='branchList'></td>")
														.appendTo(branchs);
												$("<td></td>")
														.addClass("bhf_code")
														.text(
																data.result[i].bhf_code)
														.appendTo(branchs);
												$("<td></td>").addClass(
														"bhf_nm").text(
														data.result[i].bhf_nm)
														.appendTo(branchs);
												$("<td></td>")
														.addClass("bhf_adres")
														.text(
																data.result[i].bhf_adresss)
														.appendTo(branchs);
												$("#branchList")
														.append(branchs);
											}

										} else {
											var branchs = $("<tr class='branch'></tr>");
											$("<td colspan='4'></td>").text(
													"검색된 지점이 없습니다.").appendTo(
													branchs);
											$("#branchList").append(branchs);
										}
									}
								});

					});

	$("#searching")
			.on(
					"click",
					function() {

						var goodsName = $("#search").val();
						$
								.ajax({
									type : "get",
									url : "search",
									data : {
										goodsName : goodsName
									},
									dataType : "json",

									success : function(data) {

										$("#productList").empty();

										if (data.result.length > 0) {

											var length = data.result.length;
											for (var i = 0; i < length; i++) {

												var products = $("<tr class='product'></tr>");
												$(
														"<td><input type='radio' name='goodsCodeList'></td>")
														.appendTo(products);
												$("<td></td>")
														.addClass("goods_code")
														.text(
																data.result[i].goods_code)
														.appendTo(products);
												$("<td></td>")
														.addClass("goods_nm")
														.text(
																data.result[i].goods_nm)
														.appendTo(products);
												$("<td></td>")
														.addClass("goods_pc")
														.text(
																data.result[i].goods_pc)
														.appendTo(products);
												$("#productList").append(
														products);
											}
										} else {
											var products = $("<tr class='product'></tr>");
											$("<td colspan='4'></td>").text(
													"검색된 물품이 없습니다.").appendTo(
													products);
											$("#productList").append(products);
										}
									}
								});

					});

	$(document).on("change", "input[name=goodsCodeList]", function() {

		var goods_code = $(this).parent().next().text();

		var goods_nm = $(this).parent().next().next().text();

		console.log(goods_code + "  " + goods_nm);

		$("#selectGoods").val(goods_nm);
		$("#selectGcode").val(goods_code);

	});

	$("#allBranch").change(function(){
		
		if($("#allBranch").is(":checked") == true){
			
			$(".branch .branchList").prop("checked", true);
			console.log($(".branch .branchList").prop("checked"));
			
		}else{
			
			$(".branch .branchList").prop("checked", false);
			console.log($(".branch .branchList").prop("checked"));
			
		}
		
	});

	$("#couponCancel").click(function() {
		window.location.href = "coupon_Management";
	});
</script>