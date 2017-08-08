<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script src="resources/customjs/sockjs.js"></script>

<style>
#searchSales {
	display: none;
}

#month {
	display: none;
}

#productSalesInfo {
	display: none;
}

#customerProductRankInfo {
	display: none;
	font-size: 20px;
}

 #daySalesChart{
	display : none;
}

#productSalesChart{
	display : none;
}

#ageSalesChart{
	display : none;
}

#monthSalesChart{
	display : none;
} 
</style>

<script>
var sock = new SockJS("/scts/sales-ws");
var searchYearSalesSock = new SockJS("/scts/searchYearSales-ws");

var bhf_code = "${bhf_code}";

searchYearSalesSock.onmessage = function(event){
	var data = event.data;
	data = JSON.parse(data);

	console.log(data);

	$("#daySales .count").text(data.todaySales);
	$("#MonthlySales .count").text(data.monthTotalSales);

	yearSales(data);

}

sock.onmessage = function(event){
	var data = event.data;
	data = JSON.parse(data);

	console.log(data);

	$("#daySales .count").text(data.todaySales);
	$("#MonthlySales .count").text(data.monthTotalSales);

	yearSales(data);

}

sock.onopen = function() {
	console.log('open');
	sendMessage()

};


var options = {
		chart: {
			type: 'column'
		},
		title: {
			text: '연매출'
		},
		xAxis: {
			type: 'category'
		},
		yAxis: {
			title: {
				text: '총매출'
			}

		},
		legend: {
			enabled: false
		},
		plotOptions: {
			series: {
				borderWidth: 0,
				dataLabels: {
					enabled: true

				},
				animation :{}
			}
		},

		tooltip: {
			headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
			pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y}</b>원<br/>'
		},
		series : [{
			colorByPoint:true,
			data : []
		}],
		drilldown : {
			series : []
		}
}

function sendMessage(){
	var year = parseInt(new Date().getFullYear());
	var json = JSON.stringify({
		year2 : year,
		year1 : (year-4),
		bhf_code : bhf_code
	});
	


	sock.send(json);

}

function yearSales(data){
	
	var length = data.yearSales.length;

	for(var i=0; i < length; i++){

		options.series[0].data[i] = {};
		options.series[0].data[i].name =  data.yearSales[i].year;
		options.series[0].data[i].y = data.yearSales[i].totalPrice;
		options.series[0].data[i].drilldown = data.yearSales[i].year;
		
		options.drilldown.series[i] = {}
		options.drilldown.series[i].name = data.yearSales[i].year;
		options.drilldown.series[i].id = data.yearSales[i].year;
		options.drilldown.series[i].data = [];
		yearToMonth(data.yearSales[i].year, i);

	}
	

	
	Highcharts.chart('salesChart', options);
	
	
	$("#settleSales").children().remove();

	$("#settleSales").append($("<tr></tr>").addClass('tr'));
	$('.tr').append($("<td></td>").text("연도"));
	$('.tr').append($("<td></td>").text("결제수단"));
	$('.tr').append($("<td></td>").text("총 매출"));
	
	var length = data.yearSalesInfo.length;

	for(var i = 0; i < length; i++){
		$("#settleSales").append($("<tr></tr>").attr("data", i));

		$("tr[data="+i+"]").append($("<td></td>").text(data.yearSalesInfo[i].year));
		$("tr[data="+i+"]").append($("<td></td>").text(data.yearSalesInfo[i].setle_mth_nm));
		$("tr[data="+i+"]").append($("<td></td>").text(data.yearSalesInfo[i].totalPrice +"원"));

	}

}

function yearToMonth(year, j){
	
	$.ajax({
		type : "GET",
		url : "yearToMonth",
		data : {
			year : year
		},
		dataType: 'json',
		success : function(data) {
			var length = data.yearToMonth.length;

			for(var i=0; i < length; i++){
				
				options.drilldown.series[j].data[i] = []
				options.drilldown.series[j].data[i][0]= data.yearToMonth[i].bill_issu_de;
				options.drilldown.series[j].data[i][1]= data.yearToMonth[i].totalPrice;


			}


		}
	});
	
}


