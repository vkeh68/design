<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- 
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<script src="http://code.jquery.com/jquery-latest.min.js"></script> -->


<script src="resources/customjs/shop_Register.js"></script>
<script src="resources/customjs/drawingTile.js"></script>
<link href="resources/customcss/tileMapClick.css" rel="stylesheet" />

<style>
/* number에 화살표 지우기 */
input[type=number]::-webkit-outer-spin-button{-webkit-appearance: none;margin: 0;}
input[type=number]::-webkit-inner-spin-button{-webkit-appearance: none;margin: 0;}

/* The Modal (background) */
.modal, .tileModal {
	display: none; /* Hidden by default */
	position: fixed; /* Stay in place */
	z-index: 1; /* Sit on top */
	padding-top: 220px; /* Location of the box */
	left: 0;
	top: 0;
	width: 100%; /* Full width */
	height: 100%; /* Full height */
	overflow: auto; /* Enable scroll if needed */
	background-color: rgb(0, 0, 0); /* Fallback color */
	background-color: rgba(0, 0, 0, 0.4); /* Black w/ opacity */
}

/* Modal Content */
.modal-row {
	position: relative;
	background-color: #fefefe;
	margin: auto;
	padding-top: 16px;
	width: 53%;
	height: 66%;
	box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0
		rgba(0, 0, 0, 0.19);
	-webkit-animation-name: animatetop;
	-webkit-animation-duration: 0.4s;
	animation-name: animatetop;
	animation-duration: 0.4s
}

.tileModal-row {
	position: relative;
	background-color: #fefefe;
	margin: auto;
	padding-top: 16px;
	width: 43%;
	height: 40%;
	box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0
		rgba(0, 0, 0, 0.19);
	-webkit-animation-name: animatetop;
	-webkit-animation-duration: 0.4s;
	animation-name: animatetop;
	animation-duration: 0.4s
}

/* Add Animation */
@
-webkit-keyframes animatetop {
	from {top: -300px;
	opacity: 0
}

to {
	top: 0;
	opacity: 1
}

}
@
keyframes animatetop {
	from {top: -300px;
	opacity: 0
}

to {
	top: 0;
	opacity: 1
}

}
.panel-heading {
	border: 1px solid #D5D5D5;
}
</style>

<div class="row">
	<div class="col-lg-12">
		<h3 class="page-header">
			<i class="fa fa fa-bars"></i> 매장 등록
		</h3>
		<ol class="breadcrumb">
			<li><i class="fa fa-home"></i><a href="index">Home</a></li>
			<li><i class="fa fa-bars"></i>Management</li>
		</ol>
	</div>
</div>

<!-- page start-->
<div class="row">
	<div class="col-lg-9 col-md-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h2>
					<i class="fa fa-map-marker red"></i><strong>설계도면</strong>
				</h2>
				<div class="panel-actions">
					<button class="btn btn-default" id="beaconSetting">비콘설정모드</button>
					<button class="btn btn-default" id="categorySetting">카테고리설정모드</button>
					<a href="#" class="btn-setting" id="leftDrawingBtns"><i
						id="leftBtns" class="fa fa-chevron-left" aria-hidden="true"></i></a> <a
						href="#" class="btn-setting" id="rightDrawingBtns"><i
						id="rightBtns" class="fa fa-chevron-right" aria-hidden="true"></i></a>
					<a href="#" class="btn-setting" id="MyBtn"><i id="MyBtn"
						class="fa fa-plus" aria-hidden="true"></i></a>
				</div>
			</div>
			<div class="panel-body-map">
				<input type="hidden" id="settingMode" value="0"> 
				<input type="hidden" id="countStory" value="${ countStory }">
				<input type="hidden" id="floor" value="0"> 
				<input type="hidden" id="drw_code" value="0">
				<div id="blueprint"
					style="height: 380px; text-align: center; position: absolute; z-index: 1;">
					<!-- 
					<br> <br> <br> <br> <br> <br> <br>
					<br>
					<p>설계도면 파일을 등록해주세요.</p>
				 -->
					<%-- 
					<img
					
						src="displayDrawing?fileName=/${ drawingList.get(0).drw_flpth }"
						style="width: 800px; height: 380px;"> --%>

				</div>


				<!-- 전체 타일 영역 잡을 것. 위에 이미지 태그와 겹칠수 있도록 정의해야함 -->
				<div class="tileMap"
					style="position: absolute; width: 800px; height: 380px; z-index: 2;">
					<!-- 타일영역 전체 감싸는 div -->
				</div>
				<!-- 타일영역 전체 감싸는 div -->

			</div>

		</div>
	</div>

	<div class="col-md-3"
		style="background-color: white; width: 340px; height: 417px; position: absolute; top: 230px; left: 78%; border: 1px solid #D5D5D5; text-align: center;">
		
		<div id="tile_info"></div>
		
	</div>
