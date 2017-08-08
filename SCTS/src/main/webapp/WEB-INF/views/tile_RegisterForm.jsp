<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="row">
	<div class="col-lg-12">
		<h3 class="page-header">
			<i class="fa fa fa-bars"></i>타일 정보 등록
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
		<section class="panel">
			<header class="panel-heading"> 타일 정보 등록 </header>
			<div class="panel-body">
				<div class="form">
					<form class="form-validate form-horizontal" id="feedback_form"
						method="post" action="">

						<div class="form-group ">
							<label for="name" class="control-label col-lg-2">타일 명 <span
								class="required">*</span>
							</label>
							<div class="col-lg-10">
								<input class="form-control" id="tile_nm" name="tile_nm"
									type="text" required />
							</div>
						</div>
<!-- 
						<div class="form-group ">
							<label for="major" class="control-label col-lg-2">비콘 시리얼
								번호 <span class="required">*</span> <br>(Major)
							</label>
							<div class="col-lg-10">
								<input class="form-control" id="major" name="fullname"
									type="text" required />
							</div>
						</div>

						<div class="form-group ">
							<label for="minor" class="control-label col-lg-2">비콘 시리얼
								번호 <span class="required">*</span> <br>(Minor)
							</label>
							<div class="col-lg-10">
								<input class="form-control" id="minor" name="fullname"
									type="text" required />
							</div>
						</div>

						<div class="form-group ">
							<label for="ccomment" class="control-label col-lg-2">비콘
								부가 정보</label>
							<div class="col-lg-10">
								<textarea class="form-control " id="ccomment" name="comment"
									required></textarea>
							</div>
						</div>
-->

						<div class="form-group">
							<div class="col-lg-offset-2 col-lg-10">
								<button class="btn btn-primary" type="submit">Save</button>
								<button class="btn btn-default" type="button">Cancel</button>
							</div>
						</div>
					</form>
				</div>

			</div>
		</section>
	</div>
</div>