$(document).ready(function(){

	highchartTheme();

	// 연매출 barchart!!!
	var year = new Date().getFullYear();

	for(var i = year; i >= year-4; i--){
		$(".year").append($("<option value='"+i+"'></option>").text(i));
	}


	$("#searchYear").click(function(){

		var year1 = $("#year1 option:selected").val();
		var year2 = $("#year2 option:selected").val();

		var json = JSON.stringify({
			year1 : year1,
			year2 : year2,
			bhf_code : bhf_code
		});

		searchYearSalesSock.send(json);
		
		
	});
	
	var month = new Date().getMonth() + 1;

	if(month <= 10){
		month = "0"+month;
	}
	
	var date = year + "-" + month;
	
	$.ajax({
		url : "productRank",
		type : "GET",
		dataType : "json",
		data:{
			date : date,
			standard : 1,
			bhf_code : bhf_code
		},
		success : function(data){
			var product = new Array();
			var length = data.result.length;
			
			for(var i=0; i<length; i++){
				product.push(data.result[i].goods_nm + " " + data.result[i].totalPrice+"원");
			}
			
			console.log(product);
			var j = 0;
			$(".productRank .count").css("font-size", "25px");
			$(".productRank .count").text(product[j]);
			j = 1;
			setInterval(function(){
				$(".productRank .count").css("font-size", "25px");
				$(".productRank .count").text(product[j]);
				j++;
				
				if(j == product.length){
					j=0;
				}
			}, 3000);
			
		}
	});


});



</script>
<!-- 일 매출, 월매출, 상품별 매출, 고객별 매출 -->

<div class="row">
	<div class="col-lg-12">
		<h3 class="page-header">
			<i class="fa fa fa-bars"></i>매출관리
		</h3>
		<ol class="breadcrumb">
			<li><i class="fa fa-home"></i><a href="index">Home</a></li>
			<li><i class="fa fa-bars"></i>Management</li>
		</ol>

	</div>
</div>

<div class="row">
	<div class="col-lg-3 col-md-3 col-sm-12 col-xs-12" id="daySales">
		<div class="info-box blue-bg daySales">
			<i class="fa fa-cloud-download"></i>
			<div class="count">0</div>
			<div class="title">일 판매량</div>
		</div>
		<!--/.info-box-->
	</div>
	<!--/.col-->

	<div class="col-lg-3 col-md-3 col-sm-12 col-xs-12" id="MonthlySales">
		<div class="info-box orange-bg2 MonthlySales">
			<i class="fa fa-shopping-cart"></i>
			<div class="count">0</div>
			<div class="title">월별 판매량</div>
		</div>
		<!--/.info-box-->
	</div>
	<!--/.col-->

	<div class="col-lg-3 col-md-3 col-sm-12 col-xs-12">
		<div class="info-box yellow-bg2 productRank">
			<i class="fa fa-thumbs-o-up"></i>
			<div class="count">0</div>
			<div class="title">월간 상품 판매 순위</div>
		</div>
		<!--/.info-box-->
	</div>
	<!--/.col-->

	<div class="col-lg-3 col-md-3 col-sm-12 col-xs-12">
		<div class="info-box dark-bg customerProductRank">
			<i class="fa fa-cubes"></i>
			<div class="count">0</div>
			<div class="title">고객 별 상품 판매 순위</div>
		</div>
		<!--/.info-box-->
	</div>
	<!--/.col-->

</div>


<!-- 일매출 div 누를경우 나타남 -->
<div id="searchSales" class="form-group">

	<table class="table">

		<tr>
			<td>주문일</td>
			<td><input type="date" id="date2" /> ~ <input type="date"
				id="date1" /></td>
			<td>
				<button class="btn btn-primary" id="searchDate">검색</button>
			</td>
		</tr>
		<tr>
			<td>결제방식</td>
			<td><select id="setle_mth_code">

					<option value="2">현금</option>
					<option value="1">카드</option>
					<option value="3">상품권</option>
					<option value="4">포인트</option>

			</select></td>
			<td>
				<button class="plain btn btn-default">Plain</button>
				<button class="inverted btn btn-default">Inverted</button>
				<button class="polar btn btn-default">Polar</button>
			</td>
		</tr>
	</table>

</div>


<!-- 처음 로딩시 나타남 -->
<div id="searchYearSales">

	<table class="table">
		<tr>
			<td>연도</td>
			<td><select class="year" id="year1">
			</select> ~ <select class="year" id="year2">
			</select></td>
			<td>

				<button class="btn btn-primary" id="searchYear">검색</button>
			</td>
		</tr>
	</table>

</div>

