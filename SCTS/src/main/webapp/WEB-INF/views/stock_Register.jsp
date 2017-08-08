<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style>
/* number에 화살표 지우기 */
input[type=number]::-webkit-outer-spin-button{-webkit-appearance: none;margin: 0;}
input[type=number]::-webkit-inner-spin-button{-webkit-appearance: none;margin: 0;}
</style>

<div class="row">
	<div class="col-lg-12">
		<h3 class="page-header">
			<i class="icon-envelope-l"></i> 재고 관리
		</h3>
		<ol class="breadcrumb">
			<li><i class="fa fa-home"></i><a href="index">Home</a></li>
			<li><i class="icon-envelope-l"></i>Stock</li>
		</ol>
		<!-- page start -->
		<div class="row">
			<div class="col-lg-12">
				<section class="panel" style="width:60%;">
					<header class="panel-heading"> 재고 등록</header>
					<div class="panel-body">
						<div class="form">
							<form class="form-validate form-horizontal" id="stockForm"
								action="insertStock" method="post">
								<input type="hidden" id="goods_code" name="goods_code" value="0">
								<div class="form-group">
									<label for="user_id" class="control-label col-lg-2">
									업체명 <span class="required">*</span>
									</label>
									<div class="col-lg-3">
										<select name="user_id" class="btn btn-default dropdown-toggle"
											style="width: 100%;">
											<c:forEach items="${enterList}" var="enter">
												<option value="${enter.user_id }">${enter.suply_entrps_nm}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label for="wrhousng_qy" class="control-label col-lg-2">
									입고량 <span class="required">*</span>
									</label>
									<div class="col-lg-3">
										<input class="form-control" id="wrhousng_qy" name="wrhousng_qy"
											type="number" style="width: 100%;" required />
									</div>
									
									<label for="wrhousng_de" class="control-label col-lg-2">
									입고날짜 <span class="required">*</span>
									</label>
									<div class="col-lg-3">
										<input class="form-control" id="wrhousng_de" name="wrhousng_de"
											type="date" style="width: 100%;" required />
									</div>					
	
								</div>

								<div class="form-group">
									<label for="puchas_pc" class="control-label col-lg-2">매입가
										<span class="required">*</span>
									</label>
									<div class="col-lg-3">
										<input class="form-control" id="puchas_pc"
											name="puchas_pc" type="number" style="width: 100%;"
											required />
									</div>

									<label for="puchas_de" class="control-label col-lg-2">
									매입날짜 <span class="required">*</span>
									</label>
									<div class="col-lg-3">
										<input class="form-control" id="puchas_de" name="puchas_de"
											type="date" style="width: 100%;" required />
									</div>
								</div>					

						<!-- 		<div class="form-group ">
									<label for="coupon_sales" class="control-label col-lg-2">쿠폰
										갯수<span class="required">*</span>
									</label>
									<select name="yPersent">
											<option value="per">%</option>
											<option value="won">￦</option>
									</select>
									<div class="col-lg-3">
										<input class="form-control" id="coupon_co"
											name="coupon_co" type="number" style="width: 100%;" required />
									</div>
								</div> -->

								<div class="form-group ">
									
									<label for="invntry_qy" class="control-label col-lg-2">
									재고량 <span class="required">*</span>
									</label>
									<div class="col-lg-3">
										<input class="form-control" id="invntry_qy" name="invntry_qy"
											type="number" style="width: 100%;" required />
									</div>
									
									<label for="distb_de" class="control-label col-lg-2">유통
									기한 <span class="required">*</span>
									</label>
									<div class="col-lg-3">
										<input class="form-control" id="distb_de"
											name="distb_de" type="date" style="width: 100%;"
											required />
									</div>
								</div>

								<div class="form-group" style="margin-left: 30%;">
									<div class="col-lg-offset-2 col-lg-10">
										<button class="btn btn-primary" id="stockSave" type="submit">Save</button>
										<button class="cancel btn btn-default" id="stockCancel"
											type="button">Cancel</button>
									</div>
								</div>
							</form>
						</div>
					</div>
				</section>

				<section class="panel"
					style="width: 38%; height: 30%; margin-left: 62%; margin-top: -24.7%;">
					<header class="panel-heading"> 상품 리스트</header>
					<div class="panel-body">
						<div class="form">
							<input type="text" id="search" class="form-control">
							<button type="submit" id="searching" class="btn btn-default">검색</button>
							<div class="form-group" style="overflow: scroll; height: 300px;">
								<table class="table table-striped table-advance table-hover">
									<thead>
										<tr>
											<th style="text-align: center;"></th>
											<th style="text-align: center;"><i class="icon_profile"></i>
												Product_no</th>
											<th style="text-align: center;"><i class="icon_pin_alt"></i>
												Product_name</th>
											<th style="text-align: center;"><i class="icon_pin_alt"></i>
												Price(won)</th>
										</tr>
									</thead>

									<tbody id="productList">
										<c:if test="${ goodsList != null}">
											<c:forEach items="${ goodsList }" var="list">
												<tr>
													<td><input type='radio' name='goodsCodeList'
														class='checked'></td>
													<td style="text-align: center;">${ list.goods_code }</td>
													<td style="text-align: center;">${ list.goods_nm }</td>
													<td style="text-align: center;">${ list.goods_pc }</td>
												</tr>
											</c:forEach>
										</c:if>

										<c:if test="${ goodsList == null}">
											<td style="text-align: center;" colspan="4">물품 정보가 없습니다.</td>
										</c:if>
									</tbody>

									<%-- <tbody id="categoryList">
										<c:forEach items="${}" var="category">
										
										</c:forEach>												
									</tbody> --%>
								</table>
							</div>
						</div>
					</div>
				</section>
			</div>
		</div>
	</div>
</div>

<script src="resources/customjs/stockRegister.js"></script>