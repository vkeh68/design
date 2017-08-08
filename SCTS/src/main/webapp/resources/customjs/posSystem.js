/**
 * 
 */
var daySalesSocket = new SockJS("/scts/daySales-ws");
var monthSalesSocket = new SockJS("/scts/monthSales-ws");
var productRankSocket = new SockJS("/scts/productRank-ws");
var customerRankSocket = new SockJS("/scts/customerRank-ws");
var sock = new SockJS("/scts/sales-ws");
var DashDaysock = new SockJS("/scts/echo-ws");


$(document).ready(function() {
	/* 상품번호(바코드) 입력해서 상품 한줄 추가하는 부분, 등록 버튼을 누를시 실행됨 */
	$("#getGoods").on("click", function() {
		var goods_code = $("#goods_code").val();

		$.ajax({
			url: "getGoodsAjax",
			type: "post",
			data: {goods_code : goods_code},
			dataType: "json",

			success: function(data){
				console.log(data);

				var goodsList = $('#goodsList');

				var equalsGoods = false;
				goodsList.find(".goodsItem").each(function(i, e) {
					console.log(  );
					if($(this).find(".goods_code").text() == data.goods_code) {
						equalsGoods = true;

						var purchsgoods_qy = parseInt($(this).find(".purchsgoods_qy").text());
						$(this).find(".purchsgoods_qy").text(purchsgoods_qy+1);

						reloadOnePrice($(this));
					}
				});

				if(!equalsGoods) {
					var goodsItem = $('<tr></tr>').addClass('goodsItem');
					$('<td></td>').addClass("goods_code").text(data.goods_code).appendTo(goodsItem);
					$('<td></td>').text('농심').appendTo(goodsItem);
					$('<td></td>').text(data.goods_nm).appendTo(goodsItem);
					$('<td></td>').addClass("goodsPrice").text(data.goods_pc).appendTo(goodsItem);	// 상품 한개 가격
					$('<td></td>').addClass("purchsgoods_qy").text(1).appendTo(goodsItem);		// 수량 (처음엔 1개로 들어감)
					$('<td></td>').addClass("price").text(data.goods_pc).appendTo(goodsItem);	// 수량 * 가격 , 처음엔 1개 기준으로 들어감
					$('<td></td>').addClass("dscnt_price").text(0).appendTo(goodsItem);

					goodsItem.appendTo(goodsList);
					reloadTotalPrice();
					reloadTotalDscnt();
					reloadtotalAmount();
				}
				$("#goods_code").val("");
			},
			error: function(data) {
				//console.log("에러뜸");
				window.alert("해당 상품이 존재하지 않습니다.");
				$("#goods_code").val("");
			}
		});
	});

	/*
	 * 쿠폰 포인트 모달 창에서 사용자의 아이디를 입력하였을 때
	 * 사용자가 현재 구매목록에서 사용할 수 있는 쿠폰들을 아작스로 디비에서 가져와서
	 * 모달창에 뿌려주는 역할을 함
	 */
	$('#getUserCoupon').on("click", function() {

		if($('#goodsList').find(".goodsItem").length == 0 ) {
			window.alert("구매할 상품을 먼저 등록 해주세요.");
			return;
		}

		var goods_code_Array = new Array(); 

		$('#goodsList').find(".goodsItem").each(function(i, e) {
			goods_code_Array.push( parseInt($(this).find(".goods_code").text()) );
		});

		console.log(goods_code_Array);
		var user_id = $('#user_id').val();
		console.log(user_id);

		$.ajax({

			url: "getUserCoupon",
			type: "post",
			contentType: "application/json",
			data: JSON.stringify({
				user_id : user_id,
				goods_code_Array : goods_code_Array
			}),
			dataType: "json",

			success: function(data){
				if(data.length > 0) {

					console.log(data);

					var couponList = $("#couponList");

					for(var i=0; i<data.length; i++) {
						var couponItem = $("<tr></tr>").addClass("couponItem");
						$('<input type="hidden"/>').addClass("coupon_code").val(data[i].coupon_code).appendTo(couponItem);
						$('<input type="hidden"/>').addClass("couponGoods_code").val(data[i].goods_code).appendTo(couponItem);
						$('<td></td>').text(data[i].goods_nm).appendTo(couponItem);
						$('<td></td>').text(data[i].coupon_nm).appendTo(couponItem);
						$('<td></td>').addClass("coupon_dscnt").text(data[i].coupon_dscnt).appendTo(couponItem);
						$('<td></td>').text(data[i].coupon_end_de).appendTo(couponItem);

						couponItem.appendTo(couponList);
					}
				}
				else {
					window.alert("사용할 수 있는 쿠폰이 없습니다.");
				}
			},
			error: function(data) {
				console.log("에러뜸");
			}
		});
	});

	/* 사용할 수 있는 쿠폰리스트에서 쿠폰들을 선택 후 등록버튼을 눌렀을때.
	 * 실행 되는 뷰페이지 작업
	 */
	$("#useCoupon").on("click", function() {

		$("#couponList").find(".active").each(function(i, e) {
			var goods_code = $(this).find(".couponGoods_code").val();
			var coupon_code = $(this).find(".coupon_code").val();
			var couponItem = $(this);
			console.log("외부 이치문 쿠폰 물품값 저장할때");

			$("#goodsList").find(".goodsItem").each(function(i, e) {
				if($(this).find('.goods_code').text() == goods_code) {
					console.log("내부 이치문에서 물품찾음");
					//$(this).find('.coupon_code').text(coupon_code);
					$('<input type="hidden"/>').addClass("useCoupon_code").val(coupon_code).appendTo($(this));
					reloadCouponOnePrice($(this), couponItem);
					return false;
				}
				console.log("내부 이치문 반복");
			});
		});
		$("#couponList").empty();
		closeModal();

	});


	/* 쿠폰 리스트(모달창) 에서 쿠폰 클릭햇을때 사용한다는 의미로 강조 표시 해줄 것 */
	$("#couponList").on("click", ".couponItem", function() {
		$(this).addClass("active");

	});

	$("#couponList").on("click", ".active", function() {
		$(this).removeClass("active");
	});

	/* 상품 리스트에서 상품 한개 클릭했을때 해당열에 강조 표시 */
	$("#goodsList").on("click", ".goodsItem", function() {
		$("#goodsList > .active").removeClass("active");

		$(this).addClass("active");
	});

	$("#goodsList").on("click", ".active", function() {
		$(this).removeClass("active");
	});

	/* 취소 버튼 눌럿을 시 선택된 열 삭제 */
	$("#cancleGoods").on("click", function() {
		$("#goodsList > .active").remove();
		reloadTotalPrice();
	});

	/* 수량 + 버튼 클릭 시 */
	$("#additionGoods").on("click", function() {
		var num = parseInt($("#goodsList > .active > .purchsgoods_qy").text());
		num++;

		console.log("현재 물품 수량 = " + num);
		$("#goodsList > .active > .purchsgoods_qy").text(num);
		reloadOnePrice($("#goodsList > .active"));
	});
	/* 수량 - 버튼 클릭 시 */
	$("#subtractGoods").on("click", function() {
		var num = parseInt($("#goodsList > .active > .purchsgoods_qy").text());
		if(num <= 1) {
			$("#goodsList > .active").remove();
		}
		else {
			num--;

			console.log("현재 물품 수량 = " + num);
			$("#goodsList > .active > .purchsgoods_qy").text(num);
		}
		reloadOnePrice($("#goodsList > .active"));
	});

	$(".numberPad").on("click", function() {
		var num = $(this).text();

		if(num == "D") {
			// 바코드에 입력된 숫자 한자리 지우는 것
			var length = $("#goods_code").val().length;
			var str = $("#goods_code").val().substr(0,length-1);

			$("#goods_code").val(str);
		}
		else if(num == "C") {
			// 바코드에 입력된 숫자 전체 지우는 것 들어가야함
			$("#goods_code").val("");
		}
		else {
			num = parseInt(num);
			// 바코드에 입력되어 있는 숫자에 현재 누른 숫자를 추가해줘야함

			var str = $("#goods_code").val();

			$("#goods_code").val( str + num );
		}
	});


	$(window).click(function(e) {
		if(e.target.classList.contains("modalPanel"))
			closeModal();
	});
	/********************************************************************************************************************************************/
	/********************************************************************************************************************************************/
	/***************************************************                                            *********************************************/
	/***************************************************                결제 관련 함수들                 *********************************************/
	/***************************************************                                            *********************************************/
	/********************************************************************************************************************************************/
	/********************************************************************************************************************************************/

	/* 결제 버튼 누를 시
	 * 
	 */


	/* 서버로 보낼 때 form 형식으로 post방식으로 배열값이랑 모두 전송하지만
	 * 실제 서버쪽에서 받아야 할 때 어떻게 받아야 할 지 찾을 시간이 없어서 임시로 주석 처리 하고
	 * 아작스로 처리함
	 */
	/*
	$("#card").on("click", function() {

		var $form = $('<form></form>');
		$form.attr('action', 'payment');
		$form.attr('method', 'post');
		$form.appendTo('body');


		$("#goodsList").find(".goodsItem").each(function(i, e) {

			//var goods_code = $("input[name=goods_code[]]").attr("type", "hidden");
			var goods_code = $("<input[name=goods_code[]]></input>").attr("type", "hidden");
			goods_code.val($(this).find('.goods_code').text());
			goods_code.appendTo($form);
			//var coupon_code = $("input[name=coupon_code[]").attr("type", "hidden");
			var coupon_code = $("<input[name=coupon_code[]]></input>").attr("type", "hidden");
			coupon_code.val($(this).find('.useCoupon_code').val());
			coupon_code.appendTo($form);
			//var purchsgoods_qy = $("input[name=purchsgoods_qy[]").attr("type", "hidden");
			var purchsgoods_qy = $("<input[name=purchsgoods_qy[]]></input>").attr("type", "hidden");
			purchsgoods_qy.val($(this).find('.purchsgoods_qy').text());
			purchsgoods_qy.appendTo($form);
		});

		var user_id = $("input[name=user_id]").attr("type", "hidden");
		user_id.val( $("#user_id_payment").text() );
		user_id.appendTo($form);
		var totalAmount = $("input[name=totalAmount]").attr("type", "hidden");
		totalAmount.val( $("#totalAmount").text() );
		totalAmount.appendTo($form);

		$form.submit();
	});
	 */

	$("#card").on("click", function() {
		var goodsList = new Array();
		var user_id = $("#user_id_payment").val();

		// 추후에 변경 필요
		
		// 현재 그냥 카드로 총금액 결제를 해버림.
		var totalAmount = $("#totalAmount").text();
		
		var setle_mth_code = parseInt( $("#settlement option:selected").val());
		
		$("#goodsList").find(".goodsItem").each(function(i, e) {
			var goods_code = $(this).find('.goods_code').text();
			var coupon_code = $(this).find('.useCoupon_code').val();
			var purchsgoods_qy = $(this).find('.purchsgoods_qy').text();

			var goodsItem = {
					goods_code : goods_code,
					coupon_code : coupon_code,
					purchsgoods_qy : purchsgoods_qy
			}
			goodsList.push(goodsItem);
		});
		
		var sendControllerData;
		var length = $("#settlement option").length;
		
		var setle_mth_code1 = new Array();
		var stprc1 = new Array();
		
		if(setle_mth_code != length){
			
			var stprc = parseInt( $("#totalAmount").text() ); // 결제 금액
		
			setle_mth_code1.push(setle_mth_code);
			stprc1.push(stprc);
			
			
		}else{
			
			
			$(".settlement").each(function(){
			   var a = $(this).find("option:selected").val();
			   var price = $(this).next().val();
			   console.log(price);
			   if(price != ""){
				   setle_mth_code1.push(parseInt(a));
			   }
			   
			});
			
			$(".tprice").each(function(){
				var price = $(this).val();
				
				if(price != "")
				{
					stprc1.push(parseInt(price));
				}
				
			});
			
	
		}
		
		sendControllerData = {
				user_id : user_id,
				setle_mth_code : setle_mth_code1,
				stprc : stprc1,
				goodsList : goodsList
		}
		
		console.log(sendControllerData);

		$.ajax({

			url: "payment",
			type: "post",
			contentType: "application/json",
			async:false,
			data: JSON.stringify({
				user_id : user_id,
				setle_mth_code : setle_mth_code1,
				stprc : stprc1,
				goodsList : goodsList
			}),
			success: function(data){
				//self.location.href = "mainPage";
				//location.reload();
				
				var urlindex = $(location).attr('href').indexOf('#');
				if(urlindex > 0) {
					$(location).attr('href', $(location).attr('href').substr(0,urlindex+1) );
				}
				window.alert("결제완료");
			},
			error: function(data) {
				console.log("에러뜸");
				console.log(data);
			}
		});


		var year = parseInt(new Date().getFullYear());
		var month = new Date().getMonth() + 1;

		if (month < 10) {
			month = "0" + month;
		}

		var mm = new Date().getMonth() - 3;
		if(mm < 10){
			mm = "0" +  mm;
		}


		var month1 = year + "-" + month;
		var date = year + "-" + month;
		var month2 = year + "-" + mm;
		var setle_mth_code = null;

		var sendData = JSON.stringify({
			month1 : month1,
			month2 : month2,
			setle_mth_code : setle_mth_code
		});


		var sockSendData = JSON.stringify({year2 : year, year1 : + (year-4)});
		

		var productRankSendData = JSON.stringify({
			date : date,
			standard : 1
		});
		
		
		//customerRankSocket.send();
		DashDaysock.send('text');
		monthSalesSocket.send(sendData);
		daySalesSocket.send('text');
		sock.send(sockSendData);
		productRankSocket.send(productRankSendData);
		
		
		




	});

	/********************************************************************************************************************************************/
	/********************************************************************************************************************************************/
	/***************************************************                                            *********************************************/
	/***************************************************            가격 계산 관련 함수들                 *********************************************/
	/***************************************************                                            *********************************************/
	/********************************************************************************************************************************************/
	/********************************************************************************************************************************************/
	/* 모달창 관련해서 처리하기 위한 것. */
	var closeModal = function() {
		var urlindex = $(location).attr('href').indexOf('#');
		if(urlindex > 0) {
			$(location).attr('href', $(location).attr('href').substr(0,urlindex+1) );
		}
	}

	/* 물품 한개의 추가, 삭제, 수량변경, 등의 이벤트가 발생햇을 때 가격을 처리하는 함수로서
	 * 물품 한개의 가격이 변동되어도 전체의 가격이 변동되기 때문에 모든 가격을 갱신함
	 */
	var reloadOnePrice = function(goodsItem) {

		var purchsgoods_qy = parseInt(goodsItem.find(".purchsgoods_qy").text());
		goodsItem.find(".price").text( parseInt(goodsItem.find(".goodsPrice").text()) * (purchsgoods_qy) );

		reloadTotalPrice();
		reloadTotalDscnt();
		reloadtotalAmount();
	};

	/* 전체 가격 변동이 일어날 경우 갱신하는 함수 */
	var reloadTotalPrice = function() {
		var totalPrice = 0;

		$("#goodsList").find(".goodsItem").each(function(i, e) {
			totalPrice += parseInt( $(this).find(".price").text() );
		});
		$("#totalPrice").text(totalPrice);
	};

	/* 쿠폰을 적용했을 때 물품 리스트에서 할인 가격을 갱신 해주는 함수
	 * 쿠폰 적용시 변동되는 가격은 물품 별 할인금액, 전체 할인 금액, 전체 합계가 변동됨
	 */
	var reloadCouponOnePrice = function(goodsItem, couponItem) {
		var dscnt = couponItem.find(".coupon_dscnt").text();
		var index = dscnt.indexOf("%");

		var price = parseInt(goodsItem.find(".goodsPrice").text());

		var dscnt_price = 0;

		console.log("price = " + price + "  dscnt = " + dscnt + "  index = " + index);
		console.log("dscntDouble = " + (dscnt.substr(0, index) / 100) );
		if(index > 0) {
			dscnt_price = parseInt( price * ( dscnt.substr(0, index) / 100 ) ) ;
		}
		else {
			dscnt_price = dscnt;
		}
		console.log(dscnt_price);
		goodsItem.find(".dscnt_price").text(dscnt_price);

		reloadTotalDscnt();
		reloadtotalAmount();
	};

	/* 물품들의 할인 금액을 전부 더해서 총 할인 금액에 표시해 주는 함수 */ 
	var reloadTotalDscnt = function() {
		console.log("이거 실행되나 TotalDscnt");
		var totalDscnt = 0;
		$("#goodsList").find(".goodsItem").each(function(i, e) {
			totalDscnt += parseInt( $(this).find(".dscnt_price").text() );
		});
		$("#totalDscnt").text(totalDscnt)
		console.log("총 할인 금액  = " + totalDscnt);
	};

	/* 전체 물품 가격과 전체 할인 가격을 계산해서 결제해야할 금액을 갱신해주는 함수 */
	var reloadtotalAmount = function() {
		var totalPrice = parseInt( $("#totalPrice").text() );
		var totalDscnt = parseInt( $("#totalDscnt").text() );

		console.log("할인 적용된 총 가격  = " + totalDscnt);

		var totalAmount = totalPrice - totalDscnt;
		$("#totalAmount").text(totalAmount);

	};

	/* 추후에 가격 입력시 ',' 를 찍어주는 것을 추가할 예정,
	 * 2개의 함수로 나누어서
	 * 1개는 숫자에 ','를 찍어서 반환 해주는 것과
	 * 한개는 ,를 제거해서 반환해주는 것으로 만들 예정
	 */
});