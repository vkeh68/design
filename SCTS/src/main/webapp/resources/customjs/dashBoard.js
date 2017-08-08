/**
 * 
 */
var tileSocket = new SockJS("/scts/tile-ws");

var tileTodayCountVar;

var drw_code = $("#drw_code1").text();
var X_index = $("#X_index").text();
var Y_index = $("#Y_index").text();

tileSocket.onmessage = function(event){
	var data = event.data;
	data = JSON.parse(data);
	tileTodayCountVar = data.tileTodayCount;
		
}



$(document).ready(function() {

	// 처음 로딩시 성별 그래프
	var day = $("#duration option:selected").val();

	tileGenderAll(day);
	var floor = parseInt($("#floor").val());

	console.log("대시보드 모든 정보를 확인하기 위한 콘솔로그 floor=" + floor + "   bhf_code=" + bhf_code);
	
	if($("#countStory").val() > 0) {
		imgLoad(floor);
		loadTile(floor);
		
		setInterval(function(){
			loadTile(floor);
		}, 1000);
	};

	$("#zoneType").on("click", function() {
		$("#tileShowType").val(0);
	});
	$("#categoryType").on("click", function() {
		$("#tileShowType").val(1);
	});
	$("#demoType").on("click", function() {
		$("#tileShowType").val(2);
	});


	/* ********************************************************************************************************* /
	 * 타일관련 선택시
	 */
	$("div.tileMap").on("mouseover", ".tile", function() {
		$(this).addClass("mouseover");
	});

	$("div.tileMap").on("mouseout", ".tile", function() {
		$(this).removeClass("mouseover");
	});

	// 버튼 클릭시 업로드 되어있는 사진들 전환
	$("#leftDrawingBtns").on("click", function() {
		var countStory = parseInt($("#countStory").val());
		floor = parseInt($("#floor").val());

		if(floor > 0) {
			floor--;
			$("#floor").val(floor);
			imgLoad(floor);
		}
	});

	$("#rightDrawingBtns").on("click", function() {
		var countStory = parseInt($("#countStory").val());
		floor = parseInt($("#floor").val());

		if(floor < countStory-1) {
			floor++;
			$("#floor").val(floor);
			imgLoad(floor);
		
		}
		else if(floor == countStory-1) {
			floor++;
			$("#floor").val(floor);
			$('#blueprint').empty();
			$(".tileMap").empty();

			var blueprint = $('#blueprint');
			$("<p>새로운 도면을 등록해주세요 </p>").appendTo(blueprint);
		}
	});
	/* 
	 * 타일관련 선택시
	 * *********************************************************************************************************/


	/**
	 * 타일 클릭시 해당 타일 정보를 아작스로 서버에서 가져와서 도면 우측편에 표시하는 것
	 * 실제 매장등록부분과 가장 다른 타일 클릭시 데이터를 뿌려주는 아작스
	 * 
	 */
	$("div.tileMap").on("click", ".tile", function() {
		$(".tileMap .active").removeClass("active");

		$(this).addClass("active");

		$("#tile_info_avgTime").show();

		var totalNum = $("div.tile").index($(this))
		var RowNum = $("div.tileMap > div").length;

		var drw_code = $("#drw_code").val();
		var X_index = parseInt(totalNum / RowNum);
		var Y_index = totalNum % RowNum;
		
		$("#title").attr("data-id", "1");
		$("#title").text("성별");


		$("#drw_code1").text(drw_code);
		$("#X_index").text(X_index);
		$("#Y_index").text(Y_index);

		var day = parseInt($("#duration option:selected").val());

		oneAvgTime(day, drw_code, X_index, Y_index);
		oneTileGender(day, drw_code, X_index, Y_index);


		$("#selectTile").show();
		$("#loadTile").hide();



	});



	// 버튼 누를 시 그래프
	// 성별
	$("#gender").click(function(){
		var day = $("#duration option:selected").val();
		$("#title").attr("data-id", "1");
		$("#title").text("성별");
		tileGenderAll(day);
	});

	// 나이
	$("#age").click(function(){
		var day = $("#duration option:selected").val();
		$("#title").attr("data-id", "2");
		$("#title").text("나이");
		tileAgeAll(day);
	});


	// 기간
	$("#duration").on("change", function(){
		var id = $("#title").attr("data-id");
		var day = $("#duration option:selected").val();

		if(id == "1"){
			tileGenderAll(day);
		}else if(id == "2"){
			tileAgeAll(day);
		}
	});

	// 클릭시 성별
	$("#tileGender").click(function(){
		var day = $("#tileDuration option:selected").val();
		var drw_code = $("#drw_code1").text();
		var X_index = $("#X_index").text();
		var Y_index = $("#Y_index").text();
		$("#title").attr("data-id", "1");
		$("#title").text("성별");
		oneAvgTime(day, drw_code, X_index, Y_index);
		oneTileGender(day, drw_code, X_index, Y_index);

	});
	
	

	// 나이
	$("#tileAge").click(function(){
		var day = $("#tileDuration option:selected").val();
		var drw_code = $("#drw_code1").text();
		var X_index = $("#X_index").text();
		var Y_index = $("#Y_index").text();
		$("#title").attr("data-id", "2");
		$("#title").text("나이");
		oneAvgTime(day, drw_code, X_index, Y_index);
		oneTileAge(day, drw_code, X_index, Y_index);

	});

	var options = {
			chart : {
				type : 'spline',
				animation : Highcharts.svg, // don't animate in old IE
				marginRight : 10,
				events : {
					load : function() {

						// set up the updating of the chart each second
						var series = this.series[0];
						setInterval(function() {
							var x = (new Date()).getTime(), // current time
							y = tileTodayCountVar;
							series.addPoint([ x, y ], true, true);
						}, 3000);
					}
				}
			},
			title : {
				text : '현재 존 매장 방문자'
			},
			xAxis : {
				type : 'datetime',
				tickPixelInterval : 150
			},
			yAxis : {
				title : {
					text : 'Value'
				},
				plotLines : [ {
					value : 0,
					width : 1,
					color : '#808080'
				} ]
			},
			tooltip : {
				formatter : function() {
					return '<b>'
							+ this.series.name
							+ '</b><br/>'
							+ Highcharts.dateFormat(
									'%Y-%m-%d %H:%M:%S', this.x)
							+ '<br/>'
							+ Highcharts.numberFormat(this.y, 2);
				}
			},
			legend : {
				enabled : false
			},
			exporting : {
				enabled : false
			},
			series : [ {
				name : '현재 존 매장 방문자',
				data : (function() {
					// generate an array of random data
					var data = [], time = (new Date()).getTime(), i;

					for (i = -19; i <= 0; i += 1) {
						data.push({
							x : time + i * 1000,
							y : tileTodayCountVar
						});
					}
					return data;
				}())
			} ]
		}
	
	$("#tileVisitor").click(function(){

		var day = $("#tileDuration option:selected").val();
		var drw_code = $("#drw_code1").text();
		var X_index = $("#X_index").text();
		var Y_index = $("#Y_index").text();
		
		var sendData = JSON.stringify({
			drw_code : drw_code,
			tile_crdnt_x : X_index,
			tile_crdnt_y : Y_index
		});
		
		
		if(day == "0"){
			setInterval(function(){
				tileSocket.send(sendData);
			}, 3000);
			
			

				Highcharts.chart('tile_graph', options);
			
			
		}
		
		$("#title").attr("data-id", "0");
		$("#title").text("방문자수");
		
		if(day == "7" || day == "90"){
			tileTotal(day, drw_code, X_index, Y_index);
		}
	});


	// 기간
	$("#tileDuration").on("change", function(){
		var id = $("#title").attr("data-id");
		var day = $("#tileDuration option:selected").val();
		var drw_code = $("#drw_code1").text();
		var X_index = $("#X_index").text();
		var Y_index = $("#Y_index").text();
		

		var sendData = JSON.stringify({
			drw_code : drw_code,
			tile_crdnt_x : X_index,
			tile_crdnt_y : Y_index
		});

		if(id == "1"){
			oneAvgTime(day, drw_code, X_index, Y_index);
			oneTileGender(day, drw_code, X_index, Y_index);
		}else if(id == "2"){
			oneAvgTime(day, drw_code, X_index, Y_index);
			oneTileAge(day, drw_code, X_index, Y_index)
		}else if(id == "0"){
			
			if(day == "0"){
				setInterval(function(){
					tileSocket.send(sendData);
				}, 3000);
				
				Highcharts.chart('tile_graph', options);
			}
			
			if(day == "7" || day == "90"){
				tileTotal(day, drw_code, X_index, Y_index);
			}
		}



	});




});



