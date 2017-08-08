<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link href="resources/customcss/product_Register.css" rel="stylesheet" />

<div class="row">
	<div class="col-lg-12">
		<h3 class="page-header">
			<i class="fa fa fa-bars"></i> 물품 목록
		</h3>
		<ol class="breadcrumb">
			<li><i class="fa fa-home"></i><a href="index">Home</a></li>
			<li><i class="fa fa-bars"></i>Management</li>
		</ol>
	</div>
</div>

<!-- page start-->
<form id="pageForm"></form>
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h2>
					<i class="fa fa-map-marker red"></i><strong>ProductList</strong>
				</h2>
				<div class="panel-actions">
					<a href="product_Register" class="btn-setting" id="productBtn"><i
						class="fa fa-plus" aria-hidden="true" id="productBtn"></i></a>
				</div>
			</div>
			<section class="panel col-lg-12"
				style="overflow: scroll; height: 600px;">
				<table class="table table-striped table-advance table-hover">
					<tbody>
						<tr>
							<th style="text-align: center;"><i class="icon_profile"></i>
								상품번호</th>
							<th style="text-align: center;"><i class="icon_calendar"></i>
								카테고리번호</th>
							<!-- <th style="text-align: center;"><i class="icon_mail_alt"></i>
							
					<table class="table table-striped table-advance table-hover" >
						<tbody>
							<tr>
								<th style="text-align: center;"><i class="icon_profile"></i>
									상품번호</th>
								<th style="text-align: center;"><i class="icon_calendar"></i>
									카테고리번호</th>
								<!-- <th style="text-align: center;"><i class="icon_mail_alt"></i>

									Category_name</th> -->
							<th style="text-align: center;"><i class="icon_pin_alt"></i>
								상품이름</th>
							<!-- <th style="text-align: center;"><i class="icon_pin_alt"></i>
									Product_Amount</th> -->

							<th style="text-align: center;"><i class="icon_pin_alt"></i>
								상품가격</th>
						</tr>

						<c:forEach items="${ GoodsList }" var="vo">
							<tr>
								<td style="text-align: center;">${ vo.goods_code }</td>
								<td style="text-align: center;">${ vo.detailctgry_code }</td>
								<%-- <td style="text-align: center;"><a href="${vo.goods_code}"
										class="code">이름출력 아직 안됨</a></td> --%>
								<td style="text-align: center;">${ vo.goods_nm }</td>
								<!-- <td style="text-align: center;">상품수량 아직 안됨</td> -->
								<td style="text-align: center;">${ vo.goods_pc }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</section>
		</div>
	</div>
</div>

<!-- <div id="productModal" class="modal" style="z-index: 3;">
	<div class="modal-row">
		<div class="col-lg-12">
			<section class="panel">
				<header class="panel-heading"> 물품 정보 등록 </header>
				<div class="panel-body">
					<div class="form">
						<form class="form-validate form-horizontal" id="feedback_form" enctype="multipart/form-data"
							method="post" action="product_Register">
							<div class="form-group ">
								<label for="name" class="control-label col-lg-2">상품 명 <span
									class="required">*</span>
								</label>
								<div class="col-lg-10">
									<input class="form-control" id="name" name="goods_nm"
										type="text" required />
								</div>
							</div>

							<div class="form-group ">
							
							<label for="amount" class="control-label col-lg-2">물품 수량
								<span class="required">*</span>
							</label>
							<div class="col-lg-4">
								<input class="form-control" id="amount" name="fullname"
									type="text" required />
							</div>

								<label for="price" class="control-label col-lg-2">판매 가 <span
									class="required">*</span>
								</label>
								<div class="col-lg-10">
									<input class="form-control" id="price" name="goods_pc"
										type="number" required />
								</div>
							</div>

							<div class="form-group ">

								<label for="amount" class="control-label col-lg-2">카테고리
									정보 <span class="required">*</span>
								</label>
								<div class="col-lg-10">
									<select class="form-control m-bot15" name="detailctgry_code">
										<option selected>카테고리 정보를 선택해주세요.</option>
										<option value="1">신선 식품(01)</option>
										<option value="2">차량 용품(02)</option>
										<option value="3">가전 제품(03)</option>
										<option value="4">문구 용품(04)</option>
										<option value="5">의류 제품(05)</option>
									</select>
								</div>

								<label for="price" class="control-label col-lg-2">상품 이미지
									파일<span class="required">*</span>
								</label>
								<div class="col-lg-4">
									<input type="file" name="file">
								</div>
							</div>


							<div class="form-group ">
								<label for="ccomment" class="control-label col-lg-2">상품
									설명</label>
								<div class="col-lg-10">
									<textarea class="form-control " id="ccomment" name="goods_dc"
										required></textarea>
								</div>
							</div>

							<div class="form-group">
								<div class="col-lg-offset-2 col-lg-10">
									<button class="btn btn-primary" type="submit" id="productSave">Save</button>
									<button class="btn btn-default" type="button" id="cancel">Cancel</button>
								</div>
							</div>
						</form>
					</div>

				</div>
			</section>
		</div>
	</div>
</div> -->

<div class="text-center">
	<ul class="pagination">
		<c:if test="${pageMaker.prev }">
			<li><a href="product_List?page=${pageMaker.makeSearch(pageMaker.startPage -1)}">&laquo;</a></li>
		</c:if>

		<c:forEach begin="${pageMaker.startPage}" end="${pageMaker.endPage}"
			var="idx">
			<li <c:out value="${pageMaker.cri.page == idx?'class =active':''}"/>>
				<a href="product_List${pageMaker.makeSearch(idx)}">${idx}</a>
			</li>
		</c:forEach>
		<c:if test="${pageMaker.next && pageMaker.endPage > 0}">
			<li><a href="product_List${pageMaker.makeSearch(pageMaker.endPage +1) }">&raquo;</a></li>
		</c:if>
	</ul>
</div>

<script>
	var pageForm = $("#pageForm");
	
	$(".code").on("click",function(){
		event.preventDefault();
		var goods_code = $(this).attr("href");
		var bhf_code = 1;
		alert(bhf_code);
		pageForm.attr("action", "product_Info");
		pageForm.attr("method", "get");
		$("<input type='hidden' name='product_id' value='"+goods_code+"'>").appendTo(pageForm);
		$("<input type='hidden' name='bhf_code' value='"+bhf_code+"'>").appendTo(pageForm);
		pageForm.submit();
	});

	var registerModal = document.getElementById("productModal");

	$("#productBtn").on("click", function() {
		registerModal.style.display = "block";
	});

	$("#cancel").on("click", function() {
		registerModal.style.display = "none";
	});

	window.onclick = function(event) {
		if (event.target == registerModal) {
			registerModal.style.display = "none";
		}
	}
</script>