<div id="month">

	<table class="table">
		<tr>
			<td>날짜</td>
			<td><input type="month" id="month2" /> <input type="month"
				id="month1" />
			<td>
				<button class="btn btn-primary" id="searchMonth">검색</button>
			</td>
		</tr>
		<tr>
			<td>결제수단</td>
			<td><select id="setle_mth_code2">

					<option value="2">현금</option>
					<option value="1">카드</option>
					<option value="3">상품권</option>
					<option value="4">포인트</option>

			</select></td>
			<td>
				<button class="plain btn btn-default">Plain</button>
				<button class="inverted btn btn-default">Inverted</button>
				<button class="polar btn btn-default">Polar</button>
			</td>
		</tr>
	</table>

</div>

<div id="productSalesInfo">
	<table class="table">
		<tr>
			<td width="23%">
				<!-- 아이콘!! < 이모양이랑 > 이모양 -->
				<button class="btn btn-default prev" style="width: 70px">&lt</button>
				<button class="btn btn-default next" style="width: 70px">&gt</button>
			</td>
			<td width="50%">
				<h3>2017-06</h3>
			</td>

			<td><button class="btn btn-default income" style="width: 85px">순이익순</button>
				<button class="btn btn-default total" style="width: 85px">총매출순</button>
			</td>

		</tr>


	</table>

</div>

<div id="customerProductRankInfo">
	<table class="table">
		<tr>
			<td width="23%">
				<!-- 아이콘!! < 이모양이랑 > 이모양 -->
				<button class="btn btn-default prev" style="width: 70px">&lt</button>
				<button class="btn btn-default next" style="width: 70px">&gt</button>
			</td>
			<td width="45%">
				<p style="font-size: 25px">
					<span id="date" style="font-size: 25px">2017-06</span> <span
						id="age" style="font-size: 25px">10</span><span id="a">대</span>
				</p>
			</td>

			<td>
				<button id="age10" class="btn btn-default">10대</button>
				<button id="age20" class="btn btn-default">20대</button>
				<button id="age30" class="btn btn-default">30대</button>
				<button id="age40" class="btn btn-default">40대</button>
				<button id="age50" class="btn btn-default">50대 이후</button>
				<button id="income" class="btn btn-default income"
					style="width: 85px" disabled>순이익순</button>
				<button id="total" class="btn btn-default total" style="width: 85px"
					disabled>총매출순</button>
			</td>

		</tr>


	</table>


</div>

<!-- Bar -->
<div class="row">
	<div class="col-lg-12">
		<section class="panel panel-default">
			<header class="panel-heading chartTitle"> 연매출 </header>
			<div class="panel-body text-center chart">
				<div id="salesChart"
					style="min-width: 310px; height: 400px; margin: 0 auto"></div>
				<div id="daySalesChart"
					style="min-width: 310px; height: 400px; margin: 0 auto"></div>
				<div id="ageSalesChart"
					style="min-width: 310px; height: 400px; margin: 0 auto"></div>
				<div id="monthSalesChart"
					style="min-width: 310px; height: 400px; margin: 0 auto"></div>
				<div id="productSalesChart"
					style="min-width: 310px; height: 400px; margin: 0 auto"></div>
				<div id="genderBtn"></div>
			</div>
		</section>
	</div>
</div>

<div class="row">
	<table class="table table-striped table-advance table-hover"
		id="settleSales">

		<tr>
			<th>연도</th>
			<th>결제수단</th>
			<th>총매출</th>
		</tr>
	</table>

</div>

<script>
//---------------------------------------------------------- 웹소켓 -------------------------
var daySalesSocket = new SockJS("/scts/daySales-ws");
var monthSalesSocket = new SockJS("/scts/monthSales-ws");
var productRankSocket = new SockJS("/scts/productRank-ws");
var customerRankSocket = new SockJS("/scts/customerRank-ws");
var searchDaySalesSocket = new SockJS("/scts/searchDaySales-ws");

var bhf_code = "${bhf_code}";

searchDaySalesSocket.onmessage = function(event){

	var data = event.data;
	data = JSON.parse(data);

	$("#daySales .count").text(data.todaySales);
	$("#MonthlySales .count").text(data.monthTotalSales);

	console.log(data);

	graphInfo(data);


}


customerRankSocket.onmessage = function(event){

	var data = event.data;
	data = JSON.parse(data);

	$("#daySales .count").text(data.todaySales);
	$("#MonthlySales .count").text(data.monthTotalSales);

	console.log(data);

	ageSales(data);

}