function tileGenderAll(day){

	$.ajax({
		url : "tileGender",
		type : "GET",
		data : {
			day: day
		},
		dataType : "json",
		success : function(result){
			var options = {
					chart: {
						plotBackgroundColor: null,
						plotBorderWidth: 0,
						plotShadow: false
					},
					title: {
						text: '전체 성별 방문율',
						align: 'center',
						verticalAlign: 'middle',
						y: 40
					},
					tooltip: {
						pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
					},
					plotOptions: {
						pie: {
							dataLabels: {
								enabled: true,
								distance: -50,
								style: {
									fontWeight: 'bold',
									color: 'white'
								}
							},
							startAngle: -90,
							endAngle: 90,
							center: ['50%', '75%']
						}
					},
					series: [{
						type: 'pie',
						name: '성별 방문율',
						innerSize: '50%',
						data: []
					}]
			}


			var length = result.tileGender.length;

			console.log(result);

			for(var i = 0; i < length; i++){
				options.series[0].data[i] = [];
				options.series[0].data[i][0] = result.tileGender[i].user_sexdstn;
				options.series[0].data[i][1] = result.tileGender[i].probability;
			}

			console.log(result.tileGender);
			Highcharts.chart('tile_graph', options);


		}
	});
}



function tileAgeAll(day){
	$.ajax({
		url : "tileAge",
		type : "GET",
		data : {
			day: day
		},
		dataType : "json",
		success : function(result){
			var options = {
					chart: {
						type: 'pie',
						options3d: {
							enabled: true,
							alpha: 45
						}
					},
					title: {
						text: '연령별 방문율'
					},
					plotOptions: {
						pie: {
							innerSize: 50,
							depth: 45
						}
					},
					series: [{
						name: '연령별 방문율',
						data: []
					}]
			}
			var length = result.tileAge.length;

			console.log(result);

			for(var i = 0; i < length; i++){
				options.series[0].data[i] = [];
				options.series[0].data[i][0] = result.tileAge[i].agegroup + "대";
				options.series[0].data[i][1] = result.tileAge[i].probability;
			}

			Highcharts.chart('tile_graph', options);
		}

	});
}


