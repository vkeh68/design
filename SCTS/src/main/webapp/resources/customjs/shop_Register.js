/**
 * 
 */

$(document).ready(function() {
	
	if($("#countStory").val() > 0)
		imgLoad(0);
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
		var floor = parseInt($("#floor").val());

		if(floor > 0) {
			floor--;
			$("#floor").val(floor);
			imgLoad(floor);
			tileListReload(floor);
		}
	});
	
	$("#rightDrawingBtns").on("click", function() {
		var countStory = parseInt($("#countStory").val());
		var floor = parseInt($("#floor").val());

		if(floor < countStory-1) {
			floor++;
			$("#floor").val(floor);
			imgLoad(floor);
			tileListReload(floor);
		}
		else if(floor == countStory-1) {
			floor++;
			$("#floor").val(floor);
			$('#blueprint').empty();
			$(".tileMap").empty();
			
			var blueprint = $('#blueprint');
			$("<p>새로운 도면을 등록해주세요 </p>").appendTo(blueprint);
			var tileList = $(".tileList").empty();
			$("<p>등록되지 않은 층입니다. </p>").appendTo(tileList);
		}
	});
	/* 
	 * 타일관련 선택시
	 * *********************************************************************************************************/
	
	
	/* ********************************************************************************************************* /
	 * 매장 등록 버튼 클릭 시 뜨는 모달창 관련
	 */
	$("#MyBtn").on("click", function() {
		//$('#Modal').css('display', 'block');	// 해당 하는 부분은 shopRegister.js 에서 처리하고 있어서 필요 없음

		var countStory = parseInt($("#countStory").val());
		
		$.ajax({
			url: "shop_RegisterForm",
			type: "get",
			dataType: "json",
			success: function(data) {
				// 지점 정보들 셀렉트 리스트에 등록
				var branchList = $("#bhf_code");
				branchList.empty();
				for(var i=0; i<data.length; i++) {
					$("<option></option>").val(data[i].bhf_code).text(data[i].bhf_nm).appendTo(branchList);
				}
				
				// 층 정보 셀렉트 리스트에 등록
				var floorList = $("#floorinfo_floor");
				floorList.empty();
				for(var i=0; i<countStory+1; i++) {
					$("<option></option>").val(i+1).text((i+1) + "층").appendTo(floorList);
				}
			},
			error : function(data) {
				
			}
		});
	});

	/* 
	 * 매장 등록 버튼 클릭 시 뜨는 모달창 관련
	 * *********************************************************************************************************/


	var settingMode = $("#settingMode").val();

	$("#beaconSetting").on("click", function() {
		settingMode = 0;
		var tile_info = $("#tile_info");
		tile_info.empty();
	});
	/*
	 * 카테고리 설정 클릭 시 기본 셋팅 해주는 것
	 */
	$("#categorySetting").on("click", function() {
		settingMode = 1;
		$(".tileMap .active").removeClass("active");
		
		var tile_info = $("#tile_info");
		tile_info.empty();
		
		var largeSelectBox = $("<select id='largeCategory' name='largeCategory'></select>");
		
		var detailSelectBox = $("<select id='detailCategory' name='detailCategory'></select>");
		$("<option></option>").text("세부 카테고리를 선택해 주십시오.").appendTo(detailSelectBox);
		
		var setTileList = $("<div id='setTileList'></div>");

		var setButton = $("<button id='setTileCategory'>카테고리 설정</button>");
		
		largeSelectBox.appendTo(tile_info);
		$("<br>").appendTo(tile_info);
		detailSelectBox.appendTo(tile_info);
		setTileList.appendTo(tile_info);
		setButton.appendTo($("#tile_info"));
		
		$.ajax({
			url: "shopLargeCategory",
			type: "post",
			dataType: "json",
			success: function(data) {
				
				if(data != null) {
					$("<option></option>").text("대분류 카테고리를 선택해 주십시오.").appendTo(largeSelectBox);
					
					for(var i=0; i<data.length; i++) {
						$("<option></option>").val(data[i].LCLASCTGRY_CODE).text(data[i].LCLASCTGRY_NM).appendTo(largeSelectBox);
					}
				}
				else {
					$("<option></option>").text("등록된 카테고리가 없습니다.").appendTo(largeSelectBox);
					//$("<p></p>").text("등록된 카테고리가 없습니다.").appendTo(tile_info);
				}
			},
			error: function(data) {
				
			}
		});
	});
	
	/*
	 * 이건 큰 카테고리 선택했을 때 세부 카테고리 목록 불러오는 것
	 */
	$("#tile_info").on("change", "#largeCategory", function() {
        var lclasctgry_code = $(this).val();
        if(lclasctgry_code == null) {
        	return;
        }

		var detailSelectBox = $("#detailCategory");
		detailSelectBox.empty();
		$("<option></option>").text("카테고리를 선택해 주십시오.").appendTo(detailSelectBox);
		
		
		$.ajax({
			url: "shopDetailCategory",
			type: "post",
			data: {lclasctgry_code : lclasctgry_code},
			dataType: "json",
			success: function(data) {
				
				if(data != null) {
					
					for(var i=0; i<data.length; i++) {
						$("<option></option>").val(data[i].DETAILCTGRY_CODE).text(data[i].DETAILCTGRY_NM).appendTo(detailSelectBox);
					}
					
				}
				else {
					$("<option></option>").text("등록된 세부 카테고리가 없습니다.").appendTo(detailSelectBox);
				}
			},
			error: function(data) {
				
			}
		});
	});
	
	/*
	 * 이건 세부 카테고리 선택했을때 타일에 변화 주기 위해서 쓰는 것
	 * 해당 세부 카테고리에 속하는 모든 테이블을 가져와서 도면의 타일 위에 표시함
	 */
	$("#tile_info").on("change", "#detailCategory",function(){
        var detailctgry_code = $(this).val();
        var drw_code = parseInt($("#drw_code").val());
        
		$(".tileMap .active").removeClass("active");
		
		changeDetailCategory(detailctgry_code, drw_code);
	});

	$("#tile_info").on("click", "#setTileCategory", function() {
		var tileArray = new Array();
		
        var detailctgry_code = $("#detailCategory").val();
		var drw_code = parseInt($("#drw_code").val());

		var RowNum = $("div.tileMap > div").length;
		
		$(".tile.active").each(function(i, e) {
			var tileObj = new Object();
			
			var totalNum = $("div.tile").index($(this));

			var X_index = parseInt(totalNum / RowNum);
			var Y_index = totalNum % RowNum;

			tileObj.tile_crdnt_x = X_index;
			tileObj.tile_crdnt_y = Y_index;
			
			console.log(tileObj);
			
			tileArray.push(tileObj);
		});
		
		var jObject = new Object();
		
		if(tileArray.length > 0)	// 타일 없을경우 어레이는 안들어가게 만듬
			jObject.tileList = tileArray;
		jObject.drw_code = drw_code;
		jObject.detailctgry_code = detailctgry_code;
		
		console.log(jObject);
		
		setTileCategory(jObject);
	});
	
	/**
	 * 타일 클릭시 해당 타일 정보를 아작스로 서버에서 가져와서 도면 우측편에 표시하는 것
	 */
	$("div.tileMap").on("click", ".tile", function() {
		
		var totalNum = $("div.tile").index($(this));
		var RowNum = $("div.tileMap > div").length;

		var drw_code = parseInt($("#drw_code").val());
		var X_index = parseInt(totalNum / RowNum);
		var Y_index = totalNum % RowNum;

		console.log(X_index + "  이거 확인좀 하자  " + Y_index);
		
		if(settingMode == 1) {
			//$(".tileMap .active").removeClass("active");
			if($(this).hasClass("active") === true) {
				$(this).removeClass("active");
			}
			else {
				$(this).addClass("active");
			}
		}
		
		// 비콘 설정 모드일 때
		if(settingMode == 0) {
			$(".tileMap .active").removeClass("active");
			
			$(this).addClass("active");
			
			$.ajax({
				url: "shopTileClick",
				type: "post",
				data: {
					drw_code : drw_code,
					X_index : X_index,
					Y_index : Y_index
				},
				dataType: "json",
				success: function(data) {
					var tile_info = $("#tile_info");
					tile_info.empty();
					
					if(data != null) {
						
						$("<input type='hidden' id='tile_code'></input>").val(data.tile_code).appendTo(tile_info);
						$("<p></p>").text("tile_nm = " + data.tile_nm).appendTo(tile_info);
						
						if(data.beacon_code != null) {
							//$("<p></p>").text("beacon_code = " + data.beacon_code).appendTo(tile_info);
							$("<p></p>").text("beacon_mjr = " + data.beacon_mjr).appendTo(tile_info);
							$("<p></p>").text("beacon_mnr = " + data.beacon_mnr).appendTo(tile_info);
						}
						else {
							$("<p></p>").text("등록된 비콘이 없습니다.").appendTo(tile_info);
						}
						$("<button id='getBeacon'></button>").text("비콘설정").appendTo(tile_info);
						
					}
					else {
						$("<p></p>").text("해당 타일은 등록되어 있지 않습니다.").appendTo(tile_info);
					}
				},
				error: function(data) {
					
				}
			});
		}
		else if(settingMode == 1) {
			
		}
	});
	
	
	/**
	 * 모달에 출력된 비콘리스트를 하나 클릭하면 서버에 해당 타일에 해당 비콘을 저장시키는 것
	 * 아직 제대로 코딩 안됨
	 */
	$("#beaconList").on("click", ".beacon", function() {
		var tile_code = parseInt($("#tile_code").val());
		var beacon_mjr = parseInt($(this).children(".beacon_mjr").text());
		var beacon_mnr = parseInt($(this).children(".beacon_mnr").text());
		
		console.log(beacon_mjr + "  비콘 정보들  " + beacon_mnr);
		
		$.ajax({
			url: "setTileBeacon",
			type: "post",
			dataType: "text",
			contentType: "application/json",
			data: JSON.stringify({
				tile_code : tile_code,
				beacon_mjr : beacon_mjr,
				beacon_mnr : beacon_mnr
			}),
			success: function(data) {
				if(data == "success") {
					$('#listModal').css('display', 'none');
					var tile_info = $("#tile_info");
					
					//$("<p></p>").text("beacon_code = " + data.beacon_code).appendTo(tile_info);
					$("<p></p>").text("beacon_mjr = " + beacon_mjr).appendTo(tile_info);
					$("<p></p>").text("beacon_mnr = " + beacon_mnr).appendTo(tile_info);
					$("#getBeacon").remove();
				}
			},
			error: function(data) {
				
			}
		});
	});
	
	
	/**
	 * 타일에서 비콘설정버튼을 클릭햇을때 현재 사용할 수 있는 비콘들을 모두 가져와서 리스트 형태로 출력
	 */
	$("#tile_info").on("click", "#getBeacon", function() {
		
		$.ajax({
			url: "getBeaconList",
			type: "post",
			dataType: "json",
			success: function(data) {
				if(data != null) {
					$('#listModal').css('display', 'block');
					//listModal.style.display = "block";
					
					$("#beaconList").empty();
					
					for(var i=0; i<data.length; i++) {
						var beaconItem = $("<tr class='beacon'></tr>");
						//$("<td></td>").attr("id", "beacon_mjr").text(data[i].beacon_mjr).appendTo(beaconItem);
						$("<td></td>").text(data[i].beacon_code).appendTo(beaconItem);
						$("<td></td>").addClass("beacon_mjr").text(data[i].beacon_mjr).appendTo(beaconItem);
						$("<td></td>").addClass("beacon_mnr").text(data[i].beacon_mnr).appendTo(beaconItem);
						$("<td></td>").text(data[i].beacon_sttus).appendTo(beaconItem);
						
						beaconItem.appendTo($("#beaconList"));
					}
				}
				else {
					window.alert("등록된 비콘이 없습니다.");
				}
			},
			error: function(data) {
				
			}
		});
		
	});
	
	
});