productRankSocket.onmessage = function(event){

	var data = event.data;
	data = JSON.parse(data);

	$("#daySales .count").text(data.todaySales);
	$("#MonthlySales .count").text(data.monthTotalSales);

	console.log(data);

	productInfo(data);

}


monthSalesSocket.onmessage = function(event){

	var data = event.data;
	data = JSON.parse(data);

	$("#daySales .count").text(data.todaySales);
	$("#MonthlySales .count").text(data.monthTotalSales);

	console.log(data);

	monthInfo(data);


}


daySalesSocket.onmessage = function(event){

	var data = event.data;
	data = JSON.parse(data);

	$("#daySales .count").text(data.todaySales);
	$("#MonthlySales .count").text(data.monthTotalSales);

	console.log(data);

	graphInfo(data);


}


//---------------------------------------------------------- 매출 -------------------------
//일매출
$('#daySales').click(function(){
	
	$("#salesChart").hide();
	$("#daySalesChart").show();
	$("#ageSalesChart").hide();
	$("#productSalesChart").hide();
	$("#monthSalesChart").hide();
	
	
	
	$('.chartTitle').text("일매출");

	$("#searchSales").show();
	$("#searchYearSales").hide();
	$("#month").hide();
	$("#productSalesInfo").hide();
	$("#customerProductRankInfo").hide();
	$(".w").hide();
	$(".m").hide();

	$("#settleSales").children().remove();


	$("#settleSales").append($("<tr></tr>").addClass('tr'));
	$('.tr').append($("<td></td>").text("일"));
	$('.tr').append($("<td></td>").text("결제수단"));
	$('.tr').append($("<td></td>").text("총 매출"));

	var json = JSON.stringify({
		bhf_code : bhf_code
	});
	
	daySalesSocket.send(json);

});

//월매출
$(".MonthlySales").click(function(){
	
	
	$("#salesChart").hide();
	$("#daySalesChart").hide();
	$("#ageSalesChart").hide();
	$("#productSalesChart").hide();
	$("#monthSalesChart").show();
	
	
	
	$("#searchSales").hide();
	$("#searchYearSales").hide();
	$("#month").show();
	$("#productSalesInfo").hide();
	$("#customerProductRankInfo").hide();
	$(".w").hide();
	$(".m").hide();

	$('.chartTitle').text("월매출");

	$("#settleSales").children().remove();

	$("#settleSales").append($("<tr></tr>").addClass('tr'));
	$('.tr').append($("<td></td>").text("월"));
	$('.tr').append($("<td></td>").text("결제수단"));
	$('.tr').append($("<td></td>").text("총 매출"));


	var date = new Date();

	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	if (month < 10) {
		month = "0" + month;
	}

	var mm = date.getMonth() - 3;
	if(mm < 10){
		mm = "0" +  mm;
	}


	var month1 = year + "-" + month;
	var month2 = year + "-" + mm;
	var setle_mth_code = null;

	var sendData = JSON.stringify({
		month1 : month1,
		month2 : month2,
		setle_mth_code : setle_mth_code,
		bhf_code : bhf_code
	});


	
	monthSalesSocket.send(sendData);




});

//상품 순위

$(".productRank").click(function(){
	
	
	$("#salesChart").hide();
	$("#daySalesChart").hide();
	$("#ageSalesChart").hide();
	$("#productSalesChart").show();
	$("#monthSalesChart").hide();

	
	$("#searchSales").hide();
	$("#searchYearSales").hide();
	$("#month").hide();
	$("#productSalesInfo").show();
	$("#customerProductRankInfo").hide();
	$(".w").hide();
	$(".m").hide();

	var year = new Date().getFullYear();
	var month = new Date().getMonth() + 1;

	if(month <= 10){
		month = "0"+month;
	}

	var date = year + "-" + month;
	var sendData = JSON.stringify({
		date : date,
		standard : 1,
		bhf_code : bhf_code

	});

	productRankSocket.send(sendData);
});