</div>

<div class="row">
	<div class="col-lg-6">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h2>
					<i class="fa fa-map-marker red"></i><strong>존 리스트</strong>
				</h2>

				<div class="panel-actions">
					<a href="#" class="btn-setting" id="tileBtn"><i id="tileBtn"
						class="fa fa-plus" aria-hidden="true"></i></a>
				</div>
			</div>
			<section class="panel col-lg-12"
				style="overflow: scroll; height: 430px;">

				<table class="table table-striped table-advance table-hover">
					<thead>
						<tr>
							<th style="text-align: center;"><i class="fa fa-square"></i>
								타일명</th>
							<th style="text-align: center;"><i class="fa fa-clock-o"></i>
								X축</th>
							<th style="text-align: center;"><i class="fa fa-users"></i>
								Y축</th>
							<th style="text-align: center;"><i class="fa fa-users"></i>
								비콘설정</th>
						</tr>
					</thead>
					
					<tbody class="tileList">
						<c:forEach items="${ tileList }" var="vo">
							<tr>
								<td style="text-align: center;">${ vo.get("TILE_NM") }</td>
								<td style="text-align: center;">${ vo.get("TILE_CRDNT_X") }</td>
								<td style="text-align: center;">${ vo.get("TILE_CRDNT_Y") }</td>
								<td style="text-align: center;">${ vo.get("BEACON_CODE") }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</section>
		</div>
	</div>

	<div class="col-lg-6">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h2>
					<i class="fa fa-map-marker red"></i><strong>비콘 리스트</strong>
				</h2>

				<div class="panel-actions">
					<a href="#" class="btn-setting" id="beaconBtn"><i id="beaconBtn"
						class="fa fa-plus" aria-hidden="true"></i></a>
				</div>
			</div>
			<section class="panel col-lg-12"
				style="overflow: scroll; height: 430px;">

				<table class="table table-striped table-advance table-hover">
					<tbody>
						<tr>
							<th style="text-align: center;"><i
								class="fa fa-check-square-o"></i> 메이저</th>
							<th style="text-align: center;"><i
								class="fa fa-check-square-o"></i> 마이너</th>
							<th style="text-align: center;"><i class="fa fa-flag"></i>
								상태</th>
						</tr>

						<c:forEach items="${beaconList}" var="beacon">
							<tr>
								<td style="text-align: center;">${ beacon.beacon_mjr }</td>
								<td style="text-align: center;">${ beacon.beacon_mnr }</td>
								<td style="text-align: center;">${ beacon.beacon_sttus }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</section>
		</div>
	</div>
</div>