var zoneCategory = function(floor) {
	$.ajax({
		url: "zoneCategory",
		type: "post",
		data: {floor : floor},
		dataType: "json",
		success: function(data) {
			
			var tileList = $(".tileList").empty();

			for(var i=0; i<data.length; i++) {
				var tileItem = $("<tr></tr>");

				$("<td style='text-align: center;'></td>").text(data[i].TILE_NM).appendTo(tileItem);
				$("<td style='text-align: center;'></td>").text(data[i].TILE_CRDNT_X).appendTo(tileItem);
				$("<td style='text-align: center;'></td>").text(data[i].TILE_CRDNT_Y).appendTo(tileItem);
				$("<td style='text-align: center;'></td>").text(data[i].beaconset).appendTo(tileItem);
				
				tileItem.appendTo(tileList);
			}
		},
		error : function(data) {
			
		}
	});
}

var tileListReload = function(floor) {
	$.ajax({
		url: "tileReload",
		type: "post",
		data: {floor : floor},
		dataType: "json",
		success: function(data) {
			
			var tileList = $(".tileList").empty();

			for(var i=0; i<data.length; i++) {
				var tileItem = $("<tr></tr>");

				$("<td style='text-align: center;'></td>").text(data[i].TILE_NM).appendTo(tileItem);
				$("<td style='text-align: center;'></td>").text(data[i].TILE_CRDNT_X).appendTo(tileItem);
				$("<td style='text-align: center;'></td>").text(data[i].TILE_CRDNT_Y).appendTo(tileItem);
				$("<td style='text-align: center;'></td>").text(data[i].beaconset).appendTo(tileItem);
				
				tileItem.appendTo(tileList);
			}
		},
		error : function(data) {
			
		}
	});
}