//고객이 선호하는 상품 랭킹
//순이익순으로 보여줌
$(".customerProductRank").click(function(){
	
	
	$("#salesChart").hide();
	$("#daySalesChart").hide();
	$("#ageSalesChart").hide();
	$("#productSalesChart").show();
	$("#monthSalesChart").hide();
	
	
	
	
	$("#date").text("상품 순이익");
	$("#age").text("순위");
	$("#a").hide();


	$("#searchSales").hide();
	$("#searchYearSales").hide();
	$("#month").hide();
	$("#productSalesInfo").hide();
	$("#customerProductRankInfo").show();
	
	$(".w").hide();
	$(".m").hide();

	$("#settleSales").children().remove();

	$("#settleSales").append($("<tr></tr>").addClass('tr'));
	$('.tr').append($("<td></td>").text("상품 이름"));
	$('.tr').append($("<td></td>").text("쿠폰 사용량"));
	$('.tr').append($("<td></td>").text("총 판매 수량"));
	$('.tr').append($("<td></td>").text("순이익"));
	$('.tr').append($("<td></td>").text("총 매출"));


	var year = new Date().getFullYear();
	var month = new Date().getMonth() + 1;

	if(month <= 10){
		month = "0"+month;
	}

	var date = year + "-" + month;
	var sendData = JSON.stringify({
		date : date,
		standard : 2,
		bhf_code : bhf_code

	});

	productRankSocket.send(sendData);

});

//일매출 검색
$("#searchDate").click(function(event){

	var date1 = $("#date1").val();
	var date2 = $("#date2").val();

	var setle_mth_code = $("#setle_mth_code option:selected").val();

	var sendData = JSON.stringify({
		date1 : date1,
		date2 : date2,
		setle_mth_code : setle_mth_code,
		bhf_code : bhf_code

	});

	searchDaySalesSocket.send(sendData);


});



// 월매출 검색
$("#searchMonth").click(function(){

	var month1 = $("#month1").val();
	var month2 = $("#month2").val();
	var setle_mth_code = $("#setle_mth_code2 option:selected").val();
	
	var sendData = JSON.stringify({
		month1 : month1,
		month2 : month2,
		setle_mth_code : setle_mth_code,
		bhf_code : bhf_code
	});


	monthSalesSocket.send(sendData);

});


function ageForm(){
	$("#genderBtn").empty();
	$("#genderBtn").append($("<button class='btn btn-default m'>남자</button>"));
	$("#genderBtn").append($("<button class='btn btn-default w'>여자</button>"));
	
	
	
	var year = new Date().getFullYear();

	var month = new Date().getMonth() + 1;

	if(month <= 10){
		month = "0"+month;
	}
	
	var date = year + "-" + month;
	
	$("#date").text(date);
	
	
	$("#customerProductRankInfo .income").removeAttr("disabled");
	$("#customerProductRankInfo .income").attr("enabled", true);
	$("#customerProductRankInfo .total").removeAttr("disabled");
	$("#customerProductRankInfo .total").attr("enabled", true);

}




$("#age10").click(function(){
	
	ageForm();
	$("#a").show();

	var date = $("#date").text();
	$("#age").text("10");

	var sendData = JSON.stringify({
		date : date,
		standard : 1,
		age : 10,
		gender : "null",
		bhf_code : bhf_code

	});

	customerRankSocket.send(sendData);
	

});

$("#age20").click(function(){
	
	ageForm();
	
	$("#a").show();
	
	var date = $("#date").text();
	$("#age").text("20");
	
	var sendData = JSON.stringify({
		date : date,
		standard : 1,
		age : 20,
		gender : "null",
		bhf_code : bhf_code

	});

	customerRankSocket.send(sendData);

});


$("#age30").click(function(){
	
	$("#a").show();
	
	ageForm();

	var date = $("#date").text();
	$("#age").text("30");
	
	var sendData = JSON.stringify({
		date : date,
		standard : 1,
		age : 30,
		gender : "null",
		bhf_code : bhf_code

	});

	customerRankSocket.send(sendData);
	

});

$("#age40").click(function(){
	$("#a").show();
	
	ageForm();

	var date = $("#date").text();
	$("#age").text("40");
	
	var sendData = JSON.stringify({
		date : date,
		standard : 1,
		age : 40,
		gender : "null",
		bhf_code : bhf_code

	});

	customerRankSocket.send(sendData);

});

$("#age50").click(function(){
	
	$("#a").show();
	
	
	ageForm();

	var date = $("#date").text();
	$(" #age").text("50");

	var sendData = JSON.stringify({
		date : date,
		standard : 1,
		age : 50,
		gender : "null",
		bhf_code : bhf_code

	});

	customerRankSocket.send(sendData);

});

$(document).on("click",".m", function(){

	var date = $("#date").text();
	var age = $("#age").text();
	
	var sendData = JSON.stringify({
		date : date,
		standard : 1,
		age : age,
		gender : "m",
		bhf_code : bhf_code

	});

	customerRankSocket.send(sendData);
	
});