function oneAvgTime(day, drw_code, X_index, Y_index){
	$.ajax({
		url : "oneTileAvgTime",
		type : "GET",
		data : {

			day : day,
			drw_code : drw_code,
			tile_crdnt_x : X_index,
			tile_crdnt_y : Y_index
		},
		dataType : "json",
		success : function(data){

			console.log(data);
			$("#avgTime").text(data.avgTime);
			$("#tileName").text(data.tile_nm);
		}
	});




}

function oneTileGender(day, drw_code, X_index, Y_index){

	$.ajax({
		url : "oneTileGender",
		type: "GET",
		dataType : "json",
		data : {
			day : day,
			drw_code : drw_code,
			tile_crdnt_x : X_index,
			tile_crdnt_y : Y_index
		},
		success : function(result){


			var options = {
					chart: {
						plotBackgroundColor: null,
						plotBorderWidth: 0,
						plotShadow: false
					},
					title: {
						text: '전체 성별 방문율',
						align: 'center',
						verticalAlign: 'middle',
						y: 40
					},
					tooltip: {
						pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
					},
					plotOptions: {
						pie: {
							dataLabels: {
								enabled: true,
								distance: -50,
								style: {
									fontWeight: 'bold',
									color: 'white'
								}
							},
							startAngle: -90,
							endAngle: 90,
							center: ['50%', '75%']
						}
					},
					series: [{
						type: 'pie',
						name: '성별 방문율',
						innerSize: '50%',
						data: []
					}]
			}


			var length = result.oneTileGender.length;

			if(length == 0){
				$("#tile_graph").text("존에 고객 다닌 정보가 없습니다");
				$("#tile_graph").css("line-height", "150px");
			}else{

				for(var i = 0; i < length; i++){
					options.series[0].data[i] = [];
					options.series[0].data[i][0] = result.oneTileGender[i].user_sexdstn;
					options.series[0].data[i][1] = result.oneTileGender[i].probability;
				}


				Highcharts.chart('tile_graph', options);
			}

			console.log(result);

		}
	});

}

function oneTileAge(day, drw_code, X_index, Y_index){
	$.ajax({
		url : "oneTileAge",
		type : "GET",
		data : {
			day : day,
			drw_code : drw_code,
			tile_crdnt_x : X_index,
			tile_crdnt_y : Y_index
		},
		dataType : "json",
		success : function(result){
			var options = {
					chart: {
						type: 'pie',
						options3d: {
							enabled: true,
							alpha: 45
						}
					},
					title: {
						text: '연령별 방문율'
					},
					plotOptions: {
						pie: {
							innerSize: 50,
							depth: 45
						}
					},
					series: [{
						name: '연령별 방문율',
						data: []
					}]
			}
			var length = result.tileAge.length;

			if(length == 0){
				$("#tile_graph").text("존에 고객 다닌 정보가 없습니다");
				$("#tile_graph").css("line-height", "150px");
			}else{

				for(var i = 0; i < length; i++){
					options.series[0].data[i] = [];
					options.series[0].data[i][0] = result.tileAge[i].agegroup + "대";
					options.series[0].data[i][1] = result.tileAge[i].probability;
				}

				Highcharts.chart('tile_graph', options);
			}
		}

	});
}


function tileTotal(day, drw_code, X_index, Y_index){

	$.ajax({
		url : "tileTotal",
		type : "GET",
		data : {
			day : day,
			drw_code : drw_code,
			tile_crdnt_x : X_index,
			tile_crdnt_y : Y_index
		},
		dataType : "json",
		success : function(result){
			var options = {

					chart : {
						width : 300,
						height : 200
					},
					title: {
						text: '방문자 수'
					},
					legend: {
						layout: 'vertical',
						align: 'right',
						verticalAlign: 'middle'
					},

					xAxis : {
						categories : []
					},

					series: [
						{
							name: '방문자수',
							data: []
						}, {
							name: '방문자율',
							data: []
						}
						]
			}

			console.log(result);

			var length = result.tileTotal.length;

			if(length == 0){
				$("#tile_graph").text("존에 고객 다닌 정보가 없습니다");
				$("#tile_graph").css("line-height", "150px");
			}else{

				for(var i = 0; i < length; i++){
					options.xAxis.categories[i] = result.tileTotal[i].cours_pasng_time;
					options.series[0].data[i] = result.tileTotal[i].tile_visit;
					options.series[1].data[i] = result.tileTotal[i].probability;

				}

				Highcharts.chart('tile_graph', options);
			}


		}
	});



}