<div id="Mymodal" class="modal" style="z-index: 3;">
	<div class="modal-row">
		<div class="col-lg-12">
			<section class="panel">
				<header class="panel-heading"> 설계도면 등록 </header>
				<div class="panel-body">
					<div class="form">
						<form class="form-validate form-horizontal" id="feedback_form"
							method="post" action="shop_RegisterForm"
							enctype="multipart/form-data">

							<div class="form-group ">
								<label for="cname" class="control-label col-lg-2">지점코드 <span
									class="required">*</span>
								</label>
								<div class="col-lg-10">
									<!-- <input class="form-control" id="name" name="bhf_code"
										type="text" required /> -->
									<select name="bhf_code" id="bhf_code">
										<option value="">지점선택</option>
									</select>
								</div>

							</div>

							<div class="form-group ">
								<label for="cname" class="control-label col-lg-2">층정보 <span
									class="required">*</span>
								</label>
								<div class="col-lg-10">
									<!-- <input class="form-control" id="level_info"
										name="floorinfo_floor" type="text" required /> -->
									<select name="floorinfo_floor" id="floorinfo_floor">
										<option value="">층선택</option>
									</select>
								</div>
							</div>

							<div class="form-group ">
								<label for="cname" class="control-label col-lg-2">타일 XY<span
									class="required">*</span>
								</label>
								<div class="col-lg-10">
									<input type="number" name="size_x" required value="3" min="2"
										max="10"> X <input type="number" name="size_y"
										required value="3" min="2" max="10">
								</div>
							</div>

							<div class="form-group ">
								<label for="cname" class="control-label col-lg-2">첨부파일 <span
									class="required">*</span>
								</label>
								<div class="col-lg-10">
									<input type="file" name="file" required>
								</div>
							</div>

							<div class="form-group">
								<div class="col-lg-offset-2 col-lg-10">
									<button class="btn btn-primary" id="drawingSave" type="submit">Save</button>
									<button class="btn btn-default cancleBtn" id="shopCancel" type="button">Cancel</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</section>
		</div>
	</div>
</div>

<div id="tileModal" class="modal" style="z-index: 3;">
	<div class="tileModal-row">
		<div class="col-lg-12">
			<section class="panel">
				<header class="panel-heading"> 존 정보 등록 </header>
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
						
							<div class="form-group">
								<div class="col-lg-offset-2 col-lg-10">
									<button class="btn btn-primary" id="tileSave" type="submit">Save</button>
									<button class="btn btn-default cancleBtn" id="tileCancel" type="button">Cancel</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</section>
		</div>
	</div>
</div>

<div id="beaconModal" class="modal" style="z-index: 3;">
	<div class="modal-row">
		<div class="col-lg-12">
			<section class="panel">
				<header class="panel-heading">비콘 등록 </header>
				<div class="panel-body">
					<div class="form">
						<form class="form-validate form-horizontal" id="beacon_form">
							<div class="form-group">
								<label for="name" class="control-label col-lg-2">비콘 메이저
									<span class="required">*</span>
								</label>
								<div class="col-lg-10">
									<input class="form-control" id="beacon_mjr" name="beacon_mjr"
										type="number" required />
								</div>
							</div>

							<div class="form-group">
								<label for="name" class="control-label col-lg-2">비콘 마이너
									<span class="required">*</span>
								</label>
								<div class="col-lg-10">
									<input class="form-control" id="beacon_mnr" name="beacon_mnr"
										type="number" required />
								</div>
							</div>

							<div class="form-group">
								<label for="coupon_insert" class="control-label col-lg-2">비콘
									상태 <span class="required">*</span>
								</label> <select id="selection" name="beacon_sttus" class="btn btn-default dropdown-toggle"
									style="width: 25.7%;">
									<option value="ON">ON</option>
									<option value="USE">USE</option>
								</select>
							</div>
						
							<div class="form-group">
								<div class="col-lg-offset-2 col-lg-10">
									<button class="btn btn-primary" id="beaconSave" type="submit">Save</button>
									<button class="btn btn-default cancleBtn" id="beaconCancel" type="button">Cancel</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</section>
		</div>
	</div>
</div>

<div id="listModal" class="modal" style="z-index: 3;">
	<div class="modal-row" style="overflow: scroll">
		<table class="table table-striped table-advance table-hover">
			<thead>
				<tr>
					<th style="text-align: center;"><i class="fa fa-bullseye"></i>
						비콘 코드</th>
					<th style="text-align: center;"><i
						class="fa fa-check-square-o"></i> 메이저</th>
					<th style="text-align: center;"><i
						class="fa fa-check-square-o"></i> 마이너</th>
					<th style="text-align: center;"><i class="fa fa-flag"></i> 상태</th>
				</tr>
			</thead>

			<tbody id="beaconList">

			</tbody>

		</table>
	</div>
</div>
<script src="resources/customjs/shopRegister.js"></script>