$(document).on("click",".w", function(){
	var date = $("#date").text();
	var age = $("#age").text();
	
	var sendData = JSON.stringify({
		date : date,
		standard : 1,
		age : age,
		gender : "w",
		bhf_code : bhf_code

	});

	customerRankSocket.send(sendData);
});


function ageSales(data){

	
	$("#salesChart").hide();
	$("#daySalesChart").hide();
	$("#ageSalesChart").show();
	$("#productSalesChart").hide();
	$("#monthSalesChart").hide();
	
	

	$('.chartTitle').text($("#age").text() + "대 고객별 선호 상품 순위");

	var options = {

			chart: {
				type: 'column'
			},

			title: {
				text: $('.chartTitle').text()
			},

			legend: {
				align: 'right',
				verticalAlign: 'middle',
				layout: 'vertical'
			},
			xAxis : {
				categories : []

			},
			yAxis: {
				allowDecimals: false,
				title: {
					text: 'Amount'
				}
			},
			series: [{
				name: '순이익',
				data: []
			}, {
				name: '총매출',
				data: []
			}],
			responsive: {
				rules: [{
					condition: {
						maxWidth: 500
					},
					chartOptions: {
						legend: {
							align: 'center',
							verticalAlign: 'bottom',
							layout: 'horizontal'
						},
						yAxis: {
							labels: {
								align: 'left',
								x: 0,
								y: -5
							},
							title: {
								text: null
							}
						},
						subtitle: {
							text: null
						},
						credits: {
							enabled: false
						}
					}
				}]
			}
	}
	var length = data.ageSales.length;

	for(var i=0; i < length; i++){
		options.xAxis.categories[i] = data.ageSales[i].goods_nm;
		options.series[0].data[i] = data.ageSales[i].goods_netIncome;
		options.series[1].data[i] = data.ageSales[i].totalPrice;

	}



	console.log("옵션옵션");
	console.log(options);
	console.log($("#ageSalesChart"));
	Highcharts.chart('ageSalesChart', options);


	$("#settleSales").children().remove();

	$("#settleSales").append($("<tr></tr>").addClass('tr'));
	$('.tr').append($("<td></td>").text("상품 이름"));
	$('.tr').append($("<td></td>").text("쿠폰 사용량"));
	$('.tr').append($("<td></td>").text("총 판매 수량"));
	$('.tr').append($("<td></td>").text("순이익"));
	$('.tr').append($("<td></td>").text("총 매출"));



	var length = data.ageSalesInfo.length;

	for(var i = 0; i < length; i++){
		$("#settleSales").append($("<tr></tr>").attr("data", i));

		$("tr[data="+i+"]").append($("<td></td>").text(data.ageSalesInfo[i].goods_nm));
		$("tr[data="+i+"]").append($("<td></td>").text(data.ageSalesInfo[i].totalCouponCount+"개"));
		$("tr[data="+i+"]").append($("<td></td>").text(data.ageSalesInfo[i].totalPurchsgoods_qy+"개"));
		$("tr[data="+i+"]").append($("<td></td>").text(data.ageSalesInfo[i].goods_netIncome+"원"));
		$("tr[data="+i+"]").append($("<td></td>").text(data.ageSalesInfo[i].totalPrice +"원"));

	}

}

function graphInfo(data){
	
	
	
	var length = data.daySales.length;

	var options = {

			title: {
				text: $('.chartTitle').text()
			},
			subtitle: {
				text: 'Plain'
			}, 
			xAxis:{
				categories:[]
			},
			series:[{
				type: 'column',
				colorByPoint: true,
				data : [],
				showInLegend: false
			}]

	}

	for(var i = 0; i < length; i++){

		options.xAxis.categories[i] = data.daySales[i].bill_issu_de;
		options.series[0].data[i] = data.daySales[i].totalPrice;

	}


 Highcharts.chart('daySalesChart', options);
	
	$("#settleSales").children().remove();

	$("#settleSales").append($("<tr></tr>").addClass('tr'));
	$('.tr').append($("<td></td>").text("월"));
	$('.tr').append($("<td></td>").text("결제수단"));
	$('.tr').append($("<td></td>").text("총 매출"));


	var length = data.daySalesInfo.length;

	for(var i = 0; i < length; i++){
		$("#settleSales").append($("<tr></tr>").attr("data", i));

		$("tr[data="+i+"]").append($("<td></td>").text(data.daySalesInfo[i].day));
		$("tr[data="+i+"]").append($("<td></td>").text(data.daySalesInfo[i].setle_mth_nm));
		$("tr[data="+i+"]").append($("<td></td>").text(data.daySalesInfo[i].totalPrice +"원"));

	}
}