var changeDetailCategory = function(detailctgry_code, drw_code) {

    $.ajax({
		url: "categorySetAllZone",
		type: "post",
		data: 
		{
			detailctgry_code : detailctgry_code, 
			drw_code : drw_code
		},
		dataType: "json",
		success: function(data) {
			
			if(data != null) {
				/*var selectBox = $("<select id='category' name='category'></select>");
				
				for(var i=0; i<data.length; i++) {
					$("<option></option>").val(data[i].DETAILCTGRY_CODE).text(data[i].DETAILCTGRY_NM).appendTo(selectBox);
				}
				
				selectBox.appendTo(tile_info);*/
				var tileList = $("#setTileList");
				
				for(var i=0; i<data.length; i++) {
					var info = data[i];

					var x = info.TILE_CRDNT_X;
					var y = info.TILE_CRDNT_Y;

					var row = $("div.tileMap > div").eq(x);
					var col = row.find("div.tile").eq(y);
					
					col.addClass("active");
				}
			}
			else {
				//$("<p></p>").text("등록된 카테고리가 없습니다.").appendTo(tile_info);
			}
		},
		error: function(data) {
			
		}
	});
}

var setTileCategory = function(jObject) {
	$.ajax({
		url: "setTileCategory",
		type: "post",
		contentType: "application/json;charset=UTF-8",
		data: JSON.stringify(jObject),
		dataType: "json",
		success: function(data) {
			
			if(data != null) {
				var floor = parseInt($("#floor").val());
				reloadCategorySet(floor, function() {
					var detailctgry_code = $("#detailCategory").val();
					var drw_code = parseInt($("#drw_code").val());
					changeDetailCategory(detailctgry_code, drw_code);
				});
			}
			else {
				//$("<p></p>").text("등록된 카테고리가 없습니다.").appendTo(tile_info);
			}
		},
		error: function(data) {
			
		}
	});
	
    
}
var reloadCategorySet = function(floor, callback) {
	//var countStory = $("#countStory").val();
	//var floor = $("#floor").val();
	
	$.ajax({
		url: "getDrawingFileName",
		type: "post",
		data: {
			floor : floor,
		},
		dataType: "json",
		success: function(data) {

			if(data != null) {
				
				/* 타일별로 색깔 저장 해주는 것 */
				var tileInfoList = data.tileInfoList;
				
				$("div.tile").css({'background-color' : ''});

				for(var i=0; i<tileInfoList.length; i++) {
					var info = tileInfoList[i];

					var x = info.TILE_CRDNT_X;
					var y = info.TILE_CRDNT_Y;

					var row = $("div.tileMap > div").eq(x);
					var col = row.find("div.tile").eq(y);

					col.css("background-color", hexToRgbA("#" + info.LCLASCTGRY_COLOR, 0.2));

				}


			}

			else {
				window.alert("도면이 없습니다. 등록해주세요");
			}
		},
		error: function(data) {
			console.log("아작스 에러남");
		}
	});
};
