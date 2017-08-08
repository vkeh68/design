<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<form role="form" method="post">
	<input type="hidden" name="product_id" value="${goods_code}">
</form>

<div class="row">
	<div class="col-lg-12">
		<h3 class="page-header">
			<i class="fa fa fa-bars"></i>물품 상세 정보
		</h3>
		<ol class="breadcrumb">
			<li><i class="fa fa-home"></i><a href="index">Home</a></li>
			<li><i class="fa fa-bars"></i>Management</li>
			<li><i class="fa fa-bars"></i>Edit</li>
		</ol>
	</div>
</div>

<!-- Form validations -->
<div class="row">
	<div class="col-lg-12">
		<section class="panel" >
			<header class="panel-heading"> 물품 상세 정보 </header>
			<div class="panel-body" style="height:500px;" >
				<div class="form">
					<form class="form-validate form-horizontal" id="feedback_form"
						method="get">
						<div class="form-group ">
							<label for="name" class="control-label col-lg-2">물품 사진 <span
								class="required">*</span>
							</label>
							<div class="col-lg-2" id="product_img">
								
							</div>

							<div class="form-group ">
								<label for="name" class="control-label col-lg-2">물품 명 <span
									class="required">*</span>
								</label>
								<div class="col-lg-5">
									<input class="form-control" id="goods_nm" name="goods_nm"
										type="text" value="${goods.goods_nm}" required />
									<input class="form-control" id="goods_code" name="product_id"
										type="hidden" value="${goods.goods_code}" required />
								</div>
								<br> <br> <br> <label for="amount"
									class="control-label col-lg-2">카테고리 정보 <span
									class="required">*</span>
								</label>
								<div class="col-lg-5">
									<select class="form-control m-bot15">
										<option>카테고리 정보를 선택해주세요.</option>
										<option>신선 식품(01)</option>
										<option>차량 용품(02)</option>
										<option>가전 제품(03)</option>
										<option>문구 용품(04)</option>
										<option>의류 제품(05)</option>
									</select>
								</div>
								<br> <br> <br> <label for="price"
									class="control-label col-lg-2">판매 가 <span
									class="required">*</span>
								</label>
								<div class="col-lg-5">
									<input class="form-control" id="goods_pc" name="goods_pc"
										type="text" value="${goods.goods_pc}" required />
								</div>
								<br> <br> <br> <label for="price"
									class="control-label col-lg-2">판매 수량 <span
									class="required">*</span>
								</label>
								<div class="col-lg-5">
									<input class="form-control" id="price" name="fullname"
										type="text" required />
								</div>
							</div>
						</div>

						<div class="form-group ">
							<label for="ccomment" class="control-label col-lg-2">물품
								부가 정보</label>
							<div class="col-lg-20">
								<textarea class="form-control " id="goods_dc" name="goods_dc"
									required>${goods.goods_dc}</textarea>
							</div>
						</div>

						<div class="form-group">
							<div class="col-lg-offset-2 col-lg-10">
								<button class="btn btn-primary" type="submit">Modify</button>
								<button class="btn btn-default" type="button">Cancel</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</section>
	</div>
</div>