function monthInfo(data){
	
	
	
	var length = data.daySales.length;

	var options = {

			title: {
				text: $('.chartTitle').text()
			},
			subtitle: {
				text: 'Plain'
			}, 
			xAxis:{
				categories:[]
			},
			series:[{
				type: 'column',
				colorByPoint: true,
				data : [],
				showInLegend: false
			}]

	}

	for(var i = 0; i < length; i++){

		options.xAxis.categories[i] = data.daySales[i].bill_issu_de;
		options.series[0].data[i] = data.daySales[i].totalPrice;

	}


	 Highcharts.chart('monthSalesChart', options);
	
	$("#settleSales").children().remove();

	$("#settleSales").append($("<tr></tr>").addClass('tr'));
	$('.tr').append($("<td></td>").text("월"));
	$('.tr').append($("<td></td>").text("결제수단"));
	$('.tr').append($("<td></td>").text("총 매출"));


	var length = data.daySalesInfo.length;

	for(var i = 0; i < length; i++){
		$("#settleSales").append($("<tr></tr>").attr("data", i));

		$("tr[data="+i+"]").append($("<td></td>").text(data.daySalesInfo[i].day));
		$("tr[data="+i+"]").append($("<td></td>").text(data.daySalesInfo[i].setle_mth_nm));
		$("tr[data="+i+"]").append($("<td></td>").text(data.daySalesInfo[i].totalPrice +"원"));

	}
}

function productInfo(data){
	
	
	//그래프

	$('.chartTitle').text("상품별 순위");

	var options = {

			chart: {
				type: 'column'
			},

			title: {
				text: $('.chartTitle').text()
			},

			legend: {
				align: 'right',
				verticalAlign: 'middle',
				layout: 'vertical'
			},
			xAxis : {
				categories : []

			},
			yAxis: {
				allowDecimals: false,
				title: {
					text: 'Amount'
				}
			},
			series: [{
				name: '순이익',
				data: []
			}, {
				name: '총매출',
				data: []
			}],
			responsive: {
				rules: [{
					condition: {
						maxWidth: 500
					},
					chartOptions: {
						legend: {
							align: 'center',
							verticalAlign: 'bottom',
							layout: 'horizontal'
						},
						yAxis: {
							labels: {
								align: 'left',
								x: 0,
								y: -5
							},
							title: {
								text: null
							}
						},
						subtitle: {
							text: null
						},
						credits: {
							enabled: false
						}
					}
				}]
			}
	}
	var length = data.productSales.length;

	for(var i=0; i < length; i++){
		options.xAxis.categories[i] = data.productSales[i].goods_nm;

		options.series[0].data[i] = data.productSales[i].goods_netIncome;
		options.series[1].data[i] = data.productSales[i].totalPrice;

	}


	Highcharts.chart('productSalesChart', options);


	$("#settleSales").children().remove();

	$("#settleSales").append($("<tr></tr>").addClass('tr'));
	$('.tr').append($("<td></td>").text("상품 이름"));
	$('.tr').append($("<td></td>").text("쿠폰 사용량"));
	$('.tr').append($("<td></td>").text("총 판매 수량"));
	$('.tr').append($("<td></td>").text("순이익"));
	$('.tr').append($("<td></td>").text("총 매출"));



	length = data.productSalesInfo.length;

	for(var i = 0; i < length; i++){
		$("#settleSales").append($("<tr></tr>").attr("data", i));

		$("tr[data="+i+"]").append($("<td></td>").text(data.productSalesInfo[i].goods_nm));
		$("tr[data="+i+"]").append($("<td></td>").text(data.productSalesInfo[i].totalCouponCount+"개"));
		$("tr[data="+i+"]").append($("<td></td>").text(data.productSalesInfo[i].totalPurchsgoods_qy+"개"));
		$("tr[data="+i+"]").append($("<td></td>").text(data.productSalesInfo[i].goods_netIncome+"원"));
		$("tr[data="+i+"]").append($("<td></td>").text(data.productSalesInfo[i].totalPrice +"원"));

	}

}

