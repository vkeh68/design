<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="row">
	<div class="col-lg-12">
		<h3 class="page-header">
			<i class="fa fa fa-bars"></i>프로필정보
		</h3>
		<ol class="breadcrumb">
			<li><i class="fa fa-home"></i><a href="index">Home</a></li>
			<li><i class="fa fa-bars"></i>MyProfile</li>
		</ol>
	</div>
</div>

<div class="row">
	<!-- profile-widget -->
	<div class="col-lg-12">
		<div class="profile-widget profile-widget-info">
			<div class="panel-body">
				<div class="col-lg-2 col-sm-2">
					<h4>${user_id}</h4>
					<div class="follow-ava">
						<img src="" alt="">
					</div>
					<h6>Administrator</h6>
				</div>
				<div class="col-lg-4 col-sm-4 follow-info">
					<p>Hello I’m !</p>
					<h6>
						<span><i class="icon_clock_alt"></i>11:05 AM</span> <span><i
							class="icon_calendar"></i>25.10.13</span> <span><i
							class="icon_pin_alt"></i>NY</span>
					</h6>
				</div>
				<div class="col-lg-2 col-sm-6 follow-info weather-category">
					<ul>
						<li class="active"><i class="fa fa-comments fa-2x"> </i> <br>
						</li>

					</ul>
				</div>
				<div class="col-lg-2 col-sm-6 follow-info weather-category">
					<ul>
						<li class="active"><i class="fa fa-bell fa-2x"> </i> <br>
						</li>

					</ul>
				</div>
				<div class="col-lg-2 col-sm-6 follow-info weather-category">
					<ul>
						<li class="active"><i class="fa fa-tachometer fa-2x"> </i> <br>
						</li>

					</ul>
				</div>
			</div>
		</div>
	</div>
</div>


<!-- page start-->
<div class="row">
	<div class="col-lg-12">
		<section class="panel">
			<header class="panel-heading tab-bg-info">
				<ul class="nav nav-tabs">
					<li class="active"><a data-toggle="tab" href="#profile"> <i
							class="icon-home"></i>Profile
					</a></li>

					<li><a data-toggle="tab" href="#recent-activity"><i
							class="icon-user"></i>Activity</a></li>
				</ul>
			</header>
			<div class="panel-body">
				<div class="tab-content">
					<div id="recent-activity" class="tab-pane">
						<div class="profile-activity"></div>
					</div>

					<!-- profile -->
					<div id="profile" class="tab-pane active">
						<section class="panel">
							<div class="bio-graph-heading">hiiiiiii</div>
							<div class="panel-body bio-graph-info">
								<h1>Bio Graph</h1>
								<div class="row">
									<div class="bio-row">
										<p>
											<span>Name </span>:${user_id}
										</p>
									</div>
									<div class="bio-row">
										<p>
											<span>Birthday</span>: ${user_brthdy}
										</p>
									</div>
									<div class="bio-row">
										<p>
											<span>Country </span>:${user_adres}
										</p>
									</div>
									<div class="bio-row">
										<p>
											<span>Email </span>:${user_email}
										</p>
									</div>
									<div class="bio-row">
										<p>
											<span>Mobile </span>:${user_mbtlnum}
										</p>
									</div>
									<div class="bio-row">
										<p>
											<span>MarketPlace </span>:
										</p>
									</div>
								</div>
							</div>
						</section>
						<section>
							<div class="row"></div>
						</section>
					</div>
				</div>
			</div>
		</section>
	</div>
</div>