$('.plain').click(function () {
	chart.update({
		chart: {
			inverted: false,
			polar: false
		},
		subtitle: {
			text: 'Plain'
		}
	});
});


$('.polar').click(function () {

	chart.update({
		chart: {
			inverted: false,
			polar: true
		},
		subtitle: {
			text: 'Polar'
		}
	});
});

$('.inverted').click(function () {
	chart.update({
		chart: {
			inverted: true,
			polar: false
		},
		subtitle: {
			text: 'Inverted'
		}
	});
});

$('#productSalesInfo .income').click(function () {

	var date = $("#productSalesInfo h3").text();

	var sendData = JSON.stringify({
		date : date,
		standard : 2,
		bhf_code : bhf_code

	});

	productRankSocket.send(sendData);

});

$('#productSalesInfo .total').click(function () {

	var date = $("#productSalesInfo h3").text();
	var sendData = JSON.stringify({
		date : date,
		standard : 1,
		bhf_code : bhf_code

	});

	productRankSocket.send(sendData);

});

$('#customerProductRankInfo .income').click(function () {

	var date = $("#date").text();
	var age = $("#age").text();

	var sendData = JSON.stringify({
		date : date,
		standard : 2,
		age : age,
		gender : "null",
		bhf_code : bhf_code

	});

	customerRankSocket.send(sendData);




});

$('#customerProductRankInfo .total').click(function () {

	var date = $("#date").text();
	var age = $("#age").text();
	var sendData = JSON.stringify({
		date : date,
		standard : 1,
		age : age,
		gender : "null",
		bhf_code : bhf_code

	});

	customerRankSocket.send(sendData);


});

var text = $("#productSalesInfo h3").text();

var year = parseInt(text.split("-")[0]);

var month = parseInt(text.split("-")[1].split("0")[1]);

$('#productSalesInfo .prev').click(function(){


	month = month - 1;
	if(month <= 0){
		year = year -1;
		month = 12;
	}

	if(month < 10){
		month = "0"+month;
	}

	$("#productSalesInfo h3").text(year+"-"+month);

	var date = $("#productSalesInfo h3").text();
	var sendData = JSON.stringify({
		date : date,
		standard : 1,
		bhf_code : bhf_code

	});

	productRankSocket.send(sendData);


});

$('#productSalesInfo .next').click(function(){


	if(month < 10){
		month = parseInt( $("#productSalesInfo h3").text().split("-")[1].split("0")[1]);

	}else{
		month = parseInt( $("#productSalesInfo h3").text().split("-")[1]);
	}


	month = month + 1;
	if(month >= 13){
		year = year +1;
		month = 1;
	}

	if(month < 10){
		month = "0"+month;
	}


	$("#productSalesInfo h3").text(year+"-"+month);


	if(month < 10){
		month = parseInt(month.split("0")[1]);
	}

	var date = $("#productSalesInfo h3").text();


	var sendData = JSON.stringify({
		date : date,
		standard : 1,
		bhf_code : bhf_code

	});

	productRankSocket.send(sendData);

});




text = $("#customerProductRankInfo #date").text();

year = parseInt(text.split("-")[0]);

month = parseInt(text.split("-")[1].split("0")[1]);

$('#customerProductRankInfo .prev').click(function(){

	month = month - 1;
	if(month <= 0){
		year = year -1;
		month = 12;
	}

	if(month < 10){
		month = "0"+month;
	}

	$("#date").text(year+"-"+month);

	var date = $("#date").text();
	var age = $("#age").text();
	
	var sendData = JSON.stringify({
		date : date,
		standard : 1,
		age : age,
		gender : "null",
		bhf_code : bhf_code

	});

	customerRankSocket.send(sendData);

});

$('#customerProductRankInfo .next').click(function(){
	

	if(month < 10){
		month = parseInt( $("#date").text().split("-")[1].split("0")[1]);

	}else{
		month = parseInt( $("#date").text().split("-")[1]);
	}


	month = month + 1;
	if(month >= 13){
		year = year +1;
		month = 1;
	}

	if(month < 10){
		month = "0"+month;
	}


	$("#date").text(year+"-"+month);


	if(month < 10){
		month = parseInt(month.split("0")[1]);
	}

	var date = $("#date").text();
	var age = $("#age").text();
	
	
	var sendData = JSON.stringify({
		date : date,
		standard : 1,
		age : age,
		gender : "null",
		bhf_code : bhf_code

	});

	customerRankSocket.send(sendData);

});




</